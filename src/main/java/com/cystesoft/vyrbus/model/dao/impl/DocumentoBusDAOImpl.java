/**
 * Proyecto		: SISVYR
 * Sistema		: Sistema de Ventas y Reservas
 * Descripción	:
 * Autor		: José Sullo Avalos
 * Fecha		: 27/08/2012
 */
package com.cystesoft.vyrbus.model.dao.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

import com.cystesoft.vyrbus.model.bean.DocumentoBus;
import com.cystesoft.vyrbus.model.dao.DocumentoBusDAO;

/**
 * @author JA
 * @since JDK1.6
 */
@SuppressWarnings("unchecked")
public class DocumentoBusDAOImpl extends GenericDAOImpl implements DocumentoBusDAO {

	@Override
	public ArrayList<DocumentoBus> buscarPorEstadoRegistro(String estado, String criterioOrden) {
		return (ArrayList<DocumentoBus>) super.findByEstadoRegistro(DocumentoBus.class, estado, criterioOrden) ;
	}

	@Override
	public ArrayList<DocumentoBus> buscarPorX(TreeMap<String, Object> criteriosBusqueda, List<String> criteriosOrdenar) {
		return (ArrayList<DocumentoBus>) super.findByX(DocumentoBus.class, criteriosBusqueda, criteriosOrdenar);
	}

	@Override
	public DocumentoBus buscarPorId(Long id) {
		return (DocumentoBus) super.findById(DocumentoBus.class, id);
	}

	@Override
	public void guardar(DocumentoBus documentoBus) {
		super.save(documentoBus);
	}

	@Override
	public void actualizar(DocumentoBus documentoBus) {
		super.update(documentoBus);
	}

	@Override
	public void inactivar(Long id) {
		super.inactivate(DocumentoBus.class, id);
	}


}
