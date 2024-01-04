package com.cystesoft.vyrbus.view.ctrl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.springframework.dao.DataIntegrityViolationException;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zul.A;
import org.zkoss.zul.Button;
import org.zkoss.zul.Caption;
import org.zkoss.zul.Checkbox;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Comboitem;
import org.zkoss.zul.Div;
import org.zkoss.zul.Grid;
import org.zkoss.zul.Groupbox;
import org.zkoss.zul.Image;
import org.zkoss.zul.Label;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listfoot;
import org.zkoss.zul.Listfooter;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Longbox;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Row;
import org.zkoss.zul.Rows;
import org.zkoss.zul.Separator;
import org.zkoss.zul.Tab;
import org.zkoss.zul.Tabbox;
import org.zkoss.zul.Tabpanel;
import org.zkoss.zul.Tabpanels;
import org.zkoss.zul.Tabs;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

import com.cystesoft.vyrbus.model.bean.Agencia;
import com.cystesoft.vyrbus.model.bean.DetalleEmbarquePasajero;
import com.cystesoft.vyrbus.model.bean.DetalleItinerario;
import com.cystesoft.vyrbus.model.bean.DetalleManifiesto;
import com.cystesoft.vyrbus.model.bean.EmbarquePasajero;
import com.cystesoft.vyrbus.model.bean.Itinerario;
import com.cystesoft.vyrbus.model.bean.Localidad;
import com.cystesoft.vyrbus.model.bean.MapaBus;
import com.cystesoft.vyrbus.model.bean.Pasajero;
import com.cystesoft.vyrbus.model.bean.Servicio;
import com.cystesoft.vyrbus.model.bean.TipoDocumento;
import com.cystesoft.vyrbus.model.bean.TipoItinerario;
import com.cystesoft.vyrbus.model.bean.TmpOcupacionAsientos;
import com.cystesoft.vyrbus.model.bean.VentaPasaje;
import com.cystesoft.vyrbus.service.locator.ServiceLocator;
import com.cystesoft.vyrbus.service.mappers.Asiento;
import com.cystesoft.vyrbus.service.mappers.Cafeteria;
import com.cystesoft.vyrbus.service.mappers.Coordenada;
import com.cystesoft.vyrbus.service.mappers.Monitor;
import com.cystesoft.vyrbus.service.mappers.SecuenciaTramo;
import com.cystesoft.vyrbus.service.mappers.ServiciosHigienicos;
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
	private Button btnRefress;
	private Label lbBus;
	private Label lbRuta;
	private Label lbSalida;
	private Label lbServicio;
	private Button btnCerrarEmbarque;
	private Textbox txtNumeroDocumento;
	private Button btnAbrirEmbarque;
	private Button btnCerrar;
	private Checkbox chkManual;
	private Groupbox grpbxMapaIda;
	private Label lblPasajero;
	private Label lblTipoDocumentoPasajero;
	private Label lblNumeroDocumentoPasajero;
	private Label lblEdadLabel;
	private Label lblEdad;
	private Label lblAsientoPasajeroLabel;
	private Label lblAsientoPasajero;
	private Label lblRutaPasajero;
//	private Label lblEmbarcados;
//	private Label lblPorEmbarcar;
//	private Label lblTotalPasajeros;
	private Combobox cmbPuntoEmbarque;
	private Listbox ltbxPasajerosEmbarque;
//	private Listfoot ltfoPasajerosEmbarque;
	private Combobox cmbDetalleItinerario;
	
	
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

	private Map<String, Asiento> mapaAsientosEmbarque;
	private static final String IMAGE_PRIMER_PISO = "resources/mapa/bus_primerPiso.png";
	private static final String IMAGE_SEGUNDO_PISO = "resources/mapa/bus_segundoPiso.png";
	private static final int TIPO_ASIENTO = 0;
	private static final int TIPO_MONITOR = 1;
	private static final int TIPO_CAFETERIA = 2;
		
	private String prefijoAsiento="";
	private String key = "-1";
	
	/*
	 * (non-Javadoc)
	 * @see com.tepsa.sisvyr.view.ui.WndBase#onCreate()
	 */
	@Override
	public void onCreate() throws Exception {		
		txtNumeroDocumento.setDisabled(true);		
		
		loadDetalleItinerario();
		enlazarItinerario(btnBuscarItinerario);
//		grpbxMapaIda.setHeight("100px");
	}	
	
	/*
	 * (non-Javadoc)
	 * @see com.tepsa.sisvyr.view.ui.WndBase#initComponents()
	 */
	@Override
	public void initComponents() {
		lbxItinerario=(Longbox) this.getFellow("lbxItinerario");
		btnBuscarItinerario=(Button) this.getFellow("btnBuscarItinerario");
		btnRefress=(Button)this.getFellow("btnRefress");
		lbBus=(Label) this.getFellow("lbBus");
		lbRuta=(Label) this.getFellow("lbRuta");
		lbSalida=(Label) this.getFellow("lbSalida");
		lbServicio=(Label) this.getFellow("lbServicio");
		btnCerrarEmbarque=(Button) this.getFellow("btnCerrarEmbarque");
		txtNumeroDocumento=(Textbox)this.getFellow("txtNumeroDocumento");
		btnAbrirEmbarque=(Button)this.getFellow("btnAbrirEmbarque");
		btnCerrar=(Button)this.getFellow("btnCerrar");
		chkManual=(Checkbox)this.getFellow("chkManual");
		grpbxMapaIda=(Groupbox)this.getFellow("grpbxMapaIda");
		lblPasajero=(Label)this.getFellow("lblPasajero");
		lblTipoDocumentoPasajero=(Label)this.getFellow("lblTipoDocumentoPasajero");
		lblNumeroDocumentoPasajero=(Label)this.getFellow("lblNumeroDocumentoPasajero");
		lblEdadLabel=(Label)this.getFellow("lblEdadLabel");
		lblEdad=(Label)this.getFellow("lblEdad");
		lblAsientoPasajeroLabel=(Label)this.getFellow("lblAsientoPasajeroLabel");		
		lblAsientoPasajero=(Label)this.getFellow("lblAsientoPasajero");
		lblRutaPasajero=(Label)this.getFellow("lblRutaPasajero");
//		lblEmbarcados=(Label)this.getFellow("lblEmbarcados");
//		lblPorEmbarcar=(Label)this.getFellow("lblPorEmbarcar");
//		lblTotalPasajeros=(Label)this.getFellow("lblTotalPasajeros");
		cmbPuntoEmbarque=(Combobox)this.getFellow("cmbPuntoEmbarque");
		ltbxPasajerosEmbarque=(Listbox)this.getFellow("ltbxPasajerosEmbarque");
//		ltfoPasajerosEmbarque=(Listfoot)this.getFellow("ltfoPasajerosEmbarque");
		cmbDetalleItinerario=(Combobox)this.getFellow("cmbDetalleItinerario");
		
		
		btnRefress.addEventListener(Events.ON_CLICK, new EventListener<Event>() {
			@Override
			public void onEvent(Event arg0) throws Exception {
				try {
					if(detalleItinerario!=null){
						crearEstructura(detalleItinerario.getItinerario().getServicio().getId(), grpbxMapaIda, false, detalleItinerario, mapaAsientosEmbarque);
						onRefress();
					}
				} catch (Exception e) {
					e.printStackTrace();
					DlgMessage.error(e.getMessage());
				}				
			}
		});
		
		cmbPuntoEmbarque.addEventListener(Events.ON_CHANGE, new EventListener<Event>() {
			@Override
			public void onEvent(Event arg0) throws Exception {
				try {
					if(detalleItinerario!=null){
						crearEstructura(detalleItinerario.getItinerario().getServicio().getId(), grpbxMapaIda, false, detalleItinerario, mapaAsientosEmbarque);
						onRefress();
					}
				} catch (Exception e) {
					e.printStackTrace();
					DlgMessage.error(e.getMessage());
				}
			}
		});
		
		cmbDetalleItinerario.addEventListener(Events.ON_CHANGE, new EventListener<Event>() {
			@Override
			public void onEvent(Event arg0) throws Exception {
				try {
					onSelectedDetalleItinerario();
				} catch (Exception e) {
					e.printStackTrace();
					DlgMessage.error(e.getMessage());
				}
			}
		});
	}	
	
	/**
	 * Evento que se ejecuta cuando el usuario lee el codigo de barras del documento
	 * @throws Exception
	 */
	public void OnOk_txtNumeroDocumento(String value, Event event)throws Exception{
		Onchanging_txtNumeroDocumento(value, event);
	}
	
	
	private void cliarDatosPasajero(){
		lblPasajero.setValue("");
		lblTipoDocumentoPasajero.setValue("");
		lblEdad.setValue("");
		lblEdadLabel.setValue("");
		lblNumeroDocumentoPasajero.setValue("");
		lblAsientoPasajeroLabel.setValue("");
		lblAsientoPasajero.setValue("");
		lblRutaPasajero.setValue("");
	}
	/**
	 * Evento que se ejecuta cuando el usuario lee el codigo de barras del documento
	 * @throws Exception
	 */
	public void Onchanging_txtNumeroDocumento(String value, Event event)throws Exception{
		cliarDatosPasajero();
		if(!(value.trim().isEmpty()) && chkManual.isChecked()==false ){
			//Valida si es una trama escaneado del codigo Qr
			String serie = "", numero = "";
			if(value.split("\\|").length>3) {
				String[] tramaQr = value.split("\\|");
				serie = tramaQr[2];
				numero = tramaQr[3];
			}else if(value.split("\\-").length==2) {
				String[] tramaManual = value.split("\\-");
				serie = tramaManual[0];
				numero = tramaManual[1];
			}else
				return;			
			
			if(!(Util.isNumeric(serie))){				
				String numeroComprobante=Util.autocompleNumberBoleto(serie+"-"+numero);
				
				//Busca la venta
				VentaPasaje oVentaPasaje=null;
				for(VentaPasaje ventaPasaje : listVentas){					
					if(ventaPasaje.getNumeroBoleto()!=null && ventaPasaje.getNumeroBoleto().equals(numeroComprobante)){
						oVentaPasaje=ventaPasaje;
						break;
					}
				}				
				
				//Si es que no encuentra al pasajero actualiza el map, por si haya comprado luego de que se cargo el mapa en el embarque
				if(oVentaPasaje==null) {
					onRefreshMap(); //Refresaca el mapa
					////
					for(VentaPasaje ventaPasaje : listVentas){					
						if(ventaPasaje.getNumeroBoleto()!=null && ventaPasaje.getNumeroBoleto().equals(numeroComprobante)){
							oVentaPasaje=ventaPasaje;
							break;
						}
					}
				}
				
				if(oVentaPasaje!=null){
					Asiento asiento_key= new Asiento();
					asiento_key.setNumeroAsiento(oVentaPasaje.getNumeroAsiento());
					asiento_key.setPiso(oVentaPasaje.getNumeroPiso());
					asiento_key.setKey();
															
					//Busca el objeto asiento en el mapa
					Asiento asiento=null;
					for(Component _grid: grpbxMapaIda.getChildren()) {
						Grid grid= (Grid)_grid;
						for(Component _component: grid.getChildren()) {
							Rows rows=(Rows)_component;
							for(Component component : rows.getChildren()){
								if(component instanceof Row){
									Row row=(Row)component;
									for(Component component2 : row.getChildren()){
										if(component2 instanceof Div){
											Div div=(Div) component2;
											if(div.getFirstChild() instanceof Asiento){
												Asiento oasiento=(Asiento) div.getFirstChild();										
												if(oasiento.getKey().equals(asiento_key.getKey())){
													asiento=oasiento;
													break;									
												}	
											}									
										}								
									}																							
								}
							}	
						}
					}
					
					
					
					asiento.setVentaPasaje(oVentaPasaje);
					boolean isCorrect = onClickAsiento(asiento, oVentaPasaje, mapaAsientosEmbarque, false);						
					if(isCorrect){
						//Registra el embarque
						DetalleEmbarquePasajero detalleEmbarquePasajero= new DetalleEmbarquePasajero();										
						detalleEmbarquePasajero.setEmbarquePasajero(embarquePasajero);		
						detalleEmbarquePasajero.setVentaPasaje(oVentaPasaje);
						if(event!=null && event.getName().equals("onClick"))
							detalleEmbarquePasajero.setModalidadEmbarque(Constantes.TRUE_VALUE);
						detalleEmbarquePasajero.setEstadoRegistro(Constantes.VALUE_ACTIVO);
						UtilData.auditarRegistro(detalleEmbarquePasajero, getUsuario(), Executions.getCurrent());
						ServiceLocator.getDetalleEmbarquePasajeroManager().guardar(detalleEmbarquePasajero);
						asiento.getVentaPasaje().setDetalleEmbarquePasajero(detalleEmbarquePasajero);
						
						onloadDatosPasajero(oVentaPasaje);
//						onloadEstadisticas();
						
						crearEstructura(detalleItinerario.getItinerario().getServicio().getId(), grpbxMapaIda, false, detalleItinerario, mapaAsientosEmbarque);
						onRefress();
					}else
						onloadDatosPasajero(oVentaPasaje);
				}else{
					DlgMessage.information("El Pasajero no está registrado en este servicio.",txtNumeroDocumento);
				}								
				txtNumeroDocumento.setText("sss");
				txtNumeroDocumento.setText("");

//				agregarPax(numeroComprobante);				
			}else{
				DlgMessage.warning("El Número de comprobante no es válido",txtNumeroDocumento);
				txtNumeroDocumento.setText("");
			}
		}		
	}
	
	private void onloadDatosPasajero(VentaPasaje ventaPasaje)throws Exception{
		lblPasajero.setValue(ventaPasaje.getPasajero().toString());
		lblTipoDocumentoPasajero.setValue(ventaPasaje.getPasajero().getTipoDocumento().getDenominacion()+ " N° ");
		lblNumeroDocumentoPasajero.setValue(ventaPasaje.getPasajero().getNumeroDocumento());
		lblEdadLabel.setValue("EDAD ");
		lblEdad.setValue(Util.calculaEdad(ventaPasaje.getPasajero().getFechaNacimiento()).toString());
		lblAsientoPasajeroLabel.setValue("ASIENTO N° ");
		lblAsientoPasajero.setValue(ventaPasaje.getNumeroAsiento().toString());
		lblRutaPasajero.setValue(ventaPasaje.getRuta().toString());
	}
	
	
	private void loadDetalleItinerario()throws Exception{
		Localidad localidadOrigen = ServiceLocator.getLocalidadManager().buscarPorId(getAgencia().getLocalidad().getId().longValue()); 
		String fechaInicio =  Constantes.FORMAT_DATE.format(new Date());		
		
		String criterioOrden="di.d_fecpar, to_date(di.c_horpar,'HH24:MI'), di.d_feclle, to_date(di.c_horlle,'HH24:MI')";

		List<DetalleItinerario> listResult = 
				ServiceLocator.getItinerarioManager().buscarItinerariosMantenimiento(null, localidadOrigen.getDenominacion(),null, fechaInicio, fechaInicio, null,null, criterioOrden);

		
		UtilData.cargarGenericData(cmbDetalleItinerario, false);
		for(DetalleItinerario _detalleItinerario: listResult) {
			//Valida que el itinerario ya tenga flota asignada
			if(_detalleItinerario.getItinerario().getBus()!=null) {
				
				//valida que que va realizar el embarque sea igual a la del punto de enbarque del itinerario
				if(_detalleItinerario.getAgenciaPartida().getId().intValue()==getAgencia().getId().intValue()) {
					Itinerario _itinerario = ServiceLocator.getItinerarioManager().buscarPorId(_detalleItinerario.getItinerario().getId());					
					_itinerario.setBus(_detalleItinerario.getItinerario().getBus());
					_itinerario.setServicio(_detalleItinerario.getItinerario().getServicio());
					_detalleItinerario.setItinerario(_itinerario);
					String labelComboItem = _detalleItinerario.getItinerario().getId().toString();
				 	labelComboItem += " | HORA "+ _detalleItinerario.getHoraPartida();
					labelComboItem += " | "+ _detalleItinerario.getRuta().getOrigen()+" - "+_detalleItinerario.getItinerario().getRuta().getDestino();
					labelComboItem += " | "+ _detalleItinerario.getItinerario().getServicio().getDenominacion() ;
					
					//Para que se repita el itinerario
					boolean addItem = true;
					for(Comboitem _comboitem: cmbDetalleItinerario.getItems()) {
						if(_comboitem.getValue()!= null) {
							if(((DetalleItinerario)_comboitem.getValue()).getItinerario().getId().longValue() == _detalleItinerario.getItinerario().getId().longValue()) {
								addItem = false;
								break;
							}							
						}
					}
					
					if(addItem) {
						Comboitem comboitem=new Comboitem(labelComboItem);
						comboitem.setValue(_detalleItinerario);
						cmbDetalleItinerario.appendChild(comboitem);	
					}
				}
			}			
		}
		
		cmbDetalleItinerario.setSelectedIndex(0);
		
	}
	
	/**
	 * Carga los servicios 
	 * @throws Exception
	 */
	private void onSelectedDetalleItinerario()throws Exception{
		if(cmbDetalleItinerario.getSelectedItem().getValue() instanceof DetalleItinerario) {
			DetalleItinerario odetalleItinerario = ((DetalleItinerario)cmbDetalleItinerario.getSelectedItem().getValue());
			cliarDatosPasajero();
			cmbPuntoEmbarque.setDisabled(false);
			btnAbrirEmbarque.setDisabled(true);
			itinerario=null;
			detalleItinerario=null;
			embarquePasajero=null;						
			itinerario=odetalleItinerario.getItinerario();
			detalleItinerario=odetalleItinerario;
			lbxItinerario.setValue(itinerario.getId());						
			//Carga los pintos de embarque
			ArrayList<Agencia> lsta= (ArrayList<Agencia>) ServiceLocator.getManifiestoManager().consultaPtoControl(itinerario.getId());
			cmbPuntoEmbarque.getItems().clear();
			UtilData.cargarGenericData(cmbPuntoEmbarque, true);
			for (int l = 0; l < lsta.size(); l ++) {
				Agencia agencia= lsta.get(l);
				Comboitem oComboitem = new Comboitem();
				oComboitem.setLabel(agencia.getNombreCorto());
				oComboitem.setValue(agencia);
				cmbPuntoEmbarque.appendChild(oComboitem);
				//Habilita el embarque solo si le corresponde a la agencia
				if(btnAbrirEmbarque.isDisabled() && getAgencia().getId().intValue()==agencia.getId().intValue())
					btnAbrirEmbarque.setDisabled(false);
			}
			Util.seleccionarValorItemCombo(Agencia.class, cmbPuntoEmbarque,getAgencia().getId());
			if(cmbPuntoEmbarque.getSelectedIndex()<0){
				if(cmbPuntoEmbarque.getItems().size()>0)
					cmbPuntoEmbarque.setSelectedIndex(0);
			}
			//Carga el mapa del bus
			crearEstructura(detalleItinerario.getItinerario().getServicio().getId(), grpbxMapaIda, false, detalleItinerario, mapaAsientosEmbarque);
			onRefress();
			btnRefress.setImage("/resources/mp_refrescarEnabled.png");
			btnRefress.setDisabled(false);
			
			//Deshabilita la apertura del embarque cuando la fecha del itinerario es diferente a la actual
			if(!(btnAbrirEmbarque.isDisabled()))
				btnAbrirEmbarque.setDisabled(!Constantes.FORMAT_DATE.format(new Date()).equals(Constantes.FORMAT_DATE.format(detalleItinerario.getFechaPartida())));

		}else {
			limpiaCabecera();
			cliarDatosPasajero();
		}
		
	}
	
	/**
	 * Permite enlazar los controles a la ventana de selecciÃ³n de Itinerario
	 * @param button :ha este Button se le adjuntara un listener con la llamada a la ventana de selecciÃ³n de itinerario
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
						cliarDatosPasajero();
						cmbPuntoEmbarque.setDisabled(false);
						btnAbrirEmbarque.setDisabled(true);
						itinerario=null;
						detalleItinerario=null;
						embarquePasajero=null;						
						itinerario=seleccionaItinerario.getItinerario();
						detalleItinerario=seleccionaItinerario.getDetalleItinerario();
						lbxItinerario.setValue(itinerario.getId());						
						//Carga los pintos de embarque
						ArrayList<Agencia> lsta= (ArrayList<Agencia>) ServiceLocator.getManifiestoManager().consultaPtoControl(itinerario.getId());
						cmbPuntoEmbarque.getItems().clear();
						UtilData.cargarGenericData(cmbPuntoEmbarque, true);
						for (int l = 0; l < lsta.size(); l ++) {
							Agencia agencia= lsta.get(l);
							Comboitem oComboitem = new Comboitem();
							oComboitem.setLabel(agencia.getNombreCorto());
							oComboitem.setValue(agencia);
							cmbPuntoEmbarque.appendChild(oComboitem);
							//Habilita el embarque solo si le corresponde a la agencia
							if(btnAbrirEmbarque.isDisabled() && getAgencia().getId().intValue()==agencia.getId().intValue())
								btnAbrirEmbarque.setDisabled(false);
						}
						Util.seleccionarValorItemCombo(Agencia.class, cmbPuntoEmbarque,getAgencia().getId());
						if(cmbPuntoEmbarque.getSelectedIndex()<0){
							if(cmbPuntoEmbarque.getItems().size()>0)
								cmbPuntoEmbarque.setSelectedIndex(0);
						}
						//Carga el mapa del bus
						crearEstructura(detalleItinerario.getItinerario().getServicio().getId(), grpbxMapaIda, false, detalleItinerario, mapaAsientosEmbarque);
						onRefress();
						btnRefress.setImage("/resources/mp_refrescarEnabled.png");
						btnRefress.setDisabled(false);
						
						//Deshabilita la apertura del embarque cuando la fecha del itinerario es diferente a la actual
						if(!(btnAbrirEmbarque.isDisabled()))
							btnAbrirEmbarque.setDisabled(!Constantes.FORMAT_DATE.format(new Date()).equals(Constantes.FORMAT_DATE.format(detalleItinerario.getFechaPartida())));	
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

		if(itinerario!=null && detalleItinerario!=null){
			lbBus.setValue(itinerario.getBus()!=null?itinerario.getBus().getCodigo():"--");
			lbRuta.setValue(detalleItinerario.getRuta().getOrigen()+" - "+itinerario.getRuta().getDestino());
			lbServicio.setValue(itinerario.getServicio().getDenominacion());
			lbSalida.setValue(Constantes.FORMAT_DATE.format(detalleItinerario.getFechaPartida())+" "+detalleItinerario.getHoraPartida());
			listVentas= ServiceLocator.getVentaPasajesManager().buscarVentasForMapaBus(itinerario.getId());
			
			/*Validando el estado del embarque*/
			if(embarquePasajero!=null && embarquePasajero.getEstadoEmbarque().intValue()==Constantes.TRUE_VALUE){
				txtNumeroDocumento.setDisabled(false);
				txtNumeroDocumento.setFocus(true);
				
				btnCerrar.setVisible(false);
				btnAbrirEmbarque.setVisible(false);
				btnCerrarEmbarque.setVisible(true);	
				txtNumeroDocumento.setFocus(true);
				cmbPuntoEmbarque.setDisabled(true);
				cmbDetalleItinerario.setDisabled(true);
			}else{
				btnCerrar.setVisible(true);
				btnAbrirEmbarque.setVisible(true);
				btnCerrarEmbarque.setVisible(false);
				txtNumeroDocumento.setDisabled(true);
				cmbPuntoEmbarque.setDisabled(false);
				cmbDetalleItinerario.setDisabled(false);
			}
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
							txtNumeroDocumento.setText("");
							txtNumeroDocumento.setDisabled(false);
							txtNumeroDocumento.setFocus(true);
							btnCerrar.setVisible(false);
							cmbDetalleItinerario.setDisabled(true);
							
							Util.seleccionarValorItemCombo(Agencia.class, cmbPuntoEmbarque, getAgencia().getId());
							crearEstructura(detalleItinerario.getItinerario().getServicio().getId(), grpbxMapaIda, false, detalleItinerario, mapaAsientosEmbarque);
							onRefress();
							cmbPuntoEmbarque.setDisabled(true);
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
	public void cerraEmbarque(){
		try {			
			Messagebox.show(Messages.getString("wndDespachoPasajeros.question.cerrarDespacho"), DlgMessage.NOMBREAPLICACION, DlgMessage.BTN_YESNO, Messagebox.QUESTION,DlgMessage.BTN_DEFAULT_NO, new EventListener<Event>() {
				@Override
				public void onEvent(Event e) throws Exception {
					if(e.getName().equals("onYes")){
						try {							
							/*Recupera la fecha y hora del servidor.*/
							String dateServer=MyTime.dateTimeServer();
							Date dateCierre= Constantes.FORMAT_DATE.parse(dateServer);
							String horaCierre=dateServer.split(" ")[1];
							horaCierre=horaCierre.substring(0, horaCierre.length()-3).trim();
							/*Realiza el cierre del embarque*/
							embarquePasajero.setFechaCierre(dateCierre);
							embarquePasajero.setHoraCierre(horaCierre);
							embarquePasajero.setEstadoEmbarque(Constantes.FALSE_VALUE);
							UtilData.auditarRegistro(embarquePasajero, true, getUsuario(), Executions.getCurrent());
							ServiceLocator.getEmbarquePasajeroManager().actualizar(embarquePasajero);
							
							cliarDatosPasajero();
							
							txtNumeroDocumento.setDisabled(true);
							btnCerrarEmbarque.setVisible(false);
							btnCerrar.setVisible(true);
							cmbPuntoEmbarque.setDisabled(false);
							cmbDetalleItinerario.setDisabled(false);
							
							crearEstructura(detalleItinerario.getItinerario().getServicio().getId(), grpbxMapaIda, false, detalleItinerario, mapaAsientosEmbarque);
							onRefress();
							
							DlgMessage.information("Se realizó correctamente");
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
	 * Limpia controles del cavecera
	 */
	private void limpiaCabecera(){
		lbRuta.setValue("");
		lbBus.setValue("");
		lbServicio.setValue("");
		lbSalida.setValue("");		
	}
	
	@SuppressWarnings("deprecation")
	private void crearEstructura(Integer idServicio, Groupbox grpbxParent, boolean esRetorno, DetalleItinerario detalleItinerario, Map<String, Asiento> mapaAsientos){
		try{
			Servicio servicio = null;
			/*	Busca el mapa del bus de acuerdo al servicio*/
			List<MapaBus> lstMapaBus = ServiceLocator.getMapaBusManager().buscarMapaBus(idServicio, Constantes.VALUE_ACTIVO);			
			
			/*	Creamos un Map el cual tendra como key un objeto coordenada y como valor el objeto MapaBus esto es deacuerdo a 
			 * lo que tenemos en la Base de datos	*/
			Map<Coordenada, MapaBus> mapCoordenadas = new HashMap<Coordenada, MapaBus>();
			for(MapaBus mapaBus : lstMapaBus){
				Coordenada coordenada = new Coordenada(mapaBus.getNumeroFila(), mapaBus.getNumeroColumna(), mapaBus.getNumeroPiso());
				mapCoordenadas.put(coordenada, mapaBus);
			}
			if(lstMapaBus.size()>0)
				servicio = lstMapaBus.get(0).getServicio();
			
			int nPisos = servicio.getNumeroPisos();
			int nFilas = servicio.getNumeroFilasPiso1();
			int nColumnas = servicio.getNumeroColumnasPiso1();
			prefijoAsiento = "imgAsientoIdaPiso1_";
			if(esRetorno)
				prefijoAsiento = "imgAsientoRetornoPiso1_";
			Integer numeroAsiento = 0;
			inicializarEstructura(grpbxParent);
			Image imagen = generarImagen(IMAGE_PRIMER_PISO, 154, 43);
			mapaAsientos = new HashMap<String, Asiento>();
			/*	Recorremos la cantidad de pisos del servicio	*/
			for(int i=0; i<nPisos; i++){
				String idGrid = "grdIdaPiso1";
				if(esRetorno)
					idGrid = "grdRetornoPiso1";
				/*	Si cambiamos al siguiente piso redefinimos los valores de las variables	*/
				if(i==1){
					nFilas = servicio.getNumeroFilasPiso2();
					nColumnas = servicio.getNumeroColumnasPiso2();
					prefijoAsiento = "imgAsientoIdaPiso2_";
					idGrid = "grdIdaPiso2";
					if(esRetorno){
						prefijoAsiento = "imgAsientoRetornoPiso2_";
						idGrid = "grdRetornoPiso2";
					}
					imagen = generarImagen(IMAGE_SEGUNDO_PISO, 154, 34);					
				}
				/*	Creando la grilla contenedora de asientos	*/
				Grid gridPiso = new Grid();
				gridPiso.setId(idGrid);
				gridPiso.setStyle("border:none;background:white");
				gridPiso.setWidth("154px");
				Rows rows = new Rows();
				Row row = new Row();
				row.setSpans(String.valueOf(nColumnas));
				row.appendChild(imagen);
				row.setStyle("background:white; padding:0px");
				rows.appendChild(row);
				numeroAsiento = 0;
				int totalAsientos=0;
				/*	Recorremos la cantidad de filas	*/
				for(int j=0; j<nFilas; j++){
					row = new Row();
					/*	Recorremos la cantidad de columnas	*/
					for(int k=0; k<nColumnas; k++){
						Div oDiv = new Div();
						oDiv.setWidth("28px");
						oDiv.setHeight("28px");
						oDiv.setStyle("padding:none");
						/*	Definimos la coordenada actual	*/
						String coordenadaActual = j+"-"+k+"-"+i;
						/*	Iteramos el mapa de coordenadas creado al inicio y comparamos	*/
						for(Coordenada coordenada : mapCoordenadas.keySet()){
							if(coordenada.toString().equals(coordenadaActual)){
								/*	Obtenemos el objeto y empezamos a setear las imagenes	*/
								MapaBus objetoBus = mapCoordenadas.get(coordenada);
								HashMap<String, String> propiedades = new HashMap<String, String>();
								numeroAsiento++;
								
								/*	Verificamos que el objeto sea del tipo Asiento	*/
								if(objetoBus.getTipoObjeto().intValue()==TIPO_ASIENTO){
									totalAsientos++;
									Asiento asiento = new Asiento();
//									asiento.addEventListener(Events.ON_CLICK, new EventListener<Event>() {
//										public void onEvent(Event e){
//											Component component = e.getTarget().getParent().getParent().getParent().getParent();
//											if(component.getId().equals("grdIdaPiso1") || component.getId().equals("grdIdaPiso2")){
//												onClickAsiento((Asiento)e.getTarget(), null, mapaAsientosIda, true);
//											}										
//										}
//									});
									propiedades.put("ocupante", "pasajero");
									asiento.setId(prefijoAsiento + objetoBus.getNumeroAsiento()); 
									asiento.setOcupante(Asiento.OCUPANTE_PASAJERO);
									asiento.setEstadoAsiento(Constantes.ASIENTO_PENDIENTE_EMBARQUE);
									asiento.setFila(j);
									asiento.setColumna(k);
									asiento.setPiso(i);
									asiento.setNumeroAsiento(objetoBus.getNumeroAsiento());
									asiento.setSrc(objetoBus.getPathImagen());
									asiento.setPropiedades(propiedades);
									asiento.setDraggable("true");
									asiento.setDetalleItinerario(detalleItinerario);
									asiento.setKey();
									asiento.setStyle("cursor:pointer");
									oDiv.appendChild(asiento);
									/*	Agregando los asientos a un Hashmap*/
									mapaAsientos.put(asiento.getKey(), asiento);
								}else if(objetoBus.getTipoObjeto().intValue()==TIPO_MONITOR){
									Monitor monitor = new Monitor();
									monitor.setId(prefijoAsiento + numeroAsiento);
									monitor.setFila(j);
									monitor.setColumna(k);
									monitor.setPiso(i);
									monitor.setSrc(objetoBus.getPathImagen());
									monitor.setPropiedades(propiedades);
									oDiv.appendChild(monitor);
								}else if(objetoBus.getTipoObjeto().intValue()==TIPO_CAFETERIA){
									Cafeteria cafeteria = new Cafeteria();
									cafeteria.setId(prefijoAsiento + numeroAsiento);
									cafeteria.setFila(j);
									cafeteria.setColumna(k);
									cafeteria.setPiso(i);
									cafeteria.setSrc(objetoBus.getPathImagen());
									cafeteria.setPropiedades(propiedades);
									oDiv.appendChild(cafeteria);
								}else{
									ServiciosHigienicos sshh = new ServiciosHigienicos();
									sshh.setId(prefijoAsiento + numeroAsiento);
									sshh.setFila(j);
									sshh.setColumna(k);
									sshh.setPiso(i);
									sshh.setSrc(objetoBus.getPathImagen());
									sshh.setPropiedades(propiedades);
									oDiv.appendChild(sshh);
								}
								break;
							}
						}
						row.appendChild(oDiv);
						row.setStyle("padding-top:2px; padding-left:3px; padding-right:0px; border:normal !important; background:#eeeeee");// background:#eeeeee");
					}
					rows.appendChild(row);
				}
				gridPiso.appendChild(rows);
				grpbxParent.appendChild(gridPiso);
				onRefreshMapaAsientos(mapaAsientos, detalleItinerario);
				mapaAsientosEmbarque = mapaAsientos;
//				if(totalAsientos==36 ||totalAsientos==40)
//					grpbxMapaIda.setHeight("433px");
//				else
//					grpbxMapaIda.setHeight("467px");
				
			}			
//			onloadEstadisticas();
		}catch(Exception ex){
			ex.printStackTrace();
			log.error(ex);
		}
	}
	
	/**
	 * Inicializa(limpia los objetos existentes) el contenedor de los asientos.
	 */
	private void inicializarEstructura(Groupbox groupbox){
		for(int i=groupbox.getChildren().size()-1; i>-1; i--){
			Component component = (Component)groupbox.getChildren().get(i);
			if(!(component instanceof Caption))
				groupbox.removeChild(component);
		}
	}
	
	/**
	 * Genera el objeto imagen para los pisos del bus
	 * @param src		: Path de la imagen a mostrar.
	 * @param width		: Ancho de la imagen.
	 * @param height	: Alto de la imagen.
	 * @return Image.
	 */
	private Image generarImagen(String src, int width, int height){
		Image imagen = new Image();
		imagen.setSrc(src);
		imagen.setWidth(String.valueOf(width)+"px");
		imagen.setHeight(String.valueOf(height)+"px");
		return imagen;
	}
	
	/**
	 * Realiza el refresco del mapa del bus.
	 * @throws Exception
	 */
	public void onRefreshMap() {
		onRefreshMapaAsientos(mapaAsientosEmbarque, detalleItinerario);
	}
	
	/**
	 * Realiza el refresco del mapa del bus.
	 * @param mapa				: Mapa de asientos
	 * @param detalleItinerario	: Itinerario con el que se desea actualizar el mapa.
	 */
	@SuppressWarnings("unchecked")
	public void onRefreshMapaAsientos(Map<String, Asiento> mapaAsientos, DetalleItinerario detalleItinerario){
		try{			
			onCleanMap(mapaAsientos);
			/*	Obtenemos el subconjunto que queremos buscar segun la ruta seleccioanda		*/
			List<Integer> subConjuntoBuscar = obtenerSubconjunto(detalleItinerario.getItinerario().getListSecuenciaTramo(), detalleItinerario.getRuta().getLocalidadOrigen().getId(), detalleItinerario.getRuta().getLocalidadDestino().getId());			
			List<VentaPasaje> lstVentas = ServiceLocator.getVentaPasajesManager().buscarVentasForMapaBus(detalleItinerario.getItinerario().getId());
			
			/*Busca los embarque del servicio*/
			TreeMap<String, Object>criteriosBusqueda= new TreeMap<>();
			criteriosBusqueda.put("itinerario", itinerario);
//			criteriosBusqueda.put("agencia", getAgencia());
			criteriosBusqueda.put("estadoRegistro", Constantes.VALUE_ACTIVO);
			List<EmbarquePasajero> resultEmb=ServiceLocator.getEmbarquePasajeroManager().buscarPorX(criteriosBusqueda, null);
			for(EmbarquePasajero embarquePasajero :resultEmb){
				if(embarquePasajero.getEstadoEmbarque().intValue()==Constantes.TRUE_VALUE)
					this.embarquePasajero=embarquePasajero;
				/*Buscando los pasajeros embarcados - (detalle embarque)*/						
				criteriosBusqueda= new TreeMap<>();
				criteriosBusqueda.put("embarquePasajero", embarquePasajero);
				criteriosBusqueda.put("estadoRegistro", Constantes.VALUE_ACTIVO);
				List<String> criteriosOrdenar= new ArrayList<>();
				criteriosOrdenar.add("id");				
				List<DetalleEmbarquePasajero>resultDetemb=ServiceLocator.getDetalleEmbarquePasajeroManager().buscarPorX(criteriosBusqueda, criteriosOrdenar);
				for(DetalleEmbarquePasajero detalleEmbarquePasajero : resultDetemb){
					VentaPasaje ventaPasajeEmb=detalleEmbarquePasajero.getVentaPasaje();
					for(VentaPasaje venta :lstVentas){
						//Asocia el embarque con la venta
						if(venta.getId().longValue()==ventaPasajeEmb.getId().longValue()){
							venta.setDetalleEmbarquePasajero(detalleEmbarquePasajero);
							Pasajero pasajero= ServiceLocator.getPasajeroManager().buscarPorId(ventaPasajeEmb.getPasajero().getId());
							TipoDocumento tipoDocumento=ServiceLocator.getTipoDocumentoManager().buscarPorId(pasajero.getTipoDocumento().getId().longValue());
//							TipoDocumento tipoDocumento=ServiceLocator.getTipoDocumentoManager().buscarPorId(ventaPasajeEmb.getPasajero().getTipoDocumento().getId().longValue());
							Agencia agenciaPartida= ServiceLocator.getAgenciaManager().buscarPorId(ventaPasajeEmb.getAgenciaPartida().getId().longValue());
							venta.setAgenciaPartida(agenciaPartida);
							venta.getPasajero().setTipoDocumento(tipoDocumento);
							break;
						}
					}
				}
			}
			
			lstVentas = obtenerConjuntos(lstVentas, detalleItinerario.getItinerario().getListSecuenciaTramo());
			if(lstVentas.size()>0){
				/*	Recorremos las ventas	*/
				for(VentaPasaje venta : lstVentas){
					String key=venta.getNumeroAsiento()+"-"+venta.getNumeroPiso();
					/*	Recorremos los subconjuntos	*/
					for(Integer orden : subConjuntoBuscar){						
						if(mapaAsientos.containsKey(key) && venta.getSubConjunto().contains(orden)){
							DetalleEmbarquePasajero detalleEmbarquePasajero=null;
							if(venta.getDetalleEmbarquePasajero()!=null)
								detalleEmbarquePasajero=venta.getDetalleEmbarquePasajero();
							
							//permite el embarque de pasajeros de cualquier punto de embarque siempre y cuendo sea de las misma localidad							
//							if(venta.getAgenciaPartida().getLocalidad().getId().intValue()==getAgencia().getLocalidad().getId().intValue() ||
//									(detalleEmbarquePasajero!=null && detalleEmbarquePasajero.getEmbarquePasajero().getAgencia().getId().intValue()==getAgencia().getId().intValue())){
 							if(detalleEmbarquePasajero!=null){
								Asiento asiento = mapaAsientos.get(key);
//								if(detalleEmbarquePasajero!=null){
									if(venta.getPasajero().getSexo().getId().intValue()==Constantes.ID_SEXO_FEMENINO)
										asiento.setSrc(Constantes.ICON_VENDIDO_FEMALE+venta.getNumeroAsiento()+Constantes.IMAGE_EXTENSION);
									else
										asiento.setSrc(Constantes.ICON_VENDIDO_MALE+venta.getNumeroAsiento()+Constantes.IMAGE_EXTENSION);
									asiento.setEstadoAsiento(Constantes.ASIENTO_EMBARCADO);
									EmbarquePasajero _embarquePasajero = ServiceLocator.getEmbarquePasajeroManager().buscarPorId(detalleEmbarquePasajero.getEmbarquePasajero().getId());
									String infoAsiento="EMBARCADO EN "+_embarquePasajero.getAgencia().getNombreCorto();
									infoAsiento += "\n"+venta.getPasajero();
									asiento.setTooltiptext(infoAsiento);
//								}else{								
////									asiento.setSrc(Constantes.ICON_SEMI_OCUPADO+venta.getNumeroAsiento()+Constantes.IMAGE_EXTENSION);
//									asiento.setEstadoAsiento(Constantes.ASIENTO_PENDIENTE_EMBARQUE);
//								}														
//								asiento.setTooltiptext(venta.getRuta().getOrigen()+"-"+venta.getRuta().getDestino());								
								asiento.setVentaPasaje(venta);								
							}
							break;
						}
					}
				}
				onLoadPaxByEmbarcar(lstVentas);
			}			
			
			/*	BUSCAMOS LOS ASIENTOS QUE ESTEN BLOQUEADOS PARA EL ITINERARIO SELECCIONADO	*/
			List<TmpOcupacionAsientos> lstBloqueados = ServiceLocator.getTmpOcupacionAsientosManager().buscarAsientosBloqueados(detalleItinerario.getItinerario().getId());
			lstBloqueados = obtenerConjuntos(lstBloqueados, detalleItinerario.getItinerario().getListSecuenciaTramo());
			if(lstBloqueados.size()>0){
				for(TmpOcupacionAsientos bloqueado : lstBloqueados){
					for(Integer orden : subConjuntoBuscar){
						if(mapaAsientos.containsKey(bloqueado.getKey()) && bloqueado.getSubConjunto().contains(orden)){
							Asiento asiento = mapaAsientos.get(bloqueado.getKey());
							asiento.setSrc(Constantes.ICON_BLOQUEADO+bloqueado.getNumeroAsiento()+Constantes.IMAGE_EXTENSION);
							asiento.setEstadoAsiento(Constantes.ASIENTO_BLOQUEADO);
							break;
						}
					}
				}
			}
			
			
		}catch(Exception ex){
			ex.printStackTrace();
			log.error(ex);
			DlgMessage.error(this.getClass().getName()+" "+ex.getMessage());
		}
	}
	
	private void onLoadPaxByEmbarcar(List<VentaPasaje> listVenta)throws Exception{
		Util.limpiarListbox(ltbxPasajerosEmbarque);
		int countEmbarcados=0;
		for(VentaPasaje venta : listVenta){
			if(venta.getTipoTransaccion().equals(Constantes.TIPO_OPERACION_VENTA)){
				Agencia agenciaEmbarque=null;
				if(cmbPuntoEmbarque.getSelectedItem().getValue() instanceof Agencia)
					agenciaEmbarque=(Agencia)cmbPuntoEmbarque.getSelectedItem().getValue();
				
				//Cantidad de pasajeros embarcados
				if((agenciaEmbarque==null || venta.getAgenciaPartida().getId().intValue()==agenciaEmbarque.getId().intValue()) && venta.getDetalleEmbarquePasajero()!=null)
					countEmbarcados++;
				
				//Lista de pasajeros por embarcar
				if((agenciaEmbarque==null || venta.getAgenciaPartida().getId().intValue()==agenciaEmbarque.getId().intValue()) && venta.getDetalleEmbarquePasajero()==null){					
					boolean conInfante= false; //(venta.getListVentaPasajeInfantes()!=null && venta.getListVentaPasajeInfantes().size()>0);
					
					Listitem item= new Listitem();
					Listcell cell= new Listcell(venta.getPasajero().toString());
					item.appendChild(cell);
//					cell= new Listcell(venta.getInfante()!=null?"SI":"NO");
//					cell.setStyle(venta.getInfante()!=null?"color:red":"");
					cell= new Listcell(conInfante?"SI":"NO");
					cell.setStyle(conInfante?"color:red":"");
					item.appendChild(cell);
					cell= new Listcell(venta.getNumeroAsiento().toString().length()==1?"0"+venta.getNumeroAsiento().toString():venta.getNumeroAsiento().toString());
					cell.setStyle("font-size:11px !important");
					item.appendChild(cell);
					A lkEmbarcar= new A("Embarcar");
					lkEmbarcar.setStyle("text-transform:none;color:#CB4335;font-size:11px!important");
//					lkEmbarcar.setDisabled(txtNumeroDocumento.isDisabled());
					lkEmbarcar.setDisabled(embarquePasajero!=null && embarquePasajero.getEstadoEmbarque().intValue()==Constantes.TRUE_VALUE?false:true);
					cell= new Listcell();
					cell.appendChild(lkEmbarcar);
					item.appendChild(cell);
					
					item.setValue(venta);
					ltbxPasajerosEmbarque.appendChild(item);	
					lkEmbarcar.setAttribute(VentaPasaje.class.getName(), venta);
					lkEmbarcar.addEventListener(Events.ON_CLICK, new EventListener<Event>() {
						@Override
						public void onEvent(Event arg0) throws Exception {
							VentaPasaje ovenPasaje=(VentaPasaje) arg0.getTarget().getAttribute(VentaPasaje.class.getName());
							Onchanging_txtNumeroDocumento(ovenPasaje.getNumeroBoleto(), arg0);
							
						}
					});
				}
			}			
		}
		Listfoot listfoot= new Listfoot();
		Listfooter listfooter= new Listfooter();
		listfooter.setSpan(4);
		Label label= new Label("POR EMBARCAR : ");
		label.setStyle("color:red;font-size:14px !important;font-weight:800");
		listfooter.appendChild(label);
		listfoot.appendChild(listfooter);
		label = new Label(String.valueOf(ltbxPasajerosEmbarque.getItemCount()));
		label.setStyle("color:red;font-size:17px !important;font-weight:900");
		listfooter.appendChild(label);
		listfoot.appendChild(listfooter);
		Separator separator=new Separator("vertical");
		separator.setWidth("100px");
		listfooter.appendChild(separator);
		listfoot.appendChild(listfooter);
		
		label= new Label("EMBARCADOS : ");
		label.setStyle("color:#239B56;font-size:14px !important;font-weight:800");
		listfooter.appendChild(label);
		listfoot.appendChild(listfooter);
		label = new Label(String.valueOf(countEmbarcados));
		label.setStyle("color:#239B56;font-size:17px !important;font-weight:900");
		listfooter.appendChild(label);
		listfoot.appendChild(listfooter);
		
		ltbxPasajerosEmbarque.appendChild(listfoot);
		
	}
	
	/**
	 * Realiza la limpieza del mapa del bus
	 */
	public void onCleanMap(Map<String, Asiento> mapa){
		for(String key : mapa.keySet()){
			Asiento asiento = mapa.get(key);
			asiento.setSrc(Constantes.ICON_DISPONIBLE+asiento.getNumeroAsiento()+Constantes.IMAGE_EXTENSION);
			asiento.setEstadoAsiento(Constantes.ASIENTO_PENDIENTE_EMBARQUE);
//			asiento.setTooltiptext("DISPONIBLE");
			asiento.setTooltiptext("");
		}
	}
	
	/**
	 * Obtiene los subconjuntos de una lista de ventas, tmpOcupacion.
	 * @param lista			: Lista de registros de los cuales queremos obtener los subconjuntos.
	 * @param lstSecuencias	: Lista de secuencia segun el itinerario.
	 * @return
	 */
	@SuppressWarnings({"rawtypes","unchecked"})
	private List obtenerConjuntos(List lista, List<SecuenciaTramo> lstSecuencias){
		List result = new ArrayList();
		for(int i=0; i<lista.size(); i++){
			Object obj = lista.get(i);
			if(obj instanceof TmpOcupacionAsientos){
				TmpOcupacionAsientos tmp = (TmpOcupacionAsientos)obj;
				List<Integer> subConjunto = obtenerSubconjunto(lstSecuencias, tmp.getRuta().getLocalidadOrigen().getId(), tmp.getRuta().getLocalidadDestino().getId());
				tmp.setSubConjunto(subConjunto);
				result.add(tmp);
			}else if(obj instanceof VentaPasaje){
				VentaPasaje ventaPasaje = (VentaPasaje)obj;
				List<Integer> subConjunto = obtenerSubconjunto(lstSecuencias, ventaPasaje.getRuta().getLocalidadOrigen().getId(), ventaPasaje.getRuta().getLocalidadDestino().getId());
				ventaPasaje.setSubConjunto(subConjunto);
				result.add(ventaPasaje);
			}
		}
		return result;
	}
	
	/**
	 * Obtine los subconjuntos de un registro de venta, tmpocupacion o de la ruta que estamos buscando. 
	 * @param lstSecuencias	: Lista de secuencia segun el itinerario.
	 * @param idOrigen		: Identificador del origen.
	 * @param idDestino		: Identificador del destino.
	 * @return
	 */
	private List<Integer> obtenerSubconjunto(List<SecuenciaTramo> lstSecuencias, Integer idOrigen, Integer idDestino){
		List<Integer> lstSubconjunto = new ArrayList<Integer>();
		/*	Recorremos la secuencia de tramos del itinerario	*/
		for(int j=0; j<lstSecuencias.size(); j++){
			SecuenciaTramo secuencia = lstSecuencias.get(j);
			/*	Validamos si el origen de la secuencia coincide con el origen de la ruta	*/
			if(secuencia.getOrigen().intValue()==idOrigen.intValue()){
				/*	Recorremos la secuencia de tramos desde la posicion j	*/
				for(int k=j; k<lstSecuencias.size(); k++){
					secuencia = lstSecuencias.get(k);
					lstSubconjunto.add(secuencia.getOrden());
					/*	Validamos si el destino de la secuencia coincide con el destino de la ruta	*/
					if(secuencia.getDestino().intValue()==idDestino.intValue())
						break;
				}
				break;
			}
		}
		return lstSubconjunto;
	}
	
	/**
	 * Evento utilizado cuando el usuario hace click en un asiento.
	 * @param e				: Evento
	 * @param mapaAsientos	: Mapa de asientos.
	 * @param ventaPasaje	: Itinerario seleccionado.
	 * @param esIda			: Indica si es Ida o retorno.
	 */
	private boolean onClickAsiento(Asiento asiento, VentaPasaje ventaPasaje, Map<String, Asiento> mapaAsientos, boolean isOnClickAsiento){
		key=asiento.getKey();
		boolean isCorrect=false;
		try{
			/*	Si el asiento se encuentra bloqueado mostrar mensaje de no disponibilidad	*/			
			if(isOnClickAsiento==false && consultaAsientoBloqueado(key, mapaAsientos)){				
				//DlgMessage.information("El Pasajero ya esta en la lista de Embarcados",txtNumeroDocumento);
				key="-1";
			}else{
				if(ventaPasaje!=null && isOnClickAsiento==false){
					/*	Agregamos el asiento a la lista de seleccionados	*/						
					if(ventaPasaje.getPasajero().getSexo().getId().intValue()==Constantes.ID_SEXO_MASCULINO)
						mapaAsientos.get(key).setSrc(Constantes.ICON_VENDIDO_MALE + asiento.getNumeroAsiento()+Constantes.IMAGE_EXTENSION);
					else
						mapaAsientos.get(key).setSrc(Constantes.ICON_VENDIDO_FEMALE + asiento.getNumeroAsiento()+Constantes.IMAGE_EXTENSION);
					mapaAsientos.get(key).setEstadoAsiento(Constantes.ASIENTO_EMBARCADO);									
					isCorrect=true;
				}
								
				if(isOnClickAsiento && mapaAsientos.get(key).getVentaPasaje()!=null){
					cliarDatosPasajero();
					
					onloadDatosPasajero(mapaAsientos.get(key).getVentaPasaje());
				}
			}
		}catch(DataIntegrityViolationException divex){
			DlgMessage.information(Messages.getString("WndVentaReserva.information.asientoBloqueado"));
		}catch(Exception ex){
			ex.printStackTrace();
			log.error(ex);
			DlgMessage.error(this.getClass().getName()+" "+ex.getMessage());
		}
		
		return isCorrect;
	}
	
	/**
	 * Consulta si el asiento esta bloqueado.
	 * @param key	: Clave a buscar en el mapa de asientos.
	 * @return boolean
	 */
	private boolean consultaAsientoBloqueado(String key, Map<String, Asiento> mapaAsientos){
		if(mapaAsientos.get(key).getEstadoAsiento().intValue()==Constantes.ASIENTO_PENDIENTE_EMBARQUE)
			return false;
		return true;
	}

}
