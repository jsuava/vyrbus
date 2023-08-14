/**
 * Proyecto		: SISVYR
 * Sistema		: Sistema de Ventas y Reservas
 * Descripci�n	:
 * Autor		: Jos� Abanto
 * Fecha		: 26 mar. 2022
 * Hora			: 06:28:30
 */
package pe.itsb.vyrbus.view.ctrl;

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
import org.zkoss.zul.Doublebox;
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

import pe.itsb.vyrbus.model.bean.Agencia;
import pe.itsb.vyrbus.model.bean.VentaPasaje;
import pe.itsb.vyrbus.service.exceptions.LiquidacionNullException;
import pe.itsb.vyrbus.service.exceptions.ManifiestoImpresoException;
import pe.itsb.vyrbus.service.exceptions.ObservacionesNullException;
import pe.itsb.vyrbus.service.exceptions.PerdidaServicioException;
import pe.itsb.vyrbus.service.locator.ServiceLocator;
import pe.itsb.vyrbus.service.util.Constantes;
import pe.itsb.vyrbus.service.util.Messages;
import pe.itsb.vyrbus.service.util.Util;
import pe.itsb.vyrbus.service.util.UtilData;
import pe.itsb.vyrbus.view.ui.DlgMessage;
import pe.itsb.vyrbus.view.ui.WndBase;

/**
 * @author Jose
 *
 */
public class WndPerdidaServicio extends WndBase {
	private static final long serialVersionUID = 1L;
	private Textbox txtNumeroDocumento;
	private Textbox txtNumeroControl;
	private Textbox txtNumeroBoleto;
	private Textbox txtMotivo;
	private Listbox lbxVentas;
	private Tab tabListado;
	private Window wndPerdidaServicio;
	private Doublebox dblImporte;


	private VentaPasaje boletoPerdidaServicio = null;

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.view.ui.WndBase#onCreate()
	 */
	@Override
	public void onCreate() throws Exception {
		try{
			/*Valida si el usuario tiene una liquidaci�n aperturada*/
			if(getDesktop().getSession().getAttribute(Constantes.ATRIBUTO_FECHA_LIQUIDACION)==null)
				throw new LiquidacionNullException();

			super.onCreate();
//			fechaLiquidacion = (Date) this.getDesktop().getSession().getAttribute(Constantes.ATRIBUTO_FECHA_LIQUIDACION);
			txtNumeroBoleto.setFocus(true);

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
		// TODO Auto-generated method stub
		super.initComponents();
		txtNumeroDocumento = (Textbox)this.getFellow("txtNumeroDocumento");
		txtNumeroControl = (Textbox)this.getFellow("txtNumeroControl");
		txtNumeroBoleto = (Textbox)this.getFellow("txtNumeroBoleto");
		lbxVentas = (Listbox)this.getFellow("lbxVentas");
		tabListado = (Tab)this.getFellow("tabListado");
//		wndPerdidaServicio=(Window)this.getFellow("wndPerdidaServicio");
	}

	public void buscarVentas(){
		try{
			String numeroDocumento = txtNumeroDocumento.getText().trim().equals("")?null:txtNumeroDocumento.getText().trim().toUpperCase();
			String numeroControl = txtNumeroControl.getText().trim().equals("")?null:txtNumeroControl.getText().trim();
			if(numeroControl!=null){
				numeroControl = Util.generateControlNumber(txtNumeroControl.getText().trim().toUpperCase());
				txtNumeroControl.setText(numeroControl);
			}
			String numeroBoleto = txtNumeroBoleto.getText().trim().equals("")?null:txtNumeroBoleto.getText().trim().toUpperCase();
			if(numeroBoleto!=null) {
				numeroBoleto = Util.autocompleNumberBoleto(numeroBoleto);
				txtNumeroBoleto.setText(numeroBoleto);
			}

			if(numeroDocumento==null && numeroControl==null && numeroBoleto==null){
				DlgMessage.warning(Messages.getString("WndPerdidaServicio.warning.noExisteCriteriosBusqueda"));
				return;
			}

			List<VentaPasaje> lstReservas = ServiceLocator.getVentaPasajesManager().buscarBoletosPerdidaServicio(numeroDocumento, numeroControl, numeroBoleto);
			if(lstReservas.size()>0){
				lbxVentas.getItems().clear();
				Listitem item = null;
				Listcell cell = null;
				for(VentaPasaje ventaPasaje : lstReservas){
					item = new Listitem();
					cell = new Listcell(ventaPasaje.getTipoMovimiento().getDenominacion());
					item.appendChild(cell);
					cell = new Listcell(ventaPasaje.getNumeroControl());
					item.appendChild(cell);
					cell = new Listcell(ventaPasaje.getNumeroBoleto());
					item.appendChild(cell);
					cell = new Listcell(ventaPasaje.getPasajero().getNombresApellidos());
					item.appendChild(cell);
					cell = new Listcell(ventaPasaje.getPasajero().getNumeroDocumento());
					item.appendChild(cell);
					cell = new Listcell(ventaPasaje.getRuta().getOrigen()+" - "+ventaPasaje.getRuta().getDestino());
					item.appendChild(cell);
					cell = new Listcell(ventaPasaje.getFechaPartida()!=null?Util.DatetoString(ventaPasaje.getFechaPartida(), Constantes.DATE_FORMAT): "");
					item.appendChild(cell);
					cell = new Listcell(ventaPasaje.getHoraPartida()!=null? ventaPasaje.getHoraPartida(): "");
					item.appendChild(cell);
					cell = new Listcell(ventaPasaje.getNumeroAsiento()!=null? ventaPasaje.getNumeroAsiento().toString(): "");
					item.appendChild(cell);
					cell = new Listcell(Util.DatetoString(ventaPasaje.getFechaInsercion(), Constantes.DATE_TIME_FORMAT));
					item.appendChild(cell);
					cell = new Listcell();
					Button btnPerdidaServicio = new Button("Perdida Servicio","resources/mp_perdidaServicio.png");
					btnPerdidaServicio.setId(ventaPasaje.getId().toString());
					btnPerdidaServicio.setAutodisable("self");
					btnPerdidaServicio.setClass("btnCommandM");
					btnPerdidaServicio.setStyle("font-size:11px; cursor:pointer");
					btnPerdidaServicio.addEventListener(Events.ON_CLICK, new EventListener<Event>() {
						@Override
						public void onEvent(Event e){
							validatePerdidaServicio(e.getTarget().getId());
						}
					});
					cell.appendChild(btnPerdidaServicio);
					item.appendChild(cell);
					item.setValue(ventaPasaje);
					lbxVentas.appendChild(item);
				}
				tabListado.setSelected(true);
			}else
				DlgMessage.information(Messages.getString("WndPerdidaServicio.information.noVentas"));

		}catch(Exception ex){
			DlgMessage.error(this.getClass().getSimpleName()+" "+ex.getMessage());
		}
	}

	private void validatePerdidaServicio(String idVenta){
		validatePerdidaServicio(idVenta,true);
	}

	/**
	 * Realiza la validac�n para ver si al boleto se le puede aplicar el proceso de Perdida de Servicio.
	 * @param idVenta	: Identificador de la venta.
	 * @param createVentanaDevolucion	: Indica si debe crearse la Ventana para aplicar la p�rdida de servicio.
	 */
	private boolean validatePerdidaServicio(String idVenta, boolean createVentanaDevolucion){
		try{
			boletoPerdidaServicio = null;
			/*Busca el registro original*/
			VentaPasaje ventaPasaje = ServiceLocator.getVentaPasajesManager().buscarVentaById(Long.valueOf(idVenta));
			/*Busca por el ultimo movimiento agrupado por numero de control*/
			List<VentaPasaje> lstResult= ServiceLocator.getVentaPasajesManager().buscarBoletosPerdidaServicio(null, ventaPasaje.getNumeroControl(), null);
			/*Busca por el identificador del ultmio movimiento, el registro a validar*/
			final VentaPasaje ventaPerdidaServicio=ServiceLocator.getVentaPasajesManager().buscarVentaById(lstResult.get(0).getId());

			if(ventaPerdidaServicio.getTipoMovimiento().getId().intValue()==Constantes.ID_TIPMOV_ANULACION_SISTEMA)
				throw new PerdidaServicioException(Constantes.ID_TIPMOV_ANULACION_SISTEMA);
			else if (ventaPerdidaServicio.getTipoMovimiento().getId().intValue()==Constantes.ID_TIPMOV_ANULACION)
				throw new PerdidaServicioException(Constantes.ID_TIPMOV_ANULACION_SISTEMA);
			else if(ventaPerdidaServicio.getTipoMovimiento().getId().intValue()==Constantes.ID_TIPMOV_DEVOLUCION)
				throw new PerdidaServicioException(Constantes.ID_TIPMOV_DEVOLUCION);
			else if(ventaPerdidaServicio.getTipoTransaccion().equals(Constantes.TIPO_OPERACION_PERDIDA_SERVICIO))
				throw new PerdidaServicioException(Integer.valueOf(Constantes.TIPO_OPERACION_PERDIDA_SERVICIO));

			//Debe permitir colocarlo como perdida de servicio aunque este manifestado pues esta operacion se realizara
			//Despues de emitir el manifiesto de pasajeros  MAOE 11/08/2022
//			if (ServiceLocator.getDetalleManifiestoManager().validarVentaManifiesto(Long.valueOf(idVenta)))
//				throw new ManifiestoImpresoException();

			createVentanaPerdidaServicio(ventaPerdidaServicio);

			return true;
		}catch(PerdidaServicioException psex){
			if(psex.getTipo().intValue()==Constantes.ID_TIPMOV_ANULACION_SISTEMA)
				DlgMessage.information(Messages.getString("WndPerdidaServicio.information.noPerdidaServicioAnulacion"));
			else if(psex.getTipo().intValue()==Constantes.ID_TIPMOV_DEVOLUCION)
				DlgMessage.information(Messages.getString("WndPerdidaServicio.information.noPerdidaServicioByDevuelto"));
			else if(psex.getTipo().intValue()==Integer.valueOf(Constantes.TIPO_OPERACION_PERDIDA_SERVICIO))
				DlgMessage.information(Messages.getString("WndPerdidaServicio.information.noPerdidaServicio"));
			return false;
		}catch(ManifiestoImpresoException miex){
			DlgMessage.information(Messages.getString("Generales.information.manifiestoImpreso"));
			return false;
		}catch(Exception ex){
			DlgMessage.error(this.getClass().getSimpleName()+" "+ex.getMessage());
			ex.printStackTrace();
			return false;
		}
	}

	/**
	 * Realiza la creaci�n de la Ventana para marcar el boleto como perdida de Servicio.
	 * @param ventaOriginal	: Boleto que se desea marcar.
	 * @return
	 */
	@SuppressWarnings("deprecation")
	private void createVentanaPerdidaServicio(final VentaPasaje ventaOriginal)throws Exception{

		Caption caption = null;
		Groupbox groupbox = null;
		Grid grid = new Grid();
		Columns columns = new Columns();
		Column column = null;
		Rows rows = new Rows();
		Row row = null;
		Label label = null;
		Textbox text = null;

		final Window win = new Window("", "normal", true);
		win.setWidth("500px");

		caption = new Caption("PERDIDA DE SERVICIO", "resources/menu/menu_reimprimir.png");
		win.appendChild(caption);
		label = new Label("Se va a proceder a marcar el boleto como Perdida de Servicio :");
		label.setStyle("font-size:12px !important");
		win.appendChild(label);

		win.appendChild(new Separator("horizontal"));

		groupbox = new Groupbox();
		groupbox.setClosable(false);
		groupbox.setMold("3d");
		caption = new Caption("INFORMACION DEL COMPROBANTE A MARCAR COMO PERDIDA DE SERVICIO");
		caption.setStyle("color: #ffffff; font-size:12px !important");
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
		if(ventaOriginal.getTipoComprobante().getId().intValue()==Constantes.ID_TIPCOM_BOLETO_VIAJE)
			label = new Label("NUMERO BOLETO :");
		else if(ventaOriginal.getTipoComprobante().getId().intValue()==Constantes.ID_TIPCOM_BOLETA_VENTA)
			label = new Label("NUMERO BOLETA :");
		else if(ventaOriginal.getTipoComprobante().getId().intValue()==Constantes.ID_TIPCOM_FACTURA)
			label = new Label("NUMERO FACTURA :");
		label.setStyle("color:blue");
		row.appendChild(label);
		text = new Textbox(ventaOriginal.getNumeroBoleto());
		text.setStyle("font-size:11px !important");
		text.setReadonly(true);
		text.setWidth("80px");
		row.appendChild(text);
		label = new Label("FECHA VIAJE :");
		row.appendChild(label);
		text = new Textbox(ventaOriginal.getFechaPartida()==null?"":Util.DatetoString(ventaOriginal.getFechaPartida(), Constantes.DATE_FORMAT));
		text.setStyle("font-size:11px !important");
		text.setReadonly(true);
		text.setWidth("80px");
		row.appendChild(text);
		rows.appendChild(row);

		row = new Row();
		label = new Label("NUMERO ASIENTO :");
		row.appendChild(label);
		text = new Textbox(ventaOriginal.getNumeroAsiento()==null?"":ventaOriginal.getNumeroAsiento().toString());
		text.setStyle("font-size:11px !important");
		text.setReadonly(true);
		text.setWidth("80px");
		row.appendChild(text);
		label = new Label("IMPORTE :");
		row.appendChild(label);
		dblImporte= new Doublebox();
		//Ya no se utiliza la formula para determinar el importe pagado, se utiliza el campo n_impag directamente
		//MAOE 11/08/2022
//		dblImporte.setValue(ventaOriginal.getTarifa()+ventaOriginal.getRecargo()-ventaOriginal.getDescuento());
		dblImporte.setValue(ventaOriginal.getImportePagado());
		dblImporte.setStyle("align:right;font-size:11px !important");
		dblImporte.setReadonly(true);
		dblImporte.setWidth("80px");
		dblImporte.setLocale(Locale.US);
		dblImporte.setFormat("##0.00");
		row.appendChild(dblImporte);
		rows.appendChild(row);

		row = new Row();
		row.setSpans("1,3");
		label = new Label("PASAJERO :");
		row.appendChild(label);
		text = new Textbox(ventaOriginal.getPasajero().getNombresApellidos());
		text.setReadonly(true);
		text.setWidth("90%");
		row.appendChild(text);
		rows.appendChild(row);

		row = new Row();
		row.setSpans("1,3");
		label = new Label("CLIENTE :");
		row.appendChild(label);
		text = new Textbox(ventaOriginal.getCliente()!=null?ventaOriginal.getCliente().getRazonSocial():"");
		text.setReadonly(true);
		text.setWidth("90%");
		row.appendChild(text);
		rows.appendChild(row);

		row = new Row();
		row.setSpans("1,3");
		label = new Label("TIPO FORMA PAGO :");
		label.setStyle("font-size:12px !important;font-weight: bold");
		row.appendChild(label);
		text = new Textbox(ventaOriginal.getTipoFormaPago().getDenominacion());
		text.setReadonly(true);
		text.setStyle("font-size:12px !important;color:red;font-weight: bold");
		text.setWidth("90%");
		row.appendChild(text);
		rows.appendChild(row);

		row = new Row();
		row.setSpans("1,3");
		label = new Label("CANAL VENTA :");
		label.setStyle("font-size:11px !important;font-weight: bold");
		row.appendChild(label);
		Label lblCanalVenta=new Label();
		if(ventaOriginal.getRucClienteCredito()!=null){
			Agencia agencia= ServiceLocator.getAgenciaManager().buscarAgenciaByRucClienteCredito(ventaOriginal.getRucClienteCredito());
			if(agencia!=null)
				lblCanalVenta.setValue(ventaOriginal.getCanalVenta().getDenominacion()+" <===> "+agencia.getDenominacion());
			else
				lblCanalVenta.setValue(ventaOriginal.getCanalVenta().getDenominacion()+" <===> "+"AGENCIA NO ENCONTRADA");
		}else
			lblCanalVenta.setValue(ventaOriginal.getCanalVenta().getDenominacion()+" <===> "+ventaOriginal.getAgencia().getDenominacion());
		lblCanalVenta.setStyle("font-size:11px !important;color:red;font-weight: bold");
		lblCanalVenta.setWidth("100%");
		row.appendChild(lblCanalVenta);
		rows.appendChild(row);

		row = new Row();
		row.setSpans("1,3");
		label = new Label("MOTIVO (*) :");
		row.appendChild(label);
		txtMotivo = new Textbox();
		txtMotivo.setMultiline(true);
		txtMotivo.setMaxlength(255);
		txtMotivo.setWidth("95%");
		txtMotivo.setHeight("50px");
		row.appendChild(txtMotivo);
		rows.appendChild(row);


		grid.appendChild(rows);
		groupbox.appendChild(grid);
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

		Button button = new Button("Continuar", "resources/mp_aceptarEnabled.png");
		button.setAutodisable("self");
		button.setClass("btnCommandL");
		button.addEventListener(Events.ON_CLICK, new EventListener<Event>() {
			@Override
			public void onEvent(Event e){
				guardarPerdida(ventaOriginal);
			}
		});
//		button.setHeight("28px");
		row.appendChild(button);
		button = new Button("Cancelar", "resources/mp_cancelarEnabled.png");
		button.addEventListener(Events.ON_CLICK, new EventListener<Event>() {
			@Override
			public void onEvent(Event e){
				win.onClose();
			}
		});
//		button.setHeight("28px");
		button.setClass("btnCommandL");
		button.setFocus(true);
		row.appendChild(button);

		rows.appendChild(row);

		grid.appendChild(rows);
		win.appendChild(grid);

		wndPerdidaServicio = win;
		this.appendChild(wndPerdidaServicio);
		wndPerdidaServicio.setMode(MODAL);
	}

	private void guardarPerdida(VentaPasaje venta) {
		Messagebox.show("Se va marcar el boleto como perdida de servicio, esta acci�n no se puede deshacer, �Seguro que desea continuar?", DlgMessage.NOMBREAPLICACION, DlgMessage.BTN_YESNO, Messagebox.QUESTION, new EventListener<Event>() {
			@Override
			public void onEvent(Event ev) throws Exception{
				try {
					if(txtMotivo.getText().trim().equals(""))
						throw new ObservacionesNullException();

					if(ev.getName().equals("onYes")) {
						venta.setTipoTransaccion(Constantes.TIPO_OPERACION_PERDIDA_SERVICIO);
						venta.setObservaciones(txtMotivo.getText().trim().toUpperCase());
						UtilData.auditarRegistro(venta, true, getUsuario(), Executions.getCurrent());
						ServiceLocator.getVentaPasajesManager().guardarPerdidaServicio(venta);
					}
					DlgMessage.information(Messages.getString("WndPerdidaServicio.information.exitoPerdidaServicio")+" "+venta.getNumeroControl());
					wndPerdidaServicio.onClose();
				}catch(ObservacionesNullException onex) {
					DlgMessage.information(Messages.getString("WndPerdidaServicio.information.motivoPerdidaServicio"));
				}catch(Exception ex) {
					ex.printStackTrace();
					DlgMessage.error(ex.getMessage());
				}
			}
		});
	}
}
