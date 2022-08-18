
package com.cystesoft.vyrbus.service.fe;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

/**
 * <p>
 * Java class for anonymous complex type.
 *
 * <p>
 * The following schema fragment specifies the expected content contained within
 * this class.
 *
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="token" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="tipoComprobante" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="serie" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="correlativo" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="rucEmpresa" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 *
 *
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = { "token", "tipoComprobante", "serie", "correlativo", "rucEmpresa" })
@XmlRootElement(name = "buscarDetalleComprobante")
public class BuscarDetalleComprobante {

	@XmlElementRef(name = "token", namespace = "http://tempuri.org/", type = JAXBElement.class)
	protected JAXBElement<String> token;
	@XmlElementRef(name = "tipoComprobante", namespace = "http://tempuri.org/", type = JAXBElement.class)
	protected JAXBElement<String> tipoComprobante;
	@XmlElementRef(name = "serie", namespace = "http://tempuri.org/", type = JAXBElement.class)
	protected JAXBElement<String> serie;
	@XmlElementRef(name = "correlativo", namespace = "http://tempuri.org/", type = JAXBElement.class)
	protected JAXBElement<String> correlativo;
	@XmlElementRef(name = "rucEmpresa", namespace = "http://tempuri.org/", type = JAXBElement.class)
	protected JAXBElement<String> rucEmpresa;

	/**
	 * Gets the value of the token property.
	 *
	 * @return possible object is {@link JAXBElement }{@code <}{@link String
	 *         }{@code >}
	 *
	 */
	public JAXBElement<String> getToken() {
		return token;
	}

	/**
	 * Sets the value of the token property.
	 *
	 * @param value allowed object is {@link JAXBElement }{@code <}{@link String
	 *              }{@code >}
	 *
	 */
	public void setToken(JAXBElement<String> value) {
		this.token = (value);
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
		this.tipoComprobante = (value);
	}

	/**
	 * Gets the value of the serie property.
	 *
	 * @return possible object is {@link JAXBElement }{@code <}{@link String
	 *         }{@code >}
	 *
	 */
	public JAXBElement<String> getSerie() {
		return serie;
	}

	/**
	 * Sets the value of the serie property.
	 *
	 * @param value allowed object is {@link JAXBElement }{@code <}{@link String
	 *              }{@code >}
	 *
	 */
	public void setSerie(JAXBElement<String> value) {
		this.serie = (value);
	}

	/**
	 * Gets the value of the correlativo property.
	 *
	 * @return possible object is {@link JAXBElement }{@code <}{@link String
	 *         }{@code >}
	 *
	 */
	public JAXBElement<String> getCorrelativo() {
		return correlativo;
	}

	/**
	 * Sets the value of the correlativo property.
	 *
	 * @param value allowed object is {@link JAXBElement }{@code <}{@link String
	 *              }{@code >}
	 *
	 */
	public void setCorrelativo(JAXBElement<String> value) {
		this.correlativo = (value);
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
	 * @param value allowed object is {@link JAXBElement }{@code <}{@link String
	 *              }{@code >}
	 *
	 */
	public void setRucEmpresa(JAXBElement<String> value) {
		this.rucEmpresa = (value);
	}

}
