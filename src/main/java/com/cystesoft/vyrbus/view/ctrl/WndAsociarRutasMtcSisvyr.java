/**
 * Proyecto		: SISVYR
 * Sistema		: Sistema de Ventas y Reservas
 * Descripción	: 
 * Autor		: José Abanto
 * Fecha		: 26/08/2014
 * Hora			: 10:05:30
 */
package com.cystesoft.vyrbus.view.ctrl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

import org.zkoss.zk.ui.Executions;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Comboitem;
import org.zkoss.zul.Image;
import org.zkoss.zul.Label;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;

import com.cystesoft.vyrbus.model.bean.MTCDetalleRuta;
import com.cystesoft.vyrbus.model.bean.MTCRuta;
import com.cystesoft.vyrbus.model.bean.Ruta;
import com.cystesoft.vyrbus.service.locator.ServiceLocator;
import com.cystesoft.vyrbus.service.util.Constantes;
import com.cystesoft.vyrbus.service.util.Messages;
import com.cystesoft.vyrbus.service.util.Util;
import com.cystesoft.vyrbus.service.util.UtilData;
import com.cystesoft.vyrbus.view.ui.DlgMessage;
import com.cystesoft.vyrbus.view.ui.WndBase;

/**
 * @author JABANTO
 *
 */
public class WndAsociarRutasMtcSisvyr extends WndBase implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private Combobox cmbRutaMtc;
	private Listbox lsbxRutasAsociadasMtc;
	private Label lblTotalRutas;
	private Label lblRutasAsociadas;
	private Listbox lsbxRutas;
	private Image imgAsocia;
	private Image imgDeshasocia;
		
	private List<Ruta>lstRuatas=null;
	private String srcAsociaEnabled="/resources/mp_rightArrowEnabled.png";
	private String srcAsociaDisabled="/resources/mp_rightArrowDisabled.png";
	private String srcDeshasociaEnabled="/resources/mp_leftArrowEnabled.png";
	private String srcDeshasociaDisabled="resources/mp_leftArrowDisabled.png";
	
	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.view.ui.WndBase#initComponents()
	 */
	@Override
	public void initComponents() {
		cmbRutaMtc=(Combobox)this.getFellow("cmbRutaMtc");
		lsbxRutasAsociadasMtc=(Listbox)this.getFellow("lsbxRutasAsociadasMtc");
		lblTotalRutas=(Label)this.getFellow("lblTotalRutas");
		lblRutasAsociadas=(Label)this.getFellow("lblRutasAsociadas");
		lsbxRutas=(Listbox)this.getFellow("lsbxRutas");		
		imgAsocia=(Image)this.getFellow("imgAsocia");
		imgDeshasocia=(Image)this.getFellow("imgDeshasocia");
		
		lsbxRutas.addEventListener(Events.ON_SELECT, new EventListener<Event>() {
			@Override
			public void onEvent(Event arg0) throws Exception {
				if(lsbxRutas.getSelectedItems().size()>0){
					imgAsocia.setSrc(srcAsociaEnabled);
					imgAsocia.setStyle("cursor:pointer");
				}else{
					imgAsocia.setSrc(srcAsociaDisabled);
					imgAsocia.setStyle("cursor:default");
				}
			}
		});
		
		lsbxRutasAsociadasMtc.addEventListener(Events.ON_SELECT, new EventListener<Event>() {
			@Override
			public void onEvent(Event arg0) throws Exception {
				// TODO Auto-generated method stub
				if(lsbxRutasAsociadasMtc.getSelectedItems().size()>0){
					imgDeshasocia.setSrc(srcDeshasociaEnabled);
					imgDeshasocia.setStyle("cursor:pointer");
				}else{
					imgDeshasocia.setSrc(srcDeshasociaDisabled);
					imgDeshasocia.setStyle("cursor:default");
				}
			}
		});
		
		
		imgAsocia.addEventListener(Events.ON_CLICK,new EventListener<Event>() {
			@Override
			public void onEvent(Event arg0) throws Exception {
				// TODO Auto-generated method stub
				if(imgAsocia.getSrc().equals(srcAsociaEnabled)){
					asociar();
				}
			}
			
		});
		
		imgDeshasocia.addEventListener(Events.ON_CLICK,new EventListener<Event>() {
			@Override
			public void onEvent(Event arg0) throws Exception {
				// TODO Auto-generated method stub
				if(imgDeshasocia.getSrc().equals(srcDeshasociaEnabled)){
					deshasociar();
				}
			}
		});
	}
	
	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.view.ui.WndBase#onCreate()
	 */
	@Override
	public void onCreate() throws Exception {
		cargarRutasMTC();
		cmbRutaMtc.setSelectedIndex(0);
		TreeMap<String, Object>criterisoBusqueda=new TreeMap<String, Object>();
		criterisoBusqueda.put("estadoRegistro", Constantes.VALUE_ACTIVO);
		List<String>criteriosOrden=new ArrayList<String>();
		criteriosOrden.add("origen");
		criteriosOrden.add("destino");
		lstRuatas=ServiceLocator.getRutaManager().buscarPorX(criterisoBusqueda, criteriosOrden);
		cargarRutas();
		
	}
		
	/**
	 * 
	 */
	private void cargarRutas() throws Exception{
		//Cargamos las rutas del sisvyr
		Util.limpiarListbox(lsbxRutas);
		List<MTCDetalleRuta>lstMtcDetalleRutas=ServiceLocator.getMTCDetalleRutaManager().buscarPorEstado(Constantes.VALUE_ACTIVO);
		for(Ruta ruta: lstRuatas){
			
			//Valida si la ruta ya existe en la lista de rutas asociadas con el MTC
			Boolean esxisteRuta=false;
			for(MTCDetalleRuta mtcDetalleRuta : lstMtcDetalleRutas){
				Ruta oRuta=mtcDetalleRuta.getRuta();
				if(ruta.getId().intValue()==oRuta.getId().intValue()){
					esxisteRuta=true;
					break;
				}
			}
			if(esxisteRuta==false){
				for(Listitem item : lsbxRutasAsociadasMtc.getItems()){
					Ruta oRuta=((MTCDetalleRuta)item.getValue()).getRuta();
					if(ruta.getId().intValue()==oRuta.getId().intValue()){
						esxisteRuta=true;
						break;
					}
				}
			}
			
			//Solo agrega si no esta. 
			if(esxisteRuta==false){
				Listitem item=new Listitem();
				Listcell cell=new Listcell("");
				item.appendChild(cell);
				cell=new Listcell(ruta.toString());
				item.appendChild(cell);
				
				item.setValue(ruta);
				lsbxRutas.appendChild(item);
			}
		}
		lblTotalRutas.setValue("Total Rutas : "+lsbxRutas.getItems().size());
	}
	
	/**
	 * @throws Exception 
	 * 
	 */
	private void asociar() throws Exception{
		for(Listitem item :lsbxRutas.getSelectedItems()){
			Ruta ruta=(Ruta)item.getValue();
			
			Listitem itemAsocia=new Listitem();
			Listcell cell=new Listcell("");
			itemAsocia.appendChild(cell);
			
			cell=new Listcell(ruta.toString());
			itemAsocia.appendChild(cell);
			
			
			MTCDetalleRuta mtcDetalleRuta=new MTCDetalleRuta();
			mtcDetalleRuta.setRuta(ruta);
			mtcDetalleRuta.setMtcRuta((MTCRuta)cmbRutaMtc.getSelectedItem().getValue());
						
			itemAsocia.setValue(mtcDetalleRuta);
			lsbxRutasAsociadasMtc.appendChild(itemAsocia);	
		}
		imgAsocia.setSrc(srcAsociaDisabled);
		lblRutasAsociadas.setValue("Rutas Asociadas : "+lsbxRutasAsociadasMtc.getItems().size());
		
		cargarRutas();
	}
	/**
	 * @throws Exception 
	 * 
	 */
	private void deshasociar() throws Exception{
		for(Listitem item :lsbxRutasAsociadasMtc.getSelectedItems()){
			lsbxRutasAsociadasMtc.removeItemFromSelection(item);
		}
		cargarRutas();
		imgDeshasocia.setSrc(srcDeshasociaDisabled);
		lblRutasAsociadas.setValue("Rutas Asociadas : "+lsbxRutasAsociadasMtc.getItems().size());
	}
	
	private void cargarRutasMTC() throws Exception{
		Util.limpiarListbox(lsbxRutasAsociadasMtc);
		List<MTCRuta>lstRutasMtc=ServiceLocator.getMTCRutaManager().buscarPorEstado(Constantes.VALUE_ACTIVO);
		UtilData.cargarGenericData(cmbRutaMtc, false);
		for(MTCRuta mtcRuta: lstRutasMtc){
			Comboitem comboitem=new Comboitem();
			comboitem.setLabel(mtcRuta.toString());
			comboitem.setValue(mtcRuta);
			
			cmbRutaMtc.appendChild(comboitem);
		}
	}
	
	public void cargarRutasAsociadasAlMTC() throws Exception{
		//Luego seleccionamos las ruta asociadas
		Util.limpiarListbox(lsbxRutasAsociadasMtc);
		lblRutasAsociadas.setValue("");
		List<MTCDetalleRuta> lstDetRuta=new ArrayList<MTCDetalleRuta>();
		if(cmbRutaMtc.getSelectedItem().getValue() instanceof MTCRuta){
			MTCRuta mtcRuta=cmbRutaMtc.getSelectedItem().getValue();
			lstDetRuta=ServiceLocator.getMTCDetalleRutaManager().buscarPorIdRutaMtc(mtcRuta.getId());
				for(MTCDetalleRuta oMtcDetalleRuta: lstDetRuta){
					Listitem item=new Listitem();
					Listcell cell=new Listcell("");
					item.appendChild(cell);
					
					cell=new Listcell(oMtcDetalleRuta.getRuta().toString());
					item.appendChild(cell);
					item.setValue(oMtcDetalleRuta);
					lsbxRutasAsociadasMtc.appendChild(item);
				}
		}
		lblRutasAsociadas.setValue("Rutas Asociadas : "+String.valueOf(lstDetRuta.size()));
		cargarRutas();
	}
	
	public void guardar() throws Exception{
		try {
			if(cmbRutaMtc.getSelectedItem().getValue() instanceof MTCRuta){
				MTCRuta mtcRuta=cmbRutaMtc.getSelectedItem().getValue();
				List<MTCDetalleRuta> lstDetRuta=ServiceLocator.getMTCDetalleRutaManager().buscarPorIdRutaMtc(mtcRuta.getId());
								
				for(Listitem item: lsbxRutasAsociadasMtc.getItems()){
					MTCDetalleRuta mtcDetalleRuta=(MTCDetalleRuta)item.getValue();
					Ruta ruta=mtcDetalleRuta.getRuta();
						//Valida si se trata de una nueva asociacion
						Boolean savedDetalleRuta=true;
						for(MTCDetalleRuta oMtcDetalleRuta: lstDetRuta){
							if(ruta.getId().intValue()==oMtcDetalleRuta.getRuta().getId().intValue()){
								savedDetalleRuta=false;
								break;
							}
						}
						//Asocia la rutas.
						if(savedDetalleRuta){
							MTCDetalleRuta omtcDetalleRuta=new MTCDetalleRuta();
							omtcDetalleRuta=mtcDetalleRuta;
							omtcDetalleRuta.setEstadoRegistro(Constantes.VALUE_ACTIVO);
							UtilData.auditarRegistro(omtcDetalleRuta, getUsuario(), Executions.getCurrent());
							ServiceLocator.getMTCDetalleRutaManager().guardar(omtcDetalleRuta);
						}		
				}
				
				//Busca rutas deshasociadas
				for(Listitem item:lsbxRutas.getItems()){
					Ruta ruta=(Ruta)item.getValue();
					
					//Valida si alguna ruta a sido inactivada del detalle de rutasMTC
					for(MTCDetalleRuta oMtcDetalleRuta: lstDetRuta){
						if(ruta.getId().intValue()==oMtcDetalleRuta.getRuta().getId().intValue()){
							anularRutaDetaller(oMtcDetalleRuta);
							break;
						}
					}
				}
				
				DlgMessage.information(Messages.getString("Generales.information.exitoGuardar"));
			}else{
				DlgMessage.information(Messages.getString("WndAsociaRutasMtcSisvyr.information.noRutaMtc"));
			}
		} catch (Exception e) {
			DlgMessage.error(e.getMessage());
		}
	}
	
	private void anularRutaDetaller(MTCDetalleRuta mtcDetalleRuta)throws Exception{
		mtcDetalleRuta.setEstadoRegistro(Constantes.VALUE_INACTIVO);
		UtilData.auditarRegistro(mtcDetalleRuta,true, getUsuario(), Executions.getCurrent());
		ServiceLocator.getMTCDetalleRutaManager().actualizar(mtcDetalleRuta);
	}

}
