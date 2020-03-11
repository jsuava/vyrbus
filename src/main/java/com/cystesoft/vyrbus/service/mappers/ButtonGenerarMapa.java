/**
 * Proyecto		: SISVYR
 * Sistema		: Sistema de Ventas y Reservas
 * Descripción	: 
 * Autor		: José Sullo Avalos
 * Fecha		: 20/10/2012
 */
package com.cystesoft.vyrbus.service.mappers;

import org.zkoss.zul.Toolbarbutton;

/**
 * @author Jose
 *
 */
public class ButtonGenerarMapa extends Toolbarbutton {
	private static final long serialVersionUID = 1L;
	private Integer idServicio;
	
	/**
	 * 
	 */
	public ButtonGenerarMapa() {
		super();
		// TODO Auto-generated constructor stub
	}
	/**
	 * @param label
	 * @param image
	 */
	public ButtonGenerarMapa(String label, String image) {
		super(label, image);
		// TODO Auto-generated constructor stub
	}
	/**
	 * @param label
	 */
	public ButtonGenerarMapa(String label) {
		super(label);
		// TODO Auto-generated constructor stub
	}
	
	/**
	 * @return the idServicio
	 */
	public Integer getIdServicio() {
		return idServicio;
	}
	/**
	 * @param idServicio the idServicio to set
	 */
	public void setIdServicio(Integer idServicio) {
		this.idServicio = idServicio;
	}
}
