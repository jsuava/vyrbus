package com.cystesoft.vyrbus.model.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

import com.cystesoft.vyrbus.model.bean.LineaContadoCliente;


/**
 *
 * @author JABANTO
 *
 */
public interface LineaContadoClienteDAO extends GenericDAO {
	/**
	 * Buscar lineaContadoCliente por estado Registro
	 * @param estado		:Estado registro
	 * @param criterioOrden	: cadena de criterios para el orden de la Data recuperada.
	 * @return
	 */
	public ArrayList<LineaContadoCliente> buscarPorEstadoRegistro(String estado, String criterioOrden);

	/**
	 * Buscar lineaContadoCliente según un array de criterios
	 * @param criteriosBusqueda : Array de criterios para la busqueda
	 * @param criteriosOrdenar  : Lista de criterios para el orden de la Data recuperada.
	 * @return
	 */
	public ArrayList<LineaContadoCliente> buscarPorX(TreeMap<String, Object> criteriosBusqueda, List<String> criteriosOrdenar);

	/**
	 * Buscar lineaContadoCliente por ID
	 * @param id :Identificador del lineaContadoCliente
	 * @return
	 */
	public LineaContadoCliente buscarPorId(Long id);

	/**
	 * Guarda lineaContadoCliente
	 * @param lineaContadoCliente: class LineaContadoCliente
	 */
	public void guardar(LineaContadoCliente lineaContadoCliente);

	/**
	 * Actualiza LineaContadoCliente
	 * @param LineaContadoCliente: Class LineaContadoCliente
	 */
	public void actualizar(LineaContadoCliente lineaContadoCliente);

	/**
	 * Inactivar LineaContadoCliente
	 * @param id : Identificador de LineaContadoCliente
	 */
	public void inactivar(Long id);

	/**
	 * Verifica si el Cliente cuenta con descuento corporativo.
	 * @param idCliente : Identificador del Cliente.
	 * @return
	 */
	public LineaContadoCliente validaDescuentoCliente(Long idCliente);

}
