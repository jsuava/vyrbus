/**
 * Proyecto		: SISVYR
 * Sistema		: Sistema de Ventas y Reservas
 * Descripción	: 
 * Autor		: José Abanto
 * Fecha		: 29 ago. 2021
 * Hora			: 20:59:41
 */
package com.cystesoft.vyrbus.view.ctrl;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Session;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.Doublebox;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Radio;

import com.cystesoft.vyrbus.model.bean.Agencia;
import com.cystesoft.vyrbus.model.bean.Gasto;
import com.cystesoft.vyrbus.model.bean.Usuario;
import com.cystesoft.vyrbus.service.locator.ServiceLocator;
import com.cystesoft.vyrbus.service.util.Constantes;
import com.cystesoft.vyrbus.service.util.Util;
import com.cystesoft.vyrbus.view.ui.WndBase;

/**
 * @author abant
 *
 */
public class WndRptGastosIngresos extends WndBase {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Datebox dtbxFechaDesde;
	private Datebox dtbxFechaHasta;
	private Radio rdGastos;
	private Radio rdIngresos;
	private Listbox ltbxAgencias;
	private Listbox ltbxUsuarios;
	private Listbox ltbxDetalleGasto;
	
	private List<Gasto>resultBusqueda;
	
	/* (non-Javadoc)
	 * @see com.cystesoft.vyrbus.view.ui.WndBase#onCreate()
	 */
	@Override
	public void onCreate() throws Exception {
		// TODO Auto-generated method stub
		super.onCreate();
		dtbxFechaDesde.setValue(new Date());
		dtbxFechaHasta.setValue(new Date());
		ltbxDetalleGasto.setVisible(false);
	}

	
	/* (non-Javadoc)
	 * @see com.cystesoft.vyrbus.view.ui.WndBase#initComponents()
	 */
	@Override
	public void initComponents() {
		// TODO Auto-generated method stub
		super.initComponents();
		
		dtbxFechaDesde = (Datebox)this.getFellow("dtbxFechaDesde");
		dtbxFechaHasta = (Datebox)this.getFellow("dtbxFechaHasta");
		rdGastos = (Radio)this.getFellow("rdGastos");
		rdIngresos = (Radio)this.getFellow("rdIngresos");
		ltbxAgencias = (Listbox)this.getFellow("ltbxAgencias");
		ltbxUsuarios = (Listbox)this.getFellow("ltbxUsuarios");
		ltbxDetalleGasto = (Listbox)this.getFellow("ltbxDetalleGasto");
		
		rdGastos.addEventListener(Events.ON_CHECK, new EventListener<Event>() {
			@Override
			public void onEvent(Event event) throws Exception {
				Util.limpiarListbox(ltbxAgencias);
				Util.limpiarListbox(ltbxUsuarios);
				Util.limpiarListbox(ltbxDetalleGasto);
				ltbxDetalleGasto.setVisible(false);
			}
		});
		rdIngresos.addEventListener(Events.ON_CHECK, new EventListener<Event>() {
			@Override
			public void onEvent(Event event) throws Exception {
				Util.limpiarListbox(ltbxAgencias);
				Util.limpiarListbox(ltbxUsuarios);
				Util.limpiarListbox(ltbxDetalleGasto);
				ltbxDetalleGasto.setVisible(false);
			}
		});
		ltbxAgencias.addEventListener(Events.ON_SELECT, new EventListener<Event>() {
			@Override
			public void onEvent(Event event) throws Exception {				
				loadResumenByUsuario();
			}
		});
		ltbxUsuarios.addEventListener(Events.ON_DOUBLE_CLICK, new EventListener<Event>() {
			@Override
			public void onEvent(Event event) throws Exception {
				loadDetalleGastos();				
			}
		});
	}	

	public void onSearch() {
		try {
			Util.limpiarListbox(ltbxAgencias);
			Util.limpiarListbox(ltbxUsuarios);
			Util.limpiarListbox(ltbxDetalleGasto);
			ltbxDetalleGasto.setVisible(false);
			
			String fechaInicio = Constantes.FORMAT_DATE.format(dtbxFechaDesde.getValue());
			String fechaFin = Constantes.FORMAT_DATE.format(dtbxFechaHasta.getValue());
			int tipoOperacion = (rdIngresos.isChecked()?Constantes.TRUE_VALUE:Constantes.FALSE_VALUE);
			
			resultBusqueda = ServiceLocator.getGastoManager().buscarGastosIngresos(fechaInicio, fechaFin, tipoOperacion);
			//Crea resumen por agencias
			for(Gasto gasto: resultBusqueda) {
				Listitem item =null;
				for(Listitem _item: ltbxAgencias.getItems()) {
					Agencia agencia= ((Gasto)_item.getValue()).getAgencia();
					if(agencia.getId().intValue()==gasto.getAgencia().getId().intValue()) {
						item = _item;						
						break;
					}			
				}
				if(item==null) {
					item= new Listitem();
					Listcell cell = new Listcell(gasto.getAgencia().getDenominacion());
					item.appendChild(cell);
					Doublebox dbxMonto = new Doublebox(gasto.getMonto());
					dbxMonto.setVisible(false);
					cell = new Listcell(Util.toNumberFormat(dbxMonto.getValue(), 2));
					cell.appendChild(dbxMonto);
					item.appendChild(cell);
					item.setValue(gasto);
					item.setSelected(true);
					ltbxAgencias.appendChild(item);
				}else {
					Listcell cell = (Listcell) item.getChildren().get(1);
					Doublebox dbxMonto = (Doublebox) cell.getChildren().get(0);
					dbxMonto.setValue(dbxMonto.getValue()+gasto.getMonto());
					cell.setLabel(Util.toNumberFormat(dbxMonto.getValue(), 2));
				}									
			}						
			
			//Carga el resumento por usuario
			loadResumenByUsuario();
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Crea el resumen por usuario
	 * @throws Exception
	 */
	private void loadResumenByUsuario()throws Exception{
		Util.limpiarListbox(ltbxUsuarios);
		Util.limpiarListbox(ltbxDetalleGasto);
		ltbxDetalleGasto.setVisible(false);
		
		for(Listitem itemAgencia : ltbxAgencias.getSelectedItems()) {
			if(itemAgencia.getValue()!=null && itemAgencia.getValue() instanceof Gasto) {
				Agencia agencia= ((Gasto)itemAgencia.getValue()).getAgencia();
				
				for(Gasto gasto: resultBusqueda) {
					if(gasto.getAgencia().getId().intValue()==agencia.getId().intValue()) {
						Listitem itemUsuario=null;
						for(Listitem _item: ltbxUsuarios.getItems()) {
							Usuario _usuario= ((Gasto)_item.getValue()).getUsuario();
							if(_usuario.getId().intValue()==gasto.getUsuario().getId().intValue()) {
								itemUsuario = _item;						
								break;
							}			
						}
						if(itemUsuario==null) {
							itemUsuario= new Listitem();
							Listcell cell = new Listcell(gasto.getUsuario().toString());
							itemUsuario.appendChild(cell);
							Doublebox dbxMonto = new Doublebox(gasto.getMonto());
							dbxMonto.setVisible(false);
							cell = new Listcell(Util.toNumberFormat(dbxMonto.getValue(), 2));
							cell.appendChild(dbxMonto);
							itemUsuario.appendChild(cell);
							itemUsuario.setValue(gasto);
							ltbxUsuarios.appendChild(itemUsuario);
						}else {
							Listcell cell = (Listcell) itemUsuario.getChildren().get(1);
							Doublebox dbxMonto = (Doublebox) cell.getChildren().get(0);
							dbxMonto.setValue(dbxMonto.getValue()+gasto.getMonto());
							cell.setLabel(Util.toNumberFormat(dbxMonto.getValue(), 2));
						}						
					}					
				}							
			}			
		}
	}
	
	/**
	 * 
	 */
	private void loadDetalleGastos() {
		Util.limpiarListbox(ltbxDetalleGasto);
		ltbxDetalleGasto.setVisible(true);
		if(ltbxUsuarios.getSelectedItem().getValue()!=null && ltbxUsuarios.getSelectedItem().getValue() instanceof Gasto) {
			Usuario usuario = ((Gasto)ltbxUsuarios.getSelectedItem().getValue()).getUsuario();
			for(Gasto gasto: resultBusqueda) {
				if(gasto.getUsuario().getId().intValue()==usuario.getId().intValue()) {
					Listitem item= new Listitem();
					Listcell cell= new Listcell(Constantes.FORMAT_DATE.format(gasto.getFecha()));
					cell.setStyle("font-size:11px !important");
					item.appendChild(cell);
					cell= new Listcell(gasto.getTipoGasto().getDenominacion());
					item.appendChild(cell);
					cell= new Listcell(gasto.getAgencia().getDenominacion());
					item.appendChild(cell);
					cell= new Listcell(gasto.getNumeroDocumento()!=null?gasto.getNumeroDocumento():"");
					item.appendChild(cell);
					cell= new Listcell(gasto.getNombrePiloto()!=null?gasto.getNombrePiloto():"");
					item.appendChild(cell);
					cell= new Listcell(gasto.getCodigoBus()!=null?gasto.getCodigoBus():"");
					item.appendChild(cell);
					cell= new Listcell(gasto.getConsignado()!=null?gasto.getConsignado():"");
					item.appendChild(cell);
					cell= new Listcell(gasto.getObservacion()!=null?gasto.getObservacion():"");
					item.appendChild(cell);
					cell= new Listcell(Util.toNumberFormat(gasto.getMonto(), 2));
					cell.setStyle("font-size:11px !important;text-align:right");
					item.appendChild(cell);
					ltbxDetalleGasto.appendChild(item);
				}	
			}
		}
		
	}
	
	public void btnExportar_OnClick(){
		if(ltbxDetalleGasto.getItemCount()>0) {
			Session session = getDesktop().getSession();
			HttpSession httpSession = (HttpSession)session.getNativeSession();
			httpSession.setAttribute("parcialPath",Constantes.DIRECTORY_EXCEL+"GastosOtrosIngresos.xls");
			httpSession.setAttribute("lbxGastosOtrosIngresos", ltbxDetalleGasto);
			Executions.sendRedirect("/exportXlsGastosOtrosIngresos.htm");	
		}		
	}
}
