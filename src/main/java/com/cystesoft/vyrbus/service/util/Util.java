/**
 * Proyecto		: SISVYR
 * Sistema		: Sistema de Ventas y Reservas
 * Descripción	: Clase que centraliza las funciones utilitarias que se usa en todo el sistema. 
 * Autor		: José Sullo Avalos
 * Fecha		: 08/04/2012
 */
package com.cystesoft.vyrbus.service.util;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;
import java.util.Random;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import org.apache.log4j.Logger;
import org.zkoss.util.media.Media;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zul.Button;
import org.zkoss.zul.Checkbox;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Comboitem;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.Doublebox;
import org.zkoss.zul.Filedownload;
import org.zkoss.zul.Grid;
import org.zkoss.zul.Image;
import org.zkoss.zul.Intbox;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listhead;
import org.zkoss.zul.Listheader;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Longbox;
import org.zkoss.zul.Rows;
import org.zkoss.zul.Spinner;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Timebox;
import org.zkoss.zul.Tree;

import pe.gob.mtc.wshr.ResultIdentidad;

import com.cystesoft.vyrbus.model.bean.Agencia;
import com.cystesoft.vyrbus.model.bean.AutorizadorCortesia;
import com.cystesoft.vyrbus.model.bean.Bus;
import com.cystesoft.vyrbus.model.bean.CanalVenta;
import com.cystesoft.vyrbus.model.bean.Cliente;
import com.cystesoft.vyrbus.model.bean.Concesionario;
import com.cystesoft.vyrbus.model.bean.DestinatariosEmails;
import com.cystesoft.vyrbus.model.bean.DetalleItinerario;
import com.cystesoft.vyrbus.model.bean.EspecieValorada;
import com.cystesoft.vyrbus.model.bean.EstadoCivil;
import com.cystesoft.vyrbus.model.bean.EstadoDocumentoBus;
import com.cystesoft.vyrbus.model.bean.FormaPago;
import com.cystesoft.vyrbus.model.bean.GrupoMantenimiento;
import com.cystesoft.vyrbus.model.bean.Itinerario;
import com.cystesoft.vyrbus.model.bean.Localidad;
import com.cystesoft.vyrbus.model.bean.MotivoCortesia;
import com.cystesoft.vyrbus.model.bean.Nacionalidad;
import com.cystesoft.vyrbus.model.bean.NumeroFlota;
import com.cystesoft.vyrbus.model.bean.OpcionMenu;
import com.cystesoft.vyrbus.model.bean.OperadorTarjetaCredito;
import com.cystesoft.vyrbus.model.bean.Parametros;
import com.cystesoft.vyrbus.model.bean.Personal;
import com.cystesoft.vyrbus.model.bean.PreferenciaAlimentaria;
import com.cystesoft.vyrbus.model.bean.Reniec;
import com.cystesoft.vyrbus.model.bean.Rol;
import com.cystesoft.vyrbus.model.bean.Ruta;
import com.cystesoft.vyrbus.model.bean.Servicio;
import com.cystesoft.vyrbus.model.bean.Sexo;
import com.cystesoft.vyrbus.model.bean.TarjetaCredito;
import com.cystesoft.vyrbus.model.bean.TipoAgencia;
import com.cystesoft.vyrbus.model.bean.TipoCambio;
import com.cystesoft.vyrbus.model.bean.TipoCentroCosto;
import com.cystesoft.vyrbus.model.bean.TipoCobranza;
import com.cystesoft.vyrbus.model.bean.TipoComprobante;
import com.cystesoft.vyrbus.model.bean.TipoDocumento;
import com.cystesoft.vyrbus.model.bean.TipoFlota;
import com.cystesoft.vyrbus.model.bean.TipoFormaPago;
import com.cystesoft.vyrbus.model.bean.TipoGasto;
import com.cystesoft.vyrbus.model.bean.TipoItinerario;
import com.cystesoft.vyrbus.model.bean.TipoMoneda;
import com.cystesoft.vyrbus.model.bean.TipoPersonal;
import com.cystesoft.vyrbus.model.bean.TipoVia;
import com.cystesoft.vyrbus.model.bean.TipoZona;
import com.cystesoft.vyrbus.model.bean.Usuario;
import com.cystesoft.vyrbus.model.bean.UsuarioHardware;
import com.cystesoft.vyrbus.model.bean.VSEstadoCivil;
import com.cystesoft.vyrbus.model.bean.VSSexo;
import com.cystesoft.vyrbus.model.bean.VSTipoDocumento;
import com.cystesoft.vyrbus.model.bean.VentaPasaje;
import com.cystesoft.vyrbus.service.locator.ServiceLocator;
import com.cystesoft.vyrbus.service.mappers.SecuenciaTramo;
import com.cystesoft.vyrbus.view.ui.DlgMessage;

/**
 * Clase que centraliza las funciones utilitarias que se usa en todo el sistema.
 * @author José Sullo Avalos
 * @since JDK1.6
 */
public class Util {
	public static final int OPER_MAYOR = 1;
	public static final int OPER_MENOR = 2;
	public static final int OPER_IGUAL = 3;
	public static final int OPER_MAYOR_IGUAL = 4;
	public static final int OPER_MENOR_IGUAL = 5;
	public static String MD2 = "MD2";
	public static String MD5 = "MD5";
	public static String SHA1 = "SHA-1";
	public static String SHA256 = "SHA-256";
	public static String SHA384 = "SHA-384";
	public static String SHA512 = "SHA-512";
	
	public static final String separator = System.getProperty("file.separator");//Get de system separator
	public static String base = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ@!#$";
	
	public Util() throws Exception{
		super();
	}
	
	/**
	 * Permite llenar un Listbox apartir de un Array de Array de datos de tipo Object
	 * @param listbox
	 * @param lstDatoso
	 */
	public static void llenarListbox(Listbox listbox, ArrayList<Object> lstDatos) {
		llenarListbox(listbox, lstDatos, false, false);	
	}
	/**
	 * Permite llenar un Listbox apartir de un Array de Array de datos de tipo Object
	 * @param listbox
	 * @param lstDatos
	 * @param primeraColumnaComoValorLista <b>true</b> = usa los valores de la primera columna como el value del Listbox
	 */
	public static void llenarListbox(Listbox listbox, ArrayList<Object> lstDatos, boolean primeraColumnaComoValorLista) {
		llenarListbox(listbox, lstDatos, primeraColumnaComoValorLista, false);	
	}
	

	/**
	 * Permite llenar un Listbox apartir de un Array de Array de datos de tipo Object
	 * @param listbox
	 * @param lstDatos
	 * @param primeraColumnaComoValorLista  <b>true</b> = usa los valores de la primera columna como el value del Listbox
	 * @param primeraColumnaComoEncabezado <b>true</b> = usa los valores de la primera fila como encabezado del Listbox
	 */
	@SuppressWarnings("unchecked")
	public static void llenarListbox(Listbox listbox, ArrayList<Object> lstDatos, boolean primeraColumnaComoValorLista, boolean primeraColumnaComoEncabezado) {
		limpiarListbox(listbox);
		limpiarListbox(listbox);	
		for(int f =0; f < lstDatos.size(); f ++) {
			ArrayList<Object> lstFila = (ArrayList<Object>) lstDatos.get(f);
			Listitem oListitem = new Listitem();
			for(int c = 0; c < lstFila.size(); c ++) {
				String valor = (lstFila.get(c) == null ? "" : lstFila.get(c).toString());

				if (primeraColumnaComoValorLista && c == 0)
					oListitem.setValue(valor);
				else
					oListitem.appendChild(new Listcell(valor));
			}
			listbox.appendChild(oListitem);
		}
	}

	/**
	 * Permite Limpiar el contenido de un Listbox
	 * @param listbox
	 */
	public static void limpiarListbox(Listbox listbox) {
		limpiarListbox(listbox, false);
	}
	
	public static void limpiarGrid(Grid grid){
		Rows rows = new Rows();
		grid.getRows().detach();
		grid.appendChild(rows);
	}
	
	/**
	 * Limpia el contenido de un Tree
	 * @param tree
	 */
	public static void limpiarTree(Tree tree){
		if(tree.getTreechildren()!=null){
			for (int f = tree.getTreechildren().getChildren().size() -1; f > -1; f --) {
				Component oComponent = tree.getTreechildren().getChildren().get(f);
				tree.getTreechildren().removeChild(oComponent);
			}
		}
		
	}

	/**
	 * Permite Limpiar el contenido de un Listbox 
	 * @param oListbox
	 * @param quitarEncabezado <b>true</b> = limpiar el Listbox incluyendo el encabezado
	 */
	public static void limpiarListbox(Listbox oListbox, boolean quitarEncabezado) {
		String mold = oListbox.getMold();
		int PageSize = 100;

		if(mold.equals("paging")) {
			PageSize = oListbox.getPageSize();
		}

		oListbox.setMold("default");

		for (int f = oListbox.getChildren().size() -1; f > -1; f --) {
			Component oComponent = oListbox.getChildren().get(f);

			if (oComponent instanceof Listhead) {
				if (quitarEncabezado) {
					oListbox.removeChild(oComponent);
				}
			}
				else {
					oListbox.removeChild(oComponent);
			}
		}

		oListbox.setMold(mold);

		if(mold.equals("paging")) {
			oListbox.setPageSize(PageSize);
		}
	}

	/**
	 * permite limpiar el contenido de un Combobox
	 * @param combobox
	 */
	public static void limpiarCombobox(Combobox combobox) {
		for (int i = (combobox.getItemCount() - 1); i > -1; i -- ) {
			combobox.removeItemAt(i);
		}
	}
	
	/**
	 * Permite seleccionar un Item del Combobox según el valor
	 * @param combobox
	 * @param valorItem
	 */
	public static void seleccionarValorItemCombobox(Combobox combobox, Object valorItem) {
		combobox.setSelectedIndex(-1);
		/*
		Comboitem oComboitem = new Comboitem();
		oComboitem.setValue(valorItem);	
		*/
		for (int i = 0; i < combobox.getItemCount(); i ++) {
			Comboitem oComboitem = combobox.getItemAtIndex(i);
	
			if (oComboitem.getValue()!=null &&  oComboitem.getValue().equals(valorItem)) {
				combobox.setSelectedIndex(i);
				break;
			}
		}
		
	}
	
	/**
	 * Permite seleccionar el Item del combobox segun el valor ID
	 * @param oClass	: Clase para identificar el tipo de valor del combo.
	 * @param combobox	: Objeto combobox a analizar.
	 * @param valorItem	: valor buscado.
	 */
	public static void seleccionarValorItemCombo( Class<?> oClass, Combobox combobox, Object valorItem) {	
		combobox.setSelectedIndex(-1);
		for (int i = 0; i < combobox.getItemCount(); i ++) {
			Comboitem oComboitem = combobox.getItemAtIndex(i);
			
			if (oClass.equals(Agencia.class)){ 			/****AGENCIA****/
				if (oComboitem.getValue()!=null && ((Agencia) oComboitem.getValue()).getId().equals(valorItem)) {
					combobox.setSelectedIndex(i);
					break;
				}
			}else if (oClass.equals(TipoAgencia.class)){ /****TIPO DE AGENCIA****/
				if (oComboitem.getValue()!=null && ((TipoAgencia) oComboitem.getValue()).getId().equals(valorItem)) {
					combobox.setSelectedIndex(i);
					break;
				}
			}else if (oClass.equals(Localidad.class)){ 	/****LOCALIDADES****/
				if (oComboitem.getValue()!=null && ((Localidad) oComboitem.getValue()).getId().equals(valorItem)) {
					combobox.setSelectedIndex(i);
					break;
				}
			}else if (oClass.equals(Concesionario.class)){ /****CONCESIONARIOS****/
				if (oComboitem.getValue()!=null && ((Concesionario) oComboitem.getValue()).getId().equals(valorItem)) {
					combobox.setSelectedIndex(i);
					break;
				}
			}else if (oClass.equals(FormaPago.class)){ 	/****FORMA DE PAGO****/
				if (oComboitem.getValue()!=null && ((FormaPago) oComboitem.getValue()).getId().equals(valorItem)) {
					combobox.setSelectedIndex(i);
					break;
				}
			}else if (oClass.equals(OperadorTarjetaCredito.class)){ /****OPERADOR TARJETA DE CREDITO****/
				if (oComboitem.getValue()!=null && ((OperadorTarjetaCredito) oComboitem.getValue()).getId().equals(valorItem)) {
					combobox.setSelectedIndex(i);
					break;
				}
			}else if (oClass.equals(TipoComprobante.class)){ /****TIPO COMPROBANTE****/
				if (oComboitem.getValue()!=null && ((TipoComprobante) oComboitem.getValue()).getId().equals(valorItem)) {
					combobox.setSelectedIndex(i);
					break;
				}
			}else if (oClass.equals(TipoPersonal.class)){ /****TIPO PERSONAL****/
				if (oComboitem.getValue()!=null && ((TipoPersonal) oComboitem.getValue()).getId().equals(valorItem)) {
					combobox.setSelectedIndex(i);
					break;
				}
			}else if (oClass.equals(TipoDocumento.class)){ /****TIPO DOCUMENTO****/
				if (oComboitem.getValue()!=null && ((TipoDocumento) oComboitem.getValue()).getId().equals(valorItem)) {
					combobox.setSelectedIndex(i);
					break;
				}
			}else if (oClass.equals(TipoItinerario.class)){ /****TIPO DE ITINERARIO****/
				if (oComboitem.getValue() !=null && ((TipoItinerario) oComboitem.getValue()).getId().equals(valorItem)){
					combobox.setSelectedIndex(i);
					break;
				}
				
			}else if (oClass.equals(Sexo.class)){ /****SEXO****/
				if (oComboitem.getValue()!=null && ((Sexo) oComboitem.getValue()).getId().equals(valorItem)) {
					combobox.setSelectedIndex(i);
					break;
				}
			}else if (oClass.equals(Nacionalidad.class)){ /****NACIONALIDAD****/
				if (oComboitem.getValue()!=null && ((Nacionalidad) oComboitem.getValue()).getId().equals(valorItem)) {
					combobox.setSelectedIndex(i);
					break;
				}
			}else if (oClass.equals(EstadoCivil.class)){ /****ESTADO CIVIL****/
				if (oComboitem.getValue()!=null && ((EstadoCivil) oComboitem.getValue()).getId().equals(valorItem)) {
					combobox.setSelectedIndex(i);
					break;
				}
			}else if (oClass.equals(TipoVia.class)){ /****TIPO VIA****/
				if (oComboitem.getValue()!=null && ((TipoVia) oComboitem.getValue()).getId().equals(valorItem)) {
					combobox.setSelectedIndex(i);
					break;
				}
			}else if (oClass.equals(TipoZona.class)){ /****TIPO VIA****/
				if (oComboitem.getValue()!=null && ((TipoZona) oComboitem.getValue()).getId().equals(valorItem)) {
					combobox.setSelectedIndex(i);
					break;
				}
			}else if (oClass.equals(TipoFlota.class)){ /****TIPO FLOTA****/
				if (oComboitem.getValue()!=null && ((TipoFlota) oComboitem.getValue()).getId().equals(valorItem)) {
					combobox.setSelectedIndex(i);
					break;
				}
			}else if (oClass.equals(Servicio.class)){ /****SERVICIO****/
				if (oComboitem.getValue()!=null && ((Servicio) oComboitem.getValue()).getId().equals(valorItem)) {
					combobox.setSelectedIndex(i);
					break;
				}
			}else if (oClass.equals(NumeroFlota.class)){ /****NUMERO FLOTA****/
				if (oComboitem.getValue()!=null && ((NumeroFlota) oComboitem.getValue()).getId().equals(valorItem)) {
					combobox.setSelectedIndex(i);
					break;
				}
			}else if (oClass.equals(GrupoMantenimiento.class)){ /****GRUPO MANTENIMIENTO****/
				if (oComboitem.getValue()!=null && ((GrupoMantenimiento) oComboitem.getValue()).getId().equals(valorItem)) {
					combobox.setSelectedIndex(i);
					break;
				}
			}else if (oClass.equals(Bus.class)){ /****BUS****/
				if (oComboitem.getValue()!=null && ((Bus) oComboitem.getValue()).getId().equals(valorItem)) {
					combobox.setSelectedIndex(i);
					break;
				}
			}else if (oClass.equals(EstadoDocumentoBus.class)){ /****ESTADO DOCUMENTO DEL BUS****/
				if (oComboitem.getValue()!=null && ((EstadoDocumentoBus) oComboitem.getValue()).getId().equals(valorItem)) {
					combobox.setSelectedIndex(i);
					break;
				}
			}else if (oClass.equals(Ruta.class)){ /****RUTA****/
				if (oComboitem.getValue()!=null && ((Ruta) oComboitem.getValue()).getId().equals(valorItem)) {
					combobox.setSelectedIndex(i);
					break;
				}
			}else if (oClass.equals(Personal.class)){ /****PERSONAL****/
				if (oComboitem.getValue()!=null && ((Personal) oComboitem.getValue()).getId().equals(valorItem)) {
					combobox.setSelectedIndex(i);
					break;
				}
			}else if (oClass.equals(UsuarioHardware.class)){ /****USUARIO HARWARE****/
				if (oComboitem.getValue()!=null && ((UsuarioHardware) oComboitem.getValue()).getId().equals(valorItem)) {
					combobox.setSelectedIndex(i);
					
					break;
				}
			}else if (oClass.equals(CanalVenta.class)){ /****CANAL VENTA****/
				if (oComboitem.getValue()!=null && ((CanalVenta) oComboitem.getValue()).getId().equals(valorItem)) {
					combobox.setSelectedIndex(i);
					break;
				}
			}else if (oClass.equals(TipoFormaPago.class)){ /****TIPO FORMA DE PAGO****/
				if (oComboitem.getValue()!=null && ((TipoFormaPago) oComboitem.getValue()).getId().equals(valorItem)) {
					combobox.setSelectedIndex(i);
					break;
				}
			}else if (oClass.equals(MotivoCortesia.class)){ /****MOTIVO CORTECIA****/
				if (oComboitem.getValue()!=null && ((MotivoCortesia) oComboitem.getValue()).getId().equals(valorItem)) {
					combobox.setSelectedIndex(i);
					break;
				}
			}else if (oClass.equals(Rol.class)){ /****ROL****/
				if (oComboitem.getValue()!=null && ((Rol) oComboitem.getValue()).getId().equals(valorItem)) {
					combobox.setSelectedIndex(i);
					break;
				}
			}else if (oClass.equals(Usuario.class)){ /****USUARIO****/
				if (oComboitem.getValue()!=null && ((Usuario) oComboitem.getValue()).getId().equals(valorItem)) {
					combobox.setSelectedIndex(i);
					break;
				}	
			}else if (oClass.equals(OpcionMenu.class)){ /****OPCION MENU****/
				if (oComboitem.getValue()!=null && ((OpcionMenu) oComboitem.getValue()).getId().equals(valorItem)) {
					combobox.setSelectedIndex(i);
					break;
				}	
			}else if (oClass.equals(TipoGasto.class)){ /****TIPO DE GASTO****/
				if (oComboitem.getValue()!=null && ((TipoGasto) oComboitem.getValue()).getId().equals(valorItem)) {
					combobox.setSelectedIndex(i);
					break;
				}	
			}else if (oClass.equals(AutorizadorCortesia.class)){ /****AUTORIZADOR DE CORTESIAS****/
				if (oComboitem.getValue()!=null && ((AutorizadorCortesia) oComboitem.getValue()).getId().equals(valorItem)) {
					combobox.setSelectedIndex(i);
					break;
				}
			}else if (oClass.equals(TipoCobranza.class)){ /****TIPO COBRANZA****/
				if (oComboitem.getValue()!=null && ((TipoCobranza) oComboitem.getValue()).getId().equals(valorItem)) {
					combobox.setSelectedIndex(i);
					break;
				}
			}else if (oClass.equals(EspecieValorada.class)){ /**ESPECIE VALORADA*/
				if (oComboitem.getValue()!=null && ((EspecieValorada)oComboitem.getValue()).getSerie().toString().equals(valorItem)){
					combobox.setSelectedIndex(i);
					break;
				}
			}else if (oClass.equals(PreferenciaAlimentaria.class)){ /**PREFERENCIA ALIMENTARIA*/
				if (oComboitem.getValue()!=null && ((PreferenciaAlimentaria)oComboitem.getValue()).getId().equals(valorItem)){
					combobox.setSelectedIndex(i);
					break;
				}
			}else if (oClass.equals(Cliente.class)){ /**CLIENTE*/
				if (oComboitem.getValue()!=null && ((Cliente)oComboitem.getValue()).getId().equals(valorItem)){
					combobox.setSelectedIndex(i);
					break;
				}	
			}else if(oClass.equals(TarjetaCredito.class)){ /**TARJETA DE CREDIT*/
				if (oComboitem.getValue()!=null && ((TarjetaCredito)oComboitem.getValue()).getId().equals(valorItem)){
					combobox.setSelectedIndex(i);
					break;
				}
			}else if(oClass.equals(TipoCentroCosto.class)){ /**TIPO DE CENTRO DE COSTO*/
				if (oComboitem.getValue()!=null && ((TipoCentroCosto)oComboitem.getValue()).getId().equals(valorItem)){
					combobox.setSelectedIndex(i);
					break;
				}
			}else if (oClass.equals(TipoMoneda.class)){ /**TIPO DE MONEDA*/
				if (oComboitem.getValue()!=null && ((TipoMoneda)oComboitem.getValue()).getId().equals(valorItem)){
					combobox.setSelectedIndex(i);
					break;
				}
				
				
			/** REFERENTES A LA VENTA SEGURO*/	
			}else if (oClass.equals(VSSexo.class)){//SEXO
				if (oComboitem.getValue()!=null && ((VSSexo)oComboitem.getValue()).getId().equals(valorItem)){
					combobox.setSelectedIndex(i);
					break;
				}
			}else if (oClass.equals(VSEstadoCivil.class)){//ESTADO CIVIL
				if (oComboitem.getValue()!=null && ((VSEstadoCivil)oComboitem.getValue()).getId().equals(valorItem)){
					combobox.setSelectedIndex(i);
					break;
				}
			}else if (oClass.equals(VSTipoDocumento.class)){//TIPO DOCUMENTO
				if (oComboitem.getValue()!=null && ((VSTipoDocumento)oComboitem.getValue()).getId().equals(valorItem)){
					combobox.setSelectedIndex(i);
					break;
				}
			}
			
		}
	}	
		
	/**
	 * Convierte un objeto Date s String
	 * @param myDate	: Datos a convertir
	 * @return Objeto String
	 */
	public static String DatetoString(Date myDate, String formato){
		String date="";
		try{
			DateFormat dateFormat=new SimpleDateFormat(formato);
			date=dateFormat.format(myDate);
		}catch(Exception e){
			e.printStackTrace();
		}
		return date;
	}
	
	/**
	 * Convierte un String a Date de acuerdo a un formato
	 * @param text		: Fecha en formatotexto a convertir
	 * @param pattern	: Formato al cual se desea convertir
	 * @return Objeto Date
	 */
	public static Date StringtoDate(String text,String pattern){
		Date date=null;
		try{
			DateFormat dateFormat=new SimpleDateFormat(pattern);
			date=dateFormat.parse(text);
		}catch(Exception e){
			e.printStackTrace();
		}
		return date;
	}
	
	/**
	 * Convierte un String a double con la cantidad de decimales especificada
	 * @param currency		: Valor String a convertir
	 * @param numDecimal	: Numero de decimales
	 * @return double
	 */
	public static double parseNumberFormat(String currency, int numDecimal){
		double number=0.0;
		String decimal = "";
		String pattern = "###,###,##0";
		if(numDecimal>0){
			for(int i=0;i<numDecimal;i++){
				decimal = decimal+"0";
			}
			pattern = pattern+"."+decimal;
		}
		try{
			NumberFormat nf = NumberFormat.getNumberInstance(Locale.ENGLISH);
			DecimalFormat df = (DecimalFormat)nf;
			df.applyPattern(pattern);
			currency=(currency!=null&&!"".equals(currency.trim()))?currency.trim():"0.0"; 
			number=df.parse(currency).doubleValue();
		}catch(Exception e ){
			e.printStackTrace();
		}
		return number;
	}
	
	/**
	 * convierte un double a String con la cantidad de decimales especificados 
	 * @param currency		: Valor double a convertir
	 * @param numDecimales	: Número de decimales 
	 * @return String
	 */
	public static String toNumberFormat(double currency, int numDecimales){
		String number="0.00";
		String decimal = "";
		String pattern = "###,###,##0";
		if(numDecimales>0){
			for(int i=0;i<numDecimales;i++){
				decimal = decimal+"0";
			}
			pattern = pattern+"."+decimal;
		}
			
		try{
			NumberFormat nf = NumberFormat.getNumberInstance(Locale.ENGLISH);
			DecimalFormat df = (DecimalFormat)nf;
			df.applyPattern(pattern);
			number = df.format(currency);
		}catch(Exception e ){
			e.printStackTrace();
		}
		return number;
	}
	
	/**
	 * convierte un double a String con la cantidad de decimales especificados 
	 * @param currency		: Valor double a convertir
	 * @param numDecimales	: Número de decimales 
	 * @return String
	 */
	public static String toNumberFormatNotMiles(double currency, int numDecimales){
		String number="0.00";
		String decimal = "";
		String pattern = "########0";
		if(numDecimales>0){
			for(int i=0;i<numDecimales;i++){
				decimal = decimal+"0";
			}
			pattern = pattern+"."+decimal;
		}
			
		try{
			NumberFormat nf = NumberFormat.getNumberInstance(Locale.ENGLISH);
			DecimalFormat df = (DecimalFormat)nf;
			df.applyPattern(pattern);
			number = df.format(currency);
		}catch(Exception e ){
			e.printStackTrace();
		}
		return number;
	}
	
	/**
	 * Convierte del sistema decimal a hexadecimal
	 * @param valor	: Número a convertir
	 * @return Hexadecimal 
	 */
	public static String decimalToHexadecimal(long valor){
		return Long.toHexString(valor).toUpperCase();
	}
	
	/**
	 * Genera el numero de control para el registro de venta.
	 * @param valor	: Numero hexadecimal con el cual se formara el numero de control.
	 * @return String
	 */
	public static String generateControlNumber(String valor){
		String nControl = "000000000000000";
		nControl = nControl.concat(valor);
		nControl = "T"+ nControl.substring(nControl.length()-15);
		return nControl;
	}
	
	/**
	 * Autocompleta en numero de boleto
	 * @param numeroBoleto	: numero a autocompletar
	 * @return	numero de boleto
	 */
	public static String autocompleNumberBoleto(String numeroBoleto){
		String boleto="0000000";
		int longdig=7;
		if(numeroBoleto.toUpperCase().indexOf("B")>=0 || numeroBoleto.toUpperCase().indexOf("F")>=0){
			boleto="00000000";
			longdig=8;
		}
		
		Integer sep=numeroBoleto.indexOf("-");
		if(sep>=0){
			String serie=numeroBoleto.substring(0,sep);
			String numero=numeroBoleto.substring(sep+1,numeroBoleto.length());
					
			if(numeroBoleto.length()>numero.length()){
				boleto = boleto.concat(numero);
				boleto = serie+"-"+ boleto.substring(boleto.length()-longdig);
			}else{
				boleto=numeroBoleto;
			}
			
			return boleto;
		}else
			return numeroBoleto;
	}
	
	
	/**
	 * cantidad de horas y minutos en milisegundos.
	 * @param cantidadHoras : numero de horas
	 * @return  Long
	 */
	public static Long horasMinutos(Double cantidadHoras){
		Integer hora = cantidadHoras.intValue();
		
		String horas = toNumberFormat(cantidadHoras, 2);
		Integer conPos= horas.indexOf(".");
		Integer minuto = (new Integer (horas.toString().substring(conPos+1, horas.length())));

		Long lHoras= (long) (hora * Constantes.MILISEGUNDOS_X_HORA);
		Long lMinutos = (long) (minuto * Constantes.MILISEGUNDOS_X_MINUTO);			
		Long lHorasMinutos = lHoras + lMinutos;
		
		return lHorasMinutos;
	}
	
	/**
	 * Calcula la edad.
	 * @param fechaNacimiento : fecha de nacimineto
	 * @return : edad
	 * @throws Exception
	 */
	public static Integer calculaEdad(String fechaNacimiento) throws Exception{
		Integer edad=0;
		try {
			if (!(fechaNacimiento==null || fechaNacimiento=="") ){
				Long fechaActual = new Date().getTime(); //.getTime().getTime();
				Long lFechaNacimiento = Constantes.FORMAT_DATE.parse(fechaNacimiento).getTime();
				Long lEdad = fechaActual - lFechaNacimiento;
				edad = (int) (lEdad / (Constantes.MILISEGUNDOS_X_DIA * 365));
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		return edad;
	}
	
	/**
	 * Convierte un Objeto calendar a String
	 * @param myCalendar	: Dato a convertir
	 * @return Un string
	 */
	public static String CalendartoString(Calendar myCalendar, String pattern){
		String date="";
		try{
			DateFormat dateFormat=new SimpleDateFormat(pattern);
			date=dateFormat.format(myCalendar.getTime());
		}catch(Exception e){
			e.printStackTrace();
		}
		return date;
	}
	
	/**
	 * Convierte un objeto String a Calendario
	 * @param fecha	: Datos a convertir
	 * @return Objeto Calendar
	 */
	public static Calendar StringtoCalendar(String fecha, String pattern){
		Calendar calend=Calendar.getInstance();
		try{
			DateFormat dateFormat=new java.text.SimpleDateFormat(pattern);
			Date date=dateFormat.parse(fecha);
			calend.setTime(date);
			if(pattern.equals(Constantes.DATE_TIME_FORMAT)){
				calend.set(Calendar.HOUR_OF_DAY, Integer.valueOf(fecha.substring(11, 13)));
				calend.set(Calendar.MINUTE, Integer.valueOf(fecha.substring(14, 16)));
				calend.set(Calendar.SECOND, Integer.valueOf(fecha.substring(17)));
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return calend;
	}
	
	/**
	 * Compara si un objeto es mayor.
	 * @param min		: Dato que deseamos comparar.
	 * @param sus		: Datos contra el cual se comparara.
	 * @param operador	: Tipo de operador para realizar la comparación.
	 * @return true o false
	 */
	public static boolean comparaFechas(Object min, Object sus, int operador) {
	    Calendar fech1 = Calendar.getInstance();
        Calendar fech2 = Calendar.getInstance();
        Calendar minuendo = Calendar.getInstance();
        Calendar sustraendo = Calendar.getInstance();
        
        if(min instanceof String)
        	minuendo = StringtoCalendar((String)min, Constantes.DATE_FORMAT);
        else if (min instanceof Date)
        	minuendo = StringtoCalendar(DatetoString((Date)min, Constantes.DATE_FORMAT), Constantes.DATE_FORMAT);
        else
        	minuendo = (Calendar)min;
        
        if(sus instanceof String)
        	sustraendo = StringtoCalendar((String)sus, Constantes.DATE_FORMAT);
        else if (sus instanceof Date)
        	sustraendo = StringtoCalendar(DatetoString((Date)sus, Constantes.DATE_FORMAT), Constantes.DATE_FORMAT);
        else
        	sustraendo = (Calendar)sus;
        
        sustraendo.set(Calendar.HOUR_OF_DAY,0);
        sustraendo.set(Calendar.MINUTE,0);
        sustraendo.set(Calendar.SECOND,0);
        
        minuendo.set(Calendar.HOUR_OF_DAY,0);
        minuendo.set(Calendar.MINUTE,0);
        minuendo.set(Calendar.SECOND,0);
        
        fech1.set(Calendar.YEAR, sustraendo.get(Calendar.YEAR));
        fech1.set(Calendar.MONTH, sustraendo.get(Calendar.MONTH));
        fech1.set(Calendar.DAY_OF_MONTH, sustraendo.get(Calendar.DAY_OF_MONTH));
        
        fech2.set(Calendar.YEAR, minuendo.get(Calendar.YEAR));
        fech2.set(Calendar.MONTH, minuendo.get(Calendar.MONTH));
        fech2.set(Calendar.DAY_OF_MONTH, minuendo.get(Calendar.DAY_OF_MONTH));
        
        Date startDate1 = fech1.getTime();
        Date endDate1   = fech2.getTime();
                
        long diff = endDate1.getTime() - startDate1.getTime();
        long dife = (diff / (1000L*60L*60L*24L));
        
        boolean result = false;
        
        switch (operador) {
		case OPER_MAYOR:	//Mayor
			if(dife>0)
				result = true;
			else
				result = false;
			break;
		case OPER_MENOR:	//Menor
			if(dife<0)
				result = true;
			else
				result = false;
			break;
		case OPER_IGUAL:	//Igual
			if(dife==0)
				result = true;
			else
				result = false;
			break;
		case OPER_MAYOR_IGUAL:	//Mayor igual
			if(dife>=0)
				result = true;
			else
				result = false;
			break;
		case OPER_MENOR_IGUAL:	//Menor igual
			if(dife<=0)
				result = true;
			else
				result = false;
			break;		
		}
        
        return result;
	}
	
	/**
	 * Compara si un objeto es mayor.
	 * @param min		: Dato que deseamos comparar.
	 * @param sus		: Datos contra el cual se comparara.
	 * @param operador	: Tipo de operador mara realiza rla comparación.
	 * @return true o false
	 */
	public static boolean comparaFechasWithTime(Object min, Object sus, int operador) {
//	    Calendar fech1 = Calendar.getInstance();
//        Calendar fech2 = Calendar.getInstance();
        Calendar minuendo = Calendar.getInstance();
        Calendar sustraendo = Calendar.getInstance();
        
        if(min instanceof String)
        	minuendo = StringtoCalendar((String)min, Constantes.DATE_TIME_FORMAT);
        else if (min instanceof Date)
        	minuendo = StringtoCalendar(DatetoString((Date)min, Constantes.DATE_TIME_FORMAT), Constantes.DATE_TIME_FORMAT);
        else
        	minuendo = (Calendar)min;
        
        if(sus instanceof String)
        	sustraendo = StringtoCalendar((String)sus, Constantes.DATE_TIME_FORMAT);
        else if (sus instanceof Date)
        	sustraendo = StringtoCalendar(DatetoString((Date)sus, Constantes.DATE_TIME_FORMAT), Constantes.DATE_TIME_FORMAT);
        else
        	sustraendo = (Calendar)sus;
        
        Date startDate1 = sustraendo.getTime();
        Date endDate1   = minuendo.getTime();
                
        long diff = endDate1.getTime() - startDate1.getTime();
        long dife = (diff / (1000L*60L));
        
        boolean result = false;
        
        switch (operador) {
		case OPER_MAYOR:	//Mayor
			if(dife>0)
				result = true;
			else
				result = false;
			break;
		case OPER_MENOR:	//Menor
			if(dife<0)
				result = true;
			else
				result = false;
			break;
		case OPER_IGUAL:	//Igual
			if(dife==0)
				result = true;
			else
				result = false;
			break;
		case OPER_MAYOR_IGUAL:	//Mayor igual
			if(dife>=0)
				result = true;
			else
				result = false;
			break;
		case OPER_MENOR_IGUAL:	//Menor igual
			if(dife<=0)
				result = true;
			else
				result = false;
			break;		
		}
        
        return result;
	}
	
	/**
	 * @return el primer día del mes.
	 * @throws ParseException 
	 */
	@SuppressWarnings("static-access")
	public static Date primerDiaDelMes() throws ParseException {
		MyTime time = new MyTime();
		Calendar ctime=Util.StringtoCalendar(time.dateServer(), "dd/mm/yyyy");
		Calendar cal = Calendar.getInstance();
		
		cal.set(cal.get(Calendar.YEAR),
		cal.get(Calendar.MONTH),
		cal.getActualMinimum(Calendar.DAY_OF_MONTH),
		cal.getMinimum(Calendar.HOUR_OF_DAY),
		cal.getMinimum(Calendar.MINUTE),
		cal.getMinimum(Calendar.SECOND));
		
		return cal.getTime();
	}
	
	/**
	 * calcula el porcentaje de un numero
	 * @param numeroCalculo : número del cual se calcula el porcentaje
	 * @param valorCalculo  : número al que se le aplica el porcentaje
	 * @return porcentaje
	 */
	public static double calculaPorcentaje(Double numeroCalculo, double numeroAplicaPorcentaje){
		Double porcenteje=.00;
//		String resultado=toNumberFormat((numeroCalculo*100)/numeroAplicaPorcentaje,2);
		porcenteje=Double.valueOf(toNumberFormat((numeroCalculo*100)/numeroAplicaPorcentaje,2));

		return porcenteje;
	}
	
	public static String adjuntarFechaHoraExportacion(String nombreArchivoExportar){
		GregorianCalendar oGregorianCalendar = new GregorianCalendar();
		SimpleDateFormat oSimpleDateFormat = new SimpleDateFormat("yyyyMMddHHmmss", new Locale("es", "PE"));

		nombreArchivoExportar += oSimpleDateFormat.format(oGregorianCalendar.getTime());

		return nombreArchivoExportar;
	}
	
	/**
	 * Realiza la validadcion del RUC
	 * @param valor	: RUC a validar
	 * @return <b>true</b> si el RUC es valido, <b>false</b> caso contrario.
	 */
	public static boolean validarRUC(String valor){
		/*Valida que por lo menos inicie con los numeros correctos - 30/01/2017 - jabanto*/
		int vr=Integer.valueOf(valor.substring(0,1));	
		if(!(vr==2 || vr==1))
			return false;
		
		int suma = 0;
		int digito = 0;
		int resto = 0;
		if (valor.length()==8){
			suma = 0;
			for (int i =0; i< valor.length()-1;i++){
				digito = valor.charAt(i)-'0';
				if(i==0){
					suma+= digito*2;
				}else{
					suma+=digito*(valor.length()-i);
				}
			}
			resto = suma %11;
			System.out.println("resto"+resto);
			if(resto ==1){
				resto = 11;
			}
			if(resto + (valor.charAt(valor.length()-1)-'0')==11){
				return true;
			}

		}else if (valor.length()==11){
			suma = 0;
			int x = 6;
			for(int i =0; i<valor.length()-1; i++){
				if(i==4){
					x=8;
				}
				digito = valor.charAt(i) - '0';
				x--;
				if(i==0){
					suma += digito*x;
				}else{
					suma += digito*x;
				}
			}
			resto = suma % 11;
			resto = 11 - resto;
			if(resto >=10){
				resto = resto -10;
			}
			if(resto == valor.charAt(valor.length()-1)-'0'){
				return true;
			}
		}
		return false;
	}
	
	
	public static String generaTablaHtml(Listbox oListbox){
		StringBuilder tablaHtml = new StringBuilder();
		ArrayList<Listheader> lstCabeceras = new ArrayList<Listheader>();
		tablaHtml.append("<table>");
		
		for(int f = 0; f < oListbox.getChildren().size(); f ++){
			/*Genera Cabecera*/
			if(oListbox.getChildren().get(f).getClass().isInstance(new Listhead())){
				Listhead oListhead = (Listhead) oListbox.getChildren().get(f);
				
				tablaHtml.append("<tr>");
				for(int h = 0; h < oListhead.getChildren().size(); h ++){
					if(oListhead.getChildren().get(h).getClass().isInstance(new Listheader())){
						Listheader oListheader = (Listheader) oListhead.getChildren().get(h);
						
						lstCabeceras.add(oListheader);
						
						if(oListheader.isVisible()){
							tablaHtml.append("<th>");
							tablaHtml.append(oListheader.getLabel());
							tablaHtml.append("</th>");
						}
					}
				}
				tablaHtml.append("</tr>");
				
			}else if(oListbox.getChildren().get(f).getClass().isInstance(new Listitem())){
				/*Genera el Detalle*/
				Listitem oListitem = (Listitem) oListbox.getChildren().get(f);

				tablaHtml.append("<tr>");
				for(int i = 0; i < oListitem.getChildren().size(); i ++){
					if(oListitem.getChildren().get(i).getClass().isInstance(new Listcell())){
						Listcell oListcell = (Listcell) oListitem.getChildren().get(i);
						Listheader oListheader = lstCabeceras.get(i);

						if(oListheader.isVisible()){
							tablaHtml.append("<td ");
							tablaHtml.append("Align=\"" + oListheader.getAlign() + "\"");
							tablaHtml.append(">");
							tablaHtml.append(oListcell.getLabel());
							tablaHtml.append("</td>");
						}
					}
				}
				tablaHtml.append("</tr>");
			}
		}
		tablaHtml.append("</table>");
		
		return tablaHtml.toString();
	} 
	
	/**
	 * Activa o desactiva el button Guarbar cliente (Nuevo)
	 * @param activar : (true)se activara el button, (false) se desactivará el button
	 */
	public static void disabledBtnCancelar(boolean disabled,Button btnCancelar){
		if(!(disabled)){
			btnCancelar.setImage("/resources/mp_cancelarEnabled.png");
			btnCancelar.setStyle("cursor:pointer");
		}else{
			btnCancelar.setImage("/resources/mp_cancelarDisabled.png");
			btnCancelar.setStyle("cursor:default");
		}
		btnCancelar.setDisabled(disabled);
	}
	
	/**
	 * Activa o desactiva el button Guarbar cliente (Nuevo)
	 * @param activar : (true)se activara el button, (false) se desactivará el button
	 */
	public static void disabledBtnGuardar(boolean disabled,Button btnGuardar,Boolean accesoGuardar){
		if(!(disabled) && accesoGuardar){
			btnGuardar.setImage("/resources/mp_guardarEnabled.png");
			btnGuardar.setStyle("cursor:pointer");
		}else{
			btnGuardar.setImage("/resources/mp_guardarDisabled.png");
			btnGuardar.setStyle("cursor:default");
		}
		
		if(accesoGuardar)
			btnGuardar.setDisabled(disabled);
		else 
			btnGuardar.setDisabled(true);
	}
	
	
	/**
	 * Activa o desactiva el button Nuevo cliente (Nuevo)
	 * @param activar : (true)se activara el button, (false) se desactivará el button
	 */
	public static void disabledBtnNuevo(boolean disabled,Button btnNuevo,Boolean accesoNuevo){
		if(!(disabled) && accesoNuevo){
			btnNuevo.setImage("/resources/mp_nuevoEnabled.png");
			btnNuevo.setStyle("cursor:pointer");
		}else{
			btnNuevo.setImage("/resources/mp_nuevoDisabled.png");
			btnNuevo.setStyle("cursor:default");
		}
		
		if(accesoNuevo)
			btnNuevo.setDisabled(disabled);
		else 
			btnNuevo.setDisabled(true);
	}
	
	/**
	 * Activa o desactiva el button editar cliente (Editar)
	 * @param activar : (true)se activara el button, (false) se desactivará el button
	 * @param button  : button al que se activa e inactivará.
	 */
	public static void disabledBtnEditar(boolean disabled, Button btnEditar,Boolean accesoModificar){
		if(!(disabled) && accesoModificar){
			btnEditar.setImage("/resources/mp_editarEnabled.png");
			btnEditar.setStyle("cursor:pointer");
		}else{
			btnEditar.setImage("/resources/mp_editarDisabled.png");
			btnEditar.setStyle("cursor:default");
		}
		
		if(accesoModificar)
			btnEditar.setDisabled(disabled);
		else 
			btnEditar.setDisabled(true);
	}
	
	/**
	 * Activa o desactiva el botón Buscar
	 * @param activar
	 */
	public static void disabledBtnBuscar(boolean disabled,Button btnBuscar,Boolean accesoConsultar){
		if(!(disabled) && accesoConsultar){
			btnBuscar.setImage("/resources/mp_buscarEnabled.png");
			btnBuscar.setStyle("cursor:pointer");
		}else{
			btnBuscar.setImage("/resources/mp_buscarDisabled.png");
			btnBuscar.setStyle("cursor:default");
		}
		if(accesoConsultar)
			btnBuscar.setDisabled(disabled);
		else 
			btnBuscar.setDisabled(true);
	}
	
	/**
	 * Activa o desactiva el botón Exportar
	 * @param activar
	 */
	public static void disabledBtnExportar(boolean disabled,Button btnExportar, Boolean accesoExportar){
		if(!(disabled) && accesoExportar){
			btnExportar.setImage("/resources/mp_excel.png");
			btnExportar.setStyle("cursor:pointer");
		}else{
			btnExportar.setImage("/resources/mp_excelDisabled.png");
			btnExportar.setStyle("cursor:default");
		}
		if(accesoExportar)
			btnExportar.setDisabled(disabled);
		else 
			btnExportar.setDisabled(true);
	}
	
	/**
	 * Activa o desactiva el button agregar
	 * @param disabled : (true)desactiva el control, (false) activa el control.
	 */
	public static void disabledBtnAgregar(boolean disabled,Button btnAgregar, Boolean accesoGrabar){
		if(!(disabled) && accesoGrabar) {
			btnAgregar.setImage("/resources/mp_agregarEnabled.png");
			btnAgregar.setStyle("cursor:pointer");
		}else{
			btnAgregar.setImage("/resources/mp_agregarDisabled.png");
			btnAgregar.setStyle("cursor:default");
		}
		if(accesoGrabar)
			btnAgregar.setDisabled(disabled);
		else
			btnAgregar.setDisabled(true);
	}
	
	
	/**
	 * Activa o desactiva el button acepta
	 * @param disabled : (true)desactiva el control, (false) activa el control.
	 */
	public static void disabledBtnAceptar(boolean disabled,Button btnAceptar, Boolean accesoGrabar){
		if(!(disabled) && accesoGrabar) {
			btnAceptar.setImage("/resources/mp_aceptarEnabled.png");
			btnAceptar.setStyle("cursor:pointer");
		}else{
			btnAceptar.setImage("/resources/mp_aceptarDisabled.png");
			btnAceptar.setStyle("cursor:default");
		}
		if(accesoGrabar)
			btnAceptar.setDisabled(disabled);
		else
			btnAceptar.setDisabled(true);
	}
	
	/**
	 * Activa o desactiva el button acepta
	 * @param disabled : (true)desactiva el control, (false) activa el control.
	 */
	public static void disabledBtnRefresh(boolean disabled,Button btnRefresh, Boolean accesoGrabar){
		if(!(disabled) && accesoGrabar) {
			btnRefresh.setImage("resources/mp_refrescarEnabled.png");
			btnRefresh.setStyle("cursor:pointer");
		}else{
			btnRefresh.setImage("resources/mp_refrescarDisabled.png");
			btnRefresh.setStyle("cursor:default");
		}
		if(accesoGrabar)
			btnRefresh.setDisabled(disabled);
		else
			btnRefresh.setDisabled(true);
	}
	
	
	/*PARA LA IMPORTACION DEL ARCHIVO*/
	//Subir archivo al servidor.
	public static boolean uploadFile(Media media) {
		String path = getPath()+Constantes.DIRECTORY_UPLOADS+separator;
		return saveFile(media, path);
	}
	
	//Obtiene la ruta de la aplicación Web actual
	public static String getPath(){
		return Executions.getCurrent().getDesktop().getWebApp().getRealPath(separator);
	}
	
	//Guarda el Archivo en el Servidor
	public static boolean saveFile(Media media, String path){
		boolean uploaded = false;
		BufferedInputStream in = null;
		BufferedOutputStream out = null;
		try {
			InputStream ins = media.getStreamData();
			in = new BufferedInputStream(ins);

			String fileName = media.getName();
			File arc = new File(path + fileName);
			OutputStream aout = new FileOutputStream(arc);
			out = new BufferedOutputStream(aout);

			byte buffer[] = new byte[1024];
			int ch = in.read(buffer);
			while(ch != -1){
				out.write(buffer, 0, ch);
				ch = in.read(buffer);
			}
			uploaded = true;
		}catch (IOException ie) {
			throw new RuntimeException(ie);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}finally{
			try {
					if(out != null)
						out.close();
						if(in != null)
							in.close();
					} catch (IOException e) {
						throw new RuntimeException(e);
					}
				}
		return uploaded;
	}
	
	/**
	 * Metodo que permite validar una cadena si es numerico.  
	 * @param cadena: Cadena a evaluar
	 * @return (true) si es un numerico, (false) lo contrario250
	 */
    public static boolean isNumeric(String cadena){
	    try {
	    	Integer.parseInt(cadena);
	    	return true;
	    } catch (NumberFormatException nfe){
	    	return false;
	    }
    }
    
    /**
	 * Metodo que permite validar una cadena si es un numero decimal  
	 * @param cadena: Cadena a evaluar
	 * @return (true) si es un numerico, (false) lo contrario250
	 */
    public static boolean isDecimal(String cadena){
	    try {
	    	Double.parseDouble(cadena);
	    	return true;
	    } catch (NumberFormatException nfe){
	    	return false;
	    }
    }
    
    /**
     * Permite generar la cadena que se enviara como valor al FTI.
     * @param pasajero	: Array con los datos del pasajero.
     * @return String
     */
    public static String obtenerFullTextPasajero(String[] pasajero){
		String result = "";
		for(String valor : pasajero){
			result = (result.equals("")?"":(result+" & ")) + valor+"%";
		}
		return result;
	}
    
    /**
     * Devuelve el dia de la semana de una fecha especifica
     * @param fecha
     * @return
     */
    public static String getDiaSemana(String fecha) {
        SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
        String dia = "";
        Date fechaActual = null;
        try {
            fechaActual = df.parse(fecha);
        }catch (ParseException e) {
            System.err.println("No se ha podido parsear la fecha.");
            e.printStackTrace();
        }
        GregorianCalendar fechaCalendario = new GregorianCalendar();
        fechaCalendario.setTime(fechaActual);
        int diaSemana = fechaCalendario.get(Calendar.DAY_OF_WEEK);
        switch (diaSemana) {
            case 1:
                dia = "Domingo";
                break;
            case 2:
                dia = "Lunes";
                break;
            case 3:
                dia = "Martes";
                break;
            case 4:
                dia = "Miercoles";
                break;
            case 5:
                dia = "Jueves";
                break;
            case 6:
                dia = "Viernes";
                break;
            case 7:
                dia = "Sabado";
                break;
            default:
                System.out.println("Ese dia no existe");
                break;
        }
        return dia;
    } 
    
    /**
     * Retorna el Nombre del Mes
     * @param fecha 
     * @return 
     * @throws Exception 
     */
    @SuppressWarnings("deprecation")
	public static String getNombreMes(String fecha) throws Exception{
    	Date date=Constantes.FORMAT_DATE.parse(fecha);
		switch (date.getMonth()) {
			case 0: return "ENERO"; 
			case 1: return "FEBRERO";
			case 2: return "MARZO";
			case 3: return "ABRIL";
			case 4: return "MAYO";
			case 5: return "JUNIO";
			case 6: return "JULIO";
			case 7: return "AGOSTO";
			case 8: return "SEPTIEMBRE";
			case 9: return "OCTUBRE";
			case 10: return "NOVIEMBRE";
			case 11: return "DICIEMBRE";
		}
		return null;
	}
    
    public static String getNumberMes(String abreviacionMEs) throws Exception{
//    	Date date=Constantes.FORMAT_DATE.parse(fecha);
		switch (abreviacionMEs.toUpperCase()) {
			case "ENE" : return "01";
			case "JAN" : return "01";
			case "FEB": return "02";
			case "MAR": return "03";
			case "ABR": return "04";
			case "APR": return "04";
			case "MAY": return "05";
			case "JUN": return "06";
			case "JUL": return "07";
			case "AGO": return "08";
			case "AUG": return "08";
			case "SEP": return "09";
			case "OCT": return "10";
			case "NOV": return "11";
			case "DIC": return "12";
			case "DEC": return "12";
		}
		
		
		
		return null;
	}
    
    /**
     * coloca el foco en el control que se envie como parametros
     * @param oComponent: objeto al cual se pasará el foco.
     */
    public static void setFocus(Component oComponent){
    	if (oComponent instanceof Textbox) {
			Textbox oTextbox = (Textbox) oComponent;
			oTextbox.setFocus(true);
			oTextbox.select();
		}else if(oComponent instanceof Combobox){
			Combobox oCombobox=(Combobox)oComponent;
			oCombobox.setFocus(true);
			oCombobox.select();
		}else if (oComponent instanceof Datebox){
			Datebox oDatebox=(Datebox)oComponent;
			oDatebox.setFocus(true);
			oDatebox.select();
		}else if (oComponent instanceof Checkbox){
			Checkbox oDatebox=(Checkbox)oComponent;
			oDatebox.setFocus(true);
		}else if(oComponent instanceof Spinner){
			Spinner oSpinner=(Spinner)oComponent;
			oSpinner.setFocus(true);
		}else if (oComponent instanceof Button){
			Button oButton=(Button)oComponent;
			oButton.setFocus(true);
		}else if (oComponent instanceof Intbox){
			Intbox oIntbox= (Intbox)oComponent;
			oIntbox.setFocus(true);
			oIntbox.select();
		}else if(oComponent instanceof Doublebox){
			Doublebox oDoublebox=(Doublebox)oComponent;
			oDoublebox.setFocus(true);
			oDoublebox.select();
		}else if (oComponent instanceof Longbox){
			Longbox oLongbox=(Longbox)oComponent;
			oLongbox.setFocus(true);
			oLongbox.select();
		}else if (oComponent instanceof Timebox){
			Timebox oTimebox=(Timebox)oComponent;
			oTimebox.setFocus(true);
			oTimebox.select();
		}
    }
    
    /**
     * Realiza el calculo del total a pagar de la venta.
     * @param tarifa	: Tarifa del servicio.
     * @param descuento	: Monto descuento
     * @param recargo	: Monto recargo
     * @return total a pagar.
     */
    public static double calculoTotalPagar(Double tarifa, Double descuento, Double recargo){
    	double totalPagar=tarifa-descuento;
    	double d_decimal= ((totalPagar*100)%100)/100; //Obtiene los decimales del total a pagar 
    	    	
    	totalPagar=totalPagar-d_decimal;
    	
    	if(d_decimal >= 0.00 && d_decimal < 0.25)
    		totalPagar = totalPagar + 0.00;
    	else if(d_decimal >= 0.25 && d_decimal <= 0.50)
    		totalPagar = totalPagar + 0.50;
    	else if(d_decimal >0.50 && d_decimal < 0.75)
    		totalPagar = totalPagar + 0.50;
    	else if(d_decimal >=0.75 && d_decimal <= 1)
    		totalPagar = totalPagar + 1;
    	return totalPagar;
    }
    
    /**
	 * Obtiene la fecha de caducidad para el PaxFree
	 * @param fecha	: Fecha a la cual se le sumara el tiempo de caducidad del paxfre.
	 * @return String en formato <b>dd/MM/yyyy HH:mm:ss<b>
	 * @throws Exception 
	 */
	public static final String getFechaCaducidadPaxFre(String fecha) throws Exception{
//		MyTime time= new MyTime();
		Date mtime= Constantes.FORMAT_DATE_TIME_24H.parse(fecha);
		
		Long milSec1=Constantes.MILISEGUNDOS_X_DIA*Constantes.TIEMPO_PASAR_PAXFREE;
		Long milSec2=mtime.getTime();
		Long milResul=milSec1+milSec2;
		
		java.util.Calendar calendar= java.util.Calendar.getInstance();
		calendar.setTimeInMillis(milResul);
		
		Date dfecha=calendar.getTime();
		String fechaCaducidad=Constantes.FORMAT_DATE_TIME_24H.format(dfecha);
		
		return fechaCaducidad;
	}
	
	/**
	 * Genera un numero aleatorio entre 0 y nuemro, donde superior es el intervalo mayor.
	 * @param superior	: Numero limite del intervalo de aleatorios.
	 * @return
	 */
	public static final Integer getGenerarAleatorio(Integer superior){
		int result = 0;
		Random random = new Random();
		result = random.nextInt(superior);
		return result;
	}
	
	/**
	 * Metodo que genera la contraseña de manera aleatoria.
	 */
	public static final String generarPassword(){
		int lengthPassword = 8;
		String password = "";
		int longitud = base.length();
		for(int i=0; i<lengthPassword; i++){
			int numero = (int)(Math.random()*longitud);
			String caracter = base.substring(numero, numero+1);
			password = password+caracter;
		}
		return password;
	}
	
	public static final void loadAnios(Combobox combobox){
		try{
			combobox.getItems().clear();
			combobox.setText("");
			String fecha = ServiceLocator.getVentaPasajesManager().getDateSystem();
			String anio = Util.DatetoString(Util.StringtoDate(fecha, Constantes.DATE_FORMAT), "yyyy");
			Integer limSuperior = Integer.valueOf(anio);
			Integer limInferior = limSuperior - 95;
			for(int i = limSuperior; i>limInferior; i--){
				Comboitem comboitem = new Comboitem(String.valueOf(i));
				comboitem.setValue(i);
				combobox.appendChild(comboitem);
			}
		}catch(Exception ex){
			ex.printStackTrace();
		}
	}
	
	public static final void loadMeses(Combobox combobox){
		String[] lstMeses = {"ENERO", "FEBRERO","MARZO","ABRIL","MAYO", "JUNIO", "JULIO", "AGOSTO", "SEPTIEMBRE", "OCTUBRE", "NOVIEMBRE", "DICIEMBRE"};
		combobox.getItems().clear();
		combobox.setText("");
		for(int i=0; i<lstMeses.length;i++){
			Comboitem comboitem = new Comboitem(lstMeses[i]);
			comboitem.setValue(i+1);
			combobox.appendChild(comboitem);
		}
		combobox.setSelectedIndex(-1);
		combobox.setText("");
	}
	
	public static final void loadDias(Combobox combobox, Integer mes, Integer anio){
		combobox.getItems().clear();
		combobox.setText("");
		int diasMes = 0;
		switch(mes){
	        case 1:  // Enero
	        case 3:  // Marzo
	        case 5:  // Mayo
	        case 7:  // Julio
	        case 8:  // Agosto
	        case 10:  // Octubre
	        case 12: // Diciembre
	            diasMes = 31;
	            break;
	        case 4:  // Abril
	        case 6:  // Junio
	        case 9:  // Septiembre
	        case 11: // Noviembre
	            diasMes = 30;
	            break;
	        case 2:  // Febrero
	            if ( ((anio%100 == 0) && (anio%400 == 0)) ||
	                    ((anio%100 != 0) && (anio%  4 == 0))   )
	                diasMes = 29;  // Año Bisiesto
	            else
	                diasMes = 28;
	            break;
//	        default:
//	            throw new java.lang.IllegalArgumentException(
//	            "El mes debe estar entre 0 y 11");
	    }
		for(int i=1; i<=diasMes; i++){
			Comboitem comboitem = new Comboitem(String.valueOf(i));
			comboitem.setValue(i);
			combobox.appendChild(comboitem);
		}
		
		combobox.setSelectedIndex(-1);
		combobox.setText("");
	}

	/**
	 * Exporta a un archivo de tipo Excel.
	 * @param listbox	: Objeto ListBox, de donde se va ha leer los datos a exportar
	 * @param title		: Titulo del archivo
	 */
	public static void exportarExcel(Listbox listbox, String title){
		String nombreArchivo=Util.adjuntarFechaHoraExportacion(title);
//		response.setContentType("application/vnd.ms-excel");
//		response.setHeader("Content-Disposition", "attachment; filename=avance_x_rutas.xls");

        Filedownload.save(Util.generaTablaHtml(listbox), "application/vnd.ms-excel", nombreArchivo+".xls");       
	}
	
	/**
	 * Convierta la fecha al formato dd-mm-yyyy Ejempl(Mie 10 Oct 2013)
	 * @param fecha :Fecha 
	 * @return 
	 * @throws Exception
	 */
	public static String toFechaNombreDiaMes(Date fecha) throws Exception{
		
		String dia=Util.getDiaSemana(Constantes.FORMAT_DATE.format(fecha));
		String mes=Util.getNombreMes(Constantes.FORMAT_DATE.format(fecha));
		
		String sFecha=dia.substring(0,3)+"-"+
				Constantes.FORMAT_DAY.format(fecha)+"-"+
				mes.substring(0,3)+"-"+
				Constantes.FORMAT_YEAR.format(fecha);
		
		return sFecha;
	}
	
	/**
	 * Convierta la fecha al formato dd-mm-yyyy Ejempl(Miercoles 10 de Octubre del 2013)
	 * @param fecha :Fecha 
	 * @return 
	 * @throws Exception
	 */
	public static String toFechaNombreDiaMesLong(Date fecha) throws Exception{
		
		String dia=Util.getDiaSemana(Constantes.FORMAT_DATE.format(fecha));
		String mes=Util.getNombreMes(Constantes.FORMAT_DATE.format(fecha));
		
		String sFecha=dia+" "+
				Constantes.FORMAT_DAY.format(fecha)+" de "+
				mes.substring(0,1)+mes.substring(1,mes.length()).toLowerCase()+" del "+
				Constantes.FORMAT_YEAR.format(fecha);
		
		return sFecha;
	}
	
	/**
     * Encripta un mensaje de texto mediante algoritmo de resumen de mensaje.
     * @return Codigo encriptado.
     */
	public static String generarCodigo(String message){
		byte[] digest = null;
        byte[] buffer = message.trim().getBytes();
        try {
            MessageDigest messageDigest = MessageDigest.getInstance(SHA256);
            messageDigest.reset();
            messageDigest.update(buffer);
            digest = messageDigest.digest();
        }catch (NoSuchAlgorithmException ex) {
            DlgMessage.information(Messages.getString("Generales.information.noSuchAlgorithm"));
        }
//        String hash = toHexadecimal(digest);
//        txtCodigo.setText(hash);
        return toHexadecimal(digest);
	}
	
	/**
     * Convierte un arreglo de bytes a String usando valores hexadecimales
     * @param digest arreglo de bytes a convertir
     * @return String creado a partir de <code>digest</code>
     */
    private static String toHexadecimal(byte[] digest){
        String hash = "";
        for(byte aux : digest) {
            int b = aux & 0xff;
            if (Integer.toHexString(b).length() == 1) hash += "0";
            hash += Integer.toHexString(b);
        }
        return hash;
    }
    
    /**
	 * Genera ceros a la izquierda
	 * @param longitud	: Longitud que debe de tener el campo
	 * @param valor		: Valor al que se le debe aplicar.
	 * @return
	 */
	public static String generarCeros(Integer longitud, String valor){
		String ceros="";
		
		if(longitud<valor.length())
			valor=valor.substring(0,longitud);
		for(int i=0; i<longitud-valor.length();i++){
			ceros+="0";
		}
		String svalor=ceros+""+valor;
		return svalor;
	}
    
	/**
	 * Genera Spacios a la izquierda
	 * @param longitud	: Longitud que debe de tener el campo
	 * @param valor		: Valor al que se le debe aplicar.
	 * @param alignLeft : TRUE alinea el texto a la izquierda, FALSE  a la derecha
	 * @return
	 */
	public static String generarSpacios(Integer longitud, String valor, boolean alignLeft){
		String spaces="";
		if(longitud<valor.length())
			valor=valor.substring(0,longitud);
		for(int i=0; i<longitud-valor.length();i++){
			spaces+=" ";
		}
		String svalor="";
		if(alignLeft)
			svalor=valor+""+spaces;
		else
			svalor=spaces+""+valor;
				
		return svalor;
	}
	
	/***
	 * Quita separador de un numeros decimal
	 * @param valor
	 * @return
	 */
	public static String quitarSeparador(String valor){
		//Quita la coma (,) de miles (si es que lo tubiese)
		Integer x=valor.indexOf(",");
		if(x>=0){
			String montoEntero=valor.substring(0,x);
			String montodeciamal=valor.substring(x+1,valor.length());
			valor=montoEntero+""+montodeciamal;
		}
		
		//Quita el punto (.) de decimales
		Integer i=valor.indexOf(".");
		String montoEntero=valor.substring(0,i);
		String montodeciamal=valor.substring(i+1,valor.length());
		valor=montoEntero+""+montodeciamal;
	
		return valor;
	}
	/**
	 * Valida los caracteres no validos
	 * @return caraceres invalidos encontrados.
	 */
	public static String validarCaracteresEspeciales(String cadena){
		//Estos caracteres especiales fueron enviados por la positiva, ya que no son aceptados por el sistema Affinity.
		String caracteresSpeciales=".;´;/;µ;ƒ;Š;š;†;‡;±;ˆ;‰;˜;¶;Þ;Ø;Ÿ;°;*;!;%;&;=;?;¡;¿;|;+;{;};[;];_;<;>;^;@;¢;£;¥;¦;§;¨;©;ª;«;¬;®;¯;Ì;Ë;Ê;È;Ç;Æ;Å;Ä;Ã;Â;À;¾;½;¼;»;º;Î;Ï;Ð;€;™;„;¤;Ž;‹;œ;Œ;—;“;ä;·;ª;…;‘;”;–;;\\;";
		String caracteresInvalidos="";
		String arrayCaracteres[] =caracteresSpeciales.split(";");	 
		
			for(int i=0;i<cadena.length();i++){
				char cd=cadena.charAt(i);
				
				for(int x=0; x<arrayCaracteres.length-1;x++){
//					char cr=arrayCarateres.charAt(x);
					String cr=arrayCaracteres[x].toString();
					
					if(cr.equals(String.valueOf(cd))){
						if(caracteresInvalidos.length()==0){
							caracteresInvalidos=String.valueOf(cd);
							break;
						}else{
							caracteresInvalidos+=","+String.valueOf(cd);
							break;
						}
					}
				}
								
//			}
		}
		return caracteresInvalidos;
	}
	
	/**
	 * Obtine los subconjuntos de un registro de venta, tmpocupacion o de la ruta que estamos buscando. 
	 * @param lstSecuencias	: Lista de secuencia segun el itinerario.
	 * @param idOrigen		: Identificador del origen.
	 * @param idDestino		: Identificador del destino.
	 * @return
	 */
	public static List<Integer> obtenerSubconjunto(List<SecuenciaTramo> lstSecuencias, Integer idOrigen, Integer idDestino){
		List<Integer> lstSubconjunto = new ArrayList<Integer>();
		/*	Recorremos la secuencia de tramos del itinerario	*/
		for(int j=0; j<lstSecuencias.size(); j++){
			SecuenciaTramo secuencia = lstSecuencias.get(j);
			/*	Validamos si el origen de la secuencia coincide con el origen de la ruta	*/
			if(secuencia.getOrigen().intValue()==idOrigen.intValue()){
				/*	Recorremos la secuencia de tramos desde la posicion j	*/
				for(int k=j; k<lstSecuencias.size(); k++){
					secuencia = lstSecuencias.get(k);
					lstSubconjunto.add(secuencia.getOrden());
					/*	Validamos si el destino de la secuencia coincide con el destino de la ruta	*/
					if(secuencia.getDestino().intValue()==idDestino.intValue())
						break;
				}
				break;
			}
		}
		return lstSubconjunto;
	}
	

	/**
	 * Valida y retorna los datos del pasajero obtenidos desde wsmtc, ademas del objeto Reniec. Siempre y cuando esta validacion este activa, segun parametro confirurable
	 * @param numeroDni 	: número de DNI
	 * @param searchDBReniecLocal : true si debe completar el resultado con los de nuestra base de datos de la reniec.
	 * @return ResultIdentidad
	 * @throws Exception
	 */
	public static ResultIdentidad getResultIdentidad(String numeroDni,Image imageValidacionDNI)throws Exception{
		ResultIdentidad resultIdentidad=null;
		imageValidacionDNI.setSrc("");
		/*Recupera parametros*/
		Parametros parametros=ServiceLocator.getParametrosManager().buscarPorEstadoRegistro(Constantes.VALUE_ACTIVO);
		if(parametros.getValidarDNIgetIdentidad()!=null){
			
			/* ### REACTIVACION INACTIVA*/
//			/**Valida si el parametros ValidarDNIgetIdentidad esta inactivo*/
//			if(parametros.getValidarDNIgetIdentidad().intValue()==Constantes.FALSE_VALUE){
//				resultIdentidad = WSMTC.getIdentidad(numeroDni);
//				if(resultIdentidad!=null){
//					/*Habilita el parametro ValidarDNIgetIdentidad*/
//					parametros.setValidarDNIgetIdentidad(Constantes.TRUE_VALUE);
//					if(parametros.getAlertarVentaDNIFalso()!=null)
//						/*Habilita el parametro de envio de alertas getAlertarVentaDNIFalso*/
//						parametros.setAlertarVentaDNIFalso(Constantes.TRUE_VALUE);
//					Usuario usuario=new Usuario();
//					usuario.setLogin("VYR");
//					UtilData.auditarRegistro(parametros, usuario, Executions.getCurrent());
//					ServiceLocator.getParametrosManager().actualizar(parametros);
//					
//					/*Envia alerta informado la reactivacion de estos parametros*/
//					/*Envia alerta informando*/
//					String mensaje="Se han reactivado las siguientes validaciones y envío de Alertas.: \n";
//					mensaje+="* Validación con el Método getIdentidad. \n";
//					mensaje+="* Envío de alertas al registrar una venta a un Pasajero con DNI no valido.";
//					
//					DestinatariosEmails window = new DestinatariosEmails();
//					String toAddress="moscco@tepsa.com.pe,jabanto@tepsa.com.pe";
//					window.setEmails("TO:"+toAddress);
//									
//					//Envia E-Mail
//					mensaje+="\n\n NOTA: [Este buzon es de envio automático, por favor no responda.]";
//					Sendmail.enviaEmail(mensaje,"Reactivación automática de Validaciones y envío de alertas", window);
//				}
//			}
						
			if(parametros.getValidarDNIgetIdentidad().intValue()==Constantes.TRUE_VALUE){
				if(resultIdentidad==null)
					resultIdentidad = WSMTC.getIdentidad(numeroDni);
				
				/*ademas consulta nuestra DB de la Reniec*/
				if(resultIdentidad!=null){
					Reniec reniec=ServiceLocator.getReniecManager().buscarPax(numeroDni);
					if(reniec!=null)
						resultIdentidad.setReniec(reniec);
				}
				
				/*Para mostrar la imagen con el estado de la validacion.*/
				if(resultIdentidad!=null && resultIdentidad.isReturn())
					Util.imagenValidacionDNIReniec(Constantes.TRUE_VALUE, imageValidacionDNI);
				else
					Util.imagenValidacionDNIReniec(Constantes.FALSE_VALUE, imageValidacionDNI);
			}
		}
		return resultIdentidad;
	}
	
	/**
	 * Valida si el DNI del pasajero es o no valido según validacion previa con el reniec mediante el metodo getIdentidad, para determinar el envio de la alerta
	 * @param ventaPasaje : Instancia del objeto VentaPasajes
	 * @throws Exception 
	 */
	public static void validarValidacionDNIReniec(VentaPasaje ventaPasaje) throws Exception{
		/*Valida si es DNI y este ha sido marcado como no valido*/
		if(ventaPasaje.getPasajero().getTipoDocumento().getId().intValue()==Constantes.ID_TIPDOC_DNI &&
				ventaPasaje.getPasajero().getValidadoReniec().intValue()==Constantes.FALSE_VALUE){
			/*Recupera parametros*/
			Parametros parametros=ServiceLocator.getParametrosManager().buscarPorEstadoRegistro(Constantes.VALUE_ACTIVO);
			if(parametros.getAlertarVentaDNIFalso()!=null){
				/*Valida si el parametro esta habilitado*/
				if(parametros.getAlertarVentaDNIFalso().intValue()==Constantes.TRUE_VALUE){
					Integer edad=Util.calculaEdad(ventaPasaje.getPasajero().getFechaNacimiento());
					if(edad>=18)//Solo mayores de edad
						Util.sendAlertaVentaDNIFalso(ventaPasaje, Constantes.INCIDENCIA_VENTA_PAX_DNI_INVALIDO);
				}
			}
		}
	}
	
	/**
	 * Cuando se realiza una venta a un pasajero con DNI invalido y/o a un menor de edad.
	 * @param ventaPasaje	: Objeto VentaPasajes
	 * @param incidencia	: Tipo de incidencia
	 */
	private static void sendAlertaVentaDNIFalso(VentaPasaje ventaPasaje, Integer incidencia){
		Logger log = Logger.getLogger(Util.class);
		try {
				String title="",mensaje="";
				if(incidencia.intValue()==Constantes.INCIDENCIA_VENTA_PAX_DNI_INVALIDO){
					title="Venta - DNI no valido";
					mensaje="Se ha realizado la venta de un boleto de viaje a un Pasajero con DNI "+ventaPasaje.getPasajero().getNumeroDocumento()+", el cual no existe en la Reniec.\n";
				}else if (incidencia.intValue()==Constantes.INCIDENCIA_VENTA_PAX_MENOR_EDAD){
					title="Venta - Menor de edad";
					mensaje="Se ha realizado la venta de un boleto a un menor de edad con DNI "+ventaPasaje.getPasajero().getNumeroDocumento()+"\n";
				}else{
					title="Venta - Incidencia no controlada";
					mensaje="Se ha realizado la venta de un boleto a un Pasajero con DNI "+ventaPasaje.getPasajero().getNumeroDocumento()+"\n";
				}
			
				mensaje+="Boleto     :"+(ventaPasaje.getNumeroBoleto()!=null?ventaPasaje.getNumeroBoleto():"")+"\n";
				mensaje+="N° Control :"+ventaPasaje.getNumeroControl()+"\n";
				mensaje+="Agencia    :"+ventaPasaje.getAgencia().toString()+"\n";
				mensaje+="Usuario    : "+ventaPasaje.getUsuario().toString()+"\n";;
				
				String toAddress="moscco@tepsa.com.pe";
//				String ccAddress="jabanto@tepsa.com.pe";
				
				//Envia E-Mail
				mensaje+="\n\n NOTA: [Este buzon es de envio automático, por favor no responda.]";
				DestinatariosEmails window = new DestinatariosEmails();
//				window.setEmails("TO:"+toAddress+";CC:"+ccAddress);
				window.setEmails("TO:"+toAddress);
				Sendmail.enviaEmail(mensaje,title, window);
//			}
			
		} catch (Exception e) {
			log.error("Error al enviar E-Mail "+e.getMessage());
		}
	}
	
	/**
	 * Establece el icono de la imagen, según el estado de la validacion del pasajero con la reniec.
	 * @param estadoValidacion : 0=DNI, segun reniec no es válido, 1=DNI, segun reniec es correcto.
	 * @param imageValidacionDNI : Objeto image donde se debe mostrar el icono.
	 */
	public static void imagenValidacionDNIReniec(Integer estadoValidacion, Image imageValidacionDNI){
		if(estadoValidacion.intValue()==Constantes.TRUE_VALUE){
			imageValidacionDNI.setSrc(Constantes.IMAGE_VALIDACION_DNI_OK);
			imageValidacionDNI.setTooltiptext(Messages.getString("WndPasajero.information.validacionDniOk"));
		}else{
			imageValidacionDNI.setSrc(Constantes.IMAGE_VALIDACION_DNI_ERROR);
			imageValidacionDNI.setTooltiptext(Messages.getString("WndPasajero.information.validacionDniError"));
		}
	}
	
	
	/**
	 * Busca el tipo de cambio y calcula su equibalente en el tipo de moneda del pais al cual esta asociado la agencia
	 * @param agencia	: Agencia la cual esta realizando la venta
	 * @param El objeto tipoCambio con su equibalente a la moneda local(segun el pais de donde se ingrese) 
	 * @throws Exception
	 */
	public static TipoCambio getTipoCambioEquiMonedaLocal(Agencia agencia,Double importeSoles, boolean isDescuento)throws Exception{
		if(importeSoles.doubleValue()==.00)
			return null;
				
		Double tarifaEquivalente=importeSoles;
		agencia=ServiceLocator.getAgenciaManager().buscarPorId(agencia.getId().longValue());
		TipoCambio tipoCambio=null;
		
		if(agencia.getNacionalidad()!=null && agencia.getNacionalidad().getTipoMoneda()!=null && 
				agencia.getNacionalidad().getTipoMoneda().getId().intValue()!=Constantes.ID_TIPMON_SOLES){
			tipoCambio=ServiceLocator.getTipoCambioManager().buscarUltimoTipoCambio(agencia.getNacionalidad().getTipoMoneda().getId());
			if(tipoCambio!=null){
				Double valorEquibalente=importeSoles/tipoCambio.getTipoCambio();
				/*Redondear*/
				if(valorEquibalente.doubleValue()!=importeSoles.doubleValue()){
					tarifaEquivalente=roundToPesosColombianos(valorEquibalente);
					tipoCambio.setEquivalenteMonedaLocal(tarifaEquivalente);
					
					/*Valida que lo convertifo no sea menor a la tarifa, si no es un descuento*/
					if(!(isDescuento)){
						for(int x=0;x<10;x++){
							Double importeEquiSoles=tarifaEquivalente*tipoCambio.getTipoCambio();
							if(importeEquiSoles < importeSoles){
								/*Si es me menor el suma  a la tarifa la diferencia y vuelve a calcular*/
								valorEquibalente=(importeSoles+(importeSoles-importeEquiSoles))/tipoCambio.getTipoCambio();
								tarifaEquivalente=roundToPesosColombianos(valorEquibalente);
								tipoCambio.setEquivalenteMonedaLocal(tarifaEquivalente);
							}else{
								break;
							}
						}
					}
				}else{
					tipoCambio=null;
				}
			}
		}
		return tipoCambio;
	}
	
	/**
	 * Realiza el redondeo a miles ejemplo si es 125,800 dede ser 126,000 si es 125,400 debe ser 126,000  o 51,300 ebe ser 52,000 siempre redinde hacia riba 
	 * @param importeSeoles	: Tarifa a redondear
	 * @param numeroRound	: Numero dijitos los que debe realiza el redondeo
	 * @return
	 */
	private static double roundToPesosColombianos(double importeSeoles) {
	    if(importeSeoles == 0) {
	        return 0;
	    }

	    final double d = Math.ceil(Math.log10(importeSeoles < 0 ? -importeSeoles: importeSeoles));
	    
	    int numeroRound=(int)d;
	    if(importeSeoles>=10000.00){
	    	numeroRound= (int) d/2; //Viene a ser el numero de dijitos que representa los miles a los cuales se debe redondear
	    }else{
	    	numeroRound=(int)d-3;
	    }
	    
	    final int potencia = numeroRound - (int) d;

	    final double magnitud = Math.pow(10, potencia);
	    final long shifted = Math.round(importeSeoles*magnitud);    
	    
	    return shifted/magnitud;
	    	
	}
	
	public static void descargarArchivo(File file) throws Exception{
		/*Nueva implementacion- 20/12/2015*/
		Filedownload.save(file, "application/txt");
		/* **********************************************/
		
	}
	
	public static void Zippear(String pFile, String pZipFile, String nameFile) throws Exception {
		nameFile=nameFile+".xml";
		final int BUFFER_SIZE = 1024;
		// buffer
		byte[] buffer = new byte[BUFFER_SIZE];
		try {
			FileOutputStream fos = new FileOutputStream(pZipFile);//("C:\\MyFile.zip");
    		ZipOutputStream zos = new ZipOutputStream(fos);
    		ZipEntry ze= new ZipEntry(nameFile);
    		zos.putNextEntry(ze);
    		FileInputStream in = new FileInputStream(pFile);
    		int len;
    		while ((len = in.read(buffer)) > 0) {
    			zos.write(buffer, 0, len);
    		}
    		in.close();
    		zos.closeEntry();
    		//remember close it
    		zos.close();
		} catch (Exception e) {
			throw e;
		}
	}
	
	/**
	 * Realiza la validacion si el destino pertenese o no al pool
	 * @param detalleItinerario : Instancia de la clase.
	 * @return true si el destino pertenese al pool, false lo contrario
	 * @throws Exception
	 */
	public static boolean isDestinoPool(DetalleItinerario detalleItinerario)throws Exception{
		if(detalleItinerario==null){
			return false;
		}else if(detalleItinerario.getPoolLocalidad()==null && detalleItinerario.getItinerario().getOperadoPor()!=null){
			return true;
		}else if (detalleItinerario.getPoolLocalidad()==null && detalleItinerario.getItinerario().getOperadoPor()==null){
			return false;
		}else if (detalleItinerario.getPoolLocalidad().getRutaMayor()!=null){
			Itinerario itinerario= ServiceLocator.getItinerarioManager().buscarPorId(detalleItinerario.getItinerario().getId());
			if(itinerario.getRuta().getId().intValue()==detalleItinerario.getPoolLocalidad().getRutaMayor().getId().intValue())
				return true;
			else
				return false;
		}else{
			return true;
		}
	}
	
	// redondear decimales
	public static Double formatearDecimales(Double numero, Integer numeroDecimales) {
		return Math.round(numero * Math.pow(10, numeroDecimales)) / Math.pow(10, numeroDecimales);
	}
		
}