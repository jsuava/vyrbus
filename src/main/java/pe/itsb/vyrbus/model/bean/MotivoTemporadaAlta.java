package pe.itsb.vyrbus.model.bean;

import java.io.Serializable;

/**
 *
 * @author JABANTO
 *
 */
public class MotivoTemporadaAlta extends GenericBean implements Serializable {
	private static final long serialVersionUID = 1L;

	private Integer id;
	private String nombreMotivo;
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
	 * @return the nombreMotivo
	 */
	public String getNombreMotivo() {
		return nombreMotivo;
	}
	/**
	 * @param nombreMotivo the nombreMotivo to set
	 */
	public void setNombreMotivo(String nombreMotivo) {
		this.nombreMotivo = nombreMotivo;
	}




}
