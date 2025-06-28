/**
 * Proyecto		: SISVYR
 * Sistema		: Sistema de Ventas y Reservas
 * Descripción	:
 * Autor		: José Sullo Avalos
 * Fecha		: 09/11/2013
 */
package pe.itsb.vyrbus.model.bean;

import java.io.Serializable;

/**
 * @author JABANTO
 *
 */
public class DetalleManifiesto extends GenericBean  implements Serializable{
	private static final long serialVersionUID = 1L;

	private Manifiesto manifiesto;
	private VentaPasaje ventaPasaje;
	private Agencia agencia;
	private DetalleManifiestoID detalleManifiestoID;
	
	private MovimientoPasajes movimientoPasajes;

	/**
	 * @return the manifiesto
	 */
	public Manifiesto getManifiesto() {
		return manifiesto;
	}
	/**
	 * @param manifiesto the manifiesto to set
	 */
	public void setManifiesto(Manifiesto manifiesto) {
		this.manifiesto = manifiesto;
	}
	/**
	 * @return the ventaPasaje
	 */
	public VentaPasaje getVentaPasaje() {
		return ventaPasaje;
	}
	/**
	 * @param ventaPasaje the ventaPasaje to set
	 */
	public void setVentaPasaje(VentaPasaje ventaPasaje) {
		this.ventaPasaje = ventaPasaje;
	}
	/**
	 * @return the agencia
	 */
	public Agencia getAgencia() {
		return agencia;
	}
	/**
	 * @param agencia the agencia to set
	 */
	public void setAgencia(Agencia agencia) {
		this.agencia = agencia;
	}
	/**
	 * @return the detalleManifiestoID
	 */
	public DetalleManifiestoID getDetalleManifiestoID() {
		return detalleManifiestoID;
	}
	/**
	 * @param detalleManifiestoID the detalleManifiestoID to set
	 */
	public void setDetalleManifiestoID(DetalleManifiestoID detalleManifiestoID) {
		this.detalleManifiestoID = detalleManifiestoID;
	}
	/**
	 * @return the movimientoPasajes
	 */
	public MovimientoPasajes getMovimientoPasajes() {
		return movimientoPasajes;
	}
	/**
	 * @param movimientoPasajes the movimientoPasajes to set
	 */
	public void setMovimientoPasajes(MovimientoPasajes movimientoPasajes) {
		this.movimientoPasajes = movimientoPasajes;
	}



}
