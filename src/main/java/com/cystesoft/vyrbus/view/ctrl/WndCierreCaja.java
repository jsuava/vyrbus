package com.cystesoft.vyrbus.view.ctrl;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import javax.security.auth.login.LoginException;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zul.Button;
import org.zkoss.zul.Column;
import org.zkoss.zul.Columns;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.Div;
import org.zkoss.zul.Doublebox;
//import org.zkoss.zul.Div;
import org.zkoss.zul.Grid;
import org.zkoss.zul.Groupbox;
import org.zkoss.zul.Hbox;
import org.zkoss.zul.Label;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listhead;
import org.zkoss.zul.Listheader;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Radio;
import org.zkoss.zul.Radiogroup;
import org.zkoss.zul.Row;
import org.zkoss.zul.Rows;
import org.zkoss.zul.Separator;
import org.zkoss.zul.Space;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Toolbar;
import org.zkoss.zul.Toolbarbutton;
import org.zkoss.zul.Window;

import com.cystesoft.vyrbus.model.bean.Agencia;
import com.cystesoft.vyrbus.model.bean.Liquidacion;
import com.cystesoft.vyrbus.model.bean.Rol;
import com.cystesoft.vyrbus.model.bean.TranscarLiquidacionTurno;
import com.cystesoft.vyrbus.model.bean.TranscarUsuarioPersonal;
import com.cystesoft.vyrbus.model.bean.Usuario;
import com.cystesoft.vyrbus.model.bean.VentaPasaje;
import com.cystesoft.vyrbus.service.exceptions.PasswordException;
import com.cystesoft.vyrbus.service.exceptions.UsuarioNullException;
import com.cystesoft.vyrbus.service.locator.ServiceLocator;
import com.cystesoft.vyrbus.service.util.Constantes;
import com.cystesoft.vyrbus.service.util.CreateDocument;
import com.cystesoft.vyrbus.service.util.Messages;
import com.cystesoft.vyrbus.service.util.MyTime;
import com.cystesoft.vyrbus.service.util.Util;
import com.cystesoft.vyrbus.service.util.UtilData;
import com.cystesoft.vyrbus.service.util.WSFE;
import com.cystesoft.vyrbus.view.tuentrada.LiquidacionTuentrada;
import com.cystesoft.vyrbus.view.ui.DlgMessage;
import com.cystesoft.vyrbus.view.ui.WndBase;
import com.cystesoft.vyrbus.view.ui.WndIFrame;

/**
 *
 * @author JABANTO
 *
 */
public class WndCierreCaja extends WndBase {

	private static final long serialVersionUID = -2594940987176197401L;

	private Datebox dbFechaInicio;
	private Datebox dbFechaFin;
	private Combobox cmbUsuario;
	private Listbox listLiquidacion;
	private Combobox cmbAgencia;
	private Button btnBuscar;

	private String ATRIBUTTE_LIQUIDACION="liquidacion";

	@SuppressWarnings("unused")
	private Agencia agencia=null;
	private Window wndValidaUsuario = null;
	private Window wndIngresoMonto = null;

	/*
	 * (non-Javadoc)
	 * @see com.tepsa.sisvyr.view.ui.WndBase#initComponents()
	 */
	@Override
	public void initComponents() {
		dbFechaInicio = (Datebox) this.getFellow("dbFechaInicio");
		dbFechaFin = (Datebox) this.getFellow("dbFechaFin");
		cmbUsuario = (Combobox) this.getFellow("cmbUsuario");
		listLiquidacion = (Listbox) this.getFellow("listLiquidacion");
		cmbAgencia=(Combobox) this.getFellow("cmbAgencia");
		btnBuscar=(Button)this.getFellow("btnBuscar");
	}

	/*
	 * Javadoc)
	 * @see com.tepsa.sisvyr.view.ui.WndBase#onCreate()
	 */
	@Override
	public void onCreate() throws Exception{
		agencia = new Agencia();
		agencia = (Agencia) getDesktop().getSession().getAttribute(Constantes.ATRIBUTO_AGENCIA);
		/**************************************************/
		MyTime time = new MyTime();
		Date date = Constantes.FORMAT_DATE_TIME_24H.parse(time.dateServer());

		dbFechaFin.setValue(date);
		Date fechaInico=new Date();
		fechaInico.setTime(date.getTime()-(Constantes.MILISEGUNDOS_X_DIA * 2));
		dbFechaInicio.setValue(fechaInico);

		UtilData.cargarAgenciaXtipoAgencia(cmbAgencia, getAgencia().getTipoAgencia().getId().intValue(), true);
		Util.seleccionarValorItemCombo(Agencia.class, cmbAgencia, getAgencia().getId());

		UtilData.cargarUsuariosLiquidacion(cmbUsuario, Constantes.FORMAT_DATE.format(fechaInico), Constantes.FORMAT_DATE.format(dbFechaFin.getValue()), true, getAgencia().getId());
		Util.seleccionarValorItemCombo(Usuario.class, cmbUsuario, getUsuario().getId());
		if(cmbUsuario.getSelectedIndex()==-1)
			cmbUsuario.setSelectedIndex(0);

		//Realiza la busqueda liquidaciones
		if(cmbUsuario.getSelectedIndex()>0)
			buscarLiquidacion();
		if(getRol().getId().intValue()==Constantes.ID_ROL_AGENCIA_VIAJES || getRol().getId().intValue()==Constantes.ID_ROL_CLIENTE_CORPORATIVO )
			cmbAgencia.setDisabled(true);
		else{
			//roles que tiene acceso al control cmbagencia
			List<Component>components=new ArrayList<>();
			components.add(cmbAgencia);
			List<Rol>rolAcceso=new ArrayList<>();
			rolAcceso.add(new Rol(Constantes.ID_ROL_SUPER_USUARIO));
			rolAcceso.add(new Rol(Constantes.ID_ROL_ADMIN));
			rolAcceso.add(new Rol(Constantes.ID_ROL_FINANZAS));
			accesoControlsByRol(components, rolAcceso);

			//roles que tiene acceso al control cmbusuario
			components=new ArrayList<>();
			components.add(cmbUsuario);
			rolAcceso.add(new Rol(Constantes.ID_ROL_ADMINISTRADOR));
			rolAcceso.add(new Rol(Constantes.ID_ROL_SUPER_USUARIO));
			rolAcceso.add(new Rol(Constantes.ID_ROL_ADMIN));
			rolAcceso.add(new Rol(Constantes.ID_ROL_FINANZAS));			
			accesoControlsByRol(components, rolAcceso);


			onLoadDatosDefault();
		}
	}

	/*
	 * Carga datos para los filtros
	 */
	public void onLoadDatosDefault() throws Exception{
		String fechaInicio=Constantes.FORMAT_DATE.format(dbFechaInicio.getValue());
		String fechaFin=Constantes.FORMAT_DATE.format(dbFechaFin.getValue());

		//Carga Usuarios
		if(cmbAgencia.getSelectedIndex()>0){
			Agencia agencia=cmbAgencia.getSelectedItem().getValue();
			UtilData.cargarUsuariosLiquidacion(cmbUsuario,fechaInicio,fechaFin, true, agencia.getId());
			Util.seleccionarValorItemCombo(Usuario.class, cmbUsuario, getUsuario().getId());
		}else
			UtilData.cargarGenericData(cmbUsuario, true);

		if(cmbUsuario.getSelectedIndex()==-1)
			cmbUsuario.setSelectedIndex(0);

		//Controla el button buscar
		btnBuscar.setDisabled(getRol().getId().intValue()!=Constantes.ID_ROL_SUPER_USUARIO && cmbUsuario.isDisabled() && cmbUsuario.getSelectedIndex()<1);

		Util.limpiarListbox(listLiquidacion);
	}



	/**
	 * Busca Liquidación, según los parametros seleccionados.
	 * @throws Exception
	 */
	public void buscarLiquidacion() throws Exception{

		Util.limpiarListbox(listLiquidacion);
		String fechaInicial=Constantes.FORMAT_DATE.format(dbFechaInicio.getValue());
		String FechaFinal = Constantes.FORMAT_DATE.format(dbFechaFin.getValue());
		Integer idAgencia = null;
		Integer idUsuario =null;
		if (cmbUsuario.getSelectedIndex()>0)
			idUsuario =((Usuario)cmbUsuario.getSelectedItem().getValue()).getId();
		if(cmbAgencia.getSelectedIndex()>0)
			idAgencia=((Agencia)cmbAgencia.getSelectedItem().getValue()).getId();
		Integer estadoLiquidacion= Constantes.AMBOS;

		/*Busca liquidaciones, segun parametros seleccionados*/
		List<Liquidacion> lstLiquidacion=ServiceLocator.getLiquidacionManager().buscarLiquidacion(fechaInicial, FechaFinal, idAgencia, idUsuario, estadoLiquidacion);
		Listitem item = null;
		Listcell cell = null;
		int x=0;
		for(Liquidacion liquidacion : lstLiquidacion){
			item = new Listitem();
			x += +1;
			cell = new Listcell(Integer.toString(x));
			item.appendChild(cell);
			cell = new Listcell(liquidacion.getUsuario().toString());
			item.appendChild(cell);
			cell = new Listcell(liquidacion.getUsuario().getLogin());
			item.appendChild(cell);
			cell = new Listcell(Constantes.FORMAT_DATE.format(liquidacion.getFechaLiquidacion()));
			cell.setStyle("font-size:11px !important");
			item.appendChild(cell);
			cell = new Listcell(liquidacion.getAgencia().getDenominacion());
			item.appendChild(cell);
			if (liquidacion.getestadoLiquidacion().equals(Constantes.LIQUI_ESTA_CERRADO))
				cell = new Listcell(Constantes.LIQUI_ESTA_CERRADO_LABEL);
			else
				cell = new Listcell(Constantes.LIQUI_ESTA_ABIERTO_LABEL);
			item.appendChild(cell);
			cell = new Listcell();
			if (liquidacion.getestadoLiquidacion().equals(Constantes.LIQUI_ESTA_CERRADO)){
				cell.setLabel(Constantes.FORMAT_LONG.format(liquidacion.getFechaModificacion()));
				cell.setStyle("font-size:11px !important");
			}
			item.appendChild(cell);

			cell=new Listcell();
			Hbox hbox=new Hbox();
			hbox.setAlign("center");

			/*Vista preliminar*/
			Toolbarbutton toolbarbutton =new Toolbarbutton();
			toolbarbutton.setAttribute(ATRIBUTTE_LIQUIDACION, liquidacion);
			toolbarbutton.setImage("/resources/mp_preliminar.png");
			toolbarbutton.setAutodisable("self");
			toolbarbutton.setTooltiptext("Vista Preliminar");
			hbox.appendChild(toolbarbutton);
			toolbarbutton.addEventListener(Events.ON_CLICK, new EventListener<Event>() {
				@Override
				public void onEvent(Event arg0) throws Exception {
					Liquidacion liquidacion = (Liquidacion) arg0.getTarget().getAttribute(ATRIBUTTE_LIQUIDACION);
					/*Valida la agencia*/
					if(getAgencia().getId().intValue()==Constantes.ID_AGENCIA_TUENTRADA || getAgencia().getId().intValue()==Constantes.ID_AGENCIA_SUPERMERCADOS_PERUANOS){
						List<LiquidacionTuentrada> lstLiquidacion = ServiceLocator.getLiquidacionManager().liquidacionTuentrada(getAgencia().getId(),liquidacion.getUsuario().getId(), Util.DatetoString(liquidacion.getFechaLiquidacion(), Constantes.DATE_FORMAT));
						mostrarLiquidacionTuentrada(lstLiquidacion, liquidacion.getUsuario(), Util.DatetoString(liquidacion.getFechaLiquidacion(), Constantes.DATE_FORMAT));
					}else{
//						boolean isReimprecion=liquidacion.getestadoLiquidacion().intValue()!=Constantes.TRUE_VALUE?true:false;
//						CreateDocument.creaLiquidacion(liquidacion, liquidacion.getUsuario(), isReimprecion);
//							preliminar(liquidacion);
						
						//Busca las liquidaciones de CARGA
						Liquidacion liquidacionCarga = UtilData.buscarLiquidacionCarga(liquidacion);
						liquidacion.setLiquidacionCarga(liquidacionCarga);
						//****
						String nameFile = CreateDocument.creaRptLiquidacionByEspecieValorada(liquidacion, true);
						String src=Constantes.URL_FORMATOS_LIQUIDACION +Constantes.CLAVE_PAHT+ nameFile;
						/*Carga el iframe*/
//							String src=Constantes.URL_FORMATOS_LIQUIDACION +Constantes.CLAVE_PAHT+ liquidacion.getId()+".txt";

						final WndIFrame iFrame = new WndIFrame();
						iFrame.setSrc(src);
						iFrame.setWidth("810");
						iFrame.setheight("600");
						iFrame.loadiframe();
						
						appendChild(iFrame);
						iFrame.setMode("modal");
					}
				}
			});

			/*Impresión*/
			toolbarbutton =new Toolbarbutton();
			toolbarbutton.setAttribute(ATRIBUTTE_LIQUIDACION, liquidacion);
			toolbarbutton.setImage("/resources/mp_imprimir.png");
			toolbarbutton.setTooltiptext("Imprimir");
			toolbarbutton.setDisabled(liquidacion.getestadoLiquidacion().intValue()==Constantes.TRUE_VALUE);
			toolbarbutton.setAutodisable("self");
			hbox.appendChild(toolbarbutton);
			toolbarbutton.addEventListener(Events.ON_CLICK, new EventListener<Event>() {
				@Override
				public void onEvent(Event arg0) throws Exception {
					Liquidacion liquidacion = (Liquidacion) arg0.getTarget().getAttribute(ATRIBUTTE_LIQUIDACION);
//					imprimirLiquidacion(liquidacion);
					openWindowPrintLiquidacion(liquidacion);
				}
			});

			/*Cierre*/
			toolbarbutton =new Toolbarbutton();
			toolbarbutton.setAttribute(ATRIBUTTE_LIQUIDACION, liquidacion);
			toolbarbutton.setImage("/resources/mp_cierreCaja.png");
			toolbarbutton.setTooltiptext("Cerrar");
			toolbarbutton.setAutodisable("self");
			toolbarbutton.setVisible(liquidacion.getestadoLiquidacion().intValue()==Constantes.TRUE_VALUE);
			hbox.appendChild(toolbarbutton);
			toolbarbutton.addEventListener(Events.ON_CLICK, new EventListener<Event>() {
				@Override
				public void onEvent(Event arg0) throws Exception {
					final Liquidacion liquidacion=(Liquidacion) arg0.getTarget().getAttribute(ATRIBUTTE_LIQUIDACION);
					Messagebox.show(Messages.getString("WndCierreCaja.Information.CerrarCaja"), DlgMessage.NOMBREAPLICACION, DlgMessage.BTN_YESNO, Messagebox.QUESTION,DlgMessage.BTN_DEFAULT_NO,new EventListener<Event>() {
						@Override
						public void onEvent(Event e) throws Exception {
							if(e.getName().equals("onYes")){
								Listbox lstCoretes=validaCorteEspVal(liquidacion);
								if(lstCoretes.getItems().size()>0)
									openWindowCortes(lstCoretes,liquidacion);
								else
									openWindowSolicitaPassword(liquidacion);
							}
						}
					});
				}
			});


			/*reaperturar*/
			toolbarbutton =new Toolbarbutton();
			toolbarbutton.setAttribute(ATRIBUTTE_LIQUIDACION, liquidacion);
			toolbarbutton.setImage("/resources/windows/window_aperturaCaja.png");
			toolbarbutton.setTooltiptext("Reaperturar liquidación.");
			toolbarbutton.setAutodisable("self");
			toolbarbutton.setDisabled(true);
			/*Valida si es rol admin. punto venta y la fecha de la liquidacion es la misma al dia actual*/
			if( (getRol().getId().intValue()==Constantes.ID_ROL_ADMINISTRADOR)
					&& Constantes.FORMAT_DATE.format(liquidacion.getFechaLiquidacion()).equals(Constantes.FORMAT_DATE.format(new Date()))){
				toolbarbutton.setDisabled(false);
			}else if (getRol().getId().intValue()==Constantes.ID_ROL_SUPER_USUARIO)
				toolbarbutton.setDisabled(false);

			hbox.appendChild(toolbarbutton);
			toolbarbutton.addEventListener(Events.ON_CLICK, new EventListener<Event>() {
				@Override
				public void onEvent(Event arg0) throws Exception {
					final Liquidacion liquidacion=(Liquidacion) arg0.getTarget().getAttribute(ATRIBUTTE_LIQUIDACION);
					Messagebox.show(Messages.getString("WndCierreCaja.Question.reaperturarLiquidacion"), DlgMessage.NOMBREAPLICACION, DlgMessage.BTN_YESNO, Messagebox.QUESTION,DlgMessage.BTN_DEFAULT_NO,new EventListener<Event>() {
						@Override
						public void onEvent(Event e) throws Exception {
							if(e.getName().equals("onYes")){
								try {
									/*********************************************************************************************/
									//Primero apertura la liquidacion de carga
									TranscarLiquidacionTurno liquidacionTurnoCarga= new TranscarLiquidacionTurno();
									liquidacionTurnoCarga.setFechaApertura(liquidacion.getFechaLiquidacion());
									TranscarUsuarioPersonal usuarioPersonal = ServiceLocator.getTranscarWebManager().buscarUsuario(liquidacion.getUsuario().getLogin());
									if(usuarioPersonal==null) {
										DlgMessage.information("No se puede reaperturar la liqudiación de Carga, debido a que el usuario "+getUsuario().getLogin()+" no existe en el sistema de carga.");
										return;
									}

									liquidacionTurnoCarga.setTranscarUsuarioPersonal(usuarioPersonal);
									liquidacionTurnoCarga.setAgenciaId(liquidacion.getAgencia().getId());
									UtilData.auditarRegistro(liquidacionTurnoCarga, true, getUsuario(), Executions.getCurrent());
									String messageError = ServiceLocator.getTranscarWebManager().aperturarLiquidacion(liquidacionTurnoCarga, true);
									if (messageError!=null) {
										DlgMessage.information(messageError+" - TRANSCAR");
										return;
									}
									
//									openWindowSolicitaPassword(liquidacion);
									//Reapertura la liquidacion - vyrbus
									Liquidacion oliquidacion=ServiceLocator.getLiquidacionManager().buscarPorId(liquidacion.getId().longValue());
									oliquidacion.setEstadoLiquidacion(Constantes.TRUE_VALUE);
									UtilData.auditarRegistro(oliquidacion, true, getUsuario(), Executions.getCurrent());
									ServiceLocator.getLiquidacionManager().actualizar(oliquidacion);
									//Elimina el detalle de la liquidacion
									ServiceLocator.getDetalleLiquidacionManager().deleteXidLiquidacion(liquidacion.getId().longValue());
									//
									buscarLiquidacion();

									DlgMessage.information(Messages.getString("WndCierreCaja.Information.liquidacionReaperturada"));
								} catch (Exception e2) {
									DlgMessage.error(e2.getMessage());
								}
							}
						}
					});
				}
			});

			toolbarbutton =new Toolbarbutton();
			toolbarbutton.setAttribute(ATRIBUTTE_LIQUIDACION, liquidacion);
			toolbarbutton.setImage("/resources/mp_previo.png");
			toolbarbutton.setTooltiptext("Resumen Liquidacion");
			toolbarbutton.setAutodisable("self");
			//toolbarbutton.setDisabled(true);

			hbox.appendChild(toolbarbutton);
			toolbarbutton.addEventListener(Events.ON_CLICK, new EventListener<Event>() {
				@Override
				public void onEvent(Event arg0) throws Exception {
					System.out.println("Listo para mostrar");
				}
			});

			cell.appendChild(hbox);
			item.appendChild(cell);

			item.setValue(liquidacion);
			listLiquidacion.appendChild(item);
		}
	}

	/**
	 * Realiza la validacion de los cortes en las especies valoradas.
	 * @param liquidacion : Instancia del objeto liquidacion.
	 * @return objeto Listbox con los cortes encontrados, si es que lo haya.
	 * @throws Exception
	 */
	private Listbox validaCorteEspVal(Liquidacion liquidacion) throws Exception{
//		Liquidacion liquidacion = listLiquidacion.getSelectedItem().getValue();
		String fechaLiquidacion=Constantes.FORMAT_DATE.format(liquidacion.getFechaLiquidacion());
		List<VentaPasaje> lstResul = ServiceLocator.getVentaPasajesManager().correlativosFaltantesX(fechaLiquidacion,null,null, liquidacion.getAgencia().getId(),
																									fechaLiquidacion,liquidacion.getUsuario().getId());
		Listbox lbxVerificaCorrelativos= new Listbox();
		Listhead listhead= new Listhead();
		Listheader listheader= new Listheader();
		listheader.setLabel("SERIE");listheader.setWidth("40px");
		listhead.appendChild(listheader);
		lbxVerificaCorrelativos.appendChild(listhead);
		listheader= new Listheader();
		listheader.setLabel("NÚMERO FALTANTES");
		listhead.appendChild(listheader);
		lbxVerificaCorrelativos.appendChild(listhead);

		if(lstResul.size()>0){
			Listitem item=null;
			Listcell cell=null;
//			int x=0;
			Long n_bolBack=(long)0;
			Long n_bolNext=(long)0;

//			String c_inicio="";
//			String c_fin="";
			String c_serieNext="";
			String c_faltantes="";

			for(int i=0; i<lstResul.size();i++){
				c_faltantes=""; //x=0;

				if(i<lstResul.size()){
					VentaPasaje vp=lstResul.get(i);
					String c_serie=vp.getNumeroSerie();
//					c_inicio=vp.getNumeroBoleto();

					/*Obtiene el ultimo número segun el rango configurador en la variable "parametro" y valida los correlativos faltantes*/
					String c_serieBack="";
					for(int z=i; z<lstResul.size(); z++){
//						x++;

						c_serieNext=lstResul.get(z).getNumeroSerie();
						n_bolNext=Long.valueOf(lstResul.get(z).getNumeroBoleto());

						//Valida los correlativos faltantes
						if(n_bolBack>0)
							if(!(n_bolBack+1==n_bolNext)){
								Long n_falIni=n_bolBack; Long n_falFin=(long)0;
								for(int t=1; t<(n_bolNext-n_bolBack); t++){
									if(t==1)n_falIni++;
									n_falFin=n_falIni+t;
									if(t==(n_bolNext-n_bolBack)-1)n_falFin--;
								}

								if((n_falIni.equals(n_falFin))){
									String correlativo = "0000000"+n_falIni;
									String numero=correlativo.substring(correlativo.length()-7);
									if(c_faltantes.length()>0)
										c_faltantes+=";"+String.valueOf(numero);
									else
										c_faltantes+=String.valueOf(numero);
								}

							}

						//Obtiene el ultimo numero segun el rango configurador en la variable "parametro"
//						if(c_serieNext.equals(c_serie))
//							c_fin=((VentaPasaje)lstResul.get(z)).getNumeroBoleto();

						if(c_serieBack.length()>0 && (!c_serieNext.equals(c_serieBack)))
							break;

						c_serieBack=lstResul.get(z).getNumeroSerie();
						n_bolBack=Long.valueOf(lstResul.get(z).getNumeroBoleto());
						i=z;
					}

					if(c_faltantes.length()>0){
						item= new Listitem();

						cell=new Listcell(c_serie);
						if(c_faltantes.length()>0)cell.setStyle("font-size:11px !important;");
						else cell.setStyle("font-size:11px !important");
						item.appendChild(cell);

						cell=new Listcell(c_faltantes);
						if(c_faltantes.length()>0)cell.setStyle("font-size:11px !important;");
						else cell.setStyle("font-size:11px !important");
						item.appendChild(cell);

						lbxVerificaCorrelativos.appendChild(item);

						c_faltantes="";
					}

				}
			}
		}
		return lbxVerificaCorrelativos;
	}

	/**
	 * Abre la ventada con los cortes encontrados en las especies valoradas.
	 * @param listbox 		: Objeto listbox de donde se van a leer los correlativos faltantes
	 * @param liquidacion 	: Instancia del objeto liquidacion.
	 */
	private void openWindowCortes(Listbox listbox, Liquidacion liquidacion){
		wndValidaUsuario = createWindowCortes(listbox,liquidacion);
		this.appendChild(wndValidaUsuario);
		wndValidaUsuario.setMode("modal");
	}
	/**
	 * Crea ventada con los cortes encontrados en las especies valoradas.
	 * @param listbox 		: Objeto listbox de donde se van a leer los correlativos faltantes
	 * @param liquidacion	: Instancia del objeto liquidacion.
	 * @return objet window
	 */
	private Window createWindowCortes(Listbox listbox, final Liquidacion liquidacion){
		final Window window = new Window("BOLETOS FALTANTES", "none", true);
		window.setWidth("450px");
		Label label= new Label("Se a encontrado Boletos faltantes dentro de la liquidación, si desea omitirlos pulse el botón Continuar, caso contrario Cancelar.");
		label.setStyle("font-size:11px !important; font-weight: bold");
		window.appendChild(label);
		window.appendChild(new Separator());

		/*Aceptar*/
		final Button btnAceptar = new Button("Continuar");
		btnAceptar.setImage("/resources/mp_aceptarEnabled.png");
		btnAceptar.addEventListener(Events.ON_CLICK,new EventListener<Event>() {
			@Override
			public void onEvent(Event event) throws Exception {
				openWindowSolicitaPassword(liquidacion);
				window.onClose();
			}
		});

		/*Cancelar*/
		Button btncancelar = new Button("Cancelar");
		btncancelar.setImage("/resources/mp_cancelarEnabled.png");
		btncancelar.addEventListener(Events.ON_CLICK, new EventListener<Event>() {
			@Override
			public void onEvent(Event event) throws Exception {
				window.onClose();
			}
		});

		Groupbox groupbox= new Groupbox();
		groupbox.appendChild(listbox);
		window.appendChild(groupbox);

		Hbox hbox= new Hbox();
		Separator separator= new Separator();
		separator.setWidth("30px");
		hbox.appendChild(separator);
		hbox.appendChild(btncancelar);
		hbox.appendChild(btnAceptar);
		window.appendChild(hbox);

		return window;
	}


	/**
	 * Abre la venta que solicita el password para identificar al usuario que reliza el cierre de la liquidacion
	 * @param liquidacion	: Instancia del objeto liquidacion
	 */
	private void openWindowSolicitaPassword(Liquidacion liquidacion){
		wndValidaUsuario = createWindowSolicitaPassword(liquidacion);
		this.appendChild(wndValidaUsuario);
		wndValidaUsuario.setMode("modal");
	}

	/**
	 * Crea la venta que solicita el password para identificar al usuario que reliza el cierre de la liquidacion
	 * @return objet window
	 */
	@SuppressWarnings("deprecation")
	private Window createWindowSolicitaPassword(final Liquidacion liquidacion){
		final Window window = new Window();
		window.setTitle("::: Identificación del Usuario :::");
		window.setBorder(true);
		window.setWidth("350px");

		Grid grid= new Grid();
		Columns columns= new Columns();

		Rows rows= new Rows();
		Row row= new Row();
		Column column= new Column();

		column.setWidth("100px");
		column.setAlign("right");
		columns.appendChild(column);


		column= new Column();
		columns.appendChild(column);

		grid.appendChild(columns);

		final Textbox txtUsurio = new Textbox();
		txtUsurio.setWidth("160px");
		txtUsurio.setMaxlength(20);
		txtUsurio.setStyle("font-size:12px !important; text-transform:lowercase");

		final Textbox txtPassrowd = new Textbox();
		txtPassrowd.setType("password");
		txtPassrowd.setWidth("160px");
		txtPassrowd.setMaxlength(20);
		txtPassrowd.setStyle("font-size:12px !important");

		Label label= new Label("Usuario (*) :");
		label.setSclass("label-size11");
		row=new Row();
		row.appendChild(label);
		row.appendChild(txtUsurio);
		rows.appendChild(row);

		label= new Label("Password (*) :");
		label.setSclass("label-size11");
		row=new Row();

		row.appendChild(label);
		row.appendChild(txtPassrowd);
		rows.appendChild(row);

		row=new Row();
		row.appendChild(new Separator());
		row.appendChild(new Separator());
		rows.appendChild(row);

		rows.appendChild(row);
		grid.appendChild(rows);
		window.appendChild(grid);

		Hbox hbox=new Hbox();
		hbox.setAlign("center");
		Div div=new Div();
		div.setAlign("center");
		div.setWidth(window.getWidth());
		Toolbar toolbar=new Toolbar();
		Button btncancelar=new Button("Cancelar", "/resources/mp_cerrar.png");
		btncancelar.setStyle("font-size:12px !important");
		btncancelar.setWidth("120px");
		btncancelar.setAutodisable("self");
//		btncancelar.setMold("trendy");
		btncancelar.setSclass("btnCommandM");
		hbox.appendChild(btncancelar);

		Separator separator=new Separator();
		separator.setWidth("10px");
		hbox.appendChild(separator);

		Button btnAceptar=new Button("Aceptar", "/resources/mp_aceptarEnabled.png");
		btnAceptar.setStyle("font-size:12px !important");
		btnAceptar.setWidth("120px");
		btnAceptar.setAutodisable("self");
//		btnAceptar.setMold("trendy");
		btnAceptar.setSclass("btnCommandM");
		hbox.appendChild(btnAceptar);

		div.appendChild(hbox);
		toolbar.appendChild(div);
		window.appendChild(toolbar);

		txtUsurio.addEventListener(Events.ON_OK, new EventListener<Event>() {
			@Override
			public void onEvent(Event event) throws Exception {
				txtPassrowd.setFocus(true);
			}
		});

		txtPassrowd.addEventListener(Events.ON_OK, new EventListener<Event>() {
			@Override
			public void onEvent(Event event) throws Exception {
				try{
					if(txtUsurio.getText().trim().isEmpty())
						throw new UsuarioNullException();
					else if (txtPassrowd.getText().trim().isEmpty())
						throw new PasswordException(PasswordException.PASSWORD_NULL);

					ServiceLocator.getUsuarioManager().buscarUsuarioPorLoginPassword(txtUsurio.getText().trim(), txtPassrowd.getText().trim(), Constantes.VALUE_ACTIVO);
					openWindowIngresoMonto(liquidacion);
					window.onClose();

				}catch (UsuarioNullException une){
					DlgMessage.information(Messages.getString("WndCierreCaja.Information.UsuarioNull"),txtUsurio);
				}catch (PasswordException cp){
					if(cp.getTipo().intValue()==PasswordException.PASSWORD_NULL)
						DlgMessage.information(Messages.getString("WndCierreCaja.Information.PasswordNull"),txtPassrowd);
					else if (cp.getTipo().intValue()==PasswordException.PASSWORD_INCOREC)
						DlgMessage.information(Messages.getString("WndCierreCaja.Information.SolicitaPasswordInvalido"),txtPassrowd);
				}catch (LoginException le){
					DlgMessage.information(Messages.getString("WndCierreCaja.Information.SolicitaPasswordInvalido"),txtPassrowd);
				}catch (Exception ex) {
					DlgMessage.error(this.getClass().getName() + " " + ex.getMessage());
					ex.printStackTrace();
				}

			}
		});

		btnAceptar.addEventListener(Events.ON_CLICK,new EventListener<Event>() {
			@Override
			public void onEvent(Event event) throws Exception {
				try{
					if(txtUsurio.getText().trim().isEmpty())
						throw new UsuarioNullException();
					else if (txtPassrowd.getText().trim().isEmpty())
						throw new PasswordException(PasswordException.PASSWORD_NULL);

					ServiceLocator.getUsuarioManager().buscarUsuarioPorLoginPassword(txtUsurio.getText().trim(), txtPassrowd.getText().trim(), Constantes.VALUE_ACTIVO);
					openWindowIngresoMonto(liquidacion);
					window.onClose();

				}catch (UsuarioNullException une){
					DlgMessage.information(Messages.getString("WndCierreCaja.Information.UsuarioNull"),txtUsurio);
				}catch (PasswordException cp){
					if(cp.getTipo().intValue()==PasswordException.PASSWORD_NULL)
						DlgMessage.information(Messages.getString("WndCierreCaja.Information.PasswordNull"),txtPassrowd);
					else if (cp.getTipo().intValue()==PasswordException.PASSWORD_INCOREC)
						DlgMessage.information(Messages.getString("WndCierreCaja.Information.SolicitaPasswordInvalido"),txtPassrowd);
				}catch (LoginException le){
					DlgMessage.information(Messages.getString("WndCierreCaja.Information.SolicitaPasswordInvalido"),txtPassrowd);
				} catch (Exception ex) {
					DlgMessage.error(this.getClass().getName() + " " + ex.getMessage());
					ex.printStackTrace();
				}

			}
		});

		btncancelar.addEventListener(Events.ON_CLICK, new EventListener<Event>() {
			@Override
			public void onEvent(Event event) throws Exception {
				window.onClose();
			}
		});

		return window;
	}

	/**
	 * Abre la ventana para que el usuario ingrese el monto con el cual esta cerrando la liquidacion
	 * @param liquidacion	: Instancia del Objeto liquidacion.
	 * @throws Exception
	 */
	private void openWindowPrintLiquidacion(Liquidacion liquidacion) throws Exception{
		wndIngresoMonto = createWindowPrintLiquidacion(liquidacion);
		this.appendChild(wndIngresoMonto);
		wndIngresoMonto.setMode("modal");
	}

	/**
	 * Crea la venta que solicita el password para identificar al usuario que reliza el cierre de la liquidacion
	 * @return objet window
	 */
	@SuppressWarnings("deprecation")
	private Window createWindowPrintLiquidacion(final Liquidacion liquidacion){
		final Window window = new Window();
		window.setTitle("::: Impresión :::");
		window.setBorder(true);
		window.setWidth("350px");

		Grid grid= new Grid();
		Columns columns= new Columns();

		Rows rows= new Rows();
		Row row= new Row();
		Column column= new Column();

		column.setWidth("120px");
		column.setAlign("right");
		columns.appendChild(column);


		column= new Column();
		columns.appendChild(column);

		grid.appendChild(columns);

		final Radiogroup radiogroup= new Radiogroup();
		Radio radioTermico = new Radio("Térmico");
		radioTermico.setChecked(true);
		final Radio radioMatrical = new Radio("Matricial");
		radiogroup.appendChild(radioTermico);
		radiogroup.appendChild(radioMatrical);

		Label label= new Label("Tipo de impresión (*) :");
		label.setSclass("label-size11");
		row=new Row();
		row.appendChild(label);
		row.appendChild(radiogroup);
		rows.appendChild(row);

		row=new Row();
		row.appendChild(new Separator());
		row.appendChild(new Separator());
		rows.appendChild(row);

		rows.appendChild(row);
		grid.appendChild(rows);
		window.appendChild(grid);

		Hbox hbox=new Hbox();
		hbox.setAlign("center");
		Div div=new Div();
		div.setAlign("center");
		div.setWidth(window.getWidth());
		Toolbar toolbar=new Toolbar();
		Button btncancelar=new Button("Cancelar", "/resources/mp_cerrar.png");
		btncancelar.setStyle("font-size:12px !important");
		btncancelar.setWidth("120px");
		btncancelar.setAutodisable("self");
//		btncancelar.setMold("trendy");
		btncancelar.setSclass("btnCommandM");
		hbox.appendChild(btncancelar);

		Separator separator=new Separator();
		separator.setWidth("10px");
		hbox.appendChild(separator);

		Button btnAceptar=new Button("Aceptar", "/resources/mp_aceptarEnabled.png");
		btnAceptar.setStyle("font-size:12px !important");
		btnAceptar.setWidth("120px");
		btnAceptar.setAutodisable("self");
//		btnAceptar.setMold("trendy");
		btnAceptar.setSclass("btnCommandM");
		hbox.appendChild(btnAceptar);

		div.appendChild(hbox);
		toolbar.appendChild(div);
		window.appendChild(toolbar);

		btnAceptar.addEventListener(Events.ON_CLICK,new EventListener<Event>() {
			@Override
			public void onEvent(Event event) throws Exception {
				try{
					
					if(radioMatrical.isChecked())
						imprimirLiquidacion(liquidacion);
					else
						WSFE.printLiquidacion(liquidacion, wndIngresoMonto);
					
					window.onClose();
				} catch (Exception ex) {
					DlgMessage.error(this.getClass().getName() + " " + ex.getMessage());
					ex.printStackTrace();
				}

			}
		});

		btncancelar.addEventListener(Events.ON_CLICK, new EventListener<Event>() {
			@Override
			public void onEvent(Event event) throws Exception {
				window.onClose();
			}
		});

		return window;
	}


	/**
	 * Abre la ventana para que el usuario ingrese el monto con el cual esta cerrando la liquidacion
	 * @param liquidacion	: Instancia del Objeto liquidacion.
	 * @throws Exception
	 */
	private void openWindowIngresoMonto(Liquidacion liquidacion) throws Exception{
		wndIngresoMonto = createWindowIngresMonto(liquidacion);
		this.appendChild(wndIngresoMonto);
		wndIngresoMonto.setMode("modal");
	}
	/**
	 * Crea la ventana para que el usuario ingrese el monto con el cual esta cerrando la liquidacion
	 * @param liquidacion	: Instancia del objeto liquidacion
	 * @return	object window
	 * @throws Exception
	 */
	@SuppressWarnings("deprecation")
	private  Window createWindowIngresMonto(final Liquidacion liquidacion) throws Exception {
		final Window window = new Window();
		window.setWidth("350px");
		window.setTitle("::: Cierre Liquidación de Turno :::");
//		window.setWidth("350px");
		window.setBorder(true);

		Grid grid = new Grid();

		Columns columns=new Columns();
		Column column=new Column();
		column.setAlign("right");
		column.setWidth("200px");
		columns.appendChild(column);
		columns.appendChild(new Column());
		grid.appendChild(columns);

		Rows rows = new Rows();
		Row row = new Row();
		row=new Row();
		Label label = new Label("Ingrese el efectivo en S/. a Liquidar :");
		label.setStyle("font-size:11px !important;color:break");
		row.appendChild(label);
		final Doublebox dbMonto;
		dbMonto =  new Doublebox();
		dbMonto.setValue(.00);
		dbMonto.setWidth("80px");
		dbMonto.setFormat("#,##0.00");
		dbMonto.setLocale(Locale.US);
		row.appendChild(dbMonto);
		rows.appendChild(row);

		row = new Row();
		label = new Label("Ingrese el efectivo en US$ a Liquidar :");
		label.setStyle("font-size:11px !important;color:break");
		row.appendChild(label);
		final Doublebox dbxMontoDolares;
		dbxMontoDolares =  new Doublebox();
		dbxMontoDolares.setValue(.00);
		dbxMontoDolares.setWidth("80px");
		dbxMontoDolares.setFormat("#,##0.00");
		dbxMontoDolares.setLocale(Locale.US);
		row.appendChild(dbxMontoDolares);
		rows.appendChild(row);

		row=new Row();
		row.appendChild(new Space());
		row.appendChild(new Space());
		rows.appendChild(row);

		Space space = new Space();
		space.setWidth("70px");
		space.setHeight("35px");
		Hbox hbox = new Hbox();
		hbox.setAlign("right");
		hbox.appendChild(space);

		grid.appendChild(rows);
		window.appendChild(grid);

		hbox=new Hbox();
		hbox.setAlign("center");
		Div div=new Div();
		div.setAlign("center");
		div.setWidth(window.getWidth());
		Toolbar toolbar=new Toolbar();
		Button btnCancelar=new Button("Cancelar", "/resources/mp_cerrar.png");
		btnCancelar.setStyle("font-size:12px !important");
		btnCancelar.setWidth("120px");
		btnCancelar.setAutodisable("self");
//		btnCancelar.setMold("trendy");
		btnCancelar.setSclass("btnCommandM");
		hbox.appendChild(btnCancelar);

		Separator separator=new Separator();
		separator.setWidth("10px");
		hbox.appendChild(separator);

		Button btnAceptar=new Button("Aceptar", "/resources/mp_aceptarEnabled.png");
		btnAceptar.setStyle("font-size:12px !important");
		btnAceptar.setWidth("120px");
		btnAceptar.setAutodisable("self");
//		btnAceptar.setMold("trendy");
		btnAceptar.setSclass("btnCommandM");
		hbox.appendChild(btnAceptar);

		div.appendChild(hbox);
		toolbar.appendChild(div);
		window.appendChild(toolbar);


		/*CANCELAR*/
		btnCancelar.addEventListener(Events.ON_CLICK, new EventListener<Event>() {
			@Override
			public void onEvent(Event event) throws Exception {
				window.onClose();
			}
		});
		/*Evento enter*/
		dbMonto.addEventListener(Events.ON_OK, new EventListener<Event>() {
			@Override
			public void onEvent(Event event) throws Exception {
				dbxMontoDolares.setFocus(true);
			}
		});
		/*Evento enter*/
		dbxMontoDolares.addEventListener(Events.ON_OK, new EventListener<Event>() {
			@Override
			public void onEvent(Event event) throws Exception {
				Messagebox.show(Messages.getString("WndCierreCaja.Information.questionCerrarCaja"), DlgMessage.NOMBREAPLICACION, DlgMessage.BTN_YESNO, Messagebox.QUESTION,DlgMessage.BTN_DEFAULT_NO, new EventListener<Event>() {
					@Override
					public void onEvent(Event e) throws Exception {
						if(e.getName().equals("onYes")){
							cerrarLiquidacion(liquidacion, dbMonto.getValue(), window, dbxMontoDolares.getValue());
						}
					}
				});
			}
		});
		/*Evento Click del button*/
		btnAceptar.addEventListener(Events.ON_CLICK, new EventListener<Event>() {
			@Override
			public void onEvent(Event event) throws Exception {
				Messagebox.show(Messages.getString("WndCierreCaja.Information.questionCerrarCaja"), DlgMessage.NOMBREAPLICACION, DlgMessage.BTN_YESNO, Messagebox.QUESTION,DlgMessage.BTN_DEFAULT_NO, new EventListener<Event>() {
					@Override
					public void onEvent(Event e) throws Exception {
						if(e.getName().equals("onYes")){
							cerrarLiquidacion(liquidacion, dbMonto.getValue(), window, dbxMontoDolares.getValue());
						}
					}
				});
			}
		});
		return window;
	}

	/**
	 * Realiza la peticion del cierre de la liquidacion
	 * @param liquidacion		: Instancia del objeto liquidacion.
	 * @param montoIngresado	: Monto con el cual el usuario esta cerrando la liquidacion.
	 * @param window 			: objeto window del cual esta llamando a este método.
	 * @throws Exception
	 */
	private final void cerrarLiquidacion(final Liquidacion liquidacion, double montoIngresado, Window window, double montoIngresadoDolares) throws Exception{
		/* Procesa cierre de caja*/
		UtilData.procesaCierreCaja(liquidacion, montoIngresado, getUsuario(), montoIngresadoDolares);

		/* Procesa el cierre de caja en Carga*/
		try {
			TranscarUsuarioPersonal transcarUsuarioPersonal = ServiceLocator.getTranscarWebManager().buscarUsuario(liquidacion.getUsuario().getLogin());
			int agenciaIdCargo = 0;
			String fechaLiquidacion =Constantes.FORMAT_DATE.format(liquidacion.getFechaLiquidacion());
			if(transcarUsuarioPersonal!=null ) {//&& liquidacion.getAgencia().getCodigo()!=null){
				agenciaIdCargo = liquidacion.getAgencia().getId();  //ServiceLocator.getTranscarManager().buscarIdAgenciaByCodigoAgenciaPasajes(liquidacion.getAgencia().getId().toString());
//				ServiceLocator.getTranscarWebManager().cerrarLiquidacion(transcarUsuarioPersonal.getId(), agenciaIdCargo, fechaLiquidacion, fechaLiquidacion);
				ServiceLocator.getTranscarWebManager().cerrarLiquidacion(transcarUsuarioPersonal.getId(), agenciaIdCargo, fechaLiquidacion, .00, .00);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}



		/* Procesa cierre de caja de la venta de seguros*/
//		VSLiquidacion vsLiquidacion=ServiceLocator.getVentaSeguroManager().buscarLiquidacion(liquidacion.getUsuario().getId(),liquidacion.getAgencia().getId(), Constantes.FORMAT_DATE.format(liquidacion.getFechaLiquidacion()),Constantes.TRUE_VALUE);
//		if(vsLiquidacion!=null){
//			VSLiquidacion vsLiquidacionVentas=ServiceLocator.getVentaSeguroManager().buscarLiquidacionVentas(vsLiquidacion.getId());
//			vsLiquidacion.setMontoIngresado(vsLiquidacionVentas.getMontoVentasPaxNromal());
//			vsLiquidacion.setEstadoLiquidacion(Constantes.FALSE_VALUE);
//			UtilData.auditarRegistro(vsLiquidacion, true, getUsuario(), Executions.getCurrent());
//			ServiceLocator.getVentaSeguroManager().cerrarLiquidacion(vsLiquidacion);
//		}

		/*Solicita confirmación del usuario para la impresion de la liquidacion*/
		Messagebox.show(Messages.getString("WndCierreCaja.Question.Impresion"),DlgMessage.NOMBREAPLICACION,DlgMessage.BTN_YESNO,Messagebox.QUESTION,DlgMessage.BTN_DEFAULT_NO,new EventListener<Event>() {
			@Override
			public void onEvent(Event event)throws Exception {
				if(event.getName().equals("onYes"))
					openWindowPrintLiquidacion(liquidacion);
//					imprimirLiquidacion(liquidacion);
			}
		});
		buscarLiquidacion();
		Executions.getCurrent().getSession().setAttribute(Constantes.ATRIBUTO_FECHA_LIQUIDACION, null);
		window.onClose();
	}


	/**
	 * @param liquidacion
	 * @throws Exception
	 */
	public void imprimirLiquidacion(Liquidacion liquidacion) throws Exception{	
		//Busca las liquidaciones de CARGA
		Liquidacion liquidacionCarga = UtilData.buscarLiquidacionCarga(liquidacion);
		liquidacion.setLiquidacionCarga(liquidacionCarga);
		String nameFile = CreateDocument.creaRptLiquidacionByEspecieValorada(liquidacion, true);
		File file = new File(Constantes.DIRECTORY_LIQUIDACION + Constantes.CLAVE_PAHT +nameFile);
		Util.descargarArchivo(file);
		
//		File file=CreateDocument.creaLiquidacion(liquidacion, getUsuario(), true);
//		if(getUsuarioHardware().getPrintApplet().intValue()==Constantes.TRUE_VALUE){
//			String urlFile = Constantes.URL_FORMATOS_LIQUIDACION+Constantes.CLAVE_PAHT+liquidacion.getId()+".txt";
//			Window win = (Window)Executions.createComponents("imprimir.zul", null, null);
//			win.setAttribute("formato", WndImprimir.FORMAT_LIQUIDACION_TURNO);
//			win.setAttribute("msg", "Imprimiendo la Liquidación de Turno... ");
//			win.setAttribute("urlDocumento", urlFile);
//			win.doPopup();
//		}else{
			//Descarga el archivo para la impresion
//			Util.descargarArchivo(file);
//		}
	}

	private void mostrarLiquidacionTuentrada(List<LiquidacionTuentrada> lstLiquidacion, Usuario usuario, String fechaLiquidacion){
		Window win = (Window)Executions.createComponents("/liquidacionTuentrada.zul", null, null);
		win.setAttribute("lstLiquidacion", lstLiquidacion);
		win.setAttribute("usuario", usuario);
//		win.setAttribute("usuario", getUsuario().toString());
//		win.setAttribute("login", usuario.getLogin());
		win.setAttribute("fechaLiquidacion", fechaLiquidacion);
		win.doModal();
	}
}
