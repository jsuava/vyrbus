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
	 * Formato de impresion desde un nuevo navegador 
	 * @param agenciaId : Identificador de la agencia
	 * @return True, si la configuración esta activa: False, lo contrario.
	 * @throws Exception
	 */
	public static boolean isFormatPrintViewPdf(Integer agenciaId)throws Exception{
		final int FLAG_IDFORMAT_PRINT_VIEWPDF = 3;
		
		boolean estado = getConfigFlagById_paramAgencia(FLAG_IDFORMAT_PRINT_VIEWPDF, agenciaId);		
		
		return estado;
	}
	
	/**
	 * Formato de impresion por modalidad descarga de archivo
	 * @param agenciaId: Identificador de la agencia
	 * @return True, si la configuracion esta activa; False, lo contrario.
	 * @throws Exception
	 */
	public static boolean isFormatPrintDownload(Integer agenciaId)throws Exception {
		final int FLAG_IDFORMAT_PRINT_DOWNLOAD = 2;
		
		boolean estado = getConfigFlagById_paramAgencia(FLAG_IDFORMAT_PRINT_DOWNLOAD, agenciaId);
				
		return estado;
	}
	
	/***
	 * Realiza la busqueda del Flag por su identificador, para una determinada agencia
	 * @param flagId	: Identitificador del Flag.
	 * @param agenciaId	: Identificador de la agencia en donde se validará la configuracion
	 * @return True, si la configuracion esta activa; False, lo contrario.
	 * @throws Exception
	 */
	private static boolean getConfigFlagById_paramAgencia(Integer flagId,Integer agenciaId)throws Exception{
		boolean estado = false;

		Flag flag = ServiceLocator.getFlagManager().buscarPorId(flagId.longValue());
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
	
	/**
	 * Url de la api, que genera los archivos en fomarto PDF para la impresion
	 * @return Url de la API
	 * @throws Exception
	 */
	public static String getUrlView_pdf()throws Exception{
		final long FLAG_ID = 4;
		String url = null;
		
		Flag flag = ServiceLocator.getFlagManager().buscarPorId(FLAG_ID);
		if(flag !=null && flag.getLlave()!=null && flag.getEstadoRegistro().equals(Constantes.VALUE_ACTIVO))
			url = flag.getLlave();
		
		return url;
	}
	
}
