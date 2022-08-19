/**
 * Proyecto		: SISVYR
 * Sistema		: Sistema de Ventas y Reservas
 * Descripción	: Clase que controla la venta para realizar la reimpresion de boletos.
 * Autor		: José Sullo Avalos
 * Fecha		: 07/02/2013
 */
package com.cystesoft.vyrbus.view.ctrl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zul.Button;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Tab;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

import com.cystesoft.vyrbus.model.bean.VentaPasaje;
import com.cystesoft.vyrbus.service.exceptions.CriteriosBusquedaIncompletosException;
import com.cystesoft.vyrbus.service.exceptions.FechaInicioNullException;
import com.cystesoft.vyrbus.service.exceptions.LiquidacionNullException;
import com.cystesoft.vyrbus.service.exceptions.ManifiestoImpresoException;
import com.cystesoft.vyrbus.service.exceptions.PerdidaServicioException;
import com.cystesoft.vyrbus.service.exceptions.ReimpresionByTipoMovimientoNoPermitidoException;
import com.cystesoft.vyrbus.service.locator.ServiceLocator;
import com.cystesoft.vyrbus.service.util.Constantes;
import com.cystesoft.vyrbus.service.util.Messages;
import com.cystesoft.vyrbus.service.util.Util;
import com.cystesoft.vyrbus.service.util.WSFE;
import com.cystesoft.vyrbus.view.ui.DlgMessage;
import com.cystesoft.vyrbus.view.ui.WndBase;


/**
 * @author Jose
 *
 */
public class WndReimprimirBoleto extends WndBase {
	private static final long serialVersionUID = 1L;
	private Textbox txtPasajero;
	private Textbox txtNumeroDocumento;
	private Datebox dtbxFechaPartida;
	private Listbox lbxVentas;
//	private Window wndReimpresion;
	private Tab tabListado;
	
//	private VentaPasaje boletoReimprimir;
//	private Usuario usuario = null;
//	private Combobox cmbFormaPago = null;
//	private Combobox cmbTipoFormaPago = null;
//	private Combobox cmbOperadorTarjetaCredito = null;
//	private Combobox cmbTarjetaCredito = null;
//	private Combobox cmbTipoComprobante = null;
	
	private Window wndReimprimirBoleto;
	
//	private Date fechaLiquidacion=null;
	
	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.view.ui.WndBase#onCreate()
	 */
	@Override
	public void onCreate() throws Exception {
		try{
						
			/*Valida si el usuario tiene una liquidación aperturada*/
//			if(getDesktop().getSession().getAttribute(Constantes.ATRIBUTO_FECHA_LIQUIDACION)==null)
//				throw new LiquidacionNullException();
			
			super.onCreate();
			
//			fechaLiquidacion = (Date)this.getDesktop().getSession().getAttribute(Constantes.ATRIBUTO_FECHA_LIQUIDACION);
//			
//			usuario = (Usuario)this.getDesktop().getSession().getAttribute(Constantes.ATRIBUTO_USUARIO);
			dtbxFechaPartida.setValue(new Date());
			txtNumeroDocumento.setFocus(true);
		}catch (LiquidacionNullException lnullex){
			DlgMessage.information(Messages.getString("WndVentaReserva.information.noLiquidacion"));
			closeTabWindow();
		}
		
	}
	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.view.ui.WndBase#initComponents()
	 */
	@Override
	public void initComponents() {
		super.initComponents();
		txtPasajero = (Textbox)this.getFellow("txtPasajero");
		txtNumeroDocumento = (Textbox)this.getFellow("txtNumeroDocumento");
		dtbxFechaPartida = (Datebox)this.getFellow("dtbxFechaPartida");
		lbxVentas = (Listbox)this.getFellow("lbxVentas");
		tabListado = (Tab)this.getFellow("tabListado");
		wndReimprimirBoleto=(Window)this.getFellow("wndReimprimirBoleto");
	}
	
	public void buscarBoleto(){
		try{
			lbxVentas.getItems().clear();
			String numeroDocumento = txtNumeroDocumento.getText().trim().equals("")?null:txtNumeroDocumento.getText();
			String fechaPartida = dtbxFechaPartida.getValue()==null?null:Util.DatetoString(dtbxFechaPartida.getValue(), Constantes.DATE_FORMAT);
			String[] pasajero = txtPasajero.getText().trim().equals("")?null:txtPasajero.getText().trim().toUpperCase().split(" ");
			if(fechaPartida==null)
				throw new FechaInicioNullException();
			else if(numeroDocumento==null && pasajero==null)
				throw new CriteriosBusquedaIncompletosException();
			
			List<VentaPasaje> lstVentaPasajes = ServiceLocator.getVentaPasajesManager().buscarBoletosReimprimir(numeroDocumento, pasajero, fechaPartida);
			Listcell cell = null;
			if(lstVentaPasajes.size()>0){
				for(VentaPasaje venta : lstVentaPasajes){
					Listitem listitem = new Listitem();
					cell = new Listcell(venta.getId().toString());
					listitem.appendChild(cell);
					cell = new Listcell(venta.getTipoMovimiento().getDenominacion());
					listitem.appendChild(cell);
					cell = new Listcell(venta.getRuta().getOrigen()+" - "+venta.getRuta().getDestino());
					listitem.appendChild(cell);
					cell = new Listcell(venta.getPasajero().getNombresApellidos());
					listitem.appendChild(cell);
					cell = new Listcell(Util.DatetoString(venta.getFechaPartida(), Constantes.DATE_FORMAT));
					listitem.appendChild(cell);
					cell = new Listcell(venta.getHoraPartida());
					listitem.appendChild(cell);
					cell = new Listcell(venta.getNumeroControl());
					listitem.appendChild(cell);
					cell = new Listcell(Util.toNumberFormat(venta.getImportePagado(), 2));
					listitem.appendChild(cell);
					cell = new Listcell();
					Button btnReimprimir = new Button("Reimprimir", "resources/menu/menu_reimprimir.png");
					btnReimprimir.setAutodisable("self");
					btnReimprimir.addEventListener(Events.ON_CLICK, new EventListener<Event>() {
						@Override
						public void onEvent(Event e){
							reimprimir(e.getTarget().getId());
						}
					});
					btnReimprimir.setId(venta.getId().toString());
					btnReimprimir.setStyle("font-size:11px;");
					btnReimprimir.setClass("btnCommandL");
					cell.appendChild(btnReimprimir);
					listitem.appendChild(cell);
					listitem.setValue(venta);
					lbxVentas.appendChild(listitem);
				}
				tabListado.setSelected(true);
			}else
				DlgMessage.information(Messages.getString("WndPostergacion.information.noVentas"));
			
		}catch(FechaInicioNullException finex){
			DlgMessage.information(Messages.getString("WndReimprimirBoleto.information.fechaPartida"), dtbxFechaPartida);
		}catch(CriteriosBusquedaIncompletosException cbnex){
			DlgMessage.information(Messages.getString("WndReimprimirBoleto.information.criteriosBusqueda"), txtNumeroDocumento);
		}catch(Exception ex){
			DlgMessage.error(this.getClass().getSimpleName()+" "+ex.getMessage());
		}
	}
	
	private void reimprimir(String idVenta){
		try{
//			boletoReimprimir = null;
			final VentaPasaje ventaOriginal = ServiceLocator.getVentaPasajesManager().buscarVentaById(Long.valueOf(idVenta));
			if(ventaOriginal.getTipoMovimiento().getId().intValue()==Constantes.ID_TIPMOV_ANULACION_SISTEMA)
				throw new ReimpresionByTipoMovimientoNoPermitidoException(Constantes.ID_TIPMOV_ANULACION_SISTEMA);
			if(ventaOriginal.getTipoMovimiento().getId().intValue()==Constantes.ID_TIPMOV_ANULACION)
				throw new ReimpresionByTipoMovimientoNoPermitidoException(Constantes.ID_TIPMOV_ANULACION_SISTEMA);
			else if(ventaOriginal.getTipoMovimiento().getId().intValue()==Constantes.ID_TIPMOV_DEVOLUCION)
				throw new ReimpresionByTipoMovimientoNoPermitidoException(Constantes.ID_TIPMOV_DEVOLUCION);
			else if (ServiceLocator.getDetalleManifiestoManager().validarVentaManifiesto(Long.valueOf(idVenta))){
				Date dateSys=Constantes.FORMAT_DATE.parse(ServiceLocator.getVentaPasajesManager().getDateSystem());
				Date fechaPartida=Constantes.FORMAT_DATE.parse(Constantes.FORMAT_DATE.format(ventaOriginal.getFechaPartida()));
				if(fechaPartida.getTime() < dateSys.getTime())
					throw new ManifiestoImpresoException();
			}else if(ventaOriginal.getTipoTransaccion().equals(Constantes.TIPO_OPERACION_PERDIDA_SERVICIO))
				throw new PerdidaServicioException();
//			else if(ventaOriginal.getManifiesto()!=null)
//				throw new ManifiestoImpresoException();
			
			/*###End Begin 27/10/2016 - jabantp*/
//			UsuarioHardware usuarioHardware = (UsuarioHardware) this.getDesktop().getSession().getAttribute(Constantes.ATRIBUTO_USUARIO_HARDWARE);
//			final String boleto = UtilData.buscarEspecieValorada(Constantes.ID_TIPCOM_BOLETO_VIAJE, usuarioHardware.getId());
//			wndReimpresion = createVentanaReimpresion(ventaOriginal, boleto);
//			this.appendChild(wndReimpresion);
//			wndReimpresion.setMode(MODAL);
			Messagebox.show(Messages.getString("WndReimprimirBoleto.question.reimprimir"), DlgMessage.NOMBREAPLICACION, DlgMessage.BTN_YESNO, Messagebox.QUESTION,DlgMessage.BTN_DEFAULT_NO, new EventListener<Event>() {
				@Override
				public void onEvent(Event e){
					try {
						if(e.getName().equals("onYes")){
							List<VentaPasaje>listVentas= new ArrayList<>();
							listVentas.add(ventaOriginal);
							WSFE.reimprimirComprobante(listVentas, wndReimprimirBoleto);
						}
					} catch (Exception e2) {
						e2.printStackTrace();
						DlgMessage.error(e2.getMessage());
					}
				}
			});
			
			
		}catch(ReimpresionByTipoMovimientoNoPermitidoException rtmnpex){
			if(rtmnpex.getTipoMovimiento().intValue()==Constantes.ID_TIPMOV_ANULACION_SISTEMA)
				DlgMessage.information(Messages.getString("WndReimprimirBoleto.information.reimpresionByAnulacionNoPermitido"));
			else if(rtmnpex.getTipoMovimiento().intValue()==Constantes.ID_TIPMOV_DEVOLUCION)
				DlgMessage.information(Messages.getString("WndReimprimirBoleto.information.reimpresionByDevolucionNoPermitido"));
		}catch(ManifiestoImpresoException miex){
			DlgMessage.information(Messages.getString("Generales.information.manifiestoImpreso"));
		}catch(PerdidaServicioException psex) {
			DlgMessage.information(Messages.getString("WndPerdidaServicio.information.noReimpresionBoletoPerdidaServicio"));
		}catch(Exception ex){
			DlgMessage.error(this.getClass().getSimpleName()+" "+ex.getMessage());
		}
	}
	
//	@SuppressWarnings("deprecation")
//	private Window createVentanaReimpresion(final VentaPasaje ventaOriginal, final String boleto)throws Exception{
//		
//		Caption caption = null;
//		Groupbox groupbox = null;
//		Grid grid = new Grid();
//		Columns columns = new Columns();
//		Column column = null;
//		Rows rows = new Rows();
//		Row row = null;
//		Label label = null;
//		Textbox text = null;
//		
//		final Window win = new Window("", "normal", true);
//		win.setWidth("500px");
//		
//		caption = new Caption("REIMPRESION COMPROBANTES ELECTRONICOS", "resources/menu/menu_reimprimir.png");
//		win.appendChild(caption);
//		label = new Label("Se va a realizar la Reimpresion con los siguientes datos :");
//		label.setStyle("font-size:12px !important");
//		win.appendChild(label);
//		
//		win.appendChild(new Separator("horizontal"));
//		
//		groupbox = new Groupbox();
//		groupbox.setClosable(false);
//		caption = new Caption("Información del comprobante");
//		groupbox.appendChild(caption);
//		
//		/*	Columna 1	*/
//		column = new Column();
//		column.setAlign("right");
//		columns.appendChild(column);
//		/*	Columna 2	*/
//		column = new Column();
//		columns.appendChild(column);
//		/*	Columna 3	*/
//		column = new Column();
//		column.setAlign("right");
//		columns.appendChild(column);
//		/*	Columna 4	*/
//		column = new Column();
//		columns.appendChild(column);
//		
//		grid.appendChild(columns);
//		
//		row = new Row();		
//		label = new Label(ventaOriginal.getTipoComprobante().getId().intValue()==Constantes.ID_TIPCOM_BOLETA_VENTA?"NUEVA BOLETA :":"NUEVA FACTURA :");
//		row.appendChild(label);		
//		text = new Textbox(boleto);
//		text.setReadonly(true);
//		text.setWidth("80px");
//		row.appendChild(text);		
//		label = new Label("FECHA VIAJE :");
//		row.appendChild(label);		
//		text = new Textbox(ventaOriginal.getFechaPartida()==null?"":Util.DatetoString(ventaOriginal.getFechaPartida(), Constantes.DATE_FORMAT));
//		text.setReadonly(true);
//		text.setWidth("100px");
//		row.appendChild(text);
//		rows.appendChild(row);
//		
//		row = new Row();
//		label = new Label("NUMERO ASIENTO :");
//		row.appendChild(label);
//		text = new Textbox(ventaOriginal.getNumeroAsiento()==null?"":ventaOriginal.getNumeroAsiento().toString());
//		text.setReadonly(true);
//		text.setWidth("80px");
//		row.appendChild(text);
//		label = new Label("IMPORTE :");
//		row.appendChild(label);
//		text = new Textbox(Util.toNumberFormat(Constantes.PENALIDAD_REIMPRESION, 2));
//		text.setStyle("align:right");
//		text.setReadonly(true);
//		text.setWidth("100px");
//		row.appendChild(text);
//		rows.appendChild(row);
//		
//		row = new Row();
//		row.setSpans("1,3");
//		label = new Label("PASAJERO :");
//		row.appendChild(label);
//		text = new Textbox(ventaOriginal.getPasajero().getNombresApellidos());
//		text.setReadonly(true);
//		text.setWidth("96%");
//		row.appendChild(text);
//		rows.appendChild(row);
//		
//		row = new Row();
//		row.setSpans("1,3");
//		label = new Label("CLIENTE :");
//		row.appendChild(label);
//		text = new Textbox(ventaOriginal.getCliente()!=null?ventaOriginal.getCliente().getRazonSocial():"");
//		text.setReadonly(true);
//		text.setWidth("96%");
//		row.appendChild(text);
//		rows.appendChild(row);
//		
//		row = new Row();
//		label = new Label("RUTA :");
//		row.appendChild(label);
//		text = new Textbox(ventaOriginal.getRuta().getOrigen()+" - "+ventaOriginal.getRuta().getDestino());
//		text.setReadonly(true);
//		text.setWidth("110px");
//		row.appendChild(text);
//		label = new Label("SERVICIO :");
//		row.appendChild(label);
//		text = new Textbox(ventaOriginal.getServicio().getDenominacion());
//		text.setReadonly(true);
//		text.setWidth("100px");
//		row.appendChild(text);
//		rows.appendChild(row);
//		
//		grid.appendChild(rows);
//		groupbox.appendChild(grid);
//		win.appendChild(groupbox);
//		/* ***************************************** */
//		groupbox = new Groupbox();
//		groupbox.setClosable(false);
//		caption = new Caption("Informacion del Pago");
//		groupbox.appendChild(caption);
//		
//		grid = new Grid();
//		rows = new Rows();
//		columns = new Columns();
//		/*	Columna 1	*/
//		column = new Column();
//		column.setAlign("right");
//		column.setWidth("120px");
//		columns.appendChild(column);
//		/*	Columna 2	*/
//		column = new Column();
//		columns.appendChild(column);
//		
//		grid.appendChild(columns);
//		
//		row = new Row();		
//		label = new Label("TIPO  COMPROBANTE :");
//		row.appendChild(label);		
//		cmbTipoComprobante = new Combobox();
//		cmbTipoComprobante.setWidth("120px");
//		TreeMap<String, Object> criteriosBusqueda = new TreeMap<String, Object>();
//		criteriosBusqueda.put("rubro", TipoComprobante.RUBRO_PASAJES);
//		UtilData.cargarDataCombo(cmbTipoComprobante, TipoComprobante.class, criteriosBusqueda, false);
//		row.appendChild(cmbTipoComprobante);		
//		rows.appendChild(row);
//		
//		row = new Row();
//		label = new Label("FORMA DE PAGO :");
//		row.appendChild(label);		
//		cmbFormaPago = new Combobox();
//		cmbFormaPago.setWidth("120px");
//		UtilData.cargarDataCombo(cmbFormaPago, FormaPago.class, false);
//		row.appendChild(cmbFormaPago);		
//		rows.appendChild(row);
//		
//		row = new Row();
//		label = new Label("TIPO FORMA DE PAGO :");
//		row.appendChild(label);		
//		cmbTipoFormaPago = new Combobox();
//		cmbTipoFormaPago.addEventListener(Events.ON_CHANGE, new EventListener<Event>() {
//			public void onEvent(Event e){
//				onValidateTipoFormaPago();
//			}
//		});
//		cmbTipoFormaPago.setWidth("120px");
//		row.appendChild(cmbTipoFormaPago);		
//		rows.appendChild(row);
//		
//		row = new Row();
//		label = new Label("OPERADOR TARJETA CREDITO :");
//		row.appendChild(label);		
//		cmbOperadorTarjetaCredito = new Combobox();
//		cmbOperadorTarjetaCredito.addEventListener(Events.ON_CHANGE, new EventListener<Event>() {
//			public void onEvent(Event e){
//				onLoadTarjetas();
//			}
//		});
//		cmbOperadorTarjetaCredito.setWidth("120px");
//		cmbOperadorTarjetaCredito.setDisabled(true);
//		row.appendChild(cmbOperadorTarjetaCredito);		
//		rows.appendChild(row);
//		
//		row = new Row();
//		label = new Label("TARJETA CREDITO :");
//		row.appendChild(label);		
//		cmbTarjetaCredito = new Combobox();
//		cmbTarjetaCredito.setWidth("120px");
//		cmbTarjetaCredito.setDisabled(true);
//		row.appendChild(cmbTarjetaCredito);		
//		rows.appendChild(row);
//		
//		grid.appendChild(rows);
//		
//		groupbox.appendChild(grid);
//		win.appendChild(groupbox);
//		
//		onSelectDefaultTipoComprobante();
//		onSelectDefaultFormaPago();
//		
//		grid = new Grid();
//		columns = new Columns();
//		column = new Column();
//		column.setAlign("center");
//		columns.appendChild(column);
//		column = new Column();
//		column.setAlign("center");
//		columns.appendChild(column);
//		grid.appendChild(columns);
//		rows = new Rows();
//		row = new Row();
//
//		Button button = new Button("Continuar", "resources/mp_aceptarEnabled.png");
//		button.addEventListener(Events.ON_CLICK, new EventListener<Event>() {
//			public void onEvent(Event e){
//				try{
//					if(!(cmbTipoFormaPago.getSelectedItem().getValue() instanceof TipoFormaPago))
//						throw new TipoFormaPagoNullException();
//					else if(cmbTipoFormaPago.getSelectedItem().getValue() instanceof TipoFormaPago && cmbTipoFormaPago.getText().equals(TipoFormaPago.TIPO_TARJETA)){
//						if(!(cmbOperadorTarjetaCredito.getSelectedItem().getValue() instanceof OperadorTarjetaCredito))
//							throw new OperadorTarjetaCreditoNullException();
//						else if(!(cmbTarjetaCredito.getSelectedItem().getValue() instanceof TarjetaCredito))
//							throw new TarjetaCreditoNullException();
//					}
//					
//					Messagebox.show(Messages.getString("WndReimprimirBoleto.question.confirmarReimpresion"), DlgMessage.NOMBREAPLICACION, DlgMessage.BTN_YESNO, Messagebox.QUESTION, new EventListener<Event>() {
//						public void onEvent(Event e){
//							try{
//								if(e.getName().equals("onYes")){
//									//Clonamos la venta original para luego a este actualizar el campo venta pasaje referencial (VENPAS_IDREF) 12/12/2013 jabanto
//									VentaPasaje ventaPasajereRef= (VentaPasaje) ventaOriginal.clone();
//									UtilData.auditarRegistro(ventaPasajereRef,true, usuario, Executions.getCurrent());
//									//----->>>									
//																		
//									ventaOriginal.setImportePagadoEfectivo(0.0);
//									ventaOriginal.setImportePagadoTarjeta(0.0);
//									boletoReimprimir = (VentaPasaje)ventaOriginal.clone();
//									ventaOriginal.setId(null);
//									ventaOriginal.setVentaPasaje(null);
//									ventaOriginal.setTipoMovimiento(new TipoMovimiento(Constantes.ID_TIPMOV_ANULACION_SISTEMA));
//									ventaOriginal.setUsuario(getUsuario());
//									ventaOriginal.setFechaLiquidacion(fechaLiquidacion);
//									ventaOriginal.setAgencia(getAgencia());
//									UtilData.auditarRegistro(ventaOriginal, usuario, Executions.getCurrent());
//									
//									boletoReimprimir.setId(null);
//									boletoReimprimir.setRucClienteCredito(null);//A solicitud de marco 28/09/2015
//									boletoReimprimir.setAgencia(getAgencia());
//									boletoReimprimir.setUsuario(getUsuario());
//									boletoReimprimir.setFechaLiquidacion(fechaLiquidacion);
////									boletoReimprimir.setVentaPasaje(ventaOriginal);
//									boletoReimprimir.setVentaPasaje(ventaPasajereRef);
//									boletoReimprimir.setNumeroBoletoAnterior(ventaOriginal.getNumeroBoleto());
//									boletoReimprimir.setNumeroBoleto(boleto);
//									boletoReimprimir.setPenalidad(Constantes.PENALIDAD_REIMPRESION);
//									boletoReimprimir.setImportePagado(Constantes.PENALIDAD_REIMPRESION);
//									boletoReimprimir.setImportePagadoEfectivo(0.0);
//									boletoReimprimir.setImportePagadoTarjeta(0.0);
//									boletoReimprimir.setTipoMovimiento(new TipoMovimiento(Constantes.ID_TIPMOV_REIMPRESION));
//									boletoReimprimir.setUsuarioHardware((UsuarioHardware)getDesktop().getSession().getAttribute(Constantes.ATRIBUTO_USUARIO_HARDWARE));
//									boletoReimprimir.setFormaPago(new FormaPago(Constantes.ID_FORPAG_CONTADO));
//									boletoReimprimir.setEstadoRegistro(Constantes.VALUE_ACTIVO);
//									TipoFormaPago tipoFormaPago = (TipoFormaPago)cmbTipoFormaPago.getSelectedItem().getValue();
//									boletoReimprimir.setTipoFormaPago(tipoFormaPago);
//									/*	Si ha seleccionado algun tipo de Tarjeta de Credito	*/
//									if(cmbTarjetaCredito.getSelectedItem()!=null && cmbTarjetaCredito.getSelectedItem().getValue() instanceof TarjetaCredito){
//										TarjetaCredito tarjetaCredito = (TarjetaCredito)cmbTarjetaCredito.getSelectedItem().getValue();
//										boletoReimprimir.setTarjetaCredito(tarjetaCredito);
//									}
//									boletoReimprimir.setLiquidacion(null);
//									boletoReimprimir.setFechaTransferencia(null);
//									boletoReimprimir.setEstadoDocumento(Constantes.ESTADO_DOCUMENTO_PAGADO);
//									UtilData.auditarRegistro(boletoReimprimir, usuario, Executions.getCurrent());
//									int result = ServiceLocator.getVentaPasajesManager().reimprimirBoleto(ventaOriginal, boletoReimprimir);
//									if(result==Constantes.CORRECT){
//										boletoReimprimir = ServiceLocator.getVentaPasajesManager().buscarVentaById(boletoReimprimir.getId());
//										
//										/*Implementacion para el nueno formato 01/02/2016 - jabanto */
//										boolean formato=UtilData.getFormatoImprecion(getAgencia().getId(), getTipocomprobante().getId(), getUsuarioHardware().getId());
//										File file= CreateDocument.crearBoleto(boletoReimprimir,formato);
//										
//										if(getUsuarioHardware().getPrintApplet().intValue()==Constantes.TRUE_VALUE){
////											String fileBoleto = Constantes.URL_FORMATOS_BOLETOS + boletoReimprimir.getNumeroControl()+".txt";
//											String fileBoleto = Constantes.URL_FORMATOS_BOLETOS +Constantes.CLAVE_PAHT+ boletoReimprimir.getNumeroControl()+".txt";
//											Window win = (Window)Executions.createComponents("imprimir.zul", null, null);
//											win.setAttribute("formato", WndImprimir.FORMAT_BOLETO);
//											win.setAttribute("msg", "Reimprimiendo el Boleto de Viaje... ");
//											win.setAttribute("urlDocumento", fileBoleto);
//											win.doPopup();
//											DlgMessage.information(Messages.getString("WndReimprimirBoleto.information.exitoReimpresion")+" "+boletoReimprimir.getNumeroControl());
//											wndReimpresion.onClose();
//
//										}else{
//											//Descarga el archivo para la impresion
//											Util.descargarArchivo(file);
//										}
//										buscarBoleto();
//									}								
//								}
//							}catch(NumeroBoletoDuplicadoException nbdex){
//								DlgMessage.information(Messages.getString("WndReimprimirBoleto.information.numeroBoletoVendido"));
//							}catch(Exception ex){
//								ex.printStackTrace();
//								DlgMessage.information(this.getClass().getSimpleName()+" "+ex.getMessage());
//							}
//						}
//					});
//				}catch(TipoFormaPagoNullException tfpnex){
//					DlgMessage.information(Messages.getString("WndVentaReserva.information.noTipoFormaPago"), cmbTipoFormaPago);
//				}catch(OperadorTarjetaCreditoNullException otcnex){
//					DlgMessage.information(Messages.getString("WndVentaReserva.information.noOperadorTarjetaCredito"), cmbOperadorTarjetaCredito);
//				}catch(TarjetaCreditoNullException tcnex){
//					DlgMessage.information(Messages.getString("WndVentaReserva.information.noTarjetaCredito"), cmbTarjetaCredito);
//				}catch(Exception ex){
//					DlgMessage.information(this.getClass().getSimpleName()+" "+ex.getMessage());
//				}
//			}
//		});
//		button.setHeight("28px");
//		row.appendChild(button);
//		button = new Button("Cancelar", "resources/mp_cancelarEnabled.png");
//		button.addEventListener(Events.ON_CLICK, new EventListener<Event>() {
//			public void onEvent(Event e){
//				win.onClose();
//			}
//		});
//		button.setHeight("28px");
//		button.setFocus(true);
//		row.appendChild(button);
//		
//		rows.appendChild(row);
//		
//		grid.appendChild(rows);
//		win.appendChild(grid);
//		return win;
//	}
	
//	/**
//	 * Para seleccionar el Tipo de Comprobante por defecto
//	 */
//	private void onSelectDefaultTipoComprobante(){
//		for(Comboitem comboitem : cmbTipoComprobante.getItems()){
//			/*	Si la agencia pertenece a TEPSA*/
//			if(comboitem.getValue() instanceof TipoComprobante && ((TipoComprobante)comboitem.getValue()).getId().intValue()==Constantes.ID_TIPCOM_BOLETO_VIAJE)
//				cmbTipoComprobante.setSelectedItem(comboitem);			
//		}
//		cmbTipoComprobante.setDisabled(true);
//	}
//	
//	/**
//	 * Selecciona por defecto el item del Combo Forma de Pago.
//	 */
//	private void onSelectDefaultFormaPago(){
//		/*	Seleccionamos por defecto la Forma de pago	*/
//		for(Comboitem comboitem : cmbFormaPago.getItems()){
//			/*	Si la agencia pertenece a TEPSA*/
//			if(comboitem.getValue() instanceof FormaPago && ((FormaPago)comboitem.getValue()).getId().intValue()==Constantes.ID_FORPAG_CONTADO){
//				cmbFormaPago.setSelectedItem(comboitem);
//				onLoadTipoFormaPago();
//				for(Comboitem item : cmbTipoFormaPago.getItems()){
//					if(item.getValue() instanceof TipoFormaPago && ((TipoFormaPago)item.getValue()).getId().intValue()==Constantes.ID_TIPFORPAG_EFECTIVO)
//						cmbTipoFormaPago.setSelectedItem(item);
//				}
//			}			
//		}
//		cmbFormaPago.setDisabled(true);
//	}
	
//	/**
//	 * Carga los tipos de Forma de pago.
//	 */
//	public void onLoadTipoFormaPago(){
//		try{
//			cmbTipoFormaPago.getItems().clear();
//			if(cmbFormaPago.getSelectedItem().getValue() instanceof FormaPago){
//				FormaPago formaPago = cmbFormaPago.getSelectedItem().getValue();
//				TreeMap<String, Object> criteriosBusqueda = new TreeMap<String, Object>();
//				criteriosBusqueda.put("formaPago.id", formaPago.getId());
//				List<String> criteriosOrdenar = new ArrayList<String>();
//				criteriosOrdenar.add("denominacion");
//				List<TipoFormaPago> lstTipoFormasPago = ServiceLocator.getTipoFormaPagoManager().buscarPorX(criteriosBusqueda, criteriosOrdenar);
//				UtilData.cargarGenericData(cmbTipoFormaPago, false);
//				for(TipoFormaPago tipoFormaPago : lstTipoFormasPago){
//					Comboitem item = new Comboitem();
//					item.setLabel(tipoFormaPago.getDenominacion());
//					item.setValue(tipoFormaPago);
//					cmbTipoFormaPago.appendChild(item);
//				}
//				cmbTipoFormaPago.setDisabled(false);
//			}else{
//				cmbTipoFormaPago.setDisabled(true);
//				cmbTipoFormaPago.getItems().clear();
//				cmbTipoFormaPago.setText("");
//				cmbOperadorTarjetaCredito.setDisabled(true);
//				cmbOperadorTarjetaCredito.getItems().clear();
//				cmbOperadorTarjetaCredito.setText("");
//				cmbTarjetaCredito.setDisabled(true);
//				cmbTarjetaCredito.getItems().clear();
//				cmbTarjetaCredito.setText("");
//			}
//		}catch(Exception ex){
//			DlgMessage.error(this.getClass().getSimpleName()+" "+ex.getMessage());
//		}
//	}
	
//	/**
//	 * Realiza una validación del Tipo de Forma de Pago, para habilitar o deshabilitar algunos controles.
//	 * @throws Exception 
//	 */
//	public void onValidateTipoFormaPago(){
//		try{
//			if(cmbTipoFormaPago.getSelectedItem().getValue() instanceof TipoFormaPago){ 
//				/*	Si es tarjeta cargamos los operadores de tarjeta de credito	*/
//				if(cmbTipoFormaPago.getText().equals("TARJETA")){
//					cmbOperadorTarjetaCredito.getItems().clear();
//					UtilData.cargarDataCombo(cmbOperadorTarjetaCredito, OperadorTarjetaCredito.class, false);
//					cmbOperadorTarjetaCredito.setDisabled(false);
//				}else{
//					cmbOperadorTarjetaCredito.setDisabled(true);
//					cmbOperadorTarjetaCredito.getItems().clear();
//					cmbOperadorTarjetaCredito.setText("");
//					cmbTarjetaCredito.setDisabled(true);
//					cmbTarjetaCredito.getItems().clear();
//					cmbTarjetaCredito.setText("");
//				}
//			}else{
//				cmbOperadorTarjetaCredito.setDisabled(true);
//				cmbOperadorTarjetaCredito.getItems().clear();
//				cmbOperadorTarjetaCredito.setText("");
//				cmbTarjetaCredito.setDisabled(true);
//				cmbTarjetaCredito.getItems().clear();
//				cmbTarjetaCredito.setText("");
//			}
//		}catch(Exception ex){
//			DlgMessage.error(this.getClass().getSimpleName()+" "+ex.getMessage());
//		}
//	}
	
//	/**
//	 * Carga los diferentes tarjetas de credito, de acuerdo al operador seleccionado.
//	 */
//	public void onLoadTarjetas(){
//		try{
//			cmbTarjetaCredito.getItems().clear();
//			cmbTarjetaCredito.setText("");
//			if(cmbOperadorTarjetaCredito.getSelectedItem().getValue() instanceof OperadorTarjetaCredito){
//				OperadorTarjetaCredito operadorTarjetaCredito = cmbOperadorTarjetaCredito.getSelectedItem().getValue();
//				TreeMap<String, Object> criteriosBusqueda = new TreeMap<String, Object>();
//				criteriosBusqueda.put("operadorTarjetaCredito.id", operadorTarjetaCredito.getId());
//				List<String> criteriosOrdenar = new ArrayList<String>();
//				criteriosOrdenar.add("denominacion");
//				List<TarjetaCredito> lstTarjetaCredito = ServiceLocator.getTarjetaCreditoManager().buscarPorX(criteriosBusqueda, criteriosOrdenar);
//				UtilData.cargarGenericData(cmbTarjetaCredito, false);
//				for(TarjetaCredito tarjetaCredito : lstTarjetaCredito){
//					Comboitem item = new Comboitem();
//					item.setLabel(tarjetaCredito.getDenominacion());
//					item.setValue(tarjetaCredito);
//					cmbTarjetaCredito.appendChild(item);
//				}
//				cmbTarjetaCredito.setDisabled(false);
//			}else
//				cmbTarjetaCredito.setDisabled(true);
//		}catch(Exception ex){
//			DlgMessage.error(this.getClass().getSimpleName()+" "+ex.getMessage());
//			ex.printStackTrace();
//		}
//	}
}

