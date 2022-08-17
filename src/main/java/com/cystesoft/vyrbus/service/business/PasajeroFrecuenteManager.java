package com.cystesoft.vyrbus.service.business;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

import com.cystesoft.vyrbus.model.bean.PasajeroFrecuente;

public interface PasajeroFrecuenteManager {
	public ArrayList<PasajeroFrecuente> buscarPorEstadoRegistro(String estado, String criterioOrden)throws Exception;
	public ArrayList<PasajeroFrecuente> buscarPorX(TreeMap<String, Object> criteriosBusqueda, List<String> criteriosOrdenar)throws Exception;
	public PasajeroFrecuente buscarPorId(Long id)throws Exception;
	public void guardar(PasajeroFrecuente pasajeroFrecuente)throws Exception;
	public void actualizar(PasajeroFrecuente pasajeroFrecuente)throws Exception;
	public void inactivar(Long id)throws Exception;

	/**
	 * Realiza la busqueda del PaxFree por pasajero y el estado del registro.
	 * @param idPasajero	: Identificador unico del pasajero.
	 * @param estado		: Estado del pax Activo o inactivo
	 * @return PAXFREE
	 * @throws Exception
	 */
	public PasajeroFrecuente buscarPaxFree(Long idPasajero, Integer estado)throws Exception;
	/**
	 * Realiza la busqueda del PaxFree por el ID del pasajero.
	 * @param idPasajero	: Identificador unico del pasajero.
	 * @return PAXFREE
	 * @throws Exception
	 */
	public PasajeroFrecuente buscarPaxFree(Long idPasajero)throws Exception;

	/**
	 * Busca el numero mayor de la tarjeta para el PAXFREE.
	 * @return
	 * @throws Exception
	 */
	public PasajeroFrecuente buscarMaxNumTarjeta() throws Exception;

	/**
	 * Bucar PaxFree y sus puntos.
	 * @param idPasajero : Identificado del pasajero
	 * @param estado	 : null todos los estados o x estado del Paxfree
	 * @return
	 * @throws Exception
	 */
	public PasajeroFrecuente buscarPaxFreeAndPuntos(Long idPasajero, Integer estado) throws Exception;

	/**
	 * Busca al Paxfree por Número de Documento.
	 * @param numeroDocumento	: Número de documento del Pasajero.
	 * @return PasajeroFrecuente
	 * @throws Exception
	 */
	public PasajeroFrecuente buscarPaxfreeXNumeroDocumento(String numeroDocumento) throws Exception;
}
