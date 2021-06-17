/**
 * Proyecto		: SISVYR
 * Sistema		: Sistema de Ventas y Reservas
 * Descripción	: Clase que permite mostrar el applet de impresion al puerto serial.
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
 * @author Jose abanto
 *
 */
public class WndTicketTuentradaRetorno extends WndBase {
	private static final long serialVersionUID = 1L;
	private static final String DATE_DEFAULT_FORMAT = "dd/MM/yyyy";
	private static final String URL_CONFIGURATION_FILES_INSTALLER = "http://localhost:8080/sisvyr/installer.exe";
	public static final String FORMAT_BOLETO="BOLETO";
	
	private Applet comPrinter;
	private Label lblStatus;
	private Label lblNumeroControl;
	private List<VentaPasaje> lstVentaPasaje = null;
	private String formato;
	private String msg;
	
	@Override
	@SuppressWarnings("unchecked")
	public void onCreate() throws Exception{
		lstVentaPasaje = (List<VentaPasaje>)this.getAttribute("lstVentas");
		String nControl = (String)this.getAttribute("numeroControl");
		formato = (String)this.getAttribute("formato");
		msg = (String)this.getAttribute("msg");
		
		lblStatus.setValue(msg);
		
		if(formato.equals(FORMAT_BOLETO)){
			comPrinter.setParam("msg",proccessBytesToSendVoucher(lstVentaPasaje));
			comPrinter.setParam("URLInstaller", URL_CONFIGURATION_FILES_INSTALLER);		
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
		return proccessBytesOneSale(lstSales.get(0));		
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
		
		sb.append("<RC1,300><F10><BS18,12>e-Ticket:");
		sb.append("<RC1,450><F10><BS18,15>"+" "+ventaPasaje.getNumeroBoleto());
		
		sb.append("<RC100,1090><RR><F9>www.tepsa.com.pe");
		sb.append("<RC1,1050><RR><F9>RUC");
		sb.append("<RC135,1050><RR><F9>"+" : 20502324927");
		sb.append("<RC1,1025><RR><F9>RAZON SOCIAL");
		sb.append("<RC135,1025><RR><F9>"+" :TRANSPORTES EL PINO S.A.C.");
		sb.append("<RC1,1000><RR><F9> AV. MANUEL ECHEANDIA 303 - SAN LUIS");
		
		sb.append("<p>");
		return sb.toString();
	}

}
