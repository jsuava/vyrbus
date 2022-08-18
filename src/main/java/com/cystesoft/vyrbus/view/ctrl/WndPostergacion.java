/**
 * Proyecto		: SISVYR
 * Sistema		: Sistema de Ventas y Reservas
 * Descripción	: Clase que se encarga de realizar la postergación de una Venta.
 * Autor		: José Sullo Avalos
 * Fecha		: 11/01/2013
 */
package com.cystesoft.vyrbus.view.ctrl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TreeMap;

import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zul.Button;
import org.zkoss.zul.Checkbox;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Comboitem;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.Div;
import org.zkoss.zul.Doublebox;
import org.zkoss.zul.Grid;
import org.zkoss.zul.Hbox;
import org.zkoss.zul.Image;
import org.zkoss.zul.Label;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Separator;
import org.zkoss.zul.Tab;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Vbox;
import org.zkoss.zul.Window;

import com.cystesoft.vyrbus.model.bean.Agencia;
import com.cystesoft.vyrbus.model.bean.CanalVenta;
import com.cystesoft.vyrbus.model.bean.Cliente;
import com.cystesoft.vyrbus.model.bean.ControlEspecieValorada;
import com.cystesoft.vyrbus.model.bean.DetalleItinerario;
import com.cystesoft.vyrbus.model.bean.EspecieValorada;
import com.cystesoft.vyrbus.model.bean.FormaPago;
import com.cystesoft.vyrbus.model.bean.Itinerario;
import com.cystesoft.vyrbus.model.bean.ItinerarioAgenciaPartida;
import com.cystesoft.vyrbus.model.bean.Localidad;
import com.cystesoft.vyrbus.model.bean.OperadorTarjetaCredito;
import com.cystesoft.vyrbus.model.bean.PasajeroFrecuente;
import com.cystesoft.vyrbus.model.bean.Promocion;
import com.cystesoft.vyrbus.model.bean.TarjetaCredito;
import com.cystesoft.vyrbus.model.bean.TipoComprobante;
import com.cystesoft.vyrbus.model.bean.TipoFormaPago;
import com.cystesoft.vyrbus.model.bean.TipoMovimiento;
import com.cystesoft.vyrbus.model.bean.TipoNota;
import com.cystesoft.vyrbus.model.bean.TmpOcupacionAsientos;
import com.cystesoft.vyrbus.model.bean.UsuarioHardware;
import com.cystesoft.vyrbus.model.bean.VentaPasaje;
import com.cystesoft.vyrbus.service.exceptions.CapacityExceedsException;
import com.cystesoft.vyrbus.service.exceptions.CriteriosBusquedaIncompletosException;
import com.cystesoft.vyrbus.service.exceptions.DuplicateSeatException;
import com.cystesoft.vyrbus.service.exceptions.FechaCaducidadNullException;
import com.cystesoft.vyrbus.service.exceptions.FechaViajeNoValidaException;
import com.cystesoft.vyrbus.service.exceptions.FormaPagoNullException;
import com.cystesoft.vyrbus.service.exceptions.ImporteMixtoNullException;
import com.cystesoft.vyrbus.service.exceptions.ItinerarioException;
import com.cystesoft.vyrbus.service.exceptions.LimiteSecuencialException;
import com.cystesoft.vyrbus.service.exceptions.LiquidacionNullException;
import com.cystesoft.vyrbus.service.exceptions.ManifiestoImpresoException;
import com.cystesoft.vyrbus.service.exceptions.NumeroAsientoNullException;
import com.cystesoft.vyrbus.service.exceptions.NumeroBoletoDuplicadoException;
import com.cystesoft.vyrbus.service.exceptions.NumeroBoletoNullException;
import com.cystesoft.vyrbus.service.exceptions.OperadorTarjetaCreditoNullException;
import com.cystesoft.vyrbus.service.exceptions.PerdidaServicioException;
import com.cystesoft.vyrbus.service.exceptions.PostergacionByFechaLimitePostergarException;
import com.cystesoft.vyrbus.service.exceptions.PostergacionByFormaPagoNoPermitidoException;
import com.cystesoft.vyrbus.service.exceptions.PostergacionByTipoAgenciaNoPermitidoException;
import com.cystesoft.vyrbus.service.exceptions.PostergacionByTipoMovimientoNoPermitidoException;
import com.cystesoft.vyrbus.service.exceptions.PostergacionNoCambioNombreException;
import com.cystesoft.vyrbus.service.exceptions.PostergacionNoCambioRazonSozialException;
import com.cystesoft.vyrbus.service.exceptions.TarjetaCreditoNullException;
import com.cystesoft.vyrbus.service.exceptions.TiempoExpiracionBloqueoException;
import com.cystesoft.vyrbus.service.exceptions.TipoComprobanteNullException;
import com.cystesoft.vyrbus.service.exceptions.TipoFormaPagoNullException;
import com.cystesoft.vyrbus.service.locator.ServiceLocator;
import com.cystesoft.vyrbus.service.util.AplicarPromocion;
import com.cystesoft.vyrbus.service.util.Constantes;
import com.cystesoft.vyrbus.service.util.Messages;
import com.cystesoft.vyrbus.service.util.Util;
import com.cystesoft.vyrbus.service.util.UtilData;
import com.cystesoft.vyrbus.service.util.WSFE;
import com.cystesoft.vyrbus.view.ui.DlgMessage;
import com.cystesoft.vyrbus.view.ui.WndBase;
import com.cystesoft.vyrbus.view.ui.WndBuscarPasajero;
import com.cystesoft.vyrbus.view.ui.WndMapaBus;
import com.cystesoft.vyrbus.view.ui.WndSeleccionaItinerario;

/**
 * @author Jose
 *
 */
public class WndPostergacion extends WndBase implements Serializable {
	private static final long serialVersionUID = 1L;
	private Combobox cmbOrigen;
	private Combobox cmbDestino;
	private Textbox txtNumeroBoleto;
	private Textbox txtPasajero;
	private Textbox txtNumeroControl;
	private Datebox dtbxFechaPartida;
	private Listbox lbxVentas;
	private Grid grdPostergacion;
	private Tab tabListado;
	private Tab tabDetalle;

	private Textbox txtNumeroControlActual;
	private Textbox txtItinerarioActual;
	private Textbox txtNumeroAsientoActual;
	private Textbox txtPasajeroActual;
	private Textbox txtClienteActual;
	private Textbox txtNumeroBoletoActual;
	private Textbox txtOrigenActual;
	private Textbox txtDestinoActual;
	private Textbox txtEmbarqueActual;
	private Textbox txtHoraEmbarqueActual;
	private Textbox txtServicioActual;
	private Textbox txtSalidaActual;

	private Textbox txtItinerarioPostergado;
	private Textbox txtNumeroAsientoPostergado;
	private Textbox txtNumeroPisoPostergado;
	private Textbox txtNumeroControlPostergado;
	private Textbox txtPasajeroPostergado;
	private Textbox txtClientePostergado;
	private Textbox txtClienteRuc;
	private Textbox txtClienteDireccion;
	private Textbox txtNumeroBoletoPostergado;
	private Textbox txtOrigenPostergado;
	private Textbox txtDestinoPostergado;
	private Combobox cmbEmbarquePostergado;
	private Textbox txtHoraEmbarquePostergado;
	private Textbox txtServicioPostergado;
	private Textbox txtSalidaPostergado;
	private Textbox txtIdPromocion;
	private Image imgBuscarItinerario;
	private Image imgSeleccionarAsiento;
	private Image imgQuitarPromocion;
	private Image imgBuscarPasajero;
	private Image imgBuscarCliente;
	private Image imgBuscarClienteRuc;
	private Image imgBuscarClienteDireccion;
	private Checkbox chkFechaAbierta;
	private Checkbox chkCambioNombre;
//	private Checkbox chkCambioRazonsocial;
	private Checkbox chkPagoMixto;
	private Window wndPostergacion;
	private Doublebox dblbxMontoAnterior;
	private Doublebox dblbxTarifa;
	private Doublebox dblbxDescuento;
	private Doublebox dblbxSaldo;
	private Doublebox dblbxPenalidad;
	private Doublebox dblbxImporteTotal;
	private Doublebox dblbxImporteEfectivo;
	private Doublebox dblbxImporteTarjeta;
	private Combobox cmbTipoComprobante;
	private Combobox cmbFormaPago;
	private Combobox cmbTipoFormaPago;
	private Combobox cmbOperadorTarjetaCredito;
	private Combobox cmbTarjetaCredito;
	private Combobox cmbtipoComprobantePostergado;
	private Label lblImporteEfectivo;
	private Label lblImporteTarjeta;
	private Label lblSaldo;
	private Label lblImportePagar;
	private Checkbox chkCambioRazonSocial;
	private Checkbox chkCambioRuc;
	private Checkbox chkCambioDireccionFiscal;
	private Checkbox chkCambioBoletaFactura;
	private Checkbox chkCambioFacturaBoleta;
	private Checkbox chkCambioDestino;
	private Checkbox chkCambioServicio;
//	private Label lblDescuento;
	private Label lblPromocion;


//	private TipoComprobante tipoComprobante;
	private UsuarioHardware usuarioHardware;
	private DetalleItinerario detalleItinerario;
	private VentaPasaje postergacion;
	private TipoNota tipoNotaCredito;
	private Agencia agencia;
	private CanalVenta canalVenta;
	private int secuencial=0;
	private Date fechaLiquidacion;
	private Promocion promocionAplicada = null;
	private boolean isCorporativo=false;

	private Window wndCambiarRazoSocial = null;
	private Window wndCambiarDireccionFiscal = null;

	private VentaPasaje gastoAdmin=null;
	private final String LABEL_IMPPAG_TO_TEPSA="IMPORTE TOTAL PAGAR";
	private final String LABEL_IMPPAG_TO_PASAJERO="IMPORTE TOTAL A DEVOLVER";

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
			UtilData.cargarDataCombo(cmbOrigen, Localidad.class, false);
			UtilData.cargarDataCombo(cmbDestino, Localidad.class, false);
//			tipoComprobante = (TipoComprobante)this.getDesktop().getSession().getAttribute(Constantes.ATRIBUTO_TIPO_COMPROBANTE);
			usuarioHardware = (UsuarioHardware)this.getDesktop().getSession().getAttribute(Constantes.ATRIBUTO_USUARIO_HARDWARE);
			agencia = (Agencia)this.getDesktop().getSession().getAttribute(Constantes.ATRIBUTO_AGENCIA);
//			usuario = (Usuario)this.getDesktop().getSession().getAttribute(Constantes.ATRIBUTO_USUARIO);
			canalVenta = (CanalVenta)this.getDesktop().getSession().getAttribute(Constantes.ATRIBUTO_CANAL_VENTA);
			fechaLiquidacion = (Date)this.getDesktop().getSession().getAttribute(Constantes.ATRIBUTO_FECHA_LIQUIDACION);

			enlazarItinerario(imgBuscarItinerario);

			TreeMap<String, Object> criteriosBusqueda = new TreeMap<>();
			criteriosBusqueda.put("rubro", TipoComprobante.RUBRO_PASAJES);
			UtilData.cargarDataCombo(cmbTipoComprobante, TipoComprobante.class, criteriosBusqueda, false);
			UtilData.cargarDataCombo(cmbtipoComprobantePostergado, TipoComprobante.class, criteriosBusqueda, false);
			UtilData.cargarDataCombo(cmbFormaPago, FormaPago.class, false);

			String fecha = Util.DatetoString(new Date(), "yyyyMMdd");
			dtbxFechaPartida.setConstraint("after "+fecha);
			txtNumeroControl.setFocus(true);
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
		wndPostergacion = (Window)this.getFellow("wndPostergacion");
		cmbOrigen = (Combobox)this.getFellow("cmbOrigen");
		cmbDestino = (Combobox)this.getFellow("cmbDestino");
		txtNumeroBoleto = (Textbox)this.getFellow("txtNumeroBoleto");
		txtPasajero = (Textbox)this.getFellow("txtPasajero");
		txtNumeroControl = (Textbox)this.getFellow("txtNumeroControl");
		dtbxFechaPartida = (Datebox)this.getFellow("dtbxFechaPartida");
		lbxVentas = (Listbox)this.getFellow("lbxVentas");
		grdPostergacion = (Grid)this.getFellow("grdPostergacion");
		tabListado = (Tab)this.getFellow("tabListado");
		tabDetalle = (Tab)this.getFellow("tabDetalle");

		txtNumeroControlActual = (Textbox)this.getFellow("txtNumeroControlActual");
		txtItinerarioActual = (Textbox)this.getFellow("txtItinerarioActual");
		txtNumeroAsientoActual = (Textbox)this.getFellow("txtNumeroAsientoActual");
		txtPasajeroActual = (Textbox)this.getFellow("txtPasajeroActual");
		txtClienteActual= (Textbox)this.getFellow("txtClienteActual");
		txtNumeroBoletoActual = (Textbox)this.getFellow("txtNumeroBoletoActual");
		txtOrigenActual = (Textbox)this.getFellow("txtOrigenActual");
		txtDestinoActual = (Textbox)this.getFellow("txtDestinoActual");
		txtEmbarqueActual = (Textbox)this.getFellow("txtEmbarqueActual");
		txtHoraEmbarqueActual = (Textbox)this.getFellow("txtHoraEmbarqueActual");
		txtServicioActual = (Textbox)this.getFellow("txtServicioActual");
		txtSalidaActual = (Textbox)this.getFellow("txtSalidaActual");
		cmbtipoComprobantePostergado=(Combobox)this.getFellow("cmbtipoComprobantePostergado");

		txtItinerarioPostergado = (Textbox)this.getFellow("txtItinerarioPostergado");
		txtNumeroAsientoPostergado = (Textbox)this.getFellow("txtNumeroAsientoPostergado");
		txtNumeroPisoPostergado = (Textbox)this.getFellow("txtNumeroPisoPostergado");
		txtNumeroControlPostergado = (Textbox)this.getFellow("txtNumeroControlPostergado");
		txtPasajeroPostergado = (Textbox)this.getFellow("txtPasajeroPostergado");
		imgBuscarPasajero = (Image)this.getFellow("imgBuscarPasajero");
		txtClientePostergado = (Textbox)this.getFellow("txtClientePostergado");
		txtClienteRuc=(Textbox)this.getFellow("txtClienteRuc");
		txtClienteDireccion=(Textbox)this.getFellow("txtClienteDireccion");
		imgBuscarCliente=(Image)this.getFellow("imgBuscarCliente");
		imgBuscarClienteRuc=(Image)this.getFellow("imgBuscarClienteRuc");
		imgBuscarClienteDireccion=(Image)this.getFellow("imgBuscarClienteDireccion");
		txtNumeroBoletoPostergado = (Textbox)this.getFellow("txtNumeroBoletoPostergado");
		txtOrigenPostergado = (Textbox)this.getFellow("txtOrigenPostergado");
		txtDestinoPostergado = (Textbox)this.getFellow("txtDestinoPostergado");
		cmbEmbarquePostergado = (Combobox)this.getFellow("cmbEmbarquePostergado");
		txtHoraEmbarquePostergado = (Textbox)this.getFellow("txtHoraEmbarquePostergado");
		txtServicioPostergado = (Textbox)this.getFellow("txtServicioPostergado");
		txtSalidaPostergado = (Textbox)this.getFellow("txtSalidaPostergado");
		imgBuscarItinerario = (Image)this.getFellow("imgBuscarItinerario");
		imgSeleccionarAsiento = (Image)this.getFellow("imgSeleccionarAsiento");
		chkFechaAbierta = (Checkbox)this.getFellow("chkFechaAbierta");
		chkCambioNombre =(Checkbox)this.getFellow("chkCambioNombre");
		chkCambioRazonSocial=(Checkbox)this.getFellow("chkCambioRazonSocial");
		chkCambioRuc =(Checkbox)this.getFellow("chkCambioRuc");
		chkCambioDireccionFiscal =(Checkbox)this.getFellow("chkCambioDireccionFiscal");
		chkCambioBoletaFactura =(Checkbox)this.getFellow("chkCambioBoletaFactura");
		chkCambioFacturaBoleta =(Checkbox)this.getFellow("chkCambioFacturaBoleta");
		chkCambioDestino=(Checkbox)this.getFellow("chkCambioDestino");
		chkCambioServicio=(Checkbox)this.getFellow("chkCambioServicio");

		dblbxMontoAnterior = (Doublebox)this.getFellow("dblbxMontoAnterior");
		dblbxTarifa = (Doublebox)this.getFellow("dblbxTarifa");
		dblbxDescuento = (Doublebox)this.getFellow("dblbxDescuento");
		txtIdPromocion = (Textbox)this.getFellow("txtIdPromocion");
		imgQuitarPromocion = (Image)this.getFellow("imgQuitarPromocion");
//		lblDescuento = (Label)this.getFellow("lblDescuento");
		dblbxSaldo = (Doublebox)this.getFellow("dblbxSaldo");
		dblbxPenalidad = (Doublebox)this.getFellow("dblbxPenalidad");
		dblbxImporteTotal = (Doublebox)this.getFellow("dblbxImporteTotal");
		chkPagoMixto = (Checkbox)this.getFellow("chkPagoMixto");
		lblImporteEfectivo = (Label)this.getFellow("lblImporteEfectivo");
		lblImporteTarjeta = (Label)this.getFellow("lblImporteTarjeta");
		lblSaldo=(Label)this.getFellow("lblSaldo");
		lblImportePagar=(Label)this.getFellow("lblImportePagar");
		dblbxImporteEfectivo = (Doublebox)this.getFellow("dblbxImporteEfectivo");
		dblbxImporteTarjeta = (Doublebox)this.getFellow("dblbxImporteTarjeta");
		cmbTipoComprobante = (Combobox)this.getFellow("cmbTipoComprobante");
		cmbFormaPago = (Combobox)this.getFellow("cmbFormaPago");
		cmbTipoFormaPago = (Combobox)this.getFellow("cmbTipoFormaPago");
		cmbOperadorTarjetaCredito = (Combobox)this.getFellow("cmbOperadorTarjetaCredito");
		cmbTarjetaCredito = (Combobox)this.getFellow("cmbTarjetaCredito");
		lblPromocion = (Label)this.getFellow("lblPromocion");



		dblbxMontoAnterior.setLocale(Locale.US);
		dblbxTarifa.setLocale(Locale.US);
		dblbxDescuento.setLocale(Locale.US);
		dblbxSaldo.setLocale(Locale.US);
		dblbxPenalidad.setLocale(Locale.US);
		dblbxImporteTotal.setLocale(Locale.US);
		dblbxImporteEfectivo.setLocale(Locale.US);
		dblbxImporteTarjeta.setLocale(Locale.US);
		dblbxImporteEfectivo.setValue(0.0);
		dblbxImporteTarjeta.setValue(0.0);

		lbxVentas.addEventListener(Events.ON_DOUBLE_CLICK, new EventListener<Event>() {
			@Override
			public void onEvent(Event e)throws Exception{
				try{
					VentaPasaje venta = (VentaPasaje)lbxVentas.getSelectedItem().getValue();
					secuencial = venta.getSecuencial();
					postergacion = (VentaPasaje)venta.clone();
					postergacion.setVentaPasaje(venta);

					if(venta.getTipoMovimiento().getId().intValue()==Constantes.ID_TIPMOV_ANULACION_SISTEMA)
						throw new PostergacionByTipoMovimientoNoPermitidoException(Constantes.ID_TIPMOV_ANULACION_SISTEMA);
					else if(venta.getTipoMovimiento().getId().intValue()==Constantes.ID_TIPMOV_FECHA_ABIERTA)
						throw new PostergacionByTipoMovimientoNoPermitidoException(Constantes.ID_TIPMOV_FECHA_ABIERTA);
					else if(venta.getTipoMovimiento().getId().intValue()==Constantes.ID_TIPMOV_POSTERGACION_FA)
						throw new PostergacionByTipoMovimientoNoPermitidoException(Constantes.ID_TIPMOV_POSTERGACION_FA);
					else if(venta.getFormaPago().getId().intValue()==Constantes.ID_FORPAG_CREDITO && venta.getRucClienteCredito()!=null){
						Agencia agencia = ServiceLocator.getAgenciaManager().buscarAgenciaByRucClienteCredito(venta.getRucClienteCredito());
						if(agencia.getTipoAgencia().getId().intValue()==Constantes.ID_TIPAGE_CORPORATIVO)
							throw new PostergacionByTipoAgenciaNoPermitidoException(Constantes.ID_TIPAGE_CORPORATIVO);
					}else if(venta.getFormaPago().getId().intValue()==Constantes.ID_FORPAG_CORTESIA)
						throw new PostergacionByFormaPagoNoPermitidoException(Constantes.ID_FORPAG_CORTESIA);
					else if(venta.getTipoTransaccion().equals(Constantes.TIPO_OPERACION_PERDIDA_SERVICIO))
						throw new PerdidaServicioException();

					/*###End begin 08/11/2016 - jabanto*/
					if(postergacion.getSecuencial().intValue() >= Constantes.MAXIMO_POSTERGACIONES)
						throw new LimiteSecuencialException();
					if(Util.comparaFechas(postergacion.getFechaCaducidad(), new Date(), Util.OPER_MENOR))
						throw new FechaCaducidadNullException();
					if(ServiceLocator.getDetalleManifiestoManager().validarVentaManifiesto(postergacion.getId()))
						throw new ManifiestoImpresoException();
					
					String fechaPartida = Util.DatetoString(venta.getFechaPartida(),Constantes.DATE_FORMAT)+" "+venta.getHoraPartida()+":00";
					Long limite = Util.StringtoDate(fechaPartida, Constantes.DATE_TIME_FORMAT).getTime()-(Constantes.MILISEGUNDOS_X_HORA * Constantes.TIEMPO_LIMITE_POSTERGACION);
					@SuppressWarnings("unused")
					String fechaLimitePostergar = Util.DatetoString(new Date(limite), Constantes.DATE_TIME_FORMAT);
					if(Util.comparaFechasWithTime(ServiceLocator.getVentaPasajesManager().getDateSystem(), fechaLimitePostergar, Util.OPER_MAYOR))
						throw new PostergacionByFechaLimitePostergarException();

					isCorporativo=false;
					/* Valida si es corporativo - 30/12/2016 - jabanto*/
					if(venta.getFormaPago().getId().intValue()==Constantes.ID_FORPAG_CREDITO && venta.getRucClienteCredito()!=null){
						Agencia agencia=ServiceLocator.getAgenciaManager().buscarAgenciaByRucClienteCredito(venta.getRucClienteCredito());
						if(agencia.getTipoAgencia().getId().intValue()==Constantes.ID_TIPAGE_CORPORATIVO)
							isCorporativo=true;
					}

					onSelectDefaultTipoComprobante(cmbTipoComprobante);
					onSelectDefaultTipoComprobante(cmbtipoComprobantePostergado);
					mostrarEstadoVenta(venta);
					onSelectDefaultFormaPago();
					grdPostergacion.setVisible(true);
					tabDetalle.setSelected(true);
					imgBuscarItinerario.setVisible(true);

					Date dateSys=Constantes.FORMAT_LONG.parse(ServiceLocator.getVentaPasajesManager().getDateSystem());

					/*Realiza la validacion para determinar si se puede o no postergar - 08/11/2016 - jabanto*/
					if(isCorporativo || postergacion.getSecuencial().intValue() >= Constantes.MAXIMO_POSTERGACIONES){
						chkFechaAbierta.setDisabled(true);
						chkCambioNombre.setDisabled(true);
						imgBuscarItinerario.setVisible(false);
					}
					if(Util.comparaFechas(postergacion.getFechaCaducidad(), dateSys, Util.OPER_MENOR)){
						chkFechaAbierta.setDisabled(true);
						chkCambioNombre.setDisabled(true);
						imgBuscarItinerario.setVisible(false);
					}
//					Date fechaPartida=Constantes.FORMAT_LONG.parse(Constantes.FORMAT_DATE.format(postergacion.getFechaPartida())+" "+postergacion.getHoraPartida());
//					if(ServiceLocator.getDetalleManifiestoManager().validarVentaManifiesto(postergacion.getId()) || fechaPartida.getTime() <= dateSys.getTime()){
					if(ServiceLocator.getDetalleManifiestoManager().validarVentaManifiesto(postergacion.getId())){
						chkFechaAbierta.setDisabled(true);
						chkCambioNombre.setDisabled(true);
						imgBuscarItinerario.setVisible(false);
					}

				}catch(PostergacionByTipoMovimientoNoPermitidoException ptmnpex){
					if(ptmnpex.getTipoMovimiento().intValue()==Constantes.ID_TIPMOV_ANULACION_SISTEMA)
						DlgMessage.information(Messages.getString("WndPostergacion.information.postergacionByAnulacionNoPermitido"));
					else if(ptmnpex.getTipoMovimiento().intValue()==Constantes.ID_TIPMOV_FECHA_ABIERTA || ptmnpex.getTipoMovimiento().intValue()==Constantes.ID_TIPMOV_POSTERGACION_FA)
						DlgMessage.information(Messages.getString("WndPostergacion.information.postergacionByFechaAbiertaNoPermitido"));
				}catch(PostergacionByFormaPagoNoPermitidoException pfpnpex){
					DlgMessage.information(Messages.getString("WndPostergacion.information.postergacionByCortesiaNoPermitido"));
				}catch(PostergacionByTipoAgenciaNoPermitidoException ptanpex){
					DlgMessage.information(Messages.getString("WndPostergacion.information.postergacionByCorporativoNoPermitido"));
				}catch(PostergacionByFechaLimitePostergarException pflpex){
					DlgMessage.information(Messages.getString("WndPostergacion.information.postergacionByFechaLimitePostergar"));
				}catch(LimiteSecuencialException lsex){
					DlgMessage.information(Messages.getString("WndPostergacion.information.limiteSecuencial"));
				}catch(FechaCaducidadNullException fcnex){
					DlgMessage.information(Messages.getString("WndPostergacion.information.fechaCaducidad"));
				}catch(ManifiestoImpresoException miex){
					DlgMessage.information(Messages.getString("Generales.information.manifiestoImpreso"));
				}catch(PerdidaServicioException psex) {
					DlgMessage.information(Messages.getString("WndPerdidaServicio.information.noEditarBoletoPerdidaServicio"));
				}catch(Exception ex){
					DlgMessage.error(this.getClass().getSimpleName()+" "+ex.getMessage());
				}
			}
		});

		imgSeleccionarAsiento.addEventListener(Events.ON_CLICK, new EventListener<Event>() {
			@Override
			public void onEvent(Event e) throws Exception{
				enlazarMapaBus();
			}
		});

		chkFechaAbierta.addEventListener(Events.ON_CHECK, new EventListener<Event>() {
			@Override
			public void onEvent(Event e) throws Exception{
				chkFechaAbierta_onCheck();
			}
		});


		chkCambioNombre.addEventListener(Events.ON_CHECK,new EventListener<Event>() {
			@Override
			public void onEvent(Event e) throws Exception {
				chkCambioNombre_onCheck();
			}
		});

		chkCambioRuc.addEventListener(Events.ON_CHECK,new EventListener<Event>() {
			@Override
			public void onEvent(Event e) throws Exception {
				resetCheck_despuesViaje((Checkbox)e.getTarget());
				chkCambioRuc_onCheck();
			}
		});

		chkCambioRazonSocial.addEventListener(Events.ON_CHECK, new EventListener<Event>() {
			@Override
			public void onEvent(Event event) throws Exception {
				resetCheck_despuesViaje((Checkbox)event.getTarget());
				chkCambioRazonSocial_onCheck();
			}
		});

		chkCambioDireccionFiscal.addEventListener(Events.ON_CHECK, new EventListener<Event>() {
			@Override
			public void onEvent(Event event) throws Exception {
				resetCheck_despuesViaje((Checkbox)event.getTarget());
				chkCambioDireccionFiscal_onCheck();
			}
		});

		chkCambioBoletaFactura.addEventListener(Events.ON_CHECK, new EventListener<Event>() {
			@Override
			public void onEvent(Event event) throws Exception {
				resetCheck_despuesViaje((Checkbox)event.getTarget());
				chkCambioBoletaFactura_onCheck();
			}
		});

		chkCambioFacturaBoleta.addEventListener(Events.ON_CHECK, new EventListener<Event>() {
			@Override
			public void onEvent(Event event) throws Exception {
				resetCheck_despuesViaje((Checkbox)event.getTarget());
				chkCambioFacturaBoleta_onCheck();
			}
		});



		imgQuitarPromocion.addEventListener(Events.ON_CLICK, new EventListener<Event>() {
			@Override
			public void onEvent(Event e){
				quitarPromocion();
			}
		});




		txtNumeroAsientoPostergado.addEventListener(Events.ON_FOCUS, new EventListener<Event>() {
			@Override
			public void onEvent(Event e){

			}
		});

		imgBuscarPasajero.addEventListener(Events.ON_CLICK, new EventListener<Event>() {
			@Override
			public void onEvent(Event e) throws Exception{
				final WndBuscarPasajero oWndBuscarPasajero = new WndBuscarPasajero();
				wndPostergacion.appendChild(oWndBuscarPasajero);
				oWndBuscarPasajero.oThisWindow.setTitle("Búsqueda de Pasajeros");
				oWndBuscarPasajero.setMode(MODAL);
				oWndBuscarPasajero.onCreate();
				oWndBuscarPasajero.addEventListener(Events.ON_SELECT, new EventListener<Event>() {
					@Override
					public void onEvent(Event e){
						txtPasajeroPostergado.setText(oWndBuscarPasajero.getPasajero().toString());
						postergacion.setPasajero(oWndBuscarPasajero.getPasajero());
//						aplicarPromocion(); //Segun lo combersado el 03/12/2013 con Marco y Jose Avalos) para cambio de nombre no aplica promoción

						chkCambioNombre.setDisabled(chkCambioNombre.isChecked());
					}
				});
			}
		});

		imgBuscarClienteRuc.addEventListener(Events.ON_CLICK,new EventListener<Event>() {
			@Override
			public void onEvent(Event event) throws Exception {
				final WndBuscarPasajero oWndBuscarPasajero = new WndBuscarPasajero();
				wndPostergacion.appendChild(oWndBuscarPasajero);
				oWndBuscarPasajero.oThisWindow.setTitle("Búsqueda de Clientes");
				oWndBuscarPasajero.oThisWindow.setWidth("420px");
				oWndBuscarPasajero.setMode(MODAL);
				oWndBuscarPasajero.onCreate();
				oWndBuscarPasajero.buscaPax=false;
				oWndBuscarPasajero.rdPorNombres.setLabel("Razón Social");
				oWndBuscarPasajero.rowApePat.setVisible(false);
				oWndBuscarPasajero.rowApeMat.setVisible(false);
				oWndBuscarPasajero.addEventListener(Events.ON_SELECT, new EventListener<Event>() {
					@Override
					public void onEvent(Event e){
						txtClienteRuc.setText(oWndBuscarPasajero.getCliente().getNumeroDocumento());
						txtClientePostergado.setText(oWndBuscarPasajero.getCliente().toString());
						txtClienteDireccion.setText(oWndBuscarPasajero.getCliente().getDireccion()!=null?oWndBuscarPasajero.getCliente().getDireccion():"");
						postergacion.setCliente(oWndBuscarPasajero.getCliente());

						/*Desactiva el control segun la operacion que se este realizando*/
						if(chkCambioBoletaFactura.isChecked())
							chkCambioBoletaFactura.setDisabled(true);
						else if(chkCambioRuc.isChecked()){
							chkCambioRuc.setDisabled(true);
							/*Deshabilita los demas controles relacionado a la edicion de la factura*/
							chkCambioRazonSocial.setDisabled(true);
							chkCambioDireccionFiscal.setDisabled(true);
							chkCambioFacturaBoleta.setDisabled(true);
						}
//						chkCambioRuc.setDisabled(chkCambioRuc.isChecked());
					}
				});
			}
		});

		imgBuscarCliente.addEventListener(Events.ON_CLICK, new EventListener<Event>() {
			@Override
			public void onEvent(Event event) throws Exception {
				openWindowCambioRazonSocial();
			}
		});

		imgBuscarClienteDireccion.addEventListener(Events.ON_CLICK,new EventListener<Event>() {
			@Override
			public void onEvent(Event event) throws Exception {
				openWindowCambioDireccionFiscal();
			}
		});
	}

	private void chkCambioRazonSocial_onCheck(){
		try {
			imgBuscarCliente.setVisible(chkCambioRazonSocial.isChecked());

			if(!(chkFechaAbierta.isChecked() || chkCambioNombre.isChecked()) ){//Si no es fecha Abierta o cambio de nombre
				//Valida son itinerarios diferentes, se asume que es una postergacion
				if(chkCambioRazonSocial.isChecked() && (
						(txtItinerarioActual.getText().equals(txtItinerarioPostergado.getText()) && txtNumeroAsientoActual.getText().equals(txtNumeroAsientoPostergado.getText()))
								|| txtItinerarioPostergado.getText().isEmpty())){
					copiarDatosVenta();
				}else if(!chkCambioRazonSocial.isChecked() && txtItinerarioActual.getText().equals(txtItinerarioPostergado.getText()) && txtNumeroAsientoActual.getText().equals(txtNumeroAsientoPostergado.getText()) ) {
					//Si los itinerarios son iguales, el valor de la penalidad es cero
					limpiarControlesPostergacion();
				}
				calcularPagos();
			}
		} catch (Exception e) {
			e.printStackTrace();
			DlgMessage.error(e.getMessage());
		}

	}

	private void chkCambioDireccionFiscal_onCheck(){
		try {
			imgBuscarClienteDireccion.setVisible(chkCambioDireccionFiscal.isChecked());

			if(!(chkFechaAbierta.isChecked() || chkCambioNombre.isChecked()) ){//Si no es fecha Abierta o cambio de nombre
				//Valida son itinerarios diferentes, se asume que es una postergacion
				if(chkCambioDireccionFiscal.isChecked() && (
						(txtItinerarioActual.getText().equals(txtItinerarioPostergado.getText()) && txtNumeroAsientoActual.getText().equals(txtNumeroAsientoPostergado.getText()))
								|| txtItinerarioPostergado.getText().isEmpty())){
					copiarDatosVenta();
				}else if(!chkCambioDireccionFiscal.isChecked() && txtItinerarioActual.getText().equals(txtItinerarioPostergado.getText()) && txtNumeroAsientoActual.getText().equals(txtNumeroAsientoPostergado.getText()) ) {
					//Si los itinerarios son iguales, el valor de la penalidad es cero
					limpiarControlesPostergacion();
				}
				calcularPagos();
			}
		} catch (Exception e) {
			e.printStackTrace();
			DlgMessage.error(e.getMessage());
		}
	}

	private void chkCambioBoletaFactura_onCheck(){
		try {
			imgBuscarClienteRuc.setVisible(chkCambioBoletaFactura.isChecked());

			/*cambia el tipo de comprobante*/
			onSelectDefaultTipoComprobante(cmbTipoComprobante);
			onSelectDefaultTipoComprobante(cmbtipoComprobantePostergado);
			onLoadEspecieValorada(txtNumeroBoletoPostergado, cmbtipoComprobantePostergado);

			if(!(chkCambioNombre.isChecked())){
//				Double penalidad=dblbxPenalidad.getValue()!=null?dblbxPenalidad.getValue():0;

				if(!(chkFechaAbierta.isChecked())){//Si no es fecha Abierta
					//Valida si el son itinerarios diferentes, se asume que es una postergacion
					if(chkCambioBoletaFactura.isChecked() && (
							(txtItinerarioActual.getText().equals(txtItinerarioPostergado.getText()) && txtNumeroAsientoActual.getText().equals(txtNumeroAsientoPostergado.getText()))
							|| txtItinerarioPostergado.getText().isEmpty())  ){
						copiarDatosVenta();
					}else if(!chkCambioBoletaFactura.isChecked() && txtItinerarioActual.getText().equals(txtItinerarioPostergado.getText())) {
						//Si los itinerarios son iguales, el valor de la penalidad es cero
						dblbxPenalidad.setValue(0);
						limpiarControlesPostergacion();
					}
//					else if (chkCambioBoletaFactura.isChecked() && !(txtItinerarioActual.getText().equals(txtItinerarioPostergado.getText()))){
//						//Si los itinerarios son diferentes, se resta la penalidad.
//						dblbxPenalidad.setValue(penalidad+Constantes.PENALIDAD_CAMBIO_NOMBRE);
//					}else if (chkCambioBoletaFactura.isChecked()==false && penalidad>0){
//						dblbxPenalidad.setValue(penalidad-Constantes.PENALIDAD_CAMBIO_NOMBRE);
//					}

				}
				calcularPagos();
			}
		} catch (Exception e) {
			e.printStackTrace();
			DlgMessage.error(e.getMessage());
		}
	}

	private void chkCambioFacturaBoleta_onCheck(){
		try {
			/*cambia el tipo de comprobante*/
			onSelectDefaultTipoComprobante(cmbTipoComprobante);
			onSelectDefaultTipoComprobante(cmbtipoComprobantePostergado);
			onLoadEspecieValorada(txtNumeroBoletoPostergado, cmbtipoComprobantePostergado);

			if(!(chkCambioNombre.isChecked())){
				if(!(chkFechaAbierta.isChecked())){//Si no es fecha Abierta
					//Valida si el son itinerarios diferentes, se asume que es una postergacion
					if(chkCambioFacturaBoleta.isChecked() && (
							(txtItinerarioActual.getText().equals(txtItinerarioPostergado.getText()) && txtNumeroAsientoActual.getText().equals(txtNumeroAsientoPostergado.getText()))
							|| txtItinerarioPostergado.getText().isEmpty())  ){
						copiarDatosVenta();
					}else if(!chkCambioFacturaBoleta.isChecked() && txtItinerarioActual.getText().equals(txtItinerarioPostergado.getText())) {
						//Si los itinerarios son iguales, el valor de la penalidad es cero
						dblbxPenalidad.setValue(0);
						limpiarControlesPostergacion();
					}
				}
				calcularPagos();
			}
		} catch (Exception e) {
			e.printStackTrace();
			DlgMessage.error(e.getMessage());
		}
	}

	public void buscarVentas(){
		try{
			lbxVentas.getItems().clear();
			grdPostergacion.setVisible(false);
			Integer idOrigen = cmbOrigen.getSelectedItem().getValue()==null?null:((Localidad)cmbOrigen.getSelectedItem().getValue()).getId();
			Integer idDestino = cmbDestino.getSelectedItem().getValue()==null?null:((Localidad)cmbDestino.getSelectedItem().getValue()).getId();
			String[] pasajero = txtPasajero.getText().trim().equals("")?null:txtPasajero.getText().trim().toUpperCase().split(" ");
			String numeroControl = txtNumeroControl.getText().trim().equals("")?null:txtNumeroControl.getText().trim();
			if(numeroControl!=null){
				numeroControl = Util.generateControlNumber(txtNumeroControl.getText().trim().toUpperCase());
				txtNumeroControl.setText(numeroControl);
			}
//			String numeroBoleto = txtNumeroBoleto.getText().trim().equals("")?null:txtNumeroBoleto.getText().trim().toUpperCase();
			String numeroBoleto=null;
			if(!(txtNumeroBoleto.getText().trim().isEmpty())){
				numeroBoleto=Util.autocompleNumberBoleto(txtNumeroBoleto.getText().trim().toUpperCase());
				txtNumeroBoleto.setText(numeroBoleto);
			}
			String fechaPartida = dtbxFechaPartida.getValue()==null?null:
					(Util.DatetoString(dtbxFechaPartida.getValue(),Constantes.DATE_FORMAT).equals(Util.DatetoString(Constantes.FECHA_NULL, Constantes.DATE_FORMAT))?
							null:Util.DatetoString(dtbxFechaPartida.getValue(), Constantes.DATE_FORMAT));

			if(numeroControl==null && numeroBoleto == null){
				if(fechaPartida==null)
					throw new FechaViajeNoValidaException();
				else if(idOrigen==null && idDestino==null && pasajero==null)
					throw new CriteriosBusquedaIncompletosException();
			}

			txtNumeroControl.addEventListener(Events.ON_FOCUS, new EventListener<Event>() {
				@Override
				public void onEvent(Event e){
					txtNumeroControl.setSelectionRange(0, txtNumeroControl.getText().length());
				}
			});

			List<VentaPasaje> lstReservas = ServiceLocator.getVentaPasajesManager().buscarVentasPostergar(idOrigen, idDestino, pasajero, numeroControl, numeroBoleto, fechaPartida);
			if(lstReservas.size()>0){
				lbxVentas.getItems().clear();
				Listitem item = null;
				Listcell cell = null;
				for(VentaPasaje ventaPasaje : lstReservas){
					item = new Listitem();
					cell = new Listcell(ventaPasaje.getId().toString());
					item.appendChild(cell);
					cell = new Listcell(ventaPasaje.getTipoMovimiento().getDenominacion());
					item.appendChild(cell);
					cell = new Listcell(ventaPasaje.getNumeroControl());
					item.appendChild(cell);
					cell = new Listcell(ventaPasaje.getNumeroBoleto());
					item.appendChild(cell);
					cell = new Listcell(ventaPasaje.getPasajero().toString());
					item.appendChild(cell);
					cell = new Listcell(ventaPasaje.getPasajero().getTipoDocumento().getDenominacion());
					item.appendChild(cell);
					cell = new Listcell(ventaPasaje.getPasajero().getNumeroDocumento());
					item.appendChild(cell);
					cell = new Listcell(ventaPasaje.getRuta().getOrigen());
					item.appendChild(cell);
					cell = new Listcell(ventaPasaje.getRuta().getDestino());
					item.appendChild(cell);
					cell = new Listcell(Util.DatetoString(ventaPasaje.getFechaInsercion(), Constantes.DATE_TIME_FORMAT));
					item.appendChild(cell);
					item.setValue(ventaPasaje);


//					item.addEventListener(Events.ON_DOUBLE_CLICK, new EventListener<Event>() {
//						public void onEvent(Event e)throws Exception{
//							try{
//								VentaPasaje venta = (VentaPasaje)lbxVentas.getSelectedItem().getValue();
//								secuencial = venta.getSecuencial();
//								postergacion = (VentaPasaje)venta.clone();
//								postergacion.setVentaPasaje(venta);
//
//								if(venta.getTipoMovimiento().getId().intValue()==Constantes.ID_TIPMOV_ANULACION_SISTEMA)
//									throw new PostergacionByTipoMovimientoNoPermitidoException(Constantes.ID_TIPMOV_ANULACION_SISTEMA);
//								else if(venta.getTipoMovimiento().getId().intValue()==Constantes.ID_TIPMOV_FECHA_ABIERTA)
//									throw new PostergacionByTipoMovimientoNoPermitidoException(Constantes.ID_TIPMOV_FECHA_ABIERTA);
//								else if(venta.getTipoMovimiento().getId().intValue()==Constantes.ID_TIPMOV_POSTERGACION_FA)
//									throw new PostergacionByTipoMovimientoNoPermitidoException(Constantes.ID_TIPMOV_POSTERGACION_FA);
//								else if(venta.getFormaPago().getId().intValue()==Constantes.ID_FORPAG_CREDITO && venta.getRucClienteCredito()!=null){
//									Agencia agencia = ServiceLocator.getAgenciaManager().buscarAgenciaByRucClienteCredito(venta.getRucClienteCredito());
//									if(agencia.getTipoAgencia().getId().intValue()==Constantes.ID_TIPAGE_CORPORATIVO)
//										throw new PostergacionByTipoAgenciaNoPermitidoException(Constantes.ID_TIPAGE_CORPORATIVO);
//								}else if(venta.getFormaPago().getId().intValue()==Constantes.ID_FORPAG_CORTESIA)
//									throw new PostergacionByFormaPagoNoPermitidoException(Constantes.ID_FORPAG_CORTESIA);
//
//								if(postergacion.getSecuencial().intValue() >= Constantes.MAXIMO_POSTERGACIONES)
//									throw new LimiteSecuencialException();
//
//								if(Util.comparaFechas(postergacion.getFechaCaducidad(), new Date(), Util.OPER_MENOR))
//									throw new FechaCaducidadNullException();
//
//								if(ServiceLocator.getDetalleManifiestoManager().validarVentaManifiesto(postergacion.getId()))
//									throw new ManifiestoImpresoException();
//
//								String fechaPartida = Util.DatetoString(venta.getFechaPartida(),Constantes.DATE_FORMAT)+" "+venta.getHoraPartida()+":00";
//								Long limite = Util.StringtoDate(fechaPartida, Constantes.DATE_TIME_FORMAT).getTime()-(Constantes.MILISEGUNDOS_X_HORA * Constantes.TIEMPO_LIMITE_POSTERGACION);
//								@SuppressWarnings("unused")
//								String fechaLimitePostergar = Util.DatetoString(new Date(limite), Constantes.DATE_TIME_FORMAT);
////								if(Util.comparaFechasWithTime(ServiceLocator.getVentaPasajesManager().getDateSystem(), fechaLimitePostergar, Util.OPER_MAYOR))
////									throw new PostergacionByFechaLimitePostergarException();
//
//								mostrarEstadoVenta(venta);
//								onSelectDefaultTipoComprobante();
//								onSelectDefaultFormaPago();
//								grdPostergacion.setVisible(true);
//								tabDetalle.setSelected(true);
//								imgBuscarItinerario.setVisible(true);
//							}catch(PostergacionByTipoMovimientoNoPermitidoException ptmnpex){
//								if(ptmnpex.getTipoMovimiento().intValue()==Constantes.ID_TIPMOV_ANULACION_SISTEMA)
//									DlgMessage.information(Messages.getString("WndPostergacion.information.postergacionByAnulacionNoPermitido"));
//								else if(ptmnpex.getTipoMovimiento().intValue()==Constantes.ID_TIPMOV_FECHA_ABIERTA || ptmnpex.getTipoMovimiento().intValue()==Constantes.ID_TIPMOV_POSTERGACION_FA)
//									DlgMessage.information(Messages.getString("WndPostergacion.information.postergacionByFechaAbiertaNoPermitido"));
//							}catch(PostergacionByFormaPagoNoPermitidoException pfpnpex){
//								DlgMessage.information(Messages.getString("WndPostergacion.information.postergacionByCortesiaNoPermitido"));
//							}catch(PostergacionByTipoAgenciaNoPermitidoException ptanpex){
//								DlgMessage.information(Messages.getString("WndPostergacion.information.postergacionByCorporativoNoPermitido"));
//							}catch(PostergacionByFechaLimitePostergarException pflpex){
//								DlgMessage.information(Messages.getString("WndPostergacion.information.postergacionByFechaLimitePostergar"));
//							}catch(LimiteSecuencialException lsex){
//								DlgMessage.information(Messages.getString("WndPostergacion.information.limiteSecuencial"));
//							}catch(FechaCaducidadNullException fcnex){
//								DlgMessage.information(Messages.getString("WndPostergacion.information.fechaCaducidad"));
//							}catch(ManifiestoImpresoException miex){
//								DlgMessage.information(Messages.getString("Generales.information.manifiestoImpreso"));
//							}catch(Exception ex){
//								DlgMessage.error(this.getClass().getSimpleName()+" "+ex.getMessage());
//							}
//						}
//					});

					lbxVentas.appendChild(item);
				}
				tabListado.setSelected(true);
			}else{
				DlgMessage.information(Messages.getString("WndPostergacion.information.noVentas"));
				dtbxFechaPartida.setValue(null);
			}
		}catch(FechaViajeNoValidaException fvnvex){
			DlgMessage.information(Messages.getString("WndPostergacion.informacion.noFechaViaje"), dtbxFechaPartida);
		}catch(CriteriosBusquedaIncompletosException cbiex){
			DlgMessage.information(Messages.getString("WndPostergacion.informacion.noCriteriosbusqueda"), txtNumeroControl);
		}catch(Exception ex){
//			DlgMessage.error(this.getClass().getSimpleName()+" "+ex.getMessage());
			ex.printStackTrace();
		}
	}

	private void mostrarEstadoVenta(VentaPasaje venta){
		try{
			limpiarControles();

			if(venta!=null){
				venta = ServiceLocator.getVentaPasajesManager().buscarVentaById(venta.getId());
				txtNumeroControlActual.setText(venta.getNumeroControl());
				txtItinerarioActual.setText(venta.getItinerario().getId().toString());
				txtNumeroAsientoActual.setText(venta.getNumeroAsiento().toString());
				txtPasajeroActual.setText(venta.getPasajero().toString());
				if(venta.getCliente()!=null)
					txtClienteActual.setText(venta.getCliente().getRazonSocial());
				else txtClienteActual.setText("");
				txtNumeroBoletoActual.setText(venta.getNumeroBoleto());
				txtOrigenActual.setText(venta.getRuta().getOrigen());
				txtDestinoActual.setText(venta.getRuta().getDestino());
				txtEmbarqueActual.setText(venta.getAgenciaPartida().getDenominacion());
				txtHoraEmbarqueActual.setText(venta.getHoraPartida());
				txtServicioActual.setText(venta.getServicio().getDenominacion());
				txtSalidaActual.setText(Util.DatetoString(venta.getFechaPartida(), Constantes.DATE_FORMAT)+" "+venta.getHoraPartida());

				//Nuevos datos de la venta
				txtPasajeroPostergado.setText(venta.getPasajero().toString());
				if(venta.getCliente()!=null){
					Cliente cliente=ServiceLocator.getClienteManager().buscarPorId(postergacion.getCliente().getId());
					txtClienteRuc.setText(cliente.getNumeroDocumento());
					txtClientePostergado.setText(cliente.getRazonSocial());
					txtClienteDireccion.setText(cliente.getDireccion()!=null?cliente.getDireccion():"");
					postergacion.setCliente(cliente);
				} else{
					txtClienteRuc.setText("");
					txtClientePostergado.setText("");
					txtClienteDireccion.setText("");
				}
//				String boleto = UtilData.buscarEspecieValorada(Constantes.ID_TIPCOM_BOLETO_VIAJE, usuarioHardware.getId());
//				EspecieValorada especieValorada=UtilData.buscarEspecieValorada(venta.getTipoComprobante().getId(), getAgencia(), true);
//				String boleto =especieValorada.toString();
//				txtNumeroBoletoPostergado.setText(boleto);

				onLoadEspecieValorada(txtNumeroBoletoPostergado, cmbtipoComprobantePostergado);

				dblbxMontoAnterior.setValue(venta.getTarifa()+venta.getRecargo()-venta.getDescuento());
				txtIdPromocion.setText(venta.getPromocion()==null?"":venta.getPromocion().getId().toString());


				/*##Valida si es una factura para habilitar ciertas opciones - 07/11/2016 - jabanto*/
				boolean disabledOpFacturas=true;
				if(venta.getCliente()!=null && venta.getCliente()!=null) // venta.getTipoComprobante().getId().intValue()==Constantes.ID_TIPCOM_FACTURA)
					disabledOpFacturas=false;

				chkCambioRuc.setDisabled(disabledOpFacturas);
				chkCambioRazonSocial.setDisabled(disabledOpFacturas);
				chkCambioDireccionFiscal.setDisabled(disabledOpFacturas);
				chkCambioFacturaBoleta.setDisabled(disabledOpFacturas);
				chkCambioBoletaFactura.setDisabled(!disabledOpFacturas);
			}
		}catch(PostergacionByTipoMovimientoNoPermitidoException ptmnpex){
			if(ptmnpex.getTipoMovimiento().intValue()==Constantes.ID_TIPMOV_ANULACION_SISTEMA)
				DlgMessage.information(Messages.getString("WndPostergacion.information.postergacionByAnulacionNoPermitido"));
			else if(ptmnpex.getTipoMovimiento().intValue()==Constantes.ID_TIPMOV_FECHA_ABIERTA)
				DlgMessage.information(Messages.getString("WndPostergacion.information.postergacionByFechaAbiertaNoPermitido"));
		}catch(PostergacionByFormaPagoNoPermitidoException pfpnpex){
			DlgMessage.information(Messages.getString("WndPostergacion.information.postergacionByCortesiaNoPermitido"));
		}catch(PostergacionByTipoAgenciaNoPermitidoException ptanpex){
			DlgMessage.information(Messages.getString("WndPostergacion.information.postergacionByCorporativoNoPermitido"));
		}catch(Exception ex){
			ex.printStackTrace();
			DlgMessage.error(this.getClass().getSimpleName()+" "+ex.getMessage());
		}
	}
	/**
	 * Realiza la busqueda del correlativo para el boleto a emitir.
	 * @throws Exception
	 */
	private void onLoadEspecieValorada(Textbox txtBoleto, Combobox comboTipoComprobante) throws Exception{
		/*BEGIN 15/06/2021 - javalos - Correlativo by caja*/
		EspecieValorada especieValorada=null;
		ControlEspecieValorada controlEspecieValorada = null;
		/*END 15/06/2021 - javalos - Correlativo by caja*/
		if(agencia.getTipoAgencia().getId().intValue()==Constantes.ID_TIPAGE_TEPSA){
			/*BEGIN 15/06/2021 - javalos - Correlativo by caja*/
//			especieValorada=UtilData.buscarEspecieValorada(((TipoComprobante)comboTipoComprobante.getSelectedItem().getValue()).getId(), getAgencia(),false);
//			txtBoleto.setValue(especieValorada.toString());
			controlEspecieValorada = UtilData.buscarEspecieValoradaByCaja(((TipoComprobante)comboTipoComprobante.getSelectedItem().getValue()).getId(), getAgencia(), false, getUsuarioHardware(), null);
			txtBoleto.setValue(controlEspecieValorada.toString());
			/*END 15/06/2021 - javalos - Correlativo by caja*/
		}else if(agencia.getTipoAgencia().getId().intValue()==Constantes.ID_TIPAGE_VIAJES){
			especieValorada=UtilData.buscarEspecieValorada(Constantes.ID_TIPCOM_VOUCHER_AGENCIA_VIAJES, agencia,false);
			txtBoleto.setValue(especieValorada.toString());
		}else{
			especieValorada=UtilData.buscarEspecieValorada(Constantes.ID_TIPCOM_VOUCHER_CORPORATIVO, agencia,false);
			txtBoleto.setValue(especieValorada.toString());
		}
	}
	/**
	 * Permite enlazar los controles a la ventana de selección de Itinerario
	 * @param textboxItinerario :en este Textbox se devolvera el Id del itinerario seleccionado.
	 * @param button :ha este Button se le adjuntara un listener con la llamada a la ventana de selección de itinerario
	 * @see WndItinerario:
	 */
	public void enlazarItinerario(final Image image) {
		image.addEventListener(Events.ON_CLICK, new EventListener<Event>() {
			@Override
			public void onEvent(Event e)throws Exception{
				final WndSeleccionaItinerario oWndSeleccionarItinerario = new WndSeleccionaItinerario();
				wndPostergacion.appendChild(oWndSeleccionarItinerario);
				oWndSeleccionarItinerario.onCreate();
				oWndSeleccionarItinerario.setMode(MODAL);
				oWndSeleccionarItinerario.setOrigen(txtOrigenActual.getText().trim());
				oWndSeleccionarItinerario.setDestino(txtDestinoActual.getText().trim());
				oWndSeleccionarItinerario.asignarValores();
				oWndSeleccionarItinerario.addEventListener(Events.ON_SELECT, new EventListener<Event>() {
					@Override
					public void onEvent(Event e) throws Exception{
						if(!txtItinerarioPostergado.getText().isEmpty()){
							liberarAsientos();
							System.out.println("==================================================================================================");
						}

						/*Valida si la ruta esta configurada para permitir la venta antes o despuesta de la hora de salida ## impl 10/11/2014 - jabanto*/
						txtItinerarioPostergado.setText("");
						imgSeleccionarAsiento.setVisible(false);
						detalleItinerario=null;
						DetalleItinerario detalleItinerario= oWndSeleccionarItinerario.getDetalleItinerario();
						if (!(UtilData.permiteVentaByTramo(detalleItinerario.getRuta().getId(), detalleItinerario.getItinerario().getId(),Constantes.FORMAT_DATE.format(detalleItinerario.getFechaPartida())))){
							DlgMessage.information(Messages.getString("WndVentaReserva.information.ventaNoPermitida"));
							return;
						}

						loadDatosPostergacion(oWndSeleccionarItinerario.getIdDetalleItinerario());
						imgSeleccionarAsiento.setVisible(true);
					}
				});

			}
		});
	}

	private void loadDatosPostergacion(Long idDetalleItinerario){
		try{

			promocionAplicada = null;
			detalleItinerario = ServiceLocator.getDetalleItinerarioManager().buscarPorId(idDetalleItinerario);
			if(detalleItinerario.getItinerario().getFechaRealPartida()==null){
				/*	Seteando el objeto postergacion con los nuevos datos para la venta	*/
				postergacion.setItinerario(detalleItinerario.getItinerario());
				postergacion.setServicio(detalleItinerario.getItinerario().getServicio());
				postergacion.setRuta(detalleItinerario.getRuta());
				postergacion.setFechaPartida(detalleItinerario.getFechaPartida());
				postergacion.setHoraPartida(detalleItinerario.getHoraPartida());
				postergacion.setFechaLlegada(detalleItinerario.getFechaLlegada());
				postergacion.setHoraLllegada(detalleItinerario.getHoraLlegada());
				postergacion.setDetalleItinerario(detalleItinerario);
				/*	Seteando los datos a las cajas de texto	*/
				txtItinerarioPostergado.setText(detalleItinerario.getItinerario().getId().toString());
				txtOrigenPostergado.setText(detalleItinerario.getRuta().getOrigen());
				txtDestinoPostergado.setText(detalleItinerario.getRuta().getDestino());
				txtServicioPostergado.setText(detalleItinerario.getItinerario().getServicio().getDenominacion());
				txtSalidaPostergado.setText(Util.DatetoString(detalleItinerario.getFechaPartida(), Constantes.DATE_FORMAT));
				onLoadPuntoEmbarque(detalleItinerario);
//				dblbxTarifa.setValue(detalleItinerario.getTarifa()<postergacion.getTarifa()?postergacion.getTarifa():detalleItinerario.getTarifa());
//				if(postergacion.getCliente()!=null){
//					LineaContadoCliente lineaContadoCliente=ServiceLocator.getLineaContadoClienteManager().validaDescuentoCliente(postergacion.getCliente().getId());
//					if(lineaContadoCliente !=null){
//						lblDescuento.setValue(Constantes.TIPCONVCLI_CONTADO_DESCUENTO);//+" ("+lineaContadoCliente.getDescuentoBaja()+" "+"% Dsct.)");
////						Double dscto = aplicarPorcentajeDescuento(lineaContadoCliente.getDescuentoBaja());
////						dblbxSaldo.setValue(dblbxTarifa.getValue() - dblbxMontoAnterior.getValue() - dscto);
////						postergacion.setDescuento(dscto);
//					}else{
//						lblDescuento.setValue("");
////						dblbxSaldo.setValue(dblbxTarifa.getValue()-dblbxMontoAnterior.getValue()>0.0?dblbxTarifa.getValue()-dblbxMontoAnterior.getValue():0.0);
//					}
//				}else{
//					lblDescuento.setValue("");
////					dblbxSaldo.setValue(dblbxTarifa.getValue()-dblbxMontoAnterior.getValue()>0.0?dblbxTarifa.getValue()-dblbxMontoAnterior.getValue():0.0);
//				}

//				if(!txtIdPromocion.getText().isEmpty()){
//					Promocion promocion = ServiceLocator.getPromocionManager().buscarPorId(Long.valueOf(txtIdPromocion.getText()));
//					AplicarPromocion aplicarPromocion = createObjectAplicarPromocion();
//					promocionAplicada = aplicarPromocion.executePromocion(promocion.getId().toString(), false);
//					if(promocionAplicada!=null){
//						imgQuitarPromocion.setVisible(true);
//					}
//				}
//				imgBuscarPasajero.setVisible(true);

				//Buscando al venta originar
//				final VentaPasaje ventaOriginal=ServiceLocator.getVentaPasajesManager().buscarPorId(postergacion.getId());

				//Buscando el tipo de nota de credito a aplicar
//				tipoNotaCredito=ServiceLocator.getTipoNotaManager().buscarPorId((long)Constantes.ID_TIPNOTA_POSTERGACION);

				VentaPasaje venta=lbxVentas.getSelectedItem().getValue();
				/*Valida si a cambiado el destino*/
				if(postergacion.getRuta().getId().intValue()!=venta.getRuta().getId().intValue()){
					chkCambioDestino.setChecked(true);
					chkCambioDestino.setDisabled(true);
				}

				/*Valida si a cambiado el servicio*/
				if(postergacion.getServicio().getId().intValue()!=venta.getServicio().getId().intValue()){
					chkCambioServicio.setChecked(true);
					chkCambioServicio.setDisabled(true);
				}


				/*Valida si se ha cambiado el nombre del pasajero*/
//				if(chkCambioNombre.isChecked() && postergacion.getPasajero().getId().longValue()!=ventaOriginal.getPasajero().getId().longValue()){
				calcularPagos();
//				}

				/*##End begin 03/11/2016 **/
//				if(chkCambioNombre.isChecked() || rdCambioRuc.isChecked())
//					calcularPagos(Constantes.PENALIDAD_POSTERGACION+Constantes.PENALIDAD_CAMBIO_NOMBRE);
//				else
//					calcularPagos(Constantes.PENALIDAD_POSTERGACION);

//				dblbxSaldo.setValue(dblbxTarifa.getValue()-dblbxMontoAnterior.getValue()>0.0?dblbxTarifa.getValue()-dblbxMontoAnterior.getValue()-dblbxDescuento.getValue():0.0);
//				dblbxPenalidad.setValue(Constantes.PENALIDAD_POSTERGACION);
//				dblbxImporteTotal.setValue(dblbxSaldo.getValue()+dblbxPenalidad.getValue());
			}else
				DlgMessage.information(Messages.getString("WndPostergacion.information.postergacionByItinerarioDespachado"));
		}catch(Exception ex){
			DlgMessage.error(this.getClass().getSimpleName()+" "+ex.getMessage());
		}
	}

//	private Double aplicarPorcentajeDescuento(Double porcentajeDesct){
//		Double dsct=.00;
//		if(porcentajeDesct>0){
//			dsct=dblbxTarifa.getValue()*(porcentajeDesct/100);
//		}
//		return dsct;
//	}

	/**
	 * Cargamos los puntos de embarque.
	 * @param detItinerario	: Itinerario del cual deseamos cargar los puntos de embarque.
	 * @throws Exception
	 */
	private void onLoadPuntoEmbarque(DetalleItinerario detItinerario){
		try{
			cmbEmbarquePostergado.getItems().clear();

			ArrayList<ItinerarioAgenciaPartida> arrayItiAgePartida = new ArrayList<>();
			arrayItiAgePartida = ServiceLocator.getItinerarioManager().buscarAgenciasPartida(detItinerario.getItinerario().getId(), Constantes.VALUE_ACTIVO, detItinerario.getRuta().getLocalidadOrigen().getId());
//			if(detItinerario.getItinerario().getAgenciaPartida().getId().intValue()==detItinerario.getAgenciaPartida().getId().intValue())
//				arrayItiAgePartida = ServiceLocator.getItinerarioManager().buscarAgenciasPartida(detItinerario.getItinerario().getId(), Constantes.VALUE_ACTIVO);
//			else{
//				ItinerarioAgenciaPartida itiAgePartida = new ItinerarioAgenciaPartida();
//				Agencia agencia = new Agencia();
//				agencia.setId(detItinerario.getAgenciaPartida().getId());
//				agencia.setDenominacion(detItinerario.getAgenciaPartida().getDenominacion());
//				itiAgePartida.setAgencia(agencia);
//				itiAgePartida.setHoraPartida(detItinerario.getAgenciaPartida().getHoraPartida());
//				arrayItiAgePartida.add(itiAgePartida);
//			}
			UtilData.cargarGenericData(cmbEmbarquePostergado, false);
			for(ItinerarioAgenciaPartida itiAgePartida : arrayItiAgePartida){
				Comboitem item = new Comboitem(itiAgePartida.getAgencia().getDenominacion());
				item.setValue(itiAgePartida);
				cmbEmbarquePostergado.appendChild(item);
				if(arrayItiAgePartida.size()==1){
					cmbEmbarquePostergado.setSelectedItem(item);
					txtHoraEmbarquePostergado.setValue(itiAgePartida.getHoraPartida());
				}else if(detItinerario.getAgenciaPartida().getId().intValue()==itiAgePartida.getAgencia().getId().intValue()){
					cmbEmbarquePostergado.setSelectedItem(item);
					txtHoraEmbarquePostergado.setValue(itiAgePartida.getHoraPartida());
				}
			}
			txtSalidaPostergado.setText(Util.DatetoString(detItinerario.getFechaPartida(), Constantes.DATE_FORMAT)+" "+txtHoraEmbarquePostergado.getText());
		}catch(Exception ex){
			ex.printStackTrace();
			DlgMessage.error(this.getClass().getName()+" "+ex.getMessage());
		}
	}

	/**
	 * Muestra la hora de embarque segun el punto de embarque seleccionado
	 */
	public void onSelectPtoEmbarque(){
		if(cmbEmbarquePostergado.getSelectedItem().getValue() instanceof ItinerarioAgenciaPartida){
			txtHoraEmbarquePostergado.setValue(((ItinerarioAgenciaPartida)cmbEmbarquePostergado.getSelectedItem().getValue()).getHoraPartida());
			txtSalidaPostergado.setText(txtSalidaPostergado.getText().substring(0,10)+" "+txtHoraEmbarquePostergado.getText());
		}else{
			txtHoraEmbarquePostergado.setValue("");
		}
	}

	private void limpiarControles(){
		txtNumeroControlActual.setText("");
		txtItinerarioActual.setText("");
		txtNumeroAsientoActual.setText("");
		txtPasajeroActual.setText("");
		txtNumeroBoletoActual.setText("");
		txtOrigenActual.setText("");
		txtDestinoActual.setText("");
		txtEmbarqueActual.setText("");
		txtHoraEmbarqueActual.setText("");
		txtServicioActual.setText("");
		txtSalidaActual.setText("");

		txtItinerarioPostergado.setText("");
		txtNumeroAsientoPostergado.setText("");
		txtNumeroControlPostergado.setText("");
		txtPasajeroPostergado.setText("");
		txtNumeroBoletoPostergado.setText("");
		txtOrigenPostergado.setText("");
		txtDestinoPostergado.setText("");
		cmbEmbarquePostergado.getItems().clear();
		cmbEmbarquePostergado.setText("");
		txtHoraEmbarquePostergado.setText("");
		txtServicioPostergado.setText("");
		txtSalidaPostergado.setText("");
		txtClienteRuc.setText("");
		txtClienteDireccion.setText("");

		chkFechaAbierta.setChecked(false);
		chkCambioNombre.setChecked(false);
		chkCambioRuc.setChecked(false);

		chkFechaAbierta.setDisabled(false);
		chkCambioNombre.setDisabled(false);
		chkCambioRuc.setDisabled(false);

		/*##Begin 07/11/2016 - jabanto*/
		chkCambioRazonSocial.setChecked(false);
		chkCambioRuc.setChecked(false);
		chkCambioDireccionFiscal.setChecked(false);
		chkCambioBoletaFactura.setChecked(false);
		chkCambioFacturaBoleta.setChecked(false);

		dblbxMontoAnterior.setValue(0.0);
		dblbxTarifa.setValue(0.0);
		dblbxSaldo.setValue(0.0);
		dblbxPenalidad.setValue(0.0);
		dblbxImporteTotal.setValue(0.0);
		dblbxImporteEfectivo.setValue(0.0);
		dblbxImporteTarjeta.setValue(0.0);
		dblbxDescuento.setValue(0.0);

		cmbTipoComprobante.setSelectedIndex(0);
		cmbFormaPago.setSelectedIndex(0);
		cmbTipoFormaPago.setText("");
		cmbTipoFormaPago.getItems().clear();
		cmbTipoFormaPago.setDisabled(true);
		cmbOperadorTarjetaCredito.setText("");
		cmbOperadorTarjetaCredito.getItems().clear();
		cmbOperadorTarjetaCredito.setDisabled(true);
		cmbTarjetaCredito.setText("");
		cmbTarjetaCredito.getItems().clear();
		cmbTarjetaCredito.setDisabled(true);

		lblPromocion.setValue("");
		imgQuitarPromocion.setVisible(false);
		imgBuscarPasajero.setVisible(false);
		imgBuscarPasajero.setVisible(false);
		imgBuscarCliente.setVisible(false);
		imgSeleccionarAsiento.setVisible(false);
		imgBuscarClienteRuc.setVisible(false);
		imgBuscarClienteDireccion.setVisible(false);

	}

	private void limpiarControlesPostergacion(){
		txtItinerarioPostergado.setText("");
		txtNumeroAsientoPostergado.setText("");
		txtNumeroControlPostergado.setText("");
//		txtPasajeroPostergado.setText("");
//		txtNumeroBoletoPostergado.setText("");
		txtOrigenPostergado.setText("");
		txtDestinoPostergado.setText("");
		cmbEmbarquePostergado.getItems().clear();
		cmbEmbarquePostergado.setText("");
		txtHoraEmbarquePostergado.setText("");
		txtServicioPostergado.setText("");
		txtSalidaPostergado.setText("");
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
					Comboitem item = new Comboitem();
					item.setLabel(tipoFormaPago.getDenominacion());
					item.setValue(tipoFormaPago);
					cmbTipoFormaPago.appendChild(item);
				}
				cmbTipoFormaPago.setDisabled(false);

				/*30/12/2016 - jabanto*/
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
	public void onValidateTipoFormaPago() throws Exception{
		if(cmbTipoFormaPago.getSelectedItem().getValue() instanceof TipoFormaPago){
			/*	Si es tarjeta cargamos los operadores de tarjeta de credito	*/
//			if(cmbTipoFormaPago.getText().equals("TARJETA")){
			if(((TipoFormaPago)cmbTipoFormaPago.getSelectedItem().getValue()).getId().intValue()==Constantes.ID_TIPFORPAG_TARJETA){
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

		calcularPagos();
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
				TreeMap<String, Object> criteriosBusqueda = new TreeMap<>();
				criteriosBusqueda.put("operadorTarjetaCredito.id", operadorTarjetaCredito.getId());
				List<String> criteriosOrdenar = new ArrayList<>();
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

	public void guardar(){
		try{
			if(!chkFechaAbierta.isChecked()){
				if(detalleItinerario==null)
					throw new ItinerarioException(ItinerarioException.NO_SELECT);// ItinerarioNotSelectedException();
				else if(txtNumeroAsientoPostergado.getText().trim().equals(""))
					throw new NumeroAsientoNullException();
				if(txtNumeroBoletoPostergado.getText().trim().equals(""))
					throw new NumeroBoletoNullException();
				else if(!(cmbEmbarquePostergado.getSelectedItem().getValue() instanceof ItinerarioAgenciaPartida))
					throw new ItinerarioException(ItinerarioException.AGENCIA_PARTIDA_NULL); // ItinerarioAgenciaPartidaNullException();
			}
			if(!(cmbtipoComprobantePostergado.getSelectedItem().getValue() instanceof TipoComprobante))
				throw new TipoComprobanteNullException();
			else if(!(cmbFormaPago.getSelectedItem().getValue() instanceof FormaPago))
				throw new FormaPagoNullException();
			else if(!(cmbTipoFormaPago.getSelectedItem().getValue() instanceof TipoFormaPago))
				throw new TipoFormaPagoNullException();
			else if(cmbTipoFormaPago.getSelectedItem().getValue() instanceof TipoFormaPago && cmbTipoFormaPago.getText().equals(TipoFormaPago.TIPO_TARJETA)){
				if(!(cmbOperadorTarjetaCredito.getSelectedItem().getValue() instanceof OperadorTarjetaCredito))
					throw new OperadorTarjetaCreditoNullException();
				else if(!(cmbTarjetaCredito.getSelectedItem().getValue() instanceof TarjetaCredito))
					throw new TarjetaCreditoNullException();
			}

			if(!chkFechaAbierta.isChecked() && detalleItinerario.getTarifa()==0.0)
				throw new ItinerarioException(ItinerarioException.TARIFA_IDA_CERO);

			if(chkCambioNombre.isChecked() && !chkCambioNombre.isDisabled()){
				throw new PostergacionNoCambioNombreException();
			}
			if (chkCambioRuc.isChecked() && !chkCambioRuc.isDisabled()){
				throw new PostergacionNoCambioRazonSozialException();
			}else if(chkCambioRazonSocial.isChecked() && !chkCambioRazonSocial.isDisabled()){
				DlgMessage.information(Messages.getString("WndPostergacion.information.noCambioRazonSocial"));
				return;
			}else if (chkCambioDireccionFiscal.isChecked() && !chkCambioDireccionFiscal.isDisabled()){
				DlgMessage.information(Messages.getString("WndPostergacion.information.noCambioDireccionFiscal"));
				return;
			}else if (chkCambioBoletaFactura.isChecked() && postergacion.getCliente()==null){
				DlgMessage.information(Messages.getString("WndPostergacion.information.noCambioBoletaFactura"));
				return;
			}else if (postergacion.getCliente()!=null && txtClienteDireccion.getText().trim().isEmpty()){
				DlgMessage.information(Messages.getString("WndPostergacion.information.noClienteSinDireccion"));
				return;
			}else if(chkPagoMixto.isChecked()){
				TipoFormaPago tipoFormaPago=cmbTipoFormaPago.getSelectedItem().getValue();
				if(tipoFormaPago.getId().intValue()!=Constantes.ID_TIPFORPAG_TARJETA){
					DlgMessage.information(Messages.getString("WndVentaReserva.information.importeMixtoNoSeleccionoTarjeta"),cmbTipoFormaPago);
					return;
				}
			}

			/*Validando el  tipo de nota de credito - 04/11/2016 - jabanto*/
			if(tipoNotaCredito==null){
				DlgMessage.information(Messages.getString("WndPostergacion.information.noTipoNota"));
				return;
			}


			if(chkPagoMixto.isChecked()){
				if(dblbxImporteEfectivo.getValue()<=0.0 || dblbxImporteTarjeta.getValue()<=0.0)
					throw new ImporteMixtoNullException(ImporteMixtoNullException.IMPORTE_MIXTO_CERO);
			}

			/*	Validando que el monto en efectivo + el monto en tarjeta sumen el importe pagado	*/
			if(chkPagoMixto.isChecked()){
				if(dblbxImporteTotal.getValue().doubleValue()!=(dblbxImporteEfectivo.getValue().doubleValue()+dblbxImporteTarjeta.getValue().doubleValue()))
					throw new ImporteMixtoNullException(ImporteMixtoNullException.IMPORTE_MIXTO_NOT_EQUALS);
			}

			FormaPago formaPago = (FormaPago)cmbFormaPago.getSelectedItem().getValue();
			postergacion.setFormaPago(formaPago);
			TipoComprobante tipoComprobante = (TipoComprobante)cmbtipoComprobantePostergado.getSelectedItem().getValue();
			postergacion.setTipoComprobante(tipoComprobante);
			TipoFormaPago tipoFormaPago = (TipoFormaPago)cmbTipoFormaPago.getSelectedItem().getValue();
			postergacion.setTipoFormaPago(tipoFormaPago);
			if(cmbTarjetaCredito.getSelectedItem()!=null && cmbTarjetaCredito.getSelectedItem().getValue() instanceof TarjetaCredito){
				TarjetaCredito tarjetaCredito = (TarjetaCredito)cmbTarjetaCredito.getSelectedItem().getValue();
				postergacion.setTarjetaCredito(tarjetaCredito);
			}else
				postergacion.setTarjetaCredito(null);

//			postergacion.setId(null);
			postergacion.setTipoNota(tipoNotaCredito);
			postergacion.setNumeroBoleto(txtNumeroBoletoPostergado.getText().equals("")?null:txtNumeroBoletoPostergado.getText());
			if(chkFechaAbierta.isChecked()){
				postergacion.setNumeroAsiento(null);
				postergacion.setFechaPartida(null);
				postergacion.setHoraPartida(null);
				postergacion.setFechaLlegada(null);
				postergacion.setHoraLllegada(null);
				postergacion.setAgenciaPartida(null);
				postergacion.setAgenciaLlegada(null);
				postergacion.setNumeroPiso(null);
				postergacion.setEsFechaAbierta(Constantes.TRUE_VALUE);
				postergacion.setTipoMovimiento(new TipoMovimiento(Constantes.ID_TIPMOV_POSTERGACION_FA));
				postergacion.setFechaCaducidad(new Date(new Date().getTime()+(Constantes.TIEMPO_CADUCA_BOLETO*Constantes.MILISEGUNDOS_X_DIA)));
			}else{
				postergacion.setNumeroAsiento(Integer.valueOf(txtNumeroAsientoPostergado.getText()));
				postergacion.setFechaPartida(detalleItinerario.getFechaPartida());
				postergacion.setHoraPartida(detalleItinerario.getHoraPartida());
				postergacion.setFechaLlegada(detalleItinerario.getFechaLlegada());
				postergacion.setHoraLllegada(detalleItinerario.getHoraLlegada());
				ItinerarioAgenciaPartida itinerarioAgenciaPartida = (ItinerarioAgenciaPartida)cmbEmbarquePostergado.getSelectedItem().getValue();
				postergacion.setAgenciaPartida(itinerarioAgenciaPartida.getAgencia());
				postergacion.setAgenciaLlegada(postergacion.getVentaPasaje().getAgenciaLlegada());
				postergacion.setNumeroPiso(Integer.valueOf(txtNumeroPisoPostergado.getText()));
				postergacion.setEsFechaAbierta(Constantes.FALSE_VALUE);

				/*Calcula la fecha de caducidad del boleto - 13/12/2016 - jabanto*/
				String fechaCaducidad=Constantes.FORMAT_DATE.format(postergacion.getFechaPartida())+" "+postergacion.getHoraPartida();
				Date dateCaducidad=Constantes.FORMAT_LONG.parse(fechaCaducidad);
				postergacion.setFechaCaducidad(dateCaducidad);

				//Valida si se trata de una postergacion
				if(!(txtItinerarioActual.getText().equals(txtItinerarioPostergado.getText())) ||
						(txtItinerarioActual.getText().equals(txtItinerarioPostergado.getText()) && !(txtNumeroAsientoActual.getText().equals(txtNumeroAsientoPostergado.getText())) ) ){
					postergacion.setTipoMovimiento(new TipoMovimiento(Constantes.ID_TIPMOV_POSTERGACION));

				}else{
					/*Valida si es un comprobante corporativo - 30/12/2016 - jabanto*/
					if(isCorporativo)
						postergacion.setTipoMovimiento(new TipoMovimiento(Constantes.ID_TIPMOV_CREDITO));
					else
						postergacion.setTipoMovimiento(new TipoMovimiento(Constantes.ID_TIPMOV_EFECTIVO));
				}

			}

			/*Coloca en observaciones una glosa segun la(s) operaciones realizadas*/
			String observaciones="";
			if(chkFechaAbierta.isChecked())
				observaciones="POSTERGACION F.A.";
			if(!(txtItinerarioPostergado.getText().isEmpty()) && !(txtItinerarioPostergado.getText().equals(txtItinerarioActual.getText())))
				observaciones+=(observaciones.length()>0?";POSTERGACION":"POSTERGACION");
			if(chkCambioDestino.isChecked())
				observaciones+=(observaciones.length()>0?";CAMBIO DESTINO":"CAMBIO DESTINO");
			if(chkCambioServicio.isChecked())
				observaciones+=(observaciones.length()>0?";CAMBIO SERVICIO":"CAMBIO DE SERVICIO");
			if(chkCambioNombre.isChecked())
				observaciones+=(observaciones.length()>0?";CAMBIO DE NOMBRE":"CAMBIO DE NOMBRE");
			if(chkCambioRuc.isChecked())
				observaciones+=(observaciones.length()>0?";CAMBIO DE RUC":"CAMBIO DE RUC");
			if(chkCambioRazonSocial.isChecked())
				observaciones+=(observaciones.length()>0?";CAMBIO DE RAZON SOCIAL":"CAMBIO DE RAZON SOCIAL");
			if(chkCambioDireccionFiscal.isChecked())
				observaciones+=(observaciones.length()>0?";CAMBIO DE DIRECCION FISCAL":"CAMBIO DE DIRECCION FISCAL");
			if(chkCambioBoletaFactura.isChecked())
				observaciones+=(observaciones.length()>0?";CAMBIO DE BOLETA A FACTURA":"CAMBIO DE BOLETA A FACTURA");
			if(chkCambioFacturaBoleta.isChecked())
				observaciones+=(observaciones.length()>0?";CAMBIO DE FACTURA A BOLETA":"CAMBIO DE FACTURA A BOLETA");
			/*##End Begin 04/11/2016 - jabanto*/
			//Coloca como observaciones una glosa si es un cambio de nombre o razón social
//			if(chkCambioNombre.isChecked())
//				postergacion.setObservaciones("CAMBIO DE NOMBRE");
//			if(rdCambioRuc.isChecked()){
//				if(postergacion.getObservaciones()==null)
//					postergacion.setObservaciones("CAMBIO DE DE RAZÓN SOCIAL");
//				else{
//					String observacion=postergacion.getObservaciones()+"; "+"CAMBIO DE DE RAZÓN SOCIAL";
//					postergacion.setObservaciones(observacion);
//				}
//			}

			postergacion.setObservaciones(observaciones);
			postergacion.setSecuencial(secuencial+1);
			postergacion.setTarifa(dblbxTarifa.getValue());
			postergacion.setRecargo(postergacion.getRecargo());
			postergacion.setDescuento(dblbxDescuento.getValue());
//			postergacion.setPenalidad(dblbxPenalidad.getValue());
			postergacion.setPenalidad(0.0);
			postergacion.setAcuenta(0.0);
//			postergacion.setImportePagado(dblbxImporteTotal.getValue());
//			postergacion.setImportePagado(dblbxMontoAnterior.getValue()+dblbxSaldo.getValue()); //El nuevo Comprobante sera por el monto que pago el pax mas el saldo
			postergacion.setImportePagado(dblbxTarifa.getValue()-dblbxDescuento.getValue());
			postergacion.setImportePagadoEfectivo(dblbxImporteEfectivo.getValue());
			postergacion.setImportePagadoTarjeta(dblbxImporteTarjeta.getValue());
			postergacion.setTipoTransaccion(Constantes.TIPO_OPERACION_VENTA);
			postergacion.setFechaLiquidacion(fechaLiquidacion);
			postergacion.setAgencia(getAgencia());
			postergacion.setUsuario(getUsuario());
			postergacion.setCanalVenta(canalVenta);
			postergacion.setNumeroControl("-----");
			postergacion.setLiquidacion(null);
			postergacion.setFechaTransferencia(null);
			if(!isCorporativo){
				postergacion.setRucClienteCredito(null); // a solicitud de maroc - 28/09/2015
				postergacion.setEstadoDocumento(Constantes.ESTADO_DOCUMENTO_PAGADO);
			}
			postergacion.setUsuarioHardware(usuarioHardware);
			postergacion.setNumeroBoletoAnterior(postergacion.getVentaPasaje().getNumeroBoleto());
			/*Validando si hay una diferencia en la tarifa a favor de TEPSA - 16/12/2016 - jabanto*/
			if(dblbxImporteTotal.getValue()>0 && lblImportePagar.getValue().equals(LABEL_IMPPAG_TO_TEPSA))
				postergacion.setImportePagadoByDiferencia(dblbxImporteTotal.getValue());
			else
				postergacion.setImportePagadoByDiferencia(0.00);
			UtilData.auditarRegistro(postergacion,getUsuario(), Executions.getCurrent());


			/*Instancia el gasto administrativo*/
			VentaPasaje venta=(VentaPasaje)lbxVentas.getSelectedItem().getValue();
			gastoAdmin=null;
			if(dblbxPenalidad.getValue()>.00){
				gastoAdmin= new VentaPasaje();
				gastoAdmin.setVentaOriginal(venta.getVentaOriginal());
				gastoAdmin.setVentaPasaje(venta);
				gastoAdmin.setItinerario(new Itinerario((long)1));
				gastoAdmin.setRuta(venta.getRuta());
				gastoAdmin.setCliente(postergacion.getCliente()!=null?postergacion.getCliente():null);
				gastoAdmin.setPasajero(postergacion.getPasajero());
				gastoAdmin.setFormaPago(new FormaPago(Constantes.ID_FORPAG_CONTADO));
				gastoAdmin.setServicio(venta.getServicio());
				gastoAdmin.setTipoComprobante((TipoComprobante)cmbtipoComprobantePostergado.getSelectedItem().getValue());
				gastoAdmin.setTipoMovimiento(new TipoMovimiento(Constantes.ID_TIPMOV_GASTOS_ADMINISTRATIVOS));
				gastoAdmin.setTipoFormaPago((TipoFormaPago)cmbTipoFormaPago.getSelectedItem().getValue());
				if(gastoAdmin.getTipoFormaPago().getId().intValue()==Constantes.ID_TIPFORPAG_TARJETA)
					gastoAdmin.setTarjetaCredito((TarjetaCredito)cmbTarjetaCredito.getSelectedItem().getValue());
				gastoAdmin.setNumeroBoleto(txtNumeroBoletoPostergado.getText().trim());
				gastoAdmin.setNumeroBoletoAnterior(venta.getNumeroBoleto());
				gastoAdmin.setSecuencial(Constantes.FALSE_VALUE);
				gastoAdmin.setTarifa(dblbxPenalidad.getValue());
				gastoAdmin.setRecargo(0.00);
				gastoAdmin.setDescuento(0.00);
				gastoAdmin.setPenalidad(0.00);
				gastoAdmin.setAcuenta(0.00);
				gastoAdmin.setImportePagadoByDiferencia(0.00);
				gastoAdmin.setImportePagado(dblbxPenalidad.getValue());
				gastoAdmin.setImportePagadoEfectivo(0.00);
				gastoAdmin.setImportePagadoTarjeta(0.00);
				gastoAdmin.setTipoTransaccion(Constantes.TIPO_OPERACION_VARIOS);
				gastoAdmin.setFechaCaducidad(new Date());
				gastoAdmin.setNumeroControl("-");
				gastoAdmin.setFechaLiquidacion(fechaLiquidacion);
				gastoAdmin.setAgencia(getAgencia());
				gastoAdmin.setUsuario(getUsuario());
				gastoAdmin.setCanalVenta(getUsuarioHardware().getCanalVenta());
				gastoAdmin.setIdaRetorno(Constantes.FALSE_VALUE);
				gastoAdmin.setEsFechaAbierta(Constantes.FALSE_VALUE);
				gastoAdmin.setEstadoRegistro(Constantes.VALUE_ACTIVO);
				gastoAdmin.setObservaciones(observaciones);
				/*Calcula el igv del gasto administrativo*/
				Double igv=gastoAdmin.getImportePagado()- Double.valueOf(Util.toNumberFormat(gastoAdmin.getImportePagado()/((Constantes.IGV/100)+1),2));
				gastoAdmin.setIgv(igv);
				UtilData.auditarRegistro(gastoAdmin, getUsuario(), Executions.getCurrent());

				if(chkCambioRazonSocial.isChecked()){
					Cliente cliente=postergacion.getCliente();
					cliente.setRazonSocial(txtClientePostergado.getText().trim().toUpperCase());
					postergacion.setCliente(cliente);
					gastoAdmin.setCliente(cliente);
				}else if (chkCambioDireccionFiscal.isChecked()){
					Cliente cliente=ServiceLocator.getClienteManager().buscarPorId(postergacion.getCliente().getId());
					cliente.setDireccion(txtClienteDireccion.getText().trim().toUpperCase());
					postergacion.setCliente(cliente);
					gastoAdmin.setCliente(cliente);
				}
			}


			Messagebox.show(Messages.getString("WndPostergacion.information.confirmacionPostergacion"), DlgMessage.NOMBREAPLICACION, DlgMessage.BTN_YESNO, Messagebox.QUESTION, new EventListener<Event>() {
				@Override
				public void onEvent(Event e){
					try{
						if(e.getName().equals("onYes")){
							/*	Para definir si se debe valiadar el tiempo de bloqueo del asiento	*/
							Boolean validaBloqueAsiento=false;
							/*Indica si debe realizar la validacion del asiento bloado - 07/11/2016 - jabanto*/
							if(!(chkFechaAbierta.isChecked())){
								if(txtItinerarioActual.getText().equals(txtItinerarioPostergado.getText()) && !(txtNumeroAsientoActual.getText().equals(txtNumeroAsientoPostergado.getText())) ||
										!(txtItinerarioActual.getText().equals(txtItinerarioPostergado.getText())))
									validaBloqueAsiento=true;
							}

							/*##End begin 07/11/2016 - jabanto*/
//							if(chkCambioFacturaBoleta.isChecked() || chkCambioNombre.isChecked() || chkCambioRuc.isChecked())
//								validaBloqueAsiento=false;

							postergacion.setId(null);
							postergacion.setEnviadoSFE(null);
							postergacion.setFechaEnvioSFE(null);
							/*##End Begin 07/11/2016 - jabanto*/
//							int result = ServiceLocator.getVentaPasajesManager().postergarBoleto(postergacion,validaBloqueAsiento);
//							postergacion = ServiceLocator.getVentaPasajesManager().buscarVentaById(postergacion.getId());

							/*##Begin 04/11/2016 - jabanto*/
							VentaPasaje notaCredito = ServiceLocator.getVentaPasajesManager().postergarBoleto(postergacion,validaBloqueAsiento, gastoAdmin);
							postergacion = ServiceLocator.getVentaPasajesManager().buscarVentaById(postergacion.getId());
							List<VentaPasaje>listVentaPasaje= new ArrayList<>();
							/*Realiza el envio de la nota de credito y el nuevo comprobante*/
							listVentaPasaje.add(postergacion);
							if(gastoAdmin!=null)
								listVentaPasaje.add(gastoAdmin);

							//Comentado temporalmente por MAOE
							WSFE.sendVenta(listVentaPasaje, wndPostergacion, true, notaCredito);
							/*Realiza el envio del gasto administrativo*/

							//End begin 04/11/2016 - jabanto
//							/*Implementacion para el nueno formato 01/02/2016 - jabanto */
//							boolean formato=UtilData.getFormatoImprecion(getAgencia().getId(), getTipocomprobante().getId(), getUsuarioHardware().getId());
//							File file= CreateDocument.crearBoleto(postergacion,formato);
//							if(getUsuarioHardware().getPrintApplet().intValue()==Constantes.TRUE_VALUE){
//								String fileBoleto = Constantes.URL_FORMATOS_BOLETOS +Constantes.CLAVE_PAHT+ postergacion.getNumeroControl()+".txt";
//								Window win = (Window)Executions.createComponents("imprimir.zul", null, null);
//								win.setAttribute("formato", WndImprimir.FORMAT_BOLETO);
//								win.setAttribute("msg", "Imprimiendo el Boleto de Viaje "+postergacion.getNumeroBoleto()+"... ");
//								win.setAttribute("urlDocumento", fileBoleto);
//								win.doPopup();
//							}else{
//								//Descarga el archivo para la impresion
//								Util.descargarArchivo(file);
//							}

//							if(result == Constantes.CORRECT){
								DlgMessage.information(Messages.getString("WndPostergacion.information.exitoPostergacion")+postergacion.getNumeroControl());
								limpiarControles();
								postergacion = null;
								detalleItinerario= null;
								tabListado.setSelected(true);
								grdPostergacion.setVisible(false);
//								imgSeleccionarAsiento.setVisible(false);
								buscarVentas();
								chkPagoMixto.setChecked(false);
								chkFechaAbierta.setChecked(false);
//								chkFechaAbierta_onCheck();
								habilitarPagoMixto();
//							}
						}
					}catch(CapacityExceedsException ceex){
						DlgMessage.information(Messages.getString("WndVentaReserva.information.changeCapacidad"));
					}catch(DuplicateSeatException dsex){
						DlgMessage.information(Messages.getString("WndVentaReserva.information.asientoVendido"));
					}catch(NumeroBoletoDuplicadoException nbdex){
						DlgMessage.information(Messages.getString("WndVentaReserva.information.numeroBoletoVendido"));
					}catch(TiempoExpiracionBloqueoException tebex){
						DlgMessage.information(Messages.getString("WndVentaReserva.information.expiroTiempoBloqueoAsiento"));
						tiempoExpiracionBloqueo();
					}catch(Exception ex){
						ex.printStackTrace();
						DlgMessage.error(this.getClass().getName()+" "+ex.getMessage());
					}
				}
			});
		}catch (ItinerarioException i){
			if(i.getTipo().intValue()==ItinerarioException.NO_SELECT){
				DlgMessage.information(Messages.getString("WndPostergacion.information.noItinerarioSeleccionado"), imgBuscarItinerario);
			}else if (i.getTipo().intValue()==ItinerarioException.AGENCIA_PARTIDA_NULL){
				DlgMessage.information(Messages.getString("WndPostergacion.information.noAgenciaPartidaSeleccionada"), cmbEmbarquePostergado);
			}else if(i.getTipo().intValue()==ItinerarioException.TARIFA_IDA_CERO)
				DlgMessage.information(Messages.getString("WndVentaReserva.information.noTarifaItinerario"));
		}catch(NumeroAsientoNullException nanex){
			DlgMessage.information(Messages.getString("WndPostergacion.information.noAsientoSeleccionado"), imgSeleccionarAsiento);
		}catch(NumeroBoletoNullException nbnex){
			DlgMessage.information(Messages.getString("WndPostergacion.information.noNumeroBoleto"));
		}catch(TipoComprobanteNullException tcnex){
			DlgMessage.information(Messages.getString("WndPostergacion.information.noTipoComprobante"), cmbTipoComprobante);
		}catch(FormaPagoNullException fpnex){
			DlgMessage.information(Messages.getString("WndPostergacion.information.noFormaPago"), cmbFormaPago);
		}catch(TipoFormaPagoNullException tfpnex){
			DlgMessage.information(Messages.getString("WndPostergacion.information.noTipoFormaPago"), cmbTipoFormaPago);
		}catch(OperadorTarjetaCreditoNullException otcnex){
			DlgMessage.information(Messages.getString("WndPostergacion.information.noOperadorTarjetaCredito"), cmbOperadorTarjetaCredito);
		}catch(TarjetaCreditoNullException tcnex){
			DlgMessage.information(Messages.getString("WndPostergacion.information.noTarjetaCredito"), cmbTarjetaCredito);
		}catch(ImporteMixtoNullException imnex){
			if(imnex.getTipoError().intValue()==ImporteMixtoNullException.IMPORTE_MIXTO_CERO)
				DlgMessage.information(Messages.getString("WndVentaReserva.information.importeMixtoCero"), dblbxImporteEfectivo);
			else
				DlgMessage.information(Messages.getString("WndVentaReserva.information.importeMixtoNotEquals"), dblbxImporteEfectivo);
		}catch (PostergacionNoCambioNombreException pncn){
			DlgMessage.information(Messages.getString("WndPostergacion.information.noCambioNombre"));
		}catch (PostergacionNoCambioRazonSozialException pnrzex){
			DlgMessage.information(Messages.getString("WndPostergacion.information.noCambioRazonSocial"));
		}catch(Exception ex){
			DlgMessage.error(this.getClass().getName()+" "+ex.getMessage());
			ex.printStackTrace();
		}
	}

	private void tiempoExpiracionBloqueo(){
		txtNumeroAsientoPostergado.setText("");
	}

	public void cancelar(){
		tabListado.setSelected(true);
		grdPostergacion.setVisible(false);
		postergacion = null;
		detalleItinerario= null;
		limpiarControles();
		UtilData.liberarAsientos(usuarioHardware.getId());
		chkFechaAbierta.setChecked(false);
//		imgSeleccionarAsiento.setVisible(false);
	}

	public void cerrar(){
		UtilData.liberarAsientos(usuarioHardware.getId());
		closeTabWindow();
	}

	/**
	 * Para seleccionar el Tipo de Comprobante por defecto
	 */
	private void onSelectDefaultTipoComprobante(Combobox  comboTipoComprobante){
		for(Comboitem comboitem : comboTipoComprobante.getItems()){
			/*	Si la agencia pertenece a TEPSA*/
			if(agencia.getTipoAgencia().getId().intValue()==Constantes.ID_TIPAGE_TEPSA){
				/*##Begin 07/11/2016 - jabanto*/
				if(comboitem.getValue() instanceof TipoComprobante){
					if(postergacion.getCliente()!=null || chkCambioBoletaFactura.isChecked()){
						if(((TipoComprobante)comboitem.getValue()).getId().intValue()==Constantes.ID_TIPCOM_FACTURA){
							comboTipoComprobante.setSelectedItem(comboitem);
							break;
						}
					}else{
						if (((TipoComprobante)comboitem.getValue()).getId().intValue()==Constantes.ID_TIPCOM_BOLETA_VENTA){
							comboTipoComprobante.setSelectedItem(comboitem);
							break;
						}
					}


//					if((postergacion.getCliente()!=null || chkCambioBoletaFactura.isChecked()) && ((TipoComprobante)comboitem.getValue()).getId().intValue()==Constantes.ID_TIPCOM_FACTURA)
//						comboTipoComprobante.setSelectedItem(comboitem);
//					else if (((TipoComprobante)comboitem.getValue()).getId().intValue()==Constantes.ID_TIPCOM_BOLETA_VENTA)
//						comboTipoComprobante.setSelectedItem(comboitem);
				}

				/*##End Begin 07/11/2016 - jabanto*/
//				if(comboitem.getValue() instanceof TipoComprobante && ((TipoComprobante)comboitem.getValue()).getId().intValue()==Constantes.ID_TIPCOM_BOLETO_VIAJE)
//						cmbTipoComprobante.setSelectedItem(comboitem);

			}else if(agencia.getTipoAgencia().getId().intValue() == Constantes.ID_TIPAGE_VIAJES){	//Si es AGENCIA DE VIAJES
				if(comboitem.getValue() instanceof TipoComprobante && ((TipoComprobante)comboitem.getValue()).getId().intValue()==Constantes.ID_TIPCOM_VOUCHER_AGENCIA_VIAJES)
					comboTipoComprobante.setSelectedItem(comboitem);
			}else if(agencia.getTipoAgencia().getId().intValue() == Constantes.ID_TIPAGE_CORPORATIVO){		// Si es CORPORATIVO
				if(comboitem.getValue() instanceof TipoComprobante && ((TipoComprobante)comboitem.getValue()).getId().intValue()==Constantes.ID_TIPCOM_VOUCHER_CORPORATIVO)
					comboTipoComprobante.setSelectedItem(comboitem);
			}
		}
		comboTipoComprobante.setDisabled(true);
	}

	/**
	 * Selecciona por defecto el item del Combo Forma de Pago.
	 */
	private void onSelectDefaultFormaPago(){
		/*	Seleccionamos por defecto la Forma de pago	*/
		for(Comboitem comboitem : cmbFormaPago.getItems()){
			/*Valida si es un comprobante corporativo 30/12/2016 -jabanto*/
			if(isCorporativo){
				if(comboitem.getValue() instanceof FormaPago && ((FormaPago)comboitem.getValue()).getId().intValue()==Constantes.ID_FORPAG_CREDITO){
					cmbFormaPago.setSelectedItem(comboitem);
					onLoadTipoFormaPago();
				}
			}else{
				if(comboitem.getValue() instanceof FormaPago && ((FormaPago)comboitem.getValue()).getId().intValue()==Constantes.ID_FORPAG_CONTADO){
					cmbFormaPago.setSelectedItem(comboitem);
					onLoadTipoFormaPago();
					for(Comboitem item : cmbTipoFormaPago.getItems()){
						if(item.getValue() instanceof TipoFormaPago && ((TipoFormaPago)item.getValue()).getId().intValue()==Constantes.ID_TIPFORPAG_EFECTIVO)
							cmbTipoFormaPago.setSelectedItem(item);
					}
				}
			}

			/*End begin 30/12/2016 - jabanto*/
			/*	Si la agencia pertenece a TEPSA*/
//			if(agencia.getTipoAgencia().getId().intValue()==Constantes.ID_TIPAGE_TEPSA){
//				if(comboitem.getValue() instanceof FormaPago && ((FormaPago)comboitem.getValue()).getId().intValue()==Constantes.ID_FORPAG_CONTADO){
//					cmbFormaPago.setSelectedItem(comboitem);
//					onLoadTipoFormaPago();
//					for(Comboitem item : cmbTipoFormaPago.getItems()){
//						if(item.getValue() instanceof TipoFormaPago && ((TipoFormaPago)item.getValue()).getId().intValue()==Constantes.ID_TIPFORPAG_EFECTIVO)
//							cmbTipoFormaPago.setSelectedItem(item);
//					}
//				}
//			}else{
//				if(comboitem.getValue() instanceof FormaPago && ((FormaPago)comboitem.getValue()).getId().intValue()==Constantes.ID_FORPAG_CREDITO){
//					cmbFormaPago.setSelectedItem(comboitem);
//					onLoadTipoFormaPago();
//				}
//			}
		}
		cmbFormaPago.setDisabled(true);
	}

	public void habilitarPagoMixto(){
		boolean arg = false;
		if(chkPagoMixto.isChecked())
			arg = true;

		lblImporteEfectivo.setVisible(arg);
		lblImporteTarjeta.setVisible(arg);
		dblbxImporteEfectivo.setVisible(arg);
		dblbxImporteTarjeta.setVisible(arg);
		dblbxImporteEfectivo.setValue(0.0);
		dblbxImporteTarjeta.setValue(0.0);
		dblbxImporteEfectivo.setFocus(true);
	}

	/**
	 * 	Crea un objeto AplicarPromocion pasando parámetros al constructor
	 */
	private AplicarPromocion createObjectAplicarPromocion(){
		AplicarPromocion aplicarPromocion = null;
		try{
			PasajeroFrecuente pasajeroFrecuente = ServiceLocator.getPasajeroFrecuenteManager().buscarPaxFree(postergacion.getPasajero().getId(), Constantes.TRUE_VALUE);
			boolean paxfre = false;
			if(pasajeroFrecuente!=null)
				paxfre = true;
			aplicarPromocion = new AplicarPromocion(postergacion.getRuta().getId(), postergacion.getServicio().getId(),
				getAgencia().getId(), getUsuarioHardware().getCanalVenta().getId(), null, null,
				postergacion.getNumeroAsiento().toString(), postergacion.getCliente()==null?null:postergacion.getCliente().getId(),
				postergacion.getIdaRetorno().intValue()==Constantes.TRUE_VALUE?true:false, postergacion.getFormaPago().getId(),
				postergacion.getTipoFormaPago().getId(), postergacion.getTarjetaCredito()==null?null:postergacion.getTarjetaCredito().getId(),
				postergacion.getFechaPartida(), paxfre, dblbxTarifa, dblbxDescuento,
				dblbxImporteTotal, null, lblPromocion, imgQuitarPromocion, txtIdPromocion,
				postergacion.getHoraPartida());
		}catch(Exception ex){
			DlgMessage.error(this.getClass().getName()+" "+ex.getMessage());
			ex.printStackTrace();
		}

		return aplicarPromocion;
	}

	private void quitarPromocion(){
		lblPromocion.setValue("");
		dblbxDescuento.setTooltiptext("");
		dblbxDescuento.setValue(0.0);
//		dblbxImporteTotal.setValue(dblbxTarifa.getValue()+dblbxRecargo.getValue()-dblbxDescuento.getValue());
		dblbxSaldo.setValue(dblbxTarifa.getValue()-dblbxMontoAnterior.getValue()-dblbxDescuento.getValue());
		dblbxImporteTotal.setValue(dblbxSaldo.getValue()+dblbxPenalidad.getValue());
		imgQuitarPromocion.setVisible(false);
	}

	private void chkFechaAbierta_onCheck() throws Exception {

		limpiarControlesPostergacion();


		if(chkFechaAbierta.isChecked()){
			chkFechaAbierta.setDisabled(true);
			VentaPasaje venta=lbxVentas.getSelectedItem().getValue();

			imgBuscarItinerario.setVisible(false);
			imgSeleccionarAsiento.setVisible(false);
			txtOrigenPostergado.setText(txtOrigenActual.getText());
			txtDestinoPostergado.setText(txtDestinoActual.getText());
			txtServicioPostergado.setText(txtServicioActual.getText());
			cmbEmbarquePostergado.setDisabled(true);

			//##Begin 04/11/2016 - jabanto
			chkCambioDestino.setChecked(false);
			chkCambioServicio.setChecked(false);
			/*	Seteando el objeto postergacion con los nuevos datos para la venta	*/
			postergacion.setRuta(venta.getRuta());
			postergacion.setTarifa(venta.getTarifa());
			postergacion.setDescuento(venta.getDescuento());

			//##End Begin 03/11/2016
//			dblbxTarifa.setValue(postergacion.getTarifa());
//			dblbxDescuento.setValue(postergacion.getDescuento());

			//##End Begin 03/11/2016
//			dblbxSaldo.setValue(dblbxTarifa.getValue()-dblbxMontoAnterior.getValue()-dblbxDescuento.getValue()>0.0?dblbxTarifa.getValue()-dblbxMontoAnterior.getValue()-dblbxDescuento.getValue():0.0);
//			if(chkCambioNombre.isChecked() || rdCambioRuc.isChecked())
//				dblbxPenalidad.setValue(Constantes.PENALIDAD_POSTERGACION+Constantes.PENALIDAD_CAMBIO_NOMBRE);
//			else
//				dblbxPenalidad.setValue(Constantes.PENALIDAD_POSTERGACION);
//			dblbxImporteTotal.setValue(dblbxSaldo.getValue()+dblbxPenalidad.getValue());

			/*	Seteando el objeto postergacion con los nuevos datos para la venta	*/
 			postergacion.setItinerario(new Itinerario(Long.valueOf(1)));

 			/*End Begin 04/11/2016 - jabanto*/
//			postergacion.setServicio(postergacion.getServicio());
//			postergacion.setRuta(postergacion.getRuta());
		}else{
			imgBuscarItinerario.setVisible(true);
			imgSeleccionarAsiento.setVisible(false);
//			imgSeleccionarAsiento.setVisible(true);
			cmbEmbarquePostergado.setDisabled(false);


			//##End Begin 03/11/2016
			//04/12/2013 - jabanto
//			Double penalidad=dblbxPenalidad.getValue()!=null?dblbxPenalidad.getValue():0;
//			dblbxPenalidad.setValue(penalidad-Constantes.PENALIDAD_POSTERGACION);
//
//			if(!(chkCambioNombre.isDisabled())){
//				chkCambioNombre.setChecked(false);
//				chkCambioNombre_onCheck();
//			}
//			if(!(rdCambioRuc.isDisabled())){
//				rdCambioRuc.setChecked(false);
//				chkCambioRazonSocial_onCheck();
//			}
//			calcularTotaPago();
//			dblbxTarifa.setValue(0);
//			dblbxDescuento.setValue(0);
		}
		calcularPagos();
//		calcularTotaPago();
		detalleItinerario = null;
	}


	/**
	 * @date 03/12/2013
	 * @user jabanto
	 * @throws Exception
	 */
	private void chkCambioNombre_onCheck () throws Exception{
		imgBuscarPasajero.setVisible(chkCambioNombre.isChecked());

		if(!(chkCambioRuc.isChecked()) && !(chkCambioRazonSocial.isChecked()) && !(chkCambioDireccionFiscal.isChecked()) && !(chkCambioBoletaFactura.isChecked()) && !(chkCambioFacturaBoleta.isChecked())){
//			Double penalidad=dblbxPenalidad.getValue()!=null?dblbxPenalidad.getValue():0; //##End Begin 04/11/2016
			if(!(chkFechaAbierta.isChecked())){//Si no es fecha Abierta
				//Valida son itinerarios diferentes, se asume que es una postergacion
				if(chkCambioNombre.isChecked() && (
						(txtItinerarioActual.getText().equals(txtItinerarioPostergado.getText()) && txtNumeroAsientoActual.getText().equals(txtNumeroAsientoPostergado.getText()))
						|| txtItinerarioPostergado.getText().isEmpty() )  ){
					copiarDatosVenta();
				}else if(!chkCambioNombre.isChecked() && txtItinerarioActual.getText().equals(txtItinerarioPostergado.getText()) && txtNumeroAsientoActual.getText().equals(txtNumeroAsientoPostergado.getText())) {
					//Si los itinerarios son iguales, el valor de la penalidad es cero
//					dblbxPenalidad.setValue(0);
					calcularPagos();
					limpiarControlesPostergacion();
//				}else if (chkCambioNombre.isChecked() && !(txtItinerarioActual.getText().equals(txtItinerarioPostergado.getText()))){
					//Si los itinerarios son diferentes, se resta la penalidad.
//					dblbxPenalidad.setValue(penalidad+Constantes.PENALIDAD_CAMBIO_NOMBRE);

//				}else if (chkCambioNombre.isChecked()==false && penalidad>0){
//					dblbxPenalidad.setValue(penalidad-Constantes.PENALIDAD_CAMBIO_NOMBRE);
				}
			}else{//Si es fecha abierta
//				tipoNotaCredito=ServiceLocator.getTipoNotaManager().buscarPorId((long)Constantes.ID_TIPNOTA_CAMBIO_NOMBRE_PASAJERO);
				calcularPagos();
//				if(chkCambioNombre.isChecked())
//					dblbxPenalidad.setValue(penalidad+Constantes.PENALIDAD_CAMBIO_NOMBRE);
//				else if(penalidad>0)
//					dblbxPenalidad.setValue(penalidad-Constantes.PENALIDAD_CAMBIO_NOMBRE);
			}
//
//			calcularTotaPago();
//			validarPromocion();
		}
	}

	private void resetCheck_despuesViaje(Checkbox chkException)throws Exception{
		if(chkException.getId().equals(chkCambioRuc.getId())){
			if(chkCambioRazonSocial.isChecked()){
				chkCambioRazonSocial.setChecked(false);
				chkCambioRazonSocial_onCheck();
			}
			if(chkCambioDireccionFiscal.isChecked()){
				chkCambioDireccionFiscal.setChecked(false);
				chkCambioDireccionFiscal_onCheck();
			}
			if(chkCambioBoletaFactura.isChecked()){
				chkCambioBoletaFactura.setChecked(false);
				chkCambioBoletaFactura_onCheck();
			}
			if(chkCambioFacturaBoleta.isChecked()){
				chkCambioFacturaBoleta.setChecked(false);
				chkCambioFacturaBoleta_onCheck();
			}
		}else if (chkException.getId().equals(chkCambioRazonSocial.getId())){
			if(chkCambioRuc.isChecked()){
				chkCambioRuc.setChecked(false);
				chkCambioRuc_onCheck();
			}
			if(chkCambioDireccionFiscal.isChecked()){
				chkCambioDireccionFiscal.setChecked(false);
				chkCambioDireccionFiscal_onCheck();
			}
			if(chkCambioBoletaFactura.isChecked()){
				chkCambioBoletaFactura.setChecked(false);
				chkCambioBoletaFactura_onCheck();
			}
			if(chkCambioFacturaBoleta.isChecked()){
				chkCambioFacturaBoleta.setChecked(false);
				chkCambioFacturaBoleta_onCheck();
			}
		}else if (chkException.getId().equals(chkCambioDireccionFiscal.getId())){
			if(chkCambioRuc.isChecked()){
				chkCambioRuc.setChecked(false);
				chkCambioRuc_onCheck();
			}
			if(chkCambioRazonSocial.isChecked()){
				chkCambioRazonSocial.setChecked(false);
				chkCambioRazonSocial_onCheck();
			}
			if(chkCambioBoletaFactura.isChecked()){
				chkCambioBoletaFactura.setChecked(false);
				chkCambioBoletaFactura_onCheck();
			}
			if(chkCambioFacturaBoleta.isChecked()){
				chkCambioFacturaBoleta.setChecked(false);
				chkCambioFacturaBoleta_onCheck();
			}
		}else if (chkException.getId().equals(chkCambioBoletaFactura.getId())){
			if(chkCambioRuc.isChecked()){
				chkCambioRuc.setChecked(false);
				chkCambioRuc_onCheck();
			}
			if(chkCambioRazonSocial.isChecked()){
				chkCambioRazonSocial.setChecked(false);
				chkCambioRazonSocial_onCheck();
			}
			if(chkCambioDireccionFiscal.isChecked()){
				chkCambioDireccionFiscal.setChecked(false);
				chkCambioDireccionFiscal_onCheck();
			}
			if(chkCambioFacturaBoleta.isChecked()){
				chkCambioFacturaBoleta.setChecked(false);
				chkCambioFacturaBoleta_onCheck();
			}
		}else if (chkException.getId().equals(chkCambioFacturaBoleta.getId())){
			if(chkCambioRuc.isChecked()){
				chkCambioRuc.setChecked(false);
				chkCambioRuc_onCheck();
			}
			if(chkCambioRazonSocial.isChecked()){
				chkCambioRazonSocial.setChecked(false);
				chkCambioRazonSocial_onCheck();
			}
			if(chkCambioDireccionFiscal.isChecked()){
				chkCambioDireccionFiscal.setChecked(false);
				chkCambioDireccionFiscal_onCheck();
			}
			if(chkCambioBoletaFactura.isChecked()){
				chkCambioBoletaFactura.setChecked(false);
				chkCambioBoletaFactura_onCheck();
			}
		}
	}

	/**
	 * @date 03/12/2013
	 * @user jabanto
	 * @throws Exception
	 */
	private void chkCambioRuc_onCheck () throws Exception{
		imgBuscarClienteRuc.setVisible(chkCambioRuc.isChecked());

		if(!(chkCambioNombre.isChecked())){
//			Double penalidad=dblbxPenalidad.getValue()!=null?dblbxPenalidad.getValue():0;
			if(!(chkFechaAbierta.isChecked())){//Si no es fecha Abierta
				//Valida si el son itinerarios diferentes, se asume que es una postergacion
				if(chkCambioRuc.isChecked() && (
						(txtItinerarioActual.getText().equals(txtItinerarioPostergado.getText()) && txtNumeroAsientoActual.getText().equals(txtNumeroAsientoPostergado.getText()))
						|| txtItinerarioPostergado.getText().isEmpty())  ){
					copiarDatosVenta();
				}else if(!chkCambioRuc.isChecked() && txtItinerarioActual.getText().equals(txtItinerarioPostergado.getText()) && txtNumeroAsientoActual.getText().equals(txtNumeroAsientoPostergado.getText()) ) {
					//Si los itinerarios son iguales, el valor de la penalidad es cero
					dblbxPenalidad.setValue(0);
					limpiarControlesPostergacion();
				}
//				else if (chkCambioRuc.isChecked() && !(txtItinerarioActual.getText().equals(txtItinerarioPostergado.getText()))){
//					//Si los itinerarios son diferentes, se resta la penalidad.
//					dblbxPenalidad.setValue(penalidad+Constantes.PENALIDAD_CAMBIO_NOMBRE);
//				}else if (chkCambioRuc.isChecked()==false && penalidad>0){
//					dblbxPenalidad.setValue(penalidad-Constantes.PENALIDAD_CAMBIO_NOMBRE);
//				}
			}
//
//			else{//Si es fecha abierta
//				if(chkCambioRuc.isChecked())
//					dblbxPenalidad.setValue(penalidad+Constantes.PENALIDAD_CAMBIO_NOMBRE);
//				else if(penalidad>0)
//					dblbxPenalidad.setValue(penalidad-Constantes.PENALIDAD_CAMBIO_NOMBRE);
//			}

//			calcularTotaPago();
			calcularPagos();
//			validarPromocion();
		}
	}
	/**
	 * @date 03/12/2013
	 * @user jabanto
	 * Copia los datos de la venta para el cambio de nombre
	 * @throws Exception
	 */
	private void copiarDatosVenta() throws Exception{
		VentaPasaje venta=lbxVentas.getSelectedItem().getValue();
		if(venta!=null){
			TreeMap<String, Object>criteriosBusqueda=new TreeMap<>();
			criteriosBusqueda.put("itinerario", venta.getItinerario());
			criteriosBusqueda.put("ruta", venta.getRuta());
			criteriosBusqueda.put("horaPartida", venta.getHoraPartida());
			criteriosBusqueda.put("estadoRegistro", Constantes.VALUE_ACTIVO);
			List<DetalleItinerario> lstdetiti=ServiceLocator.getDetalleItinerarioManager().buscarPorX(criteriosBusqueda, null);
			if(lstdetiti.size()==1){
				detalleItinerario=new DetalleItinerario();
				detalleItinerario=lstdetiti.get(0);
				//MAOE: 22/06/2022
				//Insertar la tarifa en el detalleItinerario para el cambio de nombre
				detalleItinerario.setTarifa(postergacion.getTarifa());

				postergacion.setItinerario(detalleItinerario.getItinerario());
				postergacion.setServicio(detalleItinerario.getItinerario().getServicio());
				postergacion.setRuta(detalleItinerario.getRuta());
				postergacion.setFechaPartida(detalleItinerario.getFechaPartida());
				postergacion.setHoraPartida(detalleItinerario.getHoraPartida());
				postergacion.setFechaLlegada(detalleItinerario.getFechaLlegada());
				postergacion.setHoraLllegada(detalleItinerario.getHoraLlegada());
				/*	Seteando los datos a las cajas de texto	*/
				txtItinerarioPostergado.setText(detalleItinerario.getItinerario().getId().toString());
				txtNumeroAsientoPostergado.setText(String.valueOf(venta.getNumeroAsiento()));
				txtNumeroPisoPostergado.setText(venta.getNumeroPiso().toString());
				txtOrigenPostergado.setText(detalleItinerario.getRuta().getOrigen());
				txtDestinoPostergado.setText(detalleItinerario.getRuta().getDestino());
				txtServicioPostergado.setText(detalleItinerario.getItinerario().getServicio().getDenominacion());
				txtSalidaPostergado.setText(Util.DatetoString(detalleItinerario.getFechaPartida(), Constantes.DATE_FORMAT));
				onLoadPuntoEmbarque(detalleItinerario);

//				Double valorPenalidad=dblbxPenalidad.getValue()!=null?dblbxPenalidad.getValue():0;
//				if(chkCambioNombre.isChecked() || rdCambioRuc.isChecked())
//					calcularPagos(Constantes.PENALIDAD_CAMBIO_NOMBRE+valorPenalidad);

				/*Begin 03/11/2016 - jabanto*/
				if(chkCambioNombre.isChecked()){
					calcularPagos();
				}


			}
		}
	}



	private void enlazarMapaBus() throws Exception{
		if(!txtItinerarioPostergado.getText().trim().equals("")){
			final WndMapaBus oWndMapaBus = new WndMapaBus();
			oWndMapaBus.load();
			oWndMapaBus.setVentaPasaje(postergacion);
			oWndMapaBus.setDetalleItinerario(detalleItinerario);
			oWndMapaBus.setUsuarioHardware(usuarioHardware);
			oWndMapaBus.setTxtAsientoSeleccionado(txtNumeroAsientoPostergado);
			oWndMapaBus.setTxtPisoSeleccionado(txtNumeroPisoPostergado);
			oWndMapaBus.setSelectAsiento(true);
			oWndMapaBus.initComponents();
			oWndMapaBus.onCreate();
			this.appendChild(oWndMapaBus);
			oWndMapaBus.setMode(MODAL);
			oWndMapaBus.addEventListener(Events.ON_SELECT, new EventListener<Event>() {
				@Override
				public void onEvent(Event e) throws Exception{
					postergacion.setNumeroAsiento(Integer.valueOf(txtNumeroAsientoPostergado.getText()));
					postergacion.setNumeroPiso(Integer.valueOf(txtNumeroPisoPostergado.getText()));

					/*##End Begin 03/11/2016 - jabanto*/
//					if(chkCambioNombre.isChecked() || rdCambioRuc.isChecked())
//						calcularPagos(Constantes.PENALIDAD_POSTERGACION+Constantes.PENALIDAD_CAMBIO_NOMBRE);
//					else
//						calcularPagos(Constantes.PENALIDAD_POSTERGACION);

					/*Begin 03/11/2016 - jabanto*/
//					tipoNotaCredito=

					calcularPagos();
//					validarPromocion();

//					calcularPagos();



//					/**
//					 * Esta seccion es para obtener las promociones que son por tarifa
//					 * y que reeemplazaran la tarifa real del servicio.
//					 */
//					try {
//						/*	Obtenemos las promociones que reemplazaran a la tarifa	*/
//						List<Promocion>lstPromocion=ServiceLocator.getPromocionManager().buscarPorTarifa(Util.DatetoString(detalleItinerario.getFechaPartida(), Constantes.DATE_FORMAT), detalleItinerario.getRuta().getId().toString(), detalleItinerario.getItinerario().getServicio().getId().toString());
//						Promocion promo = null;
//						/*	Validando si la promocion cumple con el requisito del Servicio y la Ruta	*/
//						for(int i=0; i<lstPromocion.size(); i++){
//							String[] rutas = lstPromocion.get(i).getRutas().split(",");
//							String[] servicios = lstPromocion.get(i).getServicios().split(",");
//							String[] asientos = lstPromocion.get(i).getAsientos().split(",");
//							for(int j=0; j<rutas.length; j++){
//								if(rutas[j].equals(detalleItinerario.getRuta().getId().toString())){
//									for(int k=0; k<servicios.length; k++){
//										if(servicios[k].equals(detalleItinerario.getItinerario().getServicio().getId().toString())){
//											if(asientos.length==0)
//												promo = lstPromocion.get(i);
//											else{
//												for(int m=0; m<asientos.length; m++){
//													if(asientos[m].equals(postergacion.getNumeroAsiento().toString())){
//														promo = lstPromocion.get(i);
//														break;
//													}
//												}
//											}
//										}
//									}
//								}
//							}
//						}
//
//						if(promo!=null){
//							AplicarPromocion aplicarPromocion=createObjectAplicarPromocion();
//							aplicarPromocion.executePromocion(promo.getId().toString(), false);
//							dblbxTarifa.setValue(dblbxMontoAnterior.getValue()>=dblbxTarifa.getValue()?postergacion.getTarifa():dblbxTarifa.getValue());
//							dblbxSaldo.setValue(dblbxTarifa.getValue()-dblbxMontoAnterior.getValue()-dblbxDescuento.getValue());
//							dblbxImporteTotal.setValue(dblbxSaldo.getValue()+dblbxPenalidad.getValue());
//						}
//						aplicarPromocion();
//					} catch (Exception ex) {
//						DlgMessage.error(this.getClass().getSimpleName()+" "+ex.getMessage());
//						ex.printStackTrace();
//					}
				}
			});
		}
	}

	private void validarPromocion(){
		/**
		 * Esta seccion es para obtener las promociones que son por tarifa
		 * y que reeemplazaran la tarifa real del servicio.
		 */
		try {
			if(detalleItinerario!=null){
				Promocion promo = null;
				/*	Obtenemos las promociones que reemplazaran a la tarifa	*/
				List<Promocion>lstPromocion=ServiceLocator.getPromocionManager().buscarPorTarifa(Util.DatetoString(detalleItinerario.getFechaPartida(),
																								Constantes.DATE_FORMAT), detalleItinerario.getRuta().getId().toString(),
																								detalleItinerario.getItinerario().getServicio().getId().toString(),
																								detalleItinerario.getHoraPartida().replaceAll(":", "."));
				/*	Validando si la promocion cumple con el requisito del Servicio y la Ruta	*/
				for (Promocion element : lstPromocion) {
					String[] rutas = element.getRutas().split(",");
					String[] servicios = element.getServicios().split(",");
					String[] asientos = element.getAsientos().split(",");
					for (String element2 : rutas) {
						if(element2.equals(detalleItinerario.getRuta().getId().toString())){
							for (String element3 : servicios) {
								if(element3.equals(detalleItinerario.getItinerario().getServicio().getId().toString())){
									if(asientos.length==0)
										promo = element;
									else{
										for (String element4 : asientos) {
											if(element4.equals(postergacion.getNumeroAsiento().toString())){
												promo = element;
												break;
											}
										}
									}
								}
							}
						}
					}
				}

				quitarPromocion();
				if(promo!=null){
					AplicarPromocion aplicarPromocion=createObjectAplicarPromocion();
					aplicarPromocion.executePromocion(promo.getId().toString(), false);
					/*End Begin 28/11/2016 - jabanto*/
//					if(postergacion.getTarifa()<=dblbxTarifa.getValue()){
					/*Begin 28/11/2016 - jabanto*/
					if(postergacion.getTarifa().doubleValue()==dblbxTarifa.getValue() || postergacion.getImportePagado().doubleValue()==dblbxTarifa.getValue()){
						dblbxTarifa.setValue(postergacion.getTarifa());
						dblbxDescuento.setValue(postergacion.getDescuento());
						aplicarPromocion();
					}
					else
						dblbxDescuento.setValue(0.00);


					/*##Begin 07/11/2016 - jabanto*/
//					dblbxTarifa.setValue(dblbxMontoAnterior.getValue()>=dblbxTarifa.getValue()?postergacion.getTarifa():dblbxTarifa.getValue());
//					dblbxSaldo.setValue(dblbxTarifa.getValue()-dblbxMontoAnterior.getValue()-dblbxDescuento.getValue());
//					dblbxImporteTotal.setValue(dblbxSaldo.getValue()+dblbxPenalidad.getValue());
				}else{
//					if(detalleItinerario.getTarifa().doubleValue()<=postergacion.getTarifa().doubleValue()){
					//Obtener la tarifa con el nuevo enfoque




					if(detalleItinerario.getTarifa().doubleValue()==postergacion.getTarifa().doubleValue()){
						dblbxDescuento.setValue(postergacion.getDescuento());
						dblbxTarifa.setValue(postergacion.getTarifa());
						aplicarPromocion();
					}else{ //if (detalleItinerario.getTarifa().doubleValue()>postergacion.getTarifa().doubleValue() ){ // && txtNumeroAsientoPostergado.getText().isEmpty()){
						dblbxDescuento.setValue(0.00);
						dblbxTarifa.setValue(detalleItinerario.getTarifa());
					}


					/*End Begin 28/11/2016 - jabanto*/
//					if(detalleItinerario.getTarifa().doubleValue()<=postergacion.getTarifa().doubleValue()){
//						dblbxDescuento.setValue(postergacion.getDescuento());
//						dblbxTarifa.setValue(postergacion.getTarifa());
//					}else if (detalleItinerario.getTarifa().doubleValue()>postergacion.getTarifa().doubleValue() ){ // && txtNumeroAsientoPostergado.getText().isEmpty()){
//						dblbxDescuento.setValue(0.00);
//						dblbxTarifa.setValue(detalleItinerario.getTarifa());
//					}
				}

//				aplicarPromocion();
			}
		}catch(Exception ex){
			DlgMessage.error(Messages.getString(ex.getMessage()));
		}
	}

	private void liberarAsientos(){
		try{
			if(!txtNumeroAsientoPostergado.getText().isEmpty()){
				TmpOcupacionAsientos tmpOcupacion = new TmpOcupacionAsientos();
				tmpOcupacion.setRuta(postergacion.getRuta());
				tmpOcupacion.setItinerario(postergacion.getItinerario());
				tmpOcupacion.setNumeroAsiento(Integer.valueOf(txtNumeroAsientoPostergado.getText()));
				tmpOcupacion.setNumeroPiso(Integer.valueOf(txtNumeroPisoPostergado.getText()));
				ServiceLocator.getTmpOcupacionAsientosManager().desbloquearAsiento(tmpOcupacion);
				txtNumeroAsientoPostergado.setText("");
			}
		}catch(Exception ex){
			DlgMessage.information(this.getClass().getSimpleName()+" "+ex.getMessage());
		}
	}

//	private void calcularPagos(Double penalidad){
	private void calcularPagos()throws Exception{
		tipoNotaCredito=null;
		//Si es una postergacion
		if(!(txtItinerarioPostergado.getText().isEmpty()) && !(txtItinerarioActual.getText().equals(txtItinerarioPostergado.getText())) ||
				(txtItinerarioActual.getText().equals(txtItinerarioPostergado.getText()) && !(txtNumeroAsientoActual.getText().equals(txtNumeroAsientoPostergado.getText())))){

			tipoNotaCredito=ServiceLocator.getTipoNotaManager().buscarPorId((long)Constantes.ID_TIPNOTA_POSTERGACION);

			validarPromocion();
		}else{
			dblbxDescuento.setValue(postergacion.getDescuento());
			dblbxTarifa.setValue(postergacion.getTarifa());

			if(chkFechaAbierta.isChecked())//Fecha abierta
				tipoNotaCredito=ServiceLocator.getTipoNotaManager().buscarPorId((long)Constantes.ID_TIPNOTA_POSTERGACION);
			else if(chkCambioNombre.isChecked()) //Cambio de nombre
				tipoNotaCredito=ServiceLocator.getTipoNotaManager().buscarPorId((long)Constantes.ID_TIPNOTA_CAMBIO_NOMBRE_PASAJERO);
			else if (chkCambioRuc.isChecked()) //Cambio Boleta a Factura
				tipoNotaCredito=ServiceLocator.getTipoNotaManager().buscarPorId((long)Constantes.ID_TIPNOTA_CAMBIO_RUC);
			else if (chkCambioRazonSocial.isChecked()) //Cambio Boleta a Factura
				tipoNotaCredito=ServiceLocator.getTipoNotaManager().buscarPorId((long)Constantes.ID_TIPNOTA_CAMBIO_RAZON_SOCIAL);
			else if (chkCambioDireccionFiscal.isChecked()) //Cambio Boleta a Factura
				tipoNotaCredito=ServiceLocator.getTipoNotaManager().buscarPorId((long)Constantes.ID_TIPNOTA_CAMBIO_DIRECCION_FISCAL);
			else if (chkCambioBoletaFactura.isChecked()) //Cambio Boleta a Factura
				tipoNotaCredito=ServiceLocator.getTipoNotaManager().buscarPorId((long)Constantes.ID_TIPNOTA_CAMBIO_BOLETA_FACTURA);
			else if (chkCambioFacturaBoleta.isChecked()) //Cambio Boleta a Factura
				tipoNotaCredito=ServiceLocator.getTipoNotaManager().buscarPorId((long)Constantes.ID_TIPNOTA_CAMBIO_FACTURA_BOLETA);
		}

//		dblbxTarifa.setValue(detalleItinerario.getTarifa()<postergacion.getTarifa()?dblbxMontoAnterior.getValue():detalleItinerario.getTarifa());

		//Calculando el saldo
		dblbxSaldo.setValue(dblbxTarifa.getValue()-dblbxMontoAnterior.getValue()-dblbxDescuento.getValue());
//		if(dblbxSaldo.getValue()<0)
//			dblbxSaldo.setValue(0.00);

		/*End begin 17/11/2016 - jabanto*/
		//Calculando la penalidad, tomando en cuenta la forma de pago (efectivo, tarjeta)
//		if(cmbTipoFormaPago.getSelectedItem().getValue() instanceof TipoFormaPago){
//			TipoFormaPago tipoFormaPago=cmbTipoFormaPago.getSelectedItem().getValue();
//			if(tipoFormaPago.getId().intValue()==Constantes.ID_TIPFORPAG_EFECTIVO)
//				dblbxPenalidad.setValue(tipoNotaCredito!=null?tipoNotaCredito.getGastoAdminEfectivo():.00);
//			else
//				dblbxPenalidad.setValue(tipoNotaCredito!=null?tipoNotaCredito.getGastoAdminTarjeta():.00);
//		}else
//			dblbxPenalidad.setValue(tipoNotaCredito!=null?tipoNotaCredito.getGastoAdminEfectivo():.00);


			//Calculando la penalidad, tomando en cuenta la forma de pago (efectivo, tarjeta) - 17//11/2016 - jabanto
		if(postergacion.getTipoFormaPago().getId().intValue()!=Constantes.ID_TIPFORPAG_TARJETA)
			dblbxPenalidad.setValue(tipoNotaCredito!=null?tipoNotaCredito.getGastoAdminEfectivo():.00);
		else
			dblbxPenalidad.setValue(tipoNotaCredito!=null?tipoNotaCredito.getGastoAdminTarjeta():.00);


//		if(dblbxSaldo.getValue()>=0)
//			dblbxImporteTotal.setValue(dblbxSaldo.getValue()+dblbxPenalidad.getValue());
//		else
//			dblbxImporteTotal.setValue(dblbxPenalidad.getValue());


		//Calculando el importe total a pagar
		lblSaldo.setValue("SALDO : ");
		lblSaldo.setStyle("");
		dblbxSaldo.setStyle("");
		lblSaldo.setValue("SALDO : ");
		lblImportePagar.setValue(LABEL_IMPPAG_TO_TEPSA);
		if(dblbxSaldo.getValue()>=0){
			dblbxImporteTotal.setValue(dblbxSaldo.getValue()+dblbxPenalidad.getValue());
		}else{
			lblSaldo.setValue("SALDO A FAVOR DEL PASAJERO : ");
			lblSaldo.setStyle("color:black;font-weight: bold");
			dblbxSaldo.setStyle("color:black;font-weight: bold");

			dblbxSaldo.setValue(dblbxSaldo.getValue()*-1); /*Lo convierte en un valor positivo*/
			if(dblbxSaldo.getValue()>=dblbxPenalidad.getValue()){
				dblbxImporteTotal.setValue(dblbxSaldo.getValue()-dblbxPenalidad.getValue());
//				lblImportePagar.setValue("IMPORTE TOTAL A DEVOLVER : ");
				lblImportePagar.setValue(LABEL_IMPPAG_TO_PASAJERO);
			}else{
				dblbxImporteTotal.setValue(dblbxPenalidad.getValue()-dblbxSaldo.getValue());
				lblImportePagar.setValue(LABEL_IMPPAG_TO_TEPSA);
//				lblImportePagar.setValue("IMPORTE TOTAL PAGAR : ");
			}

		}

	}

//	private void calcularTotaPago()throws Exception{
//		calcularPagos();
//		//##End Begin 03/11/2016
////		if(detalleItinerario!=null)
////			dblbxTarifa.setValue(detalleItinerario.getTarifa()<postergacion.getTarifa()?dblbxMontoAnterior.getValue():detalleItinerario.getTarifa());
////
////		dblbxSaldo.setValue(dblbxTarifa.getValue()-dblbxMontoAnterior.getValue()-dblbxDescuento.getValue());
////		dblbxImporteTotal.setValue(dblbxSaldo.getValue()+dblbxPenalidad.getValue());
//	}

	private void aplicarPromocion(){
		try{
			if(dblbxMontoAnterior.getValue()<dblbxTarifa.getValue()){
				/* RECUPERANDO LOS DATOS DEL PASAJERO FRECUENTE */
				TreeMap<String, Object> criteriosBusqueda = new TreeMap<>();
				criteriosBusqueda.put("pasajero.id", postergacion.getPasajero().getId());
				criteriosBusqueda.put("estado", Constantes.TRUE_VALUE);
				List<PasajeroFrecuente> lstPaxfree = ServiceLocator.getPasajeroFrecuenteManager().buscarPorX(criteriosBusqueda, null);

				if (postergacion.getPasajero().getIndeseable().intValue() == Constantes.FALSE_VALUE) {
					if (lstPaxfree.size() > 0) {
						postergacion.getPasajero().setPaxFree(true);
						postergacion.getPasajero().setPasajeroFrecuente(lstPaxfree.get(0));
						/*	Buscamos la promocion del Pasajero Frecuente	*/
						criteriosBusqueda = new TreeMap<>();
						criteriosBusqueda.put("cliente", "*");
						criteriosBusqueda.put("pasajeroFrecuente", "S");
						criteriosBusqueda.put("estadoRegistro", Constantes.VALUE_ACTIVO);
						/*	Validando que el PAXFRE se encuentre activo	*/
						if(postergacion.getPasajero().getPasajeroFrecuente().getEstado().intValue()==Constantes.TRUE_VALUE){
							List<Promocion> lstPromocion = null;
							if(chkFechaAbierta.isChecked()){
								Date fechaPartida = Util.StringtoDate(ServiceLocator.getVentaPasajesManager().getDateSystem(), Constantes.DATE_FORMAT);
								lstPromocion = ServiceLocator.getPromocionManager().buscarPorX(criteriosBusqueda, null, Util.DatetoString(fechaPartida, Constantes.DATE_FORMAT));
							}else{
								lstPromocion = ServiceLocator.getPromocionManager().buscarPorX(criteriosBusqueda, null, Util.DatetoString(detalleItinerario.getFechaPartida(), Constantes.DATE_FORMAT));
							}

							if(lstPromocion!=null && lstPromocion.size()==1){
								AplicarPromocion aplicarPromocion = createObjectAplicarPromocion();
								promocionAplicada = aplicarPromocion.executePromocion(lstPromocion.get(0).getId().toString(), false);
								imgQuitarPromocion.setVisible(true);
								dblbxSaldo.setValue(dblbxTarifa.getValue()-dblbxMontoAnterior.getValue()-dblbxDescuento.getValue());
								dblbxImporteTotal.setValue(dblbxSaldo.getValue()+dblbxPenalidad.getValue());
							}else if(lstPromocion.size()>1)
								DlgMessage.information(Messages.getString("WndVentaPasajes.information.muchasPromocionesPaxFre"));
						}else{
							dblbxDescuento.setValue(0.00);
							dblbxDescuento.setTooltiptext("");
							dblbxImporteTotal.setValue(dblbxTarifa.getValue()-dblbxMontoAnterior.getValue()+dblbxPenalidad.getValue()-dblbxDescuento.getValue());
						}
					}else{		//Para aplicar la promocion que tenia la venta inicial validando solo la fecha de vigencia.
						Long idPromocion = postergacion.getPromocion()==null?null:postergacion.getPromocion().getId();
						if(idPromocion!=null){
							Promocion promocion = ServiceLocator.getPromocionManager().buscarPorId(idPromocion);
							if(Util.comparaFechas(postergacion.getFechaPartida(), promocion.getFechaInicio(), Util.OPER_MAYOR_IGUAL)){
								if(Util.comparaFechas(postergacion.getFechaPartida(), promocion.getFechaFin(), Util.OPER_MENOR_IGUAL)){
									dblbxDescuento.setValue(postergacion.getDescuento());
									Double saldo = dblbxTarifa.getValue()-dblbxMontoAnterior.getValue()-dblbxDescuento.getValue();
									dblbxSaldo.setValue(saldo<0.0?0.0:saldo);
									dblbxImporteTotal.setValue(dblbxSaldo.getValue()+dblbxPenalidad.getValue());
								}
							}
						}
					}
				}

				/*	Verificamos promociones para el cliente */
				if(postergacion.getCliente()!=null){
					criteriosBusqueda = new TreeMap<>();
					criteriosBusqueda.put("cliente", postergacion.getCliente().getId().toString());
					criteriosBusqueda.put("estadoRegistro", Constantes.VALUE_ACTIVO);
					if(detalleItinerario!=null){
						List<Promocion> lstPromocion = ServiceLocator.getPromocionManager().buscarPorX(criteriosBusqueda, null, Util.DatetoString(detalleItinerario.getFechaPartida(), Constantes.DATE_FORMAT));

						for (Promocion element : lstPromocion) {
							AplicarPromocion aplicarPromocion = createObjectAplicarPromocion();
							promocionAplicada = aplicarPromocion.executePromocion(element.getId().toString(), false);
							if(promocionAplicada!=null){
								break;
							}
							imgQuitarPromocion.setVisible(true);
						}
						dblbxSaldo.setValue(dblbxTarifa.getValue()-dblbxMontoAnterior.getValue()-dblbxDescuento.getValue());
						dblbxImporteTotal.setValue(dblbxSaldo.getValue()+dblbxPenalidad.getValue());
					}
				}
				/*	En caso el saldo sea negativo reseteamos el descuento y el saldo	*/
				if(dblbxSaldo.getValue()<0){
					dblbxSaldo.setValue(0.0);
					dblbxDescuento.setValue(0.0);
					imgQuitarPromocion.setVisible(false);
					lblPromocion.setValue("");
					dblbxDescuento.setTooltiptext("");
					dblbxImporteTotal.setValue(dblbxSaldo.getValue()+dblbxPenalidad.getValue());
				}
			}
		}catch(Exception ex){
			ex.printStackTrace();
			DlgMessage.error(this.getClass().getSimpleName() + " " + ex.getMessage());
		}
	}

	private void openWindowCambioRazonSocial(){
		wndCambiarRazoSocial = createWindowCambioRazonSocial();
		this.appendChild(wndCambiarRazoSocial);
		wndCambiarRazoSocial.setMode("modal");
	}

	@SuppressWarnings("deprecation")
	private Window createWindowCambioRazonSocial(){
		final Window window = new Window("CAMBIO DE RAZION SOCIAL", "none", true);
		window.setStyle("background-color: #E6F4FB;");
//		window.setWidth("450px");
		window.appendChild(new Separator());
		window.appendChild(new Separator());

		Vbox vbox= new Vbox();

		Label label= new Label("INGRESE LA RAZON SOCIAL");
		label.setStyle("color:blue;font-weight: bold;font-size:11px !important");
		final Textbox txtNuevaRazonSocial= new Textbox();
		txtNuevaRazonSocial.setWidth("300px");
		txtNuevaRazonSocial.setStyle("font-size:11px !important");

		vbox.appendChild(label);
		vbox.appendChild(txtNuevaRazonSocial);

		window.appendChild(vbox);
		window.appendChild(new Separator());
		window.appendChild(new Separator());
		window.appendChild(new Separator());
		window.appendChild(new Separator());

		/*Aceptar*/
		final Button btnAceptar = new Button("Aceptar");
		btnAceptar.setImage("/resources/mp_aceptarEnabled.png");
		btnAceptar.addEventListener(Events.ON_CLICK,new EventListener<Event>() {
			@Override
			public void onEvent(Event event) throws Exception {
				if(txtNuevaRazonSocial.getText().trim().isEmpty()){
					DlgMessage.information(Messages.getString("WndPostergacion.informacion.cambioRazonSocialNoNuevaRazonSocial"));
					return;
				}else if (txtNuevaRazonSocial.getText().trim().length()<=5){
					DlgMessage.information(Messages.getString("WndPostergacion.informacion.cambioRazonSocialInvalidNuevaRazonSocial"));
					return;
				}
				txtClientePostergado.setText(txtNuevaRazonSocial.getText().trim().toUpperCase());
				chkCambioRazonSocial.setDisabled(true);
				chkCambioRuc.setDisabled(true);
				chkCambioDireccionFiscal.setDisabled(true);

				window.onClose();
			}
		});

		/*Cancelar*/
		Button btncancelar = new Button("Cancelar");
		btncancelar.setImage("/resources/mp_cancelarEnabled.png");
		btncancelar.addEventListener(Events.ON_CLICK, new EventListener<Event>() {
			@Override
			public void onEvent(Event event) throws Exception {
				window.onClose();
			}
		});

		Hbox hbox= new Hbox();
		hbox.appendChild(btncancelar);
		hbox.appendChild(btnAceptar);

		Div div= new Div();
		div.setAlign("center");
		div.appendChild(hbox);
		window.appendChild(div);
		window.appendChild(new Separator());

		return window;
	}

	private void openWindowCambioDireccionFiscal(){
		wndCambiarDireccionFiscal = createWindowDireccionFiscal();
		this.appendChild(wndCambiarDireccionFiscal);
		wndCambiarDireccionFiscal.setMode("modal");
	}

	@SuppressWarnings("deprecation")
	private Window createWindowDireccionFiscal(){
		final Window window = new Window("CAMBIO DE LA DIRECCION FISCAL", "none", true);
		window.setStyle("background-color: #E6F4FB;");
//		window.setWidth("450px");
		window.appendChild(new Separator());
		window.appendChild(new Separator());

		Vbox vbox= new Vbox();

		Label label= new Label("INGRESE LA DIRECCION");
		label.setStyle("color:blue;font-weight: bold;font-size:11px !important");
		final Textbox txtNuevaDireccion= new Textbox();
		txtNuevaDireccion.setWidth("300px");
		txtNuevaDireccion.setStyle("font-size:11px !important");

		vbox.appendChild(label);
		vbox.appendChild(txtNuevaDireccion);

		window.appendChild(vbox);
		window.appendChild(new Separator());
		window.appendChild(new Separator());
		window.appendChild(new Separator());
		window.appendChild(new Separator());

		/*Aceptar*/
		final Button btnAceptar = new Button("Aceptar");
		btnAceptar.setImage("/resources/mp_aceptarEnabled.png");
		btnAceptar.addEventListener(Events.ON_CLICK,new EventListener<Event>() {
			@Override
			public void onEvent(Event event) throws Exception {
				if(txtNuevaDireccion.getText().trim().isEmpty()){
					DlgMessage.information(Messages.getString("WndPostergacion.informacion.cambioDireccionNoNuevaDireccion"));
					return;
				}else if (txtNuevaDireccion.getText().trim().length()<=10){
					DlgMessage.information(Messages.getString("WndPostergacion.informacion.cambioDireccionNoInvalidDireccion"));
					return;
				}
				txtClienteDireccion.setText(txtNuevaDireccion.getText().trim().toUpperCase());
				chkCambioDireccionFiscal.setDisabled(true);
				chkCambioRazonSocial.setDisabled(true);
				chkCambioRuc.setDisabled(true);

				window.onClose();
			}
		});

		/*Cancelar*/
		Button btncancelar = new Button("Cancelar");
		btncancelar.setImage("/resources/mp_cancelarEnabled.png");
		btncancelar.addEventListener(Events.ON_CLICK, new EventListener<Event>() {
			@Override
			public void onEvent(Event event) throws Exception {
				window.onClose();
			}
		});

		Hbox hbox= new Hbox();
		hbox.appendChild(btncancelar);
		hbox.appendChild(btnAceptar);

		Div div= new Div();
		div.setAlign("center");
		div.appendChild(hbox);
		window.appendChild(div);
		window.appendChild(new Separator());

		return window;
	}
}
