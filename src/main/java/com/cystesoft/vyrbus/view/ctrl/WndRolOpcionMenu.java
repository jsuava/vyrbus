package com.cystesoft.vyrbus.view.ctrl;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

import org.zkoss.zk.ui.Executions;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Tree;
import org.zkoss.zul.Treecell;
import org.zkoss.zul.Treechildren;
import org.zkoss.zul.Treeitem;
import org.zkoss.zul.Treerow;

import com.cystesoft.vyrbus.model.bean.OpcionMenu;
import com.cystesoft.vyrbus.model.bean.Rol;
import com.cystesoft.vyrbus.model.bean.RolOpcionMenu;
import com.cystesoft.vyrbus.model.bean.RolOpcionMenuID;
import com.cystesoft.vyrbus.service.exceptions.OpcionMenuNullException;
import com.cystesoft.vyrbus.service.exceptions.RolNullException;
import com.cystesoft.vyrbus.service.locator.ServiceLocator;
import com.cystesoft.vyrbus.service.util.Constantes;
import com.cystesoft.vyrbus.service.util.Messages;
import com.cystesoft.vyrbus.service.util.UtilData;
import com.cystesoft.vyrbus.view.ui.DlgMessage;
import com.cystesoft.vyrbus.view.ui.WndBase;

/**
 * 
 * @author José Abanto
 *
 */
public class WndRolOpcionMenu extends WndBase {
	private static final long serialVersionUID = 6707055487537567895L;
	
	private Combobox cmbRol;
	private Tree trmenus;
	
	private RolOpcionMenu rolOpcionMenu=null;

	
	/*
	 * (non-Javadoc)
	 * @see com.tepsa.sisvyr.view.ui.IBase#initComponents()
	 */
	@Override
	public void initComponents() {
		cmbRol = (Combobox) this.getFellow("cmbRol");
		trmenus = (Tree) this.getFellow("trmenus");
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.tepsa.sisvyr.view.ui.IBase#onCreate()
	 */
	@Override
	public void onCreate() throws Exception {
		UtilData.cargarDataCombo(cmbRol, Rol.class, false);
		cargarOpcionesMenu();
	}

	/**
	 * Recupera los menus asociados al rol, si es que lo hay.
	 */
	public void onSelectChange(){
		desmarcaTree();
		
		if (cmbRol.getSelectedItem().getValue() instanceof Rol){
			TreeMap<String, Object> criteriosBusqueda = new TreeMap<String, Object>();
			List<String> criteriosOrdenar = new  ArrayList<String>();;
			criteriosOrdenar.add("opcionMenu");
			Rol rol = new Rol();
			rol.setId(((Rol) cmbRol.getSelectedItem().getValue()).getId());
			criteriosBusqueda.put("rol", rol);
			criteriosBusqueda.put("estadoRegistro", Constantes.VALUE_ACTIVO);
			
			List<RolOpcionMenu> list= ServiceLocator.getRolOpcionMenuManager().buscarPorX(criteriosBusqueda, criteriosOrdenar);
			
			/*Selecciona el menú de la lista tree*/
			for (RolOpcionMenu rolOpcionMenu: list){
				Integer idOmRo=rolOpcionMenu.getOpcionMenu().getId();
				for (Treeitem treeitem : trmenus.getItems()){
					Integer idOmt=((OpcionMenu) treeitem.getValue()).getId();
					if(idOmt==idOmRo){
						treeitem.setSelected(true);
						treeitem.setOpen(false);
						break;
					}
					
//					if (((OpcionMenu) treeitem.getValue()).getId().equals(rolOpcionMenu.getRolOpcionMenuID().getIdOpcionMenu())){
//						treeitem.setSelected(true);
//						treeitem.setOpen(false);
//						break;}
				}	
			}
		}
		
	}
	
	/**
	 * 
	 */
	public void onSelectChangeTree(){
		Treeitem treeitem = trmenus.getSelectedItem();
		if (treeitem !=null){
			if (treeitem.isSelected()){
				treeitem.setOpen(true);}
		}
				
		
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
	
	public void onClose() {
		closeTabWindow();
	}
		
	/**
	 * Guarda Rol Opcion menu.
	 * @param action
	 * @throws Exception
	 */
	public void onSave() throws Exception {
		try{
			if (!(cmbRol.getSelectedItem().getValue() instanceof Rol))
				throw new RolNullException();
			if (trmenus.getSelectedItems().size() <= 0)
				throw new OpcionMenuNullException();
			
			Rol rol = new Rol();
			rol.setId(((Rol) cmbRol.getSelectedItem().getValue()).getId());
			
			/*Recorre items del tree*/
			for (Treeitem treeitem : trmenus.getItems()){	
							
				OpcionMenu opcionMenu = new  OpcionMenu();
				opcionMenu.setId(((OpcionMenu) treeitem.getValue()).getId());
				
				rolOpcionMenu = new RolOpcionMenu();
				RolOpcionMenuID rolOpcionMenuID = new RolOpcionMenuID();
				rolOpcionMenuID.setIdRol(rol.getId());
				rolOpcionMenuID.setIdOpcionMenu(opcionMenu.getId());
				UtilData.auditarRegistro(rolOpcionMenuID, true, getUsuario(), Executions.getCurrent());	
				
				/*Valida si el rol y menu del item seleccionado esisten el DB*/
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
								ServiceLocator.getRolOpcionMenuManager().inactivarActivar(rolOpcionMenuID, Constantes.VALUE_INACTIVO);}
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
				
			DlgMessage.information(Messages.getString("WndRolOpcionMenu.Information.OperacionCorrecta"));			
			
		}catch (OpcionMenuNullException omnex){
			DlgMessage.information(Messages.getString("WndRolOpcionMenu.Information.NoOpcionesMenu"));
		}catch (RolNullException rnex){
			DlgMessage.information(Messages.getString("WndRolOpcionMenu.Information.NoRol"));
			cmbRol.setFocus(true);
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
	

}
