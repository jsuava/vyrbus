/**
 * Proyecto		: SISVYR
 * Sistema		: Sistema de Ventas y Reservas
 * Descripción	: Clase donde implementamos las reglas del negocio para la tabla Autorizador Motivo Cortesia VRTAUTCOR_MOTCOR.
 * Autor		: José Sullo Avalos
 * Fecha		: 03/04/2014
 */
package com.cystesoft.vyrbus.service.business.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

import org.springframework.transaction.annotation.Transactional;

import com.cystesoft.vyrbus.model.bean.AutorizadorCortesia;
import com.cystesoft.vyrbus.model.bean.AutorizadorMotivoCortesia;
import com.cystesoft.vyrbus.model.bean.MotivoCortesia;
import com.cystesoft.vyrbus.model.dao.AutorizadorMotivoCortesiaDAO;
import com.cystesoft.vyrbus.service.business.AutorizadorMotivoCortesiaManager;
import com.cystesoft.vyrbus.service.util.Constantes;

/**
 * @author Jose
 *
 */
public class AutorizadorMotivoCortesiaManagerImpl implements AutorizadorMotivoCortesiaManager {
	private AutorizadorMotivoCortesiaDAO autorizadorMotivoCortesiaDAO;


	/**
	 * @return the autorizadorMotivoCortesiaDAO
	 */
	public AutorizadorMotivoCortesiaDAO getAutorizadorMotivoCortesiaDAO() {
		return autorizadorMotivoCortesiaDAO;
	}

	/**
	 * @param autorizadorMotivoCortesiaDAO the autorizadorMotivoCortesiaDAO to set
	 */
	public void setAutorizadorMotivoCortesiaDAO(AutorizadorMotivoCortesiaDAO autorizadorMotivoCortesiaDAO) {
		this.autorizadorMotivoCortesiaDAO = autorizadorMotivoCortesiaDAO;
	}

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.service.business.AutorizadorMotivoCortesiaManager#buscarPorEstadoRegistro(java.lang.String, java.lang.String)
	 */
	@Override
	public ArrayList<AutorizadorMotivoCortesia> buscarPorEstadoRegistro(String estado, String criterioOrden) {
		return getAutorizadorMotivoCortesiaDAO().buscarPorEstadoRegistro(estado, criterioOrden);
	}

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.service.business.AutorizadorMotivoCortesiaManager#buscarPorX(java.util.TreeMap, java.util.List)
	 */
	@Override
	public ArrayList<AutorizadorMotivoCortesia> buscarPorX(TreeMap<String, Object> criteriosBusqueda, List<String> criteriosOrdenar) {
		return getAutorizadorMotivoCortesiaDAO().buscarPorX(criteriosBusqueda, criteriosOrdenar);
	}

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.service.business.AutorizadorMotivoCortesiaManager#buscarPorId(java.lang.Long)
	 */
	@Override
	public AutorizadorMotivoCortesia buscarPorId(Long id) {
		return getAutorizadorMotivoCortesiaDAO().buscarPorId(id);
	}

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.service.business.AutorizadorMotivoCortesiaManager#guardar(com.tepsa.sisvyr.model.bean.AutorizadorMotivoCortesia)
	 */
	@Override
	@Transactional
	public int guardar(AutorizadorMotivoCortesia autorizadorMotivoCortesia) {
		int result = Constantes.FAILURE;
		AutorizadorCortesia autorizadorCortesia = autorizadorMotivoCortesia.getAutorizadorCortesia();
		for(MotivoCortesia motivoCortesia : autorizadorCortesia.getListaMotivoCortesia()){
			AutorizadorMotivoCortesia autorizadorMotivo = new AutorizadorMotivoCortesia();
			autorizadorMotivo.setAutorizadorCortesia(autorizadorCortesia);
			autorizadorMotivo.setMotivoCortesia(motivoCortesia);
			autorizadorMotivo.setEstadoRegistro(Constantes.VALUE_ACTIVO);
			autorizadorMotivo.setUsuarioInsercion(autorizadorCortesia.getUsuarioInsercion());
			autorizadorMotivo.setIpInsercion(autorizadorCortesia.getIpInsercion());
			autorizadorMotivo.setUsuarioModificacion(autorizadorCortesia.getUsuarioModificacion());
			autorizadorMotivo.setIpModificacion(autorizadorCortesia.getIpModificacion());
			getAutorizadorMotivoCortesiaDAO().save(autorizadorMotivo);
		}
		result = Constantes.CORRECT;
		return result;
	}

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.service.business.AutorizadorMotivoCortesiaManager#actualizar(com.tepsa.sisvyr.model.bean.AutorizadorMotivoCortesia)
	 */
	@Override
	public int actualizar(AutorizadorMotivoCortesia autorizadorMotivoCortesia) {
		// TODO Auto-generated method stub
		return 0;
	}

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.service.business.AutorizadorMotivoCortesiaManager#inactivar(java.lang.Long)
	 */
	@Override
	public int inactivar(Long id) {
		// TODO Auto-generated method stub
		return 0;
	}
	/*
	 * (non-Javadoc)
	 * @see com.tepsa.sisvyr.service.business.AutorizadorMotivoCortesiaManager#buscarMotivosCortesia(java.lang.Long)
	 */
	@Override
	public List<MotivoCortesia> buscarMotivosCortesia(Long idPersonal)throws Exception{
		return getAutorizadorMotivoCortesiaDAO().buscarMotivosCortesia(idPersonal);
	}

}
