package pe.itsb.vyrbus.service.business.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

import org.springframework.transaction.annotation.Transactional;

import pe.itsb.vyrbus.model.bean.LineaContadoCliente;
import pe.itsb.vyrbus.model.dao.LineaContadoClienteDAO;
import pe.itsb.vyrbus.service.business.LineaContadoClienteManager;

/**
 *
 * @author JABANTO
 *
 */
public class LineaContadoClienteManagerImpl implements LineaContadoClienteManager {
	private LineaContadoClienteDAO lineaContadoClienteDAO;

	public LineaContadoClienteDAO getLineaContadoClienteDAO() {
		return lineaContadoClienteDAO;
	}

	public void setLineaContadoClienteDAO(LineaContadoClienteDAO lineaContadoClienteDAO) {
		this.lineaContadoClienteDAO = lineaContadoClienteDAO;
	}

	/*
	 * (non-Javadoc)
	 * @see com.tepsa.sisvyr.service.business.LineaContadoClienteManager#buscarPorEstadoRegistro(java.lang.String, java.lang.String)
	 */
	@Override
	public ArrayList<LineaContadoCliente> buscarPorEstadoRegistro(String estado, String criterioOrden) {
		return getLineaContadoClienteDAO().buscarPorEstadoRegistro(estado, criterioOrden);
	}

	/*
	 * (non-Javadoc)
	 * @see com.tepsa.sisvyr.service.business.LineaContadoClienteManager#buscarPorX(java.util.TreeMap, java.util.List)
	 */
	@Override
	public ArrayList<LineaContadoCliente> buscarPorX(TreeMap<String, Object> criteriosBusqueda,List<String> criteriosOrdenar) {
		return getLineaContadoClienteDAO().buscarPorX(criteriosBusqueda, criteriosOrdenar);
	}

	/*
	 * (non-Javadoc)
	 * @see com.tepsa.sisvyr.service.business.LineaContadoClienteManager#buscarPorId(java.lang.Long)
	 */
	@Override
	public LineaContadoCliente buscarPorId(Long id) {
		return getLineaContadoClienteDAO().buscarPorId(id);
	}

	/*
	 * (non-Javadoc)
	 * @see com.tepsa.sisvyr.service.business.LineaContadoClienteManager#guardar(com.tepsa.sisvyr.model.bean.LineaContadoCliente)
	 */
	@Override
	@Transactional
	public void guardar(LineaContadoCliente lineaContadoCliente) {
		getLineaContadoClienteDAO().guardar(lineaContadoCliente);
	}

	/*
	 * (non-Javadoc)
	 * @see com.tepsa.sisvyr.service.business.LineaContadoClienteManager#actualizar(com.tepsa.sisvyr.model.bean.LineaContadoCliente)
	 */
	@Override
	@Transactional
	public void actualizar(LineaContadoCliente lineaContadoCliente) {
		getLineaContadoClienteDAO().actualizar(lineaContadoCliente);
	}

	/*
	 * (non-Javadoc)
	 * @see com.tepsa.sisvyr.service.business.LineaContadoClienteManager#inactivar(java.lang.Long)
	 */
	@Override
	@Transactional
	public void inactivar(Long id) {
		getLineaContadoClienteDAO().inactivar(id);
	}

	/*
	 * (non-Javadoc)
	 * @see com.tepsa.sisvyr.service.business.LineaContadoClienteManager#validaDescuenteCliente(java.lang.Long)
	 */
	@Override
	public LineaContadoCliente validaDescuentoCliente(Long idCliente) {
		return getLineaContadoClienteDAO().validaDescuentoCliente(idCliente);
	}


}
