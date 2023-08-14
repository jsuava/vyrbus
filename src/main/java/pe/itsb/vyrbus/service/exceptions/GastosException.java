/**
 * Proyecto		: SISVYR
 * Sistema		: Sistema de Ventas y Reservas
 * Descripción	:
 * Autor		: José Avalos
 * Fecha		: 24/06/2013
 */
package pe.itsb.vyrbus.service.exceptions;

/**
 * @author JABANTO
 *
 */
public class GastosException extends Exception {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	public static final int TIPO_GASTO_NULL=1;
	public static final int MONTO_NULL=2;
	public static final int MONTO_GASTO_MAYOR_VENTAS=3;
	public static final int CONSIGNADO_NULL=4;
	public static final int OBSERVACIONES_NULL=5;
	public static final int	DOCUMENTO_NO_VALIDO=6;
	public static final int	CTACTE_NULL=7;
	public static final int	HORADEPOSITO_NULL=8;


	private int tipo;

	public GastosException(){
		super();
	}

	public GastosException(int tipo){
		this.setTipo(tipo);
	}


	/**
	 * @return the tipo
	 */
	public int getTipo() {
		return tipo;
	}


	/**
	 * @param tipo the tipo to set
	 */
	public void setTipo(int tipo) {
		this.tipo = tipo;
	}

}
