package com.cystesoft.vyrbus.model.bean;

import java.util.Date;


/**
 * PuntosPasajeroFrecuente entity. @author MyEclipse Persistence Tools
 */
public class PuntosPasajeroFrecuente extends GenericBean implements java.io.Serializable {
	private static final long serialVersionUID = 1L;
	private Long id;
    private PasajeroFrecuente pasajeroFrecuente;
    private VentaPasaje ventaPasaje;
    private Integer puntosAcumulados;
    private Date fechaEmision;
    private Date fechaCaducidad;
    private Date fechaCanje;
    private Date fechaAnulacion;
    private VentaPasaje ventaPasajeCanje;


    private Integer totalPuntaje;	//No Mapeado
    private Integer saldoInicial;
    private Integer totalAsignado;
    private Integer totalCajeados;
    private Integer saldoActual;
    private Integer puntosCanjeados;

	// Constructors

    /** default constructor */
    public PuntosPasajeroFrecuente() {
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
	 * @return the pasajeroFrecuente
	 */
	public PasajeroFrecuente getPasajeroFrecuente() {
		return pasajeroFrecuente;
	}
	/**
	 * @param pasajeroFrecuente the pasajeroFrecuente to set
	 */
	public void setPasajeroFrecuente(PasajeroFrecuente pasajeroFrecuente) {
		this.pasajeroFrecuente = pasajeroFrecuente;
	}

	/**
	 * @return the ventaPasaje
	 */
	public VentaPasaje getVentaPasaje() {
		return ventaPasaje;
	}
	/**
	 * @param ventaPasaje the ventaPasaje to set
	 */
	public void setVentaPasaje(VentaPasaje ventaPasaje) {
		this.ventaPasaje = ventaPasaje;
	}

	/**
	 * @return the puntosAcumulados
	 */
	public Integer getPuntosAcumulados() {
		return puntosAcumulados;
	}
	/**
	 * @param puntosAcumulados the puntosAcumulados to set
	 */
	public void setPuntosAcumulados(Integer puntosAcumulados) {
		this.puntosAcumulados = puntosAcumulados;
	}

	/**
	 * @return the fechaEmision
	 */
	public Date getFechaEmision() {
		return fechaEmision;
	}
	/**
	 * @param fechaEmision the fechaEmision to set
	 */
	public void setFechaEmision(Date fechaEmision) {
		this.fechaEmision = fechaEmision;
	}

	/**
	 * @return the fechaCaducidad
	 */
	public Date getFechaCaducidad() {
		return fechaCaducidad;
	}
	/**
	 * @param fechaCaducidad the fechaCaducidad to set
	 */
	public void setFechaCaducidad(Date fechaCaducidad) {
		this.fechaCaducidad = fechaCaducidad;
	}

	/**
	 * @return the fechaCanje
	 */
	public Date getFechaCanje() {
		return fechaCanje;
	}
	/**
	 * @param fechaCanje the fechaCanje to set
	 */
	public void setFechaCanje(Date fechaCanje) {
		this.fechaCanje = fechaCanje;
	}

	/**
	 * @return the fechaAnulacion
	 */
	public Date getFechaAnulacion() {
		return fechaAnulacion;
	}
	/**
	 * @param fechaAnulacion the fechaAnulacion to set
	 */
	public void setFechaAnulacion(Date fechaAnulacion) {
		this.fechaAnulacion = fechaAnulacion;
	}

	/**
	 * @return the totalPuntaje
	 */
	public Integer getTotalPuntaje() {
		return totalPuntaje;
	}

	/**
	 * @param totalPuntaje the totalPuntaje to set
	 */
	public void setTotalPuntaje(Integer totalPuntaje) {
		this.totalPuntaje = totalPuntaje;
	}

	/**
	 * @return the saldoInicial
	 */
	public Integer getSaldoInicial() {
		return saldoInicial;
	}

	/**
	 * @param saldoInicial the saldoInicial to set
	 */
	public void setSaldoInicial(Integer saldoInicial) {
		this.saldoInicial = saldoInicial;
	}

	/**
	 * @return the totalAsignado
	 */
	public Integer getTotalAsignado() {
		return totalAsignado;
	}

	/**
	 * @param totalAsignado the totalAsignado to set
	 */
	public void setTotalAsignado(Integer totalAsignado) {
		this.totalAsignado = totalAsignado;
	}

	/**
	 * @return the totalCajeados
	 */
	public Integer getTotalCajeados() {
		return totalCajeados;
	}

	/**
	 * @param totalCajeados the totalCajeados to set
	 */
	public void setTotalCajeados(Integer totalCajeados) {
		this.totalCajeados = totalCajeados;
	}

	/**
	 * @return the saldoActual
	 */
	public Integer getSaldoActual() {
		return saldoActual;
	}

	/**
	 * @param saldoActual the saldoActual to set
	 */
	public void setSaldoActual(Integer saldoActual) {
		this.saldoActual = saldoActual;
	}

	/**
	 * @return the puntosCanjeados
	 */
	public Integer getPuntosCanjeados() {
		return puntosCanjeados;
	}

	/**
	 * @param puntosCanjeados the puntosCanjeados to set
	 */
	public void setPuntosCanjeados(Integer puntosCanjeados) {
		this.puntosCanjeados = puntosCanjeados;
	}

	/**
	 * @return the ventaPasajeCanje
	 */
	public VentaPasaje getVentaPasajeCanje() {
		return ventaPasajeCanje;
	}

	/**
	 * @param ventaPasajeCanje the ventaPasajeCanje to set
	 */
	public void setVentaPasajeCanje(VentaPasaje ventaPasajeCanje) {
		this.ventaPasajeCanje = ventaPasajeCanje;
	}
}
