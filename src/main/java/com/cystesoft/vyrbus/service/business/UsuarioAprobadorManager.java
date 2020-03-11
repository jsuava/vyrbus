package com.cystesoft.vyrbus.service.business;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

import com.cystesoft.vyrbus.model.bean.UsuarioAprobador;

/**
 * 
 * @author JABANTO
 *
 */
public interface UsuarioAprobadorManager {
	/**
	 * Buscar usuario(s) aprobador(es) por estado Registro
	 * @param estado		:Estado registro
	 * @param criterioOrden	: cadena de criterios para el orden de la Data recuperada.
	 * @return
	 */
	public ArrayList<UsuarioAprobador> buscarPorEstadoRegistro(String estado, String criterioOrden);
	
	/**
	 * Buscar Usuario(s) Aprobador(es) según un array de criterios
	 * @param criteriosBusqueda : Array de criterios para la busqueda
	 * @param criteriosOrdenar  : Lista de criterios para el orden de la Data recuperada.
	 * @return
	 */
	public ArrayList<UsuarioAprobador> buscarPorX(TreeMap<String, Object> criteriosBusqueda, List<String> criteriosOrdenar);
	
	/**
	 * Buscar usuario aprobador por ID
	 * @param id :Identificador del usuarioAprobador 
	 * @return
	 */
	public UsuarioAprobador buscarPorId(Long id);
	
	/**
	 * Guarda UsuarioAprobador
	 * @param usuarioAprobador: class UsuarioAprobador
	 */
	public void guardar(UsuarioAprobador usuarioAprobador);
	
	/**
	 * Actualiza usuario identificador
	 * @param usuarioAprobador: Class usuarioAprobador
	 */
	public void actualizar(UsuarioAprobador usuarioAprobador);
	
	/**
	 * Inactivar Usuario Aprobador
	 * @param id : Identificador del usuario Aprobador
	 */
	public void inactivar(Long id);
	
	/**
	 * Busca el o los usuarios aprobadores por nivel de aprobación
	 * @param nivel	: Nivel a buscar.
	 * @throws Exception
	 */
	public List<UsuarioAprobador> buscarXNivel(Integer nivel) throws Exception;

}
