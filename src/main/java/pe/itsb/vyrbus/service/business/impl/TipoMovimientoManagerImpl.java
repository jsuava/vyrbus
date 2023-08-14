/**
 * Proyecto		: SISVYR
 * Sistema		: Sistema de Ventas y Reservas
 * Descripción	: Implementacion de metodos que permiten el acceso al modelo.
 * Autor		: José Avalos
 * Fecha		: 28/09/2012
 */
package pe.itsb.vyrbus.service.business.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

import org.springframework.transaction.annotation.Transactional;

import pe.itsb.vyrbus.model.bean.TipoMovimiento;
import pe.itsb.vyrbus.model.dao.TipoMovimientoDAO;
import pe.itsb.vyrbus.service.business.TipoMovimientoManager;
import pe.itsb.vyrbus.service.exceptions.DenominacionDuplicadaException;
import pe.itsb.vyrbus.service.util.Constantes;

/**
 * @author Jose
 *
 */
public class TipoMovimientoManagerImpl implements TipoMovimientoManager {
	private TipoMovimientoDAO tipoMovimientoVentaDAO;

	/**
	 * @return the condicionVentaDAO
	 */
	public TipoMovimientoDAO getTipoMovimientoDAO() {
		return tipoMovimientoVentaDAO;
	}
	/**
	 * @param tipoMovimientoDAO the condicionVentaDAO to set
	 */
	public void setTipoMovimientoDAO(TipoMovimientoDAO tipoMovimientoDAO) {
		this.tipoMovimientoVentaDAO = tipoMovimientoDAO;
	}

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.service.business.CondicionVentaManager#buscarPorEstadoRegistro(java.lang.String, java.lang.String)
	 */
	@Override
	@Transactional(readOnly=true)
	public ArrayList<TipoMovimiento> buscarPorEstadoRegistro(String estado, String criterioOrden) throws Exception {
		return getTipoMovimientoDAO().buscarPorEstadoRegistro(estado, criterioOrden);
	}

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.service.business.CondicionVentaManager#buscarPorX(java.util.TreeMap, java.util.List)
	 */
	@Override
	@Transactional(readOnly=true)
	public ArrayList<TipoMovimiento> buscarPorX(TreeMap<String, Object> criteriosBusqueda, List<String> criteriosOrdenar) throws Exception {
		return getTipoMovimientoDAO().buscarPorX(criteriosBusqueda, criteriosOrdenar);
	}

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.service.business.CondicionVentaManager#buscarPorId(java.lang.Long)
	 */
	@Override
	@Transactional(readOnly=true)
	public TipoMovimiento buscarPorId(Long id) throws Exception {
		return getTipoMovimientoDAO().buscarPorId(id);
	}

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.service.business.CondicionVentaManager#guardar(com.tepsa.sisvyr.model.bean.CondicionVenta)
	 */
	@Override
	@Transactional
	public void guardar(TipoMovimiento tipoMovimiento) throws Exception {
		try{
			TreeMap<String, Object> criteriosBusqueda = new TreeMap<>();
			criteriosBusqueda.put("denominacion", tipoMovimiento.getDenominacion());
			criteriosBusqueda.put("estadoRegistro", Constantes.VALUE_ACTIVO);
			List<?> result = getTipoMovimientoDAO().buscarPorX(criteriosBusqueda, null);
			/*Valida duplicidad de una Ruta*/
			if(result.size()>0)
				throw new DenominacionDuplicadaException();

			getTipoMovimientoDAO().guardar(tipoMovimiento);

		}catch (DenominacionDuplicadaException rsdex){
			throw new DenominacionDuplicadaException();
		}catch(Exception ex){
			throw new Exception(ex);
		}
	}

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.service.business.CondicionVentaManager#actualizar(com.tepsa.sisvyr.model.bean.CondicionVenta)
	 */
	@Override
	@Transactional
	public void actualizar(TipoMovimiento tipoMovimiento) throws Exception {
		try{
			TreeMap<String, Object> criteriosBusqueda = new TreeMap<>();
			criteriosBusqueda.put("denominacion", tipoMovimiento.getDenominacion());
			criteriosBusqueda.put("estadoRegistro", Constantes.VALUE_ACTIVO);
			List<?> resultDenominacion = getTipoMovimientoDAO().buscarPorX(criteriosBusqueda, null);
			/*Valida duplicidad de la denominación*/
			for (Object element : resultDenominacion) {
				TipoMovimiento ocondicionVenta = (TipoMovimiento) element;
					if (!(ocondicionVenta.getId() == tipoMovimiento.getId()))
						throw new DenominacionDuplicadaException();
				}

			getTipoMovimientoDAO().actualizar(tipoMovimiento);

		}catch (DenominacionDuplicadaException rsdex){
			throw new DenominacionDuplicadaException();
		}catch(Exception ex){
			throw new Exception(ex);
		}
	}

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.service.business.CondicionVentaManager#inactivar(java.lang.Long)
	 */
	@Override
	@Transactional
	public void inactivar(Long id) throws Exception {
		getTipoMovimientoDAO().inactivar(id);
	}
	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.service.business.TipoMovimientoManager#buscarPorX(java.lang.String, java.lang.Object[], java.util.List, java.lang.String)
	 */
	@Override
	public List<TipoMovimiento> buscarPorX(String campo, Object[] criterios,List<String> criteriosOrdenar, String estadoRegistro)throws Exception {
		// TODO Auto-generated method stub
		return getTipoMovimientoDAO().buscarPorX(campo, criterios, criteriosOrdenar, estadoRegistro);
	}

}
