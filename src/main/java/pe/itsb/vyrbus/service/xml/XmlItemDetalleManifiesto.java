/**
 * Proyecto		: SISVYR
 * Sistema		: Sistema de Ventas y Reservas
 * Descripci�n	:
 * Autor		: Jos� Abanto
 * Fecha		: 20 nov. 2022
 * Hora			: 02:53:17
 */
package pe.itsb.vyrbus.service.xml;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlType;

/**
 * @author abant
 *
 */
@XmlType
public class XmlItemDetalleManifiesto implements Serializable{
	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	private Integer asiento;
	private String boleto;
	private String pasajero;
	private String edad;
	private String tipoDocumento;
	private String numeroDocumento;
	private String destino;
	private String puntoEmbarque;
	private String formaPago;
	private String importe;
	/**
	 * @return the asiento
	 */
	public Integer getAsiento() {
		return asiento;
	}
	/**
	 * @param asiento the asiento to set
	 */
	public void setAsiento(Integer asiento) {
		this.asiento = asiento;
	}
	/**
	 * @return the boleto
	 */
	public String getBoleto() {
		return boleto;
	}
	/**
	 * @param boleto the boleto to set
	 */
	public void setBoleto(String boleto) {
		this.boleto = boleto;
	}

	/**
	 * @return the edad
	 */
	public String getEdad() {
		return edad;
	}
	/**
	 * @param edad the edad to set
	 */
	public void setEdad(String edad) {
		this.edad = edad;
	}
	/**
	 * @return the tipoDocumento
	 */
	public String getTipoDocumento() {
		return tipoDocumento;
	}
	/**
	 * @param tipoDocumento the tipoDocumento to set
	 */
	public void setTipoDocumento(String tipoDocumento) {
		this.tipoDocumento = tipoDocumento;
	}
	/**
	 * @return the numeroDocumento
	 */
	public String getNumeroDocumento() {
		return numeroDocumento;
	}
	/**
	 * @param numeroDocumento the numeroDocumento to set
	 */
	public void setNumeroDocumento(String numeroDocumento) {
		this.numeroDocumento = numeroDocumento;
	}
	/**
	 * @return the destino
	 */
	public String getDestino() {
		return destino;
	}
	/**
	 * @param destino the destino to set
	 */
	public void setDestino(String destino) {
		this.destino = destino;
	}
	/**
	 * @return the puntoEmbarque
	 */
	public String getPuntoEmbarque() {
		return puntoEmbarque;
	}
	/**
	 * @param puntoEmbarque the puntoEmbarque to set
	 */
	public void setPuntoEmbarque(String puntoEmbarque) {
		this.puntoEmbarque = puntoEmbarque;
	}
	/**
	 * @return the formaPago
	 */
	public String getFormaPago() {
		return formaPago;
	}
	/**
	 * @param formaPago the formaPago to set
	 */
	public void setFormaPago(String formaPago) {
		this.formaPago = formaPago;
	}
	/**
	 * @return the importe
	 */
	public String getImporte() {
		return importe;
	}
	/**
	 * @param importe the importe to set
	 */
	public void setImporte(String importe) {
		this.importe = importe;
	}
	/**
	 * @return the pasajero
	 */
	public String getPasajero() {
		return pasajero;
	}
	/**
	 * @param pasajero the pasajero to set
	 */
	public void setPasajero(String pasajero) {
		this.pasajero = pasajero;
	}
}
