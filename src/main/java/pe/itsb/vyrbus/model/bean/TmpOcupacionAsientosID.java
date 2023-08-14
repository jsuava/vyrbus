/**
 * Proyecto		: SISVYR
 * Sistema		: Sistema de Ventas y Reservas
 * Descripción	:
 * Autor		: José Sullo Avalos
 * Fecha		: 09/07/2012
 */
package pe.itsb.vyrbus.model.bean;

import java.io.Serializable;

/**
 *
 * @author José Sullo Avalos
 * @since JDK1.6
 */
public class TmpOcupacionAsientosID extends GenericBean implements Serializable{
	private static final long serialVersionUID = 1L;
	private Integer idUsuarioHardware;
	private Integer idUsuario;
	private Integer idRuta;
	private Long idItinerario;
	private Integer numeroAsiento;

	/**
	 * @return Objeto idUsuarioHardware.
	 */
	public Integer getIdUsuarioHardware() {
		return idUsuarioHardware;
	}
	/**
	 * @param idUsuarioHardware	: Setea el objeto idUsuarioHardware.
	 */
	public void setIdUsuarioHardware(Integer idUsuarioHardware) {
		this.idUsuarioHardware = idUsuarioHardware;
	}
//	/**
//	 * @return Objeto idAgencia.
//	 */
//	public Integer getIdAgencia() {
//		return idAgencia;
//	}
//	/**
//	 * @param idAgencia	: Setea el objeto idAgencia.
//	 */
//	public void setIdAgencia(Integer idAgencia) {
//		this.idAgencia = idAgencia;
//	}
	/**
	 * @return Objeto idUsuario.
	 */
	public Integer getIdUsuario() {
		return idUsuario;
	}
	/**
	 * @param idUsuario	: Setea el objeto idUsuario.
	 */
	public void setIdUsuario(Integer idUsuario) {
		this.idUsuario = idUsuario;
	}
	/**
	 * @return Objeto idRuta.
	 */
	public Integer getIdRuta() {
		return idRuta;
	}
	/**
	 * @param idRuta	: Setea el objeto idRuta.
	 */
	public void setIdRuta(Integer idRuta) {
		this.idRuta = idRuta;
	}
	/**
	 * @return Objeto idItinerario.
	 */
	public Long getIdItinerario() {
		return idItinerario;
	}
	/**
	 * @param idItinerario	: Setea el objeto idItinerario.
	 */
	public void setIdItinerario(Long idItinerario) {
		this.idItinerario = idItinerario;
	}
	/**
	 * @return the numeroAsiento
	 */
	public Integer getNumeroAsiento() {
		return numeroAsiento;
	}
	/**
	 * @param numeroAsiento the numeroAsiento to set
	 */
	public void setNumeroAsiento(Integer numeroAsiento) {
		this.numeroAsiento = numeroAsiento;
	}
}
