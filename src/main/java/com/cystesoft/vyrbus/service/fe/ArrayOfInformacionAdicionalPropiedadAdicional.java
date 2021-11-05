package com.cystesoft.vyrbus.service.fe;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

/**
 * <p>
 * Java class for ArrayOfInformacionAdicional.PropiedadAdicional complex type.
 * 
 * <p>
 * The following schema fragment specifies the expected content contained within
 * this class.
 * 
 * <pre>
 * &lt;complexType name="ArrayOfInformacionAdicional.PropiedadAdicional">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="InformacionAdicional.PropiedadAdicional" type="{http://schemas.datacontract.org/2004/07/FEService.Input}InformacionAdicional.PropiedadAdicional" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ArrayOfInformacionAdicional.PropiedadAdicional", namespace = "http://schemas.datacontract.org/2004/07/FEService.Input", propOrder = { "informacionAdicionalPropiedadAdicional" })
public class ArrayOfInformacionAdicionalPropiedadAdicional {

	@XmlElement(name = "InformacionAdicional.PropiedadAdicional", nillable = true)
	protected List<InformacionAdicionalPropiedadAdicional> informacionAdicionalPropiedadAdicional;

	/**
	 * Gets the value of the informacionAdicionalPropiedadAdicional property.
	 * 
	 * <p>
	 * This accessor method returns a reference to the live list, not a
	 * snapshot. Therefore any modification you make to the returned list will
	 * be present inside the JAXB object. This is why there is not a
	 * <CODE>set</CODE> method for the informacionAdicionalPropiedadAdicional
	 * property.
	 * 
	 * <p>
	 * For example, to add a new item, do as follows:
	 * 
	 * <pre>
	 * getInformacionAdicionalPropiedadAdicional().add(newItem);
	 * </pre>
	 * 
	 * 
	 * <p>
	 * Objects of the following type(s) are allowed in the list
	 * {@link InformacionAdicionalPropiedadAdicional }
	 * 
	 * 
	 */
	public List<InformacionAdicionalPropiedadAdicional> getInformacionAdicionalPropiedadAdicional() {
		if (informacionAdicionalPropiedadAdicional == null) {
			informacionAdicionalPropiedadAdicional = new ArrayList<InformacionAdicionalPropiedadAdicional>();
		}
		return this.informacionAdicionalPropiedadAdicional;
	}

}
