
package com.cystesoft.vyrbus.service.fe;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlType;

/**
 * <p>
 * Java class for GREDocumentoReferencia complex type.
 * 
 * <p>
 * The following schema fragment specifies the expected content contained within
 * this class.
 * 
 * <pre>
 * &lt;complexType name="GREDocumentoReferencia">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="numeroComprobante" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="numeroDocumentoRef" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="tipoComprobante" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="tipoComprobanteId" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="tipoDocumentoIdRef" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "GREDocumentoReferencia", namespace = "http://schemas.datacontract.org/2004/07/FEService.Input", propOrder = {
		"numeroComprobante", "numeroDocumentoRef", "tipoComprobante", "tipoComprobanteId", "tipoDocumentoIdRef" })
public class GREDocumentoReferencia {

	@XmlElementRef(name = "numeroComprobante", namespace = "http://schemas.datacontract.org/2004/07/FEService.Input", type = JAXBElement.class)
	protected JAXBElement<String> numeroComprobante;
	@XmlElementRef(name = "numeroDocumentoRef", namespace = "http://schemas.datacontract.org/2004/07/FEService.Input", type = JAXBElement.class)
	protected JAXBElement<String> numeroDocumentoRef;
	@XmlElementRef(name = "tipoComprobante", namespace = "http://schemas.datacontract.org/2004/07/FEService.Input", type = JAXBElement.class)
	protected JAXBElement<String> tipoComprobante;
	@XmlElementRef(name = "tipoComprobanteId", namespace = "http://schemas.datacontract.org/2004/07/FEService.Input", type = JAXBElement.class)
	protected JAXBElement<String> tipoComprobanteId;
	@XmlElementRef(name = "tipoDocumentoIdRef", namespace = "http://schemas.datacontract.org/2004/07/FEService.Input", type = JAXBElement.class)
	protected JAXBElement<String> tipoDocumentoIdRef;

	/**
	 * Gets the value of the numeroComprobante property.
	 * 
	 * @return possible object is {@link JAXBElement }{@code <}{@link String
	 *         }{@code >}
	 * 
	 */
	public JAXBElement<String> getNumeroComprobante() {
		return numeroComprobante;
	}

	/**
	 * Sets the value of the numeroComprobante property.
	 * 
	 * @param value allowed object is {@link JAXBElement }{@code <}{@link String
	 *              }{@code >}
	 * 
	 */
	public void setNumeroComprobante(JAXBElement<String> value) {
		this.numeroComprobante = ((JAXBElement<String>) value);
	}

	/**
	 * Gets the value of the numeroDocumentoRef property.
	 * 
	 * @return possible object is {@link JAXBElement }{@code <}{@link String
	 *         }{@code >}
	 * 
	 */
	public JAXBElement<String> getNumeroDocumentoRef() {
		return numeroDocumentoRef;
	}

	/**
	 * Sets the value of the numeroDocumentoRef property.
	 * 
	 * @param value allowed object is {@link JAXBElement }{@code <}{@link String
	 *              }{@code >}
	 * 
	 */
	public void setNumeroDocumentoRef(JAXBElement<String> value) {
		this.numeroDocumentoRef = ((JAXBElement<String>) value);
	}

	/**
	 * Gets the value of the tipoComprobante property.
	 * 
	 * @return possible object is {@link JAXBElement }{@code <}{@link String
	 *         }{@code >}
	 * 
	 */
	public JAXBElement<String> getTipoComprobante() {
		return tipoComprobante;
	}

	/**
	 * Sets the value of the tipoComprobante property.
	 * 
	 * @param value allowed object is {@link JAXBElement }{@code <}{@link String
	 *              }{@code >}
	 * 
	 */
	public void setTipoComprobante(JAXBElement<String> value) {
		this.tipoComprobante = ((JAXBElement<String>) value);
	}

	/**
	 * Gets the value of the tipoComprobanteId property.
	 * 
	 * @return possible object is {@link JAXBElement }{@code <}{@link String
	 *         }{@code >}
	 * 
	 */
	public JAXBElement<String> getTipoComprobanteId() {
		return tipoComprobanteId;
	}

	/**
	 * Sets the value of the tipoComprobanteId property.
	 * 
	 * @param value allowed object is {@link JAXBElement }{@code <}{@link String
	 *              }{@code >}
	 * 
	 */
	public void setTipoComprobanteId(JAXBElement<String> value) {
		this.tipoComprobanteId = ((JAXBElement<String>) value);
	}

	/**
	 * Gets the value of the tipoDocumentoIdRef property.
	 * 
	 * @return possible object is {@link JAXBElement }{@code <}{@link String
	 *         }{@code >}
	 * 
	 */
	public JAXBElement<String> getTipoDocumentoIdRef() {
		return tipoDocumentoIdRef;
	}

	/**
	 * Sets the value of the tipoDocumentoIdRef property.
	 * 
	 * @param value allowed object is {@link JAXBElement }{@code <}{@link String
	 *              }{@code >}
	 * 
	 */
	public void setTipoDocumentoIdRef(JAXBElement<String> value) {
		this.tipoDocumentoIdRef = ((JAXBElement<String>) value);
	}

}
