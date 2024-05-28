/**
 * Proyecto		: SISVYR
 * Sistema		: Sistema de Ventas y Reservas
 * Descripción	: 
 * Autor		: José Abanto
 * Fecha		: 30/01/2019
 * Hora			: 13:33:51
 */
package pe.itsb.vyrbus.model.dao.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

import pe.itsb.vyrbus.model.bean.TarifaByAsientoPlantillaEncabezadoDetalle;
import pe.itsb.vyrbus.model.dao.TarifaByAsientoPlantillaEncabezadoDetalleDAO;


/**
 * @author Jose Abanto
 *
 */
@SuppressWarnings("unchecked")
public class TarifaByAsientoPlantillaEncabezadoDetalleDAOImpl extends GenericDAOImpl implements TarifaByAsientoPlantillaEncabezadoDetalleDAO{

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.model.dao.TarifaByAsientoPlantillaEncabezadoDetalleDAO#buscarPorEstadoRegistro(java.lang.String, java.lang.String)
	 */	
	@Override
	public ArrayList<TarifaByAsientoPlantillaEncabezadoDetalle> buscarPorEstadoRegistro(
			String estado, String criterioOrden) {
		// TODO Auto-generated method stub
		return (ArrayList<TarifaByAsientoPlantillaEncabezadoDetalle>) findByEstadoRegistro(TarifaByAsientoPlantillaEncabezadoDetalle.class, estado, criterioOrden);
	}

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.model.dao.TarifaByAsientoPlantillaEncabezadoDetalleDAO#buscarPorX(java.util.TreeMap, java.util.List)
	 */
	@Override
	public ArrayList<TarifaByAsientoPlantillaEncabezadoDetalle> buscarPorX(
			TreeMap<String, Object> criteriosBusqueda,
			List<String> criteriosOrdenar) {
		// TODO Auto-generated method stub
		return (ArrayList<TarifaByAsientoPlantillaEncabezadoDetalle>) findByX(TarifaByAsientoPlantillaEncabezadoDetalle.class, criteriosBusqueda, criteriosOrdenar);
	}

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.model.dao.TarifaByAsientoPlantillaEncabezadoDetalleDAO#buscarPorId(java.lang.Long)
	 */
	@Override
	public TarifaByAsientoPlantillaEncabezadoDetalle buscarPorId(Long id) {
		// TODO Auto-generated method stub
		return (TarifaByAsientoPlantillaEncabezadoDetalle) findById(TarifaByAsientoPlantillaEncabezadoDetalle.class, id);
	}

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.model.dao.TarifaByAsientoPlantillaEncabezadoDetalleDAO#guardar(com.tepsa.sisvyr.model.bean.TarifaByAsientoPlantillaEncabezadoDetalle)
	 */
	@Override
	public void guardar(
			TarifaByAsientoPlantillaEncabezadoDetalle tarifaByAsientoPlantillaEncabezadoDetalle) {
		// TODO Auto-generated method stub
		save(tarifaByAsientoPlantillaEncabezadoDetalle);
	}

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.model.dao.TarifaByAsientoPlantillaEncabezadoDetalleDAO#actualizar(com.tepsa.sisvyr.model.bean.TarifaByAsientoPlantillaEncabezadoDetalle)
	 */
	@Override
	public void actualizar(
			TarifaByAsientoPlantillaEncabezadoDetalle tarifaByAsientoPlantillaEncabezadoDetalle) {
		// TODO Auto-generated method stub
		update(tarifaByAsientoPlantillaEncabezadoDetalle);
	}
	
}
