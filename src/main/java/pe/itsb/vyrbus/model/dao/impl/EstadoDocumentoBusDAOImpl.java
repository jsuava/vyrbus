/**
 * Proyecto		: SISVYR
 * Sistema		: Sistema de Ventas y Reservas
 * Descripción	:
 * Autor		: José Avalos
 * Fecha		: 25/08/2012
 */
package pe.itsb.vyrbus.model.dao.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

import pe.itsb.vyrbus.model.bean.EstadoDocumentoBus;
import pe.itsb.vyrbus.model.dao.EstadoDocumentoBusDAO;

/**
 *
 * @author JA
 * @since JDK1.6
 */
@SuppressWarnings("unchecked")
public class EstadoDocumentoBusDAOImpl extends GenericDAOImpl implements EstadoDocumentoBusDAO {


	@Override
	public ArrayList<EstadoDocumentoBus> buscarPorEstadoRegistro(String estado,String criterioOrden) {
		return (ArrayList<EstadoDocumentoBus>) super.findByEstadoRegistro(EstadoDocumentoBus.class, estado, criterioOrden);
	}

	@Override
	public ArrayList<EstadoDocumentoBus> buscarPorX(TreeMap<String, Object> criteriosBusqueda, List<String> criteriosOrdenar) {
		return (ArrayList<EstadoDocumentoBus>) super.findByX(EstadoDocumentoBus.class, criteriosBusqueda, criteriosOrdenar);
	}

	@Override
	public EstadoDocumentoBus buscarPorId(Long id) {
		return (EstadoDocumentoBus) super.findById(EstadoDocumentoBus.class, id);
	}

	@Override
	public void guardar(EstadoDocumentoBus estadoDocumentoBus) {
		super.save(estadoDocumentoBus);
	}

	@Override
	public void actualizar(EstadoDocumentoBus estadoDocumentoBus) {
		super.update(estadoDocumentoBus);
	}

	@Override
	public void inactivar(Long id) {
		super.inactivate(EstadoDocumentoBus.class, id);
	}

}
