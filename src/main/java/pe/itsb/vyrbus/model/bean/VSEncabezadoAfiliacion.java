package pe.itsb.vyrbus.model.bean;
// default package

/**
 *
 * @author JABANTO
 *
 */
public class VSEncabezadoAfiliacion extends GenericBean  implements java.io.Serializable {
	private static final long serialVersionUID = 1L;

    // Fields
	private Long id;
	 private VSMoneda vsMoneda;
	 private String codigoPatrocinador;
	 private String numeroRamo;
	 private String codigoProducto;
	 private String numeroPoliza;
	 private String tipoDeclaracion;
	 private String fechaProceso;
	 private String fechaInicio;
	 private String fechaFin;
	 private String cantidadRegistros;
	 private String cantidadMovimientos;
	 private String monto;

    // Constructors
    /** default constructor */
    public VSEncabezadoAfiliacion() {
    }


    // Property accessors
    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCodigoPatrocinador() {
        return this.codigoPatrocinador;
    }

    public void setCodigoPatrocinador(String codigoPatrocinador) {
        this.codigoPatrocinador = codigoPatrocinador;
    }

    public String getNumeroRamo() {
        return this.numeroRamo;
    }

    public void setNumeroRamo(String numeroRamo) {
        this.numeroRamo = numeroRamo;
    }

    public String getCodigoProducto() {
        return this.codigoProducto;
    }

    public void setCodigoProducto(String codigoProducto) {
        this.codigoProducto = codigoProducto;
    }

    public String getNumeroPoliza() {
        return this.numeroPoliza;
    }

    public void setNumeroPoliza(String numeroPoliza) {
        this.numeroPoliza = numeroPoliza;
    }

    public String getTipoDeclaracion() {
        return this.tipoDeclaracion;
    }

    public void setTipoDeclaracion(String tipoDeclaracion) {
        this.tipoDeclaracion = tipoDeclaracion;
    }

    public String getFechaProceso() {
        return this.fechaProceso;
    }

    public void setFechaProceso(String fechaProceso) {
        this.fechaProceso = fechaProceso;
    }

    public String getFechaInicio() {
        return this.fechaInicio;
    }

    public void setFechaInicio(String fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public String getFechaFin() {
        return this.fechaFin;
    }

    public void setFechaFin(String fechaFin) {
        this.fechaFin = fechaFin;
    }

    public String getCantidadRegistros() {
        return this.cantidadRegistros;
    }

    public void setCantidadRegistros(String cantidadRegistros) {
        this.cantidadRegistros = cantidadRegistros;
    }

    public String getCantidadMovimientos() {
        return this.cantidadMovimientos;
    }

    public void setCantidadMovimientos(String cantidadMovimientos) {
        this.cantidadMovimientos = cantidadMovimientos;
    }

    public String getMonto() {
        return this.monto;
    }

    public void setMonto(String monto) {
        this.monto = monto;
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

}