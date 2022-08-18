package pe.gob.mtc.wshr;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

/**
 * <p>
 * Java class for Finalizar2 complex type.
 *
 * <p>
 * The following schema fragment specifies the expected content contained within
 * this class.
 *
 * <pre>
 * &lt;complexType name="Finalizar2">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="Seguridad" type="{http://wshr.mtc.gob.pe/}Seguridad" minOccurs="0"/>
 *         &lt;element name="Code" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Tipo" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="FecTermino" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="HorTermino" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 *
 *
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Finalizar2", propOrder = { "seguridad", "code", "tipo",
		"fecTermino", "horTermino" })
public class Finalizar2 {

	@XmlElement(name = "Seguridad")
	protected Seguridad seguridad;
	@XmlElement(name = "Code")
	protected String code;
	@XmlElement(name = "Tipo")
	protected String tipo;
	@XmlElement(name = "FecTermino")
	protected String fecTermino;
	@XmlElement(name = "HorTermino")
	protected String horTermino;

	/**
	 * Gets the value of the seguridad property.
	 *
	 * @return possible object is {@link Seguridad }
	 *
	 */
	public Seguridad getSeguridad() {
		return seguridad;
	}

	/**
	 * Sets the value of the seguridad property.
	 *
	 * @param value
	 *            allowed object is {@link Seguridad }
	 *
	 */
	public void setSeguridad(Seguridad value) {
		this.seguridad = value;
	}

	/**
	 * Gets the value of the code property.
	 *
	 * @return possible object is {@link String }
	 *
	 */
	public String getCode() {
		return code;
	}

	/**
	 * Sets the value of the code property.
	 *
	 * @param value
	 *            allowed object is {@link String }
	 *
	 */
	public void setCode(String value) {
		this.code = value;
	}

	/**
	 * Gets the value of the tipo property.
	 *
	 * @return possible object is {@link String }
	 *
	 */
	public String getTipo() {
		return tipo;
	}

	/**
	 * Sets the value of the tipo property.
	 *
	 * @param value
	 *            allowed object is {@link String }
	 *
	 */
	public void setTipo(String value) {
		this.tipo = value;
	}

	/**
	 * Gets the value of the fecTermino property.
	 *
	 * @return possible object is {@link String }
	 *
	 */
	public String getFecTermino() {
		return fecTermino;
	}

	/**
	 * Sets the value of the fecTermino property.
	 *
	 * @param value
	 *            allowed object is {@link String }
	 *
	 */
	public void setFecTermino(String value) {
		this.fecTermino = value;
	}

	/**
	 * Gets the value of the horTermino property.
	 *
	 * @return possible object is {@link String }
	 *
	 */
	public String getHorTermino() {
		return horTermino;
	}

	/**
	 * Sets the value of the horTermino property.
	 *
	 * @param value
	 *            allowed object is {@link String }
	 *
	 */
	public void setHorTermino(String value) {
		this.horTermino = value;
	}

}
