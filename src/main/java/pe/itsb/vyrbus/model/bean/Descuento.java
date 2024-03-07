/**
 * Proyecto		: SISVYR
 * Sistema		: Sistema de Ventas y Reservas
 * Descripción	: 
 * Autor		: José Abanto
 * Fecha		: 26 feb. 2024
 * Hora			: 18:46:41
 */
package pe.itsb.vyrbus.model.bean;

import java.util.Date;

/**
 * @author abant
 *
 */
public class Descuento extends GenericBean implements java.io.Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Long id;
	private AutorizadorDescuento autorizadorDescuento;
	private String codigo;
	private String tipoDescuento;
	private Double valorMinimo;
	private Double valorMaximo;
	private Date fechaInicio;
	private Date fechaFin;
	private String observaciones;
	private Integer idaVuelta;
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
	 * @return the autorizadorDescuento
	 */
	public AutorizadorDescuento getAutorizadorDescuento() {
		return autorizadorDescuento;
	}
	/**
	 * @param autorizadorDescuento the autorizadorDescuento to set
	 */
	public void setAutorizadorDescuento(AutorizadorDescuento autorizadorDescuento) {
		this.autorizadorDescuento = autorizadorDescuento;
	}
	/**
	 * @return the codigo
	 */
	public String getCodigo() {
		return codigo;
	}
	/**
	 * @param codigo the codigo to set
	 */
	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}
	/**
	 * @return the tipoDescuento
	 */
	public String getTipoDescuento() {
		return tipoDescuento;
	}
	/**
	 * @param tipoDescuento the tipoDescuento to set
	 */
	public void setTipoDescuento(String tipoDescuento) {
		this.tipoDescuento = tipoDescuento;
	}
	/**
	 * @return the fechaInicio
	 */
	public Date getFechaInicio() {
		return fechaInicio;
	}
	/**
	 * @param fechaInicio the fechaInicio to set
	 */
	public void setFechaInicio(Date fechaInicio) {
		this.fechaInicio = fechaInicio;
	}
	/**
	 * @return the fechaFin
	 */
	public Date getFechaFin() {
		return fechaFin;
	}
	/**
	 * @param fechaFin the fechaFin to set
	 */
	public void setFechaFin(Date fechaFin) {
		this.fechaFin = fechaFin;
	}
	/**
	 * @return the observaciones
	 */
	public String getObservaciones() {
		return observaciones;
	}
	/**
	 * @param observaciones the observaciones to set
	 */
	public void setObservaciones(String observaciones) {
		this.observaciones = observaciones;
	}
	/**
	 * @return the idaVuelta
	 */
	public Integer getIdaVuelta() {
		return idaVuelta;
	}
	/**
	 * @param idaVuelta the idaVuelta to set
	 */
	public void setIdaVuelta(Integer idaVuelta) {
		this.idaVuelta = idaVuelta;
	}
	/**
	 * @return the valorMinimo
	 */
	public Double getValorMinimo() {
		return valorMinimo;
	}
	/**
	 * @param valorMinimo the valorMinimo to set
	 */
	public void setValorMinimo(Double valorMinimo) {
		this.valorMinimo = valorMinimo;
	}
	/**
	 * @return the valorMaximo
	 */
	public Double getValorMaximo() {
		return valorMaximo;
	}
	/**
	 * @param valorMaximo the valorMaximo to set
	 */
	public void setValorMaximo(Double valorMaximo) {
		this.valorMaximo = valorMaximo;
	}
	

}
