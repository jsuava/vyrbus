/**
 * Proyecto		: SISVYR
 * Sistema		: Sistema de Ventas y Reservas
 * Descripción	: 
 * Autor		: jM
 * Fecha		: 30/04/2012
 */
package com.cystesoft.vyrbus.view.ctrl;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zul.Textbox;

import com.cystesoft.vyrbus.model.bean.GrupoMantenimiento;
import com.cystesoft.vyrbus.service.exceptions.CancelaGrabacionException;
import com.cystesoft.vyrbus.service.exceptions.CodigoDuplicadoException;
import com.cystesoft.vyrbus.service.exceptions.CodigoNullException;
import com.cystesoft.vyrbus.service.exceptions.DenominacionDuplicadaException;
import com.cystesoft.vyrbus.service.exceptions.DenominacionNullException;
import com.cystesoft.vyrbus.service.exceptions.NombreCortoDuplicadoException;
import com.cystesoft.vyrbus.service.exceptions.NombreCortoNullException;
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
public class WndGrupoMantenimiento extends WndOpcionesMantenimiento {

	private static final long serialVersionUID = 1998913859947825458L;

	private Textbox txtDenominacion;
	private Textbox txtCodigo;
	private Textbox txtNombreCorto;
	
	private GrupoMantenimiento oGrupoMantenimiento = null;

	private	TreeMap<String, Object> criteriosBusqueda = new TreeMap<String, Object>();
	private List<String> criteriosOrdenar = null;
	
	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.window.IBase#onCreate()
	 */
	@Override
	public void onCreate() {
		criteriosOrdenar = new ArrayList<String>();
		criteriosOrdenar.add("denominacion");
	}

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.window.IBase#initComponents()
	 */
	@Override
	public void initComponents() {
		txtDenominacion = (Textbox) getFellow("txtDenominacion");
		txtCodigo = (Textbox) getFellow("txtCodigo");
		txtNombreCorto = (Textbox) getFellow("txtNombreCorto");
	}
	
	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.window.ui.IOpcionesMantenimiento#onNew()
	 */
	@Override
	public void onNew() {
	}

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.window.ui.IOpcionesMantenimiento#onSearch()
	 */
	@Override
	public void onSearch() {
		final WndFiltrarParametros oWndFiltrar = new WndFiltrarParametros();

		oWndFiltrar.addParameter("Denominación", String.class);
		oWndFiltrar.addParameter("Código", String.class);
		oWndFiltrar.addParameter("Nombre corto", String.class);

		this.appendChild(oWndFiltrar);
		oWndFiltrar.setMode("modal");
		oWndFiltrar.addEventListener(com.cystesoft.vyrbus.view.ui.Events.ON_FILTER, new EventListener<Event>() {

			@Override
			public void onEvent(Event event) throws Exception {
				String denominacion = (String) oWndFiltrar.getParameterValue("Denominación");
				String codigo = (String) oWndFiltrar.getParameterValue("Código");
				String nombreCorto = (String) oWndFiltrar.getParameterValue("Nombre corto");
				String estadoRegistro = Constantes.VALUE_ACTIVO;

				if (denominacion.trim().equals("")) {
					criteriosBusqueda.remove("denominacion");
				}else {criteriosBusqueda.put("denominacion", "%" + denominacion + "%");}

				if (codigo.trim().equals("")) {
					criteriosBusqueda.remove("codigo");
				}else {criteriosBusqueda.put("codigo", "%" + codigo + "%");}

				if (nombreCorto.trim().equals("")) {
					criteriosBusqueda.remove("nombreCorto");
				}else {criteriosBusqueda.put("nombreCorto", "%" + nombreCorto + "%");}

				criteriosBusqueda.put("estadoRegistro", estadoRegistro);

				listarRegistros(ServiceLocator.getGrupoMantenimientoManager().buscarPorX(criteriosBusqueda, criteriosOrdenar));
			}
		});
	}

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.window.ui.IOpcionesMantenimiento#onRefresh(int)
	 */
	@Override
	public void onRefresh(int tab) throws Exception {
		if (!criteriosBusqueda.isEmpty()) {
			this.listarRegistros(ServiceLocator.getGrupoMantenimientoManager().buscarPorX(criteriosBusqueda, criteriosOrdenar));					
		}
	}

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.window.ui.IOpcionesMantenimiento#()
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
	@Autowired
	@Override
	public void onSave(int action) throws Exception {
		try{
			if (txtDenominacion.getText().trim().equals(""))
				throw new DenominacionNullException();
			else if (txtCodigo.getText().trim().equals(""))
				throw new CodigoNullException();
			else if (txtNombreCorto.getText().trim().equals(""))
				throw new NombreCortoNullException();
			
			
			if (action==ACTION_NEW)
				oGrupoMantenimiento = new GrupoMantenimiento();
			
			Integer id = (textboxId.getText().equals("") ? 0 : new Integer(textboxId.getText()));

			oGrupoMantenimiento.setId(id);
			oGrupoMantenimiento.setDenominacion(txtDenominacion.getText().trim().toUpperCase());
			oGrupoMantenimiento.setCodigo(txtCodigo.getText().trim().toUpperCase());
			oGrupoMantenimiento.setNombreCorto(txtNombreCorto.getText().trim().toUpperCase());
			oGrupoMantenimiento.setEstadoRegistro(Constantes.VALUE_ACTIVO);

			switch (action) {
				case ACTION_NEW:
					UtilData.auditarRegistro(oGrupoMantenimiento, getUsuario(), Executions.getCurrent());
					ServiceLocator.getGrupoMantenimientoManager().guardar(oGrupoMantenimiento);
					textboxId.setText(oGrupoMantenimiento.getId().toString());
					break;

				case ACTION_MODIFY:
					UtilData.auditarRegistro(oGrupoMantenimiento, true, getUsuario(), Executions.getCurrent());
					ServiceLocator.getGrupoMantenimientoManager().actualizar(oGrupoMantenimiento);
					break;
			}
			/*RECUPERA EL REGISTRO ACTUALIZADO O EL NUEVO*/
			criteriosBusqueda.remove("denominacion");
			criteriosBusqueda.remove("codigo");
			criteriosBusqueda.remove("nombreCorto");
			criteriosBusqueda.put("denominacion", oGrupoMantenimiento.getDenominacion());
			criteriosBusqueda.put("estadoRegistro", Constantes.VALUE_ACTIVO);
			listarRegistros(ServiceLocator.getGrupoMantenimientoManager().buscarPorX(criteriosBusqueda, criteriosOrdenar));
			
		}catch (DenominacionNullException dnex){
			DlgMessage.information(Messages.getString("Generales.information.noIngresoDenominacion"),txtDenominacion);
			throw new CancelaGrabacionException();
		}catch (CodigoNullException ncnex){
			DlgMessage.information(Messages.getString("Generales.information.noIngresoCodigo"),txtCodigo);
			throw new CancelaGrabacionException();
		}catch (NombreCortoNullException ncnex){
			DlgMessage.information(Messages.getString("Generales.information.noIngresoNombreCorto"),txtNombreCorto);
		}catch (DenominacionDuplicadaException ddex){
			DlgMessage.information(Messages.getString("Generales.information.denominacionDuplicada"),txtDenominacion);
			throw new CancelaGrabacionException();
		}catch (NombreCortoDuplicadoException ncdex){
			DlgMessage.information(Messages.getString("Generales.information.nombreCortoDuplicado"),txtNombreCorto);
			new CancelaGrabacionException();
		}catch (CodigoDuplicadoException cdex){
			DlgMessage.information(Messages.getString("Generales.information.codigoDuplicado"),txtNombreCorto);
			new CancelaGrabacionException();
		}catch (Exception ex){
			DlgMessage.error(this.getClass().getName()+" "+ex.getMessage());
			ex.printStackTrace(); throw new CancelaGrabacionException();
		}
				
	}

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.window.ui.IOpcionesMantenimiento#onDelete()
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

		ServiceLocator.getGrupoMantenimientoManager().inactivar(id);
	}

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.window.ui.IOpcionesMantenimiento#onPrint()
	 */
	@Override
	public void onPrint(int tab) {
		// TODO Auto-generated method stub
	}

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.window.ui.IOpcionesMantenimiento#onExport()
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
	 * @see com.tepsa.sisvyr.window.ui.IOpcionesMantenimiento#onClose()
	 */
	@Override
	public void onClose() {
		closeTabWindow();
	}

	private void listarRegistros(ArrayList<GrupoMantenimiento> lstRegistros) {
		ArrayList<Object> lstGruposMantenimiento = new ArrayList<Object>();

		for(int r = 0; r < lstRegistros.size(); r ++) {
			GrupoMantenimiento oGrupoMantenimiento = lstRegistros.get(r);
			ArrayList<Object> lstFila = new ArrayList<Object>();

			lstFila.add(oGrupoMantenimiento.getId());
			lstFila.add(r + 1);
			lstFila.add(oGrupoMantenimiento.getDenominacion());
			lstFila.add(oGrupoMantenimiento.getCodigo());
			lstFila.add(oGrupoMantenimiento.getNombreCorto());

			lstGruposMantenimiento.add(lstFila);
		}

		Util.llenarListbox(listboxLista, lstGruposMantenimiento, true);
	}


	private void mantenimientoRegistro(Long id) throws Exception {
		oGrupoMantenimiento = ServiceLocator.getGrupoMantenimientoManager().buscarPorId(id);

		textboxId.setText(oGrupoMantenimiento.getId().toString());
		txtDenominacion.setText(oGrupoMantenimiento.getDenominacion());
		txtCodigo.setText(oGrupoMantenimiento.getCodigo());
		txtNombreCorto.setText(oGrupoMantenimiento.getNombreCorto());
	}
}