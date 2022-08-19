package com.cystesoft.vyrbus.service.business.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

import org.springframework.transaction.annotation.Transactional;

import com.cystesoft.vyrbus.model.bean.AutorizadorCortesia;
import com.cystesoft.vyrbus.model.dao.AutorizadorCortesiaDAO;
import com.cystesoft.vyrbus.model.dao.AutorizadorMotivoCortesiaDAO;
import com.cystesoft.vyrbus.service.business.AutorizadorCortesiaManager;
import com.cystesoft.vyrbus.service.exceptions.AutorizadorDuplicadoException;
import com.cystesoft.vyrbus.service.util.Constantes;

/**
 *
 * @author JABANTO
 *
 */
public class AutorizadorCortesiaManagerImpl implements AutorizadorCortesiaManager {

	private AutorizadorCortesiaDAO autorizadorCortesiaDAO;
	private AutorizadorMotivoCortesiaDAO autorizadorMotivoCortesiaDAO;

	public void setAutorizadorCortesiaDAO(AutorizadorCortesiaDAO  autorizadorCortesiaDAO){
		this.autorizadorCortesiaDAO=autorizadorCortesiaDAO;
	}

	public AutorizadorCortesiaDAO getAutorizadorCortesiaDAO(){
		return this.autorizadorCortesiaDAO;
	}

	/**
	 * @return the autorizadorMotivoCortesiaDAO
	 */
	public AutorizadorMotivoCortesiaDAO getAutorizadorMotivoCortesiaDAO() {
		return autorizadorMotivoCortesiaDAO;
	}

	/**
	 * @param autorizadorMotivoCortesiaDAO the autorizadorMotivoCortesiaDAO to set
	 */
	public void setAutorizadorMotivoCortesiaDAO(
			AutorizadorMotivoCortesiaDAO autorizadorMotivoCortesiaDAO) {
		this.autorizadorMotivoCortesiaDAO = autorizadorMotivoCortesiaDAO;
	}

	/*
	 * (non-Javadoc)
	 * @see com.tepsa.sisvyr.service.business.AutorizadorCortesiaManajer#buscarPorEstadoRegistro(java.lang.String, java.lang.String)
	 */
	@Override
	public ArrayList<AutorizadorCortesia> buscarPorEstadoRegistro(String estado, String criterioOrden) {
		// TODO Auto-generated method stub
		return getAutorizadorCortesiaDAO().buscarPorEstadoRegistro(estado, criterioOrden);
	}

	/*
	 * (non-Javadoc)
	 * @see com.tepsa.sisvyr.service.business.AutorizadorCortesiaManajer#buscarPorX(java.util.TreeMap, java.util.List)
	 */
	@Override
	public ArrayList<AutorizadorCortesia> buscarPorX(TreeMap<String, Object> criteriosBusqueda,List<String> criteriosOrdenar) {
		// TODO Auto-generated method stub
		return getAutorizadorCortesiaDAO().buscarPorX(criteriosBusqueda, criteriosOrdenar);
	}

	/*
	 * (non-Javadoc)
	 * @see com.tepsa.sisvyr.service.business.AutorizadorCortesiaManajer#buscarPorId(java.lang.Long)
	 */
	@Override
	public AutorizadorCortesia buscarPorId(Long id) {
		// TODO Auto-generated method stub
		return getAutorizadorCortesiaDAO().buscarPorId(id);
	}

	/*
	 * (non-Javadoc)
	 * @see com.tepsa.sisvyr.service.business.AutorizadorCortesiaManajer#guardar(com.tepsa.sisvyr.model.bean.AutorizadorCortesia)
	 */

	@Override
	@Transactional()
	public void guardar(AutorizadorCortesia autorizadorCortesia) throws Exception {
		try{
			TreeMap<String, Object> criteriosBusqueda = new TreeMap<>();
			criteriosBusqueda.put("personal", autorizadorCortesia.getPersonal());
//			criteriosBusqueda.put("motivoCortesia", autorizadorCortesia.getMotivoCortesia());
			criteriosBusqueda.put("estadoRegistro", Constantes.VALUE_ACTIVO);
			List<?> resul = getAutorizadorCortesiaDAO().buscarPorX(criteriosBusqueda, null);
			/* Valida duplicidad del personal y motovo cortesia */
			if(resul.size()>0)
				throw new AutorizadorDuplicadoException();
			getAutorizadorCortesiaDAO().guardar(autorizadorCortesia);
		}catch (AutorizadorDuplicadoException aucex) {
			throw new AutorizadorDuplicadoException();
		}

	}

	/*
	 * (non-Javadoc)
	 * @see com.tepsa.sisvyr.service.business.AutorizadorCortesiaManajer#actualizar(com.tepsa.sisvyr.model.bean.AutorizadorCortesia)
	 */
	@Override
	@Transactional()
	public void actualizar(AutorizadorCortesia autorizadorCortesia) throws Exception {
		try{
//			TreeMap<String, Object> criteriosBusqueda = new TreeMap<String, Object>();
//			criteriosBusqueda.put("personal", autorizadorCortesia.getPersonal());
////			criteriosBusqueda.put("motivoCortesia", autorizadorCortesia.getMotivoCortesia());
//			criteriosBusqueda.put("estadoRegistro", Constantes.VALUE_ACTIVO);
//			List<?> resul = getAutorizadorCortesiaDAO().buscarPorX(criteriosBusqueda, null);
//			/* Valida duplicidad del personal y motovo cortesia */
//			for(int r = 0; r < resul.size(); r ++) {
//				AutorizadorCortesia autorizador= (AutorizadorCortesia) resul.get(r);
//					if (!(autorizador.getId().equals(autorizadorCortesia.getId())))
//						throw new AutorizadorDuplicadoException();
//				}
//
			getAutorizadorCortesiaDAO().actualizar(autorizadorCortesia);
			getAutorizadorMotivoCortesiaDAO().inactivar(autorizadorCortesia);

//			for(MotivoCortesia motivoCortesia : autorizadorCortesia.getListaMotivoCortesia()){
//				AutorizadorMotivoCortesia autorizadorMotivo = new AutorizadorMotivoCortesia();
//				autorizadorMotivo.setAutorizadorCortesia(autorizadorCortesia);
//				autorizadorMotivo.setMotivoCortesia(motivoCortesia);
//				autorizadorMotivo.setEstadoRegistro(Constantes.VALUE_ACTIVO);
//				autorizadorMotivo.setUsuarioInsercion(autorizadorCortesia.getUsuarioInsercion());
//				autorizadorMotivo.setIpInsercion(autorizadorCortesia.getIpInsercion());
//				autorizadorMotivo.setUsuarioModificacion(autorizadorCortesia.getUsuarioModificacion());
//				autorizadorMotivo.setIpModificacion(autorizadorCortesia.getIpModificacion());
//				getAutorizadorMotivoCortesiaDAO().save(autorizadorMotivo);
//			}
		}catch (Exception ex) {
			throw new Exception();
		}

	}

	/*
	 * (non-Javadoc)
	 * @see com.tepsa.sisvyr.service.business.AutorizadorCortesiaManajer#inactivar(long)
	 */
	@Override
	@Transactional()
	public void inactivar(long id) {
		// TODO Auto-generated method stub
		getAutorizadorCortesiaDAO().inactivar(id);
	}

}
