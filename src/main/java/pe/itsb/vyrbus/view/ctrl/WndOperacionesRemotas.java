package pe.itsb.vyrbus.view.ctrl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TreeMap;

import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zul.Bandbox;
import org.zkoss.zul.Bandpopup;
import org.zkoss.zul.Button;
import org.zkoss.zul.Caption;
import org.zkoss.zul.Checkbox;
import org.zkoss.zul.Column;
import org.zkoss.zul.Columns;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Comboitem;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.Div;
import org.zkoss.zul.Doublebox;
import org.zkoss.zul.Grid;
import org.zkoss.zul.Groupbox;
import org.zkoss.zul.Hbox;
import org.zkoss.zul.Image;
import org.zkoss.zul.Label;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listhead;
import org.zkoss.zul.Listheader;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Row;
import org.zkoss.zul.Rows;
import org.zkoss.zul.Separator;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Toolbarbutton;
import org.zkoss.zul.Vbox;
import org.zkoss.zul.Window;

import pe.itsb.vyrbus.model.bean.Agencia;
import pe.itsb.vyrbus.model.bean.ControlEspecieValorada;
import pe.itsb.vyrbus.model.bean.DetalleItinerario;
import pe.itsb.vyrbus.model.bean.FormaPago;
import pe.itsb.vyrbus.model.bean.Itinerario;
import pe.itsb.vyrbus.model.bean.ItinerarioAgenciaPartida;
import pe.itsb.vyrbus.model.bean.Liquidacion;
import pe.itsb.vyrbus.model.bean.Localidad;
import pe.itsb.vyrbus.model.bean.OperadorTarjetaCredito;
import pe.itsb.vyrbus.model.bean.TarjetaCredito;
import pe.itsb.vyrbus.model.bean.TipoComprobante;
import pe.itsb.vyrbus.model.bean.TipoFormaPago;
import pe.itsb.vyrbus.model.bean.TipoMovimiento;
import pe.itsb.vyrbus.model.bean.TipoNota;
import pe.itsb.vyrbus.model.bean.TmpOcupacionAsientos;
import pe.itsb.vyrbus.model.bean.Usuario;
import pe.itsb.vyrbus.model.bean.UsuarioHardware;
import pe.itsb.vyrbus.model.bean.VentaPasaje;
import pe.itsb.vyrbus.service.exceptions.DevolucionByTipoMovimientoNoPermitidoException;
import pe.itsb.vyrbus.service.exceptions.FechaCaducidadNullException;
import pe.itsb.vyrbus.service.exceptions.FormaPagoNullException;
import pe.itsb.vyrbus.service.exceptions.ImporteMixtoNullException;
import pe.itsb.vyrbus.service.exceptions.ItinerarioException;
import pe.itsb.vyrbus.service.exceptions.LimiteSecuencialException;
import pe.itsb.vyrbus.service.exceptions.ManifiestoImpresoException;
import pe.itsb.vyrbus.service.exceptions.NumeroAsientoNullException;
import pe.itsb.vyrbus.service.exceptions.NumeroBoletoNullException;
import pe.itsb.vyrbus.service.exceptions.OperadorTarjetaCreditoNullException;
import pe.itsb.vyrbus.service.exceptions.PostergacionByFechaLimitePostergarException;
import pe.itsb.vyrbus.service.exceptions.PostergacionByFormaPagoNoPermitidoException;
import pe.itsb.vyrbus.service.exceptions.PostergacionByTipoAgenciaNoPermitidoException;
import pe.itsb.vyrbus.service.exceptions.PostergacionByTipoMovimientoNoPermitidoException;
import pe.itsb.vyrbus.service.exceptions.PostergacionNoCambioNombreException;
import pe.itsb.vyrbus.service.exceptions.PostergacionNoCambioRazonSozialException;
import pe.itsb.vyrbus.service.exceptions.TarjetaCreditoNullException;
import pe.itsb.vyrbus.service.exceptions.TipoComprobanteNullException;
import pe.itsb.vyrbus.service.exceptions.TipoFormaPagoNullException;
import pe.itsb.vyrbus.service.locator.ServiceLocator;
import pe.itsb.vyrbus.service.util.Constantes;
import pe.itsb.vyrbus.service.util.Messages;
import pe.itsb.vyrbus.service.util.Util;
import pe.itsb.vyrbus.service.util.UtilData;
import pe.itsb.vyrbus.service.util.WSFE;
import pe.itsb.vyrbus.view.ui.DlgMessage;
import pe.itsb.vyrbus.view.ui.WndBase;
import pe.itsb.vyrbus.view.ui.WndMapaBus;
import pe.itsb.vyrbus.view.ui.WndSeleccionaItinerario;

/**
 * @author JABANTO
 *
 */
@SuppressWarnings("deprecation")
public class WndOperacionesRemotas extends WndBase {
	private static final long serialVersionUID = 1L;

	private Combobox cmbOrigen;
	private Combobox cmbDestino;
	private Datebox dtbxFechaPartida;
	private Textbox txtNumeroBoleto;
	private Textbox txtNumeroControl;
	private Textbox txtNumeroDocumetoPasajero;
	private Listbox lsbxVentas;
	//Devolucion
	private Window wndDevolucion;
//	private Combobox cmbDevPorcentaje;
//	private Doublebox dblDevtarifa;
//	private Doublebox dblDevDescuento;
//	private Doublebox dblDevRecargo;
//	private Doublebox dblDevPenalidad;
//	private Doublebox dblDevImporte;
//	private Textbox txtDevMotivo;
	private VentaPasaje boletoDevolver;
	/****/
	private Textbox txtMotivo;
	private Combobox cmbPorcentajeDevolucion;
//	private Combobox  cmbTipoComprobante;
//	private Combobox cmbTipoFormaPago;
//	private Combobox cmbTarjetaCredito;

	//Postergación
	private Window wndPostergacion;
	private Textbox txtPosSituActuRuta;
	private Textbox txtPosSituActuItinerario;
	private Textbox txtNumeroAsientoPostergado;
	private Textbox txtNumeroPisoPostergado;
	private Textbox txtItinerarioPostergado;
	private Textbox txtRutaPostergado;
	private Textbox txtServicioPostergado;
	private Textbox txtBoletoPostergado;
	private Combobox cmbEmbarquePostergado;
	private Textbox txtSalidaPostergado;
	private Textbox txtHoraEmbarque;
	private Doublebox dblbxMontoAnterior;
	private Doublebox dblbxTarifa;
	private Doublebox dblbxDescuento;
	private Doublebox dblbxSaldo;
	private Doublebox dblbxPenalidad;
	private Doublebox dblbxImporteTotal;
	private Image imgAsiento=new Image("resources/mp_buscarEnabled.png");
	private Checkbox chkFechaAbierta;
	private VentaPasaje postergacion;
	private DetalleItinerario detalleItinerario;
	private int secuencial=0;

	//Reimpresión
//	private Window wndReimpresion;
	private Combobox cmbTipoComprobante;
	private Combobox cmbFormaPago;
	private Combobox cmbTipoFormaPago;
	private Combobox cmbOperadorTarjetaCredito;
	private Combobox cmbTarjetaCredito;
//	private VentaPasaje boletoReimprimir;
	private Listbox lbxUsuarioHardware;
	private Bandbox bndbxUsuarioHardware;
//	private Textbox txtNumeroboletoReimpresion;

	String styleActivo_11px="font-size:11px !important";
	String styleActivo_9px="font-size:9px !important";
	String styleAnulado_11px="font-size:11px !important; color:red";
	String styleAnulado_9px="font-size:9px !important; color:red";

//	private static final String DEVOLUCION_80 = "DEVOLUCION AL 80%";
	private static final String DEVOLUCION_100 = "DEVOLUCION AL 100%";

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.view.ui.WndBase#initComponents()
	 */
	@Override
	public void initComponents() {
		cmbOrigen=(Combobox)this.getFellow("cmbOrigen");
		cmbDestino=(Combobox)this.getFellow("cmbDestino");
		dtbxFechaPartida=(Datebox)this.getFellow("dtbxFechaPartida");
		txtNumeroBoleto=(Textbox)this.getFellow("txtNumeroBoleto");
		txtNumeroControl=(Textbox)this.getFellow("txtNumeroControl");
		txtNumeroDocumetoPasajero=(Textbox)this.getFellow("txtNumeroDocumetoPasajero");
		lsbxVentas=(Listbox)this.getFellow("lsbxVentas");

		txtNumeroControl.addEventListener(Events.ON_CHANGE,new EventListener<Event>() {
			@Override
			public void onEvent(Event event) throws Exception {
				if(txtNumeroControl.getText().trim().length()>0)
					txtNumeroControl.setText(Util.generateControlNumber(txtNumeroControl.getText().trim()));
			}
		});

		txtNumeroBoleto.addEventListener(Events.ON_CHANGE, new EventListener<Event>() {
			@Override
			public void onEvent(Event event) throws Exception {
				if(txtNumeroBoleto.getText().trim().length()>0)
					txtNumeroBoleto.setText(Util.autocompleNumberBoleto(txtNumeroBoleto.getText().trim()));
			}
		});
	}

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.view.ui.WndBase#onCreate()
	 */
	@Override
	public void onCreate() throws Exception {
		UtilData.cargarDataCombo(cmbOrigen, Localidad.class, true);
		UtilData.cargarDataCombo(cmbDestino, Localidad.class, true);

		String fecha = Util.DatetoString(new Date(), "yyyyMMdd");
		dtbxFechaPartida.setConstraint("after "+fecha);
		dtbxFechaPartida.setValue(new Date());

	}

	public void buscarVentas() throws Exception{
		String fechaPartida=Constantes.FORMAT_DATE.format(dtbxFechaPartida.getValue());
		Integer idOrigen=null;
		Integer idDestino=null;
		String numeroBoleto=null;
		String numeroControl=null;
		String documentoPax=null;

		if(cmbOrigen.getSelectedItem().getValue() instanceof Localidad)
			idOrigen=((Localidad)cmbOrigen.getSelectedItem().getValue()).getId();
		if(cmbDestino.getSelectedItem().getValue() instanceof Localidad)
			idDestino=((Localidad)cmbDestino.getSelectedItem().getValue()).getId();
		if(txtNumeroBoleto.getText().trim().length()>0)
			numeroBoleto=txtNumeroBoleto.getText().trim();
		if(txtNumeroControl.getText().trim().length()>0)
			numeroControl=txtNumeroControl.getText().trim().toUpperCase();
		if(txtNumeroDocumetoPasajero.getText().trim().length()>0)
			documentoPax=txtNumeroDocumetoPasajero.getText().trim().toUpperCase();

		List<VentaPasaje>listVentas=ServiceLocator.getVentaPasajesManager().buscarOperacionesRemotras(fechaPartida, idOrigen, idDestino, numeroBoleto, numeroControl, documentoPax);

		Util.limpiarListbox(lsbxVentas);

		for(VentaPasaje ventaPasaje: listVentas){
			boolean isAnulado=ventaPasaje.getTipoMovimiento().getId().intValue()==Constantes.ID_TIPMOV_ANULACION ||
					ventaPasaje.getTipoMovimiento().getId().intValue()==Constantes.ID_TIPMOV_ANULACION_SISTEMA ||
							ventaPasaje.getTipoMovimiento().getId().intValue()==Constantes.ID_TIPMOV_DEVOLUCION;

			Listitem item=new Listitem();
			Listcell cell=new Listcell(ventaPasaje.getNumeroBoleto());
			cell.setStyle(isAnulado?styleAnulado_11px:styleActivo_11px);
			item.appendChild(cell);
			cell=new Listcell(ventaPasaje.getNumeroControl());
			cell.setStyle(isAnulado?styleAnulado_11px:styleActivo_11px);
			item.appendChild(cell);
			cell=new Listcell(ventaPasaje.getPasajero().getNombresApellidos());
			cell.setStyle(isAnulado?styleAnulado_9px:styleActivo_9px);
			item.appendChild(cell);
			cell=new Listcell(ventaPasaje.getServicio().getDenominacion());
			cell.setStyle(isAnulado?styleAnulado_9px:styleActivo_9px);
			item.appendChild(cell);
			cell=new Listcell(ventaPasaje.getRuta().toString());
			cell.setStyle(isAnulado?styleAnulado_9px:styleActivo_9px);
			item.appendChild(cell);
			cell=new Listcell(Constantes.FORMAT_DATE.format(ventaPasaje.getFechaPartida()));
			cell.setStyle(isAnulado?styleAnulado_11px:styleActivo_11px);
			item.appendChild(cell);
			cell=new Listcell(ventaPasaje.getHoraPartida());
			cell.setStyle(isAnulado?styleAnulado_11px:styleActivo_11px);
			item.appendChild(cell);
			cell=new Listcell(ventaPasaje.getNumeroAsiento().toString());
			cell.setStyle(isAnulado?styleAnulado_11px:styleActivo_11px);
			item.appendChild(cell);

			//DEVOLVER
			Toolbarbutton btnDevolver = new Toolbarbutton();
			btnDevolver.setImage("resources/menu/menu_devolucion.png");
			btnDevolver.setTooltiptext("Devolución Remota");
			btnDevolver.setId(ventaPasaje.getId().toString());
			cell=new Listcell();
			cell.appendChild(btnDevolver);
			btnDevolver.addEventListener(Events.ON_CLICK,new EventListener<Event>() {
				@Override
				public void onEvent(Event event) throws Exception {
					try{
						VentaPasaje ventaOriginal=ServiceLocator.getVentaPasajesManager().buscarPorId(Long.valueOf(event.getTarget().getId()));

						if(ventaOriginal.getTipoMovimiento().getId().intValue()==Constantes.ID_TIPMOV_ANULACION_SISTEMA)
							throw new DevolucionByTipoMovimientoNoPermitidoException(Constantes.ID_TIPMOV_ANULACION_SISTEMA);
						if(ventaOriginal.getTipoMovimiento().getId().intValue()==Constantes.ID_TIPMOV_ANULACION)
							throw new DevolucionByTipoMovimientoNoPermitidoException(Constantes.ID_TIPMOV_ANULACION_SISTEMA);
						else if(ventaOriginal.getTipoMovimiento().getId().intValue()==Constantes.ID_TIPMOV_DEVOLUCION)
							throw new DevolucionByTipoMovimientoNoPermitidoException(Constantes.ID_TIPMOV_DEVOLUCION);

						createVentanaDevolucion(ventaOriginal);

					}catch(DevolucionByTipoMovimientoNoPermitidoException dtmnpex){
						if(dtmnpex.getTipoMovimiento().intValue()==Constantes.ID_TIPMOV_ANULACION_SISTEMA)
							DlgMessage.information(Messages.getString("WndDevolucionBoleto.information.noDevolucionAnulacion"));
						else if(dtmnpex.getTipoMovimiento().intValue()==Constantes.ID_TIPMOV_DEVOLUCION)
							DlgMessage.information(Messages.getString("WndDevolucionBoleto.information.noDevolucionByDevuelto"));
					}
				}
			});

			//POSTERGACION
			Toolbarbutton btnPostergar = new Toolbarbutton();
			btnPostergar.setDisabled(true);
			btnPostergar.setImage("resources/menu/menu_confirmarFechaAbierta.png");
			btnPostergar.setTooltiptext("Postergación remota");
			cell.appendChild(btnPostergar);
			btnPostergar.setId(ventaPasaje.getId().toString());
			btnPostergar.addEventListener(Events.ON_CLICK,new EventListener<Event>() {
				@Override
				public void onEvent(Event event) throws Exception {
					postergacion=new VentaPasaje();
					postergacion=ServiceLocator.getVentaPasajesManager().buscarPorId(Long.valueOf(event.getTarget().getId()));
					secuencial=postergacion.getSecuencial();

					try{
						if(postergacion.getTipoMovimiento().getId().intValue()==Constantes.ID_TIPMOV_ANULACION_SISTEMA)
							throw new PostergacionByTipoMovimientoNoPermitidoException(Constantes.ID_TIPMOV_ANULACION_SISTEMA);
						if(postergacion.getTipoMovimiento().getId().intValue()==Constantes.ID_TIPMOV_ANULACION)
							throw new PostergacionByTipoMovimientoNoPermitidoException(Constantes.ID_TIPMOV_ANULACION_SISTEMA);
						else if(postergacion.getTipoMovimiento().getId().intValue()==Constantes.ID_TIPMOV_FECHA_ABIERTA)
							throw new PostergacionByTipoMovimientoNoPermitidoException(Constantes.ID_TIPMOV_FECHA_ABIERTA);
						else if(postergacion.getTipoMovimiento().getId().intValue()==Constantes.ID_TIPMOV_POSTERGACION_FA)
							throw new PostergacionByTipoMovimientoNoPermitidoException(Constantes.ID_TIPMOV_POSTERGACION_FA);
						else if(postergacion.getFormaPago().getId().intValue()==Constantes.ID_FORPAG_CREDITO && postergacion.getRucClienteCredito()!=null){
							Agencia agencia = ServiceLocator.getAgenciaManager().buscarAgenciaByRucClienteCredito(postergacion.getRucClienteCredito());
							if(agencia.getTipoAgencia().getId().intValue()==Constantes.ID_TIPAGE_CORPORATIVO)
								throw new PostergacionByTipoAgenciaNoPermitidoException(Constantes.ID_TIPAGE_CORPORATIVO);
						}else if(postergacion.getFormaPago().getId().intValue()==Constantes.ID_FORPAG_CORTESIA)
							throw new PostergacionByFormaPagoNoPermitidoException(Constantes.ID_FORPAG_CORTESIA);

						if(postergacion.getSecuencial().intValue() >= Constantes.MAXIMO_POSTERGACIONES)
							throw new LimiteSecuencialException();

						if(Util.comparaFechas(postergacion.getFechaCaducidad(), new Date(), Util.OPER_MENOR))
							throw new FechaCaducidadNullException();

						if(ServiceLocator.getDetalleManifiestoManager().validarVentaManifiesto(postergacion.getId()))
							throw new ManifiestoImpresoException();

						createVentanaPostergacion(postergacion);

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
					}catch(Exception ex){
						DlgMessage.error(this.getClass().getSimpleName()+" "+ex.getMessage());
					}
				}
			});

//			//REIMPRESION
//			Toolbarbutton btnReimprimir = new Toolbarbutton();
//			btnReimprimir.setImage("resources/menu/menu_reimprimir.png");
//			btnReimprimir.setTooltiptext("Reimpresión remota");
//			cell.appendChild(btnReimprimir);
//			btnReimprimir.setId(ventaPasaje.getId().toString());
//			btnReimprimir.addEventListener(Events.ON_CLICK, new EventListener<Event>() {
//				@Override
//				public void onEvent(Event event) throws Exception {
//					try{
//						VentaPasaje ventaOriginal=ServiceLocator.getVentaPasajesManager().buscarPorId(Long.valueOf(event.getTarget().getId()));
//
//						if(ventaOriginal.getTipoMovimiento().getId().intValue()==Constantes.ID_TIPMOV_ANULACION_SISTEMA)
//							throw new ReimpresionByTipoMovimientoNoPermitidoException(Constantes.ID_TIPMOV_ANULACION_SISTEMA);
//						if(ventaOriginal.getTipoMovimiento().getId().intValue()==Constantes.ID_TIPMOV_ANULACION)
//							throw new ReimpresionByTipoMovimientoNoPermitidoException(Constantes.ID_TIPMOV_ANULACION_SISTEMA);
//						else if(ventaOriginal.getTipoMovimiento().getId().intValue()==Constantes.ID_TIPMOV_DEVOLUCION)
//							throw new ReimpresionByTipoMovimientoNoPermitidoException(Constantes.ID_TIPMOV_DEVOLUCION);
//						else if (ServiceLocator.getDetalleManifiestoManager().validarVentaManifiesto(ventaOriginal.getId()))
//							throw new ManifiestoImpresoException();
//
//						wndReimpresion = createVentanaReimpresion(ventaOriginal);
//						appendChild(wndReimpresion);
//						wndReimpresion.setMode(MODAL);
//
//
//					}catch(ReimpresionByTipoMovimientoNoPermitidoException rtmnpex){
//						if(rtmnpex.getTipoMovimiento().intValue()==Constantes.ID_TIPMOV_ANULACION_SISTEMA)
//							DlgMessage.information(Messages.getString("WndReimprimirBoleto.information.reimpresionByAnulacionNoPermitido"));
//						else if(rtmnpex.getTipoMovimiento().intValue()==Constantes.ID_TIPMOV_DEVOLUCION)
//							DlgMessage.information(Messages.getString("WndReimprimirBoleto.information.reimpresionByDevolucionNoPermitido"));
//					}catch(ManifiestoImpresoException miex){
//						DlgMessage.information(Messages.getString("Generales.information.manifiestoImpreso"));
//					}catch(Exception ex){
//						DlgMessage.error(this.getClass().getSimpleName()+" "+ex.getMessage());
//					}
//
//				}
//			});
			item.appendChild(cell);

			item.setValue(ventaPasaje);
			lsbxVentas.appendChild(item);
		}
	}


//	/**
//	 * Crea una venta para la devolucion
//	 * @param ventaOriginal
//	 * @throws Exception
//	 */
//	private void creaVentanaDevolucion(final VentaPasaje ventaOriginal) throws Exception{
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
//		caption = new Caption("DEVOLUCION DE BOLETO", "resources/menu/menu_devolucion.png");
//		win.appendChild(caption);
//
//		groupbox = new Groupbox();
//		groupbox.setClosable(false);
//		caption = new Caption("Información del Boleto");
//		caption.setStyle("color:#C03131");
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
//		label = new Label("NUMERO BOLETO :");
//		row.appendChild(label);
//		text = new Textbox(ventaOriginal.getNumeroBoleto());
//		text.setStyle(styleActivo_11px);
//		text.setReadonly(true);
//		text.setWidth("80px");
//		row.appendChild(text);
//		label = new Label("FECHA VIAJE :");
//		row.appendChild(label);
//		text = new Textbox(ventaOriginal.getFechaPartida()==null?"":Util.DatetoString(ventaOriginal.getFechaPartida(), Constantes.DATE_FORMAT));
//		text.setStyle(styleActivo_11px);
//		text.setReadonly(true);
//		text.setWidth("80px");
//		row.appendChild(text);
//		rows.appendChild(row);
//
//		row = new Row();
//		label = new Label("NUMERO ASIENTO :");
//		row.appendChild(label);
//		text = new Textbox(ventaOriginal.getNumeroAsiento()==null?"":ventaOriginal.getNumeroAsiento().toString());
//		text.setStyle(styleActivo_11px);
//		text.setReadonly(true);
//		text.setWidth("80px");
//		row.appendChild(text);
//		label = new Label("IMPORTE :");
//		row.appendChild(label);
//		text = new Textbox(Util.toNumberFormat((ventaOriginal.getTarifa()+ventaOriginal.getRecargo()-ventaOriginal.getDescuento()), 2));
//		text.setStyle(styleActivo_11px);
//		text.setStyle("align:right");
//		text.setReadonly(true);
//		text.setWidth("80px");
//		row.appendChild(text);
//		rows.appendChild(row);
//
//		row = new Row();
//		row.setSpans("1,3");
//		label = new Label("PASAJERO :");
//		row.appendChild(label);
//		text = new Textbox(ventaOriginal.getPasajero().getNombresApellidos());
//		text.setReadonly(true);
//		text.setWidth("90%");
//		row.appendChild(text);
//		rows.appendChild(row);
//
//		row = new Row();
//		row.setSpans("1,3");
//		label = new Label("CLIENTE :");
//		row.appendChild(label);
//		text = new Textbox(ventaOriginal.getCliente()!=null?ventaOriginal.getCliente().getRazonSocial():"");
//		text.setReadonly(true);
//		text.setWidth("90%");
//		row.appendChild(text);
//		rows.appendChild(row);
//
//		grid.appendChild(rows);
//		groupbox.appendChild(grid);
//		win.appendChild(groupbox);
//		/* ***************************************** */
//		groupbox = new Groupbox();
//		groupbox.setClosable(false);
//		caption = new Caption("Información de la Devolución");
//		caption.setStyle("color:#C03131");
//		groupbox.appendChild(caption);
//		groupbox.appendChild(caption);
//
//		Hbox hboxDev=new Hbox();
//		grid = new Grid();
//		rows = new Rows();
//		/*	Columna 1	*/
//		Columns columnsInfoDev= new Columns();
//		Column columnInfoDev = new Column();
//		columnInfoDev = new Column();
//		columnsInfoDev.appendChild(columnInfoDev);
//		grid.appendChild(columnsInfoDev);
//
//		/* INFO DEVOLUCION********************************/
//		Grid gridDev= new Grid();
//		gridDev.setWidth("145px");
//		gridDev.setStyle("border:none");
//		Rows rowsDev=new Rows();
//		Row rowDev=new Row();
//		columns = new Columns();
//		/*	Columna 1	*/
//		column = new Column();
//		column.setAlign("right");
//		column.setWidth("90px");
//		columns.appendChild(column);
//		/*	Columna 2	*/
//		column = new Column();
//		columns.appendChild(column);
//		gridDev.appendChild(columns);
//		/* ********************************/
//
//		/*  REMOTA*************************/
//		Groupbox groupRemoto=new Groupbox();
//		groupRemoto.setClosable(false);
//		caption = new Caption("Información de la Agencia remota");
//		caption.setStyle("color:#C03131");
//		groupRemoto.appendChild(caption);
//		Grid gridRemoto=new Grid();
//		gridRemoto.setStyle("border:none");
//		Rows rowsRemoto=new Rows();
//		Row rowRemoto=new Row();
//		//Columna 1
//		Columns columnsRemoto= new Columns();
//		Column columnRemoto= new Column();
//		columnRemoto.setAlign("right");
//		columnRemoto.setWidth("70px");
//		columnsRemoto.appendChild(columnRemoto);
//		gridRemoto.appendChild(columnsRemoto);
//
//		label=new Label("AGENCIA (*) :");
//		final Combobox cmbAgenciaRemota= new Combobox();
//		cmbAgenciaRemota.setReadonly(true);
//		UtilData.cargarAgenciaXtipoAgencia(cmbAgenciaRemota, Constantes.ID_TIPAGE_TEPSA,false);
//		cmbAgenciaRemota.setSelectedIndex(0);
//		cmbAgenciaRemota.setWidth("220px");
//		rowRemoto.appendChild(label);
//		rowRemoto.appendChild(cmbAgenciaRemota);
//		rowsRemoto.appendChild(rowRemoto);
//
//		final Combobox cmbUsuarioRemoto= new Combobox();
//		UtilData.cargarGenericData(cmbUsuarioRemoto, false);
//		cmbUsuarioRemoto.setReadonly(true);
//		cmbUsuarioRemoto.setWidth("220px");
//		label=new Label("USUARIO (*) :");
//		rowRemoto= new Row();
//		rowRemoto.appendChild(label);
//		rowRemoto.appendChild(cmbUsuarioRemoto);
//		rowsRemoto.appendChild(rowRemoto);
//		gridRemoto.appendChild(rowsRemoto);
//
//		cmbAgenciaRemota.addEventListener(Events.ON_CHANGE,new EventListener<Event>() {
//			@Override
//			public void onEvent(Event event) throws Exception {
//				cmbUsuarioRemoto.setDisabled(false);
//				Util.limpiarCombobox(cmbUsuarioRemoto);
//				UtilData.cargarGenericData(cmbUsuarioRemoto, false);
//				if(cmbAgenciaRemota.getSelectedIndex()>0){
//					UtilData.cargarUsuariosLiquidacion(cmbUsuarioRemoto,((Agencia)cmbAgenciaRemota.getSelectedItem().getValue()).getId(),Constantes.TRUE_VALUE,false);
//					cmbUsuarioRemoto.setFocus(true);
//				}
//			}
//		});
//
//		cmbUsuarioRemoto.addEventListener(Events.ON_CHANGE,new EventListener<Event>() {
//			@Override
//			public void onEvent(Event event) throws Exception {
//				txtDevMotivo.setFocus(true);
//			}
//		});
//
//		/* *************************************/
//
//		/* ******DEVOLUCION EXTEMPORANEA */
//		Grid gridExtempo=new Grid();
//		columns =new Columns();
//		column=new Column();
//		columns.appendChild(column);
//		gridExtempo.appendChild(columns);
//
//		rows=new Rows();
//		row=new Row();
//
//		final Checkbox chbxExtempo=new Checkbox("Devolución Extemporánea");
//		row.appendChild(chbxExtempo);
//		rows.appendChild(row);
//
//		row=new Row();
//		Hbox hbox=new Hbox();
//		label=new Label("Password Autorizador :");
//		label.setStyle("font-size:12px !important");
//		final Textbox txtPasAutorizador=new Textbox();
//		txtPasAutorizador.setWidth("152px");
//		txtPasAutorizador.setType("password");
//		txtPasAutorizador.setStyle("font-size:11px !important");
//		txtPasAutorizador.setDisabled(true);
//		hbox.appendChild(label);
//		hbox.appendChild(txtPasAutorizador);
//		row.appendChild(hbox);
//		rows.appendChild(row);
//
//		gridExtempo.appendChild(rows);
//
//		chbxExtempo.addEventListener(Events.ON_CHECK,new EventListener<Event>() {
//			@Override
//			public void onEvent(Event event) throws Exception {
//				if(chbxExtempo.isChecked()){
//					txtPasAutorizador.setDisabled(false);
//					txtPasAutorizador.setFocus(true);
//					txtPasAutorizador.setText("");
//				}else{
//					txtPasAutorizador.setDisabled(true);
//					txtPasAutorizador.setText("");
//				}
//			}
//		});
//
//
//		/* ************************************/
//
//
//		rowDev = new Row();
//		label = new Label("DEVOLUCION (*) :");
//		cmbDevPorcentaje = new Combobox();
//		cmbDevPorcentaje.addEventListener(Events.ON_CHANGE, new EventListener<Event>() {
//			public void onEvent(Event e){
//				Double importe = ventaOriginal.getTarifa()-ventaOriginal.getDescuento()+ventaOriginal.getRecargo();
//				if(cmbDevPorcentaje.getText().equals(DEVOLUCION_80)){
//					Double penalidad = (importe*20)/100;
//					dblDevPenalidad.setValue(penalidad);
//					dblDevImporte.setValue(importe-penalidad);
//				}else if(cmbDevPorcentaje.getText().equals(DEVOLUCION_100)){
//					dblDevPenalidad.setValue(0.0);
//					dblDevImporte.setValue(importe);
//				}else{
//					dblDevPenalidad.setValue(0.0);
//					dblDevImporte.setValue(0.0);
//				}
//				cmbUsuarioRemoto.setFocus(true);
//			}
//		});
//		cmbDevPorcentaje.setWidth("140px");
//		cmbDevPorcentaje.setStyle(styleActivo_11px);
//		cmbDevPorcentaje.setReadonly(true);
//		cmbDevPorcentaje.setAutodrop(true);
//		onLoadDevolucion(cmbDevPorcentaje);
////		Hbox
//		rows=new Rows();
//		hbox=new Hbox();
//		Space space=new Space();
//		space.setWidth("5px");
//		hbox.setAlign("center");
//		hbox.appendChild(space);
//		hbox.appendChild(label);
//		hbox.appendChild(cmbDevPorcentaje);
//		row=new Row();
//		row.appendChild(hbox);
//		rows.appendChild(row);
//
//		rowDev = new Row();
//		label = new Label("TARIFA :");
//		rowDev.appendChild(label);
//		dblDevtarifa = new Doublebox(ventaOriginal.getTarifa());
//		dblDevtarifa.setFormat("##0.00");
//		dblDevtarifa.setLocale(Locale.US);
//		dblDevtarifa.setReadonly(true);
//		rowDev.appendChild(dblDevtarifa);
//		rowsDev.appendChild(rowDev);
//
//		rowDev = new Row();
//		label = new Label("DESCUENTO :");
//		rowDev.appendChild(label);
//		dblDevDescuento = new Doublebox(ventaOriginal.getDescuento());
//		dblDevDescuento.setFormat("##0.00");
//		dblDevDescuento.setLocale(Locale.US);
//		dblDevDescuento.setReadonly(true);
//		rowDev.appendChild(dblDevDescuento);
//		rowsDev.appendChild(rowDev);
//
//		rowDev = new Row();
//		label = new Label("RECARGO :");
//		rowDev.appendChild(label);
//		dblDevRecargo = new Doublebox(ventaOriginal.getRecargo());
//		dblDevRecargo.setFormat("##0.00");
//		dblDevRecargo.setLocale(Locale.US);
//		dblDevRecargo.setReadonly(true);
//		rowDev.appendChild(dblDevRecargo);
//		rowsDev.appendChild(rowDev);
//
//		rowDev = new Row();
//		label = new Label("PENALIDAD :");
//		rowDev.appendChild(label);
//		dblDevPenalidad = new Doublebox(0.0);
//		dblDevPenalidad.setFormat("##0.00");
//		dblDevPenalidad.setLocale(Locale.US);
//		dblDevPenalidad.setReadonly(true);
//		rowDev.appendChild(dblDevPenalidad);
//		rowsDev.appendChild(rowDev);
//
//		rowDev = new Row();
//		label = new Label("IMPORTE :");
//		rowDev.appendChild(label);
//		dblDevImporte = new Doublebox(0.0);
//		dblDevImporte.setFormat("##0.00");
//		dblDevImporte.setLocale(Locale.US);
//		dblDevImporte.setReadonly(true);
//		rowDev.appendChild(dblDevImporte);
//		rowsDev.appendChild(rowDev);
//
//		gridDev.appendChild(rowsDev);
//		groupRemoto.appendChild(gridRemoto);
//		row=new Row();
//		hboxDev.appendChild(gridDev);
//
//		Vbox vbox=new Vbox();
//		vbox.appendChild(groupRemoto);
//		vbox.appendChild(gridExtempo);
//
//		hboxDev.appendChild(vbox);
//		row.appendChild(hboxDev);
//		rows.appendChild(row);
//
//		row = new Row();
//		hbox=new Hbox();
//		hbox.setAlign("center");
//		label = new Label("MOTIVO (*) :");
//		txtDevMotivo = new Textbox();
//		txtDevMotivo.setMultiline(true);
//		txtDevMotivo.setMaxlength(255);
//		txtDevMotivo.setWidth("395px");
//		txtDevMotivo.setHeight("35px");
//		hbox.appendChild(label);
//		hbox.appendChild(txtDevMotivo);
//		row.appendChild(hbox);
//		rows.appendChild(row);
//
//		grid.appendChild(rows);
//		groupbox.appendChild(grid);
//		win.appendChild(groupbox);
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
//			public void onEvent(Event e) throws Exception{
//				if(Util.comparaFechas(ventaOriginal.getFechaCaducidad(), ServiceLocator.getVentaPasajesManager().getDateSystem(), Util.OPER_MENOR)){
//					DlgMessage.information(Messages.getString("WndDevolucionBoleto.information.noDevolucionBoletoCaducado"));
//					return;
//				}else if(cmbDevPorcentaje.getSelectedIndex()==0){
//					DlgMessage.information(Messages.getString("WndDevolucionBoleto.information.noPorcentajeDevolucion"));
//					return;
//				}else if (!(cmbAgenciaRemota.getSelectedItem().getValue() instanceof Agencia)){
//					DlgMessage.information(Messages.getString("wndOperacionesRemotas.information.noAgenciaRemota"), cmbAgenciaRemota);
//					return;
//				}else if (!(cmbUsuarioRemoto.getSelectedItem().getValue() instanceof Usuario)){
//					DlgMessage.information(Messages.getString("wndOperacionesRemotas.information.noUsuarioRemoto"), cmbUsuarioRemoto);
//					return;
//				}else if(txtDevMotivo.getText().trim().equals("")){
//					DlgMessage.information(Messages.getString("WndDevolucionBoleto.information.noMotivoDevolucion"), txtDevMotivo);
//					return;
//				}else if(chbxExtempo.isChecked()){
//					if(txtPasAutorizador.getText().trim().isEmpty()){
//						DlgMessage.information(Messages.getString("wndOperacionesRemotas.information.noPasswordAutorizador"), txtDevMotivo);
//						return;
//					}else{
//						//Valida password autorizador
//						TreeMap<String, Object>criteriosBusqueda=new TreeMap<String,Object>();
//						criteriosBusqueda.put("password", txtPasAutorizador.getText().trim());
//						criteriosBusqueda.put("estadoRegistro", Constantes.VALUE_ACTIVO);
//						List<AutorizadorCortesia> lstAutCort= ServiceLocator.getAutorizadorCortesiaManager().buscarPorX(criteriosBusqueda, null);
//						if(lstAutCort.size()==0){
//							DlgMessage.information(Messages.getString("wndOperacionesRemotas.information.noPasswordAutorizadorIncorrect"), txtDevMotivo);
//							return;
//						}
//					}
//				}else if(ServiceLocator.getDetalleManifiestoManager().validarVentaManifiesto(Long.valueOf(ventaOriginal.getId()))){
//					DlgMessage.information(Messages.getString("Generales.information.manifiestoImpreso"));
//					return;
//				}
//
//
//				//Valida que el usuario remoto seleccionado tenga una liquidacion abierta
//				final Liquidacion liquidacion=ServiceLocator.getLiquidacionManager().buscarUltimaLiquidacion(((Agencia)cmbAgenciaRemota.getSelectedItem().getValue()).getId(),((Usuario)cmbUsuarioRemoto.getSelectedItem().getValue()).getId(), Constantes.TRUE_VALUE);
//				if(liquidacion==null){
//					DlgMessage.information(Messages.getString("wndOperacionesRemotas.information.noLiquidacionAbierta"));
//					return;
//				}
//
//				Messagebox.show(Messages.getString("WndDevolucionBoleto.question.confirmarDevolucion"), DlgMessage.NOMBREAPLICACION, DlgMessage.BTN_YESNO, Messagebox.QUESTION, new EventListener<Event>() {
//					public void onEvent(Event e){
//						try{
//							if(e.getName().equals("onYes")){
////									boletoDevolver = (VentaPasaje)ventaOriginal.clone();
////									boletoDevolver.setId(null);
////									boletoDevolver.setVentaPasaje(ventaOriginal);
////									boletoDevolver.setPenalidad(dblDevPenalidad.getValue());
////									boletoDevolver.setImportePagado(dblDevImporte.getValue());
////									boletoDevolver.setImportePagadoEfectivo(0.0);
////									boletoDevolver.setImportePagadoTarjeta(0.0);
////									boletoDevolver.setTipoMovimiento(new TipoMovimiento(Constantes.ID_TIPMOV_DEVOLUCION));
////									boletoDevolver.setAgencia((Agencia)cmbAgenciaRemota.getSelectedItem().getValue());
////									boletoDevolver.setUsuario((Usuario)cmbUsuarioRemoto.getSelectedItem().getValue());
//////									boletoDevolver.setUsuarioHardware((UsuarioHardware)getDesktop().getSession().getAttribute(Constantes.ATRIBUTO_USUARIO_HARDWARE));
////									boletoDevolver.setEstadoRegistro(Constantes.VALUE_ACTIVO);
////									boletoDevolver.setObservaciones(txtDevMotivo.getText().toUpperCase());
////									boletoDevolver.setFechaLiquidacion(liquidacion.getFechaLiquidacion());
////									boletoDevolver.setLiquidacion(null);
////									boletoDevolver.setFechaTransferencia(null);
////									UtilData.auditarRegistro(boletoDevolver, getUsuario(), Executions.getCurrent());
////									int result = ServiceLocator.getVentaPasajesManager().devolucionBoleto(boletoDevolver);
////
////									if(result==Constantes.CORRECT){
////										DlgMessage.information(Messages.getString("WndDevolucionBoleto.information.exitoDevolucion")+" "+boletoDevolver.getNumeroControl());
////										wndDevolucion.onClose();
////										buscarVentas();
////									}
//							}
//						}catch(Exception ex){
//							ex.printStackTrace();
//							DlgMessage.information(this.getClass().getSimpleName()+" "+ex.getMessage());
//						}
//					}
//				});
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
//
//		wndDevolucion = win;
//		this.appendChild(wndDevolucion);
//		wndDevolucion.setMode(MODAL);
//	}

	/**
	 * Crea una venta para la devolucion
	 * @param ventaOriginal
	 * @throws Exception
	 */
	private void createVentanaDevolucion(final VentaPasaje ventaOriginal) throws Exception{
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

		caption = new Caption("DEVOLUCION DE BOLETO", "resources/menu/menu_reimprimir.png");
		win.appendChild(caption);
		label = new Label("Se va a realizar la Devolución del Comprobante con los siguientes datos :");
		label.setStyle("font-size:12px !important");
		win.appendChild(label);

		win.appendChild(new Separator("horizontal"));

		groupbox = new Groupbox();
		groupbox.setClosable(false);
		caption = new Caption("Información del Comprobante a devolver");
		caption.setStyle("font-size:12px !important;color:black;font-weight: bold");
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
		text = new Textbox(Util.toNumberFormat((ventaOriginal.getTarifa()+ventaOriginal.getRecargo()-ventaOriginal.getDescuento()), 2));
		text.setStyle("align:right;font-size:11px !important");
		text.setReadonly(true);
		text.setWidth("80px");
		row.appendChild(text);
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


		grid.appendChild(rows);
		groupbox.appendChild(grid);
		win.appendChild(groupbox);
		/* ***************************************** */


		/**INFORMACION REMOTA*/
		groupbox = new Groupbox();
		groupbox.setClosable(false);
		caption = new Caption("Información Remota");
		caption.setStyle("font-size:12px !important;color:blue;font-weight: bold");
		groupbox.appendChild(caption);

		Grid gr= new Grid();
		rows= new  Rows();
		row= new Row();
		columns= new Columns();
		column= new Column();
		column.setWidth("130px");
		column.setAlign("right");
		columns.appendChild(column);
		column= new Column();
		columns.appendChild(column);
		gr.appendChild(columns);

		final Combobox cmbAgenciaRemota= new Combobox();
		cmbAgenciaRemota.setReadonly(true);
		UtilData.cargarAgenciaXtipoAgencia(cmbAgenciaRemota, Constantes.ID_TIPAGE_TEPSA,false);
		cmbAgenciaRemota.setSelectedIndex(0);
		cmbAgenciaRemota.setWidth("92%");
		label=new Label("AGENCIA REMOTA (*) :");
		row.appendChild(label);
		row.appendChild(cmbAgenciaRemota);
		rows.appendChild(row);

		final Combobox cmbUsuarioRemoto= new Combobox();
		UtilData.cargarGenericData(cmbUsuarioRemoto, false);
		cmbUsuarioRemoto.setReadonly(true);
		cmbUsuarioRemoto.setWidth("92%");
		label=new Label("USUARIO REMOTO (*) :");
		row= new Row();
		row.appendChild(label);
		row.appendChild(cmbUsuarioRemoto);
		rows.appendChild(row);
		gr.appendChild(rows);

		cmbAgenciaRemota.addEventListener(Events.ON_CHANGE,new EventListener<Event>() {
			@Override
			public void onEvent(Event event) throws Exception {
				cmbUsuarioRemoto.setDisabled(false);
				Util.limpiarCombobox(cmbUsuarioRemoto);
				UtilData.cargarGenericData(cmbUsuarioRemoto, false);
				if(cmbAgenciaRemota.getSelectedIndex()>0){
					UtilData.cargarUsuariosLiquidacion(cmbUsuarioRemoto,((Agencia)cmbAgenciaRemota.getSelectedItem().getValue()).getId(),Constantes.TRUE_VALUE,false);
					cmbUsuarioRemoto.setFocus(true);
				}
			}
		});
		groupbox.appendChild(gr);
		win.appendChild(groupbox);
		/**FIN INFORMACION REMOTA*/


		groupbox = new Groupbox();
		groupbox.setClosable(false);
		caption = new Caption("Información de la Devolución");
		caption.setStyle("font-size:12px !important;color:black;font-weight: bold");
		groupbox.appendChild(caption);

		grid = new Grid();
		rows = new Rows();
		columns = new Columns();
		/*	Columna 1	*/
		column = new Column();
		column.setAlign("right");
		column.setWidth("100px");
		columns.appendChild(column);
		/* Columna 2*/
		column = new Column();
		column.setWidth("140px");
		columns.appendChild(column);
		/* Columna 3*/
		column = new Column();
		column.setAlign("right");
		column.setWidth("100px");
		columns.appendChild(column);
		/*	Columna 4	*/
		column = new Column();
		columns.appendChild(column);
		grid.appendChild(columns);

		row = new Row();
		row.setSpans("1,3");
		label = new Label("% DE DEVOLUCION :");
		row.appendChild(label);
		cmbPorcentajeDevolucion = new Combobox();
		cmbPorcentajeDevolucion.setDisabled(true);
		cmbPorcentajeDevolucion.setWidth("130px");
		llenarTiposDevolucion(cmbPorcentajeDevolucion);
		row.appendChild(cmbPorcentajeDevolucion);
		rows.appendChild(row);

		row = new Row();
		label = new Label("MOTIVO (*) :");
		row.appendChild(label);
		txtMotivo = new Textbox();
		txtMotivo.setMultiline(true);
		txtMotivo.setMaxlength(255);
		txtMotivo.setWidth("95%");
		txtMotivo.setHeight("50px");
		row.setSpans("1,3");
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
		button.addEventListener(Events.ON_CLICK, new EventListener<Event>() {
			@Override
			public void onEvent(Event e) throws Exception{
				if(Util.comparaFechas(ventaOriginal.getFechaCaducidad(), ServiceLocator.getVentaPasajesManager().getDateSystem(), Util.OPER_MENOR)){
					DlgMessage.information(Messages.getString("WndDevolucionBoleto.information.noDevolucionBoletoCaducado"));
					return;
//				}else if(cmbDevPorcentaje.getSelectedIndex()==0){
//					DlgMessage.information(Messages.getString("WndDevolucionBoleto.information.noPorcentajeDevolucion"));
//					return;
				}else if (!(cmbAgenciaRemota.getSelectedItem().getValue() instanceof Agencia)){
					DlgMessage.information(Messages.getString("wndOperacionesRemotas.information.noAgenciaRemota"), cmbAgenciaRemota);
					return;
				}else if (!(cmbUsuarioRemoto.getSelectedItem().getValue() instanceof Usuario)){
					DlgMessage.information(Messages.getString("wndOperacionesRemotas.information.noUsuarioRemoto"), cmbUsuarioRemoto);
					return;
				}else if(txtMotivo.getText().trim().equals("")){
					DlgMessage.information(Messages.getString("WndDevolucionBoleto.information.noMotivoDevolucion"), txtMotivo);
					return;
//				}else if(chbxExtempo.isChecked()){
//					if(txtPasAutorizador.getText().trim().isEmpty()){
//						DlgMessage.information(Messages.getString("wndOperacionesRemotas.information.noPasswordAutorizador"), txtDevMotivo);
//						return;
//					}else{
//						//Valida password autorizador
//						TreeMap<String, Object>criteriosBusqueda=new TreeMap<String,Object>();
//						criteriosBusqueda.put("password", txtPasAutorizador.getText().trim());
//						criteriosBusqueda.put("estadoRegistro", Constantes.VALUE_ACTIVO);
//						List<AutorizadorCortesia> lstAutCort= ServiceLocator.getAutorizadorCortesiaManager().buscarPorX(criteriosBusqueda, null);
//						if(lstAutCort.size()==0){
//							DlgMessage.information(Messages.getString("wndOperacionesRemotas.information.noPasswordAutorizadorIncorrect"), txtDevMotivo);
//							return;
//						}
//					}
				}else if(ServiceLocator.getDetalleManifiestoManager().validarVentaManifiesto(Long.valueOf(ventaOriginal.getId()))){
					DlgMessage.information(Messages.getString("Generales.information.manifiestoImpreso"));
					return;
				}

				//Valida que el usuario remoto seleccionado tenga una liquidacion abierta
				final Liquidacion liquidacion=ServiceLocator.getLiquidacionManager().buscarUltimaLiquidacion(((Agencia)cmbAgenciaRemota.getSelectedItem().getValue()).getId(),((Usuario)cmbUsuarioRemoto.getSelectedItem().getValue()).getId(), Constantes.TRUE_VALUE);
				if(liquidacion==null){
					DlgMessage.information(Messages.getString("wndOperacionesRemotas.information.noLiquidacionAbierta"));
					return;
				}

				Messagebox.show(Messages.getString("WndDevolucionBoleto.question.confirmarDevolucion"), DlgMessage.NOMBREAPLICACION, DlgMessage.BTN_YESNO, Messagebox.QUESTION, new EventListener<Event>() {
					@Override
					public void onEvent(Event e){
						try{
							if(e.getName().equals("onYes")){
									boletoDevolver = (VentaPasaje)ventaOriginal.clone();
									boletoDevolver.setId(null);
									boletoDevolver.setVentaPasaje(ventaOriginal);
									boletoDevolver.setPenalidad(0.00);
//									boletoDevolver.setImportePagado(dblDevImporte.getValue());
									boletoDevolver.setImportePagadoEfectivo(0.0);
									boletoDevolver.setImportePagadoTarjeta(0.0);
									boletoDevolver.setTipoMovimiento(new TipoMovimiento(Constantes.ID_TIPMOV_DEVOLUCION));
									boletoDevolver.setAgencia((Agencia)cmbAgenciaRemota.getSelectedItem().getValue());
									boletoDevolver.setUsuario((Usuario)cmbUsuarioRemoto.getSelectedItem().getValue());
									boletoDevolver.setEstadoRegistro(Constantes.VALUE_ACTIVO);
									boletoDevolver.setObservaciones(txtMotivo.getText().toUpperCase());
									boletoDevolver.setFechaLiquidacion(liquidacion.getFechaLiquidacion());
									boletoDevolver.setLiquidacion(null);
									boletoDevolver.setFechaTransferencia(null);

									/*Buscando el tipo de nota de credito a aplicar*/
									if(boletoDevolver.getTipoComprobante().getId().intValue()==Constantes.ID_TIPCOM_BOLETA_VENTA ||
											boletoDevolver.getTipoComprobante().getId().intValue()==Constantes.ID_TIPCOM_FACTURA){
										TipoNota tipoNota=ServiceLocator.getTipoNotaManager().buscarPorId((long)Constantes.ID_TIPNOTA_CREDITO_DEVOLUCION);
										boletoDevolver.setTipoNota(tipoNota);
									}

									UtilData.auditarRegistro(boletoDevolver, getUsuario(), Executions.getCurrent());
									VentaPasaje notaCredito= ServiceLocator.getVentaPasajesManager().devolucionBoleto(boletoDevolver, null);

									/*Realiza el envio de la nota de credito - 11/11/2016 - jabanto*/
									if(notaCredito!=null)
										WSFE.sendNota(notaCredito);

									DlgMessage.information(Messages.getString("WndDevolucionBoleto.information.exitoDevolucion")+" "+boletoDevolver.getNumeroControl());
									wndDevolucion.onClose();
									buscarVentas();
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
		row.appendChild(button);
		button = new Button("Cancelar", "resources/mp_cancelarEnabled.png");
		button.addEventListener(Events.ON_CLICK, new EventListener<Event>() {
			@Override
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

		wndDevolucion = win;
		this.appendChild(wndDevolucion);
		wndDevolucion.setMode(MODAL);
	}

	private void llenarTiposDevolucion(Combobox cmbPorcentajeDevolucion){
		/*Todas las devoluciones son al 100% - 28/10/2016 - jabanto*/
		Comboitem  item = new Comboitem(DEVOLUCION_100);
		item.setValue(100);
		cmbPorcentajeDevolucion.appendChild(item);
		cmbPorcentajeDevolucion.setSelectedIndex(0);
	}

	private void createVentanaPostergacion(final VentaPasaje postergacion)throws Exception{
		final VentaPasaje postergacionOrg=(VentaPasaje) postergacion.clone();

		Caption caption = null;
		Groupbox groupbox = new Groupbox();
		Grid grid = new Grid();
		Columns columns = new Columns();
		Column column = null;
		Rows rows = new Rows();
		Row row = null;
		Label label = null;
		Textbox text = null;

		final Window win = new Window("", "normal", false);
		win.setWidth("700px");
		caption = new Caption("POSTERGACIÓN", "resources/mp_calendarEnabled.png");
		win.appendChild(caption);

		groupbox.setClosable(false);
		caption = new Caption("Situación Actual");
		caption.setStyle("color:#C03131");
		groupbox.appendChild(caption);

		/*column 1 */
		column=new Column();
		column.setWidth("100px");
		column.setAlign("right");
		columns.appendChild(column);
		/*column 2 */
		column=new Column();
		column.setWidth("40%px");
		columns.appendChild(column);
		/*column 3 */
		column=new Column();
		column.setWidth("120px");
		column.setAlign("right");
		columns.appendChild(column);
		/*column 4 */
		column=new Column();
		columns.appendChild(column);

		grid.appendChild(columns);

		row=new Row();
		label=new Label("ITINERARIO :");
		row.appendChild(label);
		txtPosSituActuItinerario=new Textbox(postergacionOrg.getItinerario().getId().toString());
		txtPosSituActuItinerario.setStyle(styleActivo_11px);
		txtPosSituActuItinerario.setReadonly(true);
		txtPosSituActuItinerario.setWidth("100px");
		row.appendChild(txtPosSituActuItinerario);
		rows.appendChild(row);

		label=new Label("ASIENTO :");
		row.appendChild(label);
		text=new Textbox(postergacionOrg.getNumeroAsiento().toString());
		text.setStyle(styleActivo_11px);
		text.setReadonly(true);
		text.setWidth("50px");
		row.appendChild(text);
		rows.appendChild(row);

		row=new Row();
		label=new Label("PASAJERO :");
		row.appendChild(label);
		text=new Textbox(postergacionOrg.getPasajero().toString());
		text.setReadonly(true);
		text.setWidth("210px");
		row.appendChild(text);
		rows.appendChild(row);

		label=new Label("CLIENTE :");
		row.appendChild(label);
		text=new Textbox(postergacionOrg.getCliente()!=null?postergacionOrg.getCliente().toString():"");
		text.setReadonly(true);
		text.setWidth("210px");
		row.appendChild(text);
		rows.appendChild(row);

		row=new Row();
		label=new Label("NRO. BOLETO :");
		row.appendChild(label);
		text=new Textbox(postergacionOrg.getNumeroBoleto());
		text.setStyle(styleActivo_11px);
		text.setReadonly(true);
		text.setWidth("150px");
		row.appendChild(text);
		rows.appendChild(row);

		label=new Label("NRO. CONTROL :");
		row.appendChild(label);
		text=new Textbox(postergacionOrg.getNumeroControl());
		text.setStyle(styleActivo_11px);
		text.setReadonly(true);
		text.setWidth("150px");
		row.appendChild(text);
		rows.appendChild(row);

		row=new Row();
		label=new Label("RUTA :");
		row.appendChild(label);
		txtPosSituActuRuta=new Textbox(postergacionOrg.getRuta().toString());
		txtPosSituActuRuta.setStyle(styleActivo_11px);
		txtPosSituActuRuta.setReadonly(true);
		txtPosSituActuRuta.setWidth("150px");
		row.appendChild(txtPosSituActuRuta);
		rows.appendChild(row);

		label=new Label("SERVICO :");
		row.appendChild(label);
		text=new Textbox(postergacionOrg.getServicio().getDenominacion());
		text.setStyle(styleActivo_11px);
		text.setReadonly(true);
		text.setWidth("150px");
		row.appendChild(text);
		rows.appendChild(row);

		row=new Row();
		label=new Label("PTO. EMBARQUE :");
		row.appendChild(label);
		text=new Textbox(postergacionOrg.getAgenciaPartida().getNombreCorto());
		text.setStyle(styleActivo_11px);
		text.setReadonly(true);
		text.setWidth("150px");
		row.appendChild(text);
		rows.appendChild(row);

		label=new Label("FECHA HORA SALIDA :");
		row.appendChild(label);
		text=new Textbox(Constantes.FORMAT_DATE.format(postergacionOrg.getFechaPartida())+" "+postergacionOrg.getHoraPartida());
		text.setStyle(styleActivo_11px);
		text.setReadonly(true);
		text.setWidth("100px");
		row.appendChild(text);
		rows.appendChild(row);

		grid.appendChild(rows);
		groupbox.appendChild(grid);
		win.appendChild(groupbox);

		//NUEVOS DATOS DE LA VENTA
		groupbox=new Groupbox();
		grid=new Grid();
		groupbox.setClosable(false);
		caption = new Caption("Nuevos datos de la Venta");
		caption.setStyle("color:#C03131");
		groupbox.appendChild(caption);

		/*column 1 */
		columns=new Columns();
		column=new Column();
		column.setWidth("100px");
		column.setAlign("right");
		columns.appendChild(column);
		/*column 2 */
		column=new Column();
		column.setWidth("250px");
		columns.appendChild(column);
		/*column 3 */
		column=new Column();
		column.setWidth("90px");
		column.setAlign("right");
		columns.appendChild(column);
		/*column 4 */
		column=new Column();
		columns.appendChild(column);

		grid.appendChild(columns);

		rows=new Rows();
		row=new Row();
		label=new Label("ITINERARIO :");
		row.appendChild(label);
		txtItinerarioPostergado =new Textbox();
		Hbox hbox= new Hbox();
		hbox.setAlign("center");
		txtItinerarioPostergado.setStyle(styleActivo_11px);
		txtItinerarioPostergado.setReadonly(true);
		txtItinerarioPostergado.setWidth("50px");
		hbox.appendChild(txtItinerarioPostergado);
		final Image imgItinerario =new Image("resources/mp_buscarEnabled.png");
		imgItinerario.setStyle("cursor:pointer");
		imgItinerario.setTooltiptext("Seleccionar itinerario");
		hbox.appendChild(imgItinerario);

		Separator separator=new Separator();
		separator.setWidth("25px");
		hbox.appendChild(separator);

		label=new Label("ASIENTO :");
		hbox.appendChild(label);
		txtNumeroAsientoPostergado=new Textbox();
		txtNumeroAsientoPostergado.setStyle(styleActivo_11px);
		txtNumeroAsientoPostergado.setReadonly(true);
		txtNumeroAsientoPostergado.setWidth("50px");
		txtNumeroPisoPostergado=new Textbox();
		txtNumeroPisoPostergado.setVisible(false);
		hbox.appendChild(txtNumeroPisoPostergado);
		hbox.appendChild(txtNumeroAsientoPostergado);

		String pathImg=imgAsiento.getSrc();
		imgAsiento=new Image(pathImg);
		imgAsiento.setStyle("cursor:pointer");
		imgAsiento.setVisible(false);
		hbox.appendChild(imgAsiento);
		row.appendChild(hbox);

		row.setSpans("1,2");
		chkFechaAbierta=new Checkbox("Fecha Abierta");
		row.appendChild(chkFechaAbierta);
		rows.appendChild(row);

		row=new Row();
		label=new Label("PASAJERO :");
		row.appendChild(label);
		text=new Textbox(postergacionOrg.getPasajero().toString());
		text.setReadonly(true);
		text.setWidth("210px");
		row.appendChild(text);
		rows.appendChild(row);

		label=new Label("CLIENTE :");
		row.appendChild(label);
		text=new Textbox(postergacionOrg.getCliente()!=null?postergacionOrg.getCliente().toString():"");
		text.setReadonly(true);
		text.setWidth("210px");
		row.appendChild(text);
		rows.appendChild(row);

		row=new Row();
		label=new Label("NRO. BOLETO :");
		row.appendChild(label);
		txtBoletoPostergado=new Textbox();
		txtBoletoPostergado.setStyle(styleActivo_11px);
		txtBoletoPostergado.setReadonly(true);
		txtBoletoPostergado.setWidth("150px");
		row.appendChild(txtBoletoPostergado);
		rows.appendChild(row);

		label=new Label("RUTA :");
		row.appendChild(label);
		txtRutaPostergado=new Textbox();
		txtRutaPostergado.setStyle(styleActivo_11px);
		txtRutaPostergado.setReadonly(true);
		txtRutaPostergado.setWidth("150px");
		row.appendChild(txtRutaPostergado);
		rows.appendChild(row);

		row=new Row();
		label=new Label("SERVICO :");
		row.appendChild(label);
		txtServicioPostergado=new Textbox();
		txtServicioPostergado.setStyle(styleActivo_11px);
		txtServicioPostergado.setReadonly(true);
		txtServicioPostergado.setWidth("150px");
		row.appendChild(txtServicioPostergado);
		rows.appendChild(row);

		label=new Label("PTO. EMBARQUE :");
		row.appendChild(label);
		cmbEmbarquePostergado=new Combobox();
		cmbEmbarquePostergado.setReadonly(true);
		cmbEmbarquePostergado.setWidth("160px");
		row.appendChild(cmbEmbarquePostergado);
		rows.appendChild(row);

		row=new Row();
		label=new Label("FECHA SALIDA :");
		row.appendChild(label);
		txtSalidaPostergado=new Textbox();
		txtSalidaPostergado.setStyle(styleActivo_11px);
		txtSalidaPostergado.setReadonly(true);
		txtSalidaPostergado.setWidth("150px");
		row.appendChild(txtSalidaPostergado);
		rows.appendChild(row);

		label=new Label("HORA EMBARQUE:");
		row.appendChild(label);
		txtHoraEmbarque=new Textbox();
		txtHoraEmbarque.setStyle(styleActivo_11px);
		txtHoraEmbarque.setReadonly(true);
		txtHoraEmbarque.setWidth("150px");
		row.appendChild(txtHoraEmbarque);
		rows.appendChild(row);

		grid.appendChild(rows);
		groupbox.appendChild(grid);
		win.appendChild(groupbox);

		/* DATOS DEL PAGO*/
		hbox=new Hbox();
		groupbox=new Groupbox();
		groupbox.setWidth("420px");
		grid=new Grid();
		grid.setStyle("border:none");
		groupbox.setClosable(false);
		caption = new Caption("Información del Pago");
		caption.setStyle("color:#C03131");
		groupbox.appendChild(caption);

		/*columns 1*/
		columns=new Columns();
		column=new Column();
		column.setWidth("100px");
		column.setAlign("right");
		columns.appendChild(column);
		/*column 2*/
		column=new Column();
		column.setWidth("70px");
		columns.appendChild(column);
		/*column 3*/
		column=new Column();
		column.setWidth("110px");
		column.setAlign("right");
		columns.appendChild(column);
		/*column 4*/
		column=new Column();
		columns.appendChild(column);

		grid.appendChild(columns);
		rows=new Rows();

		row=new Row();
		label=new Label("MONTO ANTERIOR :");
		row.appendChild(label);
		dblbxMontoAnterior=new Doublebox(postergacionOrg.getImportePagado());
		dblbxMontoAnterior.setWidth("40px");
		dblbxMontoAnterior.setReadonly(true);
		dblbxMontoAnterior.setFormat("##0.00");
		dblbxMontoAnterior.setLocale(Locale.US);
		row.appendChild(dblbxMontoAnterior);

		label=new Label("TIPO COMPROBANTE :");
		row.appendChild(label);
		cmbTipoComprobante=new Combobox();
		cmbTipoComprobante.setDisabled(true);
		UtilData.cargarDataCombo(cmbTipoComprobante, TipoComprobante.class, false);
		Util.seleccionarValorItemCombo(TipoComprobante.class, cmbTipoComprobante, Constantes.ID_TIPCOM_BOLETO_VIAJE);
		row.appendChild(cmbTipoComprobante);
		rows.appendChild(row);

		row=new Row();
		label=new Label("TARIFA :");
		row.appendChild(label);
		dblbxTarifa=new Doublebox(.00);
		dblbxTarifa.setWidth("40px");
		dblbxTarifa.setReadonly(true);
		dblbxTarifa.setLocale(Locale.US);
		dblbxTarifa.setFormat("##0.00");
		row.appendChild(dblbxTarifa);

		label=new Label("FORMA PAGO :");
		row.appendChild(label);
		cmbFormaPago=new Combobox();
		cmbFormaPago.setDisabled(true);
		UtilData.cargarDataCombo(cmbFormaPago, FormaPago.class, false);
		Util.seleccionarValorItemCombo(FormaPago.class, cmbFormaPago, Constantes.ID_FORPAG_CONTADO);
		row.appendChild(cmbFormaPago);
		rows.appendChild(row);

		row=new Row();
		label=new Label("DESCUENTO :");
		row.appendChild(label);
		dblbxDescuento=new Doublebox(.00);
		dblbxDescuento.setWidth("40px");
		dblbxDescuento.setReadonly(true);
		dblbxDescuento.setLocale(Locale.US);
		dblbxDescuento.setFormat("##0.00");
		row.appendChild(dblbxDescuento);

		label=new Label("TIPO FORMA PAGO :");
		row.appendChild(label);
		cmbTipoFormaPago=new Combobox();
		cmbTipoFormaPago.setReadonly(true);
		row.appendChild(cmbTipoFormaPago);
		rows.appendChild(row);

		row=new Row();
		label=new Label("SALDO :");
		row.appendChild(label);
		dblbxSaldo=new Doublebox(.00);
		dblbxSaldo.setWidth("40px");
		dblbxSaldo.setReadonly(true);
		dblbxSaldo.setLocale(Locale.US);
		dblbxSaldo.setFormat("##0.00");
		row.appendChild(dblbxSaldo);

		label=new Label("OPERADOR TARJETA :");
		row.appendChild(label);
		cmbOperadorTarjetaCredito=new Combobox();
		cmbOperadorTarjetaCredito.setReadonly(true);
		cmbOperadorTarjetaCredito.setDisabled(true);
		row.appendChild(cmbOperadorTarjetaCredito);
		rows.appendChild(row);

		row=new Row();
		label=new Label("PENALIDAD :");
		row.appendChild(label);
		dblbxPenalidad=new Doublebox(.00);
		dblbxPenalidad.setWidth("40px");
		dblbxPenalidad.setReadonly(true);
		dblbxPenalidad.setLocale(Locale.US);
		dblbxPenalidad.setFormat("##0.00");
		row.appendChild(dblbxPenalidad);

		label=new Label("TARJETA CRDITO :");
		row.appendChild(label);
		cmbTarjetaCredito=new Combobox();
		cmbTarjetaCredito.setReadonly(true);
		cmbTarjetaCredito.setDisabled(true);
		row.appendChild(cmbTarjetaCredito);
		rows.appendChild(row);

		row=new Row();
		Hbox hbox2= new Hbox();
		row.setSpans("1,3");
		label=new Label("IMPORTE TOTAL :");
		row.appendChild(label);
		dblbxImporteTotal=new Doublebox(.00);
		dblbxImporteTotal.setWidth("40px");
		dblbxImporteTotal.setReadonly(true);
		dblbxImporteTotal.setLocale(Locale.US);
		dblbxImporteTotal.setFormat("##0.00");
		hbox2.appendChild(dblbxImporteTotal);
		final Checkbox chbxPagoMixto=new Checkbox("Pago Mixto");
		hbox2.appendChild(chbxPagoMixto);
		row.appendChild(hbox2);
		rows.appendChild(row);

		row=new Row();
		row.setSpans("1,3");
		final Label lblImporteEfectivo=new Label("IMPORTE FECTIVO :");
		lblImporteEfectivo.setVisible(false);
		row.appendChild(lblImporteEfectivo);
		final Doublebox dblbxImporteEfectivo=new Doublebox(.00);
		dblbxImporteEfectivo.setWidth("40px");
		dblbxImporteEfectivo.setVisible(false);
		dblbxImporteEfectivo.setLocale(Locale.US);
		dblbxImporteEfectivo.setFormat("##0.00");
		row.appendChild(dblbxImporteEfectivo);
		rows.appendChild(row);

		row=new Row();
		row.setSpans("1,3");
		final Label lblImporteTarjeta=new Label("IMPORTE TARJETA :");
		lblImporteTarjeta.setVisible(false);
		row.appendChild(lblImporteTarjeta);
		final Doublebox dblbxImporteTarjeta=new Doublebox(.00);
		dblbxImporteTarjeta.setWidth("40px");
		dblbxImporteTarjeta.setVisible(false);
		dblbxImporteTarjeta.setLocale(Locale.US);
		dblbxImporteTarjeta.setFormat("##0.00");
		row.appendChild(dblbxImporteTarjeta);
		rows.appendChild(row);


		grid.appendChild(rows);
		groupbox.appendChild(grid);
		hbox.appendChild(groupbox);

		/* DATOS DE LA AGENCIA REMOTA*/
		Vbox vbox=new Vbox();
		groupbox=new Groupbox();
		rows=new Rows();
		grid=new Grid();
		groupbox.setClosable(false);
		caption = new Caption("Información de la Agencia Remota");
		caption.setStyle("color:#C03131");
		groupbox.appendChild(caption);

		row=new Row();
		label=new Label("AGENCIA");
		row.appendChild(label);
		rows.appendChild(row);

		row=new Row();
		final Combobox cmbAgenciaRemota =new Combobox();
		cmbAgenciaRemota.setWidth("240px");
		UtilData.cargarAgenciaXtipoAgencia(cmbAgenciaRemota, Constantes.ID_TIPAGE_TEPSA, false);
		cmbAgenciaRemota.setSelectedIndex(0);
		row.appendChild(cmbAgenciaRemota);
		rows.appendChild(row);

		row=new Row();
		label=new Label("USUARIO");
		row.appendChild(label);
		rows.appendChild(row);

		row=new Row();
		final Combobox cmbUsuarioRemoto=new Combobox();
		cmbUsuarioRemoto.setWidth("240px");
		cmbUsuarioRemoto.setReadonly(true);
		cmbUsuarioRemoto.setDisabled(true);
		row.appendChild(cmbUsuarioRemoto);
		rows.appendChild(row);


		row=new Row();
		label=new Label("ESPECIE VALORADA");
		row.appendChild(label);
		rows.appendChild(row);

		row=new Row();
		bndbxUsuarioHardware=new Bandbox();
		bndbxUsuarioHardware.setDisabled(true);
		bndbxUsuarioHardware.setMold("rounded");
		bndbxUsuarioHardware.setAutodrop(true);
		bndbxUsuarioHardware.setWidth("240px");
		bndbxUsuarioHardware.setStyle("font-size:9px");
		bndbxUsuarioHardware.setReadonly(true);
		Bandpopup bandpopup=new Bandpopup();
		lbxUsuarioHardware=new Listbox();
		lbxUsuarioHardware.setWidth("295px");
		Listhead listhead=new Listhead();
		Listheader listheader=new Listheader("NOMBRE PC", "", "170px");
		listhead.appendChild(listheader);
		listheader=new Listheader("SERIE", "", "50px");
		listhead.appendChild(listheader);
		listheader=new Listheader("CORRELATIVO", "", "75px");
		listhead.appendChild(listheader);
		lbxUsuarioHardware.appendChild(listhead);
		bandpopup.appendChild(lbxUsuarioHardware);
		bndbxUsuarioHardware.appendChild(bandpopup);
		row.appendChild(bndbxUsuarioHardware);
		rows.appendChild(row);

		grid.appendChild(rows);
		groupbox.appendChild(grid);

		Div div=new Div();
		div.setAlign("right");
		hbox2=new Hbox();
		hbox2.setAlign("center");
		Button btnContinuar=new Button("Continuar", "/resources/mp_aceptarEnabled.png");
		btnContinuar.setHeight("27px");
		hbox2.appendChild(btnContinuar);

		Button btnCancelar=new Button("Cancelar","/resources/mp_cerrar.png");
		btnCancelar.setHeight("27px");
		hbox2.appendChild(btnCancelar);
		div.appendChild(hbox2);

		vbox.appendChild(groupbox);
		vbox.appendChild(div);
		hbox.appendChild(vbox);
		win.appendChild(hbox);

		/* Carga el tipo de forma de pago*/
		onLoadTipoFormaPago(cmbTipoFormaPago,cmbFormaPago,cmbOperadorTarjetaCredito, cmbTarjetaCredito);
		Util.seleccionarValorItemCombo(TipoFormaPago.class, cmbTipoFormaPago, Constantes.ID_TIPFORPAG_EFECTIVO);

		/* Cuando el usuario selelcciona el asiento */
		imgAsiento.addEventListener(Events.ON_CLICK,new EventListener<Event>() {
			@Override
			public void onEvent(Event event) throws Exception {
				enlazarMapaBus(imgAsiento);
			}
		});

		/* Cuando selecciona el tipo forma de pago */
		cmbTipoFormaPago.addEventListener(Events.ON_CHANGE,new EventListener<Event>() {
			@Override
			public void onEvent(Event event) throws Exception {
				onValidateTipoFormaPago(cmbTipoFormaPago,cmbOperadorTarjetaCredito,cmbTarjetaCredito);
			}
		});

		/* Cuando selecciona el operador tarjeta de credito*/
		cmbOperadorTarjetaCredito.addEventListener(Events.ON_CHANGE,new EventListener<Event>() {
			@Override
			public void onEvent(Event event) throws Exception {
				onLoadTarjetas(cmbTarjetaCredito,cmbOperadorTarjetaCredito);
			}
		});

		/* Cuando selecciona pago mixo*/
		chbxPagoMixto.addEventListener(Events.ON_CHECK, new EventListener<Event>() {
			@Override
			public void onEvent(Event event) throws Exception {
				lblImporteEfectivo.setVisible(chbxPagoMixto.isChecked());
				lblImporteTarjeta.setVisible(chbxPagoMixto.isChecked());
				dblbxImporteEfectivo.setVisible(chbxPagoMixto.isChecked());
				dblbxImporteTarjeta.setVisible(chbxPagoMixto.isChecked());
				dblbxImporteEfectivo.setValue(.00);
				dblbxImporteTarjeta.setValue(.00);
			}
		});


		/** OPCIONES REMOTAS*/
		/* Cuando recive el foco el contros cmbAgenciaRemota*/
		cmbAgenciaRemota.addEventListener(Events.ON_FOCUS,new EventListener<Event>() {
			@Override
			public void onEvent(Event event) throws Exception {
				cmbAgenciaRemota.select();
			}
		});

		/* Cuando se pulsa enter*/
		cmbAgenciaRemota.addEventListener(Events.ON_OK,new EventListener<Event>() {
			@Override
			public void onEvent(Event event) throws Exception {
				Util.setFocus(cmbUsuarioRemoto);
			}
		});

		/* Cuando selecciona la agencia remota*/
		cmbAgenciaRemota.addEventListener(Events.ON_CHANGE, new EventListener<Event>() {
			@Override
			public void onEvent(Event event) throws Exception {
				cmbUsuarioRemoto.setDisabled(true);
				bndbxUsuarioHardware.setDisabled(true);
				Util.limpiarCombobox(cmbUsuarioRemoto);
				cmbUsuarioRemoto.setText("");
				bndbxUsuarioHardware.setText("");
				txtBoletoPostergado.setText("");
				if(cmbAgenciaRemota.getSelectedIndex()>0){
					UtilData.cargarUsuariosLiquidacion(cmbUsuarioRemoto,((Agencia)cmbAgenciaRemota.getSelectedItem().getValue()).getId(),Constantes.TRUE_VALUE,false);
					loadUsuarioHardware(cmbAgenciaRemota, cmbUsuarioRemoto,txtBoletoPostergado);
					cmbUsuarioRemoto.setFocus(true);

					cmbUsuarioRemoto.setDisabled(false);
					bndbxUsuarioHardware.setDisabled(false);
				}else
					cmbAgenciaRemota.setSelectedIndex(0);
			}
		});

		/* Cuando el usuario cierra la venta*/
		btnCancelar.addEventListener(Events.ON_CLICK,new EventListener<Event>() {
			@Override
			public void onEvent(Event event) throws Exception {
				liberarAsientos();
				win.onClose();
			}
		});

		dblbxImporteEfectivo.addEventListener(Events.ON_FOCUS,new EventListener<Event>() {
			@Override
			public void onEvent(Event event) throws Exception {
				dblbxImporteEfectivo.select();
			}
		});

		dblbxImporteTarjeta.addEventListener(Events.ON_FOCUS,new EventListener<Event>() {
			@Override
			public void onEvent(Event event) throws Exception {
				dblbxImporteTarjeta.select();
			}
		});

		btnContinuar.addEventListener(Events.ON_CLICK,new EventListener<Event>() {
			@Override
			public void onEvent(Event event) throws Exception {
				try{
					if(!chkFechaAbierta.isChecked()){
						if(detalleItinerario==null)
							throw new ItinerarioException(ItinerarioException.NO_SELECT);
						else if(txtNumeroAsientoPostergado.getText().trim().equals(""))
							throw new NumeroAsientoNullException();
						if(txtBoletoPostergado.getText().trim().equals(""))
							throw new NumeroBoletoNullException();
						else if(!(cmbEmbarquePostergado.getSelectedItem().getValue() instanceof ItinerarioAgenciaPartida))
							throw new ItinerarioException(ItinerarioException.AGENCIA_PARTIDA_NULL);
						else if(txtItinerarioPostergado.getText().trim().isEmpty())
							throw new ItinerarioException(ItinerarioException.NO_SELECT);
					}else if (!(cmbTipoComprobante.getSelectedItem().getValue() instanceof TipoComprobante))
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

					//Valida datos de la agencia remota
					if(!(cmbAgenciaRemota.getSelectedItem().getValue() instanceof Agencia)){
						DlgMessage.information(Messages.getString("wndOperacionesRemotas.information.noAgenciaRemota"),cmbAgenciaRemota);
						return;
					}else if (!(cmbUsuarioRemoto.getSelectedItem().getValue() instanceof Usuario)){
						DlgMessage.information(Messages.getString("wndOperacionesRemotas.information.noUsuarioRemoto"),cmbUsuarioRemoto);
						return;
					}else if (bndbxUsuarioHardware.getText().trim().isEmpty()){
						DlgMessage.information(Messages.getString("wndOperacionesRemotas.information.noEspecieValorada"));
						return;
					}


//					if(chkCambioNombre.isChecked() && chkCambioNombre.isDisabled()==false){
//						throw new PostergacionNoCambioNombreException();
//					}else if (chkCambioRazonsocial.isChecked() && chkCambioRazonsocial.isDisabled()==false){
//						throw new PostergacionNoCambioRazonSozialException();
//					}

					if(chbxPagoMixto.isChecked()){
						if(dblbxImporteEfectivo.getValue()<=0.0 || dblbxImporteTarjeta.getValue()<=0.0)
							throw new ImporteMixtoNullException(ImporteMixtoNullException.IMPORTE_MIXTO_CERO);
					}

					/*	Validando que el monto en efectivo + el monto en tarjeta sumen el importe pagado	*/
					if(chbxPagoMixto.isChecked()){
						if(dblbxImporteTotal.getValue().doubleValue()!=(dblbxImporteEfectivo.getValue().doubleValue()+dblbxImporteTarjeta.getValue().doubleValue()))
							throw new ImporteMixtoNullException(ImporteMixtoNullException.IMPORTE_MIXTO_NOT_EQUALS);
					}

					//Valida que el usuario remoto seleccionado tenga una liquidacion abierta
					final Liquidacion liquidacion=ServiceLocator.getLiquidacionManager().buscarUltimaLiquidacion(((Agencia)cmbAgenciaRemota.getSelectedItem().getValue()).getId(),((Usuario)cmbUsuarioRemoto.getSelectedItem().getValue()).getId(), Constantes.TRUE_VALUE);
					if(liquidacion==null){
						DlgMessage.information(Messages.getString("wndOperacionesRemotas.information.noLiquidacionAbierta"));
						return;
					}

					Agencia agenciaRemota=(Agencia)cmbAgenciaRemota.getSelectedItem().getValue();
					Usuario usuarioRemoto=(Usuario)cmbUsuarioRemoto.getSelectedItem().getValue();
					UsuarioHardware usuarioHardwareRemoto=((ControlEspecieValorada)lbxUsuarioHardware.getSelectedItem().getValue()).getUsuarioHardware();


					postergacion.setVentaPasaje(postergacionOrg);
					FormaPago formaPago = (FormaPago)cmbFormaPago.getSelectedItem().getValue();
					postergacion.setFormaPago(formaPago);
					TipoComprobante tipoComprobante = (TipoComprobante)cmbTipoComprobante.getSelectedItem().getValue();
					postergacion.setTipoComprobante(tipoComprobante);
					TipoFormaPago tipoFormaPago = (TipoFormaPago)cmbTipoFormaPago.getSelectedItem().getValue();
					postergacion.setTipoFormaPago(tipoFormaPago);
					if(cmbTarjetaCredito.getSelectedItem()!=null && cmbTarjetaCredito.getSelectedItem().getValue() instanceof TarjetaCredito){
						TarjetaCredito tarjetaCredito = (TarjetaCredito)cmbTarjetaCredito.getSelectedItem().getValue();
						postergacion.setTarjetaCredito(tarjetaCredito);
					}else
						postergacion.setTarjetaCredito(null);

					postergacion.setId(null);
					postergacion.setNumeroBoleto(txtBoletoPostergado.getText().equals("")?null:txtBoletoPostergado.getText());
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
					}else{
						postergacion.setNumeroAsiento(Integer.valueOf(txtNumeroAsientoPostergado.getText()));
						postergacion.setFechaPartida(detalleItinerario.getFechaPartida());
						postergacion.setHoraPartida(detalleItinerario.getHoraPartida());
						postergacion.setFechaLlegada(detalleItinerario.getFechaLlegada());
						postergacion.setHoraLllegada(detalleItinerario.getHoraLlegada());
						ItinerarioAgenciaPartida itinerarioAgenciaPartida = (ItinerarioAgenciaPartida)cmbEmbarquePostergado.getSelectedItem().getValue();
						postergacion.setAgenciaPartida(itinerarioAgenciaPartida.getAgencia());
						postergacion.setAgenciaLlegada(detalleItinerario.getAgenciaLlegada()); //opostergacion.getAgenciaLlegada());
						postergacion.setNumeroPiso(Integer.valueOf(txtNumeroPisoPostergado.getText()));
						postergacion.setEsFechaAbierta(Constantes.FALSE_VALUE);

						//Valida si se trata de una postergacion
//						if(!(txtItinerarioActual.getText().equals(txtItinerarioPostergado.getText()))){
						postergacion.setTipoMovimiento(new TipoMovimiento(Constantes.ID_TIPMOV_POSTERGACION));
//						}
					}

					//Coloca como observaciones una glosa y es que es un cambio de nombtre o razón social
//					if(chkCambioNombre.isChecked())
//						postergacion.setObservaciones("CAMBIO DE NOMBRE");
//					if(chkCambioRazonsocial.isChecked()){
//						if(postergacion.getObservaciones()==null)
//							postergacion.setObservaciones("CAMBIO DE DE RAZÓN SOCIAL");
//						else{
//							String observacion=postergacion.getObservaciones()+";"+"CAMBIO DE DE RAZÓN SOCIAL";
//							postergacion.setObservaciones(observacion);
//						}
//					}

					postergacion.setSecuencial(secuencial+1);
					postergacion.setTarifa(dblbxTarifa.getValue());
					postergacion.setRecargo(postergacion.getRecargo());
					postergacion.setDescuento(dblbxDescuento.getValue());
					postergacion.setPenalidad(dblbxPenalidad.getValue());
					postergacion.setAcuenta(0.0);
					postergacion.setImportePagado(dblbxImporteTotal.getValue());
					postergacion.setImportePagadoEfectivo(dblbxImporteEfectivo.getValue());
					postergacion.setImportePagadoTarjeta(dblbxImporteTarjeta.getValue());
					postergacion.setTipoTransaccion(Constantes.TIPO_OPERACION_VENTA);
					postergacion.setFechaLiquidacion(liquidacion.getFechaLiquidacion());
					postergacion.setAgencia(agenciaRemota);
					postergacion.setUsuario(usuarioRemoto);
					postergacion.setCanalVenta(usuarioHardwareRemoto.getCanalVenta());
					postergacion.setNumeroControl("-----");
					postergacion.setLiquidacion(null);
					postergacion.setFechaTransferencia(null);
					postergacion.setUsuarioHardware(usuarioHardwareRemoto);
					postergacion.setNumeroBoletoAnterior(postergacion.getVentaPasaje().getNumeroBoleto());
					postergacion.setEstadoDocumento(Constantes.ESTADO_DOCUMENTO_PAGADO);
					UtilData.auditarRegistro(postergacion,getUsuario(), Executions.getCurrent());

//					Messagebox.show(Messages.getString("WndPostergacion.information.confirmacionPostergacion"), DlgMessage.NOMBREAPLICACION, DlgMessage.BTN_YESNO, Messagebox.QUESTION, new EventListener<Event>() {
//						@Override
//						public void onEvent(Event e){
//							try{
//								if(e.getName().equals("onYes")){
//									int result = ServiceLocator.getVentaPasajesManager().postergarBoleto(postergacion,true);
//									if(result == Constantes.CORRECT){
//										DlgMessage.information(Messages.getString("WndPostergacion.information.exitoPostergacion")+postergacion.getNumeroControl());
//										buscarVentas();
//										buscarVentas();
//										win.onClose();
//									}
//								}
//							}catch(CapacityExceedsException ceex){
//								DlgMessage.information(Messages.getString("WndVentaReserva.information.changeCapacidad"));
//							}catch(DuplicateSeatException dsex){
//								DlgMessage.information(Messages.getString("WndVentaReserva.information.asientoVendido"));
//							}catch(NumeroBoletoDuplicadoException nbdex){
//								DlgMessage.information(Messages.getString("WndVentaReserva.information.numeroBoletoVendido"));
//							}catch(Exception ex){
//								ex.printStackTrace();
//								DlgMessage.error(this.getClass().getName()+" "+ex.getMessage());
//							}
//						}
//					});

				}catch (ItinerarioException i){
					if(i.getTipo().intValue()==ItinerarioException.NO_SELECT){
						DlgMessage.information(Messages.getString("WndPostergacion.information.noItinerarioSeleccionado"));
						imgItinerario.setFocus(true);
					}else if (i.getTipo().intValue()==ItinerarioException.AGENCIA_PARTIDA_NULL){
						DlgMessage.information(Messages.getString("WndPostergacion.information.noAgenciaPartidaSeleccionada"));
						cmbEmbarquePostergado.setFocus(true);
					}
				}catch(NumeroAsientoNullException nanex){
					DlgMessage.information(Messages.getString("WndPostergacion.information.noAsientoSeleccionado"));
					imgAsiento.setFocus(true);
				}catch(NumeroBoletoNullException nbnex){
					DlgMessage.information(Messages.getString("WndPostergacion.information.noNumeroBoleto"));
				}catch(TipoComprobanteNullException tcnex){
					DlgMessage.information(Messages.getString("WndPostergacion.information.noTipoComprobante"));
					cmbTipoComprobante.setFocus(true);
				}catch(FormaPagoNullException fpnex){
					DlgMessage.information(Messages.getString("WndPostergacion.information.noFormaPago"));
					cmbFormaPago.setFocus(true);
				}catch(TipoFormaPagoNullException tfpnex){
					DlgMessage.information(Messages.getString("WndPostergacion.information.noTipoFormaPago"));
					cmbTipoFormaPago.setFocus(true);
				}catch(OperadorTarjetaCreditoNullException otcnex){
					DlgMessage.information(Messages.getString("WndPostergacion.information.noOperadorTarjetaCredito"));
					cmbOperadorTarjetaCredito.setFocus(true);
				}catch(TarjetaCreditoNullException tcnex){
					DlgMessage.information(Messages.getString("WndPostergacion.information.noTarjetaCredito"));
					cmbTarjetaCredito.setFocus(true);
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
		});

		chkFechaAbierta.addEventListener(Events.ON_CHECK,new EventListener<Event>() {
			@Override
			public void onEvent(Event event) throws Exception {
				/* Limpia controles de la venta*/
				liberarAsientos();
				txtItinerarioPostergado.setText("");
				txtNumeroAsientoPostergado.setText("");
				txtRutaPostergado.setText("");
				imgAsiento.setVisible(false);
				txtServicioPostergado.setText("");
				Util.limpiarCombobox(cmbEmbarquePostergado);
				cmbEmbarquePostergado.setText("");
				txtSalidaPostergado.setText("");
				txtHoraEmbarque.setText("");

				/* Si es una fecha abierta*/
				if(chkFechaAbierta.isChecked()){
					imgItinerario.setVisible(false);
					imgAsiento.setVisible(false);
					txtRutaPostergado.setText(postergacionOrg.getRuta().toString());
					txtServicioPostergado.setText(postergacionOrg.getServicio().getDenominacion());
					cmbEmbarquePostergado.setDisabled(true);
					dblbxTarifa.setValue(postergacionOrg.getTarifa());
					dblbxDescuento.setValue(postergacionOrg.getDescuento());

					dblbxSaldo.setValue(dblbxTarifa.getValue()-dblbxMontoAnterior.getValue()-dblbxDescuento.getValue()>0.0?dblbxTarifa.getValue()-dblbxMontoAnterior.getValue()-dblbxDescuento.getValue():0.0);
//					if(chkCambioNombre.isChecked() || chkCambioRazonsocial.isChecked())
//						dblbxPenalidad.setValue(Constantes.PENALIDAD_POSTERGACION+Constantes.PENALIDAD_CAMBIO_NOMBRE);
//					else
						dblbxPenalidad.setValue(Constantes.PENALIDAD_POSTERGACION);
					dblbxImporteTotal.setValue(dblbxSaldo.getValue()+dblbxPenalidad.getValue());

					/*	Seteando el objeto postergacion con los nuevos datos para la venta	*/
					postergacion.setItinerario(new Itinerario(Long.valueOf(1)));
					postergacion.setServicio(postergacionOrg.getServicio());
					postergacion.setRuta(postergacionOrg.getRuta());
					cmbAgenciaRemota.setFocus(true);
				}else{
					imgItinerario.setVisible(true);
					imgAsiento.setVisible(false);
					cmbEmbarquePostergado.setDisabled(false);

					Double penalidad=dblbxPenalidad.getValue()!=null?dblbxPenalidad.getValue():0;
					dblbxPenalidad.setValue(penalidad-Constantes.PENALIDAD_POSTERGACION);

//					if(!(chkCambioNombre.isDisabled())){
//						chkCambioNombre.setChecked(false);
//						chkCambioNombre_onCheck();
//					}
//					if(!(chkCambioRazonsocial.isDisabled())){
//						chkCambioRazonsocial.setChecked(false);
//						chkCambioRazonSocial_onCheck();
//					}
					calcularPagos();
					dblbxTarifa.setValue(0);
					dblbxDescuento.setValue(0);
				}
				detalleItinerario = null;
			}
		});


		enlazarItinerario(imgItinerario);
		wndPostergacion = win;
		this.appendChild(wndPostergacion);
		wndPostergacion.setMode(MODAL);
	}


//	/**
//	 * Crea la venta para la reimpresion
//	 * @return
//	 * @throws Exception
//	 */
//	private Window createVentanaReimpresion(final VentaPasaje ventaOriginal)throws Exception{
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
//		caption = new Caption("REIMPRESION DE BOLETO", "resources/menu/menu_reimprimir.png");
//		win.appendChild(caption);
//
//		groupbox = new Groupbox();
//		groupbox.setClosable(false);
//		caption = new Caption("Información del Boleto");
//		caption.setStyle("color:#C03131");
//		groupbox.appendChild(caption);
//
//		/*	Columna 1	*/
//		column = new Column();
//		column.setAlign("right");
//		columns.appendChild(column);
//		/*	Columna 2	*/
//		column = new Column();
//		column.setWidth("150px");
//		columns.appendChild(column);
//		/*	Columna 3	*/
//		column = new Column();
//		column.setWidth("80px");
//		column.setAlign("right");
//		columns.appendChild(column);
//		/*	Columna 4	*/
//		column = new Column();
//		columns.appendChild(column);
//
//		grid.appendChild(columns);
//
//		row = new Row();
//		label = new Label("NUEVO BOLETO :");
//		row.appendChild(label);
//		txtNumeroboletoReimpresion=new Textbox();
//		txtNumeroboletoReimpresion.setReadonly(true);
//		txtNumeroboletoReimpresion.setWidth("80px");
//		txtNumeroboletoReimpresion.setStyle(styleActivo_11px);
//		row.appendChild(txtNumeroboletoReimpresion);
//		label = new Label("FECHA VIAJE :");
//		row.appendChild(label);
//		text = new Textbox(ventaOriginal.getFechaPartida()==null?"":Util.DatetoString(ventaOriginal.getFechaPartida(), Constantes.DATE_FORMAT));
//		text.setReadonly(true);
//		text.setWidth("100px");
//		text.setStyle(styleActivo_11px);
//		row.appendChild(text);
//		rows.appendChild(row);
//
//		row = new Row();
//		label = new Label("NUMERO ASIENTO :");
//		row.appendChild(label);
//		text = new Textbox(ventaOriginal.getNumeroAsiento()==null?"":ventaOriginal.getNumeroAsiento().toString());
//		text.setReadonly(true);
//		text.setWidth("80px");
//		text.setStyle(styleActivo_11px);
//		row.appendChild(text);
//		label = new Label("IMPORTE :");
//		row.appendChild(label);
//		text = new Textbox(Util.toNumberFormat(Constantes.PENALIDAD_REIMPRESION, 2));
//		text.setStyle("align:right");
//		text.setReadonly(true);
//		text.setWidth("100px");
//		text.setStyle(styleActivo_11px);
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
//		text = new Textbox(ventaOriginal.getRuta().toString());
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
//		caption.setStyle("color:#C03131");
//		groupbox.appendChild(caption);
//
//		grid = new Grid();
//		rows = new Rows();
//		columns = new Columns();
//		/*	Columna 1	*/
//		column = new Column();
//		column.setAlign("right");
//		column.setWidth("115px");
//		columns.appendChild(column);
//		/*	Columna 2	*/
//		column = new Column();
//		column.setWidth("125px");
//		columns.appendChild(column);
//		/* Columna 3 */
//		column = new Column();
//		columns.appendChild(column);
//		column.setAlign("right");
//		column.setWidth("100px");
//		columns.appendChild(column);
//		/*	Columna 4	*/
//		column = new Column();
//		columns.appendChild(column);
//
//		grid.appendChild(columns);
//
//		row = new Row();
//		row.setSpans("1,3");
//		label = new Label("TIPO  COMPROBANTE :");
//		row.appendChild(label);
//		cmbTipoComprobante = new Combobox();
//		cmbTipoComprobante.setWidth("110px");
//		cmbTipoComprobante.setDisabled(true);
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
//		cmbFormaPago.setWidth("110px");
//		UtilData.cargarDataCombo(cmbFormaPago, FormaPago.class, false);
//		row.appendChild(cmbFormaPago);
//		rows.appendChild(row);
//
//		label = new Label("TIPO FORMA DE PAGO :");
//		row.appendChild(label);
//		cmbTipoFormaPago = new Combobox();
//		cmbTipoFormaPago.setReadonly(true);
//		cmbTipoFormaPago.addEventListener(Events.ON_CHANGE, new EventListener<Event>() {
//			public void onEvent(Event e){
//				onValidateTipoFormaPago(cmbTipoFormaPago,cmbOperadorTarjetaCredito,cmbTarjetaCredito);
//			}
//		});
//		cmbTipoFormaPago.setWidth("110px");
//		row.appendChild(cmbTipoFormaPago);
//		rows.appendChild(row);
//
//		row = new Row();
//		label = new Label("OPERADOR TARJETA CREDITO :");
//		row.appendChild(label);
//		cmbOperadorTarjetaCredito = new Combobox();
//		cmbOperadorTarjetaCredito.setReadonly(true);
//		cmbOperadorTarjetaCredito.addEventListener(Events.ON_CHANGE, new EventListener<Event>() {
//			public void onEvent(Event e){
//				onLoadTarjetas(cmbTarjetaCredito,cmbOperadorTarjetaCredito);
//			}
//		});
//		cmbOperadorTarjetaCredito.setWidth("110px");
//		cmbOperadorTarjetaCredito.setDisabled(true);
//		row.appendChild(cmbOperadorTarjetaCredito);
//		rows.appendChild(row);
//
//		label = new Label("TARJETA CREDITO :");
//		row.appendChild(label);
//		cmbTarjetaCredito = new Combobox();
//		cmbTarjetaCredito.setWidth("110px");
//		cmbTarjetaCredito.setReadonly(true);
//		cmbTarjetaCredito.setDisabled(true);
//		row.appendChild(cmbTarjetaCredito);
//		rows.appendChild(row);
//
//		grid.appendChild(rows);
//		groupbox.appendChild(grid);
//		win.appendChild(groupbox);
//
//		/* Datos remotos */
//		groupbox = new Groupbox();
//		groupbox.setClosable(false);
//		caption = new Caption("Informacion de la Agencia Remota.");
//		caption.setStyle("color:#C03131");
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
//		grid.appendChild(columns);
//
//		row=new Row();
//		label= new Label("AGENCIA (*) :");
//		row.appendChild(label);
//		final Combobox cmbAgenciaRemota=new Combobox();
//		UtilData.cargarAgenciaXtipoAgencia(cmbAgenciaRemota, Constantes.ID_TIPAGE_TEPSA, false);
//		cmbAgenciaRemota.setSelectedIndex(0);
//		cmbAgenciaRemota.setWidth("330px");
//		row.appendChild(cmbAgenciaRemota);
//		rows.appendChild(row);
//
//		row=new Row();
//		label= new Label("USUARIO (*) :");
//		row.appendChild(label);
//		final Combobox cmbUsuarioRemoto=new Combobox();
//		cmbUsuarioRemoto.setWidth("330px");
//		cmbUsuarioRemoto.setDisabled(true);
//		cmbUsuarioRemoto.setReadonly(true);
//		row.appendChild(cmbUsuarioRemoto);
//		rows.appendChild(row);
//
//		row=new Row();
//		label= new Label("ESPECIE VALORADA (*) :");
//		row.appendChild(label);
//		bndbxUsuarioHardware=new Bandbox();
//		bndbxUsuarioHardware.setDisabled(true);
//		bndbxUsuarioHardware.setMold("rounded");
//		bndbxUsuarioHardware.setAutodrop(true);
//		bndbxUsuarioHardware.setWidth("330px");
//		bndbxUsuarioHardware.setStyle("font-size:9px");
//		bndbxUsuarioHardware.setReadonly(true);
//
//		Bandpopup bandpopup=new Bandpopup();
//		lbxUsuarioHardware=new Listbox();
//		lbxUsuarioHardware.setWidth("320px");
//		Listhead listhead=new Listhead();
//		Listheader listheader=new Listheader("NOMBRE PC", "", "170px");
//		listhead.appendChild(listheader);
//		listheader=new Listheader("SERIE", "", "50px");
//		listhead.appendChild(listheader);
//		listheader=new Listheader("CORRELATIVO", "", "80px");
//		listhead.appendChild(listheader);
//		lbxUsuarioHardware.appendChild(listhead);
//		bandpopup.appendChild(lbxUsuarioHardware);
//		bndbxUsuarioHardware.appendChild(bandpopup);
//		row.appendChild(bndbxUsuarioHardware);
//		rows.appendChild(row);
//
//		/* Cuando recive el foco la agencia, selecciona todo el texto */
//		cmbAgenciaRemota.addEventListener(Events.ON_FOCUS,new EventListener<Event>() {
//			@Override
//			public void onEvent(Event event) throws Exception {
//				cmbAgenciaRemota.select();
//			}
//		});
//
//		/* Cuando el usuario pulsa la tecla enter */
//		cmbAgenciaRemota.addEventListener(Events.ON_OK,new EventListener<Event>() {
//			@Override
//			public void onEvent(Event event) throws Exception {
//				cmbUsuarioRemoto.setFocus(true);
//			}
//		});
//
//		/* Cuando El usuario seleciona la agencia Remota*/
//		cmbAgenciaRemota.addEventListener(Events.ON_CHANGE,new EventListener<Event>() {
//			@Override
//			public void onEvent(Event event) throws Exception {
//				cmbUsuarioRemoto.setDisabled(true);
//				bndbxUsuarioHardware.setDisabled(true);
//				Util.limpiarCombobox(cmbUsuarioRemoto);
//				cmbUsuarioRemoto.setText("");
//				bndbxUsuarioHardware.setText("");
//				txtNumeroboletoReimpresion.setText("");
//				if(cmbAgenciaRemota.getSelectedIndex()>0){
//					UtilData.cargarUsuariosLiquidacion(cmbUsuarioRemoto,((Agencia)cmbAgenciaRemota.getSelectedItem().getValue()).getId(),Constantes.TRUE_VALUE,false);
//					loadUsuarioHardware(cmbAgenciaRemota, cmbUsuarioRemoto,txtNumeroboletoReimpresion);
//					cmbUsuarioRemoto.setFocus(true);
//
//					cmbUsuarioRemoto.setDisabled(false);
//					bndbxUsuarioHardware.setDisabled(false);
//				}else
//					cmbAgenciaRemota.setSelectedIndex(0);
//			}
//		});
//
//
//
//		grid.appendChild(rows);
//		groupbox.appendChild(grid);
//		win.appendChild(groupbox);
//
//		Util.seleccionarValorItemCombo(TipoComprobante.class, cmbTipoComprobante, Constantes.ID_TIPCOM_BOLETO_VIAJE);
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
//					if(txtNumeroboletoReimpresion.getText().trim().isEmpty()){
//						DlgMessage.information(Messages.getString("wndOperacionesRemotas.information.noEspecieValorada"),cmbAgenciaRemota);
//						return;
//					}else if(!(cmbTipoFormaPago.getSelectedItem().getValue() instanceof TipoFormaPago))
//						throw new TipoFormaPagoNullException();
//					else if(cmbTipoFormaPago.getSelectedItem().getValue() instanceof TipoFormaPago && cmbTipoFormaPago.getText().equals(TipoFormaPago.TIPO_TARJETA)){
//						if(!(cmbOperadorTarjetaCredito.getSelectedItem().getValue() instanceof OperadorTarjetaCredito))
//							throw new OperadorTarjetaCreditoNullException();
//						else if(!(cmbTarjetaCredito.getSelectedItem().getValue() instanceof TarjetaCredito))
//							throw new TarjetaCreditoNullException();
//					}
//					//Validacion de los datos demotos
//					if(!(cmbAgenciaRemota.getSelectedItem().getValue() instanceof Agencia)){
//						DlgMessage.information(Messages.getString("wndOperacionesRemotas.information.noAgenciaRemota"),cmbAgenciaRemota);
//						return;
//					}else if (!(cmbUsuarioRemoto.getSelectedItem().getValue() instanceof Usuario)){
//						DlgMessage.information(Messages.getString("wndOperacionesRemotas.information.noUsuarioRemoto"),cmbUsuarioRemoto);
//						return;
//					}else if (bndbxUsuarioHardware.getText().trim().isEmpty()){
//						DlgMessage.information(Messages.getString("wndOperacionesRemotas.information.noEspecieValorada"));
//						return;
//					}
//
//					//Valida que el usuario remoto seleccionado tenga una liquidacion abierta
//					final Liquidacion liquidacion=ServiceLocator.getLiquidacionManager().buscarUltimaLiquidacion(((Agencia)cmbAgenciaRemota.getSelectedItem().getValue()).getId(),((Usuario)cmbUsuarioRemoto.getSelectedItem().getValue()).getId(), Constantes.TRUE_VALUE);
//					if(liquidacion==null){
//						DlgMessage.information(Messages.getString("wndOperacionesRemotas.information.noLiquidacionAbierta"));
//						return;
//					}
//
//					Messagebox.show(Messages.getString("WndReimprimirBoleto.question.confirmarReimpresion"), DlgMessage.NOMBREAPLICACION, DlgMessage.BTN_YESNO, Messagebox.QUESTION, new EventListener<Event>() {
//						public void onEvent(Event e){
//							try{
//								if(e.getName().equals("onYes")){
//									Agencia agenciaRemota=(Agencia)cmbAgenciaRemota.getSelectedItem().getValue();
//									Usuario usuarioRemoto=(Usuario)cmbUsuarioRemoto.getSelectedItem().getValue();
//									UsuarioHardware usuarioHardwareRemoto=((ControlEspecieValorada)lbxUsuarioHardware.getSelectedItem().getValue()).getUsuarioHardware();
//
//									VentaPasaje ventaPasajereRef= (VentaPasaje) ventaOriginal.clone();
//									UtilData.auditarRegistro(ventaPasajereRef,true, getUsuario(), Executions.getCurrent());
//									//----->>>
//
//									ventaOriginal.setImportePagadoEfectivo(0.0);
//									ventaOriginal.setImportePagadoTarjeta(0.0);
//									boletoReimprimir = (VentaPasaje)ventaOriginal.clone();
//									ventaOriginal.setId(null);
//									ventaOriginal.setVentaPasaje(null);
//									ventaOriginal.setTipoMovimiento(new TipoMovimiento(Constantes.ID_TIPMOV_ANULACION_SISTEMA));
//									ventaOriginal.setUsuario(usuarioRemoto);
//									UtilData.auditarRegistro(ventaOriginal, getUsuario(), Executions.getCurrent());
//
//									boletoReimprimir.setId(null);
//									boletoReimprimir.setAgencia(agenciaRemota);
//									boletoReimprimir.setUsuario(usuarioRemoto);
//									boletoReimprimir.setFechaLiquidacion(liquidacion.getFechaLiquidacion());
//									boletoReimprimir.setVentaPasaje(ventaPasajereRef);
//									boletoReimprimir.setNumeroBoletoAnterior(ventaOriginal.getNumeroBoleto());
//									boletoReimprimir.setNumeroBoleto(txtNumeroboletoReimpresion.getText());
//									boletoReimprimir.setPenalidad(Constantes.PENALIDAD_REIMPRESION);
//									boletoReimprimir.setImportePagado(Constantes.PENALIDAD_REIMPRESION);
//									boletoReimprimir.setImportePagadoEfectivo(0.0);
//									boletoReimprimir.setImportePagadoTarjeta(0.0);
//									boletoReimprimir.setTipoMovimiento(new TipoMovimiento(Constantes.ID_TIPMOV_REIMPRESION));
//									boletoReimprimir.setUsuarioHardware(usuarioHardwareRemoto);
//									boletoReimprimir.setEstadoRegistro(Constantes.VALUE_ACTIVO);
//									boletoReimprimir.setFormaPago(new FormaPago(Constantes.ID_FORPAG_CONTADO));
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
//									UtilData.auditarRegistro(boletoReimprimir,getUsuario(), Executions.getCurrent());
//									int result = ServiceLocator.getVentaPasajesManager().reimprimirBoleto(ventaOriginal, boletoReimprimir);
//									if(result==Constantes.CORRECT){
//										DlgMessage.information(Messages.getString("WndReimprimirBoleto.information.exitoReimpresion")+" "+boletoReimprimir.getNumeroControl());
//										buscarVentas();
//										wndReimpresion.onClose();
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
//		row.appendChild(button);
//		rows.appendChild(row);
//
//		grid.appendChild(rows);
//		win.appendChild(grid);
//
//		cmbAgenciaRemota.setFocus(true);
//
//		return win;
//	}

	private void enlazarMapaBus(Image image) throws Exception{
//		image.addEventListener(Events.ON_CLICK, new EventListener<Event>() {
//			@Override
//			public void onEvent(Event event) throws Exception {
				if(!txtItinerarioPostergado.getText().trim().equals("")){
					WndMapaBus oWndMapaBus = new WndMapaBus();
					oWndMapaBus.load();
					oWndMapaBus.setVentaPasaje(postergacion);
					oWndMapaBus.setDetalleItinerario(detalleItinerario);
					oWndMapaBus.setUsuarioHardware(getUsuarioHardware());
					oWndMapaBus.setTxtAsientoSeleccionado(txtNumeroAsientoPostergado);
					oWndMapaBus.setTxtPisoSeleccionado(txtNumeroPisoPostergado);
					oWndMapaBus.setSelectAsiento(true);
					oWndMapaBus.initComponents();
					oWndMapaBus.onCreate();
					wndPostergacion.appendChild(oWndMapaBus);
					oWndMapaBus.setMode(MODAL);
					oWndMapaBus.addEventListener(Events.ON_SELECT, new EventListener<Event>() {
						@Override
						public void onEvent(Event e){
							postergacion.setNumeroAsiento(Integer.valueOf(txtNumeroAsientoPostergado.getText()));
							postergacion.setNumeroPiso(Integer.valueOf(txtNumeroPisoPostergado.getText()));

//							if(chkCambioNombre.isChecked() || chkCambioRazonsocial.isChecked())
//								calcularPagos(Constantes.PENALIDAD_POSTERGACION+Constantes.PENALIDAD_CAMBIO_NOMBRE);
//							else
								calcularPagos(Constantes.PENALIDAD_POSTERGACION);

						}
					});
				}
	}

	/**
	 * Permite enlazar los controles a la ventana de selección de Itinerario
	 * @param textboxItinerario :en este Textbox se devolvera el Id del itinerario seleccionado.
	 * @param button :ha este Button se le adjuntara un listener con la llamada a la ventana de selección de itinerario
	 * @see WndItinerario:
	 */
	private void enlazarItinerario(final Image image) {
		image.addEventListener(Events.ON_CLICK, new EventListener<Event>() {
			@Override
			public void onEvent(Event e)throws Exception{
				int p=txtPosSituActuRuta.getText().indexOf("-");
				String origen=txtPosSituActuRuta.getText().substring(0,p).trim();
				String destino=txtPosSituActuRuta.getText().substring(p+1,txtPosSituActuRuta.getText().length()).trim();
				final WndSeleccionaItinerario oWndSeleccionarItinerario = new WndSeleccionaItinerario();
				wndPostergacion.appendChild(oWndSeleccionarItinerario);
				oWndSeleccionarItinerario.onCreate();
				oWndSeleccionarItinerario.setMode(MODAL);
				oWndSeleccionarItinerario.setOrigen(origen);
				oWndSeleccionarItinerario.setDestino(destino);
				oWndSeleccionarItinerario.asignarValores();
				oWndSeleccionarItinerario.addEventListener(Events.ON_SELECT, new EventListener<Event>() {
					@Override
					public void onEvent(Event e) throws Exception{
						if(!txtPosSituActuItinerario.getText().isEmpty()){
							liberarAsientos();
						}

						/*Valida si la ruta esta configurada para permitir la venta antes o despuesta de la hora de salida ## impl 10/11/2014 - jabanto*/
						detalleItinerario=null;
						txtItinerarioPostergado.setText("");
						imgAsiento.setVisible(false);
						DetalleItinerario detalleItinerario= oWndSeleccionarItinerario.getDetalleItinerario();
						if (!(UtilData.permiteVentaByTramo(detalleItinerario.getRuta().getId(), detalleItinerario.getItinerario().getId(),Constantes.FORMAT_DATE.format(detalleItinerario.getFechaPartida())))){
							DlgMessage.information(Messages.getString("WndVentaReserva.information.ventaNoPermitida"));
							return;
						}

						loadDatosPostergacion(oWndSeleccionarItinerario.getIdDetalleItinerario());
						imgAsiento.setVisible(true);
					}
				});

			}
		});
	}



	/**
	 * Carga datos de la postergacion
	 */
	private void loadDatosPostergacion(Long idDetalleItinerario){
		try{
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
				/*	Seteando los datos a las cajas de texto	*/
				txtItinerarioPostergado.setText(detalleItinerario.getItinerario().getId().toString());
				txtRutaPostergado.setText(detalleItinerario.getRuta().toString());
				txtServicioPostergado.setText(detalleItinerario.getItinerario().getServicio().getDenominacion());
				txtSalidaPostergado.setText(Constantes.FORMAT_DATE.format(detalleItinerario.getFechaPartida()));
				txtHoraEmbarque.setText(detalleItinerario.getHoraPartida());
				onLoadPuntoEmbarque(detalleItinerario);

//				if(chkCambioNombre.isChecked() || chkCambioRazonsocial.isChecked())
//					calcularPagos(Constantes.PENALIDAD_POSTERGACION+Constantes.PENALIDAD_CAMBIO_NOMBRE);
//				else
				calcularPagos(Constantes.PENALIDAD_POSTERGACION);

			}else
				DlgMessage.information(Messages.getString("WndPostergacion.information.postergacionByItinerarioDespachado"));
		}catch(Exception ex){
			DlgMessage.error(this.getClass().getSimpleName()+" "+ex.getMessage());
		}
	}
	/**
	 * Calcula el pago por la postergacion
	 * @param penalidad
	 */
	private void calcularPagos(Double penalidad){
		dblbxTarifa.setValue(detalleItinerario.getTarifa()<postergacion.getTarifa()?dblbxMontoAnterior.getValue():detalleItinerario.getTarifa());
		dblbxSaldo.setValue(dblbxTarifa.getValue()-dblbxMontoAnterior.getValue()-dblbxDescuento.getValue());

		dblbxPenalidad.setValue(penalidad);
		dblbxImporteTotal.setValue(dblbxSaldo.getValue()+dblbxPenalidad.getValue());
	}

	private void calcularPagos(){
		if(detalleItinerario!=null)
			dblbxTarifa.setValue(detalleItinerario.getTarifa()<postergacion.getTarifa()?dblbxMontoAnterior.getValue():detalleItinerario.getTarifa());

		dblbxSaldo.setValue(dblbxTarifa.getValue()-dblbxMontoAnterior.getValue()-dblbxDescuento.getValue());
		dblbxImporteTotal.setValue(dblbxSaldo.getValue()+dblbxPenalidad.getValue());
	}

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
			String horaEmbarque="";
			for(ItinerarioAgenciaPartida itiAgePartida : arrayItiAgePartida){
				Comboitem item = new Comboitem(itiAgePartida.getAgencia().getDenominacion());
				item.setValue(itiAgePartida);
				cmbEmbarquePostergado.appendChild(item);
				if(arrayItiAgePartida.size()==1){
					cmbEmbarquePostergado.setSelectedItem(item);
					horaEmbarque=itiAgePartida.getHoraPartida();
				}else if(detItinerario.getAgenciaPartida().getId().intValue()==itiAgePartida.getAgencia().getId().intValue()){
					cmbEmbarquePostergado.setSelectedItem(item);
					horaEmbarque=itiAgePartida.getHoraPartida();
				}
			}
			txtSalidaPostergado.setText(Util.DatetoString(detItinerario.getFechaPartida(), Constantes.DATE_FORMAT)+" "+horaEmbarque);
		}catch(Exception ex){
			ex.printStackTrace();
			DlgMessage.error(this.getClass().getName()+" "+ex.getMessage());
		}
	}

	/**
	 * Libera asientos bloqueados por el usuario
	 */
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

	/**
	 * Carga los usuarios harware
	 */
	private void loadUsuarioHardware(Combobox cmbAgenciaRemota,Combobox cmbUsuarioRemoto, final Textbox txtBoleto){
		try{
			lbxUsuarioHardware.getItems().clear();
			if(cmbAgenciaRemota.getSelectedIndex()>0){
				Agencia agencia = (Agencia)cmbAgenciaRemota.getSelectedItem().getValue();
				TreeMap<String, Object> criteriosBusqueda = new TreeMap<>();
				criteriosBusqueda.put("agencia", agencia);
				criteriosBusqueda.put("estadoRegistro", Constantes.VALUE_ACTIVO);
				List<UsuarioHardware>lstUsuHard=ServiceLocator.getUsuarioHardwareManager().buscarPorX(criteriosBusqueda, null);

					for(UsuarioHardware usuarioHardware: lstUsuHard){
						Listitem listitem = new Listitem();
						List<ControlEspecieValorada>lstCtrol=ServiceLocator.getControlEspecieValoradaManager().buscarEspecieValoradas(agencia.getId(),Constantes.ID_TIPCOM_BOLETO_VIAJE, usuarioHardware.getId());
						if(lstCtrol.size()==1){
							ControlEspecieValorada controlEspecieValorada=lstCtrol.get(0);

							Listcell listcell = new Listcell(usuarioHardware.getDescripcion());
							listitem.appendChild(listcell);
							String serie = "000"+controlEspecieValorada.getSerie();
							listcell = new Listcell(serie.substring(serie.length()-3));
							listcell.setStyle(styleActivo_11px);
							listitem.appendChild(listcell);
							String sboleto="000000"+controlEspecieValorada.getCorrelativoActual().toString();
							listcell = new Listcell(sboleto.substring(sboleto.length()-7));
							listcell.setStyle(styleActivo_11px);
							listitem.appendChild(listcell);

							listitem.addEventListener(Events.ON_CLICK,new EventListener<Event>() {
								@Override
								public void onEvent(Event event)throws Exception {
									ControlEspecieValorada controlEspecieValorada=lbxUsuarioHardware.getSelectedItem().getValue();
									String sboleto="000000"+controlEspecieValorada.getCorrelativoActual().toString();
									String boleto=controlEspecieValorada.getSerie()+"-"+sboleto.substring(sboleto.length()-7);
									txtBoleto.setText(boleto);
									bndbxUsuarioHardware.setText(controlEspecieValorada.getUsuarioHardware().getDescripcion()+": "+boleto);
									bndbxUsuarioHardware.close();
								}
							});
							listitem.setValue(controlEspecieValorada);
							lbxUsuarioHardware.appendChild(listitem);
						}
					}
				bndbxUsuarioHardware.setText(Constantes.COMBO_LABEL_SELECCIONE);
				bndbxUsuarioHardware.setDisabled(false);
			}
		}catch(Exception ex){
			DlgMessage.information(this.getClass().getSimpleName()+" "+ex.getMessage());
		}
	}

//	/**
//	 * Selecciona por defecto el item del Combo Forma de Pago.
//	 */
//	private void onSelectDefaultFormaPago(){
//		/*	Seleccionamos por defecto la Forma de pago	*/
//		for(Comboitem comboitem : cmbFormaPago.getItems()){
//			if(comboitem.getValue() instanceof FormaPago && ((FormaPago)comboitem.getValue()).getId().intValue()==Constantes.ID_FORPAG_CONTADO){
//				cmbFormaPago.setSelectedItem(comboitem);
//				onLoadTipoFormaPago(cmbTipoFormaPago,cmbFormaPago,cmbOperadorTarjetaCredito,cmbTarjetaCredito);
//				for(Comboitem item : cmbTipoFormaPago.getItems()){
//					if(item.getValue() instanceof TipoFormaPago && ((TipoFormaPago)item.getValue()).getId().intValue()==Constantes.ID_TIPFORPAG_EFECTIVO)
//						cmbTipoFormaPago.setSelectedItem(item);
//				}
//			}
//		}
//		cmbFormaPago.setDisabled(true);
//	}
	/**
	 * Carga los tipos de Forma de pago.
	 */
	public void onLoadTipoFormaPago(Combobox cmbTipoFormaPago, Combobox cmbFormaPago, Combobox cmbOperadorTarjetaCredito, Combobox cmbTarjetaCredito){
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
	 * Carga los diferentes tarjetas de credito, de acuerdo al operador seleccionado.
	 */
	public void onLoadTarjetas(Combobox cmbTarjetaCredito,Combobox cmbOperadorTarjetaCredito){
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

	/**
	 * Realiza una validación del Tipo de Forma de Pago, para habilitar o deshabilitar algunos controles.
	 * @throws Exception
	 */
	public void onValidateTipoFormaPago(Combobox cmbTipoFormaPago, Combobox cmbOperadorTarjetaCredito, Combobox cmbTarjetaCredito){
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

//	private void onLoadDevolucion(Combobox cmbPorcentajeDevolucion){
//		Comboitem item = new Comboitem(Constantes.COMBO_LABEL_SELECCIONE);
//		cmbPorcentajeDevolucion.appendChild(item);
//		cmbPorcentajeDevolucion.setSelectedItem(item);
//		item = new Comboitem(DEVOLUCION_80);
//		item.setValue(80);
//		cmbPorcentajeDevolucion.appendChild(item);
//		item = new Comboitem(DEVOLUCION_100);
//		item.setValue(100);
//		cmbPorcentajeDevolucion.appendChild(item);
//	}

}
