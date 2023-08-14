/**
 * Proyecto		: SISVYR
 * Sistema		: Sistema de Ventas y Reservas
 * Descripción	:
 * Autor		: José Sullo Avalos
 * Fecha		: 03/04/2014
 */
package pe.itsb.vyrbus.model.bean;

import java.util.Date;

/**
 * @author JABANTO
 *
 */
public class TarifaFechaAbierta extends GenericBean {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	private long id;
	private Ruta ruta;
	private Servicio servicio;
	private Double monto;
	private Date fechaActivacion;
	private Date fechaCaducidad;
	private Date fechaSuspencion;
	/**
	 * @return the id
	 */
	public long getId() {
		return id;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(long id) {
		this.id = id;
	}
	/**
	 * @return the ruta
	 */
	public Ruta getRuta() {
		return ruta;
	}
	/**
	 * @param ruta the ruta to set
	 */
	public void setRuta(Ruta ruta) {
		this.ruta = ruta;
	}
	/**
	 * @return the servicio
	 */
	public Servicio getServicio() {
		return servicio;
	}
	/**
	 * @param servicio the servicio to set
	 */
	public void setServicio(Servicio servicio) {
		this.servicio = servicio;
	}
	/**
	 * @return the monto
	 */
	public Double getMonto() {
		return monto;
	}
	/**
	 * @param monto the monto to set
	 */
	public void setMonto(Double monto) {
		this.monto = monto;
	}
	/**
	 * @return the fechaActivacion
	 */
	public Date getFechaActivacion() {
		return fechaActivacion;
	}
	/**
	 * @param fechaActivacion the fechaActivacion to set
	 */
	public void setFechaActivacion(Date fechaActivacion) {
		this.fechaActivacion = fechaActivacion;
	}
	/**
	 * @return the fechaCaducidad
	 */
	public Date getFechaCaducidad() {
		return fechaCaducidad;
	}
	/**
	 * @param fechaCaducidad the fechaCaducidad to set
	 */
	public void setFechaCaducidad(Date fechaCaducidad) {
		this.fechaCaducidad = fechaCaducidad;
	}
	/**
	 * @return the fechaSuspencion
	 */
	public Date getFechaSuspencion() {
		return fechaSuspencion;
	}
	/**
	 * @param fechaSuspencion the fechaSuspencion to set
	 */
	public void setFechaSuspencion(Date fechaSuspencion) {
		this.fechaSuspencion = fechaSuspencion;
	}

}
