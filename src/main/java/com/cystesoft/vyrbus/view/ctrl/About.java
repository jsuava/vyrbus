/**
 * Proyecto		: SISVYR
 * Sistema		: Sistema de Ventas y Reservas
 * Descripción	: 
 * Autor		: José Sullo Avalos
 * Fecha		: 18/07/2013
 */
package com.cystesoft.vyrbus.view.ctrl;

import org.zkoss.zul.Label;

import com.cystesoft.vyrbus.service.util.Constantes;
import com.cystesoft.vyrbus.view.ui.WndBase;

/**
 * @author Jose
 *
 */
public class About extends WndBase {
	private Label lblVersion;
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.view.ui.WndBase#onCreate()
	 */
	@Override
	public void onCreate() throws Exception {
		// TODO Auto-generated method stub
		super.onCreate();
		lblVersion = (Label)this.getFellow("lblVersion");
		lblVersion.setValue(Constantes.SYSTEM_VERSION);
	}
	

}
