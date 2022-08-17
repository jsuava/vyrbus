package com.cystesoft.vyrbus.service.business;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

import com.cystesoft.vyrbus.model.bean.Pasajero;

public interface PasajeroManager {
	public ArrayList<Pasajero> buscarPorEstadoRegistro(String estado, String criterioOrden)throws Exception;
	public ArrayList<Pasajero> buscarPorX(TreeMap<String, Object> criteriosBusqueda, List<String> criteriosOrdenar)throws Exception;
	public Pasajero buscarPorId(Long id)throws Exception;
	public void guardar(Pasajero pasajero)throws Exception;
	public void actualizar(Pasajero pasajero)throws Exception;
	public void inactivar(Long id)throws Exception;
	/**
	 * Realiza la busqueda del pasajero con el indice FullText.
	 * @param nombres	: Array con los nombres y apellidos del pasajero a buscar.
	 * @return Lista de coincidencias.
	 * @throws Exception
	 */
	public ArrayList<Pasajero> buscarPorFullTextIndex(String[] nombres)throws Exception;
}
