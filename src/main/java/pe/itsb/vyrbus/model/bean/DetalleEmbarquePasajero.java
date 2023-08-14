/**
 * Proyecto		: SISVYR
 * Sistema		: Sistema de Ventas y Reservas
 * Descripción	:
 * Autor		: José Abanto
 * Fecha		: 28/11/2016
 * Hora			: 09:15:14
 */
package pe.itsb.vyrbus.model.bean;

/**
 * @author Jose Abanto
 *
 */
public class DetalleEmbarquePasajero extends GenericBean{

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	private Long id;
	private EmbarquePasajero embarquePasajero;
	private VentaPasaje ventaPasaje;
	private String apellidoPaterno;
	private String apellidoMaterno;
	private String nombres;
	private String apellidosNombres;
	private String barcode;
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
	 * @return the embarquePasajero
	 */
	public EmbarquePasajero getEmbarquePasajero() {
		return embarquePasajero;
	}
	/**
	 * @param embarquePasajero the embarquePasajero to set
	 */
	public void setEmbarquePasajero(EmbarquePasajero embarquePasajero) {
		this.embarquePasajero = embarquePasajero;
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
	 * @return the apellidoPaterno
	 */
	public String getApellidoPaterno() {
		return apellidoPaterno;
	}
	/**
	 * @param apellidoPaterno the apellidoPaterno to set
	 */
	public void setApellidoPaterno(String apellidoPaterno) {
		this.apellidoPaterno = apellidoPaterno;
	}
	/**
	 * @return the apellidoMaterno
	 */
	public String getApellidoMaterno() {
		return apellidoMaterno;
	}
	/**
	 * @param apellidoMaterno the apellidoMaterno to set
	 */
	public void setApellidoMaterno(String apellidoMaterno) {
		this.apellidoMaterno = apellidoMaterno;
	}
	/**
	 * @return the nombres
	 */
	public String getNombres() {
		return nombres;
	}
	/**
	 * @param nombres the nombres to set
	 */
	public void setNombres(String nombres) {
		this.nombres = nombres;
	}
	/**
	 * @return the apellidosNombres
	 */
	public String getApellidosNombres() {
		return apellidosNombres;
	}
	/**
	 * @param apellidosNombres the apellidosNombres to set
	 */
	public void setApellidosNombres(String apellidosNombres) {
		this.apellidosNombres = apellidosNombres;
	}
	/**
	 * @return the barcode
	 */
	public String getBarcode() {
		return barcode;
	}
	/**
	 * @param barcode the barcode to set
	 */
	public void setBarcode(String barcode) {
		this.barcode = barcode;
	}

}
