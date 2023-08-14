package pe.itsb.vyrbus.service.business.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

import org.springframework.transaction.annotation.Transactional;

import pe.itsb.vyrbus.model.bean.Rol;
import pe.itsb.vyrbus.model.dao.RolDAO;
import pe.itsb.vyrbus.service.business.RolManager;
import pe.itsb.vyrbus.service.exceptions.DenominacionDuplicadaException;
import pe.itsb.vyrbus.service.util.Constantes;

public class RolManagerImpl implements RolManager {
	private RolDAO rolDAO;

	/**
	 *  the RolDAO
	 * @return
	 */
	public RolDAO getRolDAO() {
		return rolDAO;
	}


	/**
	 *
	 * @param rol
	 */
	public void setRolDAO(RolDAO rolDAO){
		this.rolDAO=rolDAO;
	}

	/*
	 * (non-Javadoc)
	 * @see com.tepsa.sisvyr.service.business.RolManager#buscarPorEstadoRegistro(java.lang.String, java.lang.String)
	 */
	@Override
	public ArrayList<Rol> buscarPorEstadoRegistro(String estado,String criterioOrden) {
		return getRolDAO().buscarPorEstadoRegistro(estado, criterioOrden);
	}

	/*
	 * (non-Javadoc)
	 * @see com.tepsa.sisvyr.service.business.RolManager#buscarPorX(java.util.TreeMap, java.util.List)
	 */
	@Override
	public ArrayList<Rol> buscarPorX(TreeMap<String, Object> criteriosBusqueda,List<String> criteriosOrdenar) {
		return getRolDAO().buscarPorX(criteriosBusqueda, criteriosOrdenar);
	}

	/*
	 * (non-Javadoc)
	 * @see com.tepsa.sisvyr.service.business.RolManager#buscarPorId(java.lang.Long)
	 */
	@Override
	public Rol buscarPorId(Long id) {
		return getRolDAO().buscarPorId(id);
	}

	/*
	 * (non-Javadoc)
	 * @see com.tepsa.sisvyr.service.business.RolManager#guardar(com.tepsa.sisvyr.model.bean.Rol)
	 */
	@Override
	@Transactional
	public void guardar(Rol rol) throws Exception {
		try{
			TreeMap<String, Object> criteriosBusqueda = new TreeMap<>();
			criteriosBusqueda.put("denominacion", rol.getDenominacion());
			criteriosBusqueda.put("estadoRegistro", Constantes.VALUE_ACTIVO);
			List<?> resultDenominacion = getRolDAO().buscarPorX(criteriosBusqueda, null);

			/*Valida duplicidad de la denominación*/
			if(resultDenominacion.size()>0)
				throw new DenominacionDuplicadaException();

			getRolDAO().guardar(rol);

		}catch (DenominacionDuplicadaException ddex){
			throw new DenominacionDuplicadaException();
		}catch(Exception ex){
			throw new Exception(ex);
		}

	}

	/*
	 * (non-Javadoc)
	 * @see com.tepsa.sisvyr.service.business.RolManager#actualizar(com.tepsa.sisvyr.model.bean.Rol)
	 */
	@Override
	@Transactional
	public void actualizar(Rol rol) throws Exception{
		try{
			TreeMap<String, Object> criteriosBusqueda = new TreeMap<>();
			criteriosBusqueda.put("denominacion", rol.getDenominacion());
			criteriosBusqueda.put("estadoRegistro", Constantes.VALUE_ACTIVO);
			List<?> resultDenominacion = getRolDAO().buscarPorX(criteriosBusqueda, null);

			/*Valida duplicidad de la denominación*/
			for (Object element : resultDenominacion) {
				Rol orol = (Rol) element;
					if (!(orol.getId() == rol.getId()))
						throw new DenominacionDuplicadaException();
				}
			getRolDAO().actualizar(rol);

		}catch (DenominacionDuplicadaException ddex){
		}

	}

	/*
	 * (non-Javadoc)
	 * @see com.tepsa.sisvyr.service.business.RolManager#inactivar(java.lang.Long)
	 */
	@Override
	@Transactional
	public void inactivar(Long id) {
		getRolDAO().inactivar(id);
	}

}
