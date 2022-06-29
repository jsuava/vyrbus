/**
 * Proyecto		: SISVYR
 * Sistema		: Sistema de Ventas y Reservas
 * Descripción	: 
 * Autor		: José Abanto
 * Fecha		: 26 oct. 2021
 * Hora			: 10:58:36
 */
package com.cystesoft.vyrbus.service.business.impl;

import java.util.List;
import java.util.TreeMap;

import com.cystesoft.vyrbus.model.bean.Agencia;
import com.cystesoft.vyrbus.model.bean.Liquidacion;
import com.cystesoft.vyrbus.model.bean.Manifiesto;
import com.cystesoft.vyrbus.model.bean.TranscarLiquidacionTurno;
import com.cystesoft.vyrbus.model.bean.TranscarRolUsuario;
import com.cystesoft.vyrbus.model.bean.TranscarUsuarioPersonal;
import com.cystesoft.vyrbus.model.bean.Usuario;
import com.cystesoft.vyrbus.model.bean.VentaPasaje;
import com.cystesoft.vyrbus.model.dao.TranscarDAO;
import com.cystesoft.vyrbus.service.business.TranscarManager;

/**
 * @author abant
 *
 */
public class TranscarManagerImpl implements TranscarManager{
	private TranscarDAO transcarDAO;
	
	
	/**
	 * @return the transcarDAO
	 */
	public TranscarDAO getTranscarDAO() {
		return transcarDAO;
	}

	/**
	 * @param transcarDAO the transcarDAO to set
	 */
	public void setTranscarDAO(TranscarDAO transcarDAO) {
		this.transcarDAO = transcarDAO;
	}

	/* (non-Javadoc)
	 * @see com.cystesoft.vyrbus.service.business.TranscarManager#buscarRolesUsauurio()
	 */
	@Override
	public List<TranscarRolUsuario> buscarRolesUsuario() throws Exception {
		// TODO Auto-generated method stub						
		return getTranscarDAO().buscarRolesUsuario();
	}

	/* (non-Javadoc)
	 * @see com.cystesoft.vyrbus.service.business.TranscarManager#buscarRolesUsuario(java.lang.Integer)
	 */
	@Override
	public List<TranscarRolUsuario> buscarRolesUsuario(Integer usuarioId) throws Exception {
		// TODO Auto-generated method stub
		return getTranscarDAO().buscarRolesUsuario(usuarioId);
	}

	/* (non-Javadoc)
	 * @see com.cystesoft.vyrbus.service.business.TranscarManager#buscarUsuarioPersonal(java.lang.String)
	 */
	@Override
	public TranscarUsuarioPersonal buscarUsuarioPersonal(String login) throws Exception {
		// TODO Auto-generated method stub
		return getTranscarDAO().buscarUsuarioPersonal(login);
	}

	/* (non-Javadoc)
	 * @see com.cystesoft.vyrbus.service.business.TranscarManager#guardarUsuarioPersonal(com.cystesoft.vyrbus.model.bean.TranscarUsuarioPersonal, java.lang.String, boolean)
	 */
	@Override
	public void guardarUsuarioPersonal(TranscarUsuarioPersonal transcarUsusrioPeronal, String idsRoles, boolean isNuevo)
			throws Exception {
		// TODO Auto-generated method stub
		getTranscarDAO().guardarUsuarioPersonal(transcarUsusrioPeronal, idsRoles, isNuevo);
	}

	/* (non-Javadoc)
	 * @see com.cystesoft.vyrbus.service.business.TranscarManager#buscarIdAgenciaByCodigoAgenciaPasajes(java.lang.String)
	 */
	@Override
	public Integer buscarIdAgenciaByCodigoAgenciaPasajes(String codigoAgenciaPasajes) throws Exception {
		// TODO Auto-generated method stub
//		return getTranscarDAO().buscarIdAgenciaByCodigoAgenciaPasajes(codigoAgenciaPasajes);
		return null;
	}

	/* (non-Javadoc)
	 * @see com.cystesoft.vyrbus.service.business.TranscarManager#aperturarLiquidacion(com.cystesoft.vyrbus.model.bean.TranscarLiquidacionTurno)
	 */
	@Override
	public String aperturarLiquidacion(TranscarLiquidacionTurno liquidacionTurno) throws Exception {
		// TODO Auto-generated method stub
		return getTranscarDAO().aperturarLiquidacion(liquidacionTurno);
	}

	/* (non-Javadoc)
	 * @see com.cystesoft.vyrbus.service.business.TranscarManager#buscarDetalleVentas(java.lang.Integer, java.lang.Integer, java.lang.String, java.lang.String)
	 */
	@Override
	public List<VentaPasaje> buscarDetalleVentas(TranscarUsuarioPersonal usuarioPersonal, Integer agenciaId, String fechaInicial,
			String fechaFinal) throws Exception {
		// TODO Auto-generated method stub
		return getTranscarDAO().buscarDetalleVentas(usuarioPersonal, agenciaId, fechaInicial, fechaFinal);
	}

	/* (non-Javadoc)
	 * @see com.cystesoft.vyrbus.service.business.TranscarManager#buscarAgencias()
	 */
	@Override
	public List<Agencia> buscarAgencias() throws Exception {
		// TODO Auto-generated method stub
		return getTranscarDAO().buscarAgencias();
	}

	/* (non-Javadoc)
	 * @see com.cystesoft.vyrbus.service.business.TranscarManager#buscarUsuariosByVenta(java.lang.Integer, java.lang.String, java.lang.String)
	 */
	@Override
	public List<Usuario> buscarUsuariosByVenta(Integer agenciaId, String fechaInicio, String fechaFin)
			throws Exception {
		// TODO Auto-generated method stub
		return getTranscarDAO().buscarUsuariosByVenta(agenciaId, fechaInicio, fechaFin);
	}

	/* (non-Javadoc)
	 * @see com.cystesoft.vyrbus.service.business.TranscarManager#buscarLiquidacionTurno(java.lang.Integer, java.lang.Integer, java.lang.String, java.lang.String)
	 */
	@Override
	public List<Liquidacion> buscarLiquidacionTurnoResumenEspVal(Integer usuarioId,
			Integer agenciaId, String fechaInicio, String fechaFin)
			throws Exception {
		// TODO Auto-generated method stub
		return getTranscarDAO().buscarLiquidacionTurnoResumenEspVal(usuarioId, agenciaId, fechaInicio, fechaFin);
	}

	/* (non-Javadoc)
	 * @see com.cystesoft.vyrbus.service.business.TranscarManager#buscarLiquidacionTurno(java.lang.Integer, java.lang.Integer, java.lang.String, java.lang.String)
	 */
	@Override
	public Liquidacion buscarLiquidacionTurno(Integer usuarioId,
			Integer agenciaId, String fechaInicio, String fechaFin)
			throws Exception {
		// TODO Auto-generated method stub
		return getTranscarDAO().buscarLiquidacionTurno(usuarioId, agenciaId, fechaInicio, fechaFin);
	}

//	/* (non-Javadoc)
//	 * @see com.cystesoft.vyrbus.service.business.TranscarManager#cerrarLiquidacion(java.lang.Integer, java.lang.Integer, java.lang.String, java.lang.String)
//	 */
//	@Override
//	public void cerrarLiquidacion(Integer usuarioId, Integer agenciaId,String fechaInicio, String fechaFin) throws Exception {
//		// TODO Auto-generated method stub
//		getTranscarDAO().cerrarLiquidacion(usuarioId, agenciaId, fechaInicio, fechaFin);
//	}

	/* (non-Javadoc)
	 * @see com.cystesoft.vyrbus.service.business.TranscarManager#buscarLiquidacionBus(java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	public TreeMap<String, Manifiesto> buscarLiquidacionBus(String fechaInicio,
			String fechaFin, String codigoBus) throws Exception {
		// TODO Auto-generated method stub
		return getTranscarDAO().buscarLiquidacionBus(fechaInicio, fechaFin, codigoBus);
	}

	/* (non-Javadoc)
	 * @see com.cystesoft.vyrbus.service.business.TranscarManager#buscarLiquidacionCounter(java.lang.String, java.lang.String, java.lang.Integer, java.lang.Integer)
	 */
	@Override
	public TreeMap<String, Liquidacion> buscarLiquidacionCounter(String fechaInicio, String fechaFin,
			Integer agenciaId, Integer usuarioId) throws Exception {
		// TODO Auto-generated method stub
		return getTranscarDAO().buscarLiquidacionCounter(fechaInicio, fechaFin, agenciaId, usuarioId);
	}

	

}
