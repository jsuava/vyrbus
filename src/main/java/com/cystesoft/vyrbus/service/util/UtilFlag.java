/**
 * Proyecto		: SISVYR
 * Sistema		: Sistema de Ventas y Reservas
 * Descripción	: 
 * Autor		: José Abanto
 * Fecha		: 16 nov. 2022
 * Hora			: 21:08:15
 */
package com.cystesoft.vyrbus.service.util;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import com.cystesoft.vyrbus.model.bean.Flag;
import com.cystesoft.vyrbus.service.locator.ServiceLocator;

/**
 * @author abant
 *
 */
public class UtilFlag {
	final static int FLAG_IDURL_PRINTAPI_PDF = 1;	
	final static int FLAG_IDFORM_PRINT_DOWNLOAD = 2;
	final static int FLAG_IDFORM_PRINT_VIEWPDF = 3;
	final static int FLAG_IDURL_VIEW_PDF = 4;
	final static int FLAG_IDFORM_PRINT_VIEWPDF_MAN = 5;
	final static int FLAG_IDFORM_PRINT_VIEWPDF_CARDES = 6;
	final static int FLAG_IDACTIVA_IMPPAG_VENTAREMOTA = 7;
	final static int FLAG_IDSEARCH_DNI_RENIEC = 8;
	final static int FLAG_IDSEARCH_RUC_SUNAT = 9;
	final static int FLAG_IDVTA_ANULACION_USERS = 10;
	
	final static String LLAVE_ENABLED = "S";
	final static String LLAVE_DISABLED = "N";
	
	
	
	
	/**
	 * Valida si debe o no buscar el DNI en un servicio externo
	 * @return
	 * @throws Exception
	 */
	public static boolean searchDniReniec() throws Exception{
		boolean estado = getConfigFlagById(FLAG_IDSEARCH_DNI_RENIEC);
		
		return estado;
	}
	
	/**
	 * Valida si debe o no buscar el RUC en un servicio externo
	 * @return
	 * @throws Exception
	 */
	public static boolean searchRucSunat() throws Exception{
		boolean estado = getConfigFlagById(FLAG_IDSEARCH_RUC_SUNAT);
		
		return estado;
	}

	/**
	 * Activa o desactiva el control del importe a pagar para ventas remotas.
	 * @param agenciaId : Identificador de la agencia
	 * @return True, si la configuración esta activa: False, lo contrario.
	 * @throws Exception
	 */
	public static boolean isActivaImportePagarVentaRemota(Integer agenciaId)throws Exception{
				
		boolean estado = getConfigFlagById_paramAgencia(FLAG_IDACTIVA_IMPPAG_VENTAREMOTA, agenciaId);		
		
		return estado;
	}
	
	/**
	 * Formato de impresion desde un nuevo navegador, para la impreson de los manifiestos
	 * @param agenciaId : Identificador de la agencia
	 * @return True, si la configuración esta activa: False, lo contrario.
	 * @throws Exception
	 */
	public static boolean isFormatPrintViewPdfManifiesto(Integer agenciaId)throws Exception{
				
		boolean estado = getConfigFlagById_paramAgencia(FLAG_IDFORM_PRINT_VIEWPDF_MAN, agenciaId);		
		
		return estado;
	}
	
	/**
	 * Formato de impresion desde un nuevo navegador, para la carpeta de despachos
	 * @param agenciaId : Identificador de la agencia
	 * @return True, si la configuración esta activa: False, lo contrario.
	 * @throws Exception
	 */
	public static boolean isFormatPrintViewPdfCarpetaDespacho(Integer agenciaId)throws Exception{
				
		boolean estado = getConfigFlagById_paramAgencia(FLAG_IDFORM_PRINT_VIEWPDF_CARDES, agenciaId);		
		
		return estado;
	}
	
	/**
	 * Formato de impresion desde un nuevo navegador 
	 * @param agenciaId : Identificador de la agencia
	 * @return True, si la configuración esta activa: False, lo contrario.
	 * @throws Exception
	 */
	public static boolean isFormatPrintViewPdf(Integer agenciaId)throws Exception{
				
		boolean estado = getConfigFlagById_paramAgencia(FLAG_IDFORM_PRINT_VIEWPDF, agenciaId);		
		
		return estado;
	}
	
	/**
	 * Formato de impresion por modalidad descarga de archivo
	 * @param agenciaId: Identificador de la agencia
	 * @return True, si la configuracion esta activa; False, lo contrario.
	 * @throws Exception
	 */
	public static boolean isFormatPrintDownload(Integer agenciaId)throws Exception {
				
		boolean estado = getConfigFlagById_paramAgencia(FLAG_IDFORM_PRINT_DOWNLOAD, agenciaId);
				
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
	 * Realiza la busqueda del Flag por su identificador
	 * @param flag_Id: Identificador unico del flag
	 * @return True, si está habilitado; False, lo contrario
	 * @throws Exception
	 */
	private static boolean getConfigFlagById(Integer flag_Id)throws Exception{
		boolean estado = false;

		Flag flag = ServiceLocator.getFlagManager().buscarPorId(flag_Id.longValue());
		if(flag !=null && flag.getLlave()!=null && flag.getEstadoRegistro().equals(Constantes.VALUE_ACTIVO)) {
			if(flag.getLlave().equals(LLAVE_ENABLED))
				estado = true;		
		}
		
		return estado;
	}
	
	/**
	 * Url de la api, que genera los archivos en fomarto PDF para la impresion
	 * @return Url de la API
	 * @throws Exception
	 */
	public static String getUrlApi_printapi()throws Exception{		
		String url = null;
		Flag flag = ServiceLocator.getFlagManager().buscarPorId((long)FLAG_IDURL_PRINTAPI_PDF);
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
		
		String url = null;
		
		Flag flag = ServiceLocator.getFlagManager().buscarPorId((long)FLAG_IDURL_VIEW_PDF);
		if(flag !=null && flag.getLlave()!=null && flag.getEstadoRegistro().equals(Constantes.VALUE_ACTIVO))
			url = flag.getLlave();
		
		return url;
	}
	
	/**
	 * Formato de impresion desde un nuevo navegador 
	 * @param agenciaId : Identificador de la agencia
	 * @return True, si la configuración esta activa: False, lo contrario.
	 * @throws Exception
	 */
	public static boolean isEnabledUserAnulacion(Integer usuario_id)throws Exception{
		// Obtiene la lista de identificadores desde la función getFlag
		Flag flag = ServiceLocator.getFlagManager().buscarPorId((long)FLAG_IDVTA_ANULACION_USERS);
		List<String> params = Optional.ofNullable(flag)
			.map(fg -> Arrays.asList(fg.getLlave().split(",")))
			.orElseGet(Arrays::asList);
		
		// Verifica si el identificador está en la lista
        return params.stream()
            .anyMatch(id -> id.equals("*") || id.trim().equals(usuario_id.toString()));
	}
	

	
}
