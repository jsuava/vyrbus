/**
 * Proyecto		: SISVYR
 * Sistema		: Sistema de Ventas y Reservas
 * Descripción	:
 * Autor		: José Abanto
 * Fecha		: 05/01/2017
 * Hora			: 14:38:47
 */
package pe.itsb.vyrbus.model.bean;

/**
 * @author Jose Abanto
 *
 */
public class TipoPrecio extends GenericBean{

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	private Integer id;
	private String denominacion;
	private String nombreCorto;
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
	 * @return the nombreCorto
	 */
	public String getNombreCorto() {
		return nombreCorto;
	}
	/**
	 * @param nombreCorto the nombreCorto to set
	 */
	public void setNombreCorto(String nombreCorto) {
		this.nombreCorto = nombreCorto;
	}

	@Override
	public String toString(){
		return this.denominacion;
	}

}
