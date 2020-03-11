package com.cystesoft.vyrbus.view.ctrl;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zul.Button;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.Doublebox;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Tabbox;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Toolbarbutton;

import com.cystesoft.vyrbus.model.bean.Agencia;
import com.cystesoft.vyrbus.model.bean.Gasto;
import com.cystesoft.vyrbus.model.bean.Liquidacion;
import com.cystesoft.vyrbus.model.bean.LiquidacionOficina;
import com.cystesoft.vyrbus.model.bean.TipoGasto;
import com.cystesoft.vyrbus.service.exceptions.GastosException;
import com.cystesoft.vyrbus.service.exceptions.LiquidacionNullException;
import com.cystesoft.vyrbus.service.locator.ServiceLocator;
import com.cystesoft.vyrbus.service.util.Constantes;
import com.cystesoft.vyrbus.service.util.Messages;
import com.cystesoft.vyrbus.service.util.MyTime;
import com.cystesoft.vyrbus.service.util.Util;
import com.cystesoft.vyrbus.service.util.UtilData;
import com.cystesoft.vyrbus.view.ui.DlgMessage;
import com.cystesoft.vyrbus.view.ui.WndBase;

/**
 * 
 * @author JABANTO
 *
 */
public class WndLiquidacionOficina extends WndBase {
	private static final long serialVersionUID = 1L;
	
//	private Listbox listLiquidacionOficina;
//	private Textbox textboxId;
	private Listbox listCajasPendientes;
	private Listbox listCajasLiquidadas;
	private Button btnBuscar;
	private Datebox dbFecha;
	private Combobox cmbAgencia;
	private Button btnPrevio;
	private Button btnImprimir;
	private Button btnLiquidar;
	private Button btnCerrarVentana;
	private Tabbox tbgastos;
		
	//Mnt. Gastos
	private Combobox cmbTipoGasto;
	private Textbox txtNroDocumento;
	private Doublebox dbMonto;
	private Textbox txtNombrePiloto;
	private Textbox txtConsignado;
	private Textbox txtObservacion;
	private Textbox txtBus;
	private Datebox dbFechaGasto;
	private Toolbarbutton tbtNuevo;
	private Toolbarbutton tbtModificar;
	private Toolbarbutton tbtCancelar;
	private Toolbarbutton tbtGuardar;
	private Toolbarbutton tbtEliminar;
	private Listbox listGastos;
	private Textbox txtID;
	
	private Gasto gasto = null;
	MyTime myTime = new MyTime();
	Integer action=null;
	
	/*
	 * (non-Javadoc)
	 * @see com.tepsa.sisvyr.view.ui.WndBase#initComponents()
	 */
	@Override
	public void initComponents(){
	//	textboxId = (Textbox) this.getFellow("textboxId");
		listCajasPendientes = (Listbox) this.getFellow("listCajasPendientes");
		listCajasLiquidadas = (Listbox) this.getFellow("listCajasLiquidadas");
		btnBuscar = (Button) this.getFellow("btnBuscar");
		dbFecha = (Datebox) this.getFellow("dbFecha");
		cmbAgencia = (Combobox) this.getFellow("cmbAgencia");
		btnPrevio = (Button) this.getFellow("btnPrevio");
		btnImprimir = (Button) this.getFellow("btnImprimir");
		btnLiquidar = (Button) this.getFellow("btnLiquidar");
		btnCerrarVentana = (Button) this.getFellow("btnCerrarVentana");
		tbgastos = (Tabbox) this.getFellow("tbgastos");
	
		//Mnt.Gastos
		cmbTipoGasto = (Combobox) this.getFellow("cmbTipoGasto");
		txtNroDocumento = (Textbox) this.getFellow("txtNroDocumento") ;
		dbMonto = (Doublebox) this.getFellow("dbMonto");
		txtNombrePiloto = (Textbox) this.getFellow("txtNombrePiloto");
		txtConsignado = (Textbox) this.getFellow("txtConsignado");
		txtObservacion = (Textbox) this.getFellow("txtObservacion");
		txtBus = (Textbox) this.getFellow("txtBus");
		dbFechaGasto = (Datebox) this.getFellow("dbFechaGasto");
		tbtNuevo = (Toolbarbutton) this.getFellow("tbtNuevo");
		tbtModificar = (Toolbarbutton) this.getFellow("tbtModificar");
		tbtCancelar = (Toolbarbutton) this.getFellow("tbtCancelar");
		tbtGuardar = (Toolbarbutton) this.getFellow("tbtGuardar");
		tbtEliminar = (Toolbarbutton) this.getFellow("tbtEliminar");
		listGastos=(Listbox)this.getFellow("listGastos");
		txtID=(Textbox)this.getFellow("txtID");
		
		/*BUSCAR*/
		btnBuscar.addEventListener(Events.ON_CLICK, new EventListener<Event>() {
			@Override
			public void onEvent(Event event) throws Exception {
				buscarLiquidacionPendiente();
				
				if (listCajasPendientes.getItems().size()==0){
					btnLiquidar.setDisabled(true);
					btnLiquidar.setImage("resources/mp_aceptarDisabled.png");
				}else{
					btnLiquidar.setDisabled(false);
					btnLiquidar.setImage("resources/mp_aceptarEnabled.png");
				}
				
				buscarLiquidacionOficina();
			}
		});
		
		/*CERRAR VENTANA*/
		btnCerrarVentana.addEventListener(Events.ON_CLICK, new EventListener<Event>() {
			@Override
			public void onEvent(Event event) throws Exception {
				closeTabWindow();
			}
		});
		
		/*LIQUIDAR*/
		btnLiquidar.addEventListener(Events.ON_CLICK, new EventListener<Event>() {
			@Override
			public void onEvent(Event event) throws Exception {
				if (listCajasPendientes.getSelectedItems().size() >0 ){		
					Messagebox.show(Messages.getString("WndLiquidacionOficina.Information.Liquidar"), DlgMessage.NOMBREAPLICACION, DlgMessage.BTN_YESNO, Messagebox.QUESTION, new EventListener<Event>() {
						@Override
						public void onEvent(Event e) throws Exception {
							if(e.getName().equals("onYes")){
								Object[] objLiquidacion = listCajasPendientes.getSelectedItems().toArray();
									
								for (int i=0; i < objLiquidacion.length; i++){
									Liquidacion liquidacion = ((Listitem) objLiquidacion[i]).getValue();					
									LiquidacionOficina liquidacionOficina = new LiquidacionOficina();
									liquidacionOficina.setLiquidacion(liquidacion);
									liquidacionOficina.setAnio(liquidacion.getAnio());
									liquidacionOficina.setFechaLiquidacion(dbFecha.getValue());
									liquidacionOficina.setEstadoRegistro(Constantes.VALUE_ACTIVO);
							
									UtilData.auditarRegistro(liquidacionOficina, getUsuario(), Executions.getCurrent());
									ServiceLocator.getLiquidacionOficinaManager().guardar(liquidacionOficina);
								}
																	
								for (int i=0; i < objLiquidacion.length; i++){
									Liquidacion liquidacion = ((Listitem) objLiquidacion[i]).getValue();
									Listitem item = null;
									Listcell cell = null;
															
									item = new Listitem();
									cell = new Listcell(liquidacion.getUsuario().getApellidoPaterno()+" "+liquidacion.getUsuario().getApellidoMaterno()+", "+liquidacion.getUsuario().getNombre());
									item.appendChild(cell);
									item.setValue(liquidacion);
									listCajasLiquidadas.appendChild(item);
											
									//Remove liquidacion listCajasPendientes
									for (int y=0; y < listCajasPendientes.getRows(); y++){
										listCajasPendientes.setSelectedIndex(y);
										Liquidacion rliquidacion = listCajasPendientes.getSelectedItem().getValue();
										if (liquidacion.getId().equals(rliquidacion.getId())){
											listCajasPendientes.removeItemAt(y);
											break;
										}
									}
								}
										
							}
						}
					});
				}else{
					DlgMessage.information(Messages.getString("WndLiquidacionOficina.Information.LiquidacionesPendientesSelecNull"));
				}
			}
		});	

		
		//GASTOS---------------
		//NUEVO
		tbtNuevo.addEventListener(Events.ON_CLICK, new EventListener<Event>() {
			@Override
			public void onEvent(Event event) throws Exception {
				action=Constantes.ACTION_NEW;
				habilitaToolBarMntGastos(true, true, false, false, true);
				limpiaControlsMntGastos();
				habiliataControlsMntGastos(false);
				
				dbFechaGasto.setValue(Constantes.FORMAT_DATE.parse(myTime.dateServer()));
				
				tbgastos.setSelectedIndex(1);
			}
		});
		
		//MODIFICAR
		tbtModificar.addEventListener(Events.ON_CLICK,new EventListener<Event>() {
			@Override
			public void onEvent(Event event) throws Exception {
				if(listGastos.getSelectedIndex() >= 0){
					action=Constantes.ACTION_MODIFY;
					Listitem listitem=listGastos.getItemAtIndex(listGastos.getSelectedIndex());
					gasto = new Gasto();
					gasto=listitem.getValue();
					txtID.setText(String.valueOf(gasto.getId()));
					dbFechaGasto.setValue(dbFecha.getValue());
					Util.seleccionarValorItemCombo(TipoGasto.class, cmbTipoGasto, gasto.getTipoGasto().getId());
					txtNroDocumento.setText(gasto.getNumeroDocumento());
					dbMonto.setValue(gasto.getMonto());
					txtNombrePiloto.setText(gasto.getNombrePiloto());
					txtBus.setText(gasto.getCodigoBus());
					txtConsignado.setText(gasto.getConsignado());
					txtObservacion.setText(gasto.getObservacion());
					
					
					habiliataControlsMntGastos(false);
					habilitaToolBarMntGastos(true, true, false, false, true);
					tbgastos.setSelectedIndex(1);
				}				
			}
		});
		
		
		
		/*GUARDAR*/
		tbtGuardar.addEventListener(Events.ON_CLICK, new EventListener<Event>() {
			@Override
			public void onEvent(Event event) throws Exception {
				try{
					if (!(cmbTipoGasto.getSelectedItem().getValue() instanceof TipoGasto))
						throw new GastosException(GastosException.TIPO_GASTO_NULL);
					else if (dbMonto.getValue()==null)
						throw new GastosException(GastosException.MONTO_NULL);
					else if (dbMonto.getValue() <=0)
						throw new GastosException(GastosException.MONTO_NULL);
					
					if (action==Constantes.ACTION_NEW)
						gasto = new Gasto();
					else
						gasto.setId(new Integer(txtID.getText()));
					
					TipoGasto tipoGasto = new TipoGasto();
					tipoGasto.setId(((TipoGasto) cmbTipoGasto.getSelectedItem().getValue()).getId());
					tipoGasto.setDenominacion(((TipoGasto) cmbTipoGasto.getSelectedItem().getValue()).getDenominacion());
					
					gasto.setTipoGasto(tipoGasto);
					gasto.setNumeroDocumento(txtNroDocumento.getText().trim());
					gasto.setMonto(dbMonto.getValue());
					gasto.setNombrePiloto(txtNombrePiloto.getText().trim().toUpperCase());
					gasto.setCodigoBus(txtBus.getText());
					gasto.setConsignado(txtConsignado.getText().trim().toUpperCase());
					gasto.setObservacion(txtObservacion.getText().trim().toUpperCase());
					gasto.setEstadoRegistro(Constantes.VALUE_ACTIVO);
					
					/*Graba o actualiza el Gasto*/
					switch (action) {
						case Constantes.ACTION_NEW:
							UtilData.auditarRegistro(gasto,getUsuario(), Executions.getCurrent());
							ServiceLocator.getGastoManager().guardar(gasto);
							
							LiquidacionOficina liquidacionOficina = new LiquidacionOficina();
							liquidacionOficina.setAnio(Integer.valueOf( Constantes.FORMAT_YEAR.format(dbFecha.getValue().getTime())) );
							liquidacionOficina.setFechaLiquidacion(dbFecha.getValue());
							liquidacionOficina.setEstadoRegistro(Constantes.VALUE_ACTIVO);
							liquidacionOficina.setGasto(gasto);
							
							//UtilData.auditarRegistro(liquidacionOficina, usuario,exe);
							ServiceLocator.getLiquidacionOficinaManager().guardar(liquidacionOficina);
							break;
				
						case Constantes.ACTION_MODIFY:
							UtilData.auditarRegistro(gasto, true, getUsuario(), Executions.getCurrent());
							ServiceLocator.getGastoManager().actualizar(gasto);
							break;
					}
										
					listarRegistros((ArrayList<Gasto>) ServiceLocator.getGastoManager().buscarGastoLiqOficina(Constantes.FORMAT_DATE.format(dbFecha.getValue()), getUsuario().getLogin()));
					
					habiliataControlsMntGastos(true);
					habilitaToolBarMntGastos(false, true, true, true, true);
					tbgastos.setSelectedIndex(0);
					
				}catch (GastosException gex){
					if(gex.getTipo()==GastosException.MONTO_NULL){
						DlgMessage.information(Messages.getString("WndGasto.Information.MontoGasotNull"));
						cmbTipoGasto.setFocus(true);
					}else if(gex.getTipo()==GastosException.TIPO_GASTO_NULL){
						DlgMessage.information(Messages.getString("WndGasto.Information.TipoGastoNull"));
						dbMonto.setFocus(true);
					}
				}catch (LiquidacionNullException lnex){
					DlgMessage.information(Messages.getString("WndGasto.Information.LiquidacionNull"));					
				}catch (Exception ex) {
					DlgMessage.error(this.getClass().getName()+" "+ex.getMessage());
					ex.printStackTrace(); 
				}
				
			}
			
		});
		
		/*CANCELAR*/
		tbtCancelar.addEventListener(Events.ON_CLICK,new EventListener<Event>() {
			@Override
			public void onEvent(Event event) throws Exception {
				limpiaControlsMntGastos();
				habiliataControlsMntGastos(true);
				if(listGastos.getSelectedCount()==1)
					habilitaToolBarMntGastos(false, false, true, true, false);
				else
					habilitaToolBarMntGastos(false, true, true, true, true);
					
				tbgastos.setSelectedIndex(0);
				
			}
		});
		
		//ELIMNAR
		tbtEliminar.addEventListener(Events.ON_CLICK, new EventListener<Event>() {
			@Override
			public void onEvent(Event event) throws Exception {
				Messagebox.show(Messages.getString("WndGasto.Question.EliminarGasto"), DlgMessage.NOMBREAPLICACION, DlgMessage.BTN_YESNO, Messagebox.QUESTION, new EventListener<Event>() {
					@Override
					public void onEvent(Event e) throws Exception {
						if(e.getName().equals("onYes")){
							Integer idGasto=((Gasto)listGastos.getItemAtIndex(listGastos.getSelectedIndex()).getValue()).getId();
							Long idLiqOf=((Gasto)listGastos.getItemAtIndex(listGastos.getSelectedIndex()).getValue()).getLiquidacionOficina().getId();
							ServiceLocator.getGastoManager().inactivar(Long.valueOf(idGasto));
							ServiceLocator.getLiquidacionOficinaManager().inactivar(idLiqOf);
							listarRegistros((ArrayList<Gasto>) ServiceLocator.getGastoManager().buscarGastoLiqOficina(Constantes.FORMAT_DATE.format(dbFecha.getValue()), getUsuario().getLogin()));
							habilitaToolBarMntGastos(false, true, true, true, true);
						}	
					}	
				});		
			}
			
		});
		
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.tepsa.sisvyr.view.ui.WndBase#onCreate()
	 */
	@Override
	public void onCreate() throws Exception{

		UtilData.cargarDataCombo(cmbAgencia, Agencia.class, false);
		
		dbFecha.setValue(Constantes.FORMAT_DATE.parse(myTime.dateServer()));
		Util.seleccionarValorItemCombo(Agencia.class, cmbAgencia, getAgencia().getId());
		listarRegistros((ArrayList<Gasto>) ServiceLocator.getGastoManager().buscarGastoLiqOficina(Constantes.FORMAT_DATE.format(dbFecha.getValue()), getUsuario().getLogin()));
		
		btnPrevio.setDisabled(true);
		btnImprimir.setDisabled(true);
		btnLiquidar.setDisabled(true);
		btnLiquidar.setImage("resources/mp_aceptarDisabled.png");
		habilitaToolBarMntGastos(false, true, true, true, true);
		habiliataControlsMntGastos(true);	
		
		dbMonto.setFormat("#,##0.00");
		dbMonto.setLocale(Locale.US);
		
		UtilData.cargarDataCombo(cmbTipoGasto, TipoGasto.class, false);
		
	}
	
	private void listarRegistros(ArrayList<Gasto> lstRegistros) {
		Util.limpiarListbox(listGastos);
		
		Listitem item = null;
		Listcell cell = null;
		Integer x =0; /**Contador utilizado para el item.*/
			
		for(Gasto gasto : lstRegistros){
			x += +1;
			item = new Listitem();
			cell = new Listcell((x.toString()));
			item.appendChild(cell); //Correlativo
			cell = new Listcell(gasto.getTipoGasto().getDenominacion());
			item.appendChild(cell);
			cell = new Listcell(gasto.getMonto().toString());
			cell.setStyle("font-size:11px !important");
			item.appendChild(cell);
			cell = new Listcell(gasto.getObservacion());
			item.appendChild(cell);
			
			item.setValue(gasto);
			listGastos.appendChild(item);			
			}
	}

	/**
	 * Busca Liquidación pendiente por loquidar, según los parametros seleccionados.
	 * @throws Exception
	 */
	private void buscarLiquidacionPendiente() throws Exception{
		String fecha = Constantes.FORMAT_DATE.format(dbFecha.getValue());
		Integer idAgencia = (((Agencia)cmbAgencia.getSelectedItem().getValue()).getId());
		List<Liquidacion> lstLiquidacion=ServiceLocator.getLiquidacionOficinaManager().buscarLiquidacionPendiente(fecha, idAgencia);
		
		Util.limpiarListbox(listCajasPendientes);
		
		Listitem item = null;
		Listcell cell = null;
		for(Liquidacion liquidacion : lstLiquidacion){
			item = new Listitem();
			cell = new Listcell(liquidacion.getUsuario().getApellidoPaterno()+" "+liquidacion.getUsuario().getApellidoMaterno()+", "+liquidacion.getUsuario().getNombre());
			item.appendChild(cell);
			item.setValue(liquidacion);
			listCajasPendientes.appendChild(item);					
		}		
	}
	
	/**
	 * Buscar la liquidaciones liquidadas.
	 */
	public void buscarLiquidacionOficina(){
		String fecha=Constantes.FORMAT_DATE.format(dbFecha.getValue());
		Integer idAgencia=((Agencia)cmbAgencia.getSelectedItem().getValue()).getId();
		List<Liquidacion> lstLiquidacion=ServiceLocator.getLiquidacionOficinaManager().buscarLiquidacionLiquidadas(fecha, idAgencia);
		
		Util.limpiarListbox(listCajasLiquidadas);
		Listitem item = null;
		Listcell cell = null;
		for(Liquidacion liquidacion : lstLiquidacion){
			item = new Listitem();
			cell = new Listcell(liquidacion.getUsuario().getApellidoPaterno()+" "+liquidacion.getUsuario().getApellidoMaterno()+", "+liquidacion.getUsuario().getNombre());
			item.appendChild(cell);
			item.setValue(liquidacion);
			listCajasLiquidadas.appendChild(item);					
		}	
	}
	
	
	/**
	 * FechaMovimiento
	 * Monto
	 * FechaReg
	 * Observacion
	 */
	
	
	//Habilita y/o Deshabilita comtroles del mantenimineto de Gastos
		private void limpiaControlsMntGastos(){
			cmbTipoGasto.setSelectedIndex(0);
			txtNroDocumento.setText("");
			dbMonto.setText("");
			txtNombrePiloto.setText("");
			txtConsignado.setText("");
			txtObservacion.setText("");
			txtBus.setText("");
			dbFechaGasto.setText("");
		}
		
	//Habilita y/o Deshabilita comtroles del mantenimineto de Gastos
	private void habiliataControlsMntGastos(boolean estado){
		cmbTipoGasto.setDisabled(estado);
		txtNroDocumento.setDisabled(estado);
		dbMonto.setDisabled(estado);
		txtNombrePiloto.setDisabled(estado);
		txtConsignado.setDisabled(estado);
		txtObservacion.setDisabled(estado);
		txtBus.setDisabled(estado);		
		dbFechaGasto.setDisabled(estado);
	}
	
	/*Habilita/deshabilita toolbar del mantenimiento de gastos*/
	private void habilitaToolBarMntGastos(Boolean nuevo, boolean modificar, boolean cancelar, boolean guardar, boolean eliminar){
		tbtNuevo.setDisabled(nuevo);
		tbtModificar.setDisabled(modificar);
		tbtCancelar.setDisabled(cancelar);
		tbtGuardar.setDisabled(guardar);
		tbtEliminar.setDisabled(eliminar);
	}
	
	public void onSelectListGastos(){
		habilitaToolBarMntGastos(false, false, true, true, false);
	}
	
	public void onSelecFechaLiq(){
		listarRegistros((ArrayList<Gasto>) ServiceLocator.getGastoManager().buscarGastoLiqOficina(Constantes.FORMAT_DATE.format(dbFecha.getValue()), getUsuario().getLogin()));
	}
	
	
	
}
