package com.cystesoft.vyrbus.model.bean;

import java.io.Serializable;

public class TerminosVenta extends GenericBean implements Serializable {
	private static final long serialVersionUID = 1L;
	private Integer id;
	private String denominacion;
	private String idioma;
	private Integer orden;
	
	public TerminosVenta() {
	
	}
	public TerminosVenta(Integer id) {
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
	
	public String getIdioma() {
		return idioma;
	}
	public void setIdioma(String idioma) {
		this.idioma = idioma;
	}
	
	public Integer getOrden() {
		return orden;
	}
	public void setOrden(Integer orden) {
		this.orden = orden;
	}	
}