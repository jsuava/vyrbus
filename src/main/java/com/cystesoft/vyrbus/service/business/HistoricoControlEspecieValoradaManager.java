/**
 * Proyecto		: SISVYR
 * Sistema		: Sistema de Ventas y Reservas
 * Descripción	:
 * Autor		: José Abanto
 * Fecha		: 09/09/2013
 */
package com.cystesoft.vyrbus.service.business;

import com.cystesoft.vyrbus.model.bean.HistoricoControlEspecieValorada;


/**
 * @author JABANTO
 *
 */
public interface HistoricoControlEspecieValoradaManager {
	/**
	 * Guarda historico del control especie valorada
	 * @param historicoControlEspecieValorada	: objeto HistoricoControlEspecieValorada
	 * @throws Exception
	 */
	public void guardar(HistoricoControlEspecieValorada historicoControlEspecieValorada) throws Exception;
}
