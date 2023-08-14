
package pe.itsb.vyrbus.service.fe;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlType;

/**
 * <p>
 * Java class for Result complex type.
 *
 * <p>
 * The following schema fragment specifies the expected content contained within
 * this class.
 *
 * <pre>
 * &lt;complexType name="Result">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="Code" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="IsCorrect" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *         &lt;element name="Message" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="barcode" type="{http://www.w3.org/2001/XMLSchema}base64Binary" minOccurs="0"/>
 *         &lt;element name="barcodeEmbarque" type="{http://www.w3.org/2001/XMLSchema}base64Binary" minOccurs="0"/>
 *         &lt;element name="barcode_QR" type="{http://www.w3.org/2001/XMLSchema}base64Binary" minOccurs="0"/>
 *         &lt;element name="listDetalleVenta" type="{http://schemas.datacontract.org/2004/07/FEService.Input}ArrayOfDetalleVenta" minOccurs="0"/>
 *         &lt;element name="pdf" type="{http://www.w3.org/2001/XMLSchema}base64Binary" minOccurs="0"/>
 *         &lt;element name="retencionesInp" type="{http://schemas.datacontract.org/2004/07/FEService.Input}ArrayOfRetencionInp" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 *
 *
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Result", namespace = "http://schemas.datacontract.org/2004/07/FEService.Util", propOrder = { "code",
		"isCorrect", "message", "barcode", "barcodeEmbarque", "barcodeQR", "listDetalleVenta", "pdf",
		"retencionesInp" })
public class Result {

	@XmlElementRef(name = "Code", namespace = "http://schemas.datacontract.org/2004/07/FEService.Util", type = JAXBElement.class)
	protected JAXBElement<String> code;
	@XmlElement(name = "IsCorrect")
	protected Boolean isCorrect;
	@XmlElementRef(name = "Message", namespace = "http://schemas.datacontract.org/2004/07/FEService.Util", type = JAXBElement.class)
	protected JAXBElement<String> message;
	@XmlElementRef(name = "barcode", namespace = "http://schemas.datacontract.org/2004/07/FEService.Util", type = JAXBElement.class)
	protected JAXBElement<byte[]> barcode;
	@XmlElementRef(name = "barcodeEmbarque", namespace = "http://schemas.datacontract.org/2004/07/FEService.Util", type = JAXBElement.class)
	protected JAXBElement<byte[]> barcodeEmbarque;
	@XmlElementRef(name = "barcode_QR", namespace = "http://schemas.datacontract.org/2004/07/FEService.Util", type = JAXBElement.class)
	protected JAXBElement<byte[]> barcodeQR;
	@XmlElementRef(name = "listDetalleVenta", namespace = "http://schemas.datacontract.org/2004/07/FEService.Util", type = JAXBElement.class)
	protected JAXBElement<ArrayOfDetalleVenta> listDetalleVenta;
	@XmlElementRef(name = "pdf", namespace = "http://schemas.datacontract.org/2004/07/FEService.Util", type = JAXBElement.class)
	protected JAXBElement<byte[]> pdf;
	@XmlElementRef(name = "retencionesInp", namespace = "http://schemas.datacontract.org/2004/07/FEService.Util", type = JAXBElement.class)
	protected JAXBElement<ArrayOfRetencionInp> retencionesInp;

	/**
	 * Gets the value of the code property.
	 *
	 * @return possible object is {@link JAXBElement }{@code <}{@link String
	 *         }{@code >}
	 *
	 */
	public JAXBElement<String> getCode() {
		return code;
	}

	/**
	 * Sets the value of the code property.
	 *
	 * @param value allowed object is {@link JAXBElement }{@code <}{@link String
	 *              }{@code >}
	 *
	 */
	public void setCode(JAXBElement<String> value) {
		this.code = (value);
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
	 * @param value allowed object is {@link Boolean }
	 *
	 */
	public void setIsCorrect(Boolean value) {
		this.isCorrect = value;
	}

	/**
	 * Gets the value of the message property.
	 *
	 * @return possible object is {@link JAXBElement }{@code <}{@link String
	 *         }{@code >}
	 *
	 */
	public JAXBElement<String> getMessage() {
		return message;
	}

	/**
	 * Sets the value of the message property.
	 *
	 * @param value allowed object is {@link JAXBElement }{@code <}{@link String
	 *              }{@code >}
	 *
	 */
	public void setMessage(JAXBElement<String> value) {
		this.message = (value);
	}

	/**
	 * Gets the value of the barcode property.
	 *
	 * @return possible object is {@link JAXBElement
	 *         }{@code <}{@link byte[]}{@code >}
	 *
	 */
	public JAXBElement<byte[]> getBarcode() {
		return barcode;
	}

	/**
	 * Sets the value of the barcode property.
	 *
	 * @param value allowed object is {@link JAXBElement
	 *              }{@code <}{@link byte[]}{@code >}
	 *
	 */
	public void setBarcode(JAXBElement<byte[]> value) {
		this.barcode = (value);
	}

	/**
	 * Gets the value of the barcodeEmbarque property.
	 *
	 * @return possible object is {@link JAXBElement
	 *         }{@code <}{@link byte[]}{@code >}
	 *
	 */
	public JAXBElement<byte[]> getBarcodeEmbarque() {
		return barcodeEmbarque;
	}

	/**
	 * Sets the value of the barcodeEmbarque property.
	 *
	 * @param value allowed object is {@link JAXBElement
	 *              }{@code <}{@link byte[]}{@code >}
	 *
	 */
	public void setBarcodeEmbarque(JAXBElement<byte[]> value) {
		this.barcodeEmbarque = (value);
	}

	/**
	 * Gets the value of the barcodeQR property.
	 *
	 * @return possible object is {@link JAXBElement
	 *         }{@code <}{@link byte[]}{@code >}
	 *
	 */
	public JAXBElement<byte[]> getBarcodeQR() {
		return barcodeQR;
	}

	/**
	 * Sets the value of the barcodeQR property.
	 *
	 * @param value allowed object is {@link JAXBElement
	 *              }{@code <}{@link byte[]}{@code >}
	 *
	 */
	public void setBarcodeQR(JAXBElement<byte[]> value) {
		this.barcodeQR = (value);
	}

	/**
	 * Gets the value of the listDetalleVenta property.
	 *
	 * @return possible object is {@link JAXBElement
	 *         }{@code <}{@link ArrayOfDetalleVenta }{@code >}
	 *
	 */
	public JAXBElement<ArrayOfDetalleVenta> getListDetalleVenta() {
		return listDetalleVenta;
	}

	/**
	 * Sets the value of the listDetalleVenta property.
	 *
	 * @param value allowed object is {@link JAXBElement
	 *              }{@code <}{@link ArrayOfDetalleVenta }{@code >}
	 *
	 */
	public void setListDetalleVenta(JAXBElement<ArrayOfDetalleVenta> value) {
		this.listDetalleVenta = (value);
	}

	/**
	 * Gets the value of the pdf property.
	 *
	 * @return possible object is {@link JAXBElement
	 *         }{@code <}{@link byte[]}{@code >}
	 *
	 */
	public JAXBElement<byte[]> getPdf() {
		return pdf;
	}

	/**
	 * Sets the value of the pdf property.
	 *
	 * @param value allowed object is {@link JAXBElement
	 *              }{@code <}{@link byte[]}{@code >}
	 *
	 */
	public void setPdf(JAXBElement<byte[]> value) {
		this.pdf = (value);
	}

	/**
	 * Gets the value of the retencionesInp property.
	 *
	 * @return possible object is {@link JAXBElement
	 *         }{@code <}{@link ArrayOfRetencionInp }{@code >}
	 *
	 */
	public JAXBElement<ArrayOfRetencionInp> getRetencionesInp() {
		return retencionesInp;
	}

	/**
	 * Sets the value of the retencionesInp property.
	 *
	 * @param value allowed object is {@link JAXBElement
	 *              }{@code <}{@link ArrayOfRetencionInp }{@code >}
	 *
	 */
	public void setRetencionesInp(JAXBElement<ArrayOfRetencionInp> value) {
		this.retencionesInp = (value);
	}

}
