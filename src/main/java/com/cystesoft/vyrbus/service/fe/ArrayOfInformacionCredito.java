
package com.cystesoft.vyrbus.service.fe;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

/**
 * <p>
 * Java class for ArrayOfInformacionCredito complex type.
 *
 * <p>
 * The following schema fragment specifies the expected content contained within
 * this class.
 *
 * <pre>
 * &lt;complexType name="ArrayOfInformacionCredito">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="InformacionCredito" type="{http://schemas.datacontract.org/2004/07/FEService.Input}InformacionCredito" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 *
 *
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ArrayOfInformacionCredito", namespace = "http://schemas.datacontract.org/2004/07/FEService.Input", propOrder = {
		"informacionCredito" })
public class ArrayOfInformacionCredito {

	@XmlElement(name = "InformacionCredito", nillable = true)
	protected List<InformacionCredito> informacionCredito;

	/**
	 * Gets the value of the informacionCredito property.
	 *
	 * <p>
	 * This accessor method returns a reference to the live list, not a snapshot.
	 * Therefore any modification you make to the returned list will be present
	 * inside the JAXB object. This is why there is not a <CODE>set</CODE> method
	 * for the informacionCredito property.
	 *
	 * <p>
	 * For example, to add a new item, do as follows:
	 *
	 * <pre>
	 * getInformacionCredito().add(newItem);
	 * </pre>
	 *
	 *
	 * <p>
	 * Objects of the following type(s) are allowed in the list
	 * {@link InformacionCredito }
	 *
	 *
	 */
	public List<InformacionCredito> getInformacionCredito() {
		if (informacionCredito == null) {
			informacionCredito = new ArrayList<>();
		}
		return this.informacionCredito;
	}

}
