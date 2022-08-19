/**
 * Proyecto		: SISVYR
 * Sistema		: Sistema de Ventas y Reservas
 * Descripción	:
 * Autor		: jM
 * Fecha		: 26/04/2012
 */
package com.cystesoft.vyrbus.view.ui;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;
import java.util.TreeMap;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zul.Button;
import org.zkoss.zul.Checkbox;
import org.zkoss.zul.Column;
import org.zkoss.zul.Columns;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Constraint;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.Grid;
import org.zkoss.zul.Intbox;
import org.zkoss.zul.Label;
import org.zkoss.zul.Longbox;
import org.zkoss.zul.Radio;
import org.zkoss.zul.Row;
import org.zkoss.zul.Rows;
import org.zkoss.zul.Separator;
import org.zkoss.zul.Spinner;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;
import org.zkoss.zul.impl.InputElement;

import com.cystesoft.vyrbus.model.bean.Agencia;
import com.cystesoft.vyrbus.model.bean.AutorizadorCortesia;
import com.cystesoft.vyrbus.model.bean.Bus;
import com.cystesoft.vyrbus.model.bean.CanalVenta;
import com.cystesoft.vyrbus.model.bean.Concesionario;
import com.cystesoft.vyrbus.model.bean.EstadoDocumentoBus;
import com.cystesoft.vyrbus.model.bean.FormaPago;
import com.cystesoft.vyrbus.model.bean.Localidad;
import com.cystesoft.vyrbus.model.bean.MotivoCortesia;
import com.cystesoft.vyrbus.model.bean.NumeroFlota;
import com.cystesoft.vyrbus.model.bean.OpcionMenu;
import com.cystesoft.vyrbus.model.bean.OperadorTarjetaCredito;
import com.cystesoft.vyrbus.model.bean.Personal;
import com.cystesoft.vyrbus.model.bean.Rol;
import com.cystesoft.vyrbus.model.bean.Servicio;
import com.cystesoft.vyrbus.model.bean.TipoAgencia;
import com.cystesoft.vyrbus.model.bean.TipoComprobante;
import com.cystesoft.vyrbus.model.bean.TipoDocumento;
import com.cystesoft.vyrbus.model.bean.TipoFlota;
import com.cystesoft.vyrbus.model.bean.TipoFormaPago;
import com.cystesoft.vyrbus.model.bean.TipoGasto;
import com.cystesoft.vyrbus.model.bean.TipoMoneda;
import com.cystesoft.vyrbus.model.bean.Usuario;
import com.cystesoft.vyrbus.model.bean.UsuarioAprobador;
import com.cystesoft.vyrbus.model.bean.UsuarioHardware;
import com.cystesoft.vyrbus.service.util.Constantes;
import com.cystesoft.vyrbus.service.util.Util;
import com.cystesoft.vyrbus.service.util.UtilData;

/**
 *
 * @author jM
 * @since JDK1.6
 */
public class WndFiltrarParametros extends WndBase {

	private static final long serialVersionUID = -5937803276994188557L;

	public Window oThisWindow = this;

	@SuppressWarnings("rawtypes")
	private EventListener oEventListenerFilter;

	private TreeMap<String, Object> lstParametros = new TreeMap<>();
	private TreeMap<String, Object> lstParametrosValor = new TreeMap<>();
	private TreeMap<String, Object> lstControles = new TreeMap<>();

	private ArrayList<Component> lstControless = new ArrayList<>();
	private HashMap<String, Constraint> lstConstraints = new HashMap<>();
	private InputElement inputElementEnfocable;
	private List<Component>lstComponents = null;
	//private Div divMantenimiento;
	private int xc=0; //Para copiar el valor de la fecha inicio a la del final


	private Grid oGrid = new Grid();
	private Rows oRows = new Rows();
//	private Row oRowSeparador = new Row();
//	private Row oRowBoton = new Row();
	private Button oButton = new Button();
	private int indice;

	/**
	 * Constructor
	 */
	public WndFiltrarParametros() {
		super();
		this.initComponents();
	}

	/**
	 * Constructor
	 * @param title
	 * @param border
	 * @param closable
	 */
	public WndFiltrarParametros(String title, String border, boolean closable) {
		super(title, border, closable);
		this.initComponents();
	}

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.window.WndBaseInterface#onCreate()
	 */
	@Override
	public void onCreate() {

	}

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.window.WndBaseInterface#initComponents()
	 */
	@Override
	public void initComponents() {
//		this.setTitle(Messages.getString("System.title"));
		this.setTitle("PARÁMETROS DE BUSQUEDA");
		this.setMaximizable(false);
		this.setMinimizable(false);
//		this.setSizable(true);
		this.setClosable(true);
		this.setStyle("padding: 1px");
//		this.setWidth("460px");
	}

	public void generaControles() throws Exception {
		oButton.addEventListener(Events.ON_CLICK, new EventListener<Event>() {
			@SuppressWarnings("unchecked")
			@Override
			public void onEvent(Event event) throws Exception {
				Boolean bOrigen=false;
				Boolean bDestino=false;

				/*RECUPERA LOS VALORES DE LOS COMBOBOX*/
				for(int c = 0; c < lstControless.size(); c ++) {
					Component oComponent = lstControless.get(c);

					/*Solo se cumple cuando la clase es utilizada mas de una vez. como es el caso de la Localidad*/
					if (c==0)
						bOrigen=true;
					else
						bOrigen=false;

					if (c==2)
						bDestino=true;
					else
						bDestino=false;


					if(oComponent instanceof Combobox) {
						for (Entry<?,?> e : lstParametros.entrySet()) {
							Class<?> oClass = (Class<?>) e.getValue();

//							lstParametrosValor.put(e.getKey().toString(), ((Object) ((Combobox) oComponent).getSelectedItem().getValue()));

							if (oClass.equals(TipoAgencia.class) &&  ((Combobox) oComponent).getSelectedItem().getValue() instanceof TipoAgencia){
								/*BUSQUEDA POR TIPO AGENCIA*/
								if (getParameterValue(e.getKey().toString()) == null )
									lstParametrosValor.put(e.getKey().toString(), ((TipoAgencia) ((Combobox) oComponent).getSelectedItem().getValue()).getId());
									break;
							}else if (oClass.equals(Localidad.class)){
								/*BUSQUEDA POR LOCALIDAD*/
								if (e.getKey().toString().equals("1. Origen") && bOrigen==true){
									if (((Combobox) oComponent).getSelectedItem().getValue() instanceof Localidad){
										lstParametrosValor.put(e.getKey().toString(), (((Combobox) oComponent).getSelectedItem().getValue()));
										break;
									}
								}else if (e.getKey().toString().equals("2. Destino") && bDestino==true){
									if (((Combobox) oComponent).getSelectedItem().getValue() instanceof Localidad){
										lstParametrosValor.put(e.getKey().toString(), (((Combobox) oComponent).getSelectedItem().getValue()));
										break;
									}
								}else{
									if (getParameterValue(e.getKey().toString()) == null && (!e.getKey().equals("1. Origen")) && (!e.getKey().equals("2. Destino")) ){
										if (((Combobox) oComponent).getSelectedItem().getValue() instanceof Localidad)
											lstParametrosValor.put(e.getKey().toString(), ((Localidad) ((Combobox) oComponent).getSelectedItem().getValue()).getId());
											break;
									}
								}
							}else if (oClass.equals(Concesionario.class) &&  ((Combobox) oComponent).getSelectedItem().getValue() instanceof Concesionario){
								/*BUSQUEDA POR CONCESIONARIO*/
								if (getParameterValue(e.getKey().toString()) == null)
									lstParametrosValor.put(e.getKey().toString(), ((Concesionario) ((Combobox) oComponent).getSelectedItem().getValue()).getId());
									break;
							}else if (oClass.equals(FormaPago.class) &&  ((Combobox) oComponent).getSelectedItem().getValue() instanceof FormaPago){
								/*BUSQUEDA FORMA DE PAGO*/
								if (getParameterValue(e.getKey().toString()) == null)
									lstParametrosValor.put(e.getKey().toString(), ((FormaPago) ((Combobox) oComponent).getSelectedItem().getValue()).getId());
									break;
							}else if (oClass.equals(OperadorTarjetaCredito.class) &&  ((Combobox) oComponent).getSelectedItem().getValue() instanceof OperadorTarjetaCredito){
								/*BUSQUEDA OPERADOR DE TARJETA DE CREDITO*/
								if (getParameterValue(e.getKey().toString()) == null)
									lstParametrosValor.put(e.getKey().toString(), ((OperadorTarjetaCredito) ((Combobox) oComponent).getSelectedItem().getValue()).getId());
									break;
							}else if (oClass.equals(Combobox.class) && (e.getKey().equals("Porcentaje") || e.getKey().equals("Pasajeros Frecuentes"))){
								/*BUSQUEDA POR SI PORCENTACE "SI, NO"*/
								if (getParameterValue(e.getKey().toString()) == null)
									lstParametrosValor.put(e.getKey().toString(), ((Combobox) oComponent).getSelectedItem().getValue());
									break;
							}else if (oClass.equals(TipoComprobante.class) &&  ((Combobox) oComponent).getSelectedItem().getValue() instanceof TipoComprobante){
								/*BUSQUEDA POR TIPO DE COMPROBANTE*/
								if (getParameterValue(e.getKey().toString()) == null)
									lstParametrosValor.put(e.getKey().toString(), (((Combobox) oComponent).getSelectedItem().getValue()));
									break;
							}else if (oClass.equals(Agencia.class) &&  ((Combobox) oComponent).getSelectedItem().getValue() instanceof Agencia){
								/*BUSQUEDA POR AGENCIA*/
								if (getParameterValue(e.getKey().toString()) == null)
									lstParametrosValor.put(e.getKey().toString(), (((Combobox) oComponent).getSelectedItem().getValue()));
									break;
							}else if (oClass.equals(TipoFlota.class) &&  ((Combobox) oComponent).getSelectedItem().getValue() instanceof TipoFlota){
								/*BUSQUEDA POR TIPO DE FLOTA*/
								if (getParameterValue(e.getKey().toString()) == null)
									lstParametrosValor.put(e.getKey().toString(), ((TipoFlota) ((Combobox) oComponent).getSelectedItem().getValue()).getId());
									break;
							}else if (oClass.equals(Servicio.class) &&  ((Combobox) oComponent).getSelectedItem().getValue() instanceof Servicio){
								/*BUSQUEDA POR SERVICIO*/
								if (getParameterValue(e.getKey().toString()) == null)
									lstParametrosValor.put(e.getKey().toString(), (((Combobox) oComponent).getSelectedItem().getValue()));
									break;
							}else if (oClass.equals(NumeroFlota.class) &&  ((Combobox) oComponent).getSelectedItem().getValue() instanceof NumeroFlota){
								/*BUSQUEDA NUMERO DE FLOTA*/
								if (getParameterValue(e.getKey().toString()) == null)
									lstParametrosValor.put(e.getKey().toString(), ((NumeroFlota) ((Combobox) oComponent).getSelectedItem().getValue()).getId());
									break;
							}else if (oClass.equals(EstadoDocumentoBus.class) &&  ((Combobox) oComponent).getSelectedItem().getValue() instanceof EstadoDocumentoBus){
								/*BUSQUEDA ESTADO DOCUMENTO BUS*/
								if (getParameterValue(e.getKey().toString()) == null)
									lstParametrosValor.put(e.getKey().toString(), ((EstadoDocumentoBus) ((Combobox) oComponent).getSelectedItem().getValue()).getId());
									break;
							}else if (oClass.equals(Bus.class) &&  ((Combobox) oComponent).getSelectedItem().getValue() instanceof Bus){
								/*BUSQUEDA POR NUMERO DE BUS*/
								if (getParameterValue(e.getKey().toString()) == null)
									lstParametrosValor.put(e.getKey().toString(), ((Bus) ((Combobox) oComponent).getSelectedItem().getValue()).getId());
									break;
							}else if (oClass.equals(UsuarioHardware.class) &&  ((Combobox) oComponent).getSelectedItem().getValue() instanceof UsuarioHardware){
								/*BUSQUEDA POR HARDWARE*/
								if (getParameterValue(e.getKey().toString()) == null)
									lstParametrosValor.put(e.getKey().toString(), (((Combobox) oComponent).getSelectedItem().getValue()));
									break;
							}else if (oClass.equals(CanalVenta.class) &&  ((Combobox) oComponent).getSelectedItem().getValue() instanceof CanalVenta){
								/*BUSQUEDA POR CANAL DE VENTA*/
								if (getParameterValue(e.getKey().toString()) == null)
									lstParametrosValor.put(e.getKey().toString(), (((Combobox) oComponent).getSelectedItem().getValue()));
									break;
							}else if (oClass.equals(TipoFormaPago.class) &&  ((Combobox) oComponent).getSelectedItem().getValue() instanceof TipoFormaPago){
								/*BUSQUEDA TIPO FORMA DE PAGO*/
								if (getParameterValue(e.getKey().toString()) == null)
									lstParametrosValor.put(e.getKey().toString(), (((Combobox) oComponent).getSelectedItem().getValue()));
									break;
							}else if (oClass.equals(MotivoCortesia.class) &&  ((Combobox) oComponent).getSelectedItem().getValue() instanceof MotivoCortesia){
								/*BUSQUEDA MOTIVO DE CORTECIA*/
								if (getParameterValue(e.getKey().toString()) == null)
									lstParametrosValor.put(e.getKey().toString(), (((Combobox) oComponent).getSelectedItem().getValue()));
									break;
							}else if (oClass.equals(Rol.class) &&  ((Combobox) oComponent).getSelectedItem().getValue() instanceof Rol){
								/*BUSQUEDA ROLES*/
								if (getParameterValue(e.getKey().toString()) == null)
									lstParametrosValor.put(e.getKey().toString(), (((Combobox) oComponent).getSelectedItem().getValue()));
									break;
							}else if (oClass.equals(Usuario.class) &&  ((Combobox) oComponent).getSelectedItem().getValue() instanceof Usuario){
								/*BUSQUEDA USUARIOS*/
								if (getParameterValue(e.getKey().toString()) == null)
									lstParametrosValor.put(e.getKey().toString(), (((Combobox) oComponent).getSelectedItem().getValue()));
									break;
							}else if (oClass.equals(OpcionMenu.class) &&  ((Combobox) oComponent).getSelectedItem().getValue() instanceof OpcionMenu){
								/*BUSQUEDA OPCION MENU */
								if (getParameterValue(e.getKey().toString()) == null)
									lstParametrosValor.put(e.getKey().toString(), (((Combobox) oComponent).getSelectedItem().getValue()));
									break;
							}else if (oClass.equals(TipoGasto.class) &&  ((Combobox) oComponent).getSelectedItem().getValue() instanceof TipoGasto){
								/*BUSQUEDA X TIPO DE GASTO*/
								if (getParameterValue(e.getKey().toString()) == null){
									lstParametrosValor.put(e.getKey().toString(), (((Combobox) oComponent).getSelectedItem().getValue()));
									break;}
							}else if (oClass.equals(Personal.class) &&  ((Combobox) oComponent).getSelectedItem().getValue() instanceof Personal){
								/*BUSQUEDA X PERSONAL*/
								if (getParameterValue(e.getKey().toString()) == null){
									lstParametrosValor.put(e.getKey().toString(), (((Combobox) oComponent).getSelectedItem().getValue()));
									break;}
							}
							else if (oClass.equals(UsuarioAprobador.class)&&((Combobox) oComponent).getSelectedItem().getValue() instanceof UsuarioAprobador){
								/*BUSQUEDA DE USUARIO APROBADORES*/
								if (getParameterValue(e.getKey().toString()) == null){
									lstParametrosValor.put(e.getKey().toString(), (((Combobox) oComponent).getSelectedItem().getValue()));
									break;}
							}else if(oClass.equals(AutorizadorCortesia.class) && ((Combobox) oComponent).getSelectedItem().getValue() instanceof AutorizadorCortesia){
								/*BUSQUEDA DE AUTORIZADOR DE CORTESIA*/
								if (getParameterValue(e.getKey().toString()) == null){
									lstParametrosValor.put(e.getKey().toString(), (((Combobox) oComponent).getSelectedItem().getValue()));
									break;
								}
							}else if (oClass.equals(TipoMoneda.class) && ((Combobox) oComponent).getSelectedItem().getValue() instanceof TipoMoneda){
								/*BUSQUEDA POR TIPO DE MONEDA*/
								if (getParameterValue(e.getKey().toString()) == null){
									lstParametrosValor.put(e.getKey().toString(), (((Combobox) oComponent).getSelectedItem().getValue()));
									break;
								}
							}

						}
					}
				}


				/*RECUPERA LOS VALORES DE OTROS OBJETOS*/
				for (Entry<?,?> e : lstControles.entrySet()) {
					String key = (String) e.getKey();
					Component oComponent =  (Component) e.getValue();

					if (oComponent instanceof Textbox) {
						Textbox oTextbox = (Textbox) oComponent;

						lstParametrosValor.put(key, oTextbox.getText());
					}
					else if (oComponent instanceof Spinner) {
						Spinner oSpinner = (Spinner) oComponent;

						lstParametrosValor.put(key, oSpinner.getValue());
					}
					else if (oComponent instanceof Datebox) {
						final Datebox oDatebox = (Datebox) oComponent;
						oDatebox.setFormat("dd/MM/yyyy");

						lstParametrosValor.put(key, oDatebox.getValue());
					}
					else if (oComponent instanceof Checkbox) {
						Checkbox oCheckbox = (Checkbox) oComponent
								;
						lstParametrosValor.put(key, oCheckbox.isChecked() ? 1 : 0);
					}else if (oComponent instanceof Intbox){
						Intbox oIntbox = (Intbox) oComponent;
						lstParametrosValor.put(key, oIntbox.getValue());
					}else if (oComponent instanceof Longbox){
						Longbox longbox = (Longbox) oComponent;
						lstParametrosValor.put(key, longbox);
					}
				}

				oEventListenerFilter.onEvent(new Event(com.cystesoft.vyrbus.view.ui.Events.ON_FILTER));
				oThisWindow.onClose();
			}
		});


		int widthColLabel=0;

		for (Entry<?,?> e : this.lstParametros.entrySet()) {
			Class<?> oClass = (Class<?>) e.getValue();


			Separator separator= new Separator();
			separator.setWidth("1px");

			String key = (String) e.getKey();
			Row oRow = new Row();

			oRow.appendChild(separator);

			Label oLabel = new Label(key);
			oLabel.setValue(oLabel.getValue().toUpperCase()+" :");
			oRow.appendChild(oLabel);

			//Para calcular el ancho de la primera columna del oGrid
			if(widthColLabel==0)
				widthColLabel=oLabel.getValue().trim().length();
			else if(widthColLabel<oLabel.getValue().trim().length())
				widthColLabel=oLabel.getValue().trim().length();

			String widthControls="170px";
			String widthControls2="160px";

			if(oClass.equals(String.class)) {
				Textbox oTextbox = new Textbox();
				oTextbox.setWidth(widthControls2);
				lstControles.put(key, oTextbox);
				oRow.appendChild(oTextbox);

			}else if(oClass.equals(Integer.class)) {
				Spinner oSpinner = new Spinner();
				oSpinner.setWidth(widthControls);
				lstControles.put(key,oSpinner);
				oRow.appendChild(oSpinner);

			}else if(oClass.equals(Date.class)) {
				Datebox oDatebox = new Datebox();
				oDatebox.setWidth(widthControls);
				oDatebox.setReadonly(true);
				oDatebox.setFormat("dd/MM/yyyy");
				lstControles.put(key, oDatebox);
				oRow.appendChild(oDatebox);

			}else if(oClass.equals(Datebox.class)) {
				Datebox oDatebox= new Datebox();
//				Calendar ocalendar= Calendar.getInstance();
				oDatebox.setFormat("dd/MM/yyyy");
				oDatebox.setReadonly(true);
				oDatebox.setValue(new Date());
				oDatebox.setWidth(widthControls);
				lstControles.put(key, oDatebox);
				oRow.appendChild(oDatebox);

//				if(e.getKey().equals("3. Fecha Inicio")){
				if(xc==0){
					oDatebox.addEventListener(Events.ON_CHANGE, new EventListener<Event>() {
						@Override
						public void onEvent(Event event) throws Exception {

							for (Entry<?,?> e : lstControles.entrySet()) {
								Component oComponent =  (Component) e.getValue();

								if(oComponent instanceof Datebox){
									Date fechaInio= ((Datebox)event.getTarget()).getValue();

									Datebox fechaFinal=(Datebox)oComponent;
									fechaFinal.setValue(fechaInio);
								}
							}
						}
					});
				}
				xc++;

			}else if(oClass.equals(Intbox.class)) {
				Intbox oIntbox= new Intbox();
				oIntbox.setWidth(widthControls2);
				lstControles.put(key, oIntbox);
				oRow.appendChild(oIntbox);

			}else if (oClass.equals(Longbox.class)){
				Longbox longbox = new Longbox();
				longbox.setWidth(widthControls2);
				lstControles.put(key, longbox);
				oRow.appendChild(longbox);

			}else if(oClass.equals(Boolean.class)) {
				Checkbox oCheckbox = new Checkbox();
				oCheckbox.setWidth(widthControls);
				lstControles.put(key, oCheckbox);
				oRow.appendChild(oCheckbox);

			}else {// Combo de acuerdo al pojo como parametro
				final Combobox oCombobox = new Combobox();
				oCombobox.setWidth(widthControls);

				if (key == "Porcentaje" || key=="Pasajeros Frecuentes"){
					UtilData.cargarAlternativos(oCombobox);
				}else if (oClass.equals(TipoDocumento.class)){
					/*Recupera los tipos de Documento diferente a los del bus*/
					TreeMap<String, Object> criteriosBusqueda = new TreeMap<>();
					criteriosBusqueda.put("tipo", TipoDocumento.PERSONALES);
					UtilData.cargarDataCombo(oCombobox, TipoDocumento.class, criteriosBusqueda, true);

				}else{
					if (key.equals("1. Origen") && oClass.equals(Localidad.class)){
						UtilData.cargarDataCombo(oCombobox, oClass, true);
						Agencia agencia=(Agencia)Executions.getCurrent().getDesktop().getSession().getAttribute(Constantes.ATRIBUTO_AGENCIA);
						Util.seleccionarValorItemCombo(oClass, oCombobox, agencia.getLocalidad().getId());
					}else{
						UtilData.cargarDataCombo(oCombobox, oClass, true);
						if(oClass.equals(Usuario.class))
							Util.seleccionarValorItemCombo(Usuario.class, oCombobox, ((Usuario)Executions.getCurrent().getDesktop().getSession().getAttribute(Constantes.ATRIBUTO_USUARIO)).getId());
					}
				}
//				oCombobox.setReadonly(true);
				oRow.appendChild(oCombobox);

			}

			oRows.appendChild(oRow);
			registrarControles(oRows);
		}

		Columns columns= new Columns();
		Column column= new Column();

		column.setWidth("10px");
		columns.appendChild(column);

		column= new Column();
		column.setWidth(String.valueOf(widthColLabel*7)+"px");
		column.setAlign("right");
		columns.appendChild(column);

		oGrid.appendChild(columns);

		oButton.setLabel("Filtrar");
		oButton.setHeight("35px");
		oButton.setImage("resources/mp_filtrar.png");
		oButton.setClass("btnCommandM");

//		oRowSeparador.appendChild(new Separator());
//		oRowSeparador.appendChild(new Separator());
//		oRowBoton.appendChild(new Separator());
//		oRowBoton.appendChild(oButton);

//		oRows.appendChild(oRowSeparador);
//		oRows.appendChild(oRowBoton);
		oGrid.appendChild(oRows);
		this.appendChild(oGrid);

		/*Grid para el button filtrar*/
		Grid gridBtn= new Grid();
		Rows rows= new Rows();
		Row row= new Row();
		columns= new Columns();
		column= new Column();

//		column.setWidth("200px");
		column.setWidth(String.valueOf(widthColLabel*8)+"px");
		columns.appendChild(column);
		gridBtn.appendChild(columns);

		row.appendChild(new Separator());
		row.appendChild(oButton);

		rows.appendChild(row);
		gridBtn.appendChild(rows);

		Separator separator=new Separator();
		separator.setHeight("1px");
		this.appendChild(separator);

		this.appendChild(gridBtn);

		this.setWidth(String.valueOf(widthColLabel*23)+"px");
		lstComponents = new ArrayList<>();
		controles(this);
		tabular();

//		this.setWidth("460px");
	}

	/**
	 * Obtenemos los controles que podran recibir el foco.
	 * @param component
	 */
	private void controles(Component component){
		for(int i=0; i<component.getChildren().size();i++){
			Component component1 = component.getChildren().get(i);
			if(component1 instanceof Textbox || component1 instanceof Spinner || component1 instanceof Datebox ||
					component1 instanceof Intbox || component1 instanceof Longbox || component1 instanceof Checkbox ||
					component1 instanceof Combobox || component1 instanceof Button)
				lstComponents.add(component1);

			if(component1.getChildren().size()>0 && !(component1 instanceof Columns))
				controles(component1);
		}
	}

	/**
	 * Asignamos el foco al componente cuando se presione la tecla enter.
	 */
	private void tabular(){
		indice = 0;
		for(int i=0; i<lstComponents.size()-1; i++){
			Component component = lstComponents.get(i);
			indice++;
			final Component component1 = lstComponents.get(indice);
			component.addEventListener(Events.ON_OK, new EventListener<Event>() {
				@Override
				public void onEvent(Event e){
					if(component1 instanceof Textbox)
						((Textbox)component1).setFocus(true);
					else if(component1 instanceof Spinner)
						((Spinner)component1).setFocus(true);
					else if(component1 instanceof Datebox)
						((Datebox)component1).setFocus(true);
					else if(component1 instanceof Intbox)
						((Intbox)component1).setFocus(true);
					else if(component1 instanceof Longbox)
						((Longbox)component1).setFocus(true);
					else if(component1 instanceof Checkbox)
						((Checkbox)component1).setFocus(true);
					else if(component1 instanceof Combobox)
						((Combobox)component1).setFocus(true);
					else if(component1 instanceof Button)
						((Button)component1).setFocus(true);
				}
			});
		}
	}


//	EventListener<Event> selectedEventListener = new EventListener<Event>() {
//		@Override
//		public void onEvent(Event event) throws Exception {
//			if (!(event.getTarget() instanceof Combobox && ((Combobox) event.getTarget()).getSelectedIndex() < 0 )) {
//
//				if()
//
//				for (Entry<?,?> e : lstParametros.entrySet()) {
//					Class<?> oClass = (Class<?>) e.getValue();
//					String key = (String) e.getKey();
//
//					lstControles.put(key, Agencia.class);
//
//
//				}
//
//			}
//		}
//	};


	@Override
	public boolean addEventListener(String evtnm, EventListener<? extends Event> listener) {
		boolean resultadoEvento = true;

		if (evtnm.equals(com.cystesoft.vyrbus.view.ui.Events.ON_FILTER)) {
			oEventListenerFilter = listener;
		}
			else {
				resultadoEvento = super.addEventListener(evtnm, listener);
		}

		return resultadoEvento;
	}

	@Override
	public void	setMode(String name) {
		if (name.equals("modal")) {
			try {
				this.generaControles();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		super.setMode(name);
	}

	public void addParameter(String label, Class<?> returnValue) {

		this.lstParametros.put(label, returnValue);
	}

	public Object getParameterValue(String label) {
		return this.lstParametrosValor.get(label);
	}

	private void registrarControles(Component oComponent) {
		if(oComponent instanceof InputElement || oComponent instanceof Checkbox
				|| oComponent instanceof Radio || oComponent instanceof Button) {

			lstControless.add(oComponent);

			if(oComponent instanceof InputElement) {
				InputElement oInputElement = (InputElement) oComponent;
				lstConstraints.put(oComponent.getId(), oInputElement.getConstraint());
				oInputElement.setConstraint("");

				if(this.inputElementEnfocable == null && oInputElement.isVisible()) {
					this.inputElementEnfocable = oInputElement;
				}
			}
		}else{
			List<Component> lstChildrens = oComponent.getChildren();

			for(Component oComponentHijo: lstChildrens) {
				registrarControles(oComponentHijo);
			}

		}
	}



}
