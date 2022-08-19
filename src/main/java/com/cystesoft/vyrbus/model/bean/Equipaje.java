/**
 * Proyecto		: SISVYR
 * Sistema		: Sistema de Ventas y Reservas
 * Descripción	:
 * Autor		: José Abanto
 * Fecha		: 14 jul. 2021
 * Hora			: 11:37:14
 */
package com.cystesoft.vyrbus.model.bean;

/**
 * @author abant
 *
 */
public class Equipaje extends GenericBean implements java.io.Serializable{

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	private Long id;
	private Itinerario itinerario;
	private Agencia agencia;
	private Double peso;
	private String observaciones;

	private UsuarioHardware usuarioHardware;

	public Equipaje() {
		super();
	}

	public Equipaje(Long id) {
		this.id=id;
	}

	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}
	/**
	 * @return the itinerario
	 */
	public Itinerario getItinerario() {
		return itinerario;
	}
	/**
	 * @param itinerario the itinerario to set
	 */
	public void setItinerario(Itinerario itinerario) {
		this.itinerario = itinerario;
	}
	/**
	 * @return the agencia
	 */
	public Agencia getAgencia() {
		return agencia;
	}
	/**
	 * @param agencia the agencia to set
	 */
	public void setAgencia(Agencia agencia) {
		this.agencia = agencia;
	}
	/**
	 * @return the peso
	 */
	public Double getPeso() {
		return peso;
	}
	/**
	 * @param peso the peso to set
	 */
	public void setPeso(Double peso) {
		this.peso = peso;
	}
	/**
	 * @return the usuarioHardware
	 */
	public UsuarioHardware getUsuarioHardware() {
		return usuarioHardware;
	}
	/**
	 * @param usuarioHardware the usuarioHardware to set
	 */
	public void setUsuarioHardware(UsuarioHardware usuarioHardware) {
		this.usuarioHardware = usuarioHardware;
	}

	/**
	 * @return the observaciones
	 */
	public String getObservaciones() {
		return observaciones;
	}

	/**
	 * @param observaciones the observaciones to set
	 */
	public void setObservaciones(String observaciones) {
		this.observaciones = observaciones;
	}


}
