/**
 * Proyecto		: SISVYR
 * Sistema		: Sistema de Ventas y Reservas
 * Descripción	: 
 * Autor		: José Abanto
 * Fecha		: 26/08/2014
 * Hora			: 09:17:41
 */
package com.cystesoft.vyrbus.service.business.impl;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.cystesoft.vyrbus.model.bean.MTCDetalleRuta;
import com.cystesoft.vyrbus.model.dao.MTCDetalleRutaDAO;
import com.cystesoft.vyrbus.service.business.MTCDetalleRutaManager;

/**
 * @author JABANTO
 *
 */
public class MTCDetalleRutaManagerImpl implements MTCDetalleRutaManager{
	private MTCDetalleRutaDAO mtcDetalleRutaDAO;
	
	/**
	 * @return the mtcDetalleRutaDAO
	 */
	public MTCDetalleRutaDAO getMtcDetalleRutaDAO() {
		return mtcDetalleRutaDAO;
	}
	/**
	 * @param mtcDetalleRutaDAO the mtcDetalleRutaDAO to set
	 */
	public void setMtcDetalleRutaDAO(MTCDetalleRutaDAO mtcDetalleRutaDAO) {
		this.mtcDetalleRutaDAO = mtcDetalleRutaDAO;
	}
	
	
	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.service.business.MTCDetalleRutaManager#buscarPorIdRuta(java.lang.Integer)
	 */
	@Override
	public MTCDetalleRuta buscarPorIdRuta(Integer idRutaVyr) throws Exception {
		return getMtcDetalleRutaDAO().buscarPorIdRuta(idRutaVyr);
	}
	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.service.business.MTCDetalleRutaManager#guardar(com.tepsa.sisvyr.model.bean.MTCDetalleRuta)
	 */
	@Override
	@Transactional
	public void guardar(MTCDetalleRuta detalleRuta) throws Exception {
		// TODO Auto-generated method stub
		getMtcDetalleRutaDAO().guardar(detalleRuta);
	}
	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.service.business.MTCDetalleRutaManager#actualizar(com.tepsa.sisvyr.model.bean.MTCDetalleRuta)
	 */
	@Override
	@Transactional
	public void actualizar(MTCDetalleRuta detalleRuta) throws Exception {
		// TODO Auto-generated method stub
		getMtcDetalleRutaDAO().actualizar(detalleRuta);
	}
	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.service.business.MTCDetalleRutaManager#buscarPorIdRutaMtc(java.lang.Integer)
	 */
	@Override
	public List<MTCDetalleRuta> buscarPorIdRutaMtc(Integer idRutaMtc)throws Exception {
		// TODO Auto-generated method stub
		return getMtcDetalleRutaDAO().buscarPorIdRutaMtc(idRutaMtc);
	}
	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.service.business.MTCDetalleRutaManager#buscarPorEstado(java.lang.String)
	 */
	@Override
	public List<MTCDetalleRuta> buscarPorEstado(String estado) throws Exception {
		// TODO Auto-generated method stub
		return getMtcDetalleRutaDAO().buscarPorEstado(estado);
	}



	
	

}
