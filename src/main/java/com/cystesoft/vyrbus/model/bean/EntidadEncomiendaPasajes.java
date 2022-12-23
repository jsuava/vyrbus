/**
 * Proyecto		: SISVYR
 * Sistema		: Sistema de Ventas y Reservas
 * Descripción	: 
 * Autor		: José Abanto
 * Fecha		: 26 set. 2022
 * Hora			: 13:28:25
 */
package com.cystesoft.vyrbus.model.bean;

import java.util.ArrayList;

/**
 * @author Marco
 *
 */
public class EntidadEncomiendaPasajes extends GenericBean implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	Integer idAgenciaEncomienda;
	Integer idAgenciaPasajes;
	Integer idTipoEntidad;
	
	public Integer getIdAgenciaEncomienda() {
		return idAgenciaEncomienda;
	}
	public void setIdAgenciaEncomienda(Integer idAgenciaEncomienda) {
		this.idAgenciaEncomienda = idAgenciaEncomienda;
	}
	public Integer getIdAgenciaPasajes() {
		return idAgenciaPasajes;
	}
	public void setIdAgenciaPasajes(Integer idAgenciaPasajes) {
		this.idAgenciaPasajes = idAgenciaPasajes;
	}
	public Integer getIdTipoEntidad() {
		return idTipoEntidad;
	}
	public void setIdTipoEntidad(Integer idTipoEntidad) {
		this.idTipoEntidad = idTipoEntidad;
	}
	
	
	
}

