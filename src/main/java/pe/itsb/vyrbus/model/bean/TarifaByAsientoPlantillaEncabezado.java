/**
 * Proyecto		: SISVYR
 * Sistema		: Sistema de Ventas y Reservas
 * Descripción	: 
 * Autor		: José Abanto
 * Fecha		: 9 may. 2024
 * Hora			: 20:34:43
 */
package pe.itsb.vyrbus.model.bean;

import java.io.Serializable;


/**
 * @author abant
 *
 */
public class TarifaByAsientoPlantillaEncabezado extends GenericBean implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Integer id;
	private String denominacion;
	private TipoPrecio tipoPrecio;
	private Empresa empresa;
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
	 * @return the tipoPrecio
	 */
	public TipoPrecio getTipoPrecio() {
		return tipoPrecio;
	}
	/**
	 * @param tipoPrecio the tipoPrecio to set
	 */
	public void setTipoPrecio(TipoPrecio tipoPrecio) {
		this.tipoPrecio = tipoPrecio;
	}
	/**
	 * @return the empresa
	 */
	public Empresa getEmpresa() {
		return empresa;
	}
	/**
	 * @param empresa the empresa to set
	 */
	public void setEmpresa(Empresa empresa) {
		this.empresa = empresa;
	}

}
