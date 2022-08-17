/**
 * Proyecto		: SISVYR
 * Sistema		: Sistema de Ventas y Reservas
 * Descripción	: Implementación de métodos que permiten el acceso al modelo.
 * Autor		: José Sullo Avalos
 * Fecha		: 28/09/2012
 */
package com.cystesoft.vyrbus.service.business.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.TreeMap;

import org.springframework.transaction.annotation.Transactional;

import com.cystesoft.vyrbus.model.bean.DetalleItinerario;
import com.cystesoft.vyrbus.model.bean.Itinerario;
import com.cystesoft.vyrbus.model.bean.ItinerarioAgenciaLlegada;
import com.cystesoft.vyrbus.model.bean.ItinerarioAgenciaLlegadaID;
import com.cystesoft.vyrbus.model.bean.ItinerarioAgenciaPartida;
import com.cystesoft.vyrbus.model.bean.ItinerarioAgenciaPartidaID;
import com.cystesoft.vyrbus.model.bean.Servicio;
import com.cystesoft.vyrbus.model.bean.VentaPasaje;
import com.cystesoft.vyrbus.model.dao.ItinerarioAgenciaLlegadaDAO;
import com.cystesoft.vyrbus.model.dao.ItinerarioAgenciaPartidaDAO;
import com.cystesoft.vyrbus.model.dao.ItinerarioDAO;
import com.cystesoft.vyrbus.service.business.ItinerarioManager;
import com.cystesoft.vyrbus.service.util.Constantes;
import com.cystesoft.vyrbus.service.util.Util;

/**
 * @author Jose
 *
 */
public class ItinerarioManagerImpl implements ItinerarioManager {
	private ItinerarioDAO itinerarioDAO;
	private ItinerarioAgenciaPartidaDAO itinerarioAgenciaPartidaDAO;
	private ItinerarioAgenciaLlegadaDAO itinerarioAgenciaLlegadaDAO;

	/**
	 * @return the itinerarioDAO
	 */
	public ItinerarioDAO getItinerarioDAO() {
		return itinerarioDAO;
	}
	/**
	 * @param itinerarioDAO the itinerarioDAO to set
	 */
	public void setItinerarioDAO(ItinerarioDAO itinerarioDAO) {
		this.itinerarioDAO = itinerarioDAO;
	}

	/**
	 * @return the itinerarioAgenciaPartidaDAO
	 */
	public ItinerarioAgenciaPartidaDAO getItinerarioAgenciaPartidaDAO() {
		return itinerarioAgenciaPartidaDAO;
	}
	/**
	 * @param itinerarioAgenciaPartidaDAO the itinerarioAgenciaPartidaDAO to set
	 */
	public void setItinerarioAgenciaPartidaDAO(ItinerarioAgenciaPartidaDAO itinerarioAgenciaPartidaDAO) {
		this.itinerarioAgenciaPartidaDAO = itinerarioAgenciaPartidaDAO;
	}
	/**
	 * @return the itinerarioAgenciaLlegadaDAO
	 */
	public ItinerarioAgenciaLlegadaDAO getItinerarioAgenciaLlegadaDAO() {
		return itinerarioAgenciaLlegadaDAO;
	}
	/**
	 * @param itinerarioAgenciaLlegadaDAO the itinerarioAgenciaLlegadaDAO to set
	 */
	public void setItinerarioAgenciaLlegadaDAO(ItinerarioAgenciaLlegadaDAO itinerarioAgenciaLlegadaDAO) {
		this.itinerarioAgenciaLlegadaDAO = itinerarioAgenciaLlegadaDAO;
	}
	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.service.business.ItinerarioManager#buscarItinerarios(java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	public List<DetalleItinerario> buscarItinerarios(String fechaPartida, String origen, String destino) throws Exception {
		return getItinerarioDAO().buscarItinerarios(fechaPartida, origen, destino);
	}

	@Override
	public List<DetalleItinerario> buscarItinerariosMantenimiento(Long idItinerario, String origen, String destino,String fechaInicio, String fechaFinal,
			String Servicio,String tipoDeItinerario, String criterioOrden)throws Exception {
		return getItinerarioDAO().buscarItinerariosMantenimiento(idItinerario, origen, destino, fechaInicio, fechaFinal, Servicio,tipoDeItinerario, criterioOrden);
	}
	@Override
	public List<DetalleItinerario> buscarItinerariosFechaAbierta(String fechaPartida, String origen, String destino) throws Exception {
		return getItinerarioDAO().buscarItinerariosFechaAbierta(fechaPartida, origen, destino);
	}

	/*
	 * (non-Javadoc)
	 * @see com.cystesoft.vyrbus.service.business.ItinerarioManager#buscarAgenciasPartida(java.lang.Long, java.lang.String, java.lang.Integer)
	 */
	@Override
	public ArrayList<ItinerarioAgenciaPartida> buscarAgenciasPartida(Long idItinerario, String estado, Integer idLocalidad) throws Exception {
		return getItinerarioDAO().buscarAgenciasPartida(idItinerario, estado, idLocalidad);
	}

	/*
	 * (non-Javadoc)
	 * @see com.cystesoft.vyrbus.service.business.ItinerarioManager#buscarAgenciasLlegada(java.lang.Long, java.lang.String, java.lang.Integer)
	 */
	@Override
	public ArrayList<ItinerarioAgenciaLlegada> buscarAgenciasLlegada(Long idItinerario, String estado, Integer idLocalidad) throws Exception {
		return getItinerarioDAO().buscarAgenciasLlegada(idItinerario, estado, idLocalidad);
	}

	/*
	 * (non-Javadoc)
	 * @see com.tepsa.sisvyr.service.business.ItinerarioManager#guardar(com.tepsa.sisvyr.model.bean.Itinerario)
	 */
	@Override
	@Transactional
	public int guardar(Itinerario itinerario) throws Exception {
		int result = Constantes.FAILURE;
		try{
			getItinerarioDAO().save(itinerario);

			result = Constantes.CORRECT;
		}catch(Exception ex){
			throw new Exception(ex);
		}
		return result;
	}

	/*
	 * (non-Javadoc)
	 * @see com.tepsa.sisvyr.service.business.ItinerarioManager#actualizar(com.tepsa.sisvyr.model.bean.Itinerario)
	 */
	@Override
	@Transactional
	public void actualizar(Itinerario itinerario) throws Exception {
		getItinerarioDAO().update(itinerario);
	}

	/*
	 * (non-Javadoc)
	 * @see com.tepsa.sisvyr.service.business.ItinerarioManager#inactivar(java.lang.Long, java.lang.Integer)
	 */
	@Override
	@Transactional
	public void inactivar(Long id, Integer idUsuario) throws Exception {
		getItinerarioDAO().inactivar(id,idUsuario);
	}

	/*
	 * (non-Javadoc)
	 * @see com.tepsa.sisvyr.service.business.ItinerarioManager#buscarPorX(java.util.TreeMap, java.util.List)
	 */
	@Override
	public ArrayList<Itinerario> buscarPorX(TreeMap<String, Object> criteriosBusqueda,List<String> criteriosOrdenar) {
		return getItinerarioDAO().buscarPorX(criteriosBusqueda, criteriosOrdenar);
	}

	@Override
	public Itinerario buscarPorId(Long id) {
		return getItinerarioDAO().buscarPorId(id);
	}

//	@Override
//	public Calendar fechaServer() {
//		return getItinerarioDAO().fechaServer();
//	}


	/*
	 * (non-Javadoc)
	 * @see com.tepsa.sisvyr.service.business.ItinerarioManager#validadIngresoTramos(java.lang.Integer, java.lang.Integer, java.lang.Integer, java.lang.String, java.lang.String, java.lang.Integer, java.lang.Integer)
	 */
	@Override
	public List<DetalleItinerario> validadIngresoTramos(Integer idTipoItinerario, Integer idServicio, Integer idRuta,String fechaPartida, String horaPartida, Integer idAgenciaPartida,
			Integer idAgenciaLlegada) throws Exception {

		return getItinerarioDAO().validadIngresoTramos(idTipoItinerario, idServicio, idRuta, fechaPartida, horaPartida, idAgenciaPartida, idAgenciaLlegada);
	}


	/*
	 * (non-Javadoc)
	 * @see com.tepsa.sisvyr.service.business.ItinerarioManager#validaCapasidadServicioAsiento(java.lang.Long, java.lang.Integer)
	 */
	@Override
	public boolean validaCapasidadServicioAsiento(Long idItinerario,Servicio servicio) throws Exception {
		return getItinerarioDAO().validaCapasidadServicioAsiento(idItinerario, servicio);
	}

	/*
	 * (non-Javadoc)
	 * @see com.tepsa.sisvyr.service.business.ItinerarioManager#dateServer()
	 */
	@Override
	public String dateServer() {
		return getItinerarioDAO().dateServer();
	}

	/*
	 * (non-Javadoc)
	 * @see com.tepsa.sisvyr.service.business.ItinerarioManager#ObtienerItinerarioAActualizar(java.lang.String, java.lang.String, java.lang.Integer, java.lang.String, java.lang.String, java.lang.Integer, java.lang.Integer, java.lang.Integer, java.lang.Integer)
	 */
	@Override
	public Long ObtienerItinerarioAActualizar(String fechaPartida,Integer idRuta, String secuenciaTramo, String horaPartida,
			Integer idServicio, Integer idTipoItinerario,Integer idAgenciaPartida, Integer idAgenciaLlegada)throws Exception {
		return getItinerarioDAO().ObtienerItinerarioAActualizar(fechaPartida, idRuta, secuenciaTramo, horaPartida, idServicio, idTipoItinerario, idAgenciaPartida, idAgenciaLlegada);

	}

	/*
	 * (non-Javadoc)
	 * @see com.tepsa.sisvyr.service.business.ItinerarioManager#ItinerariosAActualizar(java.lang.String, java.lang.String, java.lang.Integer, java.lang.String, java.lang.String, java.lang.Integer, java.lang.Integer, java.lang.Integer, java.lang.Integer)
	 */
	@Override
	public List<VentaPasaje> buscarItinerariosAActualizar(String fechaInicio,String fechaFin, Integer idRuta, String secuenciaTramo,String horaPartida, Integer idServicio, Integer idTipoItinerario,Integer idAgenciaPartida, Integer idAgenciaLlegada)throws Exception {
		return getItinerarioDAO().buscarItinerariosAActualizar(fechaInicio, fechaFin, idRuta, secuenciaTramo, horaPartida, idServicio, idTipoItinerario, idAgenciaPartida, idAgenciaLlegada);
	}

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.service.business.ItinerarioManager#buscarRutas(java.lang.String, java.lang.String, java.lang.Integer, java.lang.Integer, java.lang.Integer, java.lang.String, java.lang.Long, java.lang.Boolean)
	 */
	@Override
	public List<DetalleItinerario> buscarRutas(String fechaInicio,String fechaFin, Integer idServicio, Integer idOrigen,Integer idDestino, String horaPartida, Long idItinerario,
			Boolean mostrarDetalle, Integer idTipoItinerario) throws Exception {
		// TODO Auto-generated method stub
		return getItinerarioDAO().buscarRutas(fechaInicio, fechaFin, idServicio, idOrigen, idDestino, horaPartida, idItinerario, mostrarDetalle, idTipoItinerario);
	}
	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.service.business.ItinerarioManager#actualizarTarifa(java.lang.Double, java.lang.String, java.lang.String, java.lang.String, java.lang.Integer, java.lang.Integer, java.lang.Double, java.lang.Long, java.lang.Integer, java.lang.String)
	 */
	@Override
	public void actualizarTarifa(Double tarifaNueva, String horaPartida,String fechaInicio, String fechaFin, Integer idRuta,
			Integer idServicio, Double tarifaActual, Long idItinerarioDetalle,String usuarioMod, String ipMod, Double tarifaLista) throws Exception {
		// TODO Auto-generated method stub
		getItinerarioDAO().actualizarTarifa(tarifaNueva, horaPartida, fechaInicio, fechaFin, idRuta, idServicio, tarifaActual, idItinerarioDetalle, usuarioMod, ipMod,tarifaLista);

	}
	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.service.business.ItinerarioManager#buscarItinerarioByRutaMayor(java.lang.String, java.lang.String, java.lang.Integer, java.lang.Integer)
	 */
	@Override
	public List<Itinerario> buscarItinerarioByRutaMayor(String fechaPartida,String horaPartida, Integer localidadIdOrigen,Integer localidadIdDestino) throws Exception {
		// TODO Auto-generated method stub
		return getItinerarioDAO().buscarItinerarioByRutaMayor(fechaPartida, horaPartida, localidadIdOrigen, localidadIdDestino);
	}
	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.service.business.ItinerarioManager#buscarItinerariosByVentaTramo(java.lang.String, java.lang.String, java.lang.Integer, java.lang.Integer, java.lang.String, java.lang.String)
	 */
	@Override
	public List<Itinerario> buscarItinerariosByVentaTramo(String fechaInicial,String fechaFinal, Integer rutaIdMayor, Integer rutaIdTramo,String horaPartidaTramo, String horaPartidaItinerario)throws Exception {
		// TODO Auto-generated method stub
		return getItinerarioDAO().buscarItinerariosByVentaTramo(fechaInicial, fechaFinal, rutaIdMayor, rutaIdTramo, horaPartidaTramo, horaPartidaItinerario);
	}

	/*
	 * (non-Javadoc)
	 * @see com.cystesoft.vyrbus.service.business.ItinerarioManager#modificarHorario(java.lang.Long, java.lang.Long)
	 */
	@Override
	@Transactional
	public int modificarHorario(Long idItinerario, Long diferencia, String login, String ipLocal) throws Exception {
		int result = Constantes.FAILURE;
		try {
			Itinerario oitinerario = buscarPorId(idItinerario);
			//Modificando los datos de la cabecera del Itinerario
			//Concatenamos la fecha con la hora de salida
			String strFechaSalida = Util.DatetoString(oitinerario.getFechaPartida(), Constantes.DATE_FORMAT) + " " + oitinerario.getHoraPartida();
			//Convertimos a Date la fecha y hora de salida
			Date dtFechaSalida = Util.StringtoDate(strFechaSalida, Constantes.DATE_TIME_SHORT_FORMAT);
			//Sumamos o restamos la diferencia de horas
			Long nFechaSalida = dtFechaSalida.getTime()+diferencia;
			dtFechaSalida = new Date(nFechaSalida);
			//Obtenemos la nueva fecha de salida
			Date dtNewFechaSalida = Util.StringtoDate(Util.DatetoString(dtFechaSalida, Constantes.DATE_FORMAT), Constantes.DATE_FORMAT);
			//Convertimos a String la nueva Hora de Salida
			String strNewHoraSalida = Util.DatetoString(dtFechaSalida, Constantes.TIME_SHORT_FORMAT);
			oitinerario.setFechaPartida(dtNewFechaSalida);
			oitinerario.setHoraPartida(strNewHoraSalida);

			System.out.println("Fecha y Hora de Salida " + dtNewFechaSalida + " " + strNewHoraSalida);

			//Concatenamos la fecha con la hora de llegada
			String strFechaLlegada = Util.DatetoString(oitinerario.getFechaLlegada(), Constantes.DATE_FORMAT) + " " + oitinerario.getHoraLlegada();
			//Convertimos a Date la fecha y hora de llegada
			Date dtFechaLlegada = Util.StringtoDate(strFechaLlegada, Constantes.DATE_TIME_SHORT_FORMAT);
			//Sumamos o restamos la diferencia de horas
			Long nFechaLlegada = dtFechaLlegada.getTime()+diferencia;
			dtFechaLlegada = new Date(nFechaLlegada);
			//Obtenemos la nueva fecha de llegada
			Date dtNewFechaLlegada = Util.StringtoDate(Util.DatetoString(dtFechaLlegada, Constantes.DATE_FORMAT), Constantes.DATE_FORMAT);
			//Convertimos a String la nueva Hora de Llegada
			String strNewHoraLlegada = Util.DatetoString(dtFechaLlegada, Constantes.TIME_SHORT_FORMAT);
			oitinerario.setFechaLlegada(dtNewFechaLlegada);
			oitinerario.setHoraLlegada(strNewHoraLlegada);
			oitinerario.setUsuarioModificacion(login);
			oitinerario.setIpModificacion(ipLocal);

			System.out.println("Fecha y Hora de Llegada " + dtNewFechaLlegada + " " + strNewHoraLlegada);

			getItinerarioDAO().save(oitinerario);

			/*Recupera detalle del Itinerario***/
			List<DetalleItinerario> lstDetalleItinerario = buscarItinerariosMantenimiento(idItinerario, "", "", "", "", "","","");

			for (DetalleItinerario detalleItinerario : lstDetalleItinerario) {
				String strFechaSalidaDetalle = new String();
				Date dtFechaSalidaDetalle = new Date();
				Long nFechaSalidaDetalle = null;
				//Concatenamos la fecha con la hora de salida
				strFechaSalidaDetalle = Util.DatetoString(detalleItinerario.getFechaPartida(), Constantes.DATE_FORMAT) + " " + detalleItinerario.getHoraPartida();
				//Convertimos a Date la fecha y hora de salida
				dtFechaSalidaDetalle = Util.StringtoDate(strFechaSalidaDetalle, Constantes.DATE_TIME_SHORT_FORMAT);
				//Sumamos o restamos la diferencia de horas
				nFechaSalidaDetalle = dtFechaSalidaDetalle.getTime()+diferencia;
				dtFechaSalidaDetalle = new Date(nFechaSalidaDetalle);
				//Obtenemos la nueva fecha de salida
				Date dtNewFechaSalidaDetalle = Util.StringtoDate(Util.DatetoString(dtFechaSalidaDetalle, Constantes.DATE_FORMAT), Constantes.DATE_FORMAT);
				//Convertimos a String la nueva Hora de Salida
				String strNewHoraSalidaDetalle = Util.DatetoString(dtFechaSalidaDetalle, Constantes.TIME_SHORT_FORMAT);
				detalleItinerario.setFechaPartida(dtNewFechaSalidaDetalle);
				detalleItinerario.setHoraPartida(strNewHoraSalidaDetalle);

				String strFechaLlegadaDetalle = new String();
				Date dtFechaLlegadaDetalle = new Date();
				Long nFechaLlegadaDetalle = null;
				//Concatenamos la fecha con la hora de salida
				strFechaLlegadaDetalle = Util.DatetoString(detalleItinerario.getFechaLlegada(), Constantes.DATE_FORMAT) + " " + detalleItinerario.getHoraLlegada();
				//Convertimos a Date la fecha y hora de salida
				dtFechaLlegadaDetalle = Util.StringtoDate(strFechaLlegadaDetalle, Constantes.DATE_TIME_SHORT_FORMAT);
				//Sumamos o restamos la diferencia de horas
				nFechaLlegadaDetalle = dtFechaLlegadaDetalle.getTime()+diferencia;
				dtFechaLlegadaDetalle = new Date(nFechaLlegadaDetalle);
				//Obtenemos la nueva fecha de salida
				Date dtNewFechaLlegadaDetalle = Util.StringtoDate(Util.DatetoString(dtFechaLlegadaDetalle, Constantes.DATE_FORMAT), Constantes.DATE_FORMAT);
				//Convertimos a String la nueva Hora de Salida
				String strNewHoraLlegadaDetalle = Util.DatetoString(dtFechaLlegadaDetalle, Constantes.TIME_SHORT_FORMAT);
				detalleItinerario.setFechaLlegada(dtNewFechaLlegadaDetalle);
				detalleItinerario.setHoraLlegada(strNewHoraLlegadaDetalle);
				detalleItinerario.setUsuarioModificacion(login);
				detalleItinerario.setIpModificacion(ipLocal);

				getItinerarioDAO().update(detalleItinerario);
			}

			List<ItinerarioAgenciaPartida> lstItinerarioAgenciaPartida = getItinerarioAgenciaPartidaDAO().buscarAgenciasPartida(idItinerario, Constantes.VALUE_ACTIVO, null);
			for (ItinerarioAgenciaPartida itinerarioAgenciaPartida : lstItinerarioAgenciaPartida) {
				//Concatenamos la fecha con la hora de salida
				String strFechaHoraAgenciaParitda = Util.DatetoString(new Date(), Constantes.DATE_FORMAT) + " " + itinerarioAgenciaPartida.getHoraPartida();
				//Convertimos a Date la fecha y hora de salida
				Date dtFechaHoraAgenciaPartida = Util.StringtoDate(strFechaHoraAgenciaParitda, Constantes.DATE_TIME_SHORT_FORMAT);
				//Sumamos o restamos la diferencia de horas
				Long nFechaHoraAgenciaPartida = dtFechaHoraAgenciaPartida.getTime()+diferencia;
				dtFechaHoraAgenciaPartida = new Date(nFechaHoraAgenciaPartida);
				//Convertimos a String la nueva Hora de Salida
				String strNewHoraAgenciaPartida = Util.DatetoString(dtFechaHoraAgenciaPartida, Constantes.TIME_SHORT_FORMAT);
				itinerarioAgenciaPartida.setHoraPartida(strNewHoraAgenciaPartida);
				itinerarioAgenciaPartida.setUsuarioModificacion(login);
				itinerarioAgenciaPartida.setIpModificacion(ipLocal);
				ItinerarioAgenciaPartidaID itinerarioAgenciaPartidaID = new ItinerarioAgenciaPartidaID(itinerarioAgenciaPartida.getItinerario().getId(), itinerarioAgenciaPartida.getAgencia().getId());
				itinerarioAgenciaPartida.setItinerarioAgenciaPartidaID(itinerarioAgenciaPartidaID);
				getItinerarioAgenciaPartidaDAO().update(itinerarioAgenciaPartida);
			}

			List<ItinerarioAgenciaLlegada> lstItinerarioAgenciaLlegada = getItinerarioAgenciaLlegadaDAO().buscarAgenciasLlegada(idItinerario, Constantes.VALUE_ACTIVO, null);
			for (ItinerarioAgenciaLlegada itinerarioAgenciaLlegada : lstItinerarioAgenciaLlegada) {
				//Concatenamos la fecha con la hora de llegada
				String strFechaHoraAgenciaLlegada = Util.DatetoString(new Date(), Constantes.DATE_FORMAT) + " " + itinerarioAgenciaLlegada.getHoraLlegada();
				//Convertimos a Date la fecha y hora de llegada
				Date dtFechaHoraAgenciaLlegada = Util.StringtoDate(strFechaHoraAgenciaLlegada, Constantes.DATE_TIME_SHORT_FORMAT);
				//Sumamos o restamos la diferencia de horas
				Long nFechaHoraAgenciaLlegada = dtFechaHoraAgenciaLlegada.getTime()+diferencia;
				dtFechaHoraAgenciaLlegada = new Date(nFechaHoraAgenciaLlegada);
				//Convertimos a String la nueva Hora de Salida
				String strNewHoraAgenciaLlegada = Util.DatetoString(dtFechaHoraAgenciaLlegada, Constantes.TIME_SHORT_FORMAT);
				itinerarioAgenciaLlegada.setHoraLlegada(strNewHoraAgenciaLlegada);
				itinerarioAgenciaLlegada.setUsuarioModificacion(login);
				itinerarioAgenciaLlegada.setIpModificacion(ipLocal);
				ItinerarioAgenciaLlegadaID itinerarioAgenciaLlegadaID = new ItinerarioAgenciaLlegadaID(itinerarioAgenciaLlegada.getItinerario().getId(), itinerarioAgenciaLlegada.getAgencia().getId());
				itinerarioAgenciaLlegada.setItinerarioAgenciaLlegadaID(itinerarioAgenciaLlegadaID);
				getItinerarioAgenciaLlegadaDAO().update(itinerarioAgenciaLlegada);
			}
			result = Constantes.CORRECT;
		}catch (Exception ex) {
			ex.printStackTrace();
			throw new Exception();
		}
		return result;
	}
}
