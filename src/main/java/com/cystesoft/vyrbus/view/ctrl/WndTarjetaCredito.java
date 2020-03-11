/**
 * Proyecto		: SISVYR
 * Sistema		: Sistema de Ventas y Reservas
 * Descripción	: 
 * Autor		: jM
 * Fecha		: 10/06/2012
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

import com.cystesoft.vyrbus.model.bean.OperadorTarjetaCredito;
import com.cystesoft.vyrbus.model.bean.TarjetaCredito;
import com.cystesoft.vyrbus.service.exceptions.CancelaGrabacionException;
import com.cystesoft.vyrbus.service.exceptions.DenominacionDuplicadaException;
import com.cystesoft.vyrbus.service.exceptions.DenominacionNullException;
import com.cystesoft.vyrbus.service.exceptions.OperadorTarjetaCreditoNullException;
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
public class WndTarjetaCredito extends WndOpcionesMantenimiento {

	private static final long serialVersionUID = -1721402526478904823L;

	private Combobox cboOperadorTarjetaCredito;
	private Textbox txtDenominacion;
	
	private TarjetaCredito oTarjetaCredito = null;
	private TreeMap<String, Object> criteriosBusqueda = new TreeMap<String, Object>();
	private List<String> criteriosOrdenar = null;

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.window.ui.IBase#onCreate()
	 */
	@Override
	public void onCreate() throws Exception {
		UtilData.cargarDataCombo(cboOperadorTarjetaCredito, OperadorTarjetaCredito.class, false);
		criteriosOrdenar = new ArrayList<String>();
		criteriosOrdenar.add("denominacion");
	}
	
	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.window.ui.IBase#initComponents()
	 */
	@Override
	public void initComponents() {
		cboOperadorTarjetaCredito = (Combobox) getFellow("cboOperadorTarjetaCredito");
		txtDenominacion = (Textbox) getFellow("txtDenominacion");
	}
	
	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.window.ui.IOpcionesMantenimiento#onNew()
	 */
	@Override
	public void onNew() {
		cboOperadorTarjetaCredito.setSelectedIndex(0);
	}
	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.window.ui.IOpcionesMantenimiento#onSearch()
	 */
	@Override
	public void onSearch() {
		final WndFiltrarParametros oWndFiltrar = new WndFiltrarParametros();
		
		oWndFiltrar.addParameter("Operador de la Tarjeta de Crédito", OperadorTarjetaCredito.class);
		oWndFiltrar.addParameter("Denominación", String.class);

		this.appendChild(oWndFiltrar);
		oWndFiltrar.setMode("modal");
		oWndFiltrar.addEventListener(com.cystesoft.vyrbus.view.ui.Events.ON_FILTER, new EventListener<Event>() {

			@Override
			public void onEvent(Event event) throws Exception {
				Integer operadorTrajetaCredito = (Integer) oWndFiltrar.getParameterValue("Operador de la Tarjeta de Crédito");
				String denominacion = (String) oWndFiltrar.getParameterValue("Denominación");
				String estadoRegistro = Constantes.VALUE_ACTIVO;

				if(denominacion.trim().equals("")){
					criteriosBusqueda.remove("denominacion");
				}else{criteriosBusqueda.put("denominacion", "%" + denominacion + "%");}
				
				if (operadorTrajetaCredito == null){
					criteriosBusqueda.remove("operadorTarjetaCredito");
				}else{
					OperadorTarjetaCredito ooperadorTarjetaCredito = new OperadorTarjetaCredito();
					ooperadorTarjetaCredito.setId(operadorTrajetaCredito);
					criteriosBusqueda.put("operadorTarjetaCredito", ooperadorTarjetaCredito);
				}
		
				criteriosBusqueda.put("estadoRegistro", estadoRegistro);

				listarRegistros(ServiceLocator.getTarjetaCreditoManager().buscarPorX(criteriosBusqueda, criteriosOrdenar));
			}
		});
	}
	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.window.ui.IOpcionesMantenimiento#onRefresh(int)
	 */
	@Override
	public void onRefresh(int tab) throws Exception {
		if (!criteriosBusqueda.isEmpty()) {
			this.listarRegistros(ServiceLocator.getTarjetaCreditoManager().buscarPorX(criteriosBusqueda, criteriosOrdenar));					
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
	public void onSave(int action)  throws Exception{
		try{
			if (!(cboOperadorTarjetaCredito.getSelectedItem().getValue() instanceof OperadorTarjetaCredito))
				throw new OperadorTarjetaCreditoNullException();
			if (txtDenominacion.getText().trim().equals(""))
				throw new DenominacionNullException();
						
			if (action==ACTION_NEW)
				oTarjetaCredito = new TarjetaCredito();
			
			OperadorTarjetaCredito oOperadorTarjetaCredito = new OperadorTarjetaCredito();
			Integer id = (textboxId.getText().equals("") ? 0 : new Integer(textboxId.getText()));
			oOperadorTarjetaCredito.setId((Integer) ((OperadorTarjetaCredito) cboOperadorTarjetaCredito.getSelectedItem().getValue()).getId());
			
			oTarjetaCredito.setId(id);
			oTarjetaCredito.setOperadorTarjetaCredito(oOperadorTarjetaCredito);
			oTarjetaCredito.setDenominacion(txtDenominacion.getText().trim().toUpperCase());
			oTarjetaCredito.setEstadoRegistro(Constantes.VALUE_ACTIVO);

			switch (action) {
				case ACTION_NEW:
					UtilData.auditarRegistro(oTarjetaCredito, getUsuario(), Executions.getCurrent());
					ServiceLocator.getTarjetaCreditoManager().guardar(oTarjetaCredito);
					textboxId.setText(oTarjetaCredito.getId().toString());
					break;

				case ACTION_MODIFY:
					UtilData.auditarRegistro(oTarjetaCredito, true, getUsuario(), Executions.getCurrent());
					ServiceLocator.getTarjetaCreditoManager().actualizar(oTarjetaCredito);
					break;
			}
			/*RECUPERA EL REGISTRO ACTUALIZADO O EL  NUEVO*/
			criteriosBusqueda.remove("denominacion");
			criteriosBusqueda.remove("operadorTarjetaCredito");
			criteriosBusqueda.put("denominacion", oTarjetaCredito.getDenominacion());
			criteriosBusqueda.put("estadoRegistro", Constantes.VALUE_ACTIVO);
			listarRegistros(ServiceLocator.getTarjetaCreditoManager().buscarPorX(criteriosBusqueda, criteriosOrdenar));
			
		}catch (OperadorTarjetaCreditoNullException otcnex){
			DlgMessage.information(Messages.getString("WndTarjetaCredito.information.TarjetaCredito"),cboOperadorTarjetaCredito);
			throw new CancelaGrabacionException();
		}catch (DenominacionNullException dnex){
			DlgMessage.information(Messages.getString("Denominacion"),txtDenominacion);
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

		ServiceLocator.getTarjetaCreditoManager().inactivar(id);
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

	private void listarRegistros(ArrayList<TarjetaCredito> lstRegistros) throws Exception {
		ArrayList<Object> lstTarjetaCreditoes = new ArrayList<Object>();

		for(int r = 0; r < lstRegistros.size(); r ++) {
			TarjetaCredito oTarjetaCredito = lstRegistros.get(r);
			ArrayList<Object> lstFila = new ArrayList<Object>();

			if (oTarjetaCredito.getOperadorTarjetaCredito().getDenominacion()==null){
				OperadorTarjetaCredito operadorTarjetaCredito = new OperadorTarjetaCredito();
				operadorTarjetaCredito = ServiceLocator.getOperadorTarjetaCreditoManager().buscarPorId(oTarjetaCredito.getOperadorTarjetaCredito().getId().longValue());
				oTarjetaCredito.setOperadorTarjetaCredito(operadorTarjetaCredito);
			}
			
			lstFila.add(oTarjetaCredito.getId());
			lstFila.add(r + 1);
			lstFila.add(oTarjetaCredito.getOperadorTarjetaCredito().getDenominacion());
			lstFila.add(oTarjetaCredito.getDenominacion());

			lstTarjetaCreditoes.add(lstFila);
		}

		Util.llenarListbox(listboxLista, lstTarjetaCreditoes, true);
	}


	private void mantenimientoRegistro(Long id) throws Exception {
		oTarjetaCredito = ServiceLocator.getTarjetaCreditoManager().buscarPorId(id);

		textboxId.setText(oTarjetaCredito.getId().toString());
		Util.seleccionarValorItemCombo(OperadorTarjetaCredito.class, cboOperadorTarjetaCredito, oTarjetaCredito.getOperadorTarjetaCredito().getId());
		txtDenominacion.setText(oTarjetaCredito.getDenominacion());
	}
}