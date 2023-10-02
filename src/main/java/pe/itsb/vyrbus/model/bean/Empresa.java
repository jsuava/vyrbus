/**
 * Proyecto		: VYRBUS
 * Sistema		: Sistema de Pasajes
 * Descripcion	:
 * Autor		: Jose Avalos
 * Fecha		: 18/04/2016
 * Hora			: 16:34:49
 */
package pe.itsb.vyrbus.model.bean;


/**
 * @author JAVALOS
 *
 */
//@Entity
//@Table(name = "VRMEMPRESA")
public class Empresa extends GenericBean implements java.io.Serializable {
	private static final long serialVersionUID = 1L;

	private Integer id;
	private String razonSocial;
	private String nombreCorto;
	private String direccion;
	private TipoDocumento tipoDocumento;
	private String numeroDocumento;
	private String repLegal;
	private String sigla;
	private String logo;

	public Empresa() {
		super();
	}


	/**
	 * @param id
	 */
	public Empresa(Integer id) {
		super();
		this.id = id;
	}


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
	 * @return the razonSocial
	 */
	public String getRazonSocial() {
		return razonSocial;
	}
	/**
	 * @param razonSocial the razonSocial to set
	 */
	public void setRazonSocial(String razonSocial) {
		this.razonSocial = razonSocial;
	}
	/**
	 * @return the nombreCorto
	 */
	public String getNombreCorto() {
		return nombreCorto;
	}
	/**
	 * @param nombreCorto the nombreCorto to set
	 */
	public void setNombreCorto(String nombreCorto) {
		this.nombreCorto = nombreCorto;
	}
	/**
	 * @return the direccion
	 */
	public String getDireccion() {
		return direccion;
	}
	/**
	 * @param direccion the direccion to set
	 */
	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}
	/**
	 * @return the tipoDocumento
	 */
	public TipoDocumento getTipoDocumento() {
		return tipoDocumento;
	}
	/**
	 * @param tipoDocumento the tipoDocumento to set
	 */
	public void setTipoDocumento(TipoDocumento tipoDocumento) {
		this.tipoDocumento = tipoDocumento;
	}
	/**
	 * @return the numeroDocumento
	 */
	public String getNumeroDocumento() {
		return numeroDocumento;
	}
	/**
	 * @param numeroDocumento the numeroDocumento to set
	 */
	public void setNumeroDocumento(String numeroDocumento) {
		this.numeroDocumento = numeroDocumento;
	}
	/**
	 * @return the repLegal
	 */
	public String getRepLegal() {
		return repLegal;
	}
	/**
	 * @param repLegal the repLegal to set
	 */
	public void setRepLegal(String repLegal) {
		this.repLegal = repLegal;
	}

	/**
	 * @return the sigla
	 */
	public String getSigla() {
		return sigla;
	}


	/**
	 * @param sigla the sigla to set
	 */
	public void setSigla(String sigla) {
		this.sigla = sigla;
	}


	/**
	 * @return the logo
	 */
	public String getLogo() {
		return logo;
	}


	/**
	 * @param logo the logo to set
	 */
	public void setLogo(String logo) {
		this.logo = logo;
	}


	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return this.razonSocial;
	}
}
