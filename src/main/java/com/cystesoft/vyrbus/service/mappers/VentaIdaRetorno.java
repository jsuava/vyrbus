/**
 * Proyecto		: SISVYR
 * Sistema		: Sistema de Ventas y Reservas
 * Descripción	:
 * Autor		: José Sullo Avalos
 * Fecha		: 03/09/2013
 */
package com.cystesoft.vyrbus.service.mappers;

import java.io.Serializable;

import com.cystesoft.vyrbus.model.bean.DetalleItinerario;
import com.cystesoft.vyrbus.model.bean.VentaPasaje;

/**
 * @author Jose
 *
 */
public class VentaIdaRetorno implements Serializable {
	private static final long serialVersionUID = 1L;
	private DetalleItinerario detalleItinerarioIDA;
	private DetalleItinerario detalleItinerarioRETORNO;
	private VentaPasaje ventaPasajeIDA;
	private VentaPasaje ventaPasajeRETORNO;

	/**
	 *
	 */
	public VentaIdaRetorno() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param detalleItinerarioIDA
	 * @param detalleItinerarioRETORNO
	 */
	public VentaIdaRetorno(DetalleItinerario detalleItinerarioIDA,
			DetalleItinerario detalleItinerarioRETORNO) {
		super();
		this.detalleItinerarioIDA = detalleItinerarioIDA;
		this.detalleItinerarioRETORNO = detalleItinerarioRETORNO;
	}

	/**
	 * @return the detalleItinerarioIDA
	 */
	public DetalleItinerario getDetalleItinerarioIDA() {
		return detalleItinerarioIDA;
	}
	/**
	 * @param detalleItinerarioIDA the detalleItinerarioIDA to set
	 */
	public void setDetalleItinerarioIDA(DetalleItinerario detalleItinerarioIDA) {
		this.detalleItinerarioIDA = detalleItinerarioIDA;
	}

	/**
	 * @return the detalleItinerarioRETORNO
	 */
	public DetalleItinerario getDetalleItinerarioRETORNO() {
		return detalleItinerarioRETORNO;
	}
	/**
	 * @param detalleItinerarioRETORNO the detalleItinerarioRETORNO to set
	 */
	public void setDetalleItinerarioRETORNO(
			DetalleItinerario detalleItinerarioRETORNO) {
		this.detalleItinerarioRETORNO = detalleItinerarioRETORNO;
	}

	/**
	 * @return the ventaPasajeIDA
	 */
	public VentaPasaje getVentaPasajeIDA() {
		return ventaPasajeIDA;
	}

	/**
	 * @param ventaPasajeIDA the ventaPasajeIDA to set
	 */
	public void setVentaPasajeIDA(VentaPasaje ventaPasajeIDA) {
		this.ventaPasajeIDA = ventaPasajeIDA;
	}

	/**
	 * @return the ventaPasajeRETORNO
	 */
	public VentaPasaje getVentaPasajeRETORNO() {
		return ventaPasajeRETORNO;
	}

	/**
	 * @param ventaPasajeRETORNO the ventaPasajeRETORNO to set
	 */
	public void setVentaPasajeRETORNO(VentaPasaje ventaPasajeRETORNO) {
		this.ventaPasajeRETORNO = ventaPasajeRETORNO;
	}
}
