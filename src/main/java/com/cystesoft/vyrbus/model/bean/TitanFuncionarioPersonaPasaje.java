/**
 * Proyecto		: SISVYR
 * Sistema		: Sistema de Ventas y Reservas
 * Descripción	:
 * Autor		: José Aabnto
 * Fecha		: 31/07/2014
 */
package com.cystesoft.vyrbus.model.bean;

import java.util.Date;

/**
 * @author JABANTO
 *
 */
public class TitanFuncionarioPersonaPasaje {
	private TitanUsuarioPersonal funcionario;
	private TitanUsuarioPersonal funcionarioActual;
	private Integer tipoFuncionarioID;
	private TitanPersona persona;
	private TitanUsuarioPersonal usuarioPersonal;
	private Integer rolUsuarioID;
	private String ip;
	private Date fechaRegistro;
	private String fechaActivacion;
	private String fechaSuspencion;
	private Integer comisionable;
	private Integer estadoRegistroID;
	/**
	 * @return the funcionario
	 */
	public TitanUsuarioPersonal getFuncionario() {
		return funcionario;
	}
	/**
	 * @param funcionario the funcionario to set
	 */
	public void setFuncionario(TitanUsuarioPersonal funcionario) {
		this.funcionario = funcionario;
	}
	/**
	 * @return the funcionarioActual
	 */
	public TitanUsuarioPersonal getFuncionarioActual() {
		return funcionarioActual;
	}
	/**
	 * @param funcionarioActual the funcionarioActual to set
	 */
	public void setFuncionarioActual(TitanUsuarioPersonal funcionarioActual) {
		this.funcionarioActual = funcionarioActual;
	}
	/**
	 * @return the tipoFuncionarioID
	 */
	public Integer getTipoFuncionarioID() {
		return tipoFuncionarioID;
	}
	/**
	 * @param tipoFuncionarioID the tipoFuncionarioID to set
	 */
	public void setTipoFuncionarioID(Integer tipoFuncionarioID) {
		this.tipoFuncionarioID = tipoFuncionarioID;
	}
	/**
	 * @return the persona
	 */
	public TitanPersona getPersona() {
		return persona;
	}
	/**
	 * @param persona the persona to set
	 */
	public void setPersona(TitanPersona persona) {
		this.persona = persona;
	}
	/**
	 * @return the usuarioPersonal
	 */
	public TitanUsuarioPersonal getUsuarioPersonal() {
		return usuarioPersonal;
	}
	/**
	 * @param usuarioPersonal the usuarioPersonal to set
	 */
	public void setUsuarioPersonal(TitanUsuarioPersonal usuarioPersonal) {
		this.usuarioPersonal = usuarioPersonal;
	}
	/**
	 * @return the rolUsuarioID
	 */
	public Integer getRolUsuarioID() {
		return rolUsuarioID;
	}
	/**
	 * @param rolUsuarioID the rolUsuarioID to set
	 */
	public void setRolUsuarioID(Integer rolUsuarioID) {
		this.rolUsuarioID = rolUsuarioID;
	}
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
	 * @return the fechaRegistro
	 */
	public Date getFechaRegistro() {
		return fechaRegistro;
	}
	/**
	 * @param fechaRegistro the fechaRegistro to set
	 */
	public void setFechaRegistro(Date fechaRegistro) {
		this.fechaRegistro = fechaRegistro;
	}

	/**
	 * @return the comisionable
	 */
	public Integer getComisionable() {
		return comisionable;
	}
	/**
	 * @param comisionable the comisionable to set
	 */
	public void setComisionable(Integer comisionable) {
		this.comisionable = comisionable;
	}
	/**
	 * @return the estadoRegistroID
	 */
	public Integer getEstadoRegistroID() {
		return estadoRegistroID;
	}
	/**
	 * @param estadoRegistroID the estadoRegistroID to set
	 */
	public void setEstadoRegistroID(Integer estadoRegistroID) {
		this.estadoRegistroID = estadoRegistroID;
	}
	/**
	 * @return the fechaActivacion
	 */
	public String getFechaActivacion() {
		return fechaActivacion;
	}
	/**
	 * @param fechaActivacion the fechaActivacion to set
	 */
	public void setFechaActivacion(String fechaActivacion) {
		this.fechaActivacion = fechaActivacion;
	}
	/**
	 * @return the fechaSuspencion
	 */
	public String getFechaSuspencion() {
		return fechaSuspencion;
	}
	/**
	 * @param fechaSuspencion the fechaSuspencion to set
	 */
	public void setFechaSuspencion(String fechaSuspencion) {
		this.fechaSuspencion = fechaSuspencion;
	}

}
