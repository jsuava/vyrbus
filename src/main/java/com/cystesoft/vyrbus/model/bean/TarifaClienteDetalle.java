/**
 * Proyecto		: SISVYR
 * Sistema		: Sistema de Ventas y Reservas
 * Descripción	: 
 * Autor		: José Abanto
 * Fecha		: 07/07/2016
 * Hora			: 11:11:05
 */
package com.cystesoft.vyrbus.model.bean;

/**
 * @author jabanto
 *
 */
public class TarifaClienteDetalle extends GenericBean{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Integer id;
	private TarifaCliente tarifaCliente;
	private Integer asientoInicio;
	private Integer asientoFin;
	private TipoAsiento tipoAsiento;
	private Double tarifa;
	/**
	 * @return the id
	 */
	public Integer getId() {
		return id;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(Integer id) {
		this.id = id;
	}
	/**
	 * @return the tarifaCliente
	 */
	public TarifaCliente getTarifaCliente() {
		return tarifaCliente;
	}
	/**
	 * @param tarifaCliente the tarifaCliente to set
	 */
	public void setTarifaCliente(TarifaCliente tarifaCliente) {
		this.tarifaCliente = tarifaCliente;
	}
	/**
	 * @return the asientoInicio
	 */
	public Integer getAsientoInicio() {
		return asientoInicio;
	}
	/**
	 * @param asientoInicio the asientoInicio to set
	 */
	public void setAsientoInicio(Integer asientoInicio) {
		this.asientoInicio = asientoInicio;
	}
	/**
	 * @return the asientoFin
	 */
	public Integer getAsientoFin() {
		return asientoFin;
	}
	/**
	 * @param asientoFin the asientoFin to set
	 */
	public void setAsientoFin(Integer asientoFin) {
		this.asientoFin = asientoFin;
	}
	
	/**
	 * @return the tarifa
	 */
	public Double getTarifa() {
		return tarifa;
	}
	/**
	 * @param tarifa the tarifa to set
	 */
	public void setTarifa(Double tarifa) {
		this.tarifa = tarifa;
	}
	/**
	 * @return the tipoAsiento
	 */
	public TipoAsiento getTipoAsiento() {
		return tipoAsiento;
	}
	/**
	 * @param tipoAsiento the tipoAsiento to set
	 */
	public void setTipoAsiento(TipoAsiento tipoAsiento) {
		this.tipoAsiento = tipoAsiento;
	}

}
