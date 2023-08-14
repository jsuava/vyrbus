/**
 * Proyecto		: SISVYR
 * Sistema		: Sistema de Ventas y Reservas
 * Descripción	:
 * Autor		: José Abanto
 * Fecha		: 05/09/2013
 */
package pe.itsb.vyrbus.model.dao;

import pe.itsb.vyrbus.model.bean.Pasajero;
import pe.itsb.vyrbus.model.bean.Reniec;

/**
 * @author JABANTO
 *
 */
public interface ReniecDAO extends GenericDAO {

	/**
	 * Realiza la busqueda del pasajero en la BD del reniec
	 * @param numeroDocumento : Número de documento del Pasajero
	 * @return	objet Reniec
	 * @throws Exception
	 */
	public Reniec buscarPax(String numeroDocumento)throws Exception;

	/**
	 *  Valida los datos del pasajero con los de la reniec
	 * @param oPasajero: oPasajero
	 * @return
	 * @throws Exception
	 */
	Pasajero validarPaxConReniec(Pasajero oPasajero) throws Exception;

}
