package com.cystesoft.vyrbus.service.business;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

import com.cystesoft.vyrbus.model.bean.UsuarioRol;
import com.cystesoft.vyrbus.model.bean.UsuarioRolID;


/**
 *
 * @author José Abanto
 *
 */
public interface UsuarioRolManager {

	/**
	 * Busqueda por estado registro
	 * @param estado		: estado rol
	 * @param criterioOrden	: lista de criterios para el orden de los datos
	 * @return
	 */
	public ArrayList<UsuarioRol> buscarPorEstadoRegistro(String estado, String criterioOrden);

	/**
	 * Busqueda por un ArraLis de Parametros.
	 * @param criteriosBusqueda :
	 * @param criteriosOrdenar	: lista de criterios para el orden de los datos
	 * @return
	 */
	public ArrayList<UsuarioRol> buscarPorX(TreeMap<String, Object> criteriosBusqueda, List<String> criteriosOrdenar);

	/**
	 * Busqueda por id
	 * @param id : identificador del Rol
	 * @return
	 */
	public UsuarioRol buscarPorId(Long id) throws Exception;

	/**
	 * Guarda
	 * @param rol
	 */
	public void guardar(UsuarioRol usuarioRol) throws Exception;

	/***
	 * Actualiza Usuario rol
	 * @param usuarioRol : Class UsuarioRol
	 */
	public void actualizar(UsuarioRol usuarioRol) throws Exception;

	/**
	 * Inactiva UsuarioRol
	 * @param id
	 */
	public void activaInactiva(UsuarioRolID usuarioRolID, String estado) throws Exception;

	/**
	 * Buscar por el Id del Usuario.
	 * @param idRol : Identificador del Rol.
	 * @return usuarioRol
	 * @throws Exception
	 */
	public List<UsuarioRol> buscarXIdUsuario(Integer idUsuario) throws Exception;

	/**
	 * Buscar por el Id del Usuario y el Id del rol.
	 * @param idUsuario : Identificador del Usuario
	 * @param idRol		: Identificador del Rol
	 * @return usuarioRol
	 * @throws Exception
	 */
	public UsuarioRol buscarXidUsuarioAndIdRol(Integer idUsuario, Integer idRol)throws Exception;

	/**
	 * Realiza la busqueda de los usuarios y rol de acuerdo a lso criterios enviados
	 * @param campo				: Campo que debera cumplir con los criterios enviados.
	 * @param criterios			: Identificadores de los usuarios separados por comas.
	 * @param criteriosOrdenar	: Criterios a utilizar para ordenar la informacion.
	 * @param estadoRegistro	: Estado de los registros.
	 * @return Lista de Agencias.
	 */
	public List<UsuarioRol> buscarPorX(String campo, Object[] criterios, List<String> criteriosOrdenar, String estadoRegistro) throws Exception;

}
