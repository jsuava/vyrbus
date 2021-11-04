/**
 * Proyecto		: SISVYR
 * Sistema		: Sistema de Ventas y Reservas
 * Descripción	: 
 * Autor		: José Sullo Avalos
 * Fecha		: 02/01/2013
 */
package com.cystesoft.vyrbus.view.ctrl;

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TreeMap;

import javax.servlet.http.HttpSession;

import org.apache.geronimo.specs.activation.CommandMapBundleTrackerCustomizer;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Session;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zul.A;
import org.zkoss.zul.Button;
import org.zkoss.zul.Caption;
import org.zkoss.zul.Column;
import org.zkoss.zul.Columns;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Comboitem;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.Div;
import org.zkoss.zul.Doublebox;
import org.zkoss.zul.Grid;
import org.zkoss.zul.Groupbox;
import org.zkoss.zul.Hbox;
import org.zkoss.zul.Hlayout;
import org.zkoss.zul.Label;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listfoot;
import org.zkoss.zul.Listfooter;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Radio;
import org.zkoss.zul.Row;
import org.zkoss.zul.Rows;
import org.zkoss.zul.Separator;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Toolbar;
import org.zkoss.zul.Vlayout;
import org.zkoss.zul.Window;

import com.cystesoft.vyrbus.model.bean.Agencia;
import com.cystesoft.vyrbus.model.bean.OperadorTarjetaCredito;
import com.cystesoft.vyrbus.model.bean.TarjetaCredito;
import com.cystesoft.vyrbus.model.bean.TipoFormaPago;
import com.cystesoft.vyrbus.model.bean.TipoMovimiento;
import com.cystesoft.vyrbus.model.bean.TranscarUsuarioPersonal;
import com.cystesoft.vyrbus.model.bean.Usuario;
import com.cystesoft.vyrbus.model.bean.VentaPasaje;
import com.cystesoft.vyrbus.service.locator.ServiceLocator;
import com.cystesoft.vyrbus.service.util.Constantes;
import com.cystesoft.vyrbus.service.util.CreateDocument;
import com.cystesoft.vyrbus.service.util.Messages;
import com.cystesoft.vyrbus.service.util.RESTCiva;
import com.cystesoft.vyrbus.service.util.Util;
import com.cystesoft.vyrbus.service.util.UtilData;
import com.cystesoft.vyrbus.service.util.WSCruzdelsur;
import com.cystesoft.vyrbus.service.util.WSFE;
import com.cystesoft.vyrbus.view.ui.DlgMessage;
import com.cystesoft.vyrbus.view.ui.WndBase;
import com.cystesoft.vyrbus.view.ui.WndIFrame;
import com.cystesoft.vyrbus.view.ui.WndImprimir;

/**
 * @author Jose
 *
 */
public class WndLiquidacionDiariaVentas extends WndBase implements Serializable {
	private static final long serialVersionUID = 1L;
	private Combobox cmbAgencia;
	private Combobox cmbCounter;
	private Combobox cmbTipoMovimiento;
	private Datebox dtbxFechaInicio;
	private Datebox dtbxFechaFin;
	private Listbox lbxVentas;
	private Grid grdTotales;
	private Label lblDevoluciones;
	private Label lblCortesias;
	private Label lblCredito;
	private Label lblPrepagado;
	private Label lblRecibos;
	private Label lblEfectivo;
	private Label lblTarjeta;
	private Label lblNotascredito;
	private Label lblEfectivoDolares;
	private Label lblCreditoDolares;
	private Radio rubroPasajes;
	private Radio rubroCarga;
	private Radio rubroAmbos;
	
//	private Window wndLiquidacionDiariaVentas;
//	private Window wndDuplicar = null;
	private Window wndAnular = null;
	private Window wndCambioformaPago = null;
	private Button btnExportar;

//	private VentaPasaje duplicadoBoleto;
	private Usuario usuario = null;
	private Agencia agencia = null;
	
	private Doublebox dblbxTotal;
	private Doublebox dbxTotalDolares;
	
	private Integer XI=0;//Correlativo 
	
	private Double total = 0.0;
	private Double totalDevolucion = 0.0;
	private Double totalCortesia = 0.0;
	private Double totalCredito = 0.0;
	private Double totalPrepagado = 0.0;
//	Double totalRecibos = 0.0;
	private Double totalEfectivo = 0.0;
	private Double totalTarjeta = 0.0;
	private Double totalNotasCredito=0.0;
	private Double totalEquipajePCE = 0.0;
	private Double totalDolares =0.0;
	private Double totalEfectivoDolares = 0.0;
	private Double totalCreditoDolares = 0.0;
	private Listfoot listfoot;
	private Listfooter listfooter;
	
	private static String[] tipoMovimiento = {Constantes.COMBO_LABEL_TODOS, "ANULADOS", 
											"DEVOLUCIONES", "CORTESIAS", "CREDITO", //"EQUIPAJES (PCE)",  
											"PREPAGADOS", "RECIBOS DE CAJA", "TARJETA MASTERCARD", "TARJETA VISA", "VENTAS"};
	
	
	
	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.view.ui.WndBase#onCreate()
	 */
	@Override
	public void onCreate() throws Exception {
//		super.onCreate();
		UtilData.cargarAgenciaXtipoAgencia(cmbAgencia, Constantes.ID_TIPAGE_TEPSA, true);
//		UtilData.cargarDataCombo(cmbAgencia, Agencia.class, true);
//		UtilData.cargarDataCombo(cmbTipoMovimiento, TipoMovimiento.class, true);
		dtbxFechaInicio.setValue(new Date());
		dtbxFechaFin.setValue(new Date());
		usuario = (Usuario)this.getDesktop().getSession().getAttribute(Constantes.ATRIBUTO_USUARIO);
		agencia = (Agencia)this.getDesktop().getSession().getAttribute(Constantes.ATRIBUTO_AGENCIA);
		onSelectDefaultAgencia();
		onLoadCounters();
		onLoadTipoMovimiento();
		
		//RESTRICCION PARA EL ACCESO A LA SELECCION DE AGENCIAS.
		if(getRol().getId().intValue()==Constantes.ID_ROL_SUPER_USUARIO ||
				getRol().getId().intValue()==Constantes.ID_ROL_GERENCIA_COMERCIAL ||
						getRol().getId().intValue()==Constantes.ID_ROL_FISCALIZACION ||
								getRol().getId().intValue()==Constantes.ID_ROL_ADMIN_COMERCIAL){
			cmbAgencia.setDisabled(false);
		}else
			cmbAgencia.setDisabled(true);
				
	
		
		btnExportar.addEventListener(Events.ON_CLICK,new EventListener<Event>() {
			@Override
			public void onEvent(Event event) throws Exception {
				// TODO Auto-generated method stub
				//Util.exportarExcel(lbxVentas, "Liquidación diaria de ventas");
				if(lbxVentas.getItems().size()>0){
					Session session = getDesktop().getSession();
					HttpSession httpSession = (HttpSession)session.getNativeSession();
					httpSession.setAttribute("parcialPath",Constantes.DIRECTORY_EXCEL+"LiquidacionDiariaVentas.xls");
					httpSession.setAttribute("lbxVentas", lbxVentas);
					Executions.sendRedirect("/exportXlsLiquidacionDiariaVentas.htm");
				}
			}
		});
		rubroPasajes.addEventListener(Events.ON_CHECK, new EventListener<Event>() {
			@Override
			public void onEvent(Event event) throws Exception {
				// TODO Auto-generated method stub
				onCheck_rubroPasajes();
			}
		});
		rubroCarga.addEventListener(Events.ON_CHECK, new EventListener<Event>() {
			@Override
			public void onEvent(Event event) throws Exception {
				// TODO Auto-generated method stub
				onCheck_rubroCarga();
			}
		});		
		rubroAmbos.addEventListener(Events.ON_CHECK, new EventListener<Event>() {
			@Override
			public void onEvent(Event event) throws Exception {
				// TODO Auto-generated method stub
				onCheck_rubroAmbos();
			}
		});
	}
	
	/**
	 * @return the tipoMovimiento
	 */
	public static String[] getTipoMovimiento() {
		return tipoMovimiento;
	}

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.view.ui.WndBase#initComponents()
	 */
	@Override
	public void initComponents() {
//		super.initComponents();
		cmbAgencia = (Combobox)this.getFellow("cmbAgencia");
		cmbCounter = (Combobox)this.getFellow("cmbCounter");
		cmbTipoMovimiento = (Combobox)this.getFellow("cmbTipoMovimiento");
		dtbxFechaInicio = (Datebox)this.getFellow("dtbxFechaInicio");
		dtbxFechaFin = (Datebox)this.getFellow("dtbxFechaFin");
		lbxVentas = (Listbox)this.getFellow("lbxVentas");
		grdTotales = (Grid)this.getFellow("grdTotales");
		lblDevoluciones = (Label)this.getFellow("lblDevoluciones");
		lblCortesias = (Label)this.getFellow("lblCortesias");
		lblCredito = (Label)this.getFellow("lblCredito");
		lblPrepagado = (Label)this.getFellow("lblPrepagado");
		lblRecibos = (Label)this.getFellow("lblRecibos");
		lblEfectivo = (Label)this.getFellow("lblEfectivo");
		lblTarjeta = (Label)this.getFellow("lblTarjeta");
		lblNotascredito = (Label)this.getFellow("lblNotascredito");		
		lblEfectivoDolares = (Label)this.getFellow("lblEfectivoDolares");
		lblCreditoDolares = (Label)this.getFellow("lblCreditoDolares");
//		wndLiquidacionDiariaVentas = (Window)this.getFellow("wndLiquidacionDiariaVentas");
		btnExportar=(Button)this.getFellow("btnExportar");
		rubroPasajes = (Radio)this.getFellow("rubroPasajes");
		rubroCarga = (Radio)this.getFellow("rubroCarga");
		rubroAmbos = (Radio)this.getFellow("rubroAmbos");
		
		
	}
	
	private void clearTotals()throws Exception{
		lblEfectivo.setValue("0.00");
		lblEfectivoDolares.setValue("0.00");
		lblRecibos.setValue("0.00");
		lblTarjeta.setValue("0.00");
		lblNotascredito.setValue("0.00");
		lblCortesias.setValue("0.00");
		lblDevoluciones.setValue("0.00");
		lblCredito.setValue("0.00");
		lblCreditoDolares.setValue("0.00");
		lblPrepagado.setValue("0.00");
	}
	
	private void onCheck_rubroAmbos() {
		try {			
			onCheck_rubroPasajes();
						
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private void onCheck_rubroCarga() {
		try {
			Util.limpiarListbox(lbxVentas);
			Util.limpiarCombobox(cmbAgencia);
			Util.limpiarCombobox(cmbCounter);		
			clearTotals();
			
			
			List<Agencia> result = ServiceLocator.getTranscarManager().buscarAgencias();
			UtilData.cargarGenericData(cmbAgencia, true);
			for(Agencia agencia: result) {
				Comboitem comboitem= new Comboitem();
				comboitem.setLabel(agencia.getDenominacion());
				comboitem.setValue(agencia);
				cmbAgencia.appendChild(comboitem);
				
				if(agencia.getCodigo()!=null && agencia.getCodigo().equals(getAgencia().getCodigo()))
					cmbAgencia.setSelectedItem(comboitem);
			}
			if(cmbAgencia.getSelectedIndex()<0)
				cmbAgencia.setSelectedIndex(0);
			
			
			String fechaInicio = Constantes.FORMAT_DATE.format(dtbxFechaInicio.getValue());
			String fechaFin = Constantes.FORMAT_DATE.format(dtbxFechaFin.getValue());
			List<Usuario> resultUsuarios = ServiceLocator.getTranscarManager().buscarUsuariosByVenta(null, fechaInicio, fechaFin);
			UtilData.cargarGenericData(cmbCounter, true);
			for(Usuario usuario : resultUsuarios) {
				Comboitem comboitem= new Comboitem(usuario.toString());
				comboitem.setValue(usuario);
				cmbCounter.appendChild(comboitem);
				
				if(usuario.getLogin()!=null && usuario.getLogin().equals(getUsuario().getLogin()))
					cmbCounter.setSelectedItem(comboitem);
			}
			if(cmbCounter.getSelectedIndex()<0)
				cmbCounter.setSelectedIndex(0);
			 
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private void onCheck_rubroPasajes() {
		try {
			Util.limpiarListbox(lbxVentas);
			Util.limpiarCombobox(cmbAgencia);
			Util.limpiarCombobox(cmbCounter);
			clearTotals();
			
			UtilData.cargarAgenciaXtipoAgencia(cmbAgencia, Constantes.ID_TIPAGE_TEPSA, true);
			onSelectDefaultAgencia();
			onLoadCounters();
				
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private void onSelectDefaultAgencia(){
		for(Comboitem comboitem : cmbAgencia.getItems()){
			if(comboitem.getValue()!=null && ((Agencia)comboitem.getValue()).getId().intValue()==agencia.getId().intValue())
				cmbAgencia.setSelectedItem(comboitem);
		}
	}

	public void onLoadCounters(){
		try{
			cmbCounter.getItems().clear();
			cmbCounter.setText("");
			Comboitem cmbitem = null;
			if(cmbAgencia.getSelectedItem().getValue() instanceof Agencia){
				Agencia agencia = (Agencia)cmbAgencia.getSelectedItem().getValue();
				List<Usuario> lstUsuarios = ServiceLocator.getVentaPasajesManager().buscarUsuarioPorAgencia(agencia.getId(), 
						Constantes.VALUE_ACTIVO, Util.DatetoString(dtbxFechaInicio.getValue(), Constantes.DATE_FORMAT), 
						Util.DatetoString(dtbxFechaFin.getValue(), Constantes.DATE_FORMAT),null);
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
			}
			
			Util.seleccionarValorItemCombo(Usuario.class, cmbCounter, getUsuario().getId());
			if(cmbCounter.getSelectedIndex()<0)
				cmbCounter.setSelectedIndex(0);
		}catch(Exception ex){
			ex.printStackTrace();
		}
	}
	
	public void onValidateFecha(){
		if(Util.comparaFechas(dtbxFechaInicio.getValue(), dtbxFechaFin.getValue(), Util.OPER_MAYOR)){
			cmbCounter.getItems().clear();
			cmbCounter.setText("");
			DlgMessage.information(Messages.getString("WndLiquidacionDiariaVentas.information.fechaInicioMenor"));
		}else{
			if(cmbAgencia.getSelectedItem().getValue() instanceof Agencia)
				onLoadCounters();
		}
	}
	
	public void onBuscarVentas(){
		try {
			lbxVentas.getItems().clear();
			grdTotales.setVisible(false);
			for(int j=0; j<lbxVentas.getChildren().size();j++){
				if(lbxVentas.getChildren().get(j) instanceof Listfoot)
					lbxVentas.getChildren().get(j).detach();
			}
			
			total = 0.0;
			totalDevolucion = 0.0;
			totalCortesia = 0.0;
			totalCredito = 0.0;
			totalPrepagado = 0.0;
			totalEfectivo = 0.0;
			totalTarjeta = 0.0;
			totalNotasCredito=0.0;
			totalEquipajePCE = 0.0;
			totalDolares =0.0;
			totalEfectivoDolares = 0.0;
			totalCreditoDolares = 0.0;						
			
			Integer idAgencia = null;
			Integer idUsuario = null;
			Integer criterio = 0;
			String fechaInicio = Util.DatetoString(dtbxFechaInicio.getValue(), Constantes.DATE_FORMAT);
			String fechaFin = Util.DatetoString(dtbxFechaFin.getValue(), Constantes.DATE_FORMAT);
			
			if(cmbAgencia.getSelectedItem().getValue() instanceof Agencia)
				idAgencia = ((Agencia)cmbAgencia.getSelectedItem().getValue()).getId();
			if(cmbCounter.getItemCount()>0 && cmbCounter.getSelectedItem().getValue() instanceof Usuario)
				idUsuario = ((Usuario)cmbCounter.getSelectedItem().getValue()).getId();
			if(cmbTipoMovimiento.getSelectedIndex()>=0)
				criterio = cmbTipoMovimiento.getSelectedIndex();
			
			if(rubroAmbos.isChecked()) {
				//Pasajes
				List<VentaPasaje> lstVentas = ServiceLocator.getVentaPasajesManager().buscarDetalladoVentas(idAgencia, idUsuario, fechaInicio, fechaFin, criterio);
				loadVentas(lstVentas, false);
				
				//Carga
				Integer _idAgencia = null;
				Integer _idUsuario = null;
				if(cmbAgencia.getSelectedItem().getValue() instanceof Agencia) {
					Agencia _agencia = cmbAgencia.getSelectedItem().getValue();
					_idAgencia = ServiceLocator.getTranscarManager().buscarIdAgenciaByCodigoAgenciaPasajes(_agencia.getCodigo());
				}
				if(cmbCounter.getSelectedItem().getValue() instanceof Usuario) {
					Usuario _usuario = cmbCounter.getSelectedItem().getValue();
					TranscarUsuarioPersonal usuarioPersonal= ServiceLocator.getTranscarManager().buscarUsuarioPersonal(_usuario.getLogin());
					if(usuarioPersonal!=null)
						_idUsuario = usuarioPersonal.getId();
				}				
				lstVentas  =ServiceLocator.getTranscarManager().buscarDetalleVentas(_idUsuario, _idAgencia, fechaInicio, fechaFin);
				loadVentas(lstVentas, true);
			}else if(rubroPasajes.isChecked()) {				
				List<VentaPasaje> lstVentas = ServiceLocator.getVentaPasajesManager().buscarDetalladoVentas(idAgencia, idUsuario, fechaInicio, fechaFin, criterio);
				loadVentas(lstVentas, false);
			}else if(rubroCarga.isChecked()) {				
				List<VentaPasaje> lstVentas  =ServiceLocator.getTranscarManager().buscarDetalleVentas(idUsuario, idAgencia, fechaInicio, fechaFin);
				loadVentas(lstVentas, true);
			}
			
			
			listfoot = new Listfoot();
			listfooter = new Listfooter();
			listfooter.setSpan(11);			
			Div div1 = new Div();
			div1.setHeight("28px");
			Div div = new Div();
			div.setHeight("22px");
			div.setAlign("right");
			Vlayout vlayout1 = new Vlayout();
			Label label = new Label();
			label.setValue("TOTAL S/. :");
			label.setStyle("font-weight:bold");
			vlayout1.appendChild(label);
			div.appendChild(vlayout1);
			label = new Label();
			label.setValue("TOTAL $ :");
			label.setStyle("font-weight:bold");
			vlayout1.appendChild(label);
			div.appendChild(vlayout1);
			
			div1.appendChild(div);

			
			listfooter.appendChild(div1);
			listfoot.appendChild(listfooter);
			
			/*Calcula el total a liquidar*/
			if(cmbTipoMovimiento.getSelectedIndex()==0){
				Double totalVentas=totalEfectivo+totalTarjeta+totalCortesia+totalPrepagado+totalCredito+totalEquipajePCE; 
				total=totalVentas-(totalDevolucion+totalCortesia+totalCredito+totalPrepagado+totalTarjeta+totalNotasCredito);
				total = totalVentas;
				totalDolares = totalEfectivoDolares + totalCreditoDolares;
			}
			
			listfooter = new Listfooter();
			Vlayout vlayout = new Vlayout();
			dblbxTotal = new Doublebox(total);
			dblbxTotal.setStyle("font-size:10px;font-align:right");
			dblbxTotal.setFormat("#,###,##0.00");
			dblbxTotal.setWidth("57px");
			dblbxTotal.setLocale(Locale.US);
			vlayout.appendChild(dblbxTotal);
			dbxTotalDolares = new Doublebox(totalDolares);
			dbxTotalDolares.setStyle("font-size:10px;font-align:right");
			dbxTotalDolares.setFormat("#,###,##0.00");
			dbxTotalDolares.setWidth("57px");
			dbxTotalDolares.setLocale(Locale.US);
			vlayout.appendChild(dbxTotalDolares);
			
			listfooter.appendChild(vlayout);						
			listfooter.setStyle("font-size:10px !important; padding:1px");
			listfoot.appendChild(listfooter);
			lbxVentas.appendChild(listfoot);
		} catch (Exception e) {
			e.printStackTrace();
			DlgMessage.error(e.getMessage());
		}		
	}
	
	@SuppressWarnings("deprecation")
	private void loadVentas(List<VentaPasaje> lstVentas, Boolean isCarga){
		try{
//			lbxVentas.getItems().clear();
//			grdTotales.setVisible(false);
//			for(int j=0; j<lbxVentas.getChildren().size();j++){
//				if(lbxVentas.getChildren().get(j) instanceof Listfoot)
//					lbxVentas.getChildren().get(j).detach();
//			}
//			Integer idAgencia = null;
//			Integer idUsuario = null;
//			Integer criterio = 0;
//			String fechaInicio = Util.DatetoString(dtbxFechaInicio.getValue(), Constantes.DATE_FORMAT);
//			String fechaFin = Util.DatetoString(dtbxFechaFin.getValue(), Constantes.DATE_FORMAT);
//			
//			if(cmbAgencia.getSelectedItem().getValue() instanceof Agencia)
//				idAgencia = ((Agencia)cmbAgencia.getSelectedItem().getValue()).getId();
//			if(cmbCounter.getItemCount()>0 && cmbCounter.getSelectedItem().getValue() instanceof Usuario)
//				idUsuario = ((Usuario)cmbCounter.getSelectedItem().getValue()).getId();
//			if(cmbTipoMovimiento.getSelectedIndex()>=0)
//				criterio = cmbTipoMovimiento.getSelectedIndex();
//			
//			List<VentaPasaje> lstVentas = ServiceLocator.getVentaPasajesManager().buscarDetalladoVentas(idAgencia, idUsuario, fechaInicio, fechaFin, criterio);
			
			Listitem item = null;
			Listcell cell = null;
			int i=0;
			if(lstVentas.size()>0){
//				total = 0.0;
//				totalDevolucion = 0.0;
//				totalCortesia = 0.0;
//				totalCredito = 0.0;
//				totalPrepagado = 0.0;
//				totalEfectivo = 0.0;
//				totalTarjeta = 0.0;
//				totalNotasCredito=0.0;
//				totalEquipajePCE = 0.0;
//				totalDolares =0.0;
//				totalEfectivoDolares = 0.0;
//				totalCreditoDolares = 0.0;
				
				for(VentaPasaje venta : lstVentas){
					i++;
					item = new Listitem();
					String style="";
//					if(cmbTipoMovimiento.getSelectedIndex()==0){
						
//					}
										
					if(cmbTipoMovimiento.getSelectedIndex()==0){
						
						if(venta.getTipoTransaccion().equals("V.(EF)") || venta.getTipoTransaccion().equals("RC.(EF)") 
								|| venta.getTipoTransaccion().equals("CONF.FA.(EF)")
								|| venta.getTipoTransaccion().equals("CONF.FA.(TRA)")
								|| venta.getTipoTransaccion().equals("FA.(EF)")
								|| venta.getTipoTransaccion().equals("FA.(TRA)")
								|| venta.getTipoTransaccion().equals("POST.(EF)")
								|| venta.getTipoTransaccion().equals("POST.FA.(EF)")
								|| venta.getTipoTransaccion().equals("REIMP.(EF)")
								|| venta.getTipoTransaccion().equals("V.(TRA)")
								|| venta.getTipoTransaccion().equals("RC.(TRA)")
								|| venta.getTipoTransaccion().equals("GAS.ADM(EFE)")
//								|| venta.getTipoTransaccion().equals("NOTA CREDITO")
								|| venta.getTipoTransaccion().equals("NOTA DEBITO")
								|| venta.getTipoTransaccion().equals("EQUIPAJE(EF)")
								|| venta.getTipoTransaccion().equals("SERV.ESP(EF)")
						){
							if(venta.getTipoMoneda()==null) {
								total = total + venta.getImportePagado();
							}else {
								totalDolares = totalDolares + venta.getImportePagado();
							}
//							style = "color:blue !important; font-weight:bold";
						}
						
						if(venta.getTipoTransaccion().equals("NOTA CREDITO")){
							totalNotasCredito+= + venta.getImportePagado();							
//							total+= - venta.getImportePagado();
						}
						
						if(venta.getTipoTransaccion().equals("V.(EF)") 
								|| venta.getTipoTransaccion().equals("CONF.FA.(EF)")
								|| venta.getTipoTransaccion().equals("FA.(EF)")
								|| venta.getTipoTransaccion().equals("POST.(EF)")
								|| venta.getTipoTransaccion().equals("POST.FA.(EF)")
								|| venta.getTipoTransaccion().equals("REIMP.(EF)")
								|| venta.getTipoTransaccion().equals("V.(TRA)")
								|| venta.getTipoTransaccion().equals("GAS.ADM(EFE)")
								|| venta.getTipoTransaccion().equals("CONF.FA.(TRA)")
								|| venta.getTipoTransaccion().equals("FA.(TRA)")
								|| venta.getTipoTransaccion().equals("EQUIPAJE(EF)")
								|| venta.getTipoTransaccion().equals("SERV.ESP(EF)")
						  )
							if(venta.getTipoMoneda()==null)
								totalEfectivo = totalEfectivo + venta.getImportePagado();
							else
								totalEfectivoDolares = totalEfectivoDolares + venta.getImportePagado();
						else if(venta.getTipoTransaccion().equals("CREDITO"))
							if(venta.getTipoMoneda()==null)
								totalCredito = totalCredito + venta.getImportePagado();
							else
								totalCreditoDolares = totalCreditoDolares + venta.getImportePagado();														
						else if(venta.getTipoTransaccion().equals("PREP.(EF)") || venta.getTipoTransaccion().equals("PREP.(TC"))
							totalPrepagado = totalPrepagado + venta.getImportePagado();							
						else if(venta.getTipoTransaccion().equals("EQUIPAJE(PCE)") 
								|| venta.getTipoTransaccion().equals("PCE")
//								|| venta.getTipoTransaccion().equals("RC.(TRA)")
							   )
							totalEquipajePCE = totalEquipajePCE + venta.getImportePagado();
						else if(venta.getTipoTransaccion().equals("V.(TC)") 
								|| venta.getTipoTransaccion().equals("FA.(TC)") 
								|| venta.getTipoTransaccion().equals("CONF.FA.(TC)") 
								|| venta.getTipoTransaccion().equals("REIMP.(TC)") 
								|| venta.getTipoTransaccion().equals("POST.(TC)")
								|| venta.getTipoTransaccion().equals("POST.FA.(TC)")
								|| venta.getTipoTransaccion().equals("GAS.ADM(TC)")
								|| venta.getTipoTransaccion().equals("EQUIPAJE(TC)")
								
							   ){
							/*Valida si existe diferencia en la tarifa en el movimiento de la venta*/
							if(venta.getImportePagadoByDiferencia()>0){
								totalTarjeta +=+ venta.getImportePagadoByDiferencia();
								totalEfectivo+=+  venta.getImportePagado()-venta.getImportePagadoByDiferencia();
							}else
								totalTarjeta = totalTarjeta + venta.getImportePagado();	
						}else if(venta.getTipoTransaccion().equals("DEV.80%") || venta.getTipoTransaccion().equals("DEV.100%")){
							/*Valida si es un boleto de viaje o recivo de caja 15/12/2016 - jabanto*/
							if(venta.getTipoComprobante().getId().intValue()==Constantes.ID_TIPCOM_BOLETO_VIAJE || venta.getTipoComprobante().getId().intValue()==Constantes.ID_TIPCOM_RECIBO_CAJA){								
								if(venta.getRucClienteCredito()!=null){
									/*No toma en cuenta las emitidas por operadores del Pool - 15/12/2016 - jabanto*/
									if(!(venta.getRucClienteCredito().equals(Constantes.RUC_CIVA)) || !(venta.getRucClienteCredito().equals(Constantes.RUC_CRUZ_DEL_SUR))){
										total = total - venta.getImportePagado();
										totalDevolucion = totalDevolucion + venta.getImportePagado();
									}
								}else{
									total = total - venta.getImportePagado();
									totalDevolucion = totalDevolucion + venta.getImportePagado();
								}
							}else{
								/*Facturas y Boletas*/
								if(venta.getRucClienteCredito()!=null){
									/*No toma en cuenta las emitidas por operadores del Pool - 15/12/2016 - jabanto*/
									if(!(venta.getRucClienteCredito().equals(Constantes.RUC_CIVA)) || !(venta.getRucClienteCredito().equals(Constantes.RUC_CRUZ_DEL_SUR))){
										/*Valida si es una devolucion de una canal Agencia de Viajes o Web*/
										if(venta.getCanalVenta().getId().intValue()==Constantes.ID_CANVEN_AGENCIA_VIAJES || venta.getCanalVenta().getId().intValue()==Constantes.ID_CANVEN_WEB){
											total = total - venta.getImportePagado();
											totalDevolucion = totalDevolucion + venta.getImportePagado();
										}
									}
								}else{
									/*Valida si es una devolucion de una canal Agencia de Viajes o Web*/
									if(venta.getCanalVenta().getId().intValue()==Constantes.ID_CANVEN_AGENCIA_VIAJES || venta.getCanalVenta().getId().intValue()==Constantes.ID_CANVEN_WEB){
										total = total - venta.getImportePagado();
										totalDevolucion = totalDevolucion + venta.getImportePagado();
									}
								}
							}
							
//							/*End Begin 15/12/2012 - jabanto  [Valida si es un boleto de viaje o recivo de caja]*/
//							if(venta.getTipoComprobante().getId().intValue()==Constantes.ID_TIPCOM_BOLETO_VIAJE || venta.getTipoComprobante().getId().intValue()==Constantes.ID_TIPCOM_RECIBO_CAJA){
//								total = total - venta.getImportePagado();
//								totalDevolucion = totalDevolucion + venta.getImportePagado();
//							}else if(venta.getRucClienteCredito()!=null && (venta.getRucClienteCredito().equals(Constantes.RUC_CIVA) ||
//																		    venta.getRucClienteCredito().equals(Constantes.RUC_CRUZ_DEL_SUR))){
//								/*Devoluciones de comprobantes emitidos por otro operador como Civa o Cruz del sur.*/
//								total = total - venta.getImportePagado();
//								totalDevolucion = totalDevolucion + venta.getImportePagado();
//							}else if(venta.getCanalVenta().getId().intValue()==Constantes.ID_CANVEN_AGENCIA_VIAJES || venta.getCanalVenta().getId().intValue()==Constantes.ID_CANVEN_WEB){
//								/*Tambien los toma en cuenta las devoluciones efectuadas a los comprobantes emitidos con un canal de Agencia de viajes o Web, ya 
//								 * que a estos no se les aplica nota de credito - 15/12/2016 - jabanto*/
//								total = total - venta.getImportePagado();
//								totalDevolucion = totalDevolucion + venta.getImportePagado();
//							}
						}else if(venta.getTipoTransaccion().equals("CORTXCUMP") 
								|| venta.getTipoTransaccion().equals("CORTXPUNT")
								|| venta.getTipoTransaccion().equals("CORT"))
							totalCortesia = totalCortesia + venta.getImportePagado();
					}else{
						if(venta.getTipoMovimiento().getId().intValue()==Constantes.ID_TIPMOV_DEVOLUCION){
							if(venta.getTipoComprobante().getId().intValue()==Constantes.ID_TIPCOM_BOLETO_VIAJE || venta.getTipoComprobante().getId().intValue()==Constantes.ID_TIPCOM_RECIBO_CAJA){
								if(venta.getRucClienteCredito()!=null){
									/*No toma en cuenta las emitidas por operadores del Pool - 15/12/2016 - jabanto*/
									if(!(venta.getRucClienteCredito().equals(Constantes.RUC_CIVA)) || !(venta.getRucClienteCredito().equals(Constantes.RUC_CRUZ_DEL_SUR)))
										total = total + venta.getImportePagado();	
								}else
									total = total + venta.getImportePagado();
							}else{
								/*Bolestas y Facturas*/
								if(venta.getRucClienteCredito()!=null){
									/*No toma en cuenta las emitidas por operadores del Pool - 15/12/2016 - jabanto*/
									if(!(venta.getRucClienteCredito().equals(Constantes.RUC_CIVA)) || !(venta.getRucClienteCredito().equals(Constantes.RUC_CRUZ_DEL_SUR))){
										/*Valida si es una devolucion de una canal Agencia de Viajes o Web*/
										if(venta.getCanalVenta().getId().intValue()==Constantes.ID_CANVEN_AGENCIA_VIAJES || venta.getCanalVenta().getId().intValue()==Constantes.ID_CANVEN_WEB)
											total = total + venta.getImportePagado();
									}
								}else{
									/*Valida si es una devolucion de una canal Agencia de Viajes o Web*/
									if(venta.getCanalVenta().getId().intValue()==Constantes.ID_CANVEN_AGENCIA_VIAJES || venta.getCanalVenta().getId().intValue()==Constantes.ID_CANVEN_WEB)
										total = total + venta.getImportePagado();	
								}
							}
						}else{
							if(venta.getTipoMoneda()==null)
								total = total + venta.getImportePagado();
							else
								totalDolares = totalDolares + venta.getImportePagado();
						}
						
						/*End Begin 15/12/2016 - jabanto [Valida si es una devolucion de Boleto de viaje o recibo de caja - 06/12/2016 - jabanto]*/
//						if(venta.getTipoMovimiento().getId().intValue()==Constantes.ID_TIPMOV_DEVOLUCION && 
//								(venta.getTipoComprobante().getId().intValue()==Constantes.ID_TIPCOM_BOLETO_VIAJE || venta.getTipoComprobante().getId().intValue()==Constantes.ID_TIPCOM_RECIBO_CAJA)){
//							total = total + venta.getImportePagado();
//						}else if (venta.getTipoMovimiento().getId().intValue()==Constantes.ID_TIPMOV_DEVOLUCION &&
//								(venta.getCanalVenta().getId().intValue()==Constantes.ID_CANVEN_AGENCIA_VIAJES || venta.getCanalVenta().getId().intValue()==Constantes.ID_CANVEN_WEB)){
//							/*Si es una devolucion de un comprobante emitido por un canal Agencia de viajes y Web - 15/12/2016 - jabanto */
//							total = total + venta.getImportePagado();
//						}else if(venta.getTipoMovimiento().getId().intValue()!=Constantes.ID_TIPMOV_DEVOLUCION){
//							total = total + venta.getImportePagado();
//						}
					}
					
					if(venta.getTipoTransaccion().equals("ANULADO"))
						style = "color:red !important; font-weight:bold";						
					
					cell = new Listcell(String.valueOf(i));
					item.appendChild(cell);
					if(venta.getServicio()!=null && (venta.getServicio().getId().intValue()==Constantes.ID_SERVICIO_POOL_CRUZDELSUR || venta.getServicio().getId().intValue()==Constantes.ID_SERVICIO_POOL_EXCLUCIVA))
						cell = new Listcell(venta.getTipoTransaccion()+"-POOL");
					else
						cell = new Listcell(venta.getTipoTransaccion());
					cell.setStyle(style);
					item.appendChild(cell);
					if(venta.getNumeroControl()!=null) {
						String numeroControl=venta.getNumeroControl();
						String prefijo=numeroControl.substring(0,1);
						numeroControl=prefijo+numeroControl.substring(6);
						cell = new Listcell(numeroControl);	
					}else
						cell = new Listcell("");					
					cell.setStyle(style);
					item.appendChild(cell);
					
					/*Evento para realizar el cambio de la forma de pago*/
					cell = new Listcell();
					cell.setStyle(style);
					if(isCarga==false && venta.getTipoMovimiento().getId().intValue()!=Constantes.ID_TIPMOV_ANULACION && 
							venta.getTipoMovimiento().getId().intValue()!=Constantes.ID_TIPMOV_ANULACION_SISTEMA && 
							venta.getTipoMovimiento().getId().intValue()!=Constantes.ID_TIPMOV_DEVOLUCION &&
							venta.getTipoMovimiento().getId().intValue()!=Constantes.ID_TIPMOV_PREPAGADO &&
							venta.getFormaPago().getId().intValue()==Constantes.ID_FORPAG_CONTADO &&
							venta.getImportePagado()>.00){
						
						if((venta.getTipoComprobante().getId().intValue()==Constantes.ID_TIPCOM_RECIBO_CAJA && venta.getNumeroBoletoAnterior()==null) ||
								venta.getTipoComprobante().getId().intValue()==Constantes.ID_TIPCOM_BOLETO_VIAJE || 
								venta.getTipoComprobante().getId().intValue()==Constantes.ID_TIPCOM_BOLETA_VENTA ||
								venta.getTipoComprobante().getId().intValue()==Constantes.ID_TIPCOM_FACTURA){
							
							A a=new A();
							a.setLabel(venta.getNumeroBoleto());
							a.setStyle("color: #0065CC;text-align:center;font-size: 11px;");
							a.setTooltiptext("Cambiar la Forma de Pago.");
							a.setId(venta.getId().toString());
							a.addEventListener(Events.ON_CLICK, new EventListener<Event>() {
								@Override
								public void onEvent(Event event) throws Exception {
									VentaPasaje ventaPasaje=ServiceLocator.getVentaPasajesManager().buscarPorId(Long.valueOf(event.getTarget().getId()));
									ventanaCambioFormaPago(ventaPasaje);
								}
							});
							
							/*Validacion de Roles*/
							if((getRol().getId().intValue()==Constantes.ID_ROL_ADMIN_PUNTO_VENTA || getRol().getId().intValue()==Constantes.ID_ROL_REP_VENTAS) 
									&& venta.getLiquidacion()==null){
								cell.appendChild(a);
							}else if(getRol().getId().intValue()==Constantes.ID_ROL_SUPER_USUARIO && 
									venta.getFechaLiquidacion().getTime()>Constantes.FORMAT_DATE.parse(Constantes.FORMAT_DATE.format(new Date())).getTime()-(Constantes.MILISEGUNDOS_X_DIA*3)){
								//Rol superusuario y como maximo 3 días con anterioridad
								cell.appendChild(a);
							}else{
								cell.setLabel(venta.getNumeroBoleto());
							}
						}else{
							cell.setLabel(venta.getNumeroBoleto());
						}
					}else{
						cell.setLabel(venta.getNumeroBoleto());
					}
					item.appendChild(cell);
					
					cell = new Listcell(venta.getNumeroBoletoAnterior()==null?"":venta.getNumeroBoletoAnterior());
					cell.setStyle("font-weight:bold");
					item.appendChild(cell);
					
					cell = new Listcell(venta.getTipoMoneda()==null?"PEN":"USD");
					cell.setStyle(style);
					item.appendChild(cell);
					
//					cell = new Listcell(venta.getImportePagadoEquibalente()==null?Util.toNumberFormat(venta.getTarifa(), 2):Util.toNumberFormat(venta.getImportePagadoEquibalente(),2));
					cell = new Listcell(Util.toNumberFormat(venta.getTarifa(), 2));
					cell.setStyle(style);
					item.appendChild(cell);
					cell = new Listcell(Util.toNumberFormat(venta.getRecargo(), 2));
					cell.setStyle(style);
					item.appendChild(cell);
					cell = new Listcell(Util.toNumberFormat(venta.getDescuento(), 2));
					cell.setStyle(style);
					item.appendChild(cell);
					cell = new Listcell(Util.toNumberFormat(venta.getAcuenta(), 2));
					cell.setStyle(style);
					item.appendChild(cell);
					cell = new Listcell(Util.toNumberFormat(venta.getPenalidad(), 2));
					cell.setStyle(style);
					item.appendChild(cell);
					cell = new Listcell(Util.toNumberFormat(venta.getImportePagado(), 2));
					cell.setStyle(style);
					item.appendChild(cell);
					cell = new Listcell(venta.getUsuario().getApellidoPaterno()+" "+venta.getUsuario().getNombre());
					cell.setStyle(style);
					item.appendChild(cell);
					cell = new Listcell(Util.DatetoString(venta.getFechaInsercion(), Constantes.DATE_TIME_FORMAT));
					cell.setStyle(style);
					item.appendChild(cell);
					cell = new Listcell();
					Hlayout hlayout = new Hlayout();
					
					/*###End begin 03/11/2016 - jabanto*/
//					Button btnDuplicado = new Button();
//					btnDuplicado.setId(venta.getId().toString());
//					btnDuplicado.addEventListener(Events.ON_CLICK, new EventListener<Event>() {
//						public void onEvent(Event e){
//							duplicarBoleto(e.getTarget().getId());
//						}
//					});
//					btnDuplicado.setImage("resources/mp_duplicar.png");
//					btnDuplicado.setStyle("cursor:pointer");
//					btnDuplicado.setTooltiptext("Haga click aqui si desea generar una copia del Boleto.");
//					hlayout.appendChild(btnDuplicado);
					
					Button btnAnular = new Button();
					if(isCarga || venta.getId()==null || rubroCarga.isChecked())
						btnAnular.setVisible(false);
					else
						btnAnular.setId(venta.getId().toString());
					btnAnular.addEventListener(Events.ON_CLICK, new EventListener<Event>() {
						@Override
						public void onEvent(Event e) throws Exception{
							anularBoleto(e.getTarget().getId());
						}
					});
					btnAnular.setImage("resources/mp_anular.png");
					btnAnular.setClass("btnImage");
//					btnAnular.setStyle("cursor:pointer");
					btnAnular.setTooltiptext("Haga click aqui si desea anular el Boleto");
					hlayout.appendChild(btnAnular);
					cell.appendChild(hlayout);
					item.appendChild(cell);
					
					/*Muestra las devoluciones de un boleto de viaje o recivo de caja - 15/12/2016 - jabanto*/
					if(venta.getTipoMovimiento().getId().intValue()==Constantes.ID_TIPMOV_DEVOLUCION && 
							(venta.getTipoComprobante().getId().intValue()==Constantes.ID_TIPCOM_BOLETO_VIAJE || venta.getTipoComprobante().getId().intValue()==Constantes.ID_TIPCOM_RECIBO_CAJA)){
						item.setValue(venta);
						lbxVentas.appendChild(item);
					}else if(venta.getTipoMovimiento().getId().intValue()==Constantes.ID_TIPMOV_DEVOLUCION &&
							(venta.getCanalVenta().getId().intValue()==Constantes.ID_CANVEN_AGENCIA_VIAJES || venta.getCanalVenta().getId().intValue()==Constantes.ID_CANVEN_WEB)){
						/*Muestra las devoluciones de un comprobante emitido por un canal Agencia de viajes o Web*/
						item.setValue(venta);
						lbxVentas.appendChild(item);
					}else if(venta.getTipoMovimiento().getId().intValue()!=Constantes.ID_TIPMOV_DEVOLUCION){
						item.setValue(venta);
						lbxVentas.appendChild(item);
					}
				}
//				Listfoot listfoot = new Listfoot();
//				Listfooter listfooter = new Listfooter();
				
//				listfooter.setSpan(11);
//				
//				Div div1 = new Div();
//				div1.setHeight("28px");
//				Div div = new Div();
//				div.setHeight("22px");
//				div.setAlign("right");
//				Vlayout vlayout1 = new Vlayout();
//				Label label = new Label();
//				label.setValue("TOTAL S/. :");
//				label.setStyle("font-weight:bold");
//				vlayout1.appendChild(label);
//				div.appendChild(vlayout1);
//				label = new Label();
//				label.setValue("TOTAL $ :");
//				label.setStyle("font-weight:bold");
//				vlayout1.appendChild(label);
//				div.appendChild(vlayout1);
//				
//				div1.appendChild(div);
//
//				
//				listfooter.appendChild(div1);
//				listfoot.appendChild(listfooter);
//				
//				/*Calcula el total a liquidar*/
//				if(cmbTipoMovimiento.getSelectedIndex()==0){
//					Double totalVentas=totalEfectivo+totalTarjeta+totalCortesia+totalPrepagado+totalCredito+totalEquipajePCE; 
//					total=totalVentas-(totalDevolucion+totalCortesia+totalCredito+totalPrepagado+totalTarjeta+totalNotasCredito);
//					total = totalVentas;
//					totalDolares = totalEfectivoDolares + totalCreditoDolares;
//				}
//				
//				listfooter = new Listfooter();
//				Vlayout vlayout = new Vlayout();
//				dblbxTotal = new Doublebox(total);
//				dblbxTotal.setStyle("font-size:10px;font-align:right");
//				dblbxTotal.setFormat("#,###,##0.00");
//				dblbxTotal.setWidth("57px");
//				dblbxTotal.setLocale(Locale.US);
//				vlayout.appendChild(dblbxTotal);
//				dbxTotalDolares = new Doublebox(totalDolares);
//				dbxTotalDolares.setStyle("font-size:10px;font-align:right");
//				dbxTotalDolares.setFormat("#,###,##0.00");
//				dbxTotalDolares.setWidth("57px");
//				dbxTotalDolares.setLocale(Locale.US);
//				vlayout.appendChild(dbxTotalDolares);
				
				
				if(cmbTipoMovimiento.getSelectedIndex()==0){
					lblEfectivo.setValue(Util.toNumberFormat(totalEfectivo,2));
//					Doublebox dblbxDevolucion = new Doublebox(totalDevolucion);
//					dblbxDevolucion.setStyle("font-size:10px;font-align:right");
//					dblbxDevolucion.setFormat("#,###,##0.00");
//					dblbxDevolucion.setWidth("57px");
//					dblbxDevolucion.setLocale(Locale.US);
//					vlayout.appendChild(dblbxDevolucion);
//					
					lblTarjeta.setValue(Util.toNumberFormat(totalTarjeta,2));
//					Doublebox dblbxCortesia = new Doublebox(totalCortesia);
//					dblbxCortesia.setStyle("font-size:10px;font-align:right");
//					dblbxCortesia.setFormat("#,###,##0.00");
//					dblbxCortesia.setWidth("57px");
//					dblbxCortesia.setLocale(Locale.US);
//					vlayout.appendChild(dblbxCortesia);
//					
					lblCortesias.setValue(Util.toNumberFormat(totalCortesia,2));
//					Doublebox dblbxCredito = new Doublebox(totalCredito);
//					dblbxCredito.setStyle("font-size:10px;font-align:right");
//					dblbxCredito.setFormat("#,###,##0.00");
//					dblbxCredito.setWidth("57px");
//					dblbxCredito.setLocale(Locale.US);
//					vlayout.appendChild(dblbxCredito);
//					
					lblCredito.setValue(Util.toNumberFormat(totalCredito,2));
//					Doublebox dblbxPrepagado = new Doublebox(totalPrepagado);
//					dblbxPrepagado.setStyle("font-size:10px;font-align:right");
//					dblbxPrepagado.setFormat("#,###,##0.00");
//					dblbxPrepagado.setWidth("57px");
//					dblbxPrepagado.setLocale(Locale.US);
//					vlayout.appendChild(dblbxPrepagado);
//					
					lblRecibos.setValue(Util.toNumberFormat(totalEquipajePCE,2));
//					Doublebox dblbxRecibos = new Doublebox(totalRecibos);
//					dblbxRecibos.setStyle("font-size:10px;font-align:right");
//					dblbxRecibos.setFormat("#,###,##0.00");
//					dblbxRecibos.setWidth("57px");
//					dblbxRecibos.setLocale(Locale.US);
//					vlayout.appendChild(dblbxRecibos);
//					
					lblNotascredito.setValue(Util.toNumberFormat(totalNotasCredito,2));
//					Doublebox dblbxVentaEF = new Doublebox(totalEfectivo);
//					dblbxVentaEF.setStyle("font-size:10px;font-align:right");
//					dblbxVentaEF.setFormat("#,###,##0.00");
//					dblbxVentaEF.setWidth("57px");
//					dblbxVentaEF.setLocale(Locale.US);
//					vlayout.appendChild(dblbxVentaEF);
//					
					lblDevoluciones.setValue(Util.toNumberFormat(totalDevolucion,2));
//					Doublebox dblbxVentaTC = new Doublebox(totalTarjeta);
//					dblbxVentaTC.setStyle("font-size:10px;font-align:right");
//					dblbxVentaTC.setFormat("#,###,##0.00");
//					dblbxVentaTC.setWidth("57px");
//					dblbxVentaTC.setLocale(Locale.US);
//					vlayout.appendChild(dblbxVentaTC);
//					
					lblPrepagado.setValue(Util.toNumberFormat(totalPrepagado,2));
//					Doublebox dblbxNotaCredito = new Doublebox(totalNotasCredito);
//					dblbxNotaCredito.setStyle("font-size:10px;font-align:right");
//					dblbxNotaCredito.setFormat("#,###,##0.00");
//					dblbxNotaCredito.setWidth("57px");
//					dblbxNotaCredito.setLocale(Locale.US);
//					vlayout.appendChild(dblbxNotaCredito);
					
					lblEfectivoDolares.setValue(Util.toNumberFormat(totalEfectivoDolares, 2));
					lblCreditoDolares.setValue(Util.toNumberFormat(totalCreditoDolares, 2));
					
					grdTotales.setVisible(true);
				}
				
//				listfooter.appendChild(vlayout);
//
//				listfooter.setStyle("font-size:10px !important; padding:1px");
//				listfoot.appendChild(listfooter);
//				lbxVentas.appendChild(listfoot);
			}else
				DlgMessage.information(Messages.getString("WndLiquidacionDiariaVentas.information.noVentas"));
		}catch(Exception ex){
			ex.printStackTrace();
			DlgMessage.error(this.getClass().getSimpleName()+" "+ex.getMessage());
		}
	}
	
//	/**
//	 *  Realiza el duplicado de boleto.
//	 * @param idVenta : Identificador de la venta 
//	 */
//	public void duplicarBoleto(String idVenta){
//		try{
//			duplicadoBoleto=null;
//			final VentaPasaje ventaOriginal = ServiceLocator.getVentaPasajesManager().buscarVentaById(Long.valueOf(idVenta));
//			Date fechaVenta = ventaOriginal.getFechaInsercion();
//			Date fechaActual = new Date();
//			Long tiempoMaximo =fechaVenta.getTime()+(Constantes.TIEMPO_MAXIMO_DUPLICAR_BOLETO*Constantes.MILISEGUNDOS_X_MINUTO);
//			if(ventaOriginal.getImportePagado().doubleValue()<=0){
//				DlgMessage.information(Messages.getString("WndLiquidacionDiariaVentas.information.noDuplicado"));
//			}else if(fechaActual.getTime()>tiempoMaximo)
//				DlgMessage.information(Messages.getString("WndLiquidacionDiariaVentas.information.tiempoMaximoAlcanzado"));
//			else if(ventaOriginal.getTipoMovimiento().getId().intValue()==Constantes.ID_TIPMOV_ANULACION_SISTEMA)
//				DlgMessage.information(Messages.getString("WndLiquidacionDiariaVentas.information.boletoAnulado"));
//			else if (ventaOriginal.getTipoMovimiento().getId().intValue()==Constantes.ID_TIPMOV_DEVOLUCION)
//				DlgMessage.information(Messages.getString("WndLiquidacionDiariaVentas.information.boletoDevuelto"));
//			else if (ServiceLocator.getDetalleManifiestoManager().validarVentaManifiesto(Long.valueOf(idVenta))){
//				DlgMessage.information(Messages.getString("Generales.information.manifiestoImpreso"));
////			else if(ventaOriginal.getManifiesto()!=null){
////				DlgMessage.information(Messages.getString("Generales.information.manifiestoImpreso"));
//			}else if(ventaOriginal.getTipoComprobante().getId().intValue()!=Constantes.ID_TIPCOM_BOLETO_VIAJE){
//				DlgMessage.information(Messages.getString("WndLiquidacionDiariaVentas.information.noBoleto"));
//			}else if(ventaOriginal.getLiquidacion()==null){
//				UsuarioHardware usuarioHardware = (UsuarioHardware) this.getDesktop().getSession().getAttribute(Constantes.ATRIBUTO_USUARIO_HARDWARE);
//				final String boleto = UtilData.buscarEspecieValorada(Constantes.ID_TIPCOM_BOLETO_VIAJE, usuarioHardware.getId());
//				wndDuplicar = createVentanaDuplicado(ventaOriginal, boleto);
//				this.appendChild(wndDuplicar);
//				wndDuplicar.setMode("modal");
//			}else
//				DlgMessage.information(Messages.getString("WndLiquidacionDiariaVentas.information.boletoLiquidado"));						
//		}catch(Exception ex){
//			DlgMessage.error(this.getClass().getSimpleName()+" "+ex.getMessage());
//			ex.printStackTrace();
//		}
//	}
	
//	private Window createVentanaDuplicado(final VentaPasaje ventaOriginal, final String boleto){
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
//		caption = new Caption("DUPLICADO DE BOLETO", "resources/mp_duplicar.png");
//		win.appendChild(caption);
//		label = new Label("Se va a generar el duplicado de boleto con los siguientes datos :");
//		label.setStyle("font-size:12px !important");
//		win.appendChild(label);
//		
//		win.appendChild(new Separator("horizontal"));
//		
//		groupbox = new Groupbox();
//		groupbox.setClosable(false);
//		caption = new Caption("Boleto Actual");
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
//		
//		label = new Label("BOLETO ACTUAL :");
//		row.appendChild(label);
//		
//		text = new Textbox(ventaOriginal.getNumeroBoleto());
//		text.setReadonly(true);
//		text.setWidth("80px");
//		row.appendChild(text);
//		
//		label = new Label("FECHA VIAJE :");
//		row.appendChild(label);
//		
//		text = new Textbox(ventaOriginal.getFechaPartida()==null?"":Util.DatetoString(ventaOriginal.getFechaPartida(), Constantes.DATE_FORMAT));
//		text.setReadonly(true);
//		text.setWidth("80px");
//		row.appendChild(text);
//		
//		rows.appendChild(row);
//		
//		row = new Row();
//		
//		label = new Label("NUMERO ASIENTO :");
//		row.appendChild(label);
//		
//		text = new Textbox(ventaOriginal.getNumeroAsiento()==null?"":ventaOriginal.getNumeroAsiento().toString());
//		text.setReadonly(true);
//		text.setWidth("80px");
//		row.appendChild(text);
//		
//		label = new Label("IMPORTE :");
//		row.appendChild(label);
//		
//		text = new Textbox(Util.toNumberFormat(ventaOriginal.getImportePagado(), 2));
//		text.setReadonly(true);
//		text.setWidth("80px");
//		row.appendChild(text);
//		
//		rows.appendChild(row);
//		grid.appendChild(rows);
//		groupbox.appendChild(grid);
//		win.appendChild(groupbox);
//		
//		groupbox = new Groupbox();
//		groupbox.setClosable(false);
//		caption = new Caption("Boleto Nuevo");
//		groupbox.appendChild(caption);
//		
//		
//		grid = new Grid();
//		columns = new Columns();
//		rows = new Rows();
//		/*	Columna 1	*/
//		column = new Column();
//		column.setAlign("right");
//		column.setWidth("118px");
//		columns.appendChild(column);
//		/*	Columna 2	*/
//		column = new Column();
//		columns.appendChild(column);
//		
//		grid.appendChild(columns);
//		
//		row = new Row();
//		
//		label = new Label("NUMERO BOLETO :");
//		row.appendChild(label);
//		
//		text = new Textbox(boleto);
//		text.setReadonly(true);
//		text.setWidth("80px");
//		row.appendChild(text);
//		
//		rows.appendChild(row);
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
//		Button button = new Button("Continuar", "resources/mp_duplicar.png");
//		button.addEventListener(Events.ON_CLICK, new EventListener<Event>() {
//			public void onEvent(Event e){
//				Messagebox.show(Messages.getString("WndLiquidacionDiariaVentas.information.confirmaDuplicado"), DlgMessage.NOMBREAPLICACION, DlgMessage.BTN_YESNO, Messagebox.QUESTION, new EventListener<Event>() {
//					public void onEvent(Event e){
//						try{
//							if(e.getName().equals("onYes")){
//								/*### BEGIN 21/05/2015 - JABANTO*/
//								String observacionesOrginal=ventaOriginal.getObservaciones()!=null?ventaOriginal.getObservaciones():null;
//								String observacion="ANULADO X UN DUPLICADO. NUEVO BOL. ==> "+boleto;
//								String observaciones=observacionesOrginal!=null?observacionesOrginal+" ::: "+observacion:observacion;
//								
//								if(observaciones.trim().length()>255)
//									observaciones=observaciones.substring(0, 255);
//								
//								duplicadoBoleto = (VentaPasaje)ventaOriginal.clone();
//								ventaOriginal.setTarifa(0.0);
//								ventaOriginal.setRecargo(0.0);
//								ventaOriginal.setDescuento(0.0);
//								ventaOriginal.setImportePagado(0.0);
//								ventaOriginal.setAcuenta(0.0);
//								ventaOriginal.setImportePagado(0.0);
//								ventaOriginal.setTipoMovimiento(new TipoMovimiento(Constantes.ID_TIPMOV_ANULACION));
//								ventaOriginal.setObservaciones(observaciones);
//								UtilData.auditarRegistro(ventaOriginal, true, usuario, Executions.getCurrent());
//																
//																
//								duplicadoBoleto.setId(null);
////								duplicadoBoleto.setVentaPasaje(ventaOriginal);
//								duplicadoBoleto.setNumeroBoleto(boleto);
//								duplicadoBoleto.setUsuarioHardware((UsuarioHardware)getDesktop().getSession().getAttribute(Constantes.ATRIBUTO_USUARIO_HARDWARE));
//								duplicadoBoleto.setEstadoRegistro(Constantes.VALUE_ACTIVO);
//								duplicadoBoleto.setFechaTransferencia(null);
//								duplicadoBoleto.setObservaciones("BOL. DUPLICADO DE ==> "+ventaOriginal.getNumeroBoleto());/*### BEGIN 21/05/2015 - JABANTO*/
//								duplicadoBoleto.setFechaInsercion(null);
//								duplicadoBoleto.setFechaModificacion(null);
//								UtilData.auditarRegistro(duplicadoBoleto, usuario, Executions.getCurrent());
//								int result = ServiceLocator.getVentaPasajesManager().duplicarBoleto(ventaOriginal, duplicadoBoleto);
//								if(result==Constantes.CORRECT){
//									duplicadoBoleto = ServiceLocator.getVentaPasajesManager().buscarVentaById(duplicadoBoleto.getId());
//									
//									/*Implementacion para el nueno formato 01/02/2016 - jabanto */
//									boolean formato=UtilData.getFormatoImprecion(getAgencia().getId(), getTipocomprobante().getId(), getUsuarioHardware().getId());
//									File file = CreateDocument.crearBoleto(duplicadoBoleto,formato);
//									
//									if(getUsuarioHardware().getPrintApplet().intValue()==Constantes.TRUE_VALUE){
////										String fileBoleto = Constantes.URL_FORMATOS_BOLETOS + duplicadoBoleto.getNumeroControl()+".txt";
//										String fileBoleto = Constantes.URL_FORMATOS_BOLETOS +Constantes.CLAVE_PAHT+ duplicadoBoleto.getNumeroControl()+".txt";
//										Window win = (Window)Executions.createComponents("imprimir.zul", null, null);
//										win.setAttribute("formato", WndImprimir.FORMAT_BOLETO);
//										win.setAttribute("msg", "Imprimiendo duplicado de Boleto de Viaje "+duplicadoBoleto.getNumeroBoleto());
//										win.setAttribute("urlDocumento", fileBoleto);
//										win.doPopup();
//										DlgMessage.information(Messages.getString("WndLiquidacionDiariaVentas.information.exitoDuplicado")+" "+duplicadoBoleto.getNumeroControl());
//										wndDuplicar.onClose();
//									}else{
//										//Descaga el archivo para la impresion
//										Util.descargarArchivo(file);
//									}
////									
//									onBuscarVentas();
//								}
//							}
//						}catch(NumeroBoletoDuplicadoException nbdex){
//							DlgMessage.information(Messages.getString("WndLiquidacionDiariaVentas.information.numeroBoletoVendido"));
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
	
	public void anularBoleto(String idVenta){
		try{
			final VentaPasaje ventaOriginal = ServiceLocator.getVentaPasajesManager().buscarVentaById(Long.valueOf(idVenta));
			final VentaPasaje ultimoMoviento = ServiceLocator.getVentaPasajesManager().buscarUltimoRegistro(ventaOriginal.getVentaOriginal());
			
			//###BEGIN 05/05/2016 - jabanto
			/*Valida si el boleto tiene movimientos superiores al que se esta anulando*/
			if(ultimoMoviento.getId().longValue()!=ventaOriginal.getId().longValue()){
				DlgMessage.information(Messages.getString("WndLiquidacionDiariaVentas.information.noAnulacion"));
				return;
			}
						
			//Validación para la anulación de un Reecibo de caja
			if (ventaOriginal.getTipoComprobante().getId().intValue()==Constantes.ID_TIPCOM_RECIBO_CAJA){
				VentaPasaje ultimoRegistro=ServiceLocator.getVentaPasajesManager().buscarUltimoRegistro(ventaOriginal.getVentaOriginal());
				//Valida si RC esta reimpreso y no esta anulado para continuar con la anulación. 
				if (ultimoRegistro.getTipoComprobante().getId().intValue()!=Constantes.ID_TIPCOM_RECIBO_CAJA &&
						ultimoRegistro.getTipoMovimiento().getId().intValue()!=Constantes.ID_TIPMOV_ANULACION){
					DlgMessage.information(Messages.getString("WndLiquidacionDiariaVentas.information.RCReimpreso"));
					return;
				}
			}
			else if(ventaOriginal.getTipoMovimiento().getId().intValue()==Constantes.ID_TIPMOV_ANULACION)
				DlgMessage.information(Messages.getString("WndLiquidacionDiariaVentas.information.boletoAnulado"));
			else if (ventaOriginal.getTipoMovimiento().getId().intValue()==Constantes.ID_TIPMOV_DEVOLUCION)
				DlgMessage.information(Messages.getString("WndLiquidacionDiariaVentas.information.boletoDevuelto"));
			else if(ServiceLocator.getDetalleManifiestoManager().validarVentaManifiesto(Long.valueOf(idVenta)))
				DlgMessage.information(Messages.getString("Generales.information.manifiestoImpreso"));
			
			
			if (!(ventaOriginal.getUsuario().getId().equals(getUsuario().getId())))
				DlgMessage.information(Messages.getString("WndLiquidacionDiariaVentas.information.otroUsuario"));
			else if(!(Constantes.FORMAT_DATE.format(ventaOriginal.getFechaInsercion()).equals(Constantes.FORMAT_DATE.format(new Date()))))
				DlgMessage.information(Messages.getString("WndLiquidacionDiariaVentas.information.fechaPasada"));			
			else if(ventaOriginal.getLiquidacion()==null){
//					if(ventaOriginal.getIdentificadorIdaRetorno()!=null){
//						Messagebox.show("Esta a punto de anular un boleto ida y vuelta, este proceso conlleva la anulación de los 2 boletos, desea continuar?", DlgMessage.NOMBREAPLICACION, DlgMessage.BTN_YESNO, Messagebox.QUESTION, new EventListener<Event>() {
//							public void onEvent(Event e){
//								if(e.getName().equals(Messagebox.ON_YES)){
//									wndAnular = createVentanaAnulacion(ventaOriginal);
//									wndLiquidacionDiariaVentas.appendChild(wndAnular);
//									wndAnular.setMode("modal");
//								}
//							}
//						});
//					}else{
				wndAnular = createVentanaAnulacion(ventaOriginal);
				this.appendChild(wndAnular);
				wndAnular.setMode("modal");
//					}
			}else
				DlgMessage.information(Messages.getString("WndLiquidacionDiariaVentas.information.boletoLiquidado"));
			
		}catch(Exception ex){
			DlgMessage.error(this.getClass().getSimpleName()+" "+ex.getMessage());
		}
	}
	
	@SuppressWarnings("deprecation")
	private Window createVentanaAnulacion(final VentaPasaje ventaOriginal){
		Caption caption = null;
		Groupbox groupbox = null;
		Grid grid = new Grid();
		Columns columns = new Columns();
		Column column = null;
		Rows rows = new Rows();
		Row row = null;
		Label label = null;
		Textbox text = null;
		
		final Window win = new Window("", "normal", true);
		win.setWidth("500px");
		
		caption = new Caption("ANULACION DE BOLETO", "resources/mp_anular.png");
		win.appendChild(caption);
		label = new Label("Se va a realizar la Anulacion del Boleto con los siguientes datos :");
		label.setStyle("font-size:12px !important");
		win.appendChild(label);
		
		win.appendChild(new Separator("horizontal"));
		
		groupbox = new Groupbox();
		groupbox.setClosable(false);
		caption = new Caption("Datos del Boleto");
		groupbox.appendChild(caption);
		
		/*	Columna 1	*/
		column = new Column();
		column.setAlign("right");
		columns.appendChild(column);
		/*	Columna 2	*/
		column = new Column();
		columns.appendChild(column);
		/*	Columna 3	*/
		column = new Column();
		column.setAlign("right");
		columns.appendChild(column);
		/*	Columna 4	*/
		column = new Column();
		columns.appendChild(column);
		
		grid.appendChild(columns);
		
		row = new Row();		
		label = new Label("NUMERO BOLETO :");
		row.appendChild(label);		
		text = new Textbox(ventaOriginal.getNumeroBoleto());
		text.setReadonly(true);
		text.setWidth("80px");
		text.setStyle("font-size:11px !important");
		row.appendChild(text);		
		label = new Label("FECHA VIAJE :");
		row.appendChild(label);		
		text = new Textbox(ventaOriginal.getFechaPartida()==null?"":Util.DatetoString(ventaOriginal.getFechaPartida(), Constantes.DATE_FORMAT));
		text.setReadonly(true);
		text.setWidth("80px");
		text.setStyle("font-size:11px !important");
		row.appendChild(text);		
		rows.appendChild(row);
		
		row = new Row();
		row.setSpans("1,3");
		label = new Label("PASAJERO :");
		row.appendChild(label);
		text = new Textbox(ventaOriginal.getPasajero().toString());
		text.setReadonly(true);
		text.setWidth("314px");
		text.setStyle("font-size:11px !important");
		row.appendChild(text);		
		rows.appendChild(row);
		
		row = new Row();		
		label = new Label("RUTA :");
		row.appendChild(label);		
		text = new Textbox(ventaOriginal.getRuta().getOrigen()+" - " + ventaOriginal.getRuta().getDestino());
		text.setReadonly(true);
		text.setWidth("100px");
		row.appendChild(text);		
		label = new Label("IMPORTE :");
		row.appendChild(label);		
		text = new Textbox(Util.toNumberFormat(ventaOriginal.getImportePagado(), 2));
		text.setReadonly(true);
		text.setWidth("80px");
		text.setStyle("font-size:11px !important");
		row.appendChild(text);		
		rows.appendChild(row);
		
		row=new Row();
		row.setSpans("1,4");
		label = new Label("MOTIVO ANULACIÓN (*) :");
		row.appendChild(label);		
		final Textbox txtMotivoAnulacion = new Textbox();
		txtMotivoAnulacion.setWidth("314px");
		txtMotivoAnulacion.setMultiline(true);
		txtMotivoAnulacion.setRows(3);
		txtMotivoAnulacion.setMaxlength(255);
		txtMotivoAnulacion.setStyle("font-size:11px !important");
		row.appendChild(txtMotivoAnulacion);		
		rows.appendChild(row);
		
		
		grid.appendChild(rows);
		groupbox.appendChild(grid);
		win.appendChild(groupbox);
		
		grid = new Grid();
		columns = new Columns();
		column = new Column();
		column.setAlign("center");
		columns.appendChild(column);
		column = new Column();
		column.setAlign("center");
		columns.appendChild(column);
		grid.appendChild(columns);
		rows = new Rows();
		row = new Row();
		
		Button button = new Button("Continuar", "resources/mp_anular.png");
		button.setClass("btnCommandM");
		button.addEventListener(Events.ON_CLICK, new EventListener<Event>() {
			@Override
			public void onEvent(Event e){
				if(txtMotivoAnulacion.getText().trim().isEmpty()){
					DlgMessage.information(Messages.getString("WndLiquidacionDiariaVentas.information.noMotivoAnulacion"),txtMotivoAnulacion);
					return;
				}
				Messagebox.show(Messages.getString("WndLiquidacionDiariaVentas.information.confirmarAnulacion"), DlgMessage.NOMBREAPLICACION, DlgMessage.BTN_YESNO, Messagebox.QUESTION, new EventListener<Event>() {
					@Override
					public void onEvent(Event e){
						try{
							if(e.getName().equals("onYes")){
								realizarAnulacion(ventaOriginal, wndAnular,txtMotivoAnulacion.getText().trim().toUpperCase());				
							}
						}catch(Exception ex){
							ex.printStackTrace();
							DlgMessage.information(this.getClass().getSimpleName()+" "+ex.getMessage());
						}
					}
				});
			}
		});
		button.setHeight("28px");
		button.setWidth("100px");
		row.appendChild(button);
		button = new Button("Cancelar", "resources/mp_cancelarEnabled.png");
		button.setClass("btnCommandM");
		button.addEventListener(Events.ON_CLICK, new EventListener<Event>() {
			@Override
			public void onEvent(Event e){
				win.onClose();
			}
		});
		button.setHeight("28px");
		button.setWidth("100px");
		button.setFocus(true);
		row.appendChild(button);
		
		rows.appendChild(row);
		
		grid.appendChild(rows);
		win.appendChild(grid);
		return win;
	}
	
	private void realizarAnulacion(VentaPasaje ventaOriginal, Window wndAnular, String motivo)throws Exception{
		int result=Constantes.FAILURE;
		
		/*Valida si es una venta del pool - 14/11/2016 - jabanto*/
		if(ventaOriginal.getServicio()!=null && (ventaOriginal.getServicio().getId().intValue()==Constantes.ID_SERVICIO_POOL_CRUZDELSUR ||
											    ventaOriginal.getServicio().getId().intValue()==Constantes.ID_SERVICIO_POOL_EXCLUCIVA)){
			
			/*Valida el operador*/
			if(ventaOriginal.getServicio().getId().intValue()==Constantes.ID_SERVICIO_POOL_CRUZDELSUR){
				/*Realiza la anulacion del boleto en el WS de cruz de sur*/
				WSCruzdelsur.anularBoleto(ventaOriginal.getNumeroBoletoAnterior());
			}else if (ventaOriginal.getServicio().getId().intValue()==Constantes.ID_SERVICIO_POOL_EXCLUCIVA){
				/*Realiza la anulacion del boleto con la API de Excluciva*/
				RESTCiva.anularBoleto(ventaOriginal.getNumeroBoleto());
			}
		}
		
		ventaOriginal.setTarifa(0.0);
		ventaOriginal.setRecargo(0.0);
		ventaOriginal.setDescuento(0.0);
		ventaOriginal.setImportePagado(0.0);
		ventaOriginal.setAcuenta(0.0);
		ventaOriginal.setImportePagado(0.0);
		ventaOriginal.setTarifaEquibalente(ventaOriginal.getTarifaEquibalente()!=null?.00:null);
		ventaOriginal.setDescuentoEquibalente(ventaOriginal.getDescuentoEquibalente()!=null?.00:null);
		ventaOriginal.setImportePagadoEquibalente(ventaOriginal.getImportePagadoEquibalente()!=null?.00:null);
		ventaOriginal.setTipoMovimiento(new TipoMovimiento(Constantes.ID_TIPMOV_ANULACION));
		ventaOriginal.setObservaciones(motivo);
		UtilData.auditarRegistro(ventaOriginal, true, usuario, Executions.getCurrent());
		VentaPasaje notaCredito= ServiceLocator.getVentaPasajesManager().anularMovimiento(ventaOriginal,false);
		if(notaCredito!=null){
			WSFE.sendNota(notaCredito);
		}
		result=Constantes.CORRECT;
		if(result==Constantes.CORRECT){
			DlgMessage.information(Messages.getString("WndLiquidacionDiariaVentas.information.exitoAnulacion"));
			wndAnular.onClose();
			onBuscarVentas();
		}
	}
	
	public void previoDetalle(Boolean isPrevio) throws Exception{
		
		if(lbxVentas.getItems().size()>0){
			XI++;
			final WndIFrame iFrame = new WndIFrame();
			String agencia="",counter="";
			if(cmbAgencia.getSelectedIndex()>0)
				agencia=(((Agencia)cmbAgencia.getSelectedItem().getValue()).getId().toString());
			if(cmbCounter.getSelectedIndex()>0)
				counter=(((Usuario)cmbCounter.getSelectedItem().getValue()).getLogin());
			String nameFile="DETLIQ-"+agencia+"-"+counter+".txt";//+"-"+XI;
			String rangoFechas=dtbxFechaInicio.getText()+" AL "+dtbxFechaFin.getText();
			
			ArrayList<VentaPasaje> lstVentas= new ArrayList<VentaPasaje>();  
			for(Listitem item: lbxVentas.getItems()){
				VentaPasaje ventaPasaje=item.getValue();
				
				lstVentas.add(ventaPasaje);
			}
			
			Double totalEfectivo=dblbxTotal.getValue()!=null?dblbxTotal.getValue():0.00;
			Double totalEfectivoDolares = dbxTotalDolares.getValue()!=null?dbxTotalDolares.getValue():0.00;
			
			File file =CreateDocument.creaDetalleLiquidacion(lstVentas, nameFile, cmbAgencia.getText().trim(), cmbCounter.getText().trim(), rangoFechas,totalEfectivo, totalEfectivoDolares);
//			String src = Constantes.URL_DIRECTORY_DETALLE_LIQUIDACION+nameFile;
			String src = Constantes.URL_DIRECTORY_DETALLE_LIQUIDACION+Constantes.CLAVE_PAHT+nameFile;
						
			if(isPrevio==true){//Vista Preliminar
				iFrame.setSrc(src);
				iFrame.setWidth("1080");
				iFrame.setheight("600");
				iFrame.loadiframe();
				iFrame.oThisWindow.setTitle("::: PREVIO - DETALLE LIQUIDACIÓN DE VENTAS :::");
				iFrame.oThisWindow.setClosable(true);
				iFrame.btnCerrar.setVisible(false);
				this.appendChild(iFrame);
				iFrame.setMode("modal");
			}else{//Imprimir
				if(getUsuarioHardware().getPrintApplet().intValue()==Constantes.TRUE_VALUE){
					Window win = (Window)Executions.createComponents("imprimir.zul", null, null);
					win.setAttribute("formato", WndImprimir.FORMAT_DETALLE_LIQUIDACION);
					win.setAttribute("msg", "Imprimiendo el Detallado de Ventas...");
					win.setAttribute("urlDocumento", src);
					win.doPopup();
				}else{
					/*Descarga el archivo*/
					Util.descargarArchivo(file);
				}
				
			}
		}
		
		
	}
	
	private void onLoadTipoMovimiento(){
		for(String tipoMovimiento : getTipoMovimiento()){
			Comboitem comboitem = new Comboitem(tipoMovimiento);
			cmbTipoMovimiento.appendChild(comboitem);
		}
		cmbTipoMovimiento.setSelectedIndex(0);		
	}

	
	private void ventanaCambioFormaPago(VentaPasaje ventaPasaje) throws Exception{
		wndCambioformaPago = windowsCambioFormaPago(ventaPasaje);
		this.appendChild(wndCambioformaPago);
		wndCambioformaPago.setMode("modal");
	}
	
	@SuppressWarnings("deprecation")
	private  Window windowsCambioFormaPago(final VentaPasaje ventaPasaje) throws Exception {
		
		final Window window = new Window();	
		window.setWidth("600px");
		window.setBorder(true);
		window.setTitle("::::CAMBIAR LA FORMA DE PAGO:::");
		Separator separator=new Separator();
		
		separator=new Separator();
		separator.setHeight("8px");
		window.appendChild(separator);
		
		Hbox hbox=new Hbox();
		hbox.setAlign("center");
		separator=new Separator();
		separator.setWidth("8px");
		hbox.appendChild(separator);
		Label  label =  new Label(ventaPasaje.getTipoMovimiento().getDenominacion());
		label.setStyle("color:red !important;font-size:12px !important;font-weight: bold;");
		hbox.appendChild(label);
		window.appendChild(hbox);
		window.appendChild(new Separator());
		hbox=new Hbox();
		hbox.setAlign("center");
		separator=new Separator();
		separator.setWidth("8px");
		hbox.appendChild(separator);
		label =  new Label();
		label.setValue("BOLETO :");
		hbox.appendChild(label);
		final Label lblBoleto=new Label();
		lblBoleto.setStyle("color:blue !important;font-size:12px !important");
		lblBoleto.setValue(ventaPasaje.getNumeroBoleto());
		hbox.appendChild(lblBoleto);
		
		separator=new Separator();
		separator.setWidth("15px");
		hbox.appendChild(separator);
		
		label =  new Label();
		label.setValue("BOLETO REF :");
		hbox.appendChild(label);
		final Label lblBoletoReferencial=new Label();
		lblBoletoReferencial.setStyle("color:blue !important;font-size:12px !important");
		lblBoletoReferencial.setValue(ventaPasaje.getNumeroBoletoAnterior()!=null?ventaPasaje.getNumeroBoletoAnterior():"");
		hbox.appendChild(lblBoletoReferencial);
		
		
		separator=new Separator();
		separator.setWidth("15px");
		hbox.appendChild(separator);
		
		label =  new Label();
		label.setValue("NRO. CONTROL :");
		hbox.appendChild(label);
		final Label lblNumeroControl=new Label();
		lblNumeroControl.setStyle("color:blue !important;font-size:12px !important");
		lblNumeroControl.setValue(ventaPasaje.getNumeroControl());
		hbox.appendChild(lblNumeroControl);
		
		
		window.appendChild(hbox);
				
		separator=new Separator();
		separator.setHeight("8px");
		window.appendChild(separator);
		
		
		Groupbox groupbox=new Groupbox();
		Caption caption=new Caption("DATOS DEL PASAJERO");
		caption.setStyle("color: #ffffff;");
		groupbox.appendChild(caption);
		groupbox.setClosable(false);
		groupbox.setMold("3d");
		
		Grid grid = new Grid();
		Columns columns=new Columns();
		Column column=new Column();column.setWidth("100px");column.setAlign("right");
		columns.appendChild(column);
		column=new Column();column.setWidth("130px");
		columns.appendChild(column);
		column=new Column();column.setWidth("110px");column.setAlign("right");
		columns.appendChild(column);
		column=new Column();
		columns.appendChild(column);
		grid.appendChild(columns);
		
		Rows rows = new Rows();
		Row row = new Row();
		row.setSpans("1,4");
		label =  new Label();
		label.setValue("PASAJERO :");
		row.appendChild(label);
		final Label lblNombres=new Label();
		lblNombres.setStyle("color:blue;font-size:11px !important");
		lblNombres.setValue(ventaPasaje.getPasajero().toString());
		row.appendChild(lblNombres);
		rows.appendChild(row);
		
		row = new Row();
		label =  new Label();
		label.setValue("NRO ASIENTO :");
		row.appendChild(label);
		final Label lblNumeroAsiento=new Label();
		lblNumeroAsiento.setStyle("color:blue;font-size:12px !important");
		lblNumeroAsiento.setValue(ventaPasaje.getNumeroAsiento()!=null?ventaPasaje.getNumeroAsiento().toString():"");
		row.appendChild(lblNumeroAsiento);
		rows.appendChild(row);
		label =  new Label();
		label.setValue("IMPORTE :");
		row.appendChild(label);
		final Label lblImporteCobrado=new Label();
		lblImporteCobrado.setStyle("color:blue;font-size:12px !important");
		lblImporteCobrado.setValue(Util.toNumberFormat(ventaPasaje.getImportePagado(), 2));
		row.appendChild(lblImporteCobrado);
		rows.appendChild(row);
		
		row = new Row();
		label =  new Label();
		label.setValue("ORIGEN :");
		row.appendChild(label);
		final Label lblOrigen=new Label();
		lblOrigen.setStyle("color:blue;font-size:11px !important");
		row.appendChild(lblOrigen);
		lblOrigen.setValue(ventaPasaje.getRuta().getOrigen());
		rows.appendChild(row);
		label =  new Label();
		label.setValue("DESTINO :");
		row.appendChild(label);
		final Label lblDestino=new Label();
		lblDestino.setStyle("color:blue;font-size:11px !important");
		lblDestino.setValue(ventaPasaje.getRuta().getDestino());
		row.appendChild(lblDestino);
		rows.appendChild(row);
		
		row = new Row();
		label =  new Label();
		label.setValue("FECHA PARTIDA :");
		row.appendChild(label);
		final Label lblFechaPartida=new Label();
		lblFechaPartida.setStyle("color:blue;font-size:12px !important");
		row.appendChild(lblFechaPartida);
		lblFechaPartida.setValue(ventaPasaje.getFechaPartida()!=null?Constantes.FORMAT_DATE.format(ventaPasaje.getFechaPartida()):"");
		rows.appendChild(row);
		label =  new Label();
		label.setValue("HORA PARTIDA :");
		row.appendChild(label);
		final Label lblHoraPartida=new Label();
		lblHoraPartida.setStyle("color:blue;font-size:11px !important");
		row.appendChild(lblHoraPartida);
		lblHoraPartida.setValue(ventaPasaje.getHoraPartida()!=null?ventaPasaje.getHoraPartida():"");
		rows.appendChild(row);
		
		row = new Row();
		label =  new Label();
		label.setValue("COMPROBANTE :");
		row.appendChild(label);
		final Label lblTipoComprobante=new Label();
		lblTipoComprobante.setStyle("color:blue;font-size:11px !important");
		lblTipoComprobante.setValue(ventaPasaje.getTipoComprobante().getDenominacion());
		row.appendChild(lblTipoComprobante);
		rows.appendChild(row);
		label =  new Label();
		label.setValue("FORMA PAGO :");
		row.appendChild(label);
		final Label lblFormaPago=new Label();
		lblFormaPago.setStyle("color:blue;font-size:11px !important");
		lblFormaPago.setValue(ventaPasaje.getFormaPago().getDenominacion());
		row.appendChild(lblFormaPago);
		rows.appendChild(row);
				
		row = new Row();
		label =  new Label();
		label.setValue("TIPO FORMA PAGO :");
		row.appendChild(label);
		final Combobox cmbTipoFormaPago=new Combobox();
		cmbTipoFormaPago.setWidth("125px");
		cmbTipoFormaPago.setReadonly(true);
		
		row.appendChild(cmbTipoFormaPago);
		rows.appendChild(row);
		label =  new Label();
		label.setValue("OPERADOR TARJETA :");
		row.appendChild(label);
		final Combobox cmbOperadorTarjeta=new Combobox();
		cmbOperadorTarjeta.setReadonly(true);
		UtilData.cargarDataCombo(cmbOperadorTarjeta, OperadorTarjetaCredito.class, false);
		row.appendChild(cmbOperadorTarjeta);
		rows.appendChild(row);
		
		row = new Row();
//		row.setSpans("1,4");
		label =  new Label();
		label.setValue("TARJETA CREDITO :");
		row.appendChild(label);
		final Combobox cmbTarjetaCredito=new Combobox();
		cmbTarjetaCredito.setReadonly(true);
		cmbTarjetaCredito.setWidth("125px");
		UtilData.cargarGenericData(cmbTarjetaCredito, false);
		row.appendChild(cmbTarjetaCredito);
		rows.appendChild(row);
		label =  new Label();
		label.setValue("NRO OPERACIÓN :");
		row.appendChild(label);
		final Textbox txtNumeroOperacion=new Textbox();
		txtNumeroOperacion.setWidth("105px");
		txtNumeroOperacion.setText(ventaPasaje.getNumeroOperacionBancaria()!=null?ventaPasaje.getNumeroOperacionBancaria():"");
		txtNumeroOperacion.setStyle("font-size:11px !important");
		row.appendChild(txtNumeroOperacion);
		rows.appendChild(row);
		
		grid.appendChild(rows);
		
		hbox=new Hbox();
		hbox.setAlign("center");
		Div div=new Div();
		div.setAlign("center");
		div.setWidth(window.getWidth());
		Toolbar toolbar=new Toolbar();
		Button tbbCancelar=new Button("Cancelar", "/resources/mp_cerrar.png");
		tbbCancelar.setStyle("font-size:12px !important");
//		tbbCancelar.setWidth("120px");
		tbbCancelar.setAutodisable("self");
		//tbbCancelar.setMold("trendy");
		tbbCancelar.setClass("btnCommandL");
		hbox.appendChild(tbbCancelar);
		
		separator=new Separator();
		separator.setWidth("10px");
		hbox.appendChild(separator);
		
		Button tbbAceptar=new Button("Aceptar", "/resources/mp_aceptarEnabled.png");
		tbbAceptar.setStyle("font-size:12px !important");
//		tbbAceptar.setWidth("120px");
		tbbAceptar.setAutodisable("self");
		//tbbAceptar.setMold("trendy");
		tbbAceptar.setClass("btnCommandL");
		hbox.appendChild(tbbAceptar);
		
		div.appendChild(hbox);
		toolbar.appendChild(div);

		groupbox.appendChild(grid);
		window.appendChild(groupbox);
		window.appendChild(toolbar);
		
		
		/*Carga los tipos de forma de pago*/
		TreeMap<String, Object> criteriosBusqueda = new TreeMap<String, Object>();
		criteriosBusqueda.put("formaPago.id", Constantes.ID_FORPAG_CONTADO);
		List<String> criteriosOrdenar = new ArrayList<String>();
		criteriosOrdenar.add("denominacion");
		List<TipoFormaPago> lstTipoFormasPago = ServiceLocator.getTipoFormaPagoManager().buscarPorX(criteriosBusqueda, criteriosOrdenar);
		UtilData.cargarGenericData(cmbTipoFormaPago, false);
		for(TipoFormaPago tipoFormaPago : lstTipoFormasPago){
			Comboitem item = new Comboitem();
			item.setLabel(tipoFormaPago.getDenominacion());
			item.setValue(tipoFormaPago);
			cmbTipoFormaPago.appendChild(item);
		}
		Util.seleccionarValorItemCombo(TipoFormaPago.class, cmbTipoFormaPago, ventaPasaje.getTipoFormaPago().getId());
		if(ventaPasaje.getTipoFormaPago().getId().intValue()==Constantes.ID_TIPFORPAG_EFECTIVO){
			cmbOperadorTarjeta.setDisabled(true);
			cmbTarjetaCredito.setDisabled(true);
			txtNumeroOperacion.setDisabled(true);
		}else if (ventaPasaje.getTipoFormaPago().getId().intValue()==Constantes.ID_TIPFORPAG_TRANSFERENCIA){
			cmbOperadorTarjeta.setDisabled(true);
			cmbTarjetaCredito.setDisabled(true);
			txtNumeroOperacion.setDisabled(false);
		}else if(ventaPasaje.getTipoFormaPago().getId().intValue()==Constantes.ID_TIPFORPAG_TARJETA){
			Util.seleccionarValorItemCombo(OperadorTarjetaCredito.class, cmbOperadorTarjeta, ventaPasaje.getTarjetaCredito().getOperadorTarjetaCredito().getId());
			cargarTajetasCredito(cmbTarjetaCredito, ventaPasaje.getTarjetaCredito().getOperadorTarjetaCredito().getId());
			Util.seleccionarValorItemCombo(TarjetaCredito.class, cmbTarjetaCredito, ventaPasaje.getTarjetaCredito().getId());
		}
		
		cmbTipoFormaPago.addEventListener(Events.ON_CHANGE, new EventListener<Event>() {
			@Override
			public void onEvent(Event event) throws Exception {
				cmbOperadorTarjeta.setSelectedIndex(0);
				cmbTarjetaCredito.setSelectedIndex(0);
				cmbOperadorTarjeta.setDisabled(true);
				cmbTarjetaCredito.setDisabled(true);
				txtNumeroOperacion.setText("");
				txtNumeroOperacion.setDisabled(true);
				
				if(cmbTipoFormaPago.getSelectedItem().getValue() instanceof TipoFormaPago){
					TipoFormaPago tipoFormaPago=(TipoFormaPago)cmbTipoFormaPago.getSelectedItem().getValue();
					if(tipoFormaPago.getId().intValue()==Constantes.ID_TIPFORPAG_TARJETA){
						cmbOperadorTarjeta.setDisabled(false);
					}else if (tipoFormaPago.getId().intValue()==Constantes.ID_TIPFORPAG_TRANSFERENCIA){
						txtNumeroOperacion.setDisabled(false);
					}
				}
				
			}
		});
		
		cmbOperadorTarjeta.addEventListener(Events.ON_CHANGE, new EventListener<Event>() {
			@Override
			public void onEvent(Event event) throws Exception {
				try {
					cmbTarjetaCredito.getItems().clear();
					cmbTarjetaCredito.setText("");
					cmbTarjetaCredito.setDisabled(true);
					txtNumeroOperacion.setDisabled(true);
					txtNumeroOperacion.setText("");
					UtilData.cargarGenericData(cmbTarjetaCredito, false);					
					if(cmbOperadorTarjeta.getSelectedItem().getValue() instanceof OperadorTarjetaCredito)
						cargarTajetasCredito(cmbTarjetaCredito, ((OperadorTarjetaCredito)cmbOperadorTarjeta.getSelectedItem().getValue()).getId());
					
				} catch (Exception e) {
					e.printStackTrace();
					DlgMessage.error(e.getMessage());
				}
			}
		});
		
		/*CANCELAR*/
		tbbCancelar.addEventListener(Events.ON_CLICK, new EventListener<Event>() {
			@Override
			public void onEvent(Event event) throws Exception {
				window.onClose();
			}
		});
		
		tbbAceptar.addEventListener(Events.ON_CLICK, new EventListener<Event>() {
			@Override
			public void onEvent(Event event) throws Exception {
				if(!(cmbTipoFormaPago.getSelectedItem().getValue() instanceof TipoFormaPago)){
					DlgMessage.information(Messages.getString("WndVentaReserva.information.noTipoFormaPago"),cmbTipoFormaPago);
					return;
				}else if (cmbOperadorTarjeta.isDisabled()==false && !(cmbOperadorTarjeta.getSelectedItem().getValue() instanceof OperadorTarjetaCredito)){
					DlgMessage.information(Messages.getString("WndVentaReserva.information.noOperadorTarjetaCredito"),cmbOperadorTarjeta);
					return;
				}else if (cmbTarjetaCredito.isDisabled()==false && !(cmbTarjetaCredito.getSelectedItem().getValue() instanceof TarjetaCredito)){
					DlgMessage.information(Messages.getString("WndVentaReserva.information.noTarjetaCredito"),cmbTarjetaCredito);
					return;
				}else if (txtNumeroOperacion.isDisabled()==false && txtNumeroOperacion.getText().trim().isEmpty()){
					DlgMessage.information(Messages.getString("WndVentaReserva.information.noNumeroOperacionBancaria"),txtNumeroOperacion);
					return;
				}
								
				Messagebox.show(Messages.getString("WndLiquidacionDiariaVentas.cambioFormapago.question"), DlgMessage.NOMBREAPLICACION, DlgMessage.BTN_YESNO, Messagebox.QUESTION,DlgMessage.BTN_DEFAULT_NO, new EventListener<Event>() {
					@Override
					public void onEvent(Event e) throws Exception {
						if(e.getName().equals("onYes")){
							try{
//								final VentaPasaje ventaPasajeOrg=ventaPasaje;
								
								ventaPasaje.setTipoFormaPago(new TipoFormaPago(((TipoFormaPago)cmbTipoFormaPago.getSelectedItem().getValue()).getId()));
								if(cmbOperadorTarjeta.getSelectedItem().getValue() instanceof OperadorTarjetaCredito)
									ventaPasaje.setOperadorTarjetaCredito(new OperadorTarjetaCredito(((OperadorTarjetaCredito)cmbOperadorTarjeta.getSelectedItem().getValue()).getId()));
								if(cmbTarjetaCredito.getSelectedItem().getValue() instanceof TarjetaCredito)
									ventaPasaje.setTarjetaCredito((new TarjetaCredito(((TarjetaCredito)cmbTarjetaCredito.getSelectedItem().getValue()).getId())));
								if(!(txtNumeroOperacion.getText().trim().isEmpty()))
									ventaPasaje.setNumeroOperacionBancaria(txtNumeroOperacion.getText().trim());
								UtilData.auditarRegistro(ventaPasaje,true, getUsuario(),Executions.getCurrent());
								ServiceLocator.getVentaPasajesManager().actualizar(ventaPasaje);
								
								
//								/*Valida si ya fue transferido a Titan*/
//								if(ventaPasajeOrg.getFechaTransferencia()!=null){
//									//Busca boleto en titan
//									Integer idCondicionBoleto=null;
//									String serieBoleto=ventaPasajeOrg.getNumeroBoleto().split("-")[0].toString();
//									String numeroBoleto=ventaPasajeOrg.getNumeroBoleto().split("-")[1].toString();
//									
//									if(ventaPasajeOrg.getTipoFormaPago().getId().intValue()==Constantes.ID_TIPFORPAG_TARJETA){
//										if(ventaPasajeOrg.getTarjetaCredito().getOperadorTarjetaCredito().getId().intValue()==Constantes.ID_OPETARCRE_VISA)
//											idCondicionBoleto=19; //Tarjeta visa
//										else
//											idCondicionBoleto=21; //Tarjeta Master card
//									}else if(ventaPasajeOrg.getTipoMovimiento().getId().intValue()==Constantes.ID_TIPMOV_PREPAGADO)
//										idCondicionBoleto=8; //Prepagado
//									else if (ventaPasajeOrg.getTipoMovimiento().getId().intValue()==Constantes.ID_TIPMOV_POSTERGACION || ventaPasajeOrg.getTipoMovimiento().getId().intValue()==Constantes.ID_TIPMOV_POSTERGACION_FA)
//										idCondicionBoleto=12; //Postergacion
//									else
//										idCondicionBoleto=2; //Efectivo
//									
//									//Busca el boleto en Titan
//									TitanVentaPasaje titanVentaPasaje=ServiceLocator.getTitanManager().buscarBoletoVentaPasaje(serieBoleto, numeroBoleto, idCondicionBoleto);
//									if(titanVentaPasaje!=null){
//										/**Obtiene la nueva condicion del boleto*/
//										if(ventaPasaje.getTipoFormaPago().getId().intValue()==Constantes.ID_TIPFORPAG_TARJETA){
//											if(ventaPasaje.getOperadorTarjetaCredito().getId().intValue()==Constantes.ID_OPETARCRE_VISA)
//												idCondicionBoleto=19; //Tarjeta visa
//											else
//												idCondicionBoleto=21; //Tarjeta Master card
//										}else if(ventaPasaje.getTipoMovimiento().getId().intValue()==Constantes.ID_TIPMOV_PREPAGADO)
//											idCondicionBoleto=8; //Prepagado
//										else if (ventaPasaje.getTipoMovimiento().getId().intValue()==Constantes.ID_TIPMOV_POSTERGACION || ventaPasaje.getTipoMovimiento().getId().intValue()==Constantes.ID_TIPMOV_POSTERGACION_FA)
//											idCondicionBoleto=12; //Postergacion
//										else
//											idCondicionBoleto=2; //Efectivo
//																				
//										titanVentaPasaje.setIdCondicionBoleto(idCondicionBoleto);
//										if(idCondicionBoleto.intValue()==19 || idCondicionBoleto.intValue()==21)
//											titanVentaPasaje.setIdTarjetas(idCondicionBoleto.intValue()==19?1:2);
//										else 
//											titanVentaPasaje.setIdTarjetas(null);
//										//Actualiza la forma de pago
//										ServiceLocator.getTitanManager().actualizarFormaPago(titanVentaPasaje);
//										//Recalcula la liquidacion
//										TitanLiquidacionTurnoPasaje titanLiquidacionTurnoPasaje=ServiceLocator.getTitanManager().buscarLiquidacionTurnoPasajeByIdLiquidacion(ventaPasaje.getLiquidacion().getId().longValue());
//										if(titanLiquidacionTurnoPasaje!=null){
//											
//										}
//									}
//								}
								
								
								DlgMessage.information(Messages.getString("Generales.information.exitoGuardar"),cmbTipoFormaPago);
								window.onClose();
								onBuscarVentas();
							}catch(Exception exc){
								exc.printStackTrace();
								DlgMessage.information(exc.getMessage());
							}
						}
					}
				});
			}
		});
		return window;
	}
	
	/**
	 * Carga las tarjetas de credito, segun el operador
	 * @param cmbTarjetaCredito			: Combobox en donde se va a cargar las tarjetas de credito.
	 * @param idOperadorTarjetaCredito	: Identificador del OperadorTrajetacredito
	 * @throws Exception
	 */
	private void cargarTajetasCredito(Combobox cmbTarjetaCredito, Integer idOperadorTarjetaCredito )throws Exception{
		TreeMap<String, Object> criteriosBusqueda = new TreeMap<String, Object>();
		criteriosBusqueda.put("operadorTarjetaCredito.id", idOperadorTarjetaCredito);
		List<String> criteriosOrdenar = new ArrayList<String>();
		criteriosOrdenar.add("denominacion");
		List<TarjetaCredito> lstTarjetaCredito = ServiceLocator.getTarjetaCreditoManager().buscarPorX(criteriosBusqueda, criteriosOrdenar);
//		UtilData.cargarGenericData(cmbTarjetaCredito, false);
		for(TarjetaCredito tarjetaCredito : lstTarjetaCredito){
			Comboitem item = new Comboitem();
			item.setLabel(tarjetaCredito.getDenominacion());
			item.setValue(tarjetaCredito);
			cmbTarjetaCredito.appendChild(item);
		}
		cmbTarjetaCredito.setDisabled(false);
	}
}
