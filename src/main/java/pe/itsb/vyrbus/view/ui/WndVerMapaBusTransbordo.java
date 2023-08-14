/**
 * Proyecto		: SISVYR
 * Sistema		: Sistema de Ventas y Reservas
 * Descripci�n	:
 * Autor		: Jos� Abanto
 * Fecha		: 13 ago. 2022
 * Hora			: 04:43:21
 */
package pe.itsb.vyrbus.view.ui;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.dao.DataIntegrityViolationException;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zul.Button;
import org.zkoss.zul.Caption;
import org.zkoss.zul.Column;
import org.zkoss.zul.Columns;
import org.zkoss.zul.Div;
import org.zkoss.zul.Grid;
import org.zkoss.zul.Groupbox;
import org.zkoss.zul.Hbox;
import org.zkoss.zul.Image;
import org.zkoss.zul.Label;
import org.zkoss.zul.Row;
import org.zkoss.zul.Rows;
import org.zkoss.zul.Separator;
import org.zkoss.zul.Window;

import pe.itsb.vyrbus.model.bean.Bus;
import pe.itsb.vyrbus.model.bean.DetalleItinerario;
import pe.itsb.vyrbus.model.bean.MapaBus;
import pe.itsb.vyrbus.model.bean.Servicio;
import pe.itsb.vyrbus.model.bean.TmpOcupacionAsientos;
import pe.itsb.vyrbus.model.bean.TmpOcupacionAsientosID;
import pe.itsb.vyrbus.model.bean.Usuario;
import pe.itsb.vyrbus.model.bean.UsuarioHardware;
import pe.itsb.vyrbus.model.bean.VentaPasaje;
import pe.itsb.vyrbus.service.exceptions.DuplicateSeatException;
import pe.itsb.vyrbus.service.locator.ServiceLocator;
import pe.itsb.vyrbus.service.mappers.Asiento;
import pe.itsb.vyrbus.service.mappers.Cafeteria;
import pe.itsb.vyrbus.service.mappers.Coordenada;
import pe.itsb.vyrbus.service.mappers.Monitor;
import pe.itsb.vyrbus.service.mappers.SecuenciaTramo;
import pe.itsb.vyrbus.service.mappers.ServiciosHigienicos;
import pe.itsb.vyrbus.service.util.Constantes;
import pe.itsb.vyrbus.service.util.Messages;
import pe.itsb.vyrbus.service.util.Util;
import pe.itsb.vyrbus.service.util.UtilData;

/**
 * @author Jose
 *
 */
public class WndVerMapaBusTransbordo  extends WndBase {
	private static final long serialVersionUID = 666325160644254381L;
	private Window oThisWindow = this;
	public final Grid oGrid = new Grid();

	private String prefijoAsiento="";
	private Long idItinerario;
	private Integer cantOcupados;
	private Integer bus;

	private List<VentaPasaje> lstOcupabilidad=null;
	private List<VentaPasaje> lstVentas = null; 		//Utilizado en el Transbordo de Pasajeros.
	public Map<String, Asiento> mapAsientosId = null;
	private Asiento asientoSeleccionado = null;
	private EventListener oEventListenerFilter;
	private Map<String, Asiento> mapaAsientos = null;

	private static final String IMAGE_PRIMER_PISO = "resources/mapa/bus_primerPiso.png";
	private static final String IMAGE_SEGUNDO_PISO = "resources/mapa/bus_segundoPiso.png";
	private static final int TIPO_ASIENTO = 0;
	private static final int TIPO_MONITOR = 1;
	private static final int TIPO_CAFETERIA = 2;
	private static final int PISO_DOS = 2;

	private String key = "-1";
    private String previousKey = "-1";


	/**
	 * Constructor
	 *
	 * @throws Exception
	 */
	public WndVerMapaBusTransbordo() throws Exception {
		super();
		this.initComponents();
		onCreate();
	}

	@Override
	public void onCreate() throws Exception {

	}

	@Override
	public void initComponents() {
		this.setMaximizable(false);
		this.setMinimizable(false);
		this.setSizable(false);
		this.setClosable(false);
		this.setStyle("padding: 5px");
		this.setWidth("452px");
		this.setVisible(true);

	}

	/**
	 * Carga el Mapa
	 *
	 * @throws Exception
	 */
	@SuppressWarnings("deprecation")
	public void load(Boolean esVisibleInfoBus, Boolean esVisibleOcupabilidad, Boolean esVisibleLeyenda, Boolean esVisibleButtons ) throws Exception {
		/* Busca itinerario para la carga del Mapa */
		List<DetalleItinerario> list = ServiceLocator.getItinerarioManager().buscarItinerariosMantenimiento(new Long(getIdItinerario()),"", "", "", "", "", "", "","");
//		DetalleItinerario detalleItinerario = new DetalleItinerario();
		DetalleItinerario detalleItinerario = list.get(0);

		Caption caption = null;

		String styleBlue11f = "font-size: 11px !important; color:#0000CF";

		/* ------------------ CONTENEDOR DE OBJETOS --------------------- */
		Grid grdContenedor = new Grid();
		grdContenedor.setStyle("padding:0px; border:none");
		Columns columns = new Columns();
		Column column = new Column();
		column.setWidth("174px");
		columns.appendChild(column);
		column = new Column();
		column.setWidth("274px");
		columns.appendChild(column);
		grdContenedor.appendChild(columns);

		Rows rowsContenedor = new Rows();
		Row rowContenedor = new Row();
		rowContenedor.setValign("top");
		rowsContenedor.appendChild(rowContenedor);
		grdContenedor.appendChild(rowsContenedor);

		/* ------------------ MAPA DEL BUS	--------------------- */
		Groupbox grpMapa = new Groupbox();
		grpMapa.setMold("3d");
		caption = new Caption();
		caption.setLabel("MAPA DEL BUS");
		caption.setStyle("color: #ffffff;");
		grpMapa.appendChild(caption);

		Grid gridPiso = new Grid();
		crearEstructura(detalleItinerario.getItinerario().getServicio().getId(), grpMapa, false, detalleItinerario, mapAsientosId, null, gridPiso,"manifiesto",false);
		rowContenedor.appendChild(grpMapa);

		/* ------------------ INFORMACION DEL BUS --------------------- */
		Grid grdContenedorInfo = new Grid();
		grdContenedorInfo.setStyle("border:none");
		Rows rowsContenedorInfo = new Rows();
		Row rowContenedorInfo = null;

		rowContenedorInfo = new Row();
		Groupbox grpInformacion = new Groupbox();
		grpInformacion.setMold("3d");
		grpInformacion.setClosable(false);
		grpInformacion.setVisible(esVisibleInfoBus);
		caption = new Caption();
		caption.setLabel("INFORMACION DEL BUS");
		caption.setStyle("color: #ffffff;");
		grpInformacion.appendChild(caption);

		Grid grdInformacion = new Grid();
		grdInformacion.setStyle("border:none");
		Rows rows = new Rows();
		Row row = null;
		Label label = null;

		Bus bus = null;
		if (detalleItinerario.getItinerario().getBus().getId() != null)
			bus = ServiceLocator.getBusManager().buscarPorId(detalleItinerario.getItinerario().getBus().getId().longValue());

		label = createLabel("N� BUS", null, null);
		row = new Row();
		row.appendChild(label);
		label = createLabel(bus==null?":":": "  + bus.getCodigo(), styleBlue11f, null);
		row.appendChild(label);
		rows.appendChild(row);

		label = createLabel("MARCA", null, null);
		row = new Row();
		row.appendChild(label);
		label = createLabel(bus==null?":":": "  + bus.getGrupoMantenimiento().getDenominacion(), styleBlue11f, null);
		row.appendChild(label);
		rows.appendChild(row);

		label = createLabel("PLACA", null, null);
		row = new Row();
		row.appendChild(label);
		label = createLabel(bus==null?":":": "  + bus.getNumeroPlaca(), styleBlue11f, null);
		row.appendChild(label);
		rows.appendChild(row);

//		label = createLabel("DESCRIPCION", null, null);
//		row = new Row();
//		row.appendChild(label);
//		label = createLabel(bus==null?":":": "  + bus.getNumeroEjes().toString(), styleBlue11f, null);
//		row.appendChild(label);
//		rows.appendChild(row);

		label = createLabel("CAPACIDAD", null, null);
		row = new Row();
		row.appendChild(label);
		Integer capacidad = detalleItinerario.getItinerario().getServicio().getNumeroAsientosPiso1() +
							(detalleItinerario.getItinerario().getServicio().getNumeroPisos().intValue()==2
							? detalleItinerario.getItinerario().getServicio().getNumeroAsientosPiso2() : 0);
		label = createLabel(": "  + capacidad.toString(), styleBlue11f, null);
		row.appendChild(label);
		rows.appendChild(row);

		label = createLabel("SERVICIO", null, null);
		row = new Row();
		row.appendChild(label);
		label = createLabel(": "  + detalleItinerario.getItinerario().getServicio().getDenominacion().toString(), styleBlue11f, null);
		row.appendChild(label);
		rows.appendChild(row);

		grdInformacion.appendChild(rows);
		grpInformacion.appendChild(grdInformacion);
		rowContenedorInfo.appendChild(grpInformacion);
		rowsContenedorInfo.appendChild(rowContenedorInfo);


		/* ------------------ OCUPABILIDAD DEL BUS ------------------ */
		rowContenedorInfo = new Row();
		Groupbox grpOcupabilidad = new Groupbox(); grpOcupabilidad.setMold("3d");
		grpOcupabilidad.setVisible(esVisibleOcupabilidad);
		grpOcupabilidad.setClosable(false);
		caption = new Caption();
		caption.setLabel("OCUPABILIDAD DEL SERVICIO");
		caption.setStyle("color: #ffffff;");
		grpOcupabilidad.appendChild(caption);

		Grid grdOcupabilidad = new Grid();
		grdOcupabilidad.setStyle("border:none");
		rows = new Rows();

		row = new Row();
		label = new Label("OCUPADOS");
		row.appendChild(label);
		label = new Label(": " + getCantOcupados());
		label.setStyle("font-size:11px !important");
		row.appendChild(label);
		rows.appendChild(row);

		row = new Row();
		label = new Label("DISPONIBLES");
		row.appendChild(label);
		label = new Label(": "+(new Integer(capacidad) - getCantOcupados()));
		label.setStyle("font-size:11px !important");
		row.appendChild(label);
		rows.appendChild(row);

		grdOcupabilidad.appendChild(rows);
		grpOcupabilidad.appendChild(grdOcupabilidad);
		rowContenedorInfo.appendChild(grpOcupabilidad);
		rowsContenedorInfo.appendChild(rowContenedorInfo);


		/* ------------------ Leyenda	------------------ */
		rowContenedorInfo = new Row();
		Groupbox grpLeyenda = new Groupbox();
		grpLeyenda.setMold("3d");
		grpLeyenda.setClosable(false);
		grpLeyenda.setVisible(esVisibleLeyenda);
		caption = new Caption("LEYENDA DE IMAGENES");
		caption.setStyle("color: #ffffff;");
		grpLeyenda.appendChild(caption);

		Grid grdLeyenda = new Grid();
		grdLeyenda.setStyle("border:none");
		rows = new Rows();


		Image image = null;
		Hbox hboxRow = null;
		Hbox hboxObjeto = null;

		row = new Row();
		hboxRow = new Hbox();
		hboxRow.setAlign("center");
		hboxRow.setWidth("260px");
		hboxObjeto = new Hbox();
		hboxObjeto.setAlign("center");
		hboxObjeto.setWidth("26px");
		image = new Image("/resources/asientos/asientoDisponible_1.png");
		hboxObjeto.appendChild(image);
		hboxRow.appendChild(hboxObjeto);
		label = new Label("Disponible");
		hboxObjeto = new Hbox();
		hboxObjeto.setAlign("center");
		hboxObjeto.setWidth("100px");
		hboxObjeto.appendChild(label);
		hboxRow.appendChild(hboxObjeto);
		hboxObjeto = new Hbox();
		hboxObjeto.setAlign("center");
		hboxObjeto.setWidth("26px");
		image = new Image("/resources/asientos/asientoReservado_1.png");
		hboxObjeto.appendChild(image);
		hboxRow.appendChild(hboxObjeto);
		label = new Label("Reservado");
		hboxObjeto = new Hbox();
		hboxObjeto.setAlign("center");
		hboxObjeto.setWidth("100px");
		hboxObjeto.appendChild(label);
		hboxRow.appendChild(hboxObjeto);
		row.appendChild(hboxRow);
		rows.appendChild(row);

		row = new Row();
		hboxRow = new Hbox();
		hboxRow.setAlign("center");
		hboxRow.setWidth("260px");
		hboxObjeto = new Hbox();
		hboxObjeto.setAlign("center");
		hboxObjeto.setWidth("26px");
		image = new Image("/resources/asientos/asientoReservadoDelivery_1.png");
		hboxObjeto.appendChild(image);
		hboxRow.appendChild(hboxObjeto);
		label = new Label("Reservado x Delivery");
		hboxObjeto = new Hbox();
		hboxObjeto.setAlign("center");
		hboxObjeto.setWidth("100px");
		hboxObjeto.appendChild(label);
		hboxRow.appendChild(hboxObjeto);
		hboxObjeto = new Hbox();
		hboxObjeto.setAlign("center");
		hboxObjeto.setWidth("26px");
		image = new Image("/resources/asientos/asientoReservadoAgente_1.png");
		hboxObjeto.appendChild(image);
		hboxRow.appendChild(hboxObjeto);
		label = new Label("Reservado x Agente");
		hboxObjeto = new Hbox();
		hboxObjeto.setAlign("center");
		hboxObjeto.setWidth("100px");
		hboxObjeto.appendChild(label);
		hboxRow.appendChild(hboxObjeto);
		row.appendChild(hboxRow);
		rows.appendChild(row);

		row = new Row();
		hboxRow = new Hbox();
		hboxRow.setAlign("center");
		hboxRow.setWidth("260px");
		hboxObjeto = new Hbox();
		hboxObjeto.setAlign("center");
		hboxObjeto.setWidth("26px");
		image = new Image("/resources/asientos/asientoVendidoFemale_1.png");
		hboxObjeto.appendChild(image);
		hboxRow.appendChild(hboxObjeto);
		label = new Label("Venta Counter");
		hboxObjeto = new Hbox();
		hboxObjeto.setAlign("center");
		hboxObjeto.setWidth("100px");
		hboxObjeto.appendChild(label);
		hboxRow.appendChild(hboxObjeto);
		hboxObjeto = new Hbox();
		hboxObjeto.setAlign("center");
		hboxObjeto.setWidth("26px");
		image = new Image("/resources/asientos/asientoVendidoMale_1.png");
		hboxObjeto.appendChild(image);
		hboxRow.appendChild(hboxObjeto);
		label = new Label("Venta Counter");
		hboxObjeto = new Hbox();
		hboxObjeto.setAlign("center");
		hboxObjeto.setWidth("100px");
		hboxObjeto.appendChild(label);
		hboxRow.appendChild(hboxObjeto);
		row.appendChild(hboxRow);
		rows.appendChild(row);

		row = new Row();
		hboxRow = new Hbox();
		hboxRow.setAlign("center");
		hboxRow.setWidth("260px");
		hboxObjeto = new Hbox();
		hboxObjeto.setAlign("center");
		hboxObjeto.setWidth("26px");
		image = new Image("/resources/asientos/asientoVendidoWebFemale_1.png");
		hboxObjeto.appendChild(image);
		hboxRow.appendChild(hboxObjeto);
		label = new Label("Venta Web");
		hboxObjeto = new Hbox();
		hboxObjeto.setAlign("center");
		hboxObjeto.setWidth("100px");
		hboxObjeto.appendChild(label);
		hboxRow.appendChild(hboxObjeto);
		hboxObjeto = new Hbox();
		hboxObjeto.setAlign("center");
		hboxObjeto.setWidth("26px");
		image = new Image("/resources/asientos/asientoVendidoWebMale_1.png");
		hboxObjeto.appendChild(image);
		hboxRow.appendChild(hboxObjeto);
		label = new Label("Venta Web");
		hboxObjeto = new Hbox();
		hboxObjeto.setAlign("center");
		hboxObjeto.setWidth("100px");
		hboxObjeto.appendChild(label);
		hboxRow.appendChild(hboxObjeto);
		row.appendChild(hboxRow);
		rows.appendChild(row);

		row = new Row();
		hboxRow = new Hbox();
		hboxRow.setAlign("center");
		hboxRow.setWidth("260px");
		hboxObjeto = new Hbox();
		hboxObjeto.setAlign("center");
		hboxObjeto.setWidth("26px");
		image = new Image("/resources/asientos/asientoBloqueado_1.png");
		hboxObjeto.appendChild(image);
		hboxRow.appendChild(hboxObjeto);
		label = new Label("Bloqueado");
		hboxObjeto = new Hbox();
		hboxObjeto.setAlign("center");
		hboxObjeto.setWidth("100px");
		hboxObjeto.appendChild(label);
		hboxRow.appendChild(hboxObjeto);
		hboxObjeto = new Hbox();
		hboxObjeto.setAlign("center");
		hboxObjeto.setWidth("26px");
		image = new Image("/resources/asientos/asientoSemiOcupado_1.png");
		hboxObjeto.appendChild(image);
		hboxRow.appendChild(hboxObjeto);
		label = new Label("Prioridad de Venta en Tramo");
		hboxObjeto = new Hbox();
		hboxObjeto.setAlign("center");
		hboxObjeto.setWidth("100px");
		hboxObjeto.appendChild(label);
		hboxRow.appendChild(hboxObjeto);
		row.appendChild(hboxRow);
		rows.appendChild(row);

		grdLeyenda.appendChild(rows);
		grpLeyenda.appendChild(grdLeyenda);
		rowContenedorInfo.appendChild(grpLeyenda);
		rowsContenedorInfo.appendChild(rowContenedorInfo);


		/* 	------------------------ Grid de Botones 	---------------------------	*/
		rowContenedorInfo = new Row();
		Grid grdBotones = new Grid();
		grdBotones.setStyle("border:none");
		rows = new Rows();
		row = new Row();

		Button buttonRefresh = new Button();
		buttonRefresh.setLabel("Refrescar");
		buttonRefresh.setClass("btnCommandL");
//		buttonRefresh.setHeight("30px");
//		buttonRefresh.setWidth("98px");
		buttonRefresh.setTooltiptext("Refrescar Mapa");
		buttonRefresh.setImage("/resources/mp_recargar16.png");
		row.appendChild(buttonRefresh);

		Button buttonAceptar = new Button();
		buttonAceptar.setLabel("Aceptar");
		buttonAceptar.setVisible(esVisibleInfoBus);
		buttonAceptar.setClass("btnCommandL");
//		buttonAceptar.setHeight("30px");
		buttonAceptar.setImage("/resources/mp_aceptarEnabled.png");
		row.appendChild(buttonAceptar);
		rows.appendChild(row);


		/*Validacion del numero de pisos del bus para la aliniaci�n.*/
		if (detalleItinerario.getItinerario().getServicio().getNumeroPisos().equals(PISO_DOS)){
			grdBotones.setHeight("140px");
			Separator separator = new Separator("vertical");
			separator.setHeight("98px");
			row = new Row();
			row.setSpans("2");
			row.appendChild(separator);
			rows.appendChild(row);
//		}else{
//			grdSegundoPiso.setHeight("448px");
//			Separator separator = new Separator();separator.setHeight("38px");
//			rowMB2.appendChild(separator);
		}
		grdBotones.appendChild(rows);
		rowContenedorInfo.appendChild(grdBotones);
		rowsContenedorInfo.appendChild(rowContenedorInfo);

		grdContenedorInfo.appendChild(rowsContenedorInfo);
		rowContenedor.appendChild(grdContenedorInfo);

		appendChild(grdContenedor);

		buttonAceptar.addEventListener(Events.ON_CLICK, new EventListener<Event>() {
			@Override
			public void onEvent(Event event) throws Exception {
				oThisWindow.onClose();
			}
		});

		buttonRefresh.addEventListener(Events.ON_CLICK, new EventListener<Event>() {
			@Override
			public void onEvent(Event event) throws Exception {
				DetalleItinerario detalleItinerario = new DetalleItinerario();
				/* Busca itinerario para la carga del Mapa */
				List<DetalleItinerario> list = ServiceLocator.getItinerarioManager().buscarItinerariosMantenimiento(new Long(getIdItinerario()), "","", "", "", "", "", "","");
				detalleItinerario = list.get(0);
				onRefreshMapaAsientos(mapAsientosId,detalleItinerario, oGrid);
			}
		});

	}

	private Label createLabel(String value, String style, String sclass){
		Label label = new Label(value);
		if(style!=null)
			label.setStyle(style);
		if (sclass!=null)
			label.setClass(sclass);
		return label;
	}

	/**
	 * Crea la estructura del mapa del Bus
	 * @param idServicio		: Identificador del servicio
	 * @param grpbxParent		: objeto GroupBox
	 * @param esRetorno			: indica si es retorno
	 * @param detalleItinerario	: objeto DetalleItinerario
	 * @param mapaAsientos		:
	 * @param gridOcupabilidad	: objeto Grid donde se mostrara el mapa del bus
	 * @param gridPiso			: objeto Grid Utilizado para los transbordos(diferenciar las variables definidad para cada mapa)
	 * @param identifica		: Utilizado para los transbordos(diferenciar las variables definidad para cada mapa)
	 */
	@SuppressWarnings({ "deprecation","unchecked" })
	public void crearEstructura(Integer idServicio, Groupbox grpbxParent, boolean esRetorno, final DetalleItinerario detalleItinerario,
								Map<String, Asiento> mapaAsientos, final Grid gridOcupabilidad, Grid gridPiso,
								String identifica,final Boolean isTransbordo){
		try{

			/*Retorna el numero de asiento seleccionado*/
			EventListener selectedEventListener = new EventListener<Event>() {
				@Override
				public void onEvent(Event event) throws Exception {
					if (event.getTarget() instanceof Asiento) {
						if(oEventListenerFilter !=null){
							asientoSeleccionado=(Asiento)event.getTarget();
							oEventListenerFilter.onEvent(new Event(pe.itsb.vyrbus.view.ui.Events.ON_SELECT));
						}
					}
				}
			};

			Servicio servicio = null;
			List<MapaBus> lstMapaBus = ServiceLocator.getMapaBusManager().buscarMapaBus(idServicio, Constantes.VALUE_ACTIVO);

			Map<Coordenada, MapaBus> mapCoordenadas = new HashMap();
			for(MapaBus mapaBus : lstMapaBus){
				Coordenada coordenada = new Coordenada(mapaBus.getNumeroFila(), mapaBus.getNumeroColumna(), mapaBus.getNumeroPiso());
				mapCoordenadas.put(coordenada, mapaBus);
			}

			if(lstMapaBus.size()>0)
				servicio = lstMapaBus.get(0).getServicio();

			int nPisos = servicio.getNumeroPisos();
			int nFilas = servicio.getNumeroFilasPiso1();
			int nColumnas = servicio.getNumeroColumnasPiso1();
			prefijoAsiento = "imgAsientoIdaPiso1_"+identifica;
			if(esRetorno)
				prefijoAsiento = "imgAsientoRetornoPiso1_"+identifica;
			int numeroAsiento = 0;

			inicializarEstructura(grpbxParent);

			Image imagen = generarImagen(IMAGE_PRIMER_PISO, 154, 43);

			mapaAsientos = new HashMap<>();
			this.mapaAsientos = new HashMap<>();

			for(int i=0; i<nPisos; i++){
				String idGrid = "grdIdaPiso1"+identifica;
				if(i==1){
					nFilas = servicio.getNumeroFilasPiso2();
					nColumnas = servicio.getNumeroColumnasPiso2();
					prefijoAsiento = "imgAsientoIdaPiso2_"+identifica;
					idGrid = "grdIdaPiso2"+identifica;
					if(esRetorno){
						prefijoAsiento = "imgAsientoRetornoPiso2_";
						idGrid = "grdRetornoPiso2";
					}
					imagen = generarImagen(IMAGE_SEGUNDO_PISO, 154, 34);

				}
				gridPiso = new Grid();
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
				for(int j=0; j<nFilas; j++){
					row = new Row();
					for(int k=0; k<nColumnas; k++){
						Div oDiv = new Div();
						oDiv.setWidth("28px");
						oDiv.setHeight("28px");
						oDiv.setStyle("padding:none");

						String coordenadaActual = j+"-"+k+"-"+i;

						for(Coordenada coordenada : mapCoordenadas.keySet()){
							if(coordenada.toString().equals(coordenadaActual)){
								MapaBus objetoBus = mapCoordenadas.get(coordenada);

								HashMap<String, String> propiedades = new HashMap<>();
								numeroAsiento++;

								if(objetoBus.getTipoObjeto().intValue()==TIPO_ASIENTO){
									Asiento asiento = new Asiento();
									asiento.addEventListener(Events.ON_CLICK, selectedEventListener);

									asiento.addEventListener(Events.ON_CLICK, new EventListener<Event>() {
										@Override
										public void onEvent(Event e){
											if(isTransbordo)
													onClickAsiento(e,detalleItinerario,gridOcupabilidad );
										}
									});

									propiedades.put("ocupante", "pasajero");
									asiento.setId(prefijoAsiento + objetoBus.getNumeroAsiento());
									asiento.setOcupante(Asiento.OCUPANTE_PASAJERO);
									asiento.setEstadoAsiento(Constantes.ASIENTO_DISPONIBLE);
									asiento.setFila(j);
									asiento.setColumna(k);
									asiento.setPiso(i);
									asiento.setNumeroAsiento(objetoBus.getNumeroAsiento());
									asiento.setSrc(objetoBus.getPathImagen());
									//MAOE: Para el tema del nuevo modelo de  Tarifas
									asiento.setNumeroZona(objetoBus.getNumeroZona());
									asiento.setPropiedades(propiedades);
									asiento.setDraggable("true");
									asiento.setDetalleItinerario(detalleItinerario);
									asiento.setKey();
									asiento.setStyle("cursor:pointer;");
									oDiv.appendChild(asiento);
									/*	Agregando los asientos a un Hashmap*/
									mapaAsientos.put(asiento.getKey(), asiento);
									this.mapaAsientos.put(asiento.getKey(), asiento);
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
						row.setStyle("padding-top:2px; padding-left:3px; padding-right:0px; border:normal !important; background:#EEEEEE");// background:#99D9EAeeeeee");
					}
					rows.appendChild(row);
				}
				gridPiso.appendChild(rows);
				grpbxParent.appendChild(gridPiso);
				onRefreshMapaAsientos(mapaAsientos, detalleItinerario, gridOcupabilidad);
//				if(esRetorno)
//					mapaAsientosRetorno = mapaAsientos;
//				else
					mapAsientosId = mapaAsientos;




			}
		}catch(Exception ex){
			ex.printStackTrace();
		}
	}




	/**
	 * Evento utilizado cuando el usuario hace click en un asiento.
	 * @param e					: Evento
	 * @param mapaAsientos				: Mapa de asientos.
	 * @param ventaPasaje	: Itinerario seleccionado.
	 * @param esIda				: Indica si es Ida o retorno.
	 */
	private void onClickAsiento(Event e,DetalleItinerario detalleItinerario, Grid gridOcupabilidad){
		Asiento asientoSeleccionado =(Asiento)e.getTarget();

		previousKey = key;
		key = asientoSeleccionado.getKey();

		/*	Elimina el asiento de la lista de asientos seleccionados y desbloquea el asiento*/
		if(removerAsientoSeleccionado(previousKey, key,detalleItinerario)){
			return;
		}

		try{
			if(consultaAsientoBloqueado(key)){
				key="-1";
			}else{
				TmpOcupacionAsientos tmpOcupacionAsientos = new TmpOcupacionAsientos();
				/*	*********************************************************************************************************	*/
				/*	Aqui habra que modificar el usuario*/
				UsuarioHardware usuarioHardware= new UsuarioHardware();
				Usuario usuario= new Usuario();
				usuarioHardware = (UsuarioHardware) Executions.getCurrent().getSession().getAttribute(Constantes.ATRIBUTO_USUARIO_HARDWARE);
				usuario = (Usuario) Executions.getCurrent().getSession().getAttribute(Constantes.ATRIBUTO_USUARIO);
				tmpOcupacionAsientos.setUsuarioHardware(usuarioHardware);
				tmpOcupacionAsientos.setUsuario(usuario);
				/*	*********************************************************************************************************	*/
				tmpOcupacionAsientos.setRuta(detalleItinerario.getRuta());
				tmpOcupacionAsientos.setItinerario(detalleItinerario.getItinerario());
				tmpOcupacionAsientos.setNumeroAsiento(asientoSeleccionado.getNumeroAsiento());
				tmpOcupacionAsientos.setNumeroPiso(asientoSeleccionado.getPiso());
				tmpOcupacionAsientos.setFechaPartida(Util.DatetoString(detalleItinerario.getFechaPartida(), Constantes.DATE_FORMAT));
				tmpOcupacionAsientos.setHoraPartida(detalleItinerario.getHoraPartida());
				tmpOcupacionAsientos.setEstadoRegistro(Constantes.VALUE_ACTIVO);
				UtilData.auditarRegistro(tmpOcupacionAsientos, false,usuario, Executions.getCurrent());
				TmpOcupacionAsientosID tmpOcupacionAsientosID = new TmpOcupacionAsientosID();
				tmpOcupacionAsientosID.setIdUsuarioHardware(tmpOcupacionAsientos.getUsuarioHardware().getId());
				tmpOcupacionAsientosID.setIdUsuario(tmpOcupacionAsientos.getUsuario().getId());
				tmpOcupacionAsientosID.setIdRuta(tmpOcupacionAsientos.getRuta().getId());
				tmpOcupacionAsientosID.setIdItinerario(tmpOcupacionAsientos.getItinerario().getId());
				tmpOcupacionAsientosID.setNumeroAsiento(tmpOcupacionAsientos.getNumeroAsiento());
				tmpOcupacionAsientos.setTmpOcupacionAsientosID(tmpOcupacionAsientosID);

				int result = ServiceLocator.getTmpOcupacionAsientosManager().bloquearAsiento(tmpOcupacionAsientos);
				if(result < 0){
					DlgMessage.information(Messages.getString("WndVentaReserva.information.asientoBloqueado"));
					WndVerMapaBus verMapaBus = new WndVerMapaBus();
					verMapaBus.onRefreshMapaAsientos(mapaAsientos, detalleItinerario,gridOcupabilidad);
					return;
				}else{
					mapaAsientos.get(key).setSrc(Constantes.PATH_PARTIAL+"asientoBloqueado_"+asientoSeleccionado.getNumeroAsiento()+Constantes.IMAGE_EXTENSION);
					mapaAsientos.get(key).setEstadoAsiento(Constantes.ASIENTO_BLOQUEADO);
					/*	Esto es para desbloquear el asiento en caso seleccione otro asiento sin haber cerrado la ventana del mapa	*/
					if(!previousKey.equals("-1") && !previousKey.equals(key)){
						String[] buffer = previousKey.split("-");	//Almacenamos en un array el asiento y el piso
						TmpOcupacionAsientos tmpOcupacion = new TmpOcupacionAsientos();
						tmpOcupacion.setRuta(detalleItinerario.getRuta());
						tmpOcupacion.setItinerario(detalleItinerario.getItinerario());
						tmpOcupacion.setNumeroAsiento(Integer.valueOf(buffer[0]));
						tmpOcupacion.setNumeroPiso(Integer.valueOf(buffer[1]));
						ServiceLocator.getTmpOcupacionAsientosManager().desbloquearAsiento(tmpOcupacion);
						mapaAsientos.get(previousKey).setSrc(Constantes.PATH_PARTIAL+"asientoDisponible_"+buffer[0]+Constantes.IMAGE_EXTENSION);
						mapaAsientos.get(previousKey).setEstadoAsiento(Constantes.ASIENTO_DISPONIBLE);
					}
				}
			}
		}catch(DuplicateSeatException dsex){
			DlgMessage.information(Messages.getString("WndVentaReserva.information.asientoVendido"));
		}catch(DataIntegrityViolationException divex){
			DlgMessage.information(Messages.getString("WndVentaReserva.information.asientoBloqueado"));
		}catch(Exception ex){
			ex.printStackTrace();
			DlgMessage.error(this.getClass().getName()+" "+ex.getMessage());
		}
	}

	/**
	 * Realiza la eliminacion del asiento seleccionado y luego lo desbloquea.
	 * @param seatSelected	: Asiento seleccionado.
	 * @return True si el asiento existe en la lista de asientos seleccionados.
	 */
	private boolean removerAsientoSeleccionado(String previusKey, String key, DetalleItinerario detalleItinerario){
		try{
			String[] buffer = key.split("-");	//Almacenamos en un array el asiento y el piso
			if(previusKey.equals(key)){
				if(mapaAsientos.get(key).getEstadoAsiento().intValue()==Constantes.ASIENTO_BLOQUEADO){
					mapaAsientos.get(key).setEstadoAsiento(Constantes.ASIENTO_DISPONIBLE);
					mapaAsientos.get(key).setSrc(Constantes.ICON_DISPONIBLE+buffer[0]+Constantes.IMAGE_EXTENSION);

					TmpOcupacionAsientos tmpOcupacion = new TmpOcupacionAsientos();
					tmpOcupacion.setRuta(detalleItinerario.getRuta());
					tmpOcupacion.setItinerario(detalleItinerario.getItinerario());
					tmpOcupacion.setNumeroAsiento(Integer.valueOf(buffer[0]));
					tmpOcupacion.setNumeroPiso(Integer.valueOf(buffer[1]));
					ServiceLocator.getTmpOcupacionAsientosManager().desbloquearAsiento(tmpOcupacion);
					return true;
				}else
					return false;
			}
		}catch(Exception ex){
			ex.printStackTrace();
		}
		return false;
	}

	/**
	 * Consulta si el asiento esta bloqueado.
	 * @param key	: Clave a buscar en el mapa de asientos.
	 * @return
	 */
	private boolean consultaAsientoBloqueado(String key){
		if(mapaAsientos.get(key).getEstadoAsiento().intValue()==Constantes.ASIENTO_DISPONIBLE)
			return false;
		return true;
	}

	/**
//	 * Obtiene la separaci�n de los asientos en el mapa.
//	 * @param servicio: Tipo de servicio, CAMA, PRE-40, PRE, SUITE
//	 * @return
//	 */
//	private String obtenerPadding(Servicio servicio) {
//		String padding = "2px";
//		if (servicio.getNumeroAsientosPiso1() == 36)
//			padding = "6px";
//		else if (servicio.getNumeroAsientosPiso1() == 40)
//			padding = "4px";
//		return padding;
//	}


	/**
	 * Realiza el refresco del mapa del bus.
	 * @param mapa				: Mapa de asientos
	 * @param detalleItinerario	: Itinerario con el que se desea actualizar el mapa.
	 */
	@SuppressWarnings("unchecked")
	public void onRefreshMapaAsientos(Map<String, Asiento> mapaAsientos, DetalleItinerario detalleItinerario, Grid gridOcupabilidad){
		try{

			//onCleanMap(mapaAsientos);
			/*	Obtenemos el subconjunto que queremos buscar segun la ruta seleccioanda		*/
			List<Integer> subConjuntoBuscar = obtenerSubconjunto(detalleItinerario.getItinerario().getListSecuenciaTramo(), detalleItinerario.getRuta().getLocalidadOrigen().getId(), detalleItinerario.getItinerario().getRuta().getLocalidadDestino().getId());

			lstVentas = ServiceLocator.getVentaPasajesManager().buscarVentasForMapaBus(detalleItinerario.getItinerario().getId());
			lstVentas = obtenerConjuntos(lstVentas, detalleItinerario.getItinerario().getListSecuenciaTramo());
			if(lstVentas.size()>0){
				for(VentaPasaje venta : lstVentas){
					String key = venta.getNumeroAsiento()+"-"+(venta.getNumeroPiso());
					//String key = venta.getNumeroAsiento()+"-"+ 0;
					for(Integer orden : subConjuntoBuscar){
						if(mapaAsientos.containsKey(key) && venta.getSubConjunto().contains(orden)){
							//if(mapaAsientos.containsKey(key)){
							Asiento asiento = mapaAsientos.get(key);
							/*	Para identificar si la venta es completa o es un tramo	*/
							if(venta.getTipoTransaccion().equals(Constantes.TIPO_OPERACION_VENTA)){
								if(venta.getPasajero().getSexo().getId().intValue()==Constantes.ID_SEXO_FEMENINO) {
									if(venta.getCanalVenta().getId().intValue()==Constantes.ID_CANVEN_WEB)
										asiento.setSrc(Constantes.ICON_VENDIDO_WEB_FEMALE+venta.getNumeroAsiento()+Constantes.IMAGE_EXTENSION);
									else
										asiento.setSrc(Constantes.ICON_VENDIDO_FEMALE+venta.getNumeroAsiento()+Constantes.IMAGE_EXTENSION);
								}else {
									if(venta.getCanalVenta().getId().intValue()==Constantes.ID_CANVEN_WEB)
										asiento.setSrc(Constantes.ICON_VENDIDO_WEB_MALE+venta.getNumeroAsiento()+Constantes.IMAGE_EXTENSION);
									else
										asiento.setSrc(Constantes.ICON_VENDIDO_MALE+venta.getNumeroAsiento()+Constantes.IMAGE_EXTENSION);
								}
								asiento.setEstadoAsiento(Constantes.ASIENTO_VENDIDO);
							}else if(venta.getTipoTransaccion().equals(Constantes.TIPO_OPERACION_RESERVA)){
								if(venta.getCanalVenta().getId().intValue()==Constantes.ID_CANVEN_DELIVERY)
									asiento.setSrc(Constantes.ICON_RESERVADO_DELIVERY+venta.getNumeroAsiento()+Constantes.IMAGE_EXTENSION);
								else if(venta.getCanalVenta().getId().intValue()==Constantes.ID_CANVEN_AGENTE)
									asiento.setSrc(Constantes.ICON_RESERVADO_AGENTE+venta.getNumeroAsiento()+Constantes.IMAGE_EXTENSION);
								else
									asiento.setSrc(Constantes.ICON_RESERVADO+venta.getNumeroAsiento()+Constantes.IMAGE_EXTENSION);
								asiento.setEstadoAsiento(Constantes.ASIENTO_RESERVADO);
							}
							asiento.setTooltiptext(venta.getRuta().toString()+"\n"+venta.getPasajero().toString());
							break;
						}
					}
					/*	Para identificar las prioridades del tramos para la venta	*/
					if(venta.getRuta().getLocalidadDestino().getId()==detalleItinerario.getRuta().getLocalidadOrigen().getId()){
						Asiento asiento = mapaAsientos.get(key);
						asiento.setSrc(Constantes.ICON_SEMI_OCUPADO+venta.getNumeroAsiento()+Constantes.IMAGE_EXTENSION);
					}
				}
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
//							nOcupados++;
							break;
						}
					}
				}
			}
			//mostrarOcupabilidad(nOcupados, detalleItinerario, gridOcupabilidad);
		}catch(Exception ex){
			ex.printStackTrace();
			DlgMessage.error(this.getClass().getName()+" "+ex.getMessage());
		}
	}

	/**
	 * Obtine los subconjuntos de un registro de venta, tmpocupacion o de la
	 * ruta que estamos buscando.
	 * @param lstSecuencias : Lista de secuencia segun el itinerario.
	 * @param idOrigen : Identificador del origen.
	 * @param idDestino: Identificador del destino.
	 * @return
	 */
	private List<Integer> obtenerSubconjunto(
			List<SecuenciaTramo> lstSecuencias, Integer idOrigen,
			Integer idDestino) {
		List<Integer> lstSubconjunto = new ArrayList<>();
		/* Recorremos la secuencia de tramos del itinerario */
		for (int j = 0; j < lstSecuencias.size(); j++) {
			SecuenciaTramo secuencia = lstSecuencias.get(j);
			/*
			 * Validamos si el origen de la secuencia coincide con el origen de
			 * la ruta
			 */
			if (secuencia.getOrigen().intValue() == idOrigen.intValue()) {
				/* Recorremos la secuencia de tramos desde la posicion j */
				for (int k = j; k < lstSecuencias.size(); k++) {
					secuencia = lstSecuencias.get(k);
					lstSubconjunto.add(secuencia.getOrden());
					/*
					 * Validamos si el destino de la secuencia coincide con el
					 * destino de la ruta
					 */
					if (secuencia.getDestino().intValue() == idDestino
							.intValue())
						break;
				}
				break;
			}
		}
		return lstSubconjunto;
	}

	/**
	 * Obtiene los subconjuntos de una lista de ventas, tmpOcupacion.
	 * @param lista : Lista de registros de los cuales queremos obtener los subconjuntos.
	 * @param lstSecuencias : Lista de secuencia segun el itinerario.
	 * @return
	 */
	@SuppressWarnings({ "unchecked" })
	private List obtenerConjuntos(List lista, List<SecuenciaTramo> lstSecuencias) {
		List result = new ArrayList<>();
		for (int i = 0; i < lista.size(); i++) {
			Object obj = lista.get(i);
			if (obj instanceof TmpOcupacionAsientos) {
				TmpOcupacionAsientos tmp = (TmpOcupacionAsientos) obj;
				List<Integer> subConjunto = obtenerSubconjunto(lstSecuencias,
						tmp.getRuta().getLocalidadOrigen().getId(), tmp
								.getRuta().getLocalidadDestino().getId());
				tmp.setSubConjunto(subConjunto);
				result.add(tmp);
			} else if (obj instanceof VentaPasaje) {
				VentaPasaje ventaPasaje = (VentaPasaje) obj;
				List<Integer> subConjunto = obtenerSubconjunto(lstSecuencias,
						ventaPasaje.getRuta().getLocalidadOrigen().getId(),
						ventaPasaje.getRuta().getLocalidadDestino().getId());
				ventaPasaje.setSubConjunto(subConjunto);
				result.add(ventaPasaje);
			}
		}
		return result;
	}


	/**
	 * Inicializa(limpia los objetos existentes) el contenedor de los asientos.
	 */
	private void inicializarEstructura(Groupbox groupbox){
		for(int i=groupbox.getChildren().size()-1; i>-1; i--){
			Component component = groupbox.getChildren().get(i);
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

	@Override
	public boolean addEventListener(String evtnm, EventListener<? extends Event> listener) {
		boolean resultadoEvento = true;
		if (evtnm.equals(pe.itsb.vyrbus.view.ui.Events.ON_SELECT)) {
			oEventListenerFilter = listener;
		}
			else {
				resultadoEvento = super.addEventListener(evtnm, listener);
		}
		return resultadoEvento;
	}

	public List<VentaPasaje>getLstVentas(){
		return this.lstVentas;
	}

	/**
	 * @return Objeto idItinerario.
	 */
	public Long getIdItinerario() {
		return idItinerario;
	}

	/**
	 * @param idItinerario
	 *            : Setea el objeto idItinerario.
	 */
	public void setIdItinerario(Long idItinerario) {
		this.idItinerario = idItinerario;
	}

	/**
	 * @return Objeto idItinerario.
	 */
	public Integer getCantOcupados() {
		return cantOcupados;
	}

	/**
	 * @param idItinerario
	 *            : Setea el objeto idItinerario.
	 */
	public void setCantOcupados(Integer cantOcupados) {
		this.cantOcupados = cantOcupados;
	}

	/**
	 * @return Objeto bus.
	 */
	public Integer getBus() {
		return bus;
	}

	/**
	 * @param idItinerario: Setea el objeto idItinerario.
	 */
	public void setBus(Integer bus) {
		this.bus = bus;
	}

	public List<VentaPasaje> getLisOcupabilidad(){
		return lstOcupabilidad;
	}

	public void setListOcupabilidad(List<VentaPasaje> lisOpabilidad){
		this.lstOcupabilidad=lisOpabilidad;
	}

	public void  setAsientoSeleccionado(Asiento asientoSeleccionado){
		this.asientoSeleccionado=asientoSeleccionado;
	}

	public Asiento getAsientoSeleccionado(){
		return asientoSeleccionado;
	}

	public List<VentaPasaje> getListVentas(){
		return lstVentas;
	}
}
