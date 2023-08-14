/**
 * Proyecto		: SISVYR
 * Sistema		: Sistema de Ventas y Reservas
 * Descripción	:
 * Autor		: José Abanto
 * Fecha		: 07/08/2015
 * Hora			: 12:32:22
 */
package pe.itsb.vyrbus.service.business.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

import org.springframework.dao.DuplicateKeyException;
import org.springframework.transaction.annotation.Transactional;

import pe.itsb.vyrbus.model.bean.TipoCambio;
import pe.itsb.vyrbus.model.dao.TipoCambioDAO;
import pe.itsb.vyrbus.service.business.TipoCambioManager;
import pe.itsb.vyrbus.service.util.Constantes;
import pe.itsb.vyrbus.service.util.Messages;

/**
 * @author jabanto
 *
 */
public class TipoCambioManagerImpl implements TipoCambioManager{
	private TipoCambioDAO tipoCambioDAO;

	/**
	 * @return the tipoCambioDAO
	 */
	public TipoCambioDAO getTipoCambioDAO() {
		return tipoCambioDAO;
	}

	/**
	 * @param tipoCambioDAO the tipoCambioDAO to set
	 */
	public void setTipoCambioDAO(TipoCambioDAO tipoCambioDAO) {
		this.tipoCambioDAO = tipoCambioDAO;
	}

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.service.business.TipoCambioManager#buscarPorEstadoRegistro(java.lang.String, java.lang.String)
	 */
	@Override
	public ArrayList<TipoCambio> buscarPorEstadoRegistro(String estado,String criterioOrden)throws Exception {
		// TODO Auto-generated method stub
		return getTipoCambioDAO().buscarPorEstadoRegistro(estado, criterioOrden);
	}

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.service.business.TipoCambioManager#buscarPorX(java.util.TreeMap, java.util.List)
	 */
	@Override
	public ArrayList<TipoCambio> buscarPorX(TreeMap<String, Object> criteriosBusqueda,List<String> criteriosOrdenar)throws Exception {
		// TODO Auto-generated method stub
		return getTipoCambioDAO().buscarPorX(criteriosBusqueda, criteriosOrdenar);
	}

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.service.business.TipoCambioManager#buscarPorId(java.lang.Long)
	 */
	@Override
	public TipoCambio buscarPorId(Long id) throws Exception{
		// TODO Auto-generated method stub
		return getTipoCambioDAO().buscarPorId(id);
	}

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.service.business.TipoCambioManager#guardar(com.tepsa.sisvyr.model.bean.TipoCambio)
	 */
	@Transactional
	@Override
	public void guardar(TipoCambio tipoCambio)throws Exception {
		TreeMap<String, Object>criteriosBusqueda=new TreeMap<>();
		criteriosBusqueda.put("fecha", tipoCambio.getFecha());
		criteriosBusqueda.put("tipoMoneda", tipoCambio.getTipoMoneda());
		criteriosBusqueda.put("estadoRegistro", Constantes.VALUE_ACTIVO);

		List<TipoCambio>result=buscarPorX(criteriosBusqueda, null);
		if(result.size()>0)
			throw new DuplicateKeyException(Messages.getString("wndTipoCambio.information.duplicate"));

		/*Valida duplicidad del tipo de cambio*/
		getTipoCambioDAO().guardar(tipoCambio);
	}

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.service.business.TipoCambioManager#actualizar(com.tepsa.sisvyr.model.bean.TipoCambio)
	 */
	@Transactional
	@Override
	public void actualizar(TipoCambio tipoCambio)throws Exception {
		// TODO Auto-generated method stub
		TreeMap<String, Object>criteriosBusqueda=new TreeMap<>();
		criteriosBusqueda.put("fecha", tipoCambio.getFecha());
		criteriosBusqueda.put("tipoMoneda", tipoCambio.getTipoMoneda());
		criteriosBusqueda.put("estadoRegistro", Constantes.VALUE_ACTIVO);

		List<TipoCambio>result=buscarPorX(criteriosBusqueda, null);
		for(TipoCambio tipoCambio2:result){
			if(tipoCambio2.getId().intValue()!=tipoCambio.getId().intValue()){
				throw new DuplicateKeyException(Messages.getString("wndTipoCambio.information.duplicate"));
			}
		}


		/*Actualiza le Tipo de cambio*/
		getTipoCambioDAO().actualizar(tipoCambio);
	}

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.service.business.TipoCambioManager#buscarUltimoTipoCambio(java.lang.Integer)
	 */
	@Override
	public TipoCambio buscarUltimoTipoCambio(Integer idTipoMoneda)throws Exception {
		// TODO Auto-generated method stub

		return getTipoCambioDAO().buscarUltimoTipoCambio(idTipoMoneda);
	}

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.service.business.TipoCambioManager#buscarTiposCambio(java.lang.String, java.lang.String, java.lang.Integer)
	 */
	@Override
	public List<TipoCambio> buscarTiposCambio(String fechaInicio,String fechaFin, Integer idTipoMoneda) throws Exception {
		// TODO Auto-generated method stub
		return getTipoCambioDAO().buscarTiposCambio(fechaInicio, fechaFin, idTipoMoneda);
	}

}
