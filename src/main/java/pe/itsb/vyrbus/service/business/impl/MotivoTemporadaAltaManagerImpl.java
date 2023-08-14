package pe.itsb.vyrbus.service.business.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

import org.springframework.transaction.annotation.Transactional;

import pe.itsb.vyrbus.model.bean.MotivoTemporadaAlta;
import pe.itsb.vyrbus.model.dao.MotivoTemporadaAltaDAO;
import pe.itsb.vyrbus.service.business.MotivoTemporadaAltaManager;
import pe.itsb.vyrbus.service.exceptions.DenominacionDuplicadaException;
import pe.itsb.vyrbus.service.util.Constantes;

/**
 *
 * @author JABANTO
 *
 */
public class MotivoTemporadaAltaManagerImpl implements MotivoTemporadaAltaManager  {

	private MotivoTemporadaAltaDAO motivoTemporadaAltaDAO;
	/**
	 * @return the motivoTemporadaAltaDAO
	 */
	public MotivoTemporadaAltaDAO getMotivoTemporadaAltaDAO() {
		return motivoTemporadaAltaDAO;
	}

	/**
	 * @param motivoTemporadaAltaDAO the motivoTemporadaAltaDAO to set
	 */
	public void setMotivoTemporadaAltaDAO(MotivoTemporadaAltaDAO motivoTemporadaAltaDAO) {
		this.motivoTemporadaAltaDAO = motivoTemporadaAltaDAO;
	}


	/*
	 * (non-Javadoc)
	 * @see com.tepsa.sisvyr.service.business.MotivoTemporadaAltaManager#buscarPorEstadoRegistro(java.lang.String, java.lang.String)
	 */
	@Override
	public ArrayList<MotivoTemporadaAlta> buscarPorEstadoRegistro(String estado, String criterioOrden) {
		return getMotivoTemporadaAltaDAO().buscarPorEstadoRegistro(estado, criterioOrden);
	}

	/*
	 * (non-Javadoc)
	 * @see com.tepsa.sisvyr.service.business.MotivoTemporadaAltaManager#buscarPorX(java.util.TreeMap, java.util.List)
	 */
	@Override
	public ArrayList<MotivoTemporadaAlta> buscarPorX(TreeMap<String, Object> criteriosBusqueda,List<String> criteriosOrdenar) {
		return getMotivoTemporadaAltaDAO().buscarPorX(criteriosBusqueda, criteriosOrdenar);
	}

	/*
	 * (non-Javadoc)
	 * @see com.tepsa.sisvyr.service.business.MotivoTemporadaAltaManager#buscarPorId(java.lang.Long)
	 */
	@Override
	public MotivoTemporadaAlta buscarPorId(Long id) {
		return getMotivoTemporadaAltaDAO().buscarPorId(id);
	}

	/*
	 * (non-Javadoc)
	 * @see com.tepsa.sisvyr.service.business.MotivoTemporadaAltaManager#guardar(com.tepsa.sisvyr.model.bean.MotivoTemporadaAlta)
	 */
	@Transactional
	@Override
	public void guardar(MotivoTemporadaAlta motivoTemporadaAlta) throws Exception {
		try{
			/*Valida duplicidad de la denominación*/
			TreeMap<String, Object> criteriosBusqueda = new TreeMap<>();
			criteriosBusqueda.put("nombreMotivo", motivoTemporadaAlta.getNombreMotivo());
			criteriosBusqueda.put("estadoRegistro", Constantes.VALUE_ACTIVO);
			List<?> resul = getMotivoTemporadaAltaDAO().buscarPorX(criteriosBusqueda, null);
			if(resul.size()>0)
				throw new DenominacionDuplicadaException();

			getMotivoTemporadaAltaDAO().guardar(motivoTemporadaAlta);

		}catch (DenominacionDuplicadaException ddex){
			throw new DenominacionDuplicadaException();
		}

	}

	/*
	 * (non-Javadoc)
	 * @see com.tepsa.sisvyr.service.business.MotivoTemporadaAltaManager#actualizar(com.tepsa.sisvyr.model.bean.MotivoTemporadaAlta)
	 */
	@Transactional
	@Override
	public void actualizar(MotivoTemporadaAlta motivoTemporadaAlta) throws Exception {
		try{
			/*Valida duplicidad de la denominación*/
			TreeMap<String, Object> criteriosBusqueda = new TreeMap<>();
			criteriosBusqueda.put("nombreMotivo", motivoTemporadaAlta.getNombreMotivo());
			criteriosBusqueda.put("estadoRegistro", Constantes.VALUE_ACTIVO);
			List<?> resul = getMotivoTemporadaAltaDAO().buscarPorX(criteriosBusqueda, null);
			for (Object element : resul) {
				MotivoTemporadaAlta oAlta = (MotivoTemporadaAlta) element;
					if (!(oAlta.getId().equals(motivoTemporadaAlta.getId())) )
						throw new DenominacionDuplicadaException();
			}
			getMotivoTemporadaAltaDAO().actualizar(motivoTemporadaAlta);

		}catch(DenominacionDuplicadaException ddex){
			throw new DenominacionDuplicadaException();
		}

	}

	/*
	 * (non-Javadoc)
	 * @see com.tepsa.sisvyr.service.business.MotivoTemporadaAltaManager#inactivar(java.lang.Long)
	 */
	@Transactional
	@Override
	public void inactivar(Long id) {
		getMotivoTemporadaAltaDAO().inactivar(id);
	}




}
