package com.cystesoft.vyrbus.service.business;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

import com.cystesoft.vyrbus.model.bean.Concesionario;

public interface ConcesionarioManager {
	/**
	 * Realiza la busqueda de concesionarios por el estado del registro.
	 * @param estado		: Estado de registro ACTIVO O INACTIVO.
	 * @param criterioOrden	: Criterio a utilizar para el ordenamiento de la informacion.
	 * @return ArrayList con los datos encontrados
	 * @throws Exception
	 */
	public ArrayList<Concesionario> buscarPorEstadoRegistro(String estado, String criterioOrden)throws Exception;
	/**
	 * Realiza la busqueda del concesionario por los criterios x.
	 * @param criteriosBusqueda	: Criterios cons los que se realizara la busqueda.
	 * @param criteriosOrdenar	: Criterios a utilizar para ordenar la busqueda.
	 * @return ArrayList con los datos encontrados.
	 * @throws Exception
	 */
	public ArrayList<Concesionario> buscarPorX(TreeMap<String, Object> criteriosBusqueda, List<String> criteriosOrdenar)throws Exception;
	/**
	 * Realiza la busqueda de concesionarios por id.
	 * @param id	: Identificador del concesionario.
	 * @return Concesionario.
	 * @throws Exception
	 */
	public Concesionario buscarPorId(Long id)throws Exception;
	/**
	 * Realiza el guardado de la informacion del concesionario. 
	 * @param concesionario	: Objeto concesionario que se desea guardar.
	 * @throws Exception
	 */
	public int guardar(Concesionario concesionario)throws Exception;
	/**
	 * Realiza la actualizacion de los datos del concesionario.
	 * @param concesionario	: Objeto concesionario que se desea actualizar.
	 * @throws Exception
	 */
	public void actualizar(Concesionario concesionario)throws Exception;
	/**
	 * Realiza la inactivacion del concesionario.
	 * @param id	: Identificador del concesionario a inactivar.
	 * @throws Exception
	 */
	public void inactivar(Long id)throws Exception;
	
	public List<Concesionario> buscarPorX(String campo, Object[] criterios, List<String> criteriosOrdenar, String estadoRegistro) throws Exception;
}
