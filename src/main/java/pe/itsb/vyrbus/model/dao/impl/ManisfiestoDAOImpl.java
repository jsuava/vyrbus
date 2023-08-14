package pe.itsb.vyrbus.model.dao.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.TreeMap;

import pe.itsb.vyrbus.model.bean.Agencia;
import pe.itsb.vyrbus.model.bean.Bus;
import pe.itsb.vyrbus.model.bean.CanalVenta;
import pe.itsb.vyrbus.model.bean.Cliente;
import pe.itsb.vyrbus.model.bean.DocumentoBus;
import pe.itsb.vyrbus.model.bean.EspecieValorada;
import pe.itsb.vyrbus.model.bean.FormaPago;
import pe.itsb.vyrbus.model.bean.GrupoMantenimiento;
import pe.itsb.vyrbus.model.bean.Itinerario;
import pe.itsb.vyrbus.model.bean.Localidad;
import pe.itsb.vyrbus.model.bean.Manifiesto;
import pe.itsb.vyrbus.model.bean.Pasajero;
import pe.itsb.vyrbus.model.bean.Personal;
import pe.itsb.vyrbus.model.bean.PreferenciaAlimentaria;
import pe.itsb.vyrbus.model.bean.ProgramacionServicio;
import pe.itsb.vyrbus.model.bean.Ruta;
import pe.itsb.vyrbus.model.bean.Servicio;
import pe.itsb.vyrbus.model.bean.TipoComprobante;
import pe.itsb.vyrbus.model.bean.TipoDocumento;
import pe.itsb.vyrbus.model.bean.TipoItinerario;
import pe.itsb.vyrbus.model.bean.VentaPasaje;
import pe.itsb.vyrbus.model.dao.ManifiestoDAO;
import pe.itsb.vyrbus.service.util.Constantes;

/**
 *
 * @author JABANTO
 *
 */
public class ManisfiestoDAOImpl extends GenericDAOImpl implements ManifiestoDAO {

	/*
	 * (non-Javadoc)
	 * @see com.tepsa.sisvyr.model.dao.ManifiestoDAO#consultaItinerario(java.lang.Long)
	 */
	@Override
	public Itinerario consultaItinerario(Long idItinerario, String origen, String destino)throws Exception {
		if(origen.isEmpty())
			origen=null;
		else origen="'"+origen+"'";


		String Sql="SELECT r.c_origen AS Origen, r.c_destino AS Destino, b.bus_id, b.c_codigo AS codigoBus, b.c_numplaca AS Placa, "+ //0-4
						"tdb.c_numdocbus AS TarjetHabilitacion, " + //5
						"di.d_fecpar AS FechaPartida, di.c_horpar AS HoraPartida, di.d_feclle AS FechaLlegada, di.c_horlle AS HoraLlegada, "+ //6-9
						"pp.c_apepat PAP, pp.c_apemat PAM, pp.c_nombre, "+  //10-12
						"pc.c_apepat CPAP, pc.c_apemat CPAM, pc.c_nombre CPN, " + //13-15
						"pt.c_apepat TAP, pt.c_apemat TAM, pt.c_nombre TN, "+ //16-18
						"pp.c_Licencia as licenticiaPiloto, pc.c_licencia as licenciaCopiloto, "+//19-20
						"gm.c_denominacion, "+//21-21
						"ps.proser_id, "+//22
						"ti.tipiti_id, "+//23
						"ti.c_denominacion as tipoItinerario, "+//24
						"s.c_denominacion as servicio, "+//25
						"pcx.personal_id CPXID, pcx.c_apepat CPXAP, pcx.c_apemat CPXAM, pcx.c_nombre CPXN, pcx.c_numdoc CPXND, pcx.tipdoc_id PCXTD, " + //26-31
						"pp.c_numdoc PPND, pp.tipdoc_id PPTD, pc.c_numdoc PCND, pc.tipdoc_id PCTD, pt.c_numdoc PTND, pt.tipdoc_id PTTD,pcx.c_licencia PCXLC  "+ //32-38
						",i.itinerario_id "+ //39
					"FROM vrtdetiti di "+
						"INNER JOIN vrtitinerario i ON (i.itinerario_id=di.itinerario_id) " +
						"INNER JOIN VRMSERVICIO s ON (s.servicio_id=i.servicio_id) "+
						"INNER JOIN vrmtipiti ti ON (ti.tipiti_id=i.tipiti_id) " +
						"LEFT JOIN vrmruta r ON (r.ruta_id=di.ruta_id) "+
						"LEFT JOIN (SELECT t.c_numdocbus, t.bus_id FROM vrtdocbus t WHERE t.tipdoc_id="+Constantes.ID_TIPDOC_TARJETA_CIRCULACION+" ) tdb ON (tdb.bus_id=i.bus_id) "+
						"LEFT JOIN vrtproser ps ON (ps.itinerario_id=i.itinerario_id AND ps.c_estreg='A') "+
						"LEFT JOIN vrmbus b ON (b.bus_id=ps.bus_id) "+
						"LEFT JOIN vrmpersonal pp ON (pp.personal_id=ps.personal_idpiloto) "+
						"LEFT JOIN vrmpersonal pc ON (pc.personal_id=ps.personal_idcopiloto) "+
						"LEFT JOIN vrmpersonal pt ON (pt.personal_id=ps.personal_idterramoza) "+
						"LEFT JOIN vrmpersonal pcx ON (pcx.personal_id=ps.personal_idcopilotoaux) "+
						"LEFT JOIN vrmgruman gm ON (gm.gruman_id=b.gruman_id) "+
					"WHERE di.itinerario_id="+ idItinerario +" " +
						"AND r.c_origen=nvl("+origen+ ",r.c_origen) " +
						"AND r.c_Destino='"+destino+"' ";

		List<?> result = getSession().createSQLQuery(Sql).list();
		Itinerario itinerario = new Itinerario();
		for (Object element : result) {
			Object[] obj = (Object[]) element;
			ProgramacionServicio programacionServicio = new ProgramacionServicio();

			Ruta ruta = new Ruta();
			ruta.setOrigen(obj[0].toString());
			ruta.setDestino(obj[1].toString());

//			if (obj[2] !=null){ //Validacion del bus
			if (obj[22] !=null){ //Validacion programacion
				Bus bus = new Bus();
				if (obj[5] !=null){ //Valida si el bus tiene configurado la tarjeta de circulacion
					DocumentoBus documentoBus = new DocumentoBus();
					documentoBus.setNumeroDocumento(obj[5].toString());
					bus.setDocumentoBus(documentoBus);
				}

				if(obj[21]!=null){//Valida el grupo mantenimiento
					GrupoMantenimiento grupoMantenimiento = new GrupoMantenimiento();
					grupoMantenimiento.setDenominacion(obj[21].toString());
					bus.setGrupoMantenimiento(grupoMantenimiento);
				}

				bus.setId(((BigDecimal) obj[2]).intValue());
				bus.setCodigo(obj[3].toString());
				bus.setNumeroPlaca(obj[4].toString());

				/*PILOTO*/
				Personal piloco = new Personal();
				piloco.setApellidoPaterno(obj[10]!=null?obj[10].toString():"");
				piloco.setApellidoMaterno(obj[11]!=null?obj[11].toString():"");
				piloco.setNombre(obj[12]!=null?obj[12].toString():"");
				piloco.setLicencia(obj[19]!=null?obj[19].toString(): "");
				piloco.setNroDocumento(obj[32]!=null?obj[32].toString():null);
				TipoDocumento tipoDocPiloto=new TipoDocumento(((BigDecimal)obj[33]).intValue());
				piloco.setTipoDocumento(tipoDocPiloto);
				/*COPILOTO*/
				Personal copiloto = new Personal();
				copiloto.setApellidoPaterno(obj[13]!=null?obj[13].toString():"");
				copiloto.setApellidoMaterno(obj[14]!=null?obj[14].toString(): "");
				copiloto.setNombre(obj[15]!=null?obj[15].toString():"");
				copiloto.setLicencia(obj[20]!=null?obj[20].toString():"");
				copiloto.setNroDocumento(obj[34]!=null?obj[34].toString():null);
				TipoDocumento tipoDocCopiloto=new TipoDocumento(((BigDecimal)obj[35]).intValue());
				copiloto.setTipoDocumento(tipoDocCopiloto);
				/*COPILOTO AUXILIAR*/
				if(obj[26]!=null){
					Personal copilotoAuxiliar=new Personal();
					copilotoAuxiliar.setId(((BigDecimal)obj[26]).longValue());
					copilotoAuxiliar.setApellidoPaterno(obj[27]!=null?obj[27].toString():"");
					copilotoAuxiliar.setApellidoMaterno(obj[28]!=null?obj[28].toString(): "");
					copilotoAuxiliar.setNombre(obj[29]!=null?obj[29].toString():"");
					copilotoAuxiliar.setNroDocumento(obj[30]!=null?obj[30].toString():null);
					copilotoAuxiliar.setLicencia(obj[38]!=null?obj[38].toString():"");

					TipoDocumento tipoDocCopilotoAux=new TipoDocumento(((BigDecimal)obj[31]).intValue());
					copilotoAuxiliar.setTipoDocumento(tipoDocCopilotoAux);
					programacionServicio.setCopilotoAuxiliar(copilotoAuxiliar);
				}
				/*TRIPULANTE*/
				// If agregado por MAOE 27/06/2021, por pandemia para TRANSMAR
				if(obj[18]!=null){
					Personal tripulante = new Personal();
					tripulante.setApellidoPaterno(obj[16]!=null?obj[16].toString():"");
					tripulante.setApellidoMaterno(obj[17]!=null?obj[17].toString():"");
					tripulante.setNombre(obj[18]!=null?obj[18].toString():"");
					tripulante.setNroDocumento(obj[36]!=null?obj[36].toString():null);
					TipoDocumento tipoDocTripulante=new TipoDocumento(((BigDecimal)obj[37]).intValue());
					tripulante.setTipoDocumento(tipoDocTripulante);
					programacionServicio.setTripulante(tripulante);
				}
				/*PROGRAMACION*/
				programacionServicio.setPiloto(piloco);
				programacionServicio.setCopiloto(copiloto);

				bus.setProgramacionServicio(programacionServicio);
				itinerario.setBus(bus);
			}

			itinerario.setFechaPartida(((Date)obj[6]));
			itinerario.setHoraPartida(obj[7].toString());
			itinerario.setFechaLlegada(((Date) obj[8]));
			itinerario.setHoraLlegada(obj[9].toString());
			itinerario.setId(((BigDecimal)obj[39]).longValue());

			/*TIPO ITINERARIO*/
			TipoItinerario tipoItinerario=new TipoItinerario();
			tipoItinerario.setId(((BigDecimal)obj[23]).intValue());
			tipoItinerario.setDenominacion(obj[24].toString());

			/*SERVICIO*/
			Servicio servicio=new Servicio();
			servicio.setDenominacion(obj[25].toString());

			itinerario.setServicio(servicio);
			itinerario.setTipoItinerario(tipoItinerario);
			itinerario.setRuta(ruta);
		}


		return itinerario;
	}

	/*
	 * (non-Javadoc)
	 * @see com.tepsa.sisvyr.model.dao.ManifiestoDAO#consultaPasajeros(java.lang.Long)
	 */
	@Override
	public List<VentaPasaje> consultaPasajeros(Long idItinerario,Integer idPruntoEmbarque)throws Exception {
		String sql ="SELECT  v.n_numasiento, v.c_numboleto, p.c_apepat ApePaterno, p.c_apemat ApeMat, p.c_nombre Nom, p.c_fecnac, "+ //0-5
//	Comentado por MAOE 22/07/2022 se cambio la forma de mostrar el monto de pago jalando n_imppag directamente
							"p.c_numdoc numDoc, r.c_origen origen, r.c_destino destino, decode(v.tipmov_id, 1, v.n_imppag, v.n_tarifa+v.n_recargo-v.n_descuento) imppag, "+ //6-9
//							"p.c_numdoc numDoc, r.c_origen origen, r.c_destino destino, v.n_imppag imppag, "+ //6-9
							"pa.c_denominacion preali, ap.c_denominacion PtoEmbarque , v.c_numControl, "+ //10-12
							"td.c_Denominacion TipDocto, v.venpas_id, "+ //13-14
							"ap.c_nomcor as NombreCorto, v.n_numpiso, tc.tipcom_id, tc.c_Denominacion," + //15-18
							"p.pasajero_id, fp.forpag_id, fp.c_denominacion formaPago,cv.c_nomcor canalVenta, c.c_razsoc clienteCredito, av.c_nomcor agenciaVenta, "+// 19-24
							"r.ruta_id, td.c_nomcor nomCorTipoDocumento, ad.c_denominacion agDestino, p.c_telefono "+//25-28
					"FROM vrtvenpas v "+
						"INNER JOIN (SELECT MAX(venpas_id)venpas_id, c_numcontrol " +
									"FROM vrtvenpas WHERE itinerario_id="+idItinerario+" GROUP BY c_numcontrol) max_venta " +
									"ON max_venta.venpas_id=v.venpas_id " +
						"INNER JOIN vrmpasajero p ON (p.pasajero_id=v.pasajero_id) "+
						"INNER JOIN vrmruta r ON (r.ruta_id=v.ruta_id)" +
						"INNER JOIN vrmtipcom tc ON (tc.tipcom_id=v.tipcom_id) "+
						"INNER JOIN vrmagencia ap ON (ap.agencia_id=v.agencia_idpartida) "+
						"INNER JOIN vrmagencia ad ON (ad.agencia_id=v.agencia_idllegada) "+
						"INNER JOIN vrmtipdoc td ON (td.tipdoc_id=p.tipdoc_id) "+
						"INNER JOIN vrmforpag fp ON (fp.forpag_id=v.forpag_id) "+
						"INNER JOIN vrmcanven cv ON (cv.canven_id=v.canven_id) "+
						"LEFT OUTER JOIN vrmcliente c ON (c.c_numdoc=v.c_rucclicre) "+
						"INNER JOIN vrmagencia av ON (av.agencia_id=v.agencia_id) "+
						"LEFT JOIN vrmpreali pa ON (pa.preali_id=v.preali_id) " +
					"WHERE  v.itinerario_id="+idItinerario+" And v.c_tiptra=1 And v.c_estreg='A' AND v.tipmov_id not in ("+Constantes.ID_TIPMOV_ANULACION_SISTEMA+","+ Constantes.ID_TIPMOV_DEVOLUCION+","+ Constantes.ID_TIPMOV_ANULACION+" ) " +
						"AND v.Agencia_Idpartida=NVL("+idPruntoEmbarque+",v.Agencia_Idpartida) " +
						"AND v.c_estreg='"+Constantes.VALUE_ACTIVO+"' "+
					"ORDER BY v.n_numpiso, v.n_numasiento, v.d_fecpar, v.c_horpar";
		log.info(sql);

		List<?> result = getSession().createSQLQuery(sql).list();
		List<VentaPasaje> lstResult = new ArrayList<>();
		for(int i=0; i<result.size(); i++){
			Object[] obj = (Object[]) result.get(i);

			TipoDocumento tipoDocumento = new TipoDocumento();
			tipoDocumento.setDenominacion(obj[13].toString());
			tipoDocumento.setNombreCorto(obj[26]!=null?obj[26].toString():null);

			Pasajero pasajero = new Pasajero();
			pasajero.setId(((BigDecimal)obj[19]).longValue());
			pasajero.setApellidoPaterno(obj[2].toString());
			pasajero.setApellidoMaterno(obj[3]!=null?obj[3].toString():"");
			pasajero.setTelefono(obj[28]!=null?obj[28].toString():"");
			pasajero.setNombre(obj[4].toString());
			if (obj[5] !=null)
				pasajero.setFechaNacimiento(obj[5].toString());
			if (obj[6] !=null)
				pasajero.setNumeroDocumento(obj[6].toString());
			pasajero.setTipoDocumento(tipoDocumento);

			Ruta ruta = new Ruta();
			ruta.setId(((BigDecimal)obj[25]).intValue());
			ruta.setOrigen(obj[7].toString());
			ruta.setDestino(obj[8].toString());

			PreferenciaAlimentaria preferenciaAlimentaria = new PreferenciaAlimentaria();
			preferenciaAlimentaria.setDenominacion(obj[10]!=null?obj[10].toString():"");

			Agencia agenciaPartida = new Agencia();
			agenciaPartida.setDenominacion(obj[11].toString());
			agenciaPartida.setNombreCorto(obj[15].toString());

			Agencia agenciaDestino = new Agencia();
			agenciaDestino.setDenominacion(obj[27].toString());

			TipoComprobante tipoComprobante=new TipoComprobante();
			tipoComprobante.setId(((BigDecimal)obj[17]).intValue());
			tipoComprobante.setDenominacion(obj[18].toString());

			FormaPago formaPago=new FormaPago();
			formaPago.setId(((BigDecimal)obj[20]).intValue());
			formaPago.setDenominacion(obj[21].toString());

			CanalVenta canalVenta=new CanalVenta();
			canalVenta.setNombreCorto(obj[22].toString());

			Cliente clienteCredito=null;
			if(obj[23]!=null){
				clienteCredito=new Cliente();
				clienteCredito.setRazonSocial(obj[23].toString());
			}

			Agencia agenciaVenta=new Agencia();
			agenciaVenta.setNombreCorto(obj[24].toString());

			VentaPasaje ventaPasaje = new VentaPasaje();
			ventaPasaje.setId(((BigDecimal) obj[14]).longValue());
			ventaPasaje.setNumeroAsiento(((BigDecimal) obj[0]).intValue());
			ventaPasaje.setNumeroBoleto(obj[1].toString());
			ventaPasaje.setImportePagado( ((BigDecimal) obj[9]).doubleValue());
			ventaPasaje.setNumeroControl(obj[12].toString());
			ventaPasaje.setPasajero(pasajero);
			ventaPasaje.setRuta(ruta);
			ventaPasaje.setPreferenciaAlimentaria(preferenciaAlimentaria);
			ventaPasaje.setAgenciaPartida(agenciaPartida);
			ventaPasaje.setAgenciaLlegada(agenciaDestino);
			ventaPasaje.setNumeroPiso(((BigDecimal)obj[16]).intValue());
			ventaPasaje.setTipoComprobante(tipoComprobante);
			ventaPasaje.setFormaPago(formaPago);
			ventaPasaje.setCanalVenta(canalVenta);
			ventaPasaje.setCliente(clienteCredito);
			ventaPasaje.setAgencia(agenciaVenta);

			lstResult.add(ventaPasaje);

		}
		return lstResult;
	}

	/*
	 * (non-Javadoc)
	 * @see com.tepsa.sisvyr.model.dao.ManifiestoDAO#consultaPtoControl(java.lang.Long)
	 */
	@Override
	public List<Agencia> consultaPtoControl(Long idItinerario)throws Exception {
//		String sql ="SELECT distinct(di.agencia_idpartida) idAgePart, l.localidad_id, l.c_denominacion localidad, di.d_fecpar, di.c_horpar "+ //0-4
//					"FROM vrtdetiti di "+
//					"INNER JOIN vrmagencia a ON (a.agencia_id=di.agencia_idpartida) "+
//					"INNER JOIN vrmlocalidad l ON (l.localidad_id=a.localidad_id) "+
//					"WHERE di.itinerario_id="+idItinerario+"  "+
//					"ORDER BY di.d_fecpar, di.c_horpar";
		String sql="SELECT distinct(a.agencia_id) idAgePart "+
						   ",l.localidad_id " +
						   ",l.c_denominacion localidad " +
					       ",vp.d_fecpar "+
					       ",vp.c_horpar "+
					       ",a.c_nomcor "+
					"FROM VRTVENPAS vp "+
					"INNER JOIN vrmagencia a ON (a.agencia_id=vp.agencia_idpartida) "+
					"INNER JOIN vrmlocalidad l ON (l.localidad_id=a.localidad_id) "+
					"WHERE vp.itinerario_id="+idItinerario+" AND vp.tipmov_id NOT IN ("+Constantes.ID_TIPMOV_ANULACION+","+Constantes.ID_TIPMOV_ANULACION_SISTEMA+","+Constantes.ID_TIPMOV_DEVOLUCION+") "+
					  "AND vp.c_estreg='"+Constantes.VALUE_ACTIVO+"' "+
					"ORDER BY vp.d_fecpar, vp.c_horpar";


		List<?> result = getSession().createSQLQuery(sql).list();
		List<Agencia> lstResult = new ArrayList<>();
		for(int i=0; i<result.size(); i++){
			Object[] obj = (Object[]) result.get(i);
			Agencia agencia= new Agencia();
			Localidad localidad = new Localidad();

			localidad.setId(((BigDecimal) obj[1]).intValue());
			localidad.setDenominacion(obj[2].toString());
			agencia.setId(((BigDecimal)obj[0]).intValue());
			agencia.setLocalidad(localidad);
			agencia.setNombreCorto(obj[5].toString());

			lstResult.add(agencia);
		}

		return lstResult;
	}

	@Override
	public List<VentaPasaje> consultaDetaPaxXRuta(Long idItinerario, Integer agenciaIdPartida)throws Exception {
		String sql="SELECT  lo.c_denominacion Origen, ld.c_denominacion Destino, count(v.venpas_id) CantPax,r.ruta_id, "+ //0-3
						" lo.localidad_id idlocalorigen, ld.localidad_id idlocaldestino "+// 4-5
					"FROM vrtvenpas v " +
						"INNER JOIN (SELECT MAX(venpas_id)venpas_id, c_numcontrol " +
										"FROM vrtvenpas WHERE itinerario_id="+idItinerario+" GROUP BY c_numcontrol) max_venta " +
										"ON max_venta.venpas_id=v.venpas_id " +
						"INNER JOIN vrmruta r ON (r.ruta_id=v.ruta_id) "+
						"INNER JOIN vrmlocalidad lo ON (lo.localidad_id=r.localidad_idorigen) "+
						"INNER JOIN vrmlocalidad ld ON (ld.localidad_id=r.localidad_iddestino) "+
					"WHERE v.itinerario_id="+idItinerario+ " And v.c_tiptra=1 And v.c_estreg='A' "
					+ "AND v.tipmov_id not in ("+Constantes.ID_TIPMOV_ANULACION_SISTEMA+","+ Constantes.ID_TIPMOV_DEVOLUCION+","+ Constantes.ID_TIPMOV_ANULACION+") "
					+ "AND v.Agencia_Idpartida=NVL("+agenciaIdPartida+",v.Agencia_Idpartida) " +
					"GROUP BY lo.c_denominacion, ld.c_denominacion, v.d_fecpar /*, v.c_horpar*/,r.ruta_id, lo.localidad_id,ld.localidad_id  "+
					"ORDER BY v.d_fecpar /*, v.c_horpar*/";

		List<?> result = getSession().createSQLQuery(sql).list();
		List<VentaPasaje> lstResult = new ArrayList<>();
		for(int i=0; i<result.size(); i++){
			Object[] obj = (Object[]) result.get(i);

			Localidad localidadOrigen = new Localidad();
			localidadOrigen.setId(((BigDecimal)obj[4]).intValue());
			localidadOrigen.setDenominacion(obj[0].toString());

			Localidad localidadDestino = new Localidad();
			localidadDestino.setId(((BigDecimal)obj[5]).intValue());
			localidadDestino.setDenominacion(obj[1].toString());

			Ruta ruta = new Ruta();
			ruta.setId(((BigDecimal)obj[3]).intValue());
			ruta.setLocalidadOrigen(localidadOrigen);
			ruta.setLocalidadDestino(localidadDestino);
			ruta.setOrigen(obj[0].toString());
			ruta.setDestino(obj[1].toString());

			VentaPasaje ventaPasaje = new VentaPasaje();
			ventaPasaje.setCantidadPax( ((BigDecimal) obj[2]).intValue());
			ventaPasaje.setRuta(ruta);

			lstResult.add(ventaPasaje);
		}

		return lstResult;
	}

	/*
	 * (non-Javadoc)
	 * @see com.tepsa.sisvyr.model.dao.ManifiestoDAO#consultaAutorizacionSunat(java.lang.Integer)
	 */
	@Override
	public EspecieValorada consultaAutorizacionSunat(Integer idAgencia)throws Exception {
		/*Comentado 27/11/2014 - jabanto*/
//		String sql= "SELECT ev.n_serie, ev.n_coract, ev.c_autsunat, ev.espval_id, "+ //0-3
//					"ev.n_corfin "+ //4-4
//					"FROM vrmespval ev "+
//					"WHERE ev.agencia_id="+ idAgencia+ " AND ev.c_estreg='A' AND ev.tipcom_id="+Constantes.ID_TIPCOM_MANIFIESTO_PAX+"  ";


		/*Modificado 27/11/2014 - jabanto */
		String sql= "SELECT ev.n_serie, ev.n_coract, ev.c_autsunat, ev.espval_id, "+ //0-3
				"ev.n_corfin, ev.n_corini "+ //4-5
				"FROM vrmespval ev "+
				"WHERE ev.agencia_id="+ idAgencia+ " AND ev.n_coract < ev.n_corfin AND ev.tipcom_id="+Constantes.ID_TIPCOM_MANIFIESTO_PAX+"  "+
				"ORDER BY ev.n_corini ";

		List<?> result = getSession().createSQLQuery(sql).list();
		EspecieValorada especieValorada = new EspecieValorada();

		if(result.size()>=1){
			/*Prevalece el primer registro, por si recupere mas de uno*/
			Object[] obj = (Object[]) result.get(0);

			especieValorada.setSerie(((BigDecimal) obj[0]).intValue());
			especieValorada.setCorrelativoActual(((BigDecimal) obj[1]).longValue());
			especieValorada.setAutorizacionSunat(obj[2].toString());
			especieValorada.setId(((BigDecimal) obj[3]).intValue());
			especieValorada.setCorrelativoFinal(((BigDecimal) obj[4]).longValue());
			especieValorada.setCorrelativoInicial(((BigDecimal)obj[5]).longValue());
		}

		//Calcula el porcentace utilizado de los manifiestos, solamen si recupera un registro
		if(result.size()==1){
			long inicial=especieValorada.getCorrelativoInicial();
			long actual=especieValorada.getCorrelativoActual();
			long finaL=especieValorada.getCorrelativoFinal();
			Double porcentajeUtilizado =(double) ((actual-inicial)*100)/(finaL-inicial);

			especieValorada.setPorcentajeUtilizado(porcentajeUtilizado);
		}


		return especieValorada;
	}

	/*
	 * (non-Javadoc)
	 * @see com.tepsa.sisvyr.model.dao.ManifiestoDAO#guarda(com.tepsa.sisvyr.model.bean.Manifiesto)
	 */
	@Override
	public void guarda(Manifiesto manifiesto) throws Exception {
		super.save(manifiesto);
	}

	@Override
	public Manifiesto consultaMinifiestImpreso(Long idItinerario)throws Exception {
		String sql ="SELECT m.c_codbus bus,m.c_numman numeroManifiesto, m.audfecins fechaHora,m.c_piloto piloto, "+ //0-3
						"u.c_apepat UPApePaterno, u.c_apemat UPApeMaterno, u.c_nombre UPnombre, "+ //4-6
						"m.itinerario_Id,  "+ //7-7
						"m.c_numautsunat, m.manifiesto_id "+ //8-8
					"FROM vrtmanifiesto m "+
					"INNER JOIN vrmusuario u ON (u.c_login=m.audusuins) "+
					"WHERE m.itinerario_id="+ idItinerario +" AND m.c_estreg='A'";

		List<?> result = getSession().createSQLQuery(sql).list();
		Manifiesto manifiesto = null;
		for (Object element : result) {
			Object[] obj = (Object[]) element;
			manifiesto=new Manifiesto();
			Itinerario itinerario = new Itinerario();
			String usuario="";

			itinerario.setId(((BigDecimal) obj[7]).longValue());
			manifiesto.setCodigoBus(obj[0].toString());
			manifiesto.setNumeroManifiesto(obj[1].toString());
			manifiesto.setFechaInsercion(((Date) obj[2]));
			manifiesto.setPiloto(obj[3].toString());
			usuario=obj[4].toString();
			if(obj[5]!=null)
				usuario+=" "+ obj[5].toString();
			usuario+=" "+ obj[6].toString();
			manifiesto.setAutorizacionSunat(obj[8].toString());
			manifiesto.setUsuarioInsercion(usuario);
			manifiesto.setItinerario(itinerario);
			manifiesto.setId(((BigDecimal)obj[9]).longValue());
		}


		return manifiesto;
	}

	/*
	 * (non-Javadoc)
	 * @see com.tepsa.sisvyr.model.dao.ManifiestoDAO#updateCorrelativoManifiesto(com.tepsa.sisvyr.model.bean.EspecieValorada)
	 */
	@Override
	public void updateCorrelativo(EspecieValorada especieValorada, Manifiesto manifiesto)throws Exception {
		String usuarioMod=manifiesto.getUsuarioModificacion();
		String ipMod=manifiesto.getIpModificacion();

		/*Actualiza correlativo del manifiesto*/
		EspecieValorada especieValorada2=(EspecieValorada)super.findById(EspecieValorada.class, especieValorada.getId().longValue());
		especieValorada2.setUsuarioModificacion(usuarioMod);
		especieValorada2.setIpModificacion(ipMod);
		especieValorada2.setCorrelativoActual(especieValorada2.getCorrelativoActual()+1);

		/*Implentado el 27/11/2014 - jabanto*/
		//Si los correlativos se agotan, inhabilita el registro temporalmente
		if(especieValorada2.getCorrelativoActual().longValue()==especieValorada2.getCorrelativoFinal().longValue())
			especieValorada2.setEstadoRegistro(Constantes.VALUE_INACTIVO);
		super.update(especieValorada2);


		//si los correlativos estan agotados, busca si la agencia tiene registrado otro registro con correlativos disponibles
		if(especieValorada2.getCorrelativoActual().longValue()==especieValorada2.getCorrelativoFinal().longValue()){
			EspecieValorada newEspecieValorada=consultaAutorizacionSunat(especieValorada2.getAgencia().getId());
			if(newEspecieValorada.getId()!=null){
				//busca la buena especie valorada por su id para habilitarla
				EspecieValorada habilitarEspecieValorada=(EspecieValorada)super.findById(EspecieValorada.class, newEspecieValorada.getId().longValue());
				habilitarEspecieValorada.setEstadoRegistro(Constantes.VALUE_ACTIVO);
				habilitarEspecieValorada.setUsuarioModificacion(especieValorada2.getUsuarioModificacion()!=null?especieValorada2.getUsuarioModificacion():habilitarEspecieValorada.getUsuarioModificacion());
				habilitarEspecieValorada.setIpModificacion(especieValorada2.getIpModificacion()!=null?especieValorada2.getIpModificacion():habilitarEspecieValorada.getIpModificacion());
				super.update(habilitarEspecieValorada);
			}else{
				//Si con cuenta con mas especies valoradas,rehabilita el registro con los correlativos agotados.
				especieValorada2.setEstadoRegistro(Constantes.VALUE_ACTIVO);
				super.update(especieValorada2);
			}
		}

//		String sql=	"UPDATE vrmespval SET  n_coract="+(especieValorada.getCorrelativoActual()+1) +
//					" WHERE espval_ID="+especieValorada.getId();
//		getSession().createSQLQuery(sql).executeUpdate();



		//comentado el 09/11/2013 en adelante se guardara un detalle en la tabla VRTDetalleManifiesto (vrtdetman)
//		/*Actualiza las ventas del itinerario con el idManifiesto*/
//		sql=" UPDATE vrtvenpas SET Manifiesto_ID="+manifiesto.getId()+" "+
//			" WHERE venpas_id IN (SELECT MAX(v.venpas_id) FROM VRTVENPAS v WHERE v.itinerario_id="+manifiesto.getItinerario().getId()+" GROUP BY v.venpas_idoriginal) "+
//			" AND itinerario_id="+manifiesto.getItinerario().getId()+" AND tipmov_id NOT IN ("+Constantes.ID_TIPMOV_ANULACION+","+Constantes.ID_TIPMOV_DEVOLUCION+")" +
//			" AND c_tiptra="+Constantes.TIPO_OPERACION_VENTA+" And c_estreg='A'";


//		sql="UPDATE vrtvenpas SET Manifiesto_ID="+manifiesto.getId()+
//			" WHERE itinerario_id="+manifiesto.getItinerario().getId()+" And c_tiptra="+Constantes.TIPO_OPERACION_VENTA+" And c_estreg='A' "; // v.c_tiptra: 1=Venta
//		getSession().createSQLQuery(sql).executeUpdate();

	}


	/*
	 * (non-Javadoc)
	 * @see com.tepsa.sisvyr.model.dao.ManifiestoDAO#validaCorrelativoManifiesto(java.lang.Integer)
	 */
	@Override
	public Double validaCorrelativoManifiesto(Integer idAgencia)throws Exception {

		return null;
	}

	/*
	 * (non-Javadoc)
	 * @see com.tepsa.sisvyr.model.dao.ManifiestoDAO#buscarManifiesto(java.lang.String, java.lang.String, java.lang.Integer, java.lang.Integer, java.lang.Integer, java.lang.Integer)
	 */
	@Override
	public List<Manifiesto> buscarManifiesto(String fechaInicial, String fechaFinal,Integer idOrigen, Integer idDestino,Integer idBus,Integer idAgenciaEmision) {

		String sql ="SELECT  m.manifiesto_id, m.c_numman, m.c_codbus, m.itinerario_id, m.c_piloto as piloto, "+ //0-4
							"m.c_copiloto as copiloto, i.d_fecpar as fechaPartida, m.c_estreg as estado, "+ //5-7
							"i.c_horpar as horaPartida,lo.c_denominacion as origen, ld.c_denominacion as destino, "+ //8-10
							"i.ruta_idmayor, "+ //11-11
							"m.audfecins, s.c_denominacion servicio,m.c_copilotoAux, m.c_tripulante,m.audfecmod "+//12-16
					"FROM vrtitinerario i "+
						"INNER JOIN vrmservicio s ON (s.servicio_id=i.servicio_id) "+
						"INNER JOIN vrtmanifiesto m ON (m.itinerario_id=i.itinerario_id) "+
						"INNER JOIN vrmruta r ON (r.ruta_id=i.ruta_idmayor) "+
						"INNER JOIN vrmlocalidad lo ON (lo.localidad_id=r.localidad_idorigen) "+
						"INNER JOIN vrmlocalidad ld ON (ld.localidad_id=r.localidad_iddestino) "+
					"WHERE i.d_fecpar > = to_date('"+fechaInicial+"','dd/mm/yyyy') AND i.d_fecpar <= to_date('"+fechaFinal+"','dd/mm/yyyy') "
						+ "AND lo.Localidad_Id=NVL("+idOrigen+",lo.Localidad_Id) "
						+ "AND ld.Localidad_Id=NVL("+idDestino+",ld.Localidad_Id) "
						+ "AND i.bus_id=NVL("+idBus+",i.bus_id) "
						+ "AND m.agencia_id=NVL("+idAgenciaEmision+",m.agencia_id) "
						+ "AND m.c_estreg in ('A','I') "
						+ "ORDER BY i.d_fecpar,i.c_horpar ";

//					whereOrigen+" "+whereDestino+" AND m.c_estreg in ('A','I') ORDER BY i.d_fecpar,i.c_horpar ";

		List<?> result = getSession().createSQLQuery(sql).list();
		List<Manifiesto> lstResult = new ArrayList<>();

		for(int i=0; i<result.size(); i++){
			Object[] obj = (Object[]) result.get(i);
			Manifiesto manifiesto = new Manifiesto();

			Localidad localidadOrigen = new Localidad();
			localidadOrigen.setDenominacion(obj[9].toString());

			Localidad localidadDestino = new Localidad();
			localidadDestino.setDenominacion(obj[10].toString());

			Ruta ruta=new Ruta();
			ruta.setId(((BigDecimal) obj[11]).intValue());
			ruta.setLocalidadOrigen(localidadOrigen);
			ruta.setLocalidadDestino(localidadDestino);

			Servicio servicio=new Servicio();
			servicio.setDenominacion(obj[13].toString());

			Itinerario itinerario = new Itinerario();
			itinerario.setId(((BigDecimal) obj[3]).longValue());
			itinerario.setFechaPartida((Date)obj[6]);
			itinerario.setHoraPartida(obj[8].toString());
			itinerario.setRuta(ruta);
			itinerario.setServicio(servicio);

			manifiesto.setId(((BigDecimal) obj[0]).longValue());
			manifiesto.setNumeroManifiesto(obj[1].toString());
			manifiesto.setCodigoBus(obj[2].toString());
			manifiesto.setPiloto(obj[4].toString());
			manifiesto.setCopiloto(obj[5].toString());
			manifiesto.setEstadoRegistro(obj[7].toString());
			manifiesto.setItinerario(itinerario);
			manifiesto.setFechaInsercion(obj[12]!=null?(Date)obj[12]:null);
			manifiesto.setCopilotoAuxiliar(obj[14]!=null?obj[14].toString():null);
			manifiesto.setTripulante(obj[15]!=null?obj[15].toString():null);
			manifiesto.setFechaModificacion(obj[16]!=null?(Date)obj[16]:null);

			lstResult.add(manifiesto);
		}


		return lstResult;
	}

	/*
	 * (non-Javadoc)
	 * @see com.tepsa.sisvyr.model.dao.ManifiestoDAO#inactivar(java.lang.Long)
	 */
	@Override
	public void inactivar(Long id) {
		super.inactivate(Manifiesto.class, id);
		/*Actualiza las ventas del itinerario con el idManifiesto*/
		String sql="UPDATE vrtvenpas SET Manifiesto_ID=null"+
			" WHERE Manifiesto_ID="+id;
		getSession().createSQLQuery(sql).executeUpdate();

	}

	/*
	 * (non-Javadoc)
	 * @see com.tepsa.sisvyr.model.dao.ManifiestoDAO#buscarPorX(java.util.TreeMap, java.util.List)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public ArrayList<Manifiesto> buscarPorX(TreeMap<String, Object> criteriosBusqueda,List<String> criteriosOrdenar) {
		return (ArrayList<Manifiesto>) super.findByX(Manifiesto.class, criteriosBusqueda, criteriosOrdenar);
	}

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.model.dao.ManifiestoDAO#buscarPorId(java.lang.Long)
	 */
	@Override
	public Manifiesto buscarPorId(Long id) throws Exception {
		// TODO Auto-generated method stub
		return (Manifiesto) super.findById(Manifiesto.class, id);
	}

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.model.dao.ManifiestoDAO#actualizar(com.tepsa.sisvyr.model.bean.Manifiesto)
	 */
	@Override
	public void actualizar(Manifiesto manifiesto) throws Exception {
		// TODO Auto-generated method stub
		super.save(manifiesto);
	}

	/* (non-Javadoc)
	 * @see pe.itsb.vyrbus.model.dao.ManifiestoDAO#buscarDevolucionIsc(java.lang.String, java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	public List<Manifiesto> buscarDevolucionIsc(String fechaInicial, String fechaFinal, String per4949, String periodo) {

		String sql ="SELECT VTP.IDITINERARIO "+
			       ",MAN.NROBUS "+
			       ",'" + Constantes.ruc + "' AS RUC "+
			       ",'" + per4949 + "' AS PER4949 "+
			       ",'" + periodo + "' AS PERIODO "+
			       ",SUBSTR(MAN.NroManifiesto,0, 3) CSERIEF "+
			       ",SUBSTR(MAN.NroManifiesto,5,9) CNUMERO "+
			       ",MAN.MARCA "+
			       ",MAN.NROPLACA "+
			       ",MAN.TARJETACIRCULACION NO_CHAB "+
			       ",VTP.FECHAPARTIDA FEC_SAL "+
			       ",MAN.PP_DP "+
			       ",MAN.PP_DS "+
			       ",MAN.LL_DP "+
			       ",MAN.LL_DS "+
			       ",VTP.IMPORTE "+
			       //-->Datos del manifiesto e itinerario.
			"FROM(  "+
			"    SELECT i.itinerario_id,m.c_numman NroManifiesto,b.c_codigo AS NroBus,gm.c_denominacion MARCA "+
			"           ,b.c_numplaca AS NroPlaca,db.c_numdocbus AS TarjetaCirculacion "+
			"           ,DptO.c_Nombreubigeo AS PP_DP,DstO.c_nombreubigeo AS PP_DS "+
			"           ,Dptd.c_Nombreubigeo AS LL_DP,DstD.c_nombreubigeo AS LL_DS "+
			"    FROM VRTMANIFIESTO m "+
			"         INNER JOIN VRMBUS b ON (b.bus_id=m.bus_id) "+
			"		  INNER JOIN VRMGRUMAN gm on (b.gruman_id = gm.gruman_id) "+
			"         INNER JOIN VRTDOCBUS db ON (db.bus_id=b.bus_id AND db.tipdoc_id=3 AND db.c_Estreg='A')  "+ //-->3=TARJETA DE CIRCULACION
			"         INNER JOIN VRTITINERARIO i ON (i.itinerario_id=m.itinerario_id) "+
			"         INNER JOIN VRMAGENCIA ao ON (ao.agencia_id=i.agencia_idpartida) "+
			"         INNER JOIN VRMUBIGEO DstO ON (DstO.ubigeo_id=ao.ubigeo_id) "+
			"         INNER JOIN VRMAGENCIA ad ON (ad.agencia_id=i.agencia_idllegada) "+
			"         INNER JOIN VRMUBIGEO DstD ON (DstD.ubigeo_id=ad.ubigeo_id) "+
			"         INNER JOIN VRMUBIGEO DptO ON (DptO.c_Coddpto=DstO.c_Coddpto AND DptO.c_Codprov='00' AND DptO.c_Coddist='00') "+
			"         INNER JOIN VRMUBIGEO Dptd ON (Dptd.c_Coddpto=DstD.c_Coddpto AND Dptd.c_Codprov='00' AND Dptd.c_Coddist='00') "+
			"    WHERE i.d_Fecpar BETWEEN to_date('" + fechaInicial + "', 'dd/MM/yyyy') AND to_date('" + fechaFinal + "', 'dd/MM/yyyy') "+
			"         AND i.n_esanulado=0 AND m.c_estreg='A') man, "+
			     //---->Ventas
			"    (SELECT venpas_max.idItinerario,venpas_max.FechaPartida "+
			"            ,SUM(vp.n_tarifa+vp.n_recargo-vp.n_descuento)Importe "+
			"            ,venpas_max.origen,venpas_max.destino "+
			"      FROM VRTVENPAS vp "+
			"           INNER JOIN (SELECT MAX(v.venpas_id) venpas_id,i.itinerario_id As idItinerario, i.d_fecpar AS fechaPArtida "+
			"                              ,r.c_origen AS origen, r.c_destino AS destino "+
			"                       FROM VRTVENPAS v "+
			"                            INNER JOIN VRTITINERARIO i ON (i.itinerario_id=v.itinerario_id) "+
			"                            INNER JOIN VRMRUTA r ON (r.ruta_id=i.ruta_idmayor) "+
			"                       WHERE i.d_fecpar BETWEEN to_date('" + fechaInicial + "', 'dd/MM/yyyy') AND to_date('" + fechaFinal + "', 'dd/MM/yyyy') "+
			"                            AND i.n_esanulado=0 "+
			"                       GROUP BY v.c_numcontrol,i.itinerario_id,i.d_fecpar,r.c_origen,r.c_destino "+
			"                       )venpas_max "+
			"                   ON (venpas_max.venpas_id=vp.venpas_id) "+
			"      WHERE vp.c_Tiptra='1' "+
			"           AND vp.c_estreg='A' "+
			"           AND vp.tipmov_id NOT IN (5,6,13) "+
			"      GROUP BY venpas_max.idItinerario,venpas_max.FechaPartida "+
			"              ,venpas_max.origen,venpas_max.destino)vtp "+
			"WHERE man.itinerario_id=vtp.idItinerario "+
			"ORDER BY VTP.FECHAPARTIDA, SUBSTR(MAN.NroManifiesto,0, 3) ,SUBSTR(MAN.NroManifiesto,5,9) ";

		//		whereOrigen+" "+whereDestino+" AND m.c_estreg in ('A','I') ORDER BY i.d_fecpar,i.c_horpar ";

		List<?> result = getSession().createSQLQuery(sql).list();
		List<Manifiesto> lstResult = new ArrayList<>();

		for(int i=0; i<result.size(); i++){
			Object[] obj = (Object[]) result.get(i);
			Manifiesto manifiesto = new Manifiesto();

			Itinerario itinerario = new Itinerario();
			itinerario.setId(((BigDecimal) obj[0]).longValue());
			itinerario.setFechaPartida((Date)obj[10]);

			manifiesto.setItinerario(itinerario);
			manifiesto.setCodigoBus(obj[1].toString());
			manifiesto.setRuc(obj[2].toString());
			manifiesto.setPer4949(obj[3].toString());
			manifiesto.setPeriodo(obj[4].toString());

			manifiesto.setNumeroManifiesto(obj[5].toString()+"-"+obj[6].toString());

			GrupoMantenimiento marca = new GrupoMantenimiento();
			marca.setDenominacion(obj[7].toString());
			Bus bus = new Bus();
			bus.setGrupoMantenimiento(marca);
			manifiesto.setBus(bus);
			manifiesto.setPlaca(obj[8].toString());
			manifiesto.setCertificadoHabilitacion(obj[9].toString());
			manifiesto.setPuntoPartidaDepartamento(obj[11].toString());
			manifiesto.setPuntoPartidaDistrito(obj[12].toString());
			manifiesto.setPuntoLlegadaDepartamento(obj[13].toString());
			manifiesto.setPuntoLlegadaDistrito(obj[14].toString());
			manifiesto.setImporte(((BigDecimal) obj[15]).doubleValue());

			lstResult.add(manifiesto);
		}


		return lstResult;
	}


}
