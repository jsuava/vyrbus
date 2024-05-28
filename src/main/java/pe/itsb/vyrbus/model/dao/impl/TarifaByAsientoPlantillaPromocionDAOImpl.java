/**
 * Proyecto		: SISVYR
 * Sistema		: Sistema de Ventas y Reservas
 * Descripción	: 
 * Autor		: José Abanto
 * Fecha		: 02/02/2019
 * Hora			: 14:59:10
 */
package pe.itsb.vyrbus.model.dao.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

import pe.itsb.vyrbus.model.bean.TarifaByAsientoPlantillaPromocion;
import pe.itsb.vyrbus.model.dao.TarifaByAsientoPlantillaPromocionDAO;


/**
 * @author Jose Abanto
 *
 */
@SuppressWarnings("unchecked")
public class TarifaByAsientoPlantillaPromocionDAOImpl extends GenericDAOImpl implements TarifaByAsientoPlantillaPromocionDAO {

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.model.dao.TarifaByAsientoPlantillaPromocionDAO#buscarPorEstadoRegistro(java.lang.String, java.lang.String)
	 */
	
	@Override
	public ArrayList<TarifaByAsientoPlantillaPromocion> buscarPorEstadoRegistro(
			String estado, String criterioOrden) {
		// TODO Auto-generated method stub
		return (ArrayList<TarifaByAsientoPlantillaPromocion>) findByEstadoRegistro(TarifaByAsientoPlantillaPromocion.class, estado, criterioOrden);
	}

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.model.dao.TarifaByAsientoPlantillaPromocionDAO#buscarPorX(java.util.TreeMap, java.util.List)
	 */
	@Override
	public ArrayList<TarifaByAsientoPlantillaPromocion> buscarPorX(
			TreeMap<String, Object> criteriosBusqueda,
			List<String> criteriosOrdenar) {
		// TODO Auto-generated method stub
		return (ArrayList<TarifaByAsientoPlantillaPromocion>) findByX(TarifaByAsientoPlantillaPromocion.class, criteriosBusqueda, criteriosOrdenar);
	}

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.model.dao.TarifaByAsientoPlantillaPromocionDAO#buscarPorId(java.lang.Long)
	 */
	@Override
	public TarifaByAsientoPlantillaPromocion buscarPorId(Long id) {
		// TODO Auto-generated method stub
		return (TarifaByAsientoPlantillaPromocion) findById(TarifaByAsientoPlantillaPromocion.class, id);
	}

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.model.dao.TarifaByAsientoPlantillaPromocionDAO#guardar(com.tepsa.sisvyr.model.bean.TarifaByAsientoPlantillaPromocion)
	 */
	@Override
	public void guardar(TarifaByAsientoPlantillaPromocion TarifaByAsientoPlantillaPromocion) {
		// TODO Auto-generated method stub
		save(TarifaByAsientoPlantillaPromocion);
	}

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.model.dao.TarifaByAsientoPlantillaPromocionDAO#actualizar(com.tepsa.sisvyr.model.bean.TarifaByAsientoPlantillaPromocion)
	 */
	@Override
	public void actualizar(TarifaByAsientoPlantillaPromocion TarifaByAsientoPlantillaPromocion) {
		// TODO Auto-generated method stub
		update(TarifaByAsientoPlantillaPromocion);
	}

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.model.dao.TarifaByAsientoPlantillaPromocionDAO#buscarByTipoAsiento(java.lang.String)
	 */
	@Override
	public List<TarifaByAsientoPlantillaPromocion> buscarByTipoAsiento(String tipoAsientos_ids) throws Exception {
		String hql="FROM TarifaByAsientoPlantillaPromocion WHERE tipoAsiento.id IN ("+tipoAsientos_ids+") AND estadoRegistro='A'";
		log.info(hql);
		List<TarifaByAsientoPlantillaPromocion>result = (List<TarifaByAsientoPlantillaPromocion>) getSession().createQuery(hql).list();
		
		return result;
	}

}
