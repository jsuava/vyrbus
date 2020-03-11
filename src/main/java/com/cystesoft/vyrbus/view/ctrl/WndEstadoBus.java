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

import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Comboitem;
import org.zkoss.zul.Textbox;

import com.cystesoft.vyrbus.model.bean.EstadoBus;
import com.cystesoft.vyrbus.service.exceptions.CancelaGrabacionException;
import com.cystesoft.vyrbus.service.exceptions.DenominacionDuplicadaException;
import com.cystesoft.vyrbus.service.exceptions.DenominacionNullException;
import com.cystesoft.vyrbus.service.exceptions.TipoEstadoNullException;
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
public class WndEstadoBus extends WndOpcionesMantenimiento {

	private static final long serialVersionUID = 1998913859947825458L;

	private Textbox txtDenominacion;
	private Combobox cboTipoEstado;
	private Textbox txtColor;
	
	private EstadoBus oEstadoBus = null;
	
	private String estados[] = new String[4];

	private TreeMap<String, Object> criteriosBusqueda = new TreeMap<String, Object>();
	private List<String> criteriosOrdenar = null;
	
	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.window.ui.IBase#onCreate()
	 */
	@Override
	public void onCreate() {
		estados[0]  = "INICIO";
		estados[1]  = "TRANSACCION";
		estados[2]  = "FIN";
		UtilData.cargarGenericData(cboTipoEstado, false);
		for (int e = 0; e < estados.length; e ++) {
			Comboitem oComboitem = new Comboitem(estados[e]);
			oComboitem.setValue(e);
			cboTipoEstado.appendChild(oComboitem);
		}
		criteriosOrdenar = new ArrayList<String>();
		criteriosOrdenar.add("denominacion");
	}

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.window.ui.IBase#initComponents()
	 */
	@Override
	public void initComponents() {
		txtDenominacion = (Textbox) getFellow("txtDenominacion");
		cboTipoEstado = (Combobox) getFellow("cboTipoEstado");
		txtColor = (Textbox) getFellow("txtColor");
	}
	
	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.window.ui.IOpcionesMantenimiento#onNew()
	 */
	@Override
	public void onNew() {
		cboTipoEstado.setSelectedIndex(0);
	}

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.window.ui.IOpcionesMantenimiento#onSearch()
	 */
	@Override
	public void onSearch() throws Exception {
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

				if(denominacion.trim().equals("")) {
					criteriosBusqueda.remove("denominacion");
				}else{criteriosBusqueda.put("denominacion", "%" + denominacion + "%");}

				if(codigo.trim().equals("")) {
					criteriosBusqueda.remove("codigo");
				}else{criteriosBusqueda.put("codigo", "%" + codigo + "%");}

				if(nombreCorto.trim().equals("")) {
					criteriosBusqueda.remove("nombreCorto");
				}else{criteriosBusqueda.put("nombreCorto", "%" + nombreCorto + "%");}

				criteriosBusqueda.put("estadoRegistro", estadoRegistro);

				listarRegistros(ServiceLocator.getEstadoBusManager().buscarPorX(criteriosBusqueda, criteriosOrdenar));
			}
		});
	}

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.window.ui.IOpcionesMantenimiento#onRefresh(int)
	 */
	@Override
	public void onRefresh(int tab) throws Exception {
		if (!criteriosBusqueda.isEmpty()) {
			this.listarRegistros(ServiceLocator.getEstadoBusManager().buscarPorX(criteriosBusqueda, criteriosOrdenar));					
		}
	}

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.window.ui.IOpcionesMantenimiento#onModify()
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
		try {
			if (txtDenominacion.getText().trim().equals(""))
				throw new DenominacionNullException();
			else if (cboTipoEstado.getSelectedIndex() == 0)
				throw new TipoEstadoNullException();
			
			if (action==ACTION_NEW)
				oEstadoBus = new EstadoBus();
			
			Integer id = (textboxId.getText().equals("") ? 0 : new Integer(textboxId.getText()));

			oEstadoBus.setId(id);
			oEstadoBus.setDenominacion(txtDenominacion.getText().trim().toUpperCase());
			oEstadoBus.setColor(txtColor.getText().trim().toUpperCase());
			oEstadoBus.setTipoEstado((Integer) cboTipoEstado.getSelectedItem().getValue());
			oEstadoBus.setEstadoRegistro(Constantes.VALUE_ACTIVO);

			switch (action) {
				case ACTION_NEW:
					UtilData.auditarRegistro(oEstadoBus, getUsuario(), Executions.getCurrent());
					ServiceLocator.getEstadoBusManager().guardar(oEstadoBus);
					break;

				case ACTION_MODIFY:
					UtilData.auditarRegistro(oEstadoBus, true, getUsuario(), Executions.getCurrent());
					ServiceLocator.getEstadoBusManager().actualizar(oEstadoBus);
					break;
			}
			/*RECUPERA EL REGISTRO ACTUALIAZADO O EL NUEVO*/
			criteriosBusqueda.remove("denominacion");
			criteriosBusqueda.remove("codigo");
			criteriosBusqueda.remove("nombreCorto");
			criteriosBusqueda.put("denominacion", oEstadoBus.getDenominacion());
			criteriosBusqueda.put("estadoRegistro", Constantes.VALUE_ACTIVO);
			listarRegistros(ServiceLocator.getEstadoBusManager().buscarPorX(criteriosBusqueda, criteriosOrdenar));
			
		}catch (DenominacionNullException dnex){
			DlgMessage.information(Messages.getString("Denominacion"),txtDenominacion);
			throw new CancelaGrabacionException();
		}catch (TipoEstadoNullException tenex){
			DlgMessage.information(Messages.getString("WndEstadosBus.information.Estado"),cboTipoEstado);
			throw new CancelaGrabacionException();
		}catch (DenominacionDuplicadaException dndex){
			DlgMessage.information(Messages.getString("DenominacionDuplicada"),cboTipoEstado);
			throw new CancelaGrabacionException();
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

		ServiceLocator.getEstadoBusManager().inactivar(id);
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

	private void listarRegistros(ArrayList<EstadoBus> lstRegistros) {
		ArrayList<Object> lstEstadoBus = new ArrayList<Object>();

		for(int r = 0; r < lstRegistros.size(); r ++) {
			EstadoBus oEstadoBus = lstRegistros.get(r);
			ArrayList<Object> lstFila = new ArrayList<Object>();

			lstFila.add(oEstadoBus.getId());
			lstFila.add(r + 1);
			lstFila.add(oEstadoBus.getDenominacion());
			lstFila.add(estados[oEstadoBus.getTipoEstado()]);
			lstFila.add(oEstadoBus.getColor());

			lstEstadoBus.add(lstFila);
		}

		Util.llenarListbox(listboxLista, lstEstadoBus, true);
	}


	private void mantenimientoRegistro(Long id) throws Exception {
		oEstadoBus = ServiceLocator.getEstadoBusManager().buscarPorId(id);

		textboxId.setText(oEstadoBus.getId().toString());
		txtDenominacion.setText(oEstadoBus.getDenominacion());
		Util.seleccionarValorItemCombobox(cboTipoEstado, oEstadoBus.getTipoEstado());
		txtColor.setText(oEstadoBus.getColor());
	}
}