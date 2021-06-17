package cruzdelsur.ws.com.pe;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlType;

/**
 * <p>
 * Java class for ResultDesbloquearAsiento complex type.
 * 
 * <p>
 * The following schema fragment specifies the expected content contained within
 * this class.
 * 
 * <pre>
 * &lt;complexType name="ResultDesbloquearAsiento">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="codigoAsiento" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="codigoTransaccion" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="error" type="{http://schemas.datacontract.org/2004/07/wscruzdelsur.Entities}Error" minOccurs="0"/>
 *         &lt;element name="isCorrect" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *         &lt;element name="nivelUnidad" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="resultadoTransaccion" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ResultDesbloquearAsiento", propOrder = { "codigoAsiento",
		"codigoTransaccion", "error", "isCorrect", "nivelUnidad",
		"resultadoTransaccion" })
public class ResultDesbloquearAsiento {

	@XmlElementRef(name = "codigoAsiento", namespace = "http://schemas.datacontract.org/2004/07/wscruzdelsur.Entities", type = JAXBElement.class)
	protected JAXBElement<String> codigoAsiento;
	@XmlElementRef(name = "codigoTransaccion", namespace = "http://schemas.datacontract.org/2004/07/wscruzdelsur.Entities", type = JAXBElement.class)
	protected JAXBElement<String> codigoTransaccion;
	@XmlElementRef(name = "error", namespace = "http://schemas.datacontract.org/2004/07/wscruzdelsur.Entities", type = JAXBElement.class)
	protected JAXBElement<Error> error;
	protected Boolean isCorrect;
	@XmlElementRef(name = "nivelUnidad", namespace = "http://schemas.datacontract.org/2004/07/wscruzdelsur.Entities", type = JAXBElement.class)
	protected JAXBElement<String> nivelUnidad;
	@XmlElementRef(name = "resultadoTransaccion", namespace = "http://schemas.datacontract.org/2004/07/wscruzdelsur.Entities", type = JAXBElement.class)
	protected JAXBElement<String> resultadoTransaccion;

	/**
	 * Gets the value of the codigoAsiento property.
	 * 
	 * @return possible object is {@link JAXBElement }{@code <}{@link String }
	 *         {@code >}
	 * 
	 */
	public JAXBElement<String> getCodigoAsiento() {
		return codigoAsiento;
	}

	/**
	 * Sets the value of the codigoAsiento property.
	 * 
	 * @param value
	 *            allowed object is {@link JAXBElement }{@code <}{@link String }
	 *            {@code >}
	 * 
	 */
	public void setCodigoAsiento(JAXBElement<String> value) {
		this.codigoAsiento = (value);
	}

	/**
	 * Gets the value of the codigoTransaccion property.
	 * 
	 * @return possible object is {@link JAXBElement }{@code <}{@link String }
	 *         {@code >}
	 * 
	 */
	public JAXBElement<String> getCodigoTransaccion() {
		return codigoTransaccion;
	}

	/**
	 * Sets the value of the codigoTransaccion property.
	 * 
	 * @param value
	 *            allowed object is {@link JAXBElement }{@code <}{@link String }
	 *            {@code >}
	 * 
	 */
	public void setCodigoTransaccion(JAXBElement<String> value) {
		this.codigoTransaccion = (value);
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
		this.error = (value);
	}

	/**
	 * Gets the value of the isCorrect property.
	 * 
	 * @return possible object is {@link Boolean }
	 * 
	 */
	public Boolean isIsCorrect() {
		return isCorrect;
	}

	/**
	 * Sets the value of the isCorrect property.
	 * 
	 * @param value
	 *            allowed object is {@link Boolean }
	 * 
	 */
	public void setIsCorrect(Boolean value) {
		this.isCorrect = value;
	}

	/**
	 * Gets the value of the nivelUnidad property.
	 * 
	 * @return possible object is {@link JAXBElement }{@code <}{@link String }
	 *         {@code >}
	 * 
	 */
	public JAXBElement<String> getNivelUnidad() {
		return nivelUnidad;
	}

	/**
	 * Sets the value of the nivelUnidad property.
	 * 
	 * @param value
	 *            allowed object is {@link JAXBElement }{@code <}{@link String }
	 *            {@code >}
	 * 
	 */
	public void setNivelUnidad(JAXBElement<String> value) {
		this.nivelUnidad = (value);
	}

	/**
	 * Gets the value of the resultadoTransaccion property.
	 * 
	 * @return possible object is {@link JAXBElement }{@code <}{@link String }
	 *         {@code >}
	 * 
	 */
	public JAXBElement<String> getResultadoTransaccion() {
		return resultadoTransaccion;
	}

	/**
	 * Sets the value of the resultadoTransaccion property.
	 * 
	 * @param value
	 *            allowed object is {@link JAXBElement }{@code <}{@link String }
	 *            {@code >}
	 * 
	 */
	public void setResultadoTransaccion(JAXBElement<String> value) {
		this.resultadoTransaccion = (value);
	}

}
