/**
 * Proyecto		: SISVYR
 * Sistema		: Sistema de Ventas y Reservas
 * Descripción	: 
 * Autor		: José Abanto
 * Fecha		: 07/07/2016
 * Hora			: 11:21:48
 */
package com.cystesoft.vyrbus.model.dao.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.TreeMap;

import com.cystesoft.vyrbus.model.bean.Ruta;
import com.cystesoft.vyrbus.model.bean.Servicio;
import com.cystesoft.vyrbus.model.bean.TarifaCliente;
import com.cystesoft.vyrbus.model.bean.TarifaClienteDetalle;
import com.cystesoft.vyrbus.model.bean.TipoAsiento;
import com.cystesoft.vyrbus.model.dao.TarifaClienteDetalleDAO;

/**
 * @author jabanto
 *
 */
public class TarifaClienteDetalleDAOImpl extends GenericDAOImpl implements TarifaClienteDetalleDAO{

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.model.dao.TarifaClienteDetalleDAO#buscarPorEstadoRegistro(java.lang.String, java.lang.String)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public ArrayList<TarifaClienteDetalle> buscarPorEstadoRegistro(String estado, String criterioOrden) {
		// TODO Auto-generated method stub
		return (ArrayList<TarifaClienteDetalle>) super.findByEstadoRegistro(TarifaClienteDetalle.class, estado, criterioOrden);
	}

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.model.dao.TarifaClienteDetalleDAO#buscarPorX(java.util.TreeMap, java.util.List)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public ArrayList<TarifaClienteDetalle> buscarPorX(TreeMap<String, Object> criteriosBusqueda,List<String> criteriosOrdenar) {
		// TODO Auto-generated method stub
		return (ArrayList<TarifaClienteDetalle>) super.findByX(TarifaClienteDetalle.class, criteriosBusqueda, criteriosOrdenar);
	}

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.model.dao.TarifaClienteDetalleDAO#buscarPorId(java.lang.Long)
	 */
	@Override
	public TarifaClienteDetalle buscarPorId(Long id) {
		// TODO Auto-generated method stub
		return (TarifaClienteDetalle) super.findById(TarifaClienteDetalle.class, id);
	}

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.model.dao.TarifaClienteDetalleDAO#guardar(com.tepsa.sisvyr.model.bean.TarifaClienteDetalle)
	 */
	@Override
	public void guardar(TarifaClienteDetalle tarifaClienteDetalle) {
		// TODO Auto-generated method stub
		super.save(tarifaClienteDetalle);
	}

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.model.dao.TarifaClienteDetalleDAO#actualizar(com.tepsa.sisvyr.model.bean.TarifaClienteDetalle)
	 */
	@Override
	public void actualizar(TarifaClienteDetalle tarifaClienteDetalle) {
		// TODO Auto-generated method stub
		super.update(tarifaClienteDetalle);
	}

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.model.dao.TarifaClienteDetalleDAO#buscarTarifaPersonalizada(java.lang.Long, java.lang.Integer, java.lang.Integer, java.lang.String)
	 */
	@Override
	public List<TarifaClienteDetalle> buscarTarifaPersonalizada(Long clienteID,Integer rutaID, Integer servicioID, String fechaPartida)throws Exception {
		return buscarTarifa(clienteID, rutaID, servicioID, fechaPartida, null);
	}

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.model.dao.TarifaClienteDetalleDAO#buscarTarifaPersonalizada(java.lang.Long, java.lang.Integer, java.lang.Integer, java.lang.String, java.lang.Integer)
	 */
	@Override
	public TarifaClienteDetalle buscarTarifaPersonalizada(Long clienteID,Integer rutaID, Integer servicioID, String fechaPartida,Integer asiento) throws Exception {
		// TODO Auto-generated method stub
		 List<TarifaClienteDetalle> result=buscarTarifa(clienteID, rutaID, servicioID, fechaPartida, asiento);
		 if(result.size()>0){
			 int index = result.size()-1;
			 return result.get(index);
		 }
		return null;
	}

	
	private List<TarifaClienteDetalle> buscarTarifa(Long clienteID,Integer rutaID, Integer servicioID, String fechaPartida,Integer asiento){
		String sql="SELECT tcd.tarcli_id, tcd.tarclidet_id, tcd.n_asiini, tcd.n_asifin, tcd.tipasi_id, tcd.n_tarifa "
				 + "FROM VRTTARCLIDET tcd "
				 + "WHERE tcd.tarcli_id IN ( SELECT tc.tarcli_id "
				 						  + "FROM VRTTARCLI tc "
				 						  + "WHERE tc.cliente_id="+clienteID+" AND tc.ruta_id="+rutaID+" AND tc.servicio_id="+servicioID+" "
				 						  	+ "AND to_date('"+fechaPartida+"','dd/MM/yyyy') BETWEEN tc.d_fecact AND tc.d_feccad "
				 						  	+ "AND tc.d_Fecsus IS NULL AND tc.c_Estreg='A' "
				 						+ ") "
				  + "AND tcd.c_estreg='A' ";
		if(asiento!=null)
			sql+="AND "+asiento+" BETWEEN tcd.n_asiini AND tcd.n_asifin ";
		sql += "ORDER BY tcd.tarclidet_id ";
		
		log.info(sql);
		List<?>result =getSession().createSQLQuery(sql).list();
		List<TarifaClienteDetalle> lstTarifaDetalle=new ArrayList<>();
		for(int i=0;i<result.size();i++){
			Object[] obj = (Object[]) result.get(i);
			TarifaCliente tarifaCliente=new TarifaCliente();
			tarifaCliente.setId(((BigDecimal)obj[0]).intValue());
			
			TarifaClienteDetalle tarifaClienteDetalle=new TarifaClienteDetalle();
			tarifaClienteDetalle.setTarifaCliente(tarifaCliente);
			tarifaClienteDetalle.setId(((BigDecimal)obj[1]).intValue());
			tarifaClienteDetalle.setAsientoInicio(((BigDecimal)obj[2]).intValue());
			tarifaClienteDetalle.setAsientoFin(((BigDecimal)obj[3]).intValue());
			tarifaClienteDetalle.setTipoAsiento(new  TipoAsiento(((BigDecimal)obj[4]).intValue()));
			tarifaClienteDetalle.setTarifa(((BigDecimal)obj[5]).doubleValue());
			
			lstTarifaDetalle.add(tarifaClienteDetalle);
		}
		
		return lstTarifaDetalle;
		
	}

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.model.dao.TarifaClienteDetalleDAO#buscarByRuc(java.lang.String)
	 */
	@Override
	public List<TarifaClienteDetalle> buscarByRuc(String ruc) throws Exception {
		String sql="SELECT tc.tarcli_id, tcd.tarclidet_id, rt.c_origen, rt.c_destino, sv.c_denominacion servicio, tc.d_fecact, tc.d_feccad "
					   + ",tcd.n_asiini, tcd.n_asifin, ta.c_denominacion tipoAsiento, tcd.n_tarifa "
				 + "FROM VRTTARCLI tc "
				 	+ "INNER JOIN VRTTARCLIDET tcd ON (tcd.tarcli_id=tc.tarcli_id) "
				 	+ "INNER JOIN VRMCLIENTE cl ON (cl.cliente_id=tc.cliente_id) "
				 	+ "INNER JOIN VRMRUTA rt ON (rt.ruta_id=tc.ruta_id) "
				 	+ "INNER JOIN VRMSERVICIO sv ON (sv.servicio_id=tc.servicio_id) "
				 	+ "INNER JOIN VRMTIPASI ta ON (ta.tipasi_id=tcd.tipasi_id) "
				 + "WHERE cl.c_numdoc='"+ruc+"' "
				 	+ "AND tc.c_estreg='A' AND tcd.c_Estreg='A' "
				 + "ORDER BY rt.c_origen, rt.c_destino, sv.c_denominacion, tcd.n_asiini, tcd.n_asifin";
		log.info(sql);
		List<?> result=getSession().createSQLQuery(sql).list();
		List<TarifaClienteDetalle> lstTarifadetalle=new ArrayList<>();
		for(int x=0;x<result.size();x++){
			Object[] obj= (Object[]) result.get(x);
				
			Ruta ruta=new Ruta();
			ruta.setOrigen(obj[2].toString());
			ruta.setDestino(obj[3].toString());
			Servicio servicio=new Servicio();
			servicio.setDenominacion(obj[4].toString());
			TipoAsiento tipoAsiento=new TipoAsiento();
			tipoAsiento.setDenominacion(obj[9].toString());
			
			TarifaCliente tarifaCliente=new TarifaCliente();
			tarifaCliente.setId(((BigDecimal)obj[0]).intValue());
			tarifaCliente.setFechaActivacion((Date)obj[5]);
			tarifaCliente.setFechaCaducidad((Date)obj[6]);
			tarifaCliente.setRuta(ruta);
			tarifaCliente.setServicio(servicio);
			
			TarifaClienteDetalle tarifaClienteDetalle=new TarifaClienteDetalle();
			tarifaClienteDetalle.setId(((BigDecimal)obj[1]).intValue());
			tarifaClienteDetalle.setAsientoInicio(((BigDecimal)obj[7]).intValue());
			tarifaClienteDetalle.setAsientoFin(((BigDecimal)obj[8]).intValue());
			tarifaClienteDetalle.setTarifa(((BigDecimal)obj[10]).doubleValue());
			tarifaClienteDetalle.setTipoAsiento(tipoAsiento);
			tarifaClienteDetalle.setTarifaCliente(tarifaCliente);
			
			lstTarifadetalle.add(tarifaClienteDetalle);
		}
		return lstTarifadetalle;
	}
}
