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

import pe.itsb.vyrbus.model.bean.Servicio;

/**
 *
 * @author jM
 * @since JDK1.6
 */
public interface ServicioDAO extends GenericDAO {
	public ArrayList<Servicio> buscarPorEstadoRegistro(String estado, String criterioOrden);
	public ArrayList<Servicio> buscarPorX(TreeMap<String, Object> criteriosBusqueda, List<String> criteriosOrdenar);
	public Servicio buscarPorId(Long id);
	public void guardar(Servicio servicio);
	public void actualizar(Servicio servicio);
	public void inactivar(Long id);
	/**
	 * Realiza la busqueda de los Servicios de acuerdo a lso criterios enviados
	 * @param campo				: Campo que debera cumplir con los criterios enviados.
	 * @param criterios			: Identificadores de los servicios separados por comas.
	 * @param criteriosOrdenar	: Criterios a utilizar para ordenar la informacion.
	 * @param estadoRegistro	: Estado de los registros.
	 * @return Lista de Servicios.
	 */
	public List<Servicio> buscarPorX(String campo, Object[] criterios, List<String> criteriosOrdenar, String estadoRegistro) throws Exception;
}