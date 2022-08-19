/**
 * Proyecto		: SISVYR
 * Sistema		: Sistema de Ventas y Reservas
 * Descripción	: Clase que realiza la impresion de los documentos..
 * Autor		: José Avalos Sullo
 * Fecha		: 07/12/2012
 */
package com.cystesoft.vyrbus.view.applet;

import java.awt.Color;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;

import javax.print.Doc;
import javax.print.DocFlavor;
import javax.print.DocPrintJob;
import javax.print.PrintException;
import javax.print.PrintService;
import javax.print.PrintServiceLookup;
import javax.print.SimpleDoc;
import javax.print.attribute.AttributeSet;
import javax.print.attribute.HashAttributeSet;
import javax.print.attribute.HashPrintRequestAttributeSet;
import javax.print.attribute.PrintRequestAttributeSet;
import javax.print.attribute.standard.PrinterName;
import javax.swing.JApplet;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;


/**
*
* @author José Avalos Sullo
* @since JDK1.6
*/
public class Printer extends JApplet implements ActionListener {
	private static final long serialVersionUID = 1L;
	/**
	 * This label contains the url of the ticket
	 */
	private String urlDocumento;
	private String urlDocumento1;
	private String urlDocumento2;
	private String namePrinter;
	/**
	 * This label contains the type format to printer
	 */
	private String formato;

	/**
	 * This string contains the escape sequence to enable condensed mode
	 */
	private static final String ACTIVATE_CONDENSED = Character.toString((char) 15);
//	private static final String FORWARD_PAGE = Character.toString((char) 12);

	/**
     * This label contain a message telling user the print was successful (Part 1)
     * */
    private JLabel lblSuccessFullMesagePart1 = new JLabel("");

    /**
     * This label contain a message telling user the print was successful (Part 2)
     * */
    private JLabel lblSuccessFullMesagePart2 = new JLabel("");

    /**
     * This button allow the user try to print again the receipt (just in case the communication with the port failed)
     */
    private JButton btnImprimir = new JButton("Reintentar");


	public Printer() {

	}

	@Override
	public void init() {
		super.init();
		addContents();
		retrieveParameters();
		btnImprimir.addActionListener(this);

		/*	DESCOMENTAR LAS SIGUIENTES 16 LINEAS EN CASO SE QUIERA MOSTRAR EL DIALOGO DEL APPLET */
		if(isPrinterConfigured()){
			print(this.getUrlFile());
			if(formato.equals("MANIFIESTO_PAX")){
				if(urlDocumento1!=null)
					print(urlDocumento1);
				if(urlDocumento2!=null)
					print(urlDocumento2);
			}else if(formato.equals("BOLETO_IDA_VUELTA")){
				if(urlDocumento1!=null)
					print(urlDocumento1);
			}
		}else{
			int valor = JOptionPane.showOptionDialog(this, "No se pudo encontrar una impresora configurada.\nPor " +
					"favor primero configure la impresora predeterminada y luego pulse el boton Reintentar para continuar con la impresión.",
					"TEPSA :: VyR-Reintentar imprimir", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null,
					new Object[]{"Reintentar", "Cerrar"}, "Reintentar");
			if(valor==JOptionPane.YES_OPTION)
				print(this.getUrlFile());
		}
		/*	FIN	*/
	}

	/**
	 * Valida que se tenga configurada la impresora.
	 * @return Boolean
	 */
	private boolean isPrinterConfigured(){
		lblSuccessFullMesagePart1.setText("Se está comprobando la existencia de");
		lblSuccessFullMesagePart2.setText("la impresora. Por favor espere...");
		PrintService service = PrintServiceLookup.lookupDefaultPrintService();

		if(service==null){
			/*	COMENTAR LAS SIGUIENTES 6 LINEAS SI SE DESEA MOSTRAR EL DIALOGO DEL APPLET	*/
//			int valor = JOptionPane.showOptionDialog(this, "No se pudo encontrar una impresora configurada.\nPor " +
//					"favor primero configure la impresora predeterminada y luego pulse el boton Reintentar para continuar con la impresión.",
//					"TEPSA :: VyR-Reintentar imprimir", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null,
//					new Object[]{"Reintentar", "Cerrar"}, "Reintentar");
//			if(valor==JOptionPane.YES_OPTION)
//				print();
			/*	FIN	*/
			return false;
		}
		return true;
	}

	/**
	 * Realizamos la recepción de todos los parametros enviados al APPLET
	 */
	private void retrieveParameters(){
		if(getParameter("namePrinter")!=null)
			namePrinter=getParameter("namePrinter");


		urlDocumento = getParameter("urlDocumento");
		formato = getParameter("formato");
		if(formato.equals("MANIFIESTO_PAX")){
			urlDocumento1 = getParameter("urlDocumento1");
			urlDocumento2 = getParameter("urlDocumento2");
		}else if (formato.equals("BOLETO_IDA_VUELTA")){
			urlDocumento1 = getParameter("urlDocumento1");
		}
	}

	/**
	 * Agregamos los controles al contenedor de objetos.
	 */
	private void addContents(){
		Container container = getContentPane();
		container.setLayout(new FlowLayout());
		container.add(lblSuccessFullMesagePart1);
		container.add(lblSuccessFullMesagePart2);
		container.setBackground(new Color(255, 255, 255));
		/*	DESCOMENTAR ESTA LINEA EN CASO SE QUIERA MOSTRAR EL DIALOGO DEL APPLET */
		container.add(btnImprimir);
		/*	FIN	*/
	}

	/**
	 * Evento click del boton.
	 * @param e	: Evento.
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource()==btnImprimir)
			print(this.getUrlFile());
	}

//	public void print(String urlFile){
//		/*	COMENTA ESTAS 2 LINEAS SI SE DESEA MOSTRAR EL DIALOGO DEL APPLET	*/
////		if(!isPrinterConfigured())
////			return;
//		/*	FIN	*/
//
////		PrintService service = PrintServiceLookup.lookupDefaultPrintService();
//
//		AttributeSet aset=new HashAttributeSet();
//		aset.add(new PrinterName("EPSON LX-300+ /II", null));
//
//		PrintService[] service = PrintServiceLookup.lookupPrintServices(null, null);
//		service=PrintServiceLookup.lookupPrintServices(null, aset);
//
//		PrintRequestAttributeSet atributos = new HashPrintRequestAttributeSet();
//		DocFlavor flavor = DocFlavor.BYTE_ARRAY.AUTOSENSE;
////		DocPrintJob pj = service.createPrintJob();
//		DocPrintJob pj = service[0].createPrintJob();
//		byte[] bytes;
//		bytes = this.getDataFile(urlFile);
//		if(bytes!=null){
//	        Doc doc = new SimpleDoc(bytes, flavor, null);
//	        try{
//	            pj.print(doc, atributos);
//	            String messagePart1 = "Se esta imprimiendo el documento enviado a la impresora...";
//	        	String messagePart2 = "Cierre esta ventana para continuar.";
//	        	getContentPane().remove(btnImprimir);
//	        	showErrorMessage("", messagePart1, messagePart2, false);
//	        }catch(PrintException pex){
//	        	String messagePart1 = "No se pudo imprimir el documento enviado a la impresora.";
//	        	String messagePart2 = "Por favor verifique que tiene configurada la impresora.";
//	        	/*	COMENTAR ESTA LINEA SI SE DESEA MOSTRAR EL DIALOGO DEL APPLET	*/
////	        	getContentPane().add(btnImprimir);
//	        	/*	FIN	*/
//	        	showErrorMessage(pex.getMessage(), messagePart1, messagePart2, true);
//	        }
//		}
//	}



	public void print(String urlFile){
		/*	COMENTA ESTAS 2 LINEAS SI SE DESEA MOSTRAR EL DIALOGO DEL APPLET	*/
//		if(!isPrinterConfigured())
//			return;
		/*	FIN	*/

		DocPrintJob pj = null;
		/*Valida si se esta enviando el nombre de la impresora donde debe imprimir, caso contrario buscara una predeterninada*/
		if(namePrinter==null){
			/*Toma la por defecto*/
			PrintService service = PrintServiceLookup.lookupDefaultPrintService();
			pj=service.createPrintJob();
		}else{
			/*By name Printer*/
			AttributeSet aset=new HashAttributeSet();
			aset.add(new PrinterName(namePrinter, null));

			PrintService[] service = PrintServiceLookup.lookupPrintServices(null, null);
			service=PrintServiceLookup.lookupPrintServices(null, aset);
			pj = service[0].createPrintJob();
		}

		byte[] bytes;
		bytes = this.getDataFile(urlFile);

		if(bytes!=null){
			DocFlavor flavor = DocFlavor.BYTE_ARRAY.AUTOSENSE;
	        Doc doc = new SimpleDoc(bytes, flavor, null);
	        try{
	        	PrintRequestAttributeSet atributos = new HashPrintRequestAttributeSet();
	            pj.print(doc, atributos);
	            String messagePart1 = "Se esta imprimiendo el documento enviado a la impresora...";
	        	String messagePart2 = "Cierre esta ventana para continuar.";
	        	getContentPane().remove(btnImprimir);
	        	showErrorMessage("", messagePart1, messagePart2, false);
	        }catch(PrintException pex){
	        	String messagePart1 = "No se pudo imprimir el documento enviado a la impresora.";
	        	String messagePart2 = "Por favor verifique que tiene configurada la impresora.";
	        	/*	COMENTAR ESTA LINEA SI SE DESEA MOSTRAR EL DIALOGO DEL APPLET	*/
//	        	getContentPane().add(btnImprimir);
	        	/*	FIN	*/
	        	showErrorMessage(pex.getMessage(), messagePart1, messagePart2, true);
	        }
		}
	}

	public String getUrlFile() {
        return urlDocumento;
    }

	/**
	 * Obtenemos el contenido del archivo para realizar la impresion.
	 * @param urlPath	: Ruta del archivo a leer.
	 * @return byte[]
	 */
	private byte[] getDataFile(String urlPath){
        byte[] datos = null;
        StringBuilder contenido = new StringBuilder();
        String linea;
        contenido.append(ACTIVATE_CONDENSED);
        try {
        	URL url = new URL(urlPath);
        	InputStream is = url.openStream();
            BufferedReader di = new BufferedReader(new InputStreamReader(is));

            do{
            	linea = di.readLine();

            	if(linea == null)
                    break;
                else
                    contenido.append(linea + "\n");

            }while(true);
//            contenido.append(FORWARD_PAGE);
            datos = contenido.toString().getBytes();

        }catch (IOException ex) {
        	int valor = JOptionPane.showOptionDialog(this, "No se pudo encontrar el archivo.\n"+ex.getMessage()+"\n¿Desea volver a intentar la impresion del documento?",
					"TEPSA :: VyR-Reintentar imprimir", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null,
					new Object[]{"Si", "No"}, "Reintentar");
			if(valor==JOptionPane.YES_OPTION)
				print(this.getUrlFile());
        }
        return datos;
    }

	/**
	 * Muestra los mensajes de error.
	 * @param errorMessage		: Texto a mostrar en el mensaje.
	 * @param messagePart1		: Mensaje a mostrar en el applet.
	 * @param messagePart2		: Mensaje a mostrar en el applet.
	 * @param showErrorMessage	: Booleano TRUE=imprime el mensaje.
	 */
	private void showErrorMessage(String errorMessage, String messagePart1,String messagePart2,boolean showErrorMessage){
        lblSuccessFullMesagePart1.setText(messagePart1);
        lblSuccessFullMesagePart2.setText(messagePart2);
        if(showErrorMessage)
            JOptionPane.showMessageDialog(this, errorMessage, "TEPSA VyR", JOptionPane.ERROR_MESSAGE);
    }
}
