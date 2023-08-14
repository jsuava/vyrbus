/**
 * Proyecto		: SISVYR
 * Sistema		: Sistema de Ventas y Reservas
 * Descripci�n	:
 * Autor		: Jos�
 * Fecha		: 09/11/2013
 */
package pe.itsb.vyrbus.service.business.impl;

import java.util.List;
import java.util.TreeMap;

import org.springframework.transaction.annotation.Transactional;

import pe.itsb.vyrbus.model.bean.DetalleManifiesto;
import pe.itsb.vyrbus.model.dao.DetalleManifiestoDAO;
import pe.itsb.vyrbus.service.business.DetalleManifiestoManager;

/**
 * @author JABANTO
 *
 */
public class DetalleManifiestoManagerImpl implements DetalleManifiestoManager {
	private DetalleManifiestoDAO detalleManifiestoDAO;



	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.service.business.DetalleManifiestoManager#buscarPorX(java.util.TreeMap, java.util.List)
	 */
	@Override
	public List<DetalleManifiesto> buscarPorX(TreeMap<String, Object> criteriosBusqueda,List<String> criteriosOrdenar) {
		// TODO Auto-generated method stub
		return getDetalleManifiestoDAO().buscarPorX(criteriosBusqueda, criteriosOrdenar);
	}

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.service.business.DetalleManifiestoManager#guradar(com.tepsa.sisvyr.model.bean.DetalleManifiesto)
	 */
	@Transactional
	@Override
	public void guradar(DetalleManifiesto detalleManifiesto) throws Exception {
		// TODO Auto-generated method stub
		getDetalleManifiestoDAO().guradar(detalleManifiesto);
	}

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.service.business.DetalleManifiestoManager#actualizar(com.tepsa.sisvyr.model.bean.DetalleManifiesto)
	 */
	@Transactional
	@Override
	public void actualizar(DetalleManifiesto detalleManifiesto)throws Exception {
		// TODO Auto-generated method stub
		getDetalleManifiestoDAO().actualizar(detalleManifiesto);
	}

	/**
	 * @return the detalleManifiestoDAO
	 */
	public DetalleManifiestoDAO getDetalleManifiestoDAO() {
		return detalleManifiestoDAO;
	}

	/**
	 * @param detalleManifiestoDAO the detalleManifiestoDAO to set
	 */
	public void setDetalleManifiestoDAO(DetalleManifiestoDAO detalleManifiestoDAO) {
		this.detalleManifiestoDAO = detalleManifiestoDAO;
	}

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.service.business.DetalleManifiestoManager#buscarPasajeros(java.lang.Long, java.lang.Integer, java.lang.Integer)
	 */
	@Override
	public List<DetalleManifiesto> buscarPasajeros(Long idItinerario,Integer idLocalidadOrigen, Integer idAgenciaPuntoEmbarque)throws Exception {
		// TODO Auto-generated method stub
		return getDetalleManifiestoDAO().buscarPasajeros(idItinerario, idLocalidadOrigen, idAgenciaPuntoEmbarque);
	}

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.service.business.DetalleManifiestoManager#cerrarDespachoPasajeros(java.lang.Integer, java.lang.Long, java.lang.String)
	 */
	@Override
	public void cerrarDespachoPasajeros(Integer idAgencia, Long idManifiesto,String idsVentas) throws Exception {
		// TODO Auto-generated method stub
		getDetalleManifiestoDAO().cerrarDespachoPasajeros(idAgencia, idManifiesto, idsVentas);
	}

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.service.business.DetalleManifiestoManager#validarVentaManifiesto(java.lang.Long)
	 */
	@Override
	public Boolean validarVentaManifiesto(Long idVenta) throws Exception {
		// TODO Auto-generated method stub
		return getDetalleManifiestoDAO().validarVentaManifiesto(idVenta);
	}

	/* (non-Javadoc)
	 * @see pe.itsb.vyrbus.service.business.DetalleManifiestoManager#quitarManifiesto(java.lang.Long)
	 */
	@Override
	public void quitarManifiesto(Long idVenta) throws Exception {
		getDetalleManifiestoDAO().quitarManifiesto(idVenta);
	}




}
