/**
 * Proyecto		: SISVYR
 * Sistema		: Sistema de Ventas y Reservas
 * Descripci�n	:
 * Autor		: Jos� Abanto
 * Fecha		: 21/11/2016
 * Hora			: 10:40:18
 */
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
import org.zkoss.zul.Button;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Comboitem;
import org.zkoss.zul.Doublebox;
import org.zkoss.zul.Groupbox;
import org.zkoss.zul.Image;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Radio;
import org.zkoss.zul.Row;
import org.zkoss.zul.Textbox;

import pe.itsb.vyrbus.model.bean.Cliente;
import pe.itsb.vyrbus.model.bean.ControlEspecieValorada;
import pe.itsb.vyrbus.model.bean.Empresa;
import pe.itsb.vyrbus.model.bean.FormaPago;
import pe.itsb.vyrbus.model.bean.Itinerario;
import pe.itsb.vyrbus.model.bean.OperadorTarjetaCredito;
import pe.itsb.vyrbus.model.bean.TarjetaCredito;
import pe.itsb.vyrbus.model.bean.TipoComprobante;
import pe.itsb.vyrbus.model.bean.TipoFormaPago;
import pe.itsb.vyrbus.model.bean.TipoMovimiento;
import pe.itsb.vyrbus.model.bean.TipoNota;
import pe.itsb.vyrbus.model.bean.VentaPasaje;
import pe.itsb.vyrbus.service.exceptions.LiquidacionNullException;
import pe.itsb.vyrbus.service.locator.ServiceLocator;
import pe.itsb.vyrbus.service.util.Constantes;
import pe.itsb.vyrbus.service.util.Messages;
import pe.itsb.vyrbus.service.util.Util;
import pe.itsb.vyrbus.service.util.UtilData;
import pe.itsb.vyrbus.view.ui.DlgMessage;
import pe.itsb.vyrbus.view.ui.WndBase;

/**
 * @author Jose Abanto
 *
 */
public class WndGastosAdministrativos extends WndBase{

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	private Radio rdCanjePce;
	private Radio rdCobroAdmin;
	private Combobox cmbTipoComprobanteAplica;
	private Textbox txtComprobanteAplica;
	private Image imgBuscarComprobanteAplica;
	private Textbox txtFechaViaje;
	private Textbox txtNumeroAsiento;
	private Textbox txtPasajero;
	private Textbox txtCliente;
	private Textbox txtImportePagado;
	private Textbox txtFormaPago;
	private Textbox txtTipoMovimiento;
	private Combobox cmbEmpresaSearch;
	private Textbox txtEmpresa;
	private Textbox txtIdEmpresa;

	private Combobox cmbTipoComprobante;
	private Textbox txtComprobante;
	private Row rowRuc;
	private Textbox txtRuc;
	private Row rowRazonSocial;
	private Textbox txtRazonSocial;
	private Row rowDireccion;
	private Textbox txtDireccion;
	private Combobox cmbFormaPago;
	private Combobox cmbOperador;
	private Combobox cmbTarjetaCredito;
	private Doublebox dblImportePagado;
	private Textbox txtMotivo;
	private Image imgBuscarCliente;
	private Groupbox gbxGastosAdmin;
//	private Window wndGastosAdministrativos;
	private Button btnGuardar;
	private Radio rdModoManual;
	private Radio rdModoElectronico;

	private VentaPasaje gastoAdmin;
	private VentaPasaje ventaOriginal;
	private Cliente cliente;
	private Date fechaLiquidacion;

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.view.ui.WndBase#initComponents()
	 */
	@Override
	public void initComponents() {
		// TODO Auto-generated method stub
		rdCanjePce = (Radio)this.getFellow("rdCanjePce");
		rdCobroAdmin = (Radio)this.getFellow("rdCobroAdmin");
		cmbTipoComprobanteAplica=(Combobox)this.getFellow("cmbTipoComprobanteAplica");
		txtComprobanteAplica=(Textbox)this.getFellow("txtComprobanteAplica");
		txtFechaViaje=(Textbox)this.getFellow("txtFechaViaje");
		txtNumeroAsiento=(Textbox)this.getFellow("txtNumeroAsiento");
		txtPasajero=(Textbox)this.getFellow("txtPasajero");
		txtCliente=(Textbox)this.getFellow("txtCliente");
		txtImportePagado=(Textbox)this.getFellow("txtImportePagado");
		txtFormaPago=(Textbox)this.getFellow("txtFormaPago");
		cmbTipoComprobante=(Combobox)this.getFellow("cmbTipoComprobante");
		txtComprobante=(Textbox)this.getFellow("txtComprobante");
		rowRuc=(Row)this.getFellow("rowRuc");
		txtRuc=(Textbox)this.getFellow("txtRuc");
		rowRazonSocial=(Row)this.getFellow("rowRazonSocial");
		txtRazonSocial=(Textbox)this.getFellow("txtRazonSocial");
		rowDireccion=(Row)this.getFellow("rowDireccion");
		txtDireccion=(Textbox)this.getFellow("txtDireccion");
		cmbFormaPago=(Combobox)this.getFellow("cmbFormaPago");
		cmbOperador=(Combobox)this.getFellow("cmbOperador");
		cmbTarjetaCredito=(Combobox)this.getFellow("cmbTarjetaCredito");
		dblImportePagado=(Doublebox)this.getFellow("dblImportePagado");
		txtMotivo=(Textbox)this.getFellow("txtMotivo");
		imgBuscarCliente=(Image)this.getFellow("imgBuscarCliente");
		imgBuscarComprobanteAplica=(Image)this.getFellow("imgBuscarComprobanteAplica");
		txtTipoMovimiento=(Textbox)this.getFellow("txtTipoMovimiento");
		gbxGastosAdmin=(Groupbox)this.getFellow("gbxGastosAdmin");
//		wndGastosAdministrativos=(Window)this.getFellow("wndGastosAdministrativos");
		btnGuardar=(Button)this.getFellow("btnGuardar");
		rdModoManual=(Radio)this.getFellow("rdModoManual");
		rdModoElectronico=(Radio)this.getFellow("rdModoElectronico");
		cmbEmpresaSearch = (Combobox)this.getFellow("cmbEmpresaSearch");
		txtEmpresa = (Textbox)this.getFellow("txtEmpresa");
		txtIdEmpresa = (Textbox)this.getFellow("txtIdEmpresa");
		

		rdCanjePce.addEventListener(Events.ON_CHECK, new EventListener<Event>() {
			@Override
			public void onEvent(Event event) throws Exception {
				select_tipoOperacion();
			}
		});
		rdCobroAdmin.addEventListener(Events.ON_CHECK, new EventListener<Event>() {
			@Override
			public void onEvent(Event event) throws Exception {
				select_tipoOperacion();
			}
		});
		txtComprobanteAplica.addEventListener(Events.ON_CHANGE, new EventListener<Event>() {
			@Override
			public void onEvent(Event event) throws Exception {
				try {
					if(cmbTipoComprobanteAplica.getSelectedItem().getValue() instanceof TipoComprobante && !(txtComprobanteAplica.getText().trim().isEmpty()))
						txtComprobanteAplica.setText(Util.autocompleNumberBoleto(txtComprobanteAplica.getText().trim().toUpperCase()));
				} catch (Exception e) {
					e.printStackTrace();
					DlgMessage.error(e.getMessage());
				}
			}
		});

		txtComprobanteAplica.addEventListener(Events.ON_OK, new EventListener<Event>() {
			@Override
			public void onEvent(Event event) throws Exception {
				try {
					onSearchComprobanteAplica();
					cmbTipoComprobante.setFocus(true);
					cmbTipoComprobante.select();
				} catch (Exception e) {
					e.printStackTrace();
					DlgMessage.error(e.getMessage());
				}
			}
		});

		cmbTipoComprobanteAplica.addEventListener(Events.ON_CHANGE, new EventListener<Event>() {
			@Override
			public void onEvent(Event event) throws Exception {
				// TODO Auto-generated method stub
				txtComprobanteAplica.setFocus(true);
				txtComprobanteAplica.select();
			}
		});

		imgBuscarComprobanteAplica.addEventListener(Events.ON_CLICK, new EventListener<Event>() {
			@Override
			public void onEvent(Event event) throws Exception {
				try {
					onSearchComprobanteAplica();
					cmbTipoComprobante.setFocus(true);
					cmbTipoComprobante.select();
				} catch (Exception e) {
					e.printStackTrace();
					DlgMessage.error(e.getMessage());
				}
			}
		});

		imgBuscarCliente.addEventListener(Events.ON_CLICK, new  EventListener<Event>() {
			@Override
			public void onEvent(Event event) throws Exception {
				try {
					onSearchCliente();

				} catch (Exception e) {
					e.printStackTrace();
					DlgMessage.error(e.getMessage());
				}
			}
		});

		txtRuc.addEventListener(Events.ON_OK, new EventListener<Event>() {
			@Override
			public void onEvent(Event event) throws Exception {
				try {
					onSearchCliente();

				} catch (Exception e) {
					e.printStackTrace();
					DlgMessage.error(e.getMessage());
				}
			}
		});

		cmbFormaPago.addEventListener(Events.ON_CHANGE, new EventListener<Event>() {
			@Override
			public void onEvent(Event event) throws Exception {
				try {
					cmbOperador.setSelectedIndex(0);
					cmbOperador.setDisabled(true);
					cmbTarjetaCredito.setText("");
					cmbTarjetaCredito.setDisabled(true);
					if(cmbFormaPago.getSelectedItem().getValue() instanceof TipoFormaPago){
						if(((TipoFormaPago)cmbFormaPago.getSelectedItem().getValue()).getId().intValue()==Constantes.ID_TIPFORPAG_TARJETA){
							cmbOperador.setDisabled(false);
							cmbOperador.setFocus(true);
							cmbOperador.select();
						}else
							txtMotivo.setFocus(true);
					}
				} catch (Exception e) {
					e.printStackTrace();
					DlgMessage.error(e.getMessage());
				}
			}
		});

		cmbOperador.addEventListener(Events.ON_CHANGE, new EventListener<Event>() {
			@Override
			public void onEvent(Event event) throws Exception {
				try {
					cmbTarjetaCredito.setText("");
					cmbTarjetaCredito.setDisabled(true);
					if(cmbOperador.getSelectedItem().getValue() instanceof OperadorTarjetaCredito){
						onLoadTarjetasCredito();
						cmbTarjetaCredito.setDisabled(false);
						cmbTarjetaCredito.setSelectedIndex(0);
					}

				} catch (Exception e) {
					e.printStackTrace();
					DlgMessage.error(e.getMessage());
				}
			}
		});

		cmbTipoComprobante.addEventListener(Events.ON_CHANGE, new EventListener<Event>() {
			@Override
			public void onEvent(Event event) throws Exception {
				try {
					cliente=null;
					txtRuc.setText("");
					txtRazonSocial.setText("");
					txtDireccion.setText("");
					rowRuc.setVisible(false);
					rowRazonSocial.setVisible(false);
					rowDireccion.setVisible(false);

					onLoadCorrelativo();

					if(cmbTipoComprobante.getSelectedItem().getValue() instanceof TipoComprobante){
						if(((TipoComprobante)cmbTipoComprobante.getSelectedItem().getValue()).getId().intValue()==Constantes.ID_TIPCOM_FACTURA){
							rowRuc.setVisible(true);
							rowRazonSocial.setVisible(true);
							rowDireccion.setVisible(true);
							if(txtComprobante.isReadonly())
								txtRuc.setFocus(true);
							else
								txtComprobante.setFocus(true);
						}else{
							if(txtComprobante.isReadonly()){
								cmbFormaPago.setFocus(true);
								cmbFormaPago.select();
							}else
								txtComprobante.setFocus(true);
						}


					}
				} catch (Exception e) {
					e.printStackTrace();
					DlgMessage.error(e.getMessage());
				}
			}
		});


		rdModoManual.addEventListener(Events.ON_CHECK, new EventListener<Event>() {
			@Override
			public void onEvent(Event event) throws Exception {
				try {
					cmbTipoComprobante.setSelectedIndex(0);
					txtComprobante.setText("");
					if(rdModoManual.isChecked())
						txtComprobante.setReadonly(false);
					else
						txtComprobante.setReadonly(true);
				} catch (Exception e) {
					e.printStackTrace();
					DlgMessage.error(e.getMessage());
				}
			}
		});

		rdModoElectronico.addEventListener(Events.ON_CHECK, new EventListener<Event>() {
			@Override
			public void onEvent(Event event) throws Exception {
				try {
					cmbTipoComprobante.setSelectedIndex(0);
					txtComprobante.setText("");
					if(rdModoElectronico.isChecked())
						txtComprobante.setReadonly(true);
					else
						txtComprobante.setReadonly(false);
				} catch (Exception e) {
					e.printStackTrace();
					DlgMessage.error(e.getMessage());
				}
			}
		});

		txtComprobante.addEventListener(Events.ON_BLUR, new EventListener<Event>() {
			@Override
			public void onEvent(Event event) throws Exception {
				try {
					/*Valida si es un ingreso manual y que el numero de correlativo sea v�lido*/
					if(rdModoManual.isChecked() && !(txtComprobante.getText().trim().isEmpty())){
						String[] yc=txtComprobante.getText().trim().split("-");
						if(yc.length==2 && Util.isNumeric(yc[0].toString()) && Util.isNumeric(yc[1].toString()) && yc[0].toString().length()==3)
							txtComprobante.setText(Util.autocompleNumberBoleto(yc[0].toString()+"-"+yc[1].toString()));
					}
				} catch (Exception e) {
					e.printStackTrace();
					DlgMessage.error(e.getMessage());
				}
			}
		});

		txtComprobanteAplica.addEventListener(Events.ON_BLUR, new EventListener<Event>() {
			@Override
			public void onEvent(Event event) throws Exception {
				try {

					txtComprobanteAplica.setText(Util.autocompleNumberBoleto(txtComprobanteAplica.getText().trim()));


				} catch (Exception e) {
					e.printStackTrace();
					DlgMessage.error(e.getMessage());
				}
			}
		});
		
		cmbEmpresaSearch.addEventListener(Events.ON_CHANGE, new EventListener<Event>() {
			public void onEvent(Event event) {
				try {
					clearControlsInfoComprobanteAplica();
					clearControlGastosAdmin();
					txtMotivo.setReadonly(true);
					gbxGastosAdmin.setOpen(false);
					if(cmbEmpresaSearch.getSelectedItem().getValue() instanceof Empresa) {
						txtEmpresa.setText(((Empresa)cmbEmpresaSearch.getSelectedItem().getValue()).getNombreCorto());
						txtIdEmpresa.setText(((Empresa)cmbEmpresaSearch.getSelectedItem().getValue()).getId().toString());
					}
				}catch(Exception ex) {
					ex.printStackTrace();
					DlgMessage.error(ex.getMessage());
				}
			}
		});
	}

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.view.ui.WndBase#onCreate()
	 */
	@Override
	public void onCreate() throws Exception {
		try {
			// TODO Auto-generated method stub
			super.onCreate();

			/*Valida si el usuario tiene una liquidación aperturada*/
			if(getDesktop().getSession().getAttribute(Constantes.ATRIBUTO_FECHA_LIQUIDACION)==null)
				throw new LiquidacionNullException();

			super.onCreate();
			fechaLiquidacion = (Date) this.getDesktop().getSession().getAttribute(Constantes.ATRIBUTO_FECHA_LIQUIDACION);

			select_tipoOperacion();
			onLoadTipoComprobantes(cmbTipoComprobante);
//			onLoadTipoComprobantes(cmbTipoComprobanteAplica);
			UtilData.cargarDataCombo(cmbOperador, OperadorTarjetaCredito.class, false);
			UtilData.cargarDataCombo(cmbEmpresaSearch, Empresa.class, false);
			dblImportePagado.setLocale(Locale.US);

			/*Carga las formas de pago*/
			List<TipoFormaPago>result=ServiceLocator.getTipoFormaPagoManager().buscarPorEstadoRegistro(Constantes.VALUE_ACTIVO, "denominacion");
			Comboitem comboitem=new Comboitem(Constantes.COMBO_LABEL_SELECCIONE);
			cmbFormaPago.appendChild(comboitem);
			for(TipoFormaPago tipoFormaPago: result){
				if(tipoFormaPago.getId().intValue()==Constantes.ID_TIPFORPAG_EFECTIVO || tipoFormaPago.getId().intValue()==Constantes.ID_TIPFORPAG_TARJETA){
					comboitem= new Comboitem(tipoFormaPago.getDenominacion());
					comboitem.setValue(tipoFormaPago);
					cmbFormaPago.appendChild(comboitem);
				}
			}
			cmbFormaPago.setSelectedIndex(0);

		}catch (LiquidacionNullException lnullex){
			DlgMessage.information(Messages.getString("WndVentaReserva.information.noLiquidacion"));
			closeTabWindow();
		} catch (Exception e) {
			e.printStackTrace();
			DlgMessage.error(e.getMessage());
		}
	}

	/**
	 *
	 * @throws Exception
	 */
	private void select_tipoOperacion()throws Exception{
		Util.limpiarCombobox(cmbTipoComprobanteAplica);
		if(rdCanjePce.isChecked()) {
			onLoadTipoComprobanteCanjePCE();
			//txtComprobanteAplica.setFocus(true);
		}else {
			onLoadTipoComprobantes(cmbTipoComprobanteAplica);
		}
		cmbEmpresaSearch.setFocus(true);
	}

	/**
	 *
	 * @throws Exception
	 */
	private void onLoadTipoComprobanteCanjePCE()throws Exception{
		UtilData.cargarGenericData(cmbTipoComprobanteAplica, false);
		TipoComprobante tipoComprobante = ServiceLocator.getTipoComprobanteManager().buscarPorId(Long.valueOf(Constantes.ID_TIPCOM_GUIA_TRANSPORTISTA));
		Comboitem comboitem= new Comboitem(tipoComprobante.getDenominacion());
		comboitem.setValue(tipoComprobante);
		cmbTipoComprobanteAplica.appendChild(comboitem);
		cmbTipoComprobanteAplica.setSelectedIndex(1);
	}

	/**
	 * Limpia los controles del ingreso de los gastos administrativos.
	 * @throws Exception
	 */
	private void clearControlGastosAdmin()throws Exception{
		txtComprobante.setText("");
		rdModoManual.setChecked(true);
		cliente=null;
		cmbTipoComprobante.setSelectedIndex(0);
		txtTipoMovimiento.setText("");
		txtRuc.setText("");
		txtRazonSocial.setText("");
		txtDireccion.setText("");
		rowRuc.setVisible(false);
		rowRazonSocial.setVisible(false);
		rowDireccion.setVisible(false);
		cmbFormaPago.setSelectedIndex(0);
		cmbOperador.setSelectedIndex(0);
		cmbOperador.setDisabled(true);
		Util.limpiarCombobox(cmbTarjetaCredito);
		cmbTarjetaCredito.setText("");
		cmbTarjetaCredito.setDisabled(true);
		dblImportePagado.setValue(0.00);
		txtMotivo.setText("");
		txtEmpresa.setText("");
		txtIdEmpresa.setText("");
	}

	/**
	 * Realiza la busqueda del comprobante al cual se le va aplicar el gasto administratrivo
	 * @throws Exception
	 */
	private void onSearchComprobanteAplica()throws Exception{
		if(txtComprobanteAplica.isDisabled())
			return;
		clearControlsInfoComprobanteAplica();
		clearControlGastosAdmin();
		txtMotivo.setReadonly(true);
		gbxGastosAdmin.setOpen(false);

		if(!(cmbEmpresaSearch.getSelectedItem().getValue() instanceof Empresa)) {
			DlgMessage.information(Messages.getString("wndGastosAdministrativos.information.noEmpresaSelect"),cmbEmpresaSearch);
			return;
		}else if(!(cmbTipoComprobanteAplica.getSelectedItem().getValue() instanceof TipoComprobante)){
			DlgMessage.information(Messages.getString("wndGastosAdministrativos.information.noTipoComprobanteApplica"),cmbTipoComprobanteAplica);
			return;
		}else if (txtComprobanteAplica.getText().trim().isEmpty()){
			DlgMessage.information(Messages.getString("wndGastosAdministrativos.information.noNumeroTipoComprobanteApplica"),txtComprobanteAplica);
			return;
		}

		TreeMap<String, Object>criteriosBusqueda= new TreeMap<>();
		criteriosBusqueda.put("empresa", cmbEmpresaSearch.getSelectedItem().getValue());
		criteriosBusqueda.put("numeroBoleto", txtComprobanteAplica.getText().trim().toUpperCase());
		criteriosBusqueda.put("tipoComprobante", cmbTipoComprobanteAplica.getSelectedItem().getValue());
		criteriosBusqueda.put("estadoRegistro", Constantes.VALUE_ACTIVO);

		List<VentaPasaje> result=ServiceLocator.getVentaPasajesManager().buscarPorX(criteriosBusqueda, null);
		for(VentaPasaje ventaPasaje:result){
			if(ventaPasaje.getTipoMovimiento().getId().intValue()!=Constantes.ID_TIPMOV_GASTOS_ADMINISTRATIVOS){
				if(ventaPasaje.getTipoMovimiento().getId().intValue()!=Constantes.ID_TIPMOV_ANULACION_SISTEMA ||
						(result.size()==0 && ventaPasaje.getTipoMovimiento().getId().intValue()==Constantes.ID_TIPMOV_ANULACION) ||
						(result.size()>0 && ventaPasaje.getTipoMovimiento().getId().intValue()!=Constantes.ID_TIPMOV_ANULACION)){

					ventaOriginal=ventaPasaje;
					break;
				}
			}
		}

		if(ventaOriginal==null){
			DlgMessage.information(Messages.getString("wndGastosAdministrativos.information.noSearchComprobanteAplica"),txtComprobanteAplica);
			return;
		}

		rdModoElectronico.setChecked(true);
		if(rdModoManual.isChecked())
			txtComprobante.setReadonly(false);

		txtTipoMovimiento.setText(ventaOriginal.getTipoMovimiento().getDenominacion());
		txtFechaViaje.setText(ventaOriginal.getFechaPartida()!=null?Constantes.FORMAT_DATE.format(ventaOriginal.getFechaPartida()):"");
		txtNumeroAsiento.setText(ventaOriginal.getNumeroAsiento()!=null?ventaOriginal.getNumeroAsiento().toString():"");
		txtPasajero.setText(ventaOriginal.getPasajero().toString());
		txtCliente.setText(ventaOriginal.getCliente()!=null?ventaOriginal.getCliente().toString():"");
		txtImportePagado.setText(Util.toNumberFormat(ventaOriginal.getImportePagado(), 2));
		txtFormaPago.setText(ventaOriginal.getTipoFormaPago().getDenominacion());

		/*Valida la forma de pago para determinar el pago*/
		if(ventaOriginal.getTipoComprobante().getId().intValue()==Constantes.ID_TIPCOM_GUIA_TRANSPORTISTA) {
			dblImportePagado.setValue(ventaOriginal.getImportePagado());
			txtMotivo.setText(ventaOriginal.getObservaciones()!=null?ventaOriginal.getObservaciones():"");
		}else {
			TipoNota tipoNota=ServiceLocator.getTipoNotaManager().buscarPorId((long)Constantes.ID_TIPNOTA_CREDITO_DEVOLUCION);
			if(ventaOriginal.getTipoFormaPago().getId().intValue()==Constantes.ID_TIPFORPAG_TARJETA)
				dblImportePagado.setValue(tipoNota.getGastoAdminTarjeta());
			else
				dblImportePagado.setValue(tipoNota.getGastoAdminEfectivo());
		}

		txtMotivo.setReadonly(false);
		gbxGastosAdmin.setOpen(true);
	}

	/**
	 * Realiza la busqueda del cliente
	 * @throws Exception
	 */
	private void onSearchCliente()throws Exception{
		if(txtRuc.isDisabled())
			return;

		cliente=null;
		txtRazonSocial.setText("");
		txtDireccion.setText("");
		if(!(txtRuc.getText().trim().isEmpty())){
			TreeMap<String, Object>criteriosBusqueda= new TreeMap<>();
			criteriosBusqueda.put("numeroDocumento", txtRuc.getText().trim());
			criteriosBusqueda.put("estadoRegistro", Constantes.VALUE_ACTIVO);
			List<Cliente>result=ServiceLocator.getClienteManager().buscarPorX(criteriosBusqueda, null);
			if(result.size()>0){
				cliente=result.get(0);
				txtRazonSocial.setText(cliente.toString());
				txtDireccion.setText(cliente.getDireccion()!=null?cliente.getDireccion():"");

				cmbFormaPago.setFocus(true);
				cmbFormaPago.select();
			}else{
				DlgMessage.information(Messages.getString("WndVentaReserva.information.noCliente"),txtRuc);
			}
		}
	}

	/**
	 * Realiza la busqueda de las tarjetas de credito, seegun el operador que se haya seleccionado.
	 * @throws Exception
	 */
	private void onLoadTarjetasCredito()throws Exception{
		Util.limpiarCombobox(cmbTarjetaCredito);
		Comboitem comboitem=new Comboitem(Constantes.COMBO_LABEL_SELECCIONE);
		cmbTarjetaCredito.appendChild(comboitem);

		TreeMap<String, Object>criteriosBusqueda= new TreeMap<>();
		criteriosBusqueda.put("operadorTarjetaCredito", cmbOperador.getSelectedItem().getValue());
		criteriosBusqueda.put("estadoRegistro", Constantes.VALUE_ACTIVO);
		List<String>criteriosOrdenar= new ArrayList<>();
		criteriosOrdenar.add("denominacion");
		List<TarjetaCredito> result=ServiceLocator.getTarjetaCreditoManager().buscarPorX(criteriosBusqueda, criteriosOrdenar);
		for(TarjetaCredito tarjetaCredito: result){
			comboitem= new Comboitem(tarjetaCredito.getDenominacion());
			comboitem.setValue(tarjetaCredito);
			cmbTarjetaCredito.appendChild(comboitem);
		}
	}

	/**
	 * Carga los tipos de comprobante.
	 * @param combobox	: control en donde se van a cargar
	 * @throws Exception
	 */
	private void onLoadTipoComprobantes(Combobox combobox)throws Exception{
		List<TipoComprobante> result= ServiceLocator.getTipoComprobanteManager().buscarPorEstadoRegistro(Constantes.VALUE_ACTIVO, "denominacion");
		Comboitem comboitem=new Comboitem(Constantes.COMBO_LABEL_SELECCIONE);
		combobox.appendChild(comboitem);
		for(TipoComprobante tipoComprobante: result){
			if(combobox.getId().equals(cmbTipoComprobante.getId())){
				if(tipoComprobante.getId().intValue()==Constantes.ID_TIPCOM_BOLETA_VENTA ||
						tipoComprobante.getId().intValue()==Constantes.ID_TIPCOM_FACTURA){

					comboitem= new Comboitem(tipoComprobante.getDenominacion());
					comboitem.setValue(tipoComprobante);
					combobox.appendChild(comboitem);
				}
			}else{
				if(tipoComprobante.getId().intValue()==Constantes.ID_TIPCOM_BOLETA_VENTA ||
						tipoComprobante.getId().intValue()==Constantes.ID_TIPCOM_FACTURA ||
						tipoComprobante.getId().intValue()==Constantes.ID_TIPCOM_BOLETO_VIAJE){
					comboitem= new Comboitem(tipoComprobante.getDenominacion());
					comboitem.setValue(tipoComprobante);
					combobox.appendChild(comboitem);
				}
			}
		}
		combobox.setSelectedIndex(0);
	}

	/**
	 * Carga el correlativo, segun el tipo de comprobante que solicite el cliente.
	 * @throws Exception
	 */
	private void onLoadCorrelativo()throws Exception{
		txtComprobante.setText("");
		if(rdModoElectronico.isChecked()){
			if(cmbTipoComprobante.getSelectedItem().getValue() instanceof TipoComprobante){
				/*BEGIN 16/06/2021 - javalos - Correlativo by caja*/
//				EspecieValorada especieValorada= UtilData.buscarEspecieValorada(((TipoComprobante)cmbTipoComprobante.getSelectedItem().getValue()).getId(), getAgencia(), false);
//				if(especieValorada!=null)
//					txtComprobante.setText(especieValorada.toString());
				ControlEspecieValorada controlEspecieValorada= UtilData.buscarEspecieValoradaByCaja(((TipoComprobante)cmbTipoComprobante.getSelectedItem().getValue()).getId(), getAgencia(), false, getUsuarioHardware(), null, Integer.valueOf(txtIdEmpresa.getText()));
				if(controlEspecieValorada!=null)
					txtComprobante.setText(controlEspecieValorada.toString());
				/*BEGIN 16/06/2021 - javalos - Correlativo by caja*/
			}
		}
	}

	private void disableControls(boolean disabled)throws Exception{
		txtComprobante.setReadonly(true);
		cmbTipoComprobanteAplica.setDisabled(disabled);
		txtComprobanteAplica.setDisabled(disabled);
		cmbTipoComprobante.setDisabled(disabled);
		txtRuc.setDisabled(disabled);
		txtRazonSocial.setDisabled(disabled);
		txtDireccion.setDisabled(disabled);
		cmbFormaPago.setDisabled(disabled);
		if(!cmbOperador.isDisabled())
			cmbOperador.setDisabled(disabled);
		if(!cmbTarjetaCredito.isDisabled())
			cmbTarjetaCredito.setDisabled(disabled);
		txtMotivo.setDisabled(disabled);
	}

	/**
	 * Guara el gasto administrativo
	 */
	public void onGrabar(){
		try {
			if(ventaOriginal==null){
				DlgMessage.information(Messages.getString("wndGastosAdministrativos.information.noComprobanteAplica"),txtComprobanteAplica);
				return;
			}else if (!(cmbTipoComprobante.getSelectedItem().getValue() instanceof TipoComprobante)){
				DlgMessage.information(Messages.getString("wndGastosAdministrativos.information.noTipoComprobante"),cmbTipoComprobante);
				return;
			}else if (rdModoElectronico.isChecked() && txtComprobante.getText().trim().isEmpty()){
				DlgMessage.information(Messages.getString("wndGastosAdministrativos.information.noCorrelativo"),txtComprobante);
				return;
			}else if (rdModoManual.isChecked() && txtComprobante.getText().trim().isEmpty()){
				DlgMessage.information(Messages.getString("wndGastosAdministrativos.information.noNumeroComprobante"),txtComprobante);
				return;
			}

			if(((TipoComprobante)cmbTipoComprobante.getSelectedItem().getValue()).getId().intValue()==Constantes.ID_TIPCOM_FACTURA){
				if(cliente==null){
					DlgMessage.information(Messages.getString("wndGastosAdministrativos.information.noCliente"),txtRuc);
					return;
				}else if(cliente.getDireccion()==null || cliente.getDireccion().isEmpty()){
					DlgMessage.information(Messages.getString("WndVentaReserva.information.noDireccionCliente"),txtRuc);
					return;
				}
			}

			if(!(cmbFormaPago.getSelectedItem().getValue() instanceof TipoFormaPago)){
				DlgMessage.information(Messages.getString("wndGastosAdministrativos.information.noFormaPago"),cmbFormaPago);
				return;
			}else if (((TipoFormaPago)cmbFormaPago.getSelectedItem().getValue()).getId().intValue()==Constantes.ID_TIPFORPAG_TARJETA){
				if(!(cmbOperador.getSelectedItem().getValue() instanceof OperadorTarjetaCredito)){
					DlgMessage.information(Messages.getString("wndGastosAdministrativos.information.noOperadorTarjeta"),cmbOperador);
					return;
				}else if (!(cmbTarjetaCredito.getSelectedItem().getValue() instanceof TarjetaCredito)){
					DlgMessage.information(Messages.getString("wndGastosAdministrativos.information.noTarjetaCredito"),cmbTarjetaCredito);
					return;
				}
			}

			if(dblImportePagado.getText().isEmpty() || dblImportePagado.getValue()<=0){
				DlgMessage.information(Messages.getString("wndGastosAdministrativos.information.noImportePagado"));
				return;
			}else if(txtMotivo.getText().trim().isEmpty()){
				DlgMessage.information(Messages.getString("wndGastosAdministrativos.information.noMotivo"),txtMotivo);
				return;
			}else if (txtMotivo.getText().trim().length()<10){
				DlgMessage.information(Messages.getString("wndGastosAdministrativos.information.noMotivoInvalid"),txtMotivo);
				return;
			}

			/*Valida si es un ingreso manual y que el numero de correlativo sea v�lido*/
			if(rdModoManual.isChecked() && !(txtComprobante.getText().trim().isEmpty())){
				String[] yc=txtComprobante.getText().trim().split("-");
				if(yc.length!=2){
					DlgMessage.information(Messages.getString("wndGastosAdministrativos.information.noComprobanteInvalid"),txtComprobante);
					return;
				}else if(!(Util.isNumeric(yc[0].toString()))){/*Validando que sean numericos el numero de serie y correlativo*/
					DlgMessage.information(Messages.getString("wndGastosAdministrativos.information.noNumeroSerieInvalid"));
					return;
				}else if (yc[0].toString().length()!=3){
					DlgMessage.information(Messages.getString("wndGastosAdministrativos.information.noNumeroSerieInvalid"));
					return;
				}else if (!(Util.isNumeric(yc[1].toString()))){
					DlgMessage.information(Messages.getString("wndGastosAdministrativos.information.noCorrelativoInvalid"),txtComprobante);
					return;
				}else{
					txtComprobante.setText(Util.autocompleNumberBoleto(yc[0].toString()+"-"+yc[1].toString()));
				}
			}

			gastoAdmin= new VentaPasaje();
			gastoAdmin.setVentaOriginal(ventaOriginal.getVentaOriginal());
			gastoAdmin.setVentaPasaje(ventaOriginal);
			gastoAdmin.setItinerario(new Itinerario((long)1));
			gastoAdmin.setRuta(ventaOriginal.getRuta());
			gastoAdmin.setCliente(cliente);
			gastoAdmin.setPasajero(ventaOriginal.getPasajero());
			gastoAdmin.setFormaPago(new FormaPago(Constantes.ID_FORPAG_CONTADO));
			gastoAdmin.setServicio(ventaOriginal.getServicio());
			gastoAdmin.setTipoComprobante((TipoComprobante)cmbTipoComprobante.getSelectedItem().getValue());

			gastoAdmin.setTipoFormaPago((TipoFormaPago)cmbFormaPago.getSelectedItem().getValue());
			if(gastoAdmin.getTipoFormaPago().getId().intValue()==Constantes.ID_TIPFORPAG_TARJETA)
				gastoAdmin.setTarjetaCredito((TarjetaCredito)cmbTarjetaCredito.getSelectedItem().getValue());
			gastoAdmin.setNumeroBoleto(txtComprobante.getText().trim().toUpperCase());
			gastoAdmin.setNumeroBoletoAnterior(ventaOriginal.getNumeroBoleto());
			gastoAdmin.setSecuencial(Constantes.FALSE_VALUE);
			gastoAdmin.setTarifa(dblImportePagado.getValue());
			gastoAdmin.setRecargo(0.00);
			gastoAdmin.setDescuento(0.00);
			gastoAdmin.setPenalidad(0.00);
			gastoAdmin.setAcuenta(0.00);
			gastoAdmin.setImportePagado(dblImportePagado.getValue());
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
			if(ventaOriginal.getTipoComprobante().getId()==Constantes.ID_TIPCOM_GUIA_TRANSPORTISTA) {
				gastoAdmin.setObservaciones("CANJE PCE ==> "+txtMotivo.getText().trim());
				gastoAdmin.setTipoMovimiento(new TipoMovimiento(Constantes.ID_TIPMOV_CANJE_GRT));
			}else {
				gastoAdmin.setTipoMovimiento(new TipoMovimiento(Constantes.ID_TIPMOV_GASTOS_ADMINISTRATIVOS));
				gastoAdmin.setObservaciones("::::REGULARIZACION::::"+txtMotivo.getText().trim().toUpperCase());
			}

			/*BEGIN 16/06/2021 - javalos - Correlativo by caja*/
			gastoAdmin.setUsuarioHardware(getUsuarioHardware());
			/*END 16/06/2021 - javalos - Correlativo by caja*/
			UtilData.auditarRegistro(gastoAdmin, getUsuario(), Executions.getCurrent());

			Double igv=gastoAdmin.getImportePagado()- Double.valueOf(Util.toNumberFormat(gastoAdmin.getImportePagado()/((Constantes.IGV/100)+1),2));
			gastoAdmin.setIgv(igv);

			Messagebox.show(Messages.getString("wndGastosAdministrativos.question.noconfirmar"), DlgMessage.NOMBREAPLICACION+" PREGUNTA", DlgMessage.BTN_YESNO, Messagebox.QUESTION, Messagebox.NO, new EventListener<Event>() {
				@Override
				public void onEvent(Event e) throws Exception{
					if(e.getName().equals(Messagebox.ON_YES)){

						Boolean generarCorrelativo=rdModoElectronico.isChecked();
						int result=ServiceLocator.getVentaPasajesManager().generarGastoAdministrativo(gastoAdmin, generarCorrelativo);
						/*Confirma la operacion*/
						if(result==Constantes.CORRECT){
							/*Solo si es electronico*/
							if(rdModoElectronico.isChecked()){
								gastoAdmin=ServiceLocator.getVentaPasajesManager().buscarVentaById(gastoAdmin.getId());
								txtComprobante.setText(gastoAdmin.getNumeroBoleto());//actualiza por si haya cambiado

								List<VentaPasaje>listVentaPasaje= new ArrayList<>();
								listVentaPasaje.add(gastoAdmin);
//								WSFE.sendVenta(listVentaPasaje, wndGastosAdministrativos, true, null);
							}

							disableControls(true);
							btnGuardar.setDisabled(true);

							DlgMessage.information(Messages.getString("wndGastosAdministrativos.information.noConfirmaGuardado"));
						}
					}
				}
			});
		} catch (Exception e) {
			e.printStackTrace();
			DlgMessage.error(e.getMessage());
		}
	}

	/**
	 * Limpia los controles con informacion del comprobante al que se va aplicar el gasto administrativo
	 * @throws Exception
	 */
	private void clearControlsInfoComprobanteAplica()throws Exception{
		ventaOriginal=null;
		txtFechaViaje.setText("");
		txtNumeroAsiento.setText("");
		txtPasajero.setText("");
		txtCliente.setText("");
		txtImportePagado.setText("");
		txtFormaPago.setText("");
	}

	/**
	 * Habilita los controles para un nuevo registro
	 */
	public void onNuevo(){
		try {
			gbxGastosAdmin.setOpen(false);
			cmbTipoComprobanteAplica.setSelectedIndex(0);
			txtComprobanteAplica.setText("");
			clearControlsInfoComprobanteAplica();
			clearControlGastosAdmin();
			disableControls(false);
			btnGuardar.setDisabled(false);

			cmbTipoComprobanteAplica.setFocus(true);
			cmbTipoComprobanteAplica.select();
		} catch (Exception e) {
			e.printStackTrace();
			DlgMessage.error(e.getMessage());
		}
	}
}
