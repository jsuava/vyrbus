/**
 * Proyecto		: SISVYR
 * Sistema		: Sistema de Ventas y Reservas
 * Descripción	: Implementación de métodos que permiten el acceso al modelo.
 * Autor		: José Sullo Avalos
 * Fecha		: 28/09/2012
 */
package com.cystesoft.vyrbus.service.business.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

import org.springframework.transaction.annotation.Transactional;

import com.cystesoft.vyrbus.model.bean.Personal;
import com.cystesoft.vyrbus.model.dao.PersonalDAO;
import com.cystesoft.vyrbus.service.business.PersonalManager;
import com.cystesoft.vyrbus.service.exceptions.CodigoDuplicadoException;
import com.cystesoft.vyrbus.service.exceptions.LicenciaDuplicadaException;
import com.cystesoft.vyrbus.service.exceptions.NumeroDocumentoDuplicadoException;
import com.cystesoft.vyrbus.service.util.Constantes;

/**
 * @author Jose
 *
 */
public class PersonalManagerImpl implements PersonalManager {
	private PersonalDAO personalDAO;
	
	/**
	 * @return the personalDAO
	 */
	public PersonalDAO getPersonalDAO() {
		return personalDAO;
	}
	/**
	 * @param personalDAO the personalDAO to set
	 */
	public void setPersonalDAO(PersonalDAO personalDAO) {
		this.personalDAO = personalDAO;
	}

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.service.business.PersonalManager#buscarPorEstadoRegistro(java.lang.String, java.lang.String)
	 */
	@Override
	@Transactional(readOnly=true)
	public ArrayList<Personal> buscarPorEstadoRegistro(String estado, String criterioOrden) throws Exception {
		return getPersonalDAO().buscarPorEstadoRegistro(estado, criterioOrden);
	}

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.service.business.PersonalManager#buscarPorX(java.util.TreeMap, java.util.List)
	 */
	@Override
	@Transactional(readOnly=true)
	public ArrayList<Personal> buscarPorX(TreeMap<String, Object> criteriosBusqueda, List<String> criteriosOrdenar) throws Exception {
		return getPersonalDAO().buscarPorX(criteriosBusqueda, criteriosOrdenar);
	}

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.service.business.PersonalManager#buscarPorId(java.lang.Long)
	 */
	@Override
	@Transactional(readOnly=true)
	public Personal buscarPorId(Long id) throws Exception {
		return getPersonalDAO().buscarPorId(id);
	}

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.service.business.PersonalManager#guardar(com.tepsa.sisvyr.model.bean.Personal)
	 */
	@Override
	@Transactional
	public void guardar(Personal personal) throws Exception {
		try{
			TreeMap<String, Object> criteriosBusqueda = new TreeMap<String, Object>();
			criteriosBusqueda.put("codigo", personal.getCodigo());
			criteriosBusqueda.put("estadoRegistro", Constantes.VALUE_ACTIVO);
			List<?> resultCodigo = getPersonalDAO().buscarPorX(criteriosBusqueda, null);
			/*Valida duplicidad del Código del Personal*/
			if(resultCodigo.size()>0)
				throw new CodigoDuplicadoException();
			
			criteriosBusqueda.remove("codigo");
			criteriosBusqueda.put("licencia", personal.getLicencia());
			criteriosBusqueda.put("estadoRegistro", Constantes.VALUE_ACTIVO);
			List<?> resultLicencia = getPersonalDAO().buscarPorX(criteriosBusqueda, null);
			/*Valida duplicidad del la Licencia del Personal*/
			if(resultLicencia.size()>0)
				throw new LicenciaDuplicadaException();
			
			criteriosBusqueda.remove("codigo");criteriosBusqueda.remove("licencia");
			criteriosBusqueda.put("nroDocumento", personal.getNroDocumento());
			criteriosBusqueda.put("estadoRegistro", Constantes.VALUE_ACTIVO);
			List<?> resultnroDocumento = getPersonalDAO().buscarPorX(criteriosBusqueda, null);
			/*Valida duplicidad del Número de Documento del Personal*/
			if(resultnroDocumento.size()>0)
				throw new NumeroDocumentoDuplicadoException();
			
			getPersonalDAO().guardar(personal);
		
		}catch (CodigoDuplicadoException cpdex){
			throw new CodigoDuplicadoException();
		}catch (LicenciaDuplicadaException ldex){
			throw new LicenciaDuplicadaException();
		}catch (NumeroDocumentoDuplicadoException nddex){
			throw new NumeroDocumentoDuplicadoException();
		}catch(Exception ex){
			throw new Exception(ex);
		}
	}

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.service.business.PersonalManager#actualizar(com.tepsa.sisvyr.model.bean.Personal)
	 */
	@Override
	@Transactional
	public void actualizar(Personal personal) throws Exception {
		try{
			TreeMap<String, Object> criteriosBusqueda = new TreeMap<String, Object>();
			criteriosBusqueda.put("codigo", personal.getCodigo());
			criteriosBusqueda.put("estadoRegistro", Constantes.VALUE_ACTIVO);
			List<?> resultCodigo = getPersonalDAO().buscarPorX(criteriosBusqueda, null);
			/*Valida duplicidad del Código del Personal*/
			for(int r = 0; r < resultCodigo.size(); r ++) {
				Personal operosnaCod= (Personal) resultCodigo.get(r);
					if (!(operosnaCod.getId().equals(personal.getId())))
						throw new CodigoDuplicadoException();
				}
			
			criteriosBusqueda.remove("codigo");
			criteriosBusqueda.put("licencia", personal.getLicencia());
			criteriosBusqueda.put("estadoRegistro", Constantes.VALUE_ACTIVO);
			List<?> resultLicencia = getPersonalDAO().buscarPorX(criteriosBusqueda, null);
			/*Valida duplicidad del la Licencia del Personal*/
			for(int r = 0; r < resultLicencia.size(); r ++) {
				Personal opersonalLic= (Personal) resultLicencia.get(r);
					if (!(opersonalLic.getId().equals(personal.getId())))
						throw new LicenciaDuplicadaException();
				}		
			
			criteriosBusqueda.remove("codigo");criteriosBusqueda.remove("licencia");
			criteriosBusqueda.put("nroDocumento", personal.getNroDocumento());
			criteriosBusqueda.put("tipoDocumento", personal.getTipoDocumento());
			criteriosBusqueda.put("estadoRegistro", Constantes.VALUE_ACTIVO);
			List<?> resultnroDocumento = getPersonalDAO().buscarPorX(criteriosBusqueda, null);
			/*Valida duplicidad del Número de Documento del Personal*/
			for(int r = 0; r < resultnroDocumento.size(); r ++) {
				Personal opersonalNroDocumento= (Personal) resultnroDocumento.get(r);
					if (!(opersonalNroDocumento.getId().equals(personal.getId())))
						throw new NumeroDocumentoDuplicadoException();
				}		
			
			getPersonalDAO().actualizar(personal);
		
		}catch (CodigoDuplicadoException cpdex){
			throw new CodigoDuplicadoException();
		}catch (LicenciaDuplicadaException ldex){
			throw new LicenciaDuplicadaException();
		}catch (NumeroDocumentoDuplicadoException nddex){
			throw new NumeroDocumentoDuplicadoException();
		}catch(Exception ex){
			throw new Exception(ex);
		}
	}

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.service.business.PersonalManager#inactivar(java.lang.Long)
	 */
	@Override
	@Transactional
	public void inactivar(Long id) throws Exception {
		getPersonalDAO().inactivar(id);
	}
	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.service.business.PersonalManager#buscarMailsXRols(java.lang.String)
	 */
	@Override
	public String buscarMailsXRols(String iDsRol) throws Exception {
		// TODO Auto-generated method stub
		return getPersonalDAO().buscarMailsXRols(iDsRol);
	}

}
