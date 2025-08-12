
package com.cystesoft.vyrbus.service.fe;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlType;

/**
 * <p>
 * Java class for GRE complex type.
 * 
 * <p>
 * The following schema fragment specifies the expected content contained within
 * this class.
 * 
 * <pre>
 * &lt;complexType name="GRE">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="agencia_id" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="destinatario_nombres" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="destinatario_numeroDocumento" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="destinatario_tipoDocumento" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="fechaEmision" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="fechaInicioTranslado" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="greDocumentoReferencia" type="{http://schemas.datacontract.org/2004/07/FEService.Input}GREDocumentoReferencia" minOccurs="0"/>
 *         &lt;element name="grePilotoCopiloto_1" type="{http://schemas.datacontract.org/2004/07/FEService.Input}GREPiloto" minOccurs="0"/>
 *         &lt;element name="grePilotoCopiloto_2" type="{http://schemas.datacontract.org/2004/07/FEService.Input}GREPiloto" minOccurs="0"/>
 *         &lt;element name="grePilotoPrincipal" type="{http://schemas.datacontract.org/2004/07/FEService.Input}GREPiloto" minOccurs="0"/>
 *         &lt;element name="grr" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="grr_rucEmisor" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="horaEmision" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="listDetalle" type="{http://schemas.datacontract.org/2004/07/FEService.Input}ArrayOfGREDetalle" minOccurs="0"/>
 *         &lt;element name="nota" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="numeroGRE" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="numeroRegistroMTC" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="puntoLlegada_direccion" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="puntoLlegada_ubigeo" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="puntoPartida_direccion" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="puntoPartida_ubigeo" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="remitente_nombres" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="remitente_numeroDocumento" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="remitente_tipoDocumento" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="rucEmpresa" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="tipoDocumento" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="totalPesoCarga" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="usuarioInsercion" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="usuario_id" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="vehiculo_certificadoHabilitacion" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="vehiculo_certificadoHabilitacion_sec1" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="vehiculo_numeroPlaca" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="vehiculo_numeroPlaca_sec1" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "GRE", namespace = "http://schemas.datacontract.org/2004/07/FEService.Input", propOrder = { "agenciaId",
		"destinatarioNombres", "destinatarioNumeroDocumento", "destinatarioTipoDocumento", "fechaEmision",
		"fechaInicioTranslado", "greDocumentoReferencia", "grePilotoCopiloto1", "grePilotoCopiloto2",
		"grePilotoPrincipal", "grr", "grrRucEmisor", "horaEmision", "listDetalle", "nota", "numeroGRE",
		"numeroRegistroMTC", "puntoLlegadaDireccion", "puntoLlegadaUbigeo", "puntoPartidaDireccion",
		"puntoPartidaUbigeo", "remitenteNombres", "remitenteNumeroDocumento", "remitenteTipoDocumento", "rucEmpresa",
		"tipoDocumento", "totalPesoCarga", "usuarioInsercion", "usuarioId", "vehiculoCertificadoHabilitacion",
		"vehiculoCertificadoHabilitacionSec1", "vehiculoNumeroPlaca", "vehiculoNumeroPlacaSec1" })
public class GRE {

	@XmlElement(name = "agencia_id")
	protected Integer agenciaId;
	@XmlElementRef(name = "destinatario_nombres", namespace = "http://schemas.datacontract.org/2004/07/FEService.Input", type = JAXBElement.class)
	protected JAXBElement<String> destinatarioNombres;
	@XmlElementRef(name = "destinatario_numeroDocumento", namespace = "http://schemas.datacontract.org/2004/07/FEService.Input", type = JAXBElement.class)
	protected JAXBElement<String> destinatarioNumeroDocumento;
	@XmlElementRef(name = "destinatario_tipoDocumento", namespace = "http://schemas.datacontract.org/2004/07/FEService.Input", type = JAXBElement.class)
	protected JAXBElement<String> destinatarioTipoDocumento;
	@XmlElementRef(name = "fechaEmision", namespace = "http://schemas.datacontract.org/2004/07/FEService.Input", type = JAXBElement.class)
	protected JAXBElement<String> fechaEmision;
	@XmlElementRef(name = "fechaInicioTranslado", namespace = "http://schemas.datacontract.org/2004/07/FEService.Input", type = JAXBElement.class)
	protected JAXBElement<String> fechaInicioTranslado;
	@XmlElementRef(name = "greDocumentoReferencia", namespace = "http://schemas.datacontract.org/2004/07/FEService.Input", type = JAXBElement.class)
	protected JAXBElement<GREDocumentoReferencia> greDocumentoReferencia;
	@XmlElementRef(name = "grePilotoCopiloto_1", namespace = "http://schemas.datacontract.org/2004/07/FEService.Input", type = JAXBElement.class)
	protected JAXBElement<GREPiloto> grePilotoCopiloto1;
	@XmlElementRef(name = "grePilotoCopiloto_2", namespace = "http://schemas.datacontract.org/2004/07/FEService.Input", type = JAXBElement.class)
	protected JAXBElement<GREPiloto> grePilotoCopiloto2;
	@XmlElementRef(name = "grePilotoPrincipal", namespace = "http://schemas.datacontract.org/2004/07/FEService.Input", type = JAXBElement.class)
	protected JAXBElement<GREPiloto> grePilotoPrincipal;
	@XmlElementRef(name = "grr", namespace = "http://schemas.datacontract.org/2004/07/FEService.Input", type = JAXBElement.class)
	protected JAXBElement<String> grr;
	@XmlElementRef(name = "grr_rucEmisor", namespace = "http://schemas.datacontract.org/2004/07/FEService.Input", type = JAXBElement.class)
	protected JAXBElement<String> grrRucEmisor;
	@XmlElementRef(name = "horaEmision", namespace = "http://schemas.datacontract.org/2004/07/FEService.Input", type = JAXBElement.class)
	protected JAXBElement<String> horaEmision;
	@XmlElementRef(name = "listDetalle", namespace = "http://schemas.datacontract.org/2004/07/FEService.Input", type = JAXBElement.class)
	protected JAXBElement<ArrayOfGREDetalle> listDetalle;
	@XmlElementRef(name = "nota", namespace = "http://schemas.datacontract.org/2004/07/FEService.Input", type = JAXBElement.class)
	protected JAXBElement<String> nota;
	@XmlElementRef(name = "numeroGRE", namespace = "http://schemas.datacontract.org/2004/07/FEService.Input", type = JAXBElement.class)
	protected JAXBElement<String> numeroGRE;
	@XmlElementRef(name = "numeroRegistroMTC", namespace = "http://schemas.datacontract.org/2004/07/FEService.Input", type = JAXBElement.class)
	protected JAXBElement<String> numeroRegistroMTC;
	@XmlElementRef(name = "puntoLlegada_direccion", namespace = "http://schemas.datacontract.org/2004/07/FEService.Input", type = JAXBElement.class)
	protected JAXBElement<String> puntoLlegadaDireccion;
	@XmlElementRef(name = "puntoLlegada_ubigeo", namespace = "http://schemas.datacontract.org/2004/07/FEService.Input", type = JAXBElement.class)
	protected JAXBElement<String> puntoLlegadaUbigeo;
	@XmlElementRef(name = "puntoPartida_direccion", namespace = "http://schemas.datacontract.org/2004/07/FEService.Input", type = JAXBElement.class)
	protected JAXBElement<String> puntoPartidaDireccion;
	@XmlElementRef(name = "puntoPartida_ubigeo", namespace = "http://schemas.datacontract.org/2004/07/FEService.Input", type = JAXBElement.class)
	protected JAXBElement<String> puntoPartidaUbigeo;
	@XmlElementRef(name = "remitente_nombres", namespace = "http://schemas.datacontract.org/2004/07/FEService.Input", type = JAXBElement.class)
	protected JAXBElement<String> remitenteNombres;
	@XmlElementRef(name = "remitente_numeroDocumento", namespace = "http://schemas.datacontract.org/2004/07/FEService.Input", type = JAXBElement.class)
	protected JAXBElement<String> remitenteNumeroDocumento;
	@XmlElementRef(name = "remitente_tipoDocumento", namespace = "http://schemas.datacontract.org/2004/07/FEService.Input", type = JAXBElement.class)
	protected JAXBElement<String> remitenteTipoDocumento;
	@XmlElementRef(name = "rucEmpresa", namespace = "http://schemas.datacontract.org/2004/07/FEService.Input", type = JAXBElement.class)
	protected JAXBElement<String> rucEmpresa;
	@XmlElementRef(name = "tipoDocumento", namespace = "http://schemas.datacontract.org/2004/07/FEService.Input", type = JAXBElement.class)
	protected JAXBElement<String> tipoDocumento;
	@XmlElementRef(name = "totalPesoCarga", namespace = "http://schemas.datacontract.org/2004/07/FEService.Input", type = JAXBElement.class)
	protected JAXBElement<String> totalPesoCarga;
	@XmlElementRef(name = "usuarioInsercion", namespace = "http://schemas.datacontract.org/2004/07/FEService.Input", type = JAXBElement.class)
	protected JAXBElement<String> usuarioInsercion;
	@XmlElement(name = "usuario_id")
	protected Integer usuarioId;
	@XmlElementRef(name = "vehiculo_certificadoHabilitacion", namespace = "http://schemas.datacontract.org/2004/07/FEService.Input", type = JAXBElement.class)
	protected JAXBElement<String> vehiculoCertificadoHabilitacion;
	@XmlElementRef(name = "vehiculo_certificadoHabilitacion_sec1", namespace = "http://schemas.datacontract.org/2004/07/FEService.Input", type = JAXBElement.class)
	protected JAXBElement<String> vehiculoCertificadoHabilitacionSec1;
	@XmlElementRef(name = "vehiculo_numeroPlaca", namespace = "http://schemas.datacontract.org/2004/07/FEService.Input", type = JAXBElement.class)
	protected JAXBElement<String> vehiculoNumeroPlaca;
	@XmlElementRef(name = "vehiculo_numeroPlaca_sec1", namespace = "http://schemas.datacontract.org/2004/07/FEService.Input", type = JAXBElement.class)
	protected JAXBElement<String> vehiculoNumeroPlacaSec1;

	/**
	 * Gets the value of the agenciaId property.
	 * 
	 * @return possible object is {@link Integer }
	 * 
	 */
	public Integer getAgenciaId() {
		return agenciaId;
	}

	/**
	 * Sets the value of the agenciaId property.
	 * 
	 * @param value allowed object is {@link Integer }
	 * 
	 */
	public void setAgenciaId(Integer value) {
		this.agenciaId = value;
	}

	/**
	 * Gets the value of the destinatarioNombres property.
	 * 
	 * @return possible object is {@link JAXBElement }{@code <}{@link String
	 *         }{@code >}
	 * 
	 */
	public JAXBElement<String> getDestinatarioNombres() {
		return destinatarioNombres;
	}

	/**
	 * Sets the value of the destinatarioNombres property.
	 * 
	 * @param value allowed object is {@link JAXBElement }{@code <}{@link String
	 *              }{@code >}
	 * 
	 */
	public void setDestinatarioNombres(JAXBElement<String> value) {
		this.destinatarioNombres = ((JAXBElement<String>) value);
	}

	/**
	 * Gets the value of the destinatarioNumeroDocumento property.
	 * 
	 * @return possible object is {@link JAXBElement }{@code <}{@link String
	 *         }{@code >}
	 * 
	 */
	public JAXBElement<String> getDestinatarioNumeroDocumento() {
		return destinatarioNumeroDocumento;
	}

	/**
	 * Sets the value of the destinatarioNumeroDocumento property.
	 * 
	 * @param value allowed object is {@link JAXBElement }{@code <}{@link String
	 *              }{@code >}
	 * 
	 */
	public void setDestinatarioNumeroDocumento(JAXBElement<String> value) {
		this.destinatarioNumeroDocumento = ((JAXBElement<String>) value);
	}

	/**
	 * Gets the value of the destinatarioTipoDocumento property.
	 * 
	 * @return possible object is {@link JAXBElement }{@code <}{@link String
	 *         }{@code >}
	 * 
	 */
	public JAXBElement<String> getDestinatarioTipoDocumento() {
		return destinatarioTipoDocumento;
	}

	/**
	 * Sets the value of the destinatarioTipoDocumento property.
	 * 
	 * @param value allowed object is {@link JAXBElement }{@code <}{@link String
	 *              }{@code >}
	 * 
	 */
	public void setDestinatarioTipoDocumento(JAXBElement<String> value) {
		this.destinatarioTipoDocumento = ((JAXBElement<String>) value);
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
	 * Gets the value of the fechaInicioTranslado property.
	 * 
	 * @return possible object is {@link JAXBElement }{@code <}{@link String
	 *         }{@code >}
	 * 
	 */
	public JAXBElement<String> getFechaInicioTranslado() {
		return fechaInicioTranslado;
	}

	/**
	 * Sets the value of the fechaInicioTranslado property.
	 * 
	 * @param value allowed object is {@link JAXBElement }{@code <}{@link String
	 *              }{@code >}
	 * 
	 */
	public void setFechaInicioTranslado(JAXBElement<String> value) {
		this.fechaInicioTranslado = ((JAXBElement<String>) value);
	}

	/**
	 * Gets the value of the greDocumentoReferencia property.
	 * 
	 * @return possible object is {@link JAXBElement
	 *         }{@code <}{@link GREDocumentoReferencia }{@code >}
	 * 
	 */
	public JAXBElement<GREDocumentoReferencia> getGreDocumentoReferencia() {
		return greDocumentoReferencia;
	}

	/**
	 * Sets the value of the greDocumentoReferencia property.
	 * 
	 * @param value allowed object is {@link JAXBElement
	 *              }{@code <}{@link GREDocumentoReferencia }{@code >}
	 * 
	 */
	public void setGreDocumentoReferencia(JAXBElement<GREDocumentoReferencia> value) {
		this.greDocumentoReferencia = ((JAXBElement<GREDocumentoReferencia>) value);
	}

	/**
	 * Gets the value of the grePilotoCopiloto1 property.
	 * 
	 * @return possible object is {@link JAXBElement }{@code <}{@link GREPiloto
	 *         }{@code >}
	 * 
	 */
	public JAXBElement<GREPiloto> getGrePilotoCopiloto1() {
		return grePilotoCopiloto1;
	}

	/**
	 * Sets the value of the grePilotoCopiloto1 property.
	 * 
	 * @param value allowed object is {@link JAXBElement }{@code <}{@link GREPiloto
	 *              }{@code >}
	 * 
	 */
	public void setGrePilotoCopiloto1(JAXBElement<GREPiloto> value) {
		this.grePilotoCopiloto1 = ((JAXBElement<GREPiloto>) value);
	}

	/**
	 * Gets the value of the grePilotoCopiloto2 property.
	 * 
	 * @return possible object is {@link JAXBElement }{@code <}{@link GREPiloto
	 *         }{@code >}
	 * 
	 */
	public JAXBElement<GREPiloto> getGrePilotoCopiloto2() {
		return grePilotoCopiloto2;
	}

	/**
	 * Sets the value of the grePilotoCopiloto2 property.
	 * 
	 * @param value allowed object is {@link JAXBElement }{@code <}{@link GREPiloto
	 *              }{@code >}
	 * 
	 */
	public void setGrePilotoCopiloto2(JAXBElement<GREPiloto> value) {
		this.grePilotoCopiloto2 = ((JAXBElement<GREPiloto>) value);
	}

	/**
	 * Gets the value of the grePilotoPrincipal property.
	 * 
	 * @return possible object is {@link JAXBElement }{@code <}{@link GREPiloto
	 *         }{@code >}
	 * 
	 */
	public JAXBElement<GREPiloto> getGrePilotoPrincipal() {
		return grePilotoPrincipal;
	}

	/**
	 * Sets the value of the grePilotoPrincipal property.
	 * 
	 * @param value allowed object is {@link JAXBElement }{@code <}{@link GREPiloto
	 *              }{@code >}
	 * 
	 */
	public void setGrePilotoPrincipal(JAXBElement<GREPiloto> value) {
		this.grePilotoPrincipal = ((JAXBElement<GREPiloto>) value);
	}

	/**
	 * Gets the value of the grr property.
	 * 
	 * @return possible object is {@link JAXBElement }{@code <}{@link String
	 *         }{@code >}
	 * 
	 */
	public JAXBElement<String> getGrr() {
		return grr;
	}

	/**
	 * Sets the value of the grr property.
	 * 
	 * @param value allowed object is {@link JAXBElement }{@code <}{@link String
	 *              }{@code >}
	 * 
	 */
	public void setGrr(JAXBElement<String> value) {
		this.grr = ((JAXBElement<String>) value);
	}

	/**
	 * Gets the value of the grrRucEmisor property.
	 * 
	 * @return possible object is {@link JAXBElement }{@code <}{@link String
	 *         }{@code >}
	 * 
	 */
	public JAXBElement<String> getGrrRucEmisor() {
		return grrRucEmisor;
	}

	/**
	 * Sets the value of the grrRucEmisor property.
	 * 
	 * @param value allowed object is {@link JAXBElement }{@code <}{@link String
	 *              }{@code >}
	 * 
	 */
	public void setGrrRucEmisor(JAXBElement<String> value) {
		this.grrRucEmisor = ((JAXBElement<String>) value);
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
	 * Gets the value of the listDetalle property.
	 * 
	 * @return possible object is {@link JAXBElement
	 *         }{@code <}{@link ArrayOfGREDetalle }{@code >}
	 * 
	 */
	public JAXBElement<ArrayOfGREDetalle> getListDetalle() {
		return listDetalle;
	}

	/**
	 * Sets the value of the listDetalle property.
	 * 
	 * @param value allowed object is {@link JAXBElement
	 *              }{@code <}{@link ArrayOfGREDetalle }{@code >}
	 * 
	 */
	public void setListDetalle(JAXBElement<ArrayOfGREDetalle> value) {
		this.listDetalle = ((JAXBElement<ArrayOfGREDetalle>) value);
	}

	/**
	 * Gets the value of the nota property.
	 * 
	 * @return possible object is {@link JAXBElement }{@code <}{@link String
	 *         }{@code >}
	 * 
	 */
	public JAXBElement<String> getNota() {
		return nota;
	}

	/**
	 * Sets the value of the nota property.
	 * 
	 * @param value allowed object is {@link JAXBElement }{@code <}{@link String
	 *              }{@code >}
	 * 
	 */
	public void setNota(JAXBElement<String> value) {
		this.nota = ((JAXBElement<String>) value);
	}

	/**
	 * Gets the value of the numeroGRE property.
	 * 
	 * @return possible object is {@link JAXBElement }{@code <}{@link String
	 *         }{@code >}
	 * 
	 */
	public JAXBElement<String> getNumeroGRE() {
		return numeroGRE;
	}

	/**
	 * Sets the value of the numeroGRE property.
	 * 
	 * @param value allowed object is {@link JAXBElement }{@code <}{@link String
	 *              }{@code >}
	 * 
	 */
	public void setNumeroGRE(JAXBElement<String> value) {
		this.numeroGRE = ((JAXBElement<String>) value);
	}

	/**
	 * Gets the value of the numeroRegistroMTC property.
	 * 
	 * @return possible object is {@link JAXBElement }{@code <}{@link String
	 *         }{@code >}
	 * 
	 */
	public JAXBElement<String> getNumeroRegistroMTC() {
		return numeroRegistroMTC;
	}

	/**
	 * Sets the value of the numeroRegistroMTC property.
	 * 
	 * @param value allowed object is {@link JAXBElement }{@code <}{@link String
	 *              }{@code >}
	 * 
	 */
	public void setNumeroRegistroMTC(JAXBElement<String> value) {
		this.numeroRegistroMTC = ((JAXBElement<String>) value);
	}

	/**
	 * Gets the value of the puntoLlegadaDireccion property.
	 * 
	 * @return possible object is {@link JAXBElement }{@code <}{@link String
	 *         }{@code >}
	 * 
	 */
	public JAXBElement<String> getPuntoLlegadaDireccion() {
		return puntoLlegadaDireccion;
	}

	/**
	 * Sets the value of the puntoLlegadaDireccion property.
	 * 
	 * @param value allowed object is {@link JAXBElement }{@code <}{@link String
	 *              }{@code >}
	 * 
	 */
	public void setPuntoLlegadaDireccion(JAXBElement<String> value) {
		this.puntoLlegadaDireccion = ((JAXBElement<String>) value);
	}

	/**
	 * Gets the value of the puntoLlegadaUbigeo property.
	 * 
	 * @return possible object is {@link JAXBElement }{@code <}{@link String
	 *         }{@code >}
	 * 
	 */
	public JAXBElement<String> getPuntoLlegadaUbigeo() {
		return puntoLlegadaUbigeo;
	}

	/**
	 * Sets the value of the puntoLlegadaUbigeo property.
	 * 
	 * @param value allowed object is {@link JAXBElement }{@code <}{@link String
	 *              }{@code >}
	 * 
	 */
	public void setPuntoLlegadaUbigeo(JAXBElement<String> value) {
		this.puntoLlegadaUbigeo = ((JAXBElement<String>) value);
	}

	/**
	 * Gets the value of the puntoPartidaDireccion property.
	 * 
	 * @return possible object is {@link JAXBElement }{@code <}{@link String
	 *         }{@code >}
	 * 
	 */
	public JAXBElement<String> getPuntoPartidaDireccion() {
		return puntoPartidaDireccion;
	}

	/**
	 * Sets the value of the puntoPartidaDireccion property.
	 * 
	 * @param value allowed object is {@link JAXBElement }{@code <}{@link String
	 *              }{@code >}
	 * 
	 */
	public void setPuntoPartidaDireccion(JAXBElement<String> value) {
		this.puntoPartidaDireccion = ((JAXBElement<String>) value);
	}

	/**
	 * Gets the value of the puntoPartidaUbigeo property.
	 * 
	 * @return possible object is {@link JAXBElement }{@code <}{@link String
	 *         }{@code >}
	 * 
	 */
	public JAXBElement<String> getPuntoPartidaUbigeo() {
		return puntoPartidaUbigeo;
	}

	/**
	 * Sets the value of the puntoPartidaUbigeo property.
	 * 
	 * @param value allowed object is {@link JAXBElement }{@code <}{@link String
	 *              }{@code >}
	 * 
	 */
	public void setPuntoPartidaUbigeo(JAXBElement<String> value) {
		this.puntoPartidaUbigeo = ((JAXBElement<String>) value);
	}

	/**
	 * Gets the value of the remitenteNombres property.
	 * 
	 * @return possible object is {@link JAXBElement }{@code <}{@link String
	 *         }{@code >}
	 * 
	 */
	public JAXBElement<String> getRemitenteNombres() {
		return remitenteNombres;
	}

	/**
	 * Sets the value of the remitenteNombres property.
	 * 
	 * @param value allowed object is {@link JAXBElement }{@code <}{@link String
	 *              }{@code >}
	 * 
	 */
	public void setRemitenteNombres(JAXBElement<String> value) {
		this.remitenteNombres = ((JAXBElement<String>) value);
	}

	/**
	 * Gets the value of the remitenteNumeroDocumento property.
	 * 
	 * @return possible object is {@link JAXBElement }{@code <}{@link String
	 *         }{@code >}
	 * 
	 */
	public JAXBElement<String> getRemitenteNumeroDocumento() {
		return remitenteNumeroDocumento;
	}

	/**
	 * Sets the value of the remitenteNumeroDocumento property.
	 * 
	 * @param value allowed object is {@link JAXBElement }{@code <}{@link String
	 *              }{@code >}
	 * 
	 */
	public void setRemitenteNumeroDocumento(JAXBElement<String> value) {
		this.remitenteNumeroDocumento = ((JAXBElement<String>) value);
	}

	/**
	 * Gets the value of the remitenteTipoDocumento property.
	 * 
	 * @return possible object is {@link JAXBElement }{@code <}{@link String
	 *         }{@code >}
	 * 
	 */
	public JAXBElement<String> getRemitenteTipoDocumento() {
		return remitenteTipoDocumento;
	}

	/**
	 * Sets the value of the remitenteTipoDocumento property.
	 * 
	 * @param value allowed object is {@link JAXBElement }{@code <}{@link String
	 *              }{@code >}
	 * 
	 */
	public void setRemitenteTipoDocumento(JAXBElement<String> value) {
		this.remitenteTipoDocumento = ((JAXBElement<String>) value);
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
	 * Gets the value of the tipoDocumento property.
	 * 
	 * @return possible object is {@link JAXBElement }{@code <}{@link String
	 *         }{@code >}
	 * 
	 */
	public JAXBElement<String> getTipoDocumento() {
		return tipoDocumento;
	}

	/**
	 * Sets the value of the tipoDocumento property.
	 * 
	 * @param value allowed object is {@link JAXBElement }{@code <}{@link String
	 *              }{@code >}
	 * 
	 */
	public void setTipoDocumento(JAXBElement<String> value) {
		this.tipoDocumento = ((JAXBElement<String>) value);
	}

	/**
	 * Gets the value of the totalPesoCarga property.
	 * 
	 * @return possible object is {@link JAXBElement }{@code <}{@link String
	 *         }{@code >}
	 * 
	 */
	public JAXBElement<String> getTotalPesoCarga() {
		return totalPesoCarga;
	}

	/**
	 * Sets the value of the totalPesoCarga property.
	 * 
	 * @param value allowed object is {@link JAXBElement }{@code <}{@link String
	 *              }{@code >}
	 * 
	 */
	public void setTotalPesoCarga(JAXBElement<String> value) {
		this.totalPesoCarga = ((JAXBElement<String>) value);
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
	 * Gets the value of the usuarioId property.
	 * 
	 * @return possible object is {@link Integer }
	 * 
	 */
	public Integer getUsuarioId() {
		return usuarioId;
	}

	/**
	 * Sets the value of the usuarioId property.
	 * 
	 * @param value allowed object is {@link Integer }
	 * 
	 */
	public void setUsuarioId(Integer value) {
		this.usuarioId = value;
	}

	/**
	 * Gets the value of the vehiculoCertificadoHabilitacion property.
	 * 
	 * @return possible object is {@link JAXBElement }{@code <}{@link String
	 *         }{@code >}
	 * 
	 */
	public JAXBElement<String> getVehiculoCertificadoHabilitacion() {
		return vehiculoCertificadoHabilitacion;
	}

	/**
	 * Sets the value of the vehiculoCertificadoHabilitacion property.
	 * 
	 * @param value allowed object is {@link JAXBElement }{@code <}{@link String
	 *              }{@code >}
	 * 
	 */
	public void setVehiculoCertificadoHabilitacion(JAXBElement<String> value) {
		this.vehiculoCertificadoHabilitacion = ((JAXBElement<String>) value);
	}

	/**
	 * Gets the value of the vehiculoCertificadoHabilitacionSec1 property.
	 * 
	 * @return possible object is {@link JAXBElement }{@code <}{@link String
	 *         }{@code >}
	 * 
	 */
	public JAXBElement<String> getVehiculoCertificadoHabilitacionSec1() {
		return vehiculoCertificadoHabilitacionSec1;
	}

	/**
	 * Sets the value of the vehiculoCertificadoHabilitacionSec1 property.
	 * 
	 * @param value allowed object is {@link JAXBElement }{@code <}{@link String
	 *              }{@code >}
	 * 
	 */
	public void setVehiculoCertificadoHabilitacionSec1(JAXBElement<String> value) {
		this.vehiculoCertificadoHabilitacionSec1 = ((JAXBElement<String>) value);
	}

	/**
	 * Gets the value of the vehiculoNumeroPlaca property.
	 * 
	 * @return possible object is {@link JAXBElement }{@code <}{@link String
	 *         }{@code >}
	 * 
	 */
	public JAXBElement<String> getVehiculoNumeroPlaca() {
		return vehiculoNumeroPlaca;
	}

	/**
	 * Sets the value of the vehiculoNumeroPlaca property.
	 * 
	 * @param value allowed object is {@link JAXBElement }{@code <}{@link String
	 *              }{@code >}
	 * 
	 */
	public void setVehiculoNumeroPlaca(JAXBElement<String> value) {
		this.vehiculoNumeroPlaca = ((JAXBElement<String>) value);
	}

	/**
	 * Gets the value of the vehiculoNumeroPlacaSec1 property.
	 * 
	 * @return possible object is {@link JAXBElement }{@code <}{@link String
	 *         }{@code >}
	 * 
	 */
	public JAXBElement<String> getVehiculoNumeroPlacaSec1() {
		return vehiculoNumeroPlacaSec1;
	}

	/**
	 * Sets the value of the vehiculoNumeroPlacaSec1 property.
	 * 
	 * @param value allowed object is {@link JAXBElement }{@code <}{@link String
	 *              }{@code >}
	 * 
	 */
	public void setVehiculoNumeroPlacaSec1(JAXBElement<String> value) {
		this.vehiculoNumeroPlacaSec1 = ((JAXBElement<String>) value);
	}

}
