package com.cystesoft.vyrbus.view.ctrl;

import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;

import org.zkoss.zul.Button;
import org.zkoss.zul.Datebox;

import com.cystesoft.vyrbus.service.util.Constantes;
import com.cystesoft.vyrbus.service.util.MyTime;
import com.cystesoft.vyrbus.view.ui.WndBase;

/**
 * 
 * @author JABANTO
 *
 */
public class WndAperturaLiquidacionOficina extends WndBase {
	private static final long serialVersionUID = -3839632424259561462L;
		
	private Datebox dbFechaLiquidacion;
	private Button btnAperturar;
	private Button btnCancelar;
	
	/*
	 * (non-Javadoc)
	 * @see com.tepsa.sisvyr.view.ui.WndBase#initComponents()
	 */
	@Override
	public void initComponents(){
		dbFechaLiquidacion = (Datebox) this.getFellow("dbFechaLiquidacion");
		btnAperturar = (Button) this.getFellow("btnAperturar");
		btnCancelar = (Button) this.getFellow("btnCancelar");
		
		//Aperturar Liquidación
		btnAperturar.addEventListener(Events.ON_CLICK, new EventListener<Event>() {
			@Override
			public void onEvent(Event event) throws Exception {
				
				closeTabWindow();
			}
		});
		
		//Cancelar
		btnCancelar.addEventListener(Events.ON_CLICK, new EventListener<Event>() {
			@Override
			public void onEvent(Event event) throws Exception {
				closeTabWindow();
			}
		});
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.tepsa.sisvyr.view.ui.WndBase#onCreate()
	 */
	@Override
	public void onCreate() throws Exception{
		MyTime myTime = new MyTime();
		dbFechaLiquidacion.setValue(Constantes.FORMAT_DATE.parse(myTime.dateServer()));		
	}

}
