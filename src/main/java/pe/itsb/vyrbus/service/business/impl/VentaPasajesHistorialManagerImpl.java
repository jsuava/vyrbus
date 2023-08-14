/**
 * Proyecto		: SISVYR
 * Sistema		: Sistema de Ventas y Reservas
 * Descripci�n	: Implementaci�n de m�todos que permiten el acceso al modelo.
 * Autor		: Jos� Avalos
 * Fecha		: 28/09/2012
 */
package pe.itsb.vyrbus.service.business.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

import pe.itsb.vyrbus.model.bean.VentaPasajeHistorial;
import pe.itsb.vyrbus.model.dao.VentaPasajesHistorialDAO;
import pe.itsb.vyrbus.service.business.VentaPasajesHistorialManager;

/**
 * @author Jose
 *
 */
public class VentaPasajesHistorialManagerImpl implements VentaPasajesHistorialManager {
	private VentaPasajesHistorialDAO ventaPasajesHistorialDAO;

	/**
	 * @return the ventaPasajesHistorialDAO
	 */
	public VentaPasajesHistorialDAO getVentaPasajesHistorialDAO() {
		return ventaPasajesHistorialDAO;
	}

	/**
	 * @param ventaPasajesHistorialDAO the ventaPasajesHistorialDAO to set
	 */
	public void setVentaPasajesHistorialDAO(VentaPasajesHistorialDAO ventaPasajesHistorialDAO) {
		this.ventaPasajesHistorialDAO = ventaPasajesHistorialDAO;
	}

	/* (non-Javadoc)
	 * @see pe.itsb.vyrbus.service.business.VentaPasajesHistorialManager#buscarPorEstadoRegistro(java.lang.String, java.lang.String)
	 */
	@Override
	public ArrayList<VentaPasajeHistorial> buscarPorEstadoRegistro(String estado, String criterioOrden) {
		// TODO Auto-generated method stub
		return getVentaPasajesHistorialDAO().buscarPorEstadoRegistro(estado, criterioOrden);
	}

	/* (non-Javadoc)
	 * @see pe.itsb.vyrbus.service.business.VentaPasajesHistorialManager#buscarPorX(java.util.TreeMap, java.util.List)
	 */
	@Override
	public ArrayList<VentaPasajeHistorial> buscarPorX(TreeMap<String, Object> criteriosBusqueda,
			List<String> criteriosOrdenar) {
		// TODO Auto-generated method stub
		return getVentaPasajesHistorialDAO().buscarPorX(criteriosBusqueda, criteriosOrdenar);
	}

	/* (non-Javadoc)
	 * @see pe.itsb.vyrbus.service.business.VentaPasajesHistorialManager#buscarPorId(java.lang.Long)
	 */
	@Override
	public VentaPasajeHistorial buscarPorId(Long id) {
		// TODO Auto-generated method stub
		return getVentaPasajesHistorialDAO().buscarPorId(id);
	}

	/* (non-Javadoc)
	 * @see pe.itsb.vyrbus.service.business.VentaPasajesHistorialManager#guardar(pe.itsb.vyrbus.model.bean.VentaPasajeHistorial)
	 */
	@Override
	public void guardar(VentaPasajeHistorial ventaPasajeHistorial) {
		// TODO Auto-generated method stub
		getVentaPasajesHistorialDAO().guardar(ventaPasajeHistorial);
	}

	/* (non-Javadoc)
	 * @see pe.itsb.vyrbus.service.business.VentaPasajesHistorialManager#actualizar(pe.itsb.vyrbus.model.bean.VentaPasajeHistorial)
	 */
	@Override
	public void actualizar(VentaPasajeHistorial ventaPasajeHistorial) {
		// TODO Auto-generated method stub
		getVentaPasajesHistorialDAO().actualizar(ventaPasajeHistorial);
	}

}
