/**
 * Proyecto		: SISVYR
 * Sistema		: Sistema de Ventas y Reservas
 * Descripci�n	:
 * Autor		: Jos� Abanto
 * Fecha		: 05/09/2016
 * Hora			: 09:34:32
 */
package pe.itsb.vyrbus.service.util;

import org.zkoss.zul.Groupbox;

import cruzdelsur.ws.com.pe.ResultBloquearAsiento;
import pe.itsb.vyrbus.model.bean.Agencia;
import pe.itsb.vyrbus.model.bean.ObjectCiva;
import pe.itsb.vyrbus.model.bean.ObjectCruzdelsur;
import pe.itsb.vyrbus.model.bean.Usuario;

/**
 * @author jabanto
 *
 */
public class AsientoPool extends Groupbox{

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	private Integer nivelAsiento;
	private String numeroAsiento;
	private ObjectCruzdelsur objectCruzdelsur;
	private Integer disponible;
	private ResultBloquearAsiento resultBloquearAsiento;
	private Double tarifa;
	private Agencia agencia;
	private Usuario usuario;

	private String asientoID;
	private ObjectCiva objectCiva;

	/*Variables staticas*/
	public static String imagenOcupado="/resources/asientos/mp_asientoOcupado.png";
	public static String imagenBloqueado="/resources/asientos/mp_asientoBloqueado.png";

	/**
	 * @return the nivelAsiento
	 */
	public Integer getNivelAsiento() {
		return nivelAsiento;
	}
	/**
	 * @param nivelAsiento the nivelAsiento to set
	 */
	public void setNivelAsiento(Integer nivelAsiento) {
		this.nivelAsiento = nivelAsiento;
	}
	/**
	 * @return the numeroAsiento
	 */
	public String getNumeroAsiento() {
		return numeroAsiento;
	}
	/**
	 * @param numeroAsiento the numeroAsiento to set
	 */
	public void setNumeroAsiento(String numeroAsiento) {
		this.numeroAsiento = numeroAsiento;
	}
	/**
	 * @return the objectCruzdelsur
	 */
	public ObjectCruzdelsur getObjectCruzdelsur() {
		return objectCruzdelsur;
	}
	/**
	 * @param objectCruzdelsur the objectCruzdelsur to set
	 */
	public void setObjectCruzdelsur(ObjectCruzdelsur objectCruzdelsur) {
		this.objectCruzdelsur = objectCruzdelsur;
	}
	/**
	 * @return the disponible
	 */
	public Integer getDisponible() {
		return disponible;
	}
	/**
	 * @param disponible the disponible to set
	 */
	public void setDisponible(Integer disponible) {
		this.disponible = disponible;
	}
	/**
	 * @return the resultBloquearAsiento
	 */
	public ResultBloquearAsiento getResultBloquearAsiento() {
		return resultBloquearAsiento;
	}
	/**
	 * @param resultBloquearAsiento the resultBloquearAsiento to set
	 */
	public void setResultBloquearAsiento(ResultBloquearAsiento resultBloquearAsiento) {
		this.resultBloquearAsiento = resultBloquearAsiento;
	}
	/**
	 * @return the tarifa
	 */
	public Double getTarifa() {
		return tarifa;
	}
	/**
	 * @param tarifa the tarifa to set
	 */
	public void setTarifa(Double tarifa) {
		this.tarifa = tarifa;
	}
	/**
	 * @return the asientoID
	 */
	public String getAsientoID() {
		return asientoID;
	}
	/**
	 * @param asientoID the asientoID to set
	 */
	public void setAsientoID(String asientoID) {
		this.asientoID = asientoID;
	}
	/**
	 * @return the objectCiva
	 */
	public ObjectCiva getObjectCiva() {
		return objectCiva;
	}
	/**
	 * @param objectCiva the objectCiva to set
	 */
	public void setObjectCiva(ObjectCiva objectCiva) {
		this.objectCiva = objectCiva;
	}
	/**
	 * @return the agencia
	 */
	public Agencia getAgencia() {
		return agencia;
	}
	/**
	 * @param agencia the agencia to set
	 */
	public void setAgencia(Agencia agencia) {
		this.agencia = agencia;
	}
	/**
	 * @return the usuario
	 */
	public Usuario getUsuario() {
		return usuario;
	}
	/**
	 * @param usuario the usuario to set
	 */
	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}



}
