
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
 *         &lt;element name="isPasajes" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *         &lt;element name="tipoComprobante" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="serie" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="correlativo" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="strRucEmpresa" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 *
 *
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = { "token", "isPasajes", "tipoComprobante", "serie", "correlativo", "strRucEmpresa" })
@XmlRootElement(name = "getRepresentacionImpresa")
public class GetRepresentacionImpresa {

	@XmlElementRef(name = "token", namespace = "http://tempuri.org/", type = JAXBElement.class)
	protected JAXBElement<String> token;
	protected Boolean isPasajes;
	@XmlElementRef(name = "tipoComprobante", namespace = "http://tempuri.org/", type = JAXBElement.class)
	protected JAXBElement<String> tipoComprobante;
	@XmlElementRef(name = "serie", namespace = "http://tempuri.org/", type = JAXBElement.class)
	protected JAXBElement<String> serie;
	@XmlElementRef(name = "correlativo", namespace = "http://tempuri.org/", type = JAXBElement.class)
	protected JAXBElement<String> correlativo;
	@XmlElementRef(name = "strRucEmpresa", namespace = "http://tempuri.org/", type = JAXBElement.class)
	protected JAXBElement<String> strRucEmpresa;

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
	 * Gets the value of the isPasajes property.
	 *
	 * @return possible object is {@link Boolean }
	 *
	 */
	public Boolean isIsPasajes() {
		return isPasajes;
	}

	/**
	 * Sets the value of the isPasajes property.
	 *
	 * @param value allowed object is {@link Boolean }
	 *
	 */
	public void setIsPasajes(Boolean value) {
		this.isPasajes = value;
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
	 * Gets the value of the strRucEmpresa property.
	 *
	 * @return possible object is {@link JAXBElement }{@code <}{@link String
	 *         }{@code >}
	 *
	 */
	public JAXBElement<String> getStrRucEmpresa() {
		return strRucEmpresa;
	}

	/**
	 * Sets the value of the strRucEmpresa property.
	 *
	 * @param value allowed object is {@link JAXBElement }{@code <}{@link String
	 *              }{@code >}
	 *
	 */
	public void setStrRucEmpresa(JAXBElement<String> value) {
		this.strRucEmpresa = (value);
	}

}
