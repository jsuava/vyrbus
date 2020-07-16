/**
 * Proyecto		: SISVYR
 * Sistema		: Sistema de Ventas y Reservas
 * Descripción	: 
 * Autor		: Marco Antonio Oscco
 * Fecha		: 28/05/2013
 */
package com.cystesoft.vyrbus.view.ctrl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;

import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zul.Checkbox;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.Doublebox;
import org.zkoss.zul.Intbox;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listheader;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Radio;
import org.zkoss.zul.Timebox;

import com.cystesoft.vyrbus.model.bean.CanalVenta;
import com.cystesoft.vyrbus.model.bean.DetalleItinerario;
import com.cystesoft.vyrbus.model.bean.Localidad;
import com.cystesoft.vyrbus.model.bean.Ruta;
import com.cystesoft.vyrbus.model.bean.Servicio;
import com.cystesoft.vyrbus.model.bean.TarifaFechaAbierta;
import com.cystesoft.vyrbus.model.bean.TarifaRegular;
import com.cystesoft.vyrbus.model.bean.TipoItinerario;
import com.cystesoft.vyrbus.service.exceptions.TarifarioException;
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
public class WndTarifario extends WndBase implements Serializable {

	private static final long serialVersionUID = 1L;
	private Radio rdAmbos;
	private Radio rdSinTarifa;
	private Radio rdConTarifa;
	private Combobox cmbCanalBus;
	private Combobox cmbServicioBus;
	private Combobox cmbOrigenBus;
	private Combobox cmbDestinoBus;
	private Datebox dtbxFecInicioBus;
	private Datebox dtbxFecFinBus;
	private Timebox tmbxHoraPartidaBus;
	private Combobox cmbTipoItinerario;
	private Listbox lsbxRutas;	
//	
	private Combobox cmbCanal;
	private Combobox cmbServicio;	
	private Combobox cmbOrigen;
	private Combobox cmbDestino;
	private Datebox dtbxFecInicio;
	private Datebox dtbxFecFinal;
	private Combobox cmbPiso;
	private Combobox cmbZona;
	private Timebox tmbxHoraPartida;

	private Doublebox dlbxTarifa;
//	private Listheader lstheaFecha;
//	private Listheader lstheaItinerario;
//	private Checkbox ckbxDetalle;
//	private Intbox ibItinerario;

	private Doublebox dlbxTarifaFa;
//	private Doublebox dlbxTarifaLista;
	
	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.view.ui.WndBase#initComponents()
	 */
	@Override
	public void initComponents() {
		// TODO Auto-generated method stub
		
		cmbCanalBus=(Combobox)this.getFellow("cmbCanalBus");
		cmbServicioBus=(Combobox)this.getFellow("cmbServicioBus");
		cmbOrigenBus=(Combobox)this.getFellow("cmbOrigenBus");
		cmbDestinoBus=(Combobox)this.getFellow("cmbDestinoBus");
		dtbxFecInicioBus=(Datebox)this.getFellow("dtbxFecInicioBus");
		dtbxFecFinBus=(Datebox)this.getFellow("dtbxFecFinBus");		
		tmbxHoraPartidaBus=(Timebox)this.getFellow("tmbxHoraPartidaBus");
		cmbCanal=(Combobox)this.getFellow("cmbCanal");
		cmbPiso=(Combobox)this.getFellow("cmbPiso");
		cmbZona=(Combobox)this.getFellow("cmbZona");
		
		dtbxFecInicio=(Datebox)this.getFellow("dtbxFecInicio");
		dtbxFecFinal=(Datebox)this.getFellow("dtbxFecFinal");
		lsbxRutas=(Listbox)this.getFellow("lsbxRutas");
//		rbActualizaTarifa=(Radio)this.getFellow("rbActualizaTarifa");
//		rbIncrementoTarifa=(Radio)this.getFellow("rbIncrementoTarifa");
		rdAmbos = (Radio)this.getFellow("rdAmbos"); 
		rdSinTarifa = (Radio)this.getFellow("rdSinTarifa"); 
		rdConTarifa = (Radio)this.getFellow("rdConTarifa"); 
		
		
		cmbOrigen=(Combobox)this.getFellow("cmbOrigen");
		cmbDestino=(Combobox)this.getFellow("cmbDestino");
		tmbxHoraPartida=(Timebox)this.getFellow("tmbxHoraPartida");
		cmbServicio=(Combobox)this.getFellow("cmbServicio");
		dlbxTarifa=(Doublebox)this.getFellow("dlbxTarifa");
//		lstheaFecha=(Listheader)this.getFellow("lstheaFecha");
//		lstheaItinerario=(Listheader)this.getFellow("lstheaItinerario");
//		ckbxDetalle=(Checkbox)this.getFellow("ckbxDetalle");
//		ibItinerario=(Intbox)this.getFellow("ibItinerario");
		cmbTipoItinerario=(Combobox)this.getFellow("cmbTipoItinerario");
		dlbxTarifaFa=(Doublebox)this.getFellow("dlbxTarifaFa");
//		dlbxTarifaLista=(Doublebox)this.getFellow("dlbxTarifaLista");
	}
	
	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.view.ui.WndBase#onCreate()
	 */
	@Override
	public void onCreate() throws Exception {
		// TODO Auto-generated method stub
//		ckbxDetalle.setDisabled(true);
		String fecha = Util.DatetoString(new Date(), "yyyyMMdd");
		
		dtbxFecInicioBus.setConstraint("after "+fecha);
		dtbxFecFinBus.setConstraint("after "+fecha);
		dtbxFecInicioBus.setValue(new Date());
		dtbxFecFinBus.setValue(new Date());		
		
		dtbxFecInicio.setConstraint("after "+fecha);
		dtbxFecFinal.setConstraint("after "+fecha);
		dtbxFecInicio.setValue(new Date());
		dtbxFecFinal.setValue(new Date());
		
		UtilData.cargarDataCombo(cmbCanalBus, CanalVenta.class,true);
		UtilData.cargarDataCombo(cmbServicioBus, Servicio.class,true);
		UtilData.cargarDataCombo(cmbOrigenBus, Localidad.class,true);
		UtilData.cargarDataCombo(cmbDestinoBus, Localidad.class,true);
		
		UtilData.cargarDataCombo(cmbTipoItinerario, TipoItinerario.class,true);
		dlbxTarifa.setLocale(Locale.US);
		dlbxTarifaFa.setLocale(Locale.US);
//		dlbxTarifaLista.setLocale(Locale.US);
		
		Util.seleccionarValorItemCombo(TipoItinerario.class, cmbTipoItinerario, Constantes.ID_TIPITI_REGULAR);
		
		/*Validacion de roles para la modificacion de la tarifa real del servicio y fecha abierta*/
		dlbxTarifa.setDisabled(getRol().getId().intValue()==Constantes.ID_ROL_MARKETING);
		dlbxTarifaFa.setDisabled(getRol().getId().intValue()==Constantes.ID_ROL_MARKETING);
//		rbIncrementoTarifa.setDisabled(getRol().getId().intValue()==Constantes.ID_ROL_MARKETING);
	}
	
	/**
	 *  Realiza la busqueda de las rutas para la actualización las tarifas
	 * @throws Exception
	 */
	public void buscar() throws Exception{
		Util.limpiarListbox(lsbxRutas);
		Integer idCanalVenta=null;
		Integer idOrigen=null;
		Integer idDestino=null;
		Integer idServicio=null;
		Integer idTipoItinerario=null;
		String strFechaInicioBus, strFechaFinBus;
		String horaPartida=null;
//		Long idItinerario=null;
		Integer con_o_sin_tarifa=null;
//		Boolean mostrarDetalle=false;
		
		
		strFechaInicioBus = Constantes.FORMAT_DATE.format(dtbxFecInicioBus.getValue());
		strFechaFinBus = Constantes.FORMAT_DATE.format(dtbxFecFinBus.getValue());
		
		
		if(cmbCanalBus.getSelectedItem().getValue() instanceof CanalVenta)
			idCanalVenta=((CanalVenta)cmbCanalBus.getSelectedItem().getValue()).getId();
		if(cmbServicioBus.getSelectedItem().getValue() instanceof Servicio)
			idServicio=((Servicio)cmbServicioBus.getSelectedItem().getValue()).getId();
		if(cmbOrigenBus.getSelectedItem().getValue() instanceof Localidad)
			idOrigen=((Localidad)cmbOrigenBus.getSelectedItem().getValue()).getId();
		if(cmbDestinoBus.getSelectedItem().getValue() instanceof Localidad)
			idDestino=((Localidad)cmbDestinoBus.getSelectedItem().getValue()).getId();
		if(tmbxHoraPartidaBus.getText()!="")
			horaPartida=tmbxHoraPartidaBus.getText();
		if(cmbTipoItinerario.getSelectedItem().getValue() instanceof TipoItinerario)
			idTipoItinerario=((TipoItinerario)cmbTipoItinerario.getSelectedItem().getValue()).getId();
		
		if(rdAmbos.isChecked())
			con_o_sin_tarifa=2;
		else if(rdSinTarifa.isChecked())
			con_o_sin_tarifa=0;
		else if(rdConTarifa.isChecked())
			con_o_sin_tarifa=1;
			
		

//		if(ckbxDetalle.isChecked())
//			mostrarDetalle=true;
//		if(ibItinerario.getValue()!=null)
//			idItinerario=ibItinerario.getValue().longValue();

		List<TarifaRegular>lstTarifas = ServiceLocator.getTarifaRegularManager().listarTarifasPorServicios(
														idCanalVenta, idServicio, idOrigen, idDestino, 
														idTipoItinerario, strFechaInicioBus, strFechaFinBus, horaPartida, con_o_sin_tarifa);
		
		/*
		 * 12/07/2020
		 * MAOE
		 * En esta parte recuperar las tarifas regulares
		 * 
		 */
				
		/* Recupera las tarifas a fecha abierta */
//		List<TarifaFechaAbierta>lstTarifaFA=ServiceLocator.getTarifaFechaAbierta().buscarTarifas(idOrigen, idDestino, idServicio);
		
		Listitem item=null;
		Listcell cell=null;
//		lstheaFecha.setVisible(false);
		for(TarifaRegular tarifaRegular: lstTarifas){
			Ruta ruta = tarifaRegular.getTarifa().getRuta();
//			Servicio servicio = tarifaRegular.getTarifa().getServicio(); 
			
			item=new Listitem();
			cell=new Listcell(tarifaRegular.getItinerario().getId().toString());
			
//			lstheaItinerario.setLabel("");
//			lstheaItinerario.setWidth("30px");
//			if(ckbxDetalle.isChecked()){
//				lstheaItinerario.setLabel("ITINERARIO");
//				lstheaItinerario.setWidth("85px");
//				cell=new Listcell(detaItinerario.getItinerario().getId().toString());
//				
//			}
			cell.setStyle("font-size:11px !important");
			item.appendChild(cell);
			//CANAL DE VENTA SI ES NULL NO EXISTE TARIFA ASOCIADA
			String strCanal = tarifaRegular.getTarifa().getCanalVenta().getDenominacion(); 
			if(strCanal!=null && strCanal.length()>0){
				cell=new Listcell(tarifaRegular.getTarifa().getCanalVenta().getDenominacion());
			}
			else{
				cell=new Listcell("SIN TARIFA");
				cell.setStyle("font-size:11px !important; text-align: left; font-weight: bold;color:red");
			}
			item.appendChild(cell);
			//SERVICIO
			cell=new Listcell(tarifaRegular.getTarifa().getServicio().getDenominacion());
			item.appendChild(cell);
			//RUTA
			cell=new Listcell(ruta.getLocalidadOrigen().getDenominacion()+" - "+ruta.getLocalidadDestino().getDenominacion());
			item.appendChild(cell);
			
//			if(ckbxDetalle.isChecked()){
			//FECHA DEL SERVICIO
				GregorianCalendar gregCalendad= new GregorianCalendar();
				gregCalendad.setTime(tarifaRegular.getFechaTarifa());
				cell=new Listcell(gregCalendad.getDisplayName(Calendar.DAY_OF_WEEK, 2, Locale.getDefault())+" "+gregCalendad.get(Calendar.DAY_OF_MONTH)+
								 " de "+gregCalendad.getDisplayName(Calendar.MONTH, 2, Locale.getDefault())+" del "+gregCalendad.get(Calendar.YEAR));
//				lstheaFecha.setVisible(true);
//			}
			item.appendChild(cell);
			cell=new Listcell(tarifaRegular.getHoraPartida());
			item.appendChild(cell);
			Integer pisoBus = (tarifaRegular.getTarifa().getPisoBus()!=null ? tarifaRegular.getTarifa().getPisoBus() : null);
			String piso;
			if(pisoBus!=null){
				pisoBus = tarifaRegular.getTarifa().getPisoBus()+1;
				piso = Integer.toString(pisoBus)+"P";
			}
			else
				piso="";
			cell=new Listcell(piso);
			cell.setStyle("font-size:11px !important; text-align: right; font-weight: bold;color:red");
			cell.setTooltiptext("Piso del Bus.");			
			item.appendChild(cell);
			//ZONA DEL BUS EN CASO TENGA MAS DE UNA
			cell=new Listcell(tarifaRegular.getTarifa().getZonaBus()!=null ? tarifaRegular.getTarifa().getZonaBus().toString() : "");
			item.appendChild(cell);
			//TARIFA DEL BUS
			cell=new Listcell(Util.toNumberFormat(tarifaRegular.getMonto(), 2));
			cell.setStyle("font-size:11px !important; text-align: right; font-weight: bold;color:blue");
			cell.setTooltiptext("Tarifa para la Venta.");			
			item.appendChild(cell);
			
			/* Recupera la tarifa a fecha abierta */
//			Double montoFA=.00;
//			for(TarifaFechaAbierta tarifaFechaAbierta:lstTarifaFA){
//				if(tarifaFechaAbierta.getRuta().getId().intValue()==detaItinerario.getRuta().getId().intValue() && 
//						tarifaFechaAbierta.getServicio().getId().intValue()==detaItinerario.getItinerario().getServicio().getId().intValue()){
//					montoFA=tarifaFechaAbierta.getMonto();
//					detaItinerario.setTarifaFechaAbierta(tarifaFechaAbierta);
//					break;
//				}
//			}
//			cell=new Listcell(Util.toNumberFormat(montoFA,2));
//			cell.setStyle("font-size:11px !important;color:blue; text-align: right; font-weight:bold");
//			cell.setTooltiptext("Tarifa para Fecha Abierta.");
//			item.appendChild(cell);
//			cell=new Listcell(Util.toNumberFormat(detaItinerario.getTarifaLista(),2));
//				cell.setStyle("font-size:11px !important; text-align: right; font-weight: bold;text-decoration: line-through;");
//			cell.setTooltiptext("Tarifa Lista y/o referencial, para la venta online. ("+Util.toNumberFormat(detaItinerario.getTarifaLista(),2)+")");
//			item.appendChild(cell);
			
			item.setValue(tarifaRegular);
			lsbxRutas.appendChild(item);
		}
	}
	
	public  void guardar() throws Exception{
//		try{
//			if (!(rbActualizaTarifa.isChecked()) && !(rbIncrementoTarifa.isChecked()))
//				throw new TarifarioException(TarifarioException.NO_SELETC_TIPO_MODIFICACION_TARIFA);
//			else if(lsbxRutas.getSelectedItems().size()<=0)
//				throw new TarifarioException(TarifarioException.NO_SELECT_RUTAS);
//			else if(dlbxTarifa.getText().isEmpty() && dlbxTarifaFa.getText().isEmpty() && dlbxTarifaLista.getText().isEmpty())
//				throw new TarifarioException(TarifarioException.NO_MONTO);
//					
//			Messagebox.show(Messages.getString(rbActualizaTarifa.isChecked()?"wndTarifa.question.confirmarUpdateTarifa": "wndTarifa.question.confirmarUpdateIncrementeTarifa"), DlgMessage.NOMBREAPLICACION, DlgMessage.BTN_YESNO, Messagebox.QUESTION,DlgMessage.BTN_DEFAULT_NO, new EventListener<Event>() {
//				@Override
//				public void onEvent(Event e) throws Exception {
//					if(e.getName().equals("onYes")){
//						Integer ndias=(int) ((dtbxFechaFinal.getValue().getTime()-dtbxFechaInicio.getValue().getTime())/Constantes.MILISEGUNDOS_X_DIA)+1;
//						String fechaInicio=Constantes.FORMAT_DATE.format(dtbxFechaInicio.getValue());
//						String fechaFin=Constantes.FORMAT_DATE.format(dtbxFechaFinal.getValue());
//						Double tarifa=dlbxTarifa.getValue()!=null?dlbxTarifa.getValue():0;
//									
//						
//						/* ACTUALIZA LAS TARIFAS DE LAS FECHA ABIERTA*/
//						/* Valida actualizacion de la tarifa fechaAbierta */
//						if (!(dlbxTarifaFa.getText().trim().isEmpty())){
//							ArrayList<TarifaFechaAbierta>arTfa=new ArrayList<TarifaFechaAbierta>();
//							/* Recorre las rutas seleccionadas*/
//							for(Listitem item: lsbxRutas.getSelectedItems()){
//								DetalleItinerario detalleItinerario=(DetalleItinerario)item.getValue();
//								
//								/* Valida si la ruta ya fue actualizada. Es para controlar cuando hay mas de un registro seleccionado que pertenese a la misma ruta y servicio*/
//								Boolean rutaUpdate=true;
//								for(TarifaFechaAbierta tarifaFechaAbierta:arTfa){
//									if(detalleItinerario.getRuta().getId().intValue()==tarifaFechaAbierta.getRuta().getId().intValue() &&
//											detalleItinerario.getItinerario().getServicio().getId().intValue()==tarifaFechaAbierta.getServicio().getId().intValue()){
//										rutaUpdate=false;
//										break;
//									}
//								}
//								
//								if(rutaUpdate){//Si la ruta aun no a sido actualizada.
//									Double nuevaTarifa=dlbxTarifaFa.getValue();
//									
//									TarifaFechaAbierta tarifaFechaAbierta=null;
//									Date fechaActual=new Date();
//									
//									/* Inactiva el regitro*/
//									if(detalleItinerario.getTarifaFechaAbierta()!=null){
//										tarifaFechaAbierta=((DetalleItinerario)item.getValue()).getTarifaFechaAbierta();
//										tarifaFechaAbierta.setEstadoRegistro(Constantes.VALUE_INACTIVO);
//										tarifaFechaAbierta.setFechaSuspencion(fechaActual);
//										UtilData.auditarRegistro(tarifaFechaAbierta, true, getUsuario(), Executions.getCurrent());
//										ServiceLocator.getTarifaFechaAbierta().actualizar(tarifaFechaAbierta);
//									}
//									
//									/* Inserta un nuevo registtro*/
//									tarifaFechaAbierta=new TarifaFechaAbierta();
//									tarifaFechaAbierta.setRuta(detalleItinerario.getRuta());
//									tarifaFechaAbierta.setServicio(detalleItinerario.getItinerario().getServicio());
//									tarifaFechaAbierta.setMonto(nuevaTarifa);
//									tarifaFechaAbierta.setFechaActivacion(fechaActual);
//									tarifaFechaAbierta.setFechaCaducidad(new Date(fechaActual.getTime()+Constantes.MILISEGUNDOS_X_DIA*365));
//									tarifaFechaAbierta.setEstadoRegistro(Constantes.VALUE_ACTIVO);
//									UtilData.auditarRegistro(tarifaFechaAbierta, getUsuario(), Executions.getCurrent());
//									ServiceLocator.getTarifaFechaAbierta().guardar(tarifaFechaAbierta);
//									
//									arTfa.add(tarifaFechaAbierta);
//								}
//							}
//						}
//						
//						
//						/* ACTUALIZA LA TARIFAS DE LOS ITINERARIOS*/
//						/* Recorre el numero de días, en funcion a la fecha inicio y la fin seleccionadas*/
//						if (!(dlbxTarifa.getText().trim().isEmpty()) || !(dlbxTarifaLista.getText().trim().isEmpty())){
//							for(int i=0;i<ndias;i++){
//								/* Recorre las rutas seleccionadas*/
//								for(Listitem item: lsbxRutas.getSelectedItems()){
//									DetalleItinerario detalleItinerario= item.getValue();
//									Ruta ruta=detalleItinerario.getRuta();
//									Servicio servicio=detalleItinerario.getItinerario().getServicio();
//									String horaPartida=detalleItinerario.getHoraPartida();
////									Double tarifaActual=detalleItinerario.getTarifa();
//									Long idDetalleItinerario=null;
//									
//									//Tarifa
//									Double nuevaTarifa=detalleItinerario.getTarifa();
//									if(!(dlbxTarifa.getText().trim().isEmpty())){
//										if(rbActualizaTarifa.isChecked())/*Cuando es una actualización de la tarifa*/
//											nuevaTarifa=tarifa;
//										else if(rbIncrementoTarifa.isChecked())/* Cuando es un incremento*/
//											nuevaTarifa+=+tarifa;
//									}
//									
//									//Tarifa Lista
//									Double tarifaLista=detalleItinerario.getTarifaLista()!=null?detalleItinerario.getTarifaLista():.00;
//									if(!(dlbxTarifaLista.getText().trim().isEmpty())){
//										tarifaLista=dlbxTarifaLista.getValue();
//									}
//									
//																    
//									/* Si la actualización sera en detalle, entonces para el Update se le pasa el parametro del idItinerario.*/
//									if(ckbxDetalle.isChecked())
//										idDetalleItinerario=detalleItinerario.getId();
//									/* Realiza la actualización*/
//									UtilData.auditarRegistro(detalleItinerario, true, getUsuario(), Executions.getCurrent());
//									String idUsuarioMod=detalleItinerario.getUsuarioModificacion();
//									String ipMod=detalleItinerario.getIpModificacion();
//									ServiceLocator.getItinerarioManager().actualizarTarifa(nuevaTarifa
//																						  ,horaPartida
//																						  ,fechaInicio
//																						  ,fechaFin
//																						  ,ruta.getId()
//																						  ,servicio.getId()
//																						  ,detalleItinerario.getTarifa()
//																						  ,idDetalleItinerario
//																						  ,idUsuarioMod
//																						  ,ipMod
//																						  ,tarifaLista);
//								}
//								
//								if(ckbxDetalle.isChecked())
//									break;
//							}
//						}
//						
//						buscar();
//						dlbxTarifa.setValue(null);
//						dlbxTarifaFa.setValue(null);
//						dlbxTarifaLista.setValue(null);
//						DlgMessage.information(Messages.getString("wndTarifa.information.confirmaUpdate"));
//					}
//				}
//			});
//			
//		}catch (TarifarioException tex){
//			if(TarifarioException.NO_SELETC_TIPO_MODIFICACION_TARIFA==tex.getTipo())
//				DlgMessage.information(Messages.getString("wndTarifa.information.NoTipoModoficacionTarifa"));
//			else if (TarifarioException.NO_MONTO==tex.getTipo())
//				DlgMessage.information(Messages.getString("wndTarifa.information.NoMonto"),dlbxTarifa);
//			else if(TarifarioException.NO_SELECT_RUTAS==tex.getTipo())
//				DlgMessage.information(Messages.getString("wndTarifa.information.NoSelectRutas"));
//		}catch (Exception ex) {
//			// TODO: handle exception
//			DlgMessage.error(this.getClass().getName()+" "+ex.getMessage());
//			ex.printStackTrace();
//		}	
	}
	
	
	public void onCkeckRbActualizaTarifa(){
//		dlbxTarifa.setReadonly(false);
//		dlbxTarifa.setFocus(true);  
	}
	
	public void onCheckRbIncrementaTarifa(){
//		 dlbxTarifa.setReadonly(false); 
//		 dlbxTarifa.setFocus(true);

	}
	
}
