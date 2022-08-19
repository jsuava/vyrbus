
package com.cystesoft.vyrbus.service.exceptions;

import java.io.Serializable;

/**
 * @author JABANTO
 *
 */
public class EstadoVentasReservasException extends Exception implements Serializable{
	private static final long serialVersionUID = 1L;

	public static final int FECHA_INCIO_NULL=0;
	public static final int FECHA_FIN_NULL=1;
	public static final int FECHA_PARTIDA_NULL=2;
	public static final int FECHAS_NULL=3;

	private Integer tipo;

	public EstadoVentasReservasException(){
		super();
	}

	public EstadoVentasReservasException(Integer tipo){
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
