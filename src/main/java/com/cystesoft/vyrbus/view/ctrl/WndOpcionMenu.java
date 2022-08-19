package com.cystesoft.vyrbus.view.ctrl;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zul.Checkbox;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Comboitem;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Spinner;
import org.zkoss.zul.Textbox;

import com.cystesoft.vyrbus.model.bean.OpcionMenu;
import com.cystesoft.vyrbus.service.exceptions.CancelaGrabacionException;
import com.cystesoft.vyrbus.service.exceptions.DenominacionDuplicadaException;
import com.cystesoft.vyrbus.service.exceptions.DenominacionNullException;
import com.cystesoft.vyrbus.service.exceptions.OpcionMenuNombreObjetoNullException;
import com.cystesoft.vyrbus.service.exceptions.OpcionMenuOrdenNullException;
import com.cystesoft.vyrbus.service.exceptions.OpcionMenuPadreNullException;
import com.cystesoft.vyrbus.service.exceptions.OpcionMenuUrlNullException;
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
 * @author José Abanto
 *
 */
public class WndOpcionMenu extends WndOpcionesMantenimiento {
	private static final long serialVersionUID = -312924285889846936L;

	private Textbox txtDenominacion;
	private Textbox txtNombreObjeto;
	private Textbox txtUrl;
	private Combobox cmbOpcionMenuPadre;
	private Spinner spOrden;
	private Checkbox ckbHabilitado;
	private Checkbox ckbMenuPadre;


	private OpcionMenu opcionMenu = null;

	private TreeMap<String, Object> condicionBusqueda = null;
	private List<String> criteriosOrdenar = null;


	/*
	 * (non-Javadoc)
	 * @see com.tepsa.sisvyr.view.ui.IBase#initComponents()
	 */
	@Override
	public void initComponents() {
		txtDenominacion = (Textbox) this.getFellow("txtDenominacion");
		txtNombreObjeto = (Textbox) this.getFellow("txtNombreObjeto");
		txtUrl = (Textbox) this.getFellow("txtUrl");
		cmbOpcionMenuPadre = (Combobox) this.getFellow("cmbOpcionMenuPadre");
		spOrden = (Spinner) this.getFellow("spOrden");
		ckbHabilitado = (Checkbox) this.getFellow("ckbHabilitado");
		ckbMenuPadre = (Checkbox) this.getFellow("ckbMenuPadre");
	}



	/*
	 * (non-Javadoc)
	 * @see com.tepsa.sisvyr.view.ui.IBase#onCreate()
	 */
	@Override
	public void onCreate() throws Exception {
		criteriosOrdenar = new ArrayList<>();
		criteriosOrdenar.add("opcionMenuPadre");
		criteriosOrdenar.add("denominacion");

	    UtilData.cargarDataCombo(cmbOpcionMenuPadre,OpcionMenu.class, false);
	}


	/*
	 * (non-Javadoc)
	 * @see com.tepsa.sisvyr.view.ui.IOpcionesMantenimiento#onNew()
	 */
	@Override
	public void onNew() throws Exception {
		opcionMenu = null;
		UtilData.cargarDataCombo(cmbOpcionMenuPadre,OpcionMenu.class, false);
		cmbOpcionMenuPadre.setSelectedIndex(0);
		ckbHabilitado.setChecked(true);
	}

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.window.IOpcionesMantenimiento#onClose()
	 */
	@Override
	public void onClose() {
		closeTabWindow();
	}

	/*
	 * (non-Javadoc)
	 * @see com.tepsa.sisvyr.view.ui.IOpcionesMantenimiento#onSearch()
	 */
	@Override
	public void onSearch() throws Exception {
		condicionBusqueda = new TreeMap<>();
		final WndFiltrarParametros oWndFiltrar = new WndFiltrarParametros();

		oWndFiltrar.addParameter("Denominación", OpcionMenu.class);

		this.appendChild(oWndFiltrar);
		oWndFiltrar.setMode("modal");
		oWndFiltrar.addEventListener(com.cystesoft.vyrbus.view.ui.Events.ON_FILTER, new EventListener<Event>() {

			@Override
			public void onEvent(Event event) throws Exception {

				if (oWndFiltrar.getParameterValue("Denominación") instanceof OpcionMenu){
					OpcionMenu opcionMenu = new OpcionMenu();
					opcionMenu.setId(((OpcionMenu) oWndFiltrar.getParameterValue("Denominación")).getId());

					listarOpcionMenul(ServiceLocator.getOpcionMenuManager().buscarMenus(opcionMenu.getId(),null));
				}else{
					String estadoRegistro = Constantes.VALUE_ACTIVO;
					condicionBusqueda.put("estadoRegistro", estadoRegistro);
					listarOpcionMenul(ServiceLocator.getOpcionMenuManager().buscarMenus(null,null));
					//listarOpcionMenul(ServiceLocator.getOpcionMenuMenager().buscarPorX(condicionBusqueda, criteriosOrdenar));
				}
			}
		});

	}

	/*
	 * (non-Javadoc)
	 * @see com.tepsa.sisvyr.view.ui.IOpcionesMantenimiento#onRefresh(int)
	 */
	@Override
	public void onRefresh(int tab) throws Exception {


	}

	/*
	 * (non-Javadoc)
	 * @see com.tepsa.sisvyr.view.ui.IOpcionesMantenimiento#onModify(int)
	 */
	@Override
	public void onModify(int tab) throws Exception {
		if (listboxLista.getSelectedIndex() >=0){
			UtilData.cargarDataCombo(cmbOpcionMenuPadre, OpcionMenu.class, false);
			mantenimiento();
		}

	}

	/*
	 * (non-Javadoc)
	 * @see com.tepsa.sisvyr.view.ui.IOpcionesMantenimiento#onCancel(int)
	 */
	@Override
	public void onCancel(int action) throws Exception {


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
			else if (spOrden.getText().isEmpty() || spOrden.getValue().intValue()<=0 )
				throw new OpcionMenuOrdenNullException();
			else if (!(ckbMenuPadre.isChecked())){
				if (!(cmbOpcionMenuPadre.getSelectedItem().getValue() instanceof OpcionMenu))
					throw new OpcionMenuPadreNullException();
				else if (txtNombreObjeto.getText().trim().isEmpty())
					throw new OpcionMenuNombreObjetoNullException();
				else if (txtUrl.getText().trim().isEmpty())
					throw new OpcionMenuUrlNullException();
			}



			if (action==ACTION_NEW)
				opcionMenu=new OpcionMenu();
			else
				opcionMenu.setId(new Integer(textboxId.getText()));

//			opcionMenu.setDenominacion(txtDenominacion.getText().trim().toUpperCase());
			opcionMenu.setDenominacion(txtDenominacion.getText().trim());
			opcionMenu.setNombreObjeto(txtNombreObjeto.getText().trim());
			opcionMenu.setUrl(txtUrl.getText().trim().trim());
			OpcionMenu opcionMenuPadre = new OpcionMenu();
			if (cmbOpcionMenuPadre.getSelectedItem().getValue() instanceof OpcionMenu){
				opcionMenuPadre.setId(((OpcionMenu) cmbOpcionMenuPadre.getSelectedItem().getValue()).getId());
				opcionMenu.setOpcionMenuPadre(opcionMenuPadre);
			}

			//opcionMenu.setOpcionMenuPadre(opcionMenuPadre);
			opcionMenu.setOrdenOpcionMenu(spOrden.getValue().intValue());
			if (ckbHabilitado.isChecked())
				opcionMenu.setHabilitado(Constantes.TRUE_VALUE);
//				opcionMenu.setHabilitado(Constantes.HABILITADO);
			else
				opcionMenu.setHabilitado(Constantes.FALSE_VALUE);
//				opcionMenu.setHabilitado(Constantes.DESHABILITADO);
			opcionMenu.setEstadoRegistro(Constantes.VALUE_ACTIVO);

			switch (action) {
				case ACTION_NEW:
					UtilData.auditarRegistro(opcionMenu,getUsuario(), Executions.getCurrent());
					ServiceLocator.getOpcionMenuManager().guardar(opcionMenu);
					break;

				case ACTION_MODIFY:
					UtilData.auditarRegistro(opcionMenu, true, getUsuario(), Executions.getCurrent());
					ServiceLocator.getOpcionMenuManager().actualizar(opcionMenu);
					break;
			}

			condicionBusqueda = new TreeMap<>();
			condicionBusqueda.put("id", opcionMenu.getId());
			listarOpcionMenul(ServiceLocator.getOpcionMenuManager().buscarMenus(null,opcionMenu.getId()));
			//listarOpcionMenul(ServiceLocator.getOpcionMenuMenager().buscarPorX(condicionBusqueda, criteriosOrdenar));

		} catch (OpcionMenuUrlNullException omunex){
			DlgMessage.information(Messages.getString("WndOpcionMenu.Information.NoUrl"),txtUrl);
			throw new CancelaGrabacionException();
		}catch (OpcionMenuNombreObjetoNullException pmnonex){
			DlgMessage.information(Messages.getString("WndOpcionMenu.Information.NoNombreObjeto"),txtNombreObjeto);
			throw new CancelaGrabacionException();
		} catch (OpcionMenuPadreNullException ompnex){
			DlgMessage.information(Messages.getString("WndOpcionMenu.Information.NoMenuPadre"),cmbOpcionMenuPadre);
			throw new CancelaGrabacionException();
		}catch (OpcionMenuOrdenNullException omonex){
			DlgMessage.information(Messages.getString("WndOpcionMenu.Information.NoOrdenOpcionMenu"),spOrden);
			throw new CancelaGrabacionException();
		}catch (DenominacionNullException nnex){
			DlgMessage.information(Messages.getString("Denominacion"),txtDenominacion);
			throw new CancelaGrabacionException();
		}catch (DenominacionDuplicadaException ddex) {
			DlgMessage.information(Messages.getString("DenominacionDuplicada"));
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
		Listitem listitem = listboxLista.getItemAtIndex(listboxLista.getSelectedIndex());
		if (((OpcionMenu)listitem.getValue()).getOpcionMenuPadre().getId()==null)
			//Anula menu padre
			ServiceLocator.getOpcionMenuManager().inactivar(((OpcionMenu) listitem.getValue()).getId().longValue());
		else
			//Anula menu hijo
			ServiceLocator.getOpcionMenuManager().inactivar(((OpcionMenu) listitem.getValue()).getOpcionMenuPadre().getId().longValue());


	}

	/*
	 * (non-Javadoc)
	 * @see com.tepsa.sisvyr.view.ui.IOpcionesMantenimiento#onPrint(int)
	 */
	@Override
	public void onPrint(int tab) throws Exception {


	}

	/*
	 * (non-Javadoc)
	 * @see com.tepsa.sisvyr.view.ui.IOpcionesMantenimiento#onExport(int)
	 */
	@Override
	public void onExport(int tab) throws Exception {


	}

	/*
	 * (non-Javadoc)
	 * @see com.tepsa.sisvyr.view.ui.IOpcionesMantenimiento#onHelp()
	 */
	@Override
	public void onHelp() throws Exception {


	}

	/*
	 * (non-Javadoc)
	 * @see com.tepsa.sisvyr.view.ui.IOpcionesMantenimiento#onChangeTab(int)
	 */
	@Override
	public void onChangeTab(int tab) throws Exception {
		if (listboxLista.getSelectedIndex() >=0){
			mantenimiento();
		}
	}


	private void listarOpcionMenul(List<OpcionMenu> lstOpcionMenus){
		Listitem item = null;
		Listcell cell = null;
		Util.limpiarListbox(listboxLista);
		int x = 0;

		/*Recupera los Menus padres asociados a los hijos*/
		for(OpcionMenu oOpcionMenu : lstOpcionMenus){
			if (oOpcionMenu.getOpcionMenuPadre()!=null){
				item = new Listitem();
				if (oOpcionMenu.getOpcionMenuPadre() !=null){
					x += +1;
					cell = new Listcell((Integer.toString(x)));
					item.appendChild(cell); //Correlativo
					cell = new Listcell(oOpcionMenu.getOpcionMenuPadre().getDenominacion());
				}else{
					cell = new Listcell((""));
					item.appendChild(cell); //Correlativo
					cell = new Listcell("");
				}
				item.appendChild(cell);
				cell = new Listcell(oOpcionMenu.getDenominacion());
				item.appendChild(cell);
				cell = new Listcell(oOpcionMenu.getNombreObjeto());
				item.appendChild(cell);
				cell = new Listcell(oOpcionMenu.getUrl());
				item.appendChild(cell);
				cell = new Listcell(oOpcionMenu.getOrdenOpcionMenu().toString());
				item.appendChild(cell);
//				if (oOpcionMenu.getHabilitado().equals(Constantes.HABILITADO))
				if (oOpcionMenu.getHabilitado().equals(Constantes.TRUE_VALUE))
					cell = new Listcell(Constantes.LABEL_ACTIVO_DESCP);
				else
					cell = new Listcell(Constantes.LABEL_INACTIVO_DESCP);
				item.appendChild(cell);
				item.setValue(oOpcionMenu);
				listboxLista.appendChild(item);
			}
		}


		/*Recupera menus sin asociar*/
		Boolean existe = false;
		for(OpcionMenu oOpcionMenu : lstOpcionMenus){
			if (oOpcionMenu.getOpcionMenuPadre()==null){
				existe = false;
				for(OpcionMenu oOpcionMenuP : lstOpcionMenus){
					if (oOpcionMenuP.getOpcionMenuPadre()!=null){
						if (oOpcionMenuP.getOpcionMenuPadre().getId()==oOpcionMenu.getId()){
							existe=true;
							break;
						}
					}
				}
				if (!existe){
					item = new Listitem();
					if (oOpcionMenu.getOpcionMenuPadre() !=null){
						cell = new Listcell((""));
						item.appendChild(cell); //Correlativo
						cell = new Listcell(oOpcionMenu.getOpcionMenuPadre().getDenominacion());
					}else{
						cell = new Listcell((""));
						item.appendChild(cell); //Correlativo
						cell = new Listcell("");
					}
					item.appendChild(cell);
					cell = new Listcell(oOpcionMenu.getDenominacion());
					item.appendChild(cell);
					cell = new Listcell(oOpcionMenu.getNombreObjeto());
					item.appendChild(cell);
					cell = new Listcell(oOpcionMenu.getUrl());
					item.appendChild(cell);
					cell = new Listcell(oOpcionMenu.getOrdenOpcionMenu().toString());
					item.appendChild(cell);
//					if (oOpcionMenu.getHabilitado().equals(Constantes.HABILITADO))
					if (oOpcionMenu.getHabilitado().equals(Constantes.TRUE_VALUE))
						cell = new Listcell(Constantes.LABEL_ACTIVO_DESCP);
					else
						cell = new Listcell(Constantes.LABEL_INACTIVO_DESCP);
					item.appendChild(cell);
					item.setValue(oOpcionMenu);
					listboxLista.appendChild(item);
				}

			}

		}




	}

	/**
	 * Recupera datos para la edicion.
	 * @throws Exception
	 */
	private void mantenimiento() throws Exception{
		ckbMenuPadre.setDisabled(true);

		txtDenominacion.setText("");
		txtNombreObjeto.setText("");
		txtUrl.setText("");

		opcionMenu=new OpcionMenu();
		Listitem listitem = listboxLista.getItemAtIndex(listboxLista.getSelectedIndex());
		opcionMenu = ServiceLocator.getOpcionMenuManager().buscarPorId(((OpcionMenu) listitem.getValue()).getOpcionMenuPadre().getId().longValue());

		textboxId.setText(opcionMenu.getId().toString());
		/*verifica si es un menu padre*/
		if (opcionMenu.getNombreObjeto()==null){
			if (opcionMenu.getOpcionMenuPadre() != null)
				Util.seleccionarValorItemCombo(OpcionMenu.class, cmbOpcionMenuPadre, opcionMenu.getOpcionMenuPadre().getId());
			txtDenominacion.setText(opcionMenu.getDenominacion());
			ckbMenuPadre.setChecked(true);
			txtNombreObjeto.setDisabled(true);
			txtUrl.setDisabled(true);
		}else{
			ckbMenuPadre.setChecked(false);
			Util.seleccionarValorItemCombo(OpcionMenu.class, cmbOpcionMenuPadre, opcionMenu.getOpcionMenuPadre().getId());
			//opcionMenu = ServiceLocator.getOpcionMenuMenager().buscarPorId();
			txtDenominacion.setText(opcionMenu.getDenominacion());
			txtNombreObjeto.setText(opcionMenu.getNombreObjeto());
			txtUrl.setText(opcionMenu.getUrl());
			txtNombreObjeto.setDisabled(false);
			txtUrl.setDisabled(false);
		}

		spOrden.setValue(opcionMenu.getOrdenOpcionMenu());
//		if (opcionMenu.getHabilitado().equals(Constantes.HABILITADO))
		if (opcionMenu.getHabilitado().equals(Constantes.TRUE_VALUE))
			ckbHabilitado.setChecked(true);
		else
			ckbHabilitado.setChecked(false);
	}


	public  void onCheckEsMenuPadre () throws Exception{
		if (ckbMenuPadre.isChecked()){
			ArrayList<OpcionMenu> lstOpcionMenu = ServiceLocator.getOpcionMenuManager().buscarPorEstadoRegistro(Constantes.VALUE_ACTIVO, "denominacion");
			cmbOpcionMenuPadre.getItems().clear();
			UtilData.cargarGenericData(cmbOpcionMenuPadre, false);
			for (OpcionMenu opcionMenu : lstOpcionMenu) {
				Comboitem oComboitem = new Comboitem();
				oComboitem.setLabel(opcionMenu.getDenominacion());
				oComboitem.setValue(opcionMenu);
				cmbOpcionMenuPadre.appendChild(oComboitem);
			}
			txtNombreObjeto.setDisabled(true);
			txtUrl.setDisabled(true);
			txtNombreObjeto.setText("");
			txtUrl.setText("");
		}else{
			UtilData.cargarDataCombo(cmbOpcionMenuPadre,OpcionMenu.class, false);
			txtNombreObjeto.setDisabled(false);
			txtUrl.setDisabled(false);
			if (opcionMenu!=null){
				txtNombreObjeto.setText(opcionMenu.getNombreObjeto());
				txtUrl.setText(opcionMenu.getUrl());
				Util.seleccionarValorItemCombo(OpcionMenu.class, cmbOpcionMenuPadre, opcionMenu.getOpcionMenuPadre().getId());
			}

		}


	}


}
