/**
 * Proyecto		: SISVYR
 * Sistema		: Sistema de Ventas y Reservas
 * Descripción	: 
 * Autor		: José Abanto
 * Fecha		: 9 may. 2024
 * Hora			: 20:35:49
 */
package pe.itsb.vyrbus.model.bean;

import java.io.Serializable;



/**
 * @author abant
 *
 */
public class TarifaByAsientoPlantillaEncabezadoDetalle extends GenericBean implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Integer id;
	private TarifaByAsientoPlantillaEncabezado tarifaByAsientoPlantillaEncabezado;
	private TipoAsiento tipoAsiento;
	private String asientos;
	private Integer orden;
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
	 * @return the orden
	 */
	public Integer getOrden() {
		return orden;
	}
	/**
	 * @param orden the orden to set
	 */
	public void setOrden(Integer orden) {
		this.orden = orden;
	}
	/**
	 * @return the tarifaByAsientoPlantillaEncabezado
	 */
	public TarifaByAsientoPlantillaEncabezado getTarifaByAsientoPlantillaEncabezado() {
		return tarifaByAsientoPlantillaEncabezado;
	}
	/**
	 * @param tarifaByAsientoPlantillaEncabezado the tarifaByAsientoPlantillaEncabezado to set
	 */
	public void setTarifaByAsientoPlantillaEncabezado(TarifaByAsientoPlantillaEncabezado tarifaByAsientoPlantillaEncabezado) {
		this.tarifaByAsientoPlantillaEncabezado = tarifaByAsientoPlantillaEncabezado;
	}

}
