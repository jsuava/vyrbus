/**
 * Proyecto		: VYRBUS
 * Sistema		: Sistema de Ventas y Reservas
 * Descripción	:
 * Autor		: José Avalos
 * Fecha		: 22 jul. 2023
 * Hora			: 22:17:34
 */
package pe.itsb.vyrbus.model.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

import pe.itsb.vyrbus.model.bean.Empresa;

/**
 * @author Jose
 *
 */
public interface EmpresaDAO extends GenericDAO {
	/**
	 * Busca la empresa por estado de registro
	 * @param estado
	 * @param criterioOrden
	 * @return
	 */
	public ArrayList<Empresa> buscarPorEstadoRegistro(String estado, String criterioOrden);
	/**
	 * Busca la empresa por criterios diversos
	 * @param criteriosBusqueda
	 * @param criteriosOrdenar
	 * @return
	 */
	public ArrayList<Empresa> buscarPorX(TreeMap<String, Object> criteriosBusqueda, List<String> criteriosOrdenar);
	/**
	 * Busca la empresa por ID
	 * @param id
	 * @return
	 */
	public Empresa buscarPorId(Long id);
	/**
	 * Guarda el objeto empresa
	 * @param empresa
	 */
	public void guardar(Empresa empresa);
	/**
	 * Actualiza el objeto empresa
	 * @param empresa
	 */
	public void actualizar(Empresa empresa);
	/**
	 * Incatica el objeto empresa
	 * @param id
	 */
	public void inactivar(Long id);
}
