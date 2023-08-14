/**
 * Proyecto		: SISVYR
 * Sistema		: Sistema de Ventas y Reservas
 * Descripción	:
 * Autor		: jM
 * Fecha		: 04/05/2012
 */
package pe.itsb.vyrbus.model.dao.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

import pe.itsb.vyrbus.model.bean.Personal;
import pe.itsb.vyrbus.model.dao.PersonalDAO;

/**
 *
 * @author jM
 * @since JDK1.6
 */
@SuppressWarnings("unchecked")
public class PersonalDAOImpl extends GenericDAOImpl implements PersonalDAO {

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.dao.PersonalDAO#buscarPorEstadoRegistro(java.lang.String)
	 */
	@Override
	public ArrayList<Personal> buscarPorEstadoRegistro(String estado, String criterioOrden) {
		return (ArrayList<Personal>) super.findByEstadoRegistro(Personal.class, estado, criterioOrden);
	}

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.dao.PersonalDAO#buscarPorX(java.util.TreeMap)
	 */
	@Override
	public ArrayList<Personal> buscarPorX(TreeMap<String, Object> criteriosBusqueda, List<String> criteriosOrdenar) {
		return (ArrayList<Personal>) super.findByX(Personal.class, criteriosBusqueda, criteriosOrdenar);
	}

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.dao.PersonalDAO#buscarPorId(java.lang.Long)
	 */
	@Override
	public Personal buscarPorId(Long id) {
		return (Personal) super.findById(Personal.class, id);
	}

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.dao.PersonalDAO#guardar(com.tepsa.sisvyr.domain.Personal)
	 */
	@Override
	public void guardar(Personal personal) {
		super.save(personal);
	}

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.dao.PersonalDAO#actualizar(com.tepsa.sisvyr.domain.Personal)
	 */
	@Override
	public void actualizar(Personal personal) {
		super.update(personal);
	}

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.dao.PersonalDAO#inactivar(java.lang.Long)
	 */
	@Override
	public void inactivar(Long id) {
		super.inactivate(Personal.class, id);
	}

	/*
	 * (non-Javadoc)
	 * @see com.tepsa.sisvyr.model.dao.PersonalDAO#buscarMailsXRols(java.lang.String)
	 */
	@Override
	public String buscarMailsXRols(String iDsRol){
		String sql="SELECT p.c_email, P.ROWID "+
					"FROM vrmpersonal p "+
					"INNER JOIN vrmusuario u ON (u.personal_id=p.personal_id) "+
					"INNER JOIN vrtusuario_rol ur ON (ur.usuario_id=u.usuario_id) "+
					"WHERE ur.rol_id IN ("+iDsRol+") "+
					      "AND u.c_estreg='A' AND ur.c_estreg='A' AND p.c_estreg='A'";
		log.info(sql);

		List<?> result = getSession().createSQLQuery(sql).list();
		String mails="";
		for (Object element : result) {
			Object[] obj = (Object[])element;
			if(mails.length()==0)
				mails=obj[0].toString();
			else mails+=","+obj[0].toString();
		}
		return mails;
	}


}