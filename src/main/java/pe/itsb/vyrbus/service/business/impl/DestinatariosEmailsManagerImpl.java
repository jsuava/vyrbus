/**
 * Proyecto		: SISVYR
 * Sistema		: Sistema de Ventas y Reservas
 * Descripción	:
 * Autor		: José Abanto
 * Fecha		: 27/06/2015
 * Hora			: 08:48:20
 */
package pe.itsb.vyrbus.service.business.impl;

import java.util.List;
import java.util.TreeMap;

import org.springframework.transaction.annotation.Transactional;

import pe.itsb.vyrbus.model.bean.DestinatariosEmails;
import pe.itsb.vyrbus.model.dao.DestinatariosEmailsDAO;
import pe.itsb.vyrbus.service.business.DestinatariosEmailsManager;

/**
 * @author jabanto
 *
 */
public class DestinatariosEmailsManagerImpl implements DestinatariosEmailsManager{
	private DestinatariosEmailsDAO destinatariosEmailsDAO;
	/**
	 * @return the destinatariosEmailsDAO
	 */
	public DestinatariosEmailsDAO getDestinatariosEmailsDAO() {
		return destinatariosEmailsDAO;
	}

	/**
	 * @param destinatariosEmailsDAO the destinatariosEmailsDAO to set
	 */
	public void setDestinatariosEmailsDAO(DestinatariosEmailsDAO destinatariosEmailsDAO) {
		this.destinatariosEmailsDAO = destinatariosEmailsDAO;
	}

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.service.business.WindowsManager#buscarByObjeto(java.lang.String)
	 */
	@Override
	public DestinatariosEmails buscarByObjeto(String objeto) throws Exception {
		// TODO Auto-generated method stub
		return getDestinatariosEmailsDAO().buscarByObjeto(objeto);
	}

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.service.business.WindowsManager#buscarPor(java.util.TreeMap, java.util.List)
	 */
	@Override
	public List<DestinatariosEmails> buscarPor(TreeMap<String, Object> criteriosBusqueda,List<String> criteriosOrdenar) throws Exception {
		// TODO Auto-generated method stub
		return getDestinatariosEmailsDAO().buscarPor(criteriosBusqueda, criteriosOrdenar);
	}

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.service.business.WindowsManager#actualizar(com.tepsa.sisvyr.model.bean.Windows)
	 */
	@Transactional
	@Override
	public void actualizar(DestinatariosEmails windows) throws Exception {
		// TODO Auto-generated method stub
		getDestinatariosEmailsDAO().actualizar(windows);
	}

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.service.business.WindowsManager#guardar(com.tepsa.sisvyr.model.bean.Windows)
	 */
	@Transactional
	@Override
	public void guardar(DestinatariosEmails windows) throws Exception {
		// TODO Auto-generated method stub
		getDestinatariosEmailsDAO().guardar(windows);
	}



}
