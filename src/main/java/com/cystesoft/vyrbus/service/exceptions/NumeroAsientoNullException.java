/**
 * Proyecto		: SISVYR
 * Sistema		: Sistema de Ventas y Reservas
 * Descripción	: 
 * Autor		: José Sullo Avalos
 * Fecha		: 31/08/2012
 */
package com.cystesoft.vyrbus.service.exceptions;

import java.io.Serializable;

/**
 *
 * @author JABANTO
 * @since JDK1.6
 */
public class NumeroAsientoNullException  extends Exception implements Serializable {
	private static final long serialVersionUID = 1L;
	private Integer numeroPiso;
	/**
	 * 
	 */
	public NumeroAsientoNullException() {
		super();
		// TODO Auto-generated constructor stub
	}
	/**
	 * @param numeroPiso
	 */
	public NumeroAsientoNullException(Integer numeroPiso) {
		super();
		this.numeroPiso = numeroPiso;
	}
	
	/**
	 * @return the numeroPiso
	 */
	public Integer getNumeroPiso() {
		return numeroPiso;
	}
	/**
	 * @param numeroPiso the numeroPiso to set
	 */
	public void setNumeroPiso(Integer numeroPiso) {
		this.numeroPiso = numeroPiso;
	}
}
