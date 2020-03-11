package com.cystesoft.vyrbus.model.dao.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

import com.cystesoft.vyrbus.model.bean.MotivoTemporadaAlta;
import com.cystesoft.vyrbus.model.dao.MotivoTemporadaAltaDAO;

/**
 * 
 * @author JABANTO
 *
 */
@SuppressWarnings("unchecked")
public class MotivoTemporadaAltaDAOImpl extends GenericDAOImpl implements MotivoTemporadaAltaDAO {

	@Override
	public ArrayList<MotivoTemporadaAlta> buscarPorEstadoRegistro(String estado, String criterioOrden) {
		return (ArrayList<MotivoTemporadaAlta>) super.findByEstadoRegistro(MotivoTemporadaAlta.class, estado, criterioOrden);
	}

	@Override
	public ArrayList<MotivoTemporadaAlta> buscarPorX(TreeMap<String, Object> criteriosBusqueda,List<String> criteriosOrdenar) {
		return (ArrayList<MotivoTemporadaAlta>) super.findByX(MotivoTemporadaAlta.class, criteriosBusqueda, criteriosOrdenar);
	}

	@Override
	public MotivoTemporadaAlta buscarPorId(Long id) {
		return (MotivoTemporadaAlta) super.findById(MotivoTemporadaAlta.class, id);
	}

	@Override
	public void guardar(MotivoTemporadaAlta motivoTemporadaAlta) {
		super.save(motivoTemporadaAlta);
	}

	@Override
	public void actualizar(MotivoTemporadaAlta motivoTemporadaAlta) {
		super.update(motivoTemporadaAlta);
	}

	@Override
	public void inactivar(Long id) {
		super.inactivate(MotivoTemporadaAlta.class, id);
	}

}
