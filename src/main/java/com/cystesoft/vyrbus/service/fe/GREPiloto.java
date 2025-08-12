
package com.cystesoft.vyrbus.service.fe;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlType;

/**
 * <p>
 * Java class for GREPiloto complex type.
 * 
 * <p>
 * The following schema fragment specifies the expected content contained within
 * this class.
 * 
 * <pre>
 * &lt;complexType name="GREPiloto">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="apellidos" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="nombres" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="numeroDocumento" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="numeroLicencia" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="tipoDocumento" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="titulo" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "GREPiloto", namespace = "http://schemas.datacontract.org/2004/07/FEService.Input", propOrder = {
		"apellidos", "nombres", "numeroDocumento", "numeroLicencia", "tipoDocumento", "titulo" })
public class GREPiloto {

	@XmlElementRef(name = "apellidos", namespace = "http://schemas.datacontract.org/2004/07/FEService.Input", type = JAXBElement.class)
	protected JAXBElement<String> apellidos;
	@XmlElementRef(name = "nombres", namespace = "http://schemas.datacontract.org/2004/07/FEService.Input", type = JAXBElement.class)
	protected JAXBElement<String> nombres;
	@XmlElementRef(name = "numeroDocumento", namespace = "http://schemas.datacontract.org/2004/07/FEService.Input", type = JAXBElement.class)
	protected JAXBElement<String> numeroDocumento;
	@XmlElementRef(name = "numeroLicencia", namespace = "http://schemas.datacontract.org/2004/07/FEService.Input", type = JAXBElement.class)
	protected JAXBElement<String> numeroLicencia;
	@XmlElementRef(name = "tipoDocumento", namespace = "http://schemas.datacontract.org/2004/07/FEService.Input", type = JAXBElement.class)
	protected JAXBElement<String> tipoDocumento;
	@XmlElementRef(name = "titulo", namespace = "http://schemas.datacontract.org/2004/07/FEService.Input", type = JAXBElement.class)
	protected JAXBElement<String> titulo;

	/**
	 * Gets the value of the apellidos property.
	 * 
	 * @return possible object is {@link JAXBElement }{@code <}{@link String
	 *         }{@code >}
	 * 
	 */
	public JAXBElement<String> getApellidos() {
		return apellidos;
	}

	/**
	 * Sets the value of the apellidos property.
	 * 
	 * @param value allowed object is {@link JAXBElement }{@code <}{@link String
	 *              }{@code >}
	 * 
	 */
	public void setApellidos(JAXBElement<String> value) {
		this.apellidos = ((JAXBElement<String>) value);
	}

	/**
	 * Gets the value of the nombres property.
	 * 
	 * @return possible object is {@link JAXBElement }{@code <}{@link String
	 *         }{@code >}
	 * 
	 */
	public JAXBElement<String> getNombres() {
		return nombres;
	}

	/**
	 * Sets the value of the nombres property.
	 * 
	 * @param value allowed object is {@link JAXBElement }{@code <}{@link String
	 *              }{@code >}
	 * 
	 */
	public void setNombres(JAXBElement<String> value) {
		this.nombres = ((JAXBElement<String>) value);
	}

	/**
	 * Gets the value of the numeroDocumento property.
	 * 
	 * @return possible object is {@link JAXBElement }{@code <}{@link String
	 *         }{@code >}
	 * 
	 */
	public JAXBElement<String> getNumeroDocumento() {
		return numeroDocumento;
	}

	/**
	 * Sets the value of the numeroDocumento property.
	 * 
	 * @param value allowed object is {@link JAXBElement }{@code <}{@link String
	 *              }{@code >}
	 * 
	 */
	public void setNumeroDocumento(JAXBElement<String> value) {
		this.numeroDocumento = ((JAXBElement<String>) value);
	}

	/**
	 * Gets the value of the numeroLicencia property.
	 * 
	 * @return possible object is {@link JAXBElement }{@code <}{@link String
	 *         }{@code >}
	 * 
	 */
	public JAXBElement<String> getNumeroLicencia() {
		return numeroLicencia;
	}

	/**
	 * Sets the value of the numeroLicencia property.
	 * 
	 * @param value allowed object is {@link JAXBElement }{@code <}{@link String
	 *              }{@code >}
	 * 
	 */
	public void setNumeroLicencia(JAXBElement<String> value) {
		this.numeroLicencia = ((JAXBElement<String>) value);
	}

	/**
	 * Gets the value of the tipoDocumento property.
	 * 
	 * @return possible object is {@link JAXBElement }{@code <}{@link String
	 *         }{@code >}
	 * 
	 */
	public JAXBElement<String> getTipoDocumento() {
		return tipoDocumento;
	}

	/**
	 * Sets the value of the tipoDocumento property.
	 * 
	 * @param value allowed object is {@link JAXBElement }{@code <}{@link String
	 *              }{@code >}
	 * 
	 */
	public void setTipoDocumento(JAXBElement<String> value) {
		this.tipoDocumento = ((JAXBElement<String>) value);
	}

	/**
	 * Gets the value of the titulo property.
	 * 
	 * @return possible object is {@link JAXBElement }{@code <}{@link String
	 *         }{@code >}
	 * 
	 */
	public JAXBElement<String> getTitulo() {
		return titulo;
	}

	/**
	 * Sets the value of the titulo property.
	 * 
	 * @param value allowed object is {@link JAXBElement }{@code <}{@link String
	 *              }{@code >}
	 * 
	 */
	public void setTitulo(JAXBElement<String> value) {
		this.titulo = ((JAXBElement<String>) value);
	}

}
