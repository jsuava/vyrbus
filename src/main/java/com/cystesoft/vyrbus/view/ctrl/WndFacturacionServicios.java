/**
 * Proyecto		: SISVYR
 * Sistema		: Sistema de Ventas y Reservas
 * Descripción	: 
 * Autor		: José Abanto
 * Fecha		: 24 jun. 2021
 * Hora			: 20:41:09
 */
package com.cystesoft.vyrbus.view.ctrl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.apache.commons.collections.map.HashedMap;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.WrongValueException;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zul.Button;
import org.zkoss.zul.Checkbox;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Comboitem;
import org.zkoss.zul.Doublebox;
import org.zkoss.zul.Image;
import org.zkoss.zul.Label;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Textbox;

import com.cystesoft.vyrbus.model.bean.Cliente;
import com.cystesoft.vyrbus.model.bean.ControlEspecieValorada;
import com.cystesoft.vyrbus.model.bean.FormaPago;
import com.cystesoft.vyrbus.model.bean.Itinerario;
import com.cystesoft.vyrbus.model.bean.Pasajero;
import com.cystesoft.vyrbus.model.bean.Ruta;
import com.cystesoft.vyrbus.model.bean.TarjetaCredito;
import com.cystesoft.vyrbus.model.bean.TipoCobranza;
import com.cystesoft.vyrbus.model.bean.TipoComprobante;
import com.cystesoft.vyrbus.model.bean.TipoDocumento;
import com.cystesoft.vyrbus.model.bean.TipoFormaPago;
import com.cystesoft.vyrbus.model.bean.TipoMovimiento;
import com.cystesoft.vyrbus.model.bean.VentaPasaje;
import com.cystesoft.vyrbus.model.bean.VentaServicioEspecial;
import com.cystesoft.vyrbus.service.exceptions.ClienteException;
import com.cystesoft.vyrbus.service.exceptions.DenominacionNullException;
import com.cystesoft.vyrbus.service.exceptions.DireccionFacturacionNullException;
import com.cystesoft.vyrbus.service.exceptions.FormaPagoNullException;
import com.cystesoft.vyrbus.service.exceptions.ImporteMixtoNullException;
import com.cystesoft.vyrbus.service.exceptions.RazonSocialNullException;
import com.cystesoft.vyrbus.service.exceptions.TipoClienteNullException;
import com.cystesoft.vyrbus.service.exceptions.TipoCobranzaNullException;
import com.cystesoft.vyrbus.service.locator.ServiceLocator;
import com.cystesoft.vyrbus.service.util.Constantes;
import com.cystesoft.vyrbus.service.util.Messages;
import com.cystesoft.vyrbus.service.util.RESTCiva;
import com.cystesoft.vyrbus.service.util.Util;
import com.cystesoft.vyrbus.service.util.UtilData;
import com.cystesoft.vyrbus.view.ui.DlgMessage;
import com.cystesoft.vyrbus.view.ui.WndBase;

/**
 * @author Jose
 *
 */
public class WndFacturacionServicios extends WndBase {

	private static final long serialVersionUID = 1L;
	
	private Label lblDocumento;
	private Label lblCliente;
	private Combobox cmbTipoComprobante;
	private Combobox cmbFormaPago;
	private Combobox cmbTipoCredito;
	private Textbox txtNumeroComprobante;
	private Textbox txtDocumento;
	private Textbox txtCliente;
	private Textbox txtDireccion;
	private Textbox txtGlosa;
	private Button btnNuevo;
	private Image imgBuscar;
	private Doublebox dbxImporte;
	private Doublebox dbxImporteResumen;
	private Doublebox dbxIGVResumen;
	private Doublebox dbxTotalResumen;
	private Checkbox chkIGV;
	private Textbox txtIdCliente;
	
	
	public static final int SEARCH_BY_DOCUMENTO = 1;
	public static final int SEARCH_BY_NOMBRES = 2;
	public static final int SEARCH_BY_RAZON = 3;
	
	private VentaServicioEspecial servicioEspecial;
	
	/* (non-Javadoc)
	 * @see com.cystesoft.vyrbus.view.ui.WndBase#onCreate()
	 */
	@Override
	public void onCreate() throws Exception {
		UtilData.cargarTipoComprobanteSunat(cmbTipoComprobante, false);
		UtilData.cargarDataCombo(cmbTipoCredito, TipoCobranza.class, false);
		cmbTipoComprobante.setSelectedIndex(2);
		
		onLoadEspecieValorada();
		onLoadFormaPago();
		cmbTipoComprobante.setFocus(true);
	}
	/* (non-Javadoc)
	 * @see com.cystesoft.vyrbus.view.ui.WndBase#initComponents()
	 */
	@Override
	public void initComponents() {
		lblDocumento = (Label)getFellow("lblDocumento");
		lblCliente = (Label)getFellow("lblCliente");
		cmbTipoComprobante = (Combobox)getFellow("cmbTipoComprobante");
		cmbFormaPago = (Combobox)getFellow("cmbFormaPago");
		cmbTipoCredito = (Combobox)getFellow("cmbTipoCredito");
		txtNumeroComprobante = (Textbox)getFellow("txtNumeroComprobante");
		txtIdCliente = (Textbox)getFellow("txtIdCliente");
		txtDocumento = (Textbox)getFellow("txtDocumento");
		txtCliente = (Textbox)getFellow("txtCliente");
		txtDireccion = (Textbox)getFellow("txtDireccion");
		txtGlosa = (Textbox)getFellow("txtGlosa");
		btnNuevo = (Button)getFellow("btnNuevo");
		imgBuscar = (Image)getFellow("imgBuscar");
		chkIGV = (Checkbox)getFellow("chkIGV");
		dbxImporte = (Doublebox)getFellow("dbxImporte");
		dbxImporteResumen = (Doublebox)getFellow("dbxImporteResumen");
		dbxIGVResumen = (Doublebox)getFellow("dbxIGVResumen");
		dbxTotalResumen = (Doublebox)getFellow("dbxTotalResumen");
		
		cmbTipoComprobante.addEventListener(Events.ON_CHANGE, new EventListener<Event>() {
			public void onEvent(Event e) {
				onLoadEspecieValorada();
				onCleanControls();
				if(cmbTipoComprobante.getSelectedItem().getValue() instanceof TipoComprobante) {
					if(((TipoComprobante)cmbTipoComprobante.getSelectedItem().getValue()).getId()==Constantes.ID_TIPCOM_FACTURA) {
						lblDocumento.setValue("RUC :");
						lblCliente.setValue("RAZON SOCIAL :");
						
					}else if(((TipoComprobante)cmbTipoComprobante.getSelectedItem().getValue()).getId()==Constantes.ID_TIPCOM_BOLETA_VENTA) {
						lblDocumento.setValue("DOCUMENTO :");
						lblCliente.setValue("CLIENTE :");
					}
				}
				txtDocumento.setFocus(true);
			}
		});
		
		btnNuevo.addEventListener(Events.ON_CLICK, new EventListener<Event>() {
			public void onEvent(Event e) {
				buscarCliente();
			}
		});
		
		imgBuscar.addEventListener(Events.ON_CLICK, new EventListener<Event>() {
			public void onEvent(Event e) {
				buscarCliente();
			}
		});
		
		cmbFormaPago.addEventListener(Events.ON_SELECT, new EventListener<Event>() {
			public void onEvent(Event e) {
				if(cmbFormaPago.getSelectedItem().getValue() instanceof FormaPago) {
					if(((FormaPago)cmbFormaPago.getSelectedItem().getValue()).getId()==Constantes.ID_FORPAG_CREDITO)
						cmbTipoCredito.setDisabled(false);
					else
						cmbTipoCredito.setDisabled(true);
				}
			}
		});
		
		dbxImporte.addEventListener(Events.ON_BLUR, new EventListener<Event>() {
			public void onEvent(Event e) {
				calcularPagos();
			}
		});
		
		chkIGV.addEventListener(Events.ON_CHECK, new EventListener<Event>() {
			public void onEvent(Event e) {
				calcularPagos();
			}
		});
	}
	
	private void onLoadEspecieValorada() {
		try {
			ControlEspecieValorada controlEspecieValorada = null;
			controlEspecieValorada=UtilData.buscarEspecieValoradaByCaja(((TipoComprobante)cmbTipoComprobante.getSelectedItem().getValue()).getId(), getAgencia(), false, getUsuarioHardware(), null);
			txtNumeroComprobante.setValue(controlEspecieValorada.toString());
		}catch (Exception ex) {
			ex.printStackTrace();
		}
	}
	
	/**
	 * Busca los clientes de acuerdo al criterio de busqueda.
	 * @param criterio	: Puede ser DOCUMENTO o RAZON SOCIAL.
	 */
	public void buscarCliente(){
		try{
			TreeMap<String, Object> criterioBusqueda = new TreeMap<String, Object>();
			if(!(txtDocumento.getText().trim().isEmpty())){
				if(((TipoComprobante) cmbTipoComprobante.getSelectedItem().getValue()).getId() == Constantes.ID_TIPCOM_FACTURA) {
					ArrayList<Cliente> lstClientes = null;
					criterioBusqueda.put("numeroDocumento", txtDocumento.getText().toUpperCase()+"%");
					List<String> criteriosOrdenar = new ArrayList<String>();
					criteriosOrdenar.add("razonSocial");
					criteriosOrdenar.add("numeroDocumento");
					lstClientes = ServiceLocator.getClienteManager().buscarPorX(criterioBusqueda, criteriosOrdenar);
					listarCliente(lstClientes);
				}else if(((TipoComprobante) cmbTipoComprobante.getSelectedItem().getValue()).getId() == Constantes.ID_TIPCOM_BOLETA_VENTA) {
					ArrayList<Pasajero> lstPasajeros = null;
					criterioBusqueda.put("numeroDocumento", txtDocumento.getText().toUpperCase()+"%");
					List<String> criteriosOrdenar = new ArrayList<String>();
					criteriosOrdenar.add("numeroDocumento");
					lstPasajeros = ServiceLocator.getPasajeroManager().buscarPorX(criterioBusqueda, criteriosOrdenar);
					listarPasajero(lstPasajeros);
				}
			}else {
				
			}
		}catch(Exception ex){
			DlgMessage.error(this.getClass().getSimpleName()+" "+ex.getMessage());
			ex.printStackTrace();
			log.error(ex);
		}
	}
	
	/**
	 * Permite crear un array de array para luego cargarlos en un listbox.
	 * @param lstRegistros	: ArrayList con los datos de los clientes encontrados.
	 * @throws Exception
	 */
	private void listarCliente(ArrayList<Cliente> lstRegistros)throws Exception{
		
		if(lstRegistros.size()==1){
			Cliente cliente = lstRegistros.get(0);
			txtCliente.setText(cliente.getRazonSocial());
			txtDireccion.setText(cliente.getDireccion());
			cmbFormaPago.setFocus(true);
			txtIdCliente.setText(cliente.getId().toString());
		}else{
			DlgMessage.information(Messages.getString("WndFacturacionServicios.information.noClienteEncontrado"));
			String nroRuc = txtDocumento.getValue();
			onCleanControls();
			txtDocumento.setValue(nroRuc);
			txtDocumento.select();
//			verificarClienteSunat();
		}
	}
	
	/**
	 * Limpia los controles del cliente.
	 */
	public void onCleanControls() {
		txtIdCliente.setText("");
		txtDocumento.setText("");
		txtCliente.setText("");
		txtDireccion.setText("");
		txtGlosa.setText("");
		cmbFormaPago.setSelectedIndex(0);
		cmbTipoCredito.setSelectedIndex(0);
		chkIGV.setChecked(false);
		dbxImporte.setValue(0.00);
		dbxImporteResumen.setValue(0.00);
		dbxIGVResumen.setValue(0.00);
		dbxTotalResumen.setValue(0.00);
	}
	
	public void verificarClienteSunat()throws WrongValueException, Exception{
		if(!(txtDocumento.getText().trim().isEmpty())){			
			String nroDocumento=txtDocumento.getText().trim();
			//Consulta RUC EN sunat
			List<String> ruc = RESTCiva.getDatosRuc(nroDocumento);

			if(ruc!=null){
				txtDocumento.setValue(ruc.get(0));
				txtCliente.setValue(ruc.get(1));
				txtDireccion.setValue(ruc.get(2));
			}else{
				onCleanControls();
			}
		}		
	}
	
	/**
	 * Permite mostrar los datos del pasajero.
	 * @param lstRegistros	: ArrayList con los datos de los pasajeros encontrados.
	 * @throws Exception
	 */
	private void listarPasajero(ArrayList<Pasajero> lstRegistros)throws Exception{
		
		if(lstRegistros.size()==1){
			Pasajero pasajero = lstRegistros.get(0);
			txtCliente.setText(pasajero.getNombresApellidos());
			txtDireccion.setText(pasajero.getDireccion());
			cmbFormaPago.setFocus(true);
			txtIdCliente.setText(pasajero.getId().toString());
		}else{
			DlgMessage.information(Messages.getString("WndFacturacionServicios.information.noPasajeroEncontrado"));
			String nroDNI = txtDocumento.getValue();
			onCleanControls();
			txtDocumento.setValue(nroDNI);
			txtDocumento.select();
//			verificarClienteSunat();
		}
	}
	
	public void onLoadFormaPago() {
		try{
			TreeMap<String, Object> parametros = new TreeMap<String, Object>();
			parametros.put("estadoRegistro", Constantes.VALUE_ACTIVO);
			ArrayList<String> criteriosOrdenar = new ArrayList<String>();
			criteriosOrdenar.add("denominacion");
			ArrayList<FormaPago> lstFormaPago = ServiceLocator.getFormaPagoManager().buscarPorX(parametros, criteriosOrdenar);
			UtilData.cargarGenericData(cmbFormaPago, false);	
			for (int l = 0; l < lstFormaPago.size(); l ++) {
				FormaPago oFormaPago = lstFormaPago.get(l);
				if(oFormaPago.getId()!=Constantes.ID_FORPAG_CORTESIA) {
				Comboitem oComboitem = new Comboitem();	
				oComboitem.setValue(oFormaPago);
				oComboitem.setLabel(oFormaPago.getDenominacion());	
				cmbFormaPago.appendChild(oComboitem);
				}
			}
		}catch(Exception ex){
			ex.printStackTrace();
		}
	}
	
	public void calcularPagos() {
		if(dbxImporte.getValue() != null) {
			if(chkIGV.isChecked()) {
				dbxImporteResumen.setValue(dbxImporte.getValue()/1.18);
				dbxIGVResumen.setValue(dbxImporte.getValue() - (dbxImporte.getValue()/1.18));
				dbxTotalResumen.setValue(dbxImporte.getValue());
			}else {
				dbxImporteResumen.setValue(dbxImporte.getValue());
				dbxIGVResumen.setValue(dbxImporte.getValue() * 0.18);
				dbxTotalResumen.setValue(dbxImporteResumen.getValue() + dbxIGVResumen.getValue());
			}
		}
	}
	
	/**
	 * Limpia los controles para un nuevo documento
	 */
	public void onNew() {
		cmbTipoComprobante.setSelectedIndex(0);
		txtNumeroComprobante.setText("");
		onCleanControls();
		servicioEspecial = new VentaServicioEspecial();
	}
	
	/**
	 * Cancela la operacion y limpia los controles
	 */
	public void onCancel() {
		onCleanControls();
	}
	
	public void onSave() {
		try {
			if(txtIdCliente.getText().trim().equals(""))
				throw new ClienteException();
			else if(txtCliente.getText().trim().equals(""))
				throw new RazonSocialNullException();
			else if(txtDireccion.getText().trim().equals(""))
				throw new DireccionFacturacionNullException();
			else if(!(cmbFormaPago.getSelectedItem().getValue() instanceof FormaPago))
				throw new FormaPagoNullException();
			else if(cmbFormaPago.getSelectedItem().getValue() instanceof FormaPago 
					&& ((FormaPago)cmbFormaPago.getSelectedItem().getValue()).getId() == Constantes.ID_FORPAG_CREDITO 
					&& !(cmbTipoCredito.getSelectedItem().getValue() instanceof TipoCobranza))
				throw new TipoCobranzaNullException();
			else if(dbxImporte.getValue() == null && dbxImporte.getValue()!=0.0)
				throw new ImporteMixtoNullException();
			else if(txtGlosa.getText().trim().equals(""))
				throw new DenominacionNullException();
			
			servicioEspecial = new VentaServicioEspecial();
			servicioEspecial.setTipoComprobante((TipoComprobante)cmbTipoComprobante.getSelectedItem().getValue());
			servicioEspecial.setNumeroComprobante(txtNumeroComprobante.getText().trim().toUpperCase());
			servicioEspecial.setFechaEmision(new Date());
			if(((TipoComprobante)cmbTipoComprobante.getSelectedItem().getValue()).getId() == Constantes.ID_TIPCOM_FACTURA) {
				servicioEspecial.setCliente(new Cliente(Long.valueOf(txtIdCliente.getText())));
				servicioEspecial.setPasajero(null);
				servicioEspecial.setTipoDocumento(new TipoDocumento(Constantes.ID_TIPDOC_RUC));
			}else {
				servicioEspecial.setCliente(null);
				servicioEspecial.setPasajero(new Pasajero(Long.valueOf(txtIdCliente.getText())));
				servicioEspecial.setTipoDocumento(new TipoDocumento(Constantes.ID_TIPDOC_DNI));
			}
			
			servicioEspecial.setNumeroDocumento(txtDocumento.getText().trim());
			servicioEspecial.setNombreCliente(txtCliente.getText().trim());
			servicioEspecial.setDireccion(txtDireccion.getText().trim());
			servicioEspecial.setFormaPago((FormaPago)cmbFormaPago.getSelectedItem().getValue());
			if(((FormaPago)cmbFormaPago.getSelectedItem().getValue()).getId() == Constantes.ID_FORPAG_CREDITO)
				servicioEspecial.setTipoCobranza((TipoCobranza)cmbTipoCredito.getSelectedItem().getValue());
			
			servicioEspecial.setGlosa(txtGlosa.getText().trim().toUpperCase());
			servicioEspecial.setImporte(dbxImporteResumen.getValue());
			servicioEspecial.setIgv(dbxIGVResumen.getValue());
			servicioEspecial.setTotal(dbxTotalResumen.getValue());
			UtilData.auditarRegistro(servicioEspecial, getUsuario(), Executions.getCurrent());
			servicioEspecial.setAgencia(getAgencia());
			servicioEspecial.setUsuarioHardware(getUsuarioHardware());
			servicioEspecial.setEstadoRegistro(Constantes.VALUE_ACTIVO);
			
			Messagebox.show(Messages.getString("WndFacturacionServicios.information.guardar"), DlgMessage.NOMBREAPLICACION, DlgMessage.BTN_YESNO, Messagebox.QUESTION, new EventListener<Event>() {
				@Override
				public void onEvent(Event e){
					try{
						if(e.getName().equals("onYes")){
							int result = ServiceLocator.getVentaServicioEspecialManager().guardar(servicioEspecial);
							
							if(result == Constantes.CORRECT){
								DlgMessage.information(Messages.getString("WndFacturacionServicios.information.exitoGuardar"));
								onCleanControls();
								cmbTipoComprobante.setSelectedIndex(0);
								txtNumeroComprobante.setText("");
							}
						}
					}catch (Exception ex) {
						DlgMessage.error(this.getClass().getName()+" "+ex.getMessage());
						ex.printStackTrace();
					}
				}
			});
			
		}catch(ClienteException cex) {
			DlgMessage.information("Debe de indicar el Cliente al que se emitira el documento", txtDocumento);
		}catch(RazonSocialNullException rsex) {
			DlgMessage.information("El cliente no existe");
		}catch(DireccionFacturacionNullException dfex) {
			DlgMessage.information("Debe ingresar la direccion de facturacion.");
		}catch(FormaPagoNullException fpex) {
			DlgMessage.information("Debe seleccionar la forma de pago.", cmbFormaPago);
		}catch(TipoCobranzaNullException tcex) {
			DlgMessage.information("Debe seleccionar el tipo de cobranza", cmbTipoCredito);
		}catch(ImporteMixtoNullException iex) {
			DlgMessage.information("Debe de ingresar el importe del documento", dbxImporte);
		}catch(DenominacionNullException dex) {
			DlgMessage.information("Debe de ingresar la Glosa del documento a emitir");
		}catch (Exception ex) {
			DlgMessage.information(ex.getMessage());
			ex.printStackTrace();
		}
			
	}
}
