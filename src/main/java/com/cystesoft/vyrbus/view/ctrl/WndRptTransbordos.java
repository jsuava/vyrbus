/**
 * Proyecto		: SISVYR
 * Sistema		: Sistema de Ventas y Reservas
 * Descripción	:
 * Autor		: José Avalos Sullo
 * Fecha		: 19/05/2014
 */
package com.cystesoft.vyrbus.view.ctrl;

import java.util.Date;
import java.util.List;

import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

import com.cystesoft.vyrbus.model.bean.Localidad;
import com.cystesoft.vyrbus.model.bean.Pasajero;
import com.cystesoft.vyrbus.model.bean.Transbordo;
import com.cystesoft.vyrbus.service.locator.ServiceLocator;
import com.cystesoft.vyrbus.service.util.Constantes;
import com.cystesoft.vyrbus.service.util.Util;
import com.cystesoft.vyrbus.service.util.UtilData;
import com.cystesoft.vyrbus.view.ui.WndBase;
import com.cystesoft.vyrbus.view.ui.WndBuscarPasajero;

/**
 * @author JABANTO
 *
 */
public class WndRptTransbordos extends WndBase {

	private static final long serialVersionUID = 1L;
	private Datebox dtbxFechaInicio;
	private Datebox dtbxFechaFin;
	private Combobox cmbOrigen;
	private Combobox cmbDestino;
	private Textbox txtNumeroBoleto;
	private Textbox txtPasajero;
	private Listbox lstTransbordos;

	Pasajero pasajero=null;
	private Window wndBusqPasajero;


	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.view.ui.WndBase#initComponents()
	 */
	@Override
	public void initComponents() {
		 dtbxFechaInicio=(Datebox)this.getFellow("dtbxFechaInicio");
		 dtbxFechaFin=(Datebox)this.getFellow("dtbxFechaFin");
		 cmbOrigen=(Combobox)this.getFellow("cmbOrigen");
		 cmbDestino=(Combobox)this.getFellow("cmbDestino");
		 txtNumeroBoleto=(Textbox)this.getFellow("txtNumeroBoleto");
		 txtPasajero=(Textbox)this.getFellow("txtPasajero");
		 lstTransbordos=(Listbox)this.getFellow("lstTransbordos");
		 
			//Autocompleta el numero de Boleto
		txtNumeroBoleto.addEventListener(Events.ON_CHANGE,new EventListener<Event>() {
			@Override
			public void onEvent(Event event) throws Exception {
				if(txtNumeroBoleto.getText().trim().length()>0)
					txtNumeroBoleto.setText(Util.autocompleNumberBoleto(txtNumeroBoleto.getText()));
			}

		});		 

	}


	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.view.ui.WndBase#onCreate()
	 */
	@Override
	public void onCreate() throws Exception {
		// TODO Auto-generated method stub
		dtbxFechaInicio.setValue(new Date());
		dtbxFechaFin.setValue(new Date());

		UtilData.cargarDataCombo(cmbOrigen, Localidad.class, true);
		UtilData.cargarDataCombo(cmbDestino, Localidad.class, true);



	}


	public void buscar() throws Exception{
		Util.limpiarListbox(lstTransbordos);

		String fechaTransInicio=null, fechaTransFin=null, origen=null, destino=null,boleto=null;
		Long idPasajero=null;

		if(dtbxFechaInicio.getValue()!=null)
			fechaTransInicio=Constantes.FORMAT_DATE.format(dtbxFechaInicio.getValue());
		if(dtbxFechaFin.getValue()!=null)
			fechaTransFin=Constantes.FORMAT_DATE.format(dtbxFechaFin.getValue());
		if(cmbOrigen.getSelectedItem().getValue() instanceof Localidad)
			origen=((Localidad)cmbOrigen.getSelectedItem().getValue()).getDenominacion();
		if(cmbDestino.getSelectedItem().getValue() instanceof Localidad)
			destino=((Localidad)cmbDestino.getSelectedItem().getValue()).getDenominacion();
		if(!(txtNumeroBoleto.getText().trim().isEmpty()))
			boleto=txtNumeroBoleto.getText().trim().toUpperCase();
		if(pasajero!=null)
			idPasajero=pasajero.getId();

		List<Transbordo>lstTransbordo=ServiceLocator.getReportesManager().pasajerosTransbordados(fechaTransInicio, fechaTransFin, origen, destino, boleto, idPasajero);
		String style="font-size:11px !important";

		for(Transbordo transbordo:lstTransbordo){
			Listitem item=new Listitem();

			Listcell cell=new Listcell(Constantes.FORMAT_DATE_TIME_24H.format(transbordo.getFechaInsercion()));
			cell.setStyle(style);
			item.appendChild(cell);
			cell=new Listcell(transbordo.getVentaPasaje().getTipoTransaccion());
			item.appendChild(cell);
			cell=new Listcell(transbordo.getVentaPasaje().getNumeroBoleto());
			cell.setStyle(style);
			item.appendChild(cell);
			cell=new Listcell(transbordo.getVentaPasaje().getPasajero().getNombresApellidos());
			item.appendChild(cell);
			cell=new Listcell(transbordo.getVentaPasaje().getRuta().toString());
			item.appendChild(cell);
			cell=new Listcell(transbordo.getServicioOrigen().getDenominacion());
			item.appendChild(cell);
			cell=new Listcell(Constantes.FORMAT_DATE.format(transbordo.getFechaPartidaOrigen()));
			cell.setStyle(style);
			item.appendChild(cell);
			cell=new Listcell(transbordo.getHoraPartidaOrigen());
			cell.setStyle(style);
			item.appendChild(cell);
			cell=new Listcell(transbordo.getNumeroAsientoOrigen().toString());
			cell.setStyle(style);
			item.appendChild(cell);
			cell=new Listcell(transbordo.getServicioDestino().getDenominacion());
			item.appendChild(cell);
			cell=new Listcell(Constantes.FORMAT_DATE.format(transbordo.getFechaPartidaDestino()));
			cell.setStyle(style);
			item.appendChild(cell);
			cell=new Listcell(transbordo.getHoraPartidaDestino());
			cell.setStyle(style);
			item.appendChild(cell);
			cell=new Listcell(transbordo.getNumeroAsientoDestino().toString());
			cell.setStyle(style);
			item.appendChild(cell);
			cell=new Listcell(transbordo.getUsuarioInsercion());
			item.appendChild(cell);

			lstTransbordos.appendChild(item);
		}
	}

	public void buscarPasajero() throws Exception{
		pasajero=null;
		final WndBuscarPasajero oWndBuscarPasajero = new WndBuscarPasajero();
		wndBusqPasajero.appendChild(oWndBuscarPasajero);
		oWndBuscarPasajero.oThisWindow.setTitle("Búsqueda de Pasajeros");
		oWndBuscarPasajero.setMode(MODAL);
		oWndBuscarPasajero.onCreate();
		oWndBuscarPasajero.addEventListener(Events.ON_SELECT, new EventListener<Event>() {
			@Override
			public void onEvent(Event e){
				pasajero=oWndBuscarPasajero.getPasajero();
				if(pasajero!=null)
					txtPasajero.setText(pasajero.getNombresApellidos());
			}
		});
	}

	public void exportarExcel(){
		if(lstTransbordos.getItems().size()>0)
			Util.exportarExcel(lstTransbordos, "TRANSBORDOS");
	}


}
