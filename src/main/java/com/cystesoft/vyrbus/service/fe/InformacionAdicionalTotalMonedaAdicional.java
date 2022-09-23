
package com.cystesoft.vyrbus.service.fe;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlType;

/**
 * <p>
 * Java class for InformacionAdicional.TotalMonedaAdicional complex type.
 * 
 * <p>
 * The following schema fragment specifies the expected content contained within
 * this class.
 * 
 * <pre>
 * &lt;complexType name="InformacionAdicional.TotalMonedaAdicional">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="codigo" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="nombre" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="valor" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "InformacionAdicional.TotalMonedaAdicional", namespace = "http://schemas.datacontract.org/2004/07/FEService.Input", propOrder = {
		"codigo", "nombre", "valor" })
public class InformacionAdicionalTotalMonedaAdicional {

	@XmlElementRef(name = "codigo", namespace = "http://schemas.datacontract.org/2004/07/FEService.Input", type = JAXBElement.class)
	protected JAXBElement<String> codigo;
	@XmlElementRef(name = "nombre", namespace = "http://schemas.datacontract.org/2004/07/FEService.Input", type = JAXBElement.class)
	protected JAXBElement<String> nombre;
	@XmlElementRef(name = "valor", namespace = "http://schemas.datacontract.org/2004/07/FEService.Input", type = JAXBElement.class)
	protected JAXBElement<String> valor;

	/**
	 * Gets the value of the codigo property.
	 * 
	 * @return possible object is {@link JAXBElement }{@code <}{@link String
	 *         }{@code >}
	 * 
	 */
	public JAXBElement<String> getCodigo() {
		return codigo;
	}

	/**
	 * Sets the value of the codigo property.
	 * 
	 * @param value allowed object is {@link JAXBElement }{@code <}{@link String
	 *              }{@code >}
	 * 
	 */
	public void setCodigo(JAXBElement<String> value) {
		this.codigo = ((JAXBElement<String>) value);
	}

	/**
	 * Gets the value of the nombre property.
	 * 
	 * @return possible object is {@link JAXBElement }{@code <}{@link String
	 *         }{@code >}
	 * 
	 */
	public JAXBElement<String> getNombre() {
		return nombre;
	}

	/**
	 * Sets the value of the nombre property.
	 * 
	 * @param value allowed object is {@link JAXBElement }{@code <}{@link String
	 *              }{@code >}
	 * 
	 */
	public void setNombre(JAXBElement<String> value) {
		this.nombre = ((JAXBElement<String>) value);
	}

	/**
	 * Gets the value of the valor property.
	 * 
	 * @return possible object is {@link JAXBElement }{@code <}{@link String
	 *         }{@code >}
	 * 
	 */
	public JAXBElement<String> getValor() {
		return valor;
	}

	/**
	 * Sets the value of the valor property.
	 * 
	 * @param value allowed object is {@link JAXBElement }{@code <}{@link String
	 *              }{@code >}
	 * 
	 */
	public void setValor(JAXBElement<String> value) {
		this.valor = ((JAXBElement<String>) value);
	}

}
