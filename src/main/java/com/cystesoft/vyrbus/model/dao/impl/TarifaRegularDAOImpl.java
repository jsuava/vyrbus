/**
 * Proyecto		: SISVYR
 * Sistema		: Sistema de Ventas y Reservas
 * Descripción	: 
 * Autor		: José Abanto
 * Fecha		: 26 oct. 2019
 * Hora			: 10:42:28
 */
package com.cystesoft.vyrbus.model.dao.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.cystesoft.vyrbus.model.bean.CanalVenta;
import com.cystesoft.vyrbus.model.bean.Itinerario;
import com.cystesoft.vyrbus.model.bean.Ruta;
import com.cystesoft.vyrbus.model.bean.Servicio;
import com.cystesoft.vyrbus.model.bean.Tarifa;
import com.cystesoft.vyrbus.model.bean.TarifaRegular;
import com.cystesoft.vyrbus.model.dao.TarifaRegularDAO;

/**
 * @author Marco
 *
 */
public class TarifaRegularDAOImpl extends GenericDAOImpl implements TarifaRegularDAO {

	/* (non-Javadoc)
	 * @see com.cystesoft.vyrbus.model.dao.TarifaRegularDao#buscarPorServicio(java.lang.Integer, java.lang.Integer, java.lang.Integer, java.util.Date)
	 */
	@Override
	public List<TarifaRegular> buscarTarifaPorServicio(Integer canalVentaID, Integer servicioID, Integer rutaID, 
													   String fechaTarifa, Integer piso, Integer zona) throws Exception {
		// TODO Auto-generated method stub
		return buscarTarifa(canalVentaID, servicioID, rutaID, fechaTarifa, piso, zona);
	}
	
	
	private List<TarifaRegular> buscarTarifa(Integer canalVentaID, Integer servicioID, Integer rutaID, String fechaTarifa, Integer piso, Integer zona){
		
		String sql = "SELECT "  
					+ "tr.tarreg_id, ta.tarifa_id, ta.canven_id, ta.servicio_id, ta.ruta_id, ta.n_pisbus, " 
					+ "ta.n_zonbus, tr.d_fectar, tr.c_horpar, tr.itinerario_id,  tr.n_monto " 
					+ "FROM vrttarifa ta inner join vrttarreg tr on (ta.tarifa_id = tr.tarifa_id) "
					+ "WHERE ta.canven_id= " + canalVentaID + " AND ta.servicio_id = " + servicioID + " AND ta.ruta_id= " + rutaID + " "
					+ "AND tr.d_fectar=to_date('" + fechaTarifa + "', 'dd/mm/yyyy') ";
		
		if(piso!=null && zona!=null)
			sql += "AND ta.n_pisbus=" + piso + " AND ta.n_zonbus=" + zona + " ";
		sql += "AND ta.c_estreg='A' AND tr.c_estreg='A'";
		
		log.info(sql);
		List<?>result =getSession().createSQLQuery(sql).list();
		List<TarifaRegular> lstTarifaRegular = new ArrayList<>();
		for(int i=0;i<result.size();i++){
			Object[] obj = (Object[]) result.get(i);
			TarifaRegular tarifaRegular = new TarifaRegular();
			tarifaRegular.setId(((BigDecimal)obj[0]).intValue());
			Tarifa tarifa = new Tarifa();
			tarifa.setId(((BigDecimal)obj[1]).intValue());
			CanalVenta canalVenta = new CanalVenta();
			canalVenta.setId(((BigDecimal)obj[2]).intValue());
			tarifa.setCanalVenta(canalVenta);
			Servicio servicio = new Servicio();
			servicio.setId(((BigDecimal)obj[3]).intValue());
			tarifa.setServicio(servicio);
			Ruta ruta = new Ruta();
			ruta.setId(((BigDecimal)obj[4]).intValue());
			tarifa.setRuta(ruta);
			tarifa.setPisoBus(((BigDecimal)obj[5]).intValue());
			tarifa.setZonaBus(((BigDecimal)obj[6]).intValue());
			
			tarifaRegular.setTarifa(tarifa);
			tarifaRegular.setFechaTarifa((Date)obj[7]);
			tarifaRegular.setHoraPartida(obj[8]!=null?obj[8].toString():null);
			tarifaRegular.setItinerario(obj[9]!=null? new Itinerario(((BigDecimal)obj[9]).longValue()):null);
			tarifaRegular.setMonto(((BigDecimal)obj[10]).doubleValue());
			
		
			lstTarifaRegular.add(tarifaRegular);
		}
		
		return lstTarifaRegular;
		
	}


	/* (non-Javadoc)
	 * @see com.cystesoft.vyrbus.model.dao.TarifaRegularDAO#buscarTarifaPorServicio(java.lang.Integer, java.lang.Integer, java.lang.Integer, java.lang.String, java.lang.Integer, java.lang.Integer)
	 */
//	@Override
//	public List<TarifaRegular> buscarTarifaPorServicio(Integer canalVentaID, Integer servicioID, Integer rutaID,
//			String fechaTarifa, Integer piso, Integer zona) throws Exception {
//		// TODO Auto-generated method stub
//		return buscarTarifa(canalVentaID, servicioID, rutaID, fechaTarifa, piso, zona);
//	}

}
