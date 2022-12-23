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
import java.util.TreeMap;

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
import org.zkoss.zul.Grid;
import org.zkoss.zul.Groupbox;
import org.zkoss.zul.Label;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Row;
import org.zkoss.zul.Rows;
import org.zkoss.zul.Separator;
import org.zkoss.zul.Tab;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

import com.cystesoft.vyrbus.model.bean.Cliente;
import com.cystesoft.vyrbus.model.bean.ControlEspecieValorada;
import com.cystesoft.vyrbus.model.bean.EspecieValorada;
import com.cystesoft.vyrbus.model.bean.FormaPago;
import com.cystesoft.vyrbus.model.bean.Itinerario;
import com.cystesoft.vyrbus.model.bean.OperadorTarjetaCredito;
import com.cystesoft.vyrbus.model.bean.TarjetaCredito;
import com.cystesoft.vyrbus.model.bean.TipoComprobante;
import com.cystesoft.vyrbus.model.bean.TipoFormaPago;
import com.cystesoft.vyrbus.model.bean.TipoMovimiento;
import com.cystesoft.vyrbus.model.bean.TipoNota;
import com.cystesoft.vyrbus.model.bean.Usuario;
import com.cystesoft.vyrbus.model.bean.UsuarioHardware;
import com.cystesoft.vyrbus.model.bean.VentaPasaje;
import com.cystesoft.vyrbus.service.exceptions.CriteriosBusquedaIncompletosException;
import com.cystesoft.vyrbus.service.exceptions.EspecieValoradaNotAvailableException;
import com.cystesoft.vyrbus.service.exceptions.FechaInicioNullException;
import com.cystesoft.vyrbus.service.exceptions.LiquidacionNullException;
import com.cystesoft.vyrbus.service.exceptions.ManifiestoImpresoException;
import com.cystesoft.vyrbus.service.exceptions.NumeroBoletoDuplicadoException;
import com.cystesoft.vyrbus.service.exceptions.OperadorTarjetaCreditoNullException;
import com.cystesoft.vyrbus.service.exceptions.PerdidaServicioException;
import com.cystesoft.vyrbus.service.exceptions.ReimpresionByTipoMovimientoNoPermitidoException;
import com.cystesoft.vyrbus.service.exceptions.TarjetaCreditoNullException;
import com.cystesoft.vyrbus.service.exceptions.TipoFormaPagoNullException;
import com.cystesoft.vyrbus.service.locator.ServiceLocator;
import com.cystesoft.vyrbus.service.util.Constantes;
import com.cystesoft.vyrbus.service.util.Messages;
import com.cystesoft.vyrbus.service.util.Util;
import com.cystesoft.vyrbus.service.util.UtilData;
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
	private Window wndReimpresion;
	private Tab tabListado;
	
//	private VentaPasaje boletoReimprimir;
//	private Usuario usuario = null;
	private Combobox cmbFormaPago = null;
	private Combobox cmbTipoFormaPago = null;
	private Combobox cmbOperadorTarjetaCredito = null;
	private Combobox cmbTarjetaCredito = null;
	private Combobox cmbTipoComprobante = null;

	private UsuarioHardware usuarioHardware = null;
//	private Window wndReimprimirBoleto;
	private Date fechaLiquidacion=null;

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.view.ui.WndBase#onCreate()
	 */
	@Override
	public void onCreate() throws Exception {
		try{

			/*Valida si el usuario tiene una liquidación aperturada*/			
			if(getDesktop().getSession().getAttribute(Constantes.ATRIBUTO_FECHA_LIQUIDACION)==null)
				throw new LiquidacionNullException();
			
			super.onCreate();
			usuarioHardware = (UsuarioHardware) this.getDesktop().getSession().getAttribute(Constantes.ATRIBUTO_USUARIO_HARDWARE);			
			fechaLiquidacion = (Date)this.getDesktop().getSession().getAttribute(Constantes.ATRIBUTO_FECHA_LIQUIDACION);
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
//		wndReimprimirBoleto=(Window)this.getFellow("wndReimprimirBoleto");
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

			/*###End Begin 17/11/2022 - jabanto*/			
			ControlEspecieValorada controlEspecieValorada = UtilData.buscarEspecieValoradaByCaja(ventaOriginal.getTipoComprobante().getId(), getAgencia(), false, usuarioHardware, null);
			final String boleto = (controlEspecieValorada!=null?controlEspecieValorada.toString():"-");
			wndReimpresion = createVentanaReimpresion(ventaOriginal, boleto);
			this.appendChild(wndReimpresion);
			wndReimpresion.setMode(MODAL);
//			Messagebox.show(Messages.getString("WndReimprimirBoleto.question.reimprimir"), DlgMessage.NOMBREAPLICACION, DlgMessage.BTN_YESNO, Messagebox.QUESTION,DlgMessage.BTN_DEFAULT_NO, new EventListener<Event>() {
//				@Override
//				public void onEvent(Event e){
//					try {
//						if(e.getName().equals("onYes")){
//							List<VentaPasaje>listVentas= new ArrayList<>();
//							listVentas.add(ventaOriginal);
//							WSFE.reimprimirComprobante(listVentas, wndReimprimirBoleto);
//						}
//					} catch (Exception e2) {
//						e2.printStackTrace();
//						DlgMessage.error(e2.getMessage());
//					}
//				}
//			});


		}catch(ReimpresionByTipoMovimientoNoPermitidoException rtmnpex){
			if(rtmnpex.getTipoMovimiento().intValue()==Constantes.ID_TIPMOV_ANULACION_SISTEMA)
				DlgMessage.information(Messages.getString("WndReimprimirBoleto.information.reimpresionByAnulacionNoPermitido"));
			else if(rtmnpex.getTipoMovimiento().intValue()==Constantes.ID_TIPMOV_DEVOLUCION)
				DlgMessage.information(Messages.getString("WndReimprimirBoleto.information.reimpresionByDevolucionNoPermitido"));
		}catch(ManifiestoImpresoException miex){
			DlgMessage.information(Messages.getString("Generales.information.manifiestoImpreso"));
		}catch(PerdidaServicioException psex) {
			DlgMessage.information(Messages.getString("WndPerdidaServicio.information.noReimpresionBoletoPerdidaServicio"));
		}catch (EspecieValoradaNotAvailableException eva) {
			DlgMessage.information(Messages.getString("UtilData.information.notAvailableEspecieValorada"));		
		}catch(Exception ex){
			ex.printStackTrace();
			DlgMessage.error(this.getClass().getSimpleName()+" "+ex.getMessage());
		}
	}

	
	
	
	@SuppressWarnings("deprecation")
	private Window createVentanaReimpresion(final VentaPasaje ventaOriginal, final String boleto)throws Exception{

		Caption caption = null;
		Groupbox groupbox = null;
		Grid grid = new Grid();
		Columns columns = new Columns();
		Column column = null;
		Rows rows = new Rows();
		Row row = null;
		Label label = null;
		Textbox text = null;	
		
		final TipoNota penalidadXduplicado = ServiceLocator.getTipoNotaManager().buscarPorId(Long.valueOf(Constantes.ID_TIPNOTA_DUPLICADO));
		
		final Window win = new Window("", "normal", true);
		win.setWidth("500px");

		caption = new Caption("REIMPRESION COMPROBANTES ELECTRONICOS", "resources/menu/menu_reimprimir.png");
		win.appendChild(caption);
		label = new Label("Se va a realizar la Reimpresion con los siguientes datos :");
		label.setStyle("font-size:12px !important");
		win.appendChild(label);

		win.appendChild(new Separator("horizontal"));

		groupbox = new Groupbox();
		groupbox.setClosable(false);
		caption = new Caption("Información del comprobante");
		groupbox.appendChild(caption);

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
		label = new Label(ventaOriginal.getTipoComprobante().getId().intValue()==Constantes.ID_TIPCOM_BOLETA_VENTA?"NUEVA BOLETA :":"NUEVA FACTURA :");
		row.appendChild(label);
		final Textbox txtNuevoComprobante = new Textbox(boleto);
		txtNuevoComprobante.setReadonly(true);
		txtNuevoComprobante.setWidth("80px");
		row.appendChild(txtNuevoComprobante);
		label = new Label("FECHA VIAJE :");
		row.appendChild(label);
		text = new Textbox(ventaOriginal.getFechaPartida()==null?"":Util.DatetoString(ventaOriginal.getFechaPartida(), Constantes.DATE_FORMAT));
		text.setReadonly(true);
		text.setWidth("100px");
		row.appendChild(text);
		rows.appendChild(row);

		row = new Row();
		label = new Label("NUMERO ASIENTO :");
		row.appendChild(label);
		text = new Textbox(ventaOriginal.getNumeroAsiento()==null?"":ventaOriginal.getNumeroAsiento().toString());
		text.setReadonly(true);
		text.setWidth("80px");
		row.appendChild(text);
		label = new Label("PENALIDAD:");
		label.setSclass("label-size12-bold-alert");
		label.setStyle("color:red");
		row.appendChild(label);
		text = new Textbox((penalidadXduplicado!=null? Util.toNumberFormat(penalidadXduplicado.getGastoAdminEfectivo(), 2):"0.00"));		
		text.setSclass("label-size12-bold-alert");
		text.setStyle("color:red");
		text.setReadonly(true);
		text.setWidth("100px");
		row.appendChild(text);
		rows.appendChild(row);

		row = new Row();
		row.setSpans("1,3");
		label = new Label("PASAJERO :");
		row.appendChild(label);
		text = new Textbox(ventaOriginal.getPasajero().getNombresApellidos());
		text.setReadonly(true);
		text.setWidth("96%");
		row.appendChild(text);
		rows.appendChild(row);

		row = new Row();
		row.setSpans("1,3");
		label = new Label("CLIENTE :");
		row.appendChild(label);
		text = new Textbox(ventaOriginal.getCliente()!=null?ventaOriginal.getCliente().getRazonSocial():"");
		text.setReadonly(true);
		text.setWidth("96%");
		row.appendChild(text);
		rows.appendChild(row);

		row = new Row();
		label = new Label("RUTA :");
		row.appendChild(label);
		text = new Textbox(ventaOriginal.getRuta().getOrigen()+" - "+ventaOriginal.getRuta().getDestino());
		text.setReadonly(true);
		text.setWidth("110px");
		row.appendChild(text);
		label = new Label("SERVICIO :");
		row.appendChild(label);
		text = new Textbox(ventaOriginal.getServicio().getDenominacion());
		text.setReadonly(true);
		text.setWidth("100px");
		row.appendChild(text);
		rows.appendChild(row);

		grid.appendChild(rows);
		groupbox.appendChild(grid);
		win.appendChild(groupbox);
		/* ***************************************** */
		groupbox = new Groupbox();
		groupbox.setClosable(false);
		caption = new Caption("Informacion del Pago");
		groupbox.appendChild(caption);

		grid = new Grid();
		rows = new Rows();
		columns = new Columns();
		/*	Columna 1	*/
		column = new Column();
		column.setAlign("right");
		column.setWidth("120px");
		columns.appendChild(column);
		/*	Columna 2	*/
		column = new Column();
		columns.appendChild(column);

		grid.appendChild(columns);

		row = new Row();
		label = new Label("TIPO  COMPROBANTE :");
		row.appendChild(label);
		cmbTipoComprobante = new Combobox();
		cmbTipoComprobante.setWidth("120px");
		TipoComprobante boleta = ServiceLocator.getTipoComprobanteManager().buscarPorId(Long.valueOf(Constantes.ID_TIPCOM_BOLETA_VENTA));
		TipoComprobante factura = ServiceLocator.getTipoComprobanteManager().buscarPorId(Long.valueOf(Constantes.ID_TIPCOM_FACTURA));
		if(boleta !=null) {
			Comboitem comboitem = new Comboitem(boleta.getDenominacion());
			comboitem.setValue(boleta);
			cmbTipoComprobante.appendChild(comboitem);
		}
		if(factura !=null) {
			Comboitem comboitem = new Comboitem(factura.getDenominacion());
			comboitem.setValue(factura);
			cmbTipoComprobante.appendChild(comboitem);
		}		
		Util.seleccionarValorItemCombo(TipoComprobante.class, cmbTipoComprobante, ventaOriginal.getTipoComprobante().getId());
		if(cmbTipoComprobante.getSelectedIndex()<0 && cmbTipoComprobante.getItemCount()>0)
			cmbTipoComprobante.setSelectedIndex(0);		
		row.appendChild(cmbTipoComprobante);
		rows.appendChild(row);

		row = new Row();
		label = new Label("FORMA DE PAGO :");
		row.appendChild(label);
		cmbFormaPago = new Combobox();
		cmbFormaPago.setWidth("120px");
		UtilData.cargarDataCombo(cmbFormaPago, FormaPago.class, false);
		row.appendChild(cmbFormaPago);
		rows.appendChild(row);

		row = new Row();
		label = new Label("TIPO FORMA DE PAGO :");
		row.appendChild(label);
		cmbTipoFormaPago = new Combobox();
		cmbTipoFormaPago.addEventListener(Events.ON_CHANGE, new EventListener<Event>() {
			public void onEvent(Event e){
				onValidateTipoFormaPago();
			}
		});
		cmbTipoFormaPago.setWidth("120px");
		row.appendChild(cmbTipoFormaPago);
		rows.appendChild(row);

		row = new Row();
		label = new Label("OPERADOR TARJETA CREDITO :");
		row.appendChild(label);
		cmbOperadorTarjetaCredito = new Combobox();
		cmbOperadorTarjetaCredito.addEventListener(Events.ON_CHANGE, new EventListener<Event>() {
			public void onEvent(Event e){
				onLoadTarjetas();
			}
		});
		cmbOperadorTarjetaCredito.setWidth("120px");
		cmbOperadorTarjetaCredito.setDisabled(true);
		row.appendChild(cmbOperadorTarjetaCredito);
		rows.appendChild(row);

		row = new Row();
		label = new Label("TARJETA CREDITO :");
		row.appendChild(label);
		cmbTarjetaCredito = new Combobox();
		cmbTarjetaCredito.setWidth("120px");
		cmbTarjetaCredito.setDisabled(true);
		row.appendChild(cmbTarjetaCredito);
		rows.appendChild(row);

		grid.appendChild(rows);

		groupbox.appendChild(grid);
		win.appendChild(groupbox);

		onSelectDefaultTipoComprobante();
		onSelectDefaultFormaPago();

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

		Button button = new Button("Continuar", "resources/mp_aceptarEnabled.png");
		button.setDisabled(penalidadXduplicado==null);
		button.addEventListener(Events.ON_CLICK, new EventListener<Event>() {
			public void onEvent(Event e){
				try{
					if(!(cmbTipoFormaPago.getSelectedItem().getValue() instanceof TipoFormaPago))
						throw new TipoFormaPagoNullException();
					else if(cmbTipoFormaPago.getSelectedItem().getValue() instanceof TipoFormaPago && cmbTipoFormaPago.getText().equals(TipoFormaPago.TIPO_TARJETA)){
						if(!(cmbOperadorTarjetaCredito.getSelectedItem().getValue() instanceof OperadorTarjetaCredito))
							throw new OperadorTarjetaCreditoNullException();
						else if(!(cmbTarjetaCredito.getSelectedItem().getValue() instanceof TarjetaCredito))
							throw new TarjetaCreditoNullException();
					}

					Messagebox.show(Messages.getString("WndReimprimirBoleto.question.confirmarReimpresion"), DlgMessage.NOMBREAPLICACION, DlgMessage.BTN_YESNO, Messagebox.QUESTION, new EventListener<Event>() {
						public void onEvent(Event e){
							try{
								if(e.getName().equals("onYes")){
									
									//No cobra
									if(penalidadXduplicado.getGastoAdminEfectivo()==null || penalidadXduplicado.getGastoAdminEfectivo() ==.00) {
										List<VentaPasaje>listVentas= new ArrayList<>();
										listVentas.add(ventaOriginal);
										WSFE.reimprimirComprobante(listVentas, wndReimpresion, Constantes.NUMERO_COPIAS_COMPROBANTE_PASAJES);										
									}else {
										//Si, cobra
										VentaPasaje gastoAdmin= new VentaPasaje();
										gastoAdmin.setVentaOriginal(ventaOriginal.getVentaOriginal());
										gastoAdmin.setVentaPasaje(ventaOriginal);
										gastoAdmin.setItinerario(new Itinerario((long)1));
										gastoAdmin.setRuta(ventaOriginal.getRuta());
										gastoAdmin.setCliente(ventaOriginal.getCliente()!=null?ventaOriginal.getCliente():null);
										gastoAdmin.setPasajero(ventaOriginal.getPasajero());
										gastoAdmin.setFormaPago((FormaPago)cmbFormaPago.getSelectedItem().getValue());
										gastoAdmin.setServicio(ventaOriginal.getServicio());
										gastoAdmin.setTipoComprobante((TipoComprobante)cmbTipoComprobante.getSelectedItem().getValue());
										gastoAdmin.setTipoMovimiento(new TipoMovimiento(Constantes.ID_TIPMOV_GASTOS_ADMINISTRATIVOS));
										gastoAdmin.setTipoFormaPago((TipoFormaPago)cmbTipoFormaPago.getSelectedItem().getValue());
										if(gastoAdmin.getTipoFormaPago().getId().intValue()==Constantes.ID_TIPFORPAG_TARJETA)
											gastoAdmin.setTarjetaCredito((TarjetaCredito)cmbTarjetaCredito.getSelectedItem().getValue());
										gastoAdmin.setNumeroBoleto(txtNuevoComprobante.getText().trim());
										gastoAdmin.setNumeroBoletoAnterior(ventaOriginal.getNumeroBoleto());
										gastoAdmin.setSecuencial(Constantes.FALSE_VALUE);
										gastoAdmin.setTarifa(penalidadXduplicado!=null && penalidadXduplicado.getGastoAdminEfectivo()!=null? penalidadXduplicado.getGastoAdminEfectivo():.00);
										gastoAdmin.setRecargo(0.00);
										gastoAdmin.setDescuento(0.00);
										gastoAdmin.setPenalidad(0.00);
										gastoAdmin.setAcuenta(0.00);
										gastoAdmin.setImportePagadoByDiferencia(0.00);
										gastoAdmin.setImportePagado(gastoAdmin.getTarifa());
										gastoAdmin.setImportePagadoEfectivo(0.00);
										gastoAdmin.setImportePagadoTarjeta(0.00);
										gastoAdmin.setTipoTransaccion(Constantes.TIPO_OPERACION_VARIOS);
										gastoAdmin.setFechaCaducidad(new Date());
										gastoAdmin.setNumeroControl("-");
										gastoAdmin.setFechaLiquidacion(fechaLiquidacion);
										gastoAdmin.setAgencia(getAgencia());
										gastoAdmin.setUsuario(getUsuario());
										gastoAdmin.setCanalVenta(getUsuarioHardware().getCanalVenta());
										gastoAdmin.setUsuarioHardware(usuarioHardware);
										gastoAdmin.setIdaRetorno(Constantes.FALSE_VALUE);
										gastoAdmin.setEsFechaAbierta(Constantes.FALSE_VALUE);
										gastoAdmin.setEstadoRegistro(Constantes.VALUE_ACTIVO);
										gastoAdmin.setObservaciones("DUPLICADO");
										/*Calcula el igv del gasto administrativo*/
										Double igv=gastoAdmin.getImportePagado()- Double.valueOf(Util.toNumberFormat(gastoAdmin.getImportePagado()/((Constantes.IGV/100)+1),2));
										gastoAdmin.setIgv(igv);
										UtilData.auditarRegistro(gastoAdmin, getUsuario(), Executions.getCurrent());
										
										//Guarda el gasto admin
										ServiceLocator.getVentaPasajesManager().generarGastoAdministrativo(gastoAdmin, true);
										
										//Consulta el gasto admin guardado
										VentaPasaje gastoAdminGenerado = ServiceLocator.getVentaPasajesManager().buscarVentaById(gastoAdmin.getId());
										
										//Gasto administrativo que será enviado a sunat
										List<VentaPasaje>listGastoAdmin= new ArrayList<>();
										listGastoAdmin.add(gastoAdminGenerado);
										
										//Venta que será reimpresa
										List<VentaPasaje>listVentasSoloReimpresion= new ArrayList<>();
										listVentasSoloReimpresion.add(ventaOriginal);
										
										WSFE.sendVenta(listGastoAdmin, wndReimpresion, true, null, false, listVentasSoloReimpresion, Constantes.NUMERO_COPIAS_COMPROBANTE_PASAJES);
									}
									
									win.onClose();
								}
							}catch(NumeroBoletoDuplicadoException nbdex){
								DlgMessage.information(Messages.getString("WndReimprimirBoleto.information.numeroBoletoVendido"));
							}catch(Exception ex){
								ex.printStackTrace();
								DlgMessage.information(this.getClass().getSimpleName()+" "+ex.getMessage());
							}
						}
					});
				}catch(TipoFormaPagoNullException tfpnex){
					DlgMessage.information(Messages.getString("WndVentaReserva.information.noTipoFormaPago"), cmbTipoFormaPago);
				}catch(OperadorTarjetaCreditoNullException otcnex){
					DlgMessage.information(Messages.getString("WndVentaReserva.information.noOperadorTarjetaCredito"), cmbOperadorTarjetaCredito);
				}catch(TarjetaCreditoNullException tcnex){
					DlgMessage.information(Messages.getString("WndVentaReserva.information.noTarjetaCredito"), cmbTarjetaCredito);
				}catch(Exception ex){
					DlgMessage.information(this.getClass().getSimpleName()+" "+ex.getMessage());
				}
			}
		});
		button.setHeight("28px");
		row.appendChild(button);
		button = new Button("Cancelar", "resources/mp_cancelarEnabled.png");
		button.addEventListener(Events.ON_CLICK, new EventListener<Event>() {
			public void onEvent(Event e){
				win.onClose();
			}
		});
		button.setHeight("28px");
		button.setFocus(true);
		row.appendChild(button);

		rows.appendChild(row);

		grid.appendChild(rows);
		win.appendChild(grid);
		return win;
	}

	/**
	 * Para seleccionar el Tipo de Comprobante por defecto
	 */
	private void onSelectDefaultTipoComprobante(){
		for(Comboitem comboitem : cmbTipoComprobante.getItems()){
			/*	Si la agencia pertenece a TEPSA*/
			if(comboitem.getValue() instanceof TipoComprobante && ((TipoComprobante)comboitem.getValue()).getId().intValue()==Constantes.ID_TIPCOM_BOLETO_VIAJE)
				cmbTipoComprobante.setSelectedItem(comboitem);
		}
		cmbTipoComprobante.setDisabled(true);
	}

	/**
	 * Selecciona por defecto el item del Combo Forma de Pago.
	 */
	private void onSelectDefaultFormaPago(){
		/*	Seleccionamos por defecto la Forma de pago	*/
		for(Comboitem comboitem : cmbFormaPago.getItems()){
			/*	Si la agencia pertenece a TEPSA*/
			if(comboitem.getValue() instanceof FormaPago && ((FormaPago)comboitem.getValue()).getId().intValue()==Constantes.ID_FORPAG_CONTADO){
				cmbFormaPago.setSelectedItem(comboitem);
				onLoadTipoFormaPago();
				for(Comboitem item : cmbTipoFormaPago.getItems()){
					if(item.getValue() instanceof TipoFormaPago && ((TipoFormaPago)item.getValue()).getId().intValue()==Constantes.ID_TIPFORPAG_EFECTIVO)
						cmbTipoFormaPago.setSelectedItem(item);
				}
			}
		}
		cmbFormaPago.setDisabled(true);
	}

	/**
	 * Carga los tipos de Forma de pago.
	 */
	public void onLoadTipoFormaPago(){
		try{
			cmbTipoFormaPago.getItems().clear();
			if(cmbFormaPago.getSelectedItem().getValue() instanceof FormaPago){				
				
				FormaPago formaPago = cmbFormaPago.getSelectedItem().getValue();
				TreeMap<String, Object> criteriosBusqueda = new TreeMap<>();
				criteriosBusqueda.put("formaPago.id", formaPago.getId());
				List<String> criteriosOrdenar = new ArrayList<>();
				criteriosOrdenar.add("denominacion");
				List<TipoFormaPago> lstTipoFormasPago = ServiceLocator.getTipoFormaPagoManager().buscarPorX(criteriosBusqueda, criteriosOrdenar);
				UtilData.cargarGenericData(cmbTipoFormaPago, false);
				for(TipoFormaPago tipoFormaPago : lstTipoFormasPago){
					if(tipoFormaPago.getTipoIngresoLiquidacion().intValue()==Constantes.FALSE_VALUE) {
						Comboitem item = new Comboitem();
						item.setLabel(tipoFormaPago.getDenominacion());
						item.setValue(tipoFormaPago);
						cmbTipoFormaPago.appendChild(item);	
					}						
				}
				cmbTipoFormaPago.setDisabled(false);

				if(formaPago.getId().intValue()==Constantes.ID_FORPAG_CREDITO){
					for(Comboitem comboitem: cmbTipoFormaPago.getItems())
					{
						if(comboitem.getValue() instanceof TipoFormaPago){
							TipoFormaPago tipoFormaPago=comboitem.getValue();
							if(tipoFormaPago.getId().intValue()==Constantes.ID_TIPFORPAG_CREDITO){
								cmbTipoFormaPago.setSelectedItem(comboitem);
								cmbTipoFormaPago.setDisabled(true);
							}
						}
					}
				}
				cmbTipoFormaPago.setDisabled(false);
			}else{
				cmbTipoFormaPago.setDisabled(true);
				cmbTipoFormaPago.getItems().clear();
				cmbTipoFormaPago.setText("");
				cmbOperadorTarjetaCredito.setDisabled(true);
				cmbOperadorTarjetaCredito.getItems().clear();
				cmbOperadorTarjetaCredito.setText("");
				cmbTarjetaCredito.setDisabled(true);
				cmbTarjetaCredito.getItems().clear();
				cmbTarjetaCredito.setText("");
			}
		}catch(Exception ex){
			DlgMessage.error(this.getClass().getSimpleName()+" "+ex.getMessage());
		}
	}

	/**
	 * Realiza una validación del Tipo de Forma de Pago, para habilitar o deshabilitar algunos controles.
	 * @throws Exception
	 */
	public void onValidateTipoFormaPago(){
		try{
			if(cmbTipoFormaPago.getSelectedItem().getValue() instanceof TipoFormaPago){
				/*	Si es tarjeta cargamos los operadores de tarjeta de credito	*/
				if(cmbTipoFormaPago.getText().equals("TARJETA")){
					cmbOperadorTarjetaCredito.getItems().clear();
					UtilData.cargarDataCombo(cmbOperadorTarjetaCredito, OperadorTarjetaCredito.class, false);
					cmbOperadorTarjetaCredito.setDisabled(false);
				}else{
					cmbOperadorTarjetaCredito.setDisabled(true);
					cmbOperadorTarjetaCredito.getItems().clear();
					cmbOperadorTarjetaCredito.setText("");
					cmbTarjetaCredito.setDisabled(true);
					cmbTarjetaCredito.getItems().clear();
					cmbTarjetaCredito.setText("");
				}
			}else{
				cmbOperadorTarjetaCredito.setDisabled(true);
				cmbOperadorTarjetaCredito.getItems().clear();
				cmbOperadorTarjetaCredito.setText("");
				cmbTarjetaCredito.setDisabled(true);
				cmbTarjetaCredito.getItems().clear();
				cmbTarjetaCredito.setText("");
			}
		}catch(Exception ex){
			DlgMessage.error(this.getClass().getSimpleName()+" "+ex.getMessage());
		}
	}

	/**
	 * Carga los diferentes tarjetas de credito, de acuerdo al operador seleccionado.
	 */
	public void onLoadTarjetas(){
		try{
			cmbTarjetaCredito.getItems().clear();
			cmbTarjetaCredito.setText("");
			if(cmbOperadorTarjetaCredito.getSelectedItem().getValue() instanceof OperadorTarjetaCredito){
				OperadorTarjetaCredito operadorTarjetaCredito = cmbOperadorTarjetaCredito.getSelectedItem().getValue();
				TreeMap<String, Object> criteriosBusqueda = new TreeMap<String, Object>();
				criteriosBusqueda.put("operadorTarjetaCredito.id", operadorTarjetaCredito.getId());
				List<String> criteriosOrdenar = new ArrayList<String>();
				criteriosOrdenar.add("denominacion");
				List<TarjetaCredito> lstTarjetaCredito = ServiceLocator.getTarjetaCreditoManager().buscarPorX(criteriosBusqueda, criteriosOrdenar);
				UtilData.cargarGenericData(cmbTarjetaCredito, false);
				for(TarjetaCredito tarjetaCredito : lstTarjetaCredito){
					Comboitem item = new Comboitem();
					item.setLabel(tarjetaCredito.getDenominacion());
					item.setValue(tarjetaCredito);
					cmbTarjetaCredito.appendChild(item);
				}
				cmbTarjetaCredito.setDisabled(false);
			}else
				cmbTarjetaCredito.setDisabled(true);
		}catch(Exception ex){
			DlgMessage.error(this.getClass().getSimpleName()+" "+ex.getMessage());
			ex.printStackTrace();
		}
	}
}

