/**
 * Proyecto		: SISVYR
 * Sistema		: Sistema de Ventas y Reservas
 * Descripción	: 
 * Autor		: José Abanto
 * Fecha		: 11 abr. 2022
 * Hora			: 14:56:10
 */
package com.cystesoft.vyrbus.view.ctrl;

import java.util.Date;
import java.util.List;
import java.util.TreeMap;

import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.Hbox;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Toolbarbutton;

import com.cystesoft.vyrbus.model.bean.Agencia;
import com.cystesoft.vyrbus.model.bean.Liquidacion;
import com.cystesoft.vyrbus.service.locator.ServiceLocator;
import com.cystesoft.vyrbus.service.util.Constantes;
import com.cystesoft.vyrbus.service.util.CreateDocument;
import com.cystesoft.vyrbus.service.util.Messages;
import com.cystesoft.vyrbus.service.util.Util;
import com.cystesoft.vyrbus.service.util.UtilData;
import com.cystesoft.vyrbus.view.ui.DlgMessage;
import com.cystesoft.vyrbus.view.ui.Events;
import com.cystesoft.vyrbus.view.ui.WndBase;
import com.cystesoft.vyrbus.view.ui.WndIFrame;

/**
 * @author abant
 *
 */
public class WndRptLiquidacionVentas extends WndBase{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Datebox dtbxFechaInicio;
//	private Datebox dtbxFechaFin;
	private Combobox cmbAgencia;
//	private Combobox cmbUsuario;
	private Listbox ltbxLiquidacionVentas;
//	private Button btnDetalleVentas;
//	private Button btnDetalleGastos;
//	private Button btnResumenSaldos;
	
	/* (non-Javadoc)
	 * @see com.cystesoft.vyrbus.view.ui.WndBase#initComponents()
	 */
	@Override
	public void initComponents() {
		// TODO Auto-generated method stub
		super.initComponents();
		
		dtbxFechaInicio = (Datebox) this.getFellow("dtxFechaInicio");
//		dtbxFechaFin = (Datebox) this.getFellow("dtbxFechaFin");
		cmbAgencia = (Combobox) this.getFellow("cmbAgencia");
//		cmbUsuario = (Combobox) this.getFellow("cmbUsuario");
		ltbxLiquidacionVentas = (Listbox) this.getFellow("ltbxLiquidacionVentas");
//		btnDetalleVentas = (Button) this.getFellow("btnDetalleVentas");
//		btnDetalleGastos = (Button) this.getFellow("btnDetalleGastos");
//		btnResumenSaldos = (Button) this.getFellow("btnResumenSaldos");
		
	}
	
	
	/* (non-Javadoc)
	 * @see com.cystesoft.vyrbus.view.ui.WndBase#onCreate()
	 */
	@Override
	public void onCreate() throws Exception {
		// TODO Auto-generated method stub
		super.onCreate();
		
		dtbxFechaInicio.setValue(new Date());
//		dtbxFechaFin.setValue(new Date());
		
//		btnResumenSaldos.setDisabled(true);
//		btnDetalleVentas.setDisabled(true);
//		btnDetalleGastos.setDisabled(true);
		
		UtilData.cargarDataCombo(cmbAgencia, Agencia.class, false);
	}		
	
	/**
	 * Evento Click del botón buscar
	 */
	public void onClick_btnBuscar() {
		try {
			Util.limpiarListbox(ltbxLiquidacionVentas);
//			btnDetalleVentas.setDisabled(true);
//			btnDetalleGastos.setDisabled(true);
//			btnResumenSaldos.setDisabled(true);
			
			if(!(cmbAgencia.getSelectedItem().getValue() instanceof Agencia)) {
				DlgMessage.information(Messages.getString("Generales.information.noSeleccionoAgencia"),cmbAgencia);
				return;
			}
			
			String fechaInicial = Constantes.FORMAT_DATE.format(dtbxFechaInicio.getValue());
			Integer agenciaId = ((Agencia)cmbAgencia.getSelectedItem().getValue()).getId();
			
			//Busca las liquidaciones de PASAJES.
			List<Liquidacion> resultPasajes = ServiceLocator.getLiquidacionManager().buscarLiquidacion(fechaInicial, fechaInicial, agenciaId, null, null);
			
			//Busca las liquidaciones de CARGA
			TreeMap<String, Liquidacion> resultCarga = ServiceLocator.getTranscarManager().buscarLiquidacionCounter(fechaInicial, fechaInicial, agenciaId, null); 
			
			//Carga el listado de liquidaciones
			cargarListaLiquidaciones(resultPasajes, resultCarga);
			
			//Valida lista de resultados par activar los controles de reportes
//			if(ltbxLiquidacionVentas.getSelectedItems().size()>0) {
//				btnDetalleVentas.setDisabled(false);
//				btnDetalleGastos.setDisabled(false);
//				btnResumenSaldos.setDisabled(false);
//			}
						
		} catch (Exception e) {
			e.printStackTrace();
			DlgMessage.error(e.getMessage());
		}
	}
	
	/**
	 * Evento Click del botón Detalle ventas
	 */
	public void viewDetalleVentas(Liquidacion liquidacion) {
		try {
			
			String nameFile = CreateDocument.creaRptLiquidacionByDetalleVentas(liquidacion);
			/*Carga el iframe*/
			String src=Constantes.URL_FORMATOS_LIQUIDACION +Constantes.CLAVE_PAHT+ nameFile;
			final WndIFrame iFrame = new WndIFrame();
			iFrame.setSrc(src);
			iFrame.setWidth("810");
			iFrame.setheight("600");
			iFrame.loadiframe();
			
			appendChild(iFrame);
			iFrame.setMode("modal");
			
		} catch (Exception e) {
			e.printStackTrace();
			DlgMessage.error(e.getMessage());
		}
	}
	
	/**
	 * Carga la vista preliminar del reporte Control Especies Valoradas
	 * @param liquidacionPasajes
	 * @param liquidacionesCarga
	 */
	private void viewCrtolEspeciesValoradas(Liquidacion liquidacion) {
try {
			
			String nameFile = CreateDocument.creaRptLiquidacionByEspecieValorada(liquidacion);
			/*Carga el iframe*/
			String src=Constantes.URL_FORMATOS_LIQUIDACION +Constantes.CLAVE_PAHT+ nameFile;
			final WndIFrame iFrame = new WndIFrame();
			iFrame.setSrc(src);
			iFrame.setWidth("810");
			iFrame.setheight("600");
			iFrame.loadiframe();
			
			appendChild(iFrame);
			iFrame.setMode("modal");
			
		} catch (Exception e) {
			e.printStackTrace();
			DlgMessage.error(e.getMessage());
		}		
	}
	
	/**
	 * Evento Click del botón Resumen de Saldos
	 */
	public void onClick_btnResumenSaldos() {
		try {
			if (!(cmbAgencia.getSelectedItem().getValue() instanceof Agencia)) {
				DlgMessage.information(Messages.getString("Generales.information.noSeleccionoAgencia"),cmbAgencia);
				return;
			}else if(ltbxLiquidacionVentas.getItemCount()>0) {
				Agencia agencia = (Agencia) cmbAgencia.getSelectedItem().getValue();
				String fechaLiquidacion = Constantes.FORMAT_DATE.format(dtbxFechaInicio.getValue());
				
				String nameFile = CreateDocument.creaRptLiquidacionByResumenSaldos(ltbxLiquidacionVentas, agencia, fechaLiquidacion);
				/*Carga el iframe*/
				String src=Constantes.URL_FORMATOS_LIQUIDACION +Constantes.CLAVE_PAHT+ nameFile;
				final WndIFrame iFrame = new WndIFrame();
				iFrame.setSrc(src);
				iFrame.setWidth("810");
				iFrame.setheight("600");
				iFrame.loadiframe();
				
				appendChild(iFrame);
				iFrame.setMode("modal");				
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			DlgMessage.error(e.getMessage());
		}
	}
	
	/**
	 * Carga el listado de liquidaciones
	 * @param liquidaciones : Listado de liquidaciones a cargar
	 * @throws Exception
	 */
	private void cargarListaLiquidaciones(List<Liquidacion> liquidaciones, TreeMap<String, Liquidacion> liquidacionesCarga)throws Exception{
		
		for(Liquidacion liquidacion: liquidaciones) {
			Listitem item = new Listitem();
			Listcell cell =new Listcell(Constantes.FORMAT_DATE.format(liquidacion.getFechaLiquidacion()));
			cell.setStyle("font-size:12px !important");
			item.appendChild(cell);			
			cell =new Listcell(liquidacion.getAgencia().toString());
			item.appendChild(cell);
			cell =new Listcell(liquidacion.getUsuario().toString());
			item.appendChild(cell);
			cell =new Listcell(liquidacion.getUsuario().getLogin());			
			item.appendChild(cell);
			cell = new Listcell();
			if (liquidacion.getestadoLiquidacion().equals(Constantes.LIQUI_ESTA_CERRADO)) {
				cell = new Listcell(Constantes.LIQUI_ESTA_CERRADO_LABEL +" - "+ Constantes.FORMAT_LONG.format(liquidacion.getFechaModificacion()));
				cell.setStyle("font-size:11px !important");
			}else
				cell = new Listcell(Constantes.LIQUI_ESTA_ABIERTO_LABEL);
			item.appendChild(cell);
						
			String key = Constantes.FORMAT_DATE.format(liquidacion.getFechaLiquidacion());
			key += liquidacion.getAgencia().getId().toString();
			key += liquidacion.getUsuario().getLogin();
			Liquidacion liquidacionCarga = liquidacionesCarga.get(key);			
			//Estado liquidacion - Carga
			cell = new Listcell();
			if(liquidacionCarga!=null) {
				if (!(liquidacionCarga.getestadoLiquidacion().equals(Constantes.LIQUI_ESTA_CERRADO))) {
					cell = new Listcell(Constantes.LIQUI_ESTA_CERRADO_LABEL +" - "+ Constantes.FORMAT_LONG.format(liquidacion.getFechaModificacion()));
					cell.setStyle("font-size:11px !important");
				}else
					cell = new Listcell(Constantes.LIQUI_ESTA_ABIERTO_LABEL);
			}else {
				cell = new Listcell("-------------");
				cell.setTooltiptext("No se encontró liquidación de Carga");
			}
			
			item.appendChild(cell);
									
			cell = new Listcell();
			Hbox hbox=new Hbox();
			hbox.setAlign("center");			
			/*Detalle Ventas*/
			Toolbarbutton toolbarbutton =new Toolbarbutton();
			toolbarbutton.setAttribute(Liquidacion.class.getName(), liquidacion);
			toolbarbutton.setImage("/resources/mp_preliminar.png");
			toolbarbutton.setAutodisable("self");
			toolbarbutton.setTooltiptext("Detalle Ventas");
			toolbarbutton.addEventListener(Events.ON_CLICK, new EventListener<Event>() {
				@Override
				public void onEvent(Event event) throws Exception {
					// TODO Auto-generated method stub
					Liquidacion _liquidacion = (Liquidacion)event.getTarget().getAttribute(Liquidacion.class.getName());
					viewDetalleVentas(_liquidacion);
				}
			});
			hbox.appendChild(toolbarbutton);
			
			/*Control especies valoradas*/
			toolbarbutton =new Toolbarbutton();
			toolbarbutton.setAttribute(Liquidacion.class.getName(), liquidacion);
			toolbarbutton.setImage("/resources/mp_preliminar.png");
			toolbarbutton.setAutodisable("self");
			toolbarbutton.setTooltiptext("Control Especies Valoradas");
			toolbarbutton.addEventListener(Events.ON_CLICK, new EventListener<Event>() {
				@Override
				public void onEvent(Event event) throws Exception {
					// TODO Auto-generated method stub
					Liquidacion _liquidacion = (Liquidacion)event.getTarget().getAttribute(Liquidacion.class.getName());
					viewCrtolEspeciesValoradas(_liquidacion);
				}
			});
			hbox.appendChild(toolbarbutton);
							
			cell.appendChild(hbox);
			item.appendChild(cell);
			
			liquidacion.setLiquidacionCarga(liquidacionCarga);
			item.setValue(liquidacion);
			ltbxLiquidacionVentas.appendChild(item);
		}
	}
}
