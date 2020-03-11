/**
 * Proyecto		: SISVYR
 * Sistema		: Sistema de Ventas y Reservas
 * Descripción	: 
 * Autor		: José Sullo Avalos
 * Fecha		: 05/07/2012
 */
package com.cystesoft.vyrbus.model.dao.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.TreeMap;

import org.zkoss.zk.ui.Executions;

import com.cystesoft.vyrbus.model.bean.Agencia;
import com.cystesoft.vyrbus.model.bean.Bus;
import com.cystesoft.vyrbus.model.bean.DetalleItinerario;
import com.cystesoft.vyrbus.model.bean.Itinerario;
import com.cystesoft.vyrbus.model.bean.ItinerarioAgenciaLlegada;
import com.cystesoft.vyrbus.model.bean.ItinerarioAgenciaPartida;
import com.cystesoft.vyrbus.model.bean.Localidad;
import com.cystesoft.vyrbus.model.bean.Pasajero;
import com.cystesoft.vyrbus.model.bean.PoolLocalidad;
import com.cystesoft.vyrbus.model.bean.Ruta;
import com.cystesoft.vyrbus.model.bean.Servicio;
import com.cystesoft.vyrbus.model.bean.TipoItinerario;
import com.cystesoft.vyrbus.model.bean.Usuario;
import com.cystesoft.vyrbus.model.bean.VentaPasaje;
import com.cystesoft.vyrbus.model.dao.ItinerarioDAO;
import com.cystesoft.vyrbus.service.exceptions.ItinerarioException;
import com.cystesoft.vyrbus.service.util.Constantes;
import com.cystesoft.vyrbus.service.util.UtilData;
/**
 *
 * @author José Sullo Avalos
 * @since JDK1.6
 */
@SuppressWarnings("unchecked")
public class ItinerarioDAOImpl extends GenericDAOImpl implements ItinerarioDAO {

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.dao.ItinerarioDAO#buscarItinerarios(java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	public List<DetalleItinerario> buscarItinerarios(String fechaPartida, String origen, String destino) throws Exception {
		String sql = "SELECT i.itinerario_id, i.c_sectra, aoi.agencia_id idAgeOrigenIti, adi.agencia_id idAgeDestinoIti, b.bus_id, b.c_codigo, " +
				"s.servicio_id, s.c_denominacion servicio, r.ruta_id, r.c_origen origen, lo.localidad_id idOrigen, ld.localidad_id idDestino, " +
				"aor.agencia_id idAgeOrigenRuta, aor.c_nomcor agpartidaNomCor, aor.c_denominacion agPartidaRuta, di.d_fecpar, di.c_horpar, " +
				"r.c_destino destino, adr.agencia_id idAgeDestinoRuta, adr.c_nomcor agllegadaNomCor, adr.c_denominacion agLlegadaRuta, " +
				"di.d_feclle, di.c_horlle, di.n_tarifa, s.n_numpis, s.n_numasipis1, s.n_numfilpis1, s.n_numcolpis1, s.n_numasipis2, " +
				"s.n_numfilpis2, s.n_numcolpis2, i.d_fecreapar, ti.tipiti_id  " +
				",rm.ruta_id, lpool.poolloc_id "+ // 33-34
				"FROM vrtitinerario i " +
					"INNER JOIN vrtdetiti di ON di.itinerario_id=i.itinerario_id " +
					"LEFT JOIN vrmbus b ON b.bus_id=i.bus_id " +
					"INNER JOIN vrmservicio s ON s.servicio_id=i.servicio_id " +
					"INNER JOIN vrmruta r ON r.ruta_id=di.ruta_id " +
					"INNER JOIN vrmruta rm ON rm.ruta_id=i.ruta_idmayor " +
					"INNER JOIN vrmagencia aor ON aor.agencia_id=di.agencia_idpartida " +
					"INNER JOIN vrmagencia adr ON adr.agencia_id=di.agencia_idllegada " +
					"INNER JOIN vrmlocalidad lo ON lo.localidad_id=r.localidad_idOrigen " +
					"INNER JOIN vrmlocalidad ld ON ld.localidad_id=r.localidad_idDestino " +
					"INNER JOIN vrmagencia aoi ON aoi.agencia_id=i.agencia_idPartida " +
					"INNER JOIN vrmagencia adi ON adi.agencia_id=i.agencia_idLlegada " +
					"INNER JOIN vrmtipiti ti ON ti.tipiti_id=i.tipiti_id " +
//					"LEFT JOIN VRTPOOLLOC lpool ON (lpool.localidad_iddestino=r.localidad_iddestino AND lpool.c_ruc='20502324927' AND lpool.c_estreg='A') " + //Para validar si el destino pertenece al pool
					"LEFT JOIN VRTPOOLLOC lpool ON (lpool.localidad_iddestino=r.localidad_iddestino AND lpool.c_ruc='20502324927' AND lpool.c_estreg='A' AND lpool.ruta_idmayor=i.ruta_idmayor) " + //Para validar si el destino pertenece al pool
				"WHERE di.d_fecpar=to_date('"+fechaPartida+"','dd/mm/yyyy') AND r.c_origen LIKE '"+origen+"%' AND r.c_destino LIKE '"+destino+"%' AND " +
					"n_esanulado=0 " +
				"ORDER BY di.d_fecpar, to_date(di.c_horpar,'HH24:MI'), s.c_denominacion, di.d_feclle, to_date(di.c_horlle,'HH24:MI')";
		
		List<?> result = getSession().createSQLQuery(sql).list();
		List<DetalleItinerario> lstResult = new ArrayList<DetalleItinerario>();
		for(int i=0; i<result.size(); i++){
			Object[] obj = (Object[]) result.get(i);
			DetalleItinerario detalleItinerario = new DetalleItinerario();
			TipoItinerario tipoItinerario= new TipoItinerario();
			tipoItinerario.setId(((BigDecimal)obj[32]).intValue());
			Itinerario itinerario = new Itinerario();
			itinerario.setId(((BigDecimal)obj[0]).longValue());
			itinerario.setSecuenciaTramo(obj[1].toString());
			itinerario.setTipoItinerario(tipoItinerario);
			
			Ruta rutaMayor=new Ruta(((BigDecimal)obj[33]).intValue());
			itinerario.setRuta(rutaMayor);
			
//			itinerario.setListSecuenciaTramo(obtenerSecuencia(itinerario.getSecuenciaTramo()));
			Agencia agenciaPartidaItinerario = new Agencia();
			agenciaPartidaItinerario.setId(((BigDecimal)obj[2]).intValue());
			itinerario.setAgenciaPartida(agenciaPartidaItinerario);
			Agencia agenciaLlegadaItinerario = new Agencia();
			agenciaLlegadaItinerario.setId(((BigDecimal)obj[3]).intValue());			
			itinerario.setAgenciaLlegada(agenciaLlegadaItinerario);
			if(obj[4]!=null){
				Bus bus = new Bus();
				bus.setId(((BigDecimal)obj[4]).intValue());
				bus.setCodigo(obj[5].toString());
				itinerario.setBus(bus);
			}
			Servicio servicio = new Servicio();
			servicio.setId(((BigDecimal)obj[6]).intValue());
			servicio.setDenominacion(obj[7].toString());
			itinerario.setServicio(servicio);
			Ruta ruta = new Ruta();
			ruta.setId(((BigDecimal)obj[8]).intValue());
			ruta.setOrigen(obj[9].toString());
			Localidad localidadOrigen = new Localidad();
			localidadOrigen.setId(((BigDecimal)obj[10]).intValue());
			ruta.setLocalidadOrigen(localidadOrigen);
			Localidad localidadDestino = new Localidad();
			localidadDestino.setId(((BigDecimal)obj[11]).intValue());
			ruta.setLocalidadDestino(localidadDestino);
			Agencia agenciaPartidaRuta = new Agencia();
			agenciaPartidaRuta.setId(((BigDecimal)obj[12]).intValue());
			agenciaPartidaRuta.setNombreCorto(obj[13]==null?null:obj[13].toString());
			agenciaPartidaRuta.setDenominacion(obj[14].toString());
			detalleItinerario.setAgenciaPartida(agenciaPartidaRuta);
			detalleItinerario.setFechaPartida((Date)obj[15]);
			detalleItinerario.setHoraPartida(obj[16].toString());
			ruta.setDestino(obj[17].toString());
//			ruta.setValidarPrioridadVenta(obj[34]!=null?((BigDecimal)obj[34]).intValue():null);
			Agencia agenciaLlegadaRuta = new Agencia();
			agenciaLlegadaRuta.setId(((BigDecimal)obj[18]).intValue());
			agenciaLlegadaRuta.setNombreCorto(obj[19]==null?null:obj[11].toString());
			agenciaLlegadaRuta.setDenominacion(obj[20].toString());
			detalleItinerario.setAgenciaLlegada(agenciaLlegadaRuta);
			detalleItinerario.setFechaLlegada((Date)obj[21]);
			detalleItinerario.setHoraLlegada(obj[22].toString());
			detalleItinerario.setTarifa(((BigDecimal)obj[23]).doubleValue());
			detalleItinerario.setRuta(ruta);
			detalleItinerario.setItinerario(itinerario);
			servicio.setNumeroPisos(((BigDecimal)obj[24]).intValue());
			servicio.setNumeroAsientosPiso1(((BigDecimal)obj[25]).intValue());
			servicio.setNumeroFilasPiso1(((BigDecimal)obj[26]).intValue());
			servicio.setNumeroColumnasPiso1(((BigDecimal)obj[27]).intValue());
			servicio.setNumeroAsientosPiso2(obj[28]==null?null:((BigDecimal)obj[28]).intValue());
			servicio.setNumeroFilasPiso2(obj[29]==null?null:((BigDecimal)obj[29]).intValue());
			servicio.setNumeroColumnasPiso2(obj[30]==null?null:((BigDecimal)obj[30]).intValue());
			itinerario.setFechaRealPartida(obj[31]==null?null:(Date)obj[31]);
			
			if(obj[34]!=null){
				String hql="FROM PoolLocalidad WHERE id="+((BigDecimal)obj[34]).intValue();
				PoolLocalidad localidadIntegracion=(PoolLocalidad) getSession().createQuery(hql).uniqueResult();
				detalleItinerario.setPoolLocalidad(localidadIntegracion);
			}
			
			
			lstResult.add(detalleItinerario);
		}
		return lstResult;
	}
	/*
	 * (non-Javadoc)
	 * @see com.tepsa.sisvyr.dao.ItinerarioDAO#buscarItinerariosFechaAbierta(java.lang.String, java.lang.String, java.lang.String)
	 */
	public List<DetalleItinerario> buscarItinerariosFechaAbierta(String fechaPartida, String origen, String destino) throws Exception {
		String sql = "SELECT DISTINCT(s.c_denominacion), s.servicio_id, r.ruta_id, r.c_origen origen, lo.localidad_id idOrigen, ao.agencia_id idAgePartida, " +
				"ao.c_nomcor agPartida, di.d_fecpar, r.c_destino destino, ld.localidad_id idDestino, ad.agencia_id idAgeDestino, ad.c_nomcor agDestino, " +
				"di.n_tarifa, s.n_numpis, s.n_numasipis1, s.n_numfilpis1, s.n_numcolpis1, s.n_numasipis2, s.n_numfilpis2, s.n_numcolpis2 " +
				"FROM vrtitinerario i " +
				"INNER JOIN vrtdetiti di ON di.itinerario_id=i.itinerario_id " +
				"INNER JOIN vrmservicio s ON s.servicio_id=i.servicio_id " +
				"INNER JOIN vrmruta r ON r.ruta_id=di.ruta_id " +
				"INNER JOIN vrmagencia ao ON ao.agencia_id=di.agencia_idpartida " +
				"INNER JOIN vrmagencia ad ON ad.agencia_id=di.agencia_idllegada " +
				"INNER JOIN vrmlocalidad lo ON lo.localidad_id=r.localidad_idOrigen " +
				"INNER JOIN vrmlocalidad ld ON ld.localidad_id=r.localidad_idDestino " +
				"WHERE di.d_fecpar=to_date('"+fechaPartida+"','dd/mm/yyyy') AND r.c_origen LIKE '"+origen+"%' AND r.c_destino LIKE '"+destino+"%' " +
						"AND n_esanulado=0 " +
				"ORDER BY s.c_denominacion ";
		
		List<?> result = getSession().createSQLQuery(sql).list();
		List<DetalleItinerario> lstResult = new ArrayList<DetalleItinerario>();
		for(int i=0; i<result.size(); i++){
			Object[] obj = (Object[]) result.get(i);
			DetalleItinerario detalleItinerario = new DetalleItinerario();
			Itinerario itinerario = new Itinerario();
			Servicio servicio = new Servicio();
			servicio.setDenominacion(obj[0].toString());
			servicio.setId(((BigDecimal)obj[1]).intValue());
			itinerario.setServicio(servicio);
			Ruta ruta = new Ruta();
			ruta.setId(((BigDecimal)obj[2]).intValue());
			ruta.setOrigen(obj[3].toString());
			Localidad localidadOrigen = new Localidad();
			localidadOrigen.setId(((BigDecimal)obj[4]).intValue());
			ruta.setLocalidadOrigen(localidadOrigen);
			Agencia agenciaPartida = new Agencia();
			agenciaPartida.setId(((BigDecimal)obj[5]).intValue());
			agenciaPartida.setNombreCorto(obj[6].toString());
			detalleItinerario.setAgenciaPartida(agenciaPartida);
			detalleItinerario.setFechaPartida((Date)obj[7]);
			ruta.setDestino(obj[8].toString());
			Localidad localidadDestino = new Localidad();
			localidadDestino.setId(((BigDecimal)obj[9]).intValue());
			ruta.setLocalidadDestino(localidadDestino);
			Agencia agenciaLlegada = new Agencia();
			agenciaLlegada.setId(((BigDecimal)obj[10]).intValue());
			agenciaLlegada.setNombreCorto(obj[11].toString());
			detalleItinerario.setAgenciaLlegada(agenciaLlegada);
			detalleItinerario.setTarifa(((BigDecimal)obj[12]).doubleValue());
			detalleItinerario.setRuta(ruta);
			detalleItinerario.setItinerario(itinerario);
			servicio.setNumeroPisos(((BigDecimal)obj[13]).intValue());
			servicio.setNumeroAsientosPiso1(((BigDecimal)obj[14]).intValue());
			servicio.setNumeroFilasPiso1(((BigDecimal)obj[15]).intValue());
			servicio.setNumeroColumnasPiso1(((BigDecimal)obj[16]).intValue());
			servicio.setNumeroAsientosPiso2(obj[17]==null?null:((BigDecimal)obj[17]).intValue());
			servicio.setNumeroFilasPiso2(obj[18]==null?null:((BigDecimal)obj[18]).intValue());
			servicio.setNumeroColumnasPiso2(obj[19]==null?null:((BigDecimal)obj[19]).intValue());
			lstResult.add(detalleItinerario);
		}
		return lstResult;
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.tepsa.sisvyr.dao.ItinerarioDAO#buscarAgenciasPartida(java.lang.Long, java.lang.String)
	 */
	public ArrayList<ItinerarioAgenciaPartida> buscarAgenciasPartida(Long idItinerario, String estado)throws Exception{
		String sql = "SELECT iap.itinerario_id, iap.agencia_id, a.c_denominacion, iap.c_horpar, a.c_nomcor " +
				"FROM vrtitiagepar iap " +
				"INNER JOIN vrmagencia a ON a.agencia_id=iap.agencia_id " +
				"WHERE iap.itinerario_id="+idItinerario+" AND iap.c_estreg IN ('"+Constantes.VALUE_ACTIVO+"','"+estado+"')";
		List<?> result = getSession().createSQLQuery(sql).list();
		ArrayList<ItinerarioAgenciaPartida> lstResult = new ArrayList<ItinerarioAgenciaPartida>();
		for(int i=0; i<result.size(); i++){
			Object[] obj = (Object[])result.get(i);
			ItinerarioAgenciaPartida itAgePartida = new ItinerarioAgenciaPartida();
			Itinerario itinerario =  new Itinerario(((BigDecimal)obj[0]).longValue());
			itAgePartida.setItinerario(itinerario);
			Agencia agencia = new Agencia(((BigDecimal)obj[1]).intValue());
			agencia.setDenominacion(obj[2].toString());
			agencia.setNombreCorto(obj[4]==null?null:obj[4].toString());
			itAgePartida.setAgencia(agencia);
			itAgePartida.setHoraPartida(obj[3].toString());
			lstResult.add(itAgePartida);
		}
		return lstResult;
	}
	/*
	 * (non-Javadoc)
	 * @see com.tepsa.sisvyr.dao.ItinerarioDAO#buscarAgenciasLlegada(java.lang.Long, java.lang.String)
	 */
	public ArrayList<ItinerarioAgenciaLlegada> buscarAgenciasLlegada(Long idItinerario, String estado)throws Exception{
		String sql = "SELECT iad.itinerario_id, iad.agencia_id, a.c_denominacion, iad.c_horlle, a.c_nomcor " +
				"FROM vrtitiagelle iad " +
				"INNER JOIN vrmagencia a ON a.agencia_id=iad.agencia_id " +
				"WHERE iad.itinerario_id="+idItinerario+" AND iad.c_estreg IN ('"+Constantes.VALUE_ACTIVO+"','"+estado+"')";
		List<?> result = getSession().createSQLQuery(sql).list();
		ArrayList<ItinerarioAgenciaLlegada> lstResult = new ArrayList<ItinerarioAgenciaLlegada>();
		for(int i=0; i<result.size(); i++){
			Object[] obj = (Object[])result.get(i);
			ItinerarioAgenciaLlegada itAgeLlegada = new ItinerarioAgenciaLlegada();
			Itinerario itinerario =  new Itinerario(((BigDecimal)obj[0]).longValue());
			itAgeLlegada.setItinerario(itinerario);
			Agencia agencia = new Agencia(((BigDecimal)obj[1]).intValue());
			agencia.setDenominacion(obj[2].toString());
			agencia.setNombreCorto(obj[4]==null?null:obj[4].toString());
			itAgeLlegada.setAgencia(agencia);
			itAgeLlegada.setHoraLlegada(obj[3].toString());
			lstResult.add(itAgeLlegada);
		}
		return lstResult;
	}
	
//	private List<SecuenciaTramo> obtenerSecuencia(String secuencia){
//		String[] sArray = secuencia.split(";");
//		List<SecuenciaTramo> lstResult = new ArrayList<SecuenciaTramo>();
//		for(String obj : sArray){
//			SecuenciaTramo secuenciaTramo = new SecuenciaTramo();
//			String[] buffer = obj.split("-");
//			secuenciaTramo.setOrigen(Integer.valueOf(buffer[0]));
//			secuenciaTramo.setDestino(Integer.valueOf(buffer[1]));
//			secuenciaTramo.setOrden(Integer.valueOf(buffer[2]));
//			lstResult.add(secuenciaTramo);
//		}
//		return lstResult;
//	}

	/*
	 * (non-Javadoc)
	 * @see com.tepsa.sisvyr.model.dao.ItinerarioDAO#validateCapacity(java.lang.Long, java.lang.Integer, java.lang.Integer)
	 */
	@Override
	public List<Object> validateCapacity(Long idItinerario, Integer asiento, Integer piso) throws Exception {
		String sql = "SELECT s.c_denominacion, s.n_numasipis1, s.n_numasipis2 " +
				"FROM vrtitinerario i " +
				"INNER JOIN vrmservicio s ON s.servicio_id=i.servicio_id " +
				"WHERE i.itinerario_id="+idItinerario +" AND i.c_estreg='"+Constantes.VALUE_ACTIVO+"' ";
		
		log.info(sql);
		List<?> result = getSession().createSQLQuery(sql).list();
		List<Object> lstResult = new LinkedList<Object>();
		for(int i=0; i<result.size(); i++){
			Object[] obj = (Object[])result.get(i);
			if(piso==0){
				if(Integer.valueOf(obj[1].toString())<asiento)
					lstResult.add(true);
				else
					lstResult.add(false);
				lstResult.add(obj[0].toString());
			}else{
				if(Integer.valueOf(obj[2].toString())<asiento)
					lstResult.add(true);
				else
					lstResult.add(false);
				lstResult.add(obj[0].toString());
			}
			
		}
		return lstResult;
	}
	
	@Override
	public List<DetalleItinerario> buscarItinerariosMantenimiento(Long idItinerario, String origen,String destino, String fechaInicio, String fechaFinal,
		String Servicio, String tipoDeItinerario, String criterioOrden) throws Exception {
		
		 if(origen.isEmpty())
			 origen=null;
		 else
			 origen="'"+origen+"'";
		 
		if(destino.isEmpty())
			destino=null;
		else
			destino="'"+destino+"'";
		
		if(Servicio.isEmpty())
			Servicio=null;
		else
			Servicio="'"+Servicio+"'";
		
		if(tipoDeItinerario.isEmpty())
			tipoDeItinerario=null;
		else
			tipoDeItinerario="'"+tipoDeItinerario+"'";
		
		
		String sql="";
		String where="";
		if (criterioOrden=="" )
			criterioOrden="di.d_fecpar, di.c_horpar,di.d_feclle, di.c_horlle "; /*MUY IMPORTANTE PARA LA RECUPERACION DEL DETALLE DEL ITINERARIO*/
		
		List<DetalleItinerario> lstResult = new ArrayList<DetalleItinerario>();		
		sql = "SELECT i.itinerario_id, i.bus_id, b.c_codigo, s.servicio_id, s.c_denominacion servicio, r.ruta_id, r.c_origen origen, " +
					"ao.c_nomcor agpartida, di.d_fecpar, di.c_horpar, r.c_destino destino, ad.c_nomcor agllegada, di.d_feclle, di.c_horlle, di.n_tarifa, " +
					"s.n_numpis, s.n_numasipis1, s.n_numfilpis1, s.n_numcolpis1, ao.agencia_id idAgeOrigen, ad.agencia_id idAgeDestino, i.c_sectra, " +
					"lo.localidad_id idOrigen, ld.localidad_id idDestino, " +
					"ti.tipiti_id, ti.c_denominacion tipoItinerario, " +
					"di.detiti_id, ao.c_denominacion ageOrigen, ad.c_denominacion ageDestino, " +
					"s.n_numAsiPis1, s.n_numAsiPis2, s.n_numfilpis2, i.d_fecreapar, r.n_horvia, " +
					"rm.localidad_idOrigen as idOrigenMayor, rm.localidad_idDestino  as odDestinoMayor " + //34-35
			"FROM vrtitinerario i " +
			"INNER JOIN vrtdetiti di ON di.itinerario_id=i.itinerario_id " +
			"LEFT JOIN vrmbus b ON b.bus_id=i.bus_id " +
			"INNER JOIN vrmservicio s ON s.servicio_id=i.servicio_id " +
			"INNER JOIN vrmruta r ON r.ruta_id=di.ruta_id " +
			"INNER JOIN vrmruta rm ON rm.ruta_id=i.ruta_idmayor " +
			"INNER JOIN vrmagencia ao ON ao.agencia_id=di.agencia_idpartida " +
			"INNER JOIN vrmagencia ad ON ad.agencia_id=di.agencia_idllegada " +
			"INNER JOIN vrmlocalidad lo ON lo.localidad_id=r.localidad_idOrigen " +
			"INNER JOIN vrmlocalidad ld ON ld.localidad_id=r.localidad_idDestino " +
			"INNER JOIN vrmtipiti ti ON ti.tipiti_id=i.tipiti_id " +
			"WHERE ";
			if(idItinerario !=null){
				where="i.itinerario_id="+idItinerario+" ";
			}else{
				where="di.d_fecpar between to_date('"+fechaInicio+"','dd/mm/yyyy') AND to_date('"+fechaFinal+"','dd/mm/yyyy') AND " +
					   "r.c_origen=NVL("+origen+",r.c_origen) " +
					   "AND r.c_destino=NVL("+destino+",r.c_destino) " +
					   "AND s.c_denominacion=NVL("+Servicio+",s.c_denominacion) " +
					   "AND ti.c_denominacion =NVL("+tipoDeItinerario+",ti.c_denominacion) ";
			}
			sql+=where+" And i.N_EsAnulado=0 And i.C_EstReg='"+Constantes.VALUE_ACTIVO+"' ORDER BY " + criterioOrden;
			
			log.info(sql);
			List<?> result = getSession().createSQLQuery(sql).list();
			for(int i=0; i<result.size(); i++){
				Object[] obj = (Object[]) result.get(i);
				DetalleItinerario detalleItinerario = new DetalleItinerario();
				TipoItinerario tipoItinerario = new TipoItinerario();
				tipoItinerario.setId(((BigDecimal)obj[24]).intValue());
				tipoItinerario.setDenominacion((obj[25]).toString());
				
				
				Localidad localidadOrigenMayor= new Localidad();
				localidadOrigenMayor.setId(((BigDecimal)obj[34]).intValue());
				Localidad localidadDestinoMayor= new Localidad();
				localidadDestinoMayor.setId(((BigDecimal)obj[35]).intValue());
				Ruta rutaMayor= new Ruta();
				rutaMayor.setLocalidadOrigen(localidadOrigenMayor);
				rutaMayor.setLocalidadDestino(localidadDestinoMayor);
				
				
				Itinerario itinerario = new Itinerario();
				itinerario.setId(((BigDecimal)obj[0]).longValue());
				itinerario.setSecuenciaTramo(obj[21].toString());
				itinerario.setTipoItinerario(tipoItinerario);
				itinerario.setRuta(rutaMayor);
				Bus bus = new Bus();
				if (obj[1] !=null){
					bus.setId(((BigDecimal)obj[1]).intValue());
					bus.setCodigo(obj[2].toString());
				}
				
				itinerario.setBus(bus);
				Servicio servicio = new Servicio();
				servicio.setId(((BigDecimal)obj[3]).intValue());
				servicio.setDenominacion(obj[4].toString());
				servicio.setNumeroAsientosPiso1(((BigDecimal) obj[29]).intValue());
				servicio.setNumeroAsientosPiso2(obj[30]==null?0 :((BigDecimal) obj[30]).intValue());
				itinerario.setServicio(servicio);
				Ruta ruta = new Ruta();
				ruta.setId(((BigDecimal)obj[5]).intValue());
				ruta.setOrigen(obj[6].toString());
				ruta.setHorasViaje(((BigDecimal)obj[33]).doubleValue());
				Localidad localidadOrigen = new Localidad();
				localidadOrigen.setId(((BigDecimal)obj[22]).intValue());
				ruta.setLocalidadOrigen(localidadOrigen);
				Localidad localidadDestino = new Localidad();
				localidadDestino.setId(((BigDecimal)obj[23]).intValue());
				ruta.setLocalidadDestino(localidadDestino);
				Agencia agenciaPartida = new Agencia();
				agenciaPartida.setId(((BigDecimal)obj[19]).intValue());
				agenciaPartida.setNombreCorto(obj[7].toString());
				agenciaPartida.setDenominacion(obj[27].toString());
				detalleItinerario.setId(((BigDecimal)obj[26]).longValue());
				detalleItinerario.setAgenciaPartida(agenciaPartida);
				detalleItinerario.setFechaPartida((Date)obj[8]);
				detalleItinerario.setHoraPartida(obj[9].toString());
				ruta.setDestino(obj[10].toString());
				Agencia agenciaLlegada = new Agencia();
				agenciaLlegada.setId(((BigDecimal)obj[20]).intValue());
				agenciaLlegada.setNombreCorto(obj[11].toString());
				agenciaLlegada.setDenominacion(obj[28].toString());
				detalleItinerario.setAgenciaLlegada(agenciaLlegada);
				detalleItinerario.setFechaLlegada((Date)obj[12]);
				detalleItinerario.setHoraLlegada(obj[13].toString());
				detalleItinerario.setTarifa(((BigDecimal)obj[14]).doubleValue());
				detalleItinerario.setRuta(ruta);
				detalleItinerario.setItinerario(itinerario);
				servicio.setNumeroPisos(((BigDecimal)obj[15]).intValue());
				servicio.setNumeroAsientosPiso1(((BigDecimal)obj[16]).intValue());
				servicio.setNumeroFilasPiso1(((BigDecimal)obj[17]).intValue());
				servicio.setNumeroColumnasPiso1(((BigDecimal)obj[18]).intValue());
				servicio.setNumeroFilasPiso2(obj[31]==null?null :((BigDecimal) obj[31]).intValue());
				itinerario.setFechaRealPartida(obj[32]==null?null:(Date)obj[32]);
				
				lstResult.add(detalleItinerario);
			}
		
		return lstResult;
	}
	

	/*
	 * (non-Javadoc)
	 * @see com.tepsa.sisvyr.model.dao.ItinerarioDAO#buscarPorX(java.util.TreeMap, java.util.List)
	 */
	@Override
	public ArrayList<Itinerario> buscarPorX(TreeMap<String, Object> criteriosBusqueda, List<String> criteriosOrdenar) {
		return (ArrayList<Itinerario>) super.findByX(Itinerario.class, criteriosBusqueda, criteriosOrdenar);
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.tepsa.sisvyr.model.dao.ItinerarioDAO#buscarPorId(java.lang.Long)
	 */
	@Override
	public Itinerario buscarPorId(Long id) {
		return (Itinerario) super.findById(Itinerario.class, id);
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.tepsa.sisvyr.model.dao.ItinerarioDAO#inactivar(java.lang.Long)
	 */
	@Override
	public void inactivar(Long id,Integer idUsuario) throws Exception {
		try{
			int ESTADO_ITINERARIO_ANULADO=1;
			//Valida la existencia de ventas o recervas activas.
			String sql="SELECT COUNT(*) "+
					   "FROM vrtvenpas v " +
					   "INNER JOIN (SELECT MAX(VENPAS_ID) VENPAS_ID,C_NUMCONTROL  FROM VRTVENPAS GROUP BY C_NUMCONTROL) ID_MAX ON (ID_MAX.venpas_id=v.venpas_id)  " +
					   "WHERE v.itinerario_id="+id+" AND v.tipmov_id NOT IN ("+Constantes.ID_TIPMOV_ANULACION+","+Constantes.ID_TIPMOV_ANULACION_SISTEMA+","+Constantes.ID_TIPMOV_DEVOLUCION+") AND v.c_estreg='A' ";
			log.info(sql);
			List<?> result = getSession().createSQLQuery(sql).list();
			int coun=((BigDecimal)result.get(0)).intValue(); 
			if(coun>0)
				throw new ItinerarioException(ItinerarioException.CON_VENTAS); // ItinerarioTieneVentasException();
			
			//Si no hay ventas o reserveas, se realiza la anulacion.
			Itinerario itinerario=buscarPorId(id);
			itinerario.setEsAnulado(ESTADO_ITINERARIO_ANULADO);
			UtilData.auditarRegistro(itinerario, true,(Usuario)Executions.getCurrent().getSession().getAttribute(Constantes.ATRIBUTO_USUARIO), Executions.getCurrent());
			super.update(itinerario);
						
//			String sqll="UPDATE vrtitinerario SET n_esanulado="+ESTADO_ITINERARIO_ANULADO+" WHERE itinerario_id="+id;
//			String sqll="UPDATE vrtitinerario SET n_esanulado="+Constantes.ITINIRARIO_ANULADO+" WHERE itinerario_id="+id;
//			getSession().createSQLQuery(sqll).executeUpdate();
								
		}catch(ItinerarioException itvex){
			if(itvex.getTipo().intValue()==ItinerarioException.CON_VENTAS)
				throw new ItinerarioException(ItinerarioException.CON_VENTAS);
		}
		
	}

	/*
	 * (non-Javadoc)
	 * @see com.tepsa.sisvyr.model.dao.impl.GenericDAOImpl#fechaServer()
	 */
//	@Override
//	public  Calendar fechaServer() {
//		return super.fechaServer();
//	}
	
	/*
	 * (non-Javadoc)
	 * @see com.tepsa.sisvyr.model.dao.impl.GenericDAOImpl#fechaServerLong()
	 */
	@Override
	public String dateServer() {
		return super.dateServer();
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.tepsa.sisvyr.model.dao.ItinerarioDAO#validadIngresoTramos(java.lang.Integer, java.lang.Integer, java.lang.Integer, java.lang.String, java.lang.String, java.lang.Integer, java.lang.Integer)
	 */
	@Override
	public List<DetalleItinerario> validadIngresoTramos(Integer idTipoItinerario,Integer idServicio, Integer idRuta, String fechaPartida,String horaPartida, Integer idAgenciaPartida,
			Integer idAgenciaLlegada) throws Exception {
			String sql =null;
			
			List<DetalleItinerario> lstResult = new ArrayList<DetalleItinerario>();
			sql="Select di.itinerario_id, di.d_FecPar, di.c_HorPar " +
				"From vrtdetiti di "	+
				"Inner Join vrtitinerario i On (i.itinerario_id=di.itinerario_id) " +
				"Where i.TipIti_ID=" + idTipoItinerario + " And i.Servicio_ID=" + idServicio + " And di.Ruta_ID=" + idRuta +
				" And di.d_fecpar=to_date('" + fechaPartida + "','dd/mm/yyyy') And di.c_HorPar='" + horaPartida + "' And di.Agencia_IDPartida=" + idAgenciaPartida +
				" And di.Agencia_IDLlegada=" + idAgenciaLlegada + " And di.C_EstReg='" + Constantes.VALUE_ACTIVO + "' And i.N_EsAnulado=0 AND i.C_EstReg='" + Constantes.VALUE_ACTIVO + "'";	
					
			List<?> result = getSession().createSQLQuery(sql).list();
			for(int i=0; i<result.size(); i++){
				Object[] obj = (Object[]) result.get(i);
				DetalleItinerario detalleItinerario = new DetalleItinerario();
				Itinerario itinerario = new Itinerario();
				
				itinerario.setId(((BigDecimal)obj[0]).longValue());
				detalleItinerario.setFechaPartida((Date)obj[1]);
				detalleItinerario.setHoraPartida(obj[2].toString());
				detalleItinerario.setItinerario(itinerario);
				
				lstResult.add(detalleItinerario);
			}
		return lstResult;
	}
	@Override
	public boolean validaCapasidadServicioAsiento(Long idItinerario,Servicio servicio) throws Exception {
		String sql="";
		List<?> result=null;
		
		if(servicio.getNumeroPisos() ==2){ //Servicio de dos Niveles
			/*Pregunta por el piso del primer nivel*/
			sql="SELECT v.venpas_id "+
				"FROM vrtvenpas v "+
					"INNER JOIN (SELECT MAX(vp.venpas_id)venpas_id FROM VRTVENPAS vp WHERE vp.itinerario_id="+idItinerario+" GROUP BY vp.c_numcontrol)venpas_idmax "+
							"ON (venpas_idmax.venpas_id=v.venpas_id) "+
				"WHERE v.itinerario_id="+idItinerario+" " +
					"AND v.n_numasiento >"+servicio.getNumeroAsientosPiso1()+" "+
					"AND v.n_numpiso=0 " +
					"AND v.tipmov_id not in("+Constantes.ID_TIPMOV_ANULACION+","+Constantes.ID_TIPMOV_ANULACION_SISTEMA+","+Constantes.ID_TIPMOV_DEVOLUCION+") "+
					"AND v.c_estreg='"+Constantes.VALUE_ACTIVO+"' ";
			
			log.info(sql);
			result = getSession().createSQLQuery(sql).list();
			if(result.size()==0){
				/*Pregunta por el piso del segundo nivel*/
				sql="SELECT v.venpas_id "+
					"FROM vrtvenpas v "+
						"INNER JOIN (SELECT MAX(vp.venpas_id)venpas_id FROM VRTVENPAS vp WHERE vp.itinerario_id="+idItinerario+" GROUP BY vp.c_numcontrol)venpas_idmax "+
								"ON (venpas_idmax.venpas_id=v.venpas_id) "+
					"WHERE v.itinerario_id="+idItinerario+" " +
						"AND v.n_numasiento >"+servicio.getNumeroAsientosPiso2()+" "+
						"AND v.n_numpiso=1 " +
						"AND v.tipmov_id not in("+Constantes.ID_TIPMOV_ANULACION+","+Constantes.ID_TIPMOV_ANULACION_SISTEMA+","+Constantes.ID_TIPMOV_DEVOLUCION+") "+
						"AND v.c_estreg='"+Constantes.VALUE_ACTIVO+"' ";
				
				log.info(sql);
				result = getSession().createSQLQuery(sql).list();
			}
		}else{ //Servicio de un Nivel
			sql="SELECT v.venpas_id "+
			    "FROM vrtvenpas v "+
					"INNER JOIN (SELECT MAX(vp.venpas_id)venpas_id FROM VRTVENPAS vp WHERE vp.itinerario_id="+idItinerario+" GROUP BY vp.c_numcontrol)venpas_idmax "+
							"ON (venpas_idmax.venpas_id=v.venpas_id) "+
			    "WHERE v.itinerario_id="+idItinerario+" " +
					"AND v.n_numasiento >"+servicio.getNumeroAsientosPiso1()+" " +
					"AND v.n_numpiso=0 "+
					"AND v.tipmov_id not in("+Constantes.ID_TIPMOV_ANULACION+","+Constantes.ID_TIPMOV_ANULACION_SISTEMA+","+Constantes.ID_TIPMOV_DEVOLUCION+") "+
					"AND v.c_estreg='"+Constantes.VALUE_ACTIVO+"' ";
			
			log.info(sql);
			result = getSession().createSQLQuery(sql).list();
		}	 
				
		if(result.size()>0)
			return true;
		else
			return false;
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.tepsa.sisvyr.model.dao.ItinerarioDAO#ObtienerItinerariosAfectadosUpdate(java.lang.String, java.lang.String, java.lang.Integer)
	 */
	@Override
	public Long ObtienerItinerarioAActualizar(String fechaPartida,Integer idRuta, String secuenciaTramo, String horaPartida, Integer idServicio, 
			Integer idTipoItinerario, Integer idAgenciaPartida, Integer idAgenciaLlegada){
		String sql="SELECT i.itinerario_id "+
				   "FROM vrtitinerario i "+
				   "WHERE i.d_fecpar=to_date('"+fechaPartida+"','"+Constantes.DATE_FORMAT+"') AND i.ruta_idmayor="+idRuta+" " +
				   "AND i.c_sectra='"+secuenciaTramo+"' AND i.c_horpar='"+horaPartida+"' " +
				   "AND i.servicio_id="+idServicio+ " AND i.tipiti_id="+idTipoItinerario+" " +
				   "AND i.agencia_idpartida="+idAgenciaPartida+" AND i.agencia_idllegada="+idAgenciaLlegada+" "+
				   "AND i.c_estreg='A' AND I.N_ESANULADO=0 ";
		
		log.info(sql);
		
		List<?> result = getSession().createSQLQuery(sql).list();
		Long idItinerario=(long) 0;
		if(result.size()>0)
			idItinerario=(new Long(result.get(0).toString()));
		
		return idItinerario;
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.tepsa.sisvyr.model.dao.ItinerarioDAO#ItinerariosAActualizar(java.lang.String, java.lang.String, java.lang.Integer, java.lang.String, java.lang.String, java.lang.Integer, java.lang.Integer, java.lang.Integer, java.lang.Integer)
	 */
	@Override
	public List<VentaPasaje> buscarItinerariosAActualizar(String fechaInicio,String fechaFin,Integer idRuta, String secuenciaTramo, String horaPartida, Integer idServicio, 
			Integer idTipoItinerario, Integer idAgenciaPartida, Integer idAgenciaLlegada){
		String sql="SELECT i.itinerario_id, r.c_origen, r.c_destino, di.d_fecpar, di.c_horpar,  p.c_nomape, "+ //0-5
					       "v.n_numasiento, v.c_tiptra, v.venpas_id, r.ruta_id,i.d_fecpar as FechaPArtidaItinerario, "+ //6-10
					       "v.c_numcontrol "+ //11-11
					"FROM vrtitinerario i  "+
					"INNER JOIN vrtdetiti di ON (di.itinerario_id=i.itinerario_id) "+
					"INNER JOIN vrmruta r ON (r.ruta_id=di.ruta_id) "+
					"LEFT OUTER JOIN (SELECT v.c_numcontrol, v.ruta_id, v.venpas_id,v.pasajero_id,v.itinerario_id,v.n_numasiento,v.c_tiptra, v.tipmov_id "+ 
						              "FROM vrtvenpas v " +
						              "INNER JOIN (SELECT MAX(venpas_id)venpas_id, c_numcontrol " +
												   "FROM vrtvenpas " +
												   "GROUP BY c_numcontrol) max_venta " +
										     "ON max_venta.venpas_id=v.venpas_id " +
						              "WHERE v.tipmov_id NOT IN("+Constantes.ID_TIPMOV_ANULACION+","+Constantes.ID_TIPMOV_ANULACION_SISTEMA+","+Constantes.ID_TIPMOV_DEVOLUCION+") AND v.c_estreg='A' "+
						              " ) v ON (v.itinerario_id=i.itinerario_id AND v.ruta_id=r.ruta_id)"+
					"LEFT OUTER JOIN vrmpasajero p ON (p.pasajero_id=v.pasajero_id) "+
					"WHERE i.d_fecpar BETWEEN to_date('"+fechaInicio+"','dd/MM/yyyy') AND to_date('"+fechaFin+"','dd/MM/yyyy') AND i.ruta_idmayor="+idRuta+" "+ 
					"AND i.c_sectra='"+secuenciaTramo+"' AND i.c_horpar='"+horaPartida+"' AND i.servicio_id="+idServicio+ " "+
					"AND i.tipiti_id="+idTipoItinerario+" AND i.agencia_idpartida="+idAgenciaPartida+" "+
					"AND i.agencia_idllegada="+idAgenciaLlegada+" AND i.c_estreg='A' AND I.N_ESANULADO=0 "+
					"ORDER BY i.itinerario_id,di.d_fecpar, di.c_horpar, r.c_origen,r.c_destino";
		
		log.info(sql);
		
		List<?> result = getSession().createSQLQuery(sql).list();
		List<VentaPasaje>lisResult= new ArrayList<VentaPasaje>();
		for(int i=0; i<result.size(); i++){
			Object[] obj = (Object[]) result.get(i);
			
			VentaPasaje ventaPasaje=new VentaPasaje();
			Itinerario itinerario=new Itinerario();
			DetalleItinerario  detalleItinerario=new DetalleItinerario();
			Ruta ruta=new Ruta();
			Pasajero pasajero=null;
			
			itinerario.setId(((BigDecimal)obj[0]).longValue());
			itinerario.setFechaPartida((Date)obj[10]);
			ruta.setOrigen(obj[1].toString());
			ruta.setDestino(obj[2].toString());
			ruta.setId(((BigDecimal)obj[9]).intValue());
			detalleItinerario.setFechaPartida((Date)obj[3]);
			detalleItinerario.setHoraPartida(obj[4].toString());
			detalleItinerario.setItinerario(itinerario);
			detalleItinerario.setRuta(ruta);
			if(obj[8]!=null){
				pasajero=new Pasajero();
				pasajero.setNombresApellidos(obj[5].toString());
				ventaPasaje.setFechaPartida((Date)obj[3]);
				ventaPasaje.setHoraPartida(obj[4].toString());
				ventaPasaje.setNumeroControl(obj[11].toString());
			}
			ventaPasaje.setNumeroAsiento(obj[6]!=null?((BigDecimal)obj[6]).intValue(): null);
			ventaPasaje.setTipoTransaccion(obj[7]!=null?obj[7].toString(): null);
			ventaPasaje.setId(obj[8]!=null?((BigDecimal)obj[8]).longValue(): null);
			ventaPasaje.setItinerario(itinerario);
			ventaPasaje.setPasajero(pasajero);
			ventaPasaje.setDetalleItinerario(detalleItinerario);
			ventaPasaje.setRuta(ruta);
			
			lisResult.add(ventaPasaje);
		}
		return lisResult;
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.tepsa.sisvyr.model.dao.ItinerarioDAO#buscarRutas(java.lang.String, java.lang.String)
	 */
	@Override
	public List<DetalleItinerario>buscarRutas(String fechaInicio, String fechaFin,Integer idServicio, Integer idOrigen,
			Integer idDestino, String horaPartida, Long idItinerario, Boolean mostrarDetalle,Integer idTipoItinerario ){
		
		String select="SELECT r.ruta_id, di.c_horpar, "+ //0-1
					 "r.c_origen as Origen, r.c_destino as Destino, s.servicio_id, s.c_denominacion as Servicio, " +//2-5
					 "di.n_tarifa, NVL(di.n_tarifaLista,0)n_tarifaLista "; //6-7";
		
		/* Cuando mostrarDetalle es TRUE*/
		if(mostrarDetalle)
			select+=" ,di.d_fecpar, di.detiti_id as itinerarioDetalle, i.itinerario_id as idItinerario "; //7-10
		
		String sql=select+" "+
			"FROM vrtitinerario i "+
				"INNER JOIN vrtdetiti di ON (di.itinerario_id=i.itinerario_id) "+
				"INNER JOIN vrmruta r ON (r.ruta_id=di.ruta_id) " +
				"INNER JOIN vrmservicio s ON (s.servicio_id=i.servicio_id) "+
			"WHERE di.d_fecpar BETWEEN to_date('"+fechaInicio+"','dd/MM/yyyy') AND to_date('"+fechaFin+"','dd/MM/yyyy') AND i.c_estreg='"+Constantes.VALUE_ACTIVO+"' AND i.n_esanulado="+Constantes.FALSE_VALUE+" " +
				"AND di.c_estreg='"+Constantes.VALUE_ACTIVO+"' ";
		
		
//		String sql="SELECT r.ruta_id, di.c_horpar, "+ //0-1
//					       "r.c_origen as Origen, r.c_destino as Destino, s.servicio_id, s.c_denominacion as Servicio, " +//2-5
//					       "di.n_tarifa "+ //6-6
//					"FROM vrtitinerario i "+
//						"INNER JOIN vrtdetiti di ON (di.itinerario_id=i.itinerario_id) "+
//						"INNER JOIN vrmruta r ON (r.ruta_id=di.ruta_id) " +
//						"INNER JOIN vrmservicio s ON (s.servicio_id=i.servicio_id) "+
//					"WHERE di.d_fecpar BETWEEN '"+fechaInicio+"' AND '"+fechaFin+"' AND i.c_estreg='"+Constantes.VALUE_ACTIVO+"' AND i.n_esanulado="+Constantes.FALSE_VALUE+" " +
//						"AND di.c_estreg='"+Constantes.VALUE_ACTIVO+"' ";
		
		if(idItinerario!=null && idItinerario!=0)
			sql+=" AND i.itinerario_id="+idItinerario;
		else{
			if(idServicio!=null && idServicio!=0)
				sql+=" AND i.servicio_id="+idServicio;
			if(idOrigen!=null && idOrigen!=0)	
				sql+=" AND r.localidad_idOrigen="+idOrigen;
			if(idDestino!=null && idDestino!=0)
				sql+=" AND r.localidad_idDestino="+idDestino;
			if(horaPartida!=null && horaPartida!="")
				sql+=" AND di.c_horpar='"+horaPartida+"' ";
			if(idTipoItinerario!=null && idTipoItinerario!=0)
				sql+=" AND i.tipIti_id="+idTipoItinerario;
		}
				
		/* Cuando mostrarDetalle es TRUE*/
		if(mostrarDetalle){
			//sql+=" GROUP BY i.itinerario_id, r.ruta_id,di.c_horpar,r.c_origen, r.c_destino, s.servicio_id, s.c_denominacion, di.n_tarifa ";
			sql+=" ORDER BY di.d_fecpar, di.c_horpar";
		}else{
			sql+=" GROUP BY r.ruta_id,di.c_horpar,r.c_origen, r.c_destino, s.servicio_id, s.c_denominacion, di.n_tarifa, di.n_tarifaLista ";
			sql+=" ORDER BY di.c_horpar";
		}
			
		log.info(sql);
		
		List<?> result = getSession().createSQLQuery(sql).list();
		List<DetalleItinerario>lisResult= new ArrayList<DetalleItinerario>();
		for(int i=0; i<result.size(); i++){
			Object[] obj = (Object[]) result.get(i);
			DetalleItinerario detalleItinerario= new DetalleItinerario();
			Ruta ruta= new Ruta();
			Servicio servicio= new Servicio();
			Itinerario itinerario= new Itinerario();
			
			ruta.setId(((BigDecimal)obj[0]).intValue());
			ruta.setOrigen(obj[2].toString());
			ruta.setDestino(obj[3].toString());
			
			servicio.setId(((BigDecimal)obj[4]).intValue());
			servicio.setDenominacion(obj[5].toString());
			
			itinerario.setServicio(servicio);
			
			/* Cuando mostrar detalle es TRUE*/
			if (mostrarDetalle){
				detalleItinerario.setFechaPartida((Date)obj[8]);
				detalleItinerario.setId(((BigDecimal)obj[9]).longValue());
				itinerario.setId(((BigDecimal)obj[10]).longValue());
			}
			
			detalleItinerario.setItinerario(itinerario);
			detalleItinerario.setHoraPartida(obj[1].toString());
			detalleItinerario.setTarifa(((BigDecimal)obj[6]).doubleValue());
			detalleItinerario.setTarifaLista(((BigDecimal)obj[7]).doubleValue());
			detalleItinerario.setRuta(ruta);
					
				
			lisResult.add(detalleItinerario);
		}
		return lisResult;
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.tepsa.sisvyr.model.dao.ItinerarioDAO#actualizarTarifa(java.lang.Double, java.lang.String, java.lang.String, java.lang.String, java.lang.Integer, java.lang.Integer, java.lang.Double, java.lang.Long, java.lang.String, java.lang.String, java.lang.Double)
	 */
	@Override
	public void actualizarTarifa(Double tarifaNueva, String horaPartida,String fechaInicio, String fechaFin, Integer idRuta, Integer idServicio,Double tarifaActual, 
			Long idItinerarioDetalle, String usuarioMod, String ipMod, Double tarifaLista){
		
		String sql="UPDATE vrtdetiti "+
						"SET vrtdetiti.n_tarifa="+tarifaNueva+ ",n_tarifaLista="+tarifaLista+", audusumod='"+usuarioMod+"', audipmodi='"+ipMod+"' "+ 
					"WHERE vrtdetiti.c_horpar='"+horaPartida+"' AND vrtdetiti.ruta_id="+idRuta+" AND vrtdetiti.n_tarifa="+tarifaActual+" "+
						"AND vrtdetiti.d_fecpar>=to_date('"+fechaInicio+"','"+Constantes.DATE_FORMAT+"') and vrtdetiti.d_fecpar<=to_date('"+fechaFin+"','"+Constantes.DATE_FORMAT+"') "+
						"AND vrtdetiti.itinerario_id in(SELECT di.itinerario_id "+
														"FROM Vrtdetiti di "+
															"INNER JOIN vrtitinerario  i ON i.itinerario_id=di.Itinerario_Id "+
														 "WHERE di.c_horpar='"+horaPartida+"' AND di.ruta_id="+idRuta+" AND di.n_tarifa="+tarifaActual+" "+
													 		"AND i.servicio_id="+idServicio+" "+
													 		"AND di.d_fecpar>=to_date('"+fechaInicio+"','"+Constantes.DATE_FORMAT+"') and di.d_fecpar<=to_date('"+fechaFin+"','"+Constantes.DATE_FORMAT+"') " +
													 		"AND i.n_esanulado="+Constantes.FALSE_VALUE+" AND i.c_estreg='"+Constantes.VALUE_ACTIVO+"' "+
													    ")  " +
						"AND vrtdetiti.c_Estreg='A' ";
			
			if(idItinerarioDetalle!=null && idItinerarioDetalle>0)
				sql+="AND vrtdetiti.detiti_id="+idItinerarioDetalle;

			log.info(sql);
			
			getSession().createSQLQuery(sql).executeUpdate();
	}
	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.model.dao.ItinerarioDAO#buscarItinerarioByRutaMayor(java.lang.String, java.lang.String, java.lang.Integer, java.lang.Integer)
	 */
	@Override
	public List<Itinerario> buscarItinerarioByRutaMayor(String fechaPartida,String horaPartida, Integer localidadIdOrigen,Integer localidadIdDestino) throws Exception {
		// TODO Auto-generated method stub
		horaPartida=horaPartida!=null?"'"+horaPartida+"'":null;
		
		String sql="SELECT i.itinerario_id "
					   + ",r.c_origen "
					   + ",r.c_Destino "
					   + ",s.c_denominacion servicio "
					   + ",i.d_Fecpar "
					   + ",i.c_horpar "
					   + ",r.ruta_id "
				 + "FROM VRTITINERARIO i "
				   + "INNER JOIN VRMRUTA r ON (r.ruta_id=i.ruta_idmayor) "
				   + "INNER JOIN VRMSERVICIO s ON (s.servicio_id=i.servicio_id) "
				 + "WHERE r.localidad_idorigen="+localidadIdOrigen+" "
				   + "AND r.localidad_iddestino="+localidadIdDestino+" "
				   + "AND i.d_fecpar='"+fechaPartida+"' "
				   + "AND i.c_horpar=NVL("+horaPartida+",i.c_horpar) "
				   + "AND i.n_esanulado="+Constantes.FALSE_VALUE+" "
				   + "AND i.c_Estreg='A' "
				 + "ORDER BY i.c_horpar";
		log.info(sql);
		List<?>result=getSession().createSQLQuery(sql).list();
		List<Itinerario>listItinerarios=new ArrayList<Itinerario>();
		for(int x=0;x<result.size();x++){
			Object[] obj=(Object[]) result.get(x);
			
			Ruta ruta=new Ruta();
			ruta.setId(((BigDecimal)obj[6]).intValue());
			ruta.setOrigen(obj[1].toString());
			ruta.setDestino(obj[2].toString());
			
			Servicio servicio=new Servicio();
			servicio.setDenominacion(obj[3].toString());
			
			Itinerario itinerario=new Itinerario(((BigDecimal)obj[0]).longValue());
			itinerario.setFechaPartida((Date)obj[4]);
			itinerario.setHoraPartida(obj[5].toString());
			itinerario.setRuta(ruta);
			itinerario.setServicio(servicio);
			
			listItinerarios.add(itinerario);
		}
		
		
		
		return listItinerarios;
	}
	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.model.dao.ItinerarioDAO#buscarItinerariosByVentaTramo(java.lang.String, java.lang.String, java.lang.Integer, java.lang.Integer, java.lang.String, java.lang.String)
	 */
	@Override
	public List<Itinerario> buscarItinerariosByVentaTramo(String fechaInicial,String fechaFinal, Integer rutaIdMayor, Integer rutaIdTramo,String horaPartidaTramo, String horaPartidaItinerario)throws Exception {
		// TODO Auto-generated method stub
		String sql="SELECT di.itinerario_id,di.ruta_id, di.d_fecpar "
				 + "FROM VRTDETITI di "
				    + "INNER JOIN VRTITINERARIO i ON (i.itinerario_id=di.itinerario_id) "
				 + "WHERE di.ruta_id="+rutaIdTramo+" " //-->identificador de la rutaTramo
				 	+ "AND di.d_Fecpar BETWEEN '"+fechaInicial+"' AND '"+fechaFinal+"' "
				 	+ "AND di.c_horpar='"+horaPartidaTramo+"' "
				 	+ "AND i.ruta_idmayor="+rutaIdMayor+" "
				 	+ "AND i.c_horpar='"+horaPartidaItinerario+"' "
				 	+ "AND i.c_estreg='"+Constantes.VALUE_ACTIVO+"' "
				 	+ "AND i.n_esanulado='0' "
				 	+ "AND di.c_estreg='"+Constantes.VALUE_ACTIVO+"' "
				+ "ORDER BY di.d_Fecpar, di.c_horpar";
		log.info(sql);
		List<?>result=getSession().createSQLQuery(sql).list();
		List<Itinerario>listItinerarios=new ArrayList<Itinerario>();
		for(int x =0;x<result.size();x++){
			Object[] obj=(Object[]) result.get(x);
			
			Ruta ruta=new Ruta(((BigDecimal)obj[1]).intValue());
			Itinerario itinerario=new Itinerario(((BigDecimal)obj[0]).longValue());
			itinerario.setRuta(ruta);
			itinerario.setFechaPartida((Date)obj[2]);
			
			listItinerarios.add(itinerario);
		}
		
		
		return listItinerarios;
	}
}
