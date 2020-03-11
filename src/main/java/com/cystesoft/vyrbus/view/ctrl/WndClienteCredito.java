package com.cystesoft.vyrbus.view.ctrl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TreeMap;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zul.Button;
import org.zkoss.zul.Caption;
import org.zkoss.zul.Column;
import org.zkoss.zul.Columns;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Comboitem;
import org.zkoss.zul.Doublebox;
import org.zkoss.zul.Grid;
import org.zkoss.zul.Hbox;
import org.zkoss.zul.Label;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Row;
import org.zkoss.zul.Rows;
import org.zkoss.zul.Separator;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Toolbarbutton;
import org.zkoss.zul.Window;

import com.cystesoft.vyrbus.model.bean.Cliente;
import com.cystesoft.vyrbus.model.bean.LineaCreditoCliente;
import com.cystesoft.vyrbus.model.bean.Rol;
import com.cystesoft.vyrbus.model.bean.Usuario;
import com.cystesoft.vyrbus.service.exceptions.LineaCreditoAprobadoNullException;
import com.cystesoft.vyrbus.service.exceptions.ObservacionesNullException;
import com.cystesoft.vyrbus.service.exceptions.ReduccionCreditoNoValidaException;
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
public class WndClienteCredito extends WndBase {

	private static final long serialVersionUID = 1L;

	private Textbox txtNumeroRuc;
	private Combobox cmbCliente;
	private Combobox cmbTipoCliente;
	private Listbox listClientesCredito;
	private Button btnBuscarClientes;

//	private Window wndDesactivar = null;
	private Window wndReduccion = null;
	private Window wndHistorial = null;
	
	
	private final String TIPO_CLIENTE_AGENCIA_VIAJES="AGENCIA DE VIAJES";
	private final String TIPO_CLIENTE_CORPORATIVO="CLIENTE CORPORATIVO";
	private final String TIPO_CLIENTE_CANJES="CANJE PUBLICITARIO";
	
	/*
	 * (non-Javadoc)
	 * @see com.tepsa.sisvyr.view.ui.WndBase#initComponents()
	 */
	@Override
	public void initComponents() {
		cmbCliente=(Combobox)this.getFellow("cmbCliente");
		cmbTipoCliente=(Combobox)this.getFellow("cmbTipoCliente");
		listClientesCredito=(Listbox)this.getFellow("listClientesCredito");
		btnBuscarClientes=(Button)this.getFellow("btnBuscarClientes");
		txtNumeroRuc=(Textbox)this.getFellow("txtNumeroRuc");
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.tepsa.sisvyr.view.ui.WndBase#onCreate()
	 */
	@Override
	public void onCreate()throws Exception{
		
		switch (getRol().getId().intValue()) {
		case Constantes.ID_ROL_FUNCIONARIO:
			UtilData.cargarClientesCredito(cmbCliente, true,getUsuario().getId());//Carga solamente clientes del funcionario
			break;
		default:
			UtilData.cargarClientesCredito(cmbCliente, true,null);
			break;
		}
		
		Util.disabledBtnBuscar(false, btnBuscarClientes, accesoConsultar());
		
		/*Carga Tipos de Clientes*/
		
		UtilData.cargarGenericData(cmbTipoCliente, true);
		Comboitem comboitem=new Comboitem();
		comboitem.setLabel("AGENCIA DE VIAJES");
		comboitem.setValue(TIPO_CLIENTE_AGENCIA_VIAJES);
		cmbTipoCliente.appendChild(comboitem);
		comboitem=new Comboitem();
		comboitem.setLabel("CLIENTE CORPORATIVO");
		comboitem.setValue(TIPO_CLIENTE_CORPORATIVO);
		cmbTipoCliente.appendChild(comboitem);
		comboitem=new Comboitem();
		comboitem.setLabel("CANJE PUBLICITARIO");
		comboitem.setValue(TIPO_CLIENTE_CANJES);
		cmbTipoCliente.appendChild(comboitem);
		
		cmbTipoCliente.setSelectedIndex(0);
	}
		
	public void listarclientesCredito() throws Exception{
		Long idCliente=null;
		Integer idFuncionario=null;
		String tipoCliente=null;
		
		if(!(txtNumeroRuc.getText().trim().isEmpty())){
			TreeMap<String, Object>criteriosBusqueda=new TreeMap<String,Object>();
			criteriosBusqueda.put("numeroDocumento", txtNumeroRuc.getText().trim());
			criteriosBusqueda.put("estadoRegistro", Constantes.VALUE_ACTIVO);
			List<Cliente> lstCliente=ServiceLocator.getClienteManager().buscarPorX(criteriosBusqueda, null);
			if(lstCliente.size()>0){
				Cliente cliente=lstCliente.get(0);
				Util.seleccionarValorItemCombo(Cliente.class, cmbCliente, cliente.getId());
				if(cmbCliente.getSelectedIndex()==-1){
//					cmbCliente.setSelectedIndex(0);
					DlgMessage.information(Messages.getString("WndAsistenteAgencias.information.noHayOcurrenciasCliente"));
					return;
				}
			}
		}
		
		
		if(cmbCliente.getSelectedIndex()>0)
			idCliente=((Cliente)cmbCliente.getSelectedItem().getValue()).getId();
		else{
			if(getRol().getId().intValue()==Constantes.ID_ROL_FUNCIONARIO)
				idFuncionario=getUsuario().getId();//Carga solamente clientes del funcionario
		}
		if(cmbTipoCliente.getSelectedIndex()>0)
			tipoCliente=(String)cmbTipoCliente.getSelectedItem().getValue();
		
		List<LineaCreditoCliente>list =ServiceLocator.getLineaCreditoClienteManager().clientesCredito(idCliente, idFuncionario,tipoCliente);
		Listitem item=null;
		Listcell cell=null;
		int x=0;
		Util.limpiarListbox(listClientesCredito);
		
		for(LineaCreditoCliente  lineaCreditoCliente: list){
			Cliente cliente=lineaCreditoCliente.getCarteraCliente().getCliente();
			Usuario funcionario = lineaCreditoCliente.getCarteraCliente().getUsuario();
			x++;
			item=new Listitem();
//			cell=new Listcell(String.valueOf(x));
//			item.appendChild(cell);
			cell=new Listcell(cliente.getRazonSocial());
			item.appendChild(cell);
			cell=new Listcell(lineaCreditoCliente.getTipoCliente()!=null? lineaCreditoCliente.getTipoCliente(): "NO ESPESIFICADO");
			item.appendChild(cell);
			cell=new Listcell(Util.toNumberFormat(lineaCreditoCliente.getLineaCreditoAprobada(),2));
			cell.setStyle("font-size:11px !important; text-align: right");
			item.appendChild(cell);
			cell=new Listcell(Util.toNumberFormat(lineaCreditoCliente.getLineaCreditoAprobada()*(lineaCreditoCliente.getSobregiro()/100),2));
			cell.setStyle("font-size:11px !important; text-align: right");
			item.appendChild(cell);
			cell=new Listcell(Util.toNumberFormat(lineaCreditoCliente.getSaldo(),2));
			cell.setStyle("font-size:11px !important; text-align: right");
			item.appendChild(cell);
			if(lineaCreditoCliente.getEstadoLineaCredito().equals(Constantes.ESTADOSOL_ACTIVA))
				cell=new Listcell(Constantes.LABEL_ESTADOSOL_ACTIVA_DESC);
			else cell=new Listcell(Constantes.LABEL_ESTADOSOL_INACTIVA_DESC);
			item.appendChild(cell);
			cell=new Listcell(funcionario.getApellidoPaterno()+" "+funcionario.getApellidoMaterno()+", "+funcionario.getNombre());
			item.appendChild(cell);
			
			final Toolbarbutton btnInactivar=new Toolbarbutton();
			btnInactivar.setVisible(false);
			btnInactivar.setWidth("60px");
			btnInactivar.setZindex(lineaCreditoCliente.getId().intValue());
			btnInactivar.setStyle("color:blue; font-style:inherit; font-size:9px !important");
			if(lineaCreditoCliente.getEstadoLineaCredito().equals(Constantes.ESTADOSOL_ACTIVA)){
				btnInactivar.setLabel("Desactivar");
				btnInactivar.setTooltiptext("Desactivar Linea de Crédito");
			}else{
				btnInactivar.setLabel("Reactivar");
				btnInactivar.setTooltiptext("Reactivar Linea de Crédito");
			}
			
			btnInactivar.setDisabled(false);
			
			cell=new Listcell();
			cell.appendChild(btnInactivar);
			item.appendChild(cell);
						
			final Toolbarbutton btnReduccionLc=new Toolbarbutton();
			btnReduccionLc.setVisible(false);
			btnReduccionLc.setId(String.valueOf(x-1));
			btnReduccionLc.setWidth("60px");
			btnReduccionLc.setStyle("color:red; font-style:inherit; font-size:9px !important");
			btnReduccionLc.setLabel("Reduccion");
			btnReduccionLc.setTooltiptext("Reducción Linea de Crédito");
			if(lineaCreditoCliente.getEstadoLineaCredito().equals(Constantes.ESTADOSOL_ACTIVA))
				btnReduccionLc.setDisabled(false);
			else btnReduccionLc.setDisabled(true);			
			btnReduccionLc.setDisabled(false);
			cell.appendChild(btnReduccionLc);
						
			final Toolbarbutton btnHistorial=new Toolbarbutton();
			btnHistorial.setVisible(false);
			btnHistorial.setTooltiptext("Historial");
			btnHistorial.setStyle("color:green; font-style:inherit; font-size:9px !important");
			btnHistorial.setLabel("Historial");
			btnHistorial.setId(String.valueOf(x-1));
			cell.appendChild(btnHistorial);
			item.appendChild(cell);
			
			/*Reducción de Linea de Credito*/
			btnReduccionLc.addEventListener(Events.ON_CLICK,new EventListener<Event>() {
				@Override
				public void onEvent(Event event) throws Exception {
					Listitem item=listClientesCredito.getItemAtIndex(Integer.valueOf(btnReduccionLc.getId()));
					openVentanaReduccionCredito(((LineaCreditoCliente)item.getValue()));
				}
			});
			
			/*Desactivar o Reactivar Linea de Crédito*/
			btnInactivar.addEventListener(Events.ON_CLICK,new EventListener<Event>() {
				@Override
				public void onEvent(Event event) throws Exception {
					Listitem item=listClientesCredito.getItemAtIndex(Integer.valueOf(btnReduccionLc.getId()));
					String estado=((LineaCreditoCliente)item.getValue()).getEstadoLineaCredito();
					Messagebox.show(Messages.getString(estado.equals(Constantes.ESTADOSOL_ACTIVA)?"wndDesactivarCredito.question.DesactivarCredito": "wndReactivarCredito.question.ReactivarCredito"), DlgMessage.NOMBREAPLICACION, DlgMessage.BTN_YESNO, Messagebox.QUESTION, new EventListener<Event>() {
						@Override
						public void onEvent(Event event) throws Exception {
							if(event.getName().equals("onYes")){
								final LineaCreditoCliente lineaCreditoCliente= ServiceLocator.getLineaCreditoClienteManager().buscarPorId(Long.valueOf(btnInactivar.getZindex()));
								if(lineaCreditoCliente.getEstadoLineaCredito().equals(Constantes.ESTADOSOL_ACTIVA)){ //Desactiva la Liena de Crédito
									lineaCreditoCliente.setEstadoLineaCredito(Constantes.ESTADOSOL_INACTIVA);
									lineaCreditoCliente.setFechaInactivacion(Constantes.FORMAT_DATE_TIME_24H.parse(new MyTime().dateServer()));
									UtilData.auditarRegistro(lineaCreditoCliente, true, getUsuario(), Executions.getCurrent());
									ServiceLocator.getLineaCreditoClienteManager().actualizar(lineaCreditoCliente);
									listarclientesCredito();
									
								}else if (lineaCreditoCliente.getEstadoLineaCredito().equals(Constantes.ESTADOSOL_INACTIVA)){ //Reactiva la Liena de Crédito
									Messagebox.show(Messages.getString("wndReactivarCredito.question.ReactivarCredito"), DlgMessage.NOMBREAPLICACION, DlgMessage.BTN_YESNO, Messagebox.QUESTION, new EventListener<Event>() {
										@Override
										public void onEvent(Event event) throws Exception {
											if(event.getName().equals("onYes")){
//												Double saldo=ServiceLocator.getLineaCreditoClienteManager().saldo(creditoCliente.getLineaCreditoAprobada(), creditoCliente.getCarteraCliente().getCliente().getId());
												lineaCreditoCliente.setEstadoLineaCredito(Constantes.ESTADOSOL_ACTIVA);
												lineaCreditoCliente.setFechaActivacion(Constantes.FORMAT_DATE_TIME_24H.parse(new MyTime().dateServer()));
//												creditoCliente.setSaldo(saldo);
												lineaCreditoCliente.setFechaInactivacion(null);
												UtilData.auditarRegistro(lineaCreditoCliente, true, getUsuario(), Executions.getCurrent());
												ServiceLocator.getLineaCreditoClienteManager().actualizar(lineaCreditoCliente);
												
												listarclientesCredito();
											}
										}
									});
								}
							}
						}
					});
				}
			});
			
//			/*Desactivar o Reactivar Linea de Crédito*/
//			btnInactivar.addEventListener(Events.ON_CLICK,new EventListener<Event>() {
//				@Override
//				public void onEvent(Event event) throws Exception {
////					Listitem item=listClientesCredito.getItemAtIndex(Integer.valueOf(btnReduccionLc.getId()));
////					String estado=((LineaCreditoCliente)item.getValue()).getEstadoLineaCredito();
////					Messagebox.show(Messages.getString(estado.equals(Constantes.ESTADOSOL_ACTIVA)?"wndDesactivarCredito.question.DesactivarCredito": "wndReactivarCredito.question.ReactivarCredito"), DlgMessage.NOMBREAPLICACION, DlgMessage.BTN_YESNO, Messagebox.QUESTION, new EventListener<Event>() {
////						@Override
////						public void onEvent(Event event) throws Exception {
////							if(event.getName().equals("onYes")){
//								final LineaCreditoCliente lineaCreditoCliente= ServiceLocator.getLineaCreditoClienteManager().buscarPorId(Long.valueOf(btnInactivar.getZindex()));
//								if(lineaCreditoCliente.getEstadoLineaCredito().equals(Constantes.ESTADOSOL_ACTIVA)){ //Desactiva la Liena de Crédito
//									openVentanaDesactivar(lineaCreditoCliente);
//									
////									creditoCliente.setEstadoLineaCredito(Constantes.ESTADOSOL_INACTIVA);
////									creditoCliente.setFechaInactivacion(Constantes.FORMAT_DATE_TIME_24H.parse(new MyTime().dateServer()));
////									UtilData.auditarRegistro(creditoCliente, true, getUsuario(), Executions.getCurrent());
////									ServiceLocator.getLineaCreditoClienteManager().actualizar(creditoCliente);
////									listarclientesCredito();
//									
//								}else if (lineaCreditoCliente.getEstadoLineaCredito().equals(Constantes.ESTADOSOL_INACTIVA)){ //Reactiva la Liena de Crédito
//									Messagebox.show(Messages.getString("wndReactivarCredito.question.ReactivarCredito"), DlgMessage.NOMBREAPLICACION, DlgMessage.BTN_YESNO, Messagebox.QUESTION, new EventListener<Event>() {
//										@Override
//										public void onEvent(Event event) throws Exception {
//											if(event.getName().equals("onYes")){
////												Double saldo=ServiceLocator.getLineaCreditoClienteManager().saldo(creditoCliente.getLineaCreditoAprobada(), creditoCliente.getCarteraCliente().getCliente().getId());
//												lineaCreditoCliente.setEstadoLineaCredito(Constantes.ESTADOSOL_ACTIVA);
//												lineaCreditoCliente.setFechaActivacion(Constantes.FORMAT_DATE_TIME_24H.parse(new MyTime().dateServer()));
////												creditoCliente.setSaldo(saldo);
//												lineaCreditoCliente.setFechaInactivacion(null);
//												UtilData.auditarRegistro(lineaCreditoCliente, true, getUsuario(), Executions.getCurrent());
//												ServiceLocator.getLineaCreditoClienteManager().actualizar(lineaCreditoCliente);
//												
//												listarclientesCredito();
//											}
//										}
//									});
////								}
//							}
////						}
////					});
//				}
//			});
			
			
			/*historial*/
			btnHistorial.addEventListener(Events.ON_CLICK,new EventListener<Event>() {
				@Override
				public void onEvent(Event event) throws Exception {
					Listitem item= listClientesCredito.getItemAtIndex(Integer.valueOf(event.getTarget().getId()));
					openVentanaHistorial((LineaCreditoCliente)item.getValue());
				}
			});
			
			
			item.setValue(lineaCreditoCliente);
			item.setTooltiptext("N° RUC: "+cliente.getNumeroDocumento());
			
			listClientesCredito.appendChild(item);
			
			
			//Asigna los roles con acceso a visualizar los siguientes botones
			List<Component>lstComponents=new ArrayList<Component>();
			lstComponents.add(btnInactivar);
			lstComponents.add(btnReduccionLc);
//			lstComponents.add(btnHistorial);
			List<Rol>listRolesAcceeso=new ArrayList<Rol>();
			listRolesAcceeso.add(new Rol(Constantes.ID_ROL_FINANZAS));
			listRolesAcceeso.add(new Rol(Constantes.ID_ROL_CREDITOS_COBRANZAS));
			listRolesAcceeso.add(new Rol(Constantes.ID_ROL_SUPER_USUARIO));
			accesoControlsByRol(lstComponents, listRolesAcceeso,true);
		}
	}
	
	/**
	 * Abre venta desactivacion de linea de credito.
	 * @param lineaCreditoCliente
	 */
//	private void openVentanaDesactivar(LineaCreditoCliente lineaCreditoCliente){
//		wndDesactivar = createVentanaDesactivar(lineaCreditoCliente);
//		this.appendChild(wndDesactivar);
//		wndDesactivar.setMode("modal");
//	}
	/**
	 * Crea ventana desactivacion de linea de credito
	 * @param lineaCreditoCliente
	 * @return
	 */
//	private Window createVentanaDesactivar(final LineaCreditoCliente lineaCreditoCliente){
//		Caption caption = null;
//				
//		final Window window = new Window("", "normal", true);
//		window.setWidth("450px");
//		
//		caption = new Caption("DESACTIVACIÓN DE LA LINEA DE CRÉDITO");
//		window.appendChild(caption);
//		
//		Label label=new Label("MOTIVO (*) :");
//		final Textbox txtMotivo =new Textbox();
//		txtMotivo.setWidth("362px");
//		txtMotivo.setMultiline(true);
//		txtMotivo.setRows(3);
//		txtMotivo.setMaxlength(255);
//		
//		Hbox hbox=new Hbox();
//		hbox.setAlign("center");
//		hbox.appendChild(label);
//		hbox.appendChild(txtMotivo);
//		
//		Div div=new Div();
//		div.setAlign("center");
//		Button btnDesactivar=new Button("Desactivar","/resources/mp_anular.png");
//		btnDesactivar.setHeight("25px");
//		div.appendChild(btnDesactivar);
//		window.appendChild(hbox);
//		window.appendChild(new Separator());
//		window.appendChild(div);
//		window.appendChild(new Separator());
//		
//		btnDesactivar.addEventListener(Events.ON_CLICK, new EventListener<Event>() {
//			@Override
//			public void onEvent(Event event) throws Exception {
//				if(txtMotivo.getText().trim().isEmpty()){
//					DlgMessage.information(Messages.getString("wndDesactivarCredito.information.noMotivo"),txtMotivo);
//					return;
//				}
//				Messagebox.show(Messages.getString("wndDesactivarCredito.question.DesactivarCredito"), DlgMessage.NOMBREAPLICACION, DlgMessage.BTN_YESNO, Messagebox.QUESTION, new EventListener<Event>() {
//					@Override
//					public void onEvent(Event event) throws Exception {
//						if(event.getName().equals("onYes")){
//							lineaCreditoCliente.setEstadoLineaCredito(Constantes.ESTADOSOL_INACTIVA);
//							lineaCreditoCliente.setFechaInactivacion(Constantes.FORMAT_DATE_TIME_24H.parse(new MyTime().dateServer()));
//							lineaCreditoCliente.setMotivo(txtMotivo.getText().trim().toUpperCase());
//							UtilData.auditarRegistro(lineaCreditoCliente, true, getUsuario(), Executions.getCurrent());
//							ServiceLocator.getLineaCreditoClienteManager().actualizar(lineaCreditoCliente);
//							listarclientesCredito();
//							window.onClose();
//						}
//					}
//				});
//			}
//		});
//		
//		
//		
//		return window;
//	}
	
	/**
	 * Abre ventana historial de linea del cliente
	 * @param lineaCreditoCliente
	 */
	private void openVentanaHistorial(LineaCreditoCliente lineaCreditoCliente){
		wndHistorial = createVentanaReduccion(lineaCreditoCliente);
		this.appendChild(wndHistorial);
		wndHistorial.setMode("modal");
	}
	/**
	 * Crea venta historial linea de credito
	 * @param lineaCreditoCliente
	 * @return
	 */
//	private Window createVentanaHistorial(final LineaCreditoCliente lineaCreditoCliente){
//		Caption caption = null;
//				
//		final Window window = new Window("", "normal", true);
//		window.setWidth("450px");
//		
//		caption = new Caption("HISTORIAL DE LINEAS DE CREDITO DEL CLIENTE");
//		window.appendChild(caption);
//		
//		
//		return window;
//	}
			
	
	
	private void openVentanaReduccionCredito(LineaCreditoCliente lineaCreditoCliente){
		wndReduccion = createVentanaReduccion(lineaCreditoCliente);
		this.appendChild(wndReduccion);
		wndReduccion.setMode("modal");
	}
	
	/**
	 * Crea la venta para la reducción del crédito
	 * @param lineaCreditoCliente : Class
	 * @return
	 */
	private Window createVentanaReduccion(final LineaCreditoCliente lineaCreditoCliente){
		Caption caption = null;
		Grid grid = new Grid();
		Columns columns = new Columns();
		Column column = null;
		Rows rows = new Rows();
		Row row = null;
		Label label = null;
		final Doublebox dbxNuevoCredito = new Doublebox();
		final Doublebox dbxSobregiro = new Doublebox();
		final Textbox txtObservaciones=new Textbox();
		Textbox txtRuc=new Textbox();
		Textbox txtRazonSocial=new Textbox();
		Separator separator=null;
				
		final Window window = new Window("", "normal", true);
		window.setWidth("450px");
		
		caption = new Caption("REDUCCION LINEA DE CREDITO");
		window.appendChild(caption);
		
		/*RUC CLIENTE*/
		Hbox hbox=new Hbox();
		label = new Label("Ruc");
		label.setStyle("font-size:12px !important");
		txtRuc.setWidth("100px");txtRuc.setReadonly(true);
		txtRuc.setText(lineaCreditoCliente.getCarteraCliente().getCliente().getNumeroDocumento());
		txtRuc.setStyle("font-size:11px !important");
		hbox.appendChild(label);
		separator=new Separator();
		separator.setWidth("51px");
		hbox.appendChild(separator);	
		hbox.appendChild(txtRuc);
		window.appendChild(hbox);
		
		separator=new Separator();
		separator.setHeight("3px");
		hbox=new Hbox(); 
		hbox.appendChild(separator);
		window.appendChild(hbox);
		
		/*RAZON SOCIAL DEL CLIENTE*/
		hbox=new Hbox();
		label = new Label("Razón Social");
		label.setStyle("font-size:12px !important");
		txtRazonSocial.setWidth("250px");txtRazonSocial.setReadonly(true);
		txtRazonSocial.setText(lineaCreditoCliente.getCarteraCliente().getCliente().getRazonSocial());
		hbox.appendChild(label);
		separator=new Separator();
		separator.setWidth("1px");
		hbox.appendChild(separator);
		hbox.appendChild(txtRazonSocial);
		window.appendChild(hbox);
		
		separator=new Separator();
		separator.setHeight("10px");
		hbox=new Hbox(); hbox.appendChild(separator);
		window.appendChild(hbox);
				
		/*NUEVO CREDITO*/
		hbox=new Hbox();
		label = new Label("Nuevo crédito");
		label.setStyle("font-size:12px !important");
		dbxNuevoCredito.setFormat("#,##0.00");
		dbxNuevoCredito.setWidth("95px");
		dbxNuevoCredito.setLocale(Locale.US);
		hbox.appendChild(label);
		hbox.appendChild(dbxNuevoCredito);
		
		/*SOBREGIRO*/
		separator= new Separator();
		separator.setWidth("10px");
		hbox.appendChild(separator);
		
		label=new Label("% Sobregiro");
		label.setStyle("font-size:12px !important");
		dbxSobregiro.setFormat("#,##0.00");
		dbxSobregiro.setWidth("60px");dbxSobregiro.setMaxlength(10);
		dbxSobregiro.setLocale(Locale.US);
		dbxSobregiro.setValue(lineaCreditoCliente.getSobregiro());
		hbox.appendChild(label);
		hbox.appendChild(dbxSobregiro);
		
		window.appendChild(hbox);
		
		separator=new Separator();
		separator.setHeight("3px");
		hbox=new Hbox();
		hbox.appendChild(separator);
		window.appendChild(hbox);
				
		/*MOTIVO*/
		hbox=new Hbox();
		label=new Label("Motivo");
		label.setStyle("font-size:12px !important");
		hbox.appendChild(label);
		
		separator=new Separator();
		separator.setWidth("35px");
		hbox.appendChild(separator);
		
		txtObservaciones.setWidth("300px");
		hbox.appendChild(txtObservaciones);
		window.appendChild(hbox);
		
				
		window.appendChild(new Separator("horizontal"));
		
		column = new Column();
		column.setAlign("right");
		columns.appendChild(column);
		
		column = new Column();
		columns.appendChild(column);
		
		column = new Column();
		column.setAlign("right");
		columns.appendChild(column);
		
		column = new Column();
		columns.appendChild(column);
		
		grid.appendChild(columns);
				
		row = new Row();
		row.appendChild(new  Separator());
		
		Button button=new Button();
		button = new Button("Cancelar", "resources/mp_cancelarEnabled.png");
		button.setClass("btn-vyrbus");
		button.addEventListener(Events.ON_CLICK, new EventListener<Event>() {
			public void onEvent(Event e){
				window.onClose();
			}
		});
		button.setHeight("28px");
		button.setDisabled(false);
		button.setFocus(true);
		row.appendChild(button);
		
		button = new Button("Aceptar", "resources/mp_aceptarEnabled.png");
		button.setClass("btn-vyrbus");
		/*Verifica acceso a Grabar*/
		if(accesoGrabar())
			button.setDisabled(false);
		else
			button.setDisabled(true);
		
		button.addEventListener(Events.ON_CLICK, new EventListener<Event>() {
			public void onEvent(Event e){
				try {
					if(dbxNuevoCredito.getValue()==null || dbxNuevoCredito.getValue()<=0)
						throw new LineaCreditoAprobadoNullException();
					else if(txtObservaciones.getText().trim().isEmpty())
						throw new ObservacionesNullException();
					else if(dbxNuevoCredito.getValue() >= lineaCreditoCliente.getLineaCreditoAprobada())
						throw new ReduccionCreditoNoValidaException();		
					
					Messagebox.show(Messages.getString("wndReduccionCredito.question.confirmaReduccion"), DlgMessage.NOMBREAPLICACION, DlgMessage.BTN_YESNO, Messagebox.QUESTION, new EventListener<Event>() {
						@Override
						public void onEvent(Event event) throws Exception {
							if(event.getName().equals("onYes")){
								Date fecha=Constantes.FORMAT_DATE_TIME_24H.parse(new MyTime().dateServer());
								LineaCreditoCliente creditoCliente=ServiceLocator.getLineaCreditoClienteManager().buscarPorId(lineaCreditoCliente.getId());
								
								/*Inactiva el registro anterior*/
								creditoCliente.setEstadoRegistro(Constantes.VALUE_INACTIVO);
								creditoCliente.setEstadoLineaCredito(Constantes.ESTADOSOL_INACTIVA);
								creditoCliente.setFechaInactivacion(fecha);
								UtilData.auditarRegistro(creditoCliente, true, getUsuario(), Executions.getCurrent());
								ServiceLocator.getLineaCreditoClienteManager().actualizar(creditoCliente);
								
								/*Inserta un nuevo registro con las combios en la Linea de Crédito*/
								Double sobregiro=.00; Double nuevaLineaCredito=.00;
								if (!(dbxSobregiro.getValue()==null))
									sobregiro=dbxSobregiro.getValue();
								if(sobregiro>0){
									nuevaLineaCredito=dbxNuevoCredito.getValue()+(dbxNuevoCredito.getValue() * (sobregiro/100));
								}else nuevaLineaCredito=dbxNuevoCredito.getValue();
								
								Double saldo=ServiceLocator.getLineaCreditoClienteManager().saldobyReduccion(nuevaLineaCredito, creditoCliente.getCarteraCliente().getCliente().getNumeroDocumento());	
								LineaCreditoCliente lineaCreditoCliente= new LineaCreditoCliente();
								lineaCreditoCliente.setCarteraCliente(creditoCliente.getCarteraCliente());
								lineaCreditoCliente.setTipoCobranza(creditoCliente.getTipoCobranza());
								lineaCreditoCliente.setSolicitudClienteCredito(creditoCliente.getSolicitudClienteCredito());
								lineaCreditoCliente.setLineaCreditoSolicitada(creditoCliente.getLineaCreditoSolicitada());
								lineaCreditoCliente.setLineaCreditoAprobada(dbxNuevoCredito.getValue());
								lineaCreditoCliente.setSobregiro(creditoCliente.getSobregiro());
								lineaCreditoCliente.setSaldo(saldo);
								lineaCreditoCliente.setFechaActivacion(fecha);
								lineaCreditoCliente.setFechaCaducidad(creditoCliente.getFechaCaducidad());
								lineaCreditoCliente.setEstadoLineaCredito(Constantes.ESTADOSOL_ACTIVA);
								lineaCreditoCliente.setEsCanje(creditoCliente.getEsCanje());
								lineaCreditoCliente.setEsComisionable(creditoCliente.getEsComisionable());
								lineaCreditoCliente.setMotivo("REDUCCION DE LC A "+lineaCreditoCliente.getLineaCreditoAprobada().toString()+"; "+txtObservaciones.getText().trim().toUpperCase());
								lineaCreditoCliente.setEstadoRegistro(Constantes.VALUE_ACTIVO);
								UtilData.auditarRegistro(lineaCreditoCliente, getUsuario(), Executions.getCurrent());
								ServiceLocator.getLineaCreditoClienteManager().guardar(lineaCreditoCliente);
								
								listarclientesCredito();
								window.onClose();
							}
						}
					});
					
				}catch(ReduccionCreditoNoValidaException rdi){
					DlgMessage.information(Messages.getString("wndReduccionCredito.Information.ReduccionIncorrecta"),dbxNuevoCredito);
				}catch (ObservacionesNullException obsex){
					DlgMessage.information(Messages.getString("wndReduccionCredito.Information.ObservacionesNull"),txtObservaciones);
				}catch(LineaCreditoAprobadoNullException lcanex){	
					DlgMessage.information(Messages.getString("wndReduccionCredito.Information.NuevoCreditoNull"),dbxNuevoCredito);
				} catch (Exception e1) {
					DlgMessage.error(this.getClass().getName()+" "+e1.getMessage());
					e1.printStackTrace();
				}
			}
		});
		button.setHeight("28px");
		button.setFocus(true);
		row.appendChild(button);
		
		rows.appendChild(row);
		
		grid.appendChild(rows);
		window.appendChild(grid);
		
		return window;
	}

	/**
	 * Evento cuando selecciona el tipoCliente
	 * @throws Exception
	 */
	public void onSelectTipoCliente()throws Exception{
		try {
			
			Util.limpiarCombobox(cmbCliente);
			if(cmbTipoCliente.getSelectedIndex()==0){
				switch (getRol().getId().intValue()) {
				case Constantes.ID_ROL_FUNCIONARIO:
					UtilData.cargarClientesCredito(cmbCliente, true,getUsuario().getId());//Carga solamente clientes del funcionario
					break;
				default:
					UtilData.cargarClientesCredito(cmbCliente, true,null);
					break;
				}
			}else{
				List<LineaCreditoCliente> listLineaCredito= new ArrayList<LineaCreditoCliente>();
				switch (getRol().getId().intValue()) {
				case Constantes.ID_ROL_FUNCIONARIO:
					listLineaCredito=ServiceLocator.getLineaCreditoClienteManager().clientesCredito(null, getUsuario().getId(),null);//Carga solamente clientes del funcionario
					break;
				default:
					listLineaCredito=ServiceLocator.getLineaCreditoClienteManager().clientesCredito(null, null,null);
					break;
				}
				
				UtilData.cargarGenericData(cmbCliente, true);
				for(LineaCreditoCliente lineaCreditoCliente :listLineaCredito){
					Cliente cliente=null;
					
					if(cmbTipoCliente.getSelectedItem().getValue().equals(TIPO_CLIENTE_AGENCIA_VIAJES) && lineaCreditoCliente.getTipoCliente().equals(TIPO_CLIENTE_AGENCIA_VIAJES)){
						cliente=lineaCreditoCliente.getCarteraCliente().getCliente();
					}else if(cmbTipoCliente.getSelectedItem().getValue().equals(TIPO_CLIENTE_CORPORATIVO) && lineaCreditoCliente.getTipoCliente().equals(TIPO_CLIENTE_CORPORATIVO)){
						cliente=lineaCreditoCliente.getCarteraCliente().getCliente();
					}else if (cmbTipoCliente.getSelectedItem().getValue().equals(TIPO_CLIENTE_CANJES) && lineaCreditoCliente.getTipoCliente().equals(TIPO_CLIENTE_CANJES)){
						cliente=lineaCreditoCliente.getCarteraCliente().getCliente();
					}
					
					if(cliente!=null){
						Comboitem comboitem=new Comboitem(cliente.getRazonSocial());
						comboitem.setValue(cliente);
						cmbCliente.appendChild(comboitem);
					}
				}
			}
			
			cmbCliente.setSelectedIndex(0);
		} catch (Exception e) {
			e.printStackTrace();
			DlgMessage.error(e.getMessage());
		}
		
	}
}

