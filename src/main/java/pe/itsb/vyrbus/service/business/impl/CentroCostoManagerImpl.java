/**
 * Proyecto		: SISVYR
 * Sistema		: Sistema de Ventas y Reservas
 * Descripción	: Clase donde implementamos las reglas del negocio para la tabla Centro de Costo VRMCENCOS.
 * Autor		: José Avalos
 * Fecha		: 16/09/2013
 */
package pe.itsb.vyrbus.service.business.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

import org.springframework.transaction.annotation.Transactional;

import pe.itsb.vyrbus.model.bean.CentroCosto;
import pe.itsb.vyrbus.model.dao.CentroCostoDAO;
import pe.itsb.vyrbus.service.business.CentroCostoManager;
import pe.itsb.vyrbus.service.util.Constantes;

/**
 * @author Jose
 *
 */
public class CentroCostoManagerImpl implements CentroCostoManager {
	private CentroCostoDAO centroCostoDAO;

	/**
	 * @return the centroCostoDAO
	 */
	public CentroCostoDAO getCentroCostoDAO() {
		return centroCostoDAO;
	}
	/**
	 * @param centroCostoDAO the centroCostoDAO to set
	 */
	public void setCentroCostoDAO(CentroCostoDAO centroCostoDAO) {
		this.centroCostoDAO = centroCostoDAO;
	}

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.service.business.CentroCostoManager#buscarPorEstadoRegistro(java.lang.String, java.lang.String)
	 */
	@Override
	public ArrayList<CentroCosto> buscarPorEstadoRegistro(String estado, String criterioOrden) throws Exception {
		// TODO Auto-generated method stub
		return getCentroCostoDAO().buscarPorEstadoRegistro(estado, criterioOrden);
	}

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.service.business.CentroCostoManager#buscarPorX(java.util.TreeMap, java.util.List)
	 */
	@Override
	public ArrayList<CentroCosto> buscarPorX(TreeMap<String, Object> criteriosBusqueda, List<String> criteriosOrdenar) throws Exception {
		return getCentroCostoDAO().buscarPorX(criteriosBusqueda, criteriosOrdenar);
	}

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.service.business.CentroCostoManager#buscarPorId(java.lang.Long)
	 */
	@Override
	public CentroCosto buscarPorId(Long id) throws Exception {
		// TODO Auto-generated method stub
		return getCentroCostoDAO().buscarPorId(id);
	}

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.service.business.CentroCostoManager#guardar(com.tepsa.sisvyr.model.bean.CentroCosto)
	 */
	@Override
	@Transactional
	public int guardar(CentroCosto centroCosto) throws Exception {
		int result = Constantes.FAILURE;
		getCentroCostoDAO().guardar(centroCosto);
		result = Constantes.CORRECT;
		return result;
	}

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.service.business.CentroCostoManager#actualizar(com.tepsa.sisvyr.model.bean.CentroCosto)
	 */
	@Override
	@Transactional
	public int actualizar(CentroCosto centroCosto) throws Exception {
		int result = Constantes.FAILURE;
		getCentroCostoDAO().actualizar(centroCosto);
		result = Constantes.CORRECT;
		return result;
	}

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.service.business.CentroCostoManager#inactivar(java.lang.Long)
	 */
	@Override
	@Transactional
	public void inactivar(Long id) throws Exception {
		getCentroCostoDAO().inactivar(id);
	}
}
