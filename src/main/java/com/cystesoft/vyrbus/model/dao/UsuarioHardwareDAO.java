package com.cystesoft.vyrbus.model.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

import com.cystesoft.vyrbus.model.bean.UsuarioHardware;

public interface UsuarioHardwareDAO extends GenericDAO {
	/**
	 * 
	 * @param estado
	 * @param criterioOrden
	 * @return
	 */
	public ArrayList<UsuarioHardware> buscarPorEstadoRegistro(String estado, String criterioOrden);
	
	/**
	 * 
	 * @param criteriosBusqueda
	 * @param criteriosOrdenar
	 * @return
	 */
	public ArrayList<UsuarioHardware> buscarPorX(TreeMap<String, Object> criteriosBusqueda, List<String> criteriosOrdenar);
	
	/**
	 * 
	 * @param id
	 * @return
	 */
	public UsuarioHardware buscarPorId(Long id);
	
	/**
	 * 
	 * @param usuarioHardware
	 */
	public void guardar(UsuarioHardware usuarioHardware) throws Exception;
	
	/**
	 * 
	 * @param usuarioHardware
	 */
	public void actualizar(UsuarioHardware usuarioHardware) throws Exception;
	
	/**
	 * 
	 * @param id
	 */
	public void inactivar(Long id);
	
	
	/**
	 * Busqueda por código
	 * @param Codigo	: código del usuario hadrware
	 * @return
	 * @throws Exception
	 */
	public UsuarioHardware buscarXCodigo(String codigo) throws Exception;
	

}
