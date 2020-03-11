/**
 * Proyecto		: SISVYR
 * Sistema		: Sistema de Ventas y Reservas
 * Descripción	: 
 * Autor		: José Abanto
 * Fecha		: 14/10/2015
 * Hora			: 14:38:30
 */
package com.cystesoft.vyrbus.view.ctrl;

import java.util.Date;
import java.util.List;
import java.util.TreeMap;
import java.util.Map.Entry;

import org.zkoss.zul.Combobox;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listhead;
import org.zkoss.zul.Listheader;
import org.zkoss.zul.Listitem;

import com.cystesoft.vyrbus.model.bean.Bus;
import com.cystesoft.vyrbus.model.bean.Localidad;
import com.cystesoft.vyrbus.model.bean.Servicio;
import com.cystesoft.vyrbus.model.bean.report.RptAvanceBus;
import com.cystesoft.vyrbus.model.bean.report.RptAvanceBuses;
import com.cystesoft.vyrbus.service.locator.ServiceLocator;
import com.cystesoft.vyrbus.service.util.Constantes;
import com.cystesoft.vyrbus.service.util.UtilData;
import com.cystesoft.vyrbus.view.ui.DlgMessage;
import com.cystesoft.vyrbus.view.ui.WndBase;

/**
 * @author jabanto
 *
 */
public class WndAvanceBuses extends WndBase{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Datebox dtbxFechaInicio;
	private Datebox dtbxFechafin;
	private Combobox cmbLocalidadOrigen;
	private Combobox cmbLocalidadDestino;
	private Combobox cmbServicio;
	private Combobox cmbBus;
	
	
	private Listbox lbxAvance = new Listbox();
	
	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.view.ui.WndBase#initComponents()
	 */
	@Override
	public void initComponents() {
		dtbxFechaInicio=(Datebox) this.getFellow("dtbxFechaInicio");
		dtbxFechafin=(Datebox) this.getFellow("dtbxFechafin");
		cmbLocalidadOrigen=(Combobox) this.getFellow("cmbLocalidadOrigen");
		cmbLocalidadDestino=(Combobox) this.getFellow("cmbLocalidadDestino");
		cmbServicio=(Combobox) this.getFellow("cmbServicio");
		cmbBus=(Combobox) this.getFellow("cmbBus");
	}
	
	
	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.view.ui.WndBase#onCreate()
	 */
	@Override
	public void onCreate() throws Exception {
		dtbxFechaInicio.setValue(new Date());
		dtbxFechafin.setValue(new Date());
		
		UtilData.cargarDataCombo(cmbLocalidadOrigen, Localidad.class, true);
		UtilData.cargarDataCombo(cmbLocalidadDestino, Localidad.class, true);
		UtilData.cargarDataCombo(cmbServicio, Servicio.class, true);
		UtilData.cargarDataCombo(cmbBus, Bus.class, true);
	}
	
	
	public void buscar()throws Exception{
		try {
			lbxAvance.getItems().clear();
			
			String fechaInicio=dtbxFechaInicio.getText().trim();
			String fechaFin=dtbxFechafin.getText().trim();
			Integer idLocalidadOrigen=null;
			Integer idLocalidadDestino=null;
			Integer idServicio=null;
			Integer codigoBus=null;
			
			if(cmbLocalidadOrigen.getSelectedItem().getValue() instanceof Localidad)
				idLocalidadOrigen=((Localidad)cmbLocalidadOrigen.getSelectedItem().getValue()).getId();
			if(cmbLocalidadDestino.getSelectedItem().getValue() instanceof Localidad)
				idLocalidadDestino=((Localidad)cmbLocalidadDestino.getSelectedItem().getValue()).getId();
			if(cmbServicio.getSelectedItem().getValue() instanceof Servicio)
				idServicio=((Servicio)cmbServicio.getSelectedItem().getValue()).getId();
			if(cmbBus.getSelectedItem().getValue() instanceof Bus)
				codigoBus=Integer.valueOf(((Bus)cmbBus.getSelectedItem().getValue()).getCodigo());
			
			lbxAvance.detach();
			lbxAvance = new Listbox();
			lbxAvance.setHeight("480px");
			
			RptAvanceBuses avanceBuses=ServiceLocator.getReportesManager().avancesBuses(fechaInicio, fechaFin, idLocalidadOrigen, idLocalidadDestino, idServicio, codigoBus);			
			crearEncabezado(avanceBuses.getEncabezado());
			mostrarInfo(avanceBuses.getMapAvanceBus());
			
			
		} catch (Exception e) {
			e.printStackTrace();
			DlgMessage.error(e.getMessage());
		}
	}
	
	/**
	 * Crea el encabezado para el avance de buses
	 * @param lstEncabezado : Lista que contiene las fechas para armar el encabezado.
	 * @throws Exception
	 */
	private Listhead crearEncabezado(List<String> lstEncabezado)throws Exception{
		Listhead listhead=new Listhead();
		listhead.appendChild(new Listheader("ITEM","","30px"));
		listhead.appendChild(new Listheader("HORA","","45px"));
		listhead.appendChild(new Listheader("SERVICIO","","130px"));
		listhead.appendChild(new Listheader("RUTA","","180px"));
		
		for(String fecha:lstEncabezado){
			Listheader listheader=new Listheader(fecha,"","55px");
			listheader.setAlign("center");
			listhead.appendChild(listheader);
		}
		lbxAvance.appendChild(listhead);
		
		return listhead;
	}
	
	/**
	 * Carga la informacion del avance por buses
	 * @param mapAvanceBus : objeto trremap el cual contiene la info para cargar en avance
	 * @throws Exception
	 */
	private void mostrarInfo(TreeMap<String, RptAvanceBus> mapAvanceBus)throws Exception{
		
		Listitem item=null;
		Listcell cell=null;
		String LIMA_PROVINCIA="LIMA PROVINCIAS";
		String PROVINCIAS_LIMA="PROVINCIAS LIMA";
		String PROVINCIAS="PROVINCIAS";
		
		boolean provinciasLima=false,provincias=false;
		int recountItem=0,index=-1;
		String key="";
		
		TreeMap<String, String>itemsAdd=new TreeMap<>();
		int tipoRutaAnt=-1;
		for(Entry<?,?> e : mapAvanceBus.entrySet()) {
			index++;
			RptAvanceBus avanceBus=(RptAvanceBus) e.getValue();
			
			/*Para separar los grupos "Provincias lima" y "Provincias"*/
			if(index>0 && avanceBus.getTipoRuta().intValue()!=tipoRutaAnt && avanceBus.getTipoRuta().intValue()>0 && (provinciasLima==false || provincias==false)){
				if(tipoRutaAnt==0 &&  provinciasLima==false){
					/*Agrega un nuevo item para el total de lima provincias*/
					addNewItemTotal(LIMA_PROVINCIA, itemsAdd, key);
					provinciasLima=true;
				}else if(tipoRutaAnt==1 && provincias==false){
					/*Agrega un nuevo item para el total de provincias lima*/
					addNewItemTotal(PROVINCIAS_LIMA, itemsAdd, key);
					provincias=true;
				}
			}
			
			key=e.getKey().toString().replace(avanceBus.getFechaPartida(), "");
			/*Agrega los servicios*/
			if(itemsAdd.get(key) == null){
				recountItem++;
				item=new Listitem();
				cell=new Listcell(String.valueOf(recountItem));
				cell.setStyle("font-size:11px !important");
				item.appendChild(cell);
				cell=new Listcell(avanceBus.getHoraPartida());
				cell.setStyle("font-size:11px !important");
				item.appendChild(cell);
				cell=new Listcell(avanceBus.getServicio());
				item.appendChild(cell);
				cell=new Listcell(avanceBus.getRuta());
				item.appendChild(cell);
				lbxAvance.appendChild(item);
				
				itemsAdd.put(key,key);
			}
			tipoRutaAnt=avanceBus.getTipoRuta();
		}
		
		//Determina el nombre del total del utimo item
		if(cmbLocalidadOrigen.getSelectedItem().getValue() instanceof Localidad){
			if(((Localidad)cmbLocalidadOrigen.getSelectedItem().getValue()).getId().intValue()==Constantes.ID_LOC_LIMA){
				/*Agrega un nuevo item para el total de lima provincias*/
				addNewItemTotal(LIMA_PROVINCIA, itemsAdd, key);
			}else if (cmbLocalidadDestino.getSelectedItem().getValue() instanceof Localidad && ((Localidad)cmbLocalidadDestino.getSelectedItem().getValue()).getId().intValue()==Constantes.ID_LOC_LIMA){
				/*Agrega un nuevo item para el total de lima provincias*/
				addNewItemTotal(PROVINCIAS_LIMA, itemsAdd, key);				
			}else{
				/*Agrega un nuevo item para el total de provincias*/
				addNewItemTotal(PROVINCIAS, itemsAdd, key);
			}
		}else if (cmbLocalidadDestino.getSelectedItem().getValue() instanceof Localidad){
			if(((Localidad)cmbLocalidadDestino.getSelectedItem().getValue()).getId().intValue()==Constantes.ID_LOC_LIMA){
				/*Agrega un nuevo item para el total de lima provincias*/
				addNewItemTotal(PROVINCIAS_LIMA, itemsAdd, key);
			}else{
				/*Agrega un nuevo item para el total de provincias*/
				addNewItemTotal(PROVINCIAS, itemsAdd, key);
			}
		}else{
			/*Agrega un nuevo item para el total de provincias*/
			addNewItemTotal(PROVINCIAS, itemsAdd, key);	
		}
		
				
		
		/*Agrega el bus que le corresponde a cada servicios, segun la fecha que haya sido programado.*/
		index=-1;
		for(Entry<?,?> e : itemsAdd.entrySet()) {
			index++;
			for(int i=4;i<lbxAvance.getListhead().getChildren().size();i++){
				Listheader listheader=(Listheader) lbxAvance.getListhead().getChildren().get(i);
				/*Validando que no sea el item que separa el tipo de ruta (Provincias Lima y Provincias)*/
				if(!(e.getValue().equals(LIMA_PROVINCIA) && e.getValue().equals(PROVINCIAS_LIMA))){
					key=e.getKey()+listheader.getLabel();
					RptAvanceBus avanceBus=mapAvanceBus.get(key);
					
					if(avanceBus!=null){
						cell=new Listcell(avanceBus.getBus()!=null?avanceBus.getBus().toString():"");
						cell.setStyle("font-size:11px !important");
						lbxAvance.getItemAtIndex(index).appendChild(cell);
					}else
						lbxAvance.getItemAtIndex(index).appendChild(new Listcell());
				}else
					lbxAvance.getItemAtIndex(index).appendChild(new Listcell());
			}
		}
		
		
		/*Suma el total por tipo de ruta*/
		int count=0;
		for(int i=4;i<lbxAvance.getListhead().getChildren().size();i++){
			for(Listitem itemT:lbxAvance.getItems()){
				cell =(Listcell) itemT.getChildren().get(i);
				if(!(cell.getLabel().isEmpty()))
					count++;
				
				if(((Listcell) itemT.getChildren().get(2)).getLabel().equals("TOTAL")){
					cell.setStyle("font-size:12px !important;font-weight:bold;color:white;text-align: center;");
					cell.setLabel(String.valueOf(count));
					count=0;
				}
			}
		}
		
		
		
		
		this.appendChild(lbxAvance);
	}
	
	
	private void addNewItemTotal(String nameTipoRuta, TreeMap<String, String>itemsAdd, String key)throws Exception{
		String styleBackground1="background:#263C76";
		Listitem item=new Listitem();
		item.appendChild(new Listcell());
		item.appendChild(new Listcell());
		Listcell cell=new Listcell("TOTAL");
		cell.setStyle("font-size:12px !important;font-weight:bold;color:white;text-align: right;");
		item.appendChild(cell);
		cell=new Listcell(nameTipoRuta);
		cell.setStyle("font-size:12px !important;font-weight:bold;color:white");
		item.appendChild(cell);
		item.setStyle(styleBackground1);
		lbxAvance.appendChild(item);
		itemsAdd.put(key+nameTipoRuta,nameTipoRuta);
	}
	
}
