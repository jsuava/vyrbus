package pe.itsb.vyrbus.model.dao.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.TreeMap;

import pe.itsb.vyrbus.model.bean.CarteraCliente;
import pe.itsb.vyrbus.model.bean.Cliente;
import pe.itsb.vyrbus.model.bean.LineaCreditoCliente;
import pe.itsb.vyrbus.model.bean.SolicitudCartera;
import pe.itsb.vyrbus.model.bean.SolicitudClienteCredito;
import pe.itsb.vyrbus.model.bean.TipoCobranza;
import pe.itsb.vyrbus.model.bean.Usuario;
import pe.itsb.vyrbus.model.bean.UsuarioAprobador;
import pe.itsb.vyrbus.model.dao.LineaCreditoClienteDAO;
import pe.itsb.vyrbus.service.locator.ServiceLocator;
import pe.itsb.vyrbus.service.util.Constantes;

/**
 *
 * @author JABANTO
 *
 */
@SuppressWarnings("unchecked")
public class LineaCreditoClienteDAOImpl extends GenericDAOImpl  implements LineaCreditoClienteDAO{

	/*
	 * (non-Javadoc)
	 * @see com.tepsa.sisvyr.model.dao.LineaCreditoClienteDAO#buscarPorEstadoRegistro(java.lang.String, java.lang.String)
	 */
	@Override
	public ArrayList<LineaCreditoCliente> buscarPorEstadoRegistro(String estado, String criterioOrden) {
		return (ArrayList<LineaCreditoCliente>)super.findByEstadoRegistro(LineaCreditoCliente.class, estado, criterioOrden);
	}

	/*
	 * (non-Javadoc)
	 * @see com.tepsa.sisvyr.model.dao.LineaCreditoClienteDAO#buscarPorX(java.util.TreeMap, java.util.List)
	 */
	@Override
	public ArrayList<LineaCreditoCliente> buscarPorX(TreeMap<String, Object> criteriosBusqueda,List<String> criteriosOrdenar) {
		return (ArrayList<LineaCreditoCliente>)super.findByX(LineaCreditoCliente.class, criteriosBusqueda, criteriosOrdenar);
	}

	/*
	 * (non-Javadoc)
	 * @see com.tepsa.sisvyr.model.dao.LineaCreditoClienteDAO#buscarPorId(java.lang.Long)
	 */
	@Override
	public LineaCreditoCliente buscarPorId(Long id) {
		return (LineaCreditoCliente)super.findById(LineaCreditoCliente.class, id);
	}

	/*
	 * (non-Javadoc)
	 * @see com.tepsa.sisvyr.model.dao.LineaCreditoClienteDAO#guardar(com.tepsa.sisvyr.model.bean.LineaCreditoCliente)
	 */
	@Override
	public void guardar(LineaCreditoCliente lineaCreditoCliente) {
		super.save(lineaCreditoCliente);
	}

	/*
	 * (non-Javadoc)
	 * @see com.tepsa.sisvyr.model.dao.LineaCreditoClienteDAO#actualizar(com.tepsa.sisvyr.model.bean.LineaCreditoCliente)
	 */
	@Override
	public void actualizar(LineaCreditoCliente lineaCreditoCliente) {
		super.update(lineaCreditoCliente);
	}

	/*
	 * (non-Javadoc)
	 * @see com.tepsa.sisvyr.model.dao.LineaCreditoClienteDAO#inactivar(java.lang.Long)
	 */
	@Override
	public void inactivar(Long id) {
		super.inactivate(LineaCreditoCliente.class, id);
	}

	/*
	 * (non-Javadoc)
	 * @see com.tepsa.sisvyr.model.dao.SolicitudClienteCreditoDAO#buscarSolicitudLineaCredito(java.lang.String, java.lang.String, java.lang.String, java.lang.Long, java.lang.Integer, java.lang.Boolean)
	 */
	@Override
	public List<LineaCreditoCliente> buscarSolicitudLineaCreditoN2(String fechaInicio, String fechaFin, String estadoSolicitud, Long idCliente,UsuarioAprobador usuarioAprobador, Boolean recu_Historia){
		String sql="SELECT u.usuario_id, u.c_apepat as FApePaterno,u.c_apemat as FApeMaterno, u.c_nombre as FNombre, "+ //0-3
			       "c.cliente_id, c.c_numdoc as Ruc, c.c_razsoc as RazonSocial,c.c_origen, scr.solcar_id, scr.d_fecsoli as FechaSolicitud, "+ //4-9
			       "scr.d_fecapro as FechaAprobacion, scr.d_fecanul as FechaAnulacion, scr.c_estsol, "+ //10-12
			       "sc.n_descbaja as DestBaja, sc.n_descalta as DestAlta, "+ //13-14
			       "scr.solclicre_id, scr.n_lincresol as creditoSolicitadi,  scr.n_sobregiro as Sobregiro, "+ //15-17
			       "scr.n_lincreapro as CreditoAprobado, scr.c_escomision, scr.c_escanje, scr.c_esamplia,  "+ //18-21
			       "tc.tipcob_id, tc.c_denominacion,null, scr.d_fecapro, scr.d_fecanul, scr.c_estsol, scr.c_obser "; //22-28
		if(recu_Historia){
			//Obtiene las solicitudes aprobadas o desaprobadas
			sql+=", scrr.n_nivapro AS nivelUlmaAprobacion "+   // 29
					"FROM vrtsolcar sc  "+
					"INNER JOIN vrmusuario u ON (u.usuario_id=sc.usuario_id) "+
					"INNER JOIN vrmcliente c ON (c.cliente_id=sc.cliente_id)  " +
					"INNER JOIN (SELECT * FROM vrtsolclicre scr  "+
					            "WHERE to_date(scr.d_fecapro,'dd/mm/yyy') BETWEEN to_date('"+fechaInicio+"','dd/MM/yyyy') AND to_date('"+fechaFin+"','dd/MM/yyyy') AND scr.n_nivapro=2 "+
					              ") scr ON (scr.solcar_id=sc.solcar_id)  "+
					"INNER JOIN vrmtipcob tc ON (tc.tipcob_id=scr.tipCob_id) "+
					"INNER JOIN (SELECT * FROM vrtsolclicre   "+
					           "WHERE solclicre_id in (SELECT max(scr.solclicre_id)FROM vrtsolclicre scr "+
					                                  "WHERE to_date(scr.d_fecapro,'dd/mm/yyy') BETWEEN to_date('"+fechaInicio+"','dd/MM/yyyy') AND to_date('"+fechaFin+"','dd/MM/yyyy') "+
					                                  "GROUP BY scr.c_numcontrol )     "+
					           ") scrr ON (scrr.c_numcontrol=scr.c_numcontrol)   "+
					"WHERE to_date(scr.audfecins,'dd/MM/yy') BETWEEN to_date('"+fechaInicio+"','dd/MM/yyyy') AND to_date('"+fechaFin+"','dd/MM/yyyy') "+
					"AND scr.c_estsol LIKE '"+estadoSolicitud+"%'  ";
			if(idCliente !=null)
				sql+= " AND c.cliente_id="+idCliente;
			sql+=" ORDER BY scr.audfecins ";

		}else{
			sql+="FROM vrtsolcar sc  " +
				"INNER JOIN vrmusuario u ON (u.usuario_id=sc.usuario_id) "+
				"INNER JOIN vrmcliente c ON (c.cliente_id=sc.cliente_id)  "+
				"INNER JOIN vrtsolclicre scr ON (scr.solcar_id=sc.solcar_id)  " +
				"INNER JOIN (SELECT MAX(solclicre_id) solclicre_id FROM vrtsolclicre WHERE c_estreg='A' GROUP BY c_numcontrol)maxsolclicre "+
						"ON (maxsolclicre.solclicre_id=scr.solclicre_id) "+
				"INNER JOIN vrmtipcob tc ON (tc.tipcob_id=scr.tipCob_id)  "+
				"WHERE sc.c_estreg='A'  "+
				"AND scr.n_nivapro IN("+Constantes.NIVEL_UNO+","+Constantes.NIVEL_TRES+") AND scr.c_estsol='"+Constantes.ESTADOSOL_ACTIVA+"' " +
//				"AND scr.solclicre_id NOT IN (NVL((SELECT lcc.Solclicre_Id FROM vrtlincrecli lcc WHERE lcc.solclicre_id=scr.solclicre_id ),0)  ) " +
				"AND scr.solclicre_id NOT IN (SELECT lcc.Solclicre_Id FROM vrtlincrecli lcc WHERE lcc.solclicre_id=scr.solclicre_id )  " +
				"AND scr.c_estreg ='A' ";
			if(idCliente !=null)
				sql+= " AND c.cliente_id="+idCliente;
			sql+=" ORDER BY scr.d_fecsoli ";
		}

		log.info(sql);
		List<?> result = getSession().createSQLQuery(sql).list();
		List<LineaCreditoCliente> lstResult = new ArrayList<>();
		for(int i=0; i<result.size(); i++){
			Object[] obj = (Object[])result.get(i);
			LineaCreditoCliente lineaCreditoCliente=new LineaCreditoCliente();
			Usuario usuario=new Usuario();
			Cliente cliente=new Cliente();
			SolicitudClienteCredito solicitudClienteCredito=new SolicitudClienteCredito();
			SolicitudCartera solicitudCartera = new SolicitudCartera();
			TipoCobranza tipoCobranza=new TipoCobranza();

			usuario.setId(((BigDecimal)obj[0]).intValue());
			if(obj[3]!=null) usuario.setApellidoPaterno(obj[1].toString());
			else usuario.setApellidoPaterno("");
			if(obj[2]!=null) usuario.setApellidoMaterno(obj[2].toString());
			else usuario.setApellidoMaterno("");
			usuario.setNombre(obj[3].toString());

			cliente.setId(((BigDecimal)obj[4]).longValue());
			cliente.setNumeroDocumento(obj[5].toString());
			cliente.setRazonSocial(obj[6].toString());
			cliente.setOrigen(obj[7].toString());

			solicitudCartera.setId(((BigDecimal)obj[8]).longValue());
			solicitudCartera.setFechaSolicitud(((Date)obj[9]));
			solicitudCartera.setDescuentoBaja(((BigDecimal)obj[13]).doubleValue());
			solicitudCartera.setDescuentoAlta(((BigDecimal)obj[14]).doubleValue());
			solicitudCartera.setUsuario(usuario);
			solicitudCartera.setCliente(cliente);

			solicitudClienteCredito.setEstadoSolicitud(obj[12].toString());
			if (obj[10] !=null) solicitudClienteCredito.setFechaAprobacion(((Date)obj[10]));
			if (obj[11] !=null) solicitudClienteCredito.setFechaAnulacion(((Date)obj[11]));
			if (obj[15] !=null) solicitudClienteCredito.setId(((BigDecimal)obj[15]).longValue());
			if (obj[16] !=null) solicitudClienteCredito.setLineaCreditoSolicitada(((BigDecimal)obj[16]).doubleValue());
			if (obj[17] !=null) solicitudClienteCredito.setSobregiro(((BigDecimal)obj[17]).doubleValue());
			if (obj[18] !=null) solicitudClienteCredito.setLineaCreditoAprobada(((BigDecimal)obj[18]).doubleValue());
			if (obj[19] !=null) solicitudClienteCredito.setEsComisionable(obj[19].toString());
			if (obj[20] !=null) solicitudClienteCredito.setEsCanje(obj[20].toString());
			if (obj[21] !=null) solicitudClienteCredito.setEsAmpliacion(obj[21].toString());
			if (obj[25] !=null) solicitudClienteCredito.setFechaAprobacion	((Date)obj[25]);
			if (obj[26] !=null) solicitudClienteCredito.setFechaAnulacion((Date)obj[26]);
			if (obj[28] !=null) solicitudClienteCredito.setObservaciones(obj[28].toString());
			solicitudClienteCredito.setEstadoSolicitud(obj[27].toString());

			if (obj[22] !=null) tipoCobranza.setId(((BigDecimal)obj[22]).intValue());
			if (obj[23] !=null) tipoCobranza.setDenominacion(obj[23].toString());
			solicitudClienteCredito.setTipoCobranza(tipoCobranza);
			solicitudClienteCredito.setSolicitudCartera(solicitudCartera);

			if(recu_Historia)
				solicitudClienteCredito.setNivelAprobacion(((BigDecimal)obj[29]).intValue());
			lineaCreditoCliente.setSolicitudClienteCredito(solicitudClienteCredito);

			lstResult.add(lineaCreditoCliente);
		}
		return lstResult;
	}

	/*
	 * (non-Javadoc)
	 * @see com.tepsa.sisvyr.model.dao.LineaCreditoClienteDAO#validadSolicitudAprobadaN3(java.lang.Long)
	 */
	@Override
	public Boolean validadSolicitudAprobadaN3(Long idSolicitudCartera){
		String sql="SELECT * "+
					"FROM vrtsolclicre scr "+
					"WHERE scr.Solclicre_Id="+idSolicitudCartera+" "+
					"AND scr.n_nivapro="+Constantes.NIVEL_TRES+" AND scr.c_estsol='"+Constantes.ESTADOSOL_ACTIVA+"' "+
					"AND scr.solclicre_id =NVL((SELECT lcc.Solclicre_Id FROM vrtlincrecli lcc WHERE lcc.solclicre_id=scr.solclicre_id AND lcc.c_estreg='A'),0)" +
					"AND scr.c_estreg='A' ";

		log.info(sql);
		List<?> result = getSession().createSQLQuery(sql).list();
		if(result.size()>0){ //LC aprobada por UGA
			return true;
		}else{ //Valida si la solicitud a sido devuelta o desaprobada
			sql="SELECT * "+
					"FROM vrtsolclicre scr "+
					"WHERE scr.Solclicre_Id="+idSolicitudCartera+" "+
					"AND scr.n_nivapro="+Constantes.NIVEL_TRES+" AND scr.c_estsol='"+Constantes.ESTADOSOL_ACTIVA+"' AND scr.c_estreg='A' ";

			log.info(sql);
			List<?> results = getSession().createSQLQuery(sql).list();
			if(results.size()>0)
				return false;
			else return true;

		}

	}

	/*
	 * (non-Javadoc)
	 * @see com.tepsa.sisvyr.model.dao.LineaCreditoClienteDAO#buscarSolicitudLineaCreditoN3(java.lang.String, java.lang.String, java.lang.String, java.lang.Long, java.lang.Integer, java.lang.Boolean)
	 */
	@Override
	public List<LineaCreditoCliente> buscarSolicitudLineaCreditoN3(String fechaInicio, String fechaFin, String estadoSolicitud,Long idCliente, UsuarioAprobador usuarioAprobador, Boolean recu_Historia) {
		String sql="SELECT u.usuario_id, u.c_apepat as FApePaterno,u.c_apemat as FApeMaterno, u.c_nombre as FNombre, "+ //0-3
					       "c.cliente_id, c.c_numdoc as Ruc, c.c_razsoc as RazonSocial,c.c_origen, sc.solcar_id,  "+ //4-8
					       "sc.n_descbaja as DestBaja, sc.n_descalta as DestAlta, sc.n_bashisto as BaseHistorica,  "+ //9-11
					       "scr.solclicre_id, scr.d_fecsoli as FechaSolicitud, "+ //12-13
					       "scr.c_escomision, scr.c_escanje, scr.c_esamplia,  tc.tipcob_id, tc.c_denominacion, null, "+ //14-19
					       "0 as lincrecli_id, scr.c_estsol, scr.n_lincresol as creditoSolicitadi, scr.n_sobregiro as Sobregiro, scr.n_lincreapro as CreditoAprobado, "+ //20-24
					       "scr.d_fecApro as FechaAprobacion, scr.d_fecAnul as FechaAnulaSolicitud,  scr.c_obser, scr.audusuins, scr.audfecins "; //25-29
		if(recu_Historia){
			//Obtiene las solicitudes aprobadas o desaprobadas y los demas según los parametros de busqueda.
			sql+=" ,  NVL(lco.c_estlincre,scr.c_estsol)  as SolicitudAprobadoXFinanzas " + //30 Para validar si la solicitud ya fue o no aprobada por finanazas(Solo para el caso que el UGA a ya rebuelto la solicitud a finanzas)
				"FROM vrtsolcar sc  "+
				"INNER JOIN vrmusuario u ON (u.usuario_id=sc.usuario_id) "+
				"INNER JOIN vrmcliente c ON (c.cliente_id=sc.cliente_id)  "+
				"INNER JOIN vrtsolclicre scr ON (scr.solcar_id=sc.solcar_id) "+
				"INNER JOIN vrmtipcob tc ON (tc.tipcob_id=scr.tipCob_id)  "+
				"LEFT OUTER JOIN vrtlincrecli lcc ON (lcc.solclicre_id=scr.solclicre_id) " +
				"LEFT OUTER JOIN (SELECT * FROM vrtlincrecli lc WHERE lc.c_Estreg='A' AND lc.c_estlincre='"+Constantes.ESTADOSOL_ACTIVA+"' ) "+
							"lco ON (lco.solclicre_id=scr.solclicre_id) "+
				"WHERE to_date(scr.audfecins,'dd/mm/yy') BETWEEN to_date('"+fechaInicio+"','dd/MM/yyyy') AND to_date('"+fechaFin+"','dd/MM/yyyy') " +
				"AND scr.c_estsol LIKE '"+estadoSolicitud+"%' "+
				"AND scr.n_nivapro='"+Constantes.NIVEL_TRES+"'  ";

			if(idCliente!=null)
				sql+= " AND c.cliente_id="+idCliente;
			sql+=" ORDER BY scr.audfecins desc";

		}else{
			//Obtien las solicitudes pendientes por aprobar
			sql+=", null as SolicitudAprobadoXFinanzas  "+
				"FROM vrtsolcar sc "+
				"INNER JOIN vrmusuario u ON (u.usuario_id=sc.usuario_id) "+
				"INNER JOIN vrmcliente c ON (c.cliente_id=sc.cliente_id)   "+
				"INNER JOIN vrtsolclicre scr ON (scr.solcar_id=sc.solcar_id) " +
				"INNER JOIN (SELECT MAX(solclicre_id) solclicre_id FROM vrtsolclicre WHERE c_estreg='A' GROUP BY c_numcontrol)maxsolclicre "+
						"ON (maxsolclicre.solclicre_id=scr.solclicre_id) "+
				"INNER JOIN vrmtipcob tc ON (tc.tipcob_id=scr.tipCob_id)    "+
				"WHERE  scr.n_nivapro =2 AND  scr.c_estreg='A' " +
				"AND scr.c_estsol='"+Constantes.ESTADOSOL_ACTIVA+"'  ";

				if(idCliente!=null)
					sql+= " AND c.cliente_id="+idCliente;
				sql+=" ORDER BY scr.d_fecsoli";
		}

		log.info(sql);
		List<?> result = getSession().createSQLQuery(sql).list();
		List<LineaCreditoCliente> lstResult = new ArrayList<>();
		for(int i=0; i<result.size(); i++){
			Object[] obj = (Object[])result.get(i);

			Usuario funcionario=new Usuario();
			Cliente cliente=new Cliente();
			SolicitudCartera solicitudCartera = new SolicitudCartera();
			SolicitudClienteCredito solicitudClienteCredito=new SolicitudClienteCredito();
			TipoCobranza tipoCobranza=new TipoCobranza();
			LineaCreditoCliente  lineaCreditoCliente=new LineaCreditoCliente();

			funcionario.setId(((BigDecimal)obj[0]).intValue());
			if(obj[3]!=null) funcionario.setApellidoPaterno(obj[1].toString());
			else funcionario.setApellidoPaterno("");
			if(obj[2]!=null) funcionario.setApellidoMaterno(obj[2].toString());
			else funcionario.setApellidoMaterno("");
			funcionario.setNombre(obj[3].toString());

			cliente.setId(((BigDecimal)obj[4]).longValue());
			cliente.setNumeroDocumento(obj[5].toString());
			cliente.setRazonSocial(obj[6].toString());
			cliente.setOrigen(obj[7].toString());

			solicitudCartera.setId(((BigDecimal)obj[8]).longValue());
			solicitudCartera.setDescuentoBaja(((BigDecimal)obj[9]).doubleValue());
			solicitudCartera.setDescuentoAlta(((BigDecimal)obj[10]).doubleValue());
			solicitudCartera.setBaseHistorica(((BigDecimal)obj[11]).doubleValue());
			solicitudCartera.setUsuario(funcionario);
			solicitudCartera.setCliente(cliente);

			solicitudClienteCredito.setId(((BigDecimal)obj[12]).longValue());
			solicitudClienteCredito.setFechaSolicitud((Date)obj[13]);
			solicitudClienteCredito.setEsComisionable(obj[14].toString());
			solicitudClienteCredito.setEsCanje(obj[15].toString());
			solicitudClienteCredito.setEsAmpliacion(obj[16].toString());
			solicitudClienteCredito.setSolicitudCartera(solicitudCartera);
			if(obj[27] !=null) solicitudClienteCredito.setObservaciones(obj[27].toString());
			else solicitudClienteCredito.setObservaciones("");
			if(obj[30] !=null) solicitudClienteCredito.setEstadoSolicitudAprobadoXFinanzas(obj[30].toString());

			tipoCobranza.setId(((BigDecimal)obj[17]).intValue());
			tipoCobranza.setDenominacion(obj[18].toString());

			lineaCreditoCliente.setId(((BigDecimal)obj[20]).longValue());
			lineaCreditoCliente.setEstadoLineaCredito(obj[21].toString());
			lineaCreditoCliente.setLineaCreditoSolicitada(((BigDecimal)obj[22]).doubleValue());
			lineaCreditoCliente.setSobregiro(((BigDecimal)obj[23]).doubleValue());
			lineaCreditoCliente.setLineaCreditoAprobada(((BigDecimal)obj[24]).doubleValue());
			if(obj[25] !=null) solicitudClienteCredito.setFechaAprobacion((Date)obj[25]);
			if(obj[26] !=null) solicitudClienteCredito.setFechaAnulacion((Date)obj[26]);
			if(obj[28] !=null) lineaCreditoCliente.setUsuarioInsercion(obj[28].toString());
			else lineaCreditoCliente.setUsuarioInsercion("");


			lineaCreditoCliente.setTipoCobranza(tipoCobranza);
			lineaCreditoCliente.setSolicitudClienteCredito(solicitudClienteCredito);

			lstResult.add(lineaCreditoCliente);
		}
		return lstResult;
	}

	/*
	 * (non-Javadoc)
	 * @see com.tepsa.sisvyr.model.dao.LineaCreditoClienteDAO#saldo(java.lang.Double, java.lang.Long)
	 */
	@Override
	public Double saldo(Double montoAprobado, Long idCliente){
//		String sql="SELECT "+
//					  "(SELECT SUM(v.n_imppag) "+ //SUMA DE VOUCHERS
//					   "FROM vrtvenpas v "+
//					   "INNER JOIN (SELECT max(vp.venpas_id) as venpas_id, c_numcontrol FROM vrtvenpas vp "+
//					              "WHERE vp.cliente_id="+idCliente+" GROUP BY vp.c_numcontrol) vpp  "+
//					        "ON (vpp.venpas_id=v.venpas_id) "+
//					   "WHERE v.cliente_id="+idCliente+" AND v.tipcom_id="+Constantes.ID_TIPCOM_VOUCHER_CORPORATIVO+" " +
//					   "AND v.tipmov_id not in ("+Constantes.ID_TIPMOV_ANULACION_SISTEMA+","+Constantes.ID_TIPMOV_ANULACION+","+Constantes.ID_TIPMOV_DEVOLUCION+") "+
//					   "AND v.c_estreg='A' ) vouchers, "+
//
//					  "(SELECT sum(v.n_imppag) "+ //SUMA DE BOLETOS
//					  "FROM vrtvenpas v "+
//					  "INNER JOIN (SELECT max(vp.venpas_id) as venpas_id, c_numcontrol FROM vrtvenpas vp "+
//					              "WHERE vp.cliente_id="+idCliente+" GROUP BY vp.c_numcontrol) vpp  "+
//					        "ON (vpp.venpas_id=v.venpas_id) "+
//					  "WHERE v.cliente_id="+idCliente+" AND v.tipcom_id="+Constantes.ID_TIPCOM_BOLETO_VIAJE+" " +
//					  "AND v.tipmov_id not in ("+Constantes.ID_TIPMOV_ANULACION_SISTEMA+","+Constantes.ID_TIPMOV_ANULACION+","+Constantes.ID_TIPMOV_DEVOLUCION+") "+
//					  "AND v.tipforpag_id="+Constantes.ID_TIPFORPAG_CREDITO+" AND v.c_estreg='A' ) Boletos, "+
//
//					  "(SELECT SUM(bc.n_imppag) "+ //SUMA LOS BOLETOS CANCELADOS
//					  "FROM vrtvenpas v "+
//					  "INNER JOIN  vrtbolcan bc ON (bc.venpas_id=v.venpas_id) "+
//					  "WHERE v.cliente_id="+idCliente+" AND v.tipcom_id="+Constantes.ID_TIPCOM_BOLETO_VIAJE+" " +
//					  "AND v.tipmov_id not in ("+Constantes.ID_TIPMOV_ANULACION_SISTEMA+","+Constantes.ID_TIPMOV_ANULACION+","+Constantes.ID_TIPMOV_DEVOLUCION+") "+
//					  "AND v.c_estreg='A') BoletosCancelados "+
//				  "FROM DUAL";

//		log.info(sql);
//		List<?> result = getSession().createSQLQuery(sql).list();
//		Object[] obj = (Object[])result.get(0);

//		Double vouchers=.00;
//		Double boletos=.00;
//		Double boletosCancelados=.00;
//
//		if(obj[0]!=null) vouchers=((BigDecimal)(obj[0])).doubleValue();
//		if(obj[1]!=null) boletos=((BigDecimal)(obj[1])).doubleValue();
//		if(obj[2]!=null) boletosCancelados=((BigDecimal)(obj[2])).doubleValue();
//
//		Double saldo= montoAprobado-(vouchers+boletos)+boletosCancelados;

		String sql="SELECT lc.n_lincreapro, lc.n_saldo "+
					"FROM vrtlincrecli lc "+
					    "INNER JOIN VRTCARCLI  cl ON (cl.carcli_id=lc.carcli_id) "+
					"WHERE cl.cliente_id="+idCliente+" "+
					   "AND lc.c_estlincre='ACT' "+
					   "AND cl.c_estreg='"+Constantes.VALUE_ACTIVO+"' ";
		log.info(sql);
		List<?> result = getSession().createSQLQuery(sql).list();
		Object[] obj = (Object[])result.get(0);

		Double lineaAprobadaAnterior=((BigDecimal)obj[0]).doubleValue();
		Double saldoAnterior=((BigDecimal)obj[1]).doubleValue();

		double nuevoSaldo=montoAprobado-lineaAprobadaAnterior+saldoAnterior;

		return nuevoSaldo;
	}

	/*
	 * (non-Javadoc)
	 * @see com.tepsa.sisvyr.model.dao.LineaCreditoClienteDAO#lineaCreditoCliente(java.lang.Long)
	 */
	@Override
	public LineaCreditoCliente lineaCreditoCliente(Long idCliente){
		String sql ="SELECT lc.lincrecli_id, lc.carcli_id,lc.solclicre_id, lc.n_lincreapro, lc.n_sobregiro, "+
					       "lc.n_saldo, lc.d_fecacti, lc.d_fecsus, "+
					       "fn.usuario_id, fn.c_login, fn.c_apepat, fn.c_apemat,fn.c_nombre, lc.c_escomision,cc.n_bashisto "+
					       ",lc.c_escanje "+
					"FROM vrtlincrecli lc "+
						"INNER JOIN vrtcarcli cc ON (cc.carcli_id=lc.carcli_id) "+
						"INNER JOIN vrmusuario fn ON (fn.usuario_id=cc.usuario_id) "+
					"WHERE cc.c_estcar='"+Constantes.ESTADOSOL_ACTIVA+"' AND lc.c_estreg='A' AND lc.c_estlincre='"+Constantes.ESTADOSOL_ACTIVA+"' "+
					"AND cc.cliente_id="+idCliente;
		List<?> result = getSession().createSQLQuery(sql).list();

		LineaCreditoCliente lineaCreditoCliente=null;
		if (result.size()>0){
			Object[] obj = (Object[])result.get(0);

			lineaCreditoCliente=new LineaCreditoCliente();

			Usuario funcionario=new Usuario();
			funcionario.setId(((BigDecimal)obj[8]).intValue());
			funcionario.setLogin(obj[9].toString());
			funcionario.setApellidoPaterno(obj[10].toString());
			funcionario.setApellidoMaterno(obj[11]!=null?obj[11].toString():"");
			funcionario.setNombre(obj[12].toString());

			CarteraCliente  carteraCliente=new CarteraCliente();
			carteraCliente.setId(((BigDecimal)obj[1]).longValue());
			carteraCliente.setUsuario(funcionario);
			carteraCliente.setBaseHistorica(((BigDecimal)obj[14]).doubleValue());

			SolicitudClienteCredito solicitudClienteCredito=new SolicitudClienteCredito();
			solicitudClienteCredito.setId(((BigDecimal)obj[2]).longValue());

			lineaCreditoCliente.setId(((BigDecimal)obj[0]).longValue());
			lineaCreditoCliente.setLineaCreditoAprobada(((BigDecimal)obj[3]).doubleValue());
			lineaCreditoCliente.setSobregiro(((BigDecimal)obj[4]).doubleValue());
			lineaCreditoCliente.setSaldo(((BigDecimal)obj[5]).doubleValue());
			lineaCreditoCliente.setCarteraCliente(carteraCliente);
			lineaCreditoCliente.setSolicitudClienteCredito(solicitudClienteCredito);
			lineaCreditoCliente.setFechaActivacion((Date)obj[6]);
			lineaCreditoCliente.setFechaSuspension(obj[7]==null?null:(Date)obj[7]);
			lineaCreditoCliente.setEsComisionable(obj[13].toString());
			lineaCreditoCliente.setEsCanje(obj[15]!=null?obj[15].toString():null);
		}
		return lineaCreditoCliente;
	}

	/*
	 * (non-Javadoc)
	 * @see com.tepsa.sisvyr.model.dao.LineaCreditoClienteDAO#clientesCredito(java.lang.Long, java.lang.Integer, java.lang.String)
	 */
	@Override
	public List<LineaCreditoCliente> clientesCredito(Long idCliente, Integer idFuncionario, String tipoCliente){
		String sql="SELECT * FROM ( "
					  + "SELECT lc.lincrecli_id, lc.carcli_id,lc.solclicre_id,c.cliente_id,u.usuario_id, "+ //0-4
						       "c.c_numdoc, c.c_razsoc,lc.n_lincreapro, lc.n_sobregiro,lc.n_saldo, lc.c_estlincre, "+ //5-10
						       "u.c_apepat, u.c_apemat, u.c_nombre, "+ //11-13
						       "NVL( "+
						           "(SELECT ta.c_denominacion FROM vrmconcesionario cns "+
						           "INNER JOIN vrmagencia a ON (a.concesionario_id=cns.concesionario_id) "+
						           "INNER JOIN vrmtipage ta ON (ta.tipage_id=a.tipage_id) "+
						           "WHERE CNS.C_RUC=c.c_numdoc AND cns.c_estreg='A' AND a.c_estreg='A' " +
						           "GROUP BY ta.c_denominacion "+
						           "), "+
						       "CASE WHEN lc.c_escanje='"+Constantes.SI+"' THEN 'CANJE PUBLICITARIO' ELSE 'CRÉDITO' END) AS tipoCliente " + //14-14
						"FROM vrtlincrecli lc "+
						"INNER JOIN vrtcarcli  cc ON (cc.carcli_id=lc.carcli_id) "+
						"INNER JOIN vrmcliente c ON (c.cliente_id=cc.cliente_id) "+
						"LEFT OUTER JOIN vrmusuario u ON (u.usuario_id=cc.usuario_id)  " +
						"INNER JOIN (SELECT scr.solclicre_id FROM vrtsolclicre scr "+
							           "WHERE scr.solclicre_id in (SELECT scr.solclicre_id FROM vrtsolclicre scr GROUP BY scr.solclicre_id) "+
							           "AND scr.n_nivapro="+Constantes.NIVEL_TRES+")scr ON (scr.solclicre_id=lc.solclicre_id) "+
						"WHERE  lc.c_estreg='A' AND cc.c_estcar='"+Constantes.ESTADOSOL_ACTIVA+"' "+
					       "AND c.cliente_id=NVL("+idCliente+",c.cliente_id) "+
					       "AND u.usuario_id=NVL("+idFuncionario+",u.usuario_id) "+
			      ")rpt ";
		if(tipoCliente!=null)
			sql+=" WHERE rpt.tipoCliente='"+tipoCliente+"' ";
		sql+=" ORDER BY rpt.c_razsoc ";
		log.info(sql);


		List<?> result = getSession().createSQLQuery(sql).list();
		List<LineaCreditoCliente> listResult= new ArrayList<>();

		for(int i=0; i<result.size(); i++){
			Object[] obj = (Object[])result.get(i);

			LineaCreditoCliente lineaCreditoCliente=new LineaCreditoCliente();
			CarteraCliente  carteraCliente=new CarteraCliente();
			SolicitudClienteCredito solicitudClienteCredito=new SolicitudClienteCredito();
			Cliente cliente=new Cliente();
			Usuario funcionario = new Usuario();

			cliente.setId(((BigDecimal)obj[3]).longValue());
			cliente.setNumeroDocumento(obj[5].toString());
			cliente.setRazonSocial(obj[6].toString());
			if(obj[14]!=null) cliente.setTipo(obj[14].toString());

			funcionario.setId(((BigDecimal)obj[4]).intValue());
			funcionario.setApellidoPaterno(obj[11].toString());
			if(obj[12]!=null) funcionario.setApellidoMaterno(obj[12].toString());
			else funcionario.setApellidoMaterno("");
			funcionario.setNombre(obj[13].toString());

			carteraCliente.setId(((BigDecimal)obj[1]).longValue());
			carteraCliente.setCliente(cliente);
			carteraCliente.setUsuario(funcionario);

			solicitudClienteCredito.setId(((BigDecimal)obj[2]).longValue());

			lineaCreditoCliente.setId(((BigDecimal)obj[0]).longValue());
			lineaCreditoCliente.setLineaCreditoAprobada(((BigDecimal)obj[7]).doubleValue());
			lineaCreditoCliente.setSobregiro(((BigDecimal)obj[8]).doubleValue());
			lineaCreditoCliente.setSaldo(((BigDecimal)obj[9]).doubleValue());
			lineaCreditoCliente.setEstadoLineaCredito(obj[10].toString());
			lineaCreditoCliente.setSolicitudClienteCredito(solicitudClienteCredito);
			lineaCreditoCliente.setCarteraCliente(carteraCliente);
			if(obj[14]!=null) lineaCreditoCliente.setTipoCliente(obj[14].toString());

			listResult.add(lineaCreditoCliente);
		}

		return listResult;
	}

	/*
	 * (non-Javadoc)
	 * @see com.tepsa.sisvyr.model.dao.LineaCreditoClienteDAO#validacionCreditoCliente(java.lang.Long)
	 */
	@Override
	public LineaCreditoCliente validacionCreditoCliente(Long idCliente) throws Exception{
		String sql ="SELECT lcc.lincrecli_id "+
					"FROM vrtlincrecli lcc  "+
					"INNER JOIN vrtcarcli cc ON (cc.carcli_id=lcc.carcli_id)  "+
					"WHERE cc.cliente_id="+idCliente+" AND lcc.c_estlincre='"+Constantes.ESTADOSOL_ACTIVA+"'  AND lcc.c_estReg='"+Constantes.VALUE_ACTIVO+"' "
							+ "AND cc.c_estcar='"+Constantes.ESTADOSOL_ACTIVA+"' AND cc.c_estreg='"+Constantes.VALUE_ACTIVO+"' ";

		log.info(sql);

		LineaCreditoCliente lineaCreditoCliente=null;
		try{
			List<?> result = getSession().createSQLQuery(sql).list();
			if(result.size()>0){
				String id= result.get(0).toString();
				lineaCreditoCliente= ServiceLocator.getLineaCreditoClienteManager().buscarPorId(Long.valueOf(id));
			}
//			else
//				throw new LineaCreditoClienteException(LineaCreditoClienteException.LINEA_CREDITO_NO_ACTIVO);
//		}catch(LineaCreditoClienteException lcex){
//			throw new LineaCreditoClienteException(lcex.getTipo());
		}catch(Exception ex){
			throw new Exception(ex);
		}

		return lineaCreditoCliente;
	}

	/*
	 * (non-Javadoc)
	 * @see com.tepsa.sisvyr.model.dao.LineaCreditoClienteDAO#restarSaldo(java.lang.Double, java.lang.Double, java.lang.Long)
	 */
	@Override
	public void restarSaldo(Double SaldoActual, Double monto, Long idLineaCreditoCliente){
		String sql="UPDATE vrtlincrecli SET n_Saldo="+(SaldoActual-monto)+" WHERE lincrecli_id="+idLineaCreditoCliente;

		log.info(sql);
		getSession().createSQLQuery(sql).executeUpdate();
	}

	/*
	 * (non-Javadoc)
	 * @see com.tepsa.sisvyr.model.dao.LineaCreditoClienteDAO#saldobyReduccion(java.lang.Double, java.lang.String)
	 */
	@Override
	public Double saldobyReduccion(Double nuevaLineaCredito, String rucclienteCredito) {
		// TODO Auto-generated method stub

		//--> Querys Desarrollados por Marco Oscco.
		//VENTA
		String sql="SELECT NVL(SUM(B.IMP), 0) vVentas "+
					"FROM( "+ //--> Boletos reimpresos credito
					     "SELECT SUM(vp.n_imppag) IMP  "+
					     "FROM vrtvenpas vp  "+
					     "WHERE vp.tipcom_id in ("+Constantes.ID_TIPCOM_BOLETO_VIAJE+","+Constantes.ID_TIPCOM_BOLETA_VENTA+","+Constantes.ID_TIPCOM_FACTURA+") "+
					       "AND vp.forpag_id="+Constantes.ID_FORPAG_CREDITO+" "+
					       "AND vp.tipmov_id="+Constantes.ID_TIPMOV_CREDITO+" "+
					       "AND vp.tipforpag_id<>"+Constantes.ID_TIPFORPAG_CANJE_PUBLICITARIO+" "+
					       "AND vp.c_estdoc='ACT'  "+
					       "AND vp.d_fecliq> to_date('31/12/2013','dd/MM/yyyy') "+
					       "AND vp.c_rucclicre='"+rucclienteCredito+"' "+
					       "AND vp.c_estreg='"+Constantes.VALUE_ACTIVO+"' "+
					  "UNION ALL "+
					      //-->Vouches pendientes por reimprimir creditos
					      "SELECT SUM(vp.n_imppag) IMP "+
					      "FROM vrtvenpas vp  "+
					      "WHERE vp.tipcom_id IN ("+Constantes.ID_TIPMOV_ANULACION_SISTEMA+","+Constantes.ID_TIPMOV_DEVOLUCION+","+Constantes.ID_TIPMOV_ANULACION+") "+
					        "AND vp.forpag_id="+Constantes.ID_FORPAG_CREDITO+ " "+
					        "AND vp.tipmov_id="+Constantes.ID_TIPMOV_CREDITO+ " "+
					        "AND vp.tipforpag_id<>"+Constantes.ID_TIPFORPAG_CANJE_PUBLICITARIO+ " "+
					        "AND vp.venpas_idref IS NULL "+
					        "AND vp.d_fecliq> to_date('31/12/2013','dd/MM/yyyy') "+
					        "AND vp.c_rucclicre='"+rucclienteCredito+"' "+
					        "AND vp.c_estreg='"+Constantes.VALUE_ACTIVO+"' "+
					  ") B";
		log.info(sql);
		//VENTA
		Double venta=.00;
		Object objVenta=getSession().createSQLQuery(sql).uniqueResult();
		if(objVenta!=null)
			venta=((BigDecimal)objVenta).doubleValue();

		//A CUENTA
		//Busca los abonos que el cliente haya echo
		sql="SELECT NVL(SUM(bc.n_imppag), 0) vAcuenta FROM vrtbolcan bc WHERE bc.c_rucclicre="+rucclienteCredito+" AND bc.c_estdoc='ACT' AND bc.c_estreg='"+Constantes.VALUE_ACTIVO+"' ";
		log.info(sql);
		Double acuenta=.00;
		Object objAcuenta=getSession().createSQLQuery(sql).uniqueResult();
		if(objAcuenta!=null)
			acuenta=((BigDecimal)objAcuenta).doubleValue();

		//Aplica formula para calcular el nuevo saldo
		double nuevoSaldo=nuevaLineaCredito-venta+acuenta;

		return nuevoSaldo;
	}

	/*
	 * (non-Javadoc)
	 * @see com.tepsa.sisvyr.model.dao.LineaCreditoClienteDAO#actualizarSaldo(java.lang.Double, java.lang.String, com.tepsa.sisvyr.model.bean.Usuario, java.lang.String, java.lang.Boolean)
	 */
	@Override
	public void actualizarSaldo(Double motoActualizar, String rucClienteCredito,Usuario usuario, String ip,Boolean aFavor) throws Exception {
		/*Recupera datos basicos para actualizar la LC*/
		String sql="SELECT lcc.lincrecli_id, lcc.n_saldo "
				 + "FROM VRTLINCRECLI lcc "
				 	+ "INNER JOIN VRTCARCLI cc ON (cc.carcli_id=lcc.carcli_id) "
				 	+ "INNER JOIN VRMCLIENTE c ON (c.cliente_id=cc.cliente_id) "
				 + "WHERE c.c_numdoc='"+rucClienteCredito+"' "
				 + "AND lcc.c_Estreg='"+Constantes.VALUE_ACTIVO+"' AND c.c_Estreg='"+Constantes.VALUE_ACTIVO+"' AND cc.c_estreg='"+Constantes.VALUE_ACTIVO+"' AND lcc.c_estlincre='ACT'";
		log.info(sql);
		List<?>result=getSession().createSQLQuery(sql).list();
		if(result.size()>0){
			Object[] obj=(Object[])result.get(0);
			Long lineaCreditoId=((BigDecimal)obj[0]).longValue();
			Double saldoActual=((BigDecimal)obj[1]).doubleValue();

			double nuevoSaldo=.00;
			if(aFavor)
				nuevoSaldo=saldoActual+motoActualizar;
			else
				nuevoSaldo=saldoActual-motoActualizar;

			/*Actualizando el Saldo*/
			sql="UPDATE VRTLINCRECLI SET n_saldo="+nuevoSaldo+" WHERE lincrecli_id="+lineaCreditoId;
			getSession().createSQLQuery(sql).executeUpdate();
		}

	}

}

/*
 *
 *
 *
*/