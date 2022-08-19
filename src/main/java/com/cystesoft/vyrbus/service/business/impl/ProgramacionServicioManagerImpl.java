package com.cystesoft.vyrbus.service.business.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

import org.springframework.transaction.annotation.Transactional;

import com.cystesoft.vyrbus.model.bean.ProgramacionServicio;
import com.cystesoft.vyrbus.model.dao.ProgramacionServicioDAO;
import com.cystesoft.vyrbus.service.business.ProgramacionServicioManager;
import com.cystesoft.vyrbus.service.exceptions.DuplicateSeatException;
import com.cystesoft.vyrbus.service.util.Constantes;

public class ProgramacionServicioManagerImpl implements ProgramacionServicioManager {
	private ProgramacionServicioDAO programacionServicioDAO;

	public ProgramacionServicioDAO getProgramacionServiciosDAO(){
		return programacionServicioDAO;
	}

	public void setProgramacionServicioDAO(ProgramacionServicioDAO programacionServicioDAO) {
		this.programacionServicioDAO = programacionServicioDAO;
	}


	/*
	 * (non-Javadoc)
	 * @see com.tepsa.sisvyr.service.business.ProgramacionServicioManager#guardar(com.tepsa.sisvyr.model.bean.ProgramacionServicio)
	 */
	@Override
	@Transactional
	public int guardar(ProgramacionServicio programacionServicio)throws Exception {
		int result = Constantes.FAILURE;
		try{
			TreeMap<String, Object>criteriosBusqueda=new TreeMap<>();
			criteriosBusqueda.put("itinerario", programacionServicio.getItinerario());
			criteriosBusqueda.put("estadoRegistro", Constantes.VALUE_ACTIVO);
			List<ProgramacionServicio>lstProgramacion=getProgramacionServiciosDAO().buscarPorX(criteriosBusqueda, null);
			if(lstProgramacion.size()>0)
				throw new DuplicateSeatException();

			getProgramacionServiciosDAO().save(programacionServicio);

			result = Constantes.CORRECT;
		}catch(Exception ex){
			throw new Exception(ex);
		}
		return result;
	}

	/*
	 * (non-Javadoc)
	 * @see com.tepsa.sisvyr.service.business.ProgramacionServicioManager#actualizar(com.tepsa.sisvyr.model.bean.ProgramacionServicio)
	 */
	@Override
	@Transactional
	public void actualizar(ProgramacionServicio programacionServicio)throws Exception {
		getProgramacionServiciosDAO().update(programacionServicio);

	}

	@Override
	@Transactional
	public void inactivar(Long id) throws Exception {
		getProgramacionServiciosDAO().inactivar(id);
	}

	/*
	 * (non-Javadoc)
	 * @see com.tepsa.sisvyr.service.business.ProgramacionServicioManager#buscarPorX(java.util.TreeMap, java.util.List)
	 */
	@Override
	public ArrayList<ProgramacionServicio> buscarPorX(TreeMap<String, Object> criteriosBusqueda,List<String> criteriosOrdenar) {
		return getProgramacionServiciosDAO().buscarPorX(criteriosBusqueda, criteriosOrdenar);
	}

	/*
	 * (non-Javadoc)
	 * @see com.tepsa.sisvyr.service.business.ProgramacionServicioManager#buscarItinerariosProgramados(java.lang.Long, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.Boolean)
	 */
	@Override
	public List<ProgramacionServicio> buscarItinerariosProgramados(Long idItinerario, String origen, String destino,String fechaInicio, String fechaFinal, String Servicio,
			Boolean itinerariosProgramados) throws Exception {
		return getProgramacionServiciosDAO().buscarItinerariosProgramados(idItinerario, origen, destino, fechaInicio, fechaFinal, Servicio, itinerariosProgramados);
	}

	@Override
	public List<ProgramacionServicio> validacionBusesProgramados(String fechaPartida,String horaPartida,Integer idBus,Integer idAgeciaPartida) throws Exception {
		return getProgramacionServiciosDAO().validacionBusesProgramados(fechaPartida,horaPartida, idBus,idAgeciaPartida);
	}

	/*
	 * (non-Javadoc)
	 * @see com.tepsa.sisvyr.service.business.ProgramacionServicioManager#udateItinerario(java.lang.Long, java.lang.Long)
	 */
	@Override
	public void updateItinerarioBus(Long idItinerario, Long idBus) throws Exception {
		getProgramacionServiciosDAO().updateItinerarioBus(idItinerario, idBus);

	}

	/*
	 * (non-Javadoc)
	 * @see com.tepsa.sisvyr.service.business.ProgramacionServicioManager#validaIngresoChoferTripulante(java.lang.String, java.lang.Integer, java.lang.Integer, java.lang.Integer)
	 */
	@Override
	public ProgramacionServicio validaIngresoChoferTripulante(String fecha,Long idPiloto, Long idCopiloto, Long idTripulante,String horaPartida, Integer idAgenciaPartida)throws Exception {
		return getProgramacionServiciosDAO().validaIngresoChoferTripulante(fecha, idPiloto, idCopiloto, idTripulante,horaPartida,idAgenciaPartida);
	}

	/*
	 * (non-Javadoc)
	 * @see com.tepsa.sisvyr.service.business.ProgramacionServicioManager#buscarProgracion(java.lang.Integer, java.lang.String, java.lang.String, java.lang.Boolean)
	 */
	@Override
	public List<ProgramacionServicio> buscarProgracion(Integer idAgencia,String fecha, String codigoBus,Boolean isSalida) throws Exception {
		return getProgramacionServiciosDAO().buscarProgracion(idAgencia, fecha,codigoBus,isSalida);
	}

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.service.business.ProgramacionServicioManager#buscarPorId(java.lang.Long)
	 */
	@Override
	public ProgramacionServicio buscarPorId(Long id) throws Exception {
		// TODO Auto-generated method stub
		return getProgramacionServiciosDAO().buscarPorId(id);
	}


}
