/**
 * Proyecto		: SISVYR
 * Sistema		: Sistema de Ventas y Reservas
 * Descripción	:
 * Autor		: José Abanto
 * Fecha		: 01/08/2013
 */
package pe.itsb.vyrbus.service.business;

import pe.itsb.vyrbus.model.bean.OcupacionAsientosDesbloqueados;

/**
 * @author JABANTO
 *
 */
public interface OcupacionAsientosDesbloqueadosManager {

	/**
	 * Guarda la ocupacion de los asientos desbloqueados.
	 * @param ocupacionAsientosDesbloqueados : Objeto
	 * @throws Exception
	 */
	public void guardar(OcupacionAsientosDesbloqueados ocupacionAsientosDesbloqueados) throws Exception;

}
