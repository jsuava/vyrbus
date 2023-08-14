/**
 * Proyecto		: SISVYR
 * Sistema		: Sistema de Ventas y Reservas
 * Descripción	:
 * Autor		: jM
 * Fecha		: 04/05/2012
 */
package pe.itsb.vyrbus.model.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

import pe.itsb.vyrbus.model.bean.DetalleEquipaje;

/**
 *
 * @author jM
 * @since JDK1.6
 */
public interface DetalleEquipajeDAO extends GenericDAO {
	/**
	 *
	 * @param estado
	 * @param criterioOrden
	 * @return
	 */
	public ArrayList<DetalleEquipaje> buscarPorEstadoRegistro(String estado, String criterioOrden);
	/**
	 *
	 * @param criteriosBusqueda
	 * @param criteriosOrdenar
	 * @return
	 */
	public ArrayList<DetalleEquipaje> buscarPorX(TreeMap<String, Object> criteriosBusqueda, List<String> criteriosOrdenar);
	/**
	 *
	 * @param id
	 * @return
	 */
	public DetalleEquipaje buscarPorId(Long id);
	/**
	 *
	 * @param detalleEquipaje
	 */
	public void guardar(DetalleEquipaje detalleEquipaje);
	/**
	 *
	 * @param detalleEquipaje
	 */
	public void actualizar(DetalleEquipaje detalleEquipaje);
	/**
	 * Realiza la busqueda del manifiesto de pasajeros
	 * @param itinerarioId : Identificador del Itinerario.
	 * @param agenciaId		: Identificador de la Agencia.
	 * @return
	 * @throws Exception
	 */
	public List<DetalleEquipaje> buscarManifiestoEquipaje(Long itinerarioId, Integer agenciaId)throws Exception;

}