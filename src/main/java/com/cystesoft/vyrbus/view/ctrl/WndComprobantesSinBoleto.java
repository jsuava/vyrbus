/**
 * Proyecto		: SISVYR
 * Sistema		: Sistema de Ventas y Reservas
 * Descripci�n	: Clase que administra los comprobantes que no tienen boleto.
 * Autor		: Jos� Avalos Sullo
 * Fecha		: 11/02/2013
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
import org.zkoss.zul.Div;
import org.zkoss.zul.Grid;
import org.zkoss.zul.Groupbox;
import org.zkoss.zul.Hbox;
import org.zkoss.zul.Label;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Row;
import org.zkoss.zul.Rows;
import org.zkoss.zul.Separator;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Toolbar;
import org.zkoss.zul.Window;

import com.cystesoft.vyrbus.model.bean.Agencia;
import com.cystesoft.vyrbus.model.bean.ComprobantesBloqueados;
import com.cystesoft.vyrbus.model.bean.ComprobantesBloqueadosID;
import com.cystesoft.vyrbus.model.bean.ControlEspecieValorada;
import com.cystesoft.vyrbus.model.bean.Parametros;
import com.cystesoft.vyrbus.model.bean.TipoComprobante;
import com.cystesoft.vyrbus.model.bean.TipoMovimiento;
import com.cystesoft.vyrbus.model.bean.UsuarioHardware;
import com.cystesoft.vyrbus.model.bean.VentaPasaje;
import com.cystesoft.vyrbus.service.exceptions.LiquidacionNullException;
import com.cystesoft.vyrbus.service.exceptions.ManifiestoImpresoException;
import com.cystesoft.vyrbus.service.exceptions.NumeroBoletoDuplicadoException;
import com.cystesoft.vyrbus.service.exceptions.ReimpresionByTipoMovimientoNoPermitidoException;
import com.cystesoft.vyrbus.service.locator.ServiceLocator;
import com.cystesoft.vyrbus.service.util.Constantes;
import com.cystesoft.vyrbus.service.util.Messages;
import com.cystesoft.vyrbus.service.util.MyTime;
import com.cystesoft.vyrbus.service.util.Util;
import com.cystesoft.vyrbus.service.util.UtilData;
import com.cystesoft.vyrbus.service.util.WSFE;
import com.cystesoft.vyrbus.view.ui.DlgMessage;
import com.cystesoft.vyrbus.view.ui.WndBase;

/**
 * @author Jose
 *
 */
public class WndComprobantesSinBoleto extends WndBase {
	private static final long serialVersionUID = 1L;
	private Listbox lbxComprobantes;
	private Combobox cmbTipoComprobante = null;
	private Combobox cmbTipoComprobanteBusq;
	private Combobox cmbAgencia;
	private Window wndComprobantesSinBoleto;


	private VentaPasaje boletoReimprimir = null;
	private Agencia agencia = null;
	private Window wndReimpresion;
	private Date fechaLiquidacion = null;
//	private CanalVenta canalVenta=null;


	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.view.ui.WndBase#onCreate()
	 */
	@Override
	public void onCreate() throws Exception {
		try{
			super.onCreate();
			cargarTipoComprobanteBusq();
			cargarAgencias();

//			canalVenta = (CanalVenta)this.getDesktop().getSession().getAttribute(Constantes.ATRIBUTO_CANAL_VENTA);
			agencia = (Agencia)this.getDesktop().getSession().getAttribute(Constantes.ATRIBUTO_AGENCIA);
			fechaLiquidacion = (Date)this.getDesktop().getSession().getAttribute(Constantes.ATRIBUTO_FECHA_LIQUIDACION);
			if(fechaLiquidacion==null)
				throw new LiquidacionNullException();

			buscarComprobantes();

		}catch(LiquidacionNullException lex){
			DlgMessage.information(Messages.getString("WndVentaReserva.information.noLiquidacion"));
			closeTabWindow();
		}catch(Exception ex){
			DlgMessage.error(ex.getMessage());
		}
	}

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.view.ui.WndBase#initComponents()
	 */
	@Override
	public void initComponents() {
		// TODO Auto-generated method stub
		super.initComponents();
		lbxComprobantes = (Listbox)this.getFellow("lbxComprobantes");
		cmbTipoComprobanteBusq=(Combobox)this.getFellow("cmbTipoComprobanteBusq");
		cmbAgencia = (Combobox)this.getFellow("cmbAgencia");
		wndComprobantesSinBoleto=(Window)this.getFellow("wndComprobantesSinBoleto");

		cmbTipoComprobanteBusq.addEventListener(Events.ON_CHANGE, new EventListener<Event>() {
			@Override
			public void onEvent(Event e){
				cargarAgencias();
			}
		});
	}

	public void cargarTipoComprobanteBusq() {
		try{
			ArrayList<TipoComprobante> lstComprobantes = ServiceLocator.getTipoComprobanteManager().buscarPorEstadoRegistro(Constantes.VALUE_ACTIVO, "denominacion");
			UtilData.cargarGenericData(cmbTipoComprobanteBusq, true);
			for (TipoComprobante tipoComprobante: lstComprobantes) {
//				if (!(tipoComprobante.getId().equals(Constantes.ID_TIPCOM_BOLETO_VIAJE) ||
//						tipoComprobante.getId().equals(Constantes.ID_TIPCOM_MANIFIESTO_PAX))){
				if(tipoComprobante.getId().intValue()==Constantes.ID_TIPCOM_RECIBO_CAJA ||
						tipoComprobante.getId().intValue()==Constantes.ID_TIPCOM_VOUCHER_AGENCIA_VIAJES ||
						tipoComprobante.getId().intValue()==Constantes.ID_TIPCOM_VOUCHER_CORPORATIVO){
					Comboitem oComboitem = new Comboitem();

					oComboitem.setValue(tipoComprobante);
					oComboitem.setLabel(tipoComprobante.getDenominacion());

					cmbTipoComprobanteBusq.appendChild(oComboitem);
				}
			}

			cmbTipoComprobanteBusq.setSelectedIndex(0);
		}catch(Exception ex){
			ex.printStackTrace();
			DlgMessage.error(ex.getMessage());
		}
	}

	private void cargarAgencias(){
		try{
			String fechaPartida = Util.DatetoString(new Date(), Constantes.DATE_FORMAT);
			Integer idTipoComprobante = null;
			if(cmbTipoComprobanteBusq.getSelectedItem().getValue() instanceof TipoComprobante)
				idTipoComprobante = ((TipoComprobante)cmbTipoComprobanteBusq.getSelectedItem().getValue()).getId();
			List<Agencia> lstAgencias = ServiceLocator.getAgenciaManager().buscarAgenciaComprobantesSinBoleto(idTipoComprobante, fechaPartida);
			cmbAgencia.getItems().clear();
			Comboitem comboitem = new Comboitem(Constantes.COMBO_LABEL_TODOS);
			cmbAgencia.appendChild(comboitem);
			cmbAgencia.setSelectedIndex(0);
			for(Agencia agencia : lstAgencias){
				comboitem = new Comboitem(agencia.getDenominacion());
				comboitem.setValue(agencia);
				cmbAgencia.appendChild(comboitem);
			}
		}catch(Exception ex){
			ex.printStackTrace();
			DlgMessage.error(ex.getMessage());
		}
	}

	public void buscarComprobantes(){
		try{
			lbxComprobantes.getItems().clear();
			String fechaPartida = Util.DatetoString(new Date(), Constantes.DATE_FORMAT);
//			String fechaPartida = "09/04/2017";
			Integer idTipoComprobante=null;
			Integer idAgenciaEmision = null;
			if(cmbTipoComprobanteBusq.getSelectedItem().getValue() instanceof TipoComprobante)
				idTipoComprobante=((TipoComprobante)cmbTipoComprobanteBusq.getSelectedItem().getValue()).getId();

			if(cmbAgencia.getSelectedItem().getValue() instanceof Agencia)
				idAgenciaEmision = ((Agencia)cmbAgencia.getSelectedItem().getValue()).getId();


//			UsuarioRol usuarioRol = ServiceLocator.getUsuarioRolManager().buscarXIdUsuario(getUsuario().getId());
//			List<UsuarioRol> lstUsuarioRol = ServiceLocator.getUsuarioRolManager().buscarXIdUsuario(getUsuario().getId());
//			UsuarioRol usuarioRol=null;
//			if(lstUsuarioRol.size()>0){
//				usuarioRol=new UsuarioRol();
//				usuarioRol=lstUsuarioRol.get(0);

//				List<VentaPasaje> lstResult = ServiceLocator.getVentaPasajesManager().buscarComprobantesSinBoleto(fechaPartida, agencia.getId(),idTipoComprobante, usuarioRol.getRol().getId(), idAgenciaEmision);
			List<VentaPasaje> lstResult = ServiceLocator.getVentaPasajesManager().buscarComprobantesSinBoleto(fechaPartida, agencia.getId(),idTipoComprobante, getRol().getId(), idAgenciaEmision);
			Listitem listitem = null;
			Listcell listcell = new Listcell();
			if(lstResult.size()>0){
				for(VentaPasaje ventaPasaje : lstResult){
					listitem = new Listitem();
					listcell = new Listcell(ventaPasaje.getAgenciaPartida().getNombreCorto());
					listitem.appendChild(listcell);
					listcell = new Listcell(ventaPasaje.getTipoComprobante().getDenominacion());
					listitem.appendChild(listcell);
					listcell = new Listcell(ventaPasaje.getNumeroBoleto());
					listitem.appendChild(listcell);
					listcell = new Listcell(ventaPasaje.getNumeroControl());
					listitem.appendChild(listcell);
					listcell = new Listcell(ventaPasaje.getNumeroAsiento().toString());
					listitem.appendChild(listcell);
					listcell = new Listcell(Util.DatetoString(ventaPasaje.getFechaPartida(), Constantes.DATE_FORMAT));
					listitem.appendChild(listcell);
					listcell = new Listcell(ventaPasaje.getHoraPartida());
					listitem.appendChild(listcell);
					listcell = new Listcell(ventaPasaje.getCliente()!=null?ventaPasaje.getCliente().getNumeroDocumento():"");
					listitem.appendChild(listcell);
					listcell = new Listcell(ventaPasaje.getCliente()!=null?ventaPasaje.getCliente().getRazonSocial():"");
					listitem.appendChild(listcell);
					listcell = new Listcell(ventaPasaje.getPasajero().getNumeroDocumento());
					listitem.appendChild(listcell);
					listcell = new Listcell(ventaPasaje.getPasajero().getNombresApellidos());
					listitem.appendChild(listcell);
					listcell = new Listcell(ventaPasaje.getRuta().getOrigen()+" - "+ventaPasaje.getRuta().getDestino());
					listitem.appendChild(listcell);
					listcell = new Listcell(ventaPasaje.getUsuario().toString());
					listitem.appendChild(listcell);
					listcell = new Listcell(ventaPasaje.getAgencia().getNombreCorto());
					listitem.appendChild(listcell);
					listcell = new Listcell(Util.DatetoString(ventaPasaje.getFechaInsercion(), Constantes.DATE_TIME_FORMAT));
					listitem.appendChild(listcell);
					listitem.addEventListener(Events.ON_DOUBLE_CLICK, new EventListener<Event>() {
						@Override
						public void onEvent(Event e){
							reimprimir(e.getTarget().getId());
						}
					});
					listitem.setId(ventaPasaje.getId().toString());
					lbxComprobantes.appendChild(listitem);
				}
			}else
				DlgMessage.information(Messages.getString("WndComprobantesSinBoleto.information.noComprobantesSinBoleto"));

//			}


		}catch(Exception ex){
			DlgMessage.information(this.getClass().getSimpleName()+" "+ex.getMessage());
		}
	}

	private void reimprimir(String idVenta){
		try{

			boletoReimprimir = null;
			final VentaPasaje ventaOriginal = ServiceLocator.getVentaPasajesManager().buscarVentaById(Long.valueOf(idVenta));
			if(ventaOriginal.getTipoMovimiento().getId().intValue()==Constantes.ID_TIPMOV_ANULACION_SISTEMA)
				throw new ReimpresionByTipoMovimientoNoPermitidoException(Constantes.ID_TIPMOV_ANULACION_SISTEMA);
			else if(ventaOriginal.getTipoMovimiento().getId().intValue()==Constantes.ID_TIPMOV_DEVOLUCION)
				throw new ReimpresionByTipoMovimientoNoPermitidoException(Constantes.ID_TIPMOV_DEVOLUCION);
			else if(ServiceLocator.getDetalleManifiestoManager().validarVentaManifiesto(Long.valueOf(idVenta)))
				throw new ManifiestoImpresoException();
//			else if(ventaOriginal.getManifiesto()!=null)
//				throw new ManifiestoImpresoException();

			/*Valida si es un voucher corporativo o agencia de viajes y si esta dentro del tiempo permitido para la reimpresion - Impl 14/02/2016 - jabanto*/
			if(ventaOriginal.getCanalVenta().getId().intValue()==Constantes.ID_CANVEN_CORPORATIVO ||
					ventaOriginal.getCanalVenta().getId().intValue()==Constantes.ID_CANVEN_AGENCIA_VIAJES ||
					ventaOriginal.getCanalVenta().getId().intValue()==Constantes.ID_CANVEN_WEB){
				Date fechaActual=Constantes.FORMAT_DATE.parse(Constantes.FORMAT_DATE.format(new Date()));
				long diasDiferencia=(ventaOriginal.getFechaPartida().getTime()-fechaActual.getTime())/Constantes.MILISEGUNDOS_X_DIA;

				//Valida si la agencia a quien pertenece el voucher tiene una autorizacion para que se reimpriman su voucher con tiempo anticipado
				boolean tieneAutorizacion=ServiceLocator.getReimpresionAnticipadaManager().tieneAutorizacionReimpresion(ventaOriginal.getAgencia().getId(), ventaOriginal.getCanalVenta().getId());
				//Premite la reimpresion como maximo hasta 1 dia antes de la fecha de viaje
				if(!tieneAutorizacion && diasDiferencia>1){
					DlgMessage.information(Messages.getString("WndReimprimirBoleto.information.reimpresionAntesFecha"));
					return;
				}
			}
			/*Valida que el comprobante no este bloqueado por otro usuario - 21/02/2017 - jabanto*/
			ComprobantesBloqueados comprobantesBloqueados= ServiceLocator.getComprobantesBloqueadosManager().buscarPorId(ventaOriginal.getId());
			if(comprobantesBloqueados!=null){
				DlgMessage.information(Messages.getString("WndComprobantesSinBoleto.information.otroUsuarioReimpresion"));
				return;
			}
			Parametros parametros= ServiceLocator.getParametrosManager().buscarPorEstadoRegistro(Constantes.VALUE_ACTIVO);
			/*Realiza el bloqueo del comprobante - 21/02/2017 - jabanto*/
			ComprobantesBloqueadosID comprobantesBloqueadosID= new ComprobantesBloqueadosID();
			comprobantesBloqueadosID.setIdVentaPasaje(ventaOriginal.getId());
			comprobantesBloqueados= new ComprobantesBloqueados();
			comprobantesBloqueados.setComprobanteBloqueadoID(comprobantesBloqueadosID);
			comprobantesBloqueados.setVentaPasaje(ventaOriginal);
			comprobantesBloqueados.setUsuarioHardware(getUsuarioHardware());
			comprobantesBloqueados.setUsuario(getUsuario());
			comprobantesBloqueados.setTipoComprobante(ventaOriginal.getTipoComprobante());
			comprobantesBloqueados.setNumeroComprobante(ventaOriginal.getNumeroBoleto());
			comprobantesBloqueados.setModulo(0);
			Date dateExpiracion=new Date(Constantes.FORMAT_DATE_TIME_24H.parse(MyTime.dateTimeServer()).getTime()+(Constantes.MILISEGUNDOS_X_MINUTO*parametros.getTiempoExpiracionBloqueComprobante()));
			comprobantesBloqueados.setFechaExpiracionBloqueo(dateExpiracion);
			comprobantesBloqueados.setEstadoRegistro(Constantes.VALUE_ACTIVO);
			UtilData.auditarRegistro(comprobantesBloqueados, getUsuario(), Executions.getCurrent());
			ServiceLocator.getComprobantesBloqueadosManager().bloquearComprobante(comprobantesBloqueados);


			/*##End Begin 28/10/2016 - jabanto*/
//			UsuarioHardware usuarioHardware = (UsuarioHardware) this.getDesktop().getSession().getAttribute(Constantes.ATRIBUTO_USUARIO_HARDWARE);
//			final String boleto = UtilData.buscarEspecieValorada(Constantes.ID_TIPCOM_BOLETO_VIAJE, usuarioHardware.getId());

			/*##Begin 28/10/2016 - jabanto*/
			/*BEGIN 16/06/2021 - javalos - Correlativo by caja*/
//			EspecieValorada especieValorada=UtilData.buscarEspecieValorada((ventaOriginal.getCliente()!=null?Constantes.ID_TIPCOM_FACTURA:Constantes.ID_TIPCOM_BOLETA_VENTA), agencia, false);
//			wndReimpresion = createVentanaReimpresion(ventaOriginal, especieValorada.toString());
			ControlEspecieValorada controlEspecieValorada=UtilData.buscarEspecieValoradaByCaja((ventaOriginal.getCliente()!=null?Constantes.ID_TIPCOM_FACTURA:Constantes.ID_TIPCOM_BOLETA_VENTA), agencia, false, getUsuarioHardware(), null);
			wndReimpresion = createVentanaReimpresion(ventaOriginal, controlEspecieValorada.toString());
			/*END 16/06/2021 - javalos - Correlativo by caja*/
			this.appendChild(wndReimpresion);
			wndReimpresion.setMode(MODAL);
		}catch(ReimpresionByTipoMovimientoNoPermitidoException rtmnpex){
			if(rtmnpex.getTipoMovimiento().intValue()==Constantes.ID_TIPMOV_ANULACION_SISTEMA)
				DlgMessage.information(Messages.getString("WndReimprimirBoleto.information.reimpresionByAnulacionNoPermitido"));
			else if(rtmnpex.getTipoMovimiento().intValue()==Constantes.ID_TIPMOV_DEVOLUCION)
				DlgMessage.information(Messages.getString("WndReimprimirBoleto.information.reimpresionByDevolucionNoPermitido"));
		}catch(ManifiestoImpresoException miex){
			DlgMessage.information(Messages.getString("Generales.information.manifiestoImpreso"));
		}catch(Exception ex){
			ex.printStackTrace();
			DlgMessage.error(this.getClass().getSimpleName()+" "+ex.getMessage());
			WSFE.sendMail(ex.getMessage()+"\n VENPAS_ID:"+idVenta, "Metod: reimprimir()");
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

		final Window win = new Window("", "normal", false);
		win.setWidth("560px");

		caption = new Caption("REIMPRESION DE BOLETO", "resources/menu/menu_reimprimir.png");
		win.appendChild(caption);
		label = new Label("Se va a realizar la Reimpresion con los siguientes datos :");
		label.setStyle("font-size:12px !important");
		win.appendChild(label);

		win.appendChild(new Separator("horizontal"));

		groupbox = new Groupbox();
		groupbox.setClosable(false);
		caption = new Caption("Informaci�n del Servicio");
		groupbox.appendChild(caption);

		/*	Columna 1	*/
		column = new Column();
		column.setAlign("right");
		column.setWidth("80px");
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
		label = new Label("ORIGEN :");
		row.appendChild(label);
		text = new Textbox(ventaOriginal.getRuta().getOrigen());
		text.setReadonly(true);
		text.setWidth("80px");
		row.appendChild(text);
		label = new Label("DESTINO :");
		row.appendChild(label);
		text = new Textbox(ventaOriginal.getRuta().getDestino());
		text.setReadonly(true);
		text.setWidth("80px");
		row.appendChild(text);
		rows.appendChild(row);

		row = new Row();
		label = new Label("FECHA SALIDA :");
		row.appendChild(label);
		text = new Textbox(Util.DatetoString(ventaOriginal.getFechaPartida(), Constantes.DATE_FORMAT));
		text.setReadonly(true);
		text.setWidth("80px");
		row.appendChild(text);
		label = new Label("HORA SALIDA :");
		row.appendChild(label);
		text = new Textbox(ventaOriginal.getHoraPartida());
		text.setReadonly(true);
		text.setWidth("80px");
		row.appendChild(text);
		rows.appendChild(row);

		row = new Row();
		label = new Label("SERVICIO :");
		row.appendChild(label);
		text = new Textbox(ventaOriginal.getServicio().getDenominacion());
		text.setReadonly(true);
		text.setWidth("100px");
		row.appendChild(text);
		label = new Label("N� ASIENTO");
		row.appendChild(label);
		text = new Textbox(ventaOriginal.getNumeroAsiento().toString());
		text.setReadonly(true);
		text.setWidth("80px");
		row.appendChild(text);

		rows.appendChild(row);

		grid.appendChild(rows);
		groupbox.appendChild(grid);
		win.appendChild(groupbox);
		/* ***************************************** */
		groupbox = new Groupbox();
		groupbox.setClosable(false);
		caption = new Caption("Informacion del Comprobante Electr�nico");
		groupbox.appendChild(caption);

		grid = new Grid();
		rows = new Rows();
		columns = new Columns();
		/*	Columna 1	*/
		column = new Column();
		column.setAlign("right");
		column.setWidth("100px");
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
		label = new Label("RUC CLIENTE :");
		row.appendChild(label);
		text = new Textbox(ventaOriginal.getCliente()!=null?ventaOriginal.getCliente().getNumeroDocumento():"");
		text.setWidth("100px");
		text.setReadonly(true);
		row.appendChild(text);
		label = new Label("AGENCIA :");
		row.appendChild(label);
		text = new Textbox(ventaOriginal.getAgencia().getDenominacion());
		text.setReadonly(true);
		text.setWidth("120px");
		row.appendChild(text);
		rows.appendChild(row);

		row = new Row();
		if(ventaOriginal.getCliente()!=null)
			label = new Label("NUEVA N� FACTURA :");
		else
			label = new Label("NUEVA N� BOLETA :");
		label.setStyle("color:blue");
		row.appendChild(label);
		text = new Textbox(boleto);
		text.setReadonly(true);
		text.setWidth("100px");
		row.appendChild(text);
		label = new Label("N� BOLETO ANTERIOR :");
		row.appendChild(label);
		text = new Textbox(ventaOriginal.getNumeroBoleto().toString());
		text.setReadonly(true);
		text.setWidth("80px");
		row.appendChild(text);
		rows.appendChild(row);

		row = new Row();
		label = new Label("COMPROBANTE :");
		row.appendChild(label);
		cmbTipoComprobante = new Combobox();
		cmbTipoComprobante.setWidth("120px");
		TreeMap<String, Object> criteriosBusqueda = new TreeMap<>();
		criteriosBusqueda.put("rubro", TipoComprobante.RUBRO_PASAJES);
		UtilData.cargarDataCombo(cmbTipoComprobante, TipoComprobante.class, criteriosBusqueda, false);
		row.appendChild(cmbTipoComprobante);
		label = new Label("COMPROBANTE ANTERIOR :");
		row.appendChild(label);
		text = new Textbox(ventaOriginal.getTipoComprobante().getDenominacion());
		text.setReadonly(true);
		text.setWidth("120px");
		row.appendChild(text);
		rows.appendChild(row);

		row = new Row();
		label = new Label("FORMA DE PAGO :");
		row.appendChild(label);
		text = new Textbox(ventaOriginal.getFormaPago().getDenominacion());
		text.setWidth("100px");
		text.setReadonly(true);
		row.appendChild(text);
		label = new Label("FORMA PAGO ANTERIOR :");
		row.appendChild(label);
		text = new Textbox(ventaOriginal.getFormaPago().getDenominacion());
		text.setReadonly(true);
		text.setWidth("120px");
		row.appendChild(text);
		rows.appendChild(row);

		row = new Row();
		row.setSpans("1,3");
		label = new Label("IMPORTE PAGADO :");
		row.appendChild(label);
		text = new Textbox(Util.toNumberFormat(ventaOriginal.getTarifa()+ventaOriginal.getRecargo()-ventaOriginal.getDescuento(),2));
		text.setWidth("100px");
		text.setReadonly(true);
		row.appendChild(text);
		rows.appendChild(row);

		grid.appendChild(rows);

		groupbox.appendChild(grid);
		win.appendChild(groupbox);

		onSelectDefaultTipoComprobante(ventaOriginal);

		grid = new Grid();
//		columns = new Columns();
//		column = new Column();
//		column.setAlign("center");
//		columns.appendChild(column);
//		column = new Column();
//		column.setAlign("center");
//		columns.appendChild(column);
//		grid.appendChild(columns);
		rows = new Rows();
		row = new Row();
		row.setSpans("0,1");
		Button button = new Button("Continuar", "resources/mp_aceptarEnabled.png");
		button.setMold("trendy");
		button.setAutodisable("self");
		button.addEventListener(Events.ON_CLICK, new EventListener<Event>() {
			@Override
			public void onEvent(Event e) throws Exception {
				//Implementado 05/08/2014 - jabanto
				/* Valida que el RC o Voucher a�n no haya sido Reimpreso.*/
				VentaPasaje ultimoRegistro=ServiceLocator.getVentaPasajesManager().buscarUltimoRegistro(ventaOriginal.getId());
				if(ultimoRegistro!=null){
					if(ultimoRegistro.getTipoComprobante().getId().intValue()==Constantes.ID_TIPCOM_BOLETO_VIAJE ||
							ultimoRegistro.getTipoComprobante().getId().intValue()==Constantes.ID_TIPCOM_BOLETA_VENTA ||
									ultimoRegistro.getTipoComprobante().getId().intValue()==Constantes.ID_TIPCOM_FACTURA ||
							ultimoRegistro.getTipoMovimiento().getId().intValue()==Constantes.ID_TIPMOV_ANULACION ||
							ultimoRegistro.getTipoMovimiento().getId().intValue()==Constantes.ID_TIPMOV_ANULACION_SISTEMA){
						DlgMessage.information("El "+ventaOriginal.getTipoComprobante().getDenominacion().toLowerCase()+" "+Messages.getString("WndComprobantesSinBoleto.information.comprovanteReimpreso"));
						return;
					}
				}
				ComprobantesBloqueados comprobantesBloqueados=ServiceLocator.getComprobantesBloqueadosManager().buscarPorId(ventaOriginal.getId());
				if(comprobantesBloqueados==null){
					DlgMessage.information(Messages.getString("WndComprobantesSinBoleto.information.expiracionBloqueo"));
					return;
				}else if(comprobantesBloqueados.getUsuario().getId().intValue()!=getUsuario().getId().intValue()){
					DlgMessage.information(Messages.getString("WndComprobantesSinBoleto.information.reimpresionOtroUsuario"));
					return;
				}else if(comprobantesBloqueados.getUsuarioHardware().getId().intValue()!=getUsuarioHardware().getId().intValue()){
					DlgMessage.information(Messages.getString("WndComprobantesSinBoleto.information.reimpresionEnOtroEquipo"));
					return;
				}

				Messagebox.show(Messages.getString("WndComprobantesSinBoleto.question.confirmarReimpresion"), DlgMessage.NOMBREAPLICACION, DlgMessage.BTN_YESNO, Messagebox.QUESTION, new EventListener<Event>() {
					@Override
					public void onEvent(Event e){
						try{
							if(e.getName().equals("onYes")){
								//Implementado 03/06/2015 - jabanto
								/* Vuelve a Validar que el RC o Voucher a�n no haya sido Reimpreso.*/
								VentaPasaje ultimoRegistro=ServiceLocator.getVentaPasajesManager().buscarUltimoRegistro(ventaOriginal.getId());
								if(ultimoRegistro!=null){
									if(ultimoRegistro.getTipoComprobante().getId().intValue()==Constantes.ID_TIPCOM_BOLETO_VIAJE ||
											ultimoRegistro.getTipoComprobante().getId().intValue()==Constantes.ID_TIPCOM_BOLETA_VENTA ||
											ultimoRegistro.getTipoComprobante().getId().intValue()==Constantes.ID_TIPCOM_FACTURA ||
											ultimoRegistro.getTipoMovimiento().getId().intValue()==Constantes.ID_TIPMOV_ANULACION ||
											ultimoRegistro.getTipoMovimiento().getId().intValue()==Constantes.ID_TIPMOV_ANULACION_SISTEMA){
										DlgMessage.information("El "+ventaOriginal.getTipoComprobante().getDenominacion().toLowerCase()+" "+Messages.getString("WndComprobantesSinBoleto.information.comprovanteReimpreso"));
										return;
									}
								}

								ComprobantesBloqueados comprobantesBloqueados=ServiceLocator.getComprobantesBloqueadosManager().buscarPorId(ventaOriginal.getId());
								if(comprobantesBloqueados==null){
									DlgMessage.information(Messages.getString("WndComprobantesSinBoleto.information.expiracionBloqueo"));
									return;
								}else if(comprobantesBloqueados.getUsuario().getId().intValue()!=getUsuario().getId().intValue()){
									DlgMessage.information(Messages.getString("WndComprobantesSinBoleto.information.reimpresionOtroUsuario"));
									return;
								}else if(comprobantesBloqueados.getUsuarioHardware().getId().intValue()!=getUsuarioHardware().getId().intValue()){
									DlgMessage.information(Messages.getString("WndComprobantesSinBoleto.information.reimpresionEnOtroEquipo"));
									return;
								}


								//Clonamos la venta original para luego a este actualizar el campo venta pasaje referencial (VENPAS_IDREF) 16/12/2013 jabanto
								VentaPasaje ventaPasajereRef= (VentaPasaje) ventaOriginal.clone();
								ventaPasajereRef.setNumeroBoletoAnterior(boleto);
								UtilData.auditarRegistro(ventaPasajereRef,true, getUsuario(), Executions.getCurrent());
								//----->>>

								boletoReimprimir = (VentaPasaje)ventaOriginal.clone();
								ventaOriginal.setId(null);
								ventaOriginal.setTipoMovimiento(new TipoMovimiento(Constantes.ID_TIPMOV_ANULACION_SISTEMA));
								ventaOriginal.setFechaLiquidacion(fechaLiquidacion);
								ventaOriginal.setAgencia(getAgencia());
								ventaOriginal.setUsuario(getUsuario());
								UtilData.auditarRegistro(ventaOriginal, getUsuario(), Executions.getCurrent());



								boletoReimprimir.setId(null);
								boletoReimprimir.setVentaPasaje(ventaPasajereRef);
//								boletoReimprimir.setVentaPasaje(ventaOriginal);
								boletoReimprimir.setNumeroBoleto(boleto);
								boletoReimprimir.setNumeroBoletoAnterior(ventaOriginal.getNumeroBoleto());
								boletoReimprimir.setUsuario(getUsuario());
								boletoReimprimir.setAgencia(getAgencia());
//								boletoReimprimir.setCanalVenta(canalVenta);
								boletoReimprimir.setLiquidacion(null);
								boletoReimprimir.setFechaTransferencia(null);
								if(boletoReimprimir.getTipoComprobante().getId().equals(Constantes.ID_TIPCOM_RECIBO_CAJA))
									boletoReimprimir.setTipoMovimiento(new TipoMovimiento(Constantes.ID_TIPMOV_PREPAGADO));
								else if(ventaOriginal.getTipoComprobante().getId().intValue()==Constantes.ID_TIPCOM_VOUCHER_AGENCIA_VIAJES
										|| ventaOriginal.getTipoComprobante().getId().intValue()==Constantes.ID_TIPCOM_VOUCHER_CORPORATIVO)
									boletoReimprimir.setTipoMovimiento(new TipoMovimiento(Constantes.ID_TIPMOV_CREDITO));
//								boletoReimprimir.setTipoComprobante(new TipoComprobante(Constantes.ID_TIPCOM_BOLETO_VIAJE));
								boletoReimprimir.setTipoComprobante((TipoComprobante)cmbTipoComprobante.getSelectedItem().getValue());
								boletoReimprimir.setUsuarioHardware((UsuarioHardware)getDesktop().getSession().getAttribute(Constantes.ATRIBUTO_USUARIO_HARDWARE));
								boletoReimprimir.setEstadoRegistro(Constantes.VALUE_ACTIVO);
								boletoReimprimir.setFechaLiquidacion(fechaLiquidacion);
								/*BEGIN 16/06/2021 - javalos - Correlativo by caja*/
								boletoReimprimir.setUsuarioHardware(getUsuarioHardware());
								/*END 16/06/2021 - javalos - Correlativo by caja*/

//								//Coloca en el campo estado documento PAG a todos los boletos o RC que no sen credito. - jabanto 16/10/2014
								if(boletoReimprimir.getFormaPago().getId().intValue()==Constantes.ID_FORPAG_CREDITO)
									boletoReimprimir.setEstadoDocumento(Constantes.ESTADO_DOCUMENTO_ACTIVO);
								else
									boletoReimprimir.setEstadoDocumento(Constantes.ESTADO_DOCUMENTO_PAGADO);

								UtilData.auditarRegistro(boletoReimprimir, getUsuario(), Executions.getCurrent());
								int result = ServiceLocator.getVentaPasajesManager().reimprimirBoleto(ventaOriginal, boletoReimprimir, false);
								
								//Actualiza el correlativo - jabanto - 22/01/2024
								ServiceLocator.getVentaPasajesManager().actualizarCorrelativoComprobante(boletoReimprimir, true);
								
								if(result==Constantes.CORRECT){
									boletoReimprimir = ServiceLocator.getVentaPasajesManager().buscarVentaById(boletoReimprimir.getId());

									/*###End Begin 28/10/2016 - jabanto*/
//									/*Implementacion para el nueno formato 01/02/2016 - jabanto */
//									boolean formato=UtilData.getFormatoImprecion(getAgencia().getId(), getTipocomprobante().getId(), getUsuarioHardware().getId());
//									File file=CreateDocument.crearBoleto(boletoReimprimir,formato);

//									if(getUsuarioHardware().getPrintApplet().intValue()==Constantes.TRUE_VALUE){
//										String fileBoleto = Constantes.URL_FORMATOS_BOLETOS +Constantes.CLAVE_PAHT+ boletoReimprimir.getNumeroControl()+".txt";
//										Window win = (Window)Executions.createComponents("imprimir.zul", null, null);
//										win.setAttribute("formato", WndImprimir.FORMAT_BOLETO);
//										win.setAttribute("msg", "Imprimiendo boleto de viaje "+boletoReimprimir.getNumeroBoleto()+"...");
//										win.setAttribute("urlDocumento", fileBoleto);
//										win.doPopup();
//										DlgMessage.information(Messages.getString("WndComprobantesSinBoleto.information.exitoReimpresion")+" "+boletoReimprimir.getNumeroControl());
//										wndReimpresion.onClose();
//									}else{
//										//Descarga el archivo para la impresion
//										Util.descargarArchivo(file);
//										wndReimpresion.onClose();
//									}

									/*##Begin 28/10/2016 -jabanto*/
									List<VentaPasaje>listVentaPasaje= new ArrayList<>();
									listVentaPasaje.add(boletoReimprimir);
									WSFE.sendVenta(listVentaPasaje, wndComprobantesSinBoleto, true, null, Constantes.NUMERO_COPIAS_COMPROBANTE_PASAJES);

									wndReimpresion.onClose();
									buscarComprobantes();

									ServiceLocator.getComprobantesBloqueadosManager().desbloquearComprobante(ventaOriginal.getId());
								}
							}
						}catch(NumeroBoletoDuplicadoException nbdex){
							DlgMessage.information(Messages.getString("WndComprobantesSinBoleto.information.numeroBoletoVendido"));
							WSFE.sendMail("El n�mero de boleto ya fue vendido, por favor consulte el N�mero de Boleto nuevamente. \n VENPAS_ID:"+ventaOriginal.getId(), "Metod: createVentanaReimpresion");
						}catch(Exception ex){
							ex.printStackTrace();
							DlgMessage.information(this.getClass().getSimpleName()+" "+ex.getMessage());
							WSFE.sendMail(ex.getMessage()+"\n VENPAS_ID:"+ventaOriginal.getId(), "Metod: createVentanaReimpresion");
						}
					}
				});
			}
		});
		Hbox hbox=new Hbox();
//		hbox.setWidth("560px");

		button.setHeight("28px");
		hbox.appendChild(button);
//		row.appendChild(button);

		Separator separator=new Separator();
		separator.setWidth("10px");
		hbox.appendChild(separator);


		button = new Button("Cancelar", "resources/mp_cancelarEnabled.png");
		button.addEventListener(Events.ON_CLICK, new EventListener<Event>() {
			@Override
			public void onEvent(Event e){
				win.onClose();
				try {
					ComprobantesBloqueados comprobantesBloqueados= new ComprobantesBloqueados();
					comprobantesBloqueados.setVentaPasaje(ventaOriginal);
					comprobantesBloqueados.setUsuario(getUsuario());
					comprobantesBloqueados.setUsuarioHardware(getUsuarioHardware());
					ServiceLocator.getComprobantesBloqueadosManager().desbloquearComprobante(comprobantesBloqueados);

				} catch (Exception e1) {
					e1.printStackTrace();
					DlgMessage.information(e1.getMessage());
				}
			}
		});
		button.setMold("trendy");
		button.setHeight("28px");
		button.setFocus(true);
		hbox.appendChild(button);

		Div div=new Div();
		div.setAlign("center");
		div.setWidth(win.getWidth());
		Toolbar toolbar=new Toolbar();
		div.appendChild(hbox);

		toolbar.appendChild(div);
		row.appendChild(toolbar);

		rows.appendChild(row);

		grid.appendChild(rows);
		win.appendChild(grid);
		return win;
	}

	/**
	 * Para seleccionar el Tipo de Comprobante por defecto
	 */
	private void onSelectDefaultTipoComprobante(VentaPasaje ventaOrieginal){
		for(Comboitem comboitem : cmbTipoComprobante.getItems()){
			/*##End Begin 28/10/2016 - jabanto*/
			/*	Si la agencia pertenece a TEPSA*/
//			if(comboitem.getValue() instanceof TipoComprobante && ((TipoComprobante)comboitem.getValue()).getId().intValue()==Constantes.ID_TIPCOM_BOLETO_VIAJE)
//				cmbTipoComprobante.setSelectedItem(comboitem);

			/*##Begin 28/10/2016 - jabanto*/
			if(comboitem.getValue() instanceof TipoComprobante){
				TipoComprobante tipoComprobante=comboitem.getValue();
				if(tipoComprobante.getId().intValue()==Constantes.ID_TIPCOM_BOLETA_VENTA && ventaOrieginal.getCliente()==null)
					cmbTipoComprobante.setSelectedItem(comboitem);
				else if (tipoComprobante.getId().intValue()==Constantes.ID_TIPCOM_FACTURA && ventaOrieginal.getCliente()!=null)
					cmbTipoComprobante.setSelectedItem(comboitem);
			}

		}
		cmbTipoComprobante.setDisabled(true);
	}
}
