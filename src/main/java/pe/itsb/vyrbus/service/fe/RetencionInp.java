
package pe.itsb.vyrbus.service.fe;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlType;

/**
 * <p>
 * Java class for RetencionInp complex type.
 *
 * <p>
 * The following schema fragment specifies the expected content contained within
 * this class.
 *
 * <pre>
 * &lt;complexType name="RetencionInp">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="FechaEmision" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="ImporteTotalRetenido" type="{http://www.w3.org/2001/XMLSchema}double" minOccurs="0"/>
 *         &lt;element name="ImporteTotalRetenidoEnLetras" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="MontoTotalPagado" type="{http://www.w3.org/2001/XMLSchema}double" minOccurs="0"/>
 *         &lt;element name="NumeroCorrelativo" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="RazonSocial" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Ruc" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Serie" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="TipoCambio" type="{http://www.w3.org/2001/XMLSchema}double" minOccurs="0"/>
 *         &lt;element name="correo" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="detalleRetencion" type="{http://schemas.datacontract.org/2004/07/FEService.Input}ArrayOfRetencionDetalleInp" minOccurs="0"/>
 *         &lt;element name="id" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="registroRetencion" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="tasaRetencion" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 *
 *
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "RetencionInp", namespace = "http://schemas.datacontract.org/2004/07/FEService.Input", propOrder = {
		"fechaEmision", "importeTotalRetenido", "importeTotalRetenidoEnLetras", "montoTotalPagado", "numeroCorrelativo",
		"razonSocial", "ruc", "serie", "tipoCambio", "correo", "detalleRetencion", "id", "registroRetencion",
		"tasaRetencion" })
public class RetencionInp {

	@XmlElementRef(name = "FechaEmision", namespace = "http://schemas.datacontract.org/2004/07/FEService.Input", type = JAXBElement.class)
	protected JAXBElement<String> fechaEmision;
	@XmlElement(name = "ImporteTotalRetenido")
	protected Double importeTotalRetenido;
	@XmlElementRef(name = "ImporteTotalRetenidoEnLetras", namespace = "http://schemas.datacontract.org/2004/07/FEService.Input", type = JAXBElement.class)
	protected JAXBElement<String> importeTotalRetenidoEnLetras;
	@XmlElement(name = "MontoTotalPagado")
	protected Double montoTotalPagado;
	@XmlElementRef(name = "NumeroCorrelativo", namespace = "http://schemas.datacontract.org/2004/07/FEService.Input", type = JAXBElement.class)
	protected JAXBElement<String> numeroCorrelativo;
	@XmlElementRef(name = "RazonSocial", namespace = "http://schemas.datacontract.org/2004/07/FEService.Input", type = JAXBElement.class)
	protected JAXBElement<String> razonSocial;
	@XmlElementRef(name = "Ruc", namespace = "http://schemas.datacontract.org/2004/07/FEService.Input", type = JAXBElement.class)
	protected JAXBElement<String> ruc;
	@XmlElementRef(name = "Serie", namespace = "http://schemas.datacontract.org/2004/07/FEService.Input", type = JAXBElement.class)
	protected JAXBElement<String> serie;
	@XmlElement(name = "TipoCambio")
	protected Double tipoCambio;
	@XmlElementRef(name = "correo", namespace = "http://schemas.datacontract.org/2004/07/FEService.Input", type = JAXBElement.class)
	protected JAXBElement<String> correo;
	@XmlElementRef(name = "detalleRetencion", namespace = "http://schemas.datacontract.org/2004/07/FEService.Input", type = JAXBElement.class)
	protected JAXBElement<ArrayOfRetencionDetalleInp> detalleRetencion;
	@XmlElementRef(name = "id", namespace = "http://schemas.datacontract.org/2004/07/FEService.Input", type = JAXBElement.class)
	protected JAXBElement<String> id;
	@XmlElementRef(name = "registroRetencion", namespace = "http://schemas.datacontract.org/2004/07/FEService.Input", type = JAXBElement.class)
	protected JAXBElement<String> registroRetencion;
	@XmlElementRef(name = "tasaRetencion", namespace = "http://schemas.datacontract.org/2004/07/FEService.Input", type = JAXBElement.class)
	protected JAXBElement<String> tasaRetencion;

	/**
	 * Gets the value of the fechaEmision property.
	 *
	 * @return possible object is {@link JAXBElement }{@code <}{@link String
	 *         }{@code >}
	 *
	 */
	public JAXBElement<String> getFechaEmision() {
		return fechaEmision;
	}

	/**
	 * Sets the value of the fechaEmision property.
	 *
	 * @param value allowed object is {@link JAXBElement }{@code <}{@link String
	 *              }{@code >}
	 *
	 */
	public void setFechaEmision(JAXBElement<String> value) {
		this.fechaEmision = (value);
	}

	/**
	 * Gets the value of the importeTotalRetenido property.
	 *
	 * @return possible object is {@link Double }
	 *
	 */
	public Double getImporteTotalRetenido() {
		return importeTotalRetenido;
	}

	/**
	 * Sets the value of the importeTotalRetenido property.
	 *
	 * @param value allowed object is {@link Double }
	 *
	 */
	public void setImporteTotalRetenido(Double value) {
		this.importeTotalRetenido = value;
	}

	/**
	 * Gets the value of the importeTotalRetenidoEnLetras property.
	 *
	 * @return possible object is {@link JAXBElement }{@code <}{@link String
	 *         }{@code >}
	 *
	 */
	public JAXBElement<String> getImporteTotalRetenidoEnLetras() {
		return importeTotalRetenidoEnLetras;
	}

	/**
	 * Sets the value of the importeTotalRetenidoEnLetras property.
	 *
	 * @param value allowed object is {@link JAXBElement }{@code <}{@link String
	 *              }{@code >}
	 *
	 */
	public void setImporteTotalRetenidoEnLetras(JAXBElement<String> value) {
		this.importeTotalRetenidoEnLetras = (value);
	}

	/**
	 * Gets the value of the montoTotalPagado property.
	 *
	 * @return possible object is {@link Double }
	 *
	 */
	public Double getMontoTotalPagado() {
		return montoTotalPagado;
	}

	/**
	 * Sets the value of the montoTotalPagado property.
	 *
	 * @param value allowed object is {@link Double }
	 *
	 */
	public void setMontoTotalPagado(Double value) {
		this.montoTotalPagado = value;
	}

	/**
	 * Gets the value of the numeroCorrelativo property.
	 *
	 * @return possible object is {@link JAXBElement }{@code <}{@link String
	 *         }{@code >}
	 *
	 */
	public JAXBElement<String> getNumeroCorrelativo() {
		return numeroCorrelativo;
	}

	/**
	 * Sets the value of the numeroCorrelativo property.
	 *
	 * @param value allowed object is {@link JAXBElement }{@code <}{@link String
	 *              }{@code >}
	 *
	 */
	public void setNumeroCorrelativo(JAXBElement<String> value) {
		this.numeroCorrelativo = (value);
	}

	/**
	 * Gets the value of the razonSocial property.
	 *
	 * @return possible object is {@link JAXBElement }{@code <}{@link String
	 *         }{@code >}
	 *
	 */
	public JAXBElement<String> getRazonSocial() {
		return razonSocial;
	}

	/**
	 * Sets the value of the razonSocial property.
	 *
	 * @param value allowed object is {@link JAXBElement }{@code <}{@link String
	 *              }{@code >}
	 *
	 */
	public void setRazonSocial(JAXBElement<String> value) {
		this.razonSocial = (value);
	}

	/**
	 * Gets the value of the ruc property.
	 *
	 * @return possible object is {@link JAXBElement }{@code <}{@link String
	 *         }{@code >}
	 *
	 */
	public JAXBElement<String> getRuc() {
		return ruc;
	}

	/**
	 * Sets the value of the ruc property.
	 *
	 * @param value allowed object is {@link JAXBElement }{@code <}{@link String
	 *              }{@code >}
	 *
	 */
	public void setRuc(JAXBElement<String> value) {
		this.ruc = (value);
	}

	/**
	 * Gets the value of the serie property.
	 *
	 * @return possible object is {@link JAXBElement }{@code <}{@link String
	 *         }{@code >}
	 *
	 */
	public JAXBElement<String> getSerie() {
		return serie;
	}

	/**
	 * Sets the value of the serie property.
	 *
	 * @param value allowed object is {@link JAXBElement }{@code <}{@link String
	 *              }{@code >}
	 *
	 */
	public void setSerie(JAXBElement<String> value) {
		this.serie = (value);
	}

	/**
	 * Gets the value of the tipoCambio property.
	 *
	 * @return possible object is {@link Double }
	 *
	 */
	public Double getTipoCambio() {
		return tipoCambio;
	}

	/**
	 * Sets the value of the tipoCambio property.
	 *
	 * @param value allowed object is {@link Double }
	 *
	 */
	public void setTipoCambio(Double value) {
		this.tipoCambio = value;
	}

	/**
	 * Gets the value of the correo property.
	 *
	 * @return possible object is {@link JAXBElement }{@code <}{@link String
	 *         }{@code >}
	 *
	 */
	public JAXBElement<String> getCorreo() {
		return correo;
	}

	/**
	 * Sets the value of the correo property.
	 *
	 * @param value allowed object is {@link JAXBElement }{@code <}{@link String
	 *              }{@code >}
	 *
	 */
	public void setCorreo(JAXBElement<String> value) {
		this.correo = (value);
	}

	/**
	 * Gets the value of the detalleRetencion property.
	 *
	 * @return possible object is {@link JAXBElement
	 *         }{@code <}{@link ArrayOfRetencionDetalleInp }{@code >}
	 *
	 */
	public JAXBElement<ArrayOfRetencionDetalleInp> getDetalleRetencion() {
		return detalleRetencion;
	}

	/**
	 * Sets the value of the detalleRetencion property.
	 *
	 * @param value allowed object is {@link JAXBElement
	 *              }{@code <}{@link ArrayOfRetencionDetalleInp }{@code >}
	 *
	 */
	public void setDetalleRetencion(JAXBElement<ArrayOfRetencionDetalleInp> value) {
		this.detalleRetencion = (value);
	}

	/**
	 * Gets the value of the id property.
	 *
	 * @return possible object is {@link JAXBElement }{@code <}{@link String
	 *         }{@code >}
	 *
	 */
	public JAXBElement<String> getId() {
		return id;
	}

	/**
	 * Sets the value of the id property.
	 *
	 * @param value allowed object is {@link JAXBElement }{@code <}{@link String
	 *              }{@code >}
	 *
	 */
	public void setId(JAXBElement<String> value) {
		this.id = (value);
	}

	/**
	 * Gets the value of the registroRetencion property.
	 *
	 * @return possible object is {@link JAXBElement }{@code <}{@link String
	 *         }{@code >}
	 *
	 */
	public JAXBElement<String> getRegistroRetencion() {
		return registroRetencion;
	}

	/**
	 * Sets the value of the registroRetencion property.
	 *
	 * @param value allowed object is {@link JAXBElement }{@code <}{@link String
	 *              }{@code >}
	 *
	 */
	public void setRegistroRetencion(JAXBElement<String> value) {
		this.registroRetencion = (value);
	}

	/**
	 * Gets the value of the tasaRetencion property.
	 *
	 * @return possible object is {@link JAXBElement }{@code <}{@link String
	 *         }{@code >}
	 *
	 */
	public JAXBElement<String> getTasaRetencion() {
		return tasaRetencion;
	}

	/**
	 * Sets the value of the tasaRetencion property.
	 *
	 * @param value allowed object is {@link JAXBElement }{@code <}{@link String
	 *              }{@code >}
	 *
	 */
	public void setTasaRetencion(JAXBElement<String> value) {
		this.tasaRetencion = (value);
	}

}
