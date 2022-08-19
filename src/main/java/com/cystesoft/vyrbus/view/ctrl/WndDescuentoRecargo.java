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
import java.util.Locale;
import java.util.TreeMap;

import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zul.Checkbox;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Doublebox;
import org.zkoss.zul.Textbox;

import com.cystesoft.vyrbus.model.bean.DescuentoRecargo;
import com.cystesoft.vyrbus.service.exceptions.CancelaGrabacionException;
import com.cystesoft.vyrbus.service.exceptions.DenominacionDuplicadaException;
import com.cystesoft.vyrbus.service.exceptions.DenominacionNullException;
import com.cystesoft.vyrbus.service.exceptions.MontoDescuentoNullException;
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
public class WndDescuentoRecargo extends WndOpcionesMantenimiento {

	private static final long serialVersionUID = -2451880424114680419L;

	private Textbox txtDenominacion;
	private Doublebox dbMonto;
	private Checkbox chkMarcarPorcentaje;

	private DescuentoRecargo oDescuentoRecargo = null;

	private TreeMap<String, Object> criteriosBusqueda = new TreeMap<>();
	private List<String> criteriosOrdenar = null;

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.window.ui.IBase#onCreate()
	 */
	@Override
	public void onCreate() throws Exception {
		criteriosOrdenar = new ArrayList<>();
		criteriosOrdenar.add("denominacion");

		dbMonto.setLocale(Locale.US);
	}

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.window.ui.IBase#initComponents()
	 */
	@Override
	public void initComponents() {
		txtDenominacion = (Textbox) getFellow("txtDenominacion");
		dbMonto = (Doublebox) getFellow("dbMonto");
		chkMarcarPorcentaje = (Checkbox) getFellow("chkMarcarPorcentaje");
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
	public void onSearch() throws Exception {
		final WndFiltrarParametros oWndFiltrar = new WndFiltrarParametros();

		oWndFiltrar.addParameter("Denominación", String.class);
		oWndFiltrar.addParameter("Porcentaje", Combobox.class);

		this.appendChild(oWndFiltrar);
		oWndFiltrar.setMode("modal");
		oWndFiltrar.addEventListener(com.cystesoft.vyrbus.view.ui.Events.ON_FILTER, new EventListener<Event>() {

			@Override
			public void onEvent(Event event) throws Exception {
				String denominacion = (String) oWndFiltrar.getParameterValue("Denominación");
				Integer esPorcentaje = (Integer) oWndFiltrar.getParameterValue("Porcentaje");
				String estadoRegistro = Constantes.VALUE_ACTIVO;

				if (denominacion.trim().equals("")) {
					criteriosBusqueda.remove("denominacion");
				}else {criteriosBusqueda.put("denominacion", "%" + denominacion + "%");}

				if (esPorcentaje == -1 ){
					criteriosBusqueda.remove("esPorcentaje");
				}else {criteriosBusqueda.put("esPorcentaje", esPorcentaje);}

				criteriosBusqueda.put("estadoRegistro", estadoRegistro);

				listarRegistros(ServiceLocator.getDescuentoRecargoManager().buscarPorX(criteriosBusqueda, criteriosOrdenar));
			}
		});
	}
	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.window.ui.IOpcionesMantenimiento#onRefresh(int)
	 */
	@Override
	public void onRefresh(int tab) throws Exception {
		if (!criteriosBusqueda.isEmpty()) {
			this.listarRegistros(ServiceLocator.getDescuentoRecargoManager().buscarPorX(criteriosBusqueda, criteriosOrdenar));
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
			if (dbMonto.getText().equals("") || dbMonto.getValue().doubleValue() == 0)
				throw new MontoDescuentoNullException();


			if (action==ACTION_NEW)
				oDescuentoRecargo = new DescuentoRecargo();

			Integer id = (textboxId.getText().equals("") ? 0 : new Integer(textboxId.getText()));

			oDescuentoRecargo.setId(id);
			oDescuentoRecargo.setDenominacion(txtDenominacion.getText().trim().toUpperCase());
			oDescuentoRecargo.setMonto(dbMonto.getValue());
			oDescuentoRecargo.setEsPorcentaje((chkMarcarPorcentaje.isChecked() ? 1 : 0));

			oDescuentoRecargo.setEstadoRegistro(Constantes.VALUE_ACTIVO);

			switch (action) {
				case ACTION_NEW:
					UtilData.auditarRegistro(oDescuentoRecargo, getUsuario(), Executions.getCurrent());
					ServiceLocator.getDescuentoRecargoManager().guardar(oDescuentoRecargo);
					textboxId.setText(oDescuentoRecargo.getId().toString());
					break;

				case ACTION_MODIFY:
					UtilData.auditarRegistro(oDescuentoRecargo, true, getUsuario(), Executions.getCurrent());
					ServiceLocator.getDescuentoRecargoManager().actualizar(oDescuentoRecargo);
					break;
			}
			/*RECUPERA EL REGITRO ACTUALIZADO O EL NUEVO*/
			criteriosBusqueda.remove("denominacion");
			criteriosBusqueda.remove("esPorcentaje");
			criteriosBusqueda.put("denominacion", oDescuentoRecargo.getDenominacion());
			criteriosBusqueda.put("estadoRegistro", Constantes.VALUE_ACTIVO);
			listarRegistros(ServiceLocator.getDescuentoRecargoManager().buscarPorX(criteriosBusqueda, criteriosOrdenar));

		}catch (DenominacionNullException dnex){
			DlgMessage.information(Messages.getString("Denominacion"),txtDenominacion);
			throw new CancelaGrabacionException();
		}catch (MontoDescuentoNullException mnex){
			DlgMessage.information(Messages.getString("WndDescuentoRecargo.information.Monto"),dbMonto);
			throw new CancelaGrabacionException();
		}catch (DenominacionDuplicadaException ddex){
			DlgMessage.information(Messages.getString("DenominacionDuplicada"),txtDenominacion);
			throw new CancelaGrabacionException();
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

		ServiceLocator.getDescuentoRecargoManager().inactivar(id);
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

	private void listarRegistros(ArrayList<DescuentoRecargo> lstRegistros) {
		ArrayList<Object> lstDescuentosRecargos = new ArrayList<>();

		for(int r = 0; r < lstRegistros.size(); r ++) {
			DescuentoRecargo oDescuentoRecargo = lstRegistros.get(r);
			ArrayList<Object> lstFila = new ArrayList<>();

			lstFila.add(oDescuentoRecargo.getId());
			lstFila.add(r + 1);
			lstFila.add(oDescuentoRecargo.getDenominacion());
			lstFila.add(oDescuentoRecargo.getMonto());
			lstFila.add((oDescuentoRecargo.isEsPorcentaje() == 1 ? "Sí" : "No"));

			lstDescuentosRecargos.add(lstFila);
		}
		Util.llenarListbox(listboxLista, lstDescuentosRecargos, true);
	}


	private void mantenimientoRegistro(Long id) throws Exception {
		oDescuentoRecargo = ServiceLocator.getDescuentoRecargoManager().buscarPorId(id);

		textboxId.setText(oDescuentoRecargo.getId().toString());
		txtDenominacion.setText(oDescuentoRecargo.getDenominacion());
		dbMonto.setValue(oDescuentoRecargo.getMonto());
		chkMarcarPorcentaje.setChecked((oDescuentoRecargo.isEsPorcentaje() == 1 ? true : false));
	}
}