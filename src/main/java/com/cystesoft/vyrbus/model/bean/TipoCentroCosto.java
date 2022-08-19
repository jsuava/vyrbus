/**
 * Proyecto		: SISVYR
 * Sistema		: Sistema de Ventas y Reservas
 * Descripción	:
 * Autor		: José Abanto
 * Fecha		: 22/07/2015
 * Hora			: 09:07:54
 */
package com.cystesoft.vyrbus.model.bean;

/**
 * @author jabanto
 *
 */
public class TipoCentroCosto extends GenericBean{

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	private Integer id;
	private Concesionario concesionario;
	private String denominacion;
	/**
	 * @return the id
	 */
	public Integer getId() {
		return id;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(Integer id) {
		this.id = id;
	}
	/**
	 * @return the concesionario
	 */
	public Concesionario getConcesionario() {
		return concesionario;
	}
	/**
	 * @param concesionario the concesionario to set
	 */
	public void setConcesionario(Concesionario concesionario) {
		this.concesionario = concesionario;
	}
	/**
	 * @return the denominacion
	 */
	public String getDenominacion() {
		return denominacion;
	}
	/**
	 * @param denominacion the denominacion to set
	 */
	public void setDenominacion(String denominacion) {
		this.denominacion = denominacion;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "["+this.id+"]"+" --> "+this.denominacion;
	}
}
