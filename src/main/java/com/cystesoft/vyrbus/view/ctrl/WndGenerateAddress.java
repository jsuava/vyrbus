/**
 * Proyecto		: SISVYR
 * Sistema		: Sistema de Ventas y Reservas
 * Descripción	:
 * Autor		: José Sullo Avalos
 * Fecha		: 09/12/2013
 */
package com.cystesoft.vyrbus.view.ctrl;

import com.cystesoft.vyrbus.view.ui.WndBase;

/**
 * @author Jose
 *
 */
public class WndGenerateAddress extends WndBase {
	private static final long serialVersionUID = 1L;
	@Override
	public void onCreate(){
		this.getDesktop().getSession().setAttribute("validatePath", true);
	}
}
