/**
 * Proyecto		: SISVYR
 * Sistema		: Sistema de Ventas y Reservas
 * Descripción	: 
 * Autor		: José Avalos
 * Fecha		: 11/09/2012
 */
package com.cystesoft.vyrbus.view.ctrl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TreeMap;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Execution;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zul.Bandbox;
import org.zkoss.zul.Button;
import org.zkoss.zul.Caption;
import org.zkoss.zul.Column;
import org.zkoss.zul.Columns;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Comboitem;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.Div;
import org.zkoss.zul.Filedownload;
import org.zkoss.zul.Grid;
import org.zkoss.zul.Groupbox;
import org.zkoss.zul.Hbox;
import org.zkoss.zul.Hlayout;
import org.zkoss.zul.Image;
import org.zkoss.zul.Intbox;
import org.zkoss.zul.Label;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listhead;
import org.zkoss.zul.Listheader;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Row;
import org.zkoss.zul.Rows;
import org.zkoss.zul.Separator;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Timebox;
import org.zkoss.zul.Window;

import com.cystesoft.vyrbus.model.bean.Agencia;
import com.cystesoft.vyrbus.model.bean.DetalleItinerario;
import com.cystesoft.vyrbus.model.bean.HRE;
import com.cystesoft.vyrbus.model.bean.Itinerario;
import com.cystesoft.vyrbus.model.bean.ItinerarioAgenciaLlegada;
import com.cystesoft.vyrbus.model.bean.ItinerarioAgenciaLlegadaID;
import com.cystesoft.vyrbus.model.bean.ItinerarioAgenciaPartida;
import com.cystesoft.vyrbus.model.bean.ItinerarioAgenciaPartidaID;
import com.cystesoft.vyrbus.model.bean.Localidad;
import com.cystesoft.vyrbus.model.bean.Manifiesto;
import com.cystesoft.vyrbus.model.bean.Pasajero;
import com.cystesoft.vyrbus.model.bean.ProgramacionServicio;
import com.cystesoft.vyrbus.model.bean.Ruta;
import com.cystesoft.vyrbus.model.bean.Servicio;
import com.cystesoft.vyrbus.model.bean.TipoItinerario;
import com.cystesoft.vyrbus.model.bean.VentaPasaje;
import com.cystesoft.vyrbus.service.exceptions.CancelaGrabacionException;
import com.cystesoft.vyrbus.service.exceptions.CapacidadServicioMayorExeption;
import com.cystesoft.vyrbus.service.exceptions.FechaInicioNullException;
import com.cystesoft.vyrbus.service.exceptions.FechaItinerarioNullException;
import com.cystesoft.vyrbus.service.exceptions.FechaLlegadaNullException;
import com.cystesoft.vyrbus.service.exceptions.FechaPasadaException;
import com.cystesoft.vyrbus.service.exceptions.HoraLlegadaNullException;
import com.cystesoft.vyrbus.service.exceptions.HoraPartidaNullException;
import com.cystesoft.vyrbus.service.exceptions.ItinerarioException;
import com.cystesoft.vyrbus.service.exceptions.LocalidadNullException;
import com.cystesoft.vyrbus.service.exceptions.NoRemoverRegistroDelListBox;
import com.cystesoft.vyrbus.service.exceptions.RutaNullException;
import com.cystesoft.vyrbus.service.exceptions.ServicioNullException;
import com.cystesoft.vyrbus.service.exceptions.TerminalDestinoNullException;
import com.cystesoft.vyrbus.service.exceptions.TerminalOrigenNullException;
import com.cystesoft.vyrbus.service.exceptions.TipoItinerarioNullException;
import com.cystesoft.vyrbus.service.locator.ServiceLocator;
import com.cystesoft.vyrbus.service.mappers.SecuenciaTramo;
import com.cystesoft.vyrbus.service.util.Constantes;
import com.cystesoft.vyrbus.service.util.Messages;
import com.cystesoft.vyrbus.service.util.MyTime;
import com.cystesoft.vyrbus.service.util.Util;
import com.cystesoft.vyrbus.service.util.UtilData;
import com.cystesoft.vyrbus.view.ui.DlgMessage;
import com.cystesoft.vyrbus.view.ui.WndFiltrarParametros;
import com.cystesoft.vyrbus.view.ui.WndOpcionesMantenimiento;

/**
 *
 * @author José Avalos
 * @since JDK1.6
 */

public class WndItinerario extends WndOpcionesMantenimiento {
	private static final long serialVersionUID = 1L;
	private Combobox cmbTipoItinerario;
	private Combobox cmbServicio;
	private Combobox cmbOrigen;
	private Combobox cmbRuta;
	private Datebox	dbFechaItinerario;
	private Timebox	tbHoraPartida;
//	private Combobox cmbTermOrigen;
//	private Combobox cmbTermDestino;
	private Listbox	lbxDetalleItinerario;
//	private Grid grdRutasTrifas;
	private Combobox cmbTerminalPartida;
	private Timebox	tbterHoraPartida;
	private Listbox lbxTerminalPartida;
	private Combobox cmbTerminalLlegada;
	private Timebox tbterHoraLlegada;
	private Listbox lbxTerminalLlegada;
	private Datebox dbFechaInicio;
	private Datebox dbFechafin;
	private Button cmdAddTerPartida;
	private Button cmdAddTerLlegada;
	private Div divMantenimiento;
	private Button cmdAgregarTramo;
	private Bandbox bbxTerminalOrigen;
	private Bandbox bbxTerminalDestino;
	private Listbox lbxTerminalOrigen;
	private Listbox lbxTerminalDestino;

	private Itinerario oitinerario=null; 

	private Integer indexEdicionTramo = 0;
	private Boolean esEdicionTramo = false;
	private Integer action; 

	private TreeMap<String, Object> criteriosBusqueda = new TreeMap<String, Object>();

	private List<DetalleItinerario> lstItinerariosDetalle;
	private List<DetalleItinerario> listTramoDuplicado=null;
	
	private List<VentaPasaje>listVentasNoUpdate=new ArrayList<VentaPasaje>();//Utilizado para la validacion de la ventas
	private Window wndItinerarios = null;
	private List<Agencia> lstAgenciaPartida;
	private List<Agencia> lstAgenciaLlegada;
	
	private String strHoraPartida = "";
	private boolean updateHorarios=false;
	private List<Listitem> lstAgenciaOrigen;
	private List<Listitem> lstAgenciaDestino;
	
	
	final String disabledDelete="/resources/mp_eliminarDisabled.png";
	final String enabledDelete="/resources/mp_eliminarEnabled.png";
	final String disabledEdit="/resources/mp_editarDisabled.png";
	final String enabledEdit="/resources/mp_editarEnabled.png";
	
	/*Variables utilizadas para consultar itinerarios afectados en la actualización.*/
	String secuenciaTramo=""; 
	String horaPartida="";
	Integer idRuta=0;
	Integer idServicio=0;
	Integer idTipoItinerario=0;
	Integer idAgenciaPartida=0; 
	Integer idagenciaLlegada=0; 
	
	//Variables que guardan los parametros de busuqeda
	String sOrigen ="";	
	String sDestino ="";	
	String sServicio = ""; 
	Long iItinerario = null;
	String tipoDeItinerario="";
	String criterioOrden="";
	Date fechaInicio = null;
	Date fechaFinal=null;
	
	@Override
	public void onCreate() throws Exception {
		UtilData.cargarDataCombo(cmbTipoItinerario, TipoItinerario.class, false);
		UtilData.cargarDataCombo(cmbServicio, Servicio.class, false);
		UtilData.cargarDataCombo(cmbOrigen, Localidad.class, false);

		Util.disabledBtnAgregar(true, cmdAgregarTramo, accesoGrabar());
		Util.disabledBtnAgregar(true, cmdAddTerPartida, accesoGrabar());
		Util.disabledBtnAgregar(true, cmdAddTerLlegada, accesoGrabar());
		
		divMantenimiento.setWidth("840px");	
		
		lbxDetalleItinerario.addEventListener(Events.ON_DOUBLE_CLICK,new EventListener<Event>() {
			@Override
			public void onEvent(Event event) throws Exception {
				if(!(btnGuardar.isDisabled()))
					editarTramo();
			}
		});
		
		tbHoraPartida.addEventListener(Events.ON_BLUR, new EventListener<Event>() {
			@Override
			public void onEvent(Event events) {
				if(updateHorarios) {
					if(lstAgenciaOrigen != null) {
						for(Listitem it : lstAgenciaOrigen) {
							for(Listitem item : lbxTerminalOrigen.getItems()) {
								if(((Agencia)item.getValue()).getDenominacion().equals(((Agencia)it.getValue()).getDenominacion())) {
									((Timebox)item.getChildren().get(1).getChildren().get(0)).setText(((Timebox)it.getChildren().get(1).getChildren().get(0)).getText());
									break;
								}
							}
						}
					}
					
					if(lstAgenciaDestino != null) {
						for(Listitem it : lstAgenciaDestino) {
							for(Listitem item : lbxTerminalDestino.getItems()) {
								if(((Agencia)item.getValue()).getDenominacion().equals(((Agencia)it.getValue()).getDenominacion())) {
									((Timebox)item.getChildren().get(1).getChildren().get(0)).setText(((Timebox)it.getChildren().get(1).getChildren().get(0)).getText());
									break;
								}
							}
						}
					}
					
					if(!strHoraPartida.equals("")) {
						if(!strHoraPartida.equals(tbHoraPartida.getText())) {
							Date horaInicial = Util.StringtoDate(dbFechaItinerario.getText()+" "+strHoraPartida, "dd/MM/yyyy HH:mm");
							Date nuevaHora = Util.StringtoDate(dbFechaItinerario.getText()+" "+tbHoraPartida.getText(), "dd/MM/yyyy HH:mm");
							Long diff = nuevaHora.getTime() - horaInicial.getTime();
							for(Listitem item : lbxTerminalOrigen.getSelectedItems()) {
								String hora = ((Timebox)item.getChildren().get(1).getChildren().get(0)).getText();
								Date newHora = Util.StringtoDate(dbFechaItinerario.getText() + " " + hora, "dd/MM/yyyy HH:mm");
								Long newDiff = newHora.getTime() + diff;
								String strNewHora = Util.DatetoString(new Date(newDiff), "HH:mm");
	//							System.out.println(strNewHora);
								((Timebox)item.getChildren().get(1).getChildren().get(0)).setText(strNewHora);
							}
							
							for(Listitem item : lbxTerminalDestino.getSelectedItems()) {
								String hora = ((Timebox)item.getChildren().get(1).getChildren().get(0)).getText();
								Date newHora = Util.StringtoDate(dbFechaItinerario.getText() + " " + hora, "dd/MM/yyyy HH:mm");
								Long newDiff = newHora.getTime() + diff;
								String strNewHora = Util.DatetoString(new Date(newDiff), "HH:mm");
	//							System.out.println(strNewHora);
								((Timebox)item.getChildren().get(1).getChildren().get(0)).setText(strNewHora);
							}
						}
					}
				}
				updateHorarios = false;
			}
		});
		
		tbHoraPartida.addEventListener(Events.ON_CHANGE, new EventListener<Event>() {
			@Override
			public void onEvent(Event events) {
				updateHorarios = true;
			}
		});
	}

	/*
	 * (non-Javadoc)
	 * @see com.tepsa.sisvyr.view.ui.IBase#initComponents()
	 */
	@Override
	public void initComponents() {
		cmbTipoItinerario=(Combobox)this.getFellow("cmbTipoItinerario");
		cmbServicio=(Combobox)this.getFellow("cmbServicio");
		cmbOrigen=(Combobox)this.getFellow("cmbOrigen");
		cmbRuta=(Combobox)this.getFellow("cmbRuta");
		dbFechaItinerario=(Datebox)this.getFellow("dbFechaItinerario");
		tbHoraPartida=(Timebox)this.getFellow("tbHoraPartida");
//		cmbTermOrigen=(Combobox)this.getFellow("cmbTermOrigen");
//		cmbTermDestino=(Combobox)this.getFellow("cmbTermDestino");
		lbxDetalleItinerario=(Listbox)this.getFellow("lbxDetalleItinerario");
//		grdRutasTrifas=(Grid)this.getFellow("grdRutasTrifas");
		cmbTerminalPartida=(Combobox)this.getFellow("cmbTerminalPartida");
		tbterHoraPartida=(Timebox)this.getFellow("tbterHoraPartida");
		lbxTerminalPartida=(Listbox)this.getFellow("lbxTerminalPartida");
		cmbTerminalLlegada=(Combobox)this.getFellow("cmbTerminalLlegada");
		tbterHoraLlegada=(Timebox)this.getFellow("tbterHoraLlegada");
		lbxTerminalLlegada=(Listbox)this.getFellow("lbxTerminalLlegada");
		dbFechaInicio=(Datebox)this.getFellow("dbFechaInicio");
		dbFechafin=(Datebox)this.getFellow("dbFechafin");
		cmdAddTerPartida=(Button)this.getFellow("cmdAddTerPartida");
		cmdAddTerLlegada=(Button)this.getFellow("cmdAddTerLlegada");
		divMantenimiento=(Div)this.getFellow("divMantenimiento");
		cmdAgregarTramo=(Button)this.getFellow("cmdAgregarTramo");
		bbxTerminalOrigen = (Bandbox)this.getFellow("bbxTerminalOrigen");
		bbxTerminalDestino = (Bandbox)this.getFellow("bbxTerminalDestino");
		lbxTerminalOrigen = (Listbox)this.getFellow("lbxTerminalOrigen");
		lbxTerminalDestino = (Listbox)this.getFellow("lbxTerminalDestino");
		
//		cmdEditarTramo=(Button)this.getFellow("cmdEditarTramo");
//		cmdQuitarTramo=(Button)this.getFellow("cmdQuitarTramo");
	}
/*
 * (non-Javadoc)
 * @see com.tepsa.sisvyr.view.ui.IOpcionesMantenimiento#onNew()
 */
	@Override
	public void onNew() {
		action=Constantes.ACTION_NEW;
		limpiaItinerario();
		limpiarTodasLasListas();
		cargaComboDefaul();
		LimpiaCombos();
//		HabilitaBotones_TerPartida(true);
//		HabilitaBotones_TerLlegada(true);
		Util.disabledBtnAgregar(false, cmdAgregarTramo, accesoGrabar());
		Util.disabledBtnAgregar(true, cmdAddTerPartida, accesoGrabar());
		Util.disabledBtnAgregar(true, cmdAddTerLlegada, accesoGrabar());
		Util.seleccionarValorItemCombo(TipoItinerario.class, cmbTipoItinerario, Constantes.ID_TIPITI_REGULAR);
		lstItinerariosDetalle = null;
		oitinerario=null;
		esEdicionTramo=false;
	}
	
	public void limpiaItinerario(){
		cmbRuta.getItems().clear();
		lbxTerminalOrigen.getItems().clear();
		bbxTerminalOrigen.setValue("");
		lbxTerminalDestino.getItems().clear();
		bbxTerminalDestino.setValue("");
		cmbTerminalPartida.getItems().clear();
		cmbTerminalLlegada.getItems().clear();
		tbHoraPartida.setValue(null);
		tbterHoraPartida.setValue(null);
		tbterHoraLlegada.setValue(null);
	}
		
	public void limpiarTodasLasListas(){
		Util.limpiarListbox(lbxDetalleItinerario);
		Util.limpiarListbox(lbxTerminalPartida);
		Util.limpiarListbox(lbxTerminalLlegada);
		Util.limpiarListbox(lbxTerminalOrigen, false);
		Util.limpiarListbox(lbxTerminalDestino, false);
//		Util.limpiarGrid(grdRutasTrifas);
	}
	
	public void cargaComboDefaul(){
		limpiaItinerario();
		UtilData.cargarGenericData(cmbRuta, false);
//		UtilData.cargarGenericData(cmbTermOrigen, false);
//		UtilData.cargarGenericData(cmbTermDestino, false);
		UtilData.cargarGenericData(cmbTerminalPartida, false);
		UtilData.cargarGenericData(cmbTerminalLlegada, false);
	}
	
	public void LimpiaCombos(){
		cmbTipoItinerario.setSelectedIndex(0);
		cmbServicio.setSelectedIndex(0);
		cmbOrigen.setSelectedIndex(0);
		cmbRuta.setSelectedIndex(0);
//		cmbTermOrigen.setSelectedIndex(0);
//		cmbTermDestino.setSelectedIndex(0);
	}
	

	
	/*
	 * (non-Javadoc)
	 * @see com.tepsa.sisvyr.view.ui.IOpcionesMantenimiento#onSearch()
	 */
	@Override
	public void onSearch() {
		final WndFiltrarParametros oWndFiltrar = new WndFiltrarParametros();

		oWndFiltrar.addParameter("1. Origen", Localidad.class);
		oWndFiltrar.addParameter("2. Destino", Localidad.class);
		oWndFiltrar.addParameter("3. Fecha Inicio", Datebox.class);
		oWndFiltrar.addParameter("4. Fecha Final", Datebox.class);
		oWndFiltrar.addParameter("5. Servicio", Servicio.class);
		oWndFiltrar.addParameter("6. Nro. Itinerario", Intbox.class);
				
		
		this.appendChild(oWndFiltrar);
		oWndFiltrar.setMode("modal");
		oWndFiltrar.addEventListener(com.cystesoft.vyrbus.view.ui.Events.ON_FILTER, new EventListener<Event>() {
			@Override
			public void onEvent(Event event) throws Exception {
				try{
					sOrigen ="";	
					sDestino ="";	
					sServicio = ""; 
					iItinerario = null;
					tipoDeItinerario="";
					criterioOrden="di.d_fecpar, to_date(di.c_horpar,'HH24:MI'), di.d_feclle, to_date(di.c_horlle,'HH24:MI')";
					
					if (oWndFiltrar.getParameterValue("1. Origen") instanceof Localidad){
						Localidad origen = (Localidad) oWndFiltrar.getParameterValue("1. Origen");
						sOrigen = origen.getDenominacion();
					}
					if (oWndFiltrar.getParameterValue("2. Destino") instanceof Localidad){
						Localidad destino = (Localidad) oWndFiltrar.getParameterValue("2. Destino");
						sDestino = destino.getDenominacion();
					}
					if (oWndFiltrar.getParameterValue("5. Servicio") instanceof Servicio ){
						Servicio oservicio = (Servicio) oWndFiltrar.getParameterValue("5. Servicio");
						sServicio = oservicio.getDenominacion();
					}
										
					fechaInicio = (Date) oWndFiltrar.getParameterValue("3. Fecha Inicio");
					fechaFinal = (Date) oWndFiltrar.getParameterValue("4. Fecha Final");
					Integer itinerario = (Integer) oWndFiltrar.getParameterValue("6. Nro. Itinerario");
					if (itinerario !=null)
						iItinerario=itinerario.longValue();
					
					listarItinerarios(ServiceLocator.getItinerarioManager().buscarItinerariosMantenimiento(iItinerario, sOrigen, 
							sDestino, Constantes.FORMAT_DATE.format(fechaInicio), Constantes.FORMAT_DATE.format(fechaFinal),
							sServicio,tipoDeItinerario, criterioOrden));	
			
				}catch (Exception ex){
					DlgMessage.error(this.getClass().getName()+" "+ex.getMessage());
					ex.printStackTrace();
				}
			}
		});
		
	}

	/*
	 * (non-Javadoc)
	 * @see com.tepsa.sisvyr.view.ui.IOpcionesMantenimiento#onRefresh(int)
	 */
	@Override
	public void onRefresh(int tab) {
		if (!criteriosBusqueda.isEmpty()) {
			//ListaItinerarios(ServiceLocator.getFormaPagoManager().buscarPorX(criteriosBusqueda, criteriosOrdenar));					
		}
	}

	/*
	 * (non-Javadoc)
	 * @see com.tepsa.sisvyr.view.ui.IOpcionesMantenimiento#onModify(int)
	 */
	@Override
	public void onModify(int tab) throws Exception {
		cargaComboDefaul();
		LimpiaCombos();
		limpiarTodasLasListas();
		Long id=((DetalleItinerario)listboxLista.getSelectedItem().getValue()).getItinerario().getId();
		manteniemientoItinerario(id);
		Itinerario itinerario=ServiceLocator.getItinerarioManager().buscarPorId(id);
		
		secuenciaTramo=itinerario.getSecuenciaTramo(); 
		horaPartida=itinerario.getHoraPartida();
		idRuta=itinerario.getRuta().getId();
		idServicio=itinerario.getServicio().getId();
		idTipoItinerario=itinerario.getTipoItinerario().getId();
		idAgenciaPartida=itinerario.getAgenciaPartida().getId();
		idagenciaLlegada=itinerario.getAgenciaLlegada().getId();
					
//		CargarRutasTarifas();
		dbFechaInicio.setDisabled(true);
		HabilitaBotones_AgredarTramo(false);
		action=Constantes.ACTION_MODIFY;
		listVentasNoUpdate=new ArrayList<VentaPasaje>();
		btnGuardar.setDisabled(false);
			
		//***Valida si la fecha del itinerario corresponde a la fecha actual****
		MyTime time = new MyTime();
		Date date=Constantes.FORMAT_DATE.parse(time.dateServer());
		if (date.getTime() > oitinerario.getFechaPartida().getTime()){
			btnGuardar.setDisabled(true);
			habilitaControles(false);
			HabilitaBotones_AgredarTramo(true);
			DlgMessage.information(Messages.getString("WndItinerario.information.EditarItinerario"));
		}else{
			//**Valida si el itinerario ya fue programado.
			TreeMap<String, Object>criteriosBusqueda=new TreeMap<String,Object>();
			criteriosBusqueda.put("itinerario", itinerario);
			criteriosBusqueda.put("estadoRegistro", Constantes.VALUE_ACTIVO);
			List<ProgramacionServicio>lstProgramacion=ServiceLocator.getProgramacionServiciosManager().buscarPorX(criteriosBusqueda, null);
			if(lstProgramacion.size()>0){
				btnGuardar.setDisabled(true);
				habilitaControles(false);
				HabilitaBotones_AgredarTramo(true);
				DlgMessage.information(Messages.getString("WndItinerario.information.itineararioProgramado"));
			}
		}
		
		Util.disabledBtnAgregar(btnGuardar.isDisabled(), cmdAgregarTramo, accesoGrabar());
		Util.disabledBtnAgregar(true, cmdAddTerPartida, accesoGrabar());
		Util.disabledBtnAgregar(true, cmdAddTerLlegada, accesoGrabar());
		asignarImagenEliminarTramo();
//		asignarImagenEliminarTerminal(lbxTerminalPartida);
//		asignarImagenEliminarTerminal(lbxTerminalLlegada);
	}

	/*
	 * (non-Javadoc)
	 * @see com.tepsa.sisvyr.view.ui.IOpcionesMantenimiento#onCancel(int)
	 */
	@Override
	public void onCancel(int action) {
		limpiarTodasLasListas();
		LimpiaCombos();
		lstItinerariosDetalle = null;
		oitinerario = null;
		esEdicionTramo=false;
	}

	/*
	 * (non-Javadoc)
	 * @see com.tepsa.sisvyr.view.ui.IOpcionesMantenimiento#onSave(int)
	 */
	@Override
	public void onSave(int action) throws Exception {
		try {
			if (lbxDetalleItinerario.getItems().size() <= 0 )
				throw new ItinerarioException(ItinerarioException.ITINERARIO_NULL); 
			else if (lbxTerminalPartida.getItems().size() <= 0)
				throw new ItinerarioException(ItinerarioException.AGENCIA_PARTIDA_NULL); 
			else if (lbxTerminalLlegada.getItems().size() <= 0)
				throw new ItinerarioException(ItinerarioException.AGENCIA_LLEGADA_NULL); 
			
			/*Realiza la validacion de los tiempos entre el origen y destino - 04/01/2017 - jabanto*/
			if(validarDiferenciaHorasTramo(null)==false)
				throw new CancelaGrabacionException();
			
			Boolean flag=true;
			save(action);
			
			if(flag)
				throw new CancelaGrabacionException();			
		}catch(ItinerarioException ine){
			if(ine.getTipo().intValue()==ItinerarioException.ITINERARIO_NULL){
				DlgMessage.information(Messages.getString("WndItinerario.information.ItinerarioNull"));
				 throw new CancelaGrabacionException();
			}else if (ine.getTipo().intValue()==ItinerarioException.AGENCIA_LLEGADA_NULL){
				DlgMessage.information(Messages.getString("WndItinerario.information.ItinerarioAgenciaLlegadaNull"));
				 throw new CancelaGrabacionException();
			}else if (ine.getTipo().intValue()==ItinerarioException.AGENCIA_PARTIDA_NULL){
				DlgMessage.information(Messages.getString("WndItinerario.information.ItinerarioAgenciaPartidaNull"));
				 throw new CancelaGrabacionException();
			}
		}catch (CancelaGrabacionException ss){
			 throw new CancelaGrabacionException();
		}catch (Exception ex) {
			DlgMessage.error(this.getClass().getName()+" "+ex.getMessage()); 
			ex.printStackTrace(); throw new CancelaGrabacionException();
		}		
	}
	
	private void save(int action){
		final int  actionf=action;	
		listVentasNoUpdate=new ArrayList<VentaPasaje>();

		Messagebox.show(Messages.getString(action==ACTION_NEW?
											  dbFechafin.getValue().getTime()>dbFechaInicio.getValue().getTime()?"WndItinerario.Question.ConfirmaInserts"
												 : "WndItinerario.Question.ConfirmaInsert"	 
											   : dbFechafin.getValue().getTime()>dbFechaInicio.getValue().getTime()? "WndItinerario.Question.ConfirmarUpdates"	 
												 :"WndItinerario.Question.ConfirmarUpdate"), 
					DlgMessage.NOMBREAPLICACION, DlgMessage.BTN_YESNO, Messagebox.QUESTION,DlgMessage.BTN_DEFAULT_NO, new EventListener<Event>() {
				@Override
				public void onEvent(Event e) throws Exception {
					if(e.getName().equals("onYes")){
						try{
							if(actionf==ACTION_MODIFY){
								generaItinerarios(actionf,buscaItinerariosActualizar());
								
								listarItinerarios(ServiceLocator.getItinerarioManager().buscarItinerariosMantenimiento(iItinerario, sOrigen, 
										sDestino, Constantes.FORMAT_DATE.format(fechaInicio), Constantes.FORMAT_DATE.format(fechaFinal),
										sServicio,tipoDeItinerario, criterioOrden));
							}else{
								generaItinerarios(actionf,null);
								if(listboxLista.getItems().size()==0){
									fechaInicio=dbFechaInicio.getValue();
									fechaFinal=dbFechafin.getValue();
									sOrigen=oitinerario.getRuta().getOrigen();
									sDestino=oitinerario.getRuta().getDestino();
									sServicio=oitinerario.getServicio().getDenominacion();
																			
									listarItinerarios(ServiceLocator.getItinerarioManager().buscarItinerariosMantenimiento(iItinerario, sOrigen, 
											sDestino, Constantes.FORMAT_DATE.format(fechaInicio), Constantes.FORMAT_DATE.format(fechaFinal),
											sServicio,tipoDeItinerario, criterioOrden));
								}
							}
							
							btnNuevo.setDisabled(accesoNuevo()?false:true);
							btnBuscar.setDisabled(accesoConsultar()?false:true);
							btnImprimir.setDisabled(accesoImprimir()?false:true);
							btnExportar.setDisabled(accesoExportar()?false:true);
							btnRefrescar.setDisabled(false);
							btnModificar.setDisabled(true);
							btnCancelar.setDisabled(true);
							btnGuardar.setDisabled(true);
							btnEliminar.setDisabled(true);
							btnCerrar.setDisabled(false);
							oTabbox.setSelectedIndex(0);
									
							Util.limpiarListbox(lbxDetalleItinerario);
							Util.limpiarListbox(lbxTerminalPartida);
							Util.limpiarListbox(lbxTerminalLlegada);
//							Rows Rows = new Rows();
//							grdRutasTrifas.getRows().detach();
//							grdRutasTrifas.appendChild(Rows);
												
							lstItinerariosDetalle = null;
							oitinerario=null;
							esEdicionTramo=false;
							
							if(actionf==ACTION_MODIFY){
								if(listVentasNoUpdate.size()>0)
									ventanaItinerarios(listVentasNoUpdate);
							}
							
					}catch (ItinerarioException itv ){
						if(itv.getTipo().intValue()==ItinerarioException.CON_VENTAS)
							DlgMessage.information(Messages.getString("WndItinerario.information.ItinerarioTieneVentas"));
					}
				}
			}
		});
	}
	
	/**
	 * GENERA ITINERARIOS
	 * @param listItinVent : lista de itinerarios que seran actualizados, esta lista tambien contiene las ventas de cada itinerario, si es que este lo tuviese .
	 * @param action
	 * @throws Exception
	 */
	@SuppressWarnings("unused")
	public void generaItinerarios(int action, List<VentaPasaje> listItinVent) throws Exception{
	//	if (dbFechafin.getValue().getTime()>dbFechaInicio.getValue().getTime()){
			List<Itinerario> lstItinerariosUpdate=null;
			if(action==ACTION_MODIFY){
				/*la lista (listItinVent) puede contener el idItinerario duplicado, ya esta contiene el detalle del itinerario
				 * por lo que en este preso de elimina los idItinerarios duplicados.*/
				lstItinerariosUpdate=new ArrayList<Itinerario>();
				for(VentaPasaje venta: listItinVent){
					Itinerario itinerario=venta.getItinerario();
					if(lstItinerariosUpdate.size()==0)
						lstItinerariosUpdate.add(itinerario);
					else{
						Boolean itinerarioEncontrado=false;
						for(Itinerario itine: lstItinerariosUpdate){
							if(itine.getId().equals(itinerario.getId())){
								itinerarioEncontrado=true;
								break;
							}
						}
						if(itinerarioEncontrado==false)
							lstItinerariosUpdate.add(itinerario);
					}
				}
			}
			
			Long cantidadDias=((dbFechafin.getValue().getTime()+Constantes.MILISEGUNDOS_X_DIA)-dbFechaInicio.getValue().getTime());
			Date fechaPartida=new Date();				
			fechaPartida.setTime(dbFechaInicio.getValue().getTime());
			Integer dias = (int) (cantidadDias /  Constantes.MILISEGUNDOS_X_DIA);	
			
			if(dias>0){
				for (int x=0 ; x < dias; x++){
					Listbox lbxDetalleItinerario = new Listbox();
					Calendar FechaLLegada = Calendar.getInstance();					
					
					if(action==ACTION_MODIFY){//Si es una actualizacion
						for(int i=x; i<lstItinerariosUpdate.size(); i++){
							Boolean updateItinerario=true; //Variable utilizada para validar si un itinerario sera o no actualizado.()
							Itinerario itinerario=lstItinerariosUpdate.get(i);
							String fPartidaItiUpdate=Constantes.FORMAT_DATE.format(itinerario.getFechaPartida());
							String fPartidaCorr=Constantes.FORMAT_DATE.format(fechaPartida);
							
							for(VentaPasaje venta: listVentasNoUpdate){//Valida que el itinerario a actualizar no tenga ventas.
								if(itinerario.getId().equals(venta.getItinerario().getId())){
									updateItinerario=false;
									break;
								}
							}
							if(updateItinerario==true && fPartidaItiUpdate.equals(fPartidaCorr)){/*Actualiza el Itinerario*/
								textboxId.setText(itinerario.getId().toString());
								oitinerario.setId(itinerario.getId());
								guardarItinerarios(action, fechaPartida, FechaLLegada, lbxDetalleItinerario, oitinerario);
							}
							break;
						}
						
					}else //Si es un nuevo registro
						guardarItinerarios(ACTION_NEW, fechaPartida, FechaLLegada, lbxDetalleItinerario, oitinerario);
					
					fechaPartida.setTime(fechaPartida.getTime() + Constantes.MILISEGUNDOS_X_DIA );
				}

			}else{ //Cuando es solamente una Día 
				Listbox lbxDetalleItinerario = new Listbox();
				Calendar FechaLLegada = Calendar.getInstance();
				
				if(action==ACTION_MODIFY ) {//Si es una actualizacion
					if(lstItinerariosUpdate.size()==1 && listVentasNoUpdate.size()==0){// si el itinerario a actualizar no tiene ventas
						Itinerario itinerario=lstItinerariosUpdate.get(0);
						textboxId.setText(itinerario.getId().toString());
						oitinerario.setId(itinerario.getId());
						guardarItinerarios(action, fechaPartida, FechaLLegada, lbxDetalleItinerario, oitinerario);
					}
				}else{//Si es un registro nuevo
					guardarItinerarios(ACTION_NEW, fechaPartida, FechaLLegada, lbxDetalleItinerario, oitinerario);
				}
				
			}
			
	}
			
	/**
	 * Busca la lista de itinerarios a actualizar y las ventas de cada itinerario, si es que los tuviese.
	 * @return
	 * @throws Exception
	 */
	private List<VentaPasaje> buscaItinerariosActualizar() throws Exception{
		String fechaInicio=Constantes.FORMAT_DATE.format(dbFechaInicio.getValue());
		String fechaFin=Constantes.FORMAT_DATE.format(dbFechafin.getValue());		
		List<VentaPasaje>list=ServiceLocator.getItinerarioManager().buscarItinerariosAActualizar(fechaInicio, fechaFin, idRuta, secuenciaTramo, horaPartida, idServicio, idTipoItinerario, idAgenciaPartida, idagenciaLlegada);
		
		for(VentaPasaje ventas: list){
			Ruta ruta=ventas.getRuta();
			if(ventas.getId()!=null){//Valida si el Itinerario tiene ventas.
				//Compara si hay rutas suprimidas en la actualizacion del Itinerario.
				Boolean rutaSuprimida=true;
				for(int i=0; i<lbxDetalleItinerario.getItems().size(); i++) {
					Listitem it = lbxDetalleItinerario.getItems().get(i);
					Integer idRutaActual = ((DetalleItinerario)it.getValue()).getRuta().getId();
					if(idRutaActual.equals(ruta.getId())){
						rutaSuprimida=false;
						break;
					}
				}
				if(rutaSuprimida==true)
					listVentasNoUpdate.add(ventas);
//				for(int y=0; y<grdRutasTrifas.getRows().getChildren().size();y++){
//					Row it = (Row)grdRutasTrifas.getRows().getChildren().get(y);
//					Integer idRutaActual=(new Integer(((Label)it.getChildren().get(0)).getValue()));
//					if(idRutaActual.equals(ruta.getId())){
//						rutaSuprimida=true;
//						break;
//					}	
//				}
//				if(rutaSuprimida==false)
//					listVentasNoUpdate.add(ventas);
			}
		}
		return list;
	}
	
	/**
	 * Guarda el Itineracario 	
	 * @param action				: nuevo o actualizacion		
	 * @param fechaPartida			: Fecha de partida del itinerario
	 * @param FechaLLegada			: Fecha de llegada del itinerario
	 * @param lbDetalleItinerario	: lista con el detalle del itinerario a guarda 
	 * @param itinerario			: Class Itinerario
	 * @throws Exception
	 */
	@SuppressWarnings({ "deprecation", "static-access" })
	private void guardarItinerarios(int action, Date fechaPartida, Calendar fechaLLegada, Listbox lbDetalleItinerario, Itinerario itinerario) throws Exception{
		for (int y=0; y < lbxDetalleItinerario.getItems().size(); y ++){
			Listitem itemDetalleItinerario = lbxDetalleItinerario.getItemAtIndex(y);
			DetalleItinerario odetalleItinerario = new DetalleItinerario();
			Timebox tbhoraPartida = new Timebox();
			Date sfechaPartida = new Date();
			tbhoraPartida.setFormat("HH:mm");
			tbhoraPartida.setText((((DetalleItinerario) itemDetalleItinerario.getValue()).getHoraPartida()));
			
			Double horasViaje = ((DetalleItinerario) itemDetalleItinerario.getValue()).getRuta().getHorasViaje();
			if (y==0)
				sfechaPartida.setTime(fechaPartida.getTime());
			else
				sfechaPartida.setTime(fechaLLegada.getTime().getTime());
			
			/*Obtiene la Fecha y hora de llegada*/
			Calendar fechaHoraLLegada = Calendar.getInstance();
			fechaHoraLLegada.setTimeInMillis(sfechaPartida.getTime());
			fechaHoraLLegada.set(Calendar.MONTH, sfechaPartida.getMonth());
			fechaHoraLLegada.set(Calendar.HOUR_OF_DAY, tbhoraPartida.getValue().getHours());
			fechaHoraLLegada.set(Calendar.MINUTE, tbhoraPartida.getValue().getMinutes());
			fechaHoraLLegada.set(Calendar.SECOND, tbhoraPartida.getValue().getSeconds());
			fechaHoraLLegada.add(Calendar.MILLISECOND, (Util.horasMinutos(horasViaje).intValue()));
									
			/*Asigna solamente la fecha llegada en formato "dd/MM/yyyy"**/
			fechaLLegada.setTime(fechaHoraLLegada.getTime());
			fechaLLegada.set(Calendar.HOUR_OF_DAY, 0);
			fechaLLegada.set(Calendar.MINUTE, 0);
			fechaLLegada.set(Calendar.SECOND, 0);

			odetalleItinerario.setId((long) 0);
			odetalleItinerario.setItinerario(itinerario);
			odetalleItinerario.setRuta(((DetalleItinerario) itemDetalleItinerario.getValue()).getRuta());
			odetalleItinerario.setAgenciaPartida(((DetalleItinerario) itemDetalleItinerario.getValue()).getAgenciaPartida());
			odetalleItinerario.setFechaPartida(sfechaPartida);
			odetalleItinerario.setHoraPartida(((DetalleItinerario) itemDetalleItinerario.getValue()).getHoraPartida());
			odetalleItinerario.setAgenciaLlegada(((DetalleItinerario) itemDetalleItinerario.getValue()).getAgenciaLlegada());
			odetalleItinerario.setFechaLlegada(fechaLLegada.getTime());
			odetalleItinerario.setHoraLlegada(((DetalleItinerario) itemDetalleItinerario.getValue()).getHoraLlegada());
			odetalleItinerario.setTarifa(((DetalleItinerario) itemDetalleItinerario.getValue()).getTarifa());
			odetalleItinerario.setEstadoRegistro(Constantes.VALUE_ACTIVO);
			odetalleItinerario.setLstItinerarioAgenciaPartida(((DetalleItinerario)itemDetalleItinerario.getValue()).getLstItinerarioAgenciaPartida());
			odetalleItinerario.setLstItinerarioAgenciaLlegada(((DetalleItinerario)itemDetalleItinerario.getValue()).getLstItinerarioAgenciaLlegada());
			
			/*Agrega el tramo, para recuperarlo al momneto del guardado*/
			agregarItinerario(odetalleItinerario, lbDetalleItinerario);
		}
		this.guardaItineratio(action,lbDetalleItinerario);
		this.guardaItinerarioDetalle(action,lbDetalleItinerario);
		this.guardaAgenciaPartida(action);
		this.guardaAgenciaLlegada(action);
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.tepsa.sisvyr.view.ui.IOpcionesMantenimiento#onDelete(int)
	 */
	@Override
	public void onDelete(int tab) throws Exception {
		try{
			/*Obtiene el identificador del itinerario*/
			Long id = ((DetalleItinerario)listboxLista.getSelectedItem().getValue()).getItinerario().getId();
			
			/*Valida si este esta asociado a una HRE*/
			criteriosBusqueda=new TreeMap<String, Object>();
			criteriosBusqueda.put("itinerario", new Itinerario(id));
			criteriosBusqueda.put("estadoRegistro", Constantes.VALUE_ACTIVO);
			List<HRE> lstHre= ServiceLocator.getHREManager().buscarPorX(criteriosBusqueda, null);
			if(lstHre.size()>0){
				DlgMessage.information(Messages.getString("WndItinerario.information.HREAsociado"));
				throw new NoRemoverRegistroDelListBox();
			}
							
			/*Anula itinerario*/
			ServiceLocator.getItinerarioManager().inactivar(id,getUsuario().getId());
			
			/*Anula la programacion, si es que tubiene uno activo*/
			criteriosBusqueda=new TreeMap<String, Object>();
			criteriosBusqueda.put("itinerario", new Itinerario(id));
			criteriosBusqueda.put("estadoRegistro", Constantes.VALUE_ACTIVO);
			List<ProgramacionServicio> lstProgramacion= ServiceLocator.getProgramacionServiciosManager().buscarPorX(criteriosBusqueda, null);
			if(lstProgramacion.size()>0){
				ProgramacionServicio programacionServicio=lstProgramacion.get(0);
				programacionServicio.setEstadoRegistro(Constantes.VALUE_INACTIVO);
				UtilData.auditarRegistro(programacionServicio, true, getUsuario(), Executions.getCurrent());
				ServiceLocator.getProgramacionServiciosManager().actualizar(programacionServicio);
			}
						
			/*Anula el manifiesto, si es que tuviese una activo.*/
			criteriosBusqueda=new TreeMap<String, Object>();
			criteriosBusqueda.put("itinerario", new Itinerario(id));
			criteriosBusqueda.put("estadoRegistro", Constantes.VALUE_ACTIVO);
			List<Manifiesto> lstManifiesto= ServiceLocator.getManifiestoManager().buscarPorX(criteriosBusqueda, null);
			if(lstManifiesto.size()>0){
				Manifiesto manifiesto=lstManifiesto.get(0);
				manifiesto.setEstadoRegistro(Constantes.VALUE_INACTIVO);
				UtilData.auditarRegistro(manifiesto, true, getUsuario(), Executions.getCurrent());
				ServiceLocator.getManifiestoManager().actualizar(manifiesto);
			}
			
			
		}catch(ItinerarioException itvex){
			if(itvex.getTipo().intValue()==ItinerarioException.CON_VENTAS){
				DlgMessage.information(Messages.getString("WndItinerario.information.itinerarioConVentas"));
				throw new NoRemoverRegistroDelListBox();
			}
		}
	}

	/*
	 * (non-Javadoc)
	 * @see com.tepsa.sisvyr.view.ui.IOpcionesMantenimiento#onPrint(int)
	 */
	@Override
	public void onPrint(int tab) {
		// TODO Auto-generated method stub
	}

	/*
	 * (non-Javadoc)
	 * @see com.tepsa.sisvyr.view.ui.IOpcionesMantenimiento#onExport(int)
	 */
	@Override
	public void onExport(int tab) {
		// TODO Auto-generated method stub
	}

	/*
	 * (non-Javadoc)
	 * @see org.zkoss.zul.Window#onClose()
	 */
	@Override
	public void onClose() {
		closeTabWindow();
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.tepsa.sisvyr.view.ui.IOpcionesMantenimiento#onHelp()
	 */
	@Override
	public void onHelp() {
		// TODO Auto-generated method stub
		
	}

	/*
	 * (non-Javadoc)
	 * @see com.tepsa.sisvyr.view.ui.IOpcionesMantenimiento#onChangeTab(int)
	 */
	@Override
	public void onChangeTab(int tab) throws Exception {
		cargaComboDefaul();
		LimpiaCombos();
		limpiarTodasLasListas();
		if (!(listboxLista.getSelectedIndex() == -1)){
			manteniemientoItinerario(((DetalleItinerario)listboxLista.getSelectedItem().getValue()).getItinerario().getId()); 
//			CargarRutasTarifas();
			Util.disabledBtnAgregar(true, cmdAgregarTramo, accesoGrabar());
			Util.disabledBtnAgregar(true, cmdAddTerPartida, accesoGrabar());
			Util.disabledBtnAgregar(true, cmdAddTerLlegada, accesoGrabar());
		}
	}

//	/**
//	 * 
//	 * @param comboBox	: Objeto donde se cargan los terminales destino, según la ruta seleccionada. 
//	 * @throws Exception
//	 */
//	public void onchangeTerminalDestino(Combobox comboBox) throws Exception{
//		comboBox.getItems().clear();
//		if (cmbRuta.getSelectedItem().getValue() instanceof Ruta){
//			Ruta oruta = new  Ruta();
//			 oruta.setId((Integer) ((Ruta) cmbRuta.getSelectedItem().getValue()).getLocalidadDestino().getId());
//			Integer destino = oruta.getId();
//			 
//			cargarTerm(comboBox, false, destino);
//		}else{
//			UtilData.cargarGenericData(comboBox, false);
//		}
//		
//		if(comboBox.getItems().size()==2)
//			comboBox.setSelectedIndex(1);
//		else
//			comboBox.setSelectedIndex(0);	
//	}
	
	public void onChangeTerminalDestino(Listbox listBox) throws Exception{
		listBox.getItems().clear();
		if (cmbRuta.getSelectedItem().getValue() instanceof Ruta){
			Ruta oruta = new  Ruta();
			oruta.setId(((Ruta) cmbRuta.getSelectedItem().getValue()).getLocalidadDestino().getId());
			Integer destino = oruta.getId();			 
			cargarAgenciaOrigenDestino(listBox, false, destino);
		}
//		else{
//			UtilData.cargarGenericData(comboBox, false);
//		}
//		
//		if(comboBox.getItems().size()==2)
//			comboBox.setSelectedIndex(1);
//		else
//			comboBox.setSelectedIndex(0);	
	}
	
	/**
	 * 	Carga las rutas, según el Origen seleccionado.
	 * @throws Exception
	 */
	public  void onChangeRutas() throws Exception{
		cmbRuta.getItems().clear();	
//		cmbTermOrigen.getItems().clear();
//		cmbTermDestino.getItems().clear();
		
		lbxTerminalOrigen.getItems().clear();
		lbxTerminalDestino.getItems().clear();
		
		if (cmbOrigen.getSelectedItem().getValue() instanceof Localidad){
			Integer Origen = ((Localidad) cmbOrigen.getSelectedItem().getValue()).getId();
			UtilData.cargarRutas(cmbRuta, false, Origen,null);
//			cargarTerm(cmbTermOrigen,false,Origen);
			cargarAgenciaOrigenDestino(lbxTerminalOrigen, false, Origen);
//			UtilData.cargarGenericData(cmbTermDestino, false);
		}else{
			UtilData.cargarGenericData(cmbRuta, false);
			Util.limpiarListbox(lbxTerminalOrigen);
			Util.limpiarListbox(lbxTerminalDestino);
			bbxTerminalOrigen.setValue("");
			bbxTerminalDestino.setValue("");
			lstAgenciaPartida.clear();
			lstAgenciaLlegada.clear();
//			UtilData.cargarGenericData(cmbTermOrigen, false);
//			UtilData.cargarGenericData(cmbTermDestino, false);
		}		
		cmbRuta.setSelectedIndex(0);		
//		if(cmbTermOrigen.getItems().size()== 2)
//			cmbTermOrigen.setSelectedIndex(1);
//		else
//			cmbTermOrigen.setSelectedIndex(0);
		if(lbxTerminalOrigen.getItems().size()==1)
			lbxTerminalOrigen.getItemAtIndex(0).setSelected(true);
		
//		cmbTermDestino.setSelectedIndex(0);
	}

	/**
	 * Agregar el tramo
	 * @throws Exception
	 */
	@SuppressWarnings({ "deprecation", "static-access"})
	public void agregarTramo() throws Exception {
		try {
			/*
			 * Para la validacion del itinerario.
			 */
			Listitem itemvDetalleItinerario=null;
			if (esEdicionTramo==false){
				if (lbxDetalleItinerario.getItems().size() > 0){
					/*Cuando NO es una edición de un Tramo*/
					itemvDetalleItinerario = lbxDetalleItinerario.getItemAtIndex(lbxDetalleItinerario.getItems().size() -1);
				}
			}else{
				/*Cuando es una edición de un Tramo*/
				itemvDetalleItinerario = lbxDetalleItinerario.getItemAtIndex(indexEdicionTramo -1);
			}
			
						
			if (!(cmbTipoItinerario.getSelectedItem().getValue() instanceof TipoItinerario))
				throw new TipoItinerarioNullException();
			else if (!(cmbServicio.getSelectedItem().getValue() instanceof Servicio))
				throw new ServicioNullException();
			else if (!(cmbOrigen.getSelectedItem().getValue() instanceof Localidad))
				throw new LocalidadNullException(LocalidadNullException.ORIGEN);
			else if (!(cmbRuta.getSelectedItem().getValue() instanceof Ruta))
				throw new RutaNullException();
			else if (dbFechaItinerario.getText().equals(""))
				throw new FechaItinerarioNullException();
			else if (tbHoraPartida.getText().equals(""))
				throw new HoraPartidaNullException();
			else if (bbxTerminalOrigen.getValue().equals(""))
				throw new TerminalOrigenNullException();
			else if (bbxTerminalDestino.getValue().equals(""))
				throw new TerminalDestinoNullException();
			else if(lbxTerminalOrigen.getSelectedItems().size()==0)
				throw new TerminalOrigenNullException();
//			else if (!(cmbTermOrigen.getSelectedItem().getValue() instanceof Agencia))
//				throw new TerminalOrigenNullException();
//			else if (!(cmbTermDestino.getSelectedItem().getValue() instanceof Agencia))
//				throw new TerminalDestinoNullException();
			else if(lbxTerminalDestino.getSelectedItems().size()==0)
				throw new TerminalDestinoNullException();
			//***Comentado temporalmente 04/11/2013 a solicitud de marco****/
//			else if (!(Constantes.FORMAT_DATE.parse(dbFechaItinerario.getText()).getTime() >=
//					Constantes.FORMAT_DATE.parse(new MyTime().dateServer()).getTime())){
////					ServiceLocator.getItinerarioManager().fechaServer().getTime().getTime())){
//				throw new ItinerarioException(ItinerarioException.FECHA_MENOR); 
//			}
			
			/*Valida el ingreso de tramos en la edicion*/
			else if (itemvDetalleItinerario != null){
				/*Valida que el Tipo de Servicio sea el mismo de los demas tramos*/
				if (!((DetalleItinerario) itemvDetalleItinerario.getValue()).getItinerario().getServicio().getId().equals((((Servicio) cmbServicio.getSelectedItem().getValue()).getId()))){
					throw new ItinerarioException(ItinerarioException.TIPO_SERVICIO_DIFERENTE); 
				}else
					/*Valida que el tipo de itinerario sea el mismo de los demas tramos*/
				if (((DetalleItinerario) itemvDetalleItinerario.getValue()).getItinerario().getTipoItinerario().getId() != 
					((TipoItinerario) cmbTipoItinerario.getSelectedItem().getValue()).getId()){
						throw new ItinerarioException(ItinerarioException.TIPO_ITINERARIO_DIFERENTE); 
				}else
				/*Valida que el Origen sea el destino del tramo anterior al editado*/
				if (!((DetalleItinerario) itemvDetalleItinerario.getValue()).getRuta().getLocalidadDestino().getId().equals(((Localidad) cmbOrigen.getSelectedItem().getValue()).getId())){
					throw new ItinerarioException(ItinerarioException.TRAMOS_DIFERENTES);	
				}else
				/*Valida que la Hora no sea menor a la del tramo anterior al editado*/
				if (!(Constantes.FORMAT_DATE.parse(dbFechaItinerario.getText()).getTime() >= 
					((DetalleItinerario) itemvDetalleItinerario.getValue()).getFechaLlegada().getTime())) {
					throw new ItinerarioException(ItinerarioException.FECHA_NEX_TRAMO_MENOR); 
				}else
				/*Valida que la Fecha no sea menor a la del tramo anterior al editado*/
				if (!(Constantes.FORMAT_TIME.parse(tbHoraPartida.getText()).getTime() >= 
					Constantes.FORMAT_TIME.parse(((DetalleItinerario) itemvDetalleItinerario.getValue()).getHoraLlegada()).getTime())){
						throw new ItinerarioException(ItinerarioException.HORA_NEX_TRAMO_MENOR); 
				}
			}
			
				
			Ruta oruta= new Ruta();
			
			oruta.setId(((Ruta) cmbRuta.getSelectedItem().getValue()).getId());	
			oruta.setHorasViaje(((Ruta) cmbRuta.getSelectedItem().getValue()).getHorasViaje());
			oruta.setOrigen( ((Ruta) cmbRuta.getSelectedItem().getValue()).getOrigen());
			oruta.setDestino( ((Ruta) cmbRuta.getSelectedItem().getValue()).getDestino());
			
			/*Obtiene la Fecha y hora de llegada*/
			Calendar FechaHoraLLegada = Calendar.getInstance();
			FechaHoraLLegada.setTimeInMillis(dbFechaItinerario.getValue().getTime());
			FechaHoraLLegada.set(Calendar.MONTH, dbFechaItinerario.getValue().getMonth());
			FechaHoraLLegada.set(Calendar.HOUR_OF_DAY, tbHoraPartida.getValue().getHours());
			FechaHoraLLegada.set(Calendar.MINUTE, tbHoraPartida.getValue().getMinutes());
			FechaHoraLLegada.set(Calendar.SECOND, 0);
			FechaHoraLLegada.add(Calendar.MILLISECOND, (Util.horasMinutos(oruta.getHorasViaje()).intValue()));
			//FechaHoraLLegada.add(FechaHoraLLegada.MILLISECOND, (int) (lHorasMinutosViaje.intValue()));
			/**********************************************************************************************************/
			
			/*Asigna solamente la fecha llegada en fomrato dd/MM/yyyy */
			Calendar FechaLLegada = Calendar.getInstance();
			FechaLLegada.setTime(FechaHoraLLegada.getTime());
			FechaLLegada.set(Calendar.HOUR_OF_DAY, 0);
			FechaLLegada.set(Calendar.MINUTE, 0);
			FechaLLegada.set(Calendar.SECOND, 0);
			
			/*Cuando es un nuevo Itinerario*/
			if(action==ACTION_NEW)
				oitinerario = new Itinerario();
			else{
				if(oitinerario!=null){
					boolean permiteEditarServ=ServiceLocator.getItinerarioManager().validaCapasidadServicioAsiento(oitinerario.getId(), ((Servicio)cmbServicio.getSelectedItem().getValue()));
					if(permiteEditarServ)
						throw new CapacidadServicioMayorExeption();
				}
			}
			
			DetalleItinerario odetalleItinerario= new DetalleItinerario();			
			
			Localidad oLocalidadOrigen = new Localidad();
			oLocalidadOrigen.setId(((Ruta) cmbRuta.getSelectedItem().getValue()).getLocalidadOrigen().getId());
			oLocalidadOrigen.setDenominacion(((Ruta) cmbRuta.getSelectedItem().getValue()).getOrigen());
			oruta.setLocalidadOrigen(oLocalidadOrigen);
			
			Agencia	oagenciaPartida = new Agencia();
			oagenciaPartida.setLocalidad(oLocalidadOrigen);
			for(Listitem item : lbxTerminalOrigen.getSelectedItems()) {
				if(tbHoraPartida.getText().equals(((Timebox)item.getChildren().get(1).getChildren().get(0)).getText())) {
					oagenciaPartida.setId(((Agencia)item.getValue()).getId());
					oagenciaPartida.setNombreCorto(((Agencia)item.getValue()).getNombreCorto());
					oagenciaPartida.setDenominacion(((Agencia)item.getValue()).getDenominacion());
				}
			}
//			oagenciaPartida.setId(((Agencia) cmbTermOrigen.getSelectedItem().getValue()).getId());
//			oagenciaPartida.setNombreCorto(((Agencia) cmbTermOrigen.getSelectedItem().getValue()).getNombreCorto());
//			oagenciaPartida.setDenominacion(((Agencia) cmbTermOrigen.getSelectedItem().getValue()).getDenominacion());
			
			Localidad oLocalidadDestino = new Localidad();
			oLocalidadDestino.setId(((Ruta) cmbRuta.getSelectedItem().getValue()).getLocalidadDestino().getId());
			oLocalidadDestino.setDenominacion(((Ruta) cmbRuta.getSelectedItem().getValue()).getDestino());
			oruta.setLocalidadDestino(oLocalidadDestino);
			
			Agencia	oagenciaLlegada= new Agencia();
			oagenciaLlegada.setLocalidad(oLocalidadDestino);
			for(Listitem item : lbxTerminalDestino.getSelectedItems()) {
				oagenciaLlegada.setId(((Agencia)item.getValue()).getId());
				oagenciaLlegada.setNombreCorto(((Agencia)item.getValue()).getNombreCorto());
				oagenciaLlegada.setDenominacion(((Agencia)item.getValue()).getDenominacion());
				break;
			}
//			oagenciaLlegada.setId(((Agencia) cmbTermDestino.getSelectedItem().getValue()).getId());
//			oagenciaLlegada.setNombreCorto(((Agencia) cmbTermDestino.getSelectedItem().getValue()).getNombreCorto());
//			oagenciaLlegada.setDenominacion(((Agencia) cmbTermDestino.getSelectedItem().getValue()).getDenominacion());
			
			Servicio oservicio = new Servicio();
			oservicio.setId(((Servicio) cmbServicio.getSelectedItem().getValue()).getId());
			oservicio.setDenominacion(((Servicio) cmbServicio.getSelectedItem().getValue()).getDenominacion());
			
			TipoItinerario oTipoItinerario = new TipoItinerario();
			oTipoItinerario.setId(((TipoItinerario) cmbTipoItinerario.getSelectedItem().getValue()).getId());
			oitinerario.setTipoItinerario(oTipoItinerario);
			oitinerario.setServicio(oservicio);
			
			odetalleItinerario.setId((long) 0);
			odetalleItinerario.setItinerario(oitinerario);
			odetalleItinerario.setRuta(oruta);
			odetalleItinerario.setAgenciaPartida(oagenciaPartida);
			odetalleItinerario.setFechaPartida(dbFechaItinerario.getValue());
			odetalleItinerario.setHoraPartida(tbHoraPartida.getText());
			odetalleItinerario.setAgenciaLlegada(oagenciaLlegada);
			odetalleItinerario.setFechaLlegada(FechaLLegada.getTime());
			odetalleItinerario.setHoraLlegada(Constantes.FORMAT_TIME.format(FechaHoraLLegada.getTime()));
			odetalleItinerario.setTarifa(0.01);
			odetalleItinerario.setEstadoRegistro(Constantes.VALUE_ACTIVO);
			List<ItinerarioAgenciaPartida> lstItinerarioAgenciaPartida = new ArrayList<ItinerarioAgenciaPartida>();
			for(Agencia agencia : lstAgenciaPartida){
				ItinerarioAgenciaPartida itinerarioAgenciaPartida = new ItinerarioAgenciaPartida();
				itinerarioAgenciaPartida.setItinerario(oitinerario);
				itinerarioAgenciaPartida.setAgencia(agencia);
				itinerarioAgenciaPartida.setHoraPartida(agencia.getHoraPartida());
				itinerarioAgenciaPartida.setEstadoRegistro(Constantes.VALUE_ACTIVO);
				itinerarioAgenciaPartida.setLocalidad(agencia.getLocalidad());
				UtilData.auditarRegistro(itinerarioAgenciaPartida, getUsuario(), Executions.getCurrent());
				lstItinerarioAgenciaPartida.add(itinerarioAgenciaPartida);
			}
			odetalleItinerario.setLstItinerarioAgenciaPartida(lstItinerarioAgenciaPartida);
			
			List<ItinerarioAgenciaLlegada> lstItinerarioAgenciaLlegada = new ArrayList<ItinerarioAgenciaLlegada>();
			for(Agencia agencia : lstAgenciaLlegada){
				ItinerarioAgenciaLlegada itinerarioAgenciaLlegada = new ItinerarioAgenciaLlegada();
				itinerarioAgenciaLlegada.setItinerario(oitinerario);
				itinerarioAgenciaLlegada.setAgencia(agencia);
				itinerarioAgenciaLlegada.setHoraLlegada(agencia.getHoraPartida());
				itinerarioAgenciaLlegada.setEstadoRegistro(Constantes.VALUE_ACTIVO);
				itinerarioAgenciaLlegada.setLocalidad(agencia.getLocalidad());
				UtilData.auditarRegistro(itinerarioAgenciaLlegada, getUsuario(), Executions.getCurrent());
				lstItinerarioAgenciaLlegada.add(itinerarioAgenciaLlegada);
			}
			odetalleItinerario.setLstItinerarioAgenciaLlegada(lstItinerarioAgenciaLlegada);
			
			final DetalleItinerario fodeDetalleItinerario=odetalleItinerario;
//			final Agencia fAgenciaPartida=oagenciaPartida;
//			final Agencia fAgenciaLlegada=oagenciaLlegada;
			final Calendar fFechaHoraLLegada=FechaHoraLLegada;
			
			/*Realiza la validacion de los tiempos entre el origen y destino - 04/01/2017 - jabanto*/
			if(validarDiferenciaHorasTramo(odetalleItinerario)==false)
				return;			
			
			/*Verifica que el tramo no exista en la BD*/
			if(isTramoDuplicado(odetalleItinerario)){
				DetalleItinerario detalleItinerario = listTramoDuplicado.get(0);
				Messagebox.show(Messages.getString("WndItinerario.information.TramosDuplicados")+
						" Nro.Itinerario:" + detalleItinerario.getItinerario().getId()  +
						", Fecha:" + Constantes.FORMAT_DATE.format(detalleItinerario.getFechaPartida()) +
						",  Hora:"+ detalleItinerario.getHoraPartida()+". ¿Desa continuar?",DlgMessage.NOMBREAPLICACION, DlgMessage.BTN_YESNO, Messagebox.QUESTION,DlgMessage.BTN_DEFAULT_NO, new EventListener<Event>() {
					@Override
					public void onEvent(Event e) throws Exception {
						if(e.getName().equals("onYes")){
							/*Llena Tramos****/
							agregarItinerario(fodeDetalleItinerario,lbxDetalleItinerario);	
							/*Valida si se trata de una edición de un Tramo*/
							if (esEdicionTramo==true){
								lbxDetalleItinerario.removeItemAt(indexEdicionTramo);
								if (lbxDetalleItinerario.getItems().size()==1)
									Util.limpiarListbox(lbxTerminalPartida);		
									esEdicionTramo=false;					
								}
							/*Carga Rutas y Tarifas*/
//							CargarRutasTarifas();	
							/*Carga Termianl Partida*/
							if (lbxDetalleItinerario.getItems().size()==1 && lbxTerminalPartida.getItems().size()==0){
								/*Carga Combo Terminal Partida*/
//								cargarTerminalPartidaLlegada(cmbTerminalPartida, false, fodeDetalleItinerario.getRuta().getLocalidadOrigen().getId(), 1);					
//								cargarTerm(cmbTerminalPartida, false, ((Localidad) cmbOrigen.getSelectedItem().getValue()).getId());
								/*Carga ListBox Terminal Partida**/
//								ItinerarioAgenciaPartida oItinerarioAgenciaPartida = new ItinerarioAgenciaPartida();
//								oItinerarioAgenciaPartida.setAgencia(fAgenciaPartida);
//								oItinerarioAgenciaPartida.setHoraPartida(tbHoraPartida.getText());
//								cargaListboxTerminalPartida(oItinerarioAgenciaPartida);
//								HabilitaBotones_TerPartida(false);
								/*Carga Fecha Inicio para la Programación*/
								dbFechaInicio.setText(dbFechaItinerario.getText());
								/*Carga Fecha Fin para la Programación*/
								dbFechafin.setText(dbFechaItinerario.getText()); 	
							}
							
							cargarTerminalPartidaLlegada(cmbTerminalPartida, false, fodeDetalleItinerario.getRuta().getLocalidadOrigen().getId(), 1);
							cargarTerminalPartida(fodeDetalleItinerario.getLstItinerarioAgenciaPartida());
								
							/*Carga la nueva fecha de salida del itinerario*/
							dbFechaItinerario.setText(Constantes.FORMAT_DATE.format(fFechaHoraLLegada.getTime()));
							/*Carga la nueva fecha de salida del itinerario*/
							tbHoraPartida.setText(Constantes.FORMAT_TIME.format(fFechaHoraLLegada.getTime()));
							/*Selecciona el Nuevo Origen*/
							Listitem itemDetalleItinerario = lbxDetalleItinerario.getItemAtIndex(lbxDetalleItinerario.getItems().size() -1);
							Util.seleccionarValorItemCombo(Localidad.class, cmbOrigen, (((DetalleItinerario) itemDetalleItinerario.getValue()).getRuta().getLocalidadDestino().getId()));
//							/*Carga Terminal Origen*/
//							cargarTerm(cmbTermOrigen, false, ((Localidad) cmbOrigen.getSelectedItem().getValue()).getId()); 							
							/*Carga el Combo Terminal LLegada*/
							if(cmbRuta.getSelectedItem().getValue() instanceof Ruta)
//								cargarTerm(cmbTerminalLlegada, false, ((Ruta) cmbRuta.getSelectedItem().getValue()).getLocalidadDestino().getId());
								cargarTerminalPartidaLlegada(cmbTerminalLlegada, false, fodeDetalleItinerario.getRuta().getLocalidadDestino().getId(), 2);
							/*Carga ListBox Terminal Llegada*/
//							ItinerarioAgenciaLlegada oItinerarioAgenciaLlegada = new ItinerarioAgenciaLlegada();
//							oItinerarioAgenciaLlegada.setAgencia(fAgenciaLlegada);
//							oItinerarioAgenciaLlegada.setHoraLlegada(Constantes.FORMAT_TIME.format(fFechaHoraLLegada.getTime()).toString());
//							Util.limpiarListbox(lbxTerminalLlegada);
//							cargaListboxTerminalLlegada(oItinerarioAgenciaLlegada);
							cargarTerminalLlegada(fodeDetalleItinerario.getLstItinerarioAgenciaLlegada());
//							HabilitaBotones_TerLlegada(false);
							/*Carga Rutas*/
							onChangeRutas();	
							
							cmbTerminalPartida.setSelectedIndex(0);
							tbterHoraPartida.setText("");
							bbxTerminalOrigen.setText("");
							bbxTerminalDestino.setText("");
							esEdicionTramo=false;
						}
					}
				});
				
			}else{
				/*Validar que la ruta exista*/
				String origen = "";
				if(lbxDetalleItinerario.getItems().size()==0) 
					origen = odetalleItinerario.getRuta().getOrigen();					
				else 
					origen = ((DetalleItinerario)lbxDetalleItinerario.getItems().get(0).getValue()).getRuta().getOrigen();
				
				String destino = odetalleItinerario.getRuta().getDestino();
				String ruta =origen +" - "+ destino;
				
//				/*Recupera el idRuta*/
				if (origen.equals("")) {
					criteriosBusqueda.remove("origen");
				}else {
					criteriosBusqueda.put("origen", "%" + origen + "%");
				}
				if (destino.equals("")) {
					criteriosBusqueda.remove("destino");
				}else {
					criteriosBusqueda.put("destino", "%" + destino + "%");
				}
				criteriosBusqueda.put("estadoRegistro", Constantes.VALUE_ACTIVO);
				ArrayList<Ruta> listarRegistros = ServiceLocator.getRutaManager().buscarPorX(criteriosBusqueda, null);
				
				if(listarRegistros.size()==0) {
					Messagebox.show("La ruta " + ruta + " no existe.", DlgMessage.NOMBREAPLICACION, DlgMessage.BTN_OK, Messagebox.INFORMATION);
				}else {
					/*Llena Tramos****/
					odetalleItinerario.getRuta().setId(listarRegistros.get(0).getId());
					agregarItinerario(odetalleItinerario,lbxDetalleItinerario);	
					/*Valida si se trata de una edición de un Tramo*/
					if (esEdicionTramo==true){
						lbxDetalleItinerario.removeItemAt(indexEdicionTramo);
						if (lbxDetalleItinerario.getItems().size()==1)
							Util.limpiarListbox(lbxTerminalPartida);		
						esEdicionTramo=false;					
					}
					/*Carga Rutas y Tarifas*/
//					CargarRutasTarifas();
					
					/*Carga Termianl Partida*/
					if (lbxDetalleItinerario.getItems().size()==1 && lbxTerminalPartida.getItems().size()==0){
						/*Carga Combo Terminal Partida*/
	//					cargarTerm(cmbTerminalPartida, false, ((Localidad) cmbOrigen.getSelectedItem().getValue()).getId());
	//					/*Carga ListBox Terminal Partida**/
	//					ItinerarioAgenciaPartida oItinerarioAgenciaPartida = new ItinerarioAgenciaPartida();
	//					oItinerarioAgenciaPartida.setAgencia(oagenciaPartida);
	//					oItinerarioAgenciaPartida.setHoraPartida(tbHoraPartida.getText());
	//					cargaListBoxTerminalPartida(oItinerarioAgenciaPartida);
						/*Carga Fecha Inicio para la Programación*/
						dbFechaInicio.setText(dbFechaItinerario.getText());
						/*Carga Fecha Fin para la Programación*/
						dbFechafin.setText(dbFechaItinerario.getText()); 	
					}
					
					/*	Para el combobox y listbox del terminal de partida	*/
					cargarTerminalPartidaLlegada(cmbTerminalPartida, false, odetalleItinerario.getRuta().getLocalidadOrigen().getId(), 1);					
					cargarTerminalPartidaLlegada(cmbTerminalLlegada, false, odetalleItinerario.getRuta().getLocalidadDestino().getId(), 2);
					
//					recargarListboxTerminales(lbxDetalleItinerario);
					cargarTerminalPartida(lstItinerarioAgenciaPartida);
					cargarTerminalLlegada(lstItinerarioAgenciaLlegada);
						
					/*Carga la nueva fecha de salida del itinerario*/
					dbFechaItinerario.setText(Constantes.FORMAT_DATE.format(FechaHoraLLegada.getTime()));
					/*Carga la nueva fecha de salida del itinerario*/
					tbHoraPartida.setText(Constantes.FORMAT_TIME.format(FechaHoraLLegada.getTime()));
					/*Selecciona el Nuevo Origen*/
					Listitem itemDetalleItinerario = lbxDetalleItinerario.getItemAtIndex(lbxDetalleItinerario.getItems().size() -1);
					Util.seleccionarValorItemCombo(Localidad.class, cmbOrigen, (((DetalleItinerario) itemDetalleItinerario.getValue()).getRuta().getLocalidadDestino().getId()));
					/*Carga Terminal Origen*/
//					cargarTerm(cmbTermOrigen, false, ((Localidad) cmbOrigen.getSelectedItem().getValue()).getId()); 							
					/*Carga el Combo Terminal LLegada*/
//					if(cmbRuta.getSelectedItem().getValue() instanceof Ruta)
//						cargarTerm(cmbTerminalLlegada, false, ((Ruta) cmbRuta.getSelectedItem().getValue()).getLocalidadDestino().getId());
					/*Carga ListBox Terminal Llegada*/
//					ItinerarioAgenciaLlegada oItinerarioAgenciaLlegada = new ItinerarioAgenciaLlegada();
//					oItinerarioAgenciaLlegada.setAgencia(oagenciaLlegada);
//					oItinerarioAgenciaLlegada.setHoraLlegada(Constantes.FORMAT_TIME.format(FechaHoraLLegada.getTime()).toString());
//					Util.limpiarListbox(listboxTerminalLlegada);
//					CargalistboxTerminalLlegada(oItinerarioAgenciaLlegada);	
					/*Carga Rutas*/
					onChangeRutas();	
					autoSeleccionarAgenciaOrigen(null, ((DetalleItinerario)itemDetalleItinerario.getValue()).getLstItinerarioAgenciaLlegada(), true);
					
					bbxTerminalOrigen.setValue("");
					bbxTerminalDestino.setValue("");
					cmbTerminalPartida.setSelectedIndex(0);
					tbterHoraPartida.setText("");
					esEdicionTramo=false;
				}
			}
		}catch (TipoItinerarioNullException tinex){
			DlgMessage.information(Messages.getString("WndItinerario.information.TipoItinerario"),cmbTipoItinerario);
		}catch (ServicioNullException snex){
			DlgMessage.information(Messages.getString("WndItinerario.information.TipoServicio"),cmbServicio);
			cmbServicio.setFocus(true); cmbServicio.select();
		}catch (LocalidadNullException lnex){
			if (lnex.getOrigenDestino().intValue()==LocalidadNullException.ORIGEN)
				DlgMessage.information(Messages.getString("WndItinerario.information.Origen"),cmbOrigen);
		}catch (RutaNullException rnex){
			DlgMessage.information(Messages.getString("WndItinerario.information.Ruta"),cmbRuta);
		}catch (FechaItinerarioNullException finex){
			DlgMessage.information(Messages.getString("WndItinerario.information.FechaItinerario"),dbFechaItinerario); 
		}catch (HoraPartidaNullException hpnex){
			DlgMessage.information(Messages.getString("WndItinerario.information.HoraPartida"),tbHoraPartida);
		}catch (TerminalOrigenNullException tonex){
			DlgMessage.information(Messages.getString("WndItinerario.information.TerminalOrigen"),bbxTerminalOrigen);
		}catch (TerminalDestinoNullException tdnex){
			DlgMessage.information(Messages.getString("WndItinerario.information.TerminalDestino"),bbxTerminalDestino);
		}catch (ItinerarioException i){
			if(i.getTipo().intValue()==ItinerarioException.FECHA_MENOR){
				DlgMessage.information(Messages.getString("WndItinerario.information.FechaItinerarioMenorALaActual"),dbFechaItinerario);
			}else if (i.getTipo().intValue()==ItinerarioException.TRAMOS_DIFERENTES){
				DlgMessage.information(Messages.getString("WndItinerario.information.TramosDiferentes"),cmbOrigen);
			}else if(i.getTipo().intValue()==ItinerarioException.TRAMOS_DUPLICADOS){
				DetalleItinerario detalleItinerario = listTramoDuplicado.get(0);
				DlgMessage.information(Messages.getString("WndItinerario.information.TramosDuplicados")+
						" Nro.Itinerario:" + detalleItinerario.getItinerario().getId()  +
						", Fecha:" + Constantes.FORMAT_DATE.format(detalleItinerario.getFechaPartida()) +
						",  Hora:"+ detalleItinerario.getHoraPartida());
			}else if (i.getTipo().intValue()==ItinerarioException.FECHA_NEX_TRAMO_MENOR){
				DlgMessage.information(Messages.getString("WndItinerario.information.ValidacionFechaPartida"),dbFechaItinerario);
			}else if (i.getTipo().intValue()==ItinerarioException.HORA_NEX_TRAMO_MENOR){
				DlgMessage.information(Messages.getString("WndItinerario.information.ValidacionHoraPartida"),tbHoraPartida);
			}else if (i.getTipo().intValue()==ItinerarioException.TIPO_SERVICIO_DIFERENTE){
				DlgMessage.information(Messages.getString("WndItinerario.information.ValidacionServicio"),cmbServicio);
			}else if (i.getTipo().intValue()==ItinerarioException.TIPO_ITINERARIO_DIFERENTE){
				DlgMessage.information(Messages.getString("WndItinerario.information.ValidacionTipoItinerario"),cmbTipoItinerario);
			}
		}catch (CapacidadServicioMayorExeption csmex){
			DlgMessage.information(Messages.getString("WndItinerario.information.CapacidadServicioMayor"));
		}
		catch (Exception ex){
			DlgMessage.error(this.getClass().getName()+" "+ex.getMessage());
			ex.printStackTrace(); 
		}
	}
	
	/**
	 * Agrega  al Detalle del Itinerario el itinerario creado. 
	 * @param odetalleItinerario		:	Clase DetalleItinerario
	 * @param lisBoxDetalleItinerario	: ListBox al que se Agregará el Itinerario
	 * @throws Exception 
	 */
	public void agregarItinerario(DetalleItinerario odetalleItinerario, Listbox lisBoxDetalleItinerario) throws Exception{
				
		Listitem item = null;
		Listcell cell = null;
		Integer x = lisBoxDetalleItinerario.getChildren().size();
		
		Servicio servicio=ServiceLocator.getServicioManager().buscarPorId(odetalleItinerario.getItinerario().getServicio().getId().longValue());
		
		item = new Listitem();
		cell = new Listcell((x.toString()));
		cell.setStyle("font-size:11px !important");
		item.appendChild(cell); //Correlativo
		
		cell = new Listcell(odetalleItinerario.getRuta().toString());
		item.appendChild(cell);
		
		cell = new Listcell(servicio.getDenominacion());
		item.appendChild(cell);
		
		cell = new Listcell(Util.toFechaNombreDiaMes(odetalleItinerario.getFechaPartida()));
		cell.setTooltiptext(Util.toFechaNombreDiaMesLong(odetalleItinerario.getFechaPartida()));
		cell.setStyle("font-size:9px !important");
		item.appendChild(cell);
		
		cell = new Listcell(odetalleItinerario.getHoraPartida());
		cell.setStyle("font-size:11px !important");
		item.appendChild(cell);
		
		cell = new Listcell(Util.toFechaNombreDiaMes(odetalleItinerario.getFechaLlegada()));
		cell.setTooltiptext(Util.toFechaNombreDiaMesLong(odetalleItinerario.getFechaLlegada()));
		cell.setStyle("font-size:9px !important");
		item.appendChild(cell);	
		
		cell = new Listcell(odetalleItinerario.getHoraLlegada());
		cell.setStyle("font-size:11px !important");
		item.appendChild(cell);
		
		cell = new Listcell(odetalleItinerario.getAgenciaPartida().getNombreCorto());
		item.appendChild(cell);
		
		cell = new Listcell(odetalleItinerario.getAgenciaLlegada().getNombreCorto());
		item.appendChild(cell);
		
		Hbox hbox=new Hbox();
		
		final Image imageDelete= new Image();
		imageDelete.setHeight("17px");
		imageDelete.setZindex(x);
		imageDelete.addEventListener(Events.ON_CLICK, new EventListener<Event>() {
			@Override
			public void onEvent(Event event) throws Exception {
				if(imageDelete.getSrc().equals(enabledDelete)){
					int index=imageDelete.getZindex();
					confirmaQuitarTramo(index-1);
				}
			}
		});
		hbox.appendChild(imageDelete);
		
		Separator separator=new Separator();
		separator.setWidth("5px");
		hbox.appendChild(separator);
		
		final Image imageEditar= new Image();
		imageEditar.setHeight("15px");
		imageEditar.setZindex(x);
		imageEditar.addEventListener(Events.ON_CLICK,new EventListener<Event>() {
			@Override
			public void onEvent(Event event) throws Exception {
				if (imageEditar.getSrc().equals(enabledEdit)){
					int index=imageEditar.getZindex();
					lbxDetalleItinerario.setSelectedIndex(index-1);
					editarTramo();
				}
			}
		});
		hbox.appendChild(imageEditar);
		
		cell = new Listcell();
		cell .appendChild(hbox);				
		item.appendChild(cell);

		item.setValue(odetalleItinerario);
		lisBoxDetalleItinerario.appendChild(item);
		asignarImagenEliminarTramo();
	}
	
	/**
	 * Confirmación para quitar un tramo
	 * @param Index 	: El indice del tramo seleccionado.
	 * @throws Exception
	 */
	public void confirmaQuitarTramo(Integer Index) throws Exception{ 
		final Integer index= Index;
		if (index >=0){
			if (index == lbxDetalleItinerario.getItems().size() -1){	
				Messagebox.show(Messages.getString("WndItinerario.question.BorrarTramo"), DlgMessage.NOMBREAPLICACION, DlgMessage.BTN_YESNO, Messagebox.QUESTION, new EventListener<Event>() {
					@Override
					public void onEvent(Event e) throws Exception {
						if(e.getName().equals("onYes")){
							quitarTramo(index);
						}	
					}	
				});				
			}else{
				DlgMessage.information(Messages.getString("WndItinerario.information.NoBorrarTramo"));
			}
		}else{
			DlgMessage.information(Messages.getString("WndItinerario.information.SeleccionarTramo"));
		}		
	}
	
	/**
	 * Quita el tramo de itinerario seleccionado.
	 * @param index	: El indice del tramo seleccionado.
	 * @throws Exception
	 */
	public void quitarTramo(Integer index) throws Exception{
		lbxDetalleItinerario.removeItemAt(index);
		
		if (index==0){
			cmbTerminalPartida.getItems().clear();
			cmbTerminalLlegada.getItems().clear();
			tbterHoraPartida.setText("");
			tbterHoraLlegada.setText("");
			Util.limpiarListbox(lbxTerminalPartida);
			Util.limpiarListbox(lbxTerminalLlegada);
			Util.limpiarListbox(lbxTerminalOrigen);
			
			cmbOrigen.setSelectedIndex(0);
		}else{
			Listitem itemDetalleItinerario = lbxDetalleItinerario.getItemAtIndex(index - 1);
			dbFechaItinerario.setValue(((DetalleItinerario) itemDetalleItinerario.getValue()).getFechaLlegada());	
			tbHoraPartida.setText(((DetalleItinerario) itemDetalleItinerario.getValue()).getHoraLlegada());
			Util.seleccionarValorItemCombo(Localidad.class, cmbOrigen, ((DetalleItinerario) itemDetalleItinerario.getValue()).getRuta().getLocalidadDestino().getId());
			onChangeRutas();
			autoSeleccionarAgenciaOrigen(null, ((DetalleItinerario) itemDetalleItinerario.getValue()).getLstItinerarioAgenciaLlegada(), true);
			Util.limpiarListbox(lbxTerminalPartida);
			Util.limpiarListbox(lbxTerminalLlegada);
			
			Listitem it = lbxDetalleItinerario.getItemAtIndex(lbxDetalleItinerario.getItems().size()-1);
			cargarTerminalPartidaLlegada(cmbTerminalPartida, false, ((DetalleItinerario)it.getValue()).getRuta().getLocalidadOrigen().getId(), 1);
			cargarTerminalPartidaLlegada(cmbTerminalLlegada, false, ((DetalleItinerario)it.getValue()).getRuta().getLocalidadDestino().getId(), 2);
		}
		esEdicionTramo=false;	
		asignarImagenEliminarTramo();
	}
	
	/**
	 * Auto selecciona las Agencias de partida, segun las agencias de llegada del ultimo tramo agregado.
	 * @param lstItinerarioAgenciaLlegada
	 */
	private void autoSeleccionarAgenciaOrigen(List<ItinerarioAgenciaPartida> lstItinerarioAgenciaPartida, List<ItinerarioAgenciaLlegada> lstItinerarioAgenciaLlegada, boolean isAgenciaPartida) {
		if(isAgenciaPartida) {
			if(lstItinerarioAgenciaPartida==null) {
				for(ItinerarioAgenciaLlegada itinerarioAgenciaLlegada : lstItinerarioAgenciaLlegada) {
					for(Listitem item : lbxTerminalOrigen.getItems()) {
						Agencia agencia = (Agencia)item.getValue();
						if(agencia.getId().intValue()==itinerarioAgenciaLlegada.getAgencia().getId()) {
							item.setSelected(true);
							((Timebox)item.getChildren().get(1).getChildren().get(0)).setText(itinerarioAgenciaLlegada.getHoraLlegada());
							break;
						}
					}
				}
			}else if(lstItinerarioAgenciaLlegada == null) {
				for(ItinerarioAgenciaPartida itinerarioAgenciaPartida : lstItinerarioAgenciaPartida) {
					for(Listitem item : lbxTerminalOrigen.getItems()) {
						Agencia agencia = (Agencia)item.getValue();
						if(agencia.getId().intValue()==itinerarioAgenciaPartida.getAgencia().getId()) {
							item.setSelected(true);
							((Timebox)item.getChildren().get(1).getChildren().get(0)).setText(itinerarioAgenciaPartida.getHoraPartida());
							break;
						}
					}
				}
			}
		}else {
			for(ItinerarioAgenciaLlegada itinerarioAgenciaLlegada : lstItinerarioAgenciaLlegada) {
				for(Listitem item : lbxTerminalDestino.getItems()) {
					Agencia agencia = (Agencia)item.getValue();
					if(agencia.getId().intValue()==itinerarioAgenciaLlegada.getAgencia().getId()) {
						item.setSelected(true);
						((Timebox)item.getChildren().get(1).getChildren().get(0)).setText(itinerarioAgenciaLlegada.getHoraLlegada());
						break;
					}
				}
			}
		}
	}
	
	/**
	 * Establece el tipo de imagen que debe contener cada tramo de itinerario (imagen activa o inactiva)
	 */
	private void asignarImagenEliminarTramo(){		
		if(lbxDetalleItinerario.getItems().size()>0){
			for(int y=0; y<lbxDetalleItinerario.getItems().size();y++){
				Integer c=lbxDetalleItinerario.getItems().size();
				Component it = lbxDetalleItinerario.getChildren().get(y+1);
				Listcell cell=(Listcell) it.getChildren().get(9);
				Hbox hbox=(Hbox)cell.getChildren().get(0);
				if(c.equals(y+1) && btnGuardar.isDisabled()==false){
					((Image)hbox.getChildren().get(0)).setSrc(enabledDelete);			
					((Image)hbox.getChildren().get(0)).setTooltiptext("Eliminar Tramo");
					((Image)hbox.getChildren().get(0)).setStyle("cursor:pointer");
					((Image)hbox.getChildren().get(2)).setSrc(enabledEdit);			
					((Image)hbox.getChildren().get(2)).setTooltiptext("Editar Tramo");
					((Image)hbox.getChildren().get(2)).setStyle("cursor:pointer");
				}else{
					((Image)hbox.getChildren().get(0)).setSrc(disabledDelete);			
					((Image)hbox.getChildren().get(0)).setTooltiptext("");
					((Image)hbox.getChildren().get(0)).setStyle("cursor:default");
					((Image)hbox.getChildren().get(2)).setSrc(disabledEdit);			
					((Image)hbox.getChildren().get(2)).setTooltiptext("");
					((Image)hbox.getChildren().get(2)).setStyle("cursor:default");
				}
			}
		}
	}	
	
	/**
	 * Para la edición del Tramo seleccionado
	 * @throws Exception
	 */
	public void editarTramo() throws Exception{
		Integer index = lbxDetalleItinerario.getSelectedIndex();
		if (index >= 0){
			if (index == lbxDetalleItinerario.getItems().size() -1){
				Listitem itemDetalleItinerario = lbxDetalleItinerario.getItemAtIndex(index);
				Util.seleccionarValorItemCombo(Localidad.class, cmbOrigen, ((DetalleItinerario) itemDetalleItinerario.getValue()).getRuta().getLocalidadOrigen().getId());	/*Selecciona el Origen*/
				/*Carga las Rutas*/
				onChangeRutas();
				Util.seleccionarValorItemCombo(Ruta.class, cmbRuta, ((DetalleItinerario) itemDetalleItinerario.getValue()).getRuta().getId());								/*Seleecion el Origen*/
				dbFechaItinerario.setValue(((DetalleItinerario) itemDetalleItinerario.getValue()).getFechaPartida());														/*Recupera Fecha partida*/
				tbHoraPartida.setText(((DetalleItinerario) itemDetalleItinerario.getValue()).getHoraPartida());																/*Recupera Hora de Partida*/
				strHoraPartida = tbHoraPartida.getText();				
//				cargarTerm(cmbTermDestino, false, ((DetalleItinerario) itemDetalleItinerario.getValue()).getRuta().getLocalidadDestino().getId()); 							/*carga Terminal Destino*/
//				Util.seleccionarValorItemCombo(Agencia.class, cmbTermOrigen, ((DetalleItinerario) itemDetalleItinerario.getValue()).getAgenciaPartida().getId());			/*Selecciona el Terminal Origen*/
//				Util.seleccionarValorItemCombo(Agencia.class, cmbTermDestino, ((DetalleItinerario) itemDetalleItinerario.getValue()).getAgenciaLlegada().getId());			/*Selecciona le Terminal Destino*/
				
				if (cmbTipoItinerario.getSelectedIndex()==0){
					/*Solo para la edición del Itinerario, despues de que esta haya sido guardado*/
					Util.seleccionarValorItemCombo(Servicio.class,cmbServicio, ((DetalleItinerario) itemDetalleItinerario.getValue()).getItinerario().getServicio().getId());/**Selecciona el Servico*/ 
					Util.seleccionarValorItemCombo(TipoItinerario.class,cmbTipoItinerario, ((DetalleItinerario) itemDetalleItinerario.getValue()).getItinerario().getTipoItinerario().getId());/**Selecciona el Tipo de Itinerario*/
				}
				
				
				autoSeleccionarAgenciaOrigen(((DetalleItinerario)itemDetalleItinerario.getValue()).getLstItinerarioAgenciaPartida(),null, true);
				onChangeTerminalDestino(lbxTerminalDestino);
				autoSeleccionarAgenciaOrigen(null, ((DetalleItinerario)itemDetalleItinerario.getValue()).getLstItinerarioAgenciaLlegada(), false);
				getAgenciasPartida();
				
				/*Se utilizan para la edicion de los tramos.*/
				indexEdicionTramo=index;
				esEdicionTramo=true;
				
				if(action==ACTION_MODIFY){
					if(lbxDetalleItinerario.getItems().size()==1)
						dbFechaItinerario.setDisabled(true);
					else
						dbFechaItinerario.setDisabled(false);
				}else 
					dbFechaItinerario.setDisabled(false);				
			}else{
				DlgMessage.information(Messages.getString("WndItinerario.information.NoEditarTramo"));
			}			
		}else{
			DlgMessage.information(Messages.getString("WndItinerario.information.SeleccionarTramo"));
		}
	}
	
	
//	/**
//	 * 	Carga Rutas y Tarifas. 
//	 * @throws Exception
//	 */
//	private  void CargarRutasTarifas () throws Exception{
//		Rows Rows = new Rows();
//		Integer Cont = 0;
//		Double tarifa=(double) 0;
//		for (int y=0; y < listboxDetalleItinerario.getItems().size(); y ++ ){
//			Listitem it = listboxDetalleItinerario.getItems().get(y);
//			String origen = ((DetalleItinerario) it.getValue()).getRuta().getOrigen();
//						
//			for (int t=y; t < listboxDetalleItinerario.getItems().size(); t ++ ){
//				Listitem iter = listboxDetalleItinerario.getItemAtIndex(t);
//				String destino = ((DetalleItinerario) iter.getValue()).getRuta().getDestino();
//				String ruta =origen +" - "+ destino;
//				
//				/*Recupera el idRuta*/
//				if (origen.equals("")) {
//					criteriosBusqueda.remove("origen");
//				}else {criteriosBusqueda.put("origen", "%" + origen + "%");}
//				if (destino.equals("")) {
//					criteriosBusqueda.remove("destino");
//				}else {criteriosBusqueda.put("destino", "%" + destino + "%");}
//				criteriosBusqueda.put("estadoRegistro", Constantes.VALUE_ACTIVO);
//				ArrayList<Ruta> listarRegistros = ServiceLocator.getRutaManager().buscarPorX(criteriosBusqueda, null);
//				if (listarRegistros.size() > 0){
//					Ruta oRuta= new Ruta();
//					oRuta= listarRegistros.get(0);
//					
//					if (lstItinerariosDetalle != null){
//						/*Para recuperar la tarifa, en caso que sea una edicion del Itinerario*/
//						for(DetalleItinerario detalleItinerario : lstItinerariosDetalle){
//							if (oRuta.getId().equals(detalleItinerario.getRuta().getId())){
//								tarifa=detalleItinerario.getTarifa();
//							}
//						}
//					}
//					
//					Row row = new Row();
//					Label idRuta = new Label( oRuta.getId().toString());	
//					row.appendChild(idRuta);	/*idRuta*/
//					Cont += 1;
//					Label item = new Label(Cont.toString());
//					row.appendChild(item);		/*Item*/
//					Label label = new Label(ruta); 				
//					row.appendChild(label);		/*Ruta*/
//					Decimalbox dbtarifa = new Decimalbox(BigDecimal.valueOf(tarifa));
//					dbtarifa.setLocale(Locale.US);
//					dbtarifa.setWidth("50px");
//					dbtarifa.setFormat("##0.00");
//					dbtarifa.setStyle("text-align:right");
//					if(btnGuardar.isDisabled()) dbtarifa.setDisabled(true);
//					row.appendChild(dbtarifa);	/*Tarifa*/	
//					Rows.appendChild(row);
//					
//				}else{
//					/*En caso de que no exista la Ruta*/
//					Messagebox.show("La ruta " + ruta + " no existe.", DlgMessage.NOMBREAPLICACION, DlgMessage.BTN_OK, Messagebox.INFORMATION);
//					quitaTramo(listboxDetalleItinerario.getItems().size() -1);
//					return;
//				}
//				
//			}
//		}
//		grdRutasTrifas.getRows().detach();
//		grdRutasTrifas.appendChild(Rows);
//	}
	
//	/**
// 	 * Agrea terminal de Partida o de Llegada. atravez del Boton "Add"
// 	 * @param oClass			: Clase según el terminal a agregar. (ItinerarioAgenciaPartida o ItinerarioAgenciaLlegada)
// 	 * @param listBoxTerminal	: ListBox en la que se cargará el terminal
// 	 * @param cmbTerminal		: ComboBox de conde se agregará el terminal de Partida o Llegada.	
// 	 * @param tbHora			: TimeBox de donde se agregará el Hora de Partida o Llegada
// 	 * @throws Exception	
// 	 */
// 	public void addTerminal(Class<?> oClass, Listbox listBoxTerminal, Combobox cmbTerminal, Timebox tbHora) throws Exception{
// 		if (cmbTerminal.getSelectedItem().getValue() instanceof Agencia && tbHora.getValue() != null){ 
// 			Listitem itemTerminal = listBoxTerminal.getItemAtIndex(listBoxTerminal.getItems().size() -1);
// 			if (oClass.equals(ItinerarioAgenciaPartida.class)){
// 				/*Cuando es Terminal Partida*/ 
// 				if (!((ItinerarioAgenciaPartida) itemTerminal.getValue()).getAgencia().getId().equals(((Agencia) cmbTerminal.getSelectedItem().getValue()).getId())){					
//	 					ItinerarioAgenciaPartida oItinerarioAgenciaPartida = new ItinerarioAgenciaPartida();
// 	 					Agencia oAgencia = new Agencia();
// 	 					oAgencia.setId(((Agencia) cmbTerminalPartida.getSelectedItem().getValue()).getId());
// 	 					oAgencia.setDenominacion(((Agencia) cmbTerminalPartida.getSelectedItem().getValue()).getDenominacion());
// 	 					oAgencia.setNombreCorto(((Agencia) cmbTerminalPartida.getSelectedItem().getValue()).getNombreCorto());
// 	 					
// 	 					oItinerarioAgenciaPartida.setAgencia(oAgencia);
// 	 					oItinerarioAgenciaPartida.setHoraPartida(tbHora.getText());
// 	 					oItinerarioAgenciaPartida.setLocalidad(((Agencia) cmbTerminalPartida.getSelectedItem().getValue()).getLocalidad());
// 	 					cargaListboxTerminalPartida(oItinerarioAgenciaPartida); 					
// 	 				
//	 			}else{
//	 				DlgMessage.information(Messages.getString("WndItinerario.information.Terminal"));
//	 			}		
// 	 		}else if (oClass.equals(ItinerarioAgenciaLlegada.class)){
// 	 				/*Cuando es Terminal Llegada*/
// 	 			if (!((ItinerarioAgenciaLlegada) itemTerminal.getValue()).getAgencia().getId().equals(((Agencia) cmbTerminal.getSelectedItem().getValue()).getId())){
//	 					ItinerarioAgenciaLlegada oItinerarioAgenciaLlegada = new ItinerarioAgenciaLlegada();
//	 					Agencia oAgencia = new Agencia();
//	 					oAgencia.setId(((Agencia) cmbTerminalLlegada.getSelectedItem().getValue()).getId());
//	 					oAgencia.setDenominacion(((Agencia) cmbTerminalLlegada.getSelectedItem().getValue()).getDenominacion());
//	 					oAgencia.setNombreCorto(((Agencia) cmbTerminalLlegada.getSelectedItem().getValue()).getNombreCorto());
//	 					
//	 					oItinerarioAgenciaLlegada.setAgencia(oAgencia);
//	 					oItinerarioAgenciaLlegada.setHoraLlegada(tbHora.getText());
//	 					oItinerarioAgenciaLlegada.setLocalidad(((Agencia) cmbTerminalLlegada.getSelectedItem().getValue()).getLocalidad());
//	 					cargaListboxTerminalLlegada(oItinerarioAgenciaLlegada);
// 	 			}else{
// 	 	 			DlgMessage.information(Messages.getString("WndItinerario.information.Terminal"));
// 	 	 		}		
// 	 		}
// 			cmbTerminal.setSelectedIndex(0);
// 			tbHora.setValue(null);
// 			if (oClass.equals(ItinerarioAgenciaPartida.class))
// 				
// 				Util.disabledBtnAgregar(true, cmdAddTerPartida, accesoGrabar());
// 			else
// 				Util.disabledBtnAgregar(true, cmdAddTerLlegada, accesoGrabar());
// 		}
// 	}
 	
 	private void HabilitaBotones_AgredarTramo(Boolean estado){
		if(estado)
			cmdAgregarTramo.setImage("/resources/mp_agregar.png");
		else
			cmdAgregarTramo.setImage("/resources/mp_agregar.png");
		cmdAgregarTramo.setDisabled(estado);
	}

	
	
//	/**
//	 * Vuelve a cargar los terminales de partida y llegada segun el detalle de itinerario
//	 * @param lstbxDetalleItinerario
//	 * @throws Exception
//	 */
//	public void recargarListboxTerminales(Listbox lstbxDetalleItinerario) throws Exception {
//		Util.limpiarListbox(lbxTerminalPartida);
//		Util.limpiarListbox(lbxTerminalLlegada);
//		for(Listitem item : lstbxDetalleItinerario.getItems()) {
//			DetalleItinerario detalle = (DetalleItinerario)item.getValue();
//			
//			ItinerarioAgenciaPartida oItinerarioAgenciaPartida = new ItinerarioAgenciaPartida();
//			Agencia oAgencia = new Agencia();
//			oAgencia.setId(detalle.getAgenciaPartida().getId());
//			oAgencia.setNombreCorto(detalle.getAgenciaPartida().getNombreCorto());
//			oAgencia.setDenominacion(detalle.getAgenciaPartida().getDenominacion());
//			oItinerarioAgenciaPartida.setAgencia(oAgencia);
//			oItinerarioAgenciaPartida.setHoraPartida(detalle.getHoraPartida());
//			oItinerarioAgenciaPartida.setLocalidad(detalle.getRuta().getLocalidadOrigen());
//			cargaListboxTerminalPartida(oItinerarioAgenciaPartida);
//			
//			ItinerarioAgenciaLlegada oItinerarioAgenciaLlegada = new ItinerarioAgenciaLlegada();
//			Agencia oAgenciaLlegada = new Agencia();
//			oAgenciaLlegada.setId(detalle.getAgenciaLlegada().getId());
//			oAgenciaLlegada.setNombreCorto(detalle.getAgenciaLlegada().getNombreCorto());
//			oAgenciaLlegada.setDenominacion(detalle.getAgenciaLlegada().getDenominacion());
//			oItinerarioAgenciaLlegada.setAgencia(oAgenciaLlegada);
//			oItinerarioAgenciaLlegada.setHoraLlegada(detalle.getHoraLlegada());
//			oItinerarioAgenciaLlegada.setLocalidad(detalle.getRuta().getLocalidadDestino());
//			cargaListboxTerminalLlegada(oItinerarioAgenciaLlegada);
//		}
//	}
	
	
//	/**
//	 * Carga los terminales de origen y destino, segun la ruta seleccionada
//	 * 
//	 * @param combobox		: ComboBox en donde se cargara el terminal 
//	 * @param todos			: True muestra "TODOS"; false muesta "SELECCIONE"
//	 * @param localidad		: identificador unico de la localidad
//	 * @throws Exception  	
//	 */
//	public static void cargarTerm(Combobox combobox, Boolean todos, Integer localidad) throws Exception{
//		TreeMap<String, Object> criteriosBusqueda = new TreeMap<String, Object>();
//		List<String> criteriosOrdenar = null;
//		Boolean esTermina=true;
//		
//		criteriosOrdenar = new ArrayList<String>();
//		criteriosOrdenar.add("denominacion");
//	
//		Localidad olocalidad = new Localidad();
//		olocalidad.setId(localidad);
//		criteriosBusqueda.put("localidad", olocalidad);
//		criteriosBusqueda.put("esTerminal", esTermina);
//		criteriosBusqueda.put("estadoRegistro", Constantes.VALUE_ACTIVO);
//		ArrayList<Agencia> lstAgePar = ServiceLocator.getAgenciaManager().buscarPorX(criteriosBusqueda, criteriosOrdenar);
//		
//		combobox.getItems().clear();
//		UtilData.cargarGenericData(combobox, todos);	
//		for (int l = 0; l < lstAgePar.size(); l ++) {
//			Agencia oAgencia = lstAgePar.get(l);
//			Comboitem oComboitem = new Comboitem();
//
//			oComboitem.setValue(oAgencia);
//			oComboitem.setLabel(oAgencia.getNombreCorto());
//
//			combobox.appendChild(oComboitem);
//		}
//	}
	
	/**
	 * Carga los terminales de origen y destino, segun la ruta seleccionada	 * 
	 * @param combobox		: Listbox en donde se cargara el terminal 
	 * @param todos			: True muestra "TODOS"; false muesta "SELECCIONE"
	 * @param localidad		: identificador unico de la localidad
	 * @throws Exception  	
	 */
	public static void cargarAgenciaOrigenDestino(Listbox listbox, Boolean todos, Integer localidad) throws Exception {
		TreeMap<String, Object> criteriosBusqueda = new TreeMap<String, Object>();
		List<String> criteriosOrdenar = null;
		Boolean esTermina=true;
		
		criteriosOrdenar = new ArrayList<String>();
		criteriosOrdenar.add("denominacion");
	
		Localidad olocalidad = new Localidad();
		olocalidad.setId(localidad);
		criteriosBusqueda.put("localidad", olocalidad);
		criteriosBusqueda.put("esTerminal", esTermina);
		criteriosBusqueda.put("estadoRegistro", Constantes.VALUE_ACTIVO);
		ArrayList<Agencia> lstAgenciaPartida = ServiceLocator.getAgenciaManager().buscarPorX(criteriosBusqueda, criteriosOrdenar);
		listbox.getItems().clear();
		for (int l = 0; l < lstAgenciaPartida.size(); l ++) {
			Agencia oAgencia = lstAgenciaPartida.get(l);
			Listitem oListItem = new Listitem();
			Listcell oListCell = new Listcell();
			oListCell.setLabel(oAgencia.getNombreCorto());
			oListItem.appendChild(oListCell);
			oListCell = new Listcell();
			Timebox oTimebox = new Timebox();
			oTimebox.setWidth("55px");
			oTimebox.setFormat("HH:mm");
			oListCell.appendChild(oTimebox);
			oListItem.setValue(oAgencia);
			oListItem.appendChild(oListCell);
			
			listbox.appendChild(oListItem);
		}
	}
	
	public void onSelectAgenciaOrigen() throws Exception {
		String terminales = "";
		lstAgenciaPartida = new ArrayList<Agencia>();
		try {				
//			if(!strHoraPartida.equals(tbHoraPartida.getText())) {
//				Date horaInicial = Util.StringtoDate(dbFechaItinerario.getText()+" "+strHoraPartida, "dd/MM/yyyy HH:mm");
//				Date nuevaHora = Util.StringtoDate(dbFechaItinerario.getText()+" "+tbHoraPartida.getText(), "dd/MM/yyyy HH:mm");
//				Long diff = nuevaHora.getTime() - horaInicial.getTime();
//				for(Listitem item : lbxTerminalOrigen.getSelectedItems()) {
//					String hora = ((Timebox)item.getChildren().get(1).getChildren().get(0)).getText();
//					Date newHora = Util.StringtoDate(dbFechaItinerario.getText() + " " + hora, "dd/MM/yyyy HH:mm");
//					Long newDiff = newHora.getTime() + diff;
//					String strNewHora = Util.DatetoString(new Date(newDiff), "HH:mm");
//					System.out.println(strNewHora);
//					((Timebox)item.getChildren().get(1).getChildren().get(0)).setText(strNewHora);
//				}
//			}
			
			for(Listitem item : lbxTerminalOrigen.getSelectedItems()) {
				terminales = terminales + ((Agencia)item.getValue()).getDenominacion() + ", ";
				if(((Timebox)item.getChildren().get(1).getChildren().get(0)).getText().equals(""))
					throw new HoraPartidaNullException();
				Agencia agenciaPartida = (Agencia)item.getValue();
				agenciaPartida.setHoraPartida(((Timebox)item.getChildren().get(1).getChildren().get(0)).getText());
				lstAgenciaPartida.add(agenciaPartida);
			}
			bbxTerminalOrigen.setValue(terminales);
			bbxTerminalOrigen.close();
		}catch (HoraPartidaNullException hpnex) {
			DlgMessage.information(Messages.getString("WndItinerario.information.HoraPartida"));
		}catch (Exception ex) { 
			DlgMessage.error(this.getClass().getName()+" "+ex.getMessage());
			ex.printStackTrace();
		}
	}
	
	public void onSelectAgenciaDestino() throws Exception{
		String terminales = "";
		lstAgenciaLlegada = new ArrayList<Agencia>();
		try {
			for(Listitem item : lbxTerminalDestino.getSelectedItems()) {
				terminales = terminales + ((Agencia)item.getValue()).getDenominacion() + ", ";
				if(((Timebox)item.getChildren().get(1).getChildren().get(0)).getText().equals(""))
					throw new HoraLlegadaNullException();
				Agencia agenciaLlegada = (Agencia)item.getValue();
				agenciaLlegada.setHoraPartida(((Timebox)item.getChildren().get(1).getChildren().get(0)).getText());
				lstAgenciaLlegada.add(agenciaLlegada);
			}
			bbxTerminalDestino.setValue(terminales);
			bbxTerminalDestino.close();
		}catch (HoraLlegadaNullException hlnex) {
			DlgMessage.information(Messages.getString("WndItinerario.information.HoraLlegada"));
		}catch (Exception ex) {
			DlgMessage.error(this.getClass().getName()+" "+ex.getMessage());
			ex.printStackTrace();
		}
		
	}
	
	/**
	 * Carga el combobox con los terminales de Partida y Llegada
	 * @param combobox					:
	 * @param todos						:
	 * @param lstbxDetalleItinerario	:
	 * @param tipoTerminal				: 0-Ambos terminales, 1-Terminal Partida, 2-Terminal Llegada
	 * @throws Exception
	 */
	public static void cargarTerminalPartidaLlegada(Combobox combobox, Boolean todos, Integer idLocalidad, int tipoTerminal) throws Exception{
		TreeMap<String, Object> criteriosBusqueda = new TreeMap<String, Object>();
		List<String> criteriosOrdenar = null;
		Boolean esTerminal=true;
		
		criteriosOrdenar = new ArrayList<String>();
		criteriosOrdenar.add("denominacion");
	
		Localidad olocalidad = new Localidad();
		olocalidad.setId(idLocalidad);
		criteriosBusqueda.put("localidad", olocalidad);
		criteriosBusqueda.put("esTerminal", esTerminal);
		criteriosBusqueda.put("estadoRegistro", Constantes.VALUE_ACTIVO);
		ArrayList<Agencia> lstAgePar = ServiceLocator.getAgenciaManager().buscarPorX(criteriosBusqueda, criteriosOrdenar);
		
		combobox.getItems().clear();
		UtilData.cargarGenericData(combobox, todos);	
		for (int l = 0; l < lstAgePar.size(); l ++) {
			Agencia oAgencia = lstAgePar.get(l);
			Comboitem oComboitem = new Comboitem();

			oComboitem.setValue(oAgencia);
			oComboitem.setLabel(oAgencia.getNombreCorto());

			combobox.appendChild(oComboitem);
		}
//		String strIdLocalidadPartida = "";
//		String strIdLocalidadLlegada = "";
//		for(Listitem item : lstbxDetalleItinerario.getItems()) {
//			DetalleItinerario detalle = (DetalleItinerario)item.getValue();
//			if(!strIdLocalidadPartida.isEmpty()) 
//				strIdLocalidadPartida = strIdLocalidadPartida + ",";						
//			strIdLocalidadPartida = strIdLocalidadPartida + detalle.getRuta().getLocalidadOrigen().getId();
//			
//			if(!strIdLocalidadLlegada.isEmpty()) 
//				strIdLocalidadLlegada = strIdLocalidadLlegada + ",";						
//			strIdLocalidadLlegada = strIdLocalidadLlegada + detalle.getRuta().getLocalidadDestino().getId();
//		}				
//		
//		List<Agencia> lstAgencias = null;
//		if(tipoTerminal==1) 
//			lstAgencias = ServiceLocator.getAgenciaManager().buscarAgenciaByLocalidad(strIdLocalidadPartida);
//		else
//			lstAgencias = ServiceLocator.getAgenciaManager().buscarAgenciaByLocalidad(strIdLocalidadLlegada);
//			
//		combobox.getItems().clear();
//		UtilData.cargarGenericData(combobox, todos);	
//		for (int l = 0; l < lstAgencias.size(); l ++) {
//			Agencia oAgencia = lstAgencias.get(l);
//			Comboitem oComboitem = new Comboitem();
//			oComboitem.setLabel(oAgencia.getNombreCorto());
//			oComboitem.setValue(oAgencia);
//			combobox.appendChild(oComboitem);
//		}
	}
	
	/**
	 * 
	 * @param action					: action 0=Nuevo; 1=Modificar
	 * @param listboxDetalleItinerario	: ListBox de donde se leerán los Itinerarios para grabar.
	 * @throws Exception
	 */
	public void guardaItineratio(int action, Listbox listboxDetalleItinerario) throws Exception{
		TipoItinerario otipoItinerario = new TipoItinerario();
		Servicio oservicio = new Servicio();
		Agencia oagenciaPartida = new Agencia();
		Agencia oagenciaLlegada = new Agencia();
		Ruta oruta = new Ruta();
		
		Date fechaPartida = new Date();
		Date fehcaLlegada = new Date();
		
		if (action==ACTION_NEW)
			oitinerario = new Itinerario();
		
		/*Recupera valores de la Primera Fila del listboxDetalleItinerario (DetalleItinerario)*/
		Integer primerIndex = 0;
		Listitem primeraFila = listboxDetalleItinerario.getItemAtIndex(primerIndex);
		String origen = ((DetalleItinerario) primeraFila.getValue()).getRuta().getOrigen();
		oagenciaPartida.setId(((DetalleItinerario) primeraFila.getValue()).getAgenciaPartida().getId());
		String shoraPartida = ((DetalleItinerario) primeraFila.getValue()).getHoraPartida();
		fechaPartida= ((DetalleItinerario) primeraFila.getValue()).getFechaPartida();
		
		/*Recupera valores de la Ultima Fila del listboxDetalleItinerario (DetalleItinerario)*/
		Integer UltimoIndex = listboxDetalleItinerario.getItems().size() -1;
		Listitem ultimaFila = listboxDetalleItinerario.getItemAtIndex(UltimoIndex);
		String destino = ((DetalleItinerario) ultimaFila.getValue()).getRuta().getDestino();
		oagenciaLlegada.setId(((DetalleItinerario) ultimaFila.getValue()).getAgenciaLlegada().getId());
		String shoraLlegada = ((DetalleItinerario) ultimaFila.getValue()).getHoraLlegada();
		fehcaLlegada= ((DetalleItinerario) ultimaFila.getValue()).getFechaLlegada();
		
		/*Recupera Ruta Mayor*/
		TreeMap<String, Object> busqRutaMayorID = new TreeMap<String, Object>();
		busqRutaMayorID.put("origen", origen);
		busqRutaMayorID.put("destino", destino);
		busqRutaMayorID.put("estadoRegistro", Constantes.VALUE_ACTIVO);
		List<?> resultRutaMayorID = ServiceLocator.getRutaManager().buscarPorX(busqRutaMayorID, null);
		
		Long id = (textboxId.getText().equals("") ? 0 : new Long(textboxId.getText()));
		
		Listitem itemDetalleItinerario = listboxDetalleItinerario.getItemAtIndex(0);
//		otipoItinerario.setId(((DetalleItinerario) itemDetalleItinerario.getValue()).getItinerario().getTipoItinerario().getId());
		otipoItinerario.setId(((TipoItinerario)cmbTipoItinerario.getSelectedItem().getValue()).getId());
		oservicio.setId(((DetalleItinerario) itemDetalleItinerario.getValue()).getItinerario().getServicio().getId());
		oservicio.setDenominacion(((DetalleItinerario) itemDetalleItinerario.getValue()).getItinerario().getServicio().getDenominacion());
	
		oruta = ((Ruta) resultRutaMayorID.get(0));
		oitinerario.setId(id);
		oitinerario.setTipoItinerario(otipoItinerario);
		
		oitinerario.setRuta(oruta);
		
		oitinerario.setServicio(oservicio);
		oitinerario.setAgenciaPartida(oagenciaPartida);
		oitinerario.setAgenciaLlegada(oagenciaLlegada);
		oitinerario.setFechaPartida(fechaPartida);
		oitinerario.setHoraPartida(shoraPartida);
		oitinerario.setFechaLlegada(fehcaLlegada);
		oitinerario.setHoraLlegada(shoraLlegada);
		oitinerario.setEsAnulado(0);
		oitinerario.setSecuenciaTramo(secuenciaTramo());
		oitinerario.setEstadoRegistro(Constantes.VALUE_ACTIVO);
		switch (action) {
			case ACTION_NEW:
				UtilData.auditarRegistro(oitinerario, getUsuario(), Executions.getCurrent());
				ServiceLocator.getItinerarioManager().guardar(oitinerario);
				break;
			case ACTION_MODIFY:
				UtilData.auditarRegistro(oitinerario, true, getUsuario(), Executions.getCurrent());
				ServiceLocator.getItinerarioManager().actualizar(oitinerario);
				break;
		}

	}
	
	/**
	 * Genera la Segucuencia para el Itinerario
	 * 
	 * @return :Secuencia
	 */
	public String secuenciaTramo(){
		SecuenciaTramo osecuenciaTramo = new SecuenciaTramo();
		String SecuenciTramo="";
		for (int i=0; i< lbxDetalleItinerario.getItems().size(); i ++){
			Listitem itemDetalleItinerario = lbxDetalleItinerario.getItemAtIndex(i);
			
			osecuenciaTramo.setOrigen(((DetalleItinerario) itemDetalleItinerario.getValue()).getRuta().getLocalidadOrigen().getId());
			osecuenciaTramo.setDestino(((DetalleItinerario) itemDetalleItinerario.getValue()).getRuta().getLocalidadDestino().getId());
			osecuenciaTramo.setOrden(i + 1);
			
			if (osecuenciaTramo.getOrden() == 1){
				if (SecuenciTramo.equals("")){
					if (lbxDetalleItinerario.getItems().size()==1)
						SecuenciTramo = osecuenciaTramo.getOrigen() +"-"+ osecuenciaTramo.getDestino() +"-"+ osecuenciaTramo.getOrden();
					else
						SecuenciTramo = osecuenciaTramo.getOrigen() +"-"+ osecuenciaTramo.getDestino() +"-"+ osecuenciaTramo.getOrden() +";";
				}
			}else{
				if (i == lbxDetalleItinerario.getItems().size() -1){
					SecuenciTramo = SecuenciTramo + osecuenciaTramo.getOrigen() +"-"+ osecuenciaTramo.getDestino() +"-"+ osecuenciaTramo.getOrden();
				}else{
					SecuenciTramo = SecuenciTramo + osecuenciaTramo.getOrigen() +"-"+ osecuenciaTramo.getDestino() +"-"+ osecuenciaTramo.getOrden() +";";
				}
			}
				
		}	
		return SecuenciTramo;
	}
	
	/**
	 * Guarda el Detalle del Itinerario.
	 * 
	 * @param action	: action 0=Nuevo; 1=Modificar 
	 * @throws Exception
	 */
	public void guardaItinerarioDetalle(int action, Listbox listboxDetalleItinerario) throws Exception {
		
		List<DetalleItinerario>lstDetalleItinerarioOrg=new ArrayList<DetalleItinerario>();
		if (action==ACTION_MODIFY){
			/*###Custom 19/05/2015 - jabanto para poder recuperar la tarifa lista*/
			TreeMap<String, Object>criterioBusqueda=new TreeMap<String, Object>();
			criterioBusqueda.put("itinerario", oitinerario);
			criterioBusqueda.put("estadoRegistro", Constantes.VALUE_ACTIVO);
			lstDetalleItinerarioOrg=ServiceLocator.getDetalleItinerarioManager().buscarPorX(criterioBusqueda, null);
			
			/*Elimina el detalle del itinerario*/
			ServiceLocator.getDetalleItinerarioManager().delete(oitinerario.getId());	
		}
		
		for (int x=0; x < listboxDetalleItinerario.getItems().size(); x ++){
			Listitem itemDetalleItinerario = listboxDetalleItinerario.getItemAtIndex(x);
			String origen = ((DetalleItinerario) itemDetalleItinerario.getValue()).getRuta().getOrigen();
//			DetalleItinerario detalle = (DetalleItinerario) itemDetalleItinerario.getValue();
			for (int y = x; y < listboxDetalleItinerario.getItems().size(); y ++) {
				Listitem itemDetalleItinerarios = listboxDetalleItinerario.getItemAtIndex(y);
				
				Long id = (long) 0;
				DetalleItinerario odetalleItinerario = new DetalleItinerario();
				Ruta oruta = new Ruta();
				Agencia oagenciaPartida = new Agencia();
				Agencia oagenciaLlegada = new Agencia();
				
				/*Para recuperar el id de la Ruta y la Tarifa por Ruta*/
				String destino = ((DetalleItinerario) itemDetalleItinerarios.getValue()).getRuta().getDestino();
//				String ruta = origen + " - " + destino;
				Double tarifaRuta=(double) 0;
				
				/*Recupera el idRuta*/
				if (origen.equals("")) {
					criteriosBusqueda.remove("origen");
				}else {criteriosBusqueda.put("origen", "%" + origen + "%");}
				if (destino.equals("")) {
					criteriosBusqueda.remove("destino");
				}else {criteriosBusqueda.put("destino", "%" + destino + "%");}
				criteriosBusqueda.put("estadoRegistro", Constantes.VALUE_ACTIVO);
				ArrayList<Ruta> listarRegistros = ServiceLocator.getRutaManager().buscarPorX(criteriosBusqueda, null);
				oruta.setId(listarRegistros.get(0).getId());
//				tarifaRuta = new Double(0);
				
//				for(int i=0; i<grdRutasTrifas.getRows().getChildren().size();i++){
//					Row it = (Row)grdRutasTrifas.getRows().getChildren().get(i);
//					String stRuta = ((Label)it.getChildren().get(2)).getValue();
//					if (stRuta.equals(ruta)) {
//						oruta.setId(new Integer(((Label)it.getChildren().get(0)).getValue()));
//						//tarifaRuta =(new  Double (((Textbox)it.getChildren().get(3)).getText()));
//						tarifaRuta =(new  Double (((Decimalbox)it.getChildren().get(3)).doubleValue()));
//						if (tarifaRuta== null || tarifaRuta.toString().trim().isEmpty())
//							tarifaRuta=0.00;
//						break;
//					}
//				}
				
				oagenciaPartida=(((DetalleItinerario) itemDetalleItinerario.getValue()).getAgenciaPartida()); //setea el idAgencia al Objeto oagenciaPartida
				oagenciaLlegada=(((DetalleItinerario) itemDetalleItinerarios.getValue()).getAgenciaLlegada());

				odetalleItinerario.setId(id);
				odetalleItinerario.setItinerario(oitinerario);
				odetalleItinerario.setRuta(oruta);
				odetalleItinerario.setAgenciaPartida(oagenciaPartida);
				odetalleItinerario.setFechaPartida(((DetalleItinerario) itemDetalleItinerario.getValue()).getFechaPartida());
				odetalleItinerario.setHoraPartida(((DetalleItinerario) itemDetalleItinerario.getValue()).getHoraPartida());
				odetalleItinerario.setAgenciaLlegada(oagenciaLlegada);
				odetalleItinerario.setFechaLlegada(((DetalleItinerario) itemDetalleItinerarios.getValue()).getFechaLlegada());
				odetalleItinerario.setHoraLlegada(((DetalleItinerario) itemDetalleItinerarios.getValue()).getHoraLlegada());
				odetalleItinerario.setTarifa(tarifaRuta);
				odetalleItinerario.setEstadoRegistro(Constantes.VALUE_ACTIVO);
					
				//###Custom 19/05/2015 - jabanto
				/*Para recuperar la tarifa lista, si es que se trate de una actualizacion*/
				Double tarifaLista=.00;
//				for(final DetalleItinerario detalleItinerario: lstDetalleItinerarioOrg){
//					if(detalleItinerario.getRuta().getId().intValue()==oruta.getId().intValue()){
//						tarifaLista=detalleItinerario.getTarifaLista()!=null?detalleItinerario.getTarifaLista():.00;
//						break;
//					}
//				}
				odetalleItinerario.setTarifaLista(tarifaLista);
				
				
				UtilData.auditarRegistro(odetalleItinerario, getUsuario(), Executions.getCurrent());
				ServiceLocator.getDetalleItinerarioManager().guardar(odetalleItinerario);
			}		
		}
	}
	
	/**
	 * Graba Itinerario Agenica Partida
	 * 
	 * @param action 0=Nuevo; 1=Modificar 
	 * @throws Exception
	 */
	public void guardaAgenciaPartida(int action) throws Exception{
		
		for(int i=0; i<lbxDetalleItinerario.getItems().size(); i++) {
			Listitem item = lbxDetalleItinerario.getItems().get(i);
//	    	Agencia oAgenciaPartida = new Agencia();
			
			if (action==ACTION_MODIFY){
				ServiceLocator.getItinerarioAgenciaPartidaManager().delete(oitinerario.getId(), ((DetalleItinerario)item.getValue()).getRuta().getLocalidadOrigen().getId());	
			}
	    	
	    	List<ItinerarioAgenciaPartida> lstItinerarioAgenciaPartida = ((DetalleItinerario)item.getValue()).getLstItinerarioAgenciaPartida();
	    	
	    	for(ItinerarioAgenciaPartida itinerarioAgenciaPartida : lstItinerarioAgenciaPartida) {
	    		ItinerarioAgenciaPartida oItinerarioAgenciaPartida = new ItinerarioAgenciaPartida();
		    	ItinerarioAgenciaPartidaID oItinerarioAgenciaPartidaID = new ItinerarioAgenciaPartidaID();
		    	oItinerarioAgenciaPartidaID.setIdItinerario(oitinerario.getId());
		    	oItinerarioAgenciaPartidaID.setIdAgencia(itinerarioAgenciaPartida.getAgencia().getId());
		    	
		    	oItinerarioAgenciaPartida.setItinerarioAgenciaPartidaID(oItinerarioAgenciaPartidaID);
		    	oItinerarioAgenciaPartida.setItinerario(oitinerario);
		    	oItinerarioAgenciaPartida.setAgencia(itinerarioAgenciaPartida.getAgencia());
		    	oItinerarioAgenciaPartida.setLocalidad(itinerarioAgenciaPartida.getLocalidad());
		    	oItinerarioAgenciaPartida.setHoraPartida(itinerarioAgenciaPartida.getHoraPartida());
		    	oItinerarioAgenciaPartida.setEstadoRegistro(Constantes.VALUE_ACTIVO);
		    	UtilData.auditarRegistro(oItinerarioAgenciaPartida, getUsuario(), Executions.getCurrent());
		    	ServiceLocator.getItinerarioAgenciaPartidaManager().guardar(oItinerarioAgenciaPartida);
	    	}	    	
		}
	}
	
	/**
	 * Graba Itinerario Agenica Llegada
	 * 
	 * @param action 0=Nuevo; 1=Modificar 
	 * @throws Exception
	 */
	public void guardaAgenciaLlegada(int action) throws Exception{
		for(int i=0; i<lbxDetalleItinerario.getItems().size(); i++) {
			Listitem item = lbxDetalleItinerario.getItems().get(i);			
			
			if (action==ACTION_MODIFY){
				ServiceLocator.getItinerarioAgenciaLlegadaManager().delete(oitinerario.getId(), ((DetalleItinerario)item.getValue()).getRuta().getLocalidadDestino().getId());	
			}
			
			List<ItinerarioAgenciaLlegada> lstItinerarioAgenciaLlegada = ((DetalleItinerario)item.getValue()).getLstItinerarioAgenciaLlegada();
	    	
	    	for(ItinerarioAgenciaLlegada itinerarioAgenciaLlegada : lstItinerarioAgenciaLlegada) {
	    		ItinerarioAgenciaLlegada oItinerarioAgenciaLlegada = new ItinerarioAgenciaLlegada();
		    	ItinerarioAgenciaLlegadaID oItinerarioAgenciaLlegadaID = new ItinerarioAgenciaLlegadaID();
		    	oItinerarioAgenciaLlegadaID.setIdItinerario(oitinerario.getId());
		    	oItinerarioAgenciaLlegadaID.setIdAgencia(itinerarioAgenciaLlegada.getAgencia().getId());
		    	
		    	oItinerarioAgenciaLlegada.setItinerarioAgenciaLlegadaID(oItinerarioAgenciaLlegadaID);
		    	oItinerarioAgenciaLlegada.setItinerario(oitinerario);
		    	oItinerarioAgenciaLlegada.setAgencia(itinerarioAgenciaLlegada.getAgencia());
		    	oItinerarioAgenciaLlegada.setLocalidad(itinerarioAgenciaLlegada.getLocalidad());
		    	oItinerarioAgenciaLlegada.setHoraLlegada(itinerarioAgenciaLlegada.getHoraLlegada());
		    	oItinerarioAgenciaLlegada.setEstadoRegistro(Constantes.VALUE_ACTIVO);
		    	UtilData.auditarRegistro(oItinerarioAgenciaLlegada, getUsuario(), Executions.getCurrent());
		    	ServiceLocator.getItinerarioAgenciaLlegadaManager().guardar(oItinerarioAgenciaLlegada);
	    	}	    	
		}
		
//	    for (int x =0; x < lbxTerminalLlegada.getItems().size(); x ++){
//	    	Listitem itemTerminalLlegada = lbxTerminalLlegada.getItemAtIndex(x);
//	    	
//	    	ItinerarioAgenciaLlegada oItinerarioAgenciaLlegada = new ItinerarioAgenciaLlegada();
//	    	ItinerarioAgenciaLlegadaID oItinerarioAgenciaLlegadaID = new ItinerarioAgenciaLlegadaID();
//	    	Agencia oagenciaLlegada= new Agencia();
//	    	
//	    	oagenciaLlegada.setId(((ItinerarioAgenciaLlegada) itemTerminalLlegada.getValue()).getAgencia().getId());
//	    	oItinerarioAgenciaLlegadaID.setIdItinerario(oitinerario.getId());
//	    	oItinerarioAgenciaLlegadaID.setIdAgencia(oagenciaLlegada.getId());
//	    	
//	    	oItinerarioAgenciaLlegada.setItinerarioAgenciaLlegadaID(oItinerarioAgenciaLlegadaID);
//	    	oItinerarioAgenciaLlegada.setItinerario(oitinerario);
//	    	oItinerarioAgenciaLlegada.setAgencia(oagenciaLlegada);
//	    	oItinerarioAgenciaLlegada.setLocalidad(((ItinerarioAgenciaLlegada) itemTerminalLlegada.getValue()).getLocalidad());
//	    	oItinerarioAgenciaLlegada.setHoraLlegada(((ItinerarioAgenciaLlegada) itemTerminalLlegada.getValue()).getHoraLlegada());
//	    	oItinerarioAgenciaLlegada.setEstadoRegistro(Constantes.VALUE_ACTIVO);
//	    	
//	    	UtilData.auditarRegistro(oItinerarioAgenciaLlegada, getUsuario(), Executions.getCurrent());
//	    	ServiceLocator.getItinerarioAgenciaLlegadaManager().guardar(oItinerarioAgenciaLlegada);
//	    }
	}
	
	/**
	 * Carga lista de itinerarios según la busqueda.
	 * 	
	 * @param lstItinerarios	: Lista de itinerarios recuperados en la busqueda 
	 * @throws Exception 
	 */
	private void listarItinerarios(List<DetalleItinerario> lstItinerarios) throws Exception{
		Util.limpiarListbox(listboxLista);
		if(lstItinerarios.size()>0){
			Listitem item = null;
			Listcell cell = null;
			Integer x =0;
			for(DetalleItinerario detalleItinerario : lstItinerarios){
				item = new Listitem();
				x += +1;
				cell = new Listcell((x.toString()));
				cell.setStyle("font-size:11px !important");
				item.appendChild(cell); //Correlativo
				cell = new Listcell(detalleItinerario.getItinerario().getId().toString());
				cell.setStyle("font-size:11px !important");
				item.appendChild(cell);
				cell = new Listcell(detalleItinerario.getRuta().getOrigen());
				item.appendChild(cell);
				cell = new Listcell(detalleItinerario.getRuta().getDestino());
				item.appendChild(cell);
				cell = new Listcell(detalleItinerario.getItinerario().getServicio().getDenominacion());
				item.appendChild(cell);
				cell = new Listcell(Util.toFechaNombreDiaMes(detalleItinerario.getFechaPartida()));
				cell.setTooltiptext(Util.toFechaNombreDiaMesLong(detalleItinerario.getFechaPartida()));
				cell.setStyle("font-size:9px !important");
				item.appendChild(cell);
				cell = new Listcell(detalleItinerario.getHoraPartida());
				cell.setStyle("font-size:11px !important");
				item.appendChild(cell);
				cell = new Listcell(Util.toFechaNombreDiaMes(detalleItinerario.getFechaLlegada()));
				cell.setTooltiptext(Util.toFechaNombreDiaMesLong(detalleItinerario.getFechaLlegada()));
				cell.setStyle("font-size:9px !important");
				item.appendChild(cell);	
				cell = new Listcell(detalleItinerario.getHoraLlegada());
				cell.setStyle("font-size:11px !important");
				item.appendChild(cell);
				cell = new Listcell(detalleItinerario.getAgenciaPartida().getNombreCorto());
				item.appendChild(cell);
				cell = new Listcell(detalleItinerario.getAgenciaLlegada().getNombreCorto());
				item.appendChild(cell);
				cell = new Listcell();
				Hbox hbox = new Hbox();
				Button btnHora = new Button();
				btnHora.addEventListener(Events.ON_CLICK, new EventListener<Event>() {
					public void onEvent(Event e) {
						DetalleItinerario detalle = ((Listitem)e.getTarget().getParent().getParent().getParent()).getValue();
						cambiarHorario(detalle);
					}
				});
				btnHora.setImage("/resources/mp_reloj.png");
				btnHora.setTooltiptext("Cambiar la hora del Itinerario");
				hbox.appendChild(btnHora);
				Button btnDuplicar = new Button();
				btnDuplicar.addEventListener(Events.ON_CLICK, new EventListener<Event>() {
					public void onEvent(Event e) {
						DetalleItinerario detalle = ((Listitem)e.getTarget().getParent().getParent().getParent()).getValue();
						crearVentanaClonacion(detalle);
					}
				});
				btnDuplicar.setImage("/resources/mp_clone.png");
				btnDuplicar.setTooltiptext("Duplicar el Itinerario");
				hbox.appendChild(btnDuplicar);
				cell.appendChild(hbox);
				item.appendChild(cell);
				
				item.setValue(detalleItinerario);
				listboxLista.appendChild(item);
			}
		}
	}
		
	/**
	 * Recupera Datos del itinerario seleccionado para la edición
	 * 
	 * @param id: identificador unico del itinerario.
	 * @throws Exception
	 */
	private void manteniemientoItinerario(Long id) throws Exception{	
		oitinerario=ServiceLocator.getItinerarioManager().buscarPorId(id);
		
		dbFechaItinerario.setText("");
		/*Recupera el itinerario ***/
		textboxId.setText(oitinerario.getId().toString());
		dbFechaInicio.setValue(oitinerario.getFechaPartida());
		dbFechafin.setValue(oitinerario.getFechaPartida());
		
		/*Recupera detalle del Itinerario***/
		lstItinerariosDetalle= ServiceLocator.getItinerarioManager().buscarItinerariosMantenimiento(id, "", "", "", "", "","","");
		
		if(lstItinerariosDetalle.size()>0){
			Listitem item = null;
			Listcell cell = null;
			Integer x =0; /*Contador utilizado para el item.*/
			Integer primerIndex =0;
			String destino = null; /*Almacena el ultimo destino cargado en el listBoxdetalleItinerario, para la validacion con el origen.*/
			String idDestino = null;
			
			DetalleItinerario oDetalleItinerario =lstItinerariosDetalle.get(0);
			destino =oDetalleItinerario.getRuta().getDestino();
			idDestino = oDetalleItinerario.getRuta().getLocalidadDestino().getId().toString();
			Util.seleccionarValorItemCombo(TipoItinerario.class, cmbTipoItinerario, oDetalleItinerario.getItinerario().getTipoItinerario().getId());
			Util.seleccionarValorItemCombo(Servicio.class, cmbServicio, oitinerario.getServicio().getId());
			Util.seleccionarValorItemCombo(Localidad.class, cmbOrigen, oitinerario.getRuta().getLocalidadDestino().getId());
			
			onChangeRutas();
			
			/*Validación que evita la duplicidad de tramos, en el Detalle del Itinerario*/
			for(DetalleItinerario detalleItinerario : lstItinerariosDetalle){
				primerIndex += +1;
				String origen = detalleItinerario.getRuta().getOrigen();
				String idOrigen = detalleItinerario.getRuta().getLocalidadOrigen().getId().toString();
				if (destino.equals(origen) || primerIndex==1){
					x += +1;
					
					destino=detalleItinerario.getRuta().getDestino();
					idDestino = detalleItinerario.getRuta().getLocalidadDestino().getId().toString();
					obtenerAgenciaPartidaLlegada(detalleItinerario, idOrigen, true);
					obtenerAgenciaPartidaLlegada(detalleItinerario, idDestino, false);
					agregarItinerario(detalleItinerario, lbxDetalleItinerario);
				}
			}
			
			autoSeleccionarAgenciaOrigen(null, ((DetalleItinerario)lbxDetalleItinerario.getItems().get(lbxDetalleItinerario.getItems().size()-1).getValue()).getLstItinerarioAgenciaLlegada(), true);
			
//			String localidadPartida = "";
//			String localidadLlegada = "";
//			for(Listitem itDetalle:lbxDetalleItinerario.getItems()) {
//				DetalleItinerario detalle = ((DetalleItinerario)itDetalle.getValue());
//				if(!localidadPartida.isEmpty())
//					localidadPartida = localidadPartida+",";
//				localidadPartida = localidadPartida + detalle.getRuta().getLocalidadOrigen().getId();
//				
//				if(!localidadLlegada.isEmpty())
//					localidadLlegada = localidadLlegada+",";
//				localidadLlegada = localidadLlegada + detalle.getRuta().getLocalidadDestino().getId();
//			}
	
			Listitem it = lbxDetalleItinerario.getItems().get(lbxDetalleItinerario.getItems().size()-1);
			/*Carga Combobox Terminal Partida*/
			cargarTerminalPartidaLlegada(cmbTerminalPartida, false, ((DetalleItinerario)it.getValue()).getRuta().getLocalidadOrigen().getId(), 1);
				
			/*Carga Combobox Terminal Llegada*/
			cargarTerminalPartidaLlegada(cmbTerminalLlegada, false, ((DetalleItinerario)it.getValue()).getRuta().getLocalidadDestino().getId(), 2);
			
	
			/*Recupera Itinerario Agencia partida**/
			DetalleItinerario ultimoTramo = lstItinerariosDetalle.get(lstItinerariosDetalle.size()-1);
			List<ItinerarioAgenciaPartida> lstItinerarioAgenciaPartida = null;	
			lstItinerarioAgenciaPartida = ServiceLocator.getItinerarioAgenciaPartidaManager().buscarAgenciasPartida(id, Constantes.VALUE_ACTIVO, ultimoTramo.getRuta().getLocalidadOrigen().getId().toString());
			if(lstItinerariosDetalle.size()>0){
				item = null;
				 cell = null;
				 x =0; /*Contador utilizado para el item.*/
					
				for(ItinerarioAgenciaPartida itinerarioAgenciaPartida : lstItinerarioAgenciaPartida){
					x += +1;
					item = new Listitem();
					cell = new Listcell((x.toString()));
					item.appendChild(cell); //Correlativo
					cell = new Listcell(itinerarioAgenciaPartida.getAgencia().getNombreCorto());
					item.appendChild(cell);
					cell = new Listcell(itinerarioAgenciaPartida.getHoraPartida());
					cell.setStyle("font-size:11px !important");
					item.appendChild(cell);
					
//					final Image image= new Image();				
//					image.setHeight("17px");
//					cell = new Listcell();
//					cell .appendChild(image);
//					item.appendChild(cell);
//					
//					image.addEventListener(Events.ON_CLICK, new EventListener<Event>() {
//						@Override
//						public void onEvent(Event event) throws Exception {
//							if(image.getSrc().equals(enabledDelete))
//								quitaTerminal(true, lbxTerminalPartida);
//						}
//					});
					
					item.setValue(itinerarioAgenciaPartida);
					lbxTerminalPartida.appendChild(item);	
//					asignarImagenEliminarTerminal(lbxTerminalPartida);
					
				}
			}
			
			/*Recupera Itinerario Agencia Llegada**/
			List<ItinerarioAgenciaLlegada> lstItinerarioAgenciaLlegada;
			lstItinerarioAgenciaLlegada= ServiceLocator.getItinerarioAgenciaLlegadaManager().buscarAgenciasLlegada(id, Constantes.VALUE_ACTIVO, ultimoTramo.getRuta().getLocalidadDestino().getId().toString());
			if(lstItinerariosDetalle.size()>0){
				item = null;
				cell = null;
				x =0; /**Contador utilizado para el item.*/
				
				for(ItinerarioAgenciaLlegada itinerarioAgenciaLlegada : lstItinerarioAgenciaLlegada){
					x += +1;
					item = new Listitem();
					cell = new Listcell((x.toString()));
					item.appendChild(cell); //Correlativo
					cell = new Listcell(itinerarioAgenciaLlegada.getAgencia().getNombreCorto());
					item.appendChild(cell);
					cell = new Listcell(itinerarioAgenciaLlegada.getHoraLlegada());
					cell.setStyle("font-size:11px !important");
					item.appendChild(cell);
					
//					final Image image= new Image();				
//					image.setHeight("17px");
//					cell = new Listcell();
//					cell .appendChild(image);
//					item.appendChild(cell);
//					
//					image.addEventListener(Events.ON_CLICK, new EventListener<Event>() {
//						@Override
//						public void onEvent(Event event) throws Exception {
//							if(image.getSrc().equals(enabledDelete))
//								quitaTerminal(false, lbxTerminalLlegada);
//						}
//					});
					
					item.setValue(itinerarioAgenciaLlegada);
					lbxTerminalLlegada.appendChild(item);	
//					asignarImagenEliminarTerminal(lbxTerminalLlegada);
				}
			}
		}
	}
	
	private void obtenerAgenciaPartidaLlegada(DetalleItinerario detalleItinerario, String localidad, boolean esPartida) {
		try {
			if(esPartida) {
				List<ItinerarioAgenciaPartida> lstItinerarioAgenciaPartida;	
				lstItinerarioAgenciaPartida= ServiceLocator.getItinerarioAgenciaPartidaManager().buscarAgenciasPartida(detalleItinerario.getItinerario().getId(), Constantes.VALUE_ACTIVO, localidad);
				detalleItinerario.setLstItinerarioAgenciaPartida(lstItinerarioAgenciaPartida);;
			}else {
				List<ItinerarioAgenciaLlegada> lstItinerarioAgenciaLlegada;	
				lstItinerarioAgenciaLlegada= ServiceLocator.getItinerarioAgenciaLlegadaManager().buscarAgenciasLlegada(detalleItinerario.getItinerario().getId(), Constantes.VALUE_ACTIVO, localidad);
				detalleItinerario.setLstItinerarioAgenciaLlegada(lstItinerarioAgenciaLlegada);;
			}
		}catch (Exception ex) {
			System.out.println(ex.getMessage());
		}
	}
	
	/**
	 * Validación de itinerarios duplicados.
	 * 
	 * @throws Exception 
	 */
	private Boolean isTramoDuplicado(DetalleItinerario detalleItinerario) throws Exception{
//		try{
			 listTramoDuplicado =ServiceLocator.getItinerarioManager().validadIngresoTramos(detalleItinerario.getItinerario().getTipoItinerario().getId(),
					  detalleItinerario.getItinerario().getServicio().getId(), 
					  detalleItinerario.getRuta().getId(), 
					  Constantes.FORMAT_DATE.format(detalleItinerario.getFechaPartida()),
					  detalleItinerario.getHoraPartida(), detalleItinerario.getAgenciaPartida().getId(),
					  detalleItinerario.getAgenciaLlegada().getId());
			 
			if (action==ACTION_MODIFY){
				for (int i =0; i < listTramoDuplicado.size(); i ++){
					if (! (oitinerario.getId().equals(listTramoDuplicado.get(i).getItinerario().getId())) )
						return true;//throw new ItinerarioException(ItinerarioException.TRAMOS_DUPLICADOS); // ItinerarioTramosDuplicados();
				}
			}else{
				if (listTramoDuplicado.size() > 0)
					return true;
					//throw new ItinerarioException(ItinerarioException.TRAMOS_DUPLICADOS); // ItinerarioTramosDuplicados();
			}
		
			
			return false;
			
//		}catch (ItinerarioException itdex){
//			if(itdex.getTipo().intValue()==ItinerarioException.TRAMOS_DUPLICADOS)
//				return true;
//				//throw new ItinerarioException(ItinerarioException.TRAMOS_DUPLICADOS); //ItinerarioTramosDuplicados();
//		}
	}

	private Window createVentanaItinerarios(final List<VentaPasaje> list){
		Caption caption = null;
		final Window window = new Window("", "normal", false);
		window.setWidth("810px");
		window.setHeight("350px");
		caption = new Caption("ITINERARIOS CON VENTAS O RESERVAS");
		window.appendChild(caption);
		
		final Listbox listbox= new Listbox();
		Listhead listhead= new Listhead();
		//Itinerario
		Listheader listheader= new Listheader();
		listheader.setLabel("ITINERARIO");
		listheader.setWidth("60px");
		listhead.appendChild(listheader);
		//Ruta
		listheader= new Listheader();
		listheader.setLabel("RUTA");
		listheader.setWidth("170px");
		listhead.appendChild(listheader);
		//Fecha Partida
		listheader= new Listheader();
		listheader.setLabel("F.PARTIDA");
		listheader.setTooltiptext("Fecha de Partida");
		listheader.setWidth("70px");
		listhead.appendChild(listheader);
		listbox.appendChild(listhead);
		//Hora Partida
		listheader= new Listheader();
		listheader.setLabel("H.PARTIDA");
		listheader.setTooltiptext("Hora de Partida");
		listheader.setWidth("50px");
		listhead.appendChild(listheader);
		listbox.appendChild(listhead);
		//Pasajero
		listheader= new Listheader();
		listheader.setLabel("PASAJERO");
		listheader.setWidth("195px");
		listhead.appendChild(listheader);
		listbox.appendChild(listhead);
		//Asiento
		listheader= new Listheader();
		listheader.setLabel("ASIENTO");
		listheader.setWidth("45px");
		listheader.setAlign("center");
		listhead.appendChild(listheader);
		listbox.appendChild(listhead);
		//Tipo transancion(Venta, reserva)
		listheader= new Listheader();
		listheader.setLabel("TIPO");
		listheader.setWidth("65px");
		listheader.setAlign("center");
		listhead.appendChild(listheader);
		listbox.appendChild(listhead);
		//Número Boleto
		listheader= new Listheader();
		listheader.setLabel("N.CONTROL");
		listheader.setWidth("115px");
		listheader.setAlign("center");
		listhead.appendChild(listheader);
		listbox.appendChild(listhead);
				
		Listitem item=null;
		Listcell cell=null;
		Util.limpiarListbox(listbox);
				
		for(VentaPasaje ventaPasaje : list){
			Itinerario itinerario=ventaPasaje.getItinerario();
			Ruta ruta=ventaPasaje.getRuta();
			Pasajero pasajero=ventaPasaje.getPasajero();

			item=new Listitem();
			cell=new Listcell(itinerario.getId().toString());
			cell.setStyle("font-size:11px !important");
			item.appendChild(cell);
			cell=new Listcell(ruta.getOrigen()+" - "+ruta.getDestino());
			item.appendChild(cell);
			cell=new Listcell(ventaPasaje.getFechaPartida()!=null?Constantes.FORMAT_DATE.format(ventaPasaje.getFechaPartida()): "");
			cell.setStyle("font-size:11px !important");
			item.appendChild(cell);
			cell=new Listcell(ventaPasaje.getHoraPartida()!=null? ventaPasaje.getHoraPartida(): "");
			cell.setStyle("font-size:11px !important");
			item.appendChild(cell);
			cell=new Listcell(pasajero.getNombresApellidos());
			item.appendChild(cell);
			cell=new Listcell(ventaPasaje.getNumeroAsiento().toString());
			cell.setStyle("font-size:11px !important");
			item.appendChild(cell);
			if(ventaPasaje.getTipoTransaccion().equals(Constantes.TIPO_OPERACION_RESERVA))
				cell=new Listcell("RESERVA");
			else cell=new Listcell("VENTA");
			item.appendChild(cell);
			cell=new Listcell(ventaPasaje.getNumeroControl()!=null? ventaPasaje.getNumeroControl(): "");
			cell.setStyle("font-size:11px !important");
			item.appendChild(cell);
			
			item.setValue(ventaPasaje);
			listbox.appendChild(item);		
		}
		listbox.setHeight("286px");
		window.appendChild(listbox);
				
		
		Label label= new Label();
		Hbox hbox= new Hbox();
		hbox.setAlign("center");
		
		label.setValue("Cantidad Registros : "+listbox.getItems().size());
		label.setStyle("font-size:11px !important; color:red; font-weight: bold;");
		hbox.appendChild(label);
		
		Button btnCerrar= new Button();
		btnCerrar.setImage("/resources/mp_cerrar.png");
		btnCerrar.setLabel("Cerrar");
		
		Button btnExportar= new Button();
		btnExportar.setImage("/resources/mp_excel.png");
		btnExportar.setLabel("Exportar");
				
		Separator separator= new Separator();
		separator.setWidth("480px");
		hbox.appendChild(separator);
		
		hbox.appendChild(btnExportar);
		hbox.appendChild(btnCerrar);
		window.appendChild(hbox);
				
		btnExportar.addEventListener(Events.ON_CLICK,new EventListener<Event>() {
			@Override
			public void onEvent(Event event) throws Exception {
				String nombreArchivo=Util.adjuntarFechaHoraExportacion("Itinerarios con ventas");
		        Filedownload.save(Util.generaTablaHtml(listbox), "application/vnd.ms-excel", nombreArchivo+".xls");
			}
		});
		
		
		btnCerrar.addEventListener(Events.ON_CLICK, new EventListener<Event>() {
			@Override
			public void onEvent(Event event) throws Exception {		
				Messagebox.show(Messages.getString("WndItinerario.Question.ConfirmaCloseVentanaVentas"),DlgMessage.NOMBREAPLICACION, DlgMessage.BTN_YESNO, Messagebox.QUESTION,DlgMessage.BTN_DEFAULT_NO, new EventListener<Event>() {
					@Override
					public void onEvent(Event e) throws Exception {
						if(e.getName().equals("onYes")){
							window.onClose();
						}
					}
				});
			}
		});
		return window;
	}
	
	private void ventanaItinerarios(List<VentaPasaje> list){
		wndItinerarios = createVentanaItinerarios(list);
		this.appendChild(wndItinerarios);
		wndItinerarios.setMode("modal");
		DlgMessage.information(Messages.getString("WndItinerario.information.ItinerarioConVentas"));
	}
	
	/**
	 * Valida la diferencia de la fecha y hora de llegada y de salida a la ciudad  - 04/01/2017 - jabanto
	 * @param detalleItinerario	: Instancia de la class detalleItinerario
	 * @return	true = si es correcto, false= lo contrario
	 * @throws Exception
	 */
	private boolean validarDiferenciaHorasTramo(DetalleItinerario detalleItinerario)throws Exception{
		boolean isCorrect=true;
		
		/*Valida si es al guadar el itinerario o al agregar el tramo*/
		if(detalleItinerario==null){
			for(int x=0; x<lbxDetalleItinerario.getItems().size();x++){
				DetalleItinerario detallIT=lbxDetalleItinerario.getItemAtIndex(x).getValue();
				DetalleItinerario detallFT=null;
				if(lbxDetalleItinerario.getItems().size()> x+1)
					detallFT=lbxDetalleItinerario.getItemAtIndex(x+1).getValue();
				if(detallFT!=null){
					Date dateITLlega=Constantes.FORMAT_LONG.parse(Constantes.FORMAT_DATE.format(detallIT.getFechaLlegada())+" "+detallIT.getHoraLlegada());
					Date dateFTSale=Constantes.FORMAT_LONG.parse(Constantes.FORMAT_DATE.format(detallFT.getFechaPartida())+" "+detallFT.getHoraPartida());
					
					long hrs=(dateFTSale.getTime()-dateITLlega.getTime())/Constantes.MILISEGUNDOS_X_MINUTO;
					//Valida que la diferencia no sea mayor a 60 minutos, segun conversado con Marco - 04/01/2017 - jabanto 
					if(hrs>60){
						isCorrect=false;
						DlgMessage.information("No puede continuar, debido a que existe demasiada diferencia entre la hora de llegada y salida de la ciudad de "+detallIT.getRuta().getDestino()+".");
						break;
					}
				}
			}
			
		}else{
			/*Buscamos el ultimo tramo agregado*/
			DetalleItinerario detallUT=null;
			if(esEdicionTramo==false && lbxDetalleItinerario.getItems().size()>0)
				detallUT=lbxDetalleItinerario.getItemAtIndex(lbxDetalleItinerario.getItems().size()-1).getValue();
			else if (esEdicionTramo && lbxDetalleItinerario.getItems().size()>1){
				detallUT=lbxDetalleItinerario.getItemAtIndex(lbxDetalleItinerario.getItems().size()-2).getValue();
			}
			if(detallUT!=null){
				Date dateITLlega=Constantes.FORMAT_LONG.parse(Constantes.FORMAT_DATE.format(detallUT.getFechaLlegada())+" "+detallUT.getHoraLlegada());
				Date dateFTSale=Constantes.FORMAT_LONG.parse(Constantes.FORMAT_DATE.format(detalleItinerario.getFechaPartida())+" "+detalleItinerario.getHoraPartida());
				
				long hrs=(dateFTSale.getTime()-dateITLlega.getTime())/Constantes.MILISEGUNDOS_X_MINUTO;
				//Valida que la diferencia no sea mayor a 60 minutos, segun conversado con Marco - 04/01/2017 - jabanto 
				if(hrs>60){
					isCorrect=false;
					DlgMessage.information("No puede continuar, debido a que existe demasiada diferencia entre la hora de llegada y salida de la ciudad de "+detallUT.getRuta().getDestino()+".");
				}
			}
		}		
		
		return isCorrect;
	}
	
	/**
	 * Muestra los terminales de Partida y Llegada cuando se selecciona el tramo
	 */
	public void showTerminales() {
		if(lbxDetalleItinerario.getSelectedItem().getValue() instanceof DetalleItinerario) {
			DetalleItinerario detalleItinerario = (DetalleItinerario)lbxDetalleItinerario.getSelectedItem().getValue();
			cargarTerminalPartida(detalleItinerario.getLstItinerarioAgenciaPartida());
			cargarTerminalLlegada(detalleItinerario.getLstItinerarioAgenciaLlegada());
		}
	}
	
	/**
	 * Realiza el llenado del Listbox de terminales de Partida
	 * @param lstItinerarioAgenciaPartida	: Datos a mostrar
	 */
	private void cargarTerminalPartida(List<ItinerarioAgenciaPartida> lstItinerarioAgenciaPartida) {
		lbxTerminalPartida.getItems().clear();
		Listitem item = null;
		Listcell cell = null;
		Integer x = 0;
		for(ItinerarioAgenciaPartida itinerarioAgenciaPartida : lstItinerarioAgenciaPartida) {
			x++;
			item = new Listitem();
			cell = new Listcell(x.toString());
			item.appendChild(cell);
			
			cell = new Listcell(itinerarioAgenciaPartida.getAgencia().getNombreCorto());
			item.appendChild(cell);
			
			cell = new Listcell(itinerarioAgenciaPartida.getHoraPartida());
			cell.setStyle("font-size:11px !important");
			item.appendChild(cell);
			
//			final Image image= new Image();		
//			image.setHeight("17px");
//			image.addEventListener(Events.ON_CLICK, new EventListener<Event>() {
//				@Override
//				public void onEvent(Event event) throws Exception {
//					if(image.getSrc().equals(enabledDelete))
//						quitaTerminal(true, lbxTerminalPartida);
//				}
//			});			
//			cell = new Listcell();
//			cell .appendChild(image);
//			item.appendChild(cell);			
			
			item.setValue(itinerarioAgenciaPartida);
			lbxTerminalPartida.appendChild(item);
//			asignarImagenEliminarTerminal(lbxTerminalPartida);			
		}
	}
	
	/**
	 * Realiza el llenado del Listbox de terminales de Llegada
	 * @param lstItinerarioAgenciaLlegada	: Datos a mostrar
	 */
	private void cargarTerminalLlegada(List<ItinerarioAgenciaLlegada> lstItinerarioAgenciaLlegada) {
		lbxTerminalLlegada.getItems().clear();
		Listitem item = null;
		Listcell cell = null;
		Integer x = 0;
		for(ItinerarioAgenciaLlegada itinerarioAgenciaLlegada : lstItinerarioAgenciaLlegada) {
			x++;
			item = new Listitem();
			cell = new Listcell(x.toString());
			item.appendChild(cell);
			
			cell = new Listcell(itinerarioAgenciaLlegada.getAgencia().getNombreCorto());
			item.appendChild(cell);
			
			cell = new Listcell(itinerarioAgenciaLlegada.getHoraLlegada());
			cell.setStyle("font-size:11px !important");
			item.appendChild(cell);
			
//			final Image image= new Image();		
//			image.setHeight("17px");
//			image.addEventListener(Events.ON_CLICK, new EventListener<Event>() {
//				@Override
//				public void onEvent(Event event) throws Exception {
//					if(image.getSrc().equals(enabledDelete))
//						quitaTerminal(true, lbxTerminalLlegada);
//				}
//			});			
//			cell = new Listcell();
//			cell .appendChild(image);
//			item.appendChild(cell);			
			
			item.setValue(itinerarioAgenciaLlegada);
			lbxTerminalLlegada.appendChild(item);
//			asignarImagenEliminarTerminal(lbxTerminalLlegada);			
		}
	}
	
	private void asignarImagenEliminarTerminal(Listbox listbox){
		if(listbox.getItems().size()>0){
			for(int y=0; y<listbox.getItems().size();y++){
				Component it = listbox.getChildren().get(y+1);
				Listcell cll=(Listcell) it.getChildren().get(3);
			
				if(y==0){
					((Image)cll.getChildren().get(0)).setSrc(disabledDelete);			
					((Image)cll.getChildren().get(0)).setTooltiptext("");
					((Image)cll.getChildren().get(0)).setStyle("cursor:default");
				}else{
					if(btnGuardar.isDisabled()){
						((Image)cll.getChildren().get(0)).setSrc(disabledDelete);			
						((Image)cll.getChildren().get(0)).setTooltiptext("");
						((Image)cll.getChildren().get(0)).setStyle("cursor:default");
					}else{
						((Image)cll.getChildren().get(0)).setSrc(enabledDelete);			
						((Image)cll.getChildren().get(0)).setTooltiptext("Eliminar Tramo");
						((Image)cll.getChildren().get(0)).setStyle("cursor:pointer");
					}
				}
			}
		}
	}
	
	/**
 	 * Elimina el terminal seleccionado
 	 * @param isTerminalPartida	: Variable booleana; true= Terminal Partida, false=Terminal Llegada.
 	 * @param lbxTerminal	: Nombre del ListBox del terminal a Borrar.
 	 * @throws Exception		: ELIMINA EL TERMINAL SELECCIONADO.
 	 */
	public void quitaTerminal (final Boolean isTerminalPartida, final Listbox lbxTerminal) throws Exception{
//		final Listbox ListBoxTerminal = listBoxTerminal;
		Integer index =lbxTerminal.getSelectedIndex();
		if (index > 0){
			Messagebox.show(Messages.getString("WndItinerario.question.EliminaTerminal"), DlgMessage.NOMBREAPLICACION, DlgMessage.BTN_YESNO, Messagebox.QUESTION, new EventListener<Event>() {
				@Override
				public void onEvent(Event e) throws Exception {
					if(e.getName().equals("onYes")){
						Integer index = lbxTerminal.getSelectedIndex();
						lbxTerminal.removeItemAt(index);
						updateTerminales(isTerminalPartida, lbxTerminal);
					}
				}
			});			
		}else{
			if (isTerminalPartida == true)
				DlgMessage.information(Messages.getString("WndItinerario.information.EliminaTerminalPartida"));
			else 
				DlgMessage.information(Messages.getString("WndItinerario.information.EliminaTerminalLlegada"));
		}
	}
	
	/**
	 * Actualiza la grilla lbxDetalleItinerario con los terminales
	 * @param isTerminalPartida	: Indica si el la grilla de terminale sde partida o llegada
	 * @param lbxTerminal	: Grilla a iterar;
	 */
	private void updateTerminales(Boolean isTerminalPartida, Listbox lbxTerminal) {
		if(isTerminalPartida) {
			List<ItinerarioAgenciaPartida> lstItinerarioAgenciaPartida = new ArrayList<ItinerarioAgenciaPartida>();
			for(int i=0; i<lbxTerminal.getItems().size(); i++) {
				ItinerarioAgenciaPartida itinerarioAgenciaPartida = (ItinerarioAgenciaPartida)lbxTerminal.getItems().get(i).getValue();
				lstItinerarioAgenciaPartida.add(itinerarioAgenciaPartida);
			}
			((DetalleItinerario)lbxDetalleItinerario.getSelectedItem().getValue()).setLstItinerarioAgenciaPartida(lstItinerarioAgenciaPartida);
		}else {
			List<ItinerarioAgenciaLlegada> lstItinerarioAgenciaLlegada = new ArrayList<ItinerarioAgenciaLlegada>();
			for(int i=0; i<lbxTerminal.getItems().size(); i++) {
				ItinerarioAgenciaLlegada itinerarioAgenciaLlegada = (ItinerarioAgenciaLlegada)lbxTerminal.getItems().get(i).getValue();
				lstItinerarioAgenciaLlegada.add(itinerarioAgenciaLlegada);
			}
			((DetalleItinerario)lbxDetalleItinerario.getSelectedItem().getValue()).setLstItinerarioAgenciaLlegada(lstItinerarioAgenciaLlegada);
		}
		
	}
	
	private void getAgenciasPartida() {
		lstAgenciaOrigen = new ArrayList<>();
		for(Listitem item : lbxTerminalOrigen.getSelectedItems()) {
			lstAgenciaOrigen.add((Listitem)item.clone());
		}
		
		lstAgenciaDestino = new ArrayList<>();
		for(Listitem item : lbxTerminalDestino.getSelectedItems()) {
			lstAgenciaDestino.add((Listitem)item.clone());
		}
	}
	
	public void cambiarHorario(final DetalleItinerario detalleItinerario) {
		final Window wndCambioHorario = new Window("Cambio de Horario", "normal", true);
		wndCambioHorario.setWidth("620px");
		Groupbox groupbox = new Groupbox();
		groupbox.setClosable(false);
		groupbox.setMold("3d");
		Caption caption = new Caption();
		caption.setLabel("DATOS DEL ITINERARIO");
		caption.setStyle("color: #ffffff;");
		groupbox.appendChild(caption);
		
		Grid grid = new Grid();
		grid.setStyle("border:none");
		Columns columns = new Columns();
		Column column = new Column();
		column.setWidth("110px");
		column.setAlign("right");
		columns.appendChild(column);
		
		column = new Column();
		column.setWidth("170px");
		columns.appendChild(column);
		
		column = new Column();
		column.setWidth("100px");
		column.setAlign("right");
		columns.appendChild(column);
		
		column = new Column();
		//column.setWidth("170px");
		columns.appendChild(column);
		
		grid.appendChild(columns);
		
		Rows rows = new Rows();
		Row row = new Row();
		Div div = new Div();
		String style = "font-weight: bold; font-size:12px !important; color: #4285F4";
		Label label = new Label("TIPO ITINERARIO :");
		div.appendChild(label);
		Textbox txtIdItinerario = new Textbox();
		txtIdItinerario.setVisible(false);
		div.appendChild(txtIdItinerario);
		row.appendChild(div);
		Label lblTipoItinerario = new Label();
		lblTipoItinerario.setValue(detalleItinerario.getItinerario().getTipoItinerario().getDenominacion());
		lblTipoItinerario.setStyle(style);
		row.appendChild(lblTipoItinerario);
		label = new Label("TIPO SERVICIO :");
		row.appendChild(label);
		Label lblTipoServicio = new Label();
		lblTipoServicio.setValue(detalleItinerario.getItinerario().getServicio().getDenominacion());
		lblTipoServicio.setStyle(style);
		row.appendChild(lblTipoServicio);
		rows.appendChild(row);
		
		row = new Row();
		label = new Label("ORIGEN :");
		row.appendChild(label);
		Label lblOrigen = new Label(detalleItinerario.getRuta().getOrigen());
		lblOrigen.setStyle(style);
		row.appendChild(lblOrigen);
		label = new Label("RUTA :");
		row.appendChild(label);
		Label lblRuta = new Label(detalleItinerario.getRuta().toString());
		lblRuta.setStyle(style);
		row.appendChild(lblRuta);
		rows.appendChild(row);		
		
		row = new Row();
		label = new Label("F.SALIDA :");
		row.appendChild(label);
		Label lblFechaSalida = new Label(Util.DatetoString(detalleItinerario.getFechaPartida(), Constantes.DATE_FORMAT));
		lblFechaSalida.setStyle(style);
		row.appendChild(lblFechaSalida);
		label = new Label("H.SALIDA :");
		row.appendChild(label);
		Label lblHoraSalida = new Label(detalleItinerario.getHoraPartida());
		lblHoraSalida.setStyle(style);
		row.appendChild(lblHoraSalida);
		rows.appendChild(row);
		
		grid.appendChild(rows);
		groupbox.appendChild(grid);
		wndCambioHorario.appendChild(groupbox);
		
		wndCambioHorario.appendChild(groupbox);
		
		groupbox = new Groupbox();
		groupbox.setClosable(false);
		groupbox.setMold("3d");
		caption = new Caption();
		caption.setLabel("DETALLE DEL ITINERARIO");
		caption.setStyle("color: #ffffff;");
		groupbox.appendChild(caption);
		
		Listbox listbox = new Listbox();
		listbox.setRows(5);
		Listhead listhead = new Listhead();
		
		Listheader listheader = new Listheader("#");
		listheader.setWidth("20px");
		listheader.setStyle("color: #ffffff;");
		listhead.appendChild(listheader);
		
		listheader = new Listheader("Ruta");
		listheader.setWidth("145px");
		listheader.setStyle("color: #ffffff;");
		listhead.appendChild(listheader);
		
		listheader = new Listheader("Tipo Servicio");
		listheader.setWidth("120px");
		listheader.setStyle("color: #ffffff;");
		listhead.appendChild(listheader);
		
		listheader = new Listheader("Fecha Salida");
		listheader.setWidth("93px");
		listheader.setStyle("color: #ffffff;");
		listhead.appendChild(listheader);
		
		listheader = new Listheader("Hr.Salida");
		listheader.setWidth("55px");
		listheader.setStyle("color: #ffffff;");
		listhead.appendChild(listheader);
		
		listheader = new Listheader("Fecha Llegada");
		listheader.setWidth("93px");
		listheader.setStyle("color: #ffffff;");
		listhead.appendChild(listheader);
		
		listheader = new Listheader("Hr.Llegada");
		listheader.setWidth("60px");
		listheader.setStyle("color: #ffffff;");
		listhead.appendChild(listheader);
		
		listbox.appendChild(listhead);
		
		try {
			/*Recupera detalle del Itinerario***/
			List<DetalleItinerario> lstItinerariosDetalle= ServiceLocator.getItinerarioManager().buscarItinerariosMantenimiento(detalleItinerario.getItinerario().getId(), "", "", "", "", "","","");
			
			if(lstItinerariosDetalle.size()>0){
				Integer x =0; /*Contador utilizado para el item.*/
				Integer primerIndex =0;
				String destino = null; /*Almacena el ultimo destino cargado en el listBoxdetalleItinerario, para la validacion con el origen.*/
				String idDestino = null;
				
				DetalleItinerario oDetalleItinerario =lstItinerariosDetalle.get(0);
				destino =oDetalleItinerario.getRuta().getDestino();
				idDestino = oDetalleItinerario.getRuta().getLocalidadDestino().getId().toString();
			
				for(DetalleItinerario detalle : lstItinerariosDetalle){
					primerIndex += +1;
					String origen = detalle.getRuta().getOrigen();
					String idOrigen = detalle.getRuta().getLocalidadOrigen().getId().toString();
					if (destino.equals(origen) || primerIndex==1){
						x += +1;
						
						destino=detalle.getRuta().getDestino();
						idDestino = detalle.getRuta().getLocalidadDestino().getId().toString();
						obtenerAgenciaPartidaLlegada(detalle, idOrigen, true);
						obtenerAgenciaPartidaLlegada(detalle, idDestino, false);
						agregarItinerario(detalle, listbox);
					}
				}
			}
			
			groupbox.appendChild(listbox);
			wndCambioHorario.appendChild(groupbox);
			
			groupbox = new Groupbox();
			groupbox.setClosable(false);
			groupbox.setMold("3d");
			caption = new Caption();
			caption.setLabel("NUEVO HORARIO");
			caption.setStyle("color: #ffffff; ");
			groupbox.appendChild(caption);
			
			grid = new Grid();
			columns = new Columns();
			column = new Column();
			column.setWidth("110px");
			column.setAlign("right");
			columns.appendChild(column);
			
			column = new Column();
			column.setWidth("110px");
			columns.appendChild(column);
			
			column = new Column();
			column.setWidth("110px");
			column.setAlign("right");
			columns.appendChild(column);
			
			column = new Column();
			column.setWidth("110px");
			columns.appendChild(column);
			
			grid.appendChild(columns);
			
			rows = new Rows();
			row = new Row();
			label = new Label("HORA ACTUAL :");
			row.appendChild(label);
			final Timebox tmHoraActual = new Timebox();
			tmHoraActual.setFormat("HH:mm");
			tmHoraActual.setWidth("60px");
			tmHoraActual.setText(detalleItinerario.getHoraPartida());
			row.appendChild(tmHoraActual);
			label = new Label("NUEVO HORARIO :");
			row.appendChild(label);
			final Timebox tmNuevoHorario = new Timebox();
			tmNuevoHorario.setFormat("HH:mm");
			tmNuevoHorario.setWidth("60px");
			row.appendChild(tmNuevoHorario);
			Hbox hbox = new Hbox();
			Button btnGrabar = new  Button("Guardar", "/resources/mp_guardarEnabled.png");
			btnGrabar.addEventListener(Events.ON_CLICK, new EventListener<Event>() {
				public void onEvent(Event e) {
					try {
						if(tmNuevoHorario.getText().equals(""))
							throw new HoraPartidaNullException();
						
						Messagebox.show("Esta seguro de continuar, el cambio afectara a todos los tramos del Itinerario", DlgMessage.NOMBREAPLICACION, DlgMessage.BTN_YESNO, Messagebox.QUESTION, new EventListener<Event>() {
							public void onEvent(Event e) {
								try {
									if(e.getName().equals("onYes")) {
										if(tmNuevoHorario.getText().equals(""))
											throw new HoraPartidaNullException();
										String fechaPartida = Util.DatetoString(detalleItinerario.getFechaPartida(), Constantes.DATE_FORMAT)+" "+tmHoraActual.getText();
										String nuevaFechaPartida = Util.DatetoString(detalleItinerario.getFechaPartida(), Constantes.DATE_FORMAT)+" "+tmNuevoHorario.getText();
										Long horaPartida = Util.StringtoDate(fechaPartida, Constantes.DATE_TIME_SHORT_FORMAT).getTime();
										Long muevoHorario = Util.StringtoDate(nuevaFechaPartida, Constantes.DATE_TIME_SHORT_FORMAT).getTime();
										Long diferencia = muevoHorario - horaPartida;
										modificarHorarios(detalleItinerario.getItinerario().getId(), diferencia);
										wndCambioHorario.onClose();
										Util.limpiarListbox(listboxLista);
										String criterio="di.d_fecpar, to_date(di.c_horpar,'HH24:MI'), di.d_feclle, to_date(di.c_horlle,'HH24:MI')";
										listarItinerarios(ServiceLocator.getItinerarioManager().buscarItinerariosMantenimiento(null, lblOrigen.getValue(), 
												"", lblFechaSalida.getValue(), lblFechaSalida.getValue(), "", "", criterio));
									}
								}catch(HoraPartidaNullException hpnex) {
									DlgMessage.information("Debe de ingresar la nueva Hora de Partida del Itinerario", tmNuevoHorario);
								}catch(Exception ex) {
									ex.printStackTrace();
									DlgMessage.error(ex.getMessage());
								}
							}
						});
					}catch(HoraPartidaNullException hpnex) {
						DlgMessage.information("Debe de ingresar la nueva Hora de Partida del Itinerario", tmNuevoHorario);
					}catch (Exception ex) {
						ex.printStackTrace();
						DlgMessage.error(ex.getMessage());
					}
				}
			});
			hbox.appendChild(btnGrabar);
			Button btnCancelar = new  Button("Cancelar","/resources/mp_cancelarEnabled.png");
			btnCancelar.addEventListener(Events.ON_CLICK, new EventListener<Event>() {
				public void onEvent(Event e) {
					wndCambioHorario.onClose();
				}
			});
			hbox.appendChild(btnCancelar);
			row.appendChild(hbox);
			
			rows.appendChild(row);
			grid.appendChild(rows);
			groupbox.appendChild(grid);
			
			wndCambioHorario.appendChild(groupbox);
			
			this.appendChild(wndCambioHorario);
			wndCambioHorario.setMode("modal");
			
			
		}catch(Exception ex) {
			DlgMessage.information("Se encontro problemas al momento de recuperar el detalle del Itinerario");
		}		
	}
	
	public void modificarHorarios(Long idItinerario, Long diferencia) {
		try {
			String ipLocal = UtilData.ipLocal(Executions.getCurrent());
			String login = getUsuario().getLogin();
			int result = ServiceLocator.getItinerarioManager().modificarHorario(idItinerario, diferencia, login, ipLocal);
			if(result == Constantes.CORRECT)
				DlgMessage.information("La modificacion se completo satisfactoriamente");
			
		}catch (Exception ex) {
			ex.printStackTrace();
			DlgMessage.error(ex.getMessage());
		}
	}
	
	public void crearVentanaClonacion(final DetalleItinerario detalleItinerario) {
		final Window wndClonarItinerario = new Window("Duplicar Itinerario", "normal", true);
		wndClonarItinerario.setWidth("840px");
		Groupbox groupbox = new Groupbox();
		groupbox.setClosable(false);
		groupbox.setMold("3d");
		Caption caption = new Caption();
		caption.setLabel("DATOS DEL ITINERARIO");
		caption.setStyle("color: #ffffff;");
		groupbox.appendChild(caption);
		
		Grid grid = new Grid();
		grid.setStyle("border:none");
		Columns columns = new Columns();
		Column column = new Column();
		column.setWidth("110px");
		column.setAlign("right");
		columns.appendChild(column);
		
		column = new Column();
		column.setWidth("170px");
		columns.appendChild(column);
		
		column = new Column();
		column.setWidth("100px");
		column.setAlign("right");
		columns.appendChild(column);
		
		column = new Column();
		//column.setWidth("170px");
		columns.appendChild(column);
		
		grid.appendChild(columns);
		
		Rows rows = new Rows();
		Row row = new Row();
		Div div = new Div();
		String style = "font-weight: bold; font-size:12px !important; color: #4285F4";
		Label label = new Label("TIPO ITINERARIO :");
		div.appendChild(label);
		Textbox txtIdItinerario = new Textbox();
		txtIdItinerario.setVisible(false);
		div.appendChild(txtIdItinerario);
		row.appendChild(div);
		Label lblTipoItinerario = new Label();
		lblTipoItinerario.setValue(detalleItinerario.getItinerario().getTipoItinerario().getDenominacion());
		lblTipoItinerario.setStyle(style);
		row.appendChild(lblTipoItinerario);
		label = new Label("TIPO SERVICIO :");
		row.appendChild(label);
		Label lblTipoServicio = new Label();
		lblTipoServicio.setValue(detalleItinerario.getItinerario().getServicio().getDenominacion());
		lblTipoServicio.setStyle(style);
		row.appendChild(lblTipoServicio);
		rows.appendChild(row);
		
		row = new Row();
		label = new Label("ORIGEN :");
		row.appendChild(label);
		Label lblOrigen = new Label(detalleItinerario.getRuta().getOrigen());
		lblOrigen.setStyle(style);
		row.appendChild(lblOrigen);
		label = new Label("RUTA :");
		row.appendChild(label);
		Label lblRuta = new Label(detalleItinerario.getRuta().toString());
		lblRuta.setStyle(style);
		row.appendChild(lblRuta);
		rows.appendChild(row);		
		
		row = new Row();
		label = new Label("F.SALIDA :");
		row.appendChild(label);
		Label lblFechaSalida = new Label(Util.DatetoString(detalleItinerario.getFechaPartida(), Constantes.DATE_FORMAT));
		lblFechaSalida.setStyle(style);
		row.appendChild(lblFechaSalida);
		label = new Label("H.SALIDA :");
		row.appendChild(label);
		Label lblHoraSalida = new Label(detalleItinerario.getHoraPartida());
		lblHoraSalida.setStyle(style);
		row.appendChild(lblHoraSalida);
		rows.appendChild(row);
		
		grid.appendChild(rows);
		groupbox.appendChild(grid);
		wndClonarItinerario.appendChild(groupbox);
		
		wndClonarItinerario.appendChild(groupbox);
		
		groupbox = new Groupbox();
		groupbox.setClosable(false);
		groupbox.setMold("3d");
		caption = new Caption();
		caption.setLabel("DETALLE DEL ITINERARIO");
		caption.setStyle("color: #ffffff;");
		groupbox.appendChild(caption);
		
		Listbox listbox = new Listbox();
		listbox.setRows(5);
		Listhead listhead = new Listhead();
		
		Listheader listheader = new Listheader("#");
		listheader.setWidth("20px");
		listheader.setStyle("color: #ffffff;");
		listhead.appendChild(listheader);
		
		listheader = new Listheader("Ruta");
		listheader.setWidth("145px");
		listheader.setStyle("color: #ffffff;");
		listhead.appendChild(listheader);
		
		listheader = new Listheader("Tipo Servicio");
		listheader.setWidth("120px");
		listheader.setStyle("color: #ffffff;");
		listhead.appendChild(listheader);
		
		listheader = new Listheader("Fecha Salida");
		listheader.setWidth("93px");
		listheader.setStyle("color: #ffffff;");
		listhead.appendChild(listheader);
		
		listheader = new Listheader("Hr.Salida");
		listheader.setWidth("55px");
		listheader.setStyle("color: #ffffff;");
		listhead.appendChild(listheader);
		
		listheader = new Listheader("Fecha Llegada");
		listheader.setWidth("93px");
		listheader.setStyle("color: #ffffff;");
		listhead.appendChild(listheader);
		
		listheader = new Listheader("Hr.Llegada");
		listheader.setWidth("60px");
		listheader.setStyle("color: #ffffff;");
		listhead.appendChild(listheader);
		
		listheader = new Listheader("Terminal Origen");
		listheader.setWidth("110px");
		listheader.setStyle("color: #ffffff;");
		listhead.appendChild(listheader);
		
		listheader = new Listheader("Terminal Destino");
		listheader.setWidth("110px");
		listheader.setStyle("color: #ffffff;");
		listhead.appendChild(listheader);
		
		listbox.appendChild(listhead);
		
		try {
			/*Recupera detalle del Itinerario***/
			List<DetalleItinerario> lstItinerariosDetalle= ServiceLocator.getItinerarioManager().buscarItinerariosMantenimiento(detalleItinerario.getItinerario().getId(), "", "", "", "", "","","");
			
			if(lstItinerariosDetalle.size()>0){
				Integer x =0; /*Contador utilizado para el item.*/
				Integer primerIndex =0;
				String destino = null; /*Almacena el ultimo destino cargado en el listBoxdetalleItinerario, para la validacion con el origen.*/
				String idDestino = null;
				
				DetalleItinerario oDetalleItinerario =lstItinerariosDetalle.get(0);
				destino =oDetalleItinerario.getRuta().getDestino();
				idDestino = oDetalleItinerario.getRuta().getLocalidadDestino().getId().toString();
			
				for(DetalleItinerario detalle : lstItinerariosDetalle){
					primerIndex += +1;
					String origen = detalle.getRuta().getOrigen();
					String idOrigen = detalle.getRuta().getLocalidadOrigen().getId().toString();
					if (destino.equals(origen) || primerIndex==1){
						x += +1;
						
						destino=detalle.getRuta().getDestino();
						idDestino = detalle.getRuta().getLocalidadDestino().getId().toString();
						obtenerAgenciaPartidaLlegada(detalle, idOrigen, true);
						obtenerAgenciaPartidaLlegada(detalle, idDestino, false);
						agregarItinerario(detalle, listbox);
					}
				}
			}
			
			groupbox.appendChild(listbox);
			wndClonarItinerario.appendChild(groupbox);
			
			groupbox = new Groupbox();
			groupbox.setClosable(false);
			groupbox.setMold("3d");
			caption = new Caption();
			caption.setLabel(":: NUEVO ITINERARIO");
			caption.setStyle("color: #ffffff; ");
			groupbox.appendChild(caption);
			
			grid = new Grid();
			columns = new Columns();
			column = new Column();
			column.setWidth("150px");
			column.setAlign("right");
			columns.appendChild(column);
			
			column = new Column();
			column.setWidth("160px");
			columns.appendChild(column);
			
			column = new Column();
			column.setWidth("130px");
			column.setAlign("right");
			columns.appendChild(column);
			
			column = new Column();
			column.setWidth("140px");
			columns.appendChild(column);			
			
			grid.appendChild(columns);
			
			rows = new Rows();
			row = new Row();
			label = new Label("TIPO ITINERARIO :");
			row.appendChild(label);
			final Combobox cmbTipoItinerario = new Combobox();
			UtilData.cargarDataCombo(cmbTipoItinerario, TipoItinerario.class, false);
			cmbTipoItinerario.setWidth("120px");
			row.appendChild(cmbTipoItinerario);
			label = new Label("TIPO SERVICIO :");
			row.appendChild(label);
			final Combobox cmbServicio = new Combobox();
			cmbServicio.setWidth("120px");
			UtilData.cargarDataCombo(cmbServicio, Servicio.class, false);
			row.appendChild(cmbServicio);
			label = new Label();
			row.appendChild(label);
			rows.appendChild(row);
			
			row = new Row();
			label = new Label("HORA SALIDA :");
			row.appendChild(label);
			final Timebox tmbxNuevaHoraSalida = new Timebox();
			tmbxNuevaHoraSalida.setFormat(Constantes.TIME_SHORT_FORMAT);
			tmbxNuevaHoraSalida.setWidth("60px");
			row.appendChild(tmbxNuevaHoraSalida);
			row.appendChild(new Label());
			row.appendChild(new Label());
			row.appendChild(new Label());
			rows.appendChild(row);
			
			grid.appendChild(rows);
			groupbox.appendChild(grid);
			
			Div div2 = new Div();
			div2.setAlign("center");
			Separator separator = new Separator();
			div2.appendChild(separator);
			Hlayout hLayout = new Hlayout();
			Button btnGrabar = new  Button("Guardar", "/resources/mp_guardarEnabled.png");
			btnGrabar.setHeight("35px");
			btnGrabar.setWidth("75px");
			btnGrabar.addEventListener(Events.ON_CLICK, new EventListener<Event>() {
				public void onEvent(Event e) {
					try {
						if(!(cmbTipoItinerario.getSelectedItem().getValue() instanceof TipoItinerario))
							throw new TipoItinerarioNullException();
						else if (!(cmbServicio.getSelectedItem().getValue() instanceof Servicio))
							throw new ServicioNullException();
						else if(tmbxNuevaHoraSalida.getText().equals(""))
							throw new HoraPartidaNullException();
							
						
						Messagebox.show("¿Esta seguro de continuar con la duplicacion del Itinerario?", DlgMessage.NOMBREAPLICACION, DlgMessage.BTN_YESNO, Messagebox.QUESTION, new EventListener<Event>() {
							public void onEvent(Event e) {
								try {
									if(e.getName().equals("onYes")) {
//										String fechaInicio = Util.DatetoString(dtFechaInicio.getValue(), Constantes.DATE_FORMAT);
//										String fechaFin = Util.DatetoString(dtFechaFin.getValue(), Constantes.DATE_FORMAT);
										Servicio servicio = cmbServicio.getSelectedItem().getValue();
										TipoItinerario tipoItinerario = cmbTipoItinerario.getSelectedItem().getValue();
										String strFechaPartida = lblFechaSalida.getValue() + " " + lblHoraSalida.getValue();
										String strNuevaFechaPartida = lblFechaSalida.getValue() + " " + tmbxNuevaHoraSalida.getText();
										Long horaPartida = Util.StringtoDate(strFechaPartida, Constantes.DATE_TIME_SHORT_FORMAT).getTime();
										Long nuevoHorario = Util.StringtoDate(strNuevaFechaPartida, Constantes.DATE_TIME_SHORT_FORMAT).getTime();
										Long diferencia = nuevoHorario - horaPartida;
										
										clonarItinerario(detalleItinerario.getItinerario().getId(), servicio, tipoItinerario, diferencia);
										wndClonarItinerario.onClose();
//										Util.limpiarListbox(listboxLista);
//										String criterio="di.d_fecpar, to_date(di.c_horpar,'HH24:MI'), di.d_feclle, to_date(di.c_horlle,'HH24:MI')";
//										listarItinerarios(ServiceLocator.getItinerarioManager().buscarItinerariosMantenimiento(null, lblOrigen.getValue(), 
//												"", lblFechaSalida.getValue(), lblFechaSalida.getValue(), "", "", criterio));
									}
								}catch(Exception ex) {
									ex.printStackTrace();
									DlgMessage.error(ex.getMessage());
								}
							}
						});
					}catch(TipoItinerarioNullException tinex) {
						DlgMessage.information("Debe de seleccionar el Tipo de Itinerario", tmbxNuevaHoraSalida);
					}catch(ServicioNullException snex) {
						DlgMessage.information("Debe de Seleccionar el Tipo de Servicio del Itinerario", cmbServicio);
					}catch(HoraPartidaNullException hpnex) {
						DlgMessage.information("Debe de ingresar la Hora de Salida del Itinerario", tmbxNuevaHoraSalida);
					}catch (Exception ex) {
						ex.printStackTrace();
						DlgMessage.error(ex.getMessage());
					}
				}
			});
			hLayout.appendChild(btnGrabar);
			
			Button btnCancelar = new  Button("Cancelar","/resources/mp_cancelarEnabled.png");
			btnCancelar.setHeight("35px");
			btnCancelar.setWidth("75px");
			btnCancelar.addEventListener(Events.ON_CLICK, new EventListener<Event>() {
				public void onEvent(Event e) {
					wndClonarItinerario.onClose();
				}
			});
			hLayout.appendChild(btnCancelar);
			div2.appendChild(hLayout);
			
			groupbox.appendChild(div2);
			
			
			wndClonarItinerario.appendChild(groupbox);
			
			this.appendChild(wndClonarItinerario);
			wndClonarItinerario.setMode("modal");
			
			
		}catch(Exception ex) {
			DlgMessage.information("Se encontro problemas al momento de recuperar el detalle del Itinerario");
		}		
	}
	
	/**
	 * 
	 * @param idItinerario		: Identificador del itinerario
	 * @param fechaInicio		: Fecha de inicio de la programacion (Fecha Partida del Itinerario)
	 * @param fechaFin			: Fecha fin de la programacion.
	 * @param servicio			: Servicio del Itinerario a programar
	 * @param tipoItinerario	: Tipo de Itinerario a programar
	 * @param diferencia		: Diferencia de horario
	 */
	public void clonarItinerario(Long idItinerario, Servicio servicio, TipoItinerario tipoItinerario, Long diferencia) {
		try {
			
			Itinerario oItinerario = ServiceLocator.getItinerarioManager().buscarPorId(idItinerario);
			List<DetalleItinerario> lstItinerariosDetalle= ServiceLocator.getItinerarioManager().buscarItinerariosMantenimiento(idItinerario, "", "", "", "", "","","");
			
			Itinerario newItinerario = (Itinerario)oItinerario.clone();
			newItinerario.setId(null);
			newItinerario.setServicio(servicio);
			newItinerario.setTipoItinerario(tipoItinerario);
			String fechaHoraPartida = Util.DatetoString(oItinerario.getFechaPartida(), Constantes.DATE_FORMAT)+" "+oItinerario.getHoraPartida();
			Date newFechaHoraPartida = new Date(Util.StringtoDate(fechaHoraPartida, Constantes.DATE_TIME_SHORT_FORMAT).getTime() + diferencia);
			newItinerario.setFechaPartida(Util.StringtoDate(Util.DatetoString(newFechaHoraPartida, Constantes.DATE_FORMAT), Constantes.DATE_FORMAT));
			newItinerario.setHoraPartida(Util.DatetoString(newFechaHoraPartida,Constantes.TIME_SHORT_FORMAT));
			String fechaHoraLlegada = Util.DatetoString(oItinerario.getFechaLlegada(), Constantes.DATE_FORMAT) + " " + oItinerario.getHoraLlegada();
			Date newFechaHoraLlegada = new Date(Util.StringtoDate(fechaHoraLlegada, Constantes.DATE_TIME_SHORT_FORMAT).getTime() + diferencia);
			newItinerario.setFechaLlegada(Util.StringtoDate(Util.DatetoString(newFechaHoraLlegada, Constantes.DATE_FORMAT), Constantes.DATE_FORMAT));
			newItinerario.setHoraLlegada(Util.DatetoString(newFechaHoraLlegada, Constantes.TIME_SHORT_FORMAT));
			UtilData.auditarRegistro(newItinerario, false, getUsuario(), Executions.getCurrent());
			
			ServiceLocator.getItinerarioManager().guardar(newItinerario);
			
			for(int i=0; i<lstItinerariosDetalle.size(); i++) {
				DetalleItinerario detalleItinerario = lstItinerariosDetalle.get(i);
				DetalleItinerario newDetalleItinerario = (DetalleItinerario)detalleItinerario.clone();
				newDetalleItinerario.setId(null);
				newDetalleItinerario.setItinerario(newItinerario);
				fechaHoraPartida = Util.DatetoString(detalleItinerario.getFechaPartida(), Constantes.DATE_FORMAT)+" "+detalleItinerario.getHoraPartida();
				newFechaHoraPartida = new Date(Util.StringtoDate(fechaHoraPartida, Constantes.DATE_TIME_SHORT_FORMAT).getTime() + diferencia);
				newDetalleItinerario.setFechaPartida(Util.StringtoDate(Util.DatetoString(newFechaHoraPartida, Constantes.DATE_FORMAT), Constantes.DATE_FORMAT));
				newDetalleItinerario.setHoraPartida(Util.DatetoString(newFechaHoraPartida,Constantes.TIME_SHORT_FORMAT));
				fechaHoraLlegada = Util.DatetoString(detalleItinerario.getFechaLlegada(), Constantes.DATE_FORMAT) + " " + detalleItinerario.getHoraLlegada();
				newFechaHoraLlegada = new Date(Util.StringtoDate(fechaHoraLlegada, Constantes.DATE_TIME_SHORT_FORMAT).getTime() + diferencia);
				newDetalleItinerario.setFechaLlegada(Util.StringtoDate(Util.DatetoString(newFechaHoraLlegada, Constantes.DATE_FORMAT), Constantes.DATE_FORMAT));
				newDetalleItinerario.setHoraLlegada(Util.DatetoString(newFechaHoraLlegada, Constantes.TIME_SHORT_FORMAT));
				UtilData.auditarRegistro(newDetalleItinerario, false, getUsuario(), Executions.getCurrent());
				
				ServiceLocator.getDetalleItinerarioManager().guardar(newDetalleItinerario);
			}
			
			List<ItinerarioAgenciaPartida> lstItinerarioAgenciaPartida = ServiceLocator.getItinerarioAgenciaPartidaManager().buscarAgenciasPartida(idItinerario, Constantes.VALUE_ACTIVO, null);
			for(int i=0; i<lstItinerarioAgenciaPartida.size(); i++) {
				ItinerarioAgenciaPartida itinerarioAgenciaPartida = lstItinerarioAgenciaPartida.get(i);
				ItinerarioAgenciaPartida newItinerarioAgenciaPartida = (ItinerarioAgenciaPartida)itinerarioAgenciaPartida.clone();
				newItinerarioAgenciaPartida.setItinerario(newItinerario);
				ItinerarioAgenciaPartidaID itinerarioAgenciaPartidaID = new ItinerarioAgenciaPartidaID(newItinerario.getId(), itinerarioAgenciaPartida.getAgencia().getId());
				newItinerarioAgenciaPartida.setItinerarioAgenciaPartidaID(itinerarioAgenciaPartidaID);
				fechaHoraPartida = Util.DatetoString(oItinerario.getFechaPartida(), Constantes.DATE_FORMAT)+" "+itinerarioAgenciaPartida.getHoraPartida();
				newFechaHoraPartida = new Date(Util.StringtoDate(fechaHoraPartida, Constantes.DATE_TIME_SHORT_FORMAT).getTime() + diferencia);
				newItinerarioAgenciaPartida.setHoraPartida(Util.DatetoString(newFechaHoraPartida,Constantes.TIME_SHORT_FORMAT));
				UtilData.auditarRegistro(newItinerarioAgenciaPartida, false, getUsuario(), Executions.getCurrent());
				
				ServiceLocator.getItinerarioAgenciaPartidaManager().guardar(newItinerarioAgenciaPartida);
			}
			
			List<ItinerarioAgenciaLlegada> lstItinerarioAgenciaLlegada = ServiceLocator.getItinerarioAgenciaLlegadaManager().buscarAgenciasLlegada(idItinerario, Constantes.VALUE_ACTIVO, null);
			for(int i=0; i<lstItinerarioAgenciaLlegada.size(); i++) {
				ItinerarioAgenciaLlegada itinerarioAgenciaLlegada = lstItinerarioAgenciaLlegada.get(i);
				ItinerarioAgenciaLlegada newItinerarioAgenciaLlegada = (ItinerarioAgenciaLlegada)itinerarioAgenciaLlegada.clone();
				newItinerarioAgenciaLlegada.setItinerario(newItinerario);
				ItinerarioAgenciaLlegadaID itinerarioAgenciaLlegadaID = new ItinerarioAgenciaLlegadaID(newItinerario.getId(), itinerarioAgenciaLlegada.getAgencia().getId());
				newItinerarioAgenciaLlegada.setItinerarioAgenciaLlegadaID(itinerarioAgenciaLlegadaID);
				fechaHoraLlegada = Util.DatetoString(oItinerario.getFechaLlegada(), Constantes.DATE_FORMAT)+" "+itinerarioAgenciaLlegada.getHoraLlegada();
				newFechaHoraLlegada = new Date(Util.StringtoDate(fechaHoraLlegada, Constantes.DATE_TIME_SHORT_FORMAT).getTime() + diferencia);
				newItinerarioAgenciaLlegada.setHoraLlegada(Util.DatetoString(newFechaHoraLlegada,Constantes.TIME_SHORT_FORMAT));
				UtilData.auditarRegistro(newItinerarioAgenciaLlegada, false, getUsuario(), Executions.getCurrent());
				
				ServiceLocator.getItinerarioAgenciaLlegadaManager().guardar(newItinerarioAgenciaLlegada);
			}
			
			int result = 1; 
			if(result == Constantes.CORRECT)
				DlgMessage.information("La duplicacion del Itinerario se completo satisfactoriamente");
			
		}catch (Exception ex) {
			ex.printStackTrace();
			DlgMessage.error(ex.getMessage());
		}
	}
	
//	/** 
//	 * Agrega el terminal de Partida
//	 * Clase ItinerarioAgenciaPartida
//	 * @param oItinerarioAgenciaPartida
//	 */
// 	private void cargaListboxTerminalPartida(ItinerarioAgenciaPartida oItinerarioAgenciaPartida){
// 		Listitem item = null;
//		Listcell cell = null;
//		Integer x = lbxTerminalPartida.getChildren().size();
//		item = new Listitem();
//		cell = new Listcell((x.toString()));
//		item.appendChild(cell); //Correlativo
//		cell = new Listcell(oItinerarioAgenciaPartida.getAgencia().getNombreCorto());
//		item.appendChild(cell);
//		cell = new Listcell(oItinerarioAgenciaPartida.getHoraPartida());
//		cell.setStyle("font-size:11px !important");
//		item.appendChild(cell);
//		
//		final Image image= new Image();		
//		image.setHeight("17px");
//		cell = new Listcell();
//		cell .appendChild(image);
//		item.appendChild(cell);
//		
//		image.addEventListener(Events.ON_CLICK, new EventListener<Event>() {
//			@Override
//			public void onEvent(Event event) throws Exception {
//				if(image.getSrc().equals(enabledDelete))
//					quitaTerminal(true, lbxTerminalPartida);
//			}
//		});
//		
//		item.setValue(oItinerarioAgenciaPartida);
//		lbxTerminalPartida.appendChild(item);
//		imagenEliminarTerminale(lbxTerminalPartida);
// 	}
// 	
// 	/**
// 	 * Agrega en el ListBox el Terminal de Llegada.
// 	 * 	Clase ItinerarioAgenciaLlegada
// 	 * @param oItinerarioAgenciaLlegada
// 	 * @throws Exception
// 	 */
// 	private  void cargaListboxTerminalLlegada(ItinerarioAgenciaLlegada oItinerarioAgenciaLlegada) throws Exception{
//		Listitem item = null;
//		Listcell cell = null;
//		
//		Integer x = lbxTerminalLlegada.getChildren().size();
//		item = new Listitem();
//		cell = new Listcell((x.toString()));
//		item.appendChild(cell); //Correlativo
//		cell = new Listcell(oItinerarioAgenciaLlegada.getAgencia().getNombreCorto());
//		item.appendChild(cell);
//		cell = new Listcell(oItinerarioAgenciaLlegada.getHoraLlegada());
//		cell.setStyle("font-size:11px !important");
//		item.appendChild(cell);
//		
//		final Image image= new Image();
//		image.setHeight("17px");
//		cell = new Listcell();
//		cell .appendChild(image);
//		item.appendChild(cell);
//		
//		image.addEventListener(Events.ON_CLICK, new EventListener<Event>() {
//			@Override
//			public void onEvent(Event event) throws Exception {
//				if(image.getSrc().equals(enabledDelete))
//					quitaTerminal(false, lbxTerminalLlegada);
//			}
//		});
//		
//		item.setValue(oItinerarioAgenciaLlegada);
//		lbxTerminalLlegada.appendChild(item);
//		imagenEliminarTerminale(lbxTerminalLlegada);
//	}
 	
 	
}
