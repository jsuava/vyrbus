/**
 * Proyecto		: SISVYR
 * Sistema		: Sistema de Ventas y Reservas
 * Descripci�n	:
 * Autor		: Jos� Abanto
 * Fecha		: 14 jul. 2021
 * Hora			: 12:06:43
 */
package pe.itsb.vyrbus.model.dao.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.TreeMap;

import pe.itsb.vyrbus.model.bean.Agencia;
import pe.itsb.vyrbus.model.bean.DetalleEquipaje;
import pe.itsb.vyrbus.model.bean.Equipaje;
import pe.itsb.vyrbus.model.bean.Pasajero;
import pe.itsb.vyrbus.model.bean.Ruta;
import pe.itsb.vyrbus.model.bean.Servicio;
import pe.itsb.vyrbus.model.bean.VentaPasaje;
import pe.itsb.vyrbus.model.dao.EquipajeDAO;
import pe.itsb.vyrbus.service.util.Constantes;

/**
 * @author abant
 *
 */
@SuppressWarnings("unchecked")
public class EquipajeDAOImpl extends GenericDAOImpl implements EquipajeDAO{

	/* (non-Javadoc)
	 * @see pe.itsb.vyrbus.model.dao.EquipajeDAO#buscarPorEstadoRegistro(java.lang.String, java.lang.String)
	 */
	@Override
	public ArrayList<Equipaje> buscarPorEstadoRegistro(String estado, String criterioOrden) {
		// TODO Auto-generated method stub
		return (ArrayList<Equipaje>) super.findByEstadoRegistro(Equipaje.class, estado, criterioOrden);
	}

	/* (non-Javadoc)
	 * @see pe.itsb.vyrbus.model.dao.EquipajeDAO#buscarPorX(java.util.TreeMap, java.util.List)
	 */
	@Override
	public ArrayList<Equipaje> buscarPorX(TreeMap<String, Object> criteriosBusqueda, List<String> criteriosOrdenar) {
		// TODO Auto-generated method stub
		return (ArrayList<Equipaje>) super.findByX(Equipaje.class, criteriosBusqueda, criteriosOrdenar);
	}

	/* (non-Javadoc)
	 * @see pe.itsb.vyrbus.model.dao.EquipajeDAO#buscarPorId(java.lang.Long)
	 */
	@Override
	public Equipaje buscarPorId(Long id) {
		// TODO Auto-generated method stub
		return (Equipaje) findById(Equipaje.class, id);
	}

	/* (non-Javadoc)
	 * @see pe.itsb.vyrbus.model.dao.EquipajeDAO#guardar(pe.itsb.vyrbus.model.bean.Equipaje)
	 */
	@Override
	public void guardar(Equipaje equipaje) {
		// TODO Auto-generated method stub
		super.save(equipaje);
	}

	/* (non-Javadoc)
	 * @see pe.itsb.vyrbus.model.dao.EquipajeDAO#actualizar(pe.itsb.vyrbus.model.bean.Equipaje)
	 */
	@Override
	public void actualizar(Equipaje equipaje) {
		// TODO Auto-generated method stub
		super.update(equipaje);
	}

	/* (non-Javadoc)
	 * @see pe.itsb.vyrbus.model.dao.EquipajeDAO#buscar(java.lang.String, java.lang.String, java.lang.Integer, java.lang.Integer, java.lang.Integer)
	 */
	@Override
	public List<Equipaje> buscar(String fechaInicio, String fechaFin, Integer agenciaIdEmbarque,
			Integer localidadIdOrigen, Integer localidadIdDestino) {
		String sql = 
				"SELECT eq.EQU_ID, DEQ.DETEQU_ID, vp.D_FECPAR, vp.C_HORPAR, rt.C_ORIGEN, rt.C_DESTINO, sv.C_DENOMINACION servicio, vp.C_NUMBOLETO numComprobante,\n" + 
				"\t   vp.N_NUMASIENTO, vp.N_NUMPISO, deq.C_TICKET, deq.N_NUMCOR, deq.N_PESO, eq.C_OBS obsEquipaje,\n" + 
				"\t   px.C_NOMAPE pasajero, ap.C_DENOMINACION agenciaEmbaruqe, deq.VENPAS_IDEXC\n" + 
				"FROM VRTEQU eq\n" + 
				"  INNER JOIN VRTDETEQU deq ON (deq.EQU_ID = eq.EQU_ID)\n" + 
				"  INNER JOIN VRTVENPAS vp ON (vp.VENPAS_ID = DEQ.VENPAS_ID)\n" + 
				"  INNER JOIN VRMRUTA rt ON (rt.RUTA_ID = vp.RUTA_ID)\n" + 
				"  INNER JOIN VRMSERVICIO sv ON (sv.SERVICIO_ID = vp.SERVICIO_ID)\n" + 
				"  INNER JOIN VRMPASAJERO px ON (px.PASAJERO_ID = vp.PASAJERO_ID)\n" + 
				"  INNER JOIN VRMAGENCIA ap ON (ap.AGENCIA_ID = eq.AGENCIA_ID)\n" + 
				"WHERE eq.C_ESTREG = '"+ Constantes.VALUE_ACTIVO +"'\n" + 
				"  AND deq.C_ESTREG = '"+ Constantes.VALUE_ACTIVO +"'\n" + 
				"  AND vp.D_FECPAR BETWEEN TO_DATE('"+ fechaInicio +"', 'dd/MM/yyyy') AND TO_DATE('"+ fechaFin +"', 'dd/MM/yyyy')\n" + 
				"  AND eq.AGENCIA_ID = "+ agenciaIdEmbarque +" \n" + 
				"  AND rt.LOCALIDAD_IDORIGEN = NVL("+ localidadIdOrigen +", rt.localidad_idorigen)\n" + 
				"  AND rt.LOCALIDAD_IDDESTINO = NVL("+ localidadIdDestino +", rt.localidad_iddestino) \n" +
				"ORDER BY vp.D_FECPAR, vp.C_HORPAR, rt.C_ORIGEN, rt.C_DESTINO, deq.C_TICKET, deq.N_NUMCOR \n";
		log.info("Buscar equipajes: " + sql);
		
		List<?> result = getSession().createSQLQuery(sql).list();
		List<Equipaje> lstResult = new ArrayList<>();
		for(int i=0; i<result.size(); i++){
			Object[] obj = (Object[])result.get(i);
			
			VentaPasaje ventaPasaje = new VentaPasaje();
			ventaPasaje.setFechaPartida((Date)obj[2]);
			ventaPasaje.setHoraEmbarque(obj[3].toString());
			ventaPasaje.setRuta(new Ruta(obj[4].toString(), obj[5].toString()));
			ventaPasaje.setServicio(new Servicio(obj[6].toString()));
			ventaPasaje.setNumeroBoleto(obj[7].toString());
			ventaPasaje.setNumeroAsiento(((BigDecimal)obj[8]).intValue());
			ventaPasaje.setNumeroPiso(((BigDecimal)obj[9]).intValue() +1);
			ventaPasaje.setPasajero(new Pasajero(obj[14].toString()));
			ventaPasaje.setAgenciaPartida(new Agencia(obj[15].toString()));
			
			Equipaje equipaje = new Equipaje();
			equipaje.setId(((BigDecimal)obj[0]).longValue());
			equipaje.setObservaciones(obj[13]!=null? obj[13].toString(): null);
			
			DetalleEquipaje detalleEquipaje = new DetalleEquipaje();
			detalleEquipaje.setId(((BigDecimal)obj[1]).longValue());
			detalleEquipaje.setEquipaje(equipaje);
			detalleEquipaje.setTicket(obj[10].toString());
			detalleEquipaje.setNumeroCorrelativo(((BigDecimal)obj[11]).intValue());
			detalleEquipaje.setPeso(((BigDecimal)obj[12]).doubleValue());
			detalleEquipaje.setVentaPasaje(ventaPasaje);
			detalleEquipaje.setVentaPasajeExceso(obj[16]!=null? new VentaPasaje(((BigDecimal)obj[16]).longValue()): null);
			
			equipaje.setDetalleEquipaje(detalleEquipaje);			
			lstResult.add(equipaje);
		}
		
		return lstResult;
	}

}
