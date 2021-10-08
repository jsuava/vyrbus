/**
 * Proyecto		: SISVYR
 * Sistema		: Sistema de Ventas y Reservas
 * Descripción	: 
 * Autor		: José Abanto
 * Fecha		: 14 set. 2021
 * Hora			: 14:49:39
 */
package com.cystesoft.vyrbus.service.xml;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlType;

/**
 * @author abant
 *
 */
@XmlType
public class XmlItemIngresoVentaLiquidacion implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
		
	private String v1_comprobante;
	private String v2_serie;
	private String v3_detalle;
	private String v4_cantidad;
	private String v5_monto;
	/**
	 * @return the v1_comprobante
	 */
	public String getV1_comprobante() {
		return v1_comprobante;
	}
	/**
	 * @param v1_comprobante the v1_comprobante to set
	 */
	public void setV1_comprobante(String v1_comprobante) {
		this.v1_comprobante = v1_comprobante;
	}
	/**
	 * @return the v2_serie
	 */
	public String getV2_serie() {
		return v2_serie;
	}
	/**
	 * @param v2_serie the v2_serie to set
	 */
	public void setV2_serie(String v2_serie) {
		this.v2_serie = v2_serie;
	}
	/**
	 * @return the v3_detalle
	 */
	public String getV3_detalle() {
		return v3_detalle;
	}
	/**
	 * @param v3_detalle the v3_detalle to set
	 */
	public void setV3_detalle(String v3_detalle) {
		this.v3_detalle = v3_detalle;
	}
	/**
	 * @return the v4_cantidad
	 */
	public String getV4_cantidad() {
		return v4_cantidad;
	}
	/**
	 * @param v4_cantidad the v4_cantidad to set
	 */
	public void setV4_cantidad(String v4_cantidad) {
		this.v4_cantidad = v4_cantidad;
	}
	/**
	 * @return the v5_monto
	 */
	public String getV5_monto() {
		return v5_monto;
	}
	/**
	 * @param v5_monto the v5_monto to set
	 */
	public void setV5_monto(String v5_monto) {
		this.v5_monto = v5_monto;
	}
	

}
