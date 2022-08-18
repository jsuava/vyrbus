package com.cystesoft.vyrbus.view.ctrl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TreeMap;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zul.Button;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.Doublebox;
import org.zkoss.zul.Image;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Longbox;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

import com.cystesoft.vyrbus.model.bean.Bus;
import com.cystesoft.vyrbus.model.bean.GastoBus;
import com.cystesoft.vyrbus.model.bean.Itinerario;
import com.cystesoft.vyrbus.model.bean.LiquidacionBus;
import com.cystesoft.vyrbus.model.bean.TipoGasto;
import com.cystesoft.vyrbus.service.exceptions.BusNullException;
import com.cystesoft.vyrbus.service.exceptions.CancelaGrabacionException;
import com.cystesoft.vyrbus.service.exceptions.GastosException;
import com.cystesoft.vyrbus.service.exceptions.GastosXBusNullException;
import com.cystesoft.vyrbus.service.exceptions.ItinerarioException;
import com.cystesoft.vyrbus.service.exceptions.OperacionNullException;
import com.cystesoft.vyrbus.service.locator.ServiceLocator;
import com.cystesoft.vyrbus.service.util.Constantes;
import com.cystesoft.vyrbus.service.util.Messages;
import com.cystesoft.vyrbus.service.util.MyTime;
import com.cystesoft.vyrbus.service.util.Util;
import com.cystesoft.vyrbus.service.util.UtilData;
import com.cystesoft.vyrbus.view.ui.DlgMessage;
import com.cystesoft.vyrbus.view.ui.WndFiltrarParametros;
import com.cystesoft.vyrbus.view.ui.WndOpcionesMantenimiento;
import com.cystesoft.vyrbus.view.ui.WndSeleccionaItinerario;

public class WndLiquidacionBus extends WndOpcionesMantenimiento {

	private static final long serialVersionUID = -7470312268557569150L;

	private  Longbox lbitinerario;
	private Combobox cmbBus;
	private Doublebox dbTotal;
	private Button btnBuscar;
	private Listbox listGastosXBus;

	private Button btnDelete;

	private LiquidacionBus liquidacionBus = null;
	private GastoBus gastoBus = null;

	private TreeMap<String, Object> criteriosBusqueda = new TreeMap<>();

	@Override
	public void onCreate() throws Exception {
		UtilData.cargarDataCombo(cmbBus, Bus.class, false);
		enlazarItinerario(btnBuscar);
		dbTotal.setLocale(Locale.US);
		onDeleteItem();

	}


	@Override
	public void initComponents() {
		lbitinerario = (Longbox) this.getFellow("lbitinerario");
		cmbBus = (Combobox) this.getFellow("cmbBus");
		dbTotal = (Doublebox) this.getFellow("dbTotal");
		btnBuscar = (Button) this.getFellow("btnBuscar");
		btnDelete= (Button) this.getFellow("btnDelete");
		listGastosXBus = (Listbox) this.getFellow("listGastosXBus");
	}

	/**
	 * Elimina el item de la lista "Gastos por bus"
	 */
	private void onDeleteItem(){
		btnDelete.addEventListener(Events.ON_CLICK,new  EventListener<Event>() {
			@Override
			public void onEvent(Event event) throws Exception {
				if (listGastosXBus.getSelectedIndex()>=0){
					Messagebox.show(Messages.getString("WndLiquidacionBus.Questin.QuitarGasto"), DlgMessage.NOMBREAPLICACION, DlgMessage.BTN_YESNO, Messagebox.QUESTION, new EventListener<Event>() {
						@Override
						public void onEvent(Event e) throws Exception {
							if(e.getName().equals("onYes")){
								Integer index =listGastosXBus.getSelectedIndex();
								listGastosXBus.removeItemAt(index);
								calculatotal();
							}
						}
					});
				}else{
					DlgMessage.information(Messages.getString("WndLiquidacionBus.Information.SeleccioneIndex"));
				}
			}
		});
	}

	public void calculatotal(){
		double total =0;
		for (int i=0; i < listGastosXBus.getItems().size(); i++){
			Component component =  listGastosXBus.getChildren().get(i+1);
			Listcell listcell = (Listcell) component.getChildren().get(2);
			Double monto = ((Doublebox) listcell.getChildren().get(0)).getValue();
			total +=+monto;

		}
		dbTotal.setValue(total);
	}


	@Override
	public void onNew() throws Exception {
		dbTotal.setReadonly(true);
		dbTotal.setValue(0);
		cmbBus.setSelectedIndex(0);
		Util.limpiarListbox(listGastosXBus);
	}

	@Override
	public void onSearch() throws Exception {

		final WndFiltrarParametros oWndFiltrar = new WndFiltrarParametros();

		oWndFiltrar.addParameter("Número de Itinerario", Longbox.class);

		this.appendChild(oWndFiltrar);
		oWndFiltrar.setMode("modal");
		oWndFiltrar.addEventListener(com.cystesoft.vyrbus.view.ui.Events.ON_FILTER, new EventListener<Event>() {

			@Override
			public void onEvent(Event event) throws Exception {

				Long iditinerario = ((Longbox) oWndFiltrar.getParameterValue("Número de Itinerario")).getValue();
				String estadoRegistro = Constantes.VALUE_ACTIVO;

				criteriosBusqueda.remove("itinerario");
				criteriosBusqueda.remove("estadoRegistro");
				criteriosBusqueda.remove("id");
				if (!(iditinerario==null)){
					Itinerario itinerario = new Itinerario();
					itinerario.setId(iditinerario);
					criteriosBusqueda.put("itinerario", itinerario);
				}
				criteriosBusqueda.put("estadoRegistro", estadoRegistro);

				listarRegistros(ServiceLocator.getLiquidacionBusManager().buscarPorX(criteriosBusqueda, null));
			}
		});
	}

	@Override
	public void onRefresh(int tab) throws Exception {
		if (!criteriosBusqueda.isEmpty()) {
			listarRegistros(ServiceLocator.getLiquidacionBusManager().buscarPorX(criteriosBusqueda, null));
		}
	}

	@Override
	public void onModify(int tab) throws Exception {
		Long id = new Long(0);
		id = new Long((String) listboxLista.getSelectedItem().getValue());
		mantenimiento(id);

	}

	@Override
	public void onCancel(int action) throws Exception {
		Util.limpiarListbox(listGastosXBus);

	}

	@Override
	public void onSave(int action) throws Exception {
		try{
			if (lbitinerario.getValue()==null)
				throw new ItinerarioException(ItinerarioException.ITINERARIO_NULL); //ItinerarioNullException();
			else if (!(cmbBus.getSelectedItem().getValue() instanceof Bus))
				throw new BusNullException();
			else if (listGastosXBus.getItems().size() == 0)
				throw new GastosXBusNullException();

			/*Valida que esten correctamente ingresados los gastos Bus*/
			validadGastoXBus();

			if (action == ACTION_NEW)
				liquidacionBus= new LiquidacionBus();
			else
				liquidacionBus.setId(new Integer(textboxId.getText()));

			Itinerario itinerario = new Itinerario();
			itinerario.setId(lbitinerario.getValue());
			Bus bus = new Bus();
			bus.setId(((Bus) cmbBus.getSelectedItem().getValue()).getId());
			bus.setCodigo(((Bus) cmbBus.getSelectedItem().getValue()).getCodigo());

			liquidacionBus.setItinerario(itinerario);
			liquidacionBus.setBus(bus);
			liquidacionBus.setTotal(dbTotal.getValue());
			liquidacionBus.setEstadoRegistro(Constantes.VALUE_ACTIVO);

			switch (action) {
				case ACTION_NEW:
					UtilData.auditarRegistro(liquidacionBus, getUsuario(), Executions.getCurrent());
					ServiceLocator.getLiquidacionBusManager().guardar(liquidacionBus);
					break;
				case ACTION_MODIFY:
					UtilData.auditarRegistro(liquidacionBus, true, getUsuario(), Executions.getCurrent());
					ServiceLocator.getLiquidacionBusManager().actualizar(liquidacionBus);

					ServiceLocator.getGastoBusManager().delete(liquidacionBus.getId().longValue());
					break;
			}

			for (int i=0; i < listGastosXBus.getItems().size(); i++){
				GastoBus gastoBus = new GastoBus();
				/*Tipo Gasto*/
				TipoGasto tipoGasto = new TipoGasto();
				Component CmbtipoGasto =  listGastosXBus.getChildren().get(i+1);
				Listcell listcell = (Listcell) CmbtipoGasto.getChildren().get(1);
				tipoGasto.setId( ((TipoGasto)  ((Combobox) listcell.getChildren().get(0)).getSelectedItem().getValue()).getId());
				/*Monto*/
				Component dbMonto =  listGastosXBus.getChildren().get(i+1);
				Listcell listcell2 = (Listcell) dbMonto.getChildren().get(2);
				Double monto = ((Doublebox) listcell2.getChildren().get(0)).getValue();
				/*Operacion*/
				Component cmbOperacion =  listGastosXBus.getChildren().get(i+1);
				Listcell listcell3 = (Listcell) cmbOperacion.getChildren().get(3);
				Integer operacion = ( ((Combobox) listcell3.getChildren().get(0)).getSelectedItem().getValue());
				/*Fecha Operacion*/
				Component dbFechaOperacion =  listGastosXBus.getChildren().get(i+1);
				Listcell listcell4 = (Listcell) dbFechaOperacion.getChildren().get(4);
				Date fechaOperacion = ( ((Datebox) listcell4.getChildren().get(0)).getValue());
				/*Observaciones*/
				Component txtObservaciones =  listGastosXBus.getChildren().get(i+1);
				Listcell listcell5 = (Listcell) txtObservaciones.getChildren().get(5);
				String observacion = ( ((Textbox) listcell5.getChildren().get(0)).getText());

				gastoBus.setLiquidacionBus(liquidacionBus);
				gastoBus.setTipoGasto(tipoGasto);
				gastoBus.setMonto(monto);
				gastoBus.setOperacion(operacion);
				gastoBus.setFechaOperacion(fechaOperacion);
				gastoBus.setObservacion(observacion);
				gastoBus.setEstadoRegistro(Constantes.VALUE_ACTIVO);

				UtilData.auditarRegistro(gastoBus, getUsuario(), Executions.getCurrent());
				ServiceLocator.getGastoBusManager().guardar(gastoBus);

			}

			Util.limpiarListbox(listGastosXBus);
			criteriosBusqueda.remove("estadoRegistro");
			criteriosBusqueda.remove("itinerario");
			criteriosBusqueda.put("id", liquidacionBus.getId());
			listarRegistros(ServiceLocator.getLiquidacionBusManager().buscarPorX(criteriosBusqueda, null));


		} catch (GastosXBusNullException gxbnex){
			DlgMessage.information(Messages.getString("WndLiquidacionBus.Information.GastosXBusNull"));
			throw new CancelaGrabacionException();
		} catch (BusNullException bnex){
			DlgMessage.information(Messages.getString("WndLiquidacionBus.Information.BusNull"));
			throw new CancelaGrabacionException();
		}catch (ItinerarioException inex){
			if(inex.getTipo().intValue()==ItinerarioException.ITINERARIO_NULL){
				DlgMessage.information(Messages.getString("WndLiquidacionBus.Information.ItinerarioNull"));
				throw new CancelaGrabacionException();
			}
		}catch (OperacionNullException onex){
			DlgMessage.information(Messages.getString("WndLiquidacionBus.Information.OperacionNull"));
			throw new CancelaGrabacionException();
		}catch (GastosException gex){
			if(gex.getTipo()==GastosException.MONTO_NULL){
				DlgMessage.information(Messages.getString("WndLiquidacionBus.Information.MontoGastoNull"));
				throw new CancelaGrabacionException();
			}else if(gex.getTipo()==GastosException.TIPO_GASTO_NULL){
				DlgMessage.information(Messages.getString("WndLiquidacionBus.Information.TipoGastoNull"));
				throw new CancelaGrabacionException();
			}
		}catch (Exception ex) {
			DlgMessage.error(this.getClass().getName()+" "+ex.getMessage());
			ex.printStackTrace(); throw new CancelaGrabacionException();
		}

	}


	@Override
	public void onClose() {
		closeTabWindow();
	}

	@Override
	public void onDelete(int tab) throws Exception {
		Long id = (long) 0;

		switch (tab) {
			case TAB_LIST:
				id = new Long((String) listboxLista.getSelectedItem().getValue());
				break;

			case TAB_MAINTENANCE:
				id = new Long(textboxId.getText());
				break;
		}

		ServiceLocator.getLiquidacionBusManager().inactivar(id);

	}

	@Override
	public void onPrint(int tab) throws Exception {
		// TODO Auto-generated method stub

	}

	@Override
	public void onExport(int tab) throws Exception {
		// TODO Auto-generated method stub

	}

	@Override
	public void onHelp() throws Exception {
		// TODO Auto-generated method stub

	}

	@Override
	public void onChangeTab(int tab) throws Exception {
		switch (tab) {
			case TAB_LIST:
				break;

			case TAB_MAINTENANCE:
				if (listboxLista.getSelectedIndex() > -1) {
					mantenimiento(new Long((String) listboxLista.getSelectedItem().getValue()));
				}
				break;
		}

	}


	/**
	 * Permite enlazar los controles a la ventana de selección de Itinerario
	 * @param button :ha este Button se le adjuntara un listener con la llamada a la ventana de selección de itinerario
	 * @see WndItinerario:
	 */
	public  void enlazarItinerario(final Button button) {
		button.setTooltiptext("Seleccionar Itinerario");
		button.addEventListener(Events.ON_CLICK, new EventListener<Event>() {
			@Override
			public void onEvent(Event event) throws Exception {
				final WndSeleccionaItinerario oWndSeleccionaItinerario = new WndSeleccionaItinerario();
				boolean buscarVentanaParent = true;
				Component oComponent = button.getParent();
				while(buscarVentanaParent){
					 if(oComponent instanceof Window) {
						 oComponent.appendChild(oWndSeleccionaItinerario);
						 buscarVentanaParent = false;
					 }else{
					 	oComponent = oComponent.getParent();
					 }
				}
				oWndSeleccionaItinerario.onCreate();
				oWndSeleccionaItinerario.setMode("modal");
				oWndSeleccionaItinerario.setVisible(true);
				oWndSeleccionaItinerario.addEventListener(Events.ON_SELECT, new EventListener<Event>() {
					@Override
					public void onEvent(Event event) throws Exception {
						/*Recuepra el numero de itinerario*/
						if (!(oWndSeleccionaItinerario.getItinerario().getBus()==null))
							Util.seleccionarValorItemCombo(Bus.class, cmbBus, oWndSeleccionaItinerario.getItinerario().getBus().getId());
						else
							cmbBus.setSelectedIndex(0);

						lbitinerario.setValue(oWndSeleccionaItinerario.getItinerario().getId());

					}
				});
			}
		});
	}


	/***
	 *
	 * @param cargarDetalle	: true(indica que sera invocado desde la edicon), false(sera invocado desde el button "insertar item")
	 * @throws Exception
	 */
	public void AddFilaGastoXBuss(Boolean cargarDetalle) throws Exception{


		MyTime myTime = new MyTime();

		Listitem item = null;
		Listcell cell =null;
		Combobox cmbTipoGasto = new Combobox();

		/*item*/
		int index = listGastosXBus.getItemCount()+1;
		item = new Listitem();
		cell = new Listcell(Integer.toString(index));
		item.appendChild(cell);

		/*Tipo de Gasto*/
		cell = new Listcell();
		UtilData.cargarDataCombo(cmbTipoGasto, TipoGasto.class, false);
		if (cargarDetalle)
			Util.seleccionarValorItemCombo(TipoGasto.class, cmbTipoGasto, gastoBus.getTipoGasto().getId());
		else
			cmbTipoGasto.setSelectedIndex(0);
		cmbTipoGasto.setWidth("120px");cmbTipoGasto.setReadonly(true);
		cell.appendChild(cmbTipoGasto);
		item.appendChild(cell);

		/*Monto*/
		cell = new Listcell();
		final Doublebox dbMonto = new Doublebox();
		dbMonto.setLocale(Locale.US);
		dbMonto.setFormat("#,##0.00");dbMonto.setWidth("80px");
		dbMonto.setWidth("60px");
		if (cargarDetalle)
			dbMonto.setValue(gastoBus.getMonto());
		else
			dbMonto.setValue(0);
		cell.appendChild(dbMonto);
		item.appendChild(cell);
		dbMonto.addEventListener(Events.ON_CHANGE, new EventListener<Event>(){
			@Override
			public void onEvent(Event event) throws Exception {
				calculatotal();
			}
		});

		/*Operacion*/
		cell = new Listcell();
		Combobox cmbOperacion= new Combobox();
		cmbOperacion.setWidth("90px");cmbOperacion.setReadonly(true);
		UtilData.cargarTipoOperacion(cmbOperacion);
		if (cargarDetalle)
			Util.seleccionarValorItemCombobox(cmbOperacion, gastoBus.getOperacion());
		else
			cmbOperacion.setSelectedIndex(0);
		cell.appendChild(cmbOperacion);
		item.appendChild(cell);

		/*Fecha operacion*/
		cell = new Listcell();
		Datebox dbFechaOperacion= new Datebox();
		dbFechaOperacion.setFormat("dd/MM/yyyy"); dbFechaOperacion.setWidth("98px");
		if (cargarDetalle)
			dbFechaOperacion.setValue(gastoBus.getFechaOperacion());
		else
			dbFechaOperacion.setValue(Constantes.FORMAT_DATE.parse(myTime.dateServer()));
		cell.appendChild(dbFechaOperacion);
		item.appendChild(cell);

		/*Observaciones*/
		cell = new Listcell();
		Textbox txtObservacion= new Textbox();
		txtObservacion.setWidth("230px");txtObservacion.setMaxlength(255);
		if (cargarDetalle)
			txtObservacion.setText(gastoBus.getObservacion());
		cell.appendChild(txtObservacion);
		item.appendChild(cell);

		/*accion*/
//		cell=new Listcell();
//		Image imgEditar= new Image();
//		imgEditar.setSrc("/resources/mp_editarEnabled.png");
//		cell.appendChild(imgEditar);
//		item.appendChild(cell);

		listGastosXBus.appendChild(item);

	}

	/**
	 * Lista los registro encontrados
	 */
	public void listaRegis(List<GastoBus> lstgastBus){

		Listitem item= null;
		Listcell cell= null;
		int x=0;

		for(GastoBus gastoBus: lstgastBus){
			x++;
			item=new Listitem();
			cell= new Listcell(String.valueOf(x));
			item.appendChild(cell);
			cell= new Listcell(gastoBus.getTipoGasto().getDenominacion());
			item.appendChild(cell);
			cell= new Listcell(gastoBus.getMonto().toString());
			cell.setStyle("font-size:11px !important");
			item.appendChild(cell);
			cell= new Listcell(gastoBus.getOperacion().toString());
			item.appendChild(cell);
			cell=new Listcell(Constantes.FORMAT_DATE.format(gastoBus.getFechaOperacion()));
			cell.setStyle("font-size:11px !important");
			item.appendChild(cell);
			cell=new Listcell(gastoBus.getObservacion());
			item.appendChild(cell);

			final Image imgEdit= new Image();
			imgEdit.setSrc("/resources/mp_editarEnabled.png");

			imgEdit.setId(String.valueOf(x));
			cell= new Listcell();
			cell.appendChild(imgEdit);
			item.appendChild(cell);

			imgEdit.addEventListener(Events.ON_CLICK, new EventListener<Event>() {
				@Override
				public void onEvent(Event event) throws Exception {
//					Listitem item= listGastosXBus.getItemAtIndex(Integer.valueOf(imgEdit.getId()));
//
//					Combobox combobox= new   Combobox();


				}
			});


			item.setValue(gastoBus);
			listGastosXBus.appendChild(item);

		}


	}


	public void validadGastoXBus() throws Exception{
		try{
			for (int i=0; i < listGastosXBus.getItems().size(); i++){
				/*Tipo Gasto*/
				Component CmbtipoGasto =  listGastosXBus.getChildren().get(i+1);
				Listcell listcell = (Listcell) CmbtipoGasto.getChildren().get(1);
				if (!( ((Combobox) listcell.getChildren().get(0)).getSelectedItem().getValue() instanceof TipoGasto))
					throw new GastosException(GastosException.TIPO_GASTO_NULL);
				/*Monto*/
				Component dbMonto =  listGastosXBus.getChildren().get(i+1);
				Listcell listcell2 = (Listcell) dbMonto.getChildren().get(2);
				Double monto = ((Doublebox) listcell2.getChildren().get(0)).getValue();
				if (monto == null)
					throw new GastosException(GastosException.MONTO_NULL);
				else if (monto <=0)
					throw new GastosException(GastosException.MONTO_NULL);
				/*Operacion*/
				Component cmbOperacion =  listGastosXBus.getChildren().get(i+1);
				Listcell listcell3 = (Listcell) cmbOperacion.getChildren().get(3);
				 if( ((Combobox) listcell3.getChildren().get(0)).getSelectedIndex() == 0)
					 throw new  OperacionNullException();
			}

		}catch (GastosException gex){
			if(gex.getTipo()==GastosException.MONTO_NULL){
				throw new GastosException(GastosException.MONTO_NULL);
			}else if(gex.getTipo()==GastosException.TIPO_GASTO_NULL){
				throw new GastosException(GastosException.TIPO_GASTO_NULL);
			}
		}catch (OperacionNullException onex){
			throw new OperacionNullException();
		}catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	private void listarRegistros(ArrayList<LiquidacionBus> lstRegistros) {
		ArrayList<Object> lstLiquidacionBus = new ArrayList<>();

		for(int r = 0; r < lstRegistros.size(); r ++) {
			LiquidacionBus liquidacionBus = lstRegistros.get(r);
			ArrayList<Object> lstFila = new ArrayList<>();

			lstFila.add(liquidacionBus.getId());
			lstFila.add(r + 1);
			lstFila.add(liquidacionBus.getBus().getCodigo());
			lstFila.add(liquidacionBus.getItinerario().getId());
			lstFila.add(liquidacionBus.getTotal());

			lstLiquidacionBus.add(lstFila);
		}

		Util.llenarListbox(listboxLista, lstLiquidacionBus, true);
	}

	private void mantenimiento(Long id) throws Exception{
		liquidacionBus = new LiquidacionBus();
		liquidacionBus = ServiceLocator.getLiquidacionBusManager().buscarXId(id);

		textboxId.setText(liquidacionBus.getId().toString());
		lbitinerario.setValue(liquidacionBus.getItinerario().getId());
		Util.seleccionarValorItemCombo(Bus.class, cmbBus, liquidacionBus.getBus().getId());
		dbTotal.setValue(liquidacionBus.getTotal());

		/*Recupera gastos del bus*/
		Util.limpiarListbox(listGastosXBus);
		TreeMap<String, Object> criteriosBusqueda = new TreeMap<>();
		ArrayList<GastoBus> list = new ArrayList<>();
		criteriosBusqueda.put("liquidacionBus", liquidacionBus);
		list = ServiceLocator.getGastoBusManager().buscarPorX(criteriosBusqueda, null);
//		listaRegis(list);
		for (GastoBus gastoBus : list){
			this.gastoBus=gastoBus;
			AddFilaGastoXBuss(true);
		}
	}


}
