/**
 * Proyecto		: SISVYR
 * Sistema		: Sistema de Ventas y Reservas
 * Descripción	:
 * Autor		: José Sullo Avalos
 * Fecha		: 20/11/2013
 */
package com.cystesoft.vyrbus.model.bean;

/**
 * @author JABANTO
 *
 */
public class SerieEspecieValoradaID extends GenericBean {
	private static final long serialVersionUID = 1L;

	private Integer idTipoComprobante;
	private String numeroSerie;
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
	 * @return the numeroSerie
	 */
	public String getNumeroSerie() {
		return numeroSerie;
	}
	/**
	 * @param numeroSerie the numeroSerie to set
	 */
	public void setNumeroSerie(String numeroSerie) {
		this.numeroSerie = numeroSerie;
	}



}
