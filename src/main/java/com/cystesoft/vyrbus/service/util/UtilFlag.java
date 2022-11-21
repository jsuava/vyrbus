/**
 * Proyecto		: SISVYR
 * Sistema		: Sistema de Ventas y Reservas
 * Descripción	: 
 * Autor		: José Abanto
 * Fecha		: 16 nov. 2022
 * Hora			: 21:08:15
 */
package com.cystesoft.vyrbus.service.util;

import com.cystesoft.vyrbus.model.bean.Flag;
import com.cystesoft.vyrbus.service.locator.ServiceLocator;

/**
 * @author abant
 *
 */
public class UtilFlag {
	/**
	 * Formato de impresion pro modalidad descaga de archivo, con uso de la ape PrintApi
	 * @param agenciaId: Identificador de la agencia
	 * @return True, si el flag esta activo; False, si el flag esta inactivo
	 * @throws Exception
	 */
	public static boolean isFormatPrintDownload(Integer agenciaId)throws Exception {
		final long FLAG_ID = 2;
		boolean estado = false;
		
		Flag flag = ServiceLocator.getFlagManager().buscarPorId(FLAG_ID);
		if(flag !=null && flag.getLlave()!=null && flag.getEstadoRegistro().equals(Constantes.VALUE_ACTIVO)) {
			if(flag.getLlave().equals("*"))
				estado = true;
			else {
				String[] agencias = flag.getLlave().split(",");
				for(String _agenciaId: agencias) {
					if(_agenciaId.contentEquals(agenciaId.toString())) {
						estado = true;
						break;
					}						
				}
			}			
		}
		
		return estado;
	}
	
	/**
	 * Url de la api, que genera los archivos en fomarto PDF para la impresion
	 * @return Url de la API
	 * @throws Exception
	 */
	public static String getUrlApi_printapi()throws Exception{
		final long FLAG_ID = 1;
		String url = null;
		
		Flag flag = ServiceLocator.getFlagManager().buscarPorId(FLAG_ID);
		if(flag !=null && flag.getLlave()!=null && flag.getEstadoRegistro().equals(Constantes.VALUE_ACTIVO))
			url = flag.getLlave();
		
		return url;
	}
	
	
	
}
