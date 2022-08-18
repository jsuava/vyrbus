/**
 * Proyecto		: SISVYR
 * Sistema		: Sistema de Ventas y Reservas
 * Descripción	:
 * Autor		: José Abanto
 * Fecha		: 27/08/2014
 * Hora			: 10:02:39
 */
package com.cystesoft.vyrbus.view.ctrl;

import java.util.Date;
import java.util.List;

import org.zkoss.zul.Combobox;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;

import com.cystesoft.vyrbus.model.bean.HRE;
import com.cystesoft.vyrbus.model.bean.Localidad;
import com.cystesoft.vyrbus.service.locator.ServiceLocator;
import com.cystesoft.vyrbus.service.util.Constantes;
import com.cystesoft.vyrbus.service.util.Util;
import com.cystesoft.vyrbus.service.util.UtilData;
import com.cystesoft.vyrbus.view.ui.DlgMessage;
import com.cystesoft.vyrbus.view.ui.WndBase;

/**
 * @author JABANTO
 *
 */
public class WndRptHREEmitidas extends WndBase{
	private static final long serialVersionUID = 1L;

	private Datebox dtbxFechaInicial;
	private Datebox dtbxFechaFinal;
	private Combobox cmbOrigen;
	private Combobox cmbDestino;
	private Listbox lsbxHREEmitidas;


	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.view.ui.WndBase#initComponents()
	 */
	@Override
	public void initComponents() {
		dtbxFechaInicial=(Datebox)this.getFellow("dtbxFechaInicial");
		dtbxFechaFinal=(Datebox)this.getFellow("dtbxFechaFinal");
		cmbOrigen=(Combobox)this.getFellow("cmbOrigen");
		cmbDestino=(Combobox)this.getFellow("cmbDestino");
		lsbxHREEmitidas=(Listbox)this.getFellow("lsbxHREEmitidas");
	}

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.view.ui.WndBase#onCreate()
	 */
	@Override
	public void onCreate() throws Exception {
		dtbxFechaInicial.setValue(new Date());
		dtbxFechaFinal.setValue(new Date());

		UtilData.cargarDataCombo(cmbOrigen, Localidad.class, true);
		UtilData.cargarDataCombo(cmbDestino, Localidad.class, true);

		/*Por defecto LIMA*/
		Util.seleccionarValorItemCombo(Localidad.class, cmbOrigen, Constantes.ID_LOC_LIMA);
	}

	/**
	 * Busca las HRE emitidas
	 * @throws Exception
	 */
	public void buscar()throws Exception{
		try {
			Util.limpiarListbox(lsbxHREEmitidas);
			String fechaInicial=Constantes.FORMAT_DATE.format(dtbxFechaInicial.getValue());
			String fechaFinal=Constantes.FORMAT_DATE.format(dtbxFechaFinal.getValue());
			Integer idOrigen=null,idDestino=null;
			if(cmbOrigen.getSelectedItem().getValue() instanceof Localidad)
				idOrigen=((Localidad)cmbOrigen.getSelectedItem().getValue()).getId();
			if(cmbDestino.getSelectedItem().getValue() instanceof Localidad)
				idDestino=((Localidad)cmbDestino.getSelectedItem().getValue()).getId();
			String style="font-size:11px !important";

			List<HRE>lstHRE=ServiceLocator.getHREManager().buscarHREEmitida(fechaInicial, fechaFinal, idOrigen, idDestino);
			for(HRE hre:lstHRE){
				Listitem item=new  Listitem();
				Listcell cell=new Listcell(Constantes.FORMAT_DATE.format(hre.getFechaSalida()));
				cell.setStyle(style);
				item.appendChild(cell);
				cell=new Listcell(hre.getHoraSalida());
				cell.setStyle(style);
				item.appendChild(cell);
				cell=new Listcell(hre.getRuta().toString());
				item.appendChild(cell);
				cell=new Listcell(hre.getProgramacionServicio().getItinerario().getServicio().toString());
				item.appendChild(cell);
				cell=new Listcell(hre.getNumeroBus());
				cell.setStyle(style);
				item.appendChild(cell);
				cell=new Listcell(hre.getFechaEstimacionLlegada()!=null?Constantes.FORMAT_DATE.format(hre.getFechaEstimacionLlegada()):"");
				cell.setStyle(style);
				item.appendChild(cell);
				cell=new Listcell(hre.getHoraEstimacionLlegada()!=null?hre.getHoraEstimacionLlegada():"");
				cell.setStyle(style);
				item.appendChild(cell);
				cell=new Listcell(hre.toString());
				cell.setStyle(style);
				item.appendChild(cell);
				cell=new Listcell(hre.getFechaLlegadaReal()!=null?Constantes.FORMAT_DATE.format(hre.getFechaLlegadaReal()):"");
				cell.setStyle(style);
				item.appendChild(cell);
				cell=new Listcell(hre.getHoraLlegadaReal()!=null?hre.getHoraLlegadaReal():"");
				cell.setStyle(style);
				item.appendChild(cell);
				cell=new Listcell(hre.getProgramacionServicio().getTripulante().toString());
				item.appendChild(cell);
				cell=new Listcell(hre.getProgramacionServicio().getPiloto().toString());
				item.appendChild(cell);
				cell=new Listcell(hre.getProgramacionServicio().getCopiloto().toString());
				item.appendChild(cell);
				cell=new Listcell(hre.getProgramacionServicio().getCopilotoAuxiliar()!=null?hre.getProgramacionServicio().getCopilotoAuxiliar().toString():"");
				item.appendChild(cell);

				lsbxHREEmitidas.appendChild(item);
			}

		} catch (Exception e) {
			e.printStackTrace();
			DlgMessage.error(e.getMessage());
		}



	}

	/**
	 * Exporta ha excel
	 */
	public void Exportar(){
		if(lsbxHREEmitidas.getItems().size()>0)
			Util.exportarExcel(lsbxHREEmitidas, "HRE EMITIDAS ");
	}

	/**
	 * CUSTOMERASSIGNEDACCOUNTID
	 */

}
