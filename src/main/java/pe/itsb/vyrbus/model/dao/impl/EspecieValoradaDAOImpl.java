/**
 * Proyecto		: SISVYR
 * Sistema		: Sistema de Ventas y Reservas
 * Descripción	:
 * Autor		: jM
 * Fecha		: 04/05/2012
 */
package pe.itsb.vyrbus.model.dao.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

import pe.itsb.vyrbus.model.bean.EspecieValorada;
import pe.itsb.vyrbus.model.dao.EspecieValoradaDAO;
import pe.itsb.vyrbus.service.util.Constantes;

/**
 *
 * @author JABANTO
 * @since JDK1.6
 */
@SuppressWarnings("unchecked")
public class EspecieValoradaDAOImpl extends GenericDAOImpl implements EspecieValoradaDAO {

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.dao.EspecieValoradaDAO#buscarPorEstadoRegistro(java.lang.String)
	 */
	@Override
	public ArrayList<EspecieValorada> buscarPorEstadoRegistro(String estado, String criterioOrden) {
		return (ArrayList<EspecieValorada>) super.findByEstadoRegistro(EspecieValorada.class, estado, criterioOrden);
	}

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.dao.EspecieValoradaDAO#buscarPorX(java.util.TreeMap)
	 */
	@Override
	public ArrayList<EspecieValorada> buscarPorX(TreeMap<String, Object> criteriosBusqueda, List<String> criteriosOrdenar) {
		return (ArrayList<EspecieValorada>) super.findByX(EspecieValorada.class, criteriosBusqueda, criteriosOrdenar);
	}

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.dao.EspecieValoradaDAO#buscarPorId(java.lang.Long)
	 */
	@Override
	public EspecieValorada buscarPorId(Long id) {
		return (EspecieValorada) super.findById(EspecieValorada.class, id);
	}

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.dao.EspecieValoradaDAO#guardar(com.tepsa.sisvyr.domain.EspecieValorada)
	 */
	@Override
	public void guardar(EspecieValorada especieValorada) {
		super.save(especieValorada);
	}

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.dao.EspecieValoradaDAO#actualizar(com.tepsa.sisvyr.domain.EspecieValorada)
	 */
	@Override
	public void actualizar(EspecieValorada especieValorada) {
		super.update(especieValorada);
	}

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.dao.EspecieValoradaDAO#inactivar(java.lang.Long)
	 */
	@Override
	public void inactivar(Long id) {
		super.inactivate(EspecieValorada.class, id);
	}

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.model.dao.EspecieValoradaDAO#buscarUltimaSerie(java.lang.Integer)
	 */
	@Override
	public String buscarUltimaSerieUtilAge(Integer idTipoComprobante) {
		String sql="";
		if(idTipoComprobante.intValue()==Constantes.ID_TIPCOM_BOLETA_VENTA ||
				idTipoComprobante.intValue()==Constantes.ID_TIPCOM_FACTURA ||
				idTipoComprobante.intValue()==Constantes.ID_TIPCOM_NOTA_CREDITO ||
				idTipoComprobante.intValue()==Constantes.ID_TIPCOM_NOTA_DEBITO ){

			sql="SELECT MAX(ev.c_serie) FROM VRMESPVAL EV WHERE ev.tipcom_id="+idTipoComprobante+" AND ev.c_estreg='A'";
		}else
			sql="SELECT MAX(ev.n_serie) FROM VRMESPVAL EV WHERE ev.tipcom_id="+idTipoComprobante+" AND ev.c_estreg='A'";
		log.info(sql);

		Object ultimaSerie = getSession().createSQLQuery(sql).uniqueResult();
		String numeroSerie="";

		if(ultimaSerie!=null){
			if(ultimaSerie instanceof Integer)
				numeroSerie=String.valueOf(((BigDecimal)ultimaSerie).intValue());
			else
				numeroSerie=ultimaSerie.toString();

			if (idTipoComprobante.intValue()==Constantes.ID_TIPCOM_NOTA_CREDITO || idTipoComprobante.intValue()==Constantes.ID_TIPCOM_NOTA_DEBITO)
				numeroSerie="X"+numeroSerie; //Le antepone la "X" solamente para el algoritmo lo pueda interpretar al momento de generar la serie de forma automatica
		}else{
			if(idTipoComprobante.intValue()==Constantes.ID_TIPCOM_BOLETA_VENTA){
				numeroSerie="BA00";
			}else if(idTipoComprobante.intValue()==Constantes.ID_TIPCOM_FACTURA){
				numeroSerie="FA00";
			}else if (idTipoComprobante.intValue()==Constantes.ID_TIPCOM_NOTA_CREDITO || idTipoComprobante.intValue()==Constantes.ID_TIPCOM_NOTA_DEBITO){
				numeroSerie="XA00"; //Le antepone la "X" solamente para el algoritmo lo pueda interpretar al momento de generar la serie de forma automatica
			}else{
				numeroSerie="0";
			}
		}

		return numeroSerie;

//		if(ultimaSerie==null){
//			numeroSerie="0";
//			ultimaSerie=0;
//			return (Integer) ultimaSerie;
//		}else{
//			return ((BigDecimal)ultimaSerie).intValue();
//		}
	}

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.model.dao.EspecieValoradaDAO#actualizarCorrelativoEspecieValorada(java.lang.Integer, java.lang.Integer, java.lang.String, long)
	 */
	@Override
	public void actualizarCorrelativoEspecieValorada(Integer idTipCom,Integer idAgencia, String serie, long correlativo) throws Exception {
		// TODO Auto-generated method stub
//		correlativo=correlativo+1;

		if(idTipCom.intValue()==Constantes.ID_TIPCOM_BOLETA_VENTA || idTipCom.intValue()==Constantes.ID_TIPCOM_FACTURA || idTipCom.intValue()==Constantes.ID_TIPCOM_NOTA_CREDITO || idTipCom.intValue()==Constantes.ID_TIPCOM_NOTA_DEBITO){
			String hql = "UPDATE EspecieValorada SET correlativoActual="+correlativo+" " +
					"WHERE tipoComprobante.id="+idTipCom+" AND agencia.id="+idAgencia+" AND "+
					"c_serie='"+serie+"' AND estadoRegistro='"+Constantes.VALUE_ACTIVO+"' ";
			log.info(hql);

			getSession().createQuery(hql).executeUpdate();
		}else{
			int iserie=Integer.parseInt(serie);
			String hql = "UPDATE EspecieValorada SET correlativoActual="+correlativo+" " +
					"WHERE tipoComprobante.id="+idTipCom+" AND agencia.id="+idAgencia+" AND "+
					"serie='"+iserie+"' AND estadoRegistro='"+Constantes.VALUE_ACTIVO+"' ";
			log.info(hql);

			getSession().createQuery(hql).executeUpdate();
		}


	}

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.model.dao.EspecieValoradaDAO#buscarUltimaEspecieRegistrada(java.lang.Integer, java.lang.Integer, java.lang.String)
	 */
	@Override
	public EspecieValorada buscarUltimaEspecieRegistrada(Integer idTipoComprobante, String numeroSerie,Integer idAgencia) throws Exception {
		// TODO Auto-generated method stub
//		String sql="SELECT MAX(ev.n_corfin) as CorrelativoFinal "+
//					"FROM VRMESPVAL ev "+
//					"WHERE ev.tipcom_id="+idTipoComprobante+" " +
//						"AND ev.n_serie='"+numeroSerie+"' " +
//						"AND ev.agencia_id="+idAgencia;

		String sql="SELECT MAX(ev.espval_id) as id "+
				"FROM VRMESPVAL ev "+
				"WHERE ev.tipcom_id="+idTipoComprobante+" " +
					"AND ev.n_serie='"+numeroSerie+"' " +
					"AND ev.agencia_id="+idAgencia;

		Object id=getSession().createSQLQuery(sql).uniqueResult();
		EspecieValorada especieValorada=null;
		if(id!=null)
			especieValorada=buscarPorId(((BigDecimal)id).longValue());



//		Object objCorrelativoFinal=getSession().createSQLQuery(sql).uniqueResult();
//		EspecieValorada especieValorada=null;
//		if(objCorrelativoFinal!=null){
//			especieValorada=new EspecieValorada();
//			especieValorada.setCorrelativoFinal(((BigDecimal)objCorrelativoFinal).longValue());
//		}

		return especieValorada;
	}

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.model.dao.EspecieValoradaDAO#ejcutarSeqCorrelativo(com.tepsa.sisvyr.model.bean.EspecieValorada)
	 */
	@Override
	public EspecieValorada ejecutarSeqCorrelativo(EspecieValorada especieValorada)throws Exception {
		String sql="SELECT "+especieValorada.getNameSecuenciador()+".NEXTVAL correlativo FROM DUAL";
		log.info("SQL: "+sql);
		Object object= getSession().createSQLQuery(sql).uniqueResult();
		Long correlativo=((BigDecimal)object).longValue();
		especieValorada.setCorrelativoActual(correlativo);

		return especieValorada;
	}

}