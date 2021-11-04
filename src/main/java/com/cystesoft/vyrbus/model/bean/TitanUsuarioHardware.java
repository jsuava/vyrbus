/**
 * Proyecto		: SISVYR
 * Sistema		: Sistema de Ventas y Reservas
 * Descripción	: 
 * Autor		: José Abanto
 * Fecha		: 26 oct. 2021
 * Hora			: 22:38:15
 */
package com.cystesoft.vyrbus.model.bean;

import java.io.Serializable;

/**
 * @author Jose
 *
 */
public class TitanUsuarioHardware implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private String ip;
	private Integer idDepartamento;
	private Integer idTipoMaquina;
	private Integer frecuenciaReloj;
	private String nombreEquipo;
	private String nombreRed;
	private Integer esServidor;
	private Integer particiones;
	private Integer memoria;
	private Integer idUsuario;
	private Integer idRol;
	private Integer idUsuarioModificacion;
	private Integer rolModificacion;
	private String ipRegistro;
	private String ipModificacion;
	private Integer idAgencia;
	private Integer idTipoComputador;
	private Integer idTipoIP;
	/**
	 * @return the ip
	 */
	public String getIp() {
		return ip;
	}
	/**
	 * @param ip the ip to set
	 */
	public void setIp(String ip) {
		this.ip = ip;
	}
	/**
	 * @return the idDepartamento
	 */
	public Integer getIdDepartamento() {
		return idDepartamento;
	}
	/**
	 * @param idDepartamento the idDepartamento to set
	 */
	public void setIdDepartamento(Integer idDepartamento) {
		this.idDepartamento = idDepartamento;
	}
	/**
	 * @return the idTipoMaquina
	 */
	public Integer getIdTipoMaquina() {
		return idTipoMaquina;
	}
	/**
	 * @param idTipoMaquina the idTipoMaquina to set
	 */
	public void setIdTipoMaquina(Integer idTipoMaquina) {
		this.idTipoMaquina = idTipoMaquina;
	}
	/**
	 * @return the frecuenciaReloj
	 */
	public Integer getFrecuenciaReloj() {
		return frecuenciaReloj;
	}
	/**
	 * @param frecuenciaReloj the frecuenciaReloj to set
	 */
	public void setFrecuenciaReloj(Integer frecuenciaReloj) {
		this.frecuenciaReloj = frecuenciaReloj;
	}
	/**
	 * @return the nombreEquipo
	 */
	public String getNombreEquipo() {
		return nombreEquipo;
	}
	/**
	 * @param nombreEquipo the nombreEquipo to set
	 */
	public void setNombreEquipo(String nombreEquipo) {
		this.nombreEquipo = nombreEquipo;
	}
	/**
	 * @return the nombreRed
	 */
	public String getNombreRed() {
		return nombreRed;
	}
	/**
	 * @param nombreRed the nombreRed to set
	 */
	public void setNombreRed(String nombreRed) {
		this.nombreRed = nombreRed;
	}
	/**
	 * @return the esServidor
	 */
	public Integer getEsServidor() {
		return esServidor;
	}
	/**
	 * @param esServidor the esServidor to set
	 */
	public void setEsServidor(Integer esServidor) {
		this.esServidor = esServidor;
	}
	/**
	 * @return the particiones
	 */
	public Integer getParticiones() {
		return particiones;
	}
	/**
	 * @param particiones the particiones to set
	 */
	public void setParticiones(Integer particiones) {
		this.particiones = particiones;
	}
	/**
	 * @return the memoria
	 */
	public Integer getMemoria() {
		return memoria;
	}
	/**
	 * @param memoria the memoria to set
	 */
	public void setMemoria(Integer memoria) {
		this.memoria = memoria;
	}
	/**
	 * @return the idUsuario
	 */
	public Integer getIdUsuario() {
		return idUsuario;
	}
	/**
	 * @param idUsuario the idUsuario to set
	 */
	public void setIdUsuario(Integer idUsuario) {
		this.idUsuario = idUsuario;
	}
	/**
	 * @return the idRol
	 */
	public Integer getIdRol() {
		return idRol;
	}
	/**
	 * @param idRol the idRol to set
	 */
	public void setIdRol(Integer idRol) {
		this.idRol = idRol;
	}
	/**
	 * @return the idUsuarioModificacion
	 */
	public Integer getIdUsuarioModificacion() {
		return idUsuarioModificacion;
	}
	/**
	 * @param idUsuarioModificacion the idUsuarioModificacion to set
	 */
	public void setIdUsuarioModificacion(Integer idUsuarioModificacion) {
		this.idUsuarioModificacion = idUsuarioModificacion;
	}
	/**
	 * @return the rolModificacion
	 */
	public Integer getRolModificacion() {
		return rolModificacion;
	}
	/**
	 * @param rolModificacion the rolModificacion to set
	 */
	public void setRolModificacion(Integer rolModificacion) {
		this.rolModificacion = rolModificacion;
	}
	/**
	 * @return the ipRegistro
	 */
	public String getIpRegistro() {
		return ipRegistro;
	}
	/**
	 * @param ipRegistro the ipRegistro to set
	 */
	public void setIpRegistro(String ipRegistro) {
		this.ipRegistro = ipRegistro;
	}
	/**
	 * @return the ipModificacion
	 */
	public String getIpModificacion() {
		return ipModificacion;
	}
	/**
	 * @param ipModificacion the ipModificacion to set
	 */
	public void setIpModificacion(String ipModificacion) {
		this.ipModificacion = ipModificacion;
	}
	/**
	 * @return the idAgencia
	 */
	public Integer getIdAgencia() {
		return idAgencia;
	}
	/**
	 * @param idAgencia the idAgencia to set
	 */
	public void setIdAgencia(Integer idAgencia) {
		this.idAgencia = idAgencia;
	}
	/**
	 * @return the idTipoComputador
	 */
	public Integer getIdTipoComputador() {
		return idTipoComputador;
	}
	/**
	 * @param idTipoComputador the idTipoComputador to set
	 */
	public void setIdTipoComputador(Integer idTipoComputador) {
		this.idTipoComputador = idTipoComputador;
	}
	/**
	 * @return the idTipoIP
	 */
	public Integer getIdTipoIP() {
		return idTipoIP;
	}
	/**
	 * @param idTipoIP the idTipoIP to set
	 */
	public void setIdTipoIP(Integer idTipoIP) {
		this.idTipoIP = idTipoIP;
	}
	
}
