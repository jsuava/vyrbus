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

import com.cystesoft.vyrbus.model.bean.DocumentoBus;
import com.cystesoft.vyrbus.model.dao.DocumentoBusDAO;
import com.cystesoft.vyrbus.service.business.DocumentoBusManager;
import com.cystesoft.vyrbus.service.exceptions.NumeroBusDuplicadoException;
import com.cystesoft.vyrbus.service.exceptions.NumeroDocumentoDuplicadoException;
import com.cystesoft.vyrbus.service.util.Constantes;

/**
 * @author Jose
 *
 */
public class DocumentoBusManagerImpl implements DocumentoBusManager {
	private DocumentoBusDAO documentoBusDAO;
	
	/**
	 * @return the documentoBusDAO
	 */
	public DocumentoBusDAO getDocumentoBusDAO() {
		return documentoBusDAO;
	}
	/**
	 * @param documentoBusDAO the documentoBusDAO to set
	 */
	public void setDocumentoBusDAO(DocumentoBusDAO documentoBusDAO) {
		this.documentoBusDAO = documentoBusDAO;
	}

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.service.business.DocumentoBusManager#buscarPorEstadoRegistro(java.lang.String, java.lang.String)
	 */
	@Override
	@Transactional(readOnly=true)
	public ArrayList<DocumentoBus> buscarPorEstadoRegistro(String estado, String criterioOrden) throws Exception {
		return getDocumentoBusDAO().buscarPorEstadoRegistro(estado, criterioOrden);
	}

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.service.business.DocumentoBusManager#buscarPorX(java.util.TreeMap, java.util.List)
	 */
	@Override
	@Transactional(readOnly=true)
	public ArrayList<DocumentoBus> buscarPorX(TreeMap<String, Object> criteriosBusqueda, List<String> criteriosOrdenar) throws Exception {
		return getDocumentoBusDAO().buscarPorX(criteriosBusqueda, criteriosOrdenar);
	}

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.service.business.DocumentoBusManager#buscarPorId(java.lang.Long)
	 */
	@Override
	@Transactional(readOnly=true)
	public DocumentoBus buscarPorId(Long id) throws Exception {
		return getDocumentoBusDAO().buscarPorId(id);
	}

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.service.business.DocumentoBusManager#guardar(com.tepsa.sisvyr.model.bean.DocumentoBus)
	 */
	@Override
	@Transactional
	public void guardar(DocumentoBus documentoBus) throws Exception {
		try{
			TreeMap<String, Object> criteriosBusqueda = new TreeMap<String, Object>();
			criteriosBusqueda.put("numeroDocumento", documentoBus.getNumeroDocumento());
			criteriosBusqueda.put("estadoRegistro", Constantes.VALUE_ACTIVO);
			List<?> result = getDocumentoBusDAO().buscarPorX(criteriosBusqueda, null);
			/*Valida duplicidad del número de Documento del bus*/
			if(result.size()>0)
				throw new NumeroDocumentoDuplicadoException();
			
			criteriosBusqueda.remove("numeroDocumento");
			criteriosBusqueda.put("tipoDocumento", documentoBus.getTipoDocumento());
			criteriosBusqueda.put("bus", documentoBus.getBus());
			criteriosBusqueda.put("estadoRegistro", Constantes.VALUE_ACTIVO);
			List<?> resultTDocBus = getDocumentoBusDAO().buscarPorX(criteriosBusqueda, null);
								
			/*Valida duplicidad del tipo de documento y el número del bus*/
			if(resultTDocBus.size()>0)
				throw new NumeroBusDuplicadoException();
			
			getDocumentoBusDAO().guardar(documentoBus);
			
		}catch (NumeroDocumentoDuplicadoException rsdex){
			throw new NumeroDocumentoDuplicadoException();
		}catch (NumeroBusDuplicadoException rsdex){
			throw new NumeroBusDuplicadoException();
		}catch(Exception ex){
			throw new Exception(ex);
		}
	}

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.service.business.DocumentoBusManager#actualizar(com.tepsa.sisvyr.model.bean.DocumentoBus)
	 */
	@Override
	@Transactional
	public void actualizar(DocumentoBus documentoBus) throws Exception {
		try{
			TreeMap<String, Object> criteriosBusqueda = new TreeMap<String, Object>();
			
			criteriosBusqueda.put("numeroDocumento", documentoBus.getNumeroDocumento());
			criteriosBusqueda.put("estadoRegistro", Constantes.VALUE_ACTIVO);
			List<?> result = getDocumentoBusDAO().buscarPorX(criteriosBusqueda, null);
			/*Valida duplicidad del número de documento*/
			for(int r = 0; r < result.size(); r ++) {
				DocumentoBus odocumentoBus = (DocumentoBus) result.get(r);
				if (!(odocumentoBus.getId() == documentoBus.getId()))
					throw new NumeroDocumentoDuplicadoException();
			}
			
			criteriosBusqueda.remove("numeroDocumento");
			criteriosBusqueda.put("tipoDocumento", documentoBus.getTipoDocumento());
			criteriosBusqueda.put("bus", documentoBus.getBus());
			criteriosBusqueda.put("estadoRegistro", Constantes.VALUE_ACTIVO);
			List<?> resultTDocBus = getDocumentoBusDAO().buscarPorX(criteriosBusqueda, null);
			
			/*Valida duplicidad del tipo de documento y el número del bus*/
			for(int r = 0; r < resultTDocBus.size(); r ++) {
				DocumentoBus odocumentoBus = (DocumentoBus) resultTDocBus.get(r);
				if (!(odocumentoBus.getId() == documentoBus.getId()))
					throw new NumeroBusDuplicadoException();
			}
			
			getDocumentoBusDAO().actualizar(documentoBus);
		
		}catch (NumeroDocumentoDuplicadoException rsdex){
			throw new NumeroDocumentoDuplicadoException();
		}catch (NumeroBusDuplicadoException rsdex){
			throw new NumeroBusDuplicadoException();
		}catch(Exception ex){
			throw new Exception(ex);
		}
	}

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.service.business.DocumentoBusManager#inactivar(java.lang.Long)
	 */
	@Override
	@Transactional
	public void inactivar(Long id) throws Exception {
		getDocumentoBusDAO().inactivar(id);
	}

}
