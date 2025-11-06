/**
 * Proyecto		: SISVYR
 * Sistema		: Sistema de Ventas y Reservas
 * Descripci�n	:
 * Autor		: Jos� Abanto
 * Fecha		: 14 nov. 2022
 * Hora			: 00:47:24
 */
package pe.itsb.vyrbus.service.util;

import org.json.JSONObject;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

/**
 * @author abant
 *
 */
//@SuppressWarnings("restriction")
public class Printapi {

	/**
	 *
	 * @param xmlZip
	 * @param fileName
	 * @param formatPrint
	 * @param isReturnPathPdf: True, si solamente retornara el ruta en donde la api almacen� los archivos creandos; False, retorna en un arreglo de bytes los achivos generados.
	 * @return
	 */
	public static byte[] getPrintPdf(byte[] xmlZip, String fileName, int formatPrint, Boolean isReturnPathPdf) {
		byte[] pdfFile = null;
		try {
			String URL_API ; //= UtilFlag.getUrlApi_printapi();
//			URL_API = "http://localhost:1363/api/GenerarPdf";
			URL_API = "http://170.0.233.156:8051/api/GenerarPdf";
			
			if(URL_API !=null) {
				String rubro = "1"; // Pasajes
				String contentBase64 = new BASE64Encoder().encode(xmlZip);

				HttpResponse<JsonNode> response = Unirest.post(URL_API)
						.field("rubro", rubro)
						.field("formatPrint", formatPrint)
						.field("fileName", fileName)
						.field("fileBase64", contentBase64)
						.field("returnPathPdf", isReturnPathPdf.toString())
				  .asJson();

				if(response.getStatus() == 200) {
					JSONObject result =  response.getBody().getObject();
					String statusCode = result.get("statusCode").toString();
					if(statusCode.equals("200")) {
						String fileBase64 = result.get("file").toString();
						pdfFile = new BASE64Decoder().decodeBuffer(fileBase64);
					}
				}
			}

			return pdfFile;

		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
}
