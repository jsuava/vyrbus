/**
 * Proyecto		: SISVYR
 * Sistema		: Sistema de Ventas y Reservas
 * Descripción	:
 * Autor		: José Abanto
 * Fecha		: 14 jul. 2021
 * Hora			: 11:39:54
 */
package pe.itsb.vyrbus.model.bean;

/**
 * @author abant
 *
 */
public class DetalleEquipaje  extends GenericBean implements java.io.Serializable{

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	private Long id;
	private Equipaje equipaje;
	private VentaPasaje ventaPasaje;
	private Integer numeroCorrelativo;
	private String ticket;
	private Double peso;
	private VentaPasaje ventaPasajeExceso;
	private Integer principal;
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
	 * @return the equipaje
	 */
	public Equipaje getEquipaje() {
		return equipaje;
	}
	/**
	 * @param equipaje the equipaje to set
	 */
	public void setEquipaje(Equipaje equipaje) {
		this.equipaje = equipaje;
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
	 * @return the numeroCorrelativo
	 */
	public Integer getNumeroCorrelativo() {
		return numeroCorrelativo;
	}
	/**
	 * @param numeroCorrelativo the numeroCorrelativo to set
	 */
	public void setNumeroCorrelativo(Integer numeroCorrelativo) {
		this.numeroCorrelativo = numeroCorrelativo;
	}
	/**
	 * @return the ticket
	 */
	public String getTicket() {
		return ticket;
	}
	/**
	 * @param ticket the ticket to set
	 */
	public void setTicket(String ticket) {
		this.ticket = ticket;
	}
	/**
	 * @return the peso
	 */
	public Double getPeso() {
		return peso;
	}
	/**
	 * @param peso the peso to set
	 */
	public void setPeso(Double peso) {
		this.peso = peso;
	}
	/**
	 * @return the ventaPasajeExceso
	 */
	public VentaPasaje getVentaPasajeExceso() {
		return ventaPasajeExceso;
	}
	/**
	 * @param ventaPasajeExceso the ventaPasajeExceso to set
	 */
	public void setVentaPasajeExceso(VentaPasaje ventaPasajeExceso) {
		this.ventaPasajeExceso = ventaPasajeExceso;
	}
	/**
	 * @return the principal
	 */
	public Integer getPrincipal() {
		return principal;
	}
	/**
	 * @param principal the principal to set
	 */
	public void setPrincipal(Integer principal) {
		this.principal = principal;
	}

}
