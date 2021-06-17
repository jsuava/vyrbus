package com.cystesoft.vyrbus.model.dao.impl;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.TreeMap;
import java.util.Map.Entry;

import com.cystesoft.vyrbus.model.bean.Agencia;
import com.cystesoft.vyrbus.model.bean.DetalleItinerario;
import com.cystesoft.vyrbus.model.bean.Itinerario;
import com.cystesoft.vyrbus.model.bean.Pasajero;
import com.cystesoft.vyrbus.model.bean.PreferenciaAlimentaria;
import com.cystesoft.vyrbus.model.bean.Promocion;
import com.cystesoft.vyrbus.model.bean.Ruta;
import com.cystesoft.vyrbus.model.bean.Servicio;
import com.cystesoft.vyrbus.model.bean.Transbordo;
import com.cystesoft.vyrbus.model.bean.Usuario;
import com.cystesoft.vyrbus.model.bean.VentaPasaje;
import com.cystesoft.vyrbus.model.bean.report.RptAvanceBus;
import com.cystesoft.vyrbus.model.bean.report.RptAvanceBuses;
import com.cystesoft.vyrbus.model.dao.ReportesDAO;
import com.cystesoft.vyrbus.service.util.Constantes;

/**
 * 
 * @author José Abanto
 *
 */
public class ReportesDAOImpl extends GenericDAOImpl implements ReportesDAO {

	/*
	 * (non-Javadoc)
	 * @see com.tepsa.sisvyr.model.dao.ReportesDAO#tarifario(java.lang.String, java.lang.String, java.lang.Integer, java.lang.Integer)
	 */
	@Override
	public ArrayList<DetalleItinerario> tarifario(String fechaInical,String fechaFinal, Integer idServicio, Integer idLocalidad,boolean incluirTarifaCero, Integer idRuta)throws Exception {
//		String criterios=idLocalidad==""?"":" AND ri.localidad_idorigen="+idLocalidad+" AND rt.localidad_idorigen="+idLocalidad+" ";
//		String criterios2=idLocalidad==""?"":" AND ri.localidad_iddestino="+idLocalidad+" AND rt.localidad_iddestino="+idLocalidad+" ";

		String sql="SELECT ri.c_origen AS ORIGEN_RUTA, ri.c_destino AS DESTINO_RUTA, i.d_fecpar AS F_PARTIDA_RUTA, "+ 
					       "i.c_horpar AS H_PARTIDA_RUTA, s.c_denominacion AS SERVICIO, rt.c_origen AS ORIGEN_TRAMO,  "+
					       "rt.c_destino AS DESTINO_TRAMO, di.d_fecpar AS F_PARTIDA_TRAMO, di.c_horpar AS H_PARTIDA_TRAMO, "+ 
					       "di.n_tarifa AS TARIFA,i.itinerario_id, di.detiti_id, rt.ruta_id  "+
					"FROM vrtitinerario i  "+
						"INNER JOIN vrtdetiti di ON (di.itinerario_id=i.itinerario_id) "+ 
						"INNER JOIN vrmruta ri ON (ri.ruta_id=i.ruta_idmayor)  "+
						"INNER JOIN vrmruta rt ON (rt.ruta_id=di.ruta_id)  "+
						"INNER JOIN vrmservicio s ON (s.servicio_id=i.servicio_id) "+ 
					"WHERE di.d_fecpar BETWEEN to_date('"+fechaInical+"','dd/MM/yyyy') AND to_date('"+fechaFinal+"','dd/MM/yyyy') "+ 
						"AND i.servicio_id = NVL("+idServicio+",i.servicio_id ) "+
						"AND i.n_esanulado=0 AND i.c_estreg='A' " +
						"AND rt.localidad_idDestino =NVL("+idLocalidad+",rt.localidad_idDestino) "+
						"AND rt.ruta_id=NVL("+idRuta+",rt.ruta_id) ";
					if(incluirTarifaCero==false)
						sql+="AND di.n_tarifa>0 ";
			
		if(idLocalidad!=null){
			//Obtiene la rutas de la localidad origen
			sql+="UNION ALL ";
				sql+="SELECT ri.c_origen AS ORIGEN_RUTA, ri.c_destino AS DESTINO_RUTA, i.d_fecpar AS F_PARTIDA_RUTA, "+ 
					       "i.c_horpar AS H_PARTIDA_RUTA, s.c_denominacion AS SERVICIO, rt.c_origen AS ORIGEN_TRAMO,  "+
					       "rt.c_destino AS DESTINO_TRAMO, di.d_fecpar AS F_PARTIDA_TRAMO, di.c_horpar AS H_PARTIDA_TRAMO, "+ 
					       "di.n_tarifa AS TARIFA,i.itinerario_id, di.detiti_id, rt.ruta_id  "+
					"FROM vrtitinerario i  "+
						"INNER JOIN vrtdetiti di ON (di.itinerario_id=i.itinerario_id) "+ 
						"INNER JOIN vrmruta ri ON (ri.ruta_id=i.ruta_idmayor)  "+
						"INNER JOIN vrmruta rt ON (rt.ruta_id=di.ruta_id)  "+
						"INNER JOIN vrmservicio s ON (s.servicio_id=i.servicio_id) "+ 
					"WHERE di.d_fecpar BETWEEN to_date('"+fechaInical+"','dd/MM/yyyy') AND to_date('"+fechaFinal+"','dd/MM/yyyy') "+ 
						"AND i.servicio_id = NVL("+idServicio+",i.servicio_id ) "+
						"AND i.n_esanulado=0 AND i.c_estreg='A' " +
						"AND rt.localidad_idOrigen =NVL("+idLocalidad+",rt.localidad_idorigen) "+
						"AND rt.ruta_id=NVL("+idRuta+",rt.ruta_id) ";
					if(incluirTarifaCero==false)
						sql+="AND di.n_tarifa>0 ";
		}
		
		//Para ordenar la informacion
		sql+="ORDER BY F_PARTIDA_RUTA,H_PARTIDA_RUTA, itinerario_id, detiti_id  ";
		
		
//		String sql="SELECT ORIGEN_RUTA, DESTINO_RUTA,F_PARTIDA_RUTA,H_PARTIDA_RUTA,SERVICIO,ORIGEN_TRAMO, "+
//						  "DESTINO_TRAMO,F_PARTIDA_TRAMO,H_PARTIDA_TRAMO,TARIFA, itinerario_id, detiti_id "+
//					"FROM ( "+
//					"SELECT ri.c_origen AS ORIGEN_RUTA, ri.c_destino AS DESTINO_RUTA, i.d_fecpar AS F_PARTIDA_RUTA, "+ 
//					       "i.c_horpar AS H_PARTIDA_RUTA, s.c_denominacion AS SERVICIO, rt.c_origen AS ORIGEN_TRAMO,  "+
//					       "rt.c_destino AS DESTINO_TRAMO, di.d_fecpar AS F_PARTIDA_TRAMO, di.c_horpar AS H_PARTIDA_TRAMO, "+ 
//					       "di.n_tarifa AS TARIFA,i.itinerario_id, di.detiti_id  "+
//					"FROM vrtitinerario i  "+
//					"INNER JOIN vrtdetiti di ON (di.itinerario_id=i.itinerario_id) "+ 
//					"INNER JOIN vrmruta ri ON (ri.ruta_id=i.ruta_idmayor)  "+
//					"INNER JOIN vrmruta rt ON (rt.ruta_id=di.ruta_id)  "+
//					"INNER JOIN vrmservicio s ON (s.servicio_id=i.servicio_id) "+ 
//					"WHERE i.d_fecpar BETWEEN to_date('"+fechaInical+"','dd/MM/yyyy') AND to_date('"+fechaFinal+"','dd/MM/yyyy') "+ 
//					"AND i.servicio_id LIKE '"+idServicio+"%' "+
//					"AND i.n_esanulado=0 AND i.c_estreg='A' ";
//		sql+=criterios;
//				
//		if(idLocalidad.length()>0){
//			sql+=" UNION ALL "+
//				"SELECT ri.c_origen AS ORIGEN_RUTA, ri.c_destino AS DESTINO_RUTA, i.d_fecpar AS F_PARTIDA_RUTA, "+ 
//				       "i.c_horpar AS H_PARTIDA_RUTA, s.c_denominacion AS SERVICIO, rt.c_origen AS ORIGEN_TRAMO,  "+
//				       "rt.c_destino AS DESTINO_TRAMO, di.d_fecpar AS F_PARTIDA_TRAMO, di.c_horpar AS H_PARTIDA_TRAMO, "+ 
//				       "di.n_tarifa AS TARIFA,i.itinerario_id, di.detiti_id "+
//				"FROM vrtitinerario i  "+
//				"INNER JOIN vrtdetiti di ON (di.itinerario_id=i.itinerario_id) "+ 
//				"INNER JOIN vrmruta ri ON (ri.ruta_id=i.ruta_idmayor)  "+
//				"INNER JOIN vrmruta rt ON (rt.ruta_id=di.ruta_id)  "+
//				"INNER JOIN vrmservicio s ON (s.servicio_id=i.servicio_id) "+ 
//				"WHERE i.d_fecpar BETWEEN to_date('"+fechaInical+"','dd/MM/yyyy') AND to_date('"+fechaFinal+"','dd/MM/yyyy') "+ 
//				"AND i.servicio_id LIKE '"+idServicio+"%' "+
//				"AND i.n_esanulado=0 AND i.c_estreg='A' ";
//			sql+=criterios2;	
//		}
//		sql+=" )TARIFAS   ORDER BY F_PARTIDA_RUTA, H_PARTIDA_RUTA, itinerario_id, detiti_id  ";
		
		log.info(sql);
		List<?> result = getSession().createSQLQuery(sql).list();
		ArrayList<DetalleItinerario> lstResult = new ArrayList<DetalleItinerario>();
		
		for(int i=0; i<result.size(); i++){
			Object[] obj = (Object[])result.get(i);
			
			Servicio servicio= new Servicio();
			servicio.setDenominacion(obj[4].toString());
			
			Itinerario itinerario = new Itinerario();
			Ruta rutaItinerario = new Ruta();
			rutaItinerario.setOrigen(obj[0].toString());
			rutaItinerario.setDestino(obj[1].toString());
			itinerario.setRuta(rutaItinerario);
			itinerario.setServicio(servicio);
			itinerario.setId(((BigDecimal)obj[10]).longValue());
			itinerario.setFechaPartida((Date)obj[2]);
			itinerario.setHoraPartida(obj[3].toString());
			
			Ruta rutaTramo = new Ruta();
			rutaTramo.setId(((BigDecimal)obj[12]).intValue());
			rutaTramo.setOrigen(obj[5].toString());
			rutaTramo.setDestino(obj[6].toString());
			
			DetalleItinerario detalleItinerario = new DetalleItinerario();
			detalleItinerario.setFechaPartida((Date)obj[7]);
			detalleItinerario.setHoraPartida(obj[8].toString());
			detalleItinerario.setTarifa(((BigDecimal) obj[9]).doubleValue());
			detalleItinerario.setRuta(rutaTramo);
			detalleItinerario.setItinerario(itinerario);
			
			lstResult.add(detalleItinerario);
		}
		
		return lstResult;
	}

//	/*
//	 * (non-Javadoc)
//	 * @see com.tepsa.sisvyr.model.dao.ReportesDAO#diarioAcumulado(java.lang.String, java.lang.String, java.lang.String, java.lang.String)
//	 */
//	@Override
//	public ArrayList<VentaPasaje> diarioAcumulado(String fechaInical,String fechaFinal, String servicio, String rutaItinerario)throws Exception {
//		String criteioLocalidad="";
//		if(Integer.valueOf(rutaItinerario).intValue()==Constantes.ID_LOC_LIMA){
//			criteioLocalidad=" AND rm.localidad_idorigen="+Constantes.ID_LOC_LIMA;
//		}else if (rutaItinerario.equals("2"))
//			criteioLocalidad=" AND rm.localidad_iddestino="+Constantes.ID_LOC_LIMA;
//		else
//			criteioLocalidad=" AND rm.localidad_iddestino !="+Constantes.ID_LOC_LIMA+" AND rm.localidad_idorigen !="+Constantes.ID_LOC_LIMA;
//		
//				
//		String sql="SELECT s.c_denominacion as Servicio "+ // 0
//					       ",NVL(s.n_numasipis1,0) + NVL(s.n_numasipis2,0) as Cap "+ // 1
//					       ",i.c_horpar as HoraPartida "+ // 2
//					       ",rm.c_origen as Origen "+ //3
//					       ",rm.c_destino as Destino "+ //4
//					       ",NVL(v1.Cantidad,0) as CantPax "+ //5
//					       ",'Par: '||ap.c_nomcor as AgenciaPartidad "+ //6
//					       ",ap.agencia_id as idAgenciaPartidad "+ //7
//					       ",'Lleg: '||al.c_nomcor as AgenciaLlegada "+ //8
//					       ",al.agencia_id as idAgenciaLlegada "+ //9
//					       ",s.servicio_id as idServicio "+ //10
//					       ",rm.ruta_id as idRuta "+ //11
//					       ",i.itinerario_id "+ //12
//					       ",i.d_fecpar "+ //13
//					       ",NVL(ImportePagado,0) as ImportePagado "+ //14
//					"FROM VRTITINERARIO i "+
//					 "INNER JOIN VRTDETITI di ON (di.itinerario_id=i.itinerario_id) "+
//					 "LEFT JOIN ( "+
//				             "SELECT COUNT(*) as Cantidad,SUM(v.n_imppag)as ImportePagado, v.ruta_id,v.itinerario_id "+
//				             "FROM vrtvenpas v "+
//				             "JOIN (SELECT MAX(venpas_id) venpas_id FROM vrtvenpas GROUP BY venpas_idoriginal) B ON  (v.venpas_id= B.venpas_id) "+
//				             "WHERE v.c_tiptra=1   AND v.tipmov_id NOT IN (5,6,13) AND v.c_estreg='A' "+
//				             "GROUP BY v.ruta_id,v.itinerario_id "+
//				              ")v1 ON (v1.ruta_id=di.ruta_id AND v1.itinerario_id=i.itinerario_id) "+
//					  "INNER JOIN VRMAGENCIA ap ON (ap.agencia_id=di.agencia_idpartida) "+
//					  "INNER JOIN VRMAGENCIA al ON (al.agencia_id=di.agencia_idllegada) "+
//					  "INNER JOIN VRMSERVICIO s ON (s.servicio_id=i.servicio_id) "+
//					  "INNER JOIN VRMRUTA rm ON (rm.ruta_id=i.ruta_idmayor) "+
//					  "WHERE i.d_fecpar BETWEEN '"+fechaInical+"' AND '"+fechaFinal+"' "+ criteioLocalidad +" "+
//					  "AND s.servicio_id=NVL("+servicio+",s.servicio_id) AND i.n_esanulado=0 "+
//					  "ORDER BY  i.c_horpar, i.d_fecpar ,di.d_fecpar, di.c_horpar,di.d_feclle, di.c_horlle ";
//		
//		
//		
//		
//		log.info(sql);
//		
//		List<?> result = getSession().createSQLQuery(sql).list();
//		ArrayList<VentaPasaje> lstResult = new ArrayList<VentaPasaje>();
//		
//		for(int i=0; i<result.size(); i++){
//			Object[] obj = (Object[])result.get(i);
//			
//			Servicio oServicio = new Servicio();
//			oServicio.setId(((BigDecimal)obj[10]).intValue());
//			oServicio.setDenominacion(obj[0].toString());
//			oServicio.setNumeroAsientosPiso1(((BigDecimal)obj[1]).intValue());
//			oServicio.setNumeroServicios(((BigDecimal)obj[5]).intValue());
//			
//			Ruta ruta=new Ruta();
//			ruta.setId(((BigDecimal)obj[11]).intValue());
//			ruta.setOrigen(obj[3].toString());
//			ruta.setDestino(obj[4].toString());
//			
//			Itinerario itinerario=new Itinerario();
//			itinerario.setId(((BigDecimal)obj[12]).longValue());
//			itinerario.setFechaPartida((Date)obj[13]);
//			itinerario.setHoraPartida(obj[2].toString());
//			itinerario.setRuta(ruta);
//			itinerario.setServicio(oServicio);
//			
//			Agencia agenciaPartida=new Agencia();
//			agenciaPartida.setNombreCorto(obj[6].toString());
//			agenciaPartida.setId(((BigDecimal)obj[7]).intValue());
//			
//			Agencia agenciaLlegada=new Agencia();
//			agenciaLlegada.setNombreCorto(obj[8].toString());
//			agenciaLlegada.setId(((BigDecimal)obj[9]).intValue());
//						
//			VentaPasaje ventaPasaje = new VentaPasaje();
//			ventaPasaje.setCantidadPax(obj[5]!=null?((BigDecimal)obj[5]).intValue():0);
//			ventaPasaje.setAgenciaPartida(agenciaPartida);
//			ventaPasaje.setAgenciaLlegada(agenciaLlegada);
//			ventaPasaje.setItinerario(itinerario);
//			ventaPasaje.setServicio(oServicio);
//			ventaPasaje.setImportePagado(obj[14]!=null?((BigDecimal)obj[14]).doubleValue():0.00);
//			
//			lstResult.add(ventaPasaje);
//		}
//
//		return lstResult;
//	}

	/*
	 * (non-Javadoc)
	 * @see com.tepsa.sisvyr.model.dao.ReportesDAO#avanceSemanalXRutas(java.lang.String, java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	public ArrayList<VentaPasaje> avanceSemanalXRutas(String fechaInicial,String fechaFinal, String tipoConsulta, String transaccion)throws Exception {
		String subQuery=" ";
		
		if(tipoConsulta.equals("0")){ //TODOS
			subQuery="AND rm.localidad_idorigen=rm.localidad_idorigen AND rm.localidad_iddestino=rm.localidad_iddestino ";
		}else if (tipoConsulta.equals("1")){ // LIMA PROVINCIA
			subQuery="AND rm.localidad_idorigen="+Constantes.ID_LOC_LIMA+" AND rm.localidad_iddestino!="+Constantes.ID_LOC_LIMA;
		}else if (tipoConsulta.equals("2")){ // PROVINCIA LIMA
			subQuery="AND rm.localidad_idorigen!="+Constantes.ID_LOC_LIMA+" AND rm.localidad_iddestino="+Constantes.ID_LOC_LIMA;
		}else{ // ENTRE PROVINCIAS
			subQuery="AND rm.localidad_idorigen!="+Constantes.ID_LOC_LIMA+" AND rm.localidad_iddestino!="+Constantes.ID_LOC_LIMA;
		}
		
		String sql="SELECT  P1.itinerario_id "+ //0
					       ",P1.ruta_idmayor "+ //1
					       ",P1.d_fecpar "+ //2
					       ",R.c_Origen "+ //3
					       ",R.c_Destino "+ //4
					       ",P1.c_origen Ori_Mayor "+ //5
					       ",P1.c_destino Des_Mayor "+ //6
					       ",P1.c_horpar "+ //7
					       ",P1.c_denominacion AS Servicio "+ //8
					       ",decode(P2.Flag, '1', COUNT(*), 0) Pasajeros "+ //9
					       ",P1.Capbus  "+ //10
					       ",NVL(PR.CantPax,0) PasajeroByRuta "+ //11
					       ",TipoConsulta "+ //12
					"FROM 	(SELECT CASE WHEN rm.localidad_idorigen="+Constantes.ID_LOC_LIMA+" AND rm.localidad_iddestino!="+Constantes.ID_LOC_LIMA+" THEN 1 " +
										"WHEN rm.localidad_idorigen!="+Constantes.ID_LOC_LIMA+" AND rm.localidad_iddestino="+Constantes.ID_LOC_LIMA+" THEN 2 " +
										"WHEN rm.localidad_idorigen!="+Constantes.ID_LOC_LIMA+" AND rm.localidad_iddestino!="+Constantes.ID_LOC_LIMA+" THEN 3 " +
									"END TipoConsulta, " +
									"i.itinerario_id, i.ruta_idmayor, s.c_denominacion, "+
						            "NVL(s.n_numasipis1,0)+NVL(s.n_numasipis2,0) Capbus, i.d_fecpar, i.c_horpar, rm.c_origen  "+
						            ",rm.c_destino, i.servicio_id   "+
						    "FROM vrtitinerario i   "+
							     "INNER JOIN vrmruta rm ON (i.ruta_idmayor = rm.ruta_id)  "+ 
							     "INNER JOIN vrmservicio s ON (i.servicio_id = s.servicio_id)  "+ 
						    "WHERE  i.d_fecpar BETWEEN to_date('"+fechaInicial+"','dd/MM/yyyy') AND to_date('"+fechaFinal+"','dd/MM/yyyy') "+
							     "AND i.n_esanulado=0 "+subQuery+" "+
						   ") P1  "+
						"LEFT JOIN (SELECT '1' Flag, vt.itinerario_id, vt.d_fecpar, vt.c_horpar, vt.d_feclle, vt.c_horlle  "+
						                  ",vt.servicio_id, vt.ruta_id  "+
						           "FROM vrtvenpas vt  "+
							           "INNER JOIN (SELECT MAX(venpas_id) venpas_id FROM vrtvenpas  GROUP BY c_numcontrol) B on (vt.venpas_id = B.venpas_id) " +
							           "INNER JOIN vrtitinerario i on (i.itinerario_id=vt.itinerario_id) "+
						           "WHERE  i.d_fecpar BETWEEN to_date('"+fechaInicial+"','dd/MM/yyyy') AND to_date('"+fechaFinal+"','dd/MM/yyyy') " +
						           		"AND vt.tipmov_id NOT IN ("+Constantes.ID_TIPMOV_ANULACION+","+Constantes.ID_TIPMOV_ANULACION_SISTEMA+","+Constantes.ID_TIPMOV_DEVOLUCION+") " +
						           		"AND vt.c_tiptra=1  "+
						           ") P2 ON  (P2.itinerario_id = P1.itinerario_id) "+ 
			            "INNER JOIN VRMRUTA rm ON (rm.ruta_id=P1.ruta_idmayor) "+
						"INNER JOIN VRTDETITI di ON (di.itinerario_id=P1.itinerario_id) "+
						"INNER JOIN VRMRUTA r ON (r.ruta_id=di.ruta_id) "+
						"LEFT JOIN (SELECT COUNT(*)CantPax, vt.ruta_id, vt.Itinerario_Id FROM vrtvenpas vt "+
					               		"INNER JOIN (SELECT MAX(venpas_id) venpas_id FROM vrtvenpas  GROUP BY c_numcontrol) B on (vt.venpas_id = B.venpas_id) "+
					               		"INNER JOIN vrtitinerario i on (i.itinerario_id=vt.itinerario_id) "+	
					                "WHERE  i.d_fecpar BETWEEN to_date('"+fechaInicial+"','dd/MM/yyyy') AND to_date('"+fechaFinal+"','dd/MM/yyyy') " +
					                    "AND vt.tipmov_id NOT IN ("+Constantes.ID_TIPMOV_ANULACION+","+Constantes.ID_TIPMOV_ANULACION_SISTEMA+","+Constantes.ID_TIPMOV_DEVOLUCION+") " +
					                    "AND vt.c_tiptra=1  "+
					                 "GROUP BY vt.ruta_id, vt.itinerario_id "+
					               ") PR ON  (PR.ruta_id=r.ruta_id AND PR.itinerario_id=P1.itinerario_id) "+
		 			"WHERE NVL(PR.CantPax,0)>=0 " +subQuery+" "+
		 			"GROUP BY R.c_Origen,R.c_Destino,P1.itinerario_id,P1.ruta_idmayor,P1.servicio_id,P1.c_origen,P1.c_destino, "+
					         "P1.c_denominacion,P1.d_fecpar, P1.c_horpar, Flag,P1.Capbus,PR.CantPax,TipoConsulta "+
					"ORDER BY TipoConsulta,P1.d_fecpar,P1.c_horpar,P1.c_origen,P1.c_destino,P1.c_denominacion";

		log.info(sql);
		
		List<?> result = getSession().createSQLQuery(sql).list();
		ArrayList<VentaPasaje> lstResult = new ArrayList<VentaPasaje>();
		
		for(int i=0; i<result.size(); i++){
			Object[] obj = (Object[])result.get(i);

			Servicio servicio=new Servicio();
			servicio.setDenominacion(obj[8].toString());
			servicio.setTotalAsientos(((BigDecimal)obj[10]).intValue());
			
			Ruta rutaMayor= new Ruta();
			rutaMayor.setId(((BigDecimal)obj[1]).intValue());
			rutaMayor.setOrigen(obj[5].toString());
			rutaMayor.setDestino(obj[6].toString());
			
			Itinerario itinerario=new Itinerario();
			itinerario.setId(((BigDecimal)obj[0]).longValue());
			itinerario.setRuta(rutaMayor);
			itinerario.setFechaPartida((Date)obj[2]);
			itinerario.setHoraPartida(obj[7].toString());
			itinerario.setServicio(servicio);
			
			DetalleItinerario detalleItinerario= null;
			if(obj[3]!=null && obj[4]!=null){
				Ruta ruta= new Ruta();
				ruta.setOrigen(obj[3].toString());
				ruta.setDestino(obj[4].toString());
				
				detalleItinerario= new DetalleItinerario();
				detalleItinerario.setRuta(ruta);
				detalleItinerario.setItinerario(itinerario);
			}
						
			VentaPasaje ventaPasaje=new VentaPasaje();
			ventaPasaje.setServicio(servicio);
			ventaPasaje.setItinerario(itinerario);
			ventaPasaje.setCantidadPax(((BigDecimal)obj[9]).intValue());
			ventaPasaje.setCantidadPaxRuta(((BigDecimal)obj[11]).intValue());
			ventaPasaje.setTipoConsulta(((BigDecimal)obj[12]).intValue());
			ventaPasaje.setDetalleItinerario(detalleItinerario);
			lstResult.add(ventaPasaje);		
		}
		
		return lstResult;
	}

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.model.dao.ReportesDAO#consultar(java.util.Date, java.util.Date, long, int, boolean)
	 */
	@SuppressWarnings("rawtypes")
	@Override
	public ArrayList<Object> diarioAcumulado(Date fechaInicial, Date fechaFinal,long idServicio, int limaProvincias, boolean mostrarCuadroIngresos)throws Exception {
		ArrayList<Object> lstReporte = new ArrayList<Object>();
		ArrayList<Object> lstEncabezado = new ArrayList<Object>();
		ArrayList<Object> lstTotal = new ArrayList<Object>();
		String sqlAgenciaPartida ="";
		String sqlAgenciaLlegada ="";
		
		TreeMap<Long, String> agenciasPartida = new TreeMap<Long, String>();
		TreeMap<Long, Double> agenciasPartidaValor = new TreeMap<Long, Double>();
		TreeMap<Long, Double> agenciasPartidaTotal = new TreeMap<Long, Double>();
		TreeMap<Long, String> agenciasLlegada = new TreeMap<Long, String>();
		TreeMap<Long, Double> agenciasLlegadaValor = new TreeMap<Long, Double>();
		TreeMap<Long, Double> agenciasLlegadaTotal = new TreeMap<Long, Double>();
		
		double totalGeneralPasajeros = 0, totalGeneralVentas = 0, totalGeneralNumeroKilometros = 0, totalGeneralNumeroKilometrosAcumulados = 0, totalGeneralNumeroAsientos = 0;
		
		String sql="SELECT  tlGrp.totalGrupo as totalGrupo_itinerario "+
					      ",sr.c_denominacion as nombreClase_itinerario "+
					      ",sr.n_numasipis1+NVL(sr.n_numasipis2,0) as numeroAsientos_itinerario "+
					      ",r.c_origen as localidadOrigen_itinerario "+ 
					      ",r.c_destino as localidadDestino_itinerario "+
					      ",r.n_kilometros as numeroKilometros_itinerario "+
					      ",v2.* "+
					"FROM VRMSERVICIO sr "+
					    "INNER JOIN (SELECT v.servicioID_Itinerario "+ 
					                      ",v.rutaMayorID_Itinerario  "+ 
					                      ",v.horaPartida_Itinerario "+ 
					                      ",v.agenPartidaRuta as idAgeParItiRuta "+ 
					                      ",v.agenLlegadaRuta as idAgeLleItiRuta "+ 
					                      ",SUM(v.cantPaxItiRuta)as cantPaxItiRuta "+ 
					                      ",SUM(v.vtaPizarraItiRuta) as vtaPizarraItiRuta "+ 
					                      ",SUM(v.vtaItiRuta) as vtaItiRuta "+
					                "FROM( "+
					                    "SELECT i.itinerario_id as itinerarioID "+
					                          ",i.servicio_id as servicioID_Itinerario "+
					                          ",i.ruta_idmayor as rutaMayorID_Itinerario "+
					                          ",i.c_horpar as horaPartida_Itinerario "+
					                          ",di.ruta_id as rutaID_DetaItinereario  "+
					                          ",di.agencia_idpartida as agenPartidaRuta "+
					                          ",di.agencia_idllegada as agenLlegadaRuta "+
					                          ",paxIti.cantidad as cantPaxItiRuta ";
											  if(mostrarCuadroIngresos){
						                          sql+=",vtapzir.cantidad as vtaPizarraItiRuta "+
						                        	   ",vtaXiti.cantidad as vtaItiRuta ";
											  }else{
												  sql+=",.00 as vtaPizarraItiRuta "+
							                           ",.00 as vtaItiRuta ";
											  }
					                    sql+="FROM VRTITINERARIO i  "+
					                         "INNER JOIN VRMRUTA r ON (r.ruta_id=i.ruta_idmayor) "+
					                         "INNER JOIN VRTDETITI di ON (di.itinerario_id=i.itinerario_id) "+
					                         //-->recupera la cantidad de pasajeros por itinerario y ruta
					                         "INNER JOIN (SELECT di.ruta_id, vp.itinerario_id, COUNT(*)AS cantidad FROM VRTVENPAS vp "+ 
					                                           "INNER JOIN (SELECT MAX(v.venpas_id)venpas_id FROM VRTVENPAS v " +
					                                           			    "INNER JOIN VRTITINERARIO I ON (i.itinerario_id=v.itinerario_id) " +
					                                           				"WHERE i.d_Fecpar BETWEEN to_date('"+Constantes.FORMAT_DATE.format(fechaInicial)+"','dd/MM/yyyy') AND to_date('"+Constantes.FORMAT_DATE.format(fechaFinal)+"','dd/MM/yyyy') GROUP BY v.c_numcontrol) maxvenpas_id ON (maxvenpas_id.venpas_id=vp.venpas_id) "+
					                                           "INNER JOIN VRTDETITI di ON (di.itinerario_id=vp.itinerario_id AND di.ruta_id=vp.ruta_id) "+
					                                      "WHERE vp.c_tiptra=1  "+
					                                          "AND vp.Forpag_Id IN (1,2) "+
					                                          "AND vp.tipmov_id NOT IN (5,6,13) "+ 
					                                          "AND di.c_estreg='A'  "+
					                                          "AND vp.c_estreg='A' "+
					                                      "GROUP BY di.ruta_id,vp.itinerario_id "+
					                                      ")paxIti ON (paxIti.ruta_id=di.ruta_id AND paxIti.itinerario_id=i.itinerario_id) ";
					                         if(mostrarCuadroIngresos){             
						                        //-->Recupera la venta pizarra por el itinerario y la ruta                           
						                        sql+="INNER JOIN (SELECT NVL(SUM(di.n_tarifa),0)cantidad, di.ruta_id,vp.itinerario_id FROM VRTVENPAS vp "+ 
							                                           "INNER JOIN (SELECT MAX(v.venpas_id)venpas_id FROM VRTVENPAS v " +
							                                           				"INNER JOIN VRTITINERARIO I ON (i.itinerario_id=v.itinerario_id) " +
							                                           				"WHERE i.d_Fecpar BETWEEN to_date('"+Constantes.FORMAT_DATE.format(fechaInicial)+"','dd/MM/yyyy') AND to_date('"+Constantes.FORMAT_DATE.format(fechaFinal)+"','dd/MM/yyyy') GROUP BY v.c_numcontrol) maxvenpas_id ON (maxvenpas_id.venpas_id=vp.venpas_id) "+
							                                           "INNER JOIN VRTDETITI di ON (di.itinerario_id=vp.itinerario_id AND di.ruta_id=vp.ruta_id) "+
							                                      "WHERE vp.c_tiptra=1 "+
							                                          "AND vp.Forpag_Id IN (1,2) "+ 
							                                          "AND vp.tipmov_id NOT IN (5,6,13) "+
							                                          "AND di.c_estreg='A'  "+
							                                          "AND vp.c_estreg='A' "+
							                                      "GROUP BY di.ruta_id,vp.itinerario_id "+
							                                   ")vtapzir  ON (vtapzir.ruta_id=di.ruta_id AND vtapzir.itinerario_id=i.itinerario_id) "+   
							                        //-->Recupera la venta por itinerario y ruta
							                        "INNER JOIN (SELECT NVL(SUM(vp.n_tarifa+vp.n_recargo-vp.n_descuento),0)cantidad, di.ruta_id,vp.itinerario_id FROM VRTVENPAS vp  "+
							                                         "INNER JOIN (SELECT MAX(v.venpas_id)venpas_id FROM VRTVENPAS v " +
							                                         			  "INNER JOIN VRTITINERARIO I ON (i.itinerario_id=v.itinerario_id) " +
							                                         			  "WHERE i.d_Fecpar BETWEEN to_date('"+Constantes.FORMAT_DATE.format(fechaInicial)+"','dd/MM/yyyy') AND to_date('"+Constantes.FORMAT_DATE.format(fechaFinal)+"','dd/MM/yyyy') GROUP BY v.c_numcontrol) maxvenpas_id ON (maxvenpas_id.venpas_id=vp.venpas_id) "+
							                                         "INNER JOIN VRTDETITI di ON (di.itinerario_id=vp.itinerario_id AND di.ruta_id=vp.ruta_id) "+
							                                    "WHERE vp.c_tiptra=1 "+
							                                        "AND vp.Forpag_Id IN (1,2) "+
							                                        "AND vp.tipmov_id NOT IN (5,6,13) "+
							                                        "AND di.c_estreg='A'  "+
							                                        "AND vp.c_estreg='A' "+
							                                    "GROUP BY di.ruta_id,vp.itinerario_id "+
							                                  ")vtaXiti ON (vtaXiti.ruta_id=di.ruta_id AND vtaXiti.itinerario_id=i.itinerario_id) ";
					                         }
					                   sql+="WHERE i.d_Fecpar BETWEEN to_date('"+Constantes.FORMAT_DATE.format(fechaInicial)+"','dd/MM/yyyy') AND to_date('"+Constantes.FORMAT_DATE.format(fechaFinal)+"','dd/MM/yyyy') "+
					                             "AND i.n_esanulado=0 AND i.c_Estreg='A' AND di.c_Estreg='A' "; 
						                    if(idServicio > 0){
						                    	sql+="AND i.servicio_id="+idServicio+" ";
						                    }
					                        sql+="AND i.n_esanulado=0 AND i.c_Estreg='A' AND di.c_Estreg='A' ";
					                        
					                        switch(limaProvincias){ //*****EL IDENTIFICADOR DEL LA LOCALIDAD LIMA ES 13 ******************
												case 0://Lima - Provincias
													sql+="AND r.localidad_idOrigen="+Constantes.ID_LOC_LIMA; // LIKE '%lima%'" ;
													break;
									
												case 1://Provincias - Lima
													sql+=" AND r.localidad_idDestino="+Constantes.ID_LOC_LIMA;//LOWER(r.c_destino) LIKE '%lima%' ";
													break;
												
												case 2://Provincias
													sql+=" AND localidad_idOrigen!="+Constantes.ID_LOC_LIMA+" AND localidad_idDestino!="+Constantes.ID_LOC_LIMA;
													break;
					                        }
					                   sql+= ")v "+
					                " WHERE v.cantPaxItiRuta>0 "+
					                " GROUP BY v.servicioID_Itinerario,v.rutaMayorID_Itinerario,v.horaPartida_Itinerario,v.agenPartidaRuta,v.agenLlegadaRuta "+
					               ")v2  "+
					          "ON (sr.servicio_id=v2.servicioID_Itinerario) "+
					    "INNER JOIN VRMRUTA r ON (r.ruta_id=v2.rutaMayorID_Itinerario) "+
					    "INNER JOIN (SELECT COUNT(*)totalGrupo, i.servicio_id, i.ruta_idmayor,i.c_horpar "+
					                 "FROM VRTITINERARIO i "+
					                   "INNER JOIN (SELECT COUNT(*)as cantidad, vp.itinerario_id "+
					                              "FROM VRTVENPAS vp  "+
					                                   "INNER JOIN (SELECT MAX(v.venpas_id)venpas_id FROM VRTVENPAS v WHERE v.d_fecpar BETWEEN to_date('"+Constantes.FORMAT_DATE.format(fechaInicial)+"','dd/MM/yyyy') AND to_date('"+Constantes.FORMAT_DATE.format(fechaFinal)+"','dd/MM/yyyy') GROUP BY v.c_numcontrol) maxvenpas_id ON (maxvenpas_id.venpas_id=vp.venpas_id) "+
					                                   "INNER JOIN VRTDETITI di ON (di.itinerario_id=vp.itinerario_id AND di.ruta_id=vp.ruta_id) "+
					                              "WHERE vp.c_tiptra=1 "+
					                                  "AND vp.tipmov_id NOT IN (5,6,13) "+
					                                  "AND di.c_estreg='A'  "+
					                                  "AND vp.c_estreg='A' "+
					                              "GROUP BY vp.itinerario_id "+
					                          ")paxIti ON (paxIti.itinerario_id=i.itinerario_id) "+
					                 "WHERE i.n_esanulado= 0  "+
					                    "AND (i.d_fecpar BETWEEN to_date('"+Constantes.FORMAT_DATE.format(fechaInicial)+"','dd/MM/yyyy') AND to_date('"+Constantes.FORMAT_DATE.format(fechaFinal)+"','dd/MM/yyyy')  ) "+ 
					                    "AND paxIti.cantidad>0 "+
					                 "GROUP BY i.servicio_id, i.ruta_idmayor, i.c_horpar "+   
					              ")tlGrp ON (tlGrp.servicio_id=v2.servicioID_Itinerario AND tlGrp.ruta_idmayor=v2.rutaMayorID_Itinerario AND tlGrp.c_horpar=v2.horaPartida_Itinerario) "+
//	              "ORDER BY "+//CONCAT(sr.c_denominacion,sr.servicio_id) "+
//			      "v2.horaPartida_Itinerario,CONCAT(r.c_origen,r.ruta_id) ";                 
					"ORDER BY CONCAT(sr.c_denominacion,sr.servicio_id) "+
					      ",v2.horaPartida_Itinerario,CONCAT(r.c_origen,r.ruta_id) ";
		
	    List<?>resultado=getSession().createSQLQuery(sql).list();  
	    
		sqlAgenciaPartida="SELECT age.agencia_id as idAgencia, age.c_denominacion as Agencia "+
								"FROM VRMAGENCIA age "+
								"WHERE age.agencia_id IN( ";	
				
		sqlAgenciaLlegada=sqlAgenciaPartida;
	      
		for(int f = 0; f < resultado.size(); f++){
			Object[] obj= (Object[]) resultado.get(f);
			long idAgenciaPartida_itinerarioRuta = ((BigDecimal)obj[9]).longValue(); // map.get("IDAGEPARITIRUTA")).longValue();
			long idAgenciaLlegada_itinerarioRuta = ((BigDecimal)obj[10]).longValue(); //map.get("IDAGELLEITIRUTA")).longValue();
	
			agenciasPartida.put(idAgenciaPartida_itinerarioRuta, "");
			agenciasPartidaValor.put(idAgenciaPartida_itinerarioRuta, new Double(0));
			agenciasPartidaTotal.put(idAgenciaPartida_itinerarioRuta, new Double(0));
			
			sqlAgenciaPartida+=(idAgenciaPartida_itinerarioRuta);
			
			agenciasLlegada.put(idAgenciaLlegada_itinerarioRuta, "");
			agenciasLlegadaValor.put(idAgenciaLlegada_itinerarioRuta, new Double(0));
			agenciasLlegadaTotal.put(idAgenciaLlegada_itinerarioRuta, new Double(0));
	
			sqlAgenciaLlegada+=(idAgenciaLlegada_itinerarioRuta);
			
	
			if(f < (resultado.size() - 1)){
				sqlAgenciaPartida+=(", ");
				sqlAgenciaLlegada+=(", ");
			}
		}
		
		
		sqlAgenciaPartida+=(") ");
		sqlAgenciaLlegada+=(") ");


		if(resultado.size() > 0){
			if(!(mostrarCuadroIngresos && limaProvincias == 0)){
				List<?> resultadoAgenciasPartida = getSession().createSQLQuery(sqlAgenciaPartida).list(); //this.getJdbcTemplateSisvyr().queryForList(sqlAgenciaPartida.toString());

				for(int f = 0; f < resultadoAgenciasPartida.size(); f ++){
					Object[] obj=(Object[]) resultadoAgenciasPartida.get(f);

					long idAgencia = ((BigDecimal)obj[0]).longValue(); //oMap.get("IDAGENCIA")).longValue();
					String nombreAgencia =obj[1].toString(); //oMap.get("AGENCIA").toString();

					agenciasPartida.put(idAgencia, nombreAgencia);
				}
			}

			if(!(mostrarCuadroIngresos && limaProvincias == 1)){
				List<?> resultadoAgenciasLlegada =getSession().createSQLQuery(sqlAgenciaLlegada).list();  //this.getJdbcTemplateSisvyr().queryForList(sqlAgenciaLlegada.toString());

				for(int f = 0; f < resultadoAgenciasLlegada.size(); f ++){
					Object[] obj=(Object[]) resultadoAgenciasLlegada.get(f);

					long idAgencia = ((BigDecimal)obj[0]).longValue(); //((BigDecimal) map.get("IDAGENCIA")).longValue();
					String nombreAgencia =obj[1].toString();// map.get("AGENCIA").toString();

					agenciasLlegada.put(idAgencia, nombreAgencia);
				}
			}
		}
		
		if(mostrarCuadroIngresos){
			lstEncabezado.add("SERVICIO");
			lstEncabezado.add("CAP.");
			lstEncabezado.add("SALIDA");
			lstEncabezado.add("RUTA ITINERARIO");
			lstEncabezado.add("#");
			lstEncabezado.add("KM X RUTA");
			lstEncabezado.add("KM ACUMULADOS");
		}else{
			lstEncabezado.add("SERVICIO");
			lstEncabezado.add("CAP.");
			lstEncabezado.add("SALIDA");
			lstEncabezado.add("RUTA ITINERARIO");
			lstEncabezado.add("#");
			lstEncabezado.add("TOTAL PAX");
		}
		
		if(!(mostrarCuadroIngresos && limaProvincias == 0)){
			for( Entry e : agenciasPartida.entrySet()){
				lstEncabezado.add("Part.: " + e.getValue());
			}
		}
	
	
		if(!(mostrarCuadroIngresos && limaProvincias == 1)){
			for(Entry e : agenciasLlegada.entrySet()){
				lstEncabezado.add("Lleg.: " + e.getValue());
			}
		}
	
	
		if(mostrarCuadroIngresos){
			lstEncabezado.add("TOT. ING. PIZARRA");
			lstEncabezado.add("SOL / KM");
			lstEncabezado.add("TOT. ING. REAL");
			lstEncabezado.add("SOL / KM");
			lstEncabezado.add("% DESCUENTO");
		}else{		
			lstEncabezado.add("% OCUPABILIDAD");
		}
	
		lstReporte.add(lstEncabezado);
		
		for(int f = 0; f < resultado.size(); f ++){
			Object[] obj=(Object[]) resultado.get(f);
			ArrayList<Object> lstFila = new ArrayList<Object>();
	
			long idClase_itinerario= ((BigDecimal) obj[6]).longValue(); // oMap.get("SERVICIOID_ITINERARIO")).longValue();
			long idRuta_itinerario= ((BigDecimal) obj[7]).longValue(); //oMap.get("RUTAMAYORID_ITINERARIO")).longValue();
			String horaPartida_itinerario = obj[8].toString(); // oMap.get("HORAPARTIDA_ITINERARIO").toString();
			String localidadOrigen_itinerario = obj[3].toString(); //oMap.get("LOCALIDADORIGEN_ITINERARIO").toString();
			String localidadDestino_itinerario = obj[4].toString();  //oMap.get("LOCALIDADDESTINO_ITINERARIO").toString();
			double numeroKilometros_itinerario =((BigDecimal) obj[5]).doubleValue(); //((BigDecimal) oMap.get("NUMEROKILOMETROS_ITINERARIO")).doubleValue();
			String nombreClase_itinerario =obj[1].toString(); //oMap.get("NOMBRECLASE_ITINERARIO").toString();
			int numeroAsientos_itinerario = ((BigDecimal)obj[2]).intValue(); //oMap.get("NUMEROASIENTOS_ITINERARIO")).intValue();
			long totalGrupo_itinerario = ((BigDecimal)obj[0]).longValue(); //oMap.get("TOTALGRUPO_ITINERARIO")).longValue();
	
			double totalPasajeros = 0, totalVentas = 0, numeroKilometrosAcumulados;
			BigDecimal totalOcupabilidad = new BigDecimal(0);
	
			numeroKilometrosAcumulados = numeroKilometros_itinerario * totalGrupo_itinerario;
	
			boolean perteneceGrupo = true;
			int saltarFilas = 0;
	
			while(perteneceGrupo){
				int filaSiguiente = f + saltarFilas;
	
				if(filaSiguiente >= resultado.size()){
					perteneceGrupo = false;
					continue;
				}
				Object[] objSiguiente=(Object[]) resultado.get(filaSiguiente);
	
				long idClase_itinerarioSiguiente = ((BigDecimal) objSiguiente[6]).longValue();  //oMapSiguiente.get("SERVICIOID_ITINERARIO")).longValue();
				long idRuta_itinerarioSiguiente= ((BigDecimal) objSiguiente[7]).longValue(); //oMapSiguiente.get("RUTAMAYORID_ITINERARIO")).longValue();
				String horaPartida_itinerarioSiguiente = objSiguiente[8].toString(); //oMapSiguiente.get("HORAPARTIDA_ITINERARIO").toString();
				long idAgenciaPartida_itinerarioRutaSiguiente = ((BigDecimal) objSiguiente[9]).longValue(); //oMapSiguiente.get("IDAGEPARITIRUTA")).longValue();
				long idAgenciaLlegada_itinerarioRutaSiguiente = ((BigDecimal) objSiguiente[10]).longValue(); //oMapSiguiente.get("IDAGELLEITIRUTA")).longValue();
				long pasajeros_itinerarioRutaSiguiente = ((BigDecimal) objSiguiente[11]).longValue(); //oMapSiguiente.get("CANTPAXITIRUTA")).longValue();
				double venta_itinerarioRutaSiguiente = ((BigDecimal) objSiguiente[13]).doubleValue(); //oMapSiguiente.get("VTAITIRUTA")).doubleValue();
				double ventaPizarra_itinerarioRutaSiguiente = ((BigDecimal) objSiguiente[12]).doubleValue(); //oMapSiguiente.get("VTAPIZARRAITIRUTA")).doubleValue();
	
				if(idClase_itinerario == idClase_itinerarioSiguiente && idRuta_itinerario == idRuta_itinerarioSiguiente && horaPartida_itinerario.equals(horaPartida_itinerarioSiguiente)){
					double totalPartida = agenciasPartidaValor.get(idAgenciaPartida_itinerarioRutaSiguiente);
					double totalLlegada = agenciasLlegadaValor.get(idAgenciaLlegada_itinerarioRutaSiguiente);
	
					if(mostrarCuadroIngresos){
						totalPartida += venta_itinerarioRutaSiguiente;
						totalLlegada += venta_itinerarioRutaSiguiente;
						totalVentas += venta_itinerarioRutaSiguiente;
						totalPasajeros += ventaPizarra_itinerarioRutaSiguiente;
					}else{
						totalPartida += pasajeros_itinerarioRutaSiguiente;
						totalLlegada += pasajeros_itinerarioRutaSiguiente;
						totalPasajeros += pasajeros_itinerarioRutaSiguiente;
					}
	
					agenciasPartidaValor.put(idAgenciaPartida_itinerarioRutaSiguiente, totalPartida);
					agenciasLlegadaValor.put(idAgenciaLlegada_itinerarioRutaSiguiente, totalLlegada);
					saltarFilas ++;
				}else
					perteneceGrupo = false;
				
			}
	
			totalGeneralNumeroAsientos += numeroAsientos_itinerario * totalGrupo_itinerario;
	
			if(mostrarCuadroIngresos){
				lstFila.add(nombreClase_itinerario);
				lstFila.add(numeroAsientos_itinerario);
				lstFila.add(horaPartida_itinerario);
								
				lstFila.add(localidadOrigen_itinerario + " - " + localidadDestino_itinerario);
				lstFila.add(totalGrupo_itinerario);
				lstFila.add(new BigDecimal(numeroKilometros_itinerario).setScale(2 , RoundingMode.HALF_UP));
				lstFila.add(new BigDecimal(numeroKilometrosAcumulados).setScale(2 , RoundingMode.HALF_UP));
			}else{
				lstFila.add(nombreClase_itinerario);
				lstFila.add(numeroAsientos_itinerario);
				lstFila.add(horaPartida_itinerario);
				lstFila.add(localidadOrigen_itinerario + " - " + localidadDestino_itinerario);
				lstFila.add(totalGrupo_itinerario);
				lstFila.add(new BigDecimal(totalPasajeros).intValue());
			}
	
			for(Entry e : agenciasPartidaValor.entrySet()){
				if(mostrarCuadroIngresos && limaProvincias == 0){
					break;
				}
	
				BigDecimal valor = new BigDecimal((Double) e.getValue());
				double total = agenciasPartidaTotal.get(e.getKey());
				total += (Double) e.getValue();
	
				if(mostrarCuadroIngresos){
					lstFila.add(valor.setScale(2, RoundingMode.HALF_UP));
				}else{
					lstFila.add(valor.intValue());
				}
	
				agenciasPartidaValor.put((Long) e.getKey(), new Double(0));
				agenciasPartidaTotal.put((Long) e.getKey(), total);
			}

			for(Entry e : agenciasLlegadaValor.entrySet()){
				if(mostrarCuadroIngresos && limaProvincias == 1){
					break;
				}
	
				BigDecimal valor = new BigDecimal((Double) e.getValue());
				double total = agenciasLlegadaTotal.get(e.getKey());
				total += (Double) e.getValue();
	
				if(mostrarCuadroIngresos){
					lstFila.add(valor.setScale(2, RoundingMode.HALF_UP));
				}else{
					lstFila.add(valor.intValue());
				}
	
				agenciasLlegadaValor.put((Long) e.getKey(), new Double(0));
				agenciasLlegadaTotal.put((Long) e.getKey(), total);
			}
	
			if(mostrarCuadroIngresos){
				BigDecimal ingresoPizarra = new BigDecimal(totalPasajeros).setScale(2, RoundingMode.HALF_UP);
				BigDecimal ingresoReal = new BigDecimal(totalVentas).setScale(2, RoundingMode.HALF_UP);
	
				lstFila.add(ingresoPizarra);
				lstFila.add(ingresoPizarra.divide(new BigDecimal(numeroKilometrosAcumulados),2 , RoundingMode.HALF_UP));
	
				lstFila.add(ingresoReal);
				lstFila.add(ingresoReal.divide(new BigDecimal(numeroKilometrosAcumulados),2 , RoundingMode.HALF_UP));
	
				if(ingresoPizarra.intValue() == 0){
					lstFila.add(new BigDecimal(0).setScale(2, RoundingMode.HALF_UP));
				}else{
					lstFila.add(ingresoReal.divide(ingresoPizarra, 100, RoundingMode.HALF_UP).subtract(new BigDecimal(1)).multiply(new BigDecimal(100)).setScale( 2, RoundingMode.HALF_UP));
					//22457.50 	22725.00
				}
			}else{
				totalOcupabilidad = new BigDecimal(totalPasajeros * 100).divide(new BigDecimal(numeroAsientos_itinerario * totalGrupo_itinerario), 2, RoundingMode.HALF_UP);
				lstFila.add(totalOcupabilidad);
			}

	
			lstReporte.add(lstFila);
	
			totalGeneralPasajeros += totalPasajeros;
			totalGeneralVentas += totalVentas;
			totalGeneralNumeroKilometros += numeroKilometros_itinerario;
			totalGeneralNumeroKilometrosAcumulados += numeroKilometrosAcumulados;
	
			f += saltarFilas - 1;
		}
	
	
		if(resultado.size() > 0){
			lstTotal.add("TOTALES");
	
			if(mostrarCuadroIngresos){
				lstTotal.add("");
				lstTotal.add("");
				lstTotal.add("");
				lstTotal.add("");
				lstTotal.add(new BigDecimal(totalGeneralNumeroKilometros).setScale(2, RoundingMode.HALF_UP));
				lstTotal.add(new BigDecimal(totalGeneralNumeroKilometrosAcumulados).setScale(2, RoundingMode.HALF_UP));
			}else{
				lstTotal.add("");
				lstTotal.add("");
				lstTotal.add("");
				lstTotal.add("");
				lstTotal.add(new BigDecimal(totalGeneralPasajeros).intValue());
			}
	
			for(Entry e : agenciasPartidaTotal.entrySet()){
				if(mostrarCuadroIngresos && limaProvincias == 0){
					break;
				}
	
				BigDecimal valor = new BigDecimal((Double) e.getValue());
	
				if(mostrarCuadroIngresos){
					lstTotal.add(valor.setScale(2, RoundingMode.HALF_UP));
				}else{
					lstTotal.add(valor.intValue());
				}
			}
	
			for(Entry e : agenciasLlegadaTotal.entrySet()){
				if(mostrarCuadroIngresos && limaProvincias == 1){
					break;
				}
	
				BigDecimal valor = new BigDecimal((Double) e.getValue());
	
				if(mostrarCuadroIngresos){
					lstTotal.add(valor.setScale(2, RoundingMode.HALF_UP));
				}else{
					lstTotal.add(valor.intValue());
				}
			}
	
	
			if(mostrarCuadroIngresos){
				BigDecimal totalIngresoPizarra = new BigDecimal(totalGeneralPasajeros);
				BigDecimal totalIngresoReal = new BigDecimal(totalGeneralVentas);
	
				BigDecimal tgPasajeros = totalIngresoPizarra.divide(new BigDecimal(totalGeneralNumeroKilometrosAcumulados) ,2, RoundingMode.HALF_UP);
				BigDecimal tgVentas = totalIngresoReal.divide(new BigDecimal(totalGeneralNumeroKilometrosAcumulados) ,2, RoundingMode.HALF_UP);
	
				lstTotal.add(totalIngresoPizarra.setScale(2, RoundingMode.HALF_UP));
				lstTotal.add(tgPasajeros);
				lstTotal.add(totalIngresoReal.setScale(2, RoundingMode.HALF_UP));
				lstTotal.add(tgVentas);
				lstTotal.add(totalIngresoReal.divide(totalIngresoPizarra, 100, RoundingMode.HALF_UP).subtract(new BigDecimal(1)).multiply(new BigDecimal(100)).setScale( 2, RoundingMode.HALF_UP));
			}else{
				lstTotal.add(new BigDecimal(totalGeneralPasajeros * 100).divide(new BigDecimal(totalGeneralNumeroAsientos), 2, RoundingMode.HALF_UP));
			}
	
			lstReporte.add(lstTotal);
		}
	
		return lstReporte;
	}

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.model.dao.ReportesDAO#diarioAcumuladoDetallado(java.util.Date, java.util.Date, long, int, boolean)
	 */
	@SuppressWarnings("rawtypes")
	@Override
	public ArrayList<Object> diarioAcumuladoDetallado(Date fechaInicial,Date fechaFinal, long idServicio, int limaProvincias,boolean mostrarCuadroIngresos) throws Exception {
		
		ArrayList<Object> lstReporte = new ArrayList<Object>();
		ArrayList<Object> lstEncabezado = new ArrayList<Object>();
		ArrayList<Object> lstTotal = new ArrayList<Object>();
		String sqlAgenciaPartida ="";
		String sqlAgenciaLlegada ="";

		TreeMap<Long, String> agenciasPartida = new TreeMap<Long, String>();
		TreeMap<Long, Double> agenciasPartidaValor = new TreeMap<Long, Double>();
		TreeMap<Long, Double> agenciasPartidaTotal = new TreeMap<Long, Double>();
		TreeMap<Long, String> agenciasLlegada = new TreeMap<Long, String>();
		TreeMap<Long, Double> agenciasLlegadaValor = new TreeMap<Long, Double>();
		TreeMap<Long, Double> agenciasLlegadaTotal = new TreeMap<Long, Double>();

		double totalGeneralPasajeros = 0, totalGeneralVentas = 0, totalGeneralNumeroKilometros = 0, totalGeneralNumeroAsientos = 0;
		
		String sql="SELECT v.* "+
					"FROM( SELECT i.itinerario_id as itinerarioID "+
					           ",i.d_fecpar as fechaPartida    "+
					           ",i.c_horpar as horaPartida "+
					           ",i.ruta_idmayor as rutaMayorID "+
					           ",r.c_origen as localidadOrigen "+
					           ",r.c_destino as localidadDestino "+
					           ",r.n_kilometros as numeroKilometros "+
					           ",sr.c_denominacion as servicio "+
					           ",sr.n_numasipis1+NVL(SR.N_NUMASIPIS2,0) as numeroAsientos "+
					           ",di.ruta_id as rutaID_DetaItinereario   "+
					           ",di.agencia_idpartida as idAgenPartidaRuta  "+
					           ",di.agencia_idllegada as idAgenLlegadaRuta  "+
					           ",paxIti.cantPax as cantPaxItiRuta  ";
					           if(mostrarCuadroIngresos){
						           sql+=",vtapzir.vtaPizarra as vtaPizarraItiRuta "+ 
						                ",vtaXiti.vtaItinerario as vtaItiRuta  ";
					           }else{
					        	   sql+=",0 as vtaPizarraItiRuta "+ 
								        ",0 as vtaItiRuta  ";
					           }
					     sql+="FROM VRTITINERARIO i   "+
					          "INNER JOIN VRMRUTA r ON (r.ruta_id=i.ruta_idmayor) "+ 
					          "INNER JOIN VRTDETITI di ON (di.itinerario_id=i.itinerario_id) "+ 
					          "INNER JOIN VRMSERVICIO sr ON (sr.servicio_id=i.servicio_id) "+
					          //-->recupera la cantidad de pasajeros por itinerario y ruta
					          "INNER JOIN ( SELECT di.ruta_id, vp.itinerario_id, COUNT(*)AS cantPax FROM VRTVENPAS vp " +
					                         "INNER JOIN (SELECT MAX(v.venpas_id)venpas_id FROM VRTVENPAS v  " +
					                         			  "INNER JOIN VRTITINERARIO I ON (i.itinerario_id=v.itinerario_id) " +
					                         			  "WHERE i.d_Fecpar BETWEEN to_date('"+Constantes.FORMAT_DATE.format(fechaInicial)+"','dd/MM/yyyy') AND to_date('"+Constantes.FORMAT_DATE.format(fechaFinal)+"','dd/MM/yyyy') GROUP BY v.c_numcontrol) maxvenpas_id ON (maxvenpas_id.venpas_id=vp.venpas_id) "+ 
					                         "INNER JOIN VRTDETITI di ON (di.itinerario_id=vp.itinerario_id AND di.ruta_id=vp.ruta_id)  "+
					                       "WHERE vp.c_tiptra=1 "+  
					                         "AND vp.Forpag_Id IN (1,2) "+ 
					                         "AND vp.tipmov_id NOT IN (5,6,13) "+ 
					                         "AND di.c_estreg='A'   "+
					                         "AND vp.c_estreg='A' "+ 
					                       "GROUP BY di.ruta_id,vp.itinerario_id "+ 
					                     ")paxIti ON (paxIti.ruta_id=di.ruta_id AND paxIti.itinerario_id=i.itinerario_id) ";
							  if(mostrarCuadroIngresos){
						          //-->Recupera la venta pizarra por el itinerario y la ruta  
						          sql+="INNER JOIN ( SELECT NVL(SUM(di.n_tarifa),0)vtaPizarra, di.ruta_id,vp.itinerario_id FROM VRTVENPAS vp "+ 
						                         "INNER JOIN (SELECT MAX(v.venpas_id)venpas_id FROM VRTVENPAS v " +
						                         						"INNER JOIN VRTITINERARIO I ON (i.itinerario_id=v.itinerario_id) " +
						                         						"WHERE i.d_Fecpar BETWEEN to_date('"+Constantes.FORMAT_DATE.format(fechaInicial)+"','dd/MM/yyyy') AND to_date('"+Constantes.FORMAT_DATE.format(fechaFinal)+"','dd/MM/yyyy') GROUP BY v.c_numcontrol) maxvenpas_id ON (maxvenpas_id.venpas_id=vp.venpas_id) "+ 
						                         "INNER JOIN VRTDETITI di ON (di.itinerario_id=vp.itinerario_id AND di.ruta_id=vp.ruta_id)  "+
						                       "WHERE vp.c_tiptra=1  "+
						                         "AND vp.Forpag_Id IN (1,2) "+ 
						                         "AND vp.tipmov_id NOT IN (5,6,13) "+ 
						                         "AND di.c_estreg='A'   "+
						                         "AND vp.c_estreg='A'  "+
						                       "GROUP BY di.ruta_id,vp.itinerario_id "+ 
						                      ")vtapzir  ON (vtapzir.ruta_id=di.ruta_id AND vtapzir.itinerario_id=i.itinerario_id) "+ 
						          //-->Recupera la venta por itinerario y ruta             
						          "INNER JOIN ( SELECT NVL(SUM(vp.n_tarifa+vp.n_recargo-vp.n_descuento),0)vtaItinerario, di.ruta_id,vp.itinerario_id FROM VRTVENPAS vp "+  
						                         "INNER JOIN (SELECT MAX(v.venpas_id)venpas_id FROM VRTVENPAS v " +
						                         			  "INNER JOIN VRTITINERARIO I ON (i.itinerario_id=v.itinerario_id) " + 
						                         			  "WHERE i.d_Fecpar BETWEEN to_date('"+Constantes.FORMAT_DATE.format(fechaInicial)+"','dd/MM/yyyy') AND to_date('"+Constantes.FORMAT_DATE.format(fechaFinal)+"','dd/MM/yyyy') GROUP BY v.c_numcontrol) maxvenpas_id ON (maxvenpas_id.venpas_id=vp.venpas_id) "+ 
						                         "INNER JOIN VRTDETITI di ON (di.itinerario_id=vp.itinerario_id AND di.ruta_id=vp.ruta_id)  "+
						                       "WHERE vp.c_tiptra=1  "+
						                         "AND vp.Forpag_Id IN (1,2) "+ 
						                         "AND vp.tipmov_id NOT IN (5,6,13) "+ 
						                         "AND di.c_estreg='A'   "+
						                         "AND vp.c_estreg='A'  "+
						                       "GROUP BY di.ruta_id,vp.itinerario_id "+ 
						                      ")vtaXiti ON (vtaXiti.ruta_id=di.ruta_id AND vtaXiti.itinerario_id=i.itinerario_id) ";
							  }
				   sql+="WHERE i.d_Fecpar BETWEEN to_date('"+Constantes.FORMAT_DATE.format(fechaInicial)+"','dd/MM/yyyy') AND to_date('"+Constantes.FORMAT_DATE.format(fechaFinal)+"','dd/MM/yyyy') "; 
						if(idServicio > 0)
							sql+="AND sr.servicio_id ="+idServicio+" ";
						
					    sql+= "AND i.n_esanulado=0  "+
						      "AND i.c_Estreg='A'  "+
						      "AND di.c_Estreg='A'  "+
						      "AND i.n_esanulado=0  "+
						      "AND i.c_Estreg='A'  "+
						      "AND di.c_Estreg='A'  ";
					    
						switch(limaProvincias){ //*****EL IDENTIFICADOR DEL LA LOCALIDAD LIMA ES 13 ******************
							case 0://Lima - Provincias
								sql+="AND r.localidad_idOrigen="+Constantes.ID_LOC_LIMA; // LIKE '%lima%'" ;
								break;
				
							case 1://Provincias - Lima
								sql+=" AND r.localidad_idDestino="+Constantes.ID_LOC_LIMA;//LOWER(r.c_destino) LIKE '%lima%' ";
								break;
							
							case 2://Provincias
								sql+=" AND localidad_idOrigen!="+Constantes.ID_LOC_LIMA+" AND localidad_idDestino!="+Constantes.ID_LOC_LIMA;
								break;
						}
						
					sql+=" ORDER BY sr.c_Denominacion,i.d_fecpar||''||i.c_horpar||''||i.itinerario_id "+
						   ")v  "+
						" WHERE v.cantPaxItiRuta>0";
		log.info(sql);			
		List<?>resultado=getSession().createSQLQuery(sql).list();	
	
		sqlAgenciaPartida="SELECT age.agencia_id as idAgencia, age.c_denominacion as agencia "+
							"FROM VRMAGENCIA age "+
							"WHERE age.agencia_id IN( ";

		sqlAgenciaLlegada=sqlAgenciaPartida;

		for(int f = 0; f < resultado.size(); f++){
			Object[] obj= (Object[]) resultado.get(f);

			long idAgenciaPartida_itinerarioRuta = ((BigDecimal)obj[10]).longValue();
			long idAgenciaLlegada_itinerarioRuta = ((BigDecimal)obj[11]).longValue();

			agenciasPartida.put(idAgenciaPartida_itinerarioRuta, "");
			agenciasPartidaValor.put(idAgenciaPartida_itinerarioRuta, new Double(0));
			agenciasPartidaTotal.put(idAgenciaPartida_itinerarioRuta, new Double(0));
			sqlAgenciaPartida+=(idAgenciaPartida_itinerarioRuta);

			agenciasLlegada.put(idAgenciaLlegada_itinerarioRuta, "");
			agenciasLlegadaValor.put(idAgenciaLlegada_itinerarioRuta, new Double(0));
			agenciasLlegadaTotal.put(idAgenciaLlegada_itinerarioRuta, new Double(0));
			sqlAgenciaLlegada+=(idAgenciaLlegada_itinerarioRuta);

			if(f < (resultado.size() - 1)){
				sqlAgenciaPartida+=(", ");
				sqlAgenciaLlegada+=(", ");
			}
		}

		sqlAgenciaPartida+=(") ");
		sqlAgenciaLlegada+=(") ");

		if(resultado.size() > 0){
			if(!(mostrarCuadroIngresos && limaProvincias == 0)){
				List<?> resultadoAgenciasPartida = getSession().createSQLQuery(sqlAgenciaPartida).list();
				
				for(int f = 0; f < resultadoAgenciasPartida.size(); f ++){
					Object[] obj= (Object[]) resultadoAgenciasPartida.get(f);
					long idAgencia = ((BigDecimal) obj[0]).intValue();
					String nombreAgencia =obj[1].toString();
					agenciasPartida.put(idAgencia, nombreAgencia);
				}
			}

			if(!(mostrarCuadroIngresos && limaProvincias == 1)){
				List<?> resultadoAgenciasLlegada = getSession().createSQLQuery(sqlAgenciaLlegada).list();

				for(int f = 0; f < resultadoAgenciasLlegada.size(); f ++){
					Object[] obj= (Object[]) resultadoAgenciasLlegada.get(f);

					long idAgencia = ((BigDecimal)obj[0]).intValue();
					String nombreAgencia =obj[1].toString();
					agenciasLlegada.put(idAgencia, nombreAgencia);
				}
			}
		}

		if(mostrarCuadroIngresos){
			lstEncabezado.add("SERVICIO");
			lstEncabezado.add("CAP.");
			lstEncabezado.add("SALIDA");
			lstEncabezado.add("RUTA ITINERARIO");
			lstEncabezado.add("KM X RUTA");
		}else{
			lstEncabezado.add("SERVICIO");
			lstEncabezado.add("CAP.");
			lstEncabezado.add("SALIDA");
			lstEncabezado.add("RUTA ITINERARIO");
			lstEncabezado.add("TOTAL PAX");
		}


		if(!(mostrarCuadroIngresos && limaProvincias == 0)){
			for(Entry e : agenciasPartida.entrySet()){
				lstEncabezado.add("Part.: " + e.getValue());
			}
		}


		if(!(mostrarCuadroIngresos && limaProvincias == 1)){
			for(Entry e : agenciasLlegada.entrySet()){
				lstEncabezado.add("Lleg.: " + e.getValue());
			}
		}
		
		if(mostrarCuadroIngresos){
			lstEncabezado.add("TOT. ING. PIZARRA");
			lstEncabezado.add("SOL / KM");
			lstEncabezado.add("TOT. ING. REAL");
			lstEncabezado.add("SOL / KM");
			lstEncabezado.add("% DESCUENTO");
		}else	
			lstEncabezado.add("% OCUPABILIDAD");
	
		lstReporte.add(lstEncabezado);

		for(int f = 0; f < resultado.size(); f++){
			Object[] obj= (Object[]) resultado.get(f);
			ArrayList<Object> lstFila = new ArrayList<Object>();
	
			long idItinerario_itinerario = ((BigDecimal)obj[0]).longValue(); 
			Date fechaPartida_itinerario = (Date)obj[1]; //oMap.get("FECHAPARTIDA");
			String horaPartida_itinerario = obj[2].toString(); // oMap.get("HORAPARTIDA").toString();
			String localidadOrigen_itinerario =obj[4].toString(); //oMap.get("LOCALIDADORIGEN").toString();
			String localidadDestino_itinerario =obj[5].toString(); // oMap.get("LOCALIDADDESTINO").toString();
			double numeroKilometros_itinerario = ((BigDecimal)obj[6]).doubleValue();  //oMap.get("NUMEROKILOMETROS")).doubleValue();
			String nombreClase_itinerario =obj[7].toString(); // oMap.get("SERVICIO").toString();
			int numeroAsientos_itinerario = ((BigDecimal)obj[8]).intValue(); // oMap.get("NUMEROASIENTOS")).intValue();
	
			double totalPasajeros = 0, totalVentas = 0;
	
			boolean perteneceGrupo = true;
			int saltarFilas = 0;
			
			while(perteneceGrupo){
				int filaSiguiente = f + saltarFilas;
	
				if(filaSiguiente >= resultado.size()){
					perteneceGrupo = false;
					continue;
				}
				Object[] objSiguiente= (Object[]) resultado.get(filaSiguiente);
	
				long idItinerario_itinerarioSiguiente = ((BigDecimal)objSiguiente[0]).longValue(); // oMapSiguiente.get("ITINERARIOID")).longValue();
				long idAgenciaPartida_itinerarioRutaSiguiente = ((BigDecimal)objSiguiente[10]).longValue(); //oMapSiguiente.get("IDAGENPARTIDARUTA")).longValue();
				long idAgenciaLlegada_itinerarioRutaSiguiente = ((BigDecimal)objSiguiente[11]).longValue(); //oMapSiguiente.get("IDAGENLLEGADARUTA")).longValue();
				long pasajeros_itinerarioRutaSiguiente = ((BigDecimal)objSiguiente[12]).longValue(); // oMapSiguiente.get("CANTPAXITIRUTA")).longValue();
				double venta_itinerarioRutaSiguiente = ((BigDecimal)objSiguiente[14]).doubleValue(); // oMapSiguiente.get("VTAITIRUTA")).doubleValue();
				double ventaPizarra_itinerarioRutaSiguiente = ((BigDecimal)objSiguiente[13]).longValue(); // oMapSiguiente.get("VTAPIZARRAITIRUTA")).doubleValue();
	
				if(idItinerario_itinerario == idItinerario_itinerarioSiguiente){
					double totalPartida = agenciasPartidaValor.get(idAgenciaPartida_itinerarioRutaSiguiente);
					double totalLlegada = agenciasLlegadaValor.get(idAgenciaLlegada_itinerarioRutaSiguiente);
	
					if(mostrarCuadroIngresos){
						totalPartida += venta_itinerarioRutaSiguiente;
						totalLlegada += venta_itinerarioRutaSiguiente;
						totalVentas += venta_itinerarioRutaSiguiente;
						totalPasajeros += ventaPizarra_itinerarioRutaSiguiente;
					}else{
						totalPartida += pasajeros_itinerarioRutaSiguiente;
						totalLlegada += pasajeros_itinerarioRutaSiguiente;
						totalPasajeros += pasajeros_itinerarioRutaSiguiente;
					}
	
					agenciasPartidaValor.put(idAgenciaPartida_itinerarioRutaSiguiente, totalPartida);
					agenciasLlegadaValor.put(idAgenciaLlegada_itinerarioRutaSiguiente, totalLlegada);
	
					saltarFilas ++;
				}else
					perteneceGrupo = false;		
			}
	
			totalGeneralNumeroAsientos += numeroAsientos_itinerario;
			
			if(mostrarCuadroIngresos){
				lstFila.add(nombreClase_itinerario);
				lstFila.add(numeroAsientos_itinerario);
				lstFila.add(Constantes.FORMAT_DATE.format(fechaPartida_itinerario) + " " + horaPartida_itinerario);
				lstFila.add(localidadOrigen_itinerario + " - " + localidadDestino_itinerario);
				lstFila.add(new BigDecimal(numeroKilometros_itinerario).setScale(2, RoundingMode.HALF_UP));
			}else{
				lstFila.add(nombreClase_itinerario);
				lstFila.add(numeroAsientos_itinerario);
				lstFila.add(Constantes.FORMAT_DATE.format(fechaPartida_itinerario) + " " + horaPartida_itinerario);
				lstFila.add(localidadOrigen_itinerario + " - " + localidadDestino_itinerario);
				lstFila.add(new BigDecimal(totalPasajeros).intValue());
			}
	
			for(Entry e : agenciasPartidaValor.entrySet()){
				if(mostrarCuadroIngresos && limaProvincias == 0)
					break;
				
				BigDecimal valor = new BigDecimal((Double) e.getValue());
				double total = agenciasPartidaTotal.get(e.getKey());
				total += (Double) e.getValue();
	
				if(mostrarCuadroIngresos)
					lstFila.add(valor.setScale(2, RoundingMode.HALF_UP));
				else
					lstFila.add(valor.intValue());
				
				agenciasPartidaValor.put((Long) e.getKey(), new Double(0));
				agenciasPartidaTotal.put((Long) e.getKey(), total);
			}
	
			for(Entry e : agenciasLlegadaValor.entrySet()){
				if(mostrarCuadroIngresos && limaProvincias == 1)
					break;
				
				BigDecimal valor = new BigDecimal((Double) e.getValue());
				double total = agenciasLlegadaTotal.get(e.getKey());
				total += (Double) e.getValue();
	
				if(mostrarCuadroIngresos)
					lstFila.add(valor.setScale(2, RoundingMode.HALF_UP));
				else
					lstFila.add(valor.intValue());
				
				agenciasLlegadaValor.put((Long) e.getKey(), new Double(0));
				agenciasLlegadaTotal.put((Long) e.getKey(), total);
			}
			
			
			if(mostrarCuadroIngresos){
				BigDecimal ingresoPizarra = new BigDecimal(totalPasajeros).setScale(2, RoundingMode.HALF_UP);
				BigDecimal ingresoReal = new BigDecimal(totalVentas).setScale(2, RoundingMode.HALF_UP);
		
				lstFila.add(ingresoPizarra);
				lstFila.add(ingresoPizarra.divide(new BigDecimal(numeroKilometros_itinerario ),2 , RoundingMode.HALF_UP));
		
				lstFila.add(ingresoReal);
				lstFila.add(ingresoReal.divide(new BigDecimal(numeroKilometros_itinerario ),2 , RoundingMode.HALF_UP));
		
				if(ingresoPizarra.intValue() == 0)
					lstFila.add(new BigDecimal(0).setScale(2, RoundingMode.HALF_UP));
				else
					lstFila.add(ingresoReal.divide(ingresoPizarra, 100, RoundingMode.HALF_UP).subtract(new BigDecimal(1)).multiply(new BigDecimal(100)).setScale( 2, RoundingMode.HALF_UP));
			
			}else
				lstFila.add(new BigDecimal(totalPasajeros * 100).divide(new BigDecimal(numeroAsientos_itinerario), 2, RoundingMode.HALF_UP));
		
			lstReporte.add(lstFila);
		
			totalGeneralPasajeros += totalPasajeros;
			totalGeneralVentas += totalVentas;
			totalGeneralNumeroKilometros += numeroKilometros_itinerario;
		
			f += saltarFilas - 1;
		}


		if(resultado.size() > 0){
			lstTotal.add("TOTAL");
		
			if(mostrarCuadroIngresos){
				lstTotal.add("");
				lstTotal.add("");
				lstTotal.add("");
				lstTotal.add(new BigDecimal(totalGeneralNumeroKilometros).setScale(2, RoundingMode.HALF_UP));
			}else{
				lstTotal.add("");
				lstTotal.add("");
				lstTotal.add("");
				lstTotal.add(new BigDecimal(totalGeneralPasajeros).intValue());
			}
		
			for(Entry e : agenciasPartidaTotal.entrySet()){
				if(mostrarCuadroIngresos && limaProvincias == 0)
					break;
				
				BigDecimal valor = new BigDecimal((Double) e.getValue());
		
				if(mostrarCuadroIngresos)
						lstTotal.add(valor.setScale(2, RoundingMode.HALF_UP));
				else
					lstTotal.add(valor.intValue());
			}
		
			for(Entry e : agenciasLlegadaTotal.entrySet()){
				if(mostrarCuadroIngresos && limaProvincias == 1)
					break;				
		
				BigDecimal valor = new BigDecimal((Double) e.getValue());
		
				if(mostrarCuadroIngresos)
					lstTotal.add(valor.setScale(2, RoundingMode.HALF_UP));
				else
					lstTotal.add(valor.intValue());
			}
		
		
			if(mostrarCuadroIngresos){		
				BigDecimal totalIngresoPizarra = new BigDecimal(totalGeneralPasajeros);
				BigDecimal totalIngresoReal = new BigDecimal(totalGeneralVentas);
		
				BigDecimal tgPasajeros = totalIngresoPizarra.divide(new BigDecimal(totalGeneralNumeroKilometros) ,2, RoundingMode.HALF_UP);
				BigDecimal tgVentas = totalIngresoReal.divide(new BigDecimal(totalGeneralNumeroKilometros) ,2, RoundingMode.HALF_UP);
		
				lstTotal.add(totalIngresoPizarra.setScale(2, RoundingMode.HALF_UP));
				lstTotal.add(tgPasajeros);
				lstTotal.add(totalIngresoReal.setScale(2, RoundingMode.HALF_UP));
				lstTotal.add(tgVentas);
				lstTotal.add(totalIngresoReal.divide(totalIngresoPizarra, 100, RoundingMode.HALF_UP).subtract(new BigDecimal(1)).multiply(new BigDecimal(100)).setScale( 2, RoundingMode.HALF_UP));
			}else
				lstTotal.add(new BigDecimal(totalGeneralPasajeros * 100).divide(new BigDecimal(totalGeneralNumeroAsientos), 2, RoundingMode.HALF_UP));		
			
			lstReporte.add(lstTotal);
		}

		return lstReporte;
	}

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.model.dao.ReportesDAO#pasajerosTransbordados(java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.Long)
	 */
	@Override
	public ArrayList<Transbordo> pasajerosTransbordados(String fechaTransInicio, String fechaTransFin, String origen,String destino, String boleto, Long idPasajero) throws Exception {
		String sql="SELECT DECODE(vp.c_tiptra,'1','VENTA','RESERVA')  AS tipo "+
					     ",vp.c_numboleto "+
					     ",p.c_nomape AS pasajero "+
					     ",r.c_origen AS origen "+
					     ",r.c_destino AS destino "+
					     ",so.c_denominacion AS servicioOrig "+
					     ",tb.d_fecparori AS fPartidaOrig "+
					     ",tb.c_horparori AS hpartidaOrig "+
					     ",tb.n_numasiori AS asienoOrig "+
					     ",sd.c_Denominacion AS servicioDest "+
					     ",tb.d_fecpardes AS fPartidadDest "+
					     ",tb.c_horpardes AS hPartidadDest "+
					     ",tb.n_numasides AS asientoDest "+
					     ",tb.audfecins AS fechaTrans "+
					     ",u.c_apepat AS apePatTrans "+
					     ",u.c_apemat AS apeMatTrans "+
					     ",u.c_nombre AS nomTrans "+
					"FROM VRTTRANSBORDO tb "+
					     "INNER JOIN VRTVENPAS vp ON (vp.venpas_id=tb.venpas_id) "+
					     "INNER JOIN VRMRUTA r ON (r.ruta_id=vp.ruta_id) "+
					     "INNER JOIN VRMPASAJERO p ON (p.pasajero_id=vp.pasajero_id) "+
					     "INNER JOIN VRMSERVICIO so ON (so.servicio_id=tb.servicio_idorigen) "+
					     "INNER JOIN VRMSERVICIO sd ON (sd.servicio_id=tb.servicio_iddestino) "+
					     "INNER JOIN VRMUSUARIO u ON (u.c_login=tb.audusuins) "+
					"WHERE TO_DATE(tb.audfecins,'dd/MM/yyy') BETWEEN to_Date('"+fechaTransInicio+"','dd/MM/yyyy') AND to_date('"+fechaTransFin+"','dd/MM/yyyy') "+
//					    "AND p.pasajero_id=NVL("+idPasajero+",p.pasajero_id) "+
					    "AND tb.c_estreg='A' ";
					    
		
		if(origen!=null)
			sql+=" AND r.c_origen='"+origen+"' ";
		if(destino!=null)
			sql+=" AND r.c_destino='"+destino+"' ";
		if(boleto!=null)
			sql+="AND vp.c_numboleto='"+boleto+"' ";
		
		sql+="AND ROWNUM<=1000 "
				+ "ORDER BY tb.audfecins desc";
		
		
		List<?>lstResult=getSession().createSQLQuery(sql).list();
		ArrayList<Transbordo>lstTransbordos=new ArrayList<Transbordo>();
		for(int x=0; x<lstResult.size();x++){
			Object[] obj=(Object[]) lstResult.get(x);
			
			VentaPasaje ventaPasaje=new VentaPasaje();
			ventaPasaje.setTipoTransaccion(obj[0].toString());
			ventaPasaje.setNumeroBoleto(obj[1]!=null?obj[1].toString():"");
			
			Pasajero pasajero=new Pasajero();
			pasajero.setNombresApellidos(obj[2].toString());
			
			Ruta ruta=new Ruta();
			ruta.setOrigen(obj[3].toString());
			ruta.setDestino(obj[4].toString());
			
			Servicio servicioOrigen=new Servicio();
			servicioOrigen.setDenominacion(obj[5].toString());
			
			Transbordo transbordo=new Transbordo();
			transbordo.setFechaPartidaOrigen((Date)obj[6]);
			transbordo.setHoraPartidaOrigen(obj[7].toString());
			transbordo.setNumeroAsientoOrigen(((BigDecimal)obj[8]).intValue());
			
			Servicio servicioDestino=new Servicio();
			servicioDestino.setDenominacion(obj[9].toString());
			
			transbordo.setFechaPartidaDestino((Date)obj[10]);
			transbordo.setHoraPartidaDestino(obj[11].toString());
			transbordo.setNumeroAsientoDestino(((BigDecimal)obj[12]).intValue());
			transbordo.setFechaInsercion((Date)obj[13]);
			
			Usuario usuario=new Usuario();
			usuario.setApellidoPaterno(obj[14].toString());
			usuario.setApellidoMaterno(obj[15]!=null?obj[15].toString():null);
			usuario.setNombre(obj[16].toString());
			
			ventaPasaje.setPasajero(pasajero);
			ventaPasaje.setRuta(ruta);
			transbordo.setVentaPasaje(ventaPasaje);
			transbordo.setServicioOrigen(servicioOrigen);
			transbordo.setServicioDestino(servicioDestino);
			transbordo.setUsuarioInsercion(usuario.toString());
						
			lstTransbordos.add(transbordo);
		}
				
		return lstTransbordos;
	}

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.model.dao.ReportesDAO#cenasXRutas(java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	public ArrayList<PreferenciaAlimentaria> cenasXRutas(String fechaInicial,String fechaFinal, String tipoConsulta) throws Exception {
		String subQuery=" ";
		
		if(tipoConsulta.equals("0")){ //TODOS
			subQuery="AND r.localidad_idorigen=r.localidad_idorigen AND r.localidad_iddestino=r.localidad_iddestino ";
		}else if (tipoConsulta.equals("1")){ // LIMA PROVINCIA
			subQuery="AND r.localidad_idorigen="+Constantes.ID_LOC_LIMA+" AND r.localidad_iddestino!="+Constantes.ID_LOC_LIMA;
		}else if (tipoConsulta.equals("2")){ // PROVINCIA LIMA
			subQuery="AND r.localidad_idorigen!="+Constantes.ID_LOC_LIMA+" AND r.localidad_iddestino="+Constantes.ID_LOC_LIMA;
		}else{ // ENTRE PROVINCIAS
			subQuery="AND r.localidad_idorigen!="+Constantes.ID_LOC_LIMA+" AND r.localidad_iddestino!="+Constantes.ID_LOC_LIMA;
		}
		
		/* RECUPERA LAS VENTAS AGRUPADAS POR TIPO DE ALIMENTACION*/ 
		String sqlAlimen="SELECT preali_id,Cena,SUM(CantCenas)CantCenas,TipoConsulta,d_fecpar "+
						   " FROM( "+
							      "SELECT pa.preali_id,pa.c_denominacion as Cena,COUNT(*)CantCenas "+
							            ",CASE WHEN r.localidad_idorigen=13 AND r.localidad_iddestino!=13 THEN 1 "+
							              "WHEN r.localidad_idorigen!=13 AND r.localidad_iddestino=13 THEN 2  "+
							              "WHEN r.localidad_idorigen!=13 AND r.localidad_iddestino!=13 THEN 3 "+ 
							            "END TipoConsulta " +
							            ",i.d_fecpar "+
							      "FROM vrtvenpas vt  "+
							           "INNER JOIN (SELECT MAX(venpas_id) venpas_id FROM vrtvenpas  GROUP BY c_numcontrol) B on (vt.venpas_id = B.venpas_id) "+ 
							           "INNER JOIN VRMPREALI pa ON (pa.preali_id=vt.preali_id)  "+
							           "INNER JOIN vrtitinerario i on (i.itinerario_id=vt.itinerario_id) "+ 
							           "INNER JOIN vrmruta r ON (r.ruta_id=i.ruta_idmayor) "+
							      "WHERE  i.d_fecpar BETWEEN to_Date('"+fechaInicial+"','dd/MM/yyyy') AND to_date('"+fechaFinal+"','dd/MM/yyyy') "+
							           "AND vt.tipmov_id NOT IN (5,6,13) AND vt.c_tiptra=1 "+  
							            subQuery+" "+ 
							      "GROUP BY i.d_fecpar,pa.c_denominacion,pa.preali_id,r.localidad_idorigen,r.localidad_iddestino "+
							    ")al "+  
							"GROUP BY d_fecpar,preali_id,Cena,TipoConsulta "+
							"ORDER BY  d_fecpar,TipoConsulta,preali_id";
		
		log.info(sqlAlimen);
		List<?> resultAlimen = getSession().createSQLQuery(sqlAlimen).list();
		
		/* RECUPERA LAS VENTAS POR RUTA Y TRAMOS */
		String sqlC="SELECT  * "+
						"FROM( "+
								// Ventas por Itienrarios.
						      "SELECT 0 AS flag, i.itinerario_id, r.c_origen, r.c_Destino, i.c_horpar,s.c_Denominacion servicio,vt.preali_id, COUNT(*)CantCenas,i.d_fecpar,i.ruta_idmayor idRutaVenta "+ 
						      "FROM vrtvenpas vt  "+
						           "INNER JOIN (SELECT MAX(venpas_id) venpas_id FROM vrtvenpas  GROUP BY c_numcontrol) B on (vt.venpas_id = B.venpas_id) "+  
						           "INNER JOIN vrtitinerario i on (i.itinerario_id=vt.itinerario_id)  "+
						           "INNER JOIN vrmruta r ON (r.ruta_id=i.ruta_idmayor) "+
						           "INNER JOIN vrmservicio s ON (s.servicio_id=i.servicio_id) "+
						      "WHERE  i.d_fecpar BETWEEN to_date('"+fechaInicial+"','dd/MM/yyyy') AND to_date('"+fechaFinal+"','dd/MM/yyyy') "+
						           "AND vt.tipmov_id NOT IN (5,6,13) AND vt.c_tiptra=1   "+
						           subQuery+" "+
//						           "AND r.localidad_idorigen=13 "+
						      "GROUP BY i.d_fecpar,i.itinerario_id,r.c_origen, r.c_Destino, i.c_horpar,s.c_Denominacion,vt.preali_id,i.ruta_idmayor "+
						  "UNION ALL "+
						      // Ventas por ruta.
						      "SELECT 1 as flag,i.itinerario_id,rv.c_origen, rv.c_Destino, i.c_horpar,s.c_Denominacion servicio,vt.preali_id, COUNT(*)CantCenas,i.d_fecpar,vt.ruta_id idRutaVenta "+ 
						      "FROM vrtvenpas vt  "+
						           "INNER JOIN (SELECT MAX(venpas_id) venpas_id FROM vrtvenpas  GROUP BY c_numcontrol) B on (vt.venpas_id = B.venpas_id) "+  
						           "INNER JOIN vrtitinerario i on (i.itinerario_id=vt.itinerario_id)  "+
						           "INNER JOIN vrmservicio s ON (s.servicio_id=i.servicio_id) "+
						           "INNER JOIN vrmruta rv ON (rv.ruta_id=vt.ruta_id) "+
						           "INNER JOIN vrmruta r ON (r.ruta_id=i.ruta_idmayor) "+
						      "WHERE  i.d_fecpar BETWEEN to_date('"+fechaInicial+"','dd/MM/yyyy') AND to_date('"+fechaFinal+"','dd/MM/yyyy') "+
						           "AND vt.tipmov_id NOT IN (5,6,13) AND vt.c_tiptra=1   "+
						           subQuery+" "+
//						           "AND r.localidad_idorigen=13 "+
						      "GROUP BY i.d_fecpar,i.itinerario_id,rv.c_origen, rv.c_Destino, i.c_horpar,s.c_Denominacion,vt.preali_id,vt.ruta_id "+
						    ")rptc "+
						"ORDER BY rptc.flag desc,rptc.d_fecpar,rptc.c_horpar";
		
		log.info(sqlC);
		List<?> result = getSession().createSQLQuery(sqlC).list();

		/* RECUPERA LOS PASAJEROS, SOLO LOS CON CENA DIFERENTE A "MENU DEL DIA"*/
		String sqlPax="SELECT i.itinerario_id, p.c_nomape pasajero "+
						       ",vt.preali_id,vt.n_numasiento asiento " +
						       ", rv.ruta_id idRutaVenta "+  
						"FROM vrtvenpas vt "+
						     "INNER JOIN (SELECT MAX(venpas_id) venpas_id FROM vrtvenpas  GROUP BY c_numcontrol) B on (vt.venpas_id = B.venpas_id) "+
						     "INNER JOIN vrtitinerario i on (i.itinerario_id=vt.itinerario_id) "+  
						     "INNER JOIN vrmpasajero p ON (p.pasajero_id=vt.pasajero_id) "+ 
						     "INNER JOIN vrmruta rv ON (rv.ruta_id=vt.ruta_id) "+
						     "INNER JOIN vrmruta r ON (r.ruta_id=i.ruta_idmayor) "+
						"WHERE  i.d_fecpar BETWEEN to_date('"+fechaInicial+"','dd/MM/yyyy') AND to_date('"+fechaFinal+"','dd/MM/yyyy') "+
						     "AND vt.tipmov_id NOT IN ("+Constantes.ID_TIPMOV_ANULACION+","+Constantes.ID_TIPMOV_DEVOLUCION+","+Constantes.ID_TIPMOV_ANULACION_SISTEMA+") " +
						     "AND vt.c_tiptra=1 "+  
						     subQuery+" "+
//						     AND r.localidad_idorigen=13
						     "AND vt.preali_id!=1 "+
						"ORDER BY vt.d_fecpar,vt.c_horpar,vt.n_numasiento";
		log.info(sqlPax);
		List<?>resulPax=getSession().createSQLQuery(sqlPax).list();
		
		List<VentaPasaje>lstPax=new ArrayList<VentaPasaje>();
		for(int i=0;i<resulPax.size();i++){
			Object[] obj=(Object[]) resulPax.get(i);
			
			Itinerario itinerario=new Itinerario(((BigDecimal)obj[0]).longValue());
			Pasajero pasajero=new Pasajero();
			pasajero.setNombresApellidos(obj[1].toString());
			PreferenciaAlimentaria alimentacion=new PreferenciaAlimentaria();
			alimentacion.setId(((BigDecimal)obj[2]).intValue());
			
			Ruta ruta=new Ruta(((BigDecimal)obj[4]).intValue());
						
			VentaPasaje ventaPasaje=new VentaPasaje();
			ventaPasaje.setItinerario(itinerario);
			ventaPasaje.setPasajero(pasajero);
			ventaPasaje.setPreferenciaAlimentaria(alimentacion);
			ventaPasaje.setNumeroAsiento(((BigDecimal)obj[3]).intValue());
			ventaPasaje.setRuta(ruta);
			
			lstPax.add(ventaPasaje);
		}
						
		
		//Asocia a preferencia limentarioa las rutas
		List<VentaPasaje>lstVentaXItinerario=new ArrayList<VentaPasaje>();
		List<VentaPasaje>lstVentaXRuta=new ArrayList<VentaPasaje>();
		for(int i=0; i<result.size(); i++){
			Object[] obj = (Object[])result.get(i);
			
			if(obj[0].toString().equals("0")){
				//Ventas por Itinerario
				Ruta rutaMayor=new Ruta();
				rutaMayor.setId(((BigDecimal)obj[9]).intValue());
				rutaMayor.setOrigen(obj[2].toString());
				rutaMayor.setDestino(obj[3].toString());
				
				Servicio servicio=new Servicio();
				servicio.setDenominacion(obj[5].toString());
				
				Itinerario itinerario=new Itinerario();
				itinerario.setId(((BigDecimal)obj[1]).longValue());
				itinerario.setHoraPartida(obj[4].toString());
				itinerario.setFechaPartida((Date)obj[8]);
				itinerario.setRuta(rutaMayor);
				itinerario.setServicio(servicio);
				
				PreferenciaAlimentaria preferenciaAlimentaria=new PreferenciaAlimentaria();
				preferenciaAlimentaria.setId(((BigDecimal)obj[6]).intValue());
								
				VentaPasaje ventaXItinerario=new VentaPasaje();
				ventaXItinerario.setCantidadPax(((BigDecimal)obj[7]).intValue());
				ventaXItinerario.setItinerario(itinerario);
				ventaXItinerario.setPreferenciaAlimentaria(preferenciaAlimentaria);
				
				//Asocia las ventas por ruta a las ventas por itinerario.
				List<VentaPasaje>lstVentaXRutaAdd=new ArrayList<VentaPasaje>();
				for(VentaPasaje ventaXRuta: lstVentaXRuta){
					if(ventaXItinerario.getItinerario().getId().longValue()==ventaXRuta.getItinerario().getId().longValue()){
						lstVentaXRutaAdd.add(ventaXRuta);
					}
				}
				ventaXItinerario.setLstVentaXRuta(lstVentaXRutaAdd);
				
				lstVentaXItinerario.add(ventaXItinerario);
			}else{
				/* Ventas por ruta*/
				Ruta rutaPax=new Ruta();
				rutaPax.setId(((BigDecimal)obj[9]).intValue());
				rutaPax.setOrigen(obj[2].toString());
				rutaPax.setDestino(obj[3].toString());
				
				Servicio servicio=new Servicio();
				servicio.setDenominacion(obj[5].toString());
				
				Itinerario itinerario=new Itinerario();
				itinerario.setId(((BigDecimal)obj[1]).longValue());
				itinerario.setHoraPartida(obj[4].toString());
				itinerario.setFechaPartida((Date)obj[8]);
				itinerario.setRuta(rutaPax);
				itinerario.setServicio(servicio);

				PreferenciaAlimentaria preferenciaAlimentaria=new PreferenciaAlimentaria();
				preferenciaAlimentaria.setId(((BigDecimal)obj[6]).intValue());

				VentaPasaje ventaXRuta=new VentaPasaje();
				ventaXRuta.setCantidadPax(((BigDecimal)obj[7]).intValue());
				ventaXRuta.setItinerario(itinerario);
				ventaXRuta.setPreferenciaAlimentaria(preferenciaAlimentaria);
				
				/* Agrega pasajeros */
				List<VentaPasaje>lstPasajeros=new ArrayList<VentaPasaje>();
				if(preferenciaAlimentaria.getId().intValue()!=Constantes.ID_PREALIM_MENU_DEL_DIA){
					for(VentaPasaje ventaPasajeros: lstPax){
						if(ventaPasajeros.getItinerario().getId().longValue()==itinerario.getId().longValue() 
								&& preferenciaAlimentaria.getId().intValue()==ventaPasajeros.getPreferenciaAlimentaria().getId().intValue()
								&& rutaPax.getId().intValue()==ventaPasajeros.getRuta().getId().intValue())
							lstPasajeros.add(ventaPasajeros);
					}
				}
				ventaXRuta.setLstPasajeros(lstPasajeros);//Agrega los pasajeros a la ventaXRuta
				
				lstVentaXRuta.add(ventaXRuta);
			}
		}
		
		//Preferencia Alimentaria
		ArrayList<PreferenciaAlimentaria>lstAlimentacion=new ArrayList<PreferenciaAlimentaria>();
		for(int i=0; i<resultAlimen.size(); i++){
			Object[] obj = (Object[])resultAlimen.get(i);
			PreferenciaAlimentaria alimentacion=new PreferenciaAlimentaria();
			alimentacion.setId(((BigDecimal)obj[0]).intValue());
			alimentacion.setDenominacion(obj[1].toString());
			alimentacion.setCantidad(((BigDecimal)obj[2]).intValue());
			alimentacion.setTipoConsulta(((BigDecimal)obj[3]).intValue());
			alimentacion.setFecha((Date)obj[4]);
			
			List<VentaPasaje>lstVentaXItinerarioAdd=new ArrayList<VentaPasaje>();
			for(VentaPasaje ventaPasaje: lstVentaXItinerario){
				if(ventaPasaje.getPreferenciaAlimentaria().getId().intValue()==alimentacion.getId().intValue() && 
						ventaPasaje.getItinerario().getFechaPartida().getTime()==alimentacion.getFecha().getTime()){
					lstVentaXItinerarioAdd.add(ventaPasaje);
				}
			}
			alimentacion.setLstVentaXItinerario(lstVentaXItinerarioAdd);
			
			lstAlimentacion.add(alimentacion);
		}
				
		return lstAlimentacion;
	}

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.model.dao.ReportesDAO#ventasPromocion(java.lang.String, java.lang.String, java.lang.Long)
	 */
	@Override
	public ArrayList<Promocion> ventasPromocion(String fechaInicio,String fechaFin, Long idPromocion,String tipoDescuento) throws Exception {
		String sql="SELECT pro.promocion_id, pro.c_denominacion as Promocion "+
					      ",DECODE(pro.c_tipdes,'S',pro.n_valdes||'   S/',pro.n_valdes||'    %') as ValDesct "+
					      ",COUNT(*)as Cantidad "+
					      ",SUM(vp.n_descuento) as TotalDest "+
					      ",SUM(vp.n_imppag)as TotalVenta "+
					"FROM VRTVENPAS vp "+
					     "INNER JOIN (SELECT MIN(v.venpas_id)venpas_id FROM VRTVENPAS v GROUP BY v.venpas_idoriginal "+
					                ")mvenpas_id ON (mvenpas_id.venpas_id=vp.venpas_id) "+
					     "INNER JOIN VRMPROMOCION pro ON (vp.promocion_id=pro.promocion_id) "+
					     "INNER JOIN VRMUSUARIO us ON (us.usuario_id=vp.usuario_id) "+
					"WHERE vp.d_fecliq BETWEEN to_date('"+fechaInicio+"','dd/MM/yyyy') AND to_date('"+fechaFin+"','dd/MM/yyyy') "+
					     "AND vp.tipmov_id IN (1,8) "+
					     "AND vp.c_tiptra='1' "+
					     "AND vp.tipcom_id in (1,2,7) "+
					     "AND vp.c_estreg='A' "+
					     "AND pro.n_estarifa=0 " +
					     "AND pro.promocion_id=NVL("+idPromocion+",pro.promocion_id) ";
					     if(tipoDescuento!=null)
					    	 sql+="AND pro.c_tipdes='"+tipoDescuento+"' ";
					sql+="GROUP BY pro.promocion_id,pro.c_Denominacion,pro.c_tipdes,pro.n_valdes "+
					"ORDER BY SUM(vp.n_imppag) desc";
		log.info(sql);
		List<?>lstResult=getSession().createSQLQuery(sql).list();
		ArrayList<Promocion> lstVentPromo=new ArrayList<Promocion>();
		for(int i=0;i<lstResult.size(); i++){
			Object[] obj=(Object[])lstResult.get(i);
					
			Promocion promocion=new Promocion();
			promocion.setId(((BigDecimal)obj[0]).longValue());
			promocion.setDenominacion(obj[1].toString());
			promocion.setTipoDescuento(obj[2].toString());
			promocion.setCantidadViajesPasajero(obj[3].toString());
			promocion.setTotalDescuento(((BigDecimal)obj[4]).doubleValue());
			promocion.setTotalVenta(((BigDecimal)obj[5]).doubleValue());
			
			lstVentPromo.add(promocion);
		}
		
		return lstVentPromo;
	}

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.model.dao.ReportesDAO#ventasPromocionLstPromociones(java.lang.String, java.lang.String)
	 */
	@Override
	public ArrayList<Promocion> ventasPromocionLstPromociones(String fechaInicio, String fechaFin) throws Exception {
		// TODO Auto-generated method stub
		String sql="SELECT pro.promocion_id, pro.c_denominacion as Promocion "+
					"FROM VRTVENPAS vp "+
					     "INNER JOIN (SELECT MIN(v.venpas_id)venpas_id FROM VRTVENPAS v GROUP BY v.venpas_idoriginal "+
					                ")mvenpas_id ON (mvenpas_id.venpas_id=vp.venpas_id) "+
					     "INNER JOIN VRMPROMOCION pro ON (vp.promocion_id=pro.promocion_id) "+
					"WHERE vp.d_fecliq BETWEEN to_date('"+fechaInicio+"','dd/MM/yyyy') AND to_date('"+fechaFin+"','dd/MM/yyyy') "+
					     "AND vp.tipmov_id IN (1,8) "+
					     "AND vp.c_tiptra='1' "+
					     "AND vp.tipcom_id=1 "+
					     "AND vp.c_estreg='A' "+
					     "AND pro.n_estarifa=0 "+
					"GROUP BY pro.promocion_id,pro.c_Denominacion "+
					"ORDER BY pro.c_Denominacion";
		
		
		log.info(sql);
		
		List<?>lstResult=getSession().createSQLQuery(sql).list();
		ArrayList<Promocion>lstPromociones=new ArrayList<Promocion>();
		for(int i=0; i<lstResult.size();i++){
			Object[] obj=(Object[]) lstResult.get(i);
			
			Promocion promocion=new Promocion();
			promocion.setId(((BigDecimal)obj[0]).longValue());
			promocion.setDenominacion(obj[1].toString());
			
			
			lstPromociones.add(promocion);
		}
		
		return lstPromociones;
	}

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.model.dao.ReportesDAO#ventasPromocionDeta(java.lang.String, java.lang.String, java.lang.Long)
	 */
	@Override
	public ArrayList<VentaPasaje> ventasPromocionDeta(String fechaInicio,String fechafin, Long idPromocion) throws Exception {
		String sql="SELECT vp.d_fecliq "+
					      ",vp.c_numboleto "+
					      ",p.c_nomape  "+
					      ",r.c_origen "+
					      ",r.c_Destino "+
					      ",s.c_denominacion as servicio "+
					      ",vp.d_fecpar "+
					      ",vp.c_horpar "+
					      ",vp.n_tarifa "+
					      ",vp.n_descuento "+
					      ",vp.n_imppag "+
					      ",us.c_apepat "+
					      ",us.c_apemat "+
					      ",us.c_nombre  "+
					      ",agv.c_Denominacion as agencia "+
					"FROM VRTVENPAS vp "+
					     "INNER JOIN (SELECT MIN(v.venpas_id)venpas_id FROM VRTVENPAS v GROUP BY v.venpas_idoriginal "+
					                ")mvenpas_id ON (mvenpas_id.venpas_id=vp.venpas_id) "+
					     "INNER JOIN VRMPASAJERO p ON (p.pasajero_id=vp.pasajero_id) "+
					     "INNER JOIN VRMRUTA r ON (r.ruta_id=vp.ruta_id) "+
					     "INNER JOIN VRMSERVICIO s ON (s.servicio_id=vp.servicio_id) "+
					     "INNER JOIN VRMUSUARIO us ON (us.usuario_id=vp.usuario_id) "+
					     "INNER JOIN VRMAGENCIA agv ON (agv.agencia_id=vp.agencia_id) "+
					"WHERE vp.d_fecliq BETWEEN to_date('"+fechaInicio+"','dd/MM/yyyy') AND to_date('"+fechafin+"','dd/MM/yyyy') "+
					     "AND vp.tipmov_id IN (1,8) "+
					     "AND vp.c_tiptra='1' "+
					     "AND vp.tipcom_id in (1,2,7) "+
					     "AND vp.c_estreg='A' "+
					     "AND vp.promocion_id="+idPromocion+" "+
					"ORDER BY vp.d_fecliq,p.c_nomape,vp.venpas_id ";
		log.info(sql);
		ArrayList<VentaPasaje>lstVentasPromo=new ArrayList<VentaPasaje>();
		try {
			List<?>lstResult=getSession().createSQLQuery(sql).list();
			for(int i=0;i<lstResult.size();i++){
				Object[] obj=(Object[]) lstResult.get(i);
				
				VentaPasaje ventaPasaje=new VentaPasaje();
				ventaPasaje.setFechaLiquidacion((Date)obj[0]);
				ventaPasaje.setNumeroBoleto(obj[1].toString());
				
				Pasajero pasajero=new Pasajero();
				pasajero.setNombresApellidos(obj[2].toString());
				
				Ruta ruta=new Ruta();
				ruta.setOrigen(obj[3].toString());
				ruta.setDestino(obj[4].toString());
				
				Servicio servicio=new Servicio();
				servicio.setDenominacion(obj[5].toString());
				
				ventaPasaje.setFechaPartida(obj[6]!=null?(Date)obj[6]:null);
				ventaPasaje.setHoraPartida(obj[7]!=null?obj[7].toString():null);
				ventaPasaje.setTarifa(((BigDecimal)obj[8]).doubleValue());
				ventaPasaje.setDescuento(((BigDecimal)obj[9]).doubleValue());
				ventaPasaje.setImportePagado(((BigDecimal)obj[10]).doubleValue());
				
				Usuario usuario=new Usuario();
				usuario.setApellidoPaterno(obj[11]!=null?obj[11].toString():null);
				usuario.setApellidoMaterno(obj[12]!=null?obj[12].toString():null);
				usuario.setNombre(obj[13]!=null?obj[13].toString():null);
				
				Agencia agencia=new Agencia();
				agencia.setDenominacion(obj[14].toString());
				
				ventaPasaje.setPasajero(pasajero);
				ventaPasaje.setRuta(ruta);
				ventaPasaje.setServicio(servicio);
				ventaPasaje.setUsuario(usuario);
				ventaPasaje.setAgencia(agencia);
				
				lstVentasPromo.add(ventaPasaje);
			}
		} catch (Exception e) {
			e.getStackTrace();
		}
		
		
		return lstVentasPromo;
	}

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.model.dao.ReportesDAO#avancesBuses(java.lang.String, java.lang.String, java.lang.Integer, java.lang.Integer, java.lang.Integer, java.lang.Integer)
	 */
	@Override
	public RptAvanceBuses avancesBuses(String fechaInicio, String fechaFin,Integer idLocalidadOrigen, Integer idLocalidadDestino,Integer idServicio, Integer codigoBus) throws Exception {
		
		/*Busqueda para armar el encabezado*/
		String sql="SELECT '' item ,to_char(i.d_Fecpar,'dd/MM/yyyy') partida "
			     + "FROM VRTITINERARIO i "
			       + "INNER JOIN VRMSERVICIO  s ON (s.servicio_id=i.servicio_id) "
			       + "INNER JOIN VRMRUTA r ON (r.ruta_id=i.ruta_idmayor) "
			       + "LEFT JOIN VRMBUS b ON (b.bus_id=i.bus_id) "
			     + "WHERE i.d_fecpar BETWEEN '"+fechaInicio+"' AND '"+fechaFin+"' "
			       + "AND r.localidad_idorigen=NVL("+idLocalidadOrigen+",r.localidad_idorigen) "
			       + "AND r.localidad_iddestino=NVL("+idLocalidadDestino+",r.localidad_iddestino) "
			       + "AND s.servicio_id=NVL("+idServicio+",s.servicio_id) ";
		if(codigoBus!=null)
	        sql+="AND b.c_codigo=NVL("+codigoBus+",b.c_codigo) ";
       sql+="AND i.n_esanulado=0 AND i.c_estreg='A' "
	     + "GROUP BY i.d_Fecpar "
	     + "ORDER BY i.d_Fecpar";
		log.info("Buscando las fechas para armar el encabezado dinamico "+sql);
		List<?>resultEncabezado=getSession().createSQLQuery(sql).list();
		List<String>lstEncavezado=new ArrayList<>();
		for(int i=0;i<resultEncabezado.size();i++){
			Object[] obj=(Object[]) resultEncabezado.get(i);
			lstEncavezado.add(obj[1].toString());
		}
			
			
		/*Busca el avance de buses*/	
		sql="SELECT to_char(i.d_Fecpar,'dd/MM/yyyy') partida,i.c_horpar, s.c_Denominacion servicio, r.c_origen||'  - '||r.c_destino ruta "
				+ ",CAST (b.c_codigo AS NUMBER )bus "
				+ ",DECODE(r.localidad_idorigen,13,0,DECODE(r.localidad_iddestino,13,1,2)) TipoRuta "
		  + "FROM VRTITINERARIO i "
		    + "INNER JOIN VRMSERVICIO  s ON (s.servicio_id=i.servicio_id) "
		    + "INNER JOIN VRMRUTA r ON (r.ruta_id=i.ruta_idmayor) "
		    + "LEFT JOIN VRMBUS b ON (b.bus_id=i.bus_id) "
		  + "WHERE i.d_fecpar BETWEEN '"+fechaInicio+"' AND '"+fechaFin+"' "
		    + "AND r.localidad_idorigen=NVL("+idLocalidadOrigen+",r.localidad_idorigen) "
	        + "AND r.localidad_iddestino=NVL("+idLocalidadDestino+",r.localidad_iddestino) "
	        + "AND s.servicio_id=NVL("+idServicio+",s.servicio_id) ";
		if(codigoBus!=null)
	        sql+="AND b.c_codigo=NVL("+codigoBus+",b.c_codigo) ";
	    sql+="AND i.n_esanulado=0 AND i.c_estreg='A' "
	    	+ "ORDER BY DECODE(r.localidad_idorigen,13,0,DECODE(r.localidad_iddestino,13,1,2)) "
	          + ",i.c_horpar||i.d_fecpar, r.c_origen||'-'||r.c_destino";
		log.info("Buscando avance de Buses "+sql);
		List<?>resulAvance=getSession().createSQLQuery(sql).list();
		TreeMap<String, RptAvanceBus> mapAvanceBuses=new TreeMap<>();
		for(int i=0;i<resulAvance.size();i++){
			Object[] obj=(Object[]) resulAvance.get(i);
			RptAvanceBus avanceBus=new RptAvanceBus();
			avanceBus.setFechaPartida(obj[0].toString());
			avanceBus.setHoraPartida(obj[1].toString());
			avanceBus.setServicio(obj[2].toString());
			avanceBus.setRuta(obj[3].toString());
			avanceBus.setBus(obj[4]!=null?((BigDecimal)obj[4]).intValue():null);
			avanceBus.setTipoRuta(((BigDecimal)obj[5]).intValue());
			
			String key=avanceBus.toString();//.replace(avanceBus.getFechaPartida(), "");
			mapAvanceBuses.put(key, avanceBus);
		}
		RptAvanceBuses avanceBuses=new RptAvanceBuses();
		avanceBuses.setMapAvanceBus(mapAvanceBuses);
		avanceBuses.setEncabezado(lstEncavezado);
		
		return avanceBuses;
	}
	

	
}
