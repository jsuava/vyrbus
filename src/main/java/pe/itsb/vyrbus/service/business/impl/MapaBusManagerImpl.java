/**
 * Proyecto		: SISVYR
 * Sistema		: Sistema de Ventas y Reservas
 * Descripci�n	: Implementaci�n de m�todos que permiten el acceso al modelo.
 * Autor		: Jos� Avalos
 * Fecha		: 28/09/2012
 */
package pe.itsb.vyrbus.service.business.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

import org.springframework.transaction.annotation.Transactional;

import pe.itsb.vyrbus.model.bean.MapaBus;
import pe.itsb.vyrbus.model.dao.MapaBusDAO;
import pe.itsb.vyrbus.model.dao.VentaPasajesDAO;
import pe.itsb.vyrbus.service.business.MapaBusManager;
import pe.itsb.vyrbus.service.exceptions.MapaBusNotUpdateException;
import pe.itsb.vyrbus.service.util.Constantes;

/**
 * @author Jose
 *
 */
public class MapaBusManagerImpl implements MapaBusManager {
	private MapaBusDAO mapaBusDAO;
	private VentaPasajesDAO ventaPasajesDAO;

	/**
	 * @return the mapaBusDAO
	 */
	public MapaBusDAO getMapaBusDAO() {
		return mapaBusDAO;
	}
	/**
	 * @param mapaBusDAO the mapaBusDAO to set
	 */
	public void setMapaBusDAO(MapaBusDAO mapaBusDAO) {
		this.mapaBusDAO = mapaBusDAO;
	}

	/**
	 * @return the ventaPasajesDAO
	 */
	public VentaPasajesDAO getVentaPasajesDAO() {
		return ventaPasajesDAO;
	}
	/**
	 * @param ventaPasajesDAO the ventaPasajesDAO to set
	 */
	public void setVentaPasajesDAO(VentaPasajesDAO ventaPasajesDAO) {
		this.ventaPasajesDAO = ventaPasajesDAO;
	}
	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.service.business.MapaBusManager#buscarMapaBus(java.lang.Integer, java.lang.String)
	 */
	@Override
	public List<MapaBus> buscarMapaBus(Integer idServicio, String estado)throws Exception {
		return getMapaBusDAO().buscarMapaBus(idServicio, estado);
	}

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.service.business.MapaBusManager#buscarServiciosWithMapa()
	 */
	@Override
	public List<MapaBus> buscarServiciosWithMapa() throws Exception {
		return getMapaBusDAO().buscarServiciosWithMapa();
	}
	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.service.business.MapaBusManager#guardarMapaBus(java.util.List)
	 */
	@Override
	@Transactional
	public int guardarMapaBus(List<MapaBus> lstMapaBus) throws Exception {
		int result = Constantes.FAILURE;

		if(getVentaPasajesDAO().validarServicio(lstMapaBus.get(0).getServicio().getId()).longValue() > 0)
			throw new MapaBusNotUpdateException();

		getMapaBusDAO().deleteMapaBus(lstMapaBus.get(0).getServicio().getId());

		for(MapaBus mapaBus : lstMapaBus){
			getMapaBusDAO().save(mapaBus);
		}
		result = Constantes.CORRECT;
		return result;
	}
	/* (non-Javadoc)
	 * @see pe.itsb.vyrbus.service.business.MapaBusManager#buscarMapaBusHorizontal(java.lang.Integer, java.lang.String)
	 */
	@Override
	public List<MapaBus> buscarMapaBusHorizontal(Integer idServicio, String estado) throws Exception {
		return getMapaBusDAO().buscarMapaBusHorizontal(idServicio, estado);
	}
	/* (non-Javadoc)
	 * @see pe.itsb.vyrbus.service.business.MapaBusManager#buscarPorX(java.util.TreeMap, java.util.List)
	 */
	@Override
	public ArrayList<MapaBus> buscarPorX(TreeMap<String, Object> criteriosBusqueda, List<String> criteriosOrdenar)
			throws Exception {
		// TODO Auto-generated method stub
		return getMapaBusDAO().buscarPorX(criteriosBusqueda, criteriosOrdenar);
	}


}
