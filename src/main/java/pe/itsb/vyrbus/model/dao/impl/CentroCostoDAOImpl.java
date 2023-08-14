/**
 * Proyecto		: SISVYR
 * Sistema		: Sistema de Ventas y Reservas
 * Descripción	: Objeto que implementa los metodos de acceso a datos de la tabla Centro de Costo VRMCENCOS.
 * Autor		: José Avalos
 * Fecha		: 16/09/2013
 */
package pe.itsb.vyrbus.model.dao.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

import pe.itsb.vyrbus.model.bean.CentroCosto;
import pe.itsb.vyrbus.model.dao.CentroCostoDAO;

/**
 * @author Jose
 *
 */
@SuppressWarnings("unchecked")
public class CentroCostoDAOImpl extends GenericDAOImpl implements CentroCostoDAO {

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.model.dao.CentroCostoDAO#buscarPorEstadoRegistro(java.lang.String, java.lang.String)
	 */
	@Override
	public ArrayList<CentroCosto> buscarPorEstadoRegistro(String estado, String criterioOrden) throws Exception {
		return (ArrayList<CentroCosto>)super.findByEstadoRegistro(CentroCosto.class, estado, criterioOrden);
	}

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.model.dao.CentroCostoDAO#buscarPorX(java.util.TreeMap, java.util.List)
	 */
	@Override
	public ArrayList<CentroCosto> buscarPorX(TreeMap<String, Object> criteriosBusqueda, List<String> criteriosOrdenar) throws Exception {
		return (ArrayList<CentroCosto>)super.findByX(CentroCosto.class, criteriosBusqueda, criteriosOrdenar);
	}

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.model.dao.CentroCostoDAO#buscarPorId(java.lang.Long)
	 */
	@Override
	public CentroCosto buscarPorId(Long id) throws Exception {
		return (CentroCosto)super.findById(CentroCosto.class, id);
	}

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.model.dao.CentroCostoDAO#guardar(com.tepsa.sisvyr.model.bean.CentroCosto)
	 */
	@Override
	public void guardar(CentroCosto centroCosto) throws Exception {
		super.save(centroCosto);
	}

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.model.dao.CentroCostoDAO#actualizar(com.tepsa.sisvyr.model.bean.CentroCosto)
	 */
	@Override
	public void actualizar(CentroCosto centroCosto) throws Exception {
		super.update(centroCosto);
	}

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.model.dao.CentroCostoDAO#inactivar(java.lang.Long)
	 */
	@Override
	public void inactivar(Long id) throws Exception {
		super.inactivate(CentroCosto.class, id);
	}

}
