package cruzdelsur.ws.com.pe;

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
 *         &lt;element name="desbloquearAsientoResult" type="{http://schemas.datacontract.org/2004/07/wscruzdelsur.Entities}ResultDesbloquearAsiento" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = { "desbloquearAsientoResult" })
@XmlRootElement(name = "desbloquearAsientoResponse", namespace = "http://tempuri.org/")
public class DesbloquearAsientoResponse {

	@XmlElementRef(name = "desbloquearAsientoResult", namespace = "http://tempuri.org/", type = JAXBElement.class)
	protected JAXBElement<ResultDesbloquearAsiento> desbloquearAsientoResult;

	/**
	 * Gets the value of the desbloquearAsientoResult property.
	 * 
	 * @return possible object is {@link JAXBElement }{@code <}
	 *         {@link ResultDesbloquearAsiento }{@code >}
	 * 
	 */
	public JAXBElement<ResultDesbloquearAsiento> getDesbloquearAsientoResult() {
		return desbloquearAsientoResult;
	}

	/**
	 * Sets the value of the desbloquearAsientoResult property.
	 * 
	 * @param value
	 *            allowed object is {@link JAXBElement }{@code <}
	 *            {@link ResultDesbloquearAsiento }{@code >}
	 * 
	 */
	public void setDesbloquearAsientoResult(
			JAXBElement<ResultDesbloquearAsiento> value) {
		this.desbloquearAsientoResult = (value);
	}

}
