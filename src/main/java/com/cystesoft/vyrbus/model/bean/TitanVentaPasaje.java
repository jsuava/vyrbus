
package com.cystesoft.vyrbus.model.bean;

import java.util.Date;

/**
 * @author JABANTO
 *
 */
public class TitanVentaPasaje {
	private Long id;
	private String serie;
	private String numeroBoleto;
	private Integer idCondicionBoleto;
	private Date fechaTransferencia;
	private Integer idTarjetas;
	
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
	 * @return the serie
	 */
	public String getSerie() {
		return serie;
	}
	/**
	 * @param serie the serie to set
	 */
	public void setSerie(String serie) {
		this.serie = serie;
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
	 * @return the fechaTransferencia
	 */
	public Date getFechaTransferencia() {
		return fechaTransferencia;
	}
	/**
	 * @param fechaTransferencia the fechaTransferencia to set
	 */
	public void setFechaTransferencia(Date fechaTransferencia) {
		this.fechaTransferencia = fechaTransferencia;
	}
	/**
	 * @return the idCondicionBoleto
	 */
	public Integer getIdCondicionBoleto() {
		return idCondicionBoleto;
	}
	/**
	 * @param idCondicionBoleto the idCondicionBoleto to set
	 */
	public void setIdCondicionBoleto(Integer idCondicionBoleto) {
		this.idCondicionBoleto = idCondicionBoleto;
	}
	/**
	 * @return the idTarjetas
	 */
	public Integer getIdTarjetas() {
		return idTarjetas;
	}
	/**
	 * @param idTarjetas the idTarjetas to set
	 */
	public void setIdTarjetas(Integer idTarjetas) {
		this.idTarjetas = idTarjetas;
	}
	
	
}
