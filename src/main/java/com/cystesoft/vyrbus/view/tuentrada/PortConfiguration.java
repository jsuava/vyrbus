/**
 * Proyecto		: SISVYR
 * Sistema		: Sistema de Ventas y Reservas
 * Descripción	: Clase para configurar los parametros del puerto serial en el cliente.
 * Autor		: José Avalos Sullo
 * Fecha		: 09/06/2014
 */
package com.cystesoft.vyrbus.view.tuentrada;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;


/**
 * description
 * @author José Avalos
 * @version 1.0
 */
@SuppressWarnings({"unchecked","rawtypes"})
public class PortConfiguration extends JPanel implements ActionListener, Serializable
{
    
	private static final long serialVersionUID = 1L;
	/**
     * This variable contains the "loadDriver" value
     * */
    private static String LOAD_DRIVER_METHOD = "loadDriver";
    /**
     * This variable contains the master id field value
     * */
    private static String MASTER_ID_LIST = "masterIdList";
    /**
     * This variable contains the lib value
     * */
    private static String LIB_FOLDER = "lib";
    /**
     * This variable contains the bin value
     * */
    private static String BIN_FOLDER = "bin";    
    /**
     * This variable contains the Java home value
     * */
    private static String JAVA_HOME = "java.home";
    /**
     * This variable contains the file name where the configuration will be
     * */
    private static String CONFIGURATION_FILE_NAME = "tepsacom";
    /**
     * This variable contains the javax.comm.properties value
     * */
    private static String JAVA_COM_PROPERTIES_FILE = "javax.comm.properties";
    /**
     * This variable contains the label for select a COM port
     * */
    private JLabel lbPortsAvailables = new JLabel("Puerto:");
    /**
     * This variable contains the combobox where the COM port availables are
     * */
    private JComboBox cmbPortsAvailables = new JComboBox();
    /**
     * This variable contains the label for bits per second
     * */
    private JLabel lbBitsPerSecond = new JLabel("Bits por segundo:");    
    /**
     * This variable contains the combobox where the bits por second are
     * */    
    private JComboBox cmbBitsPorSecond = new JComboBox();
    /**
     * This variable contains the label for databits
     * */
    private JLabel lbDatabits = new JLabel("Databits:");        
    /**
     * This variable contains the combobox where the data bits are
     * */
    private JComboBox cmbDataBits = new JComboBox();     
    /**
     * This variable contains the label for databits
     * */
    private JLabel lbParity = new JLabel("Paridad:");            
    /**
     * This variable contains the combobox where the parity options are
     * */
    private JComboBox cmbParityOptions = new JComboBox();
    /**
     * This variable contains the label for databits
     * */
    private JLabel lbStopBits = new JLabel("Bits de stop:");                
    /**
     * This variable contains the combobox where the stop bits are
     * */
    private JComboBox cmbStopBits = new JComboBox();

    /**
     * This variable contains the COM string
     * */
    private static String COM = "COM";
    /**
     * This variable will inform to the user the status of the configuration
     * */
    private JLabel lbStatus = new JLabel();
    /**
     * This variable is the button when the user press aceptar
     **/
    JButton acceptButton = new JButton("Aceptar");
    /**
     * This variable is the button when the user press cancelar
     **/    
    JButton cancelButton = new JButton("Cancelar");    
    /**
     * This variable is the main frame where the visual components are
     **/    
    private static JFrame frame = new JFrame("Configuración de puerto");   

    private String currentPort = null;
    private String currentBitsPerSecond = null;
    private String currentDataBits = null;
    private String currentParity = null;
    private String currentStopBits = null;

    /**
     * Default constructor 
     * */
    public PortConfiguration()
    {

        super(new GridBagLayout());
        fillCombobox();
        jbInit();
        setInitialSize();
        acceptButton.addActionListener(this);
        cancelButton.addActionListener(this);

        readCurrentConfiguration();
        showCurrentConfiguration();
    }

    /**
     * Fill the combobox with data
     * */
    private void fillCombobox(){
        String[] availablePorts = getPortsAsStrings();
        cmbPortsAvailables = new JComboBox(availablePorts);        

        String[] bitPorSeconds = new String[]{"75","110","134","150","300","600","1200","1800",
                "2400","4800","7200","9600","14400","19200","38400","57600","11520","12800"};
        cmbBitsPorSecond = new JComboBox(bitPorSeconds);        

        String[] dataBits = new String[]{"5","6","7","8"};        
        cmbDataBits = new JComboBox(dataBits);

        String[] parityOptions = new String[]{"Par","Impar","Marca","Espacio","Sin paridad"};        
        cmbParityOptions = new JComboBox(parityOptions);

        String[] stopBits = new String[]{"1","1.5","2"};        
        cmbStopBits = new JComboBox(stopBits);                
    }
    /**
     * Set the initial size
     * */
    private void setInitialSize(){
        lbStatus.setSize(new Dimension(20,40));
        acceptButton.setSize(new Dimension(20,40));
        cancelButton.setSize(new Dimension(20,40));        
    }

    /**
     * This method is called just one time for the constructor
     * */
    public void jbInit(){
        frame.setMinimumSize(new Dimension(250, 200));
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 5, 5, 5);        

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.gridwidth=2;
        lbStatus.setBorder(BorderFactory.createTitledBorder("Configuración actual"));
        add(lbStatus, gbc);        

        JPanel panel = new JPanel();
        panel.setLayout(new GridBagLayout());
        panel.setBorder(BorderFactory.createTitledBorder("Editar detalles"));

        //Add subcomponents        
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth=1;
        gbc.anchor = GridBagConstraints.WEST;
        panel.add(lbPortsAvailables, gbc);

        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.gridwidth=1;
        gbc.anchor = GridBagConstraints.WEST;
        panel.add(cmbPortsAvailables, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth=1;
        gbc.anchor = GridBagConstraints.WEST;
        panel.add(lbBitsPerSecond, gbc);

        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.gridwidth=1;
        gbc.anchor = GridBagConstraints.WEST;
        panel.add(cmbBitsPorSecond, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth=1;
        gbc.anchor = GridBagConstraints.WEST;
        panel.add(lbDatabits, gbc);

        gbc.gridx = 1;
        gbc.gridy = 2;
        gbc.gridwidth=1;
        gbc.anchor = GridBagConstraints.WEST;
        panel.add(cmbDataBits, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth=1;
        gbc.anchor = GridBagConstraints.WEST;
        panel.add(lbParity, gbc);

        gbc.gridx = 1;
        gbc.gridy = 3;
        gbc.gridwidth=1;
        gbc.anchor = GridBagConstraints.WEST;
        panel.add(cmbParityOptions, gbc);

        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth=1;
        gbc.anchor = GridBagConstraints.WEST;
        panel.add(lbStopBits, gbc);

        gbc.gridx = 1;
        gbc.gridy = 4;
        gbc.gridwidth=1;
        gbc.anchor = GridBagConstraints.WEST;
        panel.add(cmbStopBits, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth=2;
        //        gbc.gridheight=4;
        gbc.anchor = GridBagConstraints.CENTER;
        add(panel,gbc);
        //add(panel);

        gbc.gridx = 0;  
        gbc.gridy = 2;
        gbc.gridwidth=1;
        gbc.anchor = GridBagConstraints.CENTER;
        add(acceptButton, gbc);

        gbc.gridx = 1;
        gbc.gridy = 2;
        gbc.gridwidth=1;
        gbc.anchor = GridBagConstraints.CENTER;
        add(cancelButton, gbc);        
    }


    /**
     * Create the GUI and show it.  For thread safety,
     * this method should be invoked from the
     * event-dispatching thread.
     */
    private static void createAndShowGUI() {
        //Create and set up the window.

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //Create and set up the content pane.
        JComponent newContentPane = new PortConfiguration();
        newContentPane.setOpaque(true); //content panes must be opaque
        frame.setContentPane(newContentPane);

        //Display the window.
        frame.pack();
        frame.setVisible(true);
    }

    /**
     * Verify the current configuration (reading the file)
     * @return String current port configurated p.e. COM1
     * */
    private void readCurrentConfiguration(){
        try
        {
            String path = System.getProperty(
                    JAVA_HOME) + File.separator + BIN_FOLDER + File.separator;    
            File configurationFile=new File(path+CONFIGURATION_FILE_NAME);
            if(!configurationFile.exists())                
                currentPort = null;

            FileInputStream fis=null;
            fis = new FileInputStream(configurationFile);
            @SuppressWarnings("resource")
			BufferedReader input = new BufferedReader(new InputStreamReader(fis));

            currentPort = input.readLine();
            currentBitsPerSecond = input.readLine();
            currentDataBits = input.readLine();
            currentParity = input.readLine();
            currentStopBits = input.readLine();

            if(currentPort!=null){
                if(!currentPort.startsWith(COM))
                    currentPort=null;

            } 
        } catch (FileNotFoundException e)
        {
            e.printStackTrace();
            currentPort = null;
        }
        catch (IOException e)
        {
            e.printStackTrace();
            currentPort = null;
        }             
    }
    
    private void showCurrentConfiguration(){
        if(currentPort==null)
            lbStatus.setText("No existe la configuración");
        else{
            lbStatus.setText("Puerto: "+currentPort);
            cmbPortsAvailables.setSelectedItem(currentPort);
        }      
        
        if(currentBitsPerSecond!=null){
            cmbBitsPorSecond.setSelectedItem(currentBitsPerSecond);            
        }
        if(currentDataBits!=null){
            cmbDataBits.setSelectedItem(currentDataBits);            
        }
        if(currentParity!=null){
            cmbParityOptions.setSelectedItem(currentParity);            
        }
        if(currentStopBits!=null){
            cmbStopBits.setSelectedItem(currentStopBits);            
        }
        
    }
    
    /**
     * This method return the list of ports availables as a String[]
     * @return String[]List of availables ports
     * */
	private String []getPortsAsStrings(){
        List<String> lstPortsAsList = new ArrayList<String>();
        String [] strPortAsList = new String[]{};
        Enumeration portList = enumerateSerialPorts();
        while(portList.hasMoreElements()){
			CommPortIdentifier portId = (CommPortIdentifier) portList.nextElement();
            if (portId.getPortType() == CommPortIdentifier.PORT_SERIAL) {
                lstPortsAsList.add(portId.getName());
            }
        }
        strPortAsList = lstPortsAsList.toArray(strPortAsList);
        return strPortAsList;
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
            masterIdList_Field.set( null, null);
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

    /**
     * This method write the selected configuration
     * */
    private boolean writeConfiguration(){
        try
        {
            String path = System.getProperty(
                    JAVA_HOME) + File.separator + BIN_FOLDER + File.separator;    
            PrintWriter printWriter;
            printWriter = new PrintWriter(
                    new BufferedWriter(new FileWriter(path+CONFIGURATION_FILE_NAME)));
            printWriter.println(cmbPortsAvailables.getSelectedItem().toString());
            printWriter.println(cmbBitsPorSecond.getSelectedItem().toString());
            printWriter.println(cmbDataBits.getSelectedItem().toString());
            printWriter.println(cmbParityOptions.getSelectedItem().toString());
            printWriter.println(cmbStopBits.getSelectedItem().toString());            
            printWriter.close();
            return true;
        } catch (IOException e)
        {
            e.printStackTrace();
        }
        return false;
    }

    
    @Override
	public void actionPerformed(ActionEvent e)
    {
        if(e.getSource()==acceptButton){
            if( writeConfiguration()){
                JOptionPane.showMessageDialog(this,"Se configuró correctamente ["+cmbPortsAvailables.getSelectedItem().toString()+"]");
                frame.setVisible(false);
                frame.dispose();
            }
            else{
                JOptionPane.showMessageDialog(this,"Hubo un error al configurar");
            }
        }
        else if(e.getSource()==cancelButton){
            frame.setVisible(false);
            frame.dispose();                
        }

    }

    public static void main(String[] args) {
        //Schedule a job for the event-dispatching thread:
        //creating and showing this application's GUI.
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            @Override
			public void run() {
                createAndShowGUI();
            }
        });
    }
}
