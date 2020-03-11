/**
 * Proyecto		: SISVYR
 * Sistema		: Sistema de Ventas y Reservas
 * Descripción	: 
 * Autor		: José Abanto
 * Fecha		: 08/09/2016
 * Hora			: 14:27:24
 */
package com.cystesoft.vyrbus.model.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

import com.cystesoft.vyrbus.model.bean.Usuario;
import com.cystesoft.vyrbus.model.bean.VentaPool;

/**
 * @author jabanto
 *
 */
public interface VentaPoolDAO extends GenericDAO{
	/**
	 * 
	 * @param estado
	 * @param criterioOrden
	 * @return
	 */
	public ArrayList<VentaPool> buscarPorEstadoRegistro(String estado, String criterioOrden);
	/**
	 * 
	 * @param criteriosBusqueda
	 * @param criteriosOrdenar
	 * @return
	 */
	public ArrayList<VentaPool> buscarPorX(TreeMap<String, Object> criteriosBusqueda, List<String> criteriosOrdenar);
	/**
	 * 
	 * @param id
	 * @return
	 */
	public VentaPool buscarPorId(Long id);
	/**
	 * 
	 * @param ventaPool
	 */
	public void guardar(VentaPool ventaPool);
	/**
	 * 
	 * @param VentaPool
	 */
	public void actualizar(VentaPool VentaPool);
	/**
	 * 
	 * @param id
	 */
	public void inactivar(Long id);
	/**
	 *Realiza la busqueda de los usuarios que han realizado ventas  
	 * @param idAgencia	: Identificador de la agencia.
	 * @param fechaInicio : Fecha de inicio de la busqueda.
	 * @param fechaFin	: Fecha fin de la busqueda.
	 * @return
	 * @throws Exception
	 */
	public List<Usuario> buscarUsuarioPorAgencia(Integer idAgencia, String fechaInicio, String fechaFin)throws Exception;
	/**
	 * Raliza la busqueda de las venta
	 * @param fechaInicio	: Fecha inicio de la busqueda.
	 * @param fechaFin	: Fecha fin de la busqueda.
	 * @param agenciaId	: Identificador de la agencia.
	 * @param usuarioId	: Identificador del usuario.
	 * @return Lista de resultados
	 * @throws Exception
	 */
	public List<VentaPool> buscarVentas(String fechaInicio, String fechaFin, Integer agenciaId, Integer usuarioId)throws Exception;

}
