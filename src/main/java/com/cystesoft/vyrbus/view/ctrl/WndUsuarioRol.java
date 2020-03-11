package com.cystesoft.vyrbus.view.ctrl;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;

import com.cystesoft.vyrbus.model.bean.Personal;
import com.cystesoft.vyrbus.model.bean.Rol;
import com.cystesoft.vyrbus.model.bean.Usuario;
import com.cystesoft.vyrbus.model.bean.UsuarioRol;
import com.cystesoft.vyrbus.model.bean.UsuarioRolID;
import com.cystesoft.vyrbus.service.exceptions.CancelaGrabacionException;
import com.cystesoft.vyrbus.service.exceptions.EmailNullException;
import com.cystesoft.vyrbus.service.exceptions.NoRemoverRegistroDelListBox;
import com.cystesoft.vyrbus.service.exceptions.RolNullException;
import com.cystesoft.vyrbus.service.exceptions.SelectIndexNullException;
import com.cystesoft.vyrbus.service.exceptions.UsuarioNullException;
import com.cystesoft.vyrbus.service.exceptions.UsuarioRolDuplicadoException;
import com.cystesoft.vyrbus.service.exceptions.UsuarioRolNullException;
import com.cystesoft.vyrbus.service.locator.ServiceLocator;
import com.cystesoft.vyrbus.service.util.Constantes;
import com.cystesoft.vyrbus.service.util.Messages;
import com.cystesoft.vyrbus.service.util.Util;
import com.cystesoft.vyrbus.service.util.UtilData;
import com.cystesoft.vyrbus.view.ui.DlgMessage;
import com.cystesoft.vyrbus.view.ui.WndFiltrarParametros;
import com.cystesoft.vyrbus.view.ui.WndOpcionesMantenimiento;

/**
 * 
 * @author José Abanto.
 * 
 */
public class WndUsuarioRol extends WndOpcionesMantenimiento {
	private static final long serialVersionUID = 6628909148731885949L;

	private Combobox cmbRol;
	private Combobox cmbUsuario;
	
	private UsuarioRol usuarioRol=null;

	private TreeMap<String, Object> criteriosBusqueda = new TreeMap<String, Object>();
	private List<String> criteriosOrdenar = null;
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see com.tepsa.sisvyr.view.ui.IBase#initComponents()
	 */
	@Override
	public void initComponents() {
		cmbRol = (Combobox) this.getFellow("cmbRol");
		cmbUsuario = (Combobox) this.getFellow("cmbUsuario");

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.tepsa.sisvyr.view.ui.IBase#onCreate()
	 */
	@Override
	public void onCreate() throws Exception {
		UtilData.cargarDataCombo(cmbRol, Rol.class, false);
		UtilData.cargarDataCombo(cmbUsuario, Usuario.class, false);

		btnEliminar.setLabel("Desactivar");
		btnEliminar.setTooltiptext("Desactivar el registro seleccionado");
		
		criteriosOrdenar = new ArrayList<String>();
		criteriosOrdenar.add("rol");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.tepsa.sisvyr.view.ui.IOpcionesMantenimiento#onNew()
	 */
	@Override
	public void onNew() throws Exception {
		cmbRol.setSelectedIndex(0);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.tepsa.sisvyr.view.ui.IOpcionesMantenimiento#onSearch()
	 */
	@Override
	public void onSearch() throws Exception {
		criteriosBusqueda = new TreeMap<String, Object>();
		final WndFiltrarParametros oWndFiltrar = new WndFiltrarParametros();

		oWndFiltrar.addParameter("Rol", Rol.class);
		oWndFiltrar.addParameter("Usuario", Usuario.class);

		this.appendChild(oWndFiltrar);
		oWndFiltrar.setMode("modal");
		oWndFiltrar.addEventListener(com.cystesoft.vyrbus.view.ui.Events.ON_FILTER, new EventListener<Event>() {

			@Override
			public void onEvent(Event event) throws Exception {
				String estadoRegistro = Constantes.VALUE_ACTIVO;

				if (oWndFiltrar.getParameterValue("Rol") instanceof Rol){
					Rol rol = new Rol();
					rol = (Rol) oWndFiltrar.getParameterValue("Rol");
					criteriosBusqueda.put("rol", rol );
				}
				if (oWndFiltrar.getParameterValue("Usuario") instanceof Usuario){
					Usuario usuario = new Usuario();
					usuario = (Usuario) oWndFiltrar.getParameterValue("Usuario");
					criteriosBusqueda.put("usuario", usuario );
				}
				criteriosBusqueda.put("estadoRegistro", estadoRegistro);
				ListarUsuarioRol(ServiceLocator.getUsuarioRolManager().buscarPorX(criteriosBusqueda, criteriosOrdenar));
			}
		});

	}

	/*
	 * (non-Javadoc)
	 * @see org.zkoss.zul.Window#onClose()
	 */
	@Override
	public void onClose() {
		closeTabWindow();
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see com.tepsa.sisvyr.view.ui.IOpcionesMantenimiento#onRefresh(int)
	 */
	@Override
	public void onRefresh(int tab) throws Exception {
		if (!(criteriosBusqueda.isEmpty()))
			ListarUsuarioRol(ServiceLocator.getUsuarioRolManager().buscarPorX(criteriosBusqueda, criteriosOrdenar));

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.tepsa.sisvyr.view.ui.IOpcionesMantenimiento#onModify(int)
	 */
	@Override
	public void onModify(int tab) throws Exception {
		if (listboxLista.getSelectedIndex() >= 0)
			mantenimiento();

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.tepsa.sisvyr.view.ui.IOpcionesMantenimiento#onCancel(int)
	 */
	@Override
	public void onCancel(int action) throws Exception {
		// TODO Auto-generated method stub

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.tepsa.sisvyr.view.ui.IOpcionesMantenimiento#onSave(int)
	 */
	@Override
	public void onSave(int action) throws Exception {
		try{
			if (!(cmbRol.getSelectedItem().getValue() instanceof Rol))
				throw new RolNullException();
			else if (cmbUsuario.getSelectedIndex() < 0)
				throw new UsuarioNullException();
			
			Rol rol= cmbRol.getSelectedItem().getValue();
			Usuario usuario =  cmbUsuario.getSelectedItem().getValue();
						
			/* Valida que el usuario tenga el mail configurado  para las alertas que envíe el sistema */
			if(rol.getId().intValue()==Constantes.ID_ROL_GERENCIA_COMERCIAL 
					|| rol.getId().intValue()==Constantes.ID_ROL_JEFE_VENTAS
					|| rol.getId().intValue()==Constantes.ID_ROL_FINANZAS
					|| rol.getId().intValue()==Constantes.ID_ROL_FUNCIONARIO){
				if(usuario.getPersonal()==null)
					throw new EmailNullException();
				else {
					Personal personal= ServiceLocator.getPersonalManager().buscarPorId(usuario.getPersonal().getId());
					if(personal.getEmail().isEmpty())
						throw new EmailNullException();
				}
			}
			
			if (action==ACTION_NEW){
				usuarioRol= null;
				/*Valida si el usuario tiene algún rol asociado y activo*/
//				usuarioRol=ServiceLocator.getUsuarioRolManager().buscarXIdUsuario(usuario.getId());
				if(usuarioRol==null){
					/*para validar si el usuario tubo en algun momento el rol seleccionado y por algun motivo este esta inactivo*/
					usuarioRol= ServiceLocator.getUsuarioRolManager().buscarXidUsuarioAndIdRol(usuario.getId(), rol.getId());
					if(usuarioRol!=null){
						/* Solo actualiza el estado*/
						guardarUsuarioRol(ACTION_MODIFY, usuarioRol);
					}else{
						/* Inserta un nuevo registro*/
						usuarioRol= new UsuarioRol();
						usuarioRol.setRol(rol);
						usuarioRol.setUsuario(usuario);
						guardarUsuarioRol(action,usuarioRol);
					}
				}else
					throw new UsuarioRolNullException();
			}else{
				/*Inactiva el registro actual si es que el rol seleccionado es diferente al actual*/
				if(rol.getId().intValue()!=usuarioRol.getRol().getId().intValue()){
					usuarioRol.setEstadoRegistro(Constantes.VALUE_INACTIVO);
					UtilData.auditarRegistro(usuarioRol, true, getUsuario(), Executions.getCurrent());
					ServiceLocator.getUsuarioRolManager().actualizar(usuarioRol);
					
					/*para validar si el usuario tubo en algun momento el rol seleccionado y por algun motivo este esta inactivo*/
					usuarioRol= ServiceLocator.getUsuarioRolManager().buscarXidUsuarioAndIdRol(usuario.getId(), rol.getId());
					if(usuarioRol!=null){
						/* Solo actualiza el estado*/
						guardarUsuarioRol(action, usuarioRol);
					}else{
						/* Inserta un nuevo registro*/
						usuarioRol= new UsuarioRol();
						usuarioRol.setRol(rol);
						usuarioRol.setUsuario(usuario);
						guardarUsuarioRol(action,usuarioRol);
					}					
				}
				/*Elimina el rol anterior*/
//				UsuarioRolID usuarioRolID=((UsuarioRol)listboxLista.getSelectedItem().getValue()).getUsuarioRolID();
//				ServiceLocator.getUsuarioRolManager().activaInactiva(usuarioRolID, null);
			}
			
		}catch (UsuarioRolNullException urx){
			DlgMessage.information(Messages.getString("WndUsuarioRol.information.usuarioTieneRol"));
			throw new CancelaGrabacionException();
		}catch (EmailNullException emn){
			DlgMessage.information(Messages.getString("WndUsuarioRol.information.noIngresoEmailUsuario"));
			throw new CancelaGrabacionException();
		}catch (UsuarioNullException unex){
			DlgMessage.information(Messages.getString("WndUsuarioRol.information.noIngresoUsuario"),cmbUsuario);
			throw new CancelaGrabacionException();
		}catch (RolNullException rnex){
			DlgMessage.information(Messages.getString("WndUsuarioRol.information.noSeleccionoRol"),cmbRol);
			throw new CancelaGrabacionException();
		}catch(UsuarioRolDuplicadoException udex){
			DlgMessage.information(Messages.getString("WndUsuarioRol.information.usuarioRolDuplicado"),cmbRol);
			throw new CancelaGrabacionException();
		}catch (Exception ex) {
			DlgMessage.error(this.getClass().getName()+" "+ex.getMessage());
			ex.printStackTrace(); throw new CancelaGrabacionException();
		}

	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see com.tepsa.sisvyr.view.ui.IOpcionesMantenimiento#onDelete(int)
	 */
	@Override
	public void onDelete(int tab) throws Exception {
		try{
			/*Desactiva el Usuario*/
			Listitem listitem = listboxLista.getItemAtIndex(listboxLista.getSelectedIndex());
			
			if ( ((UsuarioRol) listitem.getValue()).getEstadoRegistro().equals(Constantes.VALUE_ACTIVO))
				ServiceLocator.getUsuarioRolManager().activaInactiva(((UsuarioRol) listitem.getValue()).getUsuarioRolID(), Constantes.VALUE_INACTIVO);
			else
				ServiceLocator.getUsuarioRolManager().activaInactiva(((UsuarioRol) listitem.getValue()).getUsuarioRolID(), Constantes.VALUE_ACTIVO);
			
			Util.limpiarListbox(listboxLista);
			ListarUsuarioRol(ServiceLocator.getUsuarioRolManager().buscarPorX(criteriosBusqueda, criteriosOrdenar));
			
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
	 * 
	 * @see com.tepsa.sisvyr.view.ui.IOpcionesMantenimiento#onPrint(int)
	 */
	@Override
	public void onPrint(int tab) throws Exception {
		// TODO Auto-generated method stub

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.tepsa.sisvyr.view.ui.IOpcionesMantenimiento#onExport(int)
	 */
	@Override
	public void onExport(int tab) throws Exception {
		// TODO Auto-generated method stub

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.tepsa.sisvyr.view.ui.IOpcionesMantenimiento#onHelp()
	 */
	@Override
	public void onHelp() throws Exception {
		// TODO Auto-generated method stub

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.tepsa.sisvyr.view.ui.IOpcionesMantenimiento#onChangeTab(int)
	 */
	@Override
	public void onChangeTab(int tab) throws Exception {
		if (listboxLista.getSelectedIndex() >= 0)
			mantenimiento();
	}
	
	private void ListarUsuarioRol(List<UsuarioRol> lstUsuariorol){
		Listitem item = null;
		Listcell cell = null;
		listboxLista.getItems().clear();
		
		Integer x = 0;
		for(UsuarioRol usuarioRol : lstUsuariorol){
			x += +1;
			item = new Listitem();
			cell = new Listcell((x.toString()));
			item.appendChild(cell); //Correlativo
			cell = new Listcell(usuarioRol.getRol().getDenominacion());
			item.appendChild(cell);
			cell = new Listcell(usuarioRol.getUsuario().getLogin());
			item.appendChild(cell);
			if (usuarioRol.getEstadoRegistro().equals(Constantes.VALUE_ACTIVO))
				cell = new Listcell(Constantes.LABEL_ACTIVO_DESCP);
			else
				cell = new Listcell(Constantes.LABEL_INACTIVO_DESCP);
			item.appendChild(cell);
					
			item.setValue(usuarioRol);
			listboxLista.appendChild(item);			
			}
	}

	private void guardarUsuarioRol(int action, UsuarioRol usuarioRol) throws Exception{
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
		
		criteriosBusqueda = new TreeMap<String, Object>();
		criteriosBusqueda.put("rol", usuarioRol.getRol());
		criteriosBusqueda.put("usuario", usuarioRol.getUsuario());
		ListarUsuarioRol(ServiceLocator.getUsuarioRolManager().buscarPorX(criteriosBusqueda, criteriosOrdenar));
	}
	
	
	private void mantenimiento() throws Exception{
		Listitem listitem = listboxLista.getItemAtIndex(listboxLista.getSelectedIndex());
	   usuarioRol =  new UsuarioRol();
	   usuarioRol = ServiceLocator.getUsuarioRolManager().buscarXidUsuarioAndIdRol((((UsuarioRol)listitem.getValue()).getUsuario().getId()), (((UsuarioRol)listitem.getValue()).getRol().getId()));

	   Util.seleccionarValorItemCombo(Rol.class, cmbRol, usuarioRol.getRol().getId());
	   Util.seleccionarValorItemCombo(Usuario.class, cmbUsuario, usuarioRol.getUsuario().getId());
	   
	   cmbUsuario.setDisabled(true);
	}
	
	public void onchangeListBox(){
		Listitem lItemUsuario = listboxLista.getItemAtIndex(listboxLista.getSelectedIndex());
		
		if ( ((UsuarioRol) lItemUsuario.getValue()).getEstadoRegistro().equals(Constantes.VALUE_ACTIVO)){
			btnEliminar.setLabel("Desactivar");
			btnEliminar.setImage("resources/toolbar/mp_toolbarEliminar.png");
			btnEliminar.setTooltiptext("Desactivar el Usuario seleccionado");
			questionDelete="¿Desea desactivar el registro seleccionado ";
		}else{
			btnEliminar.setLabel("Reactivar");
//			btnEliminar.setImage("resources/toolbar/Activate.ico");
			btnEliminar.setTooltiptext("Reactivar el registro seleccionado");
			questionDelete="¿Desea reactivar el registro seleccionado";
		}
	}

}
