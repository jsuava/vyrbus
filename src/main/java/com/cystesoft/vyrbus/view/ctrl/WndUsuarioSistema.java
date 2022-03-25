package com.cystesoft.vyrbus.view.ctrl;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zul.Checkbox;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Comboitem;
import org.zkoss.zul.Image;
import org.zkoss.zul.Label;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Radio;
import org.zkoss.zul.Tabbox;
import org.zkoss.zul.Textbox;

import com.cystesoft.vyrbus.model.bean.Agencia;
import com.cystesoft.vyrbus.model.bean.Personal;
import com.cystesoft.vyrbus.model.bean.Rol;
import com.cystesoft.vyrbus.model.bean.TranscarRolUsuario;
import com.cystesoft.vyrbus.model.bean.TranscarUsuarioPersonal;
import com.cystesoft.vyrbus.model.bean.Usuario;
import com.cystesoft.vyrbus.model.bean.UsuarioRol;
import com.cystesoft.vyrbus.model.bean.UsuarioRolID;
import com.cystesoft.vyrbus.service.exceptions.AgenciaNullException;
import com.cystesoft.vyrbus.service.exceptions.ApellidoPaternoNullException;
import com.cystesoft.vyrbus.service.exceptions.CancelaGrabacionException;
import com.cystesoft.vyrbus.service.exceptions.EmailNullException;
import com.cystesoft.vyrbus.service.exceptions.LoginNullException;
import com.cystesoft.vyrbus.service.exceptions.NoRemoverRegistroDelListBox;
import com.cystesoft.vyrbus.service.exceptions.NombresNullException;
import com.cystesoft.vyrbus.service.exceptions.PasswordException;
import com.cystesoft.vyrbus.service.exceptions.RolNullException;
import com.cystesoft.vyrbus.service.exceptions.SelectIndexNullException;
import com.cystesoft.vyrbus.service.exceptions.UsuarioLoginDuplicadoException;
import com.cystesoft.vyrbus.service.locator.ServiceLocator;
import com.cystesoft.vyrbus.service.util.Constantes;
import com.cystesoft.vyrbus.service.util.Messages;
import com.cystesoft.vyrbus.service.util.Util;
import com.cystesoft.vyrbus.service.util.UtilData;
import com.cystesoft.vyrbus.view.ui.DlgMessage;
import com.cystesoft.vyrbus.view.ui.Events;
import com.cystesoft.vyrbus.view.ui.WndFiltrarParametros;
import com.cystesoft.vyrbus.view.ui.WndOpcionesMantenimiento;

/**
 * 
 * @author José Abanto.
 *
 */
public class WndUsuarioSistema extends WndOpcionesMantenimiento {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -2840650005808576854L;
	
	private Combobox cmbPersonal;
	private Combobox cmbAgencia;
	private Textbox	txtApellidoPaterno;
	private Textbox txtApellidoMaterno;
	private Textbox txtNombres;
	private Textbox	txtCodigo;
	private Textbox txtLogin;
	private Textbox txtPassword;
	private Textbox txtConfirmaPassword;
//	private Checkbox cbEstadoUsuario;
	private Radio rdSi;
	private Radio rdNo;
//	private Combobox cmbRol;
	private Textbox txtEmail;
	private Label lblAgencia;
	private Textbox txtEmailInfo;
	private Image imgGeneratePassword;
	private Image imgShowPassword;
	private Listbox lbxRoles;
	private Label lblRolesSeleccionados;
	private Checkbox chbxSoloRolesSeleccionados;
	private Tabbox tabRoles;
	private Checkbox chbxSoloRolesSeleccionadosCarga;
	private Listbox lbxRolesCarga;
	private Label lblRolesSeleccionadosCarga;
	private Checkbox chbxIngresaComprobanteOtraAgencia;
	private Checkbox chbxAutorizaEntregaSinVerificarUsuario;
	
	private Usuario usuarioSistema=null;
	private TranscarUsuarioPersonal transcarUsuarioPersonal= null;
//	private UsuarioRol usuarioRol= null;
	private List<UsuarioRol>lstUsuarioRol=new ArrayList<UsuarioRol>();;
	private TreeMap<String, Object> criteriosBusqueda = new TreeMap<String, Object>();
	private List<String> criteriosOrdenar = null;
	
	public static String base = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ@!#$";

	/*
	 * (non-Javadoc)
	 * @see com.tepsa.sisvyr.view.ui.IBase#initComponents()
	 */
	@Override
	public void initComponents() {
		cmbPersonal = (Combobox) this.getFellow("cmbPersonal");
		cmbAgencia = (Combobox) this.getFellow("cmbAgencia");
		txtApellidoPaterno = (Textbox) this.getFellow("txtApellidoPaterno");
		txtApellidoMaterno = (Textbox) this.getFellow("txtApellidoMaterno");
		txtNombres = (Textbox) this.getFellow("txtNombres");
		txtCodigo = (Textbox) this.getFellow("txtCodigo");
		txtLogin = (Textbox) this.getFellow("txtLogin");
		txtPassword = (Textbox) this.getFellow("txtPassword");
		txtConfirmaPassword = (Textbox) this.getFellow("txtConfirmaPassword");
		rdSi = (Radio) this.getFellow("rdSi");
		rdNo = (Radio) this.getFellow("rdNo");
//		cmbRol=(Combobox)this.getFellow("cmbRol");
		txtEmail=(Textbox)this.getFellow("txtEmail");
		lblAgencia = (Label)this.getFellow("lblAgencia");
		txtEmailInfo = (Textbox)this.getFellow("txtEmailInfo");
		imgGeneratePassword = (Image)this.getFellow("imgGeneratePassword");
		lbxRoles=(Listbox)this.getFellow("lbxRoles");
		lblRolesSeleccionados=(Label)this.getFellow("lblRolesSeleccionados");
		chbxSoloRolesSeleccionados=(Checkbox)this.getFellow("chbxSoloRolesSeleccionados");
		imgShowPassword = (Image)this.getFellow("imgShowPassword");
		tabRoles = (Tabbox)this.getFellow("tabRoles");
		chbxSoloRolesSeleccionadosCarga = (Checkbox)this.getFellow("chbxSoloRolesSeleccionadosCarga");
		lbxRolesCarga = (Listbox)this.getFellow("lbxRolesCarga");
		lblRolesSeleccionadosCarga = (Label)this.getFellow("lblRolesSeleccionadosCarga");
		chbxIngresaComprobanteOtraAgencia = (Checkbox)this.getFellow("chbxIngresaComprobanteOtraAgencia");
		chbxAutorizaEntregaSinVerificarUsuario = (Checkbox)this.getFellow("chbxAutorizaEntregaSinVerificarUsuario");
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.tepsa.sisvyr.view.ui.IBase#onCreate()
	 */
	@Override
	public void onCreate() throws Exception {
		UtilData.cargarDataCombo(cmbAgencia, Agencia.class, false);
		Comboitem cboItem = new Comboitem("NO ES PERSONAL DE EMPRESA");
		cmbPersonal.appendChild(cboItem);
		UtilData.cargarDataCombo(cmbPersonal, Personal.class, false);
//		UtilData.cargarDataCombo(cmbRol, Rol.class, false);
		cargarRoles();
		cargarRolesCarga();
		disabledLbxRoels(true);
		criteriosOrdenar = new ArrayList<String>();
		criteriosOrdenar.add("apellidoPaterno");
		
		btnEliminar.setLabel("Desactivar");
		btnEliminar.setTooltiptext("Desactivar el registro seleccionado");
		
		lblRolesSeleccionados.setValue("0");
		if(getRol().getId()==Constantes.ID_ROL_SUPER_USUARIO)
			imgShowPassword.setVisible(true);
					
		
		lblRolesSeleccionadosCarga.setValue("0");
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.tepsa.sisvyr.view.ui.IOpcionesMantenimiento#onNew()
	 */
	@Override
	public void onNew() throws Exception {
		transcarUsuarioPersonal = null;
		lbxRoles.setDisabled(false);
		lbxRolesCarga.setDisabled(false);
		cmbPersonal.setSelectedIndex(0);
		cmbAgencia.setSelectedIndex(0);
//		cmbRol.setSelectedIndex(0);
		quitaSelecLbxRoles();
		rdSi.setSelected(true);
		txtPassword.setReadonly(true);
		txtConfirmaPassword.setReadonly(true);
		imgGeneratePassword.setVisible(true);
		lblRolesSeleccionados.setValue("0");
		lblRolesSeleccionadosCarga.setValue("0");
		onChangePersonal();
		disabledLbxRoels(false);
		chbxSoloRolesSeleccionados.setChecked(false);
		chbxSoloRolesSeleccionadosCarga.setChecked(false);
		soloRolesSeleccionados();
		tabRoles.setSelectedIndex(0);
	}

	/*
	 * (non-Javadoc)
	 * @see com.tepsa.sisvyr.view.ui.IOpcionesMantenimiento#onSearch()
	 */
	@Override
	public void onSearch() throws Exception {
		criteriosBusqueda = new TreeMap<String, Object>();
		final WndFiltrarParametros oWndFiltrar = new WndFiltrarParametros();
		
		oWndFiltrar.addParameter("1. Agencia", Agencia.class);
		oWndFiltrar.addParameter("2. Apellido Paterno", String.class);
		oWndFiltrar.addParameter("3. Apellido Materno", String.class);
		oWndFiltrar.addParameter("4. Nombres", String.class);
		oWndFiltrar.addParameter("5. Usuario", String.class);

		this.appendChild(oWndFiltrar);
		oWndFiltrar.setMode("modal");
		oWndFiltrar.addEventListener(com.cystesoft.vyrbus.view.ui.Events.ON_FILTER, new EventListener<Event>() {

			@Override
			public void onEvent(Event event) throws Exception {
				Agencia agencia = (Agencia) oWndFiltrar.getParameterValue("1. Agencia");
				String apellidoPaterno = (String) oWndFiltrar.getParameterValue("2. Apellido Paterno");
				String apellidoMaterno = (String) oWndFiltrar.getParameterValue("3. Apellido Materno");
				String nombres = (String) oWndFiltrar.getParameterValue("4. Nombres");
				String login = (String) oWndFiltrar.getParameterValue("5. Usuario");
				
				//String estadoRegistro = Constantes.ACTIVO;
				if(agencia==null){
					criteriosBusqueda.remove("agencia");
//					if (apellidoPaterno.trim().isEmpty()) {
//						criteriosBusqueda.remove("agencia");
//					}else{
//						criteriosBusqueda.put("agencia", agencia);
//					}
				}else
					criteriosBusqueda.put("agencia", agencia);
				
				if (apellidoPaterno.trim().isEmpty()) {
					criteriosBusqueda.remove("apellidoPaterno");
				}else {
					criteriosBusqueda.put("apellidoPaterno", "%" + apellidoPaterno + "%");
				}
				
				if (apellidoMaterno.trim().isEmpty()) {
					criteriosBusqueda.remove("apellidoMaterno");
				}else {criteriosBusqueda.put("apellidoMaterno", "%" + apellidoMaterno + "%");}
				
				if (nombres.trim().isEmpty()) {
					criteriosBusqueda.remove("nombre");
				}else {criteriosBusqueda.put("nombre", "%" + nombres + "%");}
				
				if (login.trim().isEmpty()) {
					criteriosBusqueda.remove("login");
				}else {criteriosBusqueda.put("login", "%" + login + "%");}

				Util.limpiarListbox(listboxLista);
				ListarUsuarios(ServiceLocator.getUsuarioManager().buscarPorX(criteriosBusqueda, criteriosOrdenar));
			}
		});
	}

	/*
	 * (non-Javadoc)
	 * @see com.tepsa.sisvyr.view.ui.IOpcionesMantenimiento#onRefresh(int)
	 */
	@Override
	public void onRefresh(int tab) throws Exception {
		if (!criteriosBusqueda.isEmpty()) {
			Util.limpiarListbox(listboxLista);
			ListarUsuarios(ServiceLocator.getUsuarioManager().buscarPorX(criteriosBusqueda, criteriosOrdenar));					
		}
		
	}

	/*
	 * (non-Javadoc)
	 * @see com.tepsa.sisvyr.view.ui.IOpcionesMantenimiento#onModify(int)
	 */
	@Override
	public void onModify(int tab) throws Exception {
		Long id = new Long(0);
		id = new Long( ((Usuario) listboxLista.getItemAtIndex(listboxLista.getSelectedIndex()).getValue()).getId());
		this.mantenimientoUsuario(id);
		
		txtLogin.setDisabled(true);
//		txtCodigo.setDisabled(true);
		txtPassword.setReadonly(false);
		txtConfirmaPassword.setReadonly(false);
		imgGeneratePassword.setVisible(false);
//		txtEmail.setReadonly(false);
	}

	/*
	 * (non-Javadoc)
	 * @see com.tepsa.sisvyr.view.ui.IOpcionesMantenimiento#onCancel(int)
	 */
	@Override
	public void onCancel(int action) throws Exception {
		// TODO Auto-generated method stub
		quitaSelecLbxRoles();
	}

	/*
	 * (non-Javadoc)
	 * @see com.tepsa.sisvyr.view.ui.IOpcionesMantenimiento#onSave(int)
	 */
	@Override
	public void onSave(int action) throws Exception {
		try{
			if (txtApellidoPaterno.getValue().trim().isEmpty())
				throw new ApellidoPaternoNullException();
			else if (txtNombres.getValue().trim().isEmpty())
				throw new NombresNullException();
			else if (txtLogin.getValue().trim().isEmpty())
				throw new LoginNullException();
			else if (txtPassword.getValue().trim().isEmpty())
				throw new  PasswordException(PasswordException.PASSWORD_NULL);
			else if (txtConfirmaPassword.getValue().trim().isEmpty())
				throw new PasswordException(PasswordException.PASSWORD_CONFIRMATION_NULL); //PasswordConfirmacionException();
			else if (!(txtPassword.getValue().trim().equals(txtConfirmaPassword.getValue().trim())))
				throw new PasswordException(PasswordException.PASSWORD_DIFERENTES); //PasswordDiferentesException();
//			else if(!(cmbRol.getSelectedItem().getValue() instanceof Rol))
//				throw new RolNullException();
			else if(!(cmbPersonal.getSelectedItem().getValue() instanceof Personal) && !(cmbAgencia.getSelectedItem().getValue() instanceof Agencia))
				throw new AgenciaNullException();
			else if(txtEmailInfo.getText().trim().isEmpty() && rdSi.isChecked())
				throw new EmailNullException(EmailNullException.EMAIL_ENVIO_INFO);
			else if(lbxRoles.getSelectedItems().size()==0)
				throw new RolNullException();
			else if (txtLogin.getText().trim().equals(txtPassword.getText().trim()))
				throw new PasswordException(PasswordException.PASSWORD_IGUAL_LOGIN);
			else if (lbxRolesCarga.getSelectedItems().size()==0) {
				tabRoles.setSelectedIndex(1);
				DlgMessage.information(Messages.getString("WndUsuarioRol.information.noSelectRolCarga"));
				throw new CancelaGrabacionException();
			}
			
//			Rol rol= ((Rol)cmbRol.getSelectedItem().getValue());
//			
//			/* Valida que el usuario tenga el mail configurado  para las alertas que envíe el sistema */
//			if(rol.getId().intValue()==Constantes.ID_ROL_GERENCIA_COMERCIAL 
//					|| rol.getId().intValue()==Constantes.ID_ROL_JEFE_VENTAS
//					|| rol.getId().intValue()==Constantes.ID_ROL_FINANZAS
//					|| rol.getId().intValue()==Constantes.ID_ROL_FUNCIONARIO){
//				
//				if(txtEmail.getText().trim().isEmpty())
//					throw new EmailNullException(EmailNullException.EMAIL_PERSONAL);
//			}
			
//			Rol rol= ((Rol)cmbRol.getSelectedItem().getValue());
			
			/* Valida que el usuario tenga el mail configurado  para las alertas que envíe el sistema */
			for(Listitem item:lbxRoles.getSelectedItems()){
				Rol rol=item.getValue();
				
				if(rol.getId().intValue()==Constantes.ID_ROL_GERENCIA_COMERCIAL 
						|| rol.getId().intValue()==Constantes.ID_ROL_JEFE_VENTAS
						|| rol.getId().intValue()==Constantes.ID_ROL_FINANZAS
						|| rol.getId().intValue()==Constantes.ID_ROL_FUNCIONARIO){
					
					if(txtEmail.getText().trim().isEmpty())
						throw new EmailNullException(EmailNullException.EMAIL_PERSONAL);
				}
			}
					
			boolean isNewUserCargo=true;
			if(action==ACTION_NEW){
				usuarioSistema= new Usuario();
				usuarioSistema.setId(null);
				usuarioSistema.setTipoPassword(Usuario.TIPPAS_ALEATORIO);
				
				transcarUsuarioPersonal = new TranscarUsuarioPersonal();				
			}else {
				usuarioSistema.setId(new Integer((textboxId.getValue())));
				
				if(transcarUsuarioPersonal==null) 
					transcarUsuarioPersonal= new TranscarUsuarioPersonal();
				else if(transcarUsuarioPersonal.getId()!=null)
					isNewUserCargo = false;
			}	
			if(cmbPersonal.getSelectedItem().getValue() instanceof Personal){
				Personal personal = cmbPersonal.getSelectedItem().getValue();
				usuarioSistema.setPersonal(personal);
				/* Valida si a cambiado el e-mail*/
				if(!(txtEmail.getText().trim().equals(personal.getEmail()))){
					/* Actualiza el E-Mail del Usuario en la tabla Personal*/
					personal.setEmail(txtEmail.getText().trim().toLowerCase());
					ServiceLocator.getPersonalManager().actualizar(personal);
				}
			}else 
				usuarioSistema.setPersonal(null);
				
			if(cmbAgencia.getSelectedItem().getValue() instanceof Agencia){
				Agencia agencia = new Agencia();
				agencia.setId(((Agencia) cmbAgencia.getSelectedItem().getValue()).getId());
				usuarioSistema.setAgencia(agencia);
			}else 
				usuarioSistema.setAgencia(null);
			
			String estadoRegistro=null;
			if (rdSi.isSelected())
				estadoRegistro=Constantes.VALUE_ACTIVO;
			else
				estadoRegistro=Constantes.VALUE_INACTIVO;	
			
			//instancia para la creacion/actualizacion del usuario en Pasajes
			usuarioSistema.setApellidoPaterno(txtApellidoPaterno.getText().trim().toUpperCase());
			usuarioSistema.setApellidoMaterno(txtApellidoMaterno.getText().trim().toUpperCase());
			usuarioSistema.setNombre(txtNombres.getText().trim().toUpperCase());
			usuarioSistema.setCodigo(txtCodigo.getText().trim().toUpperCase());
			usuarioSistema.setLogin(txtLogin.getText().trim().toLowerCase());
			usuarioSistema.setPwdNormal(txtPassword.getText());
			usuarioSistema.setEstadoRegistro(estadoRegistro);
			usuarioSistema.setEmailInfo(txtEmailInfo.getText().trim());
			usuarioSistema.setTipoSeguridad(usuarioSistema.getTipoSeguridad()==null?Constantes.TRUE_VALUE:usuarioSistema.getTipoSeguridad());
						
			//instancia para la creacion/actualizacion del usuario en Carga
			if(usuarioSistema.getAgencia()==null) {
				DlgMessage.information(Messages.getString("WndUsuarioRol.information.noAgenciaCarga"));
				throw new CancelaGrabacionException();
			}
			Agencia _agencia = ServiceLocator.getAgenciaManager().buscarPorId(usuarioSistema.getAgencia().getId().longValue());
			Integer agenciaIdCarga = ServiceLocator.getTranscarManager().buscarIdAgenciaByCodigoAgenciaPasajes(_agencia.getId().toString());
			if(agenciaIdCarga==null) {
				DlgMessage.information(Messages.getString("WndUsuarioRol.information.noAgenciaCarga"));
				throw new CancelaGrabacionException();
			}
			
			transcarUsuarioPersonal.setAgenciaId(agenciaIdCarga);
			transcarUsuarioPersonal.setNombres(usuarioSistema.getNombre());
			transcarUsuarioPersonal.setApellidoParterno(usuarioSistema.getApellidoPaterno());
			transcarUsuarioPersonal.setApellidoMaterno(usuarioSistema.getApellidoMaterno()!=null?usuarioSistema.getApellidoMaterno():null);
			transcarUsuarioPersonal.setLogin(usuarioSistema.getLogin());
			transcarUsuarioPersonal.setPassword(txtPassword.getText());
			transcarUsuarioPersonal.setEmail(usuarioSistema.getEmailFuncionario()!=null?usuarioSistema.getEmailFuncionario():null);
			transcarUsuarioPersonal.setSexoId(1);
			transcarUsuarioPersonal.setPermiteVentaOtrasAgencias(chbxIngresaComprobanteOtraAgencia.isChecked()?Constantes.TRUE_VALUE:Constantes.FALSE_VALUE);
			transcarUsuarioPersonal.setAutorizaEntregaSinVerificarUsuario(chbxAutorizaEntregaSinVerificarUsuario.isChecked()?Constantes.TRUE_VALUE:Constantes.FALSE_VALUE);
			//Roles seleccionados para el usuario de carga
			String idsRolesUsuarioCarga="";
			for(Listitem itemRolCarga: lbxRolesCarga.getSelectedItems()) {
				TranscarRolUsuario rolUsuario= itemRolCarga.getValue();
				if(idsRolesUsuarioCarga.length()==0)
					idsRolesUsuarioCarga = rolUsuario.getId().toString();
				else
					idsRolesUsuarioCarga = ","+rolUsuario.getId().toString();
			}
			
			
			switch (action) {
				case ACTION_NEW:
					UtilData.auditarRegistro(usuarioSistema,getUsuario(), Executions.getCurrent());
					ServiceLocator.getUsuarioManager().guardar(usuarioSistema);
					textboxId.setText(usuarioSistema.getId().toString());
					break;
				case ACTION_MODIFY:
					UtilData.auditarRegistro(usuarioSistema, true, getUsuario(), Executions.getCurrent());
					ServiceLocator.getUsuarioManager().actualizar(usuarioSistema);
					break;
			}
			
			//Guarda usuario rol
			onSave_usuarioRol(usuarioSistema, action);
			
			//Guarda el usuario en carga
			ServiceLocator.getTranscarManager().guardarUsuarioPersonal(transcarUsuarioPersonal, idsRolesUsuarioCarga, isNewUserCargo);
			
			
			
			/* Realiza la asociación del rol con el usuario*/
//			if (action==ACTION_NEW){
//				usuarioRol= new UsuarioRol();
//				usuarioRol.setRol(rol);
//				usuarioRol.setUsuario(usuarioSistema);
//				guardarUsuarioRol(action,usuarioRol);
//			}else{				
//				if(usuarioRol==null){
//					usuarioRol= new UsuarioRol();
//					usuarioRol.setRol(rol);
//					usuarioRol.setUsuario(usuarioSistema);
//					guardarUsuarioRol(action,usuarioRol);
//				}else{
//					/*Inactiva el registro actual si es que el rol seleccionado es diferente al actual*/
//					if(rol.getId().intValue()!=usuarioRol.getRol().getId().intValue()){
//						usuarioRol.setEstadoRegistro(Constantes.VALUE_INACTIVO);
//						UtilData.auditarRegistro(usuarioRol, true, getUsuario(), Executions.getCurrent());
//						ServiceLocator.getUsuarioRolManager().actualizar(usuarioRol);
//						
//						/*	Para validar si el usuario tuvo en algun momento el rol seleccionado y por algun motivo este esta inactivo	*/
//						usuarioRol= ServiceLocator.getUsuarioRolManager().buscarXidUsuarioAndIdRol(usuarioSistema.getId(), rol.getId());
//						if(usuarioRol!=null){
//							/* Solo actualiza el estado*/
//							guardarUsuarioRol(action, usuarioRol);
//						}else{
//							/* Inserta un nuevo registro*/
//							usuarioRol= new UsuarioRol();
//							usuarioRol.setRol(rol);
//							usuarioRol.setUsuario(usuarioSistema);
//							guardarUsuarioRol(action,usuarioRol);
//						}					
//					}	
//				}
//			}
			
			criteriosBusqueda = new TreeMap<String, Object>();
			criteriosBusqueda.put("id", usuarioSistema.getId());
			lbxRoles.setDisabled(true);
			Util.limpiarListbox(listboxLista);
			ListarUsuarios(ServiceLocator.getUsuarioManager().buscarPorX(criteriosBusqueda, criteriosOrdenar));
		
		}catch(EmailNullException enex){
			if(enex.getTipoEmail().intValue()==EmailNullException.EMAIL_PERSONAL){
				DlgMessage.information(Messages.getString("WndUsuarioRol.information.noIngresoEmailUsuario"));
				throw new CancelaGrabacionException();
			}else{
				DlgMessage.information(Messages.getString("WndUsuarioRol.information.noIngresoEmailEnvioCredenciales"));
				throw new CancelaGrabacionException();
			}
		}catch(RolNullException rne){
			DlgMessage.information(Messages.getString("WndUsuarioRol.information.noSeleccionoRol"));
			throw new CancelaGrabacionException();
		}catch(ApellidoPaternoNullException apnex){
			DlgMessage.information(Messages.getString("WndUsuarioSistema.information.noIngresoApellidoPaterno"),txtApellidoPaterno);
			throw new CancelaGrabacionException();
		}catch(NombresNullException nnex){
			DlgMessage.information(Messages.getString("WndUsuarioSistema.information.noIngresoNombres"),txtNombres);
			throw new CancelaGrabacionException();
		}catch (LoginNullException lnex){
			DlgMessage.information(Messages.getString("WndUsuarioSistema.information.noIngresoLogin"),txtLogin);
			throw new CancelaGrabacionException();
		}catch (PasswordException cp){
			if(cp.getTipo().intValue()==PasswordException.PASSWORD_NULL){
				DlgMessage.information(Messages.getString("WndUsuarioSistema.information.noIngresoPassword"),txtPassword);
				throw new CancelaGrabacionException();
			}else if (cp.getTipo().intValue()==PasswordException.PASSWORD_DIFERENTES){
				DlgMessage.information(Messages.getString("WndUsuarioSistema.information.passwordNoCoinciden"),txtPassword);
				throw new CancelaGrabacionException();
			}else if (cp.getTipo().intValue()==PasswordException.PASSWORD_CONFIRMATION_NULL){
				DlgMessage.information(Messages.getString("WndUsuarioSistema.information.noIngresoPasswordConfirmacion"),txtConfirmaPassword);
				throw new CancelaGrabacionException();
			}else if(cp.getTipo().intValue()==PasswordException.PASSWORD_IGUAL_LOGIN){
				DlgMessage.information("La contraseña no puede ser igual al login.", txtPassword);
				throw new CancelaGrabacionException();
			}
		}catch(UsuarioLoginDuplicadoException uldex){
			DlgMessage.information(Messages.getString("WndUsuarioSistema.information.usuarioDuplicado"));
			txtLogin.setFocus(true);
			throw new CancelaGrabacionException();
		}catch(AgenciaNullException anex){
			DlgMessage.information(Messages.getString("WndUsuarioSistema.information.noSeleccionoAgencia"));
			cmbAgencia.setFocus(true);
			throw new CancelaGrabacionException();
		}catch (Exception ex) {
			DlgMessage.error(this.getClass().getName()+" "+ex.getMessage()); 
			ex.printStackTrace(); throw new CancelaGrabacionException();
		}
	}
	
	
	/*
	 * (non-Javadoc)
	 * @see com.tepsa.sisvyr.view.ui.IOpcionesMantenimiento#onDelete(int)
	 */
	@Override
	public void onDelete(int tab) throws Exception {
		try{
			Long id = (long) 0;
			if (listboxLista.getSelectedIndex() >= 0){
				id = new Long(((Usuario) listboxLista.getItemAtIndex(listboxLista.getSelectedIndex()).getValue()).getId());
			}else{throw new SelectIndexNullException();}
			
			/*Desactiva el Usuario*/
			if ( ((Usuario) listboxLista.getItemAtIndex(listboxLista.getSelectedIndex()).getValue()).getEstadoRegistro().equals(Constantes.VALUE_ACTIVO))
				ServiceLocator.getUsuarioManager().inactivar(id);
			else /*Reactiva el Usuario*/
				ServiceLocator.getUsuarioManager().activar(id);
			
			Util.limpiarListbox(listboxLista);
			ListarUsuarios(ServiceLocator.getUsuarioManager().buscarPorX(criteriosBusqueda, criteriosOrdenar));
			
			limpiarControles();
			habilitaControles(false);
			btnNuevo.setDisabled(false);
			btnBuscar.setDisabled(false);
			btnModificar.setDisabled(true);
			btnCancelar.setDisabled(true);
			btnGuardar.setDisabled(true);
			btnEliminar.setDisabled(true);
			btnImprimir.setDisabled(true);
			btnExportar.setDisabled(true);

			
			/*Evita ser removido el registro del ListBox*/
			throw new NoRemoverRegistroDelListBox();	
			
		}catch (NoRemoverRegistroDelListBox e) {
			throw new NoRemoverRegistroDelListBox();
		}catch (SelectIndexNullException sinex){
			DlgMessage.information(Messages.getString("Generales.information.noSeleccionoUsuario"));
			throw new NoRemoverRegistroDelListBox();
		}
		
	}

	/*
	 * (non-Javadoc)
	 * @see com.tepsa.sisvyr.view.ui.IOpcionesMantenimiento#onPrint(int)
	 */
	@Override
	public void onPrint(int tab) throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onClose() {
		closeTabWindow();
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.tepsa.sisvyr.view.ui.IOpcionesMantenimiento#onExport(int)
	 */
	@Override
	public void onExport(int tab) throws Exception {
		// TODO Auto-generated method stub
		
	}

	/*
	 * (non-Javadoc)
	 * @see com.tepsa.sisvyr.view.ui.IOpcionesMantenimiento#onHelp()
	 */
	@Override
	public void onHelp() throws Exception {
		// TODO Auto-generated method stub
		
	}

	/*
	 * (non-Javadoc)
	 * @see com.tepsa.sisvyr.view.ui.IOpcionesMantenimiento#onChangeTab(int)
	 */
	@Override
	public void onChangeTab(int tab) throws Exception {
		Listitem itList = listboxLista.getItemAtIndex(listboxLista.getSelectedIndex());
		switch (tab) {
			case TAB_LIST:
				break;
			case TAB_MAINTENANCE:
				if (itList != null) {
					this.mantenimientoUsuario(((Usuario) itList.getValue()).getId().longValue());
				}
				break;
		}	
	}

	private void ListarUsuarios(List<Usuario> lstUsuarios){
		if(lstUsuarios.size()>0){
			Listitem item = null;
			Listcell cell = null;
			int x=0;
			for(Usuario usuario : lstUsuarios){
				x++;
				item = new Listitem();
				cell = new Listcell((String.valueOf(x)));
				item.appendChild(cell); //Correlativo
				String apellidoMaterno=usuario.getApellidoMaterno()!=null?usuario.getApellidoMaterno():"";
				cell = new Listcell(usuario.getApellidoPaterno() + " "+ apellidoMaterno +",  "+ usuario.getNombre());
				item.appendChild(cell);
				cell = new Listcell(usuario.getLogin());
				item.appendChild(cell);
				
				if (usuario.getEstadoRegistro().equals(Constantes.VALUE_ACTIVO))
					cell = new Listcell(Constantes.LABEL_ACTIVO_DESCP);
				if (usuario.getEstadoRegistro().equals(Constantes.VALUE_INACTIVO))
					cell = new Listcell(Constantes.LABEL_INACTIVO_DESCP);
				
				item.appendChild(cell);
							
				item.setValue(usuario);
				listboxLista.appendChild(item);			
			}
		}else
			DlgMessage.information(Messages.getString("Generales.information.noDatosEncontrados"));
	}
	
	
	private void quitaSelecLbxRoles(){
		for(Listitem item:lbxRoles.getItems()){
			item.setSelected(false);
		}
		
		for(Listitem item:lbxRolesCarga.getItems()){
			item.setSelected(false);
		}
	}
	
	private void disabledLbxRoels(Boolean estado){
		for(Listitem item:lbxRoles.getItems()){
			item.setDisabled(estado);
		}
		
		for(Listitem item:lbxRolesCarga.getItems()){
			item.setDisabled(estado);
		}
	}
	
	/**
	 * Recupera datos del usuario para la edicion o consulta.
	 * @param id
	 * @throws Exception
	 */
	private void mantenimientoUsuario(Long id) throws Exception {
		tabRoles.setSelectedIndex(0);
		quitaSelecLbxRoles();
		usuarioSistema = ServiceLocator.getUsuarioManager().buscarPorId(id);
		lstUsuarioRol=ServiceLocator.getUsuarioRolManager().buscarXIdUsuario(id.intValue());
		
		cargarRoles();
		lblRolesSeleccionados.setValue("0");
		if(lstUsuarioRol.size()>0){
			//Realiza la seleccion del los roles que el usuario tenga asignados
			for(UsuarioRol usuarioRol:lstUsuarioRol){
				for(Listitem item:lbxRoles.getItems()){
					Rol rol=(Rol)item.getValue();
					if(rol.getId().intValue()==usuarioRol.getRol().getId().intValue()){
						item.setSelected(true);
//						lbxRoles.selectItem(item);
					}
				}
			}
//			lblRolesSeleccionados.setValue(String.valueOf(lbxRoles.getSelectedItems().size()));
		}
		lblRolesSeleccionados.setValue(String.valueOf(lstUsuarioRol.size()));
		chbxSoloRolesSeleccionados.setChecked(true);		
		
		disabledLbxRoels(btnGuardar.isDisabled());

		txtEmail.setText("");
		textboxId.setText(usuarioSistema.getId().toString());
		if (usuarioSistema.getPersonal()!=null){
			Util.seleccionarValorItemCombo(Personal.class, cmbPersonal, usuarioSistema.getPersonal().getId());
			cmbPersonal.setDisabled(true);
			cmbAgencia.setDisabled(true);
			cmbAgencia.setSelectedIndex(0);
			txtApellidoPaterno.setDisabled(true);
			txtApellidoMaterno.setDisabled(true);
			txtNombres.setDisabled(true);
			txtCodigo.setDisabled(true);
		}else{
			cmbPersonal.setSelectedIndex(0);
			cmbPersonal.setDisabled(true);
			cmbAgencia.setDisabled(false);
			Agencia ag = usuarioSistema.getAgencia();
			if(ag!=null)
				Util.seleccionarValorItemCombo(Agencia.class, cmbAgencia, ag.getId());
			else{
				cmbAgencia.setSelectedIndex(0);
//				cmbPersonal.setDisabled(false);
			}
		}
		
		txtApellidoPaterno.setValue(usuarioSistema.getApellidoPaterno());
		txtApellidoMaterno.setValue(usuarioSistema.getApellidoMaterno());
		txtNombres.setValue(usuarioSistema.getNombre());
		txtCodigo.setValue(usuarioSistema.getCodigo());
		txtEmail.setValue(usuarioSistema.getPersonal()==null?"":usuarioSistema.getPersonal().getEmail());
		txtLogin.setValue(usuarioSistema.getLogin());
		txtPassword.setText(usuarioSistema.getPwdNormal());
		txtConfirmaPassword.setValue(usuarioSistema.getPwdNormal());
		
		if (usuarioSistema.getEstadoRegistro().equals(Constantes.VALUE_ACTIVO))
			rdSi.setSelected(true);
		else
			rdNo.setSelected(true);
		txtEmail.setFocus(true);

		
		//Buscar el usuario en la db de carga
		 transcarUsuarioPersonal = ServiceLocator.getTranscarManager().buscarUsuarioPersonal(usuarioSistema.getLogin());
		 if(transcarUsuarioPersonal!=null) {			 
			 List<TranscarRolUsuario> result = ServiceLocator.getTranscarManager().buscarRolesUsuario(transcarUsuarioPersonal.getId());
			 if(result.size()>0) {
				 TranscarUsuarioPersonal _usuarioPersonal= result.get(0).getTranscarUsuarioPersonal();
				 if(_usuarioPersonal.getAutorizaEntregaSinVerificarUsuario()!=null)
					 chbxAutorizaEntregaSinVerificarUsuario.setChecked(_usuarioPersonal.getAutorizaEntregaSinVerificarUsuario()==Constantes.TRUE_VALUE?true:false);
				 if(_usuarioPersonal.getPermiteVentaOtrasAgencias()!=null)
					 chbxIngresaComprobanteOtraAgencia.setChecked(_usuarioPersonal.getPermiteVentaOtrasAgencias()==Constantes.TRUE_VALUE?true:false);
				 
				 chbxSoloRolesSeleccionadosCarga.setChecked(true);
			 }
			 for(Listitem item: lbxRolesCarga.getItems()) {
				 TranscarRolUsuario rolUsuario= item.getValue();
				 for(TranscarRolUsuario _rolUsuario: result) {
					if(rolUsuario.getId().intValue()==_rolUsuario.getId().intValue())
						item.setSelected(true);
				 }				 
			 }
		 }
		 lblRolesSeleccionadosCarga.setValue(String.valueOf(lbxRolesCarga.getSelectedItems().size()));		 
		 
		 soloRolesSeleccionados();
	}

	/** 
	 * Recupera datos al seleccionar personal.
	 * @throws Exception
	 */
	public void onChangePersonal() throws Exception{
		txtApellidoPaterno.setText("");
		txtApellidoMaterno.setText("");
		txtNombres.setText("");
		txtCodigo.setText("");
		txtEmail.setText("");
		if(cmbPersonal.getSelectedIndex()<0)
			cmbPersonal.setSelectedIndex(0);
				
		if(cmbPersonal.getSelectedItem().getValue() instanceof Personal){
			cmbAgencia.setDisabled(true);
			txtApellidoPaterno.setReadonly(true);
			txtApellidoMaterno.setReadonly(true);
			txtNombres.setReadonly(true);
			txtCodigo.setReadonly(true);
			Personal personal=cmbPersonal.getSelectedItem().getValue();
			txtApellidoPaterno.setValue(personal.getApellidoPaterno());
			txtApellidoMaterno.setValue(personal.getApellidoMaterno());
			txtNombres.setValue(personal.getNombre());
			txtCodigo.setValue(personal.getCodigo());
			txtEmail.setValue(personal.getEmail());
			lblAgencia.setValue("AGENCIA :");
			/*	Sugiere el nombre de usuario	*/
			if(!(txtLogin.isDisabled()))
				txtLogin.setValue(((Personal) cmbPersonal.getSelectedItem().getValue()).getNombre().substring(0, 1) + ((Personal) cmbPersonal.getSelectedItem().getValue()).getApellidoPaterno());
			
			txtEmail.setFocus(true);
//			txtEmail.setReadonly(false);
		}else{
			cmbAgencia.setDisabled(false);
			txtApellidoPaterno.setReadonly(false);
			txtApellidoMaterno.setReadonly(false);
			txtNombres.setReadonly(false);
			txtCodigo.setReadonly(false);
			lblAgencia.setValue("AGENCIA (*) :");
			txtLogin.setText("");
			cmbAgencia.setFocus(true);
		}
		txtPassword.setText("");
		txtConfirmaPassword.setText("");
		
//		txtEmail.setReadonly(false);
		cmbAgencia.setSelectedIndex(0);
		
		
//		if (cmbPersonal.getSelectedIndex() >= 0){
//			if (cmbPersonal.getSelectedItem().getValue() instanceof Personal){
//				
//				txtApellidoPaterno.setDisabled(true);
//				txtApellidoMaterno.setDisabled(true);
//				txtNombres.setDisabled(true);
//				txtEmail.setReadonly(false);
//			}
//		}	
	}
	
	/**
	 * 
	 */
	public void onchangeListBox(){
		Listitem lItemUsuario = listboxLista.getItemAtIndex(listboxLista.getSelectedIndex());
		
		if ( ((Usuario) lItemUsuario.getValue()).getEstadoRegistro().equals(Constantes.VALUE_ACTIVO)){
			btnEliminar.setLabel("Desactivar");
			btnEliminar.setImage("resources/toolbar/mp_toolbarEliminar.png");
			btnEliminar.setTooltiptext("Desactivar el Usuario seleccionado");
			questionDelete="¿Desea desactivar al Usuario: " + ((Usuario)lItemUsuario.getValue()).getLogin();
		}else{
			btnEliminar.setLabel("Reactivar");
			btnEliminar.setImage("/resources/toolbar/mp_toolbarAceptarEnabled.png");
			btnEliminar.setTooltiptext("Reactivar el Usuario seleccionado");
			questionDelete="¿Desea reactivar al Usuario: " + ((Usuario)lItemUsuario.getValue()).getLogin();
		}
	}
	
	/**
	 * Realiza la asociación entre el usuario y los roles.
	 * @param usuario	: Objeto Usuario
	 * @param action	: accion a realizar; nuevo o actualizacion.
	 * @throws Exception
	 */
	private void onSave_usuarioRol(Usuario usuario,int action) throws Exception{
		for(Listitem item:lbxRoles.getSelectedItems()){
			Rol rolSeleccionado=item.getValue();	
			if (action==ACTION_NEW){
				UsuarioRol usuarioRol= new UsuarioRol();
				usuarioRol.setRol(rolSeleccionado);
				usuarioRol.setUsuario(usuarioSistema);
				saveUsuarioRol(action,usuarioRol);
			}else{				
				if(lstUsuarioRol.size()==0){
					UsuarioRol usuarioRol=new UsuarioRol();
					usuarioRol.setRol(rolSeleccionado);
					usuarioRol.setUsuario(usuarioSistema);
					saveUsuarioRol(Constantes.ACTION_NEW,usuarioRol);
				}else{
					/*	Para validar si el usuario tuvo en algun momento el rol seleccionado y por algun motivo este esta inactivo	*/
					UsuarioRol usuarioRol= ServiceLocator.getUsuarioRolManager().buscarXidUsuarioAndIdRol(usuarioSistema.getId(), rolSeleccionado.getId());
					if(usuarioRol!=null && usuarioRol.getEstadoRegistro().equals(Constantes.VALUE_INACTIVO)){
						/* Solo actualiza el estado*/
						usuarioRol.setEstadoRegistro(Constantes.VALUE_ACTIVO);
						saveUsuarioRol(action, usuarioRol);
					}else if(usuarioRol==null){
						/* Inserta un nuevo registro*/
						usuarioRol= new UsuarioRol();
						usuarioRol.setRol(rolSeleccionado);
						usuarioRol.setUsuario(usuarioSistema);
						saveUsuarioRol(Constantes.ACTION_NEW,usuarioRol);
					}
				}
			}
		}
		
		/*Inactiva el registro actual si es que el rol seleccionado es diferente al actual*/
		for(UsuarioRol usuarioRol:lstUsuarioRol){
			UsuarioRol usuarioRolAnular=null;
			for(Listitem itemSelect:lbxRoles.getItems()){
				Rol rol=itemSelect.getValue();
				if(rol.getId().intValue()==usuarioRol.getRol().getId().intValue() && itemSelect.isSelected()==false){
					usuarioRolAnular=new UsuarioRol();
					usuarioRolAnular=usuarioRol;
					break;
				}
			}
			if(usuarioRolAnular!=null){
				usuarioRolAnular.setEstadoRegistro(Constantes.VALUE_INACTIVO);
				UtilData.auditarRegistro(usuarioRolAnular, true, getUsuario(), Executions.getCurrent());
				ServiceLocator.getUsuarioRolManager().actualizar(usuarioRolAnular);
			}
		}
		
		
	}

	/**
	 * Guarda el Usuario rol
	 * @param action		: accion a realizar (nuevo actualizar)
	 * @param usuarioRol	: Objeto UsuarioRol
	 * @throws Exception
	 */
	private void saveUsuarioRol(int action, UsuarioRol usuarioRol) throws Exception{
		UsuarioRolID usuarioRolID = new UsuarioRolID();
		usuarioRolID.setIdRol(usuarioRol.getRol().getId());
		usuarioRolID.setIdUsuario(usuarioRol.getUsuario().getId());
		
		usuarioRol.setUsuarioRolID(usuarioRolID);
		usuarioRol.setRol(usuarioRol.getRol());
		usuarioRol.setUsuario(usuarioRol.getUsuario());
		usuarioRol.setEstadoRegistro(Constantes.VALUE_ACTIVO);
		
		switch (action) {
			case ACTION_NEW:
				UtilData.auditarRegistro(usuarioRol, this.getUsuario(), Executions.getCurrent());
				ServiceLocator.getUsuarioRolManager().guardar(usuarioRol);
				break;

			case ACTION_MODIFY:
				UtilData.auditarRegistro(usuarioRol, true, this.getUsuario(), Executions.getCurrent());
				ServiceLocator.getUsuarioRolManager().actualizar(usuarioRol);
				break;
		}
	}
	
	public void generarPassword(){
//		int lengthPassword = 8;
//		String password = "";
//		int longitud = base.length();
//		for(int i=0; i<lengthPassword; i++){
//			int numero = (int)(Math.random()*longitud);
//			String caracter = base.substring(numero, numero+1);
//			password = password+caracter;
//		}
		String passwd = Util.generarPassword();
		txtPassword.setText(passwd);
		txtConfirmaPassword.setText(passwd);
	}

	private void cargarRoles(){
		Util.limpiarListbox(lbxRoles);
		List<Rol>lstRoles=ServiceLocator.getRolManager().buscarPorEstadoRegistro(Constantes.VALUE_ACTIVO, "denominacion");
		
		for(Rol rol:lstRoles){
			Listitem item=new Listitem();
			Listcell cell=null;
			
			cell=new Listcell(rol.getDenominacion());
			item.appendChild(cell);
			
			item.setValue(rol);
			
			lbxRoles.addEventListener(Events.ON_SELECT,new EventListener<Event>() {
				@Override
				public void onEvent(Event event) throws Exception {
					lblRolesSeleccionados.setValue(String.valueOf(lbxRoles.getSelectedItems().size()));
				}
			});
			
			lbxRoles.appendChild(item);
		}
		
	}
	
	/*
	 * Carga los roles de Carga	
	 */
	private void cargarRolesCarga() throws Exception {
		Util.limpiarListbox(lbxRolesCarga);
		List<TranscarRolUsuario> result = ServiceLocator.getTranscarManager().buscarRolesUsuario();
		
		for(TranscarRolUsuario rolUsuario:result){
			Listitem item = new Listitem();
			Listcell cell = new Listcell(rolUsuario.getNombre());
			item.appendChild(cell);	
			item.setValue(rolUsuario);
			
			lbxRolesCarga.addEventListener(Events.ON_SELECT,new EventListener<Event>() {
				@Override
				public void onEvent(Event event) throws Exception {
					lblRolesSeleccionadosCarga.setValue(String.valueOf(lbxRolesCarga.getSelectedItems().size()));
				}
			});
			
			lbxRolesCarga.appendChild(item);
		}
	}
	
	
	public void reiniciarPWD(){
		try{
			TreeMap<String, Object> criteriosBusqueda = new TreeMap<String, Object>();
			criteriosBusqueda.put("tipoPassword", 3);
			List<String>criteriosOrdenar=null;
			List<Usuario> lstUsuarios = ServiceLocator.getUsuarioManager().buscarPorX(criteriosBusqueda, criteriosOrdenar);
			for(int i=0; i<lstUsuarios.size(); i++){
				Usuario user = lstUsuarios.get(i);
				user.setPwdNormal(user.getLogin().trim());
				user.setTipoPassword(0);
//				usuario.setEmailInfo("jsullo@tepsa.com.pe");
				UtilData.auditarRegistro(user, true, getUsuario(), Executions.getCurrent());
				ServiceLocator.getUsuarioManager().actualizar(user);
			}
			
		}catch(Exception ex){
			DlgMessage.error(this.getClass().getName()+" "+ex.getMessage()); 
			ex.printStackTrace();
		}
	}
	
	
	public void soloRolesSeleccionados(){
		if(chbxSoloRolesSeleccionados.isChecked()){
			for(Listitem item:lbxRoles.getItems()){
				item.setVisible(item.isSelected());
			}
		}else{
			for(Listitem item:lbxRoles.getItems()){
				item.setVisible(true);
			}
		}
		
		
		if(chbxSoloRolesSeleccionadosCarga.isChecked()){
			for(Listitem item:lbxRolesCarga.getItems()){
				item.setVisible(item.isSelected());
			}
		}else {
			for(Listitem item:lbxRolesCarga.getItems()){
				item.setVisible(true);
			}
		}
	}
	
	public void soloRolesSeleccionadosCarga(){
		if(chbxSoloRolesSeleccionadosCarga.isChecked()){
			for(Listitem item:lbxRolesCarga.getItems()){
				item.setVisible(item.isSelected());
			}
		}else{
			for(Listitem item:lbxRolesCarga.getItems()){
				item.setVisible(true);
			}
		}
	}
	
	public void mostrarPassword(){		
		if ( !(txtPassword.getText().equals("") || txtLogin.getText().equals("")) ){
			String clave = "";																	  				
			clave = txtPassword.getText();						
			DlgMessage.information("El Password es : "+clave);			
		}
	}
}
