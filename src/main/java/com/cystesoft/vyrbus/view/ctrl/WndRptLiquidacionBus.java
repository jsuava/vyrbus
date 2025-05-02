/**
 * Proyecto		: SISVYR
 * Sistema		: Sistema de Ventas y Reservas
 * Descripción	:
 * Autor		: José Abanto
 * Fecha		: 08/04/2022
 * Hora			: 12:05:13
 */
package com.cystesoft.vyrbus.view.ctrl;

import java.util.Date;
import java.util.List;
import java.util.TreeMap;

import org.zkoss.zul.Combobox;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;

import com.cystesoft.vyrbus.model.bean.Bus;
import com.cystesoft.vyrbus.model.bean.Manifiesto;
import com.cystesoft.vyrbus.service.locator.ServiceLocator;
import com.cystesoft.vyrbus.service.util.Constantes;
import com.cystesoft.vyrbus.service.util.Util;
import com.cystesoft.vyrbus.service.util.UtilData;
import com.cystesoft.vyrbus.view.ui.DlgMessage;
import com.cystesoft.vyrbus.view.ui.WndBase;

/**
 * @author abant
 *
 */
public class WndRptLiquidacionBus extends WndBase {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	private Datebox dtbxFechaInicio;
	private Datebox dtbxFechaFin;
	private Combobox cmbBus;
	private Listbox ltbxLiquidacionBus;




	/* (non-Javadoc)
	 * @see com.cystesoft.vyrbus.view.ui.WndBase#initComponents()
	 */
	@Override
	public void initComponents() {
		// TODO Auto-generated method stub
		super.initComponents();

		dtbxFechaInicio = (Datebox)this.getFellow("dtbxFechaInicio");
		dtbxFechaFin = (Datebox)this.getFellow("dtbxFechaFin");
		cmbBus = (Combobox)this.getFellow("cmbBus");
		ltbxLiquidacionBus = (Listbox)this.getFellow("ltbxLiquidacionBus");
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
		UtilData.cargarDataCombo(cmbBus, Bus.class,true);
	}

	/**
	 * Realiza la busqueda de la liquidacion x bus
	 */
	public void onClick_btnBuscar(){
		try {
			Util.limpiarListbox(ltbxLiquidacionBus);
			String fechaInicio = Constantes.FORMAT_DATE.format(dtbxFechaInicio.getValue());
			String fechaFin = Constantes.FORMAT_DATE.format(dtbxFechaFin.getValue());
			String codigoBus = null;
			if(cmbBus.getSelectedItem().getValue() instanceof Bus)
				codigoBus = ((Bus)cmbBus.getSelectedItem().getValue()).getCodigo();

			//Busca en Pasajes
			List<Manifiesto> resultPasajes = ServiceLocator.getVentaPasajesManager().buscarLiquidacionBus(fechaInicio, fechaFin, codigoBus);

			//Buscar en Carga (Transcar)
			TreeMap<String, Manifiesto> resultCarga = ServiceLocator.getTranscarWebManager().buscarLiquidacionBus(fechaInicio, fechaFin, codigoBus);
			
			Double totalSolesCarga = .00;

			for(Manifiesto manifiesto : resultPasajes){
				Listitem item = new Listitem();
				Listcell cell = new Listcell(Constantes.FORMAT_DATE.format(manifiesto.getItinerario().getFechaPartida()));
				cell.setStyle("font-size:11px !important");
				item.appendChild(cell);
				cell = new Listcell(manifiesto.getBus().getCodigo()+"-"+manifiesto.getBus().getNumeroPlaca());
				cell.setStyle("font-size:11px !important");
				item.appendChild(cell);
				cell = new Listcell(manifiesto.getItinerario().getRuta().toString());
				item.appendChild(cell);
				cell = new Listcell(manifiesto.getPiloto());
				item.appendChild(cell);
				cell = new Listcell(manifiesto.getCopiloto()!=null? manifiesto.getCopiloto():"");
				item.appendChild(cell);
				//Cabtidad de Pasajeros
				cell = new Listcell(Util.toNumberFormat(manifiesto.getCantidadPasajeros(), 0));
				cell.setStyle("font-size:11px !important");
				item.appendChild(cell);
				//Total venta pasajes
				cell = new Listcell(Util.toNumberFormat(manifiesto.getImporte(), 2));
				cell.setStyle("font-size:11px !important");
				item.appendChild(cell);
				
				totalSolesCarga = .00;
				String key = Constantes.FORMAT_DATE.format(manifiesto.getItinerario().getFechaPartida()) + "-" +manifiesto.getBus().getCodigo();
				Manifiesto manifiestoCarga = resultCarga.get(key);
				if(manifiestoCarga!=null)
					totalSolesCarga = manifiestoCarga.getImporte();
				cell = new Listcell(Util.toNumberFormat(totalSolesCarga, 2));
				cell.setStyle("font-size:11px !important");
				item.appendChild(cell);
				
				//Total Ingresos
				cell = new Listcell(Util.toNumberFormat(manifiesto.getImporte() + totalSolesCarga, 2));
				cell.setStyle("font-size:11px !important");
				item.appendChild(cell);
				
				item.setValue(manifiesto);
				ltbxLiquidacionBus.appendChild(item);
			}

		} catch (Exception e) {
			e.printStackTrace();
			DlgMessage.error(e.getMessage());
		}
	}

	/**
	 * Exporta los resultados en formato .xls
	 */
	public void onClick_btnExportar(){
		try {

			if(ltbxLiquidacionBus.getItemCount()>0)
				Util.exportarExcel(ltbxLiquidacionBus, "LiquidacionBus_");

		} catch (Exception e) {
			e.printStackTrace();
			DlgMessage.error(e.getMessage());
		}
	}
}
