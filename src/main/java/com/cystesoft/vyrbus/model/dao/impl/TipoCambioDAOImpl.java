/**
 * Proyecto		: SISVYR
 * Sistema		: Sistema de Ventas y Reservas
 * Descripción	: 
 * Autor		: José Abanto
 * Fecha		: 07/08/2015
 * Hora			: 12:47:56
 */
package com.cystesoft.vyrbus.model.dao.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.TreeMap;

import com.cystesoft.vyrbus.model.bean.TipoCambio;
import com.cystesoft.vyrbus.model.bean.TipoMoneda;
import com.cystesoft.vyrbus.model.dao.TipoCambioDAO;
import com.cystesoft.vyrbus.service.util.Constantes;

/**
 * @author jabanto
 *
 */
@SuppressWarnings("unchecked")
public class TipoCambioDAOImpl extends GenericDAOImpl implements TipoCambioDAO{

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.model.dao.TipoCambioDAO#buscarPorEstadoRegistro(java.lang.String, java.lang.String)
	 */
	@Override
	public ArrayList<TipoCambio> buscarPorEstadoRegistro(String estado,String criterioOrden)throws Exception {
		// TODO Auto-generated method stub
		return (ArrayList<TipoCambio>) super.findByEstadoRegistro(TipoCambio.class, estado, criterioOrden);
	}

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.model.dao.TipoCambioDAO#buscarPorX(java.util.TreeMap, java.util.List)
	 */
	@Override
	public ArrayList<TipoCambio> buscarPorX(TreeMap<String, Object> criteriosBusqueda,List<String> criteriosOrdenar)throws Exception {
		// TODO Auto-generated method stub
		return (ArrayList<TipoCambio>) super.findByX(TipoCambio.class, criteriosBusqueda, criteriosOrdenar);
	}

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.model.dao.TipoCambioDAO#buscarPorId(java.lang.Long)
	 */
	@Override
	public TipoCambio buscarPorId(Long id)throws Exception {
		// TODO Auto-generated method stub
		return (TipoCambio) super.findById(TipoCambio.class, id);
	}

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.model.dao.TipoCambioDAO#guardar(com.tepsa.sisvyr.model.bean.TipoCambio)
	 */
	@Override
	public void guardar(TipoCambio tipoCambio)throws Exception {
		// TODO Auto-generated method stub
		super.save(tipoCambio);
	}

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.model.dao.TipoCambioDAO#actualizar(com.tepsa.sisvyr.model.bean.TipoCambio)
	 */
	@Override
	public void actualizar(TipoCambio tipoCambio)throws Exception {
		// TODO Auto-generated method stub
		super.update(tipoCambio);
	}

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.model.dao.TipoCambioDAO#buscarUltimoTipoCambio(java.lang.Integer)
	 */
	@Override
	public TipoCambio buscarUltimoTipoCambio(Integer idTipoMoneda)throws Exception {
		// TODO Auto-generated method stub
		String sql="SELECT MAX(tc.tipCam_id)tipoCambio_id FROM VRTTIPCAM tc WHERE tc.tipmon_id="+idTipoMoneda+" AND tc.c_estreg='A'";
		log.info(sql);
		
		Object object=getSession().createSQLQuery(sql).uniqueResult();
		TipoCambio tipoCambio=null;
		if(object!=null){
			Integer idTipoCambio=((BigDecimal) object).intValue();
			tipoCambio=buscarPorId(idTipoCambio.longValue());
		}
		
		return tipoCambio;
	}

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.model.dao.TipoCambioDAO#buscarTiposCambio(java.lang.String, java.lang.String, java.lang.Integer)
	 */
	@Override
	public List<TipoCambio> buscarTiposCambio(String fechaInicio,String fechaFin, Integer idTipoMoneda) throws Exception {
		String sql="SELECT tc.tipcam_id, tc.d_fec fecha, tm.tipmon_id, tm.c_denominacion tipoMoneda "
				       + ",tc.n_tipcam tipoCambio, tc.audfecins "
				 + "FROM VRTTIPCAM tc "
				   + "INNER JOIN VRMTIPMON tm ON (tm.tipmon_id=tc.tipmon_id) "
				 + "WHERE tc.d_Fec BETWEEN '"+fechaInicio+"' AND '"+fechaFin+"' "
				   + "AND tc.c_Estreg='"+Constantes.VALUE_ACTIVO+"' "
				   + "AND tc.tipmon_id=NVL(NULL,tc.tipmon_id) "
				 + "ORDER BY tc.d_fec, tm.c_denominacion";
		log.info(sql);
		
		List<Object>result=getSession().createSQLQuery(sql).list();
		
		List<TipoCambio>lstTipoambio=new ArrayList<TipoCambio>();
		for(int x=0;x<result.size();x++){
			Object[] obj=(Object[]) result.get(x);
			TipoCambio tipoCambio=new TipoCambio();
			tipoCambio.setId(((BigDecimal)obj[0]).intValue());
			tipoCambio.setFecha(((Date)obj[1]));
			
			TipoMoneda tipoMoneda=new TipoMoneda();
			tipoMoneda.setId(((BigDecimal)obj[2]).intValue());
			tipoMoneda.setDenominacion(obj[3].toString());
			
			tipoCambio.setTipoMoneda(tipoMoneda);
			tipoCambio.setTipoCambio(((BigDecimal)obj[4]).doubleValue());
			tipoCambio.setFechaInsercion(obj[5]!=null?(Date)obj[5]:null);
			
			lstTipoambio.add(tipoCambio);
		}
		
		return lstTipoambio;
	}

}
