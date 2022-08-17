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
import org.zkoss.zul.Textbox;

import com.cystesoft.vyrbus.model.bean.TipoComprobante;
import com.cystesoft.vyrbus.service.exceptions.CancelaGrabacionException;
import com.cystesoft.vyrbus.service.exceptions.DenominacionDuplicadaException;
import com.cystesoft.vyrbus.service.exceptions.DenominacionNullException;
import com.cystesoft.vyrbus.service.exceptions.NombreCortoDuplicadoException;
import com.cystesoft.vyrbus.service.exceptions.NombreCortoNullException;
import com.cystesoft.vyrbus.service.exceptions.TipocomprobanteRubroNullException;
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
 * @since JDK1.6
 */
public class WndTipoComprobante extends WndOpcionesMantenimiento {

	private static final long serialVersionUID = 1998913859947825458L;

	private Textbox txtDenominacion;
	private Textbox txtNombreCorto;

	private Combobox cmbRubro;
	private TipoComprobante oTipoComprobante = null;

	private TreeMap<String, Object> criteriosBusqueda = new TreeMap<>();
	private List<String> criteriosOrdenar = null;

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.window.ui.IBase#onCreate()
	 */
	@Override
	public void onCreate() {
		criteriosOrdenar = new ArrayList<>();
		criteriosOrdenar.add("denominacion");
		UtilData.cargarRubroTipoComprobante(cmbRubro, false);
	}

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.window.ui.IBase#initComponents()
	 */
	@Override
	public void initComponents() {
		txtDenominacion = (Textbox) getFellow("txtDenominacion");
		txtNombreCorto = (Textbox) getFellow("txtNombreCorto");
		cmbRubro = (Combobox) getFellow("cmbRubro");
	}

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.window.ui.IOpcionesMantenimiento#onNew()
	 */
	@Override
	public void onNew() {
		cmbRubro.setSelectedIndex(0);
	}

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.window.ui.IOpcionesMantenimiento#onSearch()
	 */
	@Override
	public void onSearch() {
		final WndFiltrarParametros oWndFiltrar = new WndFiltrarParametros();

		oWndFiltrar.addParameter("Denominación", String.class);
		oWndFiltrar.addParameter("Nombre corto (o abreviatura)", String.class);

		this.appendChild(oWndFiltrar);
		oWndFiltrar.setMode("modal");
		oWndFiltrar.addEventListener(com.cystesoft.vyrbus.view.ui.Events.ON_FILTER, new EventListener<Event>() {

			@Override
			public void onEvent(Event event) throws Exception {
				String denominacion = (String) oWndFiltrar.getParameterValue("Denominación");
				String abreviatura = (String) oWndFiltrar.getParameterValue("Nombre corto (o abreviatura)");
				String estadoRegistro = Constantes.VALUE_ACTIVO;

				if (denominacion.trim().equals("")) {
					criteriosBusqueda.remove("denominacion");
				}else {criteriosBusqueda.put("denominacion", "%" + denominacion + "%");}

				if (abreviatura.trim().equals("")) {
					criteriosBusqueda.remove("abreviatura");
				}else {criteriosBusqueda.put("abreviatura", "%" + abreviatura + "%");}

				criteriosBusqueda.put("estadoRegistro", estadoRegistro);

				listarRegistros(ServiceLocator.getTipoComprobanteManager().buscarPorX(criteriosBusqueda, criteriosOrdenar));
			}
		});
	}

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.window.ui.IOpcionesMantenimiento#onRefresh(int)
	 */
	@Override
	public void onRefresh(int tab) throws Exception {
		if (!criteriosBusqueda.isEmpty()) {
			this.listarRegistros(ServiceLocator.getTipoComprobanteManager().buscarPorX(criteriosBusqueda, criteriosOrdenar));
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
			else if (txtNombreCorto.getText().trim().equals(""))
				throw new NombreCortoNullException();
			else if (cmbRubro.getSelectedIndex()<=0)
				throw new TipocomprobanteRubroNullException();

			if (action==ACTION_NEW)
				oTipoComprobante = new TipoComprobante();

			Integer id = (textboxId.getText().equals("") ? 0 : new Integer(textboxId.getText()));
			oTipoComprobante.setId(id);
			oTipoComprobante.setDenominacion(txtDenominacion.getText().trim().toUpperCase());
			oTipoComprobante.setAbreviatura(txtNombreCorto.getText().trim().toUpperCase());
			oTipoComprobante.setRubro(((Integer) cmbRubro.getSelectedItem().getValue()));
			oTipoComprobante.setEstadoRegistro(Constantes.VALUE_ACTIVO);

			switch (action) {
				case ACTION_NEW:
					UtilData.auditarRegistro(oTipoComprobante, getUsuario(), Executions.getCurrent());
					ServiceLocator.getTipoComprobanteManager().guardar(oTipoComprobante);
					textboxId.setText(oTipoComprobante.getId().toString());
					break;

				case ACTION_MODIFY:
					UtilData.auditarRegistro(oTipoComprobante, true, getUsuario(), Executions.getCurrent());
					ServiceLocator.getTipoComprobanteManager().actualizar(oTipoComprobante);
					break;
			}
			/*RECUPERA EL REGISTRO ACTUALIZADO O EL NUEVO*/
			criteriosBusqueda.remove("denominacion"); criteriosBusqueda.remove("abreviatura");
			criteriosBusqueda.put("denominacion", oTipoComprobante.getDenominacion());
			criteriosBusqueda.put("estadoRegistro", Constantes.VALUE_ACTIVO);
			listarRegistros(ServiceLocator.getTipoComprobanteManager().buscarPorX(criteriosBusqueda, criteriosOrdenar));

		}catch (DenominacionNullException dnex){
			DlgMessage.information(Messages.getString("Generales.information.noIngresoDenominacion"),txtDenominacion);
			throw new CancelaGrabacionException();
		}catch (NombreCortoNullException ncnex){
			DlgMessage.information(Messages.getString("Generales.information.noIngresoNombreCorto"),txtNombreCorto);
			throw new CancelaGrabacionException();
		}catch (DenominacionDuplicadaException ddex){
			DlgMessage.information(Messages.getString("Generales.information.denominacionDuplicada"),txtDenominacion);
			throw new CancelaGrabacionException();
		}catch (NombreCortoDuplicadoException ncdex){
			DlgMessage.information(Messages.getString("Generales.information.nombreCortoDuplicado"),txtNombreCorto);
			throw new CancelaGrabacionException();
		}catch(TipocomprobanteRubroNullException tcrnex){
			DlgMessage.information(Messages.getString("WndTipoComprobante.Information.Norubro"),cmbRubro);
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

		ServiceLocator.getTipoComprobanteManager().inactivar(id);
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

	private void listarRegistros(ArrayList<TipoComprobante> lstRegistros) {
		ArrayList<Object> lstTipoComprobantes = new ArrayList<>();

		for(int r = 0; r < lstRegistros.size(); r ++) {
			TipoComprobante oTipoComprobante = lstRegistros.get(r);
			ArrayList<Object> lstFila = new ArrayList<>();

			lstFila.add(oTipoComprobante.getId());
			lstFila.add(r + 1);
			lstFila.add(oTipoComprobante.getDenominacion());
			lstFila.add(oTipoComprobante.getAbreviatura());
			if (oTipoComprobante.getRubro().equals(Constantes.RUBRO_PASAJES))
				lstFila.add(Constantes.RUBRO_PASAJES_DESC);
			else if (oTipoComprobante.getRubro().equals(Constantes.RUBRO_CARGA))
				lstFila.add(Constantes.RUBRO_CARGA_DESC);
			else if (oTipoComprobante.getRubro().equals(Constantes.RUBRO_AMBOS))
				lstFila.add(Constantes.RUBRO_AMBOS_DESC);
			else
				lstFila.add("NO DEFINIDO");

			lstTipoComprobantes.add(lstFila);
		}

		Util.llenarListbox(listboxLista, lstTipoComprobantes, true);
	}

	private void mantenimientoRegistro(Long id) throws Exception {
		oTipoComprobante = ServiceLocator.getTipoComprobanteManager().buscarPorId(id);

		textboxId.setText(oTipoComprobante.getId().toString());
		txtDenominacion.setText(oTipoComprobante.getDenominacion());
		txtNombreCorto.setText(oTipoComprobante.getAbreviatura());
		Util.seleccionarValorItemCombobox(cmbRubro, oTipoComprobante.getRubro());
	}
}