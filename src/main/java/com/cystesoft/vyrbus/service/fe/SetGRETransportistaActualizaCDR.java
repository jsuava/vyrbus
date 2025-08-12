
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
 *         &lt;element name="listGreTransportista" type="{http://schemas.datacontract.org/2004/07/FEService.Input}ArrayOfGRE" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = { "token", "listGreTransportista" })
@XmlRootElement(name = "setGRETransportistaActualizaCDR")
public class SetGRETransportistaActualizaCDR {

	@XmlElementRef(name = "token", namespace = "http://tempuri.org/", type = JAXBElement.class)
	protected JAXBElement<String> token;
	@XmlElementRef(name = "listGreTransportista", namespace = "http://tempuri.org/", type = JAXBElement.class)
	protected JAXBElement<ArrayOfGRE> listGreTransportista;

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
		this.token = ((JAXBElement<String>) value);
	}

	/**
	 * Gets the value of the listGreTransportista property.
	 * 
	 * @return possible object is {@link JAXBElement }{@code <}{@link ArrayOfGRE
	 *         }{@code >}
	 * 
	 */
	public JAXBElement<ArrayOfGRE> getListGreTransportista() {
		return listGreTransportista;
	}

	/**
	 * Sets the value of the listGreTransportista property.
	 * 
	 * @param value allowed object is {@link JAXBElement }{@code <}{@link ArrayOfGRE
	 *              }{@code >}
	 * 
	 */
	public void setListGreTransportista(JAXBElement<ArrayOfGRE> value) {
		this.listGreTransportista = ((JAXBElement<ArrayOfGRE>) value);
	}

}
