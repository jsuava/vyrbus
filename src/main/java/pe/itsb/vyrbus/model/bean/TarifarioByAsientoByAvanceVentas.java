/**
 * Proyecto		: SISVYR
 * Sistema		: Sistema de Ventas y Reservas
 * Descripción	: 
 * Autor		: José Abanto
 * Fecha		: 9 may. 2024
 * Hora			: 20:38:13
 */
package pe.itsb.vyrbus.model.bean;

import java.io.Serializable;
import java.util.Date;
import java.util.List;


/**
 * @author abant
 *
 */
public class TarifarioByAsientoByAvanceVentas implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;	
	private Integer item;
	private Date fecha;
	private String horaSalida;
	private Servicio servicio;
	private Ruta rutaMayor;
	private Integer ocupacionAsientosSuite;
	private Integer ocupacionAsientosOtros;
	private List<TarifarioByAsientoSubByAvanceVentas> tarifarioByAsientoSubByAvanceVentas;
	private Long itinerario_id;
	
	/**
	 * @return the item
	 */
	public Integer getItem() {
		return item;
	}
	/**
	 * @param item the item to set
	 */
	public void setItem(Integer item) {
		this.item = item;
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
	 * @return the servicio
	 */
	public Servicio getServicio() {
		return servicio;
	}
	/**
	 * @param servicio the servicio to set
	 */
	public void setServicio(Servicio servicio) {
		this.servicio = servicio;
	}
	/**
	 * @return the rutaMayor
	 */
	public Ruta getRutaMayor() {
		return rutaMayor;
	}
	/**
	 * @param rutaMayor the rutaMayor to set
	 */
	public void setRutaMayor(Ruta rutaMayor) {
		this.rutaMayor = rutaMayor;
	}
	/**
	 * @return the ocupacionAsientosSuite
	 */
	public Integer getOcupacionAsientosSuite() {
		return ocupacionAsientosSuite;
	}
	/**
	 * @param ocupacionAsientosSuite the ocupacionAsientosSuite to set
	 */
	public void setOcupacionAsientosSuite(Integer ocupacionAsientosSuite) {
		this.ocupacionAsientosSuite = ocupacionAsientosSuite;
	}
	/**
	 * @return the ocupacionAsientosOtros
	 */
	public Integer getOcupacionAsientosOtros() {
		return ocupacionAsientosOtros;
	}
	/**
	 * @param ocupacionAsientosOtros the ocupacionAsientosOtros to set
	 */
	public void setOcupacionAsientosOtros(Integer ocupacionAsientosOtros) {
		this.ocupacionAsientosOtros = ocupacionAsientosOtros;
	}
	/**
	 * @return the fecha
	 */
	public Date getFecha() {
		return fecha;
	}
	/**
	 * @param fecha the fecha to set
	 */
	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}
	/**
	 * @return the itinerario_id
	 */
	public Long getItinerario_id() {
		return itinerario_id;
	}
	/**
	 * @param itinerario_id the itinerario_id to set
	 */
	public void setItinerario_id(Long itinerario_id) {
		this.itinerario_id = itinerario_id;
	}
	/**
	 * @return the tarifarioByAsientoSubByAvanceVentas
	 */
	public List<TarifarioByAsientoSubByAvanceVentas> getTarifarioByAsientoSubByAvanceVentas() {
		return tarifarioByAsientoSubByAvanceVentas;
	}
	/**
	 * @param tarifarioByAsientoSubByAvanceVentas the tarifarioByAsientoSubByAvanceVentas to set
	 */
	public void setTarifarioByAsientoSubByAvanceVentas(List<TarifarioByAsientoSubByAvanceVentas> tarifarioByAsientoSubByAvanceVentas) {
		this.tarifarioByAsientoSubByAvanceVentas = tarifarioByAsientoSubByAvanceVentas;
	}

}
