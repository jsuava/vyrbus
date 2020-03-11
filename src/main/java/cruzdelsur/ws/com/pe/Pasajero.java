package cruzdelsur.ws.com.pe;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlType;

/**
 * <p>
 * Java class for Pasajero complex type.
 * 
 * <p>
 * The following schema fragment specifies the expected content contained within
 * this class.
 * 
 * <pre>
 * &lt;complexType name="Pasajero">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="RazonSocial" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="RucEmpresa" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="apellidoMaterno" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="apellidoPaterno" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="codigoNacionalidad" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="codigoTipoDocumento" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="edad" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="email" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="fechaNacimiento" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="menus" type="{http://schemas.datacontract.org/2004/07/wscruzdelsur.Entities}ArrayOfGeneric" minOccurs="0"/>
 *         &lt;element name="nombres" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="numeroBoleto" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="numeroDocumento" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="telefono" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="tipoTarifa" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Pasajero", propOrder = { "razonSocial", "rucEmpresa",
		"apellidoMaterno", "apellidoPaterno", "codigoNacionalidad",
		"codigoTipoDocumento", "edad", "email", "fechaNacimiento", "menus",
		"nombres", "numeroBoleto", "numeroDocumento", "telefono", "tipoTarifa" })
public class Pasajero {

	@XmlElementRef(name = "RazonSocial", namespace = "http://schemas.datacontract.org/2004/07/wscruzdelsur.Entities", type = JAXBElement.class)
	protected JAXBElement<String> razonSocial;
	@XmlElementRef(name = "RucEmpresa", namespace = "http://schemas.datacontract.org/2004/07/wscruzdelsur.Entities", type = JAXBElement.class)
	protected JAXBElement<String> rucEmpresa;
	@XmlElementRef(name = "apellidoMaterno", namespace = "http://schemas.datacontract.org/2004/07/wscruzdelsur.Entities", type = JAXBElement.class)
	protected JAXBElement<String> apellidoMaterno;
	@XmlElementRef(name = "apellidoPaterno", namespace = "http://schemas.datacontract.org/2004/07/wscruzdelsur.Entities", type = JAXBElement.class)
	protected JAXBElement<String> apellidoPaterno;
	@XmlElementRef(name = "codigoNacionalidad", namespace = "http://schemas.datacontract.org/2004/07/wscruzdelsur.Entities", type = JAXBElement.class)
	protected JAXBElement<String> codigoNacionalidad;
	@XmlElementRef(name = "codigoTipoDocumento", namespace = "http://schemas.datacontract.org/2004/07/wscruzdelsur.Entities", type = JAXBElement.class)
	protected JAXBElement<String> codigoTipoDocumento;
	protected Integer edad;
	@XmlElementRef(name = "email", namespace = "http://schemas.datacontract.org/2004/07/wscruzdelsur.Entities", type = JAXBElement.class)
	protected JAXBElement<String> email;
	@XmlElementRef(name = "fechaNacimiento", namespace = "http://schemas.datacontract.org/2004/07/wscruzdelsur.Entities", type = JAXBElement.class)
	protected JAXBElement<String> fechaNacimiento;
	@XmlElementRef(name = "menus", namespace = "http://schemas.datacontract.org/2004/07/wscruzdelsur.Entities", type = JAXBElement.class)
	protected JAXBElement<ArrayOfGeneric> menus;
	@XmlElementRef(name = "nombres", namespace = "http://schemas.datacontract.org/2004/07/wscruzdelsur.Entities", type = JAXBElement.class)
	protected JAXBElement<String> nombres;
	@XmlElementRef(name = "numeroBoleto", namespace = "http://schemas.datacontract.org/2004/07/wscruzdelsur.Entities", type = JAXBElement.class)
	protected JAXBElement<String> numeroBoleto;
	@XmlElementRef(name = "numeroDocumento", namespace = "http://schemas.datacontract.org/2004/07/wscruzdelsur.Entities", type = JAXBElement.class)
	protected JAXBElement<String> numeroDocumento;
	@XmlElementRef(name = "telefono", namespace = "http://schemas.datacontract.org/2004/07/wscruzdelsur.Entities", type = JAXBElement.class)
	protected JAXBElement<String> telefono;
	@XmlElementRef(name = "tipoTarifa", namespace = "http://schemas.datacontract.org/2004/07/wscruzdelsur.Entities", type = JAXBElement.class)
	protected JAXBElement<String> tipoTarifa;

	/**
	 * Gets the value of the razonSocial property.
	 * 
	 * @return possible object is {@link JAXBElement }{@code <}{@link String }
	 *         {@code >}
	 * 
	 */
	public JAXBElement<String> getRazonSocial() {
		return razonSocial;
	}

	/**
	 * Sets the value of the razonSocial property.
	 * 
	 * @param value
	 *            allowed object is {@link JAXBElement }{@code <}{@link String }
	 *            {@code >}
	 * 
	 */
	public void setRazonSocial(JAXBElement<String> value) {
		this.razonSocial = ((JAXBElement<String>) value);
	}

	/**
	 * Gets the value of the rucEmpresa property.
	 * 
	 * @return possible object is {@link JAXBElement }{@code <}{@link String }
	 *         {@code >}
	 * 
	 */
	public JAXBElement<String> getRucEmpresa() {
		return rucEmpresa;
	}

	/**
	 * Sets the value of the rucEmpresa property.
	 * 
	 * @param value
	 *            allowed object is {@link JAXBElement }{@code <}{@link String }
	 *            {@code >}
	 * 
	 */
	public void setRucEmpresa(JAXBElement<String> value) {
		this.rucEmpresa = ((JAXBElement<String>) value);
	}

	/**
	 * Gets the value of the apellidoMaterno property.
	 * 
	 * @return possible object is {@link JAXBElement }{@code <}{@link String }
	 *         {@code >}
	 * 
	 */
	public JAXBElement<String> getApellidoMaterno() {
		return apellidoMaterno;
	}

	/**
	 * Sets the value of the apellidoMaterno property.
	 * 
	 * @param value
	 *            allowed object is {@link JAXBElement }{@code <}{@link String }
	 *            {@code >}
	 * 
	 */
	public void setApellidoMaterno(JAXBElement<String> value) {
		this.apellidoMaterno = ((JAXBElement<String>) value);
	}

	/**
	 * Gets the value of the apellidoPaterno property.
	 * 
	 * @return possible object is {@link JAXBElement }{@code <}{@link String }
	 *         {@code >}
	 * 
	 */
	public JAXBElement<String> getApellidoPaterno() {
		return apellidoPaterno;
	}

	/**
	 * Sets the value of the apellidoPaterno property.
	 * 
	 * @param value
	 *            allowed object is {@link JAXBElement }{@code <}{@link String }
	 *            {@code >}
	 * 
	 */
	public void setApellidoPaterno(JAXBElement<String> value) {
		this.apellidoPaterno = ((JAXBElement<String>) value);
	}

	/**
	 * Gets the value of the codigoNacionalidad property.
	 * 
	 * @return possible object is {@link JAXBElement }{@code <}{@link String }
	 *         {@code >}
	 * 
	 */
	public JAXBElement<String> getCodigoNacionalidad() {
		return codigoNacionalidad;
	}

	/**
	 * Sets the value of the codigoNacionalidad property.
	 * 
	 * @param value
	 *            allowed object is {@link JAXBElement }{@code <}{@link String }
	 *            {@code >}
	 * 
	 */
	public void setCodigoNacionalidad(JAXBElement<String> value) {
		this.codigoNacionalidad = ((JAXBElement<String>) value);
	}

	/**
	 * Gets the value of the codigoTipoDocumento property.
	 * 
	 * @return possible object is {@link JAXBElement }{@code <}{@link String }
	 *         {@code >}
	 * 
	 */
	public JAXBElement<String> getCodigoTipoDocumento() {
		return codigoTipoDocumento;
	}

	/**
	 * Sets the value of the codigoTipoDocumento property.
	 * 
	 * @param value
	 *            allowed object is {@link JAXBElement }{@code <}{@link String }
	 *            {@code >}
	 * 
	 */
	public void setCodigoTipoDocumento(JAXBElement<String> value) {
		this.codigoTipoDocumento = ((JAXBElement<String>) value);
	}

	/**
	 * Gets the value of the edad property.
	 * 
	 * @return possible object is {@link Integer }
	 * 
	 */
	public Integer getEdad() {
		return edad;
	}

	/**
	 * Sets the value of the edad property.
	 * 
	 * @param value
	 *            allowed object is {@link Integer }
	 * 
	 */
	public void setEdad(Integer value) {
		this.edad = value;
	}

	/**
	 * Gets the value of the email property.
	 * 
	 * @return possible object is {@link JAXBElement }{@code <}{@link String }
	 *         {@code >}
	 * 
	 */
	public JAXBElement<String> getEmail() {
		return email;
	}

	/**
	 * Sets the value of the email property.
	 * 
	 * @param value
	 *            allowed object is {@link JAXBElement }{@code <}{@link String }
	 *            {@code >}
	 * 
	 */
	public void setEmail(JAXBElement<String> value) {
		this.email = ((JAXBElement<String>) value);
	}

	/**
	 * Gets the value of the fechaNacimiento property.
	 * 
	 * @return possible object is {@link JAXBElement }{@code <}{@link String }
	 *         {@code >}
	 * 
	 */
	public JAXBElement<String> getFechaNacimiento() {
		return fechaNacimiento;
	}

	/**
	 * Sets the value of the fechaNacimiento property.
	 * 
	 * @param value
	 *            allowed object is {@link JAXBElement }{@code <}{@link String }
	 *            {@code >}
	 * 
	 */
	public void setFechaNacimiento(JAXBElement<String> value) {
		this.fechaNacimiento = ((JAXBElement<String>) value);
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
		this.menus = ((JAXBElement<ArrayOfGeneric>) value);
	}

	/**
	 * Gets the value of the nombres property.
	 * 
	 * @return possible object is {@link JAXBElement }{@code <}{@link String }
	 *         {@code >}
	 * 
	 */
	public JAXBElement<String> getNombres() {
		return nombres;
	}

	/**
	 * Sets the value of the nombres property.
	 * 
	 * @param value
	 *            allowed object is {@link JAXBElement }{@code <}{@link String }
	 *            {@code >}
	 * 
	 */
	public void setNombres(JAXBElement<String> value) {
		this.nombres = ((JAXBElement<String>) value);
	}

	/**
	 * Gets the value of the numeroBoleto property.
	 * 
	 * @return possible object is {@link JAXBElement }{@code <}{@link String }
	 *         {@code >}
	 * 
	 */
	public JAXBElement<String> getNumeroBoleto() {
		return numeroBoleto;
	}

	/**
	 * Sets the value of the numeroBoleto property.
	 * 
	 * @param value
	 *            allowed object is {@link JAXBElement }{@code <}{@link String }
	 *            {@code >}
	 * 
	 */
	public void setNumeroBoleto(JAXBElement<String> value) {
		this.numeroBoleto = ((JAXBElement<String>) value);
	}

	/**
	 * Gets the value of the numeroDocumento property.
	 * 
	 * @return possible object is {@link JAXBElement }{@code <}{@link String }
	 *         {@code >}
	 * 
	 */
	public JAXBElement<String> getNumeroDocumento() {
		return numeroDocumento;
	}

	/**
	 * Sets the value of the numeroDocumento property.
	 * 
	 * @param value
	 *            allowed object is {@link JAXBElement }{@code <}{@link String }
	 *            {@code >}
	 * 
	 */
	public void setNumeroDocumento(JAXBElement<String> value) {
		this.numeroDocumento = ((JAXBElement<String>) value);
	}

	/**
	 * Gets the value of the telefono property.
	 * 
	 * @return possible object is {@link JAXBElement }{@code <}{@link String }
	 *         {@code >}
	 * 
	 */
	public JAXBElement<String> getTelefono() {
		return telefono;
	}

	/**
	 * Sets the value of the telefono property.
	 * 
	 * @param value
	 *            allowed object is {@link JAXBElement }{@code <}{@link String }
	 *            {@code >}
	 * 
	 */
	public void setTelefono(JAXBElement<String> value) {
		this.telefono = ((JAXBElement<String>) value);
	}

	/**
	 * Gets the value of the tipoTarifa property.
	 * 
	 * @return possible object is {@link JAXBElement }{@code <}{@link String }
	 *         {@code >}
	 * 
	 */
	public JAXBElement<String> getTipoTarifa() {
		return tipoTarifa;
	}

	/**
	 * Sets the value of the tipoTarifa property.
	 * 
	 * @param value
	 *            allowed object is {@link JAXBElement }{@code <}{@link String }
	 *            {@code >}
	 * 
	 */
	public void setTipoTarifa(JAXBElement<String> value) {
		this.tipoTarifa = ((JAXBElement<String>) value);
	}

}
