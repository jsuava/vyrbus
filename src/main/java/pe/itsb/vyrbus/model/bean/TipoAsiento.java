/**
 * Proyecto		: SISVYR
 * Sistema		: Sistema de Ventas y Reservas
 * Descripción	:
 * Autor		: José Abanto
 * Fecha		: 11/07/2016
 * Hora			: 17:21:59
 */
package pe.itsb.vyrbus.model.bean;

import java.util.List;

/**
 * @author jabanto
 *
 */
public class TipoAsiento extends GenericBean{

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	private Integer id;
	private String denominacion;

	private List<Integer> asientos; //No mapeado.

	public TipoAsiento(){
		super();
	}

	public TipoAsiento(Integer id){
		this.id=id;
	}

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
	 * @return the asientos
	 */
	public List<Integer> getAsientos() {
		return asientos;
	}

	/**
	 * @param asientos the asientos to set
	 */
	public void setAsientos(List<Integer> asientos) {
		this.asientos = asientos;
	}

}
