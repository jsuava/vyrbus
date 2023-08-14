/**
 * Proyecto		: SISVYR
 * Sistema		: Sistema de Ventas y Reservas
 * Descripción	:
 * Autor		: José Avalos
 * Fecha		: 17/07/2013
 */
package pe.itsb.vyrbus.service.exceptions;

/**
 * @author JABANTO
 *
 */
public class ContactoException extends Exception {
	private static final long serialVersionUID = 1L;

	public static final int APELLIDOS_NULL=1;
	public static final int NOMBRES_NULL=2;
	public static final int FINANCIERO_APELLIDOS_NULL=3;
	public static final int FINANCIERO_NOMBRES_NULL=4;

	private Integer tipo;


	public ContactoException(){
		super();
	}

	public ContactoException(Integer tipo){
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
