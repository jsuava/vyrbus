
package com.cystesoft.vyrbus.service.fe;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

/**
 * <p>
 * Java class for ArrayOfGREDetalle complex type.
 * 
 * <p>
 * The following schema fragment specifies the expected content contained within
 * this class.
 * 
 * <pre>
 * &lt;complexType name="ArrayOfGREDetalle">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="GREDetalle" type="{http://schemas.datacontract.org/2004/07/FEService.Input}GREDetalle" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ArrayOfGREDetalle", namespace = "http://schemas.datacontract.org/2004/07/FEService.Input", propOrder = {
		"greDetalle" })
public class ArrayOfGREDetalle {

	@XmlElement(name = "GREDetalle", nillable = true)
	protected List<GREDetalle> greDetalle;

	/**
	 * Gets the value of the greDetalle property.
	 * 
	 * <p>
	 * This accessor method returns a reference to the live list, not a snapshot.
	 * Therefore any modification you make to the returned list will be present
	 * inside the JAXB object. This is why there is not a <CODE>set</CODE> method
	 * for the greDetalle property.
	 * 
	 * <p>
	 * For example, to add a new item, do as follows:
	 * 
	 * <pre>
	 * getGREDetalle().add(newItem);
	 * </pre>
	 * 
	 * 
	 * <p>
	 * Objects of the following type(s) are allowed in the list {@link GREDetalle }
	 * 
	 * 
	 */
	public List<GREDetalle> getGREDetalle() {
		if (greDetalle == null) {
			greDetalle = new ArrayList<GREDetalle>();
		}
		return this.greDetalle;
	}

}
