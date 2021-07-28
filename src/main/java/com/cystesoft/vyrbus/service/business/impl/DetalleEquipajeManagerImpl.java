/**
 * Proyecto		: SISVYR
 * Sistema		: Sistema de Ventas y Reservas
 * Descripción	: 
 * Autor		: José Abanto
 * Fecha		: 14 jul. 2021
 * Hora			: 12:18:25
 */
package com.cystesoft.vyrbus.service.business.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

import org.springframework.transaction.annotation.Transactional;

import com.cystesoft.vyrbus.model.bean.ControlEspecieValorada;
import com.cystesoft.vyrbus.model.bean.DetalleEquipaje;
import com.cystesoft.vyrbus.model.bean.Equipaje;
import com.cystesoft.vyrbus.model.bean.VentaPasaje;
import com.cystesoft.vyrbus.model.dao.ControlEspecieValoradaDAO;
import com.cystesoft.vyrbus.model.dao.DetalleEquipajeDAO;
import com.cystesoft.vyrbus.model.dao.EquipajeDAO;
import com.cystesoft.vyrbus.service.business.DetalleEquipajeManager;
import com.cystesoft.vyrbus.service.locator.ServiceLocator;
import com.cystesoft.vyrbus.service.util.Constantes;
import com.cystesoft.vyrbus.service.util.UtilData;

/**
 * @author abant
 *
 */
public class DetalleEquipajeManagerImpl implements DetalleEquipajeManager{
	private DetalleEquipajeDAO detalleEquipajeDAO;
	private EquipajeDAO equipajeDAO;	
	private ControlEspecieValoradaDAO controlEspecieValoradaDAO;
	
	/**
	 * @return the detalleEquipajeDAO
	 */
	public DetalleEquipajeDAO getDetalleEquipajeDAO() {
		return detalleEquipajeDAO;
	}

	/**
	 * @param detalleEquipajeDAO the detalleEquipajeDAO to set
	 */
	public void setDetalleEquipajeDAO(DetalleEquipajeDAO detalleEquipajeDAO) {
		this.detalleEquipajeDAO = detalleEquipajeDAO;
	}
	
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

	/**
	 * @return the controlEspecieValoradaDAO
	 */
	public ControlEspecieValoradaDAO getControlEspecieValoradaDAO() {
		return controlEspecieValoradaDAO;
	}

	/**
	 * @param controlEspecieValoradaDAO the controlEspecieValoradaDAO to set
	 */
	public void setControlEspecieValoradaDAO(ControlEspecieValoradaDAO controlEspecieValoradaDAO) {
		this.controlEspecieValoradaDAO = controlEspecieValoradaDAO;
	}
	
	
	/* (non-Javadoc)
	 * @see com.cystesoft.vyrbus.service.business.DetalleEquipajeManager#buscarPorEstadoRegistro(java.lang.String, java.lang.String)
	 */
	@Override
	public ArrayList<DetalleEquipaje> buscarPorEstadoRegistro(String estado, String criterioOrden) {
		// TODO Auto-generated method stub
		return getDetalleEquipajeDAO().buscarPorEstadoRegistro(estado, criterioOrden);
	}

	/* (non-Javadoc)
	 * @see com.cystesoft.vyrbus.service.business.DetalleEquipajeManager#buscarPorX(java.util.TreeMap, java.util.List)
	 */
	@Override
	public ArrayList<DetalleEquipaje> buscarPorX(TreeMap<String, Object> criteriosBusqueda,
			List<String> criteriosOrdenar) {
		// TODO Auto-generated method stub
		return getDetalleEquipajeDAO().buscarPorX(criteriosBusqueda, criteriosOrdenar);
	}

	/* (non-Javadoc)
	 * @see com.cystesoft.vyrbus.service.business.DetalleEquipajeManager#buscarPorId(java.lang.Long)
	 */
	@Override
	public DetalleEquipaje buscarPorId(Long id) {
		// TODO Auto-generated method stub
		return getDetalleEquipajeDAO().buscarPorId(id);
	}

	/* (non-Javadoc)
	 * @see com.cystesoft.vyrbus.service.business.DetalleEquipajeManager#guardar(com.cystesoft.vyrbus.model.bean.DetalleEquipaje)
	 */
	@Transactional
	@Override
	public void guardar(DetalleEquipaje detalleEquipaje) {
		// TODO Auto-generated method stub
		getDetalleEquipajeDAO().guardar(detalleEquipaje);
	}

	/* (non-Javadoc)
	 * @see com.cystesoft.vyrbus.service.business.DetalleEquipajeManager#actualizar(com.cystesoft.vyrbus.model.bean.DetalleEquipaje)
	 */
	@Transactional
	@Override
	public void actualizar(DetalleEquipaje detalleEquipaje) {
		// TODO Auto-generated method stub
		getDetalleEquipajeDAO().actualizar(detalleEquipaje);
	}

	/* (non-Javadoc)
	 * @see com.cystesoft.vyrbus.service.business.DetalleEquipajeManager#guardar(java.util.List, com.cystesoft.vyrbus.model.bean.Equipaje)
	 */
	@Override
	@Transactional
	public void guardar(List<DetalleEquipaje> listDetalleEquipaje, Equipaje equipaje) throws Exception {
		// TODO Auto-generated method stub

		//Guardar el equipaje
		getEquipajeDAO().guardar(equipaje);
		
		//Si existe Exceso
		VentaPasaje ventaExceso =null;
		if(listDetalleEquipaje.get(0).getVentaPasajeExceso()!=null) {
			ventaExceso = listDetalleEquipaje.get(0).getVentaPasajeExceso();
			
			//Valida si un comprobante con RUC y si este existe
			if(ventaExceso.getCliente()!=null && ventaExceso.getCliente().getId()==null) {
				ServiceLocator.getClienteManager().guardar(ventaExceso.getCliente());
			}
			
			//Graba el exceso
			//Obtenemos el correlativo actualizado 
//			ControlEspecieValorada controlEspecieValorada = UtilData.buscarEspecieValoradaByCaja(ventaExceso.getTipoComprobante().getId(), ventaExceso.getAgencia(), true, ventaExceso.getUsuarioHardware(), null);
//			ventaExceso.setNumeroBoleto(controlEspecieValorada.toString());
//			//Actualizamos el correlativo
//			int position = ventaExceso.getNumeroBoleto().indexOf("-");
//			Long correlativo = Long.valueOf(ventaExceso.getNumeroBoleto().substring(position+1))+1;
//			controlEspecieValorada.setCorrelativoActual(correlativo);
//			getControlEspecieValoradaDAO().update(controlEspecieValorada);
			
			ServiceLocator.getVentaPasajesManager().guardarVenta(ventaExceso, false, true, false, false);
		}
		
		//Guarda el detalle del equipaje
//		DetalleEquipaje detalleEquipaje = null;
		for(DetalleEquipaje _detalleEquipaje: listDetalleEquipaje) {			
			_detalleEquipaje.setEquipaje(equipaje);
			//Obtenemos el correlativo actualizado para los tickets
			ControlEspecieValorada controlEspecieValorada = UtilData.buscarEspecieValoradaByCaja(Constantes.ID_TIPCOM_TICKET_EQUIPAJE, ventaExceso.getAgencia(), true, ventaExceso.getUsuarioHardware(), null);
			_detalleEquipaje.setTicket(controlEspecieValorada.toString());
			//Actualizamos el correlativo
			int position = _detalleEquipaje.getTicket().indexOf("-");
			Long correlativo = Long.valueOf(_detalleEquipaje.getTicket().substring(position+1))+1;
			controlEspecieValorada.setCorrelativoActual(correlativo);
			getControlEspecieValoradaDAO().update(controlEspecieValorada);
			
			getDetalleEquipajeDAO().guardar(_detalleEquipaje);
//			detalleEquipaje = _detalleEquipaje;
		}
		
//		//Actualiza correlativos de los tikects
//		Long numeroCorrelativo = Long.valueOf(detalleEquipaje.getTicket().split("-")[1]);
//		ControlEspecieValorada controlEspecieValorada = UtilData.buscarEspecieValoradaByCaja(Constantes.ID_TIPCOM_TICKET_EQUIPAJE, equipaje.getAgencia(), false, equipaje.getUsuarioHardware(), null);
//		controlEspecieValorada.setCorrelativoActual(numeroCorrelativo+1);
//		getControlEspecieValoradaDAO().update(controlEspecieValorada);
	}

	/* (non-Javadoc)
	 * @see com.cystesoft.vyrbus.service.business.DetalleEquipajeManager#buscarManifiestoEquipaje(java.lang.Long, java.lang.Integer)
	 */
	@Override
	public List<DetalleEquipaje> buscarManifiestoEquipaje(Long itinerarioId, Integer agenciaId) throws Exception {
		// TODO Auto-generated method stub
		return getDetalleEquipajeDAO().buscarManifiestoEquipaje(itinerarioId, agenciaId);
	}

	

	
}
