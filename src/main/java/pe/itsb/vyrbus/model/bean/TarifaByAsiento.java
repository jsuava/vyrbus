/**
 * Proyecto		: SISVYR
 * Sistema		: Sistema de Ventas y Reservas
 * Descripción	: 
 * Autor		: José Abanto
 * Fecha		: 9 may. 2024
 * Hora			: 20:24:47
 */
package pe.itsb.vyrbus.model.bean;

import java.io.Serializable;
import java.util.Date;
import java.util.List;


/**
 * @author abant
 *
 */
public class TarifaByAsiento extends GenericBean implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Long id;
	private Date fecha;
	private Servicio servicio;
	private Ruta ruta;
	private Empresa empresa;
	
	private List<TarifaByAsientoDetalle> listTarifaByAsientoDetalle;	//No mapeado
	
	
	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(Long id) {
		this.id = id;
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
	 * @return the listTarifaByAsientoDetalle
	 */
	public List<TarifaByAsientoDetalle> getListTarifaByAsientoDetalle() {
		return listTarifaByAsientoDetalle;
	}
	/**
	 * @param listTarifaByAsientoDetalle the listTarifaByAsientoDetalle to set
	 */
	public void setListTarifaByAsientoDetalle(List<TarifaByAsientoDetalle> listTarifaByAsientoDetalle) {
		this.listTarifaByAsientoDetalle = listTarifaByAsientoDetalle;
	}
	/**
	 * @return the empresa
	 */
	public Empresa getEmpresa() {
		return empresa;
	}
	/**
	 * @param empresa the empresa to set
	 */
	public void setEmpresa(Empresa empresa) {
		this.empresa = empresa;
	}
	
}
