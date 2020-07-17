/**
 * Proyecto		: SISVYR
 * Sistema		: Sistema de Ventas y Reservas
 * Descripción	: Implementación de métodos que permiten el acceso al modelo.
 * Autor		: José Sullo Avalos
 * Fecha		: 28/09/2012
 */
package com.cystesoft.vyrbus.service.business.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

import org.springframework.transaction.annotation.Transactional;

import com.cystesoft.vyrbus.model.bean.DetalleItinerario;
import com.cystesoft.vyrbus.model.bean.Itinerario;
import com.cystesoft.vyrbus.model.bean.ItinerarioAgenciaLlegada;
import com.cystesoft.vyrbus.model.bean.ItinerarioAgenciaPartida;
import com.cystesoft.vyrbus.model.bean.Servicio;
import com.cystesoft.vyrbus.model.bean.VentaPasaje;
import com.cystesoft.vyrbus.model.dao.ItinerarioDAO;
import com.cystesoft.vyrbus.service.business.ItinerarioManager;
import com.cystesoft.vyrbus.service.util.Constantes;

/**
 * @author Jose
 *
 */
public class ItinerarioManagerImpl implements ItinerarioManager {
	private ItinerarioDAO itinerarioDAO;
	
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


}
