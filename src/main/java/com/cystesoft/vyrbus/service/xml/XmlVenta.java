/**
 * Proyecto		: SISVYR
 * Sistema		: Sistema de Ventas y Reservas
 * Descripción	:
 * Autor		: José Abanto
 * Fecha		: 24/10/2016
 * Hora			: 15:21:41
 */
package com.cystesoft.vyrbus.service.xml;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlType;

/**
 * @author jabanto
 *
 */
@XmlType
public class XmlVenta implements Serializable, Cloneable{
	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	private Boolean v0_ExcesoEquipaje;
	private String v0_ObserImport;
	private String v1_NumeroComprobante;
	private String v2_Origen;
	private String v3_Destino;
	private String v4_Embarque;
	private String v5_Desembarque;
	private String v6_FechaPartida;
	private String v7_HoraPartida;
	private String v8_Asiento;
	private String v90_Piso;
	private XmlPasajero v91_Pasajero;
	private XmlCliente v92_Cliente;
	private XmlDetalleVentaPasajes v93_DetalleVentaPasajes;
	private String v94_OpGratuita;
	private String v95_OpExonerada;
	private String v96_OpInafecta;
	private String v97_OpGravada;
	private String v98_Igv;
	private String v990_ImporteTotal;
	private String v991_ImporteTotalLetras;
	private String v992_PartPage4;
	private String v992_CentroCosto;
	private String v993_FechaEmision;
	private String v994_AgenciaEmison;
	private String v995_UsuarioEmision;
	private String z_CodigoBarraSunat;
	private String z_QR;
//	private String v997_CodigoBarraEmbarque;
	private String z_ticket;
	/**
	 * @return the v1_NumeroComprobante
	 */
	public String getV1_NumeroComprobante() {
		return v1_NumeroComprobante;
	}
	/**
	 * @param v1_NumeroComprobante the v1_NumeroComprobante to set
	 */
	public void setV1_NumeroComprobante(String v1_NumeroComprobante) {
		this.v1_NumeroComprobante = v1_NumeroComprobante;
	}
	/**
	 * @return the v2_Origen
	 */
	public String getV2_Origen() {
		return v2_Origen;
	}
	/**
	 * @param v2_Origen the v2_Origen to set
	 */
	public void setV2_Origen(String v2_Origen) {
		this.v2_Origen = v2_Origen;
	}
	/**
	 * @return the v3_Destino
	 */
	public String getV3_Destino() {
		return v3_Destino;
	}
	/**
	 * @param v3_Destino the v3_Destino to set
	 */
	public void setV3_Destino(String v3_Destino) {
		this.v3_Destino = v3_Destino;
	}
	/**
	 * @return the v4_Embarque
	 */
	public String getV4_Embarque() {
		return v4_Embarque;
	}
	/**
	 * @param v4_Embarque the v4_Embarque to set
	 */
	public void setV4_Embarque(String v4_Embarque) {
		this.v4_Embarque = v4_Embarque;
	}
	/**
	 * @return the v5_Desembarque
	 */
	public String getV5_Desembarque() {
		return v5_Desembarque;
	}
	/**
	 * @param v5_Desembarque the v5_Desembarque to set
	 */
	public void setV5_Desembarque(String v5_Desembarque) {
		this.v5_Desembarque = v5_Desembarque;
	}
	/**
	 * @return the v6_FechaPartida
	 */
	public String getV6_FechaPartida() {
		return v6_FechaPartida;
	}
	/**
	 * @param v6_FechaPartida the v6_FechaPartida to set
	 */
	public void setV6_FechaPartida(String v6_FechaPartida) {
		this.v6_FechaPartida = v6_FechaPartida;
	}
	/**
	 * @return the v7_HoraPartida
	 */
	public String getV7_HoraPartida() {
		return v7_HoraPartida;
	}
	/**
	 * @param v7_HoraPartida the v7_HoraPartida to set
	 */
	public void setV7_HoraPartida(String v7_HoraPartida) {
		this.v7_HoraPartida = v7_HoraPartida;
	}
	/**
	 * @return the v8_Asiento
	 */
	public String getV8_Asiento() {
		return v8_Asiento;
	}
	/**
	 * @param v8_Asiento the v8_Asiento to set
	 */
	public void setV8_Asiento(String v8_Asiento) {
		this.v8_Asiento = v8_Asiento;
	}
	/**
	 * @return the v9_Piso
	 */
	public String getV90_Piso() {
		return v90_Piso;
	}
	/**
	 * @param v9_Piso the v9_Piso to set
	 */
	public void setV90_Piso(String v90_Piso) {
		this.v90_Piso = v90_Piso;
	}
	/**
	 * @return the v91_Pasajero
	 */
	public XmlPasajero getV91_Pasajero() {
		return v91_Pasajero;
	}
	/**
	 * @param v91_Pasajero the v91_Pasajero to set
	 */
	public void setV91_Pasajero(XmlPasajero v91_Pasajero) {
		this.v91_Pasajero = v91_Pasajero;
	}
	/**
	 * @return the v92_Cliente
	 */
	public XmlCliente getV92_Cliente() {
		return v92_Cliente;
	}
	/**
	 * @param v92_Cliente the v92_Cliente to set
	 */
	public void setV92_Cliente(XmlCliente v92_Cliente) {
		this.v92_Cliente = v92_Cliente;
	}
	/**
	 * @return the v93_DetalleVentaPasajes
	 */
	public XmlDetalleVentaPasajes getV93_DetalleVentaPasajes() {
		return v93_DetalleVentaPasajes;
	}
	/**
	 * @param v93_DetalleVentaPasajes the v93_DetalleVentaPasajes to set
	 */
	public void setV93_DetalleVentaPasajes(XmlDetalleVentaPasajes v93_DetalleVentaPasajes) {
		this.v93_DetalleVentaPasajes = v93_DetalleVentaPasajes;
	}
	/**
	 * @return the v94_OpGratuita
	 */
	public String getV94_OpGratuita() {
		return v94_OpGratuita;
	}
	/**
	 * @param v94_OpGratuita the v94_OpGratuita to set
	 */
	public void setV94_OpGratuita(String v94_OpGratuita) {
		this.v94_OpGratuita = v94_OpGratuita;
	}
	/**
	 * @return the v95_OpExonerada
	 */
	public String getV95_OpExonerada() {
		return v95_OpExonerada;
	}
	/**
	 * @param v95_OpExonerada the v95_OpExonerada to set
	 */
	public void setV95_OpExonerada(String v95_OpExonerada) {
		this.v95_OpExonerada = v95_OpExonerada;
	}
	/**
	 * @return the v96_OpInafecta
	 */
	public String getV96_OpInafecta() {
		return v96_OpInafecta;
	}
	/**
	 * @param v96_OpInafecta the v96_OpInafecta to set
	 */
	public void setV96_OpInafecta(String v96_OpInafecta) {
		this.v96_OpInafecta = v96_OpInafecta;
	}
	/**
	 * @return the v97_OpGravada
	 */
	public String getV97_OpGravada() {
		return v97_OpGravada;
	}
	/**
	 * @param v97_OpGravada the v97_OpGravada to set
	 */
	public void setV97_OpGravada(String v97_OpGravada) {
		this.v97_OpGravada = v97_OpGravada;
	}
	/**
	 * @return the v98_Igv
	 */
	public String getV98_Igv() {
		return v98_Igv;
	}
	/**
	 * @param v98_Igv the v98_Igv to set
	 */
	public void setV98_Igv(String v98_Igv) {
		this.v98_Igv = v98_Igv;
	}
	/**
	 * @return the v99_ImporteTotal
	 */
	public String getV990_ImporteTotal() {
		return v990_ImporteTotal;
	}
	/**
	 * @param v99_ImporteTotal the v99_ImporteTotal to set
	 */
	public void setV990_ImporteTotal(String v990_ImporteTotal) {
		this.v990_ImporteTotal = v990_ImporteTotal;
	}
	/**
	 * @return the v991_ImporteTotalLetras
	 */
	public String getV991_ImporteTotalLetras() {
		return v991_ImporteTotalLetras;
	}
	/**
	 * @param v991_ImporteTotalLetras the v991_ImporteTotalLetras to set
	 */
	public void setV991_ImporteTotalLetras(String v991_ImporteTotalLetras) {
		this.v991_ImporteTotalLetras = v991_ImporteTotalLetras;
	}

	/**
	 * @return the v993_FechaEmision
	 */
	public String getV993_FechaEmision() {
		return v993_FechaEmision;
	}
	/**
	 * @param v993_FechaEmision the v993_FechaEmision to set
	 */
	public void setV993_FechaEmision(String v993_FechaEmision) {
		this.v993_FechaEmision = v993_FechaEmision;
	}
	/**
	 * @return the v994_AgenciaEmison
	 */
	public String getV994_AgenciaEmison() {
		return v994_AgenciaEmison;
	}
	/**
	 * @param v994_AgenciaEmison the v994_AgenciaEmison to set
	 */
	public void setV994_AgenciaEmison(String v994_AgenciaEmison) {
		this.v994_AgenciaEmison = v994_AgenciaEmison;
	}
	/**
	 * @return the v995_UsuarioEmision
	 */
	public String getV995_UsuarioEmision() {
		return v995_UsuarioEmision;
	}
	/**
	 * @param v995_UsuarioEmision the v995_UsuarioEmision to set
	 */
	public void setV995_UsuarioEmision(String v995_UsuarioEmision) {
		this.v995_UsuarioEmision = v995_UsuarioEmision;
	}

	/**
	 * @return the v0_ObserImport
	 */
	public String getV0_ObserImport() {
		return v0_ObserImport;
	}
	/**
	 * @param v0_ObserImport the v0_ObserImport to set
	 */
	public void setV0_ObserImport(String v0_ObserImport) {
		this.v0_ObserImport = v0_ObserImport;
	}
	/**
	 * @return the z_CodigoBarraSunat
	 */
	public String getZ_CodigoBarraSunat() {
		return z_CodigoBarraSunat;
	}
	/**
	 * @param z_CodigoBarraSunat the z_CodigoBarraSunat to set
	 */
	public void setZ_CodigoBarraSunat(String z_CodigoBarraSunat) {
		this.z_CodigoBarraSunat = z_CodigoBarraSunat;
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
	/**
	 * @return the v992_CentroCostro
	 */
	public String getV992_CentroCosto() {
		return v992_CentroCosto;
	}
	/**
	 * @param v992_CentroCostro the v992_CentroCostro to set
	 */
	public void setV992_CentroCosto(String v992_CentroCosto) {
		this.v992_CentroCosto = v992_CentroCosto;
	}
	/**
	 * @return the v992_PartPage4
	 */
	public String getV992_PartPage4() {
		return v992_PartPage4;
	}
	/**
	 * @param v992_PartPage4 the v992_PartPage4 to set
	 */
	public void setV992_PartPage4(String v992_PartPage4) {
		this.v992_PartPage4 = v992_PartPage4;
	}
	/**
	 * @return the v0_ExcesoEquipaje
	 */
	public Boolean getV0_ExcesoEquipaje() {
		return v0_ExcesoEquipaje;
	}
	/**
	 * @param v0_ExcesoEquipaje the v0_ExcesoEquipaje to set
	 */
	public void setV0_ExcesoEquipaje(Boolean v0_ExcesoEquipaje) {
		this.v0_ExcesoEquipaje = v0_ExcesoEquipaje;
	}
	/**
	 * @return the z_QR
	 */
	public String getZ_QR() {
		return z_QR;
	}
	/**
	 * @param z_QR the z_QR to set
	 */
	public void setZ_QR(String z_QR) {
		this.z_QR = z_QR;
	}
	
	/**
	 * 
	 */
	@Override
	public Object clone() throws CloneNotSupportedException {
		// TODO Auto-generated method stub
		return super.clone();
	}
}
