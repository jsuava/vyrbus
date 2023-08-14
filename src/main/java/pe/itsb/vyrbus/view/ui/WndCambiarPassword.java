package pe.itsb.vyrbus.view.ui;
import java.io.Serializable;

import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Textbox;

import pe.itsb.vyrbus.model.bean.TranscarUsuarioPersonal;
import pe.itsb.vyrbus.model.bean.Usuario;
import pe.itsb.vyrbus.service.exceptions.PasswordException;
import pe.itsb.vyrbus.service.locator.ServiceLocator;
import pe.itsb.vyrbus.service.util.Constantes;
import pe.itsb.vyrbus.service.util.Messages;
import pe.itsb.vyrbus.service.util.UtilData;

/**
 *
 * @author JABANTO
 *
 */
public class WndCambiarPassword extends WndBase implements Serializable{

	private static final long serialVersionUID = 1L;
	private Textbox txtNombreUsuario;
	private Textbox txtPasswordActual;
	private Textbox txtNuevoPassword;
	private Textbox txtConfirmarPassword;

	Usuario usuario=new Usuario();

	/*
	 * (non-Javadoc)
	 * @see com.tepsa.sisvyr.view.ui.WndBase#initComponents()
	 */
	@Override
	public void initComponents() {
		txtNombreUsuario=(Textbox)getFellow("txtNombreUsuario");
		txtPasswordActual=(Textbox)getFellow("txtPasswordActual");
		txtNuevoPassword=(Textbox)getFellow("txtNuevoPassword");
		txtConfirmarPassword=(Textbox)getFellow("txtConfirmarPassword");
	}

	/*
	 * (non-Javadoc)
	 * @see com.tepsa.sisvyr.view.ui.WndBase#onCreate()
	 */
	@Override
	public void onCreate() throws Exception {
		usuario=(Usuario)getDesktop().getSession().getAttribute("USUARIO");

		txtNombreUsuario.setValue(usuario.getLogin());
		txtPasswordActual.setFocus(true);
	}

	public void saved()throws Exception{
		try{
			if(txtPasswordActual.getText().trim().length()==0)
				throw new PasswordException(PasswordException.PASSWORD_NULL);
			else if (txtNuevoPassword.getText().trim().length()==0)
				throw new PasswordException(PasswordException.PASSWORD_NUEVO_NULL);
			else if (txtConfirmarPassword.getText().trim().length()==0)
				throw new PasswordException(PasswordException.PASSWORD_CONFIRMATION_NULL);
			else if (!(txtNuevoPassword.getText().trim().equals(txtConfirmarPassword.getText().trim())))
				throw new PasswordException(PasswordException.PASSWORD_DIFERENTES);

			/*Valida que el password actual sea el correcto*/
			Usuario usuario=ServiceLocator.getUsuarioManager().buscarUsuarioPorLoginPassword(txtNombreUsuario.getText(), txtPasswordActual.getText().trim(), Constantes.VALUE_ACTIVO);
			if(usuario==null)
				throw new PasswordException(PasswordException.PASSWORD_INCOREC);

			usuario.setPwdNormal(txtNuevoPassword.getText().trim());
			ServiceLocator.getUsuarioManager().actualizar(usuario);

			UtilData.auditarRegistro(usuario, true, getUsuario(), Executions.getCurrent());
			/*Actualiza el Password del usario en Transcar - jabanto - 07/05/2022*/
			ServiceLocator.getTranscarWebManager().actualizarPasswordUsuarioByLogin(usuario.getLogin(), usuario.getPassword());


			/*Actualia el usuario en el sistema de carga*/
			try {
				TranscarUsuarioPersonal transcarUsuarioPersonal = ServiceLocator.getTranscarWebManager().buscarUsuario(usuario.getLogin());
				if(transcarUsuarioPersonal !=null) {
					transcarUsuarioPersonal.setApellidoParterno(usuario.getApellidoPaterno());
					transcarUsuarioPersonal.setApellidoMaterno(usuario.getApellidoMaterno()!=null?usuario.getApellidoMaterno():null);
					transcarUsuarioPersonal.setNombres(usuario.getNombre());
					transcarUsuarioPersonal.setEmail(usuario.getEmailInfo()!=null?usuario.getEmailInfo():null);
					transcarUsuarioPersonal.setPassword(txtNuevoPassword.getText());
					transcarUsuarioPersonal.setIpModificacion(usuario.getIpModificacion());
					transcarUsuarioPersonal.setUsuarioModificacion(usuario.getUsuarioModificacion());
					transcarUsuarioPersonal.setEstadoRegistro(Constantes.VALUE_ACTIVO);
					transcarUsuarioPersonal.setAgenciaId(getAgencia().getId());

					ServiceLocator.getTranscarWebManager().guardarUsuario(transcarUsuarioPersonal, null, false);
				}
			} catch (Exception e) {
				e.printStackTrace();
				DlgMessage.error(Messages.getString("wndCambioPasswordCarga.information.PasswordCambiado")+ " - " + e.getMessage());
			}

			Messagebox.show(Messages.getString("wndCambioPassword.information.PasswordCambiado"), DlgMessage.NOMBREAPLICACION, DlgMessage.BTN_OK, Messagebox.INFORMATION, new EventListener<Event>() {
				@Override
				public void onEvent(Event e) throws Exception {
					closeTabWindow();
				}
			});
		}catch (PasswordException cp){
			if(cp.getTipo().intValue()==PasswordException.PASSWORD_NULL){
				DlgMessage.information(Messages.getString("wndCambioPassword.information.PasswordActualNull"));
				txtPasswordActual.setFocus(true);
			}else if (cp.getTipo().intValue()==PasswordException.PASSWORD_NUEVO_NULL){
				DlgMessage.information(Messages.getString("wndCambioPassword.information.PassrowdNuevoNull"));
				txtNuevoPassword.setFocus(true);
			}else if (cp.getTipo().intValue()==PasswordException.PASSWORD_CONFIRMATION_NULL){
				DlgMessage.information(Messages.getString("wndCambioPassword.information.PasswordConfirmarNull"));
				txtConfirmarPassword.setFocus(true);
			}else if (cp.getTipo().intValue()==PasswordException.PASSWORD_DIFERENTES){
				DlgMessage.information(Messages.getString("wndCambioPassword.information.PasswordDiferentes"));
				txtNuevoPassword.setFocus(true);
			}else if (cp.getTipo().intValue()==PasswordException.PASSWORD_INCOREC){
				DlgMessage.information(Messages.getString("wndCambioPassword.information.PasswordActualIncorrecto"));
				txtPasswordActual.setFocus(true);
			}
		}catch (Exception ex) {
			DlgMessage.error(this.getClass().getName()+" "+ex.getMessage());
			ex.printStackTrace();
		}

	}

}
