/**
 * Proyecto		: SISVYR
 * Sistema		: Sistema de Ventas y Reservas
 * Descripción	:
 * Autor		: José Abanto
 * Fecha		: 22 jul. 2023
 * Hora			: 22:22:23
 */
package pe.itsb.vyrbus.model.dao.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

import pe.itsb.vyrbus.model.bean.Empresa;
import pe.itsb.vyrbus.model.dao.EmpresaDAO;

/**
 * @author Jose
 *
 */
@SuppressWarnings("unchecked")
public class EmpresaDAOImpl extends GenericDAOImpl implements EmpresaDAO {

	/* (non-Javadoc)
	 * @see pe.itsb.vyrbus.model.dao.EmpresaDAO#buscarPorEstadoRegistro(java.lang.String, java.lang.String)
	 */
	@Override
	public ArrayList<Empresa> buscarPorEstadoRegistro(String estado, String criterioOrden) {
		return (ArrayList<Empresa>)super.findByEstadoRegistro(Empresa.class, estado, criterioOrden);
	}

	/* (non-Javadoc)
	 * @see pe.itsb.vyrbus.model.dao.EmpresaDAO#buscarPorX(java.util.TreeMap, java.util.List)
	 */
	@Override
	public ArrayList<Empresa> buscarPorX(TreeMap<String, Object> criteriosBusqueda, List<String> criteriosOrdenar) {
		return (ArrayList<Empresa>)super.findByX(Empresa.class, criteriosBusqueda, criteriosOrdenar);
	}

	/* (non-Javadoc)
	 * @see pe.itsb.vyrbus.model.dao.EmpresaDAO#buscarPorId(java.lang.Long)
	 */
	@Override
	public Empresa buscarPorId(Long id) {
		return (Empresa) super.findById(Empresa.class, id);
	}

	/* (non-Javadoc)
	 * @see pe.itsb.vyrbus.model.dao.EmpresaDAO#guardar(pe.itsb.vyrbus.model.bean.Empresa)
	 */
	@Override
	public void guardar(Empresa empresa) {
		super.save(empresa);
	}

	/* (non-Javadoc)
	 * @see pe.itsb.vyrbus.model.dao.EmpresaDAO#actualizar(pe.itsb.vyrbus.model.bean.Empresa)
	 */
	@Override
	public void actualizar(Empresa empresa) {
		super.update(empresa);
	}

	/* (non-Javadoc)
	 * @see pe.itsb.vyrbus.model.dao.EmpresaDAO#inactivar(java.lang.Long)
	 */
	@Override
	public void inactivar(Long id) {
		super.inactivate(Empresa.class, id);
	}

}
