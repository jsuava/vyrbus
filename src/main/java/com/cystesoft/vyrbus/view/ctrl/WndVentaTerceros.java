/**
 * Proyecto		: SISVYR
 * Sistema		: Sistema de Ventas y Reservas
 * Descripción	: Clase que administra todo lo relacionado a la venta de pasajes.
 * Autor		: José Avalos Sullo
 * Fecha		: 25/06/2012
 */
package com.cystesoft.vyrbus.view.ctrl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.TreeMap;

//import javax.servlet.http.HttpSession;



import org.springframework.dao.DataIntegrityViolationException;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
//import org.zkoss.zk.ui.Session;
import org.zkoss.zk.ui.WrongValueException;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zul.Bandbox;
import org.zkoss.zul.Button;
import org.zkoss.zul.Calendar;
import org.zkoss.zul.Caption;
import org.zkoss.zul.Checkbox;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Comboitem;
import org.zkoss.zul.Div;
import org.zkoss.zul.Doublebox;
import org.zkoss.zul.Grid;
import org.zkoss.zul.Groupbox;
import org.zkoss.zul.Image;
import org.zkoss.zul.Intbox;
import org.zkoss.zul.Label;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Row;
import org.zkoss.zul.Rows;
import org.zkoss.zul.Tab;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Toolbarbutton;
import org.zkoss.zul.Window;

import com.cystesoft.vyrbus.model.bean.Agencia;
import com.cystesoft.vyrbus.model.bean.CentroCosto;
import com.cystesoft.vyrbus.model.bean.Cliente;
import com.cystesoft.vyrbus.model.bean.Concesionario;
import com.cystesoft.vyrbus.model.bean.DetalleItinerario;
import com.cystesoft.vyrbus.model.bean.EspecieValorada;
import com.cystesoft.vyrbus.model.bean.EstadoCivil;
import com.cystesoft.vyrbus.model.bean.FormaPago;
import com.cystesoft.vyrbus.model.bean.HistoricoMembresia;
import com.cystesoft.vyrbus.model.bean.ItinerarioAgenciaLlegada;
import com.cystesoft.vyrbus.model.bean.ItinerarioAgenciaPartida;
import com.cystesoft.vyrbus.model.bean.LineaCreditoCliente;
import com.cystesoft.vyrbus.model.bean.Liquidacion;
import com.cystesoft.vyrbus.model.bean.Localidad;
import com.cystesoft.vyrbus.model.bean.MapaBus;
import com.cystesoft.vyrbus.model.bean.Nacionalidad;
import com.cystesoft.vyrbus.model.bean.OperadorTarjetaCredito;
import com.cystesoft.vyrbus.model.bean.Pasajero;
import com.cystesoft.vyrbus.model.bean.PasajeroFrecuente;
import com.cystesoft.vyrbus.model.bean.PreferenciaAlimentaria;
import com.cystesoft.vyrbus.model.bean.Promocion;
import com.cystesoft.vyrbus.model.bean.Reniec;
import com.cystesoft.vyrbus.model.bean.Servicio;
import com.cystesoft.vyrbus.model.bean.Sexo;
import com.cystesoft.vyrbus.model.bean.TarjetaCredito;
import com.cystesoft.vyrbus.model.bean.TipoAgencia;
import com.cystesoft.vyrbus.model.bean.TipoComprobante;
import com.cystesoft.vyrbus.model.bean.TipoDocumento;
import com.cystesoft.vyrbus.model.bean.TipoFormaPago;
import com.cystesoft.vyrbus.model.bean.TipoMovimiento;
import com.cystesoft.vyrbus.model.bean.TmpOcupacionAsientos;
import com.cystesoft.vyrbus.model.bean.TmpOcupacionAsientosID;
import com.cystesoft.vyrbus.model.bean.Ubigeo;
import com.cystesoft.vyrbus.model.bean.Usuario;
import com.cystesoft.vyrbus.model.bean.UsuarioHardware;
import com.cystesoft.vyrbus.model.bean.VentaPasaje;
import com.cystesoft.vyrbus.service.exceptions.AgenciaNullException;
import com.cystesoft.vyrbus.service.exceptions.ApellidoPaternoNullException;
import com.cystesoft.vyrbus.service.exceptions.CapacityExceedsException;
import com.cystesoft.vyrbus.service.exceptions.ClienteException;
import com.cystesoft.vyrbus.service.exceptions.ConcesionarioNullException;
import com.cystesoft.vyrbus.service.exceptions.DocumentoPaxDuplicadoException;
import com.cystesoft.vyrbus.service.exceptions.DuplicateSeatException;
import com.cystesoft.vyrbus.service.exceptions.EmailNullException;
import com.cystesoft.vyrbus.service.exceptions.FechaMenorCalendarioException;
import com.cystesoft.vyrbus.service.exceptions.FechaViajeNoValidaException;
import com.cystesoft.vyrbus.service.exceptions.FormaPagoNullException;
import com.cystesoft.vyrbus.service.exceptions.ImporteMixtoNullException;
import com.cystesoft.vyrbus.service.exceptions.ItinerarioException;
import com.cystesoft.vyrbus.service.exceptions.LineaCreditoClienteException;
import com.cystesoft.vyrbus.service.exceptions.LiquidacionNullException;
import com.cystesoft.vyrbus.service.exceptions.LocalidadNullException;
import com.cystesoft.vyrbus.service.exceptions.MailIncorectoException;
import com.cystesoft.vyrbus.service.exceptions.NacionalidadException;
import com.cystesoft.vyrbus.service.exceptions.NombresNullException;
import com.cystesoft.vyrbus.service.exceptions.NumeroBoletoDuplicadoException;
import com.cystesoft.vyrbus.service.exceptions.NumeroBoletoNullException;
import com.cystesoft.vyrbus.service.exceptions.NumeroDocumentoNullException;
import com.cystesoft.vyrbus.service.exceptions.NumeroOperacionBancariaNullException;
import com.cystesoft.vyrbus.service.exceptions.OpenDateOnCreditNotAllowedException;
import com.cystesoft.vyrbus.service.exceptions.OperadorTarjetaCreditoNullException;
import com.cystesoft.vyrbus.service.exceptions.PasajeroException;
import com.cystesoft.vyrbus.service.exceptions.PasajeroIndeseableException;
import com.cystesoft.vyrbus.service.exceptions.PasajeroNoSavedExeption;
import com.cystesoft.vyrbus.service.exceptions.PreferenciaAlimentariaException;
import com.cystesoft.vyrbus.service.exceptions.RazonSocialDuplicadoException;
import com.cystesoft.vyrbus.service.exceptions.RazonSocialNullException;
import com.cystesoft.vyrbus.service.exceptions.RucDuplicadoException;
import com.cystesoft.vyrbus.service.exceptions.RucInvalidoException;
import com.cystesoft.vyrbus.service.exceptions.SaldoInsuficienteException;
import com.cystesoft.vyrbus.service.exceptions.SexoNullException;
import com.cystesoft.vyrbus.service.exceptions.TarjetaCreditoNullException;
import com.cystesoft.vyrbus.service.exceptions.TiempoExpiracionBloqueoException;
import com.cystesoft.vyrbus.service.exceptions.TipoAgenciaNullException;
import com.cystesoft.vyrbus.service.exceptions.TipoComprobanteNullException;
import com.cystesoft.vyrbus.service.exceptions.TipoDocumentoNullException;
import com.cystesoft.vyrbus.service.exceptions.TipoFormaPagoNullException;
import com.cystesoft.vyrbus.service.exceptions.UbigeoNullException;
import com.cystesoft.vyrbus.service.exceptions.UsuarioHardwareNullException;
import com.cystesoft.vyrbus.service.exceptions.UsuarioNullException;
import com.cystesoft.vyrbus.service.exceptions.VentaReservaException;
import com.cystesoft.vyrbus.service.locator.ServiceLocator;
import com.cystesoft.vyrbus.service.mappers.Asiento;
import com.cystesoft.vyrbus.service.mappers.Cafeteria;
import com.cystesoft.vyrbus.service.mappers.Coordenada;
import com.cystesoft.vyrbus.service.mappers.Monitor;
import com.cystesoft.vyrbus.service.mappers.SecuenciaTramo;
import com.cystesoft.vyrbus.service.mappers.ServiciosHigienicos;
import com.cystesoft.vyrbus.service.util.AplicarPromocion;
import com.cystesoft.vyrbus.service.util.Constantes;
import com.cystesoft.vyrbus.service.util.Messages;
import com.cystesoft.vyrbus.service.util.MyTime;
import com.cystesoft.vyrbus.service.util.Util;
import com.cystesoft.vyrbus.service.util.UtilData;
import com.cystesoft.vyrbus.view.ui.DlgMessage;
import com.cystesoft.vyrbus.view.ui.WndBase;

/**
 *
 * @author José Avalos Sullo
 * @since JDK1.6
 */
public class WndVentaTerceros extends WndBase {
	private static final long serialVersionUID = 1L;
	public static final int SEARCH_BY_DOCUMENTO = 1;
	public static final int SEARCH_BY_NOMBRES = 2;
	public static final int SEARCH_BY_RAZON = 3;
	
	private Row rowNacionalidad;
	private Row rowAdicional;
	private Checkbox chkPagoMixto;
	private Tab tabItinerario;
	private Tab tabAsientos;
	private Tab tabVenta;
	private Tab tabInformacionVentaIda;
	private Tab tabPasajero;
	private Tab tabCliente;
	private Tab tabPagos;
	private Combobox cmbOrigen;
	private Combobox cmbDestino;
	private Combobox cmbTipoAgenciaRemota;
	private Combobox cmbAgenciaRemota;
	private Combobox cmbPtoEmbarque;
	private Combobox cmbPtoDesembarque;
	private Combobox cmbTipoDocumento;
	private Combobox cmbSexo;
	private Combobox cmbEstadoCivil;
	private Combobox cmbTipoComprobante;
	private Combobox cmbFormaPago;
	private Combobox cmbTipoFormaPago;
	private Combobox cmbOperadorTarjetaCredito;
	private Combobox cmbTarjetaCredito;
	private Combobox cmbAlimentacion;
	private Combobox cmbNacionalidad;
	private Combobox cmbUsuarioRemoto;
	private Combobox cmbDia;
	private Combobox cmbMes;
	private Combobox cmbAnio;
	private Textbox txtNumeroBoleto;
	private Textbox txtDocumentoPax;
	private Textbox txtApePat;
	private Textbox txtApeMat;
	private Textbox txtNombres;
	private Textbox txtDireccionPax;
	private Textbox txtTelefono;
	private Textbox txtUbigeoPax;
	private Textbox txtUbigeoIdPax;
	private Textbox txtEmailPax;
	private Textbox txtDocumentoCliente;
	private Textbox txtRazonSocial;
	private Textbox txtContactoCliente;
	private Textbox txtDireccionCliente;
	private Textbox txtTelefonoClienteOne;
	private Textbox txtTelefonoClienteTwo;
	private Textbox txtEmailCliente;
	private Textbox txtUbigeoCliente;
	private Textbox txtUbigeoIdCliente;
	private Textbox txtRubro;
	private Textbox txtOperacionBancaria;
	private Textbox txtIdPromocion;
	private Textbox txtObservacionesIda;
	private Calendar cldrFechaPartida;
	private Listbox lbxItinerariosIda;
	private Listbox lbxAsientos;
	private Listbox lbxPasajeros;
	private Listbox lbxClientes;
	private Listbox lbxUsuarioHardware;
	private Combobox cmbCentroCosto;
	private Toolbarbutton tlbbtnNuevoPax;
	private Toolbarbutton tlbbtnModificarPax;
	private Toolbarbutton tlbbtnCancelarPax;
	private Toolbarbutton tlbbtnGuardarPax;
	private Toolbarbutton tlbbtnLimpiarPax;
	private Toolbarbutton tlbbtnNuevoClient;
	private Toolbarbutton tlbbtnModificarClient;
	private Toolbarbutton tlbbtnCancelarClient;
	private Toolbarbutton tlbbtnGuardarClient;
	private Toolbarbutton tlbbtnLimpiarClient;
	private Button btnNextTabAsientos;
	private Button btnNextTabVenta;
	private Button btnUbigeoPax;
	private Button btnUbigeoCliente;
	private Grid grdOcupabilidadIda;
	private Label lblOrigen;
	private Label lblDestino;
	private Label lblFechaPartida;
	private Label lblFechaLlegada;
	private Label lblHoraPartida;
	private Label lblHoraLlegada;
	private Label lblNroAsiento;
	private Label lblTarifa;
	private Label lblInformativo1;
	private Label lblDescuento;
	private Label lblInformativo2;
	private Label lblInformativo3;
	private Label lblEmail;
	private Label lblImporteEfectivo;
	private Label lblImporteTarjeta;
	private Label lblPromocion;
	private Label lblCentroCosto;
	private Groupbox grpbxMapaIda;
	private Groupbox grpbxListaPasajeros;
	private Groupbox grpbxListaClientes;
	private Doublebox dblTarifa;
	private Doublebox dblRecargo;
	private Doublebox dblDescuento;
	private Doublebox dblImporte;
	private Doublebox dblImporteEfectivo;
	private Doublebox dblImporteTarjeta;
	private Image imgPasajero;
	private Image imgDetalleMotivo;
	private Image imgRefreshBoleto;
	private Image imgPromocion;
	private Image imgQuitarPromocion;
	private Image imgFidelizarPasajero;
	private Intbox ibxCantidadTrabajadores;
	private Bandbox bndbxUsuarioHardware;
	
	private DetalleItinerario detalleItinerarioIda = null;
	private Pasajero oPasajero = null;
	private Cliente oCliente = null;
	private VentaPasaje ventaPasaje = null;
	private VentaPasaje ventaIDA = null;
	private DetalleItinerario detailItinerary = null;
	private LineaCreditoCliente lineaCreditoCliente=null;
	private Integer usuhar = null;
	private Agencia agencia = null;
	private Usuario usuario = null;
//	private CanalVenta canalVenta = null;
	private Date fechaLiquidacion = null;
	private Promocion promocionAplicada = null;
	private UsuarioHardware usuarioHardwareRemoto = null;
	/**
	 * Referencia a una Agencia o Corporativo
	 */
	private Cliente clienteCredito=null; //Se puede referir a una agencia o un cliente corporativo - 16/03/2013
	private Agencia agenciaRemota = null;
	
	private static final String IMAGE_PRIMER_PISO = "resources/mapa/bus_primerPiso.png";
	private static final String IMAGE_SEGUNDO_PISO = "resources/mapa/bus_segundoPiso.png";
	private static final int TIPO_ASIENTO = 0;
	private static final int TIPO_MONITOR = 1;
	private static final int TIPO_CAFETERIA = 2;
	
	private int action = Constantes.FAILURE;
	private String prefijoAsiento="";
	private String key = "-1";
	private Map<String, Asiento> mapaAsientosIda = null;
//	private List<DetalleCalculadora> lstDetalleCalculadora = new ArrayList<DetalleCalculadora>();
	private boolean convertirPaxFre = false;
	private Date fechaLiquidacionRemota = null;
	
	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.window.ui.IBase#onCreate()
	 */
	@Override
	public void onCreate() throws Exception {
		try{
//			/*Valida si el usuario tiene una liquidación aperturada*/
//			if(getDesktop().getSession().getAttribute(Constantes.ATRIBUTO_FECHA_LIQUIDACION)==null)
//				throw new LiquidacionNullException();	
//			
			tabAsientos.setDisabled(true);
			tabVenta.setDisabled(true);
			dblImporte.setReadonly(true);
			dblTarifa.setReadonly(true);
			
			/*	Realizamos la carga de información a los controles combobox	*/
			UtilData.cargarDataCombo(cmbOrigen, Localidad.class, false );
			UtilData.cargarDataCombo(cmbDestino, Localidad.class, true);
			TreeMap<String, Object> criteriosBusqueda = new TreeMap<String, Object>();
			criteriosBusqueda.put("tipo", TipoDocumento.PERSONALES);			//Usar los alias de los campos segun el xml de mapeo
			criteriosBusqueda.put("estadoRegistro", Constantes.VALUE_ACTIVO);	//Usar los alias de los campos segun el xml de mapeo
			UtilData.cargarDataCombo(cmbTipoDocumento, TipoDocumento.class, criteriosBusqueda, false);
			Util.seleccionarValorItemCombo(TipoDocumento.class, cmbTipoDocumento, Constantes.ID_TIPDOC_DNI);			
			UtilData.cargarDataCombo(cmbSexo, Sexo.class, false);
			UtilData.cargarDataCombo(cmbEstadoCivil, EstadoCivil.class, false);
			UtilData.cargarDataCombo(cmbNacionalidad, Nacionalidad.class, false);
			UtilData.enlazarUbigeo(txtUbigeoIdPax, txtUbigeoPax, btnUbigeoPax,null);
			UtilData.enlazarUbigeo(txtUbigeoIdCliente, txtUbigeoCliente, btnUbigeoCliente,null);
			
			criteriosBusqueda = new TreeMap<String, Object>();
			criteriosBusqueda.put("rubro", TipoComprobante.RUBRO_PASAJES);
			criteriosBusqueda.put("rubro", TipoComprobante.RUBRO_AMBOS);
			UtilData.cargarDataCombo(cmbTipoComprobante, TipoComprobante.class, criteriosBusqueda, false);
			cargarFormaPago(cmbFormaPago, false);
			UtilData.cargarDataCombo(cmbAlimentacion, PreferenciaAlimentaria.class, false);
			
			disabledControlsPax(true);
			disabledControlsClient(true);
			
			lbxItinerariosIda.getPagingChild().setMold("os");
			
			/*	*********************************************************************************************************	*/
			/*	Obteniendo las variables de la Sesion	*/
			UsuarioHardware usuarioHardware = (UsuarioHardware) this.getDesktop().getSession().getAttribute(Constantes.ATRIBUTO_USUARIO_HARDWARE);
			agencia = (Agencia)this.getDesktop().getSession().getAttribute(Constantes.ATRIBUTO_AGENCIA);
			usuario = (Usuario)this.getDesktop().getSession().getAttribute(Constantes.ATRIBUTO_USUARIO);
//			canalVenta = (CanalVenta)this.getDesktop().getSession().getAttribute(Constantes.ATRIBUTO_CANAL_VENTA);
			fechaLiquidacion = (Date)this.getDesktop().getSession().getAttribute(Constantes.ATRIBUTO_FECHA_LIQUIDACION);
			
			usuhar = usuarioHardware.getId();
			/*	*********************************************************************************************************	*/
			/*	Seleccionamos por defecto como origen la localidad de la Agencia.	*/
			Util.seleccionarValorItemCombo(Localidad.class, cmbOrigen, agencia.getLocalidad().getId());

			
			/*	Definiendo el formato de moneda a utilizar en los controles	*/
			dblTarifa.setLocale(Locale.US);
			dblRecargo.setLocale(Locale.US);
			dblDescuento.setLocale(Locale.US);
			dblImporte.setLocale(Locale.US);
			dblImporteEfectivo.setLocale(Locale.US);
			dblImporteTarjeta.setLocale(Locale.US);
			
			/*	Verificamos que no se trate de una agencia TEPSA	*/
			if(agencia.getTipoAgencia().getId().intValue()!=Constantes.ID_TIPAGE_TEPSA){
				
				/*Valida concesionario para luego recuperarlo*/
				if(agencia.getConcesionario()==null)
					throw new ConcesionarioNullException();
				Concesionario concesionario=ServiceLocator.getConcesionarioManager().buscarPorId(agencia.getConcesionario().getId().longValue());
				
				/*	Busca que la agencia se encuentre registrada como cliente */
				TreeMap<String, Object> mapCliente = new TreeMap<String, Object>();
				mapCliente.put("numeroDocumento",concesionario.getRuc());
				mapCliente.put("estadoRegistro", Constantes.VALUE_ACTIVO);
				List<Cliente> listCliente = ServiceLocator.getClienteManager().buscarPorX(mapCliente, null);
				if(!(listCliente.size()>0))
					throw new ClienteException();
				
				clienteCredito=listCliente.get(0);
				lineaCreditoCliente= ServiceLocator.getLineaCreditoClienteManager().validacionCreditoCliente(clienteCredito.getId());
				
				/*Valida el credito de la Agencia o cliente Corporativo - 15-03-2013*/
				if(lineaCreditoCliente == null || lineaCreditoCliente.getSaldo()<=0)
					throw new LineaCreditoClienteException(LineaCreditoClienteException.LINEA_CREDITO_SIN_SALDO);
				
				/*	Si se trata de una agencia corporativa	*/
				if(agencia.getTipoAgencia().getId().equals(Constantes.ID_TIPAGE_CORPORATIVO)){
					mantenimientoRegistroClient(clienteCredito.getId());
					tlbbtnNuevoClient.setDisabled(true);
					tlbbtnModificarClient.setDisabled(true);
					btnUbigeoCliente.setDisabled(true);					
					lblDescuento.setValue(Constantes.TIPCONVCLI_CORPORATIVO);
					txtDocumentoCliente.setDisabled(true);
					txtRazonSocial.setDisabled(true);
				}
			}
			onSelectDefaultFormaPago();
			loadTipoAgenciaRemota();
			onChangeTipoAgenciaRemota();	
			Util.loadAnios(cmbAnio);
			Util.loadMeses(cmbMes);
		}catch (ConcesionarioNullException ccnex){
			DlgMessage.information(Messages.getString("WndVentaReserva.information.noConcecionario"));
			closeTabWindow();
		}catch (LiquidacionNullException lnullex){
			DlgMessage.information(Messages.getString("WndVentaReserva.information.noLiquidacion"));
			closeTabWindow();
		}catch (LineaCreditoClienteException lccex) {
			if(lccex.getTipo().intValue()==LineaCreditoClienteException.LINEA_CREDITO_SIN_SALDO)
				DlgMessage.information(Messages.getString("tblLineaCreditoCliente.information.noSaldoDisponible"));
			else if(lccex.getTipo().intValue()==LineaCreditoClienteException.LINEA_CREDITO_NO_ACTIVO)
				DlgMessage.information(Messages.getString("tblLineaCreditoCliente.information.lineaNoActiva"));
			closeTabWindow();
		}catch(ClienteException cex){
			DlgMessage.information(Messages.getString("WndVentaReserva.information.noCliente"));
			closeTabWindow();
		} catch (Exception ex) {
			ex.printStackTrace();
			DlgMessage.error(this.getClass().getName() + " "+ex.getMessage());
		}
	}

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.window.ui.IBase#initComponents()
	 */
	@Override
	public void initComponents() {
		tabItinerario = (Tab)this.getFellow("tabItinerario");
		tabAsientos = (Tab)this.getFellow("tabAsientos");
		tabVenta = (Tab)this.getFellow("tabVenta");
		/*	TAB SELECCION DE ITINERARIO	*/
		cmbUsuarioRemoto=(Combobox)this.getFellow("cmbUsuarioRemoto");
		lbxUsuarioHardware = (Listbox)this.getFellow("lbxUsuarioHardware");
		bndbxUsuarioHardware = (Bandbox)this.getFellow("bndbxUsuarioHardware");
		cmbAgenciaRemota = (Combobox)this.getFellow("cmbAgenciaRemota");
		cmbTipoAgenciaRemota = (Combobox)this.getFellow("cmbTipoAgenciaRemota");
		cmbOrigen = (Combobox)this.getFellow("cmbOrigen");
		cmbDestino = (Combobox)this.getFellow("cmbDestino");
		cldrFechaPartida = (Calendar)this.getFellow("cldrFechaPartida");
		lbxItinerariosIda = (Listbox)this.getFellow("lbxItinerariosIda");
		btnNextTabAsientos = (Button)this.getFellow("btnNextTabAsientos");
		/*	TAB SELECCION DE ASIENTOS	*/
		grpbxMapaIda = (Groupbox)this.getFellow("grpbxMapaIda");
		btnNextTabVenta = (Button)this.getFellow("btnNextTabVenta");
		grdOcupabilidadIda = (Grid)this.getFellow("grdOcupabilidadIda");
		/*	TAB INFORMACION DE LA VENTA	*/
		lbxAsientos = (Listbox)this.getFellow("lbxAsientos");
		tabInformacionVentaIda = (Tab)this.getFellow("tabInformacionVentaIda");
		/*	TAB INFORMACION DE LA VENTA DE IDA	*/
		lblOrigen = (Label)this.getFellow("lblOrigen");
		lblDestino = (Label)this.getFellow("lblDestino");
		cmbPtoEmbarque = (Combobox)this.getFellow("cmbPtoEmbarque");
		cmbPtoDesembarque = (Combobox)this.getFellow("cmbPtoDesembarque");
		lblFechaPartida = (Label)this.getFellow("lblFechaPartida");
		lblFechaLlegada = (Label)this.getFellow("lblFechaLlegada");
		lblHoraPartida = (Label)this.getFellow("lblHoraPartida");
		lblHoraLlegada = (Label)this.getFellow("lblHoraLlegada");
		lblNroAsiento = (Label)this.getFellow("lblNroAsiento");
		lblTarifa = (Label)this.getFellow("lblTarifa");
		txtNumeroBoleto = (Textbox)this.getFellow("txtNumeroBoleto");
		imgRefreshBoleto = (Image)this.getFellow("imgRefreshBoleto");
		cmbAlimentacion = (Combobox)this.getFellow("cmbAlimentacion");
		txtObservacionesIda = (Textbox)this.getFellow("txtObservacionesIda");
		
		tabPasajero = (Tab)this.getFellow("tabPasajero");
		tabCliente = (Tab)this.getFellow("tabCliente");
		tabPagos = (Tab)this.getFellow("tabPagos");
		/*	TAB PASAJERO	*/
		grpbxListaPasajeros = (Groupbox) this.getFellow("grpbxListaPasajeros");
		lblDescuento = (Label)this.getFellow("lblDescuento");
		lbxPasajeros = (Listbox)this.getFellow("lbxPasajeros");
		lblInformativo1 = (Label)this.getFellow("lblInformativo1");
		lblInformativo2 = (Label)this.getFellow("lblInformativo2");
		lblInformativo3 = (Label)this.getFellow("lblInformativo3");
		imgFidelizarPasajero = (Image)this.getFellow("imgFidelizarPasajero");
		tlbbtnNuevoPax = (Toolbarbutton) this.getFellow("tlbbtnNuevoPax");
		tlbbtnLimpiarPax = (Toolbarbutton) this.getFellow("tlbbtnLimpiarPax");
		tlbbtnModificarPax = (Toolbarbutton) this.getFellow("tlbbtnModificarPax");
		tlbbtnCancelarPax = (Toolbarbutton) this.getFellow("tlbbtnCancelarPax");
		tlbbtnGuardarPax = (Toolbarbutton) this.getFellow("tlbbtnGuardarPax");
		imgDetalleMotivo = (Image)this.getFellow("imgDetalleMotivo");
		imgPasajero = (Image)this.getFellow("imgPasajero");
		rowAdicional = (Row) this.getFellow("rowAdicional");
		cmbTipoDocumento = (Combobox)this.getFellow("cmbTipoDocumento");
		txtDocumentoPax = (Textbox)this.getFellow("txtDocumentoPax");
		txtApePat = (Textbox)this.getFellow("txtApePat");
		txtApeMat = (Textbox)this.getFellow("txtApeMat");
		txtNombres = (Textbox)this.getFellow("txtNombres");
		txtDireccionPax = (Textbox)this.getFellow("txtDireccionPax");
		txtTelefono = (Textbox)this.getFellow("txtTelefono");
		txtUbigeoPax = (Textbox)this.getFellow("txtUbigeoPax");
		txtUbigeoIdPax = (Textbox)this.getFellow("txtUbigeoIdPax");
		btnUbigeoPax = (Button)this.getFellow("btnUbigeoPax");
		txtEmailPax = (Textbox)this.getFellow("txtEmailPax");
		cmbDia = (Combobox)this.getFellow("cmbDia");
		cmbMes = (Combobox)this.getFellow("cmbMes");
		cmbAnio = (Combobox)this.getFellow("cmbAnio");
		cmbSexo = (Combobox)this.getFellow("cmbSexo");
		cmbEstadoCivil = (Combobox)this.getFellow("cmbEstadoCivil");
		cmbNacionalidad = (Combobox)this.getFellow("cmbNacionalidad");
		rowNacionalidad = (Row)this.getFellow("rowNacionalidad");
		lblEmail = (Label)this.getFellow("lblEmail");
		/*	TAB CLIENTE	*/
		grpbxListaClientes = (Groupbox) this.getFellow("grpbxListaClientes");
		lbxClientes = (Listbox)this.getFellow("lbxClientes");
		txtDocumentoCliente = (Textbox)this.getFellow("txtDocumentoCliente");
		txtRazonSocial = (Textbox)this.getFellow("txtRazonSocial");
		txtContactoCliente = (Textbox)this.getFellow("txtContactoCliente");
		txtDireccionCliente = (Textbox)this.getFellow("txtDireccionCliente");
		txtTelefonoClienteOne = (Textbox)this.getFellow("txtTelefonoClienteOne");
		txtTelefonoClienteTwo = (Textbox)this.getFellow("txtTelefonoClienteTwo");
		txtEmailCliente = (Textbox)this.getFellow("txtEmailCliente");
		txtUbigeoCliente = (Textbox)this.getFellow("txtUbigeoCliente");
		txtUbigeoIdCliente = (Textbox)this.getFellow("txtUbigeoIdCliente");
		btnUbigeoCliente = (Button)this.getFellow("btnUbigeoCliente");
		tlbbtnNuevoClient = (Toolbarbutton) this.getFellow("tlbbtnNuevoClient");
		tlbbtnLimpiarClient = (Toolbarbutton) this.getFellow("tlbbtnLimpiarClient");
		tlbbtnModificarClient = (Toolbarbutton) this.getFellow("tlbbtnModificarClient");
		tlbbtnCancelarClient = (Toolbarbutton) this.getFellow("tlbbtnCancelarClient");
		tlbbtnGuardarClient = (Toolbarbutton) this.getFellow("tlbbtnGuardarClient");
		txtRubro = (Textbox)this.getFellow("txtRubro");
		ibxCantidadTrabajadores = (Intbox)this.getFellow("ibxCantidadTrabajadores");
		lblCentroCosto = (Label)this.getFellow("lblCentroCosto");
		cmbCentroCosto = (Combobox)this.getFellow("cmbCentroCosto");
		/*	TAB PAGOS	*/
		dblTarifa = (Doublebox)this.getFellow("dblTarifa");
		dblRecargo = (Doublebox)this.getFellow("dblRecargo");
		dblDescuento = (Doublebox)this.getFellow("dblDescuento");
		txtIdPromocion = (Textbox)this.getFellow("txtIdPromocion");
		imgPromocion = (Image)this.getFellow("imgPromocion");
		imgQuitarPromocion = (Image)this.getFellow("imgQuitarPromocion");
		dblImporte = (Doublebox)this.getFellow("dblImporte");
		chkPagoMixto = (Checkbox)this.getFellow("chkPagoMixto");
		lblImporteEfectivo = (Label)this.getFellow("lblImporteEfectivo");
		lblImporteTarjeta = (Label)this.getFellow("lblImporteTarjeta");
		lblPromocion = (Label)this.getFellow("lblPromocion");
		dblImporteEfectivo = (Doublebox)this.getFellow("dblImporteEfectivo");
		dblImporteTarjeta = (Doublebox)this.getFellow("dblImporteTarjeta");
		txtOperacionBancaria = (Textbox)this.getFellow("txtOperacionBancaria");
		cmbTipoComprobante = (Combobox)this.getFellow("cmbTipoComprobante");
		cmbFormaPago = (Combobox)this.getFellow("cmbFormaPago");
		cmbTipoFormaPago = (Combobox)this.getFellow("cmbTipoFormaPago");
		cmbOperadorTarjetaCredito = (Combobox)this.getFellow("cmbOperadorTarjetaCredito");
		cmbTarjetaCredito = (Combobox)this.getFellow("cmbTarjetaCredito");
		
		imgRefreshBoleto.addEventListener(Events.ON_CLICK, new EventListener<Event>() {
			@Override
			public void onEvent(Event e){
				onLoadEspecieValorada(txtNumeroBoleto);
			}
		});
		
		/*	En caso haga docble click en la lista de pasajeros, muestra la informacion del mismo.	*/
		lbxPasajeros.addEventListener(Events.ON_DOUBLE_CLICK, new EventListener<Event>() {
			@Override
			public void onEvent(Event e) {
				mantenimientoRegistroPax(new Long((String) lbxPasajeros.getSelectedItem().getValue()));
				grpbxListaPasajeros.setVisible(false);
			}
		});
		
		/*	En caso haga doble click en la lista de clientes, muestra la informacion del mismo.	*/
		lbxClientes.addEventListener(Events.ON_DOUBLE_CLICK, new EventListener<Event>() {
			@Override
			public void onEvent(Event e) {
				mantenimientoRegistroClient(new Long((String) lbxClientes.getSelectedItem().getValue()));
				grpbxListaClientes.setVisible(false);
			}
		});
		
		/*	En caso haya apretado la tecla enter en el control txtDocumentoPax realiza la busqueda del pasajero	*/
		txtDocumentoPax.addEventListener(Events.ON_OK, new EventListener<Event>() {
			@Override
			public void onEvent(Event e){
				if(action!=Constantes.ACTION_NEW && action!=Constantes.ACTION_MODIFY){
					if(txtDocumentoPax.getText().trim().equals(""))
						DlgMessage.information(Messages.getString("WndVentaReserva.information.noDocumentoPax"), txtDocumentoPax);
					else
						onSearchPax(SEARCH_BY_DOCUMENTO);
				}else
					txtApePat.setFocus(true);
			}
		});
		
		/*	En caso haya apretado la tecla enter en el control txtApePat realiza la busqueda del pasajero	*/
		txtApePat.addEventListener(Events.ON_OK, new EventListener<Event>() {
			@Override
			public void onEvent(Event e){
				if(action!=Constantes.ACTION_NEW && action!=Constantes.ACTION_MODIFY){
					if(txtApePat.getText().trim().equals(""))
						DlgMessage.information(Messages.getString("WndVentaReserva.information.noApellidoPaterno"), txtApePat);
					else if(txtNombres.getText().trim().equals(""))
						DlgMessage.information(Messages.getString("WndVentaReserva.information.noNombres"), txtNombres);
					else
						onSearchPax(SEARCH_BY_NOMBRES);
				}else
					txtApeMat.setFocus(true);
			}
		});
		
		/*	En caso haya apretado la tecla enter en el control txtApeMat realiza la busqueda del pasajero	*/
		txtApeMat.addEventListener(Events.ON_OK, new EventListener<Event>() {
			@Override
			public void onEvent(Event e){
				if(action!=Constantes.ACTION_NEW && action!=Constantes.ACTION_MODIFY){
					if(txtApePat.getText().trim().equals(""))
						DlgMessage.information(Messages.getString("WndVentaReserva.information.noApellidoPaterno"), txtApePat);
					else if(txtNombres.getText().trim().equals(""))
						DlgMessage.information(Messages.getString("WndVentaReserva.information.noNombres"), txtNombres);
					else
						onSearchPax(SEARCH_BY_NOMBRES);
				}else
					txtNombres.setFocus(true);
			}
		});
		
		/*	En caso haya apretado la tecla enter en el control txtNombres realiza la busqueda del pasajero	*/
		txtNombres.addEventListener(Events.ON_OK, new EventListener<Event>() {
			@Override
			public void onEvent(Event e){
				if(action!=Constantes.ACTION_NEW && action!=Constantes.ACTION_MODIFY){
					if(txtApePat.getText().trim().equals(""))
						DlgMessage.information(Messages.getString("WndVentaReserva.information.noApellidoPaterno"), txtApePat);
					else if(txtNombres.getText().trim().equals(""))
						DlgMessage.information(Messages.getString("WndVentaReserva.information.noNombres"), txtNombres);
					else
						onSearchPax(SEARCH_BY_NOMBRES);
				}else
					txtDireccionPax.setFocus(true);
			}
		});
		
		/*	En caso haya apretado la tecla enter en el control txtDocumentoCliente realiza la busqueda del cliente	*/
		txtDocumentoCliente.addEventListener(Events.ON_OK, new EventListener<Event>() {
			@Override
			public void onEvent(Event e){
				if(action!=Constantes.ACTION_NEW && action!=Constantes.ACTION_MODIFY){
					if(txtDocumentoCliente.getText().trim().equals(""))
						DlgMessage.information(Messages.getString("WndVentaReserva.information.noDocumentoCliente"), txtDocumentoCliente);
					else
						onSearchClient(SEARCH_BY_DOCUMENTO);
				}else
					txtRazonSocial.setFocus(true);
			}
		});
		
		/*	En caso haya apretado la tecla enter en el control txtRazonSocial realiza la busqueda del cliente	*/
		txtRazonSocial.addEventListener(Events.ON_OK, new EventListener<Event>() {
			@Override
			public void onEvent(Event e){
				if(action!=Constantes.ACTION_NEW && action!=Constantes.ACTION_MODIFY){
					if(txtRazonSocial.getText().trim().equals(""))
						DlgMessage.information(Messages.getString("WndVentaReserva.information.noRazonSocial"), txtRazonSocial);
					else
						onSearchClient(SEARCH_BY_RAZON);
				}else
					txtContactoCliente.setFocus(true);
			}
		});
		
		tabPasajero.addEventListener(Events.ON_SELECT, new EventListener<Event>() {
			@Override
			public void onEvent(Event e){
				cmbTipoDocumento.setFocus(true);
			}
		});
		
		tabCliente.addEventListener(Events.ON_SELECT, new EventListener<Event>() {
			@Override
			public void onEvent(Event e){
				txtDocumentoCliente.setFocus(true);
			}
		});
		
		tabPagos.addEventListener(Events.ON_SELECT, new EventListener<Event>() {
			@Override
			public void onEvent(Event e){
				cmbTipoFormaPago.setFocus(true);
			}
		});
		
		imgPromocion.addEventListener(Events.ON_CLICK, new EventListener<Event>() {
			@Override
			public void onEvent(Event e){
				imgPromocion_loadPromociones();
			}
		});
		
		imgQuitarPromocion.addEventListener(Events.ON_CLICK, new EventListener<Event>() {
			@Override
			public void onEvent(Event e){
				quitarPromocion();
			}
		});
		
		imgFidelizarPasajero.addEventListener(Events.ON_CLICK, new EventListener<Event>() {
			@Override
			public void onEvent(Event e){
				convertirPaxfree();
			}
		});
		
		cmbDestino.addEventListener(Events.ON_CHANGING, new EventListener<Event>() {
			@Override
			public void onEvent(Event e){
				lbxItinerariosIda.getItems().clear();
			}
		});
		cmbDestino.addEventListener(Events.ON_CHANGE, new EventListener<Event>() {
			@Override
			public void onEvent(Event e){
				lbxItinerariosIda.getItems().clear();
			}
		});
		
		cmbTipoAgenciaRemota.addEventListener(Events.ON_CHANGE, new EventListener<Event>() {
			@Override
			public void onEvent(Event e){
 				onChangeTipoAgenciaRemota();
			}
		});
		
		cmbAgenciaRemota.addEventListener(Events.ON_CHANGE, new EventListener<Event>() {
			@Override
			public void onEvent(Event e){
 				onChangeAgenciaRemota();
			}
		});
		
		cmbUsuarioRemoto.addEventListener(Events.ON_CHANGE, new EventListener<Event>() {
			@Override
			public void onEvent(Event e){
 				onChangeUsuarioRemoto();
			}
		});
		
		lbxUsuarioHardware.addEventListener(Events.ON_CLICK, new EventListener<Event>() {
			@Override
			public void onEvent(Event e){
				onSelectUsuarioHardware();
			}
		});
		
		cmbMes.addEventListener(Events.ON_CHANGE, new EventListener<Event>() {
			@Override
			public void onEvent(Event e){
				if(cmbMes.getSelectedItem().getValue() instanceof Integer)
					Util.loadDias(cmbDia, (Integer)cmbMes.getSelectedItem().getValue(), (Integer)cmbAnio.getSelectedItem().getValue());
			}
		});
		
		cmbAnio.addEventListener(Events.ON_CHANGE, new EventListener<Event>() {
			@Override
			public void onEvent(Event e){
				Util.loadMeses(cmbMes);
				cmbDia.setSelectedIndex(-1);
				cmbDia.setText("");
			}
		});
	}
	
	/**
	 * Para seleccionar el Tipo de Comprobante por defecto
	 */
	private void onSelectDefaultTipoComprobante(){
		for(Comboitem comboitem : cmbTipoComprobante.getItems()){
			if(((TipoAgencia)cmbTipoAgenciaRemota.getSelectedItem().getValue()).getId().intValue()==Constantes.ID_TIPAGE_VIAJES){
				if(comboitem.getValue() instanceof TipoComprobante && ((TipoComprobante)comboitem.getValue()).getId().intValue()==Constantes.ID_TIPCOM_VOUCHER_AGENCIA_VIAJES)
					cmbTipoComprobante.setSelectedItem(comboitem);
			}else if(((TipoAgencia)cmbTipoAgenciaRemota.getSelectedItem().getValue()).getId().intValue()==Constantes.ID_TIPAGE_CORPORATIVO){
				if(comboitem.getValue() instanceof TipoComprobante && ((TipoComprobante)comboitem.getValue()).getId().intValue()==Constantes.ID_TIPCOM_VOUCHER_CORPORATIVO)
					cmbTipoComprobante.setSelectedItem(comboitem);
			}
		}
		cmbTipoComprobante.setDisabled(true);
	}
	
	/**
	 * Selecciona por defecto el item del Combobox Forma de Pago.
	 */
	private void onSelectDefaultFormaPago(){
		/*	Seleccionamos por defecto la Forma de pago	*/
		for(Comboitem comboitem : cmbFormaPago.getItems()){
			if(comboitem.getValue() instanceof FormaPago && ((FormaPago)comboitem.getValue()).getId().intValue()==Constantes.ID_FORPAG_CREDITO){
				cmbFormaPago.setSelectedItem(comboitem);
				onLoadTipoFormaPago();					
			}
		}
		cmbFormaPago.setDisabled(true);
	}
	
	/**
	 * Realiza la busqueda del correlativo para el boleto a emitir.
	 */
	private void onLoadEspecieValorada(Textbox txtBoleto){
//		if(((TipoAgencia)cmbTipoAgenciaRemota.getSelectedItem().getValue()).getId().intValue()==Constantes.ID_TIPAGE_VIAJES )
//			txtBoleto.setValue(UtilData.buscarEspecieValorada(Constantes.ID_TIPCOM_VOUCHER_AGENCIA_VIAJES, agenciaRemota));
//		else
//			txtBoleto.setValue(UtilData.buscarEspecieValorada(Constantes.ID_TIPCOM_VOUCHER_CORPORATIVO, agenciaRemota));
	}
	
	/**
	 * Realiza la limpieza de las variables globales relacionadas con los itinerarios, mapa de asientos y listbox de itinerarios.
	 */
	private void limpiarDatosItinerario(){
		lbxItinerariosIda.getItems().clear();
		mapaAsientosIda = null;
		detalleItinerarioIda = null;
	}
	
	/**
	 * Para limpiar la lista de itinerarios en caso se seleccione otra fecha de partida. 
	 */
	public void onSelectCalendarIda() throws Exception{
		limpiarDatosItinerario();
		onBuscarItinerarios();
	}
	
	/**
	 * Realiza la busqueda de los itinerarios segun la fecha de partida, origen o destino.
	 */
	public void onBuscarItinerarios(){
		try{
			Date fechaActual= Constantes.FORMAT_DATE.parse(new MyTime().dateServer());
			if (cldrFechaPartida.getValue().getTime() < fechaActual.getTime())
				throw new FechaViajeNoValidaException();
			
			lbxItinerariosIda.getItems().clear();
			if(!(cmbOrigen.getSelectedItem().getValue() instanceof Localidad))
				throw new LocalidadNullException(LocalidadNullException.ORIGEN);
			
			String fechaPartida = Util.DatetoString(cldrFechaPartida.getValue(), Constantes.DATE_FORMAT);
			String origen = cmbOrigen.getText();
			String destino = "";
			if(cmbDestino.getSelectedItem().getValue() instanceof Localidad)
				destino = cmbDestino.getText();
			
			/*	Si no se trata de una venta a fecha abierta	*/
			List<DetalleItinerario> lstItinerarios = ServiceLocator.getItinerarioManager().buscarItinerarios(fechaPartida, origen, destino);
			if(lstItinerarios.size()>0){
				Listitem item = null;
				Listcell cell = null;
				/*	Llenando el listbox con los itinerarios encontrados	*/
				for(DetalleItinerario detalleItinerario : lstItinerarios){
					if(detalleItinerario.getItinerario().getTipoItinerario().getId().equals(Constantes.ID_TIPITI_REGULAR)){
						item = new Listitem();
						cell = new Listcell(detalleItinerario.getItinerario().getServicio().getDenominacion());
						item.appendChild(cell);
						cell = new Listcell(detalleItinerario.getRuta().toString());
						item.appendChild(cell);
						cell = new Listcell(detalleItinerario.getHoraPartida());
						item.appendChild(cell);
						cell = new Listcell(Util.toNumberFormat(detalleItinerario.getTarifa(), 2));
						item.appendChild(cell);
						item.setValue(detalleItinerario);
						lbxItinerariosIda.appendChild(item);
					}
				}
			}else
				DlgMessage.information(Messages.getString("WndVentaReserva.information.noItinerariosIda"));
		}catch (FechaViajeNoValidaException fnv){
			DlgMessage.information(Messages.getString("WndVentaReserva.information.FechaViajeNoValida"));			
		}catch(FechaMenorCalendarioException fmcex){
			DlgMessage.warning(Messages.getString("WndVentaReserva.warning.fechaRetornoNoValida"));
		}catch(LocalidadNullException lnex){
			if(lnex.getOrigenDestino().intValue()==LocalidadNullException.ORIGEN)
				DlgMessage.information(Messages.getString("WndVentaReserva.information.noOrigenSeleccionado"));
			else
				DlgMessage.information(Messages.getString("WndVentaReserva.information.noDestinoSeleccionado"));
		}catch(Exception ex){
			ex.printStackTrace();
		}
	}
	
	/**
	 * Metodo utilizado cuando el usuario selecciona un itinerario.
	 * @param direccion	: Indica si es ida o retorno.
	 */
	public void onSelectItinerario(Integer direccion)throws Exception{
		if(direccion.intValue()==Constantes.SERVICIO_IDA){
			if(lbxItinerariosIda.getSelectedItem().getValue() instanceof DetalleItinerario){
				detalleItinerarioIda = lbxItinerariosIda.getSelectedItem().getValue();
				btnNextTabAsientos.setDisabled(false);
			}
		}		
	}
	
	/**
	 * Para controlar la funcionalidad del boton siguiente.
	 */
	public void next() throws Exception{
		try{
			if(tabItinerario.isSelected()){
				/*Valida si el usuario tiene una liquidación aperturada*/
//				if(cmbTipoOperacion.getSelectedIndex()==0 && getDesktop().getSession().getAttribute(Constantes.ATRIBUTO_FECHA_LIQUIDACION)==null)
//					throw new LiquidacionNullException();
				
				if(detalleItinerarioIda==null)
					throw new ItinerarioException(ItinerarioException.ES_NULL_IDA);// ItinerarioNullException(ItinerarioNullException.ES_IDA);

				/*	Si es una venta remota	*/
				if(!(cmbTipoAgenciaRemota.getSelectedItem().getValue() instanceof TipoAgencia))
					throw new TipoAgenciaNullException();
				else if (!(cmbAgenciaRemota.getSelectedItem().getValue() instanceof Agencia))
					throw new AgenciaNullException();
				else if (!(cmbUsuarioRemoto.getSelectedIndex()>0))
					throw new UsuarioNullException();
				else if(!(lbxUsuarioHardware.getSelectedIndex()>=0))
					throw new UsuarioHardwareNullException();
					
				usuarioHardwareRemoto = ((EspecieValorada)lbxUsuarioHardware.getSelectedItem().getValue()).getUsuarioHardware();
				agenciaRemota = (Agencia)cmbAgenciaRemota.getSelectedItem().getValue();
				
				/*Valida concesionario para luego recuperarlo*/
				if(agenciaRemota.getConcesionario()==null)
					throw new ConcesionarioNullException();
				
				Concesionario concesionario=ServiceLocator.getConcesionarioManager().buscarPorId(agenciaRemota.getConcesionario().getId().longValue());
				
				/*	Busca que la agencia remota se encuentre registrada como cliente */
				TreeMap<String, Object> mapCliente = new TreeMap<String, Object>();
				mapCliente.put("numeroDocumento",concesionario.getRuc());
				mapCliente.put("estadoRegistro", Constantes.VALUE_ACTIVO);
				List<Cliente> listCliente = ServiceLocator.getClienteManager().buscarPorX(mapCliente, null);
				if(!(listCliente.size()>0))
					throw new ClienteException();
				
				clienteCredito=listCliente.get(0);
				lineaCreditoCliente= ServiceLocator.getLineaCreditoClienteManager().validacionCreditoCliente(clienteCredito.getId());
				
				/*Valida el credito de la Agencia o cliente Corporativo - 15-03-2013*/
				if(lineaCreditoCliente == null || lineaCreditoCliente.getSaldo()<=0)
					throw new LineaCreditoClienteException(LineaCreditoClienteException.LINEA_CREDITO_SIN_SALDO);
				
				/*	Si se trata de una agencia corporativa	*/
				if(agenciaRemota.getTipoAgencia().getId().equals(Constantes.ID_TIPAGE_CORPORATIVO)){
					mantenimientoRegistroClient(clienteCredito.getId());
					tlbbtnNuevoClient.setDisabled(true);
					tlbbtnModificarClient.setDisabled(true);
					btnUbigeoCliente.setDisabled(true);					
					lblDescuento.setValue(Constantes.TIPCONVCLI_CORPORATIVO);
					txtDocumentoCliente.setDisabled(true);
					txtRazonSocial.setDisabled(true);
				}
				onSelectDefaultFormaPago();
				onSelectDefaultTipoComprobante();
				tabAsientos.setDisabled(false);
				tabAsientos.setSelected(true);
				crearEstructura(detalleItinerarioIda.getItinerario().getServicio().getId(), grpbxMapaIda, false, detalleItinerarioIda, mapaAsientosIda, grdOcupabilidadIda);
			}else if(tabAsientos.isSelected()){	/*	Si estamos ubicados en el tab mapa del bus	*/
				tabVenta.setDisabled(false);
				tabVenta.setSelected(true);
				lbxAsientos.setVisible(true);
				tabInformacionVentaIda.setLabel("INFORMACIÓN DE LA VENTA");
				lbxAsientos.setSelectedIndex(0);
				lbxAsientos_onSelect();
				txtDocumentoPax.setFocus(true);
			}
			
		}catch (LiquidacionNullException lnullex){
			DlgMessage.information(Messages.getString("WndVentaReserva.information.noLiquidacion"));
			closeTabWindow();
		}catch(TipoAgenciaNullException tanex){
			DlgMessage.information(Messages.getString("WndVentaReserva.information.noSeleccionoTipoAgencia"), cmbTipoAgenciaRemota);
		}catch (AgenciaNullException anex){
			DlgMessage.information(Messages.getString("WndVentaReserva.information.noAgenciaRemota"), cmbAgenciaRemota);
		}catch (UsuarioNullException urnex){
			DlgMessage.information(Messages.getString("WndVentaReserva.information.noUsuarioRemoto"), cmbUsuarioRemoto);
		}catch(ItinerarioException inex){
			if(inex.getTipo().intValue()==ItinerarioException.ES_NULL_IDA) // .getIdaRetorno().intValue()==ItinerarioNullException.ES_IDA)
				DlgMessage.information(Messages.getString("WndVentaReserva.information.noItinerarioIdaSeleccionado"));
			else if (inex.getTipo().intValue()==ItinerarioException.ES_NULL_RETORNO){
				DlgMessage.information(Messages.getString("WndVentaReserva.information.noItinerarioRetornoSeleccionado"));
			}
		}catch(UsuarioHardwareNullException uhnex){
			DlgMessage.information(Messages.getString("WndVentaReserva.information.noSeleccionoUsuarioHardware"), bndbxUsuarioHardware);
		}catch (ConcesionarioNullException ccnex){
			DlgMessage.information(Messages.getString("WndVentaReserva.information.noConcecionario"));
			closeTabWindow();
		}catch (LineaCreditoClienteException lccex) {
			if(lccex.getTipo().intValue()==LineaCreditoClienteException.LINEA_CREDITO_SIN_SALDO)
				DlgMessage.information(Messages.getString("tblLineaCreditoCliente.information.noSaldoDisponible"));
			else if(lccex.getTipo().intValue()==LineaCreditoClienteException.LINEA_CREDITO_NO_ACTIVO)
				DlgMessage.information(Messages.getString("tblLineaCreditoCliente.information.lineaNoActiva"));
			closeTabWindow();
		}catch(ClienteException cex){
			DlgMessage.information(Messages.getString("WndVentaReserva.information.noCliente"));
			closeTabWindow();
		}catch(Exception ex){
			ex.printStackTrace();
			DlgMessage.error(this.getClass().getName()+" "+ex.getMessage());
		}
	}
	
	/**
	 * Crea la estructura que va a contener el Mapa del Bus.
	 * @param idServicio		: Identificador del servicio.
	 * @param grpbxParent		: Objeto padre contenedor.
	 * @param esRetorno			: Indica si el mapa es de ida o retorno.
	 * @param detalleItinerario	: Objeto del Detalle de Itinerario.
	 * @param mapaAsientos		: Objeto contenedor.
	 * @param gridOcupabilidad	: grilla para la ocupabilidad.
	 */
	@SuppressWarnings("deprecation")
	private void crearEstructura(Integer idServicio, Groupbox grpbxParent, boolean esRetorno, DetalleItinerario detalleItinerario, Map<String, Asiento> mapaAsientos, Grid gridOcupabilidad){
		try{
			Servicio servicio = null;
			/*	Busca el mapa del bus de acuerdo al servicio	*/
			List<MapaBus> lstMapaBus = ServiceLocator.getMapaBusManager().buscarMapaBus(idServicio, Constantes.VALUE_ACTIVO);			
			
			/*	Creamos un Map el cual tendra como key un objeto coordenada y como valor el objeto MapaBus esto es deacuerdo a 
			 * lo que tenemos en la Base de datos	*/
			Map<Coordenada, MapaBus> mapCoordenadas = new HashMap<Coordenada, MapaBus>();
			for(MapaBus mapaBus : lstMapaBus){
				Coordenada coordenada = new Coordenada(mapaBus.getNumeroFila(), mapaBus.getNumeroColumna(), mapaBus.getNumeroPiso());
				mapCoordenadas.put(coordenada, mapaBus);
			}
			
			if(lstMapaBus.size()>0)
				servicio = lstMapaBus.get(0).getServicio();
			
			int nPisos = servicio.getNumeroPisos();
			int nFilas = servicio.getNumeroFilasPiso1();
			int nColumnas = servicio.getNumeroColumnasPiso1();
			prefijoAsiento = "imgAsientoIdaPiso1_";
			if(esRetorno)
				prefijoAsiento = "imgAsientoRetornoPiso1_";
			Integer numeroAsiento = 0;
			
			inicializarEstructura(grpbxParent);
			
			Image imagen = generarImagen(IMAGE_PRIMER_PISO, 154, 43);
			
			mapaAsientos = new HashMap<String, Asiento>();
			
			/*	Recorremos la cantidad de pisos del servicio	*/
			for(int i=0; i<nPisos; i++){
				String idGrid = "grdIdaPiso1";
				if(esRetorno)
					idGrid = "grdRetornoPiso1";
				/*	Si cambiamos al siguiente piso redefinimos los valores de las variables	*/
				if(i==1){
					nFilas = servicio.getNumeroFilasPiso2();
					nColumnas = servicio.getNumeroColumnasPiso2();
					prefijoAsiento = "imgAsientoIdaPiso2_";
					idGrid = "grdIdaPiso2";
					if(esRetorno){
						prefijoAsiento = "imgAsientoRetornoPiso2_";
						idGrid = "grdRetornoPiso2";
					}
					imagen = generarImagen(IMAGE_SEGUNDO_PISO, 154, 34);					
				}
				/*	Creando la grilla contenedora de asientos	*/
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
				/*	Recorremos la cantidad de filas	*/
				for(int j=0; j<nFilas; j++){
					row = new Row();
					/*	Recorremos la cantidad de columnas	*/
					for(int k=0; k<nColumnas; k++){
						Div oDiv = new Div();
						oDiv.setWidth("28px");
						oDiv.setHeight("28px");
						oDiv.setStyle("padding:none");
						
						/*	Definimos la coordenada actual	*/
						String coordenadaActual = j+"-"+k+"-"+i;
						
						/*	Iteramos el mapa de coordenadas creado al inicio y comparamos	*/
						for(Coordenada coordenada : mapCoordenadas.keySet()){
							if(coordenada.toString().equals(coordenadaActual)){
								/*	Obtenemos el objeto y empezamos a setear las imagenes	*/
								MapaBus objetoBus = mapCoordenadas.get(coordenada);
								
								HashMap<String, String> propiedades = new HashMap<String, String>();
								numeroAsiento++;
								
								/*	Verificamos que el objeto sea del tipo Asiento	*/
								if(objetoBus.getTipoObjeto().intValue()==TIPO_ASIENTO){
									Asiento asiento = new Asiento();
									asiento.addEventListener(Events.ON_CLICK, new EventListener<Event>() {
										@Override
										public void onEvent(Event e){
											Component component = e.getTarget().getParent().getParent().getParent().getParent();
											if(component.getId().equals("grdIdaPiso1") || component.getId().equals("grdIdaPiso2"))
												onClickAsiento(e, detalleItinerarioIda, mapaAsientosIda, true);
										}
									});
									propiedades.put("ocupante", "pasajero");
									asiento.setId(prefijoAsiento + objetoBus.getNumeroAsiento());
									asiento.setOcupante(Asiento.OCUPANTE_PASAJERO);
									asiento.setEstadoAsiento(Constantes.ASIENTO_DISPONIBLE);
									asiento.setFila(j);
									asiento.setColumna(k);
									asiento.setPiso(i);
									asiento.setNumeroAsiento(objetoBus.getNumeroAsiento());
									asiento.setSrc(objetoBus.getPathImagen());
									asiento.setPropiedades(propiedades);
									asiento.setDraggable("true");
									asiento.setDetalleItinerario(detalleItinerario);
									asiento.setKey();
									asiento.setStyle("cursor:pointer");
									oDiv.appendChild(asiento);
									/*	Agregando los asientos a un Hashmap*/
									mapaAsientos.put(asiento.getKey(), asiento);
								}else if(objetoBus.getTipoObjeto().intValue()==TIPO_MONITOR){
									Monitor monitor = new Monitor();
									monitor.setId(prefijoAsiento + numeroAsiento);
									monitor.setFila(j);
									monitor.setColumna(k);
									monitor.setPiso(i);
									monitor.setSrc(objetoBus.getPathImagen());
									monitor.setPropiedades(propiedades);
									oDiv.appendChild(monitor);
								}else if(objetoBus.getTipoObjeto().intValue()==TIPO_CAFETERIA){
									Cafeteria cafeteria = new Cafeteria();
									cafeteria.setId(prefijoAsiento + numeroAsiento);
									cafeteria.setFila(j);
									cafeteria.setColumna(k);
									cafeteria.setPiso(i);
									cafeteria.setSrc(objetoBus.getPathImagen());
									cafeteria.setPropiedades(propiedades);
									oDiv.appendChild(cafeteria);
								}else{
									ServiciosHigienicos sshh = new ServiciosHigienicos();
									sshh.setId(prefijoAsiento + numeroAsiento);
									sshh.setFila(j);
									sshh.setColumna(k);
									sshh.setPiso(i);
									sshh.setSrc(objetoBus.getPathImagen());
									sshh.setPropiedades(propiedades);
									oDiv.appendChild(sshh);
								}
								break;
							}
						}
						row.appendChild(oDiv);
						row.setStyle("padding-top:2px; padding-left:3px; padding-right:0px; border:normal !important; background:#99D9EA");// background:#eeeeee");
					}
					rows.appendChild(row);
				}
				gridPiso.appendChild(rows);
				grpbxParent.appendChild(gridPiso);
				onRefreshMapaAsientos(mapaAsientos, detalleItinerario, gridOcupabilidad);
				mapaAsientosIda = mapaAsientos;
			}
		}catch(Exception ex){
			ex.printStackTrace();
		}
	}
	
	/**
	 * Inicializa(limpia los objetos existentes) el contenedor de los asientos.
	 */
	private void inicializarEstructura(Groupbox groupbox){
		for(int i=groupbox.getChildren().size()-1; i>-1; i--){
			Component component = groupbox.getChildren().get(i);
			if(!(component instanceof Caption))
				groupbox.removeChild(component);
		}
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
	 * Realiza el refresco del mapa del bus.
	 * @throws Exception
	 */
	public void onRefreshMap() {
		onRefreshMapaAsientos(mapaAsientosIda, detalleItinerarioIda, grdOcupabilidadIda);
	}
	
	/**
	 * Realiza el refresco del mapa del bus.
	 * @param mapa				: Mapa de asientos
	 * @param detalleItinerario	: Itinerario con el que se desea actualizar el mapa.
	 */
	@SuppressWarnings("unchecked")
	public void onRefreshMapaAsientos(Map<String, Asiento> mapaAsientos, DetalleItinerario detalleItinerario, Grid gridOcupabilidad){
		try{
			onCleanMap(mapaAsientos);
			/*	Obtenemos el subconjunto que queremos buscar segun la ruta seleccioanda		*/
			List<Integer> subConjuntoBuscar = obtenerSubconjunto(detalleItinerario.getItinerario().getListSecuenciaTramo(), detalleItinerario.getRuta().getLocalidadOrigen().getId(), detalleItinerario.getRuta().getLocalidadDestino().getId());
			
			int nOcupados = 0;
			List<VentaPasaje> lstVentas = ServiceLocator.getVentaPasajesManager().buscarVentasForMapaBus(detalleItinerario.getItinerario().getId());
			lstVentas = obtenerConjuntos(lstVentas, detalleItinerario.getItinerario().getListSecuenciaTramo());
			if(lstVentas.size()>0){
				/*	Recorremos las ventas	*/
				for(VentaPasaje venta : lstVentas){
					String key = venta.getNumeroAsiento()+"-"+venta.getNumeroPiso();
					/*	Recorremos los subconjuntos	*/
					for(Integer orden : subConjuntoBuscar){
						if(mapaAsientos.containsKey(key) && venta.getSubConjunto().contains(orden)){
							Asiento asiento = mapaAsientos.get(key);
							if(venta.getTipoTransaccion().equals(Constantes.TIPO_OPERACION_VENTA)){
								if(venta.getPasajero().getSexo().getId().intValue()==Constantes.ID_SEXO_FEMENINO)
									asiento.setSrc(Constantes.ICON_VENDIDO_FEMALE+venta.getNumeroAsiento()+Constantes.IMAGE_EXTENSION);
								else
									asiento.setSrc(Constantes.ICON_VENDIDO_MALE+venta.getNumeroAsiento()+Constantes.IMAGE_EXTENSION);
								asiento.setEstadoAsiento(Constantes.ASIENTO_VENDIDO);
							}else if(venta.getTipoTransaccion().equals(Constantes.TIPO_OPERACION_RESERVA)){
								if(venta.getCanalVenta().getId().intValue()==Constantes.ID_CANVEN_DELIVERY)
									asiento.setSrc(Constantes.ICON_RESERVADO_DELIVERY+venta.getNumeroAsiento()+Constantes.IMAGE_EXTENSION);
								else if(venta.getCanalVenta().getId().intValue()==Constantes.ID_CANVEN_AGENTE)
									asiento.setSrc(Constantes.ICON_RESERVADO_AGENTE+venta.getNumeroAsiento()+Constantes.IMAGE_EXTENSION);
								else
									asiento.setSrc(Constantes.ICON_RESERVADO+venta.getNumeroAsiento()+Constantes.IMAGE_EXTENSION);
								asiento.setEstadoAsiento(Constantes.ASIENTO_RESERVADO);
							}
							asiento.setTooltiptext(venta.getRuta().getOrigen()+"-"+venta.getRuta().getDestino());
							nOcupados++;
							break;
						}
					}
					/*	Para identificar las prioridades del tramos para la venta	*/
					if(venta.getRuta().getLocalidadDestino().getId()==detalleItinerario.getRuta().getLocalidadOrigen().getId()){
						Asiento asiento = mapaAsientos.get(key);
						asiento.setSrc(Constantes.ICON_SEMI_OCUPADO+venta.getNumeroAsiento()+Constantes.IMAGE_EXTENSION);								
					}
				}
			}			
			
			/*	BUSCAMOS LOS ASIENTOS QUE ESTEN BLOQUEADOS PARA EL ITINERARIO SELECCIONADO	*/
			List<TmpOcupacionAsientos> lstBloqueados = ServiceLocator.getTmpOcupacionAsientosManager().buscarAsientosBloqueados(detalleItinerario.getItinerario().getId());
			lstBloqueados = obtenerConjuntos(lstBloqueados, detalleItinerario.getItinerario().getListSecuenciaTramo());
			if(lstBloqueados.size()>0){
				for(TmpOcupacionAsientos bloqueado : lstBloqueados){
					for(Integer orden : subConjuntoBuscar){
						if(mapaAsientos.containsKey(bloqueado.getKey()) && bloqueado.getSubConjunto().contains(orden)){
							Asiento asiento = mapaAsientos.get(bloqueado.getKey());
							asiento.setSrc(Constantes.ICON_BLOQUEADO+bloqueado.getNumeroAsiento()+Constantes.IMAGE_EXTENSION);
							asiento.setEstadoAsiento(Constantes.ASIENTO_BLOQUEADO);
							nOcupados++;
							break;
						}
					}
				}
			}
			mostrarOcupabilidad(nOcupados, detalleItinerario, gridOcupabilidad);
		}catch(Exception ex){
			ex.printStackTrace();
			DlgMessage.error(this.getClass().getName()+" "+ex.getMessage());
		}
	}
	
	/**
	 * Realiza la limpieza del mapa del bus
	 */
	public void onCleanMap(Map<String, Asiento> mapa){
		for(String key : mapa.keySet()){
			Asiento asiento = mapa.get(key);
			asiento.setSrc(Constantes.ICON_DISPONIBLE+asiento.getNumeroAsiento()+Constantes.IMAGE_EXTENSION);
			asiento.setEstadoAsiento(Constantes.ASIENTO_DISPONIBLE);
			asiento.setTooltiptext("DISPONIBLE");
		}
	}
	
	/**
	 * Obtiene los subconjuntos de una lista de ventas, tmpOcupacion.
	 * @param lista			: Lista de registros de los cuales queremos obtener los subconjuntos.
	 * @param lstSecuencias	: Lista de secuencia segun el itinerario.
	 * @return
	 */
	@SuppressWarnings({"rawtypes","unchecked"})
	private List obtenerConjuntos(List lista, List<SecuenciaTramo> lstSecuencias){
		List result = new ArrayList();
		for(int i=0; i<lista.size(); i++){
			Object obj = lista.get(i);
			if(obj instanceof TmpOcupacionAsientos){
				TmpOcupacionAsientos tmp = (TmpOcupacionAsientos)obj;
				List<Integer> subConjunto = obtenerSubconjunto(lstSecuencias, tmp.getRuta().getLocalidadOrigen().getId(), tmp.getRuta().getLocalidadDestino().getId());
				tmp.setSubConjunto(subConjunto);
				result.add(tmp);
			}else if(obj instanceof VentaPasaje){
				VentaPasaje ventaPasaje = (VentaPasaje)obj;
				List<Integer> subConjunto = obtenerSubconjunto(lstSecuencias, ventaPasaje.getRuta().getLocalidadOrigen().getId(), ventaPasaje.getRuta().getLocalidadDestino().getId());
				ventaPasaje.setSubConjunto(subConjunto);
				result.add(ventaPasaje);
			}
		}
		return result;
	}
	
	/**
	 * Obtine los subconjuntos de un registro de venta, tmpocupacion o de la ruta que estamos buscando. 
	 * @param lstSecuencias	: Lista de secuencia segun el itinerario.
	 * @param idOrigen		: Identificador del origen.
	 * @param idDestino		: Identificador del destino.
	 * @return
	 */
	private List<Integer> obtenerSubconjunto(List<SecuenciaTramo> lstSecuencias, Integer idOrigen, Integer idDestino){
		List<Integer> lstSubconjunto = new ArrayList<Integer>();
		/*	Recorremos la secuencia de tramos del itinerario	*/
		for(int j=0; j<lstSecuencias.size(); j++){
			SecuenciaTramo secuencia = lstSecuencias.get(j);
			/*	Validamos si el origen de la secuencia coincide con el origen de la ruta	*/
			if(secuencia.getOrigen().intValue()==idOrigen.intValue()){
				/*	Recorremos la secuencia de tramos desde la posicion j	*/
				for(int k=j; k<lstSecuencias.size(); k++){
					secuencia = lstSecuencias.get(k);
					lstSubconjunto.add(secuencia.getOrden());
					/*	Validamos si el destino de la secuencia coincide con el destino de la ruta	*/
					if(secuencia.getDestino().intValue()==idDestino.intValue())
						break;
				}
				break;
			}
		}
		return lstSubconjunto;
	}
	
	/**
	 * Muestra los valores de la ocupabilidad de asientos
	 * @param nOcupados			: Cantidad de asientos ocupados
	 * @param detalleItinerario	: Itinerarios seleccionado.
	 * @param grid				: Grid que contendra los objetos.
	 */
	private void mostrarOcupabilidad(Integer nOcupados, DetalleItinerario detalleItinerario, Grid grid){
		grid.getRows().detach();
		Rows rows = new Rows();
		
		Row row = new Row();
		Label label = new Label(detalleItinerario.getRuta().toString());
		row.appendChild(label);
		label = new Label(nOcupados.toString());
		row.appendChild(label);
		label = new Label(String.valueOf(detalleItinerario.getItinerario().getServicio().getNumeroAsientosPiso1() - nOcupados));
		row.appendChild(label);
		rows.appendChild(row);
		grid.appendChild(rows);
	}
	
	/**
	 * Evento utilizado cuando el usuario hace click en un asiento.
	 * @param e				: Evento
	 * @param mapaAsientos	: Mapa de asientos.
	 * @param ventaPasaje	: Itinerario seleccionado.
	 * @param esIda			: Indica si es Ida o retorno.
	 */
	private void onClickAsiento(Event e, DetalleItinerario detalleItinerario, Map<String, Asiento> mapaAsientos, boolean esIda){
		Asiento asientoSeleccionado = (Asiento)e.getTarget();
		key = asientoSeleccionado.getKey();
		
		/*	Elimina el asiento de la lista de asientos seleccionados y desbloquea el asiento*/
		if(removerAsientoSeleccionado(key, mapaAsientos, asientoSeleccionado.getDetalleItinerario()))			
			return;

		try{
			/*	validamos la cantidad maxima de asientos seleccionados	*/
			if(lbxAsientos.getItemCount()<=Constantes.MAX_SEAT_SELECTED){
				/*	Si el asiento se encuentra bloqueado mostrar mensaje de no disponibilidad	*/
				if(consultaAsientoBloqueado(key, mapaAsientos)){
					DlgMessage.information(Messages.getString("WndVentaReserva.information.asientoNoDisponible"));
					key="-1";
				}else{
					/*	Insertamos el asiento a la tmpOcupacionAsientos	*/
					TmpOcupacionAsientos tmpOcupacionAsientos = new TmpOcupacionAsientos();
					tmpOcupacionAsientos.setUsuarioHardware(new UsuarioHardware(usuhar));
					tmpOcupacionAsientos.setUsuario(new Usuario(usuario.getId()));
					tmpOcupacionAsientos.setRuta(detalleItinerario.getRuta());
					tmpOcupacionAsientos.setItinerario(detalleItinerario.getItinerario());
					tmpOcupacionAsientos.setNumeroAsiento(asientoSeleccionado.getNumeroAsiento());
					tmpOcupacionAsientos.setNumeroPiso(asientoSeleccionado.getPiso());
					tmpOcupacionAsientos.setFechaPartida(Util.DatetoString(detalleItinerario.getFechaPartida(), Constantes.DATE_FORMAT));
					tmpOcupacionAsientos.setHoraPartida(detalleItinerario.getHoraPartida());
					tmpOcupacionAsientos.setEstadoRegistro(Constantes.VALUE_ACTIVO);
					UtilData.auditarRegistro(tmpOcupacionAsientos, false, usuario, Executions.getCurrent());
					TmpOcupacionAsientosID tmpOcupacionAsientosID = new TmpOcupacionAsientosID();
					tmpOcupacionAsientosID.setIdUsuarioHardware(tmpOcupacionAsientos.getUsuarioHardware().getId());
					tmpOcupacionAsientosID.setIdUsuario(tmpOcupacionAsientos.getUsuario().getId());
					tmpOcupacionAsientosID.setIdRuta(tmpOcupacionAsientos.getRuta().getId());
					tmpOcupacionAsientosID.setIdItinerario(tmpOcupacionAsientos.getItinerario().getId());
					tmpOcupacionAsientosID.setNumeroAsiento(tmpOcupacionAsientos.getNumeroAsiento());
					tmpOcupacionAsientos.setTmpOcupacionAsientosID(tmpOcupacionAsientosID);
					
					int result = ServiceLocator.getTmpOcupacionAsientosManager().bloquearAsiento(tmpOcupacionAsientos);
					/*	En caso el bloqueo de asiento no haya tenido exito	*/
					if(result < 0){
						DlgMessage.information(Messages.getString("WndVentaReserva.information.asientoBloqueado"));
						if(esIda)
							onRefreshMapaAsientos(mapaAsientos, asientoSeleccionado.getDetalleItinerario(), grdOcupabilidadIda);
						return;
					}else{	/*	Agregamos el asiento a la lista de seleccionados	*/
						mapaAsientos.get(key).setSrc(Constantes.PATH_PARTIAL+"asientoBloqueado_"+asientoSeleccionado.getNumeroAsiento()+Constantes.IMAGE_EXTENSION);
						mapaAsientos.get(key).setEstadoAsiento(Constantes.ASIENTO_BLOQUEADO);
						
						Listitem listitemAsientos = new Listitem();
						Listcell cell = new Listcell(asientoSeleccionado.getDetalleItinerario().getRuta().toString());
						listitemAsientos.appendChild(cell);
						cell = new Listcell(asientoSeleccionado.getNumeroAsiento().toString());
						listitemAsientos.appendChild(cell);
						asientoSeleccionado.getDetalleItinerario().setAsiento(asientoSeleccionado.getNumeroAsiento().toString());
						asientoSeleccionado.getDetalleItinerario().setPiso(String.valueOf(asientoSeleccionado.getPiso()));
						/*============================================================*/
						asientoSeleccionado.getDetalleItinerario().setEsIda(esIda);
						/*============================================================*/
						DetalleItinerario detalle = (DetalleItinerario)asientoSeleccionado.getDetalleItinerario().clone();
						listitemAsientos.setValue(detalle);
						lbxAsientos.appendChild(listitemAsientos);
					}
				}
			}
		}catch(DuplicateSeatException dsex){
			DlgMessage.information(Messages.getString("WndVentaReserva.information.asientoVendido"));
			onRefreshMap();
		}catch(DataIntegrityViolationException divex){
			DlgMessage.information(Messages.getString("WndVentaReserva.information.asientoBloqueado"));
		}catch(Exception ex){
			ex.printStackTrace();
			DlgMessage.error(this.getClass().getName()+" "+ex.getMessage());
		}
		
		/*	Habilita o deshablita el boton para pasar a la siguiente pestana	*/
		if(lbxAsientos.getItems().size()==0)
			btnNextTabVenta.setDisabled(true);
		else
			btnNextTabVenta.setDisabled(false);
	}
	
	/**
	 * Realiza la eliminacion del asiento seleccionado y luego lo desbloquea.
	 * @param seatSelected	: Asiento seleccionado.
	 * @return True si el asiento existe en la lista de asientos seleccionados.
	 */
	private boolean removerAsientoSeleccionado(String key, Map<String, Asiento> mapaAsientos, DetalleItinerario detalleItinerario){
		try{
			String[] buffer = key.split("-");	//Almacenamos en un array el asiento y el piso
			List<Listitem> items = lbxAsientos.getItems();
			for(int i=0; i<items.size(); i++){
				Listitem listitem = items.get(i);
				if(((DetalleItinerario)listitem.getValue()).getItinerario().getId().intValue()==detalleItinerario.getItinerario().getId().intValue() 
						&& ((DetalleItinerario)listitem.getValue()).getAsiento().equals(buffer[0]) && ((DetalleItinerario)listitem.getValue()).getPiso().equals(buffer[1])){
					lbxAsientos.removeItemAt(i);
					if(mapaAsientos.get(key).getEstadoAsiento().intValue()==Constantes.ASIENTO_BLOQUEADO){
						mapaAsientos.get(key).setEstadoAsiento(Constantes.ASIENTO_DISPONIBLE);
						mapaAsientos.get(key).setSrc(Constantes.ICON_DISPONIBLE+buffer[0]+Constantes.IMAGE_EXTENSION);
					}
					/*	Habilita o deshablita el boton para pasar a la siguiente pestana	*/
					if(lbxAsientos.getItems().size()==0)
						btnNextTabVenta.setDisabled(true);
					else
						btnNextTabVenta.setDisabled(false);
					
					/*	Desbloqueamos el asiento	*/
					TmpOcupacionAsientos tmpOcupacion = new TmpOcupacionAsientos();
					tmpOcupacion.setRuta(detalleItinerario.getRuta());
					tmpOcupacion.setItinerario(detalleItinerario.getItinerario());
					tmpOcupacion.setNumeroAsiento(Integer.valueOf(buffer[0]));
					tmpOcupacion.setNumeroPiso(Integer.valueOf(buffer[1]));
					ServiceLocator.getTmpOcupacionAsientosManager().desbloquearAsiento(tmpOcupacion);
					return true;
				}
			}						
		}catch(Exception ex){
			ex.printStackTrace();
		}
		return false;
	}
	
	/**
	 * Consulta si el asiento esta bloqueado.
	 * @param key	: Clave a buscar en el mapa de asientos.
	 * @return boolean
	 */
	private boolean consultaAsientoBloqueado(String key, Map<String, Asiento> mapaAsientos){
		if(mapaAsientos.get(key).getEstadoAsiento().intValue()==Constantes.ASIENTO_DISPONIBLE)
			return false;
		return true;
	}
	
	/**
	 * Selecciona el asiento de la lista de asientos escogidos para venta.
	 */
	public void lbxAsientos_onSelect(){
		if(lbxAsientos.getSelectedItem().getValue() instanceof DetalleItinerario){
			detailItinerary = (DetalleItinerario)lbxAsientos.getSelectedItem().getValue();
			onLoadDatosVentaIda(detailItinerary);
			lblNroAsiento.setValue(detailItinerary.getAsiento());
			onLoadPagos(detailItinerary);
			onLoadPuntoEmbarque(detailItinerary, cmbPtoEmbarque);
			onLoadPuntoDesembarque(detailItinerary, cmbPtoDesembarque);
			onLoadEspecieValorada(txtNumeroBoleto);
			lblTarifa.setValue(Util.toNumberFormat(detailItinerary.getTarifa(), 2));
			tabPagos.setDisabled(false);
			buscarPromocionPorTarifa(detailItinerary, lblTarifa, true);
		}
	}
	
	/**
	 * Buscamos las promociones que haran de tarifa en el caso del TEPSA SUITE o cualquier otro servicio.
	 * @param detalleItinerario	: Datos del itinerario a utilizar para realizar la busqueda de promocion por tarifa.
	 * @param lblTarifa			: Objeto en el cual se colocara la tarifa en caso de existir.
	 * @param esIda				: Booleano que indica si es la ida o el retorno.
	 */
	private void buscarPromocionPorTarifa(DetalleItinerario detalleItinerario, Label lblTarifa, boolean esIda){
		/**	
		 * Esta seccion es para obtener las promociones que son por tarifa	
		 * y que reeemplazaran la tarifa real del servicio.
		 */
		try {
			/*	Obtenemos las promociones que reemplazaran a la tarifa	*/
			List<Promocion>lstPromocion=ServiceLocator.getPromocionManager().buscarPorTarifa(Util.DatetoString(detalleItinerario.getFechaPartida(), Constantes.DATE_FORMAT), detalleItinerario.getRuta().getId().toString(), detalleItinerario.getItinerario().getServicio().getId().toString(),detalleItinerario.getHoraPartida().replaceAll(":", "."));
			Promocion promo = null;
			/*	Validando si la promocion cumple con el requisito del Servicio y la Ruta	*/
			for(int i=0; i<lstPromocion.size(); i++){
				String[] rutas = lstPromocion.get(i).getRutas().split(",");
				String[] servicios = lstPromocion.get(i).getServicios().split(",");
				for(int j=0; j<rutas.length; j++){
					if(rutas[j].equals(detalleItinerario.getRuta().getId().toString())){
						for(int k=0; k<servicios.length; k++){
							if(servicios[k].equals(detalleItinerario.getItinerario().getServicio().getId().toString())){
								promo = lstPromocion.get(i);
								break;
							}
						}
					}
				}
			}
			
			/*	Solo en caso de ventas	*/
			if(promo!=null){
				AplicarPromocion aplicarPromocion=createObjectAplicarPromocion(esIda);
				promocionAplicada= aplicarPromocion.executePromocion(promo.getId().toString(), false);
				if(promocionAplicada!=null)
					lblTarifa.setValue(Util.toNumberFormat(promocionAplicada.getPorImporte(),2));
			}
			
		} catch (Exception ex) {
			DlgMessage.error(this.getClass().getSimpleName()+" "+ex.getMessage());
			ex.printStackTrace();
		}
	}
	
	/**
	 * Realiza la carga de los pagos que se debe realizar.
	 * @param detalleItinerario	: tarifa del servicio de acuerdo al Itinerario.
	 */
	private void onLoadPagos(DetalleItinerario detalleItinerario){
		dblTarifa.setValue(detalleItinerario.getTarifa());
		dblDescuento.setValue(0.0);	
		dblRecargo.setValue(0.0);
		dblImporte.setValue(dblTarifa.getValue()+dblRecargo.getValue() - dblDescuento.getValue());
		chkPagoMixto.setChecked(false);
		habilitarPagoMixto();
		dblImporteEfectivo.setValue(0.0);
		dblImporteTarjeta.setValue(0.0);
		lblPromocion.setValue("");
		imgQuitarPromocion.setVisible(false);
	}
	
	/**
	 * Muestra los datos del itinerario seleccionado.
	 * @param detalleItinerario	: Itinerario seleccionado.
	 */
	private void onLoadDatosVentaIda(DetalleItinerario detalleItinerario){
		lblOrigen.setValue(detalleItinerario.getRuta().getOrigen());
		lblDestino.setValue(detalleItinerario.getRuta().getDestino());
		lblFechaPartida.setValue(Util.DatetoString(detalleItinerario.getFechaPartida(), Constantes.DATE_FORMAT));
		lblFechaLlegada.setValue(Util.DatetoString(detalleItinerario.getFechaLlegada(), Constantes.DATE_FORMAT));
		lblHoraPartida.setValue(detalleItinerario.getHoraPartida());
		lblHoraLlegada.setValue(detalleItinerario.getHoraLlegada());
	}
	
	/**
	 * Cargamos los puntos de embarque.
	 * @param detItinerario	: Itinerario del cual deseamos cargar los puntos de embarque.
	 * @throws Exception
	 */
	private void onLoadPuntoEmbarque(DetalleItinerario detItinerario, Combobox cmbParent){
		try{
			cmbParent.getItems().clear();
			
			ArrayList<ItinerarioAgenciaPartida> arrayItiAgePartida = new ArrayList<ItinerarioAgenciaPartida>();
			arrayItiAgePartida = ServiceLocator.getItinerarioManager().buscarAgenciasPartida(detItinerario.getItinerario().getId(), Constantes.VALUE_ACTIVO, detItinerario.getRuta().getLocalidadOrigen().getId());/*	Si la agencia de partida del itinerario es la misma a la agencia de la ruta seleccionada	*/
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
			UtilData.cargarGenericData(cmbParent, false);
			/*	Cargamos los puntos de embarque	*/
			for(ItinerarioAgenciaPartida itiAgePartida : arrayItiAgePartida){
				Comboitem item = new Comboitem(itiAgePartida.getAgencia().getDenominacion());
				item.setValue(itiAgePartida);
				cmbParent.appendChild(item);
				if(arrayItiAgePartida.size()==1){
					cmbParent.setSelectedItem(item);
					lblHoraPartida.setValue(itiAgePartida.getHoraPartida());
				}else if(detItinerario.getAgenciaPartida().getId().intValue()==itiAgePartida.getAgencia().getId().intValue()){
					cmbParent.setSelectedItem(item);
					lblHoraPartida.setValue(itiAgePartida.getHoraPartida());
				}
			}			
		}catch(Exception ex){
			ex.printStackTrace();
			DlgMessage.error(this.getClass().getName()+" "+ex.getMessage());
		}
	}
	
	/**
	 * Cargamos los puntos de desembarque.
	 * @param detItinerario	: Itinerario del cual deseamos cargar los puntos de desembarque.
	 * @throws Exception
	 */
	private void onLoadPuntoDesembarque(DetalleItinerario detItinerario, Combobox cmbParent) {
		try{
			cmbParent.getItems().clear();
			ArrayList<ItinerarioAgenciaLlegada> arrayItiAgeLlegada = new ArrayList<ItinerarioAgenciaLlegada>();
			arrayItiAgeLlegada = ServiceLocator.getItinerarioManager().buscarAgenciasLlegada(detItinerario.getItinerario().getId(), Constantes.VALUE_ACTIVO, detItinerario.getRuta().getLocalidadDestino().getId());
//			/*	Si la agencia de llegada del itinerario es la misma a la agencia de llegada de la ruta seleccionada	*/
//			if(detItinerario.getItinerario().getAgenciaLlegada().getId().intValue()==detItinerario.getAgenciaLlegada().getId().intValue())
//				arrayItiAgeLlegada = ServiceLocator.getItinerarioManager().buscarAgenciasLlegada(detItinerario.getItinerario().getId(), Constantes.VALUE_ACTIVO);
//			else{
//				ItinerarioAgenciaLlegada itiAgeLlegada = new ItinerarioAgenciaLlegada();
//				Agencia agencia = new Agencia();
//				agencia.setId(detItinerario.getAgenciaLlegada().getId());
//				agencia.setDenominacion(detItinerario.getAgenciaLlegada().getDenominacion());
//				itiAgeLlegada.setAgencia(agencia);
//				itiAgeLlegada.setHoraLlegada(detItinerario.getAgenciaLlegada().getHoraPartida());
//				arrayItiAgeLlegada.add(itiAgeLlegada);
//			}
			
			UtilData.cargarGenericData(cmbParent, false);
			for(ItinerarioAgenciaLlegada itiAgeLlegada : arrayItiAgeLlegada){
				Comboitem item = new Comboitem(itiAgeLlegada.getAgencia().getDenominacion());
                item.setValue(itiAgeLlegada);
                cmbParent.appendChild(item);
				if(arrayItiAgeLlegada.size()==1){
					cmbParent.setSelectedItem(item);
					lblHoraLlegada.setValue(itiAgeLlegada.getHoraLlegada());
				}else if(detItinerario.getAgenciaLlegada().getId().intValue()==itiAgeLlegada.getAgencia().getId().intValue()){
					cmbParent.setSelectedItem(item);
					lblHoraLlegada.setValue(itiAgeLlegada.getHoraLlegada());
				}
			}			
		}catch(Exception ex){
			ex.printStackTrace();
			DlgMessage.error(this.getClass().getName()+" "+ex.getMessage());
		}
	}	
	/**
	 * Muestra la hora de embarque segun el punto de embarque seleccionado
	 */
	public void onSelectPtoEmbarque(){
		if(cmbPtoEmbarque.getSelectedItem().getValue() instanceof ItinerarioAgenciaPartida)
			lblHoraPartida.setValue(((ItinerarioAgenciaPartida)cmbPtoEmbarque.getSelectedItem().getValue()).getHoraPartida());
		else
			lblHoraPartida.setValue("");
	}
	
	/**
	 * Muestrala hora de llegada segun el punto de desembarque seleccionado.
	 */
	public void onSelectPtoDesembarque(){
		if(cmbPtoDesembarque.getSelectedItem().getValue() instanceof ItinerarioAgenciaLlegada)
			lblHoraLlegada.setValue(((ItinerarioAgenciaLlegada)cmbPtoDesembarque.getSelectedItem().getValue()).getHoraLlegada());
		else
			lblHoraLlegada.setValue("");
	}
	
	/* *******************************	IMPLEMENTACIONES PARA EL PASAJERO	******************************************** */
	/**
	 * Para un nuevo pasajero
	 */
	public void onNewPax() {
		onCleanControlsPax();
		disabledControlsPax(false);
		action = Constantes.ACTION_NEW;
		tlbbtnNuevoPax.setDisabled(true);
		tlbbtnModificarPax.setDisabled(true);
		tlbbtnCancelarPax.setDisabled(false);
		tlbbtnGuardarPax.setDisabled(false);
		cmbTipoDocumento.setFocus(true);
		dblDescuento.setValue(0.0);
		imgQuitarPromocion.setVisible(false);
		lblPromocion.setValue("");
		promocionAplicada=null;
		dblImporte.setValue(dblTarifa.getValue()+dblRecargo.getValue()-dblDescuento.getValue());
		oPasajero=null;
	}

	/**
	 * Para modificar a un pasajero
	 */
	public void onModifyPax() {
		action = Constantes.ACTION_MODIFY;
		disabledControlsPax(false);
		tlbbtnNuevoPax.setDisabled(true);
		tlbbtnModificarPax.setDisabled(true);
		tlbbtnCancelarPax.setDisabled(false);
		tlbbtnGuardarPax.setDisabled(false);
		cmbTipoDocumento.setFocus(true);
	}
	
	/**
	 * Para cancelar el registro o la modificacion del pasajero
	 */
	public void onCancelPax() {
		disabledControlsPax(true);
		tlbbtnNuevoPax.setDisabled(false);
		tlbbtnModificarPax.setDisabled(true);
		tlbbtnCancelarPax.setDisabled(true);
		tlbbtnGuardarPax.setDisabled(true);
		tlbbtnLimpiarPax.setDisabled(false);
		action = Constantes.FAILURE;
		txtDocumentoPax.setFocus(true);
		
		//06/09/2013 - jabanto
		if(oPasajero==null)
			onCleanControlsPax();
	}
	
	/**
	 * Limpia los controles del pasajero.
	 */
	public void onCleanControlsPax() {
		cmbTipoDocumento.setSelectedIndex(0);
		Util.seleccionarValorItemCombo(TipoDocumento.class, cmbTipoDocumento, Constantes.ID_TIPDOC_DNI);
		txtDocumentoPax.setText("");
		txtApePat.setText("");
		txtApeMat.setText("");
		txtNombres.setText("");
		txtDireccionPax.setText("");
		txtTelefono.setText("");
		txtEmailPax.setText("");
		txtUbigeoPax.setText("");
		txtUbigeoIdPax.setText("");
		cmbSexo.setSelectedIndex(0);
		cmbEstadoCivil.setSelectedIndex(0);
		cmbNacionalidad.setSelectedIndex(0);
		rowAdicional.setVisible(false);
		oPasajero = null;
		imgPasajero.setSrc("");
		cmbAnio.setSelectedIndex(-1);
		cmbMes.setSelectedIndex(-1);
		cmbDia.setSelectedIndex(-1);
		cmbTipoDocumento.setFocus(true);		
	}

	/**
	 * Deshabilita o habilita los controles relacionados al pasajero.
	 * @param arg	: Boolean valor a utilizar.
	 */
	public void disabledControlsPax(boolean arg) {
		txtDireccionPax.setDisabled(arg);
		txtTelefono.setDisabled(arg);
		txtEmailPax.setDisabled(arg);
		txtUbigeoPax.setDisabled(arg);
		cmbSexo.setDisabled(arg);
		cmbEstadoCivil.setDisabled(arg);
		btnUbigeoPax.setDisabled(arg);
		cmbNacionalidad.setDisabled(arg);
		cmbAnio.setDisabled(arg);
		cmbMes.setDisabled(arg);
		cmbDia.setDisabled(arg);
	}
	
	/**
	 * Realiza la busqueda de pasajeros
	 * @param criterio
	 */
	public void onSearchPax(Integer criterio){
		try{
			oPasajero=null;
			TreeMap<String, Object> criterioBusqueda = new TreeMap<String, Object>();
			ArrayList<Pasajero> lstPasajeros = null;
			if(criterio.intValue()==SEARCH_BY_DOCUMENTO){
				criterioBusqueda.put("numeroDocumento", txtDocumentoPax.getText().toUpperCase()+"%");
				List<String> criteriosOrdenar = new ArrayList<String>();
				criteriosOrdenar.add("apellidoPaterno");
				criteriosOrdenar.add("apellidoMaterno");
				criteriosOrdenar.add("nombre");
				lstPasajeros = ServiceLocator.getPasajeroManager().buscarPorX(criterioBusqueda, criteriosOrdenar);
			}else{
				String nombres = txtApePat.getText().trim().toUpperCase() + 
						(txtApeMat.getText().trim().equals("")?"":(" " + txtApeMat.getText().trim().toUpperCase())) + 
						" " + txtNombres.getText().trim().toUpperCase();
				String[] str1 = nombres.trim().split(" ");
				lstPasajeros = ServiceLocator.getPasajeroManager().buscarPorFullTextIndex(str1);
			}
			
			listarRegistrosPax(lstPasajeros);
		}catch(Exception ex){
			ex.printStackTrace();
			DlgMessage.error(this.getClass().getName()+" "+ex.getMessage());
		}
	}
	
	/**
	 * Lista los resultados de la busqueda
	 * @param lstRegistros	: Lista de resultados.
	 * @throws Exception
	 */
	private void listarRegistrosPax(ArrayList<Pasajero> lstRegistros)throws Exception {
		ArrayList<Object> lstPasajeros = new ArrayList<Object>();
		grpbxListaPasajeros.setVisible(false);
		
		if(lstRegistros.size()==1){
			mantenimientoRegistroPax(lstRegistros.get(0).getId());
		}else if(lstRegistros.size()>1){
			for (int r = 0; r < lstRegistros.size(); r++) {
				Pasajero oPasajero = lstRegistros.get(r);
				ArrayList<Object> lstFila = new ArrayList<Object>();
				lstFila.add(oPasajero.getId());
				lstFila.add(r + 1);
				lstFila.add(oPasajero.getTipoDocumento().getDenominacion());
				lstFila.add(oPasajero.getNumeroDocumento());
				lstFila.add(oPasajero.toString());
				lstFila.add(Util.toNumberFormat(oPasajero.getKilometros(), 2));
				lstPasajeros.add(lstFila);
			}
			Util.llenarListbox(lbxPasajeros, lstPasajeros, true);
			grpbxListaPasajeros.setVisible(true);
		}else{
			DlgMessage.information(Messages.getString("WndVentaReserva.information.noPasajerosEncontrados"));
			onCleanControlsPax();
		}
		disabledControlsPax(true);
	}
	
	
	/**
	 * Verifica si el Pasajero existe el la reniec si es que se registrando un nuevo Pas
	 * @throws Exception 
	 * @throws WrongValueException 
	 */
	public void verificarPaxReniec() throws WrongValueException, Exception{
		if(action==Constantes.ACTION_NEW  && !(txtDocumentoPax.getText().trim().isEmpty()) 
				&& cmbTipoDocumento.getSelectedItem().getValue() instanceof TipoDocumento 
				&& ((TipoDocumento)cmbTipoDocumento.getSelectedItem().getValue()).getId().intValue()==Constantes.ID_TIPDOC_DNI){
			Reniec reniec= ServiceLocator.getReniecManager().buscarPax(txtDocumentoPax.getText().trim());
			if(reniec!=null){
				Util.seleccionarValorItemCombo(TipoDocumento.class, cmbTipoDocumento, Constantes.ID_TIPDOC_DNI);
				txtApePat.setText(reniec.getApellidoPaterno());
				txtApeMat.setText(reniec.getApellidoMaterno());
				txtNombres.setText(reniec.getNombres());
				mostrarFechaNacimiento(reniec.getFechaNacimiento());
				Util.seleccionarValorItemCombo(Sexo.class, cmbSexo, Integer.valueOf(reniec.getSexo()));
				
			}else{
				String numeroDocumento=txtDocumentoPax.getText().trim();
				Integer idTipoDocumento= null;
				if(cmbTipoDocumento.getSelectedItem().getValue() instanceof TipoDocumento)
					idTipoDocumento=((TipoDocumento)cmbTipoDocumento.getSelectedItem().getValue()).getId();
				
				onCleanControlsPax();
				//recupera valores ingresado por el usuario
				txtDocumentoPax.setText(numeroDocumento);
				if(idTipoDocumento!=null) Util.seleccionarValorItemCombo(TipoDocumento.class, cmbTipoDocumento, idTipoDocumento);
				txtApePat.setFocus(true);
			}
		}
	}
	
	/**
	 * Muestra los datos del registro que se va a modificar.
	 * @param id	: Identificador del registro a modificar
	 * @throws Exception
	 */
	private void mantenimientoRegistroPax(Long idPasajero) {
		try {
			convertirPaxFre = false;
			promocionAplicada = null;
			lblPromocion.setValue("");
			imgQuitarPromocion.setVisible(false);
			imgFidelizarPasajero.setVisible(false);
			tlbbtnModificarPax.setDisabled(false);
			oPasajero = ServiceLocator.getPasajeroManager().buscarPorId(idPasajero);
			
			Ubigeo oUbigeo = oPasajero.getUbigeo();
			EstadoCivil oEstadoCivil = oPasajero.getEstadoCivil();
			TipoDocumento oTipoDocumento = oPasajero.getTipoDocumento();
			Sexo oSexo = oPasajero.getSexo();
			
			Nacionalidad oNacionalidad = oPasajero.getNacionalidad();
			String idUbigeo = new String();
			String ubicacionCompleta = new String();

			if(oUbigeo!=null) {
				idUbigeo = oUbigeo.getId();
				ubicacionCompleta = ServiceLocator.getUbigeoManager().ubicacionGeografica(oUbigeo);
			}
			
			if(oEstadoCivil!=null)
				Util.seleccionarValorItemCombo(EstadoCivil.class, cmbEstadoCivil, oPasajero.getEstadoCivil().getId());
			else
				cmbEstadoCivil.setSelectedIndex(0);
			
			if(oTipoDocumento!=null)
				Util.seleccionarValorItemCombo(TipoDocumento.class, cmbTipoDocumento, oPasajero.getTipoDocumento().getId());
			else
				cmbTipoDocumento.setSelectedIndex(0);
			
			if(oSexo!=null)
				Util.seleccionarValorItemCombo(Sexo.class, cmbSexo, oPasajero.getSexo().getId());
			else
				cmbSexo.setSelectedIndex(0);
			
			if(oNacionalidad!=null)
				Util.seleccionarValorItemCombo(Nacionalidad.class, cmbNacionalidad, oNacionalidad.getId());
			else
				cmbNacionalidad.setSelectedIndex(0);
			
			habilitarNacionalidad();
			
			txtDocumentoPax.setText(oPasajero.getNumeroDocumento());
			txtApePat.setText(oPasajero.getApellidoPaterno());
			txtApeMat.setText(oPasajero.getApellidoMaterno());
			txtNombres.setText(oPasajero.getNombre());
			if(oPasajero.getFechaNacimiento() != null)
				mostrarFechaNacimiento(oPasajero.getFechaNacimiento());
			else
				mostrarFechaNacimiento(null);
			txtDireccionPax.setText(oPasajero.getDireccion());
			txtUbigeoIdPax.setText(idUbigeo);
			txtUbigeoPax.setText(ubicacionCompleta);
			txtTelefono.setText(oPasajero.getTelefono());
			txtEmailPax.setText(oPasajero.getEmail());

			if(oPasajero.getIndeseable().intValue() == Constantes.TRUE_VALUE) {
				lblInformativo1.setVisible(false);
				lblInformativo2.setValue("***  RESTRICCION DEL PASAJERO  ***");
				lblInformativo3.setValue(oPasajero.getMotivo());
				imgDetalleMotivo.setVisible(true);
			}else{
				lblInformativo1.setVisible(true);
				lblInformativo2.setValue("");
				lblInformativo3.setValue("");
				imgDetalleMotivo.setVisible(false);
			}

			if (oPasajero.getSexo() == null) {
				imgPasajero.setSrc("");
			} else if (oPasajero.getSexo().getId().equals(Constantes.ID_SEXO_FEMENINO))
				imgPasajero.setSrc("resources/mp_woman.png");
			else
				imgPasajero.setSrc("resources/mp_man.png");

			rowAdicional.setVisible(true);
			/* RECUPERANDO LOS DATOS DEL PASAJERO FRECUENTE */
			TreeMap<String, Object> criteriosBusqueda = new TreeMap<String, Object>();
			criteriosBusqueda.put("pasajero.id", oPasajero.getId());
			criteriosBusqueda.put("estado", Constantes.TRUE_VALUE);
			List<PasajeroFrecuente> lstPaxfree = ServiceLocator.getPasajeroFrecuenteManager().buscarPorX(criteriosBusqueda, null);

			String finicio = "";
			String ffin = "";
			Integer totalViajes = 0;
			/*
			 * Validando que el pasajero no sea Indeseable para hacer la validacion de PAXFREE
			 */
			if (oPasajero.getIndeseable().intValue() == Constantes.FALSE_VALUE) {
				if (lstPaxfree.size() > 0) {
					finicio=Constantes.FORMAT_DATE_TIME_24H.format(lstPaxfree.get(0).getFechaActivacion());
					ffin=Constantes.FORMAT_DATE_TIME_24H.format(lstPaxfree.get(0).getFechaCaducidad());
					totalViajes = ServiceLocator.getVentaPasajesManager().contarViajesValidos(oPasajero.getId(), finicio, ffin);
					lblInformativo1.setValue("PASAJERO FRECUENTE");
					lblInformativo3.setValue("NUMERO DE VIAJES VALIDOS DEL " + finicio.substring(0,10) + "  AL  " + ffin.substring(0,10) + " : " + totalViajes);
					oPasajero.setPaxFree(true);
					oPasajero.setPasajeroFrecuente(lstPaxfree.get(0));
					/*	Buscamos la promocion del Pasajero Frecuente	*/
					criteriosBusqueda = new TreeMap<String, Object>();
					criteriosBusqueda.put("cliente", "*");
					criteriosBusqueda.put("pasajeroFrecuente", "S");
					criteriosBusqueda.put("estadoRegistro", Constantes.VALUE_ACTIVO);
					/*	Validando que el PAXFRE se encuentre activo	*/
					if(oPasajero.getPasajeroFrecuente().getEstado().intValue()==Constantes.TRUE_VALUE){
						List<Promocion> lstPromocion = null;
						lstPromocion = ServiceLocator.getPromocionManager().buscarPorX(criteriosBusqueda, null, Util.DatetoString(detailItinerary.getFechaPartida(), Constantes.DATE_FORMAT));
						
						if(lstPromocion!=null && lstPromocion.size()==1){
							AplicarPromocion aplicarPromocion = createObjectAplicarPromocion(true);
							promocionAplicada = aplicarPromocion.executePromocion(lstPromocion.get(0).getId().toString(), false);
							if(promocionAplicada!=null)
								imgQuitarPromocion.setVisible(true);
						}else if(lstPromocion.size()>1)
							DlgMessage.information(Messages.getString("WndVentaPasajes.information.muchasPromocionesPaxFre"));
					}else{
						dblDescuento.setValue(0.00);
						dblDescuento.setTooltiptext("");
						dblImporte.setValue(dblTarifa.getValue()+dblRecargo.getValue()-dblDescuento.getValue());
					}
				}else {
					oPasajero.setPaxFree(false);
					oPasajero.setPasajeroFrecuente(null);
					Long tiempo = new Date().getTime() - (Constantes.MILISEGUNDOS_X_DIA * Constantes.TIEMPO_PASAR_PAXFREE);
					finicio = Util.DatetoString(new Date(tiempo), Constantes.DATE_FORMAT);
					ffin = Util.DatetoString(new Date(), Constantes.DATE_FORMAT);
					totalViajes = ServiceLocator.getVentaPasajesManager().contarViajesValidos(oPasajero.getId(), finicio, ffin);
					lblInformativo1.setValue("NUMERO DE VIAJES ULTIMOS SEIS MESES : " + totalViajes.toString());
					if (totalViajes.intValue() >= Constantes.NUMERO_VIAJES_PAXFREE){
						lblInformativo3.setValue("EL CLIENTE CALIFICA PARA SER PASAJERO FRECUENTE");
						convertirPaxFre = true;
					}else
						lblInformativo3.setValue("");
					dblDescuento.setValue(0.00);
					dblDescuento.setTooltiptext("");
					dblImporte.setValue(dblTarifa.getValue()+dblRecargo.getValue()-dblDescuento.getValue());
				}
				
				tlbbtnModificarPax.setDisabled(oPasajero.isPaxFree());
			}
		} catch (Exception ex) {
			ex.printStackTrace();		
			DlgMessage.error(this.getClass().getSimpleName() + " " + ex.getMessage());
		}
	}
	
	/**
	 * Realiza el guardado del pasajero
	 */
	public void onSavePax() {
		try {
			if (!(cmbTipoDocumento.getSelectedItem().getValue() instanceof TipoDocumento))
				throw new TipoDocumentoNullException();
			else if (!cmbTipoDocumento.getText().equals("S/D") && txtDocumentoPax.getText().equals(""))
				throw new NumeroDocumentoNullException();
			else if (txtApePat.getText().equals(""))
				throw new ApellidoPaternoNullException();
			else if (txtNombres.getText().equals(""))
				throw new NombresNullException();
			else if (txtUbigeoPax.getText().equals(""))
				throw new UbigeoNullException();
			else if (!(cmbSexo.getSelectedItem().getValue() instanceof Sexo))
				throw new SexoNullException();
			else if(cmbTipoDocumento.getSelectedItem().getValue() instanceof TipoDocumento && 
					((TipoDocumento)cmbTipoDocumento.getSelectedItem().getValue()).getId().intValue()!=Constantes.ID_TIPDOC_DNI){
				if(!(cmbNacionalidad.getSelectedItem().getValue() instanceof Nacionalidad))
					throw new NacionalidadException();
//				else if(txtEmailPax.getText().equals(""))
//					throw new EmailNullException();
			}else if(cmbTipoDocumento.getSelectedItem().getValue() instanceof TipoDocumento && 
					((TipoDocumento)cmbTipoDocumento.getSelectedItem().getValue()).getId().intValue()==Constantes.ID_TIPDOC_DNI){
				if(txtDocumentoPax.getText().trim().length()<8)
					throw new VentaReservaException(VentaReservaException.LONGITUD_NUMERO_DOCUMENTO);
			}
			
			if (!(txtEmailPax.getText().trim().isEmpty())){
				if (!(UtilData.validateEmail(txtEmailPax.getText().trim())))
					throw new MailIncorectoException();
			}

			if (action == Constantes.ACTION_NEW)
				oPasajero = new Pasajero();

			Ubigeo oUbigeo = new Ubigeo();
			oUbigeo.setId(txtUbigeoIdPax.getText());

			oPasajero.setApellidoPaterno(txtApePat.getText().trim().toUpperCase());
			oPasajero.setApellidoMaterno(txtApeMat.getText().trim().equals("")?null:txtApeMat.getText().toUpperCase());
			oPasajero.setNombre(txtNombres.getText().trim().toUpperCase());
			oPasajero.setTipoDocumento((TipoDocumento) cmbTipoDocumento.getSelectedItem().getValue());
			oPasajero.setNumeroDocumento(txtDocumentoPax.getText().trim().toUpperCase());
			oPasajero.setSexo((Sexo) cmbSexo.getSelectedItem().getValue());
			oPasajero.setEstadoCivil(cmbEstadoCivil.getSelectedItem().getValue() instanceof EstadoCivil ? (EstadoCivil) cmbEstadoCivil.getSelectedItem().getValue() : null);
			oPasajero.setUbigeo(oUbigeo);
			oPasajero.setDireccion(txtDireccionPax.getText().trim().toUpperCase());
			oPasajero.setEmail(txtEmailPax.getText().trim());
			oPasajero.setTelefono(txtTelefono.getText().trim().toUpperCase());
			oPasajero.setAgencia(agencia);
//			oPasajero.setFechaNacimiento(Util.DatetoString(dtbxFechaNacimiento.getValue(), Constantes.DATE_FORMAT).
//					equals(Util.DatetoString(Constantes.FECHA_NULL,Constantes.DATE_FORMAT)) ? null : Util.DatetoString(dtbxFechaNacimiento.getValue(),Constantes.DATE_FORMAT));
			oPasajero.setFechaNacimiento(generarFechaNacimiento());
			oPasajero.setNacionalidad(cmbNacionalidad.getSelectedItem().getValue() instanceof Nacionalidad?(Nacionalidad)cmbNacionalidad.getSelectedItem().getValue():null);
			oPasajero.setNombresApellidos(oPasajero.getNombre()+" "+oPasajero.getApellidoPaterno()+(oPasajero.getApellidoMaterno()==null?"":(" "+oPasajero.getApellidoMaterno())));

			if (action == Constantes.ACTION_NEW) {
				oPasajero.setIndeseable(Constantes.FALSE_VALUE);
				oPasajero.setMotivo("");
				oPasajero.setKilometros(0.00);
				UtilData.auditarRegistro(oPasajero, false, usuario, Executions.getCurrent());
			} else {
				UtilData.auditarRegistro(oPasajero, true, usuario, Executions.getCurrent());
			}
			oPasajero.setEstadoRegistro(Constantes.VALUE_ACTIVO);

			String msg = "";
			if (action == Constantes.ACTION_NEW)
				msg = Messages.getString("WndVentaReserva.question.guardarPasajero");
			else
				msg = Messages.getString("WndVentaReserva.question.actualizarPasajero");

			Messagebox.show(msg, DlgMessage.NOMBREAPLICACION, DlgMessage.BTN_YESNO, Messagebox.QUESTION, EventSavePax(oPasajero));

		} catch (TipoDocumentoNullException tdnex) {
			DlgMessage.information(Messages.getString("WndVentaReserva.information.noSelectionTipoDocumento"), cmbTipoDocumento);			
		} catch (NumeroDocumentoNullException ndnex) {
			DlgMessage.information(Messages.getString("WndVentaReserva.information.noDocumentoPax"), txtDocumentoPax);
		} catch (ApellidoPaternoNullException apnex) {
			DlgMessage.information(Messages.getString("WndVentaReserva.information.noApellidoPaterno"), txtApePat);
		} catch (NombresNullException nnex) {
			DlgMessage.information(Messages.getString("WndVentaReserva.information.noNombres"), txtNombres);
		} catch (UbigeoNullException unex) {
			DlgMessage.information(Messages.getString("WndVentaReserva.information.noUbigeo"), btnUbigeoPax);
		} catch (SexoNullException snex) {
			DlgMessage.information(Messages.getString("WndVentaReserva.information.noSelectionSexo"), cmbSexo);
		} catch (NacionalidadException nex){
			DlgMessage.information(Messages.getString("WndVentaReserva.information.noSeleccionoNacionalidad"), cmbNacionalidad);
		} catch(EmailNullException enex){
			DlgMessage.information(Messages.getString("WndVentaReserva.information.noIngresoEmail"), txtEmailPax);
		} catch(MailIncorectoException miex) {
			DlgMessage.information(Messages.getString("WndVentaReserva.information.ingreseCorreoValido"), txtEmailPax);
		} catch(VentaReservaException vrex){
			if(vrex.getTipo().intValue()==VentaReservaException.LONGITUD_NUMERO_DOCUMENTO)
				DlgMessage.information(Messages.getString("WndVentaReserva.information.longitudDocumento"), txtDocumentoPax);
		}catch (Exception ex) {
			DlgMessage.error(this.getClass().getName() + " " + ex.getMessage());
		}
	}
	
	/**
	 * Evento para controlar el guardado del registro de Pasajero.
	 * @param action	: Indica si es una insercion o actualizacion.
	 * @param oPasajero	: Objeto a guardar.
	 * @return Event
	 */
	private EventListener<Event> EventSavePax(final Pasajero oPasajero) {
		EventListener<Event> ev = new EventListener<Event>() {
			@Override
			public void onEvent(Event e) {
				try {
					if (e.getName().equals("onYes")) {
						if (action == Constantes.ACTION_NEW) {
							ServiceLocator.getPasajeroManager().guardar(oPasajero);
							DlgMessage.information(Messages.getString("WndVentaReserva.information.exitoGuardar"));
						} else {
							ServiceLocator.getPasajeroManager().actualizar(oPasajero);
							DlgMessage.information(Messages.getString("WndVentaReserva.information.exitoActualizar"));
						}
						tlbbtnNuevoPax.setDisabled(false);
						tlbbtnModificarPax.setDisabled(false);
						tlbbtnCancelarPax.setDisabled(true);
						tlbbtnGuardarPax.setDisabled(true);
						action = Constantes.FAILURE;
						disabledControlsPax(true);
					}
				} catch (DocumentoPaxDuplicadoException dpdnex) {
					DlgMessage.information(Messages.getString("WndPasajero.information.noDocumentoPaxDuplicado"), txtDocumentoPax);
				} catch (Exception ex) {
					DlgMessage.error(this.getClass().getSimpleName() + " " + ex.getMessage());
				}
			}
		};
		return ev;
	}
	

	/* *******************************	IMPLEMENTACIONES PARA EL CLIENTE	******************************************** */
	/**
	 * Para ingresar un nuevo cliente
	 */
	public void onNewClient() {
		onCleanControlsClient();
		disabledControlsClient(false);
		action = Constantes.ACTION_NEW;
		tlbbtnNuevoClient.setDisabled(true);
		tlbbtnModificarClient.setDisabled(true);
		tlbbtnCancelarClient.setDisabled(false);
		tlbbtnGuardarClient.setDisabled(false);
		txtDocumentoCliente.setFocus(true);
		
		dblDescuento.setValue(0.0);
		imgQuitarPromocion.setVisible(false);
		lblPromocion.setValue("");
		promocionAplicada=null;
		dblImporte.setValue(dblTarifa.getValue()+dblRecargo.getValue()-dblDescuento.getValue());
	}
	
	/**
	 * Para modificar un Cliente existente
	 */
	public void onModifyClient() {
		action = Constantes.ACTION_MODIFY;
		disabledControlsClient(false);
		tlbbtnNuevoClient.setDisabled(true);
		tlbbtnModificarClient.setDisabled(true);
		tlbbtnCancelarClient.setDisabled(false);
		tlbbtnGuardarClient.setDisabled(false);
		txtDocumentoCliente.setFocus(true);
		
	}
	
	/**
	 * Para cancelar la edicion o registro de un cliente
	 */
	public void onCancelClient() {
		disabledControlsPax(true);
		tlbbtnNuevoClient.setDisabled(false);
		tlbbtnModificarClient.setDisabled(true);
		tlbbtnCancelarClient.setDisabled(true);
		tlbbtnGuardarClient.setDisabled(true);
		tlbbtnLimpiarClient.setDisabled(false);
		action = Constantes.FAILURE;
		txtDocumentoCliente.setFocus(true);		
	}

	/**
	 * Limpia los controles del cliente.
	 */
	public void onCleanControlsClient() {
		txtDocumentoCliente.setText("");
		txtRazonSocial.setText("");
		txtDireccionCliente.setText("");
		txtTelefonoClienteOne.setText("");
		txtTelefonoClienteTwo.setText("");
		txtEmailCliente.setText("");
		txtUbigeoCliente.setText("");
		txtUbigeoIdCliente.setText("");
		txtContactoCliente.setText("");
		txtRubro.setText("");
		ibxCantidadTrabajadores.setText("");
		oCliente = null;
		lineaCreditoCliente=null;
		lblDescuento.setValue("");
		txtDocumentoCliente.setFocus(true);
	}

	/**
	 * Deshabilita o habilita los controles relacionados al cliente.
	 * @param arg	= Boolean valor a utilizar.
	 */
	public void disabledControlsClient(boolean arg) {
		txtDireccionCliente.setDisabled(arg);
		txtTelefonoClienteOne.setDisabled(arg);
		txtTelefonoClienteTwo.setDisabled(arg);
		txtEmailCliente.setDisabled(arg);
		txtContactoCliente.setDisabled(arg);
		txtUbigeoCliente.setDisabled(arg);
		txtRubro.setDisabled(arg);
		ibxCantidadTrabajadores.setDisabled(arg);
	}
	
	/**
	 * Busca los clientes de acuerdo al criterio de busqueda.
	 * @param criterio	: Puede ser DOCUMENTO o RAZON SOCIAL.
	 */
	public void onSearchClient(Integer criterio){
		try{
			TreeMap<String, Object> criterioBusqueda = new TreeMap<String, Object>();
			ArrayList<Cliente> lstClientes = null;
			if(criterio.intValue()==SEARCH_BY_DOCUMENTO){
				criterioBusqueda.put("numeroDocumento", txtDocumentoCliente.getText().toUpperCase()+"%");
				List<String> criteriosOrdenar = new ArrayList<String>();
				criteriosOrdenar.add("razonSocial");
				criteriosOrdenar.add("numeroDocumento");
				lstClientes = ServiceLocator.getClienteManager().buscarPorX(criterioBusqueda, criteriosOrdenar);
			}else{
				String[] str1 = txtRazonSocial.getText().trim().toUpperCase().split(" ");
				lstClientes = ServiceLocator.getClienteManager().buscarPorRazonSocial(str1);
			}
			listarClientes(lstClientes);
		}catch(Exception ex){
			DlgMessage.error(this.getClass().getSimpleName()+" "+ex.getMessage());
			ex.printStackTrace();
		}
	}
	
	/**
	 * Permite crear un array de array para luego cargarlos en un listbox.
	 * @param lstRegistros	: ArrayList con los datos de los clientes encontrados.
	 * @throws Exception
	 */
	private void listarClientes(ArrayList<Cliente> lstRegistros)throws Exception{
		ArrayList<Object> lstClientes = new ArrayList<Object>();
		grpbxListaClientes.setVisible(false);
		
		if(lstRegistros.size()==1){
			Cliente cliente = lstRegistros.get(0);
			mantenimientoRegistroClient(cliente.getId());
		}else if(lstRegistros.size()>0){
			for (int r = 0; r < lstRegistros.size(); r++) {
				Cliente oCliente = lstRegistros.get(r);
				ArrayList<Object> lstFila = new ArrayList<Object>();
				lstFila.add(oCliente.getId());
				lstFila.add(r + 1);
				lstFila.add(oCliente.getNumeroDocumento());
				lstFila.add(oCliente.getRazonSocial());
				lstFila.add(oCliente.getContacto());
				lstClientes.add(lstFila);
			}
			Util.llenarListbox(lbxClientes, lstClientes, true);
			grpbxListaClientes.setVisible(true);			
		}else{
			DlgMessage.information(Messages.getString("WndVentaReserva.information.noClientesEncontrados"));
			onCleanControlsClient();
		}
		disabledControlsPax(true);
	}
	
	/**
	 * Muestra los datos del registro que se va a modificar.
	 * @param id	: Identificador del registro a modificar.
	 * @throws Exception
	 */
	 private void mantenimientoRegistroClient(Long id) {
		try {
			promocionAplicada = null;
			tlbbtnModificarClient.setDisabled(false);
			oCliente = ServiceLocator.getClienteManager().buscarPorId(id);
			Ubigeo oUbigeo = oCliente.getUbigeo();
			String idUbigeo = new String();
			String ubicacionCompleta = new String();
			if (oUbigeo != null) {
				idUbigeo = oUbigeo.getId();
				ubicacionCompleta = ServiceLocator.getUbigeoManager().ubicacionGeografica(oUbigeo);
			}

			txtRazonSocial.setText(oCliente.getRazonSocial());
			txtDocumentoCliente.setText(oCliente.getNumeroDocumento());
			txtDireccionCliente.setText(oCliente.getDireccion());
			txtUbigeoIdCliente.setText(idUbigeo);
			txtUbigeoCliente.setText(ubicacionCompleta);
			txtContactoCliente.setText(oCliente.getContacto());
			txtTelefonoClienteOne.setText(oCliente.getTelefonoFijo());
			txtTelefonoClienteTwo.setText(oCliente.getTelefonoFijo2());
			txtEmailCliente.setText(oCliente.getEmail());
			txtRubro.setText(oCliente.getRubro());
			ibxCantidadTrabajadores.setText(oCliente.getCantidadTrabajadores().toString());
			
			lineaCreditoCliente=null;

			lblDescuento.setValue("");
			
			if(((TipoAgencia)cmbTipoAgenciaRemota.getSelectedItem().getValue()).getId().intValue()==Constantes.ID_TIPAGE_CORPORATIVO){
				loadCentroCosto();
				tlbbtnLimpiarClient.setDisabled(true);
			}else
				tlbbtnLimpiarClient.setDisabled(false);
				
			/*	Si la venta es desde un punto TEPSA	*/
			if(agencia.getTipoAgencia().getId().intValue()==Constantes.ID_TIPAGE_TEPSA){
				/* Valida si el cliente tiene credito- Implementado 17/03/2013 */
				LineaCreditoCliente lineaCreditoCliente = ServiceLocator.getLineaCreditoClienteManager().validacionCreditoCliente(oCliente.getId());
				/*	Si tiene linea de credito*/
				if(lineaCreditoCliente != null) {
					/* Desactiva el button "Modificar cliente" */
					tlbbtnModificarClient.setDisabled(true);
					tlbbtnModificarClient.setImage("resources/mp_editarDisabled.png");
					if(lineaCreditoCliente.getEsCanje().equals(Constantes.SI)) {
						lblDescuento.setValue(Constantes.TIPCONVCLI_CREDITO_CANJE_PUBLICITARIO);
					}else{
						lblDescuento.setValue(Constantes.TIPCONVCLI_CORPORATIVO);
					}
					this.lineaCreditoCliente = lineaCreditoCliente;
					Util.seleccionarValorItemCombo(FormaPago.class,cmbFormaPago, Constantes.ID_FORPAG_CONTADO);
					onLoadTipoFormaPago();
				}else {
					lblDescuento.setValue(Constantes.TIPCONVCLI_CONTADO);
					cmbFormaPago.setDisabled(true);
					tlbbtnModificarClient.setDisabled(false);
					tlbbtnModificarClient.setImage("resources/mp_editarEnabled.png");
				}
			}
			
			/*	Verificamos promociones para el cliente */
			TreeMap<String, Object> criteriosBusqueda = new TreeMap<String, Object>();
			criteriosBusqueda.put("cliente", oCliente.getId().toString());
			criteriosBusqueda.put("estadoRegistro", Constantes.VALUE_ACTIVO);
			
			List<Promocion> lstPromocion = null;
			
			if(detailItinerary!=null)
				lstPromocion = ServiceLocator.getPromocionManager().buscarPorX(criteriosBusqueda, null, Util.DatetoString(detailItinerary.getFechaPartida(), Constantes.DATE_FORMAT));

			if(lstPromocion!=null && lstPromocion.size()>0){
				for(int i=0; i<lstPromocion.size(); i++){
					AplicarPromocion aplicarPromocion = createObjectAplicarPromocion(true);
					promocionAplicada = aplicarPromocion.executePromocion(lstPromocion.get(i).getId().toString(), false);
					if(promocionAplicada!=null){
						break;
					}
					imgQuitarPromocion.setVisible(true);
				}
				dblImporte.setValue(dblTarifa.getValue()+dblRecargo.getValue()-dblDescuento.getValue());
			}
		}catch (Exception ex) {
			ex.printStackTrace();
			DlgMessage.error(this.getClass().getSimpleName() + " " + ex.getMessage());
		}
	}
	
	 
	/**
	 * Realiza el guardado de los datos del cliente
	 * @throws Exception
	 */
	public void onSaveClient()throws Exception{
		try{
			if(txtDocumentoCliente.getText().trim().equals(""))
				throw new NumeroDocumentoNullException();
			else if(txtRazonSocial.getText().trim().equals(""))
				throw new RazonSocialNullException();
			else if(txtUbigeoCliente.getText().trim().equals(""))
				throw new UbigeoNullException();
//			else if (ibxCantidadTrabajadores.getText().trim().isEmpty() || ibxCantidadTrabajadores.getValue().intValue()<=0)
//				throw new CantidadTrabajadoresNullException();
			
			if (!(txtEmailCliente.getText().trim().isEmpty())){
				if (!(UtilData.validateEmail(txtEmailCliente.getText().trim())))
					throw new MailIncorectoException();
			}
			
			if(!Util.validarRUC(txtDocumentoCliente.getText().trim()))
				throw new RucInvalidoException();
			
			if (action == Constantes.ACTION_NEW)
				oCliente = new Cliente();
			
			Ubigeo oUbigeo = new Ubigeo();
			oUbigeo.setId(txtUbigeoIdCliente.getText());
			
			oCliente.setNumeroDocumento(txtDocumentoCliente.getValue().toString());
			oCliente.setRazonSocial(txtRazonSocial.getText().toUpperCase());
			oCliente.setContacto(txtContactoCliente.getText().toUpperCase());
			oCliente.setDireccion(txtDireccionCliente.getText().toUpperCase());
			oCliente.setTelefonoFijo(txtTelefonoClienteOne.getText().toUpperCase());
			oCliente.setTelefonoFijo2(txtTelefonoClienteTwo.getText().toUpperCase());
			oCliente.setEmail(txtEmailCliente.getText());
			oCliente.setRubro(txtRubro.getText().trim().toUpperCase());
			oCliente.setCantidadTrabajadores(0);
			oCliente.setUbigeo(oUbigeo);
			oCliente.setAgencia(agencia);
			
			if(oCliente.getId()==null){
				oCliente.setKilometros(0.00);
				UtilData.auditarRegistro(oCliente, false, usuario, Executions.getCurrent()); 
			}else{
				UtilData.auditarRegistro(oCliente, true, usuario, Executions.getCurrent());
			}
			oCliente.setEstadoRegistro(Constantes.VALUE_ACTIVO);
			
			String msg = "";
			if(action == Constantes.ACTION_NEW)
				msg = Messages.getString("WndVentaReserva.question.guardarCliente");
			else
				msg = Messages.getString("WndVentaReserva.question.actualizarCliente");
			
			Messagebox.show(msg, DlgMessage.NOMBREAPLICACION, DlgMessage.BTN_YESNO, Messagebox.QUESTION, EventSaveClient(oCliente));
//		}catch (CantidadTrabajadoresNullException ctnex){
//			DlgMessage.information(Messages.getString("WndCliente.information.CantidadTrabajadoresNull"));	
		}catch (MailIncorectoException miec){
			DlgMessage.information(Messages.getString("WndVentaReserva.information.EmailIncorrecto"), txtEmailCliente);
		}catch(NumeroDocumentoNullException ndnex){
			DlgMessage.information(Messages.getString("WndVentaReserva.information.noDocumentoCliente"), txtDocumentoCliente);
		}catch(RazonSocialNullException rsnex){
			DlgMessage.information(Messages.getString("WndVentaReserva.information.noRazonSocial"));
			txtRazonSocial.setFocus(true);
		}catch(UbigeoNullException unex){
			DlgMessage.information(Messages.getString("WndVentaReserva.information.noUbigeo"));
			txtUbigeoCliente.setFocus(true);
		}catch(RucInvalidoException riex){
			DlgMessage.information(Messages.getString("WndVentaReserva.information.rucInvalido"), txtDocumentoCliente);
		}catch(Exception ex){
			DlgMessage.error(this.getClass().getName()+" "+ex.getMessage());
		}
	}
	
	/**
	 * Evento para controlar el guardado del registro de Cliente.
	 * @param action	: Indica si es una insercion o actualizacion.
	 * @param oCliente	: Objeto a guardar.
	 * @return Event
	 */
	private EventListener<Event> EventSaveClient(final Cliente oCliente){
		EventListener<Event> ev = new EventListener<Event>() {
			@Override
			public void onEvent(Event e) {
				try {
					if (e.getName().equals("onYes")) {
						if (action == Constantes.ACTION_NEW) {
							ServiceLocator.getClienteManager().guardar(oCliente);
							DlgMessage.information(Messages.getString("WndVentaReserva.information.exitoGuardar"));
						} else {
							ServiceLocator.getClienteManager().actualizar(oCliente);
							DlgMessage.information(Messages.getString("WndVentaReserva.information.exitoActualizar"));
						}
						tlbbtnNuevoClient.setDisabled(false);
						tlbbtnModificarClient.setDisabled(false);
						tlbbtnCancelarClient.setDisabled(true);
						tlbbtnGuardarClient.setDisabled(true);
						action = Constantes.FAILURE;
						disabledControlsClient(true);						
					}
				} catch (RucDuplicadoException rdex) {
					DlgMessage.information(Messages.getString("WndCliente.information.RucDuplicado"), txtDocumentoCliente);
				} catch (RazonSocialDuplicadoException rsdex) {
					DlgMessage.information(Messages.getString("WndCliente.information.RazonSocialDuplicado"), txtRazonSocial);
				} catch (Exception ex) {ex.printStackTrace();
					DlgMessage.error(this.getClass().getSimpleName() + " " + ex.getMessage());
				}
			}
		};		
		return ev;
	}
	
	/**
	 * Carga los tipos de Forma de pago.
	 */
	public void onLoadTipoFormaPago(){
		try{			
			cmbTipoFormaPago.getItems().clear();
			
			/*Implementado 15-03-2013 - jabanto*/
			cmbOperadorTarjetaCredito.getItems().clear();
			cmbTarjetaCredito.getItems().clear();
			cmbTipoFormaPago.setText(null);
			cmbOperadorTarjetaCredito.setText(null);
			cmbTarjetaCredito.setText(null);
			cmbOperadorTarjetaCredito.setDisabled(true);
			cmbTarjetaCredito.setDisabled(true);
					
			if(cmbFormaPago.getSelectedItem().getValue() instanceof FormaPago){
				FormaPago formaPago = cmbFormaPago.getSelectedItem().getValue();
				TreeMap<String, Object> criteriosBusqueda = new TreeMap<String, Object>();
				criteriosBusqueda.put("formaPago.id", formaPago.getId());
				List<String> criteriosOrdenar = new ArrayList<String>();
				criteriosOrdenar.add("denominacion");
				List<TipoFormaPago> lstTipoFormasPago = ServiceLocator.getTipoFormaPagoManager().buscarPorX(criteriosBusqueda, criteriosOrdenar);
				UtilData.cargarGenericData(cmbTipoFormaPago, false);
				for(TipoFormaPago tipoFormaPago : lstTipoFormasPago){
					Comboitem item = new Comboitem();
					item.setLabel(tipoFormaPago.getDenominacion());
					item.setValue(tipoFormaPago);
					cmbTipoFormaPago.appendChild(item);
				}
				cmbTipoFormaPago.setDisabled(true);				
				
				/*Valida si el Cliente credito cuenta con saldo suficiente - Implementado 15/03/2013 jabanto*/
				if(agencia.getTipoAgencia().getId().equals(Constantes.ID_TIPAGE_TEPSA)){
					cmbTipoFormaPago.setDisabled(false);					
					if(formaPago.getId().equals(Constantes.ID_FORPAG_CREDITO)){
						if(lineaCreditoCliente!=null){
							if(lineaCreditoCliente.getSaldo()<=0 || lineaCreditoCliente.getSaldo() < (dblImporte.getValue()!=null? dblImporte.getValue(): .00)){
								cmbFormaPago.setSelectedIndex(0);
								cmbTipoFormaPago.getItems().clear();
								cmbTipoFormaPago.setText(null);
								cmbTipoFormaPago.setDisabled(true);
								throw new SaldoInsuficienteException();
							}
							
							if(lineaCreditoCliente.getEsCanje().equals(Constantes.SI))
								Util.seleccionarValorItemCombo(TipoFormaPago.class, cmbTipoFormaPago, Constantes.ID_TIPFORPAG_CANJE_PUBLICITARIO);								
							else
								Util.seleccionarValorItemCombo(TipoFormaPago.class, cmbTipoFormaPago, Constantes.ID_TIPFORPAG_CREDITO);								
							cmbTipoFormaPago.setDisabled(true);
						}
					}else if(formaPago.getId().equals(Constantes.ID_FORPAG_CONTADO))
						Util.seleccionarValorItemCombo(TipoFormaPago.class, cmbTipoFormaPago, Constantes.ID_TIPFORPAG_EFECTIVO);
				}else{
					Util.seleccionarValorItemCombo(TipoFormaPago.class, cmbTipoFormaPago, Constantes.ID_TIPFORPAG_CREDITO);
					if(agencia.getTipoAgencia().getId().equals(Constantes.ID_TIPAGE_VIAJES)){
						onValidateTipoFormaPago();
					}
				}
			}else
				cmbTipoFormaPago.setDisabled(true);
			
		}catch(SaldoInsuficienteException siex){
			DlgMessage.information(Messages.getString("WndVentaReserva.information.saldoInsuficiente"));
		}catch(Exception ex){
			DlgMessage.error(this.getClass().getSimpleName()+" "+ex.getMessage());
		}
	}
	
	/**
	 * Realiza una validación del Tipo de Forma de Pago, para habilitar o deshabilitar algunos controles.
	 * @throws Exception 
	 */
	public void onValidateTipoFormaPago() throws Exception{
		cmbOperadorTarjetaCredito.getItems().clear();
		cmbOperadorTarjetaCredito.setText("");
		cmbOperadorTarjetaCredito.setDisabled(true);
		cmbTarjetaCredito.getItems().clear();
		cmbTarjetaCredito.setText("");
		cmbTarjetaCredito.setDisabled(true);
		
		if(cmbTipoFormaPago.getSelectedItem().getValue() instanceof TipoFormaPago){ 
			/*	Si es tarjeta cargamos los operadores de tarjeta de credito	*/
			/*	Aqui realizamos una jugada para el caso de TU ENTRADA y agencias de viaje para que ingresen la tarjeta de credito	*/
			if(cmbTipoFormaPago.getText().equals("TARJETA") || cmbTipoFormaPago.getText().equals("CREDITO")){
				cmbOperadorTarjetaCredito.getItems().clear();
				UtilData.cargarDataCombo(cmbOperadorTarjetaCredito, OperadorTarjetaCredito.class, false);
				cmbOperadorTarjetaCredito.setDisabled(false);
			}else if(cmbTipoFormaPago.getText().equals("TRANSFERENCIA"))	//Si es una transferecnia bancaria
				txtOperacionBancaria.setDisabled(false);
			else{
				txtOperacionBancaria.setDisabled(true);
				cmbOperadorTarjetaCredito.setDisabled(true);
			}
		}else{
			txtOperacionBancaria.setDisabled(true);
			cmbOperadorTarjetaCredito.setDisabled(true);
		}
	}	
	
	/**
	 * Carga los diferentes tarjetas de credito, de acuerdo al operador seleccionado.
	 */
	public void onLoadTarjetas(){
		try{
			cmbTarjetaCredito.getItems().clear();
			cmbTarjetaCredito.setText("");
			cmbTarjetaCredito.setDisabled(true);
			
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
			}
		}catch(Exception ex){
			ex.printStackTrace();
		}
	}
	
	/**
	 * Proceso que selecciona el metodo correcto a utilizar al momento de guaradr la venta o reserva.
	 */
	public void onGuardarVentaReserva()throws Exception{
		try{
			/*	Valida el credito del cliente, si la venta es al Credito	*/
			if(((FormaPago)cmbFormaPago.getSelectedItem().getValue()).getId().equals(Constantes.ID_FORPAG_CREDITO)){
				lineaCreditoCliente=null;
				lineaCreditoCliente= ServiceLocator.getLineaCreditoClienteManager().validacionCreditoCliente(clienteCredito.getId());

				if(lineaCreditoCliente!=null && lineaCreditoCliente.getSaldo() < dblImporte.getValue())
					throw new LineaCreditoClienteException(LineaCreditoClienteException.LINEA_CREDITO_SIN_SALDO);				
			}
			
			/*	Validando que el pasajero sea convertido a Paxfre */
			if(convertirPaxFre)
				throw new VentaReservaException(VentaReservaException.CONVERTIR_PASAJERO_PAXFRE);
			
			onCrearObjetoVenta();
			
		}catch (LineaCreditoClienteException lccex) {
			if(lccex.getTipo().intValue()==LineaCreditoClienteException.LINEA_CREDITO_SIN_SALDO)
				DlgMessage.information(Messages.getString("tblLineaCreditoCliente.information.noSaldoDisponible"));
			else if(lccex.getTipo().intValue()==LineaCreditoClienteException.LINEA_CREDITO_NO_ACTIVO)
				DlgMessage.information(Messages.getString("tblLineaCreditoCliente.information.lineaNoActiva"));
			liberarAsientos();
			closeTabWindow();
		}catch(VentaReservaException vrex){
			if(vrex.getTipo().intValue()==VentaReservaException.CONVERTIR_PASAJERO_PAXFRE){
				DlgMessage.information(Messages.getString("WndVentaReserva.information.fidelizarPasajero"));
				tabPasajero.setSelected(true);
			}
		}catch(Exception ex){
			DlgMessage.error(this.getClass().getSimpleName()+" "+ex.getMessage());
		}
	}
	
	public VentaPasaje onCrearObjetoVenta(){
		try{
			imgRefreshBoleto.setVisible(false);
			ventaPasaje = null;
			
			if(detailItinerary == null)
				throw new ItinerarioException(ItinerarioException.NO_SELECT); // ItinerarioNotSelectedException();
			else if(!(cmbPtoEmbarque.getSelectedItem().getValue() instanceof ItinerarioAgenciaPartida))
				throw new ItinerarioException(ItinerarioException.AGENCIA_PARTIDA_NULL); // ItinerarioAgenciaPartidaNullException();
			else if(!(cmbPtoDesembarque.getSelectedItem().getValue() instanceof ItinerarioAgenciaLlegada))
				throw new ItinerarioException(ItinerarioException.AGENCIA_LLEGADA_NULL); //ItinerarioAgenciaLlegadaNullException();
			else if(!(cmbAlimentacion.getSelectedItem().getValue() instanceof PreferenciaAlimentaria))
				throw new PreferenciaAlimentariaException(PreferenciaAlimentariaException.ALIMENTACION_IDA_NULL);			
			
			if(txtNumeroBoleto.getText().trim().equals(""))
				throw new NumeroBoletoNullException();
			else if (!(tlbbtnGuardarPax.isDisabled()))// 06/09/2013 - jabanto
				throw new PasajeroNoSavedExeption();
			else if(oPasajero==null)
				throw new PasajeroException();
			else if(oPasajero!=null && oPasajero.getId()==null)
				throw new PasajeroException();
			else if(!(cmbTipoComprobante.getSelectedItem().getValue() instanceof TipoComprobante))
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
			}else if(cmbTipoFormaPago.getSelectedItem().getValue() instanceof TipoFormaPago && cmbTipoFormaPago.getText().equals(TipoFormaPago.TIPO_TRANSFERENCIA)){
				if(txtOperacionBancaria.getText().trim().equals(""))
					throw new NumeroOperacionBancariaNullException();
			}else if(oPasajero.getIndeseable().intValue()==Constantes.TRUE_VALUE)
				throw new PasajeroIndeseableException();
			else if(cmbTipoFormaPago.getSelectedItem().getValue() instanceof TipoFormaPago 
					&& cmbTipoFormaPago.getText().equals(TipoFormaPago.TIPO_CREDITO)){
				if((cmbOperadorTarjetaCredito.getItems().size()>0 && cmbOperadorTarjetaCredito.getSelectedItem().getValue() instanceof OperadorTarjetaCredito) && !(cmbTarjetaCredito.getSelectedItem().getValue() instanceof TarjetaCredito))
					throw new TarjetaCreditoNullException();
			}else if(dblTarifa.getValue()<=0.0)
				throw new VentaReservaException(VentaReservaException.TARIFA_IDA_CERO);
			
			Liquidacion liquidacion = ServiceLocator.getLiquidacionManager().buscarUltimaLiquidacion(((Agencia)cmbAgenciaRemota.getSelectedItem().getValue()).getId(), 
						((Usuario)cmbUsuarioRemoto.getSelectedItem().getValue()).getId(), Constantes.LIQUI_ESTA_ABIERTO);
			if(liquidacion==null){
				fechaLiquidacionRemota = fechaLiquidacion;
			}else
				fechaLiquidacionRemota = liquidacion.getFechaLiquidacion();
			
			ventaPasaje = new VentaPasaje();
			
			DetalleItinerario detalleItinerario = null;
			detalleItinerario = (DetalleItinerario)lbxAsientos.getSelectedItem().getValue();
			ventaPasaje.setEsFechaAbierta(Constantes.FALSE_VALUE);
			ventaPasaje.setIdaRetorno(Constantes.FALSE_VALUE);
			
			if(chkPagoMixto.isChecked()){
				if(dblImporteEfectivo.getValue()<=0.0 || dblImporteTarjeta.getValue()<=0.0)
					throw new ImporteMixtoNullException(ImporteMixtoNullException.IMPORTE_MIXTO_CERO);
			}
			
			ventaPasaje.setItinerario(detalleItinerario.getItinerario());
			ventaPasaje.setRuta(detalleItinerario.getRuta());
			ventaPasaje.setCliente(oCliente);
			ventaPasaje.setPasajero(oPasajero);
			FormaPago formaPago = (FormaPago)cmbFormaPago.getSelectedItem().getValue();
			ventaPasaje.setFormaPago(formaPago);
			ventaPasaje.setServicio(detalleItinerario.getItinerario().getServicio());
			TipoComprobante tipoComprobante = (TipoComprobante)cmbTipoComprobante.getSelectedItem().getValue();
			ventaPasaje.setTipoComprobante(tipoComprobante);
			TipoFormaPago tipoFormaPago = (TipoFormaPago)cmbTipoFormaPago.getSelectedItem().getValue();
			ventaPasaje.setTipoFormaPago(tipoFormaPago);
			/*	Si ha seleccionado algun tipo de Tarjeta de Credito	*/
			if(cmbTarjetaCredito.getSelectedItem()!=null && cmbTarjetaCredito.getSelectedItem().getValue() instanceof TarjetaCredito){
				TarjetaCredito tarjetaCredito = (TarjetaCredito)cmbTarjetaCredito.getSelectedItem().getValue();
				ventaPasaje.setTarjetaCredito(tarjetaCredito);
			}			
			ventaPasaje.setNumeroBoleto(txtNumeroBoleto.getText().equals("")?null:txtNumeroBoleto.getText());			
			/*	Realizar si no es Fecha Abierta	*/
			ventaPasaje.setTipoMovimiento(new TipoMovimiento(Constantes.ID_TIPMOV_CREDITO));
			ventaPasaje.setNumeroAsiento(Integer.valueOf(lblNroAsiento.getValue()));
			ItinerarioAgenciaPartida itinerarioAgenciaPartida = (ItinerarioAgenciaPartida)cmbPtoEmbarque.getSelectedItem().getValue();
			ventaPasaje.setAgenciaPartida(itinerarioAgenciaPartida.getAgencia());
			ventaPasaje.setFechaPartida(detalleItinerario.getFechaPartida());
			ventaPasaje.setHoraPartida(detalleItinerario.getHoraPartida());
			ItinerarioAgenciaLlegada itinerarioAgenciaLlegada = (ItinerarioAgenciaLlegada)cmbPtoDesembarque.getSelectedItem().getValue();
			ventaPasaje.setAgenciaLlegada(itinerarioAgenciaLlegada.getAgencia());
			ventaPasaje.setFechaLlegada(detalleItinerario.getFechaLlegada());
			ventaPasaje.setHoraLllegada(detalleItinerario.getHoraLlegada());
			PreferenciaAlimentaria preferenciaAlimentaria = (PreferenciaAlimentaria)cmbAlimentacion.getSelectedItem().getValue();
			ventaPasaje.setPreferenciaAlimentaria(preferenciaAlimentaria);
			ventaPasaje.setNumeroPiso(Integer.valueOf(detalleItinerario.getPiso()));
			
			ventaPasaje.setSecuencial(0);
			ventaPasaje.setTarifa(dblTarifa.getValue());
			ventaPasaje.setRecargo(dblRecargo.getValue());
			ventaPasaje.setDescuento(dblDescuento.getValue());
			ventaPasaje.setPenalidad(0.0);
			ventaPasaje.setAcuenta(0.0);
			ventaPasaje.setImportePagado(dblTarifa.getValue()+dblRecargo.getValue()-dblDescuento.getValue());
			ventaPasaje.setImportePagadoEfectivo(dblImporteEfectivo.getValue());
			ventaPasaje.setImportePagadoTarjeta(dblImporteTarjeta.getValue());
			ventaPasaje.setTipoTransaccion(Constantes.TIPO_OPERACION_VENTA);
			ventaPasaje.setFechaCaducidad(new Date(new Date().getTime()+(Constantes.TIEMPO_CADUCA_BOLETO * Constantes.MILISEGUNDOS_X_DIA)));
			ventaPasaje.setAgencia(agenciaRemota);
			ventaPasaje.setUsuario((Usuario)cmbUsuarioRemoto.getSelectedItem().getValue());
			ventaPasaje.setCanalVenta(usuarioHardwareRemoto.getCanalVenta());
			ventaPasaje.setFechaLiquidacion(fechaLiquidacionRemota);
			ventaPasaje.setUsuarioHardware(usuarioHardwareRemoto);
			ventaPasaje.setNumeroOperacionBancaria(txtOperacionBancaria.getText().equals("")?null:txtOperacionBancaria.getText());
			ventaPasaje.setEstadoRegistro(Constantes.VALUE_ACTIVO);
			ventaPasaje.setLiquidacion(null);
			ventaPasaje.setPromocion(txtIdPromocion.getText().trim().isEmpty()?null:new Promocion(Long.valueOf(txtIdPromocion.getText().trim())));
			ventaPasaje.setObservaciones(txtObservacionesIda.getText().trim().isEmpty()?null:txtObservacionesIda.getText().trim().toUpperCase());
			ventaPasaje.setUsuarioRemoto(getUsuario());
			ventaPasaje.setUsuarioHardwareRemoto(getUsuarioHardware());
			ventaPasaje.setEsRemoto(true);
			
			if(agenciaRemota.getTipoAgencia().getId().intValue()==Constantes.ID_TIPAGE_CORPORATIVO 
					&& cmbCentroCosto.getItems().size()>0 
					&& cmbCentroCosto.getSelectedItem().getValue() instanceof CentroCosto)
				ventaPasaje.setCentroCosto((CentroCosto)cmbCentroCosto.getSelectedItem().getValue());
			
			/*	Obteniendo el RUC del concesionario en caso no sea una agencia TEPSA	*/
			if(agencia.getTipoAgencia().getId().intValue()!=Constantes.ID_TIPAGE_TEPSA){
				Agencia agencia = ServiceLocator.getAgenciaManager().buscarPorId(Long.valueOf(this.agencia.getId()));
				ventaPasaje.setRucClienteCredito(agencia.getConcesionario().getRuc());
			}
			
			/*	Numero de Control inicial	*/
			ventaPasaje.setNumeroControl("T00000");
			UtilData.auditarRegistro(ventaPasaje, false, usuario, Executions.getCurrent());
			/*	Clonamos la venta de IDA	*/
			ventaIDA = (VentaPasaje)ventaPasaje.clone();
			
			/*	Validando que el monto en efectivo + el monto en tarjeta sumen el importe pagado	*/
			if(chkPagoMixto.isChecked()){
				if(dblImporte.getValue().doubleValue()!=(dblImporteEfectivo.getValue().doubleValue()+dblImporteTarjeta.getValue().doubleValue()))
					throw new ImporteMixtoNullException(ImporteMixtoNullException.IMPORTE_MIXTO_NOT_EQUALS);
				if(((TipoFormaPago)cmbTipoFormaPago.getSelectedItem().getValue()).getId().intValue()!=Constantes.ID_TIPFORPAG_TARJETA)
						throw new ImporteMixtoNullException(ImporteMixtoNullException.IMPORTE_MIXTO_NOT_SELECT_CARD);
			}
			
			guardarVenta(ventaIDA);
		}catch (ItinerarioException i){
			if(i.getTipo().intValue()==ItinerarioException.NO_SELECT)
				DlgMessage.information(Messages.getString("WndVentaReserva.information.noAsientoSeleccionado"));
			else if(i.getTipo().intValue()==ItinerarioException.AGENCIA_PARTIDA_NULL){
				tabInformacionVentaIda.setSelected(true);
				DlgMessage.information(Messages.getString("WndVentaReserva.information.noAgenciaPartidaSeleccionada"), cmbPtoEmbarque);
			}else if (i.getTipo().intValue()==ItinerarioException.AGENCIA_LLEGADA_NULL){
				tabInformacionVentaIda.setSelected(true);
				DlgMessage.information(Messages.getString("WndVentaReserva.information.noAgenciallegadaSeleccionada"), cmbPtoDesembarque);
			}
		}catch(PreferenciaAlimentariaException panex){
			if(panex.getTipo().intValue()==PreferenciaAlimentariaException.ALIMENTACION_IDA_NULL)
				DlgMessage.information(Messages.getString("WndVentaReserva.information.noPreferenciaAlimentaria"), cmbAlimentacion);
		}catch(NumeroBoletoNullException nbnex){
			DlgMessage.information(Messages.getString("WndVentaReserva.information.noNumeroBoleto"));			
		}catch(PasajeroException pnex){
			tabPasajero.setSelected(true);
			DlgMessage.information(Messages.getString("WndVentaReserva.information.noPasajero"), txtDocumentoPax);
		}catch(TipoComprobanteNullException tcnex){
			tabPagos.setSelected(true);
			DlgMessage.information(Messages.getString("WndVentaReserva.information.noTipoComprobante"), cmbTipoComprobante);
		}catch(FormaPagoNullException fpnex){
			tabPagos.setSelected(true);
			DlgMessage.information(Messages.getString("WndVentaReserva.information.noFormaPago"), cmbFormaPago);
		}catch(TipoFormaPagoNullException tfpnex){
			tabPagos.setSelected(true);
			DlgMessage.information(Messages.getString("WndVentaReserva.information.noTipoFormaPago"), cmbTipoFormaPago);
		}catch(OperadorTarjetaCreditoNullException otcnex){
			tabPagos.setSelected(true);
			DlgMessage.information(Messages.getString("WndVentaReserva.information.noOperadorTarjetaCredito"), cmbOperadorTarjetaCredito);
		}catch(TarjetaCreditoNullException tcnex){
			tabPagos.setSelected(true);
			DlgMessage.information(Messages.getString("WndVentaReserva.information.noTarjetaCredito"), cmbTarjetaCredito);
		}catch(NumeroOperacionBancariaNullException nobnex){
			tabPagos.setSelected(true);
			DlgMessage.information(Messages.getString("WndVentaReserva.information.noNumeroOperacionBancaria"), txtOperacionBancaria);
		}catch(PasajeroIndeseableException piex){
			DlgMessage.information(Messages.getString("WndVentaReserva.information.pasajeroIndeseable"));
		}catch(OpenDateOnCreditNotAllowedException odcaex){
			DlgMessage.information(Messages.getString("WndVentaReserva.information.openDateOnCreditNotAllowed"));
		}catch(ImporteMixtoNullException imnex){
			if(imnex.getTipoError().intValue()==ImporteMixtoNullException.IMPORTE_MIXTO_CERO)
				DlgMessage.information(Messages.getString("WndVentaReserva.information.importeMixtoCero"), dblImporteEfectivo);
			else if(imnex.getTipoError().intValue()==ImporteMixtoNullException.IMPORTE_MIXTO_NOT_EQUALS)
				DlgMessage.information(Messages.getString("WndVentaReserva.information.importeMixtoNotEquals"), dblImporteEfectivo);
			else if(imnex.getTipoError().intValue()==ImporteMixtoNullException.IMPORTE_MIXTO_NOT_SELECT_EFECTIVO)
				DlgMessage.information(Messages.getString("WndVentaReserva.information.importeMixtoNoSeleccionoEfectivo"));
			else
				DlgMessage.information(Messages.getString("WndVentaReserva.information.importeMixtoNoSeleccionoTarjeta"));
		}catch (PasajeroNoSavedExeption pnsex){
			DlgMessage.information(Messages.getString("WndVentaReserva.information.noSavedPax"));
			tabPasajero.setSelected(true);
		}catch(LiquidacionNullException lnex){
			DlgMessage.information(Messages.getString("WndVentaReserva.information.noTieneLiquidacionAbierta"));
		}catch(VentaReservaException vrex){
			if(vrex.getTipo()==VentaReservaException.TARIFA_IDA_CERO)
				DlgMessage.information(Messages.getString("WndVentaReserva.information.noTieneTarifaIda"));
			else if(vrex.getTipo()==VentaReservaException.TARIFA_RETORNO_CERO)
				DlgMessage.information(Messages.getString("WndVentaReserva.information.noTieneTarifaRetorno"));
		}catch(Exception ex){
			DlgMessage.error(this.getClass().getName()+" "+ex.getMessage());
			ex.printStackTrace();
		}
		return ventaPasaje;
	}
	
	private void guardarVenta(VentaPasaje venta){
		ventaPasaje = venta;
		Messagebox.show(Messages.getString("WndVentaReserva.information.confirmacionGuardarVenta"), DlgMessage.NOMBREAPLICACION, DlgMessage.BTN_YESNO, Messagebox.QUESTION, new EventListener<Event>() {
			@Override
			public void onEvent(Event e){
				try{
					if(e.getName().equals("onYes")){
						int result = ServiceLocator.getVentaPasajesManager().guardarVenta(ventaPasaje, false, true, true,true);
						
						/*Resta el saldo de LC del Cliente*/
						if(((FormaPago)cmbFormaPago.getSelectedItem().getValue()).getId().equals(Constantes.ID_FORPAG_CREDITO))
							ServiceLocator.getLineaCreditoClienteManager().restarSaldo(lineaCreditoCliente.getSaldo(), dblImporte.getValue(), lineaCreditoCliente.getId());
															
						if(result == Constantes.CORRECT){
							DlgMessage.information(Messages.getString("WndVentaReserva.information.exitoGuardarVenta")+ventaPasaje.getNumeroControl());
							onCleanControlsPax();
							
							if(((TipoAgencia)cmbTipoAgenciaRemota.getSelectedItem().getValue()).getId().intValue()!=Constantes.ID_TIPAGE_CORPORATIVO){
								onCleanControlsClient();								
							}else if(((TipoAgencia)cmbTipoAgenciaRemota.getSelectedItem().getValue()).getId().intValue()==Constantes.ID_TIPAGE_CORPORATIVO){
								if(cmbCentroCosto.getItems().size()>0)
									cmbCentroCosto.setSelectedIndex(0);
							}
							onCleanPagos();
							onCleanPartialListAsientosSeleccionados();
							onCleanInformacionVenta();
							tabPasajero.setSelected(true);
						}
					}
				}catch(CapacityExceedsException ceex){
					DlgMessage.information(Messages.getString("WndVentaReserva.information.changeCapacidad"));
				}catch(DuplicateSeatException dsex){
					DlgMessage.information(Messages.getString("WndVentaReserva.information.asientoVendido"));
				}catch(NumeroBoletoDuplicadoException nbdex){
					DlgMessage.information(Messages.getString("WndVentaReserva.information.numeroBoletoVendido"));
					imgRefreshBoleto.setVisible(true);
				}catch(TiempoExpiracionBloqueoException tebex){
					DlgMessage.information(Messages.getString("WndVentaReserva.information.expiroTiempoBloqueoAsiento"));
					tiempoExpiracionBloqueo();
				}catch(Exception ex){
					DlgMessage.error(this.getClass().getName()+" "+ex.getMessage());
					ex.printStackTrace();
				}
			}
		});
	}
	
	private void tiempoExpiracionBloqueo(){
		if(lbxAsientos.getSelectedIndex()>=0)
			lbxAsientos.removeItemAt(lbxAsientos.getSelectedIndex());
		
		onCleanControlsPax();
		if(agenciaRemota.getTipoAgencia().getId().intValue()!=Constantes.ID_TIPAGE_CORPORATIVO)
			onCleanControlsClient();
		else{
			if(cmbCentroCosto.getItems().size()>0)
				cmbCentroCosto.setSelectedIndex(0);
		}
		onCleanPagos();
//		onCleanPartialListAsientosSeleccionados();
		onCleanInformacionVenta();
		tabPasajero.setSelected(true);
	}

	
	/**
	 * Limpiando el contenido de los controles relacionados a los pagos
	 */
	private void onCleanPagos(){
		cmbTipoComprobante.setSelectedIndex(0);
		cmbFormaPago.setSelectedIndex(0);
		cmbTipoFormaPago.getItems().clear();
		cmbTipoFormaPago.setText("");
		cmbTipoFormaPago.setDisabled(true);
		cmbOperadorTarjetaCredito.getItems().clear();
		cmbOperadorTarjetaCredito.setText("");
		cmbOperadorTarjetaCredito.setDisabled(true);
		cmbTarjetaCredito.getItems().clear();
		cmbTarjetaCredito.setText("");
		cmbTarjetaCredito.setDisabled(true);
		dblTarifa.setValue(null);
		dblRecargo.setValue(null);
		dblDescuento.setValue(null);
		dblImporte.setValue(null);
		txtOperacionBancaria.setText("");
		promocionAplicada=null;
		onSelectDefaultTipoComprobante();
		onSelectDefaultFormaPago();
		lblPromocion.setValue("");
		txtIdPromocion.setText("");
		imgQuitarPromocion.setVisible(false);
	}
	
	/**
	 * Limpiando el contenido de los controles que muestran informacion de la venta
	 */
	private void onCleanInformacionVenta(){
		lblOrigen.setValue("");
		lblDestino.setValue("");
		cmbPtoEmbarque.getItems().clear();
		cmbPtoEmbarque.setText("");
		cmbPtoDesembarque.getItems().clear();
		cmbPtoDesembarque.setText("");
		lblFechaPartida.setValue("");
		lblFechaLlegada.setValue("");
		lblHoraPartida.setValue("");
		lblHoraLlegada.setValue("");
		lblNroAsiento.setValue("");
		lblTarifa.setValue("");
		txtNumeroBoleto.setText("");
		cmbAlimentacion.setSelectedIndex(0);
		detailItinerary=null;
		ventaPasaje = null;
		lbxAsientos.setSelectedIndex(-1);
		txtObservacionesIda.setText("");
		ventaIDA = null;
	}
	
	/**
	 * Limpiando controles
	 */
	private void onCleanPartialListAsientosSeleccionados(){
		if(lbxAsientos.getSelectedIndex()>=0)
			lbxAsientos.removeItemAt(lbxAsientos.getSelectedIndex());
		
		/*	Validando la cantidad de asientos seleccionados para reiniciar todos los objetos	*/
		if(lbxAsientos.getItems().size() == 0){
			selectedTabItinerario();
		}
	}
	
	/**
	 * Limpia los datos del itinerarios seleccionado
	 */
	private void onCleanDatosItinerario(){
		/*	IDA		*/
		lbxItinerariosIda.clearSelection();
		detalleItinerarioIda = null;
		mapaAsientosIda = null;
	}
	
	/**
	 * Permite liberar los asientos cuando se cambia de pestaña dentro de la venta
	 */
	public void liberarAsientos(){
		try{
			ServiceLocator.getTmpOcupacionAsientosManager().desbloquearAsientoByUsuarioHardware(usuhar);
			lbxAsientos.getItems().clear();
		}catch(Exception ex){
			DlgMessage.error(this.getClass().getSimpleName()+" "+ex.getMessage());
		}
	}
	
	/**
	 * Utilizado cuando el usuario hace click en el tab de Itinerarios, efecto de regresar
	 */
	public void selectedTabItinerario(){
		if(!tabAsientos.isDisabled() || !tabVenta.isDisabled()){
			selectedTabAsientos();
			tabAsientos.setDisabled(true);
			liberarAsientos();
			onCleanDatosItinerario();
			btnNextTabAsientos.setDisabled(true);
			btnNextTabVenta.setDisabled(true);
			
			if(grdOcupabilidadIda.getRows().getChildren().size()>0)
				grdOcupabilidadIda.getRows().getChildren().get(0).detach();
			
			cmbTipoAgenciaRemota.setSelectedIndex(0);
			onChangeTipoAgenciaRemota();
			tabItinerario.setSelected(true);
			tabPasajero.setSelected(true);
		}
	}
	
	/**
	 * Utilizado cuando el usuario hace click en el tab de Asientos, efecto de regresar
	 */
	public void selectedTabAsientos(){
		tabVenta.setDisabled(true);
		onCleanInformacionVenta();
		onCleanControlsPax();
		if(agencia.getTipoAgencia().getId().intValue()!=Constantes.ID_TIPAGE_CORPORATIVO)
			onCleanControlsClient();
		onCleanPagos();
		tabPasajero.setSelected(true);
	}
	
	/**
	 * habilita los controles de la nacionalidad.
	 */
	public void habilitarNacionalidad(){
		TipoDocumento tipoDocumento = cmbTipoDocumento.getSelectedItem().getValue();
		if(tipoDocumento instanceof TipoDocumento && tipoDocumento.getId().intValue()!=Constantes.ID_TIPDOC_DNI){
			rowNacionalidad.setVisible(true);
			lblEmail.setValue("EMAIL :");
			txtDocumentoPax.setMaxlength(20);
		}else{
			rowNacionalidad.setVisible(false);
			lblEmail.setValue("EMAIL :");
			txtDocumentoPax.setMaxlength(8);
		}
	}
	
	/**
	 * Habilita los controles para el pago mixto.
	 */
	public void habilitarPagoMixto(){
		boolean arg = false;
		if(chkPagoMixto.isChecked())
			arg = true;
		
		lblImporteEfectivo.setVisible(arg);
		lblImporteTarjeta.setVisible(arg);
		dblImporteEfectivo.setVisible(arg);
		dblImporteTarjeta.setVisible(arg);
		dblImporteEfectivo.setValue(0.0);
		dblImporteTarjeta.setValue(0.0);
		dblImporteEfectivo.setFocus(true);
	}
	
	/**
	 * Realiza el llenado del combobox con las formas de pago.
	 * @param combobox	: Objeto al cual se le cargaran los datos.
	 * @param todos		: Indica el primer elemente del objeto combobox.
	 * @throws Exception
	 */
	public static void cargarFormaPago(Combobox combobox, boolean todos) throws Exception {
		ArrayList<FormaPago> lstFormaPago = ServiceLocator.getFormaPagoManager().buscarPorEstadoRegistro(Constantes.VALUE_ACTIVO, "denominacion");
		UtilData.cargarGenericData(combobox, todos);	
		for (FormaPago formaPago: lstFormaPago) {
			if (!(formaPago.getId().equals(Constantes.ID_FORPAG_CORTESIA))){
				Comboitem oComboitem = new Comboitem();	
				oComboitem.setValue(formaPago);
				oComboitem.setLabel(formaPago.getDenominacion());	
				combobox.appendChild(oComboitem);
			}
		}
	}
	
	/**
	 * Obtiene las promociones vigentes de la base de datos y las muestras en pantalla.
	 */
	private void imgPromocion_loadPromociones(){
		try{
			boolean paxfre = false;
			if(oPasajero==null){
				DlgMessage.information(Messages.getString("WndVentaReserva.information.noPasajeroPromocion"), txtDocumentoPax);
				tabPasajero.setSelected(true);
				return;
			}
				
			if(oPasajero.isPaxFree())
				paxfre=true;
			String idCliente = null;
			if(oCliente!=null)
				idCliente = oCliente.getId().toString();
			
			AplicarPromocion aplicarPromocion = createObjectAplicarPromocion(true);
			Window win = aplicarPromocion.loadPromociones(paxfre, idCliente, lblFechaPartida.getValue());
			/*	Validando que el Objeto Window sea distinto de null	*/
			if(win!=null){
				this.appendChild(win);
				win.doModal();
			}
		}catch(Exception ex){
			DlgMessage.information(this.getClass().getSimpleName()+" "+ex.getMessage());
		}
	}
	
	private AplicarPromocion createObjectAplicarPromocion(boolean esIda){
		AplicarPromocion aplicarPromocion = null;
		DetalleItinerario detalle = null;
		
		Integer idCanalVenta = ((EspecieValorada)lbxUsuarioHardware.getSelectedItem().getValue()).getUsuarioHardware().getCanalVenta().getId();
		Integer idAgencia = agenciaRemota.getId();
		
		if(esIda){
			detalle = (DetalleItinerario)lbxAsientos.getSelectedItem().getValue();
			
			aplicarPromocion = new AplicarPromocion(detalle.getRuta().getId(), detalle.getItinerario().getServicio().getId(), 
					idAgencia, idCanalVenta, null, null, lblNroAsiento.getValue().equals("")?"*":lblNroAsiento.getValue(), 
					oCliente==null?null:oCliente.getId(), false, ((FormaPago)cmbFormaPago.getSelectedItem().getValue()).getId(), 
					((TipoFormaPago)cmbTipoFormaPago.getSelectedItem().getValue()).getId(), 
					(cmbTarjetaCredito.getSelectedItem()==null?null:((TarjetaCredito)cmbTarjetaCredito.getSelectedItem().getValue()).getId()), 
					(lblFechaPartida.getValue().equals("")?null:Util.StringtoDate(lblFechaPartida.getValue(), Constantes.DATE_FORMAT)), 
					(oPasajero==null?false:oPasajero.isPaxFree()), dblTarifa, dblDescuento, dblImporte, dblRecargo, lblPromocion, imgQuitarPromocion, txtIdPromocion,lblFechaPartida.getValue());			
		}
		return aplicarPromocion;
	}
	
	private void quitarPromocion(){
		lblPromocion.setValue("");
		dblDescuento.setTooltiptext("");
		dblDescuento.setValue(0.0);
		dblImporte.setValue(dblTarifa.getValue()+dblRecargo.getValue()-dblDescuento.getValue());
		txtIdPromocion.setText("");
		imgQuitarPromocion.setVisible(false);		
	}
	
	private void convertirPaxfree(){
		Messagebox.show(Messages.getString("WndVentaReserva.question.fidelizarPasajeroFrecuente"), DlgMessage.NOMBREAPLICACION, DlgMessage.BTN_YESNO, Messagebox.QUESTION, new EventListener<Event>() {
			@Override
			public void onEvent(Event e){
				if(e.getName().equals("onYes")){
					try{
						PasajeroFrecuente paxfree = ServiceLocator.getPasajeroFrecuenteManager().buscarPaxFree(oPasajero.getId());
						/*	Obteniendo la fecha y hora del servidor	*/
						String dateTimeSystem = ServiceLocator.getVentaPasajesManager().getDateSystem();
						/*	Obteniendo la fecha de caducidad	*/
						String fechaCaducidad = Util.getFechaCaducidadPaxFre(dateTimeSystem);
						PasajeroFrecuente pasajeroFrecuente = null;
						HistoricoMembresia historicoMembresia = null;
						if(paxfree == null){
							pasajeroFrecuente = new PasajeroFrecuente();
							pasajeroFrecuente.setPasajero(oPasajero);
							pasajeroFrecuente.setPuntosAcumulados(0);
							pasajeroFrecuente.setPuntosUtilizados(0);
							pasajeroFrecuente.setNumeroTarjeta(getAgencia().getId()+"-"+getUsuario().getId()+"-"+Util.getGenerarAleatorio(9999));
							pasajeroFrecuente.setFechaIngreso(Constantes.FORMAT_DATE_TIME_24H.parse(dateTimeSystem));
							pasajeroFrecuente.setFechaActivacion(Constantes.FORMAT_DATE_TIME_24H.parse(dateTimeSystem));
							pasajeroFrecuente.setFechaCaducidad(Constantes.FORMAT_DATE_TIME_24H.parse((fechaCaducidad)));
							pasajeroFrecuente.setAgencia(getAgencia());
							pasajeroFrecuente.setEstado(Constantes.TRUE_VALUE);
							pasajeroFrecuente.setEstadoRegistro(Constantes.VALUE_ACTIVO);
							ServiceLocator.getPasajeroFrecuenteManager().guardar(pasajeroFrecuente);
						}else if(paxfree.getEstado().intValue()==Constantes.FALSE_VALUE){//PAXFRE Inactivo
							historicoMembresia = new HistoricoMembresia();
							historicoMembresia.setPasajeroFrecuente(paxfree);
							historicoMembresia.setFechaActivacionAnterior(paxfree.getFechaActivacion());
							historicoMembresia.setFechaCaducidadAnterior(paxfree.getFechaCaducidad());
							historicoMembresia.setFechaSuspensionAnterior(paxfree.getFechaSuspension());
							historicoMembresia.setNuevaFechaActivacion(Constantes.FORMAT_DATE_TIME_24H.parse(dateTimeSystem));
							historicoMembresia.setNuevaFechaCaducidad(Constantes.FORMAT_DATE_TIME_24H.parse((fechaCaducidad)));
							historicoMembresia.setEstadoRegistro(Constantes.VALUE_ACTIVO);
							UtilData.auditarRegistro(historicoMembresia, getUsuario(), Executions.getCurrent());
							ServiceLocator.getHistoricoMembresiaManager().guardar(historicoMembresia);
							/*	Actualizando al pasajero frecuente	*/				
							pasajeroFrecuente = paxfree;
							pasajeroFrecuente.setFechaActivacion(Constantes.FORMAT_DATE_TIME_24H.parse(dateTimeSystem));
							pasajeroFrecuente.setFechaCaducidad(Constantes.FORMAT_DATE_TIME_24H.parse((fechaCaducidad)));
							pasajeroFrecuente.setFechaSuspension(null);
							pasajeroFrecuente.setEstado(Constantes.TRUE_VALUE);
							UtilData.auditarRegistro(paxfree, true, getUsuario(),Executions.getCurrent());
							pasajeroFrecuente.setHistoricoMembresia(historicoMembresia);
							ServiceLocator.getPasajeroFrecuenteManager().actualizar(pasajeroFrecuente);
						}
						DlgMessage.information(Messages.getString("WndVentaReserva.information.fidelizacionRealizada"));
						mantenimientoRegistroPax(oPasajero.getId());
						imgFidelizarPasajero.setVisible(false);
					}catch(Exception ex){
						ex.printStackTrace();
						DlgMessage.error(Messages.getString("WndVentaReserva.information.fidelizacionNoRealizada"));
					}
				}
			}
		});
	}
	
	private void loadTipoAgenciaRemota(){
		try{
			UtilData.cargarDataCombo(cmbTipoAgenciaRemota, TipoAgencia.class, false);
			
			for(int i =0; i<cmbTipoAgenciaRemota.getItems().size(); i++){
				Comboitem comboitem = cmbTipoAgenciaRemota.getItems().get(i);
				if(comboitem.getValue() instanceof TipoAgencia && ((TipoAgencia)comboitem.getValue()).getId().intValue()==Constantes.ID_TIPAGE_TEPSA){
					cmbTipoAgenciaRemota.removeItemAt(i);
				}
			}
			cmbTipoAgenciaRemota.setSelectedIndex(0);
			String[] buffer = Constantes.USUARIO_REMOTO.split(",");
			for(int i=0; i<buffer.length; i++){
				if(buffer[i].equals(getUsuario().getId().toString()))
					cmbTipoAgenciaRemota.setDisabled(false);
			}
		}catch(Exception ex){
			DlgMessage.information(this.getClass().getSimpleName()+" "+ex.getMessage());
		}
	}
	
	private void onChangeTipoAgenciaRemota(){
		if(cmbTipoAgenciaRemota.getSelectedItem().getValue() instanceof TipoAgencia){
			loadAgenciasRemotas();
			cmbAgenciaRemota.setDisabled(false);
		}else{
			cmbAgenciaRemota.getItems().clear();
			cmbAgenciaRemota.setDisabled(true);
			cmbAgenciaRemota.setText("");
			cmbUsuarioRemoto.getItems().clear();
			cmbUsuarioRemoto.setDisabled(true);
			cmbUsuarioRemoto.setText("");
			bndbxUsuarioHardware.setDisabled(true);
			bndbxUsuarioHardware.setText("");
			lbxUsuarioHardware.getItems().clear();
		}		
	}
	
	private void loadAgenciasRemotas(){
		try{
			TreeMap<String, Object> criteriosBusqueda = new TreeMap<String, Object>();
			criteriosBusqueda.put("tipoAgencia.id", ((TipoAgencia)cmbTipoAgenciaRemota.getSelectedItem().getValue()).getId());
			criteriosBusqueda.put("estadoRegistro", Constantes.VALUE_ACTIVO);
			List<String> criteriosOrdenar = new ArrayList<String>();
			criteriosOrdenar.add("denominacion");
			UtilData.cargarAgencia(cmbAgenciaRemota, false, criteriosBusqueda, criteriosOrdenar);
		}catch(Exception ex){
			DlgMessage.information(this.getClass().getSimpleName()+" "+ex.getMessage());
		}
	}
	
	private void onChangeAgenciaRemota(){
		if(cmbAgenciaRemota.getSelectedItem().getValue() instanceof Agencia){
			loadUsuarioRemoto();
		}else{
			cmbUsuarioRemoto.getItems().clear();
			cmbUsuarioRemoto.setDisabled(true);
			cmbUsuarioRemoto.setText("");
			bndbxUsuarioHardware.setDisabled(true);
			bndbxUsuarioHardware.setText("");
			lbxUsuarioHardware.getItems().clear();
		}		
	}
	
	private void onChangeUsuarioRemoto(){
		if(cmbUsuarioRemoto.getSelectedItem().getValue() instanceof Usuario)
			loadUsuarioHardware();
		else{
			bndbxUsuarioHardware.setDisabled(true);
			bndbxUsuarioHardware.setText("");
			lbxUsuarioHardware.getItems().clear();
		}			
	}
	
	private void loadUsuarioRemoto(){
		try{
			cmbUsuarioRemoto.setDisabled(false);
			Agencia agencia = (Agencia)cmbAgenciaRemota.getSelectedItem().getValue();
			TreeMap<String, Object> parametros = new TreeMap<String, Object>();
			parametros.put("agencia.id", agencia.getId());
			parametros.put("estadoRegistro", Constantes.VALUE_ACTIVO);
			UtilData.cargarDataCombo(cmbUsuarioRemoto, Usuario.class, parametros, false);
		}catch(Exception ex){
			DlgMessage.information(this.getClass().getSimpleName()+" "+ex.getMessage());
		}
	}
	
	private void loadUsuarioHardware(){
		try{
			lbxUsuarioHardware.getItems().clear();
			if(cmbAgenciaRemota.getSelectedItem().getValue() instanceof Agencia){
				Usuario usuario = (Usuario)cmbUsuarioRemoto.getSelectedItem().getValue();
				Agencia agencia = (Agencia)cmbAgenciaRemota.getSelectedItem().getValue();
				TreeMap<String, Object> criteriosBusqueda = new TreeMap<String, Object>();
				criteriosBusqueda.put("agencia.id", agencia.getId());
				criteriosBusqueda.put("estadoRegistro", Constantes.VALUE_ACTIVO);
				List<String> criteriosOrdenar = new ArrayList<String>();
				criteriosOrdenar.add("serie");
				List<EspecieValorada> lstEspecies = ServiceLocator.getEspecieValoradaManager().buscarPorX(criteriosBusqueda, criteriosOrdenar);
				for(EspecieValorada especieValorada : lstEspecies){
					Listitem listitem = new Listitem();
					UsuarioHardware usuarioHardware = ServiceLocator.getUsuarioHardwareManager().buscarPorId(usuario.getUsuarioHardware().getId().longValue());
					especieValorada.setUsuarioHardware(usuarioHardware);
					Listcell listcell = new Listcell(especieValorada.getUsuarioHardware().getDescripcion());
					listitem.appendChild(listcell);
					String serie = "000"+especieValorada.getSerie();
					listcell = new Listcell(serie.substring(serie.length()-3));
					listitem.appendChild(listcell);
					listcell = new Listcell(especieValorada.getCorrelativoActual().toString());
					listitem.appendChild(listcell);
					listitem.setValue(especieValorada);
					lbxUsuarioHardware.appendChild(listitem);
				}
				bndbxUsuarioHardware.setText(Constantes.COMBO_LABEL_SELECCIONE);
				bndbxUsuarioHardware.setDisabled(false);
			}
		}catch(Exception ex){
			DlgMessage.information(this.getClass().getSimpleName()+" "+ex.getMessage());
		}
	}
	
	private void onSelectUsuarioHardware(){
		try{
			if(lbxUsuarioHardware.getSelectedItem().getValue() instanceof EspecieValorada){
				EspecieValorada especieValorada = (EspecieValorada)lbxUsuarioHardware.getSelectedItem().getValue();
				bndbxUsuarioHardware.setText(especieValorada.getUsuarioHardware().getDescripcion()+" - "+especieValorada.getSerie());
				bndbxUsuarioHardware.close();
			}
		}catch(Exception ex){
			DlgMessage.information(this.getClass().getSimpleName()+" "+ex.getMessage());
		}
	}
	
	private void mostrarFechaNacimiento(String fechaNacimiento){
		if(fechaNacimiento != null){
			String dia = fechaNacimiento.substring(0, 2);
			String mes = fechaNacimiento.substring(3, 5);
			String anio = fechaNacimiento.substring(6);
			for(int i=0; i<cmbAnio.getItems().size(); i++){
				if(cmbAnio.getItems().get(i).getValue().toString().equals(anio))
					cmbAnio.setSelectedIndex(i);
			}
			
			if(cmbAnio.getSelectedIndex()>=0){
				for(int i=0; i<cmbMes.getItems().size(); i++){
					if(((Integer)cmbMes.getItems().get(i).getValue()).intValue()==Integer.valueOf(mes)){
						cmbMes.setSelectedIndex(i);
						Util.loadDias(cmbDia, (Integer)cmbMes.getSelectedItem().getValue(), (Integer)cmbAnio.getSelectedItem().getValue());
					}
				}
				
				for(int i=0; i<cmbDia.getItems().size(); i++){
					if(((Integer)cmbDia.getItems().get(i).getValue()).intValue() == Integer.valueOf(dia))
						cmbDia.setSelectedIndex(i);
				}
			}
		}else{
			cmbAnio.setSelectedIndex(-1);
			cmbAnio.setText("");
			cmbMes.setSelectedIndex(-1);
			cmbMes.setText("");
			cmbDia.setSelectedIndex(-1);
			cmbDia.setText("");
		}
	}
	
	private String generarFechaNacimiento(){
		String fechaNacimiento = null;
		if(cmbAnio.getSelectedIndex()>=0 && cmbMes.getSelectedIndex()>=0 && cmbDia.getSelectedIndex()>=0){
			String dia = "00"+ cmbDia.getText();
			String mes = "00" + cmbMes.getSelectedItem().getValue();
			fechaNacimiento = dia.substring(dia.length()-2) + "/" + mes.substring(mes.length()-2) + "/" + cmbAnio.getText();
		}
		return fechaNacimiento;
	}
	
	private void loadCentroCosto(){
		try{
			cmbCentroCosto.getItems().clear();
			TreeMap<String, Object> criteriosBusqueda = new TreeMap<String, Object>();
			criteriosBusqueda.put("concesionario.id", agenciaRemota.getConcesionario().getId());
			criteriosBusqueda.put("estadoRegistro", Constantes.VALUE_ACTIVO);
			List<String> criteriosOrdenar = new ArrayList<String>();
			criteriosOrdenar.add("denominacion");
			List<CentroCosto> lstCentroCosto = ServiceLocator.getCentroCostoManager().buscarPorX(criteriosBusqueda, criteriosOrdenar);
			cmbCentroCosto.getItems().clear();
			Comboitem item = new Comboitem(Constantes.COMBO_LABEL_SELECCIONE);
			cmbCentroCosto.appendChild(item);
			cmbCentroCosto.setSelectedIndex(0);
			if(lstCentroCosto.size()>0){
				for(CentroCosto centroCosto : lstCentroCosto){
					item = new Comboitem(centroCosto.getDenominacion());
					item.setValue(centroCosto);
					cmbCentroCosto.appendChild(item);
				}
				lblCentroCosto.setVisible(true);
				cmbCentroCosto.setVisible(true);
			}else{
				lblCentroCosto.setVisible(false);
				cmbCentroCosto.setVisible(false);
			}
		}catch(Exception ex){
			ex.printStackTrace();
		}
	}
//	
//	private void exportTicket(VentaPasaje venta){
//		TreeMap<String, Object> criteriosBusqueda = new TreeMap<String, Object>();
//		criteriosBusqueda.put("estadoRegistro", Constantes.VALUE_ACTIVO);
//		List<String> criteriosOrdenar = new ArrayList<String>();
//		criteriosOrdenar.add("idioma");
//		criteriosOrdenar.add("orden");
//		List<TerminosVenta> lstTerminos = ServiceLocator.getTerminosVentaManager().buscarPorX(criteriosBusqueda, null);
//		
//		try{
//			Agencia agencia = ServiceLocator.getAgenciaManager().buscarPorId(venta.getAgencia().getId().longValue());
//			Session session = getDesktop().getSession();
//	        HttpSession httpSession = (HttpSession)session.getNativeSession();
//	        httpSession.setAttribute("venta", venta);
//	        httpSession.setAttribute("terminos", lstTerminos);
//	        httpSession.setAttribute("concesionario", agencia.getConcesionario().getRazonSocial());
//	        getDesktop().getExecution().sendRedirect("evoucher.zul", "_black");
////	        Session session = getDesktop().getSession();
////	        HttpSession httpSession = (HttpSession)session.getNativeSession();
//	        httpSession.setAttribute("venta", venta);
//	        httpSession.setAttribute("terminos", lstTerminos);
//	        httpSession.setAttribute("concesionario", agencia.getConcesionario().getRazonSocial());
//	        Executions.sendRedirect("/servlet/EticketServlet");
//		}catch(Exception ex){
//			ex.printStackTrace();
//		}
//    }
}
