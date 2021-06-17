/**
 * Proyecto		: SISVYR
 * Sistema		: Sistema de Ventas y Reservas
 * Descripción	: 
 * Autor		: jM
 * Fecha		: 04/05/2012
 */
package com.cystesoft.vyrbus.model.dao.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

import com.cystesoft.vyrbus.model.bean.TipoDocumento;
import com.cystesoft.vyrbus.model.dao.TipoDocumentoDAO;

/**
 *
 * @author jM
 * @since JDK1.6
 */
@SuppressWarnings("unchecked")
public class TipoDocumentoDAOImpl extends GenericDAOImpl implements TipoDocumentoDAO {

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.dao.TipoDocumentoDAO#buscarPorEstadoRegistro(java.lang.String)
	 */
	@Override
	public ArrayList<TipoDocumento> buscarPorEstadoRegistro(String estado, String criterioOrden) {
		return (ArrayList<TipoDocumento>) super.findByEstadoRegistro(TipoDocumento.class, estado, criterioOrden);
	}

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.dao.TipoDocumentoDAO#buscarPorX(java.util.TreeMap)
	 */
	@Override
	public ArrayList<TipoDocumento> buscarPorX(TreeMap<String, Object> criteriosBusqueda, List<String> criteriosOrdenar) {
		return (ArrayList<TipoDocumento>) super.findByX(TipoDocumento.class, criteriosBusqueda, criteriosOrdenar);
	}

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.dao.TipoDocumentoDAO#buscarPorId(java.lang.Long)
	 */
	@Override
	public TipoDocumento buscarPorId(Long id) {
		return (TipoDocumento) super.findById(TipoDocumento.class, id);
	}

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.dao.TipoDocumentoDAO#guardar(com.tepsa.sisvyr.domain.TipoDocumento)
	 */
	@Override
	public void guardar(TipoDocumento tipoDocumento) {
		super.save(tipoDocumento);
	}

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.dao.TipoDocumentoDAO#actualizar(com.tepsa.sisvyr.domain.TipoDocumento)
	 */
	@Override
	public void actualizar(TipoDocumento tipoDocumento) {
		super.update(tipoDocumento);
	}

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.dao.TipoDocumentoDAO#inactivar(java.lang.Long)
	 */
	@Override
	public void inactivar(Long id) {
		super.inactivate(TipoDocumento.class, id);
	}
}