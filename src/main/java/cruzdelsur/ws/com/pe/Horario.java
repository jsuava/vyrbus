package cruzdelsur.ws.com.pe;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlType;

/**
 * <p>
 * Java class for Horario complex type.
 *
 * <p>
 * The following schema fragment specifies the expected content contained within
 * this class.
 *
 * <pre>
 * &lt;complexType name="Horario">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="agenciaDesembarque1" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="agenciaDesembarque2" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="agenciaDesembarqueLlave1" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="agenciaDesembarqueLlave2" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="agenciaEmbarque1" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="agenciaEmbarque2" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="agenciaEmbarqueLlave1" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="agenciaEmbarqueLlave2" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="aplicarPromocion" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="asientoDisponibleInsuperable" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="asientoDisponibleOcasion" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="asientoDisponiblePrimerPiso" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="asientoDisponibleSegundoPiso" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="codigoRuta" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="direccionDesembarque1" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="direccionDesembarque2" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="direccionEmbarque1" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="direccionEmbarque2" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="fechaHoraDesembarque1" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="fechaHoraDesembarque2" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="fechaHoraEmbarque1" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="fechaHoraEmbarque2" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="menus" type="{http://schemas.datacontract.org/2004/07/wscruzdelsur.Entities}ArrayOfGeneric" minOccurs="0"/>
 *         &lt;element name="monedaDescripcion" type="{http://www.w3.org/2001/XMLSchema}double" minOccurs="0"/>
 *         &lt;element name="numeroServicio" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="programacionLlave" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="ruta" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="rutaInternacional" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="servicio" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="servicioLlave" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="tarifaOcasion" type="{http://www.w3.org/2001/XMLSchema}double" minOccurs="0"/>
 *         &lt;element name="tarifaPrimerPiso" type="{http://www.w3.org/2001/XMLSchema}double" minOccurs="0"/>
 *         &lt;element name="tarifaSegundoPiso" type="{http://www.w3.org/2001/XMLSchema}double" minOccurs="0"/>
 *         &lt;element name="telefonoDesembarque1" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="telefonoDesembarque2" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="telefonoEmbarque1" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="telefonoEmbarque2" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="tiempoViaje" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
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
@XmlType(name = "Horario", propOrder = { "agenciaDesembarque1",
		"agenciaDesembarque2", "agenciaDesembarqueLlave1",
		"agenciaDesembarqueLlave2", "agenciaEmbarque1", "agenciaEmbarque2",
		"agenciaEmbarqueLlave1", "agenciaEmbarqueLlave2", "aplicarPromocion",
		"asientoDisponibleInsuperable", "asientoDisponibleOcasion",
		"asientoDisponiblePrimerPiso", "asientoDisponibleSegundoPiso",
		"codigoRuta", "direccionDesembarque1", "direccionDesembarque2",
		"direccionEmbarque1", "direccionEmbarque2", "fechaHoraDesembarque1",
		"fechaHoraDesembarque2", "fechaHoraEmbarque1", "fechaHoraEmbarque2",
		"menus", "monedaDescripcion", "numeroServicio", "programacionLlave",
		"ruta", "rutaInternacional", "servicio", "servicioLlave",
		"tarifaOcasion", "tarifaPrimerPiso", "tarifaSegundoPiso",
		"telefonoDesembarque1", "telefonoDesembarque2", "telefonoEmbarque1",
		"telefonoEmbarque2", "tiempoViaje", "unidadLlave" })
public class Horario {

	@XmlElementRef(name = "agenciaDesembarque1", namespace = "http://schemas.datacontract.org/2004/07/wscruzdelsur.Entities", type = JAXBElement.class)
	protected JAXBElement<String> agenciaDesembarque1;
	@XmlElementRef(name = "agenciaDesembarque2", namespace = "http://schemas.datacontract.org/2004/07/wscruzdelsur.Entities", type = JAXBElement.class)
	protected JAXBElement<String> agenciaDesembarque2;
	@XmlElementRef(name = "agenciaDesembarqueLlave1", namespace = "http://schemas.datacontract.org/2004/07/wscruzdelsur.Entities", type = JAXBElement.class)
	protected JAXBElement<String> agenciaDesembarqueLlave1;
	@XmlElementRef(name = "agenciaDesembarqueLlave2", namespace = "http://schemas.datacontract.org/2004/07/wscruzdelsur.Entities", type = JAXBElement.class)
	protected JAXBElement<String> agenciaDesembarqueLlave2;
	@XmlElementRef(name = "agenciaEmbarque1", namespace = "http://schemas.datacontract.org/2004/07/wscruzdelsur.Entities", type = JAXBElement.class)
	protected JAXBElement<String> agenciaEmbarque1;
	@XmlElementRef(name = "agenciaEmbarque2", namespace = "http://schemas.datacontract.org/2004/07/wscruzdelsur.Entities", type = JAXBElement.class)
	protected JAXBElement<String> agenciaEmbarque2;
	@XmlElementRef(name = "agenciaEmbarqueLlave1", namespace = "http://schemas.datacontract.org/2004/07/wscruzdelsur.Entities", type = JAXBElement.class)
	protected JAXBElement<String> agenciaEmbarqueLlave1;
	@XmlElementRef(name = "agenciaEmbarqueLlave2", namespace = "http://schemas.datacontract.org/2004/07/wscruzdelsur.Entities", type = JAXBElement.class)
	protected JAXBElement<String> agenciaEmbarqueLlave2;
	@XmlElementRef(name = "aplicarPromocion", namespace = "http://schemas.datacontract.org/2004/07/wscruzdelsur.Entities", type = JAXBElement.class)
	protected JAXBElement<String> aplicarPromocion;
	protected Integer asientoDisponibleInsuperable;
	protected Integer asientoDisponibleOcasion;
	protected Integer asientoDisponiblePrimerPiso;
	protected Integer asientoDisponibleSegundoPiso;
	@XmlElementRef(name = "codigoRuta", namespace = "http://schemas.datacontract.org/2004/07/wscruzdelsur.Entities", type = JAXBElement.class)
	protected JAXBElement<String> codigoRuta;
	@XmlElementRef(name = "direccionDesembarque1", namespace = "http://schemas.datacontract.org/2004/07/wscruzdelsur.Entities", type = JAXBElement.class)
	protected JAXBElement<String> direccionDesembarque1;
	@XmlElementRef(name = "direccionDesembarque2", namespace = "http://schemas.datacontract.org/2004/07/wscruzdelsur.Entities", type = JAXBElement.class)
	protected JAXBElement<String> direccionDesembarque2;
	@XmlElementRef(name = "direccionEmbarque1", namespace = "http://schemas.datacontract.org/2004/07/wscruzdelsur.Entities", type = JAXBElement.class)
	protected JAXBElement<String> direccionEmbarque1;
	@XmlElementRef(name = "direccionEmbarque2", namespace = "http://schemas.datacontract.org/2004/07/wscruzdelsur.Entities", type = JAXBElement.class)
	protected JAXBElement<String> direccionEmbarque2;
	@XmlElementRef(name = "fechaHoraDesembarque1", namespace = "http://schemas.datacontract.org/2004/07/wscruzdelsur.Entities", type = JAXBElement.class)
	protected JAXBElement<String> fechaHoraDesembarque1;
	@XmlElementRef(name = "fechaHoraDesembarque2", namespace = "http://schemas.datacontract.org/2004/07/wscruzdelsur.Entities", type = JAXBElement.class)
	protected JAXBElement<String> fechaHoraDesembarque2;
	@XmlElementRef(name = "fechaHoraEmbarque1", namespace = "http://schemas.datacontract.org/2004/07/wscruzdelsur.Entities", type = JAXBElement.class)
	protected JAXBElement<String> fechaHoraEmbarque1;
	@XmlElementRef(name = "fechaHoraEmbarque2", namespace = "http://schemas.datacontract.org/2004/07/wscruzdelsur.Entities", type = JAXBElement.class)
	protected JAXBElement<String> fechaHoraEmbarque2;
	@XmlElementRef(name = "menus", namespace = "http://schemas.datacontract.org/2004/07/wscruzdelsur.Entities", type = JAXBElement.class)
	protected JAXBElement<ArrayOfGeneric> menus;
	protected Double monedaDescripcion;
	@XmlElementRef(name = "numeroServicio", namespace = "http://schemas.datacontract.org/2004/07/wscruzdelsur.Entities", type = JAXBElement.class)
	protected JAXBElement<String> numeroServicio;
	@XmlElementRef(name = "programacionLlave", namespace = "http://schemas.datacontract.org/2004/07/wscruzdelsur.Entities", type = JAXBElement.class)
	protected JAXBElement<String> programacionLlave;
	@XmlElementRef(name = "ruta", namespace = "http://schemas.datacontract.org/2004/07/wscruzdelsur.Entities", type = JAXBElement.class)
	protected JAXBElement<String> ruta;
	@XmlElementRef(name = "rutaInternacional", namespace = "http://schemas.datacontract.org/2004/07/wscruzdelsur.Entities", type = JAXBElement.class)
	protected JAXBElement<String> rutaInternacional;
	@XmlElementRef(name = "servicio", namespace = "http://schemas.datacontract.org/2004/07/wscruzdelsur.Entities", type = JAXBElement.class)
	protected JAXBElement<String> servicio;
	@XmlElementRef(name = "servicioLlave", namespace = "http://schemas.datacontract.org/2004/07/wscruzdelsur.Entities", type = JAXBElement.class)
	protected JAXBElement<String> servicioLlave;
	protected Double tarifaOcasion;
	protected Double tarifaPrimerPiso;
	protected Double tarifaSegundoPiso;
	@XmlElementRef(name = "telefonoDesembarque1", namespace = "http://schemas.datacontract.org/2004/07/wscruzdelsur.Entities", type = JAXBElement.class)
	protected JAXBElement<String> telefonoDesembarque1;
	@XmlElementRef(name = "telefonoDesembarque2", namespace = "http://schemas.datacontract.org/2004/07/wscruzdelsur.Entities", type = JAXBElement.class)
	protected JAXBElement<String> telefonoDesembarque2;
	@XmlElementRef(name = "telefonoEmbarque1", namespace = "http://schemas.datacontract.org/2004/07/wscruzdelsur.Entities", type = JAXBElement.class)
	protected JAXBElement<String> telefonoEmbarque1;
	@XmlElementRef(name = "telefonoEmbarque2", namespace = "http://schemas.datacontract.org/2004/07/wscruzdelsur.Entities", type = JAXBElement.class)
	protected JAXBElement<String> telefonoEmbarque2;
	@XmlElementRef(name = "tiempoViaje", namespace = "http://schemas.datacontract.org/2004/07/wscruzdelsur.Entities", type = JAXBElement.class)
	protected JAXBElement<String> tiempoViaje;
	@XmlElementRef(name = "unidadLlave", namespace = "http://schemas.datacontract.org/2004/07/wscruzdelsur.Entities", type = JAXBElement.class)
	protected JAXBElement<String> unidadLlave;

	/**
	 * Gets the value of the agenciaDesembarque1 property.
	 *
	 * @return possible object is {@link JAXBElement }{@code <}{@link String }
	 *         {@code >}
	 *
	 */
	public JAXBElement<String> getAgenciaDesembarque1() {
		return agenciaDesembarque1;
	}

	/**
	 * Sets the value of the agenciaDesembarque1 property.
	 *
	 * @param value
	 *            allowed object is {@link JAXBElement }{@code <}{@link String }
	 *            {@code >}
	 *
	 */
	public void setAgenciaDesembarque1(JAXBElement<String> value) {
		this.agenciaDesembarque1 = (value);
	}

	/**
	 * Gets the value of the agenciaDesembarque2 property.
	 *
	 * @return possible object is {@link JAXBElement }{@code <}{@link String }
	 *         {@code >}
	 *
	 */
	public JAXBElement<String> getAgenciaDesembarque2() {
		return agenciaDesembarque2;
	}

	/**
	 * Sets the value of the agenciaDesembarque2 property.
	 *
	 * @param value
	 *            allowed object is {@link JAXBElement }{@code <}{@link String }
	 *            {@code >}
	 *
	 */
	public void setAgenciaDesembarque2(JAXBElement<String> value) {
		this.agenciaDesembarque2 = (value);
	}

	/**
	 * Gets the value of the agenciaDesembarqueLlave1 property.
	 *
	 * @return possible object is {@link JAXBElement }{@code <}{@link String }
	 *         {@code >}
	 *
	 */
	public JAXBElement<String> getAgenciaDesembarqueLlave1() {
		return agenciaDesembarqueLlave1;
	}

	/**
	 * Sets the value of the agenciaDesembarqueLlave1 property.
	 *
	 * @param value
	 *            allowed object is {@link JAXBElement }{@code <}{@link String }
	 *            {@code >}
	 *
	 */
	public void setAgenciaDesembarqueLlave1(JAXBElement<String> value) {
		this.agenciaDesembarqueLlave1 = (value);
	}

	/**
	 * Gets the value of the agenciaDesembarqueLlave2 property.
	 *
	 * @return possible object is {@link JAXBElement }{@code <}{@link String }
	 *         {@code >}
	 *
	 */
	public JAXBElement<String> getAgenciaDesembarqueLlave2() {
		return agenciaDesembarqueLlave2;
	}

	/**
	 * Sets the value of the agenciaDesembarqueLlave2 property.
	 *
	 * @param value
	 *            allowed object is {@link JAXBElement }{@code <}{@link String }
	 *            {@code >}
	 *
	 */
	public void setAgenciaDesembarqueLlave2(JAXBElement<String> value) {
		this.agenciaDesembarqueLlave2 = (value);
	}

	/**
	 * Gets the value of the agenciaEmbarque1 property.
	 *
	 * @return possible object is {@link JAXBElement }{@code <}{@link String }
	 *         {@code >}
	 *
	 */
	public JAXBElement<String> getAgenciaEmbarque1() {
		return agenciaEmbarque1;
	}

	/**
	 * Sets the value of the agenciaEmbarque1 property.
	 *
	 * @param value
	 *            allowed object is {@link JAXBElement }{@code <}{@link String }
	 *            {@code >}
	 *
	 */
	public void setAgenciaEmbarque1(JAXBElement<String> value) {
		this.agenciaEmbarque1 = (value);
	}

	/**
	 * Gets the value of the agenciaEmbarque2 property.
	 *
	 * @return possible object is {@link JAXBElement }{@code <}{@link String }
	 *         {@code >}
	 *
	 */
	public JAXBElement<String> getAgenciaEmbarque2() {
		return agenciaEmbarque2;
	}

	/**
	 * Sets the value of the agenciaEmbarque2 property.
	 *
	 * @param value
	 *            allowed object is {@link JAXBElement }{@code <}{@link String }
	 *            {@code >}
	 *
	 */
	public void setAgenciaEmbarque2(JAXBElement<String> value) {
		this.agenciaEmbarque2 = (value);
	}

	/**
	 * Gets the value of the agenciaEmbarqueLlave1 property.
	 *
	 * @return possible object is {@link JAXBElement }{@code <}{@link String }
	 *         {@code >}
	 *
	 */
	public JAXBElement<String> getAgenciaEmbarqueLlave1() {
		return agenciaEmbarqueLlave1;
	}

	/**
	 * Sets the value of the agenciaEmbarqueLlave1 property.
	 *
	 * @param value
	 *            allowed object is {@link JAXBElement }{@code <}{@link String }
	 *            {@code >}
	 *
	 */
	public void setAgenciaEmbarqueLlave1(JAXBElement<String> value) {
		this.agenciaEmbarqueLlave1 = (value);
	}

	/**
	 * Gets the value of the agenciaEmbarqueLlave2 property.
	 *
	 * @return possible object is {@link JAXBElement }{@code <}{@link String }
	 *         {@code >}
	 *
	 */
	public JAXBElement<String> getAgenciaEmbarqueLlave2() {
		return agenciaEmbarqueLlave2;
	}

	/**
	 * Sets the value of the agenciaEmbarqueLlave2 property.
	 *
	 * @param value
	 *            allowed object is {@link JAXBElement }{@code <}{@link String }
	 *            {@code >}
	 *
	 */
	public void setAgenciaEmbarqueLlave2(JAXBElement<String> value) {
		this.agenciaEmbarqueLlave2 = (value);
	}

	/**
	 * Gets the value of the aplicarPromocion property.
	 *
	 * @return possible object is {@link JAXBElement }{@code <}{@link String }
	 *         {@code >}
	 *
	 */
	public JAXBElement<String> getAplicarPromocion() {
		return aplicarPromocion;
	}

	/**
	 * Sets the value of the aplicarPromocion property.
	 *
	 * @param value
	 *            allowed object is {@link JAXBElement }{@code <}{@link String }
	 *            {@code >}
	 *
	 */
	public void setAplicarPromocion(JAXBElement<String> value) {
		this.aplicarPromocion = (value);
	}

	/**
	 * Gets the value of the asientoDisponibleInsuperable property.
	 *
	 * @return possible object is {@link Integer }
	 *
	 */
	public Integer getAsientoDisponibleInsuperable() {
		return asientoDisponibleInsuperable;
	}

	/**
	 * Sets the value of the asientoDisponibleInsuperable property.
	 *
	 * @param value
	 *            allowed object is {@link Integer }
	 *
	 */
	public void setAsientoDisponibleInsuperable(Integer value) {
		this.asientoDisponibleInsuperable = value;
	}

	/**
	 * Gets the value of the asientoDisponibleOcasion property.
	 *
	 * @return possible object is {@link Integer }
	 *
	 */
	public Integer getAsientoDisponibleOcasion() {
		return asientoDisponibleOcasion;
	}

	/**
	 * Sets the value of the asientoDisponibleOcasion property.
	 *
	 * @param value
	 *            allowed object is {@link Integer }
	 *
	 */
	public void setAsientoDisponibleOcasion(Integer value) {
		this.asientoDisponibleOcasion = value;
	}

	/**
	 * Gets the value of the asientoDisponiblePrimerPiso property.
	 *
	 * @return possible object is {@link Integer }
	 *
	 */
	public Integer getAsientoDisponiblePrimerPiso() {
		return asientoDisponiblePrimerPiso;
	}

	/**
	 * Sets the value of the asientoDisponiblePrimerPiso property.
	 *
	 * @param value
	 *            allowed object is {@link Integer }
	 *
	 */
	public void setAsientoDisponiblePrimerPiso(Integer value) {
		this.asientoDisponiblePrimerPiso = value;
	}

	/**
	 * Gets the value of the asientoDisponibleSegundoPiso property.
	 *
	 * @return possible object is {@link Integer }
	 *
	 */
	public Integer getAsientoDisponibleSegundoPiso() {
		return asientoDisponibleSegundoPiso;
	}

	/**
	 * Sets the value of the asientoDisponibleSegundoPiso property.
	 *
	 * @param value
	 *            allowed object is {@link Integer }
	 *
	 */
	public void setAsientoDisponibleSegundoPiso(Integer value) {
		this.asientoDisponibleSegundoPiso = value;
	}

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
	 * Gets the value of the direccionDesembarque1 property.
	 *
	 * @return possible object is {@link JAXBElement }{@code <}{@link String }
	 *         {@code >}
	 *
	 */
	public JAXBElement<String> getDireccionDesembarque1() {
		return direccionDesembarque1;
	}

	/**
	 * Sets the value of the direccionDesembarque1 property.
	 *
	 * @param value
	 *            allowed object is {@link JAXBElement }{@code <}{@link String }
	 *            {@code >}
	 *
	 */
	public void setDireccionDesembarque1(JAXBElement<String> value) {
		this.direccionDesembarque1 = (value);
	}

	/**
	 * Gets the value of the direccionDesembarque2 property.
	 *
	 * @return possible object is {@link JAXBElement }{@code <}{@link String }
	 *         {@code >}
	 *
	 */
	public JAXBElement<String> getDireccionDesembarque2() {
		return direccionDesembarque2;
	}

	/**
	 * Sets the value of the direccionDesembarque2 property.
	 *
	 * @param value
	 *            allowed object is {@link JAXBElement }{@code <}{@link String }
	 *            {@code >}
	 *
	 */
	public void setDireccionDesembarque2(JAXBElement<String> value) {
		this.direccionDesembarque2 = (value);
	}

	/**
	 * Gets the value of the direccionEmbarque1 property.
	 *
	 * @return possible object is {@link JAXBElement }{@code <}{@link String }
	 *         {@code >}
	 *
	 */
	public JAXBElement<String> getDireccionEmbarque1() {
		return direccionEmbarque1;
	}

	/**
	 * Sets the value of the direccionEmbarque1 property.
	 *
	 * @param value
	 *            allowed object is {@link JAXBElement }{@code <}{@link String }
	 *            {@code >}
	 *
	 */
	public void setDireccionEmbarque1(JAXBElement<String> value) {
		this.direccionEmbarque1 = (value);
	}

	/**
	 * Gets the value of the direccionEmbarque2 property.
	 *
	 * @return possible object is {@link JAXBElement }{@code <}{@link String }
	 *         {@code >}
	 *
	 */
	public JAXBElement<String> getDireccionEmbarque2() {
		return direccionEmbarque2;
	}

	/**
	 * Sets the value of the direccionEmbarque2 property.
	 *
	 * @param value
	 *            allowed object is {@link JAXBElement }{@code <}{@link String }
	 *            {@code >}
	 *
	 */
	public void setDireccionEmbarque2(JAXBElement<String> value) {
		this.direccionEmbarque2 = (value);
	}

	/**
	 * Gets the value of the fechaHoraDesembarque1 property.
	 *
	 * @return possible object is {@link JAXBElement }{@code <}{@link String }
	 *         {@code >}
	 *
	 */
	public JAXBElement<String> getFechaHoraDesembarque1() {
		return fechaHoraDesembarque1;
	}

	/**
	 * Sets the value of the fechaHoraDesembarque1 property.
	 *
	 * @param value
	 *            allowed object is {@link JAXBElement }{@code <}{@link String }
	 *            {@code >}
	 *
	 */
	public void setFechaHoraDesembarque1(JAXBElement<String> value) {
		this.fechaHoraDesembarque1 = (value);
	}

	/**
	 * Gets the value of the fechaHoraDesembarque2 property.
	 *
	 * @return possible object is {@link JAXBElement }{@code <}{@link String }
	 *         {@code >}
	 *
	 */
	public JAXBElement<String> getFechaHoraDesembarque2() {
		return fechaHoraDesembarque2;
	}

	/**
	 * Sets the value of the fechaHoraDesembarque2 property.
	 *
	 * @param value
	 *            allowed object is {@link JAXBElement }{@code <}{@link String }
	 *            {@code >}
	 *
	 */
	public void setFechaHoraDesembarque2(JAXBElement<String> value) {
		this.fechaHoraDesembarque2 = (value);
	}

	/**
	 * Gets the value of the fechaHoraEmbarque1 property.
	 *
	 * @return possible object is {@link JAXBElement }{@code <}{@link String }
	 *         {@code >}
	 *
	 */
	public JAXBElement<String> getFechaHoraEmbarque1() {
		return fechaHoraEmbarque1;
	}

	/**
	 * Sets the value of the fechaHoraEmbarque1 property.
	 *
	 * @param value
	 *            allowed object is {@link JAXBElement }{@code <}{@link String }
	 *            {@code >}
	 *
	 */
	public void setFechaHoraEmbarque1(JAXBElement<String> value) {
		this.fechaHoraEmbarque1 = (value);
	}

	/**
	 * Gets the value of the fechaHoraEmbarque2 property.
	 *
	 * @return possible object is {@link JAXBElement }{@code <}{@link String }
	 *         {@code >}
	 *
	 */
	public JAXBElement<String> getFechaHoraEmbarque2() {
		return fechaHoraEmbarque2;
	}

	/**
	 * Sets the value of the fechaHoraEmbarque2 property.
	 *
	 * @param value
	 *            allowed object is {@link JAXBElement }{@code <}{@link String }
	 *            {@code >}
	 *
	 */
	public void setFechaHoraEmbarque2(JAXBElement<String> value) {
		this.fechaHoraEmbarque2 = (value);
	}

	/**
	 * Gets the value of the menus property.
	 *
	 * @return possible object is {@link JAXBElement }{@code <}
	 *         {@link ArrayOfGeneric }{@code >}
	 *
	 */
	public JAXBElement<ArrayOfGeneric> getMenus() {
		return menus;
	}

	/**
	 * Sets the value of the menus property.
	 *
	 * @param value
	 *            allowed object is {@link JAXBElement }{@code <}
	 *            {@link ArrayOfGeneric }{@code >}
	 *
	 */
	public void setMenus(JAXBElement<ArrayOfGeneric> value) {
		this.menus = (value);
	}

	/**
	 * Gets the value of the monedaDescripcion property.
	 *
	 * @return possible object is {@link Double }
	 *
	 */
	public Double getMonedaDescripcion() {
		return monedaDescripcion;
	}

	/**
	 * Sets the value of the monedaDescripcion property.
	 *
	 * @param value
	 *            allowed object is {@link Double }
	 *
	 */
	public void setMonedaDescripcion(Double value) {
		this.monedaDescripcion = value;
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
	 * Gets the value of the ruta property.
	 *
	 * @return possible object is {@link JAXBElement }{@code <}{@link String }
	 *         {@code >}
	 *
	 */
	public JAXBElement<String> getRuta() {
		return ruta;
	}

	/**
	 * Sets the value of the ruta property.
	 *
	 * @param value
	 *            allowed object is {@link JAXBElement }{@code <}{@link String }
	 *            {@code >}
	 *
	 */
	public void setRuta(JAXBElement<String> value) {
		this.ruta = (value);
	}

	/**
	 * Gets the value of the rutaInternacional property.
	 *
	 * @return possible object is {@link JAXBElement }{@code <}{@link String }
	 *         {@code >}
	 *
	 */
	public JAXBElement<String> getRutaInternacional() {
		return rutaInternacional;
	}

	/**
	 * Sets the value of the rutaInternacional property.
	 *
	 * @param value
	 *            allowed object is {@link JAXBElement }{@code <}{@link String }
	 *            {@code >}
	 *
	 */
	public void setRutaInternacional(JAXBElement<String> value) {
		this.rutaInternacional = (value);
	}

	/**
	 * Gets the value of the servicio property.
	 *
	 * @return possible object is {@link JAXBElement }{@code <}{@link String }
	 *         {@code >}
	 *
	 */
	public JAXBElement<String> getServicio() {
		return servicio;
	}

	/**
	 * Sets the value of the servicio property.
	 *
	 * @param value
	 *            allowed object is {@link JAXBElement }{@code <}{@link String }
	 *            {@code >}
	 *
	 */
	public void setServicio(JAXBElement<String> value) {
		this.servicio = (value);
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
	 * Gets the value of the tarifaOcasion property.
	 *
	 * @return possible object is {@link Double }
	 *
	 */
	public Double getTarifaOcasion() {
		return tarifaOcasion;
	}

	/**
	 * Sets the value of the tarifaOcasion property.
	 *
	 * @param value
	 *            allowed object is {@link Double }
	 *
	 */
	public void setTarifaOcasion(Double value) {
		this.tarifaOcasion = value;
	}

	/**
	 * Gets the value of the tarifaPrimerPiso property.
	 *
	 * @return possible object is {@link Double }
	 *
	 */
	public Double getTarifaPrimerPiso() {
		return tarifaPrimerPiso;
	}

	/**
	 * Sets the value of the tarifaPrimerPiso property.
	 *
	 * @param value
	 *            allowed object is {@link Double }
	 *
	 */
	public void setTarifaPrimerPiso(Double value) {
		this.tarifaPrimerPiso = value;
	}

	/**
	 * Gets the value of the tarifaSegundoPiso property.
	 *
	 * @return possible object is {@link Double }
	 *
	 */
	public Double getTarifaSegundoPiso() {
		return tarifaSegundoPiso;
	}

	/**
	 * Sets the value of the tarifaSegundoPiso property.
	 *
	 * @param value
	 *            allowed object is {@link Double }
	 *
	 */
	public void setTarifaSegundoPiso(Double value) {
		this.tarifaSegundoPiso = value;
	}

	/**
	 * Gets the value of the telefonoDesembarque1 property.
	 *
	 * @return possible object is {@link JAXBElement }{@code <}{@link String }
	 *         {@code >}
	 *
	 */
	public JAXBElement<String> getTelefonoDesembarque1() {
		return telefonoDesembarque1;
	}

	/**
	 * Sets the value of the telefonoDesembarque1 property.
	 *
	 * @param value
	 *            allowed object is {@link JAXBElement }{@code <}{@link String }
	 *            {@code >}
	 *
	 */
	public void setTelefonoDesembarque1(JAXBElement<String> value) {
		this.telefonoDesembarque1 = (value);
	}

	/**
	 * Gets the value of the telefonoDesembarque2 property.
	 *
	 * @return possible object is {@link JAXBElement }{@code <}{@link String }
	 *         {@code >}
	 *
	 */
	public JAXBElement<String> getTelefonoDesembarque2() {
		return telefonoDesembarque2;
	}

	/**
	 * Sets the value of the telefonoDesembarque2 property.
	 *
	 * @param value
	 *            allowed object is {@link JAXBElement }{@code <}{@link String }
	 *            {@code >}
	 *
	 */
	public void setTelefonoDesembarque2(JAXBElement<String> value) {
		this.telefonoDesembarque2 = (value);
	}

	/**
	 * Gets the value of the telefonoEmbarque1 property.
	 *
	 * @return possible object is {@link JAXBElement }{@code <}{@link String }
	 *         {@code >}
	 *
	 */
	public JAXBElement<String> getTelefonoEmbarque1() {
		return telefonoEmbarque1;
	}

	/**
	 * Sets the value of the telefonoEmbarque1 property.
	 *
	 * @param value
	 *            allowed object is {@link JAXBElement }{@code <}{@link String }
	 *            {@code >}
	 *
	 */
	public void setTelefonoEmbarque1(JAXBElement<String> value) {
		this.telefonoEmbarque1 = (value);
	}

	/**
	 * Gets the value of the telefonoEmbarque2 property.
	 *
	 * @return possible object is {@link JAXBElement }{@code <}{@link String }
	 *         {@code >}
	 *
	 */
	public JAXBElement<String> getTelefonoEmbarque2() {
		return telefonoEmbarque2;
	}

	/**
	 * Sets the value of the telefonoEmbarque2 property.
	 *
	 * @param value
	 *            allowed object is {@link JAXBElement }{@code <}{@link String }
	 *            {@code >}
	 *
	 */
	public void setTelefonoEmbarque2(JAXBElement<String> value) {
		this.telefonoEmbarque2 = (value);
	}

	/**
	 * Gets the value of the tiempoViaje property.
	 *
	 * @return possible object is {@link JAXBElement }{@code <}{@link String }
	 *         {@code >}
	 *
	 */
	public JAXBElement<String> getTiempoViaje() {
		return tiempoViaje;
	}

	/**
	 * Sets the value of the tiempoViaje property.
	 *
	 * @param value
	 *            allowed object is {@link JAXBElement }{@code <}{@link String }
	 *            {@code >}
	 *
	 */
	public void setTiempoViaje(JAXBElement<String> value) {
		this.tiempoViaje = (value);
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
