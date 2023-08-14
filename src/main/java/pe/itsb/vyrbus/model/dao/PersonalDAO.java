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

import pe.itsb.vyrbus.model.bean.Personal;

/**
 *
 * @author jM
 * @since JDK1.6
 */
public interface PersonalDAO extends GenericDAO {
	/**
	 *
	 * @param estado
	 * @param criterioOrden
	 * @return
	 */
	public ArrayList<Personal> buscarPorEstadoRegistro(String estado, String criterioOrden);

	/**
	 *
	 * @param criteriosBusqueda
	 * @param criteriosOrdenar
	 * @return
	 */
	public ArrayList<Personal> buscarPorX(TreeMap<String, Object> criteriosBusqueda, List<String> criteriosOrdenar);

	/**
	 *
	 * @param id
	 * @return
	 */
	public Personal buscarPorId(Long id);

	/**
	 *
	 * @param personal
	 */
	public void guardar(Personal personal);

	/**
	 *
	 * @param personal
	 */
	public void actualizar(Personal personal);

	/**
	 *
	 * @param id
	 */
	public void inactivar(Long id);

	/**
	 * Buscar E-Mails para el envio del alertas.
	 * @param iDsRol : Identificador del Rol
	 * @return
	 * @throws Exception
	 */
	public String buscarMailsXRols(String iDsRol)throws Exception;
}