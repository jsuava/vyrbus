package pe.itsb.vyrbus.service.business;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

import pe.itsb.vyrbus.model.bean.Descuento;

public interface DescuentoManager {
	/**
	 * 
	 * @param estado
	 * @param criterioOrden
	 * @return
	 * @throws Exception
	 */
	public ArrayList<Descuento> buscarPorEstadoRegistro(String estado, String criterioOrden)throws Exception;
	/**
	 * 
	 * @param criteriosBusqueda
	 * @param criteriosOrdenar
	 * @return
	 * @throws Exception
	 */
	public ArrayList<Descuento> buscarPorX (TreeMap<String, Object> criteriosBusqueda, List<String> criteriosOrdenar)throws Exception;
	/**
	 * 
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public Descuento buscarPorId (Long id)throws Exception;
	/**
	 * 
	 * @param autoescuento)thro* @throws Exception
	 */
	public void guardar(Descuento descuento)throws Exception;
	/**
	 * 
	 * @param descuento
	 * @throws Exception
	 */
	public void actualizar(Descuento descuento)throws Exception;
	
}
