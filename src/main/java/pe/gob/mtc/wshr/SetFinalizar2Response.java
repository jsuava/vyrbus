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
 *         &lt;element name="setFinalizar2Result" type="{http://wshr.mtc.gob.pe/}ResultFinalizar" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 *
 *
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = { "setFinalizar2Result" })
@XmlRootElement(name = "setFinalizar2Response")
public class SetFinalizar2Response {

	protected ResultFinalizar setFinalizar2Result;

	/**
	 * Gets the value of the setFinalizar2Result property.
	 *
	 * @return possible object is {@link ResultFinalizar }
	 *
	 */
	public ResultFinalizar getSetFinalizar2Result() {
		return setFinalizar2Result;
	}

	/**
	 * Sets the value of the setFinalizar2Result property.
	 *
	 * @param value
	 *            allowed object is {@link ResultFinalizar }
	 *
	 */
	public void setSetFinalizar2Result(ResultFinalizar value) {
		this.setFinalizar2Result = value;
	}

}
