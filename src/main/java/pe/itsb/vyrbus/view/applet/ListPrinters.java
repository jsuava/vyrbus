/**
 * Proyecto		: SISVYR
 * Sistema		: Sistema de Ventas y Reservas
 * Descripción	:
 * Autor		: José Abanto
 * Fecha		: 13/04/2015
 * Hora			: 15:53:34
 */
package pe.itsb.vyrbus.view.applet;

import java.applet.AppletContext;
import java.awt.Container;
import java.net.MalformedURLException;
import java.net.URL;

import javax.print.PrintService;
import javax.print.PrintServiceLookup;
import javax.swing.JApplet;
import javax.swing.JLabel;

/**
 * @author jabanto
 *
 */
public class ListPrinters extends JApplet{

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	private JLabel lblMessage1 = new JLabel();

	/* (non-Javadoc)
	 * @see java.applet.Applet#init()
	 */
	@Override
	public void init() {
		// TODO Auto-generated method stub
		super.init();
		addContents();
		try {
//			printAvailable();

//			PrintService serviceDefault = PrintServiceLookup.lookupDefaultPrintService();
			PrintService[] services = PrintServiceLookup.lookupPrintServices(null, null);

			String printers="?prints="+String.valueOf(services.length);
//			String printDeafault="";

//			if(serviceDefault!=null)
//				printDeafault=serviceDefault.getName();
//

			for(int x=0; x<services.length;x++){
				printers+="&print"+(x+1)+"="+services[x].getName();
			}
			lblMessage1.setText("Cargando impresoras, espere por favor...");

			URL url = new URL("http://ventas.tepsa.com.pe/sisvyr/principal.zul"+printers);
//			URL url = new URL("http://192.168.50.40:8080/sisvyr/principal.zul"+printers);
			AppletContext context = getAppletContext();
			context.showDocument(url, "_self");
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * Agregamos los controles al contenedor de objetos.
	 */
	private void addContents(){
		Container container = getContentPane();
//		container.setLayout(null);
		container.add(lblMessage1);
//		container.add(lblMessage2);
//		container.add(lblImagen);
//		container.add(lblJavaVersion);
//		container.add(lblSystem);
//		container.setBackground(new Color(223, 225, 227));
	}

	/**
	 * Busca los servicios de impresion.
	 * @return Impresoras encontradas a travez del objeto PrintService
	 * @throws MalformedURLException
	 */
//	public PrintService[]  printAvailable() throws MalformedURLException {
//		PrintService[] services = PrintServiceLookup.lookupPrintServices(null, null);
//
//		String printers="?totalPrints="+String.valueOf(services.length);
//		for(int x=0; x<services.length;x++){
//			printers+="&print"+(x+1)+"="+services[x].getName();
//		}
//		lblMessage1.setText("Cargando impresoras, espere por favor...");
//
//		URL url = new URL("http://192.168.50.41:8080/sisvyr/loadPrinters.zul"+printers);
//		AppletContext context = getAppletContext();
////		context.showDocument(url, "_blank");
//		context.showDocument(url, "_self");

//		return services;
//	}

}
