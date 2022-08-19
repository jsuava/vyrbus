/**
 * Proyecto		: SISVYR
 * Sistema		: Sistema de Ventas y Reservas
 * Descripción	:
 * Autor		: José Abanto
 * Fecha		: 07/07/2016
 * Hora			: 11:22:44
 */
package com.cystesoft.vyrbus.service.business.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

import org.springframework.transaction.annotation.Transactional;

import com.cystesoft.vyrbus.model.bean.TarifaClienteDetalle;
import com.cystesoft.vyrbus.model.dao.TarifaClienteDetalleDAO;
import com.cystesoft.vyrbus.service.business.TarifaClienteDetalleManager;

/**
 * @author jabanto
 *
 */
public class TarifaClienteDetalleManagerImpl implements TarifaClienteDetalleManager{
	private TarifaClienteDetalleDAO tarifaClienteDetalleDAO;

	/**
	 * @return the tarifaClienteDetalleDAO
	 */
	public TarifaClienteDetalleDAO getTarifaClienteDetalleDAO() {
		return tarifaClienteDetalleDAO;
	}

	/**
	 * @param tarifaClienteDetalleDAO the tarifaClienteDetalleDAO to set
	 */
	public void setTarifaClienteDetalleDAO(TarifaClienteDetalleDAO tarifaClienteDetalleDAO) {
		this.tarifaClienteDetalleDAO = tarifaClienteDetalleDAO;
	}

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.service.business.TarifaClienteDetalleManager#buscarPorEstadoRegistro(java.lang.String, java.lang.String)
	 */
	@Override
	public ArrayList<TarifaClienteDetalle> buscarPorEstadoRegistro(String estado, String criterioOrden) {
		// TODO Auto-generated method stub
		return getTarifaClienteDetalleDAO().buscarPorEstadoRegistro(estado, criterioOrden);
	}

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.service.business.TarifaClienteDetalleManager#buscarPorX(java.util.TreeMap, java.util.List)
	 */
	@Override
	public ArrayList<TarifaClienteDetalle> buscarPorX(TreeMap<String, Object> criteriosBusqueda,List<String> criteriosOrdenar) {
		// TODO Auto-generated method stub
		return getTarifaClienteDetalleDAO().buscarPorX(criteriosBusqueda, criteriosOrdenar);
	}

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.service.business.TarifaClienteDetalleManager#buscarPorId(java.lang.Long)
	 */
	@Override
	public TarifaClienteDetalle buscarPorId(Long id) {
		// TODO Auto-generated method stub
		return getTarifaClienteDetalleDAO().buscarPorId(id);
	}

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.service.business.TarifaClienteDetalleManager#guardar(com.tepsa.sisvyr.model.bean.TarifaClienteDetalle)
	 */
	@Override
	@Transactional
	public void guardar(TarifaClienteDetalle tarifaClienteDetalle) {
		// TODO Auto-generated method stub
		getTarifaClienteDetalleDAO().guardar(tarifaClienteDetalle);
	}

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.service.business.TarifaClienteDetalleManager#actualizar(com.tepsa.sisvyr.model.bean.TarifaClienteDetalle)
	 */
	@Transactional
	@Override
	public void actualizar(TarifaClienteDetalle tarifaClienteDetalle) {
		// TODO Auto-generated method stub
		getTarifaClienteDetalleDAO().actualizar(tarifaClienteDetalle);
	}

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.service.business.TarifaClienteDetalleManager#buscarTarifaPersonalizada(java.lang.Long, java.lang.Integer, java.lang.Integer, java.lang.String)
	 */
	@Override
	public List<TarifaClienteDetalle> buscarTarifaPersonalizada(Long clienteID,Integer rutaID, Integer servicioID, String fechaPartida)throws Exception {
		// TODO Auto-generated method stub
		return getTarifaClienteDetalleDAO().buscarTarifaPersonalizada(clienteID, rutaID, servicioID, fechaPartida);
	}

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.service.business.TarifaClienteDetalleManager#buscarTarifaPersonalizada(java.lang.Long, java.lang.Integer, java.lang.Integer, java.lang.String, java.lang.Integer)
	 */
	@Override
	public TarifaClienteDetalle buscarTarifaPersonalizada(Long clienteID,Integer rutaID, Integer servicioID, String fechaPartida,Integer asiento) throws Exception {
		// TODO Auto-generated method stub
		return getTarifaClienteDetalleDAO().buscarTarifaPersonalizada(clienteID, rutaID, servicioID, fechaPartida, asiento);
	}

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.service.business.TarifaClienteDetalleManager#buscarByRuc(java.lang.String)
	 */
	@Override
	public List<TarifaClienteDetalle> buscarByRuc(String ruc) throws Exception {
		// TODO Auto-generated method stub
		return getTarifaClienteDetalleDAO().buscarByRuc(ruc);
	}

}
