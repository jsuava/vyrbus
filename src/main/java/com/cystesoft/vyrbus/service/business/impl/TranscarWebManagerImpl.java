/**
 * Proyecto		: SISVYR
 * Sistema		: Sistema de Ventas y Reservas
 * Descripción	:
 * Autor		: José Abanto
 * Fecha		: 2 may. 2022
 * Hora			: 22:46:06
 */
package com.cystesoft.vyrbus.service.business.impl;

import java.util.List;
import java.util.TreeMap;

import com.cystesoft.vyrbus.model.bean.Liquidacion;
import com.cystesoft.vyrbus.model.bean.Manifiesto;
import com.cystesoft.vyrbus.model.bean.TranscarLiquidacionTurno;
import com.cystesoft.vyrbus.model.bean.TranscarRolUsuario;
import com.cystesoft.vyrbus.model.bean.TranscarUsuarioPersonal;
import com.cystesoft.vyrbus.model.bean.Usuario;
import com.cystesoft.vyrbus.model.bean.VentaPasaje;
import com.cystesoft.vyrbus.model.dao.TranscarWebDAO;
import com.cystesoft.vyrbus.service.business.TranscarWebManager;
import com.cystesoft.vyrbus.service.mappers.ResumenVentas;
import com.cystesoft.vyrbus.service.mappers.VentasPiloto;

/**
 * @author abant
 *
 */
public class TranscarWebManagerImpl implements TranscarWebManager{

	private TranscarWebDAO transcarWebDAO;

	/**
	 * @return the transcarWebDAO
	 */
	public TranscarWebDAO getTranscarWebDAO() {
		return transcarWebDAO;
	}

	/**
	 * @param transcarWebDAO the transcarWebDAO to set
	 */
	public void setTranscarWebDAO(TranscarWebDAO transcarWebDAO) {
		this.transcarWebDAO = transcarWebDAO;
	}

	/* (non-Javadoc)
	 * @see com.cystesoft.vyrbus.service.business.TranscarWebManager#buscarRolesUsuario()
	 */
	@Override
	public List<TranscarRolUsuario> buscarRolesUsuario() throws Exception {
		// TODO Auto-generated method stub
		return getTranscarWebDAO().buscarRolesUsuario();
	}

	/* (non-Javadoc)
	 * @see com.cystesoft.vyrbus.service.business.TranscarWebManager#buscarRolesUsuario(java.lang.Integer)
	 */
	@Override
	public List<TranscarRolUsuario> buscarRolesUsuario(Integer usuarioId) throws Exception {
		// TODO Auto-generated method stub
		return getTranscarWebDAO().buscarRolesUsuario(usuarioId);
	}

	/* (non-Javadoc)
	 * @see com.cystesoft.vyrbus.service.business.TranscarWebManager#buscarUsuario(java.lang.String)
	 */
	@Override
	public TranscarUsuarioPersonal buscarUsuario(String login) throws Exception {
		// TODO Auto-generated method stub
		return getTranscarWebDAO().buscarUsuario(login);
	}

	/* (non-Javadoc)
	 * @see com.cystesoft.vyrbus.service.business.TranscarWebManager#guardarUsuario(com.cystesoft.vyrbus.model.bean.TranscarUsuarioPersonal, java.lang.String, boolean)
	 */
	@Override
	public void guardarUsuario(TranscarUsuarioPersonal transcarUsuario, String idsRoles, boolean isNuevo)
			throws Exception {
		// TODO Auto-generated method stub
//		transcarUsuario.setPassword(Encriptar.codifica(transcarUsuario.getPassword(), transcarUsuario.getLogin()));
		getTranscarWebDAO().guardarUsuario(transcarUsuario, idsRoles, isNuevo);
	}

//	/* (non-Javadoc)
//	 * @see com.cystesoft.vyrbus.service.business.TranscarWebManager#buscarIdAgenciaByCodigoAgenciaPasajes(java.lang.String)
//	 */
//	@Override
//	public Integer buscarIdAgenciaByCodigoAgenciaPasajes(String codigoAgenciaPasajes) throws Exception {
//		// TODO Auto-generated method stub
//		return getTranscarWebDAO().buscarIdAgenciaByCodigoAgenciaPasajes(codigoAgenciaPasajes);
//	}

	/* (non-Javadoc)
	 * @see com.cystesoft.vyrbus.service.business.TranscarWebManager#aperturarLiquidacion(com.cystesoft.vyrbus.model.bean.TranscarLiquidacionTurno)
	 */
	@Override
	public String aperturarLiquidacion(TranscarLiquidacionTurno liquidacionTurno, boolean isReapertura) throws Exception {
		// TODO Auto-generated method stub
		return getTranscarWebDAO().aperturarLiquidacion(liquidacionTurno, isReapertura);
	}

	/* (non-Javadoc)
	 * @see com.cystesoft.vyrbus.service.business.TranscarWebManager#buscarDetalleVentas(com.cystesoft.vyrbus.model.bean.TranscarUsuarioPersonal, java.lang.Integer, java.lang.String, java.lang.String)
	 */
	@Override
	public List<VentaPasaje> buscarDetalleVentas(TranscarUsuarioPersonal usuario, Integer agenciaId,
			String fechaInicial, String fechaFinal) throws Exception {
		// TODO Auto-generated method stub
		return getTranscarWebDAO().buscarDetalleVentas(usuario, agenciaId, fechaInicial, fechaFinal);
	}
//
//	/* (non-Javadoc)
//	 * @see com.cystesoft.vyrbus.service.business.TranscarWebManager#buscarAgencias()
//	 */
//	@Override
//	public List<Agencia> buscarAgencias() throws Exception {
//		// TODO Auto-generated method stub
//		return getTranscarWebDAO().buscarAgencias();
//	}

	/* (non-Javadoc)
	 * @see com.cystesoft.vyrbus.service.business.TranscarWebManager#buscarUsuariosByVenta(java.lang.Integer, java.lang.String, java.lang.String)
	 */
	@Override
	public List<Usuario> buscarUsuariosByVenta(Integer agenciaId, String fechaInicio, String fechaFin)
			throws Exception {
		// TODO Auto-generated method stub
		return getTranscarWebDAO().buscarUsuariosByVenta(agenciaId, fechaInicio, fechaFin);
	}

	/* (non-Javadoc)
	 * @see com.cystesoft.vyrbus.service.business.TranscarWebManager#buscarLiquidacionTurnoResumenEspVal(java.lang.Integer, java.lang.Integer, java.lang.String, java.lang.String)
	 */
	@Override
	public List<Liquidacion> buscarLiquidacionTurnoResumenEspVal(Integer usuarioId, Integer agenciaId,
			String fechaInicio, String fechaFin) throws Exception {
		// TODO Auto-generated method stub
		return getTranscarWebDAO().buscarLiquidacionTurnoResumenEspVal(usuarioId, agenciaId, fechaInicio, fechaFin);
	}

	/* (non-Javadoc)
	 * @see com.cystesoft.vyrbus.service.business.TranscarWebManager#buscarLiquidacionTurno(java.lang.Integer, java.lang.Integer, java.lang.String, java.lang.String)
	 */
	@Override
	public Liquidacion buscarLiquidacionTurno(Integer usuarioId, Integer agenciaId, String fechaInicio, String fechaFin)
			throws Exception {
		// TODO Auto-generated method stub
		return getTranscarWebDAO().buscarLiquidacionTurno(usuarioId, agenciaId, fechaInicio, fechaFin);
	}

	/*
	 * (non-Javadoc)
	 * @see com.cystesoft.vyrbus.service.business.TranscarWebManager#cerrarLiquidacion(java.lang.Integer, java.lang.Integer, java.lang.String, java.lang.Double, java.lang.Double)
	 */
	@Override
	public void cerrarLiquidacion(Integer usuarioId, Integer agenciaId, String fechaLiquidacion, Double efectivoIngresado, Double efectivoLiquidado)throws Exception{
		// TODO Auto-generated method stub
		getTranscarWebDAO().cerrarLiquidacion(usuarioId, agenciaId, fechaLiquidacion, efectivoIngresado, efectivoLiquidado);

	}

	/* (non-Javadoc)
	 * @see com.cystesoft.vyrbus.service.business.TranscarWebManager#buscarLiquidacionBus(java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	public TreeMap<String, Manifiesto> buscarLiquidacionBus(String fechaInicio, String fechaFin, String codigoBus)
			throws Exception {
		// TODO Auto-generated method stub
		return getTranscarWebDAO().buscarLiquidacionBus(fechaInicio, fechaFin, codigoBus);
	}

	/* (non-Javadoc)
	 * @see com.cystesoft.vyrbus.service.business.TranscarWebManager#buscarLiquidacionCounter(java.lang.String, java.lang.String, java.lang.Integer, java.lang.Integer)
	 */
	@Override
	public TreeMap<String, Liquidacion> buscarLiquidacionCounter(String fechaInicio, String fechaFin, Integer agenciaId,
			Integer usuarioId) throws Exception {
		// TODO Auto-generated method stub
		return getTranscarWebDAO().buscarLiquidacionCounter(fechaInicio, fechaFin, agenciaId, usuarioId);
	}

	/* (non-Javadoc)
	 * @see com.cystesoft.vyrbus.service.business.TranscarWebManager#actualizarPasswordUsuarioByLogin(java.lang.String)
	 */
	@Override
	public void actualizarPasswordUsuarioByLogin(String login, String passwordNew) throws Exception {
		// TODO Auto-generated method stub
		getTranscarWebDAO().actualizarPasswordUsuarioByLogin(login, passwordNew);
	}
	
	
	@Override
	public List<VentasPiloto> buscarRegistroVentas(String fInicio, String fFin) throws Exception{
		return getTranscarWebDAO().buscarRegistroVentas(fInicio, fFin);
	}

	/* (non-Javadoc)
	 * @see com.cystesoft.vyrbus.service.business.TranscarWebManager#buscarResumenVentas(java.lang.String, java.lang.String)
	 */
	@Override
	public List<ResumenVentas> buscarResumenVentas(String fechaDesde, String fechaHasta) {
		// TODO Auto-generated method stub
		return getTranscarWebDAO().buscarResumenVentas(fechaDesde, fechaHasta);
	}

	/* (non-Javadoc)
	 * @see com.cystesoft.vyrbus.service.business.TranscarWebManager#buscaTotalVentasEfectivo(java.lang.Integer, java.lang.Integer, java.lang.String)
	 */
	@Override
	public Double buscaTotalVentasEfectivo(String loginUsuario, Integer idAgencia, String fecha) throws Exception {
		// TODO Auto-generated method stub
		return getTranscarWebDAO().buscaTotalVentasEfectivo(loginUsuario, idAgencia, fecha);
	}
}
