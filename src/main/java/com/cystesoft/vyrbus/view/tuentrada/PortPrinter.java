/**
 * Proyecto		: SISVYR
 * Sistema		: Sistema de Ventas y Reservas
 * Descripción	: Clase para controlar la impresion al puerto serial
 * Autor		: José Avalos Sullo
 * Fecha		: 09/06/2014
 */
package com.cystesoft.vyrbus.view.tuentrada;

import java.awt.Color;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.net.URL;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

import javax.swing.JApplet;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;


/**

 * @author José Avalos

 * @version 1.0

 * */

@SuppressWarnings({"unused","unchecked"})
public class PortPrinter extends JApplet implements ActionListener, Serializable{
	public PortPrinter() {
	}

    private static final long serialVersionUID = 1L;
	/**
     * This variable contains the URL of tepsacargo
     * */
    private static String TEPSA_COMERCIAL_URL = "http://localhost:8080";
    /**
     * This variable contains the application name
     * */
    private static String APPLICATION_NAME = "sisvyr";
    /**
     * This variable contains the master id field value
     * */
    private static String MASTER_ID_LIST = "masterIdList";

    /**
     * This variable contains the Java home value
     * */
    private static String JAVA_HOME = "java.home";

    /**
     * This variable contains the javax.comm.properties value
     * */
    private static String JAVA_COM_PROPERTIES_FILE = "javax.comm.properties";
    /**
     * This variable contains the Win32.dll value
     * */
    private static String WIN32_DLL = "Win32com.dll";
    /**
     * This variable contains the comm.jar value
     * */
    private static String COMM_JAR = "comm.jar";
    /**
     * This variable contains the lib value
     * */
    private static String LIB_FOLDER = "lib";
    /**
     * This variable contains the bin value
     * */
    private static String BIN_FOLDER = "bin";
    /**
     * This variable contains the ext value
     * */
    private static String EXT_FOLDER = "ext";
    /**
     * This variable contains the "loadDriver" value
     * */
    private static String LOAD_DRIVER_METHOD = "loadDriver";

    /**
     * This variable contains the default COM port where the receipt is gonna be printed
     * */
    private static String DEFAULT_COM_PORT = "COM1";

    /**
     * This variable contains the value of the parameter message
     * */
    private static String PARAMETER_MESSAGE = "msg";
    /**
     * This variable contains the value of the URL of the configuration files installer
     * */
    private static String URL_CONFIGURATION_FILES_INSTALLER = "URLInstaller";
    /**
     * This variable contains the file name where the configuration will be
     * */
    private static String CONFIGURATION_FILE_NAME = "tepsacom";
    /**
     * This variable contains the COM string
     * */
    private static String COM = "COM";
    /**
     * This enumeration contains all ports available in S.O.
     * */
    private static Enumeration <CommPortIdentifier> portList;

    /**
     * This list of String contains all ports availables
     * */
    private List<String> portAvailables = new ArrayList<>();

    /**
     * This variable contains the portId
     */
    private static CommPortIdentifier portId;

    /**
     * This variable contains the message to be printed
     * */
    private static String messageString = "";
    /**
     * This variable contains the web page link to download the configuration file installer
     * */
    private String URLConfigurationFileInstaller ="";
    /**
     * This variable contains the serial port where the message is going to be write
     * */
    private static SerialPort serialPort;


    /**
     * This variable represents the stream for output
     * */
    private static OutputStream outputStream;

    /**
     * This button allow the user try to print again the receipt (just in case the communication with the port failed)
     * */
    private JButton retryButton = new JButton("Reintentar");

    /**
     * This label contain a message telling user the print was successful (Part 1)
     * */
    private JLabel successFullMesagePart1 = new JLabel("");

    /**
     * This label contain a message telling user the print was successful (Part 2)
     * */
    private JLabel successFullMesagePart2 = new JLabel("");
    /**
     * This variable contains all the information needed to print on COM port
     * */
    private PortConfigurationInfo portConfiguration = new PortConfigurationInfo();

    /**
     * This method is called when the applet is loaded
     * @param none
     * @return none
     * */
    @Override
	public void init()
    {
        System.setSecurityManager(null);
        addContents();
        retrieveParameters();
        if (isEnvironmentConfigurated())
            print(false);
        else{
            String messagePart1="Los archivos de configuración no existen";
            String messagePart2="Haga click en el enlace para instalarlos";
            showErrorMessage("", messagePart1, messagePart2, false);
            this.remove(retryButton);
            URLLabel ull1 = new URLLabel(this,
                    URLConfigurationFileInstaller,
            "Instalar archivos");
            getContentPane().add(ull1);

        }

        retryButton.addActionListener(this);
    }

    /**
     * This method retrieves all COM ports
     * @param none
     * @return none
     * */
    private void retrieveComPorts(){
        portList = enumerateSerialPorts();
    }

    /**
     * This method retrieves all parameters
     * @param none
     * @return none
     * */
    private void retrieveParameters(){
        messageString= this.getParameter(PARAMETER_MESSAGE);
        URLConfigurationFileInstaller=this.getParameter(URL_CONFIGURATION_FILES_INSTALLER);
        if(messageString==null)
            messageString = "";
        if(URLConfigurationFileInstaller==null)
            URLConfigurationFileInstaller = "http://localhost:8080/sisvyr/installer.exe";
    }

    /**
     * This method add the contents to the Applet
     * */
    private void addContents(){
        Container container = getContentPane();
        container.setLayout(new FlowLayout());
        this.getContentPane().add(successFullMesagePart1);
        this.getContentPane().add(successFullMesagePart2);
        this.getContentPane().add(retryButton);
        container.setBackground(new Color(255, 255, 255));
    }

    private int convertDataBits(int fileDataBits){
        int dataBits =0;
        switch(fileDataBits){
        case 5:
            dataBits = SerialPort.DATABITS_5;
            break;
        case 6:
            dataBits = SerialPort.DATABITS_6;
            break;
        case 7:
            dataBits = SerialPort.DATABITS_7;
            break;
        case 8:
            dataBits = SerialPort.DATABITS_8;
            break;
        default:
            dataBits = SerialPort.DATABITS_5;
        }
        return dataBits;
    }

    private int converParityOptions(String parityOptions){
        int parityOption = 0;
        if(parityOptions.equals("Par")){
            parityOption=SerialPort.PARITY_EVEN;
        }
        else if(parityOptions.equals("Impar")){
            parityOption=SerialPort.PARITY_ODD;
        }
        else if(parityOptions.equals("Marca")){
            parityOption=SerialPort.PARITY_MARK;
        }
        else if(parityOptions.equals("Espacio")){
            parityOption=SerialPort.PARITY_SPACE;
        }
        else if(parityOptions.equals("Sin paridad")){
            parityOption=SerialPort.PARITY_NONE;
        }
        return parityOption;
    }

    private int converStopBits(String parityOptions){
        int parityOption = 0;
        if(parityOptions.equals("1")){
            parityOption=SerialPort.STOPBITS_1;
        }
        else if(parityOptions.equals("1.5")){
            parityOption=SerialPort.STOPBITS_1_5;
        }
        else if(parityOptions.equals("2")){
            parityOption=SerialPort.STOPBITS_2;
        }
        return parityOption;
    }
    /**
     * This method open the connect to port sent in the argument
     * @throws PortInUseException
     * @throws UnsupportedCommOperationException
     * @throws IOException
     * */
    private void openConnection() throws PortInUseException, UnsupportedCommOperationException, IOException{
        serialPort = (SerialPort)portId.open("PrintToComPort", 2000);
        int bitsPerSecond = portConfiguration.getCurrentBitsPerSecond();
        int dataBits = convertDataBits(portConfiguration.getCurrentDataBits());
        int parityOption = converParityOptions(portConfiguration.getCurrentParity());
        int stopBits = converStopBits(portConfiguration.getCurrentStopBits());
        serialPort.setSerialPortParams(bitsPerSecond,
                dataBits,
                stopBits,
                parityOption);
        outputStream = serialPort.getOutputStream();
    }

    /**
     * This method write the message in the outputStream configured with the COM port configured.
     * @throws IOException
     * */
    private void writeOnPrinter() throws IOException{

        if(outputStream!=null && messageString!=null){
            outputStream.write(messageString.getBytes());
            System.out.println("escribiendo" + messageString);
        }
    }

    /**
     * This method close the COM port connection.
     * */
    private void closeConnection(){
        serialPort.close();
    }

    /**
     * This method retrieve the COM port configured and try to print in it.
     * */
    private void print(boolean showErrorMessage){
        try{
            retrieveComPorts();
            String filePath = System.getProperty(
                    JAVA_HOME) + File.separator + BIN_FOLDER + File.separator +CONFIGURATION_FILE_NAME;
            portConfiguration.readFromDataFile(filePath);
            if(portConfiguration.hasValidInformation()){
                while(portList.hasMoreElements()){
                    portId = (CommPortIdentifier) portList.nextElement();
                    if (portId.getPortType() == CommPortIdentifier.PORT_SERIAL) {
                        if (portId.getName().equals(portConfiguration.getCurrentPort())) {
                            openConnection();
                            writeOnPrinter();
                            closeConnection();
                            showSuccessFullResult(portConfiguration.getCurrentPort());
                            return;
                        }
                    }
                }
                //In case something is wrong
                String messagePart1="No se puede imprimir el ticket. La impresora no está lista.";
                String messagePart2="Presione el boton Reintentar";
                showErrorMessage("No se puede conectar con el puerto "+portConfiguration.getCurrentPort(),messagePart1,messagePart2,showErrorMessage);
            }
            else{
                String messagePart1="La configuración de la impresora es incorrecta!";
                String messagePart2="Ejecute el programa de configuración.";
                showErrorMessage("No existe configuración",messagePart1,messagePart2,showErrorMessage);
                this.remove(retryButton);
            }
        }
        catch(PortInUseException piuex){
        	piuex.printStackTrace();
        }catch(Exception e){
            e.printStackTrace();
            JOptionPane.showMessageDialog(this,"[Error] "+e.getMessage());
            String messagePart1="No se pudo imprimir el ticket!";
            String messagePart2="Ejecute el programa de configuración.";
            showErrorMessage("Ocurrió un error "+portConfiguration.getCurrentPort(),messagePart1,messagePart2,showErrorMessage);
        }
    }

    private void showErrorMessage(String errorMessage, String messagePart1,String messagePart2,boolean showErrorMessage){
        if(showErrorMessage)
            JOptionPane.showMessageDialog(
                    this,errorMessage,
                    "Tepsa",
                    JOptionPane.
                    ERROR_MESSAGE);
        successFullMesagePart1.setText(messagePart1);
        successFullMesagePart2.setText(messagePart2);
    }

    private void showSuccessFullResult(String configuredPort){
        successFullMesagePart1.setText( "Se imprimió el documento correctamente ["+configuredPort+"]");
        successFullMesagePart2.setText( "Cierre esta ventana para continuar");
        this.remove(retryButton);
    }


    /**
     * This method return the enumeration of the PORTS available in the PC. First refresh the javax.comm.properties
     * loading it again to give the user the opportunity of reconnect the device
     * */
    private static Enumeration<CommPortIdentifier> enumerateSerialPorts()
    {
        //doesn't read or write global variables. Completely self contained.
        Enumeration <CommPortIdentifier> portList;
        try
        {
            Field masterIdList_Field = CommPortIdentifier.
            class.getDeclaredField(MASTER_ID_LIST);
            masterIdList_Field.setAccessible(true);
            masterIdList_Field.set(	null, null);
            String temp_string = System.getProperty(
                    JAVA_HOME) + File.separator + LIB_FOLDER + File.separator + JAVA_COM_PROPERTIES_FILE;
            Method loadDriver_Method = CommPortIdentifier.
            class.getDeclaredMethod(LOAD_DRIVER_METHOD, new Class[] {String.class});
            loadDriver_Method.setAccessible(true); //unprotect it
            loadDriver_Method.invoke(null, new Object[] {temp_string});
        }
        catch(Exception e)
        {
            System.out.println(e); //***** add logger
        }
        portList = CommPortIdentifier.getPortIdentifiers();
        return portList;
    }

    private boolean isEnvironmentConfigurated(){
        successFullMesagePart1.setText("Se está comprobando la configuración");
        successFullMesagePart2.setText("de impresión. Por favor espere...");
        String path = System.getProperty(
                JAVA_HOME) + File.separator + LIB_FOLDER + File.separator;
        File java_comm_properties = new File(path+ JAVA_COM_PROPERTIES_FILE);
        if(!java_comm_properties.exists()){
            //copyConfigurationFile(JAVA_COM_PROPERTIES_FILE,path);
            return false;
        }

        path = System.getProperty(
                JAVA_HOME) + File.separator + BIN_FOLDER + File.separator ;
        File win32dll = new File(path + WIN32_DLL);
        if(!win32dll.exists()){
            //copyConfigurationFile(WIN32_DLL,path);
            return false;
        }

        path = System.getProperty(
                JAVA_HOME) + File.separator + LIB_FOLDER + File.separator + EXT_FOLDER+ File.separator;
        File comm_jar = new File(path+ COMM_JAR);
        if(!comm_jar.exists()){
            //copyConfigurationFile(COMM_JAR,path);
            return false;
        }
        return true;

    }

    private void copyConfigurationFile(String file, String destination){

        try {
            URL url  = new URL(getRepositoryFileName(file));
            InputStream in = url.openStream();
            OutputStream out = new FileOutputStream(destination);


            byte[] buf = new byte[1024];
            int len;
            while ((len = in.read(buf)) > 0) {
                out.write(buf, 0, len);
            }
            in.close();
            out.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private String getRepositoryFileName(String file){
        return TEPSA_COMERCIAL_URL + "/" + APPLICATION_NAME + "/" + file;
    }


    @Override
	public void actionPerformed(ActionEvent ae) {
        if(ae.getSource()==retryButton){
            print(true);
        }
    }

    public class PortConfigurationInfo{
        private String currentPort = null;
        private int currentBitsPerSecond = 0;
        private int currentDataBits = 0;
        private String currentParity = null;
        private String currentStopBits = null;


        public String getCurrentPort()
        {
            return currentPort;
        }
        public void setCurrentPort(String currentPort)
        {
            this.currentPort = currentPort;
        }
        public int getCurrentBitsPerSecond()
        {
            return currentBitsPerSecond;
        }
        public void setCurrentBitsPerSecond(int currentBitsPerSecond)
        {
            this.currentBitsPerSecond = currentBitsPerSecond;
        }
        public int getCurrentDataBits()
        {
            return currentDataBits;
        }
        public void setCurrentDataBits(int currentDataBits)
        {
            this.currentDataBits = currentDataBits;
        }
        public String getCurrentParity()
        {
            return currentParity;
        }
        public void setCurrentParity(String currentParity)
        {
            this.currentParity = currentParity;
        }
        public String getCurrentStopBits()
        {
            return currentStopBits;
        }
        public void setCurrentStopBits(String currentStopBits)
        {
            this.currentStopBits = currentStopBits;
        }

        public boolean hasValidInformation(){
            return currentPort!=null &&
            currentBitsPerSecond != 0 &&
            currentDataBits != 0&&
            currentParity != null &&
            currentStopBits != null;

        }

        public void readFromDataFile(String filePath){
            try
            {
                File configurationFile=new File(filePath);
                if(!configurationFile.exists())
                    setCurrentPort (null);

                FileInputStream fis=null;
                fis = new FileInputStream(configurationFile);
                @SuppressWarnings("resource")
				BufferedReader input = new BufferedReader(new InputStreamReader(fis));

                String currentPort = input.readLine();

                if(currentPort!=null){
                    if(!currentPort.startsWith(COM))
                        setCurrentPort(null);
                    else{
                        setCurrentPort (currentPort);
                        setCurrentBitsPerSecond (Integer.valueOf(input.readLine()));
                        setCurrentDataBits (Integer.valueOf(input.readLine()));
                        setCurrentParity ( input.readLine());
                        setCurrentStopBits ( input.readLine());
                    }

                }
            }
            catch(Exception e){
                e.printStackTrace();
                setCurrentPort (null);
            }
        }

    }
}