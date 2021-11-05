
package com.cystesoft.vyrbus.service.fe;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlType;

/**
 * <p>
 * Java class for DocumentCDR complex type.
 * 
 * <p>
 * The following schema fragment specifies the expected content contained within
 * this class.
 * 
 * <pre>
 * &lt;complexType name="DocumentCDR">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="id" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="idEnvioDato" type="{http://www.w3.org/2001/XMLSchema}long" minOccurs="0"/>
 *         &lt;element name="isResumen" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *         &lt;element name="rucEmpresa" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="ticket" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "DocumentCDR", namespace = "http://schemas.datacontract.org/2004/07/FEService.Input", propOrder = {
		"id", "idEnvioDato", "isResumen", "rucEmpresa", "ticket" })
public class DocumentCDR {

	@XmlElementRef(name = "id", namespace = "http://schemas.datacontract.org/2004/07/FEService.Input", type = JAXBElement.class)
	protected JAXBElement<String> id;
	protected Long idEnvioDato;
	protected Boolean isResumen;
	@XmlElementRef(name = "rucEmpresa", namespace = "http://schemas.datacontract.org/2004/07/FEService.Input", type = JAXBElement.class)
	protected JAXBElement<String> rucEmpresa;
	@XmlElementRef(name = "ticket", namespace = "http://schemas.datacontract.org/2004/07/FEService.Input", type = JAXBElement.class)
	protected JAXBElement<String> ticket;

	/**
	 * Gets the value of the id property.
	 * 
	 * @return possible object is {@link JAXBElement }{@code <}{@link String
	 *         }{@code >}
	 * 
	 */
	public JAXBElement<String> getId() {
		return id;
	}

	/**
	 * Sets the value of the id property.
	 * 
	 * @param value
	 *            allowed object is {@link JAXBElement }{@code <}{@link String
	 *            }{@code >}
	 * 
	 */
	public void setId(JAXBElement<String> value) {
		this.id = ((JAXBElement<String>) value);
	}

	/**
	 * Gets the value of the idEnvioDato property.
	 * 
	 * @return possible object is {@link Long }
	 * 
	 */
	public Long getIdEnvioDato() {
		return idEnvioDato;
	}

	/**
	 * Sets the value of the idEnvioDato property.
	 * 
	 * @param value
	 *            allowed object is {@link Long }
	 * 
	 */
	public void setIdEnvioDato(Long value) {
		this.idEnvioDato = value;
	}

	/**
	 * Gets the value of the isResumen property.
	 * 
	 * @return possible object is {@link Boolean }
	 * 
	 */
	public Boolean isIsResumen() {
		return isResumen;
	}

	/**
	 * Sets the value of the isResumen property.
	 * 
	 * @param value
	 *            allowed object is {@link Boolean }
	 * 
	 */
	public void setIsResumen(Boolean value) {
		this.isResumen = value;
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
	 * Gets the value of the ticket property.
	 * 
	 * @return possible object is {@link JAXBElement }{@code <}{@link String
	 *         }{@code >}
	 * 
	 */
	public JAXBElement<String> getTicket() {
		return ticket;
	}

	/**
	 * Sets the value of the ticket property.
	 * 
	 * @param value
	 *            allowed object is {@link JAXBElement }{@code <}{@link String
	 *            }{@code >}
	 * 
	 */
	public void setTicket(JAXBElement<String> value) {
		this.ticket = ((JAXBElement<String>) value);
	}

}
