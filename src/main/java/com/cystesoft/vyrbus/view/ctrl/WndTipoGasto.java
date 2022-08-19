package com.cystesoft.vyrbus.view.ctrl;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zul.Radio;
import org.zkoss.zul.Textbox;

import com.cystesoft.vyrbus.model.bean.TipoGasto;
import com.cystesoft.vyrbus.service.exceptions.CancelaGrabacionException;
import com.cystesoft.vyrbus.service.exceptions.DenominacionDuplicadaException;
import com.cystesoft.vyrbus.service.exceptions.DenominacionNullException;
import com.cystesoft.vyrbus.service.exceptions.NombreCortoDuplicadoException;
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

public class WndTipoGasto extends WndOpcionesMantenimiento {
	private static final long serialVersionUID = 676794100028485848L;

	private Textbox txtDenominacion;
	private Textbox txtNombreCorto;
	private Textbox txtMaskFormato;
	private Radio rbGasto;
	private Radio rbIngreso;

	private TipoGasto tipoGasto=null;
	private TreeMap<String, Object> criteriosBusqueda = new TreeMap<>();
	private List<String> criteriosOrdenar = null;


	/*
	 * (non-Javadoc)
	 * @see com.tepsa.sisvyr.view.ui.IBase#onCreate()
	 */
	@Override
	public void onCreate() throws Exception {
		criteriosOrdenar = new ArrayList<>();
		criteriosOrdenar.add("denominacion");
		rbGasto.setChecked(true);
	}

	/*
	 * (non-Javadoc)
	 * @see com.tepsa.sisvyr.view.ui.IBase#initComponents()
	 */
	@Override
	public void initComponents() {
		txtDenominacion = (Textbox) this.getFellow("txtDenominacion");
		txtNombreCorto = (Textbox) this.getFellow("txtNombreCorto");
		txtMaskFormato = (Textbox) this.getFellow("txtMaskFormato");
		rbGasto = (Radio)this.getFellow("rbGasto");
		rbIngreso = (Radio)this.getFellow("rbIngreso");
	}


	/*
	 * (non-Javadoc)
	 * @see com.tepsa.sisvyr.view.ui.IOpcionesMantenimiento#onNew()
	 */
	@Override
	public void onNew() throws Exception {

		rbGasto.setChecked(true);
	}

	/*
	 * (non-Javadoc)
	 * @see com.tepsa.sisvyr.view.ui.IOpcionesMantenimiento#onSearch()
	 */
	@Override
	public void onSearch() throws Exception {
		 criteriosBusqueda = new TreeMap<>();
		final WndFiltrarParametros oWndFiltrar = new WndFiltrarParametros();

		oWndFiltrar.addParameter("Denominación", String.class);
		oWndFiltrar.addParameter("Nombre Corto", String.class);

		this.appendChild(oWndFiltrar);
		oWndFiltrar.setMode("modal");
		oWndFiltrar.addEventListener(com.cystesoft.vyrbus.view.ui.Events.ON_FILTER, new EventListener<Event>() {

			@Override
			public void onEvent(Event event) throws Exception {
				String denominacion = (String) oWndFiltrar.getParameterValue("Denominación");
				String nombreCorto = (String) oWndFiltrar.getParameterValue("Nombre Corto");
				String estadoRegistro = Constantes.VALUE_ACTIVO;

				if(denominacion.trim().equals("")){
					criteriosBusqueda.remove("denominacion");
				}else{criteriosBusqueda.put("denominacion", "%" + denominacion + "%");}

				if (nombreCorto.trim().equals("")){
					criteriosBusqueda.remove("nombreCorto");
				}else{criteriosBusqueda.put("nombreCorto", nombreCorto);}
				criteriosBusqueda.put("estadoRegistro", estadoRegistro);

				listarRegistros(ServiceLocator.getTipoGastoManager().buscarPorX(criteriosBusqueda, criteriosOrdenar));
			}
		});

	}

	/*
	 * (non-Javadoc)
	 * @see com.tepsa.sisvyr.view.ui.IOpcionesMantenimiento#onRefresh(int)
	 */
	@Override
	public void onRefresh(int tab) throws Exception {
		if (!criteriosBusqueda.isEmpty()) {
			this.listarRegistros(ServiceLocator.getTipoGastoManager().buscarPorX(criteriosBusqueda, criteriosOrdenar));
		}

	}

	/*
	 * (non-Javadoc)
	 * @see com.tepsa.sisvyr.view.ui.IOpcionesMantenimiento#onModify(int)
	 */
	@Override
	public void onModify(int tab) throws Exception {
		Long id = new Long(0);
		id = new Long((String) listboxLista.getSelectedItem().getValue());
		this.mantenimientoRegistro(id);

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
			if (txtDenominacion.getText().trim() == "" )
				throw new DenominacionNullException();

			if (action==ACTION_NEW){
				tipoGasto= new TipoGasto();
				tipoGasto.setId(null);
			}else{
				tipoGasto.setId(new Integer(textboxId.getText()));
			}

			tipoGasto.setDenominacion(txtDenominacion.getText().trim().toUpperCase());
			tipoGasto.setNombreCorto(txtNombreCorto.getText().trim().toUpperCase());
			tipoGasto.setMascara(txtMaskFormato.getText().trim().toUpperCase());
			tipoGasto.setEstadoRegistro(Constantes.VALUE_ACTIVO);
			tipoGasto.setTipoOperacion(rbGasto.isChecked()?Constantes.FALSE_VALUE:Constantes.TRUE_VALUE);

			switch (action) {
				case ACTION_NEW:
					UtilData.auditarRegistro(tipoGasto, getUsuario(), Executions.getCurrent());
					ServiceLocator.getTipoGastoManager().guardar(tipoGasto);
					textboxId.setText(tipoGasto.getId().toString());
					break;

				case ACTION_MODIFY:
					UtilData.auditarRegistro(tipoGasto, true, getUsuario(), Executions.getCurrent());
					ServiceLocator.getTipoGastoManager().actualizar(tipoGasto);
					break;
			}
			/*Recupera el registro actualizado o el Nuevo*/
			criteriosBusqueda = new TreeMap<>();
			criteriosBusqueda.put("id", tipoGasto.getId());
			listarRegistros(ServiceLocator.getTipoGastoManager().buscarPorX(criteriosBusqueda, criteriosOrdenar));

		}catch (DenominacionNullException dnex){
			DlgMessage.information(Messages.getString("Generales.information.noIngresoDenominacion"),txtDenominacion);
			throw new CancelaGrabacionException();
		}catch (DenominacionDuplicadaException ddex){
			DlgMessage.information(Messages.getString("Generales.information.denominacionDuplicada"),txtDenominacion);
			throw new CancelaGrabacionException();
		}catch(NombreCortoDuplicadoException ncdex){
			DlgMessage.information(Messages.getString("Generales.information.nombreCortoDuplicado"),txtNombreCorto);
			throw new CancelaGrabacionException();
		}catch(Exception ex){
			DlgMessage.error(this.getClass().getName()+" "+ex.getMessage());
			ex.printStackTrace(); throw new CancelaGrabacionException();
		}


	}

	/*
	 * (non-Javadoc)
	 * @see com.tepsa.sisvyr.view.ui.IOpcionesMantenimiento#onDelete(int)
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

		ServiceLocator.getTipoGastoManager().inactivar(id);

	}

	/*
	 * (non-Javadoc)
	 * @see com.tepsa.sisvyr.view.ui.IOpcionesMantenimiento#onPrint(int)
	 */
	@Override
	public void onPrint(int tab) throws Exception {
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
				if (listboxLista.getSelectedIndex() > -1) {
					this.mantenimientoRegistro(new Long((String) listboxLista.getSelectedItem().getValue()));
				}
			break;
		}
	}

	/*
	 * (non-Javadoc)
	 * @see org.zkoss.zul.Window#onClose()
	 */
	@Override
	public void onClose() {
		closeTabWindow();
	}

	private void listarRegistros(ArrayList<TipoGasto> lstRegistros) {
		ArrayList<Object> lstTipoGasto = new ArrayList<>();

		for(int r = 0; r < lstRegistros.size(); r ++) {
			TipoGasto oTipoGasto = lstRegistros.get(r);
			ArrayList<Object> lstFila = new ArrayList<>();

			lstFila.add(oTipoGasto.getId());
			lstFila.add(r + 1);
			lstFila.add(oTipoGasto.getTipoOperacion().intValue()==Constantes.FALSE_VALUE?"GASTO":"INGRESO");
			lstFila.add(oTipoGasto.getDenominacion());
			lstFila.add(oTipoGasto.getNombreCorto());
			lstFila.add(oTipoGasto.getMascara());

			lstTipoGasto.add(lstFila);
		}

		Util.llenarListbox(listboxLista, lstTipoGasto, true);
	}

	private void mantenimientoRegistro(Long id) throws Exception {
		tipoGasto = new TipoGasto();
		tipoGasto = ServiceLocator.getTipoGastoManager().buscarPorId(id);

		textboxId.setText(tipoGasto.getId().toString());
		if(tipoGasto.getTipoOperacion().intValue()==Constantes.FALSE_VALUE)
			rbGasto.setChecked(true);
		else
			rbIngreso.setChecked(true);
		txtDenominacion.setText(tipoGasto.getDenominacion());
		txtNombreCorto.setText(tipoGasto.getNombreCorto());
		txtMaskFormato.setText(tipoGasto.getMascara());
	}

}
