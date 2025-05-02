/**
 * Proyecto		: SISVYR
 * Sistema		: Sistema de Ventas y Reservas
 * Descripci�n	:
 * Autor		: Jos� Abanto
 * Fecha		: 27/01/2017
 * Hora			: 16:07:10
 */
package com.cystesoft.vyrbus.view.ctrl;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
import org.zkoss.zul.Div;
import org.zkoss.zul.Grid;
import org.zkoss.zul.Label;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Radio;
import org.zkoss.zul.Row;
import org.zkoss.zul.Rows;
import org.zkoss.zul.Separator;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Toolbarbutton;
import org.zkoss.zul.Window;

import com.cystesoft.vyrbus.model.bean.Agencia;
import com.cystesoft.vyrbus.model.bean.Liquidacion;
import com.cystesoft.vyrbus.model.bean.TipoAgencia;
import com.cystesoft.vyrbus.model.bean.Usuario;
import com.cystesoft.vyrbus.model.bean.VentaPasaje;
import com.cystesoft.vyrbus.service.exceptions.PerdidaServicioException;
import com.cystesoft.vyrbus.service.locator.ServiceLocator;
import com.cystesoft.vyrbus.service.util.Constantes;
import com.cystesoft.vyrbus.service.util.Messages;
import com.cystesoft.vyrbus.service.util.MyTime;
import com.cystesoft.vyrbus.service.util.Util;
import com.cystesoft.vyrbus.service.util.UtilData;
import com.cystesoft.vyrbus.service.util.VentasNotas;
import com.cystesoft.vyrbus.service.util.WSFE;
import com.cystesoft.vyrbus.view.ui.DlgMessage;
import com.cystesoft.vyrbus.view.ui.WndBase;

/**
 * @author Jose Abanto
 *
 */
public class WndAnulacionDocumentos extends WndBase{

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	private Radio rdAnulacionRegular;
	private Radio rdAnulacionPersonalizada;
	private Textbox txtNumeroComprobante;
	private Row rowRangoFechas;
	private Row rowAgencia;
	private Row rowOpcionNC;
	private Row rowAplicar;
	private Datebox dtbxFechaInicio;
	private Datebox dtbxFechaFin;
	private Combobox cmbTipoAgencia;
	private Combobox cmbAgencia;
	private Radio rdRegular;
	private Radio rdSoloNC;
	private Radio rdNCNuevoComprobante;
	private Checkbox ckbxGeneraMovimientoSistema;
	private Listbox ltbxAnulacionComprobantes;
	private Button btnBuscar;
	private Label lblInfoAnulMasivo;
	private Button btnAplicar;

	private Label lblAdvertencia= null;
	private Combobox cmbUsuarioLiq= null;
	private Combobox cmbAgenciaLiq=null;
	private Datebox dtbxFechaNc = null;
	private Liquidacion liquidacion=null;
	private Button btnProcesarAnulacion=null;
	private Textbox txtMotivoAnulacion=null;
	private Window wndAnulacionBoleto;

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.view.ui.WndBase#onCreate()
	 */
	@Override
	public void onCreate() throws Exception {
		dtbxFechaInicio.setValue(new Date());
		dtbxFechaFin.setValue(new Date());

		/*Caga los tipos de agencia*/
		List<TipoAgencia>tiposAgencias=ServiceLocator.getTipoAgenciaManager().buscarPorEstadoRegistro(Constantes.VALUE_ACTIVO, "denominacion");
		UtilData.cargarGenericData(cmbTipoAgencia, false);
		for(TipoAgencia tipoAgencia:tiposAgencias){
			if(tipoAgencia.getId().intValue()!=Constantes.ID_TIPAGE_TEPSA){
				Comboitem comboitem= new Comboitem(tipoAgencia.getDenominacion());
				comboitem.setValue(tipoAgencia);
				cmbTipoAgencia.appendChild(comboitem);
			}
		}
		cmbTipoAgencia.setSelectedIndex(0);
	}


	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.view.ui.WndBase#initComponents()
	 */
	@Override
	public void initComponents() {
		rdAnulacionRegular=(Radio)this.getFellow("rdAnulacionRegular");
		rdAnulacionPersonalizada=(Radio)this.getFellow("rdAnulacionPersonalizada");
		txtNumeroComprobante=(Textbox)this.getFellow("txtNumeroComprobante");
		rowRangoFechas=(Row)this.getFellow("rowRangoFechas");
		rowAgencia=(Row)this.getFellow("rowAgencia");
		rowOpcionNC=(Row)this.getFellow("rowOpcionNC");
		rowAplicar=(Row)this.getFellow("rowAplicar");
		dtbxFechaInicio=(Datebox)this.getFellow("dtbxFechaInicio");
		dtbxFechaFin=(Datebox)this.getFellow("dtbxFechaFin");
		cmbTipoAgencia=(Combobox)this.getFellow("cmbTipoAgencia");
		cmbAgencia=(Combobox)this.getFellow("cmbAgencia");
		rdRegular=(Radio)this.getFellow("rdRegular");
		rdSoloNC=(Radio)this.getFellow("rdSoloNC");
		rdNCNuevoComprobante=(Radio)this.getFellow("rdNCNuevoComprobante");
		ckbxGeneraMovimientoSistema=(Checkbox)this.getFellow("ckbxGeneraMovimientoSistema");
		ltbxAnulacionComprobantes=(Listbox)this.getFellow("ltbxAnulacionComprobantes");
		btnBuscar=(Button)this.getFellow("btnBuscar");
		lblInfoAnulMasivo=(Label)this.getFellow("lblInfoAnulMasivo");
		btnAplicar=(Button)this.getFellow("btnAplicar");

		cmbTipoAgencia.addEventListener(Events.ON_CHANGE, new EventListener<Event>() {
			@Override
			public void onEvent(Event event) throws Exception {
				try {
					onLoadAgencias();
				} catch (Exception e) {
					e.printStackTrace();
					DlgMessage.error(e.getMessage());
				}
			}
		});

		btnBuscar.addEventListener(Events.ON_CLICK, new EventListener<Event>() {
			@Override
			public void onEvent(Event event) throws Exception {
				try {
					if(!(txtNumeroComprobante.getText().trim().isEmpty())){
						txtNumeroComprobante.setText(Util.autocompleNumberBoleto(txtNumeroComprobante.getText()));
						buscarComprobanteByNumero();
					}else
						buscarComprobantesByFechas();
				} catch (Exception e) {
					e.printStackTrace();
					DlgMessage.error(e.getMessage());
				}
			}
		});

		txtNumeroComprobante.addEventListener(Events.ON_OK, new EventListener<Event>() {
			@Override
			public void onEvent(Event event) throws Exception {
				try {
					if(!(txtNumeroComprobante.getText().trim().isEmpty())){
						txtNumeroComprobante.setText(Util.autocompleNumberBoleto(txtNumeroComprobante.getText()));
						buscarComprobanteByNumero();
					}else
						buscarComprobantesByFechas();
				} catch (Exception e) {
					e.printStackTrace();
					DlgMessage.error(e.getMessage());
				}
			}
		});

		btnAplicar.addEventListener(Events.ON_CLICK, new EventListener<Event>() {
			@Override
			public void onEvent(Event event) throws Exception {
				if(ltbxAnulacionComprobantes.getSelectedItems().size()>0){
					aplicar();
				}
			}
		});
	}

	public void onCheckedAnulacionPersonalizada(){
		try {
				Util.limpiarListbox(ltbxAnulacionComprobantes);
				habilitarAnulacionPersonalizada(rdAnulacionPersonalizada.isChecked());
				limpiarControlesAnulacionPersonalizada();
				rdSoloNC.setChecked(true);
		} catch (Exception e) {
			e.printStackTrace();
			DlgMessage.error(e.getMessage());
		}
	}

	public void onCheckedAnulacionRegular(){
		try {
			Util.limpiarListbox(ltbxAnulacionComprobantes);
			habilitarAnulacionPersonalizada(rdAnulacionPersonalizada.isChecked());
			limpiarControlesAnulacionPersonalizada();
		} catch (Exception e) {
			e.printStackTrace();
			DlgMessage.error(e.getMessage());
		}
	}

	private void limpiarControlesAnulacionPersonalizada()throws Exception{
		Util.limpiarCombobox(cmbAgencia);
		txtNumeroComprobante.setText("");
		dtbxFechaInicio.setValue(new Date());
		dtbxFechaFin.setValue(new Date());
		cmbTipoAgencia.setSelectedIndex(0);
		UtilData.cargarGenericData(cmbAgencia, false);
		rdRegular.setChecked(true);
		ckbxGeneraMovimientoSistema.setChecked(true);
		ckbxGeneraMovimientoSistema.setDisabled(true);

	}

	/**
	 * habilita los controles para la anulacion personalizada
	 * @param enabled: true=habilta: false=Dseshabilita
	 * @throws Exception
	 */
	private void habilitarAnulacionPersonalizada(boolean visible)throws Exception{
		rowRangoFechas.setVisible(visible);
		rowAgencia.setVisible(visible);
		rowOpcionNC.setVisible(visible);
		rowAplicar.setVisible(visible);
	}

	/**
	 * Carga las agencias, segun el tipo de agencia seleccionada
	 * @throws Exception
	 */
	private void onLoadAgencias()throws Exception{
		Util.limpiarCombobox(cmbAgencia);
		if(cmbTipoAgencia.getSelectedItem().getValue() instanceof TipoAgencia)
			UtilData.cargarAgenciaXtipoAgencia(cmbAgencia, ((TipoAgencia)cmbTipoAgencia.getSelectedItem().getValue()).getId(), false);
		else
			UtilData.cargarGenericData(cmbAgencia, false);
		cmbAgencia.setSelectedIndex(0);
	}

	public void onchangeAgencia()throws Exception{
		/* *******************************************************************/
		/* DE FORMA EXCLUCIVA PARA EL CLIENTE CORPORATIVO MOTA ENGIL - (SE HABILITO PARA TODOS - 03/02/2016) -
		 * Ya que es posible que a la hora de anular el comprobante con N.C. no sea necesario anular el registro con un tipo de movimiento del Sistema (5)*/
		/* *******************************************************************/
//		ckbxGeneraMovimientoSistema.setDisabled(true);
//		if(cmbAgencia.getSelectedIndex()>0){
//			if(((Agencia)cmbAgencia.getSelectedItem().getValue()).getId().intValue()==Constantes.ID_AGENCIA_MOTA_ENGIL)
//				ckbxGeneraMovimientoSistema.setDisabled(false);
//		}
	}

	/**
	 * Realiza la busqueda del comprobante por su numero
	 * @throws Exception
	 */
	private void buscarComprobanteByNumero()throws Exception{
		List<VentaPasaje> lstVentaPasaje = ServiceLocator.getVentaPasajesManager().buscarBoletosDevolucion(null, null, txtNumeroComprobante.getText().trim().toUpperCase());
		if(lstVentaPasaje.size()>0){
			/*Busca por el ultimo movimiento agrupado por numero de control*/
			String nroControl = lstVentaPasaje.get(0).getNumeroControl();
			List<VentaPasaje> lstResult= ServiceLocator.getVentaPasajesManager().buscarBoletosDevolucion(null, nroControl, null);
			/*Busca por el identificador del ultmio movimiento, el registro a validar*/
			final VentaPasaje ventaAnular=ServiceLocator.getVentaPasajesManager().buscarVentaById(lstResult.get(0).getId());
			if(ventaAnular.getTipoMovimiento().getId().intValue()==Constantes.ID_TIPMOV_ANULACION_SISTEMA)
				lstVentaPasaje= new ArrayList<>();
			else if (ventaAnular.getTipoMovimiento().getId().intValue()==Constantes.ID_TIPMOV_ANULACION)
				lstVentaPasaje= new ArrayList<>();
			else if(ventaAnular.getTipoMovimiento().getId().intValue()==Constantes.ID_TIPMOV_DEVOLUCION)
				lstVentaPasaje= new ArrayList<>();
		}
		onLoadComprobantes(lstVentaPasaje);
	}
	/**
	 * Realiza la busqueda de los comprobantes por rango de fechas y segun otros parametros seleccionados
	 * @throws Exception
	 */
	private void buscarComprobantesByFechas()throws Exception{
		/*Siempre da prioridad a la busqueda por numero de Comprobante*/
		if(!(rdAnulacionRegular.isChecked())){
			if(!(cmbTipoAgencia.getSelectedItem().getValue() instanceof TipoAgencia)){
				DlgMessage.information(Messages.getString("wndAnulacionComprobantes.information.noSelectTipoAgencia"),cmbTipoAgencia);
				return;
			}else if (cmbAgencia.getSelectedIndex()<=0){
				DlgMessage.information(Messages.getString("wndAnulacionComprobantes.information.noSelectAgencia"),cmbAgencia);
				return;
			}else{
				/*Valida que no se exceda los 30 d�as de consulta*/
				Date dateFin=Constantes.FORMAT_DATE.parse(Constantes.FORMAT_DATE.format(dtbxFechaFin.getValue()));
				Date dateIni=Constantes.FORMAT_DATE.parse(Constantes.FORMAT_DATE.format(dtbxFechaInicio.getValue()));
				long numDias=(dateFin.getTime()-dateIni.getTime())/Constantes.MILISEGUNDOS_X_DIA;
				if(numDias>30){
					DlgMessage.information(Messages.getString("wndAnulacionComprobantes.information.max30Dias"),dtbxFechaFin);
					return;
				}
			}
			String orderByX="FechaVenta, boleto";
			String fechaInicio=Constantes.FORMAT_DATE.format(dtbxFechaInicio.getValue());
			String fechaFin=Constantes.FORMAT_DATE.format(dtbxFechaFin.getValue());
			Integer agenciaId=((Agencia)cmbAgencia.getSelectedItem().getValue()).getId();
			Agencia agencia=ServiceLocator.getAgenciaManager().buscarPorId(agenciaId.longValue());

			List<VentaPasaje> lstVentaPasaje =ServiceLocator.getVentaPasajesManager().buscarDetalleVentasAgencia(fechaInicio, fechaFin, agencia.getConcesionario().getRuc(), null,orderByX,true,true,null,null,true);
			onLoadComprobantes(lstVentaPasaje);
		}else{
			buscarComprobanteByNumero();
		}
	}

	/**
	 * carga los comprobantes
	 * @param lstComprobantes	: lista de comprobantes a cargar
	 * @throws Exception
	 */
	private void onLoadComprobantes(List<VentaPasaje> lstComprobantes)throws Exception{
		Util.limpiarListbox(ltbxAnulacionComprobantes);
		ltbxAnulacionComprobantes.setCheckmark(false);
		ltbxAnulacionComprobantes.setMultiple(false);
		ltbxAnulacionComprobantes.setRows(0);
		lblInfoAnulMasivo.setVisible(false);
		if(lstComprobantes.size()>1){
			ltbxAnulacionComprobantes.setCheckmark(true);
			ltbxAnulacionComprobantes.setMultiple(true);
			lblInfoAnulMasivo.setVisible(true);
		}
		if(lstComprobantes.size()>10)
			ltbxAnulacionComprobantes.setRows(10);

		for(VentaPasaje ventaPasaje : lstComprobantes){
			if(ventaPasaje.getTipoMovimiento().getId().intValue()!=Constantes.ID_TIPMOV_ANULACION &&
					ventaPasaje.getTipoMovimiento().getId().intValue()!=Constantes.ID_TIPMOV_DEVOLUCION &&
					ventaPasaje.getTipoMovimiento().getId().intValue()!=Constantes.ID_TIPMOV_ANULACION_SISTEMA){
				Listitem item=new Listitem();
				Listcell cell= new Listcell(Constantes.FORMAT_DATE.format(ventaPasaje.getFechaLiquidacion()));
				cell.setStyle("font-size:11px !important");
				item.appendChild(cell);
				cell= new Listcell(ventaPasaje.getNumeroBoleto());
				cell.setStyle("font-size:11px !important");
				item.appendChild(cell);
				cell= new Listcell(ventaPasaje.getFormaPago().getDenominacion());
				item.appendChild(cell);
				cell= new Listcell(ventaPasaje.getRuta().toString());
				item.appendChild(cell);
				cell= new Listcell(ventaPasaje.getFechaPartida()!=null?Constantes.FORMAT_DATE.format(ventaPasaje.getFechaPartida())+" "+ventaPasaje.getHoraPartida():"");
				cell.setStyle("font-size:11px !important");
				item.appendChild(cell);
				cell= new Listcell(ventaPasaje.getNumeroAsiento()!=null?ventaPasaje.getNumeroAsiento().toString():"");
				cell.setStyle("font-size:11px !important");
				item.appendChild(cell);
				cell= new Listcell(ventaPasaje.getPasajero().toString());
				item.appendChild(cell);
				cell= new Listcell(ventaPasaje.getCliente()!=null?ventaPasaje.getCliente().toString():"");
				item.appendChild(cell);
				if(rdAnulacionRegular.isChecked()){
					Toolbarbutton btnAnular= new Toolbarbutton("Anular");
					btnAnular.setStyle("text-transform:none;color:red");
					cell= new Listcell();
					cell.appendChild(btnAnular);
					item.appendChild(cell);
					btnAnular.setAttribute(VentaPasaje.class.getName(), ventaPasaje);
					btnAnular.addEventListener(Events.ON_CLICK, new EventListener<Event>() {
						@Override
						public void onEvent(Event event) throws Exception {
							try {
								//Se quita la validacion para poder anular el boleto
//								if(((VentaPasaje)event.getTarget().getAttribute(VentaPasaje.class.getName())).getTipoTransaccion().equals(Constantes.TIPO_OPERACION_PERDIDA_SERVICIO))
//									throw new PerdidaServicioException();
								createWindowAnulacion((VentaPasaje)event.getTarget().getAttribute(VentaPasaje.class.getName()));

							}catch(PerdidaServicioException psex) {
								DlgMessage.information("No se puede anular un comprobante que esta marcado como Perdida de Servicio");
							} catch (Exception e) {
								e.printStackTrace();
								DlgMessage.error(e.getMessage());
							}
						}
					});
				}
				item.setValue(ventaPasaje);
				ltbxAnulacionComprobantes.appendChild(item);
			}
		}
	}

	/**
	 * Realiza la anulacion o aplica la nota de credito al o los comprobantes seleccionados
	 */
	private void aplicar(){
		try {
			if(ltbxAnulacionComprobantes.getSelectedItems().size()==0){
				DlgMessage.information("Debe de seleccionar el comprobante que desea Anular");
				return;
			}else if (ltbxAnulacionComprobantes.getSelectedItems().size()>20){
				DlgMessage.information("El modo Anulación Masiva solamente se puede aplicar a un máximo de 20 Comprobantes");
				return;
			}else if(((VentaPasaje)ltbxAnulacionComprobantes.getSelectedItem().getValue()).getTipoTransaccion().equals(Constantes.TIPO_OPERACION_PERDIDA_SERVICIO)) {
				DlgMessage.information("No se puede anular un comprobante marcado como Perdida de Servicio.");
				return;
			}

			VentaPasaje ventaPasaje=null;
			if(rdRegular.isChecked() && ltbxAnulacionComprobantes.getSelectedItems().size()==1)
				ventaPasaje=ltbxAnulacionComprobantes.getSelectedItem().getValue();

			createWindowAnulacion(ventaPasaje);

		} catch (Exception e) {
			e.printStackTrace();
			DlgMessage.error(e.getMessage());
		}
	}

	/**
	 * crea la venta para confirmar la anulacion
	 * @throws Exception
	 */
	@SuppressWarnings("deprecation")
	private void createWindowAnulacion(final VentaPasaje ventaPasaje)throws Exception{
//		final boolean isAnulacionSinNC = isAnulacionSinNC(ventaPasaje);

		final Window win = new Window("", "normal", true);
		win.setWidth("500px");

		Caption caption = new Caption(":::CONFIRMAR ANULACION:::", "");
		win.appendChild(caption);

//		Groupbox groupbox= new Groupbox();
//		caption= new Caption("Datos de la Liquidaci�n a qui�n se asignaran los comprobantes que se tengan que generar");
//		groupbox.appendChild(caption);

		Grid grid= new Grid();
		grid.setStyle("border:none");
		Columns columns= new Columns();
		Column column= new Column();
		column.setWidth("120px");
		column.setAlign("right");
		columns.appendChild(column);
		columns.appendChild(new Column());
		grid.appendChild(columns);

		Rows rows= new Rows();
		Row row= new Row();
		row.setSpans("2");
		Div div= new Div();
		div.setAlign("left");
//		Label lblinfo= new Label("Datos de la Liquidacion a quien se asignaran los comprobantes que se tengan que generar");
		Label lblinfo= new Label("");
		lblinfo.setStyle("color:blue;font-size:12px !important;text-transform:none;");
		div.appendChild(lblinfo);
		row.appendChild(div);
		rows.appendChild(row);

//		row= new Row();
//		row.appendChild(new Label("AGENCIA : "));
//		cmbAgenciaLiq= new Combobox();
//		cmbAgenciaLiq.setReadonly(true);
//		cmbAgenciaLiq.setWidth("250px");
//		UtilData.cargarAgenciaXtipoAgencia(cmbAgenciaLiq, Constantes.ID_TIPAGE_TEPSA, false);
//		Util.seleccionarValorItemCombo(Agencia.class, cmbAgenciaLiq, getAgencia().getId());
////		cmbAgenciaLiq.setDisabled(getRol().getId().intValue()!=Constantes.ID_ROL_SUPER_USUARIO);
//		row.appendChild(cmbAgenciaLiq);
//		row.setVisible(isAnulacionSinNC?false:true);
//		rows.appendChild(row);

//		row= new Row();
//		row.appendChild(new Label("USUARIO : "));
//		cmbUsuarioLiq= new Combobox();
//		cmbUsuarioLiq.setReadonly(true);
//		cmbUsuarioLiq.setWidth("250px");
////		cmbUsuarioLiq.setDisabled(getRol().getId().intValue()!=Constantes.ID_ROL_SUPER_USUARIO);
//		row.appendChild(cmbUsuarioLiq);
//		row.setVisible(isAnulacionSinNC?false:true);
//		rows.appendChild(row);
		
		DateFormat DATE_FORMAT = new SimpleDateFormat("yyyyMMdd");
		Date fechaHastaNC = new Date();
		Date fechaDesdeNC = new Date(fechaHastaNC.getTime() - (Constantes.MILISEGUNDOS_X_DIA *2));
		String constraintFechaNC = "between "+DATE_FORMAT.format(fechaDesdeNC)+ " and "+DATE_FORMAT.format(fechaHastaNC)+" ";
		row= new Row();
		row.appendChild(new Label("FECHA N.C. : "));
		dtbxFechaNc = new Datebox();
		dtbxFechaNc.setValue(new Date());
		dtbxFechaNc.setReadonly(true);
		dtbxFechaNc.setWidth("150px");
		dtbxFechaNc.setFormat("dd/MM/yyyy");
		dtbxFechaNc.setConstraint(constraintFechaNC);
//		dtbxFechaNc.setDisabled(getRol().getId().intValue()!=Constantes.ID_ROL_SUPER_USUARIO);
		row.appendChild(dtbxFechaNc);
		row.setVisible(rdSoloNC.isChecked());
		rows.appendChild(row);

		row= new Row();
		row.appendChild(new Label("MOTIVO (*) : "));
		txtMotivoAnulacion= new Textbox();
		txtMotivoAnulacion.setMultiline(true);
		txtMotivoAnulacion.setRows(3);
		txtMotivoAnulacion.setMaxlength(150);
		txtMotivoAnulacion.setWidth("250px");
		row.appendChild(txtMotivoAnulacion);
		rows.appendChild(row);

		row= new Row();
		row.setSpans("2");
		div= new Div();
		div.setAlign("center");
		lblAdvertencia= new Label();
		lblAdvertencia.setVisible(false);
		lblAdvertencia.setStyle("color:red;font-size:11px !important;text-transform:none;font-weight: bold;");
		div.appendChild(lblAdvertencia);
		row.appendChild(div);
		rows.appendChild(row);

		grid.appendChild(rows);
		win.appendChild(grid);
		win.appendChild(new Separator("horizontal"));

		div= new Div();
		div.setAlign("center");
		btnProcesarAnulacion= new Button("Procesar Anulación","/resources/mp_anular.png");
		btnProcesarAnulacion.setClass("btn-vyrbus");
		btnProcesarAnulacion.setAutodisable("self");
		div.appendChild(btnProcesarAnulacion);
		win.appendChild(div);

		/*Realiza la busqueda de una liquidacion del usuario que esta realizando la anulacion*/
//		if(!isAnulacionSinNC){
//			btnProcesarAnulacion.setDisabled(true);
//			liquidacion = UtilData.estadoLiquidacionUsuario(getUsuario(), getAgencia());
//			String fechaLiquidacion = Constantes.FORMAT_DATE.format(new Date());
//			UtilData.cargarUsuariosLiquidacion(cmbUsuarioLiq, fechaLiquidacion,fechaLiquidacion, false,((Agencia)cmbAgenciaLiq.getSelectedItem().getValue()).getId());
//			validarLiquidacion(liquidacion);
//			if(liquidacion != null)
//				Util.seleccionarValorItemCombo(Usuario.class, cmbUsuarioLiq, liquidacion.getUsuario().getId());
//		}

//		cmbAgenciaLiq.addEventListener(Events.ON_CHANGE, new EventListener<Event>() {
//			@Override
//			public void onEvent(Event event) throws Exception {
//				Util.limpiarCombobox(cmbUsuarioLiq);
//				if(cmbAgenciaLiq.getSelectedItem().getValue() instanceof Agencia){
//					String fechaLiquidacion=Constantes.FORMAT_DATE.format(Constantes.FORMAT_DATE.parse(MyTime.dateTimeServer()));
//					UtilData.cargarUsuariosLiquidacion(cmbUsuarioLiq, fechaLiquidacion,fechaLiquidacion, false,((Agencia)cmbAgenciaLiq.getSelectedItem().getValue()).getId());
//				}else
//					UtilData.cargarGenericData(cmbUsuarioLiq, false);
//			}
//		});
//
//		cmbUsuarioLiq.addEventListener(Events.ON_CHANGE, new EventListener<Event>() {
//			@Override
//			public void onEvent(Event event) throws Exception {
//				liquidacion = UtilData.estadoLiquidacionUsuario((Usuario)cmbUsuarioLiq.getSelectedItem().getValue(), (Agencia)cmbAgenciaLiq.getSelectedItem().getValue());
//				validarLiquidacion(liquidacion);
//			}
//		});

		btnProcesarAnulacion.addEventListener(Events.ON_CLICK, new EventListener<Event>() {
			@Override
			public void onEvent(Event event) throws Exception {
				try {
//					if(!isAnulacionSinNC && !(cmbUsuarioLiq.getSelectedItem().getValue() instanceof Usuario)){
//						DlgMessage.information("Debe de seleccionar el usuario",cmbUsuarioLiq);
//						return;
//					}else 
					if(txtMotivoAnulacion.getText().trim().isEmpty()){
						DlgMessage.information("Debe de ingresar el Motivo de la anulación.",txtMotivoAnulacion);
						return;
					}else if (txtMotivoAnulacion.getText().trim().length()<5){
						DlgMessage.information("El Motivo que ha ingresado no es válido.",txtMotivoAnulacion);
						return;
					}
					Messagebox.show("¿Realmente desea continuar con la Anulación?", DlgMessage.NOMBREAPLICACION, DlgMessage.BTN_YESNO, Messagebox.QUESTION,DlgMessage.BTN_DEFAULT_NO, new EventListener<Event>() {
						@Override
						public void onEvent(Event e){
							try {
								if(e.getName().equals("onYes")){
									procesarAnulacion(ventaPasaje,win);
								}
							} catch (Exception e2) {
								e2.printStackTrace();
								DlgMessage.error(e2.getMessage());
							}
						}
					});
				} catch (Exception e) {
					e.printStackTrace();
					DlgMessage.error(e.getMessage());
				}
			}
		});

		wndAnulacionBoleto = win;
		this.appendChild(wndAnulacionBoleto);
		wndAnulacionBoleto.setMode(MODAL);
	}

	private void validarLiquidacion(Liquidacion liquidacion)throws Exception{
		lblAdvertencia.setVisible(false);
		btnProcesarAnulacion.setDisabled(true);

		if(btnProcesarAnulacion.isDisabled()){
			if(liquidacion!=null && liquidacion.getestadoLiquidacion().intValue()==Constantes.TRUE_VALUE){
				Date date=Constantes.FORMAT_DATE.parse(MyTime.dateTimeServer());
				if(date.getTime()!=liquidacion.getFechaLiquidacion().getTime()){
					lblAdvertencia.setValue("* La fecha de su Liquidaci�n no es v�lida, esta debe ser del d�a. *");
				}else
					btnProcesarAnulacion.setDisabled(false);
			}else{
				lblAdvertencia.setValue("El Usuario Seleccionado no tiene una Liquidación aperturada, esta es necesaria para continuar con el proceso de Anulación.");
				lblAdvertencia.setVisible(true);
			}
		}
	}

	private boolean isAnulacionSinNC(VentaPasaje ventaPasaje)throws Exception{
		boolean isAnualcion=false;
		/*Valida que la anulacion este dentro de las 72 horas, desde el dia siguiente a la emision*/
		if(ventaPasaje!=null && (rdAnulacionRegular.isChecked() || rdRegular.isChecked())){
			Integer horas_maximo=Constantes.HORAS_MAXIMO_ANULACION;
			Date dateStartLimit= new Date(ventaPasaje.getFechaLiquidacion().getTime()+Constantes.MILISEGUNDOS_X_DIA);
			long horasTrans= (new Date().getTime()-dateStartLimit.getTime())/Constantes.MILISEGUNDOS_X_HORA;
			if(horasTrans<=horas_maximo)
				isAnualcion=true;
		}

		return isAnualcion;
	}

	/**
	 * Procesa la anulacion
	 * @throws Exception
	 */
	private void procesarAnulacion(VentaPasaje ventaPasaje, Window window)throws Exception{
		List<VentaPasaje>lstVentas= new ArrayList<>();
		if(ventaPasaje != null){
			VentaPasaje anulacion= ServiceLocator.getVentaPasajesManager().buscarVentaById(ventaPasaje.getId());
			anulacion.setObservaciones(txtMotivoAnulacion.getText().trim().toUpperCase());
			anulacion.setFechaAnulacion(new Date());
			anulacion.setUsuarioAnulacion(getUsuario());
			lstVentas.add(anulacion);
		}else{
			for(Listitem item:ltbxAnulacionComprobantes.getSelectedItems()){
				VentaPasaje anulacion= ServiceLocator.getVentaPasajesManager().buscarVentaById(((VentaPasaje)item.getValue()).getId());
				anulacion.setObservaciones(txtMotivoAnulacion.getText().trim().toUpperCase());
				anulacion.setFechaAnulacion(new Date());
				anulacion.setUsuarioAnulacion(getUsuario());
				lstVentas.add(anulacion);
			}
		}
		/*Determina el tipo de anulacion*/
		int tipoAnulacion=0;
		boolean anularMovimiento=true;
		if(rdAnulacionRegular.isChecked())
			tipoAnulacion = Constantes.TIPO_ANULACION_REGULAR;
		else{
			if(rdRegular.isChecked())
				tipoAnulacion = Constantes.TIPO_ANULACION_REGULAR;
			else if (rdSoloNC.isChecked())
				tipoAnulacion = Constantes.TIPO_ANULACION_NC;
			else if (rdNCNuevoComprobante.isChecked())
				tipoAnulacion = Constantes.TIPO_ANULACION_NC_NEW_COMPROBANTE;

			if(!ckbxGeneraMovimientoSistema.isDisabled())
				anularMovimiento = ckbxGeneraMovimientoSistema.isChecked();
		}
		
		Date fechaNota = null;
		// Cuando es una anulacion con NC, valida la existencia de especies valoradas
		if(tipoAnulacion == Constantes.TIPO_ANULACION_NC) {
			int tipocomprobanteId = lstVentas.get(0).getTipoComprobante().getId();
			Integer aplicarA = 0;
			if(tipocomprobanteId == Constantes.ID_TIPCOM_BOLETA_VENTA)
				aplicarA = Constantes.APLICAR_NC_A_BOLETA;
			else if(tipocomprobanteId == Constantes.ID_TIPCOM_FACTURA)
				aplicarA = Constantes.APLICAR_NC_A_FACTURA;
			
			UtilData.buscarEspecieValoradaByCaja(Constantes.ID_TIPCOM_NOTA_CREDITO, getAgencia(), false, getUsuarioHardware(), aplicarA);
			fechaNota = dtbxFechaNc.getValue();
		}

		VentasNotas ventasNotas= ServiceLocator.getVentaPasajesManager().procesarAnulacionBy(lstVentas, tipoAnulacion, anularMovimiento, this.liquidacion, false, fechaNota);
		/*Realiza el envio de las nuevas ventas generadas al Servidor F.E.*/
		List<VentaPasaje> lstVentasenviar= new ArrayList<>();
		for(VentaPasaje venta:ventasNotas.getListVentas()){
			
			//Actualiza el correlativo - jabanto - 22/01/2024
//			ServiceLocator.getVentaPasajesManager().actualizarCorrelativoComprobante(venta, true);
			ServiceLocator.getVentaPasajesManager().updateCorrelative(venta, true);
			
			VentaPasaje envioVenta=ServiceLocator.getVentaPasajesManager().buscarVentaById(venta.getId());
			lstVentasenviar.add(envioVenta);
		}
		
		//Actualiza el correlativo de las NC - jabanto - 22/01/2024
		for(VentaPasaje notaCredito:ventasNotas.getListNotasCredito()){
//			ServiceLocator.getVentaPasajesManager().actualizarCorrelativoComprobante(notaCredito, true);
			ServiceLocator.getVentaPasajesManager().updateCorrelative(notaCredito, true);
		}
		
		if(lstVentasenviar.size()>0)
			WSFE.sendVenta(lstVentasenviar, window, false, null, null);		
		
		/*Realiza el envio de las notas de credito generadas al Servidor F.E.*/
		for(VentaPasaje notaCredito:ventasNotas.getListNotasCredito()){
			WSFE.sendNota(notaCredito);
		}

		if(!(txtNumeroComprobante.getText().trim().isEmpty())){
			txtNumeroComprobante.setText(Util.autocompleNumberBoleto(txtNumeroComprobante.getText()));
			buscarComprobanteByNumero();
		}else
			buscarComprobantesByFechas();

		DlgMessage.information("El Proceso de anulaci�n termino correctamente");

		window.onClose();
	}
}
