/**
 * Proyecto		: SISVYR
 * Sistema		: Sistema de Ventas y Reservas
 * Descripción	:
 * Autor		: José Avalos Sullo
 * Fecha		: 12/06/2014
 */
package pe.itsb.vyrbus.view.applet;


import java.awt.Color;
import java.awt.Container;
import java.awt.Font;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JApplet;
import javax.swing.JLabel;

/**
 * @author Jose
 *
 */
public class JavaTest extends JApplet {
	private static final long serialVersionUID = 1L;
	private JLabel lblMessage1 = new JLabel();
	private JLabel lblMessage2 = new JLabel();
	private JLabel lblJavaVersion = new JLabel();
	private JLabel lblSystem = new JLabel();
	private JLabel lblImagen = new JLabel();

	private String msgJavaInstalado = "Versión de Java verificada";
	private String msgJavaNoInstalado = "Versión de Java no verificada";
	private String msgOk = "Enhorabuena.";
	private String msgError = "Lo sentimos.";

	/* (non-Javadoc)
	 * @see java.applet.Applet#init()
	 */
	@Override
	public void init() {
		// TODO Auto-generated method stub
		super.init();
		addContents();
		validarJava();
	}

	/**
	 * Agregamos los controles al contenedor de objetos.
	 */
	private void addContents(){
		Container container = getContentPane();
		container.setLayout(null);
		container.add(lblMessage1);
		container.add(lblMessage2);
		container.add(lblImagen);
		container.add(lblJavaVersion);
		container.add(lblSystem);
		container.setBackground(new Color(223, 225, 227));
	}

	private void validarJava(){
		String javaVersion = System.getProperty("java.version");
//		String javaVendor = System.getProperty("java.vendor");
		String osName = System.getProperty("os.name");
		String osVersion = System.getProperty("os.version");

		if(javaVersion!=null){
			lblMessage1.setText(msgJavaInstalado);
			lblMessage1.setForeground(Color.RED);
			lblMessage1.setFont(new Font("", Font.PLAIN, 19));
			lblMessage1.setBounds(15, 10, 260, 20);
			lblMessage2.setText(msgOk);
			lblMessage2.setFont(new Font("", Font.PLAIN, 16));
			lblMessage2.setBounds(75, 45, 200, 20);
			readingPicture("ok.png");
			lblJavaVersion.setText("Tiene instalada la versión de Java recomendada (Versión "+javaVersion+")");// by "+ javaVendor);
			lblJavaVersion.setBounds(75, 75, 400, 20);
			lblSystem.setText("Sistema Operativo: "+ osName+ "  ver."+osVersion);
//			lblSystem.setBounds(75, 95, 400, 20);
		}else{
			lblMessage1.setText(msgJavaNoInstalado);
			lblMessage1.setForeground(Color.RED);
			lblMessage1.setFont(new Font("", Font.PLAIN, 19));
			lblMessage1.setBounds(15, 10, 260, 20);
			lblMessage2.setText(msgError);
			lblMessage2.setFont(new Font("", Font.PLAIN, 16));
			lblMessage2.setBounds(75, 45, 200, 20);
			readingPicture("error.png");
			lblJavaVersion.setText("Usted no tiene instalado Java, es necesario que lo instale.");
			lblJavaVersion.setBounds(75, 75, 400, 20);
		}
	}

	private void readingPicture(String namePicture){
		try{
			BufferedImage myPicture = ImageIO.read(this.getClass().getResource(namePicture));
			lblImagen.setIcon(new ImageIcon(myPicture));
			lblImagen.setBounds(20, 45, 46, 46);
		}catch(IOException ioex){
			ioex.printStackTrace();
		}catch(Exception ex){
			ex.printStackTrace();
		}
	}

}
