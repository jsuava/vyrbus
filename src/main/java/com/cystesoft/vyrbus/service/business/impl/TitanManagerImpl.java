/**
 * Proyecto		: SISVYR
 * Sistema		: Sistema de Ventas y Reservas
 * Descripción	: 
 * Autor		: José Abanto
 * Fecha		: 06/10/2014
 * Hora			: 10:55:24
 */
package com.cystesoft.vyrbus.service.business.impl;

import com.cystesoft.vyrbus.model.bean.TitanComisionPersonaBase;
import com.cystesoft.vyrbus.model.bean.TitanFuncionarioPersonaPasaje;
import com.cystesoft.vyrbus.model.bean.TitanLiquidacionTurnoPasaje;
import com.cystesoft.vyrbus.model.bean.TitanPersona;
import com.cystesoft.vyrbus.model.bean.TitanUsuarioPersonal;
import com.cystesoft.vyrbus.model.bean.TitanVentaPasaje;
import com.cystesoft.vyrbus.model.dao.TitanDAO;
import com.cystesoft.vyrbus.service.business.TitanManager;

/**
 * @author JABANTO
 *
 */
public class TitanManagerImpl implements TitanManager {
	private TitanDAO titanDAO;
	/**
	 * @return the titanDAO
	 */
	public TitanDAO getTitanDAO() {
		return titanDAO;
	}
	/**
	 * @param titanDAO the titanDAO to set
	 */
	public void setTitanDAO(TitanDAO titanDAO) {
		this.titanDAO = titanDAO;
	}

	/********* TRANSACIONES REFERIDAS A LA LIQUIDACION TURNO PASAJE **********************/
	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.service.business.TitanManager#buscarLiquidacionTurnoPasajeByIdLiquidacion(java.lang.Long)
	 */
	@Override
	public TitanLiquidacionTurnoPasaje buscarLiquidacionTurnoPasajeByIdLiquidacion(Long idLiquidacionSisvyr) throws Exception {
		
		return getTitanDAO().buscarLiquidacionTurnoPasajeByIdLiquidacion(idLiquidacionSisvyr);
	}
	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.service.business.TitanManager#actualizaLiquidacionTurnoPasajeByIdLiquidacion(com.tepsa.sisvyr.model.bean.TitanLiquidacionTurnoPasaje)
	 */
	@Override
	public void actualizaLiquidacionTurnoPasajeByIdLiquidacion(TitanLiquidacionTurnoPasaje liquidacionTurnoPasaje)throws Exception {
		
		getTitanDAO().actualizaLiquidacionTurnoPasajeByIdLiquidacion(liquidacionTurnoPasaje);
	}
	
	
	/********* TRANSACIONES REFERIDAS AL USUARIO PERSONAL **********************/
	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.service.business.TitanManager#buscarUsuarioPersonalPorLogin(java.lang.String)
	 */
	@Override
	public TitanUsuarioPersonal buscarUsuarioPersonalPorLogin(String login)throws Exception {
		return getTitanDAO().buscarUsuarioPersonalPorLogin(login);
	}
	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.service.business.TitanManager#guardarUsuarioPersonal(com.tepsa.sisvyr.model.bean.TitanUsuarioPersonal)
	 */
	@Override
	public void guardarUsuarioPersonal(TitanUsuarioPersonal titanUsuarioPersonal)throws Exception {
		// TODO Auto-generated method stub
		getTitanDAO().guardarUsuarioPersonal(titanUsuarioPersonal);		
	}

	/********* TRANSACIONES REFERIDAS AL CLIENTE (T_PERSONAL) **********************/
	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.service.business.TitanManager#buscarPersonaPorRuc(java.lang.String)
	 */
	@Override
	public TitanPersona buscarPersonaPorRuc(String numeroRuc) throws Exception {
		return getTitanDAO().buscarPersonaPorRuc(numeroRuc);
	}

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.service.business.TitanManager#actualizaPersona(com.tepsa.sisvyr.model.bean.TitanPersona)
	 */
	@Override
	public void actualizaPersona(TitanPersona persona) throws Exception {
		getTitanDAO().actualizaPersona(persona);
	}

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.service.business.TitanManager#guardarPersona(com.tepsa.sisvyr.model.bean.TitanPersona)
	 */
	@Override
	public void guardarPersona(TitanPersona persona) throws Exception {
		getTitanDAO().guardarPersona(persona);
	}

	/********* TRANSACIONES REFERIDAS FUNCIONARIO PERSONA PASAJE **********************/
	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.service.business.TitanManager#buscarFuncionarioPersonaPasajePorIdPersona(java.lang.Long)
	 */
	@Override
	public TitanFuncionarioPersonaPasaje buscarFuncionarioPersonaPasajePorIdPersona(Long idPersona) throws Exception {
		return getTitanDAO().buscarFuncionarioPersonaPasajePorIdPersona(idPersona);
	}

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.service.business.TitanManager#actualizaFuncionarioPerosnaPasajes(com.tepsa.sisvyr.model.bean.TitanFuncionarioPersonaPasaje)
	 */
	@Override
	public void actualizaFuncionarioPerosnaPasajes(TitanFuncionarioPersonaPasaje funcionarioPersonaPasaje)throws Exception {
		getTitanDAO().actualizaFuncionarioPerosnaPasajes(funcionarioPersonaPasaje);
	}

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.service.business.TitanManager#guardaFuncionarioPerosnaPasajes(com.tepsa.sisvyr.model.bean.TitanFuncionarioPersonaPasaje)
	 */
	@Override
	public void guardaFuncionarioPerosnaPasajes(TitanFuncionarioPersonaPasaje funcionarioPersonaPasaje)throws Exception {
		getTitanDAO().guardaFuncionarioPerosnaPasajes(funcionarioPersonaPasaje);
	}

	/********* TRANSACIONES REFERIDAS COMISION PERSONA BASE HISTORICA **********************/
	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.service.business.TitanManager#buscarBaseHistoricaPorIdPersona(java.lang.Long)
	 */
	@Override
	public TitanComisionPersonaBase buscarBaseHistoricaPorIdPersona(Long idPersona) throws Exception {
		return getTitanDAO().buscarBaseHistoricaPorIdPersona(idPersona);
	}

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.service.business.TitanManager#actualizaBaseHistorica(com.tepsa.sisvyr.model.bean.TitanComisionPersonaBase)
	 */
	@Override
	public void actualizaBaseHistorica(TitanComisionPersonaBase comsionPersonaBase) throws Exception {
		getTitanDAO().actualizaBaseHistorica(comsionPersonaBase);
	}

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.service.business.TitanManager#guardaBaseHistorica(com.tepsa.sisvyr.model.bean.TitanComisionPersonaBase)
	 */
	@Override
	public void guardaBaseHistorica(TitanComisionPersonaBase comsionPersonaBase)throws Exception {
		getTitanDAO().guardaBaseHistorica(comsionPersonaBase);
	}
	
	
	/*****TRANSACCIONES REFERIDAS A LA VENTA DE PASAJEROS*/
	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.service.business.TitanManager#buscarBoletoVentaPasaje(java.lang.String, java.lang.String, java.lang.Integer)
	 */
	@Override
	public TitanVentaPasaje buscarBoletoVentaPasaje(String serieBoleto,String numeroBoleto, Integer idCondicionBoleto) throws Exception {
		// TODO Auto-generated method stub
		return getTitanDAO().buscarBoletoVentaPasaje(serieBoleto, numeroBoleto, idCondicionBoleto)
				;
	}
	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.service.business.TitanManager#actualizarFormaPago(com.tepsa.sisvyr.model.bean.TitanVentaPasaje)
	 */
	@Override
	public void actualizarFormaPago(TitanVentaPasaje titanVentaPasaje)throws Exception {
		getTitanDAO().actualizarFormaPago(titanVentaPasaje);
	}
	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.service.business.TitanManager#buscarFechaFacturaEspecial(java.lang.String, java.lang.String)
	 */
	@Override
	public String buscarFechaFacturaEspecial(String serie, String numero, String numeroRuc)throws Exception {
		// TODO Auto-generated method stub
		return getTitanDAO().buscarFechaFacturaEspecial(serie, numero, numeroRuc);
	}
	
	
	

}
