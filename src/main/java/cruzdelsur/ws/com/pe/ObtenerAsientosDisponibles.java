package cruzdelsur.ws.com.pe;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
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
 *         &lt;element name="servicioLlave" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="programacionLlave" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="numeroServicio" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="agenciaEmbarqueLlave" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="agenciaDesembarqueLlave" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="unidadLlave" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = { "codigoRuta", "servicioLlave",
		"programacionLlave", "numeroServicio", "agenciaEmbarqueLlave",
		"agenciaDesembarqueLlave", "unidadLlave" })
@XmlRootElement(name = "obtenerAsientosDisponibles", namespace = "http://tempuri.org/")
public class ObtenerAsientosDisponibles {

	@XmlElementRef(name = "codigoRuta", namespace = "http://tempuri.org/", type = JAXBElement.class)
	protected JAXBElement<String> codigoRuta;
	@XmlElementRef(name = "servicioLlave", namespace = "http://tempuri.org/", type = JAXBElement.class)
	protected JAXBElement<String> servicioLlave;
	@XmlElementRef(name = "programacionLlave", namespace = "http://tempuri.org/", type = JAXBElement.class)
	protected JAXBElement<String> programacionLlave;
	@XmlElement(namespace = "http://tempuri.org/")
	protected Integer numeroServicio;
	@XmlElementRef(name = "agenciaEmbarqueLlave", namespace = "http://tempuri.org/", type = JAXBElement.class)
	protected JAXBElement<String> agenciaEmbarqueLlave;
	@XmlElementRef(name = "agenciaDesembarqueLlave", namespace = "http://tempuri.org/", type = JAXBElement.class)
	protected JAXBElement<String> agenciaDesembarqueLlave;
	@XmlElementRef(name = "unidadLlave", namespace = "http://tempuri.org/", type = JAXBElement.class)
	protected JAXBElement<String> unidadLlave;

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
	 * Gets the value of the numeroServicio property.
	 * 
	 * @return possible object is {@link Integer }
	 * 
	 */
	public Integer getNumeroServicio() {
		return numeroServicio;
	}

	/**
	 * Sets the value of the numeroServicio property.
	 * 
	 * @param value
	 *            allowed object is {@link Integer }
	 * 
	 */
	public void setNumeroServicio(Integer value) {
		this.numeroServicio = value;
	}

	/**
	 * Gets the value of the agenciaEmbarqueLlave property.
	 * 
	 * @return possible object is {@link JAXBElement }{@code <}{@link String }
	 *         {@code >}
	 * 
	 */
	public JAXBElement<String> getAgenciaEmbarqueLlave() {
		return agenciaEmbarqueLlave;
	}

	/**
	 * Sets the value of the agenciaEmbarqueLlave property.
	 * 
	 * @param value
	 *            allowed object is {@link JAXBElement }{@code <}{@link String }
	 *            {@code >}
	 * 
	 */
	public void setAgenciaEmbarqueLlave(JAXBElement<String> value) {
		this.agenciaEmbarqueLlave = (value);
	}

	/**
	 * Gets the value of the agenciaDesembarqueLlave property.
	 * 
	 * @return possible object is {@link JAXBElement }{@code <}{@link String }
	 *         {@code >}
	 * 
	 */
	public JAXBElement<String> getAgenciaDesembarqueLlave() {
		return agenciaDesembarqueLlave;
	}

	/**
	 * Sets the value of the agenciaDesembarqueLlave property.
	 * 
	 * @param value
	 *            allowed object is {@link JAXBElement }{@code <}{@link String }
	 *            {@code >}
	 * 
	 */
	public void setAgenciaDesembarqueLlave(JAXBElement<String> value) {
		this.agenciaDesembarqueLlave = (value);
	}

	/**
	 * Gets the value of the unidadLlave property.
	 * 
	 * @return possible object is {@link JAXBElement }{@code <}{@link String }
	 *         {@code >}
	 * 
	 */
	public JAXBElement<String> getUnidadLlave() {
		return unidadLlave;
	}

	/**
	 * Sets the value of the unidadLlave property.
	 * 
	 * @param value
	 *            allowed object is {@link JAXBElement }{@code <}{@link String }
	 *            {@code >}
	 * 
	 */
	public void setUnidadLlave(JAXBElement<String> value) {
		this.unidadLlave = (value);
	}

}
