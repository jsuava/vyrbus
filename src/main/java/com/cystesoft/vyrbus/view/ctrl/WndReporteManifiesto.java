/**
 * Proyecto		: SISVYR
 * Sistema		: Sistema de Ventas y Reservas
 * Descripción	:
 * Autor		: José Abanto
 * Fecha		: 30/09/2014
 * Hora			: 15:33:54
 */
package com.cystesoft.vyrbus.view.ctrl;

import org.zkoss.zk.ui.Executions;

import com.cystesoft.vyrbus.view.ui.WndBase;

/**
 * @author JABANTO
 *
 */
public class WndReporteManifiesto extends WndBase{
	private static final long serialVersionUID = 1L;

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.view.ui.WndBase#onCreate()
	 */
	@Override
	public void onCreate() throws Exception {
        Executions.sendRedirect("/servlet/ReporteManifiestoServlet");
	}

	public void onLoad() throws Exception{
	}
}
