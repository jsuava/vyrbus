/**
 * Proyecto		: SISVYR
 * Sistema		: Sistema de Ventas y Reservas
 * Descripción	: 
 * Autor		: José
 * Fecha		: 15/05/2013
 */
package com.cystesoft.vyrbus.service.exceptions;

/**
 * @author JABANTO
 *
 */
public class ParametrosException extends Exception{
	private static final long serialVersionUID = 1L;
	
	public static final int TIEMPO_EXPIRA_RESERVA_NULL=1;
	public static final int TIEMPO_ACUMULA_VIAJES_PARA_PAXFRE_NULL=2;
	public static final int VIAJES_REQUERIDOS_INGRESA_PAXFRE_NULL=3;
	public static final int TIEMPO_BOLETO_VALIDO_NULL=4;
	public static final int PUNTOS_ACUMULA_POR_VENTAPAXFRE_NULL=5;
	public static final int MONTO_COBRAR_POSTERGACION_NULL=6;
	public static final int MONTO_COBRAR_REIMPRESION_NULL=7;
	public static final int MAXIMO_POSTERGACIONES_BOLETO_NULL=8;
	public static final int TIEMPO_PUEDE_REALIZAR_POSTERGACION_NULL=9;
	public static final int TIEMPO_PAX_CANJE_BOLETO_CUMPLEANIOS_NULL=10;
	public static final int TIEMPO_CADUCAN_PUNTOS_OPTENIDOS_PAXFREE_NULL=11;
	public static final int ALERTA_ENVIO_ESPECIE_VALORADA_NULL=12;
	public static final int ALERTA_SOLICITA_MANIFIESTOPAX_NULL=13;
	public static final int USUARIO_GENECIA_APROBADOR_NULL=14;
	public static final int USUARIO_COMERCIAL_APROBADOR_NULL=15;
	public static final int USUARIO_FINANZAS_APROBADOR_NULL=16;
	public static final int USUARIO_GENRENCIA_COMERCIAL_NULL=17;
	public static final int MAXIMO_ASIENTOS_SELECCIONADOS_NULL=18;

	
	private Integer tipo;
	
	public ParametrosException(){
		super();
	}
	
	public ParametrosException(Integer tipo){
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
