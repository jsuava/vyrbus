/**
 * Proyecto		: SISVYR
 * Sistema		: Sistema de Ventas y Reservas
 * Descripción	:
 * Autor		: José Abanto
 * Fecha		: 11 abr. 2022
 * Hora			: 14:56:10
 */
package com.cystesoft.vyrbus.view.ctrl;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.TreeMap;

import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zul.Checkbox;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.Hbox;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listfoot;
import org.zkoss.zul.Listfooter;
import org.zkoss.zul.Listheader;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Toolbarbutton;

import com.cystesoft.vyrbus.model.bean.Agencia;
import com.cystesoft.vyrbus.model.bean.Gasto;
import com.cystesoft.vyrbus.model.bean.Liquidacion;
import com.cystesoft.vyrbus.model.bean.TranscarUsuarioPersonal;
import com.cystesoft.vyrbus.model.bean.VentaPasaje;
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
	private Combobox cmbAgencia;
	private Listbox ltbxLiquidacionVentas;
	private Checkbox chbxPorRangoFechas;
	private Datebox dtbxFechaFin;
	private Checkbox chbxDetallePorUsuario;
	private Listheader lthdrNombresUsuario;
	private Listheader lthdrUsuario;
	private Listheader lthdrEstadoLiqPasajes;
	private Listheader lthdrEstadoLiqCarga;
	
	private String fechaInicial = null;
	private String fechaFinal = null;
	private Integer agenciaId = null;
	
	/* (non-Javadoc)
	 * @see com.cystesoft.vyrbus.view.ui.WndBase#initComponents()
	 */
	@Override
	public void initComponents() {
		// TODO Auto-generated method stub
		super.initComponents();

		dtbxFechaInicio = (Datebox) this.getFellow("dtxFechaInicio");
		dtbxFechaFin = (Datebox) this.getFellow("dtbxFechaFin");
		cmbAgencia = (Combobox) this.getFellow("cmbAgencia");
		ltbxLiquidacionVentas = (Listbox) this.getFellow("ltbxLiquidacionVentas");
		chbxPorRangoFechas = (Checkbox) this.getFellow("chbxPorRangoFechas");
		dtbxFechaFin = (Datebox) this.getFellow("dtbxFechaFin");
		chbxDetallePorUsuario = (Checkbox) this.getFellow("chbxDetallePorUsuario");
		lthdrNombresUsuario = (Listheader) this.getFellow("lthdrNombresUsuario");
		lthdrUsuario = (Listheader) this.getFellow("lthdrUsuario");
		lthdrEstadoLiqPasajes = (Listheader) this.getFellow("lthdrEstadoLiqPasajes");
		lthdrEstadoLiqCarga = (Listheader) this.getFellow("lthdrEstadoLiqCarga");

		chbxPorRangoFechas.addEventListener(org.zkoss.zk.ui.event.Events.ON_CHECK, new EventListener<Event>() {
			@Override
			public void onEvent(Event event) throws Exception {
				// TODO Auto-generated method stub
				Util.limpiarCombobox(cmbAgencia);
				if(chbxPorRangoFechas.isChecked())
					UtilData.cargarDataCombo(cmbAgencia, Agencia.class, false);
				else				
					UtilData.cargarDataCombo(cmbAgencia, Agencia.class, true);				
				dtbxFechaFin.setDisabled(!chbxPorRangoFechas.isChecked());
				cleanResult(true);
			}
		});
		chbxDetallePorUsuario.addEventListener(org.zkoss.zk.ui.event.Events.ON_CHECK, new EventListener<Event>() {
			@Override
			public void onEvent(Event event) throws Exception {
				// TODO Auto-generated method stub
				Util.limpiarListbox(ltbxLiquidacionVentas);
				lthdrNombresUsuario.setVisible(chbxDetallePorUsuario.isChecked());
				lthdrUsuario.setVisible(chbxDetallePorUsuario.isChecked());
				lthdrEstadoLiqPasajes.setVisible(chbxDetallePorUsuario.isChecked());
				lthdrEstadoLiqCarga.setVisible(chbxDetallePorUsuario.isChecked());
			}			
		});
		
	}


	/* (non-Javadoc)
	 * @see com.cystesoft.vyrbus.view.ui.WndBase#onCreate()
	 */
	@Override
	public void onCreate() throws Exception {
		// TODO Auto-generated method stub
		super.onCreate();

		dtbxFechaInicio.setValue(new Date());
		dtbxFechaFin.setValue(new Date());
		UtilData.cargarDataCombo(cmbAgencia, Agencia.class, true);
		
		dtbxFechaFin.setDisabled(true);
		lthdrNombresUsuario.setVisible(false);
		lthdrUsuario.setVisible(false);
		lthdrEstadoLiqPasajes.setVisible(false);
		lthdrEstadoLiqCarga.setVisible(false);
	}		
	
	/**
	 * Evento Click del botón buscar
	 */
	public void onClick_btnBuscar() {
		try {
			Util.limpiarListbox(ltbxLiquidacionVentas);
			
			if(chbxPorRangoFechas.isChecked() && !(cmbAgencia.getSelectedItem().getValue() instanceof Agencia)) {
				DlgMessage.information(Messages.getString("WndEspecieValorada.information.Agencia"), cmbAgencia);
				return;
			}
			
			
			
			fechaInicial = Constantes.FORMAT_DATE.format(dtbxFechaInicio.getValue());
			fechaFinal = fechaInicial;
			agenciaId = null;
			if(cmbAgencia.getSelectedItem().getValue() instanceof Agencia)
				agenciaId =  ((Agencia)cmbAgencia.getSelectedItem().getValue()).getId();
			if(!dtbxFechaFin.isDisabled())
				fechaFinal = Constantes.FORMAT_DATE.format(dtbxFechaFin.getValue());
			
			//Busca las liquidaciones de PASAJES.
			List<Liquidacion> resultPasajes = ServiceLocator.getLiquidacionManager().buscarLiquidacion(fechaInicial, fechaFinal, agenciaId, null, null);
			
			//Busca las liquidaciones de CARGA
			TreeMap<String, Liquidacion> resultCarga = ServiceLocator.getTranscarWebManager().buscarLiquidacionCounter(fechaInicial, fechaFinal, agenciaId, null); 
			
			//Carga el listado de liquidaciones
			cargarListaLiquidaciones(resultPasajes, resultCarga);

						
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
			String src=Constantes.URL_FORMATOS_LIQUIDACION + Constantes.CLAVE_PAHT+ nameFile;
			File file = new File(Constantes.DIRECTORY_LIQUIDACION + Constantes.CLAVE_PAHT +nameFile);
			final WndIFrame iFrame = new WndIFrame();
			iFrame.setSrc(src);
			iFrame.setFile(file);
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
			
			String nameFile = CreateDocument.creaRptLiquidacionByEspecieValorada(liquidacion, false);
			/*Carga el iframe*/
			String src=Constantes.URL_FORMATOS_LIQUIDACION +Constantes.CLAVE_PAHT+ nameFile;
			File file = new File(Constantes.DIRECTORY_LIQUIDACION + Constantes.CLAVE_PAHT +nameFile);
			final WndIFrame iFrame = new WndIFrame();
			iFrame.setSrc(src);
			iFrame.setFile(file);
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
	
	public void onClick_btnExportar() {
		try {
			Util.exportarExcel(ltbxLiquidacionVentas, "");
			
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
	
	private Double buscarEfectivoLiquidacion(Liquidacion liquidacion)throws Exception{
		Integer usuarioId = (chbxDetallePorUsuario.isChecked()?liquidacion.getUsuario().getId():null);
		String fecha = Constantes.FORMAT_DATE.format(liquidacion.getFechaLiquidacion());
		
		//Gastos - Pasajes
		List<Gasto> resultGasto = ServiceLocator.getGastoManager().buscarGasto(fecha, fecha, null, liquidacion.getAgencia().getId(), usuarioId);
		//Ventas Pasajes
		List<VentaPasaje> resultDetalleVentasPasaje = ServiceLocator.getVentaPasajesManager().buscarDetalladoVentas(liquidacion.getAgencia().getId(), usuarioId, fecha, fecha, -1);
		//Otros ingresos		
		List<Gasto> lstIngresos =  ServiceLocator.getGastoManager().obtenerGastosByLiquidacion(fecha, liquidacion.getAgencia().getId(), usuarioId, Constantes.TRUE_VALUE, false);

	
		List<VentaPasaje> listDetalleVentas = new ArrayList<VentaPasaje>();			
		for(VentaPasaje ventaPasaje: resultDetalleVentasPasaje) {
			ventaPasaje.setTipoConsulta(0); //Pasajes
			if(ventaPasaje.getTipoComprobante().getId().intValue()==Constantes.ID_TIPCOM_NOTA_CREDITO)
				ventaPasaje.getTipoFormaPago().setDenominacion("NOTA DE CREDITO");
			else if(ventaPasaje.getTipoMovimiento().getId().intValue()==Constantes.ID_TIPMOV_DEVOLUCION)
				ventaPasaje.getTipoFormaPago().setDenominacion("DEVOLUCION");
			else if(ventaPasaje.getTipoMovimiento().getId().intValue()==Constantes.ID_TIPMOV_ANULACION)
				ventaPasaje.getTipoFormaPago().setDenominacion("ANULACION");
			else if(ventaPasaje.getFormaPago().getId().intValue()==Constantes.ID_FORPAG_CREDITO)
				ventaPasaje.getTipoFormaPago().setDenominacion("CREDITO");		
			else if(ventaPasaje.getFormaPago().getId().intValue()==Constantes.ID_FORPAG_CORTESIA)
				ventaPasaje.getTipoFormaPago().setDenominacion("CORTESIA");
			listDetalleVentas.add(ventaPasaje);
		}
		
		//Ventas Carga
		Liquidacion liquidacionCarga = (liquidacion.getLiquidacionCarga()!=null?liquidacion.getLiquidacionCarga():null);
		if(liquidacionCarga !=null) {
			TranscarUsuarioPersonal transcarUsuarioPersonal = null;
			if(chbxDetallePorUsuario.isChecked()) {
				transcarUsuarioPersonal = new TranscarUsuarioPersonal();
				transcarUsuarioPersonal.setId(liquidacionCarga.getUsuario().getId());
				transcarUsuarioPersonal.setLogin(liquidacionCarga.getUsuario().getLogin());
			}

			List<VentaPasaje> resultDetalleVentasCarga = ServiceLocator.getTranscarWebManager().buscarDetalleVentas(transcarUsuarioPersonal, liquidacionCarga.getAgencia().getId(), fecha, fecha);
			
			for(VentaPasaje ventaCarga: resultDetalleVentasCarga) {
				ventaCarga.setTipoConsulta(1); //Carga
				if(ventaCarga.getTipoComprobante().getId().intValue()==Constantes.ID_TIPCOM_NOTA_CREDITO)
					ventaCarga.getTipoFormaPago().setDenominacion("NOTA DE CREDITO");						
				
				listDetalleVentas.add(ventaCarga);
			}
		}
		
//		//Otros ingresos
//		Double totalOtrosIngresos = .00;
//		for(Gasto otroIngreso: lstIngresos) {
//			totalOtrosIngresos += otroIngreso.getMonto();
//		}
		
		//Calcula el efectivo a liquidar
		Double saldoEfectivo  = .00;
		if(listDetalleVentas.size()>0) {
			saldoEfectivo = CreateDocument.creaRptLiquidacionByEgresos(null, listDetalleVentas, resultGasto, null, lstIngresos, false, false);	
		}		
		
		return saldoEfectivo;
	}
	
	/**
	 * Carga el listado de liquidaciones
	 * @param liquidaciones : Listado de liquidaciones a cargar
	 * @throws Exception
	 */
	private void cargarListaLiquidaciones(List<Liquidacion> liquidaciones, TreeMap<String, Liquidacion> liquidacionesCarga)throws Exception{
		
		Double totalSaldoLiquidacion = .00;
		Integer idAgencia=null;
		for(Liquidacion liquidacion: liquidaciones) {
			//Valida liquidacion de carga
			String key = Constantes.FORMAT_DATE.format(liquidacion.getFechaLiquidacion());
			key += liquidacion.getAgencia().getId().toString();
			key += liquidacion.getUsuario().getLogin();
			Liquidacion liquidacionCarga = liquidacionesCarga.get(key);	
			liquidacion.setLiquidacionCarga(liquidacionCarga);
			idAgencia = liquidacion.getAgencia().getId();
			
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
			
			Double saldoLiquidacion = buscarEfectivoLiquidacion(liquidacion);
			totalSaldoLiquidacion += saldoLiquidacion;
			cell = new Listcell(Util.toNumberFormat(saldoLiquidacion, 2));
			cell.setStyle("font-size:11px !important");
			item.appendChild(cell);
			
			if(chbxDetallePorUsuario.isChecked()) {
				cell = new Listcell();
				if (liquidacion.getestadoLiquidacion().equals(Constantes.LIQUI_ESTA_CERRADO)) {
					cell = new Listcell(Constantes.LIQUI_ESTA_CERRADO_LABEL); // +" - "+ Constantes.FORMAT_LONG.format(liquidacion.getFechaModificacion()));
					cell.setStyle("font-size:11px !important");
				}else
					cell = new Listcell(Constantes.LIQUI_ESTA_ABIERTO_LABEL);
				item.appendChild(cell);
							
							
				//Estado liquidacion - Carga
				cell = new Listcell();
				if(liquidacionCarga!=null) {
					if (liquidacionCarga.getestadoLiquidacion().equals(Constantes.LIQUI_ESTA_CERRADO)) {
						cell = new Listcell(Constantes.LIQUI_ESTA_CERRADO_LABEL); // +" - "+ Constantes.FORMAT_LONG.format(liquidacion.getFechaModificacion()));
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
			}			
			
			liquidacion.setLiquidacionCarga(liquidacionCarga);
			item.setValue(liquidacion);
			ltbxLiquidacionVentas.appendChild(item);
		}
		
		Listfoot listfoot= new Listfoot();
		Listfooter listfooter= new Listfooter("TOTAL");
		if(chbxDetallePorUsuario.isChecked())
			listfooter.setSpan(4);
		else
			listfooter.setSpan(2);
		listfoot.appendChild(listfooter);
		listfooter= new Listfooter(Util.toNumberFormat(totalSaldoLiquidacion, 2));
		listfoot.appendChild(listfooter);
		listfooter= new Listfooter();
		listfooter.setSpan(3);
		listfoot.appendChild(listfooter);		
		ltbxLiquidacionVentas.appendChild(listfoot);
	}
	
	private void cleanResult(boolean resetFechas)throws Exception{
		dtbxFechaInicio.setValue(new Date());
		dtbxFechaFin.setValue(new Date());
		cmbAgencia.setSelectedIndex(0);		
		Util.limpiarListbox(ltbxLiquidacionVentas);
	}
	
}
