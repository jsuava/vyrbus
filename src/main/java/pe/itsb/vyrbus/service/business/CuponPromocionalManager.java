/**
 * Proyecto		: SISVYR
 * Sistema		: Sistema de Ventas y Reservas
 * Descripción	: 
 * Autor		: José Abanto
 * Fecha		: 23/04/2019
 * Hora			: 19:43:17
 */
package pe.itsb.vyrbus.service.business;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

import pe.itsb.vyrbus.model.bean.CuponPromocional;


/**
 * @author abant
 *
 */
public interface CuponPromocionalManager {
	/**
	 * 
	 * @param estado
	 * @param criterioOrden
	 * @return
	 * @throws Exception
	 */
	public ArrayList<CuponPromocional> buscarPorEstadoRegistro(String estado, String criterioOrden)throws Exception;
	/**
	 * 
	 * @param criteriosBusqueda
	 * @param criteriosOrdenar
	 * @return
	 * @throws Exception
	 */
	public ArrayList<CuponPromocional> buscarPorX(TreeMap<String, Object> criteriosBusqueda, List<String> criteriosOrdenar)throws Exception;
	/**
	 * 
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public CuponPromocional buscarPorId(Long id) throws Exception;
	/**
	 * 
	 * @param codigoPromocional
	 * @throws Exception
	 */
	public void guardar(CuponPromocional codigoPromocional)throws Exception;
	/**
	 * 
	 * @param codigoPromocional
	 * @throws Exception
	 */
	public void actualizar(CuponPromocional codigoPromocional) throws Exception;
	/**
	 * Realiza la busqueda de los codigos promocionales
	 * @param fechaInicio	: Fecha inicio de la busqueda
	 * @param fechaFina		: Fecha fin de la busqueda
	 * @param denominacion	: Denominacion
	 * @return	Lista de resultados.
	 * @throws Exception
	 */
	public List<CuponPromocional> buscar(String fechaInicio, String fechaFina, String denominacion, String codigo)throws Exception;
}
