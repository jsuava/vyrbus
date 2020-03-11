/**
 * Proyecto		: SISVYR
 * Sistema		: Sistema de Ventas y Reservas
 * Descripción	: 
 * Autor		: José Abanto
 * Fecha		: 12/07/2016
 * Hora			: 10:08:11
 */
package com.cystesoft.vyrbus.view.ctrl;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TreeMap;

import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zul.Button;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.Doublebox;
import org.zkoss.zul.Groupbox;
import org.zkoss.zul.Hbox;
import org.zkoss.zul.Image;
import org.zkoss.zul.Intbox;
import org.zkoss.zul.Label;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Tab;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Toolbarbutton;

import com.cystesoft.vyrbus.model.bean.Cliente;
import com.cystesoft.vyrbus.model.bean.Localidad;
import com.cystesoft.vyrbus.model.bean.Ruta;
import com.cystesoft.vyrbus.model.bean.Servicio;
import com.cystesoft.vyrbus.model.bean.TarifaCliente;
import com.cystesoft.vyrbus.model.bean.TarifaClienteDetalle;
import com.cystesoft.vyrbus.model.bean.TipoAsiento;
import com.cystesoft.vyrbus.service.locator.ServiceLocator;
import com.cystesoft.vyrbus.service.util.Constantes;
import com.cystesoft.vyrbus.service.util.Messages;
import com.cystesoft.vyrbus.service.util.Util;
import com.cystesoft.vyrbus.service.util.UtilData;
import com.cystesoft.vyrbus.view.ui.DlgMessage;
import com.cystesoft.vyrbus.view.ui.Events;
import com.cystesoft.vyrbus.view.ui.WndBase;

/**
 * @author jabanto
 *
 */
public class WndTarifarioCliente extends WndBase{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Textbox txtRucCliente;
	private Groupbox gpbxDatosTarifa;
	private Listbox listbxDatosTarifa;	
	private Combobox cmbOrigen;
	private Combobox cmbDestino;
	private Combobox cmbServicio;
	private Datebox dtbxFechaActivacion;
	private Datebox dtbxFechaCaducidad;
	private Intbox itbxAsientoInicio;
	private Intbox itbxAsientoFin;
	private Combobox cmbTipoAsiento;
	private Doublebox dbbxTarifa;
	private Button btnAgregar;
	private Listbox listbxDetalleTarifa;
	private Button btnNuevo;
	private Button btnGuardar;
	private Button btnCancelar;
	private Label lblRaziónSocial;
	private Tab tabLista;
	private Tab tabMantenimiento;
	
	private TarifaCliente tarifaCliente=null;
	private Cliente cliente=null;
	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.view.ui.WndBase#onCreate()
	 */
	@Override
	public void onCreate() throws Exception {
		UtilData.cargarDataCombo(cmbOrigen, Localidad.class, false);
		UtilData.cargarDataCombo(cmbDestino, Localidad.class, false);
		UtilData.cargarDataCombo(cmbServicio, Servicio.class, false);
		UtilData.cargarTipoAsiento(cmbTipoAsiento, false);
		
		dbbxTarifa.setLocale(Locale.US);
		disabledControlsTarifa(true);
		disabledControlsAgregarDetalle(true);
		btnGuardar.setDisabled(true);
		btnCancelar.setDisabled(true);
	}
	
	
	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.view.ui.WndBase#initComponents()
	 */
	@Override
	public void initComponents() {
		txtRucCliente=(Textbox) this.getFellow("txtRucCliente");
		gpbxDatosTarifa=(Groupbox) this.getFellow("gpbxDatosTarifa");
		listbxDatosTarifa=(Listbox) this.getFellow("listbxDatosTarifa");
		cmbOrigen=(Combobox)this.getFellow("cmbOrigen");
		cmbDestino=(Combobox)this.getFellow("cmbDestino");
		cmbServicio=(Combobox)this.getFellow("cmbServicio");
		dtbxFechaActivacion=(Datebox)this.getFellow("dtbxFechaActivacion");
		dtbxFechaCaducidad=(Datebox)this.getFellow("dtbxFechaCaducidad");
		itbxAsientoInicio=(Intbox)this.getFellow("itbxAsientoInicio");
		itbxAsientoFin=(Intbox)this.getFellow("itbxAsientoFin");
		cmbTipoAsiento=(Combobox)this.getFellow("cmbTipoAsiento");
		dbbxTarifa=(Doublebox)this.getFellow("dbbxTarifa");
		btnAgregar=(Button)this.getFellow("btnAgregar");
		listbxDetalleTarifa=(Listbox)this.getFellow("listbxDetalleTarifa");
		btnNuevo=(Button)this.getFellow("btnNuevo");
		btnGuardar=(Button)this.getFellow("btnGuardar");
		btnCancelar=(Button)this.getFellow("btnCancelar");
		lblRaziónSocial=(Label)this.getFellow("lblRaziónSocial");
		tabLista=(Tab)this.getFellow("tabLista");
		tabMantenimiento=(Tab)this.getFellow("tabMantenimiento");
	}
	
	public void buscarCliente(){
		try {
			cliente=null;
			Util.limpiarListbox(listbxDetalleTarifa);
			limpiarControlesTarifa();
			limpiarControlesAgregarDetalle();
			Util.limpiarListbox(listbxDatosTarifa);
			tabLista.setSelected(true);
			if(!(txtRucCliente.getText().trim().isEmpty())){
				TreeMap<String, Object>criteriosBusqueda= new TreeMap<>();
				criteriosBusqueda.put("numeroDocumento", txtRucCliente.getText().trim().toUpperCase());
				criteriosBusqueda.put("estadoRegistro", Constantes.VALUE_ACTIVO);
				List<Cliente> clientes= ServiceLocator.getClienteManager().buscarPorX(criteriosBusqueda, null);
				if(clientes.size()==0){
					DlgMessage.information(Messages.getString("wndTarifacliente.information.noCliente"));
					return;
				}
				cliente=clientes.get(0);
				lblRaziónSocial.setValue(cliente.getRazonSocial());
				List<TarifaClienteDetalle> result=ServiceLocator.getTarifaClienteDetalleManager().buscarByRuc(cliente.getNumeroDocumento());
				int x=0;
				Date fechaActual=Constantes.FORMAT_DATE.parse(Constantes.FORMAT_DATE.format(new Date()));
				String style="font-size:11px !important";
				for(TarifaClienteDetalle tarifaClienteDetalle :result){
					x++;
					boolean isEditable=true;
					if(tarifaClienteDetalle.getTarifaCliente().getFechaCaducidad().getTime()<fechaActual.getTime())
						isEditable=false;

					Listitem item=new Listitem();
					Listcell cell=new Listcell(String.valueOf(x));
					cell.setStyle(style);
					item.appendChild(cell);
					cell= new Listcell(tarifaClienteDetalle.getTarifaCliente().getRuta().toString());
					item.appendChild(cell);
					cell= new Listcell(tarifaClienteDetalle.getTarifaCliente().getServicio().toString());
					item.appendChild(cell);
					cell=new Listcell(Constantes.FORMAT_DATE.format(tarifaClienteDetalle.getTarifaCliente().getFechaActivacion()));
					cell.setStyle(style);
					item.appendChild(cell);
					cell=new Listcell(Constantes.FORMAT_DATE.format(tarifaClienteDetalle.getTarifaCliente().getFechaCaducidad()));
					cell.setStyle(style);
					item.appendChild(cell);
					cell= new Listcell(tarifaClienteDetalle.getAsientoInicio().toString()+" <==> "+tarifaClienteDetalle.getAsientoFin());
					cell.setStyle(style);
					item.appendChild(cell);
					cell= new Listcell(tarifaClienteDetalle.getTipoAsiento().getDenominacion());
					item.appendChild(cell);
					cell= new Listcell(Util.toNumberFormat(tarifaClienteDetalle.getTarifa(),2));
					cell.setStyle(style);
					item.appendChild(cell);
					
					Hbox hbox= new Hbox();
					hbox.setAlign("ceilnter");
					
					Toolbarbutton toolbarbutton= new Toolbarbutton("Editar");
					toolbarbutton.setDisabled((isEditable?false:true));
					toolbarbutton.setStyle("text-transform:none;color:blue");
					toolbarbutton.setAttribute(TarifaClienteDetalle.class.getName(), tarifaClienteDetalle);
					toolbarbutton.addEventListener(Events.ON_CLICK,new EventListener<Event>() {
						@Override
						public void onEvent(Event event) throws Exception {
							try {
								TarifaClienteDetalle tarifaDetalle=(TarifaClienteDetalle) event.getTarget().getAttribute(TarifaClienteDetalle.class.getName());
								editarTarifa(tarifaDetalle);
								
							} catch (Exception e) {
								e.printStackTrace();
								DlgMessage.error(e.getMessage());
							}
						}
					});
					hbox.appendChild(toolbarbutton);
					
					toolbarbutton= new Toolbarbutton("Anular");
					toolbarbutton.setDisabled((isEditable?false:true));
					toolbarbutton.setStyle("text-transform:none;color:blue");
					toolbarbutton.setAttribute(TarifaClienteDetalle.class.getName(), tarifaClienteDetalle);
					toolbarbutton.addEventListener(Events.ON_CLICK,new EventListener<Event>() {
						@Override
						public void onEvent(Event event) throws Exception {
							try {
								TarifaClienteDetalle tarifaDetalle=(TarifaClienteDetalle) event.getTarget().getAttribute(TarifaClienteDetalle.class.getName());
								anularTarifa(tarifaDetalle);
								
							} catch (Exception e) {
								e.printStackTrace();
								DlgMessage.error(e.getMessage());
							}
						}
					});
					hbox.appendChild(toolbarbutton);
					cell= new Listcell();
					cell.appendChild(hbox);
					item.appendChild(cell);
					
					item.setValue(tarifaClienteDetalle);
					listbxDatosTarifa.appendChild(item);
				}
				
				gpbxDatosTarifa.setOpen(true);
			}	
		} catch (Exception e) {
			e.printStackTrace();
			DlgMessage.error(e.getMessage());
		}		
	}
	/**
	 * Realiza la edicion de la tarifa
	 * @param tarifaClienteDetalle
	 * @throws Exception
	 */
	private void editarTarifa(TarifaClienteDetalle tarifaClienteDetalle)throws Exception{
		Util.limpiarListbox(listbxDetalleTarifa);
		DateFormat DATE_FORMAT = new SimpleDateFormat ("yyyyMMdd");
		Date fechaActual= Constantes.FORMAT_DATE.parse(Constantes.FORMAT_DATE.format(new Date()));
		
		tarifaCliente = ServiceLocator.getTarifaClienteManager().buscarPorId(tarifaClienteDetalle.getTarifaCliente().getId().longValue());
		Util.seleccionarValorItemCombo(Localidad.class, cmbOrigen, tarifaCliente.getRuta().getLocalidadOrigen().getId());
		Util.seleccionarValorItemCombo(Localidad.class, cmbDestino, tarifaCliente.getRuta().getLocalidadDestino().getId());
		Util.seleccionarValorItemCombo(Servicio.class, cmbServicio, tarifaCliente.getServicio().getId());
		if(fechaActual.getTime()>=tarifaCliente.getFechaActivacion().getTime())
			dtbxFechaActivacion.setConstraint("after "+DATE_FORMAT.format(tarifaCliente.getFechaActivacion()));
		else
			dtbxFechaActivacion.setConstraint("after "+DATE_FORMAT.format(fechaActual));
		dtbxFechaActivacion.setValue(tarifaCliente.getFechaActivacion());
		dtbxFechaCaducidad.setValue(tarifaCliente.getFechaCaducidad());
		
		
		//Buscando el detalle
		TreeMap<String, Object>criteriosBusqueda=new TreeMap<>();
		criteriosBusqueda.put("tarifaCliente", tarifaCliente);
		criteriosBusqueda.put("estadoRegistro", Constantes.VALUE_ACTIVO);
		List<String>criteriosOrdenar= new ArrayList<>();
		criteriosOrdenar.add("id");
		List<TarifaClienteDetalle> resultTarifaClienteDetalle =ServiceLocator.getTarifaClienteDetalleManager().buscarPorX(criteriosBusqueda, criteriosOrdenar);
		for(TarifaClienteDetalle tarifaClienteDetalle2:resultTarifaClienteDetalle){
			agregarDetalleTarifaItem(tarifaClienteDetalle2);
		}
		
		disabledControlsTarifa(true);
		dtbxFechaActivacion.setDisabled(false);
		dtbxFechaCaducidad.setDisabled(false);
		disabledControlsAgregarDetalle(false);
		
		btnNuevo.setDisabled(true);
		btnGuardar.setDisabled(false);
		btnCancelar.setDisabled(false);
		
		tabMantenimiento.setSelected(true);
	}
	
	/**
	 * Realiza la anulacion de la tarifa
	 * @param tarifaClienteDetalle
	 * @throws Exception
	 */
	private void anularTarifa(final TarifaClienteDetalle tarifaClienteDetalle)throws Exception{
		Messagebox.show(Messages.getString("Generales.question.inactivate"), DlgMessage.NOMBREAPLICACION, DlgMessage.BTN_YESNO, Messagebox.QUESTION,DlgMessage.BTN_DEFAULT_NO, new EventListener<Event>() {
			@Override
			public void onEvent(Event e) throws Exception {
				if(e.getName().equals("onYes")){
					TarifaClienteDetalle detalleTarifaCliente=ServiceLocator.getTarifaClienteDetalleManager().buscarPorId(tarifaClienteDetalle.getId().longValue());
					detalleTarifaCliente.setEstadoRegistro(Constantes.VALUE_INACTIVO);
					UtilData.auditarRegistro(detalleTarifaCliente, true, getUsuario(), Executions.getCurrent());
					ServiceLocator.getTarifaClienteDetalleManager().actualizar(detalleTarifaCliente);
					
					TreeMap<String, Object>criteriosBusqueda=new TreeMap<>();
					criteriosBusqueda.put("tarifaCliente", detalleTarifaCliente.getTarifaCliente());
					criteriosBusqueda.put("estadoRegistro", Constantes.VALUE_ACTIVO);
					List<TarifaClienteDetalle>resultDetalle=ServiceLocator.getTarifaClienteDetalleManager().buscarPorX(criteriosBusqueda, null);
					if(resultDetalle.size()==0){
						TarifaCliente tarifaCliente=ServiceLocator.getTarifaClienteManager().buscarPorId(tarifaClienteDetalle.getTarifaCliente().getId().longValue());
						tarifaCliente.setEstadoRegistro(Constantes.VALUE_INACTIVO);
						UtilData.auditarRegistro(tarifaCliente, true, getUsuario(), Executions.getCurrent());
						ServiceLocator.getTarifaClienteManager().actualizar(tarifaCliente);
					}
					
					buscarCliente();
				}
			}
		});
		
	}
	
	public boolean validadDatosTarifa()throws Exception{
		if(!(cmbOrigen.getSelectedItem().getValue() instanceof Localidad)){
			DlgMessage.information(Messages.getString("wndTarifacliente.information.noOrigen"),cmbOrigen);
			return false;
		}else if (!(cmbDestino.getSelectedItem().getValue() instanceof Localidad)){
			DlgMessage.information(Messages.getString("wndTarifacliente.information.noDestino"),cmbDestino);
			return false;
		}else if (!(cmbServicio.getSelectedItem().getValue() instanceof Servicio)){
			DlgMessage.information(Messages.getString("wndTarifacliente.information.noServicio"),cmbServicio);
			return false;
		}else if (dtbxFechaActivacion.getText()==null || dtbxFechaActivacion.getText().isEmpty() ){
			DlgMessage.information(Messages.getString("wndTarifacliente.information.noFechaActivacion"),dtbxFechaActivacion);
			return false;
		}else if (dtbxFechaCaducidad.getText()==null || dtbxFechaCaducidad.getText().isEmpty() ){
			DlgMessage.information(Messages.getString("wndTarifacliente.information.noFechaCaducidad"),dtbxFechaCaducidad);
			return false;
		}else if (dtbxFechaActivacion.getValue().getTime()>dtbxFechaCaducidad.getValue().getTime()){
			DlgMessage.information(Messages.getString("wndTarifacliente.information.fechaActivacionMayorFechaCaducidad"),dtbxFechaCaducidad);
			return false;
		}
		
		return true;
	}
	
	public void agregarDetalleTarifa(){
		try {
			if(!(validadDatosTarifa()))
				return;
			else if(itbxAsientoInicio.getValue()==null || itbxAsientoInicio.getValue()<=0){
				DlgMessage.information(Messages.getString("wndTarifacliente.information.noAsientoInicio"),itbxAsientoInicio);
				return;
			}else if (itbxAsientoFin.getValue()==null || itbxAsientoFin.getValue()<=0){
				DlgMessage.information(Messages.getString("wndTarifacliente.information.noAsientoFin"),itbxAsientoFin);
				return;
			}else if(itbxAsientoInicio.getValue()>itbxAsientoFin.getValue()){
				DlgMessage.information(Messages.getString("wndTarifacliente.information.asientoInicioMayorFin"),itbxAsientoInicio);
				return;
			}else if (!(cmbTipoAsiento.getSelectedItem().getValue() instanceof TipoAsiento)){
				DlgMessage.information(Messages.getString("wndTarifacliente.information.noTipoAsiento"),cmbTipoAsiento);
				return;
			}else if(itbxAsientoFin.getValue().intValue()>((Servicio)cmbServicio.getSelectedItem().getValue()).getNumeroAsientosPiso1().intValue()){
				DlgMessage.information(Messages.getString("wndTarifacliente.information.noAsientoExcedeCapacidadBus"),itbxAsientoFin);
				return;
			}else if (dbbxTarifa.getValue()==null || dbbxTarifa.getValue()<=0){
				DlgMessage.information(Messages.getString("wndTarifacliente.information.noTarifa"),dbbxTarifa);
				return;
			}
			
			final Ruta ruta=getRuta();
			if(ruta==null){
				DlgMessage.information(Messages.getString("wndTarifacliente.information.noRutaOrigenDestinoSelect"),cmbDestino);
				return;
			}
			
			//Valida que el rango de asientos no es registrado en la dase de batos - Fecha activacion
			TreeMap<String, Object>criteriosBusqueda=new TreeMap<>();
			criteriosBusqueda.put("ruta", ruta);
			criteriosBusqueda.put("servicio", (Servicio)cmbServicio.getSelectedItem().getValue());
			criteriosBusqueda.put("estadoRegistro", Constantes.VALUE_ACTIVO);
			List<String> criteriosOrden=new ArrayList<>();
			criteriosOrden.add("fechaActivacion");
			List<TarifaCliente> resultValidacionTarifa=ServiceLocator.getTarifaClienteManager().buscarPorX(criteriosBusqueda, criteriosOrden);
			for(TarifaCliente tarifaCliente :resultValidacionTarifa){
				//Validando que la fecha activacion no este en un rango de fechas de activacion y suspencion
				if( (dtbxFechaActivacion.getValue().getTime()>=tarifaCliente.getFechaActivacion().getTime() && dtbxFechaActivacion.getValue().getTime()<=tarifaCliente.getFechaCaducidad().getTime()) ||
						(dtbxFechaCaducidad.getValue().getTime()>=tarifaCliente.getFechaActivacion().getTime() && dtbxFechaCaducidad.getValue().getTime()<=tarifaCliente.getFechaCaducidad().getTime())){
					
					//Validacion por sea una edicion 
					if(this.tarifaCliente==null || this.tarifaCliente.getId().intValue()!=tarifaCliente.getId().intValue()){
						//Busca el detalle de la tarifa, para valodar el rando de asientos.
						criteriosBusqueda=new TreeMap<>();
						criteriosBusqueda.put("tarifaCliente", tarifaCliente);
						criteriosBusqueda.put("estadoRegistro", Constantes.VALUE_ACTIVO);
						criteriosOrden=new ArrayList<>();
						criteriosOrden.add("id");
						List<TarifaClienteDetalle> resultValidacionTarifaDet=ServiceLocator.getTarifaClienteDetalleManager().buscarPorX(criteriosBusqueda, criteriosOrden);
						for(TarifaClienteDetalle  tarifaClienteDetalle :resultValidacionTarifaDet){
							//Valida el rango de asientos
							if(itbxAsientoInicio.getValue().intValue()>=tarifaClienteDetalle.getAsientoInicio().intValue() &&
									itbxAsientoInicio.getValue().intValue()<=tarifaClienteDetalle.getAsientoFin().intValue()){
								DlgMessage.information(Messages.getString("wndTarifacliente.information.taridaProgramada"),itbxAsientoFin);
								return;
							}
						}
					}
				}
			}

			//Validando duplicidad de asientos en la grid
			for(Listitem item:listbxDetalleTarifa.getItems()){
				TarifaClienteDetalle tarifaClienteDetalle=item.getValue();
				if(itbxAsientoInicio.getValue().intValue() <= tarifaClienteDetalle.getAsientoFin().intValue()){
					DlgMessage.information(Messages.getString("wndTarifacliente.information.asientoInicioMayorUtilmo"),itbxAsientoFin);
					return;
				}
			}
			
			TarifaClienteDetalle tarifaClienteDetalle=new TarifaClienteDetalle();
			tarifaClienteDetalle.setAsientoInicio(itbxAsientoInicio.getValue());
			tarifaClienteDetalle.setAsientoFin(itbxAsientoFin.getValue());
			tarifaClienteDetalle.setTipoAsiento((TipoAsiento)cmbTipoAsiento.getSelectedItem().getValue());
			tarifaClienteDetalle.setTarifa(dbbxTarifa.getValue());
			
			agregarDetalleTarifaItem(tarifaClienteDetalle);
						
			limpiarControlesAgregarDetalle();
			itbxAsientoInicio.setFocus(true);
		} catch (Exception e) {
			e.printStackTrace();
			DlgMessage.error(e.getMessage());
		}
	}
	
	private void agregarDetalleTarifaItem(TarifaClienteDetalle tarifaClienteDetalle)throws Exception{
		String style="font-size:11px !important";
		Listitem item= new Listitem();
		Listcell cell=new Listcell(tarifaClienteDetalle.getAsientoInicio().toString());
		cell.setStyle(style);
		item.appendChild(cell);
		cell=new Listcell(tarifaClienteDetalle.getAsientoFin().toString());
		cell.setStyle(style);
		item.appendChild(cell);
		cell=new Listcell(tarifaClienteDetalle.getTipoAsiento().getDenominacion());
		item.appendChild(cell);
		cell=new Listcell(Util.toNumberFormat(tarifaClienteDetalle.getTarifa(), 2));
		cell.setStyle(style);
		item.appendChild(cell);
		
		Hbox hbox= new Hbox();
		Image image= new Image("/resources/mp_eliminarEnabled.png");
		image.setAttribute(Listitem.class.getName(), item);
		image.setTooltiptext("Eliminar");
		hbox.appendChild(image);
		image.addEventListener(Events.ON_CLICK, new EventListener<Event>(){
			@Override
			public void onEvent(Event event) throws Exception {
				listbxDetalleTarifa.removeItemAt(((Listitem)event.getTarget().getAttribute(Listitem.class.getName())).getIndex());
			}
		});		
		cell=new Listcell();
		cell.appendChild(hbox);
		item.appendChild(cell);
		
		item.setValue(tarifaClienteDetalle);
		listbxDetalleTarifa.appendChild(item);
	}
	
	private void limpiarControlesAgregarDetalle()throws Exception{
		itbxAsientoInicio.setText(null);
		itbxAsientoFin.setText(null);
		cmbTipoAsiento.setSelectedIndex(0);
		dbbxTarifa.setText(null);
	}
	
	private void limpiarControlesTarifa()throws Exception{
		tarifaCliente=null;
		cmbOrigen.setSelectedIndex(0);
		cmbDestino.setSelectedIndex(0);
		cmbServicio.setSelectedIndex(0);
		dtbxFechaActivacion.setText("");
		dtbxFechaCaducidad.setText("");
	}
	
	private void disabledControlsTarifa(boolean arg){
		cmbOrigen.setDisabled(arg);
		cmbDestino.setDisabled(arg);
		cmbServicio.setDisabled(arg);
		dtbxFechaActivacion.setDisabled(arg);
		dtbxFechaCaducidad.setDisabled(arg);
	}
	private void disabledControlsAgregarDetalle(boolean arg){
		itbxAsientoInicio.setDisabled(arg);
		itbxAsientoFin.setDisabled(arg);
		cmbTipoAsiento.setDisabled(arg);
		dbbxTarifa.setDisabled(arg);
		btnAgregar.setDisabled(arg);
	}
	
	
	public void nuevo(){
		try {
			Util.limpiarListbox(listbxDetalleTarifa);
			limpiarControlesTarifa();
			limpiarControlesAgregarDetalle();
			disabledControlsTarifa(false);
			disabledControlsAgregarDetalle(false);
			dtbxFechaActivacion.setConstraint("no past");
//			dtbxFechaCaducidad.setConstraint("no past");
			btnNuevo.setDisabled(true);
			btnCancelar.setDisabled(false);
			btnGuardar.setDisabled(false);
			cmbOrigen.setFocus(true);
			cmbOrigen.select();
		} catch (Exception e) {
			e.printStackTrace();
			DlgMessage.error(e.getMessage());
		}
	}
	
	public void cancelar(){
		try {
			Util.limpiarListbox(listbxDetalleTarifa);
			limpiarControlesTarifa();
			limpiarControlesAgregarDetalle();
			disabledControlsTarifa(true);
			disabledControlsAgregarDetalle(true);
			btnNuevo.setDisabled(false);
			btnGuardar.setDisabled(true);
			btnCancelar.setDisabled(true);
		} catch (Exception e) {
			e.printStackTrace();
			DlgMessage.error(e.getMessage());
		}
	}
	
	
	/**
	 * Buscamos la ruta, en funcion el origen y destino seleccionados
	 * @return
	 * @throws Exception
	 */
	public Ruta getRuta()throws Exception{
		TreeMap<String, Object>criteriosBusqueda=new TreeMap<>();
		criteriosBusqueda.put("localidadOrigen", cmbOrigen.getSelectedItem().getValue());
		criteriosBusqueda.put("localidadDestino", cmbDestino.getSelectedItem().getValue());
		criteriosBusqueda.put("estadoRegistro", Constantes.VALUE_ACTIVO);
		final List<Ruta> rutas=ServiceLocator.getRutaManager().buscarPorX(criteriosBusqueda, null);
		Ruta ruta=null;
		if(rutas.size()>0)
			ruta=rutas.get(0);
		
		return ruta;
	}
	public void grabar(){
		try {
			if(validadDatosTarifa()){
				
				final Ruta ruta=getRuta();
				if(ruta==null){
					DlgMessage.information(Messages.getString("wndTarifacliente.information.noRutaOrigenDestinoSelect"),cmbDestino);
					return;
				}
				
				Messagebox.show(Messages.getString("Generales.query.guardar"), DlgMessage.NOMBREAPLICACION, DlgMessage.BTN_YESNO, Messagebox.QUESTION,DlgMessage.BTN_DEFAULT_NO, new EventListener<Event>() {
					@Override
					public void onEvent(Event e) throws Exception {
						if(e.getName().equals("onYes")){
							if(tarifaCliente!=null && tarifaCliente.getId()!=null){
								tarifaCliente.setEstadoRegistro(Constantes.VALUE_INACTIVO);
								UtilData.auditarRegistro(tarifaCliente, true, getUsuario(), Executions.getCurrent());
								ServiceLocator.getTarifaClienteManager().actualizar(tarifaCliente);
							}
							tarifaCliente= new TarifaCliente();
							tarifaCliente.setCliente(cliente);
							tarifaCliente.setRuta(ruta);
							tarifaCliente.setServicio((Servicio)cmbServicio.getSelectedItem().getValue());
							tarifaCliente.setFechaActivacion(dtbxFechaActivacion.getValue());
							tarifaCliente.setFechaCaducidad(dtbxFechaCaducidad.getValue());
							tarifaCliente.setEstadoRegistro(Constantes.VALUE_ACTIVO);
							UtilData.auditarRegistro(tarifaCliente, getUsuario(), Executions.getCurrent());
							ServiceLocator.getTarifaClienteManager().guardar(tarifaCliente);
							
							for(Listitem item: listbxDetalleTarifa.getItems()){
								TarifaClienteDetalle tarifaClienteDetalle=item.getValue();
								tarifaClienteDetalle.setTarifaCliente(tarifaCliente);
								tarifaClienteDetalle.setEstadoRegistro(Constantes.VALUE_ACTIVO);
								UtilData.auditarRegistro(tarifaClienteDetalle, getUsuario(), Executions.getCurrent());
								ServiceLocator.getTarifaClienteDetalleManager().guardar(tarifaClienteDetalle);
							}
							
							disabledControlsTarifa(true);
							disabledControlsAgregarDetalle(true);
							btnNuevo.setDisabled(false);
							btnGuardar.setDisabled(true);
							btnCancelar.setDisabled(true);
							
							buscarCliente();
							tabMantenimiento.setSelected(true);
							DlgMessage.information(Messages.getString("Generales.information.exitoGuardar"));
						}
					}
				});
				
			}
		} catch (Exception e) {
			e.printStackTrace();
			DlgMessage.error(e.getMessage());
		}
	}
}
