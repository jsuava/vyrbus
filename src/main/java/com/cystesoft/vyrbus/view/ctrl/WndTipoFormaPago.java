/**
 * Proyecto		: SISVYR
 * Sistema		: Sistema de Ventas y Reservas
 * Descripción	: 
 * Autor		: jM
 * Fecha		: 12/06/2012
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

import com.cystesoft.vyrbus.model.bean.FormaPago;
import com.cystesoft.vyrbus.model.bean.TipoFormaPago;
import com.cystesoft.vyrbus.service.exceptions.CancelaGrabacionException;
import com.cystesoft.vyrbus.service.exceptions.DenominacionDuplicadaException;
import com.cystesoft.vyrbus.service.exceptions.DenominacionNullException;
import com.cystesoft.vyrbus.service.exceptions.FormaPagoNullException;
import com.cystesoft.vyrbus.service.exceptions.NombreCortoDuplicadoException;
import com.cystesoft.vyrbus.service.exceptions.NombreCortoNullException;
import com.cystesoft.vyrbus.service.exceptions.TipoIngresoLiquidacionNullException;
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
public class WndTipoFormaPago extends WndOpcionesMantenimiento {
	private static final long serialVersionUID = -4832102836139355700L;
	private Combobox cboFormaPago;
	private Textbox txtDenominacion;
	private Textbox txtNombreCorto;
	private Combobox cboTipoIngresoLiquidacion;
	
	private TipoFormaPago oTipoFormaPago=null;
	private TreeMap<String, Object> criteriosBusqueda = new TreeMap<String, Object>();
	private List<String> criteriosOrdenar = null;

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.window.ui.IBase#onCreate()
	 */
	@Override
	public void onCreate() throws Exception {
		UtilData.cargarDataCombo(cboFormaPago, FormaPago.class, false);
		UtilData.cargarDataCombo(cboTipoIngresoLiquidacion, UtilData.DATA_TIPO_INGRESO_LIQUIDACION, false);
		criteriosOrdenar = new ArrayList<String>();
		criteriosOrdenar.add("denominacion");
	}
	
	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.window.ui.IBase#initComponents()
	 */
	@Override
	public void initComponents() {
		cboFormaPago = (Combobox) getFellow("cboFormaPago");
		txtDenominacion = (Textbox) getFellow("txtDenominacion");
		cboTipoIngresoLiquidacion = (Combobox) getFellow("cboTipoIngresoLiquidacion");
		txtNombreCorto = (Textbox) getFellow("txtNombreCorto");
	}

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.window.ui.IOpcionesMantenimiento#onNew()
	 */
	@Override
	public void onNew() {
		cboFormaPago.setSelectedIndex(0);
		cboTipoIngresoLiquidacion.setSelectedIndex(0);

	}

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.window.ui.IOpcionesMantenimiento#onSearch()
	 */
	@Override
	public void onSearch() {
		final WndFiltrarParametros oWndFiltrar = new WndFiltrarParametros();
		
		oWndFiltrar.addParameter("1. Forma de Pago", FormaPago.class);
		oWndFiltrar.addParameter("2. Denominación", String.class);
		oWndFiltrar.addParameter("3. Nombre corto (o sufijo)", String.class);	

		this.appendChild(oWndFiltrar);
		oWndFiltrar.setMode("modal");
		oWndFiltrar.addEventListener(com.cystesoft.vyrbus.view.ui.Events.ON_FILTER, new EventListener<Event>() {

			@Override
			public void onEvent(Event event) throws Exception {
				Integer formaPago = (Integer) oWndFiltrar.getParameterValue("1. Forma de Pago");
				String denominacion = (String) oWndFiltrar.getParameterValue("2. Denominación");
				String nombreCorto = (String) oWndFiltrar.getParameterValue("3. Nombre corto (o sufijo)");
				String estadoRegistro = Constantes.VALUE_ACTIVO;

				if(denominacion.trim().equals("")){
					criteriosBusqueda.remove("denominacion");
				}else{criteriosBusqueda.put("denominacion", "%" + denominacion + "%");}

				if(nombreCorto.trim().equals("")){
					criteriosBusqueda.remove("nombreCorto");
				}else{criteriosBusqueda.put("nombreCorto", "%" + nombreCorto + "%");}
				
				if (formaPago == null){
					criteriosBusqueda.remove("formaPago");
				}else{
					FormaPago oFormaPago = new FormaPago();
					oFormaPago.setId(formaPago);
					criteriosBusqueda.put("formaPago", oFormaPago);
				}				
				criteriosBusqueda.put("estadoRegistro", estadoRegistro);
				
				listarRegistros(ServiceLocator.getTipoFormaPagoManager().buscarPorX(criteriosBusqueda, criteriosOrdenar));
			}
		});
	}

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.window.ui.IOpcionesMantenimiento#onRefresh(int)
	 */
	@Override
	public void onRefresh(int tab) throws Exception {
		if (!criteriosBusqueda.isEmpty()) {
			this.listarRegistros(ServiceLocator.getTipoFormaPagoManager().buscarPorX(criteriosBusqueda, criteriosOrdenar));					
		}
	}

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.window.ui.IOpcionesMantenimiento#onModify(int)
	 */
	@Override
	public void onModify(int tab) throws Exception {
		// TODO Auto-generated method stub
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
			if (!(cboFormaPago.getSelectedItem().getValue() instanceof FormaPago))
				throw new FormaPagoNullException();
			else if (txtDenominacion.getText().trim().equals(""))
				throw new DenominacionNullException();
			else if (txtNombreCorto.getText().trim().equals(""))
				throw new NombreCortoNullException();
			else if (!(cboTipoIngresoLiquidacion.getSelectedIndex() > 0 ))
				throw new TipoIngresoLiquidacionNullException();
			
			if (action==ACTION_NEW)
				oTipoFormaPago = new TipoFormaPago();
			
			Integer id = (textboxId.getText().equals("") ? 0 : new Integer(textboxId.getText()));
			oTipoFormaPago.setId(id);
			if (cboFormaPago.getSelectedIndex() > -1) {
				FormaPago oFormaPago = new FormaPago();
				oFormaPago.setId(((FormaPago) cboFormaPago.getSelectedItem().getValue()).getId());
				oTipoFormaPago.setFormaPago(oFormaPago);
			}
			oTipoFormaPago.setDenominacion(txtDenominacion.getText().trim().toUpperCase());
			oTipoFormaPago.setNombreCorto(txtNombreCorto.getText().trim().toUpperCase());
			oTipoFormaPago.setTipoIngresoLiquidacion((Integer) cboTipoIngresoLiquidacion.getSelectedItem().getValue());
			oTipoFormaPago.setEsPromocion(0);
			oTipoFormaPago.setEstadoRegistro(Constantes.VALUE_ACTIVO);

			switch (action) {
				case ACTION_NEW:
					UtilData.auditarRegistro(oTipoFormaPago, getUsuario(), Executions.getCurrent());
					ServiceLocator.getTipoFormaPagoManager().guardar(oTipoFormaPago);
					textboxId.setText((new Long(oTipoFormaPago.getId()).toString()));
					break;
		
				case ACTION_MODIFY:
					UtilData.auditarRegistro(oTipoFormaPago, true, getUsuario(), Executions.getCurrent());
					ServiceLocator.getTipoFormaPagoManager().actualizar(oTipoFormaPago);
					break;
			}
			/*RECUEPRA EL REGISTRO ACTUALIZADO O EL NUEVO*/
			criteriosBusqueda.remove("denominacion");criteriosBusqueda.remove("nombreCorto");criteriosBusqueda.remove("formaPago");
			criteriosBusqueda.put("denominacion", oTipoFormaPago.getDenominacion());
			criteriosBusqueda.put("estadoRegistro", Constantes.VALUE_ACTIVO);
			listarRegistros(ServiceLocator.getTipoFormaPagoManager().buscarPorX(criteriosBusqueda, criteriosOrdenar));
			
		}catch (FormaPagoNullException fpnex){
			DlgMessage.information(Messages.getString("WndTipoFormaPago.information.noSeleccionoFormaPago"),cboFormaPago);
			throw new CancelaGrabacionException();
		}catch (DenominacionNullException dnex){
			DlgMessage.information(Messages.getString("Generales.information.noIngresoDenominacion"),txtDenominacion);
			throw new CancelaGrabacionException();
		}catch (NombreCortoNullException ncnex){
			DlgMessage.information(Messages.getString("Generales.information.noIngresoNombreCorto"),txtNombreCorto);
			throw new CancelaGrabacionException();
		}catch (TipoIngresoLiquidacionNullException tilnex){
			DlgMessage.information(Messages.getString("WndTipoFormaPago.information.noSeleccionoTipoIngresoLiquidacion"),cboTipoIngresoLiquidacion);
			throw new CancelaGrabacionException();
		}catch (DenominacionDuplicadaException ddex){
			DlgMessage.information(Messages.getString("Generales.information.denominacionDuplicada"),txtDenominacion);
			throw new CancelaGrabacionException();
		}catch (NombreCortoDuplicadoException ncdex){
			DlgMessage.information(Messages.getString("Generales.information.nombreCortoDuplicado"),txtNombreCorto);
			throw new CancelaGrabacionException();
		}catch(Exception ex){
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

		ServiceLocator.getTipoFormaPagoManager().inactivar(id);
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
	
	private void mantenimientoRegistro(Long id) throws Exception {
		oTipoFormaPago = ServiceLocator.getTipoFormaPagoManager().buscarPorId(id);
		textboxId.setText(oTipoFormaPago.getId().toString());
		Util.seleccionarValorItemCombo(FormaPago.class, cboFormaPago, oTipoFormaPago.getFormaPago().getId());
		txtDenominacion.setText(oTipoFormaPago.getDenominacion());
		txtNombreCorto.setText(oTipoFormaPago.getNombreCorto());
		Util.seleccionarValorItemCombobox(cboTipoIngresoLiquidacion, oTipoFormaPago.getTipoIngresoLiquidacion());
	}

	private void listarRegistros(ArrayList<TipoFormaPago> lstRegistros) throws Exception {
		ArrayList<Object> lstTipoFormasPago = new ArrayList<Object>();

		for(int r = 0; r < lstRegistros.size(); r ++) {
			TipoFormaPago oTipoFormaPago = lstRegistros.get(r);
			ArrayList<Object> lstFila = new ArrayList<Object>();
			String[] tipoIngresoLiquidacion = new String[3];
			
			if (oTipoFormaPago.getFormaPago().getDenominacion()==null){
				FormaPago formaPago = new FormaPago();
				formaPago= ServiceLocator.getFormaPagoManager().buscarPorId(oTipoFormaPago.getFormaPago().getId().longValue());
				oTipoFormaPago.setFormaPago(formaPago);
			}
						
			lstFila.add(oTipoFormaPago.getId());
			lstFila.add(r + 1);
			lstFila.add(oTipoFormaPago.getFormaPago().getDenominacion());
			lstFila.add(oTipoFormaPago.getDenominacion());
			lstFila.add(oTipoFormaPago.getNombreCorto());

			
			tipoIngresoLiquidacion[0] = "Ingreso";
			tipoIngresoLiquidacion[1] = "Engreso";
			tipoIngresoLiquidacion[2] = "Ambos";
			
			lstFila.add(tipoIngresoLiquidacion[oTipoFormaPago.getTipoIngresoLiquidacion()]);

			lstTipoFormasPago.add(lstFila);
		}

		Util.llenarListbox(listboxLista, lstTipoFormasPago, true);
	}
}