/**
 * Proyecto		: SISVYR
 * Sistema		: Sistema de Ventas y Reservas
 * Descripción	: 
 * Autor		: José Abanto
 * Fecha		: 30/01/2019
 * Hora			: 11:28:56
 */
package pe.itsb.vyrbus.service.business.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

import org.springframework.transaction.annotation.Transactional;
import org.zkoss.zk.ui.Executions;

import pe.itsb.vyrbus.model.bean.TarifaByAsientoDetalle;
import pe.itsb.vyrbus.model.bean.Usuario;
import pe.itsb.vyrbus.model.dao.TarifaByAsientoDAO;
import pe.itsb.vyrbus.model.dao.TarifaByAsientoDetalleDAO;
import pe.itsb.vyrbus.service.business.TarifaByAsientoDetalleManager;
import pe.itsb.vyrbus.service.util.Constantes;
import pe.itsb.vyrbus.service.util.UtilData;


/**
 * @author Jose Abanto
 *
 */
public class TarifaByAsientoDetalleManagerImpl implements TarifaByAsientoDetalleManager{
	private TarifaByAsientoDetalleDAO tarifaByAsientoDetalleDAO;
	private TarifaByAsientoDAO tarifaByAsientoDAO;

	/**
	 * @return the tarifaDetalleDAO
	 */
	public TarifaByAsientoDetalleDAO getTarifaByAsientoDetalleDAO() {
		return tarifaByAsientoDetalleDAO;
	}

	/**
	 * @param tarifaDetalleDAO the tarifaDetalleDAO to set
	 */
	public void setTarifaByAsientoDetalleDAO(TarifaByAsientoDetalleDAO tarifaDetalleDAO) {
		this.tarifaByAsientoDetalleDAO = tarifaDetalleDAO;
	}
	
	public TarifaByAsientoDAO getTarifaByAsientoDAO() {
		return tarifaByAsientoDAO;
	}

	public void setTarifaByAsientoDAO(TarifaByAsientoDAO tarifaByAsientoDAO) {
		this.tarifaByAsientoDAO = tarifaByAsientoDAO;
	}

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.service.business.TarifaDetalleManager#buscarPorEstadoRegistro(java.lang.String, java.lang.String)
	 */
	@Override
	public ArrayList<TarifaByAsientoDetalle> buscarPorEstadoRegistro(String estado,
			String criterioOrden) {
		// TODO Auto-generated method stub
		return getTarifaByAsientoDetalleDAO().buscarPorEstadoRegistro(estado, criterioOrden);
	}

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.service.business.TarifaDetalleManager#buscarPorX(java.util.TreeMap, java.util.List)
	 */
	@Override
	public ArrayList<TarifaByAsientoDetalle> buscarPorX(
			TreeMap<String, Object> criteriosBusqueda,
			List<String> criteriosOrdenar) {
		// TODO Auto-generated method stub
		return getTarifaByAsientoDetalleDAO().buscarPorX(criteriosBusqueda, criteriosOrdenar);
	}

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.service.business.TarifaDetalleManager#buscarPorId(java.lang.Long)
	 */
	@Override
	public TarifaByAsientoDetalle buscarPorId(Long id) {
		// TODO Auto-generated method stub
		return getTarifaByAsientoDetalleDAO().buscarPorId(id);
	}

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.service.business.TarifaDetalleManager#guardar(com.tepsa.sisvyr.model.bean.TarifaDetalle)
	 */
	@Transactional
	@Override
	public void guardar(TarifaByAsientoDetalle tarifaDetalle) throws Exception {
		Usuario usuario=(Usuario) Executions.getCurrent().getSession().getAttribute(Constantes.ATRIBUTO_USUARIO);
		
		//Anula las tarifas que tienen la misma configuracion de asientos con el mismo itinerario
		if(tarifaDetalle.getItinerario()!=null){
			if(tarifaDetalle.getTarifaByAsiento()!=null && tarifaDetalle.getTarifaByAsiento().getId()!=null){
				List<TarifaByAsientoDetalle>resultDuplicate=buscarConfigAsientosDuplicate(tarifaDetalle);
				for(TarifaByAsientoDetalle _tarifaDetalle: resultDuplicate){
					_tarifaDetalle.setEstadoRegistro(Constantes.VALUE_INACTIVO);
					UtilData.auditarRegistro(_tarifaDetalle, true, usuario, Executions.getCurrent());
					actualizar(_tarifaDetalle);
				}
			}else{
				//Si no existe lo crea la tarifa para luego insertar el detalle
				getTarifaByAsientoDAO().guardar(tarifaDetalle.getTarifaByAsiento());
			}
						
		}		
		
		getTarifaByAsientoDetalleDAO().guardar(tarifaDetalle);
	}

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.service.business.TarifaDetalleManager#actualizar(com.tepsa.sisvyr.model.bean.TarifaDetalle)
	 */
	@Transactional
	@Override
	public void actualizar(TarifaByAsientoDetalle tarifaByAsientoDetalle) {
		// TODO Auto-generated method stub
		getTarifaByAsientoDetalleDAO().actualizar(tarifaByAsientoDetalle);
	}

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.service.business.TarifaDetalleManager#buscarByTarifaId(java.lang.Long)
	 */
	@Override
	public List<TarifaByAsientoDetalle> buscarByTarifaId(Long tarifaByAsientoId) throws Exception {
			
		return getTarifaByAsientoDetalleDAO().buscarByTarifaId(tarifaByAsientoId);
	}

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.service.business.TarifaDetalleManager#maxPrecioByTipoPrecio(java.lang.Long, java.lang.Integer, java.lang.Integer)
	 */
	@Override
	public Double maxPrecioByTipoPrecio(Long tarifa_id, Integer tipoAsiento_id,
			Integer tipoPrecio_id) throws Exception {
		// TODO Auto-generated method stub
		return getTarifaByAsientoDetalleDAO().maxPrecioByTipoPrecio(tarifa_id, tipoAsiento_id, tipoPrecio_id);
	}

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.service.business.TarifaDetalleManager#buscarByTarifaId(java.lang.Long, java.lang.Integer)
	 */
	@Override
	public List<TarifaByAsientoDetalle> buscarByTarifaId(Long tarifaId,Integer tipoAsientoId, Integer tipoPrecioId) throws Exception {
		return getTarifaByAsientoDetalleDAO().buscarByTarifaId(tarifaId, tipoAsientoId, tipoPrecioId);
	}

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.service.business.TarifaDetalleManager#buscarConfigAsientos(com.tepsa.sisvyr.model.bean.TarifaDetalle)
	 */
	@Override
	public List<TarifaByAsientoDetalle> buscarConfigAsientosDuplicate(TarifaByAsientoDetalle tarifaByAsientoDetalle)throws Exception {
		// TODO Auto-generated method stub
		return getTarifaByAsientoDetalleDAO().buscarConfigAsientosDuplicate(tarifaByAsientoDetalle);
	}

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.service.business.TarifaDetalleManager#buscraTarifaPresentacion(java.lang.Long, java.lang.Integer, java.lang.String, java.lang.Integer, java.lang.Integer, java.lang.Integer, java.lang.Integer)
	 */
	@Override
	public Double buscraTarifaPresentacion(Long itinerarioId, Integer rutaId,
			String fechaPartida, Integer servicioId, Integer isAsientoSuite,
			Integer canalVentaId, Integer agenciaId) throws Exception {
		
		return getTarifaByAsientoDetalleDAO().buscraTarifaPresentacion(itinerarioId, rutaId, fechaPartida, servicioId, isAsientoSuite, canalVentaId, agenciaId);
	}



	
}
