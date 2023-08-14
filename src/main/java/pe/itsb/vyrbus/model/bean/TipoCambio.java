/**
 * Proyecto		: SISVYR
 * Sistema		: Sistema de Ventas y Reservas
 * Descripción	:
 * Autor		: José Abanto
 * Fecha		: 07/08/2015
 * Hora			: 11:21:01
 */
package pe.itsb.vyrbus.model.bean;

import java.util.Date;

/**
 * @author jabanto
 *
 */
public class TipoCambio extends GenericBean{

	/**
	 *
	 */
	private static final long serialVersionUID = 6426376828281202467L;
	private Integer id;
	private Date fecha;
	private TipoMoneda tipoMoneda;
	private Double tipoCambio;

	private Double equivalenteMonedaLocal; //No mapeado.


	/**
	 * @return the id
	 */
	public Integer getId() {
		return id;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(Integer id) {
		this.id = id;
	}
	/**
	 * @return the fecha
	 */
	public Date getFecha() {
		return fecha;
	}
	/**
	 * @param fecha the fecha to set
	 */
	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}
	/**
	 * @return the tipoMoneda
	 */
	public TipoMoneda getTipoMoneda() {
		return tipoMoneda;
	}
	/**
	 * @param tipoMoneda the tipoMoneda to set
	 */
	public void setTipoMoneda(TipoMoneda tipoMoneda) {
		this.tipoMoneda = tipoMoneda;
	}
	/**
	 * @return the tipoCambio
	 */
	public Double getTipoCambio() {
		return tipoCambio;
	}
	/**
	 * @param tipoCambio the tipoCambio to set
	 */
	public void setTipoCambio(Double tipoCambio) {
		this.tipoCambio = tipoCambio;
	}
	/**
	 * @return the equivalenteMonedaLocal
	 */
	public Double getEquivalenteMonedaLocal() {
		return equivalenteMonedaLocal;
	}
	/**
	 * @param equivalenteMonedaLocal the equivalenteMonedaLocal to set
	 */
	public void setEquivalenteMonedaLocal(Double equivalenteMonedaLocal) {
		this.equivalenteMonedaLocal = equivalenteMonedaLocal;
	}




}
