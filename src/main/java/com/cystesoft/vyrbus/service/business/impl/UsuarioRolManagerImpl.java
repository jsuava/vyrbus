package com.cystesoft.vyrbus.service.business.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

import org.springframework.transaction.annotation.Transactional;

import com.cystesoft.vyrbus.model.bean.UsuarioRol;
import com.cystesoft.vyrbus.model.bean.UsuarioRolID;
import com.cystesoft.vyrbus.model.dao.UsuarioRolDAO;
import com.cystesoft.vyrbus.service.business.UsuarioRolManager;
import com.cystesoft.vyrbus.service.exceptions.UsuarioRolDuplicadoException;

public class UsuarioRolManagerImpl  implements UsuarioRolManager{
	
	private UsuarioRolDAO usuarioRolDAO;
	
	
	public UsuarioRolDAO getUsuarioRolDAO(){
		return usuarioRolDAO;
	}
	
	public void setUsuarioRolDAO(UsuarioRolDAO usuarioRolDAO){
		this.usuarioRolDAO=usuarioRolDAO;
	}

	/*
	 * (non-Javadoc)
	 * @see com.tepsa.sisvyr.model.dao.UsuarioRolDAO#buscarPorEstadoRegistro(java.lang.String, java.lang.String)
	 */
	@Override
	public ArrayList<UsuarioRol> buscarPorEstadoRegistro(String estado,String criterioOrden) {
		return getUsuarioRolDAO().buscarPorEstadoRegistro(estado, criterioOrden);
	}

	/*
	 * (non-Javadoc)
	 * @see com.tepsa.sisvyr.model.dao.UsuarioRolDAO#buscarPorX(java.util.TreeMap, java.util.List)
	 */
	@Override
	public ArrayList<UsuarioRol> buscarPorX(TreeMap<String, Object> criteriosBusqueda,List<String> criteriosOrdenar) {
		return getUsuarioRolDAO().buscarPorX(criteriosBusqueda, criteriosOrdenar);
	}

	/*
	 * (non-Javadoc)
	 * @see com.tepsa.sisvyr.model.dao.UsuarioRolDAO#buscarPorId(java.lang.Long)
	 */
	@Override
	public UsuarioRol buscarPorId(Long id) throws Exception {
		return getUsuarioRolDAO().buscarPorId(id);
	}

	/*
	 * (non-Javadoc)
	 * @see com.tepsa.sisvyr.model.dao.UsuarioRolDAO#guardar(com.tepsa.sisvyr.model.bean.UsuarioRol)
	 */
	@Override
	@Transactional
	public void guardar(UsuarioRol usuarioRol) throws Exception {
		try{
			TreeMap<String, Object> criteriosBusqueda = new TreeMap<String, Object>();
			criteriosBusqueda.put("rol", usuarioRol.getRol());
			criteriosBusqueda.put("usuario", usuarioRol.getUsuario());
			List<?> resultUsuarioRol = getUsuarioRolDAO().buscarPorX(criteriosBusqueda, null);
			/*Valida duplicidad del Usuario y el Rol*/
			if(resultUsuarioRol.size()>0)
				throw new UsuarioRolDuplicadoException();
			
			getUsuarioRolDAO().guardar(usuarioRol);
			
		}catch (UsuarioRolDuplicadoException urdex) {
			throw new UsuarioRolDuplicadoException();
		}
	}

	/*
	 * (non-Javadoc)
	 * @see com.tepsa.sisvyr.model.dao.UsuarioRolDAO#actualizar(com.tepsa.sisvyr.model.bean.UsuarioRol)
	 */
	@Override
	@Transactional
	public void actualizar(UsuarioRol usuarioRol) throws Exception {
		try{
			TreeMap<String, Object> criteriosBusqueda = new TreeMap<String, Object>();
			criteriosBusqueda.put("rol", usuarioRol.getRol());
			criteriosBusqueda.put("usuario", usuarioRol.getUsuario());
			List<?> resultUsuarioRol = getUsuarioRolDAO().buscarPorX(criteriosBusqueda, null);
			/*Valida duplicidad del Usuario y el Rol*/
			for(int r = 0; r < resultUsuarioRol.size(); r ++) {
				UsuarioRol oUsuarioRol  = (UsuarioRol) resultUsuarioRol.get(r);
					if (!(oUsuarioRol.getRol().getId() == usuarioRol.getRol().getId()) && (oUsuarioRol.getUsuario().getId() == usuarioRol.getUsuario().getId()) )
						throw new UsuarioRolDuplicadoException();
				}
			
			getUsuarioRolDAO().actualizar(usuarioRol);
			
		}catch (UsuarioRolDuplicadoException urdex) {
			throw new UsuarioRolDuplicadoException();
		}
		
	}

	/*
	 * (non-Javadoc)
	 * @see com.tepsa.sisvyr.model.dao.UsuarioRolDAO#inactivar(java.lang.Long)
	 */
	@Override
	@Transactional
	public void activaInactiva(UsuarioRolID usuarioRolID, String estado) throws Exception {
		getUsuarioRolDAO().activaInactiva(usuarioRolID, estado);	
	}

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.service.business.UsuarioRolManager#buscarXidUsuarioAndIdRol(java.lang.Integer, java.lang.Integer)
	 */
	@Override
	public UsuarioRol buscarXidUsuarioAndIdRol(Integer idUsuario, Integer idRol)throws Exception {
		// TODO Auto-generated method stub
		return getUsuarioRolDAO().buscarXidUsuarioAndIdRol(idUsuario, idRol);
	}

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.service.business.UsuarioRolManager#buscarXIdUsuario(java.lang.Integer)
	 */
	@Override
	public List<UsuarioRol> buscarXIdUsuario(Integer idUsuario) throws Exception {
		// TODO Auto-generated method stub
		return getUsuarioRolDAO().buscarXIdUsuario(idUsuario);
	}

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.service.business.UsuarioRolManager#buscarPorX(java.lang.String, java.lang.Object[], java.util.List, java.lang.String)
	 */
	@Override
	public List<UsuarioRol> buscarPorX(String campo, Object[] criterios,List<String> criteriosOrdenar, String estadoRegistro)throws Exception {
		return getUsuarioRolDAO().buscarPorX(campo, criterios, criteriosOrdenar, estadoRegistro);
	}
	
	


}
