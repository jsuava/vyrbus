package pe.gob.mtc.wshr;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

/**
 * <p>
 * Java class for anonymous complex type.
 *
 * <p>
 * The following schema fragment specifies the expected content contained within
 * this class.
 *
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="oFinalizar" type="{http://wshr.mtc.gob.pe/}Finalizar2" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 *
 *
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = { "oFinalizar" })
@XmlRootElement(name = "setFinalizar2")
public class SetFinalizar2 {

	protected Finalizar2 oFinalizar;

	/**
	 * Gets the value of the oFinalizar property.
	 *
	 * @return possible object is {@link Finalizar2 }
	 *
	 */
	public Finalizar2 getOFinalizar() {
		return oFinalizar;
	}

	/**
	 * Sets the value of the oFinalizar property.
	 *
	 * @param value
	 *            allowed object is {@link Finalizar2 }
	 *
	 */
	public void setOFinalizar(Finalizar2 value) {
		this.oFinalizar = value;
	}

}
