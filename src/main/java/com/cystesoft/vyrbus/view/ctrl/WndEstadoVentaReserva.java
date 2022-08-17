
package com.cystesoft.vyrbus.view.ctrl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zul.Checkbox;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Comboitem;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Menuitem;
import org.zkoss.zul.Menupopup;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Radio;
import org.zkoss.zul.Tab;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Toolbarbutton;
import org.zkoss.zul.Window;

import com.cystesoft.vyrbus.model.bean.Localidad;
import com.cystesoft.vyrbus.model.bean.TipoMovimiento;
import com.cystesoft.vyrbus.model.bean.Usuario;
import com.cystesoft.vyrbus.model.bean.VentaPasaje;
import com.cystesoft.vyrbus.service.exceptions.EstadoVentasReservasException;
import com.cystesoft.vyrbus.service.fe.Result;
import com.cystesoft.vyrbus.service.locator.ServiceLocator;
import com.cystesoft.vyrbus.service.util.Constantes;
import com.cystesoft.vyrbus.service.util.Messages;
import com.cystesoft.vyrbus.service.util.Util;
import com.cystesoft.vyrbus.service.util.UtilData;
import com.cystesoft.vyrbus.service.util.WSFE;
import com.cystesoft.vyrbus.view.ui.DlgMessage;
import com.cystesoft.vyrbus.view.ui.WndBase;
import com.cystesoft.vyrbus.view.ui.WndBuscarPasajero;

/**
 * @author JABANTO
 *
 */
public class WndEstadoVentaReserva extends WndBase {
	private static final long serialVersionUID = 1L;

	private Datebox dtbxFechaInicioVenta;
	private Datebox dtbxFechaFinVenta;
	private Datebox dtbxFechaPartida;
	private Radio rdByDocumento;
	private Radio rdByNombres;
	private Radio rdByRuc;
	private Radio rdByRazonSocial;
	private Textbox txtBusquedaPasajero;
	private Textbox txtBusquedaCliente;
	private Textbox txtNumeroControl;
	private Textbox txtNumeroBoleto;
	private Combobox cmbUsuario;
	private Combobox cmbTipomovimiento;
	private Combobox cmbOrigen;
	private Combobox cmbDestino;
	private Listbox lstbxListaMovimientos;

	private Listbox lstbxHistorial;
	private Tab tabEstado;
	private Tab tabHistorial;
	private Checkbox chkHistorial;

//	private Radio rdAnular;
//	private Radio rdNotaCredito;

	int BUSQ_CLIENTE_RUC=0;
	int BUSQ_CLIENTE_RAZONSOCIAL=1;
	int BUSQ_PAX_NRO_DOCUMENTO=0;
	int BUSQ_PAX_NOMBRES=1;

	private Window wndEstadoVyR;
	private Menupopup menupopup= new Menupopup();



//	private Window wndAnular;

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.view.ui.WndBase#initComponents()
	 */
	@Override
	public void initComponents() {
		// TODO Auto-generated method stub
		wndEstadoVyR= (Window)this.getFellow("wndEstadoVyR");
		dtbxFechaInicioVenta=(Datebox)this.getFellow("dtbxFechaInicioVenta");
		dtbxFechaFinVenta=(Datebox)this.getFellow("dtbxFechaFinVenta");
		dtbxFechaPartida=(Datebox)this.getFellow("dtbxFechaPartida");
		rdByDocumento=(Radio)this.getFellow("rdByDocumento");
		rdByNombres=(Radio)this.getFellow("rdByNombres");
		rdByRuc=(Radio)this.getFellow("rdByRuc");
		rdByRazonSocial=(Radio)this.getFellow("rdByRazonSocial");
		txtBusquedaPasajero=(Textbox)this.getFellow("txtBusquedaPasajero");
		txtBusquedaCliente=(Textbox)this.getFellow("txtBusquedaCliente");
		txtNumeroControl=(Textbox)this.getFellow("txtNumeroControl");
		txtNumeroBoleto=(Textbox)this.getFellow("txtNumeroBoleto");
		cmbUsuario=(Combobox)this.getFellow("cmbUsuario");
		cmbTipomovimiento=(Combobox)this.getFellow("cmbTipomovimiento");
		cmbOrigen=(Combobox)this.getFellow("cmbOrigen");
		cmbDestino=(Combobox)this.getFellow("cmbDestino");
		lstbxListaMovimientos=(Listbox)this.getFellow("lstbxListaMovimientos");

		lstbxHistorial=(Listbox)this.getFellow("lstbxHistorial");
		tabEstado = (Tab)this.getFellow("tabEstado");
		tabHistorial = (Tab)this.getFellow("tabHistorial");
		chkHistorial = (Checkbox)this.getFellow("chkHistorial");

		//Auto completa el numero de control
		txtNumeroControl.addEventListener(Events.ON_CHANGE, new EventListener<Event>() {
			@Override
			public void onEvent(Event event) throws Exception {
				// TODO Auto-generated method stub
				if(txtNumeroControl.getText().trim().length()>0)
					txtNumeroControl.setText(Util.generateControlNumber(txtNumeroControl.getText().trim().toUpperCase()));
			}
		});

		//Autocompleta el numero de Boleto
		txtNumeroBoleto.addEventListener(Events.ON_CHANGE,new EventListener<Event>() {
			@Override
			public void onEvent(Event event) throws Exception {
				if(txtNumeroBoleto.getText().trim().length()>0)
					txtNumeroBoleto.setText(Util.autocompleNumberBoleto(txtNumeroBoleto.getText()));
			}

		});


	}


	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.view.ui.WndBase#onCreate()
	 */
	@Override
	public void onCreate() throws Exception {
		// TODO Auto-generated method stub
//		String fecha = Util.DatetoString(new Date(), "yyyyMMdd");
//		dtbxFechaPartida.setConstraint("after "+fecha);

		UtilData.cargarGenericData(cmbUsuario, true);
		onLoadMovimientos(cmbTipomovimiento);
		UtilData.cargarDataCombo(cmbOrigen, Localidad.class, true);
		UtilData.cargarDataCombo(cmbDestino, Localidad.class, true);
	}

	/**
	 * Ejecuta la busqueda, segun los parametros seleccionados.
	 * @throws Exception
	 */
	public void buscar() throws Exception{
		try{
			menupopup.detach();

			if (dtbxFechaInicioVenta.getValue()==null && dtbxFechaFinVenta.getValue()==null && dtbxFechaPartida.getValue()==null
					&& txtNumeroBoleto.getText().trim().isEmpty() && txtNumeroControl.getText().trim().isEmpty() )
				throw new EstadoVentasReservasException(EstadoVentasReservasException.FECHAS_NULL);
			else if(dtbxFechaInicioVenta.getValue()!=null && dtbxFechaFinVenta.getValue()==null)
				throw new EstadoVentasReservasException(EstadoVentasReservasException.FECHA_FIN_NULL);
			else if(dtbxFechaInicioVenta.getValue()==null && dtbxFechaFinVenta.getValue()!=null)
				throw new EstadoVentasReservasException(EstadoVentasReservasException.FECHA_INCIO_NULL);

			String styleActivo_11px="font-size:11px !important";
			String styleActivo_9px="font-size:9px !important";
			String styleAnulado_11px="font-size:11px !important; color:red";
			String styleAnulado_9px="font-size:9px !important; color:red";

			String fechaInicialVenta=null;
			String fechaFinVenta=null;
			String fechaPartida=null;
			Integer idUsuario=null;
			Integer idOrigen=null;
			Integer tipoMovimiento=null;
			Integer idDestino=null;
			Integer tipoBusqPax=rdByDocumento.isChecked()?BUSQ_PAX_NRO_DOCUMENTO:BUSQ_PAX_NOMBRES;
			Integer tipoBusqCliente=rdByRuc.isChecked()?BUSQ_CLIENTE_RUC:BUSQ_CLIENTE_RAZONSOCIAL;
			String numeroControl=null;
			String numeroBoleto=null;
			String busqPax=null;
			String busqCliente=null;

			if(dtbxFechaInicioVenta.getValue()!=null)
				fechaInicialVenta=Constantes.FORMAT_DATE.format(dtbxFechaInicioVenta.getValue());
			if(dtbxFechaFinVenta.getValue()!=null)
				fechaFinVenta=Constantes.FORMAT_DATE.format(dtbxFechaFinVenta.getValue());
			if(dtbxFechaPartida.getValue()!=null)
				fechaPartida=Constantes.FORMAT_DATE.format(dtbxFechaPartida.getValue());
			if(cmbUsuario.getSelectedItem().getValue() instanceof Usuario)
				idUsuario=((Usuario)cmbUsuario.getSelectedItem().getValue()).getId();
			if(cmbTipomovimiento.getSelectedIndex()>0)
				tipoMovimiento=Integer.valueOf((String)cmbTipomovimiento.getSelectedItem().getValue());
			if(cmbOrigen.getSelectedItem().getValue() instanceof Localidad)
				idOrigen=((Localidad)cmbOrigen.getSelectedItem().getValue()).getId();
			if(cmbDestino.getSelectedItem().getValue() instanceof Localidad)
				idDestino=((Localidad)cmbDestino.getSelectedItem().getValue()).getId();
			if(!(txtNumeroControl.getText().trim().isEmpty()))
				numeroControl=txtNumeroControl.getText().trim().toUpperCase();
			if(!(txtNumeroBoleto.getText().trim().isEmpty()))
				numeroBoleto=txtNumeroBoleto.getText().trim();
			if(!(txtBusquedaPasajero.getText().trim().isEmpty()))
				busqPax=txtBusquedaPasajero.getText().trim().toUpperCase();
			if(!(txtBusquedaCliente.getText().trim().isEmpty()))
				busqCliente=txtBusquedaCliente.getText().trim().toUpperCase();

			if(!chkHistorial.isChecked()){
				tabEstado.setVisible(true);
				tabHistorial.setVisible(false);
				tabEstado.setSelected(true);
				tabHistorial.setSelected(false);
				List<VentaPasaje>lstVenta=ServiceLocator.getVentaPasajesManager().
										  buscarEstadoVentasReservas(fechaInicialVenta,
												  					 fechaFinVenta,
												  					 tipoBusqCliente,
												  					 busqCliente,
												  					 tipoBusqPax,
												  					 busqPax,
												  					 fechaPartida,
												  					 idUsuario,
												  					 numeroControl,
												  					 numeroBoleto,
												  					 idOrigen,
												  					 idDestino,
												  					 tipoMovimiento);
				Util.limpiarListbox(lstbxListaMovimientos);
				int x=-1;

				for(VentaPasaje ventaPasaje: lstVenta){
					TipoMovimiento otipoMovimiento=ventaPasaje.getTipoMovimiento();
					boolean isAnulado=otipoMovimiento.getId().intValue()==Constantes.ID_TIPMOV_ANULACION || otipoMovimiento.getId().intValue()==Constantes.ID_TIPMOV_ANULACION_SISTEMA  ?true:false;

					Listitem item= new Listitem();
					Listcell cell=new Listcell(ventaPasaje.getFechaLiquidacion()!=null?Constantes.FORMAT_DATE.format(ventaPasaje.getFechaLiquidacion()):Constantes.FORMAT_DATE.format(ventaPasaje.getFechaInsercion()));
					cell.setStyle(isAnulado?styleAnulado_11px:styleActivo_11px);
					item.appendChild(cell);
					cell=new Listcell(ventaPasaje.getTipoTransaccion());
					cell.setStyle(isAnulado?styleAnulado_9px:styleActivo_9px);
					item.appendChild(cell);
					cell=new Listcell(ventaPasaje.getTipoComprobante().getDenominacion());
					cell.setStyle(isAnulado?styleAnulado_11px:styleActivo_11px);
					item.appendChild(cell);
					cell=new Listcell(ventaPasaje.getNumeroBoleto());
					cell.setStyle(isAnulado?styleAnulado_11px:styleActivo_11px);
					item.appendChild(cell);

	//				cell=new Listcell("T"+ventaPasaje.getNumeroControl().substring(5,ventaPasaje.getNumeroControl().length()));
	//				cell.setStyle(isAnulado?styleAnulado_11px:styleActivo_11px);
	//				item.appendChild(cell);
					cell=new Listcell(ventaPasaje.getRuta().toString());
					cell.setStyle(isAnulado?styleAnulado_9px:styleActivo_9px);
					item.appendChild(cell);
					cell=new Listcell(ventaPasaje.getFechaPartida()!=null?Constantes.FORMAT_DATE.format(ventaPasaje.getFechaPartida()):"");
					cell.setStyle(isAnulado?styleAnulado_11px:styleActivo_11px);
					item.appendChild(cell);
					cell=new Listcell(ventaPasaje.getHoraPartida());
					cell.setStyle(isAnulado?styleAnulado_11px:styleActivo_11px);
					item.appendChild(cell);
					cell=new Listcell(ventaPasaje.getPasajero().getNombresApellidos());
					cell.setStyle(isAnulado?styleAnulado_9px:styleActivo_9px);
					item.appendChild(cell);
					cell=new Listcell(ventaPasaje.getCliente().toString());
					cell.setStyle(isAnulado?styleAnulado_9px:styleActivo_9px);
					item.appendChild(cell);
					cell=new Listcell(ventaPasaje.getNumeroAsiento()!=null?ventaPasaje.getNumeroAsiento().toString():"");
					cell.setStyle(isAnulado?styleAnulado_11px:styleActivo_11px);
					item.appendChild(cell);

					item.addEventListener(Events.ON_DOUBLE_CLICK,new EventListener<Event>() {
						@Override
						public void onEvent(Event event) throws Exception {
							// TODO Auto-generated method stub
							VentaPasaje ventaPasaje=((VentaPasaje)lstbxListaMovimientos.getSelectedItem().getValue());
							Map<String, Object> params = new HashMap<>();
							params.put("ventaPasaje", ventaPasaje);
							Window win = (Window) Executions.createComponents("estadoVentaReservaVerVenta.zul", null, params);
							win.setMode(MODAL);
						}
					});

					Toolbarbutton tbverVenta= new Toolbarbutton("Ver venta");
					tbverVenta.setStyle("text-transform:uppercase; color:blue;");
					x++;
					tbverVenta.setId(String.valueOf(x));
					cell= new Listcell();
					cell.appendChild(tbverVenta);
					item.appendChild(cell);
					tbverVenta.addEventListener(Events.ON_CLICK,new EventListener<Event>() {
						@Override
						public void onEvent(Event event) throws Exception {
							// TODO Auto-generated method stub
							Listitem item= lstbxListaMovimientos.getItemAtIndex(Integer.valueOf(event.getTarget().getId()));
							VentaPasaje ventaPasaje=item.getValue();
							Map<String, Object> params = new HashMap<>();
							params.put("ventaPasaje", ventaPasaje);
							Window win = (Window) Executions.createComponents("estadoVentaReservaVerVenta.zul", null, params);
							win.setMode(MODAL);
						}
					});

					item.setValue(ventaPasaje);
					item.setTooltiptext("Haga doble click para ver la información de la Venta ");
					item.setContext(getMenucontex(ventaPasaje));
					lstbxListaMovimientos.appendChild(item);
				}
			}
			else{
				tabEstado.setVisible(false);
				tabHistorial.setVisible(true);
				tabEstado.setSelected(false);
				tabHistorial.setSelected(true);

				List<VentaPasaje>lstVenta=ServiceLocator.getVentaPasajesManager().buscarHistorialComprobante(numeroBoleto);

				listarHistorial(lstVenta);

			}

		}catch (EstadoVentasReservasException esx){
			if(esx.getTipo().intValue()==EstadoVentasReservasException.FECHA_INCIO_NULL){
				DlgMessage.information(Messages.getString("WndEstadoVentasReservas.information.noSelectFechaInicio"),dtbxFechaInicioVenta);
			}else if (esx.getTipo().intValue()==EstadoVentasReservasException.FECHA_FIN_NULL){
				DlgMessage.information(Messages.getString("WndEstadoVentasReservas.information.noSelectFechaFin"),dtbxFechaFinVenta);
			}else if (esx.getTipo().intValue()==EstadoVentasReservasException.FECHAS_NULL){
				DlgMessage.information(Messages.getString("WndEstadoVentasReservas.information.noSelectFechas"),dtbxFechaInicioVenta);
			}
		}


	}


	public void listarHistorial(List<VentaPasaje> lstHistorial){
		String styleActivo_11px="font-size:11px !important";
		String styleActivo_9px="font-size:9px !important";
		String styleAnulado_11px="font-size:11px !important; color:red";
		String styleAnulado_9px="font-size:9px !important; color:red";

		Util.limpiarListbox(lstbxHistorial);
		int x=-1;

		for(VentaPasaje ventaPasaje: lstHistorial){
			TipoMovimiento otipoMovimiento=ventaPasaje.getTipoMovimiento();
			boolean isAnulado=otipoMovimiento.getId().intValue()==Constantes.ID_TIPMOV_ANULACION ||
							  otipoMovimiento.getId().intValue()==Constantes.ID_TIPMOV_ANULACION_SISTEMA  ? true : false;

			Listitem item= new Listitem();
			Listcell cell=new Listcell(ventaPasaje.getId().toString());
			cell.setStyle(isAnulado?styleAnulado_11px:styleActivo_11px);
			item.appendChild(cell);

			//cell=new Listcell(ventaPasaje.getVentaOriginal().toString());
			cell=new Listcell("");
			cell.setStyle(isAnulado?styleAnulado_11px:styleActivo_11px);
			item.appendChild(cell);

			cell=new Listcell(ventaPasaje.getFechaLiquidacion()!=null ?
							  Constantes.FORMAT_DATE.format(ventaPasaje.getFechaLiquidacion()) :
							  Constantes.FORMAT_DATE.format(ventaPasaje.getFechaInsercion()));
			cell.setStyle(isAnulado?styleAnulado_9px:styleActivo_9px);
			item.appendChild(cell);

			cell=new Listcell(ventaPasaje.getRuta().toString());
			cell.setStyle(isAnulado?styleAnulado_9px:styleActivo_9px);
			item.appendChild(cell);

			cell=new Listcell(ventaPasaje.getServicio().toString());
			cell.setStyle(isAnulado?styleAnulado_9px:styleActivo_9px);
			item.appendChild(cell);


			cell=new Listcell(ventaPasaje.getTipoComprobante().getAbreviatura());
			cell.setStyle(isAnulado?styleAnulado_11px:styleActivo_11px);
			item.appendChild(cell);

			cell=new Listcell(ventaPasaje.getNumeroBoleto());
			cell.setStyle(isAnulado?styleAnulado_11px:styleActivo_11px);
			item.appendChild(cell);
			cell=new Listcell(ventaPasaje.getNumeroBoletoAnterior());
			cell.setStyle(isAnulado?styleAnulado_11px:styleActivo_11px);
			item.appendChild(cell);
			cell=new Listcell(ventaPasaje.getNumeroControl());
			cell.setStyle(isAnulado?styleAnulado_11px:styleActivo_11px);
			item.appendChild(cell);

			cell=new Listcell(ventaPasaje.getFechaPartida()!=null?Constantes.FORMAT_DATE.format(ventaPasaje.getFechaPartida()):"");
			cell.setStyle(isAnulado?styleAnulado_11px:styleActivo_11px);
			item.appendChild(cell);
			cell=new Listcell(ventaPasaje.getHoraPartida());
			cell.setStyle(isAnulado?styleAnulado_11px:styleActivo_11px);
			item.appendChild(cell);
			cell=new Listcell(ventaPasaje.getPasajero().getNombresApellidos());
			cell.setStyle(isAnulado?styleAnulado_9px:styleActivo_9px);
			item.appendChild(cell);
			cell=new Listcell(ventaPasaje.getCliente().toString());
			cell.setStyle(isAnulado?styleAnulado_9px:styleActivo_9px);
			item.appendChild(cell);

			cell=new Listcell(ventaPasaje.getNumeroAsiento()!=null?ventaPasaje.getNumeroAsiento().toString():"");
			cell.setStyle(isAnulado?styleAnulado_11px:styleActivo_11px);
			item.appendChild(cell);
			cell=new Listcell(otipoMovimiento.getDenominacion());
			cell.setStyle(isAnulado?styleAnulado_9px:styleActivo_9px);
			item.appendChild(cell);

			cell=new Listcell(ventaPasaje.getImportePagado().toString());
			cell.setStyle(isAnulado?styleAnulado_9px:styleActivo_9px);
			item.appendChild(cell);

			cell=new Listcell(ventaPasaje.getImportePagadoByDiferencia().toString());
			cell.setStyle(isAnulado?styleAnulado_9px:styleActivo_9px);
			item.appendChild(cell);

			cell=new Listcell(ventaPasaje.getCanalVenta().getNombreCorto());
			cell.setStyle(isAnulado?styleAnulado_9px:styleActivo_9px);
			item.appendChild(cell);

			cell=new Listcell(ventaPasaje.getAgencia().getNombreCorto());
			cell.setStyle(isAnulado?styleAnulado_9px:styleActivo_9px);
			item.appendChild(cell);

			cell=new Listcell(ventaPasaje.getManifiesto().getNumeroManifiesto());
			cell.setStyle(isAnulado?styleAnulado_9px:styleActivo_9px);
			item.appendChild(cell);

			item.addEventListener(Events.ON_DOUBLE_CLICK,new EventListener<Event>() {
				@Override
				public void onEvent(Event event) throws Exception {
					// TODO Auto-generated method stub
					VentaPasaje ventaPasaje=((VentaPasaje)lstbxHistorial.getSelectedItem().getValue());
					Map<String, Object> params = new HashMap<>();
					params.put("ventaPasaje", ventaPasaje);
					Window win = (Window) Executions.createComponents("estadoVentaReservaVerVenta.zul", null, params);
					win.setMode(MODAL);
				}
			});

			Toolbarbutton tbverVenta= new Toolbarbutton("Ver venta");
			tbverVenta.setStyle("text-transform:lowercase; color:blue;");
			x++;
			tbverVenta.setId(String.valueOf(x));
			cell= new Listcell();
			cell.appendChild(tbverVenta);
			item.appendChild(cell);
			tbverVenta.addEventListener(Events.ON_CLICK,new EventListener<Event>() {
				@Override
				public void onEvent(Event event) throws Exception {
					// TODO Auto-generated method stub
					Listitem item= lstbxHistorial.getItemAtIndex(Integer.valueOf(event.getTarget().getId()));
					VentaPasaje ventaPasaje=item.getValue();
					Map<String, Object> params = new HashMap<>();
					params.put("ventaPasaje", ventaPasaje);
					Window win = (Window) Executions.createComponents("estadoVentaReservaVerVenta.zul", null, params);
					win.setMode(MODAL);
				}
			});

			item.setValue(ventaPasaje);
			item.setTooltiptext("Haga doble click para ver la información de la Venta ");
			//item.setContext(getMenucontex(ventaPasaje));
			lstbxHistorial.appendChild(item);
		}
	}

	/**
	 * Crea el menu contextual para el Listbox (Click derecho)
	 * @param ventaPasaje
	 * @return
	 * @throws Exception
	 */
	private Menupopup getMenucontex(final VentaPasaje ventaPasaje)throws Exception{
		String idContext=ventaPasaje.getId().toString();

		/*Elimina el componemte que coincida con el nuevo a agregar*/
		for(Component component :this.wndEstadoVyR.getChildren()){
			if(component instanceof Menupopup){
				Menupopup menupopup= (Menupopup)component;
				if(menupopup.getId().equals(idContext)){
					this.wndEstadoVyR.removeChild(component);
					break;
				}
			}
		}

		menupopup= new Menupopup();
		menupopup.setId(idContext);

		/*========================================================================================*/
		/*ANULACION DE COMPROBANTES - END BEGIN - 01/02/2017 - JABANTO (Se translado al modulo "anulacion de comprobantes")*/
		/*========================================================================================*/
//		Menuitem menuitem=new Menuitem("Anular Comprobante","/resources/mp_anularEnabled.png");
//		menuitem.setVisible(false);
//		menuitem.setDisabled(true);
//
//		if(getRol().getId().intValue()==Constantes.ID_ROL_SUPER_USUARIO || getRol().getId().intValue()==Constantes.ID_ROL_GESTION_CORPORATIVA){
//			menuitem.setDisabled(false);
//			if(ventaPasaje.getTipoMovimiento().getId().intValue()==Constantes.ID_TIPMOV_ANULACION){
//				menuitem.setDisabled(true);
//			}else if (ventaPasaje.getTipoMovimiento().getId().intValue()==Constantes.ID_TIPMOV_DEVOLUCION){
//				menuitem.setDisabled(true);
//			}else if(ServiceLocator.getDetalleManifiestoManager().validarVentaManifiesto(Long.valueOf(ventaPasaje.getId()))){
//				menuitem.setDisabled(true);
//			}
//		}
//		menuitem.addEventListener(Events.ON_CLICK, new EventListener<Event>() {
//			@Override
//			public void onEvent(Event event) throws Exception {
//				try{
//					Date fechaLiquidacion = (Date) Executions.getCurrent().getDesktop().getSession().getAttribute(Constantes.ATRIBUTO_FECHA_LIQUIDACION);
//					Date fechaActual= Constantes.FORMAT_DATE.parse(MyTime.dateTimeServer());
//					boolean soloAlicaNotaCredito=false;
//					if(getRol().getId().intValue()==Constantes.ID_ROL_GESTION_CORPORATIVA){
//						soloAlicaNotaCredito=true;
//						if(fechaLiquidacion==null){
//							DlgMessage.information("Debe tener una liquidación aperturada y con fecha del Día actual.");
//							return;
//						}else if(fechaLiquidacion.getTime()!=fechaActual.getTime()){
//							DlgMessage.information("La fecha de su Liquidación es diferente a la Actual, Ciérrela y apertura una nueva.");
//							return;
//						}else if (ventaPasaje.getFormaPago().getId().intValue()!=Constantes.ID_FORPAG_CREDITO){
//							DlgMessage.information("El Comprobante no puede ser Anulado.");
//							return;
//						}
//					}
//
//					createWindowAnulacion(ventaPasaje, soloAlicaNotaCredito);
//
//				} catch (Exception e) {
//					e.printStackTrace();
//					DlgMessage.error(e.getMessage());
//				}
//			}
//		});
//		menupopup.appendChild(menuitem);

		/*========================================================================================*/
		/*ENVIO DE COMPROBANTE AL S.F.E.*/
		/*========================================================================================*/
		Menuitem menuitem=new Menuitem("Enviar Comprobante S.F.E.","/resources/mp_acceptEnabled.png");
		menuitem.setDisabled(true);
		if((ventaPasaje.getEnviadoSFE()==null || ventaPasaje.getEnviadoSFE().intValue()==Constantes.FAILURE) &&
				(getRol().getId().intValue()==Constantes.ID_ROL_SUPER_USUARIO || getRol().getId().intValue()==Constantes.ID_ROL_FISCALIZACION))
			menuitem.setDisabled(false);
		menuitem.addEventListener(Events.ON_CLICK, new EventListener<Event>() {
			@Override
			public void onEvent(Event event) throws Exception {
				try {
					enviarServerFE(ventaPasaje.getId());
				} catch (Exception e) {
					e.printStackTrace();
					DlgMessage.error(e.getMessage());
				}
			}
		});
		menupopup.appendChild(menuitem);



		wndEstadoVyR.appendChild(menupopup);
		return menupopup;
	}

	/**
	 * Realiza el envio del comprobante al Servidor de Facturacion electronica
	 * @param ventaPasajeId
	 */
	public void enviarServerFE(final Long ventaPasajeId){
		Messagebox.show("¿Realmente esta seguro de Enviar el Comprobante al Servidor de Facturación Electrónica?", DlgMessage.NOMBREAPLICACION, DlgMessage.BTN_YESNO, Messagebox.QUESTION, new EventListener<Event>() {
			@Override
			public void onEvent(Event e){
				try {
					if(e.getName().equals("onYes")){
						Result result=null;
						VentaPasaje oveVentaPasaje=ServiceLocator.getVentaPasajesManager().buscarVentaById(ventaPasajeId);
						if(oveVentaPasaje.getTipoComprobante().getId().intValue()==Constantes.ID_TIPCOM_NOTA_CREDITO){
							VentaPasaje oVentaPasaje=ServiceLocator.getVentaPasajesManager().buscarPorId(ventaPasajeId);
							result = WSFE.sendNota(oVentaPasaje,true);
						}else{
							List<VentaPasaje>listVentaPasaje= new ArrayList<>();
							listVentaPasaje.add(oveVentaPasaje);
							result= WSFE.sendVenta(listVentaPasaje, wndEstadoVyR, false, null,true);
						}

						if(result!=null){
							DlgMessage.information(result.getMessage().getValue());
							buscar();
						}else
							DlgMessage.information("Ha ocurrido un error al intentar enviar el Documento");
					}
				} catch (Exception e2) {
					e2.printStackTrace();
					DlgMessage.error(e2.getMessage());
				}
			}
		});
	}

//	private void createWindowAnulacion(VentaPasaje ventaPasaje, boolean soloAlicaNotaCredito)throws Exception{
//		final VentaPasaje ventaOriginal = ServiceLocator.getVentaPasajesManager().buscarVentaById(Long.valueOf(ventaPasaje.getId()));
//		final VentaPasaje ultimoMoviento = ServiceLocator.getVentaPasajesManager().buscarUltimoRegistro(ventaOriginal.getVentaOriginal());
//
//		if(ultimoMoviento.getId().longValue()!=ventaOriginal.getId().longValue()){
//			DlgMessage.information(Messages.getString("WndLiquidacionDiariaVentas.information.noAnulacion"));
//			return;
//		}
//
//		//Validación para la anulación de un Reecibo de caja
//		if (ventaOriginal.getTipoComprobante().getId().intValue()==Constantes.ID_TIPCOM_RECIBO_CAJA){
//			VentaPasaje ultimoRegistro=ServiceLocator.getVentaPasajesManager().buscarUltimoRegistro(ventaOriginal.getVentaOriginal());
//			//Valida si RC esta reimpreso y no esta anulado para continuar con la anulación.
//			if (ultimoRegistro.getTipoComprobante().getId().intValue()!=Constantes.ID_TIPCOM_RECIBO_CAJA &&
//					ultimoRegistro.getTipoMovimiento().getId().intValue()!=Constantes.ID_TIPMOV_ANULACION){
//				DlgMessage.information(Messages.getString("WndLiquidacionDiariaVentas.information.RCReimpreso"));
//				return;
//			}
//		}
//
//		if(ventaOriginal.getTipoMovimiento().getId().intValue()==Constantes.ID_TIPMOV_ANULACION){
//			DlgMessage.information(Messages.getString("WndLiquidacionDiariaVentas.information.boletoAnulado"));
//			return;
//		}else if (ventaOriginal.getTipoMovimiento().getId().intValue()==Constantes.ID_TIPMOV_DEVOLUCION){
//			DlgMessage.information(Messages.getString("WndLiquidacionDiariaVentas.information.boletoDevuelto"));
//			return;
//		}else if(ServiceLocator.getDetalleManifiestoManager().validarVentaManifiesto(Long.valueOf(ventaPasaje.getId()))){
//			DlgMessage.information(Messages.getString("Generales.information.manifiestoImpreso"));
//			return;
//		}
//
//		wndAnular = createVentanaAnulacion(ventaPasaje.getId(), soloAlicaNotaCredito);
//		this.appendChild(wndAnular);
//		wndAnular.setMode("modal");
//	}

//	@SuppressWarnings("deprecation")
//	private Window createVentanaAnulacion(Long ventaPasajeid, boolean soloAlicaNotaCredito)throws Exception{
//		final VentaPasaje ventaOriginal=ServiceLocator.getVentaPasajesManager().buscarPorId(ventaPasajeid);
//
//		Caption caption = null;
//		Groupbox groupbox = null;
//		Grid grid = new Grid();
//		Columns columns = new Columns();
//		Column column = null;
//		Rows rows = new Rows();
//		Row row = null;
//		Label label = null;
//		Textbox text = null;
//
//		final Window win = new Window("", "normal", true);
//		win.setWidth("500px");
//
//		caption = new Caption("ANULACION DEl COMPROBANTE", "");
//		win.appendChild(caption);
//		label = new Label("Se va a realizar la Anulacion del Comprobante con los siguientes datos :");
//		label.setStyle("font-size:12px !important");
//		win.appendChild(label);
//
//		win.appendChild(new Separator("horizontal"));
//
//		groupbox = new Groupbox();
//		groupbox.setClosable(false);
//		caption = new Caption("Datos del Comprobante");
//		groupbox.appendChild(caption);
//
//		/*	Columna 1	*/
//		column = new Column();
//		column.setAlign("right");
//		columns.appendChild(column);
//		/*	Columna 2	*/
//		column = new Column();
//		columns.appendChild(column);
//		/*	Columna 3	*/
//		column = new Column();
//		column.setAlign("right");
//		columns.appendChild(column);
//		/*	Columna 4	*/
//		column = new Column();
//		columns.appendChild(column);
//
//		grid.appendChild(columns);
//
//		row = new Row();
//		label = new Label("NUMERO BOLETO :");
//		row.appendChild(label);
//		text = new Textbox(ventaOriginal.getNumeroBoleto());
//		text.setReadonly(true);
//		text.setWidth("80px");
//		text.setStyle("font-size:11px !important");
//		row.appendChild(text);
//		label = new Label("FECHA VIAJE :");
//		row.appendChild(label);
//		text = new Textbox(ventaOriginal.getFechaPartida()==null?"":Util.DatetoString(ventaOriginal.getFechaPartida(), Constantes.DATE_FORMAT));
//		text.setReadonly(true);
//		text.setWidth("80px");
//		text.setStyle("font-size:11px !important");
//		row.appendChild(text);
//		rows.appendChild(row);
//
//		row = new Row();
//		row.setSpans("1,3");
//		label = new Label("PASAJERO :");
//		row.appendChild(label);
//		text = new Textbox(ventaOriginal.getPasajero().toString());
//		text.setReadonly(true);
//		text.setWidth("314px");
//		text.setStyle("font-size:11px !important");
//		row.appendChild(text);
//		rows.appendChild(row);
//
//		row = new Row();
//		label = new Label("RUTA :");
//		row.appendChild(label);
//		text = new Textbox(ventaOriginal.getRuta().getOrigen()+" - " + ventaOriginal.getRuta().getDestino());
//		text.setReadonly(true);
//		text.setWidth("80px");
//		row.appendChild(text);
//		label = new Label("IMPORTE :");
//		row.appendChild(label);
//		text = new Textbox(Util.toNumberFormat(ventaOriginal.getImportePagado(), 2));
//		text.setReadonly(true);
//		text.setWidth("80px");
//		text.setStyle("font-size:11px !important");
//		row.appendChild(text);
//		rows.appendChild(row);
//
//		row=new Row();
//		row.setSpans("1,3");
//		label = new Label("TIPO OPERACION (*) :");
//		row.appendChild(label);
//		rdAnular= new Radio("Anular comprobante");
//		rdAnular.setStyle("color:red");
//		rdNotaCredito= new Radio("Aplicar Nota de Crédito");
//		rdNotaCredito.setStyle("color:blue");
//		if(soloAlicaNotaCredito){
//			rdNotaCredito.setChecked(true);
//			rdAnular.setDisabled(true);
//		}
//		Hbox hbox= new Hbox();
//		hbox.setAlign("cente");
//		hbox.appendChild(rdAnular);
//		hbox.appendChild(rdNotaCredito);
//		row.appendChild(hbox);
//		rows.appendChild(row);
//
//		row=new Row();
//		row.setSpans("1,4");
//		label = new Label("MOTIVO (*) :");
//		row.appendChild(label);
//		final Textbox txtMotivoAnulacion = new Textbox();
//		txtMotivoAnulacion.setWidth("314px");
//		txtMotivoAnulacion.setMultiline(true);
//		txtMotivoAnulacion.setRows(3);
//		txtMotivoAnulacion.setMaxlength(255);
//		txtMotivoAnulacion.setStyle("font-size:11px !important");
//		row.appendChild(txtMotivoAnulacion);
//		rows.appendChild(row);
//
//
//		grid.appendChild(rows);
//		groupbox.appendChild(grid);
//		win.appendChild(groupbox);
//
//		grid = new Grid();
//		columns = new Columns();
//		column = new Column();
//		column.setAlign("center");
//		columns.appendChild(column);
//		column = new Column();
//		column.setAlign("center");
//		columns.appendChild(column);
//		grid.appendChild(columns);
//		rows = new Rows();
//		row = new Row();
//
//		Button button = new Button("Continuar", "resources/mp_anular.png");
//		button.addEventListener(Events.ON_CLICK, new EventListener<Event>() {
//			public void onEvent(Event e){
//				if (rdAnular.isChecked()==false && rdNotaCredito.isChecked()==false){
//					DlgMessage.information("Debe de seleccionar el tipo de Operación a realizar");
//					return;
//				}else if(txtMotivoAnulacion.getText().trim().isEmpty()){
//					DlgMessage.information(Messages.getString("WndLiquidacionDiariaVentas.information.noMotivoAnulacion"),txtMotivoAnulacion);
//					return;
//				}
//				Messagebox.show(Messages.getString("WndLiquidacionDiariaVentas.information.confirmarAnulacion"), DlgMessage.NOMBREAPLICACION, DlgMessage.BTN_YESNO, Messagebox.QUESTION, new EventListener<Event>() {
//					public void onEvent(Event e){
//						try{
//							if(e.getName().equals("onYes")){
//
//								anularVenta(ventaOriginal, wndAnular, txtMotivoAnulacion.getText().trim().toUpperCase());
//
//							}
//						}catch(Exception ex){
//							ex.printStackTrace();
//							DlgMessage.information(this.getClass().getSimpleName()+" "+ex.getMessage());
//						}
//					}
//				});
//			}
//		});
//		button.setHeight("28px");
//		row.appendChild(button);
//		button = new Button("Cancelar", "resources/mp_cancelarEnabled.png");
//		button.addEventListener(Events.ON_CLICK, new EventListener<Event>() {
//			public void onEvent(Event e){
//				win.onClose();
//			}
//		});
//		button.setHeight("28px");
//		button.setFocus(true);
//		row.appendChild(button);
//
//		rows.appendChild(row);
//
//		grid.appendChild(rows);
//		win.appendChild(grid);
//		return win;
//	}


//	/**
//	 * Realiza la anulacion de la venta
//	 * @param ventaPasajeId : Identificador de la Venta a anular.
//	 * @throws Exception
//	 */
//	private void anularVenta(VentaPasaje ventaOriginal,Window wndAnular, String motivo)throws Exception{
//		int result=Constantes.FAILURE;
//
//		/*Valida si es una venta del pool - 09/12/2016 - jabanto*/
//		if(ventaOriginal.getServicio()!=null && (ventaOriginal.getServicio().getId().intValue()==Constantes.ID_SERVICIO_POOL_CRUZDELSUR ||
//											    ventaOriginal.getServicio().getId().intValue()==Constantes.ID_SERVICIO_POOL_EXCLUCIVA)){
//
//			/*Valida el operador*/
//			if(ventaOriginal.getServicio().getId().intValue()==Constantes.ID_SERVICIO_POOL_CRUZDELSUR){
//				/*Realiza la anulacion del boleto en el WS de cruz de sur*/
//				WSCruzdelsur.anularBoleto(ventaOriginal.getNumeroBoletoAnterior());
//			}else if (ventaOriginal.getServicio().getId().intValue()==Constantes.ID_SERVICIO_POOL_EXCLUCIVA){
//				/*Realiza la anulacion del boleto con la API de Excluciva*/
//				RESTCiva.anularBoleto(ventaOriginal.getNumeroBoleto());
//			}
//		}
//
//		boolean forzarNotaCredito=(rdNotaCredito.isChecked()?true:false);
//
//		ventaOriginal.setTarifa(0.0);
//		ventaOriginal.setRecargo(0.0);
//		ventaOriginal.setDescuento(0.0);
//		ventaOriginal.setImportePagado(0.0);
//		ventaOriginal.setAcuenta(0.0);
//		ventaOriginal.setImportePagado(0.0);
//		ventaOriginal.setTarifaEquibalente(ventaOriginal.getTarifaEquibalente()!=null?.00:null);
//		ventaOriginal.setDescuentoEquibalente(ventaOriginal.getDescuentoEquibalente()!=null?.00:null);
//		ventaOriginal.setImportePagadoEquibalente(ventaOriginal.getImportePagadoEquibalente()!=null?.00:null);
//		ventaOriginal.setTipoMovimiento(new TipoMovimiento(Constantes.ID_TIPMOV_ANULACION));
//		ventaOriginal.setObservaciones(motivo);
//		UtilData.auditarRegistro(ventaOriginal, true, getUsuario(), Executions.getCurrent());
//		VentaPasaje notaCredito= ServiceLocator.getVentaPasajesManager().anularMovimiento(ventaOriginal,forzarNotaCredito);
//		if(notaCredito!=null){
//			WSFE.sendNota(notaCredito);
//		}
//		result=Constantes.CORRECT;
//		if(result==Constantes.CORRECT){
//			DlgMessage.information(Messages.getString("WndLiquidacionDiariaVentas.information.exitoAnulacion"));
//			wndAnular.onClose();
//			buscar();
//		}
//	}



	/**
	 * Carga usuarios, en base a las liquidaciones, segun le rango de fechas seleccionado
	 * @param combobox : Control que va contener los usuarios
	 * @throws Exception
	 */
	public void onLoadUsuarios(Combobox combobox) throws Exception{
		if(dtbxFechaInicioVenta.getValue()!=null && dtbxFechaFinVenta.getValue()!=null){
			String fechaInicial=Constantes.FORMAT_DATE.format(dtbxFechaInicioVenta.getValue());
			String fechaFinal=Constantes.FORMAT_DATE.format(dtbxFechaFinVenta.getValue());

			UtilData.cargarUsuariosLiquidacion(combobox, fechaInicial, fechaFinal, true, null);
		}else{
			cmbUsuario.getItems().clear();
			UtilData.cargarGenericData(cmbUsuario, true);
		}
	}

	/**
	 * Cargar los tipos de movimiento para la consulta
	 * @param combobox: control que va contiener los tipos de movimientos.
	 */
	private void onLoadMovimientos(Combobox combobox){
		Comboitem comboitem_0= new Comboitem();
		Comboitem comboitem_venta= new Comboitem();
		Comboitem comboitem_reserva= new Comboitem();
		Comboitem comboitem_fechaAbi= new Comboitem();

		comboitem_0.setLabel("TODOS");
		comboitem_venta.setLabel("VENTA");
		comboitem_reserva.setLabel("RESERVA");
		comboitem_fechaAbi.setLabel("FECHA ABIRTA");

		comboitem_0.setValue(0);
		comboitem_venta.setValue(Constantes.TIPO_OPERACION_VENTA);
		comboitem_reserva.setValue(Constantes.TIPO_OPERACION_RESERVA);
		comboitem_fechaAbi.setValue(String.valueOf(Constantes.ID_TIPMOV_FECHA_ABIERTA));

		combobox.appendChild(comboitem_0);
		combobox.appendChild(comboitem_venta);
		combobox.appendChild(comboitem_reserva);
		combobox.appendChild(comboitem_fechaAbi);
		combobox.setSelectedIndex(0);
	}

	public void buscarPasajero() throws Exception{
		final WndBuscarPasajero oWndBuscarPasajero = new WndBuscarPasajero();
		wndEstadoVyR.appendChild(oWndBuscarPasajero);
		oWndBuscarPasajero.oThisWindow.setTitle("Búsqueda de Pasajeros");
		oWndBuscarPasajero.setMode(MODAL);
		oWndBuscarPasajero.onCreate();
		oWndBuscarPasajero.addEventListener(Events.ON_SELECT, new EventListener<Event>() {
			@Override
			public void onEvent(Event e){
				if(oWndBuscarPasajero.rdPorDocumento.isChecked()){
					rdByDocumento.setChecked(true);
					txtBusquedaPasajero.setText(oWndBuscarPasajero.getPasajero().getNumeroDocumento());
				}else{
					rdByNombres.setChecked(true);
					txtBusquedaPasajero.setText(oWndBuscarPasajero.getPasajero().getNombresApellidos());
				}

			}
		});
	}

	public void buscarCliente() throws Exception{
		final WndBuscarPasajero oWndBuscarPasajero = new WndBuscarPasajero();
		wndEstadoVyR.appendChild(oWndBuscarPasajero);
		oWndBuscarPasajero.oThisWindow.setTitle("Búsqueda de Clientes");
		oWndBuscarPasajero.oThisWindow.setWidth("420px");
		oWndBuscarPasajero.setMode(MODAL);
		oWndBuscarPasajero.onCreate();
		oWndBuscarPasajero.buscaPax=false;
		oWndBuscarPasajero.rdPorNombres.setLabel("Razón Social");
		oWndBuscarPasajero.rowApePat.setVisible(false);
		oWndBuscarPasajero.rowApeMat.setVisible(false);
		oWndBuscarPasajero.addEventListener(Events.ON_SELECT, new EventListener<Event>() {
			@Override
			public void onEvent(Event e){
				if(oWndBuscarPasajero.rdPorDocumento.isChecked()){
					rdByRuc.setChecked(true);
					txtBusquedaCliente.setText(oWndBuscarPasajero.getCliente().getNumeroDocumento());
				}else{
					rdByRazonSocial.setChecked(true);
					txtBusquedaCliente.setText(oWndBuscarPasajero.getCliente().getRazonSocial());
				}

			}
		});
	}

}
