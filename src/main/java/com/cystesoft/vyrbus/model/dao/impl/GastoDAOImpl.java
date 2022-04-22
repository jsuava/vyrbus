package com.cystesoft.vyrbus.model.dao.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.TreeMap;

import com.cystesoft.vyrbus.model.bean.Agencia;
import com.cystesoft.vyrbus.model.bean.DetalleLiquidacion;
import com.cystesoft.vyrbus.model.bean.Gasto;
import com.cystesoft.vyrbus.model.bean.Liquidacion;
import com.cystesoft.vyrbus.model.bean.LiquidacionOficina;
import com.cystesoft.vyrbus.model.bean.TipoGasto;
import com.cystesoft.vyrbus.model.bean.Usuario;
import com.cystesoft.vyrbus.model.dao.GastoDAO;
import com.cystesoft.vyrbus.service.util.Constantes;

/**
 * 
* @author José Abanto
 *
 */
@SuppressWarnings("unchecked")
public class GastoDAOImpl extends GenericDAOImpl implements GastoDAO {

	/*
	 * (non-Javadoc)
	 * @see com.tepsa.sisvyr.model.dao.GastoDAO#buscarPorEstadoRegistro(java.lang.String, java.lang.String)
	 */

	@Override
	public ArrayList<Gasto> buscarPorEstadoRegistro(String estado,String criterioOrden) {
		return (ArrayList<Gasto>) super.findByEstadoRegistro(Gasto.class, estado, criterioOrden);
	}

	/*
	 * (non-Javadoc)
	 * @see com.tepsa.sisvyr.model.dao.GastoDAO#buscarPorX(java.util.TreeMap, java.util.List)
	 */
	@Override
	public ArrayList<Gasto> buscarPorX(TreeMap<String, Object> criteriosBusqueda,List<String> criteriosOrdenar) {
		return (ArrayList<Gasto>) super.findByX(Gasto.class, criteriosBusqueda, criteriosOrdenar);
	}

	/*
	 * (non-Javadoc)
	 * @see com.tepsa.sisvyr.model.dao.GastoDAO#guardar(com.tepsa.sisvyr.model.bean.Gasto)
	 */
	@Override
	public void guardar(Gasto gasto) {
		super.save(gasto);
		
	}

	/*
	 * (non-Javadoc)
	 * @see com.tepsa.sisvyr.model.dao.GastoDAO#actualizar(com.tepsa.sisvyr.model.bean.Gasto)
	 */
	@Override
	public void actualizar(Gasto gasto) {
		super.update(gasto);
	}

	/*
	 * (non-Javadoc)
	 * @see com.tepsa.sisvyr.model.dao.GastoDAO#inactivar(java.lang.Integer)
	 */
	@Override
	public void inactivar(Long id) {
		super.inactivate(Gasto.class, id);
		
	}

	/*
	 * (non-Javadoc)
	 * @see com.tepsa.sisvyr.model.dao.GastoDAO#buscarXId(java.lang.Long)
	 */
	@Override
	public Gasto buscarXId(Long id) {
		return (Gasto) super.findById(Gasto.class, id);
	}

	/*
	 * (non-Javadoc)
	 * @see com.tepsa.sisvyr.model.dao.GastoDAO#buscarGasto(java.lang.String, java.lang.Integer)
	 */
	@Override
	public List<Gasto> buscarGasto(String fechaGasto, Integer idTipoGasto, Integer idAgencia,Integer idUsuario) {
		
//		/*Criteiros de Busqueda*/
//		String criterios="";
//		if (idTipoGasto !=null && idAgencia !=null)//X tipo de gasto y agencia.
//			criterios=" AND lq.agencia_id="+idAgencia+" AND G.TIPGAS_ID="+idTipoGasto;
//		else if (idTipoGasto !=null && idAgencia ==null)//X tipo de gasto
//			criterios=" AND G.TIPGAS_ID="+idTipoGasto;
//		else if (idAgencia !=null && idTipoGasto==null)//X tipo de agencia
//			criterios=" AND lq.agencia_id="+idAgencia;
//		else if (idUsuario!=null) //X login del usuario
//			criterios=" AND lq.usuario_id='"+idUsuario+"' ";
		
		String sql="SELECT g.gasto_id,a.agencia_id,a.c_denominacion as Agencia,lq.c_nomusu as usuario, lq.d_fecliq as FechaLquidacion, tg.tipgas_id, "+ //0-5
					"tg.c_denominacion NombreGasto,g.n_monto, g.c_numdoc, g.c_codbus, g.c_nompil, g.c_consignado, g.c_observacion, dlq.detliq_id, "+ //6-13
					"dlq.audfecins as fechaInsercion, dlq.audusuins as UsuarioInsercion, dlq.audipinse as IpInsercion, "+//14-16
					"g.audfecins as GfechaInsercion, g.audusuins as GUsuarioInsercion, g.audipinse as GIpInsercion, "+//17-19
					"lq.n_estliq, a.c_nomcor as nombreCorto, nvl(tg.n_tipope,0) tipope, tg.c_nomcor nomCarGasto "+//20-23
			"FROM vrtgasto g "+
				"INNER JOIN vrtdetliq dlq ON (dlq.gasto_id=g.gasto_id) "+
				"INNER JOIN vrtliquidacion lq ON (lq.liquidacion_id=dlq.liquidacion_id) "+
				"INNER JOIN vrmtipgas tg ON (tg.tipgas_id=g.tipgas_id) "+
				"INNER JOIN vrmagencia a ON (a.agencia_id=lq.agencia_id) " +
			"WHERE lq.d_fecliq=to_date('"+fechaGasto+"','dd/MM/yyyy') " +
				 "AND lq.agencia_id=NVL("+idAgencia+",lq.agencia_id) " +
				 "AND g.tipgas_id=NVL("+idTipoGasto+",g.tipgas_id) " +
				 "AND lq.usuario_id=NVL("+idUsuario+",lq.usuario_id) " +
				 "AND lq.c_estreg='A' AND g.c_estreg='A' " +
		    "ORDER BY nvl(tg.n_tipope,0), tg.c_denominacion ";
//			"WHERE lq.d_fecliq = to_date('"+fechaGasto+"', 'dd/MM/yyyy') AND lq.c_estreg='A' AND g.c_estreg='A' "+ criterios;
					//"AND lq.agencia_id=1 AND g.c_estreg='A' AND lq.c_estreg='A' ";
		
		log.info(sql);
		
		List<?> result = getSession().createSQLQuery(sql).list();
		List<Gasto> ListResult = new ArrayList<Gasto>();
		for(int i=0; i<result.size(); i++){
			Object[] obj = (Object[]) result.get(i);
			
			
			Agencia agencia = new Agencia();
			agencia.setId(((BigDecimal)obj[1]).intValue());
			agencia.setDenominacion(obj[2].toString());
			agencia.setNombreCorto(obj[21].toString());
						
			Liquidacion liquidacion= new Liquidacion();
			liquidacion.setNombreUsuario(obj[3].toString());
			liquidacion.setFechaLiquidacion(((Date) obj[4]));
			liquidacion.setEstadoLiquidacion(((BigDecimal)obj[20]).intValue());
						
			DetalleLiquidacion detalleLiquidacion = new DetalleLiquidacion();
			detalleLiquidacion.setId(((BigDecimal)obj[13]).longValue());
			
			if (obj[14]!=null)
				detalleLiquidacion.setFechaInsercion(((Date)obj[14]));
			if (obj[15] !=null)
				detalleLiquidacion.setUsuarioInsercion(obj[15].toString());
			if (obj[16] !=null)
				detalleLiquidacion.setIpInsercion(obj[16].toString());
			
			
			TipoGasto tipoGasto=new TipoGasto();
			tipoGasto.setId(((BigDecimal)obj[5]).intValue());
			tipoGasto.setDenominacion(obj[6].toString());
			tipoGasto.setTipoOperacion(((BigDecimal)obj[22]).intValue());
			tipoGasto.setNombreCorto(obj[23]!=null?obj[23].toString():"");
			
			Gasto gasto = new Gasto();
			gasto.setId(((BigDecimal) obj[0]).intValue());
			gasto.setMonto(((BigDecimal)obj[7]).doubleValue());
			if (obj[8] !=null)
				gasto.setNumeroDocumento(obj[8].toString());
			if (obj[9] !=null)
				gasto.setCodigoBus(obj[9].toString());
			if (obj[10] !=null)
				gasto.setNombrePiloto(obj[10].toString());
			if (obj[11] !=null)
				gasto.setConsignado(obj[11].toString());
			if (obj[12] !=null)
				gasto.setObservacion(obj[12].toString());
			if (obj[17] !=null)
				gasto.setFechaInsercion(((Date)obj[17]));
			if (obj[18] !=null)
				gasto.setUsuarioInsercion(obj[18].toString());
			if (obj[19] !=null)
				gasto.setIpInsercion(obj[19].toString());	
			
			gasto.setAgencia(agencia);
			gasto.setLiquidacion(liquidacion);
			gasto.setTipoGasto(tipoGasto);
			gasto.setDetalleLiquidacion(detalleLiquidacion);
			
			ListResult.add(gasto);
		}
		return ListResult;
	}

	@Override
	public List<Gasto> buscarGastoLiqOficina(String fechaLiquidacion,String usuario) {
		String sql="SELECT g.gasto_id, tg.tipgas_id, g.c_numdoc, g.n_monto, g.c_nompil, g.c_codbus, g.c_consignado, g.c_observacion, "+
							"g.audfecins, g.audusuins, g.audipinse,  "+
							"lqo.liqofi_id, lqo.d_fecliq, tg.c_denominacion NombreGasto "+
					"FROM vrtgasto g "+
					"INNER JOIN vrtliqofi lqo ON (lqo.gasto_id=g.gasto_id) "+
					"INNER JOIN vrmtipgas tg ON (tg.tipgas_id=g.tipgas_id) "+
					"WHERE lqo.d_fecliq = to_date('"+fechaLiquidacion+"', 'dd/MM/yyyy') AND lqo.c_estreg='A' AND g.c_estreg='A' "+
					"AND g.audusuins ='"+usuario+"' ";
		
		log.info(sql);
		
		List<?> result = getSession().createSQLQuery(sql).list();
		List<Gasto> ListResult = new ArrayList<Gasto>();
		for(int i=0; i<result.size(); i++){
			Object[] obj = (Object[]) result.get(i);
				
			Gasto gasto=new Gasto();
			TipoGasto tipoGasto= new TipoGasto();
			tipoGasto.setId(((BigDecimal)obj[1]).intValue());
			tipoGasto.setDenominacion(obj[13].toString());
			
			gasto.setId(((BigDecimal) obj[0]).intValue());
			gasto.setTipoGasto(tipoGasto);
			gasto.setNumeroDocumento(obj[2] !=null? obj[2].toString(): "");
			gasto.setMonto(obj[3]!=null? ((BigDecimal)obj[3]).doubleValue(): 0);
			gasto.setNombrePiloto(obj[4]!=null? obj[4].toString(): "");
			gasto.setCodigoBus(obj[5]!=null?obj[5].toString(): "");
			gasto.setConsignado(obj[6]!=null?obj[6].toString(): "");		
			gasto.setObservacion(obj[7]!=null? obj[7].toString(): "");
			gasto.setFechaInsercion(obj[8]!=null?(Date)obj[8]: null);
			gasto.setUsuarioInsercion(obj[9]!=null? obj[9].toString(): "");
			gasto.setIpInsercion(obj[10] !=null? obj[10].toString(): "");
			
			LiquidacionOficina  liquidacionOficina=new LiquidacionOficina();
			liquidacionOficina.setId(((BigDecimal)obj[11]).longValue());
			liquidacionOficina.setFechaLiquidacion((Date)obj[12]);
			
			gasto.setLiquidacionOficina(liquidacionOficina);
			
			ListResult.add(gasto);
		}
		
		return ListResult;
	}

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.model.dao.GastoDAO#BuscarTotalGastos(java.lang.String, java.lang.Integer, java.lang.Integer)
	 */
	@Override
	public Double BuscarTotalGastos(String fecha, Integer idUsuario,Integer idAgencia) throws Exception {
		// TODO Auto-generated method stub
		
		String sql="SELECT SUM(g.n_monto) AS totalGastos "+
					"FROM vrtgasto g  "+
					"INNER JOIN vrtdetliq dlq ON (dlq.gasto_id=g.gasto_id) "+
					"INNER JOIN vrtliquidacion lq ON (lq.liquidacion_id=dlq.liquidacion_id) "+
					"WHERE lq.agencia_id="+idAgencia+" AND lq.usuario_id="+idUsuario+" AND to_char(lq.d_fecliq,'dd/mm/yyyy')=to_date('"+fecha+"','"+Constantes.DATE_FORMAT+"') ";
		
		log.info(sql);
		List<?> obj = getSession().createSQLQuery(sql).list();
		if(obj.get(0)!=null)
			return ((BigDecimal)obj.get(0)).doubleValue();
		else
			return 0.00;
	}

	/* (non-Javadoc)
	 * @see com.cystesoft.vyrbus.model.dao.GastoDAO#obtenerGastosByLiquidacion(java.lang.String, java.lang.Integer, java.lang.Integer)
	 */
	@Override
	public List<Gasto> obtenerGastosByLiquidacion(String fechaLiquidacion, Integer idAgencia, Integer idUsuario, Integer isIngreso, boolean groupByObs) {
		String sql ="";
		if(groupByObs) {
			sql = "SELECT tg.tipgas_id, tg.c_denominacion, COUNT(g.n_monto) AS CANTIDAD,  SUM(g.n_monto) AS MONTO, g.c_observacion, g.c_numdoc "
					+ "FROM vrtgasto g "
					+ "INNER JOIN vrmtipgas tg ON (tg.tipgas_id=g.tipgas_id) "
					+ "INNER JOIN (SELECT dl.gasto_id FROM vrtdetliq dl "
					+ "INNER JOIN vrtliquidacion l On (l.liquidacion_id=dl.liquidacion_id) "
					+ "WHERE l.d_fecliq=to_date('"+fechaLiquidacion+"','dd/MM/yyyy') AND dl.gasto_id is not null AND l.agencia_id="+idAgencia+" AND l.usuario_id="+idUsuario+" AND l.c_estreg='A') dl ON (dl.gasto_id=g.gasto_id) "
					+ "WHERE g.c_estreg='A' AND tg.n_tipope="+ isIngreso + " "
					+ "GROUP BY tg.tipgas_id, tg.c_denominacion, g.c_observacion, g.c_numdoc "
					+ "ORDER BY tg.c_denominacion ";
		}else {
			sql = "SELECT tg.tipgas_id, tg.c_denominacion, COUNT(g.n_monto) AS CANTIDAD,  SUM(g.n_monto) AS MONTO "
					+ "FROM vrtgasto g "
					+ "INNER JOIN vrmtipgas tg ON (tg.tipgas_id=g.tipgas_id) "
					+ "INNER JOIN (SELECT dl.gasto_id FROM vrtdetliq dl "
					+ "INNER JOIN vrtliquidacion l On (l.liquidacion_id=dl.liquidacion_id) "
					+ "WHERE l.d_fecliq=to_date('"+fechaLiquidacion+"','dd/MM/yyyy') AND dl.gasto_id is not null AND l.agencia_id="+idAgencia+" AND l.usuario_id="+idUsuario+" AND l.c_estreg='A') dl ON (dl.gasto_id=g.gasto_id) "
					+ "WHERE g.c_estreg='A' AND tg.n_tipope="+ isIngreso + " "
					+ "GROUP BY tg.tipgas_id, tg.c_denominacion "
					+ "ORDER BY tg.c_denominacion ";	
		}
		
	
		
		log.info(sql);
		List<?> result = getSession().createSQLQuery(sql).list();
		List<Gasto> lstResult = new ArrayList<Gasto>(); 
		for(int i=0; i<result.size(); i++) {
			Object[] obj = (Object[]) result.get(i);
			Gasto gasto = new Gasto();
			TipoGasto tipoGasto = new TipoGasto();
			tipoGasto.setId(((BigDecimal)obj[0]).intValue());
			tipoGasto.setDenominacion(obj[1].toString());
			gasto.setTipoGasto(tipoGasto);
			gasto.setCantidad(((BigDecimal)obj[2]).intValue());
			gasto.setMonto(((BigDecimal)obj[3]).doubleValue());
			if(groupByObs) {
				gasto.setObservacion(obj[4]!=null?obj[4].toString():"");
				gasto.setNumeroDocumento(obj[5]!=null?obj[5].toString():"");
			}
			lstResult.add(gasto);			
		}
		return lstResult;
	}

	/* (non-Javadoc)
	 * @see com.cystesoft.vyrbus.model.dao.GastoDAO#buscarGastosIngresos(java.lang.String, java.lang.String, java.lang.Integer)
	 */
	@Override
	public List<Gasto> buscarGastosIngresos(String fechaInicio, String fechaFin, Integer tipoOperacion)
			throws Exception {
		String sql="SELECT tg.n_tipope, tg.c_Denominacion tipoGasto, lq.d_Fecliq fecha, ag.agencia_id, ag.c_Denominacion oficina, u.usuario_id, u.c_apepat, u.c_apemat, u.c_nombre, gt.n_monto "
				+ "       ,gt.c_numdoc gasto_doct, gt.c_nompil gasto_piloto, gt.c_codbus gasto_bus, gt.c_consignado gasto_consignado, gt.c_observacion gasto_obsv, gt.gasto_id "
				+ "FROM vrtgasto gt "
				+ "  INNER JOIN vrmtipgas tg ON (tg.tipgas_id=gt.tipgas_id)   "
				+ "  INNER JOIN vrtdetliq dlq ON (dlq.gasto_id=gt.gasto_id) "
				+ "  INNER JOIN vrtliquidacion lq ON (lq.liquidacion_id=dlq.liquidacion_id) "
				+ "  INNER JOIN vrmagencia ag ON (ag.agencia_id=lq.agencia_id) "
				+ "  INNER JOIN vrmusuario u ON (u.usuario_id=lq.usuario_id) "
				+ "WHERE lq.d_fecliq BETWEEN '"+fechaInicio+"' AND '"+fechaFin+"' "
				+ "  AND tg.n_tipope =  NVL("+tipoOperacion+", tg.n_tipope) "
				+ "  AND lq.c_Estreg='A' AND dlq.c_estreg='A' AND gt.c_estreg='A' "
				+ "ORDER BY ag.c_Denominacion, u.c_apepat, u.c_apemat, u.c_nombre ";
		log.info(sql);
		List<?> result = getSession().createSQLQuery(sql).list();
		List<Gasto> lstResult = new ArrayList<Gasto>(); 
		for(int i=0; i<result.size(); i++) {
			Object[] obj = (Object[]) result.get(i);
			
			TipoGasto tipoGasto= new TipoGasto();
			tipoGasto.setTipoOperacion(((BigDecimal)obj[0]).intValue());
			tipoGasto.setDenominacion(obj[1].toString());
			
			Agencia agencia= new Agencia();
			agencia.setId(((BigDecimal)obj[3]).intValue());
			agencia.setDenominacion(obj[4].toString());
			
			Usuario usuario= new Usuario();
			usuario.setId(((BigDecimal)obj[5]).intValue());
			usuario.setApellidoPaterno(obj[6].toString());
			usuario.setApellidoMaterno(obj[7]!=null?obj[7].toString():"");
			usuario.setNombre(obj[8].toString());
			
			Gasto gasto= new Gasto();
			gasto.setId(((BigDecimal)obj[15]).intValue());
			gasto.setFecha((Date)obj[2]);
			gasto.setMonto(((BigDecimal)obj[9]).doubleValue());
			gasto.setNumeroDocumento(obj[10]!=null?obj[10].toString():"");
			gasto.setNombrePiloto(obj[11]!=null?obj[11].toString():"");
			gasto.setCodigoBus(obj[12]!=null?obj[12].toString():"");
			gasto.setConsignado(obj[13]!=null?obj[13].toString():"");
			gasto.setObservacion(obj[14]!=null?obj[14].toString():"");
			gasto.setTipoGasto(tipoGasto);
			gasto.setAgencia(agencia);
			gasto.setUsuario(usuario);
			
			lstResult.add(gasto);			
		}
		
		return lstResult;
	}
}
