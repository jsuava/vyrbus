package com.cystesoft.vyrbus.service.business.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

import org.springframework.transaction.annotation.Transactional;

import com.cystesoft.vyrbus.model.bean.OpcionMenu;
import com.cystesoft.vyrbus.model.dao.OpcionMenuDAO;
import com.cystesoft.vyrbus.service.business.OpcionMenuManager;
import com.cystesoft.vyrbus.service.exceptions.DenominacionDuplicadaException;
import com.cystesoft.vyrbus.service.util.Constantes;


/**
 * 
 * @author José abanto
 *
 */
public class OpcionMenuManagerImpl implements OpcionMenuManager{
	private OpcionMenuDAO opcionMenuDAO;
	
	public OpcionMenuDAO getOpcionMenuDAO(){
		return opcionMenuDAO;
	}
	
	
	public void setOpcionMenuDAO (OpcionMenuDAO opcionMenuDAO){
		this.opcionMenuDAO=opcionMenuDAO;
	}


	/*
	 * (non-Javadoc)
	 * @see com.tepsa.sisvyr.service.business.OpcionMenuMenager#buscarPorEstadoRegistro(java.lang.String, java.lang.String)
	 */
	@Override
	public ArrayList<OpcionMenu> buscarPorEstadoRegistro(String estado,String criterioOrden) {
		return getOpcionMenuDAO().buscarPorEstadoRegistro(estado, criterioOrden);
	}


	/*
	 * (non-Javadoc)
	 * @see com.tepsa.sisvyr.service.business.OpcionMenuMenager#buscarPorX(java.util.TreeMap, java.util.List)
	 */
	@Override
	public ArrayList<OpcionMenu> buscarPorX(TreeMap<String, Object> criteriosBusqueda,List<String> criteriosOrdenar) {
		return getOpcionMenuDAO().buscarPorX(criteriosBusqueda, criteriosOrdenar);
	}


	/*
	 * (non-Javadoc)
	 * @see com.tepsa.sisvyr.service.business.OpcionMenuMenager#buscarPorId(java.lang.Long)
	 */
	@Override
	public OpcionMenu buscarPorId(Long id) {
		return getOpcionMenuDAO().buscarPorId(id);
	}


	/*
	 * (non-Javadoc)
	 * @see com.tepsa.sisvyr.service.business.OpcionMenuMenager#guardar(com.tepsa.sisvyr.model.bean.OpcionMenu)
	 */
	@Override
	@Transactional
	public void guardar(OpcionMenu opcionMenu) throws Exception {
		try{
			TreeMap<String, Object> criteriosBusqueda = new TreeMap<String, Object>();
			criteriosBusqueda.put("denominacion", opcionMenu.getDenominacion());
			criteriosBusqueda.put("estadoRegistro", Constantes.VALUE_ACTIVO);
			List<?> resultDenominacion = getOpcionMenuDAO().buscarPorX(criteriosBusqueda, null);
			/*Valida duplicidad de la denominacion*/
			if(resultDenominacion.size()>0)
				throw new DenominacionDuplicadaException();

			getOpcionMenuDAO().guardar(opcionMenu);
			
		}catch (DenominacionDuplicadaException dnex){
			throw new DenominacionDuplicadaException();
		}
		
	}


	/*
	 * (non-Javadoc)
	 * @see com.tepsa.sisvyr.service.business.OpcionMenuMenager#actualizar(com.tepsa.sisvyr.model.bean.OpcionMenu)
	 */
	@Override
	@Transactional
	public void actualizar(OpcionMenu opcionMenu) throws Exception {
		try{
			TreeMap<String, Object> criteriosBusqueda = new TreeMap<String, Object>();
			criteriosBusqueda.put("denominacion", opcionMenu.getDenominacion());
			criteriosBusqueda.put("estadoRegistro", Constantes.VALUE_ACTIVO);
			List<?> resultDenominacion = getOpcionMenuDAO().buscarPorX(criteriosBusqueda, null);
			/*Valida duplicidad de la denominacion*/
			for(int r = 0; r < resultDenominacion.size(); r ++) {
				OpcionMenu oOpcionMenu = (OpcionMenu) resultDenominacion.get(r);
					if (!(oOpcionMenu.getId().equals(opcionMenu.getId())))
						throw new DenominacionDuplicadaException();
				}
			
			getOpcionMenuDAO().actualizar(opcionMenu);
			
		}catch (DenominacionDuplicadaException ddex){
			throw new DenominacionDuplicadaException();
		}
		
		
	}


	/*
	 * (non-Javadoc)
	 * @see com.tepsa.sisvyr.service.business.OpcionMenuMenager#inactivar(java.lang.Long)
	 */
	@Override
	@Transactional
	public void inactivar(Long id) {
		getOpcionMenuDAO().inactivar(id);
	}


	/*
	 * (non-Javadoc)
	 * @see com.tepsa.sisvyr.service.business.OpcionMenuMenager#cargarMenusPadres()
	 */
	@Override
	public List<OpcionMenu> buscaMenusPadres() {
		return getOpcionMenuDAO().buscaMenusPadres();
	}


	/*
	 * (non-Javadoc)
	 * @see com.tepsa.sisvyr.service.business.OpcionMenuMenager#buscarMenus(java.lang.Integer)
	 */
	@Override
	public List<OpcionMenu> buscarMenus(Integer idMenuPadre, Integer idMenu) {
		return getOpcionMenuDAO().buscarMenus(idMenuPadre, idMenu);
	}
	/*
	 * (non-Javadoc)
	 * @see com.tepsa.sisvyr.service.business.OpcionMenuManager#buscarOpcionesMenu()
	 */
	@Override
	public List<OpcionMenu> buscarOpcionesMenu() throws Exception{
		return getOpcionMenuDAO().buscarOpcionesMenu();
	}
	
	
}
