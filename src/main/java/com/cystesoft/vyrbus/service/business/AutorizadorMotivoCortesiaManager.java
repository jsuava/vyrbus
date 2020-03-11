/**
 * Proyecto		: SISVYR
 * Sistema		: Sistema de Ventas y Reservas
 * Descripción	: Interfaz donde definimos las reglas del negocio para la tabla Autorizador Motivo Cortesia VRTAUTCOR_MOTCOR.
 * Autor		: José Sullo Avalos
 * Fecha		: 03/04/2014
 */
package com.cystesoft.vyrbus.service.business;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

import com.cystesoft.vyrbus.model.bean.AutorizadorMotivoCortesia;
import com.cystesoft.vyrbus.model.bean.MotivoCortesia;

/**
 * @author Jose
 *
 */
public interface AutorizadorMotivoCortesiaManager {
	/**
	 * Realiza la busqueda por estado del registro;
	 * @param estado		: Activo o inactivo.
	 * @param criterioOrden	: criterios para ordenar la informacion
	 * @return ArrayList 
	 */
	public ArrayList<AutorizadorMotivoCortesia> buscarPorEstadoRegistro(String estado, String criterioOrden);
	/**
	 * Realiza la busqueda por criterios;
	 * @param criteriosBusqueda	: Criteriso de la busqueda.
	 * @param criteriosOrdenar	 criterios para ordenar la informacion
	 * @return ArrayList
	 */
	public ArrayList<AutorizadorMotivoCortesia> buscarPorX(TreeMap<String, Object> criteriosBusqueda, List<String> criteriosOrdenar);
	/**
	 * realiza la busqueda por identificador.
	 * @param id	: Identificador unico.
	 * @return AutorizadorMotivoCortesia
	 */
	public AutorizadorMotivoCortesia buscarPorId(Long id);
	/**
	 * Guarda la instancia del autorizadorMotivoCortesia.
	 * @param autorizadorMotivoCortesia	: Objeto a guardar.
	 */
	public int guardar(AutorizadorMotivoCortesia autorizadorMotivoCortesia);
	/**
	 * Actualiza la instancia del autorizadorMotivoCortesia.
	 * @param autorizadorMotivoCortesia	: Objeto que se desea actualizar.
	 */
	public int actualizar(AutorizadorMotivoCortesia autorizadorMotivoCortesia);
	/**
	 * Inactiva una instancia de AutorizadorMotivoCortesia
	 * @param id	: Identificador unico.
	 */
	public int inactivar(Long id);
	/**
	 * Busca los motivos de cortesia asociaods a un autorizador.
	 * @param idPersonal	: Identificador del personal.
	 * @return lista de Motivos.
	 */
	public List<MotivoCortesia> buscarMotivosCortesia(Long idPersonal)throws Exception;
}
