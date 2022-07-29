
package com.cystesoft.vyrbus.service.fe;

import java.math.BigDecimal;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlType;

/**
 * <p>
 * Java class for InformacionCredito complex type.
 * 
 * <p>
 * The following schema fragment specifies the expected content contained within
 * this class.
 * 
 * <pre>
 * &lt;complexType name="InformacionCredito">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="FechaVencimiento" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="MontoCuota" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/>
 *         &lt;element name="NroCuota" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "InformacionCredito", namespace = "http://schemas.datacontract.org/2004/07/FEService.Input", propOrder = {
		"fechaVencimiento", "montoCuota", "nroCuota" })
public class InformacionCredito {

	@XmlElementRef(name = "FechaVencimiento", namespace = "http://schemas.datacontract.org/2004/07/FEService.Input", type = JAXBElement.class)
	protected JAXBElement<String> fechaVencimiento;
	@XmlElement(name = "MontoCuota")
	protected BigDecimal montoCuota;
	@XmlElementRef(name = "NroCuota", namespace = "http://schemas.datacontract.org/2004/07/FEService.Input", type = JAXBElement.class)
	protected JAXBElement<String> nroCuota;

	/**
	 * Gets the value of the fechaVencimiento property.
	 * 
	 * @return possible object is {@link JAXBElement }{@code <}{@link String
	 *         }{@code >}
	 * 
	 */
	public JAXBElement<String> getFechaVencimiento() {
		return fechaVencimiento;
	}

	/**
	 * Sets the value of the fechaVencimiento property.
	 * 
	 * @param value allowed object is {@link JAXBElement }{@code <}{@link String
	 *              }{@code >}
	 * 
	 */
	public void setFechaVencimiento(JAXBElement<String> value) {
		this.fechaVencimiento = ((JAXBElement<String>) value);
	}

	/**
	 * Gets the value of the montoCuota property.
	 * 
	 * @return possible object is {@link BigDecimal }
	 * 
	 */
	public BigDecimal getMontoCuota() {
		return montoCuota;
	}

	/**
	 * Sets the value of the montoCuota property.
	 * 
	 * @param value allowed object is {@link BigDecimal }
	 * 
	 */
	public void setMontoCuota(BigDecimal value) {
		this.montoCuota = value;
	}

	/**
	 * Gets the value of the nroCuota property.
	 * 
	 * @return possible object is {@link JAXBElement }{@code <}{@link String
	 *         }{@code >}
	 * 
	 */
	public JAXBElement<String> getNroCuota() {
		return nroCuota;
	}

	/**
	 * Sets the value of the nroCuota property.
	 * 
	 * @param value allowed object is {@link JAXBElement }{@code <}{@link String
	 *              }{@code >}
	 * 
	 */
	public void setNroCuota(JAXBElement<String> value) {
		this.nroCuota = ((JAXBElement<String>) value);
	}

}
