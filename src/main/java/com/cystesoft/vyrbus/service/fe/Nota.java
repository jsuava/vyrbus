
package com.cystesoft.vyrbus.service.fe;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlType;

/**
 * <p>
 * Java class for Nota complex type.
 * 
 * <p>
 * The following schema fragment specifies the expected content contained within
 * this class.
 * 
 * <pre>
 * &lt;complexType name="Nota">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="agenciaID" type="{http://www.w3.org/2001/XMLSchema}long" minOccurs="0"/>
 *         &lt;element name="cliente" type="{http://schemas.datacontract.org/2004/07/FEService.Input}Cliente" minOccurs="0"/>
 *         &lt;element name="codigoTipoNota" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="descripcionSustento" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="descripcionTipoNota" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="documentoReferencia" type="{http://schemas.datacontract.org/2004/07/FEService.Input}DocumentoReferencia" minOccurs="0"/>
 *         &lt;element name="fechaEmision" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="horaEmision" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="igv" type="{http://www.w3.org/2001/XMLSchema}double" minOccurs="0"/>
 *         &lt;element name="informacionAdicional" type="{http://schemas.datacontract.org/2004/07/FEService.Input}InformacionAdicional" minOccurs="0"/>
 *         &lt;element name="numeroCorrelativo" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="numeroSerie" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="rucEmpresa" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="subtotal" type="{http://www.w3.org/2001/XMLSchema}double" minOccurs="0"/>
 *         &lt;element name="tipoComprobanteID" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="tipoMonedaSoles" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *         &lt;element name="tipoVenta" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="total" type="{http://www.w3.org/2001/XMLSchema}double" minOccurs="0"/>
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
@XmlType(name = "Nota", namespace = "http://schemas.datacontract.org/2004/07/FEService.Input", propOrder = {
		"agenciaID", "cliente", "codigoTipoNota", "descripcionSustento", "descripcionTipoNota", "documentoReferencia",
		"fechaEmision", "horaEmision", "igv", "informacionAdicional", "numeroCorrelativo", "numeroSerie", "rucEmpresa",
		"subtotal", "tipoComprobanteID", "tipoMonedaSoles", "tipoVenta", "total", "usuarioID", "usuarioInsercion",
		"usuarioModificacion" })
public class Nota {

	protected Long agenciaID;
	@XmlElementRef(name = "cliente", namespace = "http://schemas.datacontract.org/2004/07/FEService.Input", type = JAXBElement.class)
	protected JAXBElement<Cliente> cliente;
	@XmlElementRef(name = "codigoTipoNota", namespace = "http://schemas.datacontract.org/2004/07/FEService.Input", type = JAXBElement.class)
	protected JAXBElement<String> codigoTipoNota;
	@XmlElementRef(name = "descripcionSustento", namespace = "http://schemas.datacontract.org/2004/07/FEService.Input", type = JAXBElement.class)
	protected JAXBElement<String> descripcionSustento;
	@XmlElementRef(name = "descripcionTipoNota", namespace = "http://schemas.datacontract.org/2004/07/FEService.Input", type = JAXBElement.class)
	protected JAXBElement<String> descripcionTipoNota;
	@XmlElementRef(name = "documentoReferencia", namespace = "http://schemas.datacontract.org/2004/07/FEService.Input", type = JAXBElement.class)
	protected JAXBElement<DocumentoReferencia> documentoReferencia;
	@XmlElementRef(name = "fechaEmision", namespace = "http://schemas.datacontract.org/2004/07/FEService.Input", type = JAXBElement.class)
	protected JAXBElement<String> fechaEmision;
	@XmlElementRef(name = "horaEmision", namespace = "http://schemas.datacontract.org/2004/07/FEService.Input", type = JAXBElement.class)
	protected JAXBElement<String> horaEmision;
	protected Double igv;
	@XmlElementRef(name = "informacionAdicional", namespace = "http://schemas.datacontract.org/2004/07/FEService.Input", type = JAXBElement.class)
	protected JAXBElement<InformacionAdicional> informacionAdicional;
	@XmlElementRef(name = "numeroCorrelativo", namespace = "http://schemas.datacontract.org/2004/07/FEService.Input", type = JAXBElement.class)
	protected JAXBElement<String> numeroCorrelativo;
	@XmlElementRef(name = "numeroSerie", namespace = "http://schemas.datacontract.org/2004/07/FEService.Input", type = JAXBElement.class)
	protected JAXBElement<String> numeroSerie;
	@XmlElementRef(name = "rucEmpresa", namespace = "http://schemas.datacontract.org/2004/07/FEService.Input", type = JAXBElement.class)
	protected JAXBElement<String> rucEmpresa;
	protected Double subtotal;
	@XmlElementRef(name = "tipoComprobanteID", namespace = "http://schemas.datacontract.org/2004/07/FEService.Input", type = JAXBElement.class)
	protected JAXBElement<String> tipoComprobanteID;
	protected Boolean tipoMonedaSoles;
	protected Integer tipoVenta;
	protected Double total;
	protected Long usuarioID;
	@XmlElementRef(name = "usuarioInsercion", namespace = "http://schemas.datacontract.org/2004/07/FEService.Input", type = JAXBElement.class)
	protected JAXBElement<String> usuarioInsercion;
	@XmlElementRef(name = "usuarioModificacion", namespace = "http://schemas.datacontract.org/2004/07/FEService.Input", type = JAXBElement.class)
	protected JAXBElement<String> usuarioModificacion;

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
		this.cliente = ((JAXBElement<Cliente>) value);
	}

	/**
	 * Gets the value of the codigoTipoNota property.
	 * 
	 * @return possible object is {@link JAXBElement }{@code <}{@link String
	 *         }{@code >}
	 * 
	 */
	public JAXBElement<String> getCodigoTipoNota() {
		return codigoTipoNota;
	}

	/**
	 * Sets the value of the codigoTipoNota property.
	 * 
	 * @param value allowed object is {@link JAXBElement }{@code <}{@link String
	 *              }{@code >}
	 * 
	 */
	public void setCodigoTipoNota(JAXBElement<String> value) {
		this.codigoTipoNota = ((JAXBElement<String>) value);
	}

	/**
	 * Gets the value of the descripcionSustento property.
	 * 
	 * @return possible object is {@link JAXBElement }{@code <}{@link String
	 *         }{@code >}
	 * 
	 */
	public JAXBElement<String> getDescripcionSustento() {
		return descripcionSustento;
	}

	/**
	 * Sets the value of the descripcionSustento property.
	 * 
	 * @param value allowed object is {@link JAXBElement }{@code <}{@link String
	 *              }{@code >}
	 * 
	 */
	public void setDescripcionSustento(JAXBElement<String> value) {
		this.descripcionSustento = ((JAXBElement<String>) value);
	}

	/**
	 * Gets the value of the descripcionTipoNota property.
	 * 
	 * @return possible object is {@link JAXBElement }{@code <}{@link String
	 *         }{@code >}
	 * 
	 */
	public JAXBElement<String> getDescripcionTipoNota() {
		return descripcionTipoNota;
	}

	/**
	 * Sets the value of the descripcionTipoNota property.
	 * 
	 * @param value allowed object is {@link JAXBElement }{@code <}{@link String
	 *              }{@code >}
	 * 
	 */
	public void setDescripcionTipoNota(JAXBElement<String> value) {
		this.descripcionTipoNota = ((JAXBElement<String>) value);
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
		this.documentoReferencia = ((JAXBElement<DocumentoReferencia>) value);
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
		this.fechaEmision = ((JAXBElement<String>) value);
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
		this.horaEmision = ((JAXBElement<String>) value);
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
		this.informacionAdicional = ((JAXBElement<InformacionAdicional>) value);
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
		this.numeroCorrelativo = ((JAXBElement<String>) value);
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
		this.numeroSerie = ((JAXBElement<String>) value);
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
		this.rucEmpresa = ((JAXBElement<String>) value);
	}

	/**
	 * Gets the value of the subtotal property.
	 * 
	 * @return possible object is {@link Double }
	 * 
	 */
	public Double getSubtotal() {
		return subtotal;
	}

	/**
	 * Sets the value of the subtotal property.
	 * 
	 * @param value allowed object is {@link Double }
	 * 
	 */
	public void setSubtotal(Double value) {
		this.subtotal = value;
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
		this.tipoComprobanteID = ((JAXBElement<String>) value);
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
	 * Gets the value of the total property.
	 * 
	 * @return possible object is {@link Double }
	 * 
	 */
	public Double getTotal() {
		return total;
	}

	/**
	 * Sets the value of the total property.
	 * 
	 * @param value allowed object is {@link Double }
	 * 
	 */
	public void setTotal(Double value) {
		this.total = value;
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
		this.usuarioInsercion = ((JAXBElement<String>) value);
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
		this.usuarioModificacion = ((JAXBElement<String>) value);
	}

}
