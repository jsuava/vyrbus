/**
 * Proyecto		: SISVYR
 * Sistema		: Sistema de Ventas y Reservas
 * Descripción	:
 * Autor		: José Abanto
 * Fecha		: 27/08/2014
 * Hora			: 12:13:02
 */
package pe.itsb.vyrbus.service.business.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

import org.springframework.transaction.annotation.Transactional;

import pe.itsb.vyrbus.model.bean.DetalleFlotaHRE;
import pe.itsb.vyrbus.model.dao.DetalleFlotaHREDAO;
import pe.itsb.vyrbus.service.business.DetalleFlotaHREManager;

/**
 * @author JABANTO
 *
 */
public class DetalleFlotaHREManagerImpl implements DetalleFlotaHREManager {
	private DetalleFlotaHREDAO detalleFlotaHREDAO;

	/**
	 * @return the detalleFlotaHREDAO
	 */
	public DetalleFlotaHREDAO getDetalleFlotaHREDAO() {
		return detalleFlotaHREDAO;
	}
	/**
	 * @param detalleFlotaHREDAO the detalleFlotaHREDAO to set
	 */
	public void setDetalleFlotaHREDAO(DetalleFlotaHREDAO detalleFlotaHREDAO) {
		this.detalleFlotaHREDAO = detalleFlotaHREDAO;
	}

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.service.business.DetalleFlotaHREManager#guardar(com.tepsa.sisvyr.model.bean.DetalleFlotaHRE)
	 */
	@Override
	@Transactional
	public void guardar(DetalleFlotaHRE detalleFlotaHRE) throws Exception {
		// TODO Auto-generated method stub
		getDetalleFlotaHREDAO().guardar(detalleFlotaHRE);
	}

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.service.business.DetalleFlotaHREManager#actualizar(com.tepsa.sisvyr.model.bean.DetalleFlotaHRE)
	 */
	@Override
	@Transactional
	public void actualizar(DetalleFlotaHRE detalleFlotaHRE) throws Exception {
		// TODO Auto-generated method stub
		getDetalleFlotaHREDAO().actualizar(detalleFlotaHRE);
	}
	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.service.business.DetalleFlotaHREManager#buscarPorX(java.util.TreeMap, java.util.List)
	 */
	@Override
	public ArrayList<DetalleFlotaHRE> buscarPorX(TreeMap<String, Object> criteriosBusqueda,List<String> criteriosOrdenar) {
		// TODO Auto-generated method stub
		return getDetalleFlotaHREDAO().buscarPorX(criteriosBusqueda, criteriosOrdenar);
	}

}
