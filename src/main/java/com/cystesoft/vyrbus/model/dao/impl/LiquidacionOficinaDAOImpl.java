package com.cystesoft.vyrbus.model.dao.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.cystesoft.vyrbus.model.bean.Agencia;
import com.cystesoft.vyrbus.model.bean.Liquidacion;
import com.cystesoft.vyrbus.model.bean.LiquidacionOficina;
import com.cystesoft.vyrbus.model.bean.Usuario;
import com.cystesoft.vyrbus.model.dao.LiquidacionOficinaDAO;
/**
 *
 * @author JABANTO
 *
 */
public class LiquidacionOficinaDAOImpl extends GenericDAOImpl implements LiquidacionOficinaDAO {

	@Override
	public List<Liquidacion> buscarLiquidacionPendiente(String fecha, Integer idAgencia)throws Exception {

		String sql ="SELECT l.liquidacion_id, l.n_anio, l.agencia_id, l.usuario_id, l.n_estliq, l.c_nomusu as NombreUsuario, " +//0-5
							"a.c_denominacion as NombreAgencia, "+//6-6
							"u.c_apepat as ApPaterno, u.c_apemat as ApMarterno, u.c_nombre as Nombres, "+//7-9
							"l.d_fecliq as FechaLiquidacion, l.audfecmod as FechaMod, "+//10-11
							"l.audfecins as FechaInsercion, l.audipinse as ipinsercion, l.audusuins as susuarioInsercion "+//12-14
					"FROM vrtliquidacion l "+
					"INNER JOIN vrmagencia a ON (a.agencia_id=l.agencia_id) "+
					"INNER JOIN vrmusuario u ON (u.usuario_id=l.usuario_id) "+
					"WHERE l.d_fecliq = to_date('"+fecha+"','dd/MM/yyyy') "+
					"AND l.agencia_id="+idAgencia+" AND l.n_estliq=0 AND l.c_estreg='A' "+
					"AND L.Liquidacion_Id not in  (SELECT lo.liquidacion_id "+
												   "FROM vrtliqofi lo "+
                              					   "WHERE lo.d_fecliq=to_date('"+fecha+"','dd/MM/yyyy') AND lo.c_estreg='A') "+
					"ORDER BY l.d_FecLiq";

		log.info(sql);

		List<?> result = getSession().createSQLQuery(sql).list();
		List<Liquidacion> ListResult = new ArrayList<>();
		for(int i=0; i<result.size(); i++){
			Object[] obj = (Object[]) result.get(i);

			Agencia agencia = new Agencia();
			agencia.setId(((BigDecimal) obj[2]).intValue());
			agencia.setDenominacion(obj[6].toString());

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

			liquidacion.setUsuario(usuario);
			liquidacion.setAgencia(agencia);

			ListResult.add(liquidacion);
		}

		return ListResult;
	}


	/*
	 * (non-Javadoc)
	 * @see com.tepsa.sisvyr.model.dao.LiquidacionOficinaDAO#buscarLiquidacionLiquidadas(java.lang.String, java.lang.Integer)
	 */
	@Override
	public List<Liquidacion> buscarLiquidacionLiquidadas(String fecha,Integer idAgencia) {
		String sql ="SELECT l.liquidacion_id, l.n_anio, l.agencia_id, l.usuario_id, l.n_estliq, l.c_nomusu as NombreUsuario, " +//0-5
					"a.c_denominacion as NombreAgencia, "+//6-6
					"u.c_apepat as ApPaterno, u.c_apemat as ApMarterno, u.c_nombre as Nombres, "+//7-9
					"l.d_fecliq as FechaLiquidacion, l.audfecmod as FechaMod, "+//10-11
					"l.audfecins as FechaInsercion, l.audipinse as ipinsercion, l.audusuins as susuarioInsercion "+//12-14
			"FROM vrtliquidacion l "+
			"INNER JOIN vrmagencia a ON (a.agencia_id=l.agencia_id) "+
			"INNER JOIN vrmusuario u ON (u.usuario_id=l.usuario_id) "+
			"WHERE l.agencia_id="+idAgencia+" AND l.n_estliq=0 AND l.c_estreg='A' "+
			"AND L.Liquidacion_Id  in  (SELECT lo.liquidacion_id "+
										"FROM vrtliqofi lo "+
	                  				    "WHERE lo.d_fecliq=to_date('"+fecha+"','dd/MM/yyyy') AND lo.c_estreg='A') "+
			"ORDER BY l.d_FecLiq";

			log.info(sql);

			List<?> result = getSession().createSQLQuery(sql).list();
			List<Liquidacion> ListResult = new ArrayList<>();
			for(int i=0; i<result.size(); i++){
			Object[] obj = (Object[]) result.get(i);

			Agencia agencia = new Agencia();
			agencia.setId(((BigDecimal) obj[2]).intValue());
			agencia.setDenominacion(obj[6].toString());

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

			liquidacion.setUsuario(usuario);
			liquidacion.setAgencia(agencia);

			ListResult.add(liquidacion);
			}

			return ListResult;
	}

	@Override
	public void guardar(LiquidacionOficina liquidacionOficina) throws Exception {
		super.save(liquidacionOficina);

	}


	@Override
	public void inactivar(Long id) {
		super.inactivate(LiquidacionOficina.class, id);
	}


}
