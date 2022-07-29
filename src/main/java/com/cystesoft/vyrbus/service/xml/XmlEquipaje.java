/**
 * Proyecto		: SISVYR
 * Sistema		: Sistema de Ventas y Reservas
 * Descripción	: 
 * Autor		: José Abanto
 * Fecha		: 24 abr. 2022
 * Hora			: 01:20:51
 */
package com.cystesoft.vyrbus.service.xml;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlType;

/**
 * @author abant
 *
 */
@XmlType
public class XmlEquipaje implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String v1_numero;
	private String v2_destino;
	private String v3_servicio;
	private String v4_numeroBoleto;
	private String v5_fechaSalida;
	private String v6_horaSalida;
	private String v7_puntoEmbarque;
	private String v8_puntoDesembarque;
	private String z_CodigoBarra;
	private String z_ticket;
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
	 * @return the v2_destino
	 */
	public String getV2_destino() {
		return v2_destino;
	}
	/**
	 * @param v2_destino the v2_destino to set
	 */
	public void setV2_destino(String v2_destino) {
		this.v2_destino = v2_destino;
	}
	/**
	 * @return the v3_servicio
	 */
	public String getV3_servicio() {
		return v3_servicio;
	}
	/**
	 * @param v3_servicio the v3_servicio to set
	 */
	public void setV3_servicio(String v3_servicio) {
		this.v3_servicio = v3_servicio;
	}
	/**
	 * @return the v4_numeroBoleto
	 */
	public String getV4_numeroBoleto() {
		return v4_numeroBoleto;
	}
	/**
	 * @param v4_numeroBoleto the v4_numeroBoleto to set
	 */
	public void setV4_numeroBoleto(String v4_numeroBoleto) {
		this.v4_numeroBoleto = v4_numeroBoleto;
	}
	/**
	 * @return the v5_fechaSalida
	 */
	public String getV5_fechaSalida() {
		return v5_fechaSalida;
	}
	/**
	 * @param v5_fechaSalida the v5_fechaSalida to set
	 */
	public void setV5_fechaSalida(String v5_fechaSalida) {
		this.v5_fechaSalida = v5_fechaSalida;
	}
	/**
	 * @return the v6_horaSalida
	 */
	public String getV6_horaSalida() {
		return v6_horaSalida;
	}
	/**
	 * @param v6_horaSalida the v6_horaSalida to set
	 */
	public void setV6_horaSalida(String v6_horaSalida) {
		this.v6_horaSalida = v6_horaSalida;
	}
	/**
	 * @return the v7_puntoEmbarque
	 */
	public String getV7_puntoEmbarque() {
		return v7_puntoEmbarque;
	}
	/**
	 * @param v7_puntoEmbarque the v7_puntoEmbarque to set
	 */
	public void setV7_puntoEmbarque(String v7_puntoEmbarque) {
		this.v7_puntoEmbarque = v7_puntoEmbarque;
	}
	/**
	 * @return the v8_puntoDesembarque
	 */
	public String getV8_puntoDesembarque() {
		return v8_puntoDesembarque;
	}
	/**
	 * @param v8_puntoDesembarque the v8_puntoDesembarque to set
	 */
	public void setV8_puntoDesembarque(String v8_puntoDesembarque) {
		this.v8_puntoDesembarque = v8_puntoDesembarque;
	}
	/**
	 * @return the z_CodigoBarra
	 */
	public String getZ_CodigoBarra() {
		return z_CodigoBarra;
	}
	/**
	 * @param z_CodigoBarra the z_CodigoBarra to set
	 */
	public void setZ_CodigoBarra(String z_CodigoBarra) {
		this.z_CodigoBarra = z_CodigoBarra;
	}
	/**
	 * @return the z_ticket
	 */
	public String getZ_ticket() {
		return z_ticket;
	}
	/**
	 * @param z_ticket the z_ticket to set
	 */
	public void setZ_ticket(String z_ticket) {
		this.z_ticket = z_ticket;
	}
	
}
