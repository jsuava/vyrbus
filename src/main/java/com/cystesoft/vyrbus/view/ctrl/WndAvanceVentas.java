/**
 * Proyecto		: SISPAS
 * Sistema		: Sistema de Pasajes
 * Descripción	: 
 * Autor		: Victor Céspedes
 * Fecha		: 8 nov. 2018
 * Hora			: 17:21:50
 */
package com.cystesoft.vyrbus.view.ctrl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zul.Checkbox;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.Image;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listhead;
import org.zkoss.zul.Listheader;
import org.zkoss.zul.Listitem;

import com.cystesoft.vyrbus.model.bean.Localidad;
import com.cystesoft.vyrbus.model.bean.Servicio;
import com.cystesoft.vyrbus.model.bean.VentaPasaje;
import com.cystesoft.vyrbus.service.locator.ServiceLocator;
import com.cystesoft.vyrbus.service.util.Constantes;
import com.cystesoft.vyrbus.service.util.MyTime;
import com.cystesoft.vyrbus.service.util.Util;
import com.cystesoft.vyrbus.service.util.UtilData;
import com.cystesoft.vyrbus.view.ui.DlgMessage;
import com.cystesoft.vyrbus.view.ui.WndBase;


/**
 * @author Jose
 *
 */
public class WndAvanceVentas extends WndBase implements Serializable{
	private static final long serialVersionUID = 1L;
	private Datebox  dbDesde;
	private Combobox cmbOrigen;
	private Combobox cmbTipo;
	private Datebox  dbHasta;
	private Combobox cmbDestino;
	private Combobox cmbServicio;
	private Listbox  lbxAvance = new Listbox();
	private Checkbox chkMostrarTodo;
	
	/* (non-Javadoc)
	 * @see com.movilgroup.sispas.view.ui.WndBase#initComponents()
	 */
	@Override
	public void initComponents() {
		dbDesde   = (Datebox) this.getFellow("dbDesde");
		cmbOrigen = (Combobox) this.getFellow("cmbOrigen");
		//cmbTipo   = (Combobox) this.getFellow("cmbTipo");
		dbHasta   = (Datebox) this.getFellow("dbHasta");
		cmbDestino = (Combobox) this.getFellow("cmbDestino");
		cmbServicio = (Combobox) this.getFellow("cmbServicio");
		chkMostrarTodo = (Checkbox) this.getFellow("chkMostrarTodo");
		
		
		dbDesde.addEventListener(Events.ON_CHANGE, new EventListener<Event>() {
			public void onEvent(Event event) throws Exception {
				String fecha = Util.DatetoString(dbDesde.getValue(), "yyyyMMdd");
				dbHasta.setConstraint("after "+fecha);
				dbHasta.setValue(dbDesde.getValue());
			};
		});
	}
	
	/* (non-Javadoc)
	 * @see com.movilgroup.sispas.view.ui.WndBase#onCreate()
	 */
	@Override
	public void onCreate() throws Exception {
		MyTime time = new MyTime();
		dbDesde.setValue(Constantes.FORMAT_DATE.parse(time.dateServer()));
		dbHasta.setValue(Constantes.FORMAT_DATE.parse(time.dateServer()));
		UtilData.cargarDataCombo(cmbOrigen, Localidad.class, true);
		UtilData.cargarDataCombo(cmbDestino, Localidad.class, true);
		UtilData.cargarDataCombo(cmbServicio, Servicio.class, true);
	}
	
	public void onBuscar() {
		try {
			lbxAvance.getItems().clear();
			String idOrigen = "";
			String idDestino = "";
			String idServicio = "";
			String fechaDesde = Constantes.FORMAT_DATE.format(dbDesde.getValue());
			String fechaHasta = Constantes.FORMAT_DATE.format(dbHasta.getValue());
			
			if(cmbOrigen.getSelectedItem().getValue() instanceof Localidad)
				idOrigen = ((Localidad)cmbOrigen.getSelectedItem().getValue()).getId().toString();
			if(cmbDestino.getSelectedItem().getValue() instanceof Localidad)
				idDestino = ((Localidad)cmbDestino.getSelectedItem().getValue()).getId().toString();
			if(cmbServicio.getSelectedItem().getValue() instanceof Servicio)
				idServicio = ((Servicio)cmbServicio.getSelectedItem().getValue()).getId().toString();
			
			lbxAvance.detach();
			lbxAvance = new Listbox();
			lbxAvance.setHeight("480px");
			
			Listhead listhead = new Listhead();
			
			Listheader listheader = new Listheader();
			listheader.setLabel("#");
			listheader.setWidth("30px");
			listheader.setStyle("color: #ffffff;");
			listhead.appendChild(listheader);
			
			listheader = new Listheader();
			listheader.setLabel("FECHA");
			listheader.setWidth("65px");
			listheader.setStyle("color: #ffffff;");
			listhead.appendChild(listheader);
			
			listheader = new Listheader();
			listheader.setLabel("HORA");
			listheader.setWidth("50px");
			listheader.setStyle("color: #ffffff;");
			listhead.appendChild(listheader);
			
			listheader = new Listheader();
			listheader.setLabel("RUTA");
			listheader.setWidth("160px");
			listheader.setStyle("color: #ffffff;");
			listhead.appendChild(listheader);
			
			listheader = new Listheader();
			listheader.setLabel("SERVICIO");
			listheader.setWidth("100px");
			listheader.setStyle("color: #ffffff;");
			listhead.appendChild(listheader);
			
			listheader = new Listheader();
			listheader.setLabel("CAPACIDAD");
			listheader.setWidth("70px");
			listheader.setStyle("color: #ffffff;");
			listhead.appendChild(listheader);
			
			listheader = new Listheader();
			listheader.setLabel("OCUPACION");
			listheader.setWidth("70px");
			listheader.setStyle("color: #ffffff;");
			listhead.appendChild(listheader);
			
			listheader = new Listheader();
			listheader.setLabel("% OCUP..");			
			listheader.setWidth("70px");
			listheader.setStyle("color: #ffffff;");
			listhead.appendChild(listheader);
			
			listheader = new Listheader();
			listheader.setLabel("ING. 100%");
			listheader.setStyle("text-align:center");
			listheader.setWidth("80px");
			listheader.setStyle("color: #ffffff;");
			listhead.appendChild(listheader);
			
			listheader = new Listheader();
			listheader.setLabel("ING. REAL");
			listheader.setStyle("text-align:center");
			listheader.setWidth("80px");
			listheader.setStyle("color: #ffffff;");
			listhead.appendChild(listheader);
			
			listheader = new Listheader();
			listheader.setStyle("text-align:center");
			listheader.setLabel("DSCTO");
			listheader.setWidth("60px");
			listheader.setStyle("color: #ffffff;");
			listhead.appendChild(listheader);
			listheader.setTooltiptext("Por cortesias");
			
			listheader = new Listheader();
			listheader.setLabel("DSCTO %");
			listheader.setWidth("60px");
			listheader.setStyle("color: #ffffff;");
			listhead.appendChild(listheader);
			
			listheader = new Listheader();
			listheader.setLabel("SOL/KM REAL");
			listheader.setWidth("70px");
			listheader.setStyle("color: #ffffff;");
			listhead.appendChild(listheader);
			
			listheader = new Listheader();
			listheader.setLabel("MAPA");
			listheader.setWidth("60px");
			listheader.setStyle("color: #ffffff;");
			listhead.appendChild(listheader);
			
			lbxAvance.appendChild(listhead);
			
			String style = "font-size:12px !important;font-weight:bold;color:white;text-align:right; border-bottom-color: white;";
			
			List<VentaPasaje> lstAvance = null;
			List<VentaPasaje> lstTarifario = null;
			
			if(idOrigen != "" || idDestino != "") {
				lstAvance = ServiceLocator.getVentaPasajesManager().buscarAvanceVentas(idOrigen, idDestino, idServicio, fechaDesde, fechaHasta);
			}else {
				lstAvance = ServiceLocator.getVentaPasajesManager().buscarAvanceVentas(String.valueOf(Constantes.ID_LOC_LIMA), idDestino, idServicio, fechaDesde, fechaHasta);
			}
			
			addAvance(lstAvance);
			
			// Agrega avance de retorno, solamente si origen y destino están seleccionados en todos.
			if(idOrigen.isEmpty() && idDestino.isEmpty()){
				//Agregar resumen LIMA Provincias
				addResumen("Resumen Lima --> Prov","#4285F4",lstAvance);
				
				//Consulta avance por el destino
				List<VentaPasaje> lstVentasRetorno = new ArrayList<VentaPasaje>();
				lstVentasRetorno = ServiceLocator.getVentaPasajesManager().buscarAvanceVentas(idOrigen, String.valueOf(Constantes.ID_LOC_LIMA), idServicio, fechaDesde, fechaHasta);
				addAvance(lstVentasRetorno);
				addResumen("Resumen Prov --> Lima", "#4285F4", lstVentasRetorno);
				
				//Consulta avance provincias
				List<VentaPasaje> lstRegionales = new ArrayList<VentaPasaje>();
				lstRegionales = ServiceLocator.getVentaPasajesManager().buscarAvanceVentas(String.valueOf(Constantes.ID_LOC_LIMA), String.valueOf(Constantes.ID_LOC_LIMA), idServicio, fechaDesde, fechaHasta);
				addAvance(lstRegionales);
				addResumen("Resumen Regional", "#4285F4", lstRegionales);
				
				addNewItem();
				//Agregar Total Servicios IDA
				addTotalServiciosParcial(lstAvance, "SERV.LIMA --> PROV.", style);
				//Agregar Total Servicios RETORNO
				addTotalServiciosParcial(lstVentasRetorno, "SERV.PROV. --> LIMA", style);
				//Agregar Total Servicios REGIONALES
				addTotalServiciosParcial(lstRegionales, "SERV. REGIONALES", style);
				//Agregar el total de servicios
				addResumenTotal("RESUMEN TOTAL.", lstAvance, lstVentasRetorno, lstRegionales, style);
			}else {
				if(idOrigen.equals(String.valueOf(Constantes.ID_LOC_LIMA)) && idDestino.isEmpty()) {
					addResumen("Resumen Lima-->Prov","#4285F4",lstAvance);
					addNewItem();
					addTotalServiciosParcial(lstAvance, "SERV.LIMA-->PROV.", style);
					addResumenTotal("RESUMEN TOTAL", lstAvance, null, null, style);
				}else if(idOrigen.isEmpty() && idDestino.equals(String.valueOf(Constantes.ID_LOC_LIMA))) {
					addResumen("Resumen Prov-->Lima","#4285F4",lstAvance);
					addNewItem();
					addTotalServiciosParcial(lstAvance, "SERV.PROV.-->LIMA", style);
					addResumenTotal("RESUMEN TOTAL", lstAvance, null, null, style);
				}else {
					addResumen("Resumen "+cmbOrigen.getText()+"-->"+cmbDestino.getText(),"#ec6827",lstAvance);
					addNewItem();
					addTotalServiciosParcial(lstAvance, "SERV."+cmbOrigen.getText()+"-->"+cmbDestino.getText(), style);
					addResumenTotal("RESUMEN TOTAL", lstAvance, null, null, style);
				}
			}
			this.appendChild(lbxAvance);
		}catch (Exception e) {
			// TODO: handle exception
			DlgMessage.error(this.getClass().getSimpleName()+" "+e.getMessage());
		}
	}
	
	private ArrayList<Object> addAvance(List<VentaPasaje> lstAvance) throws Exception{
		Listitem item = null;
		Listcell cell = null;
		String style="";
		ArrayList<Object> xx = new ArrayList<Object>();
		int i = 0;
		Double ocupacion = 0.0;
		String servicio = "";
		
		Boolean bTodos = chkMostrarTodo.isChecked();
		String fechaActual = MyTime.dateTimeServer();
		
		for(VentaPasaje venta : lstAvance) {
			i++;
			item = new Listitem();
			cell = new Listcell(String.valueOf(i));
			cell.setStyle("border-bottom-color: white;");
			item.appendChild(cell);
			
			cell = new Listcell(Constantes.FORMAT_DATE.format(venta.getFechaPartida()));
			cell.setStyle("font-size:11px !important; border-bottom-color: white;");
			item.appendChild(cell);
			
			cell = new Listcell(venta.getHoraPartida());
			cell.setStyle("font-size:11px !important; text-align:center; border-bottom-color: white;");
			item.appendChild(cell);
			
			cell = new Listcell(venta.getRuta().getOrigen()+" - "+venta.getRuta().getDestino());
			cell.setStyle("border-bottom-color: white;");
			item.appendChild(cell);
			
			
			servicio = venta.getServicio().getDenominacion();			
			cell = new Listcell(servicio);
			cell.setStyle("border-bottom-color: white;");
			item.appendChild(cell);
			
			cell = new Listcell(venta.getServicio().getTotalAsientos().toString());
			cell.setStyle("font-size:11px !important; text-align:center; border-bottom-color: white;");
			item.appendChild(cell);
			
			cell = new Listcell(venta.getCantidadPax().toString());
			cell.setStyle("font-size:11px !important; text-align:center; border-bottom-color: white;");
			item.appendChild(cell);
			
			ocupacion = Double.valueOf(venta.getCantidadPax())/Double.valueOf(venta.getServicio().getTotalAsientos()) * 100;
			cell = new Listcell(Util.toNumberFormat(ocupacion,2).toString()+"%");
			if(ocupacion <= 65)
				style= "font-size:11px !important; background:red; text-align:center; color:white !important; font-weight: bold !important; border-bottom-color: white;";
			else if(ocupacion>65 && ocupacion<=80)
				style = "font-size:11px !important; background:yellow; text-align:center; font-weight: bold !important; border-bottom-color: white;";
			else if(ocupacion>80 && ocupacion <= 99)
				style = "font-size:11px !important; background:#92D050; text-align:center; color:white; font-weight: bold !important; border-bottom-color: white;";
			else
				style = "font-size:11px !important; background:#0066FF; text-align:center; color:white !important; font-weight: bold !important;border-bottom-color: white;";
			
			cell.setStyle(style);
			item.appendChild(cell);
			
			cell = new Listcell(venta.getImporteEsperado()==null?"0":Util.toNumberFormat(venta.getImporteEsperado(), 2));
			cell.setStyle("font-size:11px !important; text-align:right; border-bottom-color: white;");
			item.appendChild(cell);
			
			cell = new Listcell(Util.toNumberFormat(venta.getImporteReal(),2));
			cell.setStyle("font-size:11px !important; text-align:right; border-bottom-color: white;");
			item.appendChild(cell);
			
			cell = new Listcell(Util.toNumberFormat(venta.getImporteDescuentos(), 2));
			cell.setStyle("font-size:11px !important; text-align:right; border-bottom-color: white;");
			item.appendChild(cell);
			
			cell = new Listcell(venta.getImporteEsperado()==null?"0":(Util.toNumberFormat((venta.getImporteDescuentos()/venta.getImporteEsperado())*100, 2)+"%"));
			cell.setStyle("font-size:11px !important; text-align:right; border-bottom-color: white;");
			item.appendChild(cell);
			
			cell = new Listcell(Util.toNumberFormat((venta.getImporteReal()/venta.getRuta().getKilometros()), 2));
			cell.setStyle("font-size:11px !important; text-align:right; border-bottom-color: white;");
			item.appendChild(cell);
			
			cell = new Listcell();
			final Image plano = new Image("resources/bus.png");
			plano.addEventListener(Events.ON_CLICK, new EventListener<Event>() {
				public void onEvent(Event arg0) throws Exception {
					Listitem it = (Listitem)plano.getParent().getParent();
//					DlgMessage.error("Es una prueba"+((VentaPasaje)it.getValue()).getItinerario().getId());
					VentaPasaje ventaPasaje = ((VentaPasaje)it.getValue());
					enlazarVerMapa(ventaPasaje.getItinerario().getId().toString(), ventaPasaje.getRuta().getId().toString(), ventaPasaje.getCantidadPax());
				}
			});
			cell.appendChild(plano);
			cell.setTooltiptext("Hacer click aqui para ver el Croquis");
			cell.setStyle("font-size:11px !important; text-align:center; border-bottom-color: white;");
			item.appendChild(cell);
			
			item.setValue(venta);
			
			if(bTodos)
				lbxAvance.appendChild(item);
			else {
//				venta = lstAvance.get(j);
				Boolean valido = Util.comparaFechasWithTime(Util.DatetoString(venta.getFechaPartida(), Constantes.DATE_FORMAT)+" "+venta.getHoraPartida()+":00",fechaActual,  Util.OPER_MAYOR_IGUAL);
				if(valido)
					lbxAvance.appendChild(item);;
			}
			
			
			
			
			
//			lbxAvance.appendChild(item);
		}
		return xx;
	}
	
	public void addResumen(String descripcion, String background, List<VentaPasaje> lstVenta) {
		/*	Calculo de totales	*/
		String style = "font-size:12px !important;font-weight:bold;color:white; border-bottom-color: white;";
		Listitem newItem = new Listitem();
		
		newItem.setStyle("border-bottom-color: white");
		Listcell cell = new Listcell();
		cell.setStyle(style);
		newItem.appendChild(cell);
		
		cell = new Listcell();
		cell.setStyle(style);
		newItem.appendChild(cell);
		
		cell = new Listcell();
		cell.setStyle(style);
		newItem.appendChild(cell);
		
		cell = new Listcell(descripcion);
		cell.setStyle(style);
		newItem.appendChild(cell);
		
		cell = new Listcell(String.valueOf(lstVenta.size()));
		cell.setStyle("font-size:12px !important;font-weight:bold;color:white;text-align:center; border-bottom-color: white;");
		newItem.appendChild(cell);
		
		Integer cap_total = 0;
		Integer total_pax = 0;
		Double ocu_porcentaje = 0.0;
		Double ingreso_esperado = 0.0;
		Double ingreso_real = 0.0;
		Double dscto_total = 0.0;
		Double dscto_total_porcentaje = 0.0;
		Double sol_km_total = 0.0;
//		Integer total_cortesia = 0;	
		Double kilometros = 0.0;
		
		if(lstVenta == null) {
			//Calcula el total de pasajeros
			for(Listitem item : lbxAvance.getItems()) {
				int size = item.getChildren().size();
				String str_cap_total = ((Listcell)item.getChildren().get(5)).getLabel();
				String str_total_pax = ((Listcell)item.getChildren().get(6)).getLabel();
				String str_ingreso_esperado = ((Listcell)item.getChildren().get(8)).getLabel();
				
				if(!str_total_pax.isEmpty()) {
					total_pax+=Integer.valueOf(str_total_pax);
					cap_total+= Integer.valueOf(str_cap_total);
					ocu_porcentaje = Double.valueOf((total_pax/cap_total)*100);
					ingreso_esperado += Double.valueOf(str_ingreso_esperado); 
				}
			}
		}else {
			for(VentaPasaje venta : lstVenta) {
				cap_total+=venta.getServicio().getTotalAsientos();
				total_pax+=venta.getCantidadPax();
				ingreso_real+= venta.getImporteReal();
				dscto_total+= venta.getImporteDescuentos();
				kilometros+= venta.getRuta().getKilometros();
				if(venta.getImporteEsperado()!=null)
					ingreso_esperado += venta.getImporteEsperado();
				else
					ingreso_esperado += 0;
			}
		}
		
		ocu_porcentaje+= Util.formatearDecimales(((Double.valueOf(total_pax)/Double.valueOf(cap_total))*100),2);
		sol_km_total = Util.formatearDecimales(ingreso_real/kilometros, 2);
		dscto_total = Util.formatearDecimales(dscto_total, 2);
		ingreso_esperado = Util.formatearDecimales(ingreso_esperado, 2);
		dscto_total_porcentaje = (dscto_total/ingreso_esperado)*100;
				
		newItem.setStyle("background:"+background);
		
		cell = new Listcell(cap_total.toString());
		cell.setStyle("font-size:12px !important;font-weight:bold;color:white;text-align:center; border-bottom-color: white;");
		newItem.appendChild(cell);
		
		cell = new Listcell(total_pax.toString());
		cell.setStyle("font-size:12px !important;font-weight:bold;color:white;text-align:center; border-bottom-color: white;");
		newItem.appendChild(cell);
		
		cell = new Listcell(ocu_porcentaje.toString()+"%");
		cell.setStyle("font-size:12px !important;font-weight:bold;color:white;text-align:center; border-bottom-color: white;");
		newItem.appendChild(cell);
		
		cell = new Listcell(Util.toNumberFormat(ingreso_esperado, 2));
		cell.setStyle("font-size:12px !important;font-weight:bold;color:white;text-align:right; border-bottom-color: white;");
		newItem.appendChild(cell);
		
		cell = new Listcell(Util.toNumberFormat(ingreso_real, 2));
		cell.setStyle("font-size:12px !important;font-weight:bold;color:white;text-align:right; border-bottom-color: white;");
		newItem.appendChild(cell);
		
		cell = new Listcell(Util.toNumberFormat(dscto_total, 2));
		cell.setStyle("font-size:12px !important;font-weight:bold;color:white;text-align:right; border-bottom-color: white;");
		newItem.appendChild(cell);
		
		cell = new Listcell(Util.toNumberFormat(dscto_total_porcentaje, 2)+"%");
		cell.setStyle("font-size:12px !important;font-weight:bold;color:white;text-align:right; border-bottom-color: white;");
		newItem.appendChild(cell);		
		
		cell = new Listcell(sol_km_total.toString());
		cell.setStyle("font-size:12px !important;font-weight:bold;color:white;text-align:right; border-bottom-color: white;");
		newItem.appendChild(cell);
		
		cell = new Listcell();
		cell.setStyle(style);
		newItem.appendChild(cell);
		newItem.appendChild(cell);
		
		lbxAvance.appendChild(newItem);
	}
	
	/**
	 * Agrega un nuevo item
	 */
	private void addNewItem(){
		Listitem item=new Listitem();
		item.appendChild(new Listcell());
		lbxAvance.appendChild(item);
	}
	
	/**
	 * Agrega el total de servicios de la Ida, Retorno y Regionales
	 * @param lstAvance
	 * @param label
	 * @param style
	 */
	public void addTotalServiciosParcial(List<VentaPasaje> lstAvance, String label,String style){
		addResumen(label, "#626262", lstAvance);
		List<Listitem> lstItem = lbxAvance.getItems();
		Listitem item = lstItem.get(lstItem.size()-1);
		Integer total_servicios = lstAvance.size();
		Listcell cell = ((Listcell)item.getChildren().get(4));
		cell.setLabel(total_servicios.toString());
		cell.setStyle("font-size:12px !important; color:white !important; font-weight:bold; text-align:center; border-bottom-color: white;");
	}
	
	/**
	 * 
	 * @param label
	 * @param lisVentas1
	 * @param lstVentas2
	 * @param lstVentas3
	 * @param style
	 */
	private void addResumenTotal(String label,List<VentaPasaje> lstVentas1,List<VentaPasaje>lstVentas2,List<VentaPasaje>lstVentas3, String style){
		Listitem item = new Listitem();;
		Listcell cell = new Listcell();
		cell.setStyle(style);
		item.appendChild(cell);
		
		cell = new Listcell();
		cell.setStyle(style);
		item.appendChild(cell);
		
		cell = new Listcell();
		cell.setStyle(style);
		item.appendChild(cell);
		
		cell = new Listcell();
		cell.setStyle(style);
		item.appendChild(cell);

		cell.setLabel(label);
		cell.setStyle(style);
		item.appendChild(cell);
		
		Integer cap_total = 0;
		Integer total_pax = 0;
		Double ocu_porcentaje = 0.0;
		Double ingreso_esperado = 0.0;
		Double ingreso_real = 0.0;
		Double dscto_total = 0.0;
		Double dscto_total_porcentaje = 0.0;
		Double sol_km_total = 0.0;
//		Integer total_cortesia = 0;	
		Double kilometros = 0.0;
		Integer total_servicios = 0;
		
		
		if(lstVentas1!=null) {
			for(VentaPasaje venta : lstVentas1) {
				cap_total+=venta.getServicio().getTotalAsientos();
				total_pax+=venta.getCantidadPax();
				ingreso_real+= venta.getImporteReal();
				dscto_total+= venta.getImporteDescuentos();
				kilometros+= venta.getRuta().getKilometros();
				if(venta.getImporteEsperado()!=null)
					ingreso_esperado += venta.getImporteEsperado();
				else
					ingreso_esperado += 0;
			}
			total_servicios+=lstVentas1.size();
		}
		
		if(lstVentas2!=null) {
			for(VentaPasaje venta : lstVentas2) {
				cap_total+=venta.getServicio().getTotalAsientos();
				total_pax+=venta.getCantidadPax();
				ingreso_real+= venta.getImporteReal();
				dscto_total+= venta.getImporteDescuentos();
				kilometros+= venta.getRuta().getKilometros();
				if(venta.getImporteEsperado()!=null)
					ingreso_esperado += venta.getImporteEsperado();
				else
					ingreso_esperado += 0;
			}
			total_servicios+=lstVentas2.size();
		}
		
		if(lstVentas3!=null) {
			for(VentaPasaje venta : lstVentas3) {
				cap_total+=venta.getServicio().getTotalAsientos();
				total_pax+=venta.getCantidadPax();
				ingreso_real+= venta.getImporteReal();
				dscto_total+= venta.getImporteDescuentos();
				kilometros+= venta.getRuta().getKilometros();
				if(venta.getImporteEsperado()!=null)
					ingreso_esperado += venta.getImporteEsperado();
				else
					ingreso_esperado += 0;
			}
			total_servicios+=lstVentas3.size();
		}
		
		ocu_porcentaje+= Util.formatearDecimales(((Double.valueOf(total_pax)/Double.valueOf(cap_total))*100),2);
		sol_km_total = Util.formatearDecimales(ingreso_real/kilometros, 2);
		dscto_total = Util.formatearDecimales(dscto_total, 2);
		
		
		
		
		cell = new Listcell(total_servicios.toString());
		cell.setStyle("font-size:12px !important; color:white !important; font-weight:bold; text-align:center; border-bottom-color: white;");
		item.appendChild(cell);
		
		cell = new Listcell(cap_total.toString());
		cell.setStyle("font-size:12px !important;font-weight:bold;color:white;text-align:center; border-bottom-color: white;");
		item.appendChild(cell);
		
		cell = new Listcell(total_pax.toString());
		cell.setStyle("font-size:12px !important;font-weight:bold;color:white;text-align:center; border-bottom-color: white;");
		item.appendChild(cell);
		
		cell = new Listcell(ocu_porcentaje.toString()+"%");
		cell.setStyle("font-size:12px !important;font-weight:bold;color:white;text-align:center; border-bottom-color: white;");
		item.appendChild(cell);
		
		cell = new Listcell(Util.toNumberFormat(ingreso_esperado, 2));
		cell.setStyle("font-size:12px !important;font-weight:bold;color:white;text-align:right; border-bottom-color: white;");
		item.appendChild(cell);
		
		cell = new Listcell(Util.toNumberFormat(ingreso_real, 2));
		cell.setStyle("font-size:12px !important;font-weight:bold;color:white;text-align:right; border-bottom-color: white;");
		item.appendChild(cell);
		
		cell = new Listcell(Util.toNumberFormat(dscto_total, 2));
		cell.setStyle("font-size:12px !important;font-weight:bold;color:white;text-align:right; border-bottom-color: white;");
		item.appendChild(cell);
		
		cell = new Listcell(Util.toNumberFormat((dscto_total/ingreso_esperado)*100, 2)+"%");
		cell.setStyle("font-size:12px !important;font-weight:bold;color:white;text-align:right; border-bottom-color: white;");
		item.appendChild(cell);
		
		cell = new Listcell(sol_km_total.toString());
		cell.setStyle("font-size:12px !important;font-weight:bold;color:white;text-align:right; border-bottom-color: white;");
		item.appendChild(cell);
		
		cell = new Listcell();
		cell.setStyle(style);
		item.appendChild(cell);
		item.appendChild(cell);	
		
		
		item.setStyle("background:#000000");		
		lbxAvance.appendChild(item);
		
	}
	
	public  void enlazarVerMapa(String idItinerario, String idRuta, Integer ocupados) throws Exception {
		List<VentaPasaje> lisOpabilidad = ServiceLocator.getManifiestoManager().consultaDetaPaxXRuta(Long.valueOf(idItinerario));
		
		final WndVerMapaAvance oWndSeleccionaItinerario = new WndVerMapaAvance();
		oWndSeleccionaItinerario.setIdItinerario(new Long (idItinerario));
		oWndSeleccionaItinerario.setCantOcupados(ocupados);
		oWndSeleccionaItinerario.setListOcupabilidad(lisOpabilidad);		
		oWndSeleccionaItinerario.setIdRutaDetiti(Integer.parseInt(idRuta));     
		
		Boolean esVisibleInfoBus, esVisibleOcupabilidad, esVisibleLeyenda, esVisibleButtons;
		esVisibleInfoBus=true;
		esVisibleOcupabilidad=true;
		esVisibleLeyenda=true;
		esVisibleButtons=true;
		oWndSeleccionaItinerario.load(esVisibleInfoBus, esVisibleOcupabilidad, esVisibleLeyenda, esVisibleButtons);
		
		this.appendChild(oWndSeleccionaItinerario);
		oWndSeleccionaItinerario.setMode("modal");
	}
}

