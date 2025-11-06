/**
 * Proyecto		: SISVYR
 * Sistema		: Sistema de Ventas y Reservas
 * Descripci�n	:
 * Autor		: Jos� Abanto
 * Fecha		: 16 nov. 2022
 * Hora			: 21:08:15
 */
package pe.itsb.vyrbus.service.util;


import pe.itsb.vyrbus.model.bean.Flag;
import pe.itsb.vyrbus.service.locator.ServiceLocator;

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
	final static int FLAG_IDTIEMPO_POSTERGACION = 10;
	final static int FLAG_IDTIEMPO_POSTERGACION_FA = 11;
	final static int FLAG_IDTIEMPO_CAMBIO_NOMBRE = 12;
	final static int FLAG_IDCONECTION_TRANSCAR = 13;
//	final static int FLAG_IDMANIFIESTO_ELECTRONICO = 17;
	final static int FLAG_IDTIEMPO_CAMBIO_ASIENTO = 18;

	final static String LLAVE_ENABLED = "S";
	final static String LLAVE_DISABLED = "N";

	/**
	 * Valida si esta activo el flag para la generación de manifiesto electrono.
	 * @param agenciaId : Identificador de la agencia que genera el Manifiesto
	 * @return true: Activo; false: Inactivo
	 * @throws Exception
	 */
//	public static boolean isEnabledManifiestoElectronico(Integer agenciaId) throws Exception {
//		boolean estado = getConfigFlagById_paramAgencia(FLAG_IDMANIFIESTO_ELECTRONICO, agenciaId);
//		
//		return estado;
//	}
	
	/**
	 * Valida si esta activo el flag para la coneción con transcar web
	 * @return
	 * @throws Exception
	 */
	public static boolean isConeccionTranscar()throws Exception{
				
		boolean estado = getConfigFlagById(FLAG_IDCONECTION_TRANSCAR);
		
		return estado;
	}
	
	/**
	 * Obtiene el tiempo, expresado en minutos para el cambio de nombre, antes de la salida
	 * @return
	 * @throws Exception
	 */
	public static Integer getTiempoCambioNombre()throws Exception{
		Integer tiempo = null;
		
		Flag flag = getFlag(FLAG_IDTIEMPO_CAMBIO_NOMBRE);
		if(flag !=null && !flag.getLlave().equals(LLAVE_DISABLED))
			tiempo = Integer.valueOf(flag.getLlave());
		
		
		return tiempo;
		
	}
	
	/**
	 * Obtiene el tiempo, expresado en minutos para el cambio de asiento, antes de la salida
	 * @return
	 * @throws Exception
	 */
	public static Integer getTiempoCambioAsiento()throws Exception{
		Integer tiempo = null;
		
		Flag flag = getFlag(FLAG_IDTIEMPO_CAMBIO_ASIENTO);
		if(flag !=null && !flag.getLlave().equals(LLAVE_DISABLED))
			tiempo = Integer.valueOf(flag.getLlave());		
		
		return tiempo;
		
	}
	
	/**
	 * Obtiene el tiempo expresado en minutos para la postergación F.A., antes de la salida
	 * @return
	 * @throws Exception
	 */
	public static Integer getTiempoPostergacionFA()throws Exception{
		Integer tiempo = null;
		
		Flag flag = getFlag(FLAG_IDTIEMPO_POSTERGACION_FA);
		if(flag !=null && !flag.getLlave().equals(LLAVE_DISABLED))
			tiempo = Integer.valueOf(flag.getLlave());
		
		
		return tiempo;
		
	}
	
	/**
	 * Obtiene el tiempo, expresado en minutos para la postergación, antes de la salida
	 * @return
	 * @throws Exception
	 */
	public static Integer getTiempoPostergacion()throws Exception{
		Integer tiempo = null;
		
		Flag flag = getFlag(FLAG_IDTIEMPO_POSTERGACION);
		if(flag !=null && !flag.getLlave().equals(LLAVE_DISABLED))
			tiempo = Integer.valueOf(flag.getLlave());
		
		
		return tiempo;
	}
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
	 * @return True, si la configuraci�n esta activa: False, lo contrario.
	 * @throws Exception
	 */
	public static boolean isActivaImportePagarVentaRemota(Integer agenciaId)throws Exception{

		boolean estado = getConfigFlagById_paramAgencia(FLAG_IDACTIVA_IMPPAG_VENTAREMOTA, agenciaId);

		return estado;
	}

	/**
	 * Formato de impresion desde un nuevo navegador, para la impreson de los manifiestos
	 * @param agenciaId : Identificador de la agencia
	 * @return True, si la configuraci�n esta activa: False, lo contrario.
	 * @throws Exception
	 */
	public static boolean isFormatPrintViewPdfManifiesto(Integer agenciaId)throws Exception{

		boolean estado = getConfigFlagById_paramAgencia(FLAG_IDFORM_PRINT_VIEWPDF_MAN, agenciaId);

		return estado;
	}

	/**
	 * Formato de impresion desde un nuevo navegador, para la carpeta de despachos
	 * @param agenciaId : Identificador de la agencia
	 * @return True, si la configuraci�n esta activa: False, lo contrario.
	 * @throws Exception
	 */
	public static boolean isFormatPrintViewPdfCarpetaDespacho(Integer agenciaId)throws Exception{

		boolean estado = getConfigFlagById_paramAgencia(FLAG_IDFORM_PRINT_VIEWPDF_CARDES, agenciaId);

		return estado;
	}

	/**
	 * Formato de impresion desde un nuevo navegador
	 * @param agenciaId : Identificador de la agencia
	 * @return True, si la configuraci�n esta activa: False, lo contrario.
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
	 * @param agenciaId	: Identificador de la agencia en donde se validar� la configuracion
	 * @return True, si la configuracion esta activa; False, lo contrario.
	 * @throws Exception
	 */
	private static boolean getConfigFlagById_paramAgencia(Integer flagId,Integer agenciaId)throws Exception{
		boolean estado = false;

		Flag flag = ServiceLocator.getFlagManager().buscarPorId(flagId.longValue());
		if(flag !=null && flag.getLlave()!=null && !flag.getLlave().equals(LLAVE_DISABLED) && flag.getEstadoRegistro().equals(Constantes.VALUE_ACTIVO)) {
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
	 * @return True, si est� habilitado; False, lo contrario
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
	 * Realiza la busqueda del flag por su identificador
	 * @param flag_Id: Identiticador del flag
	 * @return
	 * @throws Exception
	 */
	private static Flag getFlag(Integer flag_Id)throws Exception{

		Flag flag = ServiceLocator.getFlagManager().buscarPorId(flag_Id.longValue());
		if(flag!=null && flag.getEstadoRegistro().equals(Constantes.VALUE_ACTIVO))
			return flag;
		
		return null;
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
		
//		url = "http://localhost:38822/viewboldir";
		
		return url;
	}

}
