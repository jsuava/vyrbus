/**
 * Proyecto		: SISVYR
 * Sistema		: Sistema de Ventas y Reservas
 * Descripción	:
 * Autor		: José Avalos
 * Fecha		: 09/12/2013
 */
package pe.itsb.vyrbus.view.ctrl;

import pe.itsb.vyrbus.view.ui.WndBase;

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
