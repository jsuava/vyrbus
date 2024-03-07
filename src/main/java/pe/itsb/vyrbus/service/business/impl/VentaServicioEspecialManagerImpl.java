/**
 * Proyecto		: SISVYR
 * Sistema		: Sistema de Ventas y Reservas
 * Descripci�n	:
 * Autor		: Jos� Abanto
 * Fecha		: 27 jun. 2021
 * Hora			: 22:51:23
 */
package pe.itsb.vyrbus.service.business.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

import org.springframework.transaction.annotation.Transactional;

import pe.itsb.vyrbus.model.bean.ControlEspecieValorada;
import pe.itsb.vyrbus.model.bean.VentaServicioEspecial;
import pe.itsb.vyrbus.model.dao.ControlEspecieValoradaDAO;
import pe.itsb.vyrbus.model.dao.VentaServicioEspecialDAO;
import pe.itsb.vyrbus.service.business.VentaServicioEspecialManager;
import pe.itsb.vyrbus.service.util.Constantes;
import pe.itsb.vyrbus.service.util.UtilData;

/**
 * @author Jose
 *
 */
public class VentaServicioEspecialManagerImpl implements VentaServicioEspecialManager {
	private VentaServicioEspecialDAO  ventaServicioEspecialDAO;
	private ControlEspecieValoradaDAO controlEspecieValoradaDAO;

	/**
	 * @return the ventaServicioEspecialDAO
	 */
	public VentaServicioEspecialDAO getVentaServicioEspecialDAO() {
		return ventaServicioEspecialDAO;
	}

	/**
	 * @param ventaServicioEspecialDAO the ventaServicioEspecialDAO to set
	 */
	public void setVentaServicioEspecialDAO(VentaServicioEspecialDAO ventaServicioEspecialDAO) {
		this.ventaServicioEspecialDAO = ventaServicioEspecialDAO;
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
	 * @see pe.itsb.vyrbus.service.business.VentaServicioEspecialManager#buscarPorEstadoRegistro(java.lang.String, java.lang.String)
	 */
	@Override
	public ArrayList<VentaServicioEspecial> buscarPorEstadoRegistro(String estado, String criterioOrden) throws Exception {
		return getVentaServicioEspecialDAO().buscarPorEstadoRegistro(estado, criterioOrden);
	}

	/* (non-Javadoc)
	 * @see pe.itsb.vyrbus.service.business.VentaServicioEspecialManager#buscarPorX(java.util.TreeMap, java.util.List)
	 */
	@Override
	public ArrayList<VentaServicioEspecial> buscarPorX(TreeMap<String, Object> criteriosBusqueda, List<String> criteriosOrdenar) throws Exception {
		return getVentaServicioEspecialDAO().buscarPorX(criteriosBusqueda, criteriosOrdenar);
	}

	/* (non-Javadoc)
	 * @see pe.itsb.vyrbus.service.business.VentaServicioEspecialManager#buscarPorId(java.lang.Long)
	 */
	@Override
	public VentaServicioEspecial buscarPorId(Long id) throws Exception {
		return getVentaServicioEspecialDAO().buscarPorId(id);
	}

	/* (non-Javadoc)
	 * @see pe.itsb.vyrbus.service.business.VentaServicioEspecialManager#guardar(pe.itsb.vyrbus.model.bean.VentaServicioEspecial)
	 */
	@Override
	@Transactional
	public int guardar(VentaServicioEspecial ventaServicioEspecial) throws Exception {
		ControlEspecieValorada controlEspecieValorada = UtilData.buscarEspecieValoradaByCaja(ventaServicioEspecial.getTipoComprobante().getId(), ventaServicioEspecial.getAgencia(), true, ventaServicioEspecial.getUsuarioHardware(), null, null);
		ventaServicioEspecial.setNumeroComprobante(controlEspecieValorada.toString());

		int result = Constantes.FAILURE;

		try {
		/*Actualiza el correlativo*/
//		if(ventaPasaje.getTipoComprobante().getId().intValue()!=Constantes.ID_TIPCOM_BOLETO_VIAJE){
			int position = ventaServicioEspecial.getNumeroComprobante().indexOf("-");
			Long correlativo = Long.valueOf(ventaServicioEspecial.getNumeroComprobante().substring(position+1))+1;
			controlEspecieValorada.setCorrelativoActual(correlativo);
			getControlEspecieValoradaDAO().update(controlEspecieValorada);
//		}
			getVentaServicioEspecialDAO().guardar(ventaServicioEspecial);
			result  = Constantes.CORRECT;
		}catch (Exception e) {
			throw new Exception(e);
		}
		return result;
	}

	/* (non-Javadoc)
	 * @see pe.itsb.vyrbus.service.business.VentaServicioEspecialManager#actualizar(pe.itsb.vyrbus.model.bean.VentaServicioEspecial)
	 */
	@Override
	public void actualizar(VentaServicioEspecial ventaServicioEspecial) throws Exception {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see pe.itsb.vyrbus.service.business.VentaServicioEspecialManager#inactivar(long)
	 */
	@Override
	public void inactivar(long id) throws Exception {
		getVentaServicioEspecialDAO().inactivar(id);
	}

}
