/**
 * Proyecto		: SISVYR
 * Sistema		: Sistema de Ventas y Reservas
 * Descripción	:
 * Autor		: José Abanto
 * Fecha		: 07/10/2016
 * Hora			: 17:29:44
 */
package com.cystesoft.vyrbus.service.xml;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlType;

/**
 * @author jabanto
 *
 */
@XmlType
public class XmlCliente implements Serializable{
	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	private String v1_Ruc;
	private String v2_RazonSozial;
	private String v3_DireccionLegal;
	/**
	 * @return the v1_Ruc
	 */
	public String getV1_Ruc() {
		return v1_Ruc;
	}
	/**
	 * @param v1_Ruc the v1_Ruc to set
	 */
	public void setV1_Ruc(String v1_Ruc) {
		this.v1_Ruc = v1_Ruc;
	}
	/**
	 * @return the v2_RazonSozial
	 */
	public String getV2_RazonSozial() {
		return v2_RazonSozial;
	}
	/**
	 * @param v2_RazonSozial the v2_RazonSozial to set
	 */
	public void setV2_RazonSozial(String v2_RazonSozial) {
		this.v2_RazonSozial = v2_RazonSozial;
	}
	/**
	 * @return the v3_DireccionLegal
	 */
	public String getV3_DireccionLegal() {
		return v3_DireccionLegal;
	}
	/**
	 * @param v3_DireccionLegal the v3_DireccionLegal to set
	 */
	public void setV3_DireccionLegal(String v3_DireccionLegal) {
		this.v3_DireccionLegal = v3_DireccionLegal;
	}

}
