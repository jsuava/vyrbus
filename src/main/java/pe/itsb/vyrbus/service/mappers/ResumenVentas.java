/**
 * Proyecto		: SISVYR
 * Sistema		: Sistema de Ventas y Reservas
 * Descripci�n	:
 * Autor		: Jos� Abanto
 * Fecha		: 30 may. 2021
 * Hora			: 10:35:46
 */
package pe.itsb.vyrbus.service.mappers;

import java.io.Serializable;
import java.util.Date;

import pe.itsb.vyrbus.model.bean.Agencia;
import pe.itsb.vyrbus.model.bean.CanalVenta;
import pe.itsb.vyrbus.model.bean.TipoComprobante;

/**
 * @author Marco
 *
 */
public class ResumenVentas implements Serializable{

	private static final long serialVersionUID = 1L;

	private Integer rubro;
	private CanalVenta canalVenta;
	private Agencia agencia;
	private TipoComprobante tipoComprobante;
	private Integer cantidad;
	private Double total;
	private String fechaVenta;
	private Date fechaEmision;
	private String anio;
	private String mes;
	private String dia;

	/**
	 * @return the rubro
	 */
	public Integer getRubro() {
		return rubro;
	}
	/**
	 * @param rubro the rubro to set
	 */
	public void setRubro(Integer rubro) {
		this.rubro = rubro;
	}
	/**
	 * @return the canalVenta
	 */
	public CanalVenta getCanalVenta() {
		return canalVenta;
	}
	/**
	 * @param canalVenta the canalVenta to set
	 */
	public void setCanalVenta(CanalVenta canalVenta) {
		this.canalVenta = canalVenta;
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
	 * @return the tipoComprobante
	 */
	public TipoComprobante getTipoComprobante() {
		return tipoComprobante;
	}
	/**
	 * @param tipoComprobante the tipoComprobante to set
	 */
	public void setTipoComprobante(TipoComprobante tipoComprobante) {
		this.tipoComprobante = tipoComprobante;
	}
	/**
	 * @return the cantidad
	 */
	public Integer getCantidad() {
		return cantidad;
	}
	/**
	 * @param cantidad the cantidad to set
	 */
	public void setCantidad(Integer cantidad) {
		this.cantidad = cantidad;
	}
	/**
	 * @return the total
	 */
	public Double getTotal() {
		return total;
	}
	/**
	 * @param total the total to set
	 */
	public void setTotal(Double total) {
		this.total = total;
	}
	/**
	 * @return the fechaVenta
	 */
	public String getFechaVenta() {
		return fechaVenta;
	}
	/**
	 * @param fechaVenta the fechaVenta to set
	 */
	public void setFechaVenta(String fechaVenta) {
		this.fechaVenta = fechaVenta;
	}

	public Date getFechaEmision() {
		return fechaEmision;
	}
	public void setFechaEmision(Date fechaEmision) {
		this.fechaEmision = fechaEmision;
	}
	/**
	 * @return the anio
	 */
	public String getAnio() {
		return anio;
	}
	/**
	 * @param anio the anio to set
	 */
	public void setAnio(String anio) {
		this.anio = anio;
	}
	/**
	 * @return the mes
	 */
	public String getMes() {
		return mes;
	}
	/**
	 * @param mes the mes to set
	 */
	public void setMes(String mes) {
		this.mes = mes;
	}
	/**
	 * @return the dia
	 */
	public String getDia() {
		return dia;
	}
	/**
	 * @param dia the dia to set
	 */
	public void setDia(String dia) {
		this.dia = dia;
	}


}
