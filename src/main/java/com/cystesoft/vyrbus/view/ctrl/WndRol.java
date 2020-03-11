package com.cystesoft.vyrbus.view.ctrl;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zk.ui.event.SelectEvent;
import org.zkoss.zul.Checkbox;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Tree;
import org.zkoss.zul.Treecell;
import org.zkoss.zul.Treechildren;
import org.zkoss.zul.Treeitem;
import org.zkoss.zul.Treerow;

import com.cystesoft.vyrbus.model.bean.OpcionMenu;
import com.cystesoft.vyrbus.model.bean.Rol;
import com.cystesoft.vyrbus.model.bean.RolOpcionMenu;
import com.cystesoft.vyrbus.model.bean.RolOpcionMenuID;
import com.cystesoft.vyrbus.service.exceptions.CancelaGrabacionException;
import com.cystesoft.vyrbus.service.exceptions.DenominacionDuplicadaException;
import com.cystesoft.vyrbus.service.exceptions.DenominacionNullException;
import com.cystesoft.vyrbus.service.locator.ServiceLocator;
import com.cystesoft.vyrbus.service.util.Constantes;
import com.cystesoft.vyrbus.service.util.Messages;
import com.cystesoft.vyrbus.service.util.UtilData;
import com.cystesoft.vyrbus.view.ui.DlgMessage;
import com.cystesoft.vyrbus.view.ui.WndFiltrarParametros;
import com.cystesoft.vyrbus.view.ui.WndOpcionesMantenimiento;

/**
 * 
 * @author JABANTO
 *
 */
public class WndRol extends WndOpcionesMantenimiento {
	/**
	 * 
	 */
	private static final long serialVersionUID = 8544619946784704797L;
	
	private Textbox txtDenominacion;
	private Checkbox cbNuevo;
	private Checkbox cbModificar;
	private Checkbox cbGrabar;
	private Checkbox cbEliminar;
	private Checkbox cbConsultar;
	private Checkbox cbImprimir;
	private Checkbox cbExportar;
	private Tree trmenus;
	
	private Rol rol = null;
	private RolOpcionMenu rolOpcionMenu = null;
	
	private TreeMap<String, Object> criteriosBusqueda = new TreeMap<String, Object>();
	private List<String> criteriosOrdenar = null;
	
	
	boolean estadoAntTreeitem=false;
	/*
	 * (non-Javadoc)
	 * @see com.tepsa.sisvyr.view.ui.IBase#initComponents()
	 */
	@Override
	public void initComponents() {
		txtDenominacion = (Textbox) this.getFellow("txtDenominacion");
		cbNuevo = (Checkbox) this.getFellow("cbNuevo");
		cbModificar = (Checkbox) this.getFellow("cbModificar");
		cbGrabar = (Checkbox) this.getFellow("cbGrabar");
		cbEliminar = (Checkbox) this.getFellow("cbEliminar");
		cbConsultar = (Checkbox) this.getFellow("cbConsultar");
		cbImprimir = (Checkbox) this.getFellow("cbImprimir");
		cbExportar = (Checkbox) this.getFellow("cbExportar");
		trmenus=(Tree)this.getFellow("trmenus");
		
		trmenus.addEventListener(Events.ON_SELECT,new EventListener<Event>() {
			@Override
			public void onEvent(Event event) throws Exception {
				// TODO Auto-generated method stub
				onCheckSelection(event);
			}
			
		} );
		
	}

	/*
	 * (non-Javadoc)
	 * @see com.tepsa.sisvyr.view.ui.IBase#onCreate()
	 */
	@Override
	public void onCreate() throws Exception {
		cargarOpcionesMenu();
	}
	
	
	/*
	 * (non-Javadoc)
	 * @see com.tepsa.sisvyr.view.ui.IOpcionesMantenimiento#onNew()
	 */
	@Override
	public void onNew() throws Exception {
		desmarcaTree();
		
	}

	/*
	 * (non-Javadoc)
	 * @see com.tepsa.sisvyr.view.ui.IOpcionesMantenimiento#onSearch()
	 */
	@Override
	public void onSearch() throws Exception {
		criteriosBusqueda = new TreeMap<String, Object>();
		final WndFiltrarParametros oWndFiltrar = new WndFiltrarParametros();

		oWndFiltrar.addParameter("Nombre del Rol", Rol.class);
		
		this.appendChild(oWndFiltrar);
		oWndFiltrar.setMode("modal");
		oWndFiltrar.addEventListener(com.cystesoft.vyrbus.view.ui.Events.ON_FILTER, new EventListener<Event>() {

			@Override
			public void onEvent(Event event) throws Exception {
				String estadoRegistro = Constantes.VALUE_ACTIVO;
				
				if (oWndFiltrar.getParameterValue("Nombre del Rol") instanceof Rol){
					Rol rol = new Rol();
					rol = (Rol) oWndFiltrar.getParameterValue("Nombre del Rol");
					
					criteriosBusqueda.put("denominacion", "%" + rol.getDenominacion() + "%");
				}
				criteriosBusqueda.put("estadoRegistro", estadoRegistro);
				ListarRoles(ServiceLocator.getRolManager().buscarPorX(criteriosBusqueda, criteriosOrdenar));
			}
		});
		
	}

	/*
	 * (non-Javadoc)
	 * @see com.tepsa.sisvyr.view.ui.IOpcionesMantenimiento#onRefresh(int)
	 */
	@Override
	public void onRefresh(int tab) throws Exception {
		if (!(criteriosBusqueda.isEmpty()))
			ListarRoles(ServiceLocator.getRolManager().buscarPorX(criteriosBusqueda, criteriosOrdenar));
	}

	/*
	 * (non-Javadoc)
	 * @see com.tepsa.sisvyr.view.ui.IOpcionesMantenimiento#onModify(int)
	 */
	@Override
	public void onModify(int tab) throws Exception {
		mantenimiento();
		
	}

	/*
	 * (non-Javadoc)
	 * @see com.tepsa.sisvyr.view.ui.IOpcionesMantenimiento#onCancel(int)
	 */
	@Override
	public void onCancel(int action) throws Exception {
		desmarcaTree();
		
	}

	@Override
	public void onClose() {
		closeTabWindow();
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
			
			if (action==ACTION_NEW)
				rol = new Rol();
			
			rol.setDenominacion(txtDenominacion.getText().trim().toUpperCase());
			if (cbNuevo.isChecked())
				rol.setNuevo(Constantes.TRUE_VALUE);
			else rol.setNuevo(Constantes.FALSE_VALUE);
			
			if (cbModificar.isChecked())
				rol.setModificar(Constantes.TRUE_VALUE);
			else rol.setModificar(Constantes.FALSE_VALUE);
			
			if (cbGrabar.isChecked())
				rol.setGrabar(Constantes.TRUE_VALUE);
			else rol.setGrabar(Constantes.FALSE_VALUE);
			
			if (cbEliminar.isChecked())
				rol.setEliminar(Constantes.TRUE_VALUE);
			else rol.setEliminar(Constantes.FALSE_VALUE);
			
			if (cbConsultar.isChecked())
				rol.setConsultar(Constantes.TRUE_VALUE);
			else rol.setConsultar(Constantes.FALSE_VALUE);
			
			if (cbImprimir.isChecked())
				rol.setImprimir(Constantes.TRUE_VALUE);
			else rol.setImprimir(Constantes.FALSE_VALUE);
			
			if (cbExportar.isChecked())
				rol.setExportar(Constantes.TRUE_VALUE);
			else rol.setExportar(Constantes.FALSE_VALUE);
			
			rol.setEstadoRegistro(Constantes.VALUE_ACTIVO);
			
			switch (action) {
				case ACTION_NEW:
					UtilData.auditarRegistro(rol, getUsuario(), Executions.getCurrent());
					ServiceLocator.getRolManager().guardar(rol);
					break;
				case ACTION_MODIFY:
					UtilData.auditarRegistro(rol, true, getUsuario(), Executions.getCurrent());
					ServiceLocator.getRolManager().actualizar(rol);
					break;
			}
			
			/* Guarda los accesos a los menus del sistema*/
			onSaveRolOpcionMenu(rol);
			desmarcaTree();
			
			criteriosBusqueda = new TreeMap<String, Object>();
			criteriosBusqueda.put("id", rol.getId());
			ListarRoles(ServiceLocator.getRolManager().buscarPorX(criteriosBusqueda, criteriosOrdenar));
			
			
		}catch (DenominacionNullException dnex){
			DlgMessage.information(Messages.getString("Denominacion"),txtDenominacion);
			throw new CancelaGrabacionException();
		}catch (DenominacionDuplicadaException ddex){
			DlgMessage.information(Messages.getString("DenominacionDuplicada"),txtDenominacion);
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
			id = (((Rol) listboxLista.getItemAtIndex(listboxLista.getSelectedIndex()).getValue()).getId().longValue());
			break;

		case TAB_MAINTENANCE:
			id = new Long(textboxId.getText());
			break;
	}

	ServiceLocator.getRolManager().inactivar(id);
		
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
		mantenimiento();
	}

	private void ListarRoles(List<Rol> lstRols){
		Listitem item = null;
		Listcell cell = null;
		listboxLista.getItems().clear();
		
		Integer x = listboxLista.getChildren().size();
		for(Rol rol : lstRols){
			x += +1;
			item = new Listitem();
			cell = new Listcell((x.toString()));
			item.appendChild(cell); //Correlativo
			cell = new Listcell(rol.getDenominacion());
			item.appendChild(cell);
			
			if (rol.getNuevo()==Constantes.TRUE_VALUE)
				cell = new Listcell(Constantes.LABEL_ACTIVO_DESCP);
			else
				cell = new Listcell(Constantes.LABEL_INACTIVO_DESCP);
			item.appendChild(cell);
			
			if (rol.getModificar()==Constantes.TRUE_VALUE)
				cell = new Listcell(Constantes.LABEL_ACTIVO_DESCP);
			else
				cell = new Listcell(Constantes.LABEL_INACTIVO_DESCP);
			item.appendChild(cell);
			
			if (rol.getGrabar()==Constantes.TRUE_VALUE)
				cell = new Listcell(Constantes.LABEL_ACTIVO_DESCP);
			else
				cell = new Listcell(Constantes.LABEL_INACTIVO_DESCP);
			item.appendChild(cell);
			
			if (rol.getEliminar()==Constantes.TRUE_VALUE)
				cell = new Listcell(Constantes.LABEL_ACTIVO_DESCP);
			else
				cell = new Listcell(Constantes.LABEL_INACTIVO_DESCP);
			item.appendChild(cell);
			
			if (rol.getConsultar()==Constantes.TRUE_VALUE)
				cell = new Listcell(Constantes.LABEL_ACTIVO_DESCP);
			else
				cell = new Listcell(Constantes.LABEL_INACTIVO_DESCP);
			item.appendChild(cell);
						
			if (rol.getImprimir()==Constantes.TRUE_VALUE)
				cell = new Listcell(Constantes.LABEL_ACTIVO_DESCP);
			else
				cell = new Listcell(Constantes.LABEL_INACTIVO_DESCP);
			item.appendChild(cell);
			
			if (rol.getExportar()==Constantes.TRUE_VALUE)
				cell = new Listcell(Constantes.LABEL_ACTIVO_DESCP);
			else
				cell = new Listcell(Constantes.LABEL_INACTIVO_DESCP);
			item.appendChild(cell);
						
			item.setValue(rol);
			listboxLista.appendChild(item);			
			}
	}
	
	public void mantenimiento(){
		Listitem itemList = listboxLista.getItemAtIndex(listboxLista.getSelectedIndex());
		rol = new Rol();
		rol=itemList.getValue();
		
		textboxId.setText(rol.getId().toString());
		txtDenominacion.setText(rol.getDenominacion());
		
		if (rol.getNuevo()==Constantes.TRUE_VALUE)
			cbNuevo.setChecked(true);
		else cbNuevo.setChecked(false);
		
		if (rol.getModificar()==Constantes.TRUE_VALUE)
			cbModificar.setChecked(true);
		else cbModificar.setChecked(false);
		
		if (rol.getGrabar()==Constantes.TRUE_VALUE)
			cbGrabar.setChecked(true);
		else cbGrabar.setChecked(false);
		
		if (rol.getEliminar()==Constantes.TRUE_VALUE)
			cbEliminar.setChecked(true);
		else cbEliminar.setChecked(false);
		
		if (rol.getConsultar()==Constantes.TRUE_VALUE)
			cbConsultar.setChecked(true);
		else cbConsultar.setChecked(false);
		
		if (rol.getImprimir()==Constantes.TRUE_VALUE)
			cbImprimir.setChecked(true);
		else cbImprimir.setChecked(false);
		
		if (rol.getExportar()==Constantes.TRUE_VALUE)
			cbExportar.setChecked(true);
		else cbExportar.setChecked(false);
		
		/*Recuepera los menus aociados al rol*/
		onSelectChange(rol);
	}

	/**
	 * Recupera los menus asociados al rol, si es que lo hay.
	 */
	public void onSelectChange(Rol rol){
		desmarcaTree();
	
			TreeMap<String, Object> criteriosBusqueda = new TreeMap<String, Object>();
			List<String> criteriosOrdenar = new  ArrayList<String>();;
			criteriosOrdenar.add("opcionMenu");
			criteriosBusqueda.put("rol", rol);
			criteriosBusqueda.put("estadoRegistro", Constantes.VALUE_ACTIVO);
			
			List<RolOpcionMenu> list= ServiceLocator.getRolOpcionMenuManager().buscarPorX(criteriosBusqueda, criteriosOrdenar);
			
			/*Selecciona el menú de la lista tree*/
			for (RolOpcionMenu rolOpcionMenu: list){
				Integer idOmRo=rolOpcionMenu.getOpcionMenu().getId();
				for (Treeitem treeitem : trmenus.getItems()){
					Integer idOmt=((OpcionMenu) treeitem.getValue()).getId();
					if(idOmt.intValue()==idOmRo.intValue()){
						treeitem.setSelected(true);
						//treeitem.setOpen(false);
						break;
					}
				}	
			}
	}
	
	/**
	 * Guarda Rol Opcion menu.
	 * @param action
	 * @throws Exception
	 */
	public void onSaveRolOpcionMenu(Rol rol) throws Exception {
			
			/*Recorre items del tree*/
			for (Treeitem treeitem : trmenus.getItems()){			
				OpcionMenu opcionMenu = new  OpcionMenu();
				opcionMenu.setId(((OpcionMenu) treeitem.getValue()).getId());
				
				rolOpcionMenu = new RolOpcionMenu();
				RolOpcionMenuID rolOpcionMenuID = new RolOpcionMenuID();
				rolOpcionMenuID.setIdRol(rol.getId());
				rolOpcionMenuID.setIdOpcionMenu(opcionMenu.getId());
				UtilData.auditarRegistro(rolOpcionMenuID, true, getUsuario(), Executions.getCurrent());	
				
				/*Valida si el rol y menu del item seleccionado exsisten el DB*/
				TreeMap<String, Object> criteriosBusqueda = new TreeMap<String, Object>();
				criteriosBusqueda.put("rol", rol);
				criteriosBusqueda.put("opcionMenu", opcionMenu);
				List<RolOpcionMenu> list= ServiceLocator.getRolOpcionMenuManager().buscarPorX(criteriosBusqueda, null);
				
				if (list.size() > 0){
					/*Valida si el item seleccionado existe en la Base de Datos*/
					for (RolOpcionMenu oRolOpcionMenu: list){
						
						if (oRolOpcionMenu.getRolOpcionMenuID().getIdOpcionMenu().equals(opcionMenu.getId())){
							/*Activa menu*/
							if (oRolOpcionMenu.getEstadoRegistro().equals(Constantes.VALUE_INACTIVO) && treeitem.isSelected()){
								ServiceLocator.getRolOpcionMenuManager().inactivarActivar(rolOpcionMenuID, Constantes.VALUE_ACTIVO);
							}else if (oRolOpcionMenu.getEstadoRegistro().equals(Constantes.VALUE_ACTIVO)  && treeitem.isSelected()==false){
								/*Inactiva menu*/
								ServiceLocator.getRolOpcionMenuManager().inactivarActivar(rolOpcionMenuID, Constantes.VALUE_INACTIVO);
								}
						}	
					}
				}else if (treeitem.isSelected()){
					rolOpcionMenu.setRolOpcionMenuID(rolOpcionMenuID);
					rolOpcionMenu.setRol(rol);
					rolOpcionMenu.setOpcionMenu(opcionMenu);
					rolOpcionMenu.setEstadoRegistro(Constantes.VALUE_ACTIVO);
					
					UtilData.auditarRegistro(rolOpcionMenu, getUsuario(), Executions.getCurrent());
					ServiceLocator.getRolOpcionMenuManager().guardar(rolOpcionMenu);
				}	
			}
	}

	/**
	 * CARGA MENUS
	 * @throws Exception
	 */
	public void cargarOpcionesMenu() throws Exception{
		List<OpcionMenu> list= ServiceLocator.getOpcionMenuManager().buscarPorEstadoRegistro(Constantes.VALUE_ACTIVO, "ordenOpcionMenu");
		
		Treechildren treechildren = new Treechildren();
		Treeitem treeitem = new Treeitem();
		Treerow treerow = new Treerow();
		Treecell treecell = new Treecell();
		
		/*SOPORTA HASTA MENUS BISNIETOS DEL PADRE*/
		for(OpcionMenu menuPadre: list){
			/*Agrega menus Padres*/
			if (menuPadre.getOpcionMenuPadre()==null){
				treeitem = new Treeitem();
				treecell = new Treecell(menuPadre.getDenominacion());
				treerow = new Treerow();
				treerow.appendChild(treecell);
				treeitem.setValue(menuPadre);
				treeitem.appendChild(treerow);
				
				/*HIJOS*/
				Treechildren treechildrenHijo = new Treechildren();
				for(OpcionMenu menuHijo: list){
					if (!(menuHijo.getOpcionMenuPadre()==null)){
						if (menuHijo.getOpcionMenuPadre().getId()==menuPadre.getId()){
							Treeitem treeitemHijo = new Treeitem();
							Treerow treerowHijo = new Treerow();
							Treecell treecellHijo = new Treecell(menuHijo.getDenominacion());
					
							treerowHijo.appendChild(treecellHijo);
							treeitemHijo.appendChild(treerowHijo);
							treeitemHijo.setValue(menuHijo);
							treechildrenHijo.appendChild(treeitemHijo);
							
							/*NIETOS*/
							Treechildren treechildrenNieto = new Treechildren();
							for(OpcionMenu menuNieto: list){
								if (!(menuNieto.getOpcionMenuPadre()==null)){
									if (menuNieto.getOpcionMenuPadre().getId()==menuHijo.getId()){
										
										Treeitem treeitemNieto = new Treeitem();
										Treerow treerowNieto = new Treerow();
										Treecell treecellNieto = new Treecell(menuNieto.getDenominacion());
									    
										treerowNieto.appendChild(treecellNieto);
										treeitemNieto.appendChild(treerowNieto);
										treeitemNieto.setValue(menuNieto);
										treechildrenNieto.appendChild(treeitemNieto);
									
										/*BISNIETOS*/
										Treechildren treechildrenBisnieto = new Treechildren();
										for(OpcionMenu menuBisnieto: list){
											if (!(menuBisnieto.getOpcionMenuPadre()==null)){
												if (menuBisnieto.getOpcionMenuPadre().getId()==menuNieto.getId()){
													
													Treeitem treeitemBisnieto = new Treeitem();
													Treerow treerowBisnieto = new Treerow();
													Treecell treecellBisnieto = new Treecell(menuBisnieto.getDenominacion());
												
													treerowBisnieto.appendChild(treecellBisnieto);
													treeitemBisnieto.appendChild(treerowBisnieto);
													treeitemBisnieto.setValue(menuBisnieto);
													treechildrenBisnieto.appendChild(treeitemBisnieto);
												
													treeitemNieto.appendChild(treechildrenBisnieto);
													treeitemNieto.setOpen(false);
												}
											}
										}
										treeitemHijo.appendChild(treechildrenNieto);
										treeitemHijo.setOpen(false);
									}
								}
							}
							treeitem.appendChild(treechildrenHijo);
							treeitem.setOpen(false);
						}
					}
					
				}
				treechildren.appendChild(treeitem);	
			}
		}
		trmenus.appendChild(treechildren);	
	
	}
	
	
	/**
	 * Quita el Check del tree
	 */
	public void desmarcaTree(){
		for (Treeitem treeitem : trmenus.getItems()){
			treeitem.setOpen(false);
			treeitem.setSelected(false);
		}	
	}
	
	@SuppressWarnings("rawtypes") 
	public void onCheckSelection(Event event) throws Exception{
		Treeitem selectedItem=(Treeitem) ((SelectEvent) event).getReference();
		
        if(selectedItem.isSelected()){//Si el usuario a selecciona el menu
        	if(selectedItem.getTreechildren()!=null){
	        	for(Treeitem treeSumitem:selectedItem.getTreechildren().getItems()){
					treeSumitem.setSelected(true);
				}
        	}
        	//Busca el menu padre del item seleccionado (selectedItem) para seleccionar el menu padre o los hijos que este pueda tener
        	for(Treeitem treeitems: trmenus.getItems()){
               	OpcionMenu opcionMenuItems=treeitems.getValue();
               	if(((OpcionMenu)selectedItem.getValue()).getOpcionMenuPadre()!=null){
               		if(((OpcionMenu)selectedItem.getValue()).getOpcionMenuPadre().getId().equals(opcionMenuItems.getId())){
               			/*Verifica si todos los menus hijos estan seleccionados para seleccionar el menu padre*/
               			boolean selectedAllSubItems=true;
                      	for(Treeitem treeHijos:treeitems.getTreechildren().getItems()){
                      		if(!(treeHijos.isSelected())){
                      			selectedAllSubItems=false;
                   				break;
                   			}
                   		}
	                    if(selectedAllSubItems)
	                    	treeitems.setSelected(true);//Selecciona el menu padre
	                    break;			
	                }
               	}
        	}
        }else{//Si el usuario a quita seleccion del menu
        	/*quita seleccion de los menus hijos si es que el seleccionado los tenga*/
	   		if(selectedItem.getTreechildren()!=null){
	   			for(Treeitem treeSumitem:selectedItem.getTreechildren().getItems()){
	    			treeSumitem.setSelected(false);
	   			}
	    	}
           	//Busca el menu padre del item seleccionado (selectedItem) para quitar seleccion del menu padre o los hijos que este pueda tener
   			for(Treeitem treeitems: trmenus.getItems()){
           		OpcionMenu opcionMenuItems=treeitems.getValue();
           		if(((OpcionMenu)selectedItem.getValue()).getOpcionMenuPadre()!=null){
           			if(((OpcionMenu)selectedItem.getValue()).getOpcionMenuPadre().getId().equals(opcionMenuItems.getId())){
           				//quita seleccion del menu padre
               			treeitems.setSelected(false);
               			/*quita selección de los menus hijos si es que el seleccionado los tenga*/
    	                if(selectedItem.getTreechildren()!=null){
    	                	for(Treeitem treeSumitem:selectedItem.getTreechildren().getItems()){
    	                		treeSumitem.setSelected(false);
    	                    }
    	                }
    	                break;
               		}
           		}
   			}

           	
           		
        }
	}
	
	
}
