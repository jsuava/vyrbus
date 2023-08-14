/**
 * Proyecto		: SISVYR
 * Sistema		: Sistema de Ventas y Reservas
 * Descripci�n	:
 * Autor		: Jos� Abanto
 * Fecha		: 16 nov. 2022
 * Hora			: 12:43:15
 */
package pe.itsb.vyrbus.model.bean;

/**
 * @author abant
 *
 */
public class Flag extends GenericBean implements java.io.Serializable {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	private Integer id;
	private String codigo;
	private String llave;
	private String descripcionCorta;
	private String descripcionLarga;
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
	 * @return the codigo
	 */
	public String getCodigo() {
		return codigo;
	}
	/**
	 * @param codigo the codigo to set
	 */
	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}
	/**
	 * @return the llave
	 */
	public String getLlave() {
		return llave;
	}
	/**
	 * @param llave the llave to set
	 */
	public void setLlave(String llave) {
		this.llave = llave;
	}
	/**
	 * @return the descripcionCorta
	 */
	public String getDescripcionCorta() {
		return descripcionCorta;
	}
	/**
	 * @param descripcionCorta the descripcionCorta to set
	 */
	public void setDescripcionCorta(String descripcionCorta) {
		this.descripcionCorta = descripcionCorta;
	}
	/**
	 * @return the descripcionLarga
	 */
	public String getDescripcionLarga() {
		return descripcionLarga;
	}
	/**
	 * @param descripcionLarga the descripcionLarga to set
	 */
	public void setDescripcionLarga(String descripcionLarga) {
		this.descripcionLarga = descripcionLarga;
	}


}
