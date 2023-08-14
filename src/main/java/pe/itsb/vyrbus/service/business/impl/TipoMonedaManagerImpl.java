/**
 * Proyecto		: SISVYR
 * Sistema		: Sistema de Ventas y Reservas
 * Descripción	:
 * Autor		: José Abanto
 * Fecha		: 07/08/2015
 * Hora			: 12:24:26
 */
package pe.itsb.vyrbus.service.business.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

import org.springframework.transaction.annotation.Transactional;

import pe.itsb.vyrbus.model.bean.TipoMoneda;
import pe.itsb.vyrbus.model.dao.TipoMonedaDAO;
import pe.itsb.vyrbus.service.business.TipoMonedaManager;

/**
 * @author jabanto
 *
 */
public class TipoMonedaManagerImpl implements TipoMonedaManager{
	private TipoMonedaDAO tipoMonedaDAO;

	/**
	 * @return the tipoMonedaDAO
	 */
	public TipoMonedaDAO getTipoMonedaDAO() {
		return tipoMonedaDAO;
	}

	/**
	 * @param tipoMonedaDAO the tipoMonedaDAO to set
	 */
	public void setTipoMonedaDAO(TipoMonedaDAO tipoMonedaDAO) {
		this.tipoMonedaDAO = tipoMonedaDAO;
	}

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.service.business.TipoMonedaManager#buscarPorEstadoRegistro(java.lang.String, java.lang.String)
	 */
	@Override
	public ArrayList<TipoMoneda> buscarPorEstadoRegistro(String estado,String criterioOrden)throws Exception {
		// TODO Auto-generated method stub
		return getTipoMonedaDAO().buscarPorEstadoRegistro(estado, criterioOrden);
	}

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.service.business.TipoMonedaManager#buscarPorX(java.util.TreeMap, java.util.List)
	 */
	@Override
	public ArrayList<TipoMoneda> buscarPorX(TreeMap<String, Object> criteriosBusqueda,List<String> criteriosOrdenar)throws Exception {
		// TODO Auto-generated method stub
		return getTipoMonedaDAO().buscarPorX(criteriosBusqueda, criteriosOrdenar);
	}

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.service.business.TipoMonedaManager#buscarPorId(java.lang.Long)
	 */
	@Override
	public TipoMoneda buscarPorId(Long id)throws Exception {
		// TODO Auto-generated method stub
		return getTipoMonedaDAO().buscarPorId(id);
	}

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.service.business.TipoMonedaManager#guardar(com.tepsa.sisvyr.model.bean.TipoMoneda)
	 */
	@Transactional
	@Override
	public void guardar(TipoMoneda tipoMoneda)throws Exception {
		getTipoMonedaDAO().guardar(tipoMoneda);
	}

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.service.business.TipoMonedaManager#actualizar(com.tepsa.sisvyr.model.bean.TipoMoneda)
	 */
	@Transactional
	@Override
	public void actualizar(TipoMoneda tipoMoneda)throws Exception {
		// TODO Auto-generated method stub
		getTipoMonedaDAO().actualizar(tipoMoneda);
	}


}
