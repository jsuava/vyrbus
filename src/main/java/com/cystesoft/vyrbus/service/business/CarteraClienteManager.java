package com.cystesoft.vyrbus.service.business;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

import com.cystesoft.vyrbus.model.bean.CarteraCliente;

/**
 *
 * @author JABANTO
 *
 */
public interface CarteraClienteManager {

	/**
	 * Buscar Cartera de clientes por estado
	 * @param estado		: Estado a consultar
	 * @param criterioOrden	: criterios para el ordenamiento de datos
	 * @return
	 */
	public ArrayList<CarteraCliente> buscarPorEstadoRegistro(String estado, String criterioOrden);

	/**
	 * Buscar cartera de clientes a travez de un array de criterios.
	 * @param criteriosBusqueda	: array de criterios para la busqueda.
	 * @param criteriosOrdenar	: arrey de criterios para el ordenamiento de datos
	 * @return
	 */
	public ArrayList<CarteraCliente> buscarPorX(TreeMap<String, Object> criteriosBusqueda, List<String> criteriosOrdenar);
	/**
	 * Guarda la asignacion de cartera de clientes a un funcionario
	 * @param carteraCliente : Class carteraCliente
	 */
	public void guardar(CarteraCliente carteraCliente);

	/**
	 * Actualiza cartera de Cliente
	 * @param carteraCliente	: Class carteraCliente
	 * @return
	 */
	public void actualizar (CarteraCliente  carteraCliente);

	/**
	 * Incativa cartera de cliente
	 * @param id : Identificador de la cartera de cliente
	 */
	public void inactivar(Long id);

	/**
	 * Buscar clientes signados a una determinada cartera
	 * @param idFuncionario	: Identificador del Funcionario
	 * @param idCliente		: Identificador del Cliente
	 * @return
	 */
	public List<CarteraCliente> buscarClientesCartera(Integer idFuncionario, Long idCliente);
	/**
	 * Realiza la busquera por su identificador de la entidad
	 * @param id	: Identificador.
	 * @return CarteraCliente
	 * @throws Exception
	 */
	public CarteraCliente buscarPorId(Long id)throws Exception;

}
