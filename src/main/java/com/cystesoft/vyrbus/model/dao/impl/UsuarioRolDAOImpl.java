package com.cystesoft.vyrbus.model.dao.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

import com.cystesoft.vyrbus.model.bean.UsuarioRol;
import com.cystesoft.vyrbus.model.bean.UsuarioRolID;
import com.cystesoft.vyrbus.model.dao.UsuarioRolDAO;

/**
 *
 * @author JOsé Abanto
 *
 */
@SuppressWarnings("unchecked")
public class UsuarioRolDAOImpl extends GenericDAOImpl implements UsuarioRolDAO {

	/*
	 * (non-Javadoc)
	 * @see com.tepsa.sisvyr.model.dao.UsuarioRolDAO#buscarPorEstadoRegistro(java.lang.String, java.lang.String)
	 */
	@Override
	public ArrayList<UsuarioRol> buscarPorEstadoRegistro(String estado,String criterioOrden) {
		return (ArrayList<UsuarioRol>) super.findByEstadoRegistro(UsuarioRol.class, estado, criterioOrden);
	}

	/*
	 * (non-Javadoc)
	 * @see com.tepsa.sisvyr.model.dao.UsuarioRolDAO#buscarPorX(java.util.TreeMap, java.util.List)
	 */
	@Override
	public ArrayList<UsuarioRol> buscarPorX(TreeMap<String, Object> criteriosBusqueda,List<String> criteriosOrdenar) {
		return (ArrayList<UsuarioRol>) super.findByX(UsuarioRol.class, criteriosBusqueda, criteriosOrdenar);
	}

	/*
	 * (non-Javadoc)
	 * @see com.tepsa.sisvyr.model.dao.UsuarioRolDAO#buscarPorId(java.lang.Long)
	 */
	@Override
	public UsuarioRol buscarPorId(Long id) {
		return (UsuarioRol) super.findById(UsuarioRol.class, id);
	}

	/*
	 * (non-Javadoc)
	 * @see com.tepsa.sisvyr.model.dao.UsuarioRolDAO#guardar(com.tepsa.sisvyr.model.bean.UsuarioRol)
	 */
	@Override
	public void guardar(UsuarioRol usuarioRol) {
		super.save(usuarioRol);
	}

	/*
	 * (non-Javadoc)
	 * @see com.tepsa.sisvyr.model.dao.UsuarioRolDAO#actualizar(com.tepsa.sisvyr.model.bean.UsuarioRol)
	 */
	@Override
	public void actualizar(UsuarioRol usuarioRol) {
		super.update(usuarioRol);
	}

	/*
	 * (non-Javadoc)
	 * @see com.tepsa.sisvyr.model.dao.UsuarioRolDAO#inactivar(com.tepsa.sisvyr.model.bean.UsuarioRolID)
	 */
	@Override
	public void activaInactiva(UsuarioRolID usuarioRolID, String estado) {
		String sql ="DELETE FROM vrtusuario_rol  "+
					 "WHERE rol_id="+usuarioRolID.getIdRol()+" And usuario_id="+usuarioRolID.getIdUsuario();

		log.info(sql);
		getSession().createSQLQuery(sql).executeUpdate();

	}

	/*
	 * (non-Javadoc)
	 * @see com.tepsa.sisvyr.model.dao.UsuarioRolDAO#buscarXIdRol(java.lang.Integer)
	 */
	@Override
	public List<UsuarioRol> buscarXIdUsuario(Integer idUsuario){
		Class<?> oClass=UsuarioRol.class;
		String hql="FROM "+oClass.getSimpleName()+" WHERE usuario=? AND estadoRegistro='A' AND rol.sistema=0 ORDER BY rol.denominacion "; //Sistema=0: solo los roles para acceso al sisvyr

		return getSession().createQuery(hql).setInteger(0, idUsuario).list();

	}

	/*
	 * (non-Javadoc)
	 * @see com.tepsa.sisvyr.model.dao.UsuarioRolDAO#buscarXidUsuario(java.lang.Integer)
	 */
	@Override
	public UsuarioRol buscarXidUsuarioAndIdRol(Integer idUsuario, Integer idRol){
		Class<?> oClass=UsuarioRol.class;
		String hql="FROM "+oClass.getSimpleName()+" WHERE usuario=? AND rol=?";

		return (UsuarioRol) getSession().createQuery(hql).setInteger(0, idUsuario).setInteger(1, idRol).uniqueResult();
	}

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.model.dao.UsuarioRolDAO#buscarPorX(java.lang.String, java.lang.Object[], java.util.List, java.lang.String)
	 */
	@Override
	public List<UsuarioRol> buscarPorX(String campo, Object[] criterios,List<String> criteriosOrdenar, String estadoRegistro)throws Exception {
		// TODO Auto-generated method stub
		return (List<UsuarioRol>)super.findByX(UsuarioRol.class, campo, criterios, criteriosOrdenar, estadoRegistro);
	}




//	public void delete(Integer rol_id, Integer usuario_id){
//		String sql="DELETE FROM vrtusuario_rol WHERE rol_id="+rol_id+" AND usuario_id="+usuario_id+" AND C_estReg='A' ";
//
//		log.info(sql);
//		getSession().createSQLQuery(sql).executeUpdate();
//	}


}
