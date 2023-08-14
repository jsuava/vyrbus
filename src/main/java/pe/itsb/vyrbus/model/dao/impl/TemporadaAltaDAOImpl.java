package pe.itsb.vyrbus.model.dao.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.TreeMap;

import org.zkoss.zk.ui.Executions;

import pe.itsb.vyrbus.model.bean.MotivoTemporadaAlta;
import pe.itsb.vyrbus.model.bean.TemporadaAlta;
import pe.itsb.vyrbus.model.bean.Usuario;
import pe.itsb.vyrbus.model.dao.TemporadaAltaDAO;
import pe.itsb.vyrbus.service.util.Constantes;
import pe.itsb.vyrbus.service.util.UtilData;

/**
 * @author JABANTO
 */
@SuppressWarnings("unchecked")
public class TemporadaAltaDAOImpl extends GenericDAOImpl  implements TemporadaAltaDAO {


	/*
	 * (non-Javadoc)
	 * @see com.tepsa.sisvyr.model.dao.TemporadaAltaDAO#buscarPorX(java.util.TreeMap, java.util.List)
	 */
	@Override
	public ArrayList<TemporadaAlta> buscarPorX(TreeMap<String, Object> criteriosBusqueda,List<String> criteriosOrdenar) {
		return (ArrayList<TemporadaAlta>) super.findByX(TemporadaAlta.class, criteriosBusqueda, criteriosOrdenar);
	}

	/*
	 * (non-Javadoc)
	 * @see com.tepsa.sisvyr.model.dao.TemporadaAltaDAO#buscarPorEstadoRegistro(java.lang.String, java.lang.String)
	 */
	@Override
	public ArrayList<TemporadaAlta> buscarPorEstadoRegistro(String estado,String criterioOrden) {
		return (ArrayList<TemporadaAlta>) super.findByEstadoRegistro(TemporadaAlta.class, estado, criterioOrden);
	}

	/*
	 * (non-Javadoc)
	 * @see com.tepsa.sisvyr.model.dao.TemporadaAltaDAO#guardar(com.tepsa.sisvyr.model.bean.TemporadaAlta)
	 */
	@Override
	public void guardar(TemporadaAlta temporadaAlta) {
		super.save(temporadaAlta);
	}

	/*
	 * (non-Javadoc)
	 * @see com.tepsa.sisvyr.model.dao.TemporadaAltaDAO#actualizar(com.tepsa.sisvyr.model.bean.TemporadaAlta)
	 */
	@Override
	public void actualizar(TemporadaAlta temporadaAlta) {
		super.update(temporadaAlta);
	}

	/*
	 * (non-Javadoc)
	 * @see com.tepsa.sisvyr.model.dao.TemporadaAltaDAO#buscarTemporadaAlta(java.lang.String, java.lang.String)
	 */
	@Override
	public ArrayList<TemporadaAlta> buscarTemporadaAlta(String anio, String mes, String dia){
		String sql="SELECT ta.temalt_id, mta.mottemalt_id, ta.diatemalt, mta.c_nommot "+
					"FROM VRMTEMALT ta "+
					"INNER JOIN VRMMOTTEMALT mta ON (mta.mottemalt_id=ta.mottemalt_id) "+
					"WHERE to_char(ta.diatemalt,'yyyy')='"+anio+ "' AND ta.c_estreg='"+Constantes.VALUE_ACTIVO+"' ";
		if(mes.trim().length()>0)
			sql+=" AND  to_char(ta.diatemalt,'mm')='"+mes+"' ";
		if(dia.trim().length()>0)
			sql+=" AND to_char(ta.diatemalt,'DD')='"+dia+"' ";
		sql+=" ORDER BY ta.diatemalt";


		log.info(sql);
		List<?>result=getSession().createSQLQuery(sql).list();
		ArrayList<TemporadaAlta> lstResult = new ArrayList<>();
		for (Object element : result) {
			Object[] obj = (Object[])element;
			TemporadaAlta temporadaAlta=new TemporadaAlta();
			MotivoTemporadaAlta motivoTemporadaAlta= new MotivoTemporadaAlta();
			motivoTemporadaAlta.setId(((BigDecimal)obj[1]).intValue());
			motivoTemporadaAlta.setNombreMotivo(obj[3].toString());

			temporadaAlta.setId(((BigDecimal)obj[0]).longValue());
			temporadaAlta.setDiaTemporadaAlta((Date)obj[2]);
			temporadaAlta.setMotivoTemporadaAlta(motivoTemporadaAlta);

			lstResult.add(temporadaAlta);
		}
		return lstResult;
	}

	/*
	 * (non-Javadoc)
	 * @see com.tepsa.sisvyr.model.dao.TemporadaAltaDAO#anularTemporadaAlta(java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	public void anularTemporadaAlta(String anio, String mes, String dia)throws Exception {
		TemporadaAlta temporadaAlta= new TemporadaAlta();
		Usuario usuario=(Usuario)Executions.getCurrent().getSession().getAttribute(Constantes.ATRIBUTO_USUARIO);
		UtilData.auditarRegistro(temporadaAlta, true, usuario, Executions.getCurrent());

		String sql="UPDATE VRMTEMALT SET c_estreg='"+Constantes.VALUE_INACTIVO+"', audusumod='"+usuario.getUsuarioModificacion()+"', " +
				   "AUDIPMODI='"+temporadaAlta.getIpModificacion()+"' " +
					"WHERE to_char(diatemalt,'yyyy')='"+anio+"' AND c_estreg='"+Constantes.VALUE_ACTIVO+"' ";
		if(mes.length()>0)
			sql+=" AND to_char(diatemalt,'mm')='"+mes+"' ";
		if(dia.length()>0)
			sql+=" AND to_char(diatemalt,'dd')='"+dia+"' ";

		log.info(sql);
		getSession().createSQLQuery(sql).executeUpdate();
	}


}
