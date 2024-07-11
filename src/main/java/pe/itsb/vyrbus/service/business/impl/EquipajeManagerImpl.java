/**
 * Proyecto		: SISVYR
 * Sistema		: Sistema de Ventas y Reservas
 * Descripci�n	:
 * Autor		: Jos� Abanto
 * Fecha		: 14 jul. 2021
 * Hora			: 12:14:59
 */
package pe.itsb.vyrbus.service.business.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

import org.springframework.transaction.annotation.Transactional;

import pe.itsb.vyrbus.model.bean.Equipaje;
import pe.itsb.vyrbus.model.dao.EquipajeDAO;
import pe.itsb.vyrbus.service.business.EquipajeManager;

/**
 * @author abant
 *
 */
public class EquipajeManagerImpl implements EquipajeManager{
	private EquipajeDAO equipajeDAO;

	/**
	 * @return the equipajeDAO
	 */
	public EquipajeDAO getEquipajeDAO() {
		return equipajeDAO;
	}

	/**
	 * @param equipajeDAO the equipajeDAO to set
	 */
	public void setEquipajeDAO(EquipajeDAO equipajeDAO) {
		this.equipajeDAO = equipajeDAO;
	}

	/* (non-Javadoc)
	 * @see pe.itsb.vyrbus.service.business.EquipajeManager#buscarPorEstadoRegistro(java.lang.String, java.lang.String)
	 */
	@Override
	public ArrayList<Equipaje> buscarPorEstadoRegistro(String estado, String criterioOrden) {
		// TODO Auto-generated method stub
		return getEquipajeDAO().buscarPorEstadoRegistro(estado, criterioOrden);
	}

	/* (non-Javadoc)
	 * @see pe.itsb.vyrbus.service.business.EquipajeManager#buscarPorX(java.util.TreeMap, java.util.List)
	 */
	@Override
	public ArrayList<Equipaje> buscarPorX(TreeMap<String, Object> criteriosBusqueda, List<String> criteriosOrdenar) {
		// TODO Auto-generated method stub
		return getEquipajeDAO().buscarPorX(criteriosBusqueda, criteriosOrdenar);
	}

	/* (non-Javadoc)
	 * @see pe.itsb.vyrbus.service.business.EquipajeManager#buscarPorId(java.lang.Long)
	 */
	@Override
	public Equipaje buscarPorId(Long id) {
		// TODO Auto-generated method stub
		return getEquipajeDAO().buscarPorId(id);
	}

	/* (non-Javadoc)
	 * @see pe.itsb.vyrbus.service.business.EquipajeManager#guardar(pe.itsb.vyrbus.model.bean.Equipaje)
	 */
	@Transactional
	@Override
	public void guardar(Equipaje equipaje) {
		// TODO Auto-generated method stub
		getEquipajeDAO().guardar(equipaje);
	}

	/* (non-Javadoc)
	 * @see pe.itsb.vyrbus.service.business.EquipajeManager#actualizar(pe.itsb.vyrbus.model.bean.Equipaje)
	 */
	@Transactional
	@Override
	public void actualizar(Equipaje equipaje) {
		// TODO Auto-generated method stub
		getEquipajeDAO().actualizar(equipaje);
	}

	/* (non-Javadoc)
	 * @see pe.itsb.vyrbus.service.business.EquipajeManager#buscar(java.lang.String, java.lang.String, java.lang.Integer, java.lang.Integer, java.lang.Integer)
	 */
	@Override
	public List<Equipaje> buscar(String fechaInicio, String fechaFin, Integer agenciaIdEmbaruqe,
			Integer localidadIdOrigen, Integer localidadIdDestino) {
		
		return getEquipajeDAO().buscar(fechaInicio, fechaFin, agenciaIdEmbaruqe, localidadIdOrigen, localidadIdDestino);
	}
}
