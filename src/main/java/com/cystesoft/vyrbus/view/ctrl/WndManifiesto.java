package com.cystesoft.vyrbus.view.ctrl;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.TreeMap;

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
import org.zkoss.zul.Grid;
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
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Toolbarbutton;
import org.zkoss.zul.Window;

import com.cystesoft.vyrbus.model.bean.Agencia;
import com.cystesoft.vyrbus.model.bean.Bus;
import com.cystesoft.vyrbus.model.bean.DetalleManifiesto;
import com.cystesoft.vyrbus.model.bean.DetalleManifiestoID;
import com.cystesoft.vyrbus.model.bean.DocumentoBus;
import com.cystesoft.vyrbus.model.bean.EspecieValorada;
import com.cystesoft.vyrbus.model.bean.Itinerario;
import com.cystesoft.vyrbus.model.bean.Manifiesto;
import com.cystesoft.vyrbus.model.bean.ProgramacionServicio;
import com.cystesoft.vyrbus.model.bean.Usuario;
import com.cystesoft.vyrbus.model.bean.UsuarioHardware;
import com.cystesoft.vyrbus.model.bean.VentaPasaje;
import com.cystesoft.vyrbus.service.exceptions.BusNullException;
import com.cystesoft.vyrbus.service.exceptions.CertificadoHabilitacionBusNullException;
import com.cystesoft.vyrbus.service.exceptions.CompPendientesXImprimirException;
import com.cystesoft.vyrbus.service.exceptions.ManifiestoCorrelativoAgotadoException;
import com.cystesoft.vyrbus.service.exceptions.ManifiestoDuplicateException;
import com.cystesoft.vyrbus.service.exceptions.ManifiestoImplesoException;
import com.cystesoft.vyrbus.service.locator.ServiceLocator;
import com.cystesoft.vyrbus.service.util.Constantes;
import com.cystesoft.vyrbus.service.util.CreateDocument;
import com.cystesoft.vyrbus.service.util.Messages;
import com.cystesoft.vyrbus.service.util.Util;
import com.cystesoft.vyrbus.service.util.UtilData;
import com.cystesoft.vyrbus.view.ui.DlgMessage;
import com.cystesoft.vyrbus.view.ui.WndBase;
import com.cystesoft.vyrbus.view.ui.WndComprobantesPendientesXAsociarBoleto;
import com.cystesoft.vyrbus.view.ui.WndIFrame;
import com.cystesoft.vyrbus.view.ui.WndImprimir;
import com.cystesoft.vyrbus.view.ui.WndSeleccionaItinerario;
import com.cystesoft.vyrbus.view.ui.WndVerMapaBus;

/**
 *
 * @author José Abanto.
 */
public class WndManifiesto extends WndBase {
	private static final long serialVersionUID = -3533022967415262311L;

	public Textbox txtItinerario;
	private Button cmdBuscarItinerario;
	private Label lbRuta;
	private Label lbBus;
	private Label lbFechaSalida;
	private Label lbFechaLlegada;
	private Label lbPiloto;
	private Label lbServicio;
	private Label lbcopiloto;
	private Label lbCopilotoAux;
	private Label lbTripulante;
	private Listbox ListPasajeros;
	private Combobox cmbPuntocontrol;
	private Listbox listDetaPaxRuta;
	private Listbox listPaxFormaPago;
//	private Label lbTotalPasajeros;
	private Combobox cmbImprimir;
	private Button cmdImprimir;
	private Button cmdPrevio;

	private Toolbarbutton tbbVerMapa;
	private Toolbarbutton tbbVerPax;

	List<VentaPasaje> lisOpabilidad=null;
	private EspecieValorada especieValoradaSunat = null;

	private Window wndVerPasajero = null;
	private Window wndVerDetalle = null;
	private Itinerario itinerario=null;
	private Integer porcentajeCorrelativoManifiesto=0;

	String ROTULO_SUNAT="SUNAT";
	String ROTULO_TRANSPORTISTA="TRANSPORTISTA";
	String ROTULO_ARCHIVO="ARCHIVO";

	/*Tipos de impresion Manifiesto*/
	private final Integer IMPRESION_CARPETA_DESPACHO=0;
	private final Integer IMPRESION_MANIFIESTO_PASAJEROS=1;
	private final Integer IMPRESION_LISTADO_PASAJEROS=2;

	@Override
	public void initComponents() {
		txtItinerario=(Textbox)this.getFellow("txtItinerario");
		cmdBuscarItinerario=(Button)this.getFellow("cmdBuscarItinerario");
		lbRuta=(Label)this.getFellow("lbRuta");
		lbBus=(Label)this.getFellow("lbBus");
		lbFechaSalida=(Label)this.getFellow("lbFechaSalida");
		lbFechaLlegada=(Label)this.getFellow("lbFechaLlegada");
		lbPiloto=(Label)this.getFellow("lbPiloto");
		lbServicio=(Label)this.getFellow("lbServicio");
		lbcopiloto=(Label)this.getFellow("lbcopiloto");
		lbCopilotoAux=(Label)this.getFellow("lbCopilotoAux");
		lbTripulante=(Label)this.getFellow("lbTripulante");
		ListPasajeros=(Listbox)this.getFellow("ListPasajeros");
		cmbPuntocontrol=(Combobox)this.getFellow("cmbPuntocontrol");
		listDetaPaxRuta=(Listbox)this.getFellow("listDetaPaxRuta");
		listPaxFormaPago=(Listbox)this.getFellow("listPaxFormaPago");
//		lbTotalPasajeros = (Label) this.getFellow("lbTotalPasajeros");
		cmbImprimir=(Combobox)this.getFellow("cmbImprimir");
		cmdPrevio=(Button)this.getFellow("cmdPrevio");
		cmdImprimir=(Button)this.getFellow("cmdImprimir");
		tbbVerMapa=(Toolbarbutton)this.getFellow("tbbVerMapa");
		tbbVerPax=(Toolbarbutton)this.getFellow("tbbVerPax");

		//Previo:
		cmdPrevio.addEventListener(Events.ON_CLICK,new EventListener<Event>() {
			@Override
			public void onEvent(Event event) throws Exception {

				//Previo Carpeta de Despacho
				if(cmbImprimir.getSelectedItem().getValue().equals(IMPRESION_CARPETA_DESPACHO)){
					impresionesManifiesto(IMPRESION_CARPETA_DESPACHO,true,"",null);

				//Previo Manifiesto de Pasajeros
				}else if(cmbImprimir.getSelectedItem().getValue().equals(IMPRESION_MANIFIESTO_PASAJEROS)){
					impresionesManifiesto(IMPRESION_MANIFIESTO_PASAJEROS,true,ROTULO_SUNAT,null);

				//Previo Listado de pasajeros
				}else if(cmbImprimir.getSelectedItem().getValue().equals(IMPRESION_LISTADO_PASAJEROS)){
					impresionesManifiesto(IMPRESION_LISTADO_PASAJEROS,true,"",null);
				}
			}
		});

		//Imprimir
		cmdImprimir.addEventListener(Events.ON_CLICK,new EventListener<Event>() {
			@Override
			public void onEvent(Event event) throws Exception {
				//Imprime Carpeta de Despacho
				if(cmbImprimir.getSelectedItem().getValue().equals(IMPRESION_CARPETA_DESPACHO)){
					impresionesManifiesto(IMPRESION_CARPETA_DESPACHO,false,"",null);

				//Graba manifiesto y luego lo imprime
				}else if(cmbImprimir.getSelectedItem().getValue().equals(IMPRESION_MANIFIESTO_PASAJEROS)){
					GrabaManifiesto();

				//Imprime Listado de pasajeros
				}else if(cmbImprimir.getSelectedItem().getValue().equals(IMPRESION_LISTADO_PASAJEROS)){
					impresionesManifiesto(IMPRESION_LISTADO_PASAJEROS,false,"",null);
				}
			}
		});
	}

	@Override
	public void onCreate() throws Exception {
		enlazarItinerario(cmdBuscarItinerario);
		cargarTipoImpresionManifiesto();
//		//recupera la agencia asociada al usuario hardware
//		lstUsuarioHardware= UtilData.agenciaUsuarioHardware(UtilData.getCodigoUsuarioHardware());
		/*recupera datos autorizados por la sunat para la emisiï¿½n del manifiesto.*/
		loadCorrelativosManifiesto();

		cmdImprimir.setDisabled(true);
		cmdPrevio.setDisabled(true);

		cmbImprimir.setDisabled(true);
		cmbImprimir.setSelectedIndex(-1);

		tbbVerMapa.setDisabled(true);
		tbbVerPax.setDisabled(true);
	}

	/**
	 * Graba Manifiesto
	 * @throws Exception
	 */
	public void GrabaManifiesto() throws Exception{
		ArrayList<VentaPasaje>listCompPendientesXImprimir = new ArrayList<>();
		try{
			/*recupera datos autorizados por la sunat para la emisiï¿½n del manifiesto.*/
			loadCorrelativosManifiesto();
			/*Correlativos para el Manifiesto Agotados*/
			if (porcentajeCorrelativoManifiesto>=100)
				throw new ManifiestoCorrelativoAgotadoException();
			/*Alerta al usuario si el correlativo del manifiesto es mayor o igual al 80%*/
			if (porcentajeCorrelativoManifiesto >=Constantes.ALERTAR_ENVIO_MANIFIESTOS)
				DlgMessage.information(Messages.getString("WndManifiesto.information.utilizacionCorrelativosManifiesto")+ " " +porcentajeCorrelativoManifiesto + " %");
			/*Valida si el itinerario esta programado*/
			if (itinerario.getBus() ==null)
				throw new BusNullException();
			else if (cmbImprimir.getSelectedItem().getValue().equals(IMPRESION_MANIFIESTO_PASAJEROS)){
				/*Verifica si el manifiesto ya fue impreso*/
				Manifiesto manifiesto =validaManifiestoImpreso(new Long(txtItinerario.getText()));
				if (manifiesto!=null){
					DlgMessage.information(Messages.getString("El Bus "+manifiesto.getCodigoBus()+ " esta asociado al manifiesto "+
															  manifiesto.getNumeroManifiesto()+ " del día "+ Constantes.FORMAT_DATE.format(manifiesto.getFechaInsercion())+
															  " a la(s) "+ Constantes.FORMAT_TIME.format(manifiesto.getFechaInsercion())+ " horas"+
															  " conducido por "+manifiesto.getPiloto()+ " manifestado por "+
															  manifiesto.getUsuarioInsercion()+"."+ " No puede imprimir otro manifiesto."));
					throw new ManifiestoImplesoException();
				}else if (itinerario.getBus().getDocumentoBus() ==null) //Valida si el bus tiene configurada la
					throw new CertificadoHabilitacionBusNullException();

				/* Valida los comprobantes pendientes por asociar a un boleto de viaje*/
				for(int i=0; i < ListPasajeros.getItems().size(); i++){
					VentaPasaje pasaje=new VentaPasaje();
					pasaje=ListPasajeros.getItemAtIndex(i).getValue();
					/*###BEGIN 15/09/2016*/
					if ( !((pasaje.getTipoComprobante().getId().equals(Constantes.ID_TIPCOM_BOLETO_VIAJE)) ||
							(pasaje.getTipoComprobante().getId().equals(Constantes.ID_TIPCOM_FACTURA)) ||
							(pasaje.getTipoComprobante().getId().equals(Constantes.ID_TIPCOM_BOLETA_VENTA))))
						listCompPendientesXImprimir.add(pasaje);
					//### END CUSTOM 15/09/2016
//					if(itinerario.getTipoItinerario().getId().intValue()==Constantes.ID_TIPITI_ESPECIAL){
//						if ( !((pasaje.getTipoComprobante().getId().equals(Constantes.ID_TIPCOM_BOLETO_VIAJE)) || (pasaje.getTipoComprobante().getId().equals(Constantes.ID_TIPCOM_FACTURA)) ))
//							listCompPendientesXImprimir.add(pasaje);
//					}else{
//						if (!(pasaje.getTipoComprobante().getId().equals(Constantes.ID_TIPCOM_BOLETO_VIAJE)))
//							listCompPendientesXImprimir.add(pasaje);
//					}

				}

				if(listCompPendientesXImprimir.size()>0)
					throw new CompPendientesXImprimirException();
				else{
					/*Solicita confirmación de usuario.*/
					Messagebox.show(Messages.getString("WndManifiesto.question.confirmarImpresion"), DlgMessage.NOMBREAPLICACION, DlgMessage.BTN_YESNO, Messagebox.QUESTION,DlgMessage.BTN_DEFAULT_NO, new EventListener<Event>() {
					@Override
					public void onEvent(Event e) throws Exception {
						if(e.getName().equals("onYes")){
							try{
								itinerario.setId(new Long(txtItinerario.getText()));
								ProgramacionServicio programacion=itinerario.getBus().getProgramacionServicio();

								DocumentoBus documentoBus = new DocumentoBus();
								documentoBus.setNumeroDocumento(itinerario.getBus().getDocumentoBus().getNumeroDocumento());

								Bus bus = new Bus();
								bus.setId(itinerario.getBus().getId());
								bus.setCodigo(itinerario.getBus().getCodigo());
								bus.setNumeroPlaca(itinerario.getBus().getNumeroPlaca());
								bus.setDocumentoBus(documentoBus);

								Manifiesto manifiesto = new Manifiesto();
								manifiesto.setItinerario(itinerario);
								manifiesto.setBus(bus);
								manifiesto.setCodigoBus(bus.getCodigo());
//								manifiesto.setNumeroManifiesto(especieValoradaSunat.getSerie()+"-"+especieValoradaSunat.getCorrelativoActual());
								manifiesto.setNumeroManifiesto(especieValoradaSunat.toString());
								manifiesto.setAutorizacionSunat(especieValoradaSunat.getAutorizacionSunat());
								manifiesto.setPiloto(programacion.getPiloto().toString());
								manifiesto.setCopiloto(programacion.getCopiloto().toString());
								if(programacion.getCopilotoAuxiliar()!=null)
									manifiesto.setCopilotoAuxiliar(programacion.getCopilotoAuxiliar().toString());
								//
								if(programacion.getTripulante() != null)
									manifiesto.setTripulante(programacion.getTripulante().toString());
//								manifiesto.setPiloto(lbPiloto.getValue().toString().substring(1).trim());
//								manifiesto.setCopiloto(lbcopiloto.getValue().toString().substring(1).trim());
								manifiesto.setCertificadoHabilitacion(bus.getDocumentoBus().getNumeroDocumento());
								manifiesto.setPlaca(bus.getNumeroPlaca().toString());
								manifiesto.setAgencia(getAgencia());
								manifiesto.setEstadoRegistro(Constantes.VALUE_ACTIVO);
								/*Guarda Manifiesto*/
								UtilData.auditarRegistro(manifiesto, getUsuario(), Executions.getCurrent());
								ServiceLocator.getManifiestoManager().guarda(manifiesto);

								/*Guarda detalle del Manifiesto*/
								for (Listitem item: ListPasajeros.getItems()){
									VentaPasaje ventaPasaje=item.getValue();
									DetalleManifiesto detalleManifiesto=new DetalleManifiesto();
									DetalleManifiestoID detalleManifiestoID= new DetalleManifiestoID();

									detalleManifiestoID.setIdManifiesto(manifiesto.getId());
									detalleManifiestoID.setIdVentaPasaje(ventaPasaje.getId());
									detalleManifiesto.setDetalleManifiestoID(detalleManifiestoID);
									detalleManifiesto.setEstadoRegistro(Constantes.VALUE_ACTIVO);
									UtilData.auditarRegistro(detalleManifiesto, getUsuario(), Executions.getCurrent());
									ServiceLocator.getDetalleManifiestoManager().guradar(detalleManifiesto);
								}
								//Imrime el manifiesto
								cmdImprimir.setDisabled(true);
								impresionesManifiesto(IMPRESION_MANIFIESTO_PASAJEROS,false,"",manifiesto);

								/*Actualiza correlativo manifiesto*/
								UtilData.auditarRegistro(especieValoradaSunat, true, getUsuario(), Executions.getCurrent());
								ServiceLocator.getManifiestoManager().updateCorrelativo(especieValoradaSunat, manifiesto);
							}catch(ManifiestoDuplicateException md){
								DlgMessage.information(Messages.getString("WndManifiesto.information.DuplicateManifiesto"));
							}
						}
					}
				});
			 }
		}

		}catch(ManifiestoCorrelativoAgotadoException mcaex){
			DlgMessage.information(Messages.getString("WndManifiesto.information.correlativosAgotados"));
		}catch (BusNullException bnex) {
			DlgMessage.information(Messages.getString("WndManifiesto.information.servicioNoProgramado"));
		}catch(CertificadoHabilitacionBusNullException chbnex){
			DlgMessage.information("El Bus "+ lbBus.getValue().substring(1)+ " "+Messages.getString("WndManifiesto.information.noTieneCertificadoHabilitacionBus"));
		}catch(ManifiestoImplesoException miex){
		}catch (CompPendientesXImprimirException cxie){
			enlazaCompPendientesXAsociarBoleto(listCompPendientesXImprimir);
			DlgMessage.information(Messages.getString("WndManifiesto.information.comprobantesPendientesPorImprimir"));
		}catch (Exception ex) {
			DlgMessage.error(this.getClass().getName()+" "+ex.getMessage());
			ex.printStackTrace();
		}
	}


	/**
	 *
	 * @throws Exception
	 */
	public void onRefresh() throws Exception{
		if (!(txtItinerario.getText().trim().isEmpty()))
			cargarManifiesto(new Long(txtItinerario.getText()), itinerario.getRuta().getOrigen(),itinerario.getRuta().getDestino());
	}

	/**
	 * Recupera datos autorizados por la sunat para la emisiï¿½n del manifiesto.
	 * @throws Exception
	 */
	private void loadCorrelativosManifiesto() throws Exception{
//		/*recupera datos autorizado por la Sunat, segï¿½n la agencia*/
//		//especieValoradaSunat = new EspecieValorada();
//		if (lstUsuarioHardware.size() >0){
//			Agencia agencia =  new Agencia();
//			agencia.setId(lstUsuarioHardware.get(0).getAgencia().getId());
			especieValoradaSunat =  ServiceLocator.getManifiestoManager().consultaAutorizacionSunat(getAgencia().getId());
			/*Calcula el porcenaje de correlativos utilizados de los manifiestos*/
			porcentajeCorrelativoManifiesto = (int) UtilData.porcentajeCorrelativoManifiesto(getAgencia());
//		}
	}

	/**
	 *
	 * @throws Exception
	 */
	public void onChangePtoControl() throws Exception{
		String origen= ((Agencia)cmbPuntocontrol.getSelectedItem().getValue()).getLocalidad().getDenominacion();
		if(itinerario!=null)
			cargarItinerario(itinerario.getId(), origen,itinerario.getRuta().getDestino());
//		MuetrasItinerario(Long.valueOf(txtItinerario.getText()), origen,lbDestino.getValue().substring(1).trim());
		cmbImprimir.setFocus(true);
	}


	/**
	 * Carga datos del itinerario.
	 * @param idItinerario	: identificador del itinerario.
	 * @param tramoOrigen 	: tramo origen
	 * @param tramoDestino	: tramo destino
	 * @throws Exception
	 */
	public  void cargarItinerario(Long idItinerario, String tramoOrigen, String tramoDestino) throws Exception{
		lbCopilotoAux.setValue("");
		itinerario =  new Itinerario();
		itinerario = ServiceLocator.getManifiestoManager().consultaItinerario(idItinerario, tramoOrigen, tramoDestino);

		txtItinerario.setText(idItinerario.toString());
		lbRuta.setValue(itinerario.getRuta().toString());
		/*Valida si el itinerario esta o no asiciado a un bus*/
		if (itinerario.getBus() !=null){
			lbBus.setValue(itinerario.getBus().getCodigo());
			lbPiloto.setValue(itinerario.getBus().getProgramacionServicio().getPiloto().toString());
			lbcopiloto.setValue(itinerario.getBus().getProgramacionServicio().getCopiloto().toString());
			if(itinerario.getBus().getProgramacionServicio().getCopilotoAuxiliar()!=null)
				lbCopilotoAux.setValue(itinerario.getBus().getProgramacionServicio().getCopilotoAuxiliar().toString());
			//Agregado por MAOE 27/06/2021, por PANDEMIA para TRANSMAR
			if(itinerario.getBus().getProgramacionServicio().getTripulante() != null)
				lbTripulante.setValue(itinerario.getBus().getProgramacionServicio().getTripulante().toString());
		}
		lbFechaSalida.setValue(Constantes.FORMAT_DATE.format(itinerario.getFechaPartida())+" "+itinerario.getHoraPartida());
		lbFechaLlegada.setValue(Constantes.FORMAT_DATE.format( itinerario.getFechaLlegada())+" "+itinerario.getHoraLlegada());
		lbServicio.setValue(itinerario.getServicio().getDenominacion());
	}

	/**
	 *
	 */
	@Override
	public void onClose(){
		closeTabWindow();
	}


	/**
	 * Limpia controles
	 */
	public void limpiaItinerario(){
		lbRuta.setValue(null);
//		lbDestino.setValue(null);
		lbBus.setValue(null);
		lbFechaSalida.setValue(null);
		lbFechaLlegada.setValue(null);
		lbPiloto.setValue(null);
//		lbHoraSalida.setValue(null);
//		lbHoraLlegada.setValue(null);
		lbcopiloto.setValue(null);
		lbCopilotoAux.setValue(null);
		lbTripulante.setValue(null);
	}


	/**
	 * Carga Pasajeros
	 * @param lsVentaPasajest: lista de Pasajeros a cargar.
	 * @throws Exception
	 */
	public void cargaPasajeros(List<VentaPasaje> lsVentaPasajest) throws Exception{
		if (lsVentaPasajest.size() > 0){
			Listitem item = null;
			Listcell cell = null;

			for (VentaPasaje ventaPasaje : lsVentaPasajest){
				item = new Listitem();

				cell= new Listcell(ventaPasaje.getNumeroAsiento().toString()); //Número de Asiento
				cell.setStyle("font-size:11px !important");
				item.appendChild(cell);
				cell= new Listcell("T"+ventaPasaje.getNumeroControl().toString().substring(4)); //Número de control
				cell.setStyle("font-size:11px !important");
				item.appendChild(cell);
				cell = new Listcell(ventaPasaje.getNumeroBoleto()); // Número de Boleto
				cell.setTooltiptext(ventaPasaje.getTipoComprobante().getDenominacion());
				cell.setStyle("font-size:11px !important");
				item.appendChild(cell);
				cell = new Listcell(ventaPasaje.getPasajero().toString());//.getApellidoPaterno()+" "+ ventaPasaje.getPasajero().getApellidoMaterno()+", "+ ventaPasaje.getPasajero().getNombre()); //Apellidos y Nombres del pasajero
				item.appendChild(cell);

				String edad="";
				if (ventaPasaje.getPasajero().getFechaNacimiento() !=null)
					edad= Util.calculaEdad(ventaPasaje.getPasajero().getFechaNacimiento()).toString();

				cell = new Listcell(edad.toString()); //Edad
				cell.setStyle("font-size:11px !important");
				item.appendChild(cell);
				cell = new Listcell(ventaPasaje.getPasajero().getTipoDocumento().getDenominacion()); //Tipo Docuemnto
				item.appendChild(cell);
				cell = new Listcell(ventaPasaje.getPasajero().getNumeroDocumento()); //Número Documento
				cell.setStyle("font-size:11px !important");
				item.appendChild(cell);
				cell = new Listcell(ventaPasaje.getRuta().getOrigen()); //Origen
				item.appendChild(cell);
				cell = new Listcell(ventaPasaje.getRuta().getDestino()); //Destino
				item.appendChild(cell);
				cell = new Listcell(Util.toNumberFormat(ventaPasaje.getImportePagado(), 2)); //Importe
				cell.setStyle("font-size:11px !important");
				item.appendChild(cell);
				cell  = new Listcell(ventaPasaje.getPreferenciaAlimentaria().getDenominacion()); //Preferia Alimenticia.
				item.appendChild(cell);
				cell = new Listcell(ventaPasaje.getAgenciaPartida().getNombreCorto()); //Punto de Embarque.
				item.appendChild(cell);

				item.setValue(ventaPasaje);
				//ListPasajeros.setStyle("overflow:auto");
				ListPasajeros.appendChild(item);
			}
		}

	}

	/**
	 * Carga detalle de pasajeros por ruta.
	 * @param lsVentaPasajest
	 * @throws Exception
	 */
	public void cargaDetallePasajeroXRuta(List<VentaPasaje> lsPaxRuta) throws Exception{
		if (lsPaxRuta.size() > 0){
			Listitem item = null;
			Listcell cell = null;

			int i=0,cantPax=0;
			for (VentaPasaje ventaPasaje : lsPaxRuta){
				item = new Listitem();

				cell= new Listcell(ventaPasaje.getRuta().getLocalidadOrigen().getDenominacion()); //Origen
				item.appendChild(cell);
				cell  = new Listcell(ventaPasaje.getRuta().getLocalidadDestino().getDenominacion()); //Destino
				item.appendChild(cell);
				cell = new Listcell(ventaPasaje.getCantidadPax().toString()); //Cantidad pasajeros.
				cell.setStyle("font-size:11px !important");
				item.appendChild(cell);

				Toolbarbutton btnVerDetalle=new Toolbarbutton("ver Detalle");
				btnVerDetalle.setDisabled(lsPaxRuta.size()==1);
				btnVerDetalle.setStyle("text-transform: lowercase;color:blue");
				btnVerDetalle.setId(String.valueOf(i++ +":")+ventaPasaje.getRuta().getId().toString());
				cell=new Listcell();
				cell.appendChild(btnVerDetalle);
				item.appendChild(cell);

				item.setValue(ventaPasaje);
				listDetaPaxRuta.appendChild(item);

				btnVerDetalle.addEventListener(Events.ON_CLICK, new EventListener<Event>() {
					@Override
					public void onEvent(Event event) throws Exception {
						// TODO Auto-generated method stub
						ventanaVerDetalleRuta(event.getTarget().getId());
					}
				});

				cantPax+=ventaPasaje.getCantidadPax();
			}

			item=new Listitem();
			cell=new Listcell("TOTAL PASAJEROS");
			cell.setStyle("color:red;font-weight: bold;");
			item.appendChild(cell);
			item.appendChild(new Listcell());
			cell=new Listcell(String.valueOf(cantPax));
			cell.setStyle("font-size:11px !important;color:red;font-weight: bold;");
			item.appendChild(cell);

			listDetaPaxRuta.appendChild(item);

		}
	}

	/**
	 * Carga la cantidad de pasajeros agrupados por forma de pago
	 * @param lsPaxformaPago
	 */
	private void cargarPasajerosByFormaPago(List<VentaPasaje> listVentaPasajes)throws Exception{
		Util.limpiarListbox(listPaxFormaPago);
		int paxContado=0,paxCredito=0,paxCortesia=0;
		for(VentaPasaje ventaPasaje :listVentaPasajes){
			switch (ventaPasaje.getFormaPago().getId().intValue()) {
				case Constantes.ID_FORPAG_CONTADO:
					paxContado++;
					break;
				case Constantes.ID_FORPAG_CORTESIA:
					paxCortesia++;
					break;
				case Constantes.ID_FORPAG_CREDITO:
					paxCredito++;
					break;
			}
		}
		Listitem item=null;
		Listcell cell= null;
		if(paxContado>0){
			item=new Listitem();
			cell= new Listcell("CONTADO");
			item.appendChild(cell);
			cell= new Listcell(String.valueOf(paxContado));
			cell.setStyle("font-size:11px !important");
			item.appendChild(cell);
			Toolbarbutton btnVerDetalle=new Toolbarbutton("ver Detalle");
			btnVerDetalle.setStyle("text-transform: lowercase;color:blue");
			cell=new Listcell();
			cell.appendChild(btnVerDetalle);
			item.appendChild(cell);

			btnVerDetalle.setId(String.valueOf(Constantes.ID_FORPAG_CONTADO));
			listPaxFormaPago.appendChild(item);

			btnVerDetalle.addEventListener(Events.ON_CLICK,new EventListener<Event>() {
				@Override
				public void onEvent(Event event) throws Exception {
					// TODO Auto-generated method stub
					ventanaVerDetalleFormaPago(Integer.valueOf(event.getTarget().getId()));
				}
			});
		}
		if(paxCortesia>0){
			item=new Listitem();
			cell= new Listcell("CORTESIA");
			item.appendChild(cell);
			cell= new Listcell(String.valueOf(paxCortesia));
			cell.setStyle("font-size:11px !important");
			item.appendChild(cell);
			Toolbarbutton btnVerDetalle=new Toolbarbutton("ver Detalle");
			btnVerDetalle.setStyle("text-transform: lowercase;color:blue");
			cell=new Listcell();
			cell.appendChild(btnVerDetalle);
			item.appendChild(cell);

			btnVerDetalle.setId(String.valueOf(Constantes.ID_FORPAG_CORTESIA));
			listPaxFormaPago.appendChild(item);

			btnVerDetalle.addEventListener(Events.ON_CLICK,new EventListener<Event>() {
				@Override
				public void onEvent(Event event) throws Exception {
					// TODO Auto-generated method stub
					ventanaVerDetalleFormaPago(Integer.valueOf(event.getTarget().getId()));
				}
			});
		}
		if(paxCredito>0){
			item=new Listitem();
			cell= new Listcell("CREDITO");
			item.appendChild(cell);
			cell= new Listcell(String.valueOf(paxCredito));
			cell.setStyle("font-size:11px !important");
			item.appendChild(cell);
			Toolbarbutton btnVerDetalle=new Toolbarbutton("ver Detalle");
			btnVerDetalle.setStyle("text-transform: lowercase;color:blue");
			cell=new Listcell();
			cell.appendChild(btnVerDetalle);
			item.appendChild(cell);

			btnVerDetalle.setId(String.valueOf(Constantes.ID_FORPAG_CREDITO));
			listPaxFormaPago.appendChild(item);

			btnVerDetalle.addEventListener(Events.ON_CLICK,new EventListener<Event>() {
				@Override
				public void onEvent(Event event) throws Exception {
					// TODO Auto-generated method stub
					ventanaVerDetalleFormaPago(Integer.valueOf(event.getTarget().getId()));
				}
			});
		}

	}

	/**
	 * Carga Manifiesto.
	 * @throws Exception
	 */
	public void cargarManifiesto(Long idItinerario, String tramoOrigen, String tramoDestino) throws Exception{
			limpiaItinerario();
			Util.limpiarListbox(ListPasajeros);
			Util.limpiarListbox(listDetaPaxRuta);

			/*datos del itinerario*/
			cargarItinerario(idItinerario, tramoOrigen, tramoDestino);
			/*lista de pasajeros*/
			List<VentaPasaje>listaVentaPasajes=ServiceLocator.getManifiestoManager().consultaPasajeros(idItinerario,null);
			cargaPasajeros(listaVentaPasajes);
			/*Detalle de pasajeros por ruta*/
			lisOpabilidad=ServiceLocator.getManifiestoManager().consultaDetaPaxXRuta(idItinerario);
			cargaDetallePasajeroXRuta(lisOpabilidad);
			/*Pasajeros por forma de Pago*/
			cargarPasajerosByFormaPago(listaVentaPasajes);
			/*Contidad Pasajeros*/
//			lbTotalPasajeros.setValue(((Integer) ListPasajeros.getItems().size()).toString());
			tbbVerMapa.setDisabled(false);
			tbbVerPax.setDisabled(false);
			if (ListPasajeros.getItems().size() > 0 ){
				cmbImprimir.setSelectedIndex(0);

				cmbImprimir.setDisabled(false);

				/*Valida si la agencia esta autorizada para la impresion de manifiestos*/
				validaImpresionManifiesto();
			}else{
				cmdPrevio.setDisabled(true);
				cmdImprimir.setDisabled(true);
				cmbImprimir.setDisabled(true);
			}
	}

	/**
	 * carga puntos de Control, segï¿½n el itinerario
	 * @param idItinerario : identificador del itinerario.
	 * @throws Exception
	 */
	public void cargaPuntoControl(Long idItinerario) throws Exception{
		ArrayList<Agencia> lsta= (ArrayList<Agencia>) ServiceLocator.getManifiestoManager().consultaPtoControl(idItinerario);
		cmbPuntocontrol.getItems().clear();
		for (Agencia agencia : lsta) {
			Comboitem oComboitem = new Comboitem();
			oComboitem.setLabel(agencia.getNombreCorto());
			oComboitem.setValue(agencia);
			cmbPuntocontrol.appendChild(oComboitem);
		}

	}

	/**
	 * Permite enlazar los controles a la ventana de selecciï¿½n de Itinerario
	 * @param textboxItinerario :en este Textbox se devolvera el Id del itinerario seleccionado.
	 * @param button :ha este Button se le adjuntara un listener con la llamada a la ventana de selecciï¿½n de itinerario
	 * @see WndItinerario:
	 */
	public  void enlazarItinerario(final Button button) {
		button.setTooltiptext("Seleccionar Itinerario");
		button.addEventListener(Events.ON_CLICK, new EventListener<Event>() {
			@Override
			public void onEvent(Event event) throws Exception {
				final WndSeleccionaItinerario oWndSeleccionaItinerario = new WndSeleccionaItinerario();
				boolean buscarVentanaParent = true;
				Component oComponent = button.getParent();
				while(buscarVentanaParent){
					 if(oComponent instanceof Window) {
						 oComponent.appendChild(oWndSeleccionaItinerario);
						 buscarVentanaParent = false;
					 }else{
					 	oComponent = oComponent.getParent();
					 }
				}
				oWndSeleccionaItinerario.onCreate();
				oWndSeleccionaItinerario.setMode("modal");
				oWndSeleccionaItinerario.setVisible(true);


				oWndSeleccionaItinerario.addEventListener(Events.ON_SELECT, new EventListener<Event>() {
					@Override
					public void onEvent(Event event) throws Exception {
						/*lista de puntos de control*/
						cargaPuntoControl(oWndSeleccionaItinerario.getIdItinerario());
						/*Selecciona el origen en el punto de control*/
						Util.seleccionarValorItemCombo(Agencia.class, cmbPuntocontrol,getAgencia());
						if(cmbPuntocontrol.getSelectedIndex()<0){
							if(cmbPuntocontrol.getItems().size()>0)
								cmbPuntocontrol.setSelectedIndex(0);
						}
//							cmbPuntocontrol.setSelectedIndex(0);
//						Util.seleccionarValorItemCombo(Agencia.class, cmbPuntocontrol,((Localidad) oWndSeleccionaItinerario.cmbOrigen.getSelectedItem().getValue()).getId());
						/*Carga Manifiesto*/
						cargarManifiesto(oWndSeleccionaItinerario.getIdItinerario(), oWndSeleccionaItinerario.getOrigen(), oWndSeleccionaItinerario.getItinerario().getRuta().getDestino());
					}
				});
			}
		});
	}

	/**
	 * Carga los tipo de impresion.
	 */
	private void cargarTipoImpresionManifiesto() {
		Comboitem oComboitem = new Comboitem("CARPETA DE DESPACHO");
		Comboitem oComboitem2 = new Comboitem("MANIFIESTO DE PASAJEROS");
		Comboitem oComboitem3 = new Comboitem("LISTADO DE PASAJEROS");

		oComboitem.setValue(0);
		oComboitem2.setValue(1);
		oComboitem3.setValue(2);

		cmbImprimir.appendChild(oComboitem);
		cmbImprimir.appendChild(oComboitem2);
		cmbImprimir.appendChild(oComboitem3);

	}

	/**
	 * Valida si la agencia esta autorizada para la impresion de manifiestos
	 */
	public void validaImpresionManifiesto(){
		if (cmbImprimir.getSelectedItem().getValue().equals(IMPRESION_MANIFIESTO_PASAJEROS)){
			if (!(especieValoradaSunat.getAutorizacionSunat() ==null && especieValoradaSunat.getSerie() ==null && especieValoradaSunat.getCorrelativoActual() == null )){
				/*Habili/Desabilita segun la configuracion del rol del usuario*/
				/*IMPRIMIR*/
				cmdImprimir.setDisabled(accesoImprimir()?false:true);
				cmdPrevio.setDisabled(false);
			}else{
				DlgMessage.information(Messages.getString("WndManifiesto.information.noCorrelativoManifiesto"),DlgMessage.BTN_OK);
				cmdImprimir.setDisabled(true);
				cmdPrevio.setDisabled(true);}
		}else{
			/*Habili/Desabilita segï¿½n la configuracion del rol del usuario*/
			/*IMPRIMIR*/
			cmdImprimir.setDisabled(accesoImprimir()?false:true);

			cmdPrevio.setDisabled(false);}
	}


	/**
	 * consulta si el manifiesto ya fue impreso
	 * @param idItinerario: identificador del Itinerario
	 * @return : manifiesto
	 * @throws Exception
	 */
	private Manifiesto validaManifiestoImpreso(Long idItinerario) throws Exception{
		Manifiesto manifiesto = ServiceLocator.getManifiestoManager().consultaMinifiestImpreso(idItinerario);
		return manifiesto;
	}

	/**
	 * Ver mapa
	 * @throws Exception
	 */
	public  void enlazarVerMapa() throws Exception {
		final WndVerMapaBus oWndSeleccionaItinerario = new WndVerMapaBus();
		oWndSeleccionaItinerario.setIdItinerario(new Long (txtItinerario.getText()));
		oWndSeleccionaItinerario.setCantOcupados(ListPasajeros.getItems().size());
		oWndSeleccionaItinerario.setListOcupabilidad(lisOpabilidad);

		Boolean esVisibleInfoBus, esVisibleOcupabilidad, esVisibleLeyenda, esVisibleButtons;
		esVisibleInfoBus=true;
		esVisibleOcupabilidad=true;
		esVisibleLeyenda=true;
		esVisibleButtons=true;
		oWndSeleccionaItinerario.load(esVisibleInfoBus, esVisibleOcupabilidad, esVisibleLeyenda, esVisibleButtons);

		this.appendChild(oWndSeleccionaItinerario);
		oWndSeleccionaItinerario.setMode("modal");
//

//
//		DetalleItinerario detalleItinerario = new DetalleItinerario();
//		/* Busca itinerario para la carga del Mapa */
//		List<DetalleItinerario> list = ServiceLocator.getItinerarioManager()
//				.buscarItinerariosMantenimiento(new Long(txtItinerario.getValue()),
//						null, null, null, null, null, null);
//		detalleItinerario = list.get(0);
//
//		VentaPasaje ventaPasaje = new VentaPasaje();
//		Listitem listitem = new Listitem();
//		listitem= ListPasajeros.getItemAtIndex(0);
//		ventaPasaje.setId(((VentaPasaje) listitem.getValue()).getId());
//		ventaPasaje.setServicio(detalleItinerario.getItinerario().getServicio());
//
//		Map<String, Object> params = new HashMap<String, Object>();
//		params.put("ventaPasaje", ventaPasaje);
//		params.put("detalleItinerario", detalleItinerario);
//		Window win = (Window)Executions.createComponents("mapa.zul", null, params);
//		win.setMode(MODAL);

	}

	/**
	 * Previsualizacion o impresion del documento seleccionado.(Carpeta de Depacho, Manifiesto de Pasajeros o Listado de Pasajeros)
	 * @param documento : es el documento seleccionado a previsualizar.
	 * @param esPrevio	: true(cuando es una vista preliminar), false(cuando se enviaro directamente la impresion a la impresora)
	 * @throws Exception
	 */
	public void impresionesManifiesto(Integer documento, Boolean esPrevio, String rotulo, Manifiesto manifiesto) throws Exception{
		String src="";
		String src1="";
		String src2="";
		final WndIFrame iFrame = new WndIFrame();
		Itinerario itinerario = new Itinerario();
		itinerario= ServiceLocator.getItinerarioManager().buscarPorId(new Long(txtItinerario.getText()));
		itinerario.setBus(this.itinerario.getBus());
		//Carpeta de Despacho
		if(documento.equals(IMPRESION_CARPETA_DESPACHO)){
			itinerario.setAgenciaPartida(null);

			Agencia agencia= new Agencia();
			agencia=(Agencia)cmbPuntocontrol.getSelectedItem().getValue();
			itinerario.setAgenciaPartida(agencia);
			File file=CreateDocument.creaCarpetaDespacho(itinerario, getAgencia());
//			src = Constantes.URL_FORMATOS_DESPACHOS+"CARDES-"+ itinerario.getId()+"-"+agencia.getId()+".txt";
			src = Constantes.URL_FORMATOS_DESPACHOS+Constantes.CLAVE_PAHT+"CARDES"+ itinerario.getId()+"-"+agencia.getId()+".txt";
			iFrame.setWidth("1035");

//			if(esPrevio==false && (getUsuarioHardware().getPrintApplet()==null || getUsuarioHardware().getPrintApplet().intValue()==Constantes.FALSE_VALUE)){
			if(!esPrevio){
				//Descarga el archivo para la impresion
				Util.descargarArchivo(file);
			}
		//Manifiesto de pasajeros
		}else if (documento.equals(IMPRESION_MANIFIESTO_PASAJEROS)){
			String numeroManifiesto ="";
			String autorizacionSunat="";

			if(manifiesto==null){
				//Valida si el manifiesto ya fue impreso
				manifiesto=validaManifiestoImpreso(itinerario.getId());
				if(manifiesto==null){//Si el manifiesto aún no a sido impreso
					loadCorrelativosManifiesto();
					manifiesto=new Manifiesto();
					if(especieValoradaSunat !=null){
						numeroManifiesto=especieValoradaSunat.getSerie()+"-"+especieValoradaSunat.getCorrelativoActual();
						autorizacionSunat=especieValoradaSunat.getAutorizacionSunat();
					}
					manifiesto.setNumeroManifiesto(numeroManifiesto);
					manifiesto.setAutorizacionSunat(autorizacionSunat);
				}
				manifiesto.setItinerario(itinerario);
			}

			File fileSunat= CreateDocument.creaManifiesto_ListPax(manifiesto,getUsuario(),getAgencia(),true,ROTULO_SUNAT);
			final File fileTransp=CreateDocument.creaManifiesto_ListPax(manifiesto,getUsuario(),getAgencia(),true,ROTULO_TRANSPORTISTA);
			final File fileArchivo=CreateDocument.creaManifiesto_ListPax(manifiesto,getUsuario(),getAgencia(),true,ROTULO_ARCHIVO);

//			src = Constantes.URL_FORMATOS_MANIFIESTOS +"MANPAX-"+ itinerario.getId()+"-"+ROTULO_SUNAT+".txt";
//			src1 = Constantes.URL_FORMATOS_MANIFIESTOS +"MANPAX-"+ itinerario.getId()+"-"+ROTULO_TRANSPORTISTA+".txt";
//			src2 = Constantes.URL_FORMATOS_MANIFIESTOS +"MANPAX-"+ itinerario.getId()+"-"+ROTULO_ARCHIVO+".txt";
			src = Constantes.URL_FORMATOS_MANIFIESTOS+Constantes.CLAVE_PAHT +"MANPAX"+ itinerario.getId()+"-"+ROTULO_SUNAT+".txt";
			src1 = Constantes.URL_FORMATOS_MANIFIESTOS+Constantes.CLAVE_PAHT +"MANPAX"+ itinerario.getId()+"-"+ROTULO_TRANSPORTISTA+".txt";
			src2 = Constantes.URL_FORMATOS_MANIFIESTOS+Constantes.CLAVE_PAHT +"MANPAX"+ itinerario.getId()+"-"+ROTULO_ARCHIVO+".txt";
			iFrame.setWidth("1115");
			UsuarioHardware usuarioHardware = getUsuarioHardware();

			if(!esPrevio && usuarioHardware.getPrintApplet().intValue()==Constantes.FALSE_VALUE){
//				if(esPrevio==false && getUsuarioHardware().getPrintApplet().intValue()==Constantes.FALSE_VALUE){
				File fileDestino=new File(fileSunat.getPath().replaceAll(".txt", "")+"COMP.txt");

				FileInputStream inputSunat= new FileInputStream(fileSunat);
				FileInputStream inputTransp= new FileInputStream(fileTransp);
				FileInputStream inputArchivo= new FileInputStream(fileArchivo);

				OutputStream Salida = new FileOutputStream(fileDestino);

				byte[] buffer = new byte[1024];
                int tamanio;
                //lee el fichero sunat a copiar cada 1MB
                while ((tamanio = inputSunat.read(buffer)) > 0) {
                    //Escribe el MB en el fichero destino
                	Salida.write(buffer, 0, tamanio);
                }
                inputSunat.close();

                //lee el fichero transportista a copiar cada 1MB
                while ((tamanio = inputTransp.read(buffer)) > 0) {
                    //Escribe el MB en el fichero destino
                	Salida.write(buffer, 0, tamanio);
                }
                 inputTransp.close();

               //lee el fichero Archivo a copiar cada 1MB
                 while ((tamanio = inputArchivo.read(buffer)) > 0) {
                     //Escribe el MB en el fichero destino
                	 Salida.write(buffer, 0, tamanio);
                 }
                 inputArchivo.close();

                 Salida.close();
                 Util.descargarArchivo(fileDestino);


//				//Descarga los archivos para la impresion
//				Util.descargarArchivo(fileSunat);
//
//				/*Timer que controla la descarga del manifiesto...copia guia transportista*/
//				Timer timer=new Timer(1000);
//				timer.addEventListener(Events.ON_TIMER, new EventListener<Event>() {
//					@Override
//					public void onEvent(Event event) throws Exception {
//						Util.descargarArchivo(fileTransp);
//					}
//				});
//				this.appendChild(timer);
//
//				/*Timer que controla la descarga del manifiesto...copia guia archivo*/
//				timer=new Timer(2000);
//				timer.addEventListener(Events.ON_TIMER, new EventListener<Event>() {
//					@Override
//					public void onEvent(Event event) throws Exception {
//						Util.descargarArchivo(fileArchivo);
//					}
//				});
//				this.appendChild(timer);

//				Messagebox.show(Messages.getString("Imprimiendo Manifiesto de pasajeros....Copia Transportista"), DlgMessage.NOMBREAPLICACION, DlgMessage.BTN_OK, Messagebox.INFORMATION,new EventListener<Event>() {
//					@Override
//					public void onEvent(Event e) throws Exception {
//						Util.descargarArchivo(fileTransp);
//					}
//				});


//				Messagebox.show(Messages.getString("Imprimiendo Manifiesto de pasajeros....Copia Archivo"), DlgMessage.NOMBREAPLICACION, DlgMessage.BTN_OK, Messagebox.INFORMATION, new EventListener<Event>() {
//					@Override
//					public void onEvent(Event e) throws Exception {
//						Util.descargarArchivo(fileArchivo);
//					}
//				});
			}
		//Listado de Pasajeros
		}else if(documento.equals(IMPRESION_LISTADO_PASAJEROS)){
			if(manifiesto==null){
				manifiesto=new Manifiesto();
				manifiesto.setItinerario(itinerario);
			}

			File fileListaPax= CreateDocument.creaManifiesto_ListPax(manifiesto,getUsuario(),getAgencia(),false,"");

//			src = Constantes.URL_FORMATOS_LISTADOS + "LSTPAX-"+ itinerario.getId()+".txt";
			src = Constantes.URL_FORMATOS_LISTADOS + Constantes.CLAVE_PAHT + "LSTPAX"+ itinerario.getId()+".txt";
			iFrame.setWidth("1115");

			if(!esPrevio && getUsuarioHardware().getPrintApplet().intValue()==Constantes.FALSE_VALUE){
				//Descarga el archivo para la impresion
				Util.descargarArchivo(fileListaPax);
			}
		}

		if(esPrevio){//Vista Preliminar
//			Agencia agencia= new Agencia();
//			agencia=(Agencia)cmbPuntocontrol.getSelectedItem().getValue();
//			src = "http://192.168.50.41:8080/sisvyr/formatos/listados/" + "LSTPAX-"+ itinerario.getId()+".txt";
//			src = "http://192.168.50.41:8080/sisvyr/formatos/despachos/" + "CARDES-"+ itinerario.getId()+"-"+agencia.getId()+".txt";
//			src = "http://192.168.50.41:8080/sisvyr/formatos/manifiestos/" + "MANPAX-"+ itinerario.getId()+"-"+ROTULO_ARCHIVO+".txt";

			iFrame.setSrc(src);
			iFrame.setheight("600");
			iFrame.loadiframe();
			iFrame.btnCerrar.setVisible(false);
			iFrame.oThisWindow.setClosable(true);
			switch (documento) {
			case 0://-->IMPRESION_CARPETA_DESPACHO
				iFrame.oThisWindow.setTitle("CARPETA DESPACHO");
				break;
			case 1: //-->IMPRESION_MANIFIESTO_PASAJEROS
				iFrame.oThisWindow.setTitle("MANIFIESTO DE PASAJEROS");
				break;
			case 2: //-->IMPRESION_LISTADO_PASAJEROS
				iFrame.oThisWindow.setTitle("LISTADO DE PASAJEROS");
				break;
			default:
				break;
			}

			this.appendChild(iFrame);
			iFrame.setMode("modal");
		}else{//Imprimir
			if(getUsuarioHardware().getPrintApplet().intValue()==Constantes.TRUE_VALUE){
				Window win = (Window)Executions.createComponents("imprimir.zul", null, null);
				if(documento==IMPRESION_CARPETA_DESPACHO){
					win.setAttribute("formato",WndImprimir.FORMAT_CARPETADESPACHO_PAX);
					win.setAttribute("msg", "Imprimiendo la Carpeta de Despacho... ");
				}else if(documento==IMPRESION_MANIFIESTO_PASAJEROS){
					win.setAttribute("formato", WndImprimir.FORMAT_MANIFIESTO_PAX);
					win.setAttribute("msg", "Imprimiendo el Manifiesto de Pasajeros... ");
					win.setAttribute("urlDocumento1", src1);
					win.setAttribute("urlDocumento2", src2);
				}else{
					win.setAttribute("formato", WndImprimir.FORMAT_LISTADO_PAX);
					win.setAttribute("msg", "Imprimiendo el Listado de Pasajeros... ");
				}

				win.setAttribute("urlDocumento", src);
				win.doPopup();
			}

		}

	}

	/* *FUNCIONES UTILIZADAS EN: CARPETA DESPACHO, MANIFIESTO DE PASAJEROS Y LISTADO DE PASAJEROS */
	/**
	 * Recupera Apellidos y nombre del pasajero
	 * @param listPasajeros : lista de pasajeros en el cual se consultara.
	 * @param asiento : numero de asiento a consultar.
	 * @return
	 */
	public String getPasajero(List<VentaPasaje> listPasajeros,Integer asiento, Integer longitud_Pax, Integer piso){
		String pasajero="";
		for(VentaPasaje ventaPasaje: listPasajeros){
			if(ventaPasaje.getNumeroAsiento().equals(asiento) && ventaPasaje.getNumeroPiso().equals(piso)){
				pasajero=ventaPasaje.getPasajero().getApellidoPaterno()+" "+ventaPasaje.getPasajero().getApellidoMaterno()+", "+ventaPasaje.getPasajero().getNombre();
//				//Integer lBase =24;
				if(pasajero.toString().length()>longitud_Pax)
					pasajero=pasajero.toString().substring(0,longitud_Pax);
				break;
			}
		}

		return pasajero;
	}

	public String getTipoDocumentoPax(List<VentaPasaje> listPasajeros,Integer asiento, Integer longitud_TipDoc, Integer piso){
		String tipoDocumentoPax="";
		for(VentaPasaje ventaPasaje: listPasajeros){
			if(ventaPasaje.getNumeroAsiento().equals(asiento) && ventaPasaje.getNumeroPiso().equals(piso)){
				if(ventaPasaje.getPasajero().getTipoDocumento() !=null)
					tipoDocumentoPax=ventaPasaje.getPasajero().getTipoDocumento().getDenominacion();
				if(tipoDocumentoPax.length()>longitud_TipDoc)
					tipoDocumentoPax=tipoDocumentoPax.substring(0, longitud_TipDoc);
				break;
			}
		}
		return tipoDocumentoPax;
	}
	/**
	 * Recupera el numero de documento del pasajero
	 * @param listPasajeros : lista de pasajeros en el cual se consultara.
	 * @param asiento : numero de asiento a consultar.
	 * @return
	 */
	public String getDocumentoPax(List<VentaPasaje> listPasajeros,Integer asiento, Integer piso){
		String documento="";
		for(VentaPasaje ventaPasaje: listPasajeros){
			if(ventaPasaje.getNumeroAsiento().equals(asiento) && ventaPasaje.getNumeroPiso().equals(piso)){
				if(ventaPasaje.getPasajero().getNumeroDocumento() !=null){
					documento= ventaPasaje.getPasajero().getNumeroDocumento();
					Integer longitud_D=documento.toString().length();
					if(documento.toString().length()>longitud_D)
						documento=documento.toString().substring(0,longitud_D);
					break;
				}
			}
		}
		return documento;
	}
	/**
	 * Recupera punto de embarque del pasajero
	 * @param listPasajeros : lista de pasajeros en el cual se consultara.
	 * @param asiento : numero de asiento a consultar.
	 * @return
	 */
	public String getEmbarque(List<VentaPasaje> listPasajeros,Integer asiento, Integer longitud_E, Integer piso){
		String embarque="";
		for(VentaPasaje ventaPasaje: listPasajeros){
			if(ventaPasaje.getNumeroAsiento().equals(asiento) && ventaPasaje.getNumeroPiso().equals(piso)){
				if(ventaPasaje.getAgenciaPartida().getNombreCorto() !=null)
					embarque=ventaPasaje.getAgenciaPartida().getNombreCorto();
				//Integer longitud_E=10;
				if(embarque.toString().length()>longitud_E)
					embarque=embarque.toString().substring(0,longitud_E);
				embarque=embarque.toUpperCase().substring(0,1)+embarque.toLowerCase().substring(1,embarque.toString().length());
				break;
			}
		}
		return embarque;
	}

	/**
	 * Busca los asientos repetidos en un servicio - Solo deberia haber mas de un registro con el mismo asiento cuando se vende por concepto de prioridad venta (case asiento 28 Lima - Ica aseinto 28 Ica - Arequipa)
	 * @param listPasajeros : lista de ventas en donde se va a buscar.
	 * @param asiento		: asiento a buscar.
	 * @param piso			: numerod de piso.
	 * @return
	 */
	public String getAsientos_venpasId(List<VentaPasaje> listPasajeros,Integer asiento, Integer piso){
		String asientos="";
		for(VentaPasaje ventaPasaje: listPasajeros){
			if(ventaPasaje.getNumeroAsiento().equals(asiento) && ventaPasaje.getNumeroPiso().equals(piso)){
				if(asientos.length()==0)
					asientos=ventaPasaje.getNumeroAsiento().toString()+"-"+ventaPasaje.getId().toString();
				else
					asientos+=";"+ventaPasaje.getNumeroAsiento().toString()+"-"+ventaPasaje.getId().toString();
			}
		}

		return asientos;
	}

	/**
	 * Recupera el numero de boleto del pasajero
	 * @param listPasajeros : lista de pasajeros en el cual se consultara.
	 * @param asiento : numero de asiento a consultar.
	 * @return
	 */
	public String getBoleto(List<VentaPasaje> listPasajeros,Integer asiento, Integer piso){
		String boleto="";
		for(VentaPasaje ventaPasaje: listPasajeros){
			if(ventaPasaje.getNumeroAsiento().equals(asiento) && ventaPasaje.getNumeroPiso().equals(piso)){
				boleto=ventaPasaje.getNumeroBoleto();
				break;
			}
		}
		return boleto;
	}
	/**
	 * Recupera la edad del pasajero.
	 * @param listPasajeros : lista de pasajeros en el cual se consultara.
	 * @param asiento : numero de asiento a consultar.
	 * @return
	 * @throws Exception
	 */
	public String getEdad(List<VentaPasaje> listPasajeros,Integer asiento, Integer piso) throws Exception{
		String edad="";
		for(VentaPasaje ventaPasaje: listPasajeros){
			if(ventaPasaje.getNumeroAsiento().equals(asiento) && ventaPasaje.getNumeroPiso().equals(piso)){
				if(ventaPasaje.getPasajero().getFechaNacimiento() !=null)
					edad=Util.calculaEdad(ventaPasaje.getPasajero().getFechaNacimiento()).toString();
				break;
			}
		}
		return edad;
	}
	/**
	 * Recupera la ruta del pasajero.
	 * @param listPasajeros : lista de pasajeros en el cual se consultara.
	 * @param asiento : numero de asiento a consultar.
	 * @return
	 * @throws Exception
	 */
	public String getRuta(List<VentaPasaje> listPasajeros,Integer asiento,Integer piso,Integer longituMax) throws Exception{
		String ruta="";
		for(VentaPasaje ventaPasaje: listPasajeros){
			if(ventaPasaje.getNumeroAsiento().equals(asiento) && ventaPasaje.getNumeroPiso().equals(piso)){
				String origen= ventaPasaje.getRuta().getOrigen();
				origen=origen.toUpperCase().substring(0,1)+origen.toLowerCase().substring(1,origen.length());
				String destino=ventaPasaje.getRuta().getDestino();
				destino=destino.toUpperCase().substring(0,1)+destino.toLowerCase().substring(1,destino.length());

				ruta=origen+"-"+destino;

				if(ruta.length()>longituMax)
					ruta=ruta.substring(0,longituMax);

				break;
			}
		}
		return ruta;
	}

	/**
	 * Recupera la ruta del pasajero.
	 * @param listPasajeros : lista de pasajeros en el cual se consultara.
	 * @param asiento : numero de asiento a consultar.
	 * @return
	 * @throws Exception
	 */
	public String getDestino(List<VentaPasaje> listPasajeros,Integer asiento,Integer piso,Integer longituMax) throws Exception{
		String destino="";
		for(VentaPasaje ventaPasaje: listPasajeros){
			if(ventaPasaje.getNumeroAsiento().equals(asiento) && ventaPasaje.getNumeroPiso().equals(piso)){

				destino=ventaPasaje.getRuta().getDestino();
				destino=destino.toUpperCase().substring(0,1)+destino.toLowerCase().substring(1,destino.length());


				if(destino.length()>longituMax)
					destino=destino.substring(0,longituMax);

				break;
			}
		}
		return destino;
	}

	public String getImporte(List<VentaPasaje> listPasajeros,Integer asiento, Integer piso){
		String importe="";
		for(VentaPasaje ventaPasaje: listPasajeros){
			if(ventaPasaje.getNumeroAsiento().equals(asiento) && ventaPasaje.getNumeroPiso().equals(piso)){
				importe=Util.toNumberFormat(ventaPasaje.getImportePagado(),2);
			}
		}
		return importe;
	}

	private void enlazaCompPendientesXAsociarBoleto(ArrayList<VentaPasaje> lisVentaPasajes) throws Exception{

		WndComprobantesPendientesXAsociarBoleto xAsociarBoleto=new WndComprobantesPendientesXAsociarBoleto();
		Component oComponent = cmdImprimir.getParent();
		boolean buscarVentanaParent = true;
		while(buscarVentanaParent){
			 if(oComponent instanceof Window) {
				 oComponent.appendChild(xAsociarBoleto);
				 buscarVentanaParent = false;
			 }else
			 	oComponent = oComponent.getParent();
		}
		xAsociarBoleto.setMode("modal");
		xAsociarBoleto.setVisible(true);

		xAsociarBoleto.setLstPendientesXAsociarBoleto(lisVentaPasajes);
		xAsociarBoleto.onCreate();
	}

	public void ventanaVerPasajero() throws Exception{
		wndVerPasajero = createWindowVerPasajero();
		this.appendChild(wndVerPasajero);
		wndVerPasajero.setMode("modal");
	}

	@SuppressWarnings("deprecation")
	private Window createWindowVerPasajero() throws Exception{

		final Window window = new Window("Ver Asiento del Pasajero", "normal", true);
		window.setWidth("700px");

		Grid gridTop= new Grid();
		Rows rows= new Rows();
		Row row= new Row();

		/*Columnas del grip top*/
		Columns columns= new Columns();
		/*Column-01*/
		Column column= new Column();
		column.setWidth("110px");
		columns.appendChild(column);
		/*Column-02*/
		column= new Column();
		column.setWidth("100px");
		columns.appendChild(column);
		/*Column-03*/
		column= new Column();
		column.setAlign("right");
		columns.appendChild(column);
		gridTop.appendChild(columns);

		Label label= new Label("Ingrese Nro. Asiento :");
		label.setSclass("label-size11");
		row.appendChild(label);

		final Intbox ibxAsiento= new Intbox();
		ibxAsiento.setWidth("40px");
		ibxAsiento.setMaxlength(2);
		row.appendChild(ibxAsiento);

		Button button= new Button();
		button.setLabel("Cerrar");
		button.setImage("/resources/mp_cerrar.png");
		button.setClass("btnCommandM");
		row.appendChild(button);

		rows.appendChild(row);
		gridTop.appendChild(rows);
		window.appendChild(gridTop);

		/*Listbox*/
		final Listbox lstPasajeros= new Listbox();
		lstPasajeros.setRows(6);
		Listhead listhead= new Listhead();
		//Pasajero
		Listheader listheader= new Listheader("PASAJERO");
		listheader.setWidth("220px");
		listheader.setStyle("color: #ffffff;");
		listhead.appendChild(listheader);
//		//Sexo
//		listheader= new Listheader("SEXO");
//		listheader.setWidth("70PX");
//		listhead.appendChild(listheader);
		//Ruta
		listheader= new Listheader("RUTA");
		listheader.setWidth("120px");
		listheader.setStyle("color: #ffffff;");
		listhead.appendChild(listheader);
		//Punto de embarque
		listheader= new Listheader("PUNTO EMBARQUE");
		listheader.setStyle("color: #ffffff;");
		listheader.setWidth("120px");
		listhead.appendChild(listheader);
		//Boleto
		listheader= new Listheader("TIPO COMPROBANTE");
		listheader.setWidth("120px");
		listheader.setStyle("color: #ffffff;");
		listhead.appendChild(listheader);
		//Boleto
		listheader= new Listheader("COMPROBANTE");
		listheader.setWidth("90px");
		listheader.setStyle("color: #ffffff;");
		listhead.appendChild(listheader);
		//Boleto referencial (anterior)
		listheader= new Listheader("BOLETO REF.");
		listheader.setWidth("90px");
		listheader.setStyle("color: #ffffff;");
		listhead.appendChild(listheader);
		//Tipo Movimiento
		listheader= new Listheader("TIPO MOVIMIENTO");
		listheader.setWidth("100px");
		listheader.setStyle("color: #ffffff;");
		listhead.appendChild(listheader);
		//Tipo transaccion
		listheader= new Listheader("TIP.TRANS.");
		listheader.setWidth("90px");
		listheader.setStyle("color: #ffffff;");
		listhead.appendChild(listheader);
		//Nro. Control
		listheader= new Listheader("NRO. CONTRORL");
		listheader.setWidth("100px");
		listheader.setStyle("color: #ffffff;");
		listhead.appendChild(listheader);
		//Fecha emision
		listheader= new Listheader("FECHA EMISION");
		listheader.setWidth("110px");
		listheader.setStyle("color: #ffffff;");
		listhead.appendChild(listheader);
		//Hora Emision
		listheader= new Listheader("HORA EMISION");
		listheader.setWidth("100px");
		listheader.setStyle("color: #ffffff;");
		listhead.appendChild(listheader);

		lstPasajeros.appendChild(listhead);
		window.appendChild(lstPasajeros);

		/*gridEnd*/
		Grid gridEnd = new Grid();
		rows= new Rows();
		row= new Row();

		/*Columnas del grip End*/
		columns= new Columns();
		/*Column-01*/
		column= new Column();
		column.setWidth("120px");
		column.setAlign("right");
		columns.appendChild(column);
		/*Column-02*/
		column= new Column();
		column.setWidth("230px");
		columns.appendChild(column);
		/*Column-03*/
		column= new Column();
		column.setWidth("132px");
		column.setAlign("right");
		columns.appendChild(column);
		/*Column-04*/
		column= new Column();
		columns.appendChild(column);
		/*add columns*/
		gridEnd.appendChild(columns);

		final String simboloNull="-----";

		/*Etiqueta Agencia venta*/
		label= new Label("Agencia creación :");
		label.setStyle("font-size:11px !important");
		row.appendChild(label);
		/*Valor Agencia venta*/
		final Label lblAgenciaVenta=new Label(simboloNull);
		lblAgenciaVenta.setStyle("font-size:9px !important; color:#0431B4;");
		row.appendChild(lblAgenciaVenta);

		/*Etiqueta Agencia Remota*/
		label= new Label("Agencia Remota :");
		label.setStyle("font-size:11px !important");
		row.appendChild(label);
		/*Valor agencia remota*/
		final Label lblAgenciaRemota=new Label(simboloNull);
		lblAgenciaRemota.setStyle("font-size:9px !important; color:#0431B4;");
		row.appendChild(lblAgenciaRemota);
		rows.appendChild(row);

		/*Etiqueta Usuario venta*/
		row= new Row();
		label= new Label("Usuario creación :");
		label.setStyle("font-size:11px !important");
		row.appendChild(label);
		/*Valor Usuario venta*/
		final Label lblUsuarioVenta=new Label(simboloNull);
		lblUsuarioVenta.setStyle("font-size:9px !important; color:#FF4000;");
		row.appendChild(lblUsuarioVenta);

		/*Etiqueta Usuario Remoto*/
		label= new Label("Usuario Remoto :");
		label.setStyle("font-size:11px !important");
		row.appendChild(label);
		/*Valor Usuario Remoto*/
		final Label lblUsuarioRmoto=new Label(simboloNull);
		lblUsuarioRmoto.setStyle("font-size:9px !important; color:#FF4000;");
		row.appendChild(lblUsuarioRmoto);
		rows.appendChild(row);

		/*Fecha/Hora Creacion*/
		row=new Row();row.setSpans("1,4");
		label= new Label("Fecha/Hora creación :");
		label.setStyle("font-size:11px !important");
		row.appendChild(label);
		rows.appendChild(row);
		final Label lblFechaHoraCreacion=new Label(simboloNull);
		lblFechaHoraCreacion.setStyle("font-size:10px !important; color:#0431B4;");
		row.appendChild(lblFechaHoraCreacion);

		/*Etiqueta Observaciones*/
		row= new Row();row.setSpans("1,4");
		label= new Label("Obeservaciones :");
		label.setStyle("font-size:11px !important");
		row.appendChild(label);
		/*Valor Observaciones*/
		final Label lblObservaciones=new Label();
		lblObservaciones.setStyle("font-size:11px !important; color:#FF4000;");
		row.appendChild(lblObservaciones);
		rows.appendChild(row);


		/*Usuario Modificacion*/
		row=new Row();
		label= new Label("Usuario Modificación :");
		label.setStyle("font-size:11px !important");
		row.appendChild(label);
		rows.appendChild(row);
		final Label lblUsuarioModificacion=new Label(simboloNull);
		lblUsuarioModificacion.setStyle("font-size:10px !important; color:#0431B4;");
		row.appendChild(lblUsuarioModificacion);

		/*Fecha y hora Modificacion*/
		label= new Label("Fecha/Hora Modificación :");
		label.setStyle("font-size:11px !important");
		row.appendChild(label);
		rows.appendChild(row);
		final Label lblFechaHoraModificacion=new Label(simboloNull);
		lblFechaHoraModificacion.setStyle("font-size:10px !important; color:#0431B4;");
		row.appendChild(lblFechaHoraModificacion);


		gridEnd.appendChild(rows);
		window.appendChild(gridEnd);

		ibxAsiento.addEventListener(Events.ON_OK, new EventListener<Event>() {
			@Override
			public void onEvent(Event event) throws Exception {
				// TODO Auto-generated method stub
				lblAgenciaVenta.setValue(simboloNull);
				lblUsuarioVenta.setValue(simboloNull);
				lblObservaciones.setValue(simboloNull);
				lblAgenciaRemota.setValue(simboloNull);
				lblUsuarioRmoto.setValue(simboloNull);

				// Valida que el número del asiento ingrasado no se mayor a la capasidad del bus.


				if(!(txtItinerario.getText().trim().isEmpty())){
					Itinerario oItinerario= new Itinerario();
					oItinerario.setId(Long.valueOf(txtItinerario.getText()));

					TreeMap<String, Object>criteriosBusqueda= new TreeMap<>();
					criteriosBusqueda.put("itinerario", oItinerario);
					criteriosBusqueda.put("numeroAsiento", ibxAsiento.getValue());
					criteriosBusqueda.put("estadoRegistro", Constantes.VALUE_ACTIVO);

					List<String>criteriosOrden=new ArrayList<>();
					criteriosOrden.add("id");

					List<VentaPasaje>listVenta=ServiceLocator.getVentaPasajesManager().buscarPorX(criteriosBusqueda, criteriosOrden);

					Listitem item=null;
					Listcell cell= null;
					Util.limpiarListbox(lstPasajeros);

					for(VentaPasaje ventaPasaje: listVenta){
						item=new Listitem();
						cell=new Listcell(ventaPasaje.getPasajero().toString());
						item.appendChild(cell);
//						cell=new Listcell(ventaPasaje.getPasajero().getSexo().getDenominacion());
//						item.appendChild(cell);
						cell=new Listcell(ventaPasaje.getRuta().toString());
						item.appendChild(cell);
						cell=new Listcell(ventaPasaje.getAgenciaPartida().getNombreCorto());
						item.appendChild(cell);
						cell=new Listcell(ventaPasaje.getTipoTransaccion().equals(Constantes.TIPO_OPERACION_VENTA)?ventaPasaje.getTipoComprobante().getDenominacion():"");
						item.appendChild(cell);
						cell=new Listcell(ventaPasaje.getNumeroBoleto());
						cell.setStyle("font-size:11px !important");
						item.appendChild(cell);
						cell=new Listcell(ventaPasaje.getNumeroBoletoAnterior());
						cell.setStyle("font-size:11px !important");
						item.appendChild(cell);
						cell=new Listcell(ventaPasaje.getTipoMovimiento().getDenominacion());
						item.appendChild(cell);
						cell=new Listcell(ventaPasaje.getTipoTransaccion().equals(Constantes.TIPO_OPERACION_VENTA)?"VENTA":"RESERVA");
						item.appendChild(cell);
						cell=new Listcell("T"+ventaPasaje.getNumeroControl().substring(4));
						cell.setStyle("font-size:11px !important");
						item.appendChild(cell);

						GregorianCalendar cal = new GregorianCalendar();
						cal.setTime(ventaPasaje.getFechaInsercion());

						String sFecha=Util.getDiaSemana(Constantes.FORMAT_DATE.format(ventaPasaje.getFechaInsercion())).substring(0,3);
						sFecha+=" "+Constantes.FORMAT_DAY.format(ventaPasaje.getFechaInsercion());
						sFecha+="-"+Util.getNombreMes(Constantes.FORMAT_DATE.format(ventaPasaje.getFechaInsercion())).substring(0,3);
						sFecha+="-"+Constantes.FORMAT_YEAR.format(ventaPasaje.getFechaInsercion());

						cell=new Listcell(sFecha);
						cell.setStyle("font-size:10px !important");
						item.appendChild(cell);
						cell=new Listcell(Constantes.FORMAT_TIME.format(ventaPasaje.getFechaInsercion()));
						cell.setStyle("font-size:11px !important");
						item.appendChild(cell);

						item.setValue(ventaPasaje);
						lstPasajeros.appendChild(item);
					}
				}
			}
		});


		lstPasajeros.addEventListener(Events.ON_SELECT,new EventListener<Event>() {
			@Override
			public void onEvent(Event event) throws Exception {
				// TODO Auto-generated method stub
				VentaPasaje ventaPasaje=ServiceLocator.getVentaPasajesManager().buscarPorId(((VentaPasaje)lstPasajeros.getSelectedItem().getValue()).getId());
				if(ventaPasaje!=null){
					lblAgenciaVenta.setValue(ventaPasaje.getAgencia().getDenominacion());
					Usuario usuarioCreacion=ServiceLocator.getUsuarioManager().buscarPorId( ventaPasaje.getUsuario().getId().longValue());
					lblUsuarioVenta.setValue(usuarioCreacion.toString());
					lblFechaHoraCreacion.setValue((ventaPasaje.getFechaInsercion()!=null?Constantes.FORMAT_DATE_TIME_24H.format(ventaPasaje.getFechaInsercion()):"--"));

					Usuario usuarioModificacion=ServiceLocator.getUsuarioManager().buscarUsuarioPorLogin(ventaPasaje.getUsuarioModificacion(), Constantes.VALUE_ACTIVO);
					if(usuarioModificacion!=null)
						lblUsuarioModificacion.setValue(usuarioModificacion.toString());
					lblFechaHoraModificacion.setValue((ventaPasaje.getFechaModificacion()!=null?Constantes.FORMAT_DATE_TIME_24H.format(ventaPasaje.getFechaModificacion()):"--"));

					if(ventaPasaje.getTipoTransaccion().equals(Constantes.TIPO_OPERACION_RESERVA)){
						String expiraReserva="F.E.: "+Constantes.FORMAT_DATE.format(ventaPasaje.getFechaExpiracionReserva())+" "+ventaPasaje.getHoraExpiracionReserva();
						if(ventaPasaje.getObservaciones()==null)
							ventaPasaje.setObservaciones(expiraReserva);
						else
							ventaPasaje.setObservaciones(ventaPasaje.getObservaciones()+" "+expiraReserva);
					}

					lblObservaciones.setValue(ventaPasaje.getObservaciones());
				}
			}
		});


		button.addEventListener(Events.ON_CLICK, new EventListener<Event>() {
			@Override
			public void onEvent(Event event) throws Exception {
				window.onClose();
			}
		});

		return window;
	}


	public void ventanaVerDetalleRuta(String idRuta) throws Exception{
		idRuta=idRuta.split(":")[1];

		wndVerDetalle = createWindowVerDetalle(null,Integer.valueOf(idRuta));
		this.appendChild(wndVerDetalle);
		wndVerDetalle.setMode("modal");
	}

	public void ventanaVerDetalleFormaPago(Integer idFormaPago) throws Exception{
		wndVerDetalle = createWindowVerDetalle(idFormaPago,null);
		this.appendChild(wndVerDetalle);
		wndVerDetalle.setMode("modal");
	}

	private Window createWindowVerDetalle(Integer idFormaPago, Integer idRuta) throws Exception{

		final Window window = new Window("PASAJEROS", "normal", true);
		window.setPosition("center");
		window.setWidth("870px");

		if(idFormaPago!=null){
			switch (idFormaPago) {
				case Constantes.ID_FORPAG_CONTADO:
					window.setTitle(window.getTitle()+" - CONTADO");
					break;
				case Constantes.ID_FORPAG_CORTESIA:
					window.setTitle(window.getTitle()+" - CORTESIA");
					break;
				case Constantes.ID_FORPAG_CREDITO:
					window.setTitle(window.getTitle()+" - CREDITO");
					break;
			}
		}else{
			window.setTitle(window.getTitle()+" POR RUTA");
		}

		/*Listbox*/
		final Listbox lstPasajeros= new Listbox();
		lstPasajeros.setRows(12);
		Listhead listhead= new Listhead();
		//Asiento
		Listheader listheader= new Listheader("ASIENTO");
		listheader.setWidth("50px");
		listheader.setStyle("color: #ffffff;");
		listhead.appendChild(listheader);
		//Boleto
		listheader= new Listheader("N° BOLETO");
		listheader.setWidth("100px");
		listheader.setStyle("color: #ffffff;");
		listhead.appendChild(listheader);
		//Pasajero
		listheader= new Listheader("PASAJERO");
		listheader.setWidth("200px");
		listheader.setStyle("color: #ffffff;");
		listhead.appendChild(listheader);
		//Ruta
		listheader= new Listheader("RUTA");
		listheader.setWidth("120px");
		listheader.setStyle("color: #ffffff;");
		listhead.appendChild(listheader);
		//Punto de embarque
		listheader= new Listheader("PTO. EMBARQUE");
		listheader.setWidth("100px");
		listheader.setStyle("color: #ffffff;");
		listhead.appendChild(listheader);
		//Canal
		listheader = new Listheader("CANAL");
		listheader.setWidth("75px");
		listheader.setStyle("color: #ffffff;");
		listhead.appendChild(listheader);
		lstPasajeros.appendChild(listhead);

		//
		if(idFormaPago!=null && idFormaPago.intValue()==Constantes.ID_FORPAG_CREDITO){
			listheader = new Listheader("CLIENTE CREDITO");
			listheader.setStyle("color: #ffffff;");
			listhead.appendChild(listheader);
		}else{

			if(idFormaPago!=null){
				listheader = new Listheader("AGENCIA");
				listheader.setStyle("color: #ffffff;");
				window.setWidth("800px");
			}else{
				listheader = new Listheader("EMITIDO POR:");
				listheader.setStyle("color: #ffffff;");
			}

			listhead.appendChild(listheader);
		}
		lstPasajeros.appendChild(listhead);

		int i=-1;
		/*Carga pasajeros*/
		for(Listitem item :this.ListPasajeros.getItems()){
			boolean addItem=false;
			VentaPasaje ventaPasaje=(VentaPasaje)item.getValue();

			if(idFormaPago!=null){
				if(ventaPasaje.getFormaPago().getId().intValue()==idFormaPago.intValue())
					addItem=true;
			}else if(idRuta!=null){
				if(ventaPasaje.getRuta().getId().intValue()==idRuta.intValue()){
					addItem=true; i++;
					if(i==0)
						window.setTitle(window.getTitle()+"  ("+ventaPasaje.getRuta().toString()+")");
				}
			}

			if(addItem){
				Listitem itemp=new Listitem();
				Listcell cell=new Listcell(ventaPasaje.getNumeroAsiento().toString());
				cell.setStyle("font-size:11px !important");
				itemp.appendChild(cell);
				cell=new Listcell(ventaPasaje.getNumeroBoleto());
				cell.setStyle("font-size:11px !important");
				itemp.appendChild(cell);
				cell=new Listcell(ventaPasaje.getPasajero().toString());
				itemp.appendChild(cell);
				cell=new Listcell(ventaPasaje.getRuta().toString());
				itemp.appendChild(cell);
				cell=new Listcell(ventaPasaje.getAgenciaPartida().getNombreCorto());
				itemp.appendChild(cell);
				cell=new Listcell(ventaPasaje.getCanalVenta().getNombreCorto());
				itemp.appendChild(cell);
				cell=new Listcell(ventaPasaje.getCliente()!=null?ventaPasaje.getCliente().toString():ventaPasaje.getAgencia().getNombreCorto());
				itemp.appendChild(cell);

				lstPasajeros.appendChild(itemp);
			}
		}


		window.appendChild(lstPasajeros);

		return window;
	}

}
