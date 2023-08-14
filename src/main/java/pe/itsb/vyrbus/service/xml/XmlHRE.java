/**
 * Proyecto		: SISVYR
 * Sistema		: Sistema de Ventas y Reservas
 * Descripci�n	:
 * Autor		: Jos� Abanto
 * Fecha		: 20 nov. 2022
 * Hora			: 02:54:59
 */
package pe.itsb.vyrbus.service.xml;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlType;

/**
 * @author abant
 *
 */
@XmlType
public class XmlHRE implements Serializable{
	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	private String numeroHRE;
	private String numeroPalca;
	private String fechaSalida;
	private String fechaLlegada;
	private String ruta;
	private String terminalSalida;
	private String terminalLlegada;
	private String horaSalida;
	private String horaLlegada;
	private XmlDetalleHRE xmlDetalleHRE;
	private XmlHREPuntosSalida xmlHREPuntosSalida;
	/**
	 * @return the numeroHRE
	 */
	public String getNumeroHRE() {
		return numeroHRE;
	}
	/**
	 * @param numeroHRE the numeroHRE to set
	 */
	public void setNumeroHRE(String numeroHRE) {
		this.numeroHRE = numeroHRE;
	}
	/**
	 * @return the numeroPalca
	 */
	public String getNumeroPalca() {
		return numeroPalca;
	}
	/**
	 * @param numeroPalca the numeroPalca to set
	 */
	public void setNumeroPalca(String numeroPalca) {
		this.numeroPalca = numeroPalca;
	}
	/**
	 * @return the fechaSalida
	 */
	public String getFechaSalida() {
		return fechaSalida;
	}
	/**
	 * @param fechaSalida the fechaSalida to set
	 */
	public void setFechaSalida(String fechaSalida) {
		this.fechaSalida = fechaSalida;
	}
	/**
	 * @return the fechaLlegada
	 */
	public String getFechaLlegada() {
		return fechaLlegada;
	}
	/**
	 * @param fechaLlegada the fechaLlegada to set
	 */
	public void setFechaLlegada(String fechaLlegada) {
		this.fechaLlegada = fechaLlegada;
	}
	/**
	 * @return the ruta
	 */
	public String getRuta() {
		return ruta;
	}
	/**
	 * @param ruta the ruta to set
	 */
	public void setRuta(String ruta) {
		this.ruta = ruta;
	}
	/**
	 * @return the terminalSalida
	 */
	public String getTerminalSalida() {
		return terminalSalida;
	}
	/**
	 * @param terminalSalida the terminalSalida to set
	 */
	public void setTerminalSalida(String terminalSalida) {
		this.terminalSalida = terminalSalida;
	}
	/**
	 * @return the terminalLlegada
	 */
	public String getTerminalLlegada() {
		return terminalLlegada;
	}
	/**
	 * @param terminalLlegada the terminalLlegada to set
	 */
	public void setTerminalLlegada(String terminalLlegada) {
		this.terminalLlegada = terminalLlegada;
	}
	/**
	 * @return the horaSalida
	 */
	public String getHoraSalida() {
		return horaSalida;
	}
	/**
	 * @param horaSalida the horaSalida to set
	 */
	public void setHoraSalida(String horaSalida) {
		this.horaSalida = horaSalida;
	}
	/**
	 * @return the horaLlegada
	 */
	public String getHoraLlegada() {
		return horaLlegada;
	}
	/**
	 * @param horaLlegada the horaLlegada to set
	 */
	public void setHoraLlegada(String horaLlegada) {
		this.horaLlegada = horaLlegada;
	}
	/**
	 * @return the xmlDetalleHRE
	 */
	public XmlDetalleHRE getXmlDetalleHRE() {
		return xmlDetalleHRE;
	}
	/**
	 * @param xmlDetalleHRE the xmlDetalleHRE to set
	 */
	public void setXmlDetalleHRE(XmlDetalleHRE xmlDetalleHRE) {
		this.xmlDetalleHRE = xmlDetalleHRE;
	}
	/**
	 * @return the xmlHREPuntosSalida
	 */
	public XmlHREPuntosSalida getXmlHREPuntosSalida() {
		return xmlHREPuntosSalida;
	}
	/**
	 * @param xmlHREPuntosSalida the xmlHREPuntosSalida to set
	 */
	public void setXmlHREPuntosSalida(XmlHREPuntosSalida xmlHREPuntosSalida) {
		this.xmlHREPuntosSalida = xmlHREPuntosSalida;
	}
}
