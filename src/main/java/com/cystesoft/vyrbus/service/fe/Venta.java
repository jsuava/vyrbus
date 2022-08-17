
package com.cystesoft.vyrbus.service.fe;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlType;

/**
 * <p>
 * Java class for Venta complex type.
 *
 * <p>
 * The following schema fragment specifies the expected content contained within
 * this class.
 *
 * <pre>
 * &lt;complexType name="Venta">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="InfoCreditList" type="{http://schemas.datacontract.org/2004/07/FEService.Input}ArrayOfInformacionCredito" minOccurs="0"/>
 *         &lt;element name="IsCreatePdf" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *         &lt;element name="agenciaID" type="{http://www.w3.org/2001/XMLSchema}long" minOccurs="0"/>
 *         &lt;element name="centroCosto" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="cliente" type="{http://schemas.datacontract.org/2004/07/FEService.Input}Cliente" minOccurs="0"/>
 *         &lt;element name="direccionEmbarque" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="documentoReferencia" type="{http://schemas.datacontract.org/2004/07/FEService.Input}DocumentoReferencia" minOccurs="0"/>
 *         &lt;element name="fechaEmision" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="glosaRetencion" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="horaEmision" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="igv" type="{http://www.w3.org/2001/XMLSchema}double" minOccurs="0"/>
 *         &lt;element name="informacionAdicional" type="{http://schemas.datacontract.org/2004/07/FEService.Input}InformacionAdicional" minOccurs="0"/>
 *         &lt;element name="isCredito" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *         &lt;element name="isSOUE" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *         &lt;element name="listDetalleVenta" type="{http://schemas.datacontract.org/2004/07/FEService.Input}ArrayOfDetalleVenta" minOccurs="0"/>
 *         &lt;element name="montoSubTotal" type="{http://www.w3.org/2001/XMLSchema}double" minOccurs="0"/>
 *         &lt;element name="montoTotal" type="{http://www.w3.org/2001/XMLSchema}double" minOccurs="0"/>
 *         &lt;element name="montoTotalDescuento" type="{http://www.w3.org/2001/XMLSchema}double" minOccurs="0"/>
 *         &lt;element name="numeroCorrelativo" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="numeroPrefactura" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="numeroSerie" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="observaciones" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="producto" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="rucEmpresa" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="tipoComprobanteID" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="tipoMonedaSoles" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *         &lt;element name="tipoVenta" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="usuarioID" type="{http://www.w3.org/2001/XMLSchema}long" minOccurs="0"/>
 *         &lt;element name="usuarioInsercion" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="usuarioModificacion" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 *
 *
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Venta", namespace = "http://schemas.datacontract.org/2004/07/FEService.Input", propOrder = {
		"infoCreditList", "isCreatePdf", "agenciaID", "centroCosto", "cliente", "direccionEmbarque",
		"documentoReferencia", "fechaEmision", "glosaRetencion", "horaEmision", "igv", "informacionAdicional",
		"isCredito", "isSOUE", "listDetalleVenta", "montoSubTotal", "montoTotal", "montoTotalDescuento",
		"numeroCorrelativo", "numeroPrefactura", "numeroSerie", "observaciones", "producto", "rucEmpresa",
		"tipoComprobanteID", "tipoMonedaSoles", "tipoVenta", "usuarioID", "usuarioInsercion", "usuarioModificacion" })
public class Venta {

	@XmlElementRef(name = "InfoCreditList", namespace = "http://schemas.datacontract.org/2004/07/FEService.Input", type = JAXBElement.class)
	protected JAXBElement<ArrayOfInformacionCredito> infoCreditList;
	@XmlElement(name = "IsCreatePdf")
	protected Boolean isCreatePdf;
	protected Long agenciaID;
	@XmlElementRef(name = "centroCosto", namespace = "http://schemas.datacontract.org/2004/07/FEService.Input", type = JAXBElement.class)
	protected JAXBElement<String> centroCosto;
	@XmlElementRef(name = "cliente", namespace = "http://schemas.datacontract.org/2004/07/FEService.Input", type = JAXBElement.class)
	protected JAXBElement<Cliente> cliente;
	@XmlElementRef(name = "direccionEmbarque", namespace = "http://schemas.datacontract.org/2004/07/FEService.Input", type = JAXBElement.class)
	protected JAXBElement<String> direccionEmbarque;
	@XmlElementRef(name = "documentoReferencia", namespace = "http://schemas.datacontract.org/2004/07/FEService.Input", type = JAXBElement.class)
	protected JAXBElement<DocumentoReferencia> documentoReferencia;
	@XmlElementRef(name = "fechaEmision", namespace = "http://schemas.datacontract.org/2004/07/FEService.Input", type = JAXBElement.class)
	protected JAXBElement<String> fechaEmision;
	@XmlElementRef(name = "glosaRetencion", namespace = "http://schemas.datacontract.org/2004/07/FEService.Input", type = JAXBElement.class)
	protected JAXBElement<String> glosaRetencion;
	@XmlElementRef(name = "horaEmision", namespace = "http://schemas.datacontract.org/2004/07/FEService.Input", type = JAXBElement.class)
	protected JAXBElement<String> horaEmision;
	protected Double igv;
	@XmlElementRef(name = "informacionAdicional", namespace = "http://schemas.datacontract.org/2004/07/FEService.Input", type = JAXBElement.class)
	protected JAXBElement<InformacionAdicional> informacionAdicional;
	protected Boolean isCredito;
	protected Boolean isSOUE;
	@XmlElementRef(name = "listDetalleVenta", namespace = "http://schemas.datacontract.org/2004/07/FEService.Input", type = JAXBElement.class)
	protected JAXBElement<ArrayOfDetalleVenta> listDetalleVenta;
	protected Double montoSubTotal;
	protected Double montoTotal;
	protected Double montoTotalDescuento;
	@XmlElementRef(name = "numeroCorrelativo", namespace = "http://schemas.datacontract.org/2004/07/FEService.Input", type = JAXBElement.class)
	protected JAXBElement<String> numeroCorrelativo;
	@XmlElementRef(name = "numeroPrefactura", namespace = "http://schemas.datacontract.org/2004/07/FEService.Input", type = JAXBElement.class)
	protected JAXBElement<String> numeroPrefactura;
	@XmlElementRef(name = "numeroSerie", namespace = "http://schemas.datacontract.org/2004/07/FEService.Input", type = JAXBElement.class)
	protected JAXBElement<String> numeroSerie;
	@XmlElementRef(name = "observaciones", namespace = "http://schemas.datacontract.org/2004/07/FEService.Input", type = JAXBElement.class)
	protected JAXBElement<String> observaciones;
	@XmlElementRef(name = "producto", namespace = "http://schemas.datacontract.org/2004/07/FEService.Input", type = JAXBElement.class)
	protected JAXBElement<String> producto;
	@XmlElementRef(name = "rucEmpresa", namespace = "http://schemas.datacontract.org/2004/07/FEService.Input", type = JAXBElement.class)
	protected JAXBElement<String> rucEmpresa;
	@XmlElementRef(name = "tipoComprobanteID", namespace = "http://schemas.datacontract.org/2004/07/FEService.Input", type = JAXBElement.class)
	protected JAXBElement<String> tipoComprobanteID;
	protected Boolean tipoMonedaSoles;
	protected Integer tipoVenta;
	protected Long usuarioID;
	@XmlElementRef(name = "usuarioInsercion", namespace = "http://schemas.datacontract.org/2004/07/FEService.Input", type = JAXBElement.class)
	protected JAXBElement<String> usuarioInsercion;
	@XmlElementRef(name = "usuarioModificacion", namespace = "http://schemas.datacontract.org/2004/07/FEService.Input", type = JAXBElement.class)
	protected JAXBElement<String> usuarioModificacion;

	/**
	 * Gets the value of the infoCreditList property.
	 *
	 * @return possible object is {@link JAXBElement
	 *         }{@code <}{@link ArrayOfInformacionCredito }{@code >}
	 *
	 */
	public JAXBElement<ArrayOfInformacionCredito> getInfoCreditList() {
		return infoCreditList;
	}

	/**
	 * Sets the value of the infoCreditList property.
	 *
	 * @param value allowed object is {@link JAXBElement
	 *              }{@code <}{@link ArrayOfInformacionCredito }{@code >}
	 *
	 */
	public void setInfoCreditList(JAXBElement<ArrayOfInformacionCredito> value) {
		this.infoCreditList = (value);
	}

	/**
	 * Gets the value of the isCreatePdf property.
	 *
	 * @return possible object is {@link Boolean }
	 *
	 */
	public Boolean isIsCreatePdf() {
		return isCreatePdf;
	}

	/**
	 * Sets the value of the isCreatePdf property.
	 *
	 * @param value allowed object is {@link Boolean }
	 *
	 */
	public void setIsCreatePdf(Boolean value) {
		this.isCreatePdf = value;
	}

	/**
	 * Gets the value of the agenciaID property.
	 *
	 * @return possible object is {@link Long }
	 *
	 */
	public Long getAgenciaID() {
		return agenciaID;
	}

	/**
	 * Sets the value of the agenciaID property.
	 *
	 * @param value allowed object is {@link Long }
	 *
	 */
	public void setAgenciaID(Long value) {
		this.agenciaID = value;
	}

	/**
	 * Gets the value of the centroCosto property.
	 *
	 * @return possible object is {@link JAXBElement }{@code <}{@link String
	 *         }{@code >}
	 *
	 */
	public JAXBElement<String> getCentroCosto() {
		return centroCosto;
	}

	/**
	 * Sets the value of the centroCosto property.
	 *
	 * @param value allowed object is {@link JAXBElement }{@code <}{@link String
	 *              }{@code >}
	 *
	 */
	public void setCentroCosto(JAXBElement<String> value) {
		this.centroCosto = (value);
	}

	/**
	 * Gets the value of the cliente property.
	 *
	 * @return possible object is {@link JAXBElement }{@code <}{@link Cliente
	 *         }{@code >}
	 *
	 */
	public JAXBElement<Cliente> getCliente() {
		return cliente;
	}

	/**
	 * Sets the value of the cliente property.
	 *
	 * @param value allowed object is {@link JAXBElement }{@code <}{@link Cliente
	 *              }{@code >}
	 *
	 */
	public void setCliente(JAXBElement<Cliente> value) {
		this.cliente = (value);
	}

	/**
	 * Gets the value of the direccionEmbarque property.
	 *
	 * @return possible object is {@link JAXBElement }{@code <}{@link String
	 *         }{@code >}
	 *
	 */
	public JAXBElement<String> getDireccionEmbarque() {
		return direccionEmbarque;
	}

	/**
	 * Sets the value of the direccionEmbarque property.
	 *
	 * @param value allowed object is {@link JAXBElement }{@code <}{@link String
	 *              }{@code >}
	 *
	 */
	public void setDireccionEmbarque(JAXBElement<String> value) {
		this.direccionEmbarque = (value);
	}

	/**
	 * Gets the value of the documentoReferencia property.
	 *
	 * @return possible object is {@link JAXBElement
	 *         }{@code <}{@link DocumentoReferencia }{@code >}
	 *
	 */
	public JAXBElement<DocumentoReferencia> getDocumentoReferencia() {
		return documentoReferencia;
	}

	/**
	 * Sets the value of the documentoReferencia property.
	 *
	 * @param value allowed object is {@link JAXBElement
	 *              }{@code <}{@link DocumentoReferencia }{@code >}
	 *
	 */
	public void setDocumentoReferencia(JAXBElement<DocumentoReferencia> value) {
		this.documentoReferencia = (value);
	}

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
	 * Gets the value of the glosaRetencion property.
	 *
	 * @return possible object is {@link JAXBElement }{@code <}{@link String
	 *         }{@code >}
	 *
	 */
	public JAXBElement<String> getGlosaRetencion() {
		return glosaRetencion;
	}

	/**
	 * Sets the value of the glosaRetencion property.
	 *
	 * @param value allowed object is {@link JAXBElement }{@code <}{@link String
	 *              }{@code >}
	 *
	 */
	public void setGlosaRetencion(JAXBElement<String> value) {
		this.glosaRetencion = (value);
	}

	/**
	 * Gets the value of the horaEmision property.
	 *
	 * @return possible object is {@link JAXBElement }{@code <}{@link String
	 *         }{@code >}
	 *
	 */
	public JAXBElement<String> getHoraEmision() {
		return horaEmision;
	}

	/**
	 * Sets the value of the horaEmision property.
	 *
	 * @param value allowed object is {@link JAXBElement }{@code <}{@link String
	 *              }{@code >}
	 *
	 */
	public void setHoraEmision(JAXBElement<String> value) {
		this.horaEmision = (value);
	}

	/**
	 * Gets the value of the igv property.
	 *
	 * @return possible object is {@link Double }
	 *
	 */
	public Double getIgv() {
		return igv;
	}

	/**
	 * Sets the value of the igv property.
	 *
	 * @param value allowed object is {@link Double }
	 *
	 */
	public void setIgv(Double value) {
		this.igv = value;
	}

	/**
	 * Gets the value of the informacionAdicional property.
	 *
	 * @return possible object is {@link JAXBElement
	 *         }{@code <}{@link InformacionAdicional }{@code >}
	 *
	 */
	public JAXBElement<InformacionAdicional> getInformacionAdicional() {
		return informacionAdicional;
	}

	/**
	 * Sets the value of the informacionAdicional property.
	 *
	 * @param value allowed object is {@link JAXBElement
	 *              }{@code <}{@link InformacionAdicional }{@code >}
	 *
	 */
	public void setInformacionAdicional(JAXBElement<InformacionAdicional> value) {
		this.informacionAdicional = (value);
	}

	/**
	 * Gets the value of the isCredito property.
	 *
	 * @return possible object is {@link Boolean }
	 *
	 */
	public Boolean isIsCredito() {
		return isCredito;
	}

	/**
	 * Sets the value of the isCredito property.
	 *
	 * @param value allowed object is {@link Boolean }
	 *
	 */
	public void setIsCredito(Boolean value) {
		this.isCredito = value;
	}

	/**
	 * Gets the value of the isSOUE property.
	 *
	 * @return possible object is {@link Boolean }
	 *
	 */
	public Boolean isIsSOUE() {
		return isSOUE;
	}

	/**
	 * Sets the value of the isSOUE property.
	 *
	 * @param value allowed object is {@link Boolean }
	 *
	 */
	public void setIsSOUE(Boolean value) {
		this.isSOUE = value;
	}

	/**
	 * Gets the value of the listDetalleVenta property.
	 *
	 * @return possible object is {@link JAXBElement
	 *         }{@code <}{@link ArrayOfDetalleVenta }{@code >}
	 *
	 */
	public JAXBElement<ArrayOfDetalleVenta> getListDetalleVenta() {
		return listDetalleVenta;
	}

	/**
	 * Sets the value of the listDetalleVenta property.
	 *
	 * @param value allowed object is {@link JAXBElement
	 *              }{@code <}{@link ArrayOfDetalleVenta }{@code >}
	 *
	 */
	public void setListDetalleVenta(JAXBElement<ArrayOfDetalleVenta> value) {
		this.listDetalleVenta = (value);
	}

	/**
	 * Gets the value of the montoSubTotal property.
	 *
	 * @return possible object is {@link Double }
	 *
	 */
	public Double getMontoSubTotal() {
		return montoSubTotal;
	}

	/**
	 * Sets the value of the montoSubTotal property.
	 *
	 * @param value allowed object is {@link Double }
	 *
	 */
	public void setMontoSubTotal(Double value) {
		this.montoSubTotal = value;
	}

	/**
	 * Gets the value of the montoTotal property.
	 *
	 * @return possible object is {@link Double }
	 *
	 */
	public Double getMontoTotal() {
		return montoTotal;
	}

	/**
	 * Sets the value of the montoTotal property.
	 *
	 * @param value allowed object is {@link Double }
	 *
	 */
	public void setMontoTotal(Double value) {
		this.montoTotal = value;
	}

	/**
	 * Gets the value of the montoTotalDescuento property.
	 *
	 * @return possible object is {@link Double }
	 *
	 */
	public Double getMontoTotalDescuento() {
		return montoTotalDescuento;
	}

	/**
	 * Sets the value of the montoTotalDescuento property.
	 *
	 * @param value allowed object is {@link Double }
	 *
	 */
	public void setMontoTotalDescuento(Double value) {
		this.montoTotalDescuento = value;
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
	 * Gets the value of the numeroPrefactura property.
	 *
	 * @return possible object is {@link JAXBElement }{@code <}{@link String
	 *         }{@code >}
	 *
	 */
	public JAXBElement<String> getNumeroPrefactura() {
		return numeroPrefactura;
	}

	/**
	 * Sets the value of the numeroPrefactura property.
	 *
	 * @param value allowed object is {@link JAXBElement }{@code <}{@link String
	 *              }{@code >}
	 *
	 */
	public void setNumeroPrefactura(JAXBElement<String> value) {
		this.numeroPrefactura = (value);
	}

	/**
	 * Gets the value of the numeroSerie property.
	 *
	 * @return possible object is {@link JAXBElement }{@code <}{@link String
	 *         }{@code >}
	 *
	 */
	public JAXBElement<String> getNumeroSerie() {
		return numeroSerie;
	}

	/**
	 * Sets the value of the numeroSerie property.
	 *
	 * @param value allowed object is {@link JAXBElement }{@code <}{@link String
	 *              }{@code >}
	 *
	 */
	public void setNumeroSerie(JAXBElement<String> value) {
		this.numeroSerie = (value);
	}

	/**
	 * Gets the value of the observaciones property.
	 *
	 * @return possible object is {@link JAXBElement }{@code <}{@link String
	 *         }{@code >}
	 *
	 */
	public JAXBElement<String> getObservaciones() {
		return observaciones;
	}

	/**
	 * Sets the value of the observaciones property.
	 *
	 * @param value allowed object is {@link JAXBElement }{@code <}{@link String
	 *              }{@code >}
	 *
	 */
	public void setObservaciones(JAXBElement<String> value) {
		this.observaciones = (value);
	}

	/**
	 * Gets the value of the producto property.
	 *
	 * @return possible object is {@link JAXBElement }{@code <}{@link String
	 *         }{@code >}
	 *
	 */
	public JAXBElement<String> getProducto() {
		return producto;
	}

	/**
	 * Sets the value of the producto property.
	 *
	 * @param value allowed object is {@link JAXBElement }{@code <}{@link String
	 *              }{@code >}
	 *
	 */
	public void setProducto(JAXBElement<String> value) {
		this.producto = (value);
	}

	/**
	 * Gets the value of the rucEmpresa property.
	 *
	 * @return possible object is {@link JAXBElement }{@code <}{@link String
	 *         }{@code >}
	 *
	 */
	public JAXBElement<String> getRucEmpresa() {
		return rucEmpresa;
	}

	/**
	 * Sets the value of the rucEmpresa property.
	 *
	 * @param value allowed object is {@link JAXBElement }{@code <}{@link String
	 *              }{@code >}
	 *
	 */
	public void setRucEmpresa(JAXBElement<String> value) {
		this.rucEmpresa = (value);
	}

	/**
	 * Gets the value of the tipoComprobanteID property.
	 *
	 * @return possible object is {@link JAXBElement }{@code <}{@link String
	 *         }{@code >}
	 *
	 */
	public JAXBElement<String> getTipoComprobanteID() {
		return tipoComprobanteID;
	}

	/**
	 * Sets the value of the tipoComprobanteID property.
	 *
	 * @param value allowed object is {@link JAXBElement }{@code <}{@link String
	 *              }{@code >}
	 *
	 */
	public void setTipoComprobanteID(JAXBElement<String> value) {
		this.tipoComprobanteID = (value);
	}

	/**
	 * Gets the value of the tipoMonedaSoles property.
	 *
	 * @return possible object is {@link Boolean }
	 *
	 */
	public Boolean isTipoMonedaSoles() {
		return tipoMonedaSoles;
	}

	/**
	 * Sets the value of the tipoMonedaSoles property.
	 *
	 * @param value allowed object is {@link Boolean }
	 *
	 */
	public void setTipoMonedaSoles(Boolean value) {
		this.tipoMonedaSoles = value;
	}

	/**
	 * Gets the value of the tipoVenta property.
	 *
	 * @return possible object is {@link Integer }
	 *
	 */
	public Integer getTipoVenta() {
		return tipoVenta;
	}

	/**
	 * Sets the value of the tipoVenta property.
	 *
	 * @param value allowed object is {@link Integer }
	 *
	 */
	public void setTipoVenta(Integer value) {
		this.tipoVenta = value;
	}

	/**
	 * Gets the value of the usuarioID property.
	 *
	 * @return possible object is {@link Long }
	 *
	 */
	public Long getUsuarioID() {
		return usuarioID;
	}

	/**
	 * Sets the value of the usuarioID property.
	 *
	 * @param value allowed object is {@link Long }
	 *
	 */
	public void setUsuarioID(Long value) {
		this.usuarioID = value;
	}

	/**
	 * Gets the value of the usuarioInsercion property.
	 *
	 * @return possible object is {@link JAXBElement }{@code <}{@link String
	 *         }{@code >}
	 *
	 */
	public JAXBElement<String> getUsuarioInsercion() {
		return usuarioInsercion;
	}

	/**
	 * Sets the value of the usuarioInsercion property.
	 *
	 * @param value allowed object is {@link JAXBElement }{@code <}{@link String
	 *              }{@code >}
	 *
	 */
	public void setUsuarioInsercion(JAXBElement<String> value) {
		this.usuarioInsercion = (value);
	}

	/**
	 * Gets the value of the usuarioModificacion property.
	 *
	 * @return possible object is {@link JAXBElement }{@code <}{@link String
	 *         }{@code >}
	 *
	 */
	public JAXBElement<String> getUsuarioModificacion() {
		return usuarioModificacion;
	}

	/**
	 * Sets the value of the usuarioModificacion property.
	 *
	 * @param value allowed object is {@link JAXBElement }{@code <}{@link String
	 *              }{@code >}
	 *
	 */
	public void setUsuarioModificacion(JAXBElement<String> value) {
		this.usuarioModificacion = (value);
	}

}
