package pe.itsb.vyrbus.view.ui;


import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zul.Button;
//import org.zkoss.zul.Caption;
import org.zkoss.zul.Column;
import org.zkoss.zul.Columns;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Comboitem;
import org.zkoss.zul.Div;
import org.zkoss.zul.Grid;
import org.zkoss.zul.Groupbox;
import org.zkoss.zul.Hbox;
import org.zkoss.zul.Label;
//import org.zkoss.zul.Listbox;
//import org.zkoss.zul.Listcell;
//import org.zkoss.zul.Listhead;
//import org.zkoss.zul.Listheader;
//import org.zkoss.zul.Listitem;
import org.zkoss.zul.Longbox;
import org.zkoss.zul.Row;
import org.zkoss.zul.Rows;
import org.zkoss.zul.Separator;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Toolbar;
import org.zkoss.zul.Window;

import pe.itsb.vyrbus.model.bean.Agencia;
import pe.itsb.vyrbus.model.bean.ControlEspecieValorada;
import pe.itsb.vyrbus.model.bean.ControlEspecieValoradaID;
import pe.itsb.vyrbus.model.bean.EspecieValorada;
import pe.itsb.vyrbus.model.bean.TipoComprobante;
import pe.itsb.vyrbus.model.bean.Usuario;
import pe.itsb.vyrbus.model.bean.UsuarioHardware;
import pe.itsb.vyrbus.service.exceptions.CorrelativoException;
import pe.itsb.vyrbus.service.exceptions.NumeroSerieNoRegistradaException;
import pe.itsb.vyrbus.service.exceptions.NumeroSerieNullException;
import pe.itsb.vyrbus.service.exceptions.RangoEspecieValoradaRegistradaException;
import pe.itsb.vyrbus.service.exceptions.TipoComprobanteNullException;
import pe.itsb.vyrbus.service.exceptions.UsuarioHardwareNullException;
import pe.itsb.vyrbus.service.locator.ServiceLocator;
import pe.itsb.vyrbus.service.util.Constantes;
import pe.itsb.vyrbus.service.util.Messages;
import pe.itsb.vyrbus.service.util.Util;
import pe.itsb.vyrbus.service.util.UtilData;
/**
 *
 * @author JABANTO
 *
 */
public class WndControlEspecieValorada_LoadInicio extends Window {
	private static final long serialVersionUID = -8646012251696890029L;

//	private Window oThisWindow = this;

	private Textbox textboxID = new Textbox();
	private Combobox cmbTipoComprobante = new Combobox();
	private Combobox cmbUsuarioHardware = new Combobox();
	private Combobox cmbSerie = new Combobox();
//	private Textbox txtSerie = new Textbox();
	private Longbox lngbxCorrelativoInicio = new Longbox();
	private Longbox lngbxCorrelativoFin = new Longbox();
	private Longbox lngbxCorrelativoActual = new Longbox();
	private Button btnActualizar = new Button();
//	private Listbox lbxCompAut = new Listbox();
//	private Groupbox grxCompAut=new Groupbox();

	private Usuario usuario = new Usuario();
	private Agencia agencia=new Agencia();

	private ControlEspecieValorada controlEspecieValorada=null;

	private Integer action=Constantes.ACTION_NEW;

	/**
	 * @param usuarioHardware the usuarioHardware to set
	 */
	public void setUsuarioHardware(UsuarioHardware usuarioHardware) {
		Util.seleccionarValorItemCombo(UsuarioHardware.class, cmbUsuarioHardware, usuarioHardware.getId());
	}

	public void setTipoComprobante(TipoComprobante tipoComprobante){
		Util.seleccionarValorItemCombo(TipoComprobante.class, cmbTipoComprobante, tipoComprobante.getId());
	}

	public void setControlEspecieValorada(ControlEspecieValorada controlEspecieValorada) throws Exception {
		cmbUsuarioHardware.setDisabled(true);
		if (!(controlEspecieValorada ==null)){
			Util.seleccionarValorItemCombo(TipoComprobante.class, cmbTipoComprobante, controlEspecieValorada.getTipoComprobante().getId());
			Util.seleccionarValorItemCombo(UsuarioHardware.class, cmbUsuarioHardware, controlEspecieValorada.getUsuarioHardware().getId());
			Util.seleccionarValorItemCombo(EspecieValorada.class, cmbSerie, cmbSerie.getText());
			cmbSerie.setText(controlEspecieValorada.getSerie());
			lngbxCorrelativoInicio.setValue(controlEspecieValorada.getCorrelativoInicio());
			lngbxCorrelativoFin.setValue(controlEspecieValorada.getCorrelativoFin());
			lngbxCorrelativoActual.setValue(controlEspecieValorada.getCorrelativoActual());

			btnActualizar.setLabel("Actualizar");
			action=Constantes.ACTION_MODIFY;

			cmbTipoComprobante.setDisabled(true);
		}
		this.controlEspecieValorada = controlEspecieValorada;
		DlgMessage.information(Messages.getString("WndControlEspecieValorada.Information.ControlEspecieValoradaVericar"));
	}


	public WndControlEspecieValorada_LoadInicio(Agencia agencia) throws Exception{
		super();
		this.agencia=agencia;
		initComponents();
		onCreate();
	}

	public void onCreate() throws Exception{
		UtilData.cargarDataCombo(cmbTipoComprobante, TipoComprobante.class, false);
		UtilData.cargarUsuarioHasrdware(cmbUsuarioHardware, false, agencia);
		Util.seleccionarValorItemCombo(TipoComprobante.class, cmbTipoComprobante, Constantes.ID_TIPCOM_BOLETO_VIAJE);
		cmbTipoComprobante.setDisabled(true);
		cmbUsuarioHardware.setDisabled(true);
		cargarSeriesPermitidas();


	}


	@SuppressWarnings("deprecation")
	public void initComponents(){
//		lngbxCorrelativoFin.setReadonly(true);
//		lngbxCorrelativoInicio.setReadonly(true);
//		cmbSerie.setReadonly(true);
		final Window oThisWindow = this;
		oThisWindow.setBorder(true);
		Button btnCancelar = new Button();

		Groupbox groupbox = new Groupbox();
		Grid grid = new Grid();
		Rows rows = new Rows();

		Label label = new Label();

		Row row1 = new Row();
		label.setValue("Tipo Comprobante");
		label.setSclass("label-size11-bold");
		cmbTipoComprobante.setReadonly(true);
		row1.appendChild(label);
		row1.appendChild(cmbTipoComprobante);

		label =  new Label();
		label.setValue("Usuario Hardware");
		label.setSclass("label-size11-bold");
		cmbUsuarioHardware.setReadonly(true);
		row1.appendChild(label);
		row1.appendChild(cmbUsuarioHardware);

		Row row2 = new Row();
		label =  new Label();
		label.setValue("Serie");
		label.setSclass("label-size11-bold");
		cmbSerie.setWidth("98px");
		cmbSerie.setStyle("font-size: 11px !important");
		cmbSerie.setButtonVisible(false);
		row2.appendChild(label);
		row2.appendChild(cmbSerie);

		label =  new Label();
		label.setValue("Correlativo Inicial");
		label.setSclass("label-size11-bold");
		row2.appendChild(label);
		row2.appendChild(lngbxCorrelativoInicio);

		Row row3 = new Row();
		label =  new Label();
		label.setValue("Correlativo Final");
		label.setSclass("label-size11-bold");
		row3.appendChild(label);
		row3.appendChild(lngbxCorrelativoFin);

		label =  new Label();
		label.setValue("Correlativo Actual");
		label.setSclass("label-size11-bold");
		row3.appendChild(label);
		row3.appendChild(lngbxCorrelativoActual);

		textboxID.setVisible(false);

		Row row4 = new Row();
		row4.setSpans("1,3");
//		Hbox hbox=new Hbox();
//		Toolbar toolbar=new Toolbar();
//		toolbar.setWidth("350px");
////		textboxID.setVisible(false);
//		btnCancelar.setImage("resources/mp_cerrar.png");
//		btnCancelar.setHeight("32px");
//		btnCancelar.setLabel("Cancelar");
		row4.appendChild(textboxID);
//		row4.appendChild(btnCancelar);
//
//		btnActualizar.setImage("resources/mp_refrescarEnabled.png");
//		btnActualizar.setHeight("32px");
//		btnActualizar.setLabel("Guardar");
//		toolbar.appendChild(btnCancelar);
//		toolbar.appendChild(btnActualizar);
//		hbox.appendChild(toolbar);
//		row4.appendChild(hbox);
		row4.appendChild(new Separator());


		Columns columns = new Columns();
		Column column1 = new Column();column1.setWidth("108px");
		Column column2 = new Column();column2.setWidth("150px");
		Column column3 = new Column();column3.setWidth("108px");

		columns.appendChild(column1);
		columns.appendChild(column2);
		columns.appendChild(column3);

		rows.appendChild(row1);
		rows.appendChild(row2);
		rows.appendChild(row3);
		rows.appendChild(row4);

		grid.appendChild(columns);
		grid.appendChild(rows);
		groupbox.appendChild(grid);

//		Row row4 = new Row();
		Div div=new Div();
		Separator separator=new Separator();
		separator.setWidth("145px");
		Hbox hbox=new Hbox();
		hbox.appendChild(separator);
		Toolbar toolbar=new Toolbar();
		toolbar.setWidth("550px");
//		textboxID.setVisible(false);
		btnCancelar.setImage("resources/mp_cerrar.png");
		btnCancelar.setHeight("32px");
		btnCancelar.setLabel("Cancelar");
		btnCancelar.setMold("trendy");
		hbox.appendChild(btnCancelar);

		separator=new Separator();
		separator.setWidth("70px");
		hbox.appendChild(separator);

		btnActualizar.setImage("resources/mp_refrescarEnabled.png");
		btnActualizar.setHeight("32px");
		btnActualizar.setLabel("Guardar");
		btnActualizar.setMold("trendy");
		btnActualizar.setAutodisable("self");
		hbox.appendChild(btnActualizar);

		div.appendChild(hbox);
		toolbar.appendChild(div);
		oThisWindow.appendChild(groupbox);
		oThisWindow.appendChild(toolbar);

		oThisWindow.setTitle(Messages.getString("WndControlEspecieValorada.information.title"));
		oThisWindow.setMaximizable(false);
		oThisWindow.setMinimizable(false);
//		oThisWindow.setSizable(true);
		oThisWindow.setClosable(true);
//		oThisWindow.setStyle("padding: 5px");
		oThisWindow.setWidth("530px");
//		oThisWindow.setVisible(true);

		cmbSerie.addEventListener(Events.ON_OK, new EventListener<Event>() {
			@Override
			public void onEvent(Event e){
				lngbxCorrelativoInicio.setFocus(true);
			}
		});

		lngbxCorrelativoInicio.addEventListener(Events.ON_OK, new EventListener<Event>() {
			@Override
			public void onEvent(Event e){
				lngbxCorrelativoFin.setFocus(true);
			}
		});

		lngbxCorrelativoFin.addEventListener(Events.ON_OK, new EventListener<Event>() {
			@Override
			public void onEvent(Event e){
				lngbxCorrelativoActual.setFocus(true);
			}
		});

		lngbxCorrelativoActual.addEventListener(Events.ON_OK, new EventListener<Event>() {
			@Override
			public void onEvent(Event e){
				btnActualizar.setFocus(true);
			}
		});

		//Button Cancelar
		btnCancelar.addEventListener(Events.ON_CLICK, new EventListener<Event>() {
			@Override
			public void onEvent(Event event) throws Exception {
				oThisWindow.onClose();
			}
		});

		//Button Guardar/Actualizar
		btnActualizar.addEventListener(Events.ON_CLICK, new  EventListener<Event>() {
			@Override
			public void onEvent(Event event) throws Exception {
				String msn="";
				try{
					if (!(cmbTipoComprobante.getSelectedItem().getValue() instanceof TipoComprobante))
						throw new TipoComprobanteNullException();
					else if (!(cmbUsuarioHardware.getSelectedItem().getValue() instanceof UsuarioHardware))
						throw new UsuarioHardwareNullException();
					else if (cmbSerie.getText().trim() ==null || cmbSerie.getText().trim().equals(0))
						throw new NumeroSerieNullException();
//					else if (!(cmbSerie.getSelectedItem().getValue() instanceof EspecieValorada))
//						throw new NumeroSerieNullException();
					else if (lngbxCorrelativoInicio.getValue()==null || lngbxCorrelativoInicio.getValue().equals(0))
						throw new CorrelativoException(CorrelativoException.INICIAL_NULL);
					else if (lngbxCorrelativoFin.getValue()==null || lngbxCorrelativoFin.getValue().equals(0))
						throw new CorrelativoException(CorrelativoException.FINAL_NULL);
					else if (lngbxCorrelativoActual.getValue()==null || lngbxCorrelativoActual.getValue().equals(0))
						throw new CorrelativoException(CorrelativoException.ACTUAL_NULL);
					else if (lngbxCorrelativoInicio.intValue() > lngbxCorrelativoFin.intValue())
						throw new CorrelativoException(CorrelativoException.FINAL_MENOR_INICIAL);
					else if (lngbxCorrelativoActual.intValue() > lngbxCorrelativoFin.intValue())
						throw new CorrelativoException(CorrelativoException.ACTUAL_MAYOR_FINAL);
					else if (lngbxCorrelativoActual.intValue() < lngbxCorrelativoInicio.intValue())
						throw new CorrelativoException(CorrelativoException.ACTUAL_MENOR_INICIAL);

					/*Valida que la especie valorada ingresada exista en la tabla "EspecieValora"*/
					boolean numeroSerieRegistrada=false;
					Comboitem listitem=null;
					for(int i=0; i<cmbSerie.getItems().size(); i++){
						listitem= cmbSerie.getItemAtIndex(i);
						if(((EspecieValorada)listitem.getValue()).getSerie().toString().equals(cmbSerie.getText().trim())){
							numeroSerieRegistrada=true;
							break;
						}
//						Integer serie=((EspecieValorada)listitem.getValue()).getSerie();
//						if(serie.equals(new Integer(txtSerie.getValue().toString().trim()))){
//							numeroSerieRegistrada=true;
//							break;
//						}
					}
					if (!(numeroSerieRegistrada))
						throw new NumeroSerieNoRegistradaException();

					EspecieValorada especieValorada = cmbSerie.getSelectedItem().getValue();

					if( (lngbxCorrelativoInicio.longValue() < especieValorada.getCorrelativoInicial().longValue()) || (lngbxCorrelativoFin.longValue() > especieValorada.getCorrelativoFinal().longValue()))
						throw new CorrelativoException(CorrelativoException.FUERA_RANGO);

					/*	Valida que el numero rango de correlativo ingresado no haga intersecci�n con alg�n otro rango ingresado por otro usuario	*/
					List<ControlEspecieValorada> lstResul=ServiceLocator.getControlEspecieValoradaManager()
							.validaEVOtrasCajas(((UsuarioHardware)cmbUsuarioHardware.getSelectedItem().getValue()).getId(), cmbSerie.getText().trim(),
							lngbxCorrelativoInicio.getValue().toString(), lngbxCorrelativoFin.getValue().toString());
					for(ControlEspecieValorada controlEspecieValorada: lstResul){
						if(!(controlEspecieValorada.getUsuarioHardware().getId().intValue()==((UsuarioHardware)cmbUsuarioHardware.getSelectedItem().getValue()).getId().intValue())){
							if(msn.length()==0)
								msn=controlEspecieValorada.getUsuarioHardware().getDescripcion();
							else
								msn+="; "+controlEspecieValorada.getUsuarioHardware().getDescripcion();
						}
					}
					if(msn.length()>0)
						throw new RangoEspecieValoradaRegistradaException();


					if (action==Constantes.ACTION_NEW)
						controlEspecieValorada= new ControlEspecieValorada();

					TipoComprobante tipoComprobante = new TipoComprobante();
					tipoComprobante.setId(((TipoComprobante) cmbTipoComprobante.getSelectedItem().getValue()).getId());
					tipoComprobante.setDenominacion(((TipoComprobante) cmbTipoComprobante.getSelectedItem().getValue()).getDenominacion());
					UsuarioHardware usuarioHardware = new UsuarioHardware();
					usuarioHardware.setId(((UsuarioHardware)cmbUsuarioHardware.getSelectedItem().getValue()).getId());
					usuarioHardware.setDescripcion(((UsuarioHardware)cmbUsuarioHardware.getSelectedItem().getValue()).getDescripcion());

//					ControlEspecieValoradaID controlEspecieValoradaID = new ControlEspecieValoradaID();
//					controlEspecieValoradaID.setIdTipoComprobante(tipoComprobante.getId());
//					controlEspecieValoradaID.setIdUsuarioHardware(usuarioHardware.getId());

//					controlEspecieValorada.setControlEspecieValoradaID(controlEspecieValoradaID);
					controlEspecieValorada.setTipoComprobante(tipoComprobante);
					controlEspecieValorada.setUsuarioHardware(usuarioHardware);
					controlEspecieValorada.setSerie(cmbSerie.getText().toString());
					controlEspecieValorada.setCorrelativoInicio(lngbxCorrelativoInicio.getValue().longValue());
					controlEspecieValorada.setCorrelativoFin(lngbxCorrelativoFin.getValue().longValue());
					controlEspecieValorada.setCorrelativoActual(lngbxCorrelativoActual.getValue().longValue());
					controlEspecieValorada.setEstadoRegistro(Constantes.VALUE_ACTIVO);
					controlEspecieValorada.setFormato(((EspecieValorada)listitem.getValue()).getFormato());

					switch (action) {
						case Constantes.ACTION_NEW:
							UtilData.auditarRegistro(controlEspecieValorada, usuario, Executions.getCurrent());
							ServiceLocator.getControlEspecieValoradaManager().guardar(controlEspecieValorada);
							DlgMessage.information("Registro grabado");
							break;
						case Constantes.ACTION_MODIFY:
							UtilData.auditarRegistro(controlEspecieValorada, true, usuario, Executions.getCurrent());
							ServiceLocator.getControlEspecieValoradaManager().actualizar(controlEspecieValorada);
							DlgMessage.information("Registro Actualizado");
							break;
					}
					oThisWindow.onClose();

				}catch (RangoEspecieValoradaRegistradaException revyrex){
					DlgMessage.information(Messages.getString("WndControlEspecieValorada.Information.evUYaRegistradas")+msn);
				}catch (TipoComprobanteNullException tcnex){
					DlgMessage.information(Messages.getString("WndVentaReserva.information.noTipoComprobante"));
					cmbTipoComprobante.setFocus(true);
				}catch (UsuarioHardwareNullException uhnex){
					DlgMessage.information(Messages.getString("WndControlEspecieValorada.Information.UsuarioHarware"));
						cmbUsuarioHardware.setFocus(true);
				}catch (NumeroSerieNullException nsnex){
					DlgMessage.information(Messages.getString("WndEspecieValorada.information.NumeroSerie"));
					cmbSerie.setFocus(true);
				}catch(CorrelativoException c){
					if(c.getTipo().intValue()==CorrelativoException.INICIAL_NULL){
						DlgMessage.information(Messages.getString("WndEspecieValorada.information.CorrelativoInicial"));
						lngbxCorrelativoInicio.setFocus(true);
					}else if (c.getTipo().intValue()==CorrelativoException.FINAL_NULL){
						DlgMessage.information(Messages.getString("WndEspecieValorada.information.CorrelativoFinal"));
						lngbxCorrelativoFin.setFocus(true);
					}else if (c.getTipo().intValue()==CorrelativoException.ACTUAL_NULL){
						DlgMessage.information(Messages.getString("WndEspecieValorada.information.CorrelativoActual"));
						lngbxCorrelativoActual.setFocus(true);
					}else if (c.getTipo().intValue()==CorrelativoException.FUERA_RANGO){
						DlgMessage.information(Messages.getString("WndControlEspecieValorada.Information.correlativosFueraDeRango"));
					}else if (c.getTipo().intValue()==CorrelativoException.FINAL_MENOR_INICIAL){
						DlgMessage.information(Messages.getString("WndEspecieValorada.information.CorrelativoFinalMenorInicial"));
						lngbxCorrelativoFin.setFocus(true);
					}else if (c.getTipo().intValue()==CorrelativoException.ACTUAL_MENOR_INICIAL){
						DlgMessage.information(Messages.getString("WndEspecieValorada.information.CorrelativoActualMenorInicial"));
						lngbxCorrelativoInicio.setFocus(true);
					}else if(c.getTipo().intValue()==CorrelativoException.ACTUAL_MAYOR_FINAL){
						DlgMessage.information(Messages.getString("WndEspecieValorada.information.CorrelativoActualMayorFinal"));
						lngbxCorrelativoActual.setFocus(true);
					}else if (c.getTipo().intValue()==CorrelativoException.DUPLICADO){
						DlgMessage.information(Messages.getString("WndControlEspecieValorada.Information.DuplicidadTipoComprobanteUsuarioHarware"));
						cmbTipoComprobante.setFocus(true);
					}
//				}catch (Corre){
//
				}catch (NumeroSerieNoRegistradaException evnaex){
					DlgMessage.information(Messages.getString("WndControlEspecieValorada.Information.SerieNoValida"));
				}catch (Exception ex) {
					DlgMessage.error(this.getClass().getName()+" "+ex.getMessage());
					ex.printStackTrace();
				}
			}
		});
	}


	/**
	 * @return Objeto usuario.
	 */
	public Usuario getUsuario() {
		return usuario;
	}


	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}


	public ControlEspecieValorada getControlEspecieValorada() {
		return controlEspecieValorada;
	}

//	/**
//	 * Carga lista con los comprobantes y especies valoras registradas en el manteniemiento "Especies valoradas"
//	 * @param agencia : Class agencia.
//	 * @throws Exception
//	 */
//	public void cargaLisEspeciesValoradas(Agencia agencia) throws Exception{
//		Listhead listhead=new Listhead();
//
//		Listheader listheader=new Listheader();
//		listheader.setLabel("#");
//		listheader.setWidth("30px");
//		listhead.appendChild(listheader);
//		listheader=new Listheader();
//		listheader.setLabel("TIPO COMPROBANTE");
//		listhead.appendChild(listheader);
//		listheader=new Listheader();
//		listheader.setLabel("NUMERO SERIE");
//		listhead.appendChild(listheader);
//		listheader=new Listheader();
//		listheader.setLabel("CORRELATIVO INICIAL");
//		listhead.appendChild(listheader);
//		listheader=new Listheader();
//		listheader.setLabel("NUMERO FINAL");
//		listhead.appendChild(listheader);
//		lbxCompAut.appendChild(listhead);
//
//
//		TreeMap<String, Object> criterioBusqueda = new TreeMap<String, Object>();
//
//		criterioBusqueda.put("agencia", agencia);
//		criterioBusqueda.put("estadoRegistro", Constantes.VALUE_ACTIVO);
//		ArrayList<EspecieValorada> listEspecieValoradas = ServiceLocator.getEspecieValoradaManager().buscarPorX(criterioBusqueda, null);
//
//		Listitem item = null;
//		Listcell cell = null;
//		Integer i=0;
//
//		for (EspecieValorada especieValorada: listEspecieValoradas) {
//
//			/*	Los comprobante y las especies valordas, menos los recibos de caja.	*/
//			if (especieValorada.getTipoComprobante().getId().equals(Constantes.ID_TIPCOM_BOLETO_VIAJE)){
//				i++;
//				item = new Listitem();
//
//				cell= new Listcell(i.toString());
//				item.appendChild(cell);
//				cell= new Listcell(especieValorada.getTipoComprobante().getDenominacion());
//				item.appendChild(cell);
//				cell= new Listcell(especieValorada.getSerie().toString());
//				cell.setStyle("font-size:11px !important");
//				item.appendChild(cell);
//				cell= new Listcell(especieValorada.getCorrelativoInicial().toString());
//				cell.setStyle("font-size:11px !important");
//				item.appendChild(cell);
//				cell= new Listcell(especieValorada.getCorrelativoFinal().toString());
//				cell.setStyle("font-size:11px !important");
//				item.appendChild(cell);
//
//				item.setValue(especieValorada);
//				lbxCompAut.appendChild(item);
//
//			}
//
//		}
//		Caption caption=new Caption();
//		caption.setLabel("Comprobantes y especies valoradas permitidas.");
//		grxCompAut.appendChild(caption);
//		grxCompAut.appendChild(lbxCompAut);
//		grxCompAut.setOpen(true);
//		//lbxCompAut.setVisible(false);
//		this.appendChild(grxCompAut);
//	}


	private void cargarSeriesPermitidas(){
		try{
			TreeMap<String, Object> criterioBusqueda = new TreeMap<>();

			criterioBusqueda.put("agencia", agencia);
			criterioBusqueda.put("estadoRegistro", Constantes.VALUE_ACTIVO);
			ArrayList<EspecieValorada> listEspecieValoradas = ServiceLocator.getEspecieValoradaManager().buscarPorX(criterioBusqueda, null);
			for (EspecieValorada especieValorada: listEspecieValoradas) {
				/*	Solo las Especies Valoradas que sean Boletos de Viaje.	*/
				if(especieValorada.getTipoComprobante().getId().equals(Constantes.ID_TIPCOM_BOLETO_VIAJE)){
					Comboitem comboitem = new Comboitem(especieValorada.getSerie().toString());
					comboitem.setValue(especieValorada);
					cmbSerie.appendChild(comboitem);
				}
			}
		}catch(Exception ex){
			DlgMessage.error(this.getClass().getName()+" "+ex.getMessage());
			ex.printStackTrace();
		}
	}
}
