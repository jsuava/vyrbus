/**
 * Proyecto		: SISVYR
 * Sistema		: Sistema de Ventas y Reservas
 * Descripci�n	:
 * Autor		: Jos� Abanto
 * Fecha		: 26 oct. 2019
 * Hora			: 10:42:28
 */
package pe.itsb.vyrbus.model.dao.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import pe.itsb.vyrbus.model.bean.CanalVenta;
import pe.itsb.vyrbus.model.bean.Empresa;
import pe.itsb.vyrbus.model.bean.Itinerario;
import pe.itsb.vyrbus.model.bean.Localidad;
import pe.itsb.vyrbus.model.bean.Ruta;
import pe.itsb.vyrbus.model.bean.Servicio;
import pe.itsb.vyrbus.model.bean.Tarifa;
import pe.itsb.vyrbus.model.bean.TarifaRegular;
import pe.itsb.vyrbus.model.dao.TarifaRegularDAO;
import pe.itsb.vyrbus.service.mappers.HistorialTarifa;

/**
 * @author Marco
 *
 */
public class TarifaRegularDAOImpl extends GenericDAOImpl implements TarifaRegularDAO {

	@Override
	public void delete (Long idTarifaRegular) throws Exception{
		String sql="DELETE FROM VRTTARREG WHERE VRTTARREG.TARREG_ID = " + idTarifaRegular;
		getSession().createSQLQuery(sql).executeUpdate();
	}

	/* (non-Javadoc)
	 * @see pe.itsb.vyrbus.model.dao.TarifaRegularDao#buscarPorServicio(java.lang.Integer, java.lang.Integer, java.lang.Integer, java.util.Date)
	 */
	@Override
	public List<TarifaRegular> buscarTarifaPorServicio(Integer canalVentaID, Integer servicioID, Integer rutaID, String fechaTarifa, String horaPartida, Integer piso, Integer zona, Integer empresaID) throws Exception {
		return buscarTarifa(canalVentaID, servicioID, rutaID, fechaTarifa, horaPartida, piso, zona, empresaID);
	}

	private List<TarifaRegular> buscarTarifa(Integer canalVentaID, Integer servicioID, Integer rutaID, String fechaTarifa, String horaPartida, Integer piso, Integer zona, Integer empresaID){
		String sql = "SELECT "
					+ "tr.tarreg_id, ta.tarifa_id, ta.canven_id, ta.servicio_id, ta.ruta_id, ta.n_pisbus, "
					+ "ta.n_zonbus, tr.d_fectar, tr.c_horpar, tr.itinerario_id,  tr.n_monto "
					+ "FROM vrttarifa ta inner join vrttarreg tr on (ta.tarifa_id = tr.tarifa_id) "
					+ "WHERE ta.canven_id= " + canalVentaID + " AND ta.servicio_id = " + servicioID + " AND ta.ruta_id= " + rutaID + " "
					+ "AND tr.d_fectar = to_date('" + fechaTarifa + "', 'dd/mm/yyyy') "
					+ "AND tr.c_horpar = '" + horaPartida + "' "
					+ "AND ta.empresa_id="+ empresaID + " ";
		if(piso!=null && zona!=null)
			sql += "AND ta.n_pisbus=" + piso + " AND ta.n_zonbus=" + zona + " ";
		sql += "AND ta.c_estreg='A' AND tr.c_estreg='A'";

		log.info(sql);
		List<?>result =getSession().createSQLQuery(sql).list();
		List<TarifaRegular> lstTarifaRegular = new ArrayList<>();
		for(int i=0;i<result.size();i++){
			Object[] obj = (Object[]) result.get(i);
			TarifaRegular tarifaRegular = new TarifaRegular();
			tarifaRegular.setId(((BigDecimal)obj[0]).longValue());
			Tarifa tarifa = new Tarifa();
			tarifa.setId(((BigDecimal)obj[1]).longValue());
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

	@Override
	public String buscarCantidadTarifasAReemplazar(Integer empresaID, Integer canalVentaID, Integer servicioID, Integer origenID, Integer destinoID, Integer piso, Integer zona, String fechaInicio, String fechaFin, String horaPartida, Integer PorServicio)throws Exception {
		Integer CantidadTarifas=0;
		Integer CantidadServicios=0;
		String strSql;
		strSql = "SELECT count(*) cantidad " +
				 "FROM vrttarifa t " +
				 "INNER JOIN  vrttarreg tr ON (t.tarifa_id = tr.tarifa_id) " +
				 "INNER JOIN vrmruta r ON (t.ruta_id = r.ruta_id)" +
				 "WHERE  " +
					"t.canven_id = " + canalVentaID +
					" AND t.servicio_id = "+ servicioID +
					" AND t.empresa_id = "+ empresaID +
					" AND r.localidad_idorigen = "+ origenID +
					" AND r.localidad_iddestino = "+ destinoID +
					" AND t.n_pisbus = " + piso +
					" AND t.n_zonbus = " + zona +
					" AND tr.d_fectar BETWEEN TO_DATE('"+ fechaInicio +"', 'dd/mm/yyyy') " +
					" AND TO_DATE('"+fechaFin+"', 'dd/mm/yyyy') ";
		if(PorServicio==0)
			strSql += " AND tr.c_horpar = '"+horaPartida+"' ";
		log.info(strSql);
		List<?>result =getSession().createSQLQuery(strSql).list();
		if(result.size()>0)
			CantidadTarifas = ((BigDecimal)result.get(0)).intValue();

		strSql = "SELECT COUNT(*) CANTIDAD FROM vrtitinerario i "+
					"INNER JOIN vrtdetiti di ON (i.itinerario_id = di.itinerario_id) " +
					"INNER JOIN vrmruta r ON (di.ruta_id = r.ruta_id) " +
					" AND i.servicio_id = " + servicioID +
					" AND i.empresa_id = " + empresaID +
					" AND r.localidad_idorigen = " + origenID +
					" AND r.localidad_iddestino = " + destinoID +
					" AND di.d_fecpar BETWEEN to_date('" + fechaInicio + "', 'dd/mm/yyyy')" +
					" AND to_date('" + fechaFin + "', 'dd/mm/yyyy')";
		if(PorServicio==0)
			strSql += " AND di.c_horpar='" + horaPartida + "' ";

		log.info(strSql);
		result =getSession().createSQLQuery(strSql).list();
		if(result.size()>0)
			CantidadServicios = ((BigDecimal)result.get(0)).intValue();

		String strCantidad = CantidadTarifas.toString() + "," + CantidadServicios.toString();

		return strCantidad;
	}


	@Override
	public List<TarifaRegular> buscarTarifasAReemplazar(Integer empresaID, Integer canalVentaID, Integer servicioID, Integer origenID, Integer destinoID, Integer piso, Integer zona, String fechaInicio, String fechaFin, String horaPartida, Integer PorServicio)throws Exception {
		String strSql;
		strSql = "SELECT  " +
			       "tr.tarreg_id, t.tarifa_id, t.canven_id, t.servicio_id, t.ruta_id, t.n_pisbus, " +
			       "t.n_zonbus, tr.d_fectar, tr.c_horpar, tr.itinerario_id,  tr.n_monto, t.empresa_id, tr.audfecins, tr.audusuins, tr.audipinse  " +
					"FROM vrttarifa t " +
					"INNER JOIN  vrttarreg tr ON (t.tarifa_id = tr.tarifa_id) " +
					"INNER JOIN vrmruta r ON (t.ruta_id = r.ruta_id)" +
					"WHERE  " +
					"t.canven_id = " + canalVentaID +
					" AND t.servicio_id = "+ servicioID +
					" AND t.empresa_id = "+ empresaID +
					" AND r.localidad_idorigen = "+ origenID +
					" AND r.localidad_iddestino = "+ destinoID +
					" AND t.n_pisbus = NVL("+ piso +", t.n_pisbus)" +
					" AND t.n_zonbus = " + zona +
					" AND tr.d_fectar BETWEEN TO_DATE('"+ fechaInicio +"', 'dd/mm/yyyy') " +
					" AND TO_DATE('"+fechaFin+"', 'dd/mm/yyyy') ";
//		if(PorServicio!=null && PorServicio==1)
		if(PorServicio==0)
			strSql += " AND tr.c_horpar = '"+horaPartida+"' ";

		log.info(strSql);
		List<?>result = getSession().createSQLQuery(strSql).list();
		List<TarifaRegular> lstTarifaRegular = new ArrayList<>();
		for(int i=0;i<result.size();i++){
			Object[] obj = (Object[]) result.get(i);
			TarifaRegular tarifaRegular = new TarifaRegular();
			tarifaRegular.setId(obj[0]!=null ? ((BigDecimal)obj[0]).longValue() : null);
			Tarifa tarifa = new Tarifa();
			tarifa.setId(obj[1]!=null?((BigDecimal)obj[1]).longValue():null);

			CanalVenta canalVenta = new CanalVenta();
			canalVenta.setId(obj[2]!=null ? ((BigDecimal)obj[2]).intValue() : null);
			tarifa.setCanalVenta(canalVenta);

			Servicio servicio = new Servicio();
			servicio.setId(((BigDecimal)obj[3]).intValue());
			tarifa.setServicio(servicio);

			Ruta ruta = new Ruta();
			ruta.setId(((BigDecimal)obj[4]).intValue());
			tarifa.setRuta(ruta);
			
			Empresa empresa = new Empresa();
			empresa.setId(((BigDecimal)obj[11]).intValue());
			tarifa.setEmpresa(empresa);

			tarifa.setPisoBus(obj[5]!=null ? ((BigDecimal)obj[5]).intValue() : null);
			tarifa.setZonaBus(obj[6]!=null ? ((BigDecimal)obj[6]).intValue() : null);

			tarifaRegular.setTarifa(tarifa);
			tarifaRegular.setFechaTarifa((Date)obj[7]);
			tarifaRegular.setHoraPartida(obj[8]!=null?obj[8].toString():null);
//			tarifaRegular.setItinerario(obj[9]!=null? new Itinerario(((BigDecimal)obj[9]).longValue()):null);
			tarifaRegular.setMonto(((BigDecimal)obj[10]).doubleValue());
			tarifaRegular.setFechaInsercion((Date)obj[12]);
			tarifaRegular.setUsuarioInsercion(obj[13]!=null?obj[13].toString():null);
			tarifaRegular.setIpInsercion(obj[14]!=null?obj[14].toString():null);

			lstTarifaRegular.add(tarifaRegular);
		}
		return lstTarifaRegular;
	}


	/* (non-Javadoc)
	 * @see pe.itsb.vyrbus.model.dao.TarifaRegularDAO#buscarTarifaPorServicio(java.lang.Integer, java.lang.Integer, java.lang.Integer, java.lang.String, java.lang.Integer, java.lang.Integer)
	 * con_o_sin_tarifa: Este parametro permite devolver servicios 0: Sin tarifa, solo contarifa: 1 y en el caso de ambos
	 * 					 debera tener la ausencia de la clausula NVL(Tar.CANVENID, 0)=1
	 */
	@Override
	public List<TarifaRegular> listarTarifasPorServicios(Integer empresaID, Integer canalVentaID, Integer servicioID, Integer origenID, Integer destinoID, Integer tipoItinerarioID, String fechaInicio, String fechaFin, String horaPartida, Integer con_o_sin_tarifa) throws Exception {
		// TODO Auto-generated method stub
		String strHorPar1="";
		String strHorPar2="";
		String strConTarifa=" ";
		if(horaPartida!=null) {
			strHorPar1="AND di.c_horpar='"+horaPartida+"'  ";
			strHorPar2="AND tr.c_horpar='"+horaPartida+"'  ";
		}

		//Para devolver Servicios tanto con tarifa como sin tarifa
		//strConTarifa debe ser vacia.
		if(con_o_sin_tarifa == 0)
			strConTarifa = "AND NVL(Tar.TARIFA, 0) = 0 ";
		else if(con_o_sin_tarifa == 1)
			strConTarifa = "AND NVL(Tar.TARIFA, 0) > 0 ";

		String strSql= "SELECT " +
			  "iT.idItinerario, Tar.TARIFAID, Tar.TARREGID, " +			//2
			  "Tar.CANVENID, Tar.CANAL, It.ruta_id, It.c_horpar, " +  		//6
			  "It.Origen, It.Destino, It.servicio_id, It.Servicio,  " + 	//10
			  "It.d_fecpar, It.itinerarioDetalle, Tar.PISO, Tar.ZONABUS,  " +		//14
			  "NVL(Tar.TARIFA, 0) Tarifa, It.localidad_idOrigen, It.localidad_idDestino, It.NROPISOS, It.empresa_id, It.c_nomcor " +  	//20
			"FROM  " +
			"(SELECT  " +
			  "r.ruta_id, di.c_horpar, r.c_origen as Origen, r.c_destino as Destino, s.servicio_id, s.c_denominacion as Servicio, " +
			  "di.d_fecpar, di.detiti_id as itinerarioDetalle,  " +
			  "i.itinerario_id as idItinerario, r.localidad_idOrigen, r.localidad_idDestino, s.n_numpis NROPISOS, e.empresa_id, e.c_nomcor  " +
			"FROM  " +
			  "vrtitinerario i " +
			  "INNER JOIN vrtdetiti di ON (di.itinerario_id=i.itinerario_id) " +
			  "INNER JOIN vrmruta r ON (r.ruta_id=di.ruta_id)  " +
			  "INNER JOIN vrmservicio s ON (s.servicio_id=i.servicio_id) " +
			  "INNER JOIN vrmempresa e ON (e.empresa_id=i.empresa_id) " +
			"WHERE  " +
			  "di.d_fecpar BETWEEN to_date('"+ fechaInicio +"', 'dd/MM/yyyy') AND to_date('"+ fechaFin +"', 'dd/MM/yyyy') " +
			  "AND i.c_estreg='A'  " +
			  "AND i.n_esanulado=0  " +
			  "AND di.c_estreg='A'   " +
			  "AND i.tipIti_id = NVL("+ tipoItinerarioID + ", i.tipIti_id) " +
			  "AND i.servicio_id = NVL(" + servicioID+ ", i.servicio_id)" +
			  "AND r.localidad_idOrigen = NVL("+ origenID + ", r.localidad_idorigen)" +
			  "AND r.localidad_idDestino = NVL("+ destinoID +", r.localidad_iddestino) " +
			  "AND i.empresa_id = NVL("+ empresaID +", i.empresa_id) " +
			  strHorPar1 +
			"ORDER BY di.d_fecpar, di.c_horpar) It " +
			"LEFT JOIN  " +
			"(SELECT  " +
			       "t.tarifa_id TARIFAID, " +
			       "tr.tarreg_id TARREGID,  " +
			       "cv.c_denominacion CANAL, " +
			       "t.canven_id CANVENID,  " +
			       "t.ruta_id RUTAID,  " +
			       "t.servicio_id SERVICIOID, " +
			       "tr.d_fectar FECPAR, " +
			       "tr.c_horpar HORPAR, " +
//			       "decode(t.n_pisbus, 0, '1P', 1, '2P') PISO, " +
					"t.n_pisbus PISO, " +
			       "t.n_zonbus ZONABUS, " +
			       "tr.n_monto TARIFA " +
			"FROM vrttarifa t  " +
			"INNER JOIN vrttarreg tr ON (t.tarifa_id = tr.tarifa_id) " +
			"INNER JOIN vrmcanven cv ON (t.canven_id = cv.canven_id) " +
			"INNER JOIN vrmservicio s ON (t.servicio_id = s.servicio_id) " +
			"INNER JOIN vrmruta r  ON (t.ruta_id = r.ruta_id) " +
			"WHERE  " +
			"r.localidad_idorigen=NVL("+ origenID + ",r.localidad_idorigen) AND " +
			"r.localidad_iddestino=NVL("+ destinoID +",r.localidad_iddestino) AND " +
			"tr.d_fectar BETWEEN TO_DATE('"+ fechaInicio +"','dd/mm/yyyy') AND TO_DATE('"+ fechaFin +"','dd/mm/yyyy')"  +
			strHorPar2 +
//			"AND t.n_pisbus=1
//			"and t.canven_id=2
			") Tar " +
			"ON  " +
			"( " +
			  "It.ruta_id = Tar.RUTAID AND " +
			  "It.servicio_id = Tar.SERVICIOID AND " +
			  "It.d_fecpar = Tar.FECPAR AND  " +
			  "It.c_horpar  = Tar.HORPAR " +
			") " +
//			--Eligiendo canales
			"WHERE NVL(Tar.CANVENID, 0) = NVL(" +canalVentaID+",  NVL(Tar.CANVENID, 0)) "+
//			--Sin tarifa =0; con tarifa >0 ambos sin el where
			 strConTarifa +
			"ORDER BY " +
			      " It.iditinerario,It.d_fecpar, It.c_horpar, Tar.PISO ";

		log.info(strSql);
		List<?>result =getSession().createSQLQuery(strSql).list();
		List<TarifaRegular> lstTarifaRegular = new ArrayList<>();
		for(int i=0;i<result.size();i++){
			Object[] obj = (Object[]) result.get(i);
			Itinerario itinerario = new Itinerario();
			itinerario.setId(((BigDecimal)obj[0]).longValue());
			TarifaRegular tarifaRegular = new TarifaRegular();
			tarifaRegular.setItinerario(itinerario);
			tarifaRegular.setId(obj[2]!=null ? ((BigDecimal)obj[2]).longValue() : null);
			Tarifa tarifa = new Tarifa();
			tarifa.setId(obj[1]!=null?((BigDecimal)obj[1]).longValue():null);

			CanalVenta canalVenta = new CanalVenta();
			canalVenta.setId(obj[3]!=null ? ((BigDecimal)obj[3]).intValue() : null);
			canalVenta.setDenominacion(obj[4]!=null ? obj[4].toString() : null);
			tarifa.setCanalVenta(canalVenta);

			Ruta ruta = new Ruta();
			ruta.setId(((BigDecimal)obj[5]).intValue());
			Localidad localidadOrigen = new Localidad();
			Localidad localidadDestino = new Localidad();
			localidadOrigen.setId(((BigDecimal)obj[16]).intValue());
			localidadOrigen.setDenominacion(obj[7]!=null?obj[7].toString():null);
			localidadDestino.setId(((BigDecimal)obj[17]).intValue());
			localidadDestino.setDenominacion(obj[8]!=null?obj[8].toString():null);
			ruta.setLocalidadOrigen(localidadOrigen);
			ruta.setLocalidadDestino(localidadDestino);
			tarifa.setRuta(ruta);

			Servicio servicio = new Servicio();
			servicio.setId(((BigDecimal)obj[9]).intValue());
			servicio.setDenominacion(obj[10]!=null?obj[10].toString():null);
			servicio.setNumeroPisos(((BigDecimal)obj[18]).intValue());
			tarifa.setServicio(servicio);

			tarifa.setPisoBus(obj[13]!=null ? ((BigDecimal)obj[13]).intValue() : null);
			tarifa.setZonaBus(obj[14]!=null ? ((BigDecimal)obj[14]).intValue() : null);

			Empresa empresa = new Empresa();
			empresa.setId(((BigDecimal)obj[19]).intValue());
			empresa.setNombreCorto(obj[20].toString());
			tarifa.setEmpresa(empresa);
			
			tarifaRegular.setTarifa(tarifa);
			tarifaRegular.setFechaTarifa((Date)obj[11]);
			tarifaRegular.setHoraPartida(obj[6]!=null?obj[6].toString():null);
//			tarifaRegular.setItinerario(obj[9]!=null? new Itinerario(((BigDecimal)obj[9]).longValue()):null);
			tarifaRegular.setMonto(((BigDecimal)obj[15]).doubleValue());
			lstTarifaRegular.add(tarifaRegular);
		}

		return lstTarifaRegular;
	}

	/* (non-Javadoc)
	 * @see pe.itsb.vyrbus.model.dao.TarifaRegularDAO#buscarHistorialTarifa(java.lang.Integer, java.lang.Long, java.lang.String, java.lang.String)
	 */
	@Override
	public List<HistorialTarifa> buscarHistorialTarifa(Integer canalVentaID, Long tarifaID, String fechaTarifa,
			String horaPartida) throws Exception {
		String sql="SELECT * FROM (\r\n" + 
				"SELECT \r\n" + 
				"       ra.tarreg_id, decode(ra.canven_id, 1, 'COUNTER', 'WEB ONLINE') CANAL,  s.c_denominacion SERVICIO, ra.n_pisbus+1 PISO, \r\n" + 
				"       ra.d_fectar, r.c_origen||'-'||r.c_destino RUTA, ra.c_horpar, ra.n_monto, ra.d_feccre, ra.c_usucre, ra.c_ipcrea,\r\n" + 
				"       ra.audfecins FECHAOPE, ra.audusuins USUMOD, ra.audipinse IPMOD \r\n" + 
				"FROM \r\n" + 
				"       vrttarregaud ra \r\n" + 
				"       INNER JOIN vrmservicio s on (ra.servicio_id = s.servicio_id)\r\n" + 
				"       INNER JOIN vrmruta r on (ra.ruta_id = r.ruta_id )\r\n" + 
				"WHERE \r\n" + 
				"       ra.d_fectar=TO_DATE('"+fechaTarifa+"','dd/mm/yyyy')  and ra.c_horpar='"+horaPartida+"' and ra.tarifa_id = "+tarifaID+" and ra.canven_id="+canalVentaID+" \r\n" + 
				"UNION ALL \r\n" + 
				"SELECT \r\n" + 
				"      tr.tarreg_id, decode(t.canven_id, 1, 'COUNTER', 'WEB ONLINE') CANAL,  s.c_denominacion SERVICIO, t.n_pisbus+1 PISO, \r\n" + 
				"      tr.d_fectar, r.c_origen||'-'||r.c_destino RUTA, tr.c_horpar, tr.n_monto, tr.audfecins FECCRE, \r\n" + 
				"      tr.Audusuins USUCRE, tr.audipinse IPINSE, tr.audfecins FECHAOPE, tr.audusuins USUMOD, tr.audipinse IPMOD \r\n" + 
				"FROM \r\n" + 
				"      vrttarreg tr INNER JOIN vrttarifa t on (tr.tarifa_id = t.tarifa_id)\r\n" + 
				"      INNER JOIN vrmservicio s on (t.servicio_id = s.servicio_id)\r\n" + 
				"      INNER JOIN vrmruta r on (t.ruta_id = r.ruta_id )\r\n" + 
				"WHERE \r\n" + 
				"      tr.d_fectar=TO_DATE('"+fechaTarifa+"','dd/mm/yyyy') and tr.c_horpar='"+horaPartida+"' and tr.tarifa_id = "+tarifaID+" and t.canven_id= "+canalVentaID+" ) TAR\r\n" + 
				"ORDER BY \r\n" + 
				"      TAR.tarreg_id ";
		
		log.info(sql);
		List<?>result =getSession().createSQLQuery(sql).list();
		List<HistorialTarifa> lstHistorialTarifa = new ArrayList<>();
		HistorialTarifa historialTarifa=null;
		for(int i=0;i<result.size();i++){
			
			Object[] obj = (Object[]) result.get(i);
			historialTarifa=new HistorialTarifa();
			historialTarifa.setId(((BigDecimal)obj[0]).longValue());
			historialTarifa.setCanal(obj[1]!=null?obj[1].toString():null);
			historialTarifa.setServicio(obj[2]!=null?obj[2].toString():null);
			historialTarifa.setNumeroPiso(obj[3]!=null ? ((BigDecimal)obj[3]).intValue() : null);
			historialTarifa.setFechaTarifa((Date)obj[4]);
			historialTarifa.setRuta(obj[5]!=null?obj[5].toString():null);
			historialTarifa.setHoraPartida(obj[6]!=null?obj[6].toString():null);
			historialTarifa.setImporte(((BigDecimal)obj[7]).doubleValue());
			historialTarifa.setFechaCreacion((Date)obj[8]);
			historialTarifa.setUsuarioCreacion(obj[9]!=null?obj[9].toString():null);
			historialTarifa.setIpCreacion(obj[10]!=null?obj[10].toString():null);
			historialTarifa.setFechaOperacion((Date)obj[11]);
			historialTarifa.setUsuarioModificacion(obj[12]!=null?obj[12].toString():null);
			historialTarifa.setIpModificacion(obj[13]!=null?obj[13].toString():null);
			
			lstHistorialTarifa.add(historialTarifa);
		}
		return lstHistorialTarifa;
	}

}
