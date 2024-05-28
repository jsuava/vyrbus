/**
 * Proyecto		: SISVYR
 * Sistema		: Sistema de Ventas y Reservas
 * Descripci�n	:
 * Autor		: Jos� Avalos
 * Fecha		: 10/09/2013
 */
package pe.itsb.vyrbus.view.ui;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.springframework.dao.DataIntegrityViolationException;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zul.Auxhead;
import org.zkoss.zul.Auxheader;
import org.zkoss.zul.Button;
import org.zkoss.zul.Caption;
import org.zkoss.zul.Column;
import org.zkoss.zul.Columns;
import org.zkoss.zul.Div;
import org.zkoss.zul.Grid;
import org.zkoss.zul.Groupbox;
import org.zkoss.zul.Hbox;
import org.zkoss.zul.Hlayout;
import org.zkoss.zul.Image;
import org.zkoss.zul.Label;
import org.zkoss.zul.Row;
import org.zkoss.zul.Rows;
import org.zkoss.zul.Separator;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Vbox;
import org.zkoss.zul.Vlayout;
import org.zkoss.zul.Window;

import pe.itsb.vyrbus.model.bean.DetalleItinerario;
import pe.itsb.vyrbus.model.bean.MapaBus;
import pe.itsb.vyrbus.model.bean.Ruta;
import pe.itsb.vyrbus.model.bean.Servicio;
import pe.itsb.vyrbus.model.bean.TarifaRegular;
import pe.itsb.vyrbus.model.bean.TmpOcupacionAsientos;
import pe.itsb.vyrbus.model.bean.TmpOcupacionAsientosID;
import pe.itsb.vyrbus.model.bean.UsuarioHardware;
import pe.itsb.vyrbus.model.bean.VentaPasaje;
import pe.itsb.vyrbus.model.bean.VentaTramo;
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
public class WndMapaBus extends WndBase implements Serializable {
	private static final long serialVersionUID = 1L;
	private Window oThisWindow = this;
	private Div divContenedorBus;
	private Vbox vbxEstructuraBus;
	private Grid grdOcupabilidad;
	private Textbox txtAsiento;
	private Textbox txtPiso;
	private Textbox txtAsientoSeleccionado;
    private Textbox txtPisoSeleccionado;

	private VentaPasaje ventaPasaje;
	private DetalleItinerario detalleItinerario;
	private UsuarioHardware usuarioHardware;
	private Map<String, Asiento> mapaAsientos = null;

	private boolean selectAsiento;
	private String key = "-1";
    private String previousKey = "-1";
    private String prefijoAsiento="";

	private Asiento asientoSeleccionado = null;
	@SuppressWarnings("rawtypes")
	private EventListener oEventListenerSelect;

	private static final String IMAGE_PRIMER_PISO = "resources/mapa/bus_primerPisoHor.png";
	private static final String IMAGE_SEGUNDO_PISO = "resources/mapa/bus_segundoPisoHor.png";
	private static final int TIPO_ASIENTO = 0;
	private static final int TIPO_MONITOR = 1;
	private static final int TIPO_CAFETERIA = 2;
//	private static final int PISO_DOS = 2;


	/**
	 * Constructor
	 *
	 * @throws Exception
	 */
	public WndMapaBus() throws Exception {
		super();
	}

	/**
	 * @return the txtAsientoSeleccionado
	 */
	public Textbox getTxtAsientoSeleccionado() {
		return txtAsientoSeleccionado;
	}
	/**
	 * @param txtAsientoSeleccionado the txtAsientoSeleccionado to set
	 */
	public void setTxtAsientoSeleccionado(Textbox txtAsientoSeleccionado) {
		this.txtAsientoSeleccionado = txtAsientoSeleccionado;
		txtAsiento.setText(this.txtAsientoSeleccionado.getText());
	}

	/**
	 * @return the txtPisoSeleccionado
	 */
	public Textbox getTxtPisoSeleccionado() {
		return txtPisoSeleccionado;
	}
	/**
	 * @param txtPisoSeleccionado the txtPisoSeleccionado to set
	 */
	public void setTxtPisoSeleccionado(Textbox txtPisoSeleccionado) {
		this.txtPisoSeleccionado = txtPisoSeleccionado;
		txtPiso.setText(this.txtPisoSeleccionado.getText());
	}

	/**
	 * @return the ventaPasaje
	 */
	public VentaPasaje getVentaPasaje() {
		return ventaPasaje;
	}
	/**
	 * @param ventaPasaje the ventaPasaje to set
	 */
	public void setVentaPasaje(VentaPasaje ventaPasaje) {
		this.ventaPasaje = ventaPasaje;
	}

	/**
	 * @return the detalleItinerario
	 */
	public DetalleItinerario getDetalleItinerario() {
		return detalleItinerario;
	}
	/**
	 * @param detalleItinerario the detalleItinerario to set
	 */
	public void setDetalleItinerario(DetalleItinerario detalleItinerario) {
		this.detalleItinerario = detalleItinerario;
	}

	/**
	 * @return the usuarioHardware
	 */
	@Override
	public UsuarioHardware getUsuarioHardware() {
		return usuarioHardware;
	}
	/**
	 * @param usuarioHardware the usuarioHardware to set
	 */
	public void setUsuarioHardware(UsuarioHardware usuarioHardware) {
		this.usuarioHardware = usuarioHardware;
	}

	/**
	 * @return the selectAsiento
	 */
	public boolean isSelectAsiento() {
		return selectAsiento;
	}
	/**
	 * @param selectAsiento the selectAsiento to set
	 */
	public void setSelectAsiento(boolean selectAsiento) {
		this.selectAsiento = selectAsiento;
	}

	public void  setAsientoSeleccionado(Asiento asientoSeleccionado){
		this.asientoSeleccionado=asientoSeleccionado;
	}

	public Asiento getAsientoSeleccionado(){
		return asientoSeleccionado;
	}

	/*
	 * (non-Javadoc)
	 * @see com.tepsa.sisvyr.view.ui.WndBase#onCreate()
	 */
	@Override
	public void onCreate() throws Exception {
		usuarioHardware = getUsuarioHardware();
		/*********************************************************************/

		crearEstructura(ventaPasaje);
		if(getVentaPasaje().getNumeroAsiento()!=null && !selectAsiento){
			txtAsiento.setText(getVentaPasaje().getNumeroAsiento().toString());
			txtPiso.setText(getVentaPasaje().getNumeroPiso().toString());
		}
	}

	@Override
	public void initComponents() {
		this.setMaximizable(false);
		this.setMinimizable(false);
		this.setSizable(false);
		this.setClosable(false);
		this.setStyle("padding: 5px");
		this.setWidth("676px");
		this.setVisible(true);

		try{
			ventaPasaje = getVentaPasaje();
			ventaPasaje.setServicio((Servicio)ServiceLocator.getHibernateSession().merge(ventaPasaje.getServicio()));
			setVentaPasaje(ventaPasaje);
		}catch(Exception ex){
			ex.printStackTrace();
			DlgMessage.information(this.getClass().getSimpleName()+" "+ex.getMessage());
		}
	}

	/**
	 * Carga el Mapa
	 *
	 * @throws Exception
	 */
	@SuppressWarnings({"rawtypes", "unchecked"})
	public void load() throws Exception {
		EventListener selectedEventListener = new EventListener<Event>() {
			@Override
			public void onEvent(Event event) throws Exception {
				if(oEventListenerSelect !=null){
					if(!txtAsiento.getText().trim().equals("")){
						txtAsientoSeleccionado.setText(txtAsiento.getText());
						txtPisoSeleccionado.setText(txtPiso.getText());
						oEventListenerSelect.onEvent(new Event(pe.itsb.vyrbus.view.ui.Events.ON_SELECT));
						oThisWindow.onClose();
					}else
						DlgMessage.information(Messages.getString("WndPostergacion.information.noAsientoSeleccionado"));
				}
			}
		};

		/* ------------------ CONTENEDOR DE OBJETOS --------------------- */
		divContenedorBus = new Div();
		Vlayout hLayoutPrincipal = new Vlayout();
		vbxEstructuraBus = new Vbox();
		hLayoutPrincipal.appendChild(vbxEstructuraBus);

		Hlayout hlayout = new Hlayout();
		Groupbox groupbox = new Groupbox();
		groupbox.setMold("3d");
		groupbox.setClosable(false);
		Caption caption = new Caption("LEYENDA DE IMAGENES");
		caption.setStyle("color: #ffffff;");
		groupbox.appendChild(caption);
		Grid grid = new Grid();
		grid.setWidth("341px");
		Rows rows = new Rows();
		Row row = new Row();
		Hbox hbox = new Hbox();
		hbox.setWidth("260px");
		hbox.setAlign("center");

		Hbox hbox2 = new Hbox();
		hbox2.setAlign("center");
		hbox2.setWidth("26px");
		Image image = new Image("/resources/asientos/asientoDisponible_1.png");
		image.setStyle("font-size:9px");
		hbox2.appendChild(image);
		hbox.appendChild(hbox2);

		hbox2 = new Hbox();
		hbox2.setAlign("center");
		hbox2.setWidth("100px");
		Label label = new Label("Disponible");
		label.setStyle("font-size:9px");
		hbox2.appendChild(label);
		hbox.appendChild(hbox2);

		hbox2 = new Hbox();
		hbox2.setAlign("center");
		hbox2.setWidth("26px");
		image = new Image("/resources/asientos/asientoReservado_1.png");
		image.setStyle("font-size:9px");
		hbox2.appendChild(image);
		hbox.appendChild(hbox2);

		hbox2 = new Hbox();
		hbox2.setAlign("center");
		hbox2.setWidth("100px");
		label = new Label("Reservado");
		label.setStyle("font-size:9px");
		hbox2.appendChild(label);
		hbox.appendChild(hbox2);

		row.appendChild(hbox);
		rows.appendChild(row);

		row = new Row();
		hbox = new Hbox();
		hbox.setWidth("260px");
		hbox.setAlign("center");

		hbox2 = new Hbox();
		hbox2.setAlign("center");
		hbox2.setWidth("26px");
		image = new Image("/resources/asientos/asientoReservadoDelivery_1.png");
		image.setStyle("font-size:9px");
		hbox2.appendChild(image);
		hbox.appendChild(hbox2);

		hbox2 = new Hbox();
		hbox2.setAlign("center");
		hbox2.setWidth("100px");
		label = new Label("Reservado x Delivery");
		label.setStyle("font-size:9px");
		hbox2.appendChild(label);
		hbox.appendChild(hbox2);

		hbox2 = new Hbox();
		hbox2.setAlign("center");
		hbox2.setWidth("26px");
		image = new Image("/resources/asientos/asientoReservadoAgente_1.png");
		image.setStyle("font-size:9px");
		hbox2.appendChild(image);
		hbox.appendChild(hbox2);

		hbox2 = new Hbox();
		hbox2.setAlign("center");
		hbox2.setWidth("100px");
		label = new Label("Reservado x Agente");
		label.setStyle("font-size:9px");
		hbox2.appendChild(label);
		hbox.appendChild(hbox2);

		row.appendChild(hbox);
		rows.appendChild(row);

		row = new Row();
		hbox = new Hbox();
		hbox.setWidth("260px");
		hbox.setAlign("center");

		hbox2 = new Hbox();
		hbox2.setAlign("center");
		hbox2.setWidth("26px");
		image = new Image("/resources/asientos/asientoVendidoFemale_1.png");
		image.setStyle("font-size:9px");
		hbox2.appendChild(image);
		hbox.appendChild(hbox2);

		hbox2 = new Hbox();
		hbox2.setAlign("center");
		hbox2.setWidth("100px");
		label = new Label("Venta Counter");
		label.setStyle("font-size:9px");
		hbox2.appendChild(label);
		hbox.appendChild(hbox2);

		hbox2 = new Hbox();
		hbox2.setAlign("center");
		hbox2.setWidth("26px");
		image = new Image("/resources/asientos/asientoVendidoMale_1.png");
		image.setStyle("font-size:9px");
		hbox2.appendChild(image);
		hbox.appendChild(hbox2);

		hbox2 = new Hbox();
		hbox2.setAlign("center");
		hbox2.setWidth("100px");
		label = new Label("Venta Counter");
		label.setStyle("font-size:9px");
		hbox2.appendChild(label);
		hbox.appendChild(hbox2);

		row.appendChild(hbox);
		rows.appendChild(row);

		row = new Row();
		hbox = new Hbox();
		hbox.setWidth("260px");
		hbox.setAlign("center");

		hbox2 = new Hbox();
		hbox2.setAlign("center");
		hbox2.setWidth("26px");
		image = new Image("/resources/asientos/asientoVendidoWebFemale_1.png");
		image.setStyle("font-size:9px");
		hbox2.appendChild(image);
		hbox.appendChild(hbox2);

		hbox2 = new Hbox();
		hbox2.setAlign("center");
		hbox2.setWidth("100px");
		label = new Label("Venta Web");
		label.setStyle("font-size:9px");
		hbox2.appendChild(label);
		hbox.appendChild(hbox2);

		hbox2 = new Hbox();
		hbox2.setAlign("center");
		hbox2.setWidth("26px");
		image = new Image("/resources/asientos/asientoVendidoWebMale_1.png");
		image.setStyle("font-size:9px");
		hbox2.appendChild(image);
		hbox.appendChild(hbox2);

		hbox2 = new Hbox();
		hbox2.setAlign("center");
		hbox2.setWidth("100px");
		label = new Label("Venta Web");
		label.setStyle("font-size:9px");
		hbox2.appendChild(label);
		hbox.appendChild(hbox2);

		row.appendChild(hbox);
		rows.appendChild(row);

		row = new Row();
		hbox = new Hbox();
		hbox.setWidth("260px");
		hbox.setAlign("center");

		hbox2 = new Hbox();
		hbox2.setAlign("center");
		hbox2.setWidth("26px");
		image = new Image("/resources/asientos/asientoBloqueado_1.png");
		image.setStyle("font-size:9px");
		hbox2.appendChild(image);
		hbox.appendChild(hbox2);

		hbox2 = new Hbox();
		hbox2.setAlign("center");
		hbox2.setWidth("100px");
		label = new Label("Bloqueado");
		label.setStyle("font-size:9px");
		hbox2.appendChild(label);
		hbox.appendChild(hbox2);

		hbox2 = new Hbox();
		hbox2.setAlign("center");
		hbox2.setWidth("26px");
		image = new Image("/resources/asientos/asientoSemiOcupado_1.png");
		image.setStyle("font-size:9px");
		hbox2.appendChild(image);
		hbox.appendChild(hbox2);

		hbox2 = new Hbox();
		hbox2.setAlign("center");
		hbox2.setWidth("100px");
		label = new Label("Prioridad de Venta en Tramo");
		label.setStyle("font-size:9px");
		hbox2.appendChild(label);
		hbox.appendChild(hbox2);

		row.appendChild(hbox);
		rows.appendChild(row);
		grid.appendChild(rows);
		groupbox.appendChild(grid);
		hlayout.appendChild(groupbox);

		Separator separator = new Separator();
		hlayout.appendChild(separator);

		Vlayout vlayout = new Vlayout();
		groupbox = new Groupbox();
		groupbox.setMold("3d");
		groupbox.setClosable(false);
		caption = new Caption("OCUPABILIDAD DEL SERVICIO");
		caption.setStyle("color: #ffffff;");
		groupbox.appendChild(caption);
		grdOcupabilidad = new Grid();
		grdOcupabilidad.setWidth("292px");
		grdOcupabilidad.setStyle("padding:0px");
		Auxhead auxhead = new Auxhead();
		Auxheader auxheader = new Auxheader("Ocupabilidad del Servicio");
		auxheader.setColspan(3);
		grdOcupabilidad.appendChild(auxhead);
		Columns columns = new Columns();
		Column column = new Column("RUTA", "", "40%");
		columns.appendChild(column);
		column = new Column("OCUPADOS", "", "30%");
		column.setAlign("center");
		columns.appendChild(column);
		column = new Column("DISPONIBLES", "", "30%");
		column.setAlign("center");
		columns.appendChild(column);
		grdOcupabilidad.appendChild(columns);
		rows = new Rows();
		grdOcupabilidad.appendChild(rows);
		groupbox.appendChild(grdOcupabilidad);
		vlayout.appendChild(groupbox);

		Hlayout hlayout1 = new Hlayout();
		label = new Label("Asiento Seleccionado :");
		hlayout1.appendChild(label);
		txtAsiento = new Textbox();
		txtAsiento.setWidth("20px");
		txtAsiento.setReadonly(true);
		txtAsiento.setStyle("color:blue; font-weight:bold");
		hlayout1.appendChild(txtAsiento);
		txtPiso = new Textbox();
		txtPiso.setWidth("20px");
		txtPiso.setReadonly(true);
		txtPiso.setVisible(false);
		hlayout1.appendChild(txtPiso);
		vlayout.appendChild(hlayout1);

		grid = new Grid();
		grid.setWidth("290px");
		grid.setStyle("border:none");
		rows = new Rows();
		row = new Row();
		row.setStyle("background:white");
		Button button = new Button("Refrescar Mapa", "/resources/mp_recargar16.png");
//		button.setHeight("28px");
		button.setClass("btnCommandXL");
		row.appendChild(button);
		rows.appendChild(row);
		row = new Row();
		row.setStyle("background:white");
		hlayout1 = new Hlayout();
		button = new Button("Aceptar", "/resources/mp_aceptarEnabled.png");
		button.setClass("btnCommandM");
//		button.addEventListener(Events.ON_CLICK, new EventListener<Event>() {
//			public void onEvent(Event e){
//				onRefreshMapaAsientos(mapaAsientos, getDetalleItinerario());
//			}
//		});
		button.addEventListener(Events.ON_CLICK, selectedEventListener);
		hlayout1.appendChild(button);
		button = new Button("Cancelar", "/resources/mp_cancelarEnabled.png");
		button.setClass("btnCommandM");
		button.addEventListener(Events.ON_CLICK, new EventListener<Event>() {
			@Override
			public void onEvent(Event e){
				oThisWindow.onClose();
			}
		});
		hlayout1.appendChild(button);
		row.appendChild(hlayout1);
		rows.appendChild(row);
		grid.appendChild(rows);
		vlayout.appendChild(grid);
		hlayout.appendChild(vlayout);

		hLayoutPrincipal.appendChild(hlayout);
		divContenedorBus.appendChild(hLayoutPrincipal);

		oThisWindow.appendChild(divContenedorBus);
	}

	@SuppressWarnings("deprecation")
	public void crearEstructura(VentaPasaje ventaPasaje){
		try{
			Servicio servicio = null;
			List<MapaBus> lstMapaBus = ServiceLocator.getMapaBusManager().buscarMapaBusHorizontal(ventaPasaje.getServicio().getId(), Constantes.VALUE_ACTIVO);

			Map<Coordenada, MapaBus> mapCoordenadas = new HashMap<>();
			for(MapaBus mapaBus : lstMapaBus){
				Coordenada coordenada = new Coordenada(mapaBus.getNumeroFila(), mapaBus.getNumeroColumna(), mapaBus.getNumeroPiso());
				mapCoordenadas.put(coordenada, mapaBus);
			}

			if(lstMapaBus.size()>0)
				servicio = lstMapaBus.get(0).getServicio();

			int nPisos = servicio.getNumeroPisos();
			int nFilas = servicio.getNumeroFilasPiso1();
			int nColumnas = servicio.getNumeroColumnasPiso1();
			prefijoAsiento = "imgAsientoPiso1_";
			int numeroAsiento = 0;

			inicializarEstructura();

//			Image imagen = generarImagen(IMAGE_PRIMER_PISO, 154, 43);
			Image imagen = generarImagen(IMAGE_PRIMER_PISO, 43, 170);

			mapaAsientos = new HashMap<>();
			Hlayout ohlayout = new Hlayout();

			/*	Recorremos la cantidad de pisos del servicio	*/
			for(int i=0; i<nPisos; i++){
				String idGrid = "grdPiso1";
				String idTam = (nPisos == 2 ? "130px" : "500px");
				/*	Si cambiamos al siguiente piso redefinimos los valores de las variables	*/
				if(i==1){
					nFilas = servicio.getNumeroFilasPiso2();
					nColumnas = servicio.getNumeroColumnasPiso2();
					prefijoAsiento = "imgAsientoPiso2_";
					idGrid = "grdPiso2";
					idTam = "450px";
//					imagen = generarImagen(IMAGE_SEGUNDO_PISO, 154, 34);
					imagen = generarImagen(IMAGE_SEGUNDO_PISO, 34, 170);
				}
				ohlayout.appendChild(imagen);
				/*	Creando la grilla contenedora de asientos	*/
				Grid gridPiso = new Grid();
				gridPiso.setId(idGrid);
				gridPiso.setStyle("border:none;background:white");
				//gridPiso.setWidth("154px");
				gridPiso.setWidth(idTam);
				gridPiso.setHeight("170px");
				Rows rows = new Rows();
				Row row = new Row();
//				row.setSpans(String.valueOf(nColumnas));
//				row.appendChild(imagen);
//				row.setStyle("background:white; padding:0px");
//				rows.appendChild(row);
				numeroAsiento = 0;
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

								HashMap<String, String> propiedades = new HashMap<>();
								numeroAsiento++;

								/*	Verificamos que el objeto sea del tipo Asiento	*/
								if(objetoBus.getTipoObjeto().intValue()==TIPO_ASIENTO){
									Asiento asiento = new Asiento();
									asiento.addEventListener(Events.ON_CLICK, new EventListener<Event>() {
										@Override
										public void onEvent(Event e){
											onClickAsiento(e, getVentaPasaje());
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
						row.setStyle("padding-top:2px; padding-left:3px; padding-right:0px; border:normal !important; background:#EEEEEE");// background:#99D9EA");
					}
					rows.appendChild(row);
				}
				gridPiso.appendChild(rows);
				ohlayout.appendChild(gridPiso);
				vbxEstructuraBus.appendChild(ohlayout);
				onRefreshMapaAsientos(mapaAsientos, getDetalleItinerario());
			}
		}catch(Exception ex){
			ex.printStackTrace();
		}
	}

	/**
	 * Inicializa(limpia los objetos existentes) el contenedor de los asientos.
	 */
	private void inicializarEstructura(){
		for(int i=vbxEstructuraBus.getChildren().size()-1; i>-1; i--){
			Component component = vbxEstructuraBus.getChildren().get(i);
			vbxEstructuraBus.removeChild(component);
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
	 * Evento utilizado cuando el usuario hace click en un asiento.
	 * @param e				: Evento
	 * @param mapaAsientos	: Mapa de asientos.
	 * @param ventaPasaje	: Itinerario seleccionado.
	 * @param esIda			: Indica si es Ida o retorno.
	 */
	private void onClickAsiento(Event e, VentaPasaje ventaPasaje){
		Asiento asientoSeleccionado = (Asiento)e.getTarget();
		previousKey = key;
		key = asientoSeleccionado.getKey();

		/*	Elimina el asiento de la lista de asientos seleccionados y desbloquea el asiento*/
		if(removerAsientoSeleccionado(previousKey, key)){
			txtAsiento.setText("");
			txtPiso.setText("");
			return;
		}

		try{
			if(consultaAsientoBloqueado(key)){
				DlgMessage.information(Messages.getString("WndVentaReserva.information.asientoNoDisponible"));
				key="-1";
			}else{
				TmpOcupacionAsientos tmpOcupacionAsientos = new TmpOcupacionAsientos();
				/*	*********************************************************************************************************	*/
				/*	Aqui habra que modificar el usuario*/
				tmpOcupacionAsientos.setUsuarioHardware(usuarioHardware);
				tmpOcupacionAsientos.setUsuario(getUsuario());
				/*	*********************************************************************************************************	*/
				tmpOcupacionAsientos.setRuta(ventaPasaje.getRuta());
				tmpOcupacionAsientos.setItinerario(ventaPasaje.getItinerario());
				tmpOcupacionAsientos.setNumeroAsiento(asientoSeleccionado.getNumeroAsiento());
				tmpOcupacionAsientos.setNumeroPiso(asientoSeleccionado.getPiso());
				tmpOcupacionAsientos.setFechaPartida(Util.DatetoString(ventaPasaje.getFechaPartida(), Constantes.DATE_FORMAT));
				tmpOcupacionAsientos.setHoraPartida(ventaPasaje.getHoraPartida());
				tmpOcupacionAsientos.setEstadoRegistro(Constantes.VALUE_ACTIVO);
				UtilData.auditarRegistro(tmpOcupacionAsientos, false,getUsuario(), Executions.getCurrent());
				TmpOcupacionAsientosID tmpOcupacionAsientosID = new TmpOcupacionAsientosID();
				tmpOcupacionAsientosID.setIdUsuarioHardware(tmpOcupacionAsientos.getUsuarioHardware().getId());
				tmpOcupacionAsientosID.setIdUsuario(tmpOcupacionAsientos.getUsuario().getId());
				tmpOcupacionAsientosID.setIdRuta(tmpOcupacionAsientos.getRuta().getId());
				tmpOcupacionAsientosID.setIdItinerario(tmpOcupacionAsientos.getItinerario().getId());
				tmpOcupacionAsientosID.setNumeroAsiento(tmpOcupacionAsientos.getNumeroAsiento());
				tmpOcupacionAsientos.setTmpOcupacionAsientosID(tmpOcupacionAsientosID);
				txtAsiento.setText(asientoSeleccionado.getNumeroAsiento().toString());
				txtPiso.setText(String.valueOf(asientoSeleccionado.getPiso()));

				int result = ServiceLocator.getTmpOcupacionAsientosManager().bloquearAsiento(tmpOcupacionAsientos);
				if(result < 0){
					DlgMessage.information(Messages.getString("WndVentaReserva.information.asientoBloqueado"));
					onRefreshMapaAsientos(mapaAsientos, detalleItinerario);
					return;
				}else{
					mapaAsientos.get(key).setSrc(Constantes.PATH_PARTIAL+"asientoBloqueado_"+asientoSeleccionado.getNumeroAsiento()+Constantes.IMAGE_EXTENSION);
					mapaAsientos.get(key).setEstadoAsiento(Constantes.ASIENTO_BLOQUEADO);

//					//MAOE: La idea es que cada asiento seleccionado tenga su tarifa de acuerdo al nuevo modelo
//					//Obtener la tarifa regular del asiento y almacenarlo en DetalleItinerario del asientoSeleccionado
					asientoSeleccionado.setDetalleItinerario(ventaPasaje.getDetalleItinerario());

					List<TarifaRegular> lstTarifaRegular = ServiceLocator.getTarifaRegularManager().buscarTarifaPorServicio(
							1, ventaPasaje.getServicio().getId(),
							ventaPasaje.getRuta().getId(),
							Util.DatetoString(ventaPasaje.getFechaPartida(), Constantes.DATE_FORMAT),
							ventaPasaje.getHoraPartida(),
							asientoSeleccionado.getPiso(),
							asientoSeleccionado.getNumeroZona(),
							ventaPasaje.getEmpresa().getId());
//					if(lstTarifaRegular.size()>0)
//						asientoSeleccionado.getDetalleItinerario().setTarifa(lstTarifaRegular.get(0).getMonto()!=null
//																			 ?lstTarifaRegular.get(0).getMonto()
//																			 :0.00);
//					else
//						asientoSeleccionado.getDetalleItinerario().setTarifa(0.00);
					if(lstTarifaRegular.size()>0)
						asientoSeleccionado.getDetalleItinerario().setTarifa(lstTarifaRegular.get(0).getMonto()!=null?lstTarifaRegular.get(0).getMonto() :0.00);
					else
						asientoSeleccionado.getDetalleItinerario().setTarifa(0.00);

					setAsientoSeleccionado(asientoSeleccionado);
					/*	Esto es para desbloquear el asiento en caso seleccione otro asiento sin haber cerrado la ventana del mapa	*/
					if(!previousKey.equals("-1") && !previousKey.equals(key)){
						String[] buffer = previousKey.split("-");	//Almacenamos en un array el asiento y el piso
						TmpOcupacionAsientos tmpOcupacion = new TmpOcupacionAsientos();
						tmpOcupacion.setRuta(ventaPasaje.getRuta());
						tmpOcupacion.setItinerario(ventaPasaje.getItinerario());
						tmpOcupacion.setNumeroAsiento(Integer.valueOf(buffer[0]));
						tmpOcupacion.setNumeroPiso(Integer.valueOf(buffer[1]));
						ServiceLocator.getTmpOcupacionAsientosManager().desbloquearAsiento(tmpOcupacion);
						mapaAsientos.get(previousKey).setSrc(Constantes.PATH_PARTIAL+"asientoDisponible_"+buffer[0]+Constantes.IMAGE_EXTENSION);
						mapaAsientos.get(previousKey).setEstadoAsiento(Constantes.ASIENTO_DISPONIBLE);
					}
					/*	Esto es para desbloquear el asiento en caso quiera volver a cambiar de asiento luego de haber cerrado la ventana del mapa*/
//					if(this.getDesktop().getSession().getAttribute("asientoSeleccionado")!=null){
					if(!txtAsientoSeleccionado.getText().equals("") && !txtAsientoSeleccionado.getText().equals(asientoSeleccionado.getNumeroAsiento().toString())){
						TmpOcupacionAsientos tmpOcupacion = new TmpOcupacionAsientos();
						tmpOcupacion.setRuta(ventaPasaje.getRuta());
						tmpOcupacion.setItinerario(ventaPasaje.getItinerario());
						tmpOcupacion.setNumeroAsiento(Integer.valueOf(txtAsientoSeleccionado.getText()));
						tmpOcupacion.setNumeroPiso(Integer.valueOf(txtPisoSeleccionado.getText()));
						ServiceLocator.getTmpOcupacionAsientosManager().desbloquearAsiento(tmpOcupacion);
						String key = tmpOcupacion.getNumeroAsiento()+"-"+tmpOcupacion.getNumeroPiso();
						mapaAsientos.get(key).setSrc(Constantes.PATH_PARTIAL+"asientoDisponible_"+txtAsientoSeleccionado.getText()+Constantes.IMAGE_EXTENSION);
						mapaAsientos.get(key).setEstadoAsiento(Constantes.ASIENTO_DISPONIBLE);
//						this.getDesktop().getSession().removeAttribute("asientoSeleccionado");
					}
				}
			}System.out.print(asientoSeleccionado.getDetalleItinerario().getTarifa().toString());
		}catch(DuplicateSeatException dsex){
			DlgMessage.information(Messages.getString("WndVentaReserva.information.asientoVendido"));
			onRefreshMapaAsientos(mapaAsientos, detalleItinerario);
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
	private boolean removerAsientoSeleccionado(String previusKey, String key){
		try{
			String[] buffer = key.split("-");	//Almacenamos en un array el asiento y el piso
			if(previusKey.equals(key)){
				if(mapaAsientos.get(key).getEstadoAsiento().intValue()==Constantes.ASIENTO_BLOQUEADO){
					mapaAsientos.get(key).setEstadoAsiento(Constantes.ASIENTO_DISPONIBLE);
					mapaAsientos.get(key).setSrc(Constantes.ICON_DISPONIBLE+buffer[0]+Constantes.IMAGE_EXTENSION);

					TmpOcupacionAsientos tmpOcupacion = new TmpOcupacionAsientos();
					tmpOcupacion.setRuta(ventaPasaje.getRuta());
					tmpOcupacion.setItinerario(ventaPasaje.getItinerario());
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
	 * Realiza el refresco del mapa del bus.
	 * @throws Exception
	 */
	public void onRefreshMap() throws Exception{
		onRefreshMapaAsientos(mapaAsientos, detalleItinerario);
	}

	/**
	 * Realiza el refresco del mapa del bus.
	 * @param mapa				: Mapa de asientos
	 * @param detalleItinerario	: Itinerario con el que se desea actualizar el mapa.
	 */
	@SuppressWarnings("unchecked")
	public void onRefreshMapaAsientos(Map<String, Asiento> mapaAsientos, DetalleItinerario detalleItinerario){
		try{
			/*Busca configuracion para la validacion de la venta adelantada - 09/02/2015 (jabanto)*/
			VentaTramo ventaTramo=ServiceLocator.getVentaTramoManager().buscarPorItinerarioRuta(detalleItinerario.getItinerario(), detalleItinerario.getRuta());

			onCleanMap(mapaAsientos);
			/*	Obtenemos el subconjunto que queremos buscar segun la ruta seleccioanda		*/
			List<Integer> subConjuntoBuscar = obtenerSubconjunto(detalleItinerario.getItinerario().getListSecuenciaTramo(), detalleItinerario.getRuta().getLocalidadOrigen().getId(), detalleItinerario.getRuta().getLocalidadDestino().getId());

			int nOcupados = 0;
			List<VentaPasaje> lstVentas = ServiceLocator.getVentaPasajesManager().buscarVentasForMapaBus(detalleItinerario.getItinerario().getId());
			lstVentas = obtenerConjuntos(lstVentas, detalleItinerario.getItinerario().getListSecuenciaTramo());
			if(lstVentas.size()>0){
				for(VentaPasaje venta : lstVentas){
					String key = venta.getNumeroAsiento()+"-"+venta.getNumeroPiso();
					for(Integer orden : subConjuntoBuscar){
						if(mapaAsientos.containsKey(venta.getNumeroAsiento()+"-"+venta.getNumeroPiso()) && venta.getSubConjunto().contains(orden)){
							Asiento asiento = mapaAsientos.get(venta.getNumeroAsiento()+"-"+venta.getNumeroPiso());
//							/*	Para identificar si la venta es completa o es un tramo	*/
//							if(venta.getRuta().getId()==venta.getItinerario().getRuta().getId()){
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
								asiento.setTooltiptext(venta.getRuta().getOrigen()+"-"+venta.getRuta().getDestino());
//							}else {
//								if(venta.getTipoTransaccion().equals(Constantes.TIPO_OPERACION_VENTA)){
//									asiento.setEstadoAsiento(Constantes.ASIENTO_VENDIDO);
//									asiento.setSrc(Constantes.ICON_TRAMO_VENDIDO+venta.getNumeroAsiento()+Constantes.IMAGE_EXTENSION);
//								}else if(venta.getTipoTransaccion().equals(Constantes.TIPO_OPERACION_RESERVA)){
//									asiento.setSrc(Constantes.ICON_TRAMO_RESERVADO+venta.getNumeroAsiento()+Constantes.IMAGE_EXTENSION);
//									asiento.setEstadoAsiento(Constantes.ASIENTO_RESERVADO);
//								}
//								asiento.setTooltiptext(venta.getRuta().getOrigen()+"-"+venta.getRuta().getDestino());
//							}
							nOcupados++;
							break;
						}
					}
					/*	Para identificar las prioridades del tramo para la venta	*/
					if(venta.getRuta().getLocalidadDestino().getId()==detalleItinerario.getRuta().getLocalidadOrigen().getId()){
						Asiento asiento = mapaAsientos.get(key);
						asiento.setSrc(Constantes.ICON_SEMI_OCUPADO+venta.getNumeroAsiento()+Constantes.IMAGE_EXTENSION);
					}else
						//Valida si la ruta esta configurada para la validacion de la venta adelantada - 09/02/2015 (jabanto)
						if(ventaTramo!=null && ventaTramo.getDespuesHoraSalida().intValue()==Constantes.FALSE_VALUE){
							//Valida si el destino de la ruta es igual al origen de la ruta vendida y si el asiento esta disponible  - 04/02/2015 (jabanto)
							if(detalleItinerario.getRuta().getLocalidadDestino().getId().intValue()==venta.getRuta().getLocalidadOrigen().getId().intValue()
									&& venta.getTipoTransaccion().equals(Constantes.TIPO_OPERACION_VENTA)
									&& mapaAsientos.get(key).getEstadoAsiento().intValue()==Constantes.ASIENTO_DISPONIBLE ){
								Asiento asiento = mapaAsientos.get(key);
								if(asiento!=null && asiento.getEstadoAsiento().intValue()==Constantes.ASIENTO_DISPONIBLE)
									asiento.setSrc(Constantes.ICON_SEMI_OCUPADO+venta.getNumeroAsiento()+Constantes.IMAGE_EXTENSION);
						}
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
							nOcupados++;
							break;
						}
					}
				}
			}

			//Valida si la ruta esta configurada para la validacion de la venta adelantada - 09/02/2015 (jabanto)
			if(ventaTramo!=null && ventaTramo.getDespuesHoraSalida().intValue()==Constantes.FALSE_VALUE){
				for(Entry<?,?> e : mapaAsientos.entrySet()) {
					Asiento asiento=(Asiento) e.getValue();
					//Valida si el asiento esta disponible y no semiocupado
					if(asiento.getEstadoAsiento().intValue()==Constantes.ASIENTO_DISPONIBLE && !asiento.getSrc().equals(Constantes.ICON_SEMI_OCUPADO+asiento.getNumeroAsiento()+Constantes.IMAGE_EXTENSION)){
						//Coloca el estado del asiento ha ocupado
						asiento.setSrc(Constantes.ICON_RESERVADO_AGENTE+asiento.getNumeroAsiento()+Constantes.IMAGE_EXTENSION);
						asiento.setEstadoAsiento(Constantes.ASIENTO_BLOQUEADO);
						asiento.setTooltiptext("OCUPADO");
						nOcupados++;
					}
				}
			}

			mostrarOcupabilidad(nOcupados, ventaPasaje.getServicio(), ventaPasaje.getRuta());
		}catch(Exception ex){
			ex.printStackTrace();
			DlgMessage.error(this.getClass().getName()+" "+ex.getMessage());
		}
	}

	/**
	 * Realiza la limpieza del mapa del bus
	 */
	public void onCleanMap(Map<String, Asiento> mapa){
		for(String key : mapa.keySet()){
			Asiento asiento = mapa.get(key);
			asiento.setSrc(Constantes.ICON_DISPONIBLE+asiento.getNumeroAsiento()+Constantes.IMAGE_EXTENSION);
			asiento.setEstadoAsiento(Constantes.ASIENTO_DISPONIBLE);
			asiento.setTooltiptext("DISPONIBLE");
		}
	}

	private void mostrarOcupabilidad(Integer nOcupados, Servicio servicio, Ruta ruta){
		grdOcupabilidad.getRows().detach();
		Rows rows = new Rows();
		Row row = new Row();
		Label label = new Label(ruta.toString());
		row.appendChild(label);
		label = new Label(nOcupados.toString());
		row.appendChild(label);
		label = new Label(String.valueOf(servicio.getNumeroAsientosPiso1() - nOcupados));
		row.appendChild(label);
		rows.appendChild(row);
		grdOcupabilidad.appendChild(rows);
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
		List<Integer> lstSubconjunto = new ArrayList<>();
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

	@Override
	public boolean addEventListener(String evtnm, EventListener<? extends Event> listener) {
		boolean resultadoEvento = true;
		if(evtnm.equals(pe.itsb.vyrbus.view.ui.Events.ON_SELECT)) {
			oEventListenerSelect = listener;
		}else{
			resultadoEvento = super.addEventListener(evtnm, listener);
		}
		return resultadoEvento;
	}

//	public void onAceptar(){
//		if(!txtAsiento.getText().trim().equals("")){
//			txtAsientoSeleccionado.setText(txtAsiento.getText());
//			txtPisoSeleccionado.setText(txtPiso.getText());
//			super.onClose();
//		}else
//			DlgMessage.information(Messages.getString("WndPostergacion.information.noAsientoSeleccionado"));
//	}

}
