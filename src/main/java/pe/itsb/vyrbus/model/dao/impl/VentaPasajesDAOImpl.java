/**
 * Proyecto		: SISVYR
 * Sistema		: Sistema de Ventas y Reservas
 * Descripci�n	: Implementaci�n de metodos relacionados con la Venta de Pasajes.
 * Autor		: Jos� Avalos
 * Fecha		: 09/07/2012
 */
package pe.itsb.vyrbus.model.dao.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.TreeMap;

import org.hibernate.SQLQuery;


import pe.itsb.vyrbus.model.bean.Agencia;
import pe.itsb.vyrbus.model.bean.Bus;
import pe.itsb.vyrbus.model.bean.CanalVenta;
import pe.itsb.vyrbus.model.bean.CentroCosto;
import pe.itsb.vyrbus.model.bean.Cliente;
import pe.itsb.vyrbus.model.bean.Concesionario;
import pe.itsb.vyrbus.model.bean.Empresa;
import pe.itsb.vyrbus.model.bean.EntidadEncomiendaPasajes;
import pe.itsb.vyrbus.model.bean.EstadoCivil;
import pe.itsb.vyrbus.model.bean.FormaPago;
import pe.itsb.vyrbus.model.bean.Itinerario;
import pe.itsb.vyrbus.model.bean.Liquidacion;
import pe.itsb.vyrbus.model.bean.Localidad;
import pe.itsb.vyrbus.model.bean.Manifiesto;
import pe.itsb.vyrbus.model.bean.Nacionalidad;
import pe.itsb.vyrbus.model.bean.OperadorTarjetaCredito;
import pe.itsb.vyrbus.model.bean.Pasajero;
import pe.itsb.vyrbus.model.bean.PreferenciaAlimentaria;
import pe.itsb.vyrbus.model.bean.Promocion;
import pe.itsb.vyrbus.model.bean.Ruta;
import pe.itsb.vyrbus.model.bean.Servicio;
import pe.itsb.vyrbus.model.bean.Sexo;
import pe.itsb.vyrbus.model.bean.TarifarioByAsientoByAvanceVentas;
import pe.itsb.vyrbus.model.bean.TarifarioByAsientoSubByAvanceVentas;
import pe.itsb.vyrbus.model.bean.TarjetaCredito;
import pe.itsb.vyrbus.model.bean.TipoAgencia;
import pe.itsb.vyrbus.model.bean.TipoCobranza;
import pe.itsb.vyrbus.model.bean.TipoComprobante;
import pe.itsb.vyrbus.model.bean.TipoDocumento;
import pe.itsb.vyrbus.model.bean.TipoFormaPago;
import pe.itsb.vyrbus.model.bean.TipoMoneda;
import pe.itsb.vyrbus.model.bean.TipoMovimiento;
import pe.itsb.vyrbus.model.bean.Ubigeo;
import pe.itsb.vyrbus.model.bean.Usuario;
import pe.itsb.vyrbus.model.bean.VentaPasaje;
import pe.itsb.vyrbus.model.bean.report.RptVentaUsuario;
import pe.itsb.vyrbus.model.dao.VentaPasajesDAO;
import pe.itsb.vyrbus.service.locator.ServiceLocator;
import pe.itsb.vyrbus.service.mappers.ResumenAnulacionPostergacion;
import pe.itsb.vyrbus.service.mappers.ResumenVentas;
import pe.itsb.vyrbus.service.mappers.SecuenciaTramo;
import pe.itsb.vyrbus.service.mappers.VentasPiloto;
import pe.itsb.vyrbus.service.util.Constantes;
import pe.itsb.vyrbus.service.util.MyTime;
import pe.itsb.vyrbus.service.util.Util;

/**
 *
 * @author Jos� Avalos
 * @since JDK1.6
 */
@SuppressWarnings("unchecked")
public class VentaPasajesDAOImpl extends GenericDAOImpl implements VentaPasajesDAO {

	/*
	 * (non-Javadoc)
	 * @see com.tepsa.sisvyr.model.dao.VentaPasajesDAO#buscarVentasForMapaBus(java.lang.Long)
	 */
	@Override
	public List<VentaPasaje> buscarVentasForMapaBus(Long idItinerario)throws Exception{
		String sql = "SELECT vp.venpas_id, vp.venpas_idref, vp.ruta_id, r.localidad_idorigen, r.c_origen, r.localidad_iddestino, r.c_destino, " +
				"			 p.pasajero_id, p.c_apepat, p.c_apemat, p.c_nombre, s.sexo_id, s.c_denominacion, vp.c_numboleto, vp.n_numasiento, " +
				"			 vp.c_tiptra, i.ruta_idmayor, rm.localidad_idorigen, rm.localidad_iddestino, vp.n_numpiso, i.c_sectra, i.itinerario_id, " +
				"			 p.c_numdoc, p.c_fecnac, vp.c_numcontrol, vp.canven_id, vp.d_fecpar, vp.c_horpar, " +
				"			 ap.c_nomcor as nombreCorto, p.tipdoc_id tipoDocPax, vp.forpag_id, vp.tipforpag_id, vp.c_rucclicre, vp.n_imppag, " +
				"			 vp.tipmov_id, av.c_denominacion AGVENTA, u.c_login USUARIO, al.c_denominacion AGLLEGADA, vp.audfecins FECVENTA, p.c_telefono, "+ 
				"            ap.agencia_id agencia_idpartida, al.agencia_id agencia_idllegada, tc.tipcom_id, tc.c_denominacion tipoComprobante,  "+ //43
				"            vp.c_tiptra, cl.cliente_id, cl.c_numdoc ruc_cliente, cl.c_razsoc, cl.c_direccion, vp.usuario_id, vp.empresa_id, vp.c_observaciones "+ //51
				"FROM vrtvenpas vp " +
				"	  INNER JOIN (SELECT MAX(venpas_id)venpas_id, c_numcontrol " +
				"				  FROM vrtvenpas WHERE itinerario_id="+idItinerario+" GROUP BY c_numcontrol) max_venta " +
				"				  ON max_venta.venpas_id=vp.venpas_id " +
				"	  INNER JOIN vrmruta r ON r.ruta_id=vp.ruta_id " +
				"	  INNER JOIN vrmpasajero p ON p.pasajero_id=vp.pasajero_id " +
				"	  INNER JOIN vrmsexo s ON s.sexo_id=p.sexo_id " +
				"	  INNER JOIN vrtitinerario i ON i.itinerario_id=vp.itinerario_id " +
				"	  INNER JOIN vrmruta rm ON rm.ruta_id=i.ruta_idmayor " +
				"	  LEFT JOIN vrmagencia ap ON ap.agencia_id=vp.Agencia_Idpartida " +
				"     INNER JOIN vrmagencia al ON (vp.agencia_idllegada = al.agencia_id) "+
			    "     INNER JOIN vrmagencia av ON (vp.agencia_id = av.agencia_id) "+
			    "     INNER JOIN vrmusuario u ON (vp.usuario_id = u.usuario_id) "+
			    "     INNER JOIN vrmtipcom tc ON (tc.tipcom_id=vp.tipcom_id)   "+
			    "     LEFT JOIN vrmcliente cl ON (cl.cliente_id=vp.cliente_id)  "+
//				"INNER JOIN vrmtipmov tm ON tm.tipmov_id=vp.tipmov_id " +
				"WHERE vp.itinerario_id="+idItinerario+" "+
			    "AND vp.tipmov_id not in("+Constantes.ID_TIPMOV_ANULACION_SISTEMA+","+Constantes.ID_TIPMOV_DEVOLUCION+","+Constantes.ID_TIPMOV_ANULACION+") "+
				"AND vp.c_tiptra NOT IN('"+Constantes.TIPO_OPERACION_PERDIDA_SERVICIO+"') " +
				"AND vp.c_estreg='"+Constantes.VALUE_ACTIVO+"' " +
			    "AND vp.n_numasiento IS NOT NULL "+
				"ORDER BY vp.n_numasiento ";

		log.info(sql);

		List<?> result = getSession().createSQLQuery(sql).list();
		List<VentaPasaje> lstResult = new ArrayList<>();
		for(int i=0; i<result.size(); i++){
			Object[] obj = (Object[])result.get(i);
			VentaPasaje ventaPasaje = new VentaPasaje();
			ventaPasaje.setId(((BigDecimal)obj[0]).longValue());
			Ruta ruta = new Ruta();
			ruta.setId(((BigDecimal)obj[2]).intValue());
			ruta.setLocalidadOrigen(new Localidad(((BigDecimal)obj[3]).intValue()));
			ruta.setOrigen(obj[4].toString());
			ruta.setLocalidadDestino(new Localidad(((BigDecimal)obj[5]).intValue()));
			ruta.setDestino(obj[6].toString());
			ventaPasaje.setRuta(ruta);
			Pasajero pasajero = new Pasajero();
			pasajero.setId(((BigDecimal)obj[7]).longValue());
			pasajero.setApellidoPaterno(obj[8]==null?"":obj[8].toString());
			pasajero.setApellidoMaterno(obj[9]==null?"":obj[9].toString());
			pasajero.setTipoDocumento(new TipoDocumento(((BigDecimal)obj[29]).intValue()));
			pasajero.setNumeroDocumento(obj[22]==null?"":obj[22].toString());
			pasajero.setFechaNacimiento(obj[23]==null?"":obj[23].toString());
			pasajero.setNombre(obj[10].toString());
			pasajero.setTelefono(obj[39]==null?"":obj[39].toString());
			Sexo sexo = new Sexo();
			sexo.setId(((BigDecimal)obj[11]).intValue());
			sexo.setDenominacion(obj[12].toString());
			pasajero.setSexo(sexo);
			ventaPasaje.setPasajero(pasajero);
			ventaPasaje.setNumeroBoleto(obj[13]==null?null:obj[13].toString());
			ventaPasaje.setNumeroAsiento(((BigDecimal)obj[14]).intValue());
			ventaPasaje.setTipoTransaccion(obj[15].toString());
			Itinerario itinerario = new Itinerario();
			Ruta rutaMayor = new Ruta();
			rutaMayor.setId(((BigDecimal)obj[16]).intValue());
			itinerario.setRuta(rutaMayor);
			ventaPasaje.setItinerario(itinerario);
			ventaPasaje.setNumeroPiso(((BigDecimal)obj[19]).intValue());
			ventaPasaje.setNumeroControl(obj[24]==null?"":obj[24].toString());
			itinerario.setSecuenciaTramo(obj[20].toString());
			itinerario.setListSecuenciaTramo(obtenerSecuencia(itinerario.getSecuenciaTramo()));
			itinerario.setId(obj[21]!=null?((BigDecimal)obj[21]).longValue():null);
			CanalVenta canalVenta = new CanalVenta();
			canalVenta.setId(((BigDecimal)obj[25]).intValue());
			ventaPasaje.setCanalVenta(canalVenta);
			ventaPasaje.setFechaPartida((Date)obj[26]);
			ventaPasaje.setHoraPartida(obj[27].toString());

			Agencia agenciaPartida=new Agencia();
			agenciaPartida.setId(obj[40]!=null?((BigDecimal)obj[40]).intValue():null);
			agenciaPartida.setNombreCorto(obj[28]!=null?obj[28].toString():"");
			ventaPasaje.setAgenciaPartida(agenciaPartida);

			FormaPago formaPago = new FormaPago();
			formaPago.setId(obj[30]==null?null:((BigDecimal)obj[30]).intValue());
			ventaPasaje.setFormaPago(formaPago);

			TipoFormaPago tipoFormaPago = new  TipoFormaPago();
			tipoFormaPago.setId(obj[31]==null?null:((BigDecimal)obj[31]).intValue());
			ventaPasaje.setTipoFormaPago(tipoFormaPago);

			ventaPasaje.setRucClienteCredito(obj[32]==null?null:obj[32].toString());
			ventaPasaje.setImportePagado(obj[33]==null?null:((BigDecimal)obj[33]).doubleValue());

			Agencia agenciaLlegada = new Agencia();
			agenciaLlegada.setId(obj[41]!=null?((BigDecimal)obj[41]).intValue():null);
			agenciaLlegada.setDenominacion(obj[37]==null?null:obj[37].toString());
			ventaPasaje.setAgenciaLlegada(agenciaLlegada);

			Agencia agenciaVenta = new Agencia();
			agenciaVenta.setDenominacion(obj[35]==null?null:obj[35].toString());
			ventaPasaje.setAgencia(agenciaVenta);

			Usuario usuarioVenta = new Usuario();
			usuarioVenta.setLogin(obj[36]==null?null:obj[36].toString());
			usuarioVenta.setId(((BigDecimal)obj[49]).intValue());
			ventaPasaje.setUsuario(usuarioVenta);
			ventaPasaje.setFechaInsercion(obj[38]==null?null:(Date)obj[38]);
			ventaPasaje.setTipoComprobante(new TipoComprobante(((BigDecimal)obj[42]).intValue(), obj[43].toString()));
			ventaPasaje.setTipoTransaccion(obj[44].toString());

			if(obj[45]!=null) {
				Cliente cliente = new Cliente();
				cliente.setId(((BigDecimal)obj[45]).longValue());
				cliente.setNumeroDocumento(obj[46].toString());
				cliente.setRazonSocial(obj[47].toString());
				cliente.setDireccion(obj[48]!=null?obj[48].toString():null);
				ventaPasaje.setCliente(cliente);
			}
			
			ventaPasaje.setEmpresa(new Empresa(((BigDecimal)obj[50]).intValue()));
			ventaPasaje.setObservaciones(obj[51] != null? obj[51].toString() : null);

//			TipoMovimiento tipoMovimiento = new TipoMovimiento();
//			tipoMovimiento.setId(((BigDecimal)obj[34]).intValue());

			lstResult.add(ventaPasaje);
		}
		return lstResult;
	}

	/*
	 * (non-Javadoc)
	 * @see com.tepsa.sisvyr.model.dao.VentaPasajesDAO#buscarVentasByIdVenta(java.lang.Long)
	 */
	@Override
	public VentaPasaje buscarVentasByIdVenta(Long idVenta)throws Exception{
		String sql = "SELECT vp.venpas_id, vp.venpas_idref, vp.ruta_id, r.localidad_idorigen, r.c_origen, r.localidad_iddestino, r.c_destino, " +
				"p.pasajero_id, p.c_apepat, p.c_apemat, p.c_nombre, s.sexo_id, s.c_denominacion, vp.c_numboleto, vp.n_numasiento, vp.c_tiptra, " +
				"i.ruta_idmayor, rm.localidad_idorigen, rm.localidad_iddestino, vp.n_numpiso, i.c_sectra, i.itinerario_id, vp.d_fecpar, vp.c_horpar, " +
				"so.servicio_id, so.n_numpis, so.n_numasipis1, so.n_numasipis2 " +
				"FROM vrtvenpas vp " +
				"INNER JOIN vrmruta r ON r.ruta_id=vp.ruta_id " +
				"INNER JOIN vrmpasajero p ON p.pasajero_id=vp.pasajero_id " +
				"INNER JOIN vrmsexo s ON s.sexo_id=p.sexo_id " +
				"INNER JOIN vrtitinerario i ON i.itinerario_id=vp.itinerario_id " +
				"INNER JOIN vrmruta rm ON rm.ruta_id=i.ruta_idmayor " +
				"INNER JOIN vrmservicio so ON so.servicio_id=vp.servicio_id " +
				"WHERE vp.venpas_id="+idVenta+" ORDER BY vp.n_numasiento ";

		log.info(sql);
		List<?> result = getSession().createSQLQuery(sql).list();
		VentaPasaje ventaPasaje = new VentaPasaje();
		for (Object element : result) {
			Object[] obj = (Object[])element;
			ventaPasaje.setId(((BigDecimal)obj[0]).longValue());
			Ruta ruta = new Ruta();
			ruta.setId(((BigDecimal)obj[2]).intValue());
			ruta.setLocalidadOrigen(new Localidad(((BigDecimal)obj[3]).intValue()));
			ruta.setOrigen(obj[4].toString());
			ruta.setLocalidadDestino(new Localidad(((BigDecimal)obj[5]).intValue()));
			ruta.setDestino(obj[6].toString());
			ventaPasaje.setRuta(ruta);
			Pasajero pasajero = new Pasajero();
			pasajero.setId(((BigDecimal)obj[7]).longValue());
			pasajero.setApellidoPaterno(obj[8]==null?"":obj[8].toString());
			pasajero.setApellidoMaterno(obj[9]==null?"":obj[9].toString());
			pasajero.setNombre(obj[10].toString());
			Sexo sexo = new Sexo();
			sexo.setId(((BigDecimal)obj[11]).intValue());
			sexo.setDenominacion(obj[12].toString());
			pasajero.setSexo(sexo);
			ventaPasaje.setPasajero(pasajero);
			ventaPasaje.setNumeroBoleto(obj[13]==null?null:obj[13].toString());
			ventaPasaje.setNumeroAsiento(obj[14]==null?null:((BigDecimal)obj[14]).intValue());
			ventaPasaje.setTipoTransaccion(obj[15].toString());
			Itinerario itinerario = new Itinerario();
			Ruta rutaMayor = new Ruta();
			rutaMayor.setId(((BigDecimal)obj[16]).intValue());
			itinerario.setRuta(rutaMayor);
			ventaPasaje.setItinerario(itinerario);
			ventaPasaje.setNumeroPiso(((BigDecimal)obj[19]).intValue());
			itinerario.setSecuenciaTramo(obj[20].toString());
			itinerario.setListSecuenciaTramo(obtenerSecuencia(itinerario.getSecuenciaTramo()));
			itinerario.setId(obj[21]!=null?((BigDecimal)obj[21]).longValue():null);
			ventaPasaje.setFechaPartida(obj[22]==null?null:(Date)obj[22]);
			ventaPasaje.setHoraPartida(obj[23]==null?null:obj[23].toString());
			Servicio servicio = new Servicio();
			servicio.setId(((BigDecimal)obj[24]).intValue());
			servicio.setNumeroPisos(((BigDecimal)obj[25]).intValue());
			servicio.setNumeroAsientosPiso1(((BigDecimal)obj[26]).intValue());
			servicio.setNumeroAsientosPiso2(servicio.getNumeroPisos()==2?((BigDecimal)obj[27]).intValue():null);
			ventaPasaje.setServicio(servicio);
			if(ventaPasaje.getNumeroAsiento()!=null)
				ventaPasaje.setKey();
		}
		return ventaPasaje;
	}

	private List<SecuenciaTramo> obtenerSecuencia(String secuencia){
		String[] sArray = secuencia.split(";");
		List<SecuenciaTramo> lstResult = new ArrayList<>();
		for(String obj : sArray){
			SecuenciaTramo secuenciaTramo = new SecuenciaTramo();
			String[] buffer = obj.split("-");
			secuenciaTramo.setOrigen(Integer.valueOf(buffer[0]));
			secuenciaTramo.setDestino(Integer.valueOf(buffer[1]));
			secuenciaTramo.setOrden(Integer.valueOf(buffer[2]));
			lstResult.add(secuenciaTramo);
		}
		return lstResult;
	}

	/*
	 * (non-Javadoc)
	 * @see com.tepsa.sisvyr.model.dao.VentaPasajesDAO#validateSeat(java.lang.Long, java.lang.Integer, java.lang.Integer, java.lang.Integer)
	 */
	@Override
	public Long validateSeat(Itinerario itinerario, Ruta ruta, Integer asiento, Integer piso) throws Exception {
//		String sql = "SELECT vp.venpas_id " +
//				"FROM vrtvenpas vp " +
//				"INNER JOIN (SELECT MAX(venpas_id)venpas_id, c_numcontrol " +
//					"FROM vrtvenpas " +
//					"WHERE itinerario_id="+idItinerario+" AND ruta_id="+idRuta+" GROUP BY c_numcontrol) max_venta " +
//						"ON max_venta.venpas_id=vp.venpas_id " +
//				"WHERE vp.tipmov_id not in("+Constantes.ID_TIPMOV_ANULACION_SISTEMA+","+Constantes.ID_TIPMOV_DEVOLUCION+","+Constantes.ID_TIPMOV_ANULACION+") AND " +
//				"vp.n_numasiento="+asiento+" AND vp.n_numpiso="+piso+" AND vp.c_estreg='"+Constantes.VALUE_ACTIVO+"' ";

		String sql = "SELECT vp.venpas_id, r.localidad_idOrigen, r.localidad_idDestino " +
				"FROM vrtvenpas vp " +
				"INNER JOIN (SELECT MAX(venpas_id)venpas_id, c_numcontrol " +
					"FROM vrtvenpas " +
					"WHERE itinerario_id="+itinerario.getId()+" GROUP BY c_numcontrol) max_venta " +
						"ON max_venta.venpas_id=vp.venpas_id " +
				"INNER JOIN vrmruta r ON r.ruta_id=vp.ruta_id " +
				"WHERE vp.tipmov_id not in("+Constantes.ID_TIPMOV_ANULACION_SISTEMA+","+Constantes.ID_TIPMOV_DEVOLUCION+","+Constantes.ID_TIPMOV_ANULACION+") AND " +
				"vp.c_tiptra not in('"+Constantes.TIPO_OPERACION_PERDIDA_SERVICIO+"') AND vp.n_numasiento="+asiento+" AND vp.n_numpiso="+piso+" AND vp.c_estreg='"+Constantes.VALUE_ACTIVO+"' ";

		log.info(sql);

		/*	Obtenemos las ventas que referencian al mismo asiento y mismo itinerario del boleto a vender.	*/
		List<?> result = getSession().createSQLQuery(sql).list();
		List<VentaPasaje> lstResult = new ArrayList<>();
		for(int i=0; i<result.size(); i++){
			Object[] obj = (Object[])result.get(i);
			VentaPasaje venta = new VentaPasaje();
			venta.setId(((BigDecimal)obj[0]).longValue());
			Ruta rutaVendida = new Ruta();
			rutaVendida.setLocalidadOrigen(new Localidad(((BigDecimal)obj[1]).intValue()));
			rutaVendida.setLocalidadDestino(new Localidad(((BigDecimal)obj[2]).intValue()));
			venta.setRuta(rutaVendida);
			/*	Obtenemos el subconjunto que queremos buscar segun la ruta seleccioanda		*/
			List<Integer> subConjuntoVendido = Util.obtenerSubconjunto(itinerario.getListSecuenciaTramo(), rutaVendida.getLocalidadOrigen().getId(), rutaVendida.getLocalidadDestino().getId());
			venta.setSubConjunto(subConjuntoVendido);
			lstResult.add(venta);
		}

		List<Integer> subConjuntoPorVender = Util.obtenerSubconjunto(itinerario.getListSecuenciaTramo(), ruta.getLocalidadOrigen().getId(), ruta.getLocalidadDestino().getId());

		for(int i=0; i<lstResult.size(); i++){
			VentaPasaje venta = lstResult.get(i);
			for(int j=0; j<subConjuntoPorVender.size(); j++){
				if(venta.getSubConjunto().contains(subConjuntoPorVender.get(j))){
					return venta.getId();
				}
			}
		}

		return Long.valueOf(0);
	}

	/*
	 * (non-Javadoc)
	 * @see com.tepsa.sisvyr.model.dao.VentaPasajesDAO#validarServicio(java.lang.Integer)
	 */
	@Override
	public Long validarServicio(Integer idServicio)throws Exception{
		String hql = "SELECT COUNT(id) FROM VentaPasaje WHERE servicio.id="+idServicio;
		Long total = (Long)getSession().createQuery(hql).uniqueResult();
		return total;
	}

	/*
	 * (non-Javadoc)
	 * @see com.tepsa.sisvyr.model.dao.VentaPasajesDAO#buscarReservasPorConfirmar(java.lang.Integer, java.lang.Integer, java.lang.String[], java.lang.String, java.lang.String, java.lang.String, java.lang.Integer)
	 */
	@Override
	public List<VentaPasaje> buscarReservasPorConfirmar(Integer idOrigen, Integer idDestino, String[] pasajero, String numeroDocumento, String numeroBoleto, String fechaPartida, Integer idAgencia, Long ventaPasajeId)throws Exception{
		String pax = "";
		if(pasajero!=null)
			pax = Util.obtenerFullTextPasajero(pasajero);

		String criterio = idOrigen==null?"":" AND r.localidad_idOrigen=" + idOrigen +" ";
		criterio = criterio + (idDestino==null?"":" AND r.localidad_idDestino=" + idDestino + " ");
		criterio = criterio + (pasajero==null?"":" AND CATSEARCH(p.c_nomape, '" + pax + "', null) > 0 ");
		criterio = criterio + (numeroDocumento==null?"":" AND p.c_numdoc='"+ numeroDocumento +"' ");
		criterio = criterio + (numeroBoleto==null?"":(" AND VP.c_numBoleto='" + numeroBoleto.toUpperCase() +"' "));
		criterio = criterio + (fechaPartida==null?"":" AND VP.d_fecpar=to_date('"+fechaPartida+"','"+Constantes.DATE_FORMAT+"') ");
		criterio = criterio + (idAgencia==null?"":" AND a.agencia_id=" + idAgencia + " ");
		criterio = criterio + (ventaPasajeId==null?"":" AND vp.id=" + ventaPasajeId + " ");
		String sql = "SELECT VP.* FROM vrtvenpas VP " +
				"INNER JOIN (SELECT MAX(venpas_id)venpas_id, c_numcontrol FROM vrtvenpas GROUP BY c_numcontrol) max_vta " +
				"ON max_vta.venpas_id=VP.venpas_id " +
				"INNER JOIN vrmruta r ON r.ruta_id=VP.ruta_id " +
				"INNER JOIN vrmagencia a ON a.agencia_id=VP.agencia_id " +
				"INNER JOIN vrmpasajero p ON p.pasajero_id=VP.pasajero_id " +
				"WHERE VP.c_tiptra='2' AND VP.c_estreg='"+Constantes.VALUE_ACTIVO+"' AND "
						+ "VP.tipmov_id NOT IN("+Constantes.ID_TIPMOV_ANULACION_SISTEMA+","+Constantes.ID_TIPMOV_ANULACION + ")  "
						+ "AND VP.d_fecpar IS NOT NULL " ;

		sql = sql + criterio;
		sql = sql + "ORDER BY VP.pasajero_id, VP.d_fecpar, VP.c_horpar ";

		log.info(sql);
		List<?> result = getSession().createSQLQuery(sql).addEntity("VP",VentaPasaje.class).list();
		List<VentaPasaje> lstResult = new ArrayList<>();
		String sysdate = getDateSystem();
		String expira = "";
		for(int i=0; i<result.size(); i++){
			VentaPasaje ventaPasaje = (VentaPasaje)result.get(i);
			expira = Util.DatetoString(ventaPasaje.getFechaExpiracionReserva(), Constantes.DATE_FORMAT)+" "+ventaPasaje.getHoraExpiracionReserva();
			if(Util.comparaFechasWithTime(sysdate, expira, Util.OPER_MENOR_IGUAL))
				lstResult.add(ventaPasaje);
		}
		return lstResult;
	}

	/*
	 * (non-Javadoc)
	 * @see com.tepsa.sisvyr.model.dao.VentaPasajesDAO#buscarVentaById(java.lang.Long)
	 */
	@Override
	public VentaPasaje buscarVentaById(Long idVenta)throws Exception{
		String sql = "SELECT vp.venpas_id, vp.venpas_idref, i.itinerario_id, r.ruta_id, r.c_origen, r.c_destino, c.cliente_id, c.c_numdoc doccli, " +
							"c.c_razsoc, p.pasajero_id, p.c_apepat, p.c_apemat, p.c_nombre, td.tipdoc_id, td.c_denominacion tipdoc, p.c_numdoc docpax, p.c_fecnac, " +
							"fp.forpag_id, fp.c_denominacion formaPago, s.servicio_id, s.c_denominacion servicio, tc.tipcom_id, tc.c_denominacion tipoComprobante, " +
							"tm.tipmov_id, tm.c_denominacion tipoMovimiento, tfp.tipforpag_id, tfp.c_denominacion tipoformaPago, tcr.tarcre_id, " +
							"tcr.c_denominacion tarjetacredito, vp.c_numboleto, vp.n_numasiento, vp.n_numpiso, to_char(vp.d_fecpar, 'dd/mm/yyyy') fecpar, " +
							"vp.c_horpar, to_char(vp.d_feclle, 'dd/mm/yyyy') feclle, vp.c_horlle, vp.n_secuencial, vp.n_tarifa, vp.n_recargo, vp.n_descuento, " +
							"vp.n_penalidad, vp.n_imppag, vp.n_acuenta, vp.c_tiptra, vp.d_feccad, to_char(vp.d_fecliq, 'dd/mm/yyyy') fecliq, a.agencia_id, " +
							"a.c_denominacion agencia, u.usuario_id, u.c_login, cv.canven_id, cv.c_denominacion canalVenta, vp.manifiesto_id, vp.n_numopeban, " +
							"to_char(vp.d_fecexpres, 'dd/mm/yyyy') fecexpres, vp.c_horexpres, pa.preali_id, pa.c_denominacion alimentacion, ao.agencia_id idAgeLlegada, " +
							"ao.c_denominacion partida, ad.agencia_id idAgePartida, ad.c_denominacion agDestino, vp.c_numcontrol, vp.liquidacion_id, vp.c_estreg, " +
							"to_char(vp.audfecins,'dd/mm/yyyy HH24:mi:ss'), vp.audusuins, vp.audipinse, otc.opetarcre_id, otc.c_denominacion opetarcre, " +
							"vp.c_numbolant, vp.n_idaret, vp.c_rucclicre, vp.n_esfecabi, p.c_nomape, vp.c_observaciones, vp.venpas_idoriginal, " +
							"VP.n_imppagefe, vp.n_imppagtar, vp.promocion_id, vp.n_ididaret, " +
							"cc.cencos_id, cc.c_codigo, cc.c_denominacion, " +
							"vp.c_estdoc, tm.tipmon_id, tm.c_unimon, tm.c_simmon, vp.n_imppagequ,ao.c_nomcor nombreCortoAgenciaPartida, vp.N_TARIFAEQU,vp.N_DESEQU,vp.N_TIPCAM, "+//92
							"td.c_nomcor, u.c_apepat usuApPaterno, u.c_apemat usuApMaterno, u.c_nombre usuaNombre, vp.n_igv, c.c_direccion direccionCliente, " //98
						  + "ad.c_nomcor nombreCortoAgenciaLlegada, vp.n_esfe, vp.d_esfe, vp.tipcob_id, vp.empresa_id, e.c_numdoc, e.c_razsoc empresa, e.c_nomcor AS empcor "+ //99-102
					"FROM vrtvenpas vp " +
						"INNER JOIN vrtitinerario i ON i.itinerario_id=vp.itinerario_id " +
						"INNER JOIN vrmruta r ON r.ruta_id=vp.ruta_id " +
						"LEFT JOIN vrmcliente c ON c.cliente_id=vp.cliente_id " +
						"INNER JOIN vrmpasajero p ON p.pasajero_id=vp.pasajero_id " +
						"INNER JOIN vrmtipdoc td ON td.tipdoc_id=p.tipdoc_id " +
						"LEFT JOIN vrmforpag fp ON fp.forpag_id=vp.forpag_id " +
						"INNER JOIN vrmservicio s ON s.servicio_id=vp.servicio_id " +
						"INNER JOIN vrmtipcom tc ON tc.tipcom_id=vp.tipcom_id " +
						"INNER JOIN vrmtipmov tm ON tm.tipmov_id=vp.tipmov_id " +
						"LEFT JOIN vrmtipforpag tfp ON tfp.tipforpag_id=vp.tipforpag_id " +
						"LEFT JOIN vrmtarcre tcr ON tcr.tarcre_id=vp.tarcre_id " +
						"LEFT JOIN vrmopetarcre otc ON otc.opetarcre_id=tcr.opetarcre_id " +
						"INNER JOIN vrmagencia a ON a.agencia_id=vp.agencia_id " +
						"INNER JOIN vrmusuario u ON u.usuario_id=vp.usuario_id " +
						"INNER JOIN vrmcanven cv ON cv.canven_id=vp.canven_id " +
						"LEFT JOIN vrmpreali pa ON pa.preali_id=vp.preali_id " +
						"LEFT JOIN vrmagencia ao ON ao.agencia_id=vp.agencia_idpartida " +
						"LEFT JOIN vrmagencia ad ON ad.agencia_id=vp.agencia_idllegada " +
						"LEFT JOIN vrmcencos cc ON (cc.cencos_id=vp.cencos_id) " +
						"LEFT JOIN vrmtipmon tm ON (tm.tipmon_id=vp.tipmon_id) "+
						"INNER JOIN vrmempresa e ON e.empresa_id = vp.empresa_id "+
					"WHERE vp.venpas_id="+idVenta;

		log.info(sql);

		Object[] obj = (Object[])getSession().createSQLQuery(sql).uniqueResult();

		VentaPasaje ventaPasaje = new VentaPasaje();
		ventaPasaje.setId(((BigDecimal)obj[0]).longValue());
		ventaPasaje.setVentaPasaje(obj[1]==null?null:new VentaPasaje(((BigDecimal)obj[1]).longValue()));
		ventaPasaje.setItinerario(new Itinerario(((BigDecimal)obj[2]).longValue()));
		Ruta ruta = new Ruta();
		ruta.setId(((BigDecimal)obj[3]).intValue());
		ruta.setOrigen(obj[4].toString());
		ruta.setDestino(obj[5].toString());
		ventaPasaje.setRuta(ruta);
		if(obj[6]!=null){
			Cliente cliente = new Cliente();
			cliente.setId(((BigDecimal)obj[6]).longValue());
			cliente.setNumeroDocumento(obj[7].toString());
			cliente.setRazonSocial(obj[8].toString());
			cliente.setDireccion(obj[98]!=null?obj[98].toString():null);
			ventaPasaje.setCliente(cliente);
		}
		Pasajero pasajero = new Pasajero();
		pasajero.setId(((BigDecimal)obj[9]).longValue());
		pasajero.setApellidoPaterno(obj[10]==null?null:obj[10].toString());
		pasajero.setApellidoMaterno(obj[11]==null?null:obj[11].toString());
		pasajero.setNombre(obj[12].toString());
		TipoDocumento tipoDocumento = new TipoDocumento();
		tipoDocumento.setId(((BigDecimal)obj[13]).intValue());
		tipoDocumento.setDenominacion(obj[14].toString());
		tipoDocumento.setNombreCorto(obj[93].toString());
		pasajero.setTipoDocumento(tipoDocumento);
		pasajero.setNumeroDocumento(obj[15]==null?null:obj[15].toString());
		pasajero.setFechaNacimiento(obj[16]==null?null:obj[16].toString());
		ventaPasaje.setPasajero(pasajero);
		if(obj[17]!=null){
			FormaPago formaPago = new FormaPago();
			formaPago.setId(((BigDecimal)obj[17]).intValue());
			formaPago.setDenominacion(obj[18].toString());
			ventaPasaje.setFormaPago(formaPago);
		}
		Servicio servicio = new Servicio();
		servicio.setId(((BigDecimal)obj[19]).intValue());
		servicio.setDenominacion(obj[20].toString());
		ventaPasaje.setServicio(servicio);
		TipoComprobante tipoComprobante = new TipoComprobante();
		tipoComprobante.setId(((BigDecimal)obj[21]).intValue());
		tipoComprobante.setDenominacion(obj[22].toString());
		ventaPasaje.setTipoComprobante(tipoComprobante);
		TipoMovimiento tipoMovimiento = new TipoMovimiento();
		tipoMovimiento.setId(((BigDecimal)obj[23]).intValue());
		tipoMovimiento.setDenominacion(obj[24].toString());
		ventaPasaje.setTipoMovimiento(tipoMovimiento);
		if(obj[25]!=null){
			TipoFormaPago tipoFormaPago = new TipoFormaPago();
			tipoFormaPago.setId(((BigDecimal)obj[25]).intValue());
			tipoFormaPago.setDenominacion(obj[26].toString());
			ventaPasaje.setTipoFormaPago(tipoFormaPago);
		}
		if(obj[27]!=null){
			TarjetaCredito tarjetaCredito = new TarjetaCredito();
			tarjetaCredito.setId(((BigDecimal)obj[27]).intValue());
			tarjetaCredito.setDenominacion(obj[28].toString());
			ventaPasaje.setTarjetaCredito(tarjetaCredito);
		}
		ventaPasaje.setNumeroBoleto(obj[29]==null?null:obj[29].toString());
		ventaPasaje.setNumeroAsiento(obj[30]==null?null:((BigDecimal)obj[30]).intValue());
		ventaPasaje.setNumeroPiso(obj[31]==null?null:((BigDecimal)obj[31]).intValue());
		ventaPasaje.setFechaPartida(obj[32]==null?null:Util.StringtoDate(obj[32].toString(), Constantes.DATE_FORMAT));
		ventaPasaje.setHoraPartida(obj[33]==null?null:obj[33].toString());
		ventaPasaje.setFechaLlegada(obj[34]==null?null:Util.StringtoDate(obj[34].toString(), Constantes.DATE_FORMAT));
		ventaPasaje.setHoraLllegada(obj[35]==null?null:obj[35].toString());
		ventaPasaje.setSecuencial(((BigDecimal)obj[36]).intValue());
		ventaPasaje.setTarifa(((BigDecimal)obj[37]).doubleValue());
		ventaPasaje.setRecargo(((BigDecimal)obj[38]).doubleValue());
		ventaPasaje.setDescuento(((BigDecimal)obj[39]).doubleValue());
		ventaPasaje.setPenalidad(((BigDecimal)obj[40]).doubleValue());
		ventaPasaje.setImportePagado(((BigDecimal)obj[41]).doubleValue());
		ventaPasaje.setAcuenta(((BigDecimal)obj[42]).doubleValue());
		ventaPasaje.setTipoTransaccion(obj[43].toString());
		ventaPasaje.setFechaCaducidad((Date)obj[44]);
		ventaPasaje.setFechaLiquidacion(obj[45]==null?null:Util.StringtoDate(obj[45].toString(), Constantes.DATE_FORMAT));
		Agencia agencia = new Agencia();
		agencia.setId(((BigDecimal)obj[46]).intValue());
		agencia.setDenominacion(obj[47].toString());
		ventaPasaje.setAgencia(agencia);
		Usuario usuario = new Usuario();
		usuario.setId(((BigDecimal)obj[48]).intValue());
		usuario.setLogin(obj[49].toString());
		usuario.setApellidoPaterno(obj[94].toString());
		usuario.setApellidoMaterno(obj[95]!=null?obj[95].toString():null);
		usuario.setNombre(obj[96].toString());
		ventaPasaje.setUsuario(usuario);
		CanalVenta canalVenta = new CanalVenta();
		canalVenta.setId(((BigDecimal)obj[50]).intValue());
		canalVenta.setDenominacion(obj[51].toString());
		ventaPasaje.setCanalVenta(canalVenta);
		ventaPasaje.setManifiesto(obj[52]==null?null:new Manifiesto(((BigDecimal)obj[52]).longValue()));
		ventaPasaje.setNumeroOperacionBancaria(obj[53]==null?null:obj[53].toString());
		ventaPasaje.setFechaExpiracionReserva(obj[54]==null?null:Util.StringtoDate(obj[54].toString(), Constantes.DATE_FORMAT));
		ventaPasaje.setHoraExpiracionReserva(obj[55]==null?null:obj[55].toString());
		if(obj[56]!=null){
			PreferenciaAlimentaria preferenciaAlimentaria = new PreferenciaAlimentaria();
			preferenciaAlimentaria.setId(((BigDecimal)obj[56]).intValue());
			preferenciaAlimentaria.setDenominacion(obj[57].toString());
			ventaPasaje.setPreferenciaAlimentaria(preferenciaAlimentaria);
		}
		if(obj[58]!=null){
			Agencia agenciaPartida = new Agencia();
			agenciaPartida.setId(((BigDecimal)obj[58]).intValue());
			agenciaPartida.setDenominacion(obj[59].toString());
			agenciaPartida.setNombreCorto(obj[89]!=null?obj[89].toString():obj[59].toString());
			ventaPasaje.setAgenciaPartida(agenciaPartida);
		}
		if(obj[60]!=null){
			Agencia agenciaLlegada = new Agencia();
			agenciaLlegada.setId(((BigDecimal)obj[60]).intValue());
			agenciaLlegada.setDenominacion(obj[61].toString());
			agenciaLlegada.setNombreCorto(obj[99]!=null?obj[99].toString():obj[61].toString());
			ventaPasaje.setAgenciaLlegada(agenciaLlegada);
		}
		ventaPasaje.setNumeroControl(obj[62].toString());
		ventaPasaje.setLiquidacion(obj[63]==null?null:new Liquidacion(((BigDecimal)obj[63]).intValue()));
		ventaPasaje.setEstadoRegistro(obj[64].toString());
		ventaPasaje.setFechaInsercion(Util.StringtoDate(obj[65].toString(), Constantes.DATE_TIME_FORMAT));
		ventaPasaje.setUsuarioInsercion(obj[66]==null?null:obj[66].toString());
		ventaPasaje.setIpInsercion(obj[67]==null?null:obj[67].toString());
		if(obj[27]!=null){
			OperadorTarjetaCredito operadorTarjetaCredito = new OperadorTarjetaCredito();
			operadorTarjetaCredito.setId(((BigDecimal)obj[68]).intValue());
			operadorTarjetaCredito.setDenominacion(obj[69].toString());
			ventaPasaje.getTarjetaCredito().setOperadorTarjetaCredito(operadorTarjetaCredito);
		}
		ventaPasaje.setNumeroBoletoAnterior(obj[70]==null?null:obj[70].toString());
		ventaPasaje.setIdaRetorno(((BigDecimal)obj[71]).intValue());
		ventaPasaje.setRucClienteCredito(obj[72]==null?null:obj[72].toString());
		ventaPasaje.setEsFechaAbierta(((BigDecimal)obj[73]).intValue());
		pasajero.setNombresApellidos(obj[74]==null?" ":obj[74].toString());
		ventaPasaje.setObservaciones(obj[75]==null?null:obj[75].toString());
		if(obj[76]!=null)
			ventaPasaje.setVentaOriginal(((BigDecimal)obj[76]).longValue());

		ventaPasaje.setImportePagadoEfectivo(((BigDecimal)obj[77]).doubleValue());
		ventaPasaje.setImportePagadoTarjeta(((BigDecimal)obj[78]).doubleValue());
		if(obj[79]!=null){
			Promocion promocion = new Promocion();
			promocion.setId(((BigDecimal)obj[79]).longValue());
			ventaPasaje.setPromocion(promocion);
		}
		ventaPasaje.setIdentificadorIdaRetorno(obj[80]==null?null:((BigDecimal)obj[80]).longValue());

		if(obj[81]!=null){//Centros de costo (impl: 13/03/2013 - jabanto)
			CentroCosto centroCosto=new CentroCosto();
			centroCosto.setId(((BigDecimal)obj[81]).intValue());
			centroCosto.setCodigo(obj[82].toString());
			centroCosto.setDenominacion(obj[83].toString());
			ventaPasaje.setCentroCosto(centroCosto);
		}
		ventaPasaje.setEstadoDocumento(obj[84]!=null?obj[84].toString():null);

		/*Valida si tiempo tipo de moneda*/
		if(obj[85]!=null){
			TipoMoneda tipoMoneda=new TipoMoneda();
			tipoMoneda.setId(((BigDecimal)obj[85]).intValue());
			tipoMoneda.setUnidadMonetaria(obj[86]!=null?obj[86].toString():"");
			tipoMoneda.setSimboloMonetario(obj[87]!=null?obj[87].toString():"");
			ventaPasaje.setImportePagadoEquibalente(((BigDecimal)obj[88]).doubleValue());
			ventaPasaje.setTipoMoneda(tipoMoneda);
			ventaPasaje.setTarifaEquibalente(obj[90]!=null?((BigDecimal)obj[90]).doubleValue():null);
			ventaPasaje.setDescuentoEquibalente(obj[91]!=null?((BigDecimal)obj[91]).doubleValue():null);
			ventaPasaje.setTipoCambio(obj[92]!=null?((BigDecimal)obj[92]).doubleValue():null);
		}

		/*igv de la venta*/
		ventaPasaje.setIgv((obj[97]!=null?((BigDecimal)obj[97]).doubleValue():null));
		ventaPasaje.setEnviadoSFE(obj[100]!=null?((BigDecimal)obj[100]).intValue():null);
		ventaPasaje.setFechaEnvioSFE(obj[101]!=null?(Date)obj[101]:null);
		if(obj[102]!=null) {
			TipoCobranza tipoCobranza= new TipoCobranza();
			tipoCobranza.setId(((BigDecimal)obj[102]).intValue());
			ventaPasaje.setTipoCobranza(tipoCobranza);
		}
		Empresa empresa = new Empresa();
		empresa.setId(((BigDecimal)obj[103]).intValue());
		empresa.setNumeroDocumento(obj[104].toString());
		empresa.setRazonSocial(obj[105].toString());
		empresa.setNombreCorto(obj[106].toString());
		ventaPasaje.setEmpresa(empresa);


		return ventaPasaje;
	}

	/*
	 * (non-Javadoc)
	 * @see com.tepsa.sisvyr.model.dao.VentaPasajesDAO#getDateSystem()
	 */
    @Override
	public String getDateSystem()throws Exception{
    	String sql = "SELECT to_char(sysdate,'dd/mm/yyyy hh24:mi:ss') DateSystem FROM dual";

		log.info(sql);
		try{
			SQLQuery q = getSession().createSQLQuery(sql);
			//log.debug(sql);
			String fecha = q.list().toString();
			return fecha.substring(1, fecha.length()-1);
		}catch (Exception ex) {
			log.error(ex);
			throw new Exception(ex);
		}
    }

	/*
	 * (non-Javadoc)
	 * @see com.tepsa.sisvyr.model.dao.VentaPasajesDAO#buscarFechaAbiertaPorConfirmar(java.lang.Integer, java.lang.Integer, java.lang.String[], java.lang.String, java.lang.String)
	 */
	@Override
	public List<VentaPasaje> buscarFechaAbiertaPorConfirmar(Integer idOrigen, Integer idDestino, String pasajero[], String numeroControl, String numeroBoleto, String numeroDocumento) throws Exception {
		String pax = "";
		if(pasajero!=null)
			pax = Util.obtenerFullTextPasajero(pasajero);

		String criterio = idOrigen==null?"":" AND r.localidad_idOrigen=" + idOrigen +" ";
		criterio = criterio + (idDestino==null?"":" AND r.localidad_idDestino=" + idDestino + " ");
		criterio = criterio + (pasajero==null?"":" AND CATSEARCH(p.c_nomape, '" + pax + "', null) > 0 ");
		criterio = criterio + (numeroControl==null?"":" AND {VP}.c_numcontrol='"+ numeroControl +"' ");
		criterio = criterio + (numeroBoleto==null?"":(" AND {VP}.c_numboleto='" + numeroBoleto.toUpperCase() +"' "));
		criterio = criterio + (numeroDocumento==null?"":(" AND p.c_numdoc='" + numeroDocumento +"' "));
		String sql = "SELECT {VP.*} FROM vrtvenpas {VP} " +
				"INNER JOIN (SELECT MAX(venpas_id)venpas_id, c_numcontrol FROM vrtvenpas GROUP BY c_numcontrol) max_vta " +
				"ON max_vta.venpas_id={VP}.venpas_id " +
				"INNER JOIN vrmruta r ON r.ruta_id={VP}.ruta_id " +
				"INNER JOIN vrmagencia a ON a.agencia_id={VP}.agencia_id " +
				"INNER JOIN vrmpasajero p ON p.pasajero_id={VP}.pasajero_id " +
				"INNER JOIN vrmempresa e ON e.empresa_id={VP}.empresa_id " +
				"WHERE {VP}.d_fecpar is null AND {VP}.c_estreg='"+Constantes.VALUE_ACTIVO+
				"' AND {VP}.tipmov_id NOT IN ("+Constantes.ID_TIPMOV_ANULACION_SISTEMA+","+
				Constantes.ID_TIPMOV_ANULACION+","+Constantes.ID_TIPMOV_DEVOLUCION+","+Constantes.ID_TIPMOV_SERVICIO_ESPECIAL+")  "
						+ " AND {VP}.tipcom_id 	IN ("+Constantes.ID_TIPCOM_BOLETO_VIAJE+","+Constantes.ID_TIPCOM_BOLETA_VENTA+","+Constantes.ID_TIPCOM_FACTURA+")  "
			    +"  AND {VP}.d_Feccad > sysdate ";
		sql = sql + criterio;
		sql = sql + "ORDER BY {VP}.pasajero_id ";

		log.info(sql);
		List<?> result = getSession().createSQLQuery(sql).addEntity("VP",VentaPasaje.class).list();

		return (List<VentaPasaje>)result;
	}

	/*
	 * (non-Javadoc)
	 * @see com.tepsa.sisvyr.model.dao.VentaPasajesDAO#buscarUsuarioPorAgencia(java.lang.Integer, java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	public List<Usuario> buscarUsuarioPorAgencia(Integer idAgencia, String estado, String fechaInicio, String fechaFin, String rucCredito)throws Exception{
		String hql="";
		if(rucCredito!=null){
			hql = "SELECT DISTINCT(vp.usuario.id), u.apellidoPaterno, u.apellidoMaterno, u.nombre, u.login " +
					"FROM VentaPasaje vp JOIN vp.usuario u WHERE vp.rucClienteCredito='"+rucCredito+"' AND " +
						"vp.fechaLiquidacion BETWEEN to_date('"+fechaInicio+"', 'dd/mm/yyyy') AND to_date('"+fechaFin+"', 'dd/mm/yyyy') AND " +
						"vp.estadoRegistro IN('"+Constantes.VALUE_ACTIVO+"','"+estado+"') "
					  + "AND vp.tipoComprobante.id IN ("+Constantes.ID_TIPCOM_VOUCHER_AGENCIA_VIAJES+","+Constantes.ID_TIPCOM_VOUCHER_CORPORATIVO+") "
					  + "AND vp.tipoMovimiento.id NOT IN ("+Constantes.ID_TIPMOV_ANULACION_SISTEMA+","+Constantes.ID_TIPMOV_DEVOLUCION+")" +
					    "AND vp.c_tiptra <> 2" +
					"ORDER BY u.apellidoPaterno, u.apellidoMaterno, u.nombre";
		}else{
			hql = "SELECT DISTINCT(vp.usuario.id), u.apellidoPaterno, u.apellidoMaterno, u.nombre, u.login " +
					"FROM VentaPasaje vp JOIN vp.usuario u WHERE vp.agencia.id="+idAgencia+" AND " +
						"vp.fechaLiquidacion BETWEEN to_date('"+fechaInicio+"', 'dd/mm/yyyy') AND to_date('"+fechaFin+"', 'dd/mm/yyyy') AND " +
						"vp.estadoRegistro IN('"+Constantes.VALUE_ACTIVO+"','"+estado+"') " +
					"ORDER BY u.apellidoPaterno, u.apellidoMaterno, u.nombre";
		}


		log.info(hql);
		List<?> result = getSession().createQuery(hql).list();
		List<Usuario> lstResult = new ArrayList<>();
		for(int i=0; i<result.size(); i++){
			Object[] obj = (Object[])result.get(i);
			Usuario usuario = new Usuario();
			usuario.setId(Integer.valueOf(obj[0].toString()));
			usuario.setApellidoPaterno(obj[1]==null?"":obj[1].toString());
			usuario.setApellidoMaterno(obj[2]==null?"":obj[2].toString());
			usuario.setNombre(obj[3]==null?"":obj[3].toString());
			usuario.setLogin(obj[4].toString());
			lstResult.add(usuario);
		}
		return lstResult;
	}

	/*
	 * (non-Javadoc)
	 * @see com.tepsa.sisvyr.model.dao.VentaPasajesDAO#buscarDetalladoVentas(java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	public List<VentaPasaje> buscarDetalladoVentas(String idAgencia, String idUsuario, String idTipoMovimiento, String fechaInicio, String fechaFin, String estado)throws Exception{
		idAgencia = idAgencia.equals("")?"%":idAgencia;
		idUsuario = idUsuario.equals("")?"%":idUsuario;
		idTipoMovimiento = idTipoMovimiento.equals("")?"%":idTipoMovimiento;

		String sql = "SELECT tm.c_denominacion, vp.c_numcontrol, vp.c_numboleto, vp.n_tarifa, vp.n_recargo, vp.n_descuento, vp.n_acuenta, " +
				"vp.n_penalidad, vp.n_imppag, u.c_apepat, u.c_apemat, u.c_nombre, fp.forpag_id, fp.c_denominacion forpag, tfp.tipforpag_id, " +
				"tfp.c_denominacion tipforpag, otc.opetarcre_id, otc.c_denominacion opetarcre, tc.tarcre_id, tc.c_denominacion tarcre, " +
				"vp.audfecins, vp.venpas_id, tm.tipmov_id, vp.n_ididaret " +
				"FROM vrtvenpas vp " +
				"INNER JOIN vrmtipmov tm ON tm.tipmov_id=vp.tipmov_id " +
				"INNER JOIN vrmusuario u ON u.usuario_id=vp.usuario_id " +
				"INNER JOIN vrmforpag fp ON fp.forpag_id=vp.forpag_id " +
				"INNER JOIN vrmtipforpag tfp ON tfp.tipforpag_id=vp.tipforpag_id " +
				"LEFT JOIN vrmtarcre tc ON tc.tarcre_id=vp.tarcre_id " +
				"LEFT JOIN vrmopetarcre otc ON otc.opetarcre_id=tc.opetarcre_id " +
				"WHERE vp.agencia_id LIKE '"+idAgencia+"' AND vp.usuario_id LIKE '"+idUsuario+"' AND vp.tipmov_id LIKE '"+idTipoMovimiento+"' AND " +
					"vp.d_fecliq BETWEEN to_date('"+fechaInicio+"', 'dd/mm/yyyy') AND to_date('"+fechaFin+"', 'dd/mm/yyyy') AND " +
					"vp.c_estreg IN ('"+Constantes.VALUE_ACTIVO+"','"+estado+"') AND vp.c_tiptra='"+Constantes.TIPO_OPERACION_VENTA+"' " +
				"ORDER BY vp.c_numboleto, vp.audfecins";

		log.info(sql);

		List<?> result = getSession().createSQLQuery(sql).list();
		List<VentaPasaje> lstResult = new ArrayList<>();
		for(int i=0; i<result.size(); i++){
			Object[] obj = (Object[]) result.get(i);
			VentaPasaje ventaPasaje = new VentaPasaje();
			TipoMovimiento tipoMovimiento = new TipoMovimiento();
			tipoMovimiento.setId(((BigDecimal)obj[22]).intValue());
			tipoMovimiento.setDenominacion(obj[0].toString());
			ventaPasaje.setTipoMovimiento(tipoMovimiento);
			ventaPasaje.setNumeroControl(obj[1].toString());
			ventaPasaje.setNumeroBoleto(obj[2].toString());
			ventaPasaje.setTarifa(((BigDecimal)obj[3]).doubleValue());
			ventaPasaje.setRecargo(((BigDecimal)obj[4]).doubleValue());
			ventaPasaje.setDescuento(((BigDecimal)obj[5]).doubleValue());
			ventaPasaje.setAcuenta(((BigDecimal)obj[6]).doubleValue());
			ventaPasaje.setPenalidad(((BigDecimal)obj[7]).doubleValue());
			ventaPasaje.setImportePagado(((BigDecimal)obj[8]).doubleValue());
			Usuario usuario = new Usuario();
			usuario.setApellidoPaterno(obj[9]==null?"":obj[9].toString());
			usuario.setApellidoMaterno(obj[10]==null?"":obj[10].toString());
			usuario.setNombre(obj[11]==null?"":obj[11].toString());
			ventaPasaje.setUsuario(usuario);
			FormaPago formaPago = new FormaPago();
			formaPago.setId(((BigDecimal)obj[12]).intValue());
			formaPago.setDenominacion(obj[13].toString());
			ventaPasaje.setFormaPago(formaPago);
			TipoFormaPago tipoFormaPago = new TipoFormaPago();
			tipoFormaPago.setId(((BigDecimal)obj[14]).intValue());
			tipoFormaPago.setDenominacion(obj[15].toString());
			if(obj[16]!=null){
				OperadorTarjetaCredito operadorTarjetaCredito = new OperadorTarjetaCredito();
				operadorTarjetaCredito.setId(((BigDecimal)obj[16]).intValue());
				operadorTarjetaCredito.setDenominacion(obj[17].toString());
				TarjetaCredito tarjetaCredito = new TarjetaCredito();
				tarjetaCredito.setId(((BigDecimal)obj[18]).intValue());
				tarjetaCredito.setDenominacion(obj[19].toString());
				tarjetaCredito.setOperadorTarjetaCredito(operadorTarjetaCredito);
				ventaPasaje.setTarjetaCredito(tarjetaCredito);
			}
			ventaPasaje.setFechaInsercion((Date)obj[20]);
			ventaPasaje.setId(((BigDecimal)obj[21]).longValue());
			ventaPasaje.setIdentificadorIdaRetorno(obj[23]==null?null:((BigDecimal)obj[23]).longValue());
			lstResult.add(ventaPasaje);
		}
		return lstResult;
	}

	/*
	 * (non-Javadoc)
	 * @see com.tepsa.sisvyr.model.dao.VentaPasajesDAO#validarNumeroBoleto(java.lang.String, java.lang.Integer)
	 */
	@Override
	public Integer validarNumeroBoleto(String numeroBoleto, Integer idTipoComprobante)throws Exception{
		String hql = "FROM VentaPasaje vp WHERE vp.numeroBoleto='"+numeroBoleto+"' AND vp.tipoComprobante.id="+idTipoComprobante+" AND " +
				"vp.estadoRegistro = '"+Constantes.VALUE_ACTIVO+"'";

		log.info(hql);
		int result = getSession().createQuery(hql).list().size();
		return result;
	}

	/*
	 * (non-Javadoc)
	 * @see com.tepsa.sisvyr.model.dao.VentaPasajesDAO#buscarVentasPostergar(java.lang.Integer, java.lang.Integer, java.lang.String[], java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	public List<VentaPasaje> buscarVentasPostergar(Integer idOrigen, Integer idDestino, String[] pasajero, String numeroControl, String numeroBoleto, String fechaPartida)throws Exception{
		String pax = "";
		if(pasajero!=null)
			pax = Util.obtenerFullTextPasajero(pasajero);

		String criterio = idOrigen==null?"":" AND r.localidad_idOrigen=" + idOrigen +" ";
		criterio = criterio + (idDestino==null?"":" AND r.localidad_idDestino=" + idDestino + " ");
		criterio = criterio + (pasajero==null?"":" AND CATSEARCH(p.c_nomape, '" + pax + "', null) > 0 ");
		criterio = criterio + (numeroControl==null?"":" AND VP.c_numcontrol='"+ numeroControl +"' ");
		criterio = criterio + (numeroBoleto==null?"":(" AND VP.c_numboleto='" + numeroBoleto.toUpperCase() +"' "));
		criterio = criterio + (fechaPartida==null?"":" AND VP.d_fecpar=to_date('"+fechaPartida+"','"+Constantes.DATE_FORMAT+"') ");
		String sql = "SELECT VP.* FROM vrtvenpas VP " +
				"INNER JOIN (SELECT MAX(venpas_id)venpas_id, c_numcontrol FROM vrtvenpas where d_fecpar>sysdate-60 GROUP BY c_numcontrol) max_vta " +
				"ON max_vta.venpas_id=VP.venpas_id " +
				"INNER JOIN vrmruta r ON r.ruta_id=VP.ruta_id " +
				"INNER JOIN vrmagencia a ON a.agencia_id=VP.agencia_id " +
				"INNER JOIN vrmpasajero p ON p.pasajero_id=VP.pasajero_id " +
				"WHERE VP.c_tiptra IN ('"+Constantes.TIPO_OPERACION_VENTA+"','"+Constantes.TIPO_OPERACION_PERDIDA_SERVICIO+"') AND VP.c_estreg='"+Constantes.VALUE_ACTIVO+"' " +
						"AND VP.tipcom_id in ("+Constantes.ID_TIPCOM_BOLETO_VIAJE+","+Constantes.ID_TIPCOM_BOLETA_VENTA+","+Constantes.ID_TIPCOM_FACTURA+" ) " +
						"AND VP.tipmov_id NOT IN ("+Constantes.ID_TIPMOV_ANULACION_SISTEMA+","+Constantes.ID_TIPMOV_DEVOLUCION+","+Constantes.ID_TIPMOV_ANULACION+")" ; //" --AND {VP}.tipmov_id!="+Constantes.TIPMOV_ANULACION + " " +
						//"AND VP.d_fecpar is not null AND (VP.tipforpag_id="+Constantes.TIPFORPAG_EFECTIVO+ " OR " +
						//"VP.tipforpag_id="+Constantes.TIPFORPAG_CREDITO+ " OR VP.tipforpag_id="+Constantes.TIPFORPAG_CANJE_PUBLICITARIO+") " ;

		sql = sql + criterio;
		sql = sql + "ORDER BY VP.c_numboleto, VP.d_fecpar, VP.c_horpar ";

		log.info(sql);
		List<?> result = getSession().createSQLQuery(sql).addEntity("VP",VentaPasaje.class).list();

		return (List<VentaPasaje>)result;
	}

	/*
	 * (non-Javadoc)
	 * @see com.tepsa.sisvyr.model.dao.VentaPasajesDAO#buscarBoletosReimprimir(java.lang.String, java.lang.String[], java.lang.String)
	 */
	@Override
	public List<VentaPasaje> buscarBoletosReimprimir(String numeroDocumento, String[] pasajero, String fechaPartida)throws Exception{
		String passenger = "";
		if(pasajero!=null)
			passenger = Util.obtenerFullTextPasajero(pasajero);

		String criterios = numeroDocumento==null?"":" AND p.c_numdoc LIKE '" + numeroDocumento + "%' ";
		criterios = criterios + (pasajero==null?"":" AND CATSEARCH(p.c_nomape, '" + passenger + "', null) > 0 ");

		String sql = "SELECT vp.venpas_id, r.c_origen, r.c_destino, p.c_apepat, p.c_apemat, p.c_nombre, p.c_nomape, vp.d_fecpar, " +
				"vp.c_horpar, vp.tipmov_id, tm.c_denominacion, vp.c_numcontrol, (vp.n_tarifa+vp.n_recargo-vp.n_descuento) importe, " +
				"vp.venpas_idoriginal " +
				"FROM vrtvenpas vp " +
					"INNER JOIN (SELECT MAX(venpas_id)venpas_id FROM vrtvenpas group by c_numcontrol)max_venta ON max_venta.venpas_id=vp.venpas_id " +
					"INNER JOIN vrmpasajero p ON p.pasajero_id=vp.pasajero_id " +
					"INNER JOIN vrmruta r ON r.ruta_id=vp.ruta_id " +
					"INNER JOIN vrmtipmov tm ON tm.tipmov_id=vp.tipmov_id " +
				"WHERE vp.d_fecpar = to_date('"+fechaPartida+"', '"+Constantes.DATE_FORMAT+"') "
				 + " AND vp.c_tiptra in ('"+Constantes.TIPO_OPERACION_VENTA+"','"+Constantes.TIPO_OPERACION_VENTA_POOL+"','"+Constantes.TIPO_OPERACION_PERDIDA_SERVICIO+"') " +
//				   "AND vp.tipcom_id="+Constantes.ID_TIPCOM_BOLETO_VIAJE+" "
				   "AND vp.tipcom_id IN ("+Constantes.ID_TIPCOM_BOLETA_VENTA+","+Constantes.ID_TIPCOM_FACTURA+") "
				 + "AND vp.tipmov_id NOT IN("+Constantes.ID_TIPMOV_ANULACION_SISTEMA+","+Constantes.ID_TIPMOV_DEVOLUCION+","+Constantes.ID_TIPMOV_ANULACION+") ";

		sql = sql + criterios + " ORDER BY p.c_nomape ";

		log.info(sql);
		List<?> result = getSession().createSQLQuery(sql).list();
		List<VentaPasaje> lstResult = new ArrayList<>();
		for(int i=0; i<result.size(); i++){
			Object[] obj = (Object[])result.get(i);
			VentaPasaje ventaPasaje = new VentaPasaje();
			ventaPasaje.setId(((BigDecimal)obj[0]).longValue());
			Ruta ruta = new Ruta();
			ruta.setOrigen(obj[1].toString());
			ruta.setDestino(obj[2].toString());
			ventaPasaje.setRuta(ruta);
			Pasajero pax = new Pasajero();
			pax.setApellidoPaterno(obj[3]==null?null:obj[3].toString());
			pax.setApellidoMaterno(obj[4]==null?null:obj[4].toString());
			pax.setNombre(obj[5]==null?null:obj[5].toString());
			pax.setNombresApellidos(obj[6].toString());
			ventaPasaje.setPasajero(pax);
			ventaPasaje.setFechaPartida((Date)obj[7]);
			ventaPasaje.setHoraPartida(obj[8].toString());
			TipoMovimiento tipoMovimiento = new TipoMovimiento();
			tipoMovimiento.setId(((BigDecimal)obj[9]).intValue());
			tipoMovimiento.setDenominacion(obj[10].toString());
			ventaPasaje.setTipoMovimiento(tipoMovimiento);
			ventaPasaje.setNumeroControl(obj[11].toString());
			ventaPasaje.setImportePagado(((BigDecimal)obj[12]).doubleValue());
			ventaPasaje.setVentaOriginal(((BigDecimal)obj[13]).longValue());
			lstResult.add(ventaPasaje);
		}
		return lstResult;
	}

	/*
	 * (non-Javadoc)
	 * @see com.tepsa.sisvyr.model.dao.VentaPasajesDAO#buscarBoletosDevolucion(java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	public List<VentaPasaje> buscarBoletosDevolucion(String numeroDocumento, String numeroControl, String numeroBoleto)throws Exception{
		String criterio = numeroDocumento==null?"":" AND p.c_numdoc = '" + numeroDocumento + "' ";
		criterio = criterio + (numeroControl==null?"":" AND {VP}.c_numcontrol='"+ numeroControl +"' ");
		criterio = criterio + (numeroBoleto==null?"":(" AND {VP}.c_numboleto='" + numeroBoleto.toUpperCase() +"' "));

		String criterioByMax=" WHERE c_estreg='"+Constantes.VALUE_ACTIVO+"' ";
		if(numeroControl!=null)
			criterioByMax+="AND c_numcontrol='"+numeroControl+"' ";
		else if (numeroBoleto!=null)
			criterioByMax+="AND c_numboleto='"+numeroBoleto.toUpperCase()+"' ";

		String sql = "SELECT {VP.*} FROM vrtvenpas {VP} " +
				"INNER JOIN (SELECT MAX(venpas_id)venpas_id, c_numcontrol FROM vrtvenpas "+criterioByMax+" GROUP BY c_numcontrol) max_vta " +
				"ON max_vta.venpas_id={VP}.venpas_id " +
				"INNER JOIN vrmruta r ON r.ruta_id={VP}.ruta_id " +
				"INNER JOIN vrmagencia a ON a.agencia_id={VP}.agencia_id " +
				"INNER JOIN vrmpasajero p ON p.pasajero_id={VP}.pasajero_id " +
				"WHERE {VP}.c_tiptra in('"+Constantes.TIPO_OPERACION_VENTA+"','"+Constantes.TIPO_OPERACION_VENTA_POOL+"','"+Constantes.TIPO_OPERACION_PERDIDA_SERVICIO+"','"+Constantes.TIPO_OPERACION_VENTA_ESPECIAL+"') AND {VP}.c_estreg='"+Constantes.VALUE_ACTIVO+"' "
			  + "AND {VP}.tipcom_id IN ("+Constantes.ID_TIPCOM_BOLETO_VIAJE+","+Constantes.ID_TIPCOM_BOLETA_VENTA+", "+Constantes.ID_TIPCOM_FACTURA+")" ;

		sql = sql + criterio;
		sql = sql + "ORDER BY {VP}.c_numboleto, {VP}.d_fecpar, {VP}.c_horpar ";

		log.info(sql);
		List<?> result = getSession().createSQLQuery(sql).addEntity("VP",VentaPasaje.class).list();

		return (List<VentaPasaje>)result;

	}

	/*
	 * (non-Javadoc)
	 * @see com.tepsa.sisvyr.model.dao.VentaPasajesDAO#buscarComprobantesSinBoleto(java.lang.String, java.lang.Integer, java.lang.Integer, java.lang.Integer, java.lang.Integer)
	 */
	@Override
	public List<VentaPasaje> buscarComprobantesSinBoleto(String fechaPartida, Integer idAgencia, Integer idTipoComprobante, Integer idRol, Integer idAgenciaEmision)throws Exception{
		String sql = "SELECT vp.venpas_id, ap.c_denominacion embarque, tc.c_denominacion comprobante, vp.c_numboleto, vp.c_numcontrol, " +
				"vp.n_numasiento, vp.d_fecpar, vp.c_horpar, c.c_numdoc ruc, c.c_razsoc cliente, p.c_numdoc dni, p.c_nomape, " +
				"r.c_origen, r.c_destino, u.c_apepat, u.c_apemat, u.c_nombre, a.c_denominacion agencia, vp.audfecins " +
				",ap.c_nomcor nombreCortoAgePartida, a.c_nomcor nombreCortoAgeLlegada " +
				"FROM vrtvenpas vp " +
				"INNER JOIN (SELECT MAX(venpas_id)venpas_id, venpas_idoriginal FROM vrtvenpas GROUP BY venpas_idoriginal) max_vta ON max_vta.venpas_id=vp.venpas_id " +
				"INNER JOIN vrmagencia ap ON ap.agencia_id=vp.agencia_idpartida " +
				"INNER JOIN vrmtipcom tc ON tc.tipcom_id=vp.tipcom_id " +
				"LEFT JOIN vrmcliente c ON c.cliente_id=vp.cliente_id " +
				"INNER JOIN vrmpasajero p ON p.pasajero_id=vp.pasajero_id " +
				"INNER JOIN vrmruta r ON r.ruta_id=vp.ruta_id " +
				"INNER JOIN vrmusuario u ON u.usuario_id=vp.usuario_id " +
				"INNER JOIN vrmagencia a ON a.agencia_id=vp.agencia_id " +
				"WHERE vp.d_fecpar>=to_date('"+fechaPartida+"', '"+Constantes.DATE_FORMAT+"')  AND " +
						" vp.c_tiptra='"+Constantes.TIPO_OPERACION_VENTA+"' "+
						"AND vp.c_estreg='A' AND vp.tipmov_id NOT IN (5,6,13) ";
		if(idTipoComprobante!=null)
			sql+=" AND vp.tipcom_id="+idTipoComprobante;
		else
			sql+= "AND vp.tipcom_id IN ("+Constantes.ID_TIPCOM_RECIBO_CAJA+","+Constantes.ID_TIPCOM_VOUCHER_AGENCIA_VIAJES+","+
						Constantes.ID_TIPCOM_VOUCHER_CORPORATIVO+") ";
		if(idRol.intValue()!=Constantes.ID_ROL_SUPER_USUARIO)
			sql+=" AND vp.agencia_idpartida="+idAgencia+" AND ap.n_esterminal=1";

		if(idAgenciaEmision!=null)
			sql+= " AND a.agencia_id="+idAgenciaEmision;

		sql = sql + " ORDER BY vp.d_fecpar, vp.c_horpar, embarque, comprobante, vp.c_numboleto ";
		log.info(sql);

		List<?>result = getSession().createSQLQuery(sql).list();
		List<VentaPasaje>lstResult = new ArrayList<>();

		for(int i=0; i<result.size(); i++){
			Object[] obj = (Object[])result.get(i);
			VentaPasaje ventaPasaje = new VentaPasaje();
			ventaPasaje.setId(((BigDecimal)obj[0]).longValue());
			Agencia agenciaPartida = new Agencia();
			agenciaPartida.setDenominacion(obj[1].toString());
			agenciaPartida.setNombreCorto(obj[19]!=null?obj[19].toString():obj[1].toString());
			ventaPasaje.setAgenciaPartida(agenciaPartida);
			TipoComprobante tipoComprobante = new TipoComprobante();
			tipoComprobante.setDenominacion(obj[2].toString());
			ventaPasaje.setTipoComprobante(tipoComprobante);
			ventaPasaje.setNumeroBoleto(obj[3].toString());
			ventaPasaje.setNumeroControl(obj[4].toString());
			ventaPasaje.setNumeroAsiento(((BigDecimal)obj[5]).intValue());
			ventaPasaje.setFechaPartida((Date)obj[6]);
			ventaPasaje.setHoraPartida(obj[7].toString());
			if(obj[8]!=null){
				Cliente cliente = new Cliente();
				cliente.setNumeroDocumento(obj[8].toString());
				cliente.setRazonSocial(obj[9].toString());
				ventaPasaje.setCliente(cliente);
			}
			Pasajero pasajero = new Pasajero();
			pasajero.setNumeroDocumento(obj[10]==null?null:obj[10].toString());
			pasajero.setNombresApellidos(obj[11]!=null?obj[11].toString():"");
			ventaPasaje.setPasajero(pasajero);
			Ruta ruta = new Ruta();
			ruta.setOrigen(obj[12].toString());
			ruta.setDestino(obj[13].toString());
			ventaPasaje.setRuta(ruta);
			Usuario usuario = new Usuario();
			usuario.setApellidoPaterno(obj[14]==null?null:obj[14].toString());
			usuario.setApellidoMaterno(obj[15]==null?null:obj[15].toString());
			usuario.setNombre(obj[16]==null?null:obj[16].toString());
			ventaPasaje.setUsuario(usuario);
			Agencia agencia = new Agencia();
			agencia.setDenominacion(obj[17].toString());
			agencia.setNombreCorto(obj[20]!=null?obj[20].toString():obj[17].toString());
			ventaPasaje.setAgencia(agencia);
			ventaPasaje.setFechaInsercion(obj[18]==null?null:(Date)obj[18]);

			lstResult.add(ventaPasaje);
		}
		return lstResult;
	}

	/*
	 * (non-Javadoc)
	 * @see com.tepsa.sisvyr.model.dao.VentaPasajesDAO#buscarAvanceSemanalVentas(java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	public List<VentaPasaje> buscarAvanceSemanalVentas(String idOrigen,String idDestino, String tipoTransaction, String idServicio,String fechaDesde, String fechaHasta) throws Exception {
		if(idDestino.isEmpty())
			idDestino=null;
		if (idOrigen.isEmpty())
			idOrigen=null;
		if(idServicio.isEmpty())
			idServicio=null;

		String sql="SELECT P1.c_horpar,P1.c_origen,P1.c_destino,P1.c_denominacion as Servicio, "+
					       "P1.d_fecpar,decode(P2.Flag, '1', COUNT(*), 0) Vendidos, P1.Capbus "+
					"FROM "+
					       "(SELECT i.itinerario_id, i.ruta_idmayor, s.c_denominacion,NVL(s.n_numasipis1,0)+NVL(s.n_numasipis2,0) Capbus, i.d_fecpar, i.c_horpar, r.c_origen, r.c_destino, i.servicio_id "+
					        "FROM vrtitinerario i inner join vrmruta r on (i.ruta_idmayor = r.ruta_id) "+
					        "INNER JOIN vrmservicio s ON (i.servicio_id = s.servicio_id) "+
					        "WHERE  i.d_fecpar between to_date('"+fechaDesde+"','"+Constantes.DATE_FORMAT+"') AND to_date('"+fechaHasta+"','"+Constantes.DATE_FORMAT+"')  AND i.n_esanulado=0 ";
					        //Cuendo es entre provincias
					        if(idOrigen!=null && idDestino!=null &&  idOrigen.equals(idDestino)){
					        	sql+="AND r.localidad_idorigen!="+idOrigen+" "+
									 "AND r.localidad_iddestino!="+idDestino+" ";
					        }else{
					        	sql+="AND r.localidad_idorigen=NVL("+idOrigen+",localidad_idorigen) "+
								"AND r.localidad_iddestino=NVL("+idDestino+",localidad_iddestino) ";
					        }

						    sql+="AND i.servicio_id=NVL("+idServicio+",i.servicio_id) "+
					        ") P1 "+
					        "LEFT JOIN "+
					             "(SELECT '1' Flag, vt.itinerario_id, vt.d_fecpar, vt.c_horpar, vt.d_feclle, vt.c_horlle, vt.servicio_id, vt.ruta_id "+
					             "FROM vrtvenpas vt "+
					             "INNER JOIN "+
					                   "(SELECT max(v.venpas_id) venpas_id FROM vrtvenpas v " +
					                   		"INNER JOIN VRTITINERARIO i ON (i.itinerario_id=v.itinerario_id) " +
//					                   		"WHERE v.d_fecpar BETWEEN '"+fechaDesde+"' AND '"+fechaHasta+"' GROUP BY c_numcontrol) B "+
											"WHERE i.d_fecpar BETWEEN to_date('"+fechaDesde+"','"+Constantes.DATE_FORMAT+"') AND to_date('"+fechaHasta+"','"+Constantes.DATE_FORMAT+"') GROUP BY c_numcontrol) B "+
					                 "ON (vt.venpas_id = B.venpas_id) " +
					             "WHERE vt.c_tiptra='"+tipoTransaction.trim()+"' AND tipmov_id NOT IN ("+Constantes.ID_TIPMOV_ANULACION_SISTEMA+","+Constantes.ID_TIPMOV_DEVOLUCION+","+Constantes.ID_TIPMOV_ANULACION+") ) P2 "+
					         "ON  (P2.itinerario_id = P1.itinerario_id ) "+
					"GROUP BY P1.ruta_idmayor,P1.servicio_id,P1.c_origen,P1.c_destino,P1.c_denominacion,P1.d_fecpar, P1.c_horpar, Flag,P1.Capbus "+
					"ORDER BY P1.c_horpar,P1.c_origen,P1.c_destino,P1.c_denominacion";

			log.info(sql);

			List<?> result = getSession().createSQLQuery(sql).list();
			List<VentaPasaje> lstResult = new ArrayList<>();
			for(int i=0; i<result.size(); i++){
				Object[] obj = (Object[]) result.get(i);
				VentaPasaje ventaPasaje = new VentaPasaje();
				Ruta ruta = new Ruta();
				Servicio servicio = new Servicio();
				Itinerario itinerario= new Itinerario();

				ventaPasaje.setHoraPartida(obj[0].toString());

				ruta.setOrigen(obj[1].toString());
				ruta.setDestino(obj[2].toString());

				servicio.setDenominacion(obj[3].toString());
				servicio.setTotalAsientos(((BigDecimal)obj[6]).intValue());

//				itinerario.setId(((BigDecimal)obj[7]).longValue());
				itinerario.setId(null);

				ventaPasaje.setRuta(ruta);
				ventaPasaje.setServicio(servicio);
				ventaPasaje.setFechaPartida(((Date)obj[4]));
				ventaPasaje.setCantidadPax(((BigDecimal)obj[5]).intValue());
				ventaPasaje.setItinerario(itinerario);

				lstResult.add(ventaPasaje);
			}


		return lstResult;
	}

	/*
	 * (non-Javadoc)
	 * @see com.tepsa.sisvyr.model.dao.VentaPasajesDAO#buscarAvanceSemanalVentasColumns(java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	public List<VentaPasaje> buscarAvanceSemanalVentasColumns(String idOrigen,String idDestino, String tipoTransaction, String idServicio,String fechaDesde, String fechaHasta){
		String sql ="SELECT i.d_fecpar "+
					"FROM vrtitinerario i "+
					"INNER JOIN vrmruta r ON (r.ruta_id=i.Ruta_Idmayor)  "+
					"INNER JOIN vrmservicio s ON (i.servicio_id=s.servicio_id) "+
					"WHERE i.d_fecpar between to_date('"+fechaDesde+"','"+Constantes.DATE_FORMAT+"') AND to_date('"+fechaHasta+"','"+Constantes.DATE_FORMAT+"') "  +
					"AND i.n_esanulado=0 "+
					"AND i.c_estreg='A' ";
		if(!(idOrigen.isEmpty() || idOrigen==null))
			sql+="AND r.localidad_idorigen ='"+idOrigen+"' ";
		if(!(idDestino.isEmpty() || idDestino==null))
			sql+="AND r.localidad_iddestino ='"+idDestino+"' ";
		if(!(idServicio.isEmpty() || idOrigen==null))
			sql+="AND i.servicio_id ='"+idServicio+"' ";

		sql+="GROUP BY i.d_fecpar "+
			 "ORDER BY i.d_fecpar";

		log.info(sql);

		List<?> result = getSession().createSQLQuery(sql).list();
		List<VentaPasaje> lstResult = new ArrayList<>();
		for(int i=0; i<result.size(); i++){
			VentaPasaje ventaPasaje= new VentaPasaje();
			ventaPasaje.setFechaPartida((Date)result.get(i));

			lstResult.add(ventaPasaje);
		}

		return lstResult;
	}

	/*
	 * (non-Javadoc)
	 * @see com.tepsa.sisvyr.model.dao.VentaPasajesDAO#contarViajesValidos(java.lang.Long, java.lang.String, java.lang.String)
	 * Implementaci�n para Transmar
	 */
	@Override
	public int contarViajesValidos(Long idPasajero, String fechaInicial, String fechaFinal)throws Exception{

		if(fechaInicial.equals("")){

			//Si hay ultima fecha de canje se recupera para que sea la fecha de inicio
			String sql1="SELECT TO_CHAR(MAX(vgp.d_fecemi), 'dd/mm/yyyy') FECEMI FROM vrtvengrapax vgp WHERE vgp.pasajero_id=" + idPasajero;
			log.info(sql1);

			fechaInicial = ( (getSession().createSQLQuery(sql1).uniqueResult())!=null ? ((String)getSession().createSQLQuery(sql1).uniqueResult()).toString() : "");
			//
			//Si no hay se busca desde el inicio de los tiempos
			if(fechaInicial.equals(""))
				fechaInicial="01/01/2020";
		}

		String sql = "SELECT COUNT(vp.venpas_id) " +
					"FROM vrtvenpas vp " +
						"INNER JOIN (SELECT MAX(venpas_id)venpas_id, c_numcontrol FROM vrtvenpas  "+
									  "WHERE CAST (audfecins AS DATE) BETWEEN to_date('"+fechaInicial+"','dd/mm/yyyy HH24:MI:SS')  "+
									  "AND to_date('"+fechaFinal+"','dd/mm/yyyy HH24:MI:SS') AND c_estreg='A' " +
									  "AND pasajero_id="+idPasajero+" "+
							          "GROUP BY c_numcontrol) max_venta  " +
							 " ON max_venta.venpas_id=vp.venpas_id " +
					 "WHERE CAST (vp.audfecins AS DATE) BETWEEN to_date('"+fechaInicial+"','dd/mm/yyyy HH24:MI:SS')  "+
						"AND to_date('"+fechaFinal+"','dd/mm/yyyy HH24:MI:SS') " +
						"AND vp.tipmov_id IN ("+Constantes.ID_TIPMOV_EFECTIVO+","+Constantes.ID_TIPMOV_CREDITO+","+Constantes.ID_TIPMOV_FECHA_ABIERTA+","+Constantes.ID_TIPMOV_PREPAGADO+" ) " +
						"AND vp.c_tiptra="+Constantes.TIPO_OPERACION_VENTA+" "+
						"AND vp.tipcom_id IN("+Constantes.ID_TIPCOM_BOLETO_VIAJE+","+Constantes.ID_TIPCOM_BOLETA_VENTA+","+Constantes.ID_TIPCOM_FACTURA+") " +
						"AND vp.forpag_id <> 3 " +
						"AND vp.c_estreg='A' " +
						"AND vp.pasajero_id="+idPasajero;

//				"INNER JOIN (SELECT MAX(venpas_id) venpas_id, c_numcontrol FROM vrtvenpas " +
//					"WHERE pasajero_id="+idPasajero+" GROUP BY c_numcontrol) max_idventa ON max_idventa.venpas_id=vp.venpas_id " +
//				"WHERE vp.pasajero_id="+idPasajero+" AND " +
//				"CAST (vp.audfecins AS DATE) BETWEEN to_date('"+fechaInicial+"','dd/mm/yyyy HH24:MI:SS')  "+
//					"AND to_date('"+fechaFinal+"','dd/mm/yyyy HH24:MI:SS') " +
////				"vp.d_fecliq BETWEEN to_date('"+fechaInicial+"', 'dd/mm/yyyy') AND to_date('"+fechaFinal+"', 'dd/mm/yyyy') " +
//					"AND vp.tipmov_id IN ("+Constantes.ID_TIPMOV_EFECTIVO+","+Constantes.ID_TIPMOV_CREDITO+","+Constantes.ID_TIPMOV_FECHA_ABIERTA+") " +
//					"AND vp.c_tiptra="+Constantes.TIPO_OPERACION_VENTA+" "+
//					"AND vp.tipcom_id IN("+Constantes.ID_TIPCOM_BOLETO_VIAJE+","+Constantes.ID_TIPCOM_RECIBO_CAJA+") " +
//					"AND vp.c_estreg='A' ";


		log.info(sql);

		Integer result = ((BigDecimal)getSession().createSQLQuery(sql).uniqueResult()).intValue();
		return result;
	}


	/*
	 * (non-Javadoc)
	 * @see com.tepsa.sisvyr.model.dao.VentaPasajesDAO#buscarPorId(java.lang.Long)
	 */
	@Override
	public VentaPasaje buscarPorId(Long id) {
		return (VentaPasaje) super.findById(VentaPasaje.class, id);
	}

	/*
	 * (non-Javadoc)
	 * @see com.tepsa.sisvyr.model.dao.VentaPasajesDAO#transbordarPax(java.lang.Integer, java.lang.Long, com.tepsa.sisvyr.model.bean.Itinerario)
	 */
	@Override
	public void transbordarPax(Integer numeroAsiento, Long idVentaPasaje,Itinerario itinerario) throws Exception {
//		VentaPasaje ventaPasaje=buscarPorId(idVentaPasaje);

		/*Calcula la fecha de caducidad del boleto - 13/12/2016 - jabanto*/
		String fechaCaducidad=Constantes.FORMAT_DATE.format(itinerario.getFechaPartida())+" "+itinerario.getHoraPartida();
		Date dateCaducidad=Constantes.FORMAT_LONG.parse(fechaCaducidad);
		//Obtiene el numero del piso del asiento a transbordar.
		String sql = "SELECT mp.N_NUMPIS, mp.MAPABUS_ID  "
				   + "FROM VRTMAPABUS mp "
				   + "WHERE mp.SERVICIO_ID = "+ itinerario.getServicio().getId() +" "
				   + "AND mp.N_NUMASI = "+ numeroAsiento +" AND mp.C_ESTREG = '"+ Constantes.VALUE_ACTIVO +"' ";
		log.info(sql);
		List<?> result = getSession().createSQLQuery(sql).list();
		int numeroPiso = 0;
		for (Object element : result) {
			Object[] obj = (Object[]) element;
			numeroPiso = ((BigDecimal)obj[0]).intValue();
		}


		sql="UPDATE vrtvenpas v SET v.n_numasiento="+numeroAsiento+", v.itinerario_id="+itinerario.getId()+", " +
				  "v.servicio_id="+itinerario.getServicio().getId()+", "+
		     	  "v.d_fecpar=to_date('"+Constantes.FORMAT_DATE.format(itinerario.getFechaPartida())+"','dd/MM/yyyy'), v.c_horpar='"+itinerario.getHoraPartida()+"', " +
		     	  "v.d_feclle=to_date('"+Constantes.FORMAT_DATE.format(itinerario.getFechaLlegada())+"','dd/MM/yyyy'), v.c_horlle='"+itinerario.getHoraLlegada()+"', "+
		     	  "v.d_feccad=to_date('"+Constantes.FORMAT_LONG.format(dateCaducidad)+ "','dd/MM/yyyy HH24:mi'), "+
		     	  "v.N_NUMPISO= "+ numeroPiso +" "+
//							  "v.agencia_idpartida="+itinerario.getAgenciaPartida().getId()+", v.agencia_idllegada="+itinerario.getAgenciaLlegada().getId()+" " +
//							  "v.agencia_idpartida="+ventaPasaje.getAgenciaPartida().getId()+", v.agencia_idllegada="+ventaPasaje.getAgenciaLlegada().getId()+" " +
 	        "WHERE v.venpas_id="+idVentaPasaje;
//						"WHERE v.itinerario_id="+ventaPasaje.getItinerario().getId()+" AND v.n_numasiento="+ventaPasaje.getNumeroAsiento()+ " ";
						//"AND v.tipmov_id NOT IN ("+Constantes.ID_TIPMOV_ANULACION_SISTEMA+","+Constantes.ID_TIPMOV_DEVOLUCION+","+Constantes.ID_TIPMOV_ANULACION+")  ";
			    //"WHERE v.venpas_id="+idVentaPasaje;

			log.info(sql);
			getSession().createSQLQuery(sql).executeUpdate();
	}

	/*
	 * (non-Javadoc)
	 * @see com.tepsa.sisvyr.model.dao.VentaPasajesDAO#buscarVentasPax(java.lang.String, java.lang.String, java.lang.Long)
	 */
	@Override
	public ArrayList<VentaPasaje> buscarVentasPax(String fechaInicial, String fechaFinal,Long pasajeroID){
		String sql="SELECT v.c_numboleto as Boleto, s.c_denominacion as Servicio,  "+ //0-1
					       "r.c_origen as Origen, r.c_destino as Destino, v.d_fecpar as FechaViaje,  "+ //2-4
					       "tfp.c_denominacion as TipoFormaPago, v.n_numasiento as Asiento, v.n_imppag as ImportePagado,  "+ //5-7
					       "v.d_fecliq as FechaEmision,u.c_apepat as ApPaterno, u.c_apemat as ApMaterno, u.c_nombre as Nombre,  "+ //8-11
					       "c.c_numdoc as Ruc, c.c_razsoc as RazonSocial, tm.c_denominacion as TipoMovimiento  " +  //12-14
					       ",fp.forpag_id, fp.c_denominacion "+//15-16
					"FROM vrtvenpas v  " +
						"INNER JOIN (SELECT MAX(venpas_id)venpas_id, c_numcontrol "+
							          "FROM vrtvenpas  "+
									  "WHERE CAST (audfecins AS DATE) BETWEEN to_date('"+fechaInicial+"','dd/mm/yyyy HH24:MI:SS')  "+
									  "AND to_date('"+fechaFinal+"','dd/mm/yyyy HH24:MI:SS') AND c_estreg='A' "+
									  "AND pasajero_id="+pasajeroID+" "+
							          "GROUP BY c_numcontrol) max_venta  " +
						     " ON max_venta.venpas_id=v.venpas_id " +
						"INNER JOIN vrmservicio s ON (s.servicio_id=v.servicio_id)  "+
						"INNER JOIN vrmruta r ON (r.ruta_id=v.ruta_id)  "+
						"INNER JOIN vrmtipforpag tfp ON (tfp.tipforpag_id=v.tipforpag_id)  "+
						"INNER JOIN vrmusuario u ON (u.usuario_id=v.usuario_id)  " +
						"INNER JOIN vrmtipmov tm ON (tm.tipmov_id=v.tipmov_id) " +
						"INNER JOIN vrmforpag fp ON (fp.forpag_id=v.forpag_id) "+
						"LEFT OUTER JOIN vrmcliente c ON (c.cliente_id=v.cliente_id)  " +
					"WHERE CAST (v.audfecins AS DATE) BETWEEN to_date('"+fechaInicial+"','dd/mm/yyyy HH24:MI:SS')  "+
						"AND to_date('"+fechaFinal+"','dd/mm/yyyy HH24:MI:SS') " +
						"AND v.tipmov_id IN ("+Constantes.ID_TIPMOV_EFECTIVO+","+Constantes.ID_TIPMOV_CREDITO+","+Constantes.ID_TIPMOV_FECHA_ABIERTA+","+Constantes.ID_TIPMOV_PREPAGADO+")"+
						"AND v.c_tiptra="+Constantes.TIPO_OPERACION_VENTA+" "+
						"AND v.tipcom_id IN("+Constantes.ID_TIPCOM_BOLETO_VIAJE+","+Constantes.ID_TIPCOM_BOLETA_VENTA+","+Constantes.ID_TIPCOM_FACTURA+") " +
						"AND v.c_estreg='A' " +
						"AND v.pasajero_id="+pasajeroID +" "+
						"ORDER BY v.audfecins desc" ;

		log.info(sql);
		List<?> result = getSession().createSQLQuery(sql).list();
		ArrayList<VentaPasaje> lstResult = new ArrayList<>();

		for (Object element : result) {
			Object[] obj = (Object[]) element;

			VentaPasaje ventaPasaje=new VentaPasaje();
			Servicio servicio= new Servicio();
			Ruta ruta= new Ruta();
			TipoFormaPago tipoFormaPago=new TipoFormaPago();
			Usuario usuario=new Usuario();
			Cliente cliente=new Cliente();
			TipoMovimiento tipoMovimiento=new TipoMovimiento();
			FormaPago formaPago= new FormaPago();

			servicio.setDenominacion(obj[1].toString());

			tipoFormaPago.setDenominacion(obj[5].toString());

			usuario.setApellidoPaterno(obj[9].toString());
			usuario.setApellidoMaterno(obj[10]!=null?obj[10].toString(): "");
			usuario.setNombre(obj[11].toString());

			cliente.setNumeroDocumento(obj[12]!=null? obj[12].toString(): "");
			cliente.setRazonSocial(obj[13]!=null? obj[13].toString(): "");

			ruta.setOrigen(obj[2].toString());
			ruta.setDestino(obj[3].toString());

			tipoMovimiento.setDenominacion(obj[14].toString());

			formaPago.setId(((BigDecimal)obj[15]).intValue());
			formaPago.setDenominacion(obj[16].toString());

			ventaPasaje.setNumeroBoleto(obj[0]!=null?obj[0].toString():"");
			ventaPasaje.setFechaPartida(obj[4] !=null? (Date)obj[4]: null);
			ventaPasaje.setNumeroAsiento(obj[6]!=null? ((BigDecimal)obj[6]).intValue(): null);
			ventaPasaje.setImportePagado(((BigDecimal)obj[7]).doubleValue());
			ventaPasaje.setFechaLiquidacion((Date) obj[8]);
			ventaPasaje.setServicio(servicio);
			ventaPasaje.setTipoFormaPago(tipoFormaPago);
			ventaPasaje.setUsuario(usuario);
			ventaPasaje.setCliente(cliente);
			ventaPasaje.setRuta(ruta);
			ventaPasaje.setTipoMovimiento(tipoMovimiento);
			ventaPasaje.setFormaPago(formaPago);

			lstResult.add(ventaPasaje);
		}

		return lstResult;
	}

	/*
	 * (non-Javadoc)
	 * @see com.tepsa.sisvyr.model.dao.VentaPasajesDAO#validaOcupabilidad(java.lang.Long, java.lang.Integer, java.lang.String, java.lang.Integer)
	 */
	@Override
	public List<VentaPasaje> validaOcupabilidad(Long idItinerario, Integer idRuta, String numeroAsientos, Integer numeroPiso ){
		String sql = "SELECT vp.venpas_id, vp.n_numasiento  " +
				"FROM vrtvenpas vp " +
				"INNER JOIN (SELECT MAX(venpas_id)venpas_id, c_numcontrol " +
					"FROM vrtvenpas " +
					"WHERE itinerario_id="+idItinerario+" AND ruta_id="+idRuta+" GROUP BY c_numcontrol) max_venta " +
						"ON max_venta.venpas_id=vp.venpas_id " +
				"WHERE vp.c_tiptra IN ('"+Constantes.TIPO_OPERACION_VENTA+"', '"+Constantes.TIPO_OPERACION_RESERVA+"') AND " +
				"vp.n_numasiento in ("+numeroAsientos+") AND vp.n_numpiso="+(numeroPiso-1)+" AND vp.c_estreg='"+Constantes.VALUE_ACTIVO+"' " +
				"AND vp.tipmov_id NOT IN ("+Constantes.ID_TIPMOV_ANULACION_SISTEMA+","+Constantes.ID_TIPMOV_DEVOLUCION+","+Constantes.ID_TIPMOV_ANULACION+") " +
				"ORDER BY vp.n_numasiento ";

		log.info(sql);
		List<?> result = getSession().createSQLQuery(sql).list();
		List<VentaPasaje>listResul= new ArrayList<>();
		for(int i=0; i<result.size(); i++){
			Object[] obj = (Object[]) result.get(i);
			VentaPasaje ventaPasaje= new VentaPasaje();
			ventaPasaje.setId(((BigDecimal)obj[0]).longValue());
			ventaPasaje.setNumeroAsiento(((BigDecimal)obj[1]).intValue());
			listResul.add(ventaPasaje);
		}
		return listResul;
	}

	/*
	 * (non-Javadoc)
	 * @see com.tepsa.sisvyr.model.dao.VentaPasajesDAO#validaBoletos_ServicioEspecial(java.lang.String, java.lang.String)
	 */
	@Override
	public Integer validaBoletos_ServicioEspecial(String boletoInicial, String boletoFinal){
		String sql="SELECT COUNT(*) AS Cantidad FROM vrtvenpas v WHERE v.c_numboleto BETWEEN '"+boletoInicial+"' and '"+boletoFinal+"'";
		log.info(sql);
		List<?> result = getSession().createSQLQuery(sql).list();
		return  ((BigDecimal)result.get(0)).intValue();
	}


	/*
	 * (non-Javadoc)
	 * @see com.tepsa.sisvyr.model.dao.VentaPasajesDAO#ultimoCorrelativosEmitidos(java.lang.String, java.lang.String, java.lang.Integer)
	 */
	@Override
	public List<VentaPasaje> buscarUltimoCorrelativoEmitido(String fechaUltimoEnvio, String serie, Integer idComprobante,Integer idAgencia) throws Exception{
		String fechaActual=Constantes.FORMAT_DATE.format(Constantes.FORMAT_DATE.parse(new MyTime().dateServer()));
		String sql="SELECT a.c_nomcor as Agencia "+
					       ",tc.c_denominacion as TipoComprobante "+
					       ",SUBSTR(MAX(vp.c_numboleto),0,INSTR(MAX(vp.c_numboleto),'-')-1) AS Serie "+
					       ",SUBSTR(MAX(vp.c_numboleto),INSTR((MAX(vp.c_numboleto)),'-')+1, LENGTH(MAX(vp.c_numboleto))) AS UtimoEmitido "+
					       ",COUNT(vp.c_numboleto) AS NroBoletosEmitidos "+
					       ",(TO_DATE('"+fechaActual+"','DD/MM//YYYY'))-(TO_DATE('01/04/2013','DD/MM/YYYY'))+1 as DiasTranscurridos "+
					       ",CAST(COUNT(vp.c_numboleto)/ ((TO_DATE('"+fechaActual+"','DD/MM/YYYY'))-(TO_DATE('01/04/2013','DD/MM/YYYY'))+1)AS NUMERIC(10,2))  as PromedioXdia  " +
					       ",'0080000' AS UltimoEnviado  " + //**************PENDIENTE POR IMPLEMENTAR
					       ",'01/04/2013' AS FechaUltmimoEnvio "+//**************PENDIENTE POR IMPLEMENTAR
					"FROM vrtvenpas vp "+
					"INNER JOIN (SELECT MAX(venpas_id)venpas_id, c_numcontrol "+
								"FROM vrtvenpas  "+
								"WHERE c_numboleto IS NOT NULL AND d_fecliq BETWEEN to_date('"+fechaUltimoEnvio+"','"+Constantes.DATE_FORMAT+"') AND to_date('"+fechaActual+"','"+Constantes.DATE_FORMAT+"')  "+
					            "GROUP BY c_numcontrol) max_venta  "+
										"ON max_venta.venpas_id=vp.venpas_id "+
					"INNER JOIN vrmagencia a ON (a.agencia_id=vp.agencia_id) "+
					"INNER JOIN vrmtipcom tc ON (tc.tipcom_id=vp.tipcom_id) "+
					"WHERE vp.c_numboleto IS NOT NULL AND vp.d_fecliq BETWEEN to_date('"+fechaUltimoEnvio+"','"+Constantes.DATE_FORMAT+"') AND to_date('"+fechaActual+"','"+Constantes.DATE_FORMAT+"')  ";
		if(serie!=null)
			sql+="AND SUBSTR(vp.c_numboleto,0,INSTR((vp.c_numboleto),'-')-1)='"+serie+"' ";
		if(idComprobante!=null)
			sql+="AND vp.tipcom_id="+idComprobante;
		if(idAgencia!=null)
			sql+="AND vp.agencia_id="+idAgencia;

		sql+="GROUP BY a.c_nomcor, tc.c_denominacion, SUBSTR(vp.c_numboleto,0,INSTR((vp.c_numboleto),'-')-1) ";
		sql+="ORDER BY a.c_nomcor, tc.c_denominacion, SUBSTR(vp.c_numboleto,0,INSTR((vp.c_numboleto),'-')-1)";

		List<?> result = getSession().createSQLQuery(sql).list();
		List<VentaPasaje>listResul= new ArrayList<>();
		for(int i=0; i<result.size(); i++){
			Object[] obj = (Object[]) result.get(i);
			VentaPasaje ventaPasaje= new VentaPasaje();
			Agencia agencia= new Agencia();
			TipoComprobante tipoComprobante= new TipoComprobante();
			agencia.setNombreCorto(obj[0].toString());
			tipoComprobante.setDenominacion(obj[1].toString());
			ventaPasaje.setAgencia(agencia);
			ventaPasaje.setTipoComprobante(tipoComprobante);
			ventaPasaje.setNumeroSerie(obj[2].toString());
			ventaPasaje.setUltimoEmitido(obj[3].toString());
			ventaPasaje.setNumeroBoletoEmitidos(((BigDecimal)obj[4]).intValue());
			ventaPasaje.setDiasTranscurridos(((BigDecimal)obj[5]).intValue());
			ventaPasaje.setPromedioXdia(((BigDecimal)obj[6]).doubleValue());
			ventaPasaje.setUltimoEnviadoXAbastecimientos(obj[7]!=null?obj[7].toString(): "");
			ventaPasaje.setFechaUltimoEnvioXAbastecimientos(obj[8]!=null?obj[8].toString(): "");

			listResul.add(ventaPasaje);

		}
		return listResul;
	}


	/*
	 * (non-Javadoc)
	 * @see com.tepsa.sisvyr.model.dao.VentaPasajesDAO#correlativosFaltantesX(java.lang.String, java.lang.String, java.lang.Integer, java.lang.Integer)
	 */
	@Override
	public List<VentaPasaje> correlativosFaltantesX(String fechaInicio, String serie, Integer idComprobante,Integer idAgencia, String fechaFin, Integer idUsuario) throws Exception{
//		String fechaActual=Constantes.FORMAT_DATE.format(Constantes.FORMAT_DATE.parse(new MyTime().dateServer()));

		String sql="SELECT a.c_nomcor as Agencia "+
					       ",tc.c_denominacion as TipoComprobante "+
					       ",SUBSTR((vp.c_numboleto),0,INSTR((vp.c_numboleto),'-')-1) AS Serie "+
					       ",SUBSTR((vp.c_numboleto),INSTR(((vp.c_numboleto)),'-')+1, LENGTH((vp.c_numboleto))) AS UtimoEmitido  " +
					"FROM vrtvenpas vp "+
					"INNER JOIN (SELECT MAX(venpas_id)venpas_id, c_numcontrol "+
					          "FROM vrtvenpas  "+
					          "WHERE c_numboleto IS NOT NULL AND d_fecliq BETWEEN to_date('"+fechaInicio+"','"+Constantes.DATE_FORMAT+"') AND to_date('"+fechaFin+"','"+Constantes.DATE_FORMAT+"') "+
					          "GROUP BY c_numcontrol) max_venta  "+
					          "ON max_venta.venpas_id=vp.venpas_id "+
					"INNER JOIN vrmagencia a ON (a.agencia_id=vp.agencia_id) "+
					"INNER JOIN vrmtipcom tc ON (tc.tipcom_id=vp.tipcom_id) "+
					"WHERE vp.c_numboleto IS NOT NULL AND vp.d_fecliq BETWEEN to_date('"+fechaInicio+"','"+Constantes.DATE_FORMAT+"') AND to_date('"+fechaFin+"','"+Constantes.DATE_FORMAT+"') AND vp.c_estreg='"+Constantes.VALUE_ACTIVO+"' ";
		if(serie!=null)
			sql+=" AND SUBSTR(vp.c_numboleto,0,INSTR((vp.c_numboleto),'-')-1)='"+serie+"' ";
		if(idComprobante!=null)
			sql+=" AND vp.tipcom_id="+idComprobante+" ";
		if(idAgencia!=null)
			sql+=" AND vp.agencia_id="+idAgencia+" ";
		if(idUsuario!=null)
			sql+=" AND vp.usuario_id="+idUsuario+" ";
		sql+=" ORDER BY a.c_nomcor, tc.c_denominacion, SUBSTR(vp.c_numboleto,0,INSTR((vp.c_numboleto),'-')-1), " +
				"SUBSTR((vp.c_numboleto),INSTR(((vp.c_numboleto)),'-')+1, LENGTH((vp.c_numboleto)))";

		log.info(sql);

		List<?> result = getSession().createSQLQuery(sql).list();
		List<VentaPasaje>listResul= new ArrayList<>();
		for(int i=0; i<result.size(); i++){
			Object[] obj = (Object[]) result.get(i);
			VentaPasaje ventaPasaje= new VentaPasaje();
			Agencia agencia= new Agencia();
			TipoComprobante tipoComprobante= new TipoComprobante();

			agencia.setNombreCorto(obj[0].toString());
			tipoComprobante.setDenominacion(obj[1].toString());
			ventaPasaje.setAgencia(agencia);
			ventaPasaje.setTipoComprobante(tipoComprobante);
			ventaPasaje.setNumeroSerie(obj[2].toString());
			ventaPasaje.setNumeroBoleto(obj[3].toString());

			listResul.add(ventaPasaje);
		}
		return listResul;

	}

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.model.dao.VentaPasajesDAO#buscaTotalVentasEfectivo(java.lang.Integer, java.lang.Integer, java.lang.String)
	 */
	@Override
	public Double buscaTotalVentasEfectivo(Integer idUsuario,Integer idAgencia, String fecha) throws Exception {
		// TODO Auto-generated method stub
		String sql="SELECT SUM(vp.n_imppag)As totalVentaEfectivo "+
					"FROM VRTVENPAS vp "+
					"WHERE vp.usuario_id="+idUsuario+" AND vp.agencia_id="+idAgencia+" AND to_char(vp.d_fecliq,'dd/mm/yyyy')='"+fecha+"'  "+
					"AND vp.tipmov_id NOT IN ("+Constantes.ID_TIPMOV_ANULACION_SISTEMA+","+Constantes.ID_TIPMOV_DEVOLUCION+","+Constantes.ID_TIPMOV_ANULACION+")  " +
					"AND vp.forpag_id="+Constantes.ID_FORPAG_CONTADO+" AND vp.tipforpag_id="+Constantes.ID_TIPFORPAG_EFECTIVO+"   " +
					" AND vp.c_estreg='"+Constantes.VALUE_ACTIVO+" '";

		log.info(sql);
		Object object=getSession().createSQLQuery(sql).uniqueResult();

		Double totalVentasEfecivo=0.00;
		if(object!=null){
			totalVentasEfecivo=((BigDecimal)object).doubleValue();
		}

//		Double totalVentasEfecivo=((BigDecimal) getSession().createSQLQuery(sql).uniqueResult()).doubleValue();
//		if(totalVentasEfecivo==null)
//			totalVentasEfecivo=.00;

		return totalVentasEfecivo;
	}

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.model.dao.VentaPasajesDAO#buscarPorX(java.util.TreeMap, java.util.List)
	 */
	@Override
	public ArrayList<VentaPasaje> buscarPorX(TreeMap<String, Object> criteriosBusqueda,List<String> criteriosOrdenar) {
		return (ArrayList<VentaPasaje>) super.findByX(VentaPasaje.class, criteriosBusqueda, criteriosOrdenar);
	}
//
//	/*
//	 * (non-Javadoc)
//	 * @see com.tepsa.sisvyr.model.dao.VentaPasajesDAO#buscarPorIdReferenciaIdaRetorno(java.lang.Long)
//	 */
//	@Override
//	public ArrayList<VentaPasaje> buscarPorIdReferenciaIdaRetorno(Long identificador)throws Exception{
//		String sql = "FROM VentaPasaje vp WHERE vp.identificadorIdaRetorno="+identificador;
//
//	}

	/*
	 * (non-Javadoc)
	 * @see com.tepsa.sisvyr.model.dao.VentaPasajesDAO#buscarDetalladoVentas(java.lang.Integer, java.lang.Integer, java.lang.String, java.lang.String, java.lang.Integer)
	 */
	@Override
	public List<VentaPasaje> buscarDetalladoVentas(Integer idAgencia, Integer idUsuario, String fechaInicial, String fechaFinal, Integer criterio)throws Exception{
		String where ="";
		String tipoVenta = "";
		String sql = "";StringBuilder str = new StringBuilder();
		switch (criterio) {
		case -1: //Todos -->Rpt Detalle Ventas Liquidacion
		case 0:	//Todos
			//ANULADOS
			sql = "SELECT * FROM (SELECT vp.venpas_id, vp.c_numcontrol NroControl, vp.c_numboleto NroBoleto, vp.c_numbolant NroBoletoRef, " +
					"p.c_apepat ApePat, p.c_apemat ApeMat, p.c_nombre Nombre, vp.audfecins FechaActualizacion, vp.n_tarifa MontoBase, " +
					"vp.n_recargo Recargo, vp.n_descuento Descuento, vp.n_acuenta ACuenta, vp.n_penalidad Penalidad, vp.n_imppag NetoPagado, " +
					"fp.forpag_id, fp.c_denominacion FormaPago, tfp.c_denominacion TipoFormaPago, otc.c_denominacion TipoTarjeta, tm.c_denominacion, " +
					"'ANULADO' TIPOVENTA , u.c_apepat apepatusu, u.c_nombre nombreusu, u.c_login, vp.d_fecliq,ag.c_denominacion as agencia"
					+ ",vp.tipmov_id, vp.liquidacion_id, vp.tipforpag_id,vp.tipcom_id, vp.servicio_id, vp.c_rucclicre, vp.canven_id "
					+ ",NVL(vp.n_imppagdif,0) imppagdif, vp.n_imppagequ, vp.tipmon_id, vp.n_tipcam "
					+ ",r.c_origen, r.c_destino, tcp.tipcom_id tipoMovimientoId, tcp.c_denominacion tipoComprobante " //39
					+ ",cl.c_numdoc ruc, cl.c_razsoc cliente, p.c_numdoc NUMDOC   " //41
				   +"FROM vrtvenpas vp "+
					"INNER JOIN vrmpasajero p ON p.pasajero_id=vp.pasajero_id " +
					"INNER JOIN vrmforpag fp ON fp.forpag_id=vp.forpag_id " +
					"INNER JOIN vrmtipforpag tfp ON tfp.tipforpag_id=vp.tipforpag_id " +
					"INNER JOIN vrmtipmov tm ON tm.tipmov_id=vp.tipmov_id " +
					"INNER JOIN vrmusuario u ON u.usuario_id=vp.usuario_id " +
					"INNER JOIN vrmruta r ON (r.ruta_id=vp.ruta_id)  "+
					"LEFT JOIN vrmtarcre tc ON tc.tarcre_id=vp.tarcre_id " +
					"LEFT JOIN vrmopetarcre otc ON otc.opetarcre_id=tc.opetarcre_id " +
					"LEFT JOIN vrmcliente cl ON (cl.cliente_id=vp.cliente_id) "+
					"INNER JOIN vrmagencia ag ON (ag.agencia_id=vp.agencia_id) "+
					"INNER JOIN vrmtipcom tcp ON (tcp.tipcom_id=vp.tipcom_id) "+
					"WHERE vp.agencia_id="+idAgencia+" AND vp.usuario_id= NVL("+idUsuario+",vp.usuario_id) AND vp.d_fecliq BETWEEN to_date('"+fechaInicial+"','dd/mm/yyyy') AND " +
							"to_date('"+fechaFinal+"','dd/mm/yyyy') AND vp.tipmov_id="+Constantes.ID_TIPMOV_ANULACION+" AND vp.n_imppag=0 ";

			sql = sql + " UNION ALL ";
			//DEVOLUCIONES
			sql = sql + "SELECT vp.venpas_id, vp.c_numcontrol NroControl, vp.c_numboleto NroBoleto, vp.c_numbolant NroBoletoRef, " +
					"p.c_apepat ApePat, p.c_apemat ApeMat, p.c_nombre Nombre, vp.audfecins FechaActualizacion, vp.n_tarifa MontoBase, " +
					"vp.n_recargo Recargo, vp.n_descuento Descuento, vp.n_acuenta ACuenta, vp.n_penalidad Penalidad, vp.n_imppag NetoPagado, " +
					"fp.forpag_id, fp.c_denominacion FormaPago, tfp.c_denominacion TipoFormaPago, otc.c_denominacion TipoTarjeta, tm.c_denominacion, " +
					"DECODE(vp.n_penalidad, 0,'DEV.100%','DEV.80%') TIPOVENTA, u.c_apepat apepatusu, u.c_nombre nombreusu,u.c_login,vp.d_fecliq,ag.c_denominacion as agencia " +
					 ",vp.tipmov_id, vp.liquidacion_id, vp.tipforpag_id,vp.tipcom_id, vp.servicio_id, vp.c_rucclicre, vp.canven_id " +
				     ",NVL(vp.n_imppagdif,0) imppagdif, vp.n_imppagequ, vp.tipmon_id, vp.n_tipcam "+
				     ",r.c_origen, r.c_destino, tcp.tipcom_id, tcp.c_denominacion tipoComprobante  "+
				     ",cl.c_numdoc ruc, cl.c_razsoc cliente, p.c_numdoc NUMDOC  " + //41
					"FROM vrtvenpas vp " +
					"INNER JOIN vrmpasajero p ON p.pasajero_id=vp.pasajero_id " +
					"INNER JOIN vrmforpag fp ON fp.forpag_id=vp.forpag_id " +
					"INNER JOIN vrmtipforpag tfp ON tfp.tipforpag_id=vp.tipforpag_id " +
					"INNER JOIN vrmtipmov tm ON tm.tipmov_id=vp.tipmov_id " +
					"INNER JOIN vrmusuario u ON u.usuario_id=vp.usuario_id " +
					"INNER JOIN vrmruta r ON (r.ruta_id=vp.ruta_id)  "+
					"LEFT JOIN vrmtarcre tc ON tc.tarcre_id=vp.tarcre_id " +
					"LEFT JOIN vrmopetarcre otc ON otc.opetarcre_id=tc.opetarcre_id " +
					"LEFT JOIN vrmcliente cl ON (cl.cliente_id=vp.cliente_id) "+
					"INNER JOIN vrmagencia ag ON (ag.agencia_id=vp.agencia_id) "+
					"INNER JOIN vrmtipcom tcp ON (tcp.tipcom_id=vp.tipcom_id) "+
					"WHERE vp.agencia_id="+idAgencia+" AND vp.usuario_id=NVL("+idUsuario+",vp.usuario_id) AND vp.d_fecliq BETWEEN to_date('"+fechaInicial+"','dd/mm/yyyy') AND " +
							"to_date('"+fechaFinal+"','dd/mm/yyyy') AND tm.tipmov_id="+Constantes.ID_TIPMOV_DEVOLUCION+" ";

			sql = sql + " UNION ALL ";
			//CORTESIAS
			sql = sql + "SELECT vp.venpas_id, vp.c_numcontrol NroControl, vp.c_numboleto NroBoleto, vp.c_numbolant NroBoletoRef, " +
					"p.c_apepat ApePat, p.c_apemat ApeMat, p.c_nombre Nombre, vp.audfecins FechaActualizacion, vp.n_tarifa MontoBase, " +
					"vp.n_recargo Recargo, vp.n_descuento Descuento, vp.n_acuenta ACuenta, vp.n_penalidad Penalidad, vp.n_imppag NetoPagado, " +
					"fp.forpag_id, fp.c_denominacion FormaPago, tfp.c_denominacion TipoFormaPago, otc.c_denominacion TipoTarjeta, tm.c_denominacion, " +
					"DECODE(tfp.tipforpag_id, 3, 'CORTXCUMP', 5, 'CORTXPUNT', 'CORT')TIPOVENTA, u.c_apepat apepatusu, u.c_nombre nombreusu,u.c_login,vp.d_fecliq,ag.c_denominacion as agencia " +
					",vp.tipmov_id, vp.liquidacion_id, vp.tipforpag_id,vp.tipcom_id, vp.servicio_id, vp.c_rucclicre, vp.canven_id " +
					",NVL(vp.n_imppagdif,0) imppagdif, vp.n_imppagequ, vp.tipmon_id, vp.n_tipcam "+
					",r.c_origen, r.c_destino, tcp.tipcom_id, tcp.c_denominacion tipoComprobante  "+
					",cl.c_numdoc ruc, cl.c_razsoc cliente, p.c_numdoc NUMDOC  " + //41
					"FROM vrtvenpas vp " +
					"INNER JOIN vrmpasajero p ON p.pasajero_id=vp.pasajero_id " +
					"INNER JOIN vrmforpag fp ON fp.forpag_id=vp.forpag_id " +
					"INNER JOIN vrmtipforpag tfp ON tfp.tipforpag_id=vp.tipforpag_id " +
					"INNER JOIN vrmtipmov tm ON tm.tipmov_id=vp.tipmov_id " +
					"INNER JOIN vrmusuario u ON u.usuario_id=vp.usuario_id " +
					"INNER JOIN vrmruta r ON (r.ruta_id=vp.ruta_id)  "+
					"LEFT JOIN vrmtarcre tc ON tc.tarcre_id=vp.tarcre_id " +
					"LEFT JOIN vrmopetarcre otc ON otc.opetarcre_id=tc.opetarcre_id " +
					"LEFT JOIN vrmcliente cl ON (cl.cliente_id=vp.cliente_id) "+
					"INNER JOIN vrmagencia ag ON (ag.agencia_id=vp.agencia_id) "+
					"INNER JOIN vrmtipcom tcp ON (tcp.tipcom_id=vp.tipcom_id) "+
					"INNER JOIN (SELECT MAX(venpas_id)venpas_id, c_numcontrol FROM vrtvenpas GROUP BY c_numcontrol) max_vta ON max_vta.venpas_id=vp.venpas_id " +
					"WHERE vp.agencia_id="+idAgencia+" AND vp.usuario_id=NVL("+idUsuario+",vp.usuario_id) AND vp.d_fecliq BETWEEN to_date('"+fechaInicial+"','dd/mm/yyyy') AND " +
							"to_date('"+fechaFinal+"','dd/mm/yyyy') AND fp.forpag_id=3 AND vp.c_numboleto IS NOT NULL AND "+
							"vp.tipmov_id NOT IN("+Constantes.ID_TIPMOV_ANULACION_SISTEMA+","+Constantes.ID_TIPMOV_DEVOLUCION+","+Constantes.ID_TIPMOV_ANULACION+") ";

			sql = sql + " UNION ALL ";
			//CREDITO
			sql = sql + "SELECT vp.venpas_id, vp.c_numcontrol NroControl, vp.c_numboleto NroBoleto, vp.c_numbolant NroBoletoRef, " +
					"p.c_apepat ApePat, p.c_apemat ApeMat, p.c_nombre Nombre, vp.audfecins FechaActualizacion, vp.n_tarifa MontoBase, " +
					"vp.n_recargo Recargo, vp.n_descuento Descuento, vp.n_acuenta ACuenta, vp.n_penalidad Penalidad, vp.n_imppag NetoPagado, " +
					"fp.forpag_id, fp.c_denominacion FormaPago, tfp.c_denominacion TipoFormaPago, otc.c_denominacion TipoTarjeta, tm.c_denominacion, " +
					"'CREDITO' TIPOVENTA, u.c_apepat apepatusu, u.c_nombre nombreusu,u.c_login,vp.d_fecliq,ag.c_denominacion as agencia " +
					",vp.tipmov_id, vp.liquidacion_id, vp.tipforpag_id,vp.tipcom_id, vp.servicio_id, vp.c_rucclicre, vp.canven_id " +
					",NVL(vp.n_imppagdif,0) imppagdif, vp.n_imppagequ, vp.tipmon_id, vp.n_tipcam "+
					",r.c_origen, r.c_destino, tcp.tipcom_id, tcp.c_denominacion tipoComprobante  "+
					",cl.c_numdoc ruc, cl.c_razsoc cliente, p.c_numdoc NUMDOC  " + //41
					"FROM vrtvenpas vp " +
					"INNER JOIN vrmpasajero p ON p.pasajero_id=vp.pasajero_id " +
					"INNER JOIN vrmforpag fp ON fp.forpag_id=vp.forpag_id " +
					"INNER JOIN vrmtipforpag tfp ON tfp.tipforpag_id=vp.tipforpag_id " +
					"INNER JOIN vrmtipmov tm ON tm.tipmov_id=vp.tipmov_id " +
					"INNER JOIN vrmusuario u ON u.usuario_id=vp.usuario_id " +
					"INNER JOIN vrmruta r ON (r.ruta_id=vp.ruta_id)  "+
					"LEFT JOIN vrmtarcre tc ON tc.tarcre_id=vp.tarcre_id " +
					"LEFT JOIN vrmopetarcre otc ON otc.opetarcre_id=tc.opetarcre_id " +
					"LEFT JOIN vrmcliente cl ON (cl.cliente_id=vp.cliente_id) "+
					"INNER JOIN vrmagencia ag ON (ag.agencia_id=vp.agencia_id) "+
					"INNER JOIN vrmtipcom tcp ON (tcp.tipcom_id=vp.tipcom_id) "+
//					"INNER JOIN (SELECT MAX(venpas_id)venpas_id, c_numcontrol FROM vrtvenpas GROUP BY c_numcontrol) max_vta ON max_vta.venpas_id=vp.venpas_id " +
					"WHERE vp.agencia_id="+idAgencia+" AND vp.usuario_id=NVL("+idUsuario+",vp.usuario_id) AND vp.d_fecliq BETWEEN to_date('"+fechaInicial+"','dd/mm/yyyy') AND " +
							"to_date('"+fechaFinal+"','dd/mm/yyyy') AND vp.forpag_id=2 AND vp.tipcom_id in ("+Constantes.ID_TIPCOM_BOLETO_VIAJE+","+Constantes.ID_TIPCOM_BOLETA_VENTA+","+Constantes.ID_TIPCOM_FACTURA+") AND " +
							" vp.tipmov_id NOT IN("+Constantes.ID_TIPMOV_ANULACION_SISTEMA+","+Constantes.ID_TIPMOV_DEVOLUCION+","+Constantes.ID_TIPMOV_ANULACION+") " +
							"AND vp.c_numboleto IS NOT NULL ";
			//str = str.append(sql);
			sql = sql + " UNION ALL ";
			//PREPAGADO
			sql = sql + "SELECT vp.venpas_id, vp.c_numcontrol NroControl, vp.c_numboleto NroBoleto, vp.c_numbolant NroBoletoRef, " +
					"p.c_apepat ApePat, p.c_apemat ApeMat, p.c_nombre Nombre, vp.audfecins FechaActualizacion, vp.n_tarifa MontoBase, " +
					"vp.n_recargo Recargo, vp.n_descuento Descuento, vp.n_acuenta ACuenta, vp.n_penalidad Penalidad, vp.n_imppag NetoPagado, " +
					"fp.forpag_id, fp.c_denominacion FormaPago, tfp.c_denominacion TipoFormaPago, otc.c_denominacion TipoTarjeta, tm.c_denominacion, " +
					"DECODE(tfp.c_denominacion, 'EFECTIVO', 'PREP.(EF)', 'PREP.(TC)') TIPOVENTA, u.c_apepat apepatusu, u.c_nombre nombreusu,u.c_login,vp.d_fecliq,ag.c_denominacion as agencia " +
					",vp.tipmov_id, vp.liquidacion_id, vp.tipforpag_id,vp.tipcom_id, vp.servicio_id, vp.c_rucclicre, vp.canven_id " +
					",NVL(vp.n_imppagdif,0) imppagdif, vp.n_imppagequ, vp.tipmon_id, vp.n_tipcam "+
					",r.c_origen, r.c_destino, tcp.tipcom_id, tcp.c_denominacion tipoComprobante  "+
					",cl.c_numdoc ruc, cl.c_razsoc cliente, p.c_numdoc NUMDOC  " + //41
					"FROM vrtvenpas vp " +
					"INNER JOIN vrmpasajero p ON p.pasajero_id=vp.pasajero_id " +
					"INNER JOIN vrmforpag fp ON fp.forpag_id=vp.forpag_id " +
					"INNER JOIN vrmtipforpag tfp ON tfp.tipforpag_id=vp.tipforpag_id " +
					"INNER JOIN vrmtipmov tm ON tm.tipmov_id=vp.tipmov_id " +
					"INNER JOIN vrmusuario u ON u.usuario_id=vp.usuario_id " +
					"INNER JOIN vrmruta r ON (r.ruta_id=vp.ruta_id)  "+
					"LEFT JOIN vrmtarcre tc ON tc.tarcre_id=vp.tarcre_id " +
					"LEFT JOIN vrmopetarcre otc ON otc.opetarcre_id=tc.opetarcre_id " +
					"LEFT JOIN vrmcliente cl ON (cl.cliente_id=vp.cliente_id) "+
					"INNER JOIN vrmagencia ag ON (ag.agencia_id=vp.agencia_id) "+
					"INNER JOIN vrmtipcom tcp ON (tcp.tipcom_id=vp.tipcom_id) "+
					"WHERE vp.agencia_id="+idAgencia+" AND vp.usuario_id=NVL("+idUsuario+",vp.usuario_id) AND vp.d_fecliq BETWEEN to_date('"+fechaInicial+"','dd/mm/yyyy') AND " +
							"to_date('"+fechaFinal+"','dd/mm/yyyy') AND vp.tipmov_id=12 AND "+
							"vp.tipmov_id NOT IN("+Constantes.ID_TIPMOV_ANULACION_SISTEMA+","+Constantes.ID_TIPMOV_DEVOLUCION+","+Constantes.ID_TIPMOV_ANULACION+") ";

			sql = sql + " UNION ALL ";
			//RECIBOS DE CAJA
			sql = sql + "SELECT vp.venpas_id, vp.c_numcontrol NroControl, vp.c_numboleto NroBoleto, vp.c_numbolant NroBoletoRef, " +
					"p.c_apepat ApePat, p.c_apemat ApeMat, p.c_nombre Nombre, vp.audfecins FechaActualizacion, vp.n_tarifa MontoBase, " +
					"vp.n_recargo Recargo, vp.n_descuento Descuento, vp.n_acuenta ACuenta, vp.n_penalidad Penalidad, vp.n_imppag NetoPagado, " +
					"fp.forpag_id, fp.c_denominacion FormaPago, tfp.c_denominacion TipoFormaPago, otc.c_denominacion TipoTarjeta, tm.c_denominacion, " +
//					"DECODE(tfp.c_denominacion, 'EFECTIVO', 'RC.(EF)', 'RC.(TC)') TIPOVENTA, u.c_apepat apepatusu, u.c_nombre nombreusu,u.c_login " +
					"DECODE(tfp.c_denominacion, 'EFECTIVO', 'RC.(EF)',DECODE(tfp.c_Denominacion, 'TARJETA', 'RC.(TC)','RC.(TRA)')) TIPOVENTA, " +
					"u.c_apepat apepatusu, u.c_nombre nombreusu,u.c_login,vp.d_fecliq,ag.c_denominacion as agencia " +
					",vp.tipmov_id, vp.liquidacion_id, vp.tipforpag_id,vp.tipcom_id, vp.servicio_id, vp.c_rucclicre, vp.canven_id " +
					",NVL(vp.n_imppagdif,0) imppagdif, vp.n_imppagequ, vp.tipmon_id, vp.n_tipcam "+
					",r.c_origen, r.c_destino, tcp.tipcom_id, tcp.c_denominacion tipoComprobante  "+
					",cl.c_numdoc ruc, cl.c_razsoc cliente, p.c_numdoc NUMDOC  " + //41
					"FROM vrtvenpas vp " +
					"INNER JOIN vrmpasajero p ON p.pasajero_id=vp.pasajero_id " +
					"INNER JOIN vrmforpag fp ON fp.forpag_id=vp.forpag_id " +
					"INNER JOIN vrmtipforpag tfp ON tfp.tipforpag_id=vp.tipforpag_id " +
					"INNER JOIN vrmtipmov tm ON tm.tipmov_id=vp.tipmov_id " +
					"INNER JOIN vrmusuario u ON u.usuario_id=vp.usuario_id " +
					"INNER JOIN vrmruta r ON (r.ruta_id=vp.ruta_id)  "+
					"LEFT JOIN vrmtarcre tc ON tc.tarcre_id=vp.tarcre_id " +
					"LEFT JOIN vrmopetarcre otc ON otc.opetarcre_id=tc.opetarcre_id " +
					"LEFT JOIN vrmcliente cl ON (cl.cliente_id=vp.cliente_id) "+
					"INNER JOIN vrmagencia ag ON (ag.agencia_id=vp.agencia_id) "+
					"INNER JOIN vrmtipcom tcp ON (tcp.tipcom_id=vp.tipcom_id) "+
					"WHERE vp.agencia_id="+idAgencia+" AND vp.usuario_id=NVL("+idUsuario+",vp.usuario_id) AND vp.d_fecliq BETWEEN to_date('"+fechaInicial+"','dd/mm/yyyy') AND " +
							"to_date('"+fechaFinal+"','dd/mm/yyyy') AND vp.tipcom_id="+Constantes.ID_TIPCOM_RECIBO_CAJA+" AND vp.forpag_id=1 AND " +
							"vp.tipmov_id NOT IN("+Constantes.ID_TIPMOV_ANULACION_SISTEMA+","+Constantes.ID_TIPMOV_DEVOLUCION+","+Constantes.ID_TIPMOV_ANULACION+") ";

			sql = sql + " UNION ALL ";
			//VENTAS
			tipoVenta = "DECODE(tm.c_denominacion, 'FECHA ABIERTA', DECODE(tfp.c_denominacion, 'EFECTIVO', 'FA.(EF)', DECODE(tfp.c_denominacion, 'TRANSFERENCIA','FA.(TRA)', DECODE(tfp.c_denominacion,'YAPE','FA(YAP)', 'FA.(TC)'))), " +
					"DECODE(tm.c_denominacion, 'CONFIRMACION FA', DECODE(tfp.c_denominacion, 'EFECTIVO', 'CONF.FA.(EF)', DECODE(tfp.c_denominacion, 'TRANSFERENCIA', 'CONF.FA.(TRA)', DECODE(tfp.c_denominacion,'YAPE','CONF.FA.(YAP)', 'CONF.FA.(TC)') )), " +
					"DECODE(tm.c_denominacion, 'REIMPRESION', DECODE(tfp.c_denominacion, 'EFECTIVO', 'REIMP.(EF)', DECODE(tfp.c_denominacion,'YAPE','REIMP.(YAP)', 'REIMP.(TC)') ), " +
					"DECODE(tm.c_denominacion, 'POSTERGACION', DECODE(tfp.c_denominacion, 'EFECTIVO', 'POST.(EF)', DECODE(tfp.c_denominacion,'YAPE','POST.(YAP)', 'POST.(TC)') ), " +
					"DECODE(tm.c_denominacion, 'POSTERGACION FA', DECODE(tfp.c_denominacion, 'EFECTIVO', 'POST.FA.(EF)', DECODE(tfp.c_denominacion,'YAPE','POST.FA.(YAP)', 'POST.FA.(TC)') ), "+
					"DECODE(tm.tipmov_id,"+Constantes.ID_TIPMOV_GASTOS_ADMINISTRATIVOS+", DECODE(tfp.c_denominacion, 'EFECTIVO', 'GAS.ADM(EFE)', DECODE(tfp.c_denominacion,'YAPE','GAS.ADM.(YAP)', 'GAS.ADM.(TC)') ),  " +
					"DECODE(vp.tipmov_id,"+Constantes.ID_TIPMOV_EXCESO_EQUIPAJE+", DECODE(tfp.c_denominacion, 'PCE', 'EQUIPAJE(PCE)', DECODE(tfp.c_denominacion, 'EFECTIVO', 'EQUIPAJE(EF)', DECODE(tfp.c_denominacion,'YAPE','EQUIPAJE.(YAP)', 'EQUIPAJE.(TC)') )),  " +
					"DECODE(vp.tipmov_id,"+Constantes.ID_TIPMOV_SERVICIO_ESPECIAL+", DECODE(tfp.c_denominacion, 'EFECTIVO', 'SERV.ESP(EF)', DECODE(tfp.c_denominacion,'YAPE','SERV.ESP.(YAP)', 'SERV.ESP.(TC)') ),  " +
					"DECODE(vp.tipcom_id,"+Constantes.ID_TIPCOM_NOTA_CREDITO+", 'NOTA CREDITO',  " +
					"DECODE(tm.c_denominacion, 'EFECTIVO', DECODE(tfp.c_denominacion, 'EFECTIVO', 'V.(EF)', DECODE(tfp.c_denominacion, 'TRANSFERENCIA', 'V.(TRA)', DECODE(tfp.c_denominacion,'YAPE','V.(YAP)', 'V.(TC)') )"
					+ "))))))))))) TIPOVENTA ";

			sql = sql + "SELECT vp.venpas_id, vp.c_numcontrol NroControl, vp.c_numboleto NroBoleto, vp.c_numbolant NroBoletoRef, " +
					"p.c_apepat ApePat, p.c_apemat ApeMat, p.c_nombre Nombre, vp.audfecins FechaActualizacion, vp.n_tarifa MontoBase, " +
					"vp.n_recargo Recargo, vp.n_descuento Descuento, vp.n_acuenta ACuenta, vp.n_penalidad Penalidad, vp.n_imppag NetoPagado, " +
					"fp.forpag_id, fp.c_denominacion FormaPago, tfp.c_denominacion TipoFormaPago, otc.c_denominacion TipoTarjeta, tm.c_denominacion, " +
					tipoVenta + ", u.c_apepat apepatusu, u.c_nombre nombreusu,u.c_login,vp.d_fecliq,ag.c_denominacion as agencia " +
					",vp.tipmov_id, vp.liquidacion_id, vp.tipforpag_id,vp.tipcom_id, vp.servicio_id, vp.c_rucclicre, vp.canven_id " +
					",NVL(vp.n_imppagdif,0) imppagdif, vp.n_imppagequ, vp.tipmon_id, vp.n_tipcam "+
					",r.c_origen, r.c_destino, tcp.tipcom_id, tcp.c_denominacion tipoComprobante  "+
					",cl.c_numdoc ruc, cl.c_razsoc cliente, p.c_numdoc NUMDOC  " + //41
					"FROM vrtvenpas vp " +
					"INNER JOIN vrmpasajero p ON p.pasajero_id=vp.pasajero_id " +
					"INNER JOIN vrmforpag fp ON fp.forpag_id=vp.forpag_id " +
					"INNER JOIN vrmtipforpag tfp ON tfp.tipforpag_id=vp.tipforpag_id " +
					"INNER JOIN vrmtipmov tm ON tm.tipmov_id=vp.tipmov_id " +
					"INNER JOIN vrmusuario u ON u.usuario_id=vp.usuario_id " +
					"INNER JOIN vrmruta r ON (r.ruta_id=vp.ruta_id)  "+
					"LEFT JOIN vrmtarcre tc ON tc.tarcre_id=vp.tarcre_id " +
					"LEFT JOIN vrmopetarcre otc ON otc.opetarcre_id=tc.opetarcre_id " +
					"LEFT JOIN vrmcliente cl ON (cl.cliente_id=vp.cliente_id) "+
					"INNER JOIN vrmagencia ag ON (ag.agencia_id=vp.agencia_id) "+
					"INNER JOIN vrmtipcom tcp ON (tcp.tipcom_id=vp.tipcom_id) "+
					"WHERE vp.agencia_id="+idAgencia+" AND vp.usuario_id=NVL("+idUsuario+",vp.usuario_id) AND vp.d_fecliq BETWEEN to_date('"+fechaInicial+"','dd/mm/yyyy') AND " +
   						  "to_date('"+fechaFinal+"','dd/mm/yyyy') AND vp.forpag_id=1 "
   						+ "AND vp.tipcom_id in ("+Constantes.ID_TIPCOM_BOLETO_VIAJE+","+Constantes.ID_TIPCOM_BOLETA_VENTA+","+Constantes.ID_TIPCOM_FACTURA+","+Constantes.ID_TIPCOM_NOTA_CREDITO+","+Constantes.ID_TIPCOM_NOTA_DEBITO+","+Constantes.ID_TIPCOM_GUIA_TRANSPORTISTA+","+Constantes.ID_TIPCOM_GUIA_EXCESO+") "
						+ "AND vp.n_tarifa>0 AND vp.tipmov_id NOT IN (4,5,6,10,11,12,13)) "; // +
//   					"ORDER BY venpas_id ";
//					"ORDER BY NroBoleto ";
			if(criterio == -1)
				sql += "ORDER BY tipcom_id, NroBoleto ";
			else
				sql += "ORDER BY venpas_id ";
			str = str.append(sql);
			break;
		case 1:	//Anulados
			tipoVenta = "'ANULADO' TIPOVENTA ";
			where = "  AND vp.tipmov_id=13 AND vp.n_imppag=0 ";
			break;
		case 2:	//Devoluciones
			tipoVenta = "DECODE(vp.n_penalidad, 0, 'DEV.80%', 'DEV.100%') TIPOVENTA ";
			where = "AND tm.tipmov_id=6 ";
			break;
		case 3:	//Cortesias
			tipoVenta = "DECODE(tfp.tipforpag_id, 3, 'CORTXCUMP', 5, 'CORTXPUNT', 'CORT')TIPOVENTA ";
			where = "AND fp.forpag_id=3 AND vp.c_numboleto IS NOT NULL AND vp.tipmov_id NOT IN (5, 12, 13) ";
			break;
		case 4:	//Credito
			tipoVenta = "'CREDITO' TIPOVENTA ";
			where = " AND vp.forpag_id=2 AND vp.tipcom_id in ("+Constantes.ID_TIPCOM_BOLETA_VENTA+","+Constantes.ID_TIPCOM_BOLETO_VIAJE+","+Constantes.ID_TIPCOM_FACTURA+") "
					+ "AND vp.tipmov_id NOT IN ("+Constantes.ID_TIPMOV_ANULACION_SISTEMA+","+Constantes.ID_TIPMOV_DEVOLUCION+","+Constantes.ID_TIPMOV_ANULACION+")";
			break;
//		case 5:	//EQUIPAJES(PCE)

		case 5:	//Prepagados
			tipoVenta = "DECODE(tfp.c_denominacion, 'EFECTIVO', 'PREP.(EF)', 'PREP.(TC)') TIPOVENTA ";
			where = " AND vp.tipmov_id=12 AND vp.tipmov_id NOT IN (5, 12, 13) ";
			break;
		case 6:	//Recibos de Caja
//			tipoVenta = "DECODE(tfp.c_denominacion, 'EFECTIVO', 'RC.(EF)', 'RC.(TC)') TIPOVENTA ";
			tipoVenta = "DECODE(tfp.c_denominacion, 'EFECTIVO', 'RC.(EF)',DECODE(tfp.c_Denominacion, 'TARJETA', 'RC.(TC)','RC.(TRA)')) TIPOVENTA ";
			where = " AND vp.tipcom_id=3 AND vp.forpag_id=1 AND vp.tipmov_id NOT IN (5,13) ";
			break;
		case 7:	//Tarjeta MasterCard
			tipoVenta = "'TC MASTERCARD' TIPOVENTA ";
			where = " AND vp.tipforpag_id=7 AND otc.opetarcre_id=1 AND vp.forpag_id=1 AND vp.tipmov_id NOT IN (5, 12, 13) ";
			break;
		case 8:	//Tarjeta Visa
			tipoVenta = "'TC VISA' TIPOVENTA ";
			where = " AND vp.tipforpag_id=7 AND otc.opetarcre_id=2 AND vp.forpag_id=1 AND vp.tipmov_id NOT IN (5, 12, 13) ";
			break;
		case 9:	//Ventas
			tipoVenta = "DECODE(tm.c_denominacion, 'FECHA ABIERTA', DECODE(tfp.c_denominacion, 'EFECTIVO', 'FA.(EF)', DECODE(tfp.c_denominacion,'TRANSFERENCIA','FA.(TRA)', 'FA.(TC)')), " +
					"DECODE(tm.c_denominacion, 'CONFIRMACION FA', DECODE(tfp.c_denominacion, 'EFECTIVO', 'CONF.FA.(EF)', DECODE(tfp.c_denominacion,'TRANSFERENCIA','CONF.FA.(TRA)','CONF.FA.(TC)')), " +
					"DECODE(tm.c_denominacion, 'REIMPRESION', DECODE(tfp.c_denominacion, 'EFECTIVO', 'REIMP.(EF)', 'REIMP.(TC)'), " +
					"DECODE(tm.c_denominacion, 'POSTERGACION', DECODE(tfp.c_denominacion, 'EFECTIVO', 'POST.(EF)', 'POST.(TC)'), " +
					"DECODE(tm.c_denominacion, 'POSTERGACION FA', DECODE(tfp.c_denominacion, 'EFECTIVO', 'POST.FA. (EF)', 'POST.FA. (TC)'), " +
					"DECODE(tm.tipmov_id,"+Constantes.ID_TIPMOV_GASTOS_ADMINISTRATIVOS+", DECODE(tfp.c_denominacion, 'EFECTIVO', 'GAS.ADM(EFE)','GAS.ADM(TC)'),  " +
					"DECODE(vp.tipmov_id,"+Constantes.ID_TIPMOV_EXCESO_EQUIPAJE+", DECODE(tfp.c_denominacion, 'PCE', 'EQUIPAJE(PCE)', DECODE(tfp.c_denominacion, 'EFECTIVO', 'EQUIPAJE(EF)','EQUIPAJE(TC)')),  " +
					"DECODE(vp.tipmov_id,"+Constantes.ID_TIPMOV_SERVICIO_ESPECIAL+", DECODE(tfp.c_denominacion, 'EFECTIVO', 'SERV.ESP(EF)', 'SERV.ESP(TC)'),  " +
					"DECODE(tm.c_denominacion, 'EFECTIVO', DECODE(tfp.c_denominacion, 'EFECTIVO', 'V.(EF)', DECODE(tfp.c_denominacion, 'TRANSFERENCIA', 'V.(TRA)', 'V.(TC)'))))))))))) TIPOVENTA ";
			where = " AND vp.forpag_id = 1 AND vp.tipcom_id in ("+Constantes.ID_TIPCOM_BOLETA_VENTA+","+Constantes.ID_TIPCOM_BOLETO_VIAJE+","+Constantes.ID_TIPCOM_FACTURA+","+Constantes.ID_TIPCOM_NOTA_CREDITO+","+Constantes.ID_TIPCOM_NOTA_DEBITO+") "
					+ " AND vp.n_tarifa>0 AND vp.tipmov_id NOT IN (4,5,6,10,11, 12,13) ";
			break;
		case 10: //Notas de credito
			tipoVenta = "'NOTA CREDITO' TIPOVENTA ";
			where = " AND vp.tipcom_id="+Constantes.ID_TIPCOM_NOTA_CREDITO+" AND vp.tipmov_id NOT IN (5, 13) ";
			break;
		case 11: //Notas de debito
			tipoVenta = "'NOTA DEBITO' TIPOVENTA ";
			where = " AND vp.tipcom_id="+Constantes.ID_TIPCOM_NOTA_DEBITO+" AND vp.tipmov_id NOT IN (5, 13) ";
			break;
		case 12: //Gastos administrativos
			tipoVenta = "'GAS.ADMIN' TIPOVENTA ";
			where = " AND vp.tipcom_id IN ("+Constantes.ID_TIPCOM_BOLETA_VENTA+","+Constantes.ID_TIPCOM_FACTURA+","+Constantes.ID_TIPCOM_BOLETO_VIAJE+") AND vp.tipmov_id IN ("+Constantes.ID_TIPMOV_GASTOS_ADMINISTRATIVOS+") ";
			break;
		default:
			break;
		}

		if(criterio==8 || criterio==9){
			sql = "SELECT vp.venpas_id, vp.c_numcontrol NroControl, vp.c_numboleto NroBoleto, vp.c_numbolant NroBoletoRef, " +
					"p.c_apepat ApePat, p.c_apemat ApeMat, p.c_nombre Nombre, vp.audfecins FechaActualizacion, vp.n_tarifa MontoBase, " +
					"vp.n_recargo Recargo, vp.n_descuento Descuento, vp.n_acuenta ACuenta, vp.n_penalidad Penalidad, vp.n_imppag NetoPagado, " +
					"fp.forpag_id, fp.c_denominacion FormaPago, tfp.c_denominacion TipoFormaPago, otc.c_denominacion TipoTarjeta, tm.c_denominacion, " +
					tipoVenta + ", u.c_apepat apepatusu, u.c_nombre nombreusu,u.c_login,vp.d_fecliq,ag.c_denominacion as agencia " +
					",vp.tipmov_id, vp.liquidacion_id, vp.tipforpag_id,vp.tipcom_id, vp.servicio_id, vp.c_rucclicre, vp.canven_id " +
					",NVL(vp.n_imppagdif,0) imppagdif, vp.n_imppagequ, vp.tipmon_id, vp.n_tipcam "+
					",r.c_origen, r.c_destino, tcp.tipcom_id, tcp.c_denominacion tipoComprobante  "+
					",cl.c_numdoc ruc, cl.c_razsoc cliente, p.c_numdoc NUMDOC  " + //41
					"FROM vrtvenpas vp " +
//					"INNER JOIN (SELECT MAX(venpas_id)venpas_id, c_numcontrol FROM vrtvenpas GROUP BY c_numcontrol) max_vta ON max_vta.venpas_id=vp.venpas_id " +
					"INNER JOIN vrmpasajero p ON p.pasajero_id=vp.pasajero_id " +
					"INNER JOIN vrmforpag fp ON fp.forpag_id=vp.forpag_id " +
					"INNER JOIN vrmtipforpag tfp ON tfp.tipforpag_id=vp.tipforpag_id " +
					"INNER JOIN vrmtipmov tm ON tm.tipmov_id=vp.tipmov_id " +
					"INNER JOIN vrmusuario u ON u.usuario_id=vp.usuario_id " +
					"INNER JOIN vrmruta r ON (r.ruta_id=vp.ruta_id)  "+
					"LEFT JOIN vrmtarcre tc ON tc.tarcre_id=vp.tarcre_id " +
					"LEFT JOIN vrmopetarcre otc ON otc.opetarcre_id=tc.opetarcre_id " +
					"LEFT JOIN vrmcliente cl ON (cl.cliente_id=vp.cliente_id) "+
					"INNER JOIN vrmagencia ag ON (ag.agencia_id=vp.agencia_id) "+
					"INNER JOIN vrmtipcom tcp ON (tcp.tipcom_id=vp.tipcom_id) "+
					"WHERE vp.agencia_id="+idAgencia+" AND vp.usuario_id=NVL("+idUsuario+",vp.usuario_id) AND vp.d_fecliq BETWEEN to_date('"+fechaInicial+"','dd/mm/yyyy') AND " +
							"to_date('"+fechaFinal+"','dd/mm/yyyy') AND vp.tipcom_id IN ("+Constantes.ID_TIPCOM_BOLETO_VIAJE+","+Constantes.ID_TIPCOM_RECIBO_CAJA+","+Constantes.ID_TIPCOM_BOLETA_VENTA+","+Constantes.ID_TIPCOM_FACTURA+") AND " +
							"vp.c_tiptra IN ("+Constantes.TIPO_OPERACION_VENTA+","+Constantes.TIPO_OPERACION_VENTA_ESPECIAL+","+Constantes.TIPO_OPERACION_EXCESO+") ";

			sql = sql + where;
			sql = sql + "ORDER BY vp.audfecins";
		}else if(criterio!=0 && criterio!=-1){
			sql = "SELECT vp.venpas_id, vp.c_numcontrol NroControl, vp.c_numboleto NroBoleto, vp.c_numbolant NroBoletoRef, " +
					"p.c_apepat ApePat, p.c_apemat ApeMat, p.c_nombre Nombre, vp.audfecins FechaActualizacion, vp.n_tarifa MontoBase, " +
					"vp.n_recargo Recargo, vp.n_descuento Descuento, vp.n_acuenta ACuenta, vp.n_penalidad Penalidad, vp.n_imppag NetoPagado, " +
					"fp.forpag_id, fp.c_denominacion FormaPago, tfp.c_denominacion TipoFormaPago, otc.c_denominacion TipoTarjeta, tm.c_denominacion, " +
					tipoVenta + ", u.c_apepat apepatusu, u.c_nombre nombreusu,u.c_login,vp.d_fecliq,ag.c_denominacion as agencia " +
					",vp.tipmov_id, vp.liquidacion_id, vp.tipforpag_id,vp.tipcom_id, vp.servicio_id, vp.c_rucclicre, vp.canven_id " +
					",NVL(vp.n_imppagdif,0) imppagdif, vp.n_imppagequ, vp.tipmon_id, vp.n_tipcam "+
					",r.c_origen, r.c_destino, tcp.tipcom_id, tcp.c_denominacion tipoComprobante  "+
					",cl.c_numdoc ruc, cl.c_razsoc cliente, p.c_numdoc NUMDOC  " + //41
					"FROM vrtvenpas vp " +
					"INNER JOIN vrmpasajero p ON p.pasajero_id=vp.pasajero_id " +
					"INNER JOIN vrmforpag fp ON fp.forpag_id=vp.forpag_id " +
					"INNER JOIN vrmtipforpag tfp ON tfp.tipforpag_id=vp.tipforpag_id " +
					"INNER JOIN vrmtipmov tm ON tm.tipmov_id=vp.tipmov_id " +
					"INNER JOIN vrmusuario u ON u.usuario_id=vp.usuario_id " +
					"INNER JOIN vrmruta r ON (r.ruta_id=vp.ruta_id)  "+
					"LEFT JOIN vrmtarcre tc ON tc.tarcre_id=vp.tarcre_id " +
					"LEFT JOIN vrmopetarcre otc ON otc.opetarcre_id=tc.opetarcre_id " +
					"LEFT JOIN vrmcliente cl ON (cl.cliente_id=vp.cliente_id) "+
					"INNER JOIN vrmagencia ag ON (ag.agencia_id=vp.agencia_id) "+
					"INNER JOIN vrmtipcom tcp ON (tcp.tipcom_id=vp.tipcom_id) "+
					"WHERE vp.agencia_id="+idAgencia+" AND vp.usuario_id=NVL("+idUsuario+",vp.usuario_id) AND vp.d_fecliq BETWEEN to_date('"+fechaInicial+"','dd/mm/yyyy') AND " +
							"to_date('"+fechaFinal+"','dd/mm/yyyy') AND vp.tipcom_id IN ("+Constantes.ID_TIPCOM_BOLETO_VIAJE+","+Constantes.ID_TIPCOM_RECIBO_CAJA+","+Constantes.ID_TIPCOM_BOLETA_VENTA+","+Constantes.ID_TIPCOM_FACTURA+","+Constantes.ID_TIPCOM_NOTA_CREDITO+","+Constantes.ID_TIPCOM_NOTA_DEBITO+","+Constantes.ID_TIPCOM_GUIA_TRANSPORTISTA+") AND " +
							//"vp.c_tiptra= "+Constantes.TIPO_OPERACION_VENTA
							"vp.c_tiptra IN ("+Constantes.TIPO_OPERACION_VENTA+", "+Constantes.TIPO_OPERACION_VENTA_ESPECIAL+", "+Constantes.TIPO_OPERACION_EXCESO+") ";

			sql = sql + where;
			sql = sql + "ORDER BY vp.audfecins";
		}

		log.info(sql);

		List<?> result = getSession().createSQLQuery(sql).list();
		List<VentaPasaje> lstResult = new ArrayList<>();
		for(int i=0; i<result.size(); i++){
			Object[] obj = (Object[]) result.get(i);
			VentaPasaje ventaPasaje = new VentaPasaje();
			ventaPasaje.setId(((BigDecimal)obj[0]).longValue());
			ventaPasaje.setNumeroControl(obj[1].toString());
			ventaPasaje.setNumeroBoleto(obj[2].toString());
			ventaPasaje.setNumeroBoletoAnterior(obj[3]!=null?obj[3].toString():null);
			Pasajero pasajero = new Pasajero();
			pasajero.setApellidoPaterno(obj[4].toString());
			pasajero.setApellidoMaterno(obj[5]!=null?obj[5].toString():null);
			pasajero.setNombre(obj[6].toString());
			pasajero.setNumeroDocumento(obj[42]!=null?obj[42].toString():null);
			ventaPasaje.setPasajero(pasajero);
			ventaPasaje.setFechaInsercion((Date)obj[7]);
			ventaPasaje.setTarifa(((BigDecimal)obj[8]).doubleValue());
			ventaPasaje.setRecargo(((BigDecimal)obj[9]).doubleValue());
			ventaPasaje.setDescuento(((BigDecimal)obj[10]).doubleValue());
			ventaPasaje.setAcuenta(((BigDecimal)obj[11]).doubleValue());
			ventaPasaje.setPenalidad(((BigDecimal)obj[12]).doubleValue());
			ventaPasaje.setImportePagado(((BigDecimal)obj[13]).doubleValue());
			FormaPago formaPago = new FormaPago();
			formaPago.setId(((BigDecimal)obj[14]).intValue());
			formaPago.setDenominacion(obj[15].toString());
			ventaPasaje.setFormaPago(formaPago);
			TipoFormaPago tipoFormaPago = new TipoFormaPago();
			tipoFormaPago.setId(((BigDecimal)obj[27]).intValue());
			tipoFormaPago.setDenominacion(obj[16].toString());
			ventaPasaje.setTipoFormaPago(tipoFormaPago);
			OperadorTarjetaCredito operadorTarjetaCredito = new OperadorTarjetaCredito();
			operadorTarjetaCredito.setDenominacion(obj[17]!=null?obj[17].toString():null);
			ventaPasaje.setOperadorTarjetaCredito(operadorTarjetaCredito);
			ventaPasaje.setTipoTransaccion(obj[19]!=null?obj[19].toString():"");
			Usuario usuario = new Usuario();
			usuario.setApellidoPaterno(obj[20].toString());
			usuario.setNombre(obj[21].toString());
			usuario.setLogin(obj[22].toString());
			ventaPasaje.setFechaLiquidacion(obj[23]!=null?((Date)obj[23]):null);

			Agencia agenciaVenta=new Agencia();
			agenciaVenta.setDenominacion(obj[24].toString());

			Ruta ruta= new Ruta();
			ruta.setOrigen(obj[36].toString());
			ruta.setDestino(obj[37].toString());

			TipoComprobante tipoComprobante= new TipoComprobante();
			tipoComprobante.setId(((BigDecimal)obj[38]).intValue());
			tipoComprobante.setDenominacion(obj[39].toString());

			ventaPasaje.setTipoMovimiento(new TipoMovimiento(((BigDecimal)obj[25]).intValue()));
			ventaPasaje.setLiquidacion(obj[26]!=null?new Liquidacion(((BigDecimal)obj[26]).intValue()):null);
//			ventaPasaje.setTipoFormaPago(new TipoFormaPago(((BigDecimal)obj[27]).intValue()));
//			ventaPasaje.setTipoComprobante(new TipoComprobante(((BigDecimal)obj[28]).intValue()));
			ventaPasaje.setTipoComprobante(tipoComprobante);
			ventaPasaje.setAgencia(agenciaVenta);
			ventaPasaje.setUsuario(usuario);
			ventaPasaje.setServicio(obj[29]!=null?new Servicio(((BigDecimal)obj[29]).intValue()):null);
			ventaPasaje.setRucClienteCredito(obj[30]!=null?obj[30].toString():null);
			ventaPasaje.setCanalVenta(new CanalVenta(((BigDecimal)obj[31]).intValue()));
			ventaPasaje.setImportePagadoByDiferencia(((BigDecimal)obj[32]).doubleValue());
			ventaPasaje.setImportePagadoEquibalente(obj[33]==null?null:((BigDecimal)obj[33]).doubleValue());
			if(obj[34]!=null) {
				ventaPasaje.setTipoMoneda(new TipoMoneda(((BigDecimal)obj[34]).intValue()));
				ventaPasaje.setTipoCambio(((BigDecimal)obj[35]).doubleValue());
			}
			ventaPasaje.setRuta(ruta);

			Cliente cliente =null;
			if(obj[40]!=null) {
				cliente = new Cliente();
				cliente.setNumeroDocumento(obj[40].toString());
				cliente.setRazonSocial(obj[41].toString());
				ventaPasaje.setCliente(cliente);
			}

			lstResult.add(ventaPasaje);
		}
		return lstResult;
	}

	/*
	 * (non-Javadoc)
	 * @see com.tepsa.sisvyr.model.dao.VentaPasajesDAO#buscarDetalleVentasAgencia(java.lang.String, java.lang.String, java.lang.String, java.lang.Long)
	 */
	@Override
	public List<VentaPasaje> buscarDetalleVentasAgencia(String fechaInicio, String fechaFin,String rucClienteCredito, Long idUsuario, String orden, Boolean incluirAnulados, boolean isSoles, String estadoBoleto, Integer centroCostoID, boolean byFechaReimpresion){
		//Busca los vouches emitidos
		if(estadoBoleto!=null)
			estadoBoleto="'"+estadoBoleto+"'";

		String sql="";
		if(byFechaReimpresion){
			sql="SELECT * "
					 + "FROM ( "
						//Busca los comprobantes credito por fecha de reimprecion del voucher
						+ "SELECT DECODE(vp.tipmov_id,13,'ANULADO','5','ANULADO',6,'DEVUELTO','VENDIDO') AS Tipo "+ // 0
						      ",vp.c_numcontrol ,vp.c_numboleto As boleto,vp.c_numbolant AS voucher,tfp.c_denominacion AS TipoPago,vp.n_tarifa AS Bruto "+ //1-5
						      ",vp.n_descuento AS Descuento,vp.n_imppag AS Neto "+ //6- 7
						      ",vp.cencos_id AS CCosto,u.c_apepat as userApellidoPaterno, vp.d_fecliq AS FechaVenta,vp.tipmov_id ,vp.venpas_id,cc.c_denominacion "+ //8-13
						      ",p.c_nomape as paxNombresApellidos,p.c_apepat as paxApellidoPaterno,p.c_apemat as paxApellidoMaterno,p.c_nombre as paxNombres,tag.tipage_id "+//14-18
						      ",u.c_apemat as userApellidoMaterno,u.c_nombre as userNombres,u.c_login,cc.c_codigo,vp.audfecins "+ //19-23
						      ",cs.n_comision,cs.n_incigv,cs.n_tipcom,r.c_origen,r.c_destino,vp.d_fecpar,vp.venpas_idref "+ //24-30
						      ",p.c_numdoc,agbol.c_nomcor As Agencia,vp.c_horpar as horaPartida,vp.c_horlle as horaLlegada "+//31-34
						      ",cl.cliente_id as idCliente,cl.c_numdoc as numeroruc,cl.c_razsoc as razonsocial,r.n_kilometros as kilometros "+ //35-38
						      ",NVL(vp.c_estdoc,'PENDIENTE') c_estdoc  "+ //39
						      ",NVL(vp.N_TARIFAEQU,0) tarifaEquibalente, NVL(vp.N_DESEQU,0) desctEquibalente, NVL(vp.N_IMPPAGEQU,0) netoEquibalente  "+ //40-42
						      ",vp.tipmon_id tipoMoneda, cs.concesionario_id, vp.tipcom_id "+//43-45
						"FROM VRTVENPAS vp "+
						    "INNER JOIN VRTVENPAS vp_ref ON (vp_ref.venpas_id=vp.venpas_idref) "+
						    "INNER JOIN VRMUSUARIO u ON (u.usuario_id=vp.usuario_id) " +
						    "INNER JOIN VRMPASAJERO p ON (p.pasajero_id=vp.pasajero_id) " +
						    "LEFT JOIN VRMCENCOS cc ON (cc.cencos_id=vp.cencos_id) " +
						    "INNER JOIN VRMAGENCIA ag ON (ag.agencia_id=vp.agencia_id) " +
						    "INNER JOIN VRMCONCESIONARIO cs ON (cs.c_ruc=vp.c_Rucclicre) AND cs.c_estreg='A' " +
						    "INNER JOIN VRMTIPAGE tag ON (tag.tipage_id=ag.tipage_id) " +
						    "INNER JOIN VRMTIPFORPAG tfp ON (tfp.tipforpag_id=vp.tipforpag_id) " +
						    "INNER JOIN VRMRUTA r ON (r.ruta_id=vp.ruta_id)  " +
						    "LEFT JOIN VRMCLIENTE cl ON (cl.cliente_id=vp.cliente_id) " +
						    "LEFT JOIN VRMAGENCIA agbol ON (agbol.agencia_id=vp.agencia_id) " +
						"WHERE vp.d_fecliq BETWEEN to_date('"+fechaInicio+"','"+Constantes.DATE_FORMAT+"') AND to_date('"+fechaFin+"','"+Constantes.DATE_FORMAT+"') "+
						    "AND vp.c_rucclicre='"+rucClienteCredito+"' "+
						    "AND vp.Usuario_Id=NVL("+idUsuario+",vp.usuario_id) " +
						    "AND vp.tipcom_id IN ("+Constantes.ID_TIPCOM_BOLETA_VENTA+","+Constantes.ID_TIPCOM_FACTURA+") " +
							"AND vp.c_tiptra=1 " +
							"AND vp.tipmov_id NOT IN (5,6,13) "+
							"AND vp_ref.tipcom_id NOT in ("+Constantes.ID_TIPCOM_NOTA_CREDITO+","+Constantes.ID_TIPCOM_NOTA_DEBITO+") "+
					"UNION ALL  "+
					//Busca los comprobantes credito emitidos directamente sin voucher
					 	"SELECT DECODE(vp.tipmov_id,13,'ANULADO','5','ANULADO',6,'DEVUELTO','VENDIDO') AS Tipo "+ // 0
						      ",vp.c_numcontrol ,vp.c_numboleto As boleto,vp.c_numbolant AS voucher,tfp.c_denominacion AS TipoPago,vp.n_tarifa AS Bruto "+ //1-5
						      ",vp.n_descuento AS Descuento,vp.n_imppag AS Neto "+ //6- 7
						      ",vp.cencos_id AS CCosto,u.c_apepat as userApellidoPaterno, vp.d_fecliq AS FechaVenta,vp.tipmov_id ,vp.venpas_id,cc.c_denominacion "+ //8-13
						      ",p.c_nomape as paxNombresApellidos,p.c_apepat as paxApellidoPaterno,p.c_apemat as paxApellidoMaterno,p.c_nombre as paxNombres,tag.tipage_id "+//14-18
						      ",u.c_apemat as userApellidoMaterno,u.c_nombre as userNombres,u.c_login,cc.c_codigo,vp.audfecins "+ //19-23
						      ",cs.n_comision,cs.n_incigv,cs.n_tipcom,r.c_origen,r.c_destino,vp.d_fecpar,vp.venpas_idref "+ //24-30
						      ",p.c_numdoc,agbol.c_nomcor As Agencia,vp.c_horpar as horaPartida,vp.c_horlle as horaLlegada "+//31-34
						      ",cl.cliente_id as idCliente,cl.c_numdoc as numeroruc,cl.c_razsoc as razonsocial,r.n_kilometros as kilometros "+ //35-38
						      ",NVL(vp.c_estdoc,'PENDIENTE') c_estdoc  "+ //39
						      ",NVL(vp.N_TARIFAEQU,0) tarifaEquibalente, NVL(vp.N_DESEQU,0) desctEquibalente, NVL(vp.N_IMPPAGEQU,0) netoEquibalente  "+ //40-42
						      ",vp.tipmon_id tipoMoneda, cs.concesionario_id, vp.tipcom_id "+//43-45
						"FROM VRTVENPAS vp "+
						    "INNER JOIN VRMUSUARIO u ON (u.usuario_id=vp.usuario_id) " +
						    "INNER JOIN VRMPASAJERO p ON (p.pasajero_id=vp.pasajero_id) " +
						    "LEFT JOIN VRMCENCOS cc ON (cc.cencos_id=vp.cencos_id) " +
						    "INNER JOIN VRMAGENCIA ag ON (ag.agencia_id=vp.agencia_id) " +
						    "INNER JOIN VRMCONCESIONARIO cs ON (cs.c_ruc=vp.c_Rucclicre) AND cs.c_estreg='A' " +
						    "INNER JOIN VRMTIPAGE tag ON (tag.tipage_id=ag.tipage_id) " +
						    "INNER JOIN VRMTIPFORPAG tfp ON (tfp.tipforpag_id=vp.tipforpag_id) " +
						    "INNER JOIN VRMRUTA r ON (r.ruta_id=vp.ruta_id)  " +
						    "LEFT JOIN VRMCLIENTE cl ON (cl.cliente_id=vp.cliente_id) " +
						    "LEFT JOIN VRMAGENCIA agbol ON (agbol.agencia_id=vp.agencia_id) " +
						"WHERE vp.d_fecliq BETWEEN to_date('"+fechaInicio+"','"+Constantes.DATE_FORMAT+"') AND to_date('"+fechaFin+"','"+Constantes.DATE_FORMAT+"') "+
						    "AND vp.c_rucclicre='"+rucClienteCredito+"' "+
//						    "AND vp.venpas_id=vp.venpas_idoriginal "+
						    "AND vp.venpas_idref IS NULL "+
						    "AND vp.Usuario_Id=NVL("+idUsuario+",vp.usuario_id) " +
						    "AND vp.tipcom_id IN ("+Constantes.ID_TIPCOM_BOLETA_VENTA+","+Constantes.ID_TIPCOM_FACTURA+") " +
							"AND vp.c_tiptra=1 " +
							"AND vp.tipmov_id NOT IN (5,6,13) ";
				sql+=")rpt "
					+ "WHERE rpt.c_estdoc=NVL("+estadoBoleto+",rpt.c_estdoc) ";

			sql+=" ORDER BY "+orden;
		}else{
			sql="SELECT * "
					 + "FROM ( "
					 + "SELECT DECODE(vp.tipmov_id,13,'ANULADO','5','ANULADO',6,'DEVUELTO','VENDIDO') AS Tipo "+ // 0
						      ",vp.c_numcontrol ,vp.c_numbolant As boleto,vp.c_numboleto AS voucher,tfp.c_denominacion AS TipoPago,vp.n_tarifa AS Bruto "+ //1-5
						      ",vp.n_descuento AS Descuento,vp.n_imppag AS Neto "+ //6- 7
						      ",vp.cencos_id AS CCosto,u.c_apepat as userApellidoPaterno, vp.d_fecliq AS FechaVenta,vp.tipmov_id ,vp.venpas_id,cc.c_denominacion "+ //8-13
						      ",p.c_nomape as paxNombresApellidos,p.c_apepat as paxApellidoPaterno,p.c_apemat as paxApellidoMaterno,p.c_nombre as paxNombres,tag.tipage_id "+//14-18
						      ",u.c_apemat as userApellidoMaterno,u.c_nombre as userNombres,u.c_login,cc.c_codigo,vp.audfecins "+ //19-23
						      ",cs.n_comision,cs.n_incigv,cs.n_tipcom,r.c_origen,r.c_destino,vp.d_fecpar,vp.venpas_idref "+ //24-30
						      ",p.c_numdoc,agbol.c_nomcor As Agencia,vp.c_horpar as horaPartida,vp.c_horlle as horaLlegada "+//31-34
						      ",cl.cliente_id as idCliente,cl.c_numdoc as numeroruc,cl.c_razsoc as razonsocial,r.n_kilometros as kilometros "+ //35-38
						      ",NVL(vprefbol.c_estdoc,'ACT') c_estdoc  "+ //39
						      ",NVL(vp.N_TARIFAEQU,0) tarifaEquibalente, NVL(vp.N_DESEQU,0) desctEquibalente, NVL(vp.N_IMPPAGEQU,0) netoEquibalente  "+ //40-42
						      ",vp.tipmon_id tipoMoneda,cs.concesionario_id, vp.tipcom_id "+//43-45
						"FROM VRTVENPAS vp "+
							  "INNER JOIN (SELECT MIN(VENPAS_ID)VENPAS_ID FROM VRTVENPAS GROUP BY C_NUMCONTROL )VENPAS_MIN " +
							  		 "ON (VENPAS_MIN.venpas_id=vp.venpas_id) "+
							  "INNER JOIN VRMUSUARIO u ON (u.usuario_id=vp.usuario_id) " +
							  "INNER JOIN VRMPASAJERO p ON (p.pasajero_id=vp.pasajero_id) " +
							  "LEFT JOIN VRMCENCOS cc ON (cc.cencos_id=vp.cencos_id) " +
							  "INNER JOIN VRMAGENCIA ag ON (ag.agencia_id=vp.agencia_id) " +
							  "INNER JOIN VRMCONCESIONARIO cs ON (cs.c_ruc=vp.c_Rucclicre) AND cs.c_estreg='A' " +
							  "INNER JOIN VRMTIPAGE tag ON (tag.tipage_id=ag.tipage_id) " +
							  "INNER JOIN VRMTIPFORPAG tfp ON (tfp.tipforpag_id=vp.tipforpag_id) " +
							  "INNER JOIN VRMRUTA r ON (r.ruta_id=vp.ruta_id)  " +
							  "LEFT JOIN VRMCLIENTE cl ON (cl.cliente_id=vp.cliente_id) " +
							  "LEFT JOIN VRTVENPAS vprefbol ON (vprefbol.venpas_id=vp.venpas_idref AND vprefbol.tipmov_id NOT IN (5,6)) "+
							  "LEFT JOIN VRMAGENCIA agbol ON (agbol.agencia_id=vprefbol.agencia_id) " +
						"WHERE vp.d_fecliq BETWEEN to_date('"+fechaInicio+"','"+Constantes.DATE_FORMAT+"') AND to_date('"+fechaFin+"','"+Constantes.DATE_FORMAT+"') "+
							  "AND vp.c_rucclicre='"+rucClienteCredito+"' "+
						      "AND vp.Usuario_Id=NVL("+idUsuario+",vp.usuario_id) " +
						      "AND vp.tipcom_id IN ("+Constantes.ID_TIPCOM_VOUCHER_AGENCIA_VIAJES+","+Constantes.ID_TIPCOM_VOUCHER_CORPORATIVO+") " +
//						      "AND vp.c_estdoc=NVL("+estadoBoleto+",vp.c_estdoc) "+
						      "AND vp.c_tiptra=1 ";
			if(!(incluirAnulados))
				sql+="AND vp.tipmov_id NOT IN (5,6,13) ";

				sql+="UNION ALL "+
						//Busca boleto credito sin voucher, ingresados por el counter.
						"SELECT DECODE(vp.tipmov_id,13,'ANULADO','5','ANULADO',6,'DEVUELTO','VENDIDO') AS Tipo "+ // 0
						      ",vp.c_numcontrol ,vp.c_numboleto As boleto,vp.c_numbolant AS voucher,tfp.c_denominacion AS TipoPago,vp.n_tarifa AS Bruto "+ //1-5
						      ",vp.n_descuento AS Descuento,vp.n_imppag AS Neto "+ //6- 7
						      ",vp.cencos_id AS CCosto,u.c_apepat as userApellidoPaterno, vp.d_fecliq AS FechaVenta,vp.tipmov_id ,vp.venpas_id,cc.c_denominacion "+ //8-13
						      ",p.c_nomape as paxNombresApellidos,p.c_apepat as paxApellidoPaterno,p.c_apemat as paxApellidoMaterno,p.c_nombre as paxNombres,tag.tipage_id "+//14-18
						      ",u.c_apemat as userApellidoMaterno,u.c_nombre as userNombres,u.c_login,cc.c_codigo,vp.audfecins "+ //19-23
						      ",cs.n_comision,cs.n_incigv,cs.n_tipcom,r.c_origen,r.c_destino,vp.d_fecpar,vp.venpas_idref "+ //24-30
						      ",p.c_numdoc,agbol.c_nomcor As Agencia,vp.c_horpar as horaPartida,vp.c_horlle as horaLlegada "+//31-34
						      ",cl.cliente_id as idCliente,cl.c_numdoc as numeroruc,cl.c_razsoc as razonsocial,r.n_kilometros as kilometros "+ //35-38
						      ",NVL(vp.c_estdoc,'PENDIENTE') c_estdoc  "+ //39
						      ",NVL(vp.N_TARIFAEQU,0) tarifaEquibalente, NVL(vp.N_DESEQU,0) desctEquibalente, NVL(vp.N_IMPPAGEQU,0) netoEquibalente  "+ //40-42
						      ",vp.tipmon_id tipoMoneda, cs.concesionario_id, vp.tipcom_id "+//43-45
						"FROM VRTVENPAS vp "+
						    "INNER JOIN VRMUSUARIO u ON (u.usuario_id=vp.usuario_id) " +
						    "INNER JOIN VRMPASAJERO p ON (p.pasajero_id=vp.pasajero_id) " +
						    "LEFT JOIN VRMCENCOS cc ON (cc.cencos_id=vp.cencos_id) " +
						    "INNER JOIN VRMAGENCIA ag ON (ag.agencia_id=vp.agencia_id) " +
						    "INNER JOIN VRMCONCESIONARIO cs ON (cs.c_ruc=vp.c_Rucclicre) AND cs.c_estreg='A' " +
						    "INNER JOIN VRMTIPAGE tag ON (tag.tipage_id=ag.tipage_id) " +
						    "INNER JOIN VRMTIPFORPAG tfp ON (tfp.tipforpag_id=vp.tipforpag_id) " +
						    "INNER JOIN VRMRUTA r ON (r.ruta_id=vp.ruta_id)  " +
						    "LEFT JOIN VRMCLIENTE cl ON (cl.cliente_id=vp.cliente_id) " +
//						    "LEFT JOIN VRTVENPAS vprefbol ON (vprefbol.venpas_id=vp.venpas_idref AND vprefbol.tipmov_id NOT IN (5,6)) "+
						    "LEFT JOIN VRMAGENCIA agbol ON (agbol.agencia_id=vp.agencia_id) " +
						"WHERE vp.d_fecliq BETWEEN to_date('"+fechaInicio+"','"+Constantes.DATE_FORMAT+"') AND to_date('"+fechaFin+"','"+Constantes.DATE_FORMAT+"') "+
						    "AND vp.c_rucclicre='"+rucClienteCredito+"' "+
//						    "AND vp.venpas_id=vp.venpas_idoriginal "+
							"AND vp.venpas_idref IS NULL "+
						    "AND vp.Usuario_Id=NVL("+idUsuario+",vp.usuario_id) " +
						    "AND vp.tipcom_id IN ("+Constantes.ID_TIPCOM_BOLETO_VIAJE+","+Constantes.ID_TIPCOM_BOLETA_VENTA+","+Constantes.ID_TIPCOM_FACTURA+") " +
//						    "AND vp.c_estdoc=NVL("+estadoBoleto+",vp.c_estdoc) "+
							"AND vp.c_tiptra=1 ";
				if(!(incluirAnulados))
					sql+="AND vp.tipmov_id NOT IN (5,6,13) ";

				sql+=")rpt "
					+ "WHERE rpt.c_estdoc=NVL("+estadoBoleto+",rpt.c_estdoc) ";

			sql+=" ORDER BY "+orden;
		}

		log.info(sql);
		List<?> result = getSession().createSQLQuery(sql).list();
		List<VentaPasaje> lstResult = new ArrayList<>();
		for(int i=0; i<result.size(); i++){
			Object[] obj = (Object[]) result.get(i);
			VentaPasaje ventaPasaje = new VentaPasaje();
			Pasajero pasajero= new Pasajero();
			Agencia agencia= new Agencia();
			TipoAgencia tipoAgencia= new TipoAgencia();
			TipoMovimiento tipoMovimiento=new TipoMovimiento();
			FormaPago formaPago= new FormaPago();
			CentroCosto centroCosto= new CentroCosto();
			Usuario usuario= new Usuario();

			Concesionario concesionario= new Concesionario();
			concesionario.setComision(obj[24]!=null?((BigDecimal)obj[24]).intValue():0);
			concesionario.setIncluyeIgv(obj[25]!=null?((BigDecimal)obj[25]).intValue():0);
			concesionario.setTipoComision(obj[26]!=null?((BigDecimal)obj[26]).intValue():0);
			concesionario.setId(((BigDecimal)obj[44]).intValue());

			tipoAgencia.setId(((BigDecimal)obj[18]).intValue());
			agencia.setTipoAgencia(tipoAgencia);
			agencia.setConcesionario(concesionario);

			pasajero.setNombresApellidos(obj[14]!=null?obj[14].toString():"");
			pasajero.setApellidoPaterno(obj[15]!=null?obj[15].toString():"");
			pasajero.setApellidoMaterno(obj[16]!=null?obj[16].toString():"");
			pasajero.setNombre(obj[17].toString());
			pasajero.setNumeroDocumento(obj[31]!=null?obj[31].toString():"");

			tipoMovimiento.setDenominacion(obj[0].toString());
			tipoMovimiento.setId(((BigDecimal)obj[11]).intValue());

			formaPago.setDenominacion(obj[4].toString());

			centroCosto.setId(obj[8]!=null?((BigDecimal)obj[8]).intValue():null);
			centroCosto.setDenominacion(obj[13]!=null?obj[13].toString():"");
			centroCosto.setCodigo(obj[22]!=null?obj[22].toString():"");

			usuario.setApellidoPaterno(obj[9]!=null?obj[9].toString():"");
			usuario.setApellidoMaterno(obj[19]!=null?obj[19].toString():"");
			usuario.setNombre(obj[20]!=null?obj[20].toString():"");
			usuario.setLogin(obj[21]!=null?obj[21].toString():"");

			Ruta ruta= new Ruta();
			ruta.setOrigen(obj[27].toString());
			ruta.setDestino(obj[28].toString());
			ruta.setKilometros(obj[38]!=null?((BigDecimal)obj[38]).doubleValue():.00);

			ventaPasaje.setTipoMovimiento(tipoMovimiento);
			ventaPasaje.setNumeroControl(obj[1].toString());
			ventaPasaje.setNumeroBoleto(obj[2]!=null?obj[2].toString():"");
			ventaPasaje.setNumeroBoletoAnterior(obj[3]!=null?obj[3].toString():"");
			ventaPasaje.setFormaPago(formaPago);
			ventaPasaje.setTarifa(((BigDecimal)obj[5]).doubleValue());
			ventaPasaje.setDescuento(((BigDecimal)obj[6]).doubleValue());
			ventaPasaje.setImportePagado(((BigDecimal)obj[7]).doubleValue());
			ventaPasaje.setCentroCosto(centroCosto);
			ventaPasaje.setUsuario(usuario);
			ventaPasaje.setFechaLiquidacion((Date)obj[10]);
			ventaPasaje.setVentaOriginal(((BigDecimal)obj[12]).longValue());
			ventaPasaje.setPasajero(pasajero);
			ventaPasaje.setAgencia(agencia);
			ventaPasaje.setFechaInsercion((Date)obj[23]);
			ventaPasaje.setFechaPartida((Date)obj[29]);
			ventaPasaje.setRuta(ruta);
			ventaPasaje.setHoraPartida(obj[33]!=null?obj[33].toString():"");
			ventaPasaje.setHoraLllegada(obj[34]!=null?obj[34].toString():"");
			if(obj[39]!=null && !tipoMovimiento.getDenominacion().equals("ANULADO")){
				if(obj[39].toString().equals(Constantes.ESTADO_DOCUMENTO_PAGADO))
					ventaPasaje.setEstadoDocumento("PAGADO");
				else
					ventaPasaje.setEstadoDocumento("PENDIENTE");
			}else
				ventaPasaje.setEstadoDocumento("------");


			//impl: 13/03/2014
			if(obj[30]!=null){
				Agencia agenciaBolref=new Agencia();
				agenciaBolref.setNombreCorto(obj[32]!=null?obj[32].toString():"");

				VentaPasaje ventaPasajeBolRef=new VentaPasaje();
				ventaPasajeBolRef.setId(((BigDecimal)obj[30]).longValue());
				ventaPasajeBolRef.setAgencia(agenciaBolref);
				ventaPasaje.setVentaPasaje(ventaPasajeBolRef);
			}
			if(obj[35]!=null){
				Cliente cliente=new Cliente();
				cliente.setId(((BigDecimal)obj[35]).longValue());
				cliente.setNumeroDocumento(obj[36].toString());
				cliente.setRazonSocial(obj[37].toString());
				ventaPasaje.setCliente(cliente);
			}

			/*implementado 15/08/2015 - jabanto*/
			if(!(isSoles)){
				ventaPasaje.setTarifaEquibalente(((BigDecimal)obj[40]).doubleValue());
				ventaPasaje.setDescuentoEquibalente(((BigDecimal)obj[41]).doubleValue());
				ventaPasaje.setImportePagadoEquibalente(((BigDecimal)obj[42]).doubleValue());
				ventaPasaje.setTipoMoneda(obj[43]!=null?new TipoMoneda(((BigDecimal)obj[43]).intValue()):null);
			}
			ventaPasaje.setTipoComprobante(new TipoComprobante(((BigDecimal)obj[45]).intValue()));
			ventaPasaje.setId(((BigDecimal)obj[12]).longValue());

			lstResult.add(ventaPasaje);
		}

		return lstResult;
	}

	/*
	 * (non-Javadoc)
	 * @see com.tepsa.sisvyr.model.dao.VentaPasajesDAO#validadRCReimpreso(java.lang.Long)
	 */
	@Override
	public VentaPasaje buscarUltimoRegistro(Long idVentaOriginal){
		String sql="SELECT vp.venpas_id" + // 0
						 ",vp.tipcom_id" + // 1
						 ",vp.tipmov_id "+ // 2
						 ",vp.c_numboleto "+
						 ",tm.c_denominacion "+
					"FROM VRTVENPAS VP "+
						"INNER JOIN (SELECT MAX(V.VENPAS_ID)VENPAS_ID FROM VRTVENPAS V WHERE V.VENPAS_IDORIGINAL="+idVentaOriginal+" GROUP BY V.VENPAS_IDORIGINAL)MAX_VENTA "+
					      	"ON (MAX_VENTA.VENPAS_ID=VP.VENPAS_ID) "+
						"INNER JOIN VRMTIPMOV tm ON (tm.tipmov_id=vp.tipmov_id) "+
					"WHERE VP.VENPAS_IDORIGINAL="+idVentaOriginal;
//					"AND VP.TIPCOM_ID IN ("+Constantes.ID_TIPMOV_ANULACION+") "+
//					"GROUP BY VP.VENPAS_IDORIGINAL";

		log.info(sql);
		List<?> result = getSession().createSQLQuery(sql).list();

		VentaPasaje ventaPasaje = null;
		for (Object element : result) {
			Object[] obj = (Object[]) element;

			ventaPasaje = new VentaPasaje();
			TipoComprobante tipoComprobante= new TipoComprobante();
			TipoMovimiento tipoMovimiento= new TipoMovimiento();

			tipoComprobante.setId(((BigDecimal)obj[1]).intValue());
			tipoMovimiento.setId(((BigDecimal)obj[2]).intValue());
			tipoMovimiento.setDenominacion(obj[4].toString());

			ventaPasaje.setId(((BigDecimal)obj[0]).longValue());
			ventaPasaje.setTipoComprobante(tipoComprobante);
			ventaPasaje.setTipoMovimiento(tipoMovimiento);
			if(obj[3]!=null)
				ventaPasaje.setNumeroBoleto(obj[3].toString());
		}

		return ventaPasaje;
	}

	/*
	 * (non-Javadoc)
	 * @see com.tepsa.sisvyr.model.dao.VentaPasajesDAO#activarRecibocaja(java.lang.Long)
	 */
	@Override
	public void activarReciboCaja(Long idVentaOriginal){
		//busca el segundo registro del recibo de caja el cual se genera al reimprimirmo
		TreeMap<String, Object>criteriosBusqueda= new TreeMap<>();
		criteriosBusqueda.put("ventaOriginal", idVentaOriginal);
		List<VentaPasaje>lisVentas=ServiceLocator.getVentaPasajesManager().buscarPorX(criteriosBusqueda, null);

		for(VentaPasaje ventaPasaje: lisVentas){
			/*Busca el Segundo registro del Recibo de caja el cual se genera al reimprimirmo*/
			if(ventaPasaje.getTipoComprobante().getId().intValue()==Constantes.ID_TIPCOM_RECIBO_CAJA &&
					ventaPasaje.getTipoMovimiento().getId().intValue()==Constantes.ID_TIPMOV_ANULACION_SISTEMA){
				/*Quita el venta_idRef del boleto a anular */
				String sql="UPDATE VRTVENPAS SET VENPAS_IDREF="+ventaPasaje.getVentaOriginal()+" WHERE VENPAS_IDREF="+ventaPasaje.getId();
				log.info(sql);
				getSession().createSQLQuery(sql).executeUpdate();

				/*Quita el IdventaOriginal de los boletos asociados a este id*/
				sql="UPDATE VRTVENPAS SET VENPAS_IDORIGINAL=NULL WHERE VENPAS_IDORIGINAL="+ventaPasaje.getVentaOriginal()+" AND VENPAS_ID!="+ventaPasaje.getVentaOriginal();
				log.info(sql);
				getSession().createSQLQuery(sql).executeUpdate();

				/*Elimina el segundo registro del Recibo de caja*/
				sql="DELETE FROM VRTVENPAS WHERE VENPAS_ID="+ventaPasaje.getId();
				log.info(sql);
				getSession().createSQLQuery(sql).executeUpdate();

				/*Activa el registro del Recibo de caja*/
				sql="UPDATE VRTVENPAS SET VENPAS_IDREF=NULL  WHERE VENPAS_ID="+idVentaOriginal;
				log.info(sql);
				getSession().createSQLQuery(sql).executeUpdate();

				break;
			}
		}
	}

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.model.dao.VentaPasajesDAO#buscarVoucherForAnulacion(java.lang.String, java.lang.String)
	 */
	@Override
	public List<VentaPasaje> buscarVoucherForAnulacion(Integer idTipoComprobante,String numVoucher, String numcontrol,String rucCliente,String fechaPartida,String horaPartida,String boleto) {
		if(numVoucher!=null)
			numVoucher="'"+numVoucher.toUpperCase()+"'";
		if(numcontrol!=null)
			numcontrol="'"+numcontrol+"'";
		if(rucCliente!=null)
			rucCliente="'"+rucCliente+"'";
		if(fechaPartida!=null)
			fechaPartida="'"+fechaPartida+"'";
		if(horaPartida!=null)
			horaPartida="'"+horaPartida+"'";
		if(boleto!=null)
			boleto="'"+boleto+"'";

		// TODO Auto-generated method stub
		String sql="SELECT DECODE(vpo.tipmov_id,"+Constantes.ID_TIPMOV_ANULACION+",'ANULADO',"+Constantes.ID_TIPMOV_DEVOLUCION+",'DEVUELTO','ACTIVO') AS estado "+ //0
						   ",vpo.tipmov_id "+ //1
					       ",vpo.c_numcontrol "+ //2
					       ",DECODE(vp.tipcom_id,1,vp.c_numboleto,'') AS Boleto "+ //3
					       ",vpo.c_numboleto AS voucher  "+ //4
					       ",'EFCTIVO' AS TipoPago "+ //5----
					       ",vpo.n_tarifa AS Bruto "+ //6----
					       ",vpo.n_descuento AS Descuento "+ //7
					       ",vpo.n_imppag AS Neto "+ //8
					       ",vpo.cencos_id AS CCosto "+ //9
					       ",vpo.d_fecliq AS FechaVenta "+ //10---
					       ",vpo.venpas_id "+ //11
					       ",cc.c_denominacion "+ //12
					       ",p.c_nomape as paxNombresApellidos "+ //13
					       ",tag.tipage_id "+ //14
					       ",u.c_apepat as userApellidoPaterno "+ //15
					       ",u.c_apemat as userApellidoMaterno "+ //16
					       ",u.c_nombre as userNombres "+ //17
					       ",u.c_login "+ //18
					       ",cc.c_codigo "+ //19
					       ",vpo.audfecins "+ //20
					       ",tc.c_denominacion as TipoComprobante "+ //21
					       ",c.c_razsoc as RazonSocial "+ //22
					       ",r.c_origen as origen "+ //23
					       ",r.c_destino as destino "+ //24
					       ",vpo.d_fecpar as FechaPartida "+ //25
					       ",vpo.c_horpar as HoraPartida "+ //26
					       ",vpo.n_numasiento "+ //27
					       ",vpo.venpas_idref "+//28
					"FROM VRTVENPAS vp "+
					     "INNER JOIN (SELECT MAX(venpas_id)venpas_id, venpas_idoriginal FROM vrtvenpas "+
//					                 "WHERE c_numboleto=NVL("+numVoucher+",c_numboleto) " +
//					                 "AND tipcom_id="+idTipoComprobante+" " +
//					                 "AND c_numcontrol=NVL("+numcontrol+",c_numcontrol) "+
					                 "GROUP BY venpas_idoriginal) max_venta "+
					           "ON max_venta.venpas_id=vp.venpas_id " +
					     "INNER JOIN VRTVENPAS vpo ON (vpo.venpas_id=vp.venpas_idoriginal) "+
					     "INNER JOIN VRMTIPCOM tc ON (tc.tipcom_id=vpo.tipcom_id) " +
					     "INNER JOIN VRMCLIENTE c ON (c.c_numdoc=vpo.c_rucclicre) "+
					     "INNER JOIN VRMUSUARIO u ON (u.usuario_id=vpo.usuario_id) "+
					     "INNER JOIN VRMPASAJERO p ON (p.pasajero_id=vpo.pasajero_id) "+
					     "LEFT JOIN VRMCENCOS cc ON (cc.cencos_id=vpo.cencos_id) "+
					     "INNER JOIN VRMAGENCIA ag ON (ag.agencia_id=vpo.agencia_id) "+
					     "INNER JOIN VRMTIPAGE tag ON (tag.tipage_id=ag.tipage_id) " +
					     "INNER JOIN VRMRUTA r ON (r.ruta_id=vpo.ruta_id) "+
				   "WHERE vpo.forpag_id="+Constantes.ID_FORPAG_CREDITO+" "+
				   		"AND vpo.c_numboleto=NVL("+numVoucher+",vpo.c_numboleto) ";
					if(idTipoComprobante!=null)
					    	 sql+="AND vpo.tipcom_id="+idTipoComprobante+" ";
		            sql+="AND vpo.c_numcontrol=NVL("+numcontrol+",vpo.c_numcontrol) " +
		                 "AND vpo.c_rucclicre=NVL("+rucCliente+",vpo.c_rucclicre) ";
		            if(fechaPartida!=null)
		                 sql+="AND vpo.d_fecpar=to_date("+fechaPartida+",'"+Constantes.DATE_FORMAT+"') ";
		                 sql+="AND vpo.c_horpar=NVL("+horaPartida+",vpo.c_horpar) ";
		            if(boleto!=null)
		            	sql = sql + "AND vpo.c_numbolant=NVL("+boleto+",vpo.c_numbolant) ";


		log.info(sql);
		List<?> result = getSession().createSQLQuery(sql).list();
		List<VentaPasaje> lstResult = new ArrayList<>();
		for(int i=0; i<result.size(); i++){
			Object[] obj = (Object[]) result.get(i);

			TipoMovimiento tipoMovimiento= new TipoMovimiento();
			tipoMovimiento.setDenominacion(obj[0].toString());
			tipoMovimiento.setId(((BigDecimal)obj[1]).intValue());

			VentaPasaje ventaPasaje= new VentaPasaje();
			ventaPasaje.setNumeroControl(obj[2].toString());
			ventaPasaje.setNumeroBoleto(obj[3]!=null?obj[3].toString():"");
			ventaPasaje.setNumeroVoucher(obj[4].toString());
			ventaPasaje.setImportePagado(((BigDecimal)obj[8]).doubleValue());
			ventaPasaje.setDescuento(((BigDecimal)obj[7]).doubleValue());

			CentroCosto centroCosto=null;
			if(obj[9]!=null){
				centroCosto=new CentroCosto();
				centroCosto.setId(((BigDecimal)obj[9]).intValue());
				centroCosto.setDenominacion(obj[12].toString());
				centroCosto.setCodigo(obj[19].toString());
				ventaPasaje.setCentroCosto(centroCosto);
			}

			ventaPasaje.setFechaInsercion((Date)obj[20]);
			ventaPasaje.setId(((BigDecimal)obj[11]).longValue());

			Pasajero pasajero= new Pasajero();
			pasajero.setNombresApellidos(obj[13].toString());

			TipoAgencia  tipoAgencia=new TipoAgencia();
			tipoAgencia.setId(((BigDecimal)obj[14]).intValue());

			Agencia agencia= new Agencia();
			agencia.setTipoAgencia(tipoAgencia);

			Usuario usuario= new Usuario();
			usuario.setApellidoPaterno(obj[15]!=null?obj[15].toString():"");
			usuario.setApellidoMaterno(obj[16]!=null?obj[16].toString():"");
			usuario.setNombre(obj[17]!=null?obj[17].toString():"");

			TipoComprobante tipoComprobante= new TipoComprobante();
			tipoComprobante.setDenominacion(obj[21].toString());

			Cliente cliente= new Cliente();
			cliente.setRazonSocial(obj[22]!=null?obj[22].toString():"");

			Ruta ruta= new Ruta();
			ruta.setOrigen(obj[23].toString());
			ruta.setDestino(obj[24].toString());

			ventaPasaje.setFechaPartida((Date)obj[25]);
			ventaPasaje.setHoraPartida(obj[26].toString());
			ventaPasaje.setNumeroAsiento(((BigDecimal)obj[27]).intValue());

			VentaPasaje ventaReferencial=new VentaPasaje();
			ventaReferencial.setId(obj[28]!=null?((BigDecimal)obj[28]).longValue():null);
			ventaPasaje.setVentaPasaje(ventaReferencial);

			ventaPasaje.setPasajero(pasajero);
			ventaPasaje.setAgencia(agencia);
			ventaPasaje.setUsuario(usuario);
			ventaPasaje.setTipoComprobante(tipoComprobante);
			ventaPasaje.setCliente(cliente);
			ventaPasaje.setRuta(ruta);
			ventaPasaje.setTipoMovimiento(tipoMovimiento);

			lstResult.add(ventaPasaje);
		}

		return lstResult;
	}

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.model.dao.VentaPasajesDAO#buscarEstadoVentasReservas(java.lang.String, java.lang.String, java.lang.Integer, java.lang.String, java.lang.Integer, java.lang.String, java.lang.String, java.lang.Integer, java.lang.String, java.lang.String, java.lang.Integer, java.lang.Integer, java.lang.Integer)
	 */
	@Override
	public List<VentaPasaje> buscarEstadoVentasReservas(String fechaInicialVenta,String fechaFinVenta, Integer tipoBusqCliente, String busqCliente,Integer tipoBusqPax, String busqPax, String fechaPartida,
			Integer idUsuario, String numeroControl, String numeroBoleto,Integer idOrigen, Integer idDestino, Integer tipoMovimiento)throws Exception {
		int BUSQ_CLIENTE_RUC=0;
		int BUSQ_CLIENTE_RAZONSOCIAL=1;
		int BUSQ_PAX_NRO_DOCUMENTO=0;
		int BUSQ_PAX_NOMBRES=1;

//		if(fechaPartida!=null) fechaPartida="'"+fechaPartida+"'";
		if(numeroControl!=null) numeroControl="'"+numeroControl+"'";
		if(numeroBoleto!=null) numeroBoleto="'"+numeroBoleto+"'";

		String sql="SELECT vp.venpas_id AS idVenta "+ //0
					      ",r.c_origen AS Origen "+ //1
					      ",r.c_destino AS Destino "+ //2
					      ",vp.d_fecpar AS FechaPartida "+ //3
					      ",vp.c_horpar AS HoraPartida "+ //4
					      ",vp.c_numcontrol AS NumeroControl "+ //5
					      ",p.c_nomape AS NombreApellidos "+ //6
					      ",vp.c_numboleto AS numeroBoleto "+//7
						  ",DECODE(tm.tipmov_id,1,'VENTA EFECTIVO','4','VENTA CREDITO','13','ANULADO','5','ANULADO REIMP',tm.c_denominacion) as Tipo"+ //8
					      ",c.c_Razsoc AS Cliente ," +//9
					      "vp.d_fecliq AS fechaVenta"+ //10
					      ",vp.tipmov_id " +//11
					      ",vp.n_numasiento" + //12
					      ",vp.audfecins "+//13
					      ",tc.tipcom_id, tc.c_denominacion tipoComprobante " + //14-15
					      ",vp.n_esfe "+//16
					      ",vp.venpas_idoriginal, vp.forpag_id "+//17 -18
					"FROM VRTVENPAS vp ";

		//Omite el MAX venpas_id Cunado la busqueda es por numero de control o numero de boleto.
		if(numeroBoleto==null && numeroControl==null)
			sql+="INNER JOIN (SELECT MAX(venpas_id)venpas_id FROM VRTVENPAS GROUP BY c_numcontrol)venpas_max "+
							"ON (venpas_max.venpas_id=vp.venpas_id) ";

		sql+="LEFT JOIN VRMRUTA r ON (r.ruta_id=vp.ruta_id) "+
		     "INNER JOIN VRMPASAJERO p ON (p.pasajero_id=vp.pasajero_id) "+
		     "LEFT JOIN VRMCLIENTE c ON (c.cliente_id=vp.cliente_id) " +
		     "INNER JOIN VRMTIPMOV tm ON(tm.tipmov_id=vp.tipmov_id) " +
		     "INNER JOIN VRMTIPCOM tc ON(tc.tipcom_id=vp.tipcom_id) " +
		     "WHERE vp.c_estreg='A' ";
		//Rango fechas de venta
		if(fechaInicialVenta!=null && fechaFinVenta!=null)
			sql+="AND vp.d_fecliq BETWEEN to_date('"+fechaInicialVenta+"','"+Constantes.DATE_FORMAT+"') AND to_date('"+fechaFinVenta+"','"+Constantes.DATE_FORMAT+"') ";
		// Para buscar por Cliente
		if(busqCliente!=null){
			if(tipoBusqCliente.intValue()==BUSQ_CLIENTE_RUC)
		    	 sql+="AND c.c_numdoc='"+busqCliente+"' ";
			else if(tipoBusqCliente.intValue()==BUSQ_CLIENTE_RAZONSOCIAL)
				 sql+="AND c.c_razsoc LIKE '"+busqCliente+"%' ";
		}
		//Para buscar por Pasajero
		if(busqPax!=null){
			if(tipoBusqPax.intValue()==BUSQ_PAX_NRO_DOCUMENTO)
				sql+="AND p.c_numdoc='"+busqPax+"' ";
			else if (tipoBusqPax.intValue()==BUSQ_PAX_NOMBRES)
				sql+="AND p.c_nomape LIKE '"+busqPax+"%' ";
		}
		//Si es un venta o reserva
		if(tipoMovimiento!=null){
			if(tipoMovimiento.intValue()==Integer.valueOf(Constantes.TIPO_OPERACION_VENTA) || tipoMovimiento.intValue()==Integer.valueOf(Constantes.TIPO_OPERACION_RESERVA) || tipoMovimiento.intValue()==Integer.valueOf(Constantes.TIPO_OPERACION_VARIOS))
				sql+="AND vp.c_tiptra="+tipoMovimiento+" ";
			else if(tipoMovimiento == Constantes.ID_TIPMOV_FECHA_ABIERTA)//Busca ventas a fecha aviaerta
				sql+="AND vp.c_tiptra="+Constantes.TIPO_OPERACION_VENTA+" AND vp.tipmov_id="+Constantes.ID_TIPMOV_FECHA_ABIERTA+" AND  vp.n_esfecabi=1";
			else
				sql+="AND vp.c_tiptra="+Constantes.TIPO_OPERACION_VENTA+" AND vp.tipmov_id="+Constantes.ID_TIPMOV_POSTERGACION_FA+" AND  vp.n_esfecabi=1";
		}

		//Por fecha de partida
		if(fechaPartida!=null)
			sql+="AND vp.d_fecpar=to_date('"+fechaPartida+"','"+Constantes.DATE_FORMAT+"') ";
		if(numeroBoleto!=null)
			sql+="AND vp.c_numboleto="+numeroBoleto.toUpperCase()+" ";

	     sql+="AND vp.usuario_id=NVL("+idUsuario+",vp.usuario_id) "+
	     "AND vp.c_numcontrol=NVL("+numeroControl+",vp.c_numcontrol) "+
	     "AND r.localidad_idorigen=NVL("+idOrigen+",r.localidad_idorigen) "+
	     "AND r.localidad_iddestino=NVL("+idDestino+",r.localidad_iddestino) ";

	     if(numeroBoleto==null && numeroControl==null)
	    	 sql+="AND vp.tipmov_id NOT IN ("+Constantes.ID_TIPMOV_ANULACION_SISTEMA+") ";
//	    	 	"ORDER BY vp.d_fecliq, vp.n_numasiento ";
//	     }else
	     sql+="ORDER BY vp.d_fecpar, vp.n_numasiento  ";


		log.info(sql);
		List<?> result = getSession().createSQLQuery(sql).list();
		List<VentaPasaje> lstResult = new ArrayList<>();
		for(int i=0; i<result.size(); i++){
			Object[] obj = (Object[]) result.get(i);

			Ruta ruta= new Ruta();
			ruta.setOrigen(obj[1]!=null?obj[1].toString():"");
			ruta.setDestino(obj[2]!=null?obj[2].toString():"");

			Pasajero pasajero=new Pasajero();
			pasajero.setNombresApellidos(obj[6].toString());

			Cliente cliente= new Cliente();
			cliente.setRazonSocial(obj[9]!=null?obj[9].toString():"");

			TipoMovimiento otipoMovimiento= new TipoMovimiento();
			otipoMovimiento.setId(((BigDecimal)obj[11]).intValue());

			VentaPasaje ventaPasaje= new VentaPasaje();
			ventaPasaje.setId(((BigDecimal)obj[0]).longValue());
			ventaPasaje.setFechaPartida(obj[3]!=null?(Date)obj[3]:null);
			ventaPasaje.setHoraPartida(obj[4]!=null?obj[4].toString():"");
			ventaPasaje.setNumeroControl(obj[5].toString());
			ventaPasaje.setNumeroBoleto(obj[7]!=null?obj[7].toString():"");
			ventaPasaje.setFechaLiquidacion(obj[10]!=null?(Date)obj[10]:null);
//			ventaPasaje.setEstadoRegistro(obj[8].toString());
			ventaPasaje.setTipoTransaccion(obj[8].toString());
			ventaPasaje.setNumeroAsiento(obj[12]!=null?((BigDecimal)obj[12]).intValue():null);
			ventaPasaje.setRuta(ruta);
			ventaPasaje.setPasajero(pasajero);
			ventaPasaje.setCliente(cliente);
			ventaPasaje.setTipoMovimiento(otipoMovimiento);
			ventaPasaje.setFechaInsercion(obj[13]!=null?(Date)obj[13]:null);
			if(obj[18]!=null)
				ventaPasaje.setFormaPago(new FormaPago(((BigDecimal)obj[18]).intValue()));

			TipoComprobante tipoComprobante= new TipoComprobante();
			tipoComprobante.setId(((BigDecimal)obj[14]).intValue());
			tipoComprobante.setDenominacion(obj[15].toString());
			ventaPasaje.setTipoComprobante(tipoComprobante);
			ventaPasaje.setEnviadoSFE(obj[16]!=null?((BigDecimal)obj[16]).intValue():null);
			ventaPasaje.setVentaOriginal(obj[17]!=null?((BigDecimal)obj[17]).longValue():null);

			lstResult.add(ventaPasaje);
		}

		return lstResult;
	}

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.model.dao.VentaPasajesDAO#buscarOperacionesRemotras(java.lang.String, java.lang.Integer, java.lang.Integer, java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	public List<VentaPasaje> buscarOperacionesRemotras(String fechaPartida,Integer idOrigen, Integer idDestino, String numeroBoleto,String numeroControl, String documentoPax) throws Exception {

		numeroBoleto=numeroBoleto!=null?"'"+numeroBoleto.toUpperCase()+"'":numeroBoleto;
		numeroControl=numeroControl!=null?"'"+numeroControl+"'":numeroControl;

		String sql="SELECT vp.venpas_id "+ //0
				      ",fp.c_denominacion AS FormaPago "+ //1
				      ",vp.c_numboleto AS numeroBoleto "+ //2
				      ",vp.c_numcontrol AS numeroControl "+ //3
				      ",p.c_nomape AS Pasajero "+ //4
				      ",s.c_Denominacion AS Servicio "+ //5
				      ",r.c_origen AS Origen "+ //6
				      ",r.c_destino As Destino "+ //7
				      ",vp.d_Fecpar AS FechaPartida "+ //8
				      ",vp.c_horpar AS HoraPartida "+ //9
				      ",vp.n_numasiento AS NumeroAsiento "+ //10
				      ",vp.tipmov_id "+ //11
				"FROM VRTVENPAS vp "+
				     "INNER JOIN (SELECT MAX(venpas_id)venpas_id FROM VRTVENPAS GROUP BY c_numcontrol)venpas_max ON (venpas_max.venpas_id=vp.venpas_id) "+
				     "INNER JOIN VRMFORPAG fp ON (fp.forpag_id=vp.forpag_id) "+
				     "INNER JOIN VRMPASAJERO p ON (p.pasajero_id=vp.pasajero_id) "+
				     "INNER JOIN VRMSERVICIO s ON (s.servicio_id=vp.servicio_id) "+
				     "INNER JOIN VRMRUTA r ON (r.ruta_id=vp.ruta_id) "+
				"WHERE vp.d_fecpar=to_date('"+fechaPartida+"','"+Constantes.DATE_FORMAT+"') "+
				     "AND vp.tipmov_id NOT IN ("+Constantes.ID_TIPMOV_ANULACION+","+Constantes.ID_TIPMOV_ANULACION_SISTEMA+","+Constantes.ID_TIPMOV_DEVOLUCION+","+Constantes.ID_TIPMOV_GASTOS_ADMINISTRATIVOS+") "+
				     "AND vp.c_tiptra="+Constantes.TIPO_OPERACION_VENTA+" "+
				     "AND vp.c_estreg='"+Constantes.VALUE_ACTIVO+"' "+
				     "AND r.localidad_idorigen=NVL("+idOrigen+",r.localidad_idorigen) "+
				     "AND r.localidad_iddestino=NVL("+idDestino+",r.localidad_iddestino) "+
				     "AND vp.c_numboleto=NVL("+numeroBoleto+",vp.c_numboleto) "+
				     "AND vp.c_numcontrol=NVL("+numeroControl+",vp.c_numcontrol) " +
				     "AND vp.tipcom_id in ("+Constantes.ID_TIPCOM_BOLETO_VIAJE+","+Constantes.ID_TIPCOM_BOLETA_VENTA+","+Constantes.ID_TIPCOM_FACTURA+") "+
				"ORDER BY vp.d_fecpar,vp.c_horpar, vp.n_numasiento";

		log.info(sql);
		List<?> result = getSession().createSQLQuery(sql).list();
		List<VentaPasaje> lstResult = new ArrayList<>();
		for(int i=0; i<result.size(); i++){
			Object[] obj = (Object[]) result.get(i);

			FormaPago formaPago=new FormaPago();
			formaPago.setDenominacion(obj[1].toString());

			Pasajero pasajero=new Pasajero();
			pasajero.setNombresApellidos(obj[4].toString());

			Servicio servicio=new Servicio();
			servicio.setDenominacion(obj[5].toString());

			Ruta ruta=new Ruta();
			ruta.setOrigen(obj[6].toString());
			ruta.setDestino(obj[7].toString());

			TipoMovimiento tipoMovimiento=new TipoMovimiento();
			tipoMovimiento.setId(((BigDecimal)obj[11]).intValue());

			VentaPasaje ventaPasaje= new VentaPasaje();
			ventaPasaje.setId(((BigDecimal)obj[0]).longValue());
			ventaPasaje.setNumeroBoleto(obj[2].toString());
			ventaPasaje.setNumeroControl(obj[3].toString());
			ventaPasaje.setFechaPartida((Date)obj[8]);
			ventaPasaje.setHoraPartida(obj[9].toString());
			ventaPasaje.setNumeroAsiento(((BigDecimal)obj[10]).intValue());
			ventaPasaje.setFormaPago(formaPago);
			ventaPasaje.setPasajero(pasajero);
			ventaPasaje.setServicio(servicio);
			ventaPasaje.setRuta(ruta);
			ventaPasaje.setTipoMovimiento(tipoMovimiento);

			lstResult.add(ventaPasaje);
		}

		return lstResult;
	}

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.model.dao.VentaPasajesDAO#buscarTotalVentas(java.lang.String, java.lang.Integer, java.lang.Integer)
	 */
	@Override
	public Double buscarTotalVentas(String fechaLiquidacion, Integer idAgencia,Integer idUsuario) {
		String sql="SELECT SUM(VP.n_Imppag)as total "+
					"FROM VRTVENPAS vp "+
					     "INNER JOIN (SELECT MAX(v.venpas_id)venpas_id FROM VRTVENPAS v GROUP BY v.c_numcontrol)maxidVenPas "+
					           "ON (maxidVenPas.venpas_id=vp.venpas_id) "+
					"WHERE vp.c_tiptra='1' "+
					      "AND vp.tipmov_id NOT IN ("+Constantes.ID_TIPMOV_ANULACION+","+Constantes.ID_TIPMOV_ANULACION_SISTEMA+","+Constantes.ID_TIPMOV_DEVOLUCION+") "+
					      "AND vp.agencia_id="+idAgencia+" "+
					      "AND vp.usuario_id="+idUsuario+" "+
					      "AND vp.c_estreg='A'";

		log.info(sql);
		Object total = getSession().createSQLQuery(sql).uniqueResult();

		return total==null?0.0:((BigDecimal)total).doubleValue();
	}
	/*
	 * (non-Javadoc)
	 * @see com.tepsa.sisvyr.model.dao.VentaPasajesDAO#buscarVentasByPasajero(java.lang.Long)
	 */
	@Override
	public List<VentaPasaje> buscarVentasByPasajero(Long idPasajero, String numeroBoleto, Boolean incluirReservas){
//		String hql = "SELECT vp "
//				   + "FROM VentaPasaje AS vp JOIN vp.pasajero AS p "
//                   + "WHERE vp.tipoComprobante.id IN ("+ Constantes.ID_TIPCOM_BOLETA_VENTA+","+ Constantes.ID_TIPCOM_FACTURA+") ";
//		if(incluirReservas!=null && incluirReservas)
//			hql += "AND vp.tipoTransaccion IN ('1','2') AND vp.tipoMovimiento.id NOT IN (5,13)";
//		else
//			hql += "AND vp.tipoTransaccion IN ('1') AND vp.tipoMovimiento.id NOT IN (5,11,13)";
//		if(idPasajero!=null)
//			hql +="AND vp.pasajero.id="+ idPasajero +" ";
//		if(numeroBoleto!=null)
//			hql +="AND vp.numeroBoleto='"+numeroBoleto+"' ";
//		hql += "ORDER BY vp.fechaPartida desc ";
				
		
		
		String sql = "SELECT {VP.*} FROM vrtvenpas {VP} " +
					"INNER JOIN (SELECT MAX(venpas_id)venpas_id, c_numcontrol FROM vrtvenpas GROUP BY c_numcontrol) max_vta " +
					"ON max_vta.venpas_id={VP}.venpas_id " +
					"INNER JOIN vrmpasajero p ON p.pasajero_id={VP}.pasajero_id ";
		
		if(incluirReservas!=null && incluirReservas)
			sql += "AND {VP}.c_tiptra IN ('1','2') AND {VP}.tipmov_id NOT IN (5,13)";
		else
			sql += "AND {VP}.c_tiptra IN ('1') AND {VP}.tipmov_id NOT IN (5,11,13)";
		if(idPasajero!=null)
			sql +="AND {VP}.pasajero_id="+ idPasajero +" ";
		if(numeroBoleto!=null)
			sql +="AND {VP}.c_numBoleto='"+numeroBoleto+"' ";
		sql += "ORDER BY {VP}.d_fecpar desc, {VP}.c_horpar desc ";
		
//				"WHERE {VP}.c_tiptra in('"+Constantes.TIPO_OPERACION_VENTA+"','"+Constantes.TIPO_OPERACION_VENTA_POOL+"','"+Constantes.TIPO_OPERACION_PERDIDA_SERVICIO+"') AND {VP}.c_estreg='"+Constantes.VALUE_ACTIVO+"' "
//			  + "AND {VP}.tipcom_id IN ("+Constantes.ID_TIPCOM_BOLETO_VIAJE+","+Constantes.ID_TIPCOM_BOLETA_VENTA+", "+Constantes.ID_TIPCOM_FACTURA+")" ;
//		sql = sql + "ORDER BY {VP}.c_numboleto, {VP}.d_fecpar, {VP}.c_horpar ";

		log.info(sql);
		List<?> result = getSession().createSQLQuery(sql).addEntity("VP",VentaPasaje.class).list();
		
//                   	  +"AND vp.tipoMovimiento.id NOT IN (5,11,13) "
//					  +(incluirReservas!=null && incluirReservas?"AND vp.tipoTransaccion in ('1','2') ":"AND vp.tipoTransaccion in ('1') ")
//                   	  +(idPasajero!=null?" AND vp.pasajero.id="+idPasajero+" ":"")
//                   	  +(numeroBoleto!=null?" AND vp.numeroBoleto='"+numeroBoleto+"' ":"")
//				   + "ORDER BY vp.fechaPartida desc ";

//		log.info("buscarVentasByPasajero:"+ hql);
		//List<?> result = getSession().createQuery(hql).list();
		List<VentaPasaje>lstResult = new ArrayList<>();
		for(int i=0; i<result.size(); i++){
			VentaPasaje ventaPasaje = (VentaPasaje)result.get(i);
			VentaPasaje venta = new VentaPasaje();
			venta.setId(ventaPasaje.getId());
			venta.setNumeroBoleto(ventaPasaje.getNumeroBoleto());
			venta.setNumeroBoletoAnterior(ventaPasaje.getNumeroBoletoAnterior());
			venta.setFechaLiquidacion(ventaPasaje.getFechaLiquidacion());
			venta.setTipoTransaccion(ventaPasaje.getTipoTransaccion());
			if(venta.getCliente()!=null){
				Cliente cliente = new Cliente();
				cliente.setRazonSocial(ventaPasaje.getCliente().getRazonSocial());
				venta.setCliente(cliente);
			}
			Empresa empresa = new Empresa();
			empresa.setId(ventaPasaje.getEmpresa().getId());
			empresa.setRazonSocial(ventaPasaje.getEmpresa().getRazonSocial());
			empresa.setNombreCorto(ventaPasaje.getEmpresa().getNombreCorto());
			venta.setEmpresa(empresa);
			Servicio servicio = new Servicio();
			servicio.setDenominacion(ventaPasaje.getServicio().getDenominacion());
			venta.setServicio(servicio);
			Ruta ruta = new Ruta();
			ruta.setOrigen(ventaPasaje.getRuta().getOrigen());
			ruta.setDestino(ventaPasaje.getRuta().getDestino());
			venta.setRuta(ruta);
			venta.setFechaPartida(ventaPasaje.getFechaPartida());
			venta.setHoraPartida(ventaPasaje.getHoraPartida()!=null?ventaPasaje.getHoraPartida():null);
			if(ventaPasaje.getTipoFormaPago()!=null) {
				TipoFormaPago tipoFormaPago = new TipoFormaPago();
				tipoFormaPago.setDenominacion(ventaPasaje.getTipoFormaPago().getDenominacion());
				venta.setTipoFormaPago(tipoFormaPago);	
			}
			venta.setFechaCaducidad(ventaPasaje.getFechaCaducidad()!=null?ventaPasaje.getFechaCaducidad():null);
			venta.setFechaExpiracionReserva(ventaPasaje.getFechaExpiracionReserva()!=null?ventaPasaje.getFechaExpiracionReserva():null);
			venta.setNumeroAsiento(ventaPasaje.getNumeroAsiento()!=null?ventaPasaje.getNumeroAsiento():null);
			venta.setImportePagado(ventaPasaje.getImportePagado());
			venta.setFechaInsercion(ventaPasaje.getFechaInsercion());
			TipoMovimiento tipoMovimiento = new TipoMovimiento();
			tipoMovimiento.setId(ventaPasaje.getTipoMovimiento().getId());
			tipoMovimiento.setDenominacion(ventaPasaje.getTipoMovimiento().getDenominacion());
			venta.setTipoMovimiento(tipoMovimiento);
			Usuario usuario = new Usuario();
			usuario.setNombre(ventaPasaje.getUsuario().getNombre());
			usuario.setApellidoPaterno(ventaPasaje.getUsuario().getApellidoPaterno());
			usuario.setApellidoMaterno(ventaPasaje.getUsuario().getApellidoMaterno());
			usuario.setLogin(ventaPasaje.getUsuario().getLogin());
			venta.setUsuario(usuario);
			Pasajero pasajero = new Pasajero();
			pasajero.setId(ventaPasaje.getPasajero().getId());
			pasajero.setNumeroDocumento(ventaPasaje.getPasajero().getNumeroDocumento());
			pasajero.setNombre(ventaPasaje.getPasajero().getNombre());
			pasajero.setApellidoPaterno(ventaPasaje.getPasajero().getApellidoPaterno());
			pasajero.setApellidoMaterno(ventaPasaje.getPasajero().getApellidoMaterno()!=null?ventaPasaje.getPasajero().getApellidoMaterno():null);
			pasajero.setNombresApellidos(ventaPasaje.getPasajero().getNombresApellidos()!=null?ventaPasaje.getPasajero().getNombresApellidos():null);
			venta.setPasajero(pasajero);
			lstResult.add(venta);
		}
		return lstResult;
	}

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.model.dao.VentaPasajesDAO#buscarBoletoLiberarManifiesto(java.lang.String)
	 */
	@Override
	public List<VentaPasaje> buscarBoletoLiberarManifiesto(String numeroboleto)throws Exception {
		String sql="SELECT vp.venpas_id,vp.c_numboleto,vp.n_numasiento, p.c_apepat, p.c_apemat,p.c_nombre,p.c_numdoc, " +
						  "td.c_denominacion as tipoDocumento,rt.c_origen, rt.c_destino, " +
						  "pe.c_nomcor as AgenciaPartida " +
					"FROM VRTVENPAS VP " +
						"INNER JOIN VRMPASAJERO p ON (p.pasajero_id=vp.pasajero_id) " +
						"INNER JOIN VRMTIPDOC td ON (td.tipdoc_id=p.tipdoc_id) " +
						"INNER JOIN VRMRUTA rt ON (rt.ruta_id=vp.ruta_id) " +
						"INNER JOIN VRMAGENCIA pe ON (pe.agencia_id=vp.agencia_idpartida) " +
						"INNER JOIN VRTDETMAN dm ON (dm.venpas_id=vp.venpas_id) " +
						"INNER JOIN VRTMANIFIESTO m ON (m.manifiesto_id=dm.manifiesto_id) " +
					"WHERE vp.c_numboleto='"+numeroboleto.toUpperCase()+"' " +
					    "AND vp.c_estreg='A' " +
					    "AND dm.c_estreg='A' " +
					    "AND m.c_estreg='A' ";
		log.info(sql);
		List<?> result = getSession().createSQLQuery(sql).list();
		List<VentaPasaje>lstResult = new ArrayList<>();
		for(int i=0; i<result.size(); i++){
			Object[] obj = (Object[])result.get(i);

			VentaPasaje ventaPasaje=new VentaPasaje();
			ventaPasaje.setId(((BigDecimal)obj[0]).longValue());
			ventaPasaje.setNumeroBoleto(obj[1].toString());
			ventaPasaje.setNumeroAsiento(((BigDecimal)obj[2]).intValue());

			Pasajero pasajero=new Pasajero();
			pasajero.setApellidoPaterno(obj[3]!=null?obj[3].toString():null);
			pasajero.setApellidoMaterno(obj[4]!=null?obj[4].toString():null);
			pasajero.setNombre(obj[5]!=null?obj[5].toString():null);
			pasajero.setNumeroDocumento(obj[6]!=null?obj[6].toString():null);

			TipoDocumento tipoDocumento=new TipoDocumento();
			tipoDocumento.setDenominacion(obj[7].toString());
			pasajero.setTipoDocumento(tipoDocumento);

			Ruta ruta=new Ruta();
			ruta.setOrigen(obj[8].toString());
			ruta.setDestino(obj[9].toString());

			Agencia agenciaPartida=new Agencia();
			agenciaPartida.setNombreCorto(obj[10].toString());

			ventaPasaje.setPasajero(pasajero);
			ventaPasaje.setRuta(ruta);
			ventaPasaje.setAgenciaPartida(agenciaPartida);

			lstResult.add(ventaPasaje);
		}

		return lstResult;
	}

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.model.dao.VentaPasajesDAO#buscarBoletoByAsegurado(java.lang.String)
	 */
	@Override
	public VentaPasaje buscarBoletoByAsegurado(String numeroBoleto)throws Exception {
		String sql="SELECT "+
				         "DECODE(td.tipdoc_id,8,4,td.tipdoc_id)  AS idTipoDocumento "+ //asigna el valor segun el tipo de documento registro en la ventaSeguro.
						 ",td.c_denominacion AS tipoDocumento "+
						 ",p.pasajero_id AS idPasajero "+
						 ",p.c_numdoc AS numeroDocumento "+
						 ",p.c_apepat AS apellidoPaterno "+
						 ",p.c_apemat AS apellidoMaterno "+
						 ",p.c_nombre AS nombres "+
						 ",p.c_fecnac AS fechaNacimiento "+
						 ",p.c_direccion AS direccion "+
						 ",p.ubigeo_id AS idUbigeo "+
						 ",r.ruta_id AS idRuta "+
						 ",r.c_origen AS Origen "+
						 ",r.c_destino AS Destino "+
						 ",vp.venpas_id idVenta "+
						 ",vp.d_fecliq AS fechaVenta "+
						 ",vp.d_fecpar AS fechaPartida "+
						 ",pf.paxfre_id AS idPaxFree "+
						 ",p.c_telefono AS telefono "+
						 ",DECODE(ec.estciv_id,1,2,DECODE(ec.estciv_id,2,1,DECODE(ec.estciv_id,3,5,ec.estciv_id))) AS estadoCivil "+
						 ",sx.sexo_id AS idSexo "+
						 ",ub.c_coddpto AS codigoDepartamento "+
						 ",ub.c_codprov AS codigoProvincia "+
						 ",ub.c_coddist As codigoDistrito "+
						 ",n.nacionalidad_id "+
						 ",p.c_email "+
				   "FROM VRTVENPAS vp  "+
				    	"INNER JOIN VRMPASAJERO p ON (p.pasajero_id=vp.pasajero_id) "+
				    	"INNER JOIN VRMUBIGEO ub ON (ub.ubigeo_id=p.ubigeo_id) "+
				    	"INNER JOIN VRMTIPDOC td ON (td.tipdoc_id=p.tipdoc_id) "+
				    	"INNER JOIN VRMSEXO sx ON (sx.sexo_id=p.sexo_id) "+
				    	"LEFT JOIN VRMESTCIV ec ON (ec.estciv_id=p.estciv_id) "+
				    	"INNER JOIN VRMRUTA r ON (r.ruta_id=vp.ruta_id) "+
				    	"LEFT JOIN VRTPAXFREE pf ON (pf.pasajero_id=vp.pasajero_id AND pf.n_estado=1 AND pf.c_estreg='A' ) "+
				    	"LEFT JOIN VRMNACIONALIDAD n ON (n.nacionalidad_id=p.nacionalidad_id) "+
				      "INNER JOIN (SELECT MAX(v.venpas_id) venpas_id FROM VRTVENPAS v GROUP BY v.c_numcontrol)vp_max "+
				      		"ON (vp_max.venpas_id=vp.venpas_id) "+
				    "WHERE  vp.tipmov_id NOT IN ("+Constantes.ID_TIPMOV_ANULACION+","+Constantes.ID_TIPMOV_ANULACION_SISTEMA+", "+Constantes.ID_TIPMOV_DEVOLUCION+","+Constantes.ID_TIPMOV_FECHA_ABIERTA+") "+
				       "AND vp.c_estreg='"+Constantes.VALUE_ACTIVO+"' "+
				       "AND vp.tipcom_id IN ("+Constantes.ID_TIPCOM_BOLETO_VIAJE+") "+
				       "AND vp.c_numboleto='"+numeroBoleto.toString()+"' ";

		log.info(sql);
		List<?> result=getSession().createSQLQuery(sql).list();

		VentaPasaje ventaPasaje=null;
		for (Object element : result) {
			Object[] obj=(Object[]) element;

			TipoDocumento tipoDocumento=new TipoDocumento();
			tipoDocumento.setId(((BigDecimal)obj[0]).intValue());
			tipoDocumento.setDenominacion(obj[1].toString());

			Pasajero pasajero=new Pasajero();
			pasajero.setId(((BigDecimal)obj[2]).longValue());
			pasajero.setNumeroDocumento(obj[3]!=null?obj[3].toString():null);
			pasajero.setApellidoPaterno(obj[4].toString());
			pasajero.setApellidoMaterno(obj[5]!=null?obj[5].toString():null);
			pasajero.setNombre(obj[6].toString());
			pasajero.setFechaNacimiento(obj[7]!=null?obj[7].toString():null);
			pasajero.setDireccion(obj[8]!=null?obj[8].toString():null);
			pasajero.setEmail(obj[24]!=null?obj[24].toString():null);
			pasajero.setTipoDocumento(tipoDocumento);
			if(obj[16]!=null)//Evalua si es posajero frecuente.
				pasajero.setPaxFree(true);
			else
				pasajero.setPaxFree(false);
			pasajero.setTelefono(obj[17]!=null?obj[17].toString():null);

			if(obj[18]!=null){
				EstadoCivil estadoCivil=new EstadoCivil();
				estadoCivil.setId(((BigDecimal)obj[18]).intValue());
				pasajero.setEstadoCivil(estadoCivil);
			}

			Sexo sexo=new Sexo();
			sexo.setId(((BigDecimal)obj[19]).intValue());
			pasajero.setSexo(sexo);

			Ubigeo ubigeo=new Ubigeo();
			ubigeo.setId(obj[9].toString());
			ubigeo.setCodigoDepartamento(obj[20].toString());
			ubigeo.setCodigoProvincia(obj[21].toString());
			ubigeo.setCodigoDistrito(obj[22].toString());
			pasajero.setUbigeo(ubigeo);

			if(obj[23]!=null)
				pasajero.setNacionalidad(new Nacionalidad(((BigDecimal)obj[23]).intValue()));

			Ruta ruta=new Ruta();
			ruta.setId(((BigDecimal)obj[10]).intValue());
			ruta.setOrigen(obj[11].toString());
			ruta.setDestino(obj[12].toString());

			ventaPasaje=new VentaPasaje();
			ventaPasaje.setId(((BigDecimal)obj[13]).longValue());
			ventaPasaje.setFechaLiquidacion((Date)obj[14]);
			ventaPasaje.setFechaPartida((Date)obj[15]);
			ventaPasaje.setPasajero(pasajero);
			ventaPasaje.setRuta(ruta);
		}


		return ventaPasaje;
	}

	/*
	 * (non-Javadoc)
	 * @see com.tepsa.sisvyr.model.dao.VentaPasajesDAO#buscarPasajerosByME(java.lang.Long)
	 */
	@Override
	public List<VentaPasaje> buscarPasajerosByME(Long itinearioId) throws Exception {
//		String sql="SELECT td.tipdoc_id  "
//					   + ",td.c_Denominacion tipoDocumento "
//				       + ",pax.c_numdoc numdocumento "
//				       + ",pax.c_nombre nombre "
//				       + ",pax.c_apepat paterno "
//				       + ",pax.c_apemat materno "
//				       + ",vp.c_numboleto boleto "
//				       + ",vp.n_tarifa+n_recargo-n_descuento monto "
//				       + ",vp.n_numasiento asiento "
//				       + ",vp.d_fecpar "
//				       + ",m.manifiesto_id "
//				  + "FROM VRTDETMAN dm "
//				     + "INNER JOIN VRTMANIFIESTO m ON (m.manifiesto_id=dm.manifiesto_id) "
//				     + "INNER JOIN VRTVENPAS vp ON (vp.venpas_id=dm.venpas_id) "
//				     + "INNER JOIN VRMPASAJERO pax ON (pax.pasajero_id=vp.pasajero_id) "
//				     + "INNER JOIN VRMTIPDOC td ON (td.tipdoc_id=pax.tipdoc_id) "
////				  + "WHERE td.Tipdoc_Id!="+Constantes.ID_TIPDOC_SN+" "
//				  + "WHERE m.c_estreg='"+Constantes.VALUE_ACTIVO+"' AND dm.c_estreg='"+Constantes.VALUE_ACTIVO+"' "
//					 + "AND m.itinerario_id="+itinearioId+" "
//				  + "ORDER BY vp.n_numasiento";
		String sql ="SELECT  "
//						   + "v.n_numasiento, v.c_numboleto, p.c_apepat ApePaterno, p.c_apemat ApeMat, p.c_nombre Nom, p.c_fecnac, "+ //0-5
//							"p.c_numdoc numDoc, r.c_origen origen, r.c_destino destino, (v.n_tarifa+v.n_recargo-v.n_descuento) imppag, "+ //6-9
//							"pa.c_denominacion preali, ap.c_denominacion PtoEmbarque , v.c_numControl, "+ //10-12
//							"td.c_Denominacion TipDocto, v.venpas_id, "+ //13-14
//							"ap.c_nomcor as NombreCorto, v.n_numpiso, tc.tipcom_id, tc.c_Denominacion," + //15-18
//							"p.pasajero_id, fp.forpag_id, fp.c_denominacion formaPago,cv.c_nomcor canalVenta, c.c_razsoc clienteCredito, av.c_nomcor agenciaVenta, "+// 19-24
//							"r.ruta_id, td.c_nomcor nomCorTipoDocumento "+//25-26
						+" td.tipdoc_id ,td.c_Denominacion tipoDocumento   "
					    + ",pax.c_numdoc numdocumento "
					    + ",pax.c_nombre nombre "
					    + ",pax.c_apepat paterno "
					    + ",pax.c_apemat materno "
					    + ",v.c_numboleto boleto "
					    + ",v.n_tarifa+n_recargo-n_descuento monto "
					    + ",v.n_numasiento asiento "
					    + ",v.d_fecpar "+
					"FROM vrtvenpas v "+
						"INNER JOIN (SELECT MAX(venpas_id)venpas_id, c_numcontrol " +
									"FROM vrtvenpas WHERE itinerario_id="+itinearioId+" GROUP BY c_numcontrol) max_venta " +
									"ON max_venta.venpas_id=v.venpas_id " +
						"INNER JOIN vrmpasajero pax ON (pax.pasajero_id=v.pasajero_id) "+
//						"INNER JOIN vrmruta r ON (r.ruta_id=v.ruta_id)" +
//						"INNER JOIN vrmtipcom tc ON (tc.tipcom_id=v.tipcom_id) "+
//						"INNER JOIN vrmpreali pa ON (pa.preali_id=v.preali_id) "+
//						"INNER JOIN vrmagencia ap ON (ap.agencia_id=v.agencia_idpartida) "+
						"INNER JOIN vrmtipdoc td ON (td.tipdoc_id=pax.tipdoc_id) "+
//						"INNER JOIN vrmforpag fp ON (fp.forpag_id=v.forpag_id) "+
//						"INNER JOIN vrmcanven cv ON (cv.canven_id=v.canven_id) "+
//						"LEFT OUTER JOIN vrmcliente c ON (c.c_numdoc=v.c_rucclicre) "+
//						"INNER JOIN vrmagencia av ON (av.agencia_id=v.agencia_id) "+
					"WHERE  v.itinerario_id="+itinearioId+" And v.c_tiptra=1 And v.c_estreg='A' AND v.tipmov_id not in ("+Constantes.ID_TIPMOV_ANULACION_SISTEMA+","+ Constantes.ID_TIPMOV_DEVOLUCION+","+ Constantes.ID_TIPMOV_ANULACION+" ) " +
			//			"AND v.Agencia_Idpartida=NVL("+idPruntoEmbarque+",v.Agencia_Idpartida) " +
						"AND v.c_estreg='"+Constantes.VALUE_ACTIVO+"' "+
					"ORDER BY v.n_numpiso, v.n_numasiento, v.d_fecpar, v.c_horpar";

		log.info(sql);
		List<?>result=getSession().createSQLQuery(sql).list();
		List<VentaPasaje>lstVenta=new ArrayList<>();
		for(int x=0;x<result.size();x++){
			Object[] obj=(Object[]) result.get(x);

			TipoDocumento tipoDocumento=new TipoDocumento();
			tipoDocumento.setId(((BigDecimal)obj[0]).intValue());
			tipoDocumento.setDenominacion(obj[1].toString());

			Pasajero pasajero=new Pasajero();
			pasajero.setTipoDocumento(tipoDocumento);
			pasajero.setNumeroDocumento(obj[2]!=null?obj[2].toString():null);
			pasajero.setNombre(obj[3].toString());
			pasajero.setApellidoPaterno(obj[4].toString());
			pasajero.setApellidoMaterno(obj[5]!=null?obj[5].toString():"NN");

			if(tipoDocumento.getId().intValue()==Constantes.ID_TIPDOC_DNI){
				tipoDocumento.setNombreCorto("L.E.");
			}else if(tipoDocumento.getId().intValue()==Constantes.ID_TIPDOC_CEDULA_IDENTIDAD){
				tipoDocumento.setNombreCorto("C.E.");
			}else if (tipoDocumento.getId().intValue()==Constantes.ID_TIPDOC_CARNET_EXTRANJERIA){
				tipoDocumento.setNombreCorto("C.E.");
			}else if (tipoDocumento.getId().intValue()==Constantes.ID_TIPDOC_PASAPORTE){
				tipoDocumento.setNombreCorto("P.S.");
			}else if (tipoDocumento.getId().intValue()==Constantes.ID_TIPDOC_SN){
				tipoDocumento.setNombreCorto("C.E.");//*********PENDIENTE POR DEFINIR, IMPLEMENTADO SOLO PARA PRUEBAS
				pasajero.setNumeroDocumento("00915740");
			}

			if(tipoDocumento.getNombreCorto()==null){
				throw new Exception("El tipo de documento "+tipoDocumento.getDenominacion()+" del Pasajero "+pasajero.toString()+" no esta registrado en el MTC.");
			}

//			Manifiesto manifiesto=new Manifiesto();
//			manifiesto.setId(((BigDecimal)obj[10]).longValue());

			VentaPasaje ventaPasaje=new VentaPasaje();
			ventaPasaje.setNumeroBoleto(obj[6].toString());
			ventaPasaje.setImportePagado(((BigDecimal)obj[7]).doubleValue());
			ventaPasaje.setNumeroAsiento(((BigDecimal)obj[8]).intValue());
			ventaPasaje.setPasajero(pasajero);
//			ventaPasaje.setManifiesto(manifiesto);

			lstVenta.add(ventaPasaje);
		}

		return lstVenta;
	}

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.model.dao.VentaPasajesDAO#buscarVentasPorPuntoVenta(java.lang.String, java.lang.String, java.lang.Integer, java.lang.Integer, java.lang.Integer, java.lang.Integer)
	 */
	@Override
	public List<Agencia> buscarVentasPorPuntoVenta(String fechaInicial, String fechaFinal, Integer idAgencia,Integer idUsuario, String idsTiposMovimientos, Integer idFormaPago)throws Exception {
		String queryTiposMov=null;
		if(idsTiposMovimientos==null){
			queryTiposMov="NOT IN ("+Constantes.ID_TIPMOV_ANULACION_SISTEMA+","+Constantes.ID_TIPMOV_DEVOLUCION+","+Constantes.ID_TIPMOV_ANULACION+","+Constantes.ID_TIPMOV_PREPAGADO+") ";
		}else{
			queryTiposMov="IN ("+idsTiposMovimientos+") ";
		}

		String sql="SELECT agencia,usuario,hora,vendidos, importe "
				 + "FROM( "
//					    -->Entre als 0 y 8 horas
					   +"SELECT ag.c_denominacion Agencia,vp.audusuins usuario,CAST('8' AS NUMBER) hora "
					        + ",COUNT(*) vendidos, SUM(vp.n_imppag)importe "
					   + "FROM VRTVENPAS vp "
					     + "INNER JOIN VRMAGENCIA ag ON (ag.agencia_id=vp.agencia_id) "
					   + "WHERE vp.d_fecliq BETWEEN to_date('"+fechaInicial+"', 'dd/MM/yyyy') AND to_date('"+fechaFinal+"', 'dd/MM/yyyy') AND ag.tipage_id=1 "
					   	 + "AND vp.tipmov_id "+queryTiposMov+" "
					   	 + "AND vp.tipcom_id IN ("+Constantes.ID_TIPCOM_BOLETO_VIAJE+","+Constantes.ID_TIPCOM_RECIBO_CAJA+","+Constantes.ID_TIPCOM_BOLETA_VENTA+","+Constantes.ID_TIPCOM_FACTURA+") "
					   	 + "AND vp.c_tiptra IN ("+Constantes.TIPO_OPERACION_VENTA+", "+Constantes.TIPO_OPERACION_VENTA_ESPECIAL+", "+Constantes.TIPO_OPERACION_EXCESO+", "+Constantes.TIPO_OPERACION_PERDIDA_SERVICIO+") "
					   	 + "AND ag.agencia_id=NVL("+idAgencia+",ag.agencia_id) "
					   	 + "AND vp.forpag_id=NVL("+idFormaPago+",vp.forpag_id) "
					   	 + "AND vp.usuario_id=NVL("+idUsuario+",vp.usuario_id) "
					   	 + "AND CAST((TO_CHAR(vp.audfecins,'HH24')) AS NUMBER) BETWEEN 0 AND 8 "
					   	 + "GROUP BY ag.c_denominacion,vp.audusuins "
					 + "UNION ALL "
//					    -->Entre las 9 y las 21 Horas
					   +"SELECT ag.c_denominacion Agencia,vp.audusuins usuario,CAST((TO_CHAR(vp.audfecins,'HH24'))  AS NUMBER)hora "
					        + ",COUNT(*) vendidos, SUM(vp.n_imppag)importe "
					   + "FROM VRTVENPAS vp "
					     + "INNER JOIN VRMAGENCIA ag ON (ag.agencia_id=vp.agencia_id) "
					   + "WHERE vp.d_fecliq BETWEEN to_date('"+fechaInicial+"', 'dd/MM/yyyy') AND to_date('"+fechaFinal+"', 'dd/MM/yyyy') AND ag.tipage_id=1 "
					     + "AND vp.tipmov_id "+queryTiposMov+" "
					     + "AND vp.tipcom_id IN ("+Constantes.ID_TIPCOM_BOLETO_VIAJE+","+Constantes.ID_TIPCOM_RECIBO_CAJA+","+Constantes.ID_TIPCOM_BOLETA_VENTA+","+Constantes.ID_TIPCOM_FACTURA+") "
					   	 + "AND vp.c_tiptra IN ("+Constantes.TIPO_OPERACION_VENTA+", "+Constantes.TIPO_OPERACION_VENTA_ESPECIAL+", "+Constantes.TIPO_OPERACION_EXCESO+", "+Constantes.TIPO_OPERACION_PERDIDA_SERVICIO+") "
					     + "AND ag.agencia_id=NVL("+idAgencia+",ag.agencia_id) "
					     + "AND vp.forpag_id=NVL("+idFormaPago+",vp.forpag_id) "
					     + "AND vp.usuario_id=NVL("+idUsuario+",vp.usuario_id) "
					     + "AND CAST((TO_CHAR(vp.audfecins,'HH24')) AS NUMBER) BETWEEN 9 AND 21 "
					   + "GROUP BY ag.c_denominacion,vp.audusuins, CAST((TO_CHAR(vp.audfecins,'HH24'))  AS NUMBER) "
					 + "UNION ALL "
//					    -->Entre las 22 y 23 horas
					   +"SELECT ag.c_denominacion Agencia,vp.audusuins usuario,CAST('22' AS NUMBER) hora "
					     + ",COUNT(*) vendidos, SUM(vp.n_imppag)importe "
					   + "FROM VRTVENPAS vp "
					     + "INNER JOIN VRMAGENCIA ag ON (ag.agencia_id=vp.agencia_id) "
					   + "WHERE vp.d_fecliq BETWEEN to_date('"+fechaInicial+"', 'dd/MM/yyyy') AND to_date('"+fechaFinal+"', 'dd/MM/yyyy') AND ag.tipage_id=1 "
					   	 + "AND vp.tipmov_id "+queryTiposMov+" "
					     + "AND vp.tipcom_id IN ("+Constantes.ID_TIPCOM_BOLETO_VIAJE+","+Constantes.ID_TIPCOM_RECIBO_CAJA+","+Constantes.ID_TIPCOM_BOLETA_VENTA+","+Constantes.ID_TIPCOM_FACTURA+") "
					   	 + "AND vp.c_tiptra IN ("+Constantes.TIPO_OPERACION_VENTA+", "+Constantes.TIPO_OPERACION_VENTA_ESPECIAL+", "+Constantes.TIPO_OPERACION_EXCESO+", "+Constantes.TIPO_OPERACION_PERDIDA_SERVICIO+") "
					     + "AND ag.agencia_id=NVL("+idAgencia+",ag.agencia_id) "
					     + "AND vp.forpag_id=NVL("+idFormaPago+",vp.forpag_id) "
					     + "AND vp.usuario_id=NVL("+idUsuario+",vp.usuario_id) "
					     + "AND CAST((TO_CHAR(vp.audfecins,'HH24')) AS NUMBER) BETWEEN 22 AND 23 "
					   + "GROUP BY ag.c_denominacion,vp.audusuins "
					  +") "
				 + "ORDER BY agencia,usuario,hora";

		log.info(sql);
		List<?>result=getSession().createSQLQuery(sql).list();
//		RptVentasPuntoVenta ventasPuntoVenta=new RptVentasPuntoVenta();
		List<Agencia>agencias=new ArrayList<>();
		List<String> containsAgencias=new ArrayList<>();
		List<String>containsUsuarios=new ArrayList<>();
		for(int i=0;i<result.size();i++){
			Object[] obj=(Object[]) result.get(i);

			if(!(containsAgencias.contains(obj[0].toString()))){
				/*Agrega la agencia*/
				containsAgencias.add(obj[0].toString());

				/*Agrega los usuarios*/
				List<Usuario>usuarios=new ArrayList<>();
				Usuario usuario=null;
				for(int x=0;x<result.size();x++){
					Object[] obj1=(Object[]) result.get(x);
					if(containsAgencias.contains(obj1[0].toString()) && !(containsUsuarios.contains(obj1[1].toString()))){
						containsUsuarios.add(obj1[1].toString());

						/*Horas,cantidades, montos de venta*/
						List<RptVentaUsuario>ventasUsuariosTmp=new ArrayList<>();
						for(int y=0;y<result.size();y++){
							Object[] obj2=(Object[]) result.get(y);
							if(containsAgencias.get(containsAgencias.size()-1).contains(obj2[0].toString()) && containsUsuarios.get(containsUsuarios.size()-1).contains(obj2[1].toString())){
								RptVentaUsuario ventaUsuario=new RptVentaUsuario();
								ventaUsuario.setHoraVenta(((BigDecimal)obj2[2]).intValue());
								ventaUsuario.setCantidad(((BigDecimal)obj2[3]).intValue());
								ventaUsuario.setImporte(((BigDecimal)obj2[4]).doubleValue());
								ventasUsuariosTmp.add(ventaUsuario);
							}
						}

						/*Agrega las horas y cantidades al ArrayList VentasUsuarios*/
						List<RptVentaUsuario>ventasUsuarios=new ArrayList<>();
						for(int xx=8; xx<=22;xx++){
							RptVentaUsuario oVentaUsuario=null;
							for(RptVentaUsuario ventaUsuario: ventasUsuariosTmp){
								if(xx==ventaUsuario.getHoraVenta().intValue()){
									oVentaUsuario=ventaUsuario;
									break;
								}
							}
							if(oVentaUsuario==null){
								oVentaUsuario=new RptVentaUsuario();
								oVentaUsuario.setHoraVenta(xx);
								oVentaUsuario.setCantidad(0);
								oVentaUsuario.setImporte(.00);
							}
							ventasUsuarios.add(oVentaUsuario);
						}

						/*Agrega los usuarios y sus ventas al objeto usuario*/
						usuario=new Usuario();
						usuario.setLogin(obj1[1].toString());
						usuario.setVentasUsuarios(ventasUsuarios);
						usuarios.add(usuario);


						/*Agrega la agenia al array agencias*/
						Agencia agencia=new Agencia();
						agencia.setDenominacion(obj[0].toString());
						agencia.setUsuario(usuario);
						agencias.add(agencia);
					}
				}

//				/*Agrega la agenia al array agencias*/
//				Agencia agencia=new Agencia();
//				agencia.setDenominacion(obj[0].toString());
//				agencia.setUsuario(usuario);
//				agencias.add(agencia);
			}

		}
		return agencias;
	}

	/* (non-Javadoc)
	 * @see pe.itsb.vyrbus.model.dao.VentaPasajesDAO#buscarAvanceVentas(java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	public List<VentaPasaje> buscarAvanceVentas(String idOrigen, String idDestino, String idServicio, String fechaDesde, String fechaHasta) throws Exception {
		if(idDestino.isEmpty())
			idDestino=null;
		if (idOrigen.isEmpty())
			idOrigen=null;
		if(idServicio.isEmpty())
			idServicio=null;

		String sql = " SELECT P1.d_fecpar, P1.c_origen, P1.c_destino, P1.c_horpar, P1.c_denominacion as Servicio, " +
					       "P1.Capbus, decode(P2.Flag, '1', COUNT(*), 0) Vendidos, SUM(P2.n_imppag) IREAL, " +
					       "SUM(P2.n_descuento) DESCUENTO, P1.n_kilometros, P1.itinerario_id, P1.ruta_idmayor " +
					" FROM " +
					       "(SELECT i.itinerario_id, i.ruta_idmayor, s.c_denominacion,NVL(s.n_numasipis1,0)+NVL(s.n_numasipis2,0) Capbus, " +
					       "i.d_fecpar, i.c_horpar, r.c_origen, r.c_destino, i.servicio_id, r.n_kilometros " +
					       "FROM vrtitinerario i inner join vrmruta r on (i.ruta_idmayor = r.ruta_id) " +
					       "INNER JOIN vrmservicio s ON (s.servicio_id = i.servicio_id ) " +
					       "WHERE  i.d_fecpar between to_date('"+fechaDesde+"','"+Constantes.DATE_FORMAT+"') AND " +
					       "to_date('"+fechaHasta+"','"+Constantes.DATE_FORMAT+"')  AND i.n_esanulado=0 ";
					        //Cuendo es entre provincias
					        if(idOrigen!=null && idDestino!=null &&  idOrigen.equals(idDestino)){
					        	sql+=" AND r.localidad_idorigen  != "+idOrigen+" "+
									 " AND r.localidad_iddestino != "+idDestino+" ";
					        }else{
					        	sql+=" AND r.localidad_idorigen=NVL("+idOrigen+",localidad_idorigen) "+
								" AND r.localidad_iddestino=NVL("+idDestino+",localidad_iddestino) ";
					        }

					        if(idServicio != null)
					        	sql+=" AND s.servicio_id="+idServicio+" ";

					        sql+= ") P1 "+
					        "LEFT JOIN "+
					             "(SELECT '1' Flag, vt.itinerario_id, vt.d_fecpar, vt.c_horpar, vt.d_feclle, vt.c_horlle, vt.servicio_id, vt.ruta_id, vt.n_imppag, vt.n_descuento "+
					             "FROM vrtvenpas vt "+
					             "INNER JOIN "+
					                   "(SELECT max(v.venpas_id) venpas_id FROM vrtvenpas v " +
					                   		"INNER JOIN VRTITINERARIO i ON (i.itinerario_id=v.itinerario_id) " +
											"WHERE i.d_fecpar BETWEEN to_date('"+fechaDesde+"','"+Constantes.DATE_FORMAT+"') AND to_date('"+fechaHasta+"','"+Constantes.DATE_FORMAT+"') GROUP BY c_numcontrol) B "+
					                 "ON (vt.venpas_id = B.venpas_id) " +
					             " WHERE ";

					         sql = sql	+" tipmov_id NOT IN ("+Constantes.ID_TIPMOV_ANULACION_SISTEMA+

					        		 ","+Constantes.ID_TIPMOV_DEVOLUCION+","+Constantes.ID_TIPMOV_ANULACION+") ) P2 "+
					         "ON  (P2.itinerario_id = P1.itinerario_id ) "+
					"GROUP BY P1.ruta_idmayor,P1.servicio_id,P1.c_origen,P1.c_destino,P1.c_denominacion,P1.d_fecpar, P1.c_horpar, Flag,"
					+ "P1.Capbus, P1.n_kilometros, P1.itinerario_id, P1.servicio_id "+
					"ORDER BY P1.c_horpar,P1.c_origen,P1.c_destino,P1.c_denominacion";

			log.info(sql);

			List<?> result = getSession().createSQLQuery(sql).list();

			List<VentaPasaje> lstResult = new ArrayList<>();
			for(int i=0; i<result.size(); i++){
				Object[] obj = (Object[]) result.get(i);
				VentaPasaje ventaPasaje = new VentaPasaje();
				Ruta ruta = new Ruta();
				Servicio servicio = new Servicio();
				Itinerario itinerario= new Itinerario();

				ventaPasaje.setFechaPartida(((Date)obj[0]));

				ruta.setOrigen(obj[1].toString());
				ruta.setDestino(obj[2].toString());
				ruta.setKilometros(((BigDecimal)obj[9]).doubleValue());
				ruta.setId(((BigDecimal)obj[11]).intValue());

				ventaPasaje.setHoraPartida(obj[3].toString());

				servicio.setDenominacion(obj[4].toString());
				servicio.setTotalAsientos(((BigDecimal)obj[5]).intValue());
//				servicio.setGruposervicio(((BigDecimal)obj[11]).intValue());

				itinerario.setId(((BigDecimal)obj[10]).longValue());

				ventaPasaje.setRuta(ruta);
				ventaPasaje.setServicio(servicio);
				ventaPasaje.setCantidadPax(((BigDecimal)obj[6]).intValue());
				ventaPasaje.setImporteReal(obj[7]==null?0:((BigDecimal)obj[7]).doubleValue());
				ventaPasaje.setImporteDescuentos(obj[8]==null?0:((BigDecimal)obj[8]).doubleValue());
				ventaPasaje.setItinerario(itinerario);

				sql = "SELECT SUM(P1.TOTAL) FROM (SELECT t.n_pisbus PISO, t.n_zonbus ZONA, tr.tarreg_id TARREG, tr.tarifa_id IDTARIFA, tr.itinerario_id ITINERARIO, "
						+ "tr.d_fectar FECHA, tr.c_horpar HORA, tr.n_monto TARIFA, DECODE(t.n_pisbus,0,s.n_numasipis1,s.n_numasipis2) ASIENTOS, "
						+ "(DECODE(t.n_pisbus,0,s.n_numasipis1,s.n_numasipis2) * tr.n_monto) TOTAL "
						+ "FROM vrttarreg tr "
						+ "INNER JOIN vrttarifa t ON t.tarifa_id=tr.tarifa_id "
						+ "INNER JOIN vrmservicio s ON s.servicio_id=t.servicio_id "
						+ "WHERE t.ruta_id="+ruta.getId()+" AND s.c_denominacion='"+servicio.getDenominacion()+"' AND t.canven_id=1 AND "
						+ "tr.d_fectar=to_date('"+ventaPasaje.getFechaPartida().toString().substring(0, 10)+"','yyyy-MM-dd') AND tr.c_horpar='"
						+ ventaPasaje.getHoraPartida()+"' AND tr.c_estreg='A') P1";

				Object objTotal = getSession().createSQLQuery(sql).uniqueResult();
				if(objTotal!=null)
					ventaPasaje.setImporteEsperado(((BigDecimal)objTotal).doubleValue());
				else
					ventaPasaje.setImporteEsperado(null);

				lstResult.add(ventaPasaje);
			}
		return lstResult;
	}

	/* (non-Javadoc)
	 * @see pe.itsb.vyrbus.model.dao.VentaPasajesDAO#buscarBoletosAnuladosPorUsuario(java.lang.String, java.lang.String)
	 */
	@Override
	public List<ResumenAnulacionPostergacion> buscarBoletosAnuladosByX(String fechaDesde, String fechaHasta, Integer criterio) {
		String sql = "";
		if(criterio == 1)	//By Agencia
			sql = "SELECT vp.agencia_id, a.c_denominacion, COUNT(vp.agencia_id) cantidad "
					+ "FROM vrtvenpas vp "
					+ "INNER JOIN vrmagencia a ON a.agencia_id=vp.agencia_id "
					+ "WHERE vp.tipmov_id=13 AND vp.d_fecliq BETWEEN to_date('"+fechaDesde+"', 'dd/mm/yyyy') AND "
							+ "to_date('"+fechaHasta+"', 'dd/mm/yyyy') AND vp.c_tiptra=1 AND vp.tipcom_id IN (2,7) "
					+ "GROUP BY vp.agencia_id, a.c_denominacion "
					+ "ORDER BY a.c_denominacion ASC";
		else
			sql = "SELECT vp.usuario_id, u.c_apepat||' '||u.c_apemat||' '||u.c_nombre usuario, COUNT(vp.usuario_id) cantidad "
					+ "FROM vrtvenpas vp "
					+ "INNER JOIN vrmusuario u ON u.usuario_id=vp.usuario_id "
					+ "WHERE vp.tipmov_id=13 AND vp.d_fecliq BETWEEN to_date('"+fechaDesde+"', 'dd/mm/yyyy') AND "
							+ "to_date('"+fechaHasta+"', 'dd/mm/yyyy') AND vp.c_tiptra=1 AND vp.tipcom_id IN (2,7) "
					+ "GROUP BY vp.usuario_id, (u.c_apepat||' '||u.c_apemat||' '||u.c_nombre)"
					+ "ORDER BY (u.c_apepat||' '||u.c_apemat||' '||u.c_nombre) ASC";

		log.info(sql);

		List<?> result = getSession().createSQLQuery(sql).list();
		List<ResumenAnulacionPostergacion> lstResult = new ArrayList<>();
		for(int i=0; i<result.size(); i++){
			Object[] obj = (Object[])result.get(i);
			ResumenAnulacionPostergacion resumenAnulacionPostergacion = new ResumenAnulacionPostergacion();
			resumenAnulacionPostergacion.setId(((BigDecimal)obj[0]).intValue());
			resumenAnulacionPostergacion.setDenominacion(obj[1].toString());
			resumenAnulacionPostergacion.setTotal(((BigDecimal)obj[2]).intValue());
			lstResult.add(resumenAnulacionPostergacion);
		}
		return lstResult;
	}

	/* (non-Javadoc)
	 * @see pe.itsb.vyrbus.model.dao.VentaPasajesDAO#buscarBoletosAnuladosDetalladoPorUsuario(java.lang.String, java.lang.String, java.lang.Integer)
	 */
	@Override
	public List<VentaPasaje> buscarBoletosAnuladosDetalladoByX(String fechaDesde, String fechaHasta, Integer id, Integer criterio) {
		String sql = "SELECT vp.venpas_id, vp.venpas_idoriginal, vp.c_numboleto, vp.c_numbolant, vp.c_numcontrol, vp.n_secuencial, "
				+ "p.c_apepat||' '||p.c_apemat||' '||p.c_nombre pasajero, tc.c_denominacion TIPCOM, tm.c_abreviatura TIPMOV, cv.c_nomcor CANAL, "
				+ "nvl(vp.n_numpiso, 0) PISO, nvl(vp.n_numasiento, 0) ASTO, vp.d_fecpar, vp.c_horpar, vp.n_tarifa, vp.n_imppagdif DIFAGRE, "
				+ "vp.d_fecliq FECOPE, a.c_nomcor AGENCIA,uv.c_apepat APEPATVEN, uv.c_apemat APEMATVEN, uv.c_nombre NOMVEN, vp.c_observaciones, vp.d_fecanu,"
				+ "ua.c_apepat APEPATANU, ua.c_apemat APEMATANU, ua.c_nombre  NOMBREANU "
				+ "FROM vrtvenpas vp "
				+ "INNER JOIN vrmruta r on (vp.ruta_id = r.ruta_id) "
				+ "INNER JOIN vrmpasajero p on (vp.pasajero_id = p.pasajero_id) "
				+ "INNER JOIN vrmtipcom tc on (vp.tipcom_id = tc.tipcom_id) "
				+ "INNER JOIN vrmtipmov tm on (vp.tipmov_id = tm.tipmov_id) "
				+ "INNER JOIN vrmagencia a on (vp.agencia_id = a.agencia_id) "
				+ "INNER JOIN vrmusuario uv on (vp.usuario_id = uv.usuario_id) "
				+ "INNER JOIN vrmusuario ua on (vp.usuario_idanu = ua.usuario_id)  "
				+ "INNER JOIN vrmcanven cv on (vp.canven_id = cv.canven_id) "
				+ "WHERE vp.d_fecliq between to_date('"+fechaDesde+"', 'dd/mm/yyyy') AND to_date('"+fechaHasta+"', 'dd/mm/yyyy') AND "
				+ "vp.c_tiptra=1 AND vp.tipmov_id in (13) AND vp.tipcom_id in (2,7) ";
		if(criterio == 1)	//By Agencia
			sql = sql + "AND vp.agencia_id="+id+" ";
		else
			sql = sql + "AND vp.usuario_id="+id+" ";

		sql = sql + "ORDER BY a.c_nomcor";

		log.info(sql);

		List<?> result = getSession().createSQLQuery(sql).list();
		List<VentaPasaje> lstResult = new ArrayList<>();
		for(int i=0; i<result.size(); i++) {
			Object[] obj = (Object[]) result.get(i);
			VentaPasaje ventaPasaje = new VentaPasaje();
			ventaPasaje.setId(((BigDecimal)obj[0]).longValue());
			ventaPasaje.setVentaOriginal(((BigDecimal)obj[1]).longValue());
			ventaPasaje.setNumeroBoleto(obj[2].toString());
			ventaPasaje.setNumeroBoletoAnterior(obj[3]==null?"":obj[3].toString());
			ventaPasaje.setNumeroControl(obj[4]==null?"":obj[4].toString());
			ventaPasaje.setSecuencial(((BigDecimal)obj[5]).intValue());
			Pasajero pasajero = new Pasajero();
			pasajero.setNombresApellidos(obj[6].toString());
			ventaPasaje.setPasajero(pasajero);
			TipoComprobante tipoComprobante = new TipoComprobante();
			tipoComprobante.setAbreviatura(obj[7].toString());
			ventaPasaje.setTipoComprobante(tipoComprobante);
			TipoMovimiento tipoMovimiento = new TipoMovimiento();
			tipoMovimiento.setAbreviatura(obj[8].toString());
			ventaPasaje.setTipoMovimiento(tipoMovimiento);
			CanalVenta canalVenta = new CanalVenta();
			canalVenta.setNombreCorto(obj[9].toString());
			ventaPasaje.setCanalVenta(canalVenta);
			ventaPasaje.setNumeroPiso(((BigDecimal)obj[10]).intValue());
			ventaPasaje.setNumeroAsiento(((BigDecimal)obj[11]).intValue());
			ventaPasaje.setFechaPartida((Date)obj[12]);
			ventaPasaje.setHoraPartida(obj[13]==null?"":obj[13].toString());
			ventaPasaje.setTarifa(((BigDecimal)obj[14]).doubleValue());
			ventaPasaje.setImportePagadoByDiferencia(obj[15]==null?0:((BigDecimal)obj[15]).doubleValue());
			ventaPasaje.setFechaLiquidacion((Date)obj[16]);
			Agencia agencia = new Agencia();
			agencia.setNombreCorto(obj[17].toString());
			ventaPasaje.setAgencia(agencia);
			//USuario Venta
			Usuario usuario = new Usuario();
			usuario.setApellidoPaterno(obj[18]==null?"":obj[18].toString());
			usuario.setApellidoMaterno(obj[19]==null?"":obj[19].toString());
			usuario.setNombre(obj[20]==null?"":obj[20].toString());
			ventaPasaje.setUsuario(usuario);
			ventaPasaje.setObservaciones(obj[21]==null?"":obj[21].toString());
			ventaPasaje.setFechaAnulacion((Date)obj[22]);
			//Usuario Anulacion. MAOE 31/03/2023
			Usuario usuarioAnulacion = new Usuario();
			usuarioAnulacion.setApellidoPaterno(obj[23]==null?"":obj[23].toString());
			usuarioAnulacion.setApellidoMaterno(obj[24]==null?"":obj[24].toString());
			usuarioAnulacion.setNombre(obj[25]==null?"":obj[25].toString());
			ventaPasaje.setUsuarioAnulacion(usuarioAnulacion);
			lstResult.add(ventaPasaje);
		}
		return lstResult;
	}

	/* (non-Javadoc)
	 * @see pe.itsb.vyrbus.model.dao.VentaPasajesDAO#buscarBoletosPostergadosByX(java.lang.String, java.lang.String, java.lang.Integer)
	 */
	@Override
	public List<ResumenAnulacionPostergacion> buscarBoletosPostergadosByX(String fechaDesde, String fechaHasta, Integer criterio, Integer nroPostergaciones) {
		String sql = "";
		String strNroPost="";
		if(nroPostergaciones > 0){
			strNroPost = " AND  vp.n_secuencial = " + Integer.toString(nroPostergaciones) + " ";
		}

		if(criterio == 1)	//By Agencia
			sql ="SELECT  vp.agencia_id, a.c_denominacion, COUNT(vp.agencia_id) cant "
				+ "FROM vrtvenpas vp "
				+ "INNER JOIN vrmagencia a ON a.agencia_id=vp.agencia_id "
				+ "WHERE vp.tipmov_id IN (2, 9) AND vp.d_fecliq BETWEEN to_date('"+fechaDesde+"', 'dd/mm/yyyy') "
				+ "AND to_date('"+fechaHasta+"', 'dd/mm/yyyy') AND vp.c_tiptra=1 AND vp.tipcom_id in (2,7) "
				+ strNroPost
				+ "GROUP BY(vp.agencia_id, a.c_denominacion) "
				+ "ORDER BY a.c_denominacion DESC";
		else
			sql = "SELECT  vp.usuario_id, (u.c_apepat||' '||u.c_apemat||' '||u.c_nombre) usuario, COUNT(vp.usuario_id) cant "
					+ "FROM vrtvenpas vp "
					+ "INNER JOIN vrmusuario u ON u.usuario_id=vp.usuario_id "
					+ "WHERE vp.tipmov_id in (2, 9) AND vp.d_fecliq BETWEEN to_date('"+fechaDesde+"', 'dd/mm/yyyy') "
					+ "AND to_date('"+fechaHasta+"', 'dd/mm/yyyy') AND vp.c_tiptra=1 AND vp.tipcom_id in (2,7) "
					+ strNroPost
					+ "GROUP BY vp.usuario_id, (u.c_apepat||' '||u.c_apemat||' '||u.c_nombre) "
					+ "ORDER BY (u.c_apepat||' '||u.c_apemat||' '||u.c_nombre) DESC";

		log.info(sql);

		List<?> result = getSession().createSQLQuery(sql).list();
		List<ResumenAnulacionPostergacion> lstResult = new ArrayList<>();

		for(int i=0; i<result.size(); i++) {
			Object[] obj = (Object[])result.get(i);
			ResumenAnulacionPostergacion resumenAnulacionPostergacion = new ResumenAnulacionPostergacion();
			resumenAnulacionPostergacion.setId(((BigDecimal)obj[0]).intValue());
			resumenAnulacionPostergacion.setDenominacion(obj[1].toString());
			resumenAnulacionPostergacion.setTotal(((BigDecimal)obj[2]).intValue());
			lstResult.add(resumenAnulacionPostergacion);
		}
		return lstResult;
	}

	/* (non-Javadoc)
	 * @see pe.itsb.vyrbus.model.dao.VentaPasajesDAO#buscarBoletosAnuladosDetalladoPorUsuario(java.lang.String, java.lang.String, java.lang.Integer)
	 */
	@Override
	public List<VentaPasaje> buscarBoletosPostergadosDetalladoByX(String fechaDesde, String fechaHasta, Integer id, Integer criterio, Integer nroPostergaciones) {
		String sql = "";
		String strNroPost="";
		if(nroPostergaciones > 0){
			strNroPost = " AND  vp.n_secuencial = " + Integer.toString(nroPostergaciones) + " ";
		}

		sql = "SELECT vp.venpas_id, vp.venpas_idoriginal, vp.c_numboleto, vp.c_numbolant, vp.c_numcontrol, vp.n_secuencial, "
			+ "p.c_apepat||' '||p.c_apemat||' '||p.c_nombre pasajero, tc.c_abreviatura TIPCOM, tm.c_denominacion TIPMOV, cv.c_nomcor CANAL, "
			+ "nvl(vp.n_numpiso, 0) PISO, nvl(vp.n_numasiento, 0) ASTO, vp.d_fecpar, vp.c_horpar, vp.n_tarifa, vp.n_imppagdif DIFAGRE, "
			+ "vp.d_fecliq FECOPE, a.c_nomcor AGENCIA, u.c_apepat, u.c_apemat, u.c_nombre, vp.c_observaciones "
			+ "FROM vrtvenpas vp "
			+ "INNER JOIN vrmruta r on (vp.ruta_id = r.ruta_id) "
			+ "INNER JOIN vrmpasajero p on (vp.pasajero_id = p.pasajero_id) "
			+ "INNER JOIN vrmtipcom tc on (vp.tipcom_id = tc.tipcom_id) "
			+ "INNER JOIN vrmtipmov tm on (vp.tipmov_id = tm.tipmov_id) "
			+ "INNER JOIN vrmagencia a on (vp.agencia_id = a.agencia_id) "
			+ "INNER JOIN vrmusuario u on (vp.usuario_id = u.usuario_id) "
			+ "INNER JOIN vrmcanven cv on (vp.canven_id = cv.canven_id) "
			+ "WHERE vp.d_fecliq between to_date('"+fechaDesde+"', 'dd/mm/yyyy') AND to_date('"+fechaHasta+"', 'dd/mm/yyyy') AND "
			+ "vp.c_tiptra=1 AND vp.tipmov_id in (2, 9) "; //AND vp.tipcom_id in (2,7) ";
		if(criterio == 1)	//By Agencia
			sql = sql + "AND vp.agencia_id="+id+" ";
		else
			sql = sql + "AND vp.usuario_id="+id+" ";

		sql = sql + strNroPost + " ORDER BY a.c_nomcor ";

		log.info(sql);

		List<?> result = getSession().createSQLQuery(sql).list();
		List<VentaPasaje> lstResult = new ArrayList<>();

		for(int i=0; i<result.size(); i++) {
			Object[] obj = (Object[]) result.get(i);
			VentaPasaje ventaPasaje = new VentaPasaje();
			ventaPasaje.setId(((BigDecimal)obj[0]).longValue());
			ventaPasaje.setVentaOriginal(((BigDecimal)obj[1]).longValue());
			ventaPasaje.setNumeroBoleto(obj[2].toString());
			ventaPasaje.setNumeroBoletoAnterior(obj[3]==null?"":obj[3].toString());
			ventaPasaje.setNumeroControl(obj[4]==null?"":obj[4].toString());
			ventaPasaje.setSecuencial(((BigDecimal)obj[5]).intValue());
			Pasajero pasajero = new Pasajero();
			pasajero.setNombresApellidos(obj[6].toString());
			ventaPasaje.setPasajero(pasajero);
			TipoComprobante tipoComprobante = new TipoComprobante();
			tipoComprobante.setAbreviatura(obj[7].toString());
			ventaPasaje.setTipoComprobante(tipoComprobante);
			TipoMovimiento tipoMovimiento = new TipoMovimiento();
			tipoMovimiento.setAbreviatura(obj[8].toString());
			ventaPasaje.setTipoMovimiento(tipoMovimiento);
			CanalVenta canalVenta = new CanalVenta();
			canalVenta.setNombreCorto(obj[9].toString());
			ventaPasaje.setCanalVenta(canalVenta);
			ventaPasaje.setNumeroPiso(((BigDecimal)obj[10]).intValue());
			ventaPasaje.setNumeroAsiento(((BigDecimal)obj[11]).intValue());
			ventaPasaje.setFechaPartida((Date)obj[12]);
			ventaPasaje.setHoraPartida(obj[13]==null?"":obj[13].toString());
			ventaPasaje.setTarifa(((BigDecimal)obj[14]).doubleValue());
			ventaPasaje.setImportePagadoByDiferencia(obj[15]==null?0:((BigDecimal)obj[15]).doubleValue());
			ventaPasaje.setFechaLiquidacion((Date)obj[16]);
			Agencia agencia = new Agencia();
			agencia.setNombreCorto(obj[17].toString());
			ventaPasaje.setAgencia(agencia);
			Usuario usuario = new Usuario();
			usuario.setApellidoPaterno(obj[18]==null?"":obj[18].toString());
			usuario.setApellidoMaterno(obj[19]==null?"":obj[19].toString());
			usuario.setNombre(obj[20]==null?"":obj[20].toString());
			ventaPasaje.setUsuario(usuario);
			ventaPasaje.setObservaciones(obj[21]==null?"":obj[21].toString());
			lstResult.add(ventaPasaje);
		}
		return lstResult;
	}


	@Override
	public List<ResumenVentas> buscarResumenVentas(String fechaDesde, String fechaHasta, Integer idAgencia, Integer nroConsulta) {
		String sql = "";
		String strQuerySelect="";
		String strQueryAnd="";
		String strGroupBy="";
		String strQueryOrder="";

		if(nroConsulta == 1){
			strQuerySelect = "		v.n_cantidad, v.n_total, to_char(v.d_fecven, 'dd/mm/yyyy') FECVEN ";
			strQueryAnd = "";
			strQueryOrder = "	       v.d_fecven, v.c_agencia, v.c_comprobante";
		}
		else if(nroConsulta == 2){
			strQuerySelect = "		v.n_cantidad, v.n_total, to_char(v.d_fecven, 'dd/mm/yyyy') FECVEN ";
			strQueryAnd = "		AND v.agencia_id = NVL(" + (idAgencia==0 ? null : idAgencia) +  ", v.agencia_id) ";
			strQueryOrder = "	       v.d_fecven, v.c_agencia,  v.n_rubro, v.c_comprobante ";
		}
		else{
			strQuerySelect = "		sum(v.n_cantidad) cant, sum(v.n_total) total, v.c_mes  ";
			strQueryAnd = "		AND v.agencia_id = NVL(" + (idAgencia==0 ? null : idAgencia) + ", v.agencia_id) ";
			strGroupBy = "GROUP BY "
					+ "	v.c_mes, v.n_rubro, v.canven_id, v.c_canal, v.agencia_id, v.c_agencia, v.tipcom_id, v.c_comprobante";
			strQueryOrder = "	       v.c_mes, v.c_agencia, v.n_rubro, v.c_comprobante ";
		}

			sql = " SELECT "
				+ "     v.n_rubro, v.canven_id, v.c_canal, v.agencia_id, v.c_agencia, v.tipcom_id, v.c_comprobante, "
				+ strQuerySelect
				+ "	FROM "
				+ "	       vrmagencia a right join"
				+ "	       vrhresven v  on (a.agencia_id = v.agencia_id)"
				+ "	WHERE "
				+ "	       v.d_fecven BETWEEN to_date('" + fechaDesde + "', 'dd/MM/yyyy') "
				+ "	       AND to_date('" + fechaHasta + "', 'dd/MM/yyyy')"
				+ strQueryAnd
				+ strGroupBy
				//	       --Comentar agencia para la primera consulta
				//	       --AND v.agencia_id=46
				+ "	ORDER BY "
				//	--descomentar este orden para la primera consulta
				+ strQueryOrder;
				//	--comentar este orden para la segunda consulta
				//	--       v.fecven, v.comprobante

		log.info(sql);

		List<?> result = getSession().createSQLQuery(sql).list();
		List<ResumenVentas> lstResult = new ArrayList<>();

		for(int i=0; i<result.size(); i++){
			Object[] obj = (Object[])result.get(i);
			ResumenVentas resumenVentas = new ResumenVentas();

			resumenVentas.setRubro(((BigDecimal)obj[0]).intValue());
			CanalVenta canalVenta = new CanalVenta();
			canalVenta.setId(((BigDecimal)obj[1]).intValue());
			canalVenta.setDenominacion(obj[2].toString());
			resumenVentas.setCanalVenta(canalVenta);
			Agencia agencia = new Agencia();
			agencia.setId(((BigDecimal)obj[3]).intValue());
			agencia.setDenominacion(obj[4].toString());
			resumenVentas.setAgencia(agencia);
			TipoComprobante tipoComprobante = new TipoComprobante();
			tipoComprobante.setId(((BigDecimal)obj[5]).intValue());
			tipoComprobante.setDenominacion(obj[6].toString());
			resumenVentas.setTipoComprobante(tipoComprobante);
			resumenVentas.setCantidad(((BigDecimal)obj[7]).intValue());
			resumenVentas.setTotal(((BigDecimal)obj[8]).doubleValue());

			if(nroConsulta==3)
				resumenVentas.setMes(obj[9].toString());
			else
				resumenVentas.setFechaVenta(obj[9].toString());
			lstResult.add(resumenVentas);
		}
		return lstResult;
	}

	@Override
	public List<VentaPasaje> buscarHistorialComprobante(String numeroComprobante){
		String sql="";

		sql = "SELECT "
			+ "	       vp.venpas_id, vp.venpas_idoriginal, "
			+ "	       vp.ruta_id, r.c_origen, r.c_destino, "
			+ "	       vp.cliente_id, c.c_numdoc, c.c_razsoc,  "
			+ "	       vp.pasajero_id, p.c_numdoc, p.c_apepat, p.c_apemat, p.c_nombre, "  //11
			+ "	       vp.forpag_id, fp.c_denominacion FORPAG, "
			+ "	       vp.tipforpag_id, tp.c_denominacion TIPFORPAG, "
			+ "	       vp.servicio_id, s.c_denominacion SERVICIO, "
			+ "	       vp.tipcom_id, tc.c_abreviatura TIPCOM, "
			+ "        vp.tipmov_id, tm.c_denominacion TIPMOV,"					//21
			+ "	       vp.agencia_idpartida, ap.c_nomcor AGEPAR, "
			+ "	       vp.agencia_idllegada, al.c_nomcor AGELLE, "
			+ "	       vp.c_numboleto, vp.c_numbolant, vp.c_numcontrol, "
			+ "	       vp.n_numpiso, vp.n_numasiento, vp.n_secuencial, "		//31
			+ "	       vp.d_fecpar, vp.c_horpar,  "
			+ "	       vp.n_tarifa, vp.n_imppag, vp.n_imppagdif, "
			+ "	       vp.c_tiptra, vp.d_fecliq, "
			+ "	       vp.agencia_id, a.c_denominacion AGEVEN, "				//40
			+ "	       vp.usuario_id, u.c_apepat, u.c_apemat, u.c_nombre, "
			+ "	       vp.canven_id, cv.c_nomcor CANVEN, "
			+ "	       vp.d_esfe, vp.c_observaciones, "
			+ "	       dm.manifiesto_id, m.c_numman, m.c_codbus "				//52
			+ "	FROM  "
			+ "	       vrtvenpas vp  "
			+ "	       INNER JOIN vrmpasajero p ON (vp.pasajero_id = p.pasajero_id) "
			+ "	       INNER JOIN vrmruta r ON (vp.ruta_id = r.ruta_id) "
			+ "	       INNER JOIN vrmforpag fp ON (vp.forpag_id = fp.forpag_id) "
			+ "	       INNER JOIN vrmtipforpag tp ON (vp.tipforpag_id = tp.tipforpag_id) "
			+ "        INNER JOIN vrmtipmov tm ON (vp.tipmov_id = tm.tipmov_id) "
			+ "	       INNER JOIN vrmservicio s ON (vp.servicio_id = s.servicio_id) "
			+ "	       INNER JOIN vrmtipcom tc ON (vp.tipcom_id = tc.tipcom_id) "
			+ "	       INNER JOIN vrmagencia a ON (vp.agencia_id = a.agencia_id) "
			+ "	       INNER JOIN vrmusuario u ON (vp.usuario_id = u.usuario_id) "
			+ "	       INNER JOIN vrmcanven cv ON (vp.canven_id = cv.canven_id) "
			+ "	       LEFT JOIN vrmcliente c ON (vp.cliente_id = c.cliente_id) "
			+ "	       LEFT JOIN vrmagencia ap ON (vp.agencia_idpartida = ap.agencia_id) "
			+ "	       LEFT JOIN vrmagencia al ON (vp.agencia_idllegada = al.agencia_id) "
			+ "	       LEFT JOIN vrtdetman dm ON (vp.venpas_id = dm.venpas_id) "
			+ "	       LEFT JOIN vrtmanifiesto m ON (dm.manifiesto_id = m.manifiesto_id) "
			+ "	WHERE "
			+ "	       vp.venpas_idoriginal in"
			+ "	       (SELECT "
			+ "	               vp.venpas_idoriginal"
			+ "	        FROM vrtvenpas vp"
			+ "	        WHERE "
			+ "	             vp.c_numboleto='" + numeroComprobante + "')"
			+ "	        AND vp.tipmov_id NOT IN (5)"
			+ "	ORDER BY vp.venpas_id";

		log.info(sql);

		List<?> result = getSession().createSQLQuery(sql).list();
		List<VentaPasaje> lstResult = new ArrayList<>();

		for(int i=0; i<result.size(); i++) {
			Object[] obj = (Object[]) result.get(i);
			VentaPasaje ventaPasaje = new VentaPasaje();
			ventaPasaje.setId(((BigDecimal)obj[0]).longValue());
			ventaPasaje.setVentaOriginal(((BigDecimal)obj[1]).longValue());
			Ruta ruta = new Ruta();
			ruta.setId(((BigDecimal)obj[2]).intValue());
			ruta.setOrigen(obj[3].toString());
			ruta.setDestino(obj[4].toString());
			ventaPasaje.setRuta(ruta);
			Cliente cliente = new Cliente();
			cliente.setId( obj[5]==null?0:((BigDecimal)obj[5]).longValue() );
			cliente.setNumeroDocumento(obj[6]==null?"":obj[6].toString());
			cliente.setRazonSocial(obj[7]==null?"":obj[7].toString());
			ventaPasaje.setCliente(cliente);
			Pasajero pasajero = new Pasajero();
			pasajero.setId(((BigDecimal)obj[8]).longValue());
			pasajero.setNumeroDocumento(obj[9]==null?"":obj[9].toString());
			pasajero.setApellidoPaterno(obj[10]==null?"":obj[10].toString());
			pasajero.setApellidoMaterno(obj[11]==null?"":obj[11].toString());
			pasajero.setNombre(obj[12]==null?"":obj[12].toString());
			pasajero.setNombresApellidos(pasajero.getNombre()+" "+pasajero.getApellidoPaterno()+" "+pasajero.getApellidoMaterno());
			ventaPasaje.setPasajero(pasajero);
			FormaPago formaPago = new FormaPago();
			formaPago.setId(((BigDecimal)obj[13]).intValue());
			formaPago.setDenominacion(obj[14].toString());
			ventaPasaje.setFormaPago(formaPago);
			TipoFormaPago tipoFormaPago = new TipoFormaPago();
			tipoFormaPago.setId(((BigDecimal)obj[15]).intValue());
			tipoFormaPago.setDenominacion(obj[16].toString());
			ventaPasaje.setTipoFormaPago(tipoFormaPago);
			Servicio servicio = new Servicio();
			servicio.setId(((BigDecimal)obj[17]).intValue());
			servicio.setDenominacion(obj[18].toString());
			ventaPasaje.setServicio(servicio);
			TipoComprobante tipoComprobante = new TipoComprobante();
			tipoComprobante.setId(((BigDecimal)obj[19]).intValue());
			tipoComprobante.setAbreviatura(obj[20].toString());
			ventaPasaje.setTipoComprobante(tipoComprobante);
			TipoMovimiento tipoMovimiento = new TipoMovimiento();
			tipoMovimiento.setId(((BigDecimal)obj[21]).intValue());
			tipoMovimiento.setDenominacion(obj[22].toString());
			ventaPasaje.setTipoMovimiento(tipoMovimiento);
			Agencia agenciaPartida = new Agencia();
			agenciaPartida.setId( obj[23]==null?0:((BigDecimal)obj[23]).intValue() );
			agenciaPartida.setNombreCorto(obj[24]==null?"":obj[24].toString());
			ventaPasaje.setAgenciaPartida(agenciaPartida);
			Agencia agenciaLlegada = new Agencia();
			agenciaLlegada.setId( obj[25]==null?0:((BigDecimal)obj[25]).intValue());
			agenciaLlegada.setNombreCorto(obj[26]==null?"":obj[26].toString());
			ventaPasaje.setAgenciaLlegada(agenciaLlegada);

			ventaPasaje.setNumeroBoleto(obj[27].toString());
			ventaPasaje.setNumeroBoletoAnterior(obj[28]==null?"":obj[28].toString());
			ventaPasaje.setNumeroControl(obj[29]==null?"":obj[29].toString());
			ventaPasaje.setNumeroPiso( obj[30]==null?0:((BigDecimal)obj[30]).intValue() );
			ventaPasaje.setNumeroAsiento( obj[31]==null?0:((BigDecimal)obj[31]).intValue() );
			ventaPasaje.setSecuencial(((BigDecimal)obj[32]).intValue());
			ventaPasaje.setFechaPartida((Date)obj[33]);
			ventaPasaje.setHoraPartida(obj[34]==null?"":obj[34].toString());
			ventaPasaje.setTarifa(((BigDecimal)obj[35]).doubleValue());
			ventaPasaje.setImportePagado(((BigDecimal)obj[36]).doubleValue());
			ventaPasaje.setImportePagadoByDiferencia(obj[37]==null?0:((BigDecimal)obj[37]).doubleValue());
			ventaPasaje.setTipoTransaccion(obj[38]==null?"":obj[38].toString());
			ventaPasaje.setFechaLiquidacion((Date)obj[39]);

			Agencia agencia = new Agencia();
			agencia.setId(((BigDecimal)obj[40]).intValue());
			agencia.setNombreCorto(obj[41].toString());
			ventaPasaje.setAgencia(agencia);
			Usuario usuario = new Usuario();
			usuario.setId(((BigDecimal)obj[42]).intValue());
			usuario.setApellidoPaterno(obj[43]==null?"":obj[43].toString());
			usuario.setApellidoMaterno(obj[44]==null?"":obj[44].toString());
			usuario.setNombre(obj[45]==null?"":obj[45].toString());
			ventaPasaje.setUsuario(usuario);
			CanalVenta canalVenta = new CanalVenta();
			canalVenta.setId(((BigDecimal)obj[46]).intValue());
			canalVenta.setNombreCorto(obj[47].toString());
			ventaPasaje.setCanalVenta(canalVenta);
			ventaPasaje.setFechaEnvioSFE((Date)obj[48]);
			ventaPasaje.setObservaciones(obj[49]==null?"":obj[49].toString());
			Manifiesto manifiesto = new Manifiesto();
			manifiesto.setId( obj[50]==null?0:((BigDecimal)obj[50]).longValue() );
			manifiesto.setNumeroManifiesto(obj[51]==null?"":obj[51].toString());
			manifiesto.setCodigoBus(obj[52]==null?"":obj[52].toString());
			ventaPasaje.setManifiesto(manifiesto);
			lstResult.add(ventaPasaje);
		}
		return lstResult;
	}

	@Override
	public List<List<VentaPasaje>> obtenerVentasResumenLiquidacion(Integer idAgencia, Integer idUsuario, String fechaLiquidacion) {
		String sql = "SELECT vp.venpas_id, vp.c_numboleto NroBoleto, vp.c_numbolant NroBoletoRef, vp.n_tarifa MontoBase, vp.n_recargo Recargo, "
				+ "vp.n_descuento Descuento, vp.n_acuenta ACuenta, vp.n_penalidad Penalidad, vp.n_imppag NetoPagado, "
				+ "vp.d_fecliq, vp.tipmov_id, vp.tipforpag_id,vp.tipcom_id, NVL(vp.n_imppagdif,0) imppagdif "
				+ "FROM vrtvenpas vp "
				+ "WHERE vp.agencia_id="+idAgencia + " AND vp.usuario_id="+idUsuario+" AND vp.d_fecliq = to_date('"+fechaLiquidacion+"','dd/mm/yyyy') AND "
				+ "vp.forpag_id=1 AND vp.tipcom_id = 7 AND vp.n_tarifa>0 AND vp.tipmov_id NOT IN (4,5,6,10,11,12,13)";

		List<?> result = getSession().createSQLQuery(sql).list();
//		List<VentaPasaje> lstResult = new ArrayList<>();

		for (Object element : result) {
			Object[] obj = (Object[])element;
			VentaPasaje ventaPasaje = new VentaPasaje();
			ventaPasaje.setId(((BigDecimal)obj[0]).longValue());
			ventaPasaje.setNumeroBoleto(obj[1].toString());
			ventaPasaje.setNumeroBoletoAnterior(obj[2].toString());
			ventaPasaje.setTarifa(((BigDecimal)obj[3]).doubleValue());
			ventaPasaje.setRecargo(((BigDecimal)obj[4]).doubleValue());
			ventaPasaje.setDescuento(((BigDecimal)obj[5]).doubleValue());
			ventaPasaje.setAcuenta(((BigDecimal)obj[6]).doubleValue());
			ventaPasaje.setPenalidad(((BigDecimal)obj[7]).doubleValue());
			ventaPasaje.setImportePagado(((BigDecimal)obj[7]).doubleValue());
			ventaPasaje.setFechaLiquidacion((Date)obj[9]);
			TipoMovimiento tipoMovimiento = new TipoMovimiento();
			tipoMovimiento.setId(((BigDecimal)obj[10]).intValue());
			ventaPasaje.setTipoMovimiento(tipoMovimiento);
			TipoFormaPago tipoFormaPago = new TipoFormaPago();
			tipoFormaPago.setId(((BigDecimal)obj[11]).intValue());
			ventaPasaje.setTipoFormaPago(tipoFormaPago);
			TipoComprobante tipoComprobante = new TipoComprobante();
			tipoComprobante.setId(((BigDecimal)obj[12]).intValue());
			ventaPasaje.setTipoComprobante(tipoComprobante);
			ventaPasaje.setImportePagadoByDiferencia(((BigDecimal)obj[13]).doubleValue());
		}
		return null;
	}

	/* (non-Javadoc)
	 * @see pe.itsb.vyrbus.model.dao.VentaPasajesDAO#guardarServicioEspecial(pe.itsb.vyrbus.model.bean.VentaPasaje)
	 */
	@Override
	public void guardarServicioEspecial(VentaPasaje ventaPasaje) throws Exception {
		super.save(ventaPasaje);
	}

	/*
	 * (non-Javadoc)
	 * @see pe.itsb.vyrbus.model.dao.VentaPasajesDAO#buscarFacturasServicioEspecial(java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	public List<VentaPasaje> buscarFacturasServicioEspecial (String numComprobante, String fDesde, String fHasta) throws Exception{
		String sql = "SELECT * FROM vrtvenpas vp "
				+ "INNER JOIN (SELECT MAX(venpas_id) venpas_id, c_numcontrol FROM vrtvenpas WHERE c_tiptra IN (5) GROUP BY c_numcontrol) max_id "
				+ "ON max_id.venpas_id=vp.venpas_id WHERE c_estreg='A' AND c_tiptra=5 ";
//				+ "ON max_id.venpas_id=vp.venpas_id WHERE c_estreg='A' AND tipmov_id= "+Constantes.ID_TIPMOV_SERVICIO_ESPECIAL;

		if(numComprobante != null)
				sql = sql + " AND c_numboleto = '" + numComprobante + "' ";

		sql = sql + " AND d_fecliq BETWEEN to_date('"+fDesde+"', 'dd/mm/yyyy') AND to_date('"+fHasta+"', 'dd/mm/yyyy') ORDER BY c_numboleto";

		List<?> lstResult = getSession().createSQLQuery(sql).list();
		ArrayList<VentaPasaje> lstVentaPasaje = new ArrayList<>();

		for (Object element : lstResult) {
			Object[] obj = (Object[])element;
			VentaPasaje ventaPasaje = new VentaPasaje();
			ventaPasaje = buscarPorId(Long.valueOf(((BigDecimal)obj[0]).longValue()));
			lstVentaPasaje.add(ventaPasaje);
		}
		return lstVentaPasaje;
	}

	/*
	 * (non-Javadoc)
	 * @see pe.itsb.vyrbus.model.dao.VentaPasajesDAO#buscarVentasPagoPilotos(java.lang.String, java.lang.String)
	 */
	@Override
	public List<VentasPiloto> buscarVentasPagoPilotos(String fInicio, String fFin) throws Exception{
		String sql = "SELECT vp.d_fecliq FCOMPRA, vp.c_numboleto DOCUMENTO, DECODE(c.cliente_id, null, null, c.c_numdoc) RUC, p.c_numdoc DNI, "
				+ "DECODE(vp.cliente_id, null, p.c_nomape, c.c_razsoc) RAZON, vp.n_imppag EXONERADO, 0 VENTA, 0 IGV, vp.n_imppag TOTAL, "
				+ "i.d_fecpar FSALIDABUS, to_char(vp.audfecins, 'HH24:mm') HORAVENTA, ao.c_denominacion OF_ORIGEN, ad.c_denominacion OF_DESTINO, "
				+ "(vp.n_numpiso+1)||'-'||vp.n_numasiento ASIENTO, tc.c_denominacion TIPO, vp.c_horpar HORASALIDABUS, m.c_codbus PLACA, "
				+ "m.c_piloto CHOFER, m.c_copiloto CHOFER2, m.c_tripulante TRIPULANTE "
				+ "FROM vrtmanifiesto m "
				+ "INNER JOIN vrtdetman dm ON dm.manifiesto_id=m.manifiesto_id "
				+ "INNER JOIN vrtitinerario i ON i.itinerario_id=m.itinerario_id "
				+ "INNER JOIN vrtvenpas vp ON vp.venpas_id=dm.venpas_id "
				+ "LEFT JOIN vrmcliente c ON c.cliente_id=vp.cliente_id "
				+ "INNER JOIN vrmpasajero p ON p.pasajero_id=vp.pasajero_id "
				+ "INNER JOIN vrmagencia ao ON ao.agencia_id=vp.agencia_idpartida "
				+ "INNER JOIN vrmagencia ad ON ad.agencia_id=vp.agencia_idllegada "
				+ "INNER JOIN vrmtipcom tc ON tc.tipcom_id=vp.tipcom_id "
				+ "WHERE i.d_fecpar BETWEEN to_date('"+fInicio+"','dd/MM/yyyy') AND to_date('"+fFin+"','dd/MM/yyyy')";

		List<?> result = getSession().createSQLQuery(sql).list();
		List<VentasPiloto> lstVentas = new ArrayList<>();
		for(int i=0; i<result.size(); i++) {
			Object[] obj = (Object[])result.get(i);
			VentasPiloto ventasPiloto = new VentasPiloto();
			ventasPiloto.setFechaCompra((Date)obj[0]);
			ventasPiloto.setNumeroBoleto(obj[1].toString());
			ventasPiloto.setRuc(obj[2]==null?null:obj[2].toString());
			ventasPiloto.setDni(obj[3].toString());
			ventasPiloto.setNombres(obj[4].toString());
			ventasPiloto.setExonerado(((BigDecimal)obj[5]).doubleValue());
			ventasPiloto.setVenta(((BigDecimal)obj[6]).doubleValue());
			ventasPiloto.setIgv(((BigDecimal)obj[7]).doubleValue());
			ventasPiloto.setTotal(((BigDecimal)obj[8]).doubleValue());
			ventasPiloto.setFechaSalidaBus((Date)obj[9]);
			ventasPiloto.setHoraVenta(obj[10].toString());
			ventasPiloto.setOrigen(obj[11].toString());
			ventasPiloto.setDestino(obj[12].toString());
			ventasPiloto.setAsiento(obj[13].toString());
			ventasPiloto.setTipo(obj[14].toString());
			ventasPiloto.setHoraSalidaBus(obj[15].toString());
			ventasPiloto.setCodigo(obj[16].toString());
			ventasPiloto.setPiloto(obj[17].toString());
			ventasPiloto.setCopiloto(obj[18]==null?null:obj[18].toString());
			ventasPiloto.setTripulante(obj[19]==null?null:obj[19].toString());
			lstVentas.add(ventasPiloto);
		}
		return lstVentas;
	}

	@Override
	public List<VentasPiloto> buscarRegistroVentas(String fInicio, String fFin) throws Exception{
		String sql = "SELECT \r\n" +
				"      V.FECHA, V.TD, V.SERIE, V.NUMERO, V.DNI, V.RAZON_SOCIAL, \r\n" +
				"      V.EXONERADO, V.V_VENTA, V.IGV, V.TOTAL, V.DESTINO, \r\n" +
				"      V.ASTO, V.tipcom_id, V.tipmov_id\r\n" +
				"FROM (\r\n" +
				//--VENTA PASAJES BOLETAS
				"SELECT \r\n" +
				"      vp.d_fecliq FECHA, decode(vp.tipcom_id, 2, '16', 7, '16', 8, '07') TD, \r\n" +
				"      substr(vp.c_numboleto, 1, 4) SERIE, substr(vp.c_numboleto, 6, 8) NUMERO, \r\n" +
				"      p.c_numdoc DNI, p.c_nomape RAZON_SOCIAL, vp.n_imppag EXONERADO, 0.00 V_VENTA, \r\n" +
				"      0.00 IGV, vp.n_imppag TOTAL, r.c_destino DESTINO, \r\n" +
				"      to_char(vp.n_numpiso+1, '9')||'-'||trim(to_char(vp.n_numasiento, '99')) ASTO, \r\n" +
				"      vp.tipcom_id, vp.tipmov_id\r\n" +
				"from \r\n" +
				"      vrtvenpas vp \r\n" +
				"      INNER JOIN vrmpasajero p ON (vp.pasajero_id = p.pasajero_id)\r\n" +
				"      INNER JOIN vrmruta r ON (vp.ruta_id = r.ruta_id)\r\n" +
				"      LEFT JOIN vrmcliente c ON (vp.cliente_id = c.cliente_id)\r\n" +
				"where \r\n" +
				"      vp.d_fecliq between to_date('"+fInicio+"', 'dd/MM/yyyy') \r\n" +
				"      AND to_date('"+fFin+"', 'dd/MM/yyyy')\r\n" +
				"      AND vp.c_tiptra <> (2)\r\n" +
				"      AND vp.tipmov_id not in (5, 6, 16, 17, 18)\r\n" +
				"      AND vp.tipcom_id = 7\r\n" +
				"      \r\n" +
				"UNION ALL      \r\n" +
				//--VENTA PASAJES FACTURAS
				"SELECT \r\n" +
				"      vp.d_fecliq FECHA, decode(vp.tipcom_id, 2, '16', 7, '16', 8, '07') TD, \r\n" +
				"      substr(vp.c_numboleto, 1, 4) SERIE, substr(vp.c_numboleto, 6, 8) NUMERO, \r\n" +
				"      c.c_numdoc DNI, c.c_razsoc RAZON_SOCIAL, vp.n_imppag EXONERADO, 0.00 V_VENTA, \r\n" +
				"      0.00 IGV, vp.n_imppag TOTAL, r.c_destino DESTINO, \r\n" +
				"      to_char(vp.n_numpiso+1, '9')||'-'||trim(to_char(vp.n_numasiento, '99')) ASTO, \r\n" +
				"      vp.tipcom_id, vp.tipmov_id\r\n" +
				"from \r\n" +
				"      vrtvenpas vp \r\n" +
				"      INNER JOIN vrmcliente c ON (vp.cliente_id = c.cliente_id)\r\n" +
				"--      INNER JOIN vrmpasajero p ON (vp.pasajero_id = p.pasajero_id)\r\n" +
				"      INNER JOIN vrmruta r ON (vp.ruta_id = r.ruta_id)\r\n" +
				"where \r\n" +
				"      vp.d_fecliq between to_date('"+fInicio+"', 'dd/MM/yyyy') \r\n" +
				"      AND to_date('"+fFin+"', 'dd/MM/yyyy')\r\n" +
				"      AND vp.c_tiptra <> (2)\r\n" +
				"      AND vp.tipmov_id not in (5, 6, 16, 17, 18)\r\n" +
				"      AND vp.tipcom_id = 2 \r\n" +
				"UNION ALL \r\n" +
				//--VENTA PASAJES NC
				"SELECT \r\n" +
				"      vp.d_fecliq FECHA, decode(vp.tipcom_id, 8, '07') TD, \r\n" +
				"      substr(vp.c_numboleto, 1, 4) SERIE, substr(vp.c_numboleto, 6, 8) NUMERO, \r\n" +
				"      p.c_numdoc DNI, p.c_nomape RAZON_SOCIAL, vp.n_imppag EXONERADO, 0.00 V_VENTA, \r\n" +
				"      0.00 IGV, vp.n_imppag TOTAL, r.c_destino DESTINO, \r\n" +
				"      to_char(vp.n_numpiso+1, '9')||'-'||trim(to_char(vp.n_numasiento, '99')) ASTO, \r\n" +
				"      vp.tipcom_id, vp.tipmov_id\r\n" +
				"from \r\n" +
				"      vrtvenpas vp \r\n" +
				"      INNER JOIN vrmpasajero p ON (vp.pasajero_id = p.pasajero_id)\r\n" +
				"      INNER JOIN vrmruta r ON (vp.ruta_id = r.ruta_id)\r\n" +
				"where \r\n" +
				"      vp.d_fecliq between to_date('"+fInicio+"', 'dd/MM/yyyy') \r\n" +
				"      AND to_date('"+fFin+"', 'dd/MM/yyyy')\r\n" +
				"      AND vp.c_tiptra <> (2)\r\n" +
				"      AND vp.tipmov_id not in (5, 6, 16, 17, 18)\r\n" +
				"      AND vp.tipcom_id = 8 \r\n" +
				"UNION ALL       \r\n" +
				//--VENTA PASAJES otros movimientos
				"SELECT \r\n" +
				"      vp.d_fecliq FECHA, DECODE(vp.tipcom_id, 2, '01', 7, '03', 8, '07') TD, \r\n" +
				"      SUBSTR(vp.c_numboleto, 1, 4) SERIE, SUBSTR(vp.c_numboleto, 6, 8) NUMERO, \r\n" +
				"      CASE vp.tipcom_id WHEN 2 then c.c_numdoc WHEN 7 THEN p.c_numdoc END DNI, \r\n" +
				"      CASE vp.tipcom_id WHEN 2 then c.c_razsoc WHEN 7 THEN p.c_nomape END RAZON_SOCIAL, \r\n" +
				"      0.00 EXONERADO, \r\n" +
				"      CASE vp.c_tiptra WHEN '5' THEN vp.n_imppag-vp.n_igv else vp.n_imppag END V_VENTA, \r\n" +
				"      vp.n_igv IGV, \r\n" +
				"      vp.n_imppag TOTAL, \r\n" +
				"      r.c_destino DESTINO, to_char(vp.n_numpiso+1, '9')||'-'||trim(to_char(vp.n_numasiento, '99')) ASTO, \r\n" +
				"      vp.tipcom_id, vp.tipmov_id \r\n" +
				"from \r\n" +
				"      vrtvenpas vp \r\n" +
				"      INNER JOIN vrmpasajero p ON (vp.pasajero_id = p.pasajero_id)\r\n" +
				"      INNER JOIN vrmruta r ON (vp.ruta_id = r.ruta_id)\r\n" +
				"      LEFT JOIN vrmcliente c ON (vp.cliente_id = c.cliente_id)\r\n" +
				"where \r\n" +
				"      vp.d_fecliq between to_date('"+fInicio+"', 'dd/MM/yyyy') \r\n" +
				"      AND to_date('"+fFin+"', 'dd/MM/yyyy')\r\n" +
				"      AND vp.c_tiptra <> (2)\r\n" +
				"      AND vp.tipmov_id  in (14, 16, 17, 18) \r\n" +
				"--ORDER BY \r\n" +
				"--      vp.d_fecliq, substr(vp.c_numboleto, 1, 4), substr(vp.c_numboleto, 6, 8)\r\n" +
				"      ) V \r\n" +
				"ORDER BY V.FECHA, V.TD, V.SERIE, V.NUMERO\r\n";


		List<?> result = getSession().createSQLQuery(sql).list();
		List<VentasPiloto> lstVentas = new ArrayList<>();
		for(int i=0; i<result.size(); i++) {
			Object[] obj = (Object[])result.get(i);
			VentasPiloto regVentas = new VentasPiloto();
			regVentas.setFechaCompra((Date)obj[0]);
			regVentas.setTipoDocumentoSunat(obj[1].toString());
			regVentas.setSerie(obj[2].toString());
			regVentas.setNumero(obj[3].toString());
			regVentas.setNumeroBoleto(obj[2].toString()+"-"+obj[3].toString());
			regVentas.setRuc("");
			regVentas.setDni(obj[4].toString());
			regVentas.setNombres(obj[5].toString());
			regVentas.setExonerado(((BigDecimal)obj[6]).doubleValue());
			regVentas.setVenta(((BigDecimal)obj[7]).doubleValue());
			regVentas.setIgv(((BigDecimal)obj[8]).doubleValue());
			regVentas.setTotal(((BigDecimal)obj[9]).doubleValue());
			regVentas.setDestino(obj[10].toString());
			regVentas.setAsiento(obj[11].toString());

			TipoComprobante tipoComprobante = new TipoComprobante();
			tipoComprobante.setId(((BigDecimal)obj[12]).intValue());
			regVentas.setTipoComprobante(tipoComprobante);

			TipoMovimiento tipoMovimiento = new TipoMovimiento();
			tipoMovimiento.setId(((BigDecimal)obj[13]).intValue());
			regVentas.setTipoMovimiento(tipoMovimiento);

			lstVentas.add(regVentas);
		}
		return lstVentas;
	}

	/* (non-Javadoc)
	 * @see pe.itsb.vyrbus.model.dao.VentaPasajesDAO#buscarBoletosPerdidaServicio(java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	public List<VentaPasaje> buscarBoletosPerdidaServicio(String numeroDocumento, String numeroControl, String numeroBoleto) throws Exception {
		String criterio = numeroDocumento==null?"":" AND p.c_numdoc = '" + numeroDocumento + "' ";
		criterio = criterio + (numeroControl==null?"":" AND {VP}.c_numcontrol='"+ numeroControl +"' ");
		criterio = criterio + (numeroBoleto==null?"":(" AND {VP}.c_numboleto='" + numeroBoleto.toUpperCase() +"' "));

		String criterioByMax=" WHERE c_estreg='"+Constantes.VALUE_ACTIVO+"' ";
		if(numeroControl!=null)
			criterioByMax+="AND c_numcontrol='"+numeroControl+"' ";
		else if (numeroBoleto!=null)
			criterioByMax+="AND c_numboleto='"+numeroBoleto.toUpperCase()+"' ";

		String sql = "SELECT {VP.*} FROM vrtvenpas {VP} " +
				"INNER JOIN (SELECT MAX(venpas_id)venpas_id, c_numcontrol FROM vrtvenpas "+criterioByMax+" GROUP BY c_numcontrol) max_vta " +
				"ON max_vta.venpas_id={VP}.venpas_id " +
				"INNER JOIN vrmruta r ON r.ruta_id={VP}.ruta_id " +
				"INNER JOIN vrmagencia a ON a.agencia_id={VP}.agencia_id " +
				"INNER JOIN vrmpasajero p ON p.pasajero_id={VP}.pasajero_id " +
				"WHERE {VP}.c_tiptra in('"+Constantes.TIPO_OPERACION_VENTA+"','"+Constantes.TIPO_OPERACION_VENTA_POOL+"','"+Constantes.TIPO_OPERACION_PERDIDA_SERVICIO+"') AND {VP}.c_estreg='"+Constantes.VALUE_ACTIVO+"' "
			  + "AND {VP}.tipcom_id IN ("+Constantes.ID_TIPCOM_BOLETO_VIAJE+","+Constantes.ID_TIPCOM_BOLETA_VENTA+", "+Constantes.ID_TIPCOM_FACTURA+")" ;

		sql = sql + criterio;
		sql = sql + "ORDER BY {VP}.c_numboleto, {VP}.d_fecpar, {VP}.c_horpar ";

		log.info(sql);
		List<?> result = getSession().createSQLQuery(sql).addEntity("VP",VentaPasaje.class).list();

		return (List<VentaPasaje>)result;
	}

	/* (non-Javadoc)
	 * @see pe.itsb.vyrbus.model.dao.VentaPasajesDAO#guardarPerdidaServicio(java.lang.Long, java.lang.String)
	 */
	@Override
	public void guardarPerdidaServicio(VentaPasaje perdidaServicio) throws Exception {
		String sql = "UPDATE vrtvenpas SET c_tiptra="+perdidaServicio.getTipoTransaccion()+", c_observaciones='"+perdidaServicio.getObservaciones()+
				"', audusumod='"+perdidaServicio.getUsuarioModificacion()+"', audipmodi='"+perdidaServicio.getIpModificacion()+
				"' WHERE venpas_id="+perdidaServicio.getId();

		getSession().createSQLQuery(sql).executeUpdate();

	}

	/*
	 * (non-Javadoc)
	 * @see pe.itsb.vyrbus.model.dao.VentaPasajesDAO#buscarLiquidacionBus(java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	public List<Manifiesto> buscarLiquidacionBus(String fechaInicio,String fechaFin, String codigoBus) throws Exception {
		String sql = "SELECT it.d_Fecpar fecha_salida, bs.c_codigo bus_codigo, bs.c_numplaca bus_placa "
						 + ",ma.c_piloto piloto, ma.c_copiloto copiloto, SUM(vp.n_imppag) total_soles, rt.c_origen, rt.c_destino "
						 + ",count(vp.c_numboleto) cant "
				   + "FROM vrtvenpas vp "
				     + "INNER JOIN (Select max(vp.venpas_id) venpas_id From vrtvenpas vp Group By vp.c_numcontrol ) "
				                  + "vpx ON (vpx.venpas_id = vp.venpas_id)  "
				     + "INNER JOIN vrtmanifiesto ma ON (ma.itinerario_id=vp.itinerario_id) "
				     + "INNER JOIN vrtitinerario it ON (it.itinerario_id=vp.itinerario_id) "
				     + "INNER JOIN vrmbus bs ON (bs.bus_id=ma.bus_id) "
//				     + "INNER JOIN vrmruta rt ON (rt.ruta_id=vp.ruta_id) "
				     + "INNER JOIN vrmruta rt ON (rt.ruta_id=it.ruta_idmayor) "
				   + "WHERE it.d_fecpar BETWEEN to_date('"+fechaInicio+"', 'dd/MM/yyyy') AND to_date('"+fechaFin+"', 'dd/MM/yyyy') "
				     + "AND vp.tipmov_id NOT IN (5,6,13) "
				     + "AND vp.c_tiptra = '1' "
				     + "AND vp.tipcom_id IN (1,2,7) AND vp.c_estreg='A' "
				     + "AND ma.c_estreg = 'A' "
				     + "AND it.n_esanulado = 0 AND it.c_estreg = 'A' "
				     + "AND bs.c_codigo = NVL("+(codigoBus!=null?"'"+ codigoBus + "'": "Null")+", bs.c_codigo) "
				   + "GROUP BY it.d_Fecpar, bs.c_codigo, bs.c_numplaca, ma.c_piloto, ma.c_copiloto, rt.c_origen, rt.c_destino "
				   + "ORDER BY it.d_Fecpar, bs.c_codigo, ma.c_piloto, ma.c_copiloto";
		log.info("buscarLiquidacionBus: "+sql);

		List<?> result = getSession().createSQLQuery(sql).list();

		List<Manifiesto> listManifiestos = new ArrayList<>();
		for(int i = 0; i<result.size(); i++){
			Object[] obj = (Object[]) result.get(i);

			Itinerario itinerario= new Itinerario();
			itinerario.setFechaPartida((Date)obj[0]);
			Bus bus = new Bus();
			bus.setCodigo(obj[1].toString());
			bus.setNumeroPlaca(obj[2].toString());
			Ruta ruta = new Ruta();
			ruta.setOrigen(obj[6].toString());
			ruta.setDestino(obj[7].toString());
			itinerario.setRuta(ruta);

			Manifiesto manifiesto = new Manifiesto();
			manifiesto.setItinerario(itinerario);
			manifiesto.setBus(bus);
			manifiesto.setPiloto(obj[3]!=null?obj[3].toString():"");
			manifiesto.setCopiloto(obj[4]!=null?obj[4].toString():"");
			manifiesto.setImporte(((BigDecimal)obj[5]).doubleValue());
			manifiesto.setCantidadPasajeros(((BigDecimal)obj[8]).intValue());

			listManifiestos.add(manifiesto);
		}

		return listManifiestos;
	}

	@SuppressWarnings("null")
	@Override
	public Map<String, EntidadEncomiendaPasajes> buscarEquivalenciaEntidades(Integer tipoEntidad) throws Exception {
		String sql = "select \r\n" +
				"       et.ent_idtranscar||'-'||et.tipent_idvyrtranscar clave, \r\n" +
				"       et.ent_idvyr idvyr, et.ent_idtranscar idtranscar, et.tipent_idvyrtranscar  \r\n" +
				"from \r\n" +
				"       VRTENTVYR_TRANSCAR et \r\n" +
				"where  \r\n" +
				"       et.tipent_idvyrtranscar=NVL("+tipoEntidad+" , et.tipent_idvyrtranscar) \r\n" +
				"order by \r\n" +
				"      et.ent_idtranscar";

		log.info("buscarLiquidacionBus: "+sql);

		List<?> result = getSession().createSQLQuery(sql).list();

		EntidadEncomiendaPasajes objEntEncoPas = null;
		TreeMap<String, EntidadEncomiendaPasajes> lstValores = new TreeMap<>();
		String key = null;

		for (Object element : result) {
			Object[] obj = (Object[]) element;

			key = obj[0].toString();
			objEntEncoPas = new EntidadEncomiendaPasajes();
			objEntEncoPas.setIdAgenciaPasajes(((BigDecimal)obj[1]).intValue());
			objEntEncoPas.setIdAgenciaEncomienda(((BigDecimal)obj[2]).intValue());
			objEntEncoPas.setIdTipoEntidad(((BigDecimal)obj[3]).intValue());
			lstValores.put(key, objEntEncoPas);

		}

		return lstValores;
	}

	/* (non-Javadoc)
	 * @see pe.itsb.vyrbus.model.dao.VentaPasajesDAO#buscarAvanceVentasByTarifario(java.lang.String, java.lang.Integer, java.lang.Integer, java.lang.Integer)
	 */
	@Override
	public List<TarifarioByAsientoByAvanceVentas> buscarAvanceVentasByTarifarioByAsiento(String fecha, Integer servicio_id,
			Integer localidad_idOrigen, Integer localidad_idDestino) throws Exception {
		String sql="SELECT P1.itinerario_id ,P1.ruta_idmayor ,P1.d_fecpar ,R.c_Origen ,R.c_Destino "
			       + ",P1.c_origen Ori_Mayor ,P1.c_destino Des_Mayor ,P1.c_horpar ,P1.c_denominacion AS Servicio "
			       + ",decode(P2.Flag, '1', P2.paxSuite, 0) PaxSuite,decode(P2.Flag, '1', P2.paxOtro, 0) paxOtro, P1.Capbus "
			       + ",NVL(PR.CantPaxAsiSuite,0) PaxSuiteByRuta, NVL(PR.CantPaxAsiOtro,0) PaxOtroByRuta "
			       + ",r.ruta_id, servicio_id, di.c_horpar, di.d_fecpar fecparTramo "
			 + "FROM(SELECT CASE WHEN rm.localidad_idorigen=13 THEN 1 "
			 				  + "WHEN rm.localidad_iddestino=13 THEN 2 "
			 		     + "END tipoRuta "
			 			+ ",i.itinerario_id, i.ruta_idmayor, s.c_denominacion, NVL(s.n_numasipis1,0)+NVL(s.n_numasipis2,0) Capbus "
			 	        + ",i.d_fecpar, i.c_horpar, rm.c_origen  ,rm.c_destino, i.servicio_id "
			 	  + "FROM vrtitinerario i "
			 	    + "INNER JOIN vrmruta rm ON (i.ruta_idmayor = rm.ruta_id) "
			 	    + "INNER JOIN vrmservicio s ON (i.servicio_id = s.servicio_id) "
			 	  + "WHERE  i.d_fecpar BETWEEN to_date('"+fecha+"','dd/MM/yyyy') AND to_date('"+fecha+"','dd/MM/yyyy') "
			 	    + "AND i.n_esanulado=0 AND rm.localidad_idorigen=rm.localidad_idorigen "
			 	    + "AND rm.localidad_iddestino=rm.localidad_iddestino "
			 	 + ") P1 "
			   + "LEFT JOIN ( SELECT Flag, itinerario_id, SUM(paxSuite)paxSuite, SUM(paxOtro) paxOtro "
			               + "FROM( SELECT '1' Flag, vt.itinerario_id, DECODE(mb.tipasi_id,3,COUNT(*),0) paxSuite, DECODE(mb.tipasi_id,3,0,COUNT(*)) paxOtro "
			                     + "FROM vrtvenpas vt "
			                       + "INNER JOIN (SELECT MAX(v.venpas_id) venpas_id "
			                                   + "FROM vrtvenpas v "
			                                     + "INNER JOIN vrtitinerario i on (i.itinerario_id=v.itinerario_id) "
			                                   + "WHERE i.d_fecpar BETWEEN to_date('"+fecha+"','dd/MM/yyyy') AND to_date('"+fecha+"','dd/MM/yyyy') "
			                                   + "GROUP BY v.c_numcontrol "
			                                  + ") B on (vt.venpas_id = B.venpas_id) "
			                       + "INNER JOIN vrtitinerario i on (i.itinerario_id=vt.itinerario_id) "
			                       + "INNER JOIN vrtmapabus mb ON (mb.servicio_id=i.servicio_id AND mb.n_numasi=vt.n_numasiento) "
			                     + "WHERE  i.d_fecpar BETWEEN to_date('"+fecha+"','dd/MM/yyyy') AND to_date('"+fecha+"','dd/MM/yyyy') "
			                       + "AND vt.tipmov_id NOT IN (13,5,6) AND vt.c_tiptra=1 "
			                     + "GROUP BY vt.itinerario_id, mb.tipasi_id "
			                   + ") "
			               + "GROUP BY Flag, itinerario_id "
			              + ") P2 ON  (P2.itinerario_id = P1.itinerario_id) "
			    + "INNER JOIN VRMRUTA rm ON (rm.ruta_id=P1.ruta_idmayor) "
			    + "INNER JOIN VRTDETITI di ON (di.itinerario_id=P1.itinerario_id) "
			    + "INNER JOIN VRMRUTA r ON (r.ruta_id=di.ruta_id) "
			    + "LEFT JOIN(SELECT SUM(CantPaxSuite)CantPaxAsiSuite, SUM(CantPaxOtro)CantPaxAsiOtro,ruta_id, Itinerario_Id "
			    		  + "FROM( SELECT DECODE(mb.tipasi_id,3,COUNT(*),0) CantPaxSuite, DECODE(mb.tipasi_id,3,0,COUNT(*)) CantPaxOtro "
			    		              + ",vt.ruta_id, vt.Itinerario_Id "
			    		        + "FROM vrtvenpas vt "
			    		          + "INNER JOIN (SELECT MAX(v.venpas_id) venpas_id "
			    		                      + "FROM vrtvenpas v "
			    		                        + "INNER JOIN vrtitinerario i on (i.itinerario_id=v.itinerario_id) "
			    		                      + "WHERE i.d_fecpar BETWEEN to_date('"+fecha+"','dd/MM/yyyy') AND to_date('"+fecha+"','dd/MM/yyyy') "
			    		                      + "GROUP BY v.c_numcontrol "
			    		                     + ") B on (vt.venpas_id = B.venpas_id) "
			    		          + "INNER JOIN vrtitinerario i on (i.itinerario_id=vt.itinerario_id) "
			    		          + "INNER JOIN VRTMAPABUS mb ON (mb.servicio_id=i.servicio_id AND vt.n_numasiento=mb.n_numasi) "
			    		        + "WHERE  i.d_fecpar BETWEEN to_date('"+fecha+"','dd/MM/yyyy') AND to_date('"+fecha+"','dd/MM/yyyy') "
			    		        + "AND vt.tipmov_id NOT IN (13,5,6) AND vt.c_tiptra=1 "
			    		        + "GROUP BY vt.ruta_id, vt.itinerario_id,mb.tipasi_id "
			    		      + ") "
			    		  + "GROUP BY ruta_id, itinerario_id "
			    		 + ") PR ON  (PR.ruta_id=r.ruta_id AND PR.itinerario_id=P1.itinerario_id) "
			 + "WHERE rm.localidad_idorigen=NVL("+localidad_idOrigen+",rm.localidad_idorigen) "
			   + "AND rm.localidad_iddestino=NVL("+localidad_idDestino+",rm.localidad_iddestino) "
			   + "AND servicio_id=NVL("+servicio_id+",servicio_id) "
			 + "ORDER BY tipoRuta, P1.d_fecpar, P1.c_horpar,di.d_fecpar, di.c_horpar, P1.c_origen, P1.c_destino, P1.c_denominacion";
	log.info(sql);
	List<?>result=getSession().createSQLQuery(sql).list();
	
	List<TarifarioByAsientoByAvanceVentas>listTarifarioVanceVentas= new ArrayList<>();
	for(int x=0;x<result.size();x++){
		Object[] obj=(Object[]) result.get(x);
		Long itinerario_id=((BigDecimal)obj[0]).longValue();
		
		/*Valida duplicidad de rutas*/
		boolean addRegistro=true;
		for(TarifarioByAsientoByAvanceVentas tarifarioByAvanceVentas:listTarifarioVanceVentas){
			if(itinerario_id.longValue()==tarifarioByAvanceVentas.getItinerario_id().longValue()){
				addRegistro=false;
				break;
			}
				
		}
		
		if(addRegistro){
			Ruta ruta_mayor=new Ruta();
			ruta_mayor.setId(((BigDecimal)obj[1]).intValue());
			ruta_mayor.setOrigen(obj[5].toString());
			ruta_mayor.setDestino(obj[6].toString());
			
			Servicio servicio=new Servicio();
			servicio.setId(((BigDecimal)obj[15]).intValue());
			servicio.setDenominacion(obj[8].toString());
			
			TarifarioByAsientoByAvanceVentas tarifarioByAvanceVentas=new TarifarioByAsientoByAvanceVentas();
			tarifarioByAvanceVentas.setItinerario_id(itinerario_id);
			tarifarioByAvanceVentas.setFecha((Date)obj[2]);
			tarifarioByAvanceVentas.setHoraSalida(obj[7].toString());
			tarifarioByAvanceVentas.setRutaMayor(ruta_mayor);
			tarifarioByAvanceVentas.setServicio(servicio);
			tarifarioByAvanceVentas.setOcupacionAsientosSuite(((BigDecimal)obj[9]).intValue());
			tarifarioByAvanceVentas.setOcupacionAsientosOtros(((BigDecimal)obj[10]).intValue());			
			
			List<TarifarioByAsientoSubByAvanceVentas>listTarifarioSubAvanceVentas= new ArrayList<>();
			for(int i=0;i<result.size();i++){
				Object[] subObj=(Object[]) result.get(i);				
				Long _itinerario_id=((BigDecimal)subObj[0]).longValue();
				
				if(_itinerario_id.longValue()==tarifarioByAvanceVentas.getItinerario_id().longValue()){
					Ruta ruta= new Ruta();
					ruta.setId(((BigDecimal)subObj[14]).intValue());
					ruta.setOrigen(subObj[3].toString());
					ruta.setDestino(subObj[4].toString());
					
					TarifarioByAsientoSubByAvanceVentas subByAvanceVentas= new TarifarioByAsientoSubByAvanceVentas();
					subByAvanceVentas.setRuta(ruta);
					subByAvanceVentas.setOcupacionAsientosSuite(((BigDecimal)subObj[12]).intValue());
					subByAvanceVentas.setOcupacionAsientosOtros(((BigDecimal)subObj[13]).intValue());						
					subByAvanceVentas.setHoraSalida(obj[16].toString());
					subByAvanceVentas.setFecha((Date)subObj[17]);
					listTarifarioSubAvanceVentas.add(subByAvanceVentas);
				}				
			}
			tarifarioByAvanceVentas.setTarifarioByAsientoSubByAvanceVentas(listTarifarioSubAvanceVentas);
			listTarifarioVanceVentas.add(tarifarioByAvanceVentas);
		}			
	}
	
	
	return listTarifarioVanceVentas;
	}
}

