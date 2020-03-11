package com.cystesoft.vyrbus.model.dao.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.TreeMap;

import com.cystesoft.vyrbus.model.bean.Agencia;
import com.cystesoft.vyrbus.model.bean.Bus;
import com.cystesoft.vyrbus.model.bean.HRE;
import com.cystesoft.vyrbus.model.bean.Itinerario;
import com.cystesoft.vyrbus.model.bean.Localidad;
import com.cystesoft.vyrbus.model.bean.NumeroHojaRutaID;
import com.cystesoft.vyrbus.model.bean.Personal;
import com.cystesoft.vyrbus.model.bean.ProgramacionServicio;
import com.cystesoft.vyrbus.model.bean.Ruta;
import com.cystesoft.vyrbus.model.bean.Servicio;
import com.cystesoft.vyrbus.model.bean.TipoItinerario;
import com.cystesoft.vyrbus.model.dao.ProgramacionServicioDAO;
import com.cystesoft.vyrbus.service.util.Constantes;

/**
 * 
 * @author JABANTO
 *
 */
@SuppressWarnings("unchecked")
public class ProgramacionServicioDAOImpl extends GenericDAOImpl implements ProgramacionServicioDAO {

	/*
	 * (non-Javadoc)
	 * @see com.tepsa.sisvyr.model.dao.ProgramacionServicioDAO#buscarProgramacion(java.lang.Long)
	 */
	@Override
	public List<ProgramacionServicio> buscarProgramacion(Long itinerario)throws Exception {

		return null;
	}

	/*
	 * (non-Javadoc)
	 * @see com.tepsa.sisvyr.model.dao.ProgramacionServicioDAO#buscarPorX(java.util.TreeMap, java.util.List)
	 */
	@Override
	public ArrayList<ProgramacionServicio> buscarPorX(TreeMap<String, Object> criteriosBusqueda,List<String> criteriosOrdenar) {
		return (ArrayList<ProgramacionServicio>) super.findByX(ProgramacionServicio.class, criteriosBusqueda, criteriosOrdenar);
		
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.tepsa.sisvyr.model.dao.ProgramacionServicioDAO#buscarItinerariosProgramados(java.lang.Long, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.Boolean)
	 */
	@Override
	public List<ProgramacionServicio> buscarItinerariosProgramados(Long idItinerario, String origen,String destino, String fechaInicio, String fechaFinal,
		String Servicio, Boolean itinerariosProgramados) throws Exception {
		
		String sql=null;
		String where=null;

		List<ProgramacionServicio> lstResult = new ArrayList<ProgramacionServicio>();
		if (idItinerario != null){
			//*BUSQUEDA POR NÚMERO ITINERARIO*/
			where="i.itinerario_id="+idItinerario+" ";	
		}else if (origen != null  && fechaInicio !=null && fechaFinal !=null && destino ==null && Servicio==null && idItinerario ==null){
			//*BUSQUEDA POR ORIGEN Y RANGO DE FECHAS*/
			where="i.d_fecpar between to_date('"+fechaInicio+"','dd/mm/yyyy') AND to_date('"+fechaFinal+"','dd/mm/yyyy') AND " +
					"r.c_origen ='"+origen+"' ";
		}else if (destino != null  && fechaInicio !=null && fechaFinal !=null && origen ==null && Servicio==null && idItinerario ==null){
			//*BUSQUEDA POR DESTINO Y RANGO DE FECHAS*/
			where="i.d_fecpar between to_date('"+fechaInicio+"','dd/mm/yyyy') AND to_date('"+fechaFinal+"','dd/mm/yyyy') AND " +
					"r.c_destino ='"+destino+"' ";
		}else if (Servicio != null  && fechaInicio !=null && fechaFinal !=null && origen ==null && destino==null && idItinerario ==null){
			//*BUSQUEDA POR SERVICIO Y RANGO DE FECHAS*/
			where="i.d_fecpar between to_date('"+fechaInicio+"','dd/mm/yyyy') AND to_date('"+fechaFinal+"','dd/mm/yyyy') AND " +
				  "s.c_denominacion ='"+Servicio+"' ";
		}else if (fechaInicio != null && fechaFinal != null && origen != null && destino != null && Servicio ==null){
			//*BUSQUEDA POR RANGO DE FECHAS, ORIGEN Y DESTINO*/
			where="i.d_fecpar between to_date('"+fechaInicio+"','dd/mm/yyyy') AND to_date('"+fechaFinal+"','dd/mm/yyyy') AND " +
					" r.c_origen ='"+origen+"' AND r.c_destino ='"+destino+"' ";
		}else if (fechaInicio != null && fechaFinal != null && origen != null && destino != null && Servicio != null){
			//*BUSQUEDA POR RANGO DE FECHAS, ORIGEN, DESTINO Y SERVICIO*/
			where="i.d_fecpar between to_date('"+fechaInicio+"','dd/mm/yyyy') AND to_date('"+fechaFinal+"','dd/mm/yyyy') AND " +
					" r.c_origen ='"+origen+"' AND r.c_destino ='"+destino+"' AND s.c_denominacion ='"+Servicio+"' ";
		}else if (fechaInicio != null && fechaFinal != null && origen != null && destino == null && Servicio == null){
			//*BUSQUEDA POR RANGO DE FECHAS, ORIGEN*/
			where="i.d_fecpar between to_date('"+fechaInicio+"','dd/mm/yyyy') AND to_date('"+fechaFinal+"','dd/mm/yyyy') AND " +
					" r.c_origen ='"+origen+"' ";
		}else if (fechaInicio != null && fechaFinal != null && destino != null  && origen == null && Servicio == null){
			//*BUSQUEDA POR RANGO DE FECHAS, DESTINO*/
			where="i.d_fecpar between to_date('"+fechaInicio+"','dd/mm/yyyy') AND to_date('"+fechaFinal+"','dd/mm/yyyy') AND " +
					"r.c_destino ='"+destino+"' ";
		}else if (fechaInicio != null && fechaFinal != null && origen != null && Servicio != null && destino == null  ){
			//*BUSQUEDA POR RANGO DE FECHAS, ORIGEN Y SERVICIO*/
			where="i.d_fecpar between to_date('"+fechaInicio+"','dd/mm/yyyy') AND to_date('"+fechaFinal+"','dd/mm/yyyy') AND " +
					"s.c_denominacion ='"+Servicio+"' AND r.c_origen ='"+origen+"' ";
		}else if (fechaInicio != null && fechaFinal != null && destino!= null && Servicio != null && origen == null  ){
			//*BUSQUEDA POR RANGO DE FECHAS, DESTINO Y SERVICIO*/
			where="i.d_fecpar between to_date('"+fechaInicio+"','dd/mm/yyyy') AND to_date('"+fechaFinal+"','dd/mm/yyyy') AND " +
					"s.c_denominacion ='"+Servicio+"' AND r.c_destino ='"+destino+"' ";
		}else if (fechaInicio != null && fechaFinal != null && destino == null  && origen == null && Servicio == null){
			//*BUSQUEDA POR RANGO DE FECHAS*/
			where="i.d_fecpar between to_date('"+fechaInicio+"','dd/mm/yyyy') AND to_date('"+fechaFinal+"','dd/mm/yyyy') And i.N_EsAnulado=0 And i.C_EstReg='A'";
		}
		
	
		if (where != null){
			
			if (itinerariosProgramados==true){
				/*RECUPERA PROGRAMACION.*/
				sql = "SELECT i.itinerario_id, i.d_fecpar, i.c_horpar, i.d_feclle, i.c_horlle, " + //4
							"0, 0, " + //6
							"s.servicio_id, s.c_denominacion servicio, " + //8
							"r.ruta_id, r.c_origen origen, r.c_destino destino, " + //11
							"ao.agencia_id idAgeOrigen, ao.c_nomcor agpartida, ao.c_denominacion ageOrigen,  " + //14
							"ad.agencia_id idAgeDestino, ad.c_nomcor agllegada, ad.c_denominacion ageDestino, " + //17
							"lo.localidad_id idOrigen, " + //18
							"ld.localidad_id idDestino, " + //19
							"ti.tipiti_id, ti.c_denominacion tipoItinerario, " + //21
							"ps.bus_ID, ps.personal_idpiloto, personal_idcopiloto, personal_idterramoza, b.bus_id, ps.proser_Id, " +  //27
							"ps.audfecins, ps.audusuins, ps.audipinse, personal_idcopilotoaux " + //31
						"FROM vrtitinerario i " +
							"INNER JOIN vrmservicio s ON s.servicio_id=i.servicio_id " +
							"INNER JOIN vrmruta r ON r.ruta_id=i.ruta_idmayor " +
							"INNER JOIN vrmagencia ao ON ao.agencia_id=i.agencia_idpartida " +
							"INNER JOIN vrmagencia ad ON ad.agencia_id=i.agencia_idllegada " +
							"INNER JOIN vrmlocalidad lo ON lo.localidad_id=r.localidad_idOrigen " +
							"INNER JOIN vrmlocalidad ld ON ld.localidad_id=r.localidad_idDestino " +
							"INNER JOIN vrmtipiti ti ON ti.tipiti_id=i.tipiti_id " +
							"INNER JOIN vrtproser ps ON (ps.itinerario_ID=i.itinerario_ID) " +
							"INNER JOIN vrmbus b ON b.bus_id=ps.bus_id " +
						"WHERE " + where + " And i.N_EsAnulado=0 And i.C_EstReg='A' And ps.c_estreg='A' " +
						" ORDER BY i.d_fecpar, i.c_horpar ";
				
			}else{
				/*RECUPERA ITINERARIOS PARA LA PROGRAMACION*/
				sql = "SELECT i.itinerario_id, i.d_fecpar, i.c_horpar, i.d_feclle, i.c_horlle, " + //4
							"0, 0, " + //6
							"s.servicio_id, s.c_denominacion servicio, " + //8
							"r.ruta_id, r.c_origen origen, r.c_destino destino, " + //11
							"ao.agencia_id idAgeOrigen, ao.c_nomcor agpartida, ao.c_denominacion ageOrigen,  " + //14
							"ad.agencia_id idAgeDestino, ad.c_nomcor agllegada, ad.c_denominacion ageDestino, " + //17
							"lo.localidad_id idOrigen, " + //18
							"ld.localidad_id idDestino, " + //19
							"ti.tipiti_id, ti.c_denominacion tipoItinerario " + //21
						"FROM vrtitinerario i " +
							"INNER JOIN vrmservicio s ON s.servicio_id=i.servicio_id " +
							"INNER JOIN vrmruta r ON r.ruta_id=i.ruta_idmayor " +
							"INNER JOIN vrmagencia ao ON ao.agencia_id=i.agencia_idpartida " +
							"INNER JOIN vrmagencia ad ON ad.agencia_id=i.agencia_idllegada " +
							"INNER JOIN vrmlocalidad lo ON lo.localidad_id=r.localidad_idOrigen " +
							"INNER JOIN vrmlocalidad ld ON ld.localidad_id=r.localidad_idDestino " +
							"INNER JOIN vrmtipiti ti ON ti.tipiti_id=i.tipiti_id " +
						"WHERE " + where + " And i.N_EsAnulado=0 And i.C_EstReg='A' " +
						"and i.itinerario_ID not in (Select ps.itinerario_ID From vrtproser ps where ps.itinerario_ID =itinerario_ID And ps.c_estreg='A') " +  
						" ORDER BY  i.d_fecpar, i.c_horpar";
			}
			
			List<?> result = getSession().createSQLQuery(sql).list();
			for(int i=0; i<result.size(); i++){
				Object[] obj = (Object[]) result.get(i);
				
				Localidad localidadOrigen = new Localidad();
				localidadOrigen.setId(((BigDecimal)obj[18]).intValue());
				
				Localidad localidadDestino = new Localidad();
				localidadDestino.setId(((BigDecimal)obj[19]).intValue());

				Ruta ruta = new Ruta();
				ruta.setId(((BigDecimal)obj[9]).intValue());
				ruta.setOrigen(obj[10].toString());
				ruta.setDestino(obj[11].toString());
				ruta.setLocalidadDestino(localidadDestino);
				ruta.setLocalidadOrigen(localidadOrigen);
				
				Agencia agenciaPartida = new Agencia();
				agenciaPartida.setId(((BigDecimal)obj[12]).intValue());
				agenciaPartida.setNombreCorto(obj[13].toString());
				agenciaPartida.setDenominacion(obj[14].toString());
				
				Agencia agenciaLlegada = new Agencia();
				agenciaLlegada.setId(((BigDecimal)obj[15]).intValue());
				agenciaLlegada.setNombreCorto(obj[16].toString());
				agenciaLlegada.setDenominacion(obj[17].toString());
				
				TipoItinerario tipoItinerario = new TipoItinerario();
				tipoItinerario.setId(((BigDecimal)obj[20]).intValue());
				tipoItinerario.setDenominacion((obj[21]).toString());

				Servicio servicio = new Servicio();
				servicio.setId(((BigDecimal)obj[7]).intValue());
				servicio.setDenominacion(obj[8].toString());
							
				Itinerario itinerario = new Itinerario();
				itinerario.setId(((BigDecimal)obj[0]).longValue());
				itinerario.setFechaPartida((Date)obj[1]);
				itinerario.setHoraPartida(obj[2].toString());
				itinerario.setFechaLlegada((Date)obj[3]);
				itinerario.setHoraLlegada(obj[4].toString());
				
				itinerario.setTipoItinerario(tipoItinerario);
				itinerario.setAgenciaPartida(agenciaPartida);		
				itinerario.setAgenciaLlegada(agenciaLlegada);
				itinerario.setAgenciaLlegada(agenciaLlegada);
				//itinerario.setBus(bus);
				itinerario.setServicio(servicio);
				itinerario.setRuta(ruta);
				
				ProgramacionServicio programacionServicio = new ProgramacionServicio();
				
				/*Solo cuando para la busqueda programaciones */
				if (itinerariosProgramados==true){
					Bus bus = new Bus();
					bus.setId(((BigDecimal)obj[26]).intValue());
					Personal piloto = new Personal();
					piloto.setId(((BigDecimal)obj[23]).longValue());
					Personal copiloto = new Personal();
					copiloto.setId(((BigDecimal)obj[24]).longValue());
					Personal copilotoAux=null;
					if(obj[31]!=null)
						copilotoAux=new Personal(((BigDecimal)obj[31]).longValue());
					
					Personal tripulante = new Personal();
					tripulante.setId(((BigDecimal)obj[25]).longValue());
					
					programacionServicio.setId(((BigDecimal)obj[27]).longValue());
					programacionServicio.setBus(bus);
					programacionServicio.setPiloto(piloto);
					programacionServicio.setCopiloto(copiloto);
					programacionServicio.setCopilotoAuxiliar(copilotoAux);
					programacionServicio.setTripulante(tripulante);
					programacionServicio.setFechaInsercion((Date)obj[28]);
					programacionServicio.setUsuarioInsercion(obj[29].toString());
					programacionServicio.setIpInsercion(obj[30].toString());
				}
				
				programacionServicio.setItinerario(itinerario);
				
				lstResult.add(programacionServicio);
			}
		}
		return lstResult;
	}

	/*
	 * (non-Javadoc)
	 * @see com.tepsa.sisvyr.model.dao.ProgramacionServicioDAO#buscaBusesNoProgramados(java.lang.String, java.lang.Integer)
	 */
	@SuppressWarnings("deprecation")
	@Override
	public List<ProgramacionServicio> validacionBusesProgramados(String fechaPartida,String horaPartida, Integer idBus,Integer idAgeciaPartida) throws Exception {
		
		List<ProgramacionServicio> lstResult = new ArrayList<ProgramacionServicio>();

		String sql="SELECT ps.itinerario_ID,  i.ruta_idmayor " +
				   		 ",r.c_origen, r.c_destino, ps.proser_ID " +
				   		 ",i.d_fecpar, i.c_horpar,i.d_feclle,i.c_horlle,i.agencia_idllegada " +
				   "FROM vrtproser ps " +
				   "INNER JOIN vrtitinerario i on (i.itinerario_id=ps.itinerario_id) " +
				   "INNER JOIN vrmruta r on (r.ruta_ID=i.ruta_idmayor) " +
//				   "INNER JOIN vrmpersonal p on (p.personal_ID=ps.personal_idpiloto) " +
//				   "INNER JOIN vrmpersonal cp on (cp.personal_ID=ps.personal_idcopiloto) " +
//				   "INNER JOIN vrmpersonal t on (t.personal_ID=ps.personal_idterramoza) " +
				   " WHERE ps.bus_id=" + idBus + " and i.d_fecpar = to_date('"+fechaPartida+"','dd/mm/yyyy') And ps.c_estreg='A' ";
		
		List<?> result = getSession().createSQLQuery(sql).list();
		for(int i=0; i < result.size(); i++){
			Object[] obj = (Object[]) result.get(i);
			
			Ruta ruta = new Ruta();
			ruta.setId(((BigDecimal)obj[1]).intValue());
			ruta.setOrigen(obj[2].toString());
			ruta.setDestino(obj[3].toString());
			
			Agencia agenciaLlegada=new Agencia();
			agenciaLlegada.setId(((BigDecimal)obj[9]).intValue());
			
			Itinerario itinerario = new Itinerario();
			itinerario.setId(((BigDecimal)obj[0]).longValue());
			itinerario.setFechaPartida((Date)obj[5]);
			itinerario.setHoraPartida(obj[6].toString());
			itinerario.setFechaLlegada((Date)obj[7]);
			itinerario.setHoraLlegada(obj[8].toString());
			itinerario.setAgenciaLlegada(agenciaLlegada);
			itinerario.setRuta(ruta);
			
						
			ProgramacionServicio programacionServicio = new ProgramacionServicio();
			programacionServicio.setId(((BigDecimal)obj[4]).longValue());
			programacionServicio.setItinerario(itinerario);
			
			//Valida si el bus esta en el lugar de donde se esta programando. (En base a la fecha y hora de llegada)
			//Fecha hora de llegada el bus ya programado
			Date dfechaHoraLlegada=itinerario.getFechaLlegada();
			dfechaHoraLlegada.setHours(Integer.valueOf(itinerario.getHoraLlegada().substring(0, itinerario.getHoraLlegada().indexOf(":"))) );
			dfechaHoraLlegada.setMinutes(Integer.valueOf(itinerario.getHoraLlegada().substring(itinerario.getHoraLlegada().indexOf(":")+1,itinerario.getHoraLlegada().length())) );
			
			//Fecha y hora de partida de la programacion que se esta realizando
			Date dfechaHoraPartida=itinerario.getFechaPartida();
			dfechaHoraPartida.setHours(Integer.valueOf(horaPartida.substring(0,horaPartida.indexOf(":"))));
			dfechaHoraPartida.setMinutes(Integer.valueOf(horaPartida.substring(horaPartida.indexOf(":")+1,horaPartida.length())));
			//Realiza validacion 
			if(dfechaHoraPartida.getTime()<dfechaHoraLlegada.getTime() || idAgeciaPartida.intValue()!=agenciaLlegada.getId().intValue()){
				lstResult.add(programacionServicio);
			}
		}
		
		return lstResult;
	}

	
	
	
	
	/*
	 * (non-Javadoc)
	 * @see com.tepsa.sisvyr.model.dao.ProgramacionServicioDAO#inactivar(java.lang.Long)
	 */
	@Override
	public void inactivar(Long id) throws Exception {
		super.inactivate(ProgramacionServicio.class, id);
		
	}

	/*
	 * (non-Javadoc)
	 * @see com.tepsa.sisvyr.model.dao.ProgramacionServicioDAO#udateItinerario(java.lang.Long)
	 */
	@Override
	public void updateItinerarioBus(Long idItinerario, Long idBus) throws Exception {
		String Sql= "Update vrtitinerario set Bus_ID="+idBus + " " +
					"Where itinerario_ID="+idItinerario ;	
		getSession().createSQLQuery(Sql).executeUpdate();
	}

	/*
	 * (non-Javadoc)
	 * @see com.tepsa.sisvyr.model.dao.ProgramacionServicioDAO#validaIngresoChoferTripulante(java.lang.String, java.lang.Integer)
	 */
	@SuppressWarnings("deprecation")
	@Override
	public ProgramacionServicio validaIngresoChoferTripulante(String fecha, Long idPiloto, Long idCopiloto, Long idTripulante,String horaPartida, Integer idAgenciaPartida) throws Exception {
		String sql="SELECT i.itinerario_id, i.c_horpar as horaPartida, r.c_origen, r.c_destino,  b.c_codigo as bus, ps.proser_id " +
							",i.d_fecpar,i.d_feclle,i.c_horlle,i.agencia_idllegada "+//6-9
				   "FROM  vrtproser ps "+
				   "INNER JOIN vrtitinerario i ON (i.itinerario_id=ps.itinerario_id) "+
				   "INNER JOIN vrmruta r ON (r.ruta_id=i.ruta_idmayor) "+
				   "INNER JOIN vrmbus b ON (b.bus_id=ps.bus_id) "+
				   "WHERE i.d_fecpar =to_date('"+fecha+"','dd/MM/yyyy') "+
				   "AND ps.c_estreg='A' AND i.c_estreg='A'";
		if (idPiloto!=null){
			sql+=" AND ps.personal_idpiloto="+idPiloto;
		}else if(idCopiloto!=null){
			sql+=" AND ps.personal_idcopiloto="+idCopiloto;
		}else if(idTripulante!=null){
			sql+=" AND ps.personal_idterramoza="+idTripulante;
		}
		
		sql+="ORDER BY ps.proser_id ";
		
		log.info(sql);
		
		List<?> result = getSession().createSQLQuery(sql).list();
		ProgramacionServicio programacion=null;
		for(int i=0; i < result.size(); i++){
			Object[] obj = (Object[]) result.get(i);
			programacion=new ProgramacionServicio();
			
			Ruta ruta=new Ruta();
			ruta.setOrigen(obj[2].toString());
			ruta.setDestino(obj[3].toString());
			
			Itinerario itinerario=new Itinerario();
			itinerario.setId(((BigDecimal)obj[0]).longValue());
			itinerario.setHoraPartida(obj[1].toString());
			itinerario.setFechaPartida((Date)obj[6]);
			itinerario.setFechaLlegada((Date)obj[7]);
			itinerario.setHoraLlegada(obj[8].toString());
			itinerario.setRuta(ruta);
					
			Agencia agenciaLlegada=new Agencia();
			agenciaLlegada.setId(((BigDecimal)obj[9]).intValue());
			
			itinerario.setAgenciaLlegada(agenciaLlegada);
			
			Bus bus=new Bus();
			bus.setCodigo(obj[4].toString());
		
			programacion.setId(((BigDecimal)obj[5]).longValue());
			programacion.setItinerario(itinerario);
			programacion.setBus(bus);
			
			//Valida si el bus esta en el lugar de donde se esta programando. (En base a la fecha y hora de llegada)
			//Fecha hora de llegada el bus ya programado
			Date dfechaHoraLlegada=itinerario.getFechaLlegada();
			dfechaHoraLlegada.setHours(Integer.valueOf(itinerario.getHoraLlegada().substring(0, itinerario.getHoraLlegada().indexOf(":"))) );
			dfechaHoraLlegada.setMinutes(Integer.valueOf(itinerario.getHoraLlegada().substring(itinerario.getHoraLlegada().indexOf(":")+1,itinerario.getHoraLlegada().length())) );
			
			//Fecha y hora de partida de la programacion que se esta realizando
			Date dfechaHoraPartida=itinerario.getFechaPartida();
			dfechaHoraPartida.setHours(Integer.valueOf(horaPartida.substring(0,horaPartida.indexOf(":"))));
			dfechaHoraPartida.setMinutes(Integer.valueOf(horaPartida.substring(horaPartida.indexOf(":")+1,horaPartida.length())));
			
			//Realiza validacion 
			if(dfechaHoraPartida.getTime()>dfechaHoraLlegada.getTime() && idAgenciaPartida.intValue()==agenciaLlegada.getId().intValue()){
				programacion=null;
			}
		}
		return programacion;
	}

	/* 
	 * (non-Javadoc)
	 * @see com.tepsa.sisvyr.model.dao.ProgramacionServicioDAO#buscarProgracion(java.lang.Integer, java.lang.String, java.lang.String, java.lang.Boolean)
	 */
	@Override
	public List<ProgramacionServicio> buscarProgracion(Integer idAgencia,String fecha, String codigoBus,Boolean isSalida) throws Exception {
		String sql="SELECT ps.proser_id as idProgramacion "+
					      ",i.d_fecpar as fechaPartida "+
					      ",iap.c_horpar as horaPartida "+
					      ",i.d_feclle as fechaLlegada "+
					      ",i.c_horlle as horaLlegada "+
					      ",b.c_codigo as codigoBus  "+
					      ",r.c_origen as origen "+
					      ",r.c_destino as destino "+
					      ",s.c_denominacion as servicio "+
					      ",hre.c_numhojrut as nroHojaRuta "+
					      ",hre.d_fecllerea as fechaLlegadaReal "+
					      ",hre.c_horllerea as horaLlegadaReal "+
					      ",m.n_mee mee "+ //si el manifiesto electronico fue emitido
					      ",hre.agencia_idsalida "+
					"FROM VRTPROSER ps "+
					     "INNER JOIN VRTITINERARIO i ON (i.itinerario_id=ps.itinerario_id) "+
					     "INNER JOIN VRTITIAGEPAR iap ON (iap.itinerario_id=ps.itinerario_id) "+
					     //Consistencia para mostrar solamente los servicios del punto de embarque con la hora menor.
//						 "INNER JOIN(SELECT AP.ITINERARIO_ID, AP.C_HORPAR,AP.AGENCIA_ID "+
//									"FROM VRTITIAGEPAR AP "+ 
//									     "INNER JOIN (SELECT AP.ITINERARIO_ID, MIN(AP.C_HORPAR)C_HORPAR FROM VRTITIAGEPAR AP GROUP BY AP.ITINERARIO_ID "+     
//									                ")APM ON (APM.ITINERARIO_ID=AP.ITINERARIO_ID AND APM.C_HORPAR=AP.C_HORPAR ) "+
//									 ")iap ON (iap.ITINERARIO_ID=ps.ITINERARIO_ID) "+
						 //--
					     "INNER JOIN VRMBUS b ON (b.bus_id=ps.bus_id) "+
					     "INNER JOIN VRMRUTA r ON (r.ruta_id=i.ruta_idmayor) "+
					     "INNER JOIN VRMSERVICIO s ON (s.servicio_id=i.servicio_id) "+
					     "LEFT JOIN VRTMANIFIESTO m ON (m.itinerario_id=ps.itinerario_id AND m.c_estreg='A') "+
					     "LEFT JOIN VRTHOJRUTELE hre ON (hre.itinerario_id=i.itinerario_id AND hre.c_estreg='A' ";
//					     "LEFT JOIN VRTHOJRUTELE hre ON (hre.ruta_id=i.ruta_idmayor AND hre.c_nrobus=b.c_codigo AND hre.c_estreg='A' ";
							
						if(isSalida){
							sql+=" AND hre.d_fecsal=i.d_fecpar ) ";
						}else{
							sql+="AND hre.d_fecestlle=i.d_feclle ) "+
								 "INNER JOIN VRTITIAGELLE ial ON (ial.itinerario_id=ps.itinerario_id) ";
						}
					sql+="WHERE i.n_esanulado=0 ";
					if(isSalida){
						 sql+="AND iap.agencia_id=NVL("+idAgencia+",iap.agencia_id) "+
					    	  "AND i.d_Fecpar=to_date('"+fecha+"','dd/MM/yyyy') ";
					}else{
						sql+="AND ial.agencia_id=NVL("+idAgencia+",ial.agencia_id) "+
							 "AND i.d_Feclle=to_date('"+fecha+"','dd/MM/yyyy') "+
							 "AND iap.agencia_id=i.agencia_idpartida ";
					}
				    sql+="AND b.c_codigo=NVL("+codigoBus+",b.c_codigo) "+
					     "AND i.c_estreg='"+Constantes.VALUE_ACTIVO+"'  "+ 
					     "AND ps.c_estreg='"+Constantes.VALUE_ACTIVO+"' ";
				    if (isSalida)
				    	sql+="ORDER BY i.d_fecpar, iap.c_horpar, concat(r.c_origen,r.c_destino)";
				    else 
				    	sql+="ORDER BY i.d_fecpar, i.c_horlle, concat(r.c_origen,r.c_destino)";
		log.info(sql);
		List<?> result=getSession().createSQLQuery(sql).list();
		List<ProgramacionServicio>lstProgramacion=new ArrayList<ProgramacionServicio>();
		List<Long>identificadoresProgramacion=new ArrayList<Long>();
		for(int x=0; x<result.size(); x++){
			
			Object[] obj=(Object[]) result.get(x);
			/*Programacion*/
			ProgramacionServicio programacion=new ProgramacionServicio();
			programacion.setId(((BigDecimal)obj[0]).longValue());
			/*Itinerario*/
			Itinerario itinerario=new Itinerario();
			itinerario.setFechaPartida((Date)obj[1]);
			itinerario.setHoraPartida(obj[2].toString());
			itinerario.setFechaLlegada((Date)obj[3]);
			itinerario.setHoraLlegada(obj[4].toString());
			/*Bus*/
			Bus bus=new Bus();
			bus.setCodigo(obj[5].toString());
			/*Ruta*/
			Ruta ruta=new Ruta();
			ruta.setOrigen(obj[6].toString());
			ruta.setDestino(obj[7].toString());
			/*Servicio*/
			Servicio servicio=new Servicio();
			servicio.setDenominacion(obj[8].toString());
			
			/*Sea datos de la HRE*/
			HRE hojaRuta=null;
			if(obj[9]!=null){
				hojaRuta=new HRE();
				hojaRuta.setNumeroHojaRutaID(new NumeroHojaRutaID(obj[9].toString()));
				hojaRuta.setFechaLlegadaReal(obj[10]!=null?(Date)obj[10]:null);
				hojaRuta.setHoraLlegadaReal(obj[11]!=null?obj[11].toString():null);
				hojaRuta.setAgenciaSalida(new Agencia(((BigDecimal)obj[13]).intValue()));
			}
						
			itinerario.setBus(bus);
			itinerario.setServicio(servicio);
			itinerario.setRuta(ruta);
			programacion.setItinerario(itinerario);
			programacion.setBus(bus);
			programacion.setHojaRuta(hojaRuta);
			if(obj[12]!=null)
				programacion.setMEE(((BigDecimal)obj[12]).intValue()==0?false:true);
			
			/*Permite obviar duplicados en el caso que se seleccione en el fitro agencia "TODOS"*/
			if(!(identificadoresProgramacion.contains(programacion.getId()))){
				identificadoresProgramacion.add(programacion.getId());
				lstProgramacion.add(programacion);			
			}
		}		
		
		return lstProgramacion;
	}

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.model.dao.ProgramacionServicioDAO#buscarPorId(java.lang.Long)
	 */
	@Override
	public ProgramacionServicio buscarPorId(Long id) throws Exception {
		// TODO Auto-generated method stub
		return (ProgramacionServicio) findById(ProgramacionServicio.class, id);
	}

}
