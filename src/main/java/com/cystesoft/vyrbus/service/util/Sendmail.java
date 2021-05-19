/**
 * Proyecto		: MANTYBUS
 * Sistema		: Sistema de Mantenimiento de Buses
 * Descripción	: 
 * Autor		: José Sullo Avalos
 * Fecha		: 11/08/2011
 */
package com.cystesoft.vyrbus.service.util;

import java.io.FileInputStream;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.SendFailedException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import com.cystesoft.vyrbus.model.bean.DestinatariosEmails;
import com.cystesoft.vyrbus.service.exceptions.AddressEmailInvalidException;
import com.cystesoft.vyrbus.service.exceptions.SocketMessagingException;


/**
 * @author Jose
 *
 */
public class Sendmail {
	final static String CONFIG_FILE = "mail.props";
	static String sServidorCorreo;
	static String sCorreoOrigen;
	static String[] asCorreoDestino;
	
	/**
	 * Envia email sobre incidencias presentadas.
	 * @param mensaje	: Cuerpo del email
	 * @param asunto	: Titulo del email
	 * @param window	: Objeto para obtener la lista de correos disponibles.
	 * @return Un entero 0=Fallo envio, 1=Envio exitoso
	 * @throws Exception
	 */
	public static final int enviaEmail(String mensaje, String asunto, DestinatariosEmails window)throws Exception
	{
		int result = Constantes.FAILURE;
		try{			
			String from = "jose.avalos@itsb.pe";
			Properties props = new Properties();
			// Nombre del host de correo, es smtp.gmail.com
			props.setProperty("mail.smtp.host", "itsb.pe");
			// TLS si está disponible
			props.setProperty("mail.smtp.starttls.enable", "true");
			// Puerto de gmail para envio de correos
			props.setProperty("mail.smtp.port","587");
			// Nombre del usuario
			props.setProperty("mail.smtp.user", from);
			// Si requiere o no usuario y password para conectarse.
			props.setProperty("mail.smtp.auth", "false");
			
			// Preparamos la sesion
	        Session session = Session.getDefaultInstance(props);
	        //session.setDebug(true);
	        
	        // Construir correo de texto con adjunto
//	        BodyPart texto = new MimeBodyPart();
//	        texto.setText("Documentos que vencerán");
//	        BodyPart adjunto = new MimeBodyPart();
//	        adjunto.setDataHandler(new DataHandler(new FileDataSource("/usr/local/consumos/log/consumos.log")));
//	        //adjunto.setDataHandler(new DataHandler(new FileDataSource("D:/usr/local/consumos/log/consumos.log")));
//	        adjunto.setFileName("consumos.log");
//	        
//	        //Juntar el texto y la imagen adjunta
//	        MimeMultipart multiParte = new MimeMultipart();
//	        multiParte.addBodyPart(texto);
//	        multiParte.addBodyPart(adjunto);
//	        
	        // Construimos el mensaje
	        MimeMessage message = new MimeMessage(session);
	        // Quien envia el correo
	        message.setFrom(new InternetAddress(from));
	        // A quien va dirigido
	        String[] lstEmails = window.getEmails().split(";");
			for(int i=0;i<lstEmails.length;i++){
				if(lstEmails[i].substring(0, 3).equals("TO:")){
					String[] email = lstEmails[i].substring(3).split(",");
					for(int j=0;j<email.length;j++){
						message.addRecipient(Message.RecipientType.TO, new InternetAddress(email[j]));
					}
				}else if(lstEmails[i].substring(0, 3).equals("CC:")){
					String[] email = lstEmails[i].substring(3).split(",");
					for(int j=0;j<email.length;j++){
						message.addRecipient(Message.RecipientType.CC, new InternetAddress(email[j]));
					}
				}else{
					String[] email = lstEmails[i].substring(4).split(",");
					for(int j=0;j<email.length;j++){
						message.addRecipient(Message.RecipientType.BCC, new InternetAddress(email[j]));
					}
				}
			}
			message.setSubject(asunto);
	        message.setText(mensaje);
//	        message.setContent(multiParte);
	        
	        //Para enviar el mensaje usamos la clase Transport
	        Transport t = session.getTransport("smtp");
	        // Establecemos la conexion
//	        t.connect(from,"mantybus303");
	        t.connect();
	        
	        // Enviamos el mensaje
	        t.sendMessage(message,message.getAllRecipients());
	        // Cerramos la conexxión
	        t.close();
	        result = Constantes.CORRECT;
		}catch(SendFailedException sfex){
			String addressInvalid = "";
			if(sfex.getMessage().equals("Invalid Addresses")){
				for(int i=0;i<sfex.getInvalidAddresses().length;i++){
					addressInvalid = addressInvalid+sfex.getInvalidAddresses()[i]+", ";
				}
				throw new AddressEmailInvalidException("Las siguientes direcciones de correo no son validas " + addressInvalid+"informe de lo sucedido al area de SISTEMAS.");
			}else
				throw new Exception(sfex.getMessage());
		}catch(MessagingException mex){
			throw new SocketMessagingException(mex.getMessage());
		}catch(Exception ex){
			throw new Exception(ex);
		}        
		return result;
	}
	
	/**
     * Método público y estático que envía un correo a las direcciones
     * indicadas en el fichero de propiedades, desde la dirección indicada
     * también en el mismo fichero con el asunto y el contenido que se pasan
     * como parámetros.
     * 
     * @param sAsunto String
     * @param sTexto String
     * @return boolean
     */
	public static boolean enviarEmail( String sAsunto, String sTexto ){
		try{
			Properties props = new Properties();
	        props.put("mail.smtp.host", sServidorCorreo );
	        Session mailSesion = Session.getDefaultInstance(props, null);
	
	        Message msg = new MimeMessage(mailSesion);
	   
	        msg.setFrom ( new InternetAddress( sCorreoOrigen ) );
	        msg.setSubject ( sAsunto );
	        msg.setSentDate ( new java.util.Date() );
	        msg.setText ( sTexto );
	
	        InternetAddress address[] = new InternetAddress[asCorreoDestino.length];
	        for( int i = 0; i < asCorreoDestino.length; i++ ) {
	            address[i] = new InternetAddress ( asCorreoDestino[i] );
	        }         
	           
	        msg.setRecipients (Message.RecipientType.TO, address);
	                           
	        Transport.send(msg);
		}catch( MessagingException e ){
			return false ;
		}
		return true ;
	}
	
	/**
     * Método para inicializar los valores del seridor de correo,
     * se cargan desde un fichero de configuración con los siguientes valores:
     * app.servidorCorreo=smtp.xxxx
     * app.correoOrigen=origenxxx@xxx.com
     * app.correoDestino=dst1@xxx.com,dst2@xxx.com,...,dstn@xxx.com
     */
	public static void init () {
		try{
			Properties props = new Properties();
	        props.load( new FileInputStream( CONFIG_FILE ) );
	        
	        sServidorCorreo = props.getProperty( "app.servidorCorreo" );
	        sCorreoOrigen = props.getProperty( "app.correoOrigen" );
	        String sTmp = props.getProperty( "app.correoDestino" );
	        String[] asTmp = null;
	        if( sTmp.indexOf( "," ) != 1 ) {
	            asTmp = sTmp.split( "," );
	        }else{
	            asTmp = new String[1];
	            asTmp[0] = sTmp;
	        }
	        asCorreoDestino = asTmp;
		}catch( Exception ex ){
	        System.out.println( "No hay información de arranque!!" );
	        System.exit( -2 );
	    }
	}
}
