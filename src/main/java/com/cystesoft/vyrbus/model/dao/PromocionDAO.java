/**
 * Proyecto		: SISVYR
 * Sistema		: Sistema de Ventas y Reservas
 * Descripción	: Interfaz donde declaramos los metodos de acceso a datos de la tabla Promociones VRMPROMOCION.
 * Autor		: José Sullo Avalos
 * Fecha		: 17/04/2013
 */
package com.cystesoft.vyrbus.model.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

import com.cystesoft.vyrbus.model.bean.Promocion;

/**
 * @author Jose
 *
 */
public interface PromocionDAO extends GenericDAO {
	/**
	 * Busca la promocion por el identificador unico.
	 * @param id	: Identificador.
	 * @return Promocion.
	 * @throws Exception
	 */
	public Promocion buscarPorId(Long id) throws Exception;
	/**
	 * Realiza la busqueda en la tabla promociones de acuerdo a los criterios de busqueda.
	 * @param criteriosBusqueda	: Criterios a utilizar para la busqueda.
	 * @param criteriosOrdenar	: Criterios para realizar el ordenamiento de la informacion
	 * @return Lista de Promociones.
	 */
	public ArrayList<Promocion> buscarPorX(TreeMap<String, Object> criteriosBusqueda, List<String> criteriosOrdenar)throws Exception;
	/**
	 *
	 * @param criteriosBusqueda	: Criterios a utilizar para la busqueda.
	 * @param criteriosOrdenar	: Criterios para realizar el ordenamiento de la informacion
	 * @param fechaPartida		: Fecha con la que se comparara la vigencia de la promocion.
	 * @return Lista de Promociones.
	 * @throws Exception
	 */
	public ArrayList<Promocion> buscarPorX(TreeMap<String, Object> criteriosBusqueda, List<String> criteriosOrdenar, String fechaPartida)throws Exception;
	/**
	 * Realiza la busqueda de promociones por el estado de los registroas, activos o inactivos.
	 * @param estado		: Estado de los registros.
	 * @param criterioOrden	: Criterios para ordenar la informacion.
	 * @return Lista de promociones.
	 */
	public ArrayList<Promocion> buscarPorEstadoRegistro(String estado, String criterioOrden)throws Exception;
	/**
	 * Realiza la busqueda de las promociones que se pueden aplicar.
	 * @param paxfre			: Indica si el pasajero es paxfre.
	 * @param idCliente			: indica el identificador del Cliente.
	 * @param estadoRegistro	: Estado de los registros Activos o Inactivos.
	 * @param fechaPartida		: fecha de partida del servicio.
	 * @return Lista de promociones.
	 * @throws Exception
	 */
	public List<Promocion> buscarPromocionesAplicables(boolean paxfre, String idCliente, String estadoRegistro, String fechaPartida)throws Exception;

	/**
	 * Busca promociones que son por Tarifa
	 * @param fechaPartida 	:	Fecha de partida del servicio
	 * @param ruta			: Identificador de la ruta.
	 * @param servicio		: Identificador del servicio.
	 * @param horaPartida	: Hora de partida del servicio.
	 * @return Lista de promociones
	 * @throws Exception
	 */
	public List<Promocion> buscarPorTarifa(String fechaPartida, String ruta, String servicio, String horaPartida) throws Exception;
	/**
	 * Busca las promociones que estan activas segun los criterios enviados.
	 * @param denominacion	: Nombre de la promocion.
	 * @param ruta			: Ruta de la promocion.
	 * @param servicio		: Servicio de la promocion.
	 * @param cliente		: Codigo del cliente.
	 * @param tipoDescuento	: Tipo de descuento.
	 * @param criterioOrden	: Criterios para ordenar la informacion.
	 * @return Lista de promociones.
	 * @throws Exception
	 */
	public ArrayList<Promocion> buscarPorCriterios(String denominacion, String ruta, String servicio, String cliente, String tipoDescuento, String criterioOrden)throws Exception;
}
