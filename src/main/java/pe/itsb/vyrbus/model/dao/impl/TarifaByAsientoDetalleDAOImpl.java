/**
 * Proyecto		: SISVYR
 * Sistema		: Sistema de Ventas y Reservas
 * Descripción	: 
 * Autor		: José Abanto
 * Fecha		: 30/01/2019
 * Hora			: 11:27:55
 */
package pe.itsb.vyrbus.model.dao.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

import org.jfree.util.Log;

import pe.itsb.vyrbus.model.bean.TarifaByAsientoDetalle;
import pe.itsb.vyrbus.model.dao.TarifaByAsientoDetalleDAO;


/**
 * @author Jose Abanto
 *
 */
@SuppressWarnings("unchecked")
public class TarifaByAsientoDetalleDAOImpl extends GenericDAOImpl implements TarifaByAsientoDetalleDAO {

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.model.dao.TarifaByAsientoDetalleDAO#buscarPorEstadoRegistro(java.lang.String, java.lang.String)
	 */	
	@Override
	public ArrayList<TarifaByAsientoDetalle> buscarPorEstadoRegistro(String estado,
			String criterioOrden) {
		// TODO Auto-generated method stub
		return (ArrayList<TarifaByAsientoDetalle>) findByEstadoRegistro(TarifaByAsientoDetalle.class, estado, criterioOrden);
	}

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.model.dao.TarifaByAsientoDetalleDAO#buscarPorX(java.util.TreeMap, java.util.List)
	 */
	@Override
	public ArrayList<TarifaByAsientoDetalle> buscarPorX(
			TreeMap<String, Object> criteriosBusqueda,
			List<String> criteriosOrdenar) {
		// TODO Auto-generated method stub
		return (ArrayList<TarifaByAsientoDetalle>) findByX(TarifaByAsientoDetalle.class, criteriosBusqueda, criteriosOrdenar);
	}

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.model.dao.TarifaByAsientoDetalleDAO#buscarPorId(java.lang.Long)
	 */
	@Override
	public TarifaByAsientoDetalle buscarPorId(Long id) {
		// TODO Auto-generated method stub
		return (TarifaByAsientoDetalle) findById(TarifaByAsientoDetalle.class, id);
	}

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.model.dao.TarifaByAsientoDetalleDAO#guardar(com.tepsa.sisvyr.model.bean.TarifaByAsientoDetalle)
	 */
	@Override
	public void guardar(TarifaByAsientoDetalle TarifaByAsientoDetalle) {
		// TODO Auto-generated method stub
		save(TarifaByAsientoDetalle);
	}

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.model.dao.TarifaByAsientoDetalleDAO#actualizar(com.tepsa.sisvyr.model.bean.TarifaByAsientoDetalle)
	 */
	@Override
	public void actualizar(TarifaByAsientoDetalle TarifaByAsientoDetalle) {
		// TODO Auto-generated method stub
		update(TarifaByAsientoDetalle);
	}

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.model.dao.TarifaByAsientoDetalleDAO#buscarByTarifaId(java.lang.Long)
	 */
	@Override
	public List<TarifaByAsientoDetalle> buscarByTarifaId(Long tarifaId) throws Exception {
		
		clearMemory(); //Elimina los datos de memoria para obtimiza los resultados de la consulta
		
		String hql="FROM TarifaByAsientoDetalle WHERE tarifa.id="+tarifaId+" AND estadoRegistro='A' ORDER BY id DESC, tipoPrecio.id DESC";
		Log.info(hql);
		List<TarifaByAsientoDetalle>result=getSession().createQuery(hql).list();
		
		return result;
	}

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.model.dao.TarifaByAsientoDetalleDAO#maxPrecioByTipoPrecio(java.lang.Long, java.lang.Integer, java.lang.Integer)
	 */
	@Override
	public Double maxPrecioByTipoPrecio(Long tarifa_id, Integer tipoAsiento_id, Integer tipoPrecio_id) throws Exception {
		String sql="SELECT MAX(td.n_tarifa) MaxPrecio "
				+ "FROM VRTTARDET td "
				+ "WHERE td.tar_id="+tarifa_id+" AND td.tipasi_id="+tipoAsiento_id+" AND td.tipoprecio_id="+tipoPrecio_id+" AND td.c_estreg='A'";
		log.info(sql);
		
		Double d_maxPrecio=.00;
		Object o_maxPrecio=getSession().createSQLQuery(sql).uniqueResult();
		if(o_maxPrecio!=null)
			d_maxPrecio=((BigDecimal)o_maxPrecio).doubleValue();
		
		
		return d_maxPrecio;
	}

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.model.dao.TarifaByAsientoDetalleDAO#buscarByTarifaId(java.lang.Long, java.lang.Integer)
	 */
	@Override
	public List<TarifaByAsientoDetalle> buscarByTarifaId(Long tarifaId,Integer tipoAsientoId, Integer tipoPrecioId) throws Exception {
		String hql="FROM TarifaByAsientoDetalle "
				+ "WHERE tarifa.id="+tarifaId+" "
				  + "AND tipoAsiento.id="+(tipoAsientoId!=null?tipoAsientoId:"tipoAsiento.id")+" "
				  + "AND tipoPrecio.id="+(tipoPrecioId!=null?tipoPrecioId:"tipoPrecio.id")+" "
				  + "AND estadoRegistro='A' "				  
				+ "ORDER BY tipoPrecio.id DESC, id DESC";
		Log.info(hql);
		List<TarifaByAsientoDetalle>result=getSession().createQuery(hql).list();
		
		return result;
	}

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.model.dao.TarifaByAsientoDetalleDAO#buscarConfigAsientosDuplicate(com.tepsa.sisvyr.model.bean.TarifaByAsientoDetalle)
	 */
	@Override
	public List<TarifaByAsientoDetalle> buscarConfigAsientosDuplicate(TarifaByAsientoDetalle TarifaByAsientoDetalle) throws Exception {
		String hql="FROM TarifaByAsientoDetalle "
				 + "WHERE tarifa.id="+TarifaByAsientoDetalle.getTarifaByAsiento().getId()+" "
				   + "AND asientos='"+TarifaByAsientoDetalle.getAsientos()+"' "
				   + "AND tipoAsiento.id="+TarifaByAsientoDetalle.getTipoAsiento().getId()+" "
				   + "AND tipoPrecio.id="+TarifaByAsientoDetalle.getTipoPrecio().getId()+" "
				   + "AND itinerario.id="+TarifaByAsientoDetalle.getItinerario().getId()+" "
				   + "AND estadoRegistro='A'";
		log.info(hql);
		
		List<TarifaByAsientoDetalle>result=getSession().createQuery(hql).list();
		
		return result;
	}

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.model.dao.TarifaByAsientoDetalleDAO#buscraTarifaPresentacion(java.lang.Long, java.lang.Integer, java.lang.String, java.lang.Integer, java.lang.Integer, java.lang.Integer, java.lang.Integer)
	 */
	@Override
	public Double buscraTarifaPresentacion(Long itinerarioId, Integer rutaId,String fechaPartida, Integer servicioId, Integer isAsientoSuite,Integer canalVentaId, Integer agenciaId) throws Exception {
		
		String sql="SELECT sf_get_tarpre("+itinerarioId+","+rutaId+",'"+fechaPartida+"',"+servicioId+","+isAsientoSuite+",NULL,NULL) tarifa FROM DUAL";
		log.info(sql);
		Double tarifa= (((BigDecimal) getSession().createSQLQuery(sql).uniqueResult()).doubleValue());
		
		return tarifa;
	}

}
