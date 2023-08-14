/**
 * Proyecto		: SISVYR
 * Sistema		: Sistema de Ventas y Reservas
 * Descripción	:
 * Autor		: José Abanto
 * Fecha		: 11/01/2017
 * Hora			: 09:27:45
 */
package pe.itsb.vyrbus.service.util;

import java.io.Serializable;
import java.util.List;

import pe.itsb.vyrbus.model.bean.VentaPasaje;

/**
 * @author Jose Abanto
 *
 */
public class VentasNotas implements Serializable{

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	private List<VentaPasaje> listVentas;
	private List<VentaPasaje> listNotasCredito;
	/**
	 * @return the listVentas
	 */
	public List<VentaPasaje> getListVentas() {
		return listVentas;
	}
	/**
	 * @param listVentas the listVentas to set
	 */
	public void setListVentas(List<VentaPasaje> listVentas) {
		this.listVentas = listVentas;
	}
	/**
	 * @return the listNotasCredito
	 */
	public List<VentaPasaje> getListNotasCredito() {
		return listNotasCredito;
	}
	/**
	 * @param listNotasCredito the listNotasCredito to set
	 */
	public void setListNotasCredito(List<VentaPasaje> listNotasCredito) {
		this.listNotasCredito = listNotasCredito;
	}
}
