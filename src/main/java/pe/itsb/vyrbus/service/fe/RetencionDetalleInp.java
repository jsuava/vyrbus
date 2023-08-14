
package pe.itsb.vyrbus.service.fe;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlType;

/**
 * <p>
 * Java class for RetencionDetalleInp complex type.
 *
 * <p>
 * The following schema fragment specifies the expected content contained within
 * this class.
 *
 * <pre>
 * &lt;complexType name="RetencionDetalleInp">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="fechaEmisonDoctRelacionado" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="fechaPago" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="importeNeto" type="{http://www.w3.org/2001/XMLSchema}double" minOccurs="0"/>
 *         &lt;element name="importePagado" type="{http://www.w3.org/2001/XMLSchema}double" minOccurs="0"/>
 *         &lt;element name="importeRetenido" type="{http://www.w3.org/2001/XMLSchema}double" minOccurs="0"/>
 *         &lt;element name="importeTotalDoctRelacionado" type="{http://www.w3.org/2001/XMLSchema}double" minOccurs="0"/>
 *         &lt;element name="monedaDoctRelacionado" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="monedaOriginal" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="monedaPagado" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="numDoctRelacionado" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="numeroPago" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="tipoDoctRelacionado" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 *
 *
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "RetencionDetalleInp", namespace = "http://schemas.datacontract.org/2004/07/FEService.Input", propOrder = {
		"fechaEmisonDoctRelacionado", "fechaPago", "importeNeto", "importePagado", "importeRetenido",
		"importeTotalDoctRelacionado", "monedaDoctRelacionado", "monedaOriginal", "monedaPagado", "numDoctRelacionado",
		"numeroPago", "tipoDoctRelacionado" })
public class RetencionDetalleInp {

	@XmlElementRef(name = "fechaEmisonDoctRelacionado", namespace = "http://schemas.datacontract.org/2004/07/FEService.Input", type = JAXBElement.class)
	protected JAXBElement<String> fechaEmisonDoctRelacionado;
	@XmlElementRef(name = "fechaPago", namespace = "http://schemas.datacontract.org/2004/07/FEService.Input", type = JAXBElement.class)
	protected JAXBElement<String> fechaPago;
	protected Double importeNeto;
	protected Double importePagado;
	protected Double importeRetenido;
	protected Double importeTotalDoctRelacionado;
	@XmlElementRef(name = "monedaDoctRelacionado", namespace = "http://schemas.datacontract.org/2004/07/FEService.Input", type = JAXBElement.class)
	protected JAXBElement<String> monedaDoctRelacionado;
	@XmlElementRef(name = "monedaOriginal", namespace = "http://schemas.datacontract.org/2004/07/FEService.Input", type = JAXBElement.class)
	protected JAXBElement<String> monedaOriginal;
	@XmlElementRef(name = "monedaPagado", namespace = "http://schemas.datacontract.org/2004/07/FEService.Input", type = JAXBElement.class)
	protected JAXBElement<String> monedaPagado;
	@XmlElementRef(name = "numDoctRelacionado", namespace = "http://schemas.datacontract.org/2004/07/FEService.Input", type = JAXBElement.class)
	protected JAXBElement<String> numDoctRelacionado;
	@XmlElementRef(name = "numeroPago", namespace = "http://schemas.datacontract.org/2004/07/FEService.Input", type = JAXBElement.class)
	protected JAXBElement<String> numeroPago;
	@XmlElementRef(name = "tipoDoctRelacionado", namespace = "http://schemas.datacontract.org/2004/07/FEService.Input", type = JAXBElement.class)
	protected JAXBElement<String> tipoDoctRelacionado;

	/**
	 * Gets the value of the fechaEmisonDoctRelacionado property.
	 *
	 * @return possible object is {@link JAXBElement }{@code <}{@link String
	 *         }{@code >}
	 *
	 */
	public JAXBElement<String> getFechaEmisonDoctRelacionado() {
		return fechaEmisonDoctRelacionado;
	}

	/**
	 * Sets the value of the fechaEmisonDoctRelacionado property.
	 *
	 * @param value allowed object is {@link JAXBElement }{@code <}{@link String
	 *              }{@code >}
	 *
	 */
	public void setFechaEmisonDoctRelacionado(JAXBElement<String> value) {
		this.fechaEmisonDoctRelacionado = (value);
	}

	/**
	 * Gets the value of the fechaPago property.
	 *
	 * @return possible object is {@link JAXBElement }{@code <}{@link String
	 *         }{@code >}
	 *
	 */
	public JAXBElement<String> getFechaPago() {
		return fechaPago;
	}

	/**
	 * Sets the value of the fechaPago property.
	 *
	 * @param value allowed object is {@link JAXBElement }{@code <}{@link String
	 *              }{@code >}
	 *
	 */
	public void setFechaPago(JAXBElement<String> value) {
		this.fechaPago = (value);
	}

	/**
	 * Gets the value of the importeNeto property.
	 *
	 * @return possible object is {@link Double }
	 *
	 */
	public Double getImporteNeto() {
		return importeNeto;
	}

	/**
	 * Sets the value of the importeNeto property.
	 *
	 * @param value allowed object is {@link Double }
	 *
	 */
	public void setImporteNeto(Double value) {
		this.importeNeto = value;
	}

	/**
	 * Gets the value of the importePagado property.
	 *
	 * @return possible object is {@link Double }
	 *
	 */
	public Double getImportePagado() {
		return importePagado;
	}

	/**
	 * Sets the value of the importePagado property.
	 *
	 * @param value allowed object is {@link Double }
	 *
	 */
	public void setImportePagado(Double value) {
		this.importePagado = value;
	}

	/**
	 * Gets the value of the importeRetenido property.
	 *
	 * @return possible object is {@link Double }
	 *
	 */
	public Double getImporteRetenido() {
		return importeRetenido;
	}

	/**
	 * Sets the value of the importeRetenido property.
	 *
	 * @param value allowed object is {@link Double }
	 *
	 */
	public void setImporteRetenido(Double value) {
		this.importeRetenido = value;
	}

	/**
	 * Gets the value of the importeTotalDoctRelacionado property.
	 *
	 * @return possible object is {@link Double }
	 *
	 */
	public Double getImporteTotalDoctRelacionado() {
		return importeTotalDoctRelacionado;
	}

	/**
	 * Sets the value of the importeTotalDoctRelacionado property.
	 *
	 * @param value allowed object is {@link Double }
	 *
	 */
	public void setImporteTotalDoctRelacionado(Double value) {
		this.importeTotalDoctRelacionado = value;
	}

	/**
	 * Gets the value of the monedaDoctRelacionado property.
	 *
	 * @return possible object is {@link JAXBElement }{@code <}{@link String
	 *         }{@code >}
	 *
	 */
	public JAXBElement<String> getMonedaDoctRelacionado() {
		return monedaDoctRelacionado;
	}

	/**
	 * Sets the value of the monedaDoctRelacionado property.
	 *
	 * @param value allowed object is {@link JAXBElement }{@code <}{@link String
	 *              }{@code >}
	 *
	 */
	public void setMonedaDoctRelacionado(JAXBElement<String> value) {
		this.monedaDoctRelacionado = (value);
	}

	/**
	 * Gets the value of the monedaOriginal property.
	 *
	 * @return possible object is {@link JAXBElement }{@code <}{@link String
	 *         }{@code >}
	 *
	 */
	public JAXBElement<String> getMonedaOriginal() {
		return monedaOriginal;
	}

	/**
	 * Sets the value of the monedaOriginal property.
	 *
	 * @param value allowed object is {@link JAXBElement }{@code <}{@link String
	 *              }{@code >}
	 *
	 */
	public void setMonedaOriginal(JAXBElement<String> value) {
		this.monedaOriginal = (value);
	}

	/**
	 * Gets the value of the monedaPagado property.
	 *
	 * @return possible object is {@link JAXBElement }{@code <}{@link String
	 *         }{@code >}
	 *
	 */
	public JAXBElement<String> getMonedaPagado() {
		return monedaPagado;
	}

	/**
	 * Sets the value of the monedaPagado property.
	 *
	 * @param value allowed object is {@link JAXBElement }{@code <}{@link String
	 *              }{@code >}
	 *
	 */
	public void setMonedaPagado(JAXBElement<String> value) {
		this.monedaPagado = (value);
	}

	/**
	 * Gets the value of the numDoctRelacionado property.
	 *
	 * @return possible object is {@link JAXBElement }{@code <}{@link String
	 *         }{@code >}
	 *
	 */
	public JAXBElement<String> getNumDoctRelacionado() {
		return numDoctRelacionado;
	}

	/**
	 * Sets the value of the numDoctRelacionado property.
	 *
	 * @param value allowed object is {@link JAXBElement }{@code <}{@link String
	 *              }{@code >}
	 *
	 */
	public void setNumDoctRelacionado(JAXBElement<String> value) {
		this.numDoctRelacionado = (value);
	}

	/**
	 * Gets the value of the numeroPago property.
	 *
	 * @return possible object is {@link JAXBElement }{@code <}{@link String
	 *         }{@code >}
	 *
	 */
	public JAXBElement<String> getNumeroPago() {
		return numeroPago;
	}

	/**
	 * Sets the value of the numeroPago property.
	 *
	 * @param value allowed object is {@link JAXBElement }{@code <}{@link String
	 *              }{@code >}
	 *
	 */
	public void setNumeroPago(JAXBElement<String> value) {
		this.numeroPago = (value);
	}

	/**
	 * Gets the value of the tipoDoctRelacionado property.
	 *
	 * @return possible object is {@link JAXBElement }{@code <}{@link String
	 *         }{@code >}
	 *
	 */
	public JAXBElement<String> getTipoDoctRelacionado() {
		return tipoDoctRelacionado;
	}

	/**
	 * Sets the value of the tipoDoctRelacionado property.
	 *
	 * @param value allowed object is {@link JAXBElement }{@code <}{@link String
	 *              }{@code >}
	 *
	 */
	public void setTipoDoctRelacionado(JAXBElement<String> value) {
		this.tipoDoctRelacionado = (value);
	}

}
