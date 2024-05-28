/**
 * Proyecto		: SISVYR
 * Sistema		: Sistema de Ventas y Reservas
 * Descripción	: 
 * Autor		: José Abanto
 * Fecha		: 9 may. 2024
 * Hora			: 20:29:14
 */
package pe.itsb.vyrbus.model.bean;

import java.io.Serializable;


/**
 * @author abant
 *
 */
public class TarifaByAsientoPlantillaPromocion extends GenericBean implements Serializable, Cloneable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Integer id;
	private String denominacion;
	private TipoAsiento tipoAsiento;
	private String asientos;
	private Double valorDescuento;
	private Integer isDescuentoSoles;
	private Empresa empresa;
	
	private TipoPrecio tipoPrecio; //No mapeado
	private Double precio;	//No mapeado
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
	 * @return the tipoAsiento
	 */
	public TipoAsiento getTipoAsiento() {
		return tipoAsiento;
	}
	/**
	 * @param tipoAsiento the tipoAsiento to set
	 */
	public void setTipoAsiento(TipoAsiento tipoAsiento) {
		this.tipoAsiento = tipoAsiento;
	}
	/**
	 * @return the asientos
	 */
	public String getAsientos() {
		return asientos;
	}
	/**
	 * @param asientos the asientos to set
	 */
	public void setAsientos(String asientos) {
		this.asientos = asientos;
	}
	/**
	 * @return the valorDescuento
	 */
	public Double getValorDescuento() {
		return valorDescuento;
	}
	/**
	 * @param valorDescuento the valorDescuento to set
	 */
	public void setValorDescuento(Double valorDescuento) {
		this.valorDescuento = valorDescuento;
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
	 * @return the isDescuentoSoles
	 */
	public Integer getIsDescuentoSoles() {
		return isDescuentoSoles;
	}
	/**
	 * @param isDescuentoSoles the isDescuentoSoles to set
	 */
	public void setIsDescuentoSoles(Integer isDescuentoSoles) {
		this.isDescuentoSoles = isDescuentoSoles;
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
	 * @return the precio
	 */
	public Double getPrecio() {
		return precio;
	}
	/**
	 * @param precio the precio to set
	 */
	public void setPrecio(Double precio) {
		this.precio = precio;
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#clone()
	 */
	@Override
	public Object clone() throws CloneNotSupportedException {
		// TODO Auto-generated method stub
		return super.clone();
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
