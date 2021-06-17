
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
 *         &lt;element name="getRepresentacionImpresaResult" type="{http://schemas.datacontract.org/2004/07/FEService.Util}Result" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = { "getRepresentacionImpresaResult" })
@XmlRootElement(name = "getRepresentacionImpresaResponse")
public class GetRepresentacionImpresaResponse {

	@XmlElementRef(name = "getRepresentacionImpresaResult", namespace = "http://tempuri.org/", type = JAXBElement.class)
	protected JAXBElement<Result> getRepresentacionImpresaResult;

	/**
	 * Gets the value of the getRepresentacionImpresaResult property.
	 * 
	 * @return possible object is {@link JAXBElement }{@code <}{@link Result
	 *         }{@code >}
	 * 
	 */
	public JAXBElement<Result> getGetRepresentacionImpresaResult() {
		return getRepresentacionImpresaResult;
	}

	/**
	 * Sets the value of the getRepresentacionImpresaResult property.
	 * 
	 * @param value
	 *            allowed object is {@link JAXBElement }{@code <}{@link Result
	 *            }{@code >}
	 * 
	 */
	public void setGetRepresentacionImpresaResult(JAXBElement<Result> value) {
		this.getRepresentacionImpresaResult = (value);
	}

}
