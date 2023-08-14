package pe.itsb.vyrbus.service.business.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

import org.springframework.transaction.annotation.Transactional;

import pe.itsb.vyrbus.model.bean.DetalleItinerario;
import pe.itsb.vyrbus.model.dao.DetalleItinerarioDAO;
import pe.itsb.vyrbus.service.business.DetalleItinerarioManager;

/**
 *
 * @author JABANTO
 *
 */
public class DetalleItinerarioManagerImpl implements DetalleItinerarioManager{
	private DetalleItinerarioDAO detalleItinerarioDAO;

	public DetalleItinerarioDAO getDetalleItinerarioDAO(){
		return detalleItinerarioDAO;
	}

	public void setDetalleItinerarioDAO (DetalleItinerarioDAO detalleItinerarioDAO){
		this.detalleItinerarioDAO=detalleItinerarioDAO;
	}

	@Override
	@Transactional
	public void guardar(DetalleItinerario detalleItinerario)throws Exception {
		getDetalleItinerarioDAO().save(detalleItinerario);
	}

	@Override
	@Transactional
	public void actualizar(DetalleItinerario detalleItinerario)throws Exception {
		getDetalleItinerarioDAO().update(detalleItinerario);
	}

	@Override
	@Transactional
	public void inactivar(Long id) throws Exception {
		// TODO Auto-generated method stub

	}

	@Override
	@Transactional
	public void delete(Long idItinerario) throws Exception {
		getDetalleItinerarioDAO().delete(idItinerario);

	}

	@Override
	public ArrayList<DetalleItinerario> buscarPorX(TreeMap<String, Object> criteriosBusqueda,List<String> criteriosOrdenar) throws Exception {
		return getDetalleItinerarioDAO().buscarPorX(criteriosBusqueda, criteriosOrdenar);
	}
	/*
	 * (non-Javadoc)
	 * @see com.tepsa.sisvyr.service.business.DetalleItinerarioManager#buscarPorId(java.lang.Long)
	 */
	@Override
	public DetalleItinerario buscarPorId(Long idDetalleItinerario)throws Exception{
		return getDetalleItinerarioDAO().buscarPorId(idDetalleItinerario);
	}

}
