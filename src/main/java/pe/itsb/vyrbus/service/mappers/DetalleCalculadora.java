/**
 * Proyecto		: SISVYR
 * Sistema		: Sistema de Ventas y Reservas
 * Descripción	:
 * Autor		: José Avalos
 * Fecha		: 13/04/2013
 */
package pe.itsb.vyrbus.service.mappers;

import java.io.Serializable;

/**
 * @author Jose
 *
 */
public class DetalleCalculadora implements Serializable, Cloneable {
	private static final long serialVersionUID = 1L;
	private String denominacion;
	private Double tarifa;
	private Double descuento;

	/**
	 * @return the denominacion
	 */
	public String getDenominacion() {
		return denominacion;
	}
	/**
	 * @param denominacion the denominacion to set
	 */
	public void setDenominacion(String denominacion) {
		this.denominacion = denominacion;
	}

	/**
	 * @return the tarifa
	 */
	public Double getTarifa() {
		return tarifa;
	}
	/**
	 * @param tarifa the tarifa to set
	 */
	public void setTarifa(Double tarifa) {
		this.tarifa = tarifa;
	}

	/**
	 * @return the descuento
	 */
	public Double getDescuento() {
		return descuento;
	}
	/**
	 * @param descuento the descuento to set
	 */
	public void setDescuento(Double descuento) {
		this.descuento = descuento;
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#clone()
	 */
	@Override
	public Object clone() throws CloneNotSupportedException {
		// TODO Auto-generated method stub
		return super.clone();
	}
}
