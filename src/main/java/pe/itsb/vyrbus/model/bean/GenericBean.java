/**
 * Proyecto		: SISVYR
 * Sistema		: Sistema de Ventas y Reservas
 * Descripción	: Clase que se utilizara para los campos de auditoria.
 * Autor		: José Sullo Avalos
 * Fecha		: 07/04/2012
 */
package pe.itsb.vyrbus.model.bean;

import java.io.Serializable;
import java.util.Date;


/**
 * Bean que utilizaremos para declarar las propiedades de los campos de auditoria.
 * @author José Sullo Avalos
 * @since JDK1.6
 */
public class GenericBean implements Serializable  {
	private static final long serialVersionUID = 1L;
	private String estadoRegistro;
	private Date fechaInsercion;
    private String usuarioInsercion;
    private String ipInsercion;
    private Date fechaModificacion;
    private String usuarioModificacion;
    private String ipModificacion;
    private String codigoUsuarioHardware;



    /**
	 * @return the estadoRegistro
	 */

	public String getEstadoRegistro() {
		return estadoRegistro;
	}
	/**
	 * @param estadoRegistro the estadoRegistro to set
	 */
	public void setEstadoRegistro(String estadoRegistro) {
		this.estadoRegistro = estadoRegistro;
	}

	/**
	 * @return the fechaInsercion
	 */
	public Date getFechaInsercion() {

		return fechaInsercion;
	}
	/**
	 * @param fechaInsercion the fechaInsercion to set
	 */
	public void setFechaInsercion(Date fechaInsercion) {
		this.fechaInsercion = fechaInsercion;
	}

	/**
	 * @return the usuarioInsercion
	 */
	public String getUsuarioInsercion() {

		return usuarioInsercion;
	}
	/**
	 * @param usuarioInsercion the usuarioInsercion to set
	 */
	public void setUsuarioInsercion(String usuarioInsercion) {
		this.usuarioInsercion = usuarioInsercion;
	}

	/**
	 * @return the ipInsercion
	 */
	public String getIpInsercion() {
		return ipInsercion;
	}
	/**
	 * @param ipInsercion the ipInsercion to set
	 */
	public void setIpInsercion(String ipInsercion) {
		this.ipInsercion = ipInsercion;
	}

	/**
	 * @return the fechaModificacion
	 */
	public Date getFechaModificacion() {
		return fechaModificacion;
	}
	/**
	 * @param fechaModificacion the fechaModificacion to set
	 */
	public void setFechaModificacion(Date fechaModificacion) {
		this.fechaModificacion = fechaModificacion;
	}

	/**
	 * @return the usuarioModificacion
	 */
	public String getUsuarioModificacion() {
		return usuarioModificacion;
	}
	/**
	 * @param usuarioModificacion the usuarioModificacion to set
	 */
	public void setUsuarioModificacion(String usuarioModificacion) {
		this.usuarioModificacion = usuarioModificacion;
	}

	/**
	 * @return the ipModificacion
	 */
	public String getIpModificacion() {
		return ipModificacion;
	}
	/**
	 * @param ipModificacion the ipModificacion to set
	 */
	public void setIpModificacion(String ipModificacion) {
		this.ipModificacion = ipModificacion;
	}

	/**
	 *
	 * @return the codigoUsuarioHardware
	 */
	public String getCodigoUsuarioHardware(){
		return codigoUsuarioHardware;
	}

	/**
	 *
	 * @param codigoUsuarioHardware the codigoUsuarioHardware to set
	 */
	public void setCodigoUsuarioHardware (String codigoUsuarioHardware){
		this.codigoUsuarioHardware=codigoUsuarioHardware;
	}



}