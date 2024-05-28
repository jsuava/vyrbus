/**
 * Proyecto		: SISVYR
 * Sistema		: Sistema de Ventas y Reservas
 * Descripción	: 
 * Autor		: José Abanto
 * Fecha		: 9 may. 2024
 * Hora			: 21:40:30
 */
package pe.itsb.vyrbus.model.dao.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

import pe.itsb.vyrbus.model.bean.Ruta;
import pe.itsb.vyrbus.model.bean.Servicio;
import pe.itsb.vyrbus.model.bean.TarifaByAsiento;
import pe.itsb.vyrbus.model.dao.TarifaByAsientoDAO;


/**
 * @author abant
 *
 */
@SuppressWarnings("unchecked")
public class TarifaByAsientoDAOImpl extends GenericDAOImpl implements TarifaByAsientoDAO{

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.model.dao.TarifaDAO#buscarPorEstadoRegistro(java.lang.String, java.lang.String)
	 */
	
	@Override
	public ArrayList<TarifaByAsiento> buscarPorEstadoRegistro(String estado,
			String criterioOrden) {

		return (ArrayList<TarifaByAsiento>) findByEstadoRegistro(TarifaByAsiento.class, estado, criterioOrden);
	}

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.model.dao.TarifaDAO#buscarPorX(java.util.TreeMap, java.util.List)
	 */
	@Override
	public ArrayList<TarifaByAsiento> buscarPorX(
			TreeMap<String, Object> criteriosBusqueda,
			List<String> criteriosOrdenar) {
		// TODO Auto-generated method stub
		return (ArrayList<TarifaByAsiento>) findByX(TarifaByAsiento.class, criteriosBusqueda, criteriosOrdenar);
	}

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.model.dao.TarifaDAO#buscarPorId(java.lang.Long)
	 */
	@Override
	public TarifaByAsiento buscarPorId(Long id) {
		// TODO Auto-generated method stub
		return (TarifaByAsiento) findById(TarifaByAsiento.class, id);
	}

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.model.dao.TarifaDAO#guardar(com.tepsa.sisvyr.model.bean.Tarifa)
	 */
	@Override
	public void guardar(TarifaByAsiento tarifaByAsiento) {
		// TODO Auto-generated method stub
		save(tarifaByAsiento);
	}

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.model.dao.TarifaDAO#actualizar(com.tepsa.sisvyr.model.bean.Tarifa)
	 */
	@Override
	public void actualizar(TarifaByAsiento tarifaByAsiento) {
		// TODO Auto-generated method stub
		update(tarifaByAsiento);
	}

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.model.dao.TarifaDAO#validarTarifasCreadas(java.lang.String, java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	public List<TarifaByAsiento> validarTarifasCreadas(String fechaInicio,String fechaFin, String rutas_ids, String servicios_ids) throws Exception {
		String sql="SELECT r.c_origen, r.c_destino, s.c_denominacion servicio "
				 + "FROM VRTTAR t "
				   + "INNER JOIN VRMRUTA r ON (r.ruta_id=t.ruta_id) "
				   + "INNER JOIN VRMSERVICIO s ON (s.servicio_id=t.servicio_id) "
				 + "WHERE t.d_Fec BETWEEN '"+fechaInicio+"' AND '"+fechaFin+"' "
				   + "AND t.ruta_id IN ("+rutas_ids+") "
				   + "AND t.servicio_id IN ("+servicios_ids+") "
				   + "AND t.c_estreg='A' "
				 + "GROUP BY r.c_origen, r.c_destino, s.c_denominacion";
		log.info(sql);
		List<?>result=getSession().createSQLQuery(sql).list();
		
		List<TarifaByAsiento>listTarifas= new ArrayList<>();
		for(int x=0;x<result.size();x++){
			Object[] obj = (Object[])result.get(x);
			Ruta ruta= new Ruta();
			ruta.setOrigen(obj[0].toString());
			ruta.setDestino(obj[1].toString());
			
			Servicio servicio= new Servicio();
			servicio.setDenominacion(obj[2].toString());
			
			TarifaByAsiento tarifa = new TarifaByAsiento();
			tarifa.setRuta(ruta);
			tarifa.setServicio(servicio);
			
			listTarifas.add(tarifa);
		}		
	
		return listTarifas;
	}

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.model.dao.TarifaDAO#buscarTarifaByRutaServicio(java.lang.String, java.lang.Integer, java.lang.Integer)
	 */
	@Override
	public TarifaByAsiento buscarTarifaByRutaServicio(String fecha, Integer ruta_Id,Integer servicio_Id) throws Exception {
		String hql = "FROM Tarifa WHERE fecha='"+fecha+"' AND  servicio.id="+servicio_Id+" AND ruta.id="+ruta_Id+" AND estadoRegistro='A'";
		log.info(hql);
		TarifaByAsiento tarifaByAsiento = (TarifaByAsiento) getSession().createQuery(hql).uniqueResult();
		
		return tarifaByAsiento;
	}

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.model.dao.TarifaDAO#buscarRutasGroupByRuta(java.lang.String, java.lang.Integer, java.lang.Integer)
	 */
	@Override
	public List<TarifaByAsiento> buscarRutasGroupByRuta(String fecha,Integer localidad_idOrigen, Integer localidad_idDestino)throws Exception {
		String sql="SELECT r.ruta_id, r.c_origen, r.c_destino "
				 + "FROM VRTTAR t "
				   + "INNER JOIN VRMRUTA r ON (r.ruta_id=t.ruta_id) "
				 + "WHERE t.d_fec='"+fecha+"' AND t.c_estreg='A' "
				   + "AND r.localidad_idorigen= NVL("+localidad_idOrigen+", r.localidad_idorigen) "
				   + "AND r.localidad_iddestino= NVL("+localidad_idDestino+", r.localidad_iddestino) "
				 + "GROUP BY r.ruta_id, r.c_origen, r.c_destino "
				 + "ORDER BY r.c_origen, r.c_destino";
		log.info(sql);
		List<?>result=getSession().createSQLQuery(sql).list();
		
		List<TarifaByAsiento>listTarifas= new ArrayList<>();
		for(int x=0;x<result.size();x++){
			Object[] obj = (Object[])result.get(x);
			Ruta ruta= new Ruta();
			ruta.setId(((BigDecimal)obj[0]).intValue());
			ruta.setOrigen(obj[1].toString());
			ruta.setDestino(obj[2].toString());
			
			TarifaByAsiento tarifaByAsiento = new TarifaByAsiento();
			tarifaByAsiento.setRuta(ruta);
			
			listTarifas.add(tarifaByAsiento);
		}
		
		
		return listTarifas;
	}

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.model.dao.TarifaDAO#buscarTarifasByFechaServicio(java.lang.String, java.lang.Integer)
	 */
	@Override
	public List<TarifaByAsiento> buscarTarifasByFechaServicio(String fecha,Integer servicio_id) throws Exception {
		String hql = "FROM Tarifa WHERE fecha='"+fecha+"' AND  servicio.id="+servicio_id+" AND estadoRegistro='A'";
		log.info(hql);		
		List<TarifaByAsiento> result= getSession().createQuery(hql).list();
		
		return result;
	}

}
