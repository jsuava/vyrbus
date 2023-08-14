package pe.itsb.vyrbus.service.business.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

import org.springframework.transaction.annotation.Transactional;

import pe.itsb.vyrbus.model.bean.TipoCobranza;
import pe.itsb.vyrbus.model.dao.TipoCobranzaDAO;
import pe.itsb.vyrbus.service.business.TipoCobranzaManager;
import pe.itsb.vyrbus.service.exceptions.DenominacionDuplicadaException;
import pe.itsb.vyrbus.service.util.Constantes;

/**
 *
 * @author JABANTO
 *
 */
public class TipoCobranzaManagerImpl implements TipoCobranzaManager {
	private TipoCobranzaDAO tipoCobranzaDAO;

	public TipoCobranzaDAO getTipoCobranzaDAO() {
		return tipoCobranzaDAO;
	}

	public void setTipoCobranzaDAO(TipoCobranzaDAO tipoCobranzaDAO) {
		this.tipoCobranzaDAO = tipoCobranzaDAO;
	}

	/*
	 * (non-Javadoc)
	 * @see com.tepsa.sisvyr.service.business.TipoCobranzaManager#buscarPorEstadoRegistro(java.lang.String, java.lang.String)
	 */
	@Override
	public ArrayList<TipoCobranza> buscarPorEstadoRegistro(String estado,String criterioOrden) {
		return getTipoCobranzaDAO().buscarPorEstadoRegistro(estado, criterioOrden);
	}

	/*
	 * (non-Javadoc)
	 * @see com.tepsa.sisvyr.service.business.TipoCobranzaManager#buscarPorX(java.util.TreeMap, java.util.List)
	 */
	@Override
	public ArrayList<TipoCobranza> buscarPorX(TreeMap<String, Object> criteriosBusqueda,List<String> criteriosOrdenar) {
		return getTipoCobranzaDAO().buscarPorX(criteriosBusqueda, criteriosOrdenar);
	}

	/*
	 * (non-Javadoc)
	 * @see com.tepsa.sisvyr.service.business.TipoCobranzaManager#buscarPorId(java.lang.Long)
	 */
	@Override
	public TipoCobranza buscarPorId(Long id) {
		return getTipoCobranzaDAO().buscarPorId(id);
	}

	/*
	 * (non-Javadoc)
	 * @see com.tepsa.sisvyr.service.business.TipoCobranzaManager#guardar(com.tepsa.sisvyr.model.bean.TipoCobranza)
	 */
	@Override
	@Transactional
	public void guardar(TipoCobranza tipoCobranza) throws Exception {
		try{
			TreeMap<String, Object> criteriosBusqueda = new TreeMap<>();
			criteriosBusqueda.put("denominacion", tipoCobranza.getDenominacion());
			criteriosBusqueda.put("estadoRegistro", Constantes.VALUE_ACTIVO);
			List<?> resultDenominacion = getTipoCobranzaDAO().buscarPorX(criteriosBusqueda, null);
			/*Valida duplicidad de la denominación*/
			if(resultDenominacion.size()>0)
				throw new DenominacionDuplicadaException();

			getTipoCobranzaDAO().guardar(tipoCobranza);

		}catch (DenominacionDuplicadaException rsdex){
			throw new DenominacionDuplicadaException();
		}catch (Exception ex) {
			throw new Exception(ex);
		}

	}

	/*
	 * (non-Javadoc)
	 * @see com.tepsa.sisvyr.service.business.TipoCobranzaManager#actualizar(com.tepsa.sisvyr.model.bean.TipoCobranza)
	 */
	@Override
	@Transactional
	public void actualizar(TipoCobranza tipoCobranza) throws Exception {
		try{
			TreeMap<String, Object> criteriosBusqueda = new TreeMap<>();
			criteriosBusqueda.put("denominacion", tipoCobranza.getDenominacion());
			criteriosBusqueda.put("estadoRegistro", Constantes.VALUE_ACTIVO);
			List<?> resultDenominacion = getTipoCobranzaDAO().buscarPorX(criteriosBusqueda, null);
			/*Valida duplicidad de la denominación*/
			//Valida duplicidad del Nombre corto
			for (Object element : resultDenominacion) {
				TipoCobranza otiTipoCobranza = (TipoCobranza) element;
					if (!(otiTipoCobranza.getId() == tipoCobranza.getId()))
						throw new DenominacionDuplicadaException();
				}

			getTipoCobranzaDAO().actualizar(tipoCobranza);

		}catch (DenominacionDuplicadaException rsdex){
			throw new DenominacionDuplicadaException();
		}catch (Exception ex) {
			throw new Exception(ex);
		}

	}

	/*
	 * (non-Javadoc)
	 * @see com.tepsa.sisvyr.service.business.TipoCobranzaManager#inactivar(java.lang.Long)
	 */
	@Override
	@Transactional
	public void inactivar(Long id) {
		getTipoCobranzaDAO().inactivar(id);
	}


}
