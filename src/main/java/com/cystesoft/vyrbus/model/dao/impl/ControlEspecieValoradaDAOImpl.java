/**
 * Proyecto		: SISVYR
 * Sistema		: Sistema de Ventas y Reservas
 * Descripci�n	:
 * Autor		: Jos� Sullo Avalos
 * Fecha		: 17/09/2012
 */
package com.cystesoft.vyrbus.model.dao.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.TreeMap;

import com.cystesoft.vyrbus.model.bean.Agencia;
import com.cystesoft.vyrbus.model.bean.CanalVenta;
import com.cystesoft.vyrbus.model.bean.ControlEspecieValorada;
import com.cystesoft.vyrbus.model.bean.ControlEspecieValoradaID;
import com.cystesoft.vyrbus.model.bean.HistoricoControlEspecieValorada;
import com.cystesoft.vyrbus.model.bean.HistoricoControlEspecieValoradaID;
import com.cystesoft.vyrbus.model.bean.TipoComprobante;
import com.cystesoft.vyrbus.model.bean.UsuarioHardware;
import com.cystesoft.vyrbus.model.dao.ControlEspecieValoradaDAO;
import com.cystesoft.vyrbus.service.locator.ServiceLocator;
import com.cystesoft.vyrbus.service.util.Constantes;

/**
 *
 * @author Jos� Sullo Avalos
 * @since JDK1.6
 */
@SuppressWarnings("unchecked")
public class ControlEspecieValoradaDAOImpl extends GenericDAOImpl implements ControlEspecieValoradaDAO {

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.dao.ControlEspecieValoradaDAO#buscarPorX(java.util.TreeMap, java.util.List)
	 */
	@Override
	public ArrayList<ControlEspecieValorada> buscarPorX(TreeMap<String, Object> criteriosBusqueda, List<String> criteriosOrdenar) throws Exception {
		try{
			ArrayList<ControlEspecieValorada> controlEspecies = (ArrayList<ControlEspecieValorada>) super.findByX(ControlEspecieValorada.class, criteriosBusqueda, criteriosOrdenar);
//			if(controlEspecies.size()==1){
//				for(ControlEspecieValorada especie : controlEspecies){
//					if(especie.getCorrelativoActual().longValue() <= especie.getCorrelativoFin().longValue()){
//						controlEspecies = new ArrayList<ControlEspecieValorada>();
//						controlEspecies.add(especie);
//					}else{
//						throw new EspecieValoradaNotAvailableException();
//					}
//				}
//			}
 			return controlEspecies;
//		}catch(EspecieValoradaNotAvailableException evnaex){
//			throw new EspecieValoradaNotAvailableException();
		}catch(Exception ex){
			throw new Exception(ex);
		}
	}

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.dao.ControlEspecieValoradaDAO#guardar(com.tepsa.sisvyr.domain.ControlEspecieValorada)
	 */
	@Override
	public int guardar(ControlEspecieValorada controlEspecieValorada)throws Exception {
		super.save(controlEspecieValorada);
		return 0;
	}

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.dao.ControlEspecieValoradaDAO#actualizar(com.tepsa.sisvyr.domain.ControlEspecieValorada)
	 */
	@Override
	public int actualizar(ControlEspecieValorada controlEspecieValorada)throws Exception {

		/*Gurada el historico*/
		HistoricoControlEspecieValoradaID hControlEspecieValoradaID= new HistoricoControlEspecieValoradaID();
		hControlEspecieValoradaID.setIdTipoComprobante(controlEspecieValorada.getTipoComprobante().getId());
		hControlEspecieValoradaID.setIdUsuarioHardware(controlEspecieValorada.getUsuarioHardware().getId());

		HistoricoControlEspecieValorada hControlEspecieValorada= new HistoricoControlEspecieValorada();
		hControlEspecieValorada.setHistoricoControlEspecieValoradaID(hControlEspecieValoradaID);
		hControlEspecieValorada.setTipoComprobante(controlEspecieValorada.getTipoComprobante());
		hControlEspecieValorada.setUsuarioHardware(controlEspecieValorada.getUsuarioHardware());
		hControlEspecieValorada.setSerie(controlEspecieValorada.getSerie());
		hControlEspecieValorada.setCorrelativoInicio(controlEspecieValorada.getCorrelativoInicio());
		hControlEspecieValorada.setCorrelativoFin(controlEspecieValorada.getCorrelativoFin());
		hControlEspecieValorada.setCorrelativoActual(controlEspecieValorada.getCorrelativoActual());
		hControlEspecieValorada.setEstadoRegistro(Constantes.VALUE_ACTIVO);
		hControlEspecieValorada.setUsuarioInsercion(controlEspecieValorada.getUsuarioInsercion());
		hControlEspecieValorada.setUsuarioModificacion(controlEspecieValorada.getUsuarioModificacion());
		hControlEspecieValorada.setIpInsercion(controlEspecieValorada.getIpInsercion());
		hControlEspecieValorada.setIpModificacion(controlEspecieValorada.getIpModificacion());
		ServiceLocator.getHistoricoControlEspecieValoradaManager().guardar(hControlEspecieValorada);

		super.update(controlEspecieValorada);

		return Constantes.CORRECT;
	}

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.model.dao.ControlEspecieValoradaDAO#buscarUltimoCorrelativoEspecieValorada(java.lang.Integer, java.lang.Integer, java.lang.String)
	 */
	@Override
	public ControlEspecieValorada buscarUltimoCorrelativoEspecieValorada(Integer idTipCom, Integer idUsuHar, String serie) throws Exception {
		String hql = "FROM ControlEspecieValorada cev WHERE cev.tipoComprobante.id="+idTipCom+" AND cev.usuarioHardware.id="+idUsuHar+" AND " +
				"cev.serie='"+serie+"' AND cev.estadoRegistro='"+Constantes.VALUE_ACTIVO+"' ";
		log.info(hql);
		ControlEspecieValorada obj = (ControlEspecieValorada)getSession().createQuery(hql).uniqueResult();
		return obj;
	}

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.model.dao.ControlEspecieValoradaDAO#actualizarCorrelativoEspecieValorada(java.lang.Integer, java.lang.Integer, java.lang.String, int)
	 */
	@Override
	public int actualizarCorrelativoEspecieValorada(Integer idTipCom, Integer idUsuHar, String serie, long correlativo) throws Exception {
		String hql = "UPDATE ControlEspecieValorada SET correlativoActual="+correlativo+" " +
				"WHERE tipoComprobante.id="+idTipCom+" AND usuarioHardware.id="+idUsuHar+" AND " +
				"serie='"+serie+"' AND estadoRegistro='"+Constantes.VALUE_ACTIVO+"' ";
		log.info(hql);

		return getSession().createQuery(hql).executeUpdate();
	}

	@Override
	public void inactivar(ControlEspecieValoradaID controlEspecieValoradaID)throws Exception {
		String hql="UPDATE ControlEspecieValorada SET estadoRegistro='I' "+
					"WHERE tipoComprobante.id="+controlEspecieValoradaID.getIdTipoComprobante()+" AND usuarioHardware.id="+controlEspecieValoradaID.getIdUsuarioHardware()+"  " +
					" AND  estadoRegistro='"+ Constantes.VALUE_ACTIVO+ "' ";
		log.info(hql);

		getSession().createQuery(hql).executeUpdate();

	}

	/*
	 * (non-Javadoc)
	 * @see com.tepsa.sisvyr.model.dao.ControlEspecieValoradaDAO#buscarEspecieValoradas(java.lang.Integer, java.lang.Integer)
	 */
	@Override
	public List<ControlEspecieValorada> buscarEspecieValoradas(Integer idAgencia, Integer idTipoComprobante, Integer idUsuarioHarware){
		String sql="SELECT TC.TIPCOM_ID,TC.C_DENOMINACION, UH.USUHARD_ID,UH.C_DESCRIPCION, CEV.C_SERIE, CEV.N_CORINI, "+ //0-5
					       "CEV.N_CORFIN, CEV.N_CORACTUAL, A.AGENCIA_ID, A.C_NOMCOR" +//6-9
					       ", CEV.AUDFECINS, CEV.AUDUSUINS, CEV.AUDIPINSE, UH.CANVEN_ID, CEV.N_FORMATO, CEV.C_CORSEQ "+ //10-14
					"FROM VRTCTRLESPVAL CEV "+
					"INNER JOIN VRTUSUHARD UH ON (UH.USUHARD_ID=CEV.USUHARD_ID) "+
					"INNER JOIN VRMAGENCIA A ON (A.AGENCIA_ID=UH.AGENCIA_ID) "+
					"INNER JOIN VRMTIPCOM TC ON (TC.TIPCOM_ID=CEV.TIPCOM_ID) " +
					"WHERE CEV.C_ESTREG='A' " +
						"AND A.TIPAGE_ID="+Constantes.ID_TIPAGE_TEPSA+" "; //Solo recupera los de TIPO AGENCIA TEPSA, mas no agencias de viaje, corporativos, ect.

		if(idAgencia!=null)
			sql+=" AND A.AGENCIA_ID="+idAgencia;
		if(idTipoComprobante!=null)
			sql+=" AND TC.TIPCOM_ID="+idTipoComprobante;
		if(idUsuarioHarware!=null)
			sql+=" AND CEV.USUHARD_ID="+idUsuarioHarware;
		sql+=" ORDER BY A.C_NOMCOR, TC.C_DENOMINACION";

		log.info(sql);
		List<?> result = getSession().createSQLQuery(sql).list();
		List<ControlEspecieValorada>lstResul= new ArrayList<>();

		for(int i=0; i<result.size(); i++){
			Object[] obj = (Object[])result.get(i);

			ControlEspecieValorada controlEspecieValorada= new ControlEspecieValorada();
			ControlEspecieValoradaID controlEspecieValoradaID=new ControlEspecieValoradaID();
			UsuarioHardware usuarioHardware= new UsuarioHardware();
			Agencia agencia= new Agencia();
			TipoComprobante tipoComprobante=new TipoComprobante();

			CanalVenta canalVenta=new CanalVenta();
			canalVenta.setId(((BigDecimal)obj[13]).intValue());

			tipoComprobante.setId(((BigDecimal)obj[0]).intValue());
			tipoComprobante.setDenominacion(obj[1].toString());
			controlEspecieValorada.setSerie(obj[4].toString());
			controlEspecieValorada.setCorrelativoInicio(((BigDecimal)obj[5]).longValue());
			controlEspecieValorada.setCorrelativoFin(((BigDecimal)obj[6]).longValue());
			controlEspecieValorada.setCorrelativoActual(((BigDecimal)obj[7]).longValue());
			agencia.setId(((BigDecimal)obj[8]).intValue());
			agencia.setNombreCorto(obj[9].toString());
			usuarioHardware.setId(((BigDecimal)obj[2]).intValue());
			usuarioHardware.setDescripcion(obj[3].toString());
			usuarioHardware.setAgencia(agencia);
			usuarioHardware.setCanalVenta(canalVenta);
			controlEspecieValoradaID.setIdTipoComprobante(tipoComprobante.getId());
			controlEspecieValoradaID.setIdUsuarioHardware(usuarioHardware.getId());

			controlEspecieValorada.setControlEspecieValoradaID(controlEspecieValoradaID);
			controlEspecieValorada.setTipoComprobante(tipoComprobante);
			controlEspecieValorada.setUsuarioHardware(usuarioHardware);
			controlEspecieValorada.setFechaInsercion(obj[10]!=null? (Date)obj[10]: null);
			controlEspecieValorada.setUsuarioInsercion(obj[11]!=null? obj[11].toString():" ");
			controlEspecieValorada.setIpInsercion(obj[12]!=null? obj[12].toString():" ");
			controlEspecieValorada.setFormato(((BigDecimal)obj[14]).intValue());
			controlEspecieValorada.setSecuenciador(obj[15]!=null?obj[15].toString():"");

			lstResul.add(controlEspecieValorada);
		}
		return lstResul;
	}

	/*
	 * (non-Javadoc)
	 * @see com.tepsa.sisvyr.model.dao.ControlEspecieValoradaDAO#validaEVOtrasCajas(java.lang.Integer, java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	public List<ControlEspecieValorada> validaEVOtrasCajas(Integer idTipoComprobante,String serie, String inicial, String Final){
		String hql="FROM ControlEspecieValorada WHERE serie = ? AND correlativoInicio >= ? AND correlativoInicio <= ? AND estadoRegistro='A' " +
					"OR serie = ? AND correlativoFin >= ? AND correlativoFin <= ? AND estadoRegistro='A' ";
		List<ControlEspecieValorada>lstResul=
				getSession().createQuery(hql).setString(0, serie).setLong(1, Long.valueOf(inicial)).setLong(2, Long.valueOf(Final))
		.setString(3, serie).setLong(4, Long.valueOf(inicial)).setLong(5, Long.valueOf(Final)).list();

		return lstResul;

	}

	/*
	 * (non-Javadoc)
	 * @see com.tepsa.sisvyr.model.dao.ControlEspecieValoradaDAO#buscarEspecieValoradaPorAgencia(java.lang.Long)
	 */
	@Override
	public List<ControlEspecieValorada> buscarEspecieValoradaPorAgencia(Integer idAgencia)throws Exception{
		String sql = "SELECT uh.usuhard_id, uh.c_descripcion, cev.c_serie, cev.n_coractual, cev.c_estreg, cv.canven_id, cv.c_denominacion " +
				"FROM vrtctrlespval cev " +
				"INNER JOIN vrtusuhard uh ON uh.usuhard_id=cev.usuhard_id " +
				"INNER JOIN vrmcanven cv ON cv.canven_id=uh.canven_id " +
				"WHERE uh.agencia_id="+idAgencia+" AND cev.c_estreg='"+Constantes.VALUE_ACTIVO+"' " +
				"ORDER BY uh.c_descripcion, cev.c_serie";

		log.info(sql);

		List<?> result = getSession().createSQLQuery(sql).list();
		List<ControlEspecieValorada> lstResult = new ArrayList<>();
		for(int i=0; i<result.size(); i++){
			Object[] obj = (Object[])result.get(i);
			ControlEspecieValorada controlEspecieValorada = new ControlEspecieValorada();
			UsuarioHardware usuarioHardware = new UsuarioHardware();
			usuarioHardware.setId(((BigDecimal)obj[0]).intValue());
			usuarioHardware.setDescripcion(obj[1].toString());
			controlEspecieValorada.setUsuarioHardware(usuarioHardware);
			controlEspecieValorada.setSerie(obj[2].toString());
			controlEspecieValorada.setCorrelativoActual(((BigDecimal)obj[3]).longValue());
			CanalVenta canalVenta = new CanalVenta();
			canalVenta.setId(((BigDecimal)obj[5]).intValue());
			canalVenta.setDenominacion(obj[6].toString());
			usuarioHardware.setCanalVenta(canalVenta);
			lstResult.add(controlEspecieValorada);
		}
		return lstResult;
	}

	@Override
	public void generarSecuenciador(String nameSequence, Long inicio) {
		String sql = "CREATE SEQUENCE "+nameSequence+ " START WITH "+inicio+" INCREMENT BY 1 NOMINVALUE NOMAXVALUE NOCACHE ORDER";

		log.info(sql);

		getSession().createSQLQuery(sql).executeUpdate();
	}

	/* (non-Javadoc)
	 * @see com.cystesoft.vyrbus.model.dao.ControlEspecieValoradaDAO#eliminarSecuenciador(java.lang.String)
	 */
	@Override
	public void eliminarSecuenciador(String nameSequence) {
		String sql = "DROP SEQUENCE "+nameSequence;
		log.info(sql);

		getSession().createSQLQuery(sql).executeUpdate();
	}

	/* (non-Javadoc)
	 * @see com.cystesoft.vyrbus.model.dao.ControlEspecieValoradaDAO#ejecutarSecuenciador(com.cystesoft.vyrbus.model.bean.ControlEspecieValorada)
	 */
	@Override
	public ControlEspecieValorada ejecutarSecuenciador(ControlEspecieValorada controlEspecieValorada) throws Exception {
		String sql="SELECT "+controlEspecieValorada.getSecuenciador()+".NEXTVAL correlativo FROM DUAL";
		log.info("SQL: "+sql);
		Object object= getSession().createSQLQuery(sql).uniqueResult();
		Long correlativo=((BigDecimal)object).longValue();
		controlEspecieValorada.setCorrelativoActual(correlativo);

		return controlEspecieValorada;
	}
}
