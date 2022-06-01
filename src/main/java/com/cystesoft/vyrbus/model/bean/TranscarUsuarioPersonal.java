/**
 * Proyecto		: SISVYR
 * Sistema		: Sistema de Ventas y Reservas
 * Descripción	: 
 * Autor		: José Abanto
 * Fecha		: 26 oct. 2021
 * Hora			: 16:29:42
 */
package com.cystesoft.vyrbus.model.bean;

import java.io.Serializable;

/**
 * @author abant
 *
 */
public class TranscarUsuarioPersonal extends GenericBean implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Integer id;
	private Integer agenciaId;
	private Integer tipoDocumentoIdentidad = 2; //Solo para completar
	private String apellidoParterno;
	private String apellidoMaterno;
	private String nombres;
	private String login;
	private String password;
	private String email;
	private Integer sexoId;
	private Integer distrito = 2; // Solo para completar
	private Integer provincia = 17; // Solo para completar
	private Integer departamentoId= 15; // Solo para completar
	private Integer ubigeo = 7895; // Solo para completar
	private Integer estadoCivil = 1; // Solo para completar
	private Integer permiteVentaOtrasAgencias;
	private Integer autorizaEntregaSinVerificarUsuario;
	
	public TranscarUsuarioPersonal() {
		super();
	}
	
	public TranscarUsuarioPersonal(Integer id) {
		this.id = id;
	}
	
	public TranscarUsuarioPersonal(Integer id, String login) {
		this.id = id;
		this.login = login;
	}
	
//	private Integer rolDefectoId;
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
	 * @return the agenciaId
	 */
	public Integer getAgenciaId() {
		return agenciaId;
	}
	/**
	 * @param agenciaId the agenciaId to set
	 */
	public void setAgenciaId(Integer agenciaId) {
		this.agenciaId = agenciaId;
	}
	/**
	 * @return the tipoDocumentoIdentidad
	 */
	public Integer getTipoDocumentoIdentidad() {
		return tipoDocumentoIdentidad;
	}
	/**
	 * @param tipoDocumentoIdentidad the tipoDocumentoIdentidad to set
	 */
	public void setTipoDocumentoIdentidad(Integer tipoDocumentoIdentidad) {
		this.tipoDocumentoIdentidad = tipoDocumentoIdentidad;
	}
	/**
	 * @return the apellidoParterno
	 */
	public String getApellidoParterno() {
		return apellidoParterno;
	}
	/**
	 * @param apellidoParterno the apellidoParterno to set
	 */
	public void setApellidoParterno(String apellidoParterno) {
		this.apellidoParterno = apellidoParterno;
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
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}
	/**
	 * @param password the password to set
	 */
	public void setPassword(String password) {
		this.password = password;
	}
	/**
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}
	/**
	 * @param email the email to set
	 */
	public void setEmail(String email) {
		this.email = email;
	}
	/**
	 * @return the sexoId
	 */
	public Integer getSexoId() {
		return sexoId;
	}
	/**
	 * @param sexoId the sexoId to set
	 */
	public void setSexoId(Integer sexoId) {
		this.sexoId = sexoId;
	}
	/**
	 * @return the distrito
	 */
	public Integer getDistrito() {
		return distrito;
	}
	/**
	 * @param distrito the distrito to set
	 */
	public void setDistrito(Integer distrito) {
		this.distrito = distrito;
	}
	/**
	 * @return the provincia
	 */
	public Integer getProvincia() {
		return provincia;
	}
	/**
	 * @param provincia the provincia to set
	 */
	public void setProvincia(Integer provincia) {
		this.provincia = provincia;
	}
	/**
	 * @return the departamentoId
	 */
	public Integer getDepartamentoId() {
		return departamentoId;
	}
	/**
	 * @param departamentoId the departamentoId to set
	 */
	public void setDepartamentoId(Integer departamentoId) {
		this.departamentoId = departamentoId;
	}
	/**
	 * @return the ubigeo
	 */
	public Integer getUbigeo() {
		return ubigeo;
	}
	/**
	 * @param ubigeo the ubigeo to set
	 */
	public void setUbigeo(Integer ubigeo) {
		this.ubigeo = ubigeo;
	}
	/**
	 * @return the estadoCivil
	 */
	public Integer getEstadoCivil() {
		return estadoCivil;
	}
	/**
	 * @param estadoCivil the estadoCivil to set
	 */
	public void setEstadoCivil(Integer estadoCivil) {
		this.estadoCivil = estadoCivil;
	}
	/**
	 * @return the permiteVentaOtrasAgencias
	 */
	public Integer getPermiteVentaOtrasAgencias() {
		return permiteVentaOtrasAgencias;
	}
	/**
	 * @param permiteVentaOtrasAgencias the permiteVentaOtrasAgencias to set
	 */
	public void setPermiteVentaOtrasAgencias(Integer permiteVentaOtrasAgencias) {
		this.permiteVentaOtrasAgencias = permiteVentaOtrasAgencias;
	}
	/**
	 * @return the autorizaEntregaSinVerificarUsuario
	 */
	public Integer getAutorizaEntregaSinVerificarUsuario() {
		return autorizaEntregaSinVerificarUsuario;
	}
	/**
	 * @param autorizaEntregaSinVerificarUsuario the autorizaEntregaSinVerificarUsuario to set
	 */
	public void setAutorizaEntregaSinVerificarUsuario(Integer autorizaEntregaSinVerificarUsuario) {
		this.autorizaEntregaSinVerificarUsuario = autorizaEntregaSinVerificarUsuario;
	}

}
