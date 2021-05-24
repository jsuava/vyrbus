/**
 * Proyecto		: SISVYR
 * Sistema		: Sistema de Ventas y Reservas
 * Descripción	: 
 * Autor		: José Abanto
 * Fecha		: 23 may. 2021
 * Hora			: 20:38:25
 */
package com.cystesoft.vyrbus.view.ctrl;

import java.io.Serializable;
import java.util.Date;
import java.util.Locale;

import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.Label;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Radio;
import org.zkoss.zul.Tab;

import com.cystesoft.vyrbus.model.bean.Agencia;
import com.cystesoft.vyrbus.model.bean.CanalVenta;
import com.cystesoft.vyrbus.model.bean.Localidad;
import com.cystesoft.vyrbus.model.bean.Servicio;
import com.cystesoft.vyrbus.service.util.Constantes;
import com.cystesoft.vyrbus.service.util.Util;
import com.cystesoft.vyrbus.service.util.UtilData;
import com.cystesoft.vyrbus.view.ui.WndBase;

/**
 * @author Marco
 *
 */
public class WndRptGeneralVentas extends WndBase implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private Radio rdFecha;
	private Radio rdMes;
	private Radio rdAnual;
	private Datebox dtbxFecInicioBus;
	private Datebox dtbxFecFinBus;
	private Combobox cmbAgencias;

	private Listbox lsbxVentas;
//	private Listbox lsbxTarifaFA;
//	

	private Label lblHoraConsulta;
	private Label lblUsuarioConsulta;
	private Label lblFechaConsulta;
	
	private Tab tabVentas;
//	private Tab tabVentasAnual;

//	private Listheader lstheaFecha;
//	private Listheader lstheaItinerario;
//	private Checkbox ckbxDetalle;
//	private Intbox ibItinerario;

//	
//	private String ATTRIBUTE_CANAL_VENTA="1";
//	private String ATTRIBUTE_SERVICIO="2";
//	private String ATTRIBUTE_ORIGEN="3";
//	private String ATTRIBUTE_DESTINO="4";
//	private String ATTRIBUTE_HORA_PARTIDA="5";
//	private String ATTRIBUTE_PISO="6";
//	private String ATTRIBUTE_ZONABUS="7";
//	private String ATTRIBUTE_PRECIO="8";

//	private String ATTRIBUTE_ITINERARIO="8";
	Integer flag = 0;
	Integer ambosPisos=0;
	
	@Override
	public void initComponents() {
		// TODO Auto-generated method stub
		tabVentas = (Tab)this.getFellow("tabVentas");
//		tabVentasAnual = (Tab)this.getFellow("tabVentasAnual");
		
		cmbAgencias=(Combobox)this.getFellow("cmbAgencias");
		dtbxFecInicioBus=(Datebox)this.getFellow("dtbxFecInicioBus");
		dtbxFecFinBus=(Datebox)this.getFellow("dtbxFecFinBus");		
		
		lsbxVentas=(Listbox)this.getFellow("lsbxVentas");
		rdFecha = (Radio)this.getFellow("rdFecha"); 
		rdMes = (Radio)this.getFellow("rdMes"); 
		rdAnual = (Radio)this.getFellow("rdAnual"); 
		
		
		lblHoraConsulta = (Label)this.getFellow("lblHoraConsulta");
		lblFechaConsulta = (Label)this.getFellow("lblFechaConsulta");
		lblUsuarioConsulta = (Label)this.getFellow("lblUsuarioConsulta");
		
	}
	
	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.view.ui.WndBase#onCreate()
	 */
	@Override
	public void onCreate() throws Exception {
		// TODO Auto-generated method stub
		String fecha = Util.DatetoString(new Date(), "yyyyMMdd");
		
		//Para la busqueda
		UtilData.cargarDataCombo(cmbAgencias, Agencia.class,true);

		dtbxFecInicioBus.setConstraint("after "+fecha);
		dtbxFecFinBus.setConstraint("after "+fecha);
		dtbxFecInicioBus.setValue(new Date());
		dtbxFecFinBus.setValue(new Date());		
		
		inicializarControles(true);
		
		
		//Util.seleccionarValorItemCombo(TipoItinerario.class, cmbTipoItinerario, Constantes.ID_TIPITI_REGULAR);
		
/*		
		lsbxVentas.addEventListener(Events.ON_DOUBLE_CLICK,new EventListener<Event>() {
			@Override
			public void onEvent(Event event) throws Exception {
				if(!(btnCopiarTarifa.isDisabled()))
					onClick_btnCopiarTarifa();
			}
		});		
		
		lsbxTarifaFA.addEventListener(Events.ON_DOUBLE_CLICK,new EventListener<Event>() {
			@Override
			public void onEvent(Event event) throws Exception {
				if(!(btnCopiarTarifa.isDisabled()))
					onClick_btnCopiarTarifa();
			}
		});		
*/
		
		
		/*EVENTO ON_CHANGE DEL LA FECHA INICIO*/
		dtbxFecInicioBus.addEventListener(Events.ON_CHANGE, new EventListener<Event>() {
			@Override
			public void onEvent(Event event) throws Exception {
				String fecha = Util.DatetoString(dtbxFecInicioBus.getValue(), "yyyyMMdd");
				dtbxFecFinBus.setConstraint("after "+fecha);
				/*Asigna a fecha fin el valor de la fecha inicio*/
				dtbxFecFinBus.setValue(dtbxFecInicioBus.getValue());				
			}
		});
		
	}
	
	public void buscar() throws Exception{
		
	}
	

	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	private void inicializarControles(boolean estado){
//		Util.limpiarListbox(lsbxRutas);
		cmbAgencias.setSelectedIndex(0);
		
/*		
		String fecha = Util.DatetoString(new Date(), "yyyyMMdd");
		dtbxFecInicio.setConstraint("after "+fecha);
		dtbxFecFinal.setConstraint("after "+fecha);
		dtbxFecInicio.setValue(null);
	dtbxFecFinal.setValue(null);
*/	

	}

}
