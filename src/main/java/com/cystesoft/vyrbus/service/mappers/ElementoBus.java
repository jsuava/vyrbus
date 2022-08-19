package com.cystesoft.vyrbus.service.mappers;

import java.util.HashMap;
import java.util.Map.Entry;

import org.zkoss.zul.Image;

public abstract class ElementoBus extends Image {
	private static final long serialVersionUID = 1L;
	private int fila = -1;
	private int columna = -1;
	private int piso = -1;
	private int tipo = -1;
	private HashMap<String, String> propiedades;


	public ElementoBus(){
		super();
		this.generateTooltiptext();
	}

	public ElementoBus(String src){
		super(src);
		this.generateTooltiptext();
	}

	/**
	 * @return the fila
	 */
	public int getFila() {
		return fila;
	}
	/**
	 * @param fila the fila to set
	 */
	public void setFila(int fila) {
		this.fila = fila;
		this.generateTooltiptext();
	}

	/**
	 * @return the columna
	 */
	public int getColumna() {
		return columna;
	}
	/**
	 * @param columna the columna to set
	 */
	public void setColumna(int columna) {
		this.columna = columna;
		this.generateTooltiptext();
	}

	/**
	 * @return the piso
	 */
	public int getPiso() {
		return piso;
	}
	/**
	 * @param piso the piso to set
	 */
	public void setPiso(int piso) {
		this.piso = piso;
		this.generateTooltiptext();
	}

	/**
	 * @return the tipo
	 */
	public int getTipo() {
		return tipo;
	}
	/**
	 * @param tipo the tipo to set
	 */
	public void setTipo(int tipo) {
		this.tipo = tipo;
		this.generateTooltiptext();
	}

	/**
	 * @return the propiedades
	 */
	public HashMap<String, String> getPropiedades() {
		return propiedades;
	}
	/**
	 * @param propiedades the propiedades to set
	 */
	public void setPropiedades(HashMap<String, String> propiedades) {
		this.propiedades = propiedades;
		this.generateTooltiptext();
	}

	private void generateTooltiptext(){
		StringBuilder t = new StringBuilder();
		t.append(this.getClass().getSimpleName());
		t.append("{Piso : " + (this.getPiso() + 1));
		t.append(", Fila : " + (this.getFila() + 1));
		t.append(", Columna : " + (this.getColumna() + 1));

		if(this.getPropiedades() != null){
			for(Entry<String, String> e : this.getPropiedades().entrySet()){
				t.append(", " + e.getKey() + " : " + e.getValue());
			}
		}

		t.append("}");

		this.setTooltiptext(t.toString());
		this.smartUpdate("title", this.getTooltiptext());
	}
}