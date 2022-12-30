/**
 * Proyecto		: SISVYR
 * Sistema		: Sistema de Ventas y Reservas
 * Descripción	:
 * Autor		: José Abanto
 * Fecha		: 14 jul. 2021
 * Hora			: 12:10:27
 */
package com.cystesoft.vyrbus.model.dao.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

import com.cystesoft.vyrbus.model.bean.DetalleEquipaje;
import com.cystesoft.vyrbus.model.bean.Equipaje;
import com.cystesoft.vyrbus.model.bean.Pasajero;
import com.cystesoft.vyrbus.model.bean.VentaPasaje;
import com.cystesoft.vyrbus.model.dao.DetalleEquipajeDAO;

/**
 * @author abant
 *
 */
@SuppressWarnings("unchecked")
public class DetalleEquipajeDAOImpl  extends GenericDAOImpl implements DetalleEquipajeDAO{

	/* (non-Javadoc)
	 * @see com.cystesoft.vyrbus.model.dao.DetalleEquipajeDAO#buscarPorEstadoRegistro(java.lang.String, java.lang.String)
	 */
	@Override
	public ArrayList<DetalleEquipaje> buscarPorEstadoRegistro(String estado, String criterioOrden) {
		// TODO Auto-generated method stub
		return (ArrayList<DetalleEquipaje>) super.findByEstadoRegistro(DetalleEquipaje.class, estado, criterioOrden);
	}

	/* (non-Javadoc)
	 * @see com.cystesoft.vyrbus.model.dao.DetalleEquipajeDAO#buscarPorX(java.util.TreeMap, java.util.List)
	 */
	@Override
	public ArrayList<DetalleEquipaje> buscarPorX(TreeMap<String, Object> criteriosBusqueda,
			List<String> criteriosOrdenar) {
		// TODO Auto-generated method stub
		return (ArrayList<DetalleEquipaje>) super.findByX(DetalleEquipaje.class, criteriosBusqueda, criteriosOrdenar);
	}

	/* (non-Javadoc)
	 * @see com.cystesoft.vyrbus.model.dao.DetalleEquipajeDAO#buscarPorId(java.lang.Long)
	 */
	@Override
	public DetalleEquipaje buscarPorId(Long id) {
		// TODO Auto-generated method stub
		return (DetalleEquipaje) super.findById(DetalleEquipaje.class, id);
	}

	/* (non-Javadoc)
	 * @see com.cystesoft.vyrbus.model.dao.DetalleEquipajeDAO#guardar(com.cystesoft.vyrbus.model.bean.DetalleEquipaje)
	 */
	@Override
	public void guardar(DetalleEquipaje detalleEquipaje) {
		// TODO Auto-generated method stub
		super.save(detalleEquipaje);
	}

	/* (non-Javadoc)
	 * @see com.cystesoft.vyrbus.model.dao.DetalleEquipajeDAO#actualizar(com.cystesoft.vyrbus.model.bean.DetalleEquipaje)
	 */
	@Override
	public void actualizar(DetalleEquipaje detalleEquipaje) {
		// TODO Auto-generated method stub
		super.update(detalleEquipaje);
	}

	/* (non-Javadoc)
	 * @see com.cystesoft.vyrbus.model.dao.DetalleEquipajeDAO#buscarManifiestoEquipaje(java.lang.Long, java.lang.Integer)
	 */
	@Override
	public List<DetalleEquipaje> buscarManifiestoEquipaje(Long itinerarioId, Integer agenciaId) throws Exception {
		// TODO Auto-generated method stub
//		String sql="Select deq.detequ_id,eq.equ_id, vp.c_numboleto boleto, p.c_apepat, p.c_apemat, p.c_nombre, deq.c_ticket ticket, vp.n_numasiento "
//				+ "From vrtequ eq "
//				+ "  Inner Join vrtdetequ deq On (deq.equ_id=eq.equ_id) "
//				+ "  Left Join vrtvenpas vp On (vp.venpas_id=deq.venpas_id And deq.n_principal=1) "
//				+ "  Left Join vrmpasajero p On (p.pasajero_id=vp.pasajero_id) "
//				+ "Where eq.itinerario_id="+itinerarioId+" And eq.c_estreg='A' And deq.c_estreg='A' "
//				+ "  And eq.agencia_id = "+agenciaId+" And deq.n_peso>0 "
//				+ "Order by deq.c_ticket ";
		//MAOE 28/12/2022: Ya no recuperamos los ticket sino solo los comprobantes, pax, desceipcion y total
		String sql="Select " 
		        + "eq.equ_id, vp.c_numboleto boleto, p.c_apepat, p.c_apemat, p.c_nombre, eq.c_obs, "  
		        + "vp.n_numasiento, vp.n_imppag total " 
		        + "From vrtequ eq   "
				+ "     Inner Join vrtdetequ deq On (deq.equ_id=eq.equ_id And deq.n_principal=1) "
				+ "     Left Join vrtvenpas vp On (vp.venpas_id=deq.venpas_id)   "
				+ "     Left Join vrmpasajero p On (p.pasajero_id=vp.pasajero_id) " 
				+ "Where "
				+ "     eq.itinerario_id="+ itinerarioId +" And eq.c_estreg='A' And deq.c_estreg='A' "   
				+ "     And eq.agencia_id = "+ agenciaId +" And deq.n_peso>0 " 
				+ "Order by vp.c_numboleto ";

		log.info("ManifiestoEquipaje:"+sql);
		List<?> result = getSession().createSQLQuery(sql).list();
		List<DetalleEquipaje> lstResult = new ArrayList<>();
		for(int i=0; i<result.size(); i++){
			Object[] obj = (Object[])result.get(i);
			DetalleEquipaje detalleEquipaje = new DetalleEquipaje();
			Equipaje equipaje = new Equipaje();
			equipaje.setId(((BigDecimal)obj[0]).longValue());
			detalleEquipaje.setId(((BigDecimal)obj[0]).longValue());
//			detalleEquipaje.setEquipaje(new Equipaje(  ));
			if(obj[1]!=null) {
				VentaPasaje ventaPasaje= new VentaPasaje();
				ventaPasaje.setNumeroBoleto(obj[1]!=null?obj[1].toString():null);
				Pasajero pasajero= new Pasajero();
				pasajero.setApellidoPaterno(obj[2]!=null?obj[2].toString():null);
				pasajero.setApellidoMaterno(obj[3]!=null?obj[3].toString():null);
				pasajero.setNombre(obj[4]!=null?obj[4].toString():null);
				ventaPasaje.setPasajero(pasajero);
				ventaPasaje.setNumeroAsiento(((BigDecimal)obj[6]).intValue());
				ventaPasaje.setImportePagado(((BigDecimal)obj[7]).doubleValue());
				detalleEquipaje.setVentaPasaje(ventaPasaje);
				equipaje.setObservaciones(obj[5]!=null?obj[5].toString():"");
			}
			detalleEquipaje.setEquipaje(equipaje);

			lstResult.add(detalleEquipaje);
		}
		
//		for(int i=0; i<result.size(); i++){
//			Object[] obj = (Object[])result.get(i);
//			DetalleEquipaje detalleEquipaje = new DetalleEquipaje();
//			detalleEquipaje.setId(((BigDecimal)obj[0]).longValue());
//			detalleEquipaje.setEquipaje(new Equipaje(((BigDecimal)obj[1]).longValue()));
//			if(obj[2]!=null) {
//				VentaPasaje ventaPasaje= new VentaPasaje();
//				ventaPasaje.setNumeroBoleto(obj[2]!=null?obj[2].toString():null);
//				Pasajero pasajero= new Pasajero();
//				pasajero.setApellidoPaterno(obj[3]!=null?obj[3].toString():null);
//				pasajero.setApellidoMaterno(obj[4]!=null?obj[4].toString():null);
//				pasajero.setNombre(obj[5]!=null?obj[5].toString():null);
//				ventaPasaje.setPasajero(pasajero);
//				ventaPasaje.setNumeroAsiento(((BigDecimal)obj[7]).intValue());
//				detalleEquipaje.setVentaPasaje(ventaPasaje);
//			}
//			detalleEquipaje.setTicket(obj[6].toString());
//
//			lstResult.add(detalleEquipaje);
//		}		

		return lstResult;
	}

}
