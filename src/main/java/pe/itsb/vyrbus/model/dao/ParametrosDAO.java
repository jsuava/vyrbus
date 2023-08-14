/**
 * Proyecto		: SISVYR
 * Sistema		: Sistema de Ventas y Reservas
 * Descripción	:
 * Autor		: José Sullo Avalos
 * Fecha		: 14/05/2013
 */
package pe.itsb.vyrbus.model.dao;

import pe.itsb.vyrbus.model.bean.Parametros;


/**
 * @author JABANTO
 *
 */
public interface ParametrosDAO extends GenericDAO {
	/**
	 * Busca por el estado registro
	 * @param estado	: estado a considerar
	 * @return
	 */
	public Parametros buscarPorEstadoRegistro(String estado);

	/**
	 * Guarda Parámetros
	 * @param parametros
	 * @throws Exception
	 */
	public void guardar(Parametros parametros) throws Exception;

	/**
	 * Actualizar parámetros
	 * @param parametros
	 * @throws Exception
	 */
	public void actualizar(Parametros parametros) throws Exception;

}
