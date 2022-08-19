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

import com.cystesoft.vyrbus.model.bean.PasajeroFrecuente;
import com.cystesoft.vyrbus.model.dao.PasajeroFrecuenteDAO;
import com.cystesoft.vyrbus.service.business.PasajeroFrecuenteManager;
import com.cystesoft.vyrbus.service.exceptions.TarjetaDuplicadaException;
import com.cystesoft.vyrbus.service.util.Constantes;

/**
 * @author Jose
 *
 */
public class PasajeroFrecuenteManagerImpl implements PasajeroFrecuenteManager {
	private PasajeroFrecuenteDAO pasajeroFrecuenteDAO;

	/**
	 * @return the pasajeroFrecuenteDAO
	 */
	public PasajeroFrecuenteDAO getPasajeroFrecuenteDAO() {
		return pasajeroFrecuenteDAO;
	}
	/**
	 * @param pasajeroFrecuenteDAO the pasajeroFrecuenteDAO to set
	 */
	public void setPasajeroFrecuenteDAO(PasajeroFrecuenteDAO pasajeroFrecuenteDAO) {
		this.pasajeroFrecuenteDAO = pasajeroFrecuenteDAO;
	}

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.service.business.PasajeroFrecuenteManager#buscarPorEstadoRegistro(java.lang.String, java.lang.String)
	 */
	@Override
	@Transactional(readOnly=true)
	public ArrayList<PasajeroFrecuente> buscarPorEstadoRegistro(String estado, String criterioOrden) throws Exception {
		return getPasajeroFrecuenteDAO().buscarPorEstadoRegistro(estado, criterioOrden);
	}

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.service.business.PasajeroFrecuenteManager#buscarPorX(java.util.TreeMap, java.util.List)
	 */
	@Override
	@Transactional(readOnly=true)
	public ArrayList<PasajeroFrecuente> buscarPorX(TreeMap<String, Object> criteriosBusqueda, List<String> criteriosOrdenar) throws Exception {
		return getPasajeroFrecuenteDAO().buscarPorX(criteriosBusqueda, criteriosOrdenar);
	}

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.service.business.PasajeroFrecuenteManager#buscarPorId(java.lang.Long)
	 */
	@Override
	@Transactional(readOnly=true)
	public PasajeroFrecuente buscarPorId(Long id) throws Exception {
		return getPasajeroFrecuenteDAO().buscarPorId(id);
	}

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.service.business.PasajeroFrecuenteManager#guardar(com.tepsa.sisvyr.model.bean.PasajeroFrecuente)
	 */
	@Override
	@Transactional
	public void guardar(PasajeroFrecuente pasajeroFrecuente) throws Exception {
		try{
			TreeMap<String, Object> criteriosBusqueda = new TreeMap<>();
			criteriosBusqueda.put("numeroTarjeta", pasajeroFrecuente.getNumeroTarjeta());
			criteriosBusqueda.put("estadoRegistro", Constantes.VALUE_ACTIVO);
			List<?> result = getPasajeroFrecuenteDAO().buscarPorX(criteriosBusqueda, null);

			/*Valida duplicidad de la tarjeta*/
			if(result.size()>0)
				throw new TarjetaDuplicadaException();

			getPasajeroFrecuenteDAO().guardar(pasajeroFrecuente);

		}catch (TarjetaDuplicadaException tdex){
			throw new TarjetaDuplicadaException();
		}catch(Exception ex){
			throw new Exception(ex);
		}
	}

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.service.business.PasajeroFrecuenteManager#actualizar(com.tepsa.sisvyr.model.bean.PasajeroFrecuente)
	 */
	@Override
	@Transactional
	public void actualizar(PasajeroFrecuente pasajeroFrecuente) throws Exception {
		try{
//			TreeMap<String, Object> criteriosBusqueda = new TreeMap<String, Object>();
//			criteriosBusqueda.put("numeroTarjeta", pasajeroFrecuente.getNumeroTarjeta());
//			criteriosBusqueda.put("estadoRegistro", Constantes.VALUE_ACTIVO);
//			List<?> result = getPasajeroFrecuenteDAO().buscarPorX(criteriosBusqueda, null);
//
//			/*Valida duplicidad del numero de tarjeta*/
//			for(int r = 0; r < result.size(); r ++) {
//				PasajeroFrecuente opasajeroFrecuente = (PasajeroFrecuente) result.get(r);
//					if (!(opasajeroFrecuente.getId() == pasajeroFrecuente.getId()))
//						throw new TarjetaDuplicadaException();
//			}

			getPasajeroFrecuenteDAO().actualizar(pasajeroFrecuente);

		}catch(Exception ex){
			throw new Exception(ex);
		}
	}

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.service.business.PasajeroFrecuenteManager#inactivar(java.lang.Long)
	 */
	@Override
	@Transactional
	public void inactivar(Long id) throws Exception {
		getPasajeroFrecuenteDAO().inactivar(id);
	}

	/*
	 * (non-Javadoc)
	 * @see com.tepsa.sisvyr.service.business.PasajeroFrecuenteManager#buscarMaxNumTarjeta()
	 */
	@Override
	public PasajeroFrecuente buscarMaxNumTarjeta() throws Exception {
		return getPasajeroFrecuenteDAO().buscarMaxNumTarjeta();
	}
	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.service.business.PasajeroFrecuenteManager#buscarPaxFree(java.lang.Long, java.lang.Integer)
	 */
	@Override
	public PasajeroFrecuente buscarPaxFree(Long idPasajero, Integer estado)throws Exception {
		return getPasajeroFrecuenteDAO().buscarPaxFree(idPasajero, estado);
	}

	/*
	 * (non-Javadoc)
	 * @see com.tepsa.sisvyr.service.business.PasajeroFrecuenteManager#buscarPaxFree(java.lang.Long)
	 */
	@Override
	public PasajeroFrecuente buscarPaxFree(Long idPasajero)throws Exception{
		return getPasajeroFrecuenteDAO().buscarPaxFree(idPasajero);
	}
	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.service.business.PasajeroFrecuenteManager#buscarPuntos(java.lang.Long)
	 */
	@Override
	public PasajeroFrecuente buscarPaxFreeAndPuntos(Long idPasajero,Integer estado) throws Exception {
		return getPasajeroFrecuenteDAO().buscarPaxFreeAndPuntos(idPasajero, estado);
	}
	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.service.business.PasajeroFrecuenteManager#buscarPaxfreeXNumeroDocumento(java.lang.String)
	 */
	@Override
	public PasajeroFrecuente buscarPaxfreeXNumeroDocumento(String numeroDocumento) throws Exception {
		return getPasajeroFrecuenteDAO().buscarPaxfreeXNumeroDocumento(numeroDocumento);
	}

}
