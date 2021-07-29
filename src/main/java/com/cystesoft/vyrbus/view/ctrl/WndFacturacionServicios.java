/**
 * Proyecto		: SISVYR
 * Sistema		: Sistema de Ventas y Reservas
 * Descripción	: 
 * Autor		: José Avalos
 * Fecha		: 24 jun. 2021
 * Hora			: 20:41:09
 */
package com.cystesoft.vyrbus.view.ctrl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.TreeMap;

import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zul.Button;
import org.zkoss.zul.Caption;
import org.zkoss.zul.Checkbox;
import org.zkoss.zul.Column;
import org.zkoss.zul.Columns;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Comboitem;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.Doublebox;
import org.zkoss.zul.Grid;
import org.zkoss.zul.Groupbox;
import org.zkoss.zul.Hbox;
import org.zkoss.zul.Hlayout;
import org.zkoss.zul.Image;
import org.zkoss.zul.Label;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Radio;
import org.zkoss.zul.Radiogroup;
import org.zkoss.zul.Row;
import org.zkoss.zul.Rows;
import org.zkoss.zul.Separator;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

import com.cystesoft.vyrbus.model.bean.CanalVenta;
import com.cystesoft.vyrbus.model.bean.Cliente;
import com.cystesoft.vyrbus.model.bean.ControlEspecieValorada;
import com.cystesoft.vyrbus.model.bean.FormaPago;
import com.cystesoft.vyrbus.model.bean.Itinerario;
import com.cystesoft.vyrbus.model.bean.Liquidacion;
import com.cystesoft.vyrbus.model.bean.Pasajero;
import com.cystesoft.vyrbus.model.bean.Ruta;
import com.cystesoft.vyrbus.model.bean.Servicio;
import com.cystesoft.vyrbus.model.bean.TipoCobranza;
import com.cystesoft.vyrbus.model.bean.TipoComprobante;
import com.cystesoft.vyrbus.model.bean.TipoFormaPago;
import com.cystesoft.vyrbus.model.bean.TipoMovimiento;
import com.cystesoft.vyrbus.model.bean.VentaPasaje;
import com.cystesoft.vyrbus.service.exceptions.ClienteException;
import com.cystesoft.vyrbus.service.exceptions.DenominacionNullException;
import com.cystesoft.vyrbus.service.exceptions.DireccionFacturacionNullException;
import com.cystesoft.vyrbus.service.exceptions.EspecieValoradaNotAvailableException;
import com.cystesoft.vyrbus.service.exceptions.FormaPagoNullException;
import com.cystesoft.vyrbus.service.exceptions.ImporteMixtoNullException;
import com.cystesoft.vyrbus.service.exceptions.RazonSocialNullException;
import com.cystesoft.vyrbus.service.exceptions.TipoCobranzaNullException;
import com.cystesoft.vyrbus.service.locator.ServiceLocator;
import com.cystesoft.vyrbus.service.util.Constantes;
import com.cystesoft.vyrbus.service.util.Messages;
import com.cystesoft.vyrbus.service.util.Util;
import com.cystesoft.vyrbus.service.util.UtilData;
import com.cystesoft.vyrbus.service.util.VentasNotas;
import com.cystesoft.vyrbus.service.util.WSFE;
import com.cystesoft.vyrbus.view.ui.DlgMessage;
import com.cystesoft.vyrbus.view.ui.WndBase;

/**
 * @author Jose
 *
 */
public class WndFacturacionServicios extends WndBase {

	private static final long serialVersionUID = 1L;
	
	private Combobox cmbTipoComprobante;
	private Combobox cmbFormaPago;
	private Combobox cmbTipoCobranza;
	private Textbox txtNumeroComprobante;
	private Textbox txtDocumento;
	private Textbox txtCliente;
	private Textbox txtDireccion;
	private Textbox txtGlosa;
	private Image imgBuscar;
	private Doublebox dbxImporte;
	private Doublebox dbxImporteResumen;
	private Doublebox dbxIGVResumen;
	private Doublebox dbxTotalResumen;
	private Checkbox chkIGV;
	private Textbox txtIdCliente;
	private Window wndAnular = null;
	private Window wndFacturacionServicios;
	private Window wndRegistrar = null;
	private Radio rdAnulacionRegular;
	private Radio rdAnulacionNC;
	private Textbox txtComprobante;
	private Datebox dtbxDesde;
	private Datebox dtbxHasta;
	private Listbox listboxLista;
	

	private CanalVenta canalVenta = null;
	private Date fechaLiquidacion = null;
	private Liquidacion liquidacion = null;
		
	
	public static final int SEARCH_BY_DOCUMENTO = 1;
	public static final int SEARCH_BY_NOMBRES = 2;
	public static final int SEARCH_BY_RAZON = 3;
	
	private VentaPasaje servicioEspecial;

	
	/* (non-Javadoc)
	 * @see com.cystesoft.vyrbus.view.ui.WndBase#onCreate()
	 */
	@Override
	public void onCreate() throws Exception {
		canalVenta = (CanalVenta)this.getDesktop().getSession().getAttribute(Constantes.ATRIBUTO_CANAL_VENTA);
		fechaLiquidacion = (Date)this.getDesktop().getSession().getAttribute(Constantes.ATRIBUTO_FECHA_LIQUIDACION);
		
		dtbxDesde.setValue(new Date());
		dtbxHasta.setValue(new Date());
	}
	/* (non-Javadoc)
	 * @see com.cystesoft.vyrbus.view.ui.WndBase#initComponents()
	 */
	@Override
	public void initComponents() {
		txtComprobante = (Textbox)getFellow("txtComprobante");
		dtbxDesde = (Datebox)getFellow("dtbxDesde");
		dtbxHasta = (Datebox)getFellow("dtbxHasta");
		listboxLista = (Listbox)getFellow("listboxLista");
		
		wndFacturacionServicios = (Window)getFellow("wndFacturacionServicios");		
	}
	
	/**
	 * Limpia los controles del cliente.
	 */
	public void onCleanControls() {
		txtIdCliente.setText("");
		txtDocumento.setText("");
		txtCliente.setText("");
		txtDireccion.setText("");
		txtGlosa.setText("");
		cmbFormaPago.setSelectedIndex(0);
		cmbTipoCobranza.setSelectedIndex(0);
		chkIGV.setChecked(false);
		dbxImporte.setValue(0.00);
		dbxImporteResumen.setValue(0.00);
		dbxIGVResumen.setValue(0.00);
		dbxTotalResumen.setValue(0.00);
		onLoadEspecieValorada();
	}
	
//	public void verificarClienteSunat()throws WrongValueException, Exception{
//		if(!(txtDocumento.getText().trim().isEmpty())){			
//			String nroDocumento=txtDocumento.getText().trim();
//			//Consulta RUC EN sunat
//			List<String> ruc = RESTCiva.getDatosRuc(nroDocumento);
//
//			if(ruc!=null){
//				txtDocumento.setValue(ruc.get(0));
//				txtCliente.setValue(ruc.get(1));
//				txtDireccion.setValue(ruc.get(2));
//			}else{
//				onCleanControls();
//			}
//		}		
//	}
//	
	
	
	/**
	 * Limpia los controles para un nuevo documento
	 */
	public void onNew() {
		try {
			wndRegistrar = onCreateWindowNewFactura();
			this.appendChild(wndRegistrar);
			wndRegistrar.setMode("modal");
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/* (non-Javadoc)
	 * @see com.cystesoft.vyrbus.view.ui.IOpcionesMantenimiento#onSearch()
	 */
	public void onSearch() throws Exception {
		try {
			String numComprobante = txtComprobante.getText().trim().isEmpty()?null:txtComprobante.getText().trim();
			String fechaInicio = Util.DatetoString(dtbxDesde.getValue(), Constantes.DATE_FORMAT);
			String fechaFin = Util.DatetoString(dtbxHasta.getValue(), Constantes.DATE_FORMAT);
			
			List<VentaPasaje> lstVentas = ServiceLocator.getVentaPasajesManager().buscarFacturasServicioEspecial(numComprobante, fechaInicio, fechaFin);
			if(lstVentas.size()>0)
				listarRegistros(lstVentas);
			else
				DlgMessage.information("No se encontraron registros para los criterios de busqueda");
		}catch (Exception e) {
			e.printStackTrace();
			DlgMessage.error(e.getMessage());
		}
	}
	
	public void listarRegistros(List<VentaPasaje> arrayList) {
		
		Listitem item=null;
		Listcell cell=null;
		int x=1;
		
		Util.limpiarListbox(listboxLista);
		
		for(VentaPasaje ventaPasaje: arrayList){
			item=new Listitem();
			cell=new Listcell(String.valueOf(x++));
			item.appendChild(cell);
			
			cell=new Listcell(ventaPasaje.getTipoComprobante().getDenominacion());
			item.appendChild(cell);
			
			cell=new Listcell(ventaPasaje.getNumeroBoleto());
			item.appendChild(cell);
			
			cell=new Listcell(Util.DatetoString(ventaPasaje.getFechaLiquidacion(), Constantes.DATE_FORMAT));
			item.appendChild(cell);
			
			if(ventaPasaje.getCliente()!=null)
				cell=new Listcell(ventaPasaje.getCliente().getNumeroDocumento());
			else
				cell=new Listcell(ventaPasaje.getPasajero().getNumeroDocumento());
			cell.setStyle("font-size:11px !important");
			item.appendChild(cell);
			
			if(ventaPasaje.getCliente()!=null)
				cell=new Listcell(ventaPasaje.getCliente().getRazonSocial());
			else
				cell=new Listcell(ventaPasaje.getPasajero().getNombresApellidos());
			cell.setStyle("font-size:11px !important");
			item.appendChild(cell);
			
			cell=new Listcell(ventaPasaje.getFormaPago().getDenominacion());
			cell.setStyle("font-size:11px !important");
			item.appendChild(cell);
			
			cell=new Listcell(Util.toNumberFormat(ventaPasaje.getTarifa(), 2));
			cell.setStyle("font-size:11px !important");
			item.appendChild(cell);
			
			cell=new Listcell(Util.toNumberFormat(ventaPasaje.getIgv(), 2));
			cell.setStyle("font-size:11px !important");
			item.appendChild(cell);
			
			cell=new Listcell(Util.toNumberFormat(ventaPasaje.getImportePagado(), 2));
			cell.setStyle("font-size:11px !important");
			item.appendChild(cell);
			cell = new Listcell();
			Image imgAnular = new Image();
			imgAnular.setId(ventaPasaje.getId().toString());
			imgAnular.setSrc("/resources/mp_anular.png");
			imgAnular.setTooltiptext("Anular documento electrónico");
			imgAnular.addEventListener(Events.ON_CLICK, new EventListener<Event>() {
				public void onEvent(Event e) {
					anularComprobante(e.getTarget().getId());
				}
			});
			cell.appendChild(imgAnular);
			item.appendChild(cell);			
			
			item.setValue(ventaPasaje);
			listboxLista.appendChild(item);
		}		
	}

	/* (non-Javadoc)
	 * @see com.cystesoft.vyrbus.view.ui.IOpcionesMantenimiento#onSave(int)
	 */
	public void onSave(final Window win) throws Exception {
		try {
			if(txtIdCliente.getText().trim().equals(""))
				throw new ClienteException();
			else if(txtCliente.getText().trim().equals(""))
				throw new RazonSocialNullException();
			else if(txtDireccion.getText().trim().equals(""))
				throw new DireccionFacturacionNullException();
			else if(!(cmbFormaPago.getSelectedItem().getValue() instanceof FormaPago))
				throw new FormaPagoNullException();
			else if(cmbFormaPago.getSelectedItem().getValue() instanceof FormaPago 
					&& ((FormaPago)cmbFormaPago.getSelectedItem().getValue()).getId() == Constantes.ID_FORPAG_CREDITO 
					&& !(cmbTipoCobranza.getSelectedItem().getValue() instanceof TipoCobranza))
				throw new TipoCobranzaNullException();
			else if(dbxImporte.getValue() == null && dbxImporte.getValue()!=0.0)
				throw new ImporteMixtoNullException();
			else if(txtGlosa.getText().trim().equals(""))
				throw new DenominacionNullException();
			
			Messagebox.show("Se va registrar la Venta, ¿Desea continuar?", DlgMessage.NOMBREAPLICACION, DlgMessage.BTN_YESNO, Messagebox.QUESTION, new EventListener<Event>() {
				public void onEvent(Event e) {
					try {
						if(e.getName().equals("onYes")){
							servicioEspecial = new VentaPasaje();
							
							servicioEspecial.setItinerario(new Itinerario(new Long(1)));
							servicioEspecial.setRuta(new Ruta(1));
							if(((TipoComprobante)cmbTipoComprobante.getSelectedItem().getValue()).getId() == Constantes.ID_TIPCOM_FACTURA) {
								servicioEspecial.setCliente(new Cliente(Long.valueOf(txtIdCliente.getText())));
								servicioEspecial.setPasajero(new Pasajero(Long.valueOf(1)));
							}else {
								servicioEspecial.setCliente(null);
								servicioEspecial.setPasajero(new Pasajero(Long.valueOf(txtIdCliente.getText())));
							}
							
							servicioEspecial.setFormaPago((FormaPago)cmbFormaPago.getSelectedItem().getValue());
							if(((FormaPago)cmbFormaPago.getSelectedItem().getValue()).getId() == Constantes.ID_FORPAG_CREDITO) {
								servicioEspecial.setTipoCobranza((TipoCobranza)cmbTipoCobranza.getSelectedItem().getValue());
								servicioEspecial.setTipoFormaPago(new TipoFormaPago(Constantes.ID_TIPFORPAG_CREDITO));
								servicioEspecial.setEstadoDocumento(Constantes.ESTADO_DOCUMENTO_ACTIVO);
							}else {
								servicioEspecial.setTipoFormaPago(new TipoFormaPago(Constantes.ID_TIPFORPAG_EFECTIVO));
								servicioEspecial.setTipoCobranza(null);
								servicioEspecial.setEstadoDocumento(Constantes.ESTADO_DOCUMENTO_PAGADO);
							}
							
							servicioEspecial.setTipoMovimiento(new TipoMovimiento(Constantes.ID_TIPMOV_SERVICIO_ESPECIAL));
							servicioEspecial.setServicio(new Servicio(1));
							
							servicioEspecial.setTipoComprobante((TipoComprobante)cmbTipoComprobante.getSelectedItem().getValue());
							servicioEspecial.setNumeroBoleto(txtNumeroComprobante.getText().trim().toUpperCase());
							servicioEspecial.setTarifa(dbxTotalResumen.getValue());
							servicioEspecial.setImportePagado(dbxTotalResumen.getValue());
							servicioEspecial.setTipoTransaccion(Constantes.TIPO_OPERACION_VENTA_ESPECIAL);
							servicioEspecial.setFechaCaducidad(new Date());
							
							servicioEspecial.setFechaLiquidacion(fechaLiquidacion);
							servicioEspecial.setAgencia(getAgencia());
							servicioEspecial.setUsuario(getUsuario());
							servicioEspecial.setCanalVenta(canalVenta);
							servicioEspecial.setIdaRetorno(0);
							servicioEspecial.setObservaciones(txtGlosa.getText().trim().toUpperCase());
							servicioEspecial.setIgv(dbxIGVResumen.getValue());
							servicioEspecial.setSecuencial(0);
							servicioEspecial.setRecargo(0.0);
							servicioEspecial.setDescuento(0.0);
							servicioEspecial.setPenalidad(0.0);
							servicioEspecial.setAcuenta(0.0);
							servicioEspecial.setImportePagadoEfectivo(0.0);
							servicioEspecial.setImportePagadoTarjeta(0.0);
							servicioEspecial.setEsFechaAbierta(0);
							servicioEspecial.setNumeroControl("T00000");
							
							UtilData.auditarRegistro(servicioEspecial, getUsuario(), Executions.getCurrent());
							servicioEspecial.setUsuarioHardware(getUsuarioHardware());
							servicioEspecial.setEstadoRegistro(Constantes.VALUE_ACTIVO);
							
							int result = ServiceLocator.getVentaPasajesManager().guardarServicioEspecial(servicioEspecial);
							
							servicioEspecial = ServiceLocator.getVentaPasajesManager().buscarVentaById(servicioEspecial.getId());
							
							if(servicioEspecial.getTipoComprobante().getId().intValue()==Constantes.ID_TIPCOM_BOLETA_VENTA || 
									servicioEspecial.getTipoComprobante().getId().intValue()==Constantes.ID_TIPCOM_FACTURA){
								boolean printComprobante=(true);
								
								List<VentaPasaje> listVentaPasajes= new ArrayList<>();
								listVentaPasajes.add(servicioEspecial);				
								//Aqui se envia el comprobante al servidor de Facturación Electrónica
								/*Comentado para lanzar a pruebas Transmar*/
//								WSFE.sendVenta(listVentaPasajes, wndFacturacionServicios, printComprobante, null);
							}
							
							if(result == Constantes.CORRECT) {
								DlgMessage.information("La venta se registro correctamente");
								onCleanControls();
								win.onClose();
							}
						}
					}catch (Exception ex) {
						DlgMessage.error(ex.getMessage());
					}
				}
			});
			
		}catch(ClienteException cex) {
			DlgMessage.information("Debe de indicar el Cliente al que se emitira el documento", txtDocumento);
		}catch(RazonSocialNullException rsex) {
			DlgMessage.information("El cliente no existe");
		}catch(DireccionFacturacionNullException dfex) {
			DlgMessage.information("Debe ingresar la direccion de facturacion.");
		}catch(FormaPagoNullException fpex) {
			DlgMessage.information("Debe seleccionar la forma de pago.", cmbFormaPago);
		}catch(TipoCobranzaNullException tcex) {
			DlgMessage.information("Debe seleccionar el tipo de cobranza", cmbTipoCobranza);
		}catch(ImporteMixtoNullException iex) {
			DlgMessage.information("Debe de ingresar el importe del documento", dbxImporte);
		}catch(DenominacionNullException dex) {
			DlgMessage.information("Debe de ingresar la Glosa del documento a emitir", txtGlosa);
		}catch (Exception ex) {
			DlgMessage.information(ex.getMessage());
			ex.printStackTrace();
		}
	}
	
	@Override
	public void onClose() {
		closeTabWindow();
	}
	
//	public void mantenimientoRegistro (Long id) throws Exception {
//		servicioEspecial = ServiceLocator.getVentaPasajesManager().buscarPorId(id);
//		TipoComprobante oTipoComprobante = servicioEspecial.getTipoComprobante();
////		TipoDocumento oTipoDocumento = servicioEspecial.getTipoDocumento();
//		FormaPago oFormaPago = servicioEspecial.getFormaPago();
//		TipoCobranza oTipoCobranza = servicioEspecial.getTipoCobranza();
//		Cliente oCliente = servicioEspecial.getCliente();
//		Pasajero oPasajero = servicioEspecial.getPasajero();
//		
//		textboxId.setText(String.valueOf(servicioEspecial.getId()));
//		
//		if(oTipoComprobante != null){
//			Util.seleccionarValorItemCombo(TipoComprobante.class, cmbTipoComprobante, oTipoComprobante.getId());
//		}
//		if (oFormaPago != null){
//			Util.seleccionarValorItemCombo(FormaPago.class, cmbFormaPago, oFormaPago.getId());
//		}
//		if (oFormaPago != null && oFormaPago.getId()==Constantes.ID_FORPAG_CREDITO){
//			Util.seleccionarValorItemCombo(TipoCobranza.class, cmbTipoCobranza, oTipoCobranza.getId());
//		}
////		if (oTipoCobranza != null){
////			Util.seleccionarValorItemCombo(TipoCobranza.class, cboGrupoMantenimiento, oTipoCobranza.getId());
////		}
//		txtNumeroComprobante.setText(servicioEspecial.getNumeroBoleto());
//		
//		if(servicioEspecial.getCliente()!=null) {
//			txtDocumento.setText(servicioEspecial.getCliente().getNumeroDocumento());
//			txtCliente.setText(servicioEspecial.getCliente().getRazonSocial());
//			txtDireccion.setText(servicioEspecial.getCliente().getDireccion());
//		}else {
//			txtDocumento.setText(servicioEspecial.getPasajero().getNumeroDocumento());
//			txtCliente.setText(servicioEspecial.getPasajero().getNombresApellidos());
//			txtDireccion.setText(servicioEspecial.getPasajero().getDireccion());
//		}
//		
//		txtGlosa.setText((servicioEspecial.getObservaciones()));
//		dbxImporte.setValue(servicioEspecial.getTarifa());
//		dbxImporteResumen.setValue(servicioEspecial.getTarifa());
//		dbxIGVResumen.setValue(servicioEspecial.getIgv());
//		dbxTotalResumen.setValue(servicioEspecial.getImportePagado());
//		chkIGV.setChecked(true);
//		calcularPagos();
//		disabledControls(false);
//	}
	
	public void disabledControls(boolean estado) {
		cmbTipoComprobante.setReadonly(estado);
		txtNumeroComprobante.setReadonly(!estado);
		txtDocumento.setReadonly(estado);
		txtCliente.setReadonly(!estado);
		txtDireccion.setReadonly(!estado);
		cmbFormaPago.setReadonly(estado);
		cmbTipoCobranza.setReadonly(!estado);
		txtGlosa.setReadonly(estado);
		dbxImporte.setReadonly(estado);
		dbxImporteResumen.setReadonly(!estado);
		dbxIGVResumen.setReadonly(!estado);
		dbxTotalResumen.setReadonly(!estado);
	}
	
	@SuppressWarnings("deprecation")
	private Window onCreateWindowNewFactura() throws Exception {
		Caption caption = null;
		Column column = null;
		Row row = null;
		Label label = null;
		
		final Window win = new Window("", "normal", true);
		win.setWidth("690px");
		caption = new Caption("FACTURACION DE SERVICIOS ESPECIALES");
		win.appendChild(caption);
		
		Grid grid = new Grid();
		grid.setStyle("border:none");
		
		Columns columns = new Columns();
		
		column = new Column();
		column.setAlign("right");
		column.setWidth("120px");
		columns.appendChild(column);
		
		column = new Column();
		column.setWidth("130px");
		columns.appendChild(column);
		
		column = new Column();
		column.setAlign("right");
		column.setWidth("40px");
		columns.appendChild(column);
		
		column = new Column();
		column.setWidth("100px");
		columns.appendChild(column);
		
		column = new Column();
		column.setAlign("right");
		column.setWidth("110px");
		columns.appendChild(column);
		
		column = new Column();
		columns.appendChild(column);
		
		grid.appendChild(columns);
		
		Rows rows = new Rows();
		
		row = new Row();
		label = new Label("TIPO COMPROBANTE :");
		row.appendChild(label);
		
		cmbTipoComprobante = new Combobox();
		cmbTipoComprobante.addEventListener(Events.ON_OK, new EventListener<Event>() {
			public void onEvent(Event e) {
				txtDocumento.setFocus(true);
			}
		});
		cmbTipoComprobante.setWidth("110px");
		cmbTipoComprobante.setDisabled(true);
		row.appendChild(cmbTipoComprobante);
		
		label = new Label();
		row.appendChild(label);
		
		label = new Label();
		row.appendChild(label);
		
		label = new Label("N° COMPROBANTE :");
		label.setStyle("color:blue; font-weight: bold;");
		row.appendChild(label);
		
		txtNumeroComprobante = new Textbox();
		txtNumeroComprobante.setDisabled(true);
		txtNumeroComprobante.setWidth("130px");
		txtNumeroComprobante.setStyle("font-weight:bold; font-size:14px !important; text-align: center;");
		row.appendChild(txtNumeroComprobante);
		
		rows.appendChild(row);
		
		row = new Row();
		label = new Label("RUC :");
		row.appendChild(label);
		
		Hlayout hlayout = new Hlayout();
		txtDocumento = new Textbox();
		txtDocumento.addEventListener(Events.ON_OK, new EventListener<Event>() {
			public void onEvent(Event e) {
				buscarCliente();
			}
		});
		txtDocumento.setWidth("90px");
		hlayout.appendChild(txtDocumento);
		imgBuscar = new Image("/resources/mp_buscarEnabled.png");
		imgBuscar.setStyle("cursor: pointer");
		hlayout.appendChild(imgBuscar);
		txtIdCliente = new Textbox();
		txtIdCliente.setVisible(false);
		hlayout.appendChild(txtIdCliente);
		row.appendChild(hlayout);
		
		label = new Label();
		row.appendChild(label);
		
		label = new Label();
		row.appendChild(label);
		
		label = new Label();
		row.appendChild(label);
		
		label = new Label();
		row.appendChild(label);
		
		rows.appendChild(row);
		
		row = new Row();
		row.setSpans("1,5");
		
		label = new Label("RAZON SOCIAL :");
		row.appendChild(label);
		
		txtCliente = new Textbox();
		txtCliente.setWidth("520px");
		txtCliente.setReadonly(true);
		row.appendChild(txtCliente);
		
		rows.appendChild(row);
		
		row = new Row();
		row.setSpans("1,5");
		
		label = new Label("DIRECCION :");
		row.appendChild(label);
		
		txtDireccion = new Textbox();
		txtDireccion.setWidth("520px");
		txtDireccion.setReadonly(true);
		row.appendChild(txtDireccion);
		
		rows.appendChild(row);
		
		row = new Row();
		
		label = new Label("FORMA PAGO :");
		row.appendChild(label);
		
		cmbFormaPago = new Combobox();
		cmbFormaPago.addEventListener(Events.ON_OK, new EventListener<Event>() {
			public void onEvent(Event e) {
				if(cmbFormaPago.getSelectedItem()!= null && cmbFormaPago.getSelectedItem().getValue() instanceof FormaPago && ((FormaPago)cmbFormaPago.getSelectedItem().getValue()).getId()==Constantes.ID_FORPAG_CONTADO)
					dbxImporte.setFocus(true);
				else
					cmbTipoCobranza.setFocus(true);
			}
		});
		cmbFormaPago.setWidth("110px");
		row.appendChild(cmbFormaPago);
		
		label = new Label("TIPO :");
		row.appendChild(label);
		
		cmbTipoCobranza = new Combobox();
		cmbTipoCobranza.addEventListener(Events.ON_OK, new EventListener<Event>() {
			public void onEvent(Event e) {
				dbxImporte.setFocus(true);
			}
		});
		cmbTipoCobranza.setWidth("90px");
		cmbTipoCobranza.setDisabled(true);
		row.appendChild(cmbTipoCobranza);
		
		label = new Label("IMPORTE :");
		label.setStyle("color:red; font-weight: bold;");
		row.appendChild(label);
		
		hlayout = new Hlayout();
		dbxImporte = new Doublebox();
		dbxImporte.addEventListener(Events.ON_OK, new EventListener<Event>() {
			public void onEvent(Event e) {
				txtGlosa.setFocus(true);
			}
		});
		dbxImporte.setFormat("###,##0.00");
		dbxImporte.setWidth("60px");
		dbxImporte.setStyle("color: red; font-weight: bold; font-size:14px;");
		hlayout.appendChild(dbxImporte);
		chkIGV = new Checkbox("Incluido IGV");
		hlayout.appendChild(chkIGV);
		row.appendChild(hlayout);
		
		rows.appendChild(row);
		
		row = new Row();
		row.setSpans("1,5");
		
		label = new Label("GLOSA :");
		row.appendChild(label);
		txtGlosa = new Textbox();
		txtGlosa.setMultiline(true);
		txtGlosa.setHeight("90px");
		txtGlosa.setWidth("520px");
		row.appendChild(txtGlosa);
		
		rows.appendChild(row);
		
		row = new Row();
		row.setSpans("6");
		row.setStyle("text-align:center");
		Groupbox groupbox = new Groupbox();
		groupbox.setTitle("DETALLE DEL PAGO");
		groupbox.setMold("3d");
		groupbox.setStyle("color: #ffffff; ");
		groupbox.setClosable(true);
		groupbox.setSclass("detalle");
		
		Grid grdPagos = new Grid();
		grdPagos.setStyle("border:none");
		
		columns = new Columns();
		column = new Column();
		column.setWidth("95px");
		column.setAlign("right");
		columns.appendChild(column);
		
		column = new Column();
		column.setWidth("95px");
		column.setAlign("left");
		columns.appendChild(column);
		
		column = new Column();
		column.setWidth("95px");
		column.setAlign("right");
		columns.appendChild(column);
		
		column = new Column();
		column.setWidth("95px");
		column.setAlign("left");
		columns.appendChild(column);
		
		column = new Column();
		column.setWidth("95px");
		column.setAlign("right");
		columns.appendChild(column);
		
		column = new Column();
		column.setAlign("left");
		columns.appendChild(column);
		
		grdPagos.appendChild(columns);
		
		Rows rowsPagos = new Rows();
		Row rowPagos = new Row();
		
		label = new Label("SUB TOTAL :");
		label.setStyle("color:red; font-weight: bold;");
		rowPagos.appendChild(label);
		
		dbxImporteResumen = new Doublebox();
		dbxImporteResumen.setFormat("###,##0.00");
		dbxImporteResumen.setWidth("70px");
		dbxImporteResumen.setStyle("color: red; font-weight: bold; font-size:14px;");
		dbxImporteResumen.setReadonly(true);
		rowPagos.appendChild(dbxImporteResumen);
		
		label = new Label("IGV :");
		label.setStyle("color:red; font-weight: bold;");
		rowPagos.appendChild(label);
		
		dbxIGVResumen = new Doublebox();
		dbxIGVResumen.setFormat("###,##0.00");
		dbxIGVResumen.setWidth("70px");
		dbxIGVResumen.setStyle("color: red; font-weight: bold; font-size:14px;");
		dbxIGVResumen.setReadonly(true);
		rowPagos.appendChild(dbxIGVResumen);
		
		label = new Label("IMPORTE TOTAL :");
		label.setStyle("color:red; font-weight: bold;");
		rowPagos.appendChild(label);
		
		dbxTotalResumen = new Doublebox();
		dbxTotalResumen.setFormat("###,##0.00");
		dbxTotalResumen.setWidth("70px");
		dbxTotalResumen.setStyle("color: red; font-weight: bold; font-size:14px;");
		dbxTotalResumen.setReadonly(true);
		rowPagos.appendChild(dbxTotalResumen);
		
		rowsPagos.appendChild(rowPagos);
		grdPagos.appendChild(rowsPagos);
		groupbox.appendChild(grdPagos);
		
		row.appendChild(groupbox);
		rows.appendChild(row);
		
		row = new Row();
		row.setSpans("6");
		
		Hbox hbox = new Hbox();
		hbox.setAlign("center");
		
		Button btnGuardar = new Button("Guardar");
		btnGuardar.addEventListener(Events.ON_CLICK, new EventListener<Event>() {
			public void onEvent(Event e) throws Exception {
				onSave(win);
			}
		});
		btnGuardar.setImage("/resources/mp_guardarEnabled.png");
		btnGuardar.setSclass("btnCommandL");
		btnGuardar.setStyle("width: 90px");
		hbox.appendChild(btnGuardar);
		
		Separator separator = new Separator();
		separator.setWidth("15px");
		hbox.appendChild(separator);
		
		Button btnCancelar = new Button("Cancelar");
		btnCancelar.addEventListener(Events.ON_CLICK, new EventListener<Event>() {
			public void onEvent(Event e) {
				win.onClose();
			}
		});
		btnCancelar.setImage("/resources/mp_cerrar.png");
		btnCancelar.setSclass("btnCommandL");
		btnCancelar.setStyle("width: 90px");
		hbox.appendChild(btnCancelar);
		
		row.appendChild(hbox);
		rows.appendChild(row);
		
		grid.appendChild(rows);
		win.appendChild(grid);
		
		onLoadFormaPago();
		UtilData.cargarTipoComprobanteSunat(cmbTipoComprobante, false);
		UtilData.cargarDataCombo(cmbTipoCobranza, TipoCobranza.class, false);
		cmbTipoComprobante.setSelectedIndex(2);
		onLoadEspecieValorada();
		
		cmbTipoComprobante.addEventListener(Events.ON_CHANGE, new EventListener<Event>() {
			public void onEvent(Event e) {
				onLoadEspecieValorada();
				onCleanControls();
				disabledControls(false);
				txtDocumento.setFocus(true);
			}
		});
		
		cmbFormaPago.addEventListener(Events.ON_SELECT, new EventListener<Event>() {
			public void onEvent(Event e) {
				if(cmbFormaPago.getSelectedItem().getValue() instanceof FormaPago) {
					if(((FormaPago)cmbFormaPago.getSelectedItem().getValue()).getId()==Constantes.ID_FORPAG_CREDITO)
						cmbTipoCobranza.setDisabled(false);
					else
						cmbTipoCobranza.setDisabled(true);
				}
			}
		});
		
		dbxImporte.addEventListener(Events.ON_BLUR, new EventListener<Event>() {
			public void onEvent(Event e) {
				calcularPagos();
			}
		});
		
		chkIGV.addEventListener(Events.ON_CHECK, new EventListener<Event>() {
			public void onEvent(Event e) {
				calcularPagos();
			}
		});
		
		imgBuscar.addEventListener(Events.ON_CLICK, new EventListener<Event>() {
			public void onEvent(Event e) {
				buscarCliente();
			}
		});
		
		return win;
	}
	
	public void onLoadFormaPago() {
		try{
			TreeMap<String, Object> parametros = new TreeMap<String, Object>();
			parametros.put("estadoRegistro", Constantes.VALUE_ACTIVO);
			ArrayList<String> criteriosOrdenar = new ArrayList<String>();
			criteriosOrdenar.add("denominacion");
			ArrayList<FormaPago> lstFormaPago = ServiceLocator.getFormaPagoManager().buscarPorX(parametros, criteriosOrdenar);
			UtilData.cargarGenericData(cmbFormaPago, false);	
			for (int l = 0; l < lstFormaPago.size(); l ++) {
				FormaPago oFormaPago = lstFormaPago.get(l);
				if(oFormaPago.getId()!=Constantes.ID_FORPAG_CORTESIA) {
				Comboitem oComboitem = new Comboitem();	
				oComboitem.setValue(oFormaPago);
				oComboitem.setLabel(oFormaPago.getDenominacion());	
				cmbFormaPago.appendChild(oComboitem);
				}
			}
		}catch(Exception ex){
			ex.printStackTrace();
		}
	}
	
	private void onLoadEspecieValorada() {
		try {
			ControlEspecieValorada controlEspecieValorada = null;
			controlEspecieValorada=UtilData.buscarEspecieValoradaByCaja(((TipoComprobante)cmbTipoComprobante.getSelectedItem().getValue()).getId(), getAgencia(), false, getUsuarioHardware(), null);
			txtNumeroComprobante.setValue(controlEspecieValorada.toString());
		}catch(EspecieValoradaNotAvailableException evex) {
			DlgMessage.information(Messages.getString("UtilData.information.noAsignacionEspecieValorada"));
		}catch (Exception ex) {
			ex.printStackTrace();
			DlgMessage.information(ex.getMessage());
		}
	}
	
	/**
	 * Busca los clientes de acuerdo al criterio de busqueda.
	 * @param criterio	: Puede ser DOCUMENTO o RAZON SOCIAL.
	 */
	public void buscarCliente(){
		try{
			TreeMap<String, Object> criterioBusqueda = new TreeMap<String, Object>();
			if(!(txtDocumento.getText().trim().isEmpty())){
				if(((TipoComprobante) cmbTipoComprobante.getSelectedItem().getValue()).getId() == Constantes.ID_TIPCOM_FACTURA) {
					ArrayList<Cliente> lstClientes = null;
					criterioBusqueda.put("numeroDocumento", txtDocumento.getText().toUpperCase()+"%");
					List<String> criteriosOrdenar = new ArrayList<String>();
					criteriosOrdenar.add("razonSocial");
					criteriosOrdenar.add("numeroDocumento");
					lstClientes = ServiceLocator.getClienteManager().buscarPorX(criterioBusqueda, criteriosOrdenar);
					listarCliente(lstClientes);
//				}else if(((TipoComprobante) cmbTipoComprobante.getSelectedItem().getValue()).getId() == Constantes.ID_TIPCOM_BOLETA_VENTA) {
//					ArrayList<Pasajero> lstPasajeros = null;
//					criterioBusqueda.put("numeroDocumento", txtDocumento.getText().toUpperCase()+"%");
//					List<String> criteriosOrdenar = new ArrayList<String>();
//					criteriosOrdenar.add("numeroDocumento");
//					lstPasajeros = ServiceLocator.getPasajeroManager().buscarPorX(criterioBusqueda, criteriosOrdenar);
//					listarPasajero(lstPasajeros);
				}
			}else {
				
			}
		}catch(Exception ex){
			DlgMessage.error(this.getClass().getSimpleName()+" "+ex.getMessage());
			ex.printStackTrace();
			log.error(ex);
		}
	}
	
	/**
	 * Permite crear un array de array para luego cargarlos en un listbox.
	 * @param lstRegistros	: ArrayList con los datos de los clientes encontrados.
	 * @throws Exception
	 */
	private void listarCliente(ArrayList<Cliente> lstRegistros)throws Exception{
		
		if(lstRegistros.size()==1){
			Cliente cliente = lstRegistros.get(0);
			txtCliente.setText(cliente.getRazonSocial());
			txtDireccion.setText(cliente.getDireccion());
			cmbFormaPago.setFocus(true);
			txtIdCliente.setText(cliente.getId().toString());
		}else{
			DlgMessage.information(Messages.getString("WndFacturacionServicios.information.noClienteEncontrado"));
			String nroRuc = txtDocumento.getValue();
			onCleanControls();
			txtDocumento.setValue(nroRuc);
			txtDocumento.select();
//			verificarClienteSunat();
		}
	}
	
	public void calcularPagos() {
		if(dbxImporte.getValue() != null) {
			if(chkIGV.isChecked()) {
				dbxImporteResumen.setValue(dbxImporte.getValue()/1.18);
				dbxIGVResumen.setValue(dbxImporte.getValue() - (dbxImporte.getValue()/1.18));
				dbxTotalResumen.setValue(dbxImporte.getValue());
			}else {
				dbxImporteResumen.setValue(dbxImporte.getValue());
				dbxIGVResumen.setValue(dbxImporte.getValue() * 0.18);
				dbxTotalResumen.setValue(dbxImporteResumen.getValue() + dbxIGVResumen.getValue());
			}
		}
	}
	
	public void anularComprobante(String idComprobante) {
		try {
			VentaPasaje ventaServicioEspecial = ServiceLocator.getVentaPasajesManager().buscarPorId(Long.valueOf(idComprobante));
			wndAnular = createVentanaAnulacion(ventaServicioEspecial);
			this.appendChild(wndAnular);
			wndAnular.setMode("modal");
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@SuppressWarnings("deprecation")
	private Window createVentanaAnulacion(final VentaPasaje ventaOriginal){
		Caption caption = null;
		Groupbox groupboxDetalle = null;
		Groupbox groupbox = null;
		Grid grid = new Grid();
		Columns columns = new Columns();
		Column column = null;
		Rows rows = new Rows();
		Row row = null;
		Label label = null;
		Textbox text = null;
		Radiogroup radiogroup = new Radiogroup();
		
		final Window win = new Window("", "normal", true);
		win.setWidth("520px");
		
		caption = new Caption("ANULACION DE COMPROBANTE", "resources/mp_anular.png");
		win.appendChild(caption);
		
		groupbox = new Groupbox();
		groupbox.setMold("3d");
		groupbox.setClosable(false);
		caption = new Caption("TIPO DE ANULACION");
		groupbox.appendChild(caption);
		rdAnulacionRegular = new Radio("Anulacion Regular");
		rdAnulacionRegular.setStyle("color: blue");
		rdAnulacionRegular.setSelected(true);
		radiogroup.appendChild(rdAnulacionRegular);
		radiogroup.appendChild(new Separator("vertical"));
		rdAnulacionNC = new Radio("Anulacion con NC");
		rdAnulacionNC.setStyle("color: blue");
		radiogroup.appendChild(rdAnulacionNC);
		groupbox.appendChild(radiogroup);
		
		groupbox.appendChild(new Separator("horizontal"));
		
		groupboxDetalle = new Groupbox();
		groupboxDetalle.setMold("3d");
		groupboxDetalle.setClosable(false);
		caption = new Caption("DATOS DEL COMPROBANTE");
		groupboxDetalle.appendChild(caption);
		
		/*	Columna 1	*/
		column = new Column();
		column.setAlign("right");
		columns.appendChild(column);
		/*	Columna 2	*/
		column = new Column();
		columns.appendChild(column);
		/*	Columna 3	*/
		column = new Column();
		column.setAlign("right");
		columns.appendChild(column);
		/*	Columna 4	*/
		column = new Column();
		columns.appendChild(column);
		
		grid.appendChild(columns);
		
		row = new Row();		
		label = new Label("NUMERO COMPROBANTE :");
		row.appendChild(label);		
		text = new Textbox(ventaOriginal.getNumeroBoleto());
		text.setReadonly(true);
		text.setWidth("80px");
		text.setStyle("font-size:11px !important");
		row.appendChild(text);		
		label = new Label("FECHA EMISION :");
		row.appendChild(label);		
		text = new Textbox(ventaOriginal.getFechaLiquidacion()==null?"":Util.DatetoString(ventaOriginal.getFechaLiquidacion(), Constantes.DATE_FORMAT));
		text.setReadonly(true);
		text.setWidth("80px");
		text.setStyle("font-size:11px !important");
		row.appendChild(text);		
		rows.appendChild(row);
		
		row = new Row();
		row.setSpans("1,3");
		label = new Label("CLIENTE :");
		row.appendChild(label);
		text = new Textbox(ventaOriginal.getCliente()==null?ventaOriginal.getPasajero().getNombresApellidos():ventaOriginal.getCliente().getRazonSocial());
		text.setReadonly(true);
		text.setWidth("314px");
		text.setStyle("font-size:11px !important");
		row.appendChild(text);		
		rows.appendChild(row);
		
		row = new Row();		
		label = new Label("RUC / DNI :");
		row.appendChild(label);		
		text = new Textbox(ventaOriginal.getCliente()==null?ventaOriginal.getPasajero().getNumeroDocumento():ventaOriginal.getCliente().getNumeroDocumento());
		text.setReadonly(true);
		text.setWidth("100px");
		text.setStyle("font-size:12px !important; font-weight: bold; text-align: center;");
		row.appendChild(text);		
		label = new Label("IMPORTE :");
		row.appendChild(label);		
		text = new Textbox(Util.toNumberFormat(ventaOriginal.getImportePagado(), 2));
		text.setReadonly(true);
		text.setWidth("80px");
		text.setStyle("font-size:12px !important; color: red; font-weight: bold; text-align: right;");
		row.appendChild(text);		
		rows.appendChild(row);
		
		row=new Row();
		row.setSpans("1,4");
		label = new Label("MOTIVO ANULACIÓN (*) :");
		row.appendChild(label);		
		final Textbox txtMotivoAnulacion = new Textbox();
		txtMotivoAnulacion.setWidth("314px");
		txtMotivoAnulacion.setMultiline(true);
		txtMotivoAnulacion.setRows(3);
		txtMotivoAnulacion.setMaxlength(255);
		txtMotivoAnulacion.setStyle("font-size:11px !important");
		row.appendChild(txtMotivoAnulacion);		
		rows.appendChild(row);
		
		
		grid.appendChild(rows);
		groupboxDetalle.appendChild(grid);
		
		groupbox.appendChild(groupboxDetalle);
		win.appendChild(groupbox);
		
		grid = new Grid();
		columns = new Columns();
		column = new Column();
		column.setAlign("center");
		columns.appendChild(column);
		column = new Column();
		column.setAlign("center");
		columns.appendChild(column);
		grid.appendChild(columns);
		rows = new Rows();
		row = new Row();
		
		Button button = new Button("Continuar", "resources/mp_anular.png");
		button.setClass("btnCommandM");
		button.addEventListener(Events.ON_CLICK, new EventListener<Event>() {
			@Override
			public void onEvent(Event e){
				if(txtMotivoAnulacion.getText().trim().isEmpty()){
					DlgMessage.information(Messages.getString("WndLiquidacionDiariaVentas.information.noMotivoAnulacion"), txtMotivoAnulacion);
					return;
				}
				Messagebox.show(Messages.getString("WndLiquidacionDiariaVentas.information.confirmarAnulacion"), DlgMessage.NOMBREAPLICACION, DlgMessage.BTN_YESNO, Messagebox.QUESTION, new EventListener<Event>() {
					@Override
					public void onEvent(Event e){
						try{
							if(e.getName().equals("onYes")){
								procesarAnulacion(ventaOriginal, wndAnular, txtMotivoAnulacion.getText().trim().toUpperCase());				
							}
						}catch(Exception ex){
							ex.printStackTrace();
							DlgMessage.information(this.getClass().getSimpleName()+" "+ex.getMessage());
						}
					}
				});
			}
		});
		button.setHeight("28px");
		button.setWidth("100px");
		row.appendChild(button);
		button = new Button("Cancelar", "resources/mp_cancelarEnabled.png");
		button.setClass("btnCommandM");
		button.addEventListener(Events.ON_CLICK, new EventListener<Event>() {
			@Override
			public void onEvent(Event e){
				win.onClose();
			}
		});
		button.setHeight("28px");
		button.setWidth("100px");
		button.setFocus(true);
		row.appendChild(button);
		
		rows.appendChild(row);
		
		grid.appendChild(rows);
		win.appendChild(grid);
		return win;
	}
	
	private void procesarAnulacion(VentaPasaje ventaServicioEspecial, Window window, String motivo)throws Exception{		
		List<VentaPasaje>lstVentas= new ArrayList<>();
		if(ventaServicioEspecial!=null){
			VentaPasaje anulacion= ServiceLocator.getVentaPasajesManager().buscarPorId(ventaServicioEspecial.getId());
			anulacion.setObservaciones(motivo);
			lstVentas.add(anulacion);
		}
		
		/*Determina el tipo de anulacion*/
		int tipoAnulacion=0;
		boolean anularMovimiento=true;
		if(rdAnulacionRegular.isChecked())
			tipoAnulacion=Constantes.TIPO_ANULACION_REGULAR;
		else if (rdAnulacionNC.isChecked())
			tipoAnulacion=Constantes.TIPO_ANULACION_NC;
			
		liquidacion = UtilData.estadoLiquidacionUsuario(getUsuario(), getAgencia());
		
		VentasNotas ventasNotas= ServiceLocator.getVentaPasajesManager().procesarAnulacionServicioEspecial(lstVentas, tipoAnulacion, anularMovimiento, this.liquidacion);
		/*Realiza el envio de las nuevas ventas generadas al Servidor F.E.*/
		List<VentaPasaje> lstVentasenviar= new ArrayList<>();
		for(VentaPasaje venta:ventasNotas.getListVentas()){
			VentaPasaje envioVenta=ServiceLocator.getVentaPasajesManager().buscarVentaById(venta.getId());
			lstVentasenviar.add(envioVenta);
		}
		/*	COMENTADO PARA LANZAR EL ENTORNO DE PRUEBAS EN TRANSMAR	*/
//		if(lstVentasenviar.size()>0)
//			WSFE.sendVenta(lstVentasenviar, window, false, null);
//		
//		/*Realiza el envio de las notas de credito generadas al Servidor F.E.*/
//		for(VentaPasaje notaCredito:ventasNotas.getListNotasCredito()){
//			WSFE.sendNota(notaCredito);
//		}

		DlgMessage.information("El Proceso de anulación termino correctamente");
		
		window.onClose();
	}
}
