package com.cystesoft.vyrbus.view.ui;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zul.Borderlayout;
import org.zkoss.zul.Button;
import org.zkoss.zul.Center;
import org.zkoss.zul.Checkbox;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Constraint;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.Decimalbox;
import org.zkoss.zul.Div;
import org.zkoss.zul.Doublebox;
import org.zkoss.zul.Intbox;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Longbox;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.North;
import org.zkoss.zul.Radio;
import org.zkoss.zul.Separator;
import org.zkoss.zul.Space;
import org.zkoss.zul.Spinner;
import org.zkoss.zul.Tab;
import org.zkoss.zul.Tabbox;
import org.zkoss.zul.Tabpanel;
import org.zkoss.zul.Tabpanels;
import org.zkoss.zul.Tabs;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Timebox;
import org.zkoss.zul.Toolbar;
import org.zkoss.zul.Toolbarbutton;
import org.zkoss.zul.impl.InputElement;

import com.cystesoft.vyrbus.service.exceptions.CancelaGrabacionException;
import com.cystesoft.vyrbus.service.exceptions.NoRemoverRegistroDelListBox;
import com.cystesoft.vyrbus.service.util.Messages;

/**
 * Clase base para todas las ventanas de mantenimiento,
 * hereda de {@link WndBase}
 * @author jM
 * @since JDK1.6
 */
public abstract class WndOpcionesMantenimiento extends WndBase implements IOpcionesMantenimiento{
	private static final long serialVersionUID = 6977003857234834917L;

	private ArrayList<Component> lstControles = new ArrayList<>();
	private HashMap<String, Constraint> lstConstraints = new HashMap<>();
	private InputElement inputElementEnfocable;
	private int accionUsuario;

	public int tabUsuario = TAB_LIST;

	private Borderlayout oBorderlayout = new Borderlayout();
	private North oNorth = new North();
	private Center oCenter = new Center();
	private Div oDiv = new Div();

	public Toolbar oToolbar = new Toolbar();
	public Toolbarbutton btnNuevo = new Toolbarbutton();
	public Toolbarbutton btnBuscar = new Toolbarbutton();
	public Toolbarbutton btnRefrescar = new Toolbarbutton();
	public Toolbarbutton btnModificar = new Toolbarbutton();
	public Toolbarbutton btnCancelar = new Toolbarbutton();
	public Toolbarbutton btnGuardar = new Toolbarbutton();
	public Toolbarbutton btnEliminar = new Toolbarbutton();
	public Toolbarbutton btnImprimir = new Toolbarbutton();
	public Toolbarbutton btnExportar = new Toolbarbutton();
	public Toolbarbutton btnAyuda = new Toolbarbutton();
	public String questionDelete="¿Desea eliminar el registro Seleccionado ";
	public String questionInsert="¿Desea guardar el registro?";
	public String questionUpdate="¿Desea actualizar el registro?";

	public Toolbarbutton btnCerrar = new Toolbarbutton();
	private Space oSpace = new Space();

	public Tabbox oTabbox = new Tabbox();
	private Tabs oTabs = new Tabs();
	public Tab oTabLista = new Tab();
	private Tab oTabMantenimiento = new Tab();
	private Tabpanels oTabpanels = new Tabpanels();
	private Tabpanel oTabpanelLista = new Tabpanel();
	private Tabpanel oTabpanelMantenimiento = new Tabpanel();


//	Rol rol = new Rol();

	/**
	 * Div que se mostrara en el tab lista.
	 */
	private Div divLista;
	/**
	 * Div que se mostrara en el tab mantenimiento.
	 */
	private Div divMantenimiento;
	/**
	 * Listbox que se mostrara en el tab lista.
	 */
	public Listbox listboxLista;
	/**
	 * Textbox que se usara como id en el tab mantenimiento.
	 */
	public Textbox 	textboxId;

	/**
	 * Metodo lanzado antes que se contruya la pagina
	 */
	@Override
	public void beforeCompose(){
		super.beforeCompose();
		this.generaToolbarMantenimiento();
		this.generaTabsMantenimeinto();

		this.setHeight("100%");
		this.appendChild(oBorderlayout);

		/*Habili/Desabilita según la configuracion del rol del usuario*/
		/*NUEVO*/
		btnNuevo.setDisabled(accesoNuevo()?false:true);
		/*BUSCAR*/
		btnBuscar.setDisabled(accesoConsultar()?false:true);
	}


	/**
	 * Metodo lanzado despues de contruir la pagina.
	 */
	@Override
	public void afterCompose(){
		super.afterCompose();

		this.generaControlesMantenimiento();
	}

	/**
	 * Genera la barra de herramientas para los mantenimientos.
	 */
	private void generaToolbarMantenimiento() {
		oSpace.setBar(true);

		btnNuevo.setAutodisable("self");
		btnNuevo.setLabel("Nuevo");
		btnNuevo.setTooltiptext("Crear un nuevo registro");
		btnNuevo.setImage("resources/toolbar/mp_toolbarNuevo.png");
		btnNuevo.addEventListener(Events.ON_CLICK, new EventListener<Event>() {
			@Override
			public void onEvent(Event event) throws Exception {
				limpiarControles();
				habilitaControles(true);
				onNew();
				if (!oTabLista.isDisabled())
					oTabLista.setDisabled(true);

				oTabbox.setSelectedIndex(1);
				tabUsuario = TAB_MAINTENANCE;
				if (inputElementEnfocable != null)
					inputElementEnfocable.setFocus(true);

				accionUsuario = ACTION_NEW;
				btnNuevo.setDisabled(true);
				btnBuscar.setDisabled(true);
				btnRefrescar.setDisabled(true);
				btnModificar.setDisabled(true);
				btnCancelar.setDisabled(false);
				btnGuardar.setDisabled(accesoGrabar()?false:true);
				btnEliminar.setDisabled(true);
				btnImprimir.setDisabled(true);
				btnExportar.setDisabled(true);
				btnCerrar.setDisabled(true);
			}
		});

		btnBuscar.setAutodisable("self");
		btnBuscar.setLabel("Buscar");
		btnBuscar.setTooltiptext("Buscar registro");
		btnBuscar.setImage("resources/toolbar/mp_toolbarBuscar.png");
		btnBuscar.addEventListener(Events.ON_CLICK, new EventListener<Event>() {
			@Override
			public void onEvent(Event event) throws Exception {
				onSearch();
				if (tabUsuario != TAB_LIST) {
					oTabbox.setSelectedIndex(0);
					tabUsuario = TAB_LIST;
				}
				btnModificar.setDisabled(true);
				btnEliminar.setDisabled(true);
			}
		});

		btnRefrescar.setAutodisable("self");
		btnRefrescar.setLabel("Refrescar");
		btnRefrescar.setTooltiptext("Refrescar registro");
		btnRefrescar.setImage("resources/toolbar/mp_toolbarRefrescar.png");
		btnRefrescar.setDisabled(true);
		btnRefrescar.addEventListener(Events.ON_CLICK, new EventListener<Event>() {
			@Override
			public void onEvent(Event event) throws Exception {
				onRefresh(tabUsuario);
				oTabLista.setFocus(true);
				oTabbox.setSelectedIndex(0);
				btnModificar.setDisabled(true);
				btnEliminar.setDisabled(true);

				/*Habili/Desabilita según la configuracion del rol del usuario*/
				/*IMPRIMIR*/
				if (accesoImprimir()){
					btnImprimir.setDisabled(listboxLista.getItemCount() < 1);
				}else {
					btnImprimir.setDisabled(true);
				}
				/*EXPORTAR*/
				if (accesoExportar()){
					btnExportar.setDisabled(listboxLista.getItemCount() < 1);
				}else{
					btnExportar.setDisabled(true);
				}
			}
		});

		btnModificar.setAutodisable("self");
		btnModificar.setLabel("Modificar");
		btnModificar.setTooltiptext("Modificar el registro seleccionado");
		btnModificar.setImage("resources/toolbar/mp_toolbarModificar.png");
		btnModificar.setDisabled(true);
		btnModificar.addEventListener(Events.ON_CLICK, new EventListener<Event>() {
			@Override
			public void onEvent(Event event) throws Exception {
				modify();
				onModify(tabUsuario);
			}
		});

		btnCancelar.setAutodisable("self");
		btnCancelar.setLabel("Cancelar");
		btnCancelar.setTooltiptext("Cancelar la edición del registro");
		btnCancelar.setImage("resources/toolbar/mp_toolbarCancelar.png");
		btnCancelar.setDisabled(true);
		btnCancelar.addEventListener(Events.ON_CLICK, new EventListener<Event>() {
			@Override
			public void onEvent(Event event) throws Exception {
				onCancel(accionUsuario);
				if(accionUsuario == ACTION_NEW)
					limpiarControles();

				oTabbox.setSelectedIndex(0);
				oTabLista.setDisabled(false);
				habilitaControles(false);
				btnModificar.setTooltiptext("Modificar el registro actual");

				/*Habili/Desabilita según la configuracion del rol del usuario*/
				/*NEUVO*/
				btnNuevo.setDisabled(accesoNuevo()?false:true);
				/*BUSCAR*/
				btnBuscar.setDisabled(accesoConsultar()?false:true);
				/*MODIFICAR*/
				if (accesoModificar()){
					if(listboxLista.getItems().size()>0 && listboxLista.getSelectedIndex()>=0)
						btnModificar.setDisabled(false);
					else
						btnModificar.setDisabled(accionUsuario == ACTION_NEW);
				}else btnModificar.setDisabled(true);

				/*ELIMINAR*/
				if (accesoEliminar()){
					if(listboxLista.getItems().size()>0 && listboxLista.getSelectedIndex()>=0)
						btnEliminar.setDisabled(false);
					else
						btnEliminar.setDisabled(accionUsuario == ACTION_NEW);
				}else btnEliminar.setDisabled(true);

				/*IMPRIMIR*/
				if (accesoImprimir()) {
					if(listboxLista.getItems().size()>0)
						btnImprimir.setDisabled(false);
					else
						btnImprimir.setDisabled(accionUsuario == ACTION_NEW);
				}else btnImprimir.setDisabled(true);
				/*EXPORTAR*/
				if (accesoExportar()){
					if(listboxLista.getItems().size()>0)
						btnExportar.setDisabled(false);
					else
						btnExportar.setDisabled(accionUsuario == ACTION_NEW);
				}else btnExportar.setDisabled(true);

				if(listboxLista.getItems().size()>0)
					btnRefrescar.setDisabled(false);
				else
					btnRefrescar.setDisabled(!(accionUsuario == ACTION_MODIFY));


				btnCancelar.setDisabled(true);
				btnGuardar.setDisabled(true);
				btnCerrar.setDisabled(false);

			}
		});

		btnGuardar.setAutodisable("self");
		btnGuardar.setLabel("Guardar");
		btnGuardar.setTooltiptext("Guardar los datos ingresados");
		btnGuardar.setImage("resources/toolbar/mp_toolbarGuardar.png");
		btnGuardar.setDisabled(true);
		btnGuardar.addEventListener(Events.ON_CLICK, new EventListener<Event>() {
			@Override
			public void onEvent(Event event) throws Exception {
				try{
					Messagebox.show(questionInsert, DlgMessage.NOMBREAPLICACION, DlgMessage.BTN_YESNO, Messagebox.QUESTION, new EventListener<Event>() {
						@Override
						public void onEvent(Event e) throws Exception{
							try {
								if(e.getName().equals("onYes")){
									onSave(accionUsuario);

									String accionMensaje = "";
									switch(accionUsuario){
										case ACTION_NEW:
											accionMensaje = "grabado";
											break;
										case ACTION_MODIFY:
											accionMensaje = "actualizado";
											break;
									}
									DlgMessage.information("Registro " + accionMensaje);
									oTabLista.setDisabled(false);
									habilitaControles(false);

									/*Habili/Desabilita según la configuracion del rol del usuario*/
									/*NUEVO*/
									btnNuevo.setDisabled(accesoNuevo()?false:true);
									/*BUSCAR*/
									btnBuscar.setDisabled(accesoConsultar()?false:true);
									/*IMPRIMIR*/
									btnImprimir.setDisabled(accesoImprimir()?false:true);
									/*EXPORTAR*/
									btnExportar.setDisabled(accesoExportar()?false:true);

									btnRefrescar.setDisabled(false);
									btnModificar.setDisabled(true);
									btnCancelar.setDisabled(true);
									btnGuardar.setDisabled(true);
									btnEliminar.setDisabled(true);
									btnCerrar.setDisabled(false);

									oTabbox.setSelectedIndex(0);
								}else
									return;
							}catch (CancelaGrabacionException ex){}
						}
					});


				}catch (Exception ex){
					throw new Exception();
				}
			}
		});

		btnEliminar.setAutodisable("self");
		btnEliminar.setLabel("Eliminar");
		btnEliminar.setTooltiptext("Eliminar el registro seleccionado");
		btnEliminar.setImage("resources/toolbar/mp_toolbarEliminar.png");
		btnEliminar.setDisabled(true);
		btnEliminar.addEventListener(Events.ON_CLICK, new EventListener<Event>() {
			@Override
			public void onEvent(Event event) throws Exception {

				Messagebox.show(questionDelete +  "?", Messages.getString("System.title"), Messagebox.YES | Messagebox.NO, Messagebox.QUESTION,
						new EventListener<Event>() {
							@Override
							public void onEvent(Event event) throws Exception {
								try{
									if(event.getName().equals("onYes")){
										onDelete(tabUsuario);
										switch (tabUsuario) {
											case TAB_LIST:
												listboxLista.removeItemAt(listboxLista.getSelectedIndex());
												break;
											case TAB_MAINTENANCE:
												listboxLista.removeItemAt(listboxLista.getSelectedIndex());
												break;
										}

										limpiarControles();
										habilitaControles(false);

										/*Habilita/Desabilita según la configuracion del rol del usuario*/
										/*NUEVO*/
										btnNuevo.setDisabled(accesoNuevo()?false:true);
										/*BUSCAR*/
										btnBuscar.setDisabled(accesoConsultar()?false:true);

										btnModificar.setDisabled(true);
										btnCancelar.setDisabled(true);
										btnGuardar.setDisabled(true);
										btnEliminar.setDisabled(true);
										btnImprimir.setDisabled(true);
										btnExportar.setDisabled(true);
										btnCerrar.setDisabled(false);
										onRefresh(tabUsuario);
									}
								}catch (NoRemoverRegistroDelListBox e) {
								}

							}
						}
					);
			}
		});

		btnImprimir.setAutodisable("self");
		btnImprimir.setLabel("Imprimir");
		btnImprimir.setTooltiptext("Imprimir vista actual");
		btnImprimir.setImage("resources/toolbar/mp_toolbarImprimir.png");
		btnImprimir.setDisabled(true);
		btnImprimir.addEventListener(Events.ON_CLICK, new EventListener<Event>() {
			@Override
			public void onEvent(Event event) throws Exception {
				onPrint(tabUsuario);
			}
		});

		btnExportar.setAutodisable("self");
		btnExportar.setLabel("Exportar");
		btnExportar.setTooltiptext("Exportar vista actual");
		btnExportar.setImage("resources/toolbar/mp_toolbarExportar.png");
		btnExportar.setDisabled(true);
		btnExportar.addEventListener(Events.ON_CLICK, new EventListener<Event>() {
			@Override
			public void onEvent(Event event) throws Exception {
				onExport(tabUsuario);
//				Util.exportarExcel(listboxLista,oTabLista.getLabel());
			}
		});

		btnAyuda.setLabel("Ayuda");
		btnAyuda.setTooltiptext("Mostrar ayuda");
		btnAyuda.setImage("resources/toolbar/mp_toolbarAyuda.png");
		btnAyuda.addEventListener(Events.ON_CLICK, new EventListener<Event>() {
			@Override
			public void onEvent(Event event) throws Exception {
				onHelp();
			}
		});

		btnCerrar.setLabel("Cerrar");
		btnCerrar.setTooltiptext("Cerrar la ventana");
		btnCerrar.setImage("resources/toolbar/mp_toolbarCerrar.png");
		btnCerrar.addEventListener(Events.ON_CLICK, new EventListener<Event>() {
			@Override
			public void onEvent(Event event) throws Exception {
				onClose();
				onCloseSuper();
			}
		});

		oToolbar.appendChild(btnNuevo);
		oToolbar.appendChild(btnBuscar);
		oToolbar.appendChild(btnRefrescar);
		oToolbar.appendChild(btnModificar);
		oToolbar.appendChild(btnCancelar);
		oToolbar.appendChild(btnGuardar);
		oToolbar.appendChild(btnEliminar);
		oToolbar.appendChild((Space) oSpace.clone());
		oToolbar.appendChild(btnImprimir);
		oToolbar.appendChild(btnExportar);
		oToolbar.appendChild((Space) oSpace.clone());
		oToolbar.appendChild(btnAyuda);
		oToolbar.appendChild((Space) oSpace.clone());
		oToolbar.appendChild(btnCerrar);

		oDiv.appendChild(oToolbar);
		oDiv.appendChild(new Separator());

		oNorth.setBorder("none");
		oNorth.appendChild(oDiv);

		oBorderlayout.appendChild(oNorth);
	}

	/**
	 * Genera los tabs en los que se mostrara la información.
	 */
	private void generaTabsMantenimeinto(){
		oTabLista.setLabel("Lista");
		oTabMantenimiento.setLabel("Mantenimiento");

		oTabs.appendChild(oTabLista);
		oTabs.appendChild(oTabMantenimiento);
		oTabs.setWidth("100px");

		oTabpanelLista.setStyle("overflow:auto");
		oTabpanelMantenimiento.setStyle("overflow:auto");

		oTabpanels.appendChild(oTabpanelLista);
		oTabpanels.appendChild(oTabpanelMantenimiento);

		oTabbox.appendChild(oTabs);
		oTabbox.appendChild(oTabpanels);
		oTabbox.setOrient("vertical");
		oTabbox.setHeight("100%");

		oCenter.setBorder("none");
		oCenter.appendChild(oTabbox);
		oBorderlayout.appendChild(oCenter);
	}

	/**
	 *
	 */
	private void generaControlesMantenimiento() {
		divLista = (Div) getFellow("divLista");
		divMantenimiento = (Div) getFellow("divMantenimiento");
		listboxLista = (Listbox) getFellow("listboxLista");

		textboxId = (Textbox) getFellow("textboxId");
		textboxId.setVisible(false);

		divMantenimiento.setWidth("730px");
		oTabpanelLista.appendChild(divLista);
		oTabpanelMantenimiento.appendChild(divMantenimiento);

		/*	Lanzado cuando el usuario seleccione el tab Lista	*/
		oTabLista.addEventListener(Events.ON_CLICK, new EventListener<Event>() {
			@Override
			public void onEvent(Event event) throws Exception {
				if (tabUsuario != TAB_LIST) {
					/*	Realiza el llenado de los controles con los datos del registro seleccionado para su edición	*/
					onChangeTab(TAB_LIST);

					tabUsuario = TAB_LIST;
					btnRefrescar.setDisabled(listboxLista.getItemCount() < 1);
					btnModificar.setTooltiptext("Modificar el registro seleccionado");
					btnEliminar.setTooltiptext("Eliminar el registro seleccionado");

					/*Habili/Desabilita según la configuracion del rol del usuario*/
					/*MODIFICAR*/
					if (accesoModificar())
						btnModificar.setDisabled(!(listboxLista.getSelectedIndex() > -1));
					else btnModificar.setDisabled(true);
					/*ELIMINAR*/
					if (accesoEliminar())
						btnEliminar.setDisabled(!(listboxLista.getSelectedIndex() > -1));
					else btnEliminar.setDisabled(true);
					/*IMPRIMIR*/
					if (accesoImprimir())
						btnImprimir.setDisabled(listboxLista.getItemCount() < 1);
					else btnImprimir.setDisabled(true);
					/*EXPORTAR*/
					if (accesoExportar())
						btnExportar.setDisabled(listboxLista.getItemCount() < 1);
					else btnExportar.setDisabled(true);
				}
			}
		});

		/*	Lanzado cuando el usuario seleccione el tab Mantenimiento	*/
		EventListener<Event> changeTabEventListener = new EventListener<Event>() {
			@Override
			public void onEvent(Event event) throws Exception {
				if ((event.getTarget() instanceof Listbox && ((Listbox) event.getTarget()).getSelectedIndex() > -1)
						|| (event.getTarget() instanceof Tab && tabUsuario != TAB_MAINTENANCE)) {

					Integer id;
					limpiarControles();
					onChangeTab(TAB_MAINTENANCE);

					if (event.getTarget() instanceof Listbox) {
						oTabbox.setSelectedIndex(1);
					}

					tabUsuario = TAB_MAINTENANCE;
					id = (textboxId.getText().equals("") ? 0 : new Integer(textboxId.getText()));

					btnRefrescar.setDisabled(!(id > 0));
					btnModificar.setTooltiptext("Modificar el registro actual");
					btnEliminar.setTooltiptext("Eliminar el registro actual");

					/*Habili/Desabilita según la configuracion del rol del usuario*/
					/*MODIFICAR*/
					if (accesoModificar())
						btnModificar.setDisabled(!(id > 0));
					else btnModificar.setDisabled(true);
					/*ELIMINAR*/
					if (accesoEliminar())
						btnEliminar.setDisabled(!(id > 0));
					else btnEliminar.setDisabled(true);
					/*IMPRIMIR*/
					if (accesoImprimir())
						btnImprimir.setDisabled(!(id > 0));
					else btnImprimir.setDisabled(true);
					/*EXPORTAR*/
					if (accesoExportar())
						btnExportar.setDisabled(!(id > 0));
					else btnExportar.setDisabled(true);
				}
			}
		};

		oTabMantenimiento.addEventListener(Events.ON_CLICK, changeTabEventListener);

		listboxLista.setMold("paging");
		listboxLista.setPageSize(20);
//		listboxLista.addEventListener(Events.ON_DOUBLE_CLICK, changeTabEventListener); new EventListener<Event>()




		listboxLista.addEventListener(Events.ON_DOUBLE_CLICK,new EventListener<Event>() {
			@Override
			public void onEvent(Event event) throws Exception {
				// TODO Auto-generated method stub
				if(listboxLista.getSelectedItem()!=null && listboxLista.getSelectedItem().getValue() !=null){
					modify();
					onModify(tabUsuario);
				}
			}
		});
		/*	Cuando se selecciona un registro de la Lista	*/
		listboxLista.addEventListener(Events.ON_SELECT, new EventListener<Event>() {
			@Override
			public void onEvent(Event event) throws Exception {
				btnRefrescar.setDisabled(false);//TODO optimizar la habilitacion del boton refrescar despues de realizar una busqueda

				/*Habili/Desabilita según la configuracion del rol del usuario*/
				/*MODIFICAR*/
				btnModificar.setDisabled(accesoModificar()?false:true);
				/*ELIMINAR*/
				btnEliminar.setDisabled(accesoEliminar()?false:true);
				/*IMPRIMIR*/
				btnImprimir.setDisabled(accesoImprimir()?false:true);
				/*EXPORTAR*/
				btnExportar.setDisabled(accesoExportar()?false:true);
			}
		});

		this.registrarControles(divMantenimiento);
		this.habilitaControles(false);
	}

	/**
	 * Realiza la limpieza de los controles de usuario
	 */
	public void limpiarControles() {
		for (Component oComponent : lstControles) {
			if(oComponent instanceof Combobox) {
				((Combobox) oComponent).setSelectedIndex(-1);
			}else if(oComponent instanceof Checkbox) {
				((Checkbox) oComponent).setChecked(false);
			}else if(oComponent instanceof Textbox) {
				((Textbox) oComponent).setText("");
			}else if (oComponent instanceof Datebox) {
				((Datebox) oComponent).setText("");
			}else if (oComponent instanceof Decimalbox){
				((Decimalbox) oComponent).setText("");
			}else if (oComponent instanceof Spinner){
				((Spinner) oComponent).setText("");
			}else if (oComponent instanceof Timebox){
				((Timebox) oComponent).setText("");
			}else if (oComponent instanceof Longbox){
				((Longbox) oComponent).setText("");
			}else if (oComponent instanceof Intbox){
				((Intbox) oComponent).setText("");
			}else if (oComponent instanceof Doublebox){
				((Doublebox) oComponent).setText("");
			}else if (oComponent instanceof Longbox){
				((Longbox) oComponent).setText("");
			}
		}
	}

	/**
	 *
	 * @param oComponent
	 */
	private void registrarControles(Component oComponent) {
		if(oComponent instanceof InputElement || oComponent instanceof Checkbox
				|| oComponent instanceof Radio || oComponent instanceof Button) {

			lstControles.add(oComponent);
			if(oComponent instanceof InputElement) {
				InputElement oInputElement = (InputElement) oComponent;
				lstConstraints.put(oComponent.getId(), oInputElement.getConstraint());
//				oInputElement.setConstraint(null);
				oInputElement.setConstraint("");
				if(this.inputElementEnfocable == null && oInputElement.isVisible()) {
					this.inputElementEnfocable = oInputElement;
				}
			}
		}else {
			List<Component> lstChildrens = oComponent.getChildren();
			for(Component oComponentHijo: lstChildrens) {
				registrarControles(oComponentHijo);
			}
		}
	}

	/**
	 * Habilita o deshabilita los controles.
	 * @param habilita	: Argumento true o false
	 */
	public void habilitaControles(boolean habilita) {
		for (Component oComponent : lstControles) {
			if(oComponent instanceof InputElement) {
				((InputElement) oComponent).setDisabled(!habilita);
			}
			else if(oComponent instanceof Checkbox) {
				((Checkbox) oComponent).setDisabled(!habilita);
			}
			else if(oComponent instanceof Radio) {
				((Radio) oComponent).setDisabled(!habilita);
			}
			else if(oComponent instanceof Button) {
				((Button) oComponent).setDisabled(!habilita);
			}
		}
	}

	public boolean validarDatos() {
		boolean validado = true;
		/*
		for(int c = 0; c < lstControles.size(); c ++) {
			Component oComponent = lstControles.get(c);
			if(oComponent instanceof InputElement) {
				InputElement oInputElement = (InputElement) oComponent;
				Constraint oConstraint = lstConstraints.get(oInputElement.getId());

				oInputElement.setConstraint(oConstraint);

				if(!oInputElement.isValid()) {
					validado = false;
					oInputElement.setFocus(true);
				}

				oInputElement.setConstraint(null);

				if (!validado) {
					oConstraint.validate(oInputElement, oInputElement.getText());
					break;
				}
			}
		}*/
		return validado;
	}

	private void modify(){
		limpiarControles();
		habilitaControles(true);

		if(!oTabLista.isDisabled()) {
			oTabLista.setDisabled(true);
		}
		tabUsuario = TAB_MAINTENANCE;
		oTabbox.setSelectedIndex(1);

		if (inputElementEnfocable != null)
			inputElementEnfocable.setFocus(true);

		accionUsuario = ACTION_MODIFY;
		btnNuevo.setDisabled(true);
		btnBuscar.setDisabled(true);
		btnRefrescar.setDisabled(true);
		btnModificar.setDisabled(true);
		btnCancelar.setDisabled(false);
		btnGuardar.setDisabled(accesoGrabar()?false:true);
		btnEliminar.setDisabled(true);
		btnImprimir.setDisabled(true);
		btnExportar.setDisabled(true);
		btnCerrar.setDisabled(true);

	}

	private void onCloseSuper(){
		super.onClose();
	}

	/**
	 * Método para cerrar la ventana, incluyendo el Tab que lo contenga
	 */
	@Override
	public void closeTabWindow () {
		Component oComponentParent = this;
		Tabpanel oTabpanel;
		boolean buscarTabParent = true;
		int intentosBusqueda = 0;

		while (buscarTabParent && (intentosBusqueda < 5)) {
			intentosBusqueda ++;

			if (oComponentParent instanceof Tabpanel) {
				oTabpanel = (Tabpanel) oComponentParent;
				oTabpanel.getLinkedTab().close();
				buscarTabParent = false;
			}
				else {
					oComponentParent = oComponentParent.getParent();

					if (oComponentParent == null) {
						buscarTabParent = false;
					}
			}
		}
	}
}