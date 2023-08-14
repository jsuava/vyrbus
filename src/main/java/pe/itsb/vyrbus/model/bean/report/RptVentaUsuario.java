/**
 * Proyecto		: SISVYR
 * Sistema		: Sistema de Ventas y Reservas
 * Descripción	:
 * Autor		: José Abanto
 * Fecha		: 16/06/2015
 * Hora			: 12:37:49
 */
package pe.itsb.vyrbus.model.bean.report;

import java.io.Serializable;

/**
 * @author jabanto
 *
 */
public class RptVentaUsuario implements Serializable{

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	private Integer horaVenta;
	private Integer cantidad;
	private Double importe;
	/**
	 * @return the horaVenta
	 */
	public Integer getHoraVenta() {
		return horaVenta;
	}
	/**
	 * @param horaVenta the horaVenta to set
	 */
	public void setHoraVenta(Integer horaVenta) {
		this.horaVenta = horaVenta;
	}
	/**
	 * @return the cantidad
	 */
	public Integer getCantidad() {
		return cantidad;
	}
	/**
	 * @param cantidad the cantidad to set
	 */
	public void setCantidad(Integer cantidad) {
		this.cantidad = cantidad;
	}
	/**
	 * @return the importe
	 */
	public Double getImporte() {
		return importe;
	}
	/**
	 * @param importe the importe to set
	 */
	public void setImporte(Double importe) {
		this.importe = importe;
	}

	/*
	 * (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString(){
		return (horaVenta!=null?horaVenta.toString()+" ":"")+(cantidad!=null?cantidad.toString()+" ":"")+(importe!=null?importe.toString():"");
	}

}
