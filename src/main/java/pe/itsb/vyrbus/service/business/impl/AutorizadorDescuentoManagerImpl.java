/**
 * Proyecto		: SISVYR
 * Sistema		: Sistema de Ventas y Reservas
 * Descripci�n	: Implementacion de metodos que permiten el acceso al modelo.
 * Autor		: Jos� Avalos
 * Fecha		: 27/09/2012
 */
package pe.itsb.vyrbus.service.business.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

import org.springframework.transaction.annotation.Transactional;

import pe.itsb.vyrbus.model.bean.AutorizadorDescuento;
import pe.itsb.vyrbus.model.dao.AutorizadorDescuentoDAO;
import pe.itsb.vyrbus.service.business.AutorizadorDescuentoManager;

/**
 * @author Jose
 *
 */
public class AutorizadorDescuentoManagerImpl implements AutorizadorDescuentoManager {
	private AutorizadorDescuentoDAO autorizadorDescuentoDAO;

	/**
	 * @return the busDAO
	 */
	public AutorizadorDescuentoDAO getAutorizadorDescuentoDAO() {
		return autorizadorDescuentoDAO;
	}
	/**
	 * @param autorizadorDescuentoDAO the busDAO to set
	 */
	public void setAutorizadorDescuentoDAO(AutorizadorDescuentoDAO autorizadorDescuentoDAO) {
		this.autorizadorDescuentoDAO = autorizadorDescuentoDAO;
	}

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.service.business.BusManager#buscarPorEstadoRegistro(java.lang.String, java.lang.String)
	 */
	@Override
	@Transactional(readOnly=true)
	public ArrayList<AutorizadorDescuento> buscarPorEstadoRegistro(String estado, String criterioOrden) throws Exception {
		return getAutorizadorDescuentoDAO().buscarPorEstadoRegistro(estado, criterioOrden);
	}

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.service.business.BusManager#buscarPorX(java.util.TreeMap, java.util.List)
	 */
	@Override
	@Transactional (readOnly=true)
	public ArrayList<AutorizadorDescuento> buscarPorX(TreeMap<String, Object> criteriosBusqueda, List<String> criteriosOrdenar) throws Exception {
		return getAutorizadorDescuentoDAO().buscarPorX(criteriosBusqueda, criteriosOrdenar);
	}

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.service.business.BusManager#buscarPorId(java.lang.Long)
	 */
	@Override
	@Transactional (readOnly=true)
	public AutorizadorDescuento buscarPorId(Long id) throws Exception {
		return getAutorizadorDescuentoDAO().buscarPorId(id);
	}

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.service.business.BusManager#guardar(com.tepsa.sisvyr.model.bean.Bus)
	 */
	@Override
	@Transactional
	public void guardar(AutorizadorDescuento autorizadorDescuento) throws Exception {
		try{

			getAutorizadorDescuentoDAO().guardar(autorizadorDescuento);

		}catch (Exception ex){
			throw new Exception(ex);
		}
	}

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.service.business.BusManager#actualizar(com.tepsa.sisvyr.model.bean.Bus)
	 */
	@Override
	@Transactional
	public void actualizar(AutorizadorDescuento autorizadorDescuento) throws Exception {
		try{
		
			getAutorizadorDescuentoDAO().actualizar(autorizadorDescuento);

		}catch (Exception ex){
			throw new Exception(ex);
		}
	}


}
