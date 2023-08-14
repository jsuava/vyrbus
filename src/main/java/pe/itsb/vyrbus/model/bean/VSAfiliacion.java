package pe.itsb.vyrbus.model.bean;

import java.util.Date;
// default package

/**
 *
 * @author JABANTO
 *
 */
public class VSAfiliacion extends GenericBean implements java.io.Serializable {
	private static final long serialVersionUID = 1L;

	 private Long id;
     private VSTipoRegistro vsTipoRegistro;
     private VSLiquidacion vsLiquidacion;
     private VSTipoPersona vsTipoPersona;
     private VSAsegurado vsAsegurado;
     private Integer usuarioID;
     private Integer adicional;
     private String numeroCertificado;
     private Double importePagado;
     private Date fechaVigenciaInicial;
     private Date fechaVigenciaFinal;
     private Date fechaVenta;
     private Date fechaProcesoAfiliacion;
     private Date fechaProcesoPago;
     private VSCiudad vsCiudad;
     private String esPasajeroFrecuente;
     private Integer agenciaID;
     private String numeroBoleto;


     private Agencia agencia;	//No mapeado
     private Usuario usuario;	//No mapeado


    // Constructors
    /** default constructor */
    public VSAfiliacion() {

    }

    // Property accessors
    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getAdicional() {
        return this.adicional;
    }

    public void setAdicional(Integer adicional) {
        this.adicional = adicional;
    }

    public String getNumeroCertificado() {
        return this.numeroCertificado;
    }

    public void setNumeroCertificado(String numeroCertificado) {
        this.numeroCertificado = numeroCertificado;
    }

    public Double getImportePagado() {
        return this.importePagado;
    }

    public void setImportePagado(Double importePagado) {
        this.importePagado = importePagado;
    }

    public Date getFechaVigenciaInicial() {
        return this.fechaVigenciaInicial;
    }

    public void setFechaVigenciaInicial(Date fechaVigenciaInicial) {
        this.fechaVigenciaInicial = fechaVigenciaInicial;
    }

    public Date getFechaVigenciaFinal() {
        return this.fechaVigenciaFinal;
    }

    public void setFechaVigenciaFinal(Date fechaVigenciaFinal) {
        this.fechaVigenciaFinal = fechaVigenciaFinal;
    }

    public Date getFechaVenta() {
        return this.fechaVenta;
    }

    public void setFechaVenta(Date fechaVenta) {
        this.fechaVenta = fechaVenta;
    }

    public Date getFechaProcesoAfiliacion() {
        return this.fechaProcesoAfiliacion;
    }

    public void setFechaProcesoAfiliacion(Date fechaProcesoAfiliacion) {
        this.fechaProcesoAfiliacion = fechaProcesoAfiliacion;
    }

    public Date getFechaProcesoPago() {
        return this.fechaProcesoPago;
    }

    public void setFechaProcesoPago(Date fechaProcesoPago) {
        this.fechaProcesoPago = fechaProcesoPago;
    }
	/**
	 * @return the esPasajeroFrecuente
	 */
	public String getEsPasajeroFrecuente() {
		return esPasajeroFrecuente;
	}

	/**
	 * @param esPasajeroFrecuente the esPasajeroFrecuente to set
	 */
	public void setEsPasajeroFrecuente(String esPasajeroFrecuente) {
		this.esPasajeroFrecuente = esPasajeroFrecuente;
	}

	/**
	 * @return the vsCiudad
	 */
	public VSCiudad getVsCiudad() {
		return vsCiudad;
	}

	/**
	 * @param vsCiudad the vsCiudad to set
	 */
	public void setVsCiudad(VSCiudad vsCiudad) {
		this.vsCiudad = vsCiudad;
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

	/**
	 * @return the vsAsegurado
	 */
	public VSAsegurado getVsAsegurado() {
		return vsAsegurado;
	}

	/**
	 * @param vsAsegurado the vsAsegurado to set
	 */
	public void setVsAsegurado(VSAsegurado vsAsegurado) {
		this.vsAsegurado = vsAsegurado;
	}

	/**
	 * @return the vsLiquidacion
	 */
	public VSLiquidacion getVsLiquidacion() {
		return vsLiquidacion;
	}

	/**
	 * @param vsLiquidacion the vsLiquidacion to set
	 */
	public void setVsLiquidacion(VSLiquidacion vsLiquidacion) {
		this.vsLiquidacion = vsLiquidacion;
	}

	/**
	 * @return the usuarioID
	 */
	public Integer getUsuarioID() {
		return usuarioID;
	}

	/**
	 * @param usuarioID the usuarioID to set
	 */
	public void setUsuarioID(Integer usuarioID) {
		this.usuarioID = usuarioID;
	}

	/**
	 * @return the agenciaID
	 */
	public Integer getAgenciaID() {
		return agenciaID;
	}

	/**
	 * @param agenciaID the agenciaID to set
	 */
	public void setAgenciaID(Integer agenciaID) {
		this.agenciaID = agenciaID;
	}

	/**
	 * @return the numeroBoleto
	 */
	public String getNumeroBoleto() {
		return numeroBoleto;
	}

	/**
	 * @param numeroBoleto the numeroBoleto to set
	 */
	public void setNumeroBoleto(String numeroBoleto) {
		this.numeroBoleto = numeroBoleto;
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
	 * @return the usuario
	 */
	public Usuario getUsuario() {
		return usuario;
	}

	/**
	 * @param usuario the usuario to set
	 */
	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}
}