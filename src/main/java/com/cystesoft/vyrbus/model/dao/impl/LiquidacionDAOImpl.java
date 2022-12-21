package com.cystesoft.vyrbus.model.dao.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import com.cystesoft.vyrbus.model.bean.Agencia;
import com.cystesoft.vyrbus.model.bean.Liquidacion;
import com.cystesoft.vyrbus.model.bean.TipoComprobante;
import com.cystesoft.vyrbus.model.bean.Usuario;
import com.cystesoft.vyrbus.model.dao.LiquidacionDAO;
import com.cystesoft.vyrbus.service.mappers.ResumenComprobante;
import com.cystesoft.vyrbus.service.util.Constantes;
import com.cystesoft.vyrbus.view.tuentrada.LiquidacionTuentrada;

/**
 *
 * @author José Abanto
 *
 */
@SuppressWarnings("unchecked")
public  class LiquidacionDAOImpl extends GenericDAOImpl implements LiquidacionDAO {


	/*Apertura Liquidacion*/
	@Override
	public void aperturarLiquidacion(Liquidacion liquidacion) {
		super.save(liquidacion);
	}

	@Override
	public ArrayList<Liquidacion> buscarPorX(TreeMap<String, Object> criteriosBusqueda,List<String> criteriosOrdenar) {
		return (ArrayList<Liquidacion>) super.findByX(Liquidacion.class, criteriosBusqueda, criteriosOrdenar);
	}

	@Override
	public List<Liquidacion> buscarLiquidacion(String fechaInicial,String FechaFinal, Integer idAgencia, Integer idUsuario, Integer estadoLiquidacion) throws Exception {
		String criterios="";
		String criteriosUsuario="";
		if(estadoLiquidacion!=null){
			if (estadoLiquidacion.equals(Constantes.LIQUI_ESTA_ABIERTO) || estadoLiquidacion.equals(Constantes.LIQUI_ESTA_CERRADO) )
				criterios=" AND l.n_estliq="+estadoLiquidacion;
		}

		if (idUsuario!=null)
			criteriosUsuario=" AND l.usuario_id="+idUsuario;

		String sql ="SELECT l.liquidacion_id, l.n_anio, l.agencia_id, l.usuario_id, l.n_estliq, l.c_nomusu as NombreUsuario, "+ //0-5
						"a.c_nomcor as NombreAgencia, "+ //6-6
						"u.c_apepat as ApPaterno, u.c_apemat as ApMarterno, u.c_nombre as Nombres, "+//7-9
						"l.d_fecliq as FechaLiquidacion, l.audfecmod as FechaMod, "+//10-11
						"l.audfecins as FechaInsercion, l.audipinse as ipinsercion, l.audusuins as susuarioInsercion, "+ //12-14
						"l.n_moning, l.n_moningdol, a.c_codigo "+ //15-17
				"FROM vrtliquidacion l " +
					"INNER JOIN vrmagencia a ON (a.agencia_id=l.agencia_id) "+
					"INNER JOIN vrmusuario u ON (u.usuario_id=l.usuario_id) "+
				"WHERE l.d_fecliq >= to_date('"+fechaInicial+"','dd/MM/yyyy') AND l.d_fecliq <= to_date('"+FechaFinal+"', 'dd/MM/yyyy') "+
					"AND l.agencia_id=NVL("+idAgencia+",l.agencia_id) "+criteriosUsuario+" "+criterios+" AND l.c_estreg='A' " +
				"ORDER BY l.d_FecLiq, l.audfecins ";

		log.info(sql);
		List<?> result = getSession().createSQLQuery(sql).list();
		List<Liquidacion> ListResult = new ArrayList<>();
		for(int i=0; i<result.size(); i++){
			Object[] obj = (Object[]) result.get(i);

			Agencia agencia = new Agencia();
			agencia.setId(((BigDecimal) obj[2]).intValue());
			agencia.setDenominacion(obj[6].toString());
			agencia.setCodigo(obj[17]!=null?obj[17].toString():null);

			Usuario usuario = new Usuario();
			usuario.setId(((BigDecimal) obj[3]).intValue());
			usuario.setLogin(obj[5].toString());
			usuario.setApellidoPaterno(obj[7].toString());
			if (obj[8] !=null)
				usuario.setApellidoMaterno(obj[8].toString());
			usuario.setNombre(obj[9].toString());

			Liquidacion liquidacion = new Liquidacion();
			liquidacion.setId(((BigDecimal) obj[0]).intValue());
			liquidacion.setAnio(((BigDecimal)obj[1]).intValue());
			liquidacion.setNombreUsuario(obj[5].toString());
			liquidacion.setEstadoLiquidacion(((BigDecimal) obj[4]).intValue());
			liquidacion.setFechaLiquidacion(((Date)obj[10]));
			if (obj[11] !=null)
				liquidacion.setFechaModificacion(((Date)obj[11]));
			if (obj[12] !=null)
				liquidacion.setFechaInsercion(((Date)obj[12]));
			if (obj[13] !=null)
				liquidacion.setIpInsercion(obj[13].toString());
			if (obj[14] !=null)
				liquidacion.setUsuarioInsercion(obj[14].toString());
			if (obj[15] !=null)
				liquidacion.setMontoIngresado(((BigDecimal) obj[15]).doubleValue());
//<<<<<<< HEAD
//			if(obj[16]!=null)
//				liquidacion.setUsuarioModificacion(obj[16].toString());
//=======
//			if (obj[16] !=null)
				liquidacion.setMontoIngresadoDolares(((BigDecimal) obj[16]).doubleValue());

//>>>>>>> ced84039982e9139bf3cb6857a8e1ae3016943a5
			liquidacion.setUsuario(usuario);
			liquidacion.setAgencia(agencia);

			ListResult.add(liquidacion);
		}

		return ListResult;
	}

	/*
	 * (non-Javadoc)
	 * @see com.tepsa.sisvyr.model.dao.LiquidacionDAO#buscarUltimaLiquidacion(java.lang.Integer, java.lang.Integer, java.lang.Integer)
	 */
	@Override
	public Liquidacion buscarUltimaLiquidacion(Integer idAgencia, Integer idUsuario, Integer estadoLiquidacion) throws Exception{
		String sql ="SELECT l.liquidacion_id, l.n_anio, l.agencia_id, l.usuario_id, l.c_nomusu, l.d_fecliq, l.n_estliq  " +
					"FROM vrtliquidacion l  " +
						"INNER JOIN (SELECT MAX(liquidacion_ID) liquidacion_ID " +
									 "FROM vrtliquidacion " +
									 "WHERE agencia_id="+idAgencia+" AND usuario_id="+idUsuario+" AND n_estliq="+estadoLiquidacion+" AND c_estreg='A') max_liq " +
							 "ON max_liq.liquidacion_id = l.liquidacion_id";

		log.info(sql);
		List<?> result = getSession().createSQLQuery(sql).list();
		Liquidacion liquidacion = null;
		if(result.size()==1){
			liquidacion = new Liquidacion();
			Object[] obj = (Object[])result.get(0);
			liquidacion.setId(((BigDecimal)obj[0]).intValue());
			liquidacion.setAnio(((BigDecimal)obj[1]).intValue());
			Agencia agencia = new Agencia();
			agencia.setId(((BigDecimal)obj[2]).intValue());
			liquidacion.setAgencia(agencia);
			Usuario usuario = new Usuario();
			usuario.setId(((BigDecimal)obj[3]).intValue());
			liquidacion.setUsuario(usuario);
			liquidacion.setNombreUsuario(obj[5].toString());
			liquidacion.setFechaLiquidacion((Date)obj[5]);
			liquidacion.setEstadoLiquidacion(((BigDecimal)obj[6]).intValue());
		}
		return liquidacion;
	}

	@Override
	public void actualizar(Liquidacion liquidacion) throws Exception {
		String sql="UPDATE VRTLIQUIDACION SET AUDIPMODI='"+liquidacion.getIpModificacion()+"', AUDUSUMOD='"+liquidacion.getUsuarioModificacion()+
					"', N_MONING="+liquidacion.getMontoIngresado()+",N_ESTLIQ="+liquidacion.getestadoLiquidacion()+
					", N_MONINGDOL="+liquidacion.getMontoIngresadoDolares()+" WHERE LIQUIDACION_ID="+liquidacion.getId();
		log.info(sql);
		getSession().createSQLQuery(sql).executeUpdate();
		//super.update(liquidacion);

	}

	/*
	 * (non-Javadoc)
	 * @see com.tepsa.sisvyr.model.dao.LiquidacionDAO#BuscarEspeciesValoradas(java.lang.String, java.lang.Integer, java.lang.Integer)
	 */
	@Override
	public List<Liquidacion> BuscarEspeciesValoradas(String fechaLiquidacion,Integer idAgencia, Integer idUsuario) {

		String sql="SELECT TipoComprobante "+
					       ",Serie "+
					       ",boletoInicial "+
					       ",BoletoFinal "+
					       ",BoletoFinal-boletoInicial+1 AS Cantidad "+
					       ",(BoletoFinal-boletoInicial+1)-cantRegistros AS Cortes "+
					       ",tipoComprobanteId "+
					"FROM( "+
					       //Recupera todos menos las confirmaciones de fecha habierta de fecha habierta
					      "SELECT tc.tipcom_id tipoComprobanteId, tc.c_denominacion as TipoComprobante, "+
					      		 "DECODE(tc.tipcom_id,1, MIN(SUBSTR(vp.c_numboleto,0,3)), MIN(SUBSTR(vp.c_numboleto,0,4))) AS Serie, "+
		                         "DECODE(tc.tipcom_id,1, MIN(SUBSTR(vp.c_numboleto,5, LENGTH(vp.c_numboleto))), MIN(SUBSTR(vp.c_numboleto,6, LENGTH(vp.c_numboleto)))) AS boletoInicial, "+
		                         "DECODE(tc.tipcom_id,1, MAX(SUBSTR(vp.c_numboleto,5, LENGTH(vp.c_numboleto))), MAX(SUBSTR(vp.c_numboleto,6, LENGTH(vp.c_numboleto))))  AS BoletoFinal, "+
		                         "COUNT(DISTINCT(nb.c_numboleto)) AS cantRegistros "+
		                 "FROM vrtvenpas vp "+
			                  "INNER JOIN vrmtipcom tc ON (tc.tipcom_id=vp.tipcom_id) "+
			                  "INNER JOIN (SELECT c_numboleto FROM vrtvenpas "+
			                            "WHERE c_numboleto IS NOT NULL "+
			                            "GROUP BY c_numboleto)nb ON (nb.c_numboleto=vp.c_numboleto) "+
		                "WHERE vp.d_fecliq = to_date('"+fechaLiquidacion+"','dd/MM/yyyy') "+
		                  "AND vp.agencia_id="+idAgencia+" AND vp.usuario_id="+idUsuario+" "+
		                  "AND vp.c_numboleto IS NOT NULL "+
		                  "AND vp.tipmov_id NOT IN (5,6,13) "+
		                  "AND vp.c_tiptra IN ('1','3','4','5','6','7') "+
		                "GROUP BY tc.tipcom_id, tc.c_denominacion,tc.tipcom_id, DECODE(tc.tipcom_id,1,(substrc(vp.c_numboleto,0,3)),(substrc(vp.c_numboleto,0,4))) "+ 
//					"UNION ALL " +
//							//Solo confirmaciones de fecha habierta que tienen importe mayor a cero
//							"SELECT tc.c_denominacion as TipoComprobante, "+
//					             "MIN(SUBSTR(vp.c_numboleto,0,4)) AS Serie, "+
//					             "MIN(SUBSTR(vp.c_numboleto,5, LENGTH(vp.c_numboleto))) AS boletoInicial, "+
//					             "MAX(SUBSTR(vp.c_numboleto,5, LENGTH(vp.c_numboleto))) AS BoletoFinal, "+
//					             "COUNT(DISTINCT(nb.c_numboleto)) AS cantRegistros "+
//					        "FROM vrtvenpas vp  "+
//						      	"INNER JOIN vrmtipcom tc ON (tc.tipcom_id=vp.tipcom_id) "+
//						      	"INNER JOIN (SELECT c_numboleto FROM vrtvenpas  "+
//					                  "WHERE c_numboleto IS NOT NULL "+
//					                  "GROUP BY c_numboleto)nb ON (nb.c_numboleto=vp.c_numboleto) "+
//					        "WHERE vp.d_fecliq = to_date('"+fechaLiquidacion+"','dd/MM/yyyy')  "+
//							      "AND vp.agencia_id="+idAgencia+" AND vp.usuario_id="+idUsuario+" "+
//							      "AND vp.c_numboleto IS NOT NULL   "+
//							      "AND vp.tipmov_id =7 " +
//							      "AND vp.n_imppag>0 "+
//					        "GROUP BY tc.c_denominacion, (substrc(vp.c_numboleto,0,3)) "+
					     ")esv ";

		log.info(sql);
		List<?> result = getSession().createSQLQuery(sql).list();
		List<Liquidacion> ListResult = new ArrayList<>();

		for(int i=0; i<result.size(); i++){
			Object[] obj = (Object[]) result.get(i);

			TipoComprobante tipoComprobante= new TipoComprobante();
			tipoComprobante.setDenominacion(obj[0].toString());
			tipoComprobante.setId(((BigDecimal)obj[6]).intValue());

			Liquidacion liquidacion= new Liquidacion();
			liquidacion.setTipoComprobante(tipoComprobante);
			liquidacion.setSerie(obj[1].toString().length()==3?" "+obj[1].toString():obj[1].toString());
			liquidacion.setBoletoInicial(obj[2].toString().length()==7?"0"+obj[2].toString():obj[2].toString());
			liquidacion.setBoletoFinal(obj[3].toString().length()==7?"0"+obj[3].toString():obj[3].toString());
			liquidacion.setCantidadBoletos(((BigDecimal)obj[4]).intValue());
			liquidacion.setCorte(((BigDecimal)obj[5]).intValue());


			ListResult.add(liquidacion);
		}


		return ListResult;
	}

	@Override
	public Liquidacion buscarRptLiquidacionTurno(String fechaLiquidacion, Integer idAgencia, Integer idUsuario) {
		String sql ="SELECT * "+
					"FROM( "+
						"SELECT  tc.tipcom_id, fp.forpag_id, tfp.tipforpag_id, otc.opetarcre_id, tm.tipmov_id, NULL as tipgas_id, "+ //0-5
						         "COUNT(v.n_imppag) AS CANTIDAD, "+ //6-6
						         "SUM(v.n_imppag) AS MONTO, "+ //7-7
//						         "SUM(DECODE(NVL(v.n_imppagdif,0),0,v.n_imppag,v.n_imppagdif)) AS MONTO, "+ //7-7
						         "SUM(v.n_imppagefe) AS MIX_EFECTIVO, " + //8-8
						         "SUM(v.n_imppagtar) AS MIX_TARJETA, "+ //9-9
						         "v.c_tiptra tipoTransaccion, "+//10-10
						         "v.c_rucclicre rucCliCre, v.canven_id, "+//11-12
						         "SUM(NVL(v.n_imppagdif,0)) n_imppagdif, "+ //13
						         "v.tipmon_id " + //14
						"FROM vrtvenpas v  " +
							"INNER JOIN vrmtipcom tc ON (v.tipcom_id = tc.tipcom_id) "+
							"INNER JOIN vrmforpag fp ON (v.forpag_id = fp.forpag_id) "+
							"INNER JOIN vrmtipforpag tfp ON (v.tipforpag_id = tfp.tipforpag_id) "+
							"INNER JOIN vrmtipmov tm ON (v.tipmov_id = tm.tipmov_id) "+
							"LEFT OUTER JOIN vrmtarcre tcr ON (v.tarcre_id=tcr.tarcre_id) "+
							"LEFT OUTER JOIN vrmopetarcre otc ON (tcr.opetarcre_id = otc.opetarcre_id) "+
						"WHERE v.d_fecliq=to_date('"+fechaLiquidacion+"','dd/MM/yyyy') "+
							"AND v.c_tiptra IN ('1','3','4','5','6','7') " + //Ventas y Varios(Notas de credito y gastos administrativos)
							"AND v.agencia_id="+idAgencia+" "+
							"AND v.usuario_id="+idUsuario+" "+
							"AND v.tipmov_id not in(5,13) "+
							"AND v.c_Estreg='A' "+
							"AND v.n_imppag>0 AND NVL(v.n_imppagdif,0)=0 "+
						"GROUP BY tc.tipcom_id, fp.forpag_id, tfp.tipforpag_id, otc.opetarcre_id, tm.tipmov_id, v.c_tiptra,v.c_rucclicre,v.canven_id,v.tipmon_id "+
					"UNION ALL "+
						"SELECT  tc.tipcom_id, fp.forpag_id, tfp.tipforpag_id, otc.opetarcre_id, tm.tipmov_id, NULL as tipgas_id, "+ //0-5
						         "COUNT(v.n_imppag) AS CANTIDAD, "+ //6-6
						         "SUM(v.n_imppag) AS MONTO, "+ //7-7
						         "SUM(v.n_imppagefe) AS MIX_EFECTIVO, " + //8-8
						         "SUM(v.n_imppagtar) AS MIX_TARJETA, "+ //9-9
						         "v.c_tiptra tipoTransaccion, "+//10-10
						         "v.c_rucclicre rucCliCre, v.canven_id, "+//11-12
						         "SUM(NVL(v.n_imppagdif,0)) n_imppagdif, "+ //13
						         "v.tipmon_id " + //14
						"FROM vrtvenpas v  " +
							"INNER JOIN vrmtipcom tc ON (v.tipcom_id = tc.tipcom_id) "+
							"INNER JOIN vrmforpag fp ON (v.forpag_id = fp.forpag_id) "+
							"INNER JOIN vrmtipforpag tfp ON (v.tipforpag_id = tfp.tipforpag_id) "+
							"INNER JOIN vrmtipmov tm ON (v.tipmov_id = tm.tipmov_id) "+
							"LEFT OUTER JOIN vrmtarcre tcr ON (v.tarcre_id=tcr.tarcre_id) "+
							"LEFT OUTER JOIN vrmopetarcre otc ON (tcr.opetarcre_id = otc.opetarcre_id) "+
						"WHERE v.d_fecliq=to_date('"+fechaLiquidacion+"','dd/MM/yyyy') "+
							"AND v.c_tiptra IN ('1','3','4','5','6','7') " + //Ventas y Varios(Notas de credito y gastos administrativos)
							"AND v.agencia_id="+idAgencia+" "+
							"AND v.usuario_id="+idUsuario+" "+
							"AND v.tipmov_id not in(5,13) "+
							"AND v.c_Estreg='A' "+
							"AND v.n_imppag>0 AND NVL(v.n_imppagdif,0)>0 "+
						"GROUP BY tc.tipcom_id, fp.forpag_id, tfp.tipforpag_id, otc.opetarcre_id, tm.tipmov_id, v.c_tiptra,v.c_rucclicre,v.canven_id,v.tipmon_id "+
					"UNION ALL "+
						"SELECT NULL,NULL,NULL,NULL,NULL,tg.tipgas_id, "+
						        "COUNT(g.n_monto) AS CANTIDAD,  "+
						        "SUM(g.n_monto) AS MONTO,  "+
						        "0.00 AS MIX_EFECTIVO, " +
						        "0.00 AS MIX_TARJETA, "+
						        "null tipoTransaccion, "+//10-10
						        "null rucCliCre, null canven_id, 0.00 n_imppagdif, "+//11-13
						        "null " + //14
						"FROM vrtgasto g "+
							"INNER JOIN vrmtipgas tg ON (tg.tipgas_id=g.tipgas_id) "+
							"INNER JOIN (SELECT dl.gasto_id FROM vrtdetliq dl "+
							            "INNER JOIN vrtliquidacion l On (l.liquidacion_id=dl.liquidacion_id) "+
							            "WHERE l.d_fecliq=to_date('"+fechaLiquidacion+"','dd/MM/yyyy')  "+
							            "AND l.agencia_id="+idAgencia+" AND l.usuario_id="+idUsuario+" AND l.c_estreg='A') dl ON (dl.gasto_id=g.gasto_id) "+
						"WHERE g.c_estreg='A' "+
						"GROUP BY tg.tipgas_id, tg.c_denominacion "+
					")RPT_LIQ ";

		log.info(sql);

		List<?> result = getSession().createSQLQuery(sql).list();
		Liquidacion liquidacion= new Liquidacion();
		initVar(liquidacion);

		Integer cantidadDev=0; double montoDev=0;
		Integer cantidadCredito=0; double montoCredito=0;
		Integer cantidadTarjetaVisa=0; double montoTarjetaVisa=0;
		Integer cantidadTarjetaMasterCard=0; double montoTarjetaMasterCard=0;
		Integer cantidadRc=0; double montoRc=0;
		Integer cantidadContado=0; double montoContado=0;
		Integer cantidadPrepagado=0; double montoPrepagado=0;
		Integer cantidadCortecia=0; double montoCortecia=0;
		Integer cantidadNotasCredito=0; double montoNotasCredito=0;
		Integer cantidadEfecPool=0;double montoEfecPool=.00;
		Integer cantidadTCVisaPool=0;double montoTCVisaPool=.00;
		Integer cantidadTCMastercardPool=0;double montoTCMastercardPool=.00;

		Integer cantidadTarjetaVisaRC=0;double montoTarjetaVisaRC=0;
		Integer cantidadTarjetaMasterCardRC = 0;double montoTarjetaMasterCardRC=0;
		Integer cantidadDevTarjeta=0;double montoDevTarjeta=0;

		Integer cantidadGastoAdminEfectivo=0;double montoGastoAdminEfectivo=.00;
		Integer cantidadGastoAdminTarjetaVisa=0;double montoGastoAdminTarjetaVisa=.00;
		Integer cantidadGastoAdminTarjetaMastercard=0;double montoGastoAdminTarjetaMastercard=.00;
		Integer cantidadContadoDolares=0; double montoContadoDolares = 0.0;
		Integer cantidadCreditoDolares=0; double montoCreditoDolares = 0.0;

		Integer cantidadPCE=0; double montoPCE=0.0;
		Integer cantidadTransferencias=0; double montoTransferencias=0.0;
		Integer cantidadYape=0; double montoYape=0.0;


		for (Object element : result) {
			Object[] obj = (Object[]) element;
			Integer tipoComprobanteID=(obj[0]!=null?((BigDecimal)obj[0]).intValue():null);
			Integer formaPagoID=(obj[1]!=null?((BigDecimal)obj[1]).intValue():null);
			Integer tipoMovimientoID=(obj[4]!=null?((BigDecimal)obj[4]).intValue():null);
			String tipoTransaccion=(obj[10]!=null?obj[10].toString():null);
			String rucClienteCredito=(obj[11]!=null?obj[11].toString():null);

			Double importePagadoDiferecnia=((BigDecimal)obj[13]).doubleValue();
			Integer idTipoMoneda = obj[14]==null?null:((BigDecimal)obj[14]).intValue();

			Double importeOperacion=.00;
			Integer cantidadOperacion=0;
			/*Valida la diferencia */
			if(importePagadoDiferecnia>0){
				importeOperacion=importePagadoDiferecnia;
				montoContado+=+ ((BigDecimal)obj[7]).doubleValue()-importePagadoDiferecnia;
				cantidadContado+=+ ((BigDecimal)obj[6]).intValue();
			}else{
				importeOperacion=((BigDecimal)obj[7]).doubleValue();
				cantidadOperacion=((BigDecimal)obj[6]).intValue();
			}

			/*Pago mixto*/
			Double importeMixtoEfectivo=(obj[8]!=null?((BigDecimal)obj[8]).doubleValue():.00);
			Double importeMixtoTarjeta=(obj[9]!=null?((BigDecimal)obj[9]).doubleValue():.00);

			/*INGRESOS*/
			//SOLO CONTADO.
			if(tipoComprobanteID !=null && formaPagoID.intValue()==Constantes.ID_FORPAG_CONTADO && tipoMovimientoID.intValue()!=Constantes.ID_TIPMOV_DEVOLUCION){
				int tipoFormaPagoID=((BigDecimal)obj[2]).intValue();

				if(tipoComprobanteID.intValue()==Constantes.ID_TIPCOM_BOLETO_VIAJE || tipoComprobanteID.intValue()==Constantes.ID_TIPCOM_BOLETA_VENTA || tipoComprobanteID.intValue()==Constantes.ID_TIPCOM_FACTURA || tipoComprobanteID==Constantes.ID_TIPCOM_GUIA_TRANSPORTISTA){
					if(tipoMovimientoID.intValue()==Constantes.ID_TIPMOV_PREPAGADO ) { //PREPAGADOS
						cantidadPrepagado+=+cantidadOperacion;
						montoPrepagado+=+importeOperacion;

//					}else if(tipoFormaPagoID==Constantes.ID_TIPFORPAG_EFECTIVO || tipoFormaPagoID==Constantes.ID_TIPFORPAG_TRANSFERENCIA){ //EFECTIVO
					}else if(tipoFormaPagoID==Constantes.ID_TIPFORPAG_EFECTIVO){ //EFECTIVO
						if(tipoTransaccion!=null && tipoTransaccion.equals(Constantes.TIPO_OPERACION_VENTA_POOL)){ //Valida si es venta pool
							cantidadEfecPool+= +cantidadOperacion;
							montoEfecPool+= +importeOperacion;
						}else{
							if(tipoMovimientoID.intValue()==Constantes.ID_TIPMOV_GASTOS_ADMINISTRATIVOS){
								cantidadGastoAdminEfectivo+=+cantidadOperacion;
								montoGastoAdminEfectivo+=+importeOperacion;
							}else{
								if(idTipoMoneda==null) {
									cantidadContado+=+cantidadOperacion;
									montoContado+=+importeOperacion;
								}else {
									cantidadContadoDolares+=+cantidadOperacion;
									montoContadoDolares+=+importeOperacion;
								}
							}
						}
					}else if(tipoFormaPagoID==Constantes.ID_TIPFORPAG_TARJETA ){ //TARJETA
						if(((BigDecimal)obj[3]).intValue()==Constantes.ID_OPETARCRE_VISA){ //VISA
							if(tipoTransaccion!=null && tipoTransaccion.equals(Constantes.TIPO_OPERACION_VENTA_POOL)){ //Valida si es venta pool
								cantidadTCVisaPool+= +cantidadOperacion;
								montoTCVisaPool+=+importeOperacion;
								//Valida si es mixto
								if(importeMixtoEfectivo>0){
									montoTCVisaPool+=+importeMixtoTarjeta;//Suma el monto del pago mixo con tarjeta

									montoEfecPool+=+importeMixtoEfectivo; //Suma el monto del pago mixo al Efectivo
									cantidadEfecPool+=+cantidadOperacion; //Suma la cantidad en efectivo
								}
							}else{
								if(tipoMovimientoID.intValue()==Constantes.ID_TIPMOV_GASTOS_ADMINISTRATIVOS){
									cantidadGastoAdminTarjetaVisa+=+cantidadOperacion;
									montoGastoAdminTarjetaVisa+=+importeOperacion;
									//Valida si es mixto
									if(importeMixtoEfectivo>0){
										montoGastoAdminTarjetaVisa+=+importeMixtoTarjeta;//Suma el monto del pago mixo con tarjeta

										montoGastoAdminEfectivo+=+importeMixtoEfectivo;//Suma el monto del pago mixo al Efectivo
										cantidadGastoAdminEfectivo+=+cantidadOperacion;//Suma la cantidad en efectivo
									}
								}else{
									cantidadTarjetaVisa+=+cantidadOperacion;
									montoTarjetaVisa+=+importeOperacion;
									//Valida si es mixto
									if(importeMixtoEfectivo>0){
										montoTarjetaVisa+=+importeMixtoTarjeta;//Suma el monto del pago mixo con tarjeta

										montoContado+=+importeMixtoEfectivo;//Suma el monto del pago mixo al Efectivo
										cantidadContado+=+cantidadOperacion; //Suma la cantidad en efectivo
									}
								}
							}
						}else if(((BigDecimal)obj[3]).intValue()==Constantes.ID_OPETARCRE_MSTERCARD){ //MASTER CARD
							if(tipoTransaccion!=null && tipoTransaccion.equals(Constantes.TIPO_OPERACION_VENTA_POOL)){ //Valida si es venta pool
								cantidadTCMastercardPool+= +cantidadOperacion;
								montoTCMastercardPool+= +importeOperacion;
								//Valida si es mixto
								if(importeMixtoEfectivo>0){
									montoTCMastercardPool+=+importeMixtoTarjeta;//Suma el monto del pago mixo con tarjeta

									montoEfecPool+=+importeMixtoEfectivo;//Suma el monto del pago mixo al Efectivo
									cantidadEfecPool+=+cantidadOperacion; //Suma la cantidad en efectivo
								}
							}else{
								if(tipoMovimientoID.intValue()==Constantes.ID_TIPMOV_GASTOS_ADMINISTRATIVOS){
									cantidadGastoAdminTarjetaMastercard+=+cantidadOperacion;
									montoGastoAdminTarjetaMastercard+=+importeOperacion;
									//Valida si es mixto
									if(importeMixtoEfectivo>0){
										montoGastoAdminTarjetaMastercard+=+importeMixtoTarjeta;//Suma el monto del pago mixo con tarjeta

										montoGastoAdminEfectivo+=+importeMixtoEfectivo;//Suma el monto del pago mixo al Efectivo
										cantidadGastoAdminEfectivo+=+cantidadOperacion; //Suma la cantidad en efectivo
									}
								}else{
									cantidadTarjetaMasterCard+=+cantidadOperacion;
									montoTarjetaMasterCard+=+importeOperacion;
									//Valida si es mixto
									if(importeMixtoEfectivo>0){
										montoTarjetaMasterCard+=+importeMixtoTarjeta;//Suma el monto del pago mixo con tarjeta

										montoContado+=+importeMixtoEfectivo;//Suma el monto del pago mixo al Efectivo
										cantidadContado+=+cantidadOperacion; //Suma la cantidad en efectivo
									}
								}
							}
						}
					}else if(tipoFormaPagoID==Constantes.ID_TIPFORPAG_PCE) {
						cantidadPCE+= +cantidadOperacion;
						montoPCE+= +importeOperacion;
					}else if(tipoFormaPagoID==Constantes.ID_TIPFORPAG_TRANSFERENCIA) {
						cantidadTransferencias+= +cantidadOperacion;
						montoTransferencias+= +importeOperacion;
					}else if(tipoFormaPagoID==Constantes.ID_TIPFORPAG_YAPE) {
						cantidadYape+= +cantidadOperacion;
						montoYape+= +importeOperacion;
					}
				}else if (tipoComprobanteID.intValue()==Constantes.ID_TIPCOM_NOTA_CREDITO){
					cantidadNotasCredito+= +cantidadOperacion;
					montoNotasCredito+= +importeOperacion;

				}else if (tipoComprobanteID.intValue()==Constantes.ID_TIPCOM_RECIBO_CAJA){
					/*TARJETA*/
					if(tipoFormaPagoID==Constantes.ID_TIPFORPAG_TARJETA ){// TARJETA
						if(((BigDecimal)obj[3]).intValue()==Constantes.ID_OPETARCRE_VISA){ //VISA
							cantidadTarjetaVisaRC+=+cantidadOperacion;
							montoTarjetaVisaRC+=+importeOperacion;
							//Valida si es mixto
							if(importeMixtoEfectivo>0){
								montoTarjetaVisaRC+=+importeMixtoTarjeta;//Suma el monto pago mixo con tarjeta

								montoRc+=+importeMixtoEfectivo;//Suma el monto del pago mixo al Efectivo
								cantidadRc+=+cantidadOperacion; //Suma la cantidad en efectivo
							}

						}else if(((BigDecimal)obj[3]).intValue()==Constantes.ID_OPETARCRE_MSTERCARD){ //MASTER CARD
							cantidadTarjetaMasterCardRC+=+cantidadOperacion;
							montoTarjetaMasterCardRC+=+importeOperacion;
							//Valida si es mixto
							if(importeMixtoEfectivo>0){
								montoTarjetaVisaRC+=+importeMixtoTarjeta;//Suma el pago mixo con tarjeta

								montoRc+=+importeMixtoEfectivo;//Suma el Monto del pago mixo al Efectivo
								cantidadRc+=+cantidadOperacion;//Suma la cantidad en efectivo
							}
						}
					}else{
						cantidadRc+=+cantidadOperacion;
						montoRc+=+importeOperacion;
					}
				}

			//BOLETO DE VIAJE - CORTESIA - PREPAGADOS - CREDITOS.
			}else if(tipoComprobanteID!=null
					&& (tipoComprobanteID.intValue()==Constantes.ID_TIPCOM_BOLETO_VIAJE || tipoComprobanteID.intValue()==Constantes.ID_TIPCOM_BOLETA_VENTA || tipoComprobanteID.intValue()==Constantes.ID_TIPCOM_FACTURA)
					&& tipoMovimientoID.intValue()!=Constantes.ID_TIPMOV_DEVOLUCION) {
				if(((BigDecimal)obj[1]).intValue()==Constantes.ID_FORPAG_CORTESIA){ //CORTECIA
					cantidadCortecia+=+cantidadOperacion;
					montoCortecia+=+importeOperacion;


				}else if(((BigDecimal)obj[1]).intValue()==Constantes.ID_FORPAG_CREDITO){ //CREDITO
					if(idTipoMoneda==null) {
						cantidadCredito+=+cantidadOperacion;
						montoCredito+=+importeOperacion;
					}else {
						cantidadCreditoDolares+=+cantidadOperacion;
						montoCreditoDolares+=+importeOperacion;
					}
				}

			/*OTROS INGRESOS*/
			//GASTOS
			}else if(tipoComprobanteID==null){
				if(((BigDecimal)obj[5]).intValue()==Constantes.ID_TIPGAS_GASTOS_VARIOS){ //GASTOS VARIOS
					liquidacion.setCantidadGastoVarios(cantidadOperacion);
					liquidacion.setMontoGastoVarios(importeOperacion);
				}else if(((BigDecimal)obj[5]).intValue()==Constantes.ID_TIPGAS_CONDOCUMENTO){ //GASTOS CON DOCUMENTO
						liquidacion.setCantidadGastoConDocumento(cantidadOperacion);
						liquidacion.setMontoGastoConDocumento(importeOperacion);
				}else if(((BigDecimal)obj[5]).intValue()==Constantes.ID_TIPGAS_PEAJES){//PEAJES
					liquidacion.setCantidadPeajes(cantidadOperacion);
					liquidacion.setMontoPeajes(importeOperacion);

				}else if(((BigDecimal)obj[5]).intValue()==Constantes.ID_TIPGAS_PAGO_GIROS){//PAGO DE GIROS
					liquidacion.setCantidadPagoGiros(cantidadOperacion);
					liquidacion.setMontoPagoGiros(importeOperacion);
				}
			}

			if(tipoComprobanteID !=null && tipoMovimientoID.intValue()==Constantes.ID_TIPMOV_DEVOLUCION){
				/*Si es una devolucion de un boleto de viaje o recivo de caja*/
				if (tipoComprobanteID.intValue()==Constantes.ID_TIPCOM_BOLETO_VIAJE || tipoComprobanteID.intValue()==Constantes.ID_TIPCOM_RECIBO_CAJA){

					if(rucClienteCredito!=null){
						/*No considera las devoluciones de boletos de viaje emitidos por operados del POOL - jabanto 14/12/2016*/
						if(!(rucClienteCredito.equals(Constantes.RUC_CIVA)) && !(rucClienteCredito.equals(Constantes.RUC_CRUZ_DEL_SUR))){
							cantidadDev += +cantidadOperacion;
							montoDev += +importeOperacion;
						}
					}else{
						/*Otras devoluciones de boletos de viaje*/
						cantidadDev += +cantidadOperacion;
						montoDev += +importeOperacion;
					}
				}else{
					/*Facturas y Boletas*/
					int canalVentaId=((BigDecimal)obj[12]).intValue();
					if(rucClienteCredito!=null){
						/*No considera las devoluciones emitidas por operadores del POOL - jabanto 14/12/2016*/
						if(!(rucClienteCredito.equals(Constantes.RUC_CIVA)) && !(rucClienteCredito.equals(Constantes.RUC_CRUZ_DEL_SUR))){
							/*Valida si es una devolucion de una canal Agencia de Viajes o Web*/
							if(canalVentaId==Constantes.ID_CANVEN_AGENCIA_VIAJES || canalVentaId==Constantes.ID_CANVEN_WEB){
								cantidadDev += +cantidadOperacion;
								montoDev += +importeOperacion;
							}
						}
					}else{
						/*Valida si es una devolucion de una canal Agencia de Viajes o Web*/
						if(canalVentaId==Constantes.ID_CANVEN_AGENCIA_VIAJES || canalVentaId==Constantes.ID_CANVEN_WEB){
							cantidadDev += +cantidadOperacion;
							montoDev += +importeOperacion;
						}
					}
				}
			}
		}

		liquidacion.setCantidadContado(cantidadContado);
		liquidacion.setMontoContado(montoContado);
		liquidacion.setCantidadTarjetaVisa(cantidadTarjetaVisa);
		liquidacion.setMontoTarjetaVisa(montoTarjetaVisa);
		liquidacion.setCantidadTarjetaMasterCard(cantidadTarjetaMasterCard);
		liquidacion.setMontoTarjetaMasterCard(montoTarjetaMasterCard);
		liquidacion.setCantidadRC(cantidadRc);
		liquidacion.setMontoRC(montoRc);
		liquidacion.setCantidadCreditos(cantidadCredito);
		liquidacion.setMontoCreditos(montoCredito);
		liquidacion.setCantidadPrepagado(cantidadPrepagado);
		liquidacion.setMontoPrepagado(montoPrepagado);
		liquidacion.setCantidadCortesia(cantidadCortecia);
		liquidacion.setMontoCortesia(montoCortecia);
		liquidacion.setCantidadDevolucion(cantidadDev);
		liquidacion.setMontoDevolucion(montoDev);
		liquidacion.setCantidadTarjetaMasterCardRC(cantidadTarjetaMasterCardRC);
		liquidacion.setMontoTarjetaMasterCardRC(montoTarjetaMasterCardRC);
		liquidacion.setCantidadTarjetaVisaRC(cantidadTarjetaVisaRC);
		liquidacion.setMontoTarjetaVisaRC(montoTarjetaVisaRC);
		liquidacion.setCantidadNotasCredito(cantidadNotasCredito);
		liquidacion.setMontoNotasCredito(montoNotasCredito);
		liquidacion.setCantidadEfecPool(cantidadEfecPool);
		liquidacion.setMontoEfecPool(montoEfecPool);
		liquidacion.setCantidadTCVisaPool(cantidadTCVisaPool);
		liquidacion.setMontoTCVisaPool(montoTCVisaPool);
		liquidacion.setCantidadTCMastercardPool(cantidadTCMastercardPool);
		liquidacion.setMontoTCMastercardPool(montoTCMastercardPool);
		liquidacion.setCantidadDevolucionTarjeta(cantidadDevTarjeta);
		liquidacion.setMontoDevolucionTarjeta(montoDevTarjeta);
		liquidacion.setCantidadGastosAdminEfectivo(cantidadGastoAdminEfectivo);
		liquidacion.setMontoGastosAdminEfectivo(montoGastoAdminEfectivo);
		liquidacion.setCantidadGastosAdminTarjetaVisa(cantidadGastoAdminTarjetaVisa);
		liquidacion.setMontoGastosAdminTarjetaVisa(montoGastoAdminTarjetaVisa);
		liquidacion.setCantidadGastosAdminTarjetaMastercard(cantidadGastoAdminTarjetaMastercard);
		liquidacion.setMontoGastosAdminTarjetaMastercard(montoGastoAdminTarjetaMastercard);
		liquidacion.setCantidadPCE(cantidadPCE);
		liquidacion.setMontoPCE(montoPCE);
		liquidacion.setCantidadCreditosDolares(cantidadCreditoDolares);
		liquidacion.setMontoCreditosDolares(montoCreditoDolares);
		liquidacion.setCantidadContadoDolares(cantidadContadoDolares);
		liquidacion.setMontoContadoDolares(montoContadoDolares);
		liquidacion.setCantidadTransferencias(cantidadTransferencias);
		liquidacion.setMontoTransferencias(montoTransferencias);
		liquidacion.setCantidadYape(cantidadYape);
		liquidacion.setMontoYape(montoYape);

		return liquidacion;
	}

	/**
	 * Inicia variables del RPT liquidacion turno
	 * @param liquidacion
	 */
	private void initVar(Liquidacion liquidacion){
		liquidacion.setCantidadContado(0);
		liquidacion.setMontoContado(0);
		liquidacion.setCantidadTarjetaVisa(0);
		liquidacion.setMontoTarjetaVisa(0);
		liquidacion.setCantidadTarjetaMasterCard(0);
		liquidacion.setMontoTarjetaMasterCard(0);
		liquidacion.setCantidadCortesia(0);
		liquidacion.setMontoCortesia(0);
		liquidacion.setCantidadPrepagado(0);
		liquidacion.setMontoPrepagado(0);
		liquidacion.setCantidadCreditos(0);
		liquidacion.setMontoCreditos(0);
		liquidacion.setCantidadRC(0);
		liquidacion.setMontoRC(0);
		liquidacion.setCantidadDevolucion(0);
		liquidacion.setMontoDevolucion(0);
		liquidacion.setCantidadGastoVarios(0);
		liquidacion.setMontoGastoVarios(0);
		liquidacion.setCantidadPeajes(0);
		liquidacion.setMontoPeajes(0);
		liquidacion.setCantidadPagoGiros(0);
		liquidacion.setMontoPagoGiros(0);
		liquidacion.setCantidadGastoConDocumento(0);
		liquidacion.setMontoGastoConDocumento(0);
	}

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.model.dao.LiquidacionDAO#buscarPorId(java.lang.Long)
	 */
	@Override
	public Liquidacion buscarPorId(Long id) throws Exception {
		// TODO Auto-generated method stub
		return (Liquidacion) super.findById(Liquidacion.class, id);
	}

    @Override
	public List<LiquidacionTuentrada> liquidacionTuentrada(Integer idAgencia, Integer idUsuario, String fechaliquidacion){
    	String sql = "SELECT parcial.tipo, SUM(parcial.cantidad),SUM(parcial.IMPORTE) "
    			+ "FROM  (SELECT DECODE(vp.tarcre_id, null,'CONTADO', 'TARJETA') TIPO, COUNT(vp.n_imppag)CANTIDAD, SUM(vp.n_imppag)IMPORTE "
    			+ "FROM vrtvenpas vp "
    			+ "WHERE vp.agencia_id="+idAgencia+" AND vp.usuario_id="+idUsuario+" AND vp.d_fecliq=TO_DATE('"+fechaliquidacion+"','dd/mm/yyyy') "
    			+ "GROUP BY vp.tarcre_id) parcial "
    			+ "GROUP BY parcial.tipo "
    			+ "ORDER BY parcial.tipo ";

    	log.info(sql);

    	List<?> result = getSession().createSQLQuery(sql).list();
    	List<LiquidacionTuentrada> lstResult = null;
    	if(result.size()>0){
    		lstResult = new ArrayList<>();
    		for(int i=0; i<result.size(); i++){
    			Object[] obj = (Object[])result.get(i);
    			LiquidacionTuentrada liquidacionTuentrada = new LiquidacionTuentrada();
    			liquidacionTuentrada.setTipo(obj[0].toString());
    			liquidacionTuentrada.setCantidad(((BigDecimal)obj[1]).intValue());
    			liquidacionTuentrada.setMonto(((BigDecimal)obj[2]).doubleValue());
    			lstResult.add(liquidacionTuentrada);
    		}
    	}
    	return lstResult;
    }
//
//	/* (non-Javadoc)
//	 * @see com.tepsa.sisvyr.model.dao.LiquidacionDAO#reaperturarLiquidacion(com.tepsa.sisvyr.model.bean.Liquidacion)
//	 */
//	@Override
//	public void reaperturarLiquidacion(Long idliquidacion)throws Exception {
//		/*Reapertura laliquidacion*/
//		String sql="UPDATE VRTLIQUIDACION SET n_estliq=1 WHERE liquidacion_id="+idliquidacion;
//		log.info(sql);
//		getSession().createSQLQuery(sql).executeUpdate();
//		/*Elimina el detalle de la liquidacion*/
//		sql="DELETE FROM VRTDETLIQ WHERE liquidacion_id="+idliquidacion+" AND gasto_id IS NULL";
//		log.info(sql);
//		getSession().createSQLQuery(sql).executeUpdate();
//	}
//
    @Override
	public Map<String, ResumenComprobante> buscarResumenComprobantes(String fechaLiquidacion, Integer idAgencia, Integer idUsuario){
    	String sql = "SELECT  tc.tipcom_id, tc.c_denominacion, substr(v.c_numboleto,1,4) serie, fp.forpag_id, tm.tipmov_id, COUNT(v.n_imppag) AS CANTIDAD, "
    			+ "SUM(v.n_imppag) AS MONTO, SUM(v.n_imppagefe) AS MIX_EFECTIVO, SUM(v.n_imppagtar) AS MIX_TARJETA, SUM(NVL(v.n_imppagdif,0)) n_imppagdif "
    			+ "FROM vrtvenpas v "
    			+ "INNER JOIN vrmtipcom tc ON (v.tipcom_id = tc.tipcom_id) "
    			+ "INNER JOIN vrmforpag fp ON (v.forpag_id = fp.forpag_id) "
    			+ "INNER JOIN vrmtipmov tm ON (v.tipmov_id = tm.tipmov_id) "
    			+ "WHERE v.d_fecliq=to_date('"+fechaLiquidacion+"','dd/MM/yyyy') AND v.c_tiptra IN ('1','3','4','5','6','7') AND v.agencia_id="+idAgencia+" AND "
//    			+ "v.usuario_id="+idUsuario+" AND v.tipmov_id not in(5,6,13) AND v.c_Estreg='A' AND v.n_imppag>0 AND NVL(v.n_imppagdif,0)=0 "
    			+ "v.usuario_id="+idUsuario+" AND v.tipmov_id not in(5,6) AND v.c_Estreg='A' AND NVL(v.n_imppagdif,0)=0 "
    			+ "GROUP BY tc.tipcom_id, tc.c_denominacion, substr(v.c_numboleto,1,4), fp.forpag_id, tm.tipmov_id "
    			+ "UNION ALL "
    			+ "SELECT  tc.tipcom_id, tc.c_denominacion, substr(v.c_numboleto,1,4) serie, fp.forpag_id, tm.tipmov_id, COUNT(v.n_imppag) AS CANTIDAD, "
    			+ "SUM(v.n_imppag) AS MONTO, SUM(v.n_imppagefe) AS MIX_EFECTIVO, SUM(v.n_imppagtar) AS MIX_TARJETA, SUM(NVL(v.n_imppagdif,0)) n_imppagdif "
    			+ "FROM vrtvenpas v "
    			+ "INNER JOIN vrmtipcom tc ON (v.tipcom_id = tc.tipcom_id) "
    			+ "INNER JOIN vrmforpag fp ON (v.forpag_id = fp.forpag_id) "
    			+ "INNER JOIN vrmtipmov tm ON (v.tipmov_id = tm.tipmov_id) "
    			+ "WHERE v.d_fecliq=to_date('"+fechaLiquidacion+"','dd/MM/yyyy') AND v.c_tiptra IN ('1','3','4','5','6','7') AND v.agencia_id="+idAgencia+" AND "
//    			+ "v.usuario_id="+idUsuario+" AND v.tipmov_id not in(5,6,13) AND v.c_Estreg='A' AND v.n_imppag>0 AND NVL(v.n_imppagdif,0)>0 "
    			+ "v.usuario_id="+idUsuario+" AND v.tipmov_id not in(5,6) AND v.c_Estreg='A' AND NVL(v.n_imppagdif,0)>0 "
    			+ "GROUP BY tc.tipcom_id, tc.c_denominacion, substr(v.c_numboleto,1,4), fp.forpag_id, tm.tipmov_id ";

    	log.info(sql);

		List<?> result = getSession().createSQLQuery(sql).list();

		Map<String, ResumenComprobante> map = new TreeMap<>();
		ResumenComprobante resumenComprobante = null;

		for (Object element : result) {
			Object[] obj = (Object[])element;
			String key = obj[0].toString()+obj[2].toString();
			if(map.containsKey(key)) {
				resumenComprobante = map.get(key);
				resumenComprobante.setCantidad(resumenComprobante.getCantidad() + ((BigDecimal)obj[5]).intValue());
				resumenComprobante.setMonto(resumenComprobante.getMonto() + ((BigDecimal)obj[6]).doubleValue());
				map.put(key, resumenComprobante);
			}else {
				resumenComprobante = new ResumenComprobante();
				resumenComprobante.setIdTipoComprobante(((BigDecimal)obj[0]).intValue());
				resumenComprobante.setComprobante(obj[1].toString());
				resumenComprobante.setSerie(obj[2].toString());
				resumenComprobante.setCantidad(((BigDecimal)obj[5]).intValue());
				resumenComprobante.setMonto(((BigDecimal)obj[6]).doubleValue());
				map.put(key, resumenComprobante);
			}
		}
		return map;
    }
}
