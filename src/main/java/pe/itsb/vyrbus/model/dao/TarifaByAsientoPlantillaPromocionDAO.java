/**
 * Proyecto		: SISVYR
 * Sistema		: Sistema de Ventas y Reservas
 * Descripción	: 
 * Autor		: José Abanto
 * Fecha		: 02/02/2019
 * Hora			: 14:56:51
 */
package pe.itsb.vyrbus.model.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

import pe.itsb.vyrbus.model.bean.TarifaByAsientoPlantillaPromocion;



/**
 * @author Jose Abanto
 *
 */
public interface TarifaByAsientoPlantillaPromocionDAO extends GenericDAO{
	/**
	 * 
	 * @param estado
	 * @param criterioOrden
	 * @return
	 */
	public ArrayList<TarifaByAsientoPlantillaPromocion> buscarPorEstadoRegistro(String estado, String criterioOrden);
	/**
	 * 
	 * @param criteriosBusqueda
	 * @param criteriosOrdenar
	 * @return
	 */
	public ArrayList<TarifaByAsientoPlantillaPromocion> buscarPorX(TreeMap<String, Object> criteriosBusqueda, List<String> criteriosOrdenar);
	/**
	 * 
	 * @param id
	 * @return
	 */
	public TarifaByAsientoPlantillaPromocion buscarPorId(Long id);
	/**
	 * 
	 * @param tarifaByAsientoPlantillaPromocion
	 */
	public void guardar(TarifaByAsientoPlantillaPromocion tarifaByAsientoPlantillaPromocion);
	/**
	 * 
	 * @param tarifaByAsientoPlantillaPromocion
	 */
	public void actualizar(TarifaByAsientoPlantillaPromocion tarifaByAsientoPlantillaPromocion);
	/**
	 * Realiza la busqueda de las plantillas promocionales segun los tipos de asitos
	 * @param tipoAsientos_ids	: Identificadores de los tipos de asiento
	 * @return
	 * @throws Exception
	 */
	public List<TarifaByAsientoPlantillaPromocion>buscarByTipoAsiento(String tipoAsientos_ids)throws Exception;
}
