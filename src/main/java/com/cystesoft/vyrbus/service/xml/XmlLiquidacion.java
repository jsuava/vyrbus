/**
 * Proyecto		: SISVYR
 * Sistema		: Sistema de Ventas y Reservas
 * Descripción	:
 * Autor		: José Abanto
 * Fecha		: 14 set. 2021
 * Hora			: 14:47:48
 */
package com.cystesoft.vyrbus.service.xml;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlType;

/**
 * @author abant
 *
 */
@XmlType
public class XmlLiquidacion implements Serializable{

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	private String v1_fecha;
	private String v2_oficina;
	private String v3_representanteVenta;
	private String v4_ingresoTotalEfectivo;
	private String v5_ingresoTotalCredito;
	private String v5_totalIngresos;
	private String v5_totalEgresos;
	private String v6_usuarioCierre;
	private String v7_fechaHoraCierre;
	private XmlDetalleLiquidacionIngresoVenta v8_detalleLiquidacionIngresoVenta;
	private XmlDetalleLiquidacionOtrosIngresos v90_detalleLiquidacionOtrosIngresos;
	private XmlDetalleLiquidacionEgresos v91_detalleLiquidacionEgresos;
	private String z_rptLiquidacion;

	/**
	 * @return the v1_fecha
	 */
	public String getV1_fecha() {
		return v1_fecha;
	}
	/**
	 * @param v1_fecha the v1_fecha to set
	 */
	public void setV1_fecha(String v1_fecha) {
		this.v1_fecha = v1_fecha;
	}
	/**
	 * @return the v2_oficina
	 */
	public String getV2_oficina() {
		return v2_oficina;
	}
	/**
	 * @param v2_oficina the v2_oficina to set
	 */
	public void setV2_oficina(String v2_oficina) {
		this.v2_oficina = v2_oficina;
	}
	/**
	 * @return the v3_representanteVenta
	 */
	public String getV3_representanteVenta() {
		return v3_representanteVenta;
	}
	/**
	 * @param v3_representanteVenta the v3_representanteVenta to set
	 */
	public void setV3_representanteVenta(String v3_representanteVenta) {
		this.v3_representanteVenta = v3_representanteVenta;
	}
	/**
	 * @return the v4_ingresoTotalEfectivo
	 */
	public String getV4_ingresoTotalEfectivo() {
		return v4_ingresoTotalEfectivo;
	}
	/**
	 * @param v4_ingresoTotalEfectivo the v4_ingresoTotalEfectivo to set
	 */
	public void setV4_ingresoTotalEfectivo(String v4_ingresoTotalEfectivo) {
		this.v4_ingresoTotalEfectivo = v4_ingresoTotalEfectivo;
	}
	/**
	 * @return the v5_ingresoTotalCredito
	 */
	public String getV5_ingresoTotalCredito() {
		return v5_ingresoTotalCredito;
	}
	/**
	 * @param v5_ingresoTotalCredito the v5_ingresoTotalCredito to set
	 */
	public void setV5_ingresoTotalCredito(String v5_ingresoTotalCredito) {
		this.v5_ingresoTotalCredito = v5_ingresoTotalCredito;
	}
	/**
	 * @return the v6_usuarioCierre
	 */
	public String getV6_usuarioCierre() {
		return v6_usuarioCierre;
	}
	/**
	 * @param v6_usuarioCierre the v6_usuarioCierre to set
	 */
	public void setV6_usuarioCierre(String v6_usuarioCierre) {
		this.v6_usuarioCierre = v6_usuarioCierre;
	}
	/**
	 * @return the v7_fechaHoraCierre
	 */
	public String getV7_fechaHoraCierre() {
		return v7_fechaHoraCierre;
	}
	/**
	 * @param v7_fechaHoraCierre the v7_fechaHoraCierre to set
	 */
	public void setV7_fechaHoraCierre(String v7_fechaHoraCierre) {
		this.v7_fechaHoraCierre = v7_fechaHoraCierre;
	}
	/**
	 * @return the v8_detalleLiquidacionIngresoVenta
	 */
	public XmlDetalleLiquidacionIngresoVenta getV8_detalleLiquidacionIngresoVenta() {
		return v8_detalleLiquidacionIngresoVenta;
	}
	/**
	 * @param v8_detalleLiquidacionIngresoVenta the v8_detalleLiquidacionIngresoVenta to set
	 */
	public void setV8_detalleLiquidacionIngresoVenta(XmlDetalleLiquidacionIngresoVenta v8_detalleLiquidacionIngresoVenta) {
		this.v8_detalleLiquidacionIngresoVenta = v8_detalleLiquidacionIngresoVenta;
	}
	/**
	 * @return the v90_detalleLiquidacionOtrosIngresos
	 */
	public XmlDetalleLiquidacionOtrosIngresos getV90_detalleLiquidacionOtrosIngresos() {
		return v90_detalleLiquidacionOtrosIngresos;
	}
	/**
	 * @param v90_detalleLiquidacionOtrosIngresos the v90_detalleLiquidacionOtrosIngresos to set
	 */
	public void setV90_detalleLiquidacionOtrosIngresos(XmlDetalleLiquidacionOtrosIngresos v90_detalleLiquidacionOtrosIngresos) {
		this.v90_detalleLiquidacionOtrosIngresos = v90_detalleLiquidacionOtrosIngresos;
	}
	/**
	 * @return the v91_detalleLiquidacionEgresos
	 */
	public XmlDetalleLiquidacionEgresos getV91_detalleLiquidacionEgresos() {
		return v91_detalleLiquidacionEgresos;
	}
	/**
	 * @param v91_detalleLiquidacionEgresos the v91_detalleLiquidacionEgresos to set
	 */
	public void setV91_detalleLiquidacionEgresos(XmlDetalleLiquidacionEgresos v91_detalleLiquidacionEgresos) {
		this.v91_detalleLiquidacionEgresos = v91_detalleLiquidacionEgresos;
	}
	/**
	 * @return the z_rptLiquidacion
	 */
	public String getZ_rptLiquidacion() {
		return z_rptLiquidacion;
	}
	/**
	 * @param z_rptLiquidacion the z_rptLiquidacion to set
	 */
	public void setZ_rptLiquidacion(String z_rptLiquidacion) {
		this.z_rptLiquidacion = z_rptLiquidacion;
	}
	/**
	 * @return the v5_totalIngresos
	 */
	public String getV5_totalIngresos() {
		return v5_totalIngresos;
	}
	/**
	 * @param v5_totalIngresos the v5_totalIngresos to set
	 */
	public void setV5_totalIngresos(String v5_totalIngresos) {
		this.v5_totalIngresos = v5_totalIngresos;
	}
	/**
	 * @return the v5_totalEgresos
	 */
	public String getV5_totalEgresos() {
		return v5_totalEgresos;
	}
	/**
	 * @param v5_totalEgresos the v5_totalEgresos to set
	 */
	public void setV5_totalEgresos(String v5_totalEgresos) {
		this.v5_totalEgresos = v5_totalEgresos;
	}


}
