/**
 * Proyecto		: SISVYR
 * Sistema		: Sistema de Ventas y Reservas
 * Descripción	:
 * Autor		: José Sullo Avalos
 * Fecha		: 09/09/2013
 */
package pe.itsb.vyrbus.model.dao;

import pe.itsb.vyrbus.model.bean.HistoricoControlEspecieValorada;

/**
 * @author JABANTO
 *
 */
public interface HistoricoControlEspecieValoradaDAO extends GenericDAO{

	/**
	 * Guarda historico del control especie valorada
	 * @param historicoControlEspecieValorada	: objeto HistoricoControlEspecieValorada
	 * @throws Exception
	 */
	public void guardar(HistoricoControlEspecieValorada historicoControlEspecieValorada) throws Exception;

}
