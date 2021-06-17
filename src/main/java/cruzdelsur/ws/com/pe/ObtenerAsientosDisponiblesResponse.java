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
 *         &lt;element name="obtenerAsientosDisponiblesResult" type="{http://schemas.datacontract.org/2004/07/wscruzdelsur.Entities}ArrayOfDistribucion" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = { "obtenerAsientosDisponiblesResult" })
@XmlRootElement(name = "obtenerAsientosDisponiblesResponse", namespace = "http://tempuri.org/")
public class ObtenerAsientosDisponiblesResponse {

	@XmlElementRef(name = "obtenerAsientosDisponiblesResult", namespace = "http://tempuri.org/", type = JAXBElement.class)
	protected JAXBElement<ArrayOfDistribucion> obtenerAsientosDisponiblesResult;

	/**
	 * Gets the value of the obtenerAsientosDisponiblesResult property.
	 * 
	 * @return possible object is {@link JAXBElement }{@code <}
	 *         {@link ArrayOfDistribucion }{@code >}
	 * 
	 */
	public JAXBElement<ArrayOfDistribucion> getObtenerAsientosDisponiblesResult() {
		return obtenerAsientosDisponiblesResult;
	}

	/**
	 * Sets the value of the obtenerAsientosDisponiblesResult property.
	 * 
	 * @param value
	 *            allowed object is {@link JAXBElement }{@code <}
	 *            {@link ArrayOfDistribucion }{@code >}
	 * 
	 */
	public void setObtenerAsientosDisponiblesResult(
			JAXBElement<ArrayOfDistribucion> value) {
		this.obtenerAsientosDisponiblesResult = (value);
	}

}
