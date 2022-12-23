/**
 * Proyecto		: SISVYR
 * Sistema		: Sistema de Ventas y Reservas
 * Descripción	:
 * Autor		: José Abanto
 * Fecha		: 14/10/2013
 */
package com.cystesoft.vyrbus.view.ctrl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.TreeMap;

import javax.servlet.http.HttpSession;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Session;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zul.Button;
import org.zkoss.zul.Caption;
import org.zkoss.zul.Checkbox;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Comboitem;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.Grid;
import org.zkoss.zul.Hlayout;
import org.zkoss.zul.Iframe;
import org.zkoss.zul.Image;
import org.zkoss.zul.Label;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;
//import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Radio;
import org.zkoss.zul.Radiogroup;
import org.zkoss.zul.Row;
import org.zkoss.zul.Rows;
import org.zkoss.zul.Separator;
import org.zkoss.zul.Tab;
import org.zkoss.zul.Tabpanel;
import org.zkoss.zul.Window;

import com.cystesoft.vyrbus.model.bean.Agencia;
import com.cystesoft.vyrbus.model.bean.CentroCosto;
import com.cystesoft.vyrbus.model.bean.Cliente;
import com.cystesoft.vyrbus.model.bean.Concesionario;
import com.cystesoft.vyrbus.model.bean.ItinerarioAgenciaPartida;
import com.cystesoft.vyrbus.model.bean.ItinerarioAgenciaPartidaID;
import com.cystesoft.vyrbus.model.bean.Nacionalidad;
import com.cystesoft.vyrbus.model.bean.Pasajero;
import com.cystesoft.vyrbus.model.bean.Rol;
import com.cystesoft.vyrbus.model.bean.Ruta;
import com.cystesoft.vyrbus.model.bean.Servicio;
import com.cystesoft.vyrbus.model.bean.TerminosVenta;
import com.cystesoft.vyrbus.model.bean.TipoAgencia;
import com.cystesoft.vyrbus.model.bean.TipoDocumento;
import com.cystesoft.vyrbus.model.bean.TipoMoneda;
import com.cystesoft.vyrbus.model.bean.Usuario;
import com.cystesoft.vyrbus.model.bean.VentaPasaje;
import com.cystesoft.vyrbus.service.locator.ServiceLocator;
import com.cystesoft.vyrbus.service.util.Constantes;
import com.cystesoft.vyrbus.service.util.Messages;
import com.cystesoft.vyrbus.service.util.Util;
import com.cystesoft.vyrbus.service.util.UtilData;
import com.cystesoft.vyrbus.service.util.WSFE;
import com.cystesoft.vyrbus.view.ui.DlgMessage;
import com.cystesoft.vyrbus.view.ui.WndBase;
import com.cystesoft.vyrbus.view.ui.WndIFrame;

/**
 * @author JABANTO
 *
 */
public class WndDetalleLiquidacionAgencia extends WndBase {
	private static final long serialVersionUID = 1L;

	private Combobox cmbAgencia;
	private Combobox cmbCounter;
	private Datebox dtbxFechaInicio;
	private Datebox dtbxFechaFin;
	private Listbox lstbxVentas;
	private Button btnPreliminar;
	private Tab tabPrevio;
	private Tabpanel tabPanelPrevio;
	private Tab tabListaVouchers;
	private Combobox cmbTipoMoneda;
	private Combobox cmbEstado;
	private Label lblEstado;
	private Combobox cmbCentroCosto;
	private Listbox lstbxVentasReimpresion;
	private Tab tabReimpresionTicket;
	private Window wndDetLiqAg;
	private Checkbox ckbxByFechaReimpresion;
	private Datebox dtbxFechaInicioByEmision;
	private Datebox dtbxFechaFinByEmision;
	private Button btnBuscarByFechaEmision;
	private Checkbox ckbxEmisonNotacredito;
	private Radio rdSoloNotaCredito;
	private Radio rdSoloNotaCreditoNuevoComprobante;
	private Button btnAplicarNotaCredito;

	private Iframe iFrame=new Iframe();
	List<VentaPasaje>listVentas=new ArrayList<>();

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.view.ui.WndBase#onCreate()
	 */
	@Override
	public void onCreate() throws Exception {
		// TODO Auto-generated method stub
		dtbxFechaInicio.setValue(new Date());
		dtbxFechaFin.setValue(new Date());
//		tabReimpresionTicket.setVisible(false);
		if(getAgencia().getTipoAgencia().getId().intValue()!=Constantes.ID_TIPAGE_TEPSA)
			tabReimpresionTicket.setVisible(false);
		/*carga todas la agencias de viaje y corporativas*/
		UtilData.cargarGenericData(cmbAgencia, false);
		List<Agencia>lstAgencias=ServiceLocator.getAgenciaManager().buscarAgenciasByDetalleCorporativo();
		for(Agencia agencia:lstAgencias){
			Comboitem comboitem=new Comboitem();
			comboitem.setLabel(agencia.getDenominacion());
			comboitem.setValue(agencia);

			cmbAgencia.appendChild(comboitem);
		}

		//Por defecto selecciona la agencia que corresponde.
		Util.seleccionarValorItemCombo(Agencia.class, cmbAgencia, getAgencia().getId());
		if(cmbAgencia.getSelectedIndex()<0)
			cmbAgencia.setSelectedIndex(0);

		//---Controla el acceso a los controles------------------------
		cmbAgencia.setDisabled(getAgencia().getTipoAgencia().getId().intValue()!=Constantes.ID_TIPAGE_TEPSA);

		/*Carga el combo estado*/
		Comboitem comboitem=new Comboitem("TODOS");
		cmbEstado.appendChild(comboitem);
		comboitem=new Comboitem("PAGADO");
		comboitem.setValue("PAG");
		cmbEstado.appendChild(comboitem);
		comboitem=new Comboitem("PENDIENTE");
		comboitem.setValue("ACT");
		cmbEstado.appendChild(comboitem);
		cmbEstado.setSelectedIndex(0);

		List<Component>lstControles=new ArrayList<>();
		lstControles.add(lblEstado);
		lstControles.add(cmbEstado);

		List<Rol>lstRoles=new ArrayList<>();
		lstRoles.add(new Rol(Constantes.ID_ROL_SUPER_USUARIO));
		lstRoles.add(new Rol(Constantes.ID_ROL_ADMIN));
//		lstRoles.add(new Rol(Constantes.ID_ROL_FINANZAS));
		accesoControlsByRol(lstControles, lstRoles, true);

		if(!(cmbEstado.isVisible())){
			cmbAgencia.setWidth("265px");
		}

		//--------------------------------------------------------------

		//Carga las counters--------------------------------------------------------------
		onLoadCounters();
		onLoadCentroCosto();
		//--------------------------------------------------------------

		dtbxFechaInicio.addEventListener(Events.ON_CHANGE,new EventListener<Event>() {
			@Override
			public void onEvent(Event event) throws Exception {
				// TODO Auto-generated method stub
				onLoadCounters();
				onLoadCentroCosto();
			}
		});

		dtbxFechaFin.addEventListener(Events.ON_CHANGE, new EventListener<Event>() {
			@Override
			public void onEvent(Event event) throws Exception {
				// TODO Auto-generated method stub
				onLoadCounters();
				onLoadCentroCosto();
			}
		});

		cmbAgencia.addEventListener(Events.ON_SELECT, new EventListener<Event>() {
			@Override
			public void onEvent(Event event) throws Exception {
				// TODO Auto-generated method stub
				onLoadCounters();
				onLoadCentroCosto();
				Util.limpiarListbox(lstbxVentas);
				btnPreliminar.setDisabled(true);
				iFrame.detach();
			}
		});


		cmbCentroCosto.addEventListener(Events.ON_SELECT, new EventListener<Event>() {
			@Override
			public void onEvent(Event event) throws Exception {
				try {

					onSelectCentroCosto();

				} catch (Exception e) {
					e.printStackTrace();
					DlgMessage.error(e.getMessage());
				}
			}
		});

		ckbxByFechaReimpresion.addEventListener(Events.ON_CHECK, new EventListener<Event>() {
			@Override
			public void onEvent(Event event) throws Exception {
				Util.limpiarListbox(lstbxVentasReimpresion);
				dtbxFechaInicioByEmision.setValue(null);
				dtbxFechaFinByEmision.setValue(null);

				dtbxFechaInicioByEmision.setDisabled(!ckbxByFechaReimpresion.isChecked());
				dtbxFechaFinByEmision.setDisabled(!ckbxByFechaReimpresion.isChecked());
				btnBuscarByFechaEmision.setDisabled(!ckbxByFechaReimpresion.isChecked());
				if(ckbxByFechaReimpresion.isChecked()){
					dtbxFechaInicioByEmision.setValue(new Date());
					dtbxFechaFinByEmision.setValue(new Date());
				}

			}
		});

		ckbxEmisonNotacredito.addEventListener(Events.ON_CHECK, new EventListener<Event>() {
			@Override
			public void onEvent(Event event) throws Exception {
				rdSoloNotaCredito.setDisabled(!ckbxEmisonNotacredito.isChecked());
				rdSoloNotaCreditoNuevoComprobante.setDisabled(!ckbxEmisonNotacredito.isChecked());
				btnAplicarNotaCredito.setDisabled(!ckbxEmisonNotacredito.isChecked());
			}
		});
	}

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.view.ui.WndBase#initComponents()
	 */
	@Override
	public void initComponents() {
		// TODO Auto-generated method stub
		cmbAgencia=(Combobox)this.getFellow("cmbAgencia");
		cmbCounter=(Combobox)this.getFellow("cmbCounter");
		dtbxFechaInicio=(Datebox)this.getFellow("dtbxFechaInicio");
		dtbxFechaFin=(Datebox)this.getFellow("dtbxFechaFin");
		lstbxVentas=(Listbox)this.getFellow("lstbxVentas");
		btnPreliminar=(Button)this.getFellow("btnPreliminar");
		tabPrevio=(Tab)this.getFellow("tabPrevio");
		tabListaVouchers=(Tab)this.getFellow("tabListaVouchers");
		tabPanelPrevio=(Tabpanel)this.getFellow("tabPanelPrevio");
		cmbTipoMoneda=(Combobox)this.getFellow("cmbTipoMoneda");
		cmbEstado=(Combobox)this.getFellow("cmbEstado");
		lblEstado=(Label)this.getFellow("lblEstado");
		cmbCentroCosto=(Combobox)this.getFellow("cmbCentroCosto");
		wndDetLiqAg=(Window)this.getFellow("wndDetLiqAg");
		lstbxVentasReimpresion=(Listbox)this.getFellow("lstbxVentasReimpresion");
		tabReimpresionTicket=(Tab)this.getFellow("tabReimpresionTicket");
		ckbxByFechaReimpresion=(Checkbox)this.getFellow("ckbxByFechaReimpresion");
		dtbxFechaInicioByEmision=(Datebox)this.getFellow("dtbxFechaInicioByEmision");
		dtbxFechaFinByEmision=(Datebox)this.getFellow("dtbxFechaFinByEmision");
		btnBuscarByFechaEmision=(Button)this.getFellow("btnBuscarByFechaEmision");
		ckbxEmisonNotacredito=(Checkbox)this.getFellow("ckbxEmisonNotacredito");
		rdSoloNotaCredito=(Radio)this.getFellow("rdSoloNotaCredito");
		rdSoloNotaCreditoNuevoComprobante=(Radio)this.getFellow("rdSoloNotaCreditoNuevoComprobante");
		btnAplicarNotaCredito=(Button)this.getFellow("btnAplicarNotaCredito");
	}


	private void onSelectCentroCosto()throws Exception{
		onLoadVentasReimpresion();
	}

	private void onLoadVentasReimpresion()throws Exception{
		Util.limpiarListbox(lstbxVentasReimpresion);
		String styleActivo_11px="font-size:11px !important";
		String styleActivo_9px="font-size:9px !important";
		int x=0;
		/*Agrega los comprobantes disponibles para la reimpresion de tickets*/
		for(VentaPasaje ventaPasaje: listVentas){
			//Valida si el registro esta anulado
			Boolean isAnulado=false;
			if(ventaPasaje.getTipoMovimiento().getId().intValue()==Constantes.ID_TIPMOV_ANULACION ||
					ventaPasaje.getTipoMovimiento().getId().intValue()==Constantes.ID_TIPMOV_ANULACION_SISTEMA ||
					ventaPasaje.getTipoMovimiento().getId().intValue()==Constantes.ID_TIPMOV_DEVOLUCION){
				isAnulado=true;
			}

			Integer centroCostoId=(cmbCentroCosto.getSelectedIndex()>0?((CentroCosto)cmbCentroCosto.getSelectedItem().getValue()).getId():null);

			if(!isAnulado && //ventaPasaje.getVentaPasaje()!=null &&
					(centroCostoId==null || centroCostoId.intValue()==ventaPasaje.getCentroCosto().getId().intValue()) &&
					(ventaPasaje.getNumeroBoleto().toUpperCase().indexOf("B")>=0 || ventaPasaje.getNumeroBoleto().toUpperCase().indexOf("F")>=0)){
				x++;
				Listitem item=new Listitem();
				item.appendChild(new Listcell());
				/*ITEM*/
				Listcell cell= new Listcell(String.valueOf(x));
				cell.setStyle(styleActivo_11px);
				item.appendChild(cell);
				/*FECHA DE VENTA*/
				cell=new Listcell(Constantes.FORMAT_DATE.format(ventaPasaje.getFechaLiquidacion()));
				cell.setStyle(styleActivo_11px);
				item.appendChild(cell);
				/*N° BOLETO*/
				cell=new Listcell(ventaPasaje.getNumeroBoleto());
				cell.setStyle(styleActivo_11px);
				item.appendChild(cell);
				/*PASAJERO*/
				cell=new Listcell(ventaPasaje.getPasajero().toString());
				cell.setStyle(styleActivo_9px);
				item.appendChild(cell);
				/*BRUTO*/
				if(cmbTipoMoneda.isVisible() && cmbTipoMoneda.getSelectedIndex()>0 &&
						((TipoMoneda)cmbTipoMoneda.getSelectedItem().getValue()).getId().intValue()!=Constantes.ID_TIPMON_SOLES){
					cell=new Listcell(ventaPasaje.getTarifaEquibalente()!=null?Util.toNumberFormat(ventaPasaje.getTarifaEquibalente(),2):"0.00");
//					cell=new Listcell("129,000.00");
				}else{
					cell=new Listcell(Util.toNumberFormat(ventaPasaje.getTarifa(),2));
				}
				cell.setStyle(styleActivo_11px);
				item.appendChild(cell);
				/*DESCUENTO*/
				//Validando la moneda - jabanto 15/08/2015
				if(cmbTipoMoneda.isVisible() && cmbTipoMoneda.getSelectedIndex()>0 &&
						((TipoMoneda)cmbTipoMoneda.getSelectedItem().getValue()).getId().intValue()!=Constantes.ID_TIPMON_SOLES){
					cell=new Listcell(ventaPasaje.getDescuentoEquibalente()!=null?Util.toNumberFormat(ventaPasaje.getDescuentoEquibalente(),2):"0.00");
				}else{
					cell=new Listcell(Util.toNumberFormat(ventaPasaje.getDescuento(),2));
				}
				cell.setStyle(styleActivo_11px);
				item.appendChild(cell);
				/*NETO PAGADO*/
				//Validando la moneda - jabanto 15/08/2015
				if(cmbTipoMoneda.isVisible() && cmbTipoMoneda.getSelectedIndex()>0 &&
						((TipoMoneda)cmbTipoMoneda.getSelectedItem().getValue()).getId().intValue()!=Constantes.ID_TIPMON_SOLES){
					cell=new Listcell(ventaPasaje.getImportePagadoEquibalente()!=null?Util.toNumberFormat(ventaPasaje.getImportePagadoEquibalente(),2):"0.00");
				}else{
					cell=new Listcell(Util.toNumberFormat(ventaPasaje.getImportePagado(),2));
				}
				cell.setStyle(styleActivo_11px);
				item.appendChild(cell);
				/*CENTRO DE COSTO*/
				cell=new Listcell(ventaPasaje.getCentroCosto().getCodigo()!=null?ventaPasaje.getCentroCosto().getCodigo():"");
				cell.setStyle(styleActivo_11px);
				item.appendChild(cell);
				/*ORIGEN*/
				cell=new Listcell(ventaPasaje.getRuta().getOrigen());
				cell.setStyle(styleActivo_9px);
				item.appendChild(cell);
				/*DESTINO*/
				cell=new Listcell(ventaPasaje.getRuta().getDestino());
				cell.setStyle(styleActivo_9px);
				item.appendChild(cell);
				/*FECHA VIAJE*/
				if(ventaPasaje.getFechaPartida()==null)
					cell=new Listcell(ventaPasaje.getFechaPartida()!=null?Constantes.FORMAT_DATE.format(ventaPasaje.getFechaPartida()):"");
				else
					cell=new Listcell(ventaPasaje.getFechaPartida()!=null?Constantes.FORMAT_DATE.format(ventaPasaje.getFechaPartida()):"");
				cell.setStyle(styleActivo_11px);
				item.appendChild(cell);
//				/*USUARIO*/
//				cell=new Listcell(ventaPasaje.getUsuario().toString());//.getApellidoPaterno()+" "+ventaPasaje.getUsuario().getApellidoMaterno()+", "+ventaPasaje.getUsuario().getNombre());
//				cell.setStyle(styleActivo_9px);
//				item.appendChild(cell);
				/*ESTADO DEL BOLETO*/
				cell=new Listcell(ventaPasaje.getEstadoDocumento()!=null?ventaPasaje.getEstadoDocumento():"");
				cell.setStyle(styleActivo_9px);
				item.appendChild(cell);

				item.setValue(ventaPasaje);
				lstbxVentasReimpresion.appendChild(item);
			}
		}
	}

	/**
	 * Carga los centros de costo del cliente
	 * @throws Exception
	 */
	private void onLoadCentroCosto() throws Exception{
		Util.limpiarCombobox(cmbCentroCosto);
		cmbCentroCosto.setText("");
		Comboitem cmbitem = null;
		cmbitem = new Comboitem(Constantes.COMBO_LABEL_TODOS);
		cmbCentroCosto.appendChild(cmbitem);
		if(cmbAgencia.getSelectedIndex()>0){
			Agencia agencia= ServiceLocator.getAgenciaManager().buscarPorId(((Agencia)cmbAgencia.getSelectedItem().getValue()).getId().longValue());
			if(agencia.getConcesionario()!=null){
				TreeMap<String, Object>criteriosBusqueda= new TreeMap<>();
				criteriosBusqueda.put("concesionario", agencia.getConcesionario());
				criteriosBusqueda.put("estadoRegistro", Constantes.VALUE_ACTIVO);
				List<String>criteriosOrdenar= new ArrayList<>();
				criteriosOrdenar.add("denominacion");
				List<CentroCosto>  result = ServiceLocator.getCentroCostoManager().buscarPorX(criteriosBusqueda, criteriosOrdenar);

				for(CentroCosto centroCosto: result){
					cmbitem= new Comboitem(centroCosto.getDenominacion());
					cmbitem.setValue(centroCosto);
					cmbCentroCosto.appendChild(cmbitem);
				}
			}
		}

		cmbCentroCosto.setSelectedIndex(0);
	}




	/**
	 * Carga los usuarios
	 */
	public void onLoadCounters(){
		try{
			cmbCounter.getItems().clear();
			cmbCounter.setText("");
			Comboitem cmbitem = null;

			if(cmbAgencia.getSelectedIndex()>0 && cmbAgencia.getSelectedItem().getValue() instanceof Agencia){
//				Agencia agencia = (Agencia)cmbAgencia.getSelectedItem().getValue();
				Agencia agencia=ServiceLocator.getAgenciaManager().buscarPorId(((Agencia)cmbAgencia.getSelectedItem().getValue()).getId().longValue());
				List<Usuario> lstUsuarios = ServiceLocator.getVentaPasajesManager().buscarUsuarioPorAgencia(null,
						Constantes.VALUE_ACTIVO, Util.DatetoString(dtbxFechaInicio.getValue(), Constantes.DATE_FORMAT),
						Util.DatetoString(dtbxFechaFin.getValue(), Constantes.DATE_FORMAT),agencia.getConcesionario().getRuc());
				if(lstUsuarios.size()>0){
					cmbitem = new Comboitem(Constantes.COMBO_LABEL_TODOS);
					cmbCounter.appendChild(cmbitem);
					cmbCounter.setSelectedItem(cmbitem);
					for(Usuario usuario : lstUsuarios){
						cmbitem = new Comboitem();
						cmbitem.setLabel(usuario.toString());
						cmbitem.setValue(usuario);
						cmbCounter.appendChild(cmbitem);
					}
					cmbCounter.setDisabled(false);

				}else
					cmbCounter.setDisabled(true);
			}else
				cmbCounter.setDisabled(true);


			/*tipo de moneda - jabanto 15/08/2015*/
			Util.limpiarCombobox(cmbTipoMoneda);
			cmbTipoMoneda.setVisible(false);
			cmbCounter.setWidth("265px");
			if(cmbAgencia.getSelectedIndex()>0 && cmbAgencia.getSelectedItem().getValue() instanceof Agencia){
				Agencia agencia = (Agencia)cmbAgencia.getSelectedItem().getValue();
				if(agencia.getNacionalidad()!=null){
					Nacionalidad nacionalidad=ServiceLocator.getNacionalidadManager().buscarPorId(agencia.getNacionalidad().getId().longValue());
					if(nacionalidad.getTipoMoneda()!=null){
						TipoMoneda tipoMoneda=nacionalidad.getTipoMoneda();
						Comboitem comboitem=new Comboitem("NUEVOS SOLES");
						comboitem.setValue(new TipoMoneda(Constantes.ID_TIPMON_SOLES));
						cmbTipoMoneda.appendChild(comboitem);
						comboitem=new Comboitem(tipoMoneda.getDenominacion());
						comboitem.setValue(tipoMoneda);
						cmbTipoMoneda.appendChild(comboitem);

						cmbTipoMoneda.setVisible(true);
						cmbCounter.setWidth("140px");

						if(getAgencia().getTipoAgencia().getId().intValue()==Constantes.ID_TIPAGE_TEPSA){
							Util.seleccionarValorItemCombo(TipoMoneda.class, cmbTipoMoneda, Constantes.ID_TIPMON_SOLES);
//							cmbTipoMoneda.setDisabled(false);
						}else{
							Util.seleccionarValorItemCombo(TipoMoneda.class, cmbTipoMoneda, tipoMoneda.getId());
//							cmbTipoMoneda.setDisabled(true);
						}

					}
				}
			}


		}catch(Exception ex){
			ex.printStackTrace();
		}
	}


	public void cargarListaVentas() throws Exception{
//		tabReimpresionTicket.setVisible(false);
		Util.limpiarListbox(lstbxVentas);
		iFrame.detach();
		tabListaVouchers.setSelected(true);

		if(cmbAgencia.getSelectedIndex()>0){
			String fechaInicial=Constantes.FORMAT_DATE.format(dtbxFechaInicio.getValue());
			String fechaFinal=Constantes.FORMAT_DATE.format(dtbxFechaFin.getValue());
			Agencia agencia=ServiceLocator.getAgenciaManager().buscarPorId(((Agencia)cmbAgencia.getSelectedItem().getValue()).getId().longValue());
			String estadoBoletos=(String) (cmbEstado.getSelectedIndex()>0?cmbEstado.getSelectedItem().getValue():null);


			if(agencia.getConcesionario()!=null){
//				Concesionario concesionario=ServiceLocator.getConcesionarioManager().buscarPorId(agencia.getConcesionario().getId().longValue());
//				String rucCliente=concesionario.getRuc();
				Long idUsuario =null;
				if(cmbCounter.getSelectedIndex()>0)
					idUsuario=((Usuario)cmbCounter.getSelectedItem().getValue()).getId().longValue();

				boolean isSoles=true;
				if(cmbTipoMoneda.isVisible() && cmbTipoMoneda.getSelectedIndex()>=0 && ((TipoMoneda)cmbTipoMoneda.getSelectedItem().getValue()).getId().intValue()!=Constantes.ID_TIPMON_SOLES)
					isSoles=false;
				Integer centroCostoId=null;
				if(cmbCentroCosto.getSelectedIndex()>0)
					centroCostoId=(((CentroCosto)cmbCentroCosto.getSelectedItem().getValue()).getId());

				String orderByX="voucher";
				listVentas=ServiceLocator.getVentaPasajesManager().buscarDetalleVentasAgencia(fechaInicial, fechaFinal, agencia.getConcesionario().getRuc(), idUsuario,orderByX,true,isSoles,estadoBoletos,centroCostoId,false);

				if(listVentas.size()==0){
					DlgMessage.information(Messages.getString("Generales.information.noDatosEncontrados"));
				}else{
					Listitem item=null;
					Listcell cell=null;
					int x=0;

					String styleAnulado_11px="font-size:11px !important; color:red";
					String styleActivo_11px="font-size:11px !important";
					String styleAnulado_9px="font-size:9px !important; color:red";
					String styleActivo_9px="font-size:9px !important";

					for(VentaPasaje ventaPasaje: listVentas){
						x++;
						item=new Listitem();

						//Valida si el registro esta anulado
						boolean isAnulado=false;
						if(ventaPasaje.getTipoMovimiento().getId().intValue()==Constantes.ID_TIPMOV_ANULACION ||
								ventaPasaje.getTipoMovimiento().getId().intValue()==Constantes.ID_TIPMOV_ANULACION_SISTEMA ||
								ventaPasaje.getTipoMovimiento().getId().intValue()==Constantes.ID_TIPMOV_DEVOLUCION){
							isAnulado=true;
						}

						/*ITEM*/
						cell= new Listcell(String.valueOf(x));
						cell.setStyle(isAnulado?styleAnulado_11px:styleActivo_11px);
						item.appendChild(cell);
						/*FECHA DE VENTA*/
						cell=new Listcell(Constantes.FORMAT_DATE.format(ventaPasaje.getFechaLiquidacion()));
						cell.setStyle(isAnulado?styleAnulado_11px:styleActivo_11px);
						item.appendChild(cell);
						/*TIPO*/
						cell=new Listcell(ventaPasaje.getTipoMovimiento().getDenominacion());
						cell.setStyle(isAnulado?styleAnulado_9px:styleActivo_9px);
						item.appendChild(cell);
						/*N° CONTROL*/
						cell=new Listcell(ventaPasaje.getNumeroControl());
						cell.setStyle(isAnulado?styleAnulado_11px:styleActivo_11px);
						item.appendChild(cell);
						/*N° VOUCHER*/
						cell=new Listcell(ventaPasaje.getNumeroBoletoAnterior());
						cell.setStyle(isAnulado?styleAnulado_11px:styleActivo_11px);
						item.appendChild(cell);
						/*N° BOLETO*/
						cell=new Listcell(ventaPasaje.getNumeroBoleto());
						cell.setStyle(isAnulado?styleAnulado_11px:styleActivo_11px);
						item.appendChild(cell);
						/*PASAJERO*/
						cell=new Listcell(ventaPasaje.getPasajero().toString());
						cell.setStyle(isAnulado?styleAnulado_9px:styleActivo_9px);
						item.appendChild(cell);
						/*TIPO PAGO*/
						cell=new Listcell(ventaPasaje.getFormaPago().getDenominacion());
						cell.setStyle(isAnulado?styleAnulado_9px:styleActivo_9px);
						item.appendChild(cell);
						/*BRUTO*/
						//Validando la moneda - jabanto 15/08/2015
						if(cmbTipoMoneda.isVisible() && cmbTipoMoneda.getSelectedIndex()>0 &&
								((TipoMoneda)cmbTipoMoneda.getSelectedItem().getValue()).getId().intValue()!=Constantes.ID_TIPMON_SOLES){
							cell=new Listcell(ventaPasaje.getTarifaEquibalente()!=null?Util.toNumberFormat(ventaPasaje.getTarifaEquibalente(),2):"0.00");
//							cell=new Listcell("129,000.00");
						}else{
							cell=new Listcell(Util.toNumberFormat(ventaPasaje.getTarifa(),2));
						}
						cell.setStyle(isAnulado?styleAnulado_11px:styleActivo_11px);
						item.appendChild(cell);
						/*DESCUENTO*/
						//Validando la moneda - jabanto 15/08/2015
						if(cmbTipoMoneda.isVisible() && cmbTipoMoneda.getSelectedIndex()>0 &&
								((TipoMoneda)cmbTipoMoneda.getSelectedItem().getValue()).getId().intValue()!=Constantes.ID_TIPMON_SOLES){
							cell=new Listcell(ventaPasaje.getDescuentoEquibalente()!=null?Util.toNumberFormat(ventaPasaje.getDescuentoEquibalente(),2):"0.00");
						}else{
							cell=new Listcell(Util.toNumberFormat(ventaPasaje.getDescuento(),2));
						}
						cell.setStyle(isAnulado?styleAnulado_11px:styleActivo_11px);
						item.appendChild(cell);
						/*NETO PAGADO*/
						//Validando la moneda - jabanto 15/08/2015
						if(cmbTipoMoneda.isVisible() && cmbTipoMoneda.getSelectedIndex()>0 &&
								((TipoMoneda)cmbTipoMoneda.getSelectedItem().getValue()).getId().intValue()!=Constantes.ID_TIPMON_SOLES){
							cell=new Listcell(ventaPasaje.getImportePagadoEquibalente()!=null?Util.toNumberFormat(ventaPasaje.getImportePagadoEquibalente(),2):"0.00");
						}else{
							cell=new Listcell(Util.toNumberFormat(ventaPasaje.getImportePagado(),2));
						}
						cell.setStyle(isAnulado?styleAnulado_11px:styleActivo_11px);
						item.appendChild(cell);
						/*EXPORTAR*/
						Image imgExport= new Image();
						imgExport.setSrc("/resources/mp_pdf.png");
						imgExport.setTooltiptext("Exportar a PDF.");
						cell=new Listcell();cell.appendChild(imgExport);
						if(ventaPasaje.getTipoMovimiento().getId().intValue()==Constantes.ID_TIPMOV_ANULACION ||
								ventaPasaje.getTipoMovimiento().getId().intValue()==Constantes.ID_TIPMOV_ANULACION_SISTEMA ||
									ventaPasaje.getTipoMovimiento().getId().intValue()==Constantes.ID_TIPMOV_DEVOLUCION	)
							imgExport.setVisible(false);
						else
							imgExport.setVisible(true);
						item.appendChild(cell);

						imgExport.addEventListener(Events.ON_CLICK,new EventListener<Event>() {
							@Override
							public void onEvent(Event event) throws Exception {
								// TODO Auto-generated method stub
								if(lstbxVentas.getSelectedIndex()>=0){
									VentaPasaje ventaPasaje=ServiceLocator.getVentaPasajesManager().buscarPorId(((VentaPasaje)lstbxVentas.getSelectedItem().getValue()).getVentaOriginal());
									TipoDocumento tipoDocumento= new TipoDocumento();
									tipoDocumento.setDenominacion(ventaPasaje.getPasajero().getTipoDocumento().getDenominacion());
									Pasajero pasajero=new Pasajero();
									pasajero.setNumeroDocumento(ventaPasaje.getPasajero().getNumeroDocumento());
									pasajero.setApellidoPaterno(ventaPasaje.getPasajero().getApellidoPaterno());
									pasajero.setApellidoMaterno(ventaPasaje.getPasajero().getApellidoMaterno());
									pasajero.setNombre(ventaPasaje.getPasajero().getNombre());
									pasajero.setNombresApellidos(ventaPasaje.getPasajero().getNombresApellidos());
									pasajero.setTipoDocumento(tipoDocumento);
									ventaPasaje.setPasajero(pasajero);
									Ruta ruta=new Ruta();
									ruta.setOrigen(ventaPasaje.getRuta().getOrigen());
									ruta.setDestino(ventaPasaje.getRuta().getDestino());
									ventaPasaje.setRuta(ruta);
									Servicio servicio= new Servicio();
									servicio.setDenominacion(ventaPasaje.getServicio().getDenominacion());
									ventaPasaje.setServicio(servicio);
									Usuario usuario=new Usuario();
									usuario.setLogin(ventaPasaje.getUsuario().getLogin());
									ventaPasaje.setUsuario(usuario);
									Agencia agencia= new Agencia();
									agencia.setId(ventaPasaje.getAgenciaPartida().getId());
									agencia.setDenominacion(ventaPasaje.getAgenciaPartida().getDenominacion());
									ventaPasaje.setAgenciaPartida(agencia);

									if(ventaPasaje.getCentroCosto()!=null){
										CentroCosto centroCosto=ServiceLocator.getCentroCostoManager().buscarPorId(ventaPasaje.getCentroCosto().getId().longValue());
										ventaPasaje.setCentroCosto(centroCosto);
									}

									TreeMap<String, Object> criteriosBusqueda = new TreeMap<>();
									ItinerarioAgenciaPartidaID itinerarioAgenciaPartidaID = new ItinerarioAgenciaPartidaID();
									itinerarioAgenciaPartidaID.setIdItinerario(ventaPasaje.getItinerario().getId());
									itinerarioAgenciaPartidaID.setIdAgencia(ventaPasaje.getAgenciaPartida().getId());
									criteriosBusqueda.put("itinerarioAgenciaPartidaID", itinerarioAgenciaPartidaID);
									List<ItinerarioAgenciaPartida> lstItinerarioAgenciaPartida = ServiceLocator.getItinerarioAgenciaPartidaManager().buscarPorX(criteriosBusqueda, null);
									if(lstItinerarioAgenciaPartida.size()==0)
										ventaPasaje.setHoraEmbarque(ventaPasaje.getHoraPartida());
									else
										ventaPasaje.setHoraEmbarque(lstItinerarioAgenciaPartida.get(0).getHoraPartida());

									if(ventaPasaje.getTipoMoneda()!=null){
										TipoMoneda tipoMoneda=ServiceLocator.getTipoMonedaManager().buscarPorId(ventaPasaje.getTipoMoneda().getId().longValue());
										ventaPasaje.setTipoMoneda(tipoMoneda);
									}

									exportTicket(ventaPasaje);
								}

							}
						});

						/*CENTRO DE COSTO*/
						cell=new Listcell(ventaPasaje.getCentroCosto().getCodigo()!=null?ventaPasaje.getCentroCosto().getCodigo():"");
						cell.setStyle(isAnulado?styleAnulado_11px:styleActivo_11px);
						item.appendChild(cell);
						/*ORIGEN*/
						cell=new Listcell(ventaPasaje.getRuta().getOrigen());
						cell.setStyle(isAnulado?styleAnulado_9px:styleActivo_9px);
						item.appendChild(cell);
						/*DESTINO*/
						cell=new Listcell(ventaPasaje.getRuta().getDestino());
						cell.setStyle(isAnulado?styleAnulado_9px:styleActivo_9px);
						item.appendChild(cell);
						/*FECHA VIAJE*/
						if(ventaPasaje.getFechaPartida()==null)
							cell=new Listcell(ventaPasaje.getFechaPartida()!=null?Constantes.FORMAT_DATE.format(ventaPasaje.getFechaPartida()):"");
						else
							cell=new Listcell(ventaPasaje.getFechaPartida()!=null?Constantes.FORMAT_DATE.format(ventaPasaje.getFechaPartida()):"");
						cell.setStyle(isAnulado?styleAnulado_11px:styleActivo_11px);
						item.appendChild(cell);
						/*USUARIO*/
						cell=new Listcell(ventaPasaje.getUsuario().toString());//.getApellidoPaterno()+" "+ventaPasaje.getUsuario().getApellidoMaterno()+", "+ventaPasaje.getUsuario().getNombre());
						cell.setStyle(isAnulado?styleAnulado_9px:styleActivo_9px);
						item.appendChild(cell);
						/*ESTADO DEL BOLETO*/
						cell=new Listcell(ventaPasaje.getEstadoDocumento()!=null?ventaPasaje.getEstadoDocumento():"");
						cell.setStyle(isAnulado?styleAnulado_9px:styleActivo_9px);
						item.appendChild(cell);

						item.setValue(ventaPasaje);
						lstbxVentas.appendChild(item);

					}

					/*Carga las ventas para la reimpresoon de tickets*/
					onLoadVentasReimpresion();
				}
			}

//			tabReimpresionTicket.setVisible(lstbxVentasReimpresion.getItemCount()>0);
			ckbxByFechaReimpresion.setChecked(false);
			dtbxFechaInicioByEmision.setDisabled(true);
			dtbxFechaFinByEmision.setDisabled(true);
			btnBuscarByFechaEmision.setDisabled(true);
			dtbxFechaInicioByEmision.setValue(null);
			dtbxFechaFinByEmision.setValue(null);
			btnPreliminar.setDisabled(lstbxVentas.getItems().size()==0);
		}else{
			DlgMessage.information(Messages.getString("WndDetalleLiquidacionAgencia.information.noAgencia"),cmbAgencia);
		}
	}

	private void exportTicket(VentaPasaje venta){
		TreeMap<String, Object> criteriosBusqueda = new TreeMap<>();
		criteriosBusqueda.put("estadoRegistro", Constantes.VALUE_ACTIVO);
		List<String> criteriosOrdenar = new ArrayList<>();
		criteriosOrdenar.add("idioma");
		criteriosOrdenar.add("orden");
		List<TerminosVenta> lstTerminos = ServiceLocator.getTerminosVentaManager().buscarPorX(criteriosBusqueda, null);

		try{
			Agencia agencia = ServiceLocator.getAgenciaManager().buscarPorId(venta.getAgencia().getId().longValue());
			Cliente cliente = (venta.getCliente()!=null?ServiceLocator.getClienteManager().buscarPorId(venta.getCliente().getId()):null);
			venta.setCliente(cliente);
			venta.setAgencia(agencia);
			List<VentaPasaje> lstVentas = new ArrayList<>();
			lstVentas.add(venta);
			Session session = getDesktop().getSession();
	        HttpSession httpSession = (HttpSession)session.getNativeSession();
	        httpSession.setAttribute("lstVentas", lstVentas);
	        httpSession.setAttribute("terminos", lstTerminos);
	        httpSession.setAttribute("concesionario", agencia.getConcesionario().getRazonSocial());
//	        if(venta.getTipoMoneda()!=null){
//	        	TipoMoneda tipoMoneda=ServiceLocator.getTipoMonedaManager().buscarPorId(venta.getTipoMoneda().getId().longValue());
//	        	httpSession.setAttribute("tipoMoneda", tipoMoneda);
//	        }


	        final WndIFrame iFrame = new WndIFrame();
	        iFrame.btnCerrar.setVisible(false);
	        iFrame.oThisWindow.setTitle("E-VOUCHER");
	        iFrame.oThisWindow.setClosable(true);
			iFrame.setSrc("evoucher.zul");
			iFrame.setWidth("810");
			iFrame.setheight("500");
			iFrame.loadiframe();
			this.appendChild(iFrame);
			iFrame.setMode("modal");
//			iFrame.onClose();

		}catch(Exception ex){
			ex.printStackTrace();
		}
    }



//	private void exportTicket(VentaPasaje venta){
//		try{
//			Agencia agencia = ServiceLocator.getAgenciaManager().buscarPorId(venta.getAgencia().getId().longValue());
//			List<VentaPasaje> lstVentas = new ArrayList<VentaPasaje>();
//			venta.setCliente(new Cliente());
//			venta.setAgencia(agencia);
//			lstVentas.add(venta);
//			Session session = getDesktop().getSession();
//	        HttpSession httpSession = (HttpSession)session.getNativeSession();
//	        httpSession.setAttribute("lstVentas", lstVentas);
//
//	        final WndIFrame iFrame = new WndIFrame();
//	        iFrame.btnCerrar.setVisible(false);
//	        iFrame.oThisWindow.setTitle("E-VOUCHER");
//	        iFrame.oThisWindow.setClosable(true);
//			iFrame.setSrc("evoucherTuEntrada.zul");
//			iFrame.setWidth("810");
//			iFrame.setheight("500");
//			iFrame.loadiframe();
//			this.appendChild(iFrame);
//			iFrame.setMode("modal");
////			iFrame.setVisible(false);
//
//		}catch(Exception ex){
//			ex.printStackTrace();
//		}
//    }

	public void rptVentasRealizadas(){
		try{
			iFrame.detach();

			String fechaInicial=Constantes.FORMAT_DATE.format(dtbxFechaInicio.getValue());
			String fechaFinal=Constantes.FORMAT_DATE.format(dtbxFechaFin.getValue());
			Agencia agencia=ServiceLocator.getAgenciaManager().buscarPorId(((Agencia)cmbAgencia.getSelectedItem().getValue()).getId().longValue());
			String estadoBoletos=(String) (cmbEstado.getSelectedIndex()>0?cmbEstado.getSelectedItem().getValue():null);

			if(agencia.getConcesionario()!=null){
				Concesionario concesionario=ServiceLocator.getConcesionarioManager().buscarPorId(agencia.getConcesionario().getId().longValue());
//				String rucCliente=concesionario.getRuc();
				Long idUsuario =null;
				if(cmbCounter.getSelectedItem().getValue() instanceof Usuario)
					idUsuario=((Usuario)cmbCounter.getSelectedItem().getValue()).getId().longValue();
				String orderByX="c_codigo,voucher, fechaVenta";

				boolean isSoles=true;
				if(cmbTipoMoneda.isVisible() && cmbTipoMoneda.getSelectedIndex()>=0 && ((TipoMoneda)cmbTipoMoneda.getSelectedItem().getValue()).getId().intValue()!=Constantes.ID_TIPMON_SOLES)
					isSoles=false;
				Integer centroCostoId=null;
				if(cmbCentroCosto.getSelectedIndex()>0)
					centroCostoId=(((CentroCosto)cmbCentroCosto.getSelectedItem().getValue()).getId());
				List<VentaPasaje>lstVentas=ServiceLocator.getVentaPasajesManager().buscarDetalleVentasAgencia(fechaInicial, fechaFinal, agencia.getConcesionario().getRuc(), idUsuario,orderByX,true,isSoles,estadoBoletos,centroCostoId,false);


				TipoAgencia tipoAgencia= new TipoAgencia();
				tipoAgencia.setId(((Agencia)cmbAgencia.getSelectedItem().getValue()).getTipoAgencia().getId());

				Integer porComision=concesionario.getComision()!=null?concesionario.getComision():0;
				Double igv=.00;

				Session session=getDesktop().getSession();
				HttpSession httpSession=(HttpSession)session.getNativeSession();
				httpSession.setAttribute("lstVentas", lstVentas);
				httpSession.setAttribute("tipoAgencia", tipoAgencia);
				httpSession.setAttribute("usuario", cmbCounter.getSelectedItem().getLabel().toUpperCase());
				httpSession.setAttribute("agencia", cmbAgencia.getSelectedItem().getLabel().toUpperCase());
				httpSession.setAttribute("fechaInicio", Constantes.FORMAT_DATE.format(dtbxFechaInicio.getValue()));
				httpSession.setAttribute("fechaFinal", Constantes.FORMAT_DATE.format(dtbxFechaFin.getValue()));
				httpSession.setAttribute("comision", porComision);
				httpSession.setAttribute("igv", igv);

				iFrame.setSrc("reporteVentasRealizadas.zul");
				iFrame.setWidth("100%");
				iFrame.setHeight("570px");
//				this.appendChild(iFrame);
				tabPanelPrevio.appendChild(iFrame);

				tabPrevio.setSelected(true);

			}
		}catch(Exception ex){
			ex.printStackTrace();
		}
	}

	public void exportarExcel(){
		Agencia agencia = (Agencia)cmbAgencia.getSelectedItem().getValue();
		if(agencia!=null){
			if(lstbxVentas.getItems().size()>0){
				final Window win = new Window("", "normal", false);
				win.setWidth("220px");
				Caption caption = new Caption("TIPO DE EXPORTACION", "resources/mp_excel.png");
				win.appendChild(caption);
				Grid grid = new Grid();
				Rows rows = new Rows();
				Row row = new Row();
				Radiogroup radiogroup = new Radiogroup();
				radiogroup.setOrient("vertical");
				final Radio rdEstandar = new Radio("Exportar archivo Estandar");
				radiogroup.appendChild(rdEstandar);
				Separator separator = new Separator("horizontal");
				radiogroup.appendChild(separator);
				final Radio rdDetallado = new Radio("Exportar archivo Detallado");
				radiogroup.appendChild(rdDetallado);
				separator = new Separator("horizontal");
				radiogroup.appendChild(separator);
				row.appendChild(radiogroup);
				rows.appendChild(row);

				row = new Row();
				row.setAlign("center");
				Hlayout hlayout = new Hlayout();
				Button button = new Button("Aceptar", "resources/mp_aceptarEnabled.png");
				button.addEventListener(Events.ON_CLICK, new EventListener<Event>() {
					@Override
					public void onEvent(Event e){
						if(lstbxVentas.getItems().size()>0){
							Cliente cliente=((VentaPasaje)lstbxVentas.getItemAtIndex(0).getValue()).getCliente();

							Session session = getDesktop().getSession();
							HttpSession httpSession = (HttpSession)session.getNativeSession();
							httpSession.setAttribute("cliente", cliente);

							Boolean rptPersonalizado=false;
							if(rdEstandar.isSelected())
								rptPersonalizado=false;
							else
								rptPersonalizado=true;

							if(rptPersonalizado)
								httpSession.setAttribute("path", Constantes.DIRECTORY_EXCEL+"DetalladoPersonalizado.xls");
							else
								httpSession.setAttribute("path", Constantes.DIRECTORY_EXCEL+"DetalladoCorporativo.xls");
							httpSession.setAttribute("rptPersonalizado", rptPersonalizado);
							httpSession.setAttribute("agencia", ((Agencia)cmbAgencia.getSelectedItem().getValue()).toString());
							httpSession.setAttribute("usuario", cmbCounter.getText().trim());
							httpSession.setAttribute("desde", Constantes.FORMAT_DATE.format(dtbxFechaInicio.getValue()));
							httpSession.setAttribute("hasta", Constantes.FORMAT_DATE.format(dtbxFechaFin.getValue()));
							httpSession.setAttribute("listbox", lstbxVentas);
							Executions.sendRedirect("/exportEstadosCuenta.htm");
							win.onClose();
						}
					}
				});
				button.setWidth("85px");
				button.setHeight("27px");
				hlayout.appendChild(button);
				button = new Button("Cancelar", "resources/mp_cancelarEnabled.png");
				button.addEventListener(Events.ON_CLICK, new EventListener<Event>() {
					@Override
					public void onEvent(Event e){
						win.onClose();
					}
				});
				button.setWidth("85px");
				button.setHeight("27px");
				hlayout.appendChild(button);
				row.appendChild(hlayout);
				rows.appendChild(row);

				grid.appendChild(rows);
				win.appendChild(grid);
				if(agencia.getTipoAgencia().getId().intValue()==Constantes.ID_TIPAGE_CORPORATIVO)
					rdDetallado.setSelected(true);
				else
					rdEstandar.setSelected(true);

				this.appendChild(win);
				win.doModal();
			}else
				DlgMessage.information(Messages.getString("Generales.information.noDatosExportar"));
		}else
			DlgMessage.information(Messages.getString("Generales.information.noSeleccionoAgencia"), cmbAgencia);
	}

	/**
	 * Realiza la rempresion masiva de los tickets
	 */
	public void reimprimirTickts(){
		try {

			List<VentaPasaje>listVentas= new ArrayList<>();
			for(Listitem item:lstbxVentasReimpresion.getSelectedItems()){
				VentaPasaje oventaPasaje=item.getValue();
				boolean isAnulado=false;
				/*Valida que no este anulado*/
				if(oventaPasaje.getTipoMovimiento().getId().intValue()==Constantes.ID_TIPMOV_ANULACION ||
						oventaPasaje.getTipoMovimiento().getId().intValue()==Constantes.ID_TIPMOV_ANULACION_SISTEMA ||
								oventaPasaje.getTipoMovimiento().getId().intValue()==Constantes.ID_TIPMOV_DEVOLUCION){
					isAnulado = true;
				}

				/*Solo los que no esten anulados - 10/01/2017 - jabanto*/
				if(!isAnulado){
					if(ckbxByFechaReimpresion.isChecked()){
						VentaPasaje ventaPasaje=ServiceLocator.getVentaPasajesManager().buscarVentaById(oventaPasaje.getId());
						listVentas.add(ventaPasaje);
					}else{
						if(oventaPasaje.getVentaPasaje()!=null){
							VentaPasaje ventaPasaje=ServiceLocator.getVentaPasajesManager().buscarVentaById(oventaPasaje.getVentaPasaje().getId());
							listVentas.add(ventaPasaje);
						}
					}
				}
				/*End begin 10/01/2017 - jabanto*/
//				if(isAnulado==false && oventaPasaje.getVentaPasaje()!=null){
//					VentaPasaje ventaPasaje=ServiceLocator.getVentaPasajesManager().buscarVentaById(oventaPasaje.getVentaPasaje().getId());
//					listVentas.add(ventaPasaje);
//				}
			}


			WSFE.reimprimirComprobante(listVentas, wndDetLiqAg, Constantes.NUMERO_COPIAS_COMPROBANTE_PASAJES);

		} catch (Exception e) {
			e.printStackTrace();
			DlgMessage.error(e.getMessage());
		}
	}

	/**
	 * Realiza la busqueda de los comprobantes electronicos por fecha de emision
	 */
	public void buscarByFechaEmision(){
		try {
			if(cmbAgencia.getSelectedIndex()==0){
				DlgMessage.information(Messages.getString("WndDetalleLiquidacionAgencia.information.noAgencia"),cmbAgencia);
				return;
			}
			Agencia agencia=ServiceLocator.getAgenciaManager().buscarPorId(((Agencia)cmbAgencia.getSelectedItem().getValue()).getId().longValue());
			String estadoBoletos=(String) (cmbEstado.getSelectedIndex()>0?cmbEstado.getSelectedItem().getValue():null);
			Long idUsuario =null;
			if(cmbCounter.getSelectedIndex()>0)
				idUsuario=((Usuario)cmbCounter.getSelectedItem().getValue()).getId().longValue();
			boolean isSoles=true;
			if(cmbTipoMoneda.isVisible() && cmbTipoMoneda.getSelectedIndex()>=0 && ((TipoMoneda)cmbTipoMoneda.getSelectedItem().getValue()).getId().intValue()!=Constantes.ID_TIPMON_SOLES)
				isSoles=false;
			Integer centroCostoId=null;
			if(cmbCentroCosto.getSelectedIndex()>0)
				centroCostoId=(((CentroCosto)cmbCentroCosto.getSelectedItem().getValue()).getId());

			String orderByX="FechaVenta, boleto";

			String fechaInicio=Constantes.FORMAT_DATE.format(dtbxFechaInicioByEmision.getValue());
			String fechaFin=Constantes.FORMAT_DATE.format(dtbxFechaFinByEmision.getValue());
			listVentas=ServiceLocator.getVentaPasajesManager().buscarDetalleVentasAgencia(fechaInicio, fechaFin, agencia.getConcesionario().getRuc(), idUsuario,orderByX,true,isSoles,estadoBoletos,centroCostoId,true);
			onLoadVentasReimpresion();

		} catch (Exception e) {
			e.printStackTrace();
			DlgMessage.error(e.getMessage());
		}
	}


	public void emitriNotaCredito(){
		try {
//			if(rdSoloNotaCredito.isChecked()==false && rdSoloNotaCreditoNuevoComprobante.isChecked()==false){
//				DlgMessage.information("Debe de seleccionar un opción para la emsión de Notas de Crédito.");
//				return;
//			}else if (lstbxVentasReimpresion.getSelectedCount()==0){
//				DlgMessage.information("Debe de seleccionar los comprobantes a quienes se la va a aplicar Notas de Crédito.");
//				return;
//			}else if(lstbxVentasReimpresion.getSelectedCount()>30){
//				DlgMessage.information("Solo es posible la emisión masiva de Notas de Crédito de hasta 30 comprobantes seleccionados");
//				return;
//			}else{
//				Date fechaLiquidacion=(Date) Executions.getCurrent().getDesktop().getSession().getAttribute(Constantes.ATRIBUTO_FECHA_LIQUIDACION);
//				if(fechaLiquidacion==null){
//					DlgMessage.information("Debe tener una Liquidación abierta");
//					return;
//				}else{
//					Date fechaActual= Constantes.FORMAT_DATE.parse(MyTime.dateTimeServer());
//					if(fechaLiquidacion.getTime()!=fechaActual.getTime()){
//						DlgMessage.information("La fecha de su Liquidación es diferente a la Actual, Ciérrela y apertura una nueva.");
//						return;
//					}
//				}
//			}
//
//			Messagebox.show("Este proceso es irreversible y puede tardar  varios minutos. \n ¿Realment esta seguro de continuar?  ", DlgMessage.NOMBREAPLICACION, DlgMessage.BTN_YESNO, Messagebox.QUESTION, new EventListener<Event>() {
//				@Override
//				public void onEvent(Event e){
//					try {
//						if(e.getName().equals("onYes")){
//							List<VentaPasaje>listVentas= new ArrayList<>();
//							for(Listitem item:lstbxVentasReimpresion.getSelectedItems()){
//								VentaPasaje oventaPasaje=item.getValue();
//								boolean isAnulado=false;
//								/*Valida que no este anulado*/
//								if(oventaPasaje.getTipoMovimiento().getId().intValue()==Constantes.ID_TIPMOV_ANULACION ||
//										oventaPasaje.getTipoMovimiento().getId().intValue()==Constantes.ID_TIPMOV_ANULACION_SISTEMA ||
//												oventaPasaje.getTipoMovimiento().getId().intValue()==Constantes.ID_TIPMOV_DEVOLUCION){
//									isAnulado = true;
//								}
//
//								if(!(isAnulado)){
//									if(ckbxByFechaReimpresion.isChecked()){
//										VentaPasaje ventaPasaje=ServiceLocator.getVentaPasajesManager().buscarVentaById(oventaPasaje.getId());
//										listVentas.add(ventaPasaje);
//									}else{
//										if(oventaPasaje.getVentaPasaje()!=null){
//											VentaPasaje ventaPasaje=ServiceLocator.getVentaPasajesManager().buscarVentaById(oventaPasaje.getVentaPasaje().getId());
//											listVentas.add(ventaPasaje);
//										}
//									}
//								}
//							}
//
//							/*Genera las notas de credito*/
//							TipoNota tipoNota= ServiceLocator.getTipoNotaManager().buscarPorId(Long.valueOf(Constantes.ID_TIPNOTA_ANULACION));
//							VentasNotas ventasNotas= ServiceLocator.getVentaPasajesManager().generarNotaCreditoRegularizacion(listVentas, tipoNota, rdSoloNotaCreditoNuevoComprobante.isChecked());
//
//							/*Realiza el envio de las notas de credito al Servidor F.E*/
//							for(VentaPasaje nota: ventasNotas.getListNotasCredito()){
//								WSFE.sendNota(nota);
//							}
//
//							/*Luego los nuevos comprobantes, si es que se hayan generado*/
//							if(ventasNotas.getListVentas()!=null && ventasNotas.getListVentas().size()>0){
//								List<VentaPasaje>listVentasPasajes= new ArrayList<>();
//								for(VentaPasaje ventaPasaje: ventasNotas.getListVentas()){
//									VentaPasaje oventaPasaje=ServiceLocator.getVentaPasajesManager().buscarVentaById(ventaPasaje.getId());
//									listVentasPasajes.add(oventaPasaje);
//								}
//								WSFE.sendVenta(listVentasPasajes, wndDetLiqAg, true, null);
//							}
//
//							/*Refresca la busqueda*/
//							buscarByFechaEmision();
//
//							DlgMessage.information("El proceso terminó correctamente");
//						}
//					} catch (Exception e2) {
//						e2.printStackTrace();
//						DlgMessage.error(e2.getMessage());
//					}
//				}
//			});
//

		} catch (Exception e) {
			e.printStackTrace();
			DlgMessage.error(e.getMessage());
		}
	}
}
