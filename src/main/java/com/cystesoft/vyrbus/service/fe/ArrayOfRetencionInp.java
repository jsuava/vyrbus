
package com.cystesoft.vyrbus.service.fe;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

/**
 * <p>
 * Java class for ArrayOfRetencionInp complex type.
 *
 * <p>
 * The following schema fragment specifies the expected content contained within
 * this class.
 *
 * <pre>
 * &lt;complexType name="ArrayOfRetencionInp">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="RetencionInp" type="{http://schemas.datacontract.org/2004/07/FEService.Input}RetencionInp" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 *
 *
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ArrayOfRetencionInp", namespace = "http://schemas.datacontract.org/2004/07/FEService.Input", propOrder = {
		"retencionInp" })
public class ArrayOfRetencionInp {

	@XmlElement(name = "RetencionInp", nillable = true)
	protected List<RetencionInp> retencionInp;

	/**
	 * Gets the value of the retencionInp property.
	 *
	 * <p>
	 * This accessor method returns a reference to the live list, not a snapshot.
	 * Therefore any modification you make to the returned list will be present
	 * inside the JAXB object. This is why there is not a <CODE>set</CODE> method
	 * for the retencionInp property.
	 *
	 * <p>
	 * For example, to add a new item, do as follows:
	 *
	 * <pre>
	 * getRetencionInp().add(newItem);
	 * </pre>
	 *
	 *
	 * <p>
	 * Objects of the following type(s) are allowed in the list {@link RetencionInp
	 * }
	 *
	 *
	 */
	public List<RetencionInp> getRetencionInp() {
		if (retencionInp == null) {
			retencionInp = new ArrayList<>();
		}
		return this.retencionInp;
	}

}
