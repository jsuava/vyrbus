/**
 * Proyecto		: SISVYR
 * Sistema		: Sistema de Ventas y Reservas
 * Descripci�n	:
 * Autor		: Jos� Sullo Avalos
 * Fecha		: 08/02/2013
 */
package com.cystesoft.vyrbus.view.ctrl;

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
import org.zkoss.zul.Caption;
import org.zkoss.zul.Column;
import org.zkoss.zul.Columns;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Comboitem;
import org.zkoss.zul.Div;
import org.zkoss.zul.Doublebox;
import org.zkoss.zul.Grid;
import org.zkoss.zul.Groupbox;
import org.zkoss.zul.Hbox;
import org.zkoss.zul.Image;
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

import com.cystesoft.vyrbus.model.bean.Agencia;
import com.cystesoft.vyrbus.model.bean.Cliente;
import com.cystesoft.vyrbus.model.bean.ControlEspecieValorada;
import com.cystesoft.vyrbus.model.bean.FormaPago;
import com.cystesoft.vyrbus.model.bean.Itinerario;
import com.cystesoft.vyrbus.model.bean.TipoComprobante;
import com.cystesoft.vyrbus.model.bean.TipoFormaPago;
import com.cystesoft.vyrbus.model.bean.TipoMovimiento;
import com.cystesoft.vyrbus.model.bean.TipoNota;
import com.cystesoft.vyrbus.model.bean.UsuarioHardware;
import com.cystesoft.vyrbus.model.bean.VentaPasaje;
import com.cystesoft.vyrbus.service.exceptions.DevolucionByTipoAgenciaNoPermitidoException;
import com.cystesoft.vyrbus.service.exceptions.DevolucionByTipoMovimientoNoPermitidoException;
import com.cystesoft.vyrbus.service.exceptions.EspecieValoradaNotAvailableException;
import com.cystesoft.vyrbus.service.exceptions.FechaCaducidadNullException;
import com.cystesoft.vyrbus.service.exceptions.LiquidacionNullException;
import com.cystesoft.vyrbus.service.exceptions.ManifiestoImpresoException;
import com.cystesoft.vyrbus.service.exceptions.PerdidaServicioException;
import com.cystesoft.vyrbus.service.locator.ServiceLocator;
import com.cystesoft.vyrbus.service.util.Constantes;
import com.cystesoft.vyrbus.service.util.Messages;
import com.cystesoft.vyrbus.service.util.RESTCiva;
import com.cystesoft.vyrbus.service.util.Util;
import com.cystesoft.vyrbus.service.util.UtilData;
import com.cystesoft.vyrbus.service.util.WSCruzdelsur;
import com.cystesoft.vyrbus.service.util.WSFE;
import com.cystesoft.vyrbus.view.ui.DlgMessage;
import com.cystesoft.vyrbus.view.ui.WndBase;

/**
 * @author Jose
 *
 */
public class WndDevolucionBoleto extends WndBase {
	private static final long serialVersionUID = 1L;
	private Textbox txtNumeroDocumento;
	private Textbox txtNumeroControl;
	private Textbox txtNumeroBoleto;
	private Textbox txtMotivo;
	private Listbox lbxVentas;
	private Window wndDevolucionBoleto;
	private Window wndDevolucion;

	private Combobox cmbResponsable;
	private Combobox cmbPorcentajeDevolucion;
	private Combobox  cmbTipoComprobante;
	private Textbox txtNuevoComprobante;
	private Textbox txtRuc;
	private Textbox txtRazonSocial;
	private Textbox txtDireccion;
//	private Combobox cmbTipoFormaPago;
//	private Combobox cmbOperador;
//	private Combobox cmbTarjetaCredito;
	private Doublebox dblImportePagar;
	private Label lblCargosPlicados;
	private Label lblTipoComprobante;
	private Label lblNumeroComprobante;
	private Label lblRuc;
	private Label lblRazonSocial;
	private Label lblDireccion;
//	private Label lblFormaPago;
//	private Label lblOperaodor;
//	private Label lblTarjetaCredito;
	private Label lblImportePagar;
	private Image imgBuscarCliente;

	private Row rowRuc;
	private Row rowRazonSocial;
	private Row rowDireccion;

//	private Doublebox dbltarifa;
//	private Doublebox dblDescuento;
//	private Doublebox dblRecargo;
	private Doublebox dblImporte;
	private Tab tabListado;

	private VentaPasaje boletoDevolver = null;

	private Date fechaLiquidacion = null;

//	private static final String DEVOLUCION_80 = "DEVOLUCION AL 80%";
	private static final String DEVOLUCION_100 = "DEVOLUCION AL 100%";

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
			fechaLiquidacion = (Date) this.getDesktop().getSession().getAttribute(Constantes.ATRIBUTO_FECHA_LIQUIDACION);
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
		wndDevolucionBoleto=(Window)this.getFellow("wndDevolucionBoleto");
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
				DlgMessage.warning(Messages.getString("WndDevolucionBoleto.warning.noExisteCriteriosBusqueda"));
				return;
			}

			List<VentaPasaje> lstReservas = ServiceLocator.getVentaPasajesManager().buscarBoletosDevolucion(numeroDocumento, numeroControl, numeroBoleto);
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
					Button btnDevolucion = new Button("Devolver","resources/menu/menu_devolucion.png");
					btnDevolucion.setAutodisable("self");
					btnDevolucion.setClass("btnCommandM");
					btnDevolucion.addEventListener(Events.ON_CLICK, new EventListener<Event>() {
						@Override
						public void onEvent(Event e){
							validateDevolucion(e.getTarget().getId());
						}
					});
					btnDevolucion.setId(ventaPasaje.getId().toString());
					btnDevolucion.setStyle("font-size:11px; cursor:pointer");
					cell.appendChild(btnDevolucion);
					item.appendChild(cell);
					item.setValue(ventaPasaje);
					lbxVentas.appendChild(item);
				}
				tabListado.setSelected(true);
			}else
				DlgMessage.information(Messages.getString("WndDevolucionBoleto.information.noVentas"));

		}catch(Exception ex){
			DlgMessage.error(this.getClass().getSimpleName()+" "+ex.getMessage());
		}
	}


	private void validateDevolucion(String idVenta){
		validateDevolucion(idVenta,true);
	}

	/**
	 * Realiza la validac�n para ver si al boleto se le puede aplicar este proceso.
	 * @param idVenta	: Identificador de la venta.
	 */
	private boolean validateDevolucion(String idVenta, boolean createVentanaDevolucion){
		try{
			boletoDevolver = null;
			/*Busca el registro original*/
			VentaPasaje ventaPasaje = ServiceLocator.getVentaPasajesManager().buscarVentaById(Long.valueOf(idVenta));
			/*Busca por el ultimo movimiento agrupado por numero de control*/
			List<VentaPasaje> lstResult= ServiceLocator.getVentaPasajesManager().buscarBoletosDevolucion(null, ventaPasaje.getNumeroControl(), null);
			/*Busca por el identificador del ultmio movimiento, el registro a validar*/
			final VentaPasaje ventaDevolver=ServiceLocator.getVentaPasajesManager().buscarVentaById(lstResult.get(0).getId());

			if(ventaDevolver.getTipoMovimiento().getId().intValue()==Constantes.ID_TIPMOV_ANULACION_SISTEMA)
				throw new DevolucionByTipoMovimientoNoPermitidoException(Constantes.ID_TIPMOV_ANULACION_SISTEMA);
			else if (ventaDevolver.getTipoMovimiento().getId().intValue()==Constantes.ID_TIPMOV_ANULACION)
				throw new DevolucionByTipoMovimientoNoPermitidoException(Constantes.ID_TIPMOV_ANULACION_SISTEMA);
			else if(ventaDevolver.getTipoMovimiento().getId().intValue()==Constantes.ID_TIPMOV_DEVOLUCION)
				throw new DevolucionByTipoMovimientoNoPermitidoException(Constantes.ID_TIPMOV_DEVOLUCION);
			else if(ventaDevolver.getFormaPago().getId().intValue()==Constantes.ID_FORPAG_CREDITO && ventaDevolver.getRucClienteCredito()!=null){
				Agencia agencia = ServiceLocator.getAgenciaManager().buscarAgenciaByRucClienteCredito(ventaDevolver.getRucClienteCredito());
				if(agencia.getTipoAgencia().getId().intValue()==Constantes.ID_TIPAGE_CORPORATIVO)
					throw new DevolucionByTipoAgenciaNoPermitidoException(Constantes.ID_TIPAGE_CORPORATIVO);
			}else if(ventaDevolver.getTipoTransaccion().equals(Constantes.TIPO_OPERACION_PERDIDA_SERVICIO))
				throw new PerdidaServicioException();

			if (ServiceLocator.getDetalleManifiestoManager().validarVentaManifiesto(Long.valueOf(idVenta)))
				throw new ManifiestoImpresoException();
			if(Util.comparaFechas(ventaDevolver.getFechaCaducidad(), ServiceLocator.getVentaPasajesManager().getDateSystem(), Util.OPER_MENOR))
				throw new FechaCaducidadNullException();
			else if(ventaDevolver.getFormaPago().getId().intValue()==Constantes.ID_FORPAG_CORTESIA && createVentanaDevolucion){
				Messagebox.show(Messages.getString("WndDevolucionBoleto.question.noDevolucionCortesia"), DlgMessage.NOMBREAPLICACION+" PREGUNTA", DlgMessage.BTN_YESNO, Messagebox.QUESTION, Messagebox.NO, new EventListener<Event>() {
					@Override
					public void onEvent(Event e) throws Exception{
						if(e.getName().equals(Messagebox.ON_YES)){
							if(ventaDevolver.getIdentificadorIdaRetorno()!=null){
								Messagebox.show("Esta a punto de devolver un boleto que fue comprado en la modalidad de Ida y Vuelta, desea continuar?", DlgMessage.NOMBREAPLICACION, DlgMessage.BTN_YESNO, Messagebox.QUESTION, new EventListener<Event>() {
									@Override
									public void onEvent(Event e) throws Exception{
										if(e.getName().equals(Messagebox.ON_YES)){
											createVentanaDevolucion(ventaDevolver);
										}
									}
								});
							}else
								createVentanaDevolucion(ventaDevolver);
						}
					}
				});
			}else{
				if(ventaDevolver.getIdentificadorIdaRetorno()!=null && createVentanaDevolucion){
					Messagebox.show("Esta a punto de devolver un boleto que fue comprado en la modalidad de Ida y Vuelta, desea continuar?", DlgMessage.NOMBREAPLICACION, DlgMessage.BTN_YESNO, Messagebox.QUESTION, new EventListener<Event>() {
						@Override
						public void onEvent(Event e) throws Exception{
							if(e.getName().equals(Messagebox.ON_YES)){
								createVentanaDevolucion(ventaDevolver);
							}
						}
					});
				}else if ( createVentanaDevolucion)
					createVentanaDevolucion(ventaDevolver);
			}

			return true;
		}catch(DevolucionByTipoMovimientoNoPermitidoException dtmnpex){
			if(dtmnpex.getTipoMovimiento().intValue()==Constantes.ID_TIPMOV_ANULACION_SISTEMA)
				DlgMessage.information(Messages.getString("WndDevolucionBoleto.information.noDevolucionAnulacion"));
			else if(dtmnpex.getTipoMovimiento().intValue()==Constantes.ID_TIPMOV_DEVOLUCION)
				DlgMessage.information(Messages.getString("WndDevolucionBoleto.information.noDevolucionByDevuelto"));
			return false;
		}catch(DevolucionByTipoAgenciaNoPermitidoException dtanpex){
			DlgMessage.information(Messages.getString("WndDevolucionBoleto.information.noDevolucionCorporativo"));
			return false;
		}catch(FechaCaducidadNullException fcnex){
			DlgMessage.information(Messages.getString("WndDevolucionBoleto.information.noDevolucionBoletoCaducado"));
			return false;
		}catch(ManifiestoImpresoException miex){
			DlgMessage.information(Messages.getString("Generales.information.manifiestoImpreso"));
			return false;
		}catch(PerdidaServicioException psex) {
			DlgMessage.information(Messages.getString("WndPerdidaServicio.information.noDevolucionBoletoPerdidaServicio"));
			return false;
		}catch(Exception ex){
			DlgMessage.error(this.getClass().getSimpleName()+" "+ex.getMessage());
			ex.printStackTrace();
			return false;
		}
	}

//	/**
//	 * Realiza la creaci�n de la Ventana para la confirmaci�n de la devoluci�n.
//	 * @param ventaOriginal	: Boleto que se desea devolver.
//	 * @return
//	 */
//	@SuppressWarnings("deprecation")
//	private void createVentanaDevolucion(final VentaPasaje ventaOriginal)throws Exception{
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
//		caption = new Caption("DEVOLUCION DE BOLETO", "resources/menu/menu_reimprimir.png");
//		win.appendChild(caption);
//		label = new Label("Se va a realizar la Devoluci�n del Comprobante con los siguientes datos :");
//		label.setStyle("font-size:12px !important");
//		win.appendChild(label);
//
//		win.appendChild(new Separator("horizontal"));
//
//		groupbox = new Groupbox();
//		groupbox.setClosable(false);
//		caption = new Caption("Informaci�n del Comprobante a devolver");
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
//		if(ventaOriginal.getTipoComprobante().getId().intValue()==Constantes.ID_TIPCOM_BOLETO_VIAJE)
//			label = new Label("NUMERO BOLETO :");
//		else if(ventaOriginal.getTipoComprobante().getId().intValue()==Constantes.ID_TIPCOM_BOLETA_VENTA)
//			label = new Label("NUMERO BOLETA :");
//		else if(ventaOriginal.getTipoComprobante().getId().intValue()==Constantes.ID_TIPCOM_FACTURA)
//			label = new Label("NUMERO FACTURA :");
//		label.setStyle("color:blue");
//		row.appendChild(label);
//		text = new Textbox(ventaOriginal.getNumeroBoleto());
//		text.setStyle("font-size:11px !important");
//		text.setReadonly(true);
//		text.setWidth("80px");
//		row.appendChild(text);
//		label = new Label("FECHA VIAJE :");
//		row.appendChild(label);
//		text = new Textbox(ventaOriginal.getFechaPartida()==null?"":Util.DatetoString(ventaOriginal.getFechaPartida(), Constantes.DATE_FORMAT));
//		text.setStyle("font-size:11px !important");
//		text.setReadonly(true);
//		text.setWidth("80px");
//		row.appendChild(text);
//		rows.appendChild(row);
//
//		row = new Row();
//		label = new Label("NUMERO ASIENTO :");
//		row.appendChild(label);
//		text = new Textbox(ventaOriginal.getNumeroAsiento()==null?"":ventaOriginal.getNumeroAsiento().toString());
//		text.setStyle("font-size:11px !important");
//		text.setReadonly(true);
//		text.setWidth("80px");
//		row.appendChild(text);
//		label = new Label("IMPORTE :");
//		row.appendChild(label);
//		text = new Textbox(Util.toNumberFormat((ventaOriginal.getTarifa()+ventaOriginal.getRecargo()-ventaOriginal.getDescuento()), 2));
//		text.setStyle("align:right;font-size:11px !important");
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
//		row = new Row();
//		row.setSpans("1,3");
//		label = new Label("CANAL VENTA :");
//		label.setStyle("font-size:11px !important;font-weight: bold");
//		row.appendChild(label);
//		Label lblCanalVenta=new Label();
//		if(ventaOriginal.getRucClienteCredito()!=null){
//			Agencia agencia= ServiceLocator.getAgenciaManager().buscarAgenciaByRucClienteCredito(ventaOriginal.getRucClienteCredito());
//			if(agencia!=null)
//				lblCanalVenta.setValue(ventaOriginal.getCanalVenta().getDenominacion()+" <===> "+agencia.getDenominacion());
//			else
//				lblCanalVenta.setValue(ventaOriginal.getCanalVenta().getDenominacion()+" <===> "+"AGENCIA NO ENCONTRADA");
//		}else
//			lblCanalVenta.setValue(ventaOriginal.getCanalVenta().getDenominacion()+" <===> "+ventaOriginal.getAgencia().getDenominacion());
//		lblCanalVenta.setStyle("font-size:11px !important;color:red;font-weight: bold");
//		lblCanalVenta.setWidth("100%");
//		row.appendChild(lblCanalVenta);
//		rows.appendChild(row);
//
//
//		grid.appendChild(rows);
//		groupbox.appendChild(grid);
//		win.appendChild(groupbox);
//		/* ***************************************** */
//		groupbox = new Groupbox();
//		groupbox.setClosable(false);
//		caption = new Caption("Informaci�n de la Devoluci�n");
//		groupbox.appendChild(caption);
//
//		grid = new Grid();
//		rows = new Rows();
//		columns = new Columns();
//		/*	Columna 1	*/
//		column = new Column();
//		column.setAlign("right");
//		column.setWidth("140px");
//		columns.appendChild(column);
//		/*	Columna 2	*/
//		column = new Column();
//		columns.appendChild(column);
//		grid.appendChild(columns);
//
//		row = new Row();
//		label = new Label("% DE DEVOLUCION (*) :");
//		row.appendChild(label);
//		cmbPorcentajeDevolucion = new Combobox();
//		cmbPorcentajeDevolucion.addEventListener(Events.ON_CHANGE, new EventListener<Event>() {
//			public void onEvent(Event e){
//				Double importe = ventaOriginal.getTarifa()-ventaOriginal.getDescuento()+ventaOriginal.getRecargo();
//				if(cmbPorcentajeDevolucion.getText().equals(DEVOLUCION_80)){
//					Double penalidad = (importe*20)/100;
//					dblPenalidad.setValue(penalidad);
//					dblImporte.setValue(importe-penalidad);
//				}else if(cmbPorcentajeDevolucion.getText().equals(DEVOLUCION_100)){
//					dblPenalidad.setValue(0.0);
//					dblImporte.setValue(importe);
//				}else{
//					dblPenalidad.setValue(0.0);
//					dblImporte.setValue(0.0);
//				}
//				txtMotivo.setFocus(true);
//			}
//		});
//		cmbPorcentajeDevolucion.setWidth("130px");
//		cmbPorcentajeDevolucion.setReadonly(true);
//		cmbPorcentajeDevolucion.setAutodrop(true);
//		llenarTiposDevolucion(cmbPorcentajeDevolucion);
//		row.appendChild(cmbPorcentajeDevolucion);
//		rows.appendChild(row);
//
//		row = new Row();
//		label = new Label("TARIFA :");
//		row.appendChild(label);
//		dbltarifa = new Doublebox(ventaOriginal.getTarifa());
//		dbltarifa.setFormat("##0.00");
//		row.appendChild(dbltarifa);
//		rows.appendChild(row);
//
//		row = new Row();
//		label = new Label("DESCUENTO :");
//		row.appendChild(label);
//		dblDescuento = new Doublebox(ventaOriginal.getDescuento());
//		dblDescuento.setFormat("##0.00");
//		row.appendChild(dblDescuento);
//		rows.appendChild(row);
//
//		row = new Row();
//		label = new Label("RECARGO :");
//		row.appendChild(label);
//		dblRecargo = new Doublebox(ventaOriginal.getRecargo());
//		dblRecargo.setFormat("##0.00");
//		row.appendChild(dblRecargo);
//		rows.appendChild(row);
//
//		row = new Row();
//		label = new Label("PENALIDAD :");
//		row.appendChild(label);
//		dblPenalidad = new Doublebox(0.0);
//		dblPenalidad.setFormat("##0.00");
//		row.appendChild(dblPenalidad);
//		rows.appendChild(row);
//
//		row = new Row();
//		label = new Label("IMPORTE :");
//		row.appendChild(label);
//		dblImporte = new Doublebox(0.0);
//		dblImporte.setFormat("##0.00");
//		row.appendChild(dblImporte);
//		rows.appendChild(row);
//
//		row = new Row();
//		label = new Label("MOTIVO (*) :");
//		row.appendChild(label);
//		txtMotivo = new Textbox();
//		txtMotivo.setMultiline(true);
//		txtMotivo.setMaxlength(255);
//		txtMotivo.setWidth("95%");
//		txtMotivo.setHeight("50px");
//		row.appendChild(txtMotivo);
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
//		button.setAutodisable("self");
//		button.addEventListener(Events.ON_CLICK, new EventListener<Event>() {
//			public void onEvent(Event e){
//				if(cmbPorcentajeDevolucion.getSelectedIndex()==0){
//					DlgMessage.information(Messages.getString("WndDevolucionBoleto.information.noPorcentajeDevolucion"));
//					return;
//				}else if(txtMotivo.getText().trim().equals("")){
//					DlgMessage.information(Messages.getString("WndDevolucionBoleto.information.noMotivoDevolucion"), txtMotivo);
//					return;
//				}
//				/*Nuevamente valida la devolucion*/
//				if(!(validateDevolucion(ventaOriginal.getId().toString(), false)))
//					return;
//
//				Messagebox.show(Messages.getString("WndDevolucionBoleto.question.confirmarDevolucion"), DlgMessage.NOMBREAPLICACION, DlgMessage.BTN_YESNO, Messagebox.QUESTION, new EventListener<Event>() {
//					public void onEvent(Event e){
//						try{
//							if(e.getName().equals("onYes")){
//								boletoDevolver = (VentaPasaje)ventaOriginal.clone();
//								boletoDevolver.setId(null);
//								boletoDevolver.setVentaPasaje(ventaOriginal);
//								boletoDevolver.setPenalidad(dblPenalidad.getValue());
//								boletoDevolver.setImportePagado(dblImporte.getValue());
//								boletoDevolver.setImportePagadoEfectivo(0.0);
//								boletoDevolver.setImportePagadoTarjeta(0.0);
//								boletoDevolver.setUsuario(getUsuario());
//								boletoDevolver.setAgencia(getAgencia());
//								boletoDevolver.setTipoMovimiento(new TipoMovimiento(Constantes.ID_TIPMOV_DEVOLUCION));
//								boletoDevolver.setUsuarioHardware((UsuarioHardware)getDesktop().getSession().getAttribute(Constantes.ATRIBUTO_USUARIO_HARDWARE));
//								boletoDevolver.setEstadoRegistro(Constantes.VALUE_ACTIVO);
//								boletoDevolver.setObservaciones(txtMotivo.getText().toUpperCase());
//								boletoDevolver.setFechaLiquidacion(fechaLiquidacion);
//								boletoDevolver.setLiquidacion(null);
//								boletoDevolver.setFechaTransferencia(null);
////									UtilData.auditarRegistro(boletoDevolver,true,getUsuario(), Executions.getCurrent());
//								UtilData.auditarRegistro(boletoDevolver,getUsuario(), Executions.getCurrent());
//								int result = ServiceLocator.getVentaPasajesManager().devolucionBoleto(boletoDevolver);
//
//								//
//
//								if(result==Constantes.CORRECT){
//									DlgMessage.information(Messages.getString("WndDevolucionBoleto.information.exitoDevolucion")+" "+boletoDevolver.getNumeroControl());
//									wndDevolucion.onClose();
//									//win.onClose();
//									buscarVentas();
//								}
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
//
////		return win;
//	}

	/**
	 * Realiza la creaci�n de la Ventana para la confirmaci�n de la devoluci�n.
	 * @param ventaOriginal	: Boleto que se desea devolver.
	 * @return
	 */
	@SuppressWarnings("deprecation")
	private void createVentanaDevolucion(final VentaPasaje ventaOriginal)throws Exception{
		final TipoNota tipoNotaCredito = ServiceLocator.getTipoNotaManager().buscarPorId((long)Constantes.ID_TIPNOTA_CREDITO_DEVOLUCION);


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
		label = new Label("Se va a realizar la Devoluci�n del Comprobante con los siguientes datos :");
		label.setStyle("font-size:12px !important");
		win.appendChild(label);

		win.appendChild(new Separator("horizontal"));

		groupbox = new Groupbox();
		groupbox.setClosable(false);
		groupbox.setMold("3d");
		caption = new Caption("INFORMACION DEL COMPROBANTE A DEVOLVER");
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
		dblImporte.setValue(ventaOriginal.getTarifa()+ventaOriginal.getRecargo()-ventaOriginal.getDescuento());
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


		grid.appendChild(rows);
		groupbox.appendChild(grid);
		win.appendChild(groupbox);
		/* ***************************************** */
		groupbox = new Groupbox();
		groupbox.setClosable(false);
		groupbox.setMold("3d");
		caption = new Caption("INFORMACION DE LA DEVOLUCION");
		caption.setStyle("color: #ffffff");
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
		label = new Label("% DE DEVOLUCION :");
		row.appendChild(label);
		cmbPorcentajeDevolucion = new Combobox();
		cmbPorcentajeDevolucion.setDisabled(true);
		cmbPorcentajeDevolucion.setWidth("130px");
		llenarTiposDevolucion(cmbPorcentajeDevolucion);
		row.appendChild(cmbPorcentajeDevolucion);
		rows.appendChild(row);

		label = new Label("RESPONSABLE :");
		row.appendChild(label);
		cmbResponsable = new Combobox();
		cmbResponsable.setReadonly(true);
		cmbResponsable.setWidth("120px");
		onloadResposablesDevolucion(cmbResponsable);
		row.appendChild(cmbResponsable);
		rows.appendChild(row);

		row= new Row();
		Div div= new Div();
		div.setAlign("center");
		lblCargosPlicados = new Label("SE APLICARAN CARGOS POR CONCEPTO DE GASTOS ADMINISTRATIVOS");
		lblCargosPlicados.setStyle("font-size:10px !important;color:red;font-weight: bold;");
		div.appendChild(lblCargosPlicados);
		row.setSpans("4");
		row.appendChild(div);
		rows.appendChild(row);

		row= new Row();
		lblTipoComprobante= new Label("TIPO COMPROBANTE:");
		row.appendChild(lblTipoComprobante);
		cmbTipoComprobante= new Combobox();
		cmbTipoComprobante.setWidth("130px");
		cmbTipoComprobante.setReadonly(true);
		onloadTiposComprobante(cmbTipoComprobante);
		row.appendChild(cmbTipoComprobante);

		lblNumeroComprobante = new Label("N° COMPROBANTE:");
		lblNumeroComprobante.setStyle("color:blue");
		row.appendChild(lblNumeroComprobante);
		txtNuevoComprobante= new Textbox();
		txtNuevoComprobante.setStyle("font-size:11px !important;color:blue");
		txtNuevoComprobante.setReadonly(true);
		txtNuevoComprobante.setWidth("110px");
		row.appendChild(txtNuevoComprobante);
		rows.appendChild(row);


		rowRuc= new Row();
		rowRuc.setSpans("1,3");
		lblRuc = new Label("RUC :");
		rowRuc.appendChild(lblRuc);
		txtRuc= new Textbox();
		txtRuc.setStyle("font-size:11px !important");
		txtRuc.setWidth("120px");
		Hbox hbox= new Hbox();
		hbox.setAlign("center");
		hbox.appendChild(txtRuc);
		imgBuscarCliente= new Image("resources/mp_buscarEnabled.png");
		imgBuscarCliente.setStyle("cursor:pointer");
		hbox.appendChild(imgBuscarCliente);
		rowRuc.setVisible(false);
		rowRuc.appendChild(hbox);
		rows.appendChild(rowRuc);

		rowRazonSocial= new Row();
		rowRazonSocial.setSpans("1,3");
		lblRazonSocial = new Label("RAZON SOCIAL :");
		rowRazonSocial.appendChild(lblRazonSocial);
		txtRazonSocial= new Textbox();
		txtRazonSocial.setWidth("96%");
		txtRazonSocial.setReadonly(true);
		rowRazonSocial.setVisible(false);
		rowRazonSocial.appendChild(txtRazonSocial);
		rows.appendChild(rowRazonSocial);

		rowDireccion= new Row();
		rowDireccion.setSpans("1,3");
		lblDireccion= new Label("DIECCION :");
		rowDireccion.appendChild(lblDireccion);
		txtDireccion= new Textbox();
		txtDireccion.setWidth("96%");
		txtDireccion.setReadonly(true);
		rowDireccion.setVisible(false);
		rowDireccion.appendChild(txtDireccion);
		rows.appendChild(rowDireccion);

//		row= new Row();
//		lblFormaPago= new Label("FORMA PAGO :");
//		row.appendChild(lblFormaPago);
//		cmbTipoFormaPago= new Combobox();
//		cmbTipoFormaPago.setWidth("130px");
//		cmbTipoFormaPago.setReadonly(true);
//		onLoadTipoFormaPago(cmbTipoFormaPago);
//		row.appendChild(cmbTipoFormaPago);

//		lblOperaodor = new Label("OPERADOR :");
//		row.appendChild(lblOperaodor);
//		cmbOperador= new Combobox();
//		cmbOperador.setWidth("120px");
//		cmbOperador.setReadonly(true);
//		cmbOperador.setDisabled(true);
//		onLoadOperadoresTarjeta(cmbOperador);
//		row.appendChild(cmbOperador);
//		rows.appendChild(row);

		row= new Row();
//		lblTarjetaCredito = new Label("TARJETA CREDITO :");
//		row.appendChild(lblTarjetaCredito);
//		cmbTarjetaCredito= new Combobox();
//		cmbTarjetaCredito.setWidth("130px");
//		cmbTarjetaCredito.setReadonly(true);
//		cmbTarjetaCredito.setDisabled(true);
//		row.appendChild(cmbTarjetaCredito);

		row.setSpans("1,3");
		lblImportePagar = new Label("IMPORTE PAGAR :");
		row.appendChild(lblImportePagar);
		dblImportePagar= new Doublebox();
		dblImportePagar.setStyle("color:red;font-size:13px !important;font-weight: bold");
		dblImportePagar.setFormat("##0.00");
		dblImportePagar.setLocale(Locale.US);
		dblImportePagar.setReadonly(true);
		row.appendChild(dblImportePagar);
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

		visibleControlsPago(false);
		visibleControlsClienteFactura(false);

		cmbResponsable.addEventListener(Events.ON_CHANGE, new EventListener<Event>() {
			@Override
			public void onEvent(Event event) throws Exception {
				limpiarControlsClienteFactura();
				limpiarControlsPago();
				visibleControlsPago(false);
				visibleControlsClienteFactura(false);
				if(cmbResponsable.getSelectedIndex()>0){
					int isResponsableTepsa=cmbResponsable.getSelectedItem().getValue();
					if(isResponsableTepsa==Constantes.TRUE_VALUE)
						visibleControlsPago(false);
					else{
						visibleControlsPago(true);
						dblImportePagar.setValue(0.00);

						/*Calcula el importe a pagar - 17/11/2016 - jabanto*/
						if(ventaOriginal.getTipoFormaPago().getId().intValue()==Constantes.ID_TIPFORPAG_TARJETA && ventaOriginal.getCanalVenta().getId().intValue()!=Constantes.ID_CANVEN_WEB)
							dblImportePagar.setValue(tipoNotaCredito.getGastoAdminTarjeta());
						else
							dblImportePagar.setValue(tipoNotaCredito.getGastoAdminEfectivo());
					}
				}

			}
		});

		cmbTipoComprobante.addEventListener(Events.ON_CHANGE, new EventListener<Event>() {
			@Override
			public void onEvent(Event event) throws Exception {
				// TODO Auto-generated method stub
				limpiarControlsClienteFactura();
				if(cmbTipoComprobante.getSelectedItem().getValue() instanceof TipoComprobante){
					txtNuevoComprobante.setText(getEspecieValorada(((TipoComprobante)cmbTipoComprobante.getSelectedItem().getValue()).getId()));
					if(((TipoComprobante)cmbTipoComprobante.getSelectedItem().getValue()).getId().intValue()==Constantes.ID_TIPCOM_FACTURA){
						visibleControlsClienteFactura(true);
						txtRuc.setFocus(true);
					}else{
						visibleControlsClienteFactura(false);
//						cmbTipoFormaPago.setFocus(true);
					}
				}else
					txtNuevoComprobante.setText("");
			}
		});

		txtRuc.addEventListener(Events.ON_OK, new EventListener<Event>() {
			@Override
			public void onEvent(Event event) throws Exception {
				buscarCliente();
			}
		});

		imgBuscarCliente.addEventListener(Events.ON_CLICK, new EventListener<Event>() {
			@Override
			public void onEvent(Event event) throws Exception {
				buscarCliente();
			}
		});

//		cmbTipoFormaPago.addEventListener(Events.ON_CHANGE, new EventListener<Event>() {
//			@Override
//			public void onEvent(Event event) throws Exception {
////				dblImportePagar.setValue(0.00);
//				cmbOperador.setSelectedIndex(0);
//				cmbOperador.setDisabled(true);
//				Util.limpiarCombobox(cmbTarjetaCredito);
//				cmbTarjetaCredito.setText("");
//				cmbTarjetaCredito.setDisabled(true);
//
//				if(cmbTipoFormaPago.getSelectedItem().getValue() instanceof TipoFormaPago){
//					if(((TipoFormaPago)cmbTipoFormaPago.getSelectedItem().getValue()).getId().intValue()==Constantes.ID_TIPFORPAG_TARJETA){
//						cmbOperador.setSelectedIndex(0);
//						cmbOperador.setDisabled(false);
//					}
//				}
//			}
//		});

//		cmbOperador.addEventListener(Events.ON_CHANGE, new EventListener<Event>() {
//			@Override
//			public void onEvent(Event event) throws Exception {
//				if(cmbOperador.getSelectedItem().getValue() instanceof OperadorTarjetaCredito){
//					onLoadTarjetasCredito(cmbTarjetaCredito, (OperadorTarjetaCredito)cmbOperador.getSelectedItem().getValue());
//					cmbTarjetaCredito.setDisabled(false);
//				}else{
//					Util.limpiarCombobox(cmbTarjetaCredito);
//					cmbTarjetaCredito.setText("");
//					cmbTarjetaCredito.setDisabled(true);
//				}
//			}
//		});


		Button button = new Button("Continuar", "resources/mp_aceptarEnabled.png");
		button.setAutodisable("self");
		button.setClass("btnCommandL");
		button.addEventListener(Events.ON_CLICK, new EventListener<Event>() {
			@Override
			public void onEvent(Event e){
//				if(cmbPorcentajeDevolucion.getSelectedIndex()==0){
//					DlgMessage.information(Messages.getString("WndDevolucionBoleto.information.noPorcentajeDevolucion"));
//					return;
//				}else if(txtMotivo.getText().trim().equals("")){
//					DlgMessage.information(Messages.getString("WndDevolucionBoleto.information.noMotivoDevolucion"), txtMotivo);
//					return;
//				}
//				/*Nuevamente valida la devolucion*/
				if(!(validateDevolucion(ventaOriginal.getId().toString(), false)))
					return;
				
				if(cmbResponsable.getSelectedIndex()== Constantes.TRUE_VALUE && (tipoNotaCredito.getGastoAdminEfectivo()==0 || tipoNotaCredito.getGastoAdminTarjeta()==0)) {
					DlgMessage.information("No se puede continuar porque no hay importe para generar el Gasto Administrativo");
					return;
				}
					

				try {

					devolverComprobante(tipoNotaCredito, ventaOriginal);

				} catch (Exception e1) {
					e1.printStackTrace();
					DlgMessage.information(this.getClass().getSimpleName()+" "+e1.getMessage());
				}
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

		wndDevolucion = win;
		this.appendChild(wndDevolucion);
		wndDevolucion.setMode(MODAL);

//		return win;
	}

	private void devolverComprobante(final TipoNota tipoNota, final VentaPasaje ventaOriginal)throws Exception{
		if(cmbResponsable.getSelectedIndex()<=0){
			DlgMessage.information(Messages.getString("WndDevolucionBoleto.information.niResponsable"),cmbResponsable);
			return;
		}else if((int)cmbResponsable.getSelectedItem().getValue()==Constantes.TRUE_VALUE){
			/*Si es LA EMPRESA*/


		}else{
			/*Si es el PASAJERO*/
			if(!(cmbTipoComprobante.getSelectedItem().getValue() instanceof TipoComprobante)){
				DlgMessage.information(Messages.getString("WndDevolucionBoleto.information.notipoComprobante"),cmbTipoComprobante);
				return;
			}else if (txtNuevoComprobante.getText().trim().isEmpty()){
				DlgMessage.information(Messages.getString("WndDevolucionBoleto.information.noNumeroEspecieValorada"));
				return;
			}else if (((TipoComprobante)cmbTipoComprobante.getSelectedItem().getValue()).getId().intValue()==Constantes.ID_TIPCOM_FACTURA &&
					(txtRuc.getText().trim().isEmpty() || txtRazonSocial.getText().trim().isEmpty()) ){
				DlgMessage.information(Messages.getString("WndDevolucionBoleto.information.noCliente"),txtRuc);
				return;
//			}else if (!(cmbTipoFormaPago.getSelectedItem().getValue() instanceof TipoFormaPago)){
//				DlgMessage.information(Messages.getString("WndDevolucionBoleto.information.noTipoFormaPago"),cmbTipoFormaPago);
//				return;
//			}
//			if(((TipoFormaPago)cmbTipoFormaPago.getSelectedItem().getValue()).getId().intValue()==Constantes.ID_TIPFORPAG_TARJETA &&
//					!(cmbOperador.getSelectedItem().getValue() instanceof OperadorTarjetaCredito)){
//				DlgMessage.information(Messages.getString("WndDevolucionBoleto.information.noOperadorTarjetaCredito"),cmbOperador);
//				return;
//			}else if (cmbTarjetaCredito.isDisabled()==false && !(cmbTarjetaCredito.getSelectedItem().getValue() instanceof TarjetaCredito)){
//				DlgMessage.information(Messages.getString("WndDevolucionBoleto.information.noTarjetaCredito"),cmbTarjetaCredito);
//				return;
			}else if(dblImportePagar.getValue()<=0){
				DlgMessage.information(Messages.getString("WndDevolucionBoleto.information.noImportePagar"),cmbTipoComprobante);
				return;
			}
		}
		if(txtMotivo.getText().trim().equals("")){
			DlgMessage.information(Messages.getString("WndDevolucionBoleto.information.noMotivoDevolucion"), txtMotivo);
			return;
		}

		Cliente cliente=null;
		if(!(txtRazonSocial.getText().trim().isEmpty())){
			cliente=buscarCliente();
			if(cliente==null)
				return;
		}

		final Cliente oCliente=cliente;

		Messagebox.show(Messages.getString("WndDevolucionBoleto.question.confirmarDevolucion"), DlgMessage.NOMBREAPLICACION, DlgMessage.BTN_YESNO, Messagebox.QUESTION, new EventListener<Event>() {
			@Override
			public void onEvent(Event e) throws Exception{
				try {
					if(e.getName().equals("onYes")){
						/*Valida si es una venta del pool emitida por tepsa*/
						if(ventaOriginal.getTipoTransaccion().equals(Constantes.TIPO_OPERACION_VENTA_POOL)){
							/*Valida si es de Cruz del sur*/
							if(ventaOriginal.getServicio().getId().intValue()==Constantes.ID_SERVICIO_POOL_CRUZDELSUR){
								WSCruzdelsur.anularBoleto(ventaOriginal.getNumeroBoletoAnterior());
							}else if(ventaOriginal.getServicio().getId().intValue()==Constantes.ID_SERVICIO_POOL_EXCLUCIVA){
								RESTCiva.anularBoleto(ventaOriginal.getNumeroBoleto());
							}
						}

						/*Boleto a devolver*/
						boletoDevolver = (VentaPasaje)ventaOriginal.clone();
						boletoDevolver.setId(null);
						boletoDevolver.setVentaPasaje(ventaOriginal);
						boletoDevolver.setPenalidad(0.00);
//						boletoDevolver.setImportePagado(ventaOriginal.getImportePagado());
						boletoDevolver.setImportePagado(dblImporte.getValue());
						boletoDevolver.setImportePagadoEfectivo(0.0);
						boletoDevolver.setImportePagadoTarjeta(0.0);
						boletoDevolver.setUsuario(getUsuario());
						boletoDevolver.setAgencia(getAgencia());
						boletoDevolver.setTipoMovimiento(new TipoMovimiento(Constantes.ID_TIPMOV_DEVOLUCION));
						boletoDevolver.setUsuarioHardware((UsuarioHardware)getDesktop().getSession().getAttribute(Constantes.ATRIBUTO_USUARIO_HARDWARE));
						boletoDevolver.setEstadoRegistro(Constantes.VALUE_ACTIVO);
						boletoDevolver.setObservaciones(txtMotivo.getText().toUpperCase());
						boletoDevolver.setFechaLiquidacion(fechaLiquidacion);
						boletoDevolver.setLiquidacion(null);
						boletoDevolver.setFechaTransferencia(null);
						if(ventaOriginal.getTipoComprobante().getId().intValue()==Constantes.ID_TIPCOM_BOLETA_VENTA ||
								ventaOriginal.getTipoComprobante().getId().intValue()==Constantes.ID_TIPCOM_FACTURA)
							boletoDevolver.setTipoNota(tipoNota);

						UtilData.auditarRegistro(boletoDevolver,getUsuario(), Executions.getCurrent());

						/*Valida si es por parte de tepsa*/
						if((int)cmbResponsable.getSelectedItem().getValue()==Constantes.TRUE_VALUE){
							VentaPasaje notaCredito=ServiceLocator.getVentaPasajesManager().devolucionBoleto(boletoDevolver, null);
							if(notaCredito!=null)
								WSFE.sendNota(notaCredito);
						}else{
							/*Emitir el gasto administrativo*/
							VentaPasaje gastoAdmin= new VentaPasaje();
							gastoAdmin.setVentaOriginal(ventaOriginal.getVentaOriginal());
							gastoAdmin.setVentaPasaje(ventaOriginal);
							gastoAdmin.setItinerario(new Itinerario((long)1));
							gastoAdmin.setRuta(boletoDevolver.getRuta());
							gastoAdmin.setCliente(oCliente);
							gastoAdmin.setPasajero(boletoDevolver.getPasajero());
							gastoAdmin.setFormaPago(new FormaPago(Constantes.ID_FORPAG_CONTADO));
							gastoAdmin.setServicio(boletoDevolver.getServicio());
							gastoAdmin.setTipoComprobante((TipoComprobante)cmbTipoComprobante.getSelectedItem().getValue());
							gastoAdmin.setTipoMovimiento(new TipoMovimiento(Constantes.ID_TIPMOV_GASTOS_ADMINISTRATIVOS));
							gastoAdmin.setTipoFormaPago(new TipoFormaPago(Constantes.ID_TIPFORPAG_EFECTIVO));
//							gastoAdmin.setTipoFormaPago((TipoFormaPago)cmbTipoFormaPago.getSelectedItem().getValue());
//							if(gastoAdmin.getTipoFormaPago().getId().intValue()==Constantes.ID_TIPFORPAG_TARJETA)
//								gastoAdmin.setTarjetaCredito((TarjetaCredito)cmbTarjetaCredito.getSelectedItem().getValue());
							gastoAdmin.setNumeroBoleto(txtNuevoComprobante.getText().trim().toUpperCase());
							gastoAdmin.setNumeroBoletoAnterior(boletoDevolver.getNumeroBoleto());
							gastoAdmin.setSecuencial(Constantes.FALSE_VALUE);
							gastoAdmin.setTarifa(dblImportePagar.getValue());
							gastoAdmin.setRecargo(0.00);
							gastoAdmin.setDescuento(0.00);
							gastoAdmin.setPenalidad(0.00);
							gastoAdmin.setAcuenta(0.00);
							gastoAdmin.setImportePagadoByDiferencia(0.00);
							gastoAdmin.setImportePagado(dblImportePagar.getValue());
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
							gastoAdmin.setObservaciones("POR DEVOLUCION DEL COMPROBANTE N�: "+boletoDevolver.getNumeroBoleto());
							UtilData.auditarRegistro(gastoAdmin, getUsuario(), Executions.getCurrent());

							Double igv=gastoAdmin.getImportePagado()- Double.valueOf(Util.toNumberFormat(gastoAdmin.getImportePagado()/((Constantes.IGV/100)+1),2));
							gastoAdmin.setIgv(igv);

							VentaPasaje notaCredito = ServiceLocator.getVentaPasajesManager().devolucionBoleto(boletoDevolver, gastoAdmin);

							gastoAdmin=ServiceLocator.getVentaPasajesManager().buscarVentaById(gastoAdmin.getId());
							List<VentaPasaje>listVentaPasaje= new ArrayList<>();
							listVentaPasaje.add(gastoAdmin);
							WSFE.sendVenta(listVentaPasaje, wndDevolucionBoleto, true, notaCredito, Constantes.NUMERO_COPIAS_COMPROBANTE_PASAJES);
						}



						/*Confirma la operacion*/
						DlgMessage.information(Messages.getString("WndDevolucionBoleto.information.exitoDevolucion")+" "+boletoDevolver.getNumeroControl());
						wndDevolucion.onClose();
						buscarVentas();

					}
				} catch (Exception e2) {
					throw e2;
				}
			}
		});
	}

	private Cliente buscarCliente()throws Exception{
		Cliente cliente=null;
		if(!(txtRuc.getText().trim().isEmpty())){
			txtRazonSocial.setText("");
			txtDireccion.setText("");

			TreeMap<String, Object>criteriosBusqueda= new TreeMap<>();
			criteriosBusqueda.put("numeroDocumento", txtRuc.getText().trim());
			criteriosBusqueda.put("estadoRegistro", Constantes.VALUE_ACTIVO);
			List<Cliente> result= ServiceLocator.getClienteManager().buscarPorX(criteriosBusqueda, null);
			if(result.size()>0){
				cliente= result.get(0);
				txtRazonSocial.setText(cliente.getRazonSocial());
				txtDireccion.setText(cliente.getDireccion()!=null?cliente.getDireccion():"");
			}else
				DlgMessage.information(Messages.getString("WndVentaReserva.information.noCliente"),txtRuc);
		}

		return cliente;
	}

//	private void onLoadTarjetasCredito(Combobox combobox, OperadorTarjetaCredito operadorTarjetaCredito)throws Exception{
//		Util.limpiarCombobox(cmbTarjetaCredito);
//
//		TreeMap<String, Object>criteriosBusqueda= new TreeMap<>();
//		criteriosBusqueda.put("operadorTarjetaCredito", operadorTarjetaCredito);
//		criteriosBusqueda.put("estadoRegistro", Constantes.VALUE_ACTIVO);
//
//		List<String>criteriosOrdenar= new ArrayList<>();
//		criteriosOrdenar.add("denominacion");
//		List<TarjetaCredito> result= ServiceLocator.getTarjetaCreditoManager().buscarPorX(criteriosBusqueda, criteriosOrdenar);
//
//		Comboitem comboitem = new Comboitem(Constantes.COMBO_LABEL_SELECCIONE);
//		combobox.appendChild(comboitem);
//
//		for(TarjetaCredito tarjetaCredito: result){
//			comboitem= new Comboitem(tarjetaCredito.getDenominacion());
//			comboitem.setValue(tarjetaCredito);
//			combobox.appendChild(comboitem);
//		}
//		combobox.setSelectedIndex(0);
//
//	}


//	private void onLoadOperadoresTarjeta(Combobox combobox)throws Exception{
//		List<OperadorTarjetaCredito> result=ServiceLocator.getOperadorTarjetaCreditoManager().buscarPorEstadoRegistro(Constantes.VALUE_ACTIVO, "denominacion");
//		Comboitem comboitem = new Comboitem(Constantes.COMBO_LABEL_SELECCIONE);
//		combobox.appendChild(comboitem);
//		for(OperadorTarjetaCredito operadorTarjetaCredito: result){
//			comboitem= new Comboitem(operadorTarjetaCredito.getDenominacion());
//			comboitem.setValue(operadorTarjetaCredito);
//			combobox.appendChild(comboitem);
//		}
//		combobox.setSelectedIndex(0);
//	}
//
//	private void onLoadTipoFormaPago(Combobox combobox)throws Exception{
//		List<TipoFormaPago> result= ServiceLocator.getTipoFormaPagoManager().buscarPorEstadoRegistro(Constantes.VALUE_ACTIVO, "denominacion");
//		Comboitem comboitem = new Comboitem(Constantes.COMBO_LABEL_SELECCIONE);
//		combobox.appendChild(comboitem);
//		for(TipoFormaPago tipoFormaPago :result){
//			if(tipoFormaPago.getId().intValue()==Constantes.ID_TIPFORPAG_EFECTIVO ||
//					tipoFormaPago.getId().intValue()==Constantes.ID_TIPFORPAG_TARJETA){
//				comboitem= new Comboitem(tipoFormaPago.getDenominacion());
//				comboitem.setValue(tipoFormaPago);
//				combobox.appendChild(comboitem);
//			}
//		}
//
//		Util.seleccionarValorItemCombo(TipoFormaPago.class, combobox, Constantes.ID_TIPFORPAG_EFECTIVO);
//	}

	private String getEspecieValorada(Integer tipoComprobanteID)throws Exception{
		/*BEGIN 15/06/2021 - javalos - Correlativo by caja*/
//		EspecieValorada especieValorada=UtilData.buscarEspecieValorada(tipoComprobanteID, getAgencia(), false);
//		return especieValorada.toString();
		String result = "";
		try {
			ControlEspecieValorada controlEspecieValorada = UtilData.buscarEspecieValoradaByCaja(tipoComprobanteID, getAgencia(), false, getUsuarioHardware(), null);
			if(controlEspecieValorada == null)
				throw new EspecieValoradaNotAvailableException();
			result = controlEspecieValorada.toString();
		}catch(EspecieValoradaNotAvailableException evnaex) {
			DlgMessage.information("No tiene Especies Valoradas configuradas en su equipo");
		}catch(Exception ex) {
			DlgMessage.information(ex.getMessage());
		} 
		return result;
		/*END 15/06/2021 - javalos - Correlativo by caja*/
	}


	private void onloadTiposComprobante(Combobox combobox) throws Exception{
		List<TipoComprobante>result=ServiceLocator.getTipoComprobanteManager().buscarPorEstadoRegistro(Constantes.VALUE_ACTIVO, "denominacion");
		Comboitem comboitem = new Comboitem(Constantes.COMBO_LABEL_SELECCIONE);
		combobox.appendChild(comboitem);
		for(TipoComprobante tipoComprobante: result){
			if(tipoComprobante.getId().intValue()==Constantes.ID_TIPCOM_BOLETA_VENTA ||
					tipoComprobante.getId().intValue()==Constantes.ID_TIPCOM_FACTURA){
				comboitem= new Comboitem(tipoComprobante.getDenominacion());
				comboitem.setValue(tipoComprobante);
				combobox.appendChild(comboitem);
			}
		}

		combobox.setSelectedIndex(0);
	}

	private void onloadResposablesDevolucion(Combobox combobox){
		Comboitem comboitem = new Comboitem(Constantes.COMBO_LABEL_SELECCIONE);
		combobox.appendChild(comboitem);
		comboitem= new Comboitem("PASAJERO");
		comboitem.setValue(Constantes.FALSE_VALUE);
		combobox.appendChild(comboitem);
		comboitem= new Comboitem("LA EMPRESA");
		comboitem.setValue(Constantes.TRUE_VALUE);
		combobox.appendChild(comboitem);
		combobox.setSelectedIndex(0);
	}

	private void llenarTiposDevolucion(Combobox cmbPorcentajeDevolucion){
		/*End Begin 28/10/2016 - jabanto*/
//		Comboitem item = new Comboitem(Constantes.COMBO_LABEL_SELECCIONE);
//		cmbPorcentajeDevolucion.appendChild(item);
//		cmbPorcentajeDevolucion.setSelectedItem(item);
//		item = new Comboitem(DEVOLUCION_80);
//		item.setValue(80);
//		cmbPorcentajeDevolucion.appendChild(item);


		/*Todas las devoluciones son al 100% - 28/10/2016 - jabanto*/
		Comboitem  item = new Comboitem(DEVOLUCION_100);
		item.setValue(100);
		cmbPorcentajeDevolucion.appendChild(item);
		cmbPorcentajeDevolucion.setSelectedIndex(0);
	}


	private void limpiarControlsClienteFactura()throws Exception{
		txtRuc.setText("");
		txtRazonSocial.setText("");
		txtDireccion.setText("");
	}

	private void limpiarControlsPago()throws Exception{
		cmbTipoComprobante.setSelectedIndex(0);
		txtNuevoComprobante.setText("");
//		cmbTipoFormaPago.setSelectedIndex(0);
//		cmbOperador.setSelectedIndex(0);
//		Util.limpiarCombobox(cmbTarjetaCredito);
		dblImportePagar.setValue(null);
	}

	private void visibleControlsPago(boolean visible)throws Exception{
		cmbTipoComprobante.setVisible(visible);
		txtNuevoComprobante.setVisible(visible);
//		cmbTipoFormaPago.setVisible(visible);
//		cmbOperador.setVisible(visible);
//		cmbTarjetaCredito.setVisible(visible);
		dblImportePagar.setVisible(visible);
		lblCargosPlicados.setVisible(visible);
		lblTipoComprobante.setVisible(visible);
		lblNumeroComprobante.setVisible(visible);
//		lblFormaPago.setVisible(visible);
//		lblOperaodor.setVisible(visible);
//		lblTarjetaCredito.setVisible(visible);
		lblImportePagar.setVisible(visible);
	}

	private void visibleControlsClienteFactura(boolean visible)throws Exception{
		rowRuc.setVisible(visible);
		rowRazonSocial.setVisible(visible);
		rowDireccion.setVisible(visible);
//		lblRuc.setVisible(visible);
//		lblRazonSocial.setVisible(visible);
//		lblDireccion.setVisible(visible);
//		txtRuc.setVisible(visible);
//		txtRazonSocial.setVisible(visible);
//		txtDireccion.setVisible(visible);
//		imgBuscarCliente.setVisible(visible);
	}
}
