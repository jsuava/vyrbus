package pe.itsb.vyrbus.model.dao.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.TreeMap;

import pe.itsb.vyrbus.model.bean.Cliente;
import pe.itsb.vyrbus.model.bean.SolicitudCartera;
import pe.itsb.vyrbus.model.bean.SolicitudClienteCredito;
import pe.itsb.vyrbus.model.bean.TipoCobranza;
import pe.itsb.vyrbus.model.bean.Usuario;
import pe.itsb.vyrbus.model.bean.UsuarioAprobador;
import pe.itsb.vyrbus.model.dao.SolicitudCarteraDAO;
import pe.itsb.vyrbus.service.util.Constantes;
import pe.itsb.vyrbus.service.util.MyTime;

/**
 *
 * @author JABANTO
 *
 */
public class SolicitudCarteraDAOImpl extends GenericDAOImpl implements SolicitudCarteraDAO {

	/*
	 * (non-Javadoc)
	 * @see com.tepsa.sisvyr.model.dao.SolicitudAsignarClienteCarteraFuncionarioDAO#baseHistoricaCliente(java.lang.Long)
	 */
	@Override
	public Double baseHistoricaCliente(Long idCliente) throws Exception {
		Long fechaActual=Constantes.FORMAT_DATE.parse(new MyTime().dateServer()).getTime();
		long lNumMeses=Constantes.MILISEGUNDOS_X_DIA*Constantes.CALCULO_BASE_HISTORICA;
		Long fechaResult=fechaActual-lNumMeses;
		Date fecha = new Date(fechaResult);

		int iNumMeses=(Constantes.CALCULO_BASE_HISTORICA/30);

		//Formula para la base Historica: Promedio de Ventas en los ultimos 6 Meses mas el Mes con mayor ventas.
		//**PROMEDIO DE LOS ULTIMOS 6 MESES + EL MES DE MAYOR VENTAS.
//		String sql="SELECT CAST((SUM(BH.BASEHIST)/ SUM(BH.CANTIDAD))+MAX(BH.BASEHIST)AS NUMERIC(18,2)) AS BH " +
//		String sql="SELECT CAST((SUM(BH.BASEHIST)/"+iNumMeses+" )+MAX(BH.BASEHIST)AS NUMERIC(18,2)) AS BH " +
		String sql="SELECT CAST((SUM(BH.BASEHIST)/"+iNumMeses+" ) AS NUMERIC(18,2)) AS BH " +
				   "FROM( " +
						"SELECT CAST( (sum(v.n_imppag)) AS NUMERIC(18,2)) AS BASEHIST "+ //, COUNT(*)AS CANTIDAD "+
						"FROM vrtvenpas v "+
						"INNER JOIN (SELECT MAX(venpas_id)venpas_id, c_numcontrol "+
						            "FROM vrtvenpas WHERE cliente_id="+idCliente+" AND d_fecliq >=to_date('"+Constantes.FORMAT_DATE.format(fecha)+"','dd/MM/yyyy') AND c_tiptra="+Constantes.TIPO_OPERACION_VENTA+" "+
						            "GROUP BY c_numcontrol) max_venta "+
										"ON (max_venta.venpas_id=v.venpas_id) "+
						"WHERE v.forpag_id !="+Constantes.ID_FORPAG_CORTESIA+" "+
						"AND v.tipcom_id in ("+Constantes.ID_TIPCOM_BOLETO_VIAJE+","+Constantes.ID_TIPCOM_BOLETA_VENTA+","+Constantes.ID_TIPCOM_FACTURA+") "+
						"AND v.tipmov_id NOT IN ("+Constantes.ID_TIPMOV_ANULACION+","+Constantes.ID_TIPMOV_ANULACION_SISTEMA+","+Constantes.ID_TIPMOV_DEVOLUCION+") "+
//						"AND v.tipforpag_id IN ("+Constantes.ID_TIPFORPAG_EFECTIVO+","+Constantes.ID_TIPFORPAG_CANJE_PUBLICITARIO+","+Constantes.ID_TIPFORPAG_CREDITO+","+Constantes.ID_TIPFORPAG_TARJETA+") "+
						"AND v.c_estreg='A' " +
						"GROUP BY to_char(v.d_fecliq,'MM') " +
					")BH";

		log.info(sql);

		List<?> result = getSession().createSQLQuery(sql).list();

		if(result.get(0)!=null)
			return ((BigDecimal) result.get(0)).doubleValue();
		else
			return .00;
	}

	/*
	 * (non-Javadoc)
	 * @see com.tepsa.sisvyr.model.dao.SolicitudCarteraDAO#buscarPorX(java.util.TreeMap, java.util.List)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public ArrayList<SolicitudCartera> buscarPorX( TreeMap<String, Object> criteriosBusqueda, List<String> criteriosOrdenar) {
		return (ArrayList<SolicitudCartera>) super.findByX(SolicitudCartera.class, criteriosBusqueda, criteriosOrdenar);
	}

	/*
	 * (non-Javadoc)
	 * @see com.tepsa.sisvyr.model.dao.SolicitudCarteraDAO#buscarPorId(java.lang.Long)
	 */
	@Override
	public SolicitudCartera buscarPorId(Long id) {
		return (SolicitudCartera) super.findById(SolicitudCartera.class, id);
	}

	/*
	 * (non-Javadoc)
	 * @see com.tepsa.sisvyr.model.dao.SolicitudCarteraDAO#guardar(com.tepsa.sisvyr.model.bean.SolicitudCartera)
	 */
	@Override
	public void guardar(SolicitudCartera solicitudCartera) throws Exception {
		super.save(solicitudCartera);
	}

	/*
	 * (non-Javadoc)
	 * @see com.tepsa.sisvyr.model.dao.SolicitudCarteraDAO#AprobarSolicitud(java.lang.Long, com.tepsa.sisvyr.model.bean.UsuarioAprobador)
	 */
	@Override
	public void aprobarSolicitud(Long idSolicitudCartera,UsuarioAprobador usuarioAprobador) throws Exception {
		String sql="UPDATE vrtsolcar SET c_estsolicitud='"+Constantes.ESTADOSOL_INACTIVA+"', "+
					                     "D_FECAPRO='"+Constantes.FORMAT_DATE.parse((new MyTime().dateServer())).toString()+"', "+
					                     "USUAPRO_ID="+usuarioAprobador.getId()+", "+
					                     "N_NIVAPRO="+usuarioAprobador.getNivelAprobacion()+" "+
					"WHERE SOLCAR_ID="+idSolicitudCartera+"  ";
		log.info(sql);
		getSession().createSQLQuery(sql).executeUpdate();
	}

	/*
	 * (non-Javadoc)
	 * @see com.tepsa.sisvyr.model.dao.SolicitudCarteraDAO#actualizarNivelAprobacion(java.lang.Integer)
	 */
	@Override
	public void anulaSolicitud(Integer idSolicitudCartera, UsuarioAprobador usuarioAprobador)  throws Exception {
		String sql="UPDATE vrtsolcar SET c_estsolicitud='"+Constantes.ESTADOSOL_ANULADA+"', "+
					                     "D_FECANUL='"+Constantes.FORMAT_DATE.parse((new MyTime().dateServer())).toString()+"', "+
					                     "USUAPRO_ID="+usuarioAprobador.getId()+", "+
					                     "N_NIVAPRO="+usuarioAprobador.getNivelAprobacion()+" "+
                     "WHERE SOLCAR_ID="+idSolicitudCartera+" ";
		log.info(sql);
		getSession().createSQLQuery(sql).executeUpdate();
	}

	/*
	 * (non-Javadoc)
	 * @see com.tepsa.sisvyr.model.dao.SolicitudCarteraDAO#actualizar(com.tepsa.sisvyr.model.bean.SolicitudCartera)
	 */
	@Override
	public void actualizar(SolicitudCartera solicitudCartera) throws Exception {
		super.update(solicitudCartera);

	}

	/*
	 * (non-Javadoc)
	 * @see com.tepsa.sisvyr.model.dao.SolicitudCarteraDAO#BuscarSolicitudes(java.lang.String, java.lang.String, java.lang.String, java.lang.Long)
	 */
	@Override
	public List<SolicitudCartera> BuscarSolicitudes(String fechaInicio, String fechaFin, String estadoSolicitud, Long idFuncionario,UsuarioAprobador usuarioAprobador,Long idCliente, Boolean recu_Historia){
		String sql="SELECT u.usuario_id, u.c_apepat as FApePaterno,u.c_apemat as FApeMaterno, u.c_nombre as FNombre, c.cliente_id, "+ //0-4
					       "c.c_numdoc as Ruc, c.c_razsoc as RazonSocial,c.c_origen, sc.solcar_id, scrr.d_fecsoli as FechaSolicitud, "+  //5-9
					       "scr.d_fecapro as FechaAprobacion, scr.d_fecanul as FechaAnulacion, sc.c_estsolicitud, "+ //10-12
					       "sc.n_descbaja as DestBaja, sc.n_descalta as DestAlta, scr.solclicre_id, scr.n_lincresol as creditoSolicitadi, "+  //13-16
					       "scr.n_sobregiro as Sobregiro, scr.n_lincreapro as CreditoAprobado, scr.c_escomision, scr.c_escanje, scr.c_esamplia, "+  //17-21
					       "tc.tipcob_id, tc.c_denominacion,scrr.n_nivapro as nivelUlmaAprobacion  "+ //22-24
					"FROM vrtsolcar sc  "+
					"INNER JOIN vrmusuario u ON (u.usuario_id=sc.usuario_id) "+
					"INNER JOIN vrmcliente c ON (c.cliente_id=sc.cliente_id)   "+
					"LEFT OUTER JOIN (SELECT * FROM vrtsolclicre   "+
					                 "WHERE solclicre_id in (SELECT max(scr.solclicre_id)FROM vrtsolclicre scr "+
					                                         "WHERE to_date(scr.d_fecapro,'dd/mm/yyy') BETWEEN to_date('"+fechaInicio+"','dd/MM/yyyy') AND to_date('"+fechaFin+"','dd/MM/yyyy') "+
					                                         "AND scr.n_nivapro="+Constantes.NIVEL_UNO+" "+
					                                         "GROUP BY scr.c_numcontrol "+
					                                        ")  "+
					          ")scr ON (scr.solcar_id=sc.solcar_id) "+
					"INNER JOIN (SELECT * FROM vrtsolclicre   "+
					          "WHERE solclicre_id in (SELECT max(scr.solclicre_id)FROM vrtsolclicre scr "+
					                                 "WHERE to_date(scr.d_fecapro,'dd/mm/yyy') BETWEEN to_date('"+fechaInicio+"','dd/MM/yyyy') AND to_date('"+fechaFin+"','dd/MM/yyyy') "+
					                                 "GROUP BY scr.c_numcontrol )   "+
					          ") scrr ON (scrr.c_numcontrol=scr.c_numcontrol)   "+
					"LEFT OUTER JOIN vrmtipcob tc ON (tc.tipcob_id=scrr.tipCob_id)   "+
					"WHERE to_date(sc.d_fecapro,'dd/mm/yyy') BETWEEN to_date('"+fechaInicio+"','dd/MM/yyyy') AND to_date('"+fechaFin+"','dd/MM/yyyy') "+
					"AND sc.c_estsolicitud LIKE '"+estadoSolicitud+"%' AND sc.c_estreg='A'";
//		if(recu_Historia==true){
//			sql+="	AND scr.n_nivapro="+Constantes.NIVEL_UNO;
//		}

		if(idFuncionario!=null)
			sql+=" AND sc.usuario_id="+idFuncionario;
		if(idCliente!=null)
			sql+=" AND sc.cliente_id="+idCliente;
//		if(idUsuarioAprobador!=null)
//			sql+=" AND sc.usuapro_id="+idUsuarioAprobador;
//


		log.info(sql);
		List<?> result = getSession().createSQLQuery(sql).list();
		List<SolicitudCartera> lstResult = new ArrayList<>();
		for(int i=0; i<result.size(); i++){
			Object[] obj = (Object[])result.get(i);

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
			if (obj[10] !=null) solicitudCartera.setFechaAprobacion(((Date)obj[10]));
			if (obj[11] !=null) solicitudCartera.setFechaAnulacion(((Date)obj[11]));
			solicitudCartera.setEstadoSolicitud(obj[12].toString());
			solicitudCartera.setDescuentoBaja(((BigDecimal)obj[13]).doubleValue());
			solicitudCartera.setDescuentoAlta(((BigDecimal)obj[14]).doubleValue());

			if (obj[15] !=null) solicitudClienteCredito.setId(((BigDecimal)obj[15]).longValue());
			if (obj[16] !=null) solicitudClienteCredito.setLineaCreditoSolicitada(((BigDecimal)obj[16]).doubleValue());
			if (obj[17] !=null) solicitudClienteCredito.setSobregiro(((BigDecimal)obj[17]).doubleValue());
			if (obj[18] !=null) solicitudClienteCredito.setLineaCreditoAprobada(((BigDecimal)obj[18]).doubleValue());
			if (obj[19] !=null) solicitudClienteCredito.setEsComisionable(obj[19].toString());
			if (obj[20] !=null) solicitudClienteCredito.setEsCanje(obj[20].toString());
			if (obj[21] !=null) solicitudClienteCredito.setEsAmpliacion(obj[21].toString());

			if (obj[22] !=null) tipoCobranza.setId(((BigDecimal)obj[22]).intValue());
			if (obj[23] !=null) tipoCobranza.setDenominacion(obj[23].toString());
			solicitudClienteCredito.setTipoCobranza(tipoCobranza);
			solicitudClienteCredito.setNivelAprobacion(((BigDecimal)obj[24]).intValue());

			solicitudCartera.setUsuario(usuario);
			solicitudCartera.setCliente(cliente);
			solicitudCartera.setSolicitudClienteCredito(solicitudClienteCredito);

			lstResult.add(solicitudCartera);
		}
		return lstResult;
	}
}
