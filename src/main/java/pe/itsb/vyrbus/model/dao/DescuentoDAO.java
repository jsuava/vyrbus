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

import pe.itsb.vyrbus.model.bean.Descuento;

/**
 *
 * @author JA
 * @since JDK1.6
 */
public interface DescuentoDAO {
	/**
	 * 
	 * @param estado
	 * @param criterioOrden
	 * @return
	 */
	public ArrayList<Descuento> buscarPorEstadoRegistro(String estado, String criterioOrden);
	/**
	 * 
	 * @param criteriosBusqueda
	 * @param criteriosOrdenar
	 * @return
	 */
	public ArrayList<Descuento> buscarPorX (TreeMap<String, Object> criteriosBusqueda, List<String> criteriosOrdenar);
	/**
	 * 
	 * @param id
	 * @return
	 */
	public Descuento buscarPorId (Long id);
	/**
	 * 
	 * @param descuento
	 */
	public void guardar(Descuento descuento);
	/**
	 * 
	 * @param descuento
	 */
	public void actualizar(Descuento descuento);


}
