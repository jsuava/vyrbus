package com.cystesoft.vyrbus.model.bean;
// default package



/**
 *
 * @author JABANTO
 *
 */
public class VSEstructuraDeclaracion extends GenericBean  implements java.io.Serializable {
	private static final long serialVersionUID = 1L;

    // Fields
	private Long id;
     private VSMoneda vsMoneda;
     private VSTipoRegistro  vsTipoRegistro;
     private VSTipoProceso vsTipoProceso;
     private VSEncabezadoAfiliacion vsEncabezadoAfiliacion;
     private VSPais vsPais;
     private VSCiudad vsCiudad;
     private String fechaProceso;
     private String codigoSucursal;
     private String codigoVendedor;
     private Long numeroDeclaracion;
     private String fechaVigenciaInicial;
     private String fechaVigenciaFinal;
     private String formaPago;
     private String codigoPlan;
     private String fechaTipoCambio;
     private String montoPrimaAnualNeta;
     private String montoPrimaAnualBruta;
     private String montoPrimaMensual;
     private String motivo;
     private String numeroCertificado;


    // Constructors
    /** default constructor */
    public VSEstructuraDeclaracion() {
    }

    // Property accessors
    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFechaProceso() {
        return this.fechaProceso;
    }

    public void setFechaProceso(String fechaProceso) {
        this.fechaProceso = fechaProceso;
    }

    public String getCodigoSucursal() {
        return this.codigoSucursal;
    }

    public void setCodigoSucursal(String codigoSucursal) {
        this.codigoSucursal = codigoSucursal;
    }

    public String getCodigoVendedor() {
        return this.codigoVendedor;
    }

    public void setCodigoVendedor(String codigoVendedor) {
        this.codigoVendedor = codigoVendedor;
    }

    public Long getNumeroDeclaracion() {
        return this.numeroDeclaracion;
    }

    public void setNumeroDeclaracion(Long numeroDeclaracion) {
        this.numeroDeclaracion = numeroDeclaracion;
    }

    public String getFechaVigenciaInicial() {
        return this.fechaVigenciaInicial;
    }

    public void setFechaVigenciaInicial(String fechaVigenciaInicial) {
        this.fechaVigenciaInicial = fechaVigenciaInicial;
    }

    public String getFechaVigenciaFinal() {
        return this.fechaVigenciaFinal;
    }

    public void setFechaVigenciaFinal(String fechaVigenciaFinal) {
        this.fechaVigenciaFinal = fechaVigenciaFinal;
    }

    public String getFormaPago() {
        return this.formaPago;
    }

    public void setFormaPago(String formaPago) {
        this.formaPago = formaPago;
    }

    public String getCodigoPlan() {
        return this.codigoPlan;
    }

    public void setCodigoPlan(String codigoPlan) {
        this.codigoPlan = codigoPlan;
    }

    public String getFechaTipoCambio() {
        return this.fechaTipoCambio;
    }

    public void setFechaTipoCambio(String fechaTipoCambio) {
        this.fechaTipoCambio = fechaTipoCambio;
    }

    public String getMontoPrimaAnualNeta() {
        return this.montoPrimaAnualNeta;
    }

    public void setMontoPrimaAnualNeta(String montoPrimaAnualNeta) {
        this.montoPrimaAnualNeta = montoPrimaAnualNeta;
    }

    public String getMontoPrimaAnualBruta() {
        return this.montoPrimaAnualBruta;
    }

    public void setMontoPrimaAnualBruta(String montoPrimaAnualBruta) {
        this.montoPrimaAnualBruta = montoPrimaAnualBruta;
    }

    public String getMontoPrimaMensual() {
        return this.montoPrimaMensual;
    }

    public void setMontoPrimaMensual(String montoPrimaMensual) {
        this.montoPrimaMensual = montoPrimaMensual;
    }

    public String getMotivo() {
        return this.motivo;
    }

    public void setMotivo(String motivo) {
        this.motivo = motivo;
    }

    public String getNumeroCertificado() {
        return this.numeroCertificado;
    }

    public void setNumeroCertificado(String numeroCertificado) {
        this.numeroCertificado = numeroCertificado;
    }

	/**
	 * @return the vsMoneda
	 */
	public VSMoneda getVsMoneda() {
		return vsMoneda;
	}

	/**
	 * @param vsMoneda the vsMoneda to set
	 */
	public void setVsMoneda(VSMoneda vsMoneda) {
		this.vsMoneda = vsMoneda;
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
	 * @return the vsTipoProceso
	 */
	public VSTipoProceso getVsTipoProceso() {
		return vsTipoProceso;
	}

	/**
	 * @param vsTipoProceso the vsTipoProceso to set
	 */
	public void setVsTipoProceso(VSTipoProceso vsTipoProceso) {
		this.vsTipoProceso = vsTipoProceso;
	}

	/**
	 * @return the vsEncabezadoAfiliacion
	 */
	public VSEncabezadoAfiliacion getVsEncabezadoAfiliacion() {
		return vsEncabezadoAfiliacion;
	}

	/**
	 * @param vsEncabezadoAfiliacion the vsEncabezadoAfiliacion to set
	 */
	public void setVsEncabezadoAfiliacion(VSEncabezadoAfiliacion vsEncabezadoAfiliacion) {
		this.vsEncabezadoAfiliacion = vsEncabezadoAfiliacion;
	}

	/**
	 * @return the vsPais
	 */
	public VSPais getVsPais() {
		return vsPais;
	}

	/**
	 * @param vsPais the vsPais to set
	 */
	public void setVsPais(VSPais vsPais) {
		this.vsPais = vsPais;
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


}