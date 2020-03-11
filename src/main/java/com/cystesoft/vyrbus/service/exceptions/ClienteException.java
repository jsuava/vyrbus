
package com.cystesoft.vyrbus.service.exceptions;

/**
 * @author JABANTO
 *
 */
public class ClienteException extends Exception {
	private static final long serialVersionUID = 1L;
	
	public static final int CLIENTE_NULL=1;
	public static final int NO_EXISTE=2;
	public static final int SOLICITUD_CARTERA=3;
	public static final int ASIGNACION_CARTERA=4;
	public static final int RUC_NULL=5;
	public static final int NO_DIRECCION=6;

	private Integer tipo;
	
	public ClienteException(){
		super();
	}
	
	public ClienteException(Integer tipo){
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
