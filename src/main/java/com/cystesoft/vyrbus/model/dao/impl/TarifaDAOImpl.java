/**
 * Proyecto		: SISVYR
 * Sistema		: Sistema de Ventas y Reservas
 * Descripción	: 
 * Autor		: José Abanto
 * Fecha		: 12 jul. 2020
 * Hora			: 23:41:08
 */
package com.cystesoft.vyrbus.model.dao.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

//import org.kohsuke.rngom.util.ocalizer;

import com.cystesoft.vyrbus.model.bean.CanalVenta;
import com.cystesoft.vyrbus.model.bean.Itinerario;
import com.cystesoft.vyrbus.model.bean.Localidad;
import com.cystesoft.vyrbus.model.bean.Ruta;
import com.cystesoft.vyrbus.model.bean.Servicio;
import com.cystesoft.vyrbus.model.bean.Tarifa;
import com.cystesoft.vyrbus.model.bean.TarifaRegular;
import com.cystesoft.vyrbus.model.dao.TarifaDAO;

/**
 * @author Marco
 *
 */
public class TarifaDAOImpl extends GenericDAOImpl implements TarifaDAO{

	/* (non-Javadoc)
	 * @see com.cystesoft.vyrbus.model.dao.TarifaDAO#guardar(com.cystesoft.vyrbus.model.bean.Tarifa)
	 */
	@Override
	public void guardar(Tarifa tarifa) throws Exception {
		// TODO Auto-generated method stub
		super.save(tarifa);
	}

	/* (non-Javadoc)
	 * @see com.cystesoft.vyrbus.model.dao.TarifaDAO#actualizar(com.cystesoft.vyrbus.model.bean.Tarifa)
	 */
	@Override
	public void actualizar(Tarifa tarifa) throws Exception {
		// TODO Auto-generated method stub
		super.update(tarifa);
	}

	/* (non-Javadoc)
	 * @see com.cystesoft.vyrbus.model.dao.TarifaDAO#inactivate(java.lang.Long)
	 */
	@Override
	public void inactivate(Long id) throws Exception {
		// TODO Auto-generated method stub
		super.inactivate(Tarifa.class, id);
	}
	
	@Override
	public List<Tarifa> buscarTarifa(Integer canalVentaID, 
			Integer servicioID, 
			Integer localidadOrigenID, 
			Integer localidadDestinoID, 
			Integer piso, 
			Integer zona) throws Exception{
		String sql = "SELECT " +  
						"t.tarifa_id, t.canven_id, t.servicio_id, " + 				//2
						"t.ruta_id, r.localidad_idorigen, r.localidad_iddestino, " +	//5
						"t.n_pisbus, t.n_zonbus " +					//7
						"FROM vrttarifa t  " +
						"INNER JOIN vrmruta r ON (t.ruta_id = r.ruta_id) " + 
						"WHERE   " +
						"t.c_estreg='A' " +
						" AND t.canven_id = " + canalVentaID + 
						" AND t.servicio_id = " +  servicioID + 
						" AND r.localidad_idorigen = " + localidadOrigenID + 
						" AND r.localidad_iddestino = " + localidadDestinoID  + 
						" AND t.n_zonbus =  " + zona;
		
		if(piso!=null){
			sql += " AND t.n_pisbus = ";
			sql += piso;
		}
			
		log.info(sql);
		List<?>result = getSession().createSQLQuery(sql).list();
		List<Tarifa> lstTarifa = new ArrayList<>();
		for(int i=0;i<result.size();i++){
			Object[] obj = (Object[]) result.get(i);
			Tarifa tarifa = new Tarifa();
			tarifa.setId(((BigDecimal)obj[0]).longValue());
			CanalVenta canalVenta = new CanalVenta();
			canalVenta.setId(((BigDecimal)obj[1]).intValue());
			tarifa.setCanalVenta(canalVenta);
			Servicio servicio = new Servicio();
			servicio.setId(((BigDecimal)obj[2]).intValue());
			tarifa.setServicio(servicio);
			Ruta ruta = new Ruta();
			ruta.setId(((BigDecimal)obj[3]).intValue());
			Localidad localidadOrigen = new Localidad();
			localidadOrigen.setId(((BigDecimal)obj[4]).intValue());
			Localidad localidadDestino = new Localidad();
			localidadDestino.setId(((BigDecimal)obj[5]).intValue());
			ruta.setLocalidadOrigen(localidadOrigen);
			ruta.setLocalidadDestino(localidadDestino);
			tarifa.setRuta(ruta);
			tarifa.setPisoBus(((BigDecimal)obj[6]).intValue());
			tarifa.setZonaBus(((BigDecimal)obj[7]).intValue());
			
			lstTarifa.add(tarifa);
		}
		
		return lstTarifa;		
	}
}
