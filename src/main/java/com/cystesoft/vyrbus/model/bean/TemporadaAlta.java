package com.cystesoft.vyrbus.model.bean;

import java.io.Serializable;
import java.util.Date;

/**
 * 
 * @author JABANTO
 *
 */
public class TemporadaAlta extends GenericBean implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private Long id;
	private MotivoTemporadaAlta motivoTemporadaAlta;
	private Date diaTemporadaAlta;
	
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
	 * @return the motivoTemporadaAlta
	 */
	public MotivoTemporadaAlta getMotivoTemporadaAlta() {
		return motivoTemporadaAlta;
	}
	/**
	 * @param motivoTemporadaAlta the motivoTemporadaAlta to set
	 */
	public void setMotivoTemporadaAlta(MotivoTemporadaAlta motivoTemporadaAlta) {
		this.motivoTemporadaAlta = motivoTemporadaAlta;
	}
	/**
	 * @return the diaTemporadaAlta
	 */
	public Date getDiaTemporadaAlta() {
		return diaTemporadaAlta;
	}
	/**
	 * @param diaTemporadaAlta the diaTemporadaAlta to set
	 */
	public void setDiaTemporadaAlta(Date diaTemporadaAlta) {
		this.diaTemporadaAlta = diaTemporadaAlta;
	}

}
