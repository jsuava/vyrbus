package pe.itsb.vyrbus.model.dao.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

import pe.itsb.vyrbus.model.bean.UsuarioAprobador;
import pe.itsb.vyrbus.model.dao.UsuarioArobadorDAO;

/**
 *
 * @author JABANTO
 *
 */
@SuppressWarnings("unchecked")
public class UsuarioAprobadorDAOImpl extends GenericDAOImpl implements UsuarioArobadorDAO {

	/*
	 * (non-Javadoc)
	 * @see com.tepsa.sisvyr.model.dao.UsuarioArobadorDAO#buscarPorEstadoRegistro(java.lang.String, java.lang.String)
	 */
	@Override
	public ArrayList<UsuarioAprobador> buscarPorEstadoRegistro(String estado,String criterioOrden) {
		return (ArrayList<UsuarioAprobador>) super.findByEstadoRegistro(UsuarioAprobador.class, estado, criterioOrden);
	}

	/*
	 * (non-Javadoc)
	 * @see com.tepsa.sisvyr.model.dao.UsuarioArobadorDAO#buscarPorX(java.util.TreeMap, java.util.List)
	 */
	@Override
	public ArrayList<UsuarioAprobador> buscarPorX(TreeMap<String, Object> criteriosBusqueda,List<String> criteriosOrdenar) {
		return (ArrayList<UsuarioAprobador>)super.findByX(UsuarioAprobador.class, criteriosBusqueda, criteriosOrdenar);
	}

	/*
	 * (non-Javadoc)
	 * @see com.tepsa.sisvyr.model.dao.UsuarioArobadorDAO#buscarPorId(java.lang.Long)
	 */
	@Override
	public UsuarioAprobador buscarPorId(Long id) {
		return (UsuarioAprobador)super.findById(UsuarioAprobador.class, id);
	}

	/*
	 * (non-Javadoc)
	 * @see com.tepsa.sisvyr.model.dao.UsuarioArobadorDAO#guardar(com.tepsa.sisvyr.model.bean.UsuarioAprobador)
	 */
	@Override
	public void guardar(UsuarioAprobador usuarioAprobador) {
//		String sql="INSERT INTO vrtusuapro "+
//				     "(usuapro_id, usuario_id, n_nivapro, c_estreg, audusuins, audipinse, audusumod, audipmodi) "+
//				    "VALUES "+
//				      "(SEQ_VRTUSUAPRO_ID.NEXTVAL, " +
//				      +usuarioAprobador.getUsuario().getId()+"," +
//				      +usuarioAprobador.getNivelAprobacion()+"," +
//				      "'"+usuarioAprobador.getEstadoRegistro()+"'," +
//				      "'"+usuarioAprobador.getUsuarioInsercion()+"'," +
//				      "'"+usuarioAprobador.getIpInsercion()+"', " +
//				      "'"+usuarioAprobador.getUsuarioModificacion()+"'," +
//				      "'"+usuarioAprobador.getIpModificacion()+"')";
//
//		log.info(sql);
//		getSession().createSQLQuery(sql).executeUpdate();

		super.save(usuarioAprobador);
	}

	/*
	 * (non-Javadoc)
	 * @see com.tepsa.sisvyr.model.dao.UsuarioArobadorDAO#actualizar(com.tepsa.sisvyr.model.bean.UsuarioAprobador)
	 */
	@Override
	public void actualizar(UsuarioAprobador usuarioAprobador) {
//		String sql="UPDATE vrtusuapro "+
//					       "SET "+
//					           "usuario_id = "+usuarioAprobador.getUsuario().getId()+", "+
//					           "n_nivapro = "+usuarioAprobador.getNivelAprobacion()+", "+
//					           "c_estreg = '"+usuarioAprobador.getEstadoRegistro()+"', "+
//					           "audusumod = '"+usuarioAprobador.getUsuarioModificacion()+"', "+
//					           "audipmodi = '"+usuarioAprobador.getIpModificacion()+"' "+
//					     "WHERE usuapro_id = "+usuarioAprobador.getId();
//		log.info(sql);
//		getSession().createSQLQuery(sql).executeUpdate();
		super.update(usuarioAprobador);
	}

	/*
	 * (non-Javadoc)
	 * @see com.tepsa.sisvyr.model.dao.UsuarioArobadorDAO#inactivar(java.lang.Long)
	 */
	@Override
	public void inactivar(Long id) {
		super.inactivate(UsuarioAprobador.class, id);
	}

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.model.dao.UsuarioArobadorDAO#buscarXNivel(java.lang.Integer)
	 */
	@Override
	public List<UsuarioAprobador> buscarXNivel(Integer nivel) throws Exception {
		// TODO Auto-generated method stub
		Class<?> oClass=UsuarioAprobador.class;

		String hql="FROM "+oClass.getSimpleName()+" WHERE nivelAprobacion=?";

		 return getSession().createQuery(hql).setInteger(0, nivel).list();
	}


}
