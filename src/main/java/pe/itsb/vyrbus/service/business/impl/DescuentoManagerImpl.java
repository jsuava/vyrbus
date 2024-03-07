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

import pe.itsb.vyrbus.model.bean.Descuento;
import pe.itsb.vyrbus.model.dao.DescuentoDAO;
import pe.itsb.vyrbus.service.business.DescuentoManager;

/**
 * @author Jose
 *
 */
public class DescuentoManagerImpl implements DescuentoManager {
	private DescuentoDAO descuentoDAO;

	/**
	 * @return the busDAO
	 */
	public DescuentoDAO getDescuentoDAO() {
		return descuentoDAO;
	}
	/**
	 * @param descuentoDAO the busDAO to set
	 */
	public void setDescuentoDAO(DescuentoDAO descuentoDAO) {
		this.descuentoDAO = descuentoDAO;
	}

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.service.business.BusManager#buscarPorEstadoRegistro(java.lang.String, java.lang.String)
	 */
	@Override
	@Transactional(readOnly=true)
	public ArrayList<Descuento> buscarPorEstadoRegistro(String estado, String criterioOrden) throws Exception {
		return getDescuentoDAO().buscarPorEstadoRegistro(estado, criterioOrden);
	}

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.service.business.BusManager#buscarPorX(java.util.TreeMap, java.util.List)
	 */
	@Override
	@Transactional (readOnly=true)
	public ArrayList<Descuento> buscarPorX(TreeMap<String, Object> criteriosBusqueda, List<String> criteriosOrdenar) throws Exception {
		return getDescuentoDAO().buscarPorX(criteriosBusqueda, criteriosOrdenar);
	}

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.service.business.BusManager#buscarPorId(java.lang.Long)
	 */
	@Override
	@Transactional (readOnly=true)
	public Descuento buscarPorId(Long id) throws Exception {
		return getDescuentoDAO().buscarPorId(id);
	}

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.service.business.BusManager#guardar(com.tepsa.sisvyr.model.bean.Bus)
	 */
	@Override
	@Transactional
	public void guardar(Descuento descuento) throws Exception {
		try{

			getDescuentoDAO().guardar(descuento);

		}catch (Exception ex){
			throw new Exception(ex);
		}
	}

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.service.business.BusManager#actualizar(com.tepsa.sisvyr.model.bean.Bus)
	 */
	@Override
	@Transactional
	public void actualizar(Descuento descuento) throws Exception {
		try{
		
			getDescuentoDAO().actualizar(descuento);

		}catch (Exception ex){
			throw new Exception(ex);
		}
	}


}
