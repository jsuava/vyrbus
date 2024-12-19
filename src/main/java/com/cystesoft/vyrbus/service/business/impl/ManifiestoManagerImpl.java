package com.cystesoft.vyrbus.service.business.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

import org.springframework.transaction.annotation.Transactional;

import com.cystesoft.vyrbus.model.bean.Agencia;
import com.cystesoft.vyrbus.model.bean.EspecieValorada;
import com.cystesoft.vyrbus.model.bean.Itinerario;
import com.cystesoft.vyrbus.model.bean.Manifiesto;
import com.cystesoft.vyrbus.model.bean.Usuario;
import com.cystesoft.vyrbus.model.bean.VentaPasaje;
import com.cystesoft.vyrbus.model.dao.ManifiestoDAO;
import com.cystesoft.vyrbus.service.business.ManifiestoManager;
import com.cystesoft.vyrbus.service.exceptions.ManifiestoDuplicateException;
import com.cystesoft.vyrbus.service.locator.ServiceLocator;

public class ManifiestoManagerImpl implements ManifiestoManager{
	private ManifiestoDAO manifiestoDAO;

	public ManifiestoDAO getManifiestoDAO(){
		return manifiestoDAO;
	}

	public void setManifiestoDAO(ManifiestoDAO manifiestoDAO){
		this.manifiestoDAO=manifiestoDAO;
	}


	/*
	 * (non-Javadoc)
	 * @see com.tepsa.sisvyr.service.business.ManifiestoManager#consultaItinerario(java.lang.Long)
	 */
	@Override
	public Itinerario consultaItinerario(Long idItinerario, String origen, String destino)throws Exception {
		return getManifiestoDAO().consultaItinerario(idItinerario, origen, destino);
	}

	/*
	 * (non-Javadoc)
	 * @see com.tepsa.sisvyr.service.business.ManifiestoManager#consultaPasajeros(java.lang.Long)
	 */
	@Override
	public List<VentaPasaje> consultaPasajeros(Long idItinerario,Integer idPruntoEmbarque)throws Exception {
		return getManifiestoDAO().consultaPasajeros(idItinerario,idPruntoEmbarque);
	}

	/*
	 * (non-Javadoc)
	 * @see com.tepsa.sisvyr.service.business.ManifiestoManager#consultaPtoControl(java.lang.Long)
	 */
	@Override
	public List<Agencia> consultaPtoControl(Long idItinerario)throws Exception {
		return getManifiestoDAO().consultaPtoControl(idItinerario);
	}

	/*
	 * (non-Javadoc)
	 * @see com.tepsa.sisvyr.service.business.ManifiestoManager#consultaDetaPaxXRuta(java.lang.Long)
	 */
	@Override
	public List<VentaPasaje> consultaDetaPaxXRuta(Long idItinerario, Integer agenciaIdPartida)throws Exception {
		return getManifiestoDAO().consultaDetaPaxXRuta(idItinerario, agenciaIdPartida);
	}

	/*
	 * (non-Javadoc)
	 * @see com.tepsa.sisvyr.service.business.ManifiestoManager#consultaAutorizacionSunat(java.lang.Integer)
	 */
	@Override
	public EspecieValorada consultaAutorizacionSunat(Integer idAgencia)throws Exception {
		return getManifiestoDAO().consultaAutorizacionSunat(idAgencia);
	}

	/*
	 * (non-Javadoc)
	 * @see com.tepsa.sisvyr.service.business.ManifiestoManager#guarda(com.tepsa.sisvyr.model.bean.Manifiesto)
	 */
	@Override
	@Transactional
	public void guarda(Manifiesto manifiesto) throws Exception {
		try{
			//Valida duplicidad
			TreeMap<String, Object>criteriosBusqueda=new TreeMap<>();
			criteriosBusqueda.put("numeroManifiesto", manifiesto.getNumeroManifiesto());
			List<Manifiesto> lstManifiesto= ServiceLocator.getManifiestoManager().buscarPorX(criteriosBusqueda,null);
			if(lstManifiesto.size()>0)
				throw new ManifiestoDuplicateException();

			getManifiestoDAO().guarda(manifiesto);
		}catch (ManifiestoDuplicateException md){
			throw new ManifiestoDuplicateException();
		}

	}

	/*
	 * (non-Javadoc)
	 * @see com.tepsa.sisvyr.service.business.ManifiestoManager#consultaMinifiestImpreso(java.lang.Long)
	 */
	@Override
	public Manifiesto consultaMinifiestImpreso(Long idItinerario)throws Exception {
		return getManifiestoDAO().consultaMinifiestImpreso(idItinerario);
	}

	/*
	 * (non-Javadoc)
	 * @see com.tepsa.sisvyr.service.business.ManifiestoManager#updateCorrelativoManifiesto(java.lang.Integer)
	 */
	@Transactional
	@Override
	public void updateCorrelativo(EspecieValorada especieValorada, Manifiesto manifiesto)throws Exception {
		getManifiestoDAO().updateCorrelativo(especieValorada, manifiesto);
	}

	/*
	 * (non-Javadoc)
	 * @see com.tepsa.sisvyr.service.business.ManifiestoManager#buscarManifiesto(java.lang.String, java.lang.String, java.lang.Integer, java.lang.Integer, java.lang.Integer, java.lang.Integer)
	 */
	@Override
	public List<Manifiesto> buscarManifiesto(String fechaInicial,String fechaFinal, Integer idOrigen, Integer idDestino,Integer idBus,Integer idAgenciaEmision) {
		return getManifiestoDAO().buscarManifiesto(fechaInicial, fechaFinal, idOrigen, idDestino,idBus,idAgenciaEmision);
	}

	/*
	 * (non-Javadoc)
	 * @see com.tepsa.sisvyr.service.business.ManifiestoManager#inactivar(java.lang.Long)
	 */
	@Override
	public void inactivar(Long id, Usuario usuario) {
		getManifiestoDAO().inactivar(id, usuario);

	}

	/*
	 * (non-Javadoc)
	 * @see com.tepsa.sisvyr.service.business.ManifiestoManager#buscarPorX(java.util.TreeMap, java.util.List)
	 */
	@Override
	public ArrayList<Manifiesto> buscarPorX(TreeMap<String, Object> criteriosBusqueda,List<String> criteriosOrdenar) {
		return getManifiestoDAO().buscarPorX(criteriosBusqueda, criteriosOrdenar);
	}

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.service.business.ManifiestoManager#buscarPorId(java.lang.Long)
	 */
	@Override
	public Manifiesto buscarPorId(Long id) throws Exception {
		// TODO Auto-generated method stub
		return getManifiestoDAO().buscarPorId(id);
	}

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.service.business.ManifiestoManager#actualizar(com.tepsa.sisvyr.model.bean.Manifiesto)
	 */
	@Override
	@Transactional
	public void actualizar(Manifiesto manifiesto) throws Exception {
		// TODO Auto-generated method stub
		getManifiestoDAO().guarda(manifiesto);
	}

	@Override
	public List<Manifiesto> buscarDevolucionIsc(String fechaInicial, String fechaFinal, String per4949, String periodo)  {
		return getManifiestoDAO().buscarDevolucionIsc(fechaInicial, fechaFinal, per4949, periodo);
	}
}
