/**
 * Proyecto		: SISVYR
 * Sistema		: Sistema de Ventas y Reservas
 * Descripci�n	:
 * Autor		: Jos� Abanto
 * Fecha		: 18 jul. 2021
 * Hora			: 21:19:46
 */
package com.cystesoft.vyrbus.view.ctrl;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TreeMap;

import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zk.ui.event.InputEvent;
import org.zkoss.zul.Button;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Comboitem;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.Doublebox;
import org.zkoss.zul.Intbox;
import org.zkoss.zul.Label;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Row;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

import com.cystesoft.vyrbus.model.bean.CanalVenta;
import com.cystesoft.vyrbus.model.bean.Cliente;
import com.cystesoft.vyrbus.model.bean.ControlEspecieValorada;
import com.cystesoft.vyrbus.model.bean.DetalleEquipaje;
import com.cystesoft.vyrbus.model.bean.Equipaje;
import com.cystesoft.vyrbus.model.bean.EspecieValorada;
import com.cystesoft.vyrbus.model.bean.FormaPago;
import com.cystesoft.vyrbus.model.bean.OperadorTarjetaCredito;
import com.cystesoft.vyrbus.model.bean.TarjetaCredito;
import com.cystesoft.vyrbus.model.bean.TipoComprobante;
import com.cystesoft.vyrbus.model.bean.TipoFormaPago;
import com.cystesoft.vyrbus.model.bean.TipoMovimiento;
import com.cystesoft.vyrbus.model.bean.Ubigeo;
import com.cystesoft.vyrbus.model.bean.VentaPasaje;
import com.cystesoft.vyrbus.service.locator.ServiceLocator;
import com.cystesoft.vyrbus.service.util.Constantes;
import com.cystesoft.vyrbus.service.util.Messages;
import com.cystesoft.vyrbus.service.util.Util;
import com.cystesoft.vyrbus.service.util.UtilData;
import com.cystesoft.vyrbus.service.util.WSFE;
import com.cystesoft.vyrbus.view.ui.DlgMessage;
import com.cystesoft.vyrbus.view.ui.WndBase;

/**
 * @author abant
 *
 */
public class WndEquipaje extends WndBase implements Serializable{

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
//	private Textbox txtitinerario;
//	private Button btnBuscarItinerario;
	private Window wndEquipaje;
	private Textbox txtNumeroBoleto;
	private Listbox ltbxBoletos;
	private Datebox dtbxFechaPartida;
	private Button btnBuscar;
	private Label lblKilosLibres;
	private Label lblFechaPartida;
	private Label lblServicio;
	private Label lblRuta;
	private Label lblHoraSalida;
	private Intbox itbxNumeroMaletas;
	private Intbox itbxTotalKilos;
	private Doublebox dbxTotalPago;
	private Row rowExceso;
	private Row rowExcesoTarjeta;
	private Combobox cmbTipoComprobante;
	private Textbox txtNumeroComprobante;
	private Combobox cmbTipoPago;
	private Combobox cmbOperadorTarjeta;
	private Combobox cmbTarjetaCredito;
	private Textbox txtGlosa;
	private Button btnGuardar;
	private Button btnCancelar;
	private Row rowRuc;
	private Textbox txtNumeroRUC;
	private Button btnBuscarCliente;
	private Textbox txtRazonSocial;
	private Textbox txtDireccionFiscal;
	private Textbox txtobservaciones;

	private TreeMap<String, Object> criteriosBusqueda;
	private int KG_X_BOLETO_LIBRES = 20;
	private List<DetalleEquipaje> listDetalleEquipaje=null;
	private Date fechaLiquidacion=null;
	private Equipaje equipaje=null;
	private CanalVenta canalVenta = null;
	private Cliente cliente=null;
//	private Itinerario itinerario;

	/* (non-Javadoc)
	 * @see com.cystesoft.vyrbus.view.ui.WndBase#initComponents()
	 */
	@Override
	public void initComponents() {
		// TODO Auto-generated method stub
		super.initComponents();

//		txtitinerario = (Textbox)this.getFellow("txtitinerario");
//		btnBuscarItinerario = (Button)this.getFellow("btnBuscarItinerario");
		wndEquipaje = (Window)this.getFellow("wndEquipaje");
		txtNumeroBoleto= (Textbox)this.getFellow("txtNumeroBoleto");
		ltbxBoletos= (Listbox)this.getFellow("ltbxBoletos");
		dtbxFechaPartida = (Datebox)this.getFellow("dtbxFechaPartida");
		btnBuscar= (Button)this.getFellow("btnBuscar");
		lblKilosLibres= (Label)this.getFellow("lblKilosLibres");
		lblFechaPartida= (Label)this.getFellow("lblFechaPartida");
		lblServicio= (Label)this.getFellow("lblServicio");
		lblRuta= (Label)this.getFellow("lblRuta");
		lblHoraSalida= (Label)this.getFellow("lblHoraSalida");
		itbxNumeroMaletas= (Intbox)this.getFellow("itbxNumeroMaletas");
		itbxTotalKilos= (Intbox)this.getFellow("itbxTotalKilos");
		dbxTotalPago= (Doublebox)this.getFellow("dbxTotalPago");
		rowExceso = (Row)this.getFellow("rowExceso");
		rowExcesoTarjeta = (Row)this.getFellow("rowExcesoTarjeta");
		cmbTipoComprobante= (Combobox)this.getFellow("cmbTipoComprobante");
		txtNumeroComprobante= (Textbox)this.getFellow("txtNumeroComprobante");
		cmbTipoPago= (Combobox)this.getFellow("cmbTipoPago");
		cmbOperadorTarjeta= (Combobox)this.getFellow("cmbOperadorTarjeta");
		cmbTarjetaCredito= (Combobox)this.getFellow("cmbTarjetaCredito");
		txtGlosa= (Textbox)this.getFellow("txtGlosa");
		btnGuardar = (Button)this.getFellow("btnGuardar");
		btnCancelar = (Button)this.getFellow("btnCancelar");
		rowRuc = (Row)this.getFellow("rowRuc");
		txtNumeroRUC = (Textbox)this.getFellow("txtNumeroRUC");
		btnBuscarCliente = (Button)this.getFellow("btnBuscarCliente");
		txtRazonSocial = (Textbox)this.getFellow("txtRazonSocial");
		txtDireccionFiscal = (Textbox)this.getFellow("txtDireccionFiscal");
		txtobservaciones = (Textbox)this.getFellow("txtobservaciones");

		txtNumeroBoleto.addEventListener(Events.ON_OK, new EventListener<Event>() {
			@Override
			public void onEvent(Event event) throws Exception {
				searchBoleto();
				itbxNumeroMaletas.setFocus(true);
			}
		});
		btnBuscar.addEventListener(Events.ON_CLICK, new EventListener<Event>() {
			@Override
			public void onEvent(Event event) throws Exception {
				searchBoleto();
			}
		});
		itbxTotalKilos.addEventListener(Events.ON_CHANGING, new EventListener<Event>() {
			@Override
			public void onEvent(Event event) throws Exception {
				onChanging_itbxTotalKilos(event);
			}
		});
		cmbTipoComprobante.addEventListener(Events.ON_SELECT, new EventListener<Event>() {
			@Override
			public void onEvent(Event event) throws Exception {
				onSelect_cmbTipoComprobante();
			}
		});
		cmbTipoPago.addEventListener(Events.ON_SELECT, new EventListener<Event>() {
			@Override
			public void onEvent(Event event) throws Exception {
				onSelect_cmbTipoPago();
			}
		});
		btnGuardar.addEventListener(Events.ON_CLICK, new EventListener<Event>() {
			@Override
			public void onEvent(Event event) throws Exception {
				onClick_btnGuardar();
			}
		});
		btnCancelar.addEventListener(Events.ON_CLICK, new EventListener<Event>() {
			@Override
			public void onEvent(Event event) throws Exception {
				onClick_btnCancelar();
			}
		});
		txtNumeroRUC.addEventListener(Events.ON_OK, new EventListener<Event>() {
			@Override
			public void onEvent(Event event) throws Exception {
				// TODO Auto-generated method stub
				searchCliente();
			}
		});
		btnBuscarCliente.addEventListener(Events.ON_CLICK, new EventListener<Event>() {
			@Override
			public void onEvent(Event event) throws Exception {
				searchCliente();
			}
		});

		cmbOperadorTarjeta.addEventListener(Events.ON_CHANGE, new EventListener<Event>() {
			@Override
			public void onEvent(Event event) {
				onLoadTarjetas();
			}
		});
	}


	/* (non-Javadoc)
	 * @see com.cystesoft.vyrbus.view.ui.WndBase#onCreate()
	 */
	@Override
	public void onCreate() throws Exception {
		try {
			// TODO Auto-generated method stub
			super.onCreate();

//			enlazarItinerario(btnBuscarItinerario);

			/*	*********************************************************************************************************	*/
			/*	Obteniendo las variables de la Sesion	*/
			fechaLiquidacion = (Date)this.getDesktop().getSession().getAttribute(Constantes.ATRIBUTO_FECHA_LIQUIDACION);
			canalVenta = (CanalVenta)this.getDesktop().getSession().getAttribute(Constantes.ATRIBUTO_CANAL_VENTA);

			dtbxFechaPartida.setDisabled(true);
			dtbxFechaPartida.setValue(new Date());

			//Carga los tipos de comprobante
			List<TipoComprobante> resultTipoComprobante = ServiceLocator.getTipoComprobanteManager().buscarPorEstadoRegistro(Constantes.VALUE_ACTIVO, "denominacion");
			for(TipoComprobante tipoComprobante: resultTipoComprobante) {
				if(tipoComprobante.getId().intValue()==Constantes.ID_TIPCOM_BOLETA_VENTA || tipoComprobante.getId().intValue()==Constantes.ID_TIPCOM_FACTURA ||
						tipoComprobante.getId().intValue()==Constantes.ID_TIPCOM_GUIA_TRANSPORTISTA) {
					Comboitem comboitem= new Comboitem(tipoComprobante.getDenominacion());
					comboitem.setValue(tipoComprobante);
					cmbTipoComprobante.appendChild(comboitem);
				}
			}
			UtilData.cargarDataCombo(cmbOperadorTarjeta, OperadorTarjetaCredito.class, null);
			dbxTotalPago.setLocale(Locale.US);
			cmbOperadorTarjeta.setDisabled(true);
			cmbTarjetaCredito.setDisabled(true);

			disabledControls(true);
			btnGuardar.setDisabled(true);

		} catch (Exception e) {
			e.printStackTrace();
			DlgMessage.error(e.getMessage());
		}
	}

	/**
	 *
	 * @throws Exception
	 */
	private void searchBoleto()throws Exception{
//		if(itinerario==null) {
//			DlgMessage.information(Messages.getString("WndRecepcionEquipajes.itinerario.null"),btnBuscarItinerario);
//			return;
//		}
		if(!(txtNumeroBoleto.getText().trim().isEmpty())) {
			txtNumeroBoleto.setText(Util.autocompleNumberBoleto(txtNumeroBoleto.getText().trim().toUpperCase()));
			String numeroBoleto = txtNumeroBoleto.getText().trim().toUpperCase();

			//Valida que el boleto no exista en la lista (Listbox)
			if(existsListBoleto(numeroBoleto)) {
				DlgMessage.information(Messages.getString("WndRecepcionEquipajes.boleto.duplicate"),txtNumeroBoleto);
				return;
			}

			String fechaPartida = Constantes.FORMAT_DATE.format(dtbxFechaPartida.getValue());
			List<VentaPasaje> resultVenta= ServiceLocator.getVentaPasajesManager().buscarVentasPostergar(null, null, null, null, numeroBoleto, fechaPartida);
			if(resultVenta.size()>0) {
				VentaPasaje ventaPasaje = resultVenta.get(0);

//				/*Valida que la agencia en donde se esta ingresando el equipaje sea un punto de embarque/partida del itinerario */
//				criteriosBusqueda = new TreeMap<>();
//				criteriosBusqueda.put("itinerario", ventaPasaje.getItinerario());
//				criteriosBusqueda.put("agencia", getAgencia());
//				criteriosBusqueda.put("estadoRegistro", Constantes.VALUE_ACTIVO);
//				List<ItinerarioAgenciaPartida> resultItinerarioAgePart= ServiceLocator.getItinerarioAgenciaPartidaManager().buscarPorX(criteriosBusqueda, null);
//				if(resultItinerarioAgePart.size()==0) {
//					//si no, valida con el detalle del itinerario
//					criteriosBusqueda= new TreeMap<>();
//					criteriosBusqueda.put("itinerario", ventaPasaje.getItinerario());
//					criteriosBusqueda.put("agenciaPartida", getAgencia());
//					criteriosBusqueda.put("estadoRegistro", Constantes.VALUE_ACTIVO);
//					List<DetalleItinerario> resultDetItinerario = ServiceLocator.getDetalleItinerarioManager().buscarPorX(criteriosBusqueda, null);
//					if(resultDetItinerario.size()==0) {
//						DlgMessage.information(Messages.getString("WndRecepcionEquipajes.itinerario.agenciaNoPuntoPartida"),txtNumeroBoleto);
//						return;
//					}
//				}

				//Valida que el segundo Boleto (de ser el caso) corresponda al mismo itinerario
				for(Listitem item: ltbxBoletos.getItems()) {
					VentaPasaje _ventaPasaje=item.getValue();
					if(_ventaPasaje.getItinerario().getId().longValue()!=ventaPasaje.getItinerario().getId().longValue()) {
						DlgMessage.information(Messages.getString("WndRecepcionEquipajes.itinerarioDiferente"),txtNumeroBoleto);
						return;
					}
				}

				//Valida que el boleto no haya sido registrado con algun otro equipaje
				criteriosBusqueda = new TreeMap<>();
				criteriosBusqueda.put("ventaPasaje", ventaPasaje);
				criteriosBusqueda.put("estadoRegistro", Constantes.VALUE_ACTIVO);
				List<DetalleEquipaje> resultDetEquipaje = ServiceLocator.getDetalleEquipajeManager().buscarPorX(criteriosBusqueda, null);
				if(resultDetEquipaje.size()>0) {
					DlgMessage.information(Messages.getString("WndRecepcionEquipajes.boleto.duplicate"),txtNumeroBoleto);
					return;
				}

				addItemBoleto(ventaPasaje);
				lblKilosLibres.setValue(String.valueOf(KG_X_BOLETO_LIBRES * ltbxBoletos.getItemCount()));
				if(ltbxBoletos.getItemCount()==1) {
					lblFechaPartida.setValue(Constantes.FORMAT_DATE.format(dtbxFechaPartida.getValue()));
					lblServicio.setValue(ventaPasaje.getServicio().getDenominacion());
					lblRuta.setValue(ventaPasaje.getRuta().toString());
					lblHoraSalida.setValue(ventaPasaje.getHoraPartida());
				}

				//Recalcula el exceso, por si aya valores en el campo de Kg
				calcularExceso(null);
				disabledControls(false);
				btnGuardar.setDisabled(false);

				txtNumeroBoleto.setText("");
				txtNumeroBoleto.setFocus(true);
			}else {
				DlgMessage.information(Messages.getString("WndRecepcionEquipajes.boleto.noExists"),txtNumeroBoleto);
			}
		}else {
			DlgMessage.information(Messages.getString("WndRecepcionEquipajes.boleto.null"),txtNumeroBoleto);
		}
	}

	/**
	 *
	 * @throws Exception
	 */
	private void searchCliente()throws Exception{
		cliente = null;
		txtRazonSocial.setText("");
		txtDireccionFiscal.setText("");
		if(txtNumeroRUC.getText().trim().isEmpty()) {
			DlgMessage.information(Messages.getString("WndRecepcionEquipajes.cliente.ruc.null"), txtNumeroRUC);
			return;
		}

		criteriosBusqueda = new TreeMap<>();
		criteriosBusqueda.put("numeroDocumento", txtNumeroRUC.getText().trim());
		List<Cliente> resultCliente = ServiceLocator.getClienteManager().buscarPorX(criteriosBusqueda, null);
		if(resultCliente.size()>0) {
			cliente = resultCliente.get(0);
			txtRazonSocial.setText(cliente.getRazonSocial());
			txtDireccionFiscal.setText(cliente.getDireccion());
		}else {
			txtRazonSocial.setReadonly(false);
			txtDireccionFiscal.setReadonly(false);
			DlgMessage.information(Messages.getString("WndRecepcionEquipajes.cliente.ruc.noExists"), txtRazonSocial);
		}
	}

	/**
	 *
	 * @throws Exception
	 */
	private void onChanging_itbxTotalKilos(Event event)throws Exception{
		if(((InputEvent)event).getValue()!=null && ((InputEvent)event).getValue().toString().trim().length()>0) {
			calcularExceso(Integer.valueOf(((InputEvent)event).getValue()));
		}
	}

	/**
	 *
	 * @throws Exception
	 */
	private void onSelect_cmbTipoComprobante()throws Exception{
		rowRuc.setVisible(false);
		txtNumeroBoleto.setText("");
		txtNumeroComprobante.setText("");
		txtRazonSocial.setReadonly(true);
		txtDireccionFiscal.setReadonly(true);
		txtNumeroRUC.setText("");
		txtRazonSocial.setText("");
		txtDireccionFiscal.setText("");
		if(cmbTipoComprobante.getSelectedIndex()>=0) {
			txtNumeroComprobante.setText(onLoadEspecieValorada((TipoComprobante)cmbTipoComprobante.getSelectedItem().getValue()));
			TipoComprobante tipoComprobante = cmbTipoComprobante.getSelectedItem().getValue();
			loadTipoFormaPago(tipoComprobante);
			if(tipoComprobante.getId().intValue()==Constantes.ID_TIPCOM_FACTURA)
				rowRuc.setVisible(true);
		}
	}

	/**
	 *
	 * @throws Exception
	 */
	private void onSelect_cmbTipoPago()throws Exception{
		cmbOperadorTarjeta.setDisabled(true);
		cmbOperadorTarjeta.setSelectedIndex(-1);
		cmbTarjetaCredito.setDisabled(true);
		//cmbTarjetaCredito.setSelectedIndex(0);
		if(cmbTipoPago.getSelectedIndex()>=0) {
			if(((TipoFormaPago)cmbTipoPago.getSelectedItem().getValue()).getId().intValue()==Constantes.ID_TIPFORPAG_TARJETA) {
				cmbOperadorTarjeta.setDisabled(false);
				cmbTarjetaCredito.setDisabled(false);
			}
		}
	}

	/**
	 *
	 * @throws Exception
	 */
	private void onClick_btnGuardar()throws Exception{
		/*Valida si el usuario tiene una liquidaci�n aperturada*/
		if(getDesktop().getSession().getAttribute(Constantes.ATRIBUTO_FECHA_LIQUIDACION)==null) {
			DlgMessage.information(Messages.getString("WndVentaReserva.information.noLiquidacion"));
			return;
		}
		//Valida que, la fecha de la liquidaci�n sea igual a la actual - 22/07/2021 - jabanto
		String fechaActual=Constantes.FORMAT_DATE.format(new Date());
		String s_fechaLiquidacion=(fechaLiquidacion!=null?Constantes.FORMAT_DATE.format(fechaLiquidacion):"");
		if(!(fechaActual.equals(s_fechaLiquidacion))) {
			DlgMessage.information(Messages.getString("WndVentaReserva.information.fechaLiquidacionDiferente"));
			return;
		}
		if(ltbxBoletos.getItems().size()==0) {
			DlgMessage.information(Messages.getString("WndRecepcionEquipajes.listboletos.null"), txtNumeroBoleto);
			return;
		}else if(itbxNumeroMaletas.getValue()==null || itbxNumeroMaletas.getValue()<=0 || itbxNumeroMaletas.getText().trim().isEmpty()) {
			DlgMessage.information(Messages.getString("WndRecepcionEquipajes.numeroMaletas.null"), itbxNumeroMaletas);
			return;
		}else if(itbxTotalKilos.getValue()==null || itbxTotalKilos.getValue()<=0 || itbxTotalKilos.getText().trim().isEmpty()) {
			DlgMessage.information(Messages.getString("WndRecepcionEquipajes.kilos.null"), itbxTotalKilos);
			return;
		}else if(!dbxTotalPago.isDisabled() && (dbxTotalPago.getValue()==null || dbxTotalPago.getValue()<Constantes.MONTO_MINIMO_EXCESO || dbxTotalPago.getText().trim().isEmpty())) {
			DlgMessage.information(Messages.getString("WndRecepcionEquipajes.totalPago.null"), dbxTotalPago);
			return;
		}else if(rowExceso.isVisible()) {
			if(cmbTipoComprobante.getSelectedIndex()<0) {
				DlgMessage.information(Messages.getString("WndRecepcionEquipajes.tipoComprobante.null"), cmbTipoComprobante);
				return;
			}else if(txtNumeroComprobante.getText().trim().isEmpty()) {
				DlgMessage.information(Messages.getString("WndRecepcionEquipajes.numeroComprobante.null"), txtNumeroComprobante);
				return;
			}else if(cmbTipoPago.getSelectedIndex()<0) {
				DlgMessage.information(Messages.getString("WndRecepcionEquipajes.tipoPago.null"), cmbTipoPago);
				return;
			}else if(!(cmbOperadorTarjeta.isDisabled()) && cmbOperadorTarjeta.getSelectedIndex()<0) {
				DlgMessage.information(Messages.getString("WndRecepcionEquipajes.operadorTarjeta.null"), cmbOperadorTarjeta);
				return;
			}else if(!(cmbTarjetaCredito.isDisabled()) && cmbTarjetaCredito.getSelectedIndex()<1) {
				DlgMessage.information(Messages.getString("WndRecepcionEquipajes.tarjetaCredito.null"), cmbOperadorTarjeta);
				return;
			}
		}
		if(rowRuc.isVisible()) {
			if(cliente==null && txtNumeroRUC.getText().trim().isEmpty()) {
				DlgMessage.information(Messages.getString("WndRecepcionEquipajes.cliente.ruc.null"), txtNumeroRUC);
				return;
			}else if(cliente==null && txtRazonSocial.getText().trim().isEmpty()) {
				DlgMessage.information(Messages.getString("WndRecepcionEquipajes.cliente.razonSocial.null"), txtRazonSocial);
				return;
			}else if(cliente==null && txtDireccionFiscal.getText().trim().isEmpty()) {
				DlgMessage.information(Messages.getString("WndRecepcionEquipajes.cliente.direccionFiscal.null"), txtDireccionFiscal);
				return;
			}else if(!(Util.validarRUC(txtNumeroRUC.getText().trim()))) {
				DlgMessage.information(Messages.getString("WndRecepcionEquipajes.cliente.ruc.invalid"), txtNumeroRUC);
				return;
			}

			if(cliente==null) {
				cliente = new Cliente();
				cliente.setNumeroDocumento(txtNumeroRUC.getText().trim());
				cliente.setRazonSocial(txtRazonSocial.getText().trim().toUpperCase());
				cliente.setDireccion(txtDireccionFiscal.getText().trim().toUpperCase());
				cliente.setAgencia(getAgencia());
				cliente.setUbigeo(new Ubigeo(Constantes.ID_UBIGEO_LIMA));
				cliente.setCantidadTrabajadores(0);
				cliente.setKilometros(.00);
				cliente.setEstadoRegistro(Constantes.VALUE_ACTIVO);
				UtilData.auditarRegistro(cliente, getUsuario(), Executions.getCurrent());
			}
		}

		/*Cabecera*/
		equipaje= new Equipaje();
		equipaje.setItinerario(((VentaPasaje)ltbxBoletos.getItemAtIndex(0).getValue()).getItinerario());
		equipaje.setAgencia(getAgencia());
		equipaje.setPeso(itbxTotalKilos.getValue().doubleValue());
		equipaje.setUsuarioHardware(getUsuarioHardware());
		equipaje.setEstadoRegistro(Constantes.VALUE_ACTIVO);
		if(!(txtobservaciones.getText().trim().isEmpty()))
			equipaje.setObservaciones(txtobservaciones.getText().trim().toUpperCase());
		UtilData.auditarRegistro(equipaje, getUsuario(), Executions.getCurrent());

		//Obtiene el numero de Ticket inicial
		String nroTicketInicio = onLoadEspecieValorada(new TipoComprobante(Constantes.ID_TIPCOM_TICKET_EQUIPAJE));
		if(nroTicketInicio==null || nroTicketInicio.trim().isEmpty()) {
			DlgMessage.information(Messages.getString("WndRecepcionEquipajes.correlativoTicketEquipaje.null"), cmbOperadorTarjeta);
			return;
		}
		String serie = nroTicketInicio.split("-")[0];
		long numeroTicket = Long.valueOf(nroTicketInicio.split("-")[1]) -1;

		//Calcula el exceso
		Integer totalKilosLibres = Integer.valueOf(lblKilosLibres.getValue());
		Double pesoExceso = equipaje.getPeso() - totalKilosLibres.doubleValue();
		Integer kilosLibres = (int) (pesoExceso>0? equipaje.getPeso()-pesoExceso: equipaje.getPeso());

		//Calcula el peso promedio para el detalle
		Double pesoLibrePromedio = 0.00;
		if(pesoExceso>0) {
			if(itbxNumeroMaletas.getValue()>1)
				pesoLibrePromedio = (kilosLibres.doubleValue() / (itbxNumeroMaletas.getValue().doubleValue()-1));
			else
				pesoLibrePromedio = kilosLibres.doubleValue();
		}else
			pesoLibrePromedio = (kilosLibres.doubleValue() / itbxNumeroMaletas.getValue().doubleValue());
		//Redondea a 2 decimales
		pesoLibrePromedio = new BigDecimal(pesoLibrePromedio).setScale(2, RoundingMode.HALF_UP).doubleValue();

		/*crea el Detalle*/
		int index = -1;
		listDetalleEquipaje= new ArrayList<>();
		for(int x=0; x<itbxNumeroMaletas.getValue();x++) {
			index++;
			if(index>=ltbxBoletos.getItemCount())
				index=0;
			int principal = (x==0?Constantes.TRUE_VALUE:Constantes.FALSE_VALUE);
			numeroTicket ++;

			//
			Listitem item = ltbxBoletos.getItemAtIndex(index);
			VentaPasaje ventaPasaje= item.getValue();
			String ticket = Util.autocompleNumberBoleto(serie +"-"+Long.toString(numeroTicket));
			Integer correlativo = (x+1);
			Double peso = (principal==Constantes.TRUE_VALUE && pesoExceso>0? pesoExceso: pesoLibrePromedio);
			DetalleEquipaje detalleEquipaje = getDetellaEquipaje(ventaPasaje, ticket, correlativo, peso, principal);
			listDetalleEquipaje.add(detalleEquipaje);

			//Por si el numero de maletas sea menor a la cantidad de boletos ingresados, duplica el ultimo registro
			if((x+1)==itbxNumeroMaletas.getValue() && listDetalleEquipaje.size()<ltbxBoletos.getItemCount()) {
				int dif = (ltbxBoletos.getItemCount() - listDetalleEquipaje.size());
				for(int x1=0; x1<dif; x1++) {
					for(Listitem _item: ltbxBoletos.getItems()) {
						VentaPasaje _ventaPasaje = _item.getValue();
						boolean existe = false;
						for(DetalleEquipaje _detalleEquipaje: listDetalleEquipaje) {
							if(_detalleEquipaje.getVentaPasaje().getId().longValue()==_ventaPasaje.getId().longValue()) {
								existe=true;
								break;
							}
						}
						if(!existe) {
							detalleEquipaje = getDetellaEquipaje(_ventaPasaje, ticket, correlativo, .00, Constantes.FALSE_VALUE);
							listDetalleEquipaje.add(detalleEquipaje);
						}
					}

				}
			}
		}

		//Si hay un exceso
		if(pesoExceso>0) {
			String strTmpObservaciones="";
			VentaPasaje ventaPrincipal =listDetalleEquipaje.get(0).getVentaPasaje();
			TipoFormaPago tipoFormaPago = (TipoFormaPago)cmbTipoPago.getSelectedItem().getValue();
			VentaPasaje ventaExceso= new VentaPasaje();
			ventaExceso.setVentaOriginal(ventaPrincipal.getVentaOriginal()!=null?ventaPrincipal.getVentaOriginal():null);
			ventaExceso.setVentaPasaje(ventaPrincipal);
			ventaExceso.setItinerario(ventaPrincipal.getItinerario());
			ventaExceso.setRuta(ventaPrincipal.getRuta());
			ventaExceso.setCliente(cliente);
			ventaExceso.setPasajero(ventaPrincipal.getPasajero());
			ventaExceso.setFormaPago(new FormaPago(Constantes.ID_FORPAG_CONTADO));
			ventaExceso.setServicio(ventaPrincipal.getServicio());
			ventaExceso.setTipoComprobante((TipoComprobante)cmbTipoComprobante.getSelectedItem().getValue());
			ventaExceso.setTipoMovimiento(new TipoMovimiento(Constantes.ID_TIPMOV_GRT));
			ventaExceso.setTipoFormaPago(tipoFormaPago);
			ventaExceso.setNumeroBoleto(txtNumeroComprobante.getText().trim().toUpperCase());
			ventaExceso.setNumeroBoletoAnterior(ventaPrincipal.getNumeroBoleto());
			ventaExceso.setTarifa(dbxTotalPago.getValue());
			ventaExceso.setImportePagado(dbxTotalPago.getValue());
			ventaExceso.setTipoTransaccion(Constantes.TIPO_OPERACION_EXCESO);
			ventaExceso.setFechaCaducidad(new Date());
			ventaExceso.setFechaLiquidacion(fechaLiquidacion);
			ventaExceso.setFechaPartida(ventaPrincipal.getFechaPartida());
			ventaExceso.setHoraEmbarque(ventaPrincipal.getHoraPartida());
			ventaExceso.setHoraPartida(ventaPrincipal.getHoraPartida());
			ventaExceso.setAgencia(getAgencia());
			ventaExceso.setUsuario(getUsuario());
			ventaExceso.setCanalVenta(canalVenta);
			//Se agrego esta variable para concatenar la Glosa y las Observaciones MAOE - 27/08/2022
			strTmpObservaciones = txtGlosa.getText().trim().toUpperCase()+"\n[MALETAS:"+itbxNumeroMaletas.getText()+" PESO:"+itbxTotalKilos.getText()+"Kg]\n"+txtobservaciones.getText().trim().toUpperCase();
			ventaExceso.setObservaciones(strTmpObservaciones);
			ventaExceso.setEstadoRegistro(Constantes.VALUE_ACTIVO);
			ventaExceso.setUsuarioHardware(getUsuarioHardware());
			ventaExceso.setSecuencial(0);
			ventaExceso.setRecargo(.00);
			ventaExceso.setDescuento(.00);
			ventaExceso.setPenalidad(.00);
			ventaExceso.setAcuenta(.00);
			ventaExceso.setIdaRetorno(0);
			ventaExceso.setEsFechaAbierta(0);
			ventaExceso.setImportePagadoEfectivo(.00);
			ventaExceso.setImportePagadoTarjeta(.00);
			ventaExceso.setNumeroControl("T00000000");
			ventaExceso.setAgenciaPartida(getAgencia());
			ventaExceso.setAgenciaLlegada(ventaPrincipal.getAgenciaLlegada());
			ventaExceso.setTarjetaCredito(cmbOperadorTarjeta.getSelectedIndex()<0?null:((TarjetaCredito)cmbTarjetaCredito.getSelectedItem().getValue()));
			Double igv=ventaExceso.getImportePagado()- Double.valueOf(Util.toNumberFormat(ventaExceso.getImportePagado()/((Constantes.IGV/100)+1),2));
			ventaExceso.setIgv(igv);
			UtilData.auditarRegistro(ventaExceso, getUsuario(), Executions.getCurrent());

			listDetalleEquipaje.get(0).setVentaPasajeExceso(ventaExceso);
		}

		Messagebox.show(Messages.getString("Generales.query.guardar"),DlgMessage.NOMBREAPLICACION, DlgMessage.BTN_YESNO, Messagebox.QUESTION, DlgMessage.BTN_DEFAULT_NO, new EventListener<Event>() {
			@Override
			public void onEvent(Event e){
				try {
					if(e.getName().equals("onYes")){

						ServiceLocator.getDetalleEquipajeManager().guardar(listDetalleEquipaje, equipaje, true);
						
						
						
//						boolean timerdownloadFileEquipaje = false;
						
						if(listDetalleEquipaje.get(0).getVentaPasajeExceso()!=null) {
							VentaPasaje ventaExceso= ServiceLocator.getVentaPasajesManager().buscarVentaById(listDetalleEquipaje.get(0).getVentaPasajeExceso().getId());
//							ventaExceso.setObservaciones(ventaExceso.getObservaciones()+"\n[MALETAS:"+itbxNumeroMaletas.getText()+" PESO:"+itbxTotalKilos.getText()+"Kg]");
							/**Solo Boletas y facturas*/
							if(ventaExceso.getTipoComprobante().getId().intValue()==Constantes.ID_TIPCOM_BOLETA_VENTA ||
									ventaExceso.getTipoComprobante().getId().intValue()==Constantes.ID_TIPCOM_FACTURA){
								/*Realiza el envio del boleto y realiza la impresion*/
//								boolean printComprobante=(chkVentaRemota.isChecked()?false:true);

								List<VentaPasaje> listVentaPasajes= new ArrayList<>();
								listVentaPasajes.add(ventaExceso);

								//Aqui se envia el comprobante al servidor de Facturaci�n Electr�nica
								//Comentado temporalmente por jabanto
								WSFE.sendVenta(listVentaPasajes, wndEquipaje, true, null, Constantes.NUMERO_COPIAS_COMPROBANTE_EXCESO);
								
//								timerdownloadFileEquipaje = true;
							}
						}

						//Envia impresion del Ticket de Equipaje
//						WSFE.printEquipaje(listDetalleEquipaje, wndEquipaje, timerdownloadFileEquipaje);
						
						Messagebox.show(Messages.getString("Generales.information.exitoGuardar"),DlgMessage.NOMBREAPLICACION, DlgMessage.BTN_OK, Messagebox.INFORMATION ,DlgMessage.BTN_OK, new EventListener<Event>() {
							@Override
							public void onEvent(Event event) throws Exception {
								clearControlFull();
								txtNumeroBoleto.setFocus(true);
								disabledControls(true);
								dbxTotalPago.setDisabled(true);
								btnGuardar.setDisabled(true);
							}
						});
					}
				} catch (Exception e2) {
					e2.printStackTrace();
					log.error(e2);
					DlgMessage.error(this.getClass().getName()+" "+e2.getMessage());
				}
			}
		});
	}

	/**
	 *
	 * @throws Exception
	 */
	private void onClick_btnCancelar()throws Exception{
		clearControlFull();
		txtNumeroBoleto.setFocus(true);
		dbxTotalPago.setDisabled(true);
	}



	/**
	 *
	 * @throws Exception
	 */
	private void clearControlFull()throws Exception{
		equipaje = null;
//		itinerario = null;
		Util.limpiarListbox(ltbxBoletos);
//		txtitinerario.setText("");
		lblKilosLibres.setValue("");
		lblFechaPartida.setValue("");
		lblServicio.setValue("");
		lblRuta.setValue("");
		lblHoraSalida.setValue("");
		itbxNumeroMaletas.setValue(null);
		itbxTotalKilos.setValue(null);
		dbxTotalPago.setValue(null);
		clearControlsExceso();
	}

	/**
	 *
	 * @throws Exception
	 */
	private void disabledControls(boolean isDisabled)throws Exception{
		itbxNumeroMaletas.setDisabled(isDisabled);
		itbxTotalKilos.setDisabled(isDisabled);
		txtGlosa.setDisabled(isDisabled);
		txtobservaciones.setDisabled(isDisabled);
	}

	/**
	 *
	 * @param ventaPasaje
	 * @return
	 * @throws Exception
	 */
	private DetalleEquipaje getDetellaEquipaje(VentaPasaje ventaPasaje,String numeroTicket,int correlativo, Double peso, int principal)throws Exception{
		DetalleEquipaje detalleEquipaje= new DetalleEquipaje();
		detalleEquipaje.setVentaPasaje(ventaPasaje);
//		detalleEquipaje.setNumeroCorrelativo(item.getIndex()+1);
		detalleEquipaje.setNumeroCorrelativo(correlativo);
//		detalleEquipaje.setTicket(Util.autocompleNumberBoleto(serie +"-"+numeroTicket.toString()));
		detalleEquipaje.setTicket(numeroTicket);
//		detalleEquipaje.setPeso(principal==Constantes.TRUE_VALUE && pesoExceso>0? pesoExceso: pesoLibrePromedio);
		detalleEquipaje.setPeso(peso);
		detalleEquipaje.setPrincipal(principal);
		detalleEquipaje.setEstadoRegistro(Constantes.VALUE_ACTIVO);
		UtilData.auditarRegistro(detalleEquipaje, getUsuario(), Executions.getCurrent());

		return detalleEquipaje;
	}

	/**
	 *
	 * @param tipoComprobante
	 * @throws Exception
	 */
	private void loadTipoFormaPago(TipoComprobante tipoComprobante)throws Exception{
		cmbTipoPago.setDisabled(false);
		cmbOperadorTarjeta.setDisabled(true);
		cmbTarjetaCredito.setDisabled(true);
		cmbTipoPago.setText("");
		cmbOperadorTarjeta.setSelectedIndex(-1);
		Util.limpiarCombobox(cmbTipoPago);
		if(tipoComprobante.getRubro().intValue()==Constantes.RUBRO_CARGA) {
			TipoFormaPago tipoFormaPago = ServiceLocator.getTipoFormaPagoManager().buscarPorId(Long.valueOf(Constantes.ID_TIPFORPAG_PCE));
			Comboitem comboitem= new Comboitem(tipoFormaPago.getDenominacion());
			comboitem.setValue(tipoFormaPago);
			cmbTipoPago.appendChild(comboitem);
			cmbTipoPago.setSelectedIndex(0);
			cmbTipoPago.setDisabled(true);
		}else {
			criteriosBusqueda = new TreeMap<>();
			criteriosBusqueda.put("estadoRegistro", Constantes.VALUE_ACTIVO);
			List<TipoFormaPago> resultTipoFormaPago = ServiceLocator.getTipoFormaPagoManager().buscarPorX(criteriosBusqueda, null);
			for(TipoFormaPago tipoFormaPago: resultTipoFormaPago) {
				if(tipoFormaPago.getId().intValue()==Constantes.ID_TIPFORPAG_EFECTIVO || tipoFormaPago.getId().intValue()==Constantes.ID_TIPFORPAG_TARJETA) {
					Comboitem comboitem= new Comboitem(tipoFormaPago.getDenominacion());
					comboitem.setValue(tipoFormaPago);
					cmbTipoPago.appendChild(comboitem);
				}
			}
		}
	}

	/**
	 *
	 * @param ventaPasaje
	 * @throws Exception
	 */
	private void addItemBoleto(VentaPasaje ventaPasaje)throws Exception{
		Listitem item= new Listitem();
		Listcell cell = new Listcell(ventaPasaje.getNumeroBoleto());
		cell.setStyle("font-size:11px");
		item.appendChild(cell);
		cell = new Listcell(ventaPasaje.getPasajero().toString());
		item.appendChild(cell);
		cell= new Listcell(ventaPasaje.getAgenciaPartida().getDenominacion());
		item.appendChild(cell);
		cell = new Listcell(ventaPasaje.getNumeroAsiento().toString());
		cell.setStyle("font-size:11px");
		item.appendChild(cell);

		item.setValue(ventaPasaje);
		ltbxBoletos.appendChild(item);
	}

	private void calcularExceso(Integer valor)throws Exception{
		clearControlsExceso();
		if(valor==null && itbxTotalKilos.getValue()!=null)
			valor = itbxTotalKilos.getValue();
		dbxTotalPago.setDisabled(true);
		if(valor!=null && valor.toString().trim().length()>0 && !(lblKilosLibres.getValue().isEmpty())) {
			int totalKg = valor;
			int totalKgLibres = Integer.valueOf(lblKilosLibres.getValue());
			if(totalKg>totalKgLibres) {
				rowExceso.setVisible(true);
				rowExcesoTarjeta.setVisible(true);
				dbxTotalPago.setDisabled(false);
				generatedGlosaByExceso();
			}
		}
	}

	/**
	 *
	 * @throws Exception
	 */
	private void clearControlsExceso()throws Exception{
		cliente = null;
		rowExceso.setVisible(false);
		rowExcesoTarjeta.setVisible(false);
		cmbTipoComprobante.setSelectedIndex(-1);
		txtNumeroComprobante.setText("");
		cmbTipoPago.setSelectedIndex(-1);
		cmbOperadorTarjeta.setSelectedIndex(-1);
		cmbTarjetaCredito.getItems().clear();
		dbxTotalPago.setValue(null);
		rowRuc.setVisible(false);
		txtNumeroRUC.setText("");
		txtRazonSocial.setText("");
		txtDireccionFiscal.setText("");
		txtGlosa.setText("");
		txtobservaciones.setText("");
	}

	/**
	 *
	 * @throws Exception
	 */
	private void generatedGlosaByExceso()throws Exception{
		String _subGlosa = "";
		for(Listitem item: ltbxBoletos.getItems()) {
			VentaPasaje ventaPasaje = ServiceLocator.getVentaPasajesManager().buscarPorId(((VentaPasaje)item.getValue()).getId());
			_subGlosa = (_subGlosa.isEmpty()?"COMP: "+ventaPasaje.getNumeroBoleto()+" \n"+ventaPasaje.getRuta().toString():", "+"COMP: "+ventaPasaje.getNumeroBoleto()+" \n"+ventaPasaje.getRuta().toString());
			_subGlosa += "\nASIENTO:"+ventaPasaje.getNumeroAsiento().toString();
			_subGlosa += "-->H.SALIDA:"+ventaPasaje.getHoraPartida();
		}
		txtGlosa.setText("EXCESO EQUIPAJES \n" + _subGlosa);
	}

	/**
	 *
	 * @return
	 * @throws Exception
	 */
	private boolean existsListBoleto(String numeroBoleto)throws Exception{
		boolean exists= false;
		for(Listitem item: ltbxBoletos.getItems()) {
			VentaPasaje ventaPasaje = item.getValue();
			if(ventaPasaje.getNumeroBoleto().equals(numeroBoleto)) {
				exists = true;
				break;
			}
		}

		return exists;
	}

	/**
	 * Realiza la busqueda del correlativo para el boleto a emitir.
	 */
	private String onLoadEspecieValorada(TipoComprobante tipoComprobante)throws Exception{
		String numeroComprobante = "";
		EspecieValorada especieValorada=null;
		ControlEspecieValorada controlEspecieValorada = null;
		if(getAgencia().getTipoAgencia().getId().intValue()==Constantes.ID_TIPAGE_TEPSA){
			controlEspecieValorada=UtilData.buscarEspecieValoradaByCaja(tipoComprobante.getId(), getAgencia(), false, getUsuarioHardware(), null);
			numeroComprobante = controlEspecieValorada.toString();
		}else if(getAgencia().getTipoAgencia().getId().intValue()==Constantes.ID_TIPAGE_VIAJES){
			especieValorada=UtilData.buscarEspecieValorada(Constantes.ID_TIPCOM_VOUCHER_AGENCIA_VIAJES, getAgencia(),false);
			numeroComprobante= especieValorada.toString();
		}else{
			especieValorada=UtilData.buscarEspecieValorada(Constantes.ID_TIPCOM_VOUCHER_CORPORATIVO, getAgencia(),false);
			numeroComprobante= especieValorada.toString();
		}

		return numeroComprobante;
	}

	/**
	 * Carga los diferentes tarjetas de credito, de acuerdo al operador seleccionado.
	 */
	public void onLoadTarjetas(){
		try{
			cmbTarjetaCredito.getItems().clear();
			cmbTarjetaCredito.setText("");
			cmbTarjetaCredito.setDisabled(true);

			if(cmbOperadorTarjeta.getSelectedItem().getValue() instanceof OperadorTarjetaCredito){
				OperadorTarjetaCredito operadorTarjetaCredito = cmbOperadorTarjeta.getSelectedItem().getValue();
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
			}
		}catch(Exception ex){
			ex.printStackTrace();
			log.error(ex);
		}
	}

//	/**
//	 * Permite enlazar los controles a la ventana de selecci�n de Itinerario
//	 * @param textboxItinerario :en este Textbox se devolvera el Id del itinerario seleccionado.
//	 * @param button :ha este Button se le adjuntara un listener con la llamada a la ventana de selecci�n de itinerario
//	 * @see WndItinerario:
//	 */
//	public  void enlazarItinerario(final Button button) {
//		button.setTooltiptext("Seleccionar Itinerario");
//		button.addEventListener(Events.ON_CLICK, new EventListener<Event>() {
//			@Override
//			public void onEvent(Event event) throws Exception {
//				final WndSeleccionaItinerario oWndSeleccionaItinerario = new WndSeleccionaItinerario();
//				boolean buscarVentanaParent = true;
//				Component oComponent = button.getParent();
//				while(buscarVentanaParent){
//					 if(oComponent instanceof Window) {
//						 oComponent.appendChild(oWndSeleccionaItinerario);
//						 buscarVentanaParent = false;
//					 }else{
//					 	oComponent = oComponent.getParent();
//					 }
//				}
//				oWndSeleccionaItinerario.onCreate();
//				oWndSeleccionaItinerario.dbFechaInicio.setDisabled(true);
//				oWndSeleccionaItinerario.dbFechaFin.setDisabled(true);
//				oWndSeleccionaItinerario.setMode("modal");
//				oWndSeleccionaItinerario.setVisible(true);
//
//
//				oWndSeleccionaItinerario.addEventListener(Events.ON_SELECT, new EventListener<Event>() {
//					@Override
//					public void onEvent(Event event) throws Exception {
//						itinerario = ServiceLocator.getItinerarioManager().buscarPorId(oWndSeleccionaItinerario.getIdItinerario());
//						txtitinerario.setText(itinerario.getId().toString());
//						lblServicio.setValue(itinerario.getServicio().getDenominacion());
//						lblRuta.setValue(itinerario.getRuta().toString());
//						dtbxFechaPartida.setValue(itinerario.getFechaPartida());
//						lblFechaPartida.setValue(Constantes.FORMAT_DATE.format(itinerario.getFechaPartida()));
//						lblHoraSalida.setValue(itinerario.getHoraPartida());
//						txtNumeroBoleto.setDisabled(false);
//						txtNumeroBoleto.setFocus(true);
//					}
//				});
//			}
//		});
//	}
}
