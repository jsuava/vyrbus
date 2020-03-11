/**
 * Proyecto		: SISVYR
 * Sistema		: Sistema de Ventas y Reservas
 * Descripción	: 
 * Autor		: jM
 * Fecha		: 02/05/2012
 */
package com.cystesoft.vyrbus.view.ctrl;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zul.Textbox;

import com.cystesoft.vyrbus.model.bean.Localidad;
import com.cystesoft.vyrbus.service.exceptions.CancelaGrabacionException;
import com.cystesoft.vyrbus.service.exceptions.DenominacionDuplicadaException;
import com.cystesoft.vyrbus.service.exceptions.DenominacionNullException;
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
 * @author jM
 * @since JDK1.6
 */
public class WndLocalidad extends WndOpcionesMantenimiento {

	private static final long serialVersionUID = -1721402526478904823L;

	private Textbox txtDenominacion;
	
	private Localidad oLocalidad=null;

	private TreeMap<String, Object> criteriosBusqueda = new TreeMap<String, Object>();
	private List<String> criteriosOrdenar = null;
	
	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.window.ui.IBase#onCreate()
	 */
	@Override
	public void onCreate() throws Exception {
		criteriosOrdenar = new ArrayList<String>();
		criteriosOrdenar.add("denominacion");
	}
	
	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.window.ui.IBase#initComponents()
	 */
	@Override
	public void initComponents() {
		txtDenominacion = (Textbox) getFellow("txtDenominacion");
	}
	
	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.window.ui.IOpcionesMantenimiento#onNew()
	 */
	@Override
	public void onNew() {
		// TODO Auto-generated method stub
	}
	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.window.ui.IOpcionesMantenimiento#onSearch()
	 */
	@Override
	public void onSearch() {
		final WndFiltrarParametros oWndFiltrar = new WndFiltrarParametros();

		oWndFiltrar.addParameter("Denominación", String.class);

		this.appendChild(oWndFiltrar);
		oWndFiltrar.setMode("modal");
		oWndFiltrar.addEventListener(com.cystesoft.vyrbus.view.ui.Events.ON_FILTER, new EventListener<Event>() {

			@Override
			public void onEvent(Event event) throws Exception {
				String denominacion = (String) oWndFiltrar.getParameterValue("Denominación");
				String estadoRegistro = Constantes.VALUE_ACTIVO;

				if (denominacion.trim().equals("")) {
					criteriosBusqueda.remove("denominacion");
				}else {criteriosBusqueda.put("denominacion", "%" + denominacion + "%");}

				criteriosBusqueda.put("estadoRegistro", estadoRegistro);
				listarRegistros(ServiceLocator.getLocalidadManager().buscarPorX(criteriosBusqueda, criteriosOrdenar));
			}
		});
	}
	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.window.ui.IOpcionesMantenimiento#onRefresh(int)
	 */
	@Override
	public void onRefresh(int tab) throws Exception {
		if (!criteriosBusqueda.isEmpty()) {
			this.listarRegistros(ServiceLocator.getLocalidadManager().buscarPorX(criteriosBusqueda, criteriosOrdenar));					
		}
	}
	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.window.ui.IOpcionesMantenimiento#onModify(int)
	 */
	@Override
	public void onModify(int tab) throws Exception {
		Long id = new Long(0);
		id = new Long((String) listboxLista.getSelectedItem().getValue());
		this.mantenimientoRegistro(id);
	}
	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.window.ui.IOpcionesMantenimiento#onCancel(int)
	 */
	@Override
	public void onCancel(int action) throws Exception {
		switch (action) {
			case ACTION_NEW:
				break;

			case ACTION_MODIFY:
				this.mantenimientoRegistro(new Long(textboxId.getText()));
				break;
		}
	}
	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.window.ui.IOpcionesMantenimiento#onSave(int)
	 */
	@Override
	public void onSave(int action) throws Exception {
		try{
			if (txtDenominacion.getText().trim().equals(""))
				throw new DenominacionNullException();
			
			if (action==ACTION_NEW)
				oLocalidad = new Localidad();
			
			Integer id = (textboxId.getText().equals("") ? 0 : new Integer(textboxId.getText()));
			oLocalidad.setId(id);
			oLocalidad.setDenominacion(txtDenominacion.getText().trim().toUpperCase());
			oLocalidad.setEstadoRegistro(Constantes.VALUE_ACTIVO);

			switch (action) {
				case ACTION_NEW:
					UtilData.auditarRegistro(oLocalidad, getUsuario(), Executions.getCurrent());
					ServiceLocator.getLocalidadManager().guardar(oLocalidad);
					textboxId.setText(oLocalidad.getId().toString());
					break;

				case ACTION_MODIFY:
					UtilData.auditarRegistro(oLocalidad, true, getUsuario(), Executions.getCurrent());
					ServiceLocator.getLocalidadManager().actualizar(oLocalidad);
					break;
			}
			/*RECUEPRA EL REGISTRO ACTUALIZADO O EL NUEVO*/			
			criteriosBusqueda.remove("denominacion");
			criteriosBusqueda.put("denominacion", oLocalidad.getDenominacion());
			criteriosBusqueda.put("estadoRegistro", Constantes.VALUE_ACTIVO);
			this.listarRegistros(ServiceLocator.getLocalidadManager().buscarPorX(criteriosBusqueda, criteriosOrdenar));	
			
		}catch (DenominacionNullException dnex){
		  DlgMessage.information(Messages.getString("Denominacion"));
		  txtDenominacion.setFocus(true); throw new CancelaGrabacionException();
		}catch (DenominacionDuplicadaException ddex){
			DlgMessage.information(Messages.getString("DenominacionDuplicada"));
			  txtDenominacion.setFocus(true); throw new CancelaGrabacionException();
		}catch (Exception ex){
			DlgMessage.error(this.getClass().getName()+" "+ex.getMessage());
			ex.printStackTrace(); throw new CancelaGrabacionException();
		}
		
		
	}
	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.window.ui.IOpcionesMantenimiento#onDelete(int)
	 */
	@Override
	public void onDelete(int tab) throws Exception {
		Long id = (long) 0;

		switch (tab) {
			case TAB_LIST:
				id = new Long((String) listboxLista.getSelectedItem().getValue());
				break;

			case TAB_MAINTENANCE:
				id = new Long(textboxId.getText());
				break;
		}

		ServiceLocator.getLocalidadManager().inactivar(id);
	}
	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.window.ui.IOpcionesMantenimiento#onPrint(int)
	 */
	@Override
	public void onPrint(int tab) {
		// TODO Auto-generated method stub
		
	}
	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.window.ui.IOpcionesMantenimiento#onExport(int)
	 */
	@Override
	public void onExport(int tab) {
		// TODO Auto-generated method stub
		
	}
	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.window.ui.IOpcionesMantenimiento#onHelp()
	 */
	@Override
	public void onHelp() {
		// TODO Auto-generated method stub
		
	}

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.window.ui.IOpcionesMantenimiento#onChangeTab(int)
	 */
	@Override
	public void onChangeTab(int tab) throws Exception {
		switch (tab) {
			case TAB_LIST:
				break;
	
			case TAB_MAINTENANCE:
				if (listboxLista.getSelectedIndex() > -1) {
					this.mantenimientoRegistro(new Long((String) listboxLista.getSelectedItem().getValue()));
				}
				break;
		}
	}

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.window.IOpcionesMantenimiento#onClose()
	 */
	@Override
	public void onClose() {
		closeTabWindow();
	}

	private void listarRegistros(ArrayList<Localidad> lstRegistros) {
		ArrayList<Object> lstLocalidades = new ArrayList<Object>();

		for(int r = 0; r < lstRegistros.size(); r ++) {
			Localidad oLocalidad = lstRegistros.get(r);
			ArrayList<Object> lstFila = new ArrayList<Object>();

			lstFila.add(oLocalidad.getId());
			lstFila.add(r + 1);
			lstFila.add(oLocalidad.getDenominacion());

			lstLocalidades.add(lstFila);
		}

		Util.llenarListbox(listboxLista, lstLocalidades, true);
	}


	private void mantenimientoRegistro(Long id) throws Exception {
		oLocalidad = ServiceLocator.getLocalidadManager().buscarPorId(id);

		textboxId.setText(oLocalidad.getId().toString());
		txtDenominacion.setText(oLocalidad.getDenominacion());
	}
}