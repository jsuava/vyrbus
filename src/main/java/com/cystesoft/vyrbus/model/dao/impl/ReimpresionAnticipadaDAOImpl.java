/**
 * Proyecto		: SISVYR
 * Sistema		: Sistema de Ventas y Reservas
 * Descripción	: 
 * Autor		: José Abanto
 * Fecha		: 05/07/2016
 * Hora			: 15:12:12
 */
package com.cystesoft.vyrbus.model.dao.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.TreeMap;

import com.cystesoft.vyrbus.model.bean.Agencia;
import com.cystesoft.vyrbus.model.bean.CanalVenta;
import com.cystesoft.vyrbus.model.bean.ReimpresionAnticipada;
import com.cystesoft.vyrbus.model.dao.ReimpresionAnticipadaDAO;
import com.cystesoft.vyrbus.service.util.Constantes;

/**
 * @author jabanto
 *
 */
public class ReimpresionAnticipadaDAOImpl extends GenericDAOImpl implements ReimpresionAnticipadaDAO{

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.model.dao.ReimpresionAnticipadaDAO#buscarPorID(java.lang.Long)
	 */
	@Override
	public ReimpresionAnticipada buscarPorID(Long id) throws Exception {
		// TODO Auto-generated method stub
		return (ReimpresionAnticipada) super.findById(ReimpresionAnticipada.class, id);
	}

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.model.dao.ReimpresionAnticipadaDAO#buscarPorEstadoRegistro(java.lang.String, java.lang.String)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public ArrayList<ReimpresionAnticipada> buscarPorEstadoRegistro(String estado, String criterioOrden) throws Exception {
		// TODO Auto-generated method stub
		return (ArrayList<ReimpresionAnticipada>) super.findByEstadoRegistro(ReimpresionAnticipada.class, estado, criterioOrden);
	}

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.model.dao.ReimpresionAnticipadaDAO#buscarPorX(java.util.TreeMap, java.util.List)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public ArrayList<ReimpresionAnticipada> buscarPorX(TreeMap<String, Object> criteriosBusqueda,List<String> criteriosOrdenar) {
		// TODO Auto-generated method stub
		return (ArrayList<ReimpresionAnticipada>) super.findByX(ReimpresionAnticipada.class, criteriosBusqueda, criteriosOrdenar);
	}

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.model.dao.ReimpresionAnticipadaDAO#guardar(com.tepsa.sisvyr.model.bean.ReimpresionAnticipada)
	 */
	@Override
	public void guardar(ReimpresionAnticipada reimpresionAnticipada)throws Exception {
		// TODO Auto-generated method stub
		super.save(reimpresionAnticipada);
	}

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.model.dao.ReimpresionAnticipadaDAO#actualizar(com.tepsa.sisvyr.model.bean.ReimpresionAnticipada)
	 */
	@Override
	public void actualizar(ReimpresionAnticipada reimpresionAnticipada)throws Exception {
		// TODO Auto-generated method stub
		super.update(reimpresionAnticipada);
	}

	/* 
	 * (non-Javadoc)
	 * @see com.tepsa.sisvyr.model.dao.ReimpresionAnticipadaDAO#tieneAutorizacionReimpresion(java.lang.Integer, java.lang.Integer)
	 */
	@Override
	public boolean tieneAutorizacionReimpresion(Integer agenciaID, Integer canalVentaID) throws Exception {
		boolean isAutorizado=false;
		
		TreeMap<String, Object>criteriosBusqueda=new TreeMap<>();
		criteriosBusqueda.put("agencia", new Agencia(agenciaID));
		criteriosBusqueda.put("canalVenta", new CanalVenta(canalVentaID));
		criteriosBusqueda.put("estadoRegistro", Constantes.VALUE_ACTIVO);
		
		List<ReimpresionAnticipada> result= buscarPorX(criteriosBusqueda, null);
		for(ReimpresionAnticipada reimpresionAnticipada : result){
			long milFechaActual= new Date().getTime();
			long milFechaAutorizacion=reimpresionAnticipada.getFechaEmision().getTime()+(Constantes.MILISEGUNDOS_X_MINUTO*reimpresionAnticipada.getTiempoReimpresion());
			
			if(milFechaAutorizacion>=milFechaActual){
				isAutorizado=true;
				break;
			}
		}
		
		return isAutorizado;
	}

	

}
