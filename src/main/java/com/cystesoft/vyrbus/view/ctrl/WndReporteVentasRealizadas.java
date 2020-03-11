/**
 * Proyecto		: SISVYR
 * Sistema		: Sistema de Ventas y Reservas
 * Descripción	: 
 * Autor		: José Sullo Avalos
 * Fecha		: 14/10/2013
 */
package com.cystesoft.vyrbus.view.ctrl;

import org.zkoss.zk.ui.Executions;

import com.cystesoft.vyrbus.view.ui.WndBase;

/**
 * @author Jose
 *
 */
public class WndReporteVentasRealizadas extends WndBase {
	private static final long serialVersionUID = 1L;
	
	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.view.ui.WndBase#onCreate()
	 */
	@Override
	public void onCreate() throws Exception {
        Executions.sendRedirect("/servlet/DetalladoVentasServlet");
	}
	
	public void onLoad() throws Exception{
	}
}
