/**
 * Proyecto		: SISVYR
 * Sistema		: Sistema de Ventas y Reservas
 * Descripción	: 
 * Autor		: jM
 * Fecha		: 20/04/2012
 */
package com.cystesoft.vyrbus.view.ctrl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
import org.zkoss.zul.Div;
import org.zkoss.zul.Grid;
import org.zkoss.zul.Include;
import org.zkoss.zul.Label;
import org.zkoss.zul.Menu;
import org.zkoss.zul.Menubar;
import org.zkoss.zul.Menuitem;
import org.zkoss.zul.Menupopup;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Row;
import org.zkoss.zul.Rows;
import org.zkoss.zul.Tab;
import org.zkoss.zul.Tabpanel;
import org.zkoss.zul.Tabpanels;
import org.zkoss.zul.Tabs;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

import com.cystesoft.vyrbus.model.bean.Agencia;
import com.cystesoft.vyrbus.model.bean.OpcionMenu;
import com.cystesoft.vyrbus.model.bean.RolOpcionMenu;
import com.cystesoft.vyrbus.model.bean.Usuario;
import com.cystesoft.vyrbus.model.bean.UsuarioHardware;
import com.cystesoft.vyrbus.service.exceptions.EspecieValoradaNotAvailableException;
import com.cystesoft.vyrbus.service.exceptions.PasswordException;
import com.cystesoft.vyrbus.service.locator.ServiceLocator;
import com.cystesoft.vyrbus.service.util.Constantes;
import com.cystesoft.vyrbus.service.util.Messages;
import com.cystesoft.vyrbus.service.util.UtilData;
import com.cystesoft.vyrbus.view.ui.DlgMessage;
import com.cystesoft.vyrbus.view.ui.WndBase;

/**
 *
 * @author jM
 * @since JDK1.6
 */
public class WndPrincipal extends WndBase {
	private static final long serialVersionUID = -1752418538555398323L;

	private Menubar menubarMenu;
	private Label lblUsuario;
	private Label lblAgencia;
	private Label lblPC;
	private Tabs tabsVentana;	
	private Tabpanels tabpanelsVentana;
	
	private Menu menu_N1 = null;
	private Menupopup menupopup_N1 = null;
	private Menu menu_N2 = null;
	private Menupopup menupopup_N2 = null;
	private Menuitem menuitem = null;
	
//	private Menuitem miCambiarPassword;
	
	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.window.WndBaseInterface#onCreate()
	 */
	@SuppressWarnings("deprecation")
	@Override
	public void onCreate() throws Exception {
		try{
			
			
			/*##End Begibin 25/10/2016 - jabanto*/
			/*Para la configuracion de la impresora - 21/05/2015*/
//			boolean executeApplePrint=false;
//			@SuppressWarnings("unchecked")
//			List<String> prints=(List<String>) getDesktop().getSession().getAttribute(Constantes.ATRIBUTO_IMPRESORAS_EQUIPO);
//			if(prints!=null){
//				mostrarVentana("Configurar Impresión", "configPrinters.zul");
//				executeApplePrint=true;
//			}
//			ServletRequest response = (ServletRequest)this.getDesktop().getExecution().getNativeRequest();
//			Integer countPrints=null;
//			if(response.getParameter("prints")!=null){
//				List<String> lstPrints=new ArrayList<String>();
//				countPrints= Integer.valueOf(response.getParameter("prints"));
//				
//				/*Valida si hay impresoras configuradas*/
//				if(countPrints>0){
//					for(int x=0;x<countPrints;x++){
//						String name=response.getParameter("print"+String.valueOf(x+1));
//						
//						if(name!=null)
//							lstPrints.add(name);
//					}
//					executeApplePrint=true;
////					Executions.sendRedirect("http://"+Constantes.SERVER_HOST+"/sisvyr/principal.zul");
////					Executions.sendRedirect("http://www.tepsa.com.pe/sisvyr/principal.zul");
//					Executions.sendRedirect("principal.zul");
//					getDesktop().getSession().setAttribute(Constantes.ATRIBUTO_IMPRESORAS_EQUIPO, lstPrints);
//				}
//			}
			/* ******/
			
			Usuario usuario= (Usuario) getDesktop().getSession().getAttribute(Constantes.ATRIBUTO_USUARIO);
			Agencia agencia = (Agencia) getDesktop().getSession().getAttribute(Constantes.ATRIBUTO_AGENCIA);
			UsuarioHardware usuarioHardware = (UsuarioHardware) getDesktop().getSession().getAttribute(Constantes.ATRIBUTO_USUARIO_HARDWARE);
//			TipoComprobante tipoComprobante = (TipoComprobante) getDesktop().getSession().getAttribute(Constantes.ATRIBUTO_TIPO_COMPROBANTE);
			
			//*********************************************************************
			lblUsuario.setValue(usuario.toString());
			lblAgencia.setValue(agencia.getDenominacion());
			lblPC.setValue(usuarioHardware.getDescripcion());
			
			/*CONFIGURA ACCESOS PARA LOS MENUS*/
			obtenerOpcionesMenu();
			disabledMenus();
			configuraAccesos(usuario);
			
			
			/*End begin 25/10/2016*/
			/*Valida si hay una liquidacion abierta. para luego mostra la venta de "Control se Especie Valorada"*/
//			Liquidacion liquidacion = new Liquidacion();
//			liquidacion=UtilData.estadoLiquidacionUsuario(usuario, agencia);
//			if (!(liquidacion==null) && executeApplePrint==false){
////			if (!(liquidacion==null)){
//				/*Muestra venta "Control Especie Valorada"*/
//				TreeMap<String, Object>criteriosBusqueda = new TreeMap<String, Object>();	
//				ControlEspecieValorada controlEspecieValorada = new ControlEspecieValorada();
//				//Busca especies valoras asignadas al pc 
//				criteriosBusqueda.put("tipoComprobante", tipoComprobante);
//				criteriosBusqueda.put("usuarioHardware", usuarioHardware );
//				criteriosBusqueda.put("estadoRegistro", Constantes.VALUE_ACTIVO);	
//				List<ControlEspecieValorada> list = ServiceLocator.getControlEspecieValoradaManager().buscarPorX(criteriosBusqueda, null);
//					
//				if(agencia.getTipoAgencia().getId().intValue()==Constantes.ID_TIPAGE_TEPSA){
//					if (list.size()>0){ //SI tiene configurado la especie valorada
//						controlEspecieValorada=list.get(0);
//	//					DlgMessage.information(Messages.getString("WndControlEspecieValorada.Information.ControlEspecieValoradaVericar"));
//						
//						WndControlEspecieValorada_LoadInicio wndUpdateEspecieValorada = new WndControlEspecieValorada_LoadInicio(agencia);
//						wndUpdateEspecieValorada.setUsuario(usuario);
//						wndUpdateEspecieValorada.setControlEspecieValorada(controlEspecieValorada);
//	//					wndUpdateEspecieValorada.cargaLisEspeciesValoradas(agencia);
//						appendChild(wndUpdateEspecieValorada);
//							
//						wndUpdateEspecieValorada.setMode("modal");
//						wndUpdateEspecieValorada.setVisible(true);
//						
//					}else{//NO tiene configurado la especie valorada
//						
//						final Usuario usuario_f = usuario;
//						final Agencia agencia_f = agencia;
//						Messagebox.show(Messages.getString("WndControlEspecieValorada.Information.ControlEspecieValoradaNull"), DlgMessage.NOMBREAPLICACION, DlgMessage.BTN_OK, Messagebox.INFORMATION, new EventListener<Event>() {
//							@Override
//							public void onEvent(Event e) throws Exception {
//								if(e.getName().equals("onOK") || e.getName().equals("onClose")){
//									WndControlEspecieValorada_LoadInicio wndUpdateEspecieValorada = new WndControlEspecieValorada_LoadInicio(agencia_f);
//									wndUpdateEspecieValorada.setUsuario(usuario_f);
//	//								wndUpdateEspecieValorada.cargaLisEspeciesValoradas(agencia_f);
//									wndUpdateEspecieValorada.setUsuarioHardware(getUsuarioHardware());
//									wndUpdateEspecieValorada.setTipoComprobante(getTipocomprobante());
//									appendChild(wndUpdateEspecieValorada);
//										
//									wndUpdateEspecieValorada.setMode("modal");
//									wndUpdateEspecieValorada.setVisible(true);
//								}
//							}
//						});
//					}
//				}
//			}
			
			if(usuario.getTipoPassword().intValue()==Usuario.TIPPAS_ALEATORIO){
				final Window win = new Window("", "normal", false);
				win.setHeight("210px");
				win.setWidth("260px");
				Caption caption = new Caption("Cambio de contraseña", "resources/mp_changePassword.png");
				win.appendChild(caption);
				Grid grid = new Grid();
				Columns columns = new Columns();
				Column column = new Column();
				column.setAlign("right");
				columns.appendChild(column);
				column = new Column();
				columns.appendChild(column);
				grid.appendChild(columns);
				Rows rows = new Rows();
				
				Row row = new Row();
				Div div = new Div();
				div.setAlign("center");
				Label label = new Label("Es sistema ha detectado que es necesario que cambie su contraseña.");
				label.setStyle("font-size:12px !important; color:red; font-weight:bold");
				div.appendChild(label);
				row.appendChild(div);
				row.setSpans("2");
				rows.appendChild(row);
				
				row = new Row();
				label = new Label("Usuario :");
				label.setStyle("font-weight:bold; font-size:10px !important;");
				row.appendChild(label);
				final Textbox txtLogin = new Textbox();
				txtLogin.setWidth("100px");
				txtLogin.setMaxlength(15);
				txtLogin.setDisabled(true);
				txtLogin.setText(usuario.getLogin());
				txtLogin.setStyle("text-transform:none");
				row.appendChild(txtLogin);
				rows.appendChild(row);
				
				row = new Row();
				label = new Label("Contraseña actual :");
				label.setStyle("font-weight:bold; font-size:10px !important;");
				row.appendChild(label);
				final Textbox txtPasswordActual = new Textbox();
				txtPasswordActual.setWidth("100px");
				txtPasswordActual.setType("password");
				txtPasswordActual.setMaxlength(15);
				txtPasswordActual.setStyle("text-transform:none");
				row.appendChild(txtPasswordActual);
				rows.appendChild(row);
				
				row = new Row();
				label = new Label("Nueva contraseña :");
				label.setStyle("font-weight:bold; font-size:10px !important;");
				row.appendChild(label);
				final Textbox txtPasswordNuevo = new Textbox();
				txtPasswordNuevo.setWidth("100px");
				txtPasswordNuevo.setType("password");
				txtPasswordNuevo.setMaxlength(15);
				txtPasswordNuevo.setStyle("text-transform:none");
				row.appendChild(txtPasswordNuevo);
				rows.appendChild(row);
				
				row = new Row();
				label = new Label("Confirmar contraseña :");
				label.setStyle("font-weight:bold; font-size:10px !important;");
				row.appendChild(label);
				final Textbox txtConfirmPassword = new Textbox();
				txtConfirmPassword.setWidth("100px");
				txtConfirmPassword.setType("password");
				txtConfirmPassword.setMaxlength(15);
				txtConfirmPassword.setStyle("text-transform:none");
				row.appendChild(txtConfirmPassword);
				rows.appendChild(row);
				
				final Usuario user = usuario;
				row = new Row();
				row.setSpans("2");
				Button button = new Button("Cambiar", "resources/mp_aceptarEnabled.png");
				button.addEventListener(Events.ON_CLICK, new EventListener<Event>() {
					public void onEvent(Event e){
						try{
							if(txtPasswordActual.getText().trim().isEmpty())
								throw new PasswordException(PasswordException.PASSWORD_ACTUAL_NULL);
							else if(txtPasswordNuevo.getText().trim().isEmpty())
								throw new PasswordException(PasswordException.PASSWORD_NUEVO_NULL);
							else if(txtConfirmPassword.getText().trim().isEmpty())
								throw new PasswordException(PasswordException.PASSWORD_CONFIRMATION_NULL);
							else if(!txtPasswordNuevo.getText().trim().equals(txtConfirmPassword.getText().trim()))
								throw new PasswordException(PasswordException.PASSWORD_DIFERENTES);
							else if (txtLogin.getText().trim().equals(txtPasswordNuevo.getText().trim()))
								throw new PasswordException(PasswordException.PASSWORD_IGUAL_LOGIN);
							
							user.setPwdNormal(txtPasswordNuevo.getText().trim());
							user.setTipoPassword(Usuario.TIPPAS_USUARIO);
							UtilData.auditarRegistro(user, true, user, Executions.getCurrent());
							ServiceLocator.getUsuarioManager().actualizar(user);
	
							win.onClose();
						}catch(PasswordException pex){
							if(pex.getTipo().intValue()==PasswordException.PASSWORD_ACTUAL_NULL){
								DlgMessage.information("Debe de ingresar la contraseña actual", txtPasswordActual);
							}else if(pex.getTipo().intValue()==PasswordException.PASSWORD_NUEVO_NULL){
								DlgMessage.information("Debe de ingresar la nueva contraseña", txtPasswordNuevo);
							}else if(pex.getTipo().intValue()==PasswordException.PASSWORD_CONFIRMATION_NULL){
								DlgMessage.information("Vuelva a escribir la nueva contraseña", txtConfirmPassword);
							}else if(pex.getTipo().intValue()==PasswordException.PASSWORD_DIFERENTES){
								DlgMessage.information("Las contraseñas son diferentes vuelva a escribir la nueva contraseña.", txtConfirmPassword);
							}else if(pex.getTipo().intValue()==PasswordException.PASSWORD_IGUAL_LOGIN)
								DlgMessage.information("La contraseña no puede ser igual al nombre de usuario.", txtPasswordNuevo);
						}catch(Exception ex){
							DlgMessage.error(this.getClass().getName()+" "+ex.getMessage());
						}
					}
				});
				button.setHeight("26px");
				row.appendChild(button);
				rows.appendChild(row);
				
				grid.appendChild(rows);
				win.appendChild(grid);
				this.appendChild(win);
				txtPasswordActual.setFocus(true);
				win.doModal();
			}
		
	}catch (EspecieValoradaNotAvailableException env){
		DlgMessage.information(Messages.getString("UtilData.information.notAvailableEspecieValorada"));
	}
}
	
	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.window.ui.IBase#initComponents()
	 */
	@Override
	public void initComponents() {
		menubarMenu=(Menubar)this.getFellow("menubarMenu");
		tabsVentana=(Tabs)this.getFellow("tabsVentana");
		tabpanelsVentana=(Tabpanels)this.getFellow("tabpanelsVentana");
		lblUsuario=(Label)this.getFellow("lblUsuario");
		lblAgencia=(Label)this.getFellow("lblAgencia");
		lblPC=(Label)this.getFellow("lblPC");
	}

	public void configuraAccesos(Usuario usuario) throws Exception{
//		Rol rol = new Rol();
//		rol= UtilData.getRol(usuario);
		
		List<String> criteriosOrdenar = new ArrayList<String>();
		criteriosOrdenar.add("opcionMenu");
		
//		OpcionMenu oopcionMenu = new OpcionMenu();
//		oopcionMenu.setEstadoRegistro(Constantes.VALUE_ACTIVO);
		
		TreeMap<String, Object> criteriosBusqueda = new TreeMap<String, Object>();
		criteriosBusqueda.put("rol", getRol());
		criteriosBusqueda.put("estadoRegistro", Constantes.VALUE_ACTIVO);
		List<RolOpcionMenu> lisRolOpcionMenu = ServiceLocator.getRolOpcionMenuManager().buscarPorX(criteriosBusqueda, criteriosOrdenar);
	
		Map<Integer, String> mapRolOpciones = new HashMap<Integer, String>();
		
		for (int i=0; i< lisRolOpcionMenu.size(); i++){
			mapRolOpciones.put(lisRolOpcionMenu.get(i).getOpcionMenu().getId(), lisRolOpcionMenu.get(i).getOpcionMenu().getNombreObjeto());
		}
		
		for(Component component : menubarMenu.getChildren()){
			if(!(component instanceof Menuitem)){
				for(Component component2 : component.getChildren()){
					if(!(component2 instanceof Menuitem)){
						for(Component component3 : component2.getChildren()){
							if(!(component3 instanceof Menuitem)){
								for(Component component4 : component3.getChildren()){
									if(!(component4 instanceof Menuitem)){
										for(Component component5 : component4.getChildren()){
											if(component5 instanceof Menuitem && mapRolOpciones.containsValue(((Menuitem)component5).getId())){
												((Menuitem) component5).setDisabled(false);
//												((Menuitem) component3).setVisible(true);
											}
										}
										
									}
								}
							}else{
								if(component3 instanceof Menuitem && mapRolOpciones.containsValue(((Menuitem)component3).getId())){
									((Menuitem) component3).setDisabled(false);
//									((Menuitem) component3).setVisible(true);
								}
								//Habilitado solo para desarrollo.
//								else if (((Menuitem)component3).getId().equals("mnmConfigPrinters")){ 
////										|| (((Menuitem)component3).getId().equals("mntAsociarRutasMtcSisvyr"))
////										|| (((Menuitem)component3).getId().equals("mntRptHojaRutaElectronica"))){
//									((Menuitem) component3).setDisabled(false);
//								}
							}
						}
					}
				}
			}
		}
	}
	
	/*
	 * 
	 */
	
	/**
	 * Muestra Ventana Como Ficha (Tab)
	 * @param titulo
	 * @param urlZul
	 */
	public void mostrarVentana(String titulo, String urlZul){		
		Tab oTab = new Tab();
		oTab.setClosable(true);
		Tabpanel oTabpanel = new Tabpanel();
		Include oInclude = new Include();

		oInclude.setSrc(urlZul);
		oInclude.setHeight("100%");
		
		oTab.setLabel(titulo);
		oTabpanel.appendChild(oInclude);
		
		
		tabsVentana.appendChild(oTab);
		tabpanelsVentana.appendChild(oTabpanel);

		oTab.setSelected(true);
	}
	
	public void obtenerOpcionesMenu(){
		try{
			List<OpcionMenu> lstOpcionesMenu = ServiceLocator.getOpcionMenuManager().buscarOpcionesMenu();

			for(int i=0; i<lstOpcionesMenu.size(); i++){
				OpcionMenu opcionMenu = lstOpcionesMenu.get(i);
				if(i==0){
					menu_N1 = new Menu(opcionMenu.getPadre());
					menupopup_N1 = new Menupopup();
					menu_N2 = new Menu(opcionMenu.getHijo());
					menu_N2.setStyle("font-size:12px !important color:white");
					menu_N1.setStyle("font-size:10px !important");
					menupopup_N2 = new Menupopup();
					menuitem = new Menuitem(opcionMenu.getDenominacion());
					menuitem.setId(opcionMenu.getNombreObjeto());
					menuitem.addEventListener(Events.ON_CLICK, addEventListener(opcionMenu));
					menupopup_N2.appendChild(menuitem);
					menu_N2.appendChild(menupopup_N2);
					menupopup_N1.appendChild(menu_N2);
					menu_N1.appendChild(menupopup_N1);
					
					menubarMenu.appendChild(menu_N1);
				}else{
					OpcionMenu pivot = lstOpcionesMenu.get(i-1);
					if(pivot.getIdPadre()!=opcionMenu.getIdPadre()){
						menu_N1 = new Menu(opcionMenu.getPadre());
						menupopup_N1 = new Menupopup();
						menu_N1.appendChild(menupopup_N1);
						menubarMenu.appendChild(menu_N1);
						if(opcionMenu.getIdPadre().intValue()==opcionMenu.getIdHijo()){
//							if(pivot.getIdHijo().intValue()!=opcionMenu.getIdHijo().intValue()){
								menuitem = new Menuitem(opcionMenu.getDenominacion());
								menuitem.setId(opcionMenu.getNombreObjeto());
								menuitem.addEventListener(Events.ON_CLICK, addEventListener(opcionMenu));
								menupopup_N1.appendChild(menuitem);
//							}
						}else{
							menu_N2 = new Menu(opcionMenu.getHijo());
							
						}
					}else{
						if(pivot.getIdHijo().intValue()==opcionMenu.getIdHijo().intValue() && opcionMenu.getIdPadre().intValue()==opcionMenu.getIdHijo().intValue()){
							menuitem = new Menuitem(opcionMenu.getDenominacion());
							menuitem.setId(opcionMenu.getNombreObjeto());
							menuitem.addEventListener(Events.ON_CLICK, addEventListener(opcionMenu));
							menupopup_N1.appendChild(menuitem);
						}else if(pivot.getIdHijo().intValue()==opcionMenu.getIdHijo().intValue()){
							menuitem = new Menuitem(opcionMenu.getDenominacion());
							menuitem.setId(opcionMenu.getNombreObjeto());
							menuitem.addEventListener(Events.ON_CLICK, addEventListener(opcionMenu));
							menupopup_N2.appendChild(menuitem);
						}else{
							menu_N2 = new Menu(opcionMenu.getHijo());
							menupopup_N2 = new Menupopup();
							menuitem = new Menuitem(opcionMenu.getDenominacion());
							menuitem.setId(opcionMenu.getNombreObjeto());
							menuitem.addEventListener(Events.ON_CLICK, addEventListener(opcionMenu));
							menupopup_N2.appendChild(menuitem);
							menu_N2.appendChild(menupopup_N2);
							menupopup_N1.appendChild(menu_N2);
						}
					}
				}	
			}
		}catch(Exception ex){
			ex.printStackTrace();
		}
	}
	
	private EventListener<Event> addEventListener(final OpcionMenu opcionMenu){
		EventListener<Event> ev = new EventListener<Event>() {
			public void onEvent(Event e){
				if(e.getName().equals(Events.ON_CLICK)){
					mostrarVentana(opcionMenu.getDenominacion(), opcionMenu.getUrl());
				}
			}
		};
		return ev;
	}
	
	public void cerrarSesion(){
		Messagebox.show("¿Desea Salir del Sistema?", Messages.getString("System.title"), Messagebox.YES | Messagebox.NO, Messagebox.QUESTION,
				new EventListener<Event>() {

					@Override
					public void onEvent(Event event) throws Exception {
						if(event.getName().equals("onYes")){
							Executions.sendRedirect("./");
							getDesktop().getSession().removeAttribute(Constantes.ATRIBUTO_USUARIO);
							getDesktop().getSession().removeAttribute(Constantes.ATRIBUTO_USUARIO_HARDWARE);
							getDesktop().getSession().removeAttribute(Constantes.ATRIBUTO_CANAL_VENTA);
							getDesktop().getSession().removeAttribute(Constantes.ATRIBUTO_AGENCIA);
							getDesktop().getSession().removeAttribute(Constantes.ATRIBUTO_TIPO_COMPROBANTE);
							getDesktop().getSession().removeAttribute(Constantes.ATRIBUTO_USUARIO_APROBADOR);
							getDesktop().getSession().removeAttribute(Constantes.ATRIBUTO_FECHA_LIQUIDACION);
							getDesktop().getSession().removeAttribute(Constantes.ATRIBUTO_PARAMETROS);
							getDesktop().getSession().removeAttribute(Constantes.ATRIBUTO_ROL);
							getDesktop().getSession().removeAttribute(Constantes.ATRIBUTO_IMPRESORAS_EQUIPO);
							getDesktop().getSession().removeAttribute(Constantes.ATRIBUTO_IMPRESORA);
						}
					}
				}
		);
	}
	
	public void mostrarMantenimiento(){
		Window w = (Window)Executions.createComponents("opcionesMantenimientoVenta.zul", this, null);
		w.doModal();	
	}
	
	public void disabledMenus(){
		for(Component component : menubarMenu.getChildren()){
			if(!(component instanceof Menuitem)){
				for(Component component2 : component.getChildren()){
					if(!(component2 instanceof Menuitem)){
						for(Component component3 : component2.getChildren()){
							if(!(component3 instanceof Menuitem)){
								for(Component component4 : component3.getChildren()){
									if(!(component4 instanceof Menuitem)){
										for(Component component5 : component4.getChildren()){
											if(component5 instanceof Menuitem){
												((Menuitem) component5).setDisabled(true);
//												((Menuitem) component5).setVisible(false);
											}
										}
										
									}
								}
							}else{
								if(component3 instanceof Menuitem){
									((Menuitem) component3).setDisabled(true);
//									((Menuitem) component3).setVisible(false);
								}
							}
						}
					}
				}
			}
		}	
	}
}