/**
 * Proyecto		: SISVYR
 * Sistema		: Sistema de Ventas y Reservas
 * Descripción	:
 * Autor		: José
 * Fecha		: 09/11/2013
 */
package pe.itsb.vyrbus.model.bean;

import java.io.Serializable;

/**
 * @author JABANTO
 *
 */
public class DetalleManifiestoID implements Serializable {
	private static final long serialVersionUID = 1L;

	private Long idManifiesto;
	private Long idVentaPasaje;

	public DetalleManifiestoID(){
	}

	public DetalleManifiestoID(Long idManifiesto, Long idVentaPasajes){
		super();
		this.idManifiesto=idManifiesto;
		this.idVentaPasaje=idVentaPasajes;
	}

	/**
	 * @return the idManifiesto
	 */
	public Long getIdManifiesto() {
		return idManifiesto;
	}


	/**
	 * @param idManifiesto the idManifiesto to set
	 */
	public void setIdManifiesto(Long idManifiesto) {
		this.idManifiesto = idManifiesto;
	}


	/**
	 * @return the idVentaPasajes
	 */
	public Long getIdVentaPasaje() {
		return idVentaPasaje;
	}


	/**
	 * @param idVentaPasajes the idVentaPasajes to set
	 */
	public void setIdVentaPasaje(Long idVentaPasaje) {
		this.idVentaPasaje = idVentaPasaje;
	}




}
