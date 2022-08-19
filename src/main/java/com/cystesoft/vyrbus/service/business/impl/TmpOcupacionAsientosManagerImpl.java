/**
 * Proyecto		: SISVYR
 * Sistema		: Sistema de Ventas y Reservas
 * Descripción	:
 * Autor		: José Sullo Avalos
 * Fecha		: 14/11/2012
 */
package com.cystesoft.vyrbus.service.business.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.cystesoft.vyrbus.model.bean.TmpOcupacionAsientos;
import com.cystesoft.vyrbus.model.dao.TmpOcupacionAsientosDAO;
import com.cystesoft.vyrbus.model.dao.VentaPasajesDAO;
import com.cystesoft.vyrbus.service.business.TmpOcupacionAsientosManager;
import com.cystesoft.vyrbus.service.exceptions.DuplicateSeatException;
import com.cystesoft.vyrbus.service.util.Constantes;
import com.cystesoft.vyrbus.service.util.Util;

/**
 * @author Jose
 *
 */
public class TmpOcupacionAsientosManagerImpl implements TmpOcupacionAsientosManager {
	private TmpOcupacionAsientosDAO tmpOcupacionAsientosDAO;
	private VentaPasajesDAO ventaPasajesDAO;

	/**
	 * @return the tmpOcupacionAsientosDAO
	 */
	public TmpOcupacionAsientosDAO getTmpOcupacionAsientosDAO() {
		return tmpOcupacionAsientosDAO;
	}
	/**
	 * @param tmpOcupacionAsientosDAO the tmpOcupacionAsientosDAO to set
	 */
	public void setTmpOcupacionAsientosDAO(TmpOcupacionAsientosDAO tmpOcupacionAsientosDAO) {
		this.tmpOcupacionAsientosDAO = tmpOcupacionAsientosDAO;
	}

	/**
	 * @return the ventaPasajesDAO
	 */
	public VentaPasajesDAO getVentaPasajesDAO() {
		return ventaPasajesDAO;
	}
	/**
	 * @param ventaPasajesDAO the ventaPasajesDAO to set
	 */
	public void setVentaPasajesDAO(VentaPasajesDAO ventaPasajesDAO) {
		this.ventaPasajesDAO = ventaPasajesDAO;
	}

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.service.business.TmpOcupacionAsientosManager#bloquearAsiento(com.tepsa.sisvyr.model.bean.TmpOcupacionAsientos)
	 */
	@Override
	@Transactional
	public int bloquearAsiento(TmpOcupacionAsientos tmpOcupacion) throws Exception {
		/*	Validando que el asiento no haya sido utilizado antes de la venta	*/
		Long ocupabilidad = getVentaPasajesDAO().validateSeat(tmpOcupacion.getItinerario(), tmpOcupacion.getRuta(), tmpOcupacion.getNumeroAsiento(), tmpOcupacion.getNumeroPiso());
		String fecha = getVentaPasajesDAO().getDateSystem();
		Date date = new Date(Util.StringtoDate(fecha, Constantes.DATE_TIME_FORMAT).getTime() + (Constantes.TIEMPO_EXPIRA_BLOQUEO * Constantes.MILISEGUNDOS_X_MINUTO));
		tmpOcupacion.setFechaExpiraBloqueo(date);
		if(ocupabilidad.longValue()>0)
			throw new DuplicateSeatException();

		return getTmpOcupacionAsientosDAO().bloquearAsiento(tmpOcupacion);
	}

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.service.business.TmpOcupacionAsientosManager#desbloquearAsiento(com.tepsa.sisvyr.model.bean.TmpOcupacionAsientos)
	 */
	@Override
	@Transactional
	public int desbloquearAsiento(TmpOcupacionAsientos tmpOcupacion) throws Exception {
		return getTmpOcupacionAsientosDAO().desbloquearAsiento(tmpOcupacion);
	}

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.service.business.TmpOcupacionAsientosManager#desbloquearAsientoByUsuarioHardware(java.lang.Integer)
	 */
	@Override
	@Transactional
	public int desbloquearAsientoByUsuarioHardware(Integer idUsuarioHardware) throws Exception {
		return getTmpOcupacionAsientosDAO().desbloquearAsientoByUsuarioHardware(idUsuarioHardware);
	}

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.service.business.TmpOcupacionAsientosManager#buscarAsientosBloqueados(java.lang.String, java.lang.String, java.lang.String)
	 */
//	@Override
//	public List<TmpOcupacionAsientos> buscarAsientosBloqueados(String campo, String patron, String estado) throws Exception {
//		return getTmpOcupacionAsientosDAO().buscarAsientosBloqueados(campo, patron, estado);
//	}

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.service.business.TmpOcupacionAsientosManager#buscarAsientosBloqueados(java.lang.Long)
	 */
	@Override
	public List<TmpOcupacionAsientos> buscarAsientosBloqueados(Long idItinerario) throws Exception {
		return getTmpOcupacionAsientosDAO().buscarAsientosBloqueados(idItinerario);
	}

	@Override
	public ArrayList<TmpOcupacionAsientos> buscarPorEstadoRegistro(String estado) {
		return getTmpOcupacionAsientosDAO().buscarPorEstadoRegistro(estado);
	}
	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.service.business.TmpOcupacionAsientosManager#desbloquearAsientoByUsuarioHardwareAndItinerario(java.lang.Integer, java.lang.Long)
	 */
	@Override
	public int desbloquearAsientoByUsuarioHardwareAndItinerario(Integer idUsuarioHardware, Long idItinerario) throws Exception {
		// TODO Auto-generated method stub
		return getTmpOcupacionAsientosDAO().desbloquearAsientoByUsuarioHardwareAndItinerario(idUsuarioHardware, idItinerario);
	}
	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.service.business.TmpOcupacionAsientosManager#desbloquearAsiento(java.lang.Integer, java.lang.Long, java.lang.Integer)
	 */
	@Override
	public int desbloquearAsiento(Integer idUsuarioHardware, Long idItinerario,
			Integer asiento) throws Exception {
		// TODO Auto-generated method stub
		return getTmpOcupacionAsientosDAO().desbloquearAsiento(idUsuarioHardware, idItinerario, asiento);
	}

}
