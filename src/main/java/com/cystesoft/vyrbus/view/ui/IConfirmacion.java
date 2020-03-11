/**
 * Proyecto		: SISVYR
 * Sistema		: Sistema de Ventas y Reservas
 * Descripción	: 
 * Autor		: José Sullo Avalos
 * Fecha		: 08/11/2012
 */
package com.cystesoft.vyrbus.view.ui;

/**
 * @author Jose
 *
 */
public interface IConfirmacion {
	/**
	 * Crear un nuevo pasajero.
	 */
	public void onNewPax();
	/**
	 * Buscar pasajeros.
	 * @throws Exception
	 */
	public void onSearchPax(Integer criterio)throws Exception;
	/**
	 * Modificar los datos del pasajero.
	 */
	public void onModifyPax();
	/**
	 * Guardar o actualizar los datos del pasajero.
	 */
	public void onSavePax();
	/**
	 * Cancelar la edicion del pasajero.
	 */
	public void onCancelPax();
	/**
	 * Crear un nuevo cliente
	 */
	public void onNewClient()throws Exception;
	/**
	 * Buscar clientes
	 * @throws Exception
	 */
	public void onSearchClient(Integer criterio)throws Exception;
	/**
	 * Modificar los datos del cliente
	 */
	public void onModifyClient();
	/**
	 * Guardar o actualizar los datos del cliente
	 */
	public void onSaveClient();
	/**
	 * Cancela la edicion o el nuevo registro del cliente.
	 */
	public void onCancelClient()throws Exception;
}
