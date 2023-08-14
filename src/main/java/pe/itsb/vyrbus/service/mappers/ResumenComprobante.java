/**
 * Proyecto		: SISVYR
 * Sistema		: Sistema de Ventas y Reservas
 * Descripción	:
 * Autor		: José Abanto
 * Fecha		: 15 jul. 2021
 * Hora			: 23:34:16
 */
package pe.itsb.vyrbus.service.mappers;

import java.io.Serializable;

/**
 * @author Jose
 *
 */
public class ResumenComprobante implements Serializable {
	private static final long serialVersionUID = 1L;

	private Integer idTipoComprobante;
	private String comprobante;
	private String serie;
	private Integer cantidad;
	private Double monto;
	/**
	 * @return the idTipoComprobante
	 */
	public Integer getIdTipoComprobante() {
		return idTipoComprobante;
	}
	/**
	 * @param idTipoComprobante the idTipoComprobante to set
	 */
	public void setIdTipoComprobante(Integer idTipoComprobante) {
		this.idTipoComprobante = idTipoComprobante;
	}
	/**
	 * @return the comprobante
	 */
	public String getComprobante() {
		return comprobante;
	}
	/**
	 * @param comprobante the comprobante to set
	 */
	public void setComprobante(String comprobante) {
		this.comprobante = comprobante;
	}
	/**
	 * @return the serie
	 */
	public String getSerie() {
		return serie;
	}
	/**
	 * @param serie the serie to set
	 */
	public void setSerie(String serie) {
		this.serie = serie;
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

}
