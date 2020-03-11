/**
 * Proyecto		: SISVYR
 * Sistema		: Sistema de Ventas y Reservas
 * Descripción	: 
 * Autor		: José Sullo Avalos
 * Fecha		: 31/08/2012
 */
package com.cystesoft.vyrbus.model.dao.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.TreeMap;

import com.cystesoft.vyrbus.model.bean.Pasajero;
import com.cystesoft.vyrbus.model.bean.PasajeroFrecuente;
import com.cystesoft.vyrbus.model.dao.PasajeroFrecuenteDAO;
import com.cystesoft.vyrbus.service.util.Constantes;

/**
 *
 * @author JABANTO
 * @since JDK1.6
 */
@SuppressWarnings("unchecked")
public class PasajeroFrecuenteDAOImpl extends GenericDAOImpl implements PasajeroFrecuenteDAO{
	@Override
	public ArrayList<PasajeroFrecuente> buscarPorEstadoRegistro(String estado, String criterioOrden) {
		return (ArrayList<PasajeroFrecuente>) super.findByEstadoRegistro(PasajeroFrecuente.class, estado, criterioOrden);
	}
	@Override
	public ArrayList<PasajeroFrecuente> buscarPorX(TreeMap<String, Object> criteriosBusqueda,List<String> criteriosOrdenar) {
		return (ArrayList<PasajeroFrecuente>) super.findByX(PasajeroFrecuente.class, criteriosBusqueda, criteriosOrdenar);
	}
	@Override
	public PasajeroFrecuente buscarPorId(Long id) {
		return (PasajeroFrecuente) super.findById(PasajeroFrecuente.class, id);
	}
	@Override
	public void guardar(PasajeroFrecuente pasajeroFrecuente) {
		super.save(pasajeroFrecuente);
	}
	@Override
	public void actualizar(PasajeroFrecuente pasajeroFrecuente) {
		super.update(pasajeroFrecuente);
	}
	@Override
	public void inactivar(Long id) {
		super.inactivate(PasajeroFrecuente.class, id);
	}
	/*
	 * (non-Javadoc)
	 * @see com.tepsa.sisvyr.model.dao.PasajeroFrecuenteDAO#buscarPaxFree(java.lang.Long, java.lang.Integer)
	 */
	@Override
	public PasajeroFrecuente buscarPaxFree(Long idPasajero, Integer estado)throws Exception{
		String hql = "FROM PasajeroFrecuente pf WHERE pf.pasajero.id="+idPasajero+" AND pf.estado="+estado;
		
		log.info(hql);
		
		PasajeroFrecuente paxfree = (PasajeroFrecuente)getSession().createQuery(hql).uniqueResult();
		return paxfree;
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.tepsa.sisvyr.model.dao.PasajeroFrecuenteDAO#buscarPaxFree(java.lang.Long)
	 */
	@Override
	public PasajeroFrecuente buscarPaxFree(Long idPasajero)throws Exception{
		String hql = "FROM PasajeroFrecuente pf WHERE pf.pasajero.id="+idPasajero;
		
		log.info(hql);
		
		PasajeroFrecuente paxfree = (PasajeroFrecuente)getSession().createQuery(hql).uniqueResult();
		return paxfree;
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.tepsa.sisvyr.model.dao.PasajeroFrecuenteDAO#buscarMaxNumTarjeta()
	 */
	@Override
	public PasajeroFrecuente buscarMaxNumTarjeta() throws Exception {
//		String sql = "SELECT MAX(C_NUMTAR) FROM vrtpaxfree";
		String sql = "SELECT  PF.C_NUMTAR FROM VRTPAXFREE PF WHERE PF.PAXFRE_ID=(SELECT MAX(PAXFRE_ID)FROM vrtpaxfree) ";
		log.info(sql);
		
		List<?> result = getSession().createSQLQuery(sql).list();
		PasajeroFrecuente paxfree= new PasajeroFrecuente();
		String numeroTrajeta=result.get(0).toString();
		
		Integer pos=numeroTrajeta.indexOf("-");
		numeroTrajeta=numeroTrajeta.substring(pos+1, numeroTrajeta.length());
		pos=numeroTrajeta.indexOf("-");
		numeroTrajeta=numeroTrajeta.substring(pos+1, numeroTrajeta.length());
		paxfree.setNumeroTarjeta(String.valueOf(Integer.valueOf(numeroTrajeta)+1));	
		
		return paxfree;
	} 
	
		
	/*
	 * (non-Javadoc)
	 * @see com.tepsa.sisvyr.model.dao.PasajeroFrecuenteDAO#buscarPuntos(java.lang.Long)
	 */
	@Override
	public PasajeroFrecuente buscarPaxFreeAndPuntos(Long idPasajero, Integer estado) throws Exception{
		/* Busca Paxfree*/
		String hql = "FROM PasajeroFrecuente pf WHERE pf.pasajero.estadoRegistro='A' AND pf.pasajero.id="+idPasajero; //Constantes.TRUE_VALUE;
		if(estado!=null)
			hql+=" AND estado="+estado;
		
		log.info(hql);
		PasajeroFrecuente paxfree = (PasajeroFrecuente)getSession().createQuery(hql).uniqueResult();
		
		/* Busca puntos del paxfree */
		if(paxfree!=null){
			String sql="SELECT SUM(Acumulados)Acumulados "+
						      ",SUM(Utilizados)Utilizados "+
						      ",SUM(Saldo)Saldo "+
						"FROM( "+
						    "SELECT CASE WHEN pp.d_feccan IS NOT NULL THEN SUM(pp.n_punacu)ELSE 0 END  AS Utilizados "+
						          ",DECODE(pp.d_feccan,NULL,(DECODE(pp.d_fecanu, NULL,SUM(pp.n_punacu))),0 ) AS Saldo "+
						          ",DECODE(pp.d_fecanu,NULL,SUM(pp.n_punacu),0) AS Acumulados "+                                                                                                                          
						    "FROM vrtpunpaxfre pp "+
						    "WHERE pp.paxfre_id="+paxfree.getId()+" AND pp.c_estReg='A' "+ 
						      "AND pp.d_feccad >='"+Constantes.FORMAT_DATE.format(new Date())+"' "+
						    "GROUP BY pp.d_feccan, pp.d_fecanu "+
						    ")Pts";
			log.info(sql);
			List<?>result= (List<?>) getSession().createSQLQuery(sql).list();
			if(result.size()>0){
				Object[] obj = (Object[])result.get(0);
				if(obj[0]!=null && obj[1]!=null){
					paxfree.setPuntosAcumulados(((BigDecimal)obj[0]).intValue());
					paxfree.setPuntosUtilizados(((BigDecimal)obj[1]).intValue());
				}else{
					paxfree.setPuntosAcumulados(0);
					paxfree.setPuntosUtilizados(0);
				}
			}else{
				paxfree.setPuntosAcumulados(0);
				paxfree.setPuntosUtilizados(0);
			}
		}
		return paxfree;
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.tepsa.sisvyr.model.dao.PasajeroFrecuenteDAO#buscarPaxfreeXNumeroDocumento(java.lang.String)
	 */
	@Override
	public PasajeroFrecuente buscarPaxfreeXNumeroDocumento(String numeroDocumento){
		String sql ="SELECT pf.paxfre_id, p.c_nomape,pf.c_numtar, p.pasajero_id, pf.n_estado "+
					"FROM vrtpaxfree pf "+
					"INNER JOIN vrmpasajero  p ON (p.pasajero_id=pf.pasajero_id) "+
					"WHERE p.c_numdoc='"+numeroDocumento+"' AND p.c_estreg='A'";
		
		log.info(sql);
		
		List<?>result= (List<?>) getSession().createSQLQuery(sql).list();
		PasajeroFrecuente pasajeroFrecuente= null;
		if(result.size()>0){
			Object[] obj = (Object[])result.get(0);
			pasajeroFrecuente= new PasajeroFrecuente();
			Pasajero pasajero= new Pasajero();
			
			pasajero.setNombresApellidos(obj[1].toString());
			pasajero.setId(((BigDecimal)obj[3]).longValue());
			pasajeroFrecuente.setId(((BigDecimal)obj[0]).longValue());
			pasajeroFrecuente.setNumeroTarjeta(obj[2].toString());
			pasajeroFrecuente.setEstado(((BigDecimal)obj[4]).intValue());
			pasajeroFrecuente.setPasajero(pasajero);
		}
		
		return pasajeroFrecuente;
	}
}
