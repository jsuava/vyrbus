package com.cystesoft.vyrbus.view.ui;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zul.Button;
import org.zkoss.zul.Cell;
import org.zkoss.zul.Column;
import org.zkoss.zul.Columns;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Comboitem;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.Div;
import org.zkoss.zul.Grid;
import org.zkoss.zul.Label;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listhead;
import org.zkoss.zul.Listheader;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Row;
import org.zkoss.zul.Rows;
import org.zkoss.zul.Separator;
import org.zkoss.zul.Window;

import com.cystesoft.vyrbus.model.bean.DetalleItinerario;
import com.cystesoft.vyrbus.model.bean.Itinerario;
import com.cystesoft.vyrbus.model.bean.Localidad;
import com.cystesoft.vyrbus.model.bean.Servicio;
import com.cystesoft.vyrbus.model.bean.TipoItinerario;
import com.cystesoft.vyrbus.service.locator.ServiceLocator;
import com.cystesoft.vyrbus.service.util.Constantes;
import com.cystesoft.vyrbus.service.util.Messages;
import com.cystesoft.vyrbus.service.util.Util;
import com.cystesoft.vyrbus.service.util.UtilData;

/**
 * 
 * @author Marco Oscco Espinoza
 *
 */
public class WndSeleccionaItinerario extends WndBase implements Serializable{
	private static final long serialVersionUID = 1L;
	
	public Combobox cmbOrigen = new Combobox();
	public Combobox cmbDestino = new Combobox();
	public Datebox dbFechaInicio = new Datebox();
	public Datebox dbFechaFin = new Datebox();
	public Combobox cmbServicio = new Combobox();
	private Listbox lisItinerarios = new Listbox();
	private Button cmdAceptar = new Button(); 
	private Combobox cmbTipoItinerario= new Combobox();
	private Button cmdBuscar = new Button();
	private Label lbitem = new Label();
	private Window oThisWindow = this;
	public Boolean servicoEspecial=false;
	
	@SuppressWarnings("rawtypes")
	private EventListener oEventListenerFilter;
	
	private Long idItinerario;
	private String origen;
	private String destino;
	private Long idDetalleItinerario;
	
	private Itinerario itinerario;	
	private DetalleItinerario detalleItinerario;
	/**
	 * Constructor
	 * @throws Exception 
	 */
	public WndSeleccionaItinerario() throws Exception {
		//super();
		this.initComponents();
	}
	
	/*
	 * (non-Javadoc)
	 * @see org.zkoss.zk.ui.AbstractComponent#addEventListener(java.lang.String, org.zkoss.zk.ui.event.EventListener)
	 */
	public boolean addEventListener(String evtnm, EventListener<? extends Event> listener) {
		boolean resultadoEvento = true;
		if (evtnm.equals(com.cystesoft.vyrbus.view.ui.Events.ON_SELECT)) {
			oEventListenerFilter = listener;
		}else {
				resultadoEvento = super.addEventListener(evtnm, listener);
		}
		return resultadoEvento;
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.tepsa.sisvyr.view.ui.WndBase#onCreate()
	 */
	@Override
	public void onCreate() throws Exception {
		UtilData.cargarDataCombo(cmbOrigen, Localidad.class, true);
		UtilData.cargarDataCombo(cmbDestino, Localidad.class, true);
		UtilData.cargarDataCombo(cmbServicio, Servicio.class, true);
		UtilData.cargarDataCombo(cmbTipoItinerario, TipoItinerario.class, true);
		if(servicoEspecial){
			cmbTipoItinerario.setDisabled(true);
			Util.seleccionarValorItemCombo(TipoItinerario.class, cmbTipoItinerario, Constantes.ID_TIPITI_ESPECIAL);
		}else
			Util.seleccionarValorItemCombo(TipoItinerario.class, cmbTipoItinerario, Constantes.ID_TIPITI_REGULAR);
		
				
		Calendar calendar = Calendar.getInstance();
		dbFechaInicio.setValue(calendar.getTime());
		dbFechaFin.setValue(calendar.getTime());
		
		Util.seleccionarValorItemCombo(Localidad.class, cmbOrigen, getUsuarioHardware().getAgencia().getLocalidad().getId());
		
		cmbDestino.setSelectedIndex(0);
		cmbServicio.setSelectedIndex(0);
	}

	/*
	 * (non-Javadoc)
	 * @see com.tepsa.sisvyr.view.ui.WndBase#initComponents()
	 */
	@SuppressWarnings("deprecation")
	@Override
	public void initComponents() {
		Grid oGrid = new Grid();
		Cell oCell = new Cell();
				
		EventListener<Event> selectedEventListener = new EventListener<Event>() {
			@SuppressWarnings("unchecked")
			@Override
			public void onEvent(Event event) throws Exception {
				if (!(event.getTarget() instanceof Listbox && ((Listbox) event.getTarget()).getSelectedIndex() < 0 )) {

					if (lisItinerarios.getSelectedIndex() > -1 && oEventListenerFilter != null) {
						setIdItinerario((Long) ((DetalleItinerario) lisItinerarios.getSelectedItem().getValue()).getItinerario().getId());
						setOrigen(((DetalleItinerario) lisItinerarios.getSelectedItem().getValue()).getRuta().getOrigen());
						setDestino(((DetalleItinerario) lisItinerarios.getSelectedItem().getValue()).getRuta().getDestino());
						setIdDetalleItinerario(((DetalleItinerario)lisItinerarios.getSelectedItem().getValue()).getId());
						setItinerario(ServiceLocator.getItinerarioManager().buscarPorId(getIdItinerario()));
						setDetalleItinerario((DetalleItinerario)lisItinerarios.getSelectedItem().getValue());
						oEventListenerFilter.onEvent(new Event(com.cystesoft.vyrbus.view.ui.Events.ON_SELECT));
						oThisWindow.onClose();
					}
				}
			}
		};
		
		Label label=new Label();
		Row row = new Row();
		Rows rows=new Rows();
		
		/*ORIGEN*/
		label.setValue("Origen");
		label.setSclass("label-size11-bold");
		row.appendChild(label);
		row.appendChild(cmbOrigen);
				
		/*FECHA INICIO*/
		label=new Label("Fecha Inicio");
		label.setSclass("label-size11-bold");
		dbFechaInicio.setFormat("dd/MM/yyyy");
		dbFechaInicio.setReadonly(true);
		row.appendChild(label);
		row.appendChild(dbFechaInicio);
		row.appendChild(new Separator());
		
		rows.appendChild(row);
			
		/*DESTINO*/
		row = new Row();
		label=new Label("Destino");
		label.setSclass("label-size11-bold");
//		cmbDestino.setReadonly(true);
		row.appendChild(label);
		row.appendChild(cmbDestino);
				
		/*FECHA FIN*/
		label = new Label("Fecha fin");
		label.setSclass("label-size11-bold");
		dbFechaFin.setFormat("dd/MM/yyyy");
		dbFechaFin.setReadonly(true);
		row.appendChild(label);
		row.appendChild(dbFechaFin);
		row.appendChild(new Separator());
		rows.appendChild(row);
		
		/*SERVICIO*/
		row=new Row();
		label=new Label("Servicio");
		label.setSclass("label-size11-bold");
		row.appendChild(label);
		row.appendChild(cmbServicio);
		
		/*TIPO DE ITINERARIO*/
		label=new Label("Tipo itinerario");
		label.setSclass("label-size11-bold");
		cmbTipoItinerario.setReadonly(true);
		row.appendChild(label);
		row.appendChild(cmbTipoItinerario);
				
		//cmdBuscar.setWidth("100px");
		cmdBuscar.setAutodisable("self");
//		cmdBuscar.setHeight("30px");
		cmdBuscar.setImage("/resources/mp_buscarEnabled.png");
		cmdBuscar.setClass("btnCommandM");
//		cmdBuscar.setStyle("cursor:pointer");
		cmdBuscar.setLabel("Buscar");
		row.appendChild(cmdBuscar);	
		rows.appendChild(row);
		
		//fila 3
		/*Columnas del Listbox*/
		Listhead listhead = new Listhead();
		Listheader listheader=new Listheader();
		listhead.setSizable(true);
		listheader.setLabel("ITINERARIO");listheader.setWidth("60px"); listheader.setSort("auto"); listheader.setStyle("color: #ffffff;"); listhead.appendChild(listheader); 
		listheader=new Listheader();
		listheader.setLabel("BUS"); listheader.setWidth("40px"); listheader.setSort("auto");listheader.setStyle("color: #ffffff;");listhead.appendChild(listheader); 
		listheader=new Listheader();
		listheader.setLabel("SERVICIO"); listheader.setWidth("120px"); listheader.setSort("auto");listheader.setStyle("color: #ffffff;"); listhead.appendChild(listheader); 
		listheader=new Listheader();
		listheader.setLabel("TARIFA"); listheader.setWidth("60px"); listheader.setSort("auto");listheader.setStyle("color: #ffffff;");  listhead.appendChild(listheader); 
		listheader=new Listheader();
		listheader.setLabel("FEC. SALIDA"); listheader.setWidth("70px"); listheader.setSort("auto");listheader.setStyle("color: #ffffff;"); listhead.appendChild(listheader); 
		listheader=new Listheader();
		listheader.setLabel("HOR. SALIDA"); listheader.setWidth("70px"); listheader.setSort("auto");listheader.setStyle("color: #ffffff;"); listhead.appendChild(listheader); 
		listheader=new Listheader();
		listheader.setLabel("TERM.SALIDA"); listheader.setWidth("120px"); listheader.setSort("auto");listheader.setStyle("color: #ffffff;"); listhead.appendChild(listheader); 
		listheader=new Listheader();
		listheader.setLabel("ORIGEN"); listheader.setWidth("85px"); listheader.setSort("auto");listheader.setStyle("color: #ffffff;"); listhead.appendChild(listheader); 
		listheader=new Listheader();
		listheader.setLabel("DESTINO"); listheader.setWidth("85px"); listheader.setSort("auto");listheader.setStyle("color: #ffffff;"); listhead.appendChild(listheader); 
		listheader=new Listheader();
		listheader.setLabel("TERM.LLEGADA"); listheader.setWidth("120px"); listheader.setSort("auto");listheader.setStyle("color: #ffffff;"); listhead.appendChild(listheader); 
		listheader=new Listheader();
		listheader.setLabel("FEC.LLEGADA");listheader.setWidth("75px"); listheader.setSort("auto");listheader.setStyle("color: #ffffff;"); listhead.appendChild(listheader); 
		listheader=new Listheader();
		listheader.setLabel("HOR.LLEGADA"); listheader.setWidth("70px"); listheader.setSort("auto");listheader.setStyle("color: #ffffff;"); listhead.appendChild(listheader); 
		listheader=new Listheader();
		
		lisItinerarios.setHeight("250px");
		lisItinerarios.setMold("paging");
		lisItinerarios.setPageSize(100);
		lisItinerarios.appendChild(listhead);
		lisItinerarios.addEventListener(Events.ON_DOUBLE_CLICK, selectedEventListener);
		lisItinerarios.addEventListener(Events.ON_OK, selectedEventListener);

		oCell.setColspan(5);
		oCell.appendChild(lisItinerarios);
			
		cmdAceptar.setLabel("Aceptar");
		cmdAceptar.setAutodisable("self");
//		cmdAceptar.setWidth("100px");
//		cmdAceptar.setHeight("30px");
		cmdAceptar.setImage("/resources/mp_aceptarEnabled.png");
		cmdAceptar.setClass("btnCommandL");
//		cmdAceptar.setStyle("cursor:pointer");
		cmdAceptar.addEventListener(Events.ON_CLICK, selectedEventListener);
		
		Label lblSpace= new Label(" ");
		Button cmdCancelar = new Button();
		cmdCancelar.setLabel("Cancelar");
//		cmdCancelar.setWidth("100px");
//		cmdCancelar.setHeight("30px");
		cmdCancelar.setImage("/resources/mp_cerrar.png");
		cmdCancelar.setClass("btnCommandL");
//		cmdCancelar.setStyle("cursor:pointer");
		
		Columns columns = new Columns();
		Column column= new Column();
		column.setWidth("70px");
		columns.appendChild(column);
		column= new Column();
		column.setWidth("170px");
		columns.appendChild(column);
		column= new Column();
		column.setWidth("100px");
		columns.appendChild(column);
		column= new Column();
		column.setWidth("150px");
		columns.appendChild(column);
		
		oGrid.appendChild(columns);
		oGrid.appendChild(rows);

		appendChild(oGrid);
		appendChild(lisItinerarios);

		Div div=new Div();
		div.setAlign("right");
		div.appendChild(cmdAceptar);
		div.appendChild(lblSpace);
		div.appendChild(cmdCancelar);
	
		appendChild(div);
				
		this.setTitle(Messages.getString("SELECCIÓN DE ITINERARIOS"));
		this.setMaximizable(false);
		this.setMinimizable(false);
		this.setSizable(true);
		this.setClosable(true);
		this.setStyle("padding: 5px");
		this.setWidth("850px");
		this.setVisible(true);
		
		/*BUSCA ITINERARIO*/
		cmdBuscar.addEventListener(Events.ON_CLICK, new EventListener<Event>() {
			@Override
			public void onEvent(Event event) throws Exception {
				buscar();
//				String origen=""; String destino =""; String servicio = ""; String tipoDeItinerario="";
//				String criterioOrden="di.d_fecpar, to_date(di.c_horpar,'HH24:MI'), di.d_feclle, to_date(di.c_horlle,'HH24:MI')";
//				
//				if (cmbOrigen.getSelectedItem().getValue() instanceof Localidad)
//					origen=((Localidad) cmbOrigen.getSelectedItem().getValue()).getDenominacion();
//				if (cmbDestino.getSelectedItem().getValue() instanceof Localidad)
//					destino=((Localidad) cmbDestino.getSelectedItem().getValue()).getDenominacion();
//				if (cmbServicio.getSelectedItem().getValue() instanceof Servicio)
//					servicio=((Servicio) cmbServicio.getSelectedItem().getValue()).getDenominacion();
//				if (cmbTipoItinerario.getSelectedItem().getValue() instanceof TipoItinerario)
//					tipoDeItinerario=((TipoItinerario) cmbTipoItinerario.getSelectedItem().getValue()).getDenominacion();
//				
//				ListaItinerarios(ServiceLocator.getItinerarioManager().buscarItinerariosMantenimiento(null, origen, 
//						destino, Constantes.FORMAT_DATE.format(dbFechaInicio.getValue()), Constantes.FORMAT_DATE.format(dbFechaFin.getValue()),
//						servicio,tipoDeItinerario, criterioOrden));
			}
			
		});
		
		/*EVENTO ON_CHANGE DEL LA FECHA INICIO*/
		dbFechaInicio.addEventListener(Events.ON_CHANGE, new EventListener<Event>() {
			@Override
			public void onEvent(Event event) throws Exception {
				String fecha = Util.DatetoString(dbFechaInicio.getValue(), "yyyyMMdd");
				dbFechaFin.setConstraint("after "+fecha);
				/*Asigna a fecha fin el valor de la fecha inicio*/
				dbFechaFin.setValue(dbFechaInicio.getValue());				
			}
		});
		
		/*EVENTO ON_CLICK DEL BOTON CANCELAR*/
		cmdCancelar.addEventListener(Events.ON_CLICK, new EventListener<Event>(){
			@Override
			public void onEvent(Event event) throws Exception {
				oThisWindow.onClose();
			}
		});
		
		
		cmbOrigen.addEventListener(Events.ON_FOCUS,new EventListener<Event>() {
			@Override
			public void onEvent(Event event) throws Exception {
				// TODO Auto-generated method stub
				cmbOrigen.select();
			}
		});
		cmbOrigen.addEventListener(Events.ON_OK, new EventListener<Event>() {
			@Override
			public void onEvent(Event event) throws Exception {
				// TODO Auto-generated method stub
				cmbDestino.setFocus(true);
			}
		});
		cmbDestino.addEventListener(Events.ON_FOCUS,new EventListener<Event>() {
			@Override
			public void onEvent(Event event) throws Exception {
				// TODO Auto-generated method stub
				cmbDestino.select();
			}
		});
		cmbDestino.addEventListener(Events.ON_OK,new EventListener<Event>() {
			@Override
			public void onEvent(Event event) throws Exception {
				// TODO Auto-generated method stub
				buscar();
				if(lisItinerarios.getItems().size()>0){
					Listitem item=lisItinerarios.getItemAtIndex(0);
					lisItinerarios.selectItem(item);
					lisItinerarios.setFocus(true);
				}
			}
		});
		cmbServicio.addEventListener(Events.ON_FOCUS,new EventListener<Event>() {
			@Override
			public void onEvent(Event event) throws Exception {
				// TODO Auto-generated method stub
				cmbServicio.select();
			}
		});
		cmbServicio.addEventListener(Events.ON_OK,new EventListener<Event>() {
			@Override
			public void onEvent(Event event) throws Exception {
				// TODO Auto-generated method stub
				buscar();
				if(lisItinerarios.getItems().size()>0){
					Listitem item=lisItinerarios.getItemAtIndex(0);
					lisItinerarios.selectItem(item);
					lisItinerarios.setFocus(true);
				}
			}
		});
		
	}
	
	public String getOrigen(){
		return origen;
	}
	
	public String setOrigen(String origen){
		return this.origen=origen;
	}
	
	public String getDestino(){
		return destino;
	}
	
	public void setDestino(String destino){
		this.destino=destino;
	}
	
	/**
	 * @return Objeto idItinerario.
	 */
	public Long getIdItinerario() {
		return idItinerario;
	}
	/**
	 * @param idItinerario	: Setea el objeto idItinerario.
	 */
	private void setIdItinerario(Long idItinerario) {
		this.idItinerario = idItinerario;
	}
	
	/**
	 * @param itinerario	: Setea el objeto itinerario.
	 */
	private void setItinerario(Itinerario itinerario) {
		this.itinerario = itinerario;
	}
	/**
	 * @return Objeto itinerario.
	 */
	public Itinerario getItinerario() {
		return itinerario;
	}
	
	/**
	 * @return the idDetalleItinerario
	 */
	public Long getIdDetalleItinerario() {
		return idDetalleItinerario;
	}
	/**
	 * @param idDetalleItinerario the idDetalleItinerario to set
	 */
	public void setIdDetalleItinerario(Long idDetalleItinerario) {
		this.idDetalleItinerario = idDetalleItinerario;
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
	
	private void ListaItinerarios(List<DetalleItinerario> lstItinerarios){
		Util.limpiarListbox(lisItinerarios);
		if(lstItinerarios.size()>0){
			Listitem item = null;
			Listcell cell = null;
			for(DetalleItinerario detalleItinerario : lstItinerarios){
				item = new Listitem();
				cell = new Listcell(detalleItinerario.getItinerario().getId().toString()); //Itinerario
				cell.setStyle("font-size: 11px !important");
				item.appendChild(cell); 
				cell = new Listcell(detalleItinerario.getItinerario().getBus().getCodigo()); //Bus
				cell.setStyle("font-size: 11px !important");
				item.appendChild(cell); 
				cell = new Listcell(detalleItinerario.getItinerario().getServicio().getDenominacion()); //Servicio
				item.appendChild(cell);
				cell = new Listcell(Util.toNumberFormat(detalleItinerario.getTarifa().doubleValue(), 2)); //Tarifa
				cell.setStyle("font-size: 11px !important");
				item.appendChild(cell); 
				cell = new Listcell(Constantes.FORMAT_DATE.format(detalleItinerario.getFechaPartida())); //Fecha Partida
				cell.setStyle("font-size: 11px !important");
				item.appendChild(cell);
				cell = new Listcell(detalleItinerario.getHoraPartida()); //Hora Partida
				cell.setStyle("font-size: 11px !important");
				item.appendChild(cell);
				cell = new Listcell(detalleItinerario.getAgenciaPartida().getNombreCorto()); //Terminal Partida
				item.appendChild(cell);	
				cell = new Listcell(detalleItinerario.getRuta().getOrigen());//Origen
				item.appendChild(cell);
				cell = new Listcell(detalleItinerario.getRuta().getDestino());//Destino
				item.appendChild(cell);
				cell = new Listcell(detalleItinerario.getAgenciaLlegada().getNombreCorto());//Terminal Llegada
				item.appendChild(cell);
				cell = new Listcell(Constantes.FORMAT_DATE.format(detalleItinerario.getFechaLlegada()));//Fecha Llegada
				cell.setStyle("font-size: 11px !important");
				item.appendChild(cell);
				cell = new Listcell(detalleItinerario.getHoraLlegada()); //Hora Llegada
				cell.setStyle("font-size: 11px !important");
				item.appendChild(cell);
				item.setValue(detalleItinerario);
				lisItinerarios.appendChild(item);
				
			}
			Integer contItem =lisItinerarios.getItems().size();
			lbitem.setValue(contItem.toString() + "	Registro(s) encontrado(s).");
			lbitem.setVisible(true);
		}
	}
	
	public void asignarValores(){
		for(Comboitem item : cmbOrigen.getItems()){
			if(item.getValue() instanceof Localidad && ((Localidad)item.getValue()).getDenominacion().equals(getOrigen()))
				cmbOrigen.setSelectedItem(item);
				
		}
		
		for(Comboitem item : cmbDestino.getItems()){
			if(item.getValue() instanceof Localidad && ((Localidad)item.getValue()).getDenominacion().equals(getDestino()))
				cmbDestino.setSelectedItem(item);				
		}
		String fecha = Util.DatetoString(new Date(), "yyyyMMdd");
		dbFechaInicio.setConstraint("after "+fecha);
//		cmbOrigen.setDisabled(true);
//		cmbDestino.setDisabled(true);
	}

	private void buscar() throws Exception{
		String origen=""; String destino =""; String servicio = ""; String tipoDeItinerario="";
		String criterioOrden="di.d_fecpar, to_date(di.c_horpar,'HH24:MI'), di.d_feclle, to_date(di.c_horlle,'HH24:MI')";
		
		if (cmbOrigen.getSelectedIndex()>0)
			origen=((Localidad) cmbOrigen.getSelectedItem().getValue()).getDenominacion();
		if (cmbDestino.getSelectedIndex()>0)
			destino=((Localidad) cmbDestino.getSelectedItem().getValue()).getDenominacion();
		if (cmbServicio.getSelectedIndex()>0)
			servicio=((Servicio) cmbServicio.getSelectedItem().getValue()).getDenominacion();
		if (cmbTipoItinerario.getSelectedItem().getValue() instanceof TipoItinerario)
			tipoDeItinerario=((TipoItinerario) cmbTipoItinerario.getSelectedItem().getValue()).getDenominacion();
		
		ListaItinerarios(ServiceLocator.getItinerarioManager().buscarItinerariosMantenimiento(null, origen, 
				destino, Constantes.FORMAT_DATE.format(dbFechaInicio.getValue()), Constantes.FORMAT_DATE.format(dbFechaFin.getValue()),
				servicio,tipoDeItinerario, criterioOrden));
	}
}
