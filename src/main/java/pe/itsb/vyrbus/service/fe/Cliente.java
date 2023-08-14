
package pe.itsb.vyrbus.service.fe;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlType;

/**
 * <p>
 * Java class for Cliente complex type.
 *
 * <p>
 * The following schema fragment specifies the expected content contained within
 * this class.
 *
 * <pre>
 * &lt;complexType name="Cliente">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="direccion" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="nombres" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="numeroDocumento" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="tipoDocumentoID" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 *
 *
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Cliente", namespace = "http://schemas.datacontract.org/2004/07/FEService.Input", propOrder = {
		"direccion", "nombres", "numeroDocumento", "tipoDocumentoID" })
public class Cliente {

	@XmlElementRef(name = "direccion", namespace = "http://schemas.datacontract.org/2004/07/FEService.Input", type = JAXBElement.class)
	protected JAXBElement<String> direccion;
	@XmlElementRef(name = "nombres", namespace = "http://schemas.datacontract.org/2004/07/FEService.Input", type = JAXBElement.class)
	protected JAXBElement<String> nombres;
	@XmlElementRef(name = "numeroDocumento", namespace = "http://schemas.datacontract.org/2004/07/FEService.Input", type = JAXBElement.class)
	protected JAXBElement<String> numeroDocumento;
	@XmlElementRef(name = "tipoDocumentoID", namespace = "http://schemas.datacontract.org/2004/07/FEService.Input", type = JAXBElement.class)
	protected JAXBElement<String> tipoDocumentoID;

	/**
	 * Gets the value of the direccion property.
	 *
	 * @return possible object is {@link JAXBElement }{@code <}{@link String
	 *         }{@code >}
	 *
	 */
	public JAXBElement<String> getDireccion() {
		return direccion;
	}

	/**
	 * Sets the value of the direccion property.
	 *
	 * @param value allowed object is {@link JAXBElement }{@code <}{@link String
	 *              }{@code >}
	 *
	 */
	public void setDireccion(JAXBElement<String> value) {
		this.direccion = (value);
	}

	/**
	 * Gets the value of the nombres property.
	 *
	 * @return possible object is {@link JAXBElement }{@code <}{@link String
	 *         }{@code >}
	 *
	 */
	public JAXBElement<String> getNombres() {
		return nombres;
	}

	/**
	 * Sets the value of the nombres property.
	 *
	 * @param value allowed object is {@link JAXBElement }{@code <}{@link String
	 *              }{@code >}
	 *
	 */
	public void setNombres(JAXBElement<String> value) {
		this.nombres = (value);
	}

	/**
	 * Gets the value of the numeroDocumento property.
	 *
	 * @return possible object is {@link JAXBElement }{@code <}{@link String
	 *         }{@code >}
	 *
	 */
	public JAXBElement<String> getNumeroDocumento() {
		return numeroDocumento;
	}

	/**
	 * Sets the value of the numeroDocumento property.
	 *
	 * @param value allowed object is {@link JAXBElement }{@code <}{@link String
	 *              }{@code >}
	 *
	 */
	public void setNumeroDocumento(JAXBElement<String> value) {
		this.numeroDocumento = (value);
	}

	/**
	 * Gets the value of the tipoDocumentoID property.
	 *
	 * @return possible object is {@link JAXBElement }{@code <}{@link String
	 *         }{@code >}
	 *
	 */
	public JAXBElement<String> getTipoDocumentoID() {
		return tipoDocumentoID;
	}

	/**
	 * Sets the value of the tipoDocumentoID property.
	 *
	 * @param value allowed object is {@link JAXBElement }{@code <}{@link String
	 *              }{@code >}
	 *
	 */
	public void setTipoDocumentoID(JAXBElement<String> value) {
		this.tipoDocumentoID = (value);
	}

}
