/**
 * Proyecto		: SISVYR
 * Sistema		: Sistema de Ventas y Reservas
 * Descripción	: 
 * Autor		: José Abanto
 * Fecha		: 27 jun. 2021
 * Hora			: 19:38:57
 */
package com.cystesoft.vyrbus.view.ctrl;

import java.util.Date;
import java.util.List;

import org.zkoss.zul.Datebox;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Textbox;

import com.cystesoft.vyrbus.model.bean.Manifiesto;
import com.cystesoft.vyrbus.service.locator.ServiceLocator;
import com.cystesoft.vyrbus.service.util.Constantes;
import com.cystesoft.vyrbus.service.util.Util;

import com.cystesoft.vyrbus.view.ui.DlgMessage;
import com.cystesoft.vyrbus.view.ui.WndBase;

/**
 * @author Marco
 *
 */
public class WndRptDevolucionIsc extends WndBase {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Datebox dtbxFechaInicio;
	private Datebox dtbxFechaFin;
	private Textbox txtPer4949;
	private Textbox txtPeriodo;
	private Listbox lbxManifiestos;
	
	
	
	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.view.ui.WndBase#initComponents()
	 */
	@Override
	public void initComponents() {
		 dtbxFechaInicio=(Datebox)this.getFellow("dtbxFechaInicio");
		 dtbxFechaFin=(Datebox)this.getFellow("dtbxFechaFin");
		 txtPer4949=(Textbox)this.getFellow("txtPer4949");
		 txtPeriodo=(Textbox)this.getFellow("txtPeriodo");
		 lbxManifiestos=(Listbox)this.getFellow("lbxManifiestos");
		
	}
	
	
	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.view.ui.WndBase#onCreate()
	 */
	@Override
	public void onCreate() throws Exception {
		// TODO Auto-generated method stub
		dtbxFechaInicio.setValue(new Date());
		dtbxFechaFin.setValue(new Date());
		
	}
	

	
	public void buscar() throws Exception{
		Util.limpiarListbox(lbxManifiestos);
		
		String fechaInicio=null, fechaFin=null, periodo=null,per4949=null;
		
		if(txtPer4949.getText().trim().isEmpty()){
			DlgMessage.information("Debe de ingresar el codigo PER4949.");
			txtPer4949.focus();
			return;
		}else if(txtPeriodo.getText().trim().isEmpty()){
			DlgMessage.information("Debe de ingresar el codigo del periodo.");
			txtPeriodo.focus();
			return;
		}
		
		if(dtbxFechaInicio.getValue()!=null)
			fechaInicio=Constantes.FORMAT_DATE.format(dtbxFechaInicio.getValue());
		if(dtbxFechaFin.getValue()!=null)
			fechaFin=Constantes.FORMAT_DATE.format(dtbxFechaFin.getValue());
		if(!(txtPer4949.getText().trim().isEmpty()))
			per4949=txtPer4949.getText().trim();
		if(!(txtPeriodo.getText().trim().isEmpty()))
			periodo=txtPeriodo.getText().trim();
		
		List<Manifiesto>lstManifiestos=ServiceLocator.getManifiestoManager().buscarDevolucionIsc(fechaInicio, fechaFin, per4949, periodo);
		String style="font-size:12px !important";
		String serie, numero;
		
		for(Manifiesto manifiesto:lstManifiestos){
			Listitem item=new Listitem();
			
			Listcell cell=new Listcell();
//			cell.setStyle(style);
//			item.appendChild(cell);
			cell=new Listcell(manifiesto.getItinerario().getId().toString());
			item.appendChild(cell);
			cell=new Listcell(manifiesto.getCodigoBus());
			cell.setStyle(style);
			item.appendChild(cell);
			cell=new Listcell(manifiesto.getRuc());
			cell.setStyle(style);
			item.appendChild(cell);
			cell=new Listcell(manifiesto.getPer4949());
			cell.setStyle(style);
			item.appendChild(cell);
			cell=new Listcell(manifiesto.getPeriodo());
			cell.setStyle(style);
			item.appendChild(cell);
			serie = manifiesto.getNumeroManifiesto().substring(0, 3);
			numero = manifiesto.getNumeroManifiesto().substring(4);
			cell=new Listcell(serie);
			cell.setStyle(style);
			item.appendChild(cell);
			cell=new Listcell(numero);
			cell.setStyle(style);
			item.appendChild(cell);
			cell=new Listcell(manifiesto.getBus().getGrupoMantenimiento().getDenominacion());
			cell.setStyle(style);
			item.appendChild(cell);
			cell=new Listcell(manifiesto.getPlaca());
			cell.setStyle(style);
			item.appendChild(cell);
			cell=new Listcell(manifiesto.getCertificadoHabilitacion());
			cell.setStyle(style);
			item.appendChild(cell);
			cell=new Listcell(Constantes.FORMAT_DATE.format(manifiesto.getItinerario().getFechaPartida()));
			cell.setStyle(style);
			item.appendChild(cell);
			cell=new Listcell(manifiesto.getPuntoPartidaDepartamento());
			cell.setStyle(style);
			item.appendChild(cell);
			cell=new Listcell(manifiesto.getPuntoPartidaDistrito());
			cell.setStyle(style);
			item.appendChild(cell);
			cell=new Listcell(manifiesto.getPuntoLlegadaDepartamento());
			cell.setStyle(style);
			item.appendChild(cell);
			cell=new Listcell(manifiesto.getPuntoLlegadaDistrito());
			cell.setStyle(style);
			item.appendChild(cell);
			cell=new Listcell(Util.toNumberFormat(manifiesto.getImporte(), 2));
			cell.setStyle(style);
			item.appendChild(cell);

			lbxManifiestos.appendChild(item);
		}
	}
	
	
	
	public void exportarExcel(){
//		if(lstTransbordos.getItems().size()>0)
//			Util.exportarExcel(lstTransbordos, "TRANSBORDOS");
	}

}
