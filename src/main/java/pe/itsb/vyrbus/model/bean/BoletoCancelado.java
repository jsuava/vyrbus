package pe.itsb.vyrbus.model.bean;

import java.util.Date;


/**
 * BoletoCancelado entity. @author MyEclipse Persistence Tools
 */

public class BoletoCancelado extends GenericBean implements java.io.Serializable {
	private static final long serialVersionUID = 1L;
	private Long id;
    private Long ventaPasaje;
    private String numeroDocumentoCliente;
    private String numeroComprobanteBanco;
    private String codigoBanco;
    private Date fechaOperacion;
    private Date fechaEmision;
    private Double importePagado;
    private Long rucClienteCredito;
    private Long cliente;


    // Constructors

    /** default constructor */
    public BoletoCancelado() {
    }


    // Property accessors

    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }

    public Long getVentaPasaje() {
        return this.ventaPasaje;
    }
    public void setVentaPasaje(Long ventaPasaje) {
        this.ventaPasaje = ventaPasaje;
    }

    public String getNumeroDocumentoCliente() {
        return this.numeroDocumentoCliente;
    }
    public void setNumeroDocumentoCliente(String numeroDocumentoCliente) {
        this.numeroDocumentoCliente = numeroDocumentoCliente;
    }

    public String getNumeroComprobanteBanco() {
        return this.numeroComprobanteBanco;
    }
    public void setNumeroComprobanteBanco(String numeroComprobanteBanco) {
        this.numeroComprobanteBanco = numeroComprobanteBanco;
    }

    public String getCodigoBanco() {
        return this.codigoBanco;
    }
    public void setCodigoBanco(String codigoBanco) {
        this.codigoBanco = codigoBanco;
    }

    public Date getFechaOperacion() {
        return this.fechaOperacion;
    }
    public void setFechaOperacion(Date fechaOperacion) {
        this.fechaOperacion = fechaOperacion;
    }

    public Date getFechaEmision() {
        return this.fechaEmision;
    }
    public void setFechaEmision(Date fechaEmision) {
        this.fechaEmision = fechaEmision;
    }

    public Double getImportePagado() {
        return this.importePagado;
    }
    public void setImportePagado(Double importePagado) {
        this.importePagado = importePagado;
    }

    public Long getRucClienteCredito() {
        return this.rucClienteCredito;
    }
    public void setRucClienteCredito(Long rucClienteCredito) {
        this.rucClienteCredito = rucClienteCredito;
    }

    public Long getCliente() {
        return this.cliente;
    }

    public void setCliente(Long cliente) {
        this.cliente = cliente;
    }
}