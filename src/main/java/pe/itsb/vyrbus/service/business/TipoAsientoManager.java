/**
 * Proyecto		: SISVYR
 * Sistema		: Sistema de Ventas y Reservas
 * Descripción	:
 * Autor		: José Abanto
 * Fecha		: 11/07/2016
 * Hora			: 17:30:52
 */
package pe.itsb.vyrbus.service.business;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

import pe.itsb.vyrbus.model.bean.TipoAsiento;

/**
 * @author jabanto
 *
 */
public interface TipoAsientoManager {
	/**
	 * Realiza la busqueda por el estado del registro
	 * @param estado : Estado del registro
	 * @param criterioOrden
	 * @return
	 */
	public ArrayList<TipoAsiento> buscarPorEstadoRegistro(String estado, String criterioOrden)throws Exception;
	/**
	 * Realiza la busqueda segun los criterios enviados
	 * @param criteriosBusqueda	: Criteros de busqueda.
	 * @param criteriosOrdenar	: Criterios para el orden de la informacion.
	 * @return
	 */
	public ArrayList<TipoAsiento> buscarPorX (TreeMap<String, Object> criteriosBusqueda, List<String> criteriosOrdenar)throws Exception;
	/**
	 * Realiza la busqueda por su identificador de la entidad.
	 * @param id : Identificador.
	 * @return
	 */
	public TipoAsiento buscarPorId (Long id)throws Exception;
	/**
	 * Guarda una nueva instancia de un objeto
	 * @param tipoAsiento
	 */
	public void guardar(TipoAsiento tipoAsiento)throws Exception;
	/**
	 *Actualiza una instancia de un objeto
	 * @param tipoAsiento
	 */
	public void actualizar(TipoAsiento tipoAsiento)throws Exception;
}
