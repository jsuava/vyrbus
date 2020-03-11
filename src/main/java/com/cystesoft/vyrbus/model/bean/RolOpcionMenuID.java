/**
 * Proyecto		: SISVYR
 * Sistema		: Sistema de Ventas y Reservas
 * Descripción	: 
 * Autor		: José Sullo Avalos
 * Fecha		: 25/05/2012
 */
package com.cystesoft.vyrbus.model.bean;

import java.io.Serializable;

/**
 *
 * @author José Sullo Avalos
 * @since JDK1.6
 */
public class RolOpcionMenuID extends GenericBean implements Serializable {
	private static final long serialVersionUID = 1L;
	private Integer idRol;
	private Integer idOpcionMenu;
	/**
	 * @return Objeto idRol.
	 */
	public Integer getIdRol() {
		return idRol;
	}
	/**
	 * @param idRol	: Setea el objeto idRol.
	 */
	public void setIdRol(Integer idRol) {
		this.idRol = idRol;
	}
	/**
	 * @return Objeto idOpcionMenu.
	 */
	public Integer getIdOpcionMenu() {
		return idOpcionMenu;
	}
	/**
	 * @param idOpcionMenu	: Setea el objeto idOpcionMenu.
	 */
	public void setIdOpcionMenu(Integer idOpcionMenu) {
		this.idOpcionMenu = idOpcionMenu;
	}
}
