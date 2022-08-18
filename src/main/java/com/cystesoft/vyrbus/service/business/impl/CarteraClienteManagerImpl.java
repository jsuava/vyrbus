package com.cystesoft.vyrbus.service.business.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

import org.springframework.transaction.annotation.Transactional;

import com.cystesoft.vyrbus.model.bean.CarteraCliente;
import com.cystesoft.vyrbus.model.dao.CarteraClienteDAO;
import com.cystesoft.vyrbus.service.business.CarteraClienteManager;

public class CarteraClienteManagerImpl implements CarteraClienteManager {
	private CarteraClienteDAO carteraClienteDAO;

	public CarteraClienteDAO getCarteraClienteDAO() {
		return carteraClienteDAO;
	}

	public void setCarteraClienteDAO(CarteraClienteDAO carteraClienteDAO) {
		this.carteraClienteDAO = carteraClienteDAO;
	}

	/*
	 * (non-Javadoc)
	 * @see com.tepsa.sisvyr.service.business.CarteraClienteManager#buscarPorEstadoRegistro(java.lang.String, java.lang.String)
	 */
	@Override
	public ArrayList<CarteraCliente> buscarPorEstadoRegistro(String estado,String criterioOrden) {
		return getCarteraClienteDAO().buscarPorEstadoRegistro(estado, criterioOrden);
	}

	/*
	 * (non-Javadoc)
	 * @see com.tepsa.sisvyr.service.business.CarteraClienteManager#buscarPorX(java.util.TreeMap, java.util.List)
	 */
	@Override
	public ArrayList<CarteraCliente> buscarPorX(TreeMap<String, Object> criteriosBusqueda,List<String> criteriosOrdenar) {
		return getCarteraClienteDAO().buscarPorX(criteriosBusqueda, criteriosOrdenar);
	}

	/*
	 * (non-Javadoc)
	 * @see com.tepsa.sisvyr.service.business.CarteraClienteManager#guardar(com.tepsa.sisvyr.model.bean.CarteraCliente)
	 */
	@Override
	@Transactional
	public void guardar(CarteraCliente carteraCliente) {
		getCarteraClienteDAO().guardar(carteraCliente);
	}

	/*
	 * (non-Javadoc)
	 * @see com.tepsa.sisvyr.service.business.CarteraClienteManager#inactivar(java.lang.Long)
	 */
	@Override
	@Transactional
	public void inactivar(Long id) {
		getCarteraClienteDAO().inactivar(id);
	}

	/*
	 * (non-Javadoc)
	 * @see com.tepsa.sisvyr.service.business.CarteraClienteManager#buscarClientesCartera(java.lang.Integer, java.lang.Long)
	 */
	@Override
	public List<CarteraCliente> buscarClientesCartera(Integer idFuncionario,Long idCliente) {
		return getCarteraClienteDAO().buscarClientesCartera(idFuncionario, idCliente);
	}

	/*
	 * (non-Javadoc)
	 * @see com.tepsa.sisvyr.service.business.CarteraClienteManager#actualizar(com.tepsa.sisvyr.model.bean.CarteraCliente)
	 */
	@Override
	@Transactional
	public void actualizar(CarteraCliente carteraCliente) {
		getCarteraClienteDAO().actualizar(carteraCliente);

	}

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.service.business.CarteraClienteManager#buscarPorId(java.lang.Long)
	 */
	@Override
	public CarteraCliente buscarPorId(Long id) throws Exception {
		return getCarteraClienteDAO().buscarPorId(id);
	}

}
