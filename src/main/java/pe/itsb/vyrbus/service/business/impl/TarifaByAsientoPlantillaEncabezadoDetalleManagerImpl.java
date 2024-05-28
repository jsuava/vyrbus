/**
 * Proyecto		: SISVYR
 * Sistema		: Sistema de Ventas y Reservas
 * Descripción	: 
 * Autor		: José Abanto
 * Fecha		: 30/01/2019
 * Hora			: 13:33:38
 */
package pe.itsb.vyrbus.service.business.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

import org.springframework.transaction.annotation.Transactional;

import pe.itsb.vyrbus.model.bean.TarifaByAsientoPlantillaEncabezadoDetalle;
import pe.itsb.vyrbus.model.dao.TarifaByAsientoPlantillaEncabezadoDetalleDAO;
import pe.itsb.vyrbus.service.business.TarifaByAsientoPlantillaEncabezadoDetalleManager;


/**
 * @author Jose Abanto
 *
 */
public class TarifaByAsientoPlantillaEncabezadoDetalleManagerImpl implements TarifaByAsientoPlantillaEncabezadoDetalleManager {
	private TarifaByAsientoPlantillaEncabezadoDetalleDAO tarifaByAsientoPlantillaEncabezadoDetalleDAO;

	/**
	 * @return the tarifaPlantillaEncabezadoDetalleDAO
	 */
	public TarifaByAsientoPlantillaEncabezadoDetalleDAO getTarifaByAsientoPlantillaEncabezadoDetalleDAO() {
		return tarifaByAsientoPlantillaEncabezadoDetalleDAO;
	}

	/**
	 * @param tarifaByAsientoPlantillaEncabezadoDetalleDAO the tarifaPlantillaEncabezadoDetalleDAO to set
	 */
	public void setTarifaByAsientoPlantillaEncabezadoDetalleDAO(
			TarifaByAsientoPlantillaEncabezadoDetalleDAO tarifaByAsientoPlantillaEncabezadoDetalleDAO) {
		this.tarifaByAsientoPlantillaEncabezadoDetalleDAO = tarifaByAsientoPlantillaEncabezadoDetalleDAO;
	}

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.service.business.TarifaPlantillaEncabezadoDetalleManager#buscarPorEstadoRegistro(java.lang.String, java.lang.String)
	 */
	@Override
	public ArrayList<TarifaByAsientoPlantillaEncabezadoDetalle> buscarPorEstadoRegistro(
			String estado, String criterioOrden) {
		// TODO Auto-generated method stub
		return getTarifaByAsientoPlantillaEncabezadoDetalleDAO().buscarPorEstadoRegistro(estado, criterioOrden);
	}

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.service.business.TarifaPlantillaEncabezadoDetalleManager#buscarPorX(java.util.TreeMap, java.util.List)
	 */
	@Override
	public ArrayList<TarifaByAsientoPlantillaEncabezadoDetalle> buscarPorX(
			TreeMap<String, Object> criteriosBusqueda,
			List<String> criteriosOrdenar) {
		// TODO Auto-generated method stub
		return getTarifaByAsientoPlantillaEncabezadoDetalleDAO().buscarPorX(criteriosBusqueda, criteriosOrdenar);
	}

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.service.business.TarifaPlantillaEncabezadoDetalleManager#buscarPorId(java.lang.Long)
	 */
	@Override
	public TarifaByAsientoPlantillaEncabezadoDetalle buscarPorId(Long id) {
		// TODO Auto-generated method stub
		return getTarifaByAsientoPlantillaEncabezadoDetalleDAO().buscarPorId(id);
	}

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.service.business.TarifaPlantillaEncabezadoDetalleManager#guardar(com.tepsa.sisvyr.model.bean.TarifaPlantillaEncabezadoDetalle)
	 */
	@Transactional
	@Override
	public void guardar(
			TarifaByAsientoPlantillaEncabezadoDetalle tarifaByAsientoPlantillaEncabezadoDetalle) {
		// TODO Auto-generated method stub
		getTarifaByAsientoPlantillaEncabezadoDetalleDAO().guardar(tarifaByAsientoPlantillaEncabezadoDetalle);
	}

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.service.business.TarifaPlantillaEncabezadoDetalleManager#actualizar(com.tepsa.sisvyr.model.bean.TarifaPlantillaEncabezadoDetalle)
	 */
	@Transactional
	@Override
	public void actualizar(
			TarifaByAsientoPlantillaEncabezadoDetalle tarifaByAsientoPlantillaEncabezadoDetalle) {
		// TODO Auto-generated method stub
		getTarifaByAsientoPlantillaEncabezadoDetalleDAO().actualizar(tarifaByAsientoPlantillaEncabezadoDetalle);
	}
}
