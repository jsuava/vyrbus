/**
 * Proyecto		: SISVYR
 * Sistema		: Sistema de Ventas y Reservas
 * Descripción	: 
 * Autor		: José Abanto
 * Fecha		: 10/11/2014
 * Hora			: 14:30:17
 */
package com.cystesoft.vyrbus.model.dao.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.TreeMap;

import com.cystesoft.vyrbus.model.bean.Itinerario;
import com.cystesoft.vyrbus.model.bean.Ruta;
import com.cystesoft.vyrbus.model.bean.Servicio;
import com.cystesoft.vyrbus.model.bean.VentaTramo;
import com.cystesoft.vyrbus.model.dao.VentaTramoDAO;
import com.cystesoft.vyrbus.service.util.Constantes;

/**
 * @author JABANTO
 *
 */
public class VentaTramoDAOImpl extends GenericDAOImpl implements VentaTramoDAO {

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.model.dao.VentaTramoDAO#buscarByRuta(java.lang.Integer)
	 */
	@Override
	@SuppressWarnings("unchecked")
	public List<VentaTramo> buscarByRuta(Integer idRuta, String fechaPartida) throws Exception {
		// TODO Auto-generated method stub
		String hql=" FROM VentaTramo vt WHERE to_date('"+fechaPartida+"','dd/MM/yyyy') BETWEEN vt.fechaInicio AND vt.fechaFin AND  vt.ruta.id="+idRuta+" AND vt.estadoRegistro='"+Constantes.VALUE_ACTIVO+"' ";
		log.info(hql);
				
		List<VentaTramo> listVentaTramos=getSession().createQuery(hql).list();
		
		return listVentaTramos;
	}

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.model.dao.VentaTramoDAO#guardar(com.tepsa.sisvyr.model.bean.VentaTramo)
	 */
	@Override
	public void guardar(VentaTramo ventaTramo) throws Exception {
		// TODO Auto-generated method stub
		super.save(ventaTramo);
	}

	/* 
	 * (non-Javadoc)
	 * @see com.tepsa.sisvyr.model.dao.VentaTramoDAO#buscarPorItinerarioRuta(com.tepsa.sisvyr.model.bean.Itinerario, com.tepsa.sisvyr.model.bean.Ruta)
	 */
	@Override
	public VentaTramo buscarPorItinerarioRuta(Itinerario itinerario, Ruta ruta)throws Exception {
		// TODO Auto-generated method stub
		String hql="FROM VentaTramo vt WHERE vt.itinerario.id="+itinerario.getId()+" AND vt.ruta.id="+ruta.getId()+" AND vt.estadoRegistro='"+Constantes.VALUE_ACTIVO+"' ";// AND vt.despuesHoraSalida="+isNotVentaAdelantada;
		log.info(hql);
		
		VentaTramo ventaTramo=(VentaTramo) getSession().createQuery(hql).uniqueResult();
		return ventaTramo;
	}

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.model.dao.VentaTramoDAO#buscarPor(java.util.TreeMap, java.util.List)
	 */
	@Override
	public List<VentaTramo> buscarPor(TreeMap<String, Object> criteriosBusqueda,List<String> criteriosOrden) throws Exception {
		// TODO Auto-generated method stub
		return null;//(List<VentaTramo>) super.findByX(VentaTramo.class, criteriosBusqueda, criteriosOrden);
	}

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.model.dao.VentaTramoDAO#buscarPor(java.lang.String, java.lang.String, java.lang.Integer, java.lang.Integer)
	 */
	@Override
	public List<VentaTramo> buscarPor(String fechaInicial, String fechaFinal,Integer origenId, Integer destinoId) throws Exception {
		// TODO Auto-generated method stub
		String sql="SELECT vt.ventra_id  "
					   + ",vt.ruta_id "
				       + ",vt.itinerario_id "
				       + ",r.c_origen "
				       + ",r.c_destino "
				       + ",s.c_Denominacion servicio "
				       + ",di.d_Fecpar "
				       + ",di.c_horpar "
				       + ",vt.n_deshorsal "
				  + "FROM VRTVENTRA vt "
				     + "INNER JOIN VRTDETITI di ON (di.itinerario_id=vt.itinerario_id AND di.ruta_id=vt.ruta_id) "
				     + "INNER JOIN VRMRUTA r ON (r.ruta_id=vt.ruta_id) "
				     + "INNER JOIN VRTITINERARIO i ON (i.itinerario_id=vt.itinerario_id) "
				     + "INNER JOIN VRMSERVICIO s ON (s.servicio_id=i.servicio_id) "
				  + "WHERE di.d_fecpar BETWEEN '"+fechaInicial+"' AND '"+fechaFinal+"' "
				  	 + "AND r.localidad_idorigen=NVL("+origenId+",r.localidad_idorigen) "
				  	 + "AND r.localidad_iddestino=NVL("+destinoId+",r.localidad_iddestino) "
				     + "AND vt.c_estreg='A'  "
				  + "ORDER BY di.d_Fecpar";
		log.info(sql);
		List<?>result=getSession().createSQLQuery(sql).list();
		List<VentaTramo>listVentaTramo=new ArrayList<VentaTramo>();
		for(int x=0;x<result.size();x++){
			Object[] obj=(Object[]) result.get(x);
			
			Ruta ruta=new Ruta();
			ruta.setId(((BigDecimal)obj[1]).intValue());
			ruta.setOrigen(obj[3].toString());
			ruta.setDestino(obj[4].toString());

			Servicio servicio=new Servicio();
			servicio.setDenominacion(obj[5].toString());
			
			Itinerario itinerario=new Itinerario();
			itinerario.setId(((BigDecimal)obj[2]).longValue());
			itinerario.setFechaPartida((Date)obj[6]);
			itinerario.setHoraPartida(obj[7].toString());
			itinerario.setServicio(servicio);
			
			VentaTramo ventaTramo=new VentaTramo();
			ventaTramo.setId(((BigDecimal)obj[0]).longValue());
			ventaTramo.setItinerario(itinerario);
			ventaTramo.setRuta(ruta);
			ventaTramo.setDespuesHoraSalida(((BigDecimal)obj[8]).intValue());
			
			listVentaTramo.add(ventaTramo);
		}
		return listVentaTramo;
	}

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.model.dao.VentaTramoDAO#anular(java.lang.Long)
	 */
	@Override
	public void anular(Long id) throws Exception {
		// TODO Auto-generated method stub
		String sql="UPDATE VRTVENTRA SET c_estreg='I' WHERE ventra_id="+id;
		log.info(sql);
		
		getSession().createSQLQuery(sql).executeUpdate();
	}

}
