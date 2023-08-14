/**
 * Proyecto		: ventaseguro
 * Sistema		: Sistema de Venta de Seguros
 * Descripción	:
 * Autor		: José Abanto
 * Fecha		: 25/06/2014 09:59:24
 */
package pe.itsb.vyrbus.service.exceptions;

import java.io.Serializable;

/**
 * @author JABANTO
 *
 */
public class VSAfialiacionException extends Exception implements Serializable {
	private static final long serialVersionUID = 1L;
	public static final int DUPLICITY_CERTIFICADO=1;

	private Integer tipo;

	public VSAfialiacionException(){
		super();
	}

	public VSAfialiacionException(int tipo){
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
