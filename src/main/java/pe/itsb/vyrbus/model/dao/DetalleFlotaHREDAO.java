/**
 * Proyecto		: SISVYR
 * Sistema		: Sistema de Ventas y Reservas
 * Descripción	:
 * Autor		: José Abanto
 * Fecha		: 27/08/2014
 * Hora			: 12:07:53
 */
package pe.itsb.vyrbus.model.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

import pe.itsb.vyrbus.model.bean.DetalleFlotaHRE;

/**
 * @author JABANTO
 *
 */
public interface DetalleFlotaHREDAO extends GenericDAO {
	/**
	 * Guarda el detalle de flota de la HRE.
	 * @param detalleFlotaHRE	: Nueva instancia del detalle flota HRE
	 * @throws Exception
	 */
	public void guardar(DetalleFlotaHRE detalleFlotaHRE)throws Exception;
	/**
	 * Actualiza el detalle de flota de HRE
	 * @param detalleFlotaHRE	: Instancia a actualizar.
	 * @throws Exception
	 */
	public void actualizar(DetalleFlotaHRE detalleFlotaHRE)throws Exception;
	/**
	 * Busca el detalle de la hoja de hre
	 * @param numeroHRE : Numero de HRE
	 * @return
	 * @throws Exception
	 */
	public ArrayList<DetalleFlotaHRE> buscarPorX (TreeMap<String, Object> criteriosBusqueda, List<String> criteriosOrdenar);

}
