/**
 * Proyecto		: SISVYR
 * Sistema		: Sistema de Ventas y Reservas
 * Descripción	: 
 * Autor		: José Abanto
 * Fecha		: 21/05/2015
 * Hora			: 15:24:12
 */
package com.cystesoft.vyrbus.model.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

import com.cystesoft.vyrbus.model.bean.ConfiguracionImpresora;

/**
 * @author Jose
 *
 */
public interface ConfiguracionImpresoraDAO extends GenericDAO{
	
	/**
	 * Busca alguna impresora configurada.
	 * @return	objeto configuracionImpresora.
	 * @throws Exception
	 */
	public ConfiguracionImpresora buscarImpresora()throws Exception;
	/**
	 * Guarda una nueva instancia
	 * @param configuracionImpresora	: Nueva instancia de la entidad
	 * @throws Exception
	 */
	public void guardar(ConfiguracionImpresora configuracionImpresora)throws Exception;
	/**
	 * Actualiza una instancia.
	 * @param configuracionImpresora	: Instancia a actualizar
	 * @throws Exception
	 */
	public void actualizar(ConfiguracionImpresora configuracionImpresora)throws Exception;
	
	public ArrayList<ConfiguracionImpresora> buscarPorX(TreeMap<String, Object> criteriosBusqueda, List<String> criteriosOrdenar);
	
}
