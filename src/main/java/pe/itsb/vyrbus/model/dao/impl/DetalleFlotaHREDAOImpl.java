/**
 * Proyecto		: SISVYR
 * Sistema		: Sistema de Ventas y Reservas
 * Descripción	:
 * Autor		: José Abanto
 * Fecha		: 27/08/2014
 * Hora			: 12:11:57
 */
package pe.itsb.vyrbus.model.dao.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

import pe.itsb.vyrbus.model.bean.DetalleFlotaHRE;
import pe.itsb.vyrbus.model.dao.DetalleFlotaHREDAO;

/**
 * @author JABANTO
 *
 */
public class DetalleFlotaHREDAOImpl extends GenericDAOImpl implements DetalleFlotaHREDAO {

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.model.dao.DetalleFlotaHREDAO#guardar(com.tepsa.sisvyr.model.bean.DetalleFlotaHRE)
	 */
	@Override
	public void guardar(DetalleFlotaHRE detalleFlotaHRE) throws Exception {
		// TODO Auto-generated method stub
		super.save(detalleFlotaHRE);
	}

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.model.dao.DetalleFlotaHREDAO#actualizar(com.tepsa.sisvyr.model.bean.DetalleFlotaHRE)
	 */
	@Override
	public void actualizar(DetalleFlotaHRE detalleFlotaHRE) throws Exception {
		// TODO Auto-generated method stub
		super.update(detalleFlotaHRE);
	}

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.model.dao.DetalleFlotaHREDAO#buscarPorX(java.util.TreeMap, java.util.List)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public ArrayList<DetalleFlotaHRE> buscarPorX(TreeMap<String, Object> criteriosBusqueda,List<String> criteriosOrdenar) {
		// TODO Auto-generated method stub
		return (ArrayList<DetalleFlotaHRE>) super.findByX(DetalleFlotaHRE.class, criteriosBusqueda, criteriosOrdenar);
	}



}
