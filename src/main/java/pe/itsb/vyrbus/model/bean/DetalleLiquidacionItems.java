/**
 * Proyecto		: SISVYR
 * Sistema		: Sistema de Ventas y Reservas
 * Descripción	:
 * Autor		: José Abanto
 * Fecha		: 14 set. 2021
 * Hora			: 15:26:03
 */
package pe.itsb.vyrbus.model.bean;

import java.io.Serializable;

/**
 * @author abant
 *
 */
public class DetalleLiquidacionItems implements Serializable{

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	private String concepto;
	private String subConcepto;
	private String detalle;
	private Integer cantidad;
	private Double monto;
	/**
	 * @return the concepto
	 */
	public String getConcepto() {
		return concepto;
	}
	/**
	 * @param concepto the concepto to set
	 */
	public void setConcepto(String concepto) {
		this.concepto = concepto;
	}
	/**
	 * @return the subConcepto
	 */
	public String getSubConcepto() {
		return subConcepto;
	}
	/**
	 * @param subConcepto the subConcepto to set
	 */
	public void setSubConcepto(String subConcepto) {
		this.subConcepto = subConcepto;
	}
	/**
	 * @return the detalle
	 */
	public String getDetalle() {
		return detalle;
	}
	/**
	 * @param detalle the detalle to set
	 */
	public void setDetalle(String detalle) {
		this.detalle = detalle;
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
	 * @return the monto
	 */
	public Double getMonto() {
		return monto;
	}
	/**
	 * @param monto the monto to set
	 */
	public void setMonto(Double monto) {
		this.monto = monto;
	}

}
