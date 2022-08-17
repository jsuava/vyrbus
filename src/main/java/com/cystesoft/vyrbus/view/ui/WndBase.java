/**
 * Proyecto		: SISVYR
 * Sistema		: Sistema de Ventas y Reservas
 * Descripción	:
 * Autor		: jM
 * Fecha		: 20/04/2012
 */
package com.cystesoft.vyrbus.view.ui;

import java.util.List;

import org.apache.log4j.Logger;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.ext.AfterCompose;
import org.zkoss.zk.ui.ext.BeforeCompose;
import org.zkoss.zul.Button;
import org.zkoss.zul.Checkbox;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.Listfoot;
import org.zkoss.zul.Listheader;
import org.zkoss.zul.Radio;
import org.zkoss.zul.Tabpanel;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Toolbarbutton;
import org.zkoss.zul.Window;

import com.cystesoft.vyrbus.model.bean.Agencia;
import com.cystesoft.vyrbus.model.bean.Rol;
import com.cystesoft.vyrbus.model.bean.TipoComprobante;
import com.cystesoft.vyrbus.model.bean.Usuario;
import com.cystesoft.vyrbus.model.bean.UsuarioHardware;
import com.cystesoft.vyrbus.service.util.Constantes;
import com.cystesoft.vyrbus.view.ctrl.WndLogin;

/**
 * Clase base para todas las Ventanas,
 * hereda de {@link Window}
 * @author jose
 * @since JDK1.6
 */
public abstract class WndBase extends Window implements IBase, BeforeCompose, AfterCompose {
	private static final long serialVersionUID = -7379604840270938796L;
	protected final Logger log;
	public Long txtid=null;

	/**
	 * Constructor
	 * @throws Exception
	 */
	public WndBase() {
		super();
		log = Logger.getLogger(getClass());
	}

	/**
	 * Constructor
	 *
	 * @param title
	 * @param border
	 * @param closable
	 */
	public WndBase(String title, String border, boolean closable) {
		super(title, border, closable);
		log = Logger.getLogger(getClass());
	}

	/**
	 * Metodo ZK Framework que se ejecuta antes de componer la página.
	 */
	@Override
	public void beforeCompose() {
		/*Validacion de inicio de Sesión de Usuario*/
		Usuario usuario = null;
		usuario = (Usuario) this.getDesktop().getSession().getAttribute(Constantes.ATRIBUTO_USUARIO);
		if (usuario==null){
			Class<?> oclass =  getClass();
			if (!(oclass.equals(WndLogin.class)))
				Executions.sendRedirect("login.zul");
		}
	}

	public UsuarioHardware getUsuarioHardware(){
		return (UsuarioHardware) this.getDesktop().getSession().getAttribute(Constantes.ATRIBUTO_USUARIO_HARDWARE);
	}

	public Agencia getAgencia(){
		Agencia agencia=(Agencia)this.getDesktop().getSession().getAttribute(Constantes.ATRIBUTO_AGENCIA);
		return agencia;
	}

	public Usuario getUsuario(){
		Usuario usuario=(Usuario)this.getDesktop().getSession().getAttribute(Constantes.ATRIBUTO_USUARIO);
		return usuario;
	}

	public TipoComprobante getTipocomprobante(){
		TipoComprobante tipoComprobante=(TipoComprobante) this.getDesktop().getSession().getAttribute(Constantes.ATRIBUTO_TIPO_COMPROBANTE);
		return tipoComprobante;
	}

	public Rol getRol(){
		Rol rol=(Rol) this.getDesktop().getSession().getAttribute(Constantes.ATRIBUTO_ROL);
		return rol;
	}


	/**
	 * Metodo ZK Framework que se ejecuta despues de componer la página.
	 */
	@Override
	public void afterCompose() {
		initComponents();
	}

	/**
	 * Método para cerrar la ventana, incluyendo el Tab que lo contenga.
	 */
	public void closeTabWindow () {
		Component oComponentParent = this;
		Tabpanel oTabpanel;
		boolean buscarTabParent = true;
		int intentosBusqueda = 0;

		while (buscarTabParent && (intentosBusqueda < 5)) {
			intentosBusqueda ++;

			if (oComponentParent instanceof Tabpanel) {
				oTabpanel = (Tabpanel) oComponentParent;
				oTabpanel.getLinkedTab().close();
				buscarTabParent = false;
			}else {
				oComponentParent = oComponentParent.getParent();
				if (oComponentParent == null)
					buscarTabParent = false;
			}
		}
	}


	@Override
	public void onCreate() throws Exception {
		System.out.println("Entro a la clase BASE **********************");
	}

//	/**
//	 * Busca el rol del usuario
//	 * @return returna el rol
//	 */
//	private Rol rol(){
//		Rol rl =//UtilData.getRol(getUsuario());
//		return rl;
//	}

	@Override
	public void initComponents() {
		// TODO Auto-generated method stub
	}

	/*
	 * (non-Javadoc)
	 * @see com.tepsa.sisvyr.view.ui.IBase#accesoNuevo()
	 */
	@Override
	public Boolean accesoNuevo() {
		if(getRol()!=null){
			if(getRol().getNuevo().equals(Constantes.FALSE_VALUE))
				return false;
			else if((getRol().getNuevo().equals(Constantes.TRUE_VALUE)))
				return true;
			else
				return false;
		}else{
			return false;
		}
	}

	/*
	 * (non-Javadoc)
	 * @see com.tepsa.sisvyr.view.ui.IBase#accesoModificar()
	 */
	@Override
	public Boolean accesoModificar() {
		if(getRol()!=null){
			if(getRol().getModificar().equals(Constantes.FALSE_VALUE))
				return false;
			else if((getRol().getModificar().equals(Constantes.TRUE_VALUE)))
				return true;
			else
				return false;
		}else{
			return false;
		}
	}

	/*
	 * (non-Javadoc)
	 * @see com.tepsa.sisvyr.view.ui.IBase#accesoGrabar()
	 */
	@Override
	public Boolean accesoGrabar() {
		if(getRol()!=null){
			if(getRol().getGrabar().equals(Constantes.FALSE_VALUE))
				return false;
			else if((getRol().getGrabar().equals(Constantes.TRUE_VALUE)))
				return true;
			else
				return false;
		}else{
			return false;
		}
	}

	/*
	 * (non-Javadoc)
	 * @see com.tepsa.sisvyr.view.ui.IBase#accesoEliminar()
	 */
	@Override
	public Boolean accesoEliminar() {
		if(getRol()!=null){
			if(getRol().getEliminar().equals(Constantes.FALSE_VALUE))
				return false;
			else if((getRol().getEliminar().equals(Constantes.TRUE_VALUE)))
				return true;
			else
				return false;
		}else{
			return false;
		}
	}

	/*
	 * (non-Javadoc)
	 * @see com.tepsa.sisvyr.view.ui.IBase#accesoConsultar()
	 */
	@Override
	public Boolean accesoConsultar() {
		if(getRol()!=null){
			if(getRol().getConsultar().equals(Constantes.FALSE_VALUE))
				return false;
			else if((getRol().getConsultar().equals(Constantes.TRUE_VALUE)))
				return true;
			else
				return false;
		}else{
			return false;
		}
	}

	/*
	 * (non-Javadoc)
	 * @see com.tepsa.sisvyr.view.ui.IBase#accesoImprimir()
	 */
	@Override
	public Boolean accesoImprimir() {
		if(getRol()!=null){
			if(getRol().getImprimir().equals(Constantes.FALSE_VALUE))
				return false;
			else if((getRol().getImprimir().equals(Constantes.TRUE_VALUE)))
				return true;
			else
				return false;
		}else{
			return false;
		}
	}

	/*
	 * (non-Javadoc)
	 * @see com.tepsa.sisvyr.view.ui.IBase#accesoExportar()
	 */
	@Override
	public Boolean accesoExportar() {
		if(getRol()!=null){
			if(getRol().getExportar().equals(Constantes.FALSE_VALUE))
				return false;
			else if((getRol().getExportar().equals(Constantes.TRUE_VALUE)))
				return true;
			else
				return false;
		}else{
			return false;
		}
	}

	/*
	 * (non-Javadoc)
	 * @see com.tepsa.sisvyr.view.ui.IBase#accesoSuperUsuario(java.lang.Object[])
	 */
	@Override
	public void accesoControlsRolSuperUsuario(List<Component> lstComponents){
		for (Component component : lstComponents) {
			/*Deshabilita los controles si el usuario tiene un rol diferente al SUPER USUARIO*/
			if(component instanceof Textbox)
				((Textbox)component).setDisabled(getRol().getId().intValue()!=Constantes.ID_ROL_SUPER_USUARIO);
			else if(component instanceof Combobox)
				((Combobox)component).setDisabled(getRol().getId().intValue()!=Constantes.ID_ROL_SUPER_USUARIO);
		}
	}

	/*
	 * (non-Javadoc)
	 * @see com.tepsa.sisvyr.view.ui.IBase#accesoControlsByRol(java.util.List, java.util.List)
	 */
	@Override
	public void accesoControlsByRol(List<Component> lstComponents, List<Rol> listRolesAcceeso){
		accesoControlsByRol(lstComponents, listRolesAcceeso, false);
	}

	/*
	 * (non-Javadoc)
	 * @see com.tepsa.sisvyr.view.ui.IBase#accesoControlsByRol(java.util.List, java.util.List)
	 */
	@Override
	public void accesoControlsByRol(List<Component> lstComponents, List<Rol> listRolesAcceeso, Boolean ocultarControls){
		for (Component component : lstComponents) {
			/*Deshabilita los controles si el usuario tiene un rol diferente al SUPER USUARIO*/
			if(component instanceof Textbox){
				if(!(ocultarControls))
					((Textbox)component).setDisabled(!valirarRol(listRolesAcceeso));
				else
					((Textbox)component).setVisible(valirarRol(listRolesAcceeso));
			}else if(component instanceof Combobox){
				if(!(ocultarControls))
					((Combobox)component).setDisabled(!valirarRol(listRolesAcceeso));
				else
					((Combobox)component).setVisible(valirarRol(listRolesAcceeso));
			}else if (component instanceof Datebox){
				if(!(ocultarControls))
					((Datebox)component).setDisabled(!valirarRol(listRolesAcceeso));
				else
					((Datebox)component).setVisible(valirarRol(listRolesAcceeso));
			}else if (component instanceof Button){
				if(!(ocultarControls))
					((Button)component).setDisabled(!valirarRol(listRolesAcceeso));
				else
					((Button)component).setVisible(valirarRol(listRolesAcceeso));
			}else if (component instanceof Toolbarbutton){
				if(!(ocultarControls))
					((Toolbarbutton)component).setDisabled(!valirarRol(listRolesAcceeso));
				else
					((Toolbarbutton)component).setVisible(valirarRol(listRolesAcceeso));
			}else if (component instanceof Checkbox){
				if(!(ocultarControls))
					((Checkbox)component).setDisabled(!valirarRol(listRolesAcceeso));
				else
					((Checkbox)component).setVisible(valirarRol(listRolesAcceeso));
			}else if (component instanceof Radio){
				if(!(ocultarControls))
					((Radio)component).setDisabled(!valirarRol(listRolesAcceeso));
				else
					((Radio)component).setVisible(valirarRol(listRolesAcceeso));
			}else if (component instanceof Listheader){
				if(ocultarControls)
					((Listheader)component).setVisible(valirarRol(listRolesAcceeso));
			}else if (component instanceof Listfoot)
				if(ocultarControls)
					((Listfoot)component).setVisible(valirarRol(listRolesAcceeso));

		}
	}

	/**
	 * Valida el si el rol del usuario actual esta en la lista
	 * @param listRolesAcceeso	: Lista de roles
	 * @return
	 */
	private Boolean valirarRol(List<Rol> listRolesAcceeso){
		for(Rol rol:listRolesAcceeso){
			if(rol.getId().intValue()==getRol().getId().intValue())
				return true;
		}
		return false;
	}
}
