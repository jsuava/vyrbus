package com.cystesoft.vyrbus.view.ctrl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zul.Button;
import org.zkoss.zul.Caption;
import org.zkoss.zul.Column;
import org.zkoss.zul.Columns;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Comboitem;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.Div;
import org.zkoss.zul.Doublebox;
import org.zkoss.zul.Grid;
import org.zkoss.zul.Label;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Radio;
import org.zkoss.zul.Row;
import org.zkoss.zul.Rows;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

import com.cystesoft.vyrbus.model.bean.Agencia;
import com.cystesoft.vyrbus.model.bean.Bus;
import com.cystesoft.vyrbus.model.bean.DetalleLiquidacion;
import com.cystesoft.vyrbus.model.bean.Gasto;
import com.cystesoft.vyrbus.model.bean.Liquidacion;
import com.cystesoft.vyrbus.model.bean.TipoAgencia;
import com.cystesoft.vyrbus.model.bean.TipoGasto;
import com.cystesoft.vyrbus.model.bean.Usuario;
import com.cystesoft.vyrbus.service.exceptions.BusNullException;
import com.cystesoft.vyrbus.service.exceptions.CancelaGrabacionException;
import com.cystesoft.vyrbus.service.exceptions.GastosException;
import com.cystesoft.vyrbus.service.exceptions.LiquidacionNullException;
import com.cystesoft.vyrbus.service.exceptions.NumeroDocumentoNullException;
import com.cystesoft.vyrbus.service.exceptions.PilotoNullException;
import com.cystesoft.vyrbus.service.locator.ServiceLocator;
import com.cystesoft.vyrbus.service.util.Constantes;
import com.cystesoft.vyrbus.service.util.Messages;
import com.cystesoft.vyrbus.service.util.MyTime;
import com.cystesoft.vyrbus.service.util.Util;
import com.cystesoft.vyrbus.service.util.UtilData;
import com.cystesoft.vyrbus.view.ui.DlgMessage;
import com.cystesoft.vyrbus.view.ui.WndOpcionesMantenimiento;

public class WndGasto extends WndOpcionesMantenimiento {
	private static final long serialVersionUID = 6187038948722081353L;

	private Combobox cmbTipoGasto;
	private Textbox txtNroDocumento;
	private Doublebox dbMonto;
	private Textbox txtNombrePiloto;
	private Textbox txtConsignado;
	private Textbox txtObservacion;
//	private Textbox txtBus;
	private Combobox cmbBus;
	private Datebox dbFecha;
	private Radio rbGasto;
	private Radio rbIngreso;
	
	
//	private Agencia agencia = null;
	private Gasto gasto=null;
	private DetalleLiquidacion detalleLiquidacion = null;
	
	private List<String> criteriosOrdenar = null;
	private Window wndBusqueda = null;
	
	Datebox dtbxFecha =new Datebox(new Date());
	Combobox cmbTipoAgencia=new Combobox();
	final Combobox cmbAgencia=new Combobox();
	final Combobox cmbTGasto=new Combobox();
	final Combobox cmbUsuario=new Combobox();
	
	Boolean isClikSaved=false;
	
	/*
	 * (non-Javadoc)
	 * @see com.tepsa.sisvyr.view.ui.WndBase#onCreate()
	 */
	@Override
	public void onCreate() throws Exception {
		UtilData.cargarDataCombo(cmbBus, Bus.class, false);
		
		criteriosOrdenar = new ArrayList<String>();
		criteriosOrdenar.add("tipoGasto");
		
		dbMonto.setLocale(Locale.US);
		UtilData.cargarTipoGasto(cmbTipoGasto, false, Constantes.FALSE_VALUE);
		rbGasto.setChecked(true);
	}

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.window.ui.IBase#initComponents()
	 */
	@Override
	public void initComponents() {
		cmbTipoGasto = (Combobox) this.getFellow("cmbTipoGasto");
		txtNroDocumento = (Textbox) this.getFellow("txtNroDocumento");
		dbMonto = (Doublebox) this.getFellow("dbMonto");
		txtNombrePiloto = (Textbox) this.getFellow("txtNombrePiloto");
		txtConsignado = (Textbox) this.getFellow("txtConsignado");
		txtObservacion = (Textbox) this.getFellow("txtObservacion");
		dbFecha = (Datebox) this.getFellow("dbFecha");
		cmbBus=(Combobox)this.getFellow("cmbBus");
		rbGasto = (Radio)this.getFellow("rbGasto");
		rbIngreso = (Radio)this.getFellow("rbIngreso");
		
		rbGasto.addEventListener(Events.ON_CHECK, new EventListener<Event>() {
			@Override
			public void onEvent(Event event) throws Exception {
				// TODO Auto-generated method stub
				onCheck_tipoOperacion();
			}
		});
		rbIngreso.addEventListener(Events.ON_CHECK, new EventListener<Event>() {
			@Override
			public void onEvent(Event event) throws Exception {
				// TODO Auto-generated method stub
				onCheck_tipoOperacion();
			}
		});
	}
	
	
	private void onCheck_tipoOperacion() {
		try {
			Util.limpiarCombobox(cmbTipoGasto);
			if(rbGasto.isChecked())
				UtilData.cargarTipoGasto(cmbTipoGasto, false, Constantes.FALSE_VALUE);
			else
				UtilData.cargarTipoGasto(cmbTipoGasto, false, Constantes.TRUE_VALUE);
			
			if(cmbTipoGasto.getItemCount()==2)
				cmbTipoGasto.setSelectedIndex(1);
			
			onSelectTipoGasto();
			
		} catch (Exception ex) {
			DlgMessage.error(this.getClass().getName()+" "+ex.getMessage());
			ex.printStackTrace();
		}
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.tepsa.sisvyr.view.ui.IOpcionesMantenimiento#onNew()
	 */
	@Override
	public void onNew() throws Exception {
		/*	Busca una liquidacion aperturada para la fecha actual	*/
		Liquidacion liquidacion = ServiceLocator.getLiquidacionManager().buscarUltimaLiquidacion(getAgencia().getId(), getUsuario().getId(), Constantes.LIQUI_ESTA_ABIERTO);
		try{
			if(liquidacion==null)
				throw new LiquidacionNullException();
			dbFecha.setValue(liquidacion.getFechaLiquidacion());
		}catch (LiquidacionNullException lnex){
			DlgMessage.information(Messages.getString("WndGasto.Information.LiquidacionNull"));
			throw new CancelaGrabacionException();
		}catch(Exception ex){
			DlgMessage.error(this.getClass().getSimpleName()+" "+ex.getMessage());
		}
		
		MyTime time = new MyTime();
		dbFecha.setValue(Constantes.FORMAT_DATE.parse(time.dateServer()));
		cmbTipoGasto.setSelectedIndex(0);
		cmbBus.setSelectedIndex(0);
		rbGasto.setChecked(true);
		
		isClikSaved=false;
	}

	/*
	 * (non-Javadoc)
	 * @see com.tepsa.sisvyr.view.ui.IOpcionesMantenimiento#onSearch()
	 */
	@Override
	public void onSearch() throws Exception {
//		final WndFiltrarParametros oWndFiltrar = new WndFiltrarParametros();
//
//		oWndFiltrar.addParameter("1. Fecha", Datebox.class);
//		oWndFiltrar.addParameter("2. Agencia", Agencia.class);
//		oWndFiltrar.addParameter("3. Tipo de Gasto", TipoGasto.class);
//		oWndFiltrar.addParameter("4. Usuario", Usuario.class);
//		
//		this.appendChild(oWndFiltrar);
//		oWndFiltrar.setMode("modal");
//		oWndFiltrar.addEventListener(com.tepsa.sisvyr.view.ui.Events.ON_FILTER, new EventListener<Event>() {
//
//			@Override
//			public void onEvent(Event event) throws Exception {
//				Date fecha = (Date)oWndFiltrar.getParameterValue("1. Fecha");
//				Agencia agencia = (Agencia) oWndFiltrar.getParameterValue("2. Agencia");
//				TipoGasto tipoGasto = (TipoGasto) oWndFiltrar.getParameterValue("3. Tipo de Gasto");
//				Usuario usuario=(Usuario)oWndFiltrar.getParameterValue("4. Usuario");
//				
//				String fechaGasto=null;
//				Integer idTipoGasto=null;
//				Integer idAgencia=null;
//				Integer idUsuario=null; 
//				
//				if (fecha !=null)
//					fechaGasto=Constantes.FORMAT_DATE.format(fecha.getTime());
//				if (agencia instanceof Agencia)
//					idAgencia=agencia.getId();
//				if (tipoGasto instanceof TipoGasto)
//					idTipoGasto=tipoGasto.getId();
//				if(usuario!=null)
//					idUsuario=usuario.getId();
//					
//				listarRegistros((ArrayList<Gasto>) ServiceLocator.getGastoManager().buscarGasto(fechaGasto, idTipoGasto, idAgencia, idUsuario));
//			}
//		
//		});
		ventanaBusqueda();
		
	}

	/*
	 * (non-Javadoc)
	 * @see com.tepsa.sisvyr.view.ui.IOpcionesMantenimiento#onRefresh(int)
	 */
	@Override
	public void onRefresh(int tab) throws Exception {
		//listarRegistros(ServiceLocator.getGastoManager().buscarPorX(criteriosBusqueda, criteriosOrdenar));					
	}

	/*
	 * (non-Javadoc)
	 * @see com.tepsa.sisvyr.view.ui.IOpcionesMantenimiento#onModify(int)
	 */
	@Override
	public void onModify(int tab) throws Exception {
		mantenimientoGasto();
		
	}

	/*
	 * (non-Javadoc)
	 * @see com.tepsa.sisvyr.view.ui.IOpcionesMantenimiento#onCancel(int)
	 */
	@Override
	public void onCancel(int action) throws Exception {
		
		
	}
	
	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.window.IOpcionesMantenimiento#onClose()
	 */
	@Override
	public void onClose() {
		closeTabWindow();
	}

	/*
	 * (non-Javadoc)
	 * @see com.tepsa.sisvyr.view.ui.IOpcionesMantenimiento#onSave(int)
	 */
	@Override
	public void onSave(int action) throws Exception {
		try{
			if (!(cmbTipoGasto.getSelectedItem().getValue() instanceof TipoGasto))
				throw new GastosException(GastosException.TIPO_GASTO_NULL);
			else if (dbMonto.getValue()==null)
				throw new GastosException(GastosException.MONTO_NULL);
			else if (dbMonto.getValue() <=0)
				throw new GastosException(GastosException.MONTO_NULL);
					
			if(((TipoGasto)cmbTipoGasto.getSelectedItem().getValue()).getId().equals(Constantes.ID_TIPGAS_PEAJES)){
				if(!(cmbBus.getSelectedItem().getValue() instanceof Bus))
					throw new BusNullException();
				else if(txtNombrePiloto.getText().isEmpty())
					throw new PilotoNullException();
			}else if (((TipoGasto)cmbTipoGasto.getSelectedItem().getValue()).getId().equals(Constantes.ID_TIPGAS_PAGO_GIROS)){
				if(txtNroDocumento.getText().trim().isEmpty())
					throw new NumeroDocumentoNullException();
				else if(txtNroDocumento.getText().trim().length()<8)
					throw new GastosException(GastosException.DOCUMENTO_NO_VALIDO);
				else if(txtConsignado.getText().trim().isEmpty())
					throw new GastosException(GastosException.CONSIGNADO_NULL);
			}else if(txtObservacion.getText().trim().isEmpty())
				throw new GastosException(GastosException.OBSERVACIONES_NULL);
			
						
			/*	Busca una liquidacion aperturada para la fecha actual	*/
			Liquidacion liquidacion = buscarLiquidacion(Constantes.FORMAT_DATE.format(dbFecha.getValue()),getAgencia().getId(), getUsuario().getId());
			if(liquidacion==null || liquidacion.getestadoLiquidacion().equals(Constantes.LIQUI_ESTA_CERRADO))
				throw new LiquidacionNullException();
			
			Double totalVentasEfectivo=ServiceLocator.getVentaPasajesManager().buscaTotalVentasEfectivo(getUsuario().getId(), getAgencia().getId(), Constantes.FORMAT_DATE.format(dbFecha.getValue()));
			Double totalGastos=ServiceLocator.getGastoManager().BuscarTotalGastos(Constantes.FORMAT_DATE.format(dbFecha.getValue()), getUsuario().getId(), getAgencia().getId());
			
			if(action==ACTION_NEW){
				totalGastos+=+dbMonto.getValue();
			}else{	
				totalGastos+=+dbMonto.getValue()-gasto.getMonto();
			}
			
			if(isClikSaved)
				return;
			
			if(totalVentasEfectivo<totalGastos && rbGasto.isChecked())
				throw new GastosException(GastosException.MONTO_GASTO_MAYOR_VENTAS);
						
			if (action==ACTION_NEW)
				gasto = new Gasto();
			else
				gasto.setId(new Integer(textboxId.getText()));
			
			TipoGasto tipoGasto = new TipoGasto();
			tipoGasto.setId(((TipoGasto) cmbTipoGasto.getSelectedItem().getValue()).getId());
			tipoGasto.setDenominacion(((TipoGasto) cmbTipoGasto.getSelectedItem().getValue()).getDenominacion());
			
			gasto.setTipoGasto(tipoGasto);
			gasto.setNumeroDocumento(txtNroDocumento.getText().trim());
			gasto.setMonto(dbMonto.getValue());
			gasto.setNombrePiloto(txtNombrePiloto.getText().trim().toUpperCase());
			if(cmbBus.getSelectedItem().getValue() instanceof Bus)
				gasto.setCodigoBus(((Bus)cmbBus.getSelectedItem().getValue()).getCodigo());

			gasto.setConsignado(txtConsignado.getText().trim().toUpperCase());
			gasto.setObservacion(txtObservacion.getText().trim().toUpperCase());
			gasto.setEstadoRegistro(Constantes.VALUE_ACTIVO);
			
				
			/*Graba o actualiza el Gasto*/
			switch (action) {
				case ACTION_NEW:
					UtilData.auditarRegistro(gasto,getUsuario(), Executions.getCurrent());
					ServiceLocator.getGastoManager().guardar(gasto);
					break;
				case ACTION_MODIFY:
					UtilData.auditarRegistro(gasto, true, getUsuario(), Executions.getCurrent());
					ServiceLocator.getGastoManager().actualizar(gasto);
					break;
			}
										
			if (action==ACTION_NEW)
				detalleLiquidacion = new DetalleLiquidacion();
				
			detalleLiquidacion.setLiquidacion(liquidacion);
			detalleLiquidacion.setAnio(liquidacion.getAnio());
			detalleLiquidacion.setGasto(gasto);
			detalleLiquidacion.setTotal(gasto.getMonto());
			detalleLiquidacion.setCantidad(0);
			detalleLiquidacion.setEstadoRegistro(Constantes.VALUE_ACTIVO);	
				
			/*Graba o actualiza el detalle de la Liquidacion*/
			switch (action) {
				case ACTION_NEW:
					UtilData.auditarRegistro(detalleLiquidacion,getUsuario(), Executions.getCurrent());
					ServiceLocator.getDetalleLiquidacionManager().guarda(detalleLiquidacion);
					break;
				case ACTION_MODIFY:
					UtilData.auditarRegistro(detalleLiquidacion, true, getUsuario(), Executions.getCurrent());
					ServiceLocator.getDetalleLiquidacionManager().actualizar(detalleLiquidacion);
					break;
			}
				
			String fechaGasto=dbFecha.getText();
			Integer idTipoGasto=gasto.getTipoGasto().getId();
								
			listarRegistros((ArrayList<Gasto>) ServiceLocator.getGastoManager().buscarGasto(fechaGasto, idTipoGasto, null,getUsuario().getId()));
//			isClikSaved=true;
			isClikSaved=false;
		
		}catch (GastosException gex){	
			if(gex.getTipo()==GastosException.MONTO_NULL){
				DlgMessage.information(Messages.getString("WndGasto.Information.MontoGasotNull"));
				cmbTipoGasto.setFocus(true);throw new CancelaGrabacionException();
			}else if (gex.getTipo()==GastosException.TIPO_GASTO_NULL){
				DlgMessage.information(Messages.getString("WndGasto.Information.TipoGastoNull"));
				cmbTipoGasto.setFocus(true);throw new CancelaGrabacionException();
			}else if (gex.getTipo()==GastosException.MONTO_GASTO_MAYOR_VENTAS){
				DlgMessage.information(Messages.getString("WndGasto.Information.MontoMayorVentas"));
				dbMonto.setFocus(true);throw new CancelaGrabacionException();
			}else if (gex.getTipo()==GastosException.CONSIGNADO_NULL){
				DlgMessage.information(Messages.getString("WndGasto.Information.ConsignadoNull"));
				txtConsignado.setFocus(true);throw new CancelaGrabacionException();
			}else if (gex.getTipo()==GastosException.OBSERVACIONES_NULL){
				DlgMessage.information(Messages.getString("WndGasto.Information.ObservacionesNull"));
				txtObservacion.setFocus(true);throw new CancelaGrabacionException();
			}else if (gex.getTipo()==GastosException.DOCUMENTO_NO_VALIDO){
				DlgMessage.information(Messages.getString("WndGasto.Information.DocumenoInvalido"));
				txtNroDocumento.setFocus(true);throw new CancelaGrabacionException();
			}
		}catch (NumeroDocumentoNullException nde){
			DlgMessage.information(Messages.getString("WndGasto.Information.DocumentoNull"));
			throw new CancelaGrabacionException();
		}catch (PilotoNullException pne){
			DlgMessage.information(Messages.getString("WndGasto.Information.PilotoNull"));
			throw new CancelaGrabacionException();
		}catch (BusNullException bne){
			DlgMessage.information(Messages.getString("WndGasto.Information.BusNull"));
			throw new CancelaGrabacionException();
		}catch (LiquidacionNullException lnex){
			DlgMessage.information(Messages.getString("WndGasto.Information.LiquidacionNull"));
			throw new CancelaGrabacionException();
		}catch (Exception ex) {
			DlgMessage.error(this.getClass().getName()+" "+ex.getMessage());
			ex.printStackTrace(); throw new CancelaGrabacionException();
		}
		
	}

	/*
	 * (non-Javadoc)
	 * @see com.tepsa.sisvyr.view.ui.IOpcionesMantenimiento#onDelete(int)
	 */
	@Override
	public void onDelete(int tab) throws Exception {
		Gasto ogasto = new Gasto();
		switch (tab) {
			case TAB_LIST:
				ogasto= listboxLista.getSelectedItem().getValue();
				break;
			case TAB_MAINTENANCE:
				ogasto.setId(new Integer(txtid.intValue()));	
				break;
		}
		ServiceLocator.getGastoManager().inactivar(ogasto.getId().longValue());
		ServiceLocator.getDetalleLiquidacionManager().delete(ogasto.getDetalleLiquidacion().getId());
		
	}

	/*
	 * (non-Javadoc)
	 * @see com.tepsa.sisvyr.view.ui.IOpcionesMantenimiento#onPrint(int)
	 */
	@Override
	public void onPrint(int tab) throws Exception {
		
		
	}

	/*
	 * (non-Javadoc)
	 * @see com.tepsa.sisvyr.view.ui.IOpcionesMantenimiento#onExport(int)
	 */
	@Override
	public void onExport(int tab) throws Exception {
		if(listboxLista.getItems().size()>0){
			Util.exportarExcel(listboxLista, "INGRESO DE GASTOS");
		}
	}

	/*
	 * (non-Javadoc)
	 * @see com.tepsa.sisvyr.view.ui.IOpcionesMantenimiento#onHelp()
	 */
	@Override
	public void onHelp() throws Exception {
				
	}

	/*
	 * (non-Javadoc)
	 * @see com.tepsa.sisvyr.view.ui.IOpcionesMantenimiento#onChangeTab(int)
	 */
	@Override
	public void onChangeTab(int tab) throws Exception {
		if (listboxLista.getSelectedIndex() >=0){
			mantenimientoGasto();
		}
	}

	
	private void listarRegistros(ArrayList<Gasto> lstRegistros) {
		Util.limpiarListbox(listboxLista);
		
		Listitem item = null;
		Listcell cell = null;
		Integer x =0; /**Contador utilizado para el item.*/
			
		for(Gasto gasto : lstRegistros){
			x += +1;
			item = new Listitem();
			cell = new Listcell((x.toString()));
			item.appendChild(cell); //Correlativo
			cell = new Listcell(gasto.getAgencia().getNombreCorto());
			item.appendChild(cell);
			cell = new Listcell(gasto.getLiquidacion().getNombreUsuario());
			item.appendChild(cell);
			cell = new Listcell(gasto.getTipoGasto().getDenominacion());
			item.appendChild(cell);
			cell = new Listcell(gasto.getMonto().toString());
			cell.setStyle("font-size:11px !Important");
			item.appendChild(cell);
			cell = new Listcell(gasto.getTipoGasto().getTipoOperacion().intValue()==Constantes.FALSE_VALUE?"GASTO":"INGRESO");
			item.appendChild(cell);
			cell = new Listcell(gasto.getObservacion());
			item.appendChild(cell);
			
			item.setValue(gasto);
			listboxLista.appendChild(item);			
		}

	}
	
	/**
	 * recupera datos del gasto para su edicion
	 * @param id : identificador del gasto
	 * @throws Exception
	 */
	private void mantenimientoGasto() throws Exception {
		if(listboxLista.getSelectedIndex()>=0){
			gasto =  new Gasto();
			Listitem listitem = listboxLista.getItemAtIndex(listboxLista.getSelectedIndex());
			gasto=listitem.getValue();
			
			textboxId.setText(gasto.getId().toString());
			Util.seleccionarValorItemCombo(TipoGasto.class, cmbTipoGasto, (gasto.getTipoGasto().getId()));
			
			if(gasto.getTipoGasto().getTipoOperacion().intValue()==Constantes.FALSE_VALUE)
				rbGasto.setChecked(true);
			else
				rbIngreso.setChecked(true);
			detalleLiquidacion = new DetalleLiquidacion();
			detalleLiquidacion=(gasto.getDetalleLiquidacion());
					
			dbFecha.setValue(gasto.getLiquidacion().getFechaLiquidacion());
			txtNroDocumento.setText(gasto.getNumeroDocumento());
			dbMonto.setValue(gasto.getMonto());
			if(gasto.getCodigoBus()!=null)
				seleccionarBus(gasto.getCodigoBus());
			else 
				cmbBus.setSelectedIndex(0);
			//txtBus.setText(gasto.getCodigoBus());
			txtNombrePiloto.setText(gasto.getNombrePiloto());
			txtConsignado.setText(gasto.getConsignado());
			txtObservacion.setText(gasto.getObservacion());

			onSelectTipoGasto();
			if(gasto.getLiquidacion().getestadoLiquidacion().equals(Constantes.LIQUI_ESTA_CERRADO)){
				btnGuardar.setDisabled(true);
				habilitaControles(false);
				DlgMessage.information(Messages.getString("WndGasto.Information.LiquidacionCerrada"));
			}
			
		}
		
	}

	/**
	 * Realiza la busqueda de la liquidación del usuario
	 * @return
	 * @throws Exception
	 */
	public Liquidacion buscarLiquidacion(String fecha, Integer idAgencia, Integer idUsuario) throws Exception { 			
		List<Liquidacion> list = ServiceLocator.getLiquidacionManager().buscarLiquidacion(fecha, fecha, idAgencia, idUsuario, null);
		Liquidacion liquidacion=null;
		if(list.size()>0)
			liquidacion=list.get(0);

		return liquidacion;	
	}
	
	public void onSelectTipoGasto(){
		txtConsignado.setReadonly(false);
		txtNombrePiloto.setReadonly(false);
		cmbBus.setDisabled(false);
		
		if(cmbTipoGasto.getSelectedItem().getValue() instanceof TipoGasto){
			TipoGasto tipoGasto = cmbTipoGasto.getSelectedItem().getValue();
			if(tipoGasto.getTipoOperacion().intValue()==Constantes.FALSE_VALUE) {							
				if(tipoGasto.getId().intValue()==Constantes.ID_TIPGAS_PEAJES){
					txtConsignado.setReadonly(true);
					txtConsignado.setText("");
				}else if (((TipoGasto)cmbTipoGasto.getSelectedItem().getValue()).getId().equals(Constantes.ID_TIPGAS_PAGO_GIROS)){
					cmbBus.setDisabled(true);
					cmbBus.setSelectedIndex(0);
					txtNombrePiloto.setText("");
					txtNombrePiloto.setReadonly(true);
				}	
			}else {
				txtConsignado.setReadonly(true);
				txtNombrePiloto.setReadonly(true);
				cmbBus.setDisabled(true);
			}
		}
	}
	
	/*selecciona el codigo del bus en el comobo cmbbus*/
	public void seleccionarBus(Object valorItem) {
		cmbBus.setSelectedIndex(-1);
		for (int i = 0; i < cmbBus.getItemCount(); i ++) {
			Comboitem oComboitem = cmbBus.getItemAtIndex(i);
	
			if (oComboitem.getValue()!=null && ((Bus) oComboitem.getValue()).getCodigo().equals(valorItem)) {
				cmbBus.setSelectedIndex(i);
				break;
			}
		}	
	}
	
	private void ventanaBusqueda() throws Exception{
		wndBusqueda = createWindowsBusqueda();
		this.appendChild(wndBusqueda);
		wndBusqueda.setMode("modal");
	}
	
	@SuppressWarnings("deprecation")
	private Window createWindowsBusqueda() throws Exception{
		Caption caption = null;
		final Window window = new Window("", "normal", true);
		window.setWidth("400px");
		caption = new Caption("PARAMETROS DE BÚSQUEDA");
		window.appendChild(caption);
		
		Label label=null;
						
		Grid grid=new Grid();
		Columns columns=new Columns();
		Column column=new Column();
		column.setWidth("100px");
		column.setAlign("right");
		columns.appendChild(column);
		columns.appendChild(new Column());
		grid.appendChild(columns);
		
		Rows rows=new Rows();
		Row row=new Row();
		label=new Label("FECHA :");
		dtbxFecha.setWidth("130px");
		dtbxFecha.setFormat("dd/MM/yyyy");
		dtbxFecha.setReadonly(false);
		dtbxFecha.setValue(new Date());
		row.appendChild(label);
		row.appendChild(dtbxFecha);
		rows.appendChild(row);

		row=new Row();
		label=new Label("TIPO AGENCIA :");
		cmbTipoAgencia.setWidth("220px");
		cmbTipoAgencia.setReadonly(true);
		row.appendChild(label);
		row.appendChild(cmbTipoAgencia);
		rows.appendChild(row);
		
		row=new Row();
		label=new Label("AGENCIA :");
		cmbAgencia.setWidth("220px");
		row.appendChild(label);
		row.appendChild(cmbAgencia);
		rows.appendChild(row);
		
		row=new Row();
		label=new Label("TIPO GASTO :");
		cmbTGasto.setWidth("220px");
		cmbTGasto.setReadonly(true);
		row.appendChild(label);
		row.appendChild(cmbTGasto);
		rows.appendChild(row);
		
		row=new Row();
		label=new Label("USUARIO :");
		cmbUsuario.setWidth("220px");
		row.appendChild(label);
		row.appendChild(cmbUsuario);
		rows.appendChild(row);
			
		grid.appendChild(rows);
		window.appendChild(grid);
		
		
		final Button btnFiltrar=new Button("Filtrar");
		btnFiltrar.setImage("/resources/mp_filtrar.png");
		
		Grid grid2=new Grid();
		row =new Row();
		rows=new Rows();
		Div div=new Div();
		div.setAlign("center");
		div.appendChild(btnFiltrar);
		row.appendChild(div);
		rows.appendChild(row);
		grid2.appendChild(rows);
		window.appendChild(grid2);
		
		//******
		Util.limpiarCombobox(cmbAgencia);
		Util.limpiarCombobox(cmbUsuario);
		Util.limpiarCombobox(cmbTGasto);
		//****************************************
		UtilData.cargarDataCombo(cmbTipoAgencia, TipoAgencia.class, true);
		UtilData.cargarGenericData(cmbAgencia, false);
		UtilData.cargarDataCombo(cmbTGasto, TipoGasto.class, true);
						
		Util.seleccionarValorItemCombo(TipoAgencia.class, cmbTipoAgencia, Constantes.ID_TIPAGE_TEPSA);
		UtilData.cargarAgenciaXtipoAgencia(cmbAgencia, Constantes.ID_TIPAGE_TEPSA, true);
		UtilData.cargarUsuariosLiquidacion(cmbUsuario, Constantes.FORMAT_DATE.format(dtbxFecha.getValue()), Constantes.FORMAT_DATE.format(dtbxFecha.getValue()), true, null);
		cmbUsuario.setSelectedIndex(0);
			
		//Validacion de roles.
		if(getRol().getId().intValue()==Constantes.ID_ROL_ADMIN_PUNTO_VENTA || getRol().getId().intValue()==Constantes.ID_ROL_REP_VENTAS){
			cmbTipoAgencia.setDisabled(true);
			cmbAgencia.setDisabled(true);
			Util.seleccionarValorItemCombo(Agencia.class, cmbAgencia, getAgencia().getId());
			
			if(getRol().getId().intValue()==Constantes.ID_ROL_REP_VENTAS){
				Util.seleccionarValorItemCombo(Usuario.class, cmbUsuario, getUsuario().getId());
				if(cmbUsuario.getSelectedIndex()<0)
					cmbUsuario.setSelectedIndex(0);
			}
		}else{
			cmbTipoAgencia.setDisabled(false);
			cmbAgencia.setDisabled(false);
			cmbAgencia.setSelectedIndex(0);
		}
		
		
		//Eventos onOK
		dtbxFecha.addEventListener(Events.ON_OK,new EventListener<Event>() {
			@Override
			public void onEvent(Event event) throws Exception {
				// TODO Auto-generated method stub
				cmbTipoAgencia.setFocus(true);
				cmbTipoAgencia.select();
			}
		});
		cmbTipoAgencia.addEventListener(Events.ON_OK,new EventListener<Event>() {
			@Override
			public void onEvent(Event event) throws Exception {
				// TODO Auto-generated method stub
				cmbAgencia.setFocus(true);
				cmbAgencia.select();
			}
		});
		cmbAgencia.addEventListener(Events.ON_OK,new EventListener<Event>() {
			@Override
			public void onEvent(Event event) throws Exception {
				// TODO Auto-generated method stub
				cmbTGasto.setFocus(true);
				cmbTGasto.select();
			}
		});
		cmbTGasto.addEventListener(Events.ON_OK,new EventListener<Event>() {
			@Override
			public void onEvent(Event event) throws Exception {
				// TODO Auto-generated method stub
				cmbUsuario.setFocus(true);
				cmbUsuario.select();
			}
		});
		cmbUsuario.addEventListener(Events.ON_OK,new EventListener<Event>() {
			@Override
			public void onEvent(Event event) throws Exception {
				// TODO Auto-generated method stub
				btnFiltrar.setFocus(true);
			}
		});
		btnFiltrar.addEventListener(Events.ON_OK, new EventListener<Event>() {
			@Override
			public void onEvent(Event event) throws Exception {
				// TODO Auto-generated method stub
				filtrar();
				window.onClose();
			}
		});
		
		//Eventos Change 
		dtbxFecha.addEventListener(Events.ON_CHANGE, new EventListener<Event>() {
			@Override
			public void onEvent(Event event) throws Exception {
				// cargar usuarios
				String fecha=Constantes.FORMAT_DATE.format(dtbxFecha.getValue());
				Util.limpiarCombobox(cmbUsuario);
				if(cmbAgencia.getSelectedIndex()>0){
					Integer idAgencia=((Agencia)cmbAgencia.getSelectedItem().getValue()).getId();
					UtilData.cargarUsuariosLiquidacion(cmbUsuario,fecha, fecha, true, idAgencia);
				}else
					UtilData.cargarUsuariosLiquidacion(cmbUsuario,fecha, fecha, true, null);
			}
		});
		cmbTipoAgencia.addEventListener(Events.ON_CHANGE,new EventListener<Event>() {
			@Override
			public void onEvent(Event event) throws Exception {
				// Carga las agencias asociadas al tipo se agencia seleccionada
				String fecha=Constantes.FORMAT_DATE.format(dtbxFecha.getValue());
				Util.limpiarCombobox(cmbAgencia);
				if(cmbTipoAgencia.getSelectedItem().getValue() instanceof TipoAgencia){
					Integer idTipoAgencia=((TipoAgencia)cmbTipoAgencia.getSelectedItem().getValue()).getId();
					UtilData.cargarAgenciaXtipoAgencia(cmbAgencia,idTipoAgencia , true);
				}else{
					UtilData.cargarDataCombo(cmbAgencia, Agencia.class, true);
				}
				cmbAgencia.setSelectedIndex(0);
				cmbUsuario.setSelectedIndex(0);
				UtilData.cargarUsuariosLiquidacion(cmbUsuario,fecha, fecha, true, null);
			}
		});
		cmbAgencia.addEventListener(Events.ON_CHANGE,new EventListener<Event>() {
			@Override
			public void onEvent(Event event) throws Exception {
				// Carga los Usuarios en base a la fecha y agencia Seleccionada.
				String fecha=Constantes.FORMAT_DATE.format(dtbxFecha.getValue());
				Util.limpiarCombobox(cmbUsuario);
				
				if(cmbAgencia.getSelectedIndex()>0){
					Integer idAgencia=((Agencia)cmbAgencia.getSelectedItem().getValue()).getId();
					UtilData.cargarUsuariosLiquidacion(cmbUsuario,fecha, fecha, true, idAgencia);
				}else
					UtilData.cargarUsuariosLiquidacion(cmbUsuario,fecha, fecha, true, null);
			}
		});
		
		
		//Eventsos Click
		btnFiltrar.addEventListener(Events.ON_CLICK,new EventListener<Event>() {
			@Override
			public void onEvent(Event event) throws Exception {
				// TODO Auto-generated method stub
				filtrar();
				window.onClose();
			}
		});
		
		return window;
		
	}
	
	private final void filtrar(){
		String fecha=Constantes.FORMAT_DATE.format(dtbxFecha.getValue());
		Integer idAgencia=null,idTipoGasto=null,idUsuario=null;
		if(cmbAgencia.getSelectedIndex()>0)
			idAgencia=((Agencia)cmbAgencia.getSelectedItem().getValue()).getId();
		if(cmbTGasto.getSelectedIndex()>0)
			idTipoGasto=((TipoGasto)cmbTGasto.getSelectedItem().getValue()).getId();
		if(cmbUsuario.getSelectedIndex()>0)
			idUsuario=((Usuario)cmbUsuario.getSelectedItem().getValue()).getId();
		
		listarRegistros((ArrayList<Gasto>) ServiceLocator.getGastoManager().buscarGasto(fecha, idTipoGasto, idAgencia, idUsuario));
	}
	
}
