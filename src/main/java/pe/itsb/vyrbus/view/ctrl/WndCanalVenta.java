/**
 * Proyecto		: SISVYR
 * Sistema		: Sistema de Ventas y Reservas
 * Descripción	:
 * Autor		: jM
 * Fecha		: 30/04/2012
 */
package pe.itsb.vyrbus.view.ctrl;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zul.Textbox;

import pe.itsb.vyrbus.model.bean.CanalVenta;
import pe.itsb.vyrbus.service.exceptions.CancelaGrabacionException;
import pe.itsb.vyrbus.service.exceptions.ColorNullException;
import pe.itsb.vyrbus.service.exceptions.DenominacionDuplicadaException;
import pe.itsb.vyrbus.service.exceptions.DenominacionNullException;
import pe.itsb.vyrbus.service.exceptions.NombreCortoDuplicadoException;
import pe.itsb.vyrbus.service.exceptions.NombreCortoNullException;
import pe.itsb.vyrbus.service.locator.ServiceLocator;
import pe.itsb.vyrbus.service.util.Constantes;
import pe.itsb.vyrbus.service.util.Messages;
import pe.itsb.vyrbus.service.util.Util;
import pe.itsb.vyrbus.service.util.UtilData;
import pe.itsb.vyrbus.view.ui.DlgMessage;
import pe.itsb.vyrbus.view.ui.WndFiltrarParametros;
import pe.itsb.vyrbus.view.ui.WndOpcionesMantenimiento;

/**
 *
 * @author jM
 * @since JDK1.6
 */
public class WndCanalVenta extends WndOpcionesMantenimiento {
	private static final long serialVersionUID = 1998913859947825458L;

	private Textbox txtDenominacion;
	private Textbox txtNombreCorto;
	private Textbox txtColor;

	CanalVenta oCanalVenta = null;


	private TreeMap<String, Object> criteriosBusqueda = new TreeMap<>();
	private List<String> criteriosOrdenar = null;

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.window.IBase#onCreate()
	 */
	@Override
	public void onCreate() {
		criteriosOrdenar = new ArrayList<>();
		criteriosOrdenar.add("denominacion");


	}

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.window.IBase#initComponents()
	 */
	@Override
	public void initComponents() {
		txtDenominacion = (Textbox) getFellow("txtDenominacion");
		txtNombreCorto = (Textbox) getFellow("txtNombreCorto");
		txtColor=(Textbox)this.getFellow("txtColor");
	}

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.window.IOpcionesMantenimiento#onNew()
	 */
	@Override
	public void onNew() {
	}

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.window.IOpcionesMantenimiento#onSearch()
	 */
	@Override
	public void onSearch() {
		final WndFiltrarParametros oWndFiltrar = new WndFiltrarParametros();

		oWndFiltrar.addParameter("Denominación", String.class);
		oWndFiltrar.addParameter("Nombre corto (o abreviatura)", String.class);

		this.appendChild(oWndFiltrar);
		oWndFiltrar.setMode("modal");
		oWndFiltrar.addEventListener(pe.itsb.vyrbus.view.ui.Events.ON_FILTER, new EventListener<Event>() {

			@Override
			public void onEvent(Event event) throws Exception {
				String denominacion = (String) oWndFiltrar.getParameterValue("Denominación");
				String nombreCorto = (String) oWndFiltrar.getParameterValue("Nombre corto (o abreviatura)");
				String estadoRegistro = Constantes.VALUE_ACTIVO;

				if (denominacion.trim().equals("")) {
					criteriosBusqueda.remove("denominacion");
				}else {criteriosBusqueda.put("denominacion", "%"+denominacion+"%");}

				if (nombreCorto.trim().equals("")) {
					criteriosBusqueda.remove("nombreCorto");
				}else {criteriosBusqueda.put("nombreCorto", "%"+nombreCorto+"%");}
				criteriosBusqueda.put("estadoRegistro", estadoRegistro);

				listarRegistros(ServiceLocator.getCanalVentaManager().buscarPorX(criteriosBusqueda, criteriosOrdenar));
			}
		});
	}

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.window.ui.IOpcionesMantenimiento#onRefresh(int)
	 */
	@Override
	public void onRefresh(int tab) throws Exception {
		if (!criteriosBusqueda.isEmpty()) {
			this.listarRegistros(ServiceLocator.getCanalVentaManager().buscarPorX(criteriosBusqueda, criteriosOrdenar));
		}
	}

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.window.IOpcionesMantenimiento#onModify()
	 */
	@Override
	public void onModify(int tab) throws Exception {
		Long id = new Long(0);
		id = new Long((String) listboxLista.getSelectedItem().getValue());
		this.mantenimientoRegistro(id);
	}

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.window.IOpcionesMantenimiento#onCancel(int)
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
	 * @see com.tepsa.sisvyr.window.IOpcionesMantenimiento#onSave(int)
	 */
	@Override
	public void onSave(int action) throws Exception {
		try {
			if (txtDenominacion.getText().trim().equals(""))
				throw new DenominacionNullException();
			else if (txtNombreCorto.getText().trim().equals(""))
				throw new NombreCortoNullException();
			else if (txtColor.getText().trim().isEmpty())
				throw new ColorNullException();

			if (action==ACTION_NEW)
				 oCanalVenta = new CanalVenta();

			Integer id = (textboxId.getText().equals("") ? 0 : new Integer(textboxId.getText()));

			oCanalVenta.setId(id);
			oCanalVenta.setDenominacion(txtDenominacion.getText().trim().toUpperCase());
			oCanalVenta.setNombreCorto(txtNombreCorto.getText().trim().toUpperCase());
			oCanalVenta.setEstadoRegistro(Constantes.VALUE_ACTIVO);
			oCanalVenta.setColor(txtColor.getText().trim().toUpperCase());

			switch (action) {
				case ACTION_NEW:
					UtilData.auditarRegistro(oCanalVenta, getUsuario(), Executions.getCurrent());
					ServiceLocator.getCanalVentaManager().guardar(oCanalVenta);
					textboxId.setText(oCanalVenta.getId().toString());
					break;

				case ACTION_MODIFY:
					UtilData.auditarRegistro(oCanalVenta, true,getUsuario(), Executions.getCurrent());
					ServiceLocator.getCanalVentaManager().actualizar(oCanalVenta);
					break;
			}
			/*RECUPERA EL REGISTRO NUEVO O EL ACTUALIZADO*/
			criteriosBusqueda.remove("denominacion");criteriosBusqueda.remove("nombreCorto");
			criteriosBusqueda.put("denominacion", oCanalVenta.getDenominacion());
			criteriosBusqueda.put("nombreCorto", oCanalVenta.getNombreCorto());
			criteriosBusqueda.put("estadoRegistro", Constantes.VALUE_ACTIVO);
			listarRegistros(ServiceLocator.getCanalVentaManager().buscarPorX(criteriosBusqueda, criteriosOrdenar));

		}catch (ColorNullException cnex){
			DlgMessage.information(Messages.getString("WndCanalVenta.information.noIngresoColor"),txtColor);
			throw new CancelaGrabacionException();
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
		}catch (Exception ex){
			DlgMessage.error(this.getClass().getName()+" "+ex.getMessage());
			ex.printStackTrace(); throw new CancelaGrabacionException();
		}
	}

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.window.IOpcionesMantenimiento#onDelete()
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

		ServiceLocator.getCanalVentaManager().inactivar(id);
	}

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.window.IOpcionesMantenimiento#onPrint()
	 */
	@Override
	public void onPrint(int tab) {
		// TODO Auto-generated method stub
	}

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.window.IOpcionesMantenimiento#onExport()
	 */
	@Override
	public void onExport(int tab) {
		// TODO Auto-generated method stub
	}

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.window.IOpcionesMantenimiento#onHelp()
	 */
	@Override
	public void onHelp() {
		// TODO Auto-generated method stub
	}

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.window.IOpcionesMantenimiento#onChangeTab(int)
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

	private void listarRegistros(ArrayList<CanalVenta> lstRegistros) {
		ArrayList<Object> lstCanalesVenta = new ArrayList<>();

		for(int r = 0; r < lstRegistros.size(); r ++) {
			CanalVenta oCanalVenta = lstRegistros.get(r);
			ArrayList<Object> lstFila = new ArrayList<>();

			lstFila.add(oCanalVenta.getId());
			lstFila.add(r + 1);
			lstFila.add(oCanalVenta.getDenominacion());
			lstFila.add(oCanalVenta.getNombreCorto());

			lstCanalesVenta.add(lstFila);
		}

		Util.llenarListbox(listboxLista, lstCanalesVenta, true);
	}

	private void mantenimientoRegistro(Long id) throws Exception {
		 oCanalVenta = ServiceLocator.getCanalVentaManager().buscarPorId(id);

		textboxId.setText(oCanalVenta.getId().toString());
		txtDenominacion.setText(oCanalVenta.getDenominacion());
		txtNombreCorto.setText(oCanalVenta.getNombreCorto());
		txtColor.setText(oCanalVenta.getColor());
	}

}