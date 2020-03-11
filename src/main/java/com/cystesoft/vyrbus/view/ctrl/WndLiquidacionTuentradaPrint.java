/**
 * Proyecto		: SISVYR
 * Sistema		: Sistema de Ventas y Reservas
 * Descripción	: 
 * Autor		: José Abanto
 * Fecha		: 22/08/2015
 * Hora			: 08:20:39
 */
package com.cystesoft.vyrbus.view.ctrl;

import org.zkoss.zk.ui.Executions;
import org.zkoss.zul.Window;

import com.cystesoft.vyrbus.view.ui.WndBase;

/**
 * @author jabanto
 *
 */
public class WndLiquidacionTuentradaPrint extends WndBase {
	private static final long serialVersionUID = 1L;
	private Window oWindow = this; 
	
	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.view.ui.WndBase#onCreate()
	 */
	@Override
	public void onCreate() throws Exception {
        Executions.sendRedirect("/servlet/LiquidacionTuEntradaServlet");
	}
	
	public void onLoad() throws Exception{
		oWindow.setTitle("LiquidacionTuentrada");
	}
}