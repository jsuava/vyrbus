/**
 * Proyecto		: SISVYR
 * Sistema		: Sistema de Ventas y Reservas
 * Descripción	:
 * Autor		: José
 * Fecha		: 25/05/2013
 */
package pe.itsb.vyrbus.service.business;

import pe.itsb.vyrbus.model.bean.HistoricoMembresia;

/**
 * @author JABANTO
 *
 */
public interface HistoricoMembresiaManager {
	/**
	 * Guarda historico membresia.
	 * @param historicoMembresia
	 * @throws Exception
	 */
	public void guardar(HistoricoMembresia historicoMembresia) throws Exception;
}
