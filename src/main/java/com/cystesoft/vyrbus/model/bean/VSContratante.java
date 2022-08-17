package com.cystesoft.vyrbus.model.bean;

import java.util.Date;

/**
 *
 * @author JABANTO
 *
 */
public class VSContratante extends GenericBean  implements java.io.Serializable {
	private static final long serialVersionUID = 1L;

    // Fields
	private Long id;
     private VSSexo vsSexo;
     private VSTipoRegistro vsTipoRegistro;
     private VSEstadoCivil vsEstadoCivil;
     private VSTipoDocumento vsTipoDocumento;
     private VSTipoPersona vsTipoPersona;
     private String numeroDocumento;
     private String apellidoPaterno;
     private String apellidoMaterno;
     private String nombre;
     private Date fechaNacimiento;
     private String direccion;
     private String ubigeoDepartamento;
     private String ubigeoProvincia;
     private String ubigeoDistrito;
     private String telefono;
     private String celular;
     private String razonSocial;
     private String telefonoComercial;

    // Constructors
    /** default constructor */
    public VSContratante() {
    }

    // Property accessors
    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }


    public String getNumeroDocumento() {
        return this.numeroDocumento;
    }

    public void setNumeroDocumento(String numeroDocumento) {
        this.numeroDocumento = numeroDocumento;
    }

    public String getApellidoPaterno() {
        return this.apellidoPaterno;
    }

    public void setApellidoPaterno(String apellidoPaterno) {
        this.apellidoPaterno = apellidoPaterno;
    }

    public String getApellidoMaterno() {
        return this.apellidoMaterno;
    }

    public void setApellidoMaterno(String apellidoMaterno) {
        this.apellidoMaterno = apellidoMaterno;
    }

    public String getNombre() {
        return this.nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Date getFechaNacimiento() {
        return this.fechaNacimiento;
    }

    public void setFechaNacimiento(Date fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public String getDireccion() {
        return this.direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getUbigeoDepartamento() {
        return this.ubigeoDepartamento;
    }

    public void setUbigeoDepartamento(String ubigeoDepartamento) {
        this.ubigeoDepartamento = ubigeoDepartamento;
    }

    public String getUbigeoProvincia() {
        return this.ubigeoProvincia;
    }

    public void setUbigeoProvincia(String ubigeoProvincia) {
        this.ubigeoProvincia = ubigeoProvincia;
    }

    public String getUbigeoDistrito() {
        return this.ubigeoDistrito;
    }

    public void setUbigeoDistrito(String ubigeoDistrito) {
        this.ubigeoDistrito = ubigeoDistrito;
    }

    public String getTelefono() {
        return this.telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getCelular() {
        return this.celular;
    }

    public void setCelular(String celular) {
        this.celular = celular;
    }

    public String getRazonSocial() {
        return this.razonSocial;
    }

    public void setRazonSocial(String razonSocial) {
        this.razonSocial = razonSocial;
    }

    public String getTelefonoComercial() {
        return this.telefonoComercial;
    }

    public void setTelefonoComercial(String telefonoComercial) {
        this.telefonoComercial = telefonoComercial;
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
	 * @return the vsTipoRegistro
	 */
	public VSTipoRegistro getVsTipoRegistro() {
		return vsTipoRegistro;
	}

	/**
	 * @param vsTipoRegistro the vsTipoRegistro to set
	 */
	public void setVsTipoRegistro(VSTipoRegistro vsTipoRegistro) {
		this.vsTipoRegistro = vsTipoRegistro;
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
	 * @return the vsTipoPersona
	 */
	public VSTipoPersona getVsTipoPersona() {
		return vsTipoPersona;
	}

	/**
	 * @param vsTipoPersona the vsTipoPersona to set
	 */
	public void setVsTipoPersona(VSTipoPersona vsTipoPersona) {
		this.vsTipoPersona = vsTipoPersona;
	}

}