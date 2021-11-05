
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
 *         &lt;element name="nota" type="{http://schemas.datacontract.org/2004/07/FEService.Input}Nota" minOccurs="0"/>
 *         &lt;element name="venta" type="{http://schemas.datacontract.org/2004/07/FEService.Input}Venta" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = { "token", "nota", "venta" })
@XmlRootElement(name = "setNotaVenta")
public class SetNotaVenta {

	@XmlElementRef(name = "token", namespace = "http://tempuri.org/", type = JAXBElement.class)
	protected JAXBElement<String> token;
	@XmlElementRef(name = "nota", namespace = "http://tempuri.org/", type = JAXBElement.class)
	protected JAXBElement<Nota> nota;
	@XmlElementRef(name = "venta", namespace = "http://tempuri.org/", type = JAXBElement.class)
	protected JAXBElement<Venta> venta;

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
	 * @param value
	 *            allowed object is {@link JAXBElement }{@code <}{@link String
	 *            }{@code >}
	 * 
	 */
	public void setToken(JAXBElement<String> value) {
		this.token = ((JAXBElement<String>) value);
	}

	/**
	 * Gets the value of the nota property.
	 * 
	 * @return possible object is {@link JAXBElement }{@code <}{@link Nota
	 *         }{@code >}
	 * 
	 */
	public JAXBElement<Nota> getNota() {
		return nota;
	}

	/**
	 * Sets the value of the nota property.
	 * 
	 * @param value
	 *            allowed object is {@link JAXBElement }{@code <}{@link Nota
	 *            }{@code >}
	 * 
	 */
	public void setNota(JAXBElement<Nota> value) {
		this.nota = ((JAXBElement<Nota>) value);
	}

	/**
	 * Gets the value of the venta property.
	 * 
	 * @return possible object is {@link JAXBElement }{@code <}{@link Venta
	 *         }{@code >}
	 * 
	 */
	public JAXBElement<Venta> getVenta() {
		return venta;
	}

	/**
	 * Sets the value of the venta property.
	 * 
	 * @param value
	 *            allowed object is {@link JAXBElement }{@code <}{@link Venta
	 *            }{@code >}
	 * 
	 */
	public void setVenta(JAXBElement<Venta> value) {
		this.venta = ((JAXBElement<Venta>) value);
	}

}
