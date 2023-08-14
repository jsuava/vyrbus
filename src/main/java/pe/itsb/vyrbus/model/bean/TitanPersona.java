/**
 * Proyecto		: SISVYR
 * Sistema		: Sistema de Ventas y Reservas
 * Descripción	:
 * Autor		: José Abanto
 * Fecha		: 31/07/2014
 */
package pe.itsb.vyrbus.model.bean;

import java.util.Date;

/**
 * @author JABANTO
 *
 */
public class TitanPersona {
	private Long id;
	private Integer tipoPersona;
	private String codigoCliente;
	private Integer clienteCorporativo;
	private String RazonSocial;
	private Date fechaIngreso;
	private Integer tipoDocumentoIdentidadID;
	private String numeroDocumentoSunat;
	private Integer rubroID;
	private Integer clasificacionPersonaID;
	private String tipoCredito;
	private Integer origen;
	private Integer estadoRegistro;
	private TitanUsuarioPersonal usuarioPersonal;
	private Integer rolUsuarioID;
	private String ip;
	private String fechaRegistro;


	public TitanPersona(){
		super();
	}
	public TitanPersona(Long id){
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
	 * @return the tipoPersona
	 */
	public Integer getTipoPersona() {
		return tipoPersona;
	}
	/**
	 * @param tipoPersona the tipoPersona to set
	 */
	public void setTipoPersona(Integer tipoPersona) {
		this.tipoPersona = tipoPersona;
	}
	/**
	 * @return the codigoCliente
	 */
	public String getCodigoCliente() {
		return codigoCliente;
	}
	/**
	 * @param codigoCliente the codigoCliente to set
	 */
	public void setCodigoCliente(String codigoCliente) {
		this.codigoCliente = codigoCliente;
	}
	/**
	 * @return the clienteCorporativo
	 */
	public Integer getClienteCorporativo() {
		return clienteCorporativo;
	}
	/**
	 * @param clienteCorporativo the clienteCorporativo to set
	 */
	public void setClienteCorporativo(Integer clienteCorporativo) {
		this.clienteCorporativo = clienteCorporativo;
	}
	/**
	 * @return the razonSocial
	 */
	public String getRazonSocial() {
		return RazonSocial;
	}
	/**
	 * @param razonSocial the razonSocial to set
	 */
	public void setRazonSocial(String razonSocial) {
		RazonSocial = razonSocial;
	}
	/**
	 * @return the fechaIngreso
	 */
	public Date getFechaIngreso() {
		return fechaIngreso;
	}
	/**
	 * @param fechaIngreso the fechaIngreso to set
	 */
	public void setFechaIngreso(Date fechaIngreso) {
		this.fechaIngreso = fechaIngreso;
	}
	/**
	 * @return the tipoDocumentoIdentidadID
	 */
	public Integer getTipoDocumentoIdentidadID() {
		return tipoDocumentoIdentidadID;
	}
	/**
	 * @param tipoDocumentoIdentidadID the tipoDocumentoIdentidadID to set
	 */
	public void setTipoDocumentoIdentidadID(Integer tipoDocumentoIdentidadID) {
		this.tipoDocumentoIdentidadID = tipoDocumentoIdentidadID;
	}
	/**
	 * @return the numeroDocumentoSunat
	 */
	public String getNumeroDocumentoSunat() {
		return numeroDocumentoSunat;
	}
	/**
	 * @param numeroDocumentoSunat the numeroDocumentoSunat to set
	 */
	public void setNumeroDocumentoSunat(String numeroDocumentoSunat) {
		this.numeroDocumentoSunat = numeroDocumentoSunat;
	}
	/**
	 * @return the rubroID
	 */
	public Integer getRubroID() {
		return rubroID;
	}
	/**
	 * @param rubroID the rubroID to set
	 */
	public void setRubroID(Integer rubroID) {
		this.rubroID = rubroID;
	}

	/**
	 * @return the tipoCredito
	 */
	public String getTipoCredito() {
		return tipoCredito;
	}
	/**
	 * @param tipoCredito the tipoCredito to set
	 */
	public void setTipoCredito(String tipoCredito) {
		this.tipoCredito = tipoCredito;
	}
	/**
	 * @return the origen
	 */
	public Integer getOrigen() {
		return origen;
	}
	/**
	 * @param origen the origen to set
	 */
	public void setOrigen(Integer origen) {
		this.origen = origen;
	}
	/**
	 * @return the estadoRegistro
	 */
	public Integer getEstadoRegistro() {
		return estadoRegistro;
	}
	/**
	 * @param estadoRegistro the estadoRegistro to set
	 */
	public void setEstadoRegistro(Integer estadoRegistro) {
		this.estadoRegistro = estadoRegistro;
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
	public String getFechaRegistro() {
		return fechaRegistro;
	}
	/**
	 * @param fechaRegistro the fechaRegistro to set
	 */
	public void setFechaRegistro(String fechaRegistro) {
		this.fechaRegistro = fechaRegistro;
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
	 * @return the clasificacionPersonaID
	 */
	public Integer getClasificacionPersonaID() {
		return clasificacionPersonaID;
	}
	/**
	 * @param clasificacionPersonaID the clasificacionPersonaID to set
	 */
	public void setClasificacionPersonaID(Integer clasificacionPersonaID) {
		this.clasificacionPersonaID = clasificacionPersonaID;
	}


}
