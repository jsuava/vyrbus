/**
 * Proyecto		: SISVYR
 * Sistema		: Sistema de Ventas y Reservas
 * Descripción	: 
 * Autor		: José Abanto
 * Fecha		: 11/09/2012
 */
package com.cystesoft.vyrbus.view.ctrl;
import java.util.ArrayList;
import java.util.Calendar;
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
import org.zkoss.zul.Datebox;
import org.zkoss.zul.Div;
import org.zkoss.zul.Filedownload;
import org.zkoss.zul.Grid;
import org.zkoss.zul.Hbox;
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
import com.cystesoft.vyrbus.service.exceptions.FechaItinerarioNullException;
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
 * @author José Abanto
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
	private Combobox cmbTermOrigen;
	private Combobox cmbTermDestino;
	private Listbox	listboxDetalleItinerario;
	private Grid grdRutasTrifas;
	private Combobox cmbTerminalPartida;
	private Timebox	tbterHoraPartida;	
	private Listbox listboxTerminalPartida;
	private Combobox cmbTerminalLlegada;
	private Timebox tbterHoraLlegada;
	private Listbox listboxTerminalLlegada;
	private Datebox dbFechaInicio;
	private Datebox dbFechafin;
	private Button cmdAddTerPartida;
	private Button cmdAddTerLlegada;
	private Div divMantenimiento;
	private Button cmdAgregarTramo;

	private Itinerario oitinerario=null; 

	private Integer indexEdicionTramo = 0;
	private Boolean esEdicionTramo = false;
	private Integer action; 

	private TreeMap<String, Object> criteriosBusqueda = new TreeMap<String, Object>();

	private List<DetalleItinerario> lstItinerariosDetalle;
	private List<DetalleItinerario> listTramoDuplicado=null;
	
	private List<VentaPasaje>listVentasNoUpdate=new ArrayList<VentaPasaje>();//Utilizado para la validacion de la ventas
	private Window wndItinerarios = null;
	
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
		
		listboxDetalleItinerario.addEventListener(Events.ON_DOUBLE_CLICK,new EventListener<Event>() {
			@Override
			public void onEvent(Event event) throws Exception {
				if(!(btnGuardar.isDisabled()))
					editarTramo();
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
		cmbTermOrigen=(Combobox)this.getFellow("cmbTermOrigen");
		cmbTermDestino=(Combobox)this.getFellow("cmbTermDestino");
		listboxDetalleItinerario=(Listbox)this.getFellow("listboxDetalleItinerario");
		grdRutasTrifas=(Grid)this.getFellow("grdRutasTrifas");
		cmbTerminalPartida=(Combobox)this.getFellow("cmbTerminalPartida");
		tbterHoraPartida=(Timebox)this.getFellow("tbterHoraPartida");
		listboxTerminalPartida=(Listbox)this.getFellow("listboxTerminalPartida");
		cmbTerminalLlegada=(Combobox)this.getFellow("cmbTerminalLlegada");
		tbterHoraLlegada=(Timebox)this.getFellow("tbterHoraLlegada");
		listboxTerminalLlegada=(Listbox)this.getFellow("listboxTerminalLlegada");
		dbFechaInicio=(Datebox)this.getFellow("dbFechaInicio");
		dbFechafin=(Datebox)this.getFellow("dbFechafin");
		cmdAddTerPartida=(Button)this.getFellow("cmdAddTerPartida");
		cmdAddTerLlegada=(Button)this.getFellow("cmdAddTerLlegada");
		divMantenimiento=(Div)this.getFellow("divMantenimiento");
		cmdAgregarTramo=(Button)this.getFellow("cmdAgregarTramo");
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
					
					ListaItinerarios(ServiceLocator.getItinerarioManager().buscarItinerariosMantenimiento(iItinerario, sOrigen, 
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
		ManteniemientoItinerario(id);
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
		imageEliminarTramo();
		imagenEliminarTerminale(listboxTerminalPartida);
		imagenEliminarTerminale(listboxTerminalLlegada);
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
				Boolean rutaSuprimida=false;
				for(int y=0; y<grdRutasTrifas.getRows().getChildren().size();y++){
					Row it = (Row)grdRutasTrifas.getRows().getChildren().get(y);
					Integer idRutaActual=(new Integer(((Label)it.getChildren().get(0)).getValue()));
					if(idRutaActual.equals(ruta.getId())){
						rutaSuprimida=true;
						break;
					}	
				}
				if(rutaSuprimida==false)
					listVentasNoUpdate.add(ventas);
			}
		}
		return list;
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.tepsa.sisvyr.view.ui.IOpcionesMantenimiento#onSave(int)
	 */
	@Override
	public void onSave(int action) throws Exception {
		try {
			if (listboxDetalleItinerario.getItems().size() <= 0 )
				throw new ItinerarioException(ItinerarioException.ITINERARIO_NULL); 
			else if (listboxTerminalPartida.getItems().size() <= 0)
				throw new ItinerarioException(ItinerarioException.AGENCIA_PARTIDA_NULL); 
			else if (listboxTerminalLlegada.getItems().size() <= 0)
				throw new ItinerarioException(ItinerarioException.AGENCIA_LLEGADA_NULL); 
			
			/*Realiza la validacion de los tiempos entre el origen y destino - 04/01/2017 - jabanto*/
			if(validarDiferenciaHorasTramo(null)==false)
				throw new CancelaGrabacionException();
			
			Boolean flag=true;
			
			/*Valida las horas de los tramos contra la de la ruta mayor*/
//			DetalleItinerario detItiPartida=(DetalleItinerario)listboxDetalleItinerario.getItemAtIndex(0).getValue();
//			DetalleItinerario detItiLlegada=(DetalleItinerario)listboxDetalleItinerario.getItemAtIndex(listboxDetalleItinerario.getItemCount()-1).getValue();
//			/*Busca la ruta mayor*/
//			TreeMap<String, Object>criterioBusqueda=new TreeMap<>();
//			criterioBusqueda.put("origen",detItiPartida.getRuta().getOrigen());
//			criterioBusqueda.put("destino",detItiLlegada.getRuta().getDestino());
//			criterioBusqueda.put("estadoRegistro",Constantes.VALUE_ACTIVO);
//			List<Ruta>result=ServiceLocator.getRutaManager().buscarPorX(criterioBusqueda, null);
//			Ruta rutaMayor=result.get(0);
//			/*Agrega la fecha y hora de partida del tramo inicial a un objeto Calendar*/
//			Calendar calPartida=Calendar.getInstance();
//			calPartida.setTime(detItiPartida.getFechaPartida());
//			calPartida.set(calPartida.HOUR_OF_DAY, Integer.valueOf(detItiPartida.getHoraPartida().split(":")[0]));
//			calPartida.set(calPartida.MINUTE, Integer.valueOf(detItiPartida.getHoraPartida().split(":")[1]));
//			/*Agrega la fecha y hora de Llegada del tramo final a un objeto Calendar*/
//			Calendar calLlegada=Calendar.getInstance();
//			calLlegada.setTime(detItiLlegada.getFechaLlegada());
//			calLlegada.set(calLlegada.HOUR_OF_DAY, Integer.valueOf(detItiLlegada.getHoraLlegada().split(":")[0]));
//			calLlegada.set(calLlegada.MINUTE, Integer.valueOf(detItiLlegada.getHoraLlegada().split(":")[1]));
//
//			/*Calcula la fecha y hora llegada ideal */
//			Calendar calendarLlegadaIdeal=Calendar.getInstance();
//			calendarLlegadaIdeal.setTimeInMillis((long) (calPartida.getTimeInMillis()+(rutaMayor.getHorasViaje()*Constantes.MILISEGUNDOS_X_HORA)));

			//Valida si existe diferencia
//			if(calendarLlegadaIdeal.getTimeInMillis()!=calLlegada.getTimeInMillis()){
//				final int actionf=action;
//				/*Calcula la duración que se esta programacion */
//				long valueDP=calLlegada.getTimeInMillis()-calPartida.getTimeInMillis();
//				long horasDP=valueDP/Constantes.MILISEGUNDOS_X_HORA;
//				long minutDP=(valueDP%3600)/60;
//				/*Lo convierte de long a String*/
//				String horasRuta=Util.toNumberFormat(rutaMayor.getHorasViaje(), 2).replace(".", ":");
//				String duracionPro=Util.toNumberFormat(Double.valueOf(String.valueOf(horasDP)+"."+String.valueOf(minutDP)),2).replace(".", ":");
//				
//				Messagebox.show("La Ruta "+rutaMayor.toString()+" ha sido configurada con una duración de "+horasRuta+
//						" horas de viaje, pero se esta programando con una duración de "+duracionPro+" horas. "+
//						"¿Realmente desea continuar?"
//						,DlgMessage.NOMBREAPLICACION, DlgMessage.BTN_YESNO, Messagebox.QUESTION,DlgMessage.BTN_DEFAULT_NO, new EventListener<Event>() {
//					@Override
//					public void onEvent(Event e) throws Exception {
//						if(e.getName().equals("onYes")){
//							save(actionf);
//						}
//					}
//				});
//			}else{
				//Si no hay diferencia.
				save(action);
//			}
			
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
											  dbFechafin.getValue().getTime()>dbFechaInicio.getValue().getTime()?				
													"WndItinerario.Question.ConfirmaInserts"
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
								
								ListaItinerarios(ServiceLocator.getItinerarioManager().buscarItinerariosMantenimiento(iItinerario, sOrigen, 
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
																			
									ListaItinerarios(ServiceLocator.getItinerarioManager().buscarItinerariosMantenimiento(iItinerario, sOrigen, 
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
									
							Util.limpiarListbox(listboxDetalleItinerario);
							Util.limpiarListbox(listboxTerminalPartida);
							Util.limpiarListbox(listboxTerminalLlegada);
							Rows Rows = new Rows();
							grdRutasTrifas.getRows().detach();
							grdRutasTrifas.appendChild(Rows);
												
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
			ManteniemientoItinerario(((DetalleItinerario)listboxLista.getSelectedItem().getValue()).getItinerario().getId()); 
//			CargarRutasTarifas();
			Util.disabledBtnAgregar(true, cmdAgregarTramo, accesoGrabar());
			Util.disabledBtnAgregar(true, cmdAddTerPartida, accesoGrabar());
			Util.disabledBtnAgregar(true, cmdAddTerLlegada, accesoGrabar());
		}
	}

	/**
	 * 
	 * @param comboBox	: Objeto donde se cargan los terminales destino, según la ruta seleccionada. 
	 * @throws Exception
	 */
	public void onchangeTerminalDestino(Combobox comboBox) throws Exception{
		comboBox.getItems().clear();
		if (cmbRuta.getSelectedItem().getValue() instanceof Ruta){
			Ruta oruta = new  Ruta();
			 oruta.setId((Integer) ((Ruta) cmbRuta.getSelectedItem().getValue()).getLocalidadDestino().getId());
			Integer destino = oruta.getId();
			 
			cargarTerm(comboBox, false, destino);
		}else{
			UtilData.cargarGenericData(comboBox, false);
		}
		
		if(comboBox.getItems().size()==2)
			comboBox.setSelectedIndex(1);
		else
			comboBox.setSelectedIndex(0);	
	}
	
	/**
	 * 	Carga Rutas, según el Origen seleccionado.
	 * @throws Exception
	 */
	public  void onChangeRutas() throws Exception{
		cmbRuta.getItems().clear();	
		cmbTermOrigen.getItems().clear();
		cmbTermDestino.getItems().clear();
		
		if (cmbOrigen.getSelectedItem().getValue() instanceof Localidad){
			Integer Origen = ((Localidad) cmbOrigen.getSelectedItem().getValue()).getId();
			UtilData.cargarRutas(cmbRuta, false, Origen,null);
			cargarTerm(cmbTermOrigen,false,Origen);
			UtilData.cargarGenericData(cmbTermDestino, false);
		}else{
			UtilData.cargarGenericData(cmbRuta, false);
			UtilData.cargarGenericData(cmbTermOrigen, false);
			UtilData.cargarGenericData(cmbTermDestino, false);
		}		
		cmbRuta.setSelectedIndex(0);
		if(cmbTermOrigen.getItems().size()== 2)
			cmbTermOrigen.setSelectedIndex(1);
		else
			cmbTermOrigen.setSelectedIndex(0);
		cmbTermDestino.setSelectedIndex(0);
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
				if (listboxDetalleItinerario.getItems().size() > 0){
					/*Cuando NO es una edición de un Tramo*/
					itemvDetalleItinerario = listboxDetalleItinerario.getItemAtIndex(listboxDetalleItinerario.getItems().size() -1);
				}
			}else{
				/*Cuando es una edición de un Tramo*/
				itemvDetalleItinerario = listboxDetalleItinerario.getItemAtIndex(indexEdicionTramo -1);
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
			else if (!(cmbTermOrigen.getSelectedItem().getValue() instanceof Agencia))
				throw new TerminalOrigenNullException();
			else if (!(cmbTermDestino.getSelectedItem().getValue() instanceof Agencia))
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
			
			oruta.setId((Integer) ((Ruta) cmbRuta.getSelectedItem().getValue()).getId());	
			oruta.setHorasViaje((Double) ((Ruta) cmbRuta.getSelectedItem().getValue()).getHorasViaje());
			oruta.setOrigen( ((Ruta) cmbRuta.getSelectedItem().getValue()).getOrigen());
			oruta.setDestino( ((Ruta) cmbRuta.getSelectedItem().getValue()).getDestino());
			
			/*Obtiene la Fecha y hora de llegada*/
			Calendar FechaHoraLLegada = Calendar.getInstance();
			FechaHoraLLegada.setTimeInMillis(dbFechaItinerario.getValue().getTime());
			FechaHoraLLegada.set(FechaHoraLLegada.MONTH, dbFechaItinerario.getValue().getMonth());
			FechaHoraLLegada.set(FechaHoraLLegada.HOUR_OF_DAY, tbHoraPartida.getValue().getHours());
			FechaHoraLLegada.set(FechaHoraLLegada.MINUTE, tbHoraPartida.getValue().getMinutes());
			FechaHoraLLegada.set(FechaHoraLLegada.SECOND, 0);
			FechaHoraLLegada.add(FechaHoraLLegada.MILLISECOND, (int) (Util.horasMinutos(oruta.getHorasViaje()).intValue()));
			//FechaHoraLLegada.add(FechaHoraLLegada.MILLISECOND, (int) (lHorasMinutosViaje.intValue()));
			/**********************************************************************************************************/
			
			/*Asigna solamente la fecha llegada en fomrato dd/MM/yyyy */
			Calendar FechaLLegada = Calendar.getInstance();
			FechaLLegada.setTime(FechaHoraLLegada.getTime());
			FechaLLegada.set(FechaLLegada.HOUR_OF_DAY, 0);
			FechaLLegada.set(FechaLLegada.MINUTE, 0);
			FechaLLegada.set(FechaLLegada.SECOND, 0);
			
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
			Agencia	oagenciaPartida = new Agencia();
			Agencia	oagenciaLlegada= new Agencia();
			
			
			Localidad oLocalidadOrigen = new Localidad();
			oLocalidadOrigen.setId(((Ruta) cmbRuta.getSelectedItem().getValue()).getLocalidadOrigen().getId());
			oLocalidadOrigen.setDenominacion(((Ruta) cmbRuta.getSelectedItem().getValue()).getOrigen());
			oruta.setLocalidadOrigen(oLocalidadOrigen);
			
			oagenciaPartida.setLocalidad(oLocalidadOrigen);
			oagenciaPartida.setId(((Agencia) cmbTermOrigen.getSelectedItem().getValue()).getId());
			oagenciaPartida.setNombreCorto(((Agencia) cmbTermOrigen.getSelectedItem().getValue()).getNombreCorto());
			oagenciaPartida.setDenominacion(((Agencia) cmbTermOrigen.getSelectedItem().getValue()).getDenominacion());
			
			Localidad oLocalidadDestino = new Localidad();
			oLocalidadDestino.setId(((Ruta) cmbRuta.getSelectedItem().getValue()).getLocalidadDestino().getId());
			oLocalidadDestino.setDenominacion(((Ruta) cmbRuta.getSelectedItem().getValue()).getDestino());
			oruta.setLocalidadDestino(oLocalidadDestino);
			
			oagenciaLlegada.setLocalidad(oLocalidadDestino);
			oagenciaLlegada.setId(((Agencia) cmbTermDestino.getSelectedItem().getValue()).getId());
			oagenciaLlegada.setNombreCorto(((Agencia) cmbTermDestino.getSelectedItem().getValue()).getNombreCorto());
			oagenciaLlegada.setDenominacion(((Agencia) cmbTermDestino.getSelectedItem().getValue()).getDenominacion());
			
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
			odetalleItinerario.setTarifa((Double) 0.01);
			odetalleItinerario.setEstadoRegistro(Constantes.VALUE_ACTIVO);
			
			
			final DetalleItinerario fodeDetalleItinerario=odetalleItinerario;
			final Agencia fAgenciaPartida=oagenciaPartida;
			final Agencia fAgenciaLlegada=oagenciaLlegada;
			final Calendar fFechaHoraLLegada=FechaHoraLLegada;
			
			/*Realiza la validacion de los tiempos entre el origen y destino - 04/01/2017 - jabanto*/
			if(validarDiferenciaHorasTramo(odetalleItinerario)==false)
				return;			
			
			/*Verifica que el tramo no exista en la BD*/
			if(isTramoDuplicado(odetalleItinerario)){
				DetalleItinerario detalleItinerario = (DetalleItinerario) listTramoDuplicado.get(0);
				Messagebox.show(Messages.getString("WndItinerario.information.TramosDuplicados")+
						" Nro.Itinerario:" + detalleItinerario.getItinerario().getId()  +
						", Fecha:" + Constantes.FORMAT_DATE.format(detalleItinerario.getFechaPartida()) +
						",  Hora:"+ detalleItinerario.getHoraPartida()+". ¿Desa continuar?",DlgMessage.NOMBREAPLICACION, DlgMessage.BTN_YESNO, Messagebox.QUESTION,DlgMessage.BTN_DEFAULT_NO, new EventListener<Event>() {
					@Override
					public void onEvent(Event e) throws Exception {
						if(e.getName().equals("onYes")){
							/*Llena Tramos****/
							agregarItinerario(fodeDetalleItinerario,listboxDetalleItinerario);	
							/*Valida si se trata de una edición de un Tramo*/
							if (esEdicionTramo==true){
								listboxDetalleItinerario.removeItemAt(indexEdicionTramo);
								if (listboxDetalleItinerario.getItems().size()==1)
									Util.limpiarListbox(listboxTerminalPartida);		
									esEdicionTramo=false;					
								}
							/*Carga Rutas y Tarifas*/
//							CargarRutasTarifas();	
							/*Carga Termianl Partida*/
							if (listboxDetalleItinerario.getItems().size()==1 && listboxTerminalPartida.getItems().size()==0){
								/*Carga Combo Terminal Partida*/
								cargarTerm(cmbTerminalPartida, false, ((Localidad) cmbOrigen.getSelectedItem().getValue()).getId());
								/*Carga ListBox Terminal Partida**/
								ItinerarioAgenciaPartida oItinerarioAgenciaPartida = new ItinerarioAgenciaPartida();
								oItinerarioAgenciaPartida.setAgencia(fAgenciaPartida);
								oItinerarioAgenciaPartida.setHoraPartida(tbHoraPartida.getText());
								cargaListboxTerminalPartida(oItinerarioAgenciaPartida);
//								HabilitaBotones_TerPartida(false);
								/*Carga Fecha Inicio para la Programación*/
								dbFechaInicio.setText(dbFechaItinerario.getText());
								/*Carga Fecha Fin para la Programación*/
								dbFechafin.setText(dbFechaItinerario.getText()); 	
							}
								
							/*Carga la nueva fecha de salida del itinerario*/
							dbFechaItinerario.setText(Constantes.FORMAT_DATE.format(fFechaHoraLLegada.getTime()));
							/*Carga la nueva fecha de salida del itinerario*/
							tbHoraPartida.setText(Constantes.FORMAT_TIME.format(fFechaHoraLLegada.getTime()));
							/*Selecciona el Nuevo Origen*/
							Listitem itemDetalleItinerario = listboxDetalleItinerario.getItemAtIndex(listboxDetalleItinerario.getItems().size() -1);
							Util.seleccionarValorItemCombo(Localidad.class, cmbOrigen, (((DetalleItinerario) itemDetalleItinerario.getValue()).getRuta().getLocalidadDestino().getId()));
							/*Carga Terminal Origen*/
							cargarTerm(cmbTermOrigen, false, ((Localidad) cmbOrigen.getSelectedItem().getValue()).getId()); 							
							/*Carga el Combo Terminal LLegada*/
							if(cmbRuta.getSelectedItem().getValue() instanceof Ruta)
								cargarTerm(cmbTerminalLlegada, false, ((Ruta) cmbRuta.getSelectedItem().getValue()).getLocalidadDestino().getId());
							/*Carga ListBox Terminal Llegada*/
							ItinerarioAgenciaLlegada oItinerarioAgenciaLlegada = new ItinerarioAgenciaLlegada();
							oItinerarioAgenciaLlegada.setAgencia(fAgenciaLlegada);
							oItinerarioAgenciaLlegada.setHoraLlegada(Constantes.FORMAT_TIME.format(fFechaHoraLLegada.getTime()).toString());
							Util.limpiarListbox(listboxTerminalLlegada);
							cargaListboxTerminalLlegada(oItinerarioAgenciaLlegada);	
//							HabilitaBotones_TerLlegada(false);
							/*Carga Rutas*/
							onChangeRutas();	
							
							cmbTerminalPartida.setSelectedIndex(0);
							tbterHoraPartida.setText("");
							esEdicionTramo=false;
						}
					}
				});
				
			}else{
				/*Validar que la ruta exista*/
				String origen = "";
				if(listboxDetalleItinerario.getItems().size()==0) 
					origen = odetalleItinerario.getRuta().getOrigen();					
				else 
					origen = ((DetalleItinerario)listboxDetalleItinerario.getItems().get(0).getValue()).getRuta().getOrigen();
				
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
					agregarItinerario(odetalleItinerario,listboxDetalleItinerario);	
					/*Valida si se trata de una edición de un Tramo*/
					if (esEdicionTramo==true){
						listboxDetalleItinerario.removeItemAt(indexEdicionTramo);
						if (listboxDetalleItinerario.getItems().size()==1)
							Util.limpiarListbox(listboxTerminalPartida);		
							esEdicionTramo=false;					
						}
					/*Carga Rutas y Tarifas*/
	//				CargarRutasTarifas();
					
					/*Carga Termianl Partida*/
					if (listboxDetalleItinerario.getItems().size()==1 && listboxTerminalPartida.getItems().size()==0){
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
					cargarTerminalPartidaLlegada(cmbTerminalPartida, false, listboxDetalleItinerario, 1);					
					cargarTerminalPartidaLlegada(cmbTerminalLlegada, false, listboxDetalleItinerario, 2);
					
					recargarListboxTerminales(listboxDetalleItinerario);
						
					/*Carga la nueva fecha de salida del itinerario*/
					dbFechaItinerario.setText(Constantes.FORMAT_DATE.format(FechaHoraLLegada.getTime()));
					/*Carga la nueva fecha de salida del itinerario*/
					tbHoraPartida.setText(Constantes.FORMAT_TIME.format(FechaHoraLLegada.getTime()));
					/*Selecciona el Nuevo Origen*/
					Listitem itemDetalleItinerario = listboxDetalleItinerario.getItemAtIndex(listboxDetalleItinerario.getItems().size() -1);
					Util.seleccionarValorItemCombo(Localidad.class, cmbOrigen, (((DetalleItinerario) itemDetalleItinerario.getValue()).getRuta().getLocalidadDestino().getId()));
					/*Carga Terminal Origen*/
					cargarTerm(cmbTermOrigen, false, ((Localidad) cmbOrigen.getSelectedItem().getValue()).getId()); 							
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
			DlgMessage.information(Messages.getString("WndItinerario.information.TerminalOrigen"),cmbTermOrigen);
		}catch (TerminalDestinoNullException tdnex){
			DlgMessage.information(Messages.getString("WndItinerario.information.TerminalDestino"),cmbTermDestino);
		}catch (ItinerarioException i){
			if(i.getTipo().intValue()==ItinerarioException.FECHA_MENOR){
				DlgMessage.information(Messages.getString("WndItinerario.information.FechaItinerarioMenorALaActual"),dbFechaItinerario);
			}else if (i.getTipo().intValue()==ItinerarioException.TRAMOS_DIFERENTES){
				DlgMessage.information(Messages.getString("WndItinerario.information.TramosDiferentes"),cmbOrigen);
			}else if(i.getTipo().intValue()==ItinerarioException.TRAMOS_DUPLICADOS){
				DetalleItinerario detalleItinerario = (DetalleItinerario) listTramoDuplicado.get(0);
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
	 * 	Carga Rutas y Tarifas. 
	 * @throws Exception
	 */
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
	
	/** 
	 * Agrega el terminal de Partida
	 * Clase ItinerarioAgenciaPartida
	 * @param oItinerarioAgenciaPartida
	 */
 	private void cargaListboxTerminalPartida(ItinerarioAgenciaPartida oItinerarioAgenciaPartida){
 		Listitem item = null;
		Listcell cell = null;
		Integer x = listboxTerminalPartida.getChildren().size();
		item = new Listitem();
		cell = new Listcell((x.toString()));
		item.appendChild(cell); //Correlativo
		cell = new Listcell(oItinerarioAgenciaPartida.getAgencia().getNombreCorto());
		item.appendChild(cell);
		cell = new Listcell(oItinerarioAgenciaPartida.getHoraPartida());
		cell.setStyle("font-size:11px !important");
		item.appendChild(cell);
		
		final Image image= new Image();		
		image.setHeight("17px");
		cell = new Listcell();
		cell .appendChild(image);
		item.appendChild(cell);
		
		image.addEventListener(Events.ON_CLICK, new EventListener<Event>() {
			@Override
			public void onEvent(Event event) throws Exception {
				if(image.getSrc().equals(enabledDelete))
					quitaTerminal(true, listboxTerminalPartida);
			}
		});
		
		item.setValue(oItinerarioAgenciaPartida);
		listboxTerminalPartida.appendChild(item);
		imagenEliminarTerminale(listboxTerminalPartida);
 	}
 	
 	/**
 	 * Agrega en el ListBox el Terminal de Llegada.
 	 * 	Clase ItinerarioAgenciaLlegada
 	 * @param oItinerarioAgenciaLlegada
 	 * @throws Exception
 	 */
 	private  void cargaListboxTerminalLlegada(ItinerarioAgenciaLlegada oItinerarioAgenciaLlegada) throws Exception{
		Listitem item = null;
		Listcell cell = null;
		
		Integer x = listboxTerminalLlegada.getChildren().size();
		item = new Listitem();
		cell = new Listcell((x.toString()));
		item.appendChild(cell); //Correlativo
		cell = new Listcell(oItinerarioAgenciaLlegada.getAgencia().getNombreCorto());
		item.appendChild(cell);
		cell = new Listcell(oItinerarioAgenciaLlegada.getHoraLlegada());
		cell.setStyle("font-size:11px !important");
		item.appendChild(cell);
		
		final Image image= new Image();
		image.setHeight("17px");
		cell = new Listcell();
		cell .appendChild(image);
		item.appendChild(cell);
		
		image.addEventListener(Events.ON_CLICK, new EventListener<Event>() {
			@Override
			public void onEvent(Event event) throws Exception {
				if(image.getSrc().equals(enabledDelete))
					quitaTerminal(false, listboxTerminalLlegada);
			}
		});
		
		item.setValue(oItinerarioAgenciaLlegada);
		listboxTerminalLlegada.appendChild(item);
		imagenEliminarTerminale(listboxTerminalLlegada);
	}
 	
 	/**
 	 * Agrea terminal de Partida o de Llegada. atravez del Boton "Add"
 	 * @param oClass			: Clase según el terminal a agregar. (ItinerarioAgenciaPartida o ItinerarioAgenciaLlegada)
 	 * @param listBoxTerminal	: ListBox en la que se cargará el terminal
 	 * @param cmbTerminal		: ComboBox de conde se agregará el terminal de Partida o Llegada.	
 	 * @param tbHora			: TimeBox de donde se agregará el Hora de Partida o Llegada
 	 * @throws Exception	
 	 */
 	public void addTerminal(Class<?> oClass, Listbox listBoxTerminal, Combobox cmbTerminal, Timebox tbHora) throws Exception{
 		if (cmbTerminal.getSelectedItem().getValue() instanceof Agencia && tbHora.getValue() != null){ 
 			Listitem itemTerminal = listBoxTerminal.getItemAtIndex(listBoxTerminal.getItems().size() -1);
 			if (oClass.equals(ItinerarioAgenciaPartida.class)){
 				/*Cuando es Terminal Partida*/ 
 				if (!((ItinerarioAgenciaPartida) itemTerminal.getValue()).getAgencia().getId().equals(((Agencia) cmbTerminal.getSelectedItem().getValue()).getId())){					
	 					ItinerarioAgenciaPartida oItinerarioAgenciaPartida = new ItinerarioAgenciaPartida();
 	 					Agencia oAgencia = new Agencia();
 	 					oAgencia.setId(((Agencia) cmbTerminalPartida.getSelectedItem().getValue()).getId());
 	 					oAgencia.setDenominacion(((Agencia) cmbTerminalPartida.getSelectedItem().getValue()).getDenominacion());
 	 					oAgencia.setNombreCorto(((Agencia) cmbTerminalPartida.getSelectedItem().getValue()).getNombreCorto());
 	 					
 	 					oItinerarioAgenciaPartida.setAgencia(oAgencia);
 	 					oItinerarioAgenciaPartida.setHoraPartida(tbHora.getText());
 	 					oItinerarioAgenciaPartida.setLocalidad(((Agencia) cmbTerminalPartida.getSelectedItem().getValue()).getLocalidad());
 	 					cargaListboxTerminalPartida(oItinerarioAgenciaPartida); 					
 	 				
	 			}else{
	 				DlgMessage.information(Messages.getString("WndItinerario.information.Terminal"));
	 			}		
 	 		}else if (oClass.equals(ItinerarioAgenciaLlegada.class)){
 	 				/*Cuando es Terminal Llegada*/
 	 			if (!((ItinerarioAgenciaLlegada) itemTerminal.getValue()).getAgencia().getId().equals(((Agencia) cmbTerminal.getSelectedItem().getValue()).getId())){
	 					ItinerarioAgenciaLlegada oItinerarioAgenciaLlegada = new ItinerarioAgenciaLlegada();
	 					Agencia oAgencia = new Agencia();
	 					oAgencia.setId(((Agencia) cmbTerminalLlegada.getSelectedItem().getValue()).getId());
	 					oAgencia.setDenominacion(((Agencia) cmbTerminalLlegada.getSelectedItem().getValue()).getDenominacion());
	 					oAgencia.setNombreCorto(((Agencia) cmbTerminalLlegada.getSelectedItem().getValue()).getNombreCorto());
	 					
	 					oItinerarioAgenciaLlegada.setAgencia(oAgencia);
	 					oItinerarioAgenciaLlegada.setHoraLlegada(tbHora.getText());
	 					oItinerarioAgenciaLlegada.setLocalidad(((Agencia) cmbTerminalLlegada.getSelectedItem().getValue()).getLocalidad());
	 					cargaListboxTerminalLlegada(oItinerarioAgenciaLlegada);
 	 			}else{
 	 	 			DlgMessage.information(Messages.getString("WndItinerario.information.Terminal"));
 	 	 		}		
 	 		}
 			cmbTerminal.setSelectedIndex(0);
 			tbHora.setValue(null);
 			if (oClass.equals(ItinerarioAgenciaPartida.class))
 				
 				Util.disabledBtnAgregar(true, cmdAddTerPartida, accesoGrabar());
 			else
 				Util.disabledBtnAgregar(true, cmdAddTerLlegada, accesoGrabar());
 		}
 	}
 	
 	/**
 	 * 
 	 * @param terminalPartida	: Variable Booleana; true= Terminal Partida, false=TerminAl Llegada.
 	 * @param listBoxTerminal	: Nombre del ListBox del terminal a Borrar.
 	 * @throws Exception		: ELIMINA EL TERMINAL SELECCIONADO.
 	 */
	public void quitaTerminal (Boolean terminalPartida, Listbox listBoxTerminal) throws Exception{
		 final Listbox ListBoxTerminal = listBoxTerminal;
		Integer index =listBoxTerminal.getSelectedIndex();
		if (index > 0){
			Messagebox.show(Messages.getString("WndItinerario.question.EliminaTerminal"), DlgMessage.NOMBREAPLICACION, DlgMessage.BTN_YESNO, Messagebox.QUESTION, new EventListener<Event>() {
				@Override
				public void onEvent(Event e) throws Exception {
					if(e.getName().equals("onYes")){
						Integer index = ListBoxTerminal.getSelectedIndex();
						ListBoxTerminal.removeItemAt(index);
					}
				}
			});			
		}else{
			if (terminalPartida == true)
				DlgMessage.information(Messages.getString("WndItinerario.information.EliminaTerminalPartida"));
			else 
				DlgMessage.information(Messages.getString("WndItinerario.information.EliminaTerminalLlegada"));
		}
	}
	
	private void HabilitaBotones_AgredarTramo(Boolean estado){
		if(estado)
			cmdAgregarTramo.setImage("/resources/mp_agregarDisabled.png");
		else
			cmdAgregarTramo.setImage("/resources/cmdAgregarTramo.png");
		cmdAgregarTramo.setDisabled(estado);
	}

	/**
	 * Confirmación para quitar un tramo
	 * @param Index 	: El indice del tramo seleccionado.
	 * @throws Exception
	 */
	public void confirmaQuitarTramo(Integer Index) throws Exception{ 
		final Integer index= Index;
		if (index >=0){
			if (index == listboxDetalleItinerario.getItems().size() -1){	
				Messagebox.show(Messages.getString("WndItinerario.question.BorrarTramo"), DlgMessage.NOMBREAPLICACION, DlgMessage.BTN_YESNO, Messagebox.QUESTION, new EventListener<Event>() {
					@Override
					public void onEvent(Event e) throws Exception {
						if(e.getName().equals("onYes")){
							quitaTramo(index);
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
	 * Quita el tramo seleccionado.
	 * @param index	: El indice del tramo seleccionado.
	 * @throws Exception
	 */
	public void quitaTramo(Integer index) throws Exception{
		listboxDetalleItinerario.removeItemAt(index);
		
		if (index==0){
			cmbTerminalPartida.getItems().clear();
			cmbTerminalLlegada.getItems().clear();
			tbterHoraPartida.setText("");
			tbterHoraLlegada.setText("");
			Util.limpiarListbox(listboxTerminalPartida);
			Util.limpiarListbox(listboxTerminalLlegada);
			cmbOrigen.setSelectedIndex(0);
		}else{
			Listitem itemDetalleItinerario = listboxDetalleItinerario.getItemAtIndex(index -1);
			dbFechaItinerario.setValue(((DetalleItinerario) itemDetalleItinerario.getValue()).getFechaLlegada());	
			tbHoraPartida.setText(((DetalleItinerario) itemDetalleItinerario.getValue()).getHoraLlegada());
			Util.seleccionarValorItemCombo(Agencia.class, cmbTermOrigen, ((DetalleItinerario) itemDetalleItinerario.getValue()).getAgenciaPartida().getId());
			Util.seleccionarValorItemCombo(Localidad.class, cmbOrigen, ((DetalleItinerario) itemDetalleItinerario.getValue()).getRuta().getLocalidadDestino().getId());
			onChangeRutas();
			
			recargarListboxTerminales(listboxDetalleItinerario);
			
//				ItinerarioAgenciaLlegada oItinerarioAgenciaLlegada = new ItinerarioAgenciaLlegada();
//				Agencia oAgencia = new Agencia();
//				oAgencia.setId(((DetalleItinerario) itemDetalleItinerario.getValue()).getAgenciaLlegada().getId());
//				oAgencia.setNombreCorto(((DetalleItinerario) itemDetalleItinerario.getValue()).getAgenciaLlegada().getNombreCorto());
//				oAgencia.setDenominacion(((DetalleItinerario) itemDetalleItinerario.getValue()).getAgenciaLlegada().getDenominacion());
//				oItinerarioAgenciaLlegada.setAgencia(oAgencia);
//				oItinerarioAgenciaLlegada.setHoraLlegada(((DetalleItinerario) itemDetalleItinerario.getValue()).getHoraLlegada());
//				Util.limpiarListbox(listboxTerminalLlegada);
//				cargaListboxTerminalPartida(oiti );
			
			cargarTerminalPartidaLlegada(cmbTerminalPartida, false, listboxDetalleItinerario, 1);
			cargarTerminalPartidaLlegada(cmbTerminalLlegada, false, listboxDetalleItinerario, 2);
//			cargarTerm(cmbTerminalLlegada, false, (((DetalleItinerario) itemDetalleItinerario.getValue()).getRuta().getLocalidadDestino().getId()));
		}
		esEdicionTramo=false;	
//		CargarRutasTarifas();
		imageEliminarTramo();
	}
	
	/**
	 * Vuelve a cargar los terminales de partida y llegada segun el detalle de itinerario
	 * @param lstbxDetalleItinerario
	 * @throws Exception
	 */
	public void recargarListboxTerminales(Listbox lstbxDetalleItinerario) throws Exception {
		Util.limpiarListbox(listboxTerminalPartida);
		Util.limpiarListbox(listboxTerminalLlegada);
		for(Listitem item : lstbxDetalleItinerario.getItems()) {
			DetalleItinerario detalle = (DetalleItinerario)item.getValue();
			
			ItinerarioAgenciaPartida oItinerarioAgenciaPartida = new ItinerarioAgenciaPartida();
			Agencia oAgencia = new Agencia();
			oAgencia.setId(detalle.getAgenciaPartida().getId());
			oAgencia.setNombreCorto(detalle.getAgenciaPartida().getNombreCorto());
			oAgencia.setDenominacion(detalle.getAgenciaPartida().getDenominacion());
			oItinerarioAgenciaPartida.setAgencia(oAgencia);
			oItinerarioAgenciaPartida.setHoraPartida(detalle.getHoraPartida());
			oItinerarioAgenciaPartida.setLocalidad(detalle.getRuta().getLocalidadOrigen());
			cargaListboxTerminalPartida(oItinerarioAgenciaPartida);
			
			ItinerarioAgenciaLlegada oItinerarioAgenciaLlegada = new ItinerarioAgenciaLlegada();
			Agencia oAgenciaLlegada = new Agencia();
			oAgenciaLlegada.setId(detalle.getAgenciaLlegada().getId());
			oAgenciaLlegada.setNombreCorto(detalle.getAgenciaLlegada().getNombreCorto());
			oAgenciaLlegada.setDenominacion(detalle.getAgenciaLlegada().getDenominacion());
			oItinerarioAgenciaLlegada.setAgencia(oAgenciaLlegada);
			oItinerarioAgenciaLlegada.setHoraLlegada(detalle.getHoraLlegada());
			oItinerarioAgenciaLlegada.setLocalidad(detalle.getRuta().getLocalidadDestino());
			cargaListboxTerminalLlegada(oItinerarioAgenciaLlegada);
		}
	}
	
	/**
	 * Para la edición del Tramo seleccionado
	 * @throws Exception
	 */
	public void editarTramo() throws Exception{
		Integer index = listboxDetalleItinerario.getSelectedIndex();
		if (index >= 0){
			if (index == listboxDetalleItinerario.getItems().size() -1){
				Listitem itemDetalleItinerario = listboxDetalleItinerario.getItemAtIndex(index);
				Util.seleccionarValorItemCombo(Localidad.class, cmbOrigen, ((DetalleItinerario) itemDetalleItinerario.getValue()).getRuta().getLocalidadOrigen().getId());	/*Selecciona el Origen*/
				onChangeRutas(); 																																			/*Carga las Rutas*/
				Util.seleccionarValorItemCombo(Ruta.class, cmbRuta, ((DetalleItinerario) itemDetalleItinerario.getValue()).getRuta().getId());								/*Seleecion el Origen*/
				dbFechaItinerario.setValue(((DetalleItinerario) itemDetalleItinerario.getValue()).getFechaPartida());														/*Recupera Fecha partida*/
				tbHoraPartida.setText(((DetalleItinerario) itemDetalleItinerario.getValue()).getHoraPartida());																/*Recupera Hora de Partida*/
				cargarTerm(cmbTermDestino, false, ((DetalleItinerario) itemDetalleItinerario.getValue()).getRuta().getLocalidadDestino().getId()); 							/*carga Terminal Destino*/
				Util.seleccionarValorItemCombo(Agencia.class, cmbTermOrigen, ((DetalleItinerario) itemDetalleItinerario.getValue()).getAgenciaPartida().getId());			/*Selecciona el Terminal Origen*/
				Util.seleccionarValorItemCombo(Agencia.class, cmbTermDestino, ((DetalleItinerario) itemDetalleItinerario.getValue()).getAgenciaLlegada().getId());			/*Selecciona le Terminal Destino*/
				
				if (cmbTipoItinerario.getSelectedIndex()==0){
					/*Solo para la edición del Itinerario, despues de que esta aya sido guardado*/
					Util.seleccionarValorItemCombo(Servicio.class,cmbServicio, ((DetalleItinerario) itemDetalleItinerario.getValue()).getItinerario().getServicio().getId());/**Selecciona el Servico*/ 
					Util.seleccionarValorItemCombo(TipoItinerario.class,cmbTipoItinerario, ((DetalleItinerario) itemDetalleItinerario.getValue()).getItinerario().getTipoItinerario().getId());/**Selecciona el Tipo de Itinerario*/
				}
				
				/*Se utilizan para la edicion de los tramos.*/
				indexEdicionTramo=index;
				esEdicionTramo=true;
				
				if(action==ACTION_MODIFY){
					if(listboxDetalleItinerario.getItems().size()==1)
						dbFechaItinerario.setDisabled(true);
					else
						dbFechaItinerario.setDisabled(false);
				}else dbFechaItinerario.setDisabled(false);
				
			}else{DlgMessage.information(Messages.getString("WndItinerario.information.NoEditarTramo"));}			
		}else{DlgMessage.information(Messages.getString("WndItinerario.information.SeleccionarTramo"));}
	}
	
	/**
	 * Carga los terminales de origen y destino, segun la ruta seleccionada
	 * 
	 * @param combobox		: ComboBox en donde se cargara el terminal 
	 * @param todos			: True muestra "TODOS"; false muesta "SELECCIONE"
	 * @param localidad		: identificador unico de la localidad
	 * @throws Exception  	
	 */
	public static void cargarTerm(Combobox combobox, Boolean todos, Integer localidad) throws Exception{
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
	}
	
	/**
	 * Carga los terminales de Partida y Llegada
	 * @param combobox					:
	 * @param todos						:
	 * @param lstbxDetalleItinerario	:
	 * @param tipoTerminal				: 0-Ambos terminales, 1-Terminal Partida, 2-Terminal Llegada
	 * @throws Exception
	 */
	public static void cargarTerminalPartidaLlegada(Combobox combobox, Boolean todos, Listbox lstbxDetalleItinerario, int tipoTerminal) throws Exception{
		String strIdLocalidadPartida = "";
		String strIdLocalidadLlegada = "";
		for(Listitem item : lstbxDetalleItinerario.getItems()) {
			DetalleItinerario detalle = (DetalleItinerario)item.getValue();
			if(!strIdLocalidadPartida.isEmpty()) 
				strIdLocalidadPartida = strIdLocalidadPartida + ",";						
			strIdLocalidadPartida = strIdLocalidadPartida + detalle.getRuta().getLocalidadOrigen().getId();
			
			if(!strIdLocalidadLlegada.isEmpty()) 
				strIdLocalidadLlegada = strIdLocalidadLlegada + ",";						
			strIdLocalidadLlegada = strIdLocalidadLlegada + detalle.getRuta().getLocalidadDestino().getId();
		}				
		
		List<Agencia> lstAgencias = null;
		if(tipoTerminal==1) 
			lstAgencias = ServiceLocator.getAgenciaManager().buscarAgenciaByLocalidad(strIdLocalidadPartida);
		else
			lstAgencias = ServiceLocator.getAgenciaManager().buscarAgenciaByLocalidad(strIdLocalidadLlegada);
			
		combobox.getItems().clear();
		UtilData.cargarGenericData(combobox, todos);	
		for (int l = 0; l < lstAgencias.size(); l ++) {
			Agencia oAgencia = lstAgencias.get(l);
			Comboitem oComboitem = new Comboitem();
			oComboitem.setLabel(oAgencia.getNombreCorto());
			oComboitem.setValue(oAgencia);
			combobox.appendChild(oComboitem);
		}
	}
	
	/**
	 * Graba Itinerario Agenica Llegada
	 * 
	 * @param action 0=Nuevo; 1=Modificar 
	 * @throws Exception
	 */
	public void guardaAgenciaLlegada(int action) throws Exception{
		if (action==ACTION_MODIFY){
			ServiceLocator.getItinerarioAgenciaLlegadaManager().delete(oitinerario.getId());	
		}
		
	    for (int x =0; x < listboxTerminalLlegada.getItems().size(); x ++){
	    	Listitem itemTerminalLlegada = listboxTerminalLlegada.getItemAtIndex(x);
	    	
	    	ItinerarioAgenciaLlegada oItinerarioAgenciaLlegada = new ItinerarioAgenciaLlegada();
	    	ItinerarioAgenciaLlegadaID oItinerarioAgenciaLlegadaID = new ItinerarioAgenciaLlegadaID();
	    	Agencia oagenciaLlegada= new Agencia();
	    	
	    	oagenciaLlegada.setId(((ItinerarioAgenciaLlegada) itemTerminalLlegada.getValue()).getAgencia().getId());
	    	oItinerarioAgenciaLlegadaID.setIdItinerario(oitinerario.getId());
	    	oItinerarioAgenciaLlegadaID.setIdAgencia(oagenciaLlegada.getId());
	    	
	    	oItinerarioAgenciaLlegada.setItinerarioAgenciaLlegadaID(oItinerarioAgenciaLlegadaID);
	    	oItinerarioAgenciaLlegada.setItinerario(oitinerario);
	    	oItinerarioAgenciaLlegada.setAgencia(oagenciaLlegada);
	    	oItinerarioAgenciaLlegada.setLocalidad(((ItinerarioAgenciaLlegada) itemTerminalLlegada.getValue()).getLocalidad());
	    	oItinerarioAgenciaLlegada.setHoraLlegada(((ItinerarioAgenciaLlegada) itemTerminalLlegada.getValue()).getHoraLlegada());
	    	oItinerarioAgenciaLlegada.setEstadoRegistro(Constantes.VALUE_ACTIVO);
	    	
	    	UtilData.auditarRegistro(oItinerarioAgenciaLlegada, getUsuario(), Executions.getCurrent());
	    	ServiceLocator.getItinerarioAgenciaLlegadaManager().guardar(oItinerarioAgenciaLlegada);
	    }
	}
	
	/**
	 * Graba Itinerario Agenica Partida
	 * 
	 * @param action 0=Nuevo; 1=Modificar 
	 * @throws Exception
	 */
	public void guardaAgenciaPartida(int action) throws Exception{
		
		if (action==ACTION_MODIFY){
			ServiceLocator.getItinerarioAgenciaPartidaManager().delete(oitinerario.getId());	
		}
		
	    for (int x =0; x < listboxTerminalPartida.getItems().size(); x ++){
	    	Listitem itemTerminalPartida = listboxTerminalPartida.getItemAtIndex(x);
	    	
	    	ItinerarioAgenciaPartida oItinerarioAgenciaPartida = new ItinerarioAgenciaPartida();
	    	ItinerarioAgenciaPartidaID oItinerarioAgenciaPartidaID = new ItinerarioAgenciaPartidaID();
	    	Agencia oagenciaPartida = new Agencia();
	    	
	    	oagenciaPartida.setId(((ItinerarioAgenciaPartida) itemTerminalPartida.getValue()).getAgencia().getId());
	    	oItinerarioAgenciaPartidaID.setIdItinerario(oitinerario.getId());
	    	oItinerarioAgenciaPartidaID.setIdAgencia(oagenciaPartida.getId());
	    	
	    	oItinerarioAgenciaPartida.setItinerarioAgenciaPartidaID(oItinerarioAgenciaPartidaID);
	    	oItinerarioAgenciaPartida.setItinerario(oitinerario);
	    	oItinerarioAgenciaPartida.setAgencia(oagenciaPartida);
	    	oItinerarioAgenciaPartida.setLocalidad(((ItinerarioAgenciaPartida) itemTerminalPartida.getValue()).getLocalidad());
	    	oItinerarioAgenciaPartida.setHoraPartida(((ItinerarioAgenciaPartida) itemTerminalPartida.getValue()).getHoraPartida());
	    	oItinerarioAgenciaPartida.setEstadoRegistro(Constantes.VALUE_ACTIVO);
	    	
	    	UtilData.auditarRegistro(oItinerarioAgenciaPartida, getUsuario(), Executions.getCurrent());
	    	ServiceLocator.getItinerarioAgenciaPartidaManager().guardar(oItinerarioAgenciaPartida);
	    }
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
		for (int i=0; i< listboxDetalleItinerario.getItems().size(); i ++){
			Listitem itemDetalleItinerario = listboxDetalleItinerario.getItemAtIndex(i);
			
			osecuenciaTramo.setOrigen(((DetalleItinerario) itemDetalleItinerario.getValue()).getRuta().getLocalidadOrigen().getId());
			osecuenciaTramo.setDestino(((DetalleItinerario) itemDetalleItinerario.getValue()).getRuta().getLocalidadDestino().getId());
			osecuenciaTramo.setOrden(i + 1);
			
			if (osecuenciaTramo.getOrden() == 1){
				if (SecuenciTramo.equals("")){
					if (listboxDetalleItinerario.getItems().size()==1)
						SecuenciTramo = osecuenciaTramo.getOrigen() +"-"+ osecuenciaTramo.getDestino() +"-"+ osecuenciaTramo.getOrden();
					else
						SecuenciTramo = osecuenciaTramo.getOrigen() +"-"+ osecuenciaTramo.getDestino() +"-"+ osecuenciaTramo.getOrden() +";";
				}
			}else{
				if (i == listboxDetalleItinerario.getItems().size() -1){
					SecuenciTramo = SecuenciTramo + osecuenciaTramo.getOrigen() +"-"+ osecuenciaTramo.getDestino() +"-"+ osecuenciaTramo.getOrden();
				}else{
					SecuenciTramo = SecuenciTramo + osecuenciaTramo.getOrigen() +"-"+ osecuenciaTramo.getDestino() +"-"+ osecuenciaTramo.getOrden() +";";
				}
			}
				
		}	
		return SecuenciTramo;
	}
	
	/**
	 * Carga lista de itinerarios según la busqueda.
	 * 	
	 * @param lstItinerarios	: Lista de itinerarios recuperados en la busqueda 
	 * @throws Exception 
	 */
	private void ListaItinerarios(List<DetalleItinerario> lstItinerarios) throws Exception{
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
	private void ManteniemientoItinerario(Long id) throws Exception{	
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
			
			DetalleItinerario oDetalleItinerario =lstItinerariosDetalle.get(0);
			destino =oDetalleItinerario.getRuta().getDestino();
			Util.seleccionarValorItemCombo(TipoItinerario.class, cmbTipoItinerario, oDetalleItinerario.getItinerario().getTipoItinerario().getId());
			Util.seleccionarValorItemCombo(Servicio.class, cmbServicio, oitinerario.getServicio().getId());
			Util.seleccionarValorItemCombo(Localidad.class, cmbOrigen, oitinerario.getRuta().getLocalidadDestino().getId());
			onChangeRutas();
			
			/*Validación que evita la duplicidad de tramos, en el Detalle del Itinerario*/
			for(DetalleItinerario detalleItinerario : lstItinerariosDetalle){
				primerIndex += +1;
				String origen = detalleItinerario.getRuta().getOrigen();
				if (destino.equals(origen) || primerIndex==1){
					x += +1;
					agregarItinerario(detalleItinerario, listboxDetalleItinerario);
										
					destino=detalleItinerario.getRuta().getDestino();
				}
			}
			
			
		String localidadPartida = "";
		String localidadLlegada = "";
		for(Listitem itDetalle:listboxDetalleItinerario.getItems()) {
			DetalleItinerario detalle = ((DetalleItinerario)itDetalle.getValue());
			if(!localidadPartida.isEmpty())
				localidadPartida = localidadPartida+",";
			localidadPartida = localidadPartida + detalle.getRuta().getLocalidadOrigen().getId();
			
			if(!localidadLlegada.isEmpty())
				localidadLlegada = localidadLlegada+",";
			localidadLlegada = localidadLlegada + detalle.getRuta().getLocalidadDestino().getId();
		}

		/*Carga Combobox Terminal Partida*/
		cargarTerminalPartidaLlegada(cmbTerminalPartida, false, listboxDetalleItinerario, 1);
			
		/*Carga Combobox Terminal Llegada*/
		cargarTerminalPartidaLlegada(cmbTerminalLlegada, false, listboxDetalleItinerario, 2);
		

		/*Recupera Itinerario Agencia partida**/
		List<ItinerarioAgenciaPartida> lstItinerariosAgenciaPartida;	
		lstItinerariosAgenciaPartida= (List<ItinerarioAgenciaPartida>) ServiceLocator.getItinerarioAgenciaPartidaManager().buscarAgenciasPartida(id, Constantes.VALUE_ACTIVO, localidadPartida);
		if(lstItinerariosDetalle.size()>0){
			item = null;
			 cell = null;
			 x =0; /*Contador utilizado para el item.*/
				
			for(ItinerarioAgenciaPartida itinerarioAgenciaPartida : lstItinerariosAgenciaPartida){
				x += +1;
				item = new Listitem();
				cell = new Listcell((x.toString()));
				item.appendChild(cell); //Correlativo
				cell = new Listcell(itinerarioAgenciaPartida.getAgencia().getNombreCorto());
				item.appendChild(cell);
				cell = new Listcell(itinerarioAgenciaPartida.getHoraPartida());
				cell.setStyle("font-size:11px !important");
				item.appendChild(cell);
				
				final Image image= new Image();				
				image.setHeight("17px");
				cell = new Listcell();
				cell .appendChild(image);
				item.appendChild(cell);
				
				image.addEventListener(Events.ON_CLICK, new EventListener<Event>() {
					@Override
					public void onEvent(Event event) throws Exception {
						if(image.getSrc().equals(enabledDelete))
							quitaTerminal(true, listboxTerminalPartida);
					}
				});
				
				item.setValue(itinerarioAgenciaPartida);
				listboxTerminalPartida.appendChild(item);	
				imagenEliminarTerminale(listboxTerminalPartida);
				
			}
		}
			
		/*Recupera Itinerario Agencia Llegada**/
		List<ItinerarioAgenciaLlegada> lstItinerariosAgenciaLlegada;
		lstItinerariosAgenciaLlegada= (List<ItinerarioAgenciaLlegada>) ServiceLocator.getItinerarioAgenciaLlegadaManager().buscarAgenciasLlegada(id, Constantes.VALUE_ACTIVO,localidadLlegada);
		if(lstItinerariosDetalle.size()>0){
			 item = null;
			 cell = null;
			 x =0; /**Contador utilizado para el item.*/
				
			for(ItinerarioAgenciaLlegada itinerarioAgenciaLlegada : lstItinerariosAgenciaLlegada){
				x += +1;
				item = new Listitem();
				cell = new Listcell((x.toString()));
				item.appendChild(cell); //Correlativo
				cell = new Listcell(itinerarioAgenciaLlegada.getAgencia().getNombreCorto());
				item.appendChild(cell);
				cell = new Listcell(itinerarioAgenciaLlegada.getHoraLlegada());
				cell.setStyle("font-size:11px !important");
				item.appendChild(cell);
				
				final Image image= new Image();				
				image.setHeight("17px");
				cell = new Listcell();
				cell .appendChild(image);
				item.appendChild(cell);
				
				image.addEventListener(Events.ON_CLICK, new EventListener<Event>() {
					@Override
					public void onEvent(Event event) throws Exception {
						if(image.getSrc().equals(enabledDelete))
							quitaTerminal(false, listboxTerminalLlegada);
					}
				});
				
				item.setValue(itinerarioAgenciaLlegada);
				listboxTerminalLlegada.appendChild(item);	
				imagenEliminarTerminale(listboxTerminalLlegada);
				}
			}
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
//		cell = new Listcell(odetalleItinerario.getRuta().getOrigen());
//		item.appendChild(cell);
		cell = new Listcell(odetalleItinerario.getRuta().toString());
		item.appendChild(cell);
		cell = new Listcell(servicio.getDenominacion());
		item.appendChild(cell);
//		cell = new Listcell(Constantes.FORMAT_DATE.format(odetalleItinerario.getFechaPartida()));
//		cell.setStyle("font-size:11px !important");
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
//		cell = new Listcell(Constantes.FORMAT_DATE.format(odetalleItinerario.getFechaLlegada()));
//		cell.setStyle("font-size:11px !important");
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
		cell = new Listcell();
		hbox.appendChild(imageDelete);
		cell .appendChild(hbox);	
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
		
		Separator separator=new Separator();
		separator.setWidth("5px");
		hbox.appendChild(separator);
		
		final Image imageEditar= new Image();
		imageEditar.setHeight("15px");
		cell = new Listcell();
		hbox.appendChild(imageEditar);
		cell .appendChild(hbox);
				
		item.appendChild(cell);
		
		imageEditar.setZindex(x);
		imageEditar.addEventListener(Events.ON_CLICK,new EventListener<Event>() {
			@Override
			public void onEvent(Event event) throws Exception {
				if (imageEditar.getSrc().equals(enabledEdit)){
					int index=imageEditar.getZindex();
					listboxDetalleItinerario.setSelectedIndex(index-1);
					editarTramo();
				}
			}
		});

		item.setValue(odetalleItinerario);
		lisBoxDetalleItinerario.appendChild(item);
		imageEliminarTramo();
	}
	
	/**
	 * Estable el tipo de imagen que representa a eliminar tramos(imagen activa o inactiva)
	 */
	private void imageEliminarTramo(){		
		if(listboxDetalleItinerario.getItems().size()>0){
			for(int y=0; y<listboxDetalleItinerario.getItems().size();y++){
				Integer c=listboxDetalleItinerario.getItems().size();
				Component it = (Component) listboxDetalleItinerario.getChildren().get(y+1);
				Listcell cll=(Listcell) it.getChildren().get(9);
//				Listcell cll=(Listcell) it.getChildren().get(10);
				Hbox hbox=(Hbox)cll.getChildren().get(0);
				if(c.equals(y+1) && btnGuardar.isDisabled()==false){
//					((Image)cll.getChildren().get(0)).setSrc(enabled);			
//					((Image)cll.getChildren().get(0)).setTooltiptext("Eliminar Tramo");
					((Image)hbox.getChildren().get(0)).setSrc(enabledDelete);			
					((Image)hbox.getChildren().get(0)).setTooltiptext("Eliminar Tramo");
					((Image)hbox.getChildren().get(0)).setStyle("cursor:pointer");
					((Image)hbox.getChildren().get(2)).setSrc(enabledEdit);			
					((Image)hbox.getChildren().get(2)).setTooltiptext("Editar Tramo");
					((Image)hbox.getChildren().get(2)).setStyle("cursor:pointer");
				}else{
//					((Image)cll.getChildren().get(0)).setSrc(disabled);			
//					((Image)cll.getChildren().get(0)).setTooltiptext("");
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
	
	
	private void imagenEliminarTerminale(Listbox listbox){
		if(listbox.getItems().size()>0){
			for(int y=0; y<listbox.getItems().size();y++){
				Component it = (Component) listbox.getChildren().get(y+1);
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
	
	public void limpiaItinerario(){
		cmbRuta.getItems().clear();
		cmbTermOrigen.getItems().clear();
		cmbTermDestino.getItems().clear();
		cmbTerminalPartida.getItems().clear();
		cmbTerminalLlegada.getItems().clear();
		tbHoraPartida.setValue(null);
		tbterHoraPartida.setValue(null);
		tbterHoraLlegada.setValue(null);
	}

		
	public void limpiarTodasLasListas(){
		Util.limpiarListbox(listboxDetalleItinerario);
		Util.limpiarListbox(listboxTerminalPartida);
		Util.limpiarListbox(listboxTerminalLlegada);
		Util.limpiarGrid(grdRutasTrifas);
	}
	
	public void cargaComboDefaul(){
		limpiaItinerario();
		UtilData.cargarGenericData(cmbRuta, false);
		UtilData.cargarGenericData(cmbTermOrigen, false);
		UtilData.cargarGenericData(cmbTermDestino, false);
		UtilData.cargarGenericData(cmbTerminalPartida, false);
		UtilData.cargarGenericData(cmbTerminalLlegada, false);
	}
	
	public void LimpiaCombos(){
		cmbTipoItinerario.setSelectedIndex(0);
		cmbServicio.setSelectedIndex(0);
		cmbOrigen.setSelectedIndex(0);
		cmbRuta.setSelectedIndex(0);
		cmbTermOrigen.setSelectedIndex(0);
		cmbTermDestino.setSelectedIndex(0);
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
					if (! (oitinerario.getId().equals(((DetalleItinerario) listTramoDuplicado.get(i)).getItinerario().getId())) )
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
				for (int x=0 ; x < dias; x ++){
					Listbox lbDetalleItinerario = new Listbox();
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
								guardarItinerarios(action, fechaPartida, FechaLLegada, lbDetalleItinerario, oitinerario);
							}
							break;
						}
						
					}else //Si es un nuevo registro
						guardarItinerarios(ACTION_NEW, fechaPartida, FechaLLegada, lbDetalleItinerario, oitinerario);
					
					fechaPartida.setTime(fechaPartida.getTime() + Constantes.MILISEGUNDOS_X_DIA );
				}	

			}else{ //Cuando es solamente una Día 
				Listbox lbDetalleItinerario = new Listbox();
				Calendar FechaLLegada = Calendar.getInstance();
				
				if(action==ACTION_MODIFY ) {//Si es una actualizacion
					if(lstItinerariosUpdate.size()==1 && listVentasNoUpdate.size()==0){// si el itinerario a actualizar no tiene ventas
						Itinerario itinerario=lstItinerariosUpdate.get(0);
						textboxId.setText(itinerario.getId().toString());
						oitinerario.setId(itinerario.getId());
						guardarItinerarios(action, fechaPartida, FechaLLegada, lbDetalleItinerario, oitinerario);
					}
				}else{//Si es un registro nuevo
					guardarItinerarios(ACTION_NEW, fechaPartida, FechaLLegada, lbDetalleItinerario, oitinerario);
				}
				
			}
			
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
	private void guardarItinerarios(int action, Date fechaPartida, Calendar FechaLLegada, Listbox lbDetalleItinerario, Itinerario itinerario) throws Exception{
		for (int y=0; y < listboxDetalleItinerario.getItems().size(); y ++){
			Listitem itemDetalleItinerario = listboxDetalleItinerario.getItemAtIndex(y);
			DetalleItinerario odetalleItinerario = new DetalleItinerario();
			Timebox tbhoraPartida = new Timebox();
			Date sfechaPartida = new Date();
			tbhoraPartida.setFormat("HH:mm");
			tbhoraPartida.setText((((DetalleItinerario) itemDetalleItinerario.getValue()).getHoraPartida()));
			
			Double horasViaje = ((DetalleItinerario) itemDetalleItinerario.getValue()).getRuta().getHorasViaje();
			if (y==0)
				sfechaPartida.setTime(fechaPartida.getTime());
			else
				sfechaPartida.setTime(FechaLLegada.getTime().getTime());
			
			/*Obtiene la Fecha y hora de llegada*/
			Calendar FechaHoraLLegada = Calendar.getInstance();
			FechaHoraLLegada.setTimeInMillis(sfechaPartida.getTime());
			FechaHoraLLegada.set(FechaHoraLLegada.MONTH, sfechaPartida.getMonth());
			FechaHoraLLegada.set(FechaHoraLLegada.HOUR_OF_DAY, tbhoraPartida.getValue().getHours());
			FechaHoraLLegada.set(FechaHoraLLegada.MINUTE, tbhoraPartida.getValue().getMinutes());
			FechaHoraLLegada.set(FechaHoraLLegada.SECOND, tbhoraPartida.getValue().getSeconds());
			FechaHoraLLegada.add(FechaHoraLLegada.MILLISECOND, (int) (Util.horasMinutos(horasViaje).intValue()));
									
			/*Asigna solamente la fecha llegada en formato "dd/MM/yyyy"**/
			FechaLLegada.setTime(FechaHoraLLegada.getTime());
			FechaLLegada.set(FechaLLegada.HOUR_OF_DAY, 0);
			FechaLLegada.set(FechaLLegada.MINUTE, 0);
			FechaLLegada.set(FechaLLegada.SECOND, 0);

			odetalleItinerario.setId((long) 0);
			odetalleItinerario.setItinerario(itinerario);
			odetalleItinerario.setRuta(((DetalleItinerario) itemDetalleItinerario.getValue()).getRuta());
			odetalleItinerario.setAgenciaPartida(((DetalleItinerario) itemDetalleItinerario.getValue()).getAgenciaPartida());
			odetalleItinerario.setFechaPartida(sfechaPartida);
			odetalleItinerario.setHoraPartida(((DetalleItinerario) itemDetalleItinerario.getValue()).getHoraPartida());
			odetalleItinerario.setAgenciaLlegada(((DetalleItinerario) itemDetalleItinerario.getValue()).getAgenciaLlegada());
			odetalleItinerario.setFechaLlegada(FechaLLegada.getTime());
			odetalleItinerario.setHoraLlegada(((DetalleItinerario) itemDetalleItinerario.getValue()).getHoraLlegada());
			odetalleItinerario.setTarifa(((DetalleItinerario) itemDetalleItinerario.getValue()).getTarifa());
			odetalleItinerario.setEstadoRegistro(Constantes.VALUE_ACTIVO);
			
			/*Agrega el tramo, para recuperarlo al momneto del guardado*/
			agregarItinerario(odetalleItinerario, lbDetalleItinerario);
		}
		this.guardaItineratio(action,lbDetalleItinerario);
		this.guardaItinerarioDetalle(action,lbDetalleItinerario);
		this.guardaAgenciaPartida(action);
		this.guardaAgenciaLlegada(action);
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
			for(int x=0; x<listboxDetalleItinerario.getItems().size();x++){
				DetalleItinerario detallIT=listboxDetalleItinerario.getItemAtIndex(x).getValue();
				DetalleItinerario detallFT=null;
				if(listboxDetalleItinerario.getItems().size()> x+1)
					detallFT=listboxDetalleItinerario.getItemAtIndex(x+1).getValue();
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
			if(esEdicionTramo==false && listboxDetalleItinerario.getItems().size()>0)
				detallUT=listboxDetalleItinerario.getItemAtIndex(listboxDetalleItinerario.getItems().size()-1).getValue();
			else if (esEdicionTramo && listboxDetalleItinerario.getItems().size()>1){
				detallUT=listboxDetalleItinerario.getItemAtIndex(listboxDetalleItinerario.getItems().size()-2).getValue();
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
}
