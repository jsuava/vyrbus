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
 *         &lt;element name="fechaProgramacion" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="localidadEmbarque" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="localidadDesembarque" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 *
 *
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = { "fechaProgramacion", "localidadEmbarque",
		"localidadDesembarque" })
@XmlRootElement(name = "obtenerHorarios", namespace = "http://tempuri.org/")
public class ObtenerHorarios {

	@XmlElementRef(name = "fechaProgramacion", namespace = "http://tempuri.org/", type = JAXBElement.class)
	protected JAXBElement<String> fechaProgramacion;
	@XmlElementRef(name = "localidadEmbarque", namespace = "http://tempuri.org/", type = JAXBElement.class)
	protected JAXBElement<String> localidadEmbarque;
	@XmlElementRef(name = "localidadDesembarque", namespace = "http://tempuri.org/", type = JAXBElement.class)
	protected JAXBElement<String> localidadDesembarque;

	/**
	 * Gets the value of the fechaProgramacion property.
	 *
	 * @return possible object is {@link JAXBElement }{@code <}{@link String }
	 *         {@code >}
	 *
	 */
	public JAXBElement<String> getFechaProgramacion() {
		return fechaProgramacion;
	}

	/**
	 * Sets the value of the fechaProgramacion property.
	 *
	 * @param value
	 *            allowed object is {@link JAXBElement }{@code <}{@link String }
	 *            {@code >}
	 *
	 */
	public void setFechaProgramacion(JAXBElement<String> value) {
		this.fechaProgramacion = (value);
	}

	/**
	 * Gets the value of the localidadEmbarque property.
	 *
	 * @return possible object is {@link JAXBElement }{@code <}{@link String }
	 *         {@code >}
	 *
	 */
	public JAXBElement<String> getLocalidadEmbarque() {
		return localidadEmbarque;
	}

	/**
	 * Sets the value of the localidadEmbarque property.
	 *
	 * @param value
	 *            allowed object is {@link JAXBElement }{@code <}{@link String }
	 *            {@code >}
	 *
	 */
	public void setLocalidadEmbarque(JAXBElement<String> value) {
		this.localidadEmbarque = (value);
	}

	/**
	 * Gets the value of the localidadDesembarque property.
	 *
	 * @return possible object is {@link JAXBElement }{@code <}{@link String }
	 *         {@code >}
	 *
	 */
	public JAXBElement<String> getLocalidadDesembarque() {
		return localidadDesembarque;
	}

	/**
	 * Sets the value of the localidadDesembarque property.
	 *
	 * @param value
	 *            allowed object is {@link JAXBElement }{@code <}{@link String }
	 *            {@code >}
	 *
	 */
	public void setLocalidadDesembarque(JAXBElement<String> value) {
		this.localidadDesembarque = (value);
	}

}
