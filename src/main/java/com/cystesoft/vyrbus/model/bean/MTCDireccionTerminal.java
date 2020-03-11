/**
 * Proyecto		: SISVYR
 * Sistema		: Sistema de Ventas y Reservas
 * Descripción	: 
 * Autor		: José Abanto
 * Fecha		: 25/08/2014
 * Hora			: 14:27:24
 */
package com.cystesoft.vyrbus.model.bean;

/**
 * @author JABANTO
 *
 */
public class MTCDireccionTerminal extends GenericBean {
	private static final long serialVersionUID = 1L;
	
	private Integer id;
	private Integer codigo;
	private String direccion;
	private Agencia agencia;
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
	 * @return the codigo
	 */
	public Integer getCodigo() {
		return codigo;
	}
	/**
	 * @param codigio the codigio to set
	 */
	public void setCodigo(Integer codigio) {
		this.codigo = codigio;
	}
	/**
	 * @return the direccion
	 */
	public String getDireccion() {
		return direccion;
	}
	/**
	 * @param direccion the direccion to set
	 */
	public void setDireccion(String direccion) {
		this.direccion = direccion;
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
	
	@Override
	public String toString(){
		return this.direccion;
	}
}
