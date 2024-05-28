/**
 * Proyecto		: SISVYR
 * Sistema		: Sistema de Ventas y Reservas
 * Descripción	: 
 * Autor		: José Abanto
 * Fecha		: 29/01/2019
 * Hora			: 09:35:31
 */
package pe.itsb.vyrbus.model.dao.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

import pe.itsb.vyrbus.model.bean.TarifaByAsientoPlantillaEncabezado;
import pe.itsb.vyrbus.model.dao.TarifaByAsientoPlantillaEncabezadoDAO;


/**
 * @author Jose Abanto
 *
 */
@SuppressWarnings("unchecked")
public class TarifaByAsientoPlantillaEncabezadoDAOImpl extends GenericDAOImpl implements TarifaByAsientoPlantillaEncabezadoDAO{

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.model.dao.TarifaByAsientoPlantillaEncabezadoDAO#buscarPorEstadoRegistro(java.lang.String, java.lang.String)
	 */
	
	@Override
	public ArrayList<TarifaByAsientoPlantillaEncabezado> buscarPorEstadoRegistro(String estado, String criterioOrden) {
		return (ArrayList<TarifaByAsientoPlantillaEncabezado>) findByEstadoRegistro(TarifaByAsientoPlantillaEncabezado.class, estado, criterioOrden);
	}

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.model.dao.TarifaByAsientoPlantillaEncabezadoDAO#buscarPorX(java.util.TreeMap, java.util.List)
	 */
	@Override
	public ArrayList<TarifaByAsientoPlantillaEncabezado> buscarPorX(TreeMap<String, Object> criteriosBusqueda,List<String> criteriosOrdenar) {
		return (ArrayList<TarifaByAsientoPlantillaEncabezado>) findByX(TarifaByAsientoPlantillaEncabezado.class, criteriosBusqueda, criteriosOrdenar);
	}

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.model.dao.TarifaByAsientoPlantillaEncabezadoDAO#buscarPorId(java.lang.Long)
	 */
	@Override
	public TarifaByAsientoPlantillaEncabezado buscarPorId(Long id) {
		return (TarifaByAsientoPlantillaEncabezado) findById(TarifaByAsientoPlantillaEncabezado.class, id);
	}

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.model.dao.TarifaByAsientoPlantillaEncabezadoDAO#guardar(com.tepsa.sisvyr.model.bean.TarifaByAsientoPlantillaEncabezado)
	 */
	@Override
	public void guardar(TarifaByAsientoPlantillaEncabezado tarifaByAsientoPlantillaEncabezado) {
		save(tarifaByAsientoPlantillaEncabezado);
		
	}

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.model.dao.TarifaByAsientoPlantillaEncabezadoDAO#actualizar(com.tepsa.sisvyr.model.bean.TarifaByAsientoPlantillaEncabezado)
	 */
	@Override
	public void actualizar(TarifaByAsientoPlantillaEncabezado tarifaByAsientoPlantillaEncabezado) {
		// TODO Auto-generated method stub
		update(tarifaByAsientoPlantillaEncabezado);
	}

}
