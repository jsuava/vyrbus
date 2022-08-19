/**
 * Proyecto		: SISVYR
 * Sistema		: Sistema de Ventas y Reservas
 * Descripción	: Clase que implementa los metodos declarados en la Interfaz UsuarioDAO
 * Autor		: José Sullo Avalos
 * Fecha		: 07/04/2012
 */
package com.cystesoft.vyrbus.model.dao.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

import com.cystesoft.vyrbus.model.bean.ControlAcceso;
import com.cystesoft.vyrbus.model.bean.Usuario;
import com.cystesoft.vyrbus.model.dao.UsuarioDAO;
import com.cystesoft.vyrbus.service.util.Constantes;

/**
 * Clase que implementa los metodos declarados en la Interfaz UsuarioDAO, los mismos que permitiran la
 * manipulación de datos relacionados con los usuarios.
 * @author José Sullo Avalos
 * @since JDK1.6
 */
@SuppressWarnings("unchecked")
public class UsuarioDAOImpl extends GenericDAOImpl implements UsuarioDAO {

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.dao.UsuarioDAO#buscarUsuarioPorLogin(java.lang.String, java.lang.String)
	 */
	@Override
	public Usuario buscarUsuarioPorLogin(String login, String estado)throws Exception {
		String hql ="";
		List<?> result = null;
		if(estado!=null){
			hql="FROM Usuario WHERE login=? AND estadoRegistro=?";
			log.info(hql);
			result = getSession().createQuery(hql).setString(0, login).setString(1, estado).list();
		}else{
			hql="FROM Usuario WHERE login=?";
			log.info(hql);
			result = getSession().createQuery(hql).setString(0, login).list();
		}


		Usuario usuario = new Usuario();
		if(result == null || result.size() == 0){
			usuario=null;
		}else{
			usuario=(Usuario) result.get(0);
		}
		return usuario;
	}

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.dao.UsuarioDAO#buscarUsuarioPorLoginPassword(java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	public Usuario buscarUsuarioPorLoginPassword(String login, String password,	String estado) throws Exception {
		String hql = "FROM Usuario " +
				"WHERE login=? AND password=? AND estadoRegistro=? ";
		List<?> result = getSession().createQuery(hql).setString(0, login).setString(1, password).setString(2, estado) .list();
		Usuario usuario = new Usuario();
		if(result == null || result.size() == 0){
			usuario=null;
		}else{
			usuario=(Usuario) result.get(0);
		}

		return usuario;
	}



	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.dao.UsuarioDAO#update(com.tepsa.sisvyr.domain.Usuario)
	 */
	//@Override
	public boolean update(Usuario usuario) {
		//super
		return true;
	}

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.dao.UsuarioDAO#inactivar(java.lang.Long)
	 */
	@Override
	public void inactivar(Long id) {
		super.inactivate(Usuario.class, id);
	}

	/*
	 * (non-Javadoc)
	 * @see com.tepsa.sisvyr.model.dao.UsuarioDAO#buscarPorEstadoRegistro(java.lang.String, java.lang.String)
	 */
	@Override
	public ArrayList<Usuario> buscarPorEstadoRegistro(String estado,String criterioOrden) {
		return (ArrayList<Usuario>) super.findByEstadoRegistro(Usuario.class, estado, criterioOrden);
	}

	/*
	 * (non-Javadoc)
	 * @see com.tepsa.sisvyr.model.dao.UsuarioDAO#buscarPorX(java.util.TreeMap, java.util.List)
	 */
	@Override
	public ArrayList<Usuario> buscarPorX(TreeMap<String, Object> criteriosBusqueda,List<String> criteriosOrdenar) {
		return (ArrayList<Usuario>) super.findByX(Usuario.class, criteriosBusqueda, criteriosOrdenar);
	}

	/*
	 * (non-Javadoc)
	 * @see com.tepsa.sisvyr.model.dao.UsuarioDAO#buscarPorId(java.lang.Long)
	 */
	@Override
	public Usuario buscarPorId(Long id) {
		return (Usuario) super.findById(Usuario.class, id);
	}

	/*
	 * (non-Javadoc)
	 * @see com.tepsa.sisvyr.model.dao.UsuarioDAO#guardar(com.tepsa.sisvyr.model.bean.Usuario)
	 */
	@Override
	public void guardar(Usuario usuario) {
		super.save(usuario);
	}

	/*
	 * (non-Javadoc)
	 * @see com.tepsa.sisvyr.model.dao.UsuarioDAO#actualizar(com.tepsa.sisvyr.model.bean.Usuario)
	 */
	@Override
	public void actualizar(Usuario usuario) {
		super.update(usuario);

	}

	/*
	 * (non-Javadoc)
	 * @see com.tepsa.sisvyr.model.dao.UsuarioDAO#activar(java.lang.Long)
	 */
	@Override
	public void activar(Long id) {
		super.activate(Usuario.class, id);
	}

	/*
	 * (non-Javadoc)
	 * @see com.tepsa.sisvyr.model.dao.UsuarioDAO#buscarUsuarioLiquidacion(java.lang.String, java.lang.String, java.lang.Integer)
	 */
	@Override
	public List<Usuario> buscarUsuarioLiquidacion(String fechaInicio, String fechaFinal, Integer idAgencia,Integer estadoLiquidacion){
		String sql="SELECT DISTINCT(u.c_login), u.usuario_id, u.c_apepat, u.c_apemat, u.c_nombre " +
					"FROM vrtliquidacion lq "+
					"INNER JOIN vrmusuario u ON (u.usuario_id=lq.usuario_id) "+
					"WHERE lq.c_Estreg='"+Constantes.VALUE_ACTIVO+"' " +
						 "AND lq.agencia_id= NVL("+idAgencia+",lq.agencia_id) ";
					if(fechaInicio!=null && fechaFinal!=null)
						sql+="AND lq.d_fecliq BETWEEN to_date('"+fechaInicio+"', 'dd/mm/yyyy') AND to_date('"+fechaFinal+"','dd/mm/yyyy') ";
					if(estadoLiquidacion!=null)
						sql+="AND lq.n_estliq="+estadoLiquidacion+" ";

					sql+="ORDER BY u.c_apepat, u.c_apemat, u.c_nombre ";

		log.info(sql);

		List<?> result = getSession().createSQLQuery(sql).list();
		List<Usuario> ListResult = new ArrayList<>();

		for(int i=0; i<result.size(); i++){
			Object[] obj = (Object[]) result.get(i);
			Usuario usuario= new Usuario();
			usuario.setId(((BigDecimal)obj[1]).intValue());
			usuario.setApellidoPaterno(obj[2].toString());
			usuario.setApellidoMaterno(obj[3]!=null?obj[3].toString():"");
			usuario.setNombre(obj[4].toString());

			ListResult.add(usuario);
		}
		return ListResult;
	}

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.model.dao.UsuarioDAO#buscarPorX(java.lang.String, java.lang.Object[], java.util.List, java.lang.String)
	 */
	@Override
	public List<Usuario> buscarPorX(String campo, Object[] criterios,List<String> criteriosOrdenar, String estadoRegistro)throws Exception {
		// TODO Auto-generated method stub
		return (List<Usuario>) super.findByX(Usuario.class, campo, criterios, criteriosOrdenar, estadoRegistro);
	}

	/* (non-Javadoc)
	 * @see com.cystesoft.vyrbus.model.dao.UsuarioDAO#buscarCodigoAcceso(int, java.lang.String, java.lang.String)
	 */
	@Override
	public ControlAcceso buscarCodigoAcceso(int idUsuario, String codigo, String estado) throws Exception {
		String hql ="";
		List<?> result = null;
		if(estado!=null){
			hql="FROM ControlAcceso WHERE usuario=? AND codigo=? AND  SYSDATE() BETWEEN  fechaActivacion AND fechaCaducidad  and estadoRegistro=?";
			log.info(hql);
			result = getSession().createQuery(hql).setInteger(0,idUsuario).setString(1, codigo).setString(2, estado).list();
		}

		ControlAcceso controlAcceso =  new ControlAcceso();
		if(result == null || result.size() == 0){
			controlAcceso=null;
		}else{
			controlAcceso=(ControlAcceso) result.get(0);
		}
		return controlAcceso;
	}
}