/**
 * Proyecto		: SISVYR
 * Sistema		: Sistema de Ventas y Reservas
 * Descripción	: 
 * Autor		: jM
 * Fecha		: 21/06/2012
 */
package com.cystesoft.vyrbus.view.ctrl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.DropEvent;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zul.Button;
import org.zkoss.zul.Checkbox;
import org.zkoss.zul.Div;
import org.zkoss.zul.Grid;
import org.zkoss.zul.Groupbox;
import org.zkoss.zul.Image;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Row;
import org.zkoss.zul.Rows;
import org.zkoss.zul.Separator;
import org.zkoss.zul.Spinner;
import org.zkoss.zul.Tab;
import org.zkoss.zul.Tabbox;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Vbox;

import com.cystesoft.vyrbus.model.bean.MapaBus;
import com.cystesoft.vyrbus.model.bean.Servicio;
import com.cystesoft.vyrbus.service.exceptions.CancelaGrabacionException;
import com.cystesoft.vyrbus.service.exceptions.DenominacionDuplicadaException;
import com.cystesoft.vyrbus.service.exceptions.DenominacionNullException;
import com.cystesoft.vyrbus.service.exceptions.MapaBusNotUpdateException;
import com.cystesoft.vyrbus.service.exceptions.NombreCortoDuplicadoException;
import com.cystesoft.vyrbus.service.exceptions.NombreCortoNullException;
import com.cystesoft.vyrbus.service.exceptions.NumeroAsientoNullException;
import com.cystesoft.vyrbus.service.exceptions.NumeroColumnasNullException;
import com.cystesoft.vyrbus.service.exceptions.NumeroFilasNullException;
import com.cystesoft.vyrbus.service.locator.ServiceLocator;
import com.cystesoft.vyrbus.service.mappers.Asiento;
import com.cystesoft.vyrbus.service.mappers.ButtonGenerarMapa;
import com.cystesoft.vyrbus.service.mappers.Cafeteria;
import com.cystesoft.vyrbus.service.mappers.Coordenada;
import com.cystesoft.vyrbus.service.mappers.ElementoBus;
import com.cystesoft.vyrbus.service.mappers.Monitor;
import com.cystesoft.vyrbus.service.mappers.Papelera;
import com.cystesoft.vyrbus.service.mappers.ServiciosHigienicos;
import com.cystesoft.vyrbus.service.util.Constantes;
import com.cystesoft.vyrbus.service.util.Messages;
import com.cystesoft.vyrbus.service.util.Util;
import com.cystesoft.vyrbus.service.util.UtilData;
import com.cystesoft.vyrbus.view.ui.DlgMessage;
import com.cystesoft.vyrbus.view.ui.WndFiltrarParametros;
import com.cystesoft.vyrbus.view.ui.WndOpcionesMantenimiento;

/**
 *
 * @author jM
 * @since JDK1.6
 */
public class WndCreacionMapaBus extends WndOpcionesMantenimiento {

	private static final long serialVersionUID = 7117538608765334373L;

	private Textbox txtDenominacion;
	private Textbox txtNombreCorto;
	private Checkbox chkBusDosPisos;
	private Spinner spAsientos;
	private Spinner spFilas;
	private Spinner spColumnas;
	private Spinner spAsientos2;
	private Spinner spFilas2;
	private Spinner spColumnas2;
	private Div divTv;
	private Div divAsientos;
	private Div divCafeteria;
	private Div divSSHH;
	private Div divPapelera;
	private Groupbox grpSegundoPiso;
	private Vbox vbxEstructuraBus;
	private Button btnGuardarEstructura;
	private Button btnCancelar;
	private Tabbox tbMantenimiento;
	private Tab tabEstructura;
	
	private String prefijoAsiento="";
	private String sufijoAsiento="";
	private String prefijoElementos = "";
	private int contadorIdImagen = 0;
	private boolean generarElementos = true;
	
	private Servicio oServicio = null;

	private TreeMap<String, Object> criteriosBusqueda = new TreeMap<String, Object>();
	private List<String> criteriosOrdenar = null;
	private ArrayList<Grid> listaGridPisos = new ArrayList<Grid>();
	private Servicio servicio = null;
	private ArrayList<Object> lstElementosBus = null;
	
	private static final String IMAGE_PRIMER_PISO = "resources/mapa/bus_primerPiso.png";
	private static final String IMAGE_SEGUNDO_PISO = "resources/mapa/bus_segundoPiso.png";
	private static final String IMAGE_CAFETERIA = "resources/mapa/bus_cafeteria.png";
	private static final String IMAGE_TELEVISOR = "resources/mapa/bus_tv.png";
	private static final String IMAGE_SSHH = "resources/mapa/bus_wc.png";
	private static final String IMAGE_PAPELERA = "resources/mapa/bus_papelera.png";
	
	private static final int TIPO_ASIENTO = 0;
	private static final int TIPO_MONITOR = 1;
	private static final int TIPO_CAFETERIA = 2;
	private static final int TIPO_SSHH = 3;

	
	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.window.ui.IBase#onCreate()
	 */
	@Override
	public void onCreate() throws Exception {
		criteriosOrdenar = new ArrayList<String>();
		criteriosOrdenar.add("denominacion");
		
		chkBusDosPisos.addEventListener(Events.ON_CHECK, new EventListener<Event>() {

			@Override
			public void onEvent(Event event) throws Exception {
				habilitarSegundoPiso(chkBusDosPisos.isChecked());
			}
		});

//		btnGenerarEstructuraBus.addEventListener(Events.ON_CLICK, new EventListener<Event>() {
//
//			@Override
//			public void onEvent(Event event) throws Exception {
//				if (hbEstructuraBus.getChildren().size() > 0) {
//					limpiarEstructuraBus();
//				}
//
//				generarEstructuraBus();
//			}
//		});
	}

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.window.ui.IBase#initComponents()
	 */
	@Override
	public void initComponents() {
		txtDenominacion = (Textbox) getFellow("txtDenominacion");
		txtNombreCorto = (Textbox) getFellow("txtNombreCorto");
		chkBusDosPisos = (Checkbox) getFellow("chkBusDosPisos");
		spAsientos = (Spinner) getFellow("spAsientos");
		spFilas = (Spinner) getFellow("spFilas");
		spColumnas = (Spinner) getFellow("spColumnas");
		spAsientos2 = (Spinner) getFellow("spAsientos2");
		spFilas2 = (Spinner) getFellow("spFilas2");
		spColumnas2 = (Spinner) getFellow("spColumnas2");
		grpSegundoPiso = (Groupbox)this.getFellow("grpSegundoPiso");
		/*	Para el Mapa de Bus	*/
		tabEstructura = (Tab)this.getFellow("tabEstructura");
		divTv = (Div) this.getFellow("divTv");
		divAsientos = (Div) this.getFellow("divAsientos");
		divCafeteria = (Div) this.getFellow("divCafeteria");
		divSSHH = (Div) this.getFellow("divSSHH");
		divPapelera = (Div)this.getFellow("divPapelera");
		vbxEstructuraBus = (Vbox) getFellow("vbxEstructuraBus");
		tbMantenimiento = (Tabbox) getFellow("tbMantenimiento");
		btnGuardarEstructura = (Button) getFellow("btnGuardarEstructura");
		btnCancelar = (Button)this.getFellow("btnCancelar");
	}

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.window.ui.IOpcionesMantenimiento#onNew()
	 */
	@Override
	public void onNew() {
		habilitarSegundoPiso(false);
//		tbMantenimiento.setSelectedIndex(0);
//		habilitarSegundoPiso(false);
	}

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.window.ui.IOpcionesMantenimiento#onSearch()
	 */
	@Override
	public void onSearch() {
		final WndFiltrarParametros oWndFiltrar = new WndFiltrarParametros();

		oWndFiltrar.addParameter("DENOMINACION :", String.class);
		oWndFiltrar.addParameter("CODIGO :", String.class);
		oWndFiltrar.addParameter("NOMBRE CORTO :", String.class);
		
		this.appendChild(oWndFiltrar);
		oWndFiltrar.setMode("modal");
		oWndFiltrar.addEventListener(com.cystesoft.vyrbus.view.ui.Events.ON_FILTER, new EventListener<Event>() {

			@Override
			public void onEvent(Event event) throws Exception {
				String denominacion = (String) oWndFiltrar.getParameterValue("DENOMINACION :");
				String codigo = (String) oWndFiltrar.getParameterValue("CODIGO :");
				String nombreCorto = (String) oWndFiltrar.getParameterValue("NOMBRE CORTO :");
				String estadoRegistro = Constantes.VALUE_ACTIVO;
				
				if (denominacion.trim().equals("")) {
					criteriosBusqueda.remove("denominacion");
				}else {criteriosBusqueda.put("denominacion", "%" + denominacion + "%");}

				if (codigo.trim().equals("")) {
					criteriosBusqueda.remove("codigo");
				}else {criteriosBusqueda.put("codigo", "%" + codigo + "%");}

				if (nombreCorto.trim().equals("")) {
					criteriosBusqueda.remove("nombreCorto");
				}else {criteriosBusqueda.put("nombreCorto", "%" + nombreCorto + "%");}

				criteriosBusqueda.put("estadoRegistro", estadoRegistro);
				listarRegistros(ServiceLocator.getServicioManager().buscarPorX(criteriosBusqueda, criteriosOrdenar));
			}
		});
	}
	
	private void listarRegistros(ArrayList<Servicio> lstRegistros) {
		
		ArrayList<Object> lstServicios = new ArrayList<Object>();

		for(int r = 0; r < lstRegistros.size(); r ++) {
			Servicio oServicio = lstRegistros.get(r);
			ArrayList<Object> lstFila = new ArrayList<Object>();
			lstFila.add(oServicio.getId());
			lstFila.add(r + 1);
			lstFila.add(oServicio.getDenominacion());
			lstFila.add(oServicio.getNombreCorto());
			lstFila.add(oServicio.getNumeroPisos());
			lstFila.add(oServicio.getNumeroAsientosPiso1());	
			lstFila.add(oServicio.getNumeroFilasPiso1());
			lstFila.add(oServicio.getNumeroColumnasPiso1());
			lstServicios.add(lstFila);
		}
		
		Util.llenarListbox(listboxLista, lstServicios, true);
		
		try{
			List<MapaBus> lstServiceWithMap = ServiceLocator.getMapaBusManager().buscarServiciosWithMapa();
			for(int i=0; i<listboxLista.getItemCount(); i++){
				Listitem item = listboxLista.getItems().get(i);
				
				final ButtonGenerarMapa tblButon = new ButtonGenerarMapa("Generar Estructura");
				tblButon.addEventListener(Events.ON_CLICK, new EventListener<Event>() {
					public void onEvent(Event e){
						if(tblButon.getLabel().equals("Generar Estructura"))
							generaEstructura(tblButon.getIdServicio());
						else
							modificaEstructura(tblButon.getIdServicio());
					}
				});
				tblButon.setIdServicio(Integer.valueOf(item.getValue().toString()));
				Listcell cell = new Listcell();
				cell.appendChild(tblButon);
				item.appendChild(cell);
				for(MapaBus mapaBus : lstServiceWithMap){
					if(mapaBus.getServicio().getId().intValue()==Integer.valueOf(item.getValue().toString()).intValue()){
						tblButon.setLabel("Modificar Estructura");
						break;
					}
				}
				
			}
		}catch(Exception ex){
			ex.printStackTrace();
		}
	}

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.window.ui.IOpcionesMantenimiento#onRefresh(int)
	 */
	@Override
	public void onRefresh(int tab) {
		try{
			if(!criteriosBusqueda.isEmpty())
				this.listarRegistros(ServiceLocator.getServicioManager().buscarPorX(criteriosBusqueda, criteriosOrdenar));
		}catch(Exception ex){
			DlgMessage.error(this.getClass().getName()+" "+ex.getMessage());
		}
	}

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.window.ui.IOpcionesMantenimiento#onModify(int)
	 */
	@Override
	public void onModify(int tab) {
		Long id = new Long(0);
		id = new Long((String) listboxLista.getSelectedItem().getValue());
		this.mantenimientoRegistro(id);
	}
	
	/**
	 * Realiza la asignación de información a los controles.
	 * @param id	: Identificador del registro a modificar
	 */
	private void mantenimientoRegistro(Long id) {
		try{
			oServicio = ServiceLocator.getServicioManager().buscarPorId(id);
			
			textboxId.setText(oServicio.getId().toString());
			txtDenominacion.setText(oServicio.getDenominacion());
			txtNombreCorto.setText(oServicio.getNombreCorto());
			if(oServicio.getNumeroPisos().intValue()==2){
				chkBusDosPisos.setChecked(true);
				spAsientos2.setValue(oServicio.getNumeroAsientosPiso2());
				spFilas2.setValue(oServicio.getNumeroFilasPiso2());
				spColumnas2.setValue(oServicio.getNumeroColumnasPiso2());
				grpSegundoPiso.setVisible(true);
			}else{
				chkBusDosPisos.setChecked(false);
				spAsientos2.setValue(0);
				spFilas2.setValue(0);
				spColumnas2.setValue(0);
				grpSegundoPiso.setVisible(false);
			}
			spAsientos.setValue(oServicio.getNumeroAsientosPiso1());
			spFilas.setValue(oServicio.getNumeroFilasPiso1());
			spColumnas.setValue(oServicio.getNumeroColumnasPiso1());
		}catch(Exception ex){
			DlgMessage.error(this.getClass().getSimpleName()+" "+ex.getMessage());
		}
	}

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.window.ui.IOpcionesMantenimiento#onCancel(int)
	 */
	@Override
	public void onCancel(int action) {
		switch (action) {
			case ACTION_NEW:
				break;
	
			case ACTION_MODIFY:
				this.mantenimientoRegistro(new Long(textboxId.getText()));
				break;
		}
	}

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.window.ui.IOpcionesMantenimiento#onSave(int)
	 */
	@Override
	public void onSave(int action) throws Exception {
		try{
			if (txtDenominacion.getText().trim().equals(""))
				throw new DenominacionNullException();
			else if (txtNombreCorto.getText().trim().equals(""))
				throw new NombreCortoNullException();
			else if (spAsientos.getValue().intValue()<=0)
				throw new NumeroAsientoNullException(1);
			else if (spFilas.getValue().intValue()==0)
				throw new NumeroFilasNullException(1);
			else if (spColumnas.getValue().intValue()==0)
				throw new NumeroColumnasNullException(1);
			else if(chkBusDosPisos.isChecked()){
				if (spAsientos2.getValue().intValue()<=0)
					throw new NumeroAsientoNullException(2);
				else if (spFilas2.getValue().intValue()==0)
					throw new NumeroFilasNullException(2);
				else if (spColumnas2.getValue().intValue()==0)
					throw new NumeroColumnasNullException(2);
			}
			
			if(action==ACTION_NEW)
				oServicio = new Servicio();
			
			Integer id = (textboxId.getText().equals("") ? 0 : new Integer(textboxId.getText()));
			
			oServicio.setId(id);
			oServicio.setDenominacion(txtDenominacion.getText().toUpperCase());
			oServicio.setNombreCorto(txtNombreCorto.getText().toUpperCase());
			oServicio.setNumeroPisos(chkBusDosPisos.isChecked()?2:1);
			oServicio.setNumeroAsientosPiso1(spAsientos.getValue());
			oServicio.setNumeroFilasPiso1(spFilas.getValue());
			oServicio.setNumeroColumnasPiso1(spColumnas.getValue());
			oServicio.setNumeroAsientosPiso2(spAsientos2.getValue()==0?null:spAsientos2.getValue());
			oServicio.setNumeroFilasPiso2(spFilas2.getValue()==0?null:spFilas2.getValue());
			oServicio.setNumeroColumnasPiso2(spColumnas2.getValue()==0?null:spColumnas2.getValue());
			
			oServicio.setEstadoRegistro(Constantes.VALUE_ACTIVO);
			
			switch (action) {
				case ACTION_NEW:
					UtilData.auditarRegistro(oServicio, getUsuario(), Executions.getCurrent());
					ServiceLocator.getServicioManager().guardar(oServicio);
					textboxId.setText(oServicio.getId().toString());
					break;

				case ACTION_MODIFY:
					UtilData.auditarRegistro(oServicio, true, getUsuario(), Executions.getCurrent());
					ServiceLocator.getServicioManager().actualizar(oServicio);
					break;
			}
			/*RECUPERA EL REGISTRO ACTUALIZADO O EL  NUEVO*/
			criteriosBusqueda.remove("denominacion");criteriosBusqueda.remove("codigo");criteriosBusqueda.remove("nombreCorto");
			criteriosBusqueda.put("denominacion", "%");
			criteriosBusqueda.put("estadoRegistro", Constantes.VALUE_ACTIVO);
			this.listarRegistros(ServiceLocator.getServicioManager().buscarPorX(criteriosBusqueda, criteriosOrdenar));
						
		}catch (DenominacionNullException dnex){
			DlgMessage.information(Messages.getString("Generales.information.noIngresoDenominacion"));
			throw new CancelaGrabacionException();
		}catch (NombreCortoNullException ncnex){
			DlgMessage.information(Messages.getString("Generales.information.noIngresoNombreCorto"),txtNombreCorto);
			throw new CancelaGrabacionException();
			
		}catch (NumeroAsientoNullException nanex){
			if(nanex.getNumeroPiso().intValue()==1)
				DlgMessage.information(Messages.getString("WndServicio.information.noIngresoNumeroAsientos")+" del Primer Piso");
			else
				DlgMessage.information(Messages.getString("WndServicio.information.noIngresoNumeroAsientos")+" del Segundo Piso",spAsientos);
			throw new CancelaGrabacionException();
		}catch (NumeroFilasNullException nfnex){
			if(nfnex.getNumeroPiso().intValue()==1)
				DlgMessage.information(Messages.getString("WndServicio.information.noIngresoNumeroFilas")+" del Primer Piso");
			else
				DlgMessage.information(Messages.getString("WndServicio.information.noIngresoNumeroFilas")+" del Segundo Piso",spFilas);
			throw new CancelaGrabacionException();
		}catch (NumeroColumnasNullException ncnex){
			if(ncnex.getNumeroPiso().intValue()==1)
				DlgMessage.information(Messages.getString("WndServicio.information.noIngresoCantidadColumnas")+" del Primer Piso");
			else
				DlgMessage.information(Messages.getString("WndServicio.information.noIngresoCantidadColumnas")+" del Segundo Piso", spColumnas);
			throw new CancelaGrabacionException();
			
		}catch (DenominacionDuplicadaException rsdex){
			DlgMessage.information(Messages.getString("Generales.information.denominacionDuplicada"),txtDenominacion);
			throw new CancelaGrabacionException();
		}catch(NombreCortoDuplicadoException rdnex){
			DlgMessage.information(Messages.getString("Generales.information.nombreCortoDuplicado"),txtNombreCorto);
			throw new CancelaGrabacionException();
		}catch(Exception ex){
			DlgMessage.error(this.getClass().getName()+" "+ex.getMessage());
			ex.printStackTrace();
			throw new CancelaGrabacionException();
		}
	}

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.window.ui.IOpcionesMantenimiento#onDelete(int)
	 */
	@Override
	public void onDelete(int tab) {
		// TODO Auto-generated method stub
		

	}

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.window.ui.IOpcionesMantenimiento#onPrint(int)
	 */
	@Override
	public void onPrint(int tab) {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.window.ui.IOpcionesMantenimiento#onExport(int)
	 */
	@Override
	public void onExport(int tab) {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.window.ui.IOpcionesMantenimiento#onHelp()
	 */
	@Override
	public void onHelp() {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.window.ui.IOpcionesMantenimiento#onChangeTab(int)
	 */
	@Override
	public void onChangeTab(int tab) {
		switch (tab) {
		case TAB_LIST:
			break;

		case TAB_MAINTENANCE:
			if (listboxLista.getSelectedIndex() > -1) {
				this.mantenimientoRegistro(new Long((String) listboxLista.getSelectedItem().getValue()));
			}
			break;
		}
	}

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.window.ui.IOpcionesMantenimiento#onClose()
	 */
	@Override
	public void onClose() {
		closeTabWindow();
	}

	public void habilitarSegundoPiso(boolean habilitar) {
		grpSegundoPiso.setVisible(habilitar);
		spAsientos2.setDisabled(!habilitar);
		spFilas2.setDisabled(!habilitar);
		spColumnas2.setDisabled(!habilitar);
		spAsientos2.setValue(0);
		spFilas2.setValue(0);
		spColumnas2.setValue(0);
	}
	
	@SuppressWarnings("deprecation")
	private void generaEstructura(Integer idServicio){
		try{
//			Servicio servicio = ServiceLocator.getServicioManager().buscarPorId(Long.valueOf(idServicio));
			deshabilitarToolbar(true);
			servicio = ServiceLocator.getServicioManager().buscarPorId(Long.valueOf(idServicio));
			int nPisos = servicio.getNumeroPisos();
			int nAsientos = servicio.getNumeroAsientosPiso1() + servicio.getNumeroAsientosPiso2();
			int nFilas = servicio.getNumeroFilasPiso1();
			int nColumnas = servicio.getNumeroColumnasPiso1();
			prefijoAsiento = "imgAsientoPiso1_";
			Integer numeroAsiento = 0;
			inicializarEstructura();
			if(generarElementos)
				generarImagen();
			
			Image imagen = generarImagen(IMAGE_PRIMER_PISO, 154, 43);
			numeroAsiento = 0;
			for(int i=0; i<nPisos; i++){
				String idGrid = "grdPiso1";
				if(i==1){
//					nAsientos = servicio.getNumeroAsientosPiso2();
					nFilas = servicio.getNumeroFilasPiso2();
					nColumnas = servicio.getNumeroColumnasPiso2();
					prefijoAsiento = "imgAsientoPiso2_";
					imagen = generarImagen(IMAGE_SEGUNDO_PISO, 154, 34);
					divAsientos.appendChild(new Separator("horizontal"));
					idGrid = "grdPiso2";
				}
				Grid gridPiso = new Grid();
				gridPiso.setId(idGrid);
				gridPiso.setStyle("border:none;background:white");
				gridPiso.setWidth("154px");
				Rows rows = new Rows();
				Row row = new Row();
				row.setSpans(String.valueOf(nColumnas));
				row.appendChild(imagen);
				row.setStyle("background:white; padding:0px");
				rows.appendChild(row);
//				Se comento para probar si los asientos se crean continuadamente
//				numeroAsiento = 0;
				for(int j=0; j<nFilas; j++){
					row = new Row();
					for(int k=0; k<nColumnas; k++){
						Div oDiv = new Div();
						oDiv.setWidth("28px");
						oDiv.setHeight("28px");
						oDiv.setDroppable("true");
						oDiv.setAttribute("piso", i);
						oDiv.setAttribute("fila", j);
						oDiv.setAttribute("columna", k);
						oDiv.setStyle("padding:none");
						
						if(numeroAsiento < nAsientos){
							Asiento asiento = new Asiento();
							HashMap<String, String> propiedades = new HashMap<String, String>();
							numeroAsiento++;
							contadorIdImagen++;
							propiedades.put("ocupante", "pasajero");
							asiento.setId(prefijoAsiento + numeroAsiento);
							asiento.setOcupante(Asiento.OCUPANTE_PASAJERO);
							asiento.setEstadoAsiento(Constantes.ASIENTO_DISPONIBLE);
							asiento.setNumeroAsiento(numeroAsiento);
							asiento.generarImagenes();
							asiento.setPropiedades(propiedades);
							asiento.setDraggable("true");
							divAsientos.appendChild((Asiento) asiento.clone());
						}
						
						oDiv.addEventListener(Events.ON_DROP, new EventListener<Event>() {
							public void onEvent(Event e){
								DropEvent drpEvent = (DropEvent)e;
								Component divContenedor = drpEvent.getTarget();
								Component elementoMovido = drpEvent.getDragged();
								if(divContenedor.getChildren().size()!=0){
									DlgMessage.information(Messages.getString("WndCreacionMapaBus.information.draggedAsiento"));
									return;
								}
								
								/*	Para clonar la imagen en caso que no sea un asiento	*/
								if(elementoMovido.getId().indexOf(prefijoElementos) > -1){
									contadorIdImagen ++;
									elementoMovido = (Component) elementoMovido.clone();
									elementoMovido.setId(sufijoAsiento + contadorIdImagen);
								}
								
								if(elementoMovido instanceof Asiento){
									Asiento oAsientoBus = (Asiento) elementoMovido;
									oAsientoBus.setFila((Integer) divContenedor.getAttribute("fila"));
									oAsientoBus.setColumna((Integer) divContenedor.getAttribute("columna"));
									oAsientoBus.setPiso((Integer) divContenedor.getAttribute("piso"));
									divContenedor.appendChild(oAsientoBus);
								}else if(elementoMovido instanceof Monitor){
									Monitor oMonitor = (Monitor) elementoMovido;
									oMonitor.setFila((Integer) divContenedor.getAttribute("fila"));
									oMonitor.setColumna((Integer) divContenedor.getAttribute("columna"));
									oMonitor.setPiso((Integer) divContenedor.getAttribute("piso"));
									divContenedor.appendChild(oMonitor);
								}else if(elementoMovido instanceof Cafeteria){
									Cafeteria oCafeteria = (Cafeteria) elementoMovido;
									oCafeteria.setFila((Integer) divContenedor.getAttribute("fila"));
									oCafeteria.setColumna((Integer) divContenedor.getAttribute("columna"));
									oCafeteria.setPiso((Integer) divContenedor.getAttribute("piso"));
									divContenedor.appendChild(oCafeteria);
								}else if(elementoMovido instanceof ServiciosHigienicos){
									ServiciosHigienicos oSshh = (ServiciosHigienicos) elementoMovido;
									oSshh.setFila((Integer) divContenedor.getAttribute("fila"));
									oSshh.setColumna((Integer) divContenedor.getAttribute("columna"));
									oSshh.setPiso((Integer) divContenedor.getAttribute("piso"));
									divContenedor.appendChild(oSshh);
								}
							}
						});
						row.appendChild(oDiv);
						row.setStyle("padding-top:2px; padding-left:3px; padding-right:0px; border:normal !important; background:#99D9EA");// background:#eeeeee");
					}
					rows.appendChild(row);
				}
				gridPiso.appendChild(rows);
				vbxEstructuraBus.appendChild(gridPiso);
				listaGridPisos.add(gridPiso);
			}
			
			tabEstructura.setVisible(true);
			((Tabbox)tbMantenimiento.getParent().getParent().getParent().getParent()).setSelectedIndex(1);
			tabEstructura.setSelected(true);
			btnGuardarEstructura.setDisabled(false);
			btnCancelar.setDisabled(false);
		}catch(Exception ex){
			ex.printStackTrace();
			DlgMessage.error(this.getClass().getSimpleName()+" "+ex.getMessage());
		}
	}
	
	/**
	 * Inicializa(limpia los objetos existentes) el contenedor de los asientos.
	 */
	private void inicializarEstructura(){
		for(int i=vbxEstructuraBus.getChildren().size()-1; i>-1; i--){
			Component component = (Component)vbxEstructuraBus.getChildren().get(i);
			vbxEstructuraBus.removeChild(component);
		}
		
		for(int i=divAsientos.getChildren().size()-1; i>-1; i--){
			Component component = (Component)divAsientos.getChildren().get(i);
			divAsientos.removeChild(component);
		}
		
		listaGridPisos.clear();
//		oAsientoPlantilla.setOcupante(new Integer((String) cboAsiento.getSelectedItem().getValue()));
	}
	
	/**
	 * Genera el objeto imagen para los pisos del bus
	 * @param src		: Path de la imagen a mostrar.
	 * @param width		: Ancho de la imagen.
	 * @param height	: Alto de la imagen.
	 * @return Image.
	 */
	private Image generarImagen(String src, int width, int height){
		Image imagen = new Image();
		imagen.setSrc(src);
		imagen.setWidth(String.valueOf(width)+"px");
		imagen.setHeight(String.valueOf(height)+"px");
		return imagen;
	}
	
	/**
	 * Genera las imagenes para los monitores, cafeteria y ss.hh.
	 */
	private void generarImagen(){
		prefijoElementos = "elemento_";
		contadorIdImagen++;
		Monitor monitor = new Monitor(IMAGE_TELEVISOR);
		monitor.setId(prefijoElementos+contadorIdImagen);
		monitor.setDraggable("true");
		divTv.appendChild(monitor);
		
		contadorIdImagen++;
		Cafeteria cafeteria = new Cafeteria(IMAGE_CAFETERIA);
		cafeteria.setId(prefijoElementos+contadorIdImagen);
		cafeteria.setDraggable("true");
		divCafeteria.appendChild(cafeteria);
		
		contadorIdImagen++;
		ServiciosHigienicos sshh = new ServiciosHigienicos(IMAGE_SSHH);
		sshh.setId(prefijoElementos+contadorIdImagen);
		sshh.setDraggable("true");
		divSSHH.appendChild(sshh);
		
		contadorIdImagen++;
		Papelera papelera = new Papelera(IMAGE_PAPELERA);
		papelera.addEventListener(Events.ON_DROP, new EventListener<Event>() {
			public void onEvent(Event e){
				DropEvent dropEvent = (DropEvent)e;
				Component elementoMovido = dropEvent.getDragged();
				if(!(elementoMovido instanceof Asiento))
					elementoMovido.getParent().removeChild(elementoMovido);
				else {//if(elementoMovido.getId().indexOf(prefijoAsiento) < 0){
					elementoMovido.getParent().removeChild(elementoMovido);
					divAsientos.appendChild(elementoMovido);
				}
			}
		});
		papelera.setId(prefijoElementos+contadorIdImagen);
		papelera.setDraggable("false");
		papelera.setDroppable("true");
		divPapelera.appendChild(papelera);
		generarElementos = false;
	}
	
	@SuppressWarnings("deprecation")
	private void modificaEstructura(Integer idServicio){
		try{
			deshabilitarToolbar(true);
//			Servicio servicio = null;
			List<MapaBus> lstMapaBus = ServiceLocator.getMapaBusManager().buscarMapaBus(idServicio, Constantes.VALUE_ACTIVO);
			
			Map<Coordenada, MapaBus> mapCoordenadas = new HashMap<Coordenada, MapaBus>();
			for(MapaBus mapaBus : lstMapaBus){
				Coordenada coordenada = new Coordenada(mapaBus.getNumeroFila(), mapaBus.getNumeroColumna(), mapaBus.getNumeroPiso());
				mapCoordenadas.put(coordenada, mapaBus);
			}
			
			if(lstMapaBus.size()>0)
				servicio = lstMapaBus.get(0).getServicio();
			
			int nPisos = servicio.getNumeroPisos();
//			int nAsientos = servicio.getNumeroAsientosPiso1();
			int nFilas = servicio.getNumeroFilasPiso1();
			int nColumnas = servicio.getNumeroColumnasPiso1();
			prefijoAsiento = "imgAsientoPiso1_";
			Integer numeroAsiento = 0;
			
			inicializarEstructura();
			/*	Generamos las imagenes de los monitores, cafeteria, SSHH	*/
			if(generarElementos)
				generarImagen();
			
			Image imagen = generarImagen(IMAGE_PRIMER_PISO, 154, 43);
			
			for(int i=0; i<nPisos; i++){
				String idGrid = "grdPiso1";
				if(i==1){
//					nAsientos = servicio.getNumeroAsientosPiso2();
					nFilas = servicio.getNumeroFilasPiso2();
					nColumnas = servicio.getNumeroColumnasPiso2();
					prefijoAsiento = "imgAsientoPiso2_";
					imagen = generarImagen(IMAGE_SEGUNDO_PISO, 154, 34);
					divAsientos.appendChild(new Separator("horizontal"));
					idGrid = "grdPiso2";
				}
				Grid gridPiso = new Grid();
				gridPiso.setId(idGrid);
				gridPiso.setStyle("border:none;background:white");
				gridPiso.setWidth("154px");
				Rows rows = new Rows();
				Row row = new Row();
				row.setSpans(String.valueOf(nColumnas));
				row.appendChild(imagen);
				row.setStyle("background:white; padding:0px");
				rows.appendChild(row);
				numeroAsiento = 0;
				for(int j=0; j<nFilas; j++){
					row = new Row();
					for(int k=0; k<nColumnas; k++){
						Div oDiv = new Div();
						oDiv.setWidth("28px");
						oDiv.setHeight("28px");
						oDiv.setDroppable("true");
						oDiv.setAttribute("piso", i);
						oDiv.setAttribute("fila", j);
						oDiv.setAttribute("columna", k);
						oDiv.setStyle("padding:none");
						
						String coordenadaActual = j+"-"+k+"-"+i;
						
						for(Coordenada coordenada : mapCoordenadas.keySet()){
							if(coordenada.toString().equals(coordenadaActual)){
								MapaBus objetoBus = mapCoordenadas.get(coordenada);
								
								if(objetoBus.getTipoObjeto().intValue()==TIPO_ASIENTO){
									Asiento asiento = new Asiento();
									HashMap<String, String> propiedades = new HashMap<String, String>();
									numeroAsiento++;
									contadorIdImagen++;
									propiedades.put("ocupante", "pasajero");
									asiento.setId(prefijoAsiento + numeroAsiento);
									asiento.setOcupante(Asiento.OCUPANTE_PASAJERO);
									asiento.setEstadoAsiento(Constantes.ASIENTO_DISPONIBLE);
									asiento.setFila(j);
									asiento.setColumna(k);
									asiento.setPiso(i);
									asiento.setNumeroAsiento(objetoBus.getNumeroAsiento());
									asiento.setSrc(objetoBus.getPathImagen());
									asiento.setPropiedades(propiedades);
									asiento.setDraggable("true");
									oDiv.appendChild(asiento);
								}else if(objetoBus.getTipoObjeto().intValue()==TIPO_MONITOR){
									Monitor monitor = new Monitor();
									HashMap<String, String> propiedades = new HashMap<String, String>();
									numeroAsiento++;
									contadorIdImagen++;
									monitor.setId(prefijoAsiento + numeroAsiento);
									monitor.setFila(j);
									monitor.setColumna(k);
									monitor.setPiso(i);
									monitor.setSrc(objetoBus.getPathImagen());
									monitor.setPropiedades(propiedades);
									monitor.setDraggable("true");
									oDiv.appendChild(monitor);
								}else if(objetoBus.getTipoObjeto().intValue()==TIPO_CAFETERIA){
									Cafeteria cafeteria = new Cafeteria();
									HashMap<String, String> propiedades = new HashMap<String, String>();
									numeroAsiento++;
									contadorIdImagen++;
									cafeteria.setId(prefijoAsiento + numeroAsiento);
									cafeteria.setFila(j);
									cafeteria.setColumna(k);
									cafeteria.setPiso(i);
									cafeteria.setSrc(objetoBus.getPathImagen());
									cafeteria.setPropiedades(propiedades);
									cafeteria.setDraggable("true");
									oDiv.appendChild(cafeteria);
								}else{
									ServiciosHigienicos sshh = new ServiciosHigienicos();
									HashMap<String, String> propiedades = new HashMap<String, String>();
									numeroAsiento++;
									contadorIdImagen++;
									sshh.setId(prefijoAsiento + numeroAsiento);
									sshh.setFila(j);
									sshh.setColumna(k);
									sshh.setPiso(i);
									sshh.setSrc(objetoBus.getPathImagen());
									sshh.setPropiedades(propiedades);
									sshh.setDraggable("true");
									oDiv.appendChild(sshh);
								}
								break;
							}
						}
						
						oDiv.addEventListener(Events.ON_DROP, new EventListener<Event>() {
							public void onEvent(Event e){
								DropEvent drpEvent = (DropEvent)e;
								Component divContenedor = drpEvent.getTarget();
								Component elementoMovido = drpEvent.getDragged();
								if(divContenedor.getChildren().size()!=0){
									DlgMessage.information(Messages.getString("WndCreacionMapaBus.information.draggedAsiento"));
									return;
								}
								
								/*	Para clonar la imagen en caso que no sea un asiento	*/
								if(elementoMovido.getId().indexOf(prefijoElementos) > -1){
									contadorIdImagen ++;
									elementoMovido = (Component) elementoMovido.clone();
									elementoMovido.setId(sufijoAsiento + contadorIdImagen);
								}
								
								if(elementoMovido instanceof Asiento){
									Asiento oAsientoBus = (Asiento) elementoMovido;
									oAsientoBus.setFila((Integer) divContenedor.getAttribute("fila"));
									oAsientoBus.setColumna((Integer) divContenedor.getAttribute("columna"));
									oAsientoBus.setPiso((Integer) divContenedor.getAttribute("piso"));
									divContenedor.appendChild(oAsientoBus);
								}else if(elementoMovido instanceof Monitor){
									Monitor oMonitor = (Monitor) elementoMovido;
									oMonitor.setFila((Integer) divContenedor.getAttribute("fila"));
									oMonitor.setColumna((Integer) divContenedor.getAttribute("columna"));
									oMonitor.setPiso((Integer) divContenedor.getAttribute("piso"));
									divContenedor.appendChild(oMonitor);
								}else if(elementoMovido instanceof Cafeteria){
									Cafeteria oCafeteria = (Cafeteria) elementoMovido;
									oCafeteria.setFila((Integer) divContenedor.getAttribute("fila"));
									oCafeteria.setColumna((Integer) divContenedor.getAttribute("columna"));
									oCafeteria.setPiso((Integer) divContenedor.getAttribute("piso"));
									divContenedor.appendChild(oCafeteria);
								}else if(elementoMovido instanceof ServiciosHigienicos){
									ServiciosHigienicos oSshh = (ServiciosHigienicos) elementoMovido;
									oSshh.setFila((Integer) divContenedor.getAttribute("fila"));
									oSshh.setColumna((Integer) divContenedor.getAttribute("columna"));
									oSshh.setPiso((Integer) divContenedor.getAttribute("piso"));
									divContenedor.appendChild(oSshh);
								}
							}
						});
						row.appendChild(oDiv);
						row.setStyle("padding-top:2px; padding-left:3px; padding-right:0px; border:normal !important; background:#99D9EA");// background:#eeeeee");
					}
					rows.appendChild(row);
				}
				gridPiso.appendChild(rows);
				vbxEstructuraBus.appendChild(gridPiso);
				listaGridPisos.add(gridPiso);
			}
			tabEstructura.setVisible(true);
			((Tabbox)tbMantenimiento.getParent().getParent().getParent().getParent()).setSelectedIndex(1);
			tabEstructura.setSelected(true);
			btnGuardarEstructura.setDisabled(false);
			btnCancelar.setDisabled(false);
		}catch(Exception ex){
			ex.printStackTrace();
		}
	}
	
	/**
	 * Permite la grabación del Mapa del Bus.
	 */
	public void grabarEstructura(){
		try{
			lstElementosBus = new ArrayList<Object>();
			
			if(divAsientos.getChildren().size()==1 && !(divAsientos.getChildren().get(0) instanceof Separator)){
				DlgMessage.information(Messages.getString("WndCreacionMapaBus.information.validateAsientos"));
				return;
			}else if(divAsientos.getChildren().size()>1){
				DlgMessage.information(Messages.getString("WndCreacionMapaBus.information.validateAsientos"));
				return;
			}else {
				for(int g = 0; g < listaGridPisos.size(); g ++){
					Grid oGrid = listaGridPisos.get(g);
					/*	Recorremos las filas de la grilla	*/
					for(int rs = 1; rs < oGrid.getRows().getChildren().size(); rs ++){
						Row oRow = (Row) oGrid.getRows().getChildren().get(rs);
						/*	Recorremos las celdas de la fila	*/
						for(int r = 0; r < oRow.getChildren().size(); r ++){
							Div oDiv = (Div) oRow.getChildren().get(r);
							if(oDiv.getChildren().size()>0)
								lstElementosBus.add(oDiv.getChildren().get(0));
						}
					}
				}				
			}
			
			if(lstElementosBus.size()>0){
				List<MapaBus> lstMapaBus = new ArrayList<MapaBus>();
				for(int i= 0; i<lstElementosBus.size(); i++){
					MapaBus mapaBus = new MapaBus();
					Object obj = lstElementosBus.get(i);
					ElementoBus elementoBus = (ElementoBus)obj;
					mapaBus.setPathImagen(elementoBus.getSrc());
					mapaBus.setNumeroFila(elementoBus.getFila());
					mapaBus.setNumeroColumna(elementoBus.getColumna());
					mapaBus.setNumeroPiso(elementoBus.getPiso());
					mapaBus.setServicio(servicio);
					
					if(obj instanceof Asiento){
						Asiento asiento = (Asiento)obj;
						mapaBus.setTipoObjeto(TIPO_ASIENTO);
						mapaBus.setNumeroAsiento(asiento.getNumeroAsiento());
					}else if(obj instanceof Monitor)
						mapaBus.setTipoObjeto(TIPO_MONITOR);
					else if(obj instanceof Cafeteria)
						mapaBus.setTipoObjeto(TIPO_CAFETERIA);
					else
						mapaBus.setTipoObjeto(TIPO_SSHH);
						
					mapaBus.setEstadoRegistro(Constantes.VALUE_ACTIVO);
					UtilData.auditarRegistro(mapaBus,getUsuario(), Executions.getCurrent());
					lstMapaBus.add(mapaBus);
				}
				
				int result = ServiceLocator.getMapaBusManager().guardarMapaBus(lstMapaBus);
				if(result==Constantes.CORRECT){
					btnGuardarEstructura.setDisabled(true);
					btnCancelar.setDisabled(true);
					DlgMessage.information(Messages.getString("WndCreacionMapaBus.information.exitoGuardarMapa"));
					inicializarEstructura();
					/*	Regresando el foco al tab del Servicio	*/
					((Tabbox)tabEstructura.getParent().getParent()).setSelectedIndex(0);
					/*	Regresando el foco al tab de lista	*/
					((Tabbox)tabEstructura.getParent().getParent().getParent().getParent().getParent().getParent()).setSelectedIndex(0);
					habilitarToolbar(false);
				}
			}else{
				DlgMessage.information(Messages.getString("WndCreacionMapaBus.information.noDatosGuardar"));
			}
		}catch(MapaBusNotUpdateException mbnuex){
			DlgMessage.information(Messages.getString("WndCreacionMapaBus.information.noUpdateMapaBus"));
		}catch(Exception ex){
			DlgMessage.error(this.getClass().getSimpleName()+" "+ex.getMessage());
		}
	}
	
	private void deshabilitarToolbar(boolean arg){
		btnNuevo.setDisabled(arg);
		btnBuscar.setDisabled(arg);
		btnRefrescar.setDisabled(arg);
		btnModificar.setDisabled(arg);
		btnCancelar.setDisabled(arg);
		btnGuardar.setDisabled(arg);
		btnEliminar.setDisabled(arg);
		btnImprimir.setDisabled(arg);
		btnExportar.setDisabled(arg);
	}
	
	private void habilitarToolbar(boolean arg){
		btnNuevo.setDisabled(arg);
		btnBuscar.setDisabled(arg);		
	}
	
	public void cancelarCreacion(){
		/*	Regresando el foco al tab del Servicio	*/
		((Tabbox)tabEstructura.getParent().getParent()).setSelectedIndex(0);
		/*	Regresando el foco al tab de lista	*/
		((Tabbox)tabEstructura.getParent().getParent().getParent().getParent().getParent().getParent()).setSelectedIndex(0);
		habilitarToolbar(false);
	}
	
//	private boolean validar(){
//		lstElementosBus = new ArrayList<Object>();
//		boolean validado = true;
//		
//		if(divAsientos.getChildren().size()==1 && !(divAsientos.getChildren().get(0) instanceof Separator)){
//			DlgMessage.information(Messages.getString("WndMapaBus.information.validateAsientos"));
//			validado = false;
//		}else if(divAsientos.getChildren().size()>1){
//			DlgMessage.information(Messages.getString("WndMapaBus.information.validateAsientos"));
//			validado = false;
//		}else {
//			for(int g = 0; g < listaGridPisos.size(); g ++){
//				Grid oGrid = listaGridPisos.get(g);
//				/*	Recorremos las filas de la grilla	*/
//				for(int rs = 1; rs < oGrid.getRows().getChildren().size(); rs ++){
//					Row oRow = (Row) oGrid.getRows().getChildren().get(rs);
//					/*	Recorremos las celdas de la fila	*/
//					for(int r = 0; r < oRow.getChildren().size(); r ++){
//						Div oDiv = (Div) oRow.getChildren().get(r);
//						lstElementosBus.add(oDiv.getChildren().get(0));
//					}
//				}
//			}
//			validado = true;
//		}
//		return validado;
//	}
	

//	private void limpiarEstructuraBus() {
//		List<Component> lstElementoEstructura = vbxEstructuraBus.getChildren();
//
//		for (int e = (lstElementoEstructura.size() - 1); e > -1; e --) {
//			Component oComponent = lstElementoEstructura.get(e);
//			vbxEstructuraBus.removeChild(oComponent);
//		}
//	}
//
//	private void generarEstructuraBus() {
//		
//	}

	
}