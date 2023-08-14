/**
 * Proyecto		: SISVYR
 * Sistema		: Sistema de Ventas y Reservas
 * Descripción	:
 * Autor		: José Abanto
 * Fecha		: 19/01/2017
 * Hora			: 11:17:04
 */
package pe.itsb.vyrbus.service.xml;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlType;

/**
 * @author Jose Abanto
 *
 */
@XmlType
public class XmlConfigPrint implements Serializable{

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	private String v1_EsTuentrada;
	private String v2_NombreImpresora;
	private String v3_Puerto;
	private String v4_BistBySegundo;
	private String v5_BistDatos;
	private String v6_Paridad;
	private String v7_BistParada;
	private String v8_NumeroCopias;
	/**
	 * @return the v1_EsTuentrada
	 */
	public String getV1_EsTuentrada() {
		return v1_EsTuentrada;
	}
	/**
	 * @param v1_EsTuentrada the v1_EsTuentrada to set
	 */
	public void setV1_EsTuentrada(String v1_EsTuentrada) {
		this.v1_EsTuentrada = v1_EsTuentrada;
	}
	/**
	 * @return the v2_NombreImpresora
	 */
	public String getV2_NombreImpresora() {
		return v2_NombreImpresora;
	}
	/**
	 * @param v2_NombreImpresora the v2_NombreImpresora to set
	 */
	public void setV2_NombreImpresora(String v2_NombreImpresora) {
		this.v2_NombreImpresora = v2_NombreImpresora;
	}
	/**
	 * @return the v3_Puerto
	 */
	public String getV3_Puerto() {
		return v3_Puerto;
	}
	/**
	 * @param v3_Puerto the v3_Puerto to set
	 */
	public void setV3_Puerto(String v3_Puerto) {
		this.v3_Puerto = v3_Puerto;
	}
	/**
	 * @return the v4_BistBySegundo
	 */
	public String getV4_BistBySegundo() {
		return v4_BistBySegundo;
	}
	/**
	 * @param v4_BistBySegundo the v4_BistBySegundo to set
	 */
	public void setV4_BistBySegundo(String v4_BistBySegundo) {
		this.v4_BistBySegundo = v4_BistBySegundo;
	}
	/**
	 * @return the v5_BistDatos
	 */
	public String getV5_BistDatos() {
		return v5_BistDatos;
	}
	/**
	 * @param v5_BistDatos the v5_BistDatos to set
	 */
	public void setV5_BistDatos(String v5_BistDatos) {
		this.v5_BistDatos = v5_BistDatos;
	}
	/**
	 * @return the v6_Paridad
	 */
	public String getV6_Paridad() {
		return v6_Paridad;
	}
	/**
	 * @param v6_Paridad the v6_Paridad to set
	 */
	public void setV6_Paridad(String v6_Paridad) {
		this.v6_Paridad = v6_Paridad;
	}
	/**
	 * @return the v7_BistParada
	 */
	public String getV7_BistParada() {
		return v7_BistParada;
	}
	/**
	 * @param v7_BistParada the v7_BistParada to set
	 */
	public void setV7_BistParada(String v7_BistParada) {
		this.v7_BistParada = v7_BistParada;
	}
	/**
	 * @return the v8_NumeroCopias
	 */
	public String getV8_NumeroCopias() {
		return v8_NumeroCopias;
	}
	/**
	 * @param v8_NumeroCopias the v8_NumeroCopias to set
	 */
	public void setV8_NumeroCopias(String v8_NumeroCopias) {
		this.v8_NumeroCopias = v8_NumeroCopias;
	}
}
