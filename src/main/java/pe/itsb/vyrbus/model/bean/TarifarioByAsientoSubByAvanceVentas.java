/**
 * Proyecto		: SISVYR
 * Sistema		: Sistema de Ventas y Reservas
 * Descripción	: 
 * Autor		: José Abanto
 * Fecha		: 9 may. 2024
 * Hora			: 20:39:28
 */
package pe.itsb.vyrbus.model.bean;

import java.io.Serializable;
import java.util.Date;


/**
 * @author abant
 *
 */
public class TarifarioByAsientoSubByAvanceVentas implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String horaSalida;
	private Ruta ruta;
	private Integer ocupacionAsientosSuite;
	private Integer ocupacionAsientosOtros;
	private Date fecha;
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
	 * @return the ruta
	 */
	public Ruta getRuta() {
		return ruta;
	}
	/**
	 * @param ruta the ruta to set
	 */
	public void setRuta(Ruta ruta) {
		this.ruta = ruta;
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
	public Date getFecha() {
		return fecha;
	}
	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

}
