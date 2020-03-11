/**
 * Proyecto		: SISVYR
 * Sistema		: Sistema de Ventas y Reservas
 * Descripción	: 
 * Fecha		: 27/06/2014
 */
package com.cystesoft.vyrbus.service.exceptions;

import java.io.Serializable;

/**
 * @author JABANTO
 *
 */
public class VSProcesaAfiliacionException extends Exception implements Serializable {
	private static final long serialVersionUID = 1L;
		
	public static final int ASEGURADOS_NULL=0;
	public static final int NUMERO_PULIZA_NULL=1;
	public static final int TIPO_DECLARACION=2;
	public static final int FECHA_INICIO_NULL=3;
	public static final int FECHA_FINAL_NULL=4;
	public static final int MONEDA_NULL=5;
	public static final int TIPO_ESTRUCTURA_NULL=6;
	public static final int TIPO_PAGO_NULL=7;
	public static final int BANCO_NULL=8;
	public static final int DOCUMENTO_NULL=9;
	
	
	private Integer tipo;

	public VSProcesaAfiliacionException(){
		super();
	}
	
	public VSProcesaAfiliacionException(Integer tipo){
		this.tipo=tipo;
	}
	
	/**
	 * @return the tipo
	 */
	public Integer getTipo() {
		return tipo;
	}

	/**
	 * @param tipo the tipo to set
	 */
	public void setTipo(Integer tipo) {
		this.tipo = tipo;
	}
	
}