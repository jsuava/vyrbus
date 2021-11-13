package com.cystesoft.vyrbus.service.business.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

import org.springframework.transaction.annotation.Transactional;

import com.cystesoft.vyrbus.model.bean.Agencia;
import com.cystesoft.vyrbus.model.bean.UsuarioHardware;
import com.cystesoft.vyrbus.model.dao.AgenciaDAO;
import com.cystesoft.vyrbus.model.dao.TitanDAO;
import com.cystesoft.vyrbus.model.dao.UsuarioHardwareDAO;
import com.cystesoft.vyrbus.service.business.UsuarioHardwareManager;
import com.cystesoft.vyrbus.service.exceptions.AgenciaTranscarNullException;
import com.cystesoft.vyrbus.service.exceptions.CodigoDuplicadoException;
import com.cystesoft.vyrbus.service.exceptions.UsuarioHardwareCargaDuplicateException;
import com.cystesoft.vyrbus.service.exceptions.UsuarioHardwareDuplicidadDescripcionException;
import com.cystesoft.vyrbus.service.util.Constantes;

public class UsuarioHardwareManagerImpl implements UsuarioHardwareManager{
	private UsuarioHardwareDAO usuarioHardwareDAO;
	private AgenciaDAO agenciaDAO;
	private TitanDAO titanDAO;
	
	/**
	 * 
	 * @return
	 */
	public UsuarioHardwareDAO getUsuarioHardwareDAO(){
		return usuarioHardwareDAO;
	}
	/**
	 * 
	 * @param usuarioHardwareDAO
	 */
	public void setUsuarioHardwareDAO (UsuarioHardwareDAO usuarioHardwareDAO){
		this.usuarioHardwareDAO = usuarioHardwareDAO;
	}	
	
	/**
	 * @return the agenciaDAO
	 */
	public AgenciaDAO getAgenciaDAO() {
		return agenciaDAO;
	}
	/**
	 * @param agenciaDAO the agenciaDAO to set
	 */
	public void setAgenciaDAO(AgenciaDAO agenciaDAO) {
		this.agenciaDAO = agenciaDAO;
	}
	
	/**
	 * @return the titanDAO
	 */
	public TitanDAO getTitanDAO() {
		return titanDAO;
	}
	/**
	 * @param titanDAO the titanDAO to set
	 */
	public void setTitanDAO(TitanDAO titanDAO) {
		this.titanDAO = titanDAO;
	}
	/*
	 * (non-Javadoc)
	 * @see com.tepsa.sisvyr.service.business.UsuarioHardwareManager#buscarPorEstadoRegistro(java.lang.String, java.lang.String)
	 */
	@Override
	public ArrayList<UsuarioHardware> buscarPorEstadoRegistro(String estado,String criterioOrden) {
		return getUsuarioHardwareDAO().buscarPorEstadoRegistro(estado, criterioOrden);
	}

	/*
	 * (non-Javadoc)
	 * @see com.tepsa.sisvyr.service.business.UsuarioHardwareManager#buscarPorX(java.util.TreeMap, java.util.List)
	 */
	@Override
	public ArrayList<UsuarioHardware> buscarPorX(TreeMap<String, Object> criteriosBusqueda,List<String> criteriosOrdenar) {
		return getUsuarioHardwareDAO().buscarPorX(criteriosBusqueda, criteriosOrdenar);
	}

	/*
	 * (non-Javadoc)
	 * @see com.tepsa.sisvyr.service.business.UsuarioHardwareManager#buscarPorId(java.lang.Long)
	 */
	@Override
	public UsuarioHardware buscarPorId(Long id) {
		return getUsuarioHardwareDAO().buscarPorId(id);
	}

	/*
	 * (non-Javadoc)
	 * @see com.tepsa.sisvyr.service.business.UsuarioHardwareManager#guardar(com.tepsa.sisvyr.model.bean.UsuarioHardware)
	 */
	@Override
	@Transactional
	public int guardar(UsuarioHardware usuarioHardware) throws Exception {
		int result = Constantes.FAILURE;
		try{
			TreeMap<String, Object> criteriosBusqueda = new TreeMap<String, Object>();
//			Long idAgencia = usuarioHardware.getAgencia().getId().longValue();
			Agencia agencia = getAgenciaDAO().buscarPorId(usuarioHardware.getAgencia().getId().longValue());
			usuarioHardware.setAgencia(agencia);
			if(usuarioHardware.getAgencia().getTipoAgencia().getId().intValue()==Constantes.ID_TIPAGE_TEPSA){
				criteriosBusqueda.put("codigo", usuarioHardware.getCodigo());
				criteriosBusqueda.put("estadoRegistro", Constantes.VALUE_ACTIVO);
				List<?> resultcodigo = getUsuarioHardwareDAO().buscarPorX(criteriosBusqueda, null);
				/*Valida duplicidad del Código*/
				if(resultcodigo.size()>0)
					throw new CodigoDuplicadoException();
			}
			
			criteriosBusqueda = new TreeMap<String, Object>();
			criteriosBusqueda.put("descripcion", usuarioHardware.getDescripcion());
			criteriosBusqueda.put("codigo", usuarioHardware.getCodigo());
			criteriosBusqueda.put("estadoRegistro", Constantes.VALUE_ACTIVO);
			List<?> resultDescripcion = getUsuarioHardwareDAO().buscarPorX(criteriosBusqueda, null);
			/*Valida Duplicidad de la Descripcion*/
			if(resultDescripcion.size()>0)
				throw new UsuarioHardwareDuplicidadDescripcionException();
			
			/*	Validamos que la agencia exista en Carga	*/
			Integer idAgenciaTranscar = getTitanDAO().buscarAgencia(agencia.getId());
			if(idAgenciaTranscar != null)
				usuarioHardware.getTitanUsuarioHardware().setIdAgencia(idAgenciaTranscar);
			else
				throw new AgenciaTranscarNullException();
			
			/*	Validamos que no exista el usuario Hardware en Carga	*/
			String idUsuarioHardware = getTitanDAO().buscarIdUsuarioHardware(usuarioHardware.getTitanUsuarioHardware().getIp());
			if(idUsuarioHardware != null)
				throw new UsuarioHardwareCargaDuplicateException();
			
			getUsuarioHardwareDAO().guardar(usuarioHardware);			
			usuarioHardware.getTitanUsuarioHardware().setIdUsuarioHardwareVyR(usuarioHardware.getId());
			getTitanDAO().guardarUsuarioHardware(usuarioHardware.getTitanUsuarioHardware());
			result = Constantes.CORRECT;
		}catch (CodigoDuplicadoException rsdex){
			throw new CodigoDuplicadoException();
		}catch (UsuarioHardwareDuplicidadDescripcionException uhddex){
			throw new UsuarioHardwareDuplicidadDescripcionException();
		}catch(AgenciaTranscarNullException atnex) {
			throw new AgenciaTranscarNullException();
		}catch(UsuarioHardwareCargaDuplicateException uhcdex) {
			throw new UsuarioHardwareCargaDuplicateException();
		}catch(Exception ex){
			throw new Exception(ex);
		}
		return result;
	}

	/*
	 * (non-Javadoc)
	 * @see com.tepsa.sisvyr.service.business.UsuarioHardwareManager#actualizar(com.tepsa.sisvyr.model.bean.UsuarioHardware)
	 */
	@Override
	@Transactional
	public void actualizar(UsuarioHardware usuarioHardware) throws Exception {
		try{
			Agencia agencia = getAgenciaDAO().buscarPorId(usuarioHardware.getAgencia().getId().longValue());
			usuarioHardware.setAgencia(agencia);
			TreeMap<String, Object> criteriosBusqueda = new TreeMap<String, Object>();
			if(usuarioHardware.getAgencia().getTipoAgencia().getId().intValue()==Constantes.ID_TIPAGE_TEPSA){
				criteriosBusqueda.put("codigo", usuarioHardware.getCodigo());
				criteriosBusqueda.put("estadoRegistro", Constantes.VALUE_ACTIVO);
				List<?> resultcodigo = getUsuarioHardwareDAO().buscarPorX(criteriosBusqueda, null);
				/*Valida duplicidad del código*/
				for(int r = 0; r < resultcodigo.size(); r ++) {
					UsuarioHardware oUsuarioHardware = (UsuarioHardware) resultcodigo.get(r);
					if (oUsuarioHardware.getId().intValue()!=usuarioHardware.getId().intValue())
						throw new CodigoDuplicadoException();
				}
			}
			
			criteriosBusqueda = new TreeMap<String, Object>();
			criteriosBusqueda.put("descripcion", usuarioHardware.getDescripcion());
			criteriosBusqueda.put("codigo", usuarioHardware.getCodigo());
			criteriosBusqueda.put("estadoRegistro", Constantes.VALUE_ACTIVO);
			List<?> resultDescripcion = getUsuarioHardwareDAO().buscarPorX(criteriosBusqueda, null);
			/*Valida duplicidad de la Descripcion*/
			for(int r = 0; r < resultDescripcion.size(); r ++) {
				UsuarioHardware oUsuarioHardware = (UsuarioHardware) resultDescripcion.get(r);
					if (!(oUsuarioHardware.getId().equals(usuarioHardware.getId())))
						throw new UsuarioHardwareDuplicidadDescripcionException();
				}
			
			getUsuarioHardwareDAO().actualizar(usuarioHardware);
			
			Integer idAgenciaTranscar = getTitanDAO().buscarAgencia(agencia.getId());
			if(idAgenciaTranscar != null)
				usuarioHardware.getTitanUsuarioHardware().setIdAgencia(idAgenciaTranscar);
			else
				throw new AgenciaTranscarNullException();
//			usuarioHardware.getTitanUsuarioHardware().setIdUsuarioHardwareVyR(usuarioHardware.getId());
			getTitanDAO().actualizarUsuarioHardware(usuarioHardware.getTitanUsuarioHardware());
			
		
		}catch (CodigoDuplicadoException rsdex){
			throw new CodigoDuplicadoException();
		}catch (UsuarioHardwareDuplicidadDescripcionException uhddex){
			throw new UsuarioHardwareDuplicidadDescripcionException();
		}catch(AgenciaTranscarNullException atnex) {
			throw new AgenciaTranscarNullException();
		}catch(Exception ex){
			throw new Exception(ex);
		}
		
		
	}

	/*
	 * (non-Javadoc)
	 * @see com.tepsa.sisvyr.service.business.UsuarioHardwareManager#inactivar(java.lang.Long)
	 */
	@Override
	@Transactional
	public void inactivar(Long id) {
		getUsuarioHardwareDAO().inactivar(id);
		//Inactivamos el Usuario Hardware
		getTitanDAO().inactivarUsuarioHardware(id.intValue());		
	}

	/*
	 * (non-Javadoc)
	 * @see com.tepsa.sisvyr.service.business.UsuarioHardwareManager#BuscarXCodigo(java.lang.String)
	 */
	@Override
	public UsuarioHardware buscarXCodigo(String codigo) throws Exception {
		return getUsuarioHardwareDAO().buscarXCodigo(codigo);
	}
}
