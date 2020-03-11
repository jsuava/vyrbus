/**
 * Proyecto		: SISVYR
 * Sistema		: Sistema de Ventas y Reservas
 * Descripción	: 
 * Autor		: José Abanto
 * Fecha		: 27/07/2016
 * Hora			: 11:47:04
 */
package com.cystesoft.vyrbus.service.util;


import com.cystesoft.vyrbus.service.xml.XmlVentaPasaje;

/**
 * @author jabanto
 *
 */
public class CreateDocumentXml {

	public static void createBoleto(XmlVentaPasaje xmlVentaPasaje)throws Exception{
//		String pathSavedXml=Constantes.DIRECTORY_FORMAT_TICKET+xmlVentaPasaje.getV1_NumeroComprobante();
//		
//		JAXBContext context = JAXBContext.newInstance(XmlVentaPasaje.class);
//		Marshaller marshaller = context.createMarshaller();
//		marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
//		
//		
//		
//		//Mostramos el documento XML generado por la salida estandar
//		marshaller.marshal(xmlVentaPasaje, System.out);
//		
//		FileOutputStream fos = new FileOutputStream(pathSavedXml);
//		//guardamos el objeto serializado en un documento XML
//		marshaller.marshal(xmlVentaPasaje, fos);
//		fos.close();
		
//		Unmarshaller unmarshaller = context.createUnmarshaller();
//		//Deserealizamos a partir de un documento XML
//		XmlVentaPasaje xmlVentaPasajeAux = (XmlVentaPasaje) unmarshaller.unmarshal(new File(pathSavedXml));
//		System.out.println("********* Provincia cargado desde fichero XML***************");
//		//Mostramos por linea de comandos el objeto Java obtenido 
//		//producto de la deserialziacion
//		marshaller.marshal(xmlVentaPasajeAux, System.out);
		
		
	}
}
