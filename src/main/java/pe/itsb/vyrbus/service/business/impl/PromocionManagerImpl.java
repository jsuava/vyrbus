/**
 * Proyecto		: SISVYR
 * Sistema		: Sistema de Ventas y Reservas
 * Descripción	:
 * Autor		: José Avalos
 * Fecha		: 18/04/2013
 */
package pe.itsb.vyrbus.service.business.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

import org.springframework.transaction.annotation.Transactional;

import pe.itsb.vyrbus.model.bean.Promocion;
import pe.itsb.vyrbus.model.dao.PromocionDAO;
import pe.itsb.vyrbus.service.business.PromocionManager;
import pe.itsb.vyrbus.service.exceptions.PromocionExcepcion;
import pe.itsb.vyrbus.service.util.Constantes;

/**
 * @author Jose
 *
 */
public class PromocionManagerImpl implements PromocionManager {
	private PromocionDAO promocionDAO;

	/**
	 * @return the promocionDAO
	 */
	public PromocionDAO getPromocionDAO() {
		return promocionDAO;
	}
	/**
	 * @param promocionDAO the promocionDAO to set
	 */
	public void setPromocionDAO(PromocionDAO promocionDAO) {
		this.promocionDAO = promocionDAO;
	}

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.service.business.PromocionManager#buscarporId(java.lang.Long)
	 */
	@Override
	public Promocion buscarPorId(Long id) throws Exception {
		return getPromocionDAO().buscarPorId(id);
	}
	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.service.business.PromocionManager#guardarPromocion(com.tepsa.sisvyr.model.bean.Promocion)
	 */
	@Override
	@Transactional
	public int guardarPromocion(Promocion promocion) throws Exception {
		int result = Constantes.FAILURE;
		try{
			TreeMap<String, Object> criteriosBusqueda = new TreeMap<>();
			criteriosBusqueda.put("denominacion", promocion.getDenominacion());
			criteriosBusqueda.put("estadoRegistro", Constantes.VALUE_ACTIVO);
			List<?> lstDenominacion = getPromocionDAO().buscarPorX(criteriosBusqueda, null);
			if(lstDenominacion.size()>0)
				throw new PromocionExcepcion();

//			if(promocion.getId()==null)
				getPromocionDAO().save(promocion);
//			else
//				getPromocionDAO().update(promocion);
			result = Constantes.CORRECT;
		}catch(PromocionExcepcion pex){
			throw new PromocionExcepcion(PromocionExcepcion.DENOMINACION_DUPLICADA);
		}catch(Exception ex){
			throw new Exception();
		}
		return result;
	}

	/*
	 * (non-Javadoc)
	 * @see com.tepsa.sisvyr.service.business.PromocionManager#guardarPromocion(java.util.List)
	 */
	@Override
	@Transactional
	public int guardarPromocion(List<Promocion> lstPromocion) throws Exception {
		int result = Constantes.FAILURE;
		try{
			for(Promocion promocion : lstPromocion){
				if(promocion.getId()==null)
					getPromocionDAO().save(promocion);
				else
					getPromocionDAO().update(promocion);
			}
			result = Constantes.CORRECT;
		}catch(Exception ex){
			throw new Exception();
		}
		return result;
	}

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.service.business.PromocionManager#buscarPromociones(java.lang.String)
	 */
	@Override
	public ArrayList<Promocion> buscarPorEstadoRegistro(String estado, String criterioOrden) throws Exception {
		return getPromocionDAO().buscarPorEstadoRegistro(estado, criterioOrden);
	}
	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.service.business.PromocionManager#buscarPorX(java.util.TreeMap, java.util.List)
	 */
	@Override
	public ArrayList<Promocion> buscarPorX(TreeMap<String, Object> criteriosBusqueda, List<String> criteriosOrdenar)throws Exception {
		return getPromocionDAO().buscarPorX(criteriosBusqueda, criteriosOrdenar);
	}
	/*
	 * (non-Javadoc)
	 * @see com.tepsa.sisvyr.service.business.PromocionManager#buscarPorX(java.util.TreeMap, java.util.List, java.util.Date)
	 */
	@Override
	public ArrayList<Promocion> buscarPorX(TreeMap<String, Object> criteriosBusqueda, List<String> criteriosOrdenar, String fechaPartida)throws Exception{
		return getPromocionDAO().buscarPorX(criteriosBusqueda, criteriosOrdenar, fechaPartida);
	}
	/*
	 * (non-Javadoc)
	 * @see com.tepsa.sisvyr.service.business.PromocionManager#buscarPromocionesAplicables(boolean, java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	public List<Promocion> buscarPromocionesAplicables(boolean paxfre, String idCliente, String estadoRegistro, String fechaPartida) throws Exception {
		return getPromocionDAO().buscarPromocionesAplicables(paxfre, idCliente, estadoRegistro, fechaPartida);
	}

	/*
	 * (non-Javadoc)
	 * @see com.tepsa.sisvyr.service.business.PromocionManager#buscarPorTarifa(java.lang.String, java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	public List<Promocion> buscarPorTarifa(String fechaPartida, String ruta, String servicio, String horaPartida)throws Exception {
		// TODO Auto-generated method stub
		return getPromocionDAO().buscarPorTarifa(fechaPartida, ruta, servicio,horaPartida);
	}
	/*
	 * (non-Javadoc)
	 * @see com.tepsa.sisvyr.service.business.PromocionManager#buscarPorCriterios(java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	public ArrayList<Promocion> buscarPorCriterios(String denominacion, String ruta, String servicio, String cliente, String tipoDescuento, String criterioOrden)throws Exception{
		return getPromocionDAO().buscarPorCriterios(denominacion, ruta, servicio, cliente, tipoDescuento, criterioOrden);
	}
	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.service.business.PromocionManager#actualizar(com.tepsa.sisvyr.model.bean.Promocion)
	 */
	@Override
	@Transactional
	public void actualizar(Promocion promocion) throws Exception {
		// TODO Auto-generated method stub
		getPromocionDAO().update(promocion);
	}
}
