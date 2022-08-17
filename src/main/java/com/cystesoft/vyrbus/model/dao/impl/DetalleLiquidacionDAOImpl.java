package com.cystesoft.vyrbus.model.dao.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import com.cystesoft.vyrbus.model.bean.DetalleLiquidacion;
import com.cystesoft.vyrbus.model.bean.TipoComprobante;
import com.cystesoft.vyrbus.model.bean.TipoFormaPago;
import com.cystesoft.vyrbus.model.bean.TipoMovimiento;
import com.cystesoft.vyrbus.model.dao.DetalleLiquidacionDAO;
import com.cystesoft.vyrbus.service.util.Constantes;

/**
 *
 * @author JABANTO
 *
 */
public  class DetalleLiquidacionDAOImpl extends GenericDAOImpl implements DetalleLiquidacionDAO {

	/*
	 * (non-Javadoc)
	 * @see com.tepsa.sisvyr.model.dao.DetalleLiquidacionDAO#guarda(com.tepsa.sisvyr.model.bean.DetalleLiquidacion)
	 */
	@Override
	public void guarda(DetalleLiquidacion detalleLiquidacion) throws Exception {
		super.save(detalleLiquidacion);

	}

	/*
	 * (non-Javadoc)
	 * @see com.tepsa.sisvyr.model.dao.DetalleLiquidacionDAO#actualizar(com.tepsa.sisvyr.model.bean.DetalleLiquidacion)
	 */
	@Override
	public void actualizar(DetalleLiquidacion detalleLiquidacion)throws Exception {
		super.update(detalleLiquidacion);

	}

	@Override
	public void delete(Long id) {
		super.deleted(DetalleLiquidacion.class, id);

	}

	/*
	 * (non-Javadoc)
	 * @see com.tepsa.sisvyr.model.dao.DetalleLiquidacionDAO#buscarVentas(java.lang.String, java.lang.Integer, java.lang.Integer)
	 */
	@Override
	public List<DetalleLiquidacion> buscarVentasALiquidar(String fechaLiquidacion, Integer idAgencia,Integer idUsuario) {
//		String sql="SELECT vp.tipforpag_id, tfp.c_denominacion as TipoFormaPago,vp.tipmov_id, cnv.c_denominacion as TipoMovimiento, "+
//					       "vp.tipcom_id, tcp.c_denominacion as TopoComprobante, sum(vp.n_imppag) as Total, count(vp.venpas_id) as Cantidad "+
//				   "FROM vrtvenpas vp " +
//				   "INNER JOIN vrmtipforpag tfp ON  (tfp.tipforpag_id=vp.tipforpag_id) "+
//				   "INNER JOIN vrmtipmov cnv ON (cnv.tipmov_id=vp.tipmov_id) "+
//				   "INNER JOIN vrmtipcom tcp ON (tcp.tipcom_id=vp.tipcom_id) "+
//				   "WHERE vp.d_fecliq = to_date('"+fechaLiquidacion+"','dd/MM/yyyy') "+
//				   "AND vp.agencia_id="+idAgencia+" AND vp.usuario_id="+idUsuario+"  "+
//				   "AND vp.tipmov_id not in ("+Constantes.ID_TIPMOV_ANULACION+","+Constantes.ID_TIPMOV_ANULACION_SISTEMA+") AND vp.c_tiptra=1 "+
//				   "GROUP BY vp.tipforpag_id, vp.tipmov_id, vp.tipcom_id, tfp.c_denominacion, cnv.c_denominacion, tcp.c_denominacion";

		String sql="SELECT  CASE WHEN vp.forpag_id="+Constantes.ID_FORPAG_CREDITO+" AND vp.tipforpag_id!="+Constantes.ID_TIPFORPAG_CANJE_PUBLICITARIO+" THEN 4 "+
					            "ELSE vp.tipforpag_id END tipforpag_id "+
					       ",CASE WHEN vp.forpag_id="+Constantes.ID_FORPAG_CREDITO+" AND vp.tipforpag_id!="+Constantes.ID_TIPFORPAG_CANJE_PUBLICITARIO+" THEN 'CREDITO' "+
					            "ELSE tfp.c_denominacion END TipoFormaPago "+
					       ",vp.tipmov_id "+
					       ",cnv.c_denominacion as TipoMovimiento "+
					       ",vp.tipcom_id "+
					       ",tcp.c_denominacion as TopoComprobante "+
//					     + ",sum(vp.n_imppag) as Total "+
					       ",sum(decode(nvl(vp.n_imppagdif,0),0,vp.n_imppag,n_imppagdif)) as Total  "+
					       ",count(vp.venpas_id) as Cantidad "+
					"FROM vrtvenpas vp  "+
					  "INNER JOIN vrmtipforpag tfp ON  (tfp.tipforpag_id=vp.tipforpag_id) "+
					  "INNER JOIN vrmtipmov cnv ON (cnv.tipmov_id=vp.tipmov_id) "+
					  "INNER JOIN vrmtipcom tcp ON (tcp.tipcom_id=vp.tipcom_id) "+
					"WHERE vp.d_fecliq = to_date('"+fechaLiquidacion+"','dd/MM/yyyy')  "+
					  "AND (vp.c_rucclicre IS NULL OR vp.c_rucclicre NOT IN ('20100227461', '20102427891')) "+ //-->No pool (Ventas que realiza otro operador en tepsa) - 03/12/2016 - jabanto
					  "AND vp.agencia_id=" +idAgencia+" "+
					  "AND vp.usuario_id="+idUsuario+" "+
					  "AND vp.tipmov_id not in ("+Constantes.ID_TIPMOV_ANULACION+","+Constantes.ID_TIPMOV_ANULACION_SISTEMA+") AND vp.c_tiptra IN ('1','3','5','6') "+
					"GROUP BY  "+
					        "CASE WHEN vp.forpag_id="+Constantes.ID_FORPAG_CREDITO+" AND vp.tipforpag_id!="+Constantes.ID_TIPFORPAG_CANJE_PUBLICITARIO+" THEN 4 "+
					        	 "ELSE vp.tipforpag_id END "+
					       ",CASE WHEN vp.forpag_id="+Constantes.ID_FORPAG_CREDITO+" AND vp.tipforpag_id!="+Constantes.ID_TIPFORPAG_CANJE_PUBLICITARIO+" THEN 'CREDITO' "+
					             "ELSE tfp.c_denominacion END "+
					        ",vp.tipmov_id,cnv.c_denominacion "+
					        ",vp.tipcom_id,tcp.c_denominacion  ";

		List<?> result = getSession().createSQLQuery(sql).list();
		List<DetalleLiquidacion> ListResult = new ArrayList<>();
		for(int i=0; i<result.size(); i++){
			Object[] obj = (Object[]) result.get(i);

			TipoFormaPago  tipoFormaPago = new TipoFormaPago();
			tipoFormaPago.setId(((BigDecimal) obj[0]).intValue());
			tipoFormaPago.setDenominacion(obj[1].toString());

			TipoMovimiento condicionVenta = new TipoMovimiento();
			condicionVenta.setId(((BigDecimal)obj[2]).intValue());
			condicionVenta.setDenominacion(obj[3].toString());

			TipoComprobante tipoComprobante = new TipoComprobante();
			tipoComprobante.setId(((BigDecimal)obj[4]).intValue());
			tipoComprobante.setDenominacion(obj[5].toString());

			DetalleLiquidacion detalleLiquidacion = new DetalleLiquidacion();
			detalleLiquidacion.setTotal(((BigDecimal)obj[6]).doubleValue());
			detalleLiquidacion.setCantidad(((BigDecimal)obj[7]).intValue());
			detalleLiquidacion.setTipoFormaPago(tipoFormaPago);
			detalleLiquidacion.setTipoMovimiento(condicionVenta);
			detalleLiquidacion.setTipoComprobante(tipoComprobante);

			if(condicionVenta.getId().intValue()==Constantes.ID_TIPMOV_DEVOLUCION && tipoComprobante.getId().intValue()==Constantes.ID_TIPCOM_BOLETO_VIAJE){
				ListResult.add(detalleLiquidacion);
			}else if (condicionVenta.getId().intValue()!=Constantes.ID_TIPMOV_DEVOLUCION){
				ListResult.add(detalleLiquidacion);
			}
		}

		return ListResult;
	}

	/*
	 * (non-Javadoc)
	 * @see com.tepsa.sisvyr.model.dao.DetalleLiquidacionDAO#actualizaFechaLiqVentas(java.lang.String, java.lang.Integer, java.lang.Integer)
	 */
	@Override
	public void actualizaIdLiquidacionVentasLiquidadas(Long idLiquidacion, String fechaLiquidacion, Integer idAgencia,Integer idUsuario) {
		String sql ="UPDATE vrtvenpas SET vrtvenpas.liquidacion_id="+idLiquidacion+" "+
					"WHERE vrtvenpas.venpas_id in ( SELECT vp.venpas_id "+
					                               "FROM vrtvenpas vp "+
					                               "WHERE vp.d_fecliq = to_date('"+fechaLiquidacion+"','dd/MM/yyyy') "+
					                               "AND vp.agencia_id="+idAgencia+" AND vp.usuario_id="+idUsuario+"  "+
					                               "AND vp.c_tiptra IN ('1','3','4','5','6')) ";
		 getSession().createSQLQuery(sql).executeUpdate();

	}

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.model.dao.DetalleLiquidacionDAO#deleteXidLiquidacion(java.lang.Long)
	 */
	@Override
	public void deleteXidLiquidacion(Long idLiquidacion) throws Exception {
		String sql="DELETE FROM VRTDETLIQ dlq WHERE liquidacion_ID="+idLiquidacion+" AND gasto_id IS NULL ";
		log.info("ELIMANDO EL DETALLE LIQUIDACION: "+sql);
		getSession().createSQLQuery(sql).executeUpdate();
	}


}
