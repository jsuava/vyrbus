/**
 * Proyecto		: SISVYR
 * Sistema		: Sistema de Ventas y Reservas
 * Descripción	: 
 * Autor		: José Abanto
 * Fecha		: 06/09/2016
 * Hora			: 14:13:00
 */
package com.cystesoft.vyrbus.model.bean;

import java.util.List;
import java.util.TreeMap;

import org.zkoss.zul.Button;
import org.zkoss.zul.Listbox;


/**
 * @author jabanto
 *
 */
public class ObjectCiva {
	private Integer IdBus;
    private String TipoServicio;
    private String RutaBus;
    private String IdEscala;
    private String RutaRecorrido;
    private String FechaSalidaBus;
    private String FechaLlegadaBus;
    private String DireccionSalidaBus;
    private String DireccionLlegadaBus;
    private String HoraEmbarque;
    private String HoraDesembarque;
    private String DireccionEmbarque;
    private String DireccionDesembarque;
    private String TotalHoras;
    private Double PrecioPiso1;
    private Double PrecioPiso2;
    private Integer IdOrigen;
    private Integer IdDestino;
    private Integer IdServicio;
    
    private Integer reservaID;
    private String reservaFechaExpirarion;
    private List<TreeMap<String, Object>> menus;
    private Double precioAsiento;
    
    
    private Listbox listAsientos;
	private Button btnNextTabVenta;
	
	/**
	 * @return the idBus
	 */
	public Integer getIdBus() {
		return IdBus;
	}
	/**
	 * @param idBus the idBus to set
	 */
	public void setIdBus(Integer idBus) {
		IdBus = idBus;
	}
	/**
	 * @return the tipoServicio
	 */
	public String getTipoServicio() {
		return TipoServicio.toUpperCase();
	}
	/**
	 * @param tipoServicio the tipoServicio to set
	 */
	public void setTipoServicio(String tipoServicio) {
		TipoServicio = tipoServicio;
	}
	/**
	 * @return the rutaBus
	 */
	public String getRutaBus() {
		return RutaBus.toUpperCase();
	}
	/**
	 * @param rutaBus the rutaBus to set
	 */
	public void setRutaBus(String rutaBus) {
		RutaBus = rutaBus;
	}
	/**
	 * @return the idEscala
	 */
	public String getIdEscala() {
		return IdEscala;
	}
	/**
	 * @param idEscala the idEscala to set
	 */
	public void setIdEscala(String idEscala) {
		IdEscala = idEscala;
	}
	/**
	 * @return the rutaRecorrido
	 */
	public String getRutaRecorrido() {
		return RutaRecorrido.toUpperCase();
	}
	/**
	 * @param rutaRecorrido the rutaRecorrido to set
	 */
	public void setRutaRecorrido(String rutaRecorrido) {
		RutaRecorrido = rutaRecorrido;
	}
	/**
	 * @return the fechaSalidaBus
	 */
	public String getFechaSalidaBus() {
		return FechaSalidaBus;
	}
	/**
	 * @param fechaSalidaBus the fechaSalidaBus to set
	 */
	public void setFechaSalidaBus(String fechaSalidaBus) {
		FechaSalidaBus = fechaSalidaBus;
	}
	/**
	 * @return the fechaLlegadaBus
	 */
	public String getFechaLlegadaBus() {
		return FechaLlegadaBus;
	}
	/**
	 * @param fechaLlegadaBus the fechaLlegadaBus to set
	 */
	public void setFechaLlegadaBus(String fechaLlegadaBus) {
		FechaLlegadaBus = fechaLlegadaBus;
	}
	/**
	 * @return the direccionSalidaBus
	 */
	public String getDireccionSalidaBus() {
		return DireccionSalidaBus.toUpperCase();
	}
	/**
	 * @param direccionSalidaBus the direccionSalidaBus to set
	 */
	public void setDireccionSalidaBus(String direccionSalidaBus) {
		DireccionSalidaBus = direccionSalidaBus;
	}
	/**
	 * @return the direccionLlegadaBus
	 */
	public String getDireccionLlegadaBus() {
		return DireccionLlegadaBus.toUpperCase();
	}
	/**
	 * @param direccionLlegadaBus the direccionLlegadaBus to set
	 */
	public void setDireccionLlegadaBus(String direccionLlegadaBus) {
		DireccionLlegadaBus = direccionLlegadaBus;
	}
	/**
	 * @return the horaEmbarque
	 */
	public String getHoraEmbarque() {
		return HoraEmbarque;
	}
	/**
	 * @param horaEmbarque the horaEmbarque to set
	 */
	public void setHoraEmbarque(String horaEmbarque) {
		HoraEmbarque = horaEmbarque;
	}
	/**
	 * @return the horaDesembarque
	 */
	public String getHoraDesembarque() {
		return HoraDesembarque.toUpperCase();
	}
	/**
	 * @param horaDesembarque the horaDesembarque to set
	 */
	public void setHoraDesembarque(String horaDesembarque) {
		HoraDesembarque = horaDesembarque;
	}
	/**
	 * @return the direccionEmbarque
	 */
	public String getDireccionEmbarque() {
		return DireccionEmbarque.toUpperCase();
	}
	/**
	 * @param direccionEmbarque the direccionEmbarque to set
	 */
	public void setDireccionEmbarque(String direccionEmbarque) {
		DireccionEmbarque = direccionEmbarque;
	}
	/**
	 * @return the direccionDesembarque
	 */
	public String getDireccionDesembarque() {
		return DireccionDesembarque.toUpperCase();
	}
	/**
	 * @param direccionDesembarque the direccionDesembarque to set
	 */
	public void setDireccionDesembarque(String direccionDesembarque) {
		DireccionDesembarque = direccionDesembarque;
	}
	/**
	 * @return the totalHoras
	 */
	public String getTotalHoras() {
		return TotalHoras;
	}
	/**
	 * @param totalHoras the totalHoras to set
	 */
	public void setTotalHoras(String totalHoras) {
		TotalHoras = totalHoras;
	}
	/**
	 * @return the precioPiso1
	 */
	public Double getPrecioPiso1() {
		return PrecioPiso1;
	}
	/**
	 * @param precioPiso1 the precioPiso1 to set
	 */
	public void setPrecioPiso1(Double precioPiso1) {
		PrecioPiso1 = precioPiso1;
	}
	/**
	 * @return the precioPiso2
	 */
	public Double getPrecioPiso2() {
		return PrecioPiso2;
	}
	/**
	 * @param precioPiso2 the precioPiso2 to set
	 */
	public void setPrecioPiso2(Double precioPiso2) {
		PrecioPiso2 = precioPiso2;
	}
	/**
	 * @return the idOrigen
	 */
	public Integer getIdOrigen() {
		return IdOrigen;
	}
	/**
	 * @param idOrigen the idOrigen to set
	 */
	public void setIdOrigen(Integer idOrigen) {
		IdOrigen = idOrigen;
	}
	/**
	 * @return the idDestino
	 */
	public Integer getIdDestino() {
		return IdDestino;
	}
	/**
	 * @param idDestino the idDestino to set
	 */
	public void setIdDestino(Integer idDestino) {
		IdDestino = idDestino;
	}
	/**
	 * @return the idServicio
	 */
	public Integer getIdServicio() {
		return IdServicio;
	}
	/**
	 * @param idServicio the idServicio to set
	 */
	public void setIdServicio(Integer idServicio) {
		IdServicio = idServicio;
	}
	/**
	 * @return the reservaID
	 */
	public Integer getReservaID() {
		return reservaID;
	}
	/**
	 * @param reservaID the reservaID to set
	 */
	public void setReservaID(Integer reservaID) {
		this.reservaID = reservaID;
	}
	/**
	 * @return the reservaFechaExpirarion
	 */
	public String getReservaFechaExpirarion() {
		return reservaFechaExpirarion;
	}
	/**
	 * @param reservaFechaExpirarion the reservaFechaExpirarion to set
	 */
	public void setReservaFechaExpirarion(String reservaFechaExpirarion) {
		this.reservaFechaExpirarion = reservaFechaExpirarion;
	}
	/**
	 * @return the menus
	 */
	public List<TreeMap<String, Object>> getMenus() {
		return menus;
	}
	/**
	 * @param menus the menus to set
	 */
	public void setMenus(List<TreeMap<String, Object>> menus) {
		this.menus = menus;
	}
	/**
	 * @return the precioAsiento
	 */
	public Double getPrecioAsiento() {
		return precioAsiento;
	}
	/**
	 * @param precioAsiento the precioAsiento to set
	 */
	public void setPrecioAsiento(Double precioAsiento) {
		this.precioAsiento = precioAsiento;
	}
	/**
	 * @return the listAsientos
	 */
	public Listbox getListAsientos() {
		return listAsientos;
	}
	/**
	 * @param listAsientos the listAsientos to set
	 */
	public void setListAsientos(Listbox listAsientos) {
		this.listAsientos = listAsientos;
	}
	/**
	 * @return the btnNextTabVenta
	 */
	public Button getBtnNextTabVenta() {
		return btnNextTabVenta;
	}
	/**
	 * @param btnNextTabVenta the btnNextTabVenta to set
	 */
	public void setBtnNextTabVenta(Button btnNextTabVenta) {
		this.btnNextTabVenta = btnNextTabVenta;
	}
	
}
