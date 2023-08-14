/**
 * Proyecto		: SISVYR
 * Sistema		: Sistema de Ventas y Reservas
 * Descripción	:
 * Autor		: José Abanto
 * Fecha		: 31/07/2014
 */
package pe.itsb.vyrbus.model.bean;

/**
 * @author JABANTO
 *
 */
public class TitanUsuarioRol {
	private TitanRolUsuario rolUsuario;
	private TitanUsuarioPersonal usuarioPersona;
	private Integer estado;



	/**
	 * @return the rolUsuario
	 */
	public TitanRolUsuario getRolUsuario() {
		return rolUsuario;
	}
	/**
	 * @param rolUsuario the rolUsuario to set
	 */
	public void setRolUsuario(TitanRolUsuario rolUsuario) {
		this.rolUsuario = rolUsuario;
	}
	/**
	 * @return the usuarioPersona
	 */
	public TitanUsuarioPersonal getUsuarioPersona() {
		return usuarioPersona;
	}
	/**
	 * @param usuarioPersona the usuarioPersona to set
	 */
	public void setUsuarioPersona(TitanUsuarioPersonal usuarioPersona) {
		this.usuarioPersona = usuarioPersona;
	}
	/**
	 * @return the estado
	 */
	public Integer getEstado() {
		return estado;
	}
	/**
	 * @param estado the estado to set
	 */
	public void setEstado(Integer estado) {
		this.estado = estado;
	}



}
