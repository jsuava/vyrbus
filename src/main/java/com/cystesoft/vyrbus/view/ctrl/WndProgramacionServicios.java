package com.cystesoft.vyrbus.view.ctrl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.TreeMap;

import javax.xml.ws.WebServiceException;

import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zul.Button;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Comboitem;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.Intbox;
import org.zkoss.zul.Label;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Radio;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Toolbarbutton;

import com.cystesoft.vyrbus.model.bean.Bus;
import com.cystesoft.vyrbus.model.bean.HRE;
import com.cystesoft.vyrbus.model.bean.Itinerario;
import com.cystesoft.vyrbus.model.bean.Localidad;
import com.cystesoft.vyrbus.model.bean.Manifiesto;
import com.cystesoft.vyrbus.model.bean.Parametros;
import com.cystesoft.vyrbus.model.bean.Personal;
import com.cystesoft.vyrbus.model.bean.ProgramacionServicio;
import com.cystesoft.vyrbus.model.bean.Servicio;
import com.cystesoft.vyrbus.service.exceptions.BusNullException;
import com.cystesoft.vyrbus.service.exceptions.CopilotoNullException;
import com.cystesoft.vyrbus.service.exceptions.DuplicateSeatException;
import com.cystesoft.vyrbus.service.exceptions.EdicionNoPermitidaException;
import com.cystesoft.vyrbus.service.exceptions.ItinerarioException;
import com.cystesoft.vyrbus.service.exceptions.PilotoNullException;
import com.cystesoft.vyrbus.service.exceptions.PilotosIgualesException;
import com.cystesoft.vyrbus.service.exceptions.TripulanteNullException;
import com.cystesoft.vyrbus.service.exceptions.WSMTCExcepcion;
import com.cystesoft.vyrbus.service.locator.ServiceLocator;
import com.cystesoft.vyrbus.service.util.Constantes;
import com.cystesoft.vyrbus.service.util.Messages;
import com.cystesoft.vyrbus.service.util.MyTime;
import com.cystesoft.vyrbus.service.util.Util;
import com.cystesoft.vyrbus.service.util.UtilData;
import com.cystesoft.vyrbus.service.util.WSMTC;
import com.cystesoft.vyrbus.view.ui.DlgMessage;
import com.cystesoft.vyrbus.view.ui.WndBase;

/**
 * 
 * @author JABANTO
 *
 */
public class WndProgramacionServicios extends WndBase {
	private static final long serialVersionUID = 1L;
	private Textbox	textboxId;
	private Intbox ibItinerario;
	private Combobox cmbServicio;
	private Combobox cmbOrigen;
	private Datebox	dbFechaPartida;
	private Combobox cmbDestino;
	private Datebox dbFechaLlegada;
	private Radio rdProgramados;
	private Radio rdNoProgramados;
	private Listbox listboxItinerarios;
	private Combobox cmbPiloto;
	private Combobox cmbCopiloto;
	private Combobox cmbCopilotoAuxiliar;
	private Combobox cmbTripulante;
	private Combobox cmbbus;
	private Label lblBus;
	private Label lblTipoServicio;
	private Label lblOrigen;
	private Label lblFechaPartida;
	private Label lblAgenciaPartida;
	private Label lblDestino;
	private Label lblFechaLlegada;
	private Label lblAgenciaLlegada;
	private Label lblPiloto;
	private	Label lblCopiloto;
	private Label lblCopilotoAux; 
	private Label lblTripulante;
	private Toolbarbutton tbtNuevo;
	private Toolbarbutton tbtModificar;
	private Toolbarbutton tbtGuardar;
	private Toolbarbutton tbtCancelar;
	private Toolbarbutton tbtanular;
	private Toolbarbutton tbtCerrar;
	private Button cmdBuscar;
	
	private int action;
	private ProgramacionServicio programacionServicio=null;

	
	@Override
	public void onCreate() throws Exception {			
		UtilData.cargarDataCombo(cmbServicio, Servicio.class, false);
		UtilData.cargarDataCombo(cmbOrigen, Localidad.class, true);
		UtilData.cargarDataCombo(cmbDestino, Localidad.class, true);
		UtilData.cargarPersonalXtipo(cmbPiloto, false, Constantes.ID_TIPPER_PILOTO_COPILOTO);
		UtilData.cargarPersonalXtipo(cmbCopiloto, false, Constantes.ID_TIPPER_PILOTO_COPILOTO);
		UtilData.cargarPersonalXtipo(cmbCopilotoAuxiliar, false, Constantes.ID_TIPPER_PILOTO_COPILOTO);
		UtilData.cargarPersonalXtipo(cmbTripulante, false, Constantes.ID_TIPPER_TRIPULANTE);
		
		MyTime myTime = new MyTime();
		Date date=Constantes.FORMAT_DATE.parse(myTime.dateServer());
		dbFechaPartida.setValue(date);
		dbFechaLlegada.setValue(date);
		
		/*Carga por defecto el Origen Lima*/
		Util.seleccionarValorItemCombo(Localidad.class, cmbOrigen,getAgencia().getLocalidad().getId());
		
		habilitaControlesIngresoDatos(true);
		
		/*Habilta o deshabilita botones*/
		Boolean nuevo, Modificar, Guardar, Cancelar, anular, cerrar;
		nuevo=true; Modificar=true; Guardar=true; Cancelar=true; anular=true; cerrar=false;
		HabiltaBotones(nuevo, Modificar, Guardar, Cancelar, anular, cerrar);
		
		/*Habili/Desabilita según la configuracion del rol del usuario*/
		/*BUSCAR*/
		cmdBuscar.setDisabled(accesoConsultar()?false:true);	
				
//		String fecha = Util.DatetoString(new Date(), "yyyyMMdd");
//		dbFechaPartida.setConstraint("after "+fecha);
//		dbFechaLlegada.setConstraint("after "+fecha);
		
		
		cmbPiloto.addEventListener(Events.ON_CHANGE, new EventListener<Event>() {
			@Override
			public void onEvent(Event arg0) throws Exception {
				onChangeConductorTripulante(cmbPiloto, lblPiloto, "PILOTO");
			}
		});
		cmbCopiloto.addEventListener(Events.ON_CHANGE, new EventListener<Event>() {
			@Override
			public void onEvent(Event arg0) throws Exception {
				onChangeConductorTripulante(cmbCopiloto, lblCopiloto, "COPILOTO");
			}
		});
		cmbCopilotoAuxiliar.addEventListener(Events.ON_CHANGE, new EventListener<Event>() {
			@Override
			public void onEvent(Event arg0) throws Exception {
				onChangeConductorTripulante(cmbCopilotoAuxiliar, lblCopilotoAux, "COPILOTO AUXILIAR");
			}
		});
		cmbTripulante.addEventListener(Events.ON_CHANGE, new EventListener<Event>() {
			@Override
			public void onEvent(Event arg0) throws Exception {
				onChangeConductorTripulante(cmbTripulante, lblTripulante, "TRIPULANTE");
			}
		});
	}

	@Override
	public void initComponents() {
		textboxId = (Textbox) this.getFellow("textboxId");
		ibItinerario = (Intbox) this.getFellow("ibItinerario");
		cmbServicio = (Combobox) this.getFellow("cmbServicio");
		cmbOrigen = (Combobox) this.getFellow("cmbOrigen");
		dbFechaPartida = (Datebox) this.getFellow("dbFechaPartida");
		cmbDestino = (Combobox) this.getFellow("cmbDestino");
		dbFechaLlegada = (Datebox) this.getFellow("dbFechaLlegada");
		rdProgramados = (Radio) this.getFellow("rdProgramados");
		rdNoProgramados = (Radio) this.getFellow("rdNoProgramados");
		listboxItinerarios = (Listbox) this.getFellow("listboxItinerarios");
		cmbPiloto = (Combobox) this.getFellow("cmbPiloto");
		cmbCopiloto = (Combobox) this.getFellow("cmbCopiloto");
		cmbCopilotoAuxiliar=(Combobox)this.getFellow("cmbCopilotoAuxiliar");
		cmbTripulante =	(Combobox) this.getFellow("cmbTripulante");
		cmbbus = (Combobox) this.getFellow("cmbbus");
//		lblItinerario =	(Label) this.getFellow("lblItinerario");
		lblBus = (Label) this.getFellow("lblBus");
		lblTipoServicio	= (Label) this.getFellow("lblTipoServicio");
		lblOrigen =(Label) this.getFellow("lblOrigen");
		lblFechaPartida =(Label) this.getFellow("lblFechaPartida");
//		lblHoraPartida = (Label) this.getFellow("lblHoraPartida");
		lblAgenciaPartida =(Label) this.getFellow("lblAgenciaPartida");
		lblDestino =(Label) this.getFellow("lblDestino");
		lblFechaLlegada =(Label) this.getFellow("lblFechaLlegada");
//		lblHoraLlegada =(Label) this.getFellow("lblHoraLlegada");
		lblAgenciaLlegada =(Label) this.getFellow("lblAgenciaLlegada");
		lblPiloto =(Label) this.getFellow("lblPiloto");
		lblCopiloto =(Label) this.getFellow("lblCopiloto");
		lblCopilotoAux=(Label) this.getFellow("lblCopilotoAux");
		lblTripulante =(Label) this.getFellow("lblTripulante");
		tbtNuevo = (Toolbarbutton) this.getFellow("tbtNuevo");
		tbtModificar= (Toolbarbutton) this.getFellow("tbtModificar");
		tbtGuardar= (Toolbarbutton) this.getFellow("tbtGuardar");
		tbtCancelar= (Toolbarbutton) this.getFellow("tbtCancelar");
		tbtanular= (Toolbarbutton) this.getFellow("tbtanular");
		tbtCerrar= (Toolbarbutton) this.getFellow("tbtCerrar");
		cmdBuscar = (Button) this.getFellow("cmdBuscar");
	}
	
	/**
	 * Modifica Programación.
	 * @throws Exception
	 */
	public void onModificar() throws Exception{
		try{
			/*Valida si el servicio ya tiene manifiesto*/
			TreeMap<String, Object> criteriosBusqueda = new TreeMap<String, Object>();
			criteriosBusqueda.put("bus", programacionServicio.getBus());
			criteriosBusqueda.put("itinerario", programacionServicio.getItinerario());
			criteriosBusqueda.put("estadoRegistro", Constantes.VALUE_ACTIVO);
						
			List<Manifiesto>listManifiesto=ServiceLocator.getManifiestoManager().buscarPorX(criteriosBusqueda, null);
			if(listManifiesto.size()>0)
				throw new EdicionNoPermitidaException();
						
			/*Valida si el servicio ya esta asociado a una hre*/
			HRE hre= ServiceLocator.getHREManager().buscarHREEmitida(programacionServicio.getItinerario().getId());
//			HRE hre= ServiceLocator.getHREManager().buscarHREEmitida(Constantes.FORMAT_DATE.format(programacionServicio.getItinerario().getFechaPartida()), ((Bus)cmbbus.getSelectedItem().getValue()).getCodigo());
			if(hre!=null){
				DlgMessage.information(Messages.getString("WndProgramacionServicios.Information.modifiServicioHRE"));
				return;
			}
			
			action=Constantes.ACTION_MODIFY;
			habilitaControlesIngresoDatos(false);
			
			/*Habilta o deshabilita botones*/
			Boolean nuevo, Modificar, Guardar, Cancelar, anular, cerrar;
			nuevo=true; Modificar=true; Guardar=false; Cancelar=false; anular=true; cerrar=true;
			HabiltaBotones(nuevo, Modificar, Guardar, Cancelar, anular, cerrar);
		
		}catch (EdicionNoPermitidaException end){
			DlgMessage.information(Messages.getString("WndProgramacionServicios.Information.EdicionNoPermitida"));
		}catch (Exception ex){
			DlgMessage.error(this.getClass().getName()+" "+ex.getMessage());
			ex.printStackTrace();
		}
		
	}
	
	/**
	 * Cancela la Operacion de la Programación.
	 * @throws Exception
	 */
	public void onCancelar() throws Exception{
		try{
			if (!(action==Constantes.ACTION_MODIFY)){
				limpiaPrevioProgramacion();
				/*Habilta o deshabilita botones*/
				Boolean nuevo, Modificar, Guardar, Cancelar, anular, cerrar;
				Modificar=true; Guardar=true; Cancelar=true; anular=true; cerrar=false;
				
				/*Habili/Desabilita según la configuracion del rol del usuario*/
				/*NUEVO*/
				if(rdProgramados.isChecked())
					nuevo=true;
				else
					nuevo= (accesoNuevo()?false:true);
							
				HabiltaBotones(nuevo, Modificar, Guardar, Cancelar, anular, cerrar);
			}else{
				/*Habilta o deshabilita botones*/
				Boolean nuevo, Modificar, Guardar, Cancelar, anular, cerrar;
				Guardar=true; Cancelar=true; cerrar=false;
				
				/*Habili/Desabilita según la configuracion del rol del usuario*/
				/*NEUVO*/
				if(rdProgramados.isChecked())
					nuevo=true;
				else
					nuevo=(accesoNuevo()?false:true);
				/*MODIFICAR*/
				Modificar=(accesoModificar()?false:true);
				/*ELIMINAR*/
				anular=(accesoEliminar()?false:true);
							
				HabiltaBotones(nuevo, Modificar, Guardar, Cancelar, anular, cerrar);
			}
			habilitaControlesIngresoDatos(true);
		
			
			action=Constantes.ACTION_CONSULTA;
		}catch (Exception ex){
			DlgMessage.error(this.getClass().getName()+" "+ex.getMessage());
			ex.printStackTrace();
		}
	}
	
	/**
	 * Guarda Programación.
	 * @throws Exception
	 */
	public void onGuardarProgramacion() throws Exception{
		try{
			if (cmbPiloto.getSelectedIndex() >=0 ){
				if (!(cmbPiloto.getSelectedItem().getValue() instanceof Personal))
					throw new PilotoNullException();
			}else{
				throw new PilotoNullException();
			}
			if (cmbCopiloto.getSelectedIndex() >=0 ){
				if (!(cmbCopiloto.getSelectedItem().getValue() instanceof Personal))
					throw new CopilotoNullException();
			}else{
				throw new CopilotoNullException();
			}
			if (cmbTripulante.getSelectedIndex() >=0 ){
				if (!(cmbTripulante.getSelectedItem().getValue() instanceof Personal))
					throw new TripulanteNullException();
			}else{
				throw new TripulanteNullException();
			}
			if (cmbbus.getSelectedIndex() >=0 ){
				if (!(cmbbus.getSelectedItem().getValue() instanceof Bus))
					throw new BusNullException();
			}else{
				throw new BusNullException();
			}
			if(cmbPiloto.getSelectedItem().getValue().equals(cmbCopiloto.getSelectedItem().getValue()))
				throw new PilotosIgualesException();
			
			Itinerario itinerario = ((ProgramacionServicio)listboxItinerarios.getSelectedItem().getValue()).getItinerario();
			
			if (action==Constantes.ACTION_NEW){
				//Valida que la programacion pertenesca a la fecha actual.
				String fechaActual=Constantes.FORMAT_DATE.format(Constantes.FORMAT_DATE.parse(new MyTime().dateServer()));
				if(!(Constantes.FORMAT_DATE.format(itinerario.getFechaPartida()).equals(fechaActual))){
					Boolean permitir=false;
					/* Valida si es servicio especial, para permitir solo si la diferencia es de + un dia a la fecha actual*/
					if(itinerario.getTipoItinerario().getId().intValue()==Constantes.ID_TIPITI_ESPECIAL){
						if(Constantes.FORMAT_DATE.format(itinerario.getFechaPartida()).equals(Constantes.FORMAT_DATE.format(new Date().getTime()+Constantes.MILISEGUNDOS_X_DIA)))
							permitir=true;
					}
					if (!(permitir)){
						DlgMessage.information(Messages.getString("WndProgramacionServicios.Information.programacionFueraFecha"));
						return;
					}
				}
				programacionServicio=new ProgramacionServicio();
			}
			
			Bus bus=(Bus)cmbbus.getSelectedItem().getValue();
			Personal piloto = (Personal) cmbPiloto.getSelectedItem().getValue();
			Personal copiloto = (Personal) cmbCopiloto.getSelectedItem().getValue();
			Personal copilotoAuxiliar=null;
			if(cmbCopilotoAuxiliar.getSelectedIndex()>0)
				copilotoAuxiliar = (Personal) cmbCopilotoAuxiliar.getSelectedItem().getValue();
			
			Personal tripulante = (Personal) cmbTripulante.getSelectedItem().getValue();
			Long id = (textboxId.getText().equals("") ? 0 : new Long(textboxId.getText()));
			programacionServicio.setId(id);
			programacionServicio.setItinerario(itinerario);
			programacionServicio.setBus(bus);
			programacionServicio.setPiloto(piloto);
			programacionServicio.setCopiloto(copiloto);
			programacionServicio.setCopilotoAuxiliar(copilotoAuxiliar);
			programacionServicio.setTripulante(tripulante);
			programacionServicio.setEstadoRegistro(Constantes.VALUE_ACTIVO);
			
			
			/* *****VALIDACION DE LOS PILOTOS Y BUS CON EL WS DEL MTC*******Impl: 20/08/2014 - jabanto  --desactivado el 08/11/2014*/
			String tipoDcouemnto="";
			String messageWSMTC=null;
			Parametros parametros= ServiceLocator.getParametrosManager().buscarPorEstadoRegistro("A");
			if(parametros!=null && parametros.getValidarProgramacionMtc()!=null && parametros.getValidarProgramacionMtc().intValue()==Constantes.TRUE_VALUE){ // 09/01/2017 - jabanto
				//Validando Piloto
				tipoDcouemnto=WSMTC.getTipoDocumento(piloto.getTipoDocumento().getId(), WSMTC.TIPO_CONDUCTOR_PILOTO);
				if(tipoDcouemnto.length()<7)
					messageWSMTC=WSMTC.validarConductor(tipoDcouemnto,piloto.getNroDocumento(),WSMTC.TIPO_CONDUCTOR_PILOTO);
				else
					messageWSMTC=tipoDcouemnto;
				//Validando Copiloto
				if(messageWSMTC==null){
					tipoDcouemnto=WSMTC.getTipoDocumento(copiloto.getTipoDocumento().getId(), WSMTC.TIPO_CONDUCTOR_COPILOTO);
					if(tipoDcouemnto.length()<7)
						messageWSMTC=WSMTC.validarConductor(tipoDcouemnto, copiloto.getNroDocumento(),WSMTC.TIPO_CONDUCTOR_COPILOTO);
					else
						messageWSMTC=tipoDcouemnto;
				}
				//Validando 3r Piloto.
				if(messageWSMTC==null && copilotoAuxiliar!=null){
					tipoDcouemnto=WSMTC.getTipoDocumento(copilotoAuxiliar.getTipoDocumento().getId(), WSMTC.TIPO_CONDUCTOR_COPILOTOAUXILIAR);
					if(tipoDcouemnto.length()<7)
						messageWSMTC=WSMTC.validarConductor("L.E.", copilotoAuxiliar.getNroDocumento(),WSMTC.TIPO_CONDUCTOR_COPILOTOAUXILIAR);
					else
						messageWSMTC=tipoDcouemnto;
				}
				//Validando Bus
				if(messageWSMTC==null)
					messageWSMTC=WSMTC.validarVehiculo(bus.getNumeroPlaca());
				/* ***********FIN VALIDACIN WS MTC *****/
			}
			
			//Cuando los metodos de validacion con el WSMTC encuentran algun error y el rol es super usuario, permitira omitir tal validación y continuar con la programacion del servicio.
			if(messageWSMTC!=null){
				final String messageWSMTCf=messageWSMTC;
				Messagebox.show(messageWSMTC+ "\n"+"¿Realmente desea continuar?", DlgMessage.NOMBREAPLICACION, DlgMessage.BTN_YESNO, Messagebox.QUESTION,DlgMessage.BTN_DEFAULT_NO, new EventListener<Event>() {
					@Override
					public void onEvent(Event e) throws Exception {
						if(e.getName().equals("onYes")){
							savedProgramacion();
							WSMTC.enviarCorreo(messageWSMTCf, programacionServicio, getUsuario().toString(), getAgencia().toString());
						}
					}
				});
			}else{
				Messagebox.show(Messages.getString("Generales.query.guardar"), DlgMessage.NOMBREAPLICACION, DlgMessage.BTN_YESNO, Messagebox.QUESTION,DlgMessage.BTN_DEFAULT_NO, new EventListener<Event>() {
					@Override
					public void onEvent(Event e) throws Exception {
						if(e.getName().equals("onYes")){		
							try{
								
								savedProgramacion();
								
							}catch (ItinerarioException inex){
								if(inex.getTipo().intValue()==ItinerarioException.ITINERARIO_NULL)
									DlgMessage.information(Messages.getString("WndProgramacionServicios.Information.SeleccioneItinerario"));
							}catch(DuplicateSeatException  ds){
								DlgMessage.information(Messages.getString("WndProgramacionServicios.Information.duplicate"));
							}
						}
					}
				});
			}
			
		}catch (PilotosIgualesException piex){
			DlgMessage.information(Messages.getString("WndProgramacionServicios.Information.PilotosIguales"),cmbPiloto);
		}catch(PilotoNullException pnex){
			DlgMessage.information(Messages.getString("WndProgramacionServicios.Information.SeleccionePilo"),cmbPiloto);
		}catch (CopilotoNullException cpnex){
			DlgMessage.information(Messages.getString("WndProgramacionServicios.Information.SeleccionCopiloto"),cmbCopiloto);
		}catch (TripulanteNullException tnex){
			DlgMessage.information(Messages.getString("WndProgramacionServicios.Information.SeleccioneTripulante"),cmbTripulante);
		}catch (BusNullException bnex){
			DlgMessage.information(Messages.getString("WndProgramacionServicios.Information.SeleccioneBus"),cmbbus);
		}catch (ItinerarioException inex){
			if(inex.getTipo().intValue()==ItinerarioException.ITINERARIO_NULL)
				DlgMessage.information(Messages.getString("WndProgramacionServicios.Information.SeleccioneItinerario"));
		}catch(WSMTCExcepcion  wec){
			DlgMessage.information(wec.getMessage());	
		}catch(DuplicateSeatException  ds){
			DlgMessage.information(Messages.getString("WndProgramacionServicios.Information.duplicate"));
		}catch (WebServiceException wse){
			DlgMessage.error("No se pudo establecer conexión con el Servicio Web del MTC");
		}catch (Exception ex){
			DlgMessage.error(this.getClass().getName()+" "+ex.getMessage());
			ex.printStackTrace();
		}
	}
	
	private void savedProgramacion()throws Exception{
		try{
			//Actualiza el itinerario con el identificacion del bus.
			ServiceLocator.getProgramacionServiciosManager().updateItinerarioBus(programacionServicio.getItinerario().getId(), programacionServicio.getBus().getId().longValue());
			
			switch (action) {
				case Constantes.ACTION_NEW:
					UtilData.auditarRegistro(programacionServicio, false, getUsuario(), Executions.getCurrent());
					Integer result= ServiceLocator.getProgramacionServiciosManager().guardar(programacionServicio);
					if (result==Constantes.CORRECT){
						DlgMessage.information(Messages.getString("WndProgramacionServicios.Information.GuardoCorrectamente"));
					}
					break;
				case Constantes.ACTION_MODIFY:
					UtilData.auditarRegistro(programacionServicio, true, getUsuario(), Executions.getCurrent());
					ServiceLocator.getProgramacionServiciosManager().actualizar(programacionServicio);
					DlgMessage.information(Messages.getString("WndProgramacionServicios.Information.ActualizadoCorrectamente"));
					break;
			}
			
			textboxId.setText(programacionServicio.getId().toString());			
			habilitaControlesIngresoDatos(true);
			/*Recupera los itinerarios Programados*/
			List<ProgramacionServicio> lstProgramacionServicio;
			rdProgramados.setChecked(true);
			lstProgramacionServicio= ServiceLocator.getProgramacionServiciosManager().buscarItinerariosProgramados(programacionServicio.getItinerario().getId(), null, null, 
					Constantes.FORMAT_DATE.format(dbFechaPartida.getValue()),
					Constantes.FORMAT_DATE.format(dbFechaLlegada.getValue()), 
					null,true);
			Util.limpiarListbox(listboxItinerarios);
			cargaItinerario(lstProgramacionServicio);
			/*Habilta o deshabilita botones*/
			Boolean nuevo, Modificar, Guardar, Cancelar, anular, cerrar;
			nuevo=false; Guardar=true; Cancelar=true; cerrar=false;
			/*Habili/Desabilita según la configuracion del rol del usuario*/
			/*MODIFICAR*/
			Modificar=(accesoModificar()?false:true);
			/*ELIMINAR*/		
			anular=(accesoEliminar()?false:true);
			
			HabiltaBotones(nuevo, Modificar, Guardar, Cancelar, anular, cerrar);			
			action=Constantes.ACTION_CONSULTA;
		}catch(DuplicateSeatException  ds){
			DlgMessage.information(Messages.getString("WndProgramacionServicios.Information.duplicate"));
		}
	}
	
	
	/**
	 * Anula Programación.
	 * @throws Exception 
	 */
	public void onAnularProgramacion() throws Exception{
		try{
			/*Valida si el servicio ya tiene manifiesto*/
			TreeMap<String, Object> criteriosBusqueda = new TreeMap<String, Object>();
			criteriosBusqueda.put("bus", programacionServicio.getBus());
			criteriosBusqueda.put("itinerario", programacionServicio.getItinerario());
			criteriosBusqueda.put("estadoRegistro", Constantes.VALUE_ACTIVO);
			List<Manifiesto>listManifiesto=ServiceLocator.getManifiestoManager().buscarPorX(criteriosBusqueda, null);
			if(listManifiesto.size()>0)
				throw new EdicionNoPermitidaException();
			
			/*Valida si el servicio ya esta asociado a una hre*/
			HRE hre= ServiceLocator.getHREManager().buscarHREEmitida(programacionServicio.getItinerario().getId());
//			HRE hre= ServiceLocator.getHREManager().buscarHREEmitida(Constantes.FORMAT_DATE.format(programacionServicio.getItinerario().getFechaPartida()), ((Bus)cmbbus.getSelectedItem().getValue()).getCodigo());
			if(hre!=null){
				DlgMessage.information(Messages.getString("WndProgramacionServicios.Information.deleteServicioHRE"));
				return;
			}
			
			Messagebox.show(Messages.getString("WndProgramacionServicios.question.AnularProgramacion"), 
										DlgMessage.NOMBREAPLICACION, DlgMessage.BTN_YESNO, Messagebox.QUESTION, new EventListener<Event>() {
				@Override
				public void onEvent(Event e) throws Exception {
					if(e.getName().equals("onYes")){
						ServiceLocator.getProgramacionServiciosManager().inactivar(new Long(textboxId.getValue()));
						onSearch();
					}
				}	
			});
		}catch (EdicionNoPermitidaException ede){
			DlgMessage.information(Messages.getString("WndProgramacionServicios.Information.EliminacionNoPermitida"));
		}
		
	}
	
	/**
	 * Cierra la Venta de Programación.
	 */
	public void cerrar(){
		closeTabWindow();
	}
	
	/**
	 * Nueva Programación.
	 * @throws Exception
	 */
	public void onNuevaProgramacion() throws Exception{
		try{
			if (listboxItinerarios.getItems().size() >0  && listboxItinerarios.getSelectedIndex() >=0){
				action=Constantes.ACTION_NEW;
				
				programacionServicio= null;
				
				onChangePrevioProgramacion(); /*Carga el Previo de la programación*/
				habilitaControlesIngresoDatos(false);
				
				/*Habilta o deshabilita botones*/
				Boolean nuevo, Modificar, Guardar, Cancelar, anular, cerrar;
				nuevo=true; Modificar=true; Guardar=false; Cancelar=false; anular=true; cerrar=true;
				HabiltaBotones(nuevo, Modificar, Guardar, Cancelar, anular, cerrar);
				
				cmbbus.setFocus(true);
			}else{
				DlgMessage.information(Messages.getString("WndProgramacionServicios.Information.SeleccionItinerario"),cmbbus);
			}
		}catch (Exception ex){
			DlgMessage.error(this.getClass().getName()+" "+ex.getMessage());
			ex.printStackTrace();
		}
	}
	
	
	/**
	 * Busqueda de Itinerarios para generar la Programación o para recuperar una Programación.
	 * @throws Exception
	 */
	public void onSearch() throws Exception{
		try{
			if (!(cmdBuscar.isDisabled())){
				List<ProgramacionServicio> lstProgramacionServicio=null;
				
				Long itinerario = null;
				String servicio= null;
				String origen = null;
				String destino = null;
				
				if (ibItinerario.getValue() !=null)
					itinerario=ibItinerario.getValue().longValue();
				if (cmbServicio.getSelectedItem().getValue() instanceof Servicio)
					servicio =(((Servicio) cmbServicio.getSelectedItem().getValue()).getDenominacion());
				if (cmbOrigen.getSelectedItem().getValue() instanceof Localidad)
					origen =(((Localidad) cmbOrigen.getSelectedItem().getValue()).getDenominacion());
				if (cmbDestino.getSelectedItem().getValue() instanceof Localidad)
					destino =(((Localidad) cmbDestino.getSelectedItem().getValue()).getDenominacion());
				
				/*Buqueda por estado*/
				if (rdNoProgramados.isSelected()){
					/*Recupera Itinerarios No Programados*/
					lstProgramacionServicio= ServiceLocator.getProgramacionServiciosManager().buscarItinerariosProgramados(itinerario, origen, destino, 
							Constantes.FORMAT_DATE.format(dbFechaPartida.getValue()),
							Constantes.FORMAT_DATE.format(dbFechaLlegada.getValue()), 
							servicio,false);
				}else if (rdProgramados.isSelected()){
					/*Recupera los itinerarios Programados*/
					lstProgramacionServicio= ServiceLocator.getProgramacionServiciosManager().buscarItinerariosProgramados(itinerario, origen, destino, 
							Constantes.FORMAT_DATE.format(dbFechaPartida.getValue()),
							Constantes.FORMAT_DATE.format(dbFechaLlegada.getValue()), 
							servicio,true);
				}
				Util.limpiarListbox(listboxItinerarios);
				cargaItinerario(lstProgramacionServicio);
				
				/*Habilta o deshabilita botones*/
				Boolean nuevo, Modificar, Guardar, Cancelar, anular, cerrar;
				nuevo=true; Modificar=true; Guardar=true; Cancelar=true; anular=true; cerrar=false;		
				
				HabiltaBotones(nuevo, Modificar, Guardar, Cancelar, anular, cerrar);
				
				limpiaPrevioProgramacion();
				
				action=Constantes.ACTION_CONSULTA;
			}
		}catch (Exception ex){
			DlgMessage.error(this.getClass().getName()+" "+ex.getMessage());
			ex.printStackTrace();
		}
		
	}
	
	/**
	 * Carga lista de Itinerarios en el ListBox, según la busqueda realizado.
	 * @param lstItinerario	: Lista de Itinerarios a Cargar en el ListBox.
	 */
	private void cargaItinerario(List<ProgramacionServicio> lstProgramacionServicios){
		if(lstProgramacionServicios.size()>0){
			Listitem item = null;
			Listcell cell = null;
			Integer x =0;
			for(ProgramacionServicio programacionServicio : lstProgramacionServicios){
				item = new Listitem();
				x += +1;
				cell = new Listcell((x.toString()));
				item.appendChild(cell); //Correlativo
				cell = new Listcell(programacionServicio.getItinerario().getId().toString());
				cell.setStyle("font-size:11px !important");
				item.appendChild(cell);
				cell = new Listcell(programacionServicio.getItinerario().getServicio().getDenominacion());
				item.appendChild(cell);
				cell = new Listcell(programacionServicio.getItinerario().getRuta().getOrigen());
				item.appendChild(cell);
				cell = new Listcell(programacionServicio.getItinerario().getAgenciaPartida().getNombreCorto());
				item.appendChild(cell);
				cell = new Listcell(Constantes.FORMAT_DATE.format(programacionServicio.getItinerario().getFechaPartida()));
				cell.setStyle("font-size:11px !important");
				item.appendChild(cell);
				cell = new Listcell(programacionServicio.getItinerario().getHoraPartida());
				cell.setStyle("font-size:11px !important");
				item.appendChild(cell);
				cell = new Listcell(programacionServicio.getItinerario().getRuta().getDestino());
				item.appendChild(cell);	
				cell = new Listcell(programacionServicio.getItinerario().getAgenciaLlegada().getNombreCorto());
				item.appendChild(cell);
				item.setValue(programacionServicio);
				listboxItinerarios.appendChild(item);
			}
		}
	}
	
	
	/**
	 * Habilita o desabilita ingreso de datos para la programación.
	 * @param disabled	: true=Desabilitado; false=Habilitado.
	 */
	private void habilitaControlesIngresoDatos(Boolean disabled){
		cmbPiloto.setDisabled(disabled);
		cmbCopiloto.setDisabled(disabled);
		cmbCopilotoAuxiliar.setDisabled(disabled);
		cmbTripulante.setDisabled(disabled);
		cmbbus.setDisabled(disabled);
	}
		
	public void onChangeConductorTripulante(final Combobox combobox, final Label lblPrevio,String message)throws Exception{
		if (combobox.getSelectedIndex() >=0 ){
			if (combobox.getSelectedItem().getValue() instanceof Personal){
				Itinerario itinerario =(((ProgramacionServicio)listboxItinerarios.getSelectedItem().getValue()).getItinerario());
				/*Valida si el piloto no este asociado a otro servicio en la misma fecha*/
				String fechaPartida=Constantes.FORMAT_DATE.format(itinerario.getFechaPartida());//lblFechaPartida.getValue();//Constantes.FORMAT_DATE.format(dbFechaPartida.getValue());
				Long idConductor=((Personal)combobox.getSelectedItem().getValue()).getId();
				
				ProgramacionServicio programacion=ServiceLocator.getProgramacionServiciosManager().validaIngresoChoferTripulante(fechaPartida, idConductor, null, null,itinerario.getHoraPartida(),itinerario.getAgenciaPartida().getId());
				if(programacion==null){
					lblPrevio.setValue(combobox.getText());
				}else if (programacionServicio!=null && programacion.getId().equals(programacionServicio.getId())){
					lblPrevio.setValue(combobox.getText());
				}else{
					Messagebox.show("El "+message+" seleccionado se encuentra programado en el Servicio: " +
							programacion.getItinerario().getRuta().getOrigen() +" - "+programacion.getItinerario().getRuta().getDestino() +
							",  Hora Partida:"+programacion.getItinerario().getHoraPartida()+", Bus:"+programacion.getBus().getCodigo() +
							". 	¿Desea continuar?", 
												DlgMessage.NOMBREAPLICACION, DlgMessage.BTN_YESNO, Messagebox.QUESTION, new EventListener<Event>() {
							@Override
							public void onEvent(Event e) throws Exception {
								if(e.getName().equals("onYes")){
									lblPrevio.setValue(((Personal) combobox.getSelectedItem().getValue()).getApellidoPaterno() +" "+ 
											((Personal) combobox.getSelectedItem().getValue()).getApellidoMaterno() + ", " + 
											((Personal) combobox.getSelectedItem().getValue()).getNombre());
								}else{
									if (programacionServicio!=null && action==Constantes.ACTION_MODIFY){
										Util.seleccionarValorItemCombo(Personal.class, combobox, programacionServicio.getPiloto().getId());
									}else{
										combobox.setValue("");
										lblPrevio.setValue("");
										combobox.setFocus(true);	
									}
								}
							}
						});
				     }
			}else{
				combobox.setValue("");
				lblPrevio.setValue("");
				combobox.setFocus(true);
			}
		}else{
			lblPrevio.setValue("");
			combobox.setText("");
			combobox.setFocus(true);
			//DlgMessage.information(Messages.getString("WndProgramacionServicios.Information.PiloNoexiste"));
		}
	}
	
	/**
	 * Carga códigos de los Buses en el ComboBox 
	 * @throws Exception 
	 */
	public void onChangeBus() throws Exception{ 
		Boolean busProgramado=false; /*False=Bus no Programado; true=Bus Programado*/
		if (cmbbus.getSelectedIndex() >=0){
			if (cmbbus.getSelectedItem().getValue() instanceof Bus){
				Bus bus=cmbbus.getSelectedItem().getValue();
				Itinerario itinerario=(((ProgramacionServicio)listboxItinerarios.getSelectedItem().getValue()).getItinerario());
				
				/*Valida si el bus seleccionado ya esta programado*/
				String fechaPartidad= Constantes.FORMAT_DATE.format(itinerario.getFechaPartida());
				String horaPartida=itinerario.getHoraPartida();
				List<ProgramacionServicio> list = ServiceLocator.getProgramacionServiciosManager().validacionBusesProgramados(fechaPartidad,horaPartida,bus.getId(),itinerario.getAgenciaPartida().getId());
				Long id = null;				
				if (action== Constantes.ACTION_MODIFY){
					id = (new Long(textboxId.getText()));
					if (list.size() > 0 && (((ProgramacionServicio) list.get(0)).getId().equals(id))){
						busProgramado=false;
					}
				}else{
					if (list.size() > 0){
						id =(((ProgramacionServicio) list.get(0)).getId());
						if (((ProgramacionServicio) list.get(0)).getId().equals(id))
							busProgramado=true;
						else
							busProgramado=false;
					}
				}
				
				if (busProgramado==true){
					ProgramacionServicio programacionServicio = new ProgramacionServicio();
					programacionServicio = list.get(0);
					Messagebox.show("El Bus seleccionado ya ésta Programado para esta fecha. "+ 
									"	Itinerario:" +  programacionServicio.getItinerario().getId() +
									"	Ruta:" + programacionServicio.getItinerario().getRuta().getOrigen() + " - " + programacionServicio.getItinerario().getRuta().getDestino() +
									". 	¿Desea continuar?", 
														DlgMessage.NOMBREAPLICACION, DlgMessage.BTN_YESNO, Messagebox.QUESTION, new EventListener<Event>() {
						@Override
						public void onEvent(Event e) throws Exception {
							if(e.getName().equals("onYes")){
								lblBus.setValue(((Bus) cmbbus.getSelectedItem().getValue()).getCodigo());
							}else{
								cmbbus.setText("");
								lblBus.setValue("");
							}
						}	
					});
				}else{
					lblBus.setValue(((Bus) cmbbus.getSelectedItem().getValue()).getCodigo());
				}
			}else{
				lblBus.setValue("");
			}
		}
	}
	
	/**
	 * Carga los buses según el Servicio del Itinerario seleccionado.
	 * @param servicio
	 * @throws Exception
	 */
	private void cargarDataComboBuses(Servicio servicio) throws Exception{
		List<String> criteriosOrdenar = new ArrayList<String>();
		TreeMap<String, Object> criteriosBusqueda = new TreeMap<String, Object>();
		criteriosOrdenar.add("codigo");
		
		if (servicio == null )
			criteriosBusqueda.remove("servicio");
		else
			criteriosBusqueda.put("servicio", servicio);
		criteriosBusqueda.put("estadoRegistro", Constantes.VALUE_ACTIVO);
		ArrayList<Bus> lstBuses = ServiceLocator.getBusManager().buscarPorX(criteriosBusqueda, criteriosOrdenar);
		
		cmbbus.getItems().clear();
		UtilData.cargarGenericData(cmbbus, false);
		for (int l = 0; l < lstBuses.size(); l ++) {
			Bus bus = lstBuses.get(l);
			Comboitem oComboitem = new Comboitem();
			oComboitem.setValue(bus);
			oComboitem.setLabel(bus.getCodigo() +"  Cap. "+ bus.getCapacidad());

			cmbbus.appendChild(oComboitem);
	   }
	}
	
	/**
	 * Carga el Previo de la Programación
	 */
	public void onChangePrevioProgramacion(){
		Itinerario itinerario=((ProgramacionServicio)listboxItinerarios.getSelectedItem().getValue()).getItinerario();
		
		lblTipoServicio.setValue(itinerario.getServicio().getDenominacion());
		lblOrigen.setValue(itinerario.getRuta().getOrigen());
		lblFechaPartida.setValue(Constantes.FORMAT_DATE.format(itinerario.getFechaPartida())+" "+itinerario.getHoraPartida() );
		lblAgenciaPartida.setValue(itinerario.getAgenciaPartida().getDenominacion());
		lblDestino.setValue(itinerario.getRuta().getDestino());
		lblFechaLlegada.setValue(Constantes.FORMAT_DATE.format(itinerario.getFechaLlegada())+" "+itinerario.getHoraLlegada());
		lblAgenciaLlegada.setValue(itinerario.getAgenciaLlegada().getDenominacion());
	}
	
	/**
	 * Limpia el Previo de la Programación.
	 */
	private void limpiaPrevioProgramacion(){
		cmbPiloto.setText("");
		cmbCopiloto.setText("");
		cmbCopilotoAuxiliar.setText("");
		cmbTripulante.setText("");
		cmbbus.setText("");
		
//		lblItinerario.setValue("");
		lblBus.setValue("");
		lblTipoServicio.setValue("");
		lblOrigen.setValue("");
		lblFechaPartida.setValue("");
//		lblHoraPartida.setValue("");
		lblAgenciaPartida.setValue("");
		lblDestino.setValue("");
		lblFechaLlegada.setValue("");
//		lblHoraLlegada.setValue("");
		lblAgenciaLlegada.setValue("");
		lblPiloto.setValue("");
		lblCopiloto.setValue("");
		lblTripulante.setValue("");
		
	}
	
	/**
	 * Habilita o desabilita Menú
	 * @param nuevo		: True=Desabilitado; false=Habilitado.
	 * @param Modificar	: True=Desabilitado; false=Habilitado.
	 * @param Guardar	: True=Desabilitado; false=Habilitado.
	 * @param Cancelar	: True=Desabilitado; false=Habilitado.
	 * @param anular	: True=Desabilitado; false=Habilitado.
	 * @param cerrar	: True=Desabilitado; false=Habilitado.
	 */
	public void HabiltaBotones(Boolean nuevo, Boolean Modificar, Boolean Guardar, Boolean Cancelar, Boolean anular, Boolean cerrar){
		disabledNuevo(nuevo,tbtNuevo);
		disabledEditar(Modificar,tbtModificar);
		disabledGuardar(Guardar,tbtGuardar);
		disabledCancelar(Cancelar,tbtCancelar);
		disabledEliminar(anular,tbtanular);
		disabledCerrar(cerrar,tbtCerrar);
	}
	
	/**
	 * Cuando el seleccionar un itinerario de la lista.
	 * @throws Exception 
	 */
	public void onClickLisBox() throws Exception{
		Boolean nuevo, Modificar, Guardar, Cancelar, anular, cerrar;
		
		programacionServicio =(ProgramacionServicio) listboxItinerarios.getSelectedItem().getValue();
		/*Carga buses según el tipo de servicio del Itinerario seleccionado.*/
		Servicio servicio = new Servicio();
//		servicio.setId(((ProgramacionServicio) listboxItinerarios.getItemAtIndex(listboxItinerarios.getSelectedIndex()).getValue()).getItinerario().getServicio().getId());
		servicio.setId(programacionServicio.getItinerario().getServicio().getId());
		cargarDataComboBuses(servicio);
		
		habilitaControlesIngresoDatos(true);
		
		if (rdNoProgramados.isChecked()){
			/*Habilta o deshabilita botones , para ingresar una nueva programacion*/
			Modificar=true; Guardar=true; Cancelar=true; anular=true; cerrar=false;
			
			/*Habili/Desabilita según la configuracion del rol del usuario*/
			nuevo=(accesoNuevo()?false:true);
						
			HabiltaBotones(nuevo, Modificar, Guardar, Cancelar, anular, cerrar);
			limpiaPrevioProgramacion();
			
		}else if (rdProgramados.isChecked()){
			/*Habilta o deshabilita botones , para modificar o consultar una programacion*/
			nuevo=true; Guardar=true; Cancelar=true; cerrar=false;
			/*Habili/Desabilita según la configuracion del rol del usuario*/
			/*MODIFICAR*/
			Modificar=(accesoModificar()?false:true);
			/*ANULAR*/
			anular=(accesoEliminar()?false:true);
			HabiltaBotones(nuevo, Modificar, Guardar, Cancelar, anular, cerrar);
						
			/*Carga Pilo, Copiloto, Tripulante y Bus en Ingreso de Datos.*/
			Util.seleccionarValorItemCombo(Personal.class, cmbPiloto, programacionServicio.getPiloto().getId());
			Util.seleccionarValorItemCombo(Personal.class, cmbCopiloto, programacionServicio.getCopiloto().getId());
			Util.seleccionarValorItemCombo(Personal.class, cmbTripulante, programacionServicio.getTripulante().getId());
			if(programacionServicio.getCopilotoAuxiliar()!=null)
				Util.seleccionarValorItemCombo(Personal.class, cmbCopilotoAuxiliar, programacionServicio.getCopilotoAuxiliar().getId());
			else
				cmbCopilotoAuxiliar.setText("");
			Util.seleccionarValorItemCombo(Bus.class, cmbbus, programacionServicio.getBus().getId());
			
			//Carga el previo
			onChangePrevioProgramacion();//Carga detalle del itinerario el Previo
			lblPiloto.setValue(cmbPiloto.getText());			
			lblCopiloto.setValue(cmbCopiloto.getText());
			lblCopilotoAux.setValue(cmbCopilotoAuxiliar.getText());
			lblTripulante.setValue(cmbTripulante.getText());
			lblBus.setValue(((Bus) cmbbus.getSelectedItem().getValue()).getCodigo());
//			programacionServicio=listboxItinerarios.getItemAtIndex(listboxItinerarios.getSelectedIndex()).getValue();
			textboxId.setText(programacionServicio.getId().toString());
		}
	}
	
	public void onCheckProgramadosNoProgramados(){
		Util.limpiarListbox(listboxItinerarios);		
		/*Habilta o deshabilita botones*/
		Boolean nuevo, Modificar, Guardar, Cancelar, anular, cerrar;
		nuevo=true; Modificar=true; Guardar=true; Cancelar=true; anular=true; cerrar=false;		
		HabiltaBotones(nuevo, Modificar, Guardar, Cancelar, anular, cerrar);
		limpiaPrevioProgramacion();
	}
	
	
	/**
	 * Activa o desactiva el button Nuevo
	 * @param activar : (true)se activara el button, (false) se desactivará el button
	 */
	public static void disabledNuevo(boolean disabled, Toolbarbutton btnNuevo){
		if(!(disabled)){
			btnNuevo.setImage("/resources/toolbar/mp_toolbarNuevo.png");
			btnNuevo.setStyle("cursor:pointer");
		}else{
			btnNuevo.setImage("/resources/mp_nuevoDisabled.png");
			btnNuevo.setStyle("cursor:default");
		}
		btnNuevo.setDisabled(disabled);
	}
	
	/**
	 * Activa o desactiva el button editar
	 * @param activar : (true)se activara el button, (false) se desactivará el button
	 * @param button  : button al que se activa e inactivará.
	 */
	public static void disabledEditar(boolean disabled, Toolbarbutton btnEditar){
		if(!(disabled)){
			btnEditar.setImage("/resources/toolbar/mp_toolbarModificar.png");
			btnEditar.setStyle("cursor:pointer");
		}else{
			btnEditar.setImage("/resources/mp_editarDisabled.png");
			btnEditar.setStyle("cursor:default");
		}
		btnEditar.setDisabled(disabled);
	}
	
	/**
	 * Activa o desactiva el button Cancelar
	 * @param activar : (true)se activara el button, (false) se desactivará el button
	 */
	public static void disabledCancelar(boolean disabled, Toolbarbutton btnCancelar){
		if(!(disabled)){
			btnCancelar.setImage("/resources/toolbar/mp_toolbarCancelar.png");
			btnCancelar.setStyle("cursor:pointer");
		}else{
			btnCancelar.setImage("/resources/mp_cancelarDisabled.png");
			btnCancelar.setStyle("cursor:default");
		}
		btnCancelar.setDisabled(disabled);
	}
	
	/**
	 * Activa o desactiva el button Guardar
	 * @param activar : (true)se activara el button, (false) se desactivará el button
	 */
	public static void disabledGuardar(boolean disabled, Toolbarbutton btnGuardar){
		if(!(disabled)){
			btnGuardar.setImage("/resources/toolbar/mp_toolbarGuardar.png");
			btnGuardar.setStyle("cursor:pointer");
		}else{
			btnGuardar.setImage("/resources/mp_guardarDisabled.png");
			btnGuardar.setStyle("cursor:default");
		}
		btnGuardar.setDisabled(disabled);
	}
	
	/**
	 * Activa o desactiva el button Eliminar
	 * @param activar : (true)se activara el button, (false) se desactivará el button
	 */
	public static void disabledEliminar(boolean disabled, Toolbarbutton btnElimienar){
		if(!(disabled)){
			btnElimienar.setImage("/resources/toolbar/mp_toolbarEliminar.png");
			btnElimienar.setStyle("cursor:pointer");
		}else{
			btnElimienar.setImage("/resources/mp_eliminarDisabled.png");
			btnElimienar.setStyle("cursor:default");
		}
		btnElimienar.setDisabled(disabled);
	}
	
	/**
	 * Activa o desactiva el button Cerrar
	 * @param activar : (true)se activara el button, (false) se desactivará el button
	 */
	public static void disabledCerrar(boolean disabled, Toolbarbutton btnCerrar){
		if(!(disabled)){
			btnCerrar.setImage("resources/toolbar/mp_toolbarCerrar.png");
			btnCerrar.setStyle("cursor:pointer");
		}else{
			btnCerrar.setImage("resources/mp_cerrarDisabled.png");
			btnCerrar.setStyle("cursor:default");
		}
		btnCerrar.setDisabled(disabled);
	}
	
}
