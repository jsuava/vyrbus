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
 *         &lt;element name="anularBoletoResult" type="{http://schemas.datacontract.org/2004/07/wscruzdelsur.Entities}ResultAnularBoleto" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = { "anularBoletoResult" })
@XmlRootElement(name = "anularBoletoResponse", namespace = "http://tempuri.org/")
public class AnularBoletoResponse {

	@XmlElementRef(name = "anularBoletoResult", namespace = "http://tempuri.org/", type = JAXBElement.class)
	protected JAXBElement<ResultAnularBoleto> anularBoletoResult;

	/**
	 * Gets the value of the anularBoletoResult property.
	 * 
	 * @return possible object is {@link JAXBElement }{@code <}
	 *         {@link ResultAnularBoleto }{@code >}
	 * 
	 */
	public JAXBElement<ResultAnularBoleto> getAnularBoletoResult() {
		return anularBoletoResult;
	}

	/**
	 * Sets the value of the anularBoletoResult property.
	 * 
	 * @param value
	 *            allowed object is {@link JAXBElement }{@code <}
	 *            {@link ResultAnularBoleto }{@code >}
	 * 
	 */
	public void setAnularBoletoResult(JAXBElement<ResultAnularBoleto> value) {
		this.anularBoletoResult = (value);
	}

}
