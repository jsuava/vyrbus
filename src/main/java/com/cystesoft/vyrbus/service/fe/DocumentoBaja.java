
package com.cystesoft.vyrbus.service.fe;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlType;

/**
 * <p>
 * Java class for DocumentoBaja complex type.
 * 
 * <p>
 * The following schema fragment specifies the expected content contained within
 * this class.
 * 
 * <pre>
 * &lt;complexType name="DocumentoBaja">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="agenciaID" type="{http://www.w3.org/2001/XMLSchema}long" minOccurs="0"/>
 *         &lt;element name="descripcionMotivo" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="fechaEmision" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="numeroCorrelativo" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="numeroSerie" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="rucEmpresa" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="tipoDocumentoID" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="usuarioID" type="{http://www.w3.org/2001/XMLSchema}long" minOccurs="0"/>
 *         &lt;element name="usuarioInsercion" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="usuarioModificacion" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "DocumentoBaja", namespace = "http://schemas.datacontract.org/2004/07/FEService.Input", propOrder = {
		"agenciaID", "descripcionMotivo", "fechaEmision", "numeroCorrelativo", "numeroSerie", "rucEmpresa",
		"tipoDocumentoID", "usuarioID", "usuarioInsercion", "usuarioModificacion" })
public class DocumentoBaja {

	protected Long agenciaID;
	@XmlElementRef(name = "descripcionMotivo", namespace = "http://schemas.datacontract.org/2004/07/FEService.Input", type = JAXBElement.class)
	protected JAXBElement<String> descripcionMotivo;
	@XmlElementRef(name = "fechaEmision", namespace = "http://schemas.datacontract.org/2004/07/FEService.Input", type = JAXBElement.class)
	protected JAXBElement<String> fechaEmision;
	@XmlElementRef(name = "numeroCorrelativo", namespace = "http://schemas.datacontract.org/2004/07/FEService.Input", type = JAXBElement.class)
	protected JAXBElement<String> numeroCorrelativo;
	@XmlElementRef(name = "numeroSerie", namespace = "http://schemas.datacontract.org/2004/07/FEService.Input", type = JAXBElement.class)
	protected JAXBElement<String> numeroSerie;
	@XmlElementRef(name = "rucEmpresa", namespace = "http://schemas.datacontract.org/2004/07/FEService.Input", type = JAXBElement.class)
	protected JAXBElement<String> rucEmpresa;
	@XmlElementRef(name = "tipoDocumentoID", namespace = "http://schemas.datacontract.org/2004/07/FEService.Input", type = JAXBElement.class)
	protected JAXBElement<String> tipoDocumentoID;
	protected Long usuarioID;
	@XmlElementRef(name = "usuarioInsercion", namespace = "http://schemas.datacontract.org/2004/07/FEService.Input", type = JAXBElement.class)
	protected JAXBElement<String> usuarioInsercion;
	@XmlElementRef(name = "usuarioModificacion", namespace = "http://schemas.datacontract.org/2004/07/FEService.Input", type = JAXBElement.class)
	protected JAXBElement<String> usuarioModificacion;

	/**
	 * Gets the value of the agenciaID property.
	 * 
	 * @return possible object is {@link Long }
	 * 
	 */
	public Long getAgenciaID() {
		return agenciaID;
	}

	/**
	 * Sets the value of the agenciaID property.
	 * 
	 * @param value
	 *            allowed object is {@link Long }
	 * 
	 */
	public void setAgenciaID(Long value) {
		this.agenciaID = value;
	}

	/**
	 * Gets the value of the descripcionMotivo property.
	 * 
	 * @return possible object is {@link JAXBElement }{@code <}{@link String
	 *         }{@code >}
	 * 
	 */
	public JAXBElement<String> getDescripcionMotivo() {
		return descripcionMotivo;
	}

	/**
	 * Sets the value of the descripcionMotivo property.
	 * 
	 * @param value
	 *            allowed object is {@link JAXBElement }{@code <}{@link String
	 *            }{@code >}
	 * 
	 */
	public void setDescripcionMotivo(JAXBElement<String> value) {
		this.descripcionMotivo = ((JAXBElement<String>) value);
	}

	/**
	 * Gets the value of the fechaEmision property.
	 * 
	 * @return possible object is {@link JAXBElement }{@code <}{@link String
	 *         }{@code >}
	 * 
	 */
	public JAXBElement<String> getFechaEmision() {
		return fechaEmision;
	}

	/**
	 * Sets the value of the fechaEmision property.
	 * 
	 * @param value
	 *            allowed object is {@link JAXBElement }{@code <}{@link String
	 *            }{@code >}
	 * 
	 */
	public void setFechaEmision(JAXBElement<String> value) {
		this.fechaEmision = ((JAXBElement<String>) value);
	}

	/**
	 * Gets the value of the numeroCorrelativo property.
	 * 
	 * @return possible object is {@link JAXBElement }{@code <}{@link String
	 *         }{@code >}
	 * 
	 */
	public JAXBElement<String> getNumeroCorrelativo() {
		return numeroCorrelativo;
	}

	/**
	 * Sets the value of the numeroCorrelativo property.
	 * 
	 * @param value
	 *            allowed object is {@link JAXBElement }{@code <}{@link String
	 *            }{@code >}
	 * 
	 */
	public void setNumeroCorrelativo(JAXBElement<String> value) {
		this.numeroCorrelativo = ((JAXBElement<String>) value);
	}

	/**
	 * Gets the value of the numeroSerie property.
	 * 
	 * @return possible object is {@link JAXBElement }{@code <}{@link String
	 *         }{@code >}
	 * 
	 */
	public JAXBElement<String> getNumeroSerie() {
		return numeroSerie;
	}

	/**
	 * Sets the value of the numeroSerie property.
	 * 
	 * @param value
	 *            allowed object is {@link JAXBElement }{@code <}{@link String
	 *            }{@code >}
	 * 
	 */
	public void setNumeroSerie(JAXBElement<String> value) {
		this.numeroSerie = ((JAXBElement<String>) value);
	}

	/**
	 * Gets the value of the rucEmpresa property.
	 * 
	 * @return possible object is {@link JAXBElement }{@code <}{@link String
	 *         }{@code >}
	 * 
	 */
	public JAXBElement<String> getRucEmpresa() {
		return rucEmpresa;
	}

	/**
	 * Sets the value of the rucEmpresa property.
	 * 
	 * @param value
	 *            allowed object is {@link JAXBElement }{@code <}{@link String
	 *            }{@code >}
	 * 
	 */
	public void setRucEmpresa(JAXBElement<String> value) {
		this.rucEmpresa = ((JAXBElement<String>) value);
	}

	/**
	 * Gets the value of the tipoDocumentoID property.
	 * 
	 * @return possible object is {@link JAXBElement }{@code <}{@link String
	 *         }{@code >}
	 * 
	 */
	public JAXBElement<String> getTipoDocumentoID() {
		return tipoDocumentoID;
	}

	/**
	 * Sets the value of the tipoDocumentoID property.
	 * 
	 * @param value
	 *            allowed object is {@link JAXBElement }{@code <}{@link String
	 *            }{@code >}
	 * 
	 */
	public void setTipoDocumentoID(JAXBElement<String> value) {
		this.tipoDocumentoID = ((JAXBElement<String>) value);
	}

	/**
	 * Gets the value of the usuarioID property.
	 * 
	 * @return possible object is {@link Long }
	 * 
	 */
	public Long getUsuarioID() {
		return usuarioID;
	}

	/**
	 * Sets the value of the usuarioID property.
	 * 
	 * @param value
	 *            allowed object is {@link Long }
	 * 
	 */
	public void setUsuarioID(Long value) {
		this.usuarioID = value;
	}

	/**
	 * Gets the value of the usuarioInsercion property.
	 * 
	 * @return possible object is {@link JAXBElement }{@code <}{@link String
	 *         }{@code >}
	 * 
	 */
	public JAXBElement<String> getUsuarioInsercion() {
		return usuarioInsercion;
	}

	/**
	 * Sets the value of the usuarioInsercion property.
	 * 
	 * @param value
	 *            allowed object is {@link JAXBElement }{@code <}{@link String
	 *            }{@code >}
	 * 
	 */
	public void setUsuarioInsercion(JAXBElement<String> value) {
		this.usuarioInsercion = ((JAXBElement<String>) value);
	}

	/**
	 * Gets the value of the usuarioModificacion property.
	 * 
	 * @return possible object is {@link JAXBElement }{@code <}{@link String
	 *         }{@code >}
	 * 
	 */
	public JAXBElement<String> getUsuarioModificacion() {
		return usuarioModificacion;
	}

	/**
	 * Sets the value of the usuarioModificacion property.
	 * 
	 * @param value
	 *            allowed object is {@link JAXBElement }{@code <}{@link String
	 *            }{@code >}
	 * 
	 */
	public void setUsuarioModificacion(JAXBElement<String> value) {
		this.usuarioModificacion = ((JAXBElement<String>) value);
	}

}
