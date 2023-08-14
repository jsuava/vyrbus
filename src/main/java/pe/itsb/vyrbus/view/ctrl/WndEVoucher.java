/**
 * Proyecto		: SISVYR
 * Sistema		: Sistema de Ventas y Reservas
 * Descripción	:
 * Autor		: José Avalos
 * Fecha		: 14/10/2013
 */
package pe.itsb.vyrbus.view.ctrl;

import org.zkoss.zk.ui.Executions;
import org.zkoss.zul.Window;

import pe.itsb.vyrbus.view.ui.WndBase;

/**
 * @author Jose
 *
 */
public class WndEVoucher extends WndBase {
	private static final long serialVersionUID = 1L;
	private Window oWindow = this;

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.view.ui.WndBase#onCreate()
	 */
	@Override
	public void onCreate() throws Exception {
//		Session session = getDesktop().getSession();
//        HttpSession httpSession = (HttpSession)session.getNativeSession();
//        httpSession.setAttribute("venta", getVentaPasaje());
//        httpSession.setAttribute("terminos", getLstTerminosVenta());
//        httpSession.setAttribute("concesionario", getConcesionario());

        Executions.sendRedirect("/servlet/EticketServlet");
	}

	public void onLoad() throws Exception{
		oWindow.setTitle("E-Voucher");
	}
}
