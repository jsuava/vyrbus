/**
 * Proyecto		: SISVYR
 * Sistema		: Sistema de Ventas y Reservas
 * Descripción	:
 * Autor		: José Abanto
 * Fecha		: 27/06/2015
 * Hora			: 08:50:58
 */
package pe.itsb.vyrbus.model.dao.impl;

import java.util.List;
import java.util.TreeMap;

import pe.itsb.vyrbus.model.bean.DestinatariosEmails;
import pe.itsb.vyrbus.model.dao.DestinatariosEmailsDAO;
import pe.itsb.vyrbus.service.util.Constantes;

/**
 * @author jabanto
 *
 */
public class DestinatariosEmailsDAOImpl extends GenericDAOImpl implements DestinatariosEmailsDAO{

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.model.dao.WindowsDAO#buscarByObjeto(java.lang.String)
	 */
	@Override
	public DestinatariosEmails buscarByObjeto(String objeto) throws Exception {
		// TODO Auto-generated method stub
		String hql="FROM Windows w WHERE w.objeto='"+objeto+"' AND w.c_estreg='"+Constantes.VALUE_ACTIVO+"' ";
		DestinatariosEmails windows=(DestinatariosEmails) getSession().createQuery(hql).uniqueResult();

		return windows;
	}

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.model.dao.WindowsDAO#buscarPor(java.util.TreeMap, java.util.List)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<DestinatariosEmails> buscarPor(TreeMap<String, Object> criteriosBusqueda,List<String> criteriosOrdenar) throws Exception {
		// TODO Auto-generated method stub
		return (List<DestinatariosEmails>) super.findByX(DestinatariosEmails.class, criteriosBusqueda, criteriosOrdenar);
	}

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.model.dao.WindowsDAO#actualizar(com.tepsa.sisvyr.model.bean.Windows)
	 */
	@Override
	public void actualizar(DestinatariosEmails windows) throws Exception {
		// TODO Auto-generated method stub
		super.update(windows);
	}

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.model.dao.WindowsDAO#guardar(com.tepsa.sisvyr.model.bean.Windows)
	 */
	@Override
	public void guardar(DestinatariosEmails windows) throws Exception {
		// TODO Auto-generated method stub
		super.save(windows);
	}

}
