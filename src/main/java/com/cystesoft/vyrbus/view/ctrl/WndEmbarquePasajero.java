package com.cystesoft.vyrbus.view.ctrl;

import java.io.Serializable;
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
import org.zkoss.zul.Caption;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Comboitem;
import org.zkoss.zul.Groupbox;
import org.zkoss.zul.Label;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Longbox;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Tab;
import org.zkoss.zul.Tabbox;
import org.zkoss.zul.Tabpanel;
import org.zkoss.zul.Tabpanels;
import org.zkoss.zul.Tabs;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

import com.cystesoft.vyrbus.model.bean.DetalleEmbarquePasajero;
import com.cystesoft.vyrbus.model.bean.DetalleItinerario;
import com.cystesoft.vyrbus.model.bean.DetalleManifiesto;
import com.cystesoft.vyrbus.model.bean.EmbarquePasajero;
import com.cystesoft.vyrbus.model.bean.Itinerario;
import com.cystesoft.vyrbus.model.bean.Manifiesto;
import com.cystesoft.vyrbus.model.bean.TipoDocumento;
import com.cystesoft.vyrbus.model.bean.VentaPasaje;
import com.cystesoft.vyrbus.service.locator.ServiceLocator;
import com.cystesoft.vyrbus.service.util.Constantes;
import com.cystesoft.vyrbus.service.util.Messages;
import com.cystesoft.vyrbus.service.util.MyTime;
import com.cystesoft.vyrbus.service.util.Util;
import com.cystesoft.vyrbus.service.util.UtilData;
import com.cystesoft.vyrbus.view.ui.DlgMessage;
import com.cystesoft.vyrbus.view.ui.WndBase;
import com.cystesoft.vyrbus.view.ui.WndSeleccionaItinerario;

/**
 * 
 * @author JABANTO
 *
 */

public class WndEmbarquePasajero extends WndBase implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private Longbox lbxItinerario;
	private Button btnBuscarItinerario;
	private Label lbBus;
	private Label lbRuta;
	private Label lbSalida;
	private Label lbServicio;
	private Listbox lbxPasajerosPiso1;
	private Button btnRefress;
	private Button btnCerrarEmbarque;
	private Listbox lbxPasajerosPiso2;
	private Combobox cmbTipoDocumento;
	private Textbox txtNumeroDocumento;
	private Button btnAbrirEmbarque;
	private Button btnCerrar;
	
	private Itinerario itinerario=null;
	private DetalleItinerario detalleItinerario=null;
		
	List<VentaPasaje> listVentas=null;
	List<DetalleManifiesto> listPasajeros= new ArrayList<DetalleManifiesto>();
	
	String lbTab1Defaul="PASAJEROS";
	String lb2PTab1="PASAJEROS: PRIMER PISO";
	String lb2PTab2="PASAJEROS: SEGUNDO PISO";
	
	Tabbox tabbox= new Tabbox();
	Tabs tabs= new Tabs();
	Tab tab1= new Tab();
	Tab tab2= new Tab();
	Tabpanels tabpanels= new Tabpanels();
	Tabpanel tabpanel= new Tabpanel();
	Groupbox groupbox= new Groupbox();
	
	Label lbNumPax_l= new Label();
	Label lbCapBus_l= new Label();
	Label lbNumPax= new Label();
	Label lbCapBus= new Label();
	
	private EmbarquePasajero embarquePasajero;
	
	/*
	 * (non-Javadoc)
	 * @see com.tepsa.sisvyr.view.ui.WndBase#onCreate()
	 */
	@Override
	public void onCreate() throws Exception {		
		/*Carga los tipos de documento*/
		TreeMap<String, Object>criteriosBusqueda= new TreeMap<>();
		criteriosBusqueda.put("tipo", 0);
		criteriosBusqueda.put("estadoRegistro", Constantes.VALUE_ACTIVO);
		List<String>criteriosOrden= new ArrayList<>();
		criteriosOrden.add("denominacion");
		List<TipoDocumento>result=ServiceLocator.getTipoDocumentoManager().buscarPorX(criteriosBusqueda, criteriosOrden);
		Comboitem comboitem= new Comboitem(Constantes.COMBO_LABEL_SELECCIONE);
		cmbTipoDocumento.appendChild(comboitem);
		for(TipoDocumento tipoDocumento:result){
			if(tipoDocumento.getId().intValue()!=Constantes.ID_TIPDOC_SN){
				comboitem= new Comboitem(tipoDocumento.getDenominacion());
				comboitem.setValue(tipoDocumento);
				cmbTipoDocumento.appendChild(comboitem);
			}
		}
		cmbTipoDocumento.setSelectedIndex(0);
		cmbTipoDocumento.setDisabled(true);
		txtNumeroDocumento.setDisabled(true);
		
		enlazarItinerario(btnBuscarItinerario);
	}	
	
	/*
	 * (non-Javadoc)
	 * @see com.tepsa.sisvyr.view.ui.WndBase#initComponents()
	 */
	@Override
	public void initComponents() {
		lbxItinerario=(Longbox) this.getFellow("lbxItinerario");
		btnBuscarItinerario=(Button) this.getFellow("btnBuscarItinerario");
		lbBus=(Label) this.getFellow("lbBus");
		lbRuta=(Label) this.getFellow("lbRuta");
		lbSalida=(Label) this.getFellow("lbSalida");
		lbServicio=(Label) this.getFellow("lbServicio");
		lbxPasajerosPiso1=(Listbox) this.getFellow("lbxPasajerosPiso1");
		btnRefress=(Button)this.getFellow("btnRefress");
		btnCerrarEmbarque=(Button) this.getFellow("btnCerrarEmbarque");
		lbxPasajerosPiso2=(Listbox)this.getFellow("lbxPasajerosPiso2");
		cmbTipoDocumento=(Combobox)this.getFellow("cmbTipoDocumento");
		txtNumeroDocumento=(Textbox)this.getFellow("txtNumeroDocumento");
		btnAbrirEmbarque=(Button)this.getFellow("btnAbrirEmbarque");
		btnCerrar=(Button)this.getFellow("btnCerrar");
//		wndDespachoPasajeros=(Window)this.getFellow("wndDespachoPasajeros");
		
		cmbTipoDocumento.addEventListener(Events.ON_SELECT, new EventListener<Event>() {
			@Override
			public void onEvent(Event event) throws Exception {
				if(cmbTipoDocumento.getSelectedItem().getValue() instanceof TipoDocumento){
					txtNumeroDocumento.setText("");
					txtNumeroDocumento.setFocus(true);
				}
			}
		});
	}

	/**
	 * Evento que se ejecuta cuando el usuario lee el codigo de barras del documento
	 * @throws Exception
	 */
	public void Onchanging_txtNumeroDocumento(String value, Event event)throws Exception{		
		if(cmbTipoDocumento.getSelectedItem().getValue() instanceof TipoDocumento){
			TipoDocumento tipoDocumento=cmbTipoDocumento.getSelectedItem().getValue();
			/*DNI*/
			if(tipoDocumento.getId().intValue()==Constantes.ID_TIPDOC_DNI && value.trim().length()==8){
				boolean paxEmbarcado = agregarPax(tipoDocumento.getId(), value);
				if(paxEmbarcado==false)
					DlgMessage.warning("Pasajero no encontrado.",cmbTipoDocumento);
			}else if(tipoDocumento.getId().intValue()!=Constantes.ID_TIPDOC_DNI)
				/*OTROS TIPOS DE DOCUMENTO*/
				agregarPax(tipoDocumento.getId(), value);			
		}else{
			DlgMessage.information("Debe de seleccionar el Tipo de Documento.",cmbTipoDocumento);
		}
	}
	
	/**
	 * Agrega el pasajero a las lista de Pasajeros embarcados
	 * @param tipoDocumentoID : Identificador del tipo de documento.
	 * @param value : Numero del Documento de identidad del pasajero.
	 * @throws Exception
	 */
	private boolean agregarPax(int tipoDocumentoID, String value)throws Exception{
		
		boolean duplicidadEmbarquePax=false;
		/*Primero valida la existencia del pasajero en la lista de pasajeros embarcados*/
		for(Listitem item : lbxPasajerosPiso1.getItems()){
			VentaPasaje ventaPasaje=((DetalleEmbarquePasajero)item.getValue()).getVentaPasaje();
			if(ventaPasaje.getPasajero().getTipoDocumento().getId().intValue()==tipoDocumentoID && 
					ventaPasaje.getPasajero().getNumeroDocumento().equals(value.trim().toUpperCase())){
				duplicidadEmbarquePax=true;
				Messagebox.show("El tipo y número de documento ingresado, corresponden a un Pasajero ya embarcado.", DlgMessage.NOMBREAPLICACION, DlgMessage.BTN_OK, Messagebox.ERROR,new EventListener<Event>() {
					public void onEvent(Event e) throws Exception{
						txtNumeroDocumento.setText("");
						txtNumeroDocumento.setFocus(true);
					}
				});
				break;
			}
		}
		
		boolean pasajeroEmbarcado=false;
		/*Busca al pasajero y lo agrega a la lista de pasajeros embarcados*/
		if(duplicidadEmbarquePax==false){
			for(VentaPasaje ventaPasaje : listVentas){
				if(ventaPasaje.getPasajero().getTipoDocumento().getId().intValue()==tipoDocumentoID &&
						ventaPasaje.getPasajero().getNumeroDocumento().equals(value.trim().toUpperCase())){
					pasajeroEmbarcado=true;
					
					Listitem item= new Listitem();
					Listcell cell= new Listcell(ventaPasaje.getNumeroAsiento().toString().length()==1?"0"+ventaPasaje.getNumeroAsiento().toString():ventaPasaje.getNumeroAsiento().toString());
					cell.setStyle("font-size:11px !important");
					item.appendChild(cell);
					cell= new Listcell(ventaPasaje.getNumeroBoleto());
					cell.setStyle("font-size:11px !important");
					item.appendChild(cell);
					cell= new Listcell(ventaPasaje.getPasajero().toString());
					item.appendChild(cell);
					/*Busca el nombre del tipo de documento del pasajero*/
					TipoDocumento tipoDocumento=null;
					for(Comboitem comboitem: cmbTipoDocumento.getItems()){
						if(comboitem.getValue() instanceof TipoDocumento){
							if(((TipoDocumento)comboitem.getValue()).getId().intValue()==ventaPasaje.getPasajero().getTipoDocumento().getId().intValue()){
								tipoDocumento=comboitem.getValue();
								break;
							}
						}
					}
					cell= new Listcell(tipoDocumento!=null?tipoDocumento.getDenominacion():"");
					cell.setStyle("font-size:11px !important");
					item.appendChild(cell);
					cell= new Listcell(ventaPasaje.getPasajero().getNumeroDocumento());
					cell.setStyle("font-size:11px !important");
					item.appendChild(cell);
					cell= new Listcell(ventaPasaje.getRuta().toString());
					item.appendChild(cell);
					
					DetalleEmbarquePasajero detalleEmbarquePasajero= new DetalleEmbarquePasajero();
					detalleEmbarquePasajero.setVentaPasaje(ventaPasaje);

					item.setValue(detalleEmbarquePasajero);
					lbxPasajerosPiso1.appendChild(item);
					
					txtNumeroDocumento.setText(value);//Es necesario para que el control se pueda limpiar
					txtNumeroDocumento.setText("");
				}
			}
		}
		
		return pasajeroEmbarcado;
	}
	
	
	/**
	 * Permite enlazar los controles a la ventana de selección de Itinerario
	 * @param button :ha este Button se le adjuntara un listener con la llamada a la ventana de selección de itinerario
	 * @see WndDespachoPasajeros: 
	 */
	public  void enlazarItinerario(final Button button) {
		button.setTooltiptext("Seleccionar Itinerario");
		button.addEventListener(Events.ON_CLICK, new EventListener<Event>() {
			@Override
			public void onEvent(Event event) throws Exception {
				final WndSeleccionaItinerario seleccionaItinerario = new WndSeleccionaItinerario();
				boolean buscarVentanaParent = true;
				Component oComponent = button.getParent();
				while(buscarVentanaParent){
					 if(oComponent instanceof Window) {
						 oComponent.appendChild(seleccionaItinerario);
						 buscarVentanaParent = false;
					 }else{
					 	oComponent = oComponent.getParent();
					 }
				}
				seleccionaItinerario.onCreate();
				seleccionaItinerario.setMode("modal");
				seleccionaItinerario.setVisible(true);
				seleccionaItinerario.addEventListener(Events.ON_SELECT, new EventListener<Event>() {
					@Override
					public void onEvent(Event event) throws Exception {
						Util.limpiarListbox(lbxPasajerosPiso1);
						Util.limpiarListbox(lbxPasajerosPiso2);
						itinerario=null;
						detalleItinerario=null;
						embarquePasajero=null;
						itinerario=seleccionaItinerario.getItinerario();
						detalleItinerario=seleccionaItinerario.getDetalleItinerario();
						lbxItinerario.setValue(itinerario.getId());
						generaTabs_Bus2Pisos(itinerario.getServicio().getNumeroPisos());
						onRefress();
					}
				});
			}
		});
	}

	/**
	 * Refresca informacion del itinerario ya pax.
	 * @throws Exception
	 */
	public void onRefress() throws Exception{
		listVentas=new ArrayList<>();		
		limpiaCabecera();
//		Util.disabledBtnAceptar(true, btnCerrarEmbarque, true);
//		Util.disabledBtnRefresh(true, btnRefress, true);
				
		if(itinerario!=null && detalleItinerario!=null){
			//Busca manifiesto
			TreeMap<String, Object>criteriosBusqueda=new TreeMap<String, Object>();
			criteriosBusqueda.put("itinerario", itinerario);
			criteriosBusqueda.put("estadoRegistro", Constantes.VALUE_ACTIVO);
			List<Manifiesto> listManifiesto=ServiceLocator.getManifiestoManager().buscarPorX(criteriosBusqueda, null);
			if(listManifiesto.size()>0){
				if(listManifiesto.size()==1){
					lbBus.setValue(itinerario.getBus()!=null?itinerario.getBus().getCodigo():"--");
					lbRuta.setValue(itinerario.getRuta().toString());
					lbServicio.setValue(itinerario.getServicio().getDenominacion());
					lbSalida.setValue(Constantes.FORMAT_DATE.format(itinerario.getFechaPartida())+" "+itinerario.getHoraPartida());
					listVentas= ServiceLocator.getVentaPasajesManager().buscarVentasForMapaBus(itinerario.getId());
					
					Util.limpiarListbox(lbxPasajerosPiso1);
					
					/*Busca un embarque abierto de la agencia para el servicio seleccionado*/
					criteriosBusqueda= new TreeMap<>();
					criteriosBusqueda.put("itinerario", itinerario);
					criteriosBusqueda.put("agencia", getAgencia());
					criteriosBusqueda.put("estadoRegistro", Constantes.VALUE_ACTIVO);
					List<EmbarquePasajero> resultEmb=ServiceLocator.getEmbarquePasajeroManager().buscarPorX(criteriosBusqueda, null);
					for(EmbarquePasajero embarquePasajero: resultEmb){						
						/*Buscando los pasajeros embarcados*/						
						criteriosBusqueda= new TreeMap<>();
						criteriosBusqueda.put("embarquePasajero", embarquePasajero);
						criteriosBusqueda.put("estadoRegistro", Constantes.VALUE_ACTIVO);
						List<String> criteriosOrdenar= new ArrayList<>();
						criteriosOrdenar.add("id");
						List<DetalleEmbarquePasajero>resultDetemb=ServiceLocator.getDetalleEmbarquePasajeroManager().buscarPorX(criteriosBusqueda, criteriosOrdenar);
						
						for(DetalleEmbarquePasajero detalleEmbarquePasajero:resultDetemb){
							VentaPasaje ventaPasaje=detalleEmbarquePasajero.getVentaPasaje();
							Listitem item= new Listitem();
							Listcell cell= new Listcell(ventaPasaje.getNumeroAsiento().toString().length()==1?"0"+ventaPasaje.getNumeroAsiento().toString():ventaPasaje.getNumeroAsiento().toString());
							cell.setStyle("font-size:11px !important");
							item.appendChild(cell);
							cell= new Listcell(ventaPasaje.getNumeroBoleto());
							cell.setStyle("font-size:11px !important");
							item.appendChild(cell);
							cell= new Listcell(ventaPasaje.getPasajero().toString());
							item.appendChild(cell);							
							cell= new Listcell(ventaPasaje.getPasajero().getTipoDocumento().getDenominacion());
							cell.setStyle("font-size:11px !important");
							item.appendChild(cell);
							cell= new Listcell(ventaPasaje.getPasajero().getNumeroDocumento());
							cell.setStyle("font-size:11px !important");
							item.appendChild(cell);
							cell= new Listcell(ventaPasaje.getRuta().toString());
							item.appendChild(cell);
							
							item.setValue(detalleEmbarquePasajero);
							
							lbxPasajerosPiso1.appendChild(item);
						}
						
						/*Validando el estado del embarque*/
						if(embarquePasajero.getEstadoEmbarque().intValue()==Constantes.TRUE_VALUE)
							this.embarquePasajero=embarquePasajero;
					}
					
					if(resultEmb.size()==0)
						btnAbrirEmbarque.setVisible(true);
					else{
						/*Validando el estado del embarque*/
						if(embarquePasajero!=null && embarquePasajero.getEstadoEmbarque().intValue()==Constantes.TRUE_VALUE){
							Util.seleccionarValorItemCombo(TipoDocumento.class, cmbTipoDocumento, Constantes.ID_TIPDOC_DNI);
							cmbTipoDocumento.setDisabled(false);
							txtNumeroDocumento.setDisabled(false);
							txtNumeroDocumento.setFocus(true);
							
							btnAbrirEmbarque.setVisible(false);
							btnCerrarEmbarque.setVisible(true);	
							txtNumeroDocumento.setFocus(true);
						}else
							btnAbrirEmbarque.setVisible(true);
					}
					
//					if(resultEmb.size()>0){
//						embarquePasajero=resultEmb.get(0);
//						
//						/*Buscando los pasajeros embarcados*/						
//						criteriosBusqueda= new TreeMap<>();
//						criteriosBusqueda.put("embarquePasajero", embarquePasajero);
//						criteriosBusqueda.put("estadoRegistro", Constantes.VALUE_ACTIVO);
//						List<String> criteriosOrdenar= new ArrayList<>();
//						criteriosOrdenar.add("id");
//						List<DetalleEmbarquePasajero>resultDetemb=ServiceLocator.getDetalleEmbarquePasajeroManager().buscarPorX(criteriosBusqueda, criteriosOrdenar);
//						
//						for(DetalleEmbarquePasajero detalleEmbarquePasajero:resultDetemb){
//							VentaPasaje ventaPasaje=detalleEmbarquePasajero.getVentaPasaje();
//							Listitem item= new Listitem();
//							Listcell cell= new Listcell(ventaPasaje.getNumeroAsiento().toString().length()==1?"0"+ventaPasaje.getNumeroAsiento().toString():ventaPasaje.getNumeroAsiento().toString());
//							cell.setStyle("font-size:11px !important");
//							item.appendChild(cell);
//							cell= new Listcell(ventaPasaje.getNumeroBoleto());
//							cell.setStyle("font-size:11px !important");
//							item.appendChild(cell);
//							cell= new Listcell(ventaPasaje.getPasajero().toString());
//							item.appendChild(cell);							
//							cell= new Listcell(ventaPasaje.getPasajero().getTipoDocumento().getDenominacion());
//							cell.setStyle("font-size:11px !important");
//							item.appendChild(cell);
//							cell= new Listcell(ventaPasaje.getPasajero().getNumeroDocumento());
//							cell.setStyle("font-size:11px !important");
//							item.appendChild(cell);
//							cell= new Listcell(ventaPasaje.getRuta().toString());
//							item.appendChild(cell);
//							
//							item.setValue(detalleEmbarquePasajero);
//							
//							lbxPasajerosPiso1.appendChild(item);
//						}
//						
//						/*Validando el estado del embarque*/
//						if(embarquePasajero.getEstadoEmbarque().intValue()==Constantes.TRUE_VALUE){
//							Util.seleccionarValorItemCombo(TipoDocumento.class, cmbTipoDocumento, Constantes.ID_TIPDOC_DNI);
//							cmbTipoDocumento.setDisabled(false);
//							txtNumeroDocumento.setDisabled(false);
//							txtNumeroDocumento.setFocus(true);
//							
//							btnAbrirEmbarque.setVisible(false);
//							btnCerrarEmbarque.setVisible(true);	
//							txtNumeroDocumento.setFocus(true);
//						}else
//							btnAbrirEmbarque.setVisible(true);
//					}else
//						btnAbrirEmbarque.setVisible(true);
				}else{
					DlgMessage.information(Messages.getString("wndDespachoPasajerosUpdateDocPax.information.ItineDobleManifiesto"));
				}
			}else{
				DlgMessage.information(Messages.getString("wndDespachoPasajerosUpdateDocPax.information.noManifiesto"));
			}
			
			btnRefress.setImage("resources/mp_refrescarEnabled.png");
			btnRefress.setStyle("cursor:pointer");
			
			btnRefress.setDisabled(false);
		}
	}
	
	/**
	 * Abre el embarque de pasajeros
	 */
	public void abrirEmbarque(){
		try {
			String dateServer=MyTime.dateTimeServer();
			Date dateApertura= Constantes.FORMAT_DATE.parse(dateServer);
			String horaApertura=dateServer.split(" ")[1];
			horaApertura=horaApertura.substring(0, horaApertura.length()-3).trim();
			
			embarquePasajero= new EmbarquePasajero();
			embarquePasajero.setItinerario(itinerario);
			embarquePasajero.setAgencia(getAgencia());
			embarquePasajero.setFechaApertura(dateApertura);
			embarquePasajero.setHoraApertura(horaApertura);
			embarquePasajero.setEstadoEmbarque(Constantes.TRUE_VALUE);
			embarquePasajero.setEstadoRegistro(Constantes.VALUE_ACTIVO);
			UtilData.auditarRegistro(embarquePasajero, getUsuario(), Executions.getCurrent());
			
			Messagebox.show(Messages.getString("wndDespachoPasajeros.question.apertura"), DlgMessage.NOMBREAPLICACION, DlgMessage.BTN_YESNO, Messagebox.QUESTION,DlgMessage.BTN_DEFAULT_NO, new EventListener<Event>() {
				@Override
				public void onEvent(Event e) throws Exception {
					if(e.getName().equals("onYes")){
						try {
							
							ServiceLocator.getEmbarquePasajeroManager().guardar(embarquePasajero);
							btnAbrirEmbarque.setVisible(false);
							btnCerrarEmbarque.setVisible(true);
							Util.seleccionarValorItemCombo(TipoDocumento.class, cmbTipoDocumento, Constantes.ID_TIPDOC_DNI);
							cmbTipoDocumento.setDisabled(false);
							txtNumeroDocumento.setText("");
							txtNumeroDocumento.setDisabled(false);
							txtNumeroDocumento.setFocus(true);
							btnCerrar.setVisible(false);
						} catch (Exception e2) {
							e2.printStackTrace();
							DlgMessage.error(e2.getMessage());
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
	 * Realiza el cierre de embarque
	 */
	public void cerraDespacho(){
		try {			
			Messagebox.show(Messages.getString("wndDespachoPasajeros.question.cerrarDespacho"), DlgMessage.NOMBREAPLICACION, DlgMessage.BTN_YESNO, Messagebox.QUESTION,DlgMessage.BTN_DEFAULT_NO, new EventListener<Event>() {
				@Override
				public void onEvent(Event e) throws Exception {
					if(e.getName().equals("onYes")){
						try {
							/*Inserta los pasajeros embarcados*/
							for(Listitem item:lbxPasajerosPiso1.getItems()){
								DetalleEmbarquePasajero detalleEmbarque=item.getValue();
								if(detalleEmbarque.getId()==null){
									detalleEmbarque.setEmbarquePasajero(embarquePasajero);
									detalleEmbarque.setEstadoRegistro(Constantes.VALUE_ACTIVO);
									UtilData.auditarRegistro(detalleEmbarque, getUsuario(), Executions.getCurrent());
									ServiceLocator.getDetalleEmbarquePasajeroManager().guardar(detalleEmbarque);
								}
							}
							/*Recupera la fecha y hora del servidor.*/
							String dateServer=MyTime.dateTimeServer();
							Date dateCierre= Constantes.FORMAT_DATE.parse(dateServer);
							String horaCierre=dateServer.split(" ")[1];
							horaCierre=horaCierre.substring(0, horaCierre.length()-3).trim();
							/*Realiza el cierre del embarque*/
							embarquePasajero.setFechaCierre(dateCierre);
							embarquePasajero.setHoraCierre(horaCierre);
							embarquePasajero.setEstadoEmbarque(Constantes.FALSE_VALUE);
							embarquePasajero.setCantidadPasajerosEmbarcados(lbxPasajerosPiso1.getItems().size());
							UtilData.auditarRegistro(embarquePasajero, true, getUsuario(), Executions.getCurrent());
							ServiceLocator.getEmbarquePasajeroManager().actualizar(embarquePasajero);
							
							
							cmbTipoDocumento.setDisabled(true);
							txtNumeroDocumento.setDisabled(true);
							btnCerrarEmbarque.setVisible(false);
							btnCerrar.setVisible(true);
							
							DlgMessage.information("Se realizó correctamente");
						} catch (Exception e2) {
							e2.printStackTrace();
							
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
	 * Limpia controles del cavecera
	 */
	private void limpiaCabecera(){
		lbRuta.setValue("");
		lbBus.setValue("");
		lbServicio.setValue("");
		lbSalida.setValue("");
	}
	
	/**
	 * 
	 * @param pisos : Números de pisos del bus.
	 */
	private void generaTabs_Bus2Pisos(Integer pisos){
		tabbox.detach();
		tab1.detach();
		tab2.detach();
		groupbox.detach();
		if(pisos==2){
			tabbox= new Tabbox();
			tabpanels= new Tabpanels();
				
			tab1=new Tab();
			tab1.setLabel(lb2PTab1);
				
			tab2=new Tab();
			tab2.setLabel(lb2PTab2);
				
			tabs.appendChild(tab1);
			tabs.appendChild(tab2);
			tabbox.appendChild(tabs);
				
			tabpanel=new Tabpanel();
			tabpanel.appendChild(lbxPasajerosPiso1);
			tabpanels.appendChild(tabpanel);
				
			lbxPasajerosPiso2.setVisible(true);
			tabpanel = new Tabpanel();
			tabpanel.appendChild(lbxPasajerosPiso2);
				
			tabpanels.appendChild(tabpanel);
			tabbox.appendChild(tabpanels);
			this.appendChild(tabbox);
		}else{
			
			Caption caption=new Caption();
			caption.setLabel("Lista de Pasajeros");
			lbxPasajerosPiso2.setVisible(false);
			this.appendChild(lbxPasajerosPiso1);
			tab1.setVisible(false);
			groupbox=new Groupbox();
			groupbox.appendChild(caption);
			groupbox.appendChild(lbxPasajerosPiso1);
			this.appendChild(groupbox);
		}
	}
	
}
