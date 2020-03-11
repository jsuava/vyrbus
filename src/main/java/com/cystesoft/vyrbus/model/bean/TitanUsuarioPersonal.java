/**
 * Proyecto		: SISVYR
 * Sistema		: Sistema de Ventas y Reservas
 * Descripción	: 
 * Autor		: José Avalos Sullo
 * Fecha		: 31/07/2014
 */
package com.cystesoft.vyrbus.model.bean;

import java.util.List;

/**
 * @author JABANTO
 *
 */
public class TitanUsuarioPersonal {
	private Long id;
	private String login;
	private String apellidoPaterno;
	private String apellidoMaterno;
	private String nombres;
	private List<TitanUsuarioRol> lstUsuarioRol;
	
	public TitanUsuarioPersonal(){
		super();
	}
	public TitanUsuarioPersonal(Long id){
		this.setId(id);
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
	 * @return the login
	 */
	public String getLogin() {
		return login;
	}
	/**
	 * @param login the login to set
	 */
	public void setLogin(String login) {
		this.login = login;
	}
	/**
	 * @return the apellidoPaterno
	 */
	public String getApellidoPaterno() {
		return apellidoPaterno;
	}
	/**
	 * @param apellidoPaterno the apellidoPaterno to set
	 */
	public void setApellidoPaterno(String apellidoPaterno) {
		this.apellidoPaterno = apellidoPaterno;
	}
	/**
	 * @return the apellidoMaterno
	 */
	public String getApellidoMaterno() {
		return apellidoMaterno;
	}
	/**
	 * @param apellidoMaterno the apellidoMaterno to set
	 */
	public void setApellidoMaterno(String apellidoMaterno) {
		this.apellidoMaterno = apellidoMaterno;
	}
	/**
	 * @return the nombres
	 */
	public String getNombres() {
		return nombres;
	}
	/**
	 * @param nombres the nombres to set
	 */
	public void setNombres(String nombres) {
		this.nombres = nombres;
	}
	/**
	 * @return the lstUsuarioRol
	 */
	public List<TitanUsuarioRol> getLstUsuarioRol() {
		return lstUsuarioRol;
	}
	/**
	 * @param lstUsuarioRol the lstUsuarioRol to set
	 */
	public void setLstUsuarioRol(List<TitanUsuarioRol> lstUsuarioRol) {
		this.lstUsuarioRol = lstUsuarioRol;
	}	
	
	@Override
	public String toString(){
		return (this.apellidoPaterno!=null?this.apellidoPaterno:"")+""+(this.apellidoMaterno!=null?this.apellidoMaterno:"")+","+(this.nombres!=null?this.nombres:"");
	}
}
