/**
 * Proyecto		: SISVYR
 * Sistema		: Sistema de Ventas y Reservas
 * Descripción	: 
 * Fecha		: 27/06/2014
 */
package com.cystesoft.vyrbus.view.ctrl;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.Label;
import org.zkoss.zul.Filedownload;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Toolbarbutton;

import com.cystesoft.vyrbus.model.bean.VSAfiliacion;
import com.cystesoft.vyrbus.model.bean.VSAsegurado;
import com.cystesoft.vyrbus.model.bean.VSContratante;
import com.cystesoft.vyrbus.model.bean.VSEncabezadoAfiliacion;
import com.cystesoft.vyrbus.model.bean.VSEstructuraAsegurado;
import com.cystesoft.vyrbus.model.bean.VSEstructuraContratante;
import com.cystesoft.vyrbus.model.bean.VSEstructuraDeclaracion;
import com.cystesoft.vyrbus.model.bean.VSMoneda;
import com.cystesoft.vyrbus.model.bean.VSPais;
import com.cystesoft.vyrbus.model.bean.VSTipoProceso;
import com.cystesoft.vyrbus.model.bean.VSTipoRegistro;
import com.cystesoft.vyrbus.service.exceptions.VSProcesaAfiliacionException;
import com.cystesoft.vyrbus.service.locator.ServiceLocator;
import com.cystesoft.vyrbus.service.util.Constantes;
import com.cystesoft.vyrbus.service.util.Messages;
import com.cystesoft.vyrbus.service.util.Util;
import com.cystesoft.vyrbus.service.util.UtilData;
import com.cystesoft.vyrbus.view.ui.DlgMessage;
import com.cystesoft.vyrbus.view.ui.WndBase;


/**
 * @author JABANTO
 *
 */
public class WndVSProcesarAfiliaciones extends WndBase {
	private static final long serialVersionUID = 1L;

	//Parametros de busqueda
	private Datebox dtbxBusuqedaFechaInico;
	private Datebox dtbxBusuqedaFechafin;
	private Listbox lsbxAfiliados;
	private Toolbarbutton tbbGenerarArchivo;
	//Datos para la generacion del archivo de afiliaciones
	private Label lblCodigoPatrocinador;
	private Label lblNumeroRamo;
	private Label lblCodigoProducto;
	private Label lblFechaProceso;
	private Label lblNumeroPoliza;
	private Label lblTipoDeclaracion;
	private Label lblFechaInicio;
	private Label lblFechaFin;
	private Label lblCantidadRegistro;
	private Label lblCantidadMovimientos;
	private Label lblMonto;
	private Label lblMoneda;
	
	final String NEWLINE = "\n";
	
	/*Variables*/
	private static final int ID_TIPPRO_AFILIACION=1;
	private static final int ID_MONEDA_SOLES=1;
	private static final int ID_TIPREG_DECLARACION=1;
	private static final int ID_PAIS_PERU=1;
	private static final int ID_TIPREG_ASEGURADO=3;
	private static final int CODIGO_ESTCIV_OTROS=6;
	
	private static final String COD_TIPPRO_AFILIACION="AF";
	private static final String COD_MONEDA_SOLES="S";
	private static final int CODIGO_TIPREG_DECLARACION=0;
	private static final String CODIGO_PAIS_PERU="001";
	private static final int CODIGO_TIPREG_ASEGURADO=2;
	
	private static final String LABEL_TIPPRO_AFILIACION="AFILIACION";
	private static final String LABEL_MONEDA_SOLES="SOLES";

	private static final int GEN_AFI_CODIGO_PATROCINADOR=11;
	private static final int GEN_AFI_NUMERO_RAMO=21;
	private static final String GEN_AFI_CODIGO_PRODUCTO="TEPVIA";
	private static final int GEN_AFI_NUMERO_POLIZA=2547069;
	private static final String CODIGO_SUCURSAL="CODS";
	
	private static final int ID_CONTRATANTE_TEPSA=1;
	/* variables que guardan la longitud de digitos o caracteres del encabezado*/
	private static final int LONG_ENC_CODIGO_PATROCINADOR=6;
	private static final int LONG_ENC_NUMERO_RAMO=3;
	private static final int LONG_ENC_CODIGO_PRODUCTO=6;
	private static final int LONG_ENC_NUMERO_POLIZA=12;
	private static final int LONG_ENC_TIPO_DECLARACION=2;
	private static final int LONG_ENC_FECHA_PROCESO=8;
	private static final int LONG_ENC_CANTIDAD_REGISTRO=5;
	private static final int LONG_ENC_CANTIDAD_MOVIMINETOS=5;
	private static final int LONG_ENC_MONTO=9;
	/* variables que guardan la longitud de digitos o caracteres de la estructura de la declaracion*/
	private static final int LONG_ESTDEC_TIPO_REGISTRO=1;
	private static final int LONG_ESTDEC_CODIGO_SUCURSAL_VENTA=4;
	private static final int LONG_ESTDEC_CODIGO_VENDEDOR=15;
	private static final int LONG_ESTDEC_NUMERO_DECLARACION=10;
	private static final int LONG_ESTDEC_FORMA_PAGO=3;
	private static final int LONG_ESTDEC_CODIGO_PLAN=5;
	private static final int LONG_ESTDEC_TIPO_MONEDA=1;
	private static final int LONG_ESTDEC_FECHA_TIPO_CAMBIO=8;
	private static final int LONG_ESTDEC_MONTO_PRIMA_ANUAL_NETA=13;
	private static final int LONG_ESTDEC_MONTO_PRIMA_ANUL_BRUTA=13;
	private static final int LONG_ESTDEC_MONTO_PRIMA_MENSUAL=13;
	private static final int LONG_ESTDEC_MOTIVO=3;
	private static final int LONG_ESTDEC_PAIS_DESTINO=3;
	private static final int LONG_ESTDEC_CIUDAD_DESTINO=3;
	/* CONATANTES QUE GUARDAN LA LONGITUD DE LA ESTRUCTURA DEL CONTRATANTE - AFILIACION*/
	private static final int LONG_ESTCON_RAZON_SOCIAL=50;
	private static final int LONG_ESTCON_PROFESION_OCUPACION=30;
	private static final int LONG_ESTCON_NUMERO_CUENTA=20;
	/* CONSTANTES QUE GUARDAN LA LONGITUD DE LA ESTRUCTURA DEL ASEGURADO*/
	private static final int LONG_ESTASE_ADICIONAL=1;
	/* CONSTANTES QUE GUARDAN LA LONGITUD  GENERICAS - AFILIACIONES*/
	private static final int LONG_GEN_TIPO_REGISTTRO=1;
	private static final int LONG_GEN_TIPO_PERSONA=1;
	private static final int LONG_GEN_TIPO_DOCUMENTO_IDENTIDAD=1;
	private static final int LONG_GEN_NUMERO_DOCUMENTO_IDENTIDAD=15;
	private static final int LONG_GEN_APELLIDO_PATERNO=20;
	private static final int LONG_GEN_APELLIDO_MATERNO=20;
	private static final int LONG_GEN_NOMBRES=30;
	private static final int LONG_GEN_FECHA=8;
	private static final int LONG_GEN_SEXO=1;
	private static final int LONG_GEN_DIRECCION=50;
	private static final int LONG_GEN_UBG_DEPARTAMENTO=2;
	private static final int LONG_GEN_UBG_PROVINCIA=2;
	private static final int LONG_GEN_UBG_DISTRITO=2;
	private static final int LONG_GEN_TELF_DOMICILIO=12;
	private static final int LONG_GEN_TEFT_CELULAR=12;
	private static final int LONG_GEN_ESTADO_CIVIL=1;
	private static final int LONG_GEN_CERTIFICADO=10;
	
	/* (non-Javadoc)
	 * @see com.tepsa.vs.view.ui.IBase#initComponents()
	 */
	@Override
	public void initComponents() {
		//Parametros de busqueda
		dtbxBusuqedaFechaInico=(Datebox)this.getFellow("dtbxBusuqedaFechaInico");
		dtbxBusuqedaFechafin=(Datebox)this.getFellow("dtbxBusuqedaFechafin");
		lsbxAfiliados=(Listbox)this.getFellow("lsbxAfiliados");
		tbbGenerarArchivo=(Toolbarbutton)this.getFellow("tbbGenerarArchivo");
		//Datos para la generacion del archivo de afiliaciones
		lblCodigoPatrocinador=(Label)this.getFellow("lblCodigoPatrocinador");
		lblNumeroRamo=(Label)this.getFellow("lblNumeroRamo");
		lblCodigoProducto=(Label)this.getFellow("lblCodigoProducto");
		lblFechaProceso=(Label)this.getFellow("lblFechaProceso");
		lblNumeroPoliza=(Label)this.getFellow("lblNumeroPoliza");
		lblTipoDeclaracion=(Label)this.getFellow("lblTipoDeclaracion");
		lblFechaInicio=(Label)this.getFellow("lblFechaInicio");
		lblFechaFin=(Label)this.getFellow("lblFechaFin");
		lblCantidadRegistro=(Label)this.getFellow("lblCantidadRegistro");
		lblCantidadMovimientos=(Label)this.getFellow("lblCantidadMovimientos");
		lblMonto=(Label)this.getFellow("lblMonto");
		lblMoneda=(Label)this.getFellow("lblMoneda");
	}
	
	/* (non-Javadoc)
	 * @see com.tepsa.vs.view.ui.WndBase#onCreate()
	 */
	@Override
	public void onCreate() throws Exception {
		Date fechaActual=new Date();
		
		dtbxBusuqedaFechaInico.setValue(new Date());
		dtbxBusuqedaFechafin.setValue(new Date());
		lblTipoDeclaracion.setValue(LABEL_TIPPRO_AFILIACION);
		lblMoneda.setValue(LABEL_MONEDA_SOLES);
		
		lblCodigoPatrocinador.setValue(String.valueOf(GEN_AFI_CODIGO_PATROCINADOR));
		lblNumeroRamo.setValue(String.valueOf(GEN_AFI_NUMERO_RAMO));
		lblCodigoProducto.setValue(String.valueOf(GEN_AFI_CODIGO_PRODUCTO));
		lblFechaProceso.setValue(Constantes.FORMAT_DATE.format(fechaActual));
		lblNumeroPoliza.setValue(String.valueOf(GEN_AFI_NUMERO_POLIZA));
		lblFechaInicio.setValue(Constantes.FORMAT_DATE.format(fechaActual));
		lblFechaFin.setValue(Constantes.FORMAT_DATE.format(fechaActual));
		
		lblTipoDeclaracion.setValue(LABEL_TIPPRO_AFILIACION);
	}
	
	public void buscarAfiliaciones() throws Exception{
		Util.limpiarListbox(lsbxAfiliados);
										
		String fechaInicio=Constantes.FORMAT_DATE.format(dtbxBusuqedaFechaInico.getValue());
		String fechaFin=Constantes.FORMAT_DATE.format(dtbxBusuqedaFechafin.getValue());
		List<VSAfiliacion>lstAfiliacion=ServiceLocator.getVentaSeguroManager().buscarAfiliacionesByProceso(fechaInicio, fechaFin);
		
		Listitem item=null;
		Listcell cell=null;
			
			for(VSAfiliacion afiliacion: lstAfiliacion){
				
				item=new Listitem();
				cell=new Listcell("");
				item.appendChild(cell);
				cell=new Listcell(Constantes.FORMAT_DATE.format(afiliacion.getFechaVenta()));
				cell.setStyle("font-size:11px !important");
				item.appendChild(cell);
				cell=new Listcell(afiliacion.getVsTipoRegistro().getDenominacion());
				item.appendChild(cell);
				cell=new Listcell(afiliacion.getVsTipoPersona().getDenominacion());
				item.appendChild(cell);
				cell=new Listcell(afiliacion.getVsAsegurado().toString());
				item.appendChild(cell);
				cell=new Listcell(afiliacion.getNumeroCertificado());
				cell.setStyle("font-size:11px !important;text-align: right;");
				item.appendChild(cell);
				cell=new Listcell(Util.toNumberFormat(afiliacion.getImportePagado(),2));
				cell.setStyle("font-size:11px !important;text-align: right;");
				item.appendChild(cell);
				
				item.setValue(afiliacion);
				lsbxAfiliados.appendChild(item);
				
				tbbGenerarArchivo.setDisabled(false);
			}
		
			lblCantidadRegistro.setValue("0");
			lblCantidadMovimientos.setValue("0");
			lblMonto.setValue("0");
	}
	
	public void generaArchivo() throws Exception{
		try{			
			if(lsbxAfiliados.getSelectedItems().size()<=0)
				throw new VSProcesaAfiliacionException(VSProcesaAfiliacionException.ASEGURADOS_NULL);
			else if(lblNumeroPoliza.getValue().trim().isEmpty())
				throw new VSProcesaAfiliacionException(VSProcesaAfiliacionException.NUMERO_PULIZA_NULL);
			else if (lblTipoDeclaracion.getValue().isEmpty())
				throw new VSProcesaAfiliacionException(VSProcesaAfiliacionException.TIPO_DECLARACION);
			else if (lblFechaInicio.getValue().isEmpty())
				throw new VSProcesaAfiliacionException(VSProcesaAfiliacionException.FECHA_INICIO_NULL);
			else if (lblFechaFin.getValue().isEmpty())
				throw new VSProcesaAfiliacionException(VSProcesaAfiliacionException.FECHA_FINAL_NULL);
			else if (lblMoneda.getValue().isEmpty())
				throw new VSProcesaAfiliacionException(VSProcesaAfiliacionException.MONEDA_NULL);
			
			
			Messagebox.show(Messages.getString("WndVSProcesaAfiliacion.question.generarArchivo"), DlgMessage.NOMBREAPLICACION, DlgMessage.BTN_YESNO, Messagebox.QUESTION,DlgMessage.BTN_DEFAULT_NO, new EventListener<Event>() {
				@Override
				public void onEvent(Event e) throws Exception {
					if(e.getName().equals("onYes")){
						final DateFormat FORMAT_DATE_TIME_S = new SimpleDateFormat ("ddMMyyhhmmss");
						final DateFormat FORMAT_FECHA = new SimpleDateFormat ("yyyyMMdd");
						
						String fichero = Constantes.DIRECTORY_AFILIACIONES+"TEPVIA_AP_AF_"+FORMAT_DATE_TIME_S.format(new Date())+".txt";
						File file = new File(fichero);
						
//						BufferedWriter bw = new BufferedWriter(new FileWriter(file));
						BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file),"8859_1"));
						String linea = "";
						String iDs="";
						
						VSTipoProceso tipoProcesoEncabezado=new VSTipoProceso(ID_TIPPRO_AFILIACION);
						tipoProcesoEncabezado.setCodigo(COD_TIPPRO_AFILIACION);
						VSMoneda vsMoneda=new VSMoneda(ID_MONEDA_SOLES);
						vsMoneda.setCodigo(COD_MONEDA_SOLES);
						VSContratante contratante=ServiceLocator.getVentaSeguroManager().buscarContratantePorID(ID_CONTRATANTE_TEPSA);

						//VARIABLES DEL ENCABEZADO.
						String ENCODIGO_PATROCINADOR=Util.generarCeros(LONG_ENC_CODIGO_PATROCINADOR,lblCodigoPatrocinador.getValue());
						String ENNUMERO_RAMO=Util.generarCeros(LONG_ENC_NUMERO_RAMO,lblNumeroRamo.getValue());
						String ENCODIGO_PRODUCTO=Util.generarSpacios(LONG_ENC_CODIGO_PRODUCTO,lblCodigoProducto.getValue(),true);
						String ENNUMERO_POLIZA=Util.generarCeros(LONG_ENC_NUMERO_POLIZA,lblNumeroPoliza.getValue());
						String ENTIPO_PROCESO=Util.generarSpacios(LONG_ENC_TIPO_DECLARACION,tipoProcesoEncabezado.getCodigo(),true);
						String ENFECHA_PROCESO=Util.generarCeros(LONG_GEN_FECHA,FORMAT_FECHA.format(new Date()));
						String ENFECHA_INICIO=Util.generarCeros(LONG_GEN_FECHA,FORMAT_FECHA.format(dtbxBusuqedaFechaInico.getValue()));
						String ENFECHA_FIN=Util.generarCeros(LONG_GEN_FECHA,FORMAT_FECHA.format(dtbxBusuqedaFechafin.getValue()));
						String ENCANTIDAD_REGISTROS=Util.generarCeros(LONG_ENC_CANTIDAD_REGISTRO,lblCantidadRegistro.getValue());
						String ENCANTIDAD_MOVIMIENTOS=Util.generarCeros(LONG_ENC_CANTIDAD_MOVIMINETOS,lblCantidadMovimientos.getValue());
						String ENMONTO=Util.generarCeros(LONG_ENC_MONTO,Util.quitarSeparador(Util.toNumberFormat(Double.valueOf(lblMonto.getValue()), 2)));
						String ENTIPOMONEDA=Util.generarSpacios(LONG_ESTDEC_TIPO_MONEDA,vsMoneda.getCodigo(),true);
						
						//GUARDA ENCABEZADO db
						VSEncabezadoAfiliacion encabezado= new VSEncabezadoAfiliacion();
						encabezado.setCodigoPatrocinador(ENCODIGO_PATROCINADOR);
						encabezado.setNumeroRamo(ENNUMERO_RAMO);
						encabezado.setCodigoProducto(ENCODIGO_PRODUCTO);
						encabezado.setNumeroPoliza(ENNUMERO_POLIZA);
						encabezado.setTipoDeclaracion(ENTIPO_PROCESO);
						encabezado.setFechaProceso(ENFECHA_PROCESO);
						encabezado.setFechaInicio(ENFECHA_INICIO);
						encabezado.setFechaFin(ENFECHA_FIN);
						encabezado.setCantidadRegistros(ENCANTIDAD_REGISTROS);
						encabezado.setCantidadMovimientos(ENCANTIDAD_MOVIMIENTOS);
						encabezado.setMonto(ENMONTO);
						encabezado.setVsMoneda(vsMoneda);
						encabezado.setEstadoRegistro(Constantes.VALUE_ACTIVO);
						UtilData.auditarRegistro(encabezado, getUsuario(), Executions.getCurrent());
						ServiceLocator.getVentaSeguroManager().guardarEncabezadoAfiliacion(encabezado);
						//ENCABEZADO DEL ARCHIVO txt
						linea=ENCODIGO_PATROCINADOR;
						linea+=ENNUMERO_RAMO;
						linea+=ENCODIGO_PRODUCTO;
						linea+=ENNUMERO_POLIZA;
						linea+=ENTIPO_PROCESO;
						linea+=ENFECHA_PROCESO;
						linea+=ENFECHA_INICIO;
						linea+=ENFECHA_FIN;
						linea+=ENCANTIDAD_REGISTROS;
						linea+=ENCANTIDAD_MOVIMIENTOS;
						linea+=ENMONTO;
						linea+=ENTIPOMONEDA;
						bw.write(linea+NEWLINE);
																				
						for(Listitem item: lsbxAfiliados.getSelectedItems()){
							VSAfiliacion afiliacion=item.getValue();
							if(iDs.length()==0)
								iDs=afiliacion.getId().toString();
							else
								iDs+=","+afiliacion.getId().toString();
							VSAsegurado  asegurado=afiliacion.getVsAsegurado();
							/**TIPO REGISTRO DE LA DECLARACION*/
							VSTipoRegistro tipoRegistroDeclarcion= new VSTipoRegistro();
							tipoRegistroDeclarcion.setId(ID_TIPREG_DECLARACION);
							tipoRegistroDeclarcion.setCodigo(CODIGO_TIPREG_DECLARACION);
							/***PAIS DESTINO*/
							VSPais paisDestino=new VSPais();
							paisDestino.setId(ID_PAIS_PERU);
							paisDestino.setDocigo(CODIGO_PAIS_PERU);
							/**TIPO REGISTRO DE LA ASEGURADO*/
							VSTipoRegistro tipoRegistroAsegurado= new VSTipoRegistro();
							tipoRegistroAsegurado.setId(ID_TIPREG_ASEGURADO);
							tipoRegistroAsegurado.setCodigo(CODIGO_TIPREG_ASEGURADO);	
							
							//VARIABLES ESTRUCTURA DE LA DECLARACION
							String ESDETIPO_REGUSTRO=Util.generarSpacios(LONG_ESTDEC_TIPO_REGISTRO,tipoRegistroDeclarcion.getCodigo().toString(),true);
							String ESDEFECHA_PROCESO=Util.generarSpacios(LONG_ENC_FECHA_PROCESO,FORMAT_FECHA.format(new Date()),true);
							String ESDECODIGO_SUCURSAL=Util.generarSpacios(LONG_ESTDEC_CODIGO_SUCURSAL_VENTA,CODIGO_SUCURSAL,true);
							String ESDECODIGO_VENDEDOR=Util.generarSpacios(LONG_ESTDEC_CODIGO_VENDEDOR,afiliacion.getUsuarioID().toString(),true);
							String ESDENUMERO_DECLARACIONUtil=Util.generarCeros(LONG_ESTDEC_NUMERO_DECLARACION,"");
							String ESDEFECHA_VIJENCIA_INICIAL=Util.generarSpacios(LONG_ENC_FECHA_PROCESO,FORMAT_FECHA.format(afiliacion.getFechaVigenciaInicial()),true);
							String ESDEFECHA_VIJENCIA_FINAL=Util.generarSpacios(LONG_ENC_FECHA_PROCESO,FORMAT_FECHA.format(afiliacion.getFechaVigenciaFinal()),true);
							String ESDEFORMA_PAGO=Util.generarSpacios(LONG_ESTDEC_FORMA_PAGO,"",true);
							String ESDECODIGO_PAN=Util.generarSpacios(LONG_ESTDEC_CODIGO_PLAN,"",true);
							String ESDETIPO_MONEDA=Util.generarSpacios(LONG_ESTDEC_TIPO_MONEDA,vsMoneda.getCodigo(),true);
							String ESDETIPO_CAMBIO=Util.generarSpacios(LONG_ESTDEC_FECHA_TIPO_CAMBIO,"",true);
							String ESDEMONTO_PRIMA_ANUAL_NETA=Util.generarCeros(LONG_ESTDEC_MONTO_PRIMA_ANUAL_NETA,"");
							String ESDEMONTO_PRIMA_ANUAL=Util.generarCeros(LONG_ESTDEC_MONTO_PRIMA_ANUL_BRUTA,"");
							String ESDEMONTO_PRIMA_MENSUAL=Util.generarCeros(LONG_ESTDEC_MONTO_PRIMA_MENSUAL,Util.quitarSeparador(Util.toNumberFormat(afiliacion.getImportePagado(), 2)));
							String ESDEMOTIVO=Util.generarCeros(LONG_ESTDEC_MOTIVO,"");
							String ESDENUMERO_CERTIFICADO=Util.generarCeros(LONG_GEN_CERTIFICADO,afiliacion.getNumeroCertificado());
							String ESDETIPO_PROCESO=Util.generarSpacios(LONG_ENC_TIPO_DECLARACION,tipoProcesoEncabezado.getCodigo(),true);
							String ESDEPAIS_DESTINO=Util.generarSpacios(LONG_ESTDEC_PAIS_DESTINO,paisDestino.getDocigo(),true); 
							String ESDECIUDAD_DESTINO=Util.generarSpacios(LONG_ESTDEC_CIUDAD_DESTINO,afiliacion.getVsCiudad().getCodigo(),true);
						    //VARIABLES ESTRUCTURA CONTRANTE
							String ESCOTIPO_REGISTRO=Util.generarSpacios(LONG_ESTDEC_TIPO_REGISTRO,contratante.getVsTipoRegistro().getCodigo().toString(),true);
							String ESCOTIPO_PERSONA=Util.generarSpacios(LONG_GEN_TIPO_PERSONA,contratante.getVsTipoPersona().getCodigo(),true);
							String ESCOTIPO_DOCUMENTO=Util.generarCeros(LONG_GEN_TIPO_DOCUMENTO_IDENTIDAD,contratante.getVsTipoDocumento().getCodigo().toString());
							String ESCODONUMERO_CUMENTO=Util.generarCeros(LONG_GEN_NUMERO_DOCUMENTO_IDENTIDAD,contratante.getNumeroDocumento());
							String ESCOPELLIDO_PATERNO=Util.generarSpacios(LONG_GEN_APELLIDO_PATERNO,"",true);
							String ESCOAPELLIDO_MATERNO=Util.generarSpacios(LONG_GEN_APELLIDO_MATERNO,"",true);
							String ESCONOMBRES=Util.generarSpacios(LONG_GEN_NOMBRES,"",true);
							String ESCOFECHA_NACIMIENTO=Util.generarSpacios(LONG_GEN_FECHA,"",true);
							String ESCOSEXO=Util.generarCeros(LONG_GEN_SEXO,contratante.getVsSexo().getCodigo());
							String ESCODIRECCION=Util.generarSpacios(LONG_GEN_DIRECCION,contratante.getDireccion(),true);
							String ESCOUBIGEO_DEPARTAMENTO=Util.generarSpacios(LONG_GEN_UBG_DEPARTAMENTO,contratante.getUbigeoDepartamento(),true);
							String ESCOUBIGEO_PROVINCIA=Util.generarSpacios(LONG_GEN_UBG_PROVINCIA,contratante.getUbigeoProvincia(),true);
							String ESCOUBIGEO_DISTRITO=Util.generarSpacios(LONG_GEN_UBG_DISTRITO,contratante.getUbigeoDistrito(),true);
							String ESCOTELEFONO_DOMICILIO=Util.generarSpacios(LONG_GEN_TELF_DOMICILIO,"",true);
							String ESCOTELEFONO_CELULAR=Util.generarSpacios(LONG_GEN_TEFT_CELULAR,"",true);
							String ESCOESTODO_CIVIL=Util.generarCeros(LONG_GEN_ESTADO_CIVIL,String.valueOf(CODIGO_ESTCIV_OTROS));
							String ESCORAZON_SOCIAL=Util.generarSpacios(LONG_ESTCON_RAZON_SOCIAL,contratante.getRazonSocial(),true);
							String ESCOPROFESION_OCUPACION=Util.generarSpacios(LONG_ESTCON_PROFESION_OCUPACION,"",true);
							String ESCONUMERO_CUENTA=Util.generarSpacios(LONG_ESTCON_NUMERO_CUENTA,"",true);
							String ESCOTELEFONO_COMERCIAL=Util.generarSpacios(LONG_GEN_TELF_DOMICILIO,contratante.getTelefonoComercial(),true);
							String ESCONUMERO_CERTIFICADOR=Util.generarCeros(LONG_GEN_CERTIFICADO,afiliacion.getNumeroCertificado());
							/* VARIABLES DE LA ESTRUCTURA DEL ASEGURADO */
							String ESASETIPO_REGISTRO =Util.generarSpacios(LONG_GEN_TIPO_REGISTTRO,tipoRegistroAsegurado.getCodigo().toString(),true);
							String ESASETIPO_PERSONA=Util.generarSpacios(LONG_GEN_TIPO_PERSONA,afiliacion.getVsTipoPersona().getCodigo(),true);
							String ESASETIPO_DOCUMENTO=Util.generarCeros(LONG_GEN_TIPO_DOCUMENTO_IDENTIDAD,asegurado.getVsTipoDocumento().getCodigo().toString());
//							String ESASENUMERO_DOCUMENTO=Util.generarCeros(LONG_GEN_NUMERO_DOCUMENTO_IDENTIDAD,asegurado.getNumeroDocumento());
							String ESASENUMERO_DOCUMENTO="";
							if(asegurado.getVsTipoDocumento().getId().intValue()==4 || //carnet de estranjería
									asegurado.getVsTipoDocumento().getId().intValue()==6){ //Pasaporte
								ESASENUMERO_DOCUMENTO=asegurado.getNumeroDocumento().toString();
							}else{
								ESASENUMERO_DOCUMENTO=Util.generarCeros(LONG_GEN_NUMERO_DOCUMENTO_IDENTIDAD,asegurado.getNumeroDocumento());
							}
							
							String ESASEAPELLIDO_PATERNO=Util.generarSpacios(LONG_GEN_APELLIDO_PATERNO,asegurado.getApellidoPaterno(),true);
							String ESASE_APELLIDO_MATERNO=Util.generarSpacios(LONG_GEN_APELLIDO_MATERNO,asegurado.getApellidoMaterno(),true);
							String ESASENOMBRES=Util.generarSpacios(LONG_GEN_NOMBRES,asegurado.getNombres(),true);
							String ESASEFECHA_NACIMIENTO=Util.generarSpacios(LONG_GEN_FECHA,FORMAT_FECHA.format(asegurado.getFechaNacimiento()),true);
							String ESASESEXO=Util.generarSpacios(LONG_GEN_SEXO,asegurado.getVsSexo().getCodigo(),true);
							String ESASEDIRECCION=Util.generarSpacios(LONG_GEN_DIRECCION,asegurado.getDireccion(),true);
							String ESASEUBIGEO_DEPARTAMENTO=Util.generarSpacios(LONG_GEN_UBG_DEPARTAMENTO,asegurado.getUbigeoDepartamento(),true);
							String ESASEUBIGEO_PROVINCIA=Util.generarSpacios(LONG_GEN_UBG_PROVINCIA,asegurado.getUbigeoProvincia(),true);
							String ESASEUBIGEO_DISTRITO=Util.generarSpacios(LONG_GEN_UBG_DISTRITO,asegurado.getUbigeoDistrito(),true);
							String ESASETELEFONO_DOMICILIO=Util.generarSpacios(LONG_GEN_TELF_DOMICILIO,asegurado.getTelefono(),true);
							String ESASETELEFONO_CELULAR=Util.generarSpacios(LONG_GEN_TEFT_CELULAR,asegurado.getCelular(),true);
							String ESASEESTADO_CIVIL=Util.generarCeros(LONG_GEN_ESTADO_CIVIL,asegurado.getVsEstadoCivil().getCodigo().toString());
							String ESASEADICIONAL=Util.generarCeros(LONG_ESTASE_ADICIONAL,afiliacion.getAdicional()!=null?afiliacion.getAdicional().toString():""); 
							String ESASENUMERO_CERTIFICADO=Util.generarCeros(LONG_GEN_CERTIFICADO,afiliacion.getNumeroCertificado());
							
							//GUARDA LA ESTRUCTURA DE LA DECLARACION
							VSEstructuraDeclaracion estructuraDeclaracion=new VSEstructuraDeclaracion();
							estructuraDeclaracion.setVsEncabezadoAfiliacion(encabezado);
							estructuraDeclaracion.setVsTipoRegistro(tipoRegistroDeclarcion);
							estructuraDeclaracion.setFechaProceso(encabezado.getFechaProceso());
							estructuraDeclaracion.setCodigoSucursal(ESDECODIGO_SUCURSAL);
							estructuraDeclaracion.setCodigoVendedor(ESDECODIGO_VENDEDOR);
							estructuraDeclaracion.setFechaVigenciaInicial(ESDEFECHA_VIJENCIA_INICIAL);
							estructuraDeclaracion.setFechaVigenciaFinal(ESDEFECHA_VIJENCIA_FINAL);
							estructuraDeclaracion.setVsMoneda(encabezado.getVsMoneda());
							estructuraDeclaracion.setMontoPrimaAnualNeta(ESDEMONTO_PRIMA_ANUAL_NETA);
							estructuraDeclaracion.setMontoPrimaMensual(ESDEMONTO_PRIMA_MENSUAL);
							estructuraDeclaracion.setNumeroCertificado(ESDENUMERO_CERTIFICADO);
							estructuraDeclaracion.setMotivo(ESDEMOTIVO);
							estructuraDeclaracion.setVsTipoProceso(tipoProcesoEncabezado);
							estructuraDeclaracion.setVsPais(paisDestino);
							estructuraDeclaracion.setVsCiudad(afiliacion.getVsCiudad());
							estructuraDeclaracion.setEstadoRegistro(Constantes.VALUE_ACTIVO);
							UtilData.auditarRegistro(estructuraDeclaracion, getUsuario(), Executions.getCurrent());
							ServiceLocator.getVentaSeguroManager().guardarEstructuraDeclaracion(estructuraDeclaracion);
							//GUARDA LA ESTRUCTURA DEL CONTRATANTE
							VSEstructuraContratante estructuraContratante=new VSEstructuraContratante();
							estructuraContratante.setVsEncabezadoAfiliacion(encabezado);
							estructuraContratante.setVsTipoRegistro(contratante.getVsTipoRegistro());
							estructuraContratante.setVsTipoPersona(contratante.getVsTipoPersona());
							estructuraContratante.setVsTipoDocumento(contratante.getVsTipoDocumento());
							estructuraContratante.setNumeroDocumento(contratante.getNumeroDocumento());
							estructuraContratante.setVsSexo(contratante.getVsSexo());
							estructuraContratante.setDireccion(ESCODIRECCION);
							estructuraContratante.setUbigeoDepartamento(ESCOUBIGEO_DEPARTAMENTO);
							estructuraContratante.setUbigeoProvincia(ESCOUBIGEO_PROVINCIA);
							estructuraContratante.setUbigeoDistrito(ESCOUBIGEO_DISTRITO);
							estructuraContratante.setVsEstadoCivil(contratante.getVsEstadoCivil());
							estructuraContratante.setRazonSocial(ESCORAZON_SOCIAL);
							estructuraContratante.setTelefonoComercial(ESCOTELEFONO_COMERCIAL);
							estructuraContratante.setNumeroCertificado(ESCONUMERO_CERTIFICADOR);
							estructuraContratante.setEstadoRegistro(Constantes.VALUE_ACTIVO);
							UtilData.auditarRegistro(estructuraContratante, getUsuario(), Executions.getCurrent());
							ServiceLocator.getVentaSeguroManager().guardarEstructuraContrantante(estructuraContratante);
							/* GUARDA LA ESTRUCTURA DEL ASEGURADO */
							VSEstructuraAsegurado estructuraAsegurado=new VSEstructuraAsegurado();
							estructuraAsegurado.setVsEncabezadoAfiliacion(encabezado);
							estructuraAsegurado.setVsTipoRegistro(tipoRegistroAsegurado);
							estructuraAsegurado.setVsTipoPersona(afiliacion.getVsTipoPersona());
							estructuraAsegurado.setVsTipoDocumento(asegurado.getVsTipoDocumento());
							estructuraAsegurado.setNumeroDocumento(ESASENUMERO_DOCUMENTO);
							estructuraAsegurado.setApellidoParterno(ESASEAPELLIDO_PATERNO);
							estructuraAsegurado.setApellidoMaterno(ESASE_APELLIDO_MATERNO);
							estructuraAsegurado.setNombres(ESASENOMBRES);
							estructuraAsegurado.setFechaNacimiento(asegurado.getFechaNacimiento());
							estructuraAsegurado.setVsSexo(asegurado.getVsSexo());
							estructuraAsegurado.setDireccion(ESASEDIRECCION);
							estructuraAsegurado.setUbigeoDepartamento(ESASEUBIGEO_DEPARTAMENTO);
							estructuraAsegurado.setUbigeoProvincia(ESASEUBIGEO_PROVINCIA);
							estructuraAsegurado.setUbigeoDistrito(ESASEUBIGEO_DISTRITO);
							estructuraAsegurado.setTelefono(ESASETELEFONO_DOMICILIO);
							estructuraAsegurado.setCelular(ESASETELEFONO_CELULAR);
							estructuraAsegurado.setVsEstadoCivil(asegurado.getVsEstadoCivil());
							estructuraAsegurado.setNumeroCertificado(ESASENUMERO_CERTIFICADO);
							estructuraAsegurado.setAdicional(afiliacion.getAdicional()!=null?afiliacion.getAdicional():0);
							estructuraAsegurado.setEstadoRegistro(Constantes.VALUE_ACTIVO);
							UtilData.auditarRegistro(estructuraAsegurado, getUsuario(), Executions.getCurrent());
							ServiceLocator.getVentaSeguroManager().guardarEstructuraAsegurado(estructuraAsegurado);
													
							//GENERA ESTRUCTURAS EN EL ARCHIVO .TXT
							//FILA ESTRUCTURA DE LA DECLARACION
							linea=ESDETIPO_REGUSTRO;
							linea+=ESDEFECHA_PROCESO;
							linea+=ESDECODIGO_SUCURSAL;
							linea+=ESDECODIGO_VENDEDOR;
							linea+=ESDENUMERO_DECLARACIONUtil;
							linea+=ESDEFECHA_VIJENCIA_INICIAL;
							linea+=ESDEFECHA_VIJENCIA_FINAL;
							linea+=ESDEFORMA_PAGO;
							linea+=ESDECODIGO_PAN;
							linea+=ESDETIPO_MONEDA;
							linea+=ESDETIPO_CAMBIO;
							linea+=ESDEMONTO_PRIMA_ANUAL_NETA;
							linea+=ESDEMONTO_PRIMA_ANUAL;
							linea+=ESDEMONTO_PRIMA_MENSUAL;
							linea+=ESDEMOTIVO;
							linea+=ESDENUMERO_CERTIFICADO;
							linea+=ESDETIPO_PROCESO;
							linea+=ESDEPAIS_DESTINO; 
							linea+=ESDECIUDAD_DESTINO;
							bw.write(linea+NEWLINE);
							//FILA ESTRUCTURA CONTRANTE
							linea=ESCOTIPO_REGISTRO;
							linea+=ESCOTIPO_PERSONA;
							linea+=ESCOTIPO_DOCUMENTO;
							linea+=ESCODONUMERO_CUMENTO;
							linea+=ESCOPELLIDO_PATERNO;
							linea+=ESCOAPELLIDO_MATERNO;
							linea+=ESCONOMBRES;
							linea+=ESCOFECHA_NACIMIENTO;
							linea+=ESCOSEXO;
							linea+=ESCODIRECCION;
							linea+=ESCOUBIGEO_DEPARTAMENTO;
							linea+=ESCOUBIGEO_PROVINCIA;
							linea+=ESCOUBIGEO_DISTRITO;
							linea+=ESCOTELEFONO_DOMICILIO;
							linea+=ESCOTELEFONO_CELULAR;
							linea+=ESCOESTODO_CIVIL;
							linea+=ESCORAZON_SOCIAL;
							linea+=ESCOPROFESION_OCUPACION;
							linea+=ESCONUMERO_CUENTA;
							linea+=ESCOTELEFONO_COMERCIAL;
							linea+=ESCONUMERO_CERTIFICADOR;
							bw.write(linea+NEWLINE);
							/* FILA DE LA ESTRUCTURA DEL ASEGURADO*/
							linea=ESASETIPO_REGISTRO;
							linea+=ESASETIPO_PERSONA;
							linea+=ESASETIPO_DOCUMENTO;
							linea+=ESASENUMERO_DOCUMENTO;
							linea+=ESASEAPELLIDO_PATERNO;
							linea+=ESASE_APELLIDO_MATERNO;
							linea+=ESASENOMBRES;
							linea+=ESASEFECHA_NACIMIENTO;
							linea+=ESASESEXO;
							linea+=ESASEDIRECCION;
							linea+=ESASEUBIGEO_DEPARTAMENTO;
							linea+=ESASEUBIGEO_PROVINCIA;
							linea+=ESASEUBIGEO_DISTRITO;
							linea+=ESASETELEFONO_DOMICILIO;
							linea+=ESASETELEFONO_CELULAR;
							linea+=ESASEESTADO_CIVIL;
							linea+=ESASEADICIONAL; 
							linea+=ESASENUMERO_CERTIFICADO;
							bw.write(linea+NEWLINE);
							
						}
						bw.close();
						
						//Actualiza la fecha de proceso de las afiliaciones procesadas.
						ServiceLocator.getVentaSeguroManager().actualizaFechaProcesoAfiliacion(iDs);
						DlgMessage.information(Messages.getString("WndProcesaAfiliacion.information.procesoAfiliacionFin"));
						tbbGenerarArchivo.setDisabled(true);
						
						//Descarga el archivo.				
						Filedownload.save(file, "application/txt");
					}
				}
			});
			
		}catch(VSProcesaAfiliacionException pex){
			if(pex.getTipo().intValue()==VSProcesaAfiliacionException.ASEGURADOS_NULL){
				DlgMessage.information(Messages.getString("WndProcesaAfiliacion.information.noAsegurados"));
			}else if(pex.getTipo().intValue()==VSProcesaAfiliacionException.NUMERO_PULIZA_NULL){
				DlgMessage.information(Messages.getString("WndProcesaAfiliacion.information.noNumeroPoliza"));
			}else if (pex.getTipo().intValue()==VSProcesaAfiliacionException.TIPO_DECLARACION){
				DlgMessage.information(Messages.getString("WndProcesaAfiliacion.information.noTipoDeclaracion"),lblTipoDeclaracion);
			}else if (pex.getTipo().intValue()==VSProcesaAfiliacionException.FECHA_INICIO_NULL){
				DlgMessage.information(Messages.getString("WndProcesaAfiliacion.information.noFechaInicio"),lblFechaInicio);
			}else if (pex.getTipo().intValue()==VSProcesaAfiliacionException.FECHA_FINAL_NULL){
				DlgMessage.information(Messages.getString("WndProcesaAfiliacion.information.noFechaFin"),lblFechaFin);
			}else if (pex.getTipo().intValue()==VSProcesaAfiliacionException.MONEDA_NULL){
				DlgMessage.information(Messages.getString("WndProcesaAfiliacion.information.noMoneda"),lblMoneda);
			}
		
		}
	}
	
	public void onSelectListAsegurados(){
		Integer cantRegProMovimiento=3;
		Double monto=.00;
		lblCantidadRegistro.setValue(String.valueOf(lsbxAfiliados.getSelectedItems().size())); 
		lblCantidadMovimientos.setValue(String.valueOf(lsbxAfiliados.getSelectedItems().size()*cantRegProMovimiento));
		for(Listitem item: lsbxAfiliados.getSelectedItems()){
			VSAfiliacion afiliacion=item.getValue();
			monto+=afiliacion.getImportePagado();
		}
		lblMonto.setValue(Util.toNumberFormat(monto,2));
	}
	
	
}
