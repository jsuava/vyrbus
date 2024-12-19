/**
/**
 * Sistema		: Sistema de Ventas y Reservas
 * Descripcin	: 
 * Autor		: Jos Abanto
 * Fecha		: 10/10/2016
 * Hora			: 09:39:06
 */
package com.cystesoft.vyrbus.service.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.Serializable;
import java.math.BigDecimal;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.Marshaller;
import javax.xml.namespace.QName;

import org.zkoss.io.Files;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zul.Filedownload;
import org.zkoss.zul.Timer;
import org.zkoss.zul.Window;

import sun.misc.BASE64Encoder;

import com.cystesoft.vyrbus.model.bean.Agencia;
import com.cystesoft.vyrbus.model.bean.ConfiguracionImpresora;
import com.cystesoft.vyrbus.model.bean.DestinatariosEmails;
import com.cystesoft.vyrbus.model.bean.DetalleEquipaje;
import com.cystesoft.vyrbus.model.bean.Gasto;
import com.cystesoft.vyrbus.model.bean.ItinerarioAgenciaPartida;
import com.cystesoft.vyrbus.model.bean.ItinerarioAgenciaPartidaID;
import com.cystesoft.vyrbus.model.bean.Liquidacion;
import com.cystesoft.vyrbus.model.bean.OperadorTarjetaCredito;
import com.cystesoft.vyrbus.model.bean.TipoCobranza;
import com.cystesoft.vyrbus.model.bean.TipoMovimiento;
import com.cystesoft.vyrbus.model.bean.TranscarUsuarioPersonal;
import com.cystesoft.vyrbus.model.bean.Usuario;
import com.cystesoft.vyrbus.model.bean.UsuarioHardware;
import com.cystesoft.vyrbus.model.bean.VentaPasaje;
import com.cystesoft.vyrbus.service.fe.ArrayOfDetalleVenta;
import com.cystesoft.vyrbus.service.fe.ArrayOfInformacionAdicionalPropiedadAdicional;
import com.cystesoft.vyrbus.service.fe.ArrayOfInformacionAdicionalTotalMonedaAdicional;
import com.cystesoft.vyrbus.service.fe.ArrayOfInformacionCredito;
import com.cystesoft.vyrbus.service.fe.Cliente;
import com.cystesoft.vyrbus.service.fe.DetalleVenta;
import com.cystesoft.vyrbus.service.fe.DocumentoBaja;
import com.cystesoft.vyrbus.service.fe.DocumentoReferencia;
import com.cystesoft.vyrbus.service.fe.IMEFEService;
import com.cystesoft.vyrbus.service.fe.InformacionAdicional;
import com.cystesoft.vyrbus.service.fe.InformacionAdicionalPropiedadAdicional;
import com.cystesoft.vyrbus.service.fe.InformacionAdicionalTotalMonedaAdicional;
import com.cystesoft.vyrbus.service.fe.InformacionCredito;
import com.cystesoft.vyrbus.service.fe.Nota;
import com.cystesoft.vyrbus.service.fe.Result;
import com.cystesoft.vyrbus.service.fe.MEFEService;
import com.cystesoft.vyrbus.service.fe.Venta;
import com.cystesoft.vyrbus.service.locator.ServiceLocator;
import com.cystesoft.vyrbus.service.mappers.ResumenComprobante;
import com.cystesoft.vyrbus.service.xml.XmlCliente;
import com.cystesoft.vyrbus.service.xml.XmlConfigPrint;
import com.cystesoft.vyrbus.service.xml.XmlDetalleLiquidacionEgresos;
import com.cystesoft.vyrbus.service.xml.XmlDetalleLiquidacionIngresoVenta;
import com.cystesoft.vyrbus.service.xml.XmlDetalleLiquidacionOtrosIngresos;
import com.cystesoft.vyrbus.service.xml.XmlDetalleVentaPasajes;
import com.cystesoft.vyrbus.service.xml.XmlEquipaje;
import com.cystesoft.vyrbus.service.xml.XmlEquipajes;
import com.cystesoft.vyrbus.service.xml.XmlItem;
import com.cystesoft.vyrbus.service.xml.XmlItemEgresoLiquidacion;
import com.cystesoft.vyrbus.service.xml.XmlItemIngresoVentaLiquidacion;
import com.cystesoft.vyrbus.service.xml.XmlItemOtroIngresoLiquidacion;
import com.cystesoft.vyrbus.service.xml.XmlLiquidacion;
import com.cystesoft.vyrbus.service.xml.XmlLiquidacionTuentrada;
import com.cystesoft.vyrbus.service.xml.XmlPasajero;
import com.cystesoft.vyrbus.service.xml.XmlVenta;
import com.cystesoft.vyrbus.service.xml.XmlVentaPasaje;
import com.cystesoft.vyrbus.view.tuentrada.LiquidacionTuentrada;
import com.cystesoft.vyrbus.view.ui.DlgMessage;

/**
 * @author jabanto
 *
 */
public class WSFE implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	//private static String TOKEN="#EOAM13579ZCBMKHFAQETUIP12W4R6Y8U9O#...";
	private static String TOKEN="#KENDOHAE73O10A30MS69T07CDR14T06O11SA12TATA#...";
	private static String NAMESPACE="http://schemas.datacontract.org/2004/07/FEService.Input";
	private static final DateFormat FORMAT_DATE = new SimpleDateFormat ("yyyy-MM-dd");
	private static String FE_TIPCOM_FACTURA="01";
	private static String FE_TIPCOM_BOLETA="03";
	private static String FE_TIPCOM_NOTA_CREDITO="07";
	private static String FE_TIPCOM_NOTA_DEBITO="08";
	public static String FE_TIPDOC_RUC="6";
	public static String FE_TIPDOC_DNI="1";
	public static String FE_TIPDOC_CARNET_EXTRANEJERIA="4";
	public static String FE_TIPDOC_PASAPORTE="7";
	public static String FE_TIPDOC_CEDULA_DIPLOMATICA_IDENTIDAD="A";
	public static Integer FE_TIPO_VENTA_CONTADO=20;
	public static Integer FE_TIPO_VENTA_CREDITO=21;
	public static Integer FE_TIPO_VENTA_CORTESIA=22;
	public static Integer DOLARES=2;
	
	public static IMEFEService iMEFEService;
	
	private static IMEFEService getSoap()throws Exception{
		try {
//			System.setProperty("http.proxyHost", "192.168.50.1");
//			System.setProperty("http.proxyPort", "8080");
			
			if(iMEFEService==null){
				MEFEService mefeservice= new MEFEService();
				iMEFEService=mefeservice.getBasicHttpBindingIMEFEService();
			}
		} catch (Exception e) {
			sendMail("Metod getSoap : "+e.getMessage());
			e.printStackTrace();
		}
		return iMEFEService;
	}
	
	/**
	 * Realiza la anulacion del comprobante
	 * @return
	 * @throws Exception
	 */
	public static Result anularComprobante(VentaPasaje ventaAnular)throws Exception{
		String tipoComprobante="";
		switch (ventaAnular.getTipoComprobante().getId().intValue()) {
		case Constantes.ID_TIPCOM_BOLETA_VENTA:
			tipoComprobante=FE_TIPCOM_BOLETA;
			break;
		case Constantes.ID_TIPCOM_FACTURA:
			tipoComprobante=FE_TIPCOM_FACTURA;
			break;
		case Constantes.ID_TIPCOM_NOTA_CREDITO:
			tipoComprobante=FE_TIPCOM_NOTA_CREDITO;
			break;
		case Constantes.ID_TIPCOM_NOTA_DEBITO:
			tipoComprobante=FE_TIPCOM_NOTA_DEBITO;
			break;
		default:
			break;
		}
		
		String serie=ventaAnular.getNumeroBoleto().split("-")[0];
		String correlativo=ventaAnular.getNumeroBoleto().split("-")[1];
		
		DocumentoBaja documentoBaja= new DocumentoBaja();
		documentoBaja.setAgenciaID(ventaAnular.getAgencia().getId().longValue());
		documentoBaja.setRucEmpresa(new JAXBElement<String>(new QName(NAMESPACE,"rucEmpresa"), String.class, Constantes.RUC_TRANSMAR));
		documentoBaja.setDescripcionMotivo(new JAXBElement<String>(new QName(NAMESPACE,"descripcionMotivo"), String.class, ventaAnular.getObservaciones()));
		documentoBaja.setFechaEmision(new JAXBElement<String>(new QName(NAMESPACE,"fechaEmision"), String.class, FORMAT_DATE.format(ventaAnular.getFechaLiquidacion())));
		documentoBaja.setNumeroCorrelativo(new JAXBElement<String>(new QName(NAMESPACE,"numeroCorrelativo"), String.class, correlativo));
		documentoBaja.setNumeroSerie(new JAXBElement<String>(new QName(NAMESPACE,"numeroSerie"), String.class, serie));
		documentoBaja.setTipoDocumentoID(new JAXBElement<String>(new QName(NAMESPACE,"tipoDocumentoID"), String.class, tipoComprobante));
		documentoBaja.setUsuarioID(ventaAnular.getAgencia().getId().longValue());
		documentoBaja.setUsuarioModificacion(new JAXBElement<String>(new QName(NAMESPACE,"usuarioModificacion"), String.class, ventaAnular.getUsuarioModificacion()));
	
		Result result=getSoap().setBajaDocumento(TOKEN, documentoBaja);
		
		return result;
	}
	
	/**
	 * Realiza el envio de la notas de credito
	 * @param notaCredito
	 * @throws Exception
	 */
	public static Result sendNota(VentaPasaje notaCredito)throws Exception{
		return sendNota(notaCredito, false);
	}
	
	/**
	 * Realiza el envio de la notas de credito
	 * @param notaCredito
	 * @throws Exception
	 */
	public static Result sendNota(VentaPasaje notaCredito,boolean isReemvioBySoporte)throws Exception{
		Result result=null;
		try {
			Nota nota=createNota(notaCredito,isReemvioBySoporte);
			if(nota==null)
				return null;
			result=getSoap().setNota(TOKEN, nota);
			if(result!=null && result.isIsCorrect()){
				/*Actualiza la venta como enviado al servidor de facturacion electronica*/
				VentaPasaje oNotacredito=ServiceLocator.getVentaPasajesManager().buscarPorId(notaCredito.getId());
				oNotacredito.setEnviadoSFE(Constantes.TRUE_VALUE);
				oNotacredito.setFechaEnvioSFE(Constantes.FORMAT_DATE_TIME_24H.parse(ServiceLocator.getVentaPasajesManager().getDateSystem()));
				ServiceLocator.getVentaPasajesManager().actualizar(oNotacredito);
			}else{
				sendMail("Metod sendNota : "+notaCredito.getNumeroBoleto()+" \n"+result.getMessage().getValue());
			}
		} catch (Exception e) {
			e.printStackTrace();
			DlgMessage.error(e.getMessage());
			/*Envia un e-mail con el error*/
			sendMail("Metod sendNota : "+notaCredito.getNumeroBoleto()+" \n "+e.getMessage());
		}
		
		return result;
	}
	
	/**
	 * Realiza le envio de la venta al WebService FE.
	 * @param ventaPasaje : Instancia del Object VentaPasaje
	 * @throws Exception
	 */
	public static Result sendVenta(List<VentaPasaje> listVentaPasaje, Window window, Boolean printTicket, VentaPasaje notaCredito, Integer numPrintCopies)throws Exception{
		return sendVenta(listVentaPasaje, window, printTicket, notaCredito, false, numPrintCopies);
	}
	
	/**
	 * Realiza le envio de la venta al WebService FE.
	 * @param ventaPasaje : Instancia del Object VentaPasaje
	 * @throws Exception
	 */
	public static Result sendVenta(List<VentaPasaje> listVentaPasaje, Window window, Boolean printTicket, VentaPasaje notaCredito, boolean isReemvioBySoporte, Integer numPrintCopies)throws Exception{
		return sendVenta(listVentaPasaje, window, printTicket, notaCredito, isReemvioBySoporte, null, numPrintCopies);
	}
	
	/**
	 * 
	 * @param listVentaPasaje
	 * @param window
	 * @param printTicket
	 * @param notaCredito
	 * @param isReemvioBySoporte
	 * @param listVentasSoloReimpresion
	 * @return
	 * @throws Exception
	 */
	public static Result sendVenta(List<VentaPasaje> listVentaPasaje, Window window, Boolean printTicket, VentaPasaje notaCredito, boolean isReemvioBySoporte, List<VentaPasaje> listVentasSoloReimpresion, Integer numPrintCopies)throws Exception{
		try {
			List<VentaPasaje> ventasEnviadas= new ArrayList<>();
			
			Result result=null;
			for(VentaPasaje ventaPasaje: listVentaPasaje){
				if(ventaPasaje.getCliente()!=null)
					ventaPasaje.setCliente(ServiceLocator.getClienteManager().buscarPorId(ventaPasaje.getCliente().getId()));
				ventaPasaje.getPasajero().setTipoDocumento(ServiceLocator.getTipoDocumentoManager().buscarPorId(ventaPasaje.getPasajero().getTipoDocumento().getId().longValue()));
				ventaPasaje.setUsuario(ServiceLocator.getUsuarioManager().buscarPorId(ventaPasaje.getUsuario().getId().longValue()));
				
				/*##Valida si la venta es operada por otra empresa - 25/10/2016 jabanto*/
				if(!(ventaPasaje.getTipoTransaccion().equals(Constantes.TIPO_OPERACION_VENTA_POOL))){
					/*Tepsa*/
					if(ventaPasaje.getAgenciaPartida()!=null)
						ventaPasaje.setAgenciaPartida(ServiceLocator.getAgenciaManager().buscarPorId(ventaPasaje.getAgenciaPartida().getId().longValue()));
					if(ventaPasaje.getAgenciaLlegada()!=null)
						ventaPasaje.setAgenciaLlegada(ServiceLocator.getAgenciaManager().buscarPorId(ventaPasaje.getAgenciaLlegada().getId().longValue()));				
					ventaPasaje.setTipoMovimiento(ServiceLocator.getTipoMovimientoManager().buscarPorId(ventaPasaje.getTipoMovimiento().getId().longValue()));
				}				
				
				/*crea el objeto venta*/
				Venta oventa=createVenta(ventaPasaje, isReemvioBySoporte);
				if(oventa==null)
					return null;
				
				if(notaCredito!=null){
					/*crea el objeto Nota*/
					Nota nota=createNota(notaCredito,isReemvioBySoporte);
					if(nota==null)
						return null;
					/*Envia la nota la venta a nuestro ws*/
					result= getSoap().setNotaVenta(TOKEN, nota, oventa);
					if(result!=null && result.isIsCorrect()){
						/**Actualiza la nota de credito como enviado al servidor de facturacion electronica*/
						VentaPasaje onotaCredito=ServiceLocator.getVentaPasajesManager().buscarPorId(notaCredito.getId());
						onotaCredito.setEnviadoSFE(Constantes.TRUE_VALUE);
						onotaCredito.setFechaEnvioSFE(Constantes.FORMAT_DATE_TIME_24H.parse(ServiceLocator.getVentaPasajesManager().getDateSystem()));
						ServiceLocator.getVentaPasajesManager().actualizar(onotaCredito);
					}
					notaCredito=null;
				}else{
					/*Envia la venta a nuestro ws*/
					//comentado temporalmento - 05/04/2024 - maoe
					result= getSoap().setVenta(TOKEN, oventa); 
				}
				
				/*Agrega a la lista para la impresion - 06/12/2016 - jabanto*/
//				if(result!=null && result.getBarcode().getValue()!=null){ //comentado temporalmento - 09/09/2021 - jabanto
					ventaPasaje.setResult(result);
					ventasEnviadas.add(ventaPasaje);	
//				}
			}
			
			/**Actualiza la venta como enviado al servidor de facturacion electronica - 09/12/2016 - jabanto*/
			for(VentaPasaje ventaPasaje: ventasEnviadas){
				try {
						VentaPasaje oVentaPasaje=ServiceLocator.getVentaPasajesManager().buscarPorId(ventaPasaje.getId());
						oVentaPasaje.setEnviadoSFE(Constantes.TRUE_VALUE);
						oVentaPasaje.setFechaEnvioSFE(Constantes.FORMAT_DATE_TIME_24H.parse(ServiceLocator.getVentaPasajesManager().getDateSystem()));
						ServiceLocator.getVentaPasajesManager().actualizar(oVentaPasaje);
				} catch (Exception e) {
					e.printStackTrace();
					sendMail("Metod sendVenta|Update enviando S.F.E : "+ventaPasaje.getNumeroBoleto()+"\n"+e.getMessage());
				}
			}
			
			
			//Ventas que solo seran reimprimidas
			if(listVentasSoloReimpresion!=null) {
				for(VentaPasaje ventaPasajeReimpresion: listVentasSoloReimpresion){
					String serie=ventaPasajeReimpresion.getNumeroBoleto().split("-")[0];
					String correlativo=ventaPasajeReimpresion.getNumeroBoleto().split("-")[1];
					String tipoComprobante=(ventaPasajeReimpresion.getTipoComprobante().getId().intValue()==Constantes.ID_TIPCOM_BOLETA_VENTA?FE_TIPCOM_BOLETA:FE_TIPCOM_FACTURA);
					Result resultReimpresion=getSoap().buscarDetalleComprobante(TOKEN, tipoComprobante, serie, correlativo, Constantes.RUC_TRANSMAR);						
					
					if(resultReimpresion.getBarcodeQR().getValue()!=null && resultReimpresion.getBarcodeEmbarque().getValue()!=null){
						ventaPasajeReimpresion.setResult(resultReimpresion);
						ventasEnviadas.add(ventaPasajeReimpresion);
					}else{
						/*Alertar */
						sendMail("Metod reimprimirComprobante : "+ventaPasajeReimpresion.getNumeroBoleto()+" \n"+result.getMessage().getValue());
					}	
				}				
			}
			
			/**Realiza la impresion del Ticket - 09/12/2016 - jabanto*/
			if(printTicket && ventasEnviadas.size()>0 && numPrintCopies !=null){
				/*Crea el objet xmlVentaPasaje, para crear el archivo xml para la impresion del Ticket*/
				XmlVentaPasaje filexmlprint=createXmlVenta(ventasEnviadas, false, numPrintCopies);
				if(filexmlprint!=null)
					descargarFileXml(filexmlprint, window);
			}
			
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			DlgMessage.error(e.getMessage());
			/*Envia un e-mail con el error*/
			sendMail("Metod sendVenta : "+e.getMessage());
			return null;
		}
	}
	
	/*
	 * Busca la representacio impresa del comprobante
	 * @param ventaCarga
	 * @throws Exception
	 */
	public static byte[] representacionImpresa(VentaPasaje ventaPasaje) throws Exception {
		
		String serie=ventaPasaje.getNumeroBoleto().split("-")[0];
		String correlativo=ventaPasaje.getNumeroBoleto().split("-")[1];
		String tipoComprobante= null; //(ventaCarga.getTipoComprobante().getId().intValue()==Constantes.ID_TIPCOM_BOLETA?FE_TIPCOM_BOLETA:FE_TIPCOM_FACTURA);
		if(ventaPasaje.getTipoComprobante().getId().intValue()==Constantes.ID_TIPCOM_BOLETA_VENTA)
			tipoComprobante = FE_TIPCOM_BOLETA;
		else if(ventaPasaje.getTipoComprobante().getId().intValue()==Constantes.ID_TIPCOM_FACTURA)
			tipoComprobante = FE_TIPCOM_FACTURA;
		else if(ventaPasaje.getTipoComprobante().getId().intValue()==Constantes.ID_TIPCOM_NOTA_CREDITO)
			tipoComprobante = FE_TIPCOM_NOTA_CREDITO;
		else 
			tipoComprobante = FE_TIPCOM_NOTA_DEBITO;
		
		Result result= getSoap().getRepresentacionImpresa(TOKEN, false, tipoComprobante, serie, correlativo, Constantes.ruc);
		if(result.isIsCorrect() && result.getPdf().getValue()!=null) {
			return result.getPdf().getValue();			
		}else
			return null;
	}
	
	/**
	 * Realiza la reimpresion del comprobante
	 * @param ventaPasaje
	 * @param window
	 */
	public static void reimprimirComprobante(List<VentaPasaje> listVentaPasaje, Window window, int numCopias){
		try {
			List<VentaPasaje> ventasEnviadasSFE= new ArrayList<>();
			
			/*Busca el comprobante en el Servorde de F.E*/
			for(VentaPasaje ventaPasaje: listVentaPasaje){
				String serie=ventaPasaje.getNumeroBoleto().split("-")[0];
				String correlativo=ventaPasaje.getNumeroBoleto().split("-")[1];
				String tipoComprobante=(ventaPasaje.getTipoComprobante().getId().intValue()==Constantes.ID_TIPCOM_BOLETA_VENTA?FE_TIPCOM_BOLETA:FE_TIPCOM_FACTURA);
				Result result=getSoap().buscarDetalleComprobante(TOKEN, tipoComprobante, serie, correlativo, Constantes.RUC_TRANSMAR);						
				
				if(result.getBarcodeQR().getValue()!=null && result.getBarcodeEmbarque().getValue()!=null){
					ventaPasaje.setResult(result);
					ventasEnviadasSFE.add(ventaPasaje);
				}else{
					/*Alertar */
					sendMail("Metod reimprimirComprobante : "+ventaPasaje.getNumeroBoleto()+" \n"+result.getMessage().getValue());
				}	
			}
			
			/*Crea y descarga el Archivo xml para la impresion*/
			XmlVentaPasaje fileXmlPrint=createXmlVenta(ventasEnviadasSFE, true, numCopias);
			if(fileXmlPrint!=null)
				descargarFileXml(fileXmlPrint, window);
						
		} catch (Exception e) {
			e.printStackTrace();
			DlgMessage.error(e.getMessage());
			/*Envia un e-mail con el error*/
			String numerosComp="";
			for(VentaPasaje ventaPasaje : listVentaPasaje){
				numerosComp+=(numerosComp.trim().length()==0 ?ventaPasaje.getNumeroBoleto():";"+ventaPasaje.getNumeroBoleto());
			}
			sendMail("metod reimprimirComprobante : "+numerosComp+" \n "+e.getMessage());
		}
	}
	
	/**
	 * Realiza la impresion del Ticket de Equipaje
	 * @param listDetalleEquipaje
	 */
	public static void printEquipaje(List<DetalleEquipaje> listDetalleEquipaje, Window window, boolean timerdownload) {
		XmlEquipajes fileXmlEquipajes = null;
		
		try {
			
			fileXmlEquipajes = createXmlEquipaje(listDetalleEquipaje);
			if(fileXmlEquipajes !=null)
				descargarFileXmlEquipaje(fileXmlEquipajes, window, timerdownload);
			
		} catch (Exception e) {
			e.printStackTrace();
			DlgMessage.error(e.getMessage());
			String numerosComp="";
			for(DetalleEquipaje detalleEquipaje : listDetalleEquipaje){
				numerosComp+=(numerosComp.trim().length()==0 ?detalleEquipaje.getTicket():";"+detalleEquipaje.getTicket());
			}
			sendMail("metod printVouchers : "+numerosComp+" \n "+e.getMessage());
		}		
	}
	
	/**
	 * Realiza solamente la impresion del Voucher, OJO este no se envia al Servidor F.E.
	 * @param listVouchers	: Lista de vouchers a imprimir
	 * @param window
	 */
	public static void printVouchers(List<VentaPasaje> listVouchers, Window window, int numCopias){
		XmlVentaPasaje fileXmlPrint;
		try {
			fileXmlPrint = createXmlVenta(listVouchers, false, numCopias);
			if(fileXmlPrint!=null)
				descargarFileXml(fileXmlPrint, window);
			
		} catch (Exception e) {
			e.printStackTrace();
			DlgMessage.error(e.getMessage());
			String numerosComp="";
			for(VentaPasaje ventaPasaje : listVouchers){
				numerosComp+=(numerosComp.trim().length()==0 ?ventaPasaje.getNumeroBoleto():";"+ventaPasaje.getNumeroBoleto());
			}
			sendMail("metod printVouchers : "+numerosComp+" \n "+e.getMessage());
		}
	}
	
	
	/**
	 * Realiza la impresion de la liquidacion, en formato trmico
	 * @param liquidacion
	 */
	public static void printLiquidacion(Liquidacion liquidacion, Window window){
		try {			
			
			List<Liquidacion>list = ServiceLocator.getLiquidacionManager().BuscarEspeciesValoradas(Constantes.FORMAT_DATE.format(liquidacion.getFechaLiquidacion()), liquidacion.getAgencia().getId(), liquidacion.getUsuario().getId());
			Map<String, ResumenComprobante> mapResumen = ServiceLocator.getLiquidacionManager().buscarResumenComprobantes(Constantes.FORMAT_DATE.format(liquidacion.getFechaLiquidacion()), liquidacion.getAgencia().getId(), liquidacion.getUsuario().getId());
			List<XmlItemIngresoVentaLiquidacion> lstItemIngresosVenta= new ArrayList<>();
			Double totalEfectivo= 0.00, totalCredito = 0.00, totalIngresos = 0.00, totalEgresos = 0.00;
			Integer cantidadEfectivo= 0, cantidadCredito = 0;
			for(String key : mapResumen.keySet()) {				
				ResumenComprobante resumen = mapResumen.get(key);
				String strSerie = null;
				String desde = "";
				String hasta = "";
				strSerie = resumen.getIdTipoComprobante()!=Constantes.ID_TIPCOM_GUIA_TRANSPORTISTA?resumen.getSerie():" "+resumen.getSerie().substring(0, 3);
				
				for(Liquidacion especieValorada: list) {
					if(especieValorada.getSerie().equals(strSerie)) {
						desde = especieValorada.getBoletoInicial();
						hasta = especieValorada.getboletoFinal();
						break;
					}
				}				
				
				XmlItemIngresoVentaLiquidacion itemIngresoVentaLiquidacion = new XmlItemIngresoVentaLiquidacion();
				itemIngresoVentaLiquidacion.setV1_comprobante(resumen.getComprobante().toString());
				itemIngresoVentaLiquidacion.setV2_serie(strSerie);
				itemIngresoVentaLiquidacion.setV3_detalle(desde+" - "+hasta);
				itemIngresoVentaLiquidacion.setV4_cantidad(resumen.getCantidad().toString());
				itemIngresoVentaLiquidacion.setV5_monto(resumen.getMonto().toString());
				lstItemIngresosVenta.add(itemIngresoVentaLiquidacion);		
				
				totalEfectivo += resumen.getMonto();
				cantidadEfectivo ++;
				
				if(resumen.getIdTipoComprobante().intValue()==Constantes.ID_TIPCOM_GUIA_TRANSPORTISTA) {
					totalCredito += resumen.getMonto();
					cantidadCredito ++;
				}
			}
			
			/****Carga - resumen especies valoradas*/
			/************************************************/
			TranscarUsuarioPersonal transcarUsuarioPersonal = ServiceLocator.getTranscarWebManager().buscarUsuario(liquidacion.getUsuario().getLogin());
			int agenciaIdCargo = liquidacion.getAgencia().getId();
			String fechaLiquidacion =Constantes.FORMAT_DATE.format(liquidacion.getFechaLiquidacion());
			if(transcarUsuarioPersonal!=null && liquidacion.getAgencia().getCodigo()!=null){
//				agenciaIdCargo = ServiceLocator.getTranscarWebManager().buscarIdAgenciaByCodigoAgenciaPasajes(liquidacion.getAgencia().getId().toString());
				List<Liquidacion>listResumenEspeciesValoradas = ServiceLocator.getTranscarWebManager().buscarLiquidacionTurnoResumenEspVal(transcarUsuarioPersonal.getId(), agenciaIdCargo, fechaLiquidacion, fechaLiquidacion);
				for(Liquidacion _liquidacion: listResumenEspeciesValoradas){
					XmlItemIngresoVentaLiquidacion itemIngresoVentaLiquidacion = new XmlItemIngresoVentaLiquidacion();
					itemIngresoVentaLiquidacion.setV1_comprobante(_liquidacion.getComprobante());
					itemIngresoVentaLiquidacion.setV2_serie(_liquidacion.getSerie()!=null?_liquidacion.getSerie():"");
					itemIngresoVentaLiquidacion.setV3_detalle(_liquidacion.getBoletoInicial()+" - "+_liquidacion.getboletoFinal());
					itemIngresoVentaLiquidacion.setV4_cantidad(_liquidacion.getCantidadBoletos().toString());
					itemIngresoVentaLiquidacion.setV5_monto(_liquidacion.getImporte().toString());
					lstItemIngresosVenta.add(itemIngresoVentaLiquidacion);		
					
					totalEfectivo += _liquidacion.getImporte();
					cantidadEfectivo ++;
					
					if(_liquidacion.getTipoComprobante().getId().intValue()==Constantes.TRANSCARWEB_ID_TIPCOM_PCE) {
						totalCredito += _liquidacion.getImporte();
						cantidadCredito ++;
					}
				}				
			}	
			/************************************************/
				
			totalIngresos += totalEfectivo;
			totalIngresos += totalCredito;
			XmlDetalleLiquidacionIngresoVenta xmlDetalleLiquidacionIngresoVenta= new XmlDetalleLiquidacionIngresoVenta();
			xmlDetalleLiquidacionIngresoVenta.setV1_itemIngresoVentaLiquidacion(lstItemIngresosVenta);
			
			XmlLiquidacion xmlLiquidacion= new XmlLiquidacion();
			xmlLiquidacion.setV1_fecha(Constantes.FORMAT_DATE.format(liquidacion.getFechaLiquidacion()));
			xmlLiquidacion.setV2_oficina(liquidacion.getAgencia().toString());
			xmlLiquidacion.setV3_representanteVenta(liquidacion.getUsuario().toString());
			if(liquidacion.getUsuarioModificacion()!=null) {
				Usuario usuarioCierre= ServiceLocator.getUsuarioManager().buscarUsuarioPorLogin(liquidacion.getUsuarioModificacion(), Constantes.VALUE_ACTIVO);
				if(usuarioCierre!=null)
					xmlLiquidacion.setV6_usuarioCierre(usuarioCierre.toString());	
			}
			if(liquidacion.getFechaModificacion()!=null)
				xmlLiquidacion.setV7_fechaHoraCierre(Constantes.FORMAT_LONG.format(liquidacion.getFechaModificacion()));
//			xmlLiquidacion.setV4_ingresoTotalEfectivo(totalEfectivo.toString());
			xmlLiquidacion.setV5_ingresoTotalCredito(totalCredito.toString());
			xmlLiquidacion.setV8_detalleLiquidacionIngresoVenta(xmlDetalleLiquidacionIngresoVenta);			
			
			//***************************************************
			//Otros ingresos
			List<Gasto> lstOtrosIngresos =  ServiceLocator.getGastoManager().obtenerGastosByLiquidacion(Constantes.FORMAT_DATE.format(liquidacion.getFechaLiquidacion()), liquidacion.getAgencia().getId(), liquidacion.getUsuario().getId(), Constantes.TRUE_VALUE, true);
			if(lstOtrosIngresos.size()>0) {
				List<XmlItemOtroIngresoLiquidacion> lstItemOtrosIngresos= new ArrayList<XmlItemOtroIngresoLiquidacion>();
				for(Gasto otrosIngresos: lstOtrosIngresos) {
					XmlItemOtroIngresoLiquidacion itemOtrosIngresos = new XmlItemOtroIngresoLiquidacion();
					itemOtrosIngresos.setV1_concepto(otrosIngresos.getTipoGasto().getDenominacion());
					itemOtrosIngresos.setV2_detalle(otrosIngresos.getObservacion());
					itemOtrosIngresos.setV3_cantidad(otrosIngresos.getCantidad().toString());
					itemOtrosIngresos.setV4_monto(otrosIngresos.getMonto().toString());
					lstItemOtrosIngresos.add(itemOtrosIngresos);
					
					totalIngresos += +otrosIngresos.getMonto();
				}
				xmlLiquidacion.setV90_detalleLiquidacionOtrosIngresos(new XmlDetalleLiquidacionOtrosIngresos(lstItemOtrosIngresos));	
			}
									
			//**************************************************
			//Egresos
			List<Gasto> lstGastos =  ServiceLocator.getGastoManager().obtenerGastosByLiquidacion(Constantes.FORMAT_DATE.format(liquidacion.getFechaLiquidacion()), liquidacion.getAgencia().getId(), liquidacion.getUsuario().getId(), Constantes.FALSE_VALUE, true);
			List<XmlItemEgresoLiquidacion> lstEgresos = new ArrayList<XmlItemEgresoLiquidacion>();
			for(Gasto gasto: lstGastos) {
				XmlItemEgresoLiquidacion itemEgresoLiquidacion= new XmlItemEgresoLiquidacion();
				itemEgresoLiquidacion.setV1_numero(gasto.getNumeroDocumento()!=null?gasto.getNumeroDocumento():"");
				itemEgresoLiquidacion.setV2_tipo(gasto.getTipoGasto()!=null?gasto.getTipoGasto().getDenominacion():"");
				itemEgresoLiquidacion.setV3_detalle(gasto.getObservacion()!=null?gasto.getObservacion():"");
				itemEgresoLiquidacion.setV4_cantidad(gasto.getCantidad().toString());
				itemEgresoLiquidacion.setV5_monto(gasto.getMonto().toString());
				lstEgresos.add(itemEgresoLiquidacion);
				
				totalEgresos += +gasto.getMonto();
			}					
			
			/*Otros Egresos - Pasajes*/ 
			Liquidacion liquidacion2 = ServiceLocator.getLiquidacionManager().buscarRptLiquidacionTurno(Constantes.FORMAT_DATE.format(liquidacion.getFechaLiquidacion()), liquidacion.getAgencia().getId(), liquidacion.getUsuario().getId());
			
			/*Otros Egresos - Carga*/
			if(transcarUsuarioPersonal!=null && liquidacion.getAgencia().getCodigo()!=null){				
				Liquidacion liquidacion2Cargo = ServiceLocator.getTranscarWebManager().buscarLiquidacionTurno(transcarUsuarioPersonal.getId(), agenciaIdCargo, fechaLiquidacion, fechaLiquidacion);
				
//				liquidacion2.setMontoContado(liquidacion2.getMontoContado() + liquidacion2Cargo.getMontoContado());
//				liquidacion2.setCantidadContado(liquidacion2.getCantidadContado() + liquidacion2Cargo.getCantidadContado());
				liquidacion2.setMontoTarjetaVisa(liquidacion2.getMontoTarjetaVisa() + liquidacion2Cargo.getMontoTarjetaVisa());
				liquidacion2.setCantidadTarjetaVisa(liquidacion2.getCantidadTarjetaVisa() + liquidacion2Cargo.getCantidadTarjetaVisa());				
				liquidacion2.setMontoTarjetaMasterCard(liquidacion2.getMontoTarjetaMasterCard() + liquidacion2Cargo.getMontoTarjetaMasterCard());
				liquidacion2.setCantidadTarjetaMasterCard(liquidacion2.getCantidadTarjetaMasterCard() + liquidacion2Cargo.getCantidadTarjetaMasterCard());
				liquidacion2.setMontoNotasCredito(liquidacion2.getMontoNotasCredito() + liquidacion2Cargo.getMontoNotasCredito());
				liquidacion2.setCantidadNotasCredito(liquidacion2.getCantidadNotasCredito() + liquidacion2Cargo.getCantidadNotasCredito());
				liquidacion2.setMontoPCE(liquidacion2.getMontoPCE() + liquidacion2Cargo.getMontoPCE());
				liquidacion2.setCantidadPCE(liquidacion2.getCantidadPCE() + liquidacion2Cargo.getCantidadPCE());
				liquidacion2.setMontoYape(liquidacion2.getMontoYape() + liquidacion2Cargo.getMontoYape());
				liquidacion2.setCantidadYape(liquidacion2.getCantidadYape() + liquidacion2Cargo.getCantidadYape());
			}
			
			Integer cantidadVentaTarjeta=liquidacion2.getCantidadTarjetaVisa()+liquidacion2.getCantidadTarjetaMasterCard()+
										+liquidacion2.getCantidadGastosAdminTarjetaMastercard()+liquidacion2.getCantidadGastosAdminTarjetaVisa();
			Double montoVentaTarjeta=liquidacion2.getMontoTarjetaVisa()+liquidacion2.getMontoTarjetaMasterCard()
									 +liquidacion2.getMontoGastosAdminTarjetaMastercard()+liquidacion2.getMontoGastosAdminTarjetaVisa();
			
			lstEgresos.add(new XmlItemEgresoLiquidacion("DEVOLUCIONES", "", "", liquidacion2.getCantidadDevolucion().toString(), String.valueOf(liquidacion2.getMontoDevolucion())));
			lstEgresos.add(new XmlItemEgresoLiquidacion("VTA. CORTISIA", "", "", liquidacion2.getCantidadcortesia().toString(), String.valueOf(liquidacion2.getMontoCortesia())));
//			lstEgresos.add(new XmlItemEgresoLiquidacion("CREDITO", "", "", cantidadCredito.toString(), totalCredito.toString()));
			lstEgresos.add(new XmlItemEgresoLiquidacion("CREDITO", "", "", "0", "0"));
			lstEgresos.add(new XmlItemEgresoLiquidacion("VTA. TARJETA", "", "", cantidadVentaTarjeta.toString(), montoVentaTarjeta.toString()));
			lstEgresos.add(new XmlItemEgresoLiquidacion("NOTA CREDITO", "", "", liquidacion2.getCantidadNotasCredito().toString(), String.valueOf(liquidacion2.getMontoNotasCredito())));
			lstEgresos.add(new XmlItemEgresoLiquidacion("DEV. TARJETA", "", "", liquidacion2.getCantidadDevolucionTarjeta().toString(), String.valueOf(liquidacion2.getMontoDevolucionTarjeta())));
			lstEgresos.add(new XmlItemEgresoLiquidacion("VENTA PCE", "", "", cantidadCredito.toString(), totalCredito.toString()));
			if(liquidacion2.getCantidadTransferencias()!=null && liquidacion2.getCantidadTransferencias() > 0)
				lstEgresos.add(new XmlItemEgresoLiquidacion("VENTA TRANS.", "", "", liquidacion2.getCantidadTransferencias().toString(), String.valueOf(liquidacion2.getMontoTransferencias())));
			if(liquidacion2.getCantidadYape()!=null && liquidacion2.getCantidadYape() > 0)
				lstEgresos.add(new XmlItemEgresoLiquidacion("VENTA YAPE", "", "", liquidacion2.getCantidadYape().toString(), String.valueOf(liquidacion2.getMontoYape())));
				
			xmlLiquidacion.setV91_detalleLiquidacionEgresos(new XmlDetalleLiquidacionEgresos(lstEgresos));
			
			/*Totaliza los Egresos*/
			totalEgresos += + liquidacion2.getMontoDevolucion();
			totalEgresos += + liquidacion2.getMontoCortesia();
			totalEgresos += + totalCredito;
			totalEgresos += + montoVentaTarjeta;
			totalEgresos += + liquidacion2.getMontoNotasCredito();
			totalEgresos += + liquidacion2.getMontoDevolucionTarjeta();
			totalEgresos += + liquidacion2.getMontoTransferencias();
			totalEgresos += + liquidacion2.getMontoYape();
//			totalEgresos += +liquidacion2.getMontoPCE();
			
			totalEfectivo += - totalEgresos;
			xmlLiquidacion.setV4_ingresoTotalEfectivo(totalEfectivo.toString());
			totalIngresos += - totalCredito;
			/**Totales*/
			xmlLiquidacion.setV5_totalIngresos(totalIngresos.toString());
			xmlLiquidacion.setV5_totalEgresos(totalEgresos.toString());
			//*******************************************
			XmlVentaPasaje xmlVentaPasaje= new XmlVentaPasaje();
			xmlVentaPasaje.setLiquidacion(xmlLiquidacion);
			
			String cryptoRptFormat=null;
			//Encripta en bytes del .rpt;
			String pathRpt=Constantes.DIRECTORY_FORMAT_TICKET+"Liquidacion.rpt";
			Path path = Paths.get(pathRpt);
			byte[] contenido = java.nio.file.Files.readAllBytes(path);
			cryptoRptFormat=new BASE64Encoder().encode(contenido);
			xmlLiquidacion.setZ_rptLiquidacion(cryptoRptFormat);
			
			descargarFileXml(xmlVentaPasaje, window);
		} catch (Exception e) {
			e.printStackTrace();
			DlgMessage.error(e.getMessage());
			sendMail("metod printLiquidacion : \n"+e.getMessage());
		}
	}
	
	/**
	 * Realiza la impresion de la liquidacion de tu entrada
	 * @param listLiquidacion
	 */
	public static void printLiquidacionTuentrada(List<LiquidacionTuentrada> listLiquidacion,Usuario usuario, String fechaOperacion, Window window){		
		try{
			UsuarioHardware usuarioHardware=(UsuarioHardware) Executions.getCurrent().getDesktop().getSession().getAttribute(Constantes.ATRIBUTO_USUARIO_HARDWARE);
			XmlLiquidacionTuentrada xmlLiqTuentrada= new XmlLiquidacionTuentrada();
			if(listLiquidacion!=null){
				for(LiquidacionTuentrada liquidacionTuentrada: listLiquidacion){
					if(liquidacionTuentrada.getTipo().equals("CONTADO")){
						xmlLiqTuentrada.setV3_CantidadEfectivo(liquidacionTuentrada.getCantidad().toString());
						xmlLiqTuentrada.setV4_MontoEfectivo(Util.toNumberFormat(liquidacionTuentrada.getMonto(),0));
					}else if(liquidacionTuentrada.getTipo().equals("TARJETA")){
						xmlLiqTuentrada.setV5_CantidadTarjeta(liquidacionTuentrada.getCantidad().toString());
						xmlLiqTuentrada.setV6_MontoTarjeta(Util.toNumberFormat(liquidacionTuentrada.getMonto(),2));
					}
				}
			}
			
			if(xmlLiqTuentrada.getV3_CantidadEfectivo()==null)
				xmlLiqTuentrada.setV3_CantidadEfectivo("0");
			if(xmlLiqTuentrada.getV4_MontoEfectivo()==null)
				xmlLiqTuentrada.setV4_MontoEfectivo("0.00");
			if(xmlLiqTuentrada.getV5_CantidadTarjeta()==null)
				xmlLiqTuentrada.setV5_CantidadTarjeta("0");
			if(xmlLiqTuentrada.getV6_MontoTarjeta()==null)
				xmlLiqTuentrada.setV6_MontoTarjeta("0.00");
			
			xmlLiqTuentrada.setV1_UsuarioNombres(usuario.toString());
			xmlLiqTuentrada.setV2_UsuarioLogin(usuario.getLogin());
			xmlLiqTuentrada.setV7_FechaOperacion(fechaOperacion);
			
			XmlVentaPasaje fileXmlPrint= new XmlVentaPasaje();
			fileXmlPrint.setConfigPrint(getXmlConfigPrint(usuarioHardware));
			fileXmlPrint.setLiqTuentrada(xmlLiqTuentrada);
			
			descargarFileXml(fileXmlPrint, window);
			
		} catch (Exception e) {
			e.printStackTrace();
			DlgMessage.error(e.getMessage());
			sendMail("metod printLiquidacionTuentrada : \n"+e.getMessage());
		}
	}
	
	
	/**
	 * Descarga el archivo xml para la impresion
	 * @param xmlVentaPasaje	: Instancia de la clase xmlventaPasaje
	 * @param window : Instacia de la venta de donde es invocado el metodo
	 * @param result :
	 */
	@SuppressWarnings("restriction")
	private static byte[] descargarFileXmlEquipaje(XmlEquipajes xmlEquipajes, Window window, boolean timerdownload){
		String nameFile="";
		byte[] filePdfZip = null;
		
		try {
			//Crea el archivo xml
			nameFile="9B900E6PJ-";
			//Determina el numero de copias del archivo de impresion
			int copias = 1;
			nameFile += String.valueOf(copias) + "-";
			
			String directorio="";			
			nameFile+=xmlEquipajes.getEquipaje().get(0).getV1_numero();
			directorio=Constantes.DIRECTORY_BOLETOS;			
						
			String pZipFile=directorio+nameFile+".zip";
			String pathSavedXml=directorio+nameFile;
			
			//Creando un directorio con el nombre del archivo
			File directory=new File(pathSavedXml);
			directory.mkdir();
			pathSavedXml=directory.getAbsolutePath()+Util.separator+nameFile+".xml";
			
			JAXBContext context = JAXBContext.newInstance(XmlEquipajes.class);
			Marshaller marshaller = context.createMarshaller();
			marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
			/**Mostramos el documento XML generado por la salida estandar*/
//			marshaller.marshal(xmlVentaPasaje, System.out);
			FileOutputStream fos = new FileOutputStream(pathSavedXml);
			/**guardamos el objeto serializado en un documento XML*/
			try {
				marshaller.marshal(xmlEquipajes, fos);
				fos.close();
			} catch (Exception e) {
				fos.close();
			}
			
			/*Zipeamos el xml (Basicamente para reducir el tamanio)*/			
			Util.Zippear(pathSavedXml, pZipFile,nameFile);
			final String _pZipFile=pZipFile;
			
			//************************************************************************************
			//Consulta la version de impresi�n configurada para la agencia - jabanto 16/11/2022
			Agencia agencia = (Agencia)Executions.getCurrent().getSession().getAttribute(Constantes.ATRIBUTO_AGENCIA);
			if(UtilFlag.isFormatPrintDownload(agencia.getId())) {
				String nameFileZip = nameFile + ".zip";
				File file= new File(pZipFile);
				byte[] fileXmlZip = java.nio.file.Files.readAllBytes(file.toPath());
								
				filePdfZip =  Printapi.getPrintPdf(fileXmlZip, nameFileZip, Constantes.FORMATO_IMPRESION_TICKET, false);
				if(filePdfZip !=null)
					Filedownload.save(filePdfZip, "multipart/form-data", nameFileZip);	
				
			}else if(UtilFlag.isFormatPrintViewPdf(agencia.getId())) {
				String nameFileZip = nameFile + ".zip";
				File file= new File(pZipFile);
				byte[] fileXmlZip = java.nio.file.Files.readAllBytes(file.toPath());
				filePdfZip =  Printapi.getPrintPdf(fileXmlZip, nameFileZip, Constantes.FORMATO_IMPRESION_TICKET, true);
				if(filePdfZip !=null) {
					String urlViewPdf = UtilFlag.getUrlView_pdf();
					if(urlViewPdf !=null) {
						String crypto = new BASE64Encoder().encode(filePdfZip);
						Executions.getCurrent().sendRedirect(urlViewPdf+"?vl="+crypto, "_blank");	
					}					
				}
			
			}else {
											
				if(timerdownload) {
					Timer _timer=new Timer(3000);
					_timer.addEventListener(Events.ON_TIMER, new EventListener<Event>() {
						@Override
						public void onEvent(Event event) throws Exception {
							/*Descarga el archivo .xml*/
							Filedownload.save(new File(_pZipFile), "application/zip");
						}
					});
					window.appendChild(_timer);
				}else {
					/*Descarga el archivo .xml*/
					Filedownload.save(new File(_pZipFile), "application/zip");	
				}				
			}
			
														
			/*Elimina el file.zip despues de 10 segundos*/			
			Timer timer=new Timer(10000);
			timer.addEventListener(Events.ON_TIMER, new EventListener<Event>() {
				@Override
				public void onEvent(Event event) throws Exception {
					Files.deleteAll(new File(_pZipFile));
				}
			});
			window.appendChild(timer);
			
			return filePdfZip;
		} catch (Exception e) {
			e.printStackTrace();
			DlgMessage.error(e.getMessage());
			/*Envia un e-mail con el error*/
			sendMail("Metod descargarFileXml : "+nameFile+"\n " +e.getMessage());
			return null;
		}
	}
	
	/**
	 * Descarga el archivo xml para la impresion
	 * @param xmlVentaPasaje	: Instancia de la clase xmlventaPasaje
	 * @param window : Instacia de la venta de donde es invocado el metodo
	 * @param result :
	 */
	@SuppressWarnings("restriction")
	private static byte[] descargarFileXml(XmlVentaPasaje xmlVentaPasaje, Window window){
		String nameFile="";
		byte[] filePdfZip = null;
		
		try {
			int formatPrint = 0;
			//Crea el archivo xml
			nameFile="4C608A6BF-";
			//Determina el numero de copias del archivo de impresion
			int copias = 1;
			nameFile += String.valueOf(copias) + "-";
			
			String directorio="";
			if(xmlVentaPasaje.getVenta()!=null){
				nameFile+=xmlVentaPasaje.getVenta().get(0).getV1_NumeroComprobante();
				directorio=Constantes.DIRECTORY_BOLETOS;
				formatPrint = Constantes.FORMATO_IMPRESION_TICKET;
			}else if(xmlVentaPasaje.getLiqTuentrada()!=null){
				DateFormat FORMAT_DATE_TIME_24H = new SimpleDateFormat ("yyyyMMdd HHmmss");
				nameFile+="LIQ-TUENTRADA "+FORMAT_DATE_TIME_24H.format(new Date());
				directorio=Constantes.DIRECTORY_LIQUIDACION; //+nameFile+".zip";
			}else if(xmlVentaPasaje.getLiquidacion()!=null) {
				DateFormat FORMAT_DATE_TIME_24H = new SimpleDateFormat ("yyyyMMdd HHmmss");
				nameFile+="LIQ-TURNO "+FORMAT_DATE_TIME_24H.format(new Date());
				directorio=Constantes.DIRECTORY_LIQUIDACION;
				formatPrint = Constantes.FORMATO_IMPRESION_TICKET;
			}					
			
			String pZipFile=directorio+nameFile+".zip";
			String pathSavedXml=directorio+nameFile;
			
			//Creando un directorio con el nombre del archivo
			File directory=new File(pathSavedXml);
			directory.mkdir();
			pathSavedXml=directory.getAbsolutePath()+Util.separator+nameFile+".xml";
			
			JAXBContext context = JAXBContext.newInstance(XmlVentaPasaje.class);
			Marshaller marshaller = context.createMarshaller();
			marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
			/**Mostramos el documento XML generado por la salida estandar*/
//			marshaller.marshal(xmlVentaPasaje, System.out);
			FileOutputStream fos = new FileOutputStream(pathSavedXml);
			/**guardamos el objeto serializado en un documento XML*/
			try {
				marshaller.marshal(xmlVentaPasaje, fos);
				fos.close();
			} catch (Exception e) {
				fos.close();
			}
			
			/*Zipeamos el xml (Basicamente para reducir el tamanio)*/			
			Util.Zippear(pathSavedXml, pZipFile,nameFile);			

			//************************************************************************************
			//Consulta la version de impresi�n configurada para la agencia - jabanto 16/11/2022
			Agencia agencia = (Agencia)Executions.getCurrent().getSession().getAttribute(Constantes.ATRIBUTO_AGENCIA);
			if(UtilFlag.isFormatPrintDownload(agencia.getId())) {
				String nameFileZip = nameFile + ".zip";
				File file= new File(pZipFile);
				byte[] fileXmlZip = java.nio.file.Files.readAllBytes(file.toPath());
								
				filePdfZip =  Printapi.getPrintPdf(fileXmlZip, nameFileZip, formatPrint, false);
				if(filePdfZip !=null)
					Filedownload.save(filePdfZip, "multipart/form-data", nameFileZip);
				
			}else if(UtilFlag.isFormatPrintViewPdf(agencia.getId())) {
				String nameFileZip = nameFile + ".zip";
				File file= new File(pZipFile);
				byte[] fileXmlZip = java.nio.file.Files.readAllBytes(file.toPath());
				filePdfZip =  Printapi.getPrintPdf(fileXmlZip, nameFileZip, formatPrint, true);
				if(filePdfZip !=null) {
					String urlViewPdf = UtilFlag.getUrlView_pdf();
					if(urlViewPdf !=null) {
						String crypto = new BASE64Encoder().encode(filePdfZip);
						Executions.getCurrent().sendRedirect(urlViewPdf+"?vl="+crypto, "_blank");	
					}					
				}				
			}else {				
				/*Descarga el archivo .xml*/
				Filedownload.save(new File(pZipFile), "application/zip");
			}
			
			
			/*Elimina el file.zip despues de 10 segundos*/
			final String _pZipFile=pZipFile;
			Timer timer=new Timer(10000);
			timer.addEventListener(Events.ON_TIMER, new EventListener<Event>() {
				@Override
				public void onEvent(Event event) throws Exception {
					Files.deleteAll(new File(_pZipFile));
				}
			});
			window.appendChild(timer);
			
			return filePdfZip;
		} catch (Exception e) {
			e.printStackTrace();
			DlgMessage.error(e.getMessage());
			/*Envia un e-mail con el error*/
			sendMail("Metod descargarFileXml : "+nameFile+"\n " +e.getMessage());
			return null;
		}
	}
	
	
	/**
	 * @param numPrintCopies: Indica el numero de copias que se debe imprimir el comprobante
	 * @param listDetalleEquipaje
	 * 
	 * @return
	 */
	private static XmlEquipajes createXmlEquipaje(List<DetalleEquipaje> listDetalleEquipaje) {
		XmlEquipajes xmlEquipajes = null;
		try {
			List<XmlEquipaje> listXmlEquipaje= new ArrayList<>();
			String pathRpt=Constantes.DIRECTORY_FORMAT_TICKET+"Equipaje_TM.rpt";
			Path path = Paths.get(pathRpt);
			byte[] contenido = java.nio.file.Files.readAllBytes(path);
//			@SuppressWarnings("restriction")
			@SuppressWarnings("restriction")
			String cryptoRptFormat=new BASE64Encoder().encode(contenido);
			
			for(int i = 0; i < 2; i++) { //Para duplicar la impresion de Ticket de equipaje - Para el equipaje y el Boleto
				for(DetalleEquipaje detalleEquipaje: listDetalleEquipaje) {
					VentaPasaje ventaPasaje = ServiceLocator.getVentaPasajesManager().buscarPorId(detalleEquipaje.getVentaPasaje().getId());
					
					XmlEquipaje xmlEquipaje = new XmlEquipaje();
					xmlEquipaje.setV1_numero(detalleEquipaje.getTicket());	
					xmlEquipaje.setV2_destino(ventaPasaje.getRuta().getDestino());
					xmlEquipaje.setV3_servicio(ventaPasaje.getServicio().getDenominacion());
					xmlEquipaje.setV4_numeroBoleto(ventaPasaje.getNumeroBoleto());
					xmlEquipaje.setV5_fechaSalida(Constantes.FORMAT_DATE.format(ventaPasaje.getFechaPartida()));
					xmlEquipaje.setV6_horaSalida(ventaPasaje.getHoraPartida());
					xmlEquipaje.setV7_puntoEmbarque(ventaPasaje.getAgenciaPartida().getDenominacion());
					xmlEquipaje.setV8_puntoDesembarque(ventaPasaje.getAgenciaLlegada().getDenominacion());
					xmlEquipaje.setZ_ticket(cryptoRptFormat);
					xmlEquipaje.setZ_CodigoBarra(null);
					
					listXmlEquipaje.add(xmlEquipaje);				
				}
			}			
			
			if(listXmlEquipaje.size()>0) {
				xmlEquipajes = new XmlEquipajes();
				xmlEquipajes.setEquipaje(listXmlEquipaje);
			}			
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return xmlEquipajes;
	}
	
	
	/**
	 * Crea el objeto XmlVentaPasajes para la creacion del xml para la impresion
	 * @param ventaPasaje : Instancia del Object VentaPasaje
	 * @param detalleVenta	: Instancia del Object Venta
	 * @param resultVenta : Instancia del resultado del envio de la venta.
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("restriction")
	private static XmlVentaPasaje createXmlVenta(List<VentaPasaje> listVentaPasaje, boolean isReimpresion, int numPrintCopies)throws Exception{
		try {
			XmlVentaPasaje xmlVentaPasaje= null;
			
			List<XmlVenta> listXmlVenta= new ArrayList<>();
			for(VentaPasaje ventaPasaje: listVentaPasaje){
				int tipoComprobanteId=ventaPasaje.getTipoComprobante().getId();
				if(tipoComprobanteId==Constantes.ID_TIPCOM_BOLETA_VENTA || tipoComprobanteId==Constantes.ID_TIPCOM_FACTURA || tipoComprobanteId==Constantes.ID_TIPCOM_VOUCHER_AGENCIA_VIAJES){
					/*Valida el tipo de comprobante*/
					String cryptoBarcodeEmbarque=null;
					String cryptoBarcodeSunat=null;
					String cryptoRptFormat=null;
					//Valida el tipo de moneda - 25/01/2023 jabanto
					boolean isMonedaSoles = (ventaPasaje.getTipoMoneda()!=null && ventaPasaje.getTipoMoneda().getId().intValue()==DOLARES? false: true);
					if(tipoComprobanteId!=Constantes.ID_TIPCOM_VOUCHER_AGENCIA_VIAJES){
						Result resultVenta=ventaPasaje.getResult();
						if(resultVenta.getBarcodeEmbarque().getValue()!=null)
							cryptoBarcodeEmbarque=new BASE64Encoder().encode(resultVenta.getBarcodeEmbarque().getValue());
						//Encripta el los bytes del codigo de barras - Sunat;

						if(resultVenta.getBarcodeQR().getValue()!=null) {
//							Result resultVenta=ventaPasaje.getResult();
							cryptoBarcodeSunat=new BASE64Encoder().encode(resultVenta.getBarcodeQR().getValue());	
						}						

						//Encripta en bytes del .rpt;
						String pathRpt=null;
						if(tipoComprobanteId==Constantes.ID_TIPCOM_BOLETA_VENTA){
							if(ventaPasaje.getTipoTransaccion().equals(Constantes.TIPO_OPERACION_EXCESO)) {
								pathRpt=Constantes.DIRECTORY_FORMAT_TICKET+"Boleta_exceso.rpt";
							}else {
								if(ventaPasaje.getTipoMovimiento().getId().intValue()==Constantes.ID_TIPMOV_GASTOS_ADMINISTRATIVOS)
									pathRpt=Constantes.DIRECTORY_FORMAT_TICKET+"GABoleta_TM.rpt";
								else
									pathRpt=Constantes.DIRECTORY_FORMAT_TICKET+"Boleta_TM.rpt";	
							}							
						}else{
							if(ventaPasaje.getTipoTransaccion().equals(Constantes.TIPO_OPERACION_EXCESO)) {
								pathRpt=Constantes.DIRECTORY_FORMAT_TICKET+"Factura_exceso.rpt";
							}else {
								if(ventaPasaje.getTipoMovimiento().getId().intValue()==Constantes.ID_TIPMOV_GASTOS_ADMINISTRATIVOS)
									pathRpt=Constantes.DIRECTORY_FORMAT_TICKET+"GAFactura_TM.rpt";
								else
									pathRpt=Constantes.DIRECTORY_FORMAT_TICKET+"Factura_TM.rpt";	
							}							
						}
						
						Path path = Paths.get(pathRpt);
						byte[] contenido = java.nio.file.Files.readAllBytes(path);
						cryptoRptFormat=new BASE64Encoder().encode(contenido);
					}
					
					
					/*Pasajero*/
					XmlPasajero xmlPasajero= new XmlPasajero();
					xmlPasajero.setV1_TipoDocumento(ventaPasaje.getPasajero().getTipoDocumento().getDenominacion());
					xmlPasajero.setV2_NumeroDocumento(ventaPasaje.getPasajero().getNumeroDocumento());
					xmlPasajero.setV3_NombresApellidos(ventaPasaje.getPasajero().toString());
					/*Cliente*/
					XmlCliente xmlCliente= null;
					if(ventaPasaje.getCliente()!=null){
						xmlCliente= new XmlCliente(); 
						xmlCliente.setV1_Ruc(ventaPasaje.getCliente().getNumeroDocumento());
						xmlCliente.setV2_RazonSozial(ventaPasaje.getCliente().toString());
						xmlCliente.setV3_DireccionLegal(ventaPasaje.getCliente().getDireccion());
					}
					/*VentaPasaje*/
					XmlVenta xmlVenta= new XmlVenta();
					xmlVenta.setV0_ExcesoEquipaje(ventaPasaje.getTipoTransaccion().equals(Constantes.TIPO_OPERACION_EXCESO));
					xmlVenta.setV1_NumeroComprobante(ventaPasaje.getNumeroBoleto());
					xmlVenta.setV2_Origen(ventaPasaje.getRuta().getOrigen());
					xmlVenta.setV3_Destino(ventaPasaje.getRuta().getDestino());
					if(ventaPasaje.getTipoTransaccion().equals(Constantes.TIPO_OPERACION_VENTA_POOL)){
						String[] observaciones=ventaPasaje.getObservaciones().split(";");
						xmlVenta.setV4_Embarque(getNamePool(observaciones, "PTOEMB"));
						xmlVenta.setV5_Desembarque(getNamePool(observaciones, "PTODES"));
						xmlVenta.setV0_ObserImport("***SERVICIO OPERADO POR "+getNamePool(ventaPasaje.getObservaciones().split(";"), "OPERADO")+"***");
					}else{
						if(ventaPasaje.getAgenciaPartida()!=null){
							Agencia agenciaPartida=ventaPasaje.getAgenciaPartida();
							if(agenciaPartida.getDireccion()==null)
								agenciaPartida=ServiceLocator.getAgenciaManager().buscarPorId(agenciaPartida.getId().longValue());
							xmlVenta.setV4_Embarque(agenciaPartida.getDireccion());	
						}
						if(ventaPasaje.getAgenciaLlegada()!=null){
							Agencia agenciaLlegada=ventaPasaje.getAgenciaLlegada();
							if(agenciaLlegada.getDireccion()==null)
								agenciaLlegada=ServiceLocator.getAgenciaManager().buscarPorId(agenciaLlegada.getId().longValue());
							xmlVenta.setV5_Desembarque(agenciaLlegada.getDireccion());
						}
						
					}
					xmlVenta.setV6_FechaPartida(ventaPasaje.getFechaPartida()!=null?Constantes.FORMAT_DATE.format(ventaPasaje.getFechaPartida()):null);
//					xmlVenta.setV7_HoraPartida(getHoraRealEmbarque(ventaPasaje));
					xmlVenta.setV7_HoraPartida(ventaPasaje.getHoraPartida());
					xmlVenta.setV8_Asiento(ventaPasaje.getNumeroAsiento()!=null?ventaPasaje.getNumeroAsiento().toString():null);
					xmlVenta.setV90_Piso(ventaPasaje.getNumeroPiso()!=null?ventaPasaje.getNumeroPiso().intValue()<=0?"1":String.valueOf(ventaPasaje.getNumeroPiso()+1):null);		
					xmlVenta.setV91_Pasajero(xmlPasajero);
					xmlVenta.setV92_Cliente(xmlCliente);
					xmlVenta.setV96_OpInafecta("0.00");
					/*Valida si es cortesia*/
					if(ventaPasaje.getFormaPago().getId().intValue()==Constantes.ID_FORPAG_CORTESIA){
						xmlVenta.setV94_OpGratuita(Util.toNumberFormat(ventaPasaje.getImportePagado(),2));
						xmlVenta.setV95_OpExonerada("0.00");
						xmlVenta.setV97_OpGravada("0.00");
						xmlVenta.setV98_Igv("0.00");
						xmlVenta.setV991_ImporteTotalLetras("TRANSFERENCIA GRATUITA DE UN BIEN Y/O SERVICIO PRESTADO GRATUITAMENTE");
						xmlVenta.setV990_ImporteTotal("0.00");
					}else{
						xmlVenta.setV94_OpGratuita("0.00");
						/*Valida si es grabada*/
						if(ventaPasaje.getIgv()!=null && ventaPasaje.getIgv().doubleValue()>0.00){
							xmlVenta.setV98_Igv(Util.toNumberFormat(ventaPasaje.getIgv(),2));
							xmlVenta.setV97_OpGravada(Util.toNumberFormat(ventaPasaje.getImportePagado()-ventaPasaje.getIgv(),2));
							xmlVenta.setV95_OpExonerada("0.00");
						}else{
							/*exonerada*/
							xmlVenta.setV95_OpExonerada(Util.toNumberFormat(ventaPasaje.getImportePagado(),2));
							xmlVenta.setV97_OpGravada("0.00");
							xmlVenta.setV98_Igv("0.00");
						}
						xmlVenta.setV990_ImporteTotal(Util.toNumberFormat(ventaPasaje.getImportePagado(),2));
						xmlVenta.setV991_ImporteTotalLetras(getMontoLetras(ventaPasaje.getImportePagado(), isMonedaSoles));
					}
					if(ventaPasaje.getFormaPago().getId().intValue()==Constantes.ID_FORPAG_CONTADO && ventaPasaje.getTipoFormaPago().getId().intValue()==Constantes.ID_TIPFORPAG_TARJETA){
						OperadorTarjetaCredito operadorTarjetaCredito=ServiceLocator.getOperadorTarjetaCreditoManager().buscarPorId(ventaPasaje.getTarjetaCredito().getOperadorTarjetaCredito().getId().longValue());
						String formaPago="TARJETA " + ventaPasaje.getTarjetaCredito().getDenominacion()+" - "+operadorTarjetaCredito.getDenominacion();
						xmlVenta.setV992_PartPage4("PAGO CON : "+formaPago);
					}else if (ventaPasaje.getFormaPago().getId().intValue()==Constantes.ID_FORPAG_CONTADO)
						xmlVenta.setV992_PartPage4("PAGO CON : "+ventaPasaje.getTipoFormaPago().getDenominacion());
					else if (ventaPasaje.getFormaPago().getId().intValue()==Constantes.ID_FORPAG_CREDITO){
						if(ventaPasaje.getCanalVenta().getId().intValue()==Constantes.ID_CANVEN_AGENCIA_VIAJES ||
								ventaPasaje.getCanalVenta().getId().intValue()==Constantes.ID_CANVEN_WEB)
							xmlVenta.setV992_PartPage4("FORMA PAGO : CONTADO");
						else
							xmlVenta.setV992_PartPage4("FORMA PAGO : CREDITO");
					}else{
						xmlVenta.setV992_PartPage4("***CORTESIA POR "+ventaPasaje.getTipoFormaPago().getDenominacion()+"***");
//						xmlVenta.setV0_ObserImport("***CORTESIA POR "+ventaPasaje.getTipoFormaPago().getDenominacion()+"***");
					}
					
					//Muestra el boleto anterior - jabanto - 05/04/2022
					if(ventaPasaje.getVentaPasaje()!=null){
						VentaPasaje ventaAnt = ServiceLocator.getVentaPasajesManager().buscarPorId(ventaPasaje.getVentaPasaje().getId());
						if(ventaAnt.getTipoTransaccion().equals(Constantes.TIPO_OPERACION_VENTA)){
							if(ventaPasaje.getTipoMovimiento().getAbreviatura()==null){
								TipoMovimiento tipoMovimiento = ServiceLocator.getTipoMovimientoManager().buscarPorId(ventaPasaje.getTipoMovimiento().getId().longValue());
								ventaPasaje.setTipoMovimiento(tipoMovimiento);
							}
							String v0_obserImport = "REF. " + ventaPasaje.getTipoMovimiento().getAbreviatura();
							v0_obserImport += " - " + ventaAnt.getNumeroBoleto();
							v0_obserImport += " - S/ " + Util.toNumberFormat(ventaAnt.getImportePagado(), 2);
							xmlVenta.setV0_ObserImport(v0_obserImport);
						}
					}
					
					//jabanto - 11/08/2022
					if(isReimpresion) {
						String reimpresion = (xmlVenta.getV0_ObserImport()!=null? xmlVenta.getV0_ObserImport() + " - REIMPRESIN" : "REIMPRESIN" );
						xmlVenta.setV0_ObserImport(reimpresion);
					}
						
					
					/*Centro de costo del cliente*/
					if(ventaPasaje.getCentroCosto()!=null)
						xmlVenta.setV992_CentroCosto(ventaPasaje.getCentroCosto().getCodigo()+" - "+ventaPasaje.getCentroCosto().getDenominacion());
					
					/*Numero de comprobante referencial*/
					if(ventaPasaje.getNumeroBoletoAnterior()!=null){
						String at=xmlVenta.getV992_PartPage4();
						xmlVenta.setV992_PartPage4(at+"\n"+"COMP. REF.: "+ventaPasaje.getNumeroBoletoAnterior().toUpperCase());
					}

					if(ventaPasaje.getFechaInsercion()==null)
						ventaPasaje.setFechaInsercion(new Date());
					xmlVenta.setV993_FechaEmision(Constantes.FORMAT_DATE_TIME_24H.format(ventaPasaje.getFechaInsercion()));
					xmlVenta.setV994_AgenciaEmison(ventaPasaje.getAgencia().getDenominacion());
					xmlVenta.setV995_UsuarioEmision(ventaPasaje.getUsuario().getLogin());
					xmlVenta.setZ_CodigoBarraSunat(cryptoBarcodeEmbarque);
					xmlVenta.setZ_QR(cryptoBarcodeSunat);
					//xmlVenta.setZ_CodigoBarraSunat(cryptoBarcodeSunat);
					xmlVenta.setZ_ticket(cryptoRptFormat);
										
					/*Armando el detalle*/
					Boolean isCortesia=ventaPasaje.getFormaPago().getId().intValue()==Constantes.ID_FORPAG_CORTESIA;
					List<XmlItem> xmlItems= new ArrayList<>();
					DetalleVenta detalleVenta=createDetalleVenta(ventaPasaje, isCortesia);
					XmlItem xmlItem= new XmlItem();
					xmlItem.setV1_DetalleServicio(detalleVenta.getDescripcion().getValue());
					xmlItem.setV2_Cantidad(Integer.valueOf(detalleVenta.getCantidad().intValue()).toString());
					xmlItem.setV3_Tarifa(Util.toNumberFormat(detalleVenta.getTarifa(), 2));
					xmlItems.add(xmlItem);
					XmlDetalleVentaPasajes detalleVentaPasajes= new XmlDetalleVentaPasajes();
					detalleVentaPasajes.setItem(xmlItems);
					
					xmlVenta.setV93_DetalleVentaPasajes(detalleVentaPasajes);
					
					listXmlVenta.add(xmlVenta);
				}
			}
			
			if(listXmlVenta.size()>0){
				xmlVentaPasaje= new XmlVentaPasaje();
				xmlVentaPasaje.setVenta(listXmlVenta);
			
				
				
				//Aplica la cantidad de copias que debe imprimir del comprobante
				int copiasAdicionales = (listVentaPasaje.size() * numPrintCopies)- listVentaPasaje.size();
				
				List<XmlVenta> listXmlCopyAdicional = new ArrayList<XmlVenta>();
				for(int x = 0; x < copiasAdicionales; x++) {
					for(XmlVenta xmlVenta: listXmlVenta) {
						XmlVenta oXmlVenta = (XmlVenta) xmlVenta.clone();
						listXmlCopyAdicional.add(oXmlVenta);
					}
				}
				
				for(XmlVenta xmlVenta: listXmlCopyAdicional) {
					xmlVentaPasaje.getVenta().add(xmlVenta);
				}
				
				/*Busca configuracion de impresion - POR AHORA SOLO HABILITADO PARA LA AGENCIA TU ENTRADA*/				
				Agencia agencia=(Agencia)Executions.getCurrent().getDesktop().getSession().getAttribute(Constantes.ATRIBUTO_AGENCIA);
				if(agencia.getId().intValue()==Constantes.ID_AGENCIA_TUENTRADA || agencia.getId().intValue()==Constantes.ID_AGENCIA_SUPERMERCADOS_PERUANOS){
					UsuarioHardware usuarioHardware= (UsuarioHardware)Executions.getCurrent().getDesktop().getSession().getAttribute(Constantes.ATRIBUTO_USUARIO_HARDWARE);
					xmlVentaPasaje.setConfigPrint(getXmlConfigPrint(usuarioHardware));
				}
			}
			
			
			
			
				
			return xmlVentaPasaje;
		} catch (Exception e) {
			e.printStackTrace();
			/*Envia un e-mail con el error*/
			sendMail("Metod createXmlVenta : "+e.getMessage());
			
			return null;
		}
	}
	
	/**
	 * Crea el objeto Venta para enviarla al WebService FE.
	 * @param ventaPasaje	: Instancia del Object VentaPasaje
	 * @return
	 * @throws Exception
	 */
	private static Venta createVenta(VentaPasaje ventaPasaje, boolean isReembioSoporte)throws Exception{
		try {
			String serie=ventaPasaje.getNumeroBoleto().split("-")[0].toString();
			String correlativo=ventaPasaje.getNumeroBoleto().split("-")[1].toString();
//			String fechaEmision=FORMAT_DATE.format(ventaPasaje.getFechaLiquidacion());
			String fechaEmision="";
			if(isReembioSoporte)
				fechaEmision=FORMAT_DATE.format(ventaPasaje.getFechaLiquidacion());
			else{
				Date date=Constantes.FORMAT_DATE_TIME_24H.parse(MyTime.dateTimeServer());
				fechaEmision=FORMAT_DATE.format(date);	
			}
			Boolean isCortesia=ventaPasaje.getFormaPago().getId().intValue()==Constantes.ID_FORPAG_CORTESIA;
			/*Datos del cliente/pasajero*/
			String cliente_tipoDocumentoID=null;
			String cliente_nombres=null;
			String cliente_nroDocumento=null;
			String cliente_direccion=null;
			if(ventaPasaje.getCliente()!=null){
				cliente_tipoDocumentoID=FE_TIPDOC_RUC;
				cliente_nombres=ventaPasaje.getCliente().getRazonSocial();
				cliente_nroDocumento=ventaPasaje.getCliente().getNumeroDocumento();
				cliente_direccion=ventaPasaje.getCliente().getDireccion();
			}else{
				switch (ventaPasaje.getPasajero().getTipoDocumento().getId().intValue()) {
				case Constantes.ID_TIPDOC_DNI:
					cliente_tipoDocumentoID=FE_TIPDOC_DNI;
					break;
				case Constantes.ID_TIPDOC_CARNET_EXTRANJERIA:
					cliente_tipoDocumentoID=FE_TIPDOC_CARNET_EXTRANEJERIA;
					break;
				case Constantes.ID_TIPDOC_PASAPORTE:
					cliente_tipoDocumentoID=FE_TIPDOC_PASAPORTE;
					break;
				case Constantes.ID_TIPDOC_CEDULA_IDENTIDAD:
					cliente_tipoDocumentoID=FE_TIPDOC_CEDULA_DIPLOMATICA_IDENTIDAD;
					break;
				default:
					break;
				}
				cliente_nombres=ventaPasaje.getPasajero().toString();
				cliente_nroDocumento=ventaPasaje.getPasajero().getNumeroDocumento();
			}
			/*Tipo de comprobante*/
			String tipoComprobanteID=null;
			switch (ventaPasaje.getTipoComprobante().getId().intValue()) {
			case Constantes.ID_TIPCOM_BOLETA_VENTA:
				tipoComprobanteID=FE_TIPCOM_BOLETA;
				break;
			case Constantes.ID_TIPCOM_FACTURA:
				tipoComprobanteID=FE_TIPCOM_FACTURA;
				break;
			default:
				break;
			}
				
			Cliente cliente= new Cliente();
			cliente.setNombres(new JAXBElement<String>(new QName(NAMESPACE,"nombres"), String.class, cliente_nombres));
			cliente.setTipoDocumentoID(new JAXBElement<String>(new QName(NAMESPACE,"tipoDocumentoID"), String.class, cliente_tipoDocumentoID));
			cliente.setNumeroDocumento(new JAXBElement<String>(new QName(NAMESPACE,"numeroDocumento"), String.class, cliente_nroDocumento));
			cliente.setDireccion(new JAXBElement<String>(new QName(NAMESPACE,"direccion"), String.class, cliente_direccion));
			
			Venta venta= new Venta();
			venta.setRucEmpresa(new JAXBElement<String>(new QName(NAMESPACE, "rucEmpresa"), String.class, Constantes.RUC_TRANSMAR));
			venta.setTipoComprobanteID(new JAXBElement<String>(new QName(NAMESPACE,"tipoComprobanteID"), String.class, tipoComprobanteID));
			venta.setNumeroSerie(new JAXBElement<String>(new QName(NAMESPACE,"numeroSerie"), String.class, serie));
			venta.setNumeroCorrelativo(new JAXBElement<String>(new QName(NAMESPACE,"numeroCorrelativo"), String.class, autoCompletCorrelativo(correlativo)));
			if(ventaPasaje.getTipoMoneda()!=null && ventaPasaje.getTipoMoneda().getId()==DOLARES)
				venta.setTipoMonedaSoles(false);
			else
				venta.setTipoMonedaSoles(true);
			venta.setFechaEmision(new JAXBElement<String>(new QName(NAMESPACE,"fechaEmision"), String.class, fechaEmision));		
			/*Validando si es una cortesia*/
			if(!(isCortesia)){
				if(ventaPasaje.getIgv()!=null && ventaPasaje.getIgv().doubleValue()>0.00){
					venta.setIgv(ventaPasaje.getIgv());
					venta.setMontoSubTotal(ventaPasaje.getImportePagado()-ventaPasaje.getIgv());
					venta.setMontoTotal(ventaPasaje.getImportePagado());
				}else{
					venta.setMontoTotal(ventaPasaje.getImportePagado());
					venta.setMontoSubTotal(ventaPasaje.getImportePagado());	
				}
			}else{
				venta.setMontoTotal(0.00);
				venta.setIgv(0.00);
				venta.setMontoSubTotal(0.00);
			}
			venta.setMontoTotalDescuento(0.00);
			venta.setCliente(new JAXBElement<Cliente>(new QName(NAMESPACE,"cliente"), Cliente.class, cliente));
			venta.setAgenciaID(ventaPasaje.getAgencia().getId().longValue());
			venta.setUsuarioID(ventaPasaje.getUsuario().getId().longValue());
			venta.setUsuarioInsercion(new JAXBElement<String>(new QName(NAMESPACE,"usuarioInsercion"), String.class, ventaPasaje.getUsuarioInsercion()));
			venta.setUsuarioModificacion(new JAXBElement<String>(new QName(NAMESPACE,"usuarioModificacion"), String.class, ventaPasaje.getUsuarioInsercion()));
			switch (ventaPasaje.getFormaPago().getId().intValue()) {
			case Constantes.ID_FORPAG_CONTADO:
				venta.setTipoVenta(FE_TIPO_VENTA_CONTADO);
				break;
			case Constantes.ID_FORPAG_CREDITO:
				venta.setTipoVenta(FE_TIPO_VENTA_CREDITO);
				break;
			case Constantes.ID_FORPAG_CORTESIA:
				venta.setTipoVenta(FE_TIPO_VENTA_CORTESIA);
				String observaciones="***CORTESIA POR "+ventaPasaje.getTipoFormaPago().getDenominacion()+"***";
				venta.setObservaciones(new JAXBElement<String>(new QName(NAMESPACE,"observaciones"), String.class, observaciones));
				break;
			default:
				venta.setTipoVenta(99);
			}

			/*Solo cuando es operado por otra empresa de transportes*/
			if(ventaPasaje.getTipoTransaccion().equals(Constantes.TIPO_OPERACION_VENTA_POOL)){
				String observaciones="***SERVICIO OPERADO POR "+getNamePool(ventaPasaje.getObservaciones().split(";"), "OPERADO")+"***";
				venta.setObservaciones(new JAXBElement<String>(new QName(NAMESPACE,"observaciones"), String.class, observaciones));
			}
			/*Valida si tiene centro de costo*/
			if(ventaPasaje.getCentroCosto()!=null){
				String centroCosto=ventaPasaje.getCentroCosto().getCodigo()+" - "+ventaPasaje.getCentroCosto().getDenominacion();
				venta.setCentroCosto(new JAXBElement<String>(new QName(NAMESPACE,"centroCosto"), String.class, centroCosto));
			}
			/*Comprobante referencial*/
			if(ventaPasaje.getNumeroBoletoAnterior()!=null){
				DocumentoReferencia documentoReferencia= new DocumentoReferencia();
				documentoReferencia.setNumeroDocumento(new JAXBElement<String>(new QName(NAMESPACE,"NumeroDocumento"), String.class, ventaPasaje.getNumeroBoletoAnterior()));
				venta.setDocumentoReferencia(new JAXBElement<DocumentoReferencia>(new QName(NAMESPACE,"documentoReferencia"), DocumentoReferencia.class, documentoReferencia));
			}
			/*Direccion de embarque*/
			if(ventaPasaje.getTipoTransaccion().equals(Constantes.TIPO_OPERACION_VENTA_POOL)){
				String[] observaciones=ventaPasaje.getObservaciones().split(";");
				String direccionEmbarque=getNamePool(observaciones, "PTOEMB");
				if(direccionEmbarque==null)
					direccionEmbarque="****";
				venta.setDireccionEmbarque(new JAXBElement<String>(new QName(NAMESPACE,"direccionEmbarque"), String.class, direccionEmbarque));
			}else{
				if(ventaPasaje.getAgenciaPartida()!=null){
					Agencia agenciaPartida=ventaPasaje.getAgenciaPartida();
					if(agenciaPartida.getDireccion()==null)
						agenciaPartida=ServiceLocator.getAgenciaManager().buscarPorId(agenciaPartida.getId().longValue());
					venta.setDireccionEmbarque(new JAXBElement<String>(new QName(NAMESPACE,"direccionEmbarque"), String.class, agenciaPartida.getDireccion()));	
				}
			}
			
			//***************Forma de Pago**************** jabanto - 29/07/2022
			venta.setIsCredito(false);
			if(ventaPasaje.getTipoCobranza()!=null) {				
				TipoCobranza tipoCobranza = ServiceLocator.getTipoCobranzaManager().buscarPorId(ventaPasaje.getTipoCobranza().getId().longValue());
				int dias = Integer.valueOf(tipoCobranza.getDenominacion().split(" ")[0]);
				Date fechaVencimiento = new Date(ventaPasaje.getFechaLiquidacion().getTime() + (Constantes.MILISEGUNDOS_X_DIA * dias));
				String strFechaVencimiento = Constantes.FORMAT_DATE.format(fechaVencimiento);
				
				InformacionCredito informacionCredito= new InformacionCredito();
				informacionCredito.setNroCuota(new JAXBElement<String>(new QName(NAMESPACE,"NroCuota"), String.class, "Cuota001"));
				informacionCredito.setMontoCuota(BigDecimal.valueOf(ventaPasaje.getImportePagado()));
				informacionCredito.setFechaVencimiento(new JAXBElement<String>(new QName(NAMESPACE,"FechaVencimiento"), String.class, strFechaVencimiento));
//				venta.setFechaEmision(new JAXBElement<String>(new QName(NAMESPACE,"fechaEmision"), String.class, fechaEmision));
				ArrayOfInformacionCredito arrayOfInformacionCredito = new ArrayOfInformacionCredito();
				arrayOfInformacionCredito.getInformacionCredito().add(informacionCredito);
				venta.setInfoCreditList(new JAXBElement<ArrayOfInformacionCredito>(new QName(NAMESPACE,"InfoCreditList"), ArrayOfInformacionCredito.class,arrayOfInformacionCredito));
				venta.setIsCredito(true);
			}
			
			/*=======================================================*/
			/*DETALLE DE LA VENTA*/
			/*=======================================================*/
//			Double totalOpGratuitas=.00;
			DetalleVenta detalleVenta=createDetalleVenta(ventaPasaje, isCortesia);
			
			ArrayOfDetalleVenta arrayOfDetalleVenta= new ArrayOfDetalleVenta();
			arrayOfDetalleVenta.getDetalleVenta().add(detalleVenta);
			venta.setListDetalleVenta(new JAXBElement<ArrayOfDetalleVenta>(new QName(NAMESPACE,"listDetalleVenta"), ArrayOfDetalleVenta.class,arrayOfDetalleVenta));
			
			/*======================================================*/
	        /*-->Otros conceptos tributarios. (Cat. 14)*/
	        /*======================================================*/
			//Operaciones inafectas
			ArrayOfInformacionAdicionalTotalMonedaAdicional arrayTotalMonedaAdicional= new ArrayOfInformacionAdicionalTotalMonedaAdicional();
			InformacionAdicionalTotalMonedaAdicional totalMonedaAdicional= new InformacionAdicionalTotalMonedaAdicional();
			if(venta.getIgv()!=null && venta.getIgv().doubleValue()>0.00){
				totalMonedaAdicional.setCodigo(new JAXBElement<String>(new QName(NAMESPACE,"codigo"), String.class, "1001")); /*Segun catalogo 14*/
				totalMonedaAdicional.setNombre(new JAXBElement<String>(new QName(NAMESPACE,"nombre"), String.class, "TOTAL VALOR DE VENTA - OPERACIONES GRAVADAS"));
			}else{
				totalMonedaAdicional.setCodigo(new JAXBElement<String>(new QName(NAMESPACE,"codigo"), String.class, "1003")); /*Segun catalogo 14*/
				totalMonedaAdicional.setNombre(new JAXBElement<String>(new QName(NAMESPACE,"nombre"), String.class, "TOTAL VALOR DE VENTA - OPERACIONES EXONERADAS"));
			}
			totalMonedaAdicional.setValor(new JAXBElement<String>(new QName(NAMESPACE,"valor"), String.class, Util.toNumberFormat(venta.getMontoSubTotal(),2))); /*(no incluye impuesto)*/
			arrayTotalMonedaAdicional.getInformacionAdicionalTotalMonedaAdicional().add(totalMonedaAdicional);
			//Si es cortesia
			if(isCortesia){
				totalMonedaAdicional= new InformacionAdicionalTotalMonedaAdicional();
				totalMonedaAdicional.setCodigo(new JAXBElement<String>(new QName(NAMESPACE,"codigo"), String.class, "1004")); /*Segun catalogo 14*/
				totalMonedaAdicional.setNombre(new JAXBElement<String>(new QName(NAMESPACE,"nombre"), String.class, "TOTAL VALOR DE VENTA - OPERACIONES GRATUITAS"));
				totalMonedaAdicional.setValor(new JAXBElement<String>(new QName(NAMESPACE,"valor"), String.class, Util.toNumberFormat(ventaPasaje.getImportePagado(),2)));
				arrayTotalMonedaAdicional.getInformacionAdicionalTotalMonedaAdicional().add(totalMonedaAdicional);
			}
			
			/*========================================================================*/
	        /*-->Elementos adicionales de la Factura y/o Boleta electronica. (Cat. 15)*/
	        /*========================================================================*/
			ArrayOfInformacionAdicionalPropiedadAdicional arrayPropiedadAdicional= new ArrayOfInformacionAdicionalPropiedadAdicional();
			if(!(isCortesia)){
				InformacionAdicionalPropiedadAdicional propiedadAdicional= new InformacionAdicionalPropiedadAdicional();
				propiedadAdicional.setCodigo(new JAXBElement<String>(new QName(NAMESPACE,"codigo"), String.class, "1000")); //Segun catalogo 15 (monto en letras)
				propiedadAdicional.setNombre(new JAXBElement<String>(new QName(NAMESPACE,"nombre"), String.class, "MONTO EN LETRAS"));
				propiedadAdicional.setValue(new JAXBElement<String>(new QName(NAMESPACE,"value"), String.class, getMontoLetras(venta.getMontoTotal(), venta.isTipoMonedaSoles())));
				arrayPropiedadAdicional.getInformacionAdicionalPropiedadAdicional().add(propiedadAdicional);
			}else{
				InformacionAdicionalPropiedadAdicional propiedadAdicional= new InformacionAdicionalPropiedadAdicional();
				propiedadAdicional.setCodigo(new JAXBElement<String>(new QName(NAMESPACE,"codigo"), String.class, "1002")); //Segun catalogo 15 (monto en letras)
				propiedadAdicional.setNombre(new JAXBElement<String>(new QName(NAMESPACE,"nombre"), String.class, "TRANSFERENCIA GRATUITA"));
				propiedadAdicional.setValue(new JAXBElement<String>(new QName(NAMESPACE,"value"), String.class, "TRANSFERENCIA GRATUITA DE UN BIEN Y/O SERVICIO PRESTADO GRATUITAMENTE"));
				arrayPropiedadAdicional.getInformacionAdicionalPropiedadAdicional().add(propiedadAdicional);
			}
			
			InformacionAdicional informacionAdicional= new InformacionAdicional();
			informacionAdicional.setTotalesMonedaAdicional(new JAXBElement<ArrayOfInformacionAdicionalTotalMonedaAdicional>(new QName(NAMESPACE,"TotalesMonedaAdicional"), ArrayOfInformacionAdicionalTotalMonedaAdicional.class, arrayTotalMonedaAdicional));
			informacionAdicional.setPropiedadesAdicionales(new JAXBElement<ArrayOfInformacionAdicionalPropiedadAdicional>(new QName(NAMESPACE,"PropiedadesAdicionales"), ArrayOfInformacionAdicionalPropiedadAdicional.class, arrayPropiedadAdicional));
			
			venta.setInformacionAdicional(new JAXBElement<InformacionAdicional>(new QName(NAMESPACE,"informacionAdicional"), InformacionAdicional.class,informacionAdicional));
			
			return venta;
		} catch (Exception e) {
			e.printStackTrace();
			/*Envia un e-mail con el error*/
			sendMail("Metod createVenta : "+ventaPasaje.getNumeroBoleto()+" \n "+e.getMessage());
			return null;
		}
	}
	
	/**
	 * Busca el valor, segun el nombre, el array observaciones
	 * @param observaciones
	 * @param nameSearch
	 * @return
	 */
	private static String getNamePool(String[] observaciones, String nameSearch){
		String value=null;
		
		for(String observ :observaciones){
			String[] _observ=observ.split(":");
			if(_observ[0].equals(nameSearch)){
				value=_observ[1].toString();
				break;
			}
		}
		
		return value;
	}
	
	/**
	 * crea el detalle de la venta, para el envio al WSFE
	 * @param ventaPasaje
	 * @param isCortesia
	 * @return
	 */
	private static DetalleVenta createDetalleVenta(VentaPasaje ventaPasaje, boolean isCortesia){
		String descripMovi = "";
		if(!(ventaPasaje.getTipoTransaccion().equals(Constantes.TIPO_OPERACION_VENTA_ESPECIAL))) {
			if(ventaPasaje.getTipoMovimiento().getId().intValue()==Constantes.ID_TIPMOV_EFECTIVO)		
				descripMovi = "VTA. PASAJE:";
			else
				descripMovi = ventaPasaje.getTipoMovimiento().getDenominacion().trim();
		}
		
		String pasajero=ventaPasaje.getPasajero().toString().trim();
		if(pasajero.length()>30)
			pasajero=pasajero.substring(0, 30);
		String tipoDocumento=ventaPasaje.getPasajero().getTipoDocumento().getNombreCorto().trim();
		if(tipoDocumento.length()>10)
			tipoDocumento=tipoDocumento.substring(0, 10);
		
		/*la Descripcion del Detalle*/
		String descripcionPrincipal="";
		if(ventaPasaje.getTipoTransaccion().equals(Constantes.TIPO_OPERACION_VENTA_ESPECIAL)) {
			descripcionPrincipal= descripMovi+(ventaPasaje.getObservaciones()!=null?ventaPasaje.getObservaciones():"");
		}else if(ventaPasaje.getTipoTransaccion().equals(Constantes.TIPO_OPERACION_EXCESO)) {
			descripcionPrincipal = ventaPasaje.getObservaciones();
		}else if(ventaPasaje.getTipoMovimiento().getId().intValue()!=Constantes.ID_TIPMOV_GASTOS_ADMINISTRATIVOS){
			String servicio="";
			if(ventaPasaje.getTipoTransaccion().equals(Constantes.TIPO_OPERACION_VENTA_POOL)){
				String[] observaciones=ventaPasaje.getObservaciones().split(";");
				servicio=getNamePool(observaciones, "SERVICIO");
				if(servicio==null)
					servicio="****";
			}else
				servicio=ventaPasaje.getServicio().getDenominacion().trim();
			
			/*Obtiene la hora real del embarque*/
			String strHoraPartida=getHoraRealEmbarque(ventaPasaje);		
			
			descripcionPrincipal= descripMovi+"\n"+
					 "[PAX:"+pasajero+"] ["+tipoDocumento+":"+ventaPasaje.getPasajero().getNumeroDocumento().trim()+"]\n"+
					 "[RUTA:"+ventaPasaje.getRuta().toString().trim()+"]\n"+
					 "[SERV:"+servicio+"]\n"+
					 "[ASIENTO:"+(ventaPasaje.getNumeroAsiento()!=null?ventaPasaje.getNumeroAsiento():"")+"-"+(ventaPasaje.getNumeroPiso()!=null?(ventaPasaje.getNumeroPiso().intValue()<=0?1:ventaPasaje.getNumeroPiso()):"")+"]\n"+
					 "[FECHA:"+(ventaPasaje.getFechaPartida()!=null?Constantes.FORMAT_DATE.format(ventaPasaje.getFechaPartida()):"")+"] [H. EMB:"+strHoraPartida+"]\n"+
					 "[EMBARQUE:"+ (ventaPasaje.getAgenciaPartida()!=null?ventaPasaje.getAgenciaPartida().getDenominacion():"") +"]\n"+
			 		 "[DESEMBARQUE:"+ (ventaPasaje.getAgenciaLlegada()!=null?ventaPasaje.getAgenciaLlegada().getDenominacion():"") +"]";
//					 "[FECHA:"+(ventaPasaje.getFechaPartida()!=null?Constantes.FORMAT_DATE.format(ventaPasaje.getFechaPartida()):"")+"] [HORA:"+(ventaPasaje.getHoraPartida()!=null?ventaPasaje.getHoraPartida().trim():"")+"]";
			
		}else{
			descripcionPrincipal= descripMovi+(ventaPasaje.getObservaciones()!=null?" - "+ventaPasaje.getObservaciones():"");
		}
		
		
		DetalleVenta detalleVenta= new DetalleVenta();
		
		detalleVenta.setItem(1);
		detalleVenta.setUnidadMedida(new JAXBElement<String>(new QName(NAMESPACE,"unidadMedida"), String.class, "NIU"));
		detalleVenta.setDescripcion(new JAXBElement<String>(new QName(NAMESPACE,"descripcion"), String.class, descripcionPrincipal));
		detalleVenta.setCantidad(1.0);
		detalleVenta.setTarifa(ventaPasaje.getImportePagado());		
		if(!(isCortesia)){
			if(ventaPasaje.getIgv()!=null && ventaPasaje.getIgv()>0.00){
				/*Venta grabada*/
				Double igv_x=Constantes.IGV/100; //(0.18)
				Double igv_y=igv_x+1; //(1.18)
				
				detalleVenta.setValorUnitario(Double.valueOf(Util.toNumberFormatNotMiles(detalleVenta.getTarifa() / igv_y,2))); //Precio o tarifa, pero sin igv
				detalleVenta.setIgv(Double.valueOf(Util.toNumberFormatNotMiles((detalleVenta.getTarifa() / igv_y) * igv_x,2))); //Igv del presio unitario
				detalleVenta.setTotal(Double.valueOf(Util.toNumberFormatNotMiles((detalleVenta.getTarifa() / igv_y)*detalleVenta.getCantidad(), 2)));//total de la linea del detalle (Pero sin impuestos)
				detalleVenta.setCodigoAfectacionIgv(new JAXBElement<String>(new QName(NAMESPACE,"codigoAfectacionIgv"), String.class, "10")); //-->Gravado - Operacion onerosa - Afectacion al igv (Cat. 7)
				detalleVenta.setCodigoTipoPrecio(new JAXBElement<String>(new QName(NAMESPACE,"codigoTipoPrecio"), String.class, "01")); // Precio Unitario (incluye Igv) -Tipo de precio de venta unitario (Cat. 16)
			}else{
				/*Venta Exonerada*/
				detalleVenta.setValorUnitario(detalleVenta.getTarifa()); //Precio o tarifa, pero sin igv
				detalleVenta.setIgv(0.00); //No esta afento al IGV
				detalleVenta.setTotal(detalleVenta.getTarifa()*detalleVenta.getCantidad());//total de la linea del detalle (Pero sin impuestos)
				detalleVenta.setCodigoAfectacionIgv(new JAXBElement<String>(new QName(NAMESPACE,"codigoAfectacionIgv"), String.class, "20")); //Exonerado - Operacion Onerosa - Afectacion al igv (Cat. 7)
				detalleVenta.setCodigoTipoPrecio(new JAXBElement<String>(new QName(NAMESPACE,"codigoTipoPrecio"), String.class, "01")); // Precio Unitario (incluye Igv) -Tipo de precio de venta unitario (Cat. 16)
			}			
		}else{
			/*Si es CORTESIA*/
			detalleVenta.setValorUnitario(0.00); //Precio o tarifa, sin igv
			detalleVenta.setIgv(0.00); //No esta afento al IGV
			detalleVenta.setTotal(0.00);//total de la linea del detalle (Pero sin impuestos)
			detalleVenta.setCodigoAfectacionIgv(new JAXBElement<String>(new QName(NAMESPACE,"codigoAfectacionIgv"), String.class, "20")); //Exonerado - Operacion Onerosa - Afectacion al igv (Cat. 7)
			detalleVenta.setCodigoTipoPrecio(new JAXBElement<String>(new QName(NAMESPACE,"codigoTipoPrecio"), String.class, "02")); // Valor referencial unitario en operaciones no onerosas - Tipo de precio de venta unitario (Cat. 16)
		}
		return detalleVenta;
	}
	
	
	private static Nota createNota(VentaPasaje notaCredito, boolean isReembioSoporte){
		try {
			
			//Valida el tipo de moneda - 25/01/2023 jabanto
			boolean isMonedaSoles = (notaCredito.getTipoMoneda()!=null && notaCredito.getTipoMoneda().getId().intValue()==DOLARES? false: true);
			
			/*Datos del cliente/pasajero*/
			String cliente_tipoDocumentoID=null;
			String cliente_nombres=null;
			String cliente_nroDocumento=null;
			String cliente_direccion=null;
			if(notaCredito.getCliente()!=null){
				cliente_tipoDocumentoID=FE_TIPDOC_RUC;
				cliente_nombres=notaCredito.getCliente().getRazonSocial();
				cliente_nroDocumento=notaCredito.getCliente().getNumeroDocumento();
				cliente_direccion=notaCredito.getCliente().getDireccion();
			}else{
				switch (notaCredito.getPasajero().getTipoDocumento().getId().intValue()) {
				case Constantes.ID_TIPDOC_DNI:
					cliente_tipoDocumentoID=FE_TIPDOC_DNI;
					break;
				case Constantes.ID_TIPDOC_CARNET_EXTRANJERIA:
					cliente_tipoDocumentoID=FE_TIPDOC_CARNET_EXTRANEJERIA;
					break;
				case Constantes.ID_TIPDOC_PASAPORTE:
					cliente_tipoDocumentoID=FE_TIPDOC_PASAPORTE;
					break;
				case Constantes.ID_TIPDOC_CEDULA_IDENTIDAD:
					cliente_tipoDocumentoID=FE_TIPDOC_CEDULA_DIPLOMATICA_IDENTIDAD;
					break;
				default:
					break;
				}
				cliente_nombres=notaCredito.getPasajero().toString();
				cliente_nroDocumento=notaCredito.getPasajero().getNumeroDocumento();
			}
			/*Instancia el cliente*/
			Cliente cliente=new Cliente();
			cliente.setNombres(new JAXBElement<String>(new QName(NAMESPACE,"nombres"), String.class, cliente_nombres));
			cliente.setTipoDocumentoID(new JAXBElement<String>(new QName(NAMESPACE,"tipoDocumentoID"), String.class, cliente_tipoDocumentoID));
			cliente.setNumeroDocumento(new JAXBElement<String>(new QName(NAMESPACE,"numeroDocumento"), String.class, cliente_nroDocumento));
			cliente.setDireccion(new JAXBElement<String>(new QName(NAMESPACE,"direccion"), String.class, cliente_direccion));
			
			/*Instancia el documento al que se le aplica la nota*/			
			VentaPasaje ventaRef=notaCredito.getVentaPasaje();
			if(ventaRef.getTipoComprobante()==null)
				ventaRef=ServiceLocator.getVentaPasajesManager().buscarPorId(ventaRef.getId());
			String tipoComprobanteRef=(ventaRef.getTipoComprobante().getId().intValue()==Constantes.ID_TIPCOM_BOLETA_VENTA?FE_TIPCOM_BOLETA:FE_TIPCOM_FACTURA);
			DocumentoReferencia documentoReferencia= new DocumentoReferencia();
			documentoReferencia.setNumeroDocumento(new JAXBElement<String>(new QName(NAMESPACE, "NumeroDocumento"), String.class, ventaRef.getNumeroBoleto()));
			documentoReferencia.setTipoComprobante(new JAXBElement<String>(new QName(NAMESPACE, "TipoComprobante"), String.class,tipoComprobanteRef));
			documentoReferencia.setFechaDocumento(new JAXBElement<String>(new QName(NAMESPACE, "FechaDocumento"), String.class,FORMAT_DATE.format(ventaRef.getFechaLiquidacion())));
			
			
//			Date date=Constantes.FORMAT_DATE_TIME_24H.parse(MyTime.dateTimeServer());
			String fechaEmision= ""; //FORMAT_DATE.format(date);
			if(isReembioSoporte)
				fechaEmision=FORMAT_DATE.format(notaCredito.getFechaLiquidacion());
			else{
				Date date=Constantes.FORMAT_DATE_TIME_24H.parse(MyTime.dateTimeServer());
				fechaEmision=FORMAT_DATE.format(date);	
			}
			
			
			/*Instancia la nota*/
			Nota nota=new Nota();
			nota.setRucEmpresa(new JAXBElement<String>(new QName(NAMESPACE, "rucEmpresa"), String.class, Constantes.RUC_TRANSMAR));
			nota.setCliente(new JAXBElement<Cliente>(new QName(NAMESPACE,"cliente"), Cliente.class, cliente));
			nota.setDocumentoReferencia(new JAXBElement<DocumentoReferencia>(new QName(NAMESPACE, "documentoReferencia"), DocumentoReferencia.class,documentoReferencia));
			nota.setNumeroSerie(new JAXBElement<String>(new QName(NAMESPACE, "numeroSerie"), String.class,notaCredito.getNumeroBoleto().split("-")[0]));
			nota.setNumeroCorrelativo(new JAXBElement<String>(new QName(NAMESPACE, "numeroCorrelativo"), String.class,notaCredito.getNumeroBoleto().split("-")[1]));
			nota.setFechaEmision(new JAXBElement<String>(new QName(NAMESPACE, "fechaEmision"), String.class,fechaEmision));
			nota.setCodigoTipoNota(new JAXBElement<String>(new QName(NAMESPACE, "codigoTipoNota"), String.class,notaCredito.getTipoNota().getCodigoEquivalenteSunat()));
			nota.setDescripcionTipoNota(new JAXBElement<String>(new QName(NAMESPACE, "descripcionTipoNota"), String.class,notaCredito.getTipoNota().getEquivalenteSunat()));
			nota.setTipoComprobanteID(new JAXBElement<String>(new QName(NAMESPACE, "tipoComprobanteID"), String.class,(notaCredito.getTipoComprobante().getId().intValue()==Constantes.ID_TIPCOM_NOTA_CREDITO?FE_TIPCOM_NOTA_CREDITO:FE_TIPCOM_NOTA_DEBITO)));
			nota.setDescripcionSustento(new JAXBElement<String>(new QName(NAMESPACE, "descripcionSustento"), String.class,notaCredito.getTipoNota().getMovimiento()));
			nota.setIgv(notaCredito.getIgv()!=null && notaCredito.getIgv()>0.00? notaCredito.getIgv():.00);
			nota.setTotal(notaCredito.getImportePagado());
			nota.setSubtotal(nota.getTotal()-nota.getIgv());
			nota.setTipoMonedaSoles(true);
			nota.setAgenciaID(notaCredito.getAgencia().getId().longValue());
			nota.setUsuarioID(notaCredito.getUsuario().getId().longValue());
			nota.setUsuarioInsercion(new JAXBElement<String>(new QName(NAMESPACE, "usuarioInsercion"), String.class,notaCredito.getUsuarioInsercion()));
			nota.setUsuarioModificacion(new JAXBElement<String>(new QName(NAMESPACE, "usuarioModificacion"), String.class,notaCredito.getUsuarioModificacion()));
			switch (ventaRef.getFormaPago().getId().intValue()) {
				case Constantes.ID_FORPAG_CONTADO:
					nota.setTipoVenta(FE_TIPO_VENTA_CONTADO);
					break;
				case Constantes.ID_FORPAG_CREDITO:
					nota.setTipoVenta(FE_TIPO_VENTA_CREDITO);
					break;
				case Constantes.ID_FORPAG_CORTESIA:
					nota.setTipoVenta(FE_TIPO_VENTA_CORTESIA);
					break;
				default:
					nota.setTipoVenta(99);
			}
			
			//-->Otros conceptos tributarios. (Cat. 14)
			ArrayOfInformacionAdicionalTotalMonedaAdicional arrayTotalMonedaAdicional= new ArrayOfInformacionAdicionalTotalMonedaAdicional();
			InformacionAdicionalTotalMonedaAdicional totalMonedaAdicional= new InformacionAdicionalTotalMonedaAdicional();
			if(nota.getIgv()!=null && nota.getIgv().doubleValue()>0.00){
				totalMonedaAdicional.setCodigo(new JAXBElement<String>(new QName(NAMESPACE,"codigo"), String.class, "1001")); /*Segun catalogo 14*/
				totalMonedaAdicional.setNombre(new JAXBElement<String>(new QName(NAMESPACE,"nombre"), String.class, "TOTAL VALOR DE VENTA - OPERACIONES GRAVADAS"));
			}else{
				totalMonedaAdicional.setCodigo(new JAXBElement<String>(new QName(NAMESPACE,"codigo"), String.class, "1003")); /*Segun catalogo 14*/
				totalMonedaAdicional.setNombre(new JAXBElement<String>(new QName(NAMESPACE,"nombre"), String.class, "TOTAL VALOR DE VENTA - OPERACIONES EXONERADAS"));
			}
			totalMonedaAdicional.setValor(new JAXBElement<String>(new QName(NAMESPACE,"valor"), String.class, Util.toNumberFormat(nota.getSubtotal(),2))); /*(no incluye impuesto)*/
			arrayTotalMonedaAdicional.getInformacionAdicionalTotalMonedaAdicional().add(totalMonedaAdicional);
			
			//-->Elementos adicionales de la Factura y/o Boleta electronica. (Cat. 15)
			ArrayOfInformacionAdicionalPropiedadAdicional arrayPropiedadAdicional= new ArrayOfInformacionAdicionalPropiedadAdicional();
			InformacionAdicionalPropiedadAdicional propiedadAdicional= new InformacionAdicionalPropiedadAdicional();
			propiedadAdicional.setCodigo(new JAXBElement<String>(new QName(NAMESPACE,"codigo"), String.class, "1000")); //Segun catalogo 15 (monto en letras)
			propiedadAdicional.setNombre(new JAXBElement<String>(new QName(NAMESPACE,"nombre"), String.class, "MONTO EN LETRAS"));
			propiedadAdicional.setValue(new JAXBElement<String>(new QName(NAMESPACE,"value"), String.class, getMontoLetras(nota.getTotal(), isMonedaSoles)));
			arrayPropiedadAdicional.getInformacionAdicionalPropiedadAdicional().add(propiedadAdicional);			
			
			InformacionAdicional informacionAdicional= new InformacionAdicional();
			informacionAdicional.setTotalesMonedaAdicional(new JAXBElement<ArrayOfInformacionAdicionalTotalMonedaAdicional>(new QName(NAMESPACE,"TotalesMonedaAdicional"), ArrayOfInformacionAdicionalTotalMonedaAdicional.class, arrayTotalMonedaAdicional));
			informacionAdicional.setPropiedadesAdicionales(new JAXBElement<ArrayOfInformacionAdicionalPropiedadAdicional>(new QName(NAMESPACE,"PropiedadesAdicionales"), ArrayOfInformacionAdicionalPropiedadAdicional.class, arrayPropiedadAdicional));
			
			nota.setInformacionAdicional(new JAXBElement<InformacionAdicional>(new QName(NAMESPACE,"informacionAdicional"), InformacionAdicional.class,informacionAdicional));
			
			
			return nota;
		} catch (Exception e) {
			e.printStackTrace();
			/*Envia un e-mail con el error*/
			sendMail("Metod createNota : "+notaCredito.getNumeroBoleto()+" \n"+e.getMessage());
			return null;
		}
	}
	
	
	
	private static String getMontoLetras(Double importe, boolean isSoles)throws Exception{
		/*Monto en letras*/
		String strImportePagado = Util.toNumberFormat(importe, 2);
		int indice = strImportePagado.lastIndexOf(".");
		ConvertirNumeroString num = new ConvertirNumeroString();
		String strEnLetras = num.convertirLetras(importe.intValue()).toUpperCase()+" CON " + strImportePagado.substring(indice+1) + "/100 ";
		strEnLetras += (isSoles?"SOLES":"DOLARES AMERICANOS");
		
		return strEnLetras;
	}
	
	/**
	 * Completa el numero de digitos del correlativo a 8 digitos.
	 * @param correlativo	: Nmere de correlativo.
	 * @return	Correlativo formateado a 8 digitos.
	 * @throws Exception
	 */
	private static String autoCompletCorrelativo(String correlativo)throws Exception{
		String _correlativo= "00000000" + correlativo;
		_correlativo = _correlativo.substring(correlativo.toString().length(), _correlativo.length());

    	return _correlativo;
	}
	
	
	public static void sendMail(String message, String title){
		DestinatariosEmails window= new DestinatariosEmails();
		window.setEmails("TO:marco.oscco@itsb.pe");
		try {
			Sendmail.enviaEmail(message, (title==null?"Error FE.":title), window);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private static void sendMail(String message){
		sendMail(message,null);
	}
	
	public static double redondearDecimales(double valorInicial, int numeroDecimales) {
        double parteEntera, resultado;
        resultado = valorInicial;
        parteEntera = Math.floor(resultado);
        resultado=(resultado-parteEntera)*Math.pow(10, numeroDecimales);
        resultado=Math.round(resultado);
        resultado=(resultado/Math.pow(10, numeroDecimales))+parteEntera;
        return resultado;
    }
	
	/**
	 * Obtiene la hora real del embarque del pasajero
	 * @param ventaPasaje : Intancia de la clase VentaPasaje
	 * @return
	 */
	private static String getHoraRealEmbarque(VentaPasaje ventaPasaje){
		/*Obtiene la hora real de embarque del pasajero*/
		String horaRealEmbarque=null;
		if(ventaPasaje.getFechaPartida()==null)
			horaRealEmbarque = "";
		else{
			if(ventaPasaje.getItinerario()!=null)
				horaRealEmbarque = obtenerHoraEmbarque(ventaPasaje.getItinerario().getId(), ventaPasaje.getAgenciaPartida().getId());
			else
				horaRealEmbarque=ventaPasaje.getHoraEmbarque();
		}
		String strHoraPartida = (horaRealEmbarque==null?ventaPasaje.getHoraPartida():horaRealEmbarque);
		
		return strHoraPartida;
	}
	
	/**
	 * Busca la hora de embarque segun el itinerario y agencia de partida.
	 * @param idItinerario	: Identificador del Itienrario
	 * @param idAgencia     : Identificador de la agencia de partida. 
	 * @return
	 */
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
	
	/**
	 * Obtiene la configuracion de la impresora
	 * @param usuarioHardware
	 * @return
	 */
	private static XmlConfigPrint getXmlConfigPrint(UsuarioHardware usuarioHardware){
		XmlConfigPrint xmlConfigPrint=null;
		try {
			ConfiguracionImpresora configuracionImpresora = UtilData.getConfiguracionImpresora(usuarioHardware.getId());
			if(configuracionImpresora!=null){
				xmlConfigPrint= new XmlConfigPrint();
				xmlConfigPrint.setV1_EsTuentrada("true");
				xmlConfigPrint.setV2_NombreImpresora(configuracionImpresora.getImpresora());
				xmlConfigPrint.setV3_Puerto(configuracionImpresora.getPuerto()!=null?configuracionImpresora.getPuerto():null);
				xmlConfigPrint.setV4_BistBySegundo(configuracionImpresora.getBistPorSegundo()!=null?configuracionImpresora.getBistPorSegundo().toString():null);
				xmlConfigPrint.setV5_BistDatos(configuracionImpresora.getBistDatos()!=null?configuracionImpresora.getBistDatos().toString():null);						
				xmlConfigPrint.setV6_Paridad(configuracionImpresora.getParidad()!=null?configuracionImpresora.getParidad().toString():null);
				xmlConfigPrint.setV7_BistParada(configuracionImpresora.getBistParada()!=null?configuracionImpresora.getBistParada().toString():null);
			}
		} catch (Exception e) {
			e.printStackTrace();
			/*Envia un e-mail con el error*/
			sendMail("Metod getXmlConfigPrint : "+e.getMessage());
		}
		return xmlConfigPrint;
	}
	
}
