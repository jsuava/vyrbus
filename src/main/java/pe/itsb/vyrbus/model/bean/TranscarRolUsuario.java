/**
 * Proyecto		: SISVYR
 * Sistema		: Sistema de Ventas y Reservas
 * Descripción	:
 * Autor		: José Abanto
 * Fecha		: 26 oct. 2021
 * Hora			: 10:51:38
 */
package pe.itsb.vyrbus.model.bean;

import java.io.Serializable;

/**
 * @author abant
 *
 */
public class TranscarRolUsuario implements Serializable{

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	private Integer id;
	private String nombre;
	private TranscarUsuarioPersonal transcarUsuarioPersonal;
	//	private Integer usuarioId;

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
	 * @return the nombre
	 */
	public String getNombre() {
		return nombre;
	}
	/**
	 * @param nombre the nombre to set
	 */
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
//	/**
//	 * @return the usuarioId
//	 */
//	public Integer getUsuarioId() {
//		return usuarioId;
//	}
//	/**
//	 * @param usuarioId the usuarioId to set
//	 */
//	public void setUsuarioId(Integer usuarioId) {
//		this.usuarioId = usuarioId;
//	}
	/**
	 * @return the transcarUsuarioPersonal
	 */
	public TranscarUsuarioPersonal getTranscarUsuarioPersonal() {
		return transcarUsuarioPersonal;
	}
	/**
	 * @param transcarUsuarioPersonal the transcarUsuarioPersonal to set
	 */
	public void setTranscarUsuarioPersonal(TranscarUsuarioPersonal transcarUsuarioPersonal) {
		this.transcarUsuarioPersonal = transcarUsuarioPersonal;
	}


}
