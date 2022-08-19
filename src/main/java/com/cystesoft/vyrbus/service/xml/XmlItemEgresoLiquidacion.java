/**
 * Proyecto		: SISVYR
 * Sistema		: Sistema de Ventas y Reservas
 * Descripción	:
 * Autor		: José Abanto
 * Fecha		: 14 set. 2021
 * Hora			: 14:50:33
 */
package com.cystesoft.vyrbus.service.xml;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlType;

/**
 * @author abant
 *
 */
@XmlType
public class XmlItemEgresoLiquidacion implements Serializable{

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	private String v1_numero;
	private String v2_tipo;
	private String v3_detalle;
	private String v4_cantidad;
	private String v5_monto;


	public XmlItemEgresoLiquidacion() {
		super();
	}

	public XmlItemEgresoLiquidacion(String v1_numero, String v2_tipo, String v3_detalle, String v4_cantidad, String v5_monto) {
		this.v1_numero = v1_numero;
		this.v2_tipo = v2_tipo;
		this.v3_detalle  = v3_detalle;
		this.v4_cantidad = v4_cantidad;
		this.v5_monto = v5_monto;
	}

	/**
	 * @return the v1_numero
	 */
	public String getV1_numero() {
		return v1_numero;
	}
	/**
	 * @param v1_numero the v1_numero to set
	 */
	public void setV1_numero(String v1_numero) {
		this.v1_numero = v1_numero;
	}
	/**
	 * @return the v2_tipo
	 */
	public String getV2_tipo() {
		return v2_tipo;
	}
	/**
	 * @param v2_tipo the v2_tipo to set
	 */
	public void setV2_tipo(String v2_tipo) {
		this.v2_tipo = v2_tipo;
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
