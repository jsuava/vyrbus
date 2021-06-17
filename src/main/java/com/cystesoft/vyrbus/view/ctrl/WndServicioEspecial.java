package com.cystesoft.vyrbus.view.ctrl;

import java.io.FileInputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.TreeMap;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.zkoss.util.media.Media;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zul.Button;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Comboitem;
import org.zkoss.zul.Intbox;
import org.zkoss.zul.Label;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listheader;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Radio;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

import com.cystesoft.vyrbus.model.bean.CanalVenta;
import com.cystesoft.vyrbus.model.bean.Cliente;
import com.cystesoft.vyrbus.model.bean.DetalleItinerario;
import com.cystesoft.vyrbus.model.bean.FormaPago;
import com.cystesoft.vyrbus.model.bean.Itinerario;
import com.cystesoft.vyrbus.model.bean.ItinerarioAgenciaLlegada;
import com.cystesoft.vyrbus.model.bean.ItinerarioAgenciaPartida;
import com.cystesoft.vyrbus.model.bean.Pasajero;
import com.cystesoft.vyrbus.model.bean.PreferenciaAlimentaria;
import com.cystesoft.vyrbus.model.bean.Ruta;
import com.cystesoft.vyrbus.model.bean.Servicio;
import com.cystesoft.vyrbus.model.bean.Sexo;
import com.cystesoft.vyrbus.model.bean.TipoComprobante;
import com.cystesoft.vyrbus.model.bean.TipoDocumento;
import com.cystesoft.vyrbus.model.bean.TipoFormaPago;
import com.cystesoft.vyrbus.model.bean.TipoMovimiento;
import com.cystesoft.vyrbus.model.bean.Ubigeo;
import com.cystesoft.vyrbus.model.bean.VentaPasaje;
import com.cystesoft.vyrbus.service.exceptions.ApellidoPaternoNullException;
import com.cystesoft.vyrbus.service.exceptions.ClienteException;
import com.cystesoft.vyrbus.service.exceptions.ColumaNullException;
import com.cystesoft.vyrbus.service.exceptions.CorrelativoException;
import com.cystesoft.vyrbus.service.exceptions.DenominacionSexoNoExisteException;
import com.cystesoft.vyrbus.service.exceptions.DuplicateSeatException;
import com.cystesoft.vyrbus.service.exceptions.FechaNacimientoNullxception;
import com.cystesoft.vyrbus.service.exceptions.ItinerarioException;
import com.cystesoft.vyrbus.service.exceptions.LiquidacionNullException;
import com.cystesoft.vyrbus.service.exceptions.NombresNullException;
import com.cystesoft.vyrbus.service.exceptions.NumeroAsientoDuplicadoException;
import com.cystesoft.vyrbus.service.exceptions.NumeroAsientoFueraRangoException;
import com.cystesoft.vyrbus.service.exceptions.NumeroAsientoNullException;
import com.cystesoft.vyrbus.service.exceptions.NumeroAsientoOcupadoException;
import com.cystesoft.vyrbus.service.exceptions.NumeroBoletoDuplicadoException;
import com.cystesoft.vyrbus.service.exceptions.NumeroDocumentoIncorrectoException;
import com.cystesoft.vyrbus.service.exceptions.NumeroDocumentoNullException;
import com.cystesoft.vyrbus.service.exceptions.NumeroPaxMayorCapacidadServicioException;
import com.cystesoft.vyrbus.service.exceptions.NumeroPisoErronioException;
import com.cystesoft.vyrbus.service.exceptions.NumeroSerieNullException;
import com.cystesoft.vyrbus.service.exceptions.PasajeroException;
import com.cystesoft.vyrbus.service.exceptions.SexoNullException;
import com.cystesoft.vyrbus.service.exceptions.TipoDocumentoNoEncontradoException;
import com.cystesoft.vyrbus.service.exceptions.TipoDocumentoNullException;
import com.cystesoft.vyrbus.service.locator.ServiceLocator;
import com.cystesoft.vyrbus.service.util.Constantes;
import com.cystesoft.vyrbus.service.util.Messages;
import com.cystesoft.vyrbus.service.util.Util;
import com.cystesoft.vyrbus.service.util.UtilData;
import com.cystesoft.vyrbus.service.util.WSFE;
import com.cystesoft.vyrbus.view.ui.DlgMessage;
import com.cystesoft.vyrbus.view.ui.WndBase;
import com.cystesoft.vyrbus.view.ui.WndSeleccionaItinerario;

public class WndServicioEspecial extends WndBase implements Serializable{
	private static final long serialVersionUID = 1L;
	private Listbox listPasajeros; 
	private Button btnBuscarItinerario;
	private Textbox txtItinerario;
	private Label lblServicio;
	private Label lblOrigen;
	private Label lblDestino;
	private Label lblFechaPartida;
	private Label lblFechaLlegada;
	private Label lblHoraPartida;
	private Label lblHoraLlegada;
	private Combobox cmbPtoEmbarque;
	private Combobox cmbAgeLlegada;
	private Radio rdBoleta;
	private Radio rdFactura;
	private Intbox ibxSerie;
	private Textbox txtInicio;
	private Radio rdFacturaEspecial;
	private Textbox txtSerieFactura;
	private Textbox txtNumeroFactura;
	private Button btnCargarArchivo;
	private Label lblNumeroPasajeros;
	private Button btnGuardarVentas;
	private Listheader lhPiso;
	private Textbox txtRuc;
	private Label lblRazonSocial;
	private Label lblDescuento;
	private Label lblTarifa;
	private Textbox txtIdCliente;
	private Window wndServicioEspecial;
	
	private DetalleItinerario detalleItinerario=null;
	private CanalVenta canalVenta=null;
	private Date fechaLiquidacion=null;
	String message="";
	
	/*Variables que refieren a las columnas del archivo excel*/
	int colNombres=0;
	int colApParteno=1;
	int colApMaterno=2;
	int colTipDocumento=3;
	int colNumDocumento=4;
	int colSexo=5;
	int colFecNacimineto=6;
	int colNumAsiento=7;
	int colPiso=8;
	
	/*
	 * (non-Javadoc)
	 * @see com.tepsa.sisvyr.view.ui.WndBase#initComponents()
	 */
	@Override
	public void initComponents() {
		listPasajeros=(Listbox)this.getFellow("listPasajeros"); 
		btnBuscarItinerario=(Button)this.getFellow("btnBuscarItinerario");
		txtItinerario=(Textbox)this.getFellow("txtItinerario");
		lblServicio=(Label)this.getFellow("lblServicio");
		lblOrigen=(Label)this.getFellow("lblOrigen");
		lblDestino=(Label)this.getFellow("lblDestino");
		lblFechaPartida=(Label)this.getFellow("lblFechaPartida");
		lblFechaLlegada=(Label)this.getFellow("lblFechaLlegada");
		lblHoraPartida=(Label)this.getFellow("lblHoraPartida");
		lblHoraLlegada=(Label)this.getFellow("lblHoraLlegada");
		cmbPtoEmbarque=(Combobox)this.getFellow("cmbPtoEmbarque");
		cmbAgeLlegada=(Combobox)this.getFellow("cmbAgeLlegada");
		rdBoleta=(Radio)this.getFellow("rdBoleta");
		rdFactura=(Radio)this.getFellow("rdFactura");
		ibxSerie=(Intbox)this.getFellow("ibxSerie");
		txtInicio=(Textbox)this.getFellow("txtInicio");
		rdFacturaEspecial=(Radio)this.getFellow("rdFacturaEspecial");
		txtSerieFactura=(Textbox)this.getFellow("txtSerieFactura");
		txtNumeroFactura=(Textbox)this.getFellow("txtNumeroFactura");
		btnCargarArchivo=(Button)this.getFellow("btnCargarArchivo");
		lblNumeroPasajeros=(Label)this.getFellow("lblNumeroPasajeros");
		btnGuardarVentas=(Button)this.getFellow("btnGuardarVentas");
		lhPiso=(Listheader)this.getFellow("lhPiso");
		txtRuc=(Textbox)this.getFellow("txtRuc");
		lblRazonSocial=(Label)this.getFellow("lblRazonSocial");
		lblDescuento=(Label)this.getFellow("lblDescuento");
		lblTarifa=(Label)this.getFellow("lblTarifa");
		txtIdCliente=(Textbox)this.getFellow("txtIdCliente");
		wndServicioEspecial=(Window)this.getFellow("wndServicioEspecial");
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.tepsa.sisvyr.view.ui.WndBase#onCreate()
	 */
	@Override
	public void onCreate() throws Exception {
		canalVenta = (CanalVenta)this.getDesktop().getSession().getAttribute(Constantes.ATRIBUTO_CANAL_VENTA);
		fechaLiquidacion = (Date)this.getDesktop().getSession().getAttribute(Constantes.ATRIBUTO_FECHA_LIQUIDACION);
		enlazarItinerario(btnBuscarItinerario);
		UtilData.cargarGenericData(cmbPtoEmbarque, false);
		UtilData.cargarGenericData(cmbAgeLlegada, false);
	}
	
	/**
	 * Abre el Archivo excel que fue previamente suvido al Sevidor.
	 * @param media: 
	 */
	public void openFileExel(Media media){
		try{
			String path=Util.getPath()+Constantes.DIRECTORY_UPLOADS+Util.separator+media.getName();
			if(path!=null){
				POIFSFileSystem fs = new POIFSFileSystem(new FileInputStream(path));
				HSSFWorkbook wb = new HSSFWorkbook(fs);
				HSSFSheet sheet = wb.getSheetAt(0);
				readFileExcel(sheet);	
			}	
		}catch (Exception e) {
			DlgMessage.error(this.getClass().getName()+" "+e.getMessage());
			e.printStackTrace();
		}
	}
	
	/**
	 * Da lectura al archivo excel.
	 * @param sheet	:
	 * @throws Exception
	 */
	private void readFileExcel(HSSFSheet sheet) throws Exception{
		String mensag="";
		int i=0;
				
		Util.limpiarListbox(listPasajeros);
		List<Pasajero>listAsientos=new ArrayList<Pasajero>();//Almacen los asientos que se van agregando, para luego validar si hay duplicados.
		
		try{
			TipoDocumento tipoDocumento=null;
			Sexo sexo=null;
			HSSFRow row = null;
			HSSFCell cellNumDoc = null;
			
			for(i=1;i<=sheet.getLastRowNum();i++){
				row = sheet.getRow(i);
				//Valida datos de las columnas
				if(row.getCell(colNombres)==null || row.getCell(colNombres).toString().trim().isEmpty())
					throw new NombresNullException();
				else if(row.getCell(colApParteno)==null || row.getCell(colApParteno).toString().trim().isEmpty())
					throw new ApellidoPaternoNullException();
				else if(row.getCell(colTipDocumento)==null || row.getCell(colTipDocumento).toString().trim().isEmpty())
					throw new TipoDocumentoNullException();
				else if(row.getCell(colNumDocumento)==null || row.getCell(colNumDocumento).toString().trim().isEmpty())
					throw new NumeroDocumentoNullException();
				else if(row.getCell(colSexo)==null || row.getCell(colSexo).toString().trim().isEmpty())
					throw new SexoNullException();
				else if(row.getCell(colFecNacimineto)==null || row.getCell(colFecNacimineto).toString().trim().isEmpty()){
					mensag=row.getCell(colApParteno).toString()+" "+row.getCell(colNombres).toString();
					throw new FechaNacimientoNullxception();
				}else if(row.getCell(colNumAsiento)==null || row.getCell(colNumAsiento).toString().trim().isEmpty())
					throw new NumeroAsientoNullException();
				
				tipoDocumento=tipoDocumento(row.getCell(colTipDocumento).toString().toUpperCase().trim());
				sexo=sexo(row.getCell(colSexo).toString().toUpperCase().trim());
				
				if(tipoDocumento==null){
					mensag=row.getCell(colNombres).toString()+" "+row.getCell(colApParteno).toString();
					throw new TipoDocumentoNoEncontradoException();
				}else if (sexo==null){
					mensag=row.getCell(colNombres).toString()+" "+row.getCell(colApParteno).toString();
					throw new DenominacionSexoNoExisteException();
				}

				Pasajero pasajero=new Pasajero();
				pasajero.setNombre(row.getCell(colNombres).toString().toUpperCase().trim());
				pasajero.setApellidoPaterno(row.getCell(colApParteno).toString().toUpperCase().trim());
				pasajero.setApellidoMaterno(row.getCell(colApMaterno).toString().trim().isEmpty()?"":row.getCell(colApMaterno).toString().toUpperCase().trim());
				pasajero.setTipoDocumento(tipoDocumento);
				cellNumDoc = row.getCell(colNumDocumento);
				String numeroDocumento="";
				
				/*Valida el formato del numero de documento*/
				if(cellNumDoc.getCellType()==HSSFCell.CELL_TYPE_STRING ){//String
					String numdoc=cellNumDoc.toString();
					numeroDocumento=numdoc;
				}else if(cellNumDoc.getCellType()==HSSFCell.CELL_TYPE_NUMERIC){ //Numeric
					Double numdoc=Double.valueOf(cellNumDoc.toString());
					numeroDocumento=String.valueOf(numdoc.longValue());
				}else{//Otro, no aceptable
					throw new NumeroDocumentoIncorrectoException();
				}

				pasajero.setNumeroDocumento(numeroDocumento.trim());
				pasajero.setSexo(sexo);
				pasajero.setNombresApellidos(pasajero.getNombre()+" "+pasajero.getApellidoPaterno()+" "+pasajero.getApellidoMaterno());
				pasajero.setFechaNacimiento(row.getCell(colFecNacimineto)!=null?Constantes.FORMAT_DATE.format(row.getCell(colFecNacimineto).getDateCellValue()): "01/01/1990");
				
				Integer numeroPisos=detalleItinerario.getItinerario().getServicio().getNumeroPisos();
				Integer numeroAsientosPiso1=detalleItinerario.getItinerario().getServicio().getNumeroAsientosPiso1();
				Integer numeroAsientosPiso2=0;
				if(detalleItinerario.getItinerario().getServicio().getNumeroAsientosPiso2()!=null)
					numeroAsientosPiso2=detalleItinerario.getItinerario().getServicio().getNumeroAsientosPiso2();
				pasajero.setNumeroPiso(Constantes.PISO_UNO+1);
				
				/* Valida la columna 8 la cual representa el numero de piso al que corresponde el asiento*/
				if(row.getCell(colPiso)!=null){
					if(!(row.getCell(colPiso).toString().trim().isEmpty())){
						Double dpiso=Double.valueOf(row.getCell(colPiso).toString().trim());
						if(dpiso.intValue()==(Constantes.PISO_DOS+1) && numeroPisos.equals(Constantes.PISO_DOS+1))
							pasajero.setNumeroPiso(Constantes.PISO_DOS+1);
						else if(dpiso.intValue()==(Constantes.PISO_UNO+1))
							pasajero.setNumeroPiso(Constantes.PISO_UNO+1);
						else
							throw new NumeroPisoErronioException();
					}
				}
								
				/*Valida la duplicidad del Númeo de Asiento en el archivo excel*/
				Double numeroAseinto=Double.valueOf(row.getCell(colNumAsiento).toString().trim());
				if(listAsientos.size()>0){
					for(Pasajero pasajeroV: listAsientos){
						if(pasajeroV.getNumeroAsiento().equals(numeroAseinto.intValue()) && pasajeroV.getNumeroPiso().equals(pasajero.getNumeroPiso())  )
							throw new NumeroAsientoDuplicadoException();
					}
				}
				pasajero.setNumeroAsiento(numeroAseinto.intValue());
				if(detalleItinerario.getItinerario().getId().equals(txtItinerario.getText()))
					throw new ItinerarioException(ItinerarioException.ITINERARIO_NULL); // ItinerarioNullException();
											
				/*Valida Asiento y piso*/
				if(pasajero.getNumeroPiso().equals(Constantes.PISO_UNO+1)){
					if(pasajero.getNumeroAsiento()>numeroAsientosPiso1)
						throw new NumeroAsientoFueraRangoException();	
				}
				if (pasajero.getNumeroPiso().equals(Constantes.PISO_DOS+1)){
					if(pasajero.getNumeroAsiento()>numeroAsientosPiso2)
						throw new NumeroAsientoFueraRangoException();
				}
								
				cargarListPasajeros(pasajero,listPasajeros);
				listAsientos.add(pasajero);
			}
													
			lblNumeroPasajeros.setValue(String.valueOf(listPasajeros.getItems().size()));
			Util.disabledBtnGuardar(listPasajeros.getItems().size()==0, btnGuardarVentas, accesoGrabar());
		
		}catch (FechaNacimientoNullxception fnnex){
			Util.limpiarListbox(listPasajeros);
			DlgMessage.information(Messages.getString("wndServicioEspecial.information.nullFechaNacimiento ")+mensag);
		}catch (NumeroAsientoFueraRangoException nafr){	
			Util.limpiarListbox(listPasajeros);
			DlgMessage.information(Messages.getString("wndServicioEspecial.information.asientoFuerraDeRango"));
		}catch (ItinerarioException inex){
			if(inex.getTipo().intValue()==ItinerarioException.ITINERARIO_NULL){
				Util.limpiarListbox(listPasajeros);
				DlgMessage.information(Messages.getString("wndServicioEspecial.information.nullItinerarioOrNoconinciden"));
			}
		}catch (NumeroPisoErronioException npnex){
			Util.limpiarListbox(listPasajeros);
			DlgMessage.information(Messages.getString("wndServicioEspecial.information.numeroPisoErronio"));
		}catch (NombresNullException nnex){
			Util.limpiarListbox(listPasajeros);
			DlgMessage.information(Messages.getString("wndServicioEspecial.information.nullNombre"));
		}catch (ApellidoPaternoNullException apnex){
			Util.limpiarListbox(listPasajeros);
			DlgMessage.information(Messages.getString("wndServicioEspecial.information.nullApPaterno"));
		}catch (TipoDocumentoNullException tdnex){
			Util.limpiarListbox(listPasajeros);
			DlgMessage.information(Messages.getString("wndServicioEspecial.information.nullTipoDocumento"));
		}catch (NumeroDocumentoNullException ndnex){
			Util.limpiarListbox(listPasajeros);
			DlgMessage.information(Messages.getString("wndServicioEspecial.information.nullNumeroDocumento"));
		}catch (SexoNullException snex){
			Util.limpiarListbox(listPasajeros);
			DlgMessage.information(Messages.getString("wndServicioEspecial.information.nullSexo"));
		}catch (NumeroAsientoNullException nanex){
			Util.limpiarListbox(listPasajeros);
			DlgMessage.information(Messages.getString("wndServicioEspecial.information.nullAsiento"));
		}catch (ColumaNullException cnex){
			Util.limpiarListbox(listPasajeros);
			DlgMessage.information(Messages.getString("wndServicioEspecial.information.nullCabecera"));
		}catch (TipoDocumentoNoEncontradoException tdneex){
			Util.limpiarListbox(listPasajeros);
			DlgMessage.information(Messages.getString("wndServicioEspecial.information.TipoDocumentoNoExiste")+" "+mensag+", no esta registrado en el Sistema.");
		}catch (DenominacionSexoNoExisteException dexnexex){
			Util.limpiarListbox(listPasajeros);
			DlgMessage.information(Messages.getString("wndServicioEspecial.information.SexoNoExiste")+" "+mensag+", no esta registrado en el Sistema.");
		}catch (NumeroDocumentoIncorrectoException ndiex){
			Util.limpiarListbox(listPasajeros);
			DlgMessage.information(Messages.getString("wndServicioEspecial.information.FormatoNumeroDocumentoInvalido")+" "+mensag+", no es válido.");
		}catch (NumeroAsientoDuplicadoException nadex){
			Util.limpiarListbox(listPasajeros);
			DlgMessage.information(Messages.getString("wndServicioEspecial.information.AsientoDuplicado"));
		}catch (Exception e) {
			Util.limpiarListbox(listPasajeros);
			DlgMessage.information("Los datos ingresados en el archivo Excel no tienen el Formato correcto o hay columnas necesarias que están vacías. (Fila "+i+")");
			e.printStackTrace();
		}
	}
	
	/**
	 * Carga datos del Pasajero al Listbox.
	 * @param pasajero : Class Pasajero
	 * @param listbox  : Listbox donde se cargan los datos del pasajero
	 */
	private void cargarListPasajeros(Pasajero pasajero, Listbox listbox){
		Listitem item=new Listitem();
		Listcell cell=new Listcell(pasajero.getNombresApellidos());
		cell.setStyle("text-align:left");
		item.appendChild(cell);
		cell=new Listcell(pasajero.getTipoDocumento().getDenominacion());
		cell.setStyle("text-align:left");
		item.appendChild(cell);
		cell=new Listcell(pasajero.getNumeroDocumento());
		cell.setStyle("text-align:left; font-size:11px !important;");
		item.appendChild(cell);
		cell=new Listcell(pasajero.getSexo().getDenominacion());
		cell.setStyle("text-align:left");
		item.appendChild(cell);
		cell=new Listcell(pasajero.getFechaNacimiento());
		cell.setStyle("font-size:11px !important");
		item.appendChild(cell);
		cell=new Listcell(pasajero.getNumeroAsiento().toString());
		cell.setStyle("text-aling:left; font-size:11px !important;");
		item.appendChild(cell);
		cell=new Listcell(pasajero.getNumeroPiso().toString());
		cell.setStyle("text-aling:left; font-size:11px !important;");
		item.appendChild(cell);
		
		item.setValue(pasajero);
		listbox.appendChild(item);
	}
	
	/**
	 * Carga datos del Itinerario
	 * @param itinerario
	 */
	private void cargarDatosItinerario(Itinerario itinerario){
		txtItinerario.setText(itinerario.getId().toString());
		lblServicio.setValue(itinerario.getServicio().getDenominacion());
		lblOrigen.setValue(itinerario.getRuta().getOrigen());
		lblDestino.setValue(itinerario.getRuta().getDestino());
		lblFechaPartida.setValue(Constantes.FORMAT_DATE.format(itinerario.getFechaPartida()));
		lblHoraPartida.setValue(itinerario.getHoraPartida());
		lblFechaLlegada.setValue(Constantes.FORMAT_DATE.format(itinerario.getFechaLlegada()));
		lblHoraLlegada.setValue(itinerario.getHoraLlegada());
		lblTarifa.setValue(Util.toNumberFormat(detalleItinerario.getTarifa(), 2));
	}
	
	/**
	 * Permite enlazar los controles a la ventana de selección de Itinerario
	 * @param button :ha este Button se le adjuntara un listener con la llamada a la ventana de selección de itinerario
	 * @see WndItinerario: 
	 */
	public  void enlazarItinerario(final Button button) {
		button.setTooltiptext("Seleccionar Itinerario");
		button.addEventListener(Events.ON_CLICK, new EventListener<Event>() {
			@Override
			public void onEvent(Event event) throws Exception {
				final WndSeleccionaItinerario selectIti = new WndSeleccionaItinerario();
				boolean buscarVentanaParent = true;
				Component oComponent = button.getParent();
				while(buscarVentanaParent){
					 if(oComponent instanceof Window) {
						 oComponent.appendChild(selectIti);
						 buscarVentanaParent = false;
					 }else{
					 	oComponent = oComponent.getParent();
					 }
				}
				selectIti.servicoEspecial=true;
				selectIti.onCreate();
				selectIti.setMode("modal");
				selectIti.setVisible(true);
				selectIti.addEventListener(Events.ON_SELECT, new EventListener<Event>() {
					@Override
					public void onEvent(Event event) throws Exception {
						limpiaControles(false);
						detalleItinerario= new DetalleItinerario();
						detalleItinerario=ServiceLocator.getDetalleItinerarioManager().buscarPorId(selectIti.getIdDetalleItinerario());
						detalleItinerario.setItinerario(selectIti.getItinerario());
						cargarDatosItinerario(detalleItinerario.getItinerario());
						onLoadPuntoEmbarque(detalleItinerario);
						onLoadPuntoDesembarque(detalleItinerario);
						Util.disabledBtnExportar(false, btnCargarArchivo, true);
						if(detalleItinerario.getItinerario().getServicio().getNumeroPisos().equals(2))
							lhPiso.setVisible(true);
						else lhPiso.setVisible(false);
						Util.limpiarListbox(listPasajeros);
						lblNumeroPasajeros.setValue(String.valueOf(0));
					}
				});
			}
		});
	}
	
		
	/**
	 * Genera Ventas para cada uno de los pasajeros importados.
	 * @throws Exception
	 */
	public void guardarVentas() throws Exception{
		try{
			if(detalleItinerario.getItinerario()==null || txtItinerario.getText().trim().isEmpty())
				throw new ItinerarioException(ItinerarioException.ITINERARIO_NULL); 
			else if (!(cmbPtoEmbarque.getSelectedItem().getValue() instanceof ItinerarioAgenciaPartida))
				throw new ItinerarioException(ItinerarioException.AGENCIA_PARTIDA_NULL);
			else if (!(cmbAgeLlegada.getSelectedItem().getValue() instanceof ItinerarioAgenciaLlegada))
				throw new ItinerarioException(ItinerarioException.AGENCIA_LLEGADA_NULL); 
//			else if (rdBoleta.isChecked()){
//				if(ibxSerie.getText().trim().isEmpty() || ibxSerie.getText().trim().length()!=3)
//					throw new NumeroSerieNullException();
//				else if(txtInicio.getText().trim().isEmpty())
//					throw new CorrelativoException(CorrelativoException.INICIAL_NULL);
//				else{
//					message=String.valueOf(validaBoletosUtilizados(txtInicio.getText().trim()));
//					if(Integer.valueOf(message)>0)
//						throw new NumeroBoletoDuplicadoException();
//				}
//			}
			if (rdFacturaEspecial.isChecked()){
				if(txtSerieFactura.getText().trim().isEmpty()){
					throw new NumeroSerieNullException();
				}else if (txtSerieFactura.getText().trim().toUpperCase().indexOf("F")==-1){
					throw new NumeroSerieNullException();
				}else if (txtSerieFactura.getText().trim().length()!=4){
					throw new NumeroSerieNullException();
				}else if (txtNumeroFactura.getText().trim().isEmpty()){
					throw new CorrelativoException(CorrelativoException.ACTUAL_NULL);
				}
				if(txtRuc.getText().trim().isEmpty() || txtIdCliente.getText().trim().isEmpty())
					throw new ClienteException(ClienteException.CLIENTE_NULL); // ClienteNullException();
			}else{
				validaBoletosUtilizados(txtInicio.getText().trim());
			}
			
			if(listPasajeros.getItems().size()==0)
				throw new PasajeroException();
			else if (detalleItinerario.getItinerario().getServicio().getNumeroPisos()==1){
				if(detalleItinerario.getItinerario().getServicio().getNumeroAsientosPiso1()<listPasajeros.getItems().size()){
					throw new NumeroPaxMayorCapacidadServicioException();
				}
			}else if (detalleItinerario.getItinerario().getServicio().getNumeroPisos()==2){
				Servicio servicio=detalleItinerario.getItinerario().getServicio();
				Integer capServicio=servicio.getNumeroAsientosPiso1()+servicio.getNumeroAsientosPiso2();
				if(capServicio<listPasajeros.getItems().size()){
					throw new NumeroPaxMayorCapacidadServicioException();
				}
			}
			
			/*Recupera la fecha de emision del comprobante 28/12/2016 - jabanto*/
			if(rdFacturaEspecial.isChecked()){
				String fechaFactura=ServiceLocator.getTitanManager().buscarFechaFacturaEspecial(txtSerieFactura.getText().trim().toUpperCase(), txtNumeroFactura.getText().trim().toUpperCase(),txtRuc.getText().trim());
				if(fechaFactura==null){
					DlgMessage.information("El Número de Factura que ha ingresado no esta registrada o no le pertenece al Cliente.");
					return;
				}
				fechaLiquidacion=Constantes.FORMAT_DATE.parse(fechaFactura);
			}else{
				fechaLiquidacion=(Date)this.getDesktop().getSession().getAttribute(Constantes.ATRIBUTO_FECHA_LIQUIDACION);
				//Valida que la fecha de la liquidacion
				if(fechaLiquidacion==null){
					throw new LiquidacionNullException();
				}
			}
						
			/*Realiza las validaciones necesarias a los datos del los pasajeros importados*/
			validadDatosPasajeros();
				
			Messagebox.show(Messages.getString("wndServicioEspecial.question.confimarProceso"),DlgMessage.NOMBREAPLICACION, DlgMessage.BTN_YESNO, Messagebox.QUESTION, new EventListener<Event>() {
				@Override
				public void onEvent(Event e) throws Exception {
					if(e.getName().equals("onYes")){
						List<VentaPasaje>lstVentas= new  ArrayList<>();
						for(Listitem item: listPasajeros.getItems()){
							Pasajero pasajero=buscarPasajero(((Pasajero)item.getValue()));
							VentaPasaje ventaPasaje= new VentaPasaje();
							ventaPasaje.setEsFechaAbierta(Constantes.FALSE_VALUE);							
							ventaPasaje.setItinerario(detalleItinerario.getItinerario());
							Ruta ruta=ServiceLocator.getRutaManager().buscarPorId(detalleItinerario.getRuta().getId().longValue());
							ventaPasaje.setRuta(ruta);
							if (!(txtIdCliente.getText().trim().isEmpty())){
								Cliente cliente= new Cliente();
								cliente.setId(Long.valueOf(txtIdCliente.getText()));
								ventaPasaje.setCliente(cliente);
							}
							ventaPasaje.setPasajero(pasajero);
							FormaPago formaPago = new FormaPago();
							formaPago.setId(Constantes.ID_FORPAG_CONTADO);
							ventaPasaje.setFormaPago(formaPago);
							ventaPasaje.setServicio(detalleItinerario.getItinerario().getServicio());
							TipoFormaPago tipoFormaPago = new TipoFormaPago();
							tipoFormaPago.setId(Constantes.ID_TIPFORPAG_EFECTIVO);
							ventaPasaje.setTipoFormaPago(tipoFormaPago);
							TipoComprobante tipoComprobante = new TipoComprobante();
							if(rdBoleta.isChecked()){
//								ventaPasaje.setNumeroBoleto(ibxSerie.getText().trim().toUpperCase()+"-"+txtInicio.getText().trim());
								ventaPasaje.setNumeroBoleto("000-0000000");
								tipoComprobante.setId(Constantes.ID_TIPCOM_BOLETA_VENTA);
							}else if(rdFactura.isChecked()){
								ventaPasaje.setNumeroBoleto("000-0000000");
								tipoComprobante.setId(Constantes.ID_TIPCOM_FACTURA);
							}else{ 
								ventaPasaje.setNumeroBoleto(txtSerieFactura.getText().trim().toUpperCase()+"-"+txtNumeroFactura.getText().trim());
								ventaPasaje.setServicioEspecialFactura(true);
								tipoComprobante.setId(Constantes.ID_TIPCOM_FACTURA);
							}
							
//							tipoComprobante.setId(Constantes.ID_TIPCOM_BOLETO_VIAJE);
							ventaPasaje.setTipoComprobante(tipoComprobante);
							ventaPasaje.setTipoMovimiento(new TipoMovimiento(Constantes.ID_TIPMOV_EFECTIVO));
							ventaPasaje.setNumeroAsiento(Integer.valueOf(((Pasajero)item.getValue()).getNumeroAsiento()));
							ItinerarioAgenciaPartida itinerarioAgenciaPartida = (ItinerarioAgenciaPartida)cmbPtoEmbarque.getSelectedItem().getValue();
							ventaPasaje.setAgenciaPartida(itinerarioAgenciaPartida.getAgencia());
							ventaPasaje.setFechaPartida(detalleItinerario.getFechaPartida());
							ventaPasaje.setHoraPartida(detalleItinerario.getHoraPartida());
							ItinerarioAgenciaLlegada itinerarioAgenciaLlegada = (ItinerarioAgenciaLlegada)cmbAgeLlegada.getSelectedItem().getValue();
							ventaPasaje.setAgenciaLlegada(itinerarioAgenciaLlegada.getAgencia());
							ventaPasaje.setFechaLlegada(detalleItinerario.getFechaLlegada());
							ventaPasaje.setHoraLllegada(detalleItinerario.getHoraLlegada());
							PreferenciaAlimentaria preferenciaAlimentaria = new PreferenciaAlimentaria();
							preferenciaAlimentaria.setId(Constantes.ID_PREALIM_MENU_DEL_DIA);
							ventaPasaje.setPreferenciaAlimentaria(preferenciaAlimentaria);
							ventaPasaje.setNumeroPiso(((Pasajero)item.getValue()).getNumeroPiso()-1);
							ventaPasaje.setSecuencial(0);
							ventaPasaje.setTarifa(detalleItinerario.getTarifa());
							ventaPasaje.setRecargo(0.00);
							ventaPasaje.setDescuento(0.00);
							ventaPasaje.setPenalidad(0.0);
							ventaPasaje.setAcuenta(0.0);
							ventaPasaje.setImportePagadoByDiferencia(0.00);
							ventaPasaje.setImportePagado(ventaPasaje.getTarifa()-(ventaPasaje.getTarifa()*(ventaPasaje.getDescuento()/100)));
							ventaPasaje.setImportePagadoEfectivo(.00);
							ventaPasaje.setImportePagadoTarjeta(.00);
							ventaPasaje.setTipoTransaccion(Constantes.TIPO_OPERACION_VENTA);
							ventaPasaje.setFechaCaducidad(new Date(new Date().getTime()+(Constantes.TIEMPO_CADUCA_BOLETO * Constantes.MILISEGUNDOS_X_DIA)));
							ventaPasaje.setAgencia(getAgencia());
							ventaPasaje.setUsuario(getUsuario());
							ventaPasaje.setCanalVenta(canalVenta);	
							ventaPasaje.setFechaLiquidacion(fechaLiquidacion);
							ventaPasaje.setEstadoRegistro(Constantes.VALUE_ACTIVO);
							ventaPasaje.setLiquidacion(null);
							ventaPasaje.setIdaRetorno(Constantes.FALSE_VALUE);
							ventaPasaje.setNumeroControl("DEMO");
							ventaPasaje.setEstadoDocumento(Constantes.ESTADO_DOCUMENTO_PAGADO);
							ventaPasaje.setEsRemoto(false);
							UtilData.auditarRegistro(ventaPasaje, false, getUsuario(), Executions.getCurrent());
							ventaPasaje.setUsuarioHardware(getUsuarioHardware());
							ServiceLocator.getVentaPasajesManager().guardarVenta(ventaPasaje, false, true, false, true);
							
							
							/** 28/12/2016 - jabanto */
							/*Valida si son emitidos por el counter, para envialos al Servidor de F.E.*/
							if(rdBoleta.isChecked() || rdFactura.isChecked())
								lstVentas.add(ventaPasaje);
							
							/*===========================End biegin 28/12/2016 - jabanto*/
//							if(rdBoleta.isChecked()){
//								ventaPasaje = ServiceLocator.getVentaPasajesManager().buscarVentaById(ventaPasaje.getId());
								
								/*Implementacion para el nuevo formato 01/02/2016 - jabanto */
//								boolean formato=UtilData.getFormatoImprecion(getAgencia().getId(), getTipocomprobante().getId(), getUsuarioHardware().getId());
//								File file= CreateDocument.crearBoleto(ventaPasaje,formato);
								
//								if(getUsuarioHardware().getPrintApplet().intValue()==Constantes.TRUE_VALUE){
////									String fileBoleto = Constantes.URL_FORMATOS_BOLETOS+ventaPasaje.getNumeroControl()+".txt";
//									String fileBoleto = Constantes.URL_FORMATOS_BOLETOS+Constantes.CLAVE_PAHT+ventaPasaje.getNumeroControl()+".txt";
//									Window win = (Window)Executions.createComponents("imprimir.zul", null, null);
//									win.setAttribute("formato", WndImprimir.FORMAT_BOLETO);
//									win.setAttribute("msg", "Imprimiendo el Boleto de Viaje "+ventaPasaje.getNumeroBoleto()+" ... ");
//									win.setAttribute("urlDocumento", fileBoleto);
//									win.doPopup();
//								}else{
//									//Descarga el archivo para la impresion.
//									Util.descargarArchivo(file);
//								}
//								
//								txtInicio.setText(generaCerosIzquierda(nuevoNumeroBoleto(Integer.valueOf(txtInicio.getText())).toString()));
//							}
						}
						
						
						/** 28/12/2016 - jabanto */
						/*Valida si son emitidos por el counter, para envialos al Servidor de F.E.*/
						if(rdBoleta.isChecked() || rdFactura.isChecked()){
							List<VentaPasaje>lstVentaSendFE= new ArrayList<>();
							for(VentaPasaje oVentaPasaje: lstVentas){
								VentaPasaje ventaPasaje= ServiceLocator.getVentaPasajesManager().buscarVentaById(oVentaPasaje.getId());
								lstVentaSendFE.add(ventaPasaje);
							}
							/*Realiza el envio*/
							WSFE.sendVenta(lstVentaSendFE, wndServicioEspecial, true, null);
						}
						
						
						DlgMessage.information(Messages.getString("wndServicioEspecial.information.confirmaGuardado"));
						Util.disabledBtnExportar(true, btnCargarArchivo, false);
						Util.disabledBtnGuardar(true, btnGuardarVentas, false);
					}
				}
			});
		
		}catch (FechaNacimientoNullxception fnnex){
			DlgMessage.information(Messages.getString("wndServicioEspecial.information.nullFechaNacimiento")+message);
		}catch (NumeroBoletoDuplicadoException nbdex){
			DlgMessage.information(Messages.getString("De los Boletos que va ha utilizar, "+message+" están utilizados"),txtInicio);
		}catch (ClienteException cl){
			if(cl.getTipo().intValue()==ClienteException.CLIENTE_NULL){
				DlgMessage.information(Messages.getString("wndServicioEspecial.information.nullCliente"),txtRuc);
			}
		}catch (ItinerarioException inex){
			if(inex.getTipo().intValue()==ItinerarioException.ITINERARIO_NULL){
				DlgMessage.information(Messages.getString("wndServicioEspecial.information.nullItinerario"),btnBuscarItinerario);
			}else if (inex.getTipo().intValue()==ItinerarioException.AGENCIA_LLEGADA_NULL){
				DlgMessage.information(Messages.getString("wndServicioEspecial.information.nullAgenciaLlegada"),cmbAgeLlegada);
			}else if(inex.getTipo().intValue()==ItinerarioException.AGENCIA_PARTIDA_NULL){
				DlgMessage.information(Messages.getString("wndServicioEspecial.information.nullPtoEmbarque"),cmbPtoEmbarque);
			}
		}catch(NumeroSerieNullException nsnex){
			DlgMessage.information(Messages.getString(rdBoleta.isChecked()?"wndServicioEspecial.information.nullSerieBoletoViaje":"wndServicioEspecial.information.nullSerieFactura"));
			if(rdBoleta.isChecked())
				ibxSerie.setFocus(true);
			else txtSerieFactura.setFocus(true);
		}catch(CorrelativoException c){
			if(c.getTipo().intValue()==CorrelativoException.INICIAL_NULL){
				DlgMessage.information(Messages.getString("wndServicioEspecial.information.nullNumeroBoletoInicial"),txtInicio);
			}else if(c.getTipo().intValue()==CorrelativoException.ACTUAL_NULL){
				DlgMessage.information(Messages.getString("wndServicioEspecial.information.nullNumeroFactura"),txtNumeroFactura);
			}
		}catch (PasajeroException pnex){
			DlgMessage.information(Messages.getString("wndServicioEspecial.information.nullListPasajeros"));
		}catch (NumeroPaxMayorCapacidadServicioException npmcsex){
			DlgMessage.information(Messages.getString("wndServicioEspecial.information.pasajerosExcedenCapacidadServicio"));
		}catch (NumeroAsientoFueraRangoException nafrex){
			DlgMessage.information(Messages.getString(message));
		}catch (NumeroDocumentoIncorrectoException ndeex){
			DlgMessage.information(Messages.getString("wndServicioEspecial.information.numeroDoctPasajeroIncorrecto")+" "+message+".");
		}catch (NumeroDocumentoNullException ndnuex){
			DlgMessage.information(Messages.getString("wndServicioEspecial.information.nullNumeroDoctPasajero")+" "+message+".");
		}catch (TipoDocumentoNullException tdnex){
			DlgMessage.information(Messages.getString("wndServicioEspecial.information.nullTipoDocumentoPasajero")+" "+message+".");
		}catch (NombresNullException pnex){
			DlgMessage.information(Messages.getString("wndServicioEspecial.information.nullNombresApellidosPasajero"));
		}catch (SexoNullException snex){
			DlgMessage.information(Messages.getString("wndServicioEspecial.information.nullSexoPasajero")+" "+message+".");
		}catch (NumeroAsientoDuplicadoException nadex){
			DlgMessage.information(Messages.getString("wndServicioEspecial.information.asientosDuplicadosPasajero")+" "+message+".");
		}catch (NumeroAsientoNullException nanex){
			DlgMessage.information(Messages.getString("wndServicioEspecial.information.nullAsientoPasajero")+" "+message+".");
		}catch (NumeroAsientoOcupadoException naoex){
			DlgMessage.information(Messages.getString("wndServicioEspecial.information.asientosOcupados")+" "+message+".");
		}catch (DuplicateSeatException dpas){
			DlgMessage.information(Messages.getString("WndVentaReserva.information.asientoVendido"));
		}catch (LiquidacionNullException  lexc) {
			DlgMessage.information(Messages.getString("WndVentaReserva.information.noLiquidacion"));
		}catch (Exception e) {
			e.printStackTrace();
			DlgMessage.error(this.getClass().getName()+" "+e.getMessage());
		}
	}
	
	/**
	 * Valida los datos del los pasajeros cargados del excel antes de guardar.
	 * @throws Exception
	 */
	private void validadDatosPasajeros() throws Exception{
		try{
			message="";
			ArrayList<Pasajero>arrayListPax= new ArrayList<Pasajero>();
			
			for(Listitem item: listPasajeros.getItems()){
				Pasajero pasajero=item.getValue();
				TipoDocumento tipoDocumento=pasajero.getTipoDocumento();
				if(pasajero.getNombresApellidos()==null)
					throw new NombresNullException();
				else if (tipoDocumento==null){
					message=pasajero.getNombresApellidos();
					throw new TipoDocumentoNullException();
				}else if(tipoDocumento.getId().equals(Constantes.ID_TIPDOC_DNI)){
					if (Util.isNumeric(pasajero.getNumeroDocumento())==false || pasajero.getNumeroDocumento().toString().length()!=8){
						message=pasajero.getNombresApellidos();
						throw new NumeroDocumentoIncorrectoException();
					}
				}
				if (pasajero.getNumeroDocumento()==null){
					message=pasajero.getNombresApellidos();
					throw new NumeroDocumentoNullException();
				}else if (pasajero.getSexo()==null){
					message=pasajero.getNombresApellidos();
					throw new SexoNullException();
				}else if(pasajero.getNumeroAsiento()==null){
					message=pasajero.getNombresApellidos();
					throw new NumeroAsientoNullException();
				}else if (arrayListPax.size()>0){//Valida posibles asientos duplicados en la lista. 
					for(Pasajero pasajeros: arrayListPax){
						if(pasajero.getNumeroAsiento().equals(pasajeros.getNumeroAsiento()) && pasajero.getNumeroPiso().equals(pasajeros.getNumeroPiso())){
							message=pasajero.getNombresApellidos()+" y "+pasajeros.getNombresApellidos();
							throw new NumeroAsientoDuplicadoException();
						}
					}
				}
				if(pasajero.getFechaNacimiento()==null){
					message=pasajero.getNombresApellidos();
					throw new FechaNacimientoNullxception();
				}
				
				/*Valida Asiento y piso*/
				Integer numeroAsientosPiso1=detalleItinerario.getItinerario().getServicio().getNumeroAsientosPiso1();
				Integer numeroAsientosPiso2=0;
				if(detalleItinerario.getItinerario().getServicio().getNumeroAsientosPiso2()!=null)
					numeroAsientosPiso2=detalleItinerario.getItinerario().getServicio().getNumeroAsientosPiso2();
				if(pasajero.getNumeroPiso().equals(Constantes.PISO_UNO+1)){
					if(pasajero.getNumeroAsiento()>numeroAsientosPiso1){
						message="El Número de Asiento "+pasajero.getNumeroAsiento()+" del Pasajero "+ pasajero.getNombresApellidos()+" es mayor a la Capasidad del Piso del Bus.";
						throw new NumeroAsientoFueraRangoException();
					}
				}else if (pasajero.getNumeroPiso().equals(Constantes.PISO_DOS+1)){
					if(pasajero.getNumeroAsiento()>numeroAsientosPiso2){
						message="El Número de Asiento "+pasajero.getNumeroAsiento()+" del Pasajero "+ pasajero.getNombresApellidos()+" es mayor a la Capasidad del Piso del Bus.";
						throw new NumeroAsientoFueraRangoException();
					}
				}
				
				arrayListPax.add(pasajero);
			}
			
			/*	Valida que los asientos de los pasajeros no esten ocupados*/
			String numeroAsientos=""; Integer numeroPiso=0;
			List<VentaPasaje> listOcupabilidad=new ArrayList<VentaPasaje>();
			for(int x=0; x <detalleItinerario.getItinerario().getServicio().getNumeroPisos(); x++){
				numeroPiso=detalleItinerario.getItinerario().getServicio().getNumeroPisos();
				for(Pasajero pasajero: arrayListPax){
					if(numeroPiso.equals(pasajero.getNumeroPiso())){
						if(numeroAsientos.trim().isEmpty())
							numeroAsientos="'"+pasajero.getNumeroAsiento().toString()+"'";
						else
							numeroAsientos+=",'"+pasajero.getNumeroAsiento().toString()+"'";
					}
				}
				listOcupabilidad=ServiceLocator.getVentaPasajesManager().validaOcupabilidad(detalleItinerario.getItinerario().getId(), detalleItinerario.getRuta().getId(), numeroAsientos, numeroPiso);
				for(VentaPasaje venta: listOcupabilidad){
					if(message.trim().isEmpty())
						message="( "+venta.getNumeroAsiento().toString();
					else
						message+=", "+venta.getNumeroAsiento().toString();
				}
				if (!(message.trim().isEmpty()))
					message+=" )";
			}
			if(listOcupabilidad.size()>0)
				throw new NumeroAsientoOcupadoException();

		}catch (FechaNacimientoNullxception fnnex){
			throw new FechaNacimientoNullxception(); 
		}catch (NumeroAsientoOcupadoException nao){
			throw new NumeroAsientoOcupadoException();
		}catch (NumeroAsientoFueraRangoException nafrex){
			throw new NumeroAsientoFueraRangoException();
		}catch (NumeroDocumentoIncorrectoException ndeex){
			throw new NumeroDocumentoIncorrectoException();
		}catch (NumeroDocumentoNullException ndnuex){
			throw new NumeroDocumentoNullException();
		}catch (TipoDocumentoNullException tdnex){
			throw new TipoDocumentoNullException();
		}catch (NombresNullException pnex){
			throw new  PasajeroException();
		}catch (SexoNullException snex){
			throw new SexoNullException();
		}catch (NumeroAsientoDuplicadoException nadex){
			throw new NumeroAsientoDuplicadoException();
		}catch (NumeroAsientoNullException nanex){
			throw new NumeroAsientoNullException();
		}
	}
	
	
	/**
	 * Valida Los boletos a generar no estén registrados en el sistema
	 * @param boletoInicial : Boleto con el se iniciará la inpresión.
	 * @return
	 * @throws Exception
	 */
	private Integer validaBoletosUtilizados(String boletoInicial) throws Exception{
		Integer cantPax=(listPasajeros.getItems().size())-1;
		String serie=ibxSerie.getText().trim();
		String boletoInicio=serie+"-"+generaCerosIzquierda(boletoInicial);
		String boletoFinal=serie+"-"+generaCerosIzquierda(String.valueOf(Long.valueOf(boletoInicial)+cantPax));
		Integer cant=ServiceLocator.getVentaPasajesManager().validaBoletos_ServicioEspecial(boletoInicio, boletoFinal);
		
		return cant;
	}
	
	/**
	 * Realiza la busqueda del pasajero.
	 * @param pasajero
	 * @return
	 * @throws Exception
	 */
	private Pasajero buscarPasajero(Pasajero pasajero) throws Exception{
		Pasajero pax=null;
		TreeMap<String, Object> criteriosBusqueda = new TreeMap<String, Object>();
		criteriosBusqueda.put("numeroDocumento", pasajero.getNumeroDocumento().trim());
		criteriosBusqueda.put("estadoRegistro", Constantes.VALUE_ACTIVO);
		List<Pasajero>lisPax=ServiceLocator.getPasajeroManager().buscarPorX(criteriosBusqueda, null);
		if(lisPax.size()>0)
			pax=lisPax.get(0);
		else
			pax=GuardaPasajero(pasajero);
			
		return pax;
	}
	
	
	/**
	 * Guarda el Pasajero de ser el caso que no exsista
	 * @param pasajero :Class Pasajero
	 * @return
	 * @throws Exception 
	 */
	private Pasajero GuardaPasajero(Pasajero pasajero) throws Exception{
		String UBIGEO_LIMA="150101";
		Pasajero oPasajero = new Pasajero();
		Ubigeo oUbigeo= new Ubigeo();
		oUbigeo.setId(UBIGEO_LIMA);
				
		oPasajero.setTipoDocumento(pasajero.getTipoDocumento());
		oPasajero.setNumeroDocumento(pasajero.getNumeroDocumento().trim().toUpperCase());
		oPasajero.setApellidoPaterno(pasajero.getApellidoPaterno().trim().toUpperCase());
		oPasajero.setApellidoMaterno(pasajero.getApellidoMaterno().trim().toUpperCase());
		oPasajero.setNombre(pasajero.getNombre().trim().toUpperCase());
		oPasajero.setNombresApellidos(pasajero.getNombresApellidos());
		oPasajero.setFechaNacimiento(pasajero.getFechaNacimiento()!=null?pasajero.getFechaNacimiento(): Util.DatetoString(Constantes.FECHA_NULL, Constantes.DATE_FORMAT));
		oPasajero.setSexo(pasajero.getSexo());
		oPasajero.setUbigeo(oUbigeo);
		oPasajero.setKilometros(.00);
		oPasajero.setIndeseable(0);
		oPasajero.setAgencia(getAgencia());
		oPasajero.setEstadoRegistro(Constantes.VALUE_ACTIVO);
		UtilData.auditarRegistro(oPasajero, getUsuario(), Executions.getCurrent());
		ServiceLocator.getPasajeroManager().guardar(oPasajero);
		return oPasajero;
	}
	
	
	/**
	 * Busca el tipo de documento del pasajero, el cual esta ingresado en el excel.
	 * @param tipoDocumento : Nombre del tipo de ducumento.
	 * @return (null) si el nombre de documento no existe, caso contrario todos los datos correspondientes al tipo de documento.
	 * @throws Exception
	 */
	private TipoDocumento tipoDocumento(String tipoDocumento) throws Exception{
		TipoDocumento tdocumento=null;
		TreeMap<String, Object> criteriosBusqueda = new TreeMap<String, Object>();
		criteriosBusqueda.put("denominacion", tipoDocumento.toString().trim().toUpperCase());
		criteriosBusqueda.put("estadoRegistro", Constantes.VALUE_ACTIVO);
		List<TipoDocumento> list=ServiceLocator.getTipoDocumentoManager().buscarPorX(criteriosBusqueda, null);
		if(list.size()>0){
			tdocumento= new TipoDocumento();
			tdocumento=list.get(0);
		}
		return tdocumento;
	}
	
	/**
	 * Busca el sexo del pasajero, el cual esta ingresado el Excel.
	 * @param sexo : Nombre denominado para la busqueda.
	 * @return (null) si la denominacion no existe el sistema, caso contrario todos los datos correspondientes a la Clase Sexo.
	 * @throws Exception
	 */
	private Sexo sexo(String sexo) throws Exception{
		Sexo sex=null;
		TreeMap<String, Object> criteriosBusqueda = new TreeMap<String, Object>();
		criteriosBusqueda.put("denominacion", sexo.toString().trim().toUpperCase());
		criteriosBusqueda.put("estadoRegistro", Constantes.VALUE_ACTIVO);
		List<Sexo>list=ServiceLocator.getSexoManager().buscarPorX(criteriosBusqueda, null);
		if(list.size()>0)
			sex=list.get(0);
		return sex;
	}
	
	/**
	 * Carga los puntos de embarque.
	 * @param detItinerario	: Itinerario del cual se desea cargar los puntos de embarque.
	 * @throws Exception
	 */
	private void onLoadPuntoEmbarque(DetalleItinerario detItinerario){
		try{
			cmbPtoEmbarque.getItems().clear();
			
			ArrayList<ItinerarioAgenciaPartida> arrayItiAgePartida = new ArrayList<ItinerarioAgenciaPartida>();
			arrayItiAgePartida = ServiceLocator.getItinerarioManager().buscarAgenciasPartida(detItinerario.getItinerario().getId(), Constantes.VALUE_ACTIVO, detItinerario.getRuta().getLocalidadOrigen().getId());
//			if(detItinerario.getItinerario().getAgenciaPartida().getId().intValue()==detItinerario.getAgenciaPartida().getId().intValue())
//				arrayItiAgePartida = ServiceLocator.getItinerarioManager().buscarAgenciasPartida(detItinerario.getItinerario().getId(), Constantes.VALUE_ACTIVO);
//			else{
//				ItinerarioAgenciaPartida itiAgePartida = new ItinerarioAgenciaPartida();
//				Agencia agencia = new Agencia();
//				agencia.setId(detItinerario.getAgenciaPartida().getId());
//				agencia.setDenominacion(detItinerario.getAgenciaPartida().getDenominacion());
//				itiAgePartida.setAgencia(agencia);
//				itiAgePartida.setHoraPartida(detItinerario.getAgenciaPartida().getHoraPartida());
//				arrayItiAgePartida.add(itiAgePartida);
//			}
			UtilData.cargarGenericData(cmbPtoEmbarque, false);
			for(ItinerarioAgenciaPartida itiAgePartida : arrayItiAgePartida){
				Comboitem item = new Comboitem(itiAgePartida.getAgencia().getDenominacion());
				item.setValue(itiAgePartida);
				cmbPtoEmbarque.appendChild(item);
				if(arrayItiAgePartida.size()==1){
					cmbPtoEmbarque.setSelectedItem(item);
					lblHoraPartida.setValue(itiAgePartida.getHoraPartida());
				}else if(detItinerario.getAgenciaPartida().getId().intValue()==itiAgePartida.getAgencia().getId().intValue()){
					cmbPtoEmbarque.setSelectedItem(item);
					lblHoraPartida.setValue(itiAgePartida.getHoraPartida());
				}
			}			
		}catch(Exception ex){
			ex.printStackTrace();
			DlgMessage.error(this.getClass().getName()+" "+ex.getMessage());
		}
	}
	
	
	/**
	 * Cargamos los puntos de desembarque.
	 * @param detItinerario	: Itinerario del cual deseamos cargar los puntos de desembarque.
	 * @throws Exception
	 */
	private void onLoadPuntoDesembarque(DetalleItinerario detItinerario) {
		try{
			cmbAgeLlegada.getItems().clear();
			ArrayList<ItinerarioAgenciaLlegada> arrayItiAgeLlegada = new ArrayList<ItinerarioAgenciaLlegada>();
			arrayItiAgeLlegada = ServiceLocator.getItinerarioManager().buscarAgenciasLlegada(detItinerario.getItinerario().getId(), Constantes.VALUE_ACTIVO, detItinerario.getRuta().getLocalidadDestino().getId());
//			if(detItinerario.getItinerario().getAgenciaLlegada().getId().intValue()==detItinerario.getAgenciaLlegada().getId().intValue())
//				arrayItiAgeLlegada = ServiceLocator.getItinerarioManager().buscarAgenciasLlegada(detItinerario.getItinerario().getId(), Constantes.VALUE_ACTIVO);
//			else{
//				ItinerarioAgenciaLlegada itiAgeLlegada = new ItinerarioAgenciaLlegada();
//				Agencia agencia = new Agencia();
//				agencia.setId(detItinerario.getAgenciaLlegada().getId());
//				agencia.setDenominacion(detItinerario.getAgenciaLlegada().getDenominacion());
//				itiAgeLlegada.setAgencia(agencia);
//				itiAgeLlegada.setHoraLlegada(detItinerario.getAgenciaLlegada().getHoraPartida());
//				arrayItiAgeLlegada.add(itiAgeLlegada);
//			}
			
			UtilData.cargarGenericData(cmbAgeLlegada, false);
			for(ItinerarioAgenciaLlegada itiAgeLlegada : arrayItiAgeLlegada){
				Comboitem item = new Comboitem(itiAgeLlegada.getAgencia().getDenominacion());
                item.setValue(itiAgeLlegada);
                cmbAgeLlegada.appendChild(item);
				if(arrayItiAgeLlegada.size()==1){
					cmbAgeLlegada.setSelectedItem(item);
					lblHoraLlegada.setValue(itiAgeLlegada.getHoraLlegada());
				}else if(detItinerario.getAgenciaLlegada().getId().intValue()==itiAgeLlegada.getAgencia().getId().intValue()){
					cmbAgeLlegada.setSelectedItem(item);
					lblHoraLlegada.setValue(itiAgeLlegada.getHoraLlegada());
				}
			}			
		}catch(Exception ex){
			ex.printStackTrace();
			DlgMessage.error(this.getClass().getName()+" "+ex.getMessage());
		}
	}	
	
	/**
	 * Muestra la hora de embarque segun el punto de embarque seleccionado
	 */
	public void onSelectPtoEmbarque(){
		if(cmbPtoEmbarque.getSelectedItem().getValue() instanceof ItinerarioAgenciaPartida)
			lblHoraPartida.setValue(((ItinerarioAgenciaPartida)cmbPtoEmbarque.getSelectedItem().getValue()).getHoraPartida());
		else
			lblHoraPartida.setValue("");
	}
	
	/**
	 * Muestrala hora de llegada segun el punto de desembarque seleccionado.
	 */
	public void onSelectPtoDesembarque(){
		if(cmbAgeLlegada.getSelectedItem().getValue() instanceof ItinerarioAgenciaLlegada)
			lblHoraLlegada.setValue(((ItinerarioAgenciaLlegada)cmbAgeLlegada.getSelectedItem().getValue()).getHoraLlegada());
		else
			lblHoraLlegada.setValue("");
	}
	
	public String generaCerosIzquierda(String numero){
		String num="";
		if(Util.isNumeric(numero)){
			String ceros="00000000";
			int index=ceros.length()-numero.length();
			num=ceros.substring(0,index)+""+numero;
		}
		return num;
	}
	
	
//	/**
//	 * Genera el siguiente número de Boleto.
//	 * @param numeroBoleto
//	 * @return
//	 */
//	private Long nuevoNumeroBoleto(Integer numeroBoleto){
//		return (long) (numeroBoleto+1);
//	}
	
	/**
	 * Limpia controles
	 */
	private void limpiaControles(Boolean limpiaEspeciesValorads){
		txtItinerario.setText("");
		lblServicio.setValue("");
		lblOrigen.setValue("");
		lblDestino.setValue("");
		lblFechaPartida.setValue("");
		lblFechaLlegada.setValue("");
		lblHoraPartida.setValue("");
		lblHoraLlegada.setValue("");
		UtilData.cargarGenericData(cmbPtoEmbarque, false);
		UtilData.cargarGenericData(cmbAgeLlegada, false);
//		if(limpiaEspeciesValorads){
		rdBoleta.setChecked(false);
		ibxSerie.setText("");
		txtInicio.setText("");
		rdFacturaEspecial.setChecked(true);
		txtSerieFactura.setText("");
		txtNumeroFactura.setText("");
		
		ibxSerie.setReadonly(true);
		txtInicio.setReadonly(true);
		txtSerieFactura.setReadonly(false);
		txtNumeroFactura.setReadonly(false);
		txtSerieFactura.setFocus(true);
//		}
		txtIdCliente.setText("");
		txtRuc.setText("");
		lblRazonSocial.setValue("");
		lblDescuento.setValue("");
		lblTarifa.setValue("");
		Util.disabledBtnExportar(false, btnCargarArchivo, true);
	}
	
	/**
	 * Realiza la busqueda del Cliente
	 * @param Ruc	: Número de Ruc del Cliente a buscar
	 * @throws Exception
	 */
	public void buscarCliente(String Ruc) throws Exception{
//		limpiaControles(false);
		if (!(Ruc.trim().isEmpty())){
			Cliente cliente=ServiceLocator.getClienteManager().buscarCliente_ServicioEspecial(Ruc);
			if(cliente!=null){
				txtIdCliente.setValue(cliente.getId().toString());
				lblRazonSocial.setValue(cliente.getRazonSocial());
				lblDescuento.setValue(Util.toNumberFormat(cliente.getDescuento(), 2));
			}else{
				DlgMessage.information(Messages.getString("wndServicioEspecial.information.clienteNoEncontrado"));
				txtRuc.setFocus(true);
				txtRuc.select();
			}
		}
	}
	
	
	
}
