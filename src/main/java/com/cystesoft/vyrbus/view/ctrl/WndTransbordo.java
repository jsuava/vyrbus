package com.cystesoft.vyrbus.view.ctrl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zul.Button;
import org.zkoss.zul.Grid;
import org.zkoss.zul.Groupbox;
import org.zkoss.zul.Image;
import org.zkoss.zul.Intbox;
import org.zkoss.zul.Label;
import org.zkoss.zul.Longbox;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Row;
import org.zkoss.zul.Window;

import com.cystesoft.vyrbus.model.bean.DetalleItinerario;
import com.cystesoft.vyrbus.model.bean.Itinerario;
import com.cystesoft.vyrbus.model.bean.MovimientoPasajes;
import com.cystesoft.vyrbus.model.bean.TmpOcupacionAsientos;
import com.cystesoft.vyrbus.model.bean.TmpOcupacionAsientosID;
import com.cystesoft.vyrbus.model.bean.Transbordo;
import com.cystesoft.vyrbus.model.bean.VentaPasaje;
import com.cystesoft.vyrbus.service.exceptions.DuplicateSeatException;
import com.cystesoft.vyrbus.service.exceptions.ItinerarioException;
import com.cystesoft.vyrbus.service.exceptions.NoSeleccionItinerariosException;
import com.cystesoft.vyrbus.service.exceptions.NumeroAsientoNullException;
import com.cystesoft.vyrbus.service.exceptions.ServicioDespachadoException;
import com.cystesoft.vyrbus.service.exceptions.TransbordoAsientoBNullException;
import com.cystesoft.vyrbus.service.exceptions.TransbordoAsientoDesocupadoException;
import com.cystesoft.vyrbus.service.exceptions.TransbordoAsientoHaciaNullException;
import com.cystesoft.vyrbus.service.exceptions.TransbordoAsientoOcupadoException;
import com.cystesoft.vyrbus.service.exceptions.TransbordoNoPermitidoExeption;
import com.cystesoft.vyrbus.service.exceptions.TransbordoRutasDiferentesException;
import com.cystesoft.vyrbus.service.locator.ServiceLocator;
import com.cystesoft.vyrbus.service.mappers.Asiento;
import com.cystesoft.vyrbus.service.util.Constantes;
import com.cystesoft.vyrbus.service.util.Messages;
import com.cystesoft.vyrbus.service.util.Util;
import com.cystesoft.vyrbus.service.util.UtilData;
import com.cystesoft.vyrbus.view.ui.DlgMessage;
import com.cystesoft.vyrbus.view.ui.WndBase;
import com.cystesoft.vyrbus.view.ui.WndSeleccionaItinerario;
import com.cystesoft.vyrbus.view.ui.WndVerMapaBusTransbordo;
import com.cystesoft.vyrbus.view.ui.WndVerPasajero;

/**
 *
 * @author José abanto
 *
 */
public class WndTransbordo extends WndBase implements Serializable{
	private static final long serialVersionUID = 1L;

	private  WndVerMapaBusTransbordo verMapaBus =null;
	private  WndVerMapaBusTransbordo verMapaBusH =null;

	private Map<String, Asiento> mapAsientosId = null;
	private List<VentaPasaje> listVentasDesde =null;
	private List<VentaPasaje> listVentasHacia =null;
	private List<DetalleItinerario> listDetItiDesde=null;
	private List<DetalleItinerario> listDetItiHacia=null;
	private ArrayList<Asiento> listAsientosOcupados= new ArrayList<>();
	Itinerario itinerarioDesde=null;
	Itinerario itinerarioHacia=null;
	Asiento asientoSelectDesde=null;
	Asiento asientoSelectHacia=null;

	private Button btnBuscar;
	private Longbox lbxItinerario;
	private Grid grdMapaDesde;
	private Groupbox gbxMapaDesde;
//	private Grid gritnerarioDesde;
	private Label lbOrigen;
	private Label lbDestino;
	private Label lbSalida;
	private Label lbFecha;
	private Label lbBus;
	private Label lbServicio;
	private Label lbOcupados;
	private Label lbDisponibles;
	private Button btnBuscarH;
	private Longbox lbxItinerarioH;
	private Grid grdMapaHacia;
	private Groupbox gbxMapaHacia;
//	private Grid gritnerarioHacia;
	private Label lbOrigenH;
	private Label lbDestinoH;
	private Label lbSalidaH;
	private Label lbFechaH;
	private Label lbBusH;
	private Label lbServicioH;
	private Label lbOcupadosH;
	private Label lbDisponiblesH;
	private Button btnRefrescarH;
	private Button btnRefrescar;
	private Intbox ibxSeleccion;
	private Intbox ibxSeleccionH;
	private Button btnVerPasajero;
	private Button btnVerPasajeroH;
	private Row rwLeft;
	private Row rwRight;
	private Image imgDerercha;
	private Image imgAllDerecha;
	private Image imgIzquierda;
	private Image imgAllIzquierda;


	private Integer heightMp1=0;
	private Integer heightMp2=0;

	public String enabledImgDerecha="/resources/mp_rightArrowEnabled.png";
	public String enabledImgAllDerecha="/resources/mp_allRightArrowEnabled.png";
	public String enabledImgIzquierda="/resources/mp_leftArrowEnabled.png";
	public String enabledImgAllIzquierda="/resources/mp_allLeftArrowEnabled.png";

	public String disabledImgDerecha="/resources/mp_rightArrowDisabled.png";
	public String disabledImgAllDerecha="/resources/mp_allRightArrowDisabled.png";
	public String disabledImgIzquierda="/resources/mp_leftArrowDisabled.png";
	public String disabledImgAllIzquierda="/resources/mp_allLeftArrowDisabled.png";

	@Override
	public void initComponents() {
		btnBuscar = (Button) this.getFellow("btnBuscar");
		lbxItinerario = (Longbox) this.getFellow("lbxItinerario");
		grdMapaDesde = (Grid) this.getFellow("grdMapaDesde");
		gbxMapaDesde = (Groupbox) this.getFellow("gbxMapaDesde");
//		gritnerarioDesde = (Grid) this.getFellow("gritnerarioDesde");
		lbOrigen = (Label) this.getFellow("lbOrigen");
		lbDestino = (Label) this.getFellow("lbDestino");
		lbSalida = (Label) this.getFellow("lbSalida");
		lbFecha = (Label) this.getFellow("lbFecha");
		lbBus = (Label) this.getFellow("lbBus");
		lbServicio = (Label) this.getFellow("lbServicio");
		lbOcupados = (Label) this.getFellow("lbOcupados");
		lbDisponibles = (Label) this.getFellow("lbDisponibles");
		btnBuscarH = (Button) this.getFellow("btnBuscarH");
		lbxItinerarioH = (Longbox) this.getFellow("lbxItinerarioH");
		grdMapaHacia = (Grid) this.getFellow("grdMapaHacia");
		gbxMapaHacia = (Groupbox) this.getFellow("gbxMapaHacia");
//		gritnerarioHacia = (Grid) this.getFellow("gritnerarioHacia");
		lbOrigenH = (Label) this.getFellow("lbOrigenH");
		lbDestinoH = (Label) this.getFellow("lbDestinoH");
		lbSalidaH = (Label) this.getFellow("lbSalidaH");
		lbFechaH = (Label) this.getFellow("lbFechaH");
		lbBusH = (Label) this.getFellow("lbBusH");
		lbServicioH = (Label) this.getFellow("lbServicioH");
		lbOcupadosH = (Label) this.getFellow("lbOcupadosH");
		lbDisponiblesH = (Label) this.getFellow("lbDisponiblesH");
		ibxSeleccion = (Intbox) this.getFellow("ibxSeleccion");
		ibxSeleccionH= (Intbox) this.getFellow("ibxSeleccionH");
		btnRefrescar=(Button) this.getFellow("btnRefrescar");
		btnRefrescarH=(Button) this.getFellow("btnRefrescarH");
		btnVerPasajero=(Button) this.getFellow("btnVerPasajero");
		btnVerPasajeroH=(Button) this.getFellow("btnVerPasajeroH");
		rwLeft=(Row)this.getFellow("rwLeft");
		rwRight=(Row)this.getFellow("rwRight");
		imgDerercha=(Image)this.getFellow("imgDerercha");
		imgAllDerecha=(Image)this.getFellow("imgAllDerecha");
		imgIzquierda=(Image)this.getFellow("imgIzquierda");
		imgAllIzquierda=(Image)this.getFellow("imgAllIzquierda");
	}



	@Override
	public void onCreate() throws Exception {
		enlazarItinerario(btnBuscar);
		enlazarItinerario(btnBuscarH);

		enlazaVerPasajero(btnVerPasajero);
		enlazaVerPasajero(btnVerPasajeroH);
	}

	/**
	 * Enlaza la llamada a la venta verPasajero
	 * @param button : Objeto del cual será invocado.
	 */
	public void enlazaVerPasajero(final Button button){
		button.setTooltiptext("Ver detalles del Pasajero.");
		button.setStyle("cursor:default");
		button.addEventListener(Events.ON_CLICK, new EventListener<Event>() {
			@Override
			public void onEvent(Event event) throws Exception {
				boolean validaSeleccionAsiento=false;
				if(button.getId().equals("btnVerPasajero")){
					if(ibxSeleccion.getValue() !=null)
						validaSeleccionAsiento=true;
				}else{
					if(ibxSeleccionH.getValue() !=null)
						validaSeleccionAsiento=true;
				}

				if(validaSeleccionAsiento){
					final WndVerPasajero verPasajero = new WndVerPasajero();
					boolean buscarVentanaParent = true;
					Component oComponent = button.getParent();
					while(buscarVentanaParent){
						 if(oComponent instanceof Window) {
							 oComponent.appendChild(verPasajero);
							 buscarVentanaParent = false;
						 }else
						 	oComponent = oComponent.getParent();
					}
					verPasajero.setMode("modal");
					verPasajero.setVisible(true);

					if(button.getId().equals("btnVerPasajero")){
						for(VentaPasaje venta: listVentasDesde){
							if(venta.getNumeroAsiento().equals(asientoSelectDesde.getNumeroAsiento()))
								verPasajero.setVentaPasaje(venta);
						}
						verPasajero.setAsiento(asientoSelectDesde);
					}else{
						for(VentaPasaje venta: listVentasHacia){
							if(venta.getNumeroAsiento().equals(asientoSelectHacia.getNumeroAsiento()))
								verPasajero.setVentaPasaje(venta);
						}
						verPasajero.setAsiento(asientoSelectHacia);
					}
					verPasajero.onCreate();
				}
			}
		});

	}

	/**
	 * Enlaza la llamada a la venta de selección de itinerarios
	 * @param button :Objeto de donde sera llamado el itinerario.
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
					 }else
					 	oComponent = oComponent.getParent();
				}

				oWndSeleccionaItinerario.onCreate();
				oWndSeleccionaItinerario.setMode("modal");
				oWndSeleccionaItinerario.setVisible(true);
				oWndSeleccionaItinerario.addEventListener(Events.ON_SELECT, new EventListener<Event>() {
					@Override
					public void onEvent(Event event) throws Exception {
						try{
							/*limpia grid (por hacer)*/
							if(button.getId().equals("btnBuscar"))
								limpiaControlsMapaDirecho();
							else
								limpiaControlsMapaIzquierdo();

							Itinerario itinerario=getItiItinerario(oWndSeleccionaItinerario.getItinerario());
							Date fechaActual=Constantes.FORMAT_DATE.parse(Constantes.FORMAT_DATE.format(new Date()));
							Date fechaItinerario= itinerario.getFechaPartida();

							/*cuando se quiere realizar un transbordo de un itinerario con fecha anterior a la actual*/
							if(fechaItinerario.getTime()<fechaActual.getTime())
								throw new ItinerarioException(ItinerarioException.FECHA_MENOR);


							if(button.getId().equals("btnBuscar")){
								//Itinerario Desde
								lbxItinerario.setValue( oWndSeleccionaItinerario.getIdItinerario());
								cargarMapa(gbxMapaDesde, grdMapaDesde,lbxItinerario.getValue());
								cargaInfoItinerarioDesde(lbxItinerario.getValue());
								ibxSeleccion.setValue(null);
								listDetItiDesde=cargaListDetalleItinerario(itinerarioDesde);
								imgDerercha.setSrc(disabledImgIzquierda);
							}else{
								//Itinerario Hacia
								lbxItinerarioH.setValue( oWndSeleccionaItinerario.getIdItinerario());
								cargarMapa(gbxMapaHacia, grdMapaHacia, lbxItinerarioH.getValue());
								cargaInfoItinerarioHacia(lbxItinerarioH.getValue());
								ibxSeleccionH.setValue(null);
								listDetItiHacia=cargaListDetalleItinerario(itinerarioHacia);
								imgIzquierda.setSrc(disabledImgDerecha);
							}

						}catch(ItinerarioException itex){
							if(itex.getTipo().intValue()==ItinerarioException.FECHA_MENOR)
								DlgMessage.information(Messages.getString("WndTransbordos.information.fechaIncorrecta"));
						}

					}
				});
			}
		});
	}


	private Itinerario getItiItinerario(Itinerario itinerario){
		return itinerario;
	}

	/**
	 * Caraga el detalle del itinerario en una lista para la validación de las rutas al momento del transbordo.
	 * @param idItinerario : Identificador del itinerario
	 * @throws Exception
	 */
	public ArrayList<DetalleItinerario> cargaListDetalleItinerario(Itinerario itinerario) throws Exception{
		 ArrayList<DetalleItinerario> listDetalleItinerario= new ArrayList<>();
		 TreeMap<String, Object> criteriosBusqueda = new TreeMap<>();
		 criteriosBusqueda.put("itinerario", itinerario);
		 criteriosBusqueda.put("estadoRegistro", Constantes.VALUE_ACTIVO);
		 listDetalleItinerario=ServiceLocator.getDetalleItinerarioManager().buscarPorX(criteriosBusqueda, null);
		 return listDetalleItinerario;
	}

	/**
	 * Refresca el Mapa
	 * @throws Exception
	 */
	public void onRefrescarMapa(Button button) throws Exception{
		if(button.getId().equals("btnRefrescar")){
			//DESDE
			if(lbxItinerario.getValue() !=null){
//				if(lbxItinerario.getValue()!=null)
//					ServiceLocator.getTmpOcupacionAsientosManager().desbloquearAsientoByUsuarioHardwareAndItinerario(getUsuarioHardware().getId(), lbxItinerario.getValue());
//				ibxSeleccion.setValue(null);
				cargarMapa(gbxMapaDesde,grdMapaDesde, lbxItinerario.getValue());
				cargaInfoItinerarioDesde(itinerarioDesde.getId());
			}
		}else{
			//HACIA
			if(lbxItinerarioH.getValue() !=null){
//				if(lbxItinerarioH.getValue()!=null)
//					ServiceLocator.getTmpOcupacionAsientosManager().desbloquearAsientoByUsuarioHardwareAndItinerario(getUsuarioHardware().getId(), lbxItinerarioH.getValue());
//				ibxSeleccionH.setValue(null);
				cargarMapa(gbxMapaHacia,grdMapaHacia, lbxItinerarioH.getValue());
				cargaInfoItinerarioHacia(itinerarioHacia.getId());
			}
		}
	}

    private void refrescaAmbosMapas() throws Exception{
    	/*Desmarca seleccion de asientos*/
		ibxSeleccion.setValue(null);
		ibxSeleccionH.setValue(null);

    	onRefrescarMapa(btnRefrescar);
		cargaInfoItinerarioDesde(itinerarioDesde.getId());
		onRefrescarMapa(btnRefrescarH);
		cargaInfoItinerarioHacia(itinerarioHacia.getId());


    }



	/**
	 * Carga el mapa del bus
	 * @param groupbox		: Objeto que contine el grid donde se carga el mapa del bus.
	 * @param grid			: Objeto que contiene el mapa del bus.
	 * @param idItinerario 	: Identificador del itinerario
	 * @throws Exception
	 */
    @SuppressWarnings("deprecation")
	public void cargarMapa(Groupbox groupbox, Grid grid, Long idItinerario) throws Exception{
		DetalleItinerario detalleItinerario= new DetalleItinerario();

		/* Busca itinerario para la carga del Mapa */
		List<DetalleItinerario> list = ServiceLocator.getItinerarioManager().buscarItinerariosMantenimiento(idItinerario,"", "", "", "","", "","");
		detalleItinerario = list.get(0);

		/*Evalua el grid en el que cargará el Mapa*/
//		final WndVerMapaBus verMapaBus= new WndVerMapaBus();

		if(grid.getId().equals("grdMapaDesde")){
			Grid gridPisos = new Grid();
			if(verMapaBus==null)
				verMapaBus= new WndVerMapaBusTransbordo();
			verMapaBus.crearEstructura(detalleItinerario.getItinerario().getServicio().getId(), groupbox, false, detalleItinerario, mapAsientosId, grid, gridPisos,"mapa1",true);
			verMapaBus.addEventListener(Events.ON_SELECT, new EventListener<Event>() {
				@Override
				public void onEvent(Event event) throws Exception {
						if(ibxSeleccion.getValue()!=null && verMapaBus.getAsientoSeleccionado().getNumeroAsiento().intValue()==ibxSeleccion.getValue().intValue())
							ibxSeleccion.setValue(null);
						else
							ibxSeleccion.setValue(verMapaBus.getAsientoSeleccionado().getNumeroAsiento());
						asientoSelectDesde=verMapaBus.getAsientoSeleccionado();

						disableImgDerecha(ibxSeleccion.getValue()==null || itinerarioHacia==null
											|| asientoSelectDesde.getEstadoAsiento()==Constantes.ASIENTO_DISPONIBLE
											|| asientoSelectDesde.getEstadoAsiento()==Constantes.ASIENTO_BLOQUEADO
										 );


						btnVerPasajero.setDisabled(ibxSeleccion.getValue()==null
												   || verMapaBus.getAsientoSeleccionado().getEstadoAsiento()==Constantes.ASIENTO_DISPONIBLE
												   || verMapaBus.getAsientoSeleccionado().getEstadoAsiento()==Constantes.ASIENTO_BLOQUEADO
												  );

						if(btnVerPasajero.isDisabled()){
							btnVerPasajero.setSrc("/resources/mp_preliminarDisabled.png");
							btnVerPasajero.setStyle("cursor:default");
						}else{
							btnVerPasajero.setSrc("/resources/mp_preliminar.png");
							btnVerPasajero.setStyle("cursor:pointer");
						}
//						onClickAsiento(event, detalleItinerario, grdMapaDesde);
				}


			});
			listVentasDesde=verMapaBus.getListVentas();
		}else{
			Grid gridPiso = new Grid();
			if(verMapaBusH==null)
				verMapaBusH= new WndVerMapaBusTransbordo();

			verMapaBusH.crearEstructura(detalleItinerario.getItinerario().getServicio().getId(), groupbox, false, detalleItinerario, mapAsientosId, grid, gridPiso,"mapa2",true);
			verMapaBusH.addEventListener(Events.ON_SELECT, new EventListener<Event>() {

				@Override
				public void onEvent(Event event) throws Exception {
					if(ibxSeleccionH.getValue()!=null && verMapaBusH.getAsientoSeleccionado().getNumeroAsiento().intValue()==ibxSeleccionH.getValue().intValue())
						ibxSeleccionH.setValue(null);
					else
						ibxSeleccionH.setValue(verMapaBusH.getAsientoSeleccionado().getNumeroAsiento());
					asientoSelectHacia=verMapaBusH.getAsientoSeleccionado();

					disableImgIzquierda( ibxSeleccionH.getValue()==null || itinerarioDesde==null
										 || asientoSelectHacia.getEstadoAsiento()==Constantes.ASIENTO_DISPONIBLE
										 || asientoSelectHacia.getEstadoAsiento()==Constantes.ASIENTO_BLOQUEADO
									   );

					btnVerPasajeroH.setDisabled(ibxSeleccionH.getValue()==null
												|| asientoSelectHacia.getEstadoAsiento()==Constantes.ASIENTO_DISPONIBLE
												|| asientoSelectHacia.getEstadoAsiento()==Constantes.ASIENTO_BLOQUEADO
											  );
					if(btnVerPasajeroH.isDisabled()){
						btnVerPasajeroH.setSrc("/resources/mp_preliminarDisabled.png");
						btnVerPasajeroH.setStyle("cursor:default");
					}else{
						btnVerPasajeroH.setSrc("/resources/mp_preliminar.png");
						btnVerPasajeroH.setStyle("cursor:pointer");
					}
				}
			});
			listVentasHacia=verMapaBusH.getListVentas();
		}

		/*Adecua el tamanio de los grid en función al tamanio del mapa*/
		int heightBase=50;
		int heightAsiento=35;
		Integer height_pp=detalleItinerario.getItinerario().getServicio().getNumeroFilasPiso1();
		int height_sp=0;
		if(detalleItinerario.getItinerario().getServicio().getNumeroFilasPiso2() !=null){
			height_sp=(detalleItinerario.getItinerario().getServicio().getNumeroFilasPiso2())+1;
		}

		Integer height= ((height_pp+height_sp)*heightAsiento)+heightBase;
		if(grid.getId().equals("grdMapaDesde"))
			heightMp1=height;
		else heightMp2=height;

		if(heightMp1 > heightMp2 ){
			gbxMapaDesde.setHeight(heightMp1.toString()+"px");
			gbxMapaHacia.setHeight(heightMp1.toString()+"px");
			rwLeft.setHeight(heightMp1-296.50+"px");
			rwRight.setHeight(rwLeft.getHeight());
		}else{
			gbxMapaDesde.setHeight(heightMp2.toString()+"px");
			gbxMapaHacia.setHeight(heightMp2.toString()+"px");
			rwRight.setHeight(heightMp2-296.50+"px");
			rwLeft.setHeight(rwRight.getHeight());
		}
	}



	/**
	 * Carga información del Itinerario "Desde"
	 * @param idItinerario : Identificador del Itinerario desde.
	 * @throws Exception
	 */
	@SuppressWarnings("deprecation")
	public void cargaInfoItinerarioDesde(Long idItinerario) throws Exception{
		itinerarioDesde=ServiceLocator.getItinerarioManager().buscarPorId(idItinerario);
		List<VentaPasaje> lstVentas = ServiceLocator.getVentaPasajesManager().buscarVentasForMapaBus(itinerarioDesde.getId());
		Date salida=Constantes.FORMAT_TIME.parse(itinerarioDesde.getHoraPartida());

		lbOrigen.setValue(itinerarioDesde.getRuta().getOrigen());
		lbDestino.setValue(itinerarioDesde.getRuta().getDestino());
		lbSalida.setValue(Constantes.FORMAT_TIME.format(salida));
		lbFecha.setValue(Constantes.FORMAT_DATE.format(itinerarioDesde.getFechaPartida()));
		if(itinerarioDesde.getBus() !=null)
			lbBus.setValue(itinerarioDesde.getBus().getCodigo());
		else
			lbBus.setValue("");
		lbServicio.setValue(itinerarioDesde.getServicio().getDenominacion());
		lbOcupados.setValue(Util.toNumberFormat(lstVentas.size(),0));


		Integer asientosP1=itinerarioDesde.getServicio().getNumeroAsientosPiso1();
		Integer asientosP2=0;

		if(itinerarioDesde.getServicio().getNumeroAsientosPiso2() !=null)
			asientosP2=itinerarioDesde.getServicio().getNumeroAsientosPiso2();

		int totalAsientos=asientosP1+asientosP2;
		int asientosDisponibles=totalAsientos - lstVentas.size();

		lbDisponibles.setValue(Integer.toString(asientosDisponibles));


		disableImgAllRerecha(lstVentas.size()==0);
//		disableImgDerecha(ibxSeleccion.getValue()==null || itinerarioHacia==null );

		btnVerPasajero.setDisabled(ibxSeleccion.getValue()==null);
		if(btnVerPasajero.isDisabled()){
			btnVerPasajero.setSrc("/resources/mp_preliminarDisabled.png");
			btnVerPasajero.setStyle("cursor:default");
		}else{
			btnVerPasajero.setSrc("/resources/mp_preliminar.png");
			btnVerPasajero.setStyle("cursor:pointer");
		}
	}

	/**
	 * Carga información de Itinerario "Hacia"
	 * @param idItinerario :identificador del itinerario Hacia.
	 * @throws Exception
	 */
	@SuppressWarnings("deprecation")
	public void cargaInfoItinerarioHacia(Long idItinerario) throws Exception{
		itinerarioHacia=ServiceLocator.getItinerarioManager().buscarPorId(idItinerario);
		List<VentaPasaje> lstVentas = ServiceLocator.getVentaPasajesManager().buscarVentasForMapaBus(itinerarioHacia.getId());
		Date salida=Constantes.FORMAT_TIME.parse(itinerarioHacia.getHoraPartida());

		lbOrigenH.setValue(itinerarioHacia.getRuta().getOrigen());
		lbDestinoH.setValue(itinerarioHacia.getRuta().getDestino());
		lbSalidaH.setValue(Constantes.FORMAT_TIME.format(salida));
		lbFechaH.setValue(Constantes.FORMAT_DATE.format(itinerarioHacia.getFechaPartida()));
		if(itinerarioHacia.getBus() !=null)
			lbBusH.setValue(itinerarioHacia.getBus().getCodigo());
		else
			lbBusH.setValue("");
		lbServicioH.setValue(itinerarioHacia.getServicio().getDenominacion());
		lbOcupadosH.setValue(Util.toNumberFormat(lstVentas.size(),0));

		Integer asientosP1=itinerarioHacia.getServicio().getNumeroAsientosPiso1();
		Integer asientosP2=0;

		if(itinerarioHacia.getServicio().getNumeroAsientosPiso2() !=null)
			asientosP2=itinerarioHacia.getServicio().getNumeroAsientosPiso2();

		int totalAsientos=asientosP1+asientosP2;
		int asientosDisponibles=totalAsientos - lstVentas.size();

		lbDisponiblesH.setValue(Integer.toString(asientosDisponibles));

		disableImgAllIzquierda(lstVentas.size()==0);
//		disableImgIzquierda(ibxSeleccionH.getText().trim().isEmpty() || itinerarioDesde==null);

		if(ibxSeleccion.getValue()==null ){
			btnVerPasajeroH.setDisabled(true);
			btnVerPasajeroH.setSrc("/resources/mp_preliminarDisabled.png");
			btnVerPasajeroH.setStyle("cursor:default");
		}else{
			btnVerPasajeroH.setDisabled(false);
			btnVerPasajeroH.setSrc("/resources/mp_preliminar.png");
			btnVerPasajeroH.setStyle("cursor:pointer");
		}
	}

	/**
	 * Realiza el proceso del Transbordo x pasajero de izquiera a derecha
	 * @throws Exception
	 */
	public void transbordarDeizquierdaADerecha()throws Exception{
		try{
			if(itinerarioDesde==null || itinerarioHacia==null)
				throw new NoSeleccionItinerariosException();
			else if(ibxSeleccion.getValue() ==null)//Valida la seleccion del asiento a transbordar
				throw new NumeroAsientoNullException();
			else if (!(asientoSelectDesde.getEstadoAsiento().equals(Constantes.ASIENTO_VENDIDO) || asientoSelectDesde.getEstadoAsiento().equals(Constantes.ASIENTO_RESERVADO)))
				throw new TransbordoAsientoDesocupadoException();
			else if(ibxSeleccionH.getValue()==null)//Validad seleccion del asiento dende se realizará el transbordo.
				throw new TransbordoAsientoHaciaNullException();

			boolean flag=false;

			/*Valida si el asiento esta disponible*/
			onRefrescarMapa(btnRefrescarH);
			for(VentaPasaje ventas : listVentasHacia){
				if(ibxSeleccionH.getValue().equals(ventas.getNumeroAsiento()))
					throw new TransbordoAsientoOcupadoException();
			}

			Integer idRuta_D=0;
			/*Valida si la venta no tiene asiciado un manifiesto - (verificar validacion del manifiesto)*/
			VentaPasaje ventaPasaje = new VentaPasaje();
			for(VentaPasaje ventas: listVentasDesde){
				if(ibxSeleccion.getValue().equals(ventas.getNumeroAsiento())){
					idRuta_D=ventas.getRuta().getId();

					//Valida manifiesto impreso
					if (ServiceLocator.getDetalleManifiestoManager().validarVentaManifiesto(ventas.getId()))
						throw new ServicioDespachadoException();

					ventaPasaje=ServiceLocator.getVentaPasajesManager().buscarPorId(ventas.getId());
					break;
				}
			}

			/*Valida si las rutas son las mismas*/
			for(DetalleItinerario detalleItinerario: listDetItiHacia){
				if(idRuta_D.equals(detalleItinerario.getRuta().getId()))
					flag=true;
			}
			if(!(flag))
				throw new TransbordoRutasDiferentesException();

			/*Genera el transbodo*/
			Integer numeroAsiento= ibxSeleccionH.getValue();
			Long idVentaPasaje = ventaPasaje.getId();
			ServiceLocator.getVentaPasajesManager().transbordarPax(numeroAsiento, idVentaPasaje, itinerarioHacia);

			/*Realiza el insert a la tabla transbordo*/
			onSaveTransbordo(ventaPasaje, itinerarioDesde, itinerarioHacia, ibxSeleccion.getValue(), numeroAsiento);
			
			//Guardar Tracking MAOE 02/03/2024
			guardarTracking(ventaPasaje, itinerarioHacia, asientoSelectHacia.getPiso(), numeroAsiento);

			/*Desbloque el asiento*/
			ServiceLocator.getTmpOcupacionAsientosManager().desbloquearAsiento(getUsuarioHardware().getId(), lbxItinerarioH.getValue(), ibxSeleccionH.getValue().intValue());

			/*Desmarca seleccion de asientos*/
			ibxSeleccion.setValue(null);
			ibxSeleccionH.setValue(null);

			/*Refesca Mapas despues de hacer echo el tranbordo*/
			verMapaBus=null;
			verMapaBusH=null;
			refrescaAmbosMapas();
			disableImgDerecha(true);

		}catch (NoSeleccionItinerariosException nsiex){
			DlgMessage.information(Messages.getString("WndTransbordos.information.noSeleccionoItinerarios"));
		}catch (NumeroAsientoNullException nanex){
			DlgMessage.information(Messages.getString("WndTransbordos.information.noSeleccionAsientoOrigen"));
		}catch (TransbordoAsientoDesocupadoException nadex){
			DlgMessage.information(Messages.getString("WndTransbordos.information.asientoDesocupado"));
		}catch(TransbordoAsientoHaciaNullException nahnex){
			DlgMessage.information(Messages.getString("WndTransbordos.information.noSeleccionoAsientoDestino"));
		}catch (ServicioDespachadoException sdex){
			DlgMessage.information(Messages.getString("WndTransbordos.information.itinerarioConDespacho"));
		}catch(TransbordoAsientoOcupadoException taoex){
			DlgMessage.information(Messages.getString("WndTransbordos.information.asientoOcupado"));
		}catch(TransbordoRutasDiferentesException trdex){
			DlgMessage.information(Messages.getString("WndTransbordos.information.rutasDiferentes"));
		}catch (Exception ex) {
			DlgMessage.error(this.getClass().getName()+" "+ex.getMessage());
			ex.printStackTrace();
		}
	}



	/**
	 *  Realiza el proceso del Transbordo x pasajero de Izquiera a Derecha.
	 * @throws Exception
	 */
	public void transbordarDeDerechaAIzquierda()throws Exception{
		try{
			if(itinerarioDesde==null || itinerarioHacia==null)
				throw new NoSeleccionItinerariosException();
			else if(ibxSeleccionH.getValue() ==null)
				throw new NumeroAsientoNullException();
			else if(ibxSeleccion.getValue()==null)
				throw new TransbordoAsientoBNullException();
			else if (!(asientoSelectHacia.getEstadoAsiento().equals(Constantes.ASIENTO_VENDIDO) || asientoSelectHacia.getEstadoAsiento().equals(Constantes.ASIENTO_RESERVADO)))
				throw new TransbordoAsientoDesocupadoException();
			boolean flag=false;

			/*Valida si el asiento esta disponible*/
			//onRefrescarMapaDesde();//Refresca antes de realizar el transbordo.
			onRefrescarMapa(btnRefrescar);
			for(VentaPasaje ventas : listVentasDesde){
				if(ibxSeleccion.getValue().equals(ventas.getNumeroAsiento()))
					throw new TransbordoAsientoOcupadoException();
			}

			Integer idRuta_H=0;
			/*Valida si la venta no tiene asiciado un manifiesto - (verificar validacion del manifiesto)*/
			VentaPasaje ventaPasaje = new VentaPasaje();
			for(VentaPasaje ventas: listVentasHacia){
				if(ibxSeleccionH.getValue().equals(ventas.getNumeroAsiento())){
					ventaPasaje=ServiceLocator.getVentaPasajesManager().buscarPorId(ventas.getId());
					idRuta_H=ventas.getRuta().getId();

					if(ServiceLocator.getDetalleManifiestoManager().validarVentaManifiesto(ventaPasaje.getId()))
						throw new ServicioDespachadoException();
//					if (!(ventaPasaje.getManifiesto()==null))
//						throw new ServicioDespachadoException();
					break;
				}
			}

			/*Valida si las rutas son las mismas*/
			for(DetalleItinerario detalleItinerario: listDetItiDesde){
				if(idRuta_H.equals(detalleItinerario.getRuta().getId()))
					flag=true;
			}
			if(!(flag))
				throw new TransbordoRutasDiferentesException();

			/*Genera transbordo*/
			Integer numeroAsiento= ibxSeleccion.getValue();
			Long idVentaPasaje = ventaPasaje.getId();
			ServiceLocator.getVentaPasajesManager().transbordarPax(numeroAsiento, idVentaPasaje, itinerarioDesde);
			onSaveTransbordo(ventaPasaje, itinerarioHacia, itinerarioDesde, ibxSeleccionH.getValue(), numeroAsiento);
			
			//Guardar Tracking MAOE 02/03/2024
			guardarTracking(ventaPasaje, itinerarioDesde, asientoSelectHacia.getPiso(), numeroAsiento);

			/*Desbloque el asiento*/
			ServiceLocator.getTmpOcupacionAsientosManager().desbloquearAsiento(getUsuarioHardware().getId(), lbxItinerario.getValue(), ibxSeleccion.getValue().intValue());

			/*Desmarca seleccion de asientos*/
			ibxSeleccion.setValue(null);
			ibxSeleccionH.setValue(null);

			/*Refesca Mapas despues de hacer echo el tranbordo*/
			verMapaBus=null;
			verMapaBusH=null;
			refrescaAmbosMapas();
			disableImgIzquierda(true);

		}catch (NoSeleccionItinerariosException nsiex){
			DlgMessage.information(Messages.getString("WndTransbordos.information.noSeleccionoItinerarios"));
		}catch (NumeroAsientoNullException nanex){
			DlgMessage.information(Messages.getString("WndTransbordos.information.noSeleccionAsientoOrigen"));
		}catch (TransbordoAsientoDesocupadoException nadex){
			DlgMessage.information(Messages.getString("WndTransbordos.information.asientoDesocupado"));
		}catch(TransbordoAsientoHaciaNullException nahnex){
			DlgMessage.information(Messages.getString("WndTransbordos.information.noSeleccionoAsientoDestino"));
		}catch (ServicioDespachadoException sdex){
			DlgMessage.information(Messages.getString("WndTransbordos.information.itinerarioConDespacho"));
		}catch(TransbordoAsientoOcupadoException taoex){
			DlgMessage.information(Messages.getString("WndTransbordos.information.asientoOcupado"));
		}catch(TransbordoRutasDiferentesException trdex){
			DlgMessage.information(Messages.getString("WndTransbordos.information.rutasDiferentes"));
		}catch (Exception ex) {
			DlgMessage.error(this.getClass().getName()+" "+ex.getMessage());
			ex.printStackTrace();
		}
	}

	public void guardarTracking(VentaPasaje ventaPasaje, Itinerario itinerarioHacia, Integer numPiso, Integer numAsiento)throws Exception{
		try{
			MovimientoPasajes trackingIda = new MovimientoPasajes();
			
			trackingIda.setVentaPasaje(ventaPasaje);
			trackingIda.setOperacion("TRANSBORDO");
			trackingIda.setFechaOperacion(Util.DatetoString(new Date(), "dd/MM/yyyy"));
			trackingIda.setServicio(itinerarioHacia.getServicio());
			trackingIda.setRuta(itinerarioHacia.getRuta());
			trackingIda.setAgenciaEmbarque(ventaPasaje.getAgenciaPartida());
			trackingIda.setFechaEmbarque(Util.DatetoString(itinerarioHacia.getFechaPartida(), "dd/MM/yyyy"));
			String strHorEmbarque = UtilData.obtenerHoraEmbarque( itinerarioHacia.getId(), ventaPasaje.getAgenciaPartida().getId());
			trackingIda.setHoraEmbarque( strHorEmbarque ==null ? UtilData.obtenerHoraEmbarque( itinerarioHacia.getId(), itinerarioHacia.getAgenciaPartida().getId()) : strHorEmbarque);
			trackingIda.setNumeroPiso(numPiso);
			trackingIda.setNumeroAsiento(numAsiento);
			trackingIda.setImportePagado(ventaPasaje.getImportePagado());
			trackingIda.setTipoFormaPago(ventaPasaje.getTipoFormaPago());
			trackingIda.setEstadoRegistro(Constantes.VALUE_ACTIVO);
			UtilData.auditarRegistro(trackingIda, getUsuario(), Executions.getCurrent());
			ServiceLocator.getMovimientoPasajesManager().guardar(trackingIda);
			
		}catch(Exception e) {
			DlgMessage.error(this.getClass().getName()+" "+e.getMessage());
			e.printStackTrace();			
		}
	}	
	
	/**
	 * Busca asientos ocupados el bus que recivirá el transbordo
	 * @param button   : Indica el mapa donde se ara la validación.
	 * @param lisVentas : listado de ventas a transvordad
	 * @return asientos ocupados.
	 * @throws Exception
	 */
	private String getAsientosOcupados(Button button, List<VentaPasaje> lisVentas) throws Exception{
		//btnRefrescarH= indica que el transborde es izquierda a Derecha
		//btnRefrescar= indica que el transborde es Derecha a izquierda

		listAsientosOcupados = new ArrayList<>();
		String asientosOcupados="";
		onRefrescarMapa(button);
		for(VentaPasaje venta: button.getId().equals("btnRefrescarH")? listVentasDesde: listVentasHacia){
			Integer numAsiento=venta.getNumeroAsiento();

			for(VentaPasaje ventas:button.getId().equals("btnRefrescarH")? listVentasHacia: listVentasDesde){
				Integer numAsiento_MpDr=ventas.getNumeroAsiento();

				if(numAsiento.equals(numAsiento_MpDr)){
					Asiento asiento= new Asiento();
					asiento.setNumeroAsiento(numAsiento_MpDr);
					listAsientosOcupados.add(asiento);

					if(asientosOcupados=="")
						asientosOcupados=numAsiento_MpDr.toString();
					else
						asientosOcupados+=","+numAsiento_MpDr.toString();
					break;
				}
			}
		}
		return asientosOcupados;
	}

	/**
	 * valida si asientos a transbordar exceden la capacidad del bus que recivirá el transbordo.
	 * @param isDesde : (true) Indica que se validara en el mapa Izquierdo, (false) Indica que se ara la validación en el mapa Derecho.
	 * @return
	 */
	private Integer getAsientosExcedenCapBus(Boolean isDesde){
		int aseintosExedenCapBus=0;
		final Integer capAsientosPiso1_Desde=itinerarioDesde.getServicio().getNumeroAsientosPiso1();
		final Integer capAsientosPiso1_Hacia=itinerarioHacia.getServicio().getNumeroAsientosPiso1();
		if(!isDesde){
			if(capAsientosPiso1_Hacia > capAsientosPiso1_Desde){
				for(VentaPasaje venta: listVentasHacia){
					Integer asiento=venta.getNumeroAsiento();
					if(asiento>capAsientosPiso1_Desde)
						aseintosExedenCapBus ++;
				}
			}
		}else{
			if(capAsientosPiso1_Desde > capAsientosPiso1_Hacia){
				for(VentaPasaje venta: listVentasDesde){
					Integer asiento=venta.getNumeroAsiento();
					if(asiento>capAsientosPiso1_Desde)
						aseintosExedenCapBus ++;
				}
			}
		}
		return aseintosExedenCapBus;
	}

	/**
	 * Valida los asientos que no coinciden con la ruta
	 * @param lisVenta
	 * @param listDetItiDesde
	 * @return lista de asientos que no coinciden con la ruta
	 */
	private List<VentaPasaje> getLisAsientosNoCoincideRuta(List<VentaPasaje> lisVenta, List<DetalleItinerario> listDetIti){
		ArrayList<VentaPasaje> lisAsientosNoCoincideRuta = new ArrayList<>();

		for(VentaPasaje venta: lisVenta){
			Integer idRuta1=venta.getRuta().getId();
			boolean flag=false;

			for(DetalleItinerario detalleItinerario: listDetIti){
				if(idRuta1.equals(detalleItinerario.getRuta().getId())){
					flag=true;
					break;
				}
			}
			if(!(flag))
				lisAsientosNoCoincideRuta.add(venta);
		}
		return lisAsientosNoCoincideRuta;
	}

	/**
	 * Valida si el asiento se transbordara o se omitira.
	 * @param lisAsientosNoCoincideRuta : lista de asientos que no coindiden con la ruta
	 * @param numeroAsiento				: asiento a evaluar para el transbordo.
	 * @return (true)el asiento sera omitido en el transbordo, (false) el asiento sera transbordado.
	 */
	private Boolean asientoNoCoincideRuta(List<VentaPasaje> lisAsientosNoCoincideRuta, Integer numeroAsiento ){
		boolean flag=false;
		for(VentaPasaje ventas: lisAsientosNoCoincideRuta){
			if(numeroAsiento.equals(ventas.getNumeroAsiento()))
				flag=true;
		}
		return flag;
	}


	/**
	 * Transborda todos los pasajeros de Izquiera a Derecha
	 * @throws Exception
	 */
	public void transbordarAllDeIzquieraADerecha() throws Exception{
		try{
			if(itinerarioDesde==null || itinerarioHacia==null)
				throw new NoSeleccionItinerariosException();
			else if(itinerarioDesde.getId().equals(itinerarioHacia.getId()))
				throw new TransbordoNoPermitidoExeption();

			if(listVentasDesde.size()>0) {
				final Integer capAsientosPiso1_MapaDer=itinerarioHacia.getServicio().getNumeroAsientosPiso1();
				final String asientosOcupados=getAsientosOcupados(btnRefrescarH, listVentasDesde);
				final Integer aseintosExedenCapBus = getAsientosExcedenCapBus(true);

				/*valida si hay asientos que no coinciden con la ruta del bus que recibirá el transbordo*/
				final List<VentaPasaje> lisAsientosNoCoincideRuta=getLisAsientosNoCoincideRuta(listVentasDesde, listDetItiHacia);
				String asientosNoCoincideRuta="";
				for(VentaPasaje ventas: lisAsientosNoCoincideRuta){
					if(asientosNoCoincideRuta=="")
						asientosNoCoincideRuta=ventas.getNumeroAsiento().toString();
					else asientosNoCoincideRuta+=","+ventas.getNumeroAsiento().toString();
				}

				final String fasientosNoCoincideRuta=asientosNoCoincideRuta;

				Messagebox.show("Se van ha transbordar todos los asientos, ¿Realmente desea continuar ?", DlgMessage.NOMBREAPLICACION, DlgMessage.BTN_YESNO, Messagebox.QUESTION,DlgMessage.BTN_DEFAULT_NO, new EventListener<Event>(){
					@Override
					public void onEvent(Event event) throws Exception {
						// TODO Auto-generated method stub
						if(event.getName().equals("onYes")){
							if(lisAsientosNoCoincideRuta.size()>0){
								Messagebox.show("Los asientos ("+fasientosNoCoincideRuta+"), no coinciden con la ruta. ¿Desea omitirlos y continuar con el transbordo?", DlgMessage.NOMBREAPLICACION, DlgMessage.BTN_YESNO, Messagebox.QUESTION, new EventListener<Event>() {
									@Override
									public void onEvent(Event event) throws Exception {
										if(event.getName().equals("onYes")){
											if(asientosOcupados==""){
												if(aseintosExedenCapBus > 0){
													Messagebox.show("Existe(n) "+aseintosExedenCapBus+" asiento(s) mayor(es) a la capacidad del Bus donde se realizará el transbordo. ¿Desea omitir y continuar con el transbordo de los demás asientos?", DlgMessage.NOMBREAPLICACION, DlgMessage.BTN_YESNO, Messagebox.QUESTION, new EventListener<Event>() {
														@Override
														public void onEvent(Event event)throws Exception {
															if(event.getName().equals("onYes")){
																//bloquea asientos del itinerario a donde se va ha realizar el transbordo
																bloqueaAsientosDestino(capAsientosPiso1_MapaDer, listVentasDesde, lisAsientosNoCoincideRuta,itinerarioHacia,asientosOcupados);

																//Realiza el transbordo
																for(VentaPasaje venta_MpIz: listVentasDesde){
																	Integer numeroAsiento= venta_MpIz.getNumeroAsiento();
																	Long idVentaPasaje = venta_MpIz.getId();
																	if (!(numeroAsiento > capAsientosPiso1_MapaDer)){
																		/*Valida asientos que no coinciden con la ruta de "HACIA"*/
																		Boolean flag=asientoNoCoincideRuta(lisAsientosNoCoincideRuta, numeroAsiento);
																		if(!(flag)){
																			ServiceLocator.getVentaPasajesManager().transbordarPax(numeroAsiento, idVentaPasaje, itinerarioHacia);
																			onSaveTransbordo(venta_MpIz, itinerarioDesde, itinerarioHacia, numeroAsiento, numeroAsiento);
																			guardarTracking(venta_MpIz, itinerarioHacia, venta_MpIz.getNumeroPiso(), numeroAsiento);
																		}
																	}

																}
																/*Desbloquea asientos bloqueados al incio del proceso*/
																desbloqueartos(listVentasDesde, itinerarioHacia);
																/*Refresca Mapas despues de hacer echo el tranbordo*/
																refrescaAmbosMapas();
																disableImgDerecha(true);
															}
														}
													});
												}else{
													//bloquea asientos del itinerario a donde se va ha realizar el transbordo
													bloqueaAsientosDestino(capAsientosPiso1_MapaDer, listVentasDesde, lisAsientosNoCoincideRuta,itinerarioHacia,asientosOcupados);

													//Realiza el transbordo
													for(VentaPasaje venta_MpIz: listVentasDesde){
														Integer numeroAsiento= venta_MpIz.getNumeroAsiento();
														Long idVentaPasaje = venta_MpIz.getId();
														/*Valida asientos que no coinciden con la ruta de "HACIA"*/
														Boolean flag=asientoNoCoincideRuta(lisAsientosNoCoincideRuta, numeroAsiento);
														if(!(flag)){
															ServiceLocator.getVentaPasajesManager().transbordarPax(numeroAsiento, idVentaPasaje, itinerarioHacia);
															onSaveTransbordo(venta_MpIz, itinerarioDesde, itinerarioHacia, numeroAsiento, numeroAsiento);
															guardarTracking(venta_MpIz, itinerarioHacia, venta_MpIz.getNumeroPiso(), numeroAsiento);
														}
													}
													/*Desbloque asientos bloqueados al incio del proceso*/
													desbloqueartos(listVentasDesde, itinerarioHacia);
													/*Refesca Mapas despues de hacer echo el tranbordo*/
													refrescaAmbosMapas();
													disableImgDerecha(true);
												}
											}else{
												//Consulta al usuario si desea continuar o cancelar con el transbordo.
												final ArrayList<Asiento> listAsientosOcupadosF=listAsientosOcupados;
												Messagebox.show("Los siguientes asientos del Bus donde se realizará el transbordo están ocupados: ("+asientosOcupados+"). ¿Desea omitir estos asientos y continuar con el resto ?", DlgMessage.NOMBREAPLICACION, DlgMessage.BTN_YESNO, Messagebox.QUESTION, new EventListener<Event>() {
													@Override
													public void onEvent(Event event) throws Exception {
														if(event.getName().equals("onYes")){
															if(aseintosExedenCapBus > 0){
																Messagebox.show("Existe(n) "+aseintosExedenCapBus+" asiento(s) mayor(es) a la capacidad del Bus donde se realizará el transbordo. ¿Desea omitir y continuar con el transbordo de los demas asientos?", DlgMessage.NOMBREAPLICACION, DlgMessage.BTN_YESNO, Messagebox.QUESTION, new EventListener<Event>() {
																	@Override
																	public void onEvent(Event event)throws Exception {
																		if(event.getName().equals("onYes")){
																			//bloquea asientos del itinerario a donde se va ha realizar el transbordo
																			bloqueaAsientosDestino(capAsientosPiso1_MapaDer, listVentasDesde, lisAsientosNoCoincideRuta,itinerarioHacia,asientosOcupados);

																			//Realiza el transbordo
																			for(VentaPasaje venta_MpIz: listVentasDesde){
																				Integer numeroAsiento= venta_MpIz.getNumeroAsiento();
																				Long idVentaPasaje = venta_MpIz.getId();
																				boolean asientoOcupado=false;//false(el asiento esta desocupado, (true) el asiento esta ocupado). flag que omite el transbordo a los asientos ocupados.
																				for (Asiento element : listAsientosOcupadosF) {
																					if(element.getNumeroAsiento().equals(numeroAsiento)){
																						asientoOcupado=true;
																						break;
																					}

																				}
																				if (!(asientoOcupado)){
																					if (!(numeroAsiento > capAsientosPiso1_MapaDer)){
																						/*Valida asientos que no coinciden con la ruta de "HACIA"*/
																						Boolean flag=asientoNoCoincideRuta(lisAsientosNoCoincideRuta, numeroAsiento);
																						if(!(flag)){
																							ServiceLocator.getVentaPasajesManager().transbordarPax(numeroAsiento, idVentaPasaje, itinerarioHacia);
																							onSaveTransbordo(venta_MpIz, itinerarioDesde, itinerarioHacia, numeroAsiento, numeroAsiento);
																							guardarTracking(venta_MpIz, itinerarioHacia, venta_MpIz.getNumeroPiso(), numeroAsiento);
																						}
																					}
																				}

																			}
																			/*Desbloque asientos bloqueados al incio del proceso*/
																			desbloqueartos(listVentasDesde, itinerarioHacia);
																			/*Refesca Mapas despues de hacer echo el tranbordo*/
																			refrescaAmbosMapas();
																			disableImgDerecha(true);
																		}
																	}
																});
															}else{
																//bloquea asientos del itinerario a donde se va ha realizar el transbordo
																bloqueaAsientosDestino(capAsientosPiso1_MapaDer, listVentasDesde, lisAsientosNoCoincideRuta,itinerarioHacia,asientosOcupados);

																//Realiza el transbordo
																for(VentaPasaje venta_MpIz: listVentasDesde){
																	Integer numeroAsiento= venta_MpIz.getNumeroAsiento();
																	Long idVentaPasaje = venta_MpIz.getId();
																	boolean asientoOcupado=false;//false(el asiento esta desocupado, (true) el asiento esta ocupado). flag que omite el transbordo a los asientos ocupados.
																	for (Asiento element : listAsientosOcupadosF) {
																		if(element.getNumeroAsiento().equals(numeroAsiento)){
																			asientoOcupado=true;
																			break;
																		}

																	}
																	if (!(asientoOcupado)){
																		/*Valida asientos que no coinciden con la ruta de "HACIA"*/
																		Boolean flag=asientoNoCoincideRuta(lisAsientosNoCoincideRuta, numeroAsiento);
																		if(!(flag)){
																			ServiceLocator.getVentaPasajesManager().transbordarPax(numeroAsiento, idVentaPasaje, itinerarioHacia);
																			onSaveTransbordo(venta_MpIz, itinerarioDesde, itinerarioHacia, numeroAsiento, numeroAsiento);
																			guardarTracking(venta_MpIz, itinerarioHacia, venta_MpIz.getNumeroPiso(), numeroAsiento);
																		}
																	}
																}
																/*Desbloque asientos bloqueados al incio del proceso*/
																desbloqueartos(listVentasDesde, itinerarioHacia);
																/*Refesca Mapas despues de hacer echo el tranbordo*/
																refrescaAmbosMapas();
																disableImgDerecha(true);
															}
														}
													}
												});
											}
										}
									}
								});
							}else{
								if(asientosOcupados==""){
									if(aseintosExedenCapBus > 0){
										Messagebox.show("Existe(n) "+aseintosExedenCapBus+" asiento(s) mayor(es) a la capacidad del Bus donde se realizará el transbordo. ¿Desea omitir y continuar con el transbordo de los demas asientos?", DlgMessage.NOMBREAPLICACION, DlgMessage.BTN_YESNO, Messagebox.QUESTION, new EventListener<Event>() {
											@Override
											public void onEvent(Event event)throws Exception {
												if(event.getName().equals("onYes")){
													//bloquea asientos del itinerario a donde se va ha realizar el transbordo
													bloqueaAsientosDestino(capAsientosPiso1_MapaDer, listVentasDesde, lisAsientosNoCoincideRuta,itinerarioHacia,asientosOcupados);

													//Realiza el transbordo
													for(VentaPasaje venta_MpIz: listVentasDesde){
														Integer numeroAsiento= venta_MpIz.getNumeroAsiento();
														Long idVentaPasaje = venta_MpIz.getId();
														if (!(numeroAsiento > capAsientosPiso1_MapaDer)){
															ServiceLocator.getVentaPasajesManager().transbordarPax(numeroAsiento, idVentaPasaje, itinerarioHacia);
															onSaveTransbordo(venta_MpIz, itinerarioDesde, itinerarioHacia, numeroAsiento, numeroAsiento);
															guardarTracking(venta_MpIz, itinerarioHacia, venta_MpIz.getNumeroPiso(), numeroAsiento);
														}
													}
													/*Desbloque asientos bloqueados al incio del proceso*/
													desbloqueartos(listVentasDesde, itinerarioHacia);
													/*Refesca Mapas despues de hacer echo el tranbordo*/
													refrescaAmbosMapas();
													disableImgDerecha(true);
												}
											}
										});
									}else{
										//bloquea asientos del itinerario a donde se va ha realizar el transbordo
										bloqueaAsientosDestino(capAsientosPiso1_MapaDer, listVentasDesde, lisAsientosNoCoincideRuta,itinerarioHacia,asientosOcupados);

										//Realiza el transbordo
										for(VentaPasaje venta_MpIz: listVentasDesde){
											Integer numeroAsiento= venta_MpIz.getNumeroAsiento();
											Long idVentaPasaje = venta_MpIz.getId();
											ServiceLocator.getVentaPasajesManager().transbordarPax(numeroAsiento, idVentaPasaje, itinerarioHacia);
											onSaveTransbordo(venta_MpIz, itinerarioDesde, itinerarioHacia, numeroAsiento, numeroAsiento);
											guardarTracking(venta_MpIz, itinerarioHacia, venta_MpIz.getNumeroPiso(), numeroAsiento);
										}
										/*Desbloque asientos bloqueados al incio del proceso*/
										desbloqueartos(listVentasDesde, itinerarioHacia);
										/*Refesca Mapas despues de hacer echo el tranbordo*/
										refrescaAmbosMapas();
										disableImgDerecha(true);
									}
								}else{
									//Consulta al usuario si desea continuar o cancelar con el transbordo.
									final ArrayList<Asiento> listAsientosOcupadosF=listAsientosOcupados;
									Messagebox.show("Los siguientes asientos del Bus donde se realizará el transbordo están ocupados: ("+asientosOcupados+"). ¿Desea omitir estos asientos y continuar con el resto ?", DlgMessage.NOMBREAPLICACION, DlgMessage.BTN_YESNO, Messagebox.QUESTION, new EventListener<Event>() {
										@Override
										public void onEvent(Event event) throws Exception {
											if(event.getName().equals("onYes")){
												if(aseintosExedenCapBus > 0){
													Messagebox.show("Existe(n) "+aseintosExedenCapBus+" asiento(s) mayor(es) a la capacidad del Bus donde se realizará el transbordo. ¿Desea omitir y continuar con el transbordo de los demas asientos?", DlgMessage.NOMBREAPLICACION, DlgMessage.BTN_YESNO, Messagebox.QUESTION, new EventListener<Event>() {
														@Override
														public void onEvent(Event event)throws Exception {
															if(event.getName().equals("onYes")){
																//bloquea asientos del itinerario a donde se va ha realizar el transbordo
																bloqueaAsientosDestino(capAsientosPiso1_MapaDer, listVentasDesde, lisAsientosNoCoincideRuta,itinerarioHacia,asientosOcupados);

																//Realiza el transbordo
																for(VentaPasaje venta_MpIz: listVentasDesde){
																	Integer numeroAsiento= venta_MpIz.getNumeroAsiento();
																	Long idVentaPasaje = venta_MpIz.getId();
																	boolean asientoOcupado=false;//false(el asiento esta desocupado, (true) el asiento esta ocupado). flag que omite el transbordo a los asientos ocupados.
																	for (Asiento element : listAsientosOcupadosF) {
																		if(element.getNumeroAsiento().equals(numeroAsiento)){
																			asientoOcupado=true;
																			break;
																		}
																	}
																	if (!(asientoOcupado)){
																		if (!(numeroAsiento > capAsientosPiso1_MapaDer)){
																			ServiceLocator.getVentaPasajesManager().transbordarPax(numeroAsiento, idVentaPasaje, itinerarioHacia);
																			onSaveTransbordo(venta_MpIz, itinerarioDesde, itinerarioHacia, numeroAsiento, numeroAsiento);
																			guardarTracking(venta_MpIz, itinerarioHacia, venta_MpIz.getNumeroPiso(), numeroAsiento);
																		}
																	}
																}
																/*Desbloque asientos bloqueados al incio del proceso*/
																desbloqueartos(listVentasDesde, itinerarioHacia);
																/*Refesca Mapas despues de hacer echo el tranbordo*/
																refrescaAmbosMapas();
																disableImgDerecha(true);
															}
														}
													});
												}else{
													//bloquea asientos del itinerario a donde se va ha realizar el transbordo
													bloqueaAsientosDestino(capAsientosPiso1_MapaDer, listVentasDesde, lisAsientosNoCoincideRuta,itinerarioHacia,asientosOcupados);

													//Realiza el transbordo
													for(VentaPasaje venta_MpIz: listVentasDesde){
														Integer numeroAsiento= venta_MpIz.getNumeroAsiento();
														Long idVentaPasaje = venta_MpIz.getId();
														boolean asientoOcupado=false;//false(el asiento esta desocupado, (true) el asiento esta ocupado). flag que omite el transbordo a los asientos ocupados.
														for (Asiento element : listAsientosOcupadosF) {
															if(element.getNumeroAsiento().equals(numeroAsiento)){
																asientoOcupado=true;
																break;
															}

														}
														if (!(asientoOcupado)){
															ServiceLocator.getVentaPasajesManager().transbordarPax(numeroAsiento, idVentaPasaje, itinerarioHacia);
															onSaveTransbordo(venta_MpIz, itinerarioDesde, itinerarioHacia, numeroAsiento, numeroAsiento);
															guardarTracking(venta_MpIz, itinerarioHacia, venta_MpIz.getNumeroPiso(), numeroAsiento);
														}
													}
													/*Desbloque asientos bloqueados al incio del proceso*/
													desbloqueartos(listVentasDesde, itinerarioHacia);
													/*Refesca Mapas despues de hacer echo el tranbordo*/
													refrescaAmbosMapas();
													disableImgDerecha(true);
												}
											}
										}
									});
								}
							}

						}
					}
				});
			}

		}catch (TransbordoNoPermitidoExeption tnpex){
			DlgMessage.information(Messages.getString("WndTransbordos.information.transbordoNoPermitido"));
		}catch (NoSeleccionItinerariosException nsiex){
			DlgMessage.information(Messages.getString("WndTransbordos.information.noSeleccionoItinerarios"));
		}catch (Exception ex) {
			DlgMessage.error(this.getClass().getName()+" "+ex.getMessage());
			ex.printStackTrace();
		}
	}

	/**
	 * Transborda los pasajeros de derecha a izquierda
	 * @throws Exception
	 */
	public void transbordarAllDeDerechaAIzquierda() throws Exception{
		try{
			if(itinerarioDesde==null || itinerarioHacia==null)
				throw new NoSeleccionItinerariosException();
			else if(itinerarioDesde.getId().equals(itinerarioHacia.getId()))
				throw new TransbordoNoPermitidoExeption();

			if(listVentasHacia.size()>0) {
				final Integer capAsientosPiso1_MapaIzq=itinerarioDesde.getServicio().getNumeroAsientosPiso1();
				final String asientosOcupados=getAsientosOcupados(btnRefrescar, listVentasHacia);
				final Integer aseintosExedenCapBus = getAsientosExcedenCapBus(false);

				/*valida si hay asientos que no coinciden con la ruta del bus que recivirá el transbordo*/
				final List<VentaPasaje> lisAsientosNoCoincideRuta=getLisAsientosNoCoincideRuta(listVentasHacia, listDetItiDesde);
				String asientosNoCoincideRuta="";
				for(VentaPasaje ventas: lisAsientosNoCoincideRuta){
					if(asientosNoCoincideRuta=="")
						asientosNoCoincideRuta=ventas.getNumeroAsiento().toString();
					else asientosNoCoincideRuta+=","+ventas.getNumeroAsiento().toString();
				}

				final String fasientosNoCoincideRuta=asientosNoCoincideRuta;

				Messagebox.show("Se van ha transbordar todos los asientos, ¿Realmente desea continuar ?", DlgMessage.NOMBREAPLICACION, DlgMessage.BTN_YESNO, Messagebox.QUESTION,DlgMessage.BTN_DEFAULT_NO, new EventListener<Event>(){
					@Override
					public void onEvent(Event event) throws Exception {
						// TODO Auto-generated method stub
						if(event.getName().equals("onYes")){
							if(lisAsientosNoCoincideRuta.size()>0){
								Messagebox.show("Los asientos ("+fasientosNoCoincideRuta+"), no coinciden con la ruta. ¿Desea omitirlos y continuar con el transbordo?", DlgMessage.NOMBREAPLICACION, DlgMessage.BTN_YESNO, Messagebox.QUESTION, new EventListener<Event>() {
									@Override
									public void onEvent(Event event) throws Exception {
										if(event.getName().equals("onYes")){
											if(asientosOcupados==""){
												if(aseintosExedenCapBus > 0){
													Messagebox.show("Existe(n) "+aseintosExedenCapBus+" asiento(s) mayor(es) a la capacidad del Bus donde se realizará el transbordo. ¿Desea omitir y continuar con el transbordo de los demas asientos?", DlgMessage.NOMBREAPLICACION, DlgMessage.BTN_YESNO, Messagebox.QUESTION, new EventListener<Event>() {
														@Override
														public void onEvent(Event event)throws Exception {
															if(event.getName().equals("onYes")){
																//bloquea asientos del itinerario a donde se va ha realizar el transbordo
																bloqueaAsientosDestino(capAsientosPiso1_MapaIzq, listVentasHacia, lisAsientosNoCoincideRuta,itinerarioDesde,asientosOcupados);

																//Realiza el transbordo
																for(VentaPasaje venta_MpDr: listVentasHacia){
																	Integer numeroAsiento= venta_MpDr.getNumeroAsiento();
																	Long idVentaPasaje = venta_MpDr.getId();
																	if (!(numeroAsiento > capAsientosPiso1_MapaIzq)){
																		/*Valida asientos que no coinciden con la ruta de "HACIA"*/
																		Boolean flag=asientoNoCoincideRuta(lisAsientosNoCoincideRuta, numeroAsiento);
																		if(!(flag)){
																			ServiceLocator.getVentaPasajesManager().transbordarPax(numeroAsiento, idVentaPasaje, itinerarioDesde);
																			onSaveTransbordo(venta_MpDr, itinerarioHacia, itinerarioDesde, numeroAsiento, numeroAsiento);
																			guardarTracking(venta_MpDr, itinerarioDesde, venta_MpDr.getNumeroPiso(), numeroAsiento);
																		}
																	}
																}
																/*Desbloquea asientos bloqueados al incio del proceso*/
																desbloqueartos(listVentasHacia, itinerarioDesde);
																/*Refresca Mapas despues de hacer echo el tranbordo*/
																refrescaAmbosMapas();
																disableImgIzquierda(true);
															}
														}
													});
												}else{
													//bloquea asientos del itinerario a donde se va ha realizar el transbordo
													bloqueaAsientosDestino(capAsientosPiso1_MapaIzq, listVentasHacia, lisAsientosNoCoincideRuta,itinerarioDesde,asientosOcupados);

													//Realiza el transbordo
													for(VentaPasaje venta_MpDr: listVentasHacia){
														Integer numeroAsiento= venta_MpDr.getNumeroAsiento();
														Long idVentaPasaje = venta_MpDr.getId();
														/*Valida asientos que no coinciden con la ruta de "HACIA"*/
														Boolean flag=asientoNoCoincideRuta(lisAsientosNoCoincideRuta, numeroAsiento);
														if(!(flag)){
															ServiceLocator.getVentaPasajesManager().transbordarPax(numeroAsiento, idVentaPasaje, itinerarioDesde);
															onSaveTransbordo(venta_MpDr, itinerarioHacia, itinerarioDesde, numeroAsiento, numeroAsiento);
															guardarTracking(venta_MpDr, itinerarioDesde, venta_MpDr.getNumeroPiso(), numeroAsiento);
														}
													}
													/*Desbloque asientos bloqueados al incio del proceso*/
													desbloqueartos(listVentasHacia, itinerarioDesde);
													/*Refesca Mapas despues de hacer echo el tranbordo*/
													refrescaAmbosMapas();
													disableImgIzquierda(true);
												}
											}else{
												//Consulta al usuario si desea continuar o cancelar con el transbordo.
												final ArrayList<Asiento> listAsientosOcupadosF=listAsientosOcupados;
												Messagebox.show("Los siguientes asientos del Bus donde se realizará el transbordo están ocupados: "+asientosOcupados+". ¿Desea omitir estos asientos y continuar con el resto ?", DlgMessage.NOMBREAPLICACION, DlgMessage.BTN_YESNO, Messagebox.QUESTION, new EventListener<Event>() {
													@Override
													public void onEvent(Event event) throws Exception {
														if(event.getName().equals("onYes")){
															if(aseintosExedenCapBus > 0){
																Messagebox.show("Existe(n) "+aseintosExedenCapBus+" asiento(s) mayor(es) a la capacidad del Bus donde se realizará el transbordo. ¿Desea omitir y continuar con el transbordo de los demas asientos?", DlgMessage.NOMBREAPLICACION, DlgMessage.BTN_YESNO, Messagebox.QUESTION, new EventListener<Event>() {
																	@Override
																	public void onEvent(Event event)throws Exception {
																		if(event.getName().equals("onYes")){
																			//bloquea asientos del itinerario a donde se va ha realizar el transbordo
																			bloqueaAsientosDestino(capAsientosPiso1_MapaIzq, listVentasHacia, lisAsientosNoCoincideRuta,itinerarioDesde,asientosOcupados);

																			//Realiza el transbordo
																			for(VentaPasaje venta_MpDr: listVentasHacia){
																				Integer numeroAsiento= venta_MpDr.getNumeroAsiento();
																				Long idVentaPasaje = venta_MpDr.getId();
																				boolean asientoOcupado=false;//false(el asiento esta desocupado, (true) el asiento esta ocupado). flag que omite el transbordo a los asientos ocupados.
																				for (Asiento element : listAsientosOcupadosF) {
																					if(element.getNumeroAsiento().equals(numeroAsiento)){
																						asientoOcupado=true;
																						break;
																					}
																				}
																				if (!(asientoOcupado)){
																					if (!(numeroAsiento > capAsientosPiso1_MapaIzq)){
																						/*Valida asientos que no coinciden con la ruta de "HACIA"*/
																						Boolean flag=asientoNoCoincideRuta(lisAsientosNoCoincideRuta, numeroAsiento);
																						if(!(flag)){
																							ServiceLocator.getVentaPasajesManager().transbordarPax(numeroAsiento, idVentaPasaje, itinerarioDesde);
																							onSaveTransbordo(venta_MpDr, itinerarioHacia, itinerarioDesde, numeroAsiento, numeroAsiento);
																							guardarTracking(venta_MpDr, itinerarioDesde, venta_MpDr.getNumeroPiso(), numeroAsiento);
																						}
																					}
																				}

																			}
																			/*Desbloque asientos bloqueados al incio del proceso*/
																			desbloqueartos(listVentasHacia, itinerarioDesde);
																			/*Refesca Mapas despues de hacer echo el tranbordo*/
																			refrescaAmbosMapas();
																			disableImgIzquierda(true);
																		}
																	}
																});
															}else{
																//bloquea asientos del itinerario a donde se va ha realizar el transbordo
																bloqueaAsientosDestino(capAsientosPiso1_MapaIzq, listVentasHacia, lisAsientosNoCoincideRuta,itinerarioDesde,asientosOcupados);

																//Realiza el transbordo
																for(VentaPasaje venta_MpDr: listVentasHacia){
																	Integer numeroAsiento= venta_MpDr.getNumeroAsiento();
																	Long idVentaPasaje = venta_MpDr.getId();
																	boolean asientoOcupado=false;//false(el asiento esta desocupado, (true) el asiento esta ocupado). flag que omite el transbordo a los asientos ocupados.
																	for (Asiento element : listAsientosOcupadosF) {
																		if(element.getNumeroAsiento().equals(numeroAsiento)){
																			asientoOcupado=true;
																			break;
																		}
																	}
																	if (!(asientoOcupado)){
																		/*Valida asientos que no coinciden con la ruta de "HACIA"*/
																		Boolean flag=asientoNoCoincideRuta(lisAsientosNoCoincideRuta, numeroAsiento);
																		if(!(flag)){
																			ServiceLocator.getVentaPasajesManager().transbordarPax(numeroAsiento, idVentaPasaje, itinerarioDesde);
																			onSaveTransbordo(venta_MpDr, itinerarioHacia, itinerarioDesde, numeroAsiento, numeroAsiento);
																			guardarTracking(venta_MpDr, itinerarioDesde, venta_MpDr.getNumeroPiso(), numeroAsiento);
																		}
																	}
																}
																/*Desbloque asientos bloqueados al incio del proceso*/
																desbloqueartos(listVentasHacia, itinerarioDesde);
																/*Refesca Mapas despues de hacer echo el tranbordo*/
																refrescaAmbosMapas();
																disableImgIzquierda(true);
															}
														}
													}
												});
											}
										}
									}
								});
							}else{
								if(asientosOcupados==""){
									if(aseintosExedenCapBus > 0){
										Messagebox.show("Existe(n) "+aseintosExedenCapBus+" asiento(s) mayor(es) a la capacidad del Bus donde se realizará el transbordo. ¿Desea omitir y continuar con el transbordo de los demas asientos?", DlgMessage.NOMBREAPLICACION, DlgMessage.BTN_YESNO, Messagebox.QUESTION, new EventListener<Event>() {
											@Override
											public void onEvent(Event event)throws Exception {
												if(event.getName().equals("onYes")){
													//bloquea asientos del itinerario a donde se va ha realizar el transbordo
													bloqueaAsientosDestino(capAsientosPiso1_MapaIzq, listVentasHacia, lisAsientosNoCoincideRuta,itinerarioDesde,asientosOcupados);

													//Realiza el transbordo
													for(VentaPasaje venta_MpDr: listVentasHacia){
														Integer numeroAsiento= venta_MpDr.getNumeroAsiento();
														Long idVentaPasaje = venta_MpDr.getId();
														if (!(numeroAsiento > capAsientosPiso1_MapaIzq)){
															ServiceLocator.getVentaPasajesManager().transbordarPax(numeroAsiento, idVentaPasaje, itinerarioDesde);
															onSaveTransbordo(venta_MpDr, itinerarioHacia, itinerarioDesde, numeroAsiento, numeroAsiento);
															guardarTracking(venta_MpDr, itinerarioDesde, venta_MpDr.getNumeroPiso(), numeroAsiento);
														}
													}
													/*Desbloque asientos bloqueados al incio del proceso*/
													desbloqueartos(listVentasHacia, itinerarioDesde);
													/*Refesca Mapas despues de hacer echo el tranbordo*/
													refrescaAmbosMapas();
													disableImgIzquierda(true);
												}
											}
										});
									}else{
										//bloquea asientos del itinerario a donde se va ha realizar el transbordo
										bloqueaAsientosDestino(capAsientosPiso1_MapaIzq, listVentasHacia, lisAsientosNoCoincideRuta,itinerarioDesde,asientosOcupados);


										//Realiza el transbordo
										for(VentaPasaje venta_MpDr: listVentasHacia){
											Integer numeroAsiento= venta_MpDr.getNumeroAsiento();
											Long idVentaPasaje = venta_MpDr.getId();
											ServiceLocator.getVentaPasajesManager().transbordarPax(numeroAsiento, idVentaPasaje, itinerarioDesde);
											onSaveTransbordo(venta_MpDr, itinerarioHacia, itinerarioDesde, numeroAsiento, numeroAsiento);
											guardarTracking(venta_MpDr, itinerarioDesde, venta_MpDr.getNumeroPiso(), numeroAsiento);
										}
										/*Desbloque asientos bloqueados al incio del proceso*/
										desbloqueartos(listVentasHacia, itinerarioDesde);
										/*Refesca Mapas despues de hacer echo el tranbordo*/
										refrescaAmbosMapas();
										disableImgIzquierda(true);
									}
								}else{
									//Consulta al usuario si desea continuar o cancelar con el transbordo.
									final ArrayList<Asiento> listAsientosOcupadosF=listAsientosOcupados;
									Messagebox.show("Los siguientes asientos del Bus donde se realizará el transbordo están ocupados: "+asientosOcupados+". ¿Desea omitir estos asientos y continuar con el resto ?", DlgMessage.NOMBREAPLICACION, DlgMessage.BTN_YESNO, Messagebox.QUESTION, new EventListener<Event>() {
										@Override
										public void onEvent(Event event) throws Exception {
											if(event.getName().equals("onYes")){
												if(aseintosExedenCapBus > 0){
													Messagebox.show("Existe(n) "+aseintosExedenCapBus+" asiento(s) mayor(es) a la capacidad del Bus donde se realizará el transbordo. ¿Desea omitir y continuar con el transbordo de los demas asientos?", DlgMessage.NOMBREAPLICACION, DlgMessage.BTN_YESNO, Messagebox.QUESTION, new EventListener<Event>() {
														@Override
														public void onEvent(Event event)throws Exception {
															if(event.getName().equals("onYes")){
																//bloquea asientos del itinerario a donde se va ha realizar el transbordo
																bloqueaAsientosDestino(capAsientosPiso1_MapaIzq, listVentasHacia, lisAsientosNoCoincideRuta,itinerarioDesde,asientosOcupados);


																//Realiza el transbordo
																for(VentaPasaje venta_MpDr: listVentasHacia){
																	Integer numeroAsiento= venta_MpDr.getNumeroAsiento();
																	Long idVentaPasaje = venta_MpDr.getId();
																	boolean asientoOcupado=false;//false(el asiento esta desocupado, (true) el asiento esta ocupado). flag que omite el transbordo a los asientos ocupados.
																	for (Asiento element : listAsientosOcupadosF) {
																		if(element.getNumeroAsiento().equals(numeroAsiento)){
																			asientoOcupado=true;
																			break;
																		}

																	}
																	if (!(asientoOcupado)){
																		if (!(numeroAsiento > capAsientosPiso1_MapaIzq)){
																			ServiceLocator.getVentaPasajesManager().transbordarPax(numeroAsiento, idVentaPasaje, itinerarioDesde);
																			onSaveTransbordo(venta_MpDr, itinerarioHacia, itinerarioDesde, numeroAsiento, numeroAsiento);
																			guardarTracking(venta_MpDr, itinerarioDesde, venta_MpDr.getNumeroPiso(), numeroAsiento);
																		}
																	}
																}
																/*Desbloque asientos bloqueados al incio del proceso*/
																desbloqueartos(listVentasHacia, itinerarioDesde);
																/*Refesca Mapas despues de hacer echo el tranbordo*/
																refrescaAmbosMapas();
																disableImgIzquierda(true);
															}
														}
													});
												}else{
													//bloquea asientos del itinerario a donde se va ha realizar el transbordo
													bloqueaAsientosDestino(capAsientosPiso1_MapaIzq, listVentasHacia, lisAsientosNoCoincideRuta,itinerarioDesde,asientosOcupados);


													//Realiza el transbordo
													for(VentaPasaje venta_MpDr: listVentasHacia){
														Integer numeroAsiento= venta_MpDr.getNumeroAsiento();
														Long idVentaPasaje = venta_MpDr.getId();
														boolean asientoOcupado=false;//false(el asiento esta desocupado, (true) el asiento esta ocupado). flag que omite el transbordo a los asientos ocupados.
														for (Asiento element : listAsientosOcupadosF) {
															if(element.getNumeroAsiento().equals(numeroAsiento)){
																asientoOcupado=true;
																break;
															}

														}
														if (!(asientoOcupado)){
															ServiceLocator.getVentaPasajesManager().transbordarPax(numeroAsiento, idVentaPasaje, itinerarioDesde);
															onSaveTransbordo(venta_MpDr, itinerarioHacia, itinerarioDesde, numeroAsiento, numeroAsiento);
															guardarTracking(venta_MpDr, itinerarioDesde, venta_MpDr.getNumeroPiso(), numeroAsiento);
														}
													}
													/*Desbloque asientos bloqueados al incio del proceso*/
													desbloqueartos(listVentasHacia, itinerarioDesde);
													/*Refesca Mapas despues de hacer echo el tranbordo*/
													refrescaAmbosMapas();
													disableImgIzquierda(true);
												}
											}
										}
									});
								}
							}



						}
					}
				});


			}

		}catch (TransbordoNoPermitidoExeption tnpex){
			DlgMessage.information(Messages.getString("WndTransbordos.information.transbordoNoPermitido"));
		}catch (NoSeleccionItinerariosException nsiex){
			DlgMessage.information(Messages.getString("WndTransbordos.information.noSeleccionoItinerarios"));
		}catch (Exception ex) {
			DlgMessage.error(this.getClass().getName()+" "+ex.getMessage());
			ex.printStackTrace();
		}
	}

	/**
	 * Guarda el transbordo.
	 * @param ventaPasaje		: Objet ventaPasajes
	 * @param itinerarioOrigen	: Objet itinerarioOrigen
	 * @param itinerarioDestino	: Objet itinerarioDestino
	 * @param asientoOrigen		: Número de asiento origen
	 * @param asientoDestino	: Número de asiento destino
	 * @throws Exception
	 */
	private void onSaveTransbordo(VentaPasaje ventaPasaje, Itinerario itinerarioOrigen, Itinerario itinerarioDestino, Integer asientoOrigen, Integer asientoDestino) throws Exception{
		/*Realiza el insert a la trabla transbordo*/
		Transbordo transbordo= new Transbordo();
		transbordo.setVentaPasaje(ventaPasaje);
		transbordo.setItinerarioOrigen(itinerarioOrigen);
		transbordo.setServicioOrigen(itinerarioOrigen.getServicio());
		transbordo.setFechaPartidaOrigen(itinerarioOrigen.getFechaPartida());
		transbordo.setHoraPartidaOrigen(itinerarioOrigen.getHoraPartida());
		transbordo.setNumeroAsientoOrigen(asientoOrigen);

		transbordo.setItinerarioDestino(itinerarioDestino);
		transbordo.setServicioDestino(itinerarioDestino.getServicio());
		transbordo.setFechaPartidaDestino(itinerarioDestino.getFechaPartida());
		transbordo.setHoraPartidaDestino(itinerarioDestino.getHoraPartida());
		transbordo.setNumeroAsientoDestino(asientoDestino);
		transbordo.setEstadoRegistro(Constantes.VALUE_ACTIVO);
		UtilData.auditarRegistro(transbordo, getUsuario(), Executions.getCurrent());
		ServiceLocator.getTransbordoManajer().guardar(transbordo);
	}


	/**
	 * Limpia controles del mapa del lado Izquierdo
	 * @throws Exception
	 */
	private void limpiaControlsMapaDirecho() throws Exception{
		/*desbloque posibles asientos bloquedos*/
		if(itinerarioDesde!=null)
			ServiceLocator.getTmpOcupacionAsientosManager().desbloquearAsientoByUsuarioHardwareAndItinerario(getUsuarioHardware().getId(), itinerarioDesde.getId());

//		Util.limpiarGrid(grdMapaDesde);

		itinerarioDesde=null;
		lbxItinerario.setValue(null);
		lbOrigen.setValue("");
		lbDestino.setValue("");
		lbSalida.setValue("");
		lbFecha.setValue("");
		lbBus.setValue("");
		lbServicio.setValue("");
		lbOcupados.setValue("");
		lbDisponibles.setValue("");
		ibxSeleccion.setValue(null);


	}

	/**
	 * Limpia controles del Mapa derecho
	 * @throws Exception
	 */
	private void limpiaControlsMapaIzquierdo() throws Exception{
		/*desbloque posibles asientos bloquedos*/
		if(itinerarioHacia!=null)
			ServiceLocator.getTmpOcupacionAsientosManager().desbloquearAsientoByUsuarioHardwareAndItinerario(getUsuarioHardware().getId(), itinerarioHacia.getId());

//		Util.limpiarGrid(gritnerarioHacia);

		itinerarioHacia=null;
		lbxItinerarioH.setValue(null);
		lbOrigenH.setValue("");
		lbDestinoH.setValue("");
		lbSalidaH.setValue("");
		lbFechaH.setValue("");
		lbBusH.setValue("");
		lbServicioH.setValue("");
		lbOcupadosH.setValue("");
		lbDisponiblesH.setValue("");
		ibxSeleccionH.setValue(null);

	}

	/**
	 * Realiza el bloque masivo ante un transbordo masivo.
	 * @param capAsientos	: Capasidad de asientos del bus.
	 * @param lstVentas		: Lista de ventas.
	 * @param lisAsientosNoCoincideRuta	: Lista de asientos que no coinciden con la ruta.
	 * @param itiTransbordo	: Itinerario donde se va a realizar el transbordo.
	 * @throws Exception
	 */
	private void bloqueaAsientosDestino(Integer capAsientos, List<VentaPasaje> lstVentas, List<VentaPasaje>lisAsientosNoCoincideRuta, Itinerario itiTransbordo, String asientosOcupados) throws Exception{
		String exceptionAsiento=""; //Para mostra el numero de asiento duplicado

		try{
			String sasientosOcupados[]=asientosOcupados.split(",");
			List<TmpOcupacionAsientos>listTmpOcup=ServiceLocator.getTmpOcupacionAsientosManager().buscarAsientosBloqueados(itiTransbordo.getId());

			/*bloquea asientos del bus a donde se va ha hacer el transbordo*/
			for(VentaPasaje venta: lstVentas){
				Integer numeroAsiento= venta.getNumeroAsiento();
				if (!(numeroAsiento > capAsientos)){
					/*Valida asientos que no coinciden con la ruta de "HACIA"*/
					Boolean flag=asientoNoCoincideRuta(lisAsientosNoCoincideRuta, numeroAsiento);
					if(!(flag)){
						/*	Insertamos el asiento a la tmpOcupacionAsientos	*/
						TmpOcupacionAsientos tmpOcupacionAsientos = new TmpOcupacionAsientos();
						tmpOcupacionAsientos.setUsuarioHardware(getUsuarioHardware());
						tmpOcupacionAsientos.setUsuario(getUsuario());
						tmpOcupacionAsientos.setRuta(venta.getRuta());
						tmpOcupacionAsientos.setItinerario(itiTransbordo);
						tmpOcupacionAsientos.setNumeroAsiento(venta.getNumeroAsiento());
						tmpOcupacionAsientos.setNumeroPiso(venta.getNumeroPiso());
						tmpOcupacionAsientos.setFechaPartida(Util.DatetoString(venta.getFechaPartida(), Constantes.DATE_FORMAT));
						tmpOcupacionAsientos.setHoraPartida(venta.getHoraPartida());
						tmpOcupacionAsientos.setEstadoRegistro(Constantes.VALUE_ACTIVO);
						UtilData.auditarRegistro(tmpOcupacionAsientos, false, getUsuario(), Executions.getCurrent());

						TmpOcupacionAsientosID tmpOcupacionAsientosID = new TmpOcupacionAsientosID();
						tmpOcupacionAsientosID.setIdUsuarioHardware(tmpOcupacionAsientos.getUsuarioHardware().getId());
						tmpOcupacionAsientosID.setIdUsuario(tmpOcupacionAsientos.getUsuario().getId());
						tmpOcupacionAsientosID.setIdRuta(tmpOcupacionAsientos.getRuta().getId());
						tmpOcupacionAsientosID.setIdItinerario(tmpOcupacionAsientos.getItinerario().getId());
						tmpOcupacionAsientosID.setNumeroAsiento(tmpOcupacionAsientos.getNumeroAsiento());
						tmpOcupacionAsientos.setTmpOcupacionAsientosID(tmpOcupacionAsientosID);

						/*Valida que el asiento a bloquear no este ocupado por algún pasajeo*/
						String asientoABloquear=tmpOcupacionAsientosID.getNumeroAsiento().toString();
						boolean bloquear=true;
						for (String asientoOcupado : sasientosOcupados) {
							if(asientoOcupado.equals(asientoABloquear)){
								bloquear=false;
								break;
							}
						}

						/*Valida que el asiento no este bloqueado*/
						if(listTmpOcup.size()>0){
							for(TmpOcupacionAsientos ocupacionAsientos: listTmpOcup){
								String asientoBloqueado=ocupacionAsientos.getNumeroAsiento().toString();
								if(asientoBloqueado.equals(asientoABloquear)){
									bloquear=false;
									break;
								}
							}
						}

						if(bloquear){
							exceptionAsiento=tmpOcupacionAsientos.getNumeroAsiento().toString();
							ServiceLocator.getTmpOcupacionAsientosManager().bloquearAsiento(tmpOcupacionAsientos);
						}
					}
				}
			}

		}catch (DuplicateSeatException dex){
			DlgMessage.information(Messages.getString("WndTransbordos.information.asientosDuplicados")+" ("+exceptionAsiento+"), probablemente hayan quedado asientos bloquedos por la interrupción del proceso.");
			onRefrescarMapa(btnBuscar);
			onRefrescarMapa(btnBuscarH);
		}

	}


	/**
	 * Realiza el desbloque masivo una vez terminado el transbordo.
	 * @param lstVentas		: Lista de ventas.
	 * @param itiTransbordo	: Itinerario donde se realizó el transbordo.
	 * @throws Exception
	 */
	private void desbloqueartos(List<VentaPasaje> lstVentas, Itinerario itiTransbordo) throws Exception{
		for (VentaPasaje venta: lstVentas){
			TmpOcupacionAsientos tmpOcupacion= new TmpOcupacionAsientos();
			tmpOcupacion.setItinerario(itiTransbordo);
			tmpOcupacion.setRuta(venta.getRuta());
			tmpOcupacion.setNumeroAsiento(venta.getNumeroAsiento());
			tmpOcupacion.setNumeroPiso(venta.getNumeroPiso());
			tmpOcupacion.setUsuarioHardware(getUsuarioHardware());

			ServiceLocator.getTmpOcupacionAsientosManager().desbloquearAsiento(tmpOcupacion);
		}
	}


	private void disableImgDerecha(boolean isDisable){
		if(!(isDisable)){
			imgDerercha.setSrc(enabledImgDerecha);
			imgDerercha.setStyle("cursor:pointer");
		}else{
			imgDerercha.setSrc(disabledImgDerecha);
			imgDerercha.setStyle("cursor:default");
		}
	}

	private void disableImgAllRerecha(boolean isDisable){
		if(!(isDisable)){
			imgAllDerecha.setSrc(enabledImgAllDerecha);
			imgAllDerecha.setStyle("cursor:pointer");
		}else{
			imgAllDerecha.setSrc(disabledImgAllDerecha);
			imgAllDerecha.setStyle("cursor:default");
		}
	}

	private  void disableImgIzquierda(boolean isDisable){
		if(!(isDisable)){
			imgIzquierda.setSrc(enabledImgIzquierda);
			imgIzquierda.setStyle("cursor:pointer");
		}else{
			imgIzquierda.setSrc(disabledImgIzquierda);
			imgIzquierda.setStyle("cursor:default");
		}
	}

	private void disableImgAllIzquierda(boolean isDisable){
		if(!(isDisable)){
			imgAllIzquierda.setSrc(enabledImgAllIzquierda);
			imgAllIzquierda.setStyle("cursor:pointer");
		}else{
			imgAllIzquierda.setSrc(disabledImgAllIzquierda);
			imgAllIzquierda.setStyle("cursor:default");
		}
	}

	/**
	 * Permite liberar los asientos cuando se cambia de pestaña dentro de la venta
	 */
	public void liberarAsientos(){
		try{
			if(itinerarioDesde!=null)
				ServiceLocator.getTmpOcupacionAsientosManager().desbloquearAsientoByUsuarioHardwareAndItinerario(getUsuarioHardware().getId(),itinerarioDesde.getId());
			if(itinerarioHacia!=null)
				ServiceLocator.getTmpOcupacionAsientosManager().desbloquearAsientoByUsuarioHardwareAndItinerario(getUsuarioHardware().getId(),itinerarioHacia.getId());
		}catch(Exception ex){
			DlgMessage.error(this.getClass().getSimpleName()+" "+ex.getMessage());
		}
	}

}
