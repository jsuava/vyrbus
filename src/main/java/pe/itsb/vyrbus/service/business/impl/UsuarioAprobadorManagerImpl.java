package pe.itsb.vyrbus.service.business.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

import org.springframework.transaction.annotation.Transactional;

import pe.itsb.vyrbus.model.bean.UsuarioAprobador;
import pe.itsb.vyrbus.model.dao.UsuarioArobadorDAO;
import pe.itsb.vyrbus.service.business.UsuarioAprobadorManager;

/**
 *
 * @author JABANTO
 *
 */
public class UsuarioAprobadorManagerImpl implements UsuarioAprobadorManager {
	private UsuarioArobadorDAO usuarioArobadorDAO;

	public UsuarioArobadorDAO getUsuarioArobadorDAO() {
		return usuarioArobadorDAO;
	}

	public void setUsuarioArobadorDAO(UsuarioArobadorDAO usuarioArobadorDAO) {
		this.usuarioArobadorDAO = usuarioArobadorDAO;
	}


	/*
	 * (non-Javadoc)
	 * @see com.tepsa.sisvyr.service.business.UsuarioAprobadorManager#buscarPorEstadoRegistro(java.lang.String, java.lang.String)
	 */
	@Override
	public ArrayList<UsuarioAprobador> buscarPorEstadoRegistro(String estado,String criterioOrden) {
		return getUsuarioArobadorDAO().buscarPorEstadoRegistro(estado, criterioOrden);
	}

	/*
	 * (non-Javadoc)
	 * @see com.tepsa.sisvyr.service.business.UsuarioAprobadorManager#buscarPorX(java.util.TreeMap, java.util.List)
	 */
	@Override
	public ArrayList<UsuarioAprobador> buscarPorX(TreeMap<String, Object> criteriosBusqueda,List<String> criteriosOrdenar) {
		return getUsuarioArobadorDAO().buscarPorX(criteriosBusqueda, criteriosOrdenar);
	}

	/*
	 * (non-Javadoc)
	 * @see com.tepsa.sisvyr.service.business.UsuarioAprobadorManager#buscarPorId(java.lang.Long)
	 */
	@Override
	public UsuarioAprobador buscarPorId(Long id) {
		return getUsuarioArobadorDAO().buscarPorId(id);
	}

	/*
	 * (non-Javadoc)
	 * @see com.tepsa.sisvyr.service.business.UsuarioAprobadorManager#guardar(com.tepsa.sisvyr.model.bean.UsuarioAprobador)
	 */
	@Override
	@Transactional
	public void guardar(UsuarioAprobador usuarioAprobador) {
		getUsuarioArobadorDAO().guardar(usuarioAprobador);
	}

	/*
	 * (non-Javadoc)
	 * @see com.tepsa.sisvyr.service.business.UsuarioAprobadorManager#actualizar(com.tepsa.sisvyr.model.bean.UsuarioAprobador)
	 */
	@Override
	@Transactional
	public void actualizar(UsuarioAprobador usuarioAprobador) {
		getUsuarioArobadorDAO().actualizar(usuarioAprobador);
	}

	/*
	 * (non-Javadoc)
	 * @see com.tepsa.sisvyr.service.business.UsuarioAprobadorManager#inactivar(java.lang.Long)
	 */
	@Override
	@Transactional
	public void inactivar(Long id) {
		getUsuarioArobadorDAO().inactivar(id);
	}

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.service.business.UsuarioAprobadorManager#buscarXNivel(java.lang.Integer)
	 */
	@Override
	public List<UsuarioAprobador> buscarXNivel(Integer nivel) throws Exception {
		// TODO Auto-generated method stub
		return getUsuarioArobadorDAO().buscarXNivel(nivel);
	}


}
