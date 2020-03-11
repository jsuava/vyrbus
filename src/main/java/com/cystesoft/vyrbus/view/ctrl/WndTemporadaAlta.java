package com.cystesoft.vyrbus.view.ctrl;

import java.io.Serializable;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zul.Calendar;
import org.zkoss.zul.Checkbox;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Comboitem;
import org.zkoss.zul.Groupbox;
import org.zkoss.zul.Image;
import org.zkoss.zul.Intbox;
import org.zkoss.zul.Label;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Toolbarbutton;
import org.zkoss.zul.Tree;
import org.zkoss.zul.Treecell;
import org.zkoss.zul.Treechildren;
import org.zkoss.zul.Treecol;
import org.zkoss.zul.Treeitem;
import org.zkoss.zul.Treerow;

import com.cystesoft.vyrbus.model.bean.MotivoTemporadaAlta;
import com.cystesoft.vyrbus.model.bean.TemporadaAlta;
import com.cystesoft.vyrbus.service.exceptions.AnioAperturaCalendarioErronioException;
import com.cystesoft.vyrbus.service.exceptions.AnioDiferenteException;
import com.cystesoft.vyrbus.service.exceptions.DenominacionNullException;
import com.cystesoft.vyrbus.service.exceptions.DiaExisteException;
import com.cystesoft.vyrbus.service.exceptions.FechaMenorCalendarioException;
import com.cystesoft.vyrbus.service.exceptions.FechaPasadaException;
import com.cystesoft.vyrbus.service.locator.ServiceLocator;
import com.cystesoft.vyrbus.service.util.Constantes;
import com.cystesoft.vyrbus.service.util.Messages;
import com.cystesoft.vyrbus.service.util.MyTime;
import com.cystesoft.vyrbus.service.util.Util;
import com.cystesoft.vyrbus.service.util.UtilData;
import com.cystesoft.vyrbus.view.ui.DlgMessage;
import com.cystesoft.vyrbus.view.ui.WndBase;

/**
 * 
 * @author JABANTO
 *
 */
public class WndTemporadaAlta extends WndBase implements Serializable {
	private static final long serialVersionUID = 1L;

	private Groupbox gbFinTempAlta;
	private Calendar calInicioTempAlta;
	private Calendar calFinTempalta;
	private Combobox cmbMotivoTemporadaAlta;
	private Tree trTempAlta;
	private Label lblDel;
	private Label lblAl;
	private Checkbox cbxRangoFechas;
	private Toolbarbutton tbbAperturaCalendario;
	private Toolbarbutton tbbBusqueda;
	private Toolbarbutton tbbModificar;
	private Toolbarbutton tbbCancelar;
	private Toolbarbutton tbbGurdar;
	private Toolbarbutton tbbAnularTodo;
	private Image imgBuscar;
	private Image imgAdd;
	private Intbox ibxAnio;
	private Combobox cmbMes;
	private Treecol tcDelete;
	
	private String deleteEnable="resources/mp_eliminarEnabled.png";
	private int action=0;
	private  Treechildren treechildren = new Treechildren();
	private Treechildren treechildrenHijo = new Treechildren();
	private Treeitem treeitem = new Treeitem();
	private ArrayList<TemporadaAlta>listDiasEliminados= null;
	
	/*
	 * (non-Javadoc)
	 * @see com.tepsa.sisvyr.view.ui.WndBase#initComponents()
	 */
	@Override
	public void initComponents() {
		gbFinTempAlta=(Groupbox)this.getFellow("gbFinTempAlta");
		calInicioTempAlta=(Calendar)this.getFellow("calInicioTempAlta");
		calFinTempalta=(Calendar)this.getFellow("calFinTempalta");
		cmbMotivoTemporadaAlta=(Combobox)this.getFellow("cmbMotivoTemporadaAlta");
		trTempAlta=(Tree)this.getFellow("trTempAlta");
		lblDel=(Label)this.getFellow("lblDel");
		lblAl=(Label)this.getFellow("lblAl");
		cbxRangoFechas=(Checkbox)this.getFellow("cbxRangoFechas");
		tbbAperturaCalendario=(Toolbarbutton)this.getFellow("tbbAperturaCalendario");
		tbbBusqueda=(Toolbarbutton)this.getFellow("tbbBusqueda");
		tbbModificar=(Toolbarbutton)this.getFellow("tbbModificar");
		tbbCancelar=(Toolbarbutton)this.getFellow("tbbCancelar");
		tbbGurdar=(Toolbarbutton)this.getFellow("tbbGurdar");
		tbbAnularTodo=(Toolbarbutton)this.getFellow("tbbAnularTodo");
		imgBuscar=(Image)this.getFellow("imgBuscar");
		imgAdd=(Image)this.getFellow("imgAdd");
		ibxAnio=(Intbox)this.getFellow("ibxAnio");
		cmbMes=(Combobox)this.getFellow("cmbMes");
		tcDelete=(Treecol)this.getFellow("tcDelete");
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.tepsa.sisvyr.view.ui.WndBase#onCreate()
	 */
	@Override
	public void onCreate() throws Exception {
		UtilData.cargarDataCombo(cmbMotivoTemporadaAlta, MotivoTemporadaAlta.class, false);
		cargarMeses();
		cmbMes.setSelectedIndex(0);
		cmbMotivoTemporadaAlta.setSelectedIndex(0);
		
		//CALENDAR INICIO TEMPORADA ALTA
		calInicioTempAlta.addEventListener(Events.ON_CHANGE,new EventListener<Event>() {
			@Override
			public void onEvent(Event event) throws Exception {
				if(imgBuscar.getId().equals(Constantes.LABEL_ESTADOSOL_INACTIVA_DESC)){ //indica que es que su estado es disabled
					if (!(ibxAnio.getText().trim().isEmpty())){
						if (!(Constantes.FORMAT_YEAR.format(calInicioTempAlta.getValue()).equals(ibxAnio.getText().trim())))
							onChangeAnio();
						selectDate();
					}else{
						limpiaControles(true);
						disabledControls(true);
						disabledImgAdd(true);
					}
				}
			}
		});

		//FIN TEMPORADA ALTA
		calFinTempalta.addEventListener(Events.ON_CHANGE,new EventListener<Event>() {
			@Override
			public void onEvent(Event event) throws Exception {
				// TODO Auto-generated method stub			
				selectDate();
			}
		});
	}
	
	/**
	 * Apertura nuevo calendario
	 * @throws Exception 
	 */
	public void onAperturaCalendario() throws Exception{
		Util.limpiarCombobox(cmbMotivoTemporadaAlta);
		UtilData.cargarDataCombo(cmbMotivoTemporadaAlta, MotivoTemporadaAlta.class, false);
		Util.limpiarTree(trTempAlta);
		limpiaControles(true);
		disabledImgBuscar(true);
		java.util.Calendar calendar=java.util.Calendar.getInstance();
		action=Constantes.ACTION_NEW;
		ibxAnio.setReadonly(false);
		ibxAnio.setFocus(true);
		disabledTbbAperturaCalendario(true, accesoNuevo());
		disabledTbbGuardar(false, accesoGrabar());
		disabledTbbBuscar(true, accesoConsultar());
		disabledTbbAnularTodo(true, accesoEliminar());
		ibxAnio.setText(Constantes.FORMAT_YEAR.format(calendar.getTime()));
		disabledTbbCancelar(false);
		tcDelete.setVisible(true);
		onChangeAnio();
		selectDate();
		gbFinTempAlta.setVisible(false);
		listDiasEliminados=null;
	}
	
	/**
	 * Habilita los parametros para realizar la busqueda
	 */
	public void onBuscar(){
		Util.limpiarTree(trTempAlta);
		ibxAnio.setReadonly(false);
		cmbMes.setDisabled(false);
		disabledTbbAperturaCalendario(true, accesoNuevo());
		disabledTbbBuscar(true, accesoConsultar());
		disabledTbbAnularTodo(true, accesoEliminar());
		disabledImgBuscar(false);
		disabledTbbCancelar(false);
		ibxAnio.setFocus(true);
		limpiaControles(true);
	}
	
	/**
	 * Modificar 
	 * @throws Exception
	 */
	public void onModificar()throws Exception{
		tcDelete.setVisible(true);
		ibxAnio.setReadonly(true);
		cmbMes.setDisabled(true);
		disabledTbbModificar(true, accesoModificar());
		disabledTbbCancelar(false);
		disabledTbbGuardar(false, accesoGrabar());
		disabledTbbAnularTodo(true, accesoEliminar());
		disabledImgBuscar(true);
		action=Constantes.ACTION_MODIFY;
	}
	
	/**
	 * Cancela la apertura
	 */
	public void onCancelar()throws Exception{
		disabledTbbAperturaCalendario(false, accesoNuevo());
		disabledTbbGuardar(true, accesoGrabar());
		disabledTbbBuscar(false, accesoConsultar());
		disabledTbbAnularTodo(true, accesoEliminar());
		disabledTbbModificar(true, accesoConsultar());
		ibxAnio.setReadonly(true);
		disabledTbbCancelar(true);
		disabledControls(true);
		disabledImgAdd(true);
		limpiaControles(true);
		Util.limpiarTree(trTempAlta);
		disabledImgBuscar(true);
		cmbMes.setDisabled(true);
		gbFinTempAlta.setVisible(false);
		listDiasEliminados=null;
	}
	
	/**
	 * Guarda calendario
	 * @throws Exception
	 */
	public void onGuardarCalendario() throws Exception{
		try {
			Messagebox.show(Messages.getString(action==Constantes.ACTION_NEW?"wndTempAlta.question.saved" : "wndTempAlta.question.update"), DlgMessage.NOMBREAPLICACION, DlgMessage.BTN_YESNO, Messagebox.QUESTION,DlgMessage.BTN_DEFAULT_NO, new EventListener<Event>() {
				@Override
				public void onEvent(Event e) throws Exception {
					if(e.getName().equals("onYes")){
						Date dateRp= new Date();//Para validar fechas repetidas, por un tema de los grupos.
						
						if(action==Constantes.ACTION_MODIFY && listDiasEliminados!=null){
							String mes=""; String dia="";String anio="";
//							if(cmbMes.getSelectedIndex()>0)
//								mes=cmbMes.getSelectedItem().getValue();

							for(TemporadaAlta temporadaAlta: listDiasEliminados){
								anio=Constantes.FORMAT_YEAR.format(temporadaAlta.getDiaTemporadaAlta());
								mes=Constantes.FORMAT_MONTH.format(temporadaAlta.getDiaTemporadaAlta());
								dia=Constantes.FORMAT_DAY.format(temporadaAlta.getDiaTemporadaAlta());
								ServiceLocator.getTemporadaAltaManager().anularTemporadaAlta(anio, mes,dia);
							}
						}
						
						for(Treeitem treeitem: trTempAlta.getItems()){
							TemporadaAlta ta= treeitem.getValue();
							TemporadaAlta temporadaAlta=null;
							
							if (!(dateRp.getTime()==ta.getDiaTemporadaAlta().getTime())){
								temporadaAlta=new TemporadaAlta();
								temporadaAlta.setId((((TemporadaAlta)treeitem.getValue()).getId()));
								temporadaAlta.setDiaTemporadaAlta(ta.getDiaTemporadaAlta());
								temporadaAlta.setMotivoTemporadaAlta((((TemporadaAlta)treeitem.getValue()).getMotivoTemporadaAlta()));
								temporadaAlta.setEstadoRegistro(Constantes.VALUE_ACTIVO);
								
								if(action==Constantes.ACTION_MODIFY){
									if(temporadaAlta.getId()==null){
										UtilData.auditarRegistro(temporadaAlta, getUsuario(), Executions.getCurrent());
										ServiceLocator.getTemporadaAltaManager().guardar(temporadaAlta);
									}
								}else{
									UtilData.auditarRegistro(temporadaAlta, getUsuario(), Executions.getCurrent());
									ServiceLocator.getTemporadaAltaManager().guardar(temporadaAlta);
								}
							}
							
							if(temporadaAlta!=null)
								dateRp=temporadaAlta.getDiaTemporadaAlta();								
						}	
						
						disabledControls(true);
						disabledImgBuscar(true);
						disabledImgAdd(true);
						disabledTbbAperturaCalendario(false, accesoNuevo());
						disabledTbbBuscar(false, accesoConsultar());
						disabledTbbCancelar(true);
						disabledTbbGuardar(true, accesoGrabar());
						disabledTbbAnularTodo(true, accesoEliminar());
						limpiaControles(false);
						ibxAnio.setReadonly(true);
						cmbMes.setDisabled(true);
						tcDelete.setVisible(false);
						listDiasEliminados=null;
					}
				}
			});	
		} catch (Exception e) {
			DlgMessage.error(this.getClass().getName()+" "+e.getMessage());
			e.printStackTrace();
		}
	}
	
	public void onAnularTodo(){
//		Messagebox.show(Messages.getString("wndTempAlta.question.anularTodo"), DlgMessage.NOMBREAPLICACION, DlgMessage.BTN_YESNO, Messagebox.QUESTION, new EventListener<Event>() {
//			@Override
//			public void onEvent(Event e) throws Exception {
//				if(e.getName().equals("onYes")){
//					String mes="";
//					if(cmbMes.getSelectedIndex()>0)
//						mes=cmbMes.getSelectedItem().getValue();
//					ServiceLocator.getTemporadaAltaManager().anularTemporadaAlta(ibxAnio.getText().trim(), mes, getUsuario().getLogin());
//					Util.limpiarTree(trTempAlta);
//					disabledTbbAnularTodo(true, accesoEliminar());
//					disabledTbbCancelar(true);
//					disabledTbbModificar(true, accesoModificar());
//					disabledImgBuscar(true);
//					disabledTbbBuscar(false, accesoConsultar());
//					disabledTbbAperturaCalendario(false, accesoNuevo());
//					ibxAnio.setReadonly(true);
//					cmbMes.setDisabled(true);
//					disabledControls(true);
//				}
//			}
//		});
	}
	
	
	/**
	 * Valida y carga el calendario en el año ingresado y con el primer mes del mismo.
	 * @throws Exception
	 */
	@SuppressWarnings("deprecation")
	public void onChangeAnio() throws Exception{
		try{
			if(imgBuscar.getId().equals(Constantes.LABEL_INACTIVO_DESCP)){
				if(ibxAnio.getText().trim().length()!=4)
					throw new AnioAperturaCalendarioErronioException();
				else if (ibxAnio.getValue()< 2013 )
					throw new AnioAperturaCalendarioErronioException();
				
				if (!(ibxAnio.getText().trim().isEmpty())){
					java.util.Calendar  calendar=java.util.Calendar.getInstance();
//					calendar.set(ibxAnio.getValue(), 0, 1);
					calendar.set(ibxAnio.getValue(),new Date().getMonth(),new Date().getDate());
					calInicioTempAlta.setValue(calendar.getTime());
					selectDate();
				}else{
					limpiaControles(true);
					disabledControls(true);
					disabledImgAdd(true);
				}
			}else{
				java.util.Calendar  calendar=java.util.Calendar.getInstance();
				calendar.set(ibxAnio.getValue(), 0, 1);
				calInicioTempAlta.setValue(calendar.getTime());
			}
			
		}catch (AnioAperturaCalendarioErronioException aperex){
			DlgMessage.information(Messages.getString("wndTempAlta.Information.anioAperturaErronio"));
			ibxAnio.setFocus(true);
			limpiaControles(true);
			disabledControls(true);
			disabledImgAdd(true);
		}
	}
	
	/**
	 * Agregar al calendario de fechas altas.
	 * @param fechaDel 	:Fecha del que inicia.
	 * @param fechaAl	:Fecha al que termina.
	 * @throws Exception
	 */
	@SuppressWarnings("deprecation")
	public void addTempAlta(String fechaDel, String fechaAl) throws Exception{
		String MSdia="";
		Date dateact=imgAdd.getId().equals("false")?Constantes.FORMAT_DATE.parse(new MyTime().dateServer()):null;
		Date datefc=imgAdd.getId().equals("false")?Constantes.FORMAT_DATE.parse(lblDel.getValue()):null;
		try{
			if(imgAdd.getId().equals("false")){
				if (!(cmbMotivoTemporadaAlta.getSelectedItem().getValue() instanceof MotivoTemporadaAlta))
					throw new DenominacionNullException();
				else if (!(Constantes.FORMAT_YEAR.format(calInicioTempAlta.getValue()).equals(ibxAnio.getText())))
					throw new AnioDiferenteException();
				else if (dateact.getTime() >= datefc.getTime() )
					throw new FechaPasadaException();
							
				Long cDias=(Constantes.FORMAT_DATE.parse(lblAl.getValue()).getTime()-Constantes.FORMAT_DATE.parse(lblDel.getValue()).getTime());
				cDias=(Long) (cDias/Constantes.MILISEGUNDOS_X_DIA)+1;	
				Long lnexDia=Constantes.FORMAT_DATE.parse(lblDel.getValue()).getTime()-Constantes.MILISEGUNDOS_X_DIA;
//				trTempAlta.setVisible(true);
				
				for (int i=0; i<cDias; i++){
					lnexDia+=+Constantes.MILISEGUNDOS_X_DIA;
					Date nextDia= new Date(lnexDia);
										
					/*Valida que el dia no este registrado*/
					if(action==Constantes.ACTION_NEW){
						String anio=Constantes.FORMAT_YEAR.format(nextDia);
						String mes=Constantes.FORMAT_MONTH.format(nextDia);
						String dia=Constantes.FORMAT_DAY.format(nextDia);
						List<TemporadaAlta>validaDia=ServiceLocator.getTemporadaAltaManager().buscarTemporadaAlta(anio, mes, dia);
						if(validaDia.size()>0){
							MSdia=Constantes.FORMAT_DATE.format(nextDia);
							throw new DiaExisteException();
						}
					}
						
					/*Setea temporada alta*/
					TemporadaAlta temporadaAlta= new TemporadaAlta();
					temporadaAlta.setMotivoTemporadaAlta(((MotivoTemporadaAlta)cmbMotivoTemporadaAlta.getSelectedItem().getValue()));
					temporadaAlta.setDiaTemporadaAlta(nextDia);
					
					/*Valida que el anio sea el mismo al anterior agregado a las lista de temporas altas*/
					String anio="";String mes="";
					for (Treeitem treeitems : trTempAlta.getItems()){
						TemporadaAlta tempAltItems= treeitems.getValue();
						if(temporadaAlta !=null){
							anio=Constantes.FORMAT_YEAR.format(tempAltItems.getDiaTemporadaAlta());
							mes=Constantes.FORMAT_MONTH.format(tempAltItems.getDiaTemporadaAlta());
							
							if(mes.equals(Constantes.FORMAT_MONTH.format(temporadaAlta.getDiaTemporadaAlta()))){
								if (!(anio.equals(Constantes.FORMAT_YEAR.format(temporadaAlta.getDiaTemporadaAlta()))))
									throw new AnioDiferenteException();
								else if (nextDia.getTime() <= tempAltItems.getDiaTemporadaAlta().getTime())
									throw new FechaMenorCalendarioException();	
							}
						}
					}
					
					/*Valida para la creacion de un nuevo gropo con el Mes*/
					Boolean newMonth=true;
					for (Treeitem treeitems : trTempAlta.getItems()){
						TemporadaAlta tempAltItems= treeitems.getValue();
						if(temporadaAlta !=null && tempAltItems.getDiaTemporadaAlta().getMonth()==temporadaAlta.getDiaTemporadaAlta().getMonth()){
							newMonth=false;
							break;
						}	
					}
					
					
					if(newMonth){ //Crea un grupo por mes
						addItem(temporadaAlta);//Crea grupo con el nombre del mes
						
						/*Agrega subItem con el dia*/
						treechildrenHijo= new Treechildren();
						addSubItem(treechildrenHijo,temporadaAlta);
						treeitem.appendChild(treechildrenHijo);
						treechildren.appendChild(treeitem);	
						trTempAlta.appendChild(treechildren);
					}else{//Agrega dia segun el Mes al que corresponde 
												
						for (Treeitem treeitem : trTempAlta.getItems()){
							TemporadaAlta tampAltaItems= treeitem.getValue();
							if(Constantes.FORMAT_MONTH.format(tampAltaItems.getDiaTemporadaAlta()).equals(Constantes.FORMAT_MONTH.format(temporadaAlta.getDiaTemporadaAlta()))){
								Treeitem subItem= addSubItem(treeitem.getTreechildren(),temporadaAlta);
								trTempAlta.setSelectedItem(subItem);
								break;
							}
						}
					}
				}
				
				cmbMotivoTemporadaAlta.setSelectedIndex(0);
				trTempAlta.setHeight("472px");
			}
		
		}catch (FechaPasadaException fpex){
			DlgMessage.information(Messages.getString("wndTempAlta.Information.fechaPasada"));
		}catch (DiaExisteException deex){
			DlgMessage.information(Messages.getString(" El dia "+MSdia+" ya esta registrado como Temporada Alta."));
		}catch (FechaMenorCalendarioException fncex){
			DlgMessage.information(Messages.getString("wndTempAlta.Information.fechaMenorCalendario"));
		}catch (AnioDiferenteException adex){
			DlgMessage.information(Messages.getString("wndTempAlta.Information.anioDiferente"));
		}catch (DenominacionNullException dn) {
			DlgMessage.information(Messages.getString("wndTempAlta.Information.nullDenominacion"),cmbMotivoTemporadaAlta);
		}
	}
	
	/**
	 * Agrega un nuevo Grupo.(Mes)
	 * @throws Exception
	 */
	private void addItem(TemporadaAlta temporadaAlta) throws Exception{	
		Treerow treerow = new Treerow();
		Treecell treecell = null;
//		Treecell treecell = new Treecell();
//		treerow = new Treerow();
		
		treeitem = new Treeitem();
		treecell = new Treecell(Util.getNombreMes(Constantes.FORMAT_DATE.format(temporadaAlta.getDiaTemporadaAlta())));
		treecell.setStyle("color:#0431B4; font-weight: bold;");
		treerow.appendChild(treecell);
		treerow.setDraggable("true");
		treeitem.setValue(temporadaAlta);
		treeitem.appendChild(treerow);
	}
	
	/**
	 * Agrega los subItmes al Tree
	 * @param treechildrenSub : Treechildren donde se agregara el subItem
	 * @throws ParseException 
	 */
	private Treeitem addSubItem(Treechildren treechildrenSub, TemporadaAlta temporadaAlta) throws ParseException{
		Treeitem treeSubitem = new Treeitem();
		Treerow treerow = new Treerow();
		
		/*Dia del Mes*/
		Treecell treecell = new Treecell(Constantes.FORMAT_DATE.format(temporadaAlta.getDiaTemporadaAlta()).substring(0,2));
		treecell.setTooltiptext(Util.getDiaSemana(Constantes.FORMAT_DATE.format(temporadaAlta.getDiaTemporadaAlta())));
		treerow.appendChild(treecell);
		/*Denominación*/
		treecell = new Treecell(temporadaAlta.getMotivoTemporadaAlta().getNombreMotivo());
		treecell.setStyle("font-size: 11px !important;");
		treerow.appendChild(treecell);
		
		//solo muestra la opcion a eliminar a las que son de fecha mayor o igual a la actual
		MyTime myTime= new MyTime();
		String fechaActual=myTime.dateServer();
		if(temporadaAlta.getDiaTemporadaAlta().getTime()>= Constantes.FORMAT_DATE.parse(fechaActual).getTime()){
			/*Button Eliminar*/
			treecell = new Treecell();
			Image image= new Image();image.setSrc(deleteEnable);image.setHeight("20px");
			image.setTooltiptext("Elimar Día");
			treecell.appendChild(image);
			treerow.appendChild(treecell);
			
			/*Elimainar item*/
			image.addEventListener(Events.ON_CLICK,new EventListener<Event>() {
				@Override
				public void onEvent(Event event) throws Exception {
					Treeitem treeitem=trTempAlta.getSelectedItem();
					
					/*Registra el dia que se esta eliminado para actualizar en la DB al guardar los cambios*/
					if(action==Constantes.ACTION_MODIFY){
						if(listDiasEliminados==null)
							 listDiasEliminados= new ArrayList<TemporadaAlta>();
						listDiasEliminados.add((TemporadaAlta)treeitem.getValue());
					}
					
					if(treeitem.getParent().getChildren().size()==1)
						trTempAlta.getSelectedItem().getParentItem().detach();
					else
						trTempAlta.getSelectedItem().detach();
				}
			});
		}
		
		treeSubitem.appendChild(treerow);
		treeSubitem.setValue(temporadaAlta);
		treechildrenSub.appendChild(treeSubitem);
		
		return treeSubitem;
	}
		
	@SuppressWarnings("deprecation")
	public void buscarTemporadaAlta() throws Exception{
			
		if(imgBuscar.getId().equals(Constantes.LABEL_ESTADOSOL_ACTIVA_DESC)){
			Util.limpiarTree(trTempAlta);
			tcDelete.setVisible(false);
			String anio=ibxAnio.getText().trim();
			String mes="";
			if (cmbMes.getSelectedIndex()>0)
				mes=cmbMes.getSelectedItem().getValue().toString();
			ArrayList<TemporadaAlta>lsresul=ServiceLocator.getTemporadaAltaManager().buscarTemporadaAlta(anio, mes, "");
			
			for(TemporadaAlta temporadaAlta: lsresul){
				/*Valida para la creacion de un nuevo grupo con el Mes*/
				Boolean newMonth=true;
				for (Treeitem treeitems : trTempAlta.getItems()){
					TemporadaAlta tempAltItems= treeitems.getValue();
					if(temporadaAlta !=null && tempAltItems.getDiaTemporadaAlta().getMonth()==temporadaAlta.getDiaTemporadaAlta().getMonth()){
						newMonth=false;
						break;	
					}	
				}
						
				if(newMonth){ //Crea un grupo con un nuevo mes
					addItem(temporadaAlta);//Crea grupo con el nombre del mes
					/*Agrega subItem con el dia*/
					treechildrenHijo= new Treechildren();
					addSubItem(treechildrenHijo,temporadaAlta);
					treeitem.appendChild(treechildrenHijo);
					treeitem.setOpen(false);
					treechildren.appendChild(treeitem);	
					trTempAlta.appendChild(treechildren);
						
				}else{//Agrega dia segun el Mes al que correspoende 
					for (Treeitem treeitems : trTempAlta.getItems()){
						TemporadaAlta tampAltaItems= treeitems.getValue();
						if(Constantes.FORMAT_MONTH.format(tampAltaItems.getDiaTemporadaAlta()).equals(Constantes.FORMAT_MONTH.format(temporadaAlta.getDiaTemporadaAlta()))){
							addSubItem(treeitems.getTreechildren(),temporadaAlta);
							break;
						}
					}
				}
				
				if(trTempAlta.getItems().size()>0){
					disabledTbbAnularTodo(false, accesoEliminar());
					disabledTbbModificar(false, accesoModificar());
				}else{
					disabledTbbAnularTodo(true, accesoEliminar());
					disabledTbbModificar(true, accesoModificar());
				}		
			}
//			trTempAlta.setVisible(true);
		}
	}
	
	
	/**
	 * Carga en el Combobox los meses del anio
	 * @throws Exception
	 */
	@SuppressWarnings("deprecation")
	private void cargarMeses() throws Exception{
		java.util.Calendar  calendar=java.util.Calendar.getInstance();
		UtilData.cargarGenericData(cmbMes, false);
		
		for(int i=0; i<12; i++){
			calendar.set(2013, i, 1);
			String idMes=String.valueOf(calendar.getTime().getMonth()+1);
			
			if(idMes.length()==1)
				idMes="0"+idMes;
			Comboitem oComboitem = new Comboitem();
			
			oComboitem.setLabel(Util.getNombreMes(Constantes.FORMAT_DATE.format(calendar.getTime())));
			oComboitem.setValue(idMes);
			
			cmbMes.appendChild(oComboitem);
		}
		cmbMes.setSelectedIndex(0);
	}
	
	/**
	 * Al seleccionar la fecha del Calendario
	 * @throws Exception
	 */
	@SuppressWarnings({ "deprecation"})
	private void selectDate() throws Exception{
		if (tbbAperturaCalendario.isDisabled()){
			if(!(ibxAnio.getText().trim().isEmpty()) && ibxAnio.getText().trim().length()==4 && ibxAnio.getValue()>= new Date().getYear()){
				disabledControls(false);
				
				//valida y se asegura que la fecha del calendario fin siempre NO sea sea menor al calendario de inicio.
				if(calInicioTempAlta.getValue().getTime()>calFinTempalta.getValue().getTime())
					calFinTempalta.setValue(calInicioTempAlta.getValue());
								
				if (cbxRangoFechas.isChecked()){ 
					lblDel.setValue(Constantes.FORMAT_DATE.format(calInicioTempAlta.getValue()));
					lblAl.setValue(Constantes.FORMAT_DATE.format(calFinTempalta.getValue()));
				}else{
					lblDel.setValue(Constantes.FORMAT_DATE.format(calInicioTempAlta.getValue()));
					lblAl.setValue(Constantes.FORMAT_DATE.format(calInicioTempAlta.getValue()));
				}
				cmbMotivoTemporadaAlta.setFocus(true);
			}else{
				disabledControls(true);
				DlgMessage.information(Messages.getString("wndTempAlta.Information.nullAnio"));
				ibxAnio.setFocus(true);
			}
		}
				
	}

	/**
	 * Event onCheck del control CheckBox
	 */
	public void onCheck_XRango(){
		if(!(cbxRangoFechas.isDisabled())){
			if(cbxRangoFechas.isChecked()){ 
				calFinTempalta.setValue(calInicioTempAlta.getValue());
				gbFinTempAlta.setVisible(true);
				lblAl.setValue(lblDel.getValue());				
			}else{
				gbFinTempAlta.setVisible(false);
			}
		}
	}
	
	/*ESTADOS DE LOS CONTROLES*/
	 private void limpiaControles(Boolean clearAnioMes){
		cbxRangoFechas.setChecked(false);
		lblDel.setValue("");
		lblAl.setValue("");
		cmbMotivoTemporadaAlta.setSelectedIndex(0);
		if(clearAnioMes){
			ibxAnio.setText("");
			cmbMes.setSelectedIndex(0);
		}
	}
	
	
	/**
	 * Activa o deshabilita controles.
	 * @param disabled
	 */
	private void disabledControls(Boolean disabled){
		cbxRangoFechas.setDisabled(disabled);
		cmbMotivoTemporadaAlta.setDisabled(disabled);
		disabledImgAdd(disabled);
	}
	
	/**
	 * Activa o desactiva el toolbarbutton apertura de calendario
	 * @param disabled : (true)desactiva el control, (false) activa el control.
	 */
	public  void disabledTbbAperturaCalendario(Boolean disabled, Boolean accesoNuevo){
		if(!(disabled) && accesoNuevo) {
			tbbAperturaCalendario.setImage("/resources/mp_calendarEnabled.png");
			tbbAperturaCalendario.setStyle("cursor:pointer");
		}else{
			tbbAperturaCalendario.setImage("/resources/mp_calendarDisabled.png");
			tbbAperturaCalendario.setStyle("cursor:default");
		}
		
		if(accesoNuevo)
			tbbAperturaCalendario.setDisabled(disabled);
		else
			tbbAperturaCalendario.setDisabled(true);
	}
	
	/**
	 * Activa o desactiva el toolbarbutton Buscar
	 * @param disabled : (true)desactiva el control, (false) activa el control.
	 */
	public  void disabledTbbBuscar(Boolean disabled, Boolean accesoBuscar){
		if(!(disabled) && accesoBuscar) {
			tbbBusqueda.setImage("/resources/mp_buscarEnabled.png");
			tbbBusqueda.setStyle("cursor:pointer");
		}else{
			tbbBusqueda.setImage("/resources/mp_buscarDisabled.png");
			tbbBusqueda.setStyle("cursor:default");
		}
		
		if(accesoBuscar)
			tbbBusqueda.setDisabled(disabled);
		else tbbBusqueda.setDisabled(true);
	}
	
	/**
	 * Activa o desactiva el toolbarbutton Modificar
	 * @param disabled : (true)desactiva el control, (false) activa el control.
	 */
	public  void disabledTbbModificar(Boolean disabled, Boolean accesoModificar){
		if(!(disabled) && accesoModificar) {
			tbbModificar.setImage("/resources/mp_editarEnabled.png");
			tbbModificar.setStyle("cursor:pointer");
		}else{
			tbbModificar.setImage("/resources/mp_editarDisabled.png");
			tbbModificar.setStyle("cursor:default");
		}
		
		if(accesoModificar)
			tbbModificar.setDisabled(disabled);
		else tbbModificar.setDisabled(true);
	}
	
	/**
	 * Activa o desactiva el toolbarbutton Cancelar Apertura
	 * @param disabled : (true)desactiva el control, (false) activa el control.
	 */
	public  void disabledTbbCancelar(Boolean disabled){
		if(!(disabled)) {
			tbbCancelar.setImage("/resources/mp_cancelarEnabled.png");
			tbbCancelar.setStyle("cursor:pointer");
		}else{
			tbbCancelar.setImage("/resources/mp_cancelarDisabled.png");
			tbbCancelar.setStyle("cursor:default");
		}
		
		tbbCancelar.setDisabled(disabled);
	}
	
	/**
	 * Activa o desactiva el toolbarbutton Guardar
	 * @param disabled : (true)desactiva el control, (false) activa el control.
	 */
	public  void disabledTbbGuardar(Boolean disabled, Boolean accesoGuardar){
		if(!(disabled) && accesoGuardar) {
			tbbGurdar.setImage("/resources/mp_guardarEnabled.png");
			tbbGurdar.setStyle("cursor:pointer");
		}else{
			tbbGurdar.setImage("/resources/mp_guardarDisabled.png");
			tbbGurdar.setStyle("cursor:default");
		}
		
		if(accesoGuardar)
			tbbGurdar.setDisabled(disabled);
		else tbbGurdar.setDisabled(true);
	}
	
	
	/**
	 * Activa o desactiva el toolbarbutton Guardar
	 * @param disabled : (true)desactiva el control, (false) activa el control.
	 */
	public  void disabledTbbAnularTodo(Boolean disabled, Boolean accesoAnular){
		if(!(disabled) && accesoAnular) {
			tbbAnularTodo.setImage("/resources/mp_anular.png");
			tbbAnularTodo.setStyle("cursor:pointer");
		}else{
			tbbAnularTodo.setImage("/resources/mp_anularDisabled.png");
			tbbAnularTodo.setStyle("cursor:default");
		}
		
		if(accesoAnular)
			tbbAnularTodo.setDisabled(disabled);
		else tbbAnularTodo.setDisabled(true);
	}
	

	/**
	 * Activa o desactiva la busqueda de temporada alta.
	 * @param disabled : (true)desactiva el control, (false) activa el control.
	 */
	public  void disabledImgBuscar(Boolean disabled){
		if(!(disabled)) {
			imgBuscar.setSrc("/resources/mp_buscarEnabled.png");
			imgBuscar.setStyle("cursor:pointer");
			imgBuscar.setId(Constantes.LABEL_ACTIVO_DESCP); //Indica que su estado es Enabled
		}else{
			imgBuscar.setSrc("/resources/mp_buscarDisabled.png");
			imgBuscar.setStyle("cursor:default");
			imgBuscar.setId(Constantes.LABEL_INACTIVO_DESCP); //Indica que su estado es Disabled
		}
	}
	
	/**
	 * Activa o desactiva agregar Temporada alta
	 * @param disabled : (true)desactiva el control, (false) activa el control.
	 */
	public  void disabledImgAdd(Boolean disabled){
		if(!(disabled)) {
			imgAdd.setSrc("/resources/mp_rightArrowEnabled.png");
			imgAdd.setStyle("cursor:pointer");
		}else{
			imgAdd.setSrc("/resources/mp_rightArrowDisabled.png");
			imgAdd.setStyle("cursor:default");
		}
		imgAdd.setId(String.valueOf(disabled));
	}
}

