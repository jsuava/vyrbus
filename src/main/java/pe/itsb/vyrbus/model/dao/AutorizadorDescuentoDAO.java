/**
 * Proyecto		: SISVYR
 * Sistema		: Sistema de Ventas y Reservas
 * Descripci�n	:
 * Autor		: JA
 * Fecha		: 13/08/2012
 */
package pe.itsb.vyrbus.model.dao;
import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

import pe.itsb.vyrbus.model.bean.AutorizadorDescuento;

/**
 *
 * @author JA
 * @since JDK1.6
 */
public interface AutorizadorDescuentoDAO {
	/**
	 * 
	 * @param estado
	 * @param criterioOrden
	 * @return
	 */
	public ArrayList<AutorizadorDescuento> buscarPorEstadoRegistro(String estado, String criterioOrden);
	/**
	 * 
	 * @param criteriosBusqueda
	 * @param criteriosOrdenar
	 * @return
	 */
	public ArrayList<AutorizadorDescuento> buscarPorX (TreeMap<String, Object> criteriosBusqueda, List<String> criteriosOrdenar);
	/**
	 * 
	 * @param id
	 * @return
	 */
	public AutorizadorDescuento buscarPorId (Long id);
	/**
	 * 
	 * @param autorizadorDescuento
	 */
	public void guardar(AutorizadorDescuento autorizadorDescuento);
	/**
	 * 
	 * @param autorizadorDescuento
	 */
	public void actualizar(AutorizadorDescuento autorizadorDescuento);


}
