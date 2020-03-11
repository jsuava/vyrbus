/**
 * Proyecto		: SISVYR
 * Sistema		: Sistema de Ventas y Reservas
 * Descripción	: 
 * Autor		: José Sullo Avalos
 * Fecha		: 05/09/2013
 */
package com.cystesoft.vyrbus.service.business;

import com.cystesoft.vyrbus.model.bean.Pasajero;
import com.cystesoft.vyrbus.model.bean.Reniec;

/**
 * @author JABANTO
 *
 */
public interface ReniecManager  {
	/**
	 * Realiza la busqueda del pasajero en la BD del reniec
	 * @param numeroDocumento : Número de documento del Pasajero
	 * @return	objet Reniec
	 * @throws Exception
	 */
	public Reniec buscarPax(String numeroDocumento) throws Exception;
	
	/**
	 *  Valida los datos del pasajero con los de la reniec
	 * @param oPasajero: oPasajero
	 * @return
	 * @throws Exception
	 */
	Pasajero validarPaxConReniec(Pasajero oPasajero) throws Exception;
}
