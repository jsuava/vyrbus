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

import pe.itsb.vyrbus.model.bean.OperadorTarjetaCredito;
import pe.itsb.vyrbus.model.dao.OperadorTarjetaCreditoDAO;
import pe.itsb.vyrbus.service.business.OperadorTarjetaCreditoManager;
import pe.itsb.vyrbus.service.exceptions.DenominacionDuplicadaException;
import pe.itsb.vyrbus.service.util.Constantes;

/**
 * @author Jose
 *
 */
public class OperadorTarjetaCreditoManagerImpl implements OperadorTarjetaCreditoManager {
	private OperadorTarjetaCreditoDAO operadorTarjetaCreditoDAO;

	/**
	 * @return the operadorTarjetaCreditoDAO
	 */
	public OperadorTarjetaCreditoDAO getOperadorTarjetaCreditoDAO() {
		return operadorTarjetaCreditoDAO;
	}
	/**
	 * @param operadorTarjetaCreditoDAO the operadorTarjetaCreditoDAO to set
	 */
	public void setOperadorTarjetaCreditoDAO(OperadorTarjetaCreditoDAO operadorTarjetaCreditoDAO) {
		this.operadorTarjetaCreditoDAO = operadorTarjetaCreditoDAO;
	}

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.service.business.OperadorTarjetaCreditoManager#buscarPorEstadoRegistro(java.lang.String, java.lang.String)
	 */
	@Override
	@Transactional(readOnly=true)
	public ArrayList<OperadorTarjetaCredito> buscarPorEstadoRegistro(String estado, String criterioOrden) throws Exception {
		return getOperadorTarjetaCreditoDAO().buscarPorEstadoRegistro(estado, criterioOrden);
	}

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.service.business.OperadorTarjetaCreditoManager#buscarPorX(java.util.TreeMap, java.util.List)
	 */
	@Override
	@Transactional(readOnly=true)
	public ArrayList<OperadorTarjetaCredito> buscarPorX(TreeMap<String, Object> criteriosBusqueda, List<String> criteriosOrdenar) throws Exception {
		return getOperadorTarjetaCreditoDAO().buscarPorX(criteriosBusqueda, criteriosOrdenar);
	}

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.service.business.OperadorTarjetaCreditoManager#buscarPorId(java.lang.Long)
	 */
	@Override
	@Transactional(readOnly=true)
	public OperadorTarjetaCredito buscarPorId(Long id) throws Exception {
		return getOperadorTarjetaCreditoDAO().buscarPorId(id);
	}

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.service.business.OperadorTarjetaCreditoManager#guardar(com.tepsa.sisvyr.model.bean.OperadorTarjetaCredito)
	 */
	@Override
	@Transactional
	public void guardar(OperadorTarjetaCredito operadorTarjetaCredito) throws Exception {
		try{
			TreeMap<String, Object> criteriosBusqueda = new TreeMap<>();
			criteriosBusqueda.put("denominacion", operadorTarjetaCredito.getDenominacion());
			criteriosBusqueda.put("estadoRegistro", Constantes.VALUE_ACTIVO);
			List<?> resultDenominacion = getOperadorTarjetaCreditoDAO().buscarPorX(criteriosBusqueda, null);
			/*Valida duplicidad de la denominación*/
			if(resultDenominacion.size()>0)
				throw new DenominacionDuplicadaException();

			getOperadorTarjetaCreditoDAO().guardar(operadorTarjetaCredito);

		}catch (DenominacionDuplicadaException rsdex){
			throw new DenominacionDuplicadaException();
		}catch(Exception ex){
			throw new Exception(ex);
		}
	}

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.service.business.OperadorTarjetaCreditoManager#actualizar(com.tepsa.sisvyr.model.bean.OperadorTarjetaCredito)
	 */
	@Override
	@Transactional
	public void actualizar(OperadorTarjetaCredito operadorTarjetaCredito) throws Exception {
		try{
			TreeMap<String, Object> criteriosBusqueda = new TreeMap<>();
			criteriosBusqueda.put("denominacion", operadorTarjetaCredito.getDenominacion());
			criteriosBusqueda.put("estadoRegistro", Constantes.VALUE_ACTIVO);
			List<?> resultDenominacion = getOperadorTarjetaCreditoDAO().buscarPorX(criteriosBusqueda, null);

			/*Valida duplicidad de la denominación*/
			for (Object element : resultDenominacion) {
				OperadorTarjetaCredito ooperadorTarjetaCredito = (OperadorTarjetaCredito) element;
					if (!(ooperadorTarjetaCredito.getId() == operadorTarjetaCredito.getId()))
						throw new DenominacionDuplicadaException();
				}

			getOperadorTarjetaCreditoDAO().actualizar(operadorTarjetaCredito);

		}catch (DenominacionDuplicadaException rsdex){
			throw new DenominacionDuplicadaException();
		}catch(Exception ex){
			throw new Exception(ex);
		}
	}

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.service.business.OperadorTarjetaCreditoManager#inactivar(java.lang.Long)
	 */
	@Override
	@Transactional
	public void inactivar(Long id) throws Exception {
		getOperadorTarjetaCreditoDAO().inactivar(id);
	}

}
