/**
 * Proyecto		: SISVYR
 * Sistema		: Sistema de Ventas y Reservas
 * Descripción	:
 * Autor		: José Sullo Avalos
 * Fecha		: 27/08/2012
 */
package pe.itsb.vyrbus.model.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

import pe.itsb.vyrbus.model.bean.DocumentoBus;
/**
 * @author JA
 * @since JDK1.6
 */
public interface DocumentoBusDAO extends GenericDAO{
	public ArrayList<DocumentoBus> buscarPorEstadoRegistro(String estado, String criterioOrden);
	public ArrayList<DocumentoBus> buscarPorX(TreeMap<String, Object> criteriosBusqueda, List<String> criteriosOrdenar);

	public DocumentoBus buscarPorId(Long id);
	public void guardar(DocumentoBus documentoBus);
	public void actualizar(DocumentoBus documentoBus);
	public void inactivar(Long id);

}
