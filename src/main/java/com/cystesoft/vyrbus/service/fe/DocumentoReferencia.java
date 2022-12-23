
package com.cystesoft.vyrbus.service.fe;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlType;

/**
 * <p>
 * Java class for DocumentoReferencia complex type.
 * 
 * <p>
 * The following schema fragment specifies the expected content contained within
 * this class.
 * 
 * <pre>
 * &lt;complexType name="DocumentoReferencia">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="FechaDocumento" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="NumeroDocumento" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="TipoComprobante" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "DocumentoReferencia", namespace = "http://schemas.datacontract.org/2004/07/FEService.Input", propOrder = {
		"fechaDocumento", "numeroDocumento", "tipoComprobante" })
public class DocumentoReferencia {

	@XmlElementRef(name = "FechaDocumento", namespace = "http://schemas.datacontract.org/2004/07/FEService.Input", type = JAXBElement.class)
	protected JAXBElement<String> fechaDocumento;
	@XmlElementRef(name = "NumeroDocumento", namespace = "http://schemas.datacontract.org/2004/07/FEService.Input", type = JAXBElement.class)
	protected JAXBElement<String> numeroDocumento;
	@XmlElementRef(name = "TipoComprobante", namespace = "http://schemas.datacontract.org/2004/07/FEService.Input", type = JAXBElement.class)
	protected JAXBElement<String> tipoComprobante;

	/**
	 * Gets the value of the fechaDocumento property.
	 * 
	 * @return possible object is {@link JAXBElement }{@code <}{@link String
	 *         }{@code >}
	 * 
	 */
	public JAXBElement<String> getFechaDocumento() {
		return fechaDocumento;
	}

	/**
	 * Sets the value of the fechaDocumento property.
	 * 
	 * @param value allowed object is {@link JAXBElement }{@code <}{@link String
	 *              }{@code >}
	 * 
	 */
	public void setFechaDocumento(JAXBElement<String> value) {
		this.fechaDocumento = ((JAXBElement<String>) value);
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

}
