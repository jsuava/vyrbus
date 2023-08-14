/**
 * Proyecto		: SISVYR
 * Sistema		: Sistema de Ventas y Reservas
 * Descripción	:
 * Autor		: José Abanto
 * Fecha		: 15/05/2013
 */
package pe.itsb.vyrbus.service.exceptions;

/**
 * @author JABANTO
 *
 */
public class ItinerarioException extends Exception {
	private static final long serialVersionUID = 1L;

	public static final int FECHA_MENOR=1;
	public static final int NO_SELECT=2;
	public static final int ITINERARIO_NULL=3;
	public static final int CON_VENTAS=4;
	public static final int TRAMOS_DIFERENTES=5;
	public static final int TRAMOS_DUPLICADOS=6;
	public static final int FECHA_NEX_TRAMO_MENOR=7;
	public static final int HORA_NEX_TRAMO_MENOR=8;
	public static final int TIPO_SERVICIO_DIFERENTE=9;
	public static final int TIPO_ITINERARIO_DIFERENTE=10;
	public static final int ES_NULL_IDA=11;
	public static final int ES_NULL_RETORNO=12;
	public static final int AGENCIA_PARTIDA_NULL=13;
	public static final int AGENCIA_LLEGADA_NULL=14;
	public static final int AGENCIA_PARTIDA_RETORNO_NULL=15;
	public static final int AGENCIA_LLEGADA_RETORNO_NULL=16;
	public static final int TARIFA_IDA_CERO = 17;
	public static final int TARIFA_RETORNO_CERO = 18;
	public static final int CON_HRE=19;

	private Integer tipo;

	public ItinerarioException(){
		super();
	}

	public ItinerarioException(Integer tipo){
		this.setTipo(tipo);
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
