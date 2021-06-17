
package com.cystesoft.vyrbus.service.exceptions;

/**
 * @author JABANTO
 *
 */
public class CorrelativoException extends Exception {
	private static final long serialVersionUID = 1L;
	
	public static final int INICIAL_NULL=1;
	public static final int FINAL_NULL=2;
	public static final int ACTUAL_NULL=3;
	public static final int INCORRECTO=4;
	public static final int FUERA_RANGO=5;
	public static final int FINAL_MENOR_INICIAL=6;
	public static final int ACTUAL_MAYOR_FINAL=7;
	public static final int ACTUAL_MENOR_INICIAL=8;
	public static final int DUPLICADO=9;
	public static final int CORRELATIVO_INICIAL_MENOR_DB=10;
	public static final int CORRELATIVO_FINAL_MENOR_DB=11;
	public static final int SERIE_NO_REGISTRADA=12;
	public static final int SERIE_DUPLICADA=13;
	
	private Integer tipo;
	
	public CorrelativoException(){
		super();
	}
	
	public CorrelativoException(Integer tipo){
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
