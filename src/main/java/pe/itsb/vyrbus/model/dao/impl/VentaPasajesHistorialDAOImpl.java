/**
 * Proyecto		: SISVYR
 * Sistema		: Sistema de Ventas y Reservas
 * Descripci�n	: Implementaci�n de metodos relacionados con la Venta de Pasajes.
 * Autor		: Jos� Avalos
 * Fecha		: 09/07/2012
 */
package pe.itsb.vyrbus.model.dao.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

import pe.itsb.vyrbus.model.bean.VentaPasajeHistorial;
import pe.itsb.vyrbus.model.dao.VentaPasajesHistorialDAO;

/**
 *
 * @author Jos� Avalos
 * @since JDK1.6
 */
@SuppressWarnings("unchecked")
public class VentaPasajesHistorialDAOImpl extends GenericDAOImpl implements VentaPasajesHistorialDAO {

	/* (non-Javadoc)
	 * @see pe.itsb.vyrbus.model.dao.VentaPasajesHistorialDAO#buscarPorEstadoRegistro(java.lang.String, java.lang.String)
	 */
	@Override
	public ArrayList<VentaPasajeHistorial> buscarPorEstadoRegistro(String estado, String criterioOrden) {
		// TODO Auto-generated method stub
		return (ArrayList<VentaPasajeHistorial>) super.findByEstadoRegistro(VentaPasajeHistorial.class, estado, criterioOrden);
	}

	/* (non-Javadoc)
	 * @see pe.itsb.vyrbus.model.dao.VentaPasajesHistorialDAO#buscarPorX(java.util.TreeMap, java.util.List)
	 */
	@Override
	public ArrayList<VentaPasajeHistorial> buscarPorX(TreeMap<String, Object> criteriosBusqueda,
			List<String> criteriosOrdenar) {
		// TODO Auto-generated method stub
		return (ArrayList<VentaPasajeHistorial>) super.findByX(VentaPasajeHistorial.class, criteriosBusqueda, criteriosOrdenar);
	}

	/* (non-Javadoc)
	 * @see pe.itsb.vyrbus.model.dao.VentaPasajesHistorialDAO#buscarPorId(java.lang.Long)
	 */
	@Override
	public VentaPasajeHistorial buscarPorId(Long id) {
		// TODO Auto-generated method stub
		return (VentaPasajeHistorial) super.findById(VentaPasajeHistorial.class, id);
	}

	/* (non-Javadoc)
	 * @see pe.itsb.vyrbus.model.dao.VentaPasajesHistorialDAO#guardar(pe.itsb.vyrbus.model.bean.VentaPasajeHistorial)
	 */
	@Override
	public void guardar(VentaPasajeHistorial ventaPasajeHistorial) {
		// TODO Auto-generated method stub
		super.save(ventaPasajeHistorial);
	}

	/* (non-Javadoc)
	 * @see pe.itsb.vyrbus.model.dao.VentaPasajesHistorialDAO#actualizar(pe.itsb.vyrbus.model.bean.VentaPasajeHistorial)
	 */
	@Override
	public void actualizar(VentaPasajeHistorial ventaPasajeHistorial) {
		// TODO Auto-generated method stub
		super.update(ventaPasajeHistorial);
	}


}

