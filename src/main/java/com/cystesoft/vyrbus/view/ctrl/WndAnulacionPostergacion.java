/**
 * Proyecto		: SISVYR
 * Sistema		: Sistema de Ventas y Reservas
 * Descripción	: 
 * Autor		: José Abanto
 * Fecha		: 22 may. 2021
 * Hora			: 21:42:04
 */
package com.cystesoft.vyrbus.view.ctrl;

import java.util.List;

import org.zkoss.zul.Datebox;
import org.zkoss.zul.Hlayout;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listhead;
import org.zkoss.zul.Listheader;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Radiogroup;
import org.zkoss.zul.Separator;

import com.cystesoft.vyrbus.service.locator.ServiceLocator;
import com.cystesoft.vyrbus.service.mappers.ResumenAnulacionPostergacion;
import com.cystesoft.vyrbus.service.util.Constantes;
import com.cystesoft.vyrbus.service.util.Messages;
import com.cystesoft.vyrbus.service.util.MyTime;
import com.cystesoft.vyrbus.view.ui.DlgMessage;
import com.cystesoft.vyrbus.view.ui.WndBase;

/**
 * @author Jose
 *
 */
public class WndAnulacionPostergacion extends WndBase {

	private static final long serialVersionUID = 1L;
	private Datebox dbDesde;
	private Datebox dbHasta;
	private Radiogroup rdgCriterios;
	private Listbox lbxAgencia = new Listbox();
	private Listbox lbxUsuario = new Listbox();
	private Hlayout hlytResumen;
	
	private static final int SEARCH_BY_AGENCIA = 1;
	private static final int SEARCH_BY_USER = 2;
	
	/* (non-Javadoc)
	 * @see com.cystesoft.vyrbus.view.ui.WndBase#initComponents()
	 */
	@Override
	public void initComponents() {
		dbDesde = (Datebox)this.getFellow("dbDesde");
		dbHasta = (Datebox)this.getFellow("dbHasta");
		rdgCriterios = (Radiogroup)this.getFellow("rdgCriterios");
		hlytResumen = (Hlayout)this.getFellow("hlytResumen");
	}

	/* (non-Javadoc)
	 * @see com.cystesoft.vyrbus.view.ui.WndBase#onCreate()
	 */
	@Override
	public void onCreate() throws Exception {
		MyTime time = new MyTime();
		dbDesde.setValue(Constantes.FORMAT_DATE.parse(time.dateServer()));
		dbHasta.setValue(Constantes.FORMAT_DATE.parse(time.dateServer()));
	}
	
	public void onSearch() {
		String fechaDesde = Constantes.FORMAT_DATE.format(dbDesde.getValue());
		String fechaHasta = Constantes.FORMAT_DATE.format(dbHasta.getValue());
		if(rdgCriterios.getSelectedItem() == null) {
			DlgMessage.information(Messages.getString("wndAnulacionPostergacion.information.noCriterios"));
			return;
		}
		
		try {
			if(rdgCriterios.getSelectedIndex()==0) {
				List<ResumenAnulacionPostergacion> lstAnuladosByAgencia = ServiceLocator.getVentaPasajesManager().buscarBoletosAnuladosByX(fechaDesde, fechaHasta, SEARCH_BY_AGENCIA);
				listarAnuladosPostergadosByAgencia(lstAnuladosByAgencia);
				List<ResumenAnulacionPostergacion> lstAnuladosByUsuario = ServiceLocator.getVentaPasajesManager().buscarBoletosAnuladosByX(fechaDesde, fechaHasta, SEARCH_BY_USER);
				listarAnuladosPostergadosByUsuario(lstAnuladosByUsuario);
			}else if(rdgCriterios.getSelectedIndex()==1) {
				List<ResumenAnulacionPostergacion> lstPostergadosByAgencia = ServiceLocator.getVentaPasajesManager().buscarBoletosPostergadosByX(fechaDesde, fechaHasta, SEARCH_BY_AGENCIA);
				listarAnuladosPostergadosByAgencia(lstPostergadosByAgencia);
				List<ResumenAnulacionPostergacion> lstPostergadosByUsuario = ServiceLocator.getVentaPasajesManager().buscarBoletosPostergadosByX(fechaDesde, fechaHasta, SEARCH_BY_USER);
				listarAnuladosPostergadosByUsuario(lstPostergadosByUsuario);				
			}else {
				System.out.println("Posterga x numero");
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void listarAnuladosPostergadosByAgencia(List<ResumenAnulacionPostergacion> lstResumen) {
		Listitem item = null;
		Listcell cell = null;
		
		lbxAgencia.getItems().clear();
		
		lbxAgencia.detach();
		lbxAgencia = new Listbox();
		lbxAgencia.setHeight("250px");
		lbxAgencia.setWidth("285px");
		
		Listhead listhead = new Listhead();
		
		Listheader listheader = new Listheader();
		listheader.setLabel("#");
		listheader.setWidth("30px");
		listhead.appendChild(listheader);
		
		listheader = new Listheader();
		listheader.setLabel("Agencia");
		listheader.setWidth("160px");
		listhead.appendChild(listheader);
		
		listheader = new Listheader();
		listheader.setLabel("Cantidad");
		listheader.setWidth("80px");
		listhead.appendChild(listheader);
		
		lbxAgencia.appendChild(listhead);
		
		hlytResumen.appendChild(lbxAgencia);
		
		Separator separator = new Separator();
		hlytResumen.appendChild(separator);
		
		int i=0;
		for(ResumenAnulacionPostergacion resumen : lstResumen) {
			i++;
			item = new Listitem();
			
			cell = new Listcell(String.valueOf(i));
			item.appendChild(cell);
			
			cell = new Listcell(resumen.getDenominacion());
			item.appendChild(cell);
			
			cell = new Listcell(resumen.getTotal().toString());
			item.appendChild(cell);
			
			lbxAgencia.appendChild(item);
			
		}
	}
	
	public void listarAnuladosPostergadosByUsuario(List<ResumenAnulacionPostergacion> lstResumen) {
		Listitem item = null;
		Listcell cell = null;
		
		lbxUsuario.getItems().clear();
		lbxUsuario.detach();
		lbxUsuario = new Listbox();
		lbxUsuario.setHeight("250px");
		lbxUsuario.setWidth("375px");
		
		Listhead listhead = new Listhead();
		
		Listheader listheader = new Listheader();
		listheader.setLabel("#");
		listheader.setWidth("30px");
		listhead.appendChild(listheader);
		
		listheader = new Listheader();
		listheader.setLabel("Usuario");
		listheader.setWidth("250px");
		listhead.appendChild(listheader);
		
		listheader = new Listheader();
		listheader.setLabel("Cantidad");
		listheader.setWidth("80px");
		listhead.appendChild(listheader);
		
		lbxUsuario.appendChild(listhead);
		
		hlytResumen.appendChild(lbxUsuario);
		
		int i=0;
		for(ResumenAnulacionPostergacion resumen : lstResumen) {
			i++;
			item = new Listitem();
			
			cell = new Listcell(String.valueOf(i));
			item.appendChild(cell);
			
			cell = new Listcell(resumen.getDenominacion());
			item.appendChild(cell);
			
			cell = new Listcell(resumen.getTotal().toString());
			item.appendChild(cell);
			
			lbxUsuario.appendChild(item);
			
		}
	}
}
