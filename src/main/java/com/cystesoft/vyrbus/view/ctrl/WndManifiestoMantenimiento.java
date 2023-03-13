package com.cystesoft.vyrbus.view.ctrl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.TreeMap;

import javax.servlet.http.HttpSession;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Session;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zul.Button;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.Iframe;
import org.zkoss.zul.Label;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Toolbarbutton;

import com.cystesoft.vyrbus.model.bean.Agencia;
import com.cystesoft.vyrbus.model.bean.Bus;
import com.cystesoft.vyrbus.model.bean.DocumentoBus;
import com.cystesoft.vyrbus.model.bean.Itinerario;
import com.cystesoft.vyrbus.model.bean.Localidad;
import com.cystesoft.vyrbus.model.bean.Manifiesto;
import com.cystesoft.vyrbus.model.bean.Personal;
import com.cystesoft.vyrbus.model.bean.ProgramacionServicio;
import com.cystesoft.vyrbus.model.bean.Rol;
import com.cystesoft.vyrbus.model.bean.TipoDocumento;
import com.cystesoft.vyrbus.model.bean.Usuario;
import com.cystesoft.vyrbus.model.bean.VentaPasaje;
import com.cystesoft.vyrbus.service.locator.ServiceLocator;
import com.cystesoft.vyrbus.service.util.Constantes;
import com.cystesoft.vyrbus.service.util.Messages;
import com.cystesoft.vyrbus.service.util.MyTime;
import com.cystesoft.vyrbus.service.util.Util;
import com.cystesoft.vyrbus.service.util.UtilData;
import com.cystesoft.vyrbus.view.ui.DlgMessage;
import com.cystesoft.vyrbus.view.ui.WndBase;
import com.cystesoft.vyrbus.view.ui.WndIFrame;

public class WndManifiestoMantenimiento extends WndBase{
	private static final long serialVersionUID = 2468732545392804234L;

	private Datebox dbFechaInicio;
	private Combobox cmbOrigen;
	private Datebox dbFechaFin;
	private Combobox cmbDestino;
	private Label lbManifiesto;
//	private Label lbItinerario;
	private Label lbFechaPartida;
	private Label lbOrigen;
	private Label lbDestino;
	private Label lbHoraPartida;
	private Label lbTripulante;
	private Label lbFechaHoraImpresion;
	private Label lbFechaHoraAnulacion;
	private Label lbBus;
	private Label lbServicio;
	private Label lbPiloto;
	private Label lbCopiloto;
	private Label lbCopilotoAux;
	private Listbox listManifiestos;
	private Button btnBuscar;
	private Combobox cmbBus;
	private Combobox cmbAgencia;

	private Iframe iFrame=new Iframe();

	@Override
	public void initComponents() {
		dbFechaInicio = (Datebox) this.getFellow("dbFechaInicio");
		cmbOrigen = (Combobox) this.getFellow("cmbOrigen");
		dbFechaFin = (Datebox) this.getFellow("dbFechaFin");
		cmbDestino = (Combobox) this.getFellow("cmbDestino");
		lbManifiesto = (Label) this.getFellow("lbManifiesto");
//		lbItinerario = (Label) this.getFellow("lbItinerario");
		lbFechaPartida = (Label) this.getFellow("lbFechaPartida");
		lbOrigen = (Label) this.getFellow("lbOrigen");
		lbDestino = (Label) this.getFellow("lbDestino");
		lbHoraPartida = (Label) this.getFellow("lbHoraPartida");
		lbTripulante = (Label) this.getFellow("lbTripulante");
		lbFechaHoraImpresion=(Label)this.getFellow("lbFechaHoraImpresion");
		lbFechaHoraAnulacion=(Label)this.getFellow("lbFechaHoraAnulacion");
		lbBus=(Label)this.getFellow("lbBus");
		lbServicio=(Label)this.getFellow("lbServicio");
		lbPiloto = (Label) this.getFellow("lbPiloto");
		lbCopiloto = (Label) this.getFellow("lbCopiloto");
		lbCopilotoAux=(Label)this.getFellow("lbCopilotoAux");
		listManifiestos = (Listbox) this.getFellow("listManifiestos");
		btnBuscar = (Button) this.getFellow("btnBuscar");
		cmbBus=(Combobox)this.getFellow("cmbBus");
		cmbAgencia=(Combobox)this.getFellow("cmbAgencia");
	}

	@Override
	public void onCreate() throws Exception {
		MyTime time = new MyTime();

		dbFechaInicio.setValue(Constantes.FORMAT_DATE.parse(time.dateServer()));
		dbFechaFin.setValue(Constantes.FORMAT_DATE.parse(time.dateServer()));

		UtilData.cargarDataCombo(cmbOrigen, Localidad.class, true);
		UtilData.cargarDataCombo(cmbDestino, Localidad.class, true);
		UtilData.cargarDataCombo(cmbBus, Bus.class, true);
		UtilData.cargarAgenciaXtipoAgencia(cmbAgencia, Constantes.ID_TIPAGE_TEPSA, true);

		/*Selecciona valores por defecto*/
		Localidad localidad = new Localidad();
		localidad.setId(getAgencia().getLocalidad().getId());
		Util.seleccionarValorItemCombo(Localidad.class, cmbOrigen, localidad.getId());
		cmbDestino.setSelectedIndex(0);

		/*Validacion por rol*/
		List<Component>lstComponents=new ArrayList<>();
		lstComponents.add(cmbAgencia);
		List<Rol>listRolesAcceeso=new ArrayList<>();
		listRolesAcceeso.add(new Rol(Constantes.ID_ROL_SUPER_USUARIO));
		listRolesAcceeso.add(new Rol(Constantes.ID_ROL_ADMIN));
		listRolesAcceeso.add(new Rol(Constantes.ID_ROL_FINANZAS));
		listRolesAcceeso.add(new Rol(Constantes.ID_ROL_FISCALIZACION));
//		listRolesAcceeso.add(new Rol(Constantes.ID_ROL_ASISTENTE_ADMIN_COMERCIAL));
		listRolesAcceeso.add(new Rol(Constantes.ID_ROL_FLOTA));
		accesoControlsByRol(lstComponents, listRolesAcceeso);

		if(cmbAgencia.isDisabled())
			Util.seleccionarValorItemCombo(Agencia.class, cmbAgencia, getAgencia().getId());
		else
			cmbAgencia.setSelectedIndex(0);


		/*Habilita/Desabilita segun la configuracion del rol del usuario*/
		/*BUSCAR*/
		btnBuscar.setDisabled(accesoConsultar()?false:true);
	}

	/**
	 * Anula Manifiesto
	 */
	public void onInactivateManifiesto(String id){
		final Long idManiefiesto=Long.valueOf(id);
		Messagebox.show(Messages.getString("WndManifiesto.question.confirmarAnulacion"), DlgMessage.NOMBREAPLICACION, DlgMessage.BTN_YESNO, Messagebox.QUESTION,DlgMessage.BTN_DEFAULT_NO, new EventListener<Event>() {
			@Override
			public void onEvent(Event e) throws Exception {
				if(e.getName().equals("onYes")){
					ServiceLocator.getManifiestoManager().inactivar(Long.valueOf(idManiefiesto));
					onSearchManifiesto();
				}
			}
		});
	}

	/**
	 * Busca Manifiesto
	 * @throws Exception
	 */
	public void onSearchManifiesto() throws Exception{
		String fechaInicial=Constantes.FORMAT_DATE.format(dbFechaInicio.getValue());
		String fechaFinal=Constantes.FORMAT_DATE.format(dbFechaFin.getValue());
		Integer idOrigen=null;
		Integer idDestino=null;
		Integer idBus=null;
		Integer idAgencia=null;

		if(cmbAgencia.getSelectedIndex()>0 && cmbAgencia.getSelectedItem().getValue() instanceof Agencia)
			idAgencia=((Agencia)cmbAgencia.getSelectedItem().getValue()).getId();
		if (cmbOrigen.getSelectedItem().getValue() instanceof Localidad)
			idOrigen=((Localidad) cmbOrigen.getSelectedItem().getValue()).getId();
		if (cmbDestino.getSelectedItem().getValue() instanceof Localidad)
			idDestino=((Localidad) cmbDestino.getSelectedItem().getValue()).getId();
		if(cmbBus.getSelectedItem().getValue() instanceof Bus)
			idBus=((Bus)cmbBus.getSelectedItem().getValue()).getId();

		clearLb();
		Util.limpiarListbox(listManifiestos);
		loadManifiesto(ServiceLocator.getManifiestoManager().buscarManifiesto(fechaInicial, fechaFinal, idOrigen, idDestino,idBus,idAgencia));
	}

	/**
	 * Selecciona Manifiesto
	 */
	public void onSelectManifiesto(){
		Manifiesto manifiesto=(Manifiesto)listManifiestos.getSelectedItem().getValue();
		lbManifiesto.setValue(manifiesto.getNumeroManifiesto());
		lbBus.setValue(manifiesto.getCodigoBus());
		lbFechaHoraImpresion.setValue(manifiesto.getFechaInsercion()!=null?(Constantes.FORMAT_DATE_TIME_24H.format(manifiesto.getFechaInsercion())):"");
		if(manifiesto.getEstadoRegistro().equals(Constantes.VALUE_INACTIVO))
			lbFechaHoraAnulacion.setValue(manifiesto.getFechaModificacion()!=null?(Constantes.FORMAT_DATE_TIME_24H.format(manifiesto.getFechaModificacion())):"");
		lbFechaPartida.setValue(Constantes.FORMAT_DATE.format(manifiesto.getItinerario().getFechaPartida()));
		lbOrigen.setValue(manifiesto.getItinerario().getRuta().getLocalidadOrigen().getDenominacion());
		lbDestino.setValue(manifiesto.getItinerario().getRuta().getLocalidadDestino().getDenominacion());
		lbServicio.setValue(manifiesto.getItinerario().getServicio()!=null?manifiesto.getItinerario().getServicio().getDenominacion():"");
		lbHoraPartida.setValue(manifiesto.getItinerario().getHoraPartida());
		lbPiloto.setValue(manifiesto.getPiloto());
		lbCopiloto.setValue(manifiesto.getCopiloto());
		lbCopilotoAux.setValue(manifiesto.getCopilotoAuxiliar()!=null?manifiesto.getCopilotoAuxiliar():"");
		lbTripulante.setValue(manifiesto.getTripulante()!=null?manifiesto.getTripulante():"");
	}

	/**
	 * Selecciona de manera automatica la localida origen en base a la agencia seleccionada.
	 * @throws Exception
	 */
	public void onChange_cmbAgencia()throws Exception{
		Integer idLocalidadOrigen=getAgencia().getId();

		if(cmbAgencia.getSelectedIndex()>0 && cmbAgencia.getSelectedItem().getValue() instanceof Agencia)
			idLocalidadOrigen=((Agencia)cmbAgencia.getSelectedItem().getValue()).getLocalidad().getId();

		Util.seleccionarValorItemCombo(Localidad.class, cmbOrigen, idLocalidadOrigen);

	}

	/**
	 *
	 */
	public void clearLb(){
		lbManifiesto.setValue("");
		lbFechaPartida.setValue("");
		lbOrigen.setValue("");
		lbDestino.setValue("");
		lbHoraPartida.setValue("");
		lbPiloto.setValue("");
		lbCopiloto.setValue("");
		lbBus.setValue("");
		lbFechaHoraImpresion.setValue("");
		lbServicio.setValue("");
		lbCopilotoAux.setValue("");
		lbTripulante.setValue("");
		lbFechaHoraAnulacion.setValue("");
	}

	/**
	 *
	 * @param lisManifiesto
	 * @throws Exception
	 */
	public void loadManifiesto(List<Manifiesto> lisManifiesto) throws Exception{
			Listitem item = null;
			Listcell cell = null;
			int x = 0;

			for (Manifiesto manifiesto : lisManifiesto){
				x += +1;
				item = new Listitem();
				cell = new Listcell((String.valueOf(x)));
				cell.setStyle("font-size:11px !important");
				item.appendChild(cell); //Correlativo

				cell = new Listcell(manifiesto.getNumeroManifiesto());
				if(manifiesto.getEstadoRegistro().equals(Constantes.VALUE_ACTIVO))
					cell.setStyle("font-size:11px !important");
				else cell.setStyle("font-size:11px !important; color:red");
				item.appendChild(cell); //Numero de Manifiesto

				cell = new Listcell(manifiesto.getCodigoBus());
				if(manifiesto.getEstadoRegistro().equals(Constantes.VALUE_ACTIVO))
					cell.setStyle("font-size:11px !important");
				else cell.setStyle("font-size:11px !important; color:red");
				item.appendChild(cell); //Numero de Bus

				cell = new Listcell(manifiesto.getItinerario().getId().toString());
				if(manifiesto.getEstadoRegistro().equals(Constantes.VALUE_ACTIVO))
					cell.setStyle("font-size:11px !important");
				else cell.setStyle("font-size:11px !important; color:red");
				item.appendChild(cell); //Itinerario

				cell = new Listcell(Constantes.FORMAT_DATE.format(manifiesto.getItinerario().getFechaPartida()));
				if(manifiesto.getEstadoRegistro().equals(Constantes.VALUE_ACTIVO))
					cell.setStyle("font-size:11px !important");
				else cell.setStyle("font-size:11px !important; color:red");
				item.appendChild(cell); //Fecha Partida

				cell = new Listcell(manifiesto.getItinerario().getHoraPartida());
				if(manifiesto.getEstadoRegistro().equals(Constantes.VALUE_ACTIVO))
					cell.setStyle("font-size:11px !important");
				else cell.setStyle("font-size:11px !important; color:red");
				item.appendChild(cell); //Hora Partida

				cell = new Listcell(manifiesto.getItinerario().getRuta().getLocalidadOrigen().getDenominacion());
				if(manifiesto.getEstadoRegistro().equals(Constantes.VALUE_ACTIVO))
					cell.setStyle("font-size:11px !important");
				else cell.setStyle("font-size:11px !important; color:red");
				item.appendChild(cell); //origen

				cell = new Listcell(manifiesto.getItinerario().getRuta().getLocalidadDestino().getDenominacion());
				if (!(manifiesto.getEstadoRegistro().equals(Constantes.VALUE_ACTIVO)))
					cell.setStyle("color:red");
				item.appendChild(cell); //Destino
				if (manifiesto.getEstadoRegistro().equals(Constantes.VALUE_ACTIVO))
					cell = new Listcell(Constantes.LABEL_ACTIVO_DESCP);
				else{
					cell = new Listcell(Constantes.LABEL_INACTIVO_DESCP);
					cell.setStyle("color:red");
				}
				item.appendChild(cell); //Estado del manifiesto

				Toolbarbutton btnAnular= new Toolbarbutton();
				btnAnular.setDisabled(!manifiesto.getEstadoRegistro().equals(Constantes.VALUE_ACTIVO));

				if(!btnAnular.isDisabled()){
					Date fechaActual=Constantes.FORMAT_DATE.parse(Constantes.FORMAT_DATE.format(new Date()));
					Date fechaManifiesto=manifiesto.getItinerario().getFechaPartida();
					if(fechaManifiesto.getTime()!=fechaActual.getTime()) {
						if( fechaManifiesto.getTime()<fechaActual.getTime())
							btnAnular.setDisabled(true);
					}
				}

				/************************************************************************/
				if(!(btnAnular.isDisabled())){
					/*Da acceso a anular solamente al rol super Usuario.*/
					List<Component> lstComponents=new ArrayList<>();
					lstComponents.add(btnAnular);
					accesoControlsRolSuperUsuario(lstComponents);
				}
				/************************************************************************/

				btnAnular.setTooltiptext(manifiesto.getEstadoRegistro().equals(Constantes.VALUE_ACTIVO)?"Anular manifiesto":"");
				btnAnular.setImage("resources/mp_anular.png");
				btnAnular.setId(manifiesto.getId().toString());
				cell= new Listcell();
				cell.appendChild(btnAnular);
				item.appendChild(cell);
				btnAnular.addEventListener(Events.ON_CLICK, new EventListener<Event>() {
					@Override
					public void onEvent(Event e) throws Exception {
						onInactivateManifiesto(e.getTarget().getId());
					}
				});

				Toolbarbutton btnPrevio=new Toolbarbutton();
//				btnPrevio.setDisabled(!manifiesto.getEstadoRegistro().equals(Constantes.VALUE_ACTIVO));
				btnPrevio.setImage("resources/mp_pdf.png");
				btnPrevio.setTooltiptext("Visualizar en formato pdf");
//				btnPrevio.setTooltiptext(manifiesto.getEstadoRegistro().equals(Constantes.VALUE_ACTIVO)?"Pre-visualizar Manifiesto":"");
				btnPrevio.setId(manifiesto.getId().toString());
				btnPrevio.setAutodisable("self");
				cell.appendChild(btnPrevio);
				item.appendChild(cell);
				btnPrevio.addEventListener(Events.ON_CLICK,new EventListener<Event>() {
					@Override
					public void onEvent(Event event) throws Exception {
						// TODO Auto-generated method stub
						previoManifiesto(false,Long.valueOf(event.getTarget().getId()));
					}
				});

				Toolbarbutton btnImprimir=new Toolbarbutton();
				btnImprimir.setDisabled(!manifiesto.getEstadoRegistro().equals(Constantes.VALUE_ACTIVO));
				btnImprimir.setImage("resources/mp_imprimir.png");
				btnImprimir.setId(manifiesto.getId().toString());
				btnImprimir.setTooltiptext(manifiesto.getEstadoRegistro().equals(Constantes.VALUE_ACTIVO)?"Imprimir Manifiesto":"");
				btnImprimir.setAutodisable("self");
				cell.appendChild(btnImprimir);
				item.appendChild(cell);
				btnImprimir.addEventListener(Events.ON_CLICK, new EventListener<Event>() {
					@Override
					public void onEvent(Event event) throws Exception {
						// TODO Auto-generated method stub
						previoManifiesto(true,Long.valueOf(event.getTarget().getId()));
					}
				});

//				Toolbarbutton btnExportar=new Toolbarbutton();
//				btnExportar.setDisabled(!manifiesto.getEstadoRegistro().equals(Constantes.VALUE_ACTIVO));
//				btnExportar.setImage("resources/mp_excel.png");
//				btnExportar.setId(manifiesto.getId().toString());
//				btnExportar.setTooltiptext(manifiesto.getEstadoRegistro().equals(Constantes.VALUE_ACTIVO)?"Imprimir Manifiesto":"");
//				cell.appendChild(btnExportar);
//				item.appendChild(cell);
//				btnExportar.addEventListener(Events.ON_CLICK, new EventListener<Event>() {
//					@Override
//					public void onEvent(Event event) throws Exception {
//						// TODO Auto-generated method stub
//						exportar(Long.valueOf(event.getTarget().getId()));
//					}
//				});
//				btnExportar.setDisabled(true);
				item.setValue(manifiesto);
				listManifiestos.appendChild(item);
			}
	}

//	/**
//	 * Exporta el manifiesto ha excel
//	 * @param idManifiesto
//	 * @throws Exception
//	 */
//	public void exportar(Long idManifiesto)throws Exception{
//
//	}


	/**
	 * Pre-visualiza o imprime el manifiesto
	 * @param imprimir : true impre manifiesto, false pre-visualiza el manifiesto.
	 * @throws Exception
	 */
	public void previoManifiesto(Boolean imprimir, Long idManifiesto) throws Exception{
		iFrame.detach();
		TreeMap<String, Object>criteriosBusqueda=new TreeMap<>();
		criteriosBusqueda.put("id", idManifiesto);
		List<Manifiesto> lstResult= ServiceLocator.getManifiestoManager().buscarPorX(criteriosBusqueda, null);
		Manifiesto manifiesto=lstResult.get(0);
		Itinerario itinerario=ServiceLocator.getItinerarioManager().buscarPorId(manifiesto.getItinerario().getId());
		List<VentaPasaje> listPasajero=ServiceLocator.getManifiestoManager().consultaPasajeros(manifiesto.getItinerario().getId(),null);
		Bus bus=ServiceLocator.getBusManager().buscarPorId(manifiesto.getBus().getId().longValue());
		/*busca el numero de la tarjeta de habilitacion del bus*/
		criteriosBusqueda=new TreeMap<>();
		criteriosBusqueda.put("bus", bus);
		criteriosBusqueda.put("tipoDocumento", new TipoDocumento(Constantes.ID_TIPDOC_TARJETA_CIRCULACION));
		criteriosBusqueda.put("estadoRegistro", Constantes.VALUE_ACTIVO);
		List<DocumentoBus> lstDocBus=ServiceLocator.getDocumentoBusManager().buscarPorX(criteriosBusqueda, null);
		DocumentoBus documentoBus=lstDocBus.get(0);
		/*busca la programacion, para luego optener el piloto,copiloto y copilotoAux*/
		criteriosBusqueda=new TreeMap<>();
		criteriosBusqueda.put("itinerario", itinerario);
		criteriosBusqueda.put("estadoRegistro", Constantes.VALUE_ACTIVO);
		List<ProgramacionServicio>lstProgramacion=ServiceLocator.getProgramacionServiciosManager().buscarPorX(criteriosBusqueda, null);
		ProgramacionServicio programacionServicio=lstProgramacion.get(0);
		Personal piloto=programacionServicio.getPiloto();
		Personal copiloto=programacionServicio.getCopiloto();
		Personal copilotoAux=(programacionServicio.getCopilotoAuxiliar()!=null?programacionServicio.getCopilotoAuxiliar():null);
		Personal tripulante=programacionServicio.getTripulante();

		/* Realiza tratamiento de los asientos*/
		List<VentaPasaje>listPasajeros=new ArrayList<>();
//		for(int x=0; x<itinerario.getServicio().getNumeroAsientosPiso1(); x++){
		for(int x=0; x<(itinerario.getServicio().getNumeroAsientosPiso1()+(itinerario.getServicio().getNumeroAsientosPiso2()!=null?itinerario.getServicio().getNumeroAsientosPiso2():0)); x++){
			Boolean asientoVendido=false;
			VentaPasaje ventaPasaje=new VentaPasaje();
			ventaPasaje.setNumeroAsiento(x+1);
			for(VentaPasaje venta:listPasajero){
				if(ventaPasaje.getNumeroAsiento().intValue()==venta.getNumeroAsiento().intValue()){
					listPasajeros.add(venta);
					asientoVendido=true;
//					break;
				}
			}
			if(!asientoVendido)
				listPasajeros.add(ventaPasaje);
		}


		/*Busca la serie del manifiesto para obtener la agencia a quien le corresponde*/
//		String numeroManifiesto=manifiesto.getNumeroManifiesto();
//		criteriosBusqueda=new TreeMap<>();
//		criteriosBusqueda.put("serie", numeroManifiesto.substring(0,numeroManifiesto.indexOf("-")));
//		criteriosBusqueda.put("tipoComprobante", new TipoComprobante(Constantes.ID_TIPCOM_MANIFIESTO_PAX));
//		criteriosBusqueda.put("estadoRegistro", Constantes.VALUE_ACTIVO);

		/*Busca usuario que lo emitio*/
		Usuario usuario=ServiceLocator.getUsuarioManager().buscarUsuarioPorLogin(manifiesto.getUsuarioInsercion(), null);

//		/*Busca agencia que la emitio*/
//		String numeroManifiesto=manifiesto.getNumeroManifiesto();
//		Integer serie=Integer.valueOf(numeroManifiesto.substring(0,numeroManifiesto.indexOf("-")));
//		criteriosBusqueda=new TreeMap<>();
//		criteriosBusqueda.put("serie", serie);
//		criteriosBusqueda.put("tipoComprobante", new TipoComprobante(Constantes.ID_TIPCOM_MANIFIESTO_PAX));
//		List<EspecieValorada> resultEspVal=ServiceLocator.getEspecieValoradaManager().buscarPorX(criteriosBusqueda, null);
//		EspecieValorada especieValorada=resultEspVal.get(0);

		int totalAsientos= itinerario.getServicio().getNumeroAsientosPiso1();
		if(itinerario.getServicio().getNumeroAsientosPiso2()!=null)
			totalAsientos += itinerario.getServicio().getNumeroAsientosPiso2();

		Session session=getDesktop().getSession();
		HttpSession httpSession=(HttpSession)session.getNativeSession();
		httpSession.setAttribute("listPasajeros", listPasajeros);
		httpSession.setAttribute("numeroManifiesto", manifiesto.getNumeroManifiesto());
		httpSession.setAttribute("usuario", usuario.toString());
		httpSession.setAttribute("agencia",manifiesto.getAgencia().toString());
		httpSession.setAttribute("itinerario", itinerario.getId().toString());
		httpSession.setAttribute("origen", itinerario.getRuta().getOrigen());
		httpSession.setAttribute("destino", itinerario.getRuta().getDestino());
		httpSession.setAttribute("piloto", piloto.toString());
		httpSession.setAttribute("copiloto", copiloto.toString());
		httpSession.setAttribute("copilotoAux", (copilotoAux!=null?copilotoAux.toString():""));
		httpSession.setAttribute("licencia1", piloto.getLicencia());
		httpSession.setAttribute("licencia2", copiloto.getLicencia());
		httpSession.setAttribute("licencia3", (copilotoAux!=null?copilotoAux.getLicencia():""));
		if(tripulante!=null){
			httpSession.setAttribute("tripulante", tripulante.toString());
			httpSession.setAttribute("dniTripulante",tripulante.getNroDocumento()!=null?tripulante.getNroDocumento():"");
		}
		else{
			httpSession.setAttribute("tripulante", "");
			httpSession.setAttribute("dniTripulante","");
		}
		httpSession.setAttribute("bus", bus.getCodigo());
		httpSession.setAttribute("placaBus", bus.getNumeroPlaca());
		httpSession.setAttribute("tarjetaHabilitacion", documentoBus.getNumeroDocumento());
		httpSession.setAttribute("salida",Constantes.FORMAT_DATE.format(itinerario.getFechaPartida())+" "+itinerario.getHoraPartida());
		httpSession.setAttribute("servicio", itinerario.getServicio().getDenominacion());
//		httpSession.setAttribute("totalAsientos", itinerario.getServicio().getNumeroAsientosPiso1().toString());
		httpSession.setAttribute("totalAsientos", String.valueOf(totalAsientos));
		httpSession.setAttribute("numeroAutoSunat", manifiesto.getAutorizacionSunat());
		httpSession.setAttribute("totalPasajeros", String.valueOf(listPasajero.size()));


//		httpSession.setAttribute("tipoAgencia", tipoAgencia);
//		httpSession.setAttribute("usuario", cmbCounter.getSelectedItem().getLabel().toUpperCase());
//		httpSession.setAttribute("agencia", cmbAgencia.getSelectedItem().getLabel().toUpperCase());
//		httpSession.setAttribute("fechaInicio", Constantes.FORMAT_DATE.format(dtbxFechaInicio.getValue()));
//		httpSession.setAttribute("fechaFinal", Constantes.FORMAT_DATE.format(dtbxFechaFin.getValue()));
//		httpSession.setAttribute("comision", porComision);
//		httpSession.setAttribute("igv", igv);

		final WndIFrame iFrame = new WndIFrame();
        iFrame.btnCerrar.setVisible(false);
        iFrame.oThisWindow.setTitle("MANIFIESTO DE PASAJEROS");
        iFrame.oThisWindow.setClosable(true);
		iFrame.setSrc("reporteManifisto.zul");
		iFrame.setWidth("810");
		iFrame.setheight("700");
		iFrame.loadiframe();
		this.appendChild(iFrame);
		iFrame.setMode("modal");


//		String src="";
//		final WndIFrame iFrame = new WndIFrame();
//
//		TreeMap<String, Object>criteriosBusqueda=new TreeMap<String,Object>();
//		criteriosBusqueda.put("id", idManifiesto);
//		List<Manifiesto> lstManifiesto=ServiceLocator.getManifiestoManager().buscarPorX(criteriosBusqueda, null);
//		if(lstManifiesto.size()>0){
//			Manifiesto manifiesto=lstManifiesto.get(0);
//			Bus bus = ServiceLocator.getManifiestoManager().consultaItinerario(manifiesto.getItinerario().getId(), "", manifiesto.getItinerario().getRuta().getDestino()).getBus();
//			Itinerario itinerario=manifiesto.getItinerario();
//			itinerario.setBus(bus);
//			manifiesto.setItinerario(itinerario);
//
//			String ROTULO_SUNAT="SUNAT";
//
//			CreateDocument.creaManifiesto_ListPax(manifiesto,getUsuario(),getAgencia(),true,ROTULO_SUNAT);
//			src = Constantes.URL_FORMATOS_MANIFIESTOS +"MANPAX-"+ manifiesto.getItinerario().getId()+"-"+ROTULO_SUNAT+".txt";
//			iFrame.setWidth("1115");
//
//			if(imprimir){
//				Window win = (Window)Executions.createComponents("imprimir.zul", null, null);
//				win.setAttribute("formato", WndImprimir.FORMAT_MANIFIESTO_PAX);
//				win.setAttribute("msg", "Imprimiendo el Manifiesto de Pasajeros... ");
//				win.setAttribute("urlDocumento", src);
//				win.doPopup();
//			}else{
//				iFrame.setSrc(src);
//				iFrame.setheight("580");
//				iFrame.loadiframe();
//				iFrame.btnCerrar.setVisible(false);
//				iFrame.oThisWindow.setClosable(true);
//				iFrame.setTitle("MANIFIESTO DE PASAJEROS. "+manifiesto.getItinerario().getRuta());
//				this.appendChild(iFrame);
//				iFrame.setMode("modal");
//			}
//		}
	}



}
