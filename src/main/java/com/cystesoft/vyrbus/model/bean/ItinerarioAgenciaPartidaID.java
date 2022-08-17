/**
 * Proyecto		: SISVYR
 * Sistema		: Sistema de Ventas y Reservas
 * Descripción	:
 * Autor		: José Sullo Avalos
 * Fecha		: 20/08/2012
 */
package com.cystesoft.vyrbus.model.bean;

import java.io.Serializable;

/**
 *
 * @author José Sullo Avalos
 * @since JDK1.6
 */
public class ItinerarioAgenciaPartidaID extends GenericBean implements Serializable {
	private static final long serialVersionUID = 1L;
	private Long idItinerario;
	private Integer idAgencia;

	/**
	 * @param idItinerario
	 * @param idAgencia
	 */
	public ItinerarioAgenciaPartidaID(Long idItinerario, Integer idAgencia) {
		super();
		this.idItinerario = idItinerario;
		this.idAgencia = idAgencia;
	}

	/**
	 *
	 */
	public ItinerarioAgenciaPartidaID() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Long getIdItinerario() {
		return idItinerario;
	}

	public void setIdItinerario(Long idItinerario) {
		this.idItinerario = idItinerario;
	}

	public Integer getIdAgencia() {
		return idAgencia;
	}

	public void setIdAgencia(Integer idAgencia) {
		this.idAgencia = idAgencia;
	}
}
