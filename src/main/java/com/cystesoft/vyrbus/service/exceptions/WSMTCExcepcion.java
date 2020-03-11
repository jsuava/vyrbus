/**
 * Proyecto		: SISVYR
 * Sistema		: Sistema de Ventas y Reservas
 * Descripción	: 
 * Autor		: José Abanto
 * Fecha		: 20/08/2014
 * Hora			: 11:34:49
 */
package com.cystesoft.vyrbus.service.exceptions;

import java.io.Serializable;

/**
 * @author JABANTO
 *
 */
public class WSMTCExcepcion extends Exception implements Serializable{
	private static final long serialVersionUID = 1L;
	
//	public static final int PILOTO_INAVILITADO=1;
//	public static final int COPILOTO_INAVILITADO=2;
//	public static final int TRIPILOTO_INAVILITADO=3;
//	public static final int VEHICULO_INAVILITADO=4;
	
//	private Integer tipo;

	public WSMTCExcepcion(){
		super();
	}
	public WSMTCExcepcion(String message){
		super(message);
	}
	
//	
//	/**
//	 * @return the tipo
//	 */
//	public Integer getTipo() {
//		return tipo;
//	}
//	/**
//	 * @param tipo the tipo to set
//	 */
//	public void setTipo(Integer tipo) {
//		this.tipo = tipo;
//	}
	
}
