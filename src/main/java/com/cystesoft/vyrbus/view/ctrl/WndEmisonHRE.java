/**
 * Proyecto		: SISVYR
 * Sistema		: Sistema de Ventas y Reservas
 * Descripción	: 
 * Autor		: José Abanto
 * Fecha		: 22/08/2014
 * Hora			: 10:16:19
 */
package com.cystesoft.vyrbus.view.ctrl;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TreeMap;

import javax.xml.ws.WebServiceException;

import mstc.ws.gob.pe.ArrayOfMConductor;
import mstc.ws.gob.pe.ArrayOfMPasajero;
import mstc.ws.gob.pe.ArrayOfMTripulante;
import mstc.ws.gob.pe.Finalizar;
import mstc.ws.gob.pe.MConductor;
import mstc.ws.gob.pe.MPasajero;
import mstc.ws.gob.pe.MTripulante;
import mstc.ws.gob.pe.Manifiesto;
import mstc.ws.gob.pe.Tripulante;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zul.Button;
import org.zkoss.zul.Column;
import org.zkoss.zul.Columns;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Comboitem;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.Div;
import org.zkoss.zul.Hbox;
import org.zkoss.zul.Label;
import org.zkoss.zul.Grid;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listheader;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Radio;
import org.zkoss.zul.Row;
import org.zkoss.zul.Rows;
import org.zkoss.zul.Separator;
import org.zkoss.zul.Timebox;
import org.zkoss.zul.Toolbar;
import org.zkoss.zul.Toolbarbutton;
import org.zkoss.zul.Window;

import com.cystesoft.vyrbus.model.bean.Agencia;
import com.cystesoft.vyrbus.model.bean.Bus;
import com.cystesoft.vyrbus.model.bean.DetalleFlotaHRE;
import com.cystesoft.vyrbus.model.bean.HRE;
import com.cystesoft.vyrbus.model.bean.Itinerario;
import com.cystesoft.vyrbus.model.bean.MTCDetalleRuta;
import com.cystesoft.vyrbus.model.bean.MTCDireccionTerminal;
import com.cystesoft.vyrbus.model.bean.NumeroHojaRutaID;
import com.cystesoft.vyrbus.model.bean.Parametros;
import com.cystesoft.vyrbus.model.bean.Pasajero;
import com.cystesoft.vyrbus.model.bean.Personal;
import com.cystesoft.vyrbus.model.bean.ProgramacionServicio;
import com.cystesoft.vyrbus.model.bean.Rol;
import com.cystesoft.vyrbus.model.bean.TipoAgencia;
import com.cystesoft.vyrbus.model.bean.VentaPasaje;
import com.cystesoft.vyrbus.service.exceptions.WSMTCExcepcion;
import com.cystesoft.vyrbus.service.locator.ServiceLocator;
import com.cystesoft.vyrbus.service.util.Constantes;
import com.cystesoft.vyrbus.service.util.CreateDocument;
import com.cystesoft.vyrbus.service.util.Messages;
import com.cystesoft.vyrbus.service.util.Util;
import com.cystesoft.vyrbus.service.util.UtilData;
import com.cystesoft.vyrbus.service.util.WSMTC;
import com.cystesoft.vyrbus.view.ui.DlgMessage;
import com.cystesoft.vyrbus.view.ui.WndBase;
import com.cystesoft.vyrbus.view.ui.WndIFrame;
import com.cystesoft.vyrbus.view.ui.WndImprimir;

/**
 * @author JABANTO
 *
 */
@SuppressWarnings("deprecation")
public class WndEmisonHRE extends WndBase {
	private static final long serialVersionUID = 1L;
	
	private Combobox cmbAgencia;
	private Datebox dtbxFecha;
	private Listbox lstbxSalidas;
	private Combobox cmbBus;
	private Label lblNorServicios;
	private Label lblServicioAsociadosHRE;
	private Label lblServicioFinalizadosHRE;
	private Radio rdSalida;
	private Label lblManifiestosEmitidos;
//	private Radio rdLlegada;
	private int PROCESO_GENERAR_HRE=0;
	private int PROCESO_GENERAR_ME=1;
	private int PROCESO_IMPRIMIR_HRE=2;
	private int PROCESO_PREVISUALIZA_HRE=3;
	private int PROCESO_ANULAR_HRE=4;
	private Listheader lshdHRE;
	private Listheader lshdME;
	private Div divHREEmitidas;
	private Div divHREFinalizadas;
	private Div divMEEmitidas;

	private String ATTRIBUTE_NRO_HOJARUTA="numeroHojaRuta";
	private String ATTRIBUTE_HORA_PARTIDA="horaPartida";
	
	
	List<ProgramacionServicio>lstProgramacion=new ArrayList<ProgramacionServicio>();
	private Window wndFinalizarHRE = null;
	private Window wndGenerarHRE = null;
	private String codigoME="";
		
	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.view.ui.WndBase#initComponents()
	 */
	@Override
	public void initComponents() {
		cmbAgencia=(Combobox)this.getFellow("cmbAgencia");
		dtbxFecha=(Datebox)this.getFellow("dtbxFecha");
		lstbxSalidas=(Listbox)this.getFellow("lstbxSalidas");
		cmbBus=(Combobox)this.getFellow("cmbBus");
		lblNorServicios=(Label)this.getFellow("lblNorServicios");
		lblServicioAsociadosHRE=(Label)this.getFellow("lblServicioAsociadosHRE");
		lblServicioFinalizadosHRE=(Label)this.getFellow("lblServicioFinalizadosHRE");
		rdSalida=(Radio)this.getFellow("rdSalida");
		lblManifiestosEmitidos=(Label)this.getFellow("lblManifiestosEmitidos");
		lshdHRE=(Listheader)this.getFellow("lshdHRE");
		lshdME=(Listheader)this.getFellow("lshdME");
		divHREEmitidas=(Div)this.getFellow("divHREEmitidas");
		divHREFinalizadas=(Div)this.getFellow("divHREFinalizadas");
		divMEEmitidas=(Div)this.getFellow("divMEEmitidas");
//		rdLlegada=(Radio)this.getFellow("rdLlegada");
	}
	
	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.view.ui.WndBase#onCreate() 
	 */
	@Override
	public void onCreate() throws Exception {
		/*Carga por defecto salida*/
		rdSalida.setChecked(true);
		/*Carga agencias, solamente terminales*/
		List<String>criteriosOrdenar=new ArrayList<String>();
		criteriosOrdenar.add("denominacion");
		TreeMap<String, Object>criteriosBusqueda=new TreeMap<String, Object>();
		criteriosBusqueda.put("tipoAgencia", new TipoAgencia(Constantes.ID_TIPAGE_TEPSA));
		criteriosBusqueda.put("esTerminal", true);
		criteriosBusqueda.put("estadoRegistro", Constantes.VALUE_ACTIVO);
		List<Agencia>lstAgencia=ServiceLocator.getAgenciaManager().buscarPorX(criteriosBusqueda, criteriosOrdenar);
		UtilData.cargarGenericData(cmbAgencia, true);
		for(Agencia agencia:lstAgencia){
			Comboitem comboitem=new Comboitem();
			comboitem.setLabel(agencia.getDenominacion());
			comboitem.setValue(agencia);
			cmbAgencia.appendChild(comboitem);
		}
		Util.seleccionarValorItemCombo(Agencia.class, cmbAgencia, getAgencia().getId());
		
		
		lblNorServicios.setValue("0");
		dtbxFecha.setValue(new Date());
		buscar();
		cargarBuses(lstProgramacion);
				
		
		/**APLICA PERMISOS COMPLETOS A LOS SIGUIENTES ROLES*/
		/*Valida si es rol SUPER USUARIO*/
		List<Rol>listRolesAcceeso=new ArrayList<Rol>();
		listRolesAcceeso.add(new Rol(Constantes.ID_ROL_SUPER_USUARIO));
		listRolesAcceeso.add(new Rol(Constantes.ID_ROL_ADMIN_COMERCIAL));
		listRolesAcceeso.add(new Rol(Constantes.ID_ROL_ASISTENTE_ADMIN_COMERCIAL));
		listRolesAcceeso.add(new Rol(Constantes.ID_ROL_GERENCIA_COMERCIAL));
		/*Componentes a validar*/
		List<Component>lstComponents=new ArrayList<Component>();
		lstComponents.add(cmbAgencia);
		lstComponents.add(dtbxFecha);
		/*Aplica persimos*/
		accesoControlsByRol(lstComponents, listRolesAcceeso);
		
//		if(getRol().getId().intValue()!=Constantes.ID_ROL_SUPER_USUARIO){
//			cmbAgencia.setDisabled(true);
//			dtbxFecha.setDisabled(true);
//		}
	}
	
	/**
	 * Busca programacion.
	 * @throws Exception 
	 */
	public void buscar() throws Exception{
		Util.limpiarListbox(lstbxSalidas);
		lblNorServicios.setValue("0");
		
//		if(cmbAgencia.getSelectedItem().getValue() instanceof Agencia){
			Parametros parametros=ServiceLocator.getParametrosManager().buscarPorEstadoRegistro(Constantes.VALUE_ACTIVO);
			
//			Agencia agenciaPartida=(Agencia)cmbAgencia.getSelectedItem().getValue();
			Integer  idAgenciaPartida=null;
			if(cmbAgencia.getSelectedItem().getValue() instanceof Agencia)
			   idAgenciaPartida=((Agencia)cmbAgencia.getSelectedItem().getValue()).getId();
			String fecha=Constantes.FORMAT_DATE.format(dtbxFecha.getValue());
			Boolean isSalida=rdSalida.isChecked()?true:false;
			String codigoBus=null;
			if(cmbBus.getSelectedIndex()>0 && cmbBus.getSelectedItem().getValue() instanceof Bus)
				codigoBus=((Bus)cmbBus.getSelectedItem().getValue()).getCodigo();
			/*Varibles que guardan el formato de las celdas de lisbBox*/
			final String styleFont="font-size:11px !important;";
			final String styleLeft="text-align: left;";
			Integer totalHreEmitidas=0,totalHreFinalizadas=0;
			
			/*Busca programacion de servicio*/
			lstProgramacion=ServiceLocator.getProgramacionServiciosManager().buscarProgracion(idAgenciaPartida, fecha,codigoBus,isSalida);
			
			/*Si no hay algun bus seleccionado, estos se vuelven a cargar*/
			if(!(cmbBus.getSelectedIndex()>0))
				cargarBuses(lstProgramacion);
			
			/*Carga los servicios programados*/
			int meEmitidas=0;
			for(ProgramacionServicio programacion: lstProgramacion){
				Itinerario itinerario=programacion.getItinerario();
				Listitem item=new Listitem();
				
				//cuenta las ME emitidas
				if(programacion.isMEE()!=null && programacion.isMEE())
					meEmitidas++;
				
				//Estado de la HRE
				Boolean isHreEmitida=false;
				Boolean isHreFinalizada=false;
				if(programacion.getHojaRuta()!=null){
					isHreEmitida=true;
					totalHreEmitidas++;
					if(programacion.getHojaRuta().getFechaLlegadaReal()!=null && programacion.getHojaRuta().getHoraLlegadaReal()!=null){
						isHreFinalizada=true;
						totalHreFinalizadas++;
					}
				}

				Listcell cell=new Listcell(Constantes.FORMAT_DATE.format(itinerario.getFechaPartida()));
				cell.setStyle(styleFont);
				item.appendChild(cell);
				cell=new Listcell(itinerario.getHoraPartida());
				cell.setStyle(styleFont);
				item.appendChild(cell);
				cell=new Listcell(itinerario.getRuta().toString());
				cell.setStyle(styleLeft);
				item.appendChild(cell);
				cell=new Listcell(itinerario.getServicio().getDenominacion());
				cell.setStyle(styleLeft);
				item.appendChild(cell);
				cell=new Listcell(Constantes.FORMAT_DATE.format(itinerario.getFechaLlegada()));
				cell.setStyle(styleFont);
				item.appendChild(cell);
				cell=new Listcell(itinerario.getHoraLlegada());
				cell.setStyle(styleFont);
				item.appendChild(cell);
				cell=new Listcell(itinerario.getBus().getCodigo());
				cell.setStyle("font-size:11px !important;");
				item.appendChild(cell);
				cell=new Listcell(isHreEmitida?programacion.getHojaRuta().toString():"--- --------------");
				cell.setStyle("font-size:11px !important;");
				item.appendChild(cell);
				
				cell=new Listcell();
				/*SALIDA*/
				if(rdSalida.isChecked()){
					Toolbarbutton btnGenerarME=new Toolbarbutton();
					/**
					 * Cuando la hoja de ruta aún no ha sido emitida 
					 */
					if(!(isHreEmitida)){
						Toolbarbutton btnGenerarHRE=new Toolbarbutton();
						btnGenerarHRE.setImage("/resources/mp_generarhre.png");
						btnGenerarHRE.setTooltiptext("Generar Hoja de Ruta Electrónica.");
						btnGenerarHRE.setId(programacion.getId().toString());
						btnGenerarHRE.setAttribute(ATTRIBUTE_HORA_PARTIDA, itinerario.getHoraPartida());
						btnGenerarHRE.setAutodisable("self");
						cell.appendChild(btnGenerarHRE);
						item.appendChild(cell);
						/****HABILITA/INABILITA LA EMISION DE LA HRE*/
						Integer tipempoEmisonHRE=parametros.getTiempoEmisionHRE();/*Parametro que determina el tiempo antes de la hora de salida en que debe habilitarge la emision */
						//Da formato a la fecha y hora de partida
						String fechaPartida=Constantes.FORMAT_DATE.format(programacion.getItinerario().getFechaPartida());
						String horaPartida=programacion.getItinerario().getHoraPartida();
						Date dfechaHoraPartida=Constantes.FORMAT_DATE.parse(fechaPartida);
						dfechaHoraPartida.setHours(Integer.valueOf(horaPartida.split(":")[0]));
						dfechaHoraPartida.setMinutes(Integer.valueOf(horaPartida.split(":")[1]));
						dfechaHoraPartida.setSeconds(0);
						Date dFechaHoraActual=new Date();
						dFechaHoraActual.setTime(dFechaHoraActual.getTime()+(Constantes.MILISEGUNDOS_X_MINUTO*tipempoEmisonHRE));
						//Valida la fecha y hora de partida. 
						if(!(dFechaHoraActual.getTime()>=dfechaHoraPartida.getTime())){
							btnGenerarHRE.setImage("/resources/mp_generarhreDisabled.png");
							btnGenerarHRE.setTooltiptext("Se habilitará "+tipempoEmisonHRE.toString()+" Minutos antes de la hora de partida del Servicio.");
							btnGenerarHRE.setDisabled(true);					
						}
						/*
						 * Evento del ON_CLICK
						 */
						btnGenerarHRE.addEventListener(Events.ON_CLICK, new EventListener<Event>() {
							@Override
							public void onEvent(final Event event) throws Exception {
							   try {
								   String horaPartida=String.valueOf(event.getTarget().getAttribute(ATTRIBUTE_HORA_PARTIDA));
								   ProgramacionServicio programacionServicio=ServiceLocator.getProgramacionServiciosManager().buscarPorId(Long.valueOf(event.getTarget().getId()));
								   Itinerario itinerario=programacionServicio.getItinerario();
								   itinerario.setHoraPartida(horaPartida);
								   programacionServicio.setItinerario(itinerario);
								   openWindowsGenerarHRE(programacionServicio);
									
							    } catch (Exception e) {
								   DlgMessage.information(e.getMessage());
							    }					
							}
						});
						
						/*Deshabilita la emision del Manifiesto electrónico*/
						btnGenerarME.setImage("/resources/mp_generarmeDisabled.png");
						btnGenerarME.setDisabled(true);
						btnGenerarME.setTooltiptext("Se habilitará una vez emitida la HRE.");
						cell=new Listcell();
						cell.appendChild(btnGenerarME);
						item.appendChild(cell);
					}else{
						
						/**CUANDO LA HOJA DE RUTA YA ESTA EMITIDA*/
						Hbox hbox=new Hbox();
						hbox.setAlign("center");
						/*Impresión de hoja de ruta*/
						Toolbarbutton btnPrintHRE =new Toolbarbutton();
						btnPrintHRE.setId(programacion.getId().toString());
						btnPrintHRE.setAutodisable("self");
						btnPrintHRE.setImage("/resources/mp_imprimir.png");
						btnPrintHRE.setTooltiptext("Imprimir Hoja de Ruta Electrónica.");
						btnPrintHRE.setAttribute(ATTRIBUTE_NRO_HOJARUTA, programacion.getHojaRuta().toString());
						hbox.appendChild(btnPrintHRE);
						/*vista preliminar de la Hoja de ruta*/
						Toolbarbutton btnPrevioPrintHRE =new Toolbarbutton();
						btnPrevioPrintHRE.setId(programacion.getId().toString());
						btnPrevioPrintHRE.setAutodisable("self");
						btnPrevioPrintHRE.setImage("/resources/mp_preliminar.png");
						btnPrevioPrintHRE.setTooltiptext("Vista preliminar de la Hoja de Ruta Electrónica.");
						btnPrevioPrintHRE.setAttribute(ATTRIBUTE_NRO_HOJARUTA, programacion.getHojaRuta().toString());
						hbox.appendChild(btnPrevioPrintHRE);
						
						/*
						 * Evento imprimir HRE
						 */
						btnPrintHRE.addEventListener(Events.ON_CLICK,new EventListener<Event>() {
							@Override
							public void onEvent(Event event) throws Exception {
								// TODO Auto-generated method stub
								try {
									/*Procesa impresión de HRE*/
									procesar(Long.valueOf(event.getTarget().getId()),PROCESO_IMPRIMIR_HRE,String.valueOf(event.getTarget().getAttribute(ATTRIBUTE_NRO_HOJARUTA)),null,null);
									
								} catch (Exception e) {
									e.getStackTrace();
									DlgMessage.error(e.getMessage());
								}
							}
						});
						/*
						 * Evento previsualizar impresion de hoja de ruta
						 */
						btnPrevioPrintHRE.addEventListener(Events.ON_CLICK,new EventListener<Event>() {
							@Override
							public void onEvent(Event event) throws Exception {
								try {
									/*Procesa previsualización de la impresion de la HRE*/
									procesar(Long.valueOf(event.getTarget().getId()),PROCESO_PREVISUALIZA_HRE,String.valueOf(event.getTarget().getAttribute(ATTRIBUTE_NRO_HOJARUTA)),null,null);
									
								} catch (Exception e) {
									e.getStackTrace();
									DlgMessage.error(e.getMessage());
								}
								
							}
						});
						
						/*Anulacion de la hoja de ruta*/
						/*Valida disponibilidad de la anulacion de la hre.*/
						if(   (getRol().getId().intValue()==Constantes.ID_ROL_SUPER_USUARIO || getRol().getId().intValue()==Constantes.ID_ROL_ADMIN_PUNTO_VENTA ||
							   getRol().getId().intValue()==Constantes.ID_ROL_ADMIN_COMERCIAL || getRol().getId().intValue()==Constantes.ID_ROL_ASISTENTE_ADMIN_COMERCIAL ||
							   getRol().getId().intValue()==Constantes.ID_ROL_GERENCIA_COMERCIAL) 
						   && 
							   programacion.getItinerario().getFechaPartida().getTime()==Constantes.FORMAT_DATE.parse(Constantes.FORMAT_DATE.format(new Date())).getTime()){
							
							final Toolbarbutton btnAnularHre =new Toolbarbutton();
							btnAnularHre.setImage("/resources/mp_anulacion.png");
							btnAnularHre.setTooltiptext("Anular Hoja de Ruta Eletrónica.");
							btnAnularHre.setAutodisable("self");
							btnAnularHre.setAttribute(ATTRIBUTE_NRO_HOJARUTA, programacion.getHojaRuta().toString());
							hbox.appendChild(btnAnularHre);
							
							/*Si es rol ADIM.PUNTO VENTA, valida que sea la agencia en donde se emitio la hre*/
							if(getRol().getId().intValue()==Constantes.ID_ROL_ADMIN_PUNTO_VENTA &&
									programacion.getHojaRuta().getAgenciaSalida().getId().intValue()!=getAgencia().getId().intValue()){
								btnAnularHre.setDisabled(true);
								btnAnularHre.setImage("/resources/mp_anulacionDisabled.png");
							}else{
								/*
								 * Evento anulacion de la hoja de ruta electroncia
								 * */
								btnAnularHre.addEventListener(Events.ON_CLICK,new EventListener<Event>() {
									@Override
									public void onEvent(Event event) throws Exception {
										Messagebox.show(Messages.getString("WndEmisonhre.question.anularHre"), DlgMessage.NOMBREAPLICACION, DlgMessage.BTN_YESNO, Messagebox.QUESTION,DlgMessage.BTN_DEFAULT_NO, new EventListener<Event>() {
											@Override
											public void onEvent(Event e) throws Exception {
												if(e.getName().equals("onYes")){
													try {
														/*Procesa previsualización de la impresion de la HRE*/
														procesar(null,PROCESO_ANULAR_HRE,String.valueOf(btnAnularHre.getAttribute(ATTRIBUTE_NRO_HOJARUTA)),null,null);
														/*Confirma exito del fin del proceso*/
														DlgMessage.information(Messages.getString("WndEmisonhre.information.confirAnulacionHre"));
														/*Refresca lista*/
														buscar();
													} catch (Exception e2) {
														e2.getStackTrace();
														DlgMessage.error(e2.getMessage());
													}
												}
											}
										});
									}
								});
							}
						}	
						cell.appendChild(hbox);
						item.appendChild(cell);
						
						
						/** MANIFIESTO ELECTRONICO ****************/
						if(programacion.isMEE()==null || programacion.isMEE()==false){
							cell=new Listcell();
							btnGenerarME.setAutodisable("self");
							btnGenerarME.setAttribute(ATTRIBUTE_NRO_HOJARUTA, programacion.getHojaRuta().toString());
							btnGenerarME.setImage("/resources/mp_generarme.png");
							btnGenerarME.setTooltiptext("Generar Manifiesto Electrónico.");
							btnGenerarME.setId(programacion.getId().toString());
							cell.appendChild(btnGenerarME);
							item.appendChild(cell);
							/*
							 * Evento Generar ME
							 */
							btnGenerarME.addEventListener(Events.ON_CLICK, new EventListener<Event>() {
								@Override
								public void onEvent(final Event event) throws Exception {
									Messagebox.show(Messages.getString("WndEmisonhre.question.generarMe"), DlgMessage.NOMBREAPLICACION, DlgMessage.BTN_YESNO, Messagebox.QUESTION,DlgMessage.BTN_DEFAULT_NO, new EventListener<Event>() {
										@Override
										public void onEvent(Event e) throws Exception {
											if(e.getName().equals("onYes")){
												try{
													codigoME="";
													/*Porcesa Manifiesto electronico*/
													procesar(Long.valueOf(event.getTarget().getId()),PROCESO_GENERAR_ME,String.valueOf(event.getTarget().getAttribute(ATTRIBUTE_NRO_HOJARUTA)),null,null);
													/*Confirma exito del fin del proceso*/
													DlgMessage.information(Messages.getString("WndEmisonhre.information.confirmaGenerarMe")+" \n Código retornado : "+ codigoME);
													/*Refresca lista*/
													buscar();
												}catch (WSMTCExcepcion wsex){
													DlgMessage.information(wsex.getMessage());
												}catch (WebServiceException wse){
													DlgMessage.error("No se pudo establecer conexión con el Servicio Web del MTC.");
												}catch(Exception exc){
													if(exc.getMessage()!=null)
														DlgMessage.error(exc.getMessage());
													else
														DlgMessage.error("Ha ocurrido un error al genarar el ME, por favor vuelva a intentarlo.");
												}
											}
										}
									});						
								}
							});
						}else{
							item.appendChild(new Listcell());
						}
					}
					lshdHRE.setLabel("EMISIÓN HRE");
					lshdHRE.setTooltiptext("Emision/Impresión de HRE.");
					lshdME.setVisible(true);
					divHREEmitidas.setVisible(true);
					divHREFinalizadas.setVisible(false);
					divMEEmitidas.setVisible(true);
				}else{
					/*LLEGADA*/
					/*Finalizar*/
					if(!(isHreFinalizada)){
						Toolbarbutton btnFinalizarHRE=new Toolbarbutton();
						btnFinalizarHRE.setImage("/resources/mp_acceptDisabled.png");
						btnFinalizarHRE.setDisabled(true);
						btnFinalizarHRE.setAutodisable("self");
						cell.appendChild(btnFinalizarHRE);
						item.appendChild(cell);
						/*Valida que tenga hoja de ruta*/
						if(programacion.getHojaRuta()!=null){
							/*Habilita/inhabilita la finalizacion de la hoja de ruta (Se puede finalizar 3 horas antes de la fecha de llegada.)*/
							Integer TIEMPO_MINIMO_ANTES_LLEGE_BUS=3; //Indica que se puede finalizar 3 horas entes de la fecha de llegada del servicio
							String fechaLlegada=Constantes.FORMAT_DATE.format(programacion.getItinerario().getFechaLlegada());
							String horaLlegada=programacion.getItinerario().getHoraLlegada();
							Date dfechaHoraLlegada=Constantes.FORMAT_DATE.parse(fechaLlegada);
							dfechaHoraLlegada.setHours(Integer.valueOf(horaLlegada.split(":")[0]));
							dfechaHoraLlegada.setMinutes(Integer.valueOf(horaLlegada.split(":")[1]));
							dfechaHoraLlegada.setSeconds(0);
							Date dFechaHoraActual=new Date();
							dFechaHoraActual.setTime(dFechaHoraActual.getTime()+(Constantes.MILISEGUNDOS_X_HORA*TIEMPO_MINIMO_ANTES_LLEGE_BUS));
							//Aplica validacion
							if(dFechaHoraActual.getTime()>=dfechaHoraLlegada.getTime()){
								btnFinalizarHRE.setImage("/resources/mp_acceptEnabled.png");
								btnFinalizarHRE.setDisabled(false);
								btnFinalizarHRE.setTooltiptext("Finalizar HRE");
							}
							btnFinalizarHRE.setId(programacion.getId().toString());
							btnFinalizarHRE.setId(programacion.getId().toString());
							btnFinalizarHRE.setAttribute(programacion.getId().toString(), programacion);

							/*
							 * Evento Finalizar HRE
							 */
							btnFinalizarHRE.addEventListener(Events.ON_CLICK, new EventListener<Event>() {
								@Override
								public void onEvent(Event arg0) throws Exception {
									openWindowsFinalizarHRE((ProgramacionServicio)arg0.getTarget().getAttribute(arg0.getTarget().getId()));
								}
							});
						}
					}else{
						item.appendChild(new Listcell());
					}
					lshdHRE.setLabel("FINALIZAR HRE");
					lshdHRE.setTooltiptext("Finalizar HRE Emitidas");
					lshdME.setVisible(false);
					divHREEmitidas.setVisible(false);
					divHREFinalizadas.setVisible(true);
					divMEEmitidas.setVisible(false);
				}
								
				item.setValue(programacion);
				lstbxSalidas.appendChild(item);		
			}
			lblNorServicios.setValue(String.valueOf(lstbxSalidas.getItems().size()));
			lblServicioAsociadosHRE.setValue(totalHreEmitidas.toString());
			lblServicioFinalizadosHRE.setValue(totalHreFinalizadas.toString());
			lblManifiestosEmitidos.setValue(String.valueOf(meEmitidas));
			if(lstbxSalidas.getItems().size()>0)
				lstbxSalidas.setSelectedIndex(0);
//		}
	}
	
	/**
	 * Genera la hoja de ruta Electronica.
	 * @param idProgramacion 	: Identificador de la programacion.
	 * @param proceso			: tipo de proceso a ejecutar. 
	 * @param numeroHRE			: Numero de hoja de ruta. (para el caso de la impresion y previsualización de la hoja de ruta, asi como para la generacion del ME)
	 * @param pilotoIniciaConduccion : Piloto que inicia la conducción. (Solo la generación de la Hoja de Ruta Electronica)
	 * @throws Exception
	 */
	private void procesar(Long idProgramacion,int proceso, String numeroHRE, Personal pilotoIniciaConduccion, String horaPartida)throws Exception,WSMTCExcepcion{
		try{
			ProgramacionServicio programacion=null;
			if(idProgramacion!=null)
				programacion=ServiceLocator.getProgramacionServiciosManager().buscarPorId(idProgramacion);
						
			if(proceso==PROCESO_GENERAR_HRE || proceso==PROCESO_GENERAR_ME){
				/**Busca pasajeros para la emision del Manifiesto electronico*/
				final List<VentaPasaje>listaPasajeros=ServiceLocator.getVentaPasajesManager().buscarPasajerosByME(programacion.getItinerario().getId());
				if(listaPasajeros.size()==0){
					throw new WSMTCExcepcion(Messages.getString("WndEmisonme.information.noManifiesto"));
				}
				
				/**Proceso que genera la HRE y el MUE - 12/01/2015*/
				generarHRE(programacion,pilotoIniciaConduccion,horaPartida,listaPasajeros);
//			if(proceso==PROCESO_GENERAR_HRE ){
//				//Genera HRE.
//				//==================================
//				generarHRE(programacion,pilotoIniciaConduccion,horaPartida);
//			}else if(proceso==PROCESO_GENERAR_ME){
				//Genera Manifiesto electronico (ME)
				//=============================
				
//				/**Busca pasajeros para la emision del Manifiesto electronico*/
//				final List<VentaPasaje>listaPasajeros=ServiceLocator.getVentaPasajesManager().buscarPasajerosByME(programacion.getItinerario().getId());
//				if(listaPasajeros.size()==0){
//					throw new WSMTCExcepcion(Messages.getString("WndEmisonme.information.noManifiesto"));
//				}
				/*Genera el ME*/
//				HRE hre=ServiceLocator.getHREManager().buscarPorId(numeroHRE);
//				generarME(hre, listaPasajeros);
			}else if(proceso==PROCESO_IMPRIMIR_HRE){
				//Imprime HRE.
				//======================================
				printHre(numeroHRE);
			}else if (proceso==PROCESO_PREVISUALIZA_HRE){
				//Previsualiza HRE.
				//======================================
				previoPrintHRE(numeroHRE);
			}else if (proceso==PROCESO_ANULAR_HRE){
				//Anula una HRE.
				//======================================
				anularHRE(numeroHRE);
			}
		}catch (WebServiceException wse){
			wse.printStackTrace();
			throw new WebServiceException(wse.getMessage());
		}catch (WSMTCExcepcion wsexc){
			throw new WSMTCExcepcion(wsexc.getMessage());
		}catch (Exception e) {
			e.printStackTrace();
			throw new Exception(e.getMessage());
		}
	}
	
//	/**
//	 * Genera la hre
//	 * @param idProgramacion
//	 * @throws Exception
//	 */
//	private void generarHRE(ProgramacionServicio programacion, Personal pilotoIniciaConduccion, String horaPartida)throws WSMTCExcepcion, Exception{
////		ProgramacionServicio programacion=ServiceLocator.getProgramacionServiciosManager().buscarPorId(programacions.getId());
//		Itinerario itinerario=programacion.getItinerario();
//		
//		/** Busca detalle de la ruta registrada en el MTC*/
//		MTCDetalleRuta mtcDetalleRuta=ServiceLocator.getMTCDetalleRutaManager().buscarPorIdRuta(itinerario.getRuta().getId());
//		if(mtcDetalleRuta==null)
//			throw new WSMTCExcepcion(Messages.getString("WndEmisonhre.information.noRuta"));
//		
//		/**Busca terminales origen y destino registrados en el MTC*/
//		if(!(cmbAgencia.getSelectedItem().getValue() instanceof Agencia))
//			throw new WSMTCExcepcion(Messages.getString("WndEmisonhre.information.noSelectAgencia"));
//		
////		MTCDireccionTerminal mtcTerminalSalida=ServiceLocator.getMTCDireccionTerminalManager().buscarPorIdAgencia(itinerario.getAgenciaPartida().getId());
//		MTCDireccionTerminal mtcTerminalSalida=ServiceLocator.getMTCDireccionTerminalManager().buscarPorIdAgencia(((Agencia)cmbAgencia.getSelectedItem().getValue()).getId());
//		MTCDireccionTerminal mtcTerminalLlegada=ServiceLocator.getMTCDireccionTerminalManager().buscarPorIdAgencia(itinerario.getAgenciaLlegada().getId());
//		if(mtcTerminalSalida==null)
//			throw new WSMTCExcepcion(Messages.getString("WndEmisonhre.information.noTerminalPartida"));
//		else if (mtcTerminalLlegada==null)
//			throw new WSMTCExcepcion(Messages.getString("WndEmisonhre.information.noTerminalLlegada"));
//		
////		Viaje viaje=new Viaje();
////		viaje.setNroRuta(mtcDetalleRuta.getMtcRuta().getCodigo());
////		viaje.setTerSalida(mtcTerminalSalida.getCodigo());
////		viaje.setFecSalida(Constantes.FORMAT_DATE.format(itinerario.getFechaPartida()));
////		viaje.setHorSalida(itinerario.getHoraPartida());
////		viaje.setHorSalida(horaPartida);
////		viaje.setTerLlegada(mtcTerminalLlegada.getCodigo());
////		viaje.setHorEstLlegada(itinerario.getHoraLlegada());
////		viaje.setFecEstLlegada(Constantes.FORMAT_DATE.format(itinerario.getFechaLlegada()));
//		
//		
//		/**Llena al array con el turno de conduccion de los conductores*/
//		Personal piloto=ServiceLocator.getPersonalManager().buscarPorId(pilotoIniciaConduccion.getId()); //programacion.getPiloto();
//		Personal copiloto=programacion.getCopiloto();
//		if(pilotoIniciaConduccion.getId().intValue()==programacion.getCopiloto().getId().longValue())
//			copiloto=programacion.getPiloto();
//		
//		Personal copilotoAux=programacion.getCopilotoAuxiliar()!=null?programacion.getCopilotoAuxiliar():null;
//		int cantPilotos=2; //Por defectos piloto + copiloto
//		if(programacion.getCopilotoAuxiliar()!=null)//Valida la programacion de un copiloto auxiliar.
//			cantPilotos+=+1;
////		String duracion=duracionViaje(itinerario.getFechaPartida(),itinerario.getHoraPartida(),itinerario.getFechaLlegada(),itinerario.getHoraLlegada());//Obtiene la duracion del viaje
//		String duracion=duracionViaje(itinerario.getFechaPartida(),horaPartida,itinerario.getFechaLlegada(),itinerario.getHoraLlegada());//Obtiene la duracion del viaje
//		int totalHorasViaje=Integer.valueOf(duracion.split(":")[0].toString());
//		
//		Date fechahoraLlegada=itinerario.getFechaLlegada();
//		fechahoraLlegada.setHours(Integer.valueOf(itinerario.getHoraLlegada().split(":")[0].toString()));
//		fechahoraLlegada.setMinutes(Integer.valueOf(itinerario.getHoraLlegada().split(":")[1].toString()));
//		
//		
//		Date fechaHoraInicio=toIntegrateDateTime(itinerario.getFechaPartida(),horaPartida);
//		ArrayOfHConductor arrayOfHConductor=new ArrayOfHConductor();
//		int p=1;
//		
//		/*Validando la hora de inicio de conduccion para determinar si es nocturna*/
//		int horasInica=WSMTC.HORAS_CONDUCCION;
//		int horasConduccion=WSMTC.HORAS_CONDUCCION;
//		if( (fechaHoraInicio.getHours()==WSMTC.HORA_INICIO_NORTURNO && fechaHoraInicio.getMinutes()>=WSMTC.MINUTO_INICIO_NORTURNO) ||
//			(fechaHoraInicio.getHours()>WSMTC.HORA_INICIO_NORTURNO)
//		  ){
//			horasInica=WSMTC.HORAS_CONDUCCION_NOCTURNA;
//			horasConduccion=WSMTC.HORAS_CONDUCCION_NOCTURNA;
//		}
//		
//		Date fechaHoraTermino=new Date();
//		/*Genera el turno de conduccion de los conductores, segun las horas establecidas en el parametro */
//		for(int h=horasInica; h<=totalHorasViaje;){
//			p++;
//			Boolean isFinConduccionCopiloto=false; //Indica si el copiloto a finalizado su turno de conduccion
//			Personal conductor=null;
//			
//			/*Determina el numero de horas de conduccion */
//			fechaHoraTermino=getFechaHoraTermino(fechaHoraInicio,horasConduccion);
//			if(     
//				(fechaHoraInicio.getHours()==WSMTC.HORA_INICIO_NORTURNO && fechaHoraInicio.getMinutes()>=WSMTC.MINUTO_INICIO_NORTURNO) ||
//				(fechaHoraInicio.getHours()>WSMTC.HORA_INICIO_NORTURNO && fechaHoraInicio.getHours()<24) ||
//				(fechaHoraInicio.getHours()>=0 && fechaHoraInicio.getHours()<WSMTC.HORA_FIN_NORTURNO) ||
//			
//				(fechaHoraTermino.getHours()==WSMTC.HORA_INICIO_NORTURNO && fechaHoraTermino.getMinutes()>WSMTC.MINUTO_INICIO_NORTURNO) ||
//			    (fechaHoraTermino.getHours()>WSMTC.HORA_INICIO_NORTURNO && fechaHoraTermino.getHours()<24) ||
//			    (fechaHoraTermino.getHours()>=0 && fechaHoraTermino.getHours()<WSMTC.HORA_FIN_NORTURNO) 
//			  ){
//				
//				horasConduccion=WSMTC.HORAS_CONDUCCION_NOCTURNA;
//			}else{
//				horasConduccion=WSMTC.HORAS_CONDUCCION;
//			}
//			
//			
//			/*Cuando solo van piloto y copiloto*/
//			if(cantPilotos==2){
//				if(p%2==0){ //Inicia piloto
//					conductor=piloto;
//					conductor.setTipoConductor(WSMTC.TIPO_CONDUCTOR_PILOTO);
//				}else{
//					conductor=copiloto;
//					conductor.setTipoConductor(WSMTC.TIPO_CONDUCTOR_COPILOTO);
//				}
//			}else if (cantPilotos==3){//Cuando van piloto, copiloto y copilotoAuxiliar
//				if(p%2==0){
//					if(h==horasConduccion || isFinConduccionCopiloto==false){
//						conductor=piloto;
//						conductor.setTipoConductor(WSMTC.TIPO_CONDUCTOR_PILOTO);
//					}else{
//						conductor=copilotoAux;
//						isFinConduccionCopiloto=false;
//						conductor.setTipoConductor(WSMTC.TIPO_CONDUCTOR_COPILOTO);
//					}
//				}else{
//					conductor=copiloto;
//					isFinConduccionCopiloto=true;
//					conductor.setTipoConductor(WSMTC.TIPO_CONDUCTOR_COPILOTOAUXILIAR);
//				}
//			}
//			/*Calcula la fecha y hora de termino*/
//			fechaHoraTermino=getFechaHoraTermino(fechaHoraInicio,horasConduccion);
//						
//			/*Valida si la fecha hora de termino es mayor o igual a la fecha hora de llegada del servicio*/
//			if(fechaHoraTermino.getTime()>=fechahoraLlegada.getTime())
//				fechaHoraTermino=toIntegrateDateTime(itinerario.getFechaLlegada(),itinerario.getHoraLlegada());
////			if(h==totalHorasViaje)
////				fechaHoraTermino=toIntegrateDateTime(itinerario.getFechaLlegada(),itinerario.getHoraLlegada());
//			/*Setea parametros*/
//			HConductor hConductor=new HConductor();
////			MConductor hConductor=new MConductor();
//			hConductor.setTpoDocumento(WSMTC.getTipoDocumento(conductor.getTipoDocumento().getId(), conductor.getTipoConductor()));
//			hConductor.setPersonal(conductor);
//			hConductor.setNroDocumento(conductor.getNroDocumento());
//			hConductor.setFecInicio(Constantes.FORMAT_DATE.format(fechaHoraInicio));
//			hConductor.setHorInicio(Constantes.FORMAT_TIME.format(fechaHoraInicio));
//			hConductor.setFecTermino(Constantes.FORMAT_DATE.format(fechaHoraTermino));
//			hConductor.setHorTermino(Constantes.FORMAT_TIME.format(fechaHoraTermino));
////			System.out.println("========(" +horasConduccion+ ")==="+String.valueOf(h)+" HORAS================");
////			System.out.println("FECHA INICIO  : "+Constantes.FORMAT_DATE.format(fechaHoraInicio));
////			System.out.println("HORA INICIO   : "+Constantes.FORMAT_TIME.format(fechaHoraInicio));
////			System.out.println("FECHA TERMINO : "+Constantes.FORMAT_DATE.format(fechaHoraTermino));
////			System.out.println("HORA TERMINO  : "+Constantes.FORMAT_TIME.format(fechaHoraTermino));
////			System.out.println("PILOTO  : "+conductor.toString());
//			
//			arrayOfHConductor.getHConductor().add(hConductor);	
//			/*Finaliza cuando haya culminado el viaje*/
////			if(h==totalHorasViaje)
//			if(fechaHoraTermino.getTime()>=fechahoraLlegada.getTime())
//				break;
//			/*Incrementa h en funcion a las horas de construccion */
//			if(
//				(fechaHoraTermino.getHours()==WSMTC.HORA_INICIO_NORTURNO && fechaHoraInicio.getMinutes()>=WSMTC.MINUTO_INICIO_NORTURNO) ||
//				(fechaHoraTermino.getHours()>WSMTC.HORA_INICIO_NORTURNO && fechaHoraTermino.getHours()<24) ||
//			    (fechaHoraTermino.getHours()>=0 && fechaHoraTermino.getHours()<WSMTC.HORA_FIN_NORTURNO)
//			  ){
//				
//				h+=+WSMTC.HORAS_CONDUCCION_NOCTURNA;
//				horasConduccion=WSMTC.HORAS_CONDUCCION_NOCTURNA;
//			}else{
//				h+=+WSMTC.HORAS_CONDUCCION;
//				horasConduccion=WSMTC.HORAS_CONDUCCION;
//			}
//
//			if(h>totalHorasViaje)
//				h=totalHorasViaje;
//			fechaHoraInicio=fechaHoraTermino;
//			
//		}
//		
//		/**Llena parametros a un array la tripulante*/
//		Personal tripulante=programacion.getTripulante();
//		ArrayOfHTripulante arrayOfHTripulante=new ArrayOfHTripulante();
//		HTripulante hTripulante=new HTripulante();
//		hTripulante.setTpoDocumento(WSMTC.getTipoDocumento(tripulante.getTipoDocumento().getId(), WSMTC.TIPO_TRIPULANTE));
//		hTripulante.setPersonal(tripulante);
//		hTripulante.setNroDocumento(tripulante.getNroDocumento());
//		arrayOfHTripulante.getHTripulante().add(hTripulante);
//		
//		/**Pasa parametros a la Hoja de ruta Electronica*/
//		Bus bus=programacion.getBus();
//		HojaRuta hojaRuta=new HojaRuta();
//		hojaRuta.setConductores(arrayOfHConductor);
//		hojaRuta.setTripulantes(arrayOfHTripulante);
//		hojaRuta.setNroRuta(mtcDetalleRuta.getMtcRuta().getCodigo());
//		hojaRuta.setTerSalida(mtcTerminalSalida.getCodigo());
//		hojaRuta.setFecSalida(Constantes.FORMAT_DATE.format(itinerario.getFechaPartida()));
//		hojaRuta.setHorSalida(horaPartida);
//		hojaRuta.setTerLlegada(mtcTerminalLlegada.getCodigo());
//		hojaRuta.setFecEstLlegada(Constantes.FORMAT_DATE.format(itinerario.getFechaLlegada()));
//		hojaRuta.setHorEstLlegada(itinerario.getHoraLlegada());
//		hojaRuta.setNroPlaca(WSMTC.toFormatNroPlaca(bus.getNumeroPlaca()));
//					
//		//===================================================================================================================
//		//IMPACTA EN LA BASE DE DATOS DEL MTC
//		//===================================================================================================================
//		/*Valida si el servicio aún no tiene HRE generada*/
////		HRE hre= ServiceLocator.getHREManager().buscarHREEmitida(Constantes.FORMAT_DATE.format(itinerario.getFechaPartida()), itinerario.getBus().getCodigo());
//		HRE hre= ServiceLocator.getHREManager().buscarHREEmitida(itinerario.getId());
//		if(hre!=null)
//			throw new WSMTCExcepcion(Messages.getString("WndEmisonhre.information.duplicate"));
//		
//		/** Guarda el tripulante - DATOS ENVIADOS SON SOLAMENTE PARA PRUEBA, FALTA DETERMINAR DE DONDE SE VA A OBTENER ESTA IMFORMACION - 10/4/2015*/
//		Tripulante oTripulante =new Tripulante();
//		oTripulante.setApellido(hTripulante.getPersonal().getApellidoPaterno());
//		oTripulante.setNombre(hTripulante.getPersonal().getNombre());
////		oTripulante.setCondicion("1");
////		oTripulante.setDomicilio("LIMA");
////		oTripulante.setFecIng(Constantes.FORMAT_DATE.format(new Date()));
//		oTripulante.setFecNac("01/01/1990");
//		oTripulante.setTpoDocumento(hTripulante.getTpoDocumento());
//		oTripulante.setNroDocumento(hTripulante.getNroDocumento());
//		oTripulante.setSexo(hTripulante.getPersonal().getSexo().getId().intValue()==Constantes.TRUE_VALUE?"F":"M");
//		WSMTC.setTripulante(oTripulante);
//						
//		/**Guarda el Viaje*/
////		WSMTC.setViaje(viaje); Segun Erick(MTC) ya no es necesario.
//		/**Genera la Hoja de Ruta Electronica*/
//		String NroHojaRuta=WSMTC.setHojaRuta(hojaRuta);
//		
//		
//		/*Se asegura que las fechas del itinerario no cambien ya que esto es transaccional*/
//		itinerario.setFechaPartida(Constantes.FORMAT_DATE.parse(Constantes.FORMAT_DATE.format(itinerario.getFechaPartida())));
//		itinerario.setFechaLlegada(Constantes.FORMAT_DATE.parse(Constantes.FORMAT_DATE.format(itinerario.getFechaLlegada())));
//		
//		//===================================================================================================================
//		//IMPACTA EN NUESTA BASE DE DATOS
//		//===================================================================================================================
//		/*Guarda HRE en muestra BD*/
//		NumeroHojaRutaID numeroHojaRutaID=new NumeroHojaRutaID(NroHojaRuta);
//		HRE hojaRutaE=new HRE();
//		hojaRutaE.setNumeroHojaRutaID(numeroHojaRutaID);
////		hojaRutaE.setAgenciaSalida(itinerario.getAgenciaPartida());
//		hojaRutaE.setAgenciaSalida(((Agencia)cmbAgencia.getSelectedItem().getValue()));
//		hojaRutaE.setFechaSalida(itinerario.getFechaPartida());
//		hojaRutaE.setHoraSalida(hojaRuta.getHorSalida());
//		hojaRutaE.setAgenciaLlegada(itinerario.getAgenciaLlegada());
//		hojaRutaE.setFechaEstimacionLlegada(itinerario.getFechaLlegada());
//		hojaRutaE.setHoraEstimacionLlegada(hojaRuta.getHorEstLlegada());
//		hojaRutaE.setMtcCodigoTerminalSalida(mtcTerminalSalida.getCodigo());
//		hojaRutaE.setMtcCodigoTerminalLlegada(mtcTerminalLlegada.getCodigo());
//		hojaRutaE.setMtcCodigoRuta(mtcDetalleRuta.getMtcRuta().getCodigo());
//		hojaRutaE.setMtcRuta(mtcDetalleRuta.getMtcRuta());
//		hojaRutaE.setMtcTerminalSalida(mtcTerminalSalida);
//		hojaRutaE.setMtcTerminalLlegada(mtcTerminalLlegada);
//		hojaRutaE.setNumeroBus(bus.getCodigo());
//		hojaRutaE.setNumeroPlaca(hojaRuta.getNroPlaca());
//		hojaRutaE.setRuta(itinerario.getRuta());
//		hojaRutaE.setItinerario(itinerario);
//		
//		hojaRutaE.setEstadoRegistro(Constantes.VALUE_ACTIVO);
//		UtilData.auditarRegistro(hojaRutaE, getUsuario(), Executions.getCurrent());
//		ServiceLocator.getHREManager().guardar(hojaRutaE);
//		
//		/*Guarda detalle flota hre*/
//		//Conductores
//		for(HConductor hConductor: hojaRuta.getConductores().getHConductor()){
//			DetalleFlotaHRE detalleFlotaHRE=new DetalleFlotaHRE();
//			detalleFlotaHRE.setHre(hojaRutaE);
//			detalleFlotaHRE.setTipoPersonal(hConductor.getPersonal().getTipoPersonal());
//			detalleFlotaHRE.setNumeroDocumento(hConductor.getNroDocumento());
//			detalleFlotaHRE.setPersonal(hConductor.getPersonal());
//			detalleFlotaHRE.setFechaInicioConduccion(Constantes.FORMAT_DATE.parse(hConductor.getFecInicio()));
//			detalleFlotaHRE.setHoraInicioConduccion(String.valueOf(hConductor.getHorInicio()));
//			detalleFlotaHRE.setFechaTerminoConcduccion(Constantes.FORMAT_DATE.parse(hConductor.getFecTermino()));
//			detalleFlotaHRE.setHoraTerminoConduccion(String.valueOf(hConductor.getHorTermino()));
//			//Validamos la longitud del tipo de documento retornado por el MTC
//			String tipodocumento=hConductor.getTpoDocumento();
//			if(tipodocumento.length()>15)
//				tipodocumento=tipodocumento.substring(0,15);
//			detalleFlotaHRE.setMtcTipoDocumentoID(tipodocumento);
//			detalleFlotaHRE.setEstadoRegistro(Constantes.VALUE_ACTIVO);
//			UtilData.auditarRegistro(detalleFlotaHRE, getUsuario(), Executions.getCurrent());
//			ServiceLocator.getDetalleFlotaHREManager().guardar(detalleFlotaHRE);
//		}
//		//Tripulantes
//		for(HTripulante ohTripulante: hojaRuta.getTripulantes().getHTripulante()){
//			DetalleFlotaHRE detalleFlotaHRE=new DetalleFlotaHRE();
//			detalleFlotaHRE.setHre(hojaRutaE);
//			detalleFlotaHRE.setTipoPersonal(ohTripulante.getPersonal().getTipoPersonal());
//			detalleFlotaHRE.setNumeroDocumento(ohTripulante.getNroDocumento());
//			detalleFlotaHRE.setPersonal(ohTripulante.getPersonal());
//			//Validamos la longitud del tipo de documento retornado por el MTC
//			String tipodocumento=ohTripulante.getTpoDocumento();
//			if(tipodocumento.length()>15)
//				tipodocumento=tipodocumento.substring(0,15);
//			detalleFlotaHRE.setMtcTipoDocumentoID(tipodocumento);
//			detalleFlotaHRE.setEstadoRegistro(Constantes.VALUE_ACTIVO);
//			UtilData.auditarRegistro(detalleFlotaHRE, getUsuario(), Executions.getCurrent());
//			ServiceLocator.getDetalleFlotaHREManager().guardar(detalleFlotaHRE);
//		}
//		
//	}
	
	/**
	 * Genera la hre
	 * @param idProgramacion
	 * @throws Exception
	 */
	private void generarHRE(ProgramacionServicio programacion, Personal pilotoIniciaConduccion, String horaPartida,List<VentaPasaje> listaPasajeros)throws WSMTCExcepcion, Exception{
//		ProgramacionServicio programacion=ServiceLocator.getProgramacionServiciosManager().buscarPorId(programacions.getId());
		Itinerario itinerario=programacion.getItinerario();
		
		/** Busca detalle de la ruta registrada en el MTC*/
		MTCDetalleRuta mtcDetalleRuta=ServiceLocator.getMTCDetalleRutaManager().buscarPorIdRuta(itinerario.getRuta().getId());
		if(mtcDetalleRuta==null)
			throw new WSMTCExcepcion(Messages.getString("WndEmisonhre.information.noRuta"));
		
		/**Busca terminales origen y destino registrados en el MTC*/
		if(!(cmbAgencia.getSelectedItem().getValue() instanceof Agencia))
			throw new WSMTCExcepcion(Messages.getString("WndEmisonhre.information.noSelectAgencia"));
		
//		MTCDireccionTerminal mtcTerminalSalida=ServiceLocator.getMTCDireccionTerminalManager().buscarPorIdAgencia(itinerario.getAgenciaPartida().getId());
		MTCDireccionTerminal mtcTerminalSalida=ServiceLocator.getMTCDireccionTerminalManager().buscarPorIdAgencia(((Agencia)cmbAgencia.getSelectedItem().getValue()).getId());
		MTCDireccionTerminal mtcTerminalLlegada=ServiceLocator.getMTCDireccionTerminalManager().buscarPorIdAgencia(itinerario.getAgenciaLlegada().getId());
		if(mtcTerminalSalida==null)
			throw new WSMTCExcepcion(Messages.getString("WndEmisonhre.information.noTerminalPartida"));
		else if (mtcTerminalLlegada==null)
			throw new WSMTCExcepcion(Messages.getString("WndEmisonhre.information.noTerminalLlegada"));
		
//		Viaje viaje=new Viaje();
//		viaje.setNroRuta(mtcDetalleRuta.getMtcRuta().getCodigo());
//		viaje.setTerSalida(mtcTerminalSalida.getCodigo());
//		viaje.setFecSalida(Constantes.FORMAT_DATE.format(itinerario.getFechaPartida()));
//		viaje.setHorSalida(itinerario.getHoraPartida());
//		viaje.setHorSalida(horaPartida);
//		viaje.setTerLlegada(mtcTerminalLlegada.getCodigo());
//		viaje.setHorEstLlegada(itinerario.getHoraLlegada());
//		viaje.setFecEstLlegada(Constantes.FORMAT_DATE.format(itinerario.getFechaLlegada()));
		
		
		/**Llena al array con el turno de conduccion de los conductores*/
		Personal piloto=ServiceLocator.getPersonalManager().buscarPorId(pilotoIniciaConduccion.getId()); //programacion.getPiloto();
		Personal copiloto=programacion.getCopiloto();
		if(pilotoIniciaConduccion.getId().intValue()==programacion.getCopiloto().getId().longValue())
			copiloto=programacion.getPiloto();
		
		Personal copilotoAux=programacion.getCopilotoAuxiliar()!=null?programacion.getCopilotoAuxiliar():null;
		int cantPilotos=2; //Por defectos piloto + copiloto
		if(programacion.getCopilotoAuxiliar()!=null)//Valida la programacion de un copiloto auxiliar.
			cantPilotos+=+1;
//		String duracion=duracionViaje(itinerario.getFechaPartida(),itinerario.getHoraPartida(),itinerario.getFechaLlegada(),itinerario.getHoraLlegada());//Obtiene la duracion del viaje
		String duracion=duracionViaje(itinerario.getFechaPartida(),horaPartida,itinerario.getFechaLlegada(),itinerario.getHoraLlegada());//Obtiene la duracion del viaje
		int totalHorasViaje=Integer.valueOf(duracion.split(":")[0].toString());
		
		Date fechahoraLlegada=itinerario.getFechaLlegada();
		fechahoraLlegada.setHours(Integer.valueOf(itinerario.getHoraLlegada().split(":")[0].toString()));
		fechahoraLlegada.setMinutes(Integer.valueOf(itinerario.getHoraLlegada().split(":")[1].toString()));
		
		
		Date fechaHoraInicio=toIntegrateDateTime(itinerario.getFechaPartida(),horaPartida);
//		ArrayOfHConductor arrayOfHConductor=new ArrayOfHConductor();
		ArrayOfMConductor arrayOfmConductor=new ArrayOfMConductor();
		int p=1;
		
		/*Validando la hora de inicio de conduccion para determinar si es nocturna*/
		int horasInica=WSMTC.HORAS_CONDUCCION;
		int horasConduccion=WSMTC.HORAS_CONDUCCION;
		if( (fechaHoraInicio.getHours()==WSMTC.HORA_INICIO_NORTURNO && fechaHoraInicio.getMinutes()>=WSMTC.MINUTO_INICIO_NORTURNO) ||
			(fechaHoraInicio.getHours()>WSMTC.HORA_INICIO_NORTURNO)
		  ){
			horasInica=WSMTC.HORAS_CONDUCCION_NOCTURNA;
			horasConduccion=WSMTC.HORAS_CONDUCCION_NOCTURNA;
		}
		
		Date fechaHoraTermino=new Date();
		/*Genera el turno de conduccion de los conductores, segun las horas establecidas en el parametro */
		for(int h=horasInica; h<=totalHorasViaje;){
			p++;
			Boolean isFinConduccionCopiloto=false; //Indica si el copiloto a finalizado su turno de conduccion
			Personal conductor=null;
			
			/*Determina el numero de horas de conduccion */
			fechaHoraTermino=getFechaHoraTermino(fechaHoraInicio,horasConduccion);
			if(     
				(fechaHoraInicio.getHours()==WSMTC.HORA_INICIO_NORTURNO && fechaHoraInicio.getMinutes()>=WSMTC.MINUTO_INICIO_NORTURNO) ||
				(fechaHoraInicio.getHours()>WSMTC.HORA_INICIO_NORTURNO && fechaHoraInicio.getHours()<24) ||
				(fechaHoraInicio.getHours()>=0 && fechaHoraInicio.getHours()<WSMTC.HORA_FIN_NORTURNO) ||
			
				(fechaHoraTermino.getHours()==WSMTC.HORA_INICIO_NORTURNO && fechaHoraTermino.getMinutes()>WSMTC.MINUTO_INICIO_NORTURNO) ||
			    (fechaHoraTermino.getHours()>WSMTC.HORA_INICIO_NORTURNO && fechaHoraTermino.getHours()<24) ||
			    (fechaHoraTermino.getHours()>=0 && fechaHoraTermino.getHours()<WSMTC.HORA_FIN_NORTURNO) 
			  ){
				
				horasConduccion=WSMTC.HORAS_CONDUCCION_NOCTURNA;
			}else{
				horasConduccion=WSMTC.HORAS_CONDUCCION;
			}
			
			
			/*Cuando solo van piloto y copiloto*/
			if(cantPilotos==2){
				if(p%2==0){ //Inicia piloto
					conductor=piloto;
					conductor.setTipoConductor(WSMTC.TIPO_CONDUCTOR_PILOTO);
				}else{
					conductor=copiloto;
					conductor.setTipoConductor(WSMTC.TIPO_CONDUCTOR_COPILOTO);
				}
			}else if (cantPilotos==3){//Cuando van piloto, copiloto y copilotoAuxiliar
				if(p%2==0){
					if(h==horasConduccion || isFinConduccionCopiloto==false){
						conductor=piloto;
						conductor.setTipoConductor(WSMTC.TIPO_CONDUCTOR_PILOTO);
					}else{
						conductor=copilotoAux;
						isFinConduccionCopiloto=false;
						conductor.setTipoConductor(WSMTC.TIPO_CONDUCTOR_COPILOTO);
					}
				}else{
					conductor=copiloto;
					isFinConduccionCopiloto=true;
					conductor.setTipoConductor(WSMTC.TIPO_CONDUCTOR_COPILOTOAUXILIAR);
				}
			}
			/*Calcula la fecha y hora de termino*/
			fechaHoraTermino=getFechaHoraTermino(fechaHoraInicio,horasConduccion);
						
			/*Valida si la fecha hora de termino es mayor o igual a la fecha hora de llegada del servicio*/
			if(fechaHoraTermino.getTime()>=fechahoraLlegada.getTime())
				fechaHoraTermino=toIntegrateDateTime(itinerario.getFechaLlegada(),itinerario.getHoraLlegada());
//			if(h==totalHorasViaje)
//				fechaHoraTermino=toIntegrateDateTime(itinerario.getFechaLlegada(),itinerario.getHoraLlegada());
			/*Setea parametros*/
			MConductor hConductor=new MConductor();
			hConductor.setTpoDoc(WSMTC.getTipoDocumento(conductor.getTipoDocumento().getId(), conductor.getTipoConductor()));
			hConductor.setPersonal(conductor);
			hConductor.setNroDoc(conductor.getNroDocumento());
			hConductor.setFecInicio(Constantes.FORMAT_DATE.format(fechaHoraInicio));
			hConductor.setHorInicio(Constantes.FORMAT_TIME.format(fechaHoraInicio));
			hConductor.setFecTermino(Constantes.FORMAT_DATE.format(fechaHoraTermino));
			hConductor.setHorTermino(Constantes.FORMAT_TIME.format(fechaHoraTermino));
//			System.out.println("========(" +horasConduccion+ ")==="+String.valueOf(h)+" HORAS================");
//			System.out.println("FECHA INICIO  : "+Constantes.FORMAT_DATE.format(fechaHoraInicio));
//			System.out.println("HORA INICIO   : "+Constantes.FORMAT_TIME.format(fechaHoraInicio));
//			System.out.println("FECHA TERMINO : "+Constantes.FORMAT_DATE.format(fechaHoraTermino));
//			System.out.println("HORA TERMINO  : "+Constantes.FORMAT_TIME.format(fechaHoraTermino));
//			System.out.println("PILOTO  : "+conductor.toString());
			
			arrayOfmConductor.getMConductor().add(hConductor);	
			/*Finaliza cuando haya culminado el viaje*/
//			if(h==totalHorasViaje)
			if(fechaHoraTermino.getTime()>=fechahoraLlegada.getTime())
				break;
			/*Incrementa h en funcion a las horas de construccion */
			if(
				(fechaHoraTermino.getHours()==WSMTC.HORA_INICIO_NORTURNO && fechaHoraInicio.getMinutes()>=WSMTC.MINUTO_INICIO_NORTURNO) ||
				(fechaHoraTermino.getHours()>WSMTC.HORA_INICIO_NORTURNO && fechaHoraTermino.getHours()<24) ||
			    (fechaHoraTermino.getHours()>=0 && fechaHoraTermino.getHours()<WSMTC.HORA_FIN_NORTURNO)
			  ){
				
				h+=+WSMTC.HORAS_CONDUCCION_NOCTURNA;
				horasConduccion=WSMTC.HORAS_CONDUCCION_NOCTURNA;
			}else{
				h+=+WSMTC.HORAS_CONDUCCION;
				horasConduccion=WSMTC.HORAS_CONDUCCION;
			}

			if(h>totalHorasViaje)
				h=totalHorasViaje;
			fechaHoraInicio=fechaHoraTermino;
			
		}
		
		/**Llena parametros a un array la tripulante*/
		Personal tripulante=programacion.getTripulante();
		ArrayOfMTripulante arrayOfMTripulante=new ArrayOfMTripulante();
		MTripulante hTripulante=new MTripulante();
		hTripulante.setTpoDoc(WSMTC.getTipoDocumento(tripulante.getTipoDocumento().getId(), WSMTC.TIPO_TRIPULANTE));
		hTripulante.setPersonal(tripulante);
		hTripulante.setNroDoc(tripulante.getNroDocumento());
		arrayOfMTripulante.getMTripulante().add(hTripulante);
		
		
		/**Setea Pasajeros*/
//		long manifiestoId=0;
		ArrayOfMPasajero arrayOfMPasajero=new ArrayOfMPasajero();
		for(VentaPasaje ventaPasaje:listaPasajeros){
			Pasajero pasajero=ventaPasaje.getPasajero();
//			manifiestoId=ventaPasaje.getManifiesto().getId();
			
			MPasajero mPasajero=new MPasajero();
			mPasajero.setTpoDoc(pasajero.getTipoDocumento().getNombreCorto());
			mPasajero.setNroDoc(pasajero.getNumeroDocumento());
			mPasajero.setNombre(pasajero.getNombre());
			mPasajero.setPaterno(pasajero.getApellidoPaterno());
			mPasajero.setMaterno(pasajero.getApellidoMaterno());
			mPasajero.setSerBol(ventaPasaje.getNumeroBoleto().split("-")[0]);
			mPasajero.setNumBol(ventaPasaje.getNumeroBoleto().split("-")[1]);
			mPasajero.setMtoBol(Util.toNumberFormat(ventaPasaje.getImportePagado(), 2));
			mPasajero.setAsiBol(ventaPasaje.getNumeroAsiento().toString());
								
			arrayOfMPasajero.getMPasajero().add(mPasajero);
		}
		
		/**Pasa parametros a la Hoja de ruta Electronica*/
		Bus bus=programacion.getBus();
		/** Setea Manifiesto*/
		Manifiesto manifiesto=new Manifiesto();
		manifiesto.setConductores(arrayOfmConductor);
		manifiesto.setTripulantes(arrayOfMTripulante);
		manifiesto.setNroRuta(mtcDetalleRuta.getMtcRuta().getCodigo());//.getNumeroHojaRutaID().getIdNumeroHojaRuta()
		manifiesto.setTerSalida(mtcTerminalSalida.getCodigo());
		manifiesto.setFecSalida(Constantes.FORMAT_DATE.format(itinerario.getFechaPartida()));
		manifiesto.setHorSalida(horaPartida);
		manifiesto.setTerLlegada(mtcTerminalLlegada.getCodigo());
		manifiesto.setFecEstLlegada(Constantes.FORMAT_DATE.format(itinerario.getFechaLlegada()));
		manifiesto.setHorEstLlegada(itinerario.getHoraLlegada());
		manifiesto.setNroPlaca(WSMTC.toFormatNroPlaca(bus.getNumeroPlaca()));
		manifiesto.setPasajeros(arrayOfMPasajero);
		
		
		//===================================================================================================================
		//IMPACTA EN LA BASE DE DATOS DEL MTC
		//===================================================================================================================
		/*Valida si el servicio aún no tiene HRE generada*/
//		HRE hre= ServiceLocator.getHREManager().buscarHREEmitida(Constantes.FORMAT_DATE.format(itinerario.getFechaPartida()), itinerario.getBus().getCodigo());
		HRE hre= ServiceLocator.getHREManager().buscarHREEmitida(itinerario.getId());
		if(hre!=null)
			throw new WSMTCExcepcion(Messages.getString("WndEmisonhre.information.duplicate"));
		
		/** Guarda el tripulante - DATOS ENVIADOS SON SOLAMENTE PARA PRUEBA, FALTA DETERMINAR DE DONDE SE VA A OBTENER ESTA IMFORMACION - 10/4/2015*/
		Tripulante oTripulante =new Tripulante();
		oTripulante.setApellido(hTripulante.getPersonal().getApellidoPaterno());
		oTripulante.setNombre(hTripulante.getPersonal().getNombre());
//		oTripulante.setCondicion("1");
//		oTripulante.setDomicilio("LIMA");
//		oTripulante.setFecIng(Constantes.FORMAT_DATE.format(new Date()));
		oTripulante.setFecNac("01/01/1990");
		oTripulante.setTpoDocumento(hTripulante.getTpoDoc());
		oTripulante.setNroDocumento(hTripulante.getNroDoc());
		oTripulante.setSexo(hTripulante.getPersonal().getSexo().getId().intValue()==Constantes.TRUE_VALUE?"F":"M");
		WSMTC.setTripulante(oTripulante);
		
		/**Genera la HRE y el MUE*/
		codigoME=WSMTC.setManifiesto(manifiesto); //Impacta el ME			
		
		/*Actualiza el manifiesto en nuestra BD*/
//		com.tepsa.sisvyr.model.bean.Manifiesto manifiestoTepsa=ServiceLocator.getManifiestoManager().buscarPorId(manifiestoId);
//		manifiestoTepsa.setMee(Constantes.TRUE_VALUE);
//		UtilData.auditarRegistro(manifiestoTepsa,true, getUsuario(),Executions.getCurrent());
//		ServiceLocator.getManifiestoManager().actualizar(manifiestoTepsa);
		
		
		/*Se asegura que las fechas del itinerario no cambien ya que esto es transaccional*/
		itinerario.setFechaPartida(Constantes.FORMAT_DATE.parse(Constantes.FORMAT_DATE.format(itinerario.getFechaPartida())));
		itinerario.setFechaLlegada(Constantes.FORMAT_DATE.parse(Constantes.FORMAT_DATE.format(itinerario.getFechaLlegada())));
		
		//===================================================================================================================
		//IMPACTA EN NUESTA BASE DE DATOS
		//===================================================================================================================
		/*Guarda HRE en muestra BD*/
		NumeroHojaRutaID numeroHojaRutaID=new NumeroHojaRutaID(codigoME);
		HRE hojaRutaE=new HRE();
		hojaRutaE.setNumeroHojaRutaID(numeroHojaRutaID);
//		hojaRutaE.setAgenciaSalida(itinerario.getAgenciaPartida());
		hojaRutaE.setAgenciaSalida(((Agencia)cmbAgencia.getSelectedItem().getValue()));
		hojaRutaE.setFechaSalida(itinerario.getFechaPartida());
		hojaRutaE.setHoraSalida(manifiesto.getHorSalida());
		hojaRutaE.setAgenciaLlegada(itinerario.getAgenciaLlegada());
		hojaRutaE.setFechaEstimacionLlegada(itinerario.getFechaLlegada());
		hojaRutaE.setHoraEstimacionLlegada(manifiesto.getHorEstLlegada());
		hojaRutaE.setMtcCodigoTerminalSalida(mtcTerminalSalida.getCodigo());
		hojaRutaE.setMtcCodigoTerminalLlegada(mtcTerminalLlegada.getCodigo());
		hojaRutaE.setMtcCodigoRuta(mtcDetalleRuta.getMtcRuta().getCodigo());
		hojaRutaE.setMtcRuta(mtcDetalleRuta.getMtcRuta());
		hojaRutaE.setMtcTerminalSalida(mtcTerminalSalida);
		hojaRutaE.setMtcTerminalLlegada(mtcTerminalLlegada);
		hojaRutaE.setNumeroBus(bus.getCodigo());
		hojaRutaE.setNumeroPlaca(manifiesto.getNroPlaca());
		hojaRutaE.setRuta(itinerario.getRuta());
		hojaRutaE.setItinerario(itinerario);
		
		hojaRutaE.setEstadoRegistro(Constantes.VALUE_ACTIVO);
		UtilData.auditarRegistro(hojaRutaE, getUsuario(), Executions.getCurrent());
		ServiceLocator.getHREManager().guardar(hojaRutaE);
		
		/*Guarda detalle flota hre*/
		//Conductores
		for(MConductor hConductor: manifiesto.getConductores().getMConductor()){
			DetalleFlotaHRE detalleFlotaHRE=new DetalleFlotaHRE();
			detalleFlotaHRE.setHre(hojaRutaE);
			detalleFlotaHRE.setTipoPersonal(hConductor.getPersonal().getTipoPersonal());
			detalleFlotaHRE.setNumeroDocumento(hConductor.getNroDoc());
			detalleFlotaHRE.setPersonal(hConductor.getPersonal());
			detalleFlotaHRE.setFechaInicioConduccion(Constantes.FORMAT_DATE.parse(hConductor.getFecInicio()));
			detalleFlotaHRE.setHoraInicioConduccion(String.valueOf(hConductor.getHorInicio()));
			detalleFlotaHRE.setFechaTerminoConcduccion(Constantes.FORMAT_DATE.parse(hConductor.getFecTermino()));
			detalleFlotaHRE.setHoraTerminoConduccion(String.valueOf(hConductor.getHorTermino()));
			//Validamos la longitud del tipo de documento retornado por el MTC
			String tipodocumento=hConductor.getNroDoc();
			if(tipodocumento.length()>15)
				tipodocumento=tipodocumento.substring(0,15);
			detalleFlotaHRE.setMtcTipoDocumentoID(tipodocumento);
			detalleFlotaHRE.setEstadoRegistro(Constantes.VALUE_ACTIVO);
			UtilData.auditarRegistro(detalleFlotaHRE, getUsuario(), Executions.getCurrent());
			ServiceLocator.getDetalleFlotaHREManager().guardar(detalleFlotaHRE);
		}
		//Tripulantes
		for(MTripulante ohTripulante: manifiesto.getTripulantes().getMTripulante()){
			DetalleFlotaHRE detalleFlotaHRE=new DetalleFlotaHRE();
			detalleFlotaHRE.setHre(hojaRutaE);
			detalleFlotaHRE.setTipoPersonal(ohTripulante.getPersonal().getTipoPersonal());
			detalleFlotaHRE.setNumeroDocumento(ohTripulante.getNroDoc());
			detalleFlotaHRE.setPersonal(ohTripulante.getPersonal());
			//Validamos la longitud del tipo de documento retornado por el MTC
			String tipodocumento=ohTripulante.getTpoDoc();
			if(tipodocumento.length()>15)
				tipodocumento=tipodocumento.substring(0,15);
			detalleFlotaHRE.setMtcTipoDocumentoID(tipodocumento);
			detalleFlotaHRE.setEstadoRegistro(Constantes.VALUE_ACTIVO);
			UtilData.auditarRegistro(detalleFlotaHRE, getUsuario(), Executions.getCurrent());
			ServiceLocator.getDetalleFlotaHREManager().guardar(detalleFlotaHRE);
		}
		
	}
//	/**
//	 * Genera el Manifiesto electronico (ME)
//	 * @param viaje			: Objeto viaje.
//	 * @param hojaRuta		: Objeto hojaRota.
//	 * @param listaPasajeros	: lista de pasajeros.
//	 * @throws Exception
//	 */
//	private void generarME(HRE hre, List<VentaPasaje> listaPasajeros)throws Exception{
//		
////		if(Constantes.PROXY!=null){
////			String puerto="";
////			String proxy=Constantes.PROXY;
////			String ipProxy=proxy.split(":")[0].toString();
////			if(proxy.split(":").length==2)
////				puerto=proxy.split(":")[1].toString();
////			
////			System.setProperty("http.proxyHost", ipProxy);
////			System.setProperty("http.proxyPort", puerto);
////		}
//		
////		WSServiciosHR wsServiciosHR=new WSServiciosHR();
////		/**Clase de seguridad tempora (hasta que se pase a produccion)*/
////		Seguridad seguridad=new Seguridad();
////		seguridad.setRuc("20502324927");
////		seguridad.setUsuario("059979");
////		seguridad.setClave("123456");
////		seguridad.setPartida("000530PNR");
//		
//		
//		/*Busca el detalle de la hoja de ruta generada*/
//		TreeMap<String, Object> criteriosBusqueda=new TreeMap<String, Object>();
//		criteriosBusqueda.put("hre", hre);
//		criteriosBusqueda.put("estadoRegistro", Constantes.VALUE_ACTIVO);
//		List<String>criteriosOrden=new ArrayList<String>();
//		criteriosOrden.add("id");
//		List<DetalleFlotaHRE>detalleHRE=ServiceLocator.getDetalleFlotaHREManager().buscarPorX(criteriosBusqueda, criteriosOrden);
//		
//		/*Setea la tripulantes y pilotos*/
//		ArrayOfMTripulante arrayOfMTripulante=new ArrayOfMTripulante();
//		ArrayOfMConductor arrayOfMConductor=new ArrayOfMConductor();
//		if(detalleHRE.size()>0){
//			for(DetalleFlotaHRE detalleFlotaHRE:detalleHRE){
//				if(detalleFlotaHRE.getTipoPersonal().getId().intValue()==Constantes.ID_TIPPER_PILOTO_COPILOTO){
//					MConductor mConductor=new MConductor();
//					mConductor.setTpoDoc(WSMTC.getTipoDocumento(detalleFlotaHRE.getPersonal().getTipoDocumento().getId(), Constantes.ID_TIPPER_PILOTO_COPILOTO));
//					mConductor.setNroDoc(detalleFlotaHRE.getPersonal().getNroDocumento());
//					mConductor.setFecInicio(Constantes.FORMAT_DATE.format(detalleFlotaHRE.getFechaInicioConduccion()));
//					mConductor.setHorInicio(detalleFlotaHRE.getHoraInicioConduccion());
//					mConductor.setFecTermino(Constantes.FORMAT_DATE.format(detalleFlotaHRE.getFechaTerminoConcduccion()));
//					mConductor.setHorTermino(detalleFlotaHRE.getHoraTerminoConduccion());
//					arrayOfMConductor.getMConductor().add(mConductor);
//				}else if(detalleFlotaHRE.getTipoPersonal().getId().intValue()==Constantes.ID_TIPPER_TRIPULANTE){
//					MTripulante mTripulante=new MTripulante();
//					mTripulante.setTpoDoc(WSMTC.getTipoDocumento(detalleFlotaHRE.getPersonal().getTipoDocumento().getId(), Constantes.ID_TIPPER_TRIPULANTE));
//					mTripulante.setNroDoc(detalleFlotaHRE.getPersonal().getNroDocumento());
//										
//					arrayOfMTripulante.getMTripulante().add(mTripulante);
//				}
//			}
//		}else
//			throw new Exception(Messages.getString("WndEmisonme.information.noHRE"));				
//		if(arrayOfMTripulante.getMTripulante().size()==0)
//			throw new Exception(Messages.getString("WndEmisonme.information.noTripulante"));
//
//		/**Setea Pasajeros*/
//		long manifiestoId=0;
//		ArrayOfMPasajero arrayOfMPasajero=new ArrayOfMPasajero();
//		for(VentaPasaje ventaPasaje:listaPasajeros){
//			Pasajero pasajero=ventaPasaje.getPasajero();
//			manifiestoId=ventaPasaje.getManifiesto().getId();
//			
//			MPasajero mPasajero=new MPasajero();
//			mPasajero.setTpoDoc(pasajero.getTipoDocumento().getNombreCorto());
//			mPasajero.setNroDoc(pasajero.getNumeroDocumento());
//			mPasajero.setNombre(pasajero.getNombre());
//			mPasajero.setPaterno(pasajero.getApellidoPaterno());
//			mPasajero.setMaterno(pasajero.getApellidoMaterno());
//			mPasajero.setSerBol(ventaPasaje.getNumeroBoleto().split("-")[0]);
//			mPasajero.setNumBol(ventaPasaje.getNumeroBoleto().split("-")[1]);
//			mPasajero.setMtoBol(Util.toNumberFormat(ventaPasaje.getImportePagado(), 2));
//			mPasajero.setAsiBol(ventaPasaje.getNumeroAsiento().toString());
//								
//			arrayOfMPasajero.getMPasajero().add(mPasajero);
//		}
//		
//		/** Setea Manifiesto*/
//		Manifiesto manifiesto=new Manifiesto();
//		manifiesto.setNroRuta(hre.getMtcCodigoRuta());//.getNumeroHojaRutaID().getIdNumeroHojaRuta()
//		manifiesto.setNroPlaca(hre.getNumeroPlaca());
//		manifiesto.setTerSalida(hre.getMtcTerminalSalida().getCodigo());
//		manifiesto.setTerLlegada(hre.getMtcTerminalLlegada().getCodigo());
//		manifiesto.setFecSalida(Constantes.FORMAT_DATE.format(hre.getFechaSalida()));
//		manifiesto.setHorSalida(hre.getHoraSalida());
//		manifiesto.setFecEstLlegada(Constantes.FORMAT_DATE.format(hre.getFechaEstimacionLlegada()));
//		manifiesto.setHorEstLlegada(hre.getHoraEstimacionLlegada());
//		manifiesto.setTripulantes(arrayOfMTripulante);
//		manifiesto.setConductores(arrayOfMConductor);
//		manifiesto.setPasajeros(arrayOfMPasajero);
//		codigoME=WSMTC.setManifiesto(manifiesto); //Impacta el ME
//		
//		
//		//END BEGIN 09/01/2016 - JABANTO
////		manifiesto.setSeguridad(seguridad);
////		ResultManifiesto resultManifiesto= wsServiciosHR.getWSServiciosHRSoap().setManifiesto(manifiesto);
////		if(!(resultManifiesto.isReturn())){
////			String message="El MTC ha retornado los siguientes mensajes al Generar le Manifiesto Electrónico. \n";
////			if(resultManifiesto.getErrores()!=null)
////				message+=resultManifiesto.getCode()+" - "+resultManifiesto.getErrores().getInfo();
////			throw new Exception(message);
////		}else{
////			System.out.println(resultManifiesto.getCode());
////			if(resultManifiesto.getCode()!=null)
////				codigoME=resultManifiesto.getCode();
////			else
////				codigoME="NO CODE";
////		}
//				
//		/*Actualiza el manifiesto*/
//		com.tepsa.sisvyr.model.bean.Manifiesto manifiestoTepsa=ServiceLocator.getManifiestoManager().buscarPorId(manifiestoId);
//		manifiestoTepsa.setMee(Constantes.TRUE_VALUE);
//		UtilData.auditarRegistro(manifiestoTepsa,true, getUsuario(),Executions.getCurrent());
//		ServiceLocator.getManifiestoManager().actualizar(manifiestoTepsa);
//	}
//	
	
	/**
	 * Finaliza la generacion de hoja de ruta.
	 * @param nroHojaRuta	: Numero de La HRE.
	 * @param fechaLlegada	: Fecha real de llegada.
	 * @param horaLlegada	: Hora real de Llegada
	 * @throws Exception
	 */
	private void finalizarHRE(String nroHojaRuta, String fechaLlegada, String horaLlegada)throws Exception{
		try{
			Finalizar finalizar=new Finalizar();
			finalizar.setCode(nroHojaRuta);
			finalizar.setFecLlegada(fechaLlegada);
			finalizar.setHorLlegada(horaLlegada);
			WSMTC.setFinalizar(finalizar);
			
			/*Impacta en nuestra BD*/
			HRE hre=ServiceLocator.getHREManager().buscarPorId(nroHojaRuta);
			hre.setFechaLlegadaReal(Constantes.FORMAT_DATE.parse(fechaLlegada));
			hre.setHoraLlegadaReal(horaLlegada);
			UtilData.auditarRegistro(hre, true, getUsuario(), Executions.getCurrent());
			ServiceLocator.getHREManager().actualizar(hre);
			
		}catch (WebServiceException wse){
			throw new WebServiceException(wse.getMessage());
		}catch (WSMTCExcepcion wsexc){
			throw new WSMTCExcepcion(wsexc.getMessage());
		}catch (Exception e) {
			e.printStackTrace();
			throw new Exception(e.getMessage());
		}
	}
	
	/**
	 * Imprime hoja de ruta.
	 * @param hojaRuta		: Instancia de la HojaRuta (Class MTC ws)
	 * @param programacion  : Instancia de la Programacion
	 * @throws Exception
	 */
//	public void printHre(HojaRuta hojaRuta,ProgramacionServicio programacion)throws Exception{
//	public void printHre(ProgramacionServicio programacion)throws Exception{
	public void printHre(String numeroHRE)throws Exception{
		String namefile=numeroHRE;
		/*Crea el archivo a imprimir*/
		File file= CreateDocument.crearHRE(namefile,numeroHRE);
		if(getUsuarioHardware().getPrintApplet().intValue()==Constantes.TRUE_VALUE){
			/*Inicia proceso de impresion*/
//			String file = Constantes.URL_FORMATOS_HRE+namefile+".txt";
			String url = Constantes.URL_FORMATOS_HRE + Constantes.CLAVE_PAHT + namefile+".txt";
			Window win = (Window)Executions.createComponents("imprimir.zul", null, null);
			win.setAttribute("formato", WndImprimir.FORMAT_LIQUIDACION_TURNO);
			win.setAttribute("msg", "Imprimiendo Hoja de Ruta Eléctronica... ");
			win.setAttribute("urlDocumento", url);
			win.doPopup();
		}else{
			//Descarga el archivo para la impresion
			Util.descargarArchivo(file);
		}

	}
	
	/**
	 * Previsializa la impresion de la hoja de ruta.
	 * @param hojaRuta		: Instancia de la HojaRuta (Class MTC ws)
	 * @param programacion  : Instancia de la Programacion
	 * @throws Exception
	 */
	//public void previoPrintHRE(ProgramacionServicio programacion)throws Exception{
	public void previoPrintHRE(String numeroHRE)throws Exception{
		String namefile=numeroHRE;//programacion.getHojaRuta().toString();
		/*Crea el archivo a imprimir*/
		CreateDocument.crearHRE(namefile,numeroHRE);
		
		/*Inicia proceso de previsualizacion*/
		final WndIFrame iFrame = new WndIFrame();
//		iFrame.setSrc(Constantes.URL_FORMATOS_HRE + namefile+".txt");
		iFrame.setSrc(Constantes.URL_FORMATOS_HRE +Constantes.CLAVE_PAHT + namefile+".txt");
		iFrame.setWidth("1050");
		iFrame.setheight("600");
		iFrame.loadiframe();
		
		this.appendChild(iFrame);
		iFrame.setMode("modal");
	}
	
	/**
	 * Anular hoja de ruta Electronica
	 * @param numeroHRE : Número de la hoja de ruta.
	 * @throws Exception
	 */
	private void anularHRE(String numeroHRE)throws Exception{
		try {
			
			if(WSMTC.anularHRE(numeroHRE)){
				HRE hre=ServiceLocator.getHREManager().buscarPorId(numeroHRE);
				hre.setEstadoRegistro(Constantes.VALUE_INACTIVO);
				UtilData.auditarRegistro(hre, true, getUsuario(), Executions.getCurrent());
				ServiceLocator.getHREManager().actualizar(hre);
			}
			
		}catch (WSMTCExcepcion wsex){
			throw new Exception(wsex.getMessage());
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}
		
	}
	/**
	 * Carga los buses que estan programados.
	 * @throws Exception
	 */
	public void cargarBuses()throws Exception{
		cargarBuses(null);
	}
	/**
	 * Carga los buses que estan programados.
	 * @param bus 
	 * @throws Exception
	 */
	private void cargarBuses(List<ProgramacionServicio> lstProgramacion)throws Exception{
		if(lstProgramacion==null){
			lstProgramacion=new ArrayList<ProgramacionServicio>();
			if(cmbAgencia.getSelectedItem().getValue() instanceof Agencia){
				Agencia agenciaPartida=(Agencia)cmbAgencia.getSelectedItem().getValue();
				String fecha=Constantes.FORMAT_DATE.format(dtbxFecha.getValue());
				Boolean isSalida=rdSalida.isChecked()?true:false;
				lstProgramacion=ServiceLocator.getProgramacionServiciosManager().buscarProgracion(agenciaPartida.getId(), fecha,null,isSalida);
			}
		}
		
		Util.limpiarCombobox(cmbBus);
		/*Ordena de manera ascendente los numero de buses*/
		Object[]obj=new String[lstProgramacion.size()];
		int x=-1;
		for(ProgramacionServicio programacion: lstProgramacion){
			Bus bus=programacion.getBus();
			x++;
			obj[x]=bus.getCodigo();
		}
		Arrays.sort(obj, 0, lstProgramacion.size());
		
		//Llena el combo
		UtilData.cargarGenericData(cmbBus, true);		
		for (int y=0;y<obj.length;y++) {
			String codigoBus=obj[y].toString();
			Bus bus=new Bus();
			bus.setCodigo(codigoBus);
			
			Comboitem comboitem=new Comboitem(bus.getCodigo());
			comboitem.setValue(bus);
			
			cmbBus.appendChild(comboitem);
		}
	}
	
	/*
	 * Calcula la duracion del viaje
	 */
	private String duracionViaje(Date fechaPartida, String horaPartida,Date fechaLlegada, String horaLlegada)throws Exception{
		//Cancula duracion del viaje
		Date dateSalida=toIntegrateDateTime(fechaPartida, horaPartida);
		Date dateLlegada=toIntegrateDateTime(fechaLlegada, horaLlegada);
		
		Calendar calendarSalida=Calendar.getInstance();
		Calendar calendarLlegada=Calendar.getInstance();
		calendarSalida.set(dateSalida.getYear(), dateSalida.getMonth(), dateSalida.getDate(), dateSalida.getHours(), dateSalida.getMinutes());
		calendarLlegada.set(dateLlegada.getYear(), dateLlegada.getMonth(), dateLlegada.getDate(), dateLlegada.getHours(), dateLlegada.getMinutes());
		
		long milSalida=calendarSalida.getTimeInMillis();
		long milLlegada=calendarLlegada.getTimeInMillis();
		long diff=milLlegada-milSalida;
		
		long difHoras=(diff / Constantes.MILISEGUNDOS_X_HORA);
		long difMinutos=Math.abs(diff / Constantes.MILISEGUNDOS_X_MINUTO);
		long restoMinutos=difMinutos%60;
		
		String duracion=String.valueOf(difHoras+ ":"+restoMinutos);
		
		return duracion;
	}
	 
	/**
	 * Calcula la fecha y hora en que debe terminar el conductor de conducir.
	 * @param fechaHoraInicio 	: Fecha y hora en que inicia 
	 * @param horasConduccion	: Horas de cnduccion.
	 * @return fechaTermino
	 * @throws Exception 
	 */
	private Date getFechaHoraTermino(Date fechaHoraInicio, int horasConduccion) throws Exception{
		Date fechaInicio=Constantes.FORMAT_DATE.parse(Constantes.FORMAT_DATE.format(fechaHoraInicio));
		String horaInicio=Constantes.FORMAT_TIME.format(fechaHoraInicio);
				
		 Date fechaTermino=toIntegrateDateTime(fechaInicio, horaInicio);
		 long milfechaTermino=fechaTermino.getTime()+(horasConduccion*Constantes.MILISEGUNDOS_X_HORA);
		 fechaTermino=new Date(milfechaTermino);
		 
		 return fechaTermino;
	}
	/**
	  * Integra la fecha y la hora a un objeto date
	  * @param fecha 	: Fecha 
	  * @param hora		: Hora
	  * @return Date
	*/
	private Date toIntegrateDateTime(Date fecha, String hora){
		Date fechaHora=fecha;
		fechaHora.setHours(Integer.valueOf(hora.split(":")[0]));
		fechaHora.setMinutes(Integer.valueOf(hora.split(":")[1]));
		fechaHora.setSeconds(0);
		
		return fechaHora;
	 }
	
	
	
	/**
	 * Permite abrir la venta para generar la hre
	 * @param programacionServicio
	 * @throws Exception
	 */
	private void openWindowsGenerarHRE(ProgramacionServicio programacionServicio) throws Exception{
		wndGenerarHRE = createWindowsGenerarHRE(programacionServicio);
		this.appendChild(wndGenerarHRE);
		wndGenerarHRE.setMode("modal");
	}
	/**
	 * Crea la venta para generar la hre
	 * @param programacionServicio
	 * @return
	 * @throws Exception
	 */
	private  Window createWindowsGenerarHRE(final ProgramacionServicio programacionServicio) throws Exception {
		
		final Window window = new Window();	
		window.setWidth("440px");
		window.setBorder(true);
		window.setTitle("GENERAR LA HOJA DE RUTA ELECTRÓNICA (HRE)");
		
		Grid grid = new Grid();
		Columns columns=new Columns();
		Column column=new Column();column.setWidth("110px");column.setAlign("right");
		columns.appendChild(column);
		column=new Column();
		columns.appendChild(column);
		grid.appendChild(columns);
		
		Rows rows = new Rows();
		Row row = new Row();
		
		Label  label =  new Label();
		label.setValue("BUS :");
		row.appendChild(label);
		final Label lblBus=new Label();
		lblBus.setStyle("color:blue;font-size:11px !important");
		lblBus.setValue(programacionServicio.getBus().getCodigo());
		row.appendChild(lblBus);
		rows.appendChild(row);
		
		row=new Row();
		label =  new Label();
		label.setValue("FECHA/HORA SALIDA :");
		row.appendChild(label);
		final Label lblFechaSalida=new Label();
		lblFechaSalida.setStyle("color:blue;font-size:11px !important");
		lblFechaSalida.setValue(Constantes.FORMAT_DATE.format(programacionServicio.getItinerario().getFechaPartida())+" "+programacionServicio.getItinerario().getHoraPartida());
		row.appendChild(lblFechaSalida);
		rows.appendChild(row);
		
		row=new Row();
		label =  new Label();
		label.setValue("RUTA :");
		row.appendChild(label);
		row.appendChild(label);
		final Label lblRuta=new Label();
		lblRuta.setStyle("color:blue");
		lblRuta.setValue(programacionServicio.getItinerario().getRuta().toString());
		row.appendChild(lblRuta);
		rows.appendChild(row);
		
		row=new Row();
		label =  new Label();
		label.setValue("SERVICIO :");
		row.appendChild(label);
		row.appendChild(label);
		final Label lblServicio=new Label();
		lblServicio.setStyle("color:blue");
		lblServicio.setValue(programacionServicio.getItinerario().getServicio().toString());
		row.appendChild(lblServicio);
		rows.appendChild(row);
		
		row=new Row();
		row.setSpans("1,2");
		label =  new Label();
		label.setValue("PILOTO QUE INICIA LA CONDUCCIÓN :");
		label.setStyle("color:red;font-weight: bold;font-size:10px !important");		
		row.appendChild(label);
		final Combobox cmbPilotIniciaConduccion=new Combobox();
		cmbPilotIniciaConduccion.setReadonly(true);
		cmbPilotIniciaConduccion.setWidth("250px");
		row.appendChild(cmbPilotIniciaConduccion);
		rows.appendChild(row);
		
		row=new Row();
		row.appendChild(new Separator());
		row.appendChild(new Separator());
		rows.appendChild(row);
		
		
		grid.appendChild(rows);
		
		
		Hbox hbox=new Hbox();
		hbox.setAlign("center");
		Div div=new Div();
		div.setAlign("center");
		div.setWidth(window.getWidth());
		Toolbar toolbar=new Toolbar();
		Button tbbCancelar=new Button("Cancelar", "/resources/mp_cerrar.png");
		tbbCancelar.setStyle("font-size:12px !important");
		tbbCancelar.setWidth("120px");
		tbbCancelar.setAutodisable("self");
		tbbCancelar.setMold("trendy");
		hbox.appendChild(tbbCancelar);
		
		Separator separator=new Separator();
		separator.setWidth("10px");
		hbox.appendChild(separator);
		
		Button tbbAceptar=new Button("Aceptar", "/resources/mp_aceptarEnabled.png");
		tbbAceptar.setStyle("font-size:12px !important");
		tbbAceptar.setWidth("120px");
		tbbAceptar.setAutodisable("self");
		tbbAceptar.setMold("trendy");
		hbox.appendChild(tbbAceptar);
		
		div.appendChild(hbox);
		toolbar.appendChild(div);

		window.appendChild(grid);
		window.appendChild(toolbar);
		
		
		/*Carga pilotos*/
		UtilData.cargarGenericData(cmbPilotIniciaConduccion, false);
		//Piloto
		Comboitem comboitem=new Comboitem(programacionServicio.getPiloto().toString());
		comboitem.setValue(programacionServicio.getPiloto());
		cmbPilotIniciaConduccion.appendChild(comboitem);
		//Copiloto
		comboitem=new Comboitem(programacionServicio.getCopiloto().toString());
		comboitem.setValue(programacionServicio.getCopiloto());
		cmbPilotIniciaConduccion.appendChild(comboitem);
		//Copiloto auxiliar
		if(programacionServicio.getCopilotoAuxiliar()!=null){
			comboitem=new Comboitem(programacionServicio.getCopilotoAuxiliar().toString());
			comboitem.setValue(programacionServicio.getCopilotoAuxiliar());
			cmbPilotIniciaConduccion.appendChild(comboitem);
		}
			
		
		
		/*CANCELAR*/
		tbbCancelar.addEventListener(Events.ON_CLICK, new EventListener<Event>() {
			@Override
			public void onEvent(Event event) throws Exception {
				window.onClose();
			}
		});
		
		tbbAceptar.addEventListener(Events.ON_CLICK, new EventListener<Event>() {
			@Override
			public void onEvent(Event event) throws Exception {
				if(!(cmbPilotIniciaConduccion.getSelectedItem().getValue() instanceof Personal)){
				    DlgMessage.information(Messages.getString("WndEmisonhre.information.noSelectPilotoInicio"));
					return;
				}
								
				Messagebox.show(Messages.getString("WndEmisonhre.question.generarhre"), DlgMessage.NOMBREAPLICACION, DlgMessage.BTN_YESNO, Messagebox.QUESTION,DlgMessage.BTN_DEFAULT_NO, new EventListener<Event>() {
					@Override
					public void onEvent(Event e) throws Exception {
						if(e.getName().equals("onYes")){
							try{
								/*Porcesa Manifiesto electronico*/
								procesar(programacionServicio.getId(),PROCESO_GENERAR_HRE,null,(Personal)cmbPilotIniciaConduccion.getSelectedItem().getValue(),programacionServicio.getItinerario().getHoraPartida());
								
								/*Confirma exito del fin del proceso*/
								DlgMessage.information(Messages.getString("WndEmisonhre.information.confirmaGenerarhre"));
								
								window.onClose();
								
								/*Refresca lista*/
								buscar();
							}catch (WebServiceException wse){
								DlgMessage.error("No se pudo establecer conexión con el Servicio Web del MTC.");
							}catch(Exception exc){
								if(exc.getMessage()!=null)
									DlgMessage.information(exc.getMessage());
								else
									DlgMessage.error("Ha ocurrido un error al genarar la HRE, por favor vuelva a intentarlo.");
							}
						}
					}
				});
			}
		});
		return window;
	}
	
	
	/**
	 * Permiter abrir la venta para finalizar la hre
	 * @param programacionServicio
	 * @throws Exception
	 */
	private void openWindowsFinalizarHRE(ProgramacionServicio programacionServicio) throws Exception{
		wndFinalizarHRE = createWindowsfinalizarHRE(programacionServicio);
		this.appendChild(wndFinalizarHRE);
		wndFinalizarHRE.setMode("modal");
	}
	/**
	 * create la Ventana para finalizar la hre
	 * @param programacionServicio
	 * @return
	 * @throws Exception
	 */
	private  Window createWindowsfinalizarHRE(ProgramacionServicio programacionServicio) throws Exception {
				
		final Window window = new Window();	
		window.setWidth("440px");
		window.setBorder(true);
		window.setTitle("Finalizar Hoja de Ruta Electrónica (HRE)");
//		window.setClosable(true);
		
		Grid grid = new Grid();
//		grid.setStyle("background:black");
		Columns columns=new Columns();
		Column column=new Column();column.setWidth("110px");column.setAlign("right");
		columns.appendChild(column);
		column=new Column();column.setWidth("100px");
		columns.appendChild(column);
		column=new Column();column.setWidth("90px");column.setAlign("right");
		columns.appendChild(column);
		column=new Column();
		columns.appendChild(column);
		grid.appendChild(columns);
		
		Rows rows = new Rows();
		Row row = new Row();
		
		Label  label =  new Label();
		label.setValue("NRO. HOJA RUTA :");
		row.appendChild(label);
		row.appendChild(label);
		final Label lblNroHojaruta=new Label();
		lblNroHojaruta.setStyle("color:red;font-size:11px !important");
		lblNroHojaruta.setValue(programacionServicio.getHojaRuta().toString());
		row.appendChild(lblNroHojaruta);
		row.appendChild(new Separator());
		row.appendChild(new Separator());
		rows.appendChild(row);
		
		row=new Row();
		label =  new Label();
		label.setValue("FECHA/HORA SALIDA :");
		row.appendChild(label);
		row.appendChild(label);
		final Label lblFechaSalida=new Label();
		lblFechaSalida.setStyle("color:blue;font-size:11px !important");
		lblFechaSalida.setValue(Constantes.FORMAT_DATE.format(programacionServicio.getItinerario().getFechaPartida())+" "+programacionServicio.getItinerario().getHoraPartida());
		row.appendChild(lblFechaSalida);
		
		label =  new Label();
		label.setValue("RUTA :");
		row.appendChild(label);
		row.appendChild(label);
		final Label lblRuta=new Label();
		lblRuta.setStyle("color:blue");
		lblRuta.setValue(programacionServicio.getItinerario().getRuta().toString());
		row.appendChild(lblRuta);
		rows.appendChild(row);
		
		row=new Row();
		label =  new Label();
		label.setValue("SERVICIO :");
		row.appendChild(label);
		row.appendChild(label);
		final Label lblServicio=new Label();
		lblServicio.setStyle("color:blue");
		lblServicio.setValue(programacionServicio.getItinerario().getServicio().toString());
		row.appendChild(lblServicio);
		
		label =  new Label();
		label.setValue("BUS :");
		row.appendChild(label);
		row.appendChild(label);
		final Label lblBus=new Label();
		lblBus.setStyle("color:blue;font-size:11px !important");
		lblBus.setValue(programacionServicio.getItinerario().getBus().getCodigo());
		row.appendChild(lblBus);
		rows.appendChild(row);
		
		row=new Row();
		label =  new Label();
		label.setValue("FECHA LLEGADA :");
		row.appendChild(label);
		row.appendChild(label);
		final Label lblFechaLlegada=new Label(Constantes.FORMAT_DATE.format(new Date()));
		lblFechaLlegada.setStyle("color:blue;font-size:11px !important");
		row.appendChild(lblFechaLlegada);
		
		label =  new Label();
		label.setValue("HORA LLEGADA :");
		row.appendChild(label);
		row.appendChild(label);
		final Timebox tmbxHoraLlegada=new Timebox();
		tmbxHoraLlegada.setFormat("HH:mm");
		row.appendChild(tmbxHoraLlegada);
		rows.appendChild(row);
		
		grid.appendChild(rows);
		
		Hbox hbox=new Hbox();
		Div div=new Div();
		div.setAlign("center");
		div.setWidth(window.getWidth());
		Toolbar toolbar=new Toolbar();
		Button tbbCancelar=new Button("Cancelar", "/resources/mp_cerrar.png");
		tbbCancelar.setStyle("font-size:12px !important");
		tbbCancelar.setWidth("120px");
		tbbCancelar.setAutodisable("self");
		tbbCancelar.setMold("trendy");
		hbox.appendChild(tbbCancelar);
		
		Separator separator=new Separator();
		separator.setWidth("10px");
		hbox.appendChild(separator);
		
		Button tbbAceptar=new Button("Aceptar", "/resources/mp_aceptarEnabled.png");
		tbbAceptar.setStyle("font-size:12px !important");
		tbbAceptar.setWidth("120px");
		tbbAceptar.setAutodisable("self");
		tbbAceptar.setMold("trendy");
		hbox.appendChild(tbbAceptar);
		
		div.appendChild(hbox);
		toolbar.appendChild(div);

		window.appendChild(grid);
		window.appendChild(toolbar);
		
		/*CANCELAR*/
		tbbCancelar.addEventListener(Events.ON_CLICK, new EventListener<Event>() {
			@Override
			public void onEvent(Event event) throws Exception {
				window.onClose();
			}
		});
		
		tbbAceptar.addEventListener(Events.ON_CLICK, new EventListener<Event>() {
			@Override
			public void onEvent(Event event) throws Exception {
				if(tmbxHoraLlegada.getText().isEmpty()){
					DlgMessage.information(Messages.getString("WndEmisonhre.information.noHoraLlegadaRela"),tmbxHoraLlegada);					
					return;
				}
								
				Messagebox.show(Messages.getString("WndEmisonhre.question.finalizaHRE"), DlgMessage.NOMBREAPLICACION, DlgMessage.BTN_YESNO, Messagebox.QUESTION,DlgMessage.BTN_DEFAULT_NO, new EventListener<Event>() {
					@Override
					public void onEvent(Event e) throws Exception {
						if(e.getName().equals("onYes")){
							try{
								finalizarHRE(lblNroHojaruta.getValue(),lblFechaLlegada.getValue(),tmbxHoraLlegada.getText());
								window.onClose();
								DlgMessage.information(Messages.getString("WndEmisonhre.information.confirmaFinalizahre"));
								buscar();
							}catch(Exception exc){
								if(exc.getMessage()!=null)
									DlgMessage.information(exc.getMessage());
								else
									DlgMessage.error("Ha ocurrido un error al genarar la HRE, por favor vuelva a intentarlo.");
							}
						}
					}
				});
			}
		});
		return window;
	}
}
