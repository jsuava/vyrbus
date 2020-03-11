package com.cystesoft.vyrbus.service.business;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

import com.cystesoft.vyrbus.model.bean.DetalleItinerario;
/**
 * 
 * @author JABANTO
 *
 */
public interface DetalleItinerarioManager {
	
	
	/**
	 * 
	 * @param criteriosBusqueda	: parametros para la busqueda.
	 * @param criteriosOrdenar	: parametros para el orden de los datos
	 * @return Array con el resultado de la busqueda
	 * @throws Exception
	 */
	public ArrayList<DetalleItinerario> buscarPorX(TreeMap<String, Object> criteriosBusqueda, List<String> criteriosOrdenar)throws Exception;
	
	/**
	 * 
	 * @param odetalleItinerario
	 * @throws Exception
	 */
	public void guardar(DetalleItinerario odetalleItinerario)throws Exception;
	/**
	 * 
	 * @param detalleItinerario
	 * @throws Exception
	 */
	public void actualizar(DetalleItinerario detalleItinerario)throws Exception;
	/**
	 * 
	 * @param id
	 * @throws Exception
	 */
	public void inactivar(Long id)throws Exception;
	/**
	 * 
	 * @param idItinerario
	 * @throws Exception
	 */
	public void delete (Long idItinerario) throws Exception;
	/**
	 * Realiza la busqueda del objeto por el ID
	 * @param id	: Identificador unico del objeto.
	 * @return DetalleIitnerario.
	 * @throws Exception
	 */
	public DetalleItinerario buscarPorId(Long idDetalleItinerario)throws Exception;
	
}
