package pe.itsb.vyrbus.model.bean;

import java.io.Serializable;

public class CentroCosto extends GenericBean implements Serializable {
	private static final long serialVersionUID = 1L;
	private Integer id;
	private Concesionario concesionario;
	private String responsable;
	private String codigo;
	private String denominacion;
	private TipoCentroCosto tipoCentroCosto;


	/**
	 * Constructor
	 */
	public CentroCosto() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param id	: Identificador del centro de costo.
	 */
	public CentroCosto(Integer id) {
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
	 * @return the concesionario
	 */
	public Concesionario getConcesionario() {
		return concesionario;
	}
	/**
	 * @param concesionario the concesionario to set
	 */
	public void setConcesionario(Concesionario concesionario) {
		this.concesionario = concesionario;
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
	 * @return the denominacion
	 */
	public String getDenominacion() {
		return denominacion;
	}
	/**
	 * @param denominacion the denominacion to set
	 */
	public void setDenominacion(String denominacion) {
		this.denominacion = denominacion;
	}

	/**
	 * @return the responsable
	 */
	public String getResponsable() {
		return responsable;
	}
	/**
	 * @param responsable the responsable to set
	 */
	public void setResponsable(String responsable) {
		this.responsable = responsable;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "["+this.id+"]"+this.codigo+" --> "+this.denominacion;
	}

	/**
	 * @return the tipoCentroCosto
	 */
	public TipoCentroCosto getTipoCentroCosto() {
		return tipoCentroCosto;
	}

	/**
	 * @param tipoCentroCosto the tipoCentroCosto to set
	 */
	public void setTipoCentroCosto(TipoCentroCosto tipoCentroCosto) {
		this.tipoCentroCosto = tipoCentroCosto;
	}
}
