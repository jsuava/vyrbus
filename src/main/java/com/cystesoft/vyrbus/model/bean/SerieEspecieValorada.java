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
public class SerieEspecieValorada extends GenericBean{
	private static final long serialVersionUID = 1L;

	private TipoComprobante tipoComprobante;
	private Agencia agencia;
	private String numeroSerie;
	private SerieEspecieValoradaID serieEspecieValorada_ID;
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
	/**
	 * @return the serieEspecieValorada_ID
	 */
	public SerieEspecieValoradaID getSerieEspecieValorada_ID() {
		return serieEspecieValorada_ID;
	}
	/**
	 * @param serieEspecieValorada_ID the serieEspecieValorada_ID to set
	 */
	public void setSerieEspecieValorada_ID(SerieEspecieValoradaID serieEspecieValorada_ID) {
		this.serieEspecieValorada_ID = serieEspecieValorada_ID;
	}

}
