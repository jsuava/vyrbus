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
 *         &lt;element name="obtenerHorariosResult" type="{http://schemas.datacontract.org/2004/07/wscruzdelsur.Entities}ArrayOfHorario" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 *
 *
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = { "obtenerHorariosResult" })
@XmlRootElement(name = "obtenerHorariosResponse", namespace = "http://tempuri.org/")
public class ObtenerHorariosResponse {

	@XmlElementRef(name = "obtenerHorariosResult", namespace = "http://tempuri.org/", type = JAXBElement.class)
	protected JAXBElement<ArrayOfHorario> obtenerHorariosResult;

	/**
	 * Gets the value of the obtenerHorariosResult property.
	 *
	 * @return possible object is {@link JAXBElement }{@code <}
	 *         {@link ArrayOfHorario }{@code >}
	 *
	 */
	public JAXBElement<ArrayOfHorario> getObtenerHorariosResult() {
		return obtenerHorariosResult;
	}

	/**
	 * Sets the value of the obtenerHorariosResult property.
	 *
	 * @param value
	 *            allowed object is {@link JAXBElement }{@code <}
	 *            {@link ArrayOfHorario }{@code >}
	 *
	 */
	public void setObtenerHorariosResult(JAXBElement<ArrayOfHorario> value) {
		this.obtenerHorariosResult = (value);
	}

}
