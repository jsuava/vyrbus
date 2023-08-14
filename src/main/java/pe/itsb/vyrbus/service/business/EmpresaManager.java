/**
 * Proyecto		: SISVYR
 * Sistema		: Sistema de Ventas y Reservas
 * Descripción	:
 * Autor		: José Abanto
 * Fecha		: 25 jul. 2023
 * Hora			: 00:12:34
 */
package pe.itsb.vyrbus.service.business;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

import pe.itsb.vyrbus.model.bean.Empresa;

/**
 * @author Jose
 *
 */
public interface EmpresaManager {
	/**
	 * Busca la empresa por estado de registro
	 * @param estado
	 * @param criterioOrden
	 * @return
	 */
	public ArrayList<Empresa> buscarPorEstadoRegistro(String estado, String criterioOrden) throws Exception;
	/**
	 * Busca la empresa por criterios diversos
	 * @param criteriosBusqueda
	 * @param criteriosOrdenar
	 * @return
	 */
	public ArrayList<Empresa> buscarPorX(TreeMap<String, Object> criteriosBusqueda, List<String> criteriosOrdenar) throws Exception;
	/**
	 * Busca la empresa por ID
	 * @param id
	 * @return
	 */
	public Empresa buscarPorId(Long id) throws Exception;
	/**
	 * Guarda el objeto empresa
	 * @param empresa
	 */
	public void guardar(Empresa empresa) throws Exception;
	/**
	 * Actualiza el objeto empresa
	 * @param empresa
	 */
	public void actualizar(Empresa empresa) throws Exception;
	/**
	 * Incatica el objeto empresa
	 * @param id
	 */
	public void inactivar(Long id) throws Exception;
}
