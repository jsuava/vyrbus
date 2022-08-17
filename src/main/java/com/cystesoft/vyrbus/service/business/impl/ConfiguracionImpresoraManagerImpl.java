/**
 * Proyecto		: SISVYR
 * Sistema		: Sistema de Ventas y Reservas
 * Descripción	:
 * Autor		: José Abanto
 * Fecha		: 21/05/2015
 * Hora			: 15:40:20
 */
package com.cystesoft.vyrbus.service.business.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

import org.springframework.transaction.annotation.Transactional;

import com.cystesoft.vyrbus.model.bean.ConfiguracionImpresora;
import com.cystesoft.vyrbus.model.dao.ConfiguracionImpresoraDAO;
import com.cystesoft.vyrbus.service.business.ConfiguracionImpresoraManager;
import com.cystesoft.vyrbus.service.util.Constantes;

/**
 * @author Jose
 *
 */
public class ConfiguracionImpresoraManagerImpl implements ConfiguracionImpresoraManager{
	private ConfiguracionImpresoraDAO configuracionImpresoraDAO;

	/**
	 * @return the configuracionImpresoraDAO
	 */
	public ConfiguracionImpresoraDAO getConfiguracionImpresoraDAO() {
		return configuracionImpresoraDAO;
	}

	/**
	 * @param configuracionImpresoraDAO the configuracionImpresoraDAO to set
	 */
	public void setConfiguracionImpresoraDAO(ConfiguracionImpresoraDAO configuracionImpresoraDAO) {
		this.configuracionImpresoraDAO = configuracionImpresoraDAO;
	}

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.service.business.ConfiguracionImpresoraManager#buscarImpresora()
	 */
	@Override
	public ConfiguracionImpresora buscarImpresora() throws Exception {
		// TODO Auto-generated method stub
		return getConfiguracionImpresoraDAO().buscarImpresora();
	}

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.service.business.ConfiguracionImpresoraManager#gardar(com.tepsa.sisvyr.model.bean.ConfiguracionImpresora)
	 */
	@Transactional
	@Override
	public void guardar(ConfiguracionImpresora configuracionImpresora)throws Exception {
		// TODO Auto-generated method stub
		/*Primero valida si ya existe alguna configuracion para la pc seleccionada*/
		TreeMap<String, Object>criteriosBusqueda= new TreeMap<>();
		criteriosBusqueda.put("usuarioHardware", configuracionImpresora.getUsuarioHardware());
		criteriosBusqueda.put("estadoRegistro", Constantes.VALUE_ACTIVO);
		List<ConfiguracionImpresora>result=buscarPorX(criteriosBusqueda, null);
		if(result.size()>0){
			/*Anula el registro*/
			ConfiguracionImpresora configuracionImpresora2=result.get(0);
			configuracionImpresora2.setEstadoRegistro(Constantes.VALUE_INACTIVO);
			configuracionImpresora2.setUsuarioModificacion(configuracionImpresora.getUsuarioInsercion());
			configuracionImpresora2.setIpModificacion(configuracionImpresora.getIpInsercion());
			actualizar(configuracionImpresora2);
		}


		getConfiguracionImpresoraDAO().guardar(configuracionImpresora);
	}

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.service.business.ConfiguracionImpresoraManager#actualizar(com.tepsa.sisvyr.model.bean.ConfiguracionImpresora)
	 */
	@Transactional
	@Override
	public void actualizar(ConfiguracionImpresora configuracionImpresora)throws Exception {
		// TODO Auto-generated method stub
		getConfiguracionImpresoraDAO().actualizar(configuracionImpresora);
	}

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.service.business.ConfiguracionImpresoraManager#buscarPorX(java.util.TreeMap, java.util.List)
	 */
	@Override
	public ArrayList<ConfiguracionImpresora> buscarPorX(TreeMap<String, Object> criteriosBusqueda,List<String> criteriosOrdenar) {
		// TODO Auto-generated method stub
		return getConfiguracionImpresoraDAO().buscarPorX(criteriosBusqueda, criteriosOrdenar);
	}
}
