/**
 * Proyecto		: SISVYR
 * Sistema		: Sistema de Ventas y Reservas
 * Descripción	: Objeto que implementa los metodos de acceso a datos de la tabla Promociones VRTPROMOCION.
 * Autor		: José Avalos
 * Fecha		: 17/04/2013
 */
package pe.itsb.vyrbus.model.dao.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map.Entry;
import java.util.TreeMap;

import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import pe.itsb.vyrbus.model.bean.Cliente;
import pe.itsb.vyrbus.model.bean.Promocion;
import pe.itsb.vyrbus.model.dao.PromocionDAO;
import pe.itsb.vyrbus.service.util.Constantes;
import pe.itsb.vyrbus.service.util.Util;

/**
 * @author Jose
 *
 */
@SuppressWarnings("unchecked")
public class PromocionDAOImpl extends GenericDAOImpl implements PromocionDAO {

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.model.dao.PromocionDAO#buscarporId(java.lang.Long)
	 */
	@Override
	public Promocion buscarPorId(Long id) throws Exception {
		return (Promocion)super.findById(Promocion.class, id);
	}

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.model.dao.PromocionDAO#buscarPorX(java.util.TreeMap, java.util.List)
	 */
	@Override
	public ArrayList<Promocion> buscarPorX(TreeMap<String, Object> criteriosBusqueda, List<String> criteriosOrdenar) {
		return (ArrayList<Promocion>)super.findByX(Promocion.class, criteriosBusqueda, criteriosOrdenar);
	}

	/*
	 * (non-Javadoc)
	 * @see com.tepsa.sisvyr.model.dao.PromocionDAO#buscarPorX(java.util.TreeMap, java.util.List, java.util.Date)
	 */
	@Override
	public ArrayList<Promocion> buscarPorX(TreeMap<String, Object> criteriosBusqueda, List<String> criteriosOrdenar, String fechaPartida)throws Exception{
		Criteria oCriteria = getSession().createCriteria(Promocion.class);
		for(Entry<?,?> e : criteriosBusqueda.entrySet()) {
			if(e.getValue() instanceof String)
				oCriteria.add(Restrictions.ilike((String) e.getKey(), e.getValue()));
			else
				oCriteria.add(Restrictions.like((String) e.getKey(), e.getValue()));
		}
		oCriteria.add(Restrictions.sqlRestriction("to_date('"+fechaPartida+"','dd/mm/yyyy') BETWEEN d_fecini AND d_fecfin"));
		if(criteriosOrdenar!=null){
			for(String ordenar : criteriosOrdenar){
				oCriteria.addOrder(Order.asc(ordenar));
			}
		}
		oCriteria.setMaxResults(1000);
		return (ArrayList<Promocion>)oCriteria.list();
	}

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.model.dao.PromocionDAO#buscarPorEstadoRegistro(java.lang.String, java.lang.String)
	 */
	@Override
	public ArrayList<Promocion> buscarPorEstadoRegistro(String estado, String criterioOrden) {
		return (ArrayList<Promocion>)super.findByEstadoRegistro(Promocion.class, estado, criterioOrden);
	}

	/*
	 * (non-Javadoc)
	 * @see com.tepsa.sisvyr.model.dao.PromocionDAO#buscarPromocionesAplicables(boolean, java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	public List<Promocion> buscarPromocionesAplicables(boolean paxfre, String idCliente, String estadoRegistro, String fechaPartida) throws Exception {
		String sql = "SELECT promocion_id, c_denominacion, c_rutas, c_servicios, c_punven, c_canven, c_pasnue, c_canviapas, c_asientos, " +
				"c_edapas, c_cliente, c_idavue, n_valdes, c_tipdes, n_porimp, c_forpag, c_tippag, c_tarcre, c_entemp, c_paxfre, d_fecini, " +
				"d_fecfin, c_estreg, to_char(audfecins, 'dd/MM/yyyy hh24:mi:ss'), audusuins, audipinse " +
				"FROM vrmpromocion " +
				"WHERE c_cliente='*' AND c_paxfre='*' AND c_estreg='"+estadoRegistro+"' " +
						"AND to_date('"+fechaPartida+"','dd/mm/yyyy') BETWEEN d_fecini AND d_fecfin AND n_esTarifa="+Constantes.FALSE_VALUE ;
		if(idCliente!=null)
			sql = sql + " UNION ALL " +
					"SELECT promocion_id, c_denominacion, c_rutas, c_servicios, c_punven, c_canven, c_pasnue, c_canviapas, c_asientos, " +
				"c_edapas, c_cliente, c_idavue, n_valdes, c_tipdes, n_porimp, c_forpag, c_tippag, c_tarcre, c_entemp, c_paxfre, d_fecini, " +
				"d_fecfin, c_estreg, to_char(audfecins, 'dd/MM/yyyy hh24:mi:ss'), audusuins, audipinse " +
				"FROM vrmpromocion " +
				"WHERE c_cliente='"+idCliente+"' AND c_paxfre='*' AND c_estreg='"+estadoRegistro+"' " +
						"AND to_date('"+fechaPartida+"','dd/mm/yyyy') BETWEEN d_fecini AND d_fecfin ";

		if(paxfre)
			sql = sql + " UNION ALL " +
					"SELECT promocion_id, c_denominacion, c_rutas, c_servicios, c_punven, c_canven, c_pasnue, c_canviapas, c_asientos, " +
				"c_edapas, c_cliente, c_idavue, n_valdes, c_tipdes, n_porimp, c_forpag, c_tippag, c_tarcre, c_entemp, c_paxfre, d_fecini, " +
				"d_fecfin, c_estreg, to_char(audfecins, 'dd/MM/yyyy hh24:mi:ss'), audusuins, audipinse " +
				"FROM vrmpromocion " +
				"WHERE c_cliente='*' AND c_paxfre='S' AND c_estreg='"+estadoRegistro+"' " +
						"AND to_date('"+fechaPartida+"','dd/mm/yyyy') BETWEEN d_fecini AND d_fecfin ";

		List<?> result = getSession().createSQLQuery(sql).list();
		List<Promocion> lstResult = new ArrayList<>();
		for(int i=0; i<result.size(); i++){
			Object[] obj = (Object[])result.get(i);
			Promocion promocion = new Promocion();
			promocion.setId(((BigDecimal)obj[0]).longValue());
			promocion.setDenominacion(obj[1].toString());
			promocion.setRutas(obj[2].toString());
			promocion.setServicios(obj[3].toString());
			promocion.setPuntoVenta(obj[4].toString());
			promocion.setCanalVenta(obj[5].toString());
			promocion.setPasajeroNuevo(obj[6].toString());
			promocion.setCantidadViajesPasajero(obj[7].toString());
			promocion.setAsientos(obj[8].toString());
			promocion.setEdadPasajero(null);
			promocion.setCliente(obj[10].toString());
			promocion.setIdaVuelta(obj[11].toString());
			promocion.setValorDescuento(obj[12]==null?null:((BigDecimal)obj[12]).doubleValue());
			promocion.setTipoDescuento(obj[13].toString());
			promocion.setPorImporte(obj[14]==null?null:((BigDecimal)obj[14]).doubleValue());
			promocion.setFormaPago(obj[15].toString());
			promocion.setTipoFormaPago(obj[16].toString());
			promocion.setTarjetaCredito(obj[17].toString());
			promocion.setEnTemporada(obj[18].toString());
			promocion.setPasajeroFrecuente(obj[19].toString());
			promocion.setFechaInicio(obj[20]==null?null:(Date)obj[20]);
			promocion.setFechaFin(obj[21]==null?null:(Date)obj[21]);
			promocion.setEstadoRegistro(obj[22].toString());
			promocion.setFechaInsercion(Util.StringtoDate(obj[23].toString(), Constantes.DATE_TIME_FORMAT));
			promocion.setUsuarioInsercion(obj[24].toString());
			promocion.setIpInsercion(obj[25].toString());
			lstResult.add(promocion);
		}
		return lstResult;
	}

	/*
	 * (non-Javadoc)
	 * @see com.tepsa.sisvyr.model.dao.PromocionDAO#buscarPorTarifa(java.lang.String, java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	public List<Promocion> buscarPorTarifa(String fechaPartida, String ruta, String servicio, String horaPartida){
		/*Primero busca incluyendo la hora de partida - 15/12/2015 - jabanto*/
		String hql=" FROM Promocion WHERE esTarifa=1 AND to_date('"+fechaPartida+"','dd/mm/yyyy') BETWEEN fechaInicio AND fechaFin " +
				"AND estadoRegistro='"+Constantes.VALUE_ACTIVO+"' AND rutas LIKE '%"+ruta+"%' AND servicios LIKE '%"+servicio+"%' AND horasPartida LIKE '%"+horaPartida+"%' ";

		List<Promocion> result = getSession().createQuery(hql).list();
		/*Valida que la promocion por tarifa recuperada corresponda a la ruta que se esta consultando, ya que pueden coinsidir por el tema de la like en el campo "c_ruta" - 10/01/2017 - jabanto*/
		boolean isRuta=false;
		for(Promocion promocion:result){
			String[] rutas=promocion.getRutas().split(",");
			for(String rutaID :rutas){
				if(rutaID.equals(ruta)){
					isRuta=true;
					break;
				}
			}
		}
		if(!(isRuta))
			result= new ArrayList<>();

		/*Si no hay, busca las que estan configuradas para todas las horas de partida 15/12/2015 - jabanto*/
		if(result.size()==0){
			hql=" FROM Promocion WHERE esTarifa=1 AND to_date('"+fechaPartida+"','dd/mm/yyyy') BETWEEN fechaInicio AND fechaFin " +
					"AND estadoRegistro='"+Constantes.VALUE_ACTIVO+"' AND rutas LIKE '%"+ruta+"%' AND servicios LIKE '%"+servicio+"%' AND horasPartida='*' ";
			result = getSession().createQuery(hql).list();
		}

		return result;
	}

	/*
	 * (non-Javadoc)
	 * @see com.tepsa.sisvyr.model.dao.PromocionDAO#buscarPorCriterios(java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	public ArrayList<Promocion> buscarPorCriterios(String denominacion, String ruta, String servicio, String cliente, String tipoDescuento, String criterioOrden)throws Exception{
		String sql = "SELECT promocion_id, c_denominacion, c_rutas, c_servicios, c_punven, c_canven, c_pasnue, c_canviapas, c_asientos, " +
				"c_edapas, c_cliente, c_idavue, n_valdes, c_tipdes, n_porimp, c_forpag, c_tippag, c_tarcre, c_entemp, c_paxfre, d_fecini, " +
				"d_fecfin, c_estreg, to_char(audfecins, 'dd/MM/yyyy hh24:mi:ss'), audusuins, audipinse, c_expresion, " +
				"c_beneficio, n_esacumulable, n_estarifa, c_horpar " +
				"FROM vrmpromocion WHERE c_estreg='A' ";
		if(denominacion != null)
			sql = sql + " AND c_denominacion LIKE '%"+denominacion+"%' ";
		if(ruta != null)
			sql = sql + " AND c_rutas LIKE '%"+ruta+"%' ";
		if(servicio != null)
			sql = sql + " AND c_servicio LIKE '%"+servicio+"%'";
		if(cliente != null){
			if(Character.isDigit(cliente.charAt(0))){
				TreeMap<String, Object> criteriosBusqueda = new TreeMap<>();
				criteriosBusqueda.put("numeroDocumento", cliente);
				ArrayList<Cliente> lstCliente = (ArrayList<Cliente>)super.findByX(Cliente.class, criteriosBusqueda, null);
				if(lstCliente.size() == 1)
					sql = sql + " AND c_cliente='"+lstCliente.get(0).getId().toString()+"' ";
			}
		}
		if(tipoDescuento != null)
			sql = sql + " AND c_tipdes='"+tipoDescuento+"' ";

		sql = sql + "ORDER BY "+criterioOrden;

		List<?> result = getSession().createSQLQuery(sql).list();
		ArrayList<Promocion> lstResult = new ArrayList<>();
		for (Object element : result) {
			Object[] obj = (Object[])element;
			Promocion promocion = new Promocion();
			promocion.setId(((BigDecimal)obj[0]).longValue());
			promocion.setDenominacion(obj[1].toString());
			promocion.setRutas(obj[2].toString());
			promocion.setServicios(obj[3].toString());
			promocion.setPuntoVenta(obj[4].toString());
			promocion.setCanalVenta(obj[5].toString());
			promocion.setPasajeroNuevo(obj[6].toString());
			promocion.setCantidadViajesPasajero(obj[7].toString());
			promocion.setAsientos(obj[8].toString());
			promocion.setEdadPasajero(null);
			promocion.setCliente(obj[10].toString());
			promocion.setIdaVuelta(obj[11].toString());
			promocion.setValorDescuento(obj[12]==null?null:((BigDecimal)obj[12]).doubleValue());
			promocion.setTipoDescuento(obj[13].toString());
			promocion.setPorImporte(obj[14]==null?null:((BigDecimal)obj[14]).doubleValue());
			promocion.setFormaPago(obj[15].toString());
			promocion.setTipoFormaPago(obj[16].toString());
			promocion.setTarjetaCredito(obj[17].toString());
			promocion.setEnTemporada(obj[18].toString());
			promocion.setPasajeroFrecuente(obj[19].toString());
			promocion.setFechaInicio(obj[20]==null?null:(Date)obj[20]);
			promocion.setFechaFin(obj[21]==null?null:(Date)obj[21]);
			promocion.setEstadoRegistro(obj[22].toString());
			promocion.setFechaInsercion(Util.StringtoDate(obj[23].toString(), Constantes.DATE_TIME_FORMAT));
			promocion.setUsuarioInsercion(obj[24].toString());
			promocion.setIpInsercion(obj[25].toString());
			promocion.setExpresion(obj[26].toString());
			promocion.setBeneficio(obj[27].toString());
			promocion.setEsAcumulable(((BigDecimal)obj[28]).intValue());
			promocion.setEsTarifa(((BigDecimal)obj[29]).intValue());
			promocion.setHorasPartida(obj[30]!=null?obj[30].toString():"");
			lstResult.add(promocion);
		}
		return lstResult;
	}

}
