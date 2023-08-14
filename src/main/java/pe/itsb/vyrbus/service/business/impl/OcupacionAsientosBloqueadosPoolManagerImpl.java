/**
 * Proyecto		: SISVYR
 * Sistema		: Sistema de Ventas y Reservas
 * Descripción	:
 * Autor		: José Abanto
 * Fecha		: 08/09/2016
 * Hora			: 14:29:32
 */
package pe.itsb.vyrbus.service.business.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

import org.springframework.transaction.annotation.Transactional;

import pe.itsb.vyrbus.model.bean.OcupacionAsientosBloqueadosPool;
import pe.itsb.vyrbus.model.dao.OcupacionAsientosBloqueadosPoolDAO;
import pe.itsb.vyrbus.service.business.OcupacionAsientosBloqueadosPoolManager;

/**
 * @author jabanto
 *
 */
public class OcupacionAsientosBloqueadosPoolManagerImpl implements OcupacionAsientosBloqueadosPoolManager {
	private OcupacionAsientosBloqueadosPoolDAO ocupacionAsientosBloqueadosPoolDAO;



	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.service.business.OcupacionAsientosBloqueadosPoolManager#buscarPorEstadoRegistro(java.lang.String, java.lang.String)
	 */
	@Override
	public ArrayList<OcupacionAsientosBloqueadosPool> buscarPorEstadoRegistro(
			String estado, String criterioOrden) {
		// TODO Auto-generated method stub
		return getOcupacionAsientosBloqueadosPoolDAO().buscarPorEstadoRegistro(estado, criterioOrden);
	}

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.service.business.OcupacionAsientosBloqueadosPoolManager#buscarPorX(java.util.TreeMap, java.util.List)
	 */
	@Override
	public ArrayList<OcupacionAsientosBloqueadosPool> buscarPorX(
			TreeMap<String, Object> criteriosBusqueda,
			List<String> criteriosOrdenar) {
		// TODO Auto-generated method stub
		return getOcupacionAsientosBloqueadosPoolDAO().buscarPorX(criteriosBusqueda, criteriosOrdenar);
	}

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.service.business.OcupacionAsientosBloqueadosPoolManager#buscarPorId(java.lang.Long)
	 */
	@Override
	public OcupacionAsientosBloqueadosPool buscarPorId(Long id) {
		// TODO Auto-generated method stub
		return getOcupacionAsientosBloqueadosPoolDAO().buscarPorId(id);
	}

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.service.business.OcupacionAsientosBloqueadosPoolManager#guardar(com.tepsa.sisvyr.model.bean.OcupacionAsientosBloqueadosPool)
	 */
	@Override
	@Transactional
	public void guardar(
			OcupacionAsientosBloqueadosPool ocupacionAsientosBloqueadosPool) {
		// TODO Auto-generated method stub
		getOcupacionAsientosBloqueadosPoolDAO().guardar(ocupacionAsientosBloqueadosPool);
	}

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.service.business.OcupacionAsientosBloqueadosPoolManager#actualizar(com.tepsa.sisvyr.model.bean.OcupacionAsientosBloqueadosPool)
	 */
	@Override
	@Transactional
	public void actualizar(
			OcupacionAsientosBloqueadosPool ocupacionAsientosBloqueadosPool) {
		// TODO Auto-generated method stub
		getOcupacionAsientosBloqueadosPoolDAO().update(ocupacionAsientosBloqueadosPool);
	}

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.service.business.OcupacionAsientosBloqueadosPoolManager#delete(java.lang.String)
	 */
	@Override
	@Transactional
	public void delete(String codeTransaction) {
		// TODO Auto-generated method stub
		getOcupacionAsientosBloqueadosPoolDAO().delete(codeTransaction);
	}

	/**
	 * @return the ocupacionAsientosBloqueadosPoolDAO
	 */
	public OcupacionAsientosBloqueadosPoolDAO getOcupacionAsientosBloqueadosPoolDAO() {
		return ocupacionAsientosBloqueadosPoolDAO;
	}

	/**
	 * @param ocupacionAsientosBloqueadosPoolDAO the ocupacionAsientosBloqueadosPoolDAO to set
	 */
	public void setOcupacionAsientosBloqueadosPoolDAO(
			OcupacionAsientosBloqueadosPoolDAO ocupacionAsientosBloqueadosPoolDAO) {
		this.ocupacionAsientosBloqueadosPoolDAO = ocupacionAsientosBloqueadosPoolDAO;
	}


}
