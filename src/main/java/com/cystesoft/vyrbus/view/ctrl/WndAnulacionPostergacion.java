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
import com.cystesoft.vyrbus.service.util.MyTime;
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
		
		try {
			if(rdgCriterios.getSelectedIndex()==0) {
				List<ResumenAnulacionPostergacion> lstAnuladosByAgencia = ServiceLocator.getVentaPasajesManager().buscarBoletosAnuladosPorAgencia(fechaDesde, fechaHasta);
				listarAnuladosByAgencia(lstAnuladosByAgencia);
				List<ResumenAnulacionPostergacion> lstAnuladosByUsuario = ServiceLocator.getVentaPasajesManager().buscarBoletosAnuladosPorUsuario(fechaDesde, fechaHasta);
				listarAnuladosByUsuario(lstAnuladosByUsuario);
			}else if(rdgCriterios.getSelectedIndex()==1) {
				System.out.println("Postergacion");
			}else {
				System.out.println("Posterga x numero");
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void listarAnuladosByAgencia(List<ResumenAnulacionPostergacion> lstResumen) {
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
		listheader.setStyle("color: #ffffff;");
		listhead.appendChild(listheader);
		
		listheader = new Listheader();
		listheader.setLabel("AGENCIA");
		listheader.setWidth("175px");
		listheader.setStyle("color: #ffffff;");
		listhead.appendChild(listheader);
		
		listheader = new Listheader();
		listheader.setLabel("CANTIDAD");
		listheader.setWidth("80px");
		listheader.setStyle("color: #ffffff;");
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
	
	public void listarAnuladosByUsuario(List<ResumenAnulacionPostergacion> lstResumen) {

		Listitem item = null;
		Listcell cell = null;
		
		lbxUsuario.getItems().clear();
		lbxUsuario.detach();
		lbxUsuario = new Listbox();
		lbxUsuario.setHeight("250px");
		lbxUsuario.setWidth("375px");
		
		Listhead listheadUsuario = new Listhead();
		
		Listheader listheaderUsuario = new Listheader();
		listheaderUsuario.setLabel("#");
		listheaderUsuario.setWidth("30px");
		listheaderUsuario.setStyle("color: #ffffff;");
		listheadUsuario.appendChild(listheaderUsuario);
		
		listheaderUsuario = new Listheader();
		listheaderUsuario.setLabel("USUARIO");
		listheaderUsuario.setWidth("265px");
		listheaderUsuario.setStyle("color: #ffffff;");
		listheadUsuario.appendChild(listheaderUsuario);
		
		listheaderUsuario = new Listheader();
		listheaderUsuario.setLabel("CANTIDAD");
		listheaderUsuario.setWidth("80px");
		listheaderUsuario.setStyle("color: #ffffff;");
		listheadUsuario.appendChild(listheaderUsuario);
		
		lbxUsuario.appendChild(listheadUsuario);
		hlytResumen.appendChild(lbxUsuario);
		
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
			
			lbxUsuario.appendChild(item);
			
		}

		
	}
}
