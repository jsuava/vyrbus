/**
 * Proyecto		: SISVYR
 * Sistema		: Sistema de Ventas y Reservas
 * Descripción	:
 * Autor		: José Abanto
 * Fecha		: 08/09/2016
 * Hora			: 14:27:05
 */
package pe.itsb.vyrbus.service.business.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

import org.springframework.transaction.annotation.Transactional;
import org.zkoss.zk.ui.Executions;

import pe.itsb.vyrbus.model.bean.Usuario;
import pe.itsb.vyrbus.model.bean.VentaPasaje;
import pe.itsb.vyrbus.model.bean.VentaPool;
import pe.itsb.vyrbus.model.dao.VentaPoolDAO;
import pe.itsb.vyrbus.service.business.VentaPoolManager;
import pe.itsb.vyrbus.service.util.Constantes;
import pe.itsb.vyrbus.service.util.UtilData;

/**
 * @author jabanto
 *
 */
public class VentaPoolManagerImpl implements VentaPoolManager{
	private VentaPoolDAO ventaPoolDAO;

	/**
	 * @return the ventaPoolDAO
	 */
	public VentaPoolDAO getVentaPoolDAO() {
		return ventaPoolDAO;
	}

	/**
	 * @param ventaPoolDAO the ventaPoolDAO to set
	 */
	public void setVentaPoolDAO(VentaPoolDAO ventaPoolDAO) {
		this.ventaPoolDAO = ventaPoolDAO;
	}

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.service.business.VentaPoolManager#buscarPorEstadoRegistro(java.lang.String, java.lang.String)
	 */
	@Override
	public ArrayList<VentaPool> buscarPorEstadoRegistro(String estado,String criterioOrden) {
		// TODO Auto-generated method stub
		return getVentaPoolDAO().buscarPorEstadoRegistro(estado, criterioOrden);
	}

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.service.business.VentaPoolManager#buscarPorX(java.util.TreeMap, java.util.List)
	 */
	@Override
	public ArrayList<VentaPool> buscarPorX(
			TreeMap<String, Object> criteriosBusqueda,
			List<String> criteriosOrdenar) {
		// TODO Auto-generated method stub
		return getVentaPoolDAO().buscarPorX(criteriosBusqueda, criteriosOrdenar);
	}

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.service.business.VentaPoolManager#buscarPorId(java.lang.Long)
	 */
	@Override
	public VentaPool buscarPorId(Long id) {
		// TODO Auto-generated method stub
		return getVentaPoolDAO().buscarPorId(id);
	}

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.service.business.VentaPoolManager#guardar(com.tepsa.sisvyr.model.bean.VentaPool)
	 */
	@Override
	@Transactional
	public VentaPool guardar(VentaPasaje ventaPasaje,String codigoTransaccion,String numeroComprobanteOperador) throws Exception {
		VentaPool ventaPool= new VentaPool();
		ventaPool.setAgencia(ventaPasaje.getAgencia());
		ventaPool.setCliente((ventaPasaje.getCliente()!=null?ventaPasaje.getCliente():null));
		ventaPool.setCodigoTransaccionOperador(codigoTransaccion);
		ventaPool.setDescuento(ventaPasaje.getDescuento());
		ventaPool.setFechaPartida(ventaPasaje.getFechaPartida());
		ventaPool.setFormaPago(ventaPasaje.getFormaPago());
		ventaPool.setHoraLlegada(ventaPasaje.getHoraLllegada());
		ventaPool.setHoraPartida(ventaPasaje.getHoraEmbarque());
		ventaPool.setImportePagado(ventaPasaje.getImportePagado());
		ventaPool.setNumeroAsiento(ventaPasaje.getNumeroAsiento().toString());
		ventaPool.setNumeroBoletoTepsa(ventaPasaje.getNumeroBoleto());
		ventaPool.setNumeroComprobanteOperador(numeroComprobanteOperador);
		ventaPool.setOperador(ventaPasaje.getOperadoPor());
		ventaPool.setPiso(ventaPasaje.getNumeroPiso());
		ventaPool.setPuntoEmbaruqe(ventaPasaje.getAgenciaPartida().getDenominacion());
		ventaPool.setPasajero(ventaPasaje.getPasajero());
		ventaPool.setRuta(ventaPasaje.getRuta().toString());
		ventaPool.setServicio(ventaPasaje.getServicio().getDenominacion());
		ventaPool.setFechaLlegada(ventaPasaje.getFechaLlegada());
		ventaPool.setTarifa(ventaPasaje.getTarifa());
		ventaPool.setTarjetaCredito(ventaPasaje.getTarjetaCredito()!=null?ventaPasaje.getTarjetaCredito():null);
		ventaPool.setUsuario(ventaPasaje.getUsuario());
		ventaPool.setEstadoRegistro(Constantes.VALUE_ACTIVO);
		ventaPool.setFechaLiquidacion(ventaPasaje.getFechaLiquidacion());
		ventaPool.setLocalidadOrigen(ventaPasaje.getRuta().getLocalidadOrigen());
		ventaPool.setLocalidadDestino(ventaPasaje.getRuta().getLocalidadDestino());
		UtilData.auditarRegistro(ventaPool, ventaPasaje.getUsuario(), Executions.getCurrent());


		getVentaPoolDAO().guardar(ventaPool);

		return ventaPool;
	}

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.service.business.VentaPoolManager#actualizar(com.tepsa.sisvyr.model.bean.VentaPool)
	 */
	@Override
	@Transactional
	public void actualizar(VentaPool VentaPool) {
		// TODO Auto-generated method stub
		getVentaPoolDAO().actualizar(VentaPool);
	}

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.service.business.VentaPoolManager#inactivar(java.lang.Long)
	 */
	@Override
	public void inactivar(Long id) {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.service.business.VentaPoolManager#buscarUsuarioPorAgencia(java.lang.Integer, java.lang.String, java.lang.String)
	 */
	@Override
	public List<Usuario> buscarUsuarioPorAgencia(Integer idAgencia,String fechaInicio, String fechaFin) throws Exception {
		// TODO Auto-generated method stub
		return getVentaPoolDAO().buscarUsuarioPorAgencia(idAgencia, fechaInicio, fechaFin);
	}

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.service.business.VentaPoolManager#buscarVentas(java.lang.String, java.lang.String, java.lang.Integer, java.lang.Integer)
	 */
	@Override
	public List<VentaPool> buscarVentas(String fechaInicio, String fechaFin,Integer agenciaId, Integer usuarioId) throws Exception {
		// TODO Auto-generated method stub
		return getVentaPoolDAO().buscarVentas(fechaInicio, fechaFin, agenciaId, usuarioId);
	}
}
