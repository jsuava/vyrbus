/**
 * Proyecto		: SISVYR
 * Sistema		: Sistema de Ventas y Reservas
 * Descripción	:
 * Autor		: José Abanto
 * Fecha		: 21/02/2017
 * Hora			: 10:09:08
 */
package pe.itsb.vyrbus.model.bean;

import java.io.Serializable;

/**
 * @author Jose Abanto
 *
 */
public class ComprobantesBloqueadosID extends GenericBean implements Serializable{

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	private long idVentaPasaje;
	/**
	 * @return the idVentaPasaje
	 */
	public long getIdVentaPasaje() {
		return idVentaPasaje;
	}
	/**
	 * @param idVentaPasaje the idVentaPasaje to set
	 */
	public void setIdVentaPasaje(long idVentaPasaje) {
		this.idVentaPasaje = idVentaPasaje;
	}


}
