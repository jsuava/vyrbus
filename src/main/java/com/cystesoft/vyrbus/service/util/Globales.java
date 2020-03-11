/**
 * Proyecto		: SISVYR
 * Sistema		: Sistema de Ventas y Reservas
 * Descripción	: 
 * Autor		: José Sullo Avalos
 * Fecha		: 08/01/2013
 */
package com.cystesoft.vyrbus.service.util;

import java.util.Date;

import com.cystesoft.vyrbus.model.bean.Agencia;
import com.cystesoft.vyrbus.model.bean.CanalVenta;
import com.cystesoft.vyrbus.model.bean.TipoComprobante;
import com.cystesoft.vyrbus.model.bean.Usuario;
import com.cystesoft.vyrbus.model.bean.UsuarioHardware;

/**
 * @author Jose
 *
 */
public class Globales {
	private Usuario usuario;
	private UsuarioHardware usuarioHardware;
	private CanalVenta canalVenta;
	private Agencia agencia;
	private TipoComprobante tipoComprobante;
	private Date fechaLiquidacion;
	
	/**
	 * @return the usuario
	 */
	public Usuario getUsuario() {
		return usuario;
	}
	/**
	 * @param usuario the usuario to set
	 */
	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}
	
	/**
	 * @return the usuarioHardware
	 */
	public UsuarioHardware getUsuarioHardware() {
		return usuarioHardware;
	}
	/**
	 * @param usuarioHardware the usuarioHardware to set
	 */
	public void setUsuarioHardware(UsuarioHardware usuarioHardware) {
		this.usuarioHardware = usuarioHardware;
	}
	
	/**
	 * @return the canalVenta
	 */
	public CanalVenta getCanalVenta() {
		return canalVenta;
	}
	/**
	 * @param canalVenta the canalVenta to set
	 */
	public void setCanalVenta(CanalVenta canalVenta) {
		this.canalVenta = canalVenta;
	}
	
	/**
	 * @return the agencia
	 */
	public Agencia getAgencia() {
		return agencia;
	}
	/**
	 * @param agencia the agencia to set
	 */
	public void setAgencia(Agencia agencia) {
		this.agencia = agencia;
	}
	
	/**
	 * @return the tipoComprobante
	 */
	public TipoComprobante getTipoComprobante() {
		return tipoComprobante;
	}
	/**
	 * @param tipoComprobante the tipoComprobante to set
	 */
	public void setTipoComprobante(TipoComprobante tipoComprobante) {
		this.tipoComprobante = tipoComprobante;
	}
	
	/**
	 * @return the fechaLiquidacion
	 */
	public Date getFechaLiquidacion() {
		return fechaLiquidacion;
	}
	/**
	 * @param fechaLiquidacion the fechaLiquidacion to set
	 */
	public void setFechaLiquidacion(Date fechaLiquidacion) {
		this.fechaLiquidacion = fechaLiquidacion;
	}
}
