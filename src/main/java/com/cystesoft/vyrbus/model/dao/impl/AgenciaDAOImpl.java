/**
 * Proyecto		: SISVYR
 * Sistema		: Sistema de Ventas y Reservas
 * Descripción	: 
 * Autor		: jM
 * Fecha		: 04/05/2012
 */
package com.cystesoft.vyrbus.model.dao.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

import com.cystesoft.vyrbus.model.bean.Agencia;
import com.cystesoft.vyrbus.model.dao.AgenciaDAO;
import com.cystesoft.vyrbus.service.util.Constantes;

/**
 *
 * @author jM
 * @since JDK1.6
 */
@SuppressWarnings("unchecked")
public class AgenciaDAOImpl extends GenericDAOImpl implements AgenciaDAO {

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.dao.AgenciaDAO#buscarPorEstadoRegistro(java.lang.String)
	 */
	@Override
	public ArrayList<Agencia> buscarPorEstadoRegistro(String estado, String criterioOrden) {
		return (ArrayList<Agencia>) super.findByEstadoRegistro(Agencia.class, estado, criterioOrden);
	}

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.dao.AgenciaDAO#buscarPorX(java.util.TreeMap)
	 */
	@Override
	public ArrayList<Agencia> buscarPorX(TreeMap<String, Object> criteriosBusqueda, List<String> criteriosOrdenar) {
		return (ArrayList<Agencia>) super.findByX(Agencia.class, criteriosBusqueda, criteriosOrdenar);
	}

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.dao.AgenciaDAO#buscarPorId(java.lang.Long)
	 */
	@Override
	public Agencia buscarPorId(Long id) {
		return (Agencia) super.findById(Agencia.class, id);
	}

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.dao.AgenciaDAO#guardar(com.tepsa.sisvyr.domain.Agencia)
	 */
	@Override
	public void guardar(Agencia agencia) {
		super.save(agencia);
	}

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.dao.AgenciaDAO#actualizar(com.tepsa.sisvyr.domain.Agencia)
	 */
	@Override
	public void actualizar(Agencia agencia) {
		super.update(agencia);
	}

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.dao.AgenciaDAO#inactivar(java.lang.Long)
	 */
	@Override
	public void inactivar(Long id) {
		super.inactivate(Agencia.class, id);
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.tepsa.sisvyr.model.dao.AgenciaDAO#buscarAgenciaByRucClienteCredito(java.lang.String)
	 */
	public Agencia buscarAgenciaByRucClienteCredito(String ruc)throws Exception{
		String hql = "SELECT A FROM Agencia as A JOIN A.concesionario as C WHERE C.ruc='"+ruc+"' AND A.estadoRegistro='"+Constantes.VALUE_ACTIVO+"'";
		
		log.info(hql);
		
		Agencia agencia = (Agencia)getSession().createQuery(hql).uniqueResult();
		return agencia;
	}

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.model.dao.AgenciaDAO#buscarPorX(java.lang.String, java.lang.Object[], java.util.List, java.lang.String)
	 */
	@Override
	public List<Agencia> buscarPorX(String campo, Object[] criterios, List<String> criteriosOrdenar, String estadoRegistro) throws Exception {
		return (List<Agencia>) super.findByX(Agencia.class, campo, criterios, criteriosOrdenar, estadoRegistro);
	}
	/*
	 * (non-Javadoc)
	 * @see com.tepsa.sisvyr.model.dao.AgenciaDAO#buscarAgenciaComprobantesSinBoleto(java.lang.Integer, java.lang.String)
	 */
	public List<Agencia> buscarAgenciaComprobantesSinBoleto(Integer idTipoComprobante, String fechaPartida){
		String sql = "SELECT DISTINCT(a.c_denominacion), a.agencia_id "
				+ "FROM vrtvenpas vp "
				+ "INNER JOIN (SELECT MAX(venpas_id)venpas_id, venpas_idoriginal FROM vrtvenpas GROUP BY venpas_idoriginal) max_vta "
				+ "ON max_vta.venpas_id=vp.venpas_id "
				+ "INNER JOIN vrmagencia a ON a.agencia_id=vp.agencia_id "
				+ "WHERE vp.d_fecpar>=to_date('"+fechaPartida+"', '"+Constantes.DATE_FORMAT+"') AND "
				+ "vp.c_tiptra='"+Constantes.TIPO_OPERACION_VENTA+"' "+" AND vp.c_estreg='A' AND vp.tipmov_id NOT IN (5,6,13) " ;
			if(idTipoComprobante!=null)
				sql = sql + "AND vp.tipcom_id = "+idTipoComprobante+" ";
			else 
				sql = sql + "AND vp.tipcom_id IN ("+Constantes.ID_TIPCOM_RECIBO_CAJA+","+Constantes.ID_TIPCOM_VOUCHER_AGENCIA_VIAJES+","+
						Constantes.ID_TIPCOM_VOUCHER_CORPORATIVO+") ";
			
			sql = sql + "ORDER BY a.c_denominacion ";
		
		log.info(sql);
		
		List<?> result = getSession().createSQLQuery(sql).list();
		List<Agencia> lstResult = new ArrayList<Agencia>();
		for(int i=0; i<result.size(); i++){
			Object[] obj = (Object[])result.get(i);
			Agencia agencia = new Agencia();
			agencia.setDenominacion(obj[0].toString());
			agencia.setId(((BigDecimal)obj[1]).intValue());
			lstResult.add(agencia);
		}
		return lstResult;
	}

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.model.dao.AgenciaDAO#bucarAgenciasByDetalleCorporativo()
	 */
	@Override
	public List<Agencia> buscarAgenciasByDetalleCorporativo() throws Exception {
		String hql="SELECT A FROM Agencia as A JOIN A.concesionario as C WHERE A.estadoRegistro='"+Constantes.VALUE_ACTIVO+"' ORDER BY A.denominacion ";
		
		log.info(hql);
		
		List<Agencia> lstAgencias = getSession().createQuery(hql).list();
		return lstAgencias;
	}
}