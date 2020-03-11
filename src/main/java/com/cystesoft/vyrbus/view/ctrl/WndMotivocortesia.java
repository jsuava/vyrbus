
package com.cystesoft.vyrbus.view.ctrl;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zul.Intbox;
import org.zkoss.zul.Textbox;

import com.cystesoft.vyrbus.model.bean.MotivoCortesia;
import com.cystesoft.vyrbus.service.exceptions.CancelaGrabacionException;
import com.cystesoft.vyrbus.service.exceptions.DenominacionDuplicadaException;
import com.cystesoft.vyrbus.service.exceptions.DenominacionNullException;
import com.cystesoft.vyrbus.service.exceptions.GenericException;
import com.cystesoft.vyrbus.service.locator.ServiceLocator;
import com.cystesoft.vyrbus.service.util.Constantes;
import com.cystesoft.vyrbus.service.util.Messages;
import com.cystesoft.vyrbus.service.util.Util;
import com.cystesoft.vyrbus.service.util.UtilData;
import com.cystesoft.vyrbus.view.ui.DlgMessage;
import com.cystesoft.vyrbus.view.ui.WndFiltrarParametros;
import com.cystesoft.vyrbus.view.ui.WndOpcionesMantenimiento;

public class WndMotivocortesia extends WndOpcionesMantenimiento  {
	private static final long serialVersionUID = -9184053961402319107L;
	
	private Textbox txtDenominacion;
	private Intbox intbxCodigoTitan;
	
	private MotivoCortesia motivoCortesia=null;
	private TreeMap<String, Object> criteriosBusqueda = new TreeMap<String, Object>();
	private List<String> criteriosOrdenar = null;
	
	/*
	 * (non-Javadoc)
	 * @see com.tepsa.sisvyr.view.ui.WndBase#onCreate()
	 */
	@Override
	public void onCreate() throws Exception {
		criteriosOrdenar = new ArrayList<String>();
		criteriosOrdenar.add("denominacion");	
	}

	/*
	 * (non-Javadoc)
	 * @see com.tepsa.sisvyr.view.ui.WndBase#initComponents()
	 */
	@Override
	public void initComponents() {
		txtDenominacion = (Textbox) this.getFellow("txtDenominacion");
		intbxCodigoTitan=(Intbox)this.getFellow("intbxCodigoTitan");
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.tepsa.sisvyr.view.ui.IOpcionesMantenimiento#onNew()
	 */
	@Override
	public void onNew() throws Exception {
		// TODO Auto-generated method stub
		
	}
	
/*
 * 
 *(non-Javadoc)
 * @see com.tepsa.sisvyr.view.ui.IOpcionesMantenimiento#onSearch()
 */
	@Override
	public void onSearch() throws Exception {
		 criteriosBusqueda = new TreeMap<String, Object>();
			final WndFiltrarParametros oWndFiltrar = new WndFiltrarParametros();
			oWndFiltrar.addParameter("Denominación", String.class);

			this.appendChild(oWndFiltrar);
			oWndFiltrar.setMode("modal");
			oWndFiltrar.addEventListener(com.cystesoft.vyrbus.view.ui.Events.ON_FILTER, new EventListener<Event>() {

				@Override
				public void onEvent(Event event) throws Exception {
					String denominacion = (String) oWndFiltrar.getParameterValue("Denominación");
					String estadoRegistro = Constantes.VALUE_ACTIVO;

					if(denominacion.trim().equals(""))
						criteriosBusqueda.remove("denominacion");
					else criteriosBusqueda.put("denominacion", "%"+denominacion +"%");
					
					criteriosBusqueda.put("estadoRegistro", estadoRegistro);
					listarRegistros(ServiceLocator.getMotivoCortesiaManager().buscarPorX(criteriosBusqueda, criteriosOrdenar));
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
			this.listarRegistros(ServiceLocator.getMotivoCortesiaManager().buscarPorX(criteriosBusqueda, criteriosOrdenar));					
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
		mantenimientoRegistro(id);
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
			if (txtDenominacion.getText().trim().isEmpty())
				throw new DenominacionNullException();
			else if (intbxCodigoTitan.getValue()==null || intbxCodigoTitan.getValue()<=0)
				throw new GenericException(Messages.getString("WndMotivoCortesia.information.noCodigotitan"),intbxCodigoTitan);
				
			
			if (action==ACTION_NEW){
				motivoCortesia = new MotivoCortesia();
				motivoCortesia.setId(null);
			}else{
				motivoCortesia.setId(new Integer(textboxId.getText()));
			}
			 motivoCortesia.setDenominacion(txtDenominacion.getText().trim().toUpperCase());
			 motivoCortesia.setCodigoTitan(intbxCodigoTitan.getValue());
			 motivoCortesia.setEstadoRegistro(Constantes.VALUE_ACTIVO);
			 			 
			 switch (action) {
				case ACTION_NEW:
					UtilData.auditarRegistro(motivoCortesia, getUsuario(), Executions.getCurrent());
					ServiceLocator.getMotivoCortesiaManager().guardar(motivoCortesia);
					textboxId.setText(motivoCortesia.getId().toString());
					break;
		
				case ACTION_MODIFY:
					UtilData.auditarRegistro(motivoCortesia, true, getUsuario(), Executions.getCurrent());
					ServiceLocator.getMotivoCortesiaManager().actualizar(motivoCortesia);
					break;
			}
			/*Recupera el registro actualizado o el Nuevo*/
			criteriosBusqueda = new TreeMap<String, Object>();
			criteriosBusqueda.put("id", motivoCortesia.getId());
			listarRegistros(ServiceLocator.getMotivoCortesiaManager().buscarPorX(criteriosBusqueda, criteriosOrdenar));
			
		}catch (GenericException gex){
			DlgMessage.information(gex.getMessage(),(gex.getObjectFocus()!=null?gex.getObjectFocus():null));
			throw new CancelaGrabacionException();
		}catch (DenominacionDuplicadaException ddex){
			DlgMessage.information(Messages.getString("DenominacionDuplicada"),txtDenominacion);
			throw new CancelaGrabacionException();
		}catch(DenominacionNullException dnex){
			DlgMessage.information(Messages.getString("Denominacion"),txtDenominacion);
			throw new CancelaGrabacionException();
		}catch (Exception ex) {
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
		ServiceLocator.getMotivoCortesiaManager().inactivar(id);
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
	 * .(non-Javadoc)
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
				if (listboxLista.getSelectedIndex() > -1) 
					this.mantenimientoRegistro(new Long((String) listboxLista.getSelectedItem().getValue()));
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

	/**
	 * Lista los registros enconstradors
	 * @param lstRegistros : array que contuienne los resultapdos
	 */
	private void listarRegistros(ArrayList<MotivoCortesia> lstRegistros) {
		ArrayList<Object> lstmotivoCortecia = new ArrayList<Object>();

		for(int r = 0; r < lstRegistros.size(); r ++) {
			MotivoCortesia motivoCortesia = lstRegistros.get(r);
			ArrayList<Object> lstFila = new ArrayList<Object>();

			lstFila.add(motivoCortesia.getId());
			lstFila.add(r + 1);
			lstFila.add(motivoCortesia.getDenominacion());
			lstFila.add(motivoCortesia.getCodigoTitan()!=null?motivoCortesia.getCodigoTitan():"");
			
			lstmotivoCortecia.add(lstFila);
		}
		Util.llenarListbox(listboxLista, lstmotivoCortecia, true);
	}

	private void mantenimientoRegistro(Long id) throws Exception {
		motivoCortesia = new MotivoCortesia();
		motivoCortesia = ServiceLocator.getMotivoCortesiaManager().buscarPorId(id);

		textboxId.setText(motivoCortesia.getId().toString());
		txtDenominacion.setText(motivoCortesia.getDenominacion());
		intbxCodigoTitan.setValue(motivoCortesia.getCodigoTitan()!=null?motivoCortesia.getCodigoTitan():null);
	}
	
}
