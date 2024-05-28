/**
 * Proyecto		: SISVYR
 * Sistema		: Sistema de Ventas y Reservas
 * Descripción	: 
 * Autor		: José Abanto
 * Fecha		: 02/02/2019
 * Hora			: 14:59:45
 */
package pe.itsb.vyrbus.service.business.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

import org.springframework.transaction.annotation.Transactional;

import pe.itsb.vyrbus.model.bean.TarifaByAsientoPlantillaPromocion;
import pe.itsb.vyrbus.model.dao.TarifaByAsientoPlantillaPromocionDAO;
import pe.itsb.vyrbus.service.business.TarifaByAsientoPlantillaPromocionManager;


/**
 * @author Jose Abanto
 *
 */
public class TarifaByAsientoPlantillaPromocionManagerImpl implements TarifaByAsientoPlantillaPromocionManager{
	private TarifaByAsientoPlantillaPromocionDAO tarifaByAsientoPlantillaPromocionDAO;

	/**
	 * @return the tarifaPlantillaPromocionDAO
	 */
	public TarifaByAsientoPlantillaPromocionDAO getTarifaByAsientoPlantillaPromocionDAO() {
		return tarifaByAsientoPlantillaPromocionDAO;
	}

	/**
	 * @param tarifaByAsientoPlantillaPromocionDAO the tarifaPlantillaPromocionDAO to set
	 */
	public void setTarifaByAsientoPlantillaPromocionDAO(
			TarifaByAsientoPlantillaPromocionDAO tarifaByAsientoPlantillaPromocionDAO) {
		this.tarifaByAsientoPlantillaPromocionDAO = tarifaByAsientoPlantillaPromocionDAO;
	}

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.service.business.TarifaPlantillaPromocionManager#buscarPorEstadoRegistro(java.lang.String, java.lang.String)
	 */
	@Override
	public ArrayList<TarifaByAsientoPlantillaPromocion> buscarPorEstadoRegistro(
			String estado, String criterioOrden) {
		// TODO Auto-generated method stub
		return getTarifaByAsientoPlantillaPromocionDAO().buscarPorEstadoRegistro(estado, criterioOrden);
	}

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.service.business.TarifaPlantillaPromocionManager#buscarPorX(java.util.TreeMap, java.util.List)
	 */
	@Override
	public ArrayList<TarifaByAsientoPlantillaPromocion> buscarPorX(
			TreeMap<String, Object> criteriosBusqueda,
			List<String> criteriosOrdenar) {
		// TODO Auto-generated method stub
		return getTarifaByAsientoPlantillaPromocionDAO().buscarPorX(criteriosBusqueda, criteriosOrdenar);
	}

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.service.business.TarifaPlantillaPromocionManager#buscarPorId(java.lang.Long)
	 */
	@Override
	public TarifaByAsientoPlantillaPromocion buscarPorId(Long id) {
		// TODO Auto-generated method stub
		return getTarifaByAsientoPlantillaPromocionDAO().buscarPorId(id);
	}

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.service.business.TarifaPlantillaPromocionManager#guardar(com.tepsa.sisvyr.model.bean.TarifaPlantillaPromocion)
	 */
	@Transactional
	@Override
	public void guardar(TarifaByAsientoPlantillaPromocion tarifaByAsientoPlantillaPromocion) {
		// TODO Auto-generated method stub
		getTarifaByAsientoPlantillaPromocionDAO().guardar(tarifaByAsientoPlantillaPromocion);
	}

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.service.business.TarifaPlantillaPromocionManager#actualizar(com.tepsa.sisvyr.model.bean.TarifaPlantillaPromocion)
	 */
	@Transactional
	@Override
	public void actualizar(TarifaByAsientoPlantillaPromocion tarifaByAsientoPlantillaPromocion) {
		// TODO Auto-generated method stub
		getTarifaByAsientoPlantillaPromocionDAO().actualizar(tarifaByAsientoPlantillaPromocion);
	}

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.service.business.TarifaPlantillaPromocionManager#buscarByTipoAsiento(java.lang.String)
	 */
	@Override
	public List<TarifaByAsientoPlantillaPromocion> buscarByTipoAsiento(
			String tipoAsientos_ids) throws Exception {
		// TODO Auto-generated method stub
		return getTarifaByAsientoPlantillaPromocionDAO().buscarByTipoAsiento(tipoAsientos_ids);
	}
}
