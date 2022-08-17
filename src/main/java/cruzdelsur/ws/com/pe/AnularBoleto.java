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
 *         &lt;element name="serie" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="numero" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 *
 *
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = { "serie", "numero" })
@XmlRootElement(name = "anularBoleto", namespace = "http://tempuri.org/")
public class AnularBoleto {

	@XmlElementRef(name = "serie", namespace = "http://tempuri.org/", type = JAXBElement.class)
	protected JAXBElement<String> serie;
	@XmlElementRef(name = "numero", namespace = "http://tempuri.org/", type = JAXBElement.class)
	protected JAXBElement<String> numero;

	/**
	 * Gets the value of the serie property.
	 *
	 * @return possible object is {@link JAXBElement }{@code <}{@link String }
	 *         {@code >}
	 *
	 */
	public JAXBElement<String> getSerie() {
		return serie;
	}

	/**
	 * Sets the value of the serie property.
	 *
	 * @param value
	 *            allowed object is {@link JAXBElement }{@code <}{@link String }
	 *            {@code >}
	 *
	 */
	public void setSerie(JAXBElement<String> value) {
		this.serie = (value);
	}

	/**
	 * Gets the value of the numero property.
	 *
	 * @return possible object is {@link JAXBElement }{@code <}{@link String }
	 *         {@code >}
	 *
	 */
	public JAXBElement<String> getNumero() {
		return numero;
	}

	/**
	 * Sets the value of the numero property.
	 *
	 * @param value
	 *            allowed object is {@link JAXBElement }{@code <}{@link String }
	 *            {@code >}
	 *
	 */
	public void setNumero(JAXBElement<String> value) {
		this.numero = (value);
	}

}
