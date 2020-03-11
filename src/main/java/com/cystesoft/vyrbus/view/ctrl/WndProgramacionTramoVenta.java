/**
 * Proyecto		: SISVYR
 * Sistema		: Sistema de Ventas y Reservas
 * Descripción	: 
 * Autor		: José Abanto
 * Fecha		: 26/01/2015
 * Hora			: 12:36:17
 */
package com.cystesoft.vyrbus.view.ctrl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.TreeMap;

import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zul.Button;
import org.zkoss.zul.Checkbox;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.Intbox;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Timebox;
import org.zkoss.zul.Toolbarbutton;

import com.cystesoft.vyrbus.model.bean.DetalleItinerario;
import com.cystesoft.vyrbus.model.bean.Itinerario;
import com.cystesoft.vyrbus.model.bean.Localidad;
import com.cystesoft.vyrbus.model.bean.VentaTramo;
import com.cystesoft.vyrbus.service.locator.ServiceLocator;
import com.cystesoft.vyrbus.service.util.Constantes;
import com.cystesoft.vyrbus.service.util.Messages;
import com.cystesoft.vyrbus.service.util.Util;
import com.cystesoft.vyrbus.service.util.UtilData;
import com.cystesoft.vyrbus.view.ui.DlgMessage;
import com.cystesoft.vyrbus.view.ui.WndBase;

/**
 * @author jabanto
 *
 */
public class WndProgramacionTramoVenta extends WndBase{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Combobox cmbOrigen;
	private Combobox cmbDestino;
	private Datebox dtbxFechaPartida;
	private Listbox lsbxitinerarios;
	private Listbox lsbxDetalleItinerario;
	private Timebox tmbxHoraPartida;
	private Datebox dtbxDesde;
	private Datebox dtbxHasta;
	private Intbox itbxTiempo;
	private Button btnGuardar;
	private Checkbox chbxVentaAdelantada;
	
	/*Lista de tramos programados*/
	private Datebox dtbxFechaInicial;
	private Datebox dtbxFechaFinal;
	private Combobox cmbOrigenList;
	private Combobox cmbDestinoList;
	private Listbox lsbxListaTramosProgramados;
	
	
	
	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.view.ui.WndBase#initComponents()
	 */
	@Override
	public void initComponents() {
		cmbOrigen=(Combobox)this.getFellow("cmbOrigen");
		cmbDestino=(Combobox)this.getFellow("cmbDestino");
		dtbxFechaPartida=(Datebox)this.getFellow("dtbxFechaPartida");
		lsbxitinerarios=(Listbox)this.getFellow("lsbxitinerarios");
		lsbxDetalleItinerario=(Listbox)this.getFellow("lsbxDetalleItinerario");
		tmbxHoraPartida=(Timebox)this.getFellow("tmbxHoraPartida");
		dtbxDesde=(Datebox)this.getFellow("dtbxDesde");
		dtbxHasta=(Datebox)this.getFellow("dtbxHasta");
		itbxTiempo=(Intbox)this.getFellow("itbxTiempo");
		btnGuardar=(Button)this.getFellow("btnGuardar");
		chbxVentaAdelantada=(Checkbox)this.getFellow("chbxVentaAdelantada");
		
		/*Lista de tramos programados*/
		dtbxFechaInicial=(Datebox)this.getFellow("dtbxFechaInicial");
		dtbxFechaFinal=(Datebox)this.getFellow("dtbxFechaFinal");
		cmbOrigenList=(Combobox)this.getFellow("cmbOrigenList");
		cmbDestinoList=(Combobox)this.getFellow("cmbDestinoList");
		lsbxListaTramosProgramados=(Listbox)this.getFellow("lsbxListaTramosProgramados");
	}
	
	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.view.ui.WndBase#onCreate()
	 */
	@Override
	public void onCreate() throws Exception {
		/*Lista tramos programados*/
		UtilData.cargarDataCombo(cmbOrigenList, Localidad.class, true);
		UtilData.cargarDataCombo(cmbDestinoList, Localidad.class, true);
		cmbOrigenList.setSelectedIndex(0);
		cmbDestinoList.setSelectedIndex(0);
		dtbxFechaInicial.setValue(new Date());
		dtbxFechaFinal.setValue(new Date());
		
		
		UtilData.cargarDataCombo(cmbOrigen, Localidad.class, false);
		UtilData.cargarDataCombo(cmbDestino, Localidad.class, false);		
		cmbOrigen.setSelectedIndex(0);
		cmbDestino.setSelectedIndex(0);
		dtbxFechaPartida.setValue(new Date());
		dtbxDesde.setValue(new Date());
		dtbxHasta.setValue(new Date());
		dtbxDesde.setDisabled(true);
		dtbxHasta.setDisabled(true);
		btnGuardar.setDisabled(true);
	}

	/**
	 * Busca los itinerarios, según los parametros seleccionados
	 * @throws Exception
	 */
	public void buscarItinerarios()throws Exception{
		try {
			Util.limpiarListbox(lsbxitinerarios);
			Util.limpiarListbox(lsbxDetalleItinerario);
			dtbxDesde.setDisabled(true);
			dtbxHasta.setDisabled(true);
			btnGuardar.setDisabled(true);
			
			if(cmbOrigen.getSelectedItem().getValue() instanceof Localidad && cmbDestino.getSelectedItem().getValue() instanceof Localidad){			
				String fechaPartida=Constantes.FORMAT_DATE.format(dtbxFechaPartida.getValue());
				String horaPartida=(tmbxHoraPartida.getValue()!=null?Constantes.FORMAT_TIME.format(tmbxHoraPartida.getValue()):null);
				Integer localidadIdOrigen=((Localidad)cmbOrigen.getSelectedItem().getValue()).getId();
				Integer localidadIdDestino=((Localidad)cmbDestino.getSelectedItem().getValue()).getId();
				List<Itinerario>listItinerario=ServiceLocator.getItinerarioManager().buscarItinerarioByRutaMayor(fechaPartida,horaPartida,localidadIdOrigen,localidadIdDestino);
				
				int x=1;
				for(Itinerario itinerario: listItinerario){
					Listitem item=new Listitem();
					Listcell cell=new Listcell(String.valueOf(x++));
					cell.setStyle("font-size:11px !important");
					item.appendChild(cell);
					cell=new Listcell(itinerario.getRuta().getOrigen());
					item.appendChild(cell);
					cell=new Listcell(itinerario.getRuta().getDestino());
					item.appendChild(cell);
					cell=new Listcell(itinerario.getServicio().getDenominacion());
					item.appendChild(cell);
					cell=new Listcell(Constantes.FORMAT_DATE.format(itinerario.getFechaPartida()));
					cell.setStyle("font-size:11px !important");
					item.appendChild(cell);
					cell=new Listcell(itinerario.getHoraPartida());
					cell.setStyle("font-size:11px !important");
					item.appendChild(cell);
										
					item.setValue(itinerario);
					lsbxitinerarios.appendChild(item);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			DlgMessage.error(e.getMessage());
		}
		
	}
	
	/**
	 * Busca el detalle del itinerario seleccionado.
	 */
	public void buscarDetalleItinerario(){
		try {
			Util.limpiarListbox(lsbxDetalleItinerario);
			Itinerario itinerario=(Itinerario)lsbxitinerarios.getSelectedItem().getValue();
			
			TreeMap<String, Object>criteriosBusqueda=new TreeMap<String, Object>();
			criteriosBusqueda.put("itinerario",itinerario);
			criteriosBusqueda.put("estadoRegistro",Constantes.VALUE_ACTIVO);
			List<String>criteriosOrdenar=new ArrayList<String>();
			criteriosOrdenar.add("fechaPartida");
			criteriosOrdenar.add("horaPartida");
			List<DetalleItinerario>lstDetalleItinerario=ServiceLocator.getDetalleItinerarioManager().buscarPorX(criteriosBusqueda, criteriosOrdenar);
			
			int x=1;
			for(DetalleItinerario detalleItinerario:lstDetalleItinerario){
				if(detalleItinerario.getRuta().getId().intValue()!=itinerario.getRuta().getId().intValue()){
					Listitem item=new Listitem();
					
					Listcell cell=new Listcell();
					item.appendChild(cell);
					cell=new Listcell(String.valueOf(x++));
					cell.setStyle("font-size:11px !important");
					item.appendChild(cell);
					cell=new Listcell(detalleItinerario.getRuta().toString());
					item.appendChild(cell);
					cell=new Listcell(Constantes.FORMAT_DATE.format(detalleItinerario.getFechaPartida()));
					cell.setStyle("font-size:11px !important");
					item.appendChild(cell);
					cell=new Listcell(detalleItinerario.getHoraPartida());
					cell.setStyle("font-size:11px !important");
					item.appendChild(cell);
					cell=new Listcell(Util.toNumberFormat(detalleItinerario.getTarifa(),2));
					cell.setStyle("font-size:11px !important");
					item.appendChild(cell);
					
					item.setValue(detalleItinerario);
					lsbxDetalleItinerario.appendChild(item);
				}
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			DlgMessage.error(e.getMessage());
		}
		
	}

	/**
	 * Evento cuando selecciona el detalle itinerarios
	 */
	public void onSelectDetalleItinerario(){
		dtbxDesde.setDisabled(true);
		dtbxHasta.setDisabled(true);
		btnGuardar.setDisabled(true);
		if(lsbxDetalleItinerario.getSelectedCount()>0){
			DetalleItinerario detalleItinerario=(DetalleItinerario)lsbxDetalleItinerario.getSelectedItem().getValue();
			dtbxDesde.setDisabled(false);
			dtbxHasta.setDisabled(false);
			btnGuardar.setDisabled(false);
				
			String fecha = Util.DatetoString(detalleItinerario.getFechaPartida(), "yyyyMMdd");
			dtbxDesde.setConstraint("after "+fecha);
			dtbxHasta.setConstraint("after "+fecha);
				
			dtbxDesde.setValue(detalleItinerario.getFechaPartida());
			dtbxHasta.setValue(detalleItinerario.getFechaPartida());
		}
	}
	
	/**
	 * Guarda las rutas(tramos) seleccionadas con la activacion de la venta
	 */
	public void Guardar(){
		try {
			/*  */
			if(lsbxitinerarios.getSelectedItems().size()==0){
				DlgMessage.information(Messages.getString("WndProgramacionVentaTramos.information.noSelectItinerario"));
				return;
			}else if (lsbxDetalleItinerario.getSelectedItems().size()==0){
				DlgMessage.information(Messages.getString("Debe de seleccionar el Tramo, el cual desea Programar."));
				return;
			}
			
			Messagebox.show(Messages.getString("Generales.query.guardar"),DlgMessage.NOMBREAPLICACION, DlgMessage.BTN_YESNO, Messagebox.QUESTION,DlgMessage.BTN_DEFAULT_NO, new EventListener<Event>() {
				@Override
				public void onEvent(Event e) throws Exception {
					if(e.getName().equals("onYes")){
						Itinerario itinerario=(Itinerario)lsbxitinerarios.getSelectedItem().getValue();
						
						String fechaInicial=Constantes.FORMAT_DATE.format(dtbxDesde.getValue());
						String fechaFinal=Constantes.FORMAT_DATE.format(dtbxHasta.getValue());
						
						for(Listitem item:lsbxDetalleItinerario.getSelectedItems()){
							DetalleItinerario detalleItinerario=(DetalleItinerario)item.getValue();
							
							//-->Busca los itinerarios y rutas a guardar
							List<Itinerario>listItinerarios=ServiceLocator.getItinerarioManager().buscarItinerariosByVentaTramo(fechaInicial, fechaFinal, itinerario.getRuta().getId()
									,detalleItinerario.getRuta().getId(), detalleItinerario.getHoraPartida(), itinerario.getHoraPartida());
							
							Integer isNotVentaAdelantada=Constantes.TRUE_VALUE;
							if(chbxVentaAdelantada.isChecked())
								isNotVentaAdelantada=Constantes.FALSE_VALUE;
							
							//Valida los itinerarios y rutas a insertar
							List<VentaTramo>listaVentaTramoDuplicado=new ArrayList<VentaTramo>();
							for(Itinerario oItinerario:listItinerarios){
								//Guarda la venta por tramo
								VentaTramo ventaTramo=ServiceLocator.getVentaTramoManager().buscarPorItinerarioRuta(oItinerario, oItinerario.getRuta());
								if(ventaTramo!=null)
									listaVentaTramoDuplicado.add(ventaTramo);
							}
							
							if(listaVentaTramoDuplicado.size()>0){
								DlgMessage.information(Messages.getString("WndProgramacionVentaTramos.information.tramosDuplicate"));
								return;
							}else{
								for(Itinerario oItinerario:listItinerarios){
									//Guarda la venta por tramo
									VentaTramo ventaTramo=new VentaTramo();
									ventaTramo.setItinerario(oItinerario);
									ventaTramo.setRuta(oItinerario.getRuta());
									ventaTramo.setTiempo(itbxTiempo.getValue());
									ventaTramo.setDespuesHoraSalida(isNotVentaAdelantada);
									ventaTramo.setFechaInicio(oItinerario.getFechaPartida());
									ventaTramo.setFechaFin(oItinerario.getFechaPartida());
									ventaTramo.setEstadoRegistro(Constantes.VALUE_ACTIVO);
									UtilData.auditarRegistro(ventaTramo, getUsuario(), Executions.getCurrent());
									ServiceLocator.getVentaTramoManager().guardar(ventaTramo);
								}
							}
						}
						DlgMessage.information(Messages.getString("Generales.information.exitoGuardar"));
						dtbxDesde.setDisabled(true);
						dtbxHasta.setDisabled(true);
						btnGuardar.setDisabled(true);
					}
				}
			});
		} catch (Exception e) {
			e.printStackTrace();
			DlgMessage.error(e.getMessage());
		}
	}
	
	/**
	 * Realiza la busqueda de los tramos programados
	 */
	public void buscarTramosProgramados(){
		try {
			
			Util.limpiarListbox(lsbxListaTramosProgramados);
			
			String fechaInicial=Constantes.FORMAT_DATE.format(dtbxFechaInicial.getValue());
			String fechaFinal=Constantes.FORMAT_DATE.format(dtbxFechaFinal.getValue());
			Integer origenId=null;
			Integer destinoId=null;
			if(cmbOrigenList.getSelectedItem().getValue() instanceof Localidad)
				origenId=((Localidad)cmbOrigenList.getSelectedItem().getValue()).getId();
			if(cmbDestinoList.getSelectedItem().getValue() instanceof Localidad)
				destinoId=((Localidad)cmbDestinoList.getSelectedItem().getValue()).getId();
			List<VentaTramo>result=ServiceLocator.getVentaTramoManager().buscarPor(fechaInicial, fechaFinal, origenId, destinoId);
						
			int x=1;
			for(VentaTramo ventaTramo:result){
				Listitem item=new Listitem();
				Listcell cell=new Listcell(String.valueOf(x++));
				cell.setStyle("font-size:11px !important");
				item.appendChild(cell);
				cell=new Listcell(ventaTramo.getItinerario().getId().toString());
				cell.setStyle("font-size:11px !important");
				item.appendChild(cell);
				cell=new Listcell(ventaTramo.getRuta().getOrigen());
				item.appendChild(cell);
				cell=new Listcell(ventaTramo.getRuta().getDestino());
				item.appendChild(cell);
				cell=new Listcell(ventaTramo.getItinerario().getServicio().getDenominacion());
				item.appendChild(cell);
				cell=new Listcell(Constantes.FORMAT_DATE.format(ventaTramo.getItinerario().getFechaPartida()));
				cell.setStyle("font-size:11px !important");
				item.appendChild(cell);
				cell=new Listcell(ventaTramo.getItinerario().getHoraPartida());
				cell.setStyle("font-size:11px !important");
				item.appendChild(cell);
//				cell=new Listcell("VENTA ADELANTADA");
//				item.appendChild(cell);
				final Toolbarbutton btnEliminar=new Toolbarbutton();
				btnEliminar.setAttribute("ventaTramo", ventaTramo);
				btnEliminar.setStyle("text-transform: none; color:red; font-size:11px !important");
				if(ventaTramo.getItinerario().getFechaPartida().getTime()< Constantes.FORMAT_DATE.parse(Constantes.FORMAT_DATE.format(new Date())).getTime())
					btnEliminar.setDisabled(true);
				btnEliminar.setLabel("Elimnar");
				
				cell=new Listcell();
				cell.appendChild(btnEliminar);
				item.appendChild(cell);
				
				
				item.setValue(ventaTramo);
				lsbxListaTramosProgramados.appendChild(item);
				
				
				btnEliminar.addEventListener(Events.ON_CLICK, new EventListener<Event>() {
					@Override
					public void onEvent(Event event) throws Exception {
						// TODO Auto-generated method stub
						eliminar((VentaTramo)btnEliminar.getAttribute("ventaTramo"));
					}
				});
			}
		} catch (Exception e) {
			e.printStackTrace();
			DlgMessage.error(e.getMessage());
		}
	}
	
	/**
	 * Elimina el tramo programado
	 * @param ventaTramo
	 * @throws Exception
	 */
	private void eliminar(final VentaTramo ventaTramo)throws Exception{
		try {
			Messagebox.show(Messages.getString("Generales.question.delete"),DlgMessage.NOMBREAPLICACION, DlgMessage.BTN_YESNO, Messagebox.QUESTION,DlgMessage.BTN_DEFAULT_NO, new EventListener<Event>() {
				@Override
				public void onEvent(Event e) throws Exception {
					if(e.getName().equals("onYes")){
						ServiceLocator.getVentaTramoManager().anular(ventaTramo.getId());
						buscarTramosProgramados();
					}
				}
			});
		} catch (Exception e) {
			e.printStackTrace();
			DlgMessage.error(e.getMessage());
		}
	}
}
