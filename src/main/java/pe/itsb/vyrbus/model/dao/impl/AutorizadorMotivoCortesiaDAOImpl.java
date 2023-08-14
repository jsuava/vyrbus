/**
 * Proyecto		: SISVYR
 * Sistema		: Sistema de Ventas y Reservas
 * Descripción	: Objeto que implementa los metodos de acceso a datos de la tabla Autorizador Motivo Cortesia VRTAUTCOR_MOTCOR.
 * Autor		: José Avalos
 * Fecha		: 03/04/2014
 */
package pe.itsb.vyrbus.model.dao.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

import pe.itsb.vyrbus.model.bean.AutorizadorCortesia;
import pe.itsb.vyrbus.model.bean.AutorizadorMotivoCortesia;
import pe.itsb.vyrbus.model.bean.MotivoCortesia;
import pe.itsb.vyrbus.model.dao.AutorizadorMotivoCortesiaDAO;
import pe.itsb.vyrbus.service.util.Constantes;

/**
 * @author Jose
 *
 */
@SuppressWarnings("unchecked")
public class AutorizadorMotivoCortesiaDAOImpl extends GenericDAOImpl implements
		AutorizadorMotivoCortesiaDAO {

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.model.dao.IAutorizadorMotivoCortesiaDAO#buscarPorEstadoRegistro(java.lang.String, java.lang.String)
	 */
	@Override
	public ArrayList<AutorizadorMotivoCortesia> buscarPorEstadoRegistro(String estado, String criterioOrden) {
		// TODO Auto-generated method stub
		return (ArrayList<AutorizadorMotivoCortesia>)super.findByEstadoRegistro(AutorizadorMotivoCortesia.class, estado, criterioOrden);
	}

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.model.dao.IAutorizadorMotivoCortesiaDAO#buscarPorX(java.util.TreeMap, java.util.List)
	 */
	@Override
	public ArrayList<AutorizadorMotivoCortesia> buscarPorX(TreeMap<String, Object> criteriosBusqueda, List<String> criteriosOrdenar) {
		// TODO Auto-generated method stub
		return (ArrayList<AutorizadorMotivoCortesia>)super.findByX(AutorizadorMotivoCortesia.class, criteriosBusqueda, criteriosOrdenar);
	}

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.model.dao.IAutorizadorMotivoCortesiaDAO#buscarPorId(java.lang.Long)
	 */
	@Override
	public AutorizadorMotivoCortesia buscarPorId(Long id) {
		// TODO Auto-generated method stub
		return (AutorizadorMotivoCortesia)super.findById(AutorizadorMotivoCortesia.class, id);
	}

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.model.dao.IAutorizadorMotivoCortesiaDAO#guardar(com.tepsa.sisvyr.model.bean.AutorizadorMotivoCortesia)
	 */
	@Override
	public void guardar(AutorizadorMotivoCortesia autorizadorMotivoCortesia) {
		// TODO Auto-generated method stub
		super.save(autorizadorMotivoCortesia);
	}

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.model.dao.IAutorizadorMotivoCortesiaDAO#actualizar(com.tepsa.sisvyr.model.bean.AutorizadorMotivoCortesia)
	 */
	@Override
	public void actualizar(AutorizadorMotivoCortesia autorizadorMotivoCortesia) {
		// TODO Auto-generated method stub
		super.update(autorizadorMotivoCortesia);
	}

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.model.dao.IAutorizadorMotivoCortesiaDAO#inactivar(java.lang.Long)
	 */
	@Override
	public void inactivar(Long id) {
		// TODO Auto-generated method stub
		super.inactivate(AutorizadorMotivoCortesia.class, id);
	}

	/*
	 * (non-Javadoc)
	 * @see com.tepsa.sisvyr.model.dao.AutorizadorMotivoCortesiaDAO#inactivar(com.tepsa.sisvyr.model.bean.AutorizadorCortesia)
	 */
	@Override
	public void inactivar(AutorizadorCortesia autorizadorCortesia){
		String sql = "UPDATE vrtautcor_motcor SET c_estreg='"+Constantes.VALUE_INACTIVO+"' WHERE autcor_id="+autorizadorCortesia.getId();
		getSession().createSQLQuery(sql).executeUpdate();
	}
	/*
	 * (non-Javadoc)
	 * @see com.tepsa.sisvyr.model.dao.AutorizadorMotivoCortesiaDAO#buscarMotivosCortesia(java.lang.Long)
	 */
	@Override
	public List<MotivoCortesia> buscarMotivosCortesia(Long idPersonal){
		String sql = "SELECT mc.motcor_id, mc.c_denominacion "
				+ "FROM vrtautcor_motcor amc "
				+ "INNER JOIN vrtautcor ac ON ac.autcor_id=amc.autcor_id "
				+ "INNER JOIN vrmmotcor mc ON mc.motcor_id=amc.motcor_id "
				+ "WHERE ac.personal_id="+idPersonal+" AND amc.c_estreg='A' "
				+ "ORDER BY mc.c_denominacion";

		List<?> result = getSession().createSQLQuery(sql).list();
		List<MotivoCortesia> lstResult = new ArrayList<>();
		for(int i=0; i<result.size(); i++){
			Object[]obj = (Object[])result.get(i);
			MotivoCortesia motivoCortesia = new MotivoCortesia();
			motivoCortesia.setId(((BigDecimal)obj[0]).intValue());
			motivoCortesia.setDenominacion(obj[1].toString());
			lstResult.add(motivoCortesia);
		}
		return lstResult;
	}
}
