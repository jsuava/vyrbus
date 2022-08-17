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
 *         &lt;element name="codigoRuta" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="localidadEmbarque" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="localidadDesembarque" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="numeroServicio" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="programacionLlave" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="servicioLlave" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="codigoAsiento" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="nivelUnidad" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 *
 *
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = { "codigoRuta", "localidadEmbarque",
		"localidadDesembarque", "numeroServicio", "programacionLlave",
		"servicioLlave", "codigoAsiento", "nivelUnidad" })
@XmlRootElement(name = "bloquearAsiento", namespace = "http://tempuri.org/")
public class BloquearAsiento {

	@XmlElementRef(name = "codigoRuta", namespace = "http://tempuri.org/", type = JAXBElement.class)
	protected JAXBElement<String> codigoRuta;
	@XmlElementRef(name = "localidadEmbarque", namespace = "http://tempuri.org/", type = JAXBElement.class)
	protected JAXBElement<String> localidadEmbarque;
	@XmlElementRef(name = "localidadDesembarque", namespace = "http://tempuri.org/", type = JAXBElement.class)
	protected JAXBElement<String> localidadDesembarque;
	@XmlElementRef(name = "numeroServicio", namespace = "http://tempuri.org/", type = JAXBElement.class)
	protected JAXBElement<String> numeroServicio;
	@XmlElementRef(name = "programacionLlave", namespace = "http://tempuri.org/", type = JAXBElement.class)
	protected JAXBElement<String> programacionLlave;
	@XmlElementRef(name = "servicioLlave", namespace = "http://tempuri.org/", type = JAXBElement.class)
	protected JAXBElement<String> servicioLlave;
	@XmlElementRef(name = "codigoAsiento", namespace = "http://tempuri.org/", type = JAXBElement.class)
	protected JAXBElement<String> codigoAsiento;
	@XmlElementRef(name = "nivelUnidad", namespace = "http://tempuri.org/", type = JAXBElement.class)
	protected JAXBElement<String> nivelUnidad;

	/**
	 * Gets the value of the codigoRuta property.
	 *
	 * @return possible object is {@link JAXBElement }{@code <}{@link String }
	 *         {@code >}
	 *
	 */
	public JAXBElement<String> getCodigoRuta() {
		return codigoRuta;
	}

	/**
	 * Sets the value of the codigoRuta property.
	 *
	 * @param value
	 *            allowed object is {@link JAXBElement }{@code <}{@link String }
	 *            {@code >}
	 *
	 */
	public void setCodigoRuta(JAXBElement<String> value) {
		this.codigoRuta = (value);
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

	/**
	 * Gets the value of the numeroServicio property.
	 *
	 * @return possible object is {@link JAXBElement }{@code <}{@link String }
	 *         {@code >}
	 *
	 */
	public JAXBElement<String> getNumeroServicio() {
		return numeroServicio;
	}

	/**
	 * Sets the value of the numeroServicio property.
	 *
	 * @param value
	 *            allowed object is {@link JAXBElement }{@code <}{@link String }
	 *            {@code >}
	 *
	 */
	public void setNumeroServicio(JAXBElement<String> value) {
		this.numeroServicio = (value);
	}

	/**
	 * Gets the value of the programacionLlave property.
	 *
	 * @return possible object is {@link JAXBElement }{@code <}{@link String }
	 *         {@code >}
	 *
	 */
	public JAXBElement<String> getProgramacionLlave() {
		return programacionLlave;
	}

	/**
	 * Sets the value of the programacionLlave property.
	 *
	 * @param value
	 *            allowed object is {@link JAXBElement }{@code <}{@link String }
	 *            {@code >}
	 *
	 */
	public void setProgramacionLlave(JAXBElement<String> value) {
		this.programacionLlave = (value);
	}

	/**
	 * Gets the value of the servicioLlave property.
	 *
	 * @return possible object is {@link JAXBElement }{@code <}{@link String }
	 *         {@code >}
	 *
	 */
	public JAXBElement<String> getServicioLlave() {
		return servicioLlave;
	}

	/**
	 * Sets the value of the servicioLlave property.
	 *
	 * @param value
	 *            allowed object is {@link JAXBElement }{@code <}{@link String }
	 *            {@code >}
	 *
	 */
	public void setServicioLlave(JAXBElement<String> value) {
		this.servicioLlave = (value);
	}

	/**
	 * Gets the value of the codigoAsiento property.
	 *
	 * @return possible object is {@link JAXBElement }{@code <}{@link String }
	 *         {@code >}
	 *
	 */
	public JAXBElement<String> getCodigoAsiento() {
		return codigoAsiento;
	}

	/**
	 * Sets the value of the codigoAsiento property.
	 *
	 * @param value
	 *            allowed object is {@link JAXBElement }{@code <}{@link String }
	 *            {@code >}
	 *
	 */
	public void setCodigoAsiento(JAXBElement<String> value) {
		this.codigoAsiento = (value);
	}

	/**
	 * Gets the value of the nivelUnidad property.
	 *
	 * @return possible object is {@link JAXBElement }{@code <}{@link String }
	 *         {@code >}
	 *
	 */
	public JAXBElement<String> getNivelUnidad() {
		return nivelUnidad;
	}

	/**
	 * Sets the value of the nivelUnidad property.
	 *
	 * @param value
	 *            allowed object is {@link JAXBElement }{@code <}{@link String }
	 *            {@code >}
	 *
	 */
	public void setNivelUnidad(JAXBElement<String> value) {
		this.nivelUnidad = (value);
	}

}
