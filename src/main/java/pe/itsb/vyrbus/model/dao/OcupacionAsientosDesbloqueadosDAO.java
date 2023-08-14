/**
 * Proyecto		: SISVYR
 * Sistema		: Sistema de Ventas y Reservas
 * Descripción	:
 * Autor		: José Sullo Avalos
 * Fecha		: 01/08/2013
 */
package pe.itsb.vyrbus.model.dao;

import pe.itsb.vyrbus.model.bean.OcupacionAsientosDesbloqueados;

/**
 * @author JABANTO
 *
 */
public interface OcupacionAsientosDesbloqueadosDAO extends GenericDAO {

	/**
	 * Guarda la ocupacion de los asientos desbloqueados.
	 * @param ocupacionAsientosDesbloqueados : Objeto
	 * @throws Exception
	 */
	public void guardar(OcupacionAsientosDesbloqueados ocupacionAsientosDesbloqueados) throws Exception;
}
