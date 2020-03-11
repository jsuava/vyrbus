package com.cystesoft.vyrbus.model.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

import com.cystesoft.vyrbus.model.bean.DetalleItinerario;

public interface DetalleItinerarioDAO extends GenericDAO {
	/**
	 * 
	 * @param idItinerario
	 * @throws Exception
	 */
	public void delete (Long idItinerario) throws Exception;
	
	/**
	 * 
	 * @param criteriosBusqueda
	 * @param criteriosOrdenar
	 * @return
	 */
	public ArrayList<DetalleItinerario> buscarPorX(TreeMap<String, Object> criteriosBusqueda, List<String> criteriosOrdenar);
	/**
	 * Realiza la busqueda del objeto por el ID
	 * @param id	: Identificador unico del objeto.
	 * @return DetalleIitnerario.
	 * @throws Exception
	 */
	public DetalleItinerario buscarPorId(Long idDetalleItinerario)throws Exception;
}
