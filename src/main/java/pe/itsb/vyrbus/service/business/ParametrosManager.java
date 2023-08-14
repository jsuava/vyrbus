/**
 * Proyecto		: SISVYR
 * Sistema		: Sistema de Ventas y Reservas
 * Descripción	:
 * Autor		: José Avalos
 * Fecha		: 14/05/2013
 */
package pe.itsb.vyrbus.service.business;

import org.springframework.transaction.annotation.Transactional;

import pe.itsb.vyrbus.model.bean.Parametros;

/**
 * @author JABANTO
 *
 */
public interface ParametrosManager  {
	/**
	 * Busca por el estado registro
	 * @param estado	: estado a considerar
	 * @return
	 */
	public Parametros buscarPorEstadoRegistro(String estado);


	/**
	 * Guarda Parametros
	 * @param parametros
	 * @throws Exception
	 */
	@Transactional
	public void guardar(Parametros parametros) throws Exception;

	/**
	 * Actualizar parámetros
	 * @param parametros
	 * @throws Exception
	 */
	public void actualizar(Parametros parametros) throws Exception;

}
