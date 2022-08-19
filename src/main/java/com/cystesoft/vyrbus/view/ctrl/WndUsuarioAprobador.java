package com.cystesoft.vyrbus.view.ctrl;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Intbox;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;

import com.cystesoft.vyrbus.model.bean.Usuario;
import com.cystesoft.vyrbus.model.bean.UsuarioAprobador;
import com.cystesoft.vyrbus.service.exceptions.CancelaGrabacionException;
import com.cystesoft.vyrbus.service.exceptions.EmailNullException;
import com.cystesoft.vyrbus.service.exceptions.NivelUsuarioAprobadorNullException;
import com.cystesoft.vyrbus.service.exceptions.UsuarioNullException;
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
 * @author JABANTO
 *
 */
public class WndUsuarioAprobador extends WndOpcionesMantenimiento {
	private static final long serialVersionUID = 1L;

	private Combobox cmbUsuario;
	private Intbox itbNivelAprobacion;

	private UsuarioAprobador usuarioAprobador=null;
	private TreeMap<String, Object> criteriosBusqueda = null;
	private List<String> criteriosOrdenar = null;

	/*
	 * (non-Javadoc)
	 * @see com.tepsa.sisvyr.view.ui.WndBase#initComponents()
	 */
	@Override
	public void initComponents() {
		cmbUsuario=(Combobox)this.getFellow("cmbUsuario");
		itbNivelAprobacion=(Intbox)this.getFellow("itbNivelAprobacion");
	}

	/*
	 * (non-Javadoc)
	 * @see com.tepsa.sisvyr.view.ui.WndBase#onCreate()
	 */
	@Override
	public void onCreate() throws Exception {
		criteriosOrdenar = new ArrayList<>();
		criteriosOrdenar.add("nivelAprobacion");

		UtilData.cargarUsuarios(cmbUsuario, false);
	}

	/*
	 * (non-Javadoc)
	 * @see com.tepsa.sisvyr.view.ui.IOpcionesMantenimiento#onNew()
	 */
	@Override
	public void onNew() throws Exception {
		cmbUsuario.setText("");
	}

	/*
	 * (non-Javadoc)
	 * @see com.tepsa.sisvyr.view.ui.IOpcionesMantenimiento#onSearch()
	 */
	@Override
	public void onSearch() throws Exception {
		// TODO Auto-generated method stub
		final WndFiltrarParametros oWndFiltrar = new WndFiltrarParametros();
		criteriosBusqueda = new TreeMap<>();

		oWndFiltrar.addParameter("Usuario aprobador", UsuarioAprobador.class);

		this.appendChild(oWndFiltrar);
		oWndFiltrar.setMode("modal");
		oWndFiltrar.addEventListener(com.cystesoft.vyrbus.view.ui.Events.ON_FILTER, new EventListener<Event>() {

			@Override
			public void onEvent(Event event) throws Exception {
				UsuarioAprobador usuarioAprobador = (UsuarioAprobador) oWndFiltrar.getParameterValue("Usuario aprobador");
				String estadoRegistro = Constantes.VALUE_ACTIVO;
				if(usuarioAprobador!=null)
					criteriosBusqueda.put("usuario", usuarioAprobador.getUsuario());
				criteriosBusqueda.put("estadoRegistro", estadoRegistro);

				listarUsuariosAprobador(ServiceLocator.getUsuarioAprobadorManager().buscarPorX(criteriosBusqueda, criteriosOrdenar));
			}
		});

	}

	/*
	 * (non-Javadoc)
	 * @see com.tepsa.sisvyr.view.ui.IOpcionesMantenimiento#onRefresh(int)
	 */
	@Override
	public void onRefresh(int tab) throws Exception {
		// TODO Auto-generated method stub
		if(!(criteriosBusqueda.isEmpty()))
			listarUsuariosAprobador(ServiceLocator.getUsuarioAprobadorManager().buscarPorX(criteriosBusqueda, criteriosOrdenar));

	}

	/*
	 * (non-Javadoc)
	 * @see com.tepsa.sisvyr.view.ui.IOpcionesMantenimiento#onModify(int)
	 */
	@Override
	public void onModify(int tab) throws Exception {
		// TODO Auto-generated method stub
		mantenimiento();
	}

	/*
	 * (non-Javadoc)
	 * @see com.tepsa.sisvyr.view.ui.IOpcionesMantenimiento#onCancel(int)
	 */
	@Override
	public void onCancel(int action) throws Exception {
		// TODO Auto-generated method stub

	}

	/*
	 * (non-Javadoc)
	 * @see com.tepsa.sisvyr.view.ui.IOpcionesMantenimiento#onSave(int)
	 */
	@Override
	public void onSave(int action) throws Exception {
		try{
			if (cmbUsuario.getSelectedIndex() < 0)
				throw new UsuarioNullException();
			else if (itbNivelAprobacion.getValue()==null || itbNivelAprobacion.getValue().toString().trim().isEmpty())
				throw new NivelUsuarioAprobadorNullException();

			//Valida si el usuario tiene un Email Configurado.
			if (!(UtilData.validateUserMail((Usuario)cmbUsuario.getSelectedItem().getValue())))
				throw new EmailNullException(EmailNullException.EMAIL_PERSONAL);

			if(action==ACTION_NEW)
				usuarioAprobador=new UsuarioAprobador();
			Usuario usuario=(Usuario)cmbUsuario.getSelectedItem().getValue();
			usuarioAprobador.setUsuario(usuario);
			usuarioAprobador.setNivelAprobacion(itbNivelAprobacion.getValue());
			usuarioAprobador.setEstadoRegistro(Constantes.VALUE_ACTIVO);

			switch (action) {
				case ACTION_NEW:
					UtilData.auditarRegistro(usuarioAprobador, getUsuario(), Executions.getCurrent());
					ServiceLocator.getUsuarioAprobadorManager().guardar(usuarioAprobador);
					break;

				case ACTION_MODIFY:
					UtilData.auditarRegistro(usuarioAprobador, true, getUsuario(), Executions.getCurrent());
					ServiceLocator.getUsuarioAprobadorManager().actualizar(usuarioAprobador);
					break;
			}
			/*RECUEPRA EL REGISTRO*/
			criteriosBusqueda= new TreeMap<>();
			criteriosBusqueda.put("id", usuarioAprobador.getId());
			listarUsuariosAprobador(ServiceLocator.getUsuarioAprobadorManager().buscarPorX(criteriosBusqueda, criteriosOrdenar));


			/*Cuando se actualiza un usuario aprobador el sistema modifica el password, falta investigar por que*/
			/*Ha manera temporal esta es la solucion.*/
			usuario=ServiceLocator.getUsuarioManager().buscarXId(usuario.getId().longValue());
			ServiceLocator.getUsuarioManager().actualizar(usuario);

		}catch (EmailNullException emx){
			if(emx.getTipoEmail().intValue()==EmailNullException.EMAIL_PERSONAL){
				DlgMessage.information(Messages.getString("WndUsuarioAprobador.information.noMailUser"),cmbUsuario);
			}
			throw new CancelaGrabacionException();
		}catch (NivelUsuarioAprobadorNullException nuanuex){
			DlgMessage.information(Messages.getString("Generales.information.noSeleccionoUsuario"),itbNivelAprobacion);
			throw new CancelaGrabacionException();
		}catch (UsuarioNullException unex){
			DlgMessage.information(Messages.getString("Generales.information.noSeleccionoUsuario"),cmbUsuario);
			throw new CancelaGrabacionException();
		}catch (Exception e) {
			DlgMessage.error(this.getClass().getName()+" "+e.getMessage());
			e.printStackTrace(); throw new CancelaGrabacionException();
		}
	}

	/*
	 * (non-Javadoc)
	 * @see com.tepsa.sisvyr.view.ui.IOpcionesMantenimiento#onDelete(int)
	 */
	@Override
	public void onDelete(int tab) throws Exception {
			UsuarioAprobador usuarioAprobador=((UsuarioAprobador)listboxLista.getSelectedItem().getValue());
			ServiceLocator.getUsuarioAprobadorManager().inactivar(usuarioAprobador.getId().longValue());

	}

	@Override
	public void onClose() {
		// TODO Auto-generated method stub
		closeTabWindow();
	}
	/*
	 * (non-Javadoc)
	 * @see com.tepsa.sisvyr.view.ui.IOpcionesMantenimiento#onPrint(int)
	 */
	@Override
	public void onPrint(int tab) throws Exception {
		// TODO Auto-generated method stub
		// TODO Auto-generated method stub

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
		switch (tab) {
			case TAB_LIST:
				break;
			case TAB_MAINTENANCE:
				mantenimiento();
				break;
		}

	}

	private void listarUsuariosAprobador(List<UsuarioAprobador> list){

		Listitem item=null;
		Listcell cell=null;
		int x=0;
		Util.limpiarListbox(listboxLista);

		for (UsuarioAprobador usuarioAprobador: list){
			Usuario usuario=usuarioAprobador.getUsuario();
			if(usuario.getApellidoMaterno()==null)
				usuario.setApellidoMaterno("");

			x++;
			item=new Listitem();
			cell=new Listcell(String.valueOf(x));
			item.appendChild(cell);
			cell=new Listcell(usuario.toString());
			item.appendChild(cell);
			cell=new Listcell(usuarioAprobador.getNivelAprobacion().toString());
			item.appendChild(cell);

			item.setValue(usuarioAprobador);
			listboxLista.appendChild(item);
		}
	}

	private void mantenimiento(){
		if(listboxLista.getSelectedIndex()>=0){
			Listitem item=listboxLista.getItemAtIndex(listboxLista.getSelectedIndex());
			usuarioAprobador=new UsuarioAprobador();
			usuarioAprobador=item.getValue();
			Util.seleccionarValorItemCombo(Usuario.class, cmbUsuario, usuarioAprobador.getUsuario().getId());
			itbNivelAprobacion.setValue(usuarioAprobador.getNivelAprobacion());
		}
	}
}
