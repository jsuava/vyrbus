/**
 * Proyecto		: SISVYR
 * Sistema		: Sistema de Ventas y Reservas
 * Descripción	:
 * Autor		: José Sullo Avalos
 * Fecha		: 02/11/2012
 */
package com.cystesoft.vyrbus.view.ctrl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.TreeMap;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zul.Button;
import org.zkoss.zul.Checkbox;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.Include;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listfoot;
import org.zkoss.zul.Listheader;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Tab;
import org.zkoss.zul.Textbox;

import com.cystesoft.vyrbus.model.bean.Agencia;
import com.cystesoft.vyrbus.model.bean.Localidad;
import com.cystesoft.vyrbus.model.bean.Rol;
import com.cystesoft.vyrbus.model.bean.TipoMovimiento;
import com.cystesoft.vyrbus.model.bean.VentaPasaje;
import com.cystesoft.vyrbus.service.exceptions.EsAgenciaTepsaException;
import com.cystesoft.vyrbus.service.exceptions.LiquidacionNullException;
import com.cystesoft.vyrbus.service.locator.ServiceLocator;
import com.cystesoft.vyrbus.service.util.Constantes;
import com.cystesoft.vyrbus.service.util.Messages;
import com.cystesoft.vyrbus.service.util.Util;
import com.cystesoft.vyrbus.service.util.UtilData;
import com.cystesoft.vyrbus.view.ui.DlgMessage;
import com.cystesoft.vyrbus.view.ui.WndBase;

/**
 * @author Jose
 *
 */
public class WndConfirmarReserva extends WndBase {
	private static final long serialVersionUID = 1L;

	private Listbox lbxReservas;
	private Include incConfirmacion;
	private Combobox cmbOrigen;
	private Combobox cmbDestino;
	private Combobox cmbAgencia;
	private Textbox txtPasajero;
	private Textbox txtNumeroDocumento;
	private Listheader listHeaderCheck;
	private Listfoot listFootAnulacionMasiva;
	private Checkbox chbxActivaAnulacionMasiva;
//	private Textbox txtNumeroBoleto;
	private Datebox dtbxFechaPartida;
	private Tab tabBusqueda;
	private Tab tabListado;
	private Tab tabDetalle;
	private VentaPasaje reserva = null;


	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.view.ui.IBase#onCreate()
	 */
	@Override
	public void onCreate() throws Exception {
		try{
			/*Valida si el usuario tiene una liquidación aperturada*/
			if(getDesktop().getSession().getAttribute(Constantes.ATRIBUTO_FECHA_LIQUIDACION)==null)
				throw new LiquidacionNullException();

			Agencia agencia = (Agencia) this.getDesktop().getSession().getAttribute(Constantes.ATRIBUTO_AGENCIA);
			/*valida que se trate de una Agencia Tepsa*/
			if(!(agencia.getTipoAgencia().getId().equals(Constantes.ID_TIPAGE_TEPSA))){
				closeTabWindow();
				throw new EsAgenciaTepsaException();
			}

			String[] buffer = Constantes.USUARIO_ANULA_RESERVA.split(",");
			for (String element : buffer) {
				if(element.equals(getUsuario().getId().toString())){
					cmbAgencia.setDisabled(false);
					break;
				}
			}

			UtilData.cargarDataCombo(cmbOrigen, Localidad.class, false);
			UtilData.cargarDataCombo(cmbDestino, Localidad.class, false);
			TreeMap<String, Object> parametros = new TreeMap<>();
			parametros.put("tipoAgencia.id", Constantes.ID_TIPAGE_TEPSA);
			parametros.put("estadoRegistro", Constantes.VALUE_ACTIVO);
			List<String> criteriosOrdenar = new ArrayList<>();
			criteriosOrdenar.add("denominacion");

			UtilData.cargarAgencia(cmbAgencia, true, parametros, criteriosOrdenar);
			Util.seleccionarValorItemCombo(Agencia.class, cmbAgencia, getAgencia().getId());
			String fecha = Util.DatetoString(new Date(), "yyyyMMdd");
			dtbxFechaPartida.setConstraint("after "+fecha);
			cmbOrigen.setFocus(true);

			/*Anulacion multiple*/
			List<Component>lstComponents=new ArrayList<>();
//			lstComponents.add(listHeaderCheck);
			lstComponents.add(listFootAnulacionMasiva);

			List<Rol>listRolesAcceeso=new ArrayList<>();
			listRolesAcceeso.add(new Rol(Constantes.ID_ROL_SUPER_USUARIO));
//			listRolesAcceeso.add(new Rol(Constantes.ID_ROL_JEFE_VENTAS));
			listRolesAcceeso.add(new Rol(Constantes.ID_ROL_ADMIN));
//			listRolesAcceeso.add(new Rol(Constantes.ID_ROL_ADMIN_COMERCIAL));
//			listRolesAcceeso.add(new Rol(Constantes.ID_ROL_GESTION_CORPORATIVA));
			accesoControlsByRol(lstComponents, listRolesAcceeso, true);

		}catch (LiquidacionNullException lnullex){
			DlgMessage.information(Messages.getString("WndVentaReserva.information.noLiquidacion"));
			closeTabWindow();
		}catch (EsAgenciaTepsaException esagtep) {
			DlgMessage.information(Messages.getString("WndConfirmarReserva.information.noEsAgenciaTepsa"));
		}

	}

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.view.ui.IBase#initComponents()
	 */
	@Override
	public void initComponents() {
		lbxReservas = (Listbox)this.getFellow("lbxReservas");
		incConfirmacion = (Include)this.getFellow("incConfirmacion");
		cmbOrigen = (Combobox)this.getFellow("cmbOrigen");
		cmbDestino = (Combobox)this.getFellow("cmbDestino");
		cmbAgencia = (Combobox)this.getFellow("cmbAgencia");
		txtPasajero = (Textbox)this.getFellow("txtPasajero");
		txtNumeroDocumento = (Textbox)this.getFellow("txtNumeroDocumento");
//		txtNumeroBoleto = (Textbox)this.getFellow("txtNumeroBoleto");
		dtbxFechaPartida = (Datebox)this.getFellow("dtbxFechaPartida");
		tabBusqueda = (Tab)this.getFellow("tabBusqueda");
		tabListado = (Tab)this.getFellow("tabListado");
		tabDetalle = (Tab)this.getFellow("tabDetalle");
		listHeaderCheck=(Listheader)this.getFellow("listHeaderCheck");
		listFootAnulacionMasiva=(Listfoot)this.getFellow("listFootAnulacionMasiva");
		chbxActivaAnulacionMasiva=(Checkbox)this.getFellow("chbxActivaAnulacionMasiva");

		lbxReservas.addEventListener(Events.ON_DOUBLE_CLICK, new EventListener<Event>() {
			@Override
			public void onEvent(Event e){
				if(!(lbxReservas.isCheckmark())){
					VentaPasaje reserva = (VentaPasaje)lbxReservas.getSelectedItem().getValue();
					reserva.setServicio(reserva.getServicio());
					incConfirmacion.setSrc(null);
					incConfirmacion.setDynamicProperty("objetoConfirmar", reserva);
					incConfirmacion.setSrc("confirmacion.zul");
					tabDetalle.setSelected(true);
					lbxReservas.getItems().clear();
				}
			}
		});
	}

	public void buscarReservas(){
		try{
			lbxReservas.getItems().clear();
			Integer idAgencia = (cmbAgencia.getSelectedItem().getValue() instanceof Agencia?((Agencia)cmbAgencia.getSelectedItem().getValue()).getId():null);
			Integer idOrigen = cmbOrigen.getSelectedItem().getValue()==null?null:((Localidad)cmbOrigen.getSelectedItem().getValue()).getId();
			Integer idDestino = cmbDestino.getSelectedItem().getValue()==null?null:((Localidad)cmbDestino.getSelectedItem().getValue()).getId();
			String[] pasajero = txtPasajero.getText().trim().equals("")?null:txtPasajero.getText().trim().toUpperCase().split(" ");
			String numeroDocumento = txtNumeroDocumento.getText().trim().equals("")?null:txtNumeroDocumento.getText().trim().toUpperCase();
//			if(numeroDocumento!=null){
//				numeroDocumento = Util.generateControlNumber(txtNumeroDocumento.getText().trim().toUpperCase());
//				txtNumeroDocumento.setText(numeroDocumento);
//			}
//			String numeroBoleto = txtNumeroBoleto.getText().trim().equals("")?null:txtNumeroBoleto.getText().trim();
			String fechaPartida = dtbxFechaPartida.getValue()==null?null:
					(Util.DatetoString(dtbxFechaPartida.getValue(),Constantes.DATE_FORMAT).equals(Util.DatetoString(Constantes.FECHA_NULL, Constantes.DATE_FORMAT))?
							null:Util.DatetoString(dtbxFechaPartida.getValue(), Constantes.DATE_FORMAT));

			if(idOrigen==null && idDestino==null && pasajero==null && numeroDocumento==null && fechaPartida==null){
				DlgMessage.warning(Messages.getString("WndConfirmarReserva.warning.noExisteIngresoBusqueda"));
				return;
			}

			List<VentaPasaje> lstReservas = ServiceLocator.getVentaPasajesManager().buscarReservasPorConfirmar(idOrigen, idDestino, pasajero, numeroDocumento, null, fechaPartida, idAgencia);
			if(lstReservas.size()>0){
				lbxReservas.getItems().clear();
				Listitem item = null;
				Listcell cell = null;
				int i=0;
				for(VentaPasaje ventaPasaje : lstReservas){
					item = new Listitem();
					item.appendChild(new Listcell());
					cell = new Listcell(ventaPasaje.getId().toString());
					item.appendChild(cell);
					cell = new Listcell(ventaPasaje.getItinerario().getId().toString());
					item.appendChild(cell);
					cell = new Listcell(ventaPasaje.getRuta().toString());
					item.appendChild(cell);
					cell = new Listcell(ventaPasaje.getPasajero().toString());
					item.appendChild(cell);
					cell = new Listcell(ventaPasaje.getCliente()==null?"":ventaPasaje.getCliente().toString());
					item.appendChild(cell);
					cell = new Listcell(ventaPasaje.getNumeroAsiento().toString());
					item.appendChild(cell);
					cell = new Listcell(ventaPasaje.getNumeroControl());
					item.appendChild(cell);
					cell = new Listcell(Util.DatetoString(ventaPasaje.getFechaPartida(), Constantes.DATE_FORMAT));
					item.appendChild(cell);
					cell = new Listcell(ventaPasaje.getImportePagado().toString());
					item.appendChild(cell);
					cell = new Listcell(ventaPasaje.getPreferenciaAlimentaria()==null?"":ventaPasaje.getPreferenciaAlimentaria().getDenominacion());
					item.appendChild(cell);
					cell = new Listcell();
					Button button = new Button("", "resources/mp_anular.png");
					button.setClass("btnImage");
					button.addEventListener(Events.ON_CLICK, new EventListener<Event>() {
						@Override
						public void onEvent(Event e){
							lbxReservas.setSelectedIndex(Integer.valueOf(e.getTarget().getId()));
							VentaPasaje reserva = (VentaPasaje)lbxReservas.getSelectedItem().getValue();
							anularReserva(reserva);
						}
					});
					button.setId(""+i);
					button.setTooltiptext("Haga click aqui si desea anular la reserva.");
					cell.appendChild(button);
					item.appendChild(cell);
					item.setValue(ventaPasaje);
					lbxReservas.appendChild(item);
					i++;
				}
				tabListado.setSelected(true);
			}else{
				DlgMessage.information(Messages.getString("WndConfirmarReserva.information.noReservas"));
				dtbxFechaPartida.setValue(null);
			}
		}catch(Exception ex){
			DlgMessage.error(this.getClass().getSimpleName()+" "+ex.getMessage());
		}
	}

	private void anularReserva(VentaPasaje reserva1){
		reserva = reserva1;
		Messagebox.show(Messages.getString("WndConfirmarReserva.information.confirmaAnularReserva"), DlgMessage.NOMBREAPLICACION, DlgMessage.BTN_YESNO, Messagebox.QUESTION, new EventListener<Event>() {
			@Override
			public void onEvent(Event e){
				if(e.getName().equals("onYes")){
					try{
						reserva.setTipoMovimiento(new TipoMovimiento(Constantes.ID_TIPMOV_ANULACION));
						UtilData.auditarRegistro(reserva, true, getUsuario(), Executions.getCurrent());
						int result = ServiceLocator.getVentaPasajesManager().anularReserva(reserva);
						if(result ==Constantes.CORRECT){
							DlgMessage.information(Messages.getString("WndConfirmarReserva.information.reservaAnulada"));
							buscarReservas();
							tabBusqueda.setSelected(true);
						}else
							DlgMessage.information(Messages.getString("WndConfirmarReserva.information.reservaNoAnulada"));
					}catch(Exception ex){
						DlgMessage.error(this.getClass().getSimpleName()+" "+ex.getMessage());
					}
				}
			}
		});
	}


	/**
	 * Realiza la anulacion de las recervas de manera masiva.
	 * @throws Exception
	 */
	public void anulacionMasiva()throws Exception{
		if(lbxReservas.getSelectedItems().size()>0){
			Messagebox.show(Messages.getString("WndConfirmarReserva.information.confirmaAnularReservaMasiva"), DlgMessage.NOMBREAPLICACION, DlgMessage.BTN_YESNO, Messagebox.QUESTION, new EventListener<Event>() {
				@Override
				public void onEvent(Event e){
					if(e.getName().equals("onYes")){
						try{
							for(Listitem item:lbxReservas.getSelectedItems()){
								VentaPasaje reserva = (VentaPasaje)item.getValue();
								reserva.setTipoMovimiento(new TipoMovimiento(Constantes.ID_TIPMOV_ANULACION));
								UtilData.auditarRegistro(reserva, true, getUsuario(), Executions.getCurrent());
								ServiceLocator.getVentaPasajesManager().anularReserva(reserva);
							}

							DlgMessage.information(Messages.getString("WndConfirmarReserva.information.reservaAnuladaMasiva"));
							buscarReservas();
							tabBusqueda.setSelected(true);
						}catch(Exception ex){
							ex.printStackTrace();
							DlgMessage.error(this.getClass().getSimpleName()+" "+ex.getMessage());
						}
					}
				}
			});
		}

	}


	/**
	 * Activa la anulacion masiva
	 * @throws Exception
	 */
	public void activarAnulacionMasiva()throws Exception{
		try {
			if(chbxActivaAnulacionMasiva.isChecked()){
				lbxReservas.setCheckmark(true);
				lbxReservas.setMultiple(true);
				listHeaderCheck.setVisible(true);
			}else{
				lbxReservas.setCheckmark(false);
				lbxReservas.setMultiple(false);
				listHeaderCheck.setVisible(false);
			}

		} catch (Exception e) {
			e.printStackTrace();
			DlgMessage.error(this.getClass().getSimpleName()+" "+e.getMessage());
		}
	}

}
