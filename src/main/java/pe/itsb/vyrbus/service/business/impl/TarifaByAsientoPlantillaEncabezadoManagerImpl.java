/**
 * Proyecto		: SISVYR
 * Sistema		: Sistema de Ventas y Reservas
 * Descripción	: 
 * Autor		: José Abanto
 * Fecha		: 29/01/2019
 * Hora			: 09:36:20
 */
package pe.itsb.vyrbus.service.business.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

import org.springframework.transaction.annotation.Transactional;

import pe.itsb.vyrbus.model.bean.TarifaByAsientoPlantillaEncabezado;
import pe.itsb.vyrbus.model.bean.TarifaByAsientoPlantillaEncabezadoDetalle;
import pe.itsb.vyrbus.model.dao.TarifaByAsientoPlantillaEncabezadoDAO;
import pe.itsb.vyrbus.model.dao.TarifaByAsientoPlantillaEncabezadoDetalleDAO;
import pe.itsb.vyrbus.service.business.TarifaByAsientoPlantillaEncabezadoManager;


/**
 * @author Jose Abanto
 *
 */
public class TarifaByAsientoPlantillaEncabezadoManagerImpl  implements TarifaByAsientoPlantillaEncabezadoManager{
	private TarifaByAsientoPlantillaEncabezadoDAO tarifaByAsientoPlantillaEncabezadoDAO;
	private TarifaByAsientoPlantillaEncabezadoDetalleDAO tarifaByAsientoPlantillaEncabezadoDetalleDAO;

	/**
	 * @return the tarifaPlantillaEncabezadoDAO
	 */
	public TarifaByAsientoPlantillaEncabezadoDAO getTarifaByAsientoPlantillaEncabezadoDAO() {
		return tarifaByAsientoPlantillaEncabezadoDAO;
	}

	/**
	 * @param tarifaByAsientoPlantillaEncabezadoDAO the tarifaPlantillaEncabezadoDAO to set
	 */
	public void setTarifaByAsientoPlantillaEncabezadoDAO(
			TarifaByAsientoPlantillaEncabezadoDAO tarifaByAsientoPlantillaEncabezadoDAO) {
		this.tarifaByAsientoPlantillaEncabezadoDAO = tarifaByAsientoPlantillaEncabezadoDAO;
	}

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
	 * @see com.tepsa.sisvyr.service.business.TarifaPlantillaEncabezadoManager#buscarPorEstadoRegistro(java.lang.String, java.lang.String)
	 */
	@Override
	public ArrayList<TarifaByAsientoPlantillaEncabezado> buscarPorEstadoRegistro(
			String estado, String criterioOrden) {
		// TODO Auto-generated method stub
		return getTarifaByAsientoPlantillaEncabezadoDAO().buscarPorEstadoRegistro(estado, criterioOrden);
	}

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.service.business.TarifaPlantillaEncabezadoManager#buscarPorX(java.util.TreeMap, java.util.List)
	 */
	@Override
	public ArrayList<TarifaByAsientoPlantillaEncabezado> buscarPorX(
			TreeMap<String, Object> criteriosBusqueda,
			List<String> criteriosOrdenar) {
		// TODO Auto-generated method stub
		return getTarifaByAsientoPlantillaEncabezadoDAO().buscarPorX(criteriosBusqueda, criteriosOrdenar);
	}

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.service.business.TarifaPlantillaEncabezadoManager#buscarPorId(java.lang.Long)
	 */
	@Override
	public TarifaByAsientoPlantillaEncabezado buscarPorId(Long id) {
		// TODO Auto-generated method stub
		return getTarifaByAsientoPlantillaEncabezadoDAO().buscarPorId(id);
	}

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.service.business.TarifaPlantillaEncabezadoManager#guardar(com.tepsa.sisvyr.model.bean.TarifaPlantillaEncabezado)
	 */
	@Transactional
	@Override
	public void guardar(TarifaByAsientoPlantillaEncabezado tarifaPlantillaEncabezado) {
		// TODO Auto-generated method stub
		getTarifaByAsientoPlantillaEncabezadoDAO().guardar(tarifaPlantillaEncabezado);
	}

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.service.business.TarifaPlantillaEncabezadoManager#actualizar(com.tepsa.sisvyr.model.bean.TarifaPlantillaEncabezado)
	 */
	@Transactional
	@Override
	public void actualizar(TarifaByAsientoPlantillaEncabezado tarifaPlantillaEncabezado) {
		// TODO Auto-generated method stub
		getTarifaByAsientoPlantillaEncabezadoDAO().actualizar(tarifaPlantillaEncabezado);
	}

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.service.business.TarifaPlantillaEncabezadoManager#guardar(com.tepsa.sisvyr.model.bean.TarifaPlantillaEncabezado, java.util.List)
	 */
	@Transactional
	@Override
	public void guardar(TarifaByAsientoPlantillaEncabezado tarifaByAsientoPlantillaEncabezado, List<TarifaByAsientoPlantillaEncabezadoDetalle> lstTarifaByAsientoPlantillaEncabezadoDetalles)throws Exception {
		if(tarifaByAsientoPlantillaEncabezado.getId()==null)
			guardar(tarifaByAsientoPlantillaEncabezado);
		else
			actualizar(tarifaByAsientoPlantillaEncabezado);
		
		for(TarifaByAsientoPlantillaEncabezadoDetalle encabezadoDetalle: lstTarifaByAsientoPlantillaEncabezadoDetalles){
			encabezadoDetalle.setTarifaByAsientoPlantillaEncabezado(tarifaByAsientoPlantillaEncabezado);
			getTarifaByAsientoPlantillaEncabezadoDetalleDAO().guardar(encabezadoDetalle);
		}
		
	}
	
	

	
}
