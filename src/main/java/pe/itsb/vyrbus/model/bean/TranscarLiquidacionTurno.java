/**
 * Proyecto		: SISVYR
 * Sistema		: Sistema de Ventas y Reservas
 * Descripción	:
 * Autor		: José Abanto
 * Fecha		: 27 oct. 2021
 * Hora			: 11:48:01
 */
package pe.itsb.vyrbus.model.bean;

import java.util.Date;

/**
 * @author abant
 *
 */
public class TranscarLiquidacionTurno extends GenericBean{

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	private Long id;
	private Integer operacion;
	private Date fechaApertura;
	private TranscarUsuarioPersonal transcarUsuarioPersonal;
	private Integer agenciaId;

	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}
	/**
	 * @return the operacion
	 */
	public Integer getOperacion() {
		return operacion;
	}
	/**
	 * @param operacion the operacion to set
	 */
	public void setOperacion(Integer operacion) {
		this.operacion = operacion;
	}
	/**
	 * @return the fechaApertura
	 */
	public Date getFechaApertura() {
		return fechaApertura;
	}
	/**
	 * @param fechaApertura the fechaApertura to set
	 */
	public void setFechaApertura(Date fechaApertura) {
		this.fechaApertura = fechaApertura;
	}
	/**
	 * @return the transcarUsuarioPersonal
	 */
	public TranscarUsuarioPersonal getTranscarUsuarioPersonal() {
		return transcarUsuarioPersonal;
	}
	/**
	 * @param transcarUsuarioPersonal the transcarUsuarioPersonal to set
	 */
	public void setTranscarUsuarioPersonal(TranscarUsuarioPersonal transcarUsuarioPersonal) {
		this.transcarUsuarioPersonal = transcarUsuarioPersonal;
	}
	/**
	 * @return the agenciaId
	 */
	public Integer getAgenciaId() {
		return agenciaId;
	}
	/**
	 * @param agenciaId the agenciaId to set
	 */
	public void setAgenciaId(Integer agenciaId) {
		this.agenciaId = agenciaId;
	}


}
