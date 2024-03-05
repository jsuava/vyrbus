/**
 * Proyecto		: SYSVYR
 * Sistema		: Sistema de Mantenimiento de Buses
 * Descripci�n	:
 * Autor		: Jos� Sullo Avalos
 * Fecha		: 31/10/2010
 */
package pe.itsb.vyrbus.model.bean;

import java.io.Serializable;

/**
 * @author Jose
 *
 */
public class Sexo extends GenericBean implements Serializable {

	private static final long serialVersionUID = -1L;

	private Integer id;
	private String denominacion;

	public Sexo() {
		super();
	}
	
	public Sexo(Integer id) {
		this.id = id;
	}
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}

	public String getDenominacion() {
		return denominacion;
	}
	public void setDenominacion(String denominacion) {
		this.denominacion = denominacion;
	}
	
	
}
