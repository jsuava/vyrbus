/**
 * Proyecto		: SISVYR
 * Sistema		: Sistema de Ventas y Reservas
 * Descripción	:
 * Autor		: José Sullo Avalos
 * Fecha		: 02/11/2012
 */
package com.cystesoft.vyrbus.view.ctrl;

import java.util.Date;
import java.util.List;

import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zul.Button;
import org.zkoss.zul.Calendar;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Div;
import org.zkoss.zul.Include;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Tab;
import org.zkoss.zul.Textbox;

import com.cystesoft.vyrbus.model.bean.DetalleItinerario;
import com.cystesoft.vyrbus.model.bean.Localidad;
import com.cystesoft.vyrbus.model.bean.TarifaRegular;
import com.cystesoft.vyrbus.model.bean.VentaPasaje;
import com.cystesoft.vyrbus.service.exceptions.ItinerarioException;
import com.cystesoft.vyrbus.service.exceptions.LiquidacionNullException;
import com.cystesoft.vyrbus.service.exceptions.LocalidadNullException;
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
public class WndConfirmarFechaAbierta extends WndBase implements EventListener<Event> {
	private static final long serialVersionUID = 1L;

	private Listbox lbxVentasFechaAbierta;
	private Include incConfirmacion;
	private Combobox cmbOrigen;
	private Combobox cmbDestino;
	private Combobox cmbOrigenItinerario;
	private Combobox cmbDestinoItinerario;
	private Textbox txtPasajero;
	private Textbox txtNumeroControl;
	private Textbox txtNumeroBoleto;
	private Textbox txtNumeroDocumento;
	private Tab tabBusqueda;
	private Tab tabListado;
	private Tab tabDetalle;
	private Calendar cldrFechaPartida;
	private Listbox lbxItinerarios;
	private Div divItinerario;
	private Button btnSiguiente;
	private Button btnCancelar;

	private DetalleItinerario detalleItinerario = null;
	private VentaPasaje ventaFechaAbierta = null;


	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.view.ui.IBase#onCreate()
	 */
	@Override
	public void onCreate() throws Exception {
		try{
			/*Valida si el usuario tiene una liquidación aperturada*/
			if(getDesktop().getSession().getAttribute(Constantes.ATRIBUTO_FECHA_LIQUIDACION)==null)
				throw new LiquidacionNullException();

			UtilData.cargarDataCombo(cmbOrigen, Localidad.class, false);
			UtilData.cargarDataCombo(cmbDestino, Localidad.class, false);
			UtilData.cargarDataCombo(cmbOrigenItinerario, Localidad.class, false);
			UtilData.cargarDataCombo(cmbDestinoItinerario, Localidad.class, false);
			txtNumeroControl.setFocus(true);
		}catch (LiquidacionNullException lnullex){
			DlgMessage.information(Messages.getString("WndVentaReserva.information.noLiquidacion"));
			closeTabWindow();
		}

	}

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.view.ui.IBase#initComponents()
	 */
	@Override
	public void initComponents() {
		lbxVentasFechaAbierta = (Listbox)this.getFellow("lbxVentasFechaAbierta");
		incConfirmacion = (Include)this.getFellow("incConfirmacion");
		cmbOrigen = (Combobox)this.getFellow("cmbOrigen");
		cmbDestino = (Combobox)this.getFellow("cmbDestino");
		cmbOrigenItinerario = (Combobox)this.getFellow("cmbOrigenItinerario");
		cmbDestinoItinerario = (Combobox)this.getFellow("cmbDestinoItinerario");
		txtPasajero = (Textbox)this.getFellow("txtPasajero");
		txtNumeroControl = (Textbox)this.getFellow("txtNumeroControl");
		txtNumeroBoleto = (Textbox)this.getFellow("txtNumeroBoleto");
		txtNumeroDocumento = (Textbox)this.getFellow("txtNumeroDocumento");
		tabBusqueda = (Tab)this.getFellow("tabBusqueda");
		tabListado = (Tab)this.getFellow("tabListado");
		tabDetalle = (Tab)this.getFellow("tabDetalle");
		divItinerario = (Div)this.getFellow("divItinerario");
		lbxItinerarios = (Listbox)this.getFellow("lbxItinerarios");
		cldrFechaPartida = (Calendar)this.getFellow("cldrFechaPartida");
		btnSiguiente = (Button)this.getFellow("btnSiguiente");
		btnCancelar = (Button)this.getFellow("btnCancelar");

		lbxVentasFechaAbierta.addEventListener(Events.ON_DOUBLE_CLICK, this);

		btnCancelar.addEventListener(Events.ON_CLICK, new EventListener<Event>() {
			@Override
			public void onEvent(Event e){
				limpiarDatosItinerario();
				tabBusqueda.setSelected(true);
				divItinerario.setVisible(false);
			}
		});

		tabListado.addEventListener(Events.ON_SELECT, new EventListener<Event>() {
			@Override
			public void onEvent(Event e){
				limpiarDatosItinerario();
				divItinerario.setVisible(false);
			}
		});
	}

	public void buscarVentasFechaAbierta(){
		try{
			lbxVentasFechaAbierta.getItems().clear();
			Integer idOrigen = cmbOrigen.getSelectedItem().getValue()==null?null:((Localidad)cmbOrigen.getSelectedItem().getValue()).getId();
			Integer idDestino = cmbDestino.getSelectedItem().getValue()==null?null:((Localidad)cmbDestino.getSelectedItem().getValue()).getId();
			String[] pasajero = txtPasajero.getText().trim().equals("")?null:txtPasajero.getText().trim().toUpperCase().split(" ");
			String numeroControl = txtNumeroControl.getText().trim().equals("")?null:txtNumeroControl.getText().trim().toUpperCase();
			if(numeroControl!=null){
				numeroControl = Util.generateControlNumber(txtNumeroControl.getText().trim().toUpperCase());
				txtNumeroControl.setText(numeroControl);
			}
			String numeroBoleto = txtNumeroBoleto.getText().trim().equals("")?null:txtNumeroBoleto.getText().trim();
			if(numeroBoleto!=null) {
				numeroBoleto = Util.autocompleNumberBoleto(numeroBoleto);
				txtNumeroBoleto.setText(numeroBoleto);
			}

			String numeroDocumento = txtNumeroDocumento.getText().trim().equals("")?null:txtNumeroDocumento.getText().trim().toUpperCase();

			if(idOrigen==null && idDestino==null && pasajero==null && numeroControl==null && numeroBoleto==null && numeroDocumento==null){
				DlgMessage.warning(Messages.getString("WndConfirmarFechaAbierta.warning.noExistePatronBusqueda"));
				return;
			}

			List<VentaPasaje> lstReservas = ServiceLocator.getVentaPasajesManager().buscarFechaAbiertaPorConfirmar(idOrigen, idDestino, pasajero, numeroControl, numeroBoleto, numeroDocumento);
			if(lstReservas.size()>0){
				lbxVentasFechaAbierta.getItems().clear();
				Listitem item = null;
				Listcell cell = null;
				int i=0;
				for(VentaPasaje ventaPasaje : lstReservas){
					item = new Listitem();
					cell = new Listcell(ventaPasaje.getId().toString());
					item.appendChild(cell);
//					cell = new Listcell(ventaPasaje.getItinerario().getId().toString());
					cell = new Listcell(ventaPasaje.getFormaPago().getDenominacion().toString());
					item.appendChild(cell);
					cell = new Listcell(ventaPasaje.getTipoFormaPago().getDenominacion().toString());
					item.appendChild(cell);
					cell = new Listcell(ventaPasaje.getRuta().toString());
					item.appendChild(cell);
					cell = new Listcell(ventaPasaje.getPasajero().toString());
					item.appendChild(cell);
					cell = new Listcell(ventaPasaje.getCliente()==null?"":ventaPasaje.getCliente().toString());
					item.appendChild(cell);
					cell = new Listcell(ventaPasaje.getNumeroControl());
					item.appendChild(cell);
					cell = new Listcell(Util.toNumberFormat(ventaPasaje.getImportePagado(), 2));
					item.appendChild(cell);
					cell = new Listcell();
					Button btn = new Button("Confirmar", "resources/menu/menu_confirmarFechaAbierta.png");
					btn.setAutodisable("self");
					btn.setClass("btnCommandL");
					btn.addEventListener(Events.ON_CLICK, new EventListener<Event>() {
						@Override
						public void onEvent(Event e){
							lbxVentasFechaAbierta.setSelectedIndex(Integer.valueOf(e.getTarget().getId()));
							ventaFechaAbierta = (VentaPasaje)lbxVentasFechaAbierta.getSelectedItem().getValue();
							/*Valida fecha de Caducidad del Boleto*/
							if(ventaFechaAbierta.getFechaCaducidad()!=null && ventaFechaAbierta.getFechaCaducidad().getTime() < new Date().getTime()){
								DlgMessage.information(Messages.getString("WndConfirmarFechaAbierta.information.boletoCaducado"));
								return;
							}else if (ventaFechaAbierta.getFechaCaducidad()==null){
								DlgMessage.information(Messages.getString("WndConfirmarFechaAbierta.information.boletoCaducado"));
								return;
							}

							seleccionarOrigenDestino(ventaFechaAbierta);
							divItinerario.setVisible(true);
							lbxItinerarios.getItems().clear();
							cldrFechaPartida.setValue(new Date());
							lbxVentasFechaAbierta.getItems().clear();
							tabDetalle.setSelected(true);
							if(ventaFechaAbierta.getFormaPago().getId().equals(Constantes.ID_FORPAG_CORTESIA)){
								cmbOrigenItinerario.setDisabled(true);
								cmbDestinoItinerario.setDisabled(true);
							}else{
								cmbOrigenItinerario.setDisabled(false);
								cmbDestinoItinerario.setDisabled(false);
							}
						}
					});
					btn.setId(""+i);
					btn.setHeight("27px");
					btn.setStyle("font-size:11px; cursor:pointer");
					cell.appendChild(btn);
					item.appendChild(cell);
					item.setValue(ventaPasaje);
					lbxVentasFechaAbierta.appendChild(item);
					i++;
				}
				tabListado.setSelected(true);
				limpiarFiltros();
			}else
				DlgMessage.information(Messages.getString("WndConfirmarFechaAbierta.information.noReservas"));
		}catch(Exception ex){
			ex.printStackTrace();
			DlgMessage.error(this.getClass().getSimpleName()+" "+ex.getMessage());
		}
	}

	private void limpiarFiltros(){
		txtNumeroControl.setText("");
		txtNumeroBoleto.setText("");
		cmbOrigenItinerario.setSelectedIndex(0);
		cmbDestinoItinerario.setSelectedIndex(0);
		txtNumeroDocumento.setText("");
		txtPasajero.setText("");
	}

	@Override
	public void onEvent(Event e){
		if(lbxVentasFechaAbierta.getSelectedIndex()>=0){
			ventaFechaAbierta = (VentaPasaje)lbxVentasFechaAbierta.getSelectedItem().getValue();

			/*Valida fecha de Caducidad del Boleto*/
			if(ventaFechaAbierta.getFechaCaducidad()!=null && ventaFechaAbierta.getFechaCaducidad().getTime() < new Date().getTime()){
				DlgMessage.information(Messages.getString("WndConfirmarFechaAbierta.information.boletoCaducado"));
				return;
			}else if (ventaFechaAbierta.getFechaCaducidad()==null){
				DlgMessage.information(Messages.getString("WndConfirmarFechaAbierta.information.boletoCaducado"));
				return;
			}

			seleccionarOrigenDestino(ventaFechaAbierta);
			divItinerario.setVisible(true);
			lbxItinerarios.getItems().clear();
			cldrFechaPartida.setValue(new Date());
			lbxVentasFechaAbierta.getItems().clear();
			tabDetalle.setSelected(true);
		}
	}

	/**
	 * Selecciona los combos de Origen y Destino de acuerdo a la informacion que tiene la venta a Fecha Abierta.
	 * @param fechaAbierta	: Venta a fecha abierta.
	 */
	private void seleccionarOrigenDestino(VentaPasaje fechaAbierta){
		for(int i=1; i<cmbOrigenItinerario.getItemCount(); i++){
			Localidad origen = cmbOrigenItinerario.getItems().get(i).getValue();
			if(origen.getId().intValue()==fechaAbierta.getRuta().getLocalidadOrigen().getId().intValue()){
				cmbOrigenItinerario.setSelectedIndex(i);
				break;
			}
		}

		for(int i=1; i<cmbDestinoItinerario.getItemCount(); i++){
			Localidad destino = cmbDestinoItinerario.getItems().get(i).getValue();
			if(destino.getId().intValue()==fechaAbierta.getRuta().getLocalidadDestino().getId().intValue()){
				cmbDestinoItinerario.setSelectedIndex(i);
				break;
			}
		}
	}

	/**
	 * Realiza la busqueda de los itinerarios segun la fecha de partida, origen o destino.
	 */
	public void onBuscarItinerarios(){
		try{
			lbxItinerarios.getItems().clear();
			if(!(cmbOrigenItinerario.getSelectedItem().getValue() instanceof Localidad))
				throw new LocalidadNullException(LocalidadNullException.ORIGEN);

			String fechaPartida = Util.DatetoString(cldrFechaPartida.getValue(), Constantes.DATE_FORMAT);
			String origen = cmbOrigenItinerario.getText();
			String destino = "";
			if(cmbDestinoItinerario.getSelectedItem().getValue() instanceof Localidad)
				destino = cmbDestinoItinerario.getText();

			List<DetalleItinerario> lstItinerarios = ServiceLocator.getItinerarioManager().buscarItinerarios(fechaPartida, origen, destino);
			if(lstItinerarios.size()>0){
				Listitem item = null;
				Listcell cell = null;
				for(DetalleItinerario detalleItinerario : lstItinerarios){
					//NO MUESTRA LOS ITINERARIOS DE TIPO ESPECIAL.
					if(detalleItinerario.getItinerario().getTipoItinerario().getId().equals(Constantes.ID_TIPITI_REGULAR)){
						item = new Listitem();
						cell = new Listcell(detalleItinerario.getItinerario().getServicio().getDenominacion());
						item.appendChild(cell);
						cell = new Listcell(detalleItinerario.getRuta().toString());
						item.appendChild(cell);
						cell = new Listcell(detalleItinerario.getHoraPartida());
						item.appendChild(cell);

						/*
						 * 12/07/2020
						 * MAOE
						 * Codigo para recuperar las tarifas por itinerario
						 */

						List<TarifaRegular> lstTarifaRegular = ServiceLocator.getTarifaRegularManager().buscarTarifaPorServicio(1,
								detalleItinerario.getItinerario().getServicio().getId(),
								detalleItinerario.getRuta().getId(), fechaPartida,
								detalleItinerario.getHoraPartida(), null, null);
						String strTarifas="";
						if(lstTarifaRegular.size()>0){
							for(TarifaRegular tarifa: lstTarifaRegular){
								strTarifas += tarifa.getMonto().toString();
								if(lstTarifaRegular.size()>1)
									strTarifas += " / ";
							}
							if(lstTarifaRegular.size()>1)
								strTarifas = strTarifas.substring(0, strTarifas.length()-3);
						}
						else
							strTarifas="0.00";

						detalleItinerario.setTarifa(Double.valueOf(strTarifas.substring(0, 2)));

						/*
						 * 	FINAL RECUPERACION DE TARIFAS
						 */

//						cell = new Listcell(Util.toNumberFormat(detalleItinerario.getTarifa(), 2));
						cell = new Listcell(strTarifas);
						item.appendChild(cell);

						item.setValue(detalleItinerario);
						lbxItinerarios.appendChild(item);
					}
				}
			}else
				DlgMessage.information(Messages.getString("WndVentaReserva.information.noItinerariosIda"));
		}catch(LocalidadNullException lnex){
			if(lnex.getOrigenDestino().intValue()==LocalidadNullException.ORIGEN)
				DlgMessage.information(Messages.getString("WndVentaReserva.information.noOrigenSeleccionado"));
			else
				DlgMessage.information(Messages.getString("WndVentaReserva.information.noDestinoSeleccionado"));
		}catch(Exception ex){
			ex.printStackTrace();
			DlgMessage.error(this.getClass().getSimpleName()+" "+ex.getMessage());
		}
	}

	/**
	 * Para limpiar la lista de itinerarios en caso se seleccione otra fecha de partida, y realizamos la busqueda de itinerarios.
	 */
	public void onSelectCalendar(){
		limpiarDatosItinerario();
		onBuscarItinerarios();
	}

	/**
	 * Realiza la limpieza de las variables globales relacionadas con los itinerarios, mapa de asientos y listbox de itinerarios.
	 */
	private void limpiarDatosItinerario(){
		lbxItinerarios.getItems().clear();
		detalleItinerario = null;
	}

	/**
	 * Metodo utilizado cuando el usuario selecciona un itinerario.
	 */
	public void onSelectItinerario()throws Exception{
		if(lbxItinerarios.getSelectedItem().getValue() instanceof DetalleItinerario){
			detalleItinerario = lbxItinerarios.getSelectedItem().getValue();
			ventaFechaAbierta.setItinerario(detalleItinerario.getItinerario());
			ventaFechaAbierta.setRuta(detalleItinerario.getRuta());
			ventaFechaAbierta.setServicio(detalleItinerario.getItinerario().getServicio());
			ventaFechaAbierta.setFechaPartida(detalleItinerario.getFechaPartida());
			ventaFechaAbierta.setHoraPartida(detalleItinerario.getHoraPartida());
			ventaFechaAbierta.setFechaLlegada(detalleItinerario.getFechaLlegada());
			ventaFechaAbierta.setHoraLllegada(detalleItinerario.getHoraLlegada());
			ventaFechaAbierta.setDetalleItinerario(detalleItinerario);
//			ventaFechaAbierta.setTarifa(detalleItinerario.getTarifa());
			btnSiguiente.setDisabled(false);
		}
	}

	public void completarVenta(){
		try{
			if(detalleItinerario.getTarifa()==0.0)
				throw new ItinerarioException(ItinerarioException.TARIFA_IDA_CERO);

			/*Valida si la ruta esta configurada para permitir la venta antes o despues de la hora de salida ## impl 28/01/2015 - jabanto*/
			if (!(UtilData.permiteVentaByTramo(detalleItinerario.getRuta().getId(), detalleItinerario.getItinerario().getId(),Constantes.FORMAT_DATE.format(detalleItinerario.getFechaPartida())))){
				DlgMessage.information(Messages.getString("WndVentaReserva.information.ventaNoPermitida"));
				return;
			}

			divItinerario.setVisible(false);
			incConfirmacion.setSrc(null);
			incConfirmacion.setDynamicProperty("objetoConfirmar", ventaFechaAbierta);
			incConfirmacion.setSrc("confirmacion.zul");
		}catch(ItinerarioException inex){
			if(inex.getTipo().intValue()==ItinerarioException.TARIFA_IDA_CERO)
				DlgMessage.information(Messages.getString("WndVentaReserva.information.noTarifaItinerarioRetorno"));
		}catch(Exception ex){
			DlgMessage.error(this.getClass().getSimpleName()+" "+ex.getMessage());
		}
	}
}
