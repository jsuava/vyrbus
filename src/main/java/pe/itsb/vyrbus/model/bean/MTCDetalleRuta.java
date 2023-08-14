/**
 * Proyecto		: SISVYR
 * Sistema		: Sistema de Ventas y Reservas
 * Descripción	:
 * Autor		: José Abanto
 * Fecha		: 26/08/2014
 * Hora			: 08:42:35
 */
package pe.itsb.vyrbus.model.bean;

/**
 * @author JABANTO
 *
 */
public class MTCDetalleRuta extends GenericBean {
	private static final long serialVersionUID = 1L;

	private Integer id;
	private MTCRuta mtcRuta;
	private Ruta ruta;
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
	 * @return the mtcRuta
	 */
	public MTCRuta getMtcRuta() {
		return mtcRuta;
	}
	/**
	 * @param mtcRuta the mtcRuta to set
	 */
	public void setMtcRuta(MTCRuta mtcRuta) {
		this.mtcRuta = mtcRuta;
	}
	/**
	 * @return the ruta
	 */
	public Ruta getRuta() {
		return ruta;
	}
	/**
	 * @param ruta the ruta to set
	 */
	public void setRuta(Ruta ruta) {
		this.ruta = ruta;
	}



}
