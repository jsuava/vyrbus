/**
 * Proyecto		: SISVYR
 * Sistema		: Sistema de Ventas y Reservas
 * Descripción	:
 * Autor		: José Abanto
 * Fecha		: 07/10/2016
 * Hora			: 17:29:28
 */
package com.cystesoft.vyrbus.service.xml;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlType;

/**
 * @author jabanto
 *
 */
@XmlType
public class XmlPasajero implements Serializable{
	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	private String v1_TipoDocumento;
	private String v2_NumeroDocumento;
	private String v3_NombresApellidos;
	/**
	 * @return the v1_TipoDocumento
	 */
	public String getV1_TipoDocumento() {
		return v1_TipoDocumento;
	}
	/**
	 * @param v1_TipoDocumento the v1_TipoDocumento to set
	 */
	public void setV1_TipoDocumento(String v1_TipoDocumento) {
		this.v1_TipoDocumento = v1_TipoDocumento;
	}
	/**
	 * @return the v2_NumeroDocumento
	 */
	public String getV2_NumeroDocumento() {
		return v2_NumeroDocumento;
	}
	/**
	 * @param v2_NumeroDocumento the v2_NumeroDocumento to set
	 */
	public void setV2_NumeroDocumento(String v2_NumeroDocumento) {
		this.v2_NumeroDocumento = v2_NumeroDocumento;
	}
	/**
	 * @return the v3_NombresApellidos
	 */
	public String getV3_NombresApellidos() {
		return v3_NombresApellidos;
	}
	/**
	 * @param v3_NombresApellidos the v3_NombresApellidos to set
	 */
	public void setV3_NombresApellidos(String v3_NombresApellidos) {
		this.v3_NombresApellidos = v3_NombresApellidos;
	}


}
