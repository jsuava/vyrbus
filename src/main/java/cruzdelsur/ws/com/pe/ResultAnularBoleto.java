package cruzdelsur.ws.com.pe;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlType;

/**
 * <p>
 * Java class for ResultAnularBoleto complex type.
 * 
 * <p>
 * The following schema fragment specifies the expected content contained within
 * this class.
 * 
 * <pre>
 * &lt;complexType name="ResultAnularBoleto">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="anulado" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *         &lt;element name="error" type="{http://schemas.datacontract.org/2004/07/wscruzdelsur.Entities}Error" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ResultAnularBoleto", propOrder = { "anulado", "error" })
public class ResultAnularBoleto {

	protected Boolean anulado;
	@XmlElementRef(name = "error", namespace = "http://schemas.datacontract.org/2004/07/wscruzdelsur.Entities", type = JAXBElement.class)
	protected JAXBElement<Error> error;

	/**
	 * Gets the value of the anulado property.
	 * 
	 * @return possible object is {@link Boolean }
	 * 
	 */
	public Boolean isAnulado() {
		return anulado;
	}

	/**
	 * Sets the value of the anulado property.
	 * 
	 * @param value
	 *            allowed object is {@link Boolean }
	 * 
	 */
	public void setAnulado(Boolean value) {
		this.anulado = value;
	}

	/**
	 * Gets the value of the error property.
	 * 
	 * @return possible object is {@link JAXBElement }{@code <}{@link Error }
	 *         {@code >}
	 * 
	 */
	public JAXBElement<Error> getError() {
		return error;
	}

	/**
	 * Sets the value of the error property.
	 * 
	 * @param value
	 *            allowed object is {@link JAXBElement }{@code <}{@link Error }
	 *            {@code >}
	 * 
	 */
	public void setError(JAXBElement<Error> value) {
		this.error = ((JAXBElement<Error>) value);
	}

}
