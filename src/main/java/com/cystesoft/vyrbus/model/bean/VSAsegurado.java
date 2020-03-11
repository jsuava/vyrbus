/**
 * Proyecto		: SISVYR
 * Sistema		: Sistema de Ventas y Reservas
 * Descripción	: 
 * Fecha		: 24/06/2014
 */
package com.cystesoft.vyrbus.model.bean;

import java.io.Serializable;
import java.util.Date;


/**
 * @author JABANTO
 *
 */
public class VSAsegurado extends GenericBean implements Serializable {
private static final long serialVersionUID = 1L;
	
	 private Long id;
     private VSSexo vsSexo;
     private VSEstadoCivil vsEstadoCivil;
     private VSTipoDocumento vsTipoDocumento;
	 private String numeroDocumento;
     private String apellidoPaterno;
     private String apellidoMaterno;
     private String nombres;
     private Date fechaNacimiento;
     private String direccion;
     private String ubigeoDepartamento;
     private String ubigeoProvincia;
     private String ubigeoDistrito;
     private String telefono;
     private String celular;
     private String urbanizacion;
     private Integer nacionalidadID;
     private String email;
     
     private Ubigeo ubigeo;
     private String tipoPasajero;		//No mapeado -- Indica si es un pasajero normal o frecuente
     
     

    // Constructors

    /** default constructor */
    public VSAsegurado() {
    }

    // Property accessors
    public Long getId() {
        return this.id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }

	


	/**
	 * @return the numeroDocumento
	 */
	public String getNumeroDocumento() {
		return numeroDocumento;
	}

	/**
	 * @param numeroDocumento the numeroDocumento to set
	 */
	public void setNumeroDocumento(String numeroDocumento) {
		this.numeroDocumento = numeroDocumento;
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
	 * @return the fechaNacimiento
	 */
	public Date getFechaNacimiento() {
		return fechaNacimiento;
	}

	/**
	 * @param fechaNacimiento the fechaNacimiento to set
	 */
	public void setFechaNacimiento(Date fechaNacimiento) {
		this.fechaNacimiento = fechaNacimiento;
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
	 * @return the ubigeoDepartamento
	 */
	public String getUbigeoDepartamento() {
		return ubigeoDepartamento;
	}

	/**
	 * @param ubigeoDepartamento the ubigeoDepartamento to set
	 */
	public void setUbigeoDepartamento(String ubigeoDepartamento) {
		this.ubigeoDepartamento = ubigeoDepartamento;
	}

	/**
	 * @return the ubigeoProvincia
	 */
	public String getUbigeoProvincia() {
		return ubigeoProvincia;
	}

	/**
	 * @param ubigeoProvincia the ubigeoProvincia to set
	 */
	public void setUbigeoProvincia(String ubigeoProvincia) {
		this.ubigeoProvincia = ubigeoProvincia;
	}

	/**
	 * @return the ubigeoDistrito
	 */
	public String getUbigeoDistrito() {
		return ubigeoDistrito;
	}

	/**
	 * @param ubigeoDistrito the ubigeoDistrito to set
	 */
	public void setUbigeoDistrito(String ubigeoDistrito) {
		this.ubigeoDistrito = ubigeoDistrito;
	}

	/**
	 * @return the telefono
	 */
	public String getTelefono() {
		return telefono;
	}

	/**
	 * @param telefono the telefono to set
	 */
	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}

	/**
	 * @return the celular
	 */
	public String getCelular() {
		return celular;
	}

	/**
	 * @param celular the celular to set
	 */
	public void setCelular(String celular) {
		this.celular = celular;
	}

	/**
	 * @return the tipoPasajero
	 */
	public String getTipoPasajero() {
		return tipoPasajero;
	}

	/**
	 * @param tipoPasajero the tipoPasajero to set
	 */
	public void setTipoPasajero(String tipoPasajero) {
		this.tipoPasajero = tipoPasajero;
	}

	
	/**
	 * @return the vsEstadoCivil
	 */
	public VSEstadoCivil getVsEstadoCivil() {
		return vsEstadoCivil;
	}

	/**
	 * @param vsEstadoCivil the vsEstadoCivil to set
	 */
	public void setVsEstadoCivil(VSEstadoCivil vsEstadoCivil) {
		this.vsEstadoCivil = vsEstadoCivil;
	}

	/**
	 * @return the vsTipoDocumento
	 */
	public VSTipoDocumento getVsTipoDocumento() {
		return vsTipoDocumento;
	}

	/**
	 * @param vsTipoDocumento the vsTipoDocumento to set
	 */
	public void setVsTipoDocumento(VSTipoDocumento vsTipoDocumento) {
		this.vsTipoDocumento = vsTipoDocumento;
	}

	/**
	 * @return the vsSexo
	 */
	public VSSexo getVsSexo() {
		return vsSexo;
	}

	/**
	 * @param vsSexo the vsSexo to set
	 */
	public void setVsSexo(VSSexo vsSexo) {
		this.vsSexo = vsSexo;
	}

	/**
	 * @return the ubigeo
	 */
	public Ubigeo getUbigeo() {
		return ubigeo;
	}

	/**
	 * @param ubigeo the ubigeo to set
	 */
	public void setUbigeo(Ubigeo ubigeo) {
		this.ubigeo = ubigeo;
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return (apellidoPaterno==null?" ":apellidoPaterno) + (apellidoMaterno==null?"":" " + apellidoMaterno) + ", " + (nombres==null?" ":nombres);
	}

	/**
	 * @return the urbanizacion
	 */
	public String getUrbanizacion() {
		return urbanizacion;
	}

	/**
	 * @param urbanizacion the urbanizacion to set
	 */
	public void setUrbanizacion(String urbanizacion) {
		this.urbanizacion = urbanizacion;
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
	 * @return the nacionalidadID
	 */
	public Integer getNacionalidadID() {
		return nacionalidadID;
	}

	/**
	 * @param nacionalidadID the nacionalidadID to set
	 */
	public void setNacionalidadID(Integer nacionalidadID) {
		this.nacionalidadID = nacionalidadID;
	}
    
}
