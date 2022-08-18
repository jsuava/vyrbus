/**
 * Proyecto		: VYRBUS
 * Sistema		: Sistema de Ventas y Reservas
 * Descripción	: Clase utilizada para para representar a los asientos.
 * Autor		: Marco Oscco Espinoza
 * Fecha		: 10/07/2012
 */
package com.cystesoft.vyrbus.service.mappers;

import java.io.Serializable;

import com.cystesoft.vyrbus.model.bean.DetalleItinerario;
import com.cystesoft.vyrbus.model.bean.TipoAsiento;
import com.cystesoft.vyrbus.model.bean.VentaPasaje;
import com.cystesoft.vyrbus.service.util.Constantes;

/**
 *
 * @author José Sullo Avalos
 * @since JDK1.6
 */
public class Asiento extends ElementoBus implements Serializable {
	private static final long serialVersionUID = 1L;
	private Integer numeroAsiento;
	private Integer estadoAsiento;
	private Integer ocupante;
	private String directoryImages = "resources/asientos/";
	private String key;
	private DetalleItinerario detalleItinerario;
	private VentaPasaje ventaPasaje;
	private TipoAsiento tipoAsiento;
	private Integer numeroZona;
//	private Ruta ruta;

	public static final int OCUPANTE_PILOTO = 1;
	public static final int OCUPANTE_COPILOTO = 2;
	public static final int OCUPANTE_TRIPULANTE = 3;
	public static final int OCUPANTE_PASAJERO = 4;


	/**
	 * @return Objeto numeroAsiento.
	 */
	public Integer getNumeroAsiento() {
		return numeroAsiento;
	}
	/**
	 * @param numeroAsiento	: Setea el objeto numeroAsiento.
	 */
	public void setNumeroAsiento(Integer numeroAsiento) {
		this.numeroAsiento = numeroAsiento;
//		generarImagenes();
	}

	public Integer getNumeroZona() {
		return numeroZona;
	}
	public void setNumeroZona(Integer numeroZona) {
		this.numeroZona = numeroZona;
	}
	/**
	 * @return Objeto estadoAsiento.
	 */
	public Integer getEstadoAsiento() {
		return estadoAsiento;
	}
	/**
	 * @param estadoAsiento	: Setea el objeto estadoAsiento.
	 */
	public void setEstadoAsiento(Integer estadoAsiento) {
		this.estadoAsiento = estadoAsiento;
	}

	/**
	 * @return the ocupante
	 */
	public Integer getOcupante() {
		return ocupante;
	}
	/**
	 * @param ocupante the ocupante to set
	 */
	public void setOcupante(Integer ocupante) {
		this.ocupante = ocupante;
	}

	/**
	 * @return the directoryImages
	 */
	public String getDirectoryImages() {
		return directoryImages;
	}
	/**
	 * @param directoryImages the directoryImages to set
	 */
	public void setDirectoryImages(String directoryImages) {
		this.directoryImages = directoryImages;
	}

	public void generarImagenes(){
		StringBuilder pathImage = new StringBuilder();
		pathImage.append(getDirectoryImages()+"asiento");
		switch(this.getOcupante()){
		case OCUPANTE_PILOTO:
			pathImage.append("Piloto");
			break;
		case OCUPANTE_COPILOTO:
			pathImage.append("Copiloto");
			break;

		case OCUPANTE_TRIPULANTE:
			pathImage.append("Tripulante");
			break;

		case OCUPANTE_PASAJERO:
			switch(this.getEstadoAsiento()){
			case Constantes.ASIENTO_DISPONIBLE :
				pathImage.append("Disponible_");
				break;

//			case ESTADO_TRAMO_RESERVADO:
//				pathImage.append("TramoReservado");
//				break;
//
//			case Constantes.ASIENTO_RESERVADO:
//				pathImage.append("Reservado");
//				break;
//
//			case ESTADO_MULTI_RESERVA:
//				pathImage.append("");/*TODO imagen pendiente*/
//				break;
//
//			case Constantes.ASIENTO_BLOQUEADO:
//				pathImage.append("Bloqueado");
//				break;
//
//			case ESTADO_PRIORIDAD_VENTA_TRAMO:
//				pathImage.append("SemiOcupado");
//				break;
//
//			case ESTADO_TRAMO_VENDIDO:
//				pathImage.append("TramoVendido");
//				break;
//
//			case Constantes.ASIENTO_VENDIDO:
//				pathImage.append("Vendido");
//				break;
			}
			pathImage.append(getNumeroAsiento());
		}
		pathImage.append(Constantes.IMAGE_EXTENSION);
		this.setSrc(pathImage.toString());
	}

	/**
	 * @return the detalleItinerario
	 */
	public DetalleItinerario getDetalleItinerario() {
		return detalleItinerario;
	}
	/**
	 * @param detalleItinerario the detalleItinerario to set
	 */
	public void setDetalleItinerario(DetalleItinerario detalleItinerario) {
		this.detalleItinerario = detalleItinerario;
	}

	/**
	 *
	 * @return the ventaPasaje
	 */
	public VentaPasaje getVentaPasaje(){
		return ventaPasaje;
	}
	/***
	 * @param ventaPasaje : ventaPasaje the ventaPasajero to set
	 */
	public void setVentaPasaje(VentaPasaje ventaPasaje){
		this.ventaPasaje=ventaPasaje;
	}


	/**
	 * Clave con el siguiente formato <b>nAsiento-nPiso
	 * @return the key
	 */
	public String getKey() {
		return key;
	}
	/**
	 * Setea el formato de la clave <b>nAsiento-nPiso
	 * @param key the key to set
	 */
	public void setKey() {
		this.key = numeroAsiento + "-"+ getPiso();
	}
	/**
	 * @return the ruta
	 */
//	public Ruta getRuta() {
//		return ruta;
//	}
//	/**
//	 * @param ruta the ruta to set
//	 */
//	public void setRuta(Ruta ruta) {
//		this.ruta = ruta;
//	}
	/**
	 * @return the tipoAsiento
	 */
	public TipoAsiento getTipoAsiento() {
		return tipoAsiento;
	}
	/**
	 * @param tipoAsiento the tipoAsiento to set
	 */
	public void setTipoAsiento(TipoAsiento tipoAsiento) {
		this.tipoAsiento = tipoAsiento;
	}
}
