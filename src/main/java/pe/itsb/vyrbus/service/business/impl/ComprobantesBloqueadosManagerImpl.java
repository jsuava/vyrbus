/**
 * Proyecto		: SISVYR
 * Sistema		: Sistema de Ventas y Reservas
 * Descripción	:
 * Autor		: José Abanto
 * Fecha		: 21/02/2017
 * Hora			: 10:28:08
 */
package pe.itsb.vyrbus.service.business.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

import org.springframework.transaction.annotation.Transactional;

import pe.itsb.vyrbus.model.bean.ComprobantesBloqueados;
import pe.itsb.vyrbus.model.dao.ComprobantesBloqueadosDAO;
import pe.itsb.vyrbus.service.business.ComprobantesBloqueadosManager;

/**
 * @author Jose Abanto
 *
 */
public class ComprobantesBloqueadosManagerImpl implements ComprobantesBloqueadosManager{
	private ComprobantesBloqueadosDAO comprobantesBloqueadosDAO;

	/**
	 * @return the comprobantesBloqueadosDAO
	 */
	public ComprobantesBloqueadosDAO getComprobantesBloqueadosDAO() {
		return comprobantesBloqueadosDAO;
	}

	/**
	 * @param comprobantesBloqueadosDAO the comprobantesBloqueadosDAO to set
	 */
	public void setComprobantesBloqueadosDAO(ComprobantesBloqueadosDAO comprobantesBloqueadosDAO) {
		this.comprobantesBloqueadosDAO = comprobantesBloqueadosDAO;
	}

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.service.business.ComprobantesBloqueadosManager#bloquearComprobante(com.tepsa.sisvyr.model.bean.ComprobantesBloqueados)
	 */
	@Transactional
	@Override
	public int bloquearComprobante(ComprobantesBloqueados ComprobantesBloqueados)
			throws Exception {
		// TODO Auto-generated method stub
		return getComprobantesBloqueadosDAO().bloquearComprobante(ComprobantesBloqueados);
	}

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.service.business.ComprobantesBloqueadosManager#desbloquearComprobante(com.tepsa.sisvyr.model.bean.ComprobantesBloqueados)
	 */
	@Override
	public int desbloquearComprobante(
			ComprobantesBloqueados comprobantesBloqueados) throws Exception {
		// TODO Auto-generated method stub
		return getComprobantesBloqueadosDAO().desbloquearComprobante(comprobantesBloqueados);
	}

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.service.business.ComprobantesBloqueadosManager#desbloquearComprobanteByUsuarioHardware(java.lang.Integer)
	 */
	@Override
	public int desbloquearComprobanteByUsuarioHardware(Integer idUsuarioHardware)
			throws Exception {
		// TODO Auto-generated method stub
		return getComprobantesBloqueadosDAO().desbloquearComprobanteByUsuarioHardware(idUsuarioHardware);
	}

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.service.business.ComprobantesBloqueadosManager#buscarPorEstadoRegistro(java.lang.String)
	 */
	@Override
	public ArrayList<ComprobantesBloqueados> buscarPorEstadoRegistro(
			String estado) {
		// TODO Auto-generated method stub
		return getComprobantesBloqueadosDAO().buscarPorEstadoRegistro(estado);
	}

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.service.business.ComprobantesBloqueadosManager#desbloquearComprobante(java.lang.Long)
	 */
	@Override
	public int desbloquearComprobante(Long idVentaPasaje) throws Exception {
		// TODO Auto-generated method stub
		return getComprobantesBloqueadosDAO().desbloquearComprobante(idVentaPasaje);
	}

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.service.business.ComprobantesBloqueadosManager#buscarPorX(java.util.TreeMap, java.util.List)
	 */
	@Override
	public ArrayList<ComprobantesBloqueados> buscarPorX(
			TreeMap<String, Object> criteriosBusqueda,
			List<String> criteriosOrdenar) throws Exception {
		// TODO Auto-generated method stub
		return getComprobantesBloqueadosDAO().buscarPorX(criteriosBusqueda, criteriosOrdenar);
	}

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.service.business.ComprobantesBloqueadosManager#buscarPorId(java.lang.Long)
	 */
	@Override
	public ComprobantesBloqueados buscarPorId(Long ventaPasajeId)
			throws Exception {
		// TODO Auto-generated method stub
		return getComprobantesBloqueadosDAO().buscarPorId(ventaPasajeId);
	}



}
