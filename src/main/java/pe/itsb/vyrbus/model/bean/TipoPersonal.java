package pe.itsb.vyrbus.model.bean;

import java.io.Serializable;

// Generated 22/12/2009 05:34:14 PM by Hibernate Tools 3.2.4.GA

public class TipoPersonal extends GenericBean implements Serializable {
	private static final long serialVersionUID=1L;
	private Integer id;
	private String denominacion;

	public TipoPersonal() {
	}

	public TipoPersonal(Integer id) {
		this.id = id;
	}

	public Integer getId() {
		return this.id;
	}
	public void setId(Integer id) {
		this.id = id;
	}

	public String getDenominacion() {
		return this.denominacion;
	}
	public void setDenominacion(String denominacion) {
		this.denominacion = denominacion;
	}
}
