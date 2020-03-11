/**
 * Proyecto		: VYRBUS
 * Sistema		: Sistema de Ventas y Reservas
 * Descripción	: 
 * Autor		: Marco Antonio Oscco Espinoza
 * Fecha		: 2 mar. 2020
 * Hora			: 15:33:55
 */
package com.cystesoft.vyrbus.view.ctrl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.TreeMap;

import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zul.Button;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.Div;
import org.zkoss.zul.Grid;
import org.zkoss.zul.Intbox;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Timebox;
import org.zkoss.zul.Window;

import com.cystesoft.vyrbus.model.bean.CanalVenta;
import com.cystesoft.vyrbus.model.bean.DetalleItinerario;
import com.cystesoft.vyrbus.model.bean.Itinerario;
import com.cystesoft.vyrbus.model.bean.Localidad;
import com.cystesoft.vyrbus.model.bean.Servicio;
import com.cystesoft.vyrbus.model.bean.TipoItinerario;
import com.cystesoft.vyrbus.model.bean.VentaPasaje;
import com.cystesoft.vyrbus.service.locator.ServiceLocator;
import com.cystesoft.vyrbus.service.util.Constantes;
import com.cystesoft.vyrbus.service.util.Util;
import com.cystesoft.vyrbus.service.util.UtilData;
import com.cystesoft.vyrbus.view.ui.DlgMessage;
import com.cystesoft.vyrbus.view.ui.WndFiltrarParametros;
import com.cystesoft.vyrbus.view.ui.WndOpcionesMantenimiento;

/**
 * @author Marco Oscco
 *
 */
public class WndProgramacionTarifa extends WndOpcionesMantenimiento{
	
	private static final long serialVersionUID = 1L;

	private Combobox cmbCanal;
	private Combobox cmbServicio;
	private Combobox cmbOrigen;
	private Combobox cmbDestino;
	private Datebox	dbFechaPartida;
	private Timebox	tbHoraPartida;
	
	private Grid grdRutasTarifas;
	private Datebox dbFechaInicio;
	private Datebox dbFechafin;
	private Div divMantenimiento;


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
		UtilData.cargarDataCombo(cmbCanal, CanalVenta.class, false);
		UtilData.cargarDataCombo(cmbServicio, Servicio.class, false);
		UtilData.cargarDataCombo(cmbOrigen, Localidad.class, false);
		UtilData.cargarDataCombo(cmbDestino, Localidad.class, false);

//		Util.disabledBtnAgregar(true, cmdAgregarTramo, accesoGrabar());
//		Util.disabledBtnAgregar(true, cmdAddTerPartida, accesoGrabar());
//		Util.disabledBtnAgregar(true, cmdAddTerLlegada, accesoGrabar());
		
		divMantenimiento.setWidth("840px");	
		
//		listboxDetalleItinerario.addEventListener(Events.ON_DOUBLE_CLICK,new EventListener<Event>() {
//			@Override
//			public void onEvent(Event event) throws Exception {
//				if(!(btnGuardar.isDisabled()))
//					editarTramo();
//			}
//		});
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.tepsa.sisvyr.view.ui.IBase#initComponents()
	 */
	@Override
	public void initComponents() {
		cmbCanal=(Combobox)this.getFellow("cmbCanal");
		cmbServicio=(Combobox)this.getFellow("cmbServicio");
		cmbOrigen=(Combobox)this.getFellow("cmbOrigen");
		cmbDestino=(Combobox)this.getFellow("cmbDestino");
		dbFechaPartida=(Datebox)this.getFellow("dbFechaPartida");
		tbHoraPartida=(Timebox)this.getFellow("tbHoraPartida");
		grdRutasTarifas=(Grid)this.getFellow("grdRutasTarifas");
		dbFechaInicio=(Datebox)this.getFellow("dbFechaInicio");
		dbFechafin=(Datebox)this.getFellow("dbFechafin");

		divMantenimiento=(Div)this.getFellow("divMantenimiento");
//		cmdEditarTramo=(Button)this.getFellow("cmdEditarTramo");
//		cmdQuitarTramo=(Button)this.getFellow("cmdQuitarTramo");
	}
	
	
	
	
	/* (non-Javadoc)
	 * @see com.cystesoft.vyrbus.view.ui.IOpcionesMantenimiento#onNew()
	 */
	@Override
	public void onNew() throws Exception {
		action=Constantes.ACTION_NEW;
		limpiaTarifa();
		limpiarTodasLasListas();
		cargaComboDefaul();
		LimpiaCombos();

		Util.seleccionarValorItemCombo(CanalVenta.class, cmbCanal, Constantes.ID_TIPITI_REGULAR);
		lstItinerariosDetalle = null;
		oitinerario=null;
		esEdicionTramo=false;
		
	}
	
	
	

	/* (non-Javadoc)
	 * @see com.cystesoft.vyrbus.view.ui.IOpcionesMantenimiento#onSearch()
	 */
	@Override
	public void onSearch() throws Exception {
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
//					criterioOrden="di.d_fecpar, to_date(di.c_horpar,'HH24:MI'), di.d_feclle, to_date(di.c_horlle,'HH24:MI')";
//					
//					if (oWndFiltrar.getParameterValue("1. Origen") instanceof Localidad){
//						Localidad origen = (Localidad) oWndFiltrar.getParameterValue("1. Origen");
//						sOrigen = origen.getDenominacion();
//					}
//					if (oWndFiltrar.getParameterValue("2. Destino") instanceof Localidad){
//						Localidad destino = (Localidad) oWndFiltrar.getParameterValue("2. Destino");
//						sDestino = destino.getDenominacion();
//					}
//					if (oWndFiltrar.getParameterValue("5. Servicio") instanceof Servicio ){
//						Servicio oservicio = (Servicio) oWndFiltrar.getParameterValue("5. Servicio");
//						sServicio = oservicio.getDenominacion();
//					}
//										
//					fechaInicio = (Date) oWndFiltrar.getParameterValue("3. Fecha Inicio");
//					fechaFinal = (Date) oWndFiltrar.getParameterValue("4. Fecha Final");
//					Integer itinerario = (Integer) oWndFiltrar.getParameterValue("6. Nro. Itinerario");
//					if (itinerario !=null)
//						iItinerario=itinerario.longValue();
//					
//					ListaItinerarios(ServiceLocator.getItinerarioManager().buscarItinerariosMantenimiento(iItinerario, sOrigen, 
//							sDestino, Constantes.FORMAT_DATE.format(fechaInicio), Constantes.FORMAT_DATE.format(fechaFinal),
//							sServicio,tipoDeItinerario, criterioOrden));	
			
				}catch (Exception ex){
					DlgMessage.error(this.getClass().getName()+" "+ex.getMessage());
					ex.printStackTrace();
				}
			}
		});

		
	}

	/* (non-Javadoc)
	 * @see com.cystesoft.vyrbus.view.ui.IOpcionesMantenimiento#onRefresh(int)
	 */
	@Override
	public void onRefresh(int tab) throws Exception {
		// TODO Auto-generated method stub
		
	}

	/* (non-Javadoc)
	 * @see com.cystesoft.vyrbus.view.ui.IOpcionesMantenimiento#onModify(int)
	 */
	@Override
	public void onModify(int tab) throws Exception {
		// TODO Auto-generated method stub
		
	}

	/* (non-Javadoc)
	 * @see com.cystesoft.vyrbus.view.ui.IOpcionesMantenimiento#onCancel(int)
	 */
	@Override
	public void onCancel(int action) throws Exception {
		// TODO Auto-generated method stub
		
	}

	/* (non-Javadoc)
	 * @see com.cystesoft.vyrbus.view.ui.IOpcionesMantenimiento#onSave(int)
	 */
	@Override
	public void onSave(int action) throws Exception {
		// TODO Auto-generated method stub
		
	}

	/* (non-Javadoc)
	 * @see com.cystesoft.vyrbus.view.ui.IOpcionesMantenimiento#onDelete(int)
	 */
	@Override
	public void onDelete(int tab) throws Exception {
		// TODO Auto-generated method stub
		
	}

	/* (non-Javadoc)
	 * @see com.cystesoft.vyrbus.view.ui.IOpcionesMantenimiento#onPrint(int)
	 */
	@Override
	public void onPrint(int tab) throws Exception {
		// TODO Auto-generated method stub
		
	}

	/* (non-Javadoc)
	 * @see com.cystesoft.vyrbus.view.ui.IOpcionesMantenimiento#onExport(int)
	 */
	@Override
	public void onExport(int tab) throws Exception {
		// TODO Auto-generated method stub
		
	}

	/* (non-Javadoc)
	 * @see com.cystesoft.vyrbus.view.ui.IOpcionesMantenimiento#onHelp()
	 */
	@Override
	public void onHelp() throws Exception {
		// TODO Auto-generated method stub
		
	}

	/* (non-Javadoc)
	 * @see com.cystesoft.vyrbus.view.ui.IOpcionesMantenimiento#onChangeTab(int)
	 */
	@Override
	public void onChangeTab(int tab) throws Exception {
		cargaComboDefaul();
		LimpiaCombos();
		limpiarTodasLasListas();
		if (!(listboxLista.getSelectedIndex() == -1)){
//			MantenimientoTarifa(((DetalleITarifa)listboxLista.getSelectedItem().getValue()).getItinerario().getId());
			System.out.println("Todo OK!!!!!");
//			CargarRutasTarifas();
//			Util.disabledBtnAgregar(true, cmdAgregarTramo, accesoGrabar());
//			Util.disabledBtnAgregar(true, cmdAddTerPartida, accesoGrabar());
//			Util.disabledBtnAgregar(true, cmdAddTerLlegada, accesoGrabar());
		}
		
	}
	

	public void limpiaTarifa(){
		tbHoraPartida.setValue(null);

	}

		
	public void limpiarTodasLasListas(){
//		Util.limpiarListbox(listboxDetalleItinerario);
//		Util.limpiarListbox(listboxTerminalPartida);
//		Util.limpiarListbox(listboxTerminalLlegada);
		Util.limpiarGrid(grdRutasTarifas);
	}
	
	public void cargaComboDefaul(){
		limpiaTarifa();
//		UtilData.cargarGenericData(cmbCanal, false);
//		UtilData.cargarGenericData(cmbOrigen, false);
//		UtilData.cargarGenericData(cmbDestino, false);
	}
	
	public void LimpiaCombos(){
		cmbCanal.setSelectedIndex(0);
		cmbServicio.setSelectedIndex(0);
		cmbOrigen.setSelectedIndex(0);
		cmbDestino.setSelectedIndex(0);

	}
	
	
	
}
