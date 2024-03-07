package pe.itsb.vyrbus.service.business;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

import pe.itsb.vyrbus.model.bean.AutorizadorDescuento;

public interface AutorizadorDescuentoManager {
	/**
	 * 
	 * @param estado
	 * @param criterioOrden
	 * @return
	 * @throws Exception
	 */
	public ArrayList<AutorizadorDescuento> buscarPorEstadoRegistro(String estado, String criterioOrden)throws Exception;
	/**
	 * 
	 * @param criteriosBusqueda
	 * @param criteriosOrdenar
	 * @return
	 * @throws Exception
	 */
	public ArrayList<AutorizadorDescuento> buscarPorX (TreeMap<String, Object> criteriosBusqueda, List<String> criteriosOrdenar)throws Exception;
	/**
	 * 
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public AutorizadorDescuento buscarPorId (Long id)throws Exception;
	/**
	 * 
	 * @param autorizadorDescuento
	 * @throws Exception
	 */
	public void guardar(AutorizadorDescuento autorizadorDescuento)throws Exception;
	/**
	 * 
	 * @param autorizadorDescuento
	 * @throws Exception
	 */
	public void actualizar(AutorizadorDescuento autorizadorDescuento)throws Exception;
	
}
