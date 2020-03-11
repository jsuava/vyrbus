
package com.cystesoft.vyrbus.view.ctrl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.zkoss.zul.Combobox;
import org.zkoss.zul.Comboitem;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;

import com.cystesoft.vyrbus.model.bean.Promocion;
import com.cystesoft.vyrbus.model.bean.VentaPasaje;
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
public class WndRptVentaPromocion extends WndBase {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Datebox dtbxFechaInicio;
	private Datebox dtbxFechaFinal;
	private Combobox cmbPromocion;
	private Listbox lstVentasPromocion;
	private Combobox cmbTipoDescuento;
	private Listbox lstVentasPromocionDeta;
	
	
	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.view.ui.WndBase#initComponents()
	 */
	@Override
	public void initComponents() {
		// TODO Auto-generated method stub
		dtbxFechaInicio=(Datebox)this.getFellow("dtbxFechaInicio");
		dtbxFechaFinal=(Datebox)this.getFellow("dtbxFechaFinal");
		cmbPromocion=(Combobox)this.getFellow("cmbPromocion");
		lstVentasPromocion=(Listbox)this.getFellow("lstVentasPromocion");
		cmbTipoDescuento=(Combobox)this.getFellow("cmbTipoDescuento");
		lstVentasPromocionDeta=(Listbox)this.getFellow("lstVentasPromocionDeta");
	}
		
	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.view.ui.WndBase#onCreate()
	 */
	@Override
	public void onCreate() throws Exception {
		// TODO Auto-generated method stub
		dtbxFechaInicio.setValue(new Date());
		dtbxFechaFinal.setValue(new Date());
		cargarPromociones();
		
		
		/* Carga tipo descuento*/
		UtilData.cargarGenericData(cmbTipoDescuento, true);
		Comboitem comboitem=new Comboitem();
		comboitem.setLabel("SOLES");
		comboitem.setValue("S");
		cmbTipoDescuento.appendChild(comboitem);
		comboitem=new Comboitem();
		comboitem.setLabel("PORCENTAJE");
		comboitem.setValue("P");
		cmbTipoDescuento.appendChild(comboitem);
	}

	/**
	 * Carga el combo con las promociones, segun el rango de fecha que se haya seleccionado.
	 * @throws Exception
	 */
	public void cargarPromociones() throws Exception{
		try{
			Util.limpiarCombobox(cmbPromocion);
			String fechaInicio=Constantes.FORMAT_DATE.format(dtbxFechaInicio.getValue());
			String fechaFin=Constantes.FORMAT_DATE.format(dtbxFechaFinal.getValue());
			
			List<Promocion>lstPromociones=ServiceLocator.getReportesManager().ventasPromocionLstPromociones(fechaInicio, fechaFin);
			UtilData.cargarGenericData(cmbPromocion, true);
			for(Promocion promocion:lstPromociones){
				Comboitem comboitem=new Comboitem();
				comboitem.setLabel(promocion.getDenominacion());
				comboitem.setValue(promocion);
				cmbPromocion.appendChild(comboitem);
			}
		}catch (Exception ex){
			ex.getStackTrace();
			DlgMessage.error(Messages.getString(ex.getMessage()));
		}
	}
	
	/**
	 * Busca las ventas asociadas a alguna promcion.
	 */
	public void buscarVentasPromocion(){
		try {
			Util.limpiarListbox(lstVentasPromocion);
			Util.limpiarListbox(lstVentasPromocionDeta);
			String style="font-size:11px !important";
			String fechaInicio=Constantes.FORMAT_DATE.format(dtbxFechaInicio.getValue());
			String fechaFin=Constantes.FORMAT_DATE.format(dtbxFechaFinal.getValue());
			Long idPromocion=null;
			String tipoDescuento=null;
			
			if(cmbPromocion.getSelectedIndex()>0)
				idPromocion=((Promocion)cmbPromocion.getSelectedItem().getValue()).getId();
			if(cmbTipoDescuento.getSelectedIndex()>0)
				tipoDescuento=cmbTipoDescuento.getSelectedItem().getValue();
			ArrayList<Promocion>lstVtaPromo=ServiceLocator.getReportesManager().ventasPromocion(fechaInicio, fechaFin, idPromocion,tipoDescuento);
			
			Double totalDescuento=.00, totalVenta=.00;
			Integer cantidad=0;
			for(Promocion promocion:lstVtaPromo){
				Listitem item=new Listitem();
				Listcell cell=null;
				
				cell=new Listcell(promocion.getDenominacion());
				item.appendChild(cell);
				cell=new Listcell(promocion.getTipoDescuento());
				cell.setStyle(style);
				item.appendChild(cell);
				cell=new Listcell(promocion.getCantidadViajesPasajero().toString());
				cell.setStyle(style);
				item.appendChild(cell);
				cell=new Listcell(Util.toNumberFormat(promocion.getTotalDescuento(), 2));
				cell.setStyle(style);
				item.appendChild(cell);
				cell=new Listcell(Util.toNumberFormat(promocion.getTotalVenta(), 2));
				cell.setStyle(style);
				item.appendChild(cell);
				
				item.setValue(promocion);
				lstVentasPromocion.appendChild(item);
				
				totalDescuento+=promocion.getTotalDescuento();
				totalVenta+=promocion.getTotalVenta();
				cantidad+=Integer.valueOf(promocion.getCantidadViajesPasajero());
			}
			
			/* AGREGA TOTALES AL FINAL DE LA LISTA*/
			String styleTotales="font-size:11px !important; color:red;font-weight: bold;";
			Listitem item=new Listitem();
			Listcell cell=null;
			
			cell=new Listcell("TOTALES :");
			cell.setStyle(styleTotales+"text-align: right;");
			item.appendChild(cell);
			cell=new Listcell();
			cell.setStyle(styleTotales);
			item.appendChild(cell);
			cell=new Listcell();
			cell.setStyle(styleTotales);
			cell=new Listcell(cantidad.toString());
			cell.setStyle(styleTotales);
			item.appendChild(cell);
			cell=new Listcell(Util.toNumberFormat(totalDescuento, 2));
			cell.setStyle(styleTotales);
			item.appendChild(cell);
			cell=new Listcell(Util.toNumberFormat(totalVenta, 2));
			cell.setStyle(styleTotales);
			item.appendChild(cell);

			lstVentasPromocion.appendChild(item);
			
			if(lstVentasPromocion.getItems().size()>0)
				lstVentasPromocion.setSelectedIndex(0);
			
		} catch (Exception e) {
			e.getStackTrace();
			DlgMessage.error(Messages.getString(e.getMessage()));
		}
	}
	
	/**
	 * Busca el detalle de la promocion seleccionada.
	 * @throws Exception 
	 */
	public void buscarVentasPromocionDetalle() throws Exception{
		try{
			if(lstVentasPromocion.getItems().size()>0 && lstVentasPromocion.getSelectedItem().getValue() instanceof Promocion){
				Util.limpiarListbox(lstVentasPromocionDeta);
				String fechaInicio=Constantes.FORMAT_DATE.format(dtbxFechaInicio.getValue());
				String fechaFin=Constantes.FORMAT_DATE.format(dtbxFechaFinal.getValue());
				
				Promocion promocion=lstVentasPromocion.getSelectedItem().getValue();
				ArrayList<VentaPasaje>lstVentasPromo=ServiceLocator.getReportesManager().ventasPromocionDeta(fechaInicio, fechaFin, promocion.getId());
				
				Integer cant=0;
				String style="font-size:11px !important;";
				for(VentaPasaje ventaPasaje:lstVentasPromo){
					cant++;
					Listitem item=new Listitem();
					Listcell cell=null;
					
					cell=new Listcell(cant.toString());
					cell.setStyle(style);
					item.appendChild(cell);
					cell=new Listcell(Constantes.FORMAT_DATE.format(ventaPasaje.getFechaLiquidacion()));
					cell.setStyle(style);
					item.appendChild(cell);
					cell=new Listcell(ventaPasaje.getNumeroBoleto());
					cell.setStyle(style);
					item.appendChild(cell);
					cell=new Listcell(ventaPasaje.getPasajero().getNombresApellidos());
					item.appendChild(cell);
					cell=new Listcell(ventaPasaje.getRuta().toString());
					item.appendChild(cell);
					cell=new Listcell(ventaPasaje.getServicio().getDenominacion());
					item.appendChild(cell);
					cell=new Listcell(ventaPasaje.getFechaPartida()!=null?Constantes.FORMAT_DATE.format(ventaPasaje.getFechaPartida()):"");
					cell.setStyle(style);
					item.appendChild(cell);
					cell=new Listcell(ventaPasaje.getHoraPartida()!=null?ventaPasaje.getHoraPartida():"");
					cell.setStyle(style);
					item.appendChild(cell);
					cell=new Listcell(Util.toNumberFormat(ventaPasaje.getTarifa(),2));
					cell.setStyle(style+"text-align: right;");
					item.appendChild(cell);
					cell=new Listcell(Util.toNumberFormat(ventaPasaje.getDescuento(),2));
					cell.setStyle(style+"text-align: right;");
					item.appendChild(cell);
					cell=new Listcell(Util.toNumberFormat(ventaPasaje.getImportePagado(),2));
					cell.setStyle(style+"text-align: right;");
					item.appendChild(cell);
					cell=new Listcell(ventaPasaje.getUsuario().toString());
					item.appendChild(cell);
					cell=new Listcell(ventaPasaje.getAgencia().getDenominacion());
					item.appendChild(cell);
					
					lstVentasPromocionDeta.appendChild(item);
				}
				
				if(lstVentasPromocionDeta.getItems().size()>0)
					lstVentasPromocionDeta.setSelectedIndex(0);
			}
			
		}catch(Exception ex){
			ex.getStackTrace();
			DlgMessage.error(Messages.getString(ex.getMessage()));
		}
		
	}
	
	
	public void exportar(){
		if(lstVentasPromocion.getItems().size()>0){
			Util.exportarExcel(lstVentasPromocion, "VENTAS_PROMOCION_");
		}
	}
	
	public void exportarDetalle(){
		if(lstVentasPromocionDeta.getItems().size()>0){
			Util.exportarExcel(lstVentasPromocionDeta, "VENTAS_PROMOCION_DETALLE_");
		}
	}
	
}
