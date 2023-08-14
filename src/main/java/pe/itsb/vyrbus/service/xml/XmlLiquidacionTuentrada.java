/**
 * Proyecto		: SISVYR
 * Sistema		: Sistema de Ventas y Reservas
 * Descripción	:
 * Autor		: José Abanto
 * Fecha		: 19/01/2017
 * Hora			: 17:29:26
 */
package pe.itsb.vyrbus.service.xml;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlType;

/**
 * @author Jose Abanto
 *
 */
@XmlType
public class XmlLiquidacionTuentrada implements Serializable{

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	private String v1_UsuarioNombres;
	private String v2_UsuarioLogin;
	private String v3_CantidadEfectivo;
	private String v4_MontoEfectivo;
	private String v5_CantidadTarjeta;
	private String v6_MontoTarjeta;
	private String v7_FechaOperacion;
	/**
	 * @return the v1_UsuarioNombres
	 */
	public String getV1_UsuarioNombres() {
		return v1_UsuarioNombres;
	}
	/**
	 * @param v1_UsuarioNombres the v1_UsuarioNombres to set
	 */
	public void setV1_UsuarioNombres(String v1_UsuarioNombres) {
		this.v1_UsuarioNombres = v1_UsuarioNombres;
	}
	/**
	 * @return the v2_UsuarioLogin
	 */
	public String getV2_UsuarioLogin() {
		return v2_UsuarioLogin;
	}
	/**
	 * @param v2_UsuarioLogin the v2_UsuarioLogin to set
	 */
	public void setV2_UsuarioLogin(String v2_UsuarioLogin) {
		this.v2_UsuarioLogin = v2_UsuarioLogin;
	}
	/**
	 * @return the v3_CantidadEfectivo
	 */
	public String getV3_CantidadEfectivo() {
		return v3_CantidadEfectivo;
	}
	/**
	 * @param v3_CantidadEfectivo the v3_CantidadEfectivo to set
	 */
	public void setV3_CantidadEfectivo(String v3_CantidadEfectivo) {
		this.v3_CantidadEfectivo = v3_CantidadEfectivo;
	}
	/**
	 * @return the v4_MontoEfectivo
	 */
	public String getV4_MontoEfectivo() {
		return v4_MontoEfectivo;
	}
	/**
	 * @param v4_MontoEfectivo the v4_MontoEfectivo to set
	 */
	public void setV4_MontoEfectivo(String v4_MontoEfectivo) {
		this.v4_MontoEfectivo = v4_MontoEfectivo;
	}
	/**
	 * @return the v5_CantidadTarjeta
	 */
	public String getV5_CantidadTarjeta() {
		return v5_CantidadTarjeta;
	}
	/**
	 * @param v5_CantidadTarjeta the v5_CantidadTarjeta to set
	 */
	public void setV5_CantidadTarjeta(String v5_CantidadTarjeta) {
		this.v5_CantidadTarjeta = v5_CantidadTarjeta;
	}
	/**
	 * @return the v6_MontoTarjeta
	 */
	public String getV6_MontoTarjeta() {
		return v6_MontoTarjeta;
	}
	/**
	 * @param v6_MontoTarjeta the v6_MontoTarjeta to set
	 */
	public void setV6_MontoTarjeta(String v6_MontoTarjeta) {
		this.v6_MontoTarjeta = v6_MontoTarjeta;
	}
	/**
	 * @return the v7_FechaOperacion
	 */
	public String getV7_FechaOperacion() {
		return v7_FechaOperacion;
	}
	/**
	 * @param v7_FechaOperacion the v7_FechaOperacion to set
	 */
	public void setV7_FechaOperacion(String v7_FechaOperacion) {
		this.v7_FechaOperacion = v7_FechaOperacion;
	}

}
