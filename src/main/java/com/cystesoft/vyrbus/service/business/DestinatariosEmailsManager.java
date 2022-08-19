/**
 * Proyecto		: SISVYR
 * Sistema		: Sistema de Ventas y Reservas
 * Descripción	:
 * Autor		: José Abanto
 * Fecha		: 27/06/2015
 * Hora			: 08:47:57
 */
package com.cystesoft.vyrbus.service.business;

import java.util.List;
import java.util.TreeMap;

import com.cystesoft.vyrbus.model.bean.DestinatariosEmails;

/**
 * @author jabanto
 *
 */
public interface DestinatariosEmailsManager {
	/**
	 * Realiza la busqueda por el nombre del objeto.
	 * @param objeto	: Nombre del objeto.
	 * @return objeto Windows
	 * @throws Exception
	 */
	public DestinatariosEmails buscarByObjeto(String objeto)throws Exception;
	/**
	 * Realiza la busuqeda, según los criterios enviados.
	 * @param criteriosBusqueda	: Criterios de busqueda
	 * @param criteriosOrdenar	: Lista de criterios para el orden de los resultados
	 * @return lista de resultados.
	 * @throws Exception
	 */
	public List<DestinatariosEmails> buscarPor(TreeMap<String, Object>criteriosBusqueda, List<String>criteriosOrdenar)throws Exception;
	/**
	 * Actualiza una instancia de un objeto.
	 * @param windows	: Instancia del onjeto windows
	 * @throws Exception
	 */
	public void actualizar(DestinatariosEmails windows)throws Exception;
	/**
	 * Guarda una nueva instacia de un objeto.
	 * @param windows	: nueva instancia del objeto Windows
	 * @throws Exception
	 */
	public void guardar(DestinatariosEmails windows)throws Exception;
}
