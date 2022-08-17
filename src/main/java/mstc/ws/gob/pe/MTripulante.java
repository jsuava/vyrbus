package mstc.ws.gob.pe;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

import com.cystesoft.vyrbus.model.bean.Personal;

/**
 * <p>
 * Java class for MTripulante complex type.
 *
 * <p>
 * The following schema fragment specifies the expected content contained within
 * this class.
 *
 * <pre>
 * &lt;complexType name="MTripulante">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="TpoDoc" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="NroDoc" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 *
 *
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "MTripulante", propOrder = { "tpoDoc", "nroDoc","personal" })
public class MTripulante {

	@XmlElement(name = "TpoDoc")
	protected String tpoDoc;
	@XmlElement(name = "NroDoc")
	protected String nroDoc;
	@XmlElement(name = "personal")
	private Personal personal;
	/**
	 * Gets the value of the tpoDoc property.
	 *
	 * @return possible object is {@link String }
	 *
	 */
	public String getTpoDoc() {
		return tpoDoc;
	}

	/**
	 * Sets the value of the tpoDoc property.
	 *
	 * @param value
	 *            allowed object is {@link String }
	 *
	 */
	public void setTpoDoc(String value) {
		this.tpoDoc = value;
	}

	/**
	 * Gets the value of the nroDoc property.
	 *
	 * @return possible object is {@link String }
	 *
	 */
	public String getNroDoc() {
		return nroDoc;
	}

	/**
	 * Sets the value of the nroDoc property.
	 *
	 * @param value
	 *            allowed object is {@link String }
	 *
	 */
	public void setNroDoc(String value) {
		this.nroDoc = value;
	}

	/**
	 * @return the personal
	 */
	public Personal getPersonal() {
		return personal;
	}

	/**
	 * @param personal the personal to set
	 */
	public void setPersonal(Personal personal) {
		this.personal = personal;
	}

}
