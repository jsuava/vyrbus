/**
 * Proyecto		: SISVYR
 * Sistema		: Sistema de Ventas y Reservas
 * Descripción	:
 * Autor		: José Abanto
 * Fecha		: 14 set. 2021
 * Hora			: 14:50:09
 */
package com.cystesoft.vyrbus.service.xml;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlType;

/**
 * @author abant
 *
 */
@XmlType
public class XmlItemOtroIngresoLiquidacion implements Serializable{

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	private String v1_concepto;
	private String v2_detalle;
	private String v3_cantidad;
	private String v4_monto;
	/**
	 * @return the v1_concepto
	 */
	public String getV1_concepto() {
		return v1_concepto;
	}
	/**
	 * @param v1_concepto the v1_concepto to set
	 */
	public void setV1_concepto(String v1_concepto) {
		this.v1_concepto = v1_concepto;
	}
	/**
	 * @return the v2_detalle
	 */
	public String getV2_detalle() {
		return v2_detalle;
	}
	/**
	 * @param v2_detalle the v2_detalle to set
	 */
	public void setV2_detalle(String v2_detalle) {
		this.v2_detalle = v2_detalle;
	}
	/**
	 * @return the v3_cantidad
	 */
	public String getV3_cantidad() {
		return v3_cantidad;
	}
	/**
	 * @param v3_cantidad the v3_cantidad to set
	 */
	public void setV3_cantidad(String v3_cantidad) {
		this.v3_cantidad = v3_cantidad;
	}
	/**
	 * @return the v4_monto
	 */
	public String getV4_monto() {
		return v4_monto;
	}
	/**
	 * @param v4_monto the v4_monto to set
	 */
	public void setV4_monto(String v4_monto) {
		this.v4_monto = v4_monto;
	}
}
