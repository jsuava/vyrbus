package cruzdelsur.ws.com.pe;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlType;

/**
 * <p>
 * Java class for Distribucion complex type.
 * 
 * <p>
 * The following schema fragment specifies the expected content contained within
 * this class.
 * 
 * <pre>
 * &lt;complexType name="Distribucion">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="altoObjeto" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="anchoObjeto" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="asiento" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="descripcion" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="descuento" type="{http://www.w3.org/2001/XMLSchema}double" minOccurs="0"/>
 *         &lt;element name="descuentoInfante" type="{http://www.w3.org/2001/XMLSchema}double" minOccurs="0"/>
 *         &lt;element name="descuentonino" type="{http://www.w3.org/2001/XMLSchema}double" minOccurs="0"/>
 *         &lt;element name="disponible" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="elemento" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="igv" type="{http://www.w3.org/2001/XMLSchema}double" minOccurs="0"/>
 *         &lt;element name="igvInfante" type="{http://www.w3.org/2001/XMLSchema}double" minOccurs="0"/>
 *         &lt;element name="igvNino" type="{http://www.w3.org/2001/XMLSchema}double" minOccurs="0"/>
 *         &lt;element name="nivelPiso" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="tarifa" type="{http://www.w3.org/2001/XMLSchema}double" minOccurs="0"/>
 *         &lt;element name="tarifaDescripcion" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="tarifaInfante" type="{http://www.w3.org/2001/XMLSchema}double" minOccurs="0"/>
 *         &lt;element name="tarifaNino" type="{http://www.w3.org/2001/XMLSchema}double" minOccurs="0"/>
 *         &lt;element name="tarifaTotalInfante" type="{http://www.w3.org/2001/XMLSchema}double" minOccurs="0"/>
 *         &lt;element name="tarifaTotalNino" type="{http://www.w3.org/2001/XMLSchema}double" minOccurs="0"/>
 *         &lt;element name="tarifatotal" type="{http://www.w3.org/2001/XMLSchema}double" minOccurs="0"/>
 *         &lt;element name="totalNivel" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="x1" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="y1" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Distribucion", propOrder = { "altoObjeto", "anchoObjeto",
		"asiento", "descripcion", "descuento", "descuentoInfante",
		"descuentonino", "disponible", "elemento", "igv", "igvInfante",
		"igvNino", "nivelPiso", "tarifa", "tarifaDescripcion", "tarifaInfante",
		"tarifaNino", "tarifaTotalInfante", "tarifaTotalNino", "tarifatotal",
		"totalNivel", "x1", "y1" })
public class Distribucion {

	protected Integer altoObjeto;
	protected Integer anchoObjeto;
	@XmlElementRef(name = "asiento", namespace = "http://schemas.datacontract.org/2004/07/wscruzdelsur.Entities", type = JAXBElement.class)
	protected JAXBElement<String> asiento;
	@XmlElementRef(name = "descripcion", namespace = "http://schemas.datacontract.org/2004/07/wscruzdelsur.Entities", type = JAXBElement.class)
	protected JAXBElement<String> descripcion;
	protected Double descuento;
	protected Double descuentoInfante;
	protected Double descuentonino;
	protected Integer disponible;
	@XmlElementRef(name = "elemento", namespace = "http://schemas.datacontract.org/2004/07/wscruzdelsur.Entities", type = JAXBElement.class)
	protected JAXBElement<String> elemento;
	protected Double igv;
	protected Double igvInfante;
	protected Double igvNino;
	@XmlElementRef(name = "nivelPiso", namespace = "http://schemas.datacontract.org/2004/07/wscruzdelsur.Entities", type = JAXBElement.class)
	protected JAXBElement<String> nivelPiso;
	protected Double tarifa;
	@XmlElementRef(name = "tarifaDescripcion", namespace = "http://schemas.datacontract.org/2004/07/wscruzdelsur.Entities", type = JAXBElement.class)
	protected JAXBElement<String> tarifaDescripcion;
	protected Double tarifaInfante;
	protected Double tarifaNino;
	protected Double tarifaTotalInfante;
	protected Double tarifaTotalNino;
	protected Double tarifatotal;
	@XmlElementRef(name = "totalNivel", namespace = "http://schemas.datacontract.org/2004/07/wscruzdelsur.Entities", type = JAXBElement.class)
	protected JAXBElement<String> totalNivel;
	protected Integer x1;
	protected Integer y1;

	/**
	 * Gets the value of the altoObjeto property.
	 * 
	 * @return possible object is {@link Integer }
	 * 
	 */
	public Integer getAltoObjeto() {
		return altoObjeto;
	}

	/**
	 * Sets the value of the altoObjeto property.
	 * 
	 * @param value
	 *            allowed object is {@link Integer }
	 * 
	 */
	public void setAltoObjeto(Integer value) {
		this.altoObjeto = value;
	}

	/**
	 * Gets the value of the anchoObjeto property.
	 * 
	 * @return possible object is {@link Integer }
	 * 
	 */
	public Integer getAnchoObjeto() {
		return anchoObjeto;
	}

	/**
	 * Sets the value of the anchoObjeto property.
	 * 
	 * @param value
	 *            allowed object is {@link Integer }
	 * 
	 */
	public void setAnchoObjeto(Integer value) {
		this.anchoObjeto = value;
	}

	/**
	 * Gets the value of the asiento property.
	 * 
	 * @return possible object is {@link JAXBElement }{@code <}{@link String }
	 *         {@code >}
	 * 
	 */
	public JAXBElement<String> getAsiento() {
		return asiento;
	}

	/**
	 * Sets the value of the asiento property.
	 * 
	 * @param value
	 *            allowed object is {@link JAXBElement }{@code <}{@link String }
	 *            {@code >}
	 * 
	 */
	public void setAsiento(JAXBElement<String> value) {
		this.asiento = ((JAXBElement<String>) value);
	}

	/**
	 * Gets the value of the descripcion property.
	 * 
	 * @return possible object is {@link JAXBElement }{@code <}{@link String }
	 *         {@code >}
	 * 
	 */
	public JAXBElement<String> getDescripcion() {
		return descripcion;
	}

	/**
	 * Sets the value of the descripcion property.
	 * 
	 * @param value
	 *            allowed object is {@link JAXBElement }{@code <}{@link String }
	 *            {@code >}
	 * 
	 */
	public void setDescripcion(JAXBElement<String> value) {
		this.descripcion = ((JAXBElement<String>) value);
	}

	/**
	 * Gets the value of the descuento property.
	 * 
	 * @return possible object is {@link Double }
	 * 
	 */
	public Double getDescuento() {
		return descuento;
	}

	/**
	 * Sets the value of the descuento property.
	 * 
	 * @param value
	 *            allowed object is {@link Double }
	 * 
	 */
	public void setDescuento(Double value) {
		this.descuento = value;
	}

	/**
	 * Gets the value of the descuentoInfante property.
	 * 
	 * @return possible object is {@link Double }
	 * 
	 */
	public Double getDescuentoInfante() {
		return descuentoInfante;
	}

	/**
	 * Sets the value of the descuentoInfante property.
	 * 
	 * @param value
	 *            allowed object is {@link Double }
	 * 
	 */
	public void setDescuentoInfante(Double value) {
		this.descuentoInfante = value;
	}

	/**
	 * Gets the value of the descuentonino property.
	 * 
	 * @return possible object is {@link Double }
	 * 
	 */
	public Double getDescuentonino() {
		return descuentonino;
	}

	/**
	 * Sets the value of the descuentonino property.
	 * 
	 * @param value
	 *            allowed object is {@link Double }
	 * 
	 */
	public void setDescuentonino(Double value) {
		this.descuentonino = value;
	}

	/**
	 * Gets the value of the disponible property.
	 * 
	 * @return possible object is {@link Integer }
	 * 
	 */
	public Integer getDisponible() {
		return disponible;
	}

	/**
	 * Sets the value of the disponible property.
	 * 
	 * @param value
	 *            allowed object is {@link Integer }
	 * 
	 */
	public void setDisponible(Integer value) {
		this.disponible = value;
	}

	/**
	 * Gets the value of the elemento property.
	 * 
	 * @return possible object is {@link JAXBElement }{@code <}{@link String }
	 *         {@code >}
	 * 
	 */
	public JAXBElement<String> getElemento() {
		return elemento;
	}

	/**
	 * Sets the value of the elemento property.
	 * 
	 * @param value
	 *            allowed object is {@link JAXBElement }{@code <}{@link String }
	 *            {@code >}
	 * 
	 */
	public void setElemento(JAXBElement<String> value) {
		this.elemento = ((JAXBElement<String>) value);
	}

	/**
	 * Gets the value of the igv property.
	 * 
	 * @return possible object is {@link Double }
	 * 
	 */
	public Double getIgv() {
		return igv;
	}

	/**
	 * Sets the value of the igv property.
	 * 
	 * @param value
	 *            allowed object is {@link Double }
	 * 
	 */
	public void setIgv(Double value) {
		this.igv = value;
	}

	/**
	 * Gets the value of the igvInfante property.
	 * 
	 * @return possible object is {@link Double }
	 * 
	 */
	public Double getIgvInfante() {
		return igvInfante;
	}

	/**
	 * Sets the value of the igvInfante property.
	 * 
	 * @param value
	 *            allowed object is {@link Double }
	 * 
	 */
	public void setIgvInfante(Double value) {
		this.igvInfante = value;
	}

	/**
	 * Gets the value of the igvNino property.
	 * 
	 * @return possible object is {@link Double }
	 * 
	 */
	public Double getIgvNino() {
		return igvNino;
	}

	/**
	 * Sets the value of the igvNino property.
	 * 
	 * @param value
	 *            allowed object is {@link Double }
	 * 
	 */
	public void setIgvNino(Double value) {
		this.igvNino = value;
	}

	/**
	 * Gets the value of the nivelPiso property.
	 * 
	 * @return possible object is {@link JAXBElement }{@code <}{@link String }
	 *         {@code >}
	 * 
	 */
	public JAXBElement<String> getNivelPiso() {
		return nivelPiso;
	}

	/**
	 * Sets the value of the nivelPiso property.
	 * 
	 * @param value
	 *            allowed object is {@link JAXBElement }{@code <}{@link String }
	 *            {@code >}
	 * 
	 */
	public void setNivelPiso(JAXBElement<String> value) {
		this.nivelPiso = ((JAXBElement<String>) value);
	}

	/**
	 * Gets the value of the tarifa property.
	 * 
	 * @return possible object is {@link Double }
	 * 
	 */
	public Double getTarifa() {
		return tarifa;
	}

	/**
	 * Sets the value of the tarifa property.
	 * 
	 * @param value
	 *            allowed object is {@link Double }
	 * 
	 */
	public void setTarifa(Double value) {
		this.tarifa = value;
	}

	/**
	 * Gets the value of the tarifaDescripcion property.
	 * 
	 * @return possible object is {@link JAXBElement }{@code <}{@link String }
	 *         {@code >}
	 * 
	 */
	public JAXBElement<String> getTarifaDescripcion() {
		return tarifaDescripcion;
	}

	/**
	 * Sets the value of the tarifaDescripcion property.
	 * 
	 * @param value
	 *            allowed object is {@link JAXBElement }{@code <}{@link String }
	 *            {@code >}
	 * 
	 */
	public void setTarifaDescripcion(JAXBElement<String> value) {
		this.tarifaDescripcion = ((JAXBElement<String>) value);
	}

	/**
	 * Gets the value of the tarifaInfante property.
	 * 
	 * @return possible object is {@link Double }
	 * 
	 */
	public Double getTarifaInfante() {
		return tarifaInfante;
	}

	/**
	 * Sets the value of the tarifaInfante property.
	 * 
	 * @param value
	 *            allowed object is {@link Double }
	 * 
	 */
	public void setTarifaInfante(Double value) {
		this.tarifaInfante = value;
	}

	/**
	 * Gets the value of the tarifaNino property.
	 * 
	 * @return possible object is {@link Double }
	 * 
	 */
	public Double getTarifaNino() {
		return tarifaNino;
	}

	/**
	 * Sets the value of the tarifaNino property.
	 * 
	 * @param value
	 *            allowed object is {@link Double }
	 * 
	 */
	public void setTarifaNino(Double value) {
		this.tarifaNino = value;
	}

	/**
	 * Gets the value of the tarifaTotalInfante property.
	 * 
	 * @return possible object is {@link Double }
	 * 
	 */
	public Double getTarifaTotalInfante() {
		return tarifaTotalInfante;
	}

	/**
	 * Sets the value of the tarifaTotalInfante property.
	 * 
	 * @param value
	 *            allowed object is {@link Double }
	 * 
	 */
	public void setTarifaTotalInfante(Double value) {
		this.tarifaTotalInfante = value;
	}

	/**
	 * Gets the value of the tarifaTotalNino property.
	 * 
	 * @return possible object is {@link Double }
	 * 
	 */
	public Double getTarifaTotalNino() {
		return tarifaTotalNino;
	}

	/**
	 * Sets the value of the tarifaTotalNino property.
	 * 
	 * @param value
	 *            allowed object is {@link Double }
	 * 
	 */
	public void setTarifaTotalNino(Double value) {
		this.tarifaTotalNino = value;
	}

	/**
	 * Gets the value of the tarifatotal property.
	 * 
	 * @return possible object is {@link Double }
	 * 
	 */
	public Double getTarifatotal() {
		return tarifatotal;
	}

	/**
	 * Sets the value of the tarifatotal property.
	 * 
	 * @param value
	 *            allowed object is {@link Double }
	 * 
	 */
	public void setTarifatotal(Double value) {
		this.tarifatotal = value;
	}

	/**
	 * Gets the value of the totalNivel property.
	 * 
	 * @return possible object is {@link JAXBElement }{@code <}{@link String }
	 *         {@code >}
	 * 
	 */
	public JAXBElement<String> getTotalNivel() {
		return totalNivel;
	}

	/**
	 * Sets the value of the totalNivel property.
	 * 
	 * @param value
	 *            allowed object is {@link JAXBElement }{@code <}{@link String }
	 *            {@code >}
	 * 
	 */
	public void setTotalNivel(JAXBElement<String> value) {
		this.totalNivel = ((JAXBElement<String>) value);
	}

	/**
	 * Gets the value of the x1 property.
	 * 
	 * @return possible object is {@link Integer }
	 * 
	 */
	public Integer getX1() {
		return x1;
	}

	/**
	 * Sets the value of the x1 property.
	 * 
	 * @param value
	 *            allowed object is {@link Integer }
	 * 
	 */
	public void setX1(Integer value) {
		this.x1 = value;
	}

	/**
	 * Gets the value of the y1 property.
	 * 
	 * @return possible object is {@link Integer }
	 * 
	 */
	public Integer getY1() {
		return y1;
	}

	/**
	 * Sets the value of the y1 property.
	 * 
	 * @param value
	 *            allowed object is {@link Integer }
	 * 
	 */
	public void setY1(Integer value) {
		this.y1 = value;
	}

}
