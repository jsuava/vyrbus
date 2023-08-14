/**
 * Proyecto		: SISVYR
 * Sistema		: Sistema de Ventas y Reservas
 * Descripción	:
 * Autor		: José Abanto
 * Fecha		: 27 jun. 2021
 * Hora			: 22:45:11
 */
package pe.itsb.vyrbus.model.dao.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

import pe.itsb.vyrbus.model.bean.VentaServicioEspecial;
import pe.itsb.vyrbus.model.dao.VentaServicioEspecialDAO;

/**
 * @author Jose
 *
 */
@SuppressWarnings("unchecked")
public class VentaServicioEspecialDAOImpl extends GenericDAOImpl implements VentaServicioEspecialDAO {

	/* (non-Javadoc)
	 * @see pe.itsb.vyrbus.model.dao.VentaServicioEspecialDAO#buscarPorEstadoRegistro(java.lang.String, java.lang.String)
	 */
	@Override
	public ArrayList<VentaServicioEspecial> buscarPorEstadoRegistro(String estado, String criterioOrden) {
		return (ArrayList<VentaServicioEspecial>) super.findByEstadoRegistro(VentaServicioEspecial.class, estado, criterioOrden);
	}

	/* (non-Javadoc)
	 * @see pe.itsb.vyrbus.model.dao.VentaServicioEspecialDAO#buscarPorX(java.util.TreeMap, java.util.List)
	 */
	@Override
	public ArrayList<VentaServicioEspecial> buscarPorX(TreeMap<String, Object> criteriosBusqueda, List<String> criteriosOrdenar) {
		return (ArrayList<VentaServicioEspecial>) super.findByX(VentaServicioEspecial.class, criteriosBusqueda, criteriosOrdenar);
	}

	/* (non-Javadoc)
	 * @see pe.itsb.vyrbus.model.dao.VentaServicioEspecialDAO#buscarPorId(java.lang.Long)
	 */
	@Override
	public VentaServicioEspecial buscarPorId(Long id) {
		return (VentaServicioEspecial) super.findById(VentaServicioEspecial.class, id);
	}

	/* (non-Javadoc)
	 * @see pe.itsb.vyrbus.model.dao.VentaServicioEspecialDAO#guardar(pe.itsb.vyrbus.model.bean.VentaServicioEspecial)
	 */
	@Override
	public void guardar(VentaServicioEspecial ventaServicioEspecial) {
		super.save(ventaServicioEspecial);
	}

	/* (non-Javadoc)
	 * @see pe.itsb.vyrbus.model.dao.VentaServicioEspecialDAO#actualizar(pe.itsb.vyrbus.model.bean.VentaServicioEspecial)
	 */
	@Override
	public void actualizar(VentaServicioEspecial ventaServicioEspecial) {
		super.update(ventaServicioEspecial);
	}

	/* (non-Javadoc)
	 * @see pe.itsb.vyrbus.model.dao.VentaServicioEspecialDAO#inactivar(long)
	 */
	@Override
	public void inactivar(long id) {
		super.inactivate(VentaServicioEspecial.class, id);
	}

}
