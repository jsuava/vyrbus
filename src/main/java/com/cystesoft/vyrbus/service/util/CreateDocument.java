/**
 * Proyecto		: SISVYR
 * Sistema		: Sistema de Ventas y Reservas
 * Descripciï¿½n	: 
 * Autor		: Josï¿½ Sullo Avalos
 * Fecha		: 17/12/2012
 */
package com.cystesoft.vyrbus.service.util;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.jfree.util.Log;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listitem;

import com.cystesoft.vyrbus.model.bean.Agencia;
import com.cystesoft.vyrbus.model.bean.CentroCosto;
import com.cystesoft.vyrbus.model.bean.DetalleEquipaje;
import com.cystesoft.vyrbus.model.bean.DetalleFlotaHRE;
import com.cystesoft.vyrbus.model.bean.Equipaje;
import com.cystesoft.vyrbus.model.bean.Gasto;
import com.cystesoft.vyrbus.model.bean.HRE;
import com.cystesoft.vyrbus.model.bean.Itinerario;
import com.cystesoft.vyrbus.model.bean.ItinerarioAgenciaPartida;
import com.cystesoft.vyrbus.model.bean.ItinerarioAgenciaPartidaID;
import com.cystesoft.vyrbus.model.bean.Liquidacion;
import com.cystesoft.vyrbus.model.bean.Manifiesto;
import com.cystesoft.vyrbus.model.bean.MapaBus;
import com.cystesoft.vyrbus.model.bean.Nacionalidad;
import com.cystesoft.vyrbus.model.bean.Personal;
import com.cystesoft.vyrbus.model.bean.Servicio;
import com.cystesoft.vyrbus.model.bean.TipoPersonal;
import com.cystesoft.vyrbus.model.bean.TranscarUsuarioPersonal;
import com.cystesoft.vyrbus.model.bean.Usuario;
import com.cystesoft.vyrbus.model.bean.VSAfiliacion;
import com.cystesoft.vyrbus.model.bean.VSAsegurado;
import com.cystesoft.vyrbus.model.bean.VSSexo;
import com.cystesoft.vyrbus.model.bean.VSTipoDocumento;
import com.cystesoft.vyrbus.model.bean.VentaPasaje;
import com.cystesoft.vyrbus.service.locator.ServiceLocator;
import com.cystesoft.vyrbus.service.mappers.Asiento;
import com.cystesoft.vyrbus.service.mappers.Coordenada;
import com.cystesoft.vyrbus.service.mappers.ResumenComprobante;
import com.cystesoft.vyrbus.view.ctrl.WndManifiesto;

public class CreateDocument implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 4410452174308928031L;
	private static final String NEWLINE = "\n";
	
	private static Double totalManifiesto;
	
	public static final File crearRecibCaja(VentaPasaje ventaPasaje){
//		String fichero = Constantes.DIRECTORY_RECIBO_CAJA +ventaPasaje.getNumeroControl()+".txt";
		String fichero = Constantes.DIRECTORY_RECIBO_CAJA +Constantes.CLAVE_PAHT + ventaPasaje.getNumeroControl()+".txt";
		File file = new File(fichero);
		
		try{
						
			BufferedWriter bw = new BufferedWriter(new FileWriter(file));
			String linea = "";
			
			bw.write(NEWLINE);bw.write(NEWLINE);

			
			/**Linea 0*/
			//NUMERO DEL RECIBO DE CAJA. - FISCALIZACIï¿½N
			linea = tabular(8)+"Nro: " + ventaPasaje.getNumeroBoleto();
			
			//NUMERO DEL RECIBO DE CAJA. - CLIENTE
			linea+= tabular(65)+"N: " + ventaPasaje.getNumeroBoleto();
			bw.write(linea+NEWLINE);
			
			//SI ES PAXFREE
			if(ventaPasaje.getPasajero().getPasajeroFrecuente()!=null){
				linea = tabular(8)+"PASAJERO FRECUENTE"+tabular(45)+"PASAJERO FRECUENTE";
				bw.write(linea+NEWLINE);
			}else
				bw.write(NEWLINE);
			
			//PUNTO DE EMBARQUE:
			linea= tabular(8)+"Pto.Embarque: " + ventaPasaje.getAgenciaPartida().getDenominacion();
			linea+= tabular(35)+"Pto.Embarque: " + ventaPasaje.getAgenciaPartida().getDenominacion();
			bw.write(linea+NEWLINE);
				
			bw.write(NEWLINE);bw.write(NEWLINE); //bw.write(NEWLINE);
			
			int destino_f=23;
			int origen_c=30;
			int destino_c=46;
			
			/**Linea 1*/
			//NOMBRE DEL CLIENTE QUE PAGA EL RECIBO DE CAJA
						
			/**Linea 2*/
			//IMPORTE PAGADO - FISCALIZACIï¿½N 
			Double importe=ventaPasaje.getImportePagado();
			linea = tabular(23) + importe.toString();
			
			//IMPORTE PAGADO - CLIENTE
			linea+= tabular(50) + importe.toString();
			bw.write(linea + NEWLINE);
			
			/**Linea 3*/
			//ORIGEN - FISCALIZACIï¿½N
			String origen=ventaPasaje.getRuta().getOrigen();
			linea=tabular(15)+origen;
			//DESTINO - FISCALIZACIï¿½N
			String destino=ventaPasaje.getRuta().getDestino();
			linea+=tabular(destino_f-destino.length())+destino;
//			linea+=tabular(14)+destino;
			
			//ORIGEN - CLIENTE
			linea+=tabular(origen_c-origen.length())+origen;
			//DESTINO - CLIENTE
			linea+=tabular(destino_c-destino.length())+destino;
			bw.write(linea+NEWLINE);
			
			/**Linea 4*/
			//HORA DE SALIDA - FISCALIZACIï¿½N
//			String horaSalida=ventaPasaje.getHoraPartida();
			String strHoraRealEmbarque = obtenerHoraEmbarque(ventaPasaje.getItinerario().getId(), ventaPasaje.getAgenciaPartida().getId());
			String horaSalida = (strHoraRealEmbarque==null?ventaPasaje.getHoraPartida():strHoraRealEmbarque);
			linea=tabular(19)+horaSalida;
			//FECHA DE SALIDA - FISCALIZACIï¿½N
			String fechaSalida=Constantes.FORMAT_DATE.format(ventaPasaje.getFechaPartida());
			linea+=tabular(17)+fechaSalida;
			
//			//HORA DE SALIDA - CLIENTE
			linea+=tabular(20)+horaSalida;
//			//FECHA DE SALIDA - CLIENTE
			linea+=tabular(37)+fechaSalida;
			bw.write(linea+NEWLINE);
			
			/**Linea 5*/
			//Nï¿½MERO DE ASIENTO - FISCALIZACIï¿½N
			bw.write(NEWLINE);
			String asiento=ventaPasaje.getNumeroAsiento().toString();
			linea=tabular(20)+asiento;
			
			//Nï¿½MERO DE ASIENTO - CLIENTE
			linea+=tabular(50)+asiento;
			bw.write(linea+NEWLINE);
			bw.write(NEWLINE);
			
			/**Linea 6*/
			//NOMBRE DEL PASAJERO A QUIEN SE LE VA A EMITIR EL BOLETO  - FISCALIZACIï¿½N
			String pasajero=ventaPasaje.getPasajero().getNombresApellidos();
			linea=tabular(18)+pasajero;
			
			//NOMBRE DEL PASAJERO A QUIEN SE LE VA A EMITIR EL BOLETO - CLIENTE
			linea+=tabular(52-pasajero.length())+pasajero;
			bw.write(linea+NEWLINE);
			
			/**Linea 7*/
			//Nï¿½MERO DOCUMENTO PASAJERO - FISCALIZACIï¿½N
			String numeroDocuPasajero=ventaPasaje.getPasajero().getNumeroDocumento().toString();
			linea=tabular(30)+numeroDocuPasajero;
			
			//Nï¿½MERO DE DOCUMENTO PASAJERO - CLIENTE
			linea+=tabular(50)+numeroDocuPasajero;
			bw.write(linea+NEWLINE);
			
			bw.write(NEWLINE);
			bw.write(NEWLINE);
			
			bw.close();
			
		}catch(IOException ioex){
			ioex.printStackTrace();
		}catch(Exception ex){
			ex.printStackTrace();
		}
		
		return file;
	}
	
	/**
	 * Crea el archivo para la impresion del boleto.
	 * @param ventaPasaje : Instanncia del Objeto VentaPasaje
	 * @param formatoGrande : Tipo de formato del Boleto, 1=Formato grande, 0=Formato estandar(Chico)
	 * @return
	 */
	public static final File crearBoleto(VentaPasaje ventaPasaje,boolean formatoGrande)throws Exception{
		if(formatoGrande)
			return crearBoletoFormatGrande(ventaPasaje);
		else
			return crearBoleto(ventaPasaje);
	}
	
	private static final File crearBoletoFormatGrande(VentaPasaje ventaPasaje)throws Exception{
//		String fichero = "/usr/local/apache-tomcat-5.5.28/webapps/vyrweb/vouchers/"+ventaPasaje.getNumeroControl()+".txt";
//		String fichero = Constantes.DIRECTORY_BOLETOS + ventaPasaje.getNumeroControl()+".txt";
		String fichero = Constantes.DIRECTORY_BOLETOS +Constantes.CLAVE_PAHT+ ventaPasaje.getNumeroControl()+".txt";
		File file = new File(fichero);
		BufferedWriter bw = new BufferedWriter(new FileWriter(file));
		String linea = "";
		try{
			
//			BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file),"8859_1"));
			String boletoPostergado = "";
//			String montoAnterior = "";
			//-----		LINEA 1
			if(ventaPasaje.getVentaPasaje()==null)
				if(ventaPasaje.getFechaPartida()==null){		//FECHA ABIERTA
					linea = "BOLETO SUJETO A VARIACION DE TARIFA";
					bw.write(tabular(15) + linea + NEWLINE);
				}else
					bw.write(NEWLINE);
			else{
//				VentaPasaje boletoReferencia = ServiceLocator.getVentaPasajesManager().buscarVentaById(ventaPasaje.getVentaPasaje().getId());
				if(ventaPasaje.getTipoMovimiento().getId().intValue()==Constantes.ID_TIPMOV_POSTERGACION || ventaPasaje.getTipoMovimiento().getId().intValue()==Constantes.ID_TIPMOV_POSTERGACION_FA){
					boletoPostergado = "POST. BOL. ANT.: " + ventaPasaje.getNumeroBoletoAnterior();
//					montoAnterior = "Monto Ant.: " + Util.toNumberFormat(boletoReferencia.getImportePagado(),2);
//					linea = boletoPostergado + " " + montoAnterior;
					linea = boletoPostergado;
					bw.write(tabular(15) + linea + NEWLINE);
				}else if(ventaPasaje.getTipoMovimiento().getId().intValue()==Constantes.ID_TIPMOV_PREPAGADO){
					linea = "RC : "+ventaPasaje.getNumeroBoletoAnterior();
					bw.write(tabular(15) + linea + NEWLINE);
				}else if(ventaPasaje.getTipoMovimiento().getId().intValue()==Constantes.ID_TIPMOV_CREDITO){
					linea = "VOUCHER : "+ventaPasaje.getNumeroBoletoAnterior();
					bw.write(tabular(15) + linea + NEWLINE);
				}else if(ventaPasaje.getTipoMovimiento().getId().intValue()==Constantes.ID_TIPMOV_REIMPRESION){
					linea = "REIMP : "+ventaPasaje.getNumeroBoletoAnterior();
					bw.write(tabular(15) + linea + NEWLINE);
				}else if(ventaPasaje.getTipoMovimiento().getId().intValue()==Constantes.ID_TIPMOV_CONFIRMACION_FA){
					linea = "CONF. FA : "+ventaPasaje.getNumeroBoletoAnterior();
					bw.write(tabular(15) + linea + NEWLINE);
				}
			}
			//-----		LINEA 2
			if(ventaPasaje.getFormaPago().getId().intValue()==Constantes.ID_FORPAG_CORTESIA){
				if(ventaPasaje.getTipoFormaPago().getId().intValue()==Constantes.ID_TIPFORPAG_PUNTOS)
					linea = "CORTESIA X PUNTOS";
				else if(ventaPasaje.getTipoFormaPago().getId().intValue()==Constantes.ID_TIPFORPAG_CUMPLEANIOS)
					linea = "CORTESIA POR CUMPLEANIOS";
				else if(ventaPasaje.getTipoFormaPago().getId().intValue()==Constantes.ID_TIPFORPAG_ORDEN_TRABAJO)
					linea = "CORTESIA POR ORDEN DE TRABAJO";
				else if(ventaPasaje.getTipoFormaPago().getId().intValue()==Constantes.ID_TIPFORPAG_CORTESIA)
					linea = "CORTESIA";				
				bw.write(tabular(15) + linea + NEWLINE);
			}else if(ventaPasaje.getFechaPartida()==null){
				linea = "FECHA ABIERTA";
				bw.write(tabular(15) + linea + NEWLINE);
			}else if (ventaPasaje.getCentroCosto()!=null){
				//(impl:13/03/2014 - jabanto)
				CentroCosto centroCosto=ventaPasaje.getCentroCosto();
				Integer lenMaxNameCC=27;
				if(centroCosto.getDenominacion().length()>lenMaxNameCC)
					linea = "CENTRO COSTO:"+centroCosto.getCodigo()+"-"+centroCosto.getDenominacion().substring(0,lenMaxNameCC);
				else
					linea = "CENTRO COSTO:"+centroCosto.getCodigo()+"-"+centroCosto.getDenominacion();
				
				bw.write(tabular(15) + linea + NEWLINE);
			}else if(ventaPasaje.getTipoMovimiento().getId().intValue()==Constantes.ID_TIPMOV_PREPAGADO){
				linea = "BOLETO PREPAGADO";
				bw.write(tabular(15) + linea+ tabular(59) + "PREPAGADO" + NEWLINE);
			}else
				bw.write(NEWLINE);
			
			bw.write(NEWLINE);
			
			//-----	LINEA
			if(ventaPasaje.getTipoMovimiento().getId().intValue()==Constantes.ID_TIPMOV_PREPAGADO){
				linea = tabular(90) + "RC : "+ventaPasaje.getNumeroBoletoAnterior();
				bw.write(linea + NEWLINE);
			}else if(ventaPasaje.getTipoMovimiento().getId().intValue()==Constantes.ID_TIPMOV_CREDITO){
				linea = tabular(90) + "VOUCHER : "+ventaPasaje.getNumeroBoletoAnterior();
				bw.write(linea + NEWLINE);
			}else if(ventaPasaje.getTipoMovimiento().getId().intValue()==Constantes.ID_TIPMOV_REIMPRESION){
				linea = tabular(90) + "REIMP : "+ventaPasaje.getNumeroBoletoAnterior();
				bw.write(linea + NEWLINE);
			}else
				bw.write(NEWLINE);
			
			//-----		Linea 3 	HORA DE EMISION
			String strHoraEmision = Util.DatetoString(new Date(), Constantes.TIME_FORMAT)+tabular(21);
			linea = tabular(40) + strHoraEmision.substring(0, 21);
			bw.write(linea + NEWLINE);
//			bw.write("XXXXOXXXXRXXXXOXXXXRXXXXOXXXXRXXXXOXXXXRXXXXOXXXXRXXXXOXXXXRXXXXOXXXXRXXXXOXXXXRXXXXOXXXXRXXXXOXXXXRXXXXOXXXXRXXXXOXXXXRXXXXOXXXXRXXXXOXXXXRXXXXOXXXXRXXXXOXXXXR");
			
			//-----		Linea 4 	FECHA EMISION, CADUCIDAD
			bw.write(NEWLINE);
			String strFechaEmision = Util.DatetoString(new Date(), Constantes.DATE_FORMAT) + tabular(21);
			String strFechaCaducidad = Util.DatetoString(ventaPasaje.getFechaCaducidad(), Constantes.DATE_FORMAT) + tabular(26);
			linea = tabular(30) + strFechaEmision.substring(0, 21) + tabular(10) + strFechaCaducidad.substring(0, 26) + 
					tabular(18) + strFechaEmision.substring(0, 10) + tabular(12) + strFechaCaducidad.substring(0, 10);   
			bw.write(linea + NEWLINE);
			
			//-----		Linea 5 	RAZON SOCIAL, RUC
			String strRazon="",strRuc="";
			if(ventaPasaje.getCliente()!=null){
				strRazon = ventaPasaje.getCliente().getRazonSocial() + tabular(48);
				strRuc = ventaPasaje.getCliente().getNumeroDocumento() + tabular(30);
				linea = tabular(10) + strRazon.substring(0, 40) + tabular(10) + strRuc.substring(0, 30) + tabular(10);// + 
//						strRazon.substring(0, 23) + tabular(2) + strRuc.substring(0, 11);
				bw.write(linea + NEWLINE);
			}else
				bw.write(NEWLINE);
			
			//-----		Linea 6 	PASAJERO, EDAD
			String pasajero = ventaPasaje.getPasajero().toString() + tabular(48);
			Long edad = null;
			if(ventaPasaje.getPasajero().getFechaNacimiento()!=null && !ventaPasaje.getPasajero().getFechaNacimiento().equals(Constantes.FECHA_NULL))
				edad = (new Date().getTime()-Util.StringtoDate(ventaPasaje.getPasajero().getFechaNacimiento(), Constantes.DATE_FORMAT).getTime())/(Constantes.MILISEGUNDOS_X_DIA*365);
			
			if(edad !=null)
				linea = tabular(10) + pasajero.substring(0, 40) + tabular(10) + (edad.toString()+tabular(30)).substring(0, 30) +
						(ventaPasaje.getCliente()!=null?tabular(10) + strRazon.substring(0,23) + tabular(2) + strRuc.substring(0,11):"");
//					tabular(10) + pasajero.substring(0, 23) + tabular(5) + edad.toString();
				
			else
				linea = tabular(10) + pasajero.substring(0, 40) + (ventaPasaje.getCliente()!=null? tabular(50) + strRazon.substring(0,23) + tabular(2) + strRuc.substring(0,11):""); //+ tabular(50) + pasajero.substring(0, 23);
			bw.write(linea + NEWLINE);
			
			//-----		Linea 7 	TIPO DOC, NUMDOC, ORIGEN, DESTINO
//			String strOrigen = ventaPasaje.getRuta().getOrigen();
			String strOrigens = ventaPasaje.getRuta().getOrigen();
			String strServicio = ventaPasaje.getServicio().getDenominacion();
			String strDestino = ventaPasaje.getRuta().getDestino();
			String strTipoDocumento = ventaPasaje.getPasajero().getTipoDocumento().getDenominacion();
			
			linea = tabular(10) + (strTipoDocumento+tabular(40)).substring(0, 40) + tabular(10) + (ventaPasaje.getPasajero().getNumeroDocumento()+tabular(30)).substring(0, 30);
			linea = linea +	//tabular(10) + (strOrigens+" - "+strServicio+tabular(20)).substring(0, 20) +
					(edad!=null?tabular(10) + pasajero.substring(0, 23) + tabular(5) + edad.toString(): tabular(50) + pasajero.substring(0, 23));
//					tabular(5) + (strDestino+tabular(12)).substring(0, 12);
			bw.write(linea + NEWLINE);
			
			//-----		Linea 8 	ORIGEN, DESTINO			
			linea = tabular(10) + (ventaPasaje.getRuta().getOrigen()+" - "+strServicio+tabular(30)).substring(0, 30) + 
					tabular(12) + (ventaPasaje.getRuta().getDestino()+tabular(30)).substring(0,30) +
					tabular(18) + (strOrigens+" - "+strServicio+tabular(20)).substring(0, 20) + tabular(7) + (strDestino+tabular(9)).substring(0, 9);;
			bw.write(linea + NEWLINE);
			
			//-----		Linea 9 	TIPOPAGO, LUGAR EMBARQUE, HORA EMBARQUE, FVIAJE, HPARTIDA, ASIENTO
			String strAsiento = "00"+ventaPasaje.getNumeroAsiento();
			strAsiento = strAsiento.substring(strAsiento.length()-2)+(ventaPasaje.getOperadoPor()!=null?" [PISO-"+ventaPasaje.getNumeroPiso()+"]":"");
			String strFechaViaje = ventaPasaje.getFechaPartida()==null?null:Util.DatetoString(ventaPasaje.getFechaPartida(), Constantes.DATE_FORMAT);
			/**		PARA OBTENER LA HORA DE EMBARQUE	*/
			//String strHoraPartida = ventaPasaje.getHoraPartida();
			String strHoraRealEmbarque=null;
			if(ventaPasaje.getFechaPartida()==null)
				strHoraRealEmbarque = "";
			else{
				if(ventaPasaje.getItinerario()!=null)
					strHoraRealEmbarque = obtenerHoraEmbarque(ventaPasaje.getItinerario().getId(), ventaPasaje.getAgenciaPartida().getId());
				else
					strHoraRealEmbarque=ventaPasaje.getHoraEmbarque();
			}
			
			String strHoraPartida = (strHoraRealEmbarque==null?ventaPasaje.getHoraPartida():strHoraRealEmbarque);
			String strTC = "";
			String strOperadorTC = "";
			String strEmbarque = "";
			String strHoraEmbarque = "";
			if(ventaPasaje.getTarjetaCredito()!=null){
				strTC = ventaPasaje.getTarjetaCredito().getDenominacion();
				strOperadorTC = ventaPasaje.getTarjetaCredito().getOperadorTarjetaCredito().getDenominacion();
			}
			
			String strFormaPago = ventaPasaje.getFormaPago().getDenominacion()+"-"+ventaPasaje.getTipoFormaPago().getDenominacion()+"-"+strOperadorTC+"->"+strTC;
			
			if(ventaPasaje.getFechaPartida()!=null){
				strEmbarque = "Emb.: " + (ventaPasaje.getAgenciaPartida().getDenominacion().length()>23?ventaPasaje.getAgenciaPartida().getDenominacion().substring(0, 22):ventaPasaje.getAgenciaPartida().getDenominacion());
				strHoraEmbarque = "H.Emb.: " + strHoraPartida;
				linea = (strFormaPago+tabular(42)).substring(0, 42) + (strEmbarque +"/"+strHoraEmbarque+tabular(43)).substring(0, 43);
//				linea = linea + tabular(7) + strFechaViaje + tabular(8) + strHoraPartida + tabular(15) + strAsiento;				
			}else
				linea = tabular(5) + (strFormaPago+tabular(30)).substring(0, 30);
			
			bw.write(linea + NEWLINE);
			
			//-----		Linea 10 	VALORPASAJE, TIPOPAGO
//			String strTarifa = Util.toNumberFormat(ventaPasaje.getTarifa(), 2);
			String strTarifa = Util.toNumberFormat(ventaPasaje.getImportePagado(), 2);
			if(ventaPasaje.getOperadoPor()==null)
				linea = tabular(72) + strTarifa + tabular(12) + tabular(7) + strFechaViaje + tabular(8) + strHoraPartida + tabular(15) + strAsiento;
			else
				linea = tabular(72) + strTarifa + tabular(12) + tabular(7) + strFechaViaje + tabular(8) + strHoraPartida + tabular(8) + strAsiento;
//			if(ventaPasaje.getVentaPasaje()!=null){
//				linea = tabular(72) + strTarifa + tabular(12) + boletoPostergado;
//			}else
//				linea = tabular(72) + strTarifa + tabular(12) + strFormaPago;
			bw.write(linea + NEWLINE);
			
			//-----		Linea 11 	FVIAJE, HPARTIDA, ASIENTO, DCTO, IGV, VALORPASAJE
			String descuento = Util.toNumberFormat(ventaPasaje.getDescuento(), 2);
			if(ventaPasaje.getFechaPartida()!=null){
				if(ventaPasaje.getOperadoPor()==null)
					linea = strFechaViaje + tabular(5) + strHoraPartida + tabular(10) + strAsiento + tabular(8) + descuento + tabular(28) + "0.00";
				else
					linea = strFechaViaje + tabular(5) + strHoraPartida + tabular(3) + strAsiento + tabular(8) + descuento + tabular(28) + "0.00";
//				linea = linea + tabular(14) + (strEmbarque+tabular(25)).substring(0, 25);
				if(ventaPasaje.getVentaPasaje()!=null){
					linea = linea + tabular(20) + boletoPostergado;
				}else
					linea = linea + tabular(20) + strFormaPago;
			}else{
				linea = tabular(40) + descuento + tabular(28) + "0.00";
				
				if(ventaPasaje.getVentaPasaje()!=null){
					linea = linea + tabular(12) + boletoPostergado;
				}else
					linea = linea + tabular(12) + strFormaPago;
			}
			
			bw.write(linea + NEWLINE);
			
			//-----		Linea 12 	SON, TOTAL, IGV
			String strImportePagado = Util.toNumberFormat(ventaPasaje.getImportePagado(), 2);
			ConvertirNumeroString num = new ConvertirNumeroString();
			int indice = strImportePagado.lastIndexOf(".");
//			String strImporte = num.convertirLetras(ventaPasaje.getImportePagado().intValue()).toUpperCase()+" CON " + strImportePagado.substring(indice+1) + "/100 NUEVOS SOLES" + tabular(35);
			String strImporte = num.convertirLetras(ventaPasaje.getImportePagado().intValue()).toUpperCase()+" CON " + strImportePagado.substring(indice+1) + "/100 SOLES" + tabular(35);
			strImporte = strImporte.substring(0, 35);
//			linea = strImporte + tabular(37) + strImportePagado + tabular(12)+ (strHoraEmbarque+tabular(20)).substring(0, 20)+ tabular(19) + strTarifa;  // + tabular(19) + "0.00";
			linea = tabular(72) + strImportePagado+tabular(20)+ (strEmbarque+tabular(20)).substring(0, 20);
			bw.write(linea + NEWLINE);
			
			linea=strImporte +  tabular(62) + (strHoraEmbarque+tabular(20)).substring(0, 20) + tabular(14) + strTarifa; //+ tabular(16) + "0.00";
			bw.write(linea + NEWLINE);
			String operadoPor="";
			if(ventaPasaje.getOperadoPor()!=null){
				operadoPor="***OPERADADO POR : "+ventaPasaje.getOperadoPor()+"***";
				linea=tabular(10) + operadoPor +tabular(122-operadoPor.length()) + "0.00";
				bw.write(linea + NEWLINE);
				operadoPor="OPERADADO POR : "+ventaPasaje.getOperadoPor();
			}else{
				linea=tabular(131) + "0.00";
				bw.write(linea + NEWLINE);
			}
			linea=tabular(131) + strImportePagado;
			bw.write(linea + NEWLINE);
			
			linea = tabular(60) + (ventaPasaje.getNumeroControl()+tabular(30)).substring(0, 30) + 
					tabular(1) + (ventaPasaje.getNumeroControl()+tabular(30)).substring(0, 16) + tabular(3) + operadoPor; 
			bw.write(linea + NEWLINE);
			bw.write(NEWLINE);
			bw.write(NEWLINE);
			bw.write(NEWLINE);
			bw.write(NEWLINE);
			bw.write(NEWLINE);
			
				
		}catch(IOException ioex){
			ioex.printStackTrace();
			Log.error(ioex.getMessage());
		}catch(Exception ex){
			ex.printStackTrace();
			Log.error(ex.getMessage());
		}		
//		return fichero;
		
		bw.close();
		return file;
	}
	
	private static final File crearBoleto(VentaPasaje ventaPasaje){
//		String fichero = "/usr/local/apache-tomcat-5.5.28/webapps/vyrweb/vouchers/"+ventaPasaje.getNumeroControl()+".txt";
//		String fichero = Constantes.DIRECTORY_BOLETOS + ventaPasaje.getNumeroControl()+".txt";
		String fichero = Constantes.DIRECTORY_BOLETOS +Constantes.CLAVE_PAHT+ ventaPasaje.getNumeroControl()+".txt";
		File file = new File(fichero);
		try{
			BufferedWriter bw = new BufferedWriter(new FileWriter(file));
			String linea = "";
			String boletoPostergado = "";
//			String montoAnterior = "";
			//-----		LINEA 1
			if(ventaPasaje.getVentaPasaje()==null)
				if(ventaPasaje.getFechaPartida()==null){		//FECHA ABIERTA
					linea = "BOLETO SUJETO A VARIACION DE TARIFA";
					bw.write(tabular(15) + linea + NEWLINE);
				}else
					bw.write(NEWLINE);
			else{
//				VentaPasaje boletoReferencia = ServiceLocator.getVentaPasajesManager().buscarVentaById(ventaPasaje.getVentaPasaje().getId());
				if(ventaPasaje.getTipoMovimiento().getId().intValue()==Constantes.ID_TIPMOV_POSTERGACION || ventaPasaje.getTipoMovimiento().getId().intValue()==Constantes.ID_TIPMOV_POSTERGACION_FA){
					boletoPostergado = "POST. BOL. ANT.: " + ventaPasaje.getNumeroBoletoAnterior();
//					montoAnterior = "Monto Ant.: " + Util.toNumberFormat(boletoReferencia.getImportePagado(),2);
//					linea = boletoPostergado + " " + montoAnterior;
					linea = boletoPostergado;
					bw.write(tabular(15) + linea + NEWLINE);
				}else if(ventaPasaje.getTipoMovimiento().getId().intValue()==Constantes.ID_TIPMOV_PREPAGADO){
					linea = "RC : "+ventaPasaje.getNumeroBoletoAnterior();
					bw.write(tabular(15) + linea + NEWLINE);
				}else if(ventaPasaje.getTipoMovimiento().getId().intValue()==Constantes.ID_TIPMOV_CREDITO){
					linea = "VOUCHER : "+ventaPasaje.getNumeroBoletoAnterior();
					bw.write(tabular(15) + linea + NEWLINE);
				}else if(ventaPasaje.getTipoMovimiento().getId().intValue()==Constantes.ID_TIPMOV_REIMPRESION){
					linea = "REIMP : "+ventaPasaje.getNumeroBoletoAnterior();
					bw.write(tabular(15) + linea + NEWLINE);
				}else if(ventaPasaje.getTipoMovimiento().getId().intValue()==Constantes.ID_TIPMOV_CONFIRMACION_FA){
					linea = "CONF. FA : "+ventaPasaje.getNumeroBoletoAnterior();
					bw.write(tabular(15) + linea + NEWLINE);
				}
			}
			//-----		LINEA 2
			if(ventaPasaje.getFormaPago().getId().intValue()==Constantes.ID_FORPAG_CORTESIA){
				if(ventaPasaje.getTipoFormaPago().getId().intValue()==Constantes.ID_TIPFORPAG_PUNTOS)
					linea = "CORTESIA X PUNTOS";
				else if(ventaPasaje.getTipoFormaPago().getId().intValue()==Constantes.ID_TIPFORPAG_CUMPLEANIOS)
					linea = "CORTESIA POR CUMPLEANIOS";
				else if(ventaPasaje.getTipoFormaPago().getId().intValue()==Constantes.ID_TIPFORPAG_ORDEN_TRABAJO)
					linea = "CORTESIA POR ORDEN DE TRABAJO";
				else if(ventaPasaje.getTipoFormaPago().getId().intValue()==Constantes.ID_TIPFORPAG_CORTESIA)
					linea = "CORTESIA";				
				bw.write(tabular(15) + linea + NEWLINE);
			}else if(ventaPasaje.getFechaPartida()==null){
				linea = "FECHA ABIERTA";
				bw.write(tabular(15) + linea + NEWLINE);
			}else if (ventaPasaje.getCentroCosto()!=null){
				//(impl:13/03/2014 - jabanto)
				CentroCosto centroCosto=ventaPasaje.getCentroCosto();
				Integer lenMaxNameCC=27;
				if(centroCosto.getDenominacion().length()>lenMaxNameCC)
					linea = "CENTRO COSTO:"+centroCosto.getCodigo()+"-"+centroCosto.getDenominacion().substring(0,lenMaxNameCC);
				else
					linea = "CENTRO COSTO:"+centroCosto.getCodigo()+"-"+centroCosto.getDenominacion();
				
				bw.write(tabular(15) + linea + NEWLINE);
			}else if(ventaPasaje.getTipoMovimiento().getId().intValue()==Constantes.ID_TIPMOV_PREPAGADO){
				linea = "BOLETO PREPAGADO";
				bw.write(tabular(15) + linea+ tabular(59) + "PREPAGADO" + NEWLINE);
			}else
				bw.write(NEWLINE);
			
			bw.write(NEWLINE);
			
			//-----	LINEA
			if(ventaPasaje.getTipoMovimiento().getId().intValue()==Constantes.ID_TIPMOV_PREPAGADO){
				linea = tabular(90) + "RC : "+ventaPasaje.getNumeroBoletoAnterior();
				bw.write(linea + NEWLINE);
			}else if(ventaPasaje.getTipoMovimiento().getId().intValue()==Constantes.ID_TIPMOV_CREDITO){
				linea = tabular(90) + "VOUCHER : "+ventaPasaje.getNumeroBoletoAnterior();
				bw.write(linea + NEWLINE);
			}else if(ventaPasaje.getTipoMovimiento().getId().intValue()==Constantes.ID_TIPMOV_REIMPRESION){
				linea = tabular(90) + "REIMP : "+ventaPasaje.getNumeroBoletoAnterior();
				bw.write(linea + NEWLINE);
			}else
				bw.write(NEWLINE);
			
			//-----		Linea 3 	HORA DE EMISION
			String strHoraEmision = Util.DatetoString(new Date(), Constantes.TIME_FORMAT)+tabular(21);
			linea = tabular(40) + strHoraEmision.substring(0, 21);
			bw.write(linea + NEWLINE);
//			bw.write("XXXXOXXXXRXXXXOXXXXRXXXXOXXXXRXXXXOXXXXRXXXXOXXXXRXXXXOXXXXRXXXXOXXXXRXXXXOXXXXRXXXXOXXXXRXXXXOXXXXRXXXXOXXXXRXXXXOXXXXRXXXXOXXXXRXXXXOXXXXRXXXXOXXXXRXXXXOXXXXR");
			
			//-----		Linea 4 	FECHA EMISION, CADUCIDAD
			String strFechaEmision = Util.DatetoString(new Date(), Constantes.DATE_FORMAT) + tabular(21);
			String strFechaCaducidad = Util.DatetoString(ventaPasaje.getFechaCaducidad(), Constantes.DATE_FORMAT) + tabular(26);
			linea = tabular(30) + strFechaEmision.substring(0, 21) + tabular(10) + strFechaCaducidad.substring(0, 26) + 
					tabular(18) + strFechaEmision.substring(0, 10) + tabular(12) + strFechaCaducidad.substring(0, 10);   
			bw.write(linea + NEWLINE);
			
			//-----		Linea 5 	RAZON SOCIAL, RUC
			if(ventaPasaje.getCliente()!=null){
				String strRazon = ventaPasaje.getCliente().getRazonSocial() + tabular(48);
				String strRuc = ventaPasaje.getCliente().getNumeroDocumento() + tabular(30);
				linea = tabular(10) + strRazon.substring(0, 40) + tabular(10) + strRuc.substring(0, 30) + tabular(10) + 
						strRazon.substring(0, 23) + tabular(2) + strRuc.substring(0, 11);
				bw.write(linea + NEWLINE);
			}else
				bw.write(NEWLINE);
			
			//-----		Linea 6 	PASAJERO, EDAD
			String pasajero = ventaPasaje.getPasajero().toString() + tabular(48);
			Long edad = null;
			if(ventaPasaje.getPasajero().getFechaNacimiento()!=null && !ventaPasaje.getPasajero().getFechaNacimiento().equals(Constantes.FECHA_NULL))
				edad = (new Date().getTime()-Util.StringtoDate(ventaPasaje.getPasajero().getFechaNacimiento(), Constantes.DATE_FORMAT).getTime())/(Constantes.MILISEGUNDOS_X_DIA*365);
			
			if(edad !=null)
				linea = tabular(10) + pasajero.substring(0, 40) + tabular(10) + (edad.toString()+tabular(30)).substring(0, 30) + 
					tabular(10) + pasajero.substring(0, 23) + tabular(5) + edad.toString();
			else
				linea = tabular(10) + pasajero.substring(0, 40) + tabular(50) + pasajero.substring(0, 23);
			bw.write(linea + NEWLINE);
			
			//-----		Linea 7 	TIPO DOC, NUMDOC, ORIGEN, DESTINO
//			String strOrigen = ventaPasaje.getRuta().getOrigen();
			String strOrigens = ventaPasaje.getRuta().getOrigen();
			String strServicio = ventaPasaje.getServicio().getDenominacion();
			String strDestino = ventaPasaje.getRuta().getDestino();
			String strTipoDocumento = ventaPasaje.getPasajero().getTipoDocumento().getDenominacion();
			
			linea = tabular(10) + (strTipoDocumento+tabular(40)).substring(0, 40) + tabular(10) + (ventaPasaje.getPasajero().getNumeroDocumento()+tabular(30)).substring(0, 30);
			linea = linea +	tabular(10) + (strOrigens+" - "+strServicio+tabular(20)).substring(0, 20) + 
					tabular(5) + (strDestino+tabular(12)).substring(0, 12);
			bw.write(linea + NEWLINE);
			
			//-----		Linea 8 	ORIGEN, DESTINO			
			linea = tabular(10) + (ventaPasaje.getRuta().getOrigen()+" - "+strServicio+tabular(30)).substring(0, 30) + 
					tabular(12) + ventaPasaje.getRuta().getDestino();
			bw.write(linea + NEWLINE);
			
			//-----		Linea 9 	TIPOPAGO, LUGAR EMBARQUE, HORA EMBARQUE, FVIAJE, HPARTIDA, ASIENTO
			String strAsiento = "00"+ventaPasaje.getNumeroAsiento();
			strAsiento = strAsiento.substring(strAsiento.length()-2);
			String strFechaViaje = ventaPasaje.getFechaPartida()==null?null:Util.DatetoString(ventaPasaje.getFechaPartida(), Constantes.DATE_FORMAT);
			/**		PARA OBTENER LA HORA DE EMBARQUE	*/
			//String strHoraPartida = ventaPasaje.getHoraPartida();
			String strHoraRealEmbarque=null;
			if(ventaPasaje.getFechaPartida()==null)
				strHoraRealEmbarque = "";
			else{
				if(ventaPasaje.getItinerario()!=null)
					strHoraRealEmbarque = obtenerHoraEmbarque(ventaPasaje.getItinerario().getId(), ventaPasaje.getAgenciaPartida().getId());
				else
					strHoraRealEmbarque=ventaPasaje.getHoraEmbarque();
			}
			
			String strHoraPartida = (strHoraRealEmbarque==null?ventaPasaje.getHoraPartida():strHoraRealEmbarque);
			String strTC = "";
			String strOperadorTC = "";
			String strEmbarque = "";
			String strHoraEmbarque = "";
			if(ventaPasaje.getTarjetaCredito()!=null){
				strTC = ventaPasaje.getTarjetaCredito().getDenominacion();
				strOperadorTC = ventaPasaje.getTarjetaCredito().getOperadorTarjetaCredito().getDenominacion();
			}
			
			String strFormaPago = ventaPasaje.getFormaPago().getDenominacion()+"-"+ventaPasaje.getTipoFormaPago().getDenominacion()+"-"+strOperadorTC+"->"+strTC;
			
			if(ventaPasaje.getFechaPartida()!=null){
				strEmbarque = "Emb.: " + (ventaPasaje.getAgenciaPartida().getDenominacion().length()>23?ventaPasaje.getAgenciaPartida().getDenominacion().substring(0, 22):ventaPasaje.getAgenciaPartida().getDenominacion());
				strHoraEmbarque = "H.Emb.: " + strHoraPartida;
				linea = (strFormaPago+tabular(42)).substring(0, 42) + (strEmbarque +"/"+strHoraEmbarque+tabular(43)).substring(0, 43);
				if(ventaPasaje.getOperadoPor()==null)
					linea = linea + tabular(7) + strFechaViaje + tabular(8) + strHoraPartida + tabular(15) + strAsiento;				
				else
					linea = linea + tabular(7) + strFechaViaje + tabular(8) + strHoraPartida + tabular(6) + strAsiento;
			}else
				linea = tabular(3) + (strFormaPago+tabular(30)).substring(0, 30);
			
			bw.write(linea + NEWLINE);
			
			//-----		Linea 10 	VALORPASAJE, TIPOPAGO
//			String strTarifa = Util.toNumberFormat(ventaPasaje.getTarifa(), 2);
			String strTarifa = Util.toNumberFormat(ventaPasaje.getImportePagado(), 2);
			if(ventaPasaje.getVentaPasaje()!=null)
//				linea = tabular(72) + strTarifa + tabular(12) + boletoPostergado +"  "+ montoAnterior;
				linea = tabular(72) + strTarifa + tabular(12) + boletoPostergado;
			else
				linea = tabular(72) + strTarifa + tabular(12) + strFormaPago;
			bw.write(linea + NEWLINE);
			
			//-----		Linea 11 	FVIAJE, HPARTIDA, ASIENTO, DCTO, IGV, VALORPASAJE
			String descuento = Util.toNumberFormat(ventaPasaje.getDescuento(), 2);
			if(ventaPasaje.getFechaPartida()!=null){
//				linea = strFechaViaje + tabular(5) + strHoraPartida + tabular(10) + strAsiento + tabular(8) + descuento + tabular(28) + "0.00";
				if(ventaPasaje.getOperadoPor()==null)
					linea = strFechaViaje + tabular(5) + strHoraPartida + tabular(10) + strAsiento + tabular(8) + descuento + tabular(28) + "0.00";
				else
					linea = strFechaViaje + tabular(5) + strHoraPartida + tabular(3) + strAsiento + tabular(8) + descuento + tabular(28) + "0.00";
				linea = linea + tabular(14) + (strEmbarque+tabular(25)).substring(0, 25)+ tabular(11) + strTarifa;
			}else
				linea = tabular(40) + descuento + tabular(28) + "0.00" + tabular(52) + strTarifa;
			
			bw.write(linea + NEWLINE);
			
			//-----		Linea 12 	SON, TOTAL, IGV
			String strImportePagado = Util.toNumberFormat(ventaPasaje.getImportePagado(), 2);
			ConvertirNumeroString num = new ConvertirNumeroString();
			int indice = strImportePagado.lastIndexOf(".");
//			String strImporte = num.convertirLetras(ventaPasaje.getImportePagado().intValue()).toUpperCase()+" CON " + strImportePagado.substring(indice+1) + "/100 NUEVOS SOLES" + tabular(35);
			String strImporte = num.convertirLetras(ventaPasaje.getImportePagado().intValue()).toUpperCase()+" CON " + strImportePagado.substring(indice+1) + "/100 SOLES" + tabular(35);
			strImporte = strImporte.substring(0, 35);
			linea = tabular(25) + strImporte + tabular(12) + strImportePagado + tabular(12)+ (strHoraEmbarque+tabular(20)).substring(0, 20) + tabular(19) + "0.00";
			bw.write(linea + NEWLINE);
			
			//-----		Linea 13	USUARIO, CONTROL, CONTROL, USUARIO, TOTAL
//			linea = tabular(55) + (ventaPasaje.getUsuario().getLogin() + "  -  " + ventaPasaje.getNumeroControl()+tabular(30)).substring(0, 30) + 
//					tabular(5) + (ventaPasaje.getNumeroControl()+" - "+ventaPasaje.getUsuario().getLogin()+tabular(30)).substring(0, 30) + 
//					tabular(19) + strImportePagado;
			linea = tabular(60) + (ventaPasaje.getNumeroControl()+tabular(30)).substring(0, 30) + 
					tabular(1) + (ventaPasaje.getNumeroControl()).substring(0, 16) + 
					tabular(20) + strImportePagado;
			bw.write(linea + NEWLINE);
			if(ventaPasaje.getOperadoPor()!=null){
				linea = tabular(0)+ "OPERADO POR : " + (ventaPasaje.getOperadoPor()+tabular(80)).substring(0, 80) + 
						tabular(1)+ "OPERADOR POR : " + (ventaPasaje.getOperadoPor()+tabular(30)).substring(0, 16); // +
				bw.write(linea + NEWLINE);
			}else{
				bw.write(NEWLINE);
			}
			bw.write(NEWLINE);
			bw.write(NEWLINE);
			bw.write(NEWLINE);
			bw.close();
		}catch(IOException ioex){
			ioex.printStackTrace();
			Log.error(ioex.getMessage());
		}catch(Exception ex){
			ex.printStackTrace();
			Log.error(ex.getMessage());
		}
//		return fichero;
		return file;
	}
	
//	/**
//	 * Nuevo boleto 06/03/2015
//	 * @param ventaPasaje
//	 * @return
//	 */
//	public static final String crearBoleto_V2(VentaPasaje ventaPasaje){
//		String fichero = Constantes.DIRECTORY_BOLETOS + ventaPasaje.getNumeroControl()+".txt";
//		File file = new File(fichero);
//		try{
//			BufferedWriter bw = new BufferedWriter(new FileWriter(file));
//			String linea = "";
//			String boletoPostergado = "";
//			//-----		LINEA 1
//			if(ventaPasaje.getVentaPasaje()==null)
//				if(ventaPasaje.getFechaPartida()==null){		//FECHA ABIERTA
//					linea = "BOLETO SUJETO A VARIACION DE TARIFA";
//					bw.write(tabular(15) + linea + NEWLINE);
//				}else
//					bw.write(NEWLINE);
//			else{
//				if(ventaPasaje.getTipoMovimiento().getId().intValue()==Constantes.ID_TIPMOV_POSTERGACION || ventaPasaje.getTipoMovimiento().getId().intValue()==Constantes.ID_TIPMOV_POSTERGACION_FA){
//					boletoPostergado = "POST. BOL. ANT.: " + ventaPasaje.getNumeroBoletoAnterior();
//					linea = boletoPostergado;
//					bw.write(tabular(15) + linea + NEWLINE);
//				}else if(ventaPasaje.getTipoMovimiento().getId().intValue()==Constantes.ID_TIPMOV_PREPAGADO){
//					linea = "RC : "+ventaPasaje.getNumeroBoletoAnterior();
//					bw.write(tabular(15) + linea + NEWLINE);
//				}else if(ventaPasaje.getTipoMovimiento().getId().intValue()==Constantes.ID_TIPMOV_CREDITO){
//					linea = "VOUCHER : "+ventaPasaje.getNumeroBoletoAnterior();
//					bw.write(tabular(15) + linea + NEWLINE);
//				}else if(ventaPasaje.getTipoMovimiento().getId().intValue()==Constantes.ID_TIPMOV_REIMPRESION){
//					linea = "REIMP : "+ventaPasaje.getNumeroBoletoAnterior();
//					bw.write(tabular(15) + linea + NEWLINE);
//				}else if(ventaPasaje.getTipoMovimiento().getId().intValue()==Constantes.ID_TIPMOV_CONFIRMACION_FA){
//					linea = "CONF. FA : "+ventaPasaje.getNumeroBoletoAnterior();
//					bw.write(tabular(15) + linea + NEWLINE);
//				}
//			}
//			//-----		LINEA 2
//			if(ventaPasaje.getFormaPago().getId().intValue()==Constantes.ID_FORPAG_CORTESIA){
//				if(ventaPasaje.getTipoFormaPago().getId().intValue()==Constantes.ID_TIPFORPAG_PUNTOS)
//					linea = "CORTESIA X PUNTOS";
//				else if(ventaPasaje.getTipoFormaPago().getId().intValue()==Constantes.ID_TIPFORPAG_CUMPLEANIOS)
//					linea = "CORTESIA POR CUMPLEANIOS";
//				else if(ventaPasaje.getTipoFormaPago().getId().intValue()==Constantes.ID_TIPFORPAG_ORDEN_TRABAJO)
//					linea = "CORTESIA POR ORDEN DE TRABAJO";
//				else if(ventaPasaje.getTipoFormaPago().getId().intValue()==Constantes.ID_TIPFORPAG_CORTESIA)
//					linea = "CORTESIA";				
//				bw.write(tabular(15) + linea + NEWLINE);
//			}else if(ventaPasaje.getFechaPartida()==null){
//				linea = "FECHA ABIERTA";
//				bw.write(tabular(15) + linea + NEWLINE);
//			}else if (ventaPasaje.getCentroCosto()!=null){
//				//(impl:13/03/2014 - jabanto)
//				CentroCosto centroCosto=ventaPasaje.getCentroCosto();
//				Integer lenMaxNameCC=27;
//				if(centroCosto.getDenominacion().length()>lenMaxNameCC)
//					linea = "CENTRO COSTO:"+centroCosto.getCodigo()+"-"+centroCosto.getDenominacion().substring(0,lenMaxNameCC);
//				else
//					linea = "CENTRO COSTO:"+centroCosto.getCodigo()+"-"+centroCosto.getDenominacion();
//				
//				bw.write(tabular(15) + linea + NEWLINE);
//			}else if(ventaPasaje.getTipoMovimiento().getId().intValue()==Constantes.ID_TIPMOV_PREPAGADO){
//				linea = "BOLETO PREPAGADO";
//				bw.write(tabular(15) + linea+ tabular(59) + "PREPAGADO" + NEWLINE);
//			}else
//				bw.write(NEWLINE);
//			
//			bw.write(NEWLINE);
//			
//			//-----	LINEA
//			if(ventaPasaje.getTipoMovimiento().getId().intValue()==Constantes.ID_TIPMOV_PREPAGADO){
//				linea = tabular(90) + "RC : "+ventaPasaje.getNumeroBoletoAnterior();
//				bw.write(linea + NEWLINE);
//			}else if(ventaPasaje.getTipoMovimiento().getId().intValue()==Constantes.ID_TIPMOV_CREDITO){
//				linea = tabular(90) + "VOUCHER : "+ventaPasaje.getNumeroBoletoAnterior();
//				bw.write(linea + NEWLINE);
//			}else if(ventaPasaje.getTipoMovimiento().getId().intValue()==Constantes.ID_TIPMOV_REIMPRESION){
//				linea = tabular(90) + "REIMP : "+ventaPasaje.getNumeroBoletoAnterior();
//				bw.write(linea + NEWLINE);
//			}else
//				bw.write(NEWLINE);
//			
//			//-----		Linea 3 	HORA DE EMISION
//			String strHoraEmision = Util.DatetoString(new Date(), Constantes.TIME_FORMAT)+tabular(21);
//			linea = tabular(40) + strHoraEmision.substring(0, 21);
//			bw.write(linea + NEWLINE);
////			bw.write("XXXXOXXXXRXXXXOXXXXRXXXXOXXXXRXXXXOXXXXRXXXXOXXXXRXXXXOXXXXRXXXXOXXXXRXXXXOXXXXRXXXXOXXXXRXXXXOXXXXRXXXXOXXXXRXXXXOXXXXRXXXXOXXXXRXXXXOXXXXRXXXXOXXXXRXXXXOXXXXR");
//			
//			//-----		Linea 4 	FECHA EMISION, CADUCIDAD
//			String strFechaEmision = Util.DatetoString(new Date(), Constantes.DATE_FORMAT) + tabular(21);
//			String strFechaCaducidad = Util.DatetoString(ventaPasaje.getFechaCaducidad(), Constantes.DATE_FORMAT) + tabular(26);
//			linea = tabular(30) + strFechaEmision.substring(0, 21) + tabular(10) + strFechaCaducidad.substring(0, 26) + 
//					tabular(18) + strFechaEmision.substring(0, 10) + tabular(12) + strFechaCaducidad.substring(0, 10);   
//			bw.write(linea + NEWLINE);
//			
//			//-----		Linea 5 	RAZON SOCIAL, RUC
//			if(ventaPasaje.getCliente()!=null){
//				String strRazon = ventaPasaje.getCliente().getRazonSocial() + tabular(48);
//				String strRuc = ventaPasaje.getCliente().getNumeroDocumento() + tabular(30);
//				linea = tabular(10) + strRazon.substring(0, 40) + tabular(10) + strRuc.substring(0, 30) + tabular(10) + 
//						strRazon.substring(0, 23) + tabular(2) + strRuc.substring(0, 11);
//				bw.write(linea + NEWLINE);
//			}else
//				bw.write(NEWLINE);
//			
//			//-----		Linea 6 	PASAJERO, EDAD
//			String pasajero = ventaPasaje.getPasajero().toString() + tabular(48);
//			Long edad = null;
//			if(ventaPasaje.getPasajero().getFechaNacimiento()!=null && !ventaPasaje.getPasajero().getFechaNacimiento().equals(Constantes.FECHA_NULL))
//				edad = (new Date().getTime()-Util.StringtoDate(ventaPasaje.getPasajero().getFechaNacimiento(), Constantes.DATE_FORMAT).getTime())/(Constantes.MILISEGUNDOS_X_DIA*365);
//			
//			if(edad !=null)
//				linea = tabular(10) + pasajero.substring(0, 40) + tabular(10) + (edad.toString()+tabular(30)).substring(0, 30) + 
//					tabular(10) + pasajero.substring(0, 23) + tabular(5) + edad.toString();
//			else
//				linea = tabular(10) + pasajero.substring(0, 40) + tabular(50) + pasajero.substring(0, 23);
//			bw.write(linea + NEWLINE);
//			
//			//-----		Linea 7 	TIPO DOC, NUMDOC, ORIGEN, DESTINO
//			String strOrigens = ventaPasaje.getRuta().getOrigen();
//			String strServicio = ventaPasaje.getServicio().getDenominacion();
//			String strDestino = ventaPasaje.getRuta().getDestino();
//			String strTipoDocumento = ventaPasaje.getPasajero().getTipoDocumento().getDenominacion();
//			
//			linea = tabular(10) + (strTipoDocumento+tabular(40)).substring(0, 40) + tabular(10) + (ventaPasaje.getPasajero().getNumeroDocumento()+tabular(30)).substring(0, 30);
//			linea = linea +	tabular(10) + (strOrigens+" - "+strServicio+tabular(20)).substring(0, 20) + 
//					tabular(5) + (strDestino+tabular(12)).substring(0, 12);
//			bw.write(linea + NEWLINE);
//			
//			//-----		Linea 8 	ORIGEN, DESTINO			
//			linea = tabular(10) + (ventaPasaje.getRuta().getOrigen()+" - "+strServicio+tabular(30)).substring(0, 30) + 
//					tabular(12) + ventaPasaje.getRuta().getDestino();
//			bw.write(linea + NEWLINE);
//			
//			//-----		Linea 9 	TIPOPAGO, LUGAR EMBARQUE, HORA EMBARQUE, FVIAJE, HPARTIDA, ASIENTO
//			String strAsiento = "00"+ventaPasaje.getNumeroAsiento();
//			strAsiento = strAsiento.substring(strAsiento.length()-2);
//			String strFechaViaje = ventaPasaje.getFechaPartida()==null?null:Util.DatetoString(ventaPasaje.getFechaPartida(), Constantes.DATE_FORMAT);
//			/**		PARA OBTENER LA HORA DE EMBARQUE	*/
//			String strHoraRealEmbarque=null;
//			if(ventaPasaje.getFechaPartida()==null)
//				strHoraRealEmbarque = "";
//			else
//				strHoraRealEmbarque = obtenerHoraEmbarque(ventaPasaje.getItinerario().getId(), ventaPasaje.getAgenciaPartida().getId());
//			
//			String strHoraPartida = (strHoraRealEmbarque==null?ventaPasaje.getHoraPartida():strHoraRealEmbarque);
//			String strTC = "";
//			String strOperadorTC = "";
//			String strEmbarque = "";
//			String strHoraEmbarque = "";
//			if(ventaPasaje.getTarjetaCredito()!=null){
//				strTC = ventaPasaje.getTarjetaCredito().getDenominacion();
//				strOperadorTC = ventaPasaje.getTarjetaCredito().getOperadorTarjetaCredito().getDenominacion();
//			}
//			
//			String strFormaPago = ventaPasaje.getFormaPago().getDenominacion()+"-"+ventaPasaje.getTipoFormaPago().getDenominacion()+"-"+strOperadorTC+"->"+strTC;
//			
//			if(ventaPasaje.getFechaPartida()!=null){
//				strEmbarque = "Emb.: " + (ventaPasaje.getAgenciaPartida().getDenominacion().length()>23?ventaPasaje.getAgenciaPartida().getDenominacion().substring(0, 22):ventaPasaje.getAgenciaPartida().getDenominacion());
//				strHoraEmbarque = "H.Emb.: " + strHoraPartida;
//				linea = (strFormaPago+tabular(42)).substring(0, 42) + (strEmbarque +"/"+strHoraEmbarque+tabular(43)).substring(0, 43);
//				linea = linea + tabular(7) + strFechaViaje + tabular(8) + strHoraPartida + tabular(15) + strAsiento;				
//			}else
//				linea = tabular(3) + (strFormaPago+tabular(30)).substring(0, 30);
//			
//			bw.write(linea + NEWLINE);
//			
//			//-----		Linea 10 	VALORPASAJE, TIPOPAGO
//			String strTarifa = Util.toNumberFormat(ventaPasaje.getImportePagado(), 2);
//			if(ventaPasaje.getVentaPasaje()!=null)
//				linea = tabular(72) + strTarifa + tabular(12) + boletoPostergado;
//			else
//				linea = tabular(72) + strTarifa + tabular(12) + strFormaPago;
//			bw.write(linea + NEWLINE);
//			
//			//-----		Linea 11 	FVIAJE, HPARTIDA, ASIENTO, DCTO, IGV, VALORPASAJE
//			String descuento = Util.toNumberFormat(ventaPasaje.getDescuento(), 2);
//			if(ventaPasaje.getFechaPartida()!=null){
//				linea = strFechaViaje + tabular(5) + strHoraPartida + tabular(10) + strAsiento + tabular(8) + descuento + tabular(28) + "0.00";
//				linea = linea + tabular(14) + (strEmbarque+tabular(25)).substring(0, 25)+ tabular(11) + strTarifa;
//			}else
//				linea = tabular(40) + descuento + tabular(28) + "0.00" + tabular(52) + strTarifa;
//			
//			bw.write(linea + NEWLINE);
//			
//			//-----		Linea 12 	SON, TOTAL, IGV
//			String strImportePagado = Util.toNumberFormat(ventaPasaje.getImportePagado(), 2);
//			ConvertirNumeroString num = new ConvertirNumeroString();
//			int indice = strImportePagado.lastIndexOf(".");
////			String strImporte = num.convertirLetras(ventaPasaje.getImportePagado().intValue()).toUpperCase()+" CON " + strImportePagado.substring(indice+1) + "/100 NUEVOS SOLES" + tabular(35);
//			String strImporte = num.convertirLetras(ventaPasaje.getImportePagado().intValue()).toUpperCase()+" CON " + strImportePagado.substring(indice+1) + "/100 SOLES" + tabular(35);
//			strImporte = strImporte.substring(0, 35);
//			linea = tabular(25) + strImporte + tabular(12) + strImportePagado + tabular(12)+ (strHoraEmbarque+tabular(20)).substring(0, 20) + tabular(19) + "0.00";
//			bw.write(linea + NEWLINE);
//			
//			//-----		Linea 13	USUARIO, CONTROL, CONTROL, USUARIO, TOTAL
//			linea = tabular(60) + (ventaPasaje.getNumeroControl()+tabular(30)).substring(0, 30) + 
//					tabular(1) + (ventaPasaje.getNumeroControl()).substring(0, 16) + 
//					tabular(20) + strImportePagado;
//			bw.write(linea + NEWLINE);
//			bw.write(NEWLINE);
//			bw.write(NEWLINE);
//			bw.write(NEWLINE);
//			bw.close();
//		}catch(IOException ioex){
//			ioex.printStackTrace();
//		}catch(Exception ex){
//			ex.printStackTrace();
//		}
//		return fichero;
//	}
	
	
	public static final File creaLiquidacion(Liquidacion liquidacion, Usuario usuarioCierre,Boolean reimpresion){
//		String fichero = Constantes.DIRECTORY_LIQUIDACION + liquidacion.getId()+".txt";
		String fichero = Constantes.DIRECTORY_LIQUIDACION + Constantes.CLAVE_PAHT+ liquidacion.getId()+".txt";
		File file = new File(fichero);
		file = new File(fichero);
		Integer longitud_C=0;
		Integer longitud_M=0;
		try{
			BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file),"UTF-8"));
			String linea = "";
						
			//---> line 1:	TITULO DEL REPORTE
			String title="LIQUIDACION DE TURNO";
			linea = tabular(38)+title;
			bw.write(linea + NEWLINE);
			
			//---> line 2: 	OFICINA Y FECHA HORA DE IMPRESION
			longitud_C=liquidacion.getAgencia().getDenominacion().toString().length();		
			linea = tabular(3) +"OFICINA   : "+liquidacion.getAgencia().getDenominacion()+tabular(45-longitud_C)+"FECHA LIQUIDACION: "+ Constantes.FORMAT_DATE.format(liquidacion.getFechaLiquidacion());
			bw.write(linea + NEWLINE);
			
			//---> line 3: representante de ventas
			String rv =liquidacion.getUsuario().getNombre()+" "+liquidacion.getUsuario().getApellidoPaterno();
			longitud_C = rv.toString().length();
			linea = tabular(3) + "REP. VENTA: "+ liquidacion.getUsuario().getNombre()+" "+liquidacion.getUsuario().getApellidoPaterno();
			if (reimpresion==true)
				linea += tabular(45-longitud_C)+"FECHA REIMPRESION: "+(Constantes.FORMAT_LONG.format(new Date()));
			bw.write(linea+ NEWLINE);			
			
			//---> line 4: Salto de linea
			bw.write(NEWLINE);
			
			//---> line 5: Titulo: 
//			linea=tabular(3)+"COMPROBANTE"+tabular(14)+"SERIE"+tabular(12)+"CANTIDAD"+tabular(13)+"IMPORTE"; 
			linea=tabular(3)+"COMPROBANTE"+tabular(8)+"SERIE"+tabular(4)+"DESDE"+tabular(7)+"HASTA"+tabular(6)+"CANTIDAD"+tabular(4)+"IMPORTE";
			bw.write(linea+NEWLINE);
			
			//---> line 6: linea
			linea=tabular(3)+"-----------------------------------------------------------------------";
			bw.write(linea+NEWLINE);
			
			//---> line 7: Valores: Especies Valorada, serie, del, al, cont, corte
//			List<Liquidacion>list = ServiceLocator.getLiquidacionManager().BuscarEspeciesValoradas(Constantes.FORMAT_DATE.format(liquidacion.getFechaLiquidacion()), liquidacion.getAgencia().getId(), liquidacion.getUsuario().getId());
//			Map<String, ResumenComprobante> mapResumen = ServiceLocator.getLiquidacionManager().buscarResumenComprobantes(Constantes.FORMAT_DATE.format(liquidacion.getFechaLiquidacion()), liquidacion.getAgencia().getId(), liquidacion.getUsuario().getId());
//			for(String key : mapResumen.keySet()) {
//				ResumenComprobante resumen = mapResumen.get(key);
//				longitud_C=resumen.getComprobante().toString().length();
//				String strSerie = null;
//				strSerie = resumen.getIdTipoComprobante()!=Constantes.ID_TIPCOM_GUIA_TRANSPORTISTA?resumen.getSerie():" "+resumen.getSerie().substring(0, 3);
//				linea = tabular(3)+resumen.getComprobante()+tabular(26-longitud_C)+strSerie;
//				longitud_C=resumen.getCantidad().toString().length();
//				linea += tabular(16)+tabular(1-longitud_C+1)+resumen.getCantidad();
//				longitud_M=(int) resumen.getMonto().intValue();
//				longitud_M = longitud_M.toString().length(); 
//				if (longitud_M==4)
//					longitud_M += +1;
//				else if (longitud_M==5)
//					longitud_M += +2;
//				linea += tabular(19-longitud_M)+Util.toNumberFormat(resumen.getMonto(),2);
//				bw.write(linea+NEWLINE);
//			}						
			
			
			List<Liquidacion>list = ServiceLocator.getLiquidacionManager().BuscarEspeciesValoradas(Constantes.FORMAT_DATE.format(liquidacion.getFechaLiquidacion()), liquidacion.getAgencia().getId(), liquidacion.getUsuario().getId());
			Map<String, ResumenComprobante> mapResumen = ServiceLocator.getLiquidacionManager().buscarResumenComprobantes(Constantes.FORMAT_DATE.format(liquidacion.getFechaLiquidacion()), liquidacion.getAgencia().getId(), liquidacion.getUsuario().getId());
			for(String key : mapResumen.keySet()) {
				ResumenComprobante resumen = mapResumen.get(key);
				longitud_C=resumen.getComprobante().toString().length();
				String strSerie = null;
				String desde = "";
				String hasta = "";
				strSerie = resumen.getIdTipoComprobante()!=Constantes.ID_TIPCOM_GUIA_TRANSPORTISTA?resumen.getSerie():" "+resumen.getSerie().substring(0, 3);
				linea = tabular(3)+resumen.getComprobante()+tabular(19-longitud_C)+strSerie;
				
				for(Liquidacion especieValorada: list) {
					if(especieValorada.getSerie().equals(strSerie)) {
						desde = especieValorada.getBoletoInicial();
						hasta = especieValorada.getboletoFinal();
						break;
					}
				}				
				longitud_C = desde.length();
				linea += tabular(5)+tabular(1-longitud_C+1)+desde;
				longitud_C = hasta.length();
				linea += tabular(5)+tabular(1-longitud_C+1)+hasta;
				
				
				longitud_C=resumen.getCantidad().toString().length();
				linea += tabular(5)+tabular(1-longitud_C+1)+resumen.getCantidad();
				longitud_M=(int) resumen.getMonto().intValue();
				longitud_M = longitud_M.toString().length(); 
				if (longitud_M==4)
					longitud_M += +1;
				else if (longitud_M==5)
					longitud_M += +2;
				linea += tabular(11-longitud_M)+Util.toNumberFormat(resumen.getMonto(),2);
				bw.write(linea+NEWLINE);
			}
			
			/**CARGA - RESUMEN ESPECIES VALORADAS*/
			TranscarUsuarioPersonal transcarUsuarioPersonal = ServiceLocator.getTranscarManager().buscarUsuarioPersonal(liquidacion.getUsuario().getLogin());
			int agenciaIdCargo = 0;
			String fechaLiquidacion =Constantes.FORMAT_DATE.format(liquidacion.getFechaLiquidacion());

			if(transcarUsuarioPersonal!=null){ // && liquidacion.getAgencia().getCodigo()!=null){
//				agenciaIdCargo = ServiceLocator.getTranscarManager().buscarIdAgenciaByCodigoAgenciaPasajes(liquidacion.getAgencia().getCodigo());				
				agenciaIdCargo = ServiceLocator.getTranscarManager().buscarIdAgenciaByCodigoAgenciaPasajes(liquidacion.getAgencia().getId().toString());
				List<Liquidacion> listLiquidacionCarga = ServiceLocator.getTranscarManager().buscarLiquidacionTurnoResumenEspVal(transcarUsuarioPersonal.getId(), agenciaIdCargo, fechaLiquidacion, fechaLiquidacion);
				for(Liquidacion _liquidacion : listLiquidacionCarga) {
					longitud_C=_liquidacion.getComprobante().toString().length();
					String strSerie = (_liquidacion.getSerie()!=null?_liquidacion.getSerie():"");
					String desde = _liquidacion.getBoletoInicial();
					String hasta = _liquidacion.getboletoFinal();
					linea = tabular(3)+_liquidacion.getComprobante()+tabular(19-longitud_C)+strSerie;
										
					longitud_C = desde.length();
					linea += tabular(5)+tabular(1-longitud_C+1)+desde;
					longitud_C = hasta.length();
					linea += tabular(5)+tabular(1-longitud_C+1)+hasta;
										
					longitud_C=_liquidacion.getCantidadBoletos().toString().length();
					linea += tabular(5)+tabular(1-longitud_C+1)+_liquidacion.getCantidadBoletos();
					longitud_M=(int) _liquidacion.getImporte().intValue();
					longitud_M = longitud_M.toString().length(); 
					if (longitud_M==4)
						longitud_M += +1;
					else if (longitud_M==5)
						longitud_M += +2;
					linea += tabular(11-longitud_M)+Util.toNumberFormat(_liquidacion.getImporte(),2);
					bw.write(linea+NEWLINE);
				}
			}
			
			
//			for (Liquidacion especiesValorada: list ){
//				longitud_C=especiesValorada.getTipoComprobante().getDenominacion().toString().length();
//				linea = tabular(3)+especiesValorada.getTipoComprobante().getDenominacion()+tabular(22-longitud_C)+"Serie: "+especiesValorada.getSerie()+" ";
//				linea +="Del: "+especiesValorada.getBoletoInicial();
//				linea += " Al: "+especiesValorada.getboletoFinal()+tabular(3);
//				longitud_C=especiesValorada.getCantidadBoletos().toString().length();
//				linea += tabular(3)+"Cant:"+tabular(1-longitud_C+1)+especiesValorada.getCantidadBoletos()+tabular(3)+"Corte: "+tabular(1-longitud_C)+especiesValorada.getCorte();
//				bw.write(linea+NEWLINE);
//			}
					
			/**PASAJES*/
			Liquidacion liquidacion2 = ServiceLocator.getLiquidacionManager().buscarRptLiquidacionTurno(Constantes.FORMAT_DATE.format(liquidacion.getFechaLiquidacion()), liquidacion.getAgencia().getId(), liquidacion.getUsuario().getId());
			
			/**CARGA*/
			if(transcarUsuarioPersonal!=null) { //&& liquidacion.getAgencia().getCodigo()!=null){				
				Liquidacion liquidacion2Cargo = ServiceLocator.getTranscarManager().buscarLiquidacionTurno(transcarUsuarioPersonal.getId(), agenciaIdCargo, fechaLiquidacion, fechaLiquidacion);
				
				liquidacion2.setMontoContado(liquidacion2.getMontoContado() + liquidacion2Cargo.getMontoContado());
				liquidacion2.setCantidadContado(liquidacion2.getCantidadContado() + liquidacion2Cargo.getCantidadContado());
				liquidacion2.setMontoTarjetaVisa(liquidacion2.getMontoTarjetaVisa() + liquidacion2Cargo.getMontoTarjetaVisa());
				liquidacion2.setCantidadTarjetaVisa(liquidacion2.getCantidadTarjetaVisa() + liquidacion2Cargo.getCantidadTarjetaVisa());				
				liquidacion2.setMontoTarjetaMasterCard(liquidacion2.getMontoTarjetaMasterCard() + liquidacion2Cargo.getMontoTarjetaMasterCard());
				liquidacion2.setCantidadTarjetaMasterCard(liquidacion2.getCantidadTarjetaMasterCard() + liquidacion2Cargo.getCantidadTarjetaMasterCard());
				liquidacion2.setMontoNotasCredito(liquidacion2.getMontoNotasCredito() + liquidacion2Cargo.getMontoNotasCredito());
				liquidacion2.setCantidadNotasCredito(liquidacion2.getCantidadNotasCredito() + liquidacion2Cargo.getCantidadNotasCredito());
				liquidacion2.setMontoPCE(liquidacion2.getMontoPCE() + liquidacion2Cargo.getMontoPCE());
				liquidacion2.setCantidadPCE(liquidacion2.getCantidadPCE() + liquidacion2Cargo.getCantidadPCE());
			}			
			
			
			/********** BUSCAR LIQUIDACION DE VENTA DE SEGUROS********************************/
			/* Busca liquidacion de venta de seguros*/
//			double montoTotalVentasSeguro=0,montoTotalVentasSeguroPaxfre=0;
//			int cantidadVentasSeguro=0,cantidadVentasPaxfre=0;
			
			
//			VSLiquidacion vsLiquidacion=ServiceLocator.getVentaSeguroManager().buscarLiquidacion(liquidacion.getUsuario().getId(),liquidacion.getAgencia().getId(), Constantes.FORMAT_DATE.format(liquidacion.getFechaLiquidacion()), null);
//			if(vsLiquidacion!=null){
//				/* Busca las busca ventas por el id de la liquidacion encontrada*/
//				vsLiquidacion=ServiceLocator.getVentaSeguroManager().buscarLiquidacionVentas(vsLiquidacion.getId());
//				montoTotalVentasSeguro=vsLiquidacion.getMontoVentasTotal();
//				cantidadVentasSeguro=vsLiquidacion.getCantidadVentasTotal();
//				cantidadVentasPaxfre=vsLiquidacion.getCantidadVentasPaxFree();
//				montoTotalVentasSeguroPaxfre=vsLiquidacion.getMontoVentasPaxFree();
//			}
			/*********************************************************************************/
			
			/********** BUSCAR LAS VENTAS DEL POOL********************************/
			Double montoTotalVentasPoolEfectivo=.00, montoTotalVentasPoolVisa=.00, montoTotalVentasPoolMastercard=.00;
			Integer cantidadVentasPoolEfectivo=0,cantidadVentasPoolVisa=0, cantidadVentasPoolMastercard=0;
//			String fechaLiquidacion=Constantes.FORMAT_DATE.format(liquidacion.getFechaLiquidacion());
//			List<VentaPool> ventasPool=ServiceLocator.getVentaPoolManager().buscarVentas(fechaLiquidacion, fechaLiquidacion, liquidacion.getAgencia().getId(), liquidacion.getUsuario().getId());
//			for(VentaPool ventaPool : ventasPool){
//				if(ventaPool.getEstadoRegistro().equals(Constantes.VALUE_ACTIVO)){
//					if(ventaPool.getTarjetaCredito()==null){
//						montoTotalVentasPoolEfectivo+=+ventaPool.getImportePagado();
//						cantidadVentasPoolEfectivo++;
//					}else if(ventaPool.getTarjetaCredito().getOperadorTarjetaCredito().getId().intValue()==Constantes.ID_OPETARCRE_VISA){
//						montoTotalVentasPoolVisa+=ventaPool.getImportePagado();
//						cantidadVentasPoolVisa++;
//					}else if(ventaPool.getTarjetaCredito().getOperadorTarjetaCredito().getId().intValue()==Constantes.ID_OPETARCRE_MSTERCARD){
//						montoTotalVentasPoolMastercard+=ventaPool.getImportePagado();
//						cantidadVentasPoolMastercard++;
//					}
//				}
//			}
			cantidadVentasPoolEfectivo= liquidacion2.getCantidadEfecPool();
			montoTotalVentasPoolEfectivo= liquidacion2.getMontoEfecPool();
			cantidadVentasPoolVisa= liquidacion2.getCantidadTCVisaPool();
			montoTotalVentasPoolVisa= liquidacion2.getMontoTCVisaPool();
			cantidadVentasPoolMastercard= liquidacion2.getCantidadTCMastercardPool();
			montoTotalVentasPoolMastercard= liquidacion2.getMontoTCMastercardPool();
			/********************************************************************/
			String interline="-----------------------------------------------------------------------";
			
			//---> line 8: Resumen de Ventas
			bw.write(NEWLINE);
			linea=tabular(3)+"RESUMEN VENTAS";
			bw.write(linea+NEWLINE);
			
			//---> line 9: linea
//			linea=tabular(3)+"--------------";
//			bw.write(linea+NEWLINE);
			
			//---> line 10: tipo, cantidad, importe
			linea=tabular(7)+"TIPO"+tabular(34)+"CANTIDAD"+tabular(13)+"IMPORTE";
			bw.write(linea+NEWLINE);
			linea=tabular(3)+interline;
			bw.write(linea+NEWLINE);
			
			//---> line 11: --> Contado
			longitud_C=liquidacion2.getCantidadContado().toString().length();
			longitud_M=(int) liquidacion2.getMontoContado();
			longitud_M = longitud_M.toString().length(); 
			if (longitud_M==4)
				longitud_M += +1;
			else if (longitud_M==5)
				longitud_M += +2;
			linea=tabular(3)+"EFECTIVO"+tabular(40-longitud_C)+liquidacion2.getCantidadContado()+tabular(19-longitud_M)+Util.toNumberFormat(liquidacion2.getMontoContado(),2);
			bw.write(linea+NEWLINE);
			
			//---> line 11: --> Contado Dolares
			if(liquidacion2.getCantidadContadoDolares()>0) {
				longitud_C=liquidacion2.getCantidadContadoDolares().toString().length();
				longitud_M=(int) liquidacion2.getMontoContadoDolares();
				longitud_M = longitud_M.toString().length(); 
				if (longitud_M==4)
					longitud_M += +1;
				else if (longitud_M==5)
					longitud_M += +2;
				linea=tabular(3)+"EFECTIVO DOLARES"+tabular(32-longitud_C)+liquidacion2.getCantidadContadoDolares()+tabular(19-longitud_M)+Util.toNumberFormat(liquidacion2.getMontoContadoDolares(),2);
				bw.write(linea+NEWLINE);
			}
			
			//---> line 12: --> Tarjeta visa
			longitud_C=liquidacion2.getCantidadTarjetaVisa().toString().length();
			longitud_M=(int) liquidacion2.getMontoTarjetaVisa();
			longitud_M = longitud_M.toString().length(); 
			if (longitud_M==4)
				longitud_M += +1;
			else if (longitud_M==5)
				longitud_M += +2;
			linea=tabular(3)+"TARJETA CREDITO VISA"+tabular(28-longitud_C)+liquidacion2.getCantidadTarjetaVisa()+tabular(19-longitud_M)+Util.toNumberFormat(liquidacion2.getMontoTarjetaVisa(),2);
			bw.write(linea+NEWLINE);
			
			//---> line 13: --> Tarjeta Master Card
			longitud_C=liquidacion2.getCantidadTarjetaMasterCard().toString().length();
			longitud_M=(int) liquidacion2.getMontoTarjetaMasterCard();
			longitud_M = longitud_M.toString().length(); 
			if (longitud_M==4)
				longitud_M += +1;
			else if (longitud_M==5)
				longitud_M += +2;
			linea=tabular(3)+"TARJETA CREDITO MASTER CARD"+tabular(21-longitud_C)+liquidacion2.getCantidadTarjetaMasterCard()+tabular(19-longitud_M)+Util.toNumberFormat(liquidacion2.getMontoTarjetaMasterCard(),2);
			bw.write(linea+NEWLINE);
			
			//---> line 14: --> Cortesia
			longitud_C=liquidacion2.getCantidadcortesia().toString().length();
			longitud_M=(int) liquidacion2.getMontoCortesia();
			longitud_M = longitud_M.toString().length();
			if (longitud_M==4)
				longitud_M += +1;
			else if (longitud_M==5)
				longitud_M += +2;	
			linea=tabular(3)+"CORTESIA"+tabular(40-longitud_C)+liquidacion2.getCantidadcortesia()+tabular(19-longitud_M)+Util.toNumberFormat(liquidacion2.getMontoCortesia(),2);
			bw.write(linea+NEWLINE);
			
			//---> line 15: --> Prepagado
			if(liquidacion2.getCantidadPrepagado() > 0){
				longitud_C=liquidacion2.getCantidadPrepagado().toString().length();
				longitud_M=(int) liquidacion2.getMontoPrepagado();
				longitud_M = longitud_M.toString().length();
				if (longitud_M==4)
					longitud_M += +1;
				else if (longitud_M==5)
					longitud_M += +2;
				linea=tabular(3)+"PREPAGADOS"+tabular(38-longitud_C)+liquidacion2.getCantidadPrepagado()+tabular(19-longitud_M)+Util.toNumberFormat(liquidacion2.getMontoPrepagado(),2);
				bw.write(linea+NEWLINE);
			}
			
			//---> line 16: --> Creditos
			longitud_C=liquidacion2.getCantidadCreditos().toString().length();
			longitud_M=(int) liquidacion2.getMontoCreditos();
			longitud_M = longitud_M.toString().length();
			if (longitud_M==4)
				longitud_M += +1;
			else if (longitud_M==5)
				longitud_M += +2;
			linea=tabular(3)+"CREDITOS"+tabular(40-longitud_C)+liquidacion2.getCantidadCreditos()+tabular(19-longitud_M)+Util.toNumberFormat(liquidacion2.getMontoCreditos(),2);
			bw.write(linea+NEWLINE);
			
			//---> line 16: --> Creditos
			if(liquidacion2.getCantidadCreditosDolares()>0) {
				longitud_C=liquidacion2.getCantidadCreditosDolares().toString().length();
				longitud_M=(int) liquidacion2.getMontoCreditosDolares();
				longitud_M = longitud_M.toString().length();
				if (longitud_M==4)
					longitud_M += +1;
				else if (longitud_M==5)
					longitud_M += +2;
				linea=tabular(3)+"CREDITOS DOLARES"+tabular(32-longitud_C)+liquidacion2.getCantidadCreditosDolares()+tabular(19-longitud_M)+Util.toNumberFormat(liquidacion2.getMontoCreditosDolares(),2);
				bw.write(linea+NEWLINE);
			}
			
			//---> line 17: --> PCE
			longitud_C=liquidacion2.getCantidadPCE().toString().length();
			longitud_M=(int) liquidacion2.getMontoPCE();
			longitud_M = longitud_M.toString().length();
			if (longitud_M==4)
				longitud_M += +1;
			else if (longitud_M==5)
				longitud_M += +2;
			linea=tabular(3)+"PCE"+tabular(45-longitud_C)+liquidacion2.getCantidadPCE()+tabular(19-longitud_M)+Util.toNumberFormat(liquidacion2.getMontoPCE(),2);
			bw.write(linea+NEWLINE);
			
			//Co0mentado por MAOE 27/06/2021, TRANSMAR NO MANEJA POOL
			//---> line 16: --> Venta Seguros
//			longitud_C=String.valueOf(cantidadVentasSeguro).length();
//			longitud_M=(int) montoTotalVentasSeguro;			
//			longitud_M = longitud_M.toString().length();
//			if (longitud_M==4)
//				longitud_M += +1;
//			else if (longitud_M==5)
//				longitud_M += +2;
//			linea=tabular(3)+"VENTA DE SEGUROS"+tabular(32-longitud_C)+cantidadVentasSeguro+tabular(19-longitud_M)+Util.toNumberFormat(montoTotalVentasSeguro,2);
//			bw.write(linea+NEWLINE);
			
			//---> line 16: --> Venta Pool
//			longitud_C=String.valueOf(cantidadVentasPoolEfectivo).length();
//			longitud_M=(int) montoTotalVentasPoolEfectivo.doubleValue();			
//			longitud_M = longitud_M.toString().length();
//			if (longitud_M==4)
//				longitud_M += +1;
//			else if (longitud_M==5)
//				longitud_M += +2;
//			linea=tabular(3)+"VENTA POOL - EFECTIVO"+tabular(27-longitud_C)+cantidadVentasPoolEfectivo+tabular(19-longitud_M)+Util.toNumberFormat(montoTotalVentasPoolEfectivo,2);
//			bw.write(linea+NEWLINE);
			
//			longitud_C=String.valueOf(cantidadVentasPoolVisa).length();
//			longitud_M=(int) montoTotalVentasPoolVisa.doubleValue();			
//			longitud_M = longitud_M.toString().length();
//			if (longitud_M==4)
//				longitud_M += +1;
//			else if (longitud_M==5)
//				longitud_M += +2;
//			linea=tabular(3)+"VENTA POOL - VISA"+tabular(31-longitud_C)+cantidadVentasPoolVisa+tabular(19-longitud_M)+Util.toNumberFormat(montoTotalVentasPoolVisa,2);
//			bw.write(linea+NEWLINE);
//			
//			longitud_C=String.valueOf(cantidadVentasPoolMastercard).length();
//			longitud_M=(int) montoTotalVentasPoolMastercard.doubleValue();			
//			longitud_M = longitud_M.toString().length();
//			if (longitud_M==4)
//				longitud_M += +1;
//			else if (longitud_M==5)
//				longitud_M += +2;
//			linea=tabular(3)+"VENTA POOL - MASTER CARD"+tabular(24-longitud_C)+cantidadVentasPoolMastercard+tabular(19-longitud_M)+Util.toNumberFormat(montoTotalVentasPoolMastercard,2);
//			bw.write(linea+NEWLINE);
			
			
			//---> line 17:
			linea=tabular(45)+"-------"+tabular(10)+"------------";
			bw.write(linea+NEWLINE);
			
			//---> liena 18: totales
			double totalVentaPasajes=liquidacion2.getMontoContado()+liquidacion2.getMontoTarjetaVisa()+liquidacion2.getMontoTarjetaMasterCard()+liquidacion2.getMontoCortesia()+liquidacion2.getMontoPrepagado()+liquidacion2.getMontoCreditos()+montoTotalVentasPoolEfectivo+montoTotalVentasPoolVisa+montoTotalVentasPoolMastercard+liquidacion2.getMontoPCE(); //+montoTotalVentasSeguro;
			Integer cantidadVentaPasajes=liquidacion2.getCantidadContado()+liquidacion2.getCantidadTarjetaVisa()+liquidacion2.getCantidadTarjetaMasterCard()+liquidacion2.getCantidadcortesia()+liquidacion2.getCantidadPrepagado()+liquidacion2.getCantidadCreditos()+cantidadVentasPoolEfectivo+cantidadVentasPoolVisa+cantidadVentasPoolMastercard+liquidacion2.getCantidadPCE(); //cantidadVentasSeguro;
			longitud_C=cantidadVentaPasajes.toString().length();
			longitud_M=(int) totalVentaPasajes;
			longitud_M = longitud_M.toString().length();
			if (longitud_M==4)
				longitud_M += +1;
			else if (longitud_M==5)
				longitud_M += +2;
			else if (longitud_M==6)
				longitud_M += +3;
			linea=tabular(3)+"TOTAL VENTAS"+tabular(36-longitud_C)+cantidadVentaPasajes+tabular(19-longitud_M)+Util.toNumberFormat(totalVentaPasajes,2);
			bw.write(linea+NEWLINE);
			
			double totalVentaPasajesDolares = 0.0;
			Integer cantidadVentaPasajesDolares = 0;
			if(liquidacion2.getCantidadContadoDolares()>0 || liquidacion2.getCantidadCreditosDolares()>0) {
				//---> liena 18: totales
				totalVentaPasajesDolares=liquidacion2.getMontoContadoDolares()+liquidacion2.getMontoCreditosDolares();
				cantidadVentaPasajesDolares=liquidacion2.getCantidadContadoDolares()+liquidacion2.getCantidadCreditosDolares();
				longitud_C=cantidadVentaPasajesDolares.toString().length();
				longitud_M=(int) totalVentaPasajesDolares;
				longitud_M = longitud_M.toString().length();
				if (longitud_M==4)
					longitud_M += +1;
				else if (longitud_M==5)
					longitud_M += +2;
				else if (longitud_M==6)
					longitud_M += +3;
				linea=tabular(3)+"TOTAL VENTAS DOLARES"+tabular(28-longitud_C)+cantidadVentaPasajesDolares+tabular(19-longitud_M)+Util.toNumberFormat(totalVentaPasajesDolares,2);
				bw.write(linea+NEWLINE);
			}
						
			//---> linea 19: Otros ingresos
			bw.write(NEWLINE);
			linea=tabular(3)+"OTROS INGRESOS"+tabular(28)+"CANTIDAD"+tabular(13)+"IMPORTE";
			bw.write(linea+NEWLINE);
			
			//---> linea 20: linea
//			linea=tabular(3)+"--------------";
			linea=tabular(3)+interline;
			bw.write(linea+NEWLINE);
			
			//Comentado por MAOE 27/06/2021, TRANSMAR NO MANEJA RECIBOS DE CAJA
			//---> Recibo de caja - efectivo
//			linea=tabular(3)+"RECIBOS DE CAJA EFECTIVO"+tabular(17)+"CANTIDAD"+tabular(13)+"IMPORTE";
//			longitud_C=liquidacion2.getCantidadRCCaja().toString().length();
//			longitud_M=(int) liquidacion2.getMontoRC();
//			longitud_M = longitud_M.toString().length(); 
//			if (longitud_M==4)
//				longitud_M += +1;
//			else if (longitud_M==5)
//				longitud_M += +2;
//			linea=tabular(3)+"RECIBOS DE CAJA EFECTIVO"+tabular(24-longitud_C)+liquidacion2.getCantidadRCCaja()+tabular(19-longitud_M)+Util.toNumberFormat(liquidacion2.getMontoRC(),2);;
//			bw.write(linea+NEWLINE);
//			
//			//---> Recibo de caja - tarjate visa
//			longitud_C=liquidacion2.getCantidadTarjetaVisaRC().toString().length();
//			longitud_M=(int) liquidacion2.getMontoTarjetaVisaRC();
//			longitud_M = longitud_M.toString().length(); 
//			if (longitud_M==4)
//				longitud_M += +1;
//			else if (longitud_M==5)
//				longitud_M += +2;
//			linea=tabular(3)+"RECIBOS DE CAJA TARJETA VISA"+tabular(20-longitud_C)+liquidacion2.getCantidadTarjetaVisaRC()+tabular(19-longitud_M)+Util.toNumberFormat(liquidacion2.getMontoTarjetaVisaRC(),2);;
//			bw.write(linea+NEWLINE);
//			
//			//---> Recibo de caja - tarjate Master card
//			longitud_C=liquidacion2.getCantidadTarjetaMasterCardRC().toString().length();
//			longitud_M=(int) liquidacion2.getMontoTarjetaMasterCardRC();
//			longitud_M = longitud_M.toString().length(); 
//			if (longitud_M==4)
//				longitud_M += +1;
//			else if (longitud_M==5)
//				longitud_M += +2;
//			linea=tabular(3)+"RECIBOS DE CAJA TARJETA MASTER CARD"+tabular(13-longitud_C)+liquidacion2.getCantidadTarjetaMasterCardRC()+tabular(19-longitud_M)+Util.toNumberFormat(liquidacion2.getMontoTarjetaMasterCardRC(),2);;
//			bw.write(linea+NEWLINE);
			
			//-->Gastos administrativos - Efectivo
			longitud_C=liquidacion2.getCantidadGastosAdminEfectivo().toString().length();
			longitud_M=(int) liquidacion2.getMontoGastosAdminEfectivo();
			longitud_M = longitud_M.toString().length(); 
			if (longitud_M==4)
				longitud_M += +1;
			else if (longitud_M==5)
				longitud_M += +2;
			linea=tabular(3)+"GASTO ADMIN. EFECTIVO"+tabular(27-longitud_C)+liquidacion2.getCantidadGastosAdminEfectivo()+tabular(19-longitud_M)+Util.toNumberFormat(liquidacion2.getMontoGastosAdminEfectivo(),2);;
			bw.write(linea+NEWLINE);			
			
			//-->Gastos administrativos - Tarjeta visa
			longitud_C=liquidacion2.getCantidadGastosAdminTarjetaVisa().toString().length();
			longitud_M=(int) liquidacion2.getMontoGastosAdminTarjetaVisa();
			longitud_M = longitud_M.toString().length(); 
			if (longitud_M==4)
				longitud_M += +1;
			else if (longitud_M==5)
				longitud_M += +2;
			linea=tabular(3)+"GASTO ADMIN. TARJETA VISA"+tabular(23-longitud_C)+liquidacion2.getCantidadGastosAdminTarjetaVisa()+tabular(19-longitud_M)+Util.toNumberFormat(liquidacion2.getMontoGastosAdminTarjetaVisa(),2);;
			bw.write(linea+NEWLINE);
			
			//-->Gastos administrativos - Tarjeta Master Card
			longitud_C=liquidacion2.getCantidadGastosAdminTarjetaMastercard().toString().length();
			longitud_M=(int) liquidacion2.getMontoGastosAdminTarjetaMastercard();
			longitud_M = longitud_M.toString().length(); 
			if (longitud_M==4)
				longitud_M += +1;
			else if (longitud_M==5)
				longitud_M += +2;
			linea=tabular(3)+"GASTO ADMIN. TARJETA MASTER CARD"+tabular(16-longitud_C)+liquidacion2.getCantidadGastosAdminTarjetaMastercard()+tabular(19-longitud_M)+Util.toNumberFormat(liquidacion2.getMontoGastosAdminTarjetaMastercard(),2);;
			bw.write(linea+NEWLINE);
						
			List<Gasto> lstIngresos =  ServiceLocator.getGastoManager().obtenerGastosByLiquidacion(Constantes.FORMAT_DATE.format(liquidacion.getFechaLiquidacion()), liquidacion.getAgencia().getId(), liquidacion.getUsuario().getId(), Constantes.TRUE_VALUE, false);
			int cantidadIngreso = 0;
			double importeIngreso = 0;
			for(Gasto otroIngreso: lstIngresos) {
				cantidadIngreso += otroIngreso.getCantidad();
				importeIngreso += otroIngreso.getMonto();
				longitud_C=otroIngreso.getCantidad().toString().length();
				longitud_M=(int) otroIngreso.getMonto().intValue();
				longitud_M = longitud_M.toString().length();
				if (longitud_M==4)
					longitud_M += +1;
				else if (longitud_M==5)
					longitud_M += +2;
				linea=tabular(3)+otroIngreso.getTipoGasto().getDenominacion()+tabular((48-otroIngreso.getTipoGasto().getDenominacion().length())-longitud_C)+otroIngreso.getCantidad()+tabular(19-longitud_M)+Util.toNumberFormat(otroIngreso.getMonto(),2);
				bw.write(linea+NEWLINE);
			}
			
			linea=tabular(45)+"-------"+tabular(10)+"------------";
			bw.write(linea+NEWLINE);
			
			//---> linea 22: valores otros ingresos
			Integer cantidadRCTotal=liquidacion2.getCantidadRCCaja()+liquidacion2.getCantidadTarjetaVisaRC()+liquidacion2.getCantidadTarjetaMasterCardRC()+
								    liquidacion2.getCantidadGastosAdminEfectivo()+liquidacion2.getCantidadGastosAdminTarjetaVisa()+liquidacion2.getCantidadGastosAdminTarjetaMastercard()+
								    cantidadIngreso;
			double montoRcTotal=liquidacion2.getMontoRC()+liquidacion2.getMontoTarjetaVisaRC()+liquidacion2.getMontoTarjetaMasterCardRC()+
								liquidacion2.getMontoGastosAdminEfectivo()+liquidacion2.getMontoGastosAdminTarjetaVisa()+liquidacion2.getMontoGastosAdminTarjetaMastercard()+
								importeIngreso;
			longitud_C=cantidadRCTotal.toString().length();
			longitud_M=(int) montoRcTotal;
			longitud_M = longitud_M.toString().length();
			if (longitud_M==4)
				longitud_M += +1;
			else if (longitud_M==5)
				longitud_M += +2;
			linea=tabular(3)+"TOTAL OTROS INGRESOS"+tabular(28-longitud_C)+cantidadRCTotal+tabular(19-longitud_M)+Util.toNumberFormat(montoRcTotal,2);
			bw.write(linea+NEWLINE);
			
			//---> linea 23: salto de linea
			bw.write(NEWLINE);
			
			//---> linea 24: Egresos.
			linea=tabular(3)+"EGRESOS";
			bw.write(linea+NEWLINE);
			
			//---> linea 25: linea
//			linea=tabular(3)+"-------";
//			bw.write(linea+NEWLINE);
			
			//---> linea 26: cabecera egresos.
			linea=tabular(7)+"CONCEPTO"+tabular(30)+"CANTIDAD"+tabular(13)+"IMPORTE";
			bw.write(linea+NEWLINE);
			linea=tabular(3)+interline;
			bw.write(linea+NEWLINE);
			
			List<Gasto> lstGastos =  ServiceLocator.getGastoManager().obtenerGastosByLiquidacion(Constantes.FORMAT_DATE.format(liquidacion.getFechaLiquidacion()), liquidacion.getAgencia().getId(), liquidacion.getUsuario().getId(), Constantes.FALSE_VALUE, false);
			int cantidadGasto = 0;
			double importeGasto = 0;
			for(int i=0; i<lstGastos.size(); i++) {
				Gasto gasto = lstGastos.get(i);
				cantidadGasto = cantidadGasto + gasto.getCantidad();
				importeGasto = importeGasto + gasto.getMonto();
				longitud_C=gasto.getCantidad().toString().length();
				longitud_M=(int) gasto.getMonto().intValue();
				longitud_M = longitud_M.toString().length();
				if (longitud_M==4)
					longitud_M += +1;
				else if (longitud_M==5)
					longitud_M += +2;
				linea=tabular(3)+gasto.getTipoGasto().getDenominacion()+tabular((48-gasto.getTipoGasto().getDenominacion().length())-longitud_C)+gasto.getCantidad()+tabular(19-longitud_M)+Util.toNumberFormat(gasto.getMonto(),2);
				bw.write(linea+NEWLINE);
			}
			
			
//			//---> linea 27: Gastos varios 
//			longitud_C=liquidacion2.getCantidadGastoVarios().toString().length();
//			longitud_M=(int) liquidacion2.getMontoGastoVarios();
//			longitud_M = longitud_M.toString().length();
//			if (longitud_M==4)
//				longitud_M += +1;
//			else if (longitud_M==5)
//				longitud_M += +2;
//			linea=tabular(3)+"GASTOS VARIOS"+tabular(35-longitud_C)+liquidacion2.getCantidadGastoVarios()+tabular(19-longitud_M)+Util.toNumberFormat(liquidacion2.getMontoGastoVarios(),2);
//			bw.write(linea+NEWLINE);
//			
//			//---> linea 28: Gastos con documento 
//			longitud_C=liquidacion2.getCantidadGastoConDocumento().toString().length();
//			longitud_M=(int) liquidacion2.getMontoGastoConDocumento();
//			longitud_M = longitud_M.toString().length();
//			if (longitud_M==4)
//				longitud_M += +1;
//			else if (longitud_M==5)
//				longitud_M += +2;
//			linea=tabular(3)+"GASTOS CON DOCUMENTO"+tabular(28-longitud_C)+liquidacion2.getCantidadGastoConDocumento()+tabular(19-longitud_M)+Util.toNumberFormat(liquidacion2.getMontoGastoConDocumento(),2);
//			bw.write(linea+NEWLINE);
//						
//			//---> linea 29: Peajes
//			longitud_C=liquidacion2.getCantidadPeajes().toString().length();
//			longitud_M=(int) liquidacion2.getMontoPeajes();
//			longitud_M = longitud_M.toString().length();
//			if (longitud_M==4)
//				longitud_M += +1;
//			else if (longitud_M==5)
//				longitud_M += +2;
//			linea=tabular(3)+"PEAJES"+tabular(42-longitud_C)+liquidacion2.getCantidadPeajes()+tabular(19-longitud_M)+Util.toNumberFormat(liquidacion2.getMontoPeajes(),2);
//			bw.write(linea+NEWLINE);
//			
//			//---> linea 30: Pago de Giros
//			longitud_C=liquidacion2.getCantidadPagoGiros().toString().length();
//			longitud_M=(int) liquidacion2.getMontoPagoGiros();
//			longitud_M = longitud_M.toString().length();
//			if (longitud_M==4)
//				longitud_M += +1;
//			else if (longitud_M==5)
//				longitud_M += +2;
//			linea=tabular(3)+"PAGO DE GIROS"+tabular(35-longitud_C)+liquidacion2.getCantidadPagoGiros()+tabular(19-longitud_M)+Util.toNumberFormat(liquidacion2.getMontoPagoGiros(),2);
//			bw.write(linea+NEWLINE);
			
			//---> linea 31: Devoliciï¿½n al 80%
			longitud_C=liquidacion2.getCantidadDevolucion().toString().length();
			longitud_M=(int) liquidacion2.getMontoDevolucion();
			longitud_M = longitud_M.toString().length();
			if (longitud_M==4)
				longitud_M += +1;
			else if (longitud_M==5)
				longitud_M += +2;
			linea=tabular(3)+"DEVOLUCIONES"+tabular(36-longitud_C)+liquidacion2.getCantidadDevolucion()+tabular(19-longitud_M)+Util.toNumberFormat(liquidacion2.getMontoDevolucion(),2);
			bw.write(linea+NEWLINE);
			
//			//---> linea 31: Devoliciï¿½n al 100%
//			longitud_C=liquidacion2.getCantidadDevolucion100().toString().length();
//			longitud_M=(int) liquidacion2.getMontoDevolucion100();
//			longitud_M = longitud_M.toString().length();
//			if (longitud_M==4)
//				longitud_M += +1;
//			else if (longitud_M==5)
//				longitud_M += +2;
//			linea=tabular(3)+"DEVOLUCION AL 100%"+tabular(30-longitud_C)+liquidacion2.getCantidadDevolucion100()+tabular(19-longitud_M)+Util.toNumberFormat(liquidacion2.getMontoDevolucion100(),2);
//			bw.write(linea+NEWLINE);
			
			//---> linea 32: Venta x cortesia
			longitud_C=liquidacion2.getCantidadcortesia().toString().length();
			longitud_M=(int) liquidacion2.getMontoCortesia();
			longitud_M = longitud_M.toString().length();
			if (longitud_M==4)
				longitud_M += +1;
			else if (longitud_M==5)
				longitud_M += +2;
			linea=tabular(3)+"VENTA X CORTESIA"+tabular(32-longitud_C)+liquidacion2.getCantidadcortesia()+tabular(19-longitud_M)+Util.toNumberFormat(liquidacion2.getMontoCortesia(),2);
			bw.write(linea+NEWLINE);
			
			//---> linea 33: Venta a credito
			longitud_C=liquidacion2.getCantidadCreditos().toString().length();
			longitud_M=(int) liquidacion2.getMontoCreditos();
			longitud_M = longitud_M.toString().length();
			if (longitud_M==4)
				longitud_M += +1;
			else if (longitud_M==5)
				longitud_M += +2;
			linea=tabular(3)+"VENTA A CREDITO"+tabular(33-longitud_C)+liquidacion2.getCantidadCreditos()+tabular(19-longitud_M)+Util.toNumberFormat(liquidacion2.getMontoCreditos(),2);
			bw.write(linea+NEWLINE);
			
			if(liquidacion2.getCantidadCreditosDolares()>0) {
				//---> linea 33: Venta a credito
				longitud_C=liquidacion2.getCantidadCreditosDolares().toString().length();
				longitud_M=(int) liquidacion2.getMontoCreditosDolares();
				longitud_M = longitud_M.toString().length();
				if (longitud_M==4)
					longitud_M += +1;
				else if (longitud_M==5)
					longitud_M += +2;
				linea=tabular(3)+"VENTA A CREDITO US$"+tabular(29-longitud_C)+liquidacion2.getCantidadCreditosDolares()+tabular(19-longitud_M)+Util.toNumberFormat(liquidacion2.getMontoCreditosDolares(),2);
				bw.write(linea+NEWLINE);
			}
			
			//---> linea 34: Venta Prepagada
			longitud_C=liquidacion2.getCantidadPrepagado().toString().length();
			longitud_M=(int) liquidacion2.getMontoPrepagado();
			longitud_M = longitud_M.toString().length();
			if (longitud_M==4)
				longitud_M += +1;
			else if (longitud_M==5)
				longitud_M += +2;
			linea=tabular(3)+"VENTA PREPAGADA"+tabular(33-longitud_C)+liquidacion2.getCantidadPrepagado()+tabular(19-longitud_M)+Util.toNumberFormat(liquidacion2.getMontoPrepagado(),2);
			bw.write(linea+NEWLINE);
			
			//---> linea 35: Venta contarjeta de credito
			Integer cantidadVentaTarjeta=liquidacion2.getCantidadTarjetaVisa()+liquidacion2.getCantidadTarjetaMasterCard()
										+liquidacion2.getCantidadTarjetaMasterCardRC()+liquidacion2.getCantidadTarjetaVisaRC()
										+cantidadVentasPoolVisa+cantidadVentasPoolMastercard
										+liquidacion2.getCantidadGastosAdminTarjetaMastercard()+liquidacion2.getCantidadGastosAdminTarjetaVisa();
			double montoVentaTarjeta=liquidacion2.getMontoTarjetaVisa()+liquidacion2.getMontoTarjetaMasterCard()
									 +liquidacion2.getMontoTarjetaMasterCardRC()+liquidacion2.getMontoTarjetaVisaRC()
									 +montoTotalVentasPoolVisa+montoTotalVentasPoolMastercard+
									 +liquidacion2.getMontoGastosAdminTarjetaMastercard()+liquidacion2.getMontoGastosAdminTarjetaVisa();
			
			longitud_C=cantidadVentaTarjeta.toString().length();
			longitud_M=(int) montoVentaTarjeta;
			longitud_M = longitud_M.toString().length();
			if (longitud_M==4)
				longitud_M += +1;
			else if (longitud_M==5)
				longitud_M += +2;
			
			linea=tabular(3)+"VENTAS Y GASTOS ADMIN. CON TARJETA"+tabular(14-longitud_C)+cantidadVentaTarjeta+tabular(19-longitud_M)+Util.toNumberFormat(montoVentaTarjeta, 2);
			bw.write(linea+NEWLINE);
			
			//---> linea 36: Venta PCE
			longitud_C=liquidacion2.getCantidadPCE().toString().length();
			longitud_M=(int) liquidacion2.getMontoPCE();
			longitud_M = longitud_M.toString().length();
			if (longitud_M==4)
				longitud_M += +1;
			else if (longitud_M==5)
				longitud_M += +2;
			linea=tabular(3)+"VENTA PCE"+tabular(39-longitud_C)+liquidacion2.getCantidadPCE()+tabular(19-longitud_M)+Util.toNumberFormat(liquidacion2.getMontoPCE(),2);
			bw.write(linea+NEWLINE);
			
			
			
			//-->Notas de credito
			Integer cantidadNotasCredito=liquidacion2.getCantidadNotasCredito();
			double montoNotasCredito=liquidacion2.getMontoNotasCredito();
			
			longitud_C=cantidadNotasCredito.toString().length();
			longitud_M=(int) montoNotasCredito;
			longitud_M = longitud_M.toString().length();
			if (longitud_M==4)
				longitud_M += +1;
			else if (longitud_M==5)
				longitud_M += +2;
			
			linea=tabular(3)+"NOTAS DE CREDITO"+tabular(32-longitud_C)+cantidadNotasCredito+tabular(19-longitud_M)+Util.toNumberFormat(montoNotasCredito, 2);
			bw.write(linea+NEWLINE);
			
			//---> Venta de Seguros - Pasajero Frecuente
//			longitud_C=String.valueOf(cantidadVentasPaxfre).length();
//			longitud_M=(int) montoTotalVentasSeguroPaxfre;
//			longitud_M = longitud_M.toString().length();
//			if (longitud_M==4)
//				longitud_M += +1;
//			else if (longitud_M==5)
//				longitud_M += +2;
//			linea=tabular(3)+"VENTA DE SEGUROS PASAJERO FRECUENTE"+tabular(13-longitud_C)+cantidadVentasPaxfre+tabular(19-longitud_M)+Util.toNumberFormat(montoTotalVentasSeguroPaxfre, 2);
//			bw.write(linea+NEWLINE);
			
			
			//---> linea 36: LINEA
			linea=tabular(45)+"-------"+tabular(10)+"------------";
			bw.write(linea+NEWLINE);
			
			//---> linea 37: Total Egresos
			Integer CantidadEgresos=/*liquidacion2.getCantidadGastoVarios()+liquidacion2.getCantidadPeajes()+liquidacion2.getCantidadPagoGiros()+
								    liquidacion2.getCantidadGastoConDocumento()+*/
									liquidacion2.getCantidadDevolucion()+liquidacion2.getCantidadcortesia()+
									liquidacion2.getCantidadCreditos()+liquidacion2.getCantidadPrepagado()+cantidadVentaTarjeta+cantidadNotasCredito+cantidadGasto+liquidacion2.getCantidadPCE();
//									+ cantidadVentasPaxfre;
			double Totalegresos=/*liquidacion2.getMontoGastoVarios()+liquidacion2.getMontoPeajes()+liquidacion2.getMontoPagoGiros()+
								liquidacion2.getMontoGastoConDocumento()+*/
								liquidacion2.getMontoDevolucion()+liquidacion2.getMontoCortesia()+
								liquidacion2.getMontoCreditos()+liquidacion2.getMontoPrepagado()+montoVentaTarjeta+montoNotasCredito+importeGasto+liquidacion2.getMontoPCE();
//								+ montoTotalVentasSeguroPaxfre;
			
			longitud_C=CantidadEgresos.toString().length();
			longitud_M=(int) Totalegresos;
			longitud_M = longitud_M.toString().length();
			if (longitud_M==4)
				longitud_M += +1;
			else if (longitud_M==5)
				longitud_M += +2;
			else if (longitud_M==6)
				longitud_M += +3;
			
			linea=tabular(3)+"TOTAL EGRESOS"+tabular(35-longitud_C)+CantidadEgresos+tabular(19-longitud_M)+Util.toNumberFormat(Totalegresos,2);
			bw.write(linea+NEWLINE);
			
			Integer cantidadEgresosDolares = 0;
			double totalEgresosDolares = 0.0;
			if(liquidacion2.getCantidadCreditosDolares()>0) {
				//---> linea 37: Total Egresos
				cantidadEgresosDolares = liquidacion2.getCantidadCreditosDolares();
				totalEgresosDolares = liquidacion2.getMontoCreditosDolares();
				
				longitud_C=cantidadEgresosDolares.toString().length();
				longitud_M=(int) totalEgresosDolares;
				longitud_M = longitud_M.toString().length();
				if (longitud_M==4)
					longitud_M += +1;
				else if (longitud_M==5)
					longitud_M += +2;
				else if (longitud_M==6)
					longitud_M += +3;
				
				linea=tabular(3)+"TOTAL EGRESOS US$"+tabular(31-longitud_C)+cantidadEgresosDolares+tabular(19-longitud_M)+Util.toNumberFormat(totalEgresosDolares,2);
				bw.write(linea+NEWLINE);
			}
			
			bw.write(NEWLINE);
			
			//---> Operaciones coplementarias
			linea=tabular(3)+"OPERACIONES COMPLEMENTARIAS"+tabular(15)+"CANTIDAD"+tabular(13)+"IMPORTE";			
			bw.write(linea+NEWLINE);
			linea=tabular(3)+interline;
			bw.write(linea+NEWLINE);
			/*Devoluciones con tarjeta de credito*/
			longitud_C=liquidacion2.getCantidadDevolucionTarjeta().toString().length();
			longitud_M=(int) liquidacion2.getMontoDevolucionTarjeta();
			longitud_M = longitud_M.toString().length();
			if (longitud_M==4)
				longitud_M += +1;
			else if (longitud_M==5)
				longitud_M += +2;
			linea=tabular(3)+"DEVOLUCIONES CON TARJETA"+tabular(24-longitud_C)+liquidacion2.getCantidadDevolucionTarjeta()+tabular(19-longitud_M)+Util.toNumberFormat(liquidacion2.getMontoDevolucionTarjeta(),2);
			bw.write(linea+NEWLINE);
			
			//---> linea 38: salto de linea
			bw.write(NEWLINE);
			
			//---> linea 39: Total a depositar
//			double totalADepositar=(totalVentaPasajes+liquidacion2.getMontoRC())-Totalegresos;
			double totalADepositar=(totalVentaPasajes+montoRcTotal)-Totalegresos;
			longitud_M = (int) totalADepositar;
			longitud_M = longitud_M.toString().length();
			if (longitud_M==4)
				longitud_M += +1;
			else if (longitud_M==5)
				longitud_M += +2;
			else if (longitud_M==6)
				longitud_M += +3;
			linea=tabular(3)+"TOTAL A DEPOSITAR"+tabular(50-longitud_M)+Util.toNumberFormat(totalADepositar,2);
			bw.write(linea+NEWLINE);
			
			double totalADepositarDolares = 0.0;
			if(liquidacion2.getCantidadContadoDolares()>0 || liquidacion2.getCantidadCreditosDolares()>0) {
				//---> linea 39: Total a depositar
				totalADepositarDolares=totalVentaPasajesDolares-totalEgresosDolares;
				longitud_M = (int) totalADepositarDolares;
				longitud_M = longitud_M.toString().length();
				if (longitud_M==4)
					longitud_M += +1;
				else if (longitud_M==5)
					longitud_M += +2;
				else if (longitud_M==6)
					longitud_M += +3;
				linea=tabular(3)+"TOTAL A DEPOSITAR US$"+tabular(46-longitud_M)+Util.toNumberFormat(totalADepositarDolares,2);
				bw.write(linea+NEWLINE);
			}
			
			if (liquidacion.getestadoLiquidacion().equals(Constantes.LIQUI_ESTA_CERRADO)){
				//---> linea 40: Total Efectivo ingresado
				longitud_M = liquidacion.getMontoIngresado().intValue();
				longitud_M = longitud_M.toString().length();
				if (longitud_M==4)
					longitud_M += +1;
				else if (longitud_M==5)
					longitud_M += +2;
				else if (longitud_M==6)
					longitud_M += +3;
				linea=tabular(3)+"TOTAL EFECTIVO INGRESADO"+tabular(43-longitud_M)+Util.toNumberFormat(liquidacion.getMontoIngresado(),2);
				bw.write(linea+NEWLINE);
				
				if(liquidacion.getMontoIngresadoDolares()>0.0) {
					//---> linea 40: Total Efectivo ingresado
					longitud_M = liquidacion.getMontoIngresadoDolares().intValue();
					longitud_M = longitud_M.toString().length();
					if (longitud_M==4)
						longitud_M += +1;
					else if (longitud_M==5)
						longitud_M += +2;
					else if (longitud_M==6)
						longitud_M += +3;
					linea=tabular(3)+"TOTAL EFECTIVO INGRESADO US$"+tabular(39-longitud_M)+Util.toNumberFormat(liquidacion.getMontoIngresadoDolares(),2);
					bw.write(linea+NEWLINE);
				}
				
				//---> linea 41: Diferencia
				double diferencia = (liquidacion.getMontoIngresado()-totalADepositar);
				longitud_M = new Double(diferencia).intValue();
				longitud_M = longitud_M.toString().length();// Util.toNumberFormat(diferencia,2).length();
//				longitud_M = longitud_M.toString().length();
				if (longitud_M==4)
					longitud_M += +1;
				else if (longitud_M==5)
					longitud_M += +2;
				else if (longitud_M==6)
					longitud_M += +3;
				linea=tabular(3)+"DIFERENCIA"+tabular(57-longitud_M)+Util.toNumberFormat(diferencia,2);
				bw.write(linea+NEWLINE);
				
				if(liquidacion.getMontoIngresadoDolares()>0.0) {
					//---> linea 41: Diferencia
					double diferenciaDolares = (liquidacion.getMontoIngresadoDolares()-totalADepositarDolares);
					longitud_M = new Double(diferenciaDolares).intValue();
					longitud_M = longitud_M.toString().length();// Util.toNumberFormat(diferencia,2).length();
//					longitud_M = longitud_M.toString().length();
					if (longitud_M==4)
						longitud_M += +1;
					else if (longitud_M==5)
						longitud_M += +2;
					else if (longitud_M==6)
						longitud_M += +3;
					linea=tabular(3)+"DIFERENCIA US$"+tabular(53-longitud_M)+Util.toNumberFormat(diferenciaDolares,2);
					bw.write(linea+NEWLINE);
				}
			}
						
			//---> linea 42: salto de linea
			bw.write(NEWLINE);
			
			//---> linea 43: Usuario Cierre
			linea=tabular(3)+"USUARIO CIERRE      :"+tabular(2)+usuarioCierre.getNombre()+" "+usuarioCierre.getApellidoPaterno();
			bw.write(linea+NEWLINE);
			
			//---> linea 44: Fecha y hora de cierre
			String fechaHoraImpresion = Constantes.FORMAT_LONG.format(liquidacion.getFechaModificacion());
			linea=tabular(3)+"FECHA Y HORA CIERRE :"+tabular(2)+fechaHoraImpresion;
			bw.write(linea+NEWLINE);
			
			bw.write(NEWLINE);
			bw.write(NEWLINE);
	
			bw.close();
			
//			return totalADepositar;
		}catch(IOException ioex){
			ioex.printStackTrace();
//			return .00;
		}catch(Exception ex){
			ex.printStackTrace();
//			return .00;
		}
		return file;
	}
	
	/**
	 * Calcula el total de a depositar
	 * @param liquidacion
	 * @return
	 */
	public double saldoDepositarLiquidacion(Liquidacion liquidacion){
		
		double totalVentaPasajes=liquidacion.getMontoContado()+liquidacion.getMontoTarjetaVisa()+liquidacion.getMontoTarjetaMasterCard()+
				liquidacion.getMontoCortesia()+liquidacion.getMontoPrepagado()+liquidacion.getMontoCreditos();
		
		double montoVentaTarjeta=liquidacion.getMontoTarjetaVisa()+liquidacion.getMontoTarjetaMasterCard();
		
		double Totalegresos=liquidacion.getMontoGastoVarios()+liquidacion.getMontoPeajes()+liquidacion.getMontoPagoGiros()+
				liquidacion.getMontoDevolucion()+liquidacion.getMontoCortesia()+
				liquidacion.getMontoCreditos()+liquidacion.getMontoPrepagado()+montoVentaTarjeta;
		
		double totalADepositar=(totalVentaPasajes+liquidacion.getMontoRC())-Totalegresos;
		
		
		return totalADepositar;
	}
	 
	
	
	/**
	 * Crea el detalle para el despacho del Bus
	 * @param itinerario	: Class itinerario
	 * @param list			: lista que contiene los pasajeros
	 * @param manifiesto	: Class WndManifiesto
	 * @param bw			:
	 * @throws Exception
	 */
	private static final void creaDetalleDespacho(Integer numeroAsientos, List<VentaPasaje> list, WndManifiesto manifiesto,BufferedWriter bw, Integer piso) throws Exception{
		String linea="";
		String linea2="";
		String linea3="";
		String linea4="";
		
		String pasajero="";
		String documento="";
		String embarque="";
		String boleto="";
		String edad="";
		String ruta="";
				
		Integer lBase=24; 
		Integer longitud_C=0;
		
		Servicio servicio = null;
		List<MapaBus> lstMapaBus=ServiceLocator.getMapaBusManager().buscarMapaBus(list.get(0).getItinerario().getServicio().getId(), Constantes.VALUE_ACTIVO);
		
		Map<Coordenada, MapaBus> mapCoordenadas = new HashMap<Coordenada, MapaBus>();
		for(MapaBus mapaBus : lstMapaBus){
			Coordenada coordenada = new Coordenada(mapaBus.getNumeroFila(), mapaBus.getNumeroColumna(), mapaBus.getNumeroPiso());
			mapCoordenadas.put(coordenada, mapaBus);
		}
			
		if(lstMapaBus.size()>0)
			servicio = lstMapaBus.get(0).getServicio();
		
//		int nPisos = servicio.getNumeroPisos();
		int nFilas = servicio.getNumeroFilasPiso1();
		int nColumnas = servicio.getNumeroColumnasPiso1();
		String numeroAsiento = "";
		
//		for(int i=0; i<nPisos; i++){
			ArrayList<Coordenada> lisCoordenadas= new ArrayList<Coordenada>();
			if(piso==1){
				nFilas = servicio.getNumeroFilasPiso2();
				nColumnas = servicio.getNumeroColumnasPiso2();
				bw.write(NEWLINE);

				linea="PISO 2.";
				bw.write(linea+NEWLINE);
			}
			
			
			linea="+-----------------------------------------------------------+ +-----------------------------------------------------------+";
			bw.write(linea+NEWLINE);
			
			
			numeroAsiento = "";
			for(int j=0; j<nFilas; j++){
				
				for(int k=0; k<nColumnas; k++){
//					String coordenadaActual = j+"-"+k+"-"+i;
					String coordenadaActual = j+"-"+k+"-"+piso;
					
					for(Coordenada coordenada : mapCoordenadas.keySet()){
						if(coordenada.toString().equals(coordenadaActual) && piso.equals(piso)){
							MapaBus objetoBus = mapCoordenadas.get(coordenada);			
							
							lisCoordenadas.add(coordenada);
							
							Asiento asiento = new Asiento();
							asiento.setNumeroAsiento(objetoBus.getNumeroAsiento());
							
							if(!(asiento.getNumeroAsiento()==null) ){
								numeroAsiento=asiento.getNumeroAsiento().toString();
								if(numeroAsiento.toString().length()==1)
									numeroAsiento="0"+numeroAsiento;
								
								Integer longitud_D=11;
								Integer longitud_E=10;
								Integer longitud_Bole=13;
								Integer longitud_Ed=3;
								Integer longitud_Ruta=21;
																
								if(coordenada.getColumna()==0){
									linea="| "+(numeroAsiento)+": ";							
									pasajero=manifiesto.getPasajero(list, (new Integer(numeroAsiento)),lBase, piso);
									linea+=tabular(0-lBase)+pasajero;
									longitud_C=pasajero.length();
									linea+=tabular(lBase-longitud_C);
									/**Documento*/
									linea2="| #D: ";
									documento= manifiesto.getDocumentoPax(list, (new Integer(numeroAsiento)),piso);
									longitud_C=documento.length();
									linea2+=documento+tabular(longitud_D-longitud_C);
									/**Embarque*/
									linea2+="#E:";
									embarque=manifiesto.getEmbarque(list, (new Integer(numeroAsiento)),longitud_E,piso);
									longitud_C=embarque.length();
									linea2+=embarque+tabular(longitud_E-longitud_C)+"| ";
									/**Boleto*/
									linea3="| #Bole: ";
									boleto=manifiesto.getBoleto(list, (new Integer(numeroAsiento)),piso);
									longitud_C=boleto.length();
									linea3+=boleto+tabular(longitud_Bole-longitud_C);
									/**Edad*/
									linea3+="#Ed: ";
									edad=manifiesto.getEdad(list, (new Integer(numeroAsiento)),piso);
									longitud_C=edad.length();	
									linea3+=edad+tabular(longitud_Ed-longitud_C)+"| ";
									/**Ruta*/
									linea4="| #Ruta: ";
									ruta=manifiesto.getRuta(list,(new Integer(numeroAsiento)),piso,longitud_Ruta);
									longitud_C=ruta.length();
									linea4+=ruta+tabular(longitud_Ruta-longitud_C)+"| ";
									
								}else if(coordenada.getColumna()==1){	
									linea+="| "+(numeroAsiento)+": ";							
									pasajero=manifiesto.getPasajero(list, (new Integer(numeroAsiento)),lBase, piso);
									linea+=tabular(0-lBase)+pasajero;
									longitud_C=pasajero.length();
									linea+=tabular(lBase-longitud_C)+"| ";
									/**Documento*/
									linea2+="#D: ";
									documento= manifiesto.getDocumentoPax(list, (new Integer(numeroAsiento)),piso);
									longitud_C=documento.length();
									linea2+=documento+tabular(longitud_D-longitud_C);
									/**Embarque*/
									linea2+="#E:";
									embarque=manifiesto.getEmbarque(list, (new Integer(numeroAsiento)),longitud_E,piso);
									longitud_C=embarque.length();
									linea2+=embarque+tabular(longitud_E-longitud_C)+"| ";
									/**Boleto*/
									linea3+="#Bole: ";
									boleto=manifiesto.getBoleto(list, (new Integer(numeroAsiento)),piso);
									longitud_C=boleto.length();
									linea3+=boleto+tabular(longitud_Bole-longitud_C);
									/**Edad*/
									linea3+="#Ed: ";
									edad=manifiesto.getEdad(list, (new Integer(numeroAsiento)),piso);
									longitud_C=edad.length();	
									linea3+=edad+tabular(longitud_Ed-longitud_C)+"| ";
									/**Ruta*/
									linea4+="#Ruta: ";
									ruta=manifiesto.getRuta(list,(new Integer(numeroAsiento)),piso,longitud_Ruta);
									longitud_C=ruta.length();
									linea4+=ruta+tabular(longitud_Ruta-longitud_C)+"| ";
									
								}else if(coordenada.getColumna()==3){
									/*Valida columnas incomletas. Ejemplo Tepsa Suite*/
									boolean flag=false;
									for(Coordenada coordenada2: lisCoordenadas){
										switch (nColumnas) {
										case 5:
											if(coordenada2.getColumna()==1)
												flag=true;
											break;	
										default:
											break;
										}
									}
									if(!(flag)){
										linea+=tabular(30);
										linea2+=tabular(30);
										linea3+=tabular(30);
										linea4+=tabular(30);
									}
									
									linea+="| "+(numeroAsiento)+": ";							
									pasajero=manifiesto.getPasajero(list, (new Integer(numeroAsiento)),lBase, piso);
									linea+=tabular(0-lBase)+pasajero;
									longitud_C=pasajero.length();
									linea+=tabular(lBase-longitud_C)+"| ";
									/**Documento*/
									linea2+="| #D: ";
									documento= manifiesto.getDocumentoPax(list, (new Integer(numeroAsiento)),piso);
									longitud_C=documento.length();
									linea2+=documento+tabular(longitud_D-longitud_C);
									/**Embarque*/
									linea2+="#E:";
									embarque=manifiesto.getEmbarque(list, (new Integer(numeroAsiento)),longitud_E,piso);
									longitud_C=embarque.length();
									linea2+=embarque+tabular(longitud_E-longitud_C)+"| ";
									/**Boleto*/
									linea3+="| #Bole: ";
									boleto=manifiesto.getBoleto(list, (new Integer(numeroAsiento)),piso);
									longitud_C=boleto.length();
									linea3+=boleto+tabular(longitud_Bole-longitud_C);
									/**Edad*/
									linea3+="#Ed: ";
									edad=manifiesto.getEdad(list, (new Integer(numeroAsiento)),piso);
									longitud_C=edad.length();	
									linea3+=edad+tabular(longitud_Ed-longitud_C)+"| ";
									/**Ruta*/
									linea4+="| #Ruta: ";
									ruta=manifiesto.getRuta(list,(new Integer(numeroAsiento)),piso,longitud_Ruta);
									longitud_C=ruta.length();
									linea4+=ruta+tabular(longitud_Ruta-longitud_C)+"| ";
								}else if(coordenada.getColumna()==4){
									/*Valida columnas incomletas. Ejemplo Tepsa Suite*/
									boolean flag=false;
									for(Coordenada coordenada2: lisCoordenadas){
										if(coordenada2.getColumna()==3)
											flag=true;
									}
									if(!(flag)){
										linea+=tabular(30)+"| ";
										linea2+=tabular(30)+"| ";
										linea3+=tabular(30)+"| ";
										linea4+=tabular(30)+"| ";
									}
									
									linea+=(numeroAsiento)+": ";							
									pasajero=manifiesto.getPasajero(list, (new Integer(numeroAsiento)),lBase, piso);
									linea+=tabular(0-lBase)+pasajero;
									longitud_C=pasajero.length();
									linea+=tabular(lBase-longitud_C)+"| ";
									/**Documento*/
									linea2+="#D: ";
									documento= manifiesto.getDocumentoPax(list, (new Integer(numeroAsiento)),piso);
									longitud_C=documento.length();
									linea2+=documento+tabular(longitud_D-longitud_C);
									/**Embarque*/
									linea2+="#E:";
									embarque=manifiesto.getEmbarque(list, (new Integer(numeroAsiento)),longitud_E,piso);
									longitud_C=embarque.length();
									linea2+=embarque+tabular(longitud_E-longitud_C)+"| ";
									/**Boleto*/
									linea3+="#Bole: ";
									boleto=manifiesto.getBoleto(list, (new Integer(numeroAsiento)),piso);
									longitud_C=boleto.length();
									linea3+=boleto+tabular(longitud_Bole-longitud_C);
									/**Edad*/
									linea3+="#Ed: ";
									edad=manifiesto.getEdad(list, (new Integer(numeroAsiento)),piso);
									longitud_C=edad.length();	
									linea3+=edad+tabular(longitud_Ed-longitud_C)+"| ";
									/**Ruta*/
									linea4+="#Ruta: ";
									ruta=manifiesto.getRuta(list,(new Integer(numeroAsiento)),piso,longitud_Ruta);
									longitud_C=ruta.length();
									linea4+=ruta+tabular(longitud_Ruta-longitud_C)+"| ";
								}		
								
							}
						}
						
					}
					
				}
				
				String s=linea.substring(0,1).toString();
				if(!(s.equals("+"))){
					bw.write(linea+NEWLINE);
					bw.write(linea2+NEWLINE);
					bw.write(linea3+NEWLINE);
					bw.write(linea4+NEWLINE);

					linea="+-----------------------------------------------------------+";
					linea+=tabular(1)+"+-----------------------------------------------------------+";
					bw.write(linea+NEWLINE);
				}
			
//			}		
		}
		
	}
	/**
	 * 
	 * @param itinerario
	 * @return
	 */
	public static final File creaCarpetaDespacho(Itinerario itinerario, Agencia agencia){
		WndManifiesto manifiesto = new WndManifiesto();
//		Integer lBase=24; 
		Integer longitud_C=0;
		String fichero = Constantes.DIRECTORY_DESPACHOS +Constantes.CLAVE_PAHT +"CARDES"+itinerario.getId()+"-"+itinerario.getAgenciaPartida().getId()+".txt";
		File file = new File(fichero);
		try{
//			BufferedWriter bw = new BufferedWriter(new FileWriter(file));
			BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file),"UTF-8"));
			String linea = "";
						
			//---> line 1:	TITULO DEL REPORTE
			String title="CARPETA DE DESPACHO";
			linea = tabular(52)+title;
			bw.write(linea + NEWLINE);
			
			/* QUITADO POR LA IMPLEMENTACION DE TRANSMAR	*/
//			//---> line 2: salto de linea
//			bw.write(linea+NEWLINE);
			
			/*	AGREGADO A SOLICITUD DE TRANSMAR*/
			//---> line 2: salto de linea
			linea = Constantes.empresa;
			String ruc = Constantes.ruc;
			linea += tabular(81)+"RUC : "+ruc;
			bw.write(linea+NEWLINE);
			
			/*	AGREGADO A SOLICITUD DE TRANSMAR*/
			//---> line 3:(Agencia - Nro.Itinerario - Bus)
			String agenciA=agencia.getDenominacion();
			longitud_C=agenciA.length();
			String bus="";
			if(!(itinerario.getBus()==null))
				bus=itinerario.getBus().getCodigo();
			linea="Agencia   : "+agenciA+tabular(44-longitud_C);
			longitud_C=itinerario.getId().toString().length();
			linea+="Nro.Itin.: "+itinerario.getId()+tabular(34-longitud_C);
			linea+="Bus : "+bus;
			bw.write(linea+NEWLINE);			
			
			/* QUITADO POR LA IMPLEMENTACION DE TRANSMAR	*/
//			//---> line 3: Razon social, servicio y ruc
//			String servicio=itinerario.getServicio().getDenominacion();
//			linea = Constantes.empresa+tabular(24)+"Servicio: ";
//			String servicio_o=servicio.toUpperCase().toString().substring(0,1)+ servicio.toLowerCase().substring(1, servicio.toString().length());
//			linea +=servicio_o;
//			longitud_C= servicio_o.toString().length();
//			String ruc = Constantes.ruc;
//			linea +=tabular(45-longitud_C)+"Ruc: "+ruc;
//			bw.write(linea+NEWLINE);
//			
//			//---> line 4: Salto de linea
//			bw.write(NEWLINE);
			
			/*	AGREGADO A SOLICITUD DE TRANSMAR*/
			//---> line 4:(Origen - Destino - Placa)
			String origen=itinerario.getRuta().getLocalidadOrigen().getDenominacion();
			String destino=itinerario.getRuta().getLocalidadDestino().getDenominacion();
			String placa="";
			if(!(itinerario.getBus()==null))
				placa=itinerario.getBus().getNumeroPlaca();
			longitud_C=origen.length();
			linea="Origen    : "+origen+tabular(44-longitud_C); longitud_C=destino.length();
			linea+="Destino  : "+destino+tabular(32-longitud_C);
			linea+="Placa : "+placa;
			bw.write(linea+NEWLINE);
			
			/* QUITADO POR LA IMPLEMENTACION DE TRANSMAR	*/
//			//---> line 5: 	Origen, Destino, salida, hora:
//			String origen=itinerario.getRuta().getOrigen();
//			origen=origen.toUpperCase().toString().substring(0,1)+origen.toLowerCase().substring(1,origen.toString().length());
//			String destino=itinerario.getRuta().getDestino();
//			destino=destino.toUpperCase().toString().substring(0,1)+destino.toLowerCase().substring(1,destino.toString().length());
//			linea = "Origen: "+origen;
//			longitud_C= destino.toString().length();
//			linea+=tabular(29-longitud_C)+"Destino: "+destino;
//			String fechaSalida=Constantes.FORMAT_DATE.format(itinerario.getFechaPartida());
//			longitud_C=destino.toString().length();
//			linea +=tabular(28-longitud_C)+"Salida :"+fechaSalida;
//			linea +=tabular(8)+"Hora :"+itinerario.getHoraPartida(); 
//			bw.write(linea+NEWLINE);
			
			/*	AGREGADO A SOLICITUD DE TRANSMAR*/
			//---> linea 5:(Chofer1 -  Licencia - Nro. Tarj. Habilit.)
			String Chofer=""; String licencia=""; String TarjHabilit="";
			if(!(itinerario.getBus()==null)){
				Personal piloto = new Personal(); 
				piloto=itinerario.getBus().getProgramacionServicio().getPiloto();
				Chofer=piloto.toString(); //.getApellidoPaterno()+" "+piloto.getApellidoMaterno()+", "+piloto.getNombre();
				if(piloto.getLicencia() != null)
					licencia=piloto.getLicencia();
				if(!(itinerario.getBus().getDocumentoBus()==null))
					TarjHabilit=itinerario.getBus().getDocumentoBus().getNumeroDocumento();
			}
			longitud_C=Chofer.length();		
			linea="Chofer 1  : "+Chofer+tabular(44-longitud_C); longitud_C=licencia.length();
			linea+="Licencia : "+licencia+tabular(19-longitud_C);
			linea+="Nro. Tarj. Habilit.: "+TarjHabilit;
			bw.write(linea+NEWLINE);
			
//			//---> line 6: Salto de linea:
//			bw.write(NEWLINE);
			
			//---> linea 8:(Chofer2 -  Licencia - Marca)
			String marca="";
			if(!(itinerario.getBus()==null)){
				Personal copiloto = new Personal(); 
				copiloto=itinerario.getBus().getProgramacionServicio().getCopiloto();
				Chofer=copiloto.toString(); //.getApellidoPaterno()+" "+copiloto.getApellidoMaterno()+", "+copiloto.getNombre();
				if(copiloto.getLicencia() != null)
					licencia=copiloto.getLicencia();
				if(!(itinerario.getBus().getGrupoMantenimiento().getDenominacion()==null))
					marca=itinerario.getBus().getGrupoMantenimiento().getDenominacion();
			}
			longitud_C=Chofer.length();
			linea="Chofer 2  : "+Chofer+tabular(44-longitud_C); longitud_C=licencia.length();
			linea+="Licencia : "+licencia+tabular(32-longitud_C);
			linea+="Marca : "+marca;
			bw.write(linea+NEWLINE);
			//---> linea 8:(Chofer3 -  Licencia )
			Chofer="";licencia="";String servicio="";
			if(!(itinerario.getBus()==null) && itinerario.getBus().getProgramacionServicio().getCopilotoAuxiliar()!=null){
				Personal copilotoAux = new Personal(); 
				copilotoAux=itinerario.getBus().getProgramacionServicio().getCopilotoAuxiliar();
				Chofer=copilotoAux.toString();
				if(copilotoAux.getLicencia() != null)
					licencia=copilotoAux.getLicencia();
			}
			servicio=itinerario.getServicio().getDenominacion();
			longitud_C=Chofer.length();
			linea="Chofer 3  : "+Chofer+tabular(44-longitud_C); longitud_C=licencia.length();
			linea+="Licencia : "+licencia+tabular(29-longitud_C);
			linea+="Servicio : "+servicio;			
			bw.write(linea+NEWLINE);
			
			//---> linea 9:(Terramoza -  Salida - Servicio)
			String tripulante=""; String salida=""; String dniTerramoza=""; 
			//Se agrego la condicion en el if porque la tripulante ya no es obligatorio, por MAOE 27/06/2021
			if(!(itinerario.getBus()==null) && itinerario.getBus().getProgramacionServicio().getTripulante()!=null ){
				Personal terramoza = new Personal(); 
				terramoza=itinerario.getBus().getProgramacionServicio().getTripulante();
				tripulante=terramoza.toString();//.getApellidoPaterno()+" "+terramoza.getApellidoMaterno()+", "+terramoza.getNombre();
				dniTerramoza=terramoza.getNroDocumento()!=null?terramoza.getNroDocumento():"";
			}
			salida=Constantes.FORMAT_DATE.format(itinerario.getFechaPartida())+" "+itinerario.getHoraPartida();
			
			longitud_C=tripulante.length();
			linea="Terramoza : "+tripulante+tabular(49-longitud_C); longitud_C=dniTerramoza.length();
			linea+="DNI : "+dniTerramoza+tabular(31-longitud_C);
			linea+="Salida : "+salida;			
			bw.write(linea+NEWLINE);
			
			if(itinerario.getServicio().getNumeroPisos()==2){
				linea="PISO 1.";
				bw.write(linea+NEWLINE);
			}
			
			List<VentaPasaje> list=ServiceLocator.getManifiestoManager().consultaPasajeros(itinerario.getId(),itinerario.getAgenciaPartida().getId());
//			Messagebox.show(itinerario.getAgenciaPartida().getId().toString());
			Integer piso=0; //Piso por defecto.
			Integer numeroAsientos=0;
			if(list.size()>0){
				list.get(0).setItinerario(itinerario);
				//---> line 7: Detalle
				/*Crea detalle para el Despacho del Bus*/
//				Integer piso=0; //Piso por defecto.
//				Integer numeroAsientos=0;
				for(int xd=0; xd < itinerario.getServicio().getNumeroPisos(); xd++){
					piso=xd;
					if(piso.equals(Constantes.PISO_UNO)){
						creaDetalleDespacho(itinerario.getServicio().getNumeroAsientosPiso1(), list, manifiesto, bw, piso);
						numeroAsientos=itinerario.getServicio().getNumeroAsientosPiso1();
					}else if(piso.equals(Constantes.PISO_DOS)){
						creaDetalleDespacho(itinerario.getServicio().getNumeroAsientosPiso2(), list, manifiesto, bw, piso);
						numeroAsientos+=itinerario.getServicio().getNumeroAsientosPiso2();
					}
				}
			}
			
			bw.write(NEWLINE);
//			String numeroAsientos=itinerario.getServicio().getNumeroAsientosPiso1().toString();
			longitud_C=numeroAsientos.toString().length();
			linea="Total Asientos : "+numeroAsientos;
			linea+=tabular(18-longitud_C)+"Despachador: .......................";
			bw.write(linea+NEWLINE);
			
			bw.write(NEWLINE);
			
			Integer totalPasajeros=list.size();
			longitud_C=totalPasajeros.toString().length();
			linea="Total Pasajeros: "+tabular(2-longitud_C)+totalPasajeros;
			linea+=tabular(17-longitud_C)+" Firma      : .......................";
			
			bw.write(linea+NEWLINE);
			
			bw.write(NEWLINE);
			bw.write(NEWLINE);
			
			bw.close();
		}catch(IOException ioex){
			ioex.printStackTrace();
		}catch(Exception ex){
			ex.printStackTrace();
		}
		return file;
	}
		
	
	public static final File creaManifiesto_Equipajes(List<DetalleEquipaje> listDetalleEquipajes, Itinerario itinerario)throws Exception{
		Equipaje equipaje = ServiceLocator.getEquipajeManager().buscarPorId(listDetalleEquipajes.get(0).getEquipaje().getId());
		String numeroManifiesto = (Util.autocompleNumberBoleto("000-"+equipaje.getId().toString())).split("-")[1];			
		Integer longitud_C=0;
		String fichero = Constantes.DIRECTORY_MANIFIESTOS +Constantes.CLAVE_PAHT + "MANIFIESTO_EQUIPAJE_"+numeroManifiesto+".txt";
					
		File file = new File(fichero);
		
		try {
			BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file),"UTF-8"));
			String linea = "";
			
			
			
			//---> line 1:	TITULO DEL REPORTE
			String title="";
			title=("MANIFIESTO DE EQUIPAJES");
			linea = tabular(54)+title;
			bw.write(linea + NEWLINE);
			//---> line 2:
			linea = Constantes.empresa;
			String ruc= Constantes.ruc;
			linea +=tabular(89)+"RUC: "+ruc;
			bw.write(linea+NEWLINE);
			//---> line 3:
			String ofPrincipal = Constantes.direccion_empresa;
			String centraTelf = Constantes.nro_telefono;
			linea="Of.Principal: "+ofPrincipal+tabular(10)+"Central Telf.: "+centraTelf+tabular(23);
			linea+="Nro.Manif.: "+numeroManifiesto;
			bw.write(linea+NEWLINE);
			//---> line 5:(Agencia - Nro.Itinerario - Bus)
			String agenciA=equipaje.getAgencia().getDenominacion();
			longitud_C=agenciA.length();
			String bus="";
			if(!(itinerario.getBus()==null))
				bus=itinerario.getBus().getCodigo();
			linea="Agencia   : "+agenciA+tabular(44-longitud_C);
			longitud_C=itinerario.getId().toString().length();
			linea+="Nro.Itin.: "+itinerario.getId()+tabular(38-longitud_C);
			linea+="Bus : "+bus;
			bw.write(linea+NEWLINE);
			//---> line 6:(Origen - Destino - Placa)
			String origen=itinerario.getRuta().getOrigen();
			String destino=itinerario.getRuta().getDestino();
			String placa="";
			if(!(itinerario.getBus()==null))
				placa=itinerario.getBus().getNumeroPlaca();
			longitud_C=origen.length();
			linea="Origen    : "+origen+tabular(44-longitud_C); longitud_C=destino.length();
			linea+="Destino  : "+destino+tabular(36-longitud_C);
			linea+="Placa : "+placa;
			bw.write(linea+NEWLINE);
			
			//---> linea 7:(Chofer1 -  Licencia - Nro. Tarj. Habilit.)
			String Chofer=""; String licencia=""; String TarjHabilit="";
			if(!(itinerario.getBus()==null)){
				Personal piloto = new Personal(); 
				piloto=itinerario.getBus().getProgramacionServicio().getPiloto();
				Chofer=piloto.toString(); //.getApellidoPaterno()+" "+piloto.getApellidoMaterno()+", "+piloto.getNombre();
				if(piloto.getLicencia() != null)
					licencia=piloto.getLicencia();
				if(!(itinerario.getBus().getDocumentoBus()==null))
					TarjHabilit=itinerario.getBus().getDocumentoBus().getNumeroDocumento();
				
				longitud_C=Chofer.length();		
				linea="Chofer 1  : "+Chofer+tabular(44-longitud_C); longitud_C=licencia.length();
				linea+="Licencia : "+licencia+tabular(23-longitud_C);
				linea+="Nro. Tarj. Habilit.: "+TarjHabilit;
				bw.write(linea+NEWLINE);
				//---> linea 8:(Chofer2 -  Licencia - Marca)
				String marca="";
				if(!(itinerario.getBus()==null)){
					Personal copiloto = new Personal(); 
					copiloto=itinerario.getBus().getProgramacionServicio().getCopiloto();
					Chofer=copiloto.toString(); //.getApellidoPaterno()+" "+copiloto.getApellidoMaterno()+", "+copiloto.getNombre();
					if(copiloto.getLicencia() != null)
						licencia=copiloto.getLicencia();
					if(!(itinerario.getBus().getGrupoMantenimiento().getDenominacion()==null))
						marca=itinerario.getBus().getGrupoMantenimiento().getDenominacion();
				}
				longitud_C=Chofer.length();
				linea="Chofer 2  : "+Chofer+tabular(44-longitud_C); longitud_C=licencia.length();
				linea+="Licencia : "+licencia+tabular(36-longitud_C);
				linea+="Marca : "+marca;
				bw.write(linea+NEWLINE);
				//---> linea 8:(Chofer3 -  Licencia )
				Chofer="";licencia="";String servicio="";
				if(!(itinerario.getBus()==null) && itinerario.getBus().getProgramacionServicio().getCopilotoAuxiliar()!=null){
					Personal copilotoAux = new Personal(); 
					copilotoAux=itinerario.getBus().getProgramacionServicio().getCopilotoAuxiliar();
					Chofer=copilotoAux.toString();
					if(copilotoAux.getLicencia() != null)
						licencia=copilotoAux.getLicencia();
				}
				servicio=itinerario.getServicio().getDenominacion();
				longitud_C=Chofer.length();
				linea="Chofer 3  : "+Chofer+tabular(44-longitud_C); longitud_C=licencia.length();
				linea+="Licencia : "+licencia+tabular(33-longitud_C);
				linea+="Servicio : "+servicio;
				
				bw.write(linea+NEWLINE);
				//---> linea 9:(Terramoza -  Salida - Servicio)
				String tripulante=""; String salida=""; String dniTerramoza=""; 
				//Se agrego la condicion en el if porque la tripulante ya no es obligatorio, por MAOE 27/06/2021
				if(!(itinerario.getBus()==null) && itinerario.getBus().getProgramacionServicio().getTripulante()!=null ){
					Personal terramoza = new Personal(); 
					terramoza=itinerario.getBus().getProgramacionServicio().getTripulante();
					tripulante=terramoza.toString();//.getApellidoPaterno()+" "+terramoza.getApellidoMaterno()+", "+terramoza.getNombre();
					dniTerramoza=terramoza.getNroDocumento()!=null?terramoza.getNroDocumento():"";
				}
				salida=Constantes.FORMAT_DATE.format(itinerario.getFechaPartida())+" "+itinerario.getHoraPartida();
				
				longitud_C=tripulante.length();
				linea="Terramoza : "+tripulante+tabular(49-longitud_C); longitud_C=dniTerramoza.length();
				linea+="DNI : "+dniTerramoza+tabular(35-longitud_C);
				linea+="Salida : "+salida;
				
				bw.write(linea+NEWLINE);
			}
			
			int longAsiento = 6;
			int longNroComprobante = 15;
			int longPasajero = 82;
			int longTikect = 16;
			
			/*Crea Encabezado*/
			bw.write(NEWLINE);
			bw.write(NEWLINE);
			linea="+-------------------------------------------------------------------------------------------------------------------------------------+";
			bw.write(linea+NEWLINE);
			linea = "| ASIENTO ";
			linea += "|  COMPROBANTE   ";
			linea += "| PASAJERO" + tabular(74);
			linea += "|        TICKET        |";
			bw.write(linea+NEWLINE);
			linea="+-------------------------------------------------------------------------------------------------------------------------------------+";
			bw.write(linea+NEWLINE);
			for(DetalleEquipaje detalleEquipaje: listDetalleEquipajes){
				/*ASIENTO*/
				String asiento=(detalleEquipaje.getVentaPasaje()!=null?detalleEquipaje.getVentaPasaje().getNumeroAsiento().toString():"");
				asiento = (asiento.length()==1?"0"+asiento:asiento);
				longitud_C=asiento.length();
				linea="|   "+asiento+tabular(longAsiento-longitud_C);
				/*BOLETO*/
				String numeroComprobante=(detalleEquipaje.getVentaPasaje()!=null?detalleEquipaje.getVentaPasaje().getNumeroBoleto():"");
				longitud_C=numeroComprobante.length();
				linea+="| "+numeroComprobante+tabular(longNroComprobante-longitud_C)+"| ";
				/*PASAJERO*/
				String pasajero=(detalleEquipaje.getVentaPasaje()!=null?detalleEquipaje.getVentaPasaje().getPasajero().toString():"");
				longitud_C=pasajero.length();
				linea+=pasajero+tabular(longPasajero-longitud_C)+"| ";
				/*TICKET*/
				String ticket=detalleEquipaje.getTicket();
				longitud_C=ticket.length();
				linea+= tabular(5)+ ticket+tabular(longTikect-longitud_C)+"| ";
								
				bw.write(linea+NEWLINE);
			}
			linea="+-------------------------------------------------------------------------------------------------------------------------------------+";
			bw.write(linea+NEWLINE);
		
			bw.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
				
		return file;
	}
	
	/**
	 * Crea manifiesto de pasajeros / Listado de Pasajeros
	 * @param itinerario : class itinerario
	 * @param usuario    : class usuario
	 * @param agencia	 : class agencia
	 * @param esManiesto : true(es Manifiesto), false(es linstado de pasajeros)
	 * @return
	 */
	public static final File creaManifiesto_ListPax(Manifiesto manifiesto, Usuario usuario, Agencia agencia, Boolean esManiesto, String rotulo){
		Itinerario itinerario= new Itinerario();
		itinerario=ServiceLocator.getItinerarioManager().buscarPorId(manifiesto.getItinerario().getId());
		WndManifiesto wndmanifiesto = new WndManifiesto();
		Integer longitud_C=0;
		String fichero="";
		totalManifiesto=0.00;
		if(esManiesto==true){
//			fichero = Constantes.DIRECTORY_MANIFIESTOS + "MANPAX-"+itinerario.getId()+"-"+rotulo+".txt";
			fichero = Constantes.DIRECTORY_MANIFIESTOS+Constantes.CLAVE_PAHT + "MANPAX"+itinerario.getId()+"-"+rotulo+".txt";
		}else{
//			fichero = Constantes.DIRECTORY_LISTADOS + "LSTPAX-"+itinerario.getId()+".txt";
			fichero = Constantes.DIRECTORY_LISTADOS +Constantes.CLAVE_PAHT + "LSTPAX"+itinerario.getId()+".txt";
		}
			
		File file = new File(fichero);
		try{
//			BufferedWriter bw = new BufferedWriter(new FileWriter(file));
//			BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file),"8859_1"));
			BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file),"UTF-8"));
			String linea = "";
						
			//---> line 1:	TITULO DEL REPORTE
			String title="";
//			if(esManiesto==true){
			title=(esManiesto?"MANIFIESTO DE PASAJEROS":"LISTADO DE PASAJEROS");
			linea = tabular(54)+title;
			bw.write(linea + NEWLINE);
			//---> line 2:
			linea = Constantes.empresa;
			String ruc= Constantes.ruc;
			linea +=tabular(89)+"RUC: "+ruc;
			bw.write(linea+NEWLINE);
			//---> line 3:
			String ofPrincipal = Constantes.direccion_empresa;
			String centraTelf = Constantes.nro_telefono;
			linea="Of.Principal: "+ofPrincipal+tabular(10)+"Central Telf.: "+centraTelf+tabular(23);
			if(esManiesto==true)
				linea+="Nro.Manif.: "+manifiesto.getNumeroManifiesto();
			bw.write(linea+NEWLINE);
			//---> line 4:  Comentado por MAOE 27/06/2021, se movio el usuario al pie de pagina
//			String user=usuario.getApellidoPaterno()+" "+usuario.getApellidoMaterno()+", "+usuario.getNombre();
//			longitud_C=user.length();
//			linea="Usuario   : "+user+tabular(88-longitud_C)+"Cod.Serv : ";
//			bw.write(linea+NEWLINE);
			//---> line 5:(Agencia - Nro.Itinerario - Bus)
			String agenciA=agencia.getDenominacion();
			longitud_C=agenciA.length();
			String bus="";
			if(!(itinerario.getBus()==null))
				bus=itinerario.getBus().getCodigo();
			linea="Agencia   : "+agenciA+tabular(44-longitud_C);
			longitud_C=itinerario.getId().toString().length();
			linea+="Nro.Itin.: "+itinerario.getId()+tabular(38-longitud_C);
			linea+="Bus : "+bus;
			bw.write(linea+NEWLINE);
			//---> line 6:(Origen - Destino - Placa)
			String origen=itinerario.getRuta().getLocalidadOrigen().getDenominacion();
			String destino=itinerario.getRuta().getLocalidadDestino().getDenominacion();
			String placa="";
			if(!(itinerario.getBus()==null))
				placa=itinerario.getBus().getNumeroPlaca();
			longitud_C=origen.length();
			linea="Origen    : "+origen+tabular(44-longitud_C); longitud_C=destino.length();
			linea+="Destino  : "+destino+tabular(36-longitud_C);
			linea+="Placa : "+placa;
			bw.write(linea+NEWLINE);
			
			if(esManiesto){
				//---> linea 7:(Chofer1 -  Licencia - Nro. Tarj. Habilit.)
				String Chofer=""; String licencia=""; String TarjHabilit="";
				if(!(itinerario.getBus()==null)){
					Personal piloto = new Personal(); 
					piloto=itinerario.getBus().getProgramacionServicio().getPiloto();
					Chofer=piloto.toString(); //.getApellidoPaterno()+" "+piloto.getApellidoMaterno()+", "+piloto.getNombre();
					if(piloto.getLicencia() != null)
						licencia=piloto.getLicencia();
					if(!(itinerario.getBus().getDocumentoBus()==null))
						TarjHabilit=itinerario.getBus().getDocumentoBus().getNumeroDocumento();
				}
				longitud_C=Chofer.length();		
				linea="Chofer 1  : "+Chofer+tabular(44-longitud_C); longitud_C=licencia.length();
				linea+="Licencia : "+licencia+tabular(23-longitud_C);
				linea+="Nro. Tarj. Habilit.: "+TarjHabilit;
				bw.write(linea+NEWLINE);
				//---> linea 8:(Chofer2 -  Licencia - Marca)
				String marca="";
				if(!(itinerario.getBus()==null)){
					Personal copiloto = new Personal(); 
					copiloto=itinerario.getBus().getProgramacionServicio().getCopiloto();
					Chofer=copiloto.toString(); //.getApellidoPaterno()+" "+copiloto.getApellidoMaterno()+", "+copiloto.getNombre();
					if(copiloto.getLicencia() != null)
						licencia=copiloto.getLicencia();
					if(!(itinerario.getBus().getGrupoMantenimiento().getDenominacion()==null))
						marca=itinerario.getBus().getGrupoMantenimiento().getDenominacion();
				}
				longitud_C=Chofer.length();
				linea="Chofer 2  : "+Chofer+tabular(44-longitud_C); longitud_C=licencia.length();
				linea+="Licencia : "+licencia+tabular(36-longitud_C);
				linea+="Marca : "+marca;
				bw.write(linea+NEWLINE);
				//---> linea 8:(Chofer3 -  Licencia )
				Chofer="";licencia="";String servicio="";
				if(!(itinerario.getBus()==null) && itinerario.getBus().getProgramacionServicio().getCopilotoAuxiliar()!=null){
					Personal copilotoAux = new Personal(); 
					copilotoAux=itinerario.getBus().getProgramacionServicio().getCopilotoAuxiliar();
					Chofer=copilotoAux.toString();
					if(copilotoAux.getLicencia() != null)
						licencia=copilotoAux.getLicencia();
				}
				servicio=itinerario.getServicio().getDenominacion();
				longitud_C=Chofer.length();
				linea="Chofer 3  : "+Chofer+tabular(44-longitud_C); longitud_C=licencia.length();
				linea+="Licencia : "+licencia+tabular(33-longitud_C);
				linea+="Servicio : "+servicio;
				
				bw.write(linea+NEWLINE);
				//---> linea 9:(Terramoza -  Salida - Servicio)
				String tripulante=""; String salida=""; String dniTerramoza=""; 
				//Se agrego la condicion en el if porque la tripulante ya no es obligatorio, por MAOE 27/06/2021
				if(!(itinerario.getBus()==null) && itinerario.getBus().getProgramacionServicio().getTripulante()!=null ){
					Personal terramoza = new Personal(); 
					terramoza=itinerario.getBus().getProgramacionServicio().getTripulante();
					tripulante=terramoza.toString();//.getApellidoPaterno()+" "+terramoza.getApellidoMaterno()+", "+terramoza.getNombre();
					dniTerramoza=terramoza.getNroDocumento()!=null?terramoza.getNroDocumento():"";
				}
				salida=Constantes.FORMAT_DATE.format(itinerario.getFechaPartida())+" "+itinerario.getHoraPartida();
				
				longitud_C=tripulante.length();
				linea="Terramoza : "+tripulante+tabular(49-longitud_C); longitud_C=dniTerramoza.length();
				linea+="DNI : "+dniTerramoza+tabular(35-longitud_C);
				linea+="Salida : "+salida;
				
				bw.write(linea+NEWLINE);
			}
			
//			//---> linea 10: line
			if(itinerario.getServicio().getNumeroPisos()==2){
				linea="PISO 1.";
				bw.write(linea+NEWLINE);
			}
			
			/*Crea Encabezado piso 1*/
			linea="+-------------------------------------------------------------------------------------------------------------------------------------+";
			bw.write(linea+NEWLINE);
			creaEncabezadoManifiesto(bw);
			
			//---> linea 13: Detalle pasajeros
			List<VentaPasaje> list=ServiceLocator.getManifiestoManager().consultaPasajeros(itinerario.getId(),null);
			
			/*Crea detalle */
			bw.write(linea+NEWLINE);
			Integer piso=0;
			//Bengin 09/11/2021 - Jabanto - Muestra los pasajeros de ambos pisos en una sola lista (Solicitud de transmar)
			creaDetalleManifiesto(itinerario.getServicio().getNumeroAsientosPiso1(), wndmanifiesto, list, bw, piso, 0);
			/*Cuando el es de dos pisos*/			
			if(itinerario.getServicio().getNumeroPisos()==2){
				piso++;
				creaDetalleManifiesto(itinerario.getServicio().getNumeroAsientosPiso2()+itinerario.getServicio().getNumeroAsientosPiso1(), wndmanifiesto, list, bw,piso, itinerario.getServicio().getNumeroAsientosPiso1());
			}							
			
			//END BEGIN 09/11/2021 - Solicitud de Transmar (Margarita)
//			creaDetalleManifiesto(itinerario.getServicio().getNumeroAsientosPiso1(), wndmanifiesto, list, bw, piso, 0);
//			/*Cuando el es de dos pisos*/			
//			if(itinerario.getServicio().getNumeroPisos()==2){
//				linea="+-------------------------------------------------------------------------------------------------------------------------------------+";
//				bw.write(linea+NEWLINE);
//				/*Crea Encabezado piso 2*/
//				bw.write(NEWLINE);
//				linea="PISO 2.";
//				bw.write(linea+NEWLINE);
//				linea="+-------------------------------------------------------------------------------------------------------------------------------------+";
//				bw.write(linea+NEWLINE);
//				creaEncabezadoManifiesto(bw);
//				linea="+-------------------------------------------------------------------------------------------------------------------------------------+";
//				bw.write(linea+NEWLINE);
//				/*
//				 *12/07/2020
//				 *MAOE
//				 *PARA IMPRIMIR EL MANIFIESTO CON ASIENTOS SIN REINICIO EN EL SEGUNDO PISO				 * 
//				 */
//				creaDetalleManifiesto(itinerario.getServicio().getNumeroAsientosPiso2()+itinerario.getServicio().getNumeroAsientosPiso1(), wndmanifiesto, list, bw,piso+1, itinerario.getServicio().getNumeroAsientosPiso1());
//			}
							
			//---> linea final del detalle: line
			linea="+-------------------------------------------------------------------------------------------------------------------------------------+";
			bw.write(linea+NEWLINE);
			//---> total asientos - total pasajeros - archivo
			/*
			 * 12/07/2020
			 * MAOE
			 * Este segmento de codigo es el inicial consideramdo que la numeracion
			 * de asientos en los pisos se reinicia.
			 *   
			 */
			
//			Integer numeroAseintos=0;
//			for(int x=0; x<itinerario.getServicio().getNumeroPisos(); x++){
//				if(x==0)
//					numeroAseintos=itinerario.getServicio().getNumeroAsientosPiso1();
//				else if(x==1)
//					numeroAseintos+=itinerario.getServicio().getNumeroAsientosPiso2();
//			}
//			
			/*
			 * 12/07/2020
			 * MAOE
			 * Este segmento de codigo es el nuevo consideramdo que la numeracion
			 * de asientos en los pisos no se reinicia., sino es continuado
			 *   
			 */

			Integer numeroAseintos= itinerario.getServicio().getNumeroAsientosPiso1() + itinerario.getServicio().getNumeroAsientosPiso2();
//			for(int x=0; x<itinerario.getServicio().getNumeroPisos(); x++){
//				if(x==0)
//					numeroAseintos=itinerario.getServicio().getNumeroAsientosPiso1();
//				else if(x==1)
//					numeroAseintos+=itinerario.getServicio().getNumeroAsientosPiso2();
//			}
			
			
			
//			longitud_C=itinerario.getServicio().getNumeroAsientosPiso1().toString().length();
			longitud_C=numeroAseintos.toString().length();
			linea="Total Asientos : "+numeroAseintos+tabular(40-longitud_C);
//			linea="Total Asientos : "+itinerario.getServicio().getNumeroAsientosPiso1()+tabular(40-longitud_C);
			
			longitud_C=list.size();longitud_C=longitud_C.toString().length();
			linea+="Total Pasajeros : "+list.size()+tabular(23-longitud_C);
			//Se movio mas abajo MAOE 27/06/2021
//			linea+=rotulo+tabular(8);
			linea+="Total Manifiesto: S/ "+Util.toNumberFormat(totalManifiesto, 2);
			
			bw.write(linea+NEWLINE);		
			//---> autorizacion SUNAT
			if(esManiesto==true){
				linea="Aut. SUNAT Nro : "+manifiesto.getAutorizacionSunat();
				bw.write(linea+NEWLINE);
				//---> centro computo - lineas punteadas Indicador si es copia SUNAT, TRANSPORTISTA o ARCHIVO 
				linea=rotulo+tabular(33-rotulo.length());
				linea+=".................."+tabular(10);
				linea+=".................."+tabular(10);
				linea+=".................."+tabular(9);
				linea+="..................";
				bw.write(linea+NEWLINE);
				//---> 
				linea="EMPRESA"+tabular(25);
				linea+=Constantes.empresa+tabular(13);
				linea+="Conductor (1)"+tabular(15);
				linea+="Conductor (2)"+tabular(14);
				linea+="Conductor (3)";
				bw.write(linea+NEWLINE);
				//---> line User:
				String user=usuario.getApellidoPaterno()+" "+usuario.getApellidoMaterno()+", "+usuario.getNombre();
				longitud_C=user.length();
				linea="Usuario   : "+user; //+tabular(88-longitud_C)+"Cod.Serv : ";
				bw.write(linea+NEWLINE);
				// linea : salto de linea				
				bw.write(NEWLINE);
				// linea : observaciones
				linea="OBSERVACIONES : ";
				linea+="......................................................................................................................";
				bw.write(linea+NEWLINE);
				linea="......................................................................................................................................";
				bw.write(linea+NEWLINE);
				linea="......................................................................................................................................";
				bw.write(linea+NEWLINE);
			}else
				bw.write(NEWLINE);
			
			
			/*calcula el salto de pagina segun el tipo de servicio*/
//			if(esManiesto){
			switch (numeroAseintos) {
			case 36: // Si el servicio es de 36 Asientos
				//Inserta 10 Saltos de linea
				for(int i=0; i<10;i++){
					bw.write(NEWLINE);
				}
				break;
			case 40: // Si el servicio es de 40 Asientos
				//Inserta 10 Saltos de linea
				for(int i=0; i<6;i++){
					bw.write(NEWLINE);
				}
				break;
			case 44: // Si el Servicio es de 44 Asientos
				bw.write(NEWLINE);
				bw.write(NEWLINE);
				break;
			default:
				break;
			}
//			}	
			
			bw.close();
		}catch(IOException ioex){
			ioex.printStackTrace();
		}catch(Exception ex){
			ex.printStackTrace();
		}
		return file;
	}
	
	/**
	 * Crea el encabezado para el Manifiesto de Pasajeros.
	 * @param bw
	 * @throws Exception
	 */
	private static final void creaEncabezadoManifiesto(BufferedWriter bw) throws Exception{
		String linea = "";
		//---> linea 11: encabezado del detalle de pasajeros(Ato - N.Boleto - Nombre - Edad - TipoDoc. - Nr.Docum. - Destino - PtoEmbarque - Importe)
		linea="| Ato";
		linea+="| N.Boleto      ";
		linea+="| Nombre"+tabular(27);
		linea+="|Edad";
		linea+="|T.Doc";
		linea+="|Nr.Documento";
		linea+="| Destino       ";
		linea+="| Pto Embarque  ";
		linea+="|  F.P.   ";
		linea+="|  Importe  |";
		bw.write(linea+NEWLINE);
	}
	
	private static Integer getLenCaracteresInvalidos(String string){
		//Valida caracteres como (ï¿½,?), para reducir el espacio.
		int lengMenos=0;
		String caracteresSpeciales="ï¿½:?";		
		for(int y=0;y<string.length();y++){
			char cr0=string.toLowerCase().charAt(y);
			for(int x=0; x<caracteresSpeciales.split(":").length-1;x++){
				char cr1=caracteresSpeciales.charAt(x);
				
				if(cr0==cr1){
					lengMenos++;
					break;
				}
			}
		}
				
		return lengMenos;		
	}
	
	/**
	 * Crea detalle para el manifiesto y listado de pasajeros.
	 * @param numeroAsientos	: Nï¿½mero de asientos del bus.
	 * @param wndmanifiesto		: Class WndManifiesto
	 * @param list				: Lista que contine los pasajeros	
	 * @param bw				:
	 * @param piso				: Indica el numero de piso del bus.
	 * 12/07/2020
	 * MAOE
	 * SE AGERGO UN PARAMETRO PARA LA IMPRESION DEL MANIFIESTO EN BUSES QUE NO SE REINICIA LA NUMERACION EN EL SEGUNDO PISO.
	 * @throws Exception
	 */
	private static final void creaDetalleManifiesto(Integer numeroAsientos,WndManifiesto wndmanifiesto, List<VentaPasaje> list, BufferedWriter bw, Integer piso, Integer numeroAsientosPiso1) throws Exception{
		Integer longitud_C=0;
		/*
		 * 
		 * 
		 */
		int nroAsientos=0;
		if(piso == Constantes.PISO_UNO){
			nroAsientos = 0;
		}
		else{
			nroAsientos= numeroAsientosPiso1;
		}
		
		for(int i=nroAsientos; i<numeroAsientos; i++){
			String boleto="";
			String nombre="";
			String edad="";
			String tipoDocumento="";
			String documentoPax="";
			String destino="";
			String ptoEmbarque="";
			String formaPago="";
			String importe="";
			Integer longBoleto=14;
			Integer longNombre=33;
			Integer longEdad=3;
			Integer longTipodocumento=4;
			Integer longDocumentoPax=11;
			Integer longDestino=14;
			Integer longPtoEmbarque=14;
			Integer longFormaPago=8;
			Integer longImporte=6;
			Integer longitud_pax=33;
			
			Integer asiento=i+1;
			String linea="";
			
//			Busca los asientos en un servicio - Solo deberia haber mas de un registro con el mismo asiento cuando se vende por concepto de prioridad venta (case asiento 28 Lima - Ica aseinto 28 Ica - Arequipa)
			String asientos[]=wndmanifiesto.getAsientos_venpasId(list, asiento, piso).split(";");
			if(asientos[0].toString().length()>0){
				for(int ar=0;ar<asientos.length;ar++){
					long idVenta=Long.valueOf(asientos[ar].split("-")[1]);
					for(VentaPasaje ventaPasaje:list){
						if(ventaPasaje.getId().longValue()==idVenta){
							//Asiento
							String _asiento=(asiento.toString().length()==1?"0"+asiento.toString():asiento.toString());
							longitud_C= _asiento.length();
							linea="| "+_asiento+tabular(3-longitud_C)+"| ";
							/*BOLETO*/
							boleto=ventaPasaje.getNumeroBoleto();
							longitud_C=boleto.length();
							linea+=boleto+tabular(longBoleto-longitud_C)+"| ";
							
							/*NOMBRE*/
							nombre=ventaPasaje.getPasajero().toString();
							if(nombre.length()>longitud_pax)
								nombre=nombre.substring(0, longitud_pax);
							longitud_C=nombre.length();
							linea+=nombre+tabular((longNombre-getLenCaracteresInvalidos(nombre))-longitud_C)+"| ";
							/*EDAD*/
							edad=Util.calculaEdad(ventaPasaje.getPasajero().getFechaNacimiento()).toString();
							longitud_C=edad.length();
							linea+=edad+tabular(longEdad-longitud_C)+"| ";
							/*TIPO DOCUMENTO*/
							if(ventaPasaje.getPasajero().getTipoDocumento()!=null)
								tipoDocumento=ventaPasaje.getPasajero().getTipoDocumento().getNombreCorto();//.getDenominacion();
							if(tipoDocumento.length()>longTipodocumento)
								tipoDocumento=tipoDocumento.substring(0, longTipodocumento);
							longitud_C=tipoDocumento.length();
							linea+=tipoDocumento+tabular(longTipodocumento-longitud_C)+"| ";
							/*NUMERO DOCUMENTO*/
							if(ventaPasaje.getPasajero().getNumeroDocumento()!=null)
								documentoPax=ventaPasaje.getPasajero().getNumeroDocumento();
							if(documentoPax.length()>longDocumentoPax)
								documentoPax=documentoPax.substring(0,longDocumentoPax);
							longitud_C=documentoPax.length();
							linea+=documentoPax+tabular(longDocumentoPax-longitud_C)+"| ";
							/*DESTINO*/
							String odestino=ventaPasaje.getRuta().getDestino();
							destino=odestino.toUpperCase().substring(0,1)+odestino.toLowerCase().substring(1,odestino.length());
							if(destino.length()>longDestino)
								destino=destino.substring(0,longDestino);
							longitud_C=destino.length();
							linea+=destino.toUpperCase()+tabular(longDestino-longitud_C)+"| ";
							/*PUNTO DE EMPBARQUE*/
							ptoEmbarque=ventaPasaje.getAgenciaPartida().getNombreCorto();
							if(ptoEmbarque.length()>longPtoEmbarque)
								ptoEmbarque=ptoEmbarque.substring(0,longPtoEmbarque);
							longitud_C=ptoEmbarque.length();
							linea+=ptoEmbarque.toUpperCase()+tabular(longPtoEmbarque-longitud_C)+"| ";
							/*FORMA PAGO*/
							formaPago=(ventaPasaje.getFormaPago().getId().intValue()!=Constantes.ID_FORPAG_CORTESIA?"CONTADO":ventaPasaje.getFormaPago().getDenominacion());
							if(formaPago.length()>longFormaPago)
								formaPago=formaPago.substring(0,longFormaPago);
							longitud_C=formaPago.length();
							linea+=formaPago.toUpperCase()+tabular(longFormaPago-longitud_C)+"| ";
							/*IMPORTE*/
							String simbolo="";
							totalManifiesto += ventaPasaje.getImportePagado();
							importe=Util.toNumberFormat(ventaPasaje.getImportePagado(),2);
							longitud_C=importe.length();
							if(longitud_C > 0){
								simbolo="S/ ";
								longImporte=6;
							}
							linea+=simbolo+tabular(longImporte-longitud_C)+importe+" |";
							
							bw.write(linea+NEWLINE);
							break;
						}
					}
				}
			}else{
				String _asiento=(asiento.toString().length()==1?"0"+asiento.toString():asiento.toString());
				//El asiento esta libre
				longitud_C=_asiento.length();
				linea="| "+_asiento+tabular(3-longitud_C)+"| ";//Asiento
				linea+=boleto+tabular(longBoleto)+"| ";//Numero de boleto
				linea+=nombre+tabular(longitud_pax)+"| ";//Nombres pasajero
				linea+=edad+tabular(longEdad)+"| ";//Eda Pasajero
				linea+=tipoDocumento+tabular(longTipodocumento)+"| ";//tipo de documento
				linea+=documentoPax+tabular(longDocumentoPax)+"| ";//numero documento
				linea+=destino.toUpperCase()+tabular(longDestino)+"| ";//Destino
				linea+=ptoEmbarque.toUpperCase()+tabular(longPtoEmbarque)+"| ";//Punto de embarque
				linea+=tabular(longFormaPago)+"|"; //Forma pago
				linea+=tabular(longImporte)+"     |"; //Importe
				bw.write(linea+NEWLINE);
			}	
		}
				
	}
	
	
	public static final File creaDetalleLiquidacion(List<VentaPasaje> lstVentas,String nameFile, String local, String usuario, String rangoFechas, Double totalEfectivo, Double totalDolares){
					
//		String fichero = Constantes.DIRECTORY_DETALLE_LIQUIDACION +nameFile;
		String fichero = Constantes.DIRECTORY_DETALLE_LIQUIDACION +Constantes.CLAVE_PAHT +nameFile;
		String empresa = Constantes.empresa+tabular(85)+"RUC :"+Constantes.ruc;
		String ofiCentral=Constantes.direccion_empresa;
		String title="LIQUIDACION VENTA DE PASAJES";
		String encabezado="|NRO |";
		encabezado+="TRANSACCION|";
//		encabezado+=" N.CONTROL|";
		encabezado+="  COMPROBANTE   |";
		encabezado+="  COMP. REF.    |";
		encabezado+="  BRUTO   |";
		encabezado+=" RCGO  |";
		encabezado+=" DESCTO|";
		encabezado+=" A CTA.|";
		encabezado+=" PENAL.|";
		encabezado+="   NETO   |";
		encabezado+="  USUARIO   |";
		encabezado+=" FEC.VENTA  |";
		String interlineado="+----------------------------------------------------------------------------------------------------------------------------------+";
		
		String fechasDesdeHasta="DETALLE DE VENTAS REALIZADAS "+rangoFechas;
		String fechaHoraImpresion="FECHA HORA IMPRESION :"+Constantes.FORMAT_LONG.format(new Date());
		Integer lineBase=2;
		
		File file = new File(fichero);
		try{
			BufferedWriter bw = new BufferedWriter(new FileWriter(file));
			String linea = "";
			linea = tabular(lineBase)+empresa;
			bw.write(linea + NEWLINE);
			
			linea=tabular(lineBase)+ofiCentral;
			bw.write(linea + NEWLINE);
			
			linea=tabular(lineBase+50)+title;
			bw.write(linea + NEWLINE);
			
			linea=tabular(lineBase)+"LOCAL   :"+local;
			bw.write(linea+NEWLINE);
			linea=tabular(lineBase)+"USUARIO :"+usuario.toString();
			bw.write(linea+NEWLINE+NEWLINE);
			
			linea=tabular(lineBase)+fechasDesdeHasta+tabular(32)+fechaHoraImpresion;
			bw.write(linea+NEWLINE);
			
			linea=tabular(lineBase)+interlineado;
			bw.write(linea+NEWLINE);
			linea=tabular(lineBase)+encabezado;
			bw.write(linea+NEWLINE);
			linea=tabular(lineBase)+interlineado;
			bw.write(linea+NEWLINE);
			
			Integer longNroItem=2;
			Integer longTransaccion=9;
//			Integer longNControl=8;
			Integer longBoleto=14;
			Integer longBruto=8;
			Integer longRecargo=5;
			Integer longUsuario=10;
			Integer longFechaVenta=10;
			 
//			Double total=.00;
			int x=0;
			for(VentaPasaje venta: lstVentas){
				x++;
				/*NRO ITEM*/				
				linea=tabular(lineBase)+"|"+addColumnDetalleLiquidacion(String.valueOf(x), longNroItem,false);				
				/**TRANSACCION*/
				linea+=addColumnDetalleLiquidacion(venta.getTipoTransaccion(), longTransaccion,false);
//				/**NRO. CONTROL*/
//				linea+=addColumnDetalleLiquidacion("T"+venta.getNumeroControl().substring(longNControl+1,venta.getNumeroControl().length()), longNControl,false);
				/**NRO. BOLETO*/
				linea+=addColumnDetalleLiquidacion(venta.getNumeroBoleto(), longBoleto,false);
				/**NRO. BOLETO REFERENCIA*/
				linea+=addColumnDetalleLiquidacion(venta.getNumeroBoletoAnterior()!=null?venta.getNumeroBoletoAnterior():"", longBoleto,false);
				/**BRUTO*/
				linea+=addColumnDetalleLiquidacion(Util.toNumberFormat(venta.getTarifa(),2), longBruto,true);
				/**RECARGO*/
				linea+=addColumnDetalleLiquidacion(Util.toNumberFormat(venta.getRecargo(),2), longRecargo,true);
				/**DESCUENTO*/
				linea+=addColumnDetalleLiquidacion(Util.toNumberFormat(venta.getDescuento(),2), longRecargo,true);
				/**A CUENTA*/
				linea+=addColumnDetalleLiquidacion(Util.toNumberFormat(venta.getAcuenta(),2), longRecargo,true);
				/**PENALIDAD*/
				linea+=addColumnDetalleLiquidacion(Util.toNumberFormat(venta.getPenalidad(),2), longRecargo,true);
				/**NETO*/
				linea+=addColumnDetalleLiquidacion(Util.toNumberFormat(venta.getImportePagado(),2), longBruto,true);
				/**REPRESENTANTE DE VENTAS*/
				linea+=addColumnDetalleLiquidacion(venta.getUsuario().getLogin(), longUsuario,false);
				/**FECHA  VENTA*/
				linea+=addColumnDetalleLiquidacion(Constantes.FORMAT_DATE.format(venta.getFechaInsercion()), longFechaVenta,false);
				
				bw.write(linea+NEWLINE);
//				total+=venta.getImportePagado();
			}
			linea=tabular(lineBase)+interlineado;
			bw.write(linea+NEWLINE);
			
			linea=tabular(88-totalEfectivo.toString().length())+"TOTAL EFECTIVO : "+Util.toNumberFormat(totalEfectivo, 2); 
			bw.write(linea+NEWLINE);
			
			linea=tabular(89-totalDolares.toString().length())+"TOTAL DOLARES : "+Util.toNumberFormat(totalDolares, 2); 
			bw.write(linea+NEWLINE);

			bw.close();
			
		}catch(Exception ex){
			ex.printStackTrace();
		}
		
		return file;
	}
	/**
	 * Crea la impresion del certificado del Asegurado.
	 * @param vsAfiliacion : 
	 */
	public static File creaCertificadoSeguro(VSAfiliacion vsAfiliacion)throws Exception{
//		String fichero = Constantes.DIRECTORY_CERTIFICADOR_SEGURO+vsAfiliacion.getNumeroCertificado()+".txt";
		String fichero = Constantes.DIRECTORY_CERTIFICADOR_SEGURO+Constantes.CLAVE_PAHT+vsAfiliacion.getNumeroCertificado()+".txt";
		File file = new File(fichero);
		BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file),"8859_1"));
		String linea = "";
		try {
			/*Busca el tipo de documento*/
			List<VSTipoDocumento> lsttipoDocumento=ServiceLocator.getVentaSeguroManager().buscarTipoDocumentoPorEstado(Constantes.VALUE_ACTIVO);
			for(VSTipoDocumento tipoDocumento: lsttipoDocumento){
				if(vsAfiliacion.getVsAsegurado().getVsTipoDocumento().getId().intValue()==tipoDocumento.getId().intValue()){
					vsAfiliacion.getVsAsegurado().setVsTipoDocumento(tipoDocumento);
					break;
				}
			}
			/*Busca el tipo genero*/
			List<VSSexo> lstSexo=ServiceLocator.getVentaSeguroManager().buscarSexoPorEstado(Constantes.VALUE_ACTIVO);
			for(VSSexo sexo: lstSexo){
				if(vsAfiliacion.getVsAsegurado().getVsSexo().getId().intValue()==sexo.getId().intValue()){
					vsAfiliacion.getVsAsegurado().setVsSexo(sexo);
					break;
				}
			}
			
			/*Busca ubigeo*/
			String nameDepartamento="",nameProvincia="",nameDistrito="";
			String sUbigeo=ServiceLocator.getUbigeoManager().ubicacionGeografica(vsAfiliacion.getVsAsegurado().getUbigeo().getId());
			Integer posBarra=sUbigeo.indexOf("/");
			nameDepartamento=sUbigeo.substring(0,posBarra).trim();
			posBarra+=sUbigeo.substring(posBarra+2,sUbigeo.length()).indexOf("/");
			nameProvincia=sUbigeo.substring(nameDepartamento.length()+2,posBarra+1).trim();
			nameDistrito=sUbigeo.substring(posBarra+4,sUbigeo.length()).toString();
						
			/*Busca Nacionalidad*/
			String nacionalidad="";
			if(vsAfiliacion.getVsAsegurado().getNacionalidadID()!=null){
				Nacionalidad oNacionalidad = ServiceLocator.getNacionalidadManager().buscarPorId(vsAfiliacion.getVsAsegurado().getNacionalidadID().longValue());
				nacionalidad=oNacionalidad.getDenominacion();
			}
			
			Integer base=1;

			/*1. Número del Certificado*/
			linea = tabular(base+81)+vsAfiliacion.getNumeroCertificado()+tabular(30)+Constantes.FORMAT_DATE.format(vsAfiliacion.getFechaVenta());
			bw.write(linea + NEWLINE);
			/*2. Datos del Asegurado*/
			bw.write(NEWLINE);
			bw.write(NEWLINE);
			bw.write(NEWLINE);
			bw.write(NEWLINE);
			bw.write(NEWLINE);
			bw.write(NEWLINE);
			bw.write(NEWLINE);
			bw.write(NEWLINE);
			bw.write(NEWLINE);
			bw.write(NEWLINE);
			bw.write(NEWLINE);
			bw.write(NEWLINE);
			
//			VSAsegurado asegurado=vsAfiliacion.getVsAsegurado();
//			linea=tabular(base)+"Apellido Paterno,Materno y Nombres: "+asegurado.toString();
//			bw.write(linea + NEWLINE);
//			String tipoDocumento=asegurado.getVsTipoDocumento().getDenominacion() +"  Nro : "+asegurado.getNumeroDocumento();
//			linea=tabular(base)+"Tipo Documento   : "+tipoDocumento+tabular(61-tipoDocumento.length())+"Genero : "+asegurado.getVsSexo().getCodigo();
//			bw.write(linea + NEWLINE);
//			linea=tabular(base)+"Fecha Nacimiento : "+Constantes.FORMAT_DATE.format(asegurado.getFechaNacimiento())+tabular(8)+"Nacionalidad : ";
//			bw.write(linea + NEWLINE);
//			String telefono="";
//			String celular="";
//			if(asegurado.getTelefono()!=null)
//				telefono=asegurado.getTelefono();
//			if(asegurado.getCelular()!=null)
//				celular=asegurado.getCelular();
//			linea=tabular(base)+"Teléfono Fijo   : "+telefono+tabular(24-telefono.length())+"Celular: "+celular;bw.write(" ");
//			bw.write(linea + NEWLINE);
//			linea=tabular(base)+"Dirección, Urbanización : "+asegurado.getDireccion()+" - "+nameDistrito;
//			bw.write(linea + NEWLINE);
//			linea=tabular(base)+"Departamento, Provincia, Distrito : "+nameDepartamento+" - "+nameProvincia+" - "+nameDistrito;
//			bw.write(linea + NEWLINE);
//			linea=tabular(base)+"Correo Electrónico autorizado para envío y recepción de la póliza, renovación y otros documentos : ";
//			bw.write(linea + NEWLINE);
			
			/*4. Vigencia del Seguro*/
//			bw.write(NEWLINE);
//			bw.write(NEWLINE);
//			bw.write(NEWLINE);
//			bw.write(NEWLINE);
//			bw.write(NEWLINE);
//			bw.write(NEWLINE);
//			bw.write(NEWLINE);
//			bw.write(NEWLINE);
//			bw.write(NEWLINE);
//			bw.write(NEWLINE);
//			bw.write(NEWLINE);
//			bw.write(NEWLINE);
//			bw.write(NEWLINE);
//			bw.write(NEWLINE);
//			linea=tabular(base+16)+Constantes.FORMAT_DATE.format(vsAfiliacion.getFechaVigenciaInicial());
//			bw.write(linea + NEWLINE);
			
			/*FORMATO UN POCO MAS ESTRUCTURADO*/
			VSAsegurado asegurado=vsAfiliacion.getVsAsegurado();
			linea=tabular(base)+"APELLIDO PATERNO : "+asegurado.getApellidoPaterno();
			bw.write(linea + NEWLINE);
			linea=tabular(base)+"APELLIDO MATERNO : "+asegurado.getApellidoMaterno();
			bw.write(linea + NEWLINE);
			linea=tabular(base)+"NOMBRES          : "+asegurado.getNombres();
			bw.write(linea + NEWLINE);
			String tipoDocumento=asegurado.getVsTipoDocumento().getDenominacion();
			String numeroDocumento=asegurado.getNumeroDocumento();
			String genero=asegurado.getVsSexo().getCodigo();
			linea=tabular(base)+"TIPO DOCUMENTO   : "+tipoDocumento+tabular(23-tipoDocumento.length())+"NRO DOCUMENTO : "+numeroDocumento+tabular(20-numeroDocumento.length())+"GENERO : "+genero;
			bw.write(linea + NEWLINE);
			linea=tabular(base)+"FECHA NACIMIENTO : "+Constantes.FORMAT_DATE.format(asegurado.getFechaNacimiento())+tabular(14)+"NACIONALIDAD : "+nacionalidad;
			bw.write(linea + NEWLINE);
			String telefono="";
			String celular="";
			if(asegurado.getTelefono()!=null)
				telefono=asegurado.getTelefono();
			if(asegurado.getCelular()!=null)
				celular=asegurado.getCelular();
			linea=tabular(base)+"TELEFONO FIJO    : "+telefono+tabular(29-telefono.length())+"CELULAR : "+celular;bw.write("");
			bw.write(linea + NEWLINE);
			linea=tabular(base)+"DIRECCION        : "+asegurado.getDireccion();
			bw.write(linea + NEWLINE);
			linea=tabular(base)+"URBANIZACION     : "+(asegurado.getUrbanizacion()!=null?asegurado.getUrbanizacion():"");
			bw.write(linea + NEWLINE);
			linea=tabular(base)+"DEPARTAMENTO, PROVINCIA, DISTRITO : "+nameDepartamento+" - "+nameProvincia+" - "+nameDistrito;
			bw.write(linea + NEWLINE);
			linea=tabular(base)+"CORREO ELECTRONICO AUTORIZADO PARA ENVIO Y RECEPCION DE LA POLIZA, RENOVACION Y OTROS DOCUMENTOS : "+(asegurado.getEmail()!=null?asegurado.getEmail():" ");
			bw.write(linea + NEWLINE);
			
			/*4. Vigencia del Seguro*/
			bw.write(NEWLINE);
			bw.write(NEWLINE);
			bw.write(NEWLINE);
			bw.write(NEWLINE);
			bw.write(NEWLINE);
			bw.write(NEWLINE);
			bw.write(NEWLINE);
			bw.write(NEWLINE);
			bw.write(NEWLINE);
			bw.write(NEWLINE);
//			bw.write(NEWLINE);
			linea=tabular(base+16)+Constantes.FORMAT_DATE.format(vsAfiliacion.getFechaVigenciaInicial());
			bw.write(linea + NEWLINE);
			
			bw.close();
		} catch (Exception e) {
			bw.close();
			throw new Exception(e.getMessage());
		}
		
		return file;
	}

	
	/** 
	 * Agrega una cuena columna al detalle de la liquidaciï¿½n
	 * @param valueDeta	: valor de la columna
	 * @param longitudColumn : Longitud predeternimada para la columna
	 * @return
	 */
	private static String addColumnDetalleLiquidacion(String valueDeta, Integer longitudColumn, boolean isNumber){
		Integer longitud_C=0; String linea="";
		longitud_C=valueDeta.length();
		valueDeta=valueDeta.substring(0,longitud_C>longitudColumn?longitudColumn:longitud_C).toString();
		if(isNumber)// Si es Numï¿½rico alinea de Derecha a Izquierda
			linea=tabular((longitudColumn-longitud_C)+1)+valueDeta+" |";
		else//Si es una Cadena y alinea de Izquierada a Derecha
			linea=tabular(1)+valueDeta+tabular(longitudColumn-longitud_C)+" |";

		return linea;
	}
	
	/**
	 * Devuelve una cadena de espacios en blanco.
	 * @param espacios	: Numero de espacios en blanco.
	 * @return String
	 */
	private static final String tabular(int espacios){
		String cadena = "";
		for(int i=0; i<espacios; i++){
			cadena = cadena + " ";
		}
		return cadena;
	}
	
	private static String obtenerHoraEmbarque(Long idItinerario, Integer idAgencia){
		String result = null;
		try{
			TreeMap<String, Object> criteriosBusqueda = new TreeMap<String, Object>();
			ItinerarioAgenciaPartidaID itinerarioAgenciaPartidaID = new ItinerarioAgenciaPartidaID();
			itinerarioAgenciaPartidaID.setIdItinerario(idItinerario);
			itinerarioAgenciaPartidaID.setIdAgencia(idAgencia);
			criteriosBusqueda.put("itinerarioAgenciaPartidaID", itinerarioAgenciaPartidaID);
			criteriosBusqueda.put("estadoRegistro", Constantes.VALUE_ACTIVO);
			List<ItinerarioAgenciaPartida> lstItinerarioAgenciaPartida = ServiceLocator.getItinerarioAgenciaPartidaManager().buscarPorX(criteriosBusqueda, null);
			if(lstItinerarioAgenciaPartida.size()>0)
				result = lstItinerarioAgenciaPartida.get(0).getHoraPartida();
			else
				result = null;
		}catch(Exception ex){
			ex.printStackTrace();
		}
		return result;
	}

	
	public static File creaDetalleVentaSeguros(Listbox listVentaSeguros, String nameFile,String agencia, String usuario)throws Exception{
//		String fichero = Constantes.DIRECTORY_DETALLE_VENTA_SEGUROS+nameFile+".txt";
		String fichero = Constantes.DIRECTORY_DETALLE_VENTA_SEGUROS +Constantes.CLAVE_PAHT + nameFile+".txt";
		File file = new File(fichero);
		BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file),"8859_1"));
		String linea = "";
		try{
			Integer base=1;
			int longFecha=12,longBoleto=11,longPasajero=45,longTipPax=6,longImporte=7,longUser=30;
			String lineas="+------------------------------------------------------------------------------------------------------------------------------+";
			String columnas="|   FECHA    |   BOLETO    |            PASAJERO                           | TIPPAX | IMPORTE |            USUARIO             |";
			
			
			linea = tabular(base)+Constantes.empresa+tabular(85)+"RUC : "+Constantes.ruc;
			bw.write(linea + NEWLINE);
			linea = tabular(base+50)+"REPORTE VENTA DE SEGUROS";
			bw.write(linea + NEWLINE);
			bw.write(NEWLINE);
			linea = tabular(base)+"AGENCIA : "+agencia+tabular(58)+"FECHA / HORA IMPRESIÓN : "+Constantes.FORMAT_DATE_TIME_24H.format(new Date());
			bw.write(linea + NEWLINE);
			linea = tabular(base)+"USUARIO : "+usuario;
			bw.write(linea + NEWLINE);
			
			linea = tabular(base)+lineas;
			bw.write(linea + NEWLINE);
			linea = tabular(base)+columnas;
			bw.write(linea + NEWLINE);
			linea = tabular(base)+lineas;
			bw.write(linea + NEWLINE);		
			
			Double totalPaxFree=.00;
			Double totalPaxNormal=.00;
			for(Listitem item: listVentaSeguros.getItems()){
				VSAfiliacion afiliacion=item.getValue();
				String fecha="| "+Constantes.FORMAT_DATE.format(afiliacion.getFechaVenta());
				linea=addColumnDetalleLiquidacion(fecha, longFecha, false);
				String boleto=(afiliacion.getNumeroBoleto()!=null?afiliacion.getNumeroBoleto():" ");
				linea+=addColumnDetalleLiquidacion(boleto, longBoleto, false);
				String pasajero=(afiliacion.getVsAsegurado().toString());
				linea+=addColumnDetalleLiquidacion(pasajero, longPasajero, false);
				String tipoPax="  "+afiliacion.getVsAsegurado().getTipoPasajero().substring(0,1);
				linea+=addColumnDetalleLiquidacion(tipoPax, longTipPax, false);
				String importe="  "+Util.toNumberFormat(afiliacion.getImportePagado(), 2);
				linea+=addColumnDetalleLiquidacion(importe, longImporte, false);
				String user=afiliacion.getUsuario().toString();
				linea+=addColumnDetalleLiquidacion(user, longUser, false);
				
				bw.write(linea + NEWLINE);
				
				if(afiliacion.getVsAsegurado().getTipoPasajero().equals("FRECUENTE"))
					totalPaxFree+=afiliacion.getImportePagado();
				else 
					totalPaxNormal+=afiliacion.getImportePagado();
			}
			linea = tabular(base)+lineas;
			bw.write(linea + NEWLINE);
			
			linea = tabular(base+62)+"TOTAL PAX NORMAL : "+tabular(10-totalPaxNormal.toString().length())+Util.toNumberFormat(totalPaxNormal,2); // addColumnDetalleLiquidacion(totalPaxNormal.toString(), 10,true);
			bw.write(linea + NEWLINE);
			linea = tabular(base+59)+"TOTAL PAX FRECUENTE : "+tabular(10-totalPaxFree.toString().length())+Util.toNumberFormat(totalPaxFree,2);
			bw.write(linea + NEWLINE);
			linea = tabular(base+59)+"---------------------------------";
			bw.write(linea + NEWLINE);
			linea = tabular(base+73)+"TOTAL : "+tabular(10-String.valueOf(totalPaxNormal+totalPaxFree).toString().length())+Util.toNumberFormat(totalPaxNormal+totalPaxFree,2);
			bw.write(linea + NEWLINE);
			
			bw.write(NEWLINE);
			
			bw.close();
		}catch(Exception ex){
			ex.printStackTrace();
			bw.close();
			throw new Exception(ex.getMessage());
		}
		
		return file;
	}

	
	/**
	 * Crea archivo para la impresión y/o pre-visualizacion de la hoja de ruta.
	 * @param nameFile		: Nombre del Archivo a crear.
	 * @param hojaRuta		: Instancia de la Clase Hojaruta (WS MTC)
	 * @param programacion	: Instancia de la programacion
	 * @throws Exception
	 */
//	public static void crearHRE(String nameFile,ProgramacionServicio programacion) throws Exception{
	public static File crearHRE(String nameFile,String numeroHRE) throws Exception{
		Integer base=6;
		Integer maxLongDocu=107;
//		String fichero = Constantes.DIRECTORY_HRE+nameFile+".txt";
		String fichero = Constantes.DIRECTORY_HRE + Constantes.CLAVE_PAHT + nameFile+".txt";
			
		File file = new File(fichero);
		try{			
			/**Busca el detalle de la hre para obtener los intercambios de pilotos en ruta*/
//			HRE hre=new HRE(new NumeroHojaRutaID(programacion.getHojaRuta().getNumeroHojaRutaID().getIdNumeroHojaRuta()));
//			HRE hre=new HRE(new NumeroHojaRutaID(numeroHRE));
			HRE hre=ServiceLocator.getHREManager().buscarPorId(numeroHRE);
			TreeMap<String, Object>criteriosBusqueda=new TreeMap<String, Object>();
			criteriosBusqueda.put("hre", hre);
			criteriosBusqueda.put("tipoPersonal",new TipoPersonal(Constantes.ID_TIPPER_PILOTO_COPILOTO));
			criteriosBusqueda.put("estadoRegistro", Constantes.VALUE_ACTIVO);
			List<String>criteriosOrden=new ArrayList<String>();
			criteriosOrden.add("id");
			List<DetalleFlotaHRE>resultDetalleFlota=ServiceLocator.getDetalleFlotaHREManager().buscarPorX(criteriosBusqueda, criteriosOrden);
			
			
			
//			BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file),"8859_1"));
			BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file),"UTF-8"));
			String linea = "";
			String interliniado="--------------------------------------------------------------------------------------------------------------------------------";
			String interliniado1="+------------------------------------------------------------------------------------------------------------------------------+";
			//---> line 1:	TITULO DEL REPORTE
			String title="REGISTRO NACIONAL DE TRANSPORTE TERRESTRE HOJA DE RUTA";
			linea = tabular(40)+title;
			bw.write(linea + NEWLINE);
			//
			bw.write(NEWLINE);
			bw.write(tabular(115) + Constantes.FORMAT_DATE_TIME_24H.format(new Date())+ NEWLINE);
			// 
			bw.write(tabular(base)+interliniado+NEWLINE);
			String label="RAZON SOCIAL       : "+Constantes.empresa;
			linea = tabular(base)+label;
			linea+=tabular(60)+"Nro. HRE :"+hre.getNumeroHojaRutaID().getIdNumeroHojaRuta(); //programacion.getHojaRuta().toString();
			bw.write(linea+NEWLINE);
			label="DIRECCION          : ";
			linea = tabular(base)+label;
			bw.write(linea+NEWLINE);
			label="CORREO ELECTRONICO :                                     TELEFONO : ";
			linea = tabular(base)+label;
			bw.write(linea+NEWLINE);
			label="NUMERO PLACA       : "+hre.getNumeroPlaca(); //WSMTC.toFormatNroPlaca(programacion.getBus().getNumeroPlaca());
			label+=tabular(20)+"FECHA INICIO VIAJE : "+Constantes.FORMAT_DATE.format(hre.getFechaSalida());//+Constantes.FORMAT_DATE.format(programacion.getItinerario().getFechaPartida());
			label+=tabular(22)+"FECHA FIN VIAJE : "+Constantes.FORMAT_DATE.format(hre.getFechaEstimacionLlegada());//+Constantes.FORMAT_DATE.format(programacion.getItinerario().getFechaLlegada());
			linea = tabular(base)+label;
			bw.write(linea+NEWLINE);
			/*da formato especial a la ruta*/
//			String ruta=programacion.getMtcDetalleRuta().getMtcRuta().toString();
			String ruta=hre.getMtcRuta().toString(); //programacion.getHojaRuta().getMtcRuta().toString(); // mtcDetalleRuta.getMtcRuta().toString();
			
			if(ruta.length() > maxLongDocu){
				String ruta1=ruta.substring(0, maxLongDocu);
				String ruta2=ruta.substring(maxLongDocu, ruta.length());
				if(ruta2.length()>maxLongDocu){
					String ruta3=ruta2.substring(0, maxLongDocu);
					String ruta4=ruta2.substring(maxLongDocu, ruta.length());
					label="RUTA               : "+ruta1;
					label+="\n                           "+ruta3;
					label+="\n                           "+ruta4;
				}else{
					label="RUTA               : "+ruta1;
					label+="\n                           "+ruta2;
				}
			}else{
//				label="RUTA               : "+programacion.getMtcDetalleRuta().getMtcRuta().toString();
//				label="RUTA               : "+programacion.getHojaRuta().getMtcRuta().toString();
				label="RUTA               : "+hre.getMtcRuta().toString(); //programacion.getHojaRuta().getMtcRuta().toString();
			}
			
			linea = tabular(base)+label;
			bw.write(linea+NEWLINE);
			bw.write(tabular(base)+interliniado+NEWLINE);
			/*Fin formato especial ruta*/
			
			label="ESTANDAR           : X";
			linea = tabular(base)+label;
			bw.write(linea+NEWLINE);
			label="TERMINAL T. SALIDA : "+hre.getMtcTerminalSalida().getDireccion(); // programacion.getHojaRuta().getMtcTerminalSalida().getDireccion();
			linea = tabular(base)+label;
			bw.write(linea+NEWLINE);
			label="TERMINAL T. LLEGADA: "+hre.getMtcTerminalLlegada().getDireccion(); //programacion.getHojaRuta().getMtcTerminalLlegada().getDireccion();
			linea = tabular(base)+label;
			bw.write(linea+NEWLINE);
			label="ESCALAS COMERCIALES: ";
			linea = tabular(base)+label;
			bw.write(linea+NEWLINE);
			label="HORA SALIDA        : "+hre.getHoraSalida(); //programacion.getItinerario().getHoraPartida();
			linea = tabular(base)+label;
			bw.write(linea+NEWLINE);
			label="HORA LLEGADA       : "+hre.getHoraEstimacionLlegada();// programacion.getItinerario().getHoraLlegada();
			linea = tabular(base)+label;
			bw.write(linea+NEWLINE);
			bw.write(tabular(base)+interliniado+NEWLINE);
			
			bw.write(NEWLINE);
			
			bw.write(tabular(base)+interliniado1+NEWLINE);
			String encabezado="| NOMBRES COMPLETOS                                                 |  LICENCIA          | HORA INICIO | HORA FIN    | TURNO   |";
			bw.write(tabular(base)+encabezado+NEWLINE);
			bw.write(tabular(base)+interliniado1+NEWLINE);
			/*turnos de conduccion de los pilotos*/
//			bw.write(NEWLINE);
			int maxLenNombres=66;
			int maxLenLicencia=19;
			int maxLenHoraInicio=12;
			int maxLenHoraFin=12;
			int maxTurno=8;
			int turno=0;
//			for(HConductor hConductor :hojaRuta.getConductores().getHConductor()){
			
//			/**Busca el detalle de la hre para obtener los intercambios de pilotos en ruta*/
//			HRE hre=new HRE(new NumeroHojaRutaID(programacion.getHojaRuta().getNumeroHojaRutaID().getIdNumeroHojaRuta()));
//			TreeMap<String, Object>criteriosBusqueda=new TreeMap<>();
//			criteriosBusqueda.put("hre", hre);
//			criteriosBusqueda.put("tipoPersonal",new TipoPersonal(Constantes.ID_TIPPER_PILOTO_COPILOTO));
//			criteriosBusqueda.put("estadoRegistro", Constantes.VALUE_ACTIVO);
//			List<String>criteriosOrden=new ArrayList<>();
//			criteriosOrden.add("id");
//			List<DetalleFlotaHRE>resultDetalleFlota=ServiceLocator.getDetalleFlotaHREManager().buscarPorX(criteriosBusqueda, criteriosOrden);
			
			for(DetalleFlotaHRE detalleFlotaHRE: resultDetalleFlota){
				turno++;
				Personal personal=detalleFlotaHRE.getPersonal();
				String nombes=personal.toString();
				String licencia=personal.getLicencia();
				String horaInicio=detalleFlotaHRE.getHoraInicioConduccion();
				String horaFin=detalleFlotaHRE.getHoraTerminoConduccion();
				
				/*Crea linae Nombres    Licencia    Truno*/				
				String conductor="| "+nombes+tabular(maxLenNombres-nombes.length())+
								 "| "+licencia+tabular(maxLenLicencia-licencia.length())+
								 "| "+horaInicio+tabular(maxLenHoraInicio-horaInicio.length())+
								 "| "+horaFin+tabular(maxLenHoraFin-horaFin.length())+
								 "| "+turno+tabular(maxTurno-String.valueOf(turno).length())+"|";				
				linea = tabular(base)+conductor;
				bw.write(linea+NEWLINE);

			}
			/*Incidencias del Viaje*/
			bw.write(tabular(base)+interliniado1+NEWLINE);
			bw.write(NEWLINE);
			encabezado="INCIDENCIAS DEL VIAJE :";
			bw.write(tabular(base)+encabezado+NEWLINE);
			
			String incidencia="................................................   EN  .........................................................................";
			String descripcion="            Nombres y Apellidos                                            Lugar, Fecha y hora";
			String constancia="DEJA CONSTANCIA QUE ............................................................................................................";
			constancia+="\n      ................................................................................................................................";
			constancia+="\n      ................................................................................................................................";
			constancia+="\n      ...........................................................................   ..........................   .....................";
			String descripcionFin="                                                                                        Firma                     Nro. DNI";
			bw.write(tabular(base)+incidencia+NEWLINE);
			bw.write(tabular(base)+descripcion+NEWLINE);
			bw.write(tabular(base)+constancia+NEWLINE);
			bw.write(tabular(base)+descripcionFin+NEWLINE);
			
			bw.write(NEWLINE);
			
			bw.write(tabular(base)+incidencia+NEWLINE);
			bw.write(tabular(base)+descripcion+NEWLINE);
			bw.write(tabular(base)+constancia+NEWLINE);
			bw.write(tabular(base)+descripcionFin+NEWLINE);
			
			bw.write(NEWLINE);
			
			bw.write(tabular(base)+incidencia+NEWLINE);
			bw.write(tabular(base)+descripcion+NEWLINE);
			bw.write(tabular(base)+constancia+NEWLINE);
			bw.write(tabular(base)+descripcionFin+NEWLINE);
			
			bw.write(NEWLINE);
						
			bw.close();
		} catch (Exception ex) {
			ex.printStackTrace();
			throw new Exception(ex.getMessage());
		}
		
		return file;
	}
	
}

