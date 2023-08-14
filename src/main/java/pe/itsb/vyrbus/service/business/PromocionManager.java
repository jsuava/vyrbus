/**
 * Proyecto		: SISVYR
 * Sistema		: Sistema de Ventas y Reservas
 * Descripción	: Interfaz donde definimos las reglas del negocio para la tabla Promocion VRMPROMOCION.
 * Autor		: José Avalos
 * Fecha		: 18/04/2013
 */
package pe.itsb.vyrbus.service.business;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

import pe.itsb.vyrbus.model.bean.Promocion;

/**
 * @author Jose
 *
 */
public interface PromocionManager {
	/**
	 * Busca la promocion por el identificador unico.
	 * @param id	: Identificador.
	 * @return Promocion.
	 * @throws Exception
	 */
	public Promocion buscarPorId(Long id) throws Exception;
	/**
	 * Guarda el objeto promocion.
	 * @param promocion	: Objeto que se desea guardar.
	 * @return -1:fallo, 1:exitoso
	 * @throws Exception
	 */
	public int guardarPromocion(Promocion promocion)throws Exception;
	/**
	 * Guarda el objeto promocion.
	 * @param lstPromocion	: Objetos que se desean guardar.
	 * @return -1:fallo, 1:exitoso
	 * @throws Exception
	 */
	public int guardarPromocion(List<Promocion> lstPromocion)throws Exception;
	/**
	 * Busca las promociones que estan activas.
	 * @param estado		: Estado de los registros, Activos o Inactivos.
	 * @param criterioOrden	: Criterios para ordenar la informacion.
	 * @return Lista de promociones.
	 * @throws Exception
	 */
	public ArrayList<Promocion> buscarPorEstadoRegistro(String estado, String criterioOrden)throws Exception;
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
	 * @param fechaPartida 	: Fecha de partida del servicio
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
	/**
	 * Actualiza una promocion
	 * @param promocion : Objeto que se desea actualizar
	 * @throws Exception
	 */
	public  void actualizar(Promocion promocion)throws Exception;
}
