/**
 * Proyecto		: SISVYR
 * Sistema		: Sistema de Ventas y Reservas
 * Descripción	: Implementacion de metodos que permiten el acceso al modelo.
 * Autor		: José Avalos
 * Fecha		: 27/09/2012
 */
package pe.itsb.vyrbus.service.business.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

import org.springframework.transaction.annotation.Transactional;

import pe.itsb.vyrbus.model.bean.Concesionario;
import pe.itsb.vyrbus.model.dao.ConcesionarioDAO;
import pe.itsb.vyrbus.service.business.ConcesionarioManager;
import pe.itsb.vyrbus.service.exceptions.RazonSocialDuplicadoException;
import pe.itsb.vyrbus.service.exceptions.RucDuplicadoException;
import pe.itsb.vyrbus.service.util.Constantes;

/**
 * @author Jose
 *
 */
public class ConcesionarioManagerImpl implements ConcesionarioManager {
	private ConcesionarioDAO concesionarioDAO;

	/**
	 * @return the concesionarioDAO
	 */
	public ConcesionarioDAO getConcesionarioDAO() {
		return concesionarioDAO;
	}
	/**
	 * @param concesionarioDAO the concesionarioDAO to set
	 */
	public void setConcesionarioDAO(ConcesionarioDAO concesionarioDAO) {
		this.concesionarioDAO = concesionarioDAO;
	}

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.service.business.ConcesionarioManager#buscarPorEstadoRegistro(java.lang.String, java.lang.String)
	 */
	@Override
	@Transactional(readOnly=true)
	public ArrayList<Concesionario> buscarPorEstadoRegistro(String estado, String criterioOrden) throws Exception {
		return getConcesionarioDAO().buscarPorEstadoRegistro(estado, criterioOrden);
	}

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.service.business.ConcesionarioManager#buscarPorX(java.util.TreeMap, java.util.List)
	 */
	@Override
	@Transactional(readOnly=true)
	public ArrayList<Concesionario> buscarPorX(TreeMap<String, Object> criteriosBusqueda, List<String> criteriosOrdenar) throws Exception {
		return getConcesionarioDAO().buscarPorX(criteriosBusqueda, criteriosOrdenar);
	}

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.service.business.ConcesionarioManager#buscarPorId(java.lang.Long)
	 */
	@Override
	@Transactional(readOnly=true)
	public Concesionario buscarPorId(Long id) throws Exception {
		return getConcesionarioDAO().buscarPorId(id);
	}

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.service.business.ConcesionarioManager#guardar(com.tepsa.sisvyr.model.bean.Concesionario)
	 */
	@Override
	@Transactional
	public int guardar(Concesionario concesionario) throws Exception {
		int result = Constantes.FAILURE;
		try{
			TreeMap<String, Object> criteriosBusqueda = new TreeMap<>();
			criteriosBusqueda.put("ruc", concesionario.getRuc());
			List<?> lstResult = getConcesionarioDAO().buscarPorX(criteriosBusqueda, null);
			/*Valida duplicidad de RUC*/
			if(lstResult.size()>0)
				throw new RucDuplicadoException();

			criteriosBusqueda.remove("ruc");
			criteriosBusqueda.put("razonSocial", concesionario.getRazonSocial());
			List<?> resultRS = getConcesionarioDAO().buscarPorX(criteriosBusqueda, null);
			/*Valida duplicidad de Razon Social*/
			if (resultRS.size()>0)
				throw new RazonSocialDuplicadoException();

			getConcesionarioDAO().guardar(concesionario);
			result = Constantes.CORRECT;
		}catch (RazonSocialDuplicadoException rsdex){
			throw new RazonSocialDuplicadoException();
		}catch(RucDuplicadoException rdnex){
			throw new RucDuplicadoException();
		}catch(Exception ex){
			throw new Exception(ex);
		}
		return result;
	}

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.service.business.ConcesionarioManager#actualizar(com.tepsa.sisvyr.model.bean.Concesionario)
	 */
	@Override
	@Transactional
	public void actualizar(Concesionario concesionario) throws Exception {
		try{
			TreeMap<String, Object> criteriosBusqueda = new TreeMap<>();
			criteriosBusqueda.put("ruc", concesionario.getRuc());
			criteriosBusqueda.put("estadoRegistro", Constantes.VALUE_ACTIVO);
			List<?> result = getConcesionarioDAO().buscarPorX(criteriosBusqueda, null);
			/*Valida duplicidad del Ruc*/
			for (Object element : result) {
				Concesionario oConcesionario = (Concesionario) element;
				if (oConcesionario.getId().intValue() != concesionario.getId().intValue())
					throw new RucDuplicadoException();
			}

			criteriosBusqueda.remove("ruc");
			criteriosBusqueda.put("razonSocial", concesionario.getRazonSocial());
			List<?> resultRS = getConcesionarioDAO().buscarPorX(criteriosBusqueda, null);
			/*Valida duplicidad de la Razón Social*/
			for (Object element : resultRS) {
				Concesionario oConcesionario = (Concesionario) element;
				if (oConcesionario.getId().intValue() != concesionario.getId().intValue())
					throw new RazonSocialDuplicadoException();
			}

			getConcesionarioDAO().actualizar(concesionario);

		}catch (RazonSocialDuplicadoException rsdex){
			throw new RazonSocialDuplicadoException();
		}catch(RucDuplicadoException rdnex){
			throw new RucDuplicadoException();
		}catch(Exception ex){
			throw new Exception(ex);
		}
	}

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.service.business.ConcesionarioManager#inactivar(java.lang.Long)
	 */
	@Override
	@Transactional
	public void inactivar(Long id) throws Exception {
		getConcesionarioDAO().inactivar(id);
	}
	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.service.business.ConcesionarioManager#buscarPorX(java.lang.String, java.lang.Object[], java.util.List, java.lang.String)
	 */
	@Override
	public List<Concesionario> buscarPorX(String campo, Object[] criterios,List<String> criteriosOrdenar, String estadoRegistro)throws Exception {
		// TODO Auto-generated method stub
		return getConcesionarioDAO().buscarPorX(campo, criterios, criteriosOrdenar, estadoRegistro);
	}

}
