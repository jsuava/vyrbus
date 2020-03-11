/**
 * Proyecto		: SISVYR
 * Sistema		: Sistema de Ventas y Reservas
 * Descripción	: Clase que permite mostrar el applet de impresion al puerto serial.
 * Autor		: José Avalos Sullo
 * Fecha		: 09/06/2014
 */
package com.cystesoft.vyrbus.view.tuentrada;

import java.util.Date;
import java.util.List;

import org.zkoss.zul.Applet;
import org.zkoss.zul.Label;

import com.cystesoft.vyrbus.model.bean.Agencia;
import com.cystesoft.vyrbus.model.bean.VentaPasaje;
import com.cystesoft.vyrbus.service.locator.ServiceLocator;
import com.cystesoft.vyrbus.service.util.Util;
import com.cystesoft.vyrbus.view.ui.WndBase;

/**
 * @author Jose
 *
 */
public class WndTicketTuentrada extends WndBase {
	private static final long serialVersionUID = 1L;
	private static final String DATE_DEFAULT_FORMAT = "dd/MM/yyyy";
	private static final String URL_CONFIGURATION_FILES_INSTALLER = "http://localhost:8080/sisvyr/installer.exe";
	public static final String FORMAT_BOLETO="BOLETO";
	public static final String FORMAT_BOLETO_IDA_VUELTA="BOLETO_IDA_VUELTA";
	public static final String FORMAT_LIQUIDACION_TURNO="LIQUIDACION_TURNO";
	
	private Applet comPrinter;
	private Label lblStatus;
	private Label lblNumeroControl;
	
	private List<VentaPasaje> lstVentaPasaje = null;
	
	private String formato;
	private String msg;
//	private String urlDocumento;	
	
	@SuppressWarnings("unchecked")
	public void onCreate() throws Exception{
		lstVentaPasaje = (List<VentaPasaje>)this.getAttribute("lstVentas");
		String nControl = (String)this.getAttribute("numeroControl");
		formato = (String)this.getAttribute("formato");
		msg = (String)this.getAttribute("msg");
//		urlDocumento = (String)this.getAttribute("urlDocumento");
		
		lblStatus.setValue(msg);
		
		if(formato.equals(FORMAT_BOLETO)){
			comPrinter.setParam("msg",proccessBytesToSendVoucher(lstVentaPasaje));
			comPrinter.setParam("URLInstaller", URL_CONFIGURATION_FILES_INSTALLER);
//			lblStatus.setValue("Se imprimirán "+sales.size()+" ventas(s)");			
		}else if (formato.equals(FORMAT_LIQUIDACION_TURNO)){
			comPrinter.setParam("msg",proccessBytesToSendClearance());
			comPrinter.setParam("URLInstaller",URL_CONFIGURATION_FILES_INSTALLER);
//			lbStatus.setValue("Se imprimirán 1 liquidacion");			
		}
		
		if(nControl!=null)
			lblNumeroControl.setValue(nControl);
		this.doModal();
	}
	
	
	
	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.view.ui.WndBase#initComponents()
	 */
	@Override
	public void initComponents() {
		super.initComponents();
		comPrinter = (Applet)this.getFellow("comPrinter");
		lblStatus = (Label)this.getFellow("lblStatus");
		lblNumeroControl = (Label)this.getFellow("lblNumeroControl");
	}

	/**
	 * Metodo que procesa las ventas.
	 * @param lstSales	: Lista de ventas a imprimir.
	 * @return 
	 * @throws Exception 
	 */
	private String proccessBytesToSendVoucher(List<VentaPasaje> lstSales) throws Exception{
		if(lstSales.size()==1){
			return proccessBytesOneSale(lstSales.get(0));		
		}
		else
			return proccessBytesManySales(lstSales);
	}
	
	/**
	 * Metodo para crear el texto a imprimir en la liquidación de turno
	 * @return
	 */
	private String proccessBytesToSendClearance(){
		
		String usuario = (String)this.getAttribute("usuario");
		String login = (String)this.getAttribute("login");
		Integer cantidadContado = (Integer)this.getAttribute("cantidadContado");
		Double montoContado = (Double)this.getAttribute("montoContado");
		Integer cantidadTarjeta = (Integer)this.getAttribute("cantidadTarjeta");
		Double montoTarjeta = (Double)this.getAttribute("montoTarjeta");
		String fechaLiquidacion = (String)this.getAttribute("fechaLiquidacion");
		StringBuffer sb = new StringBuffer();
		sb.append("<n>");
        sb.append("<RC1,300><F6>TEPSA");
        sb.append("<RC60,220><F3><BS15,15>LIQUIDACION DE VENTAS");
        sb.append("<RC100,100><F9>PTO. VENTA");
        sb.append("<RC120,100><F3><BS15,15>TUENTRADA");
        sb.append("<RC160,100><F9>USUARIO");
        sb.append("<RC180,100><F3><BS15,15>"+usuario);
        sb.append("<RC220,450><F9>CANTIDAD");
        sb.append("<RC220,750><F9>TOTAL");
        sb.append("<RC240,150><F9>VENTA CONTADO");
        sb.append("<RC240,480><F3>"+cantidadContado);
        sb.append("<RC240,730><F3>"+Util.toNumberFormat(montoContado, 2));
        sb.append("<RC280,150><F9>VENTA CON T. CREDITO");
        sb.append("<RC280,480><F3>"+cantidadTarjeta);
        sb.append("<RC280,730><F3>"+Util.toNumberFormat(montoTarjeta, 2));
        sb.append("<RC340,150><F9>TOTAL");
        sb.append("<RC340,480><F3>"+(cantidadContado+cantidadTarjeta));
        sb.append("<RC340,730><F3>"+Util.toNumberFormat(montoContado+montoTarjeta, 2));
        sb.append("<RC380,150><F3><BS15,15>TOTAL EFECTIVO A ENTREGAR");
        sb.append("<RC380,730><F10>"+Util.toNumberFormat(montoContado, 2));
        sb.append("<RC100,1050><RR><F9>www.tepsa.com.pe");
        sb.append("<RC1,1020><RR><F9>USUARIO:");
        sb.append("<RC250,1020><RR><F9>"+login);
        sb.append("<RC1,990><RR><F9>FECHA DE OPERACION:");
        sb.append("<RC250,990><RR><F9>"+fechaLiquidacion);
        sb.append("<p>");
		
		
		return sb.toString();
	}	
	
	/**
	 * Metodo para crear el texto para imprimir el boleto de viaje.
	 * @param ventaPasaje
	 * @return
	 * @throws Exception 
	 */
	private String proccessBytesOneSale(VentaPasaje ventaPasaje) throws Exception{
		Agencia puntoEmbarque=ServiceLocator.getAgenciaManager().buscarPorId(ventaPasaje.getAgenciaPartida().getId().longValue());
		String asiento = "00"+ventaPasaje.getNumeroAsiento().toString();
		StringBuffer sb = new StringBuffer();
		sb.append("<n>");
		sb.append("<RC50,170>TEPSA");
		sb.append("<RC100,50><F9>ORIGEN");
		sb.append("<RC100,250><F9>DESTINO");
		sb.append("<RC100,450><F9>F. VIAJE");
		sb.append("<RC100,730><F9>H. VIAJE");
		sb.append("<RC120,50><F3><BS15,15>"+ventaPasaje.getRuta().getOrigen());
		sb.append("<RC120,250><F3><BS15,15>"+ventaPasaje.getRuta().getDestino());
		sb.append("<RC120,450><F3><BS15,15>"+Util.DatetoString(ventaPasaje.getFechaPartida(),DATE_DEFAULT_FORMAT));
//		sb.append("<RC120,730><F3>"+ sales.getItinerario().getHoraPartida().substring(0,sales.getItinerario().getHoraPartida().length()-3));
//		sb.append("<RC120,730><F3>"+ ventaPasaje.getHoraPartida().substring(0,ventaPasaje.getHoraPartida().length()-3));
		sb.append("<RC120,730><F3>"+ ventaPasaje.getHoraPartida().toString());
		sb.append("<RC170,50><F9>PASAJERO");
		sb.append("<RC170,730><F9>DOC. IDENTIDAD");
		sb.append("<RC190,50><F3><BS15,12>"+ventaPasaje.getPasajero().toString());
		sb.append("<RC190,730><F3><BS15,12>"+ventaPasaje.getPasajero().getNumeroDocumento());			
		sb.append("<RC230,50><F9>EMPRESA");
		sb.append("<RC230,730><F9>RUC");
		if(ventaPasaje.getCliente()!=null){
			sb.append("<RC250,50><F3><BS15,12>"+ventaPasaje.getCliente().getRazonSocial());
			sb.append("<RC250,730><F3><BS15,12>"+ventaPasaje.getCliente().getNumeroDocumento());
		}else{
			sb.append("<RC250,50><F3><BS15,12>");
			sb.append("<RC250,730><F3><BS15,12>");
		}
		sb.append("<RC290,50><F9>IMPORTE");
		sb.append("<RC290,200><F9>ASIENTO");
		sb.append("<RC290,350><F9>EMBARQUE");
		sb.append("<RC290,730><F9>NRO. CONTROL");
		sb.append("<RC310,5><F3>"+"S/ "+Util.toNumberFormat(ventaPasaje.getImportePagado(), 2));
		sb.append("<RC310,200><F3>"+asiento.substring(asiento.length()-2));
		sb.append("<RC310,350><F3><BS15,12>"+puntoEmbarque.getNombreCorto().toUpperCase());
		sb.append("<RC310,730><F3><BS15,15>"+ventaPasaje.getNumeroControl());
		sb.append("<RC360,100><F2>1) El Ticket es Personal e intransferible.");
		sb.append("<RC380,100><F2>2) Este documento no tiene validez con fines tributarios.");
		sb.append("<RC400,100><F2>3) El Ticket debe ser canjeado 30 minutos antes de la fecha y hora de viaje.");
		sb.append("<RC420,100><F2>4) Las condiciones generales que rigen este contrato figuran en el boleto de viaje.");
		sb.append("<RC448,100><F9> USUARIO : "+ventaPasaje.getUsuario().getLogin() + "         F.OPERACION :"+(ventaPasaje.getFechaInsercion()==null?Util.DatetoString(new Date(), DATE_DEFAULT_FORMAT):Util.DatetoString(ventaPasaje.getFechaInsercion(), DATE_DEFAULT_FORMAT)));
		
//		sb.append("<RC1,300><F10><BS15,12>e-Ticket:");
//		sb.append("<RC1,450><F10><BS15,15>"+ventaPasaje.getNumeroBoleto());
		sb.append("<RC1,300><F10><BS18,12>e-Ticket:");
		sb.append("<RC1,450><F10><BS18,15>"+" "+ventaPasaje.getNumeroBoleto());
		
		//#comen 26/01/2015 - jabanto
//		sb.append("<RC100,1050><RR><F9>www.tepsa.com.pe");
//		sb.append("<RC1,1020><RR><F9>USUARIO:");
//		sb.append("<RC250,1020><RR><F9>"+ventaPasaje.getUsuario().getLogin());
//		sb.append("<RC1,990><RR><F9>FECHA DE OPERACION:");
//		sb.append("<RC250,990><RR><F9>"+(ventaPasaje.getFechaInsercion()==null?Util.DatetoString(new Date(), DATE_DEFAULT_FORMAT):Util.DatetoString(ventaPasaje.getFechaInsercion(), DATE_DEFAULT_FORMAT)));
		
		sb.append("<RC100,1090><RR><F9>www.tepsa.com.pe");
		sb.append("<RC1,1050><RR><F9>RUC");
		sb.append("<RC135,1050><RR><F9>"+" : 20502324927");
		sb.append("<RC1,1025><RR><F9>RAZON SOCIAL");
		sb.append("<RC135,1025><RR><F9>"+" :TRANSPORTES EL PINO S.A.C.");
		sb.append("<RC1,1000><RR><F9> AV. MANUEL ECHEANDIA 303 - SAN LUIS");
		
		sb.append("<p>");
		return sb.toString();

	}
	
	/**
	 * Metodo para crear el texto que se enviara al puerto serial.
	 * @param lstSales	: Boletos que se desea imprimir.
	 * @return Texto a imprimir por el puerto serial.
	 */
	private String proccessBytesManySales(List<VentaPasaje> lstSales){
		VentaPasaje ventaPasaje = lstSales.get(0);
		StringBuffer sb = new StringBuffer();
		sb.append("<n>");
		sb.append("<RC1,220>TEPSA");
		sb.append("<RC1,400><F3>www.tepsa.com.pe");
		sb.append("<RC50,170><F9>ORIGEN<RC50,370><F9>DESTINO<RC50,570><F9>F. VIAJE<RC50,770><F9>H. VIAJE");
		sb.append("<RC70,170><F3><BS15,15>");
		sb.append(ventaPasaje.getRuta().getOrigen().toUpperCase());
		sb.append("<RC70,370><F3><BS15,15>");
		sb.append(ventaPasaje.getRuta().getDestino().toUpperCase());
		sb.append("<RC70,570><F3><BS15,15>");
		sb.append(Util.DatetoString(ventaPasaje.getFechaPartida(), DATE_DEFAULT_FORMAT));
		sb.append("<RC70,770><F3>");
//		sb.append(ventaPasaje.getItinerario().getHoraPartida().substring(0,ventaPasaje.getItinerario().getHoraPartida().length()-3));
		sb.append(ventaPasaje.getHoraPartida().toString());
		sb.append("<RC130,50><F9>EMPRESA:");
		if(ventaPasaje.getCliente()!=null){
			sb.append("<RC120,200><F5><BS15,15>");
			sb.append(ventaPasaje.getCliente().getRazonSocial());
		}else{
			sb.append("<RC120,200><F5><BS15,15>");			
		}
		sb.append("<RC170,50><F9>RUC:");
//		if(ventaPasaje.getPasajero().getId().intValue()!=ventaPasaje.getCliente().getId().intValue()){
		if(ventaPasaje.getCliente()!=null){
			sb.append("<RC160,200><F5>");
			sb.append(ventaPasaje.getCliente().getNumeroDocumento());
		}else{
			sb.append("<RC160,200><F5>");			
		}
		sb.append("<RC170,530><F9>PTO.EMB.:");
		sb.append("<RC160,700><F5><BS15,15>");
		sb.append(ventaPasaje.getAgencia().getDenominacion().toUpperCase());
		sb.append("<RC210,50><F3><BS15,15>PASAJERO");
		sb.append("<RC210,530><F3><BS15,15>DOC.ID.");
		sb.append("<RC210,730><F3><BS15,15>E-TICKET");
		sb.append("<RC210,900><F3><BS15,15>ASTO");
		sb.append("<RC210,980><F3><BS15,15>NETO");
		int thickness = 30;
		int initialPosition = 250;
		for(int i=0;i<lstSales.size();i++){
			int finalPosition =initialPosition+(i*thickness); 
			sb.append("<RC");
			sb.append(finalPosition);
			sb.append(",50><F9>");
			sb.append(lstSales.get(i).getPasajero().toString());
			sb.append("<RC");
			sb.append(finalPosition);
			sb.append(",530><F9>");
			sb.append(lstSales.get(i).getPasajero().getNumeroDocumento());
			sb.append("<RC");
			sb.append(finalPosition);
			sb.append(",730><F9>");
			sb.append(lstSales.get(i).getNumeroBoleto());
			sb.append("<RC");
			sb.append(finalPosition);
			sb.append(",900><F9>");
			sb.append(lstSales.get(i).getNumeroAsiento());
			sb.append("<RC");
			sb.append(finalPosition);
			sb.append(",980><F9>");
			sb.append(Util.toNumberFormat(lstSales.get(i).getImportePagado(), 2));
		}
		sb.append("<RC370,100><F2>1) El Ticket es Personal e intransferible.");
		sb.append("<RC390,100><F2>2) Este documento no tiene validez con fines tributarios.");
		sb.append("<RC410,100><F2>3) El Ticket debe ser canjeado 30 minutos antes de la fecha y hora de viaje.");
		sb.append("<RC430,100><F2>4) Las condiciones generales que rigen este contrato figuran en el boleto de viaje.");

		sb.append("<RC450,400><F2>");
		sb.append(ventaPasaje.getUsuario().getLogin());
		sb.append(" - ");
		sb.append(ventaPasaje.getFechaInsercion()==null?Util.DatetoString(new Date(), DATE_DEFAULT_FORMAT):Util.DatetoString(ventaPasaje.getFechaInsercion(), DATE_DEFAULT_FORMAT));
		sb.append("<p>");

		return sb.toString();
	}
}
