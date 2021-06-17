package com.cystesoft.vyrbus.view.ctrl;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TreeMap;

import org.zkoss.zhtml.Messagebox;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zul.Button;
import org.zkoss.zul.Checkbox;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Comboitem;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.Doublebox;
import org.zkoss.zul.Grid;
import org.zkoss.zul.Groupbox;
import org.zkoss.zul.Label;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Radio;
import org.zkoss.zul.Tab;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Toolbarbutton;

import com.cystesoft.vyrbus.model.bean.CarteraCliente;
import com.cystesoft.vyrbus.model.bean.Cliente;
import com.cystesoft.vyrbus.model.bean.LineaCreditoCliente;
import com.cystesoft.vyrbus.model.bean.SolicitudCartera;
import com.cystesoft.vyrbus.model.bean.SolicitudClienteCredito;
import com.cystesoft.vyrbus.model.bean.Usuario;
import com.cystesoft.vyrbus.model.bean.UsuarioAprobador;
import com.cystesoft.vyrbus.service.exceptions.FuncionarioNullException;
import com.cystesoft.vyrbus.service.exceptions.LineaCreditoAprobadoNullException;
import com.cystesoft.vyrbus.service.exceptions.MotivoDesapruebaNullException;
import com.cystesoft.vyrbus.service.exceptions.ObservacionesNullException;
import com.cystesoft.vyrbus.service.exceptions.SolicitudClienteCreditoAnuladaException;
import com.cystesoft.vyrbus.service.exceptions.UsuarioAprobadorNullException;
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
public class WndAprobarCreditoN3 extends WndBase implements Serializable {
	private static final long serialVersionUID = 1L;
	
	/*Tabs*/
	private Tab tabPendientes;
	private Tab tabHistorial;
	Boolean selectPendientes=false; //Tru(selecciona el tabHistorial solicitudes), (false)  pendientes.
	
	private Button btnBuscar;
	private Listbox listPendientes;
	private Combobox cmbCliente;
	
	/*Solicitud*/
	private Label lblRuc;
	private Label lblRazonSocial;
	private Label lblBaseHistorica;
	private Label lblOrigen;
	private Label lblTipoCombranza;
	private Groupbox grbSolicitud;
	private Label lblFechaSolicitud;
	private Label lblFuncionario;
	private Label lblCreditoSolicitado;
	private Label lblSobregiro;
	private Label lblMontoSobregiro;
//	private Doublebox dbxMontoSobregiro;
	private Checkbox chbesCanje;
	private Checkbox chbEsAmpliacion;
	private Checkbox chbEsComisionable;
//	private Doublebox dbxDescuentoBaja;
//	private Doublebox dbxDescuentoAlta;
	private Textbox txtObservaciones;
	private Radio rbAprueba;
	private Doublebox dbxCreditoREconsiderar;
	private Radio rbDesaprueba;
	private Radio rbDevuelveFinanzas;
	private Textbox txtMotivo;
	private Button btnAceptar;
	private Grid grdAproBusqCliente;
	private Label lblAmpliacion;
	/*Historial */
	private Datebox dtxFechaInicio;
	private Datebox dtxFechaFinal;
	private Combobox cmbEstadoSolicitud;
	private Listbox listHistSolicitudes;
	private Combobox cmbClienteHist;
	private Button btnBuscarSolApro;
	/*Class*/
	UsuarioAprobador usuarioAprobador=new UsuarioAprobador();
	CarteraCliente carteraCliente=null;
	SolicitudClienteCredito solicitudClienteCredito=null;
	LineaCreditoCliente lineaCreditoCliente=null;
	
	
	/*Otras variables*/
	int action=0;
	
	/*
	 * (non-Javadoc)
	 * @see com.tepsa.sisvyr.view.ui.WndBase#initComponents()
	 */
	@Override
	public void initComponents() {
		/*Tabs*/
		tabPendientes=(Tab)this.getFellow("tabPendientes");
		tabHistorial=(Tab)this.getFellow("tabHistorial");
		
		btnBuscar=(Button)this.getFellow("btnBuscar");
		listPendientes=(Listbox)this.getFellow("listPendientes");
		grbSolicitud=(Groupbox)this.getFellow("grbSolicitud");
		cmbCliente=(Combobox)this.getFellow("cmbCliente");
		/*Solicitud*/
		lblFechaSolicitud=(Label)this.getFellow("lblFechaSolicitud");
		lblFuncionario=(Label)this.getFellow("lblFuncionario");
		lblCreditoSolicitado=(Label)this.getFellow("lblCreditoSolicitado");
		lblSobregiro=(Label)this.getFellow("lblSobregiro");
		lblMontoSobregiro=(Label)this.getFellow("lblMontoSobregiro");
//		dbxMontoSobregiro=(Doublebox)this.getFellow("dbxMontoSobregiro");
		chbesCanje=(Checkbox)this.getFellow("chbesCanje");
		chbEsAmpliacion=(Checkbox)this.getFellow("chbEsAmpliacion");
		chbEsComisionable=(Checkbox)this.getFellow("chbEsComisionable");
		txtObservaciones=(Textbox)this.getFellow("txtObservaciones");
		rbAprueba=(Radio)this.getFellow("rbAprueba");
		dbxCreditoREconsiderar=(Doublebox)this.getFellow("dbxCreditoREconsiderar");
		rbDesaprueba=(Radio)this.getFellow("rbDesaprueba");
		rbDevuelveFinanzas=(Radio)this.getFellow("rbDevuelveFinanzas");
		txtMotivo=(Textbox)this.getFellow("txtMotivo");
		lblRuc=(Label)this.getFellow("lblRuc");
		lblRazonSocial=(Label)this.getFellow("lblRazonSocial");
		lblBaseHistorica=(Label)this.getFellow("lblBaseHistorica");
		lblOrigen=(Label)this.getFellow("lblOrigen");
//		dbxDescuentoBaja=(Doublebox)this.getFellow("dbxDescuentoBaja");
//		dbxDescuentoAlta=(Doublebox)this.getFellow("dbxDescuentoAlta");
		lblTipoCombranza=(Label)this.getFellow("lblTipoCombranza");
		btnAceptar=(Button)this.getFellow("btnAceptar");
		grdAproBusqCliente=(Grid)this.getFellow("grdAproBusqCliente");
		lblAmpliacion=(Label)this.getFellow("lblAmpliacion");
		/*Historial */
		dtxFechaInicio=(Datebox)this.getFellow("dtxFechaInicio");
		dtxFechaFinal=(Datebox)this.getFellow("dtxFechaFinal");
		cmbEstadoSolicitud=(Combobox)this.getFellow("cmbEstadoSolicitud");
		listHistSolicitudes=(Listbox)this.getFellow("listHistSolicitudes");
		cmbClienteHist=(Combobox)this.getFellow("cmbClienteHist");
		btnBuscarSolApro=(Button)this.getFellow("btnBuscarSolApro");
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.tepsa.sisvyr.view.ui.WndBase#onCreate()
	 */
	@Override
	public void onCreate() throws Exception {
		try{
			usuarioAprobador=(UsuarioAprobador)getDesktop().getSession().getAttribute(Constantes.ATRIBUTO_USUARIO_APROBADOR);
			
			if(usuarioAprobador==null)
				throw new UsuarioAprobadorNullException();
			Date date=new Date();
			date=Constantes.FORMAT_DATE.parse(new MyTime().dateServer());
			
			cargarClientePendientes();
			UtilData.cargarClientesSolicitud(cmbClienteHist, true);
			
			visibleSolicitud(false);
			listPendientes();
			
//			lblSobregiro.setLocale(Locale.US);
			dbxCreditoREconsiderar.setLocale(Locale.US);
//			lblCreditoSolicitado.setLocale(Locale.US);
//			dbxMontoSobregiro.setLocale(Locale.US);
//			dbxDescuentoBaja.setLocale(Locale.US);
//			dbxDescuentoAlta.setLocale(Locale.US);
			
			dtxFechaInicio.setValue(date);
			dtxFechaFinal.setValue(date);
			UtilData.cargarEstadoSolicitudLC(cmbEstadoSolicitud, true);
			cmbEstadoSolicitud.setSelectedIndex(0);
			
			Util.disabledBtnBuscar(false, btnBuscar, accesoConsultar());
			Util.disabledBtnBuscar(false, btnBuscarSolApro, accesoConsultar());
			
		}catch (UsuarioAprobadorNullException uanex){
			DlgMessage.information(Messages.getString("wndApruebaCartera.infomation.UsuarioAprobadorNull"));
			closeTabWindow();
		}
	}
	
	
	/**
	 * Muestra detalle de la solicitud
	 * @param solicitudClienteCredito : class
	 */
	private void showDetalleSolicitud(SolicitudClienteCredito solicitudClienteCredito){
		//this.lineaCreditoCliente=lineaCreditoCliente;
		//.solicitudClienteCredito=solicitudClienteCredito; //lineaCreditoCliente.getSolicitudClienteCredito();
		
		Cliente cliente=solicitudClienteCredito.getSolicitudCartera().getCliente();
		Usuario funcionario=solicitudClienteCredito.getSolicitudCartera().getUsuario();
		
		lblRuc.setValue(cliente.getNumeroDocumento());
		lblRazonSocial.setValue(cliente.getRazonSocial());
		if(cliente.getOrigen().equals(Constantes.ORIGEN_LIMA))
			lblOrigen.setValue(Constantes.ORIGEN_LIMA_DESC);
		else 
			lblOrigen.setValue(Constantes.ORIGEN_PROVINCIAS_DESC);
		lblBaseHistorica.setValue(Util.toNumberFormat(solicitudClienteCredito.getSolicitudCartera().getBaseHistorica(),2));
		lblTipoCombranza.setValue(solicitudClienteCredito.getTipoCobranza().getDenominacion());
		
		lblFechaSolicitud.setValue(Constantes.FORMAT_DATE.format(solicitudClienteCredito.getFechaSolicitud()));
		lblFuncionario.setValue(funcionario.getApellidoPaterno()+" "+funcionario.getApellidoMaterno()+", "+funcionario.getNombre());
//		dbxDescuentoBaja.setValue(solicitudClienteCredito.getSolicitudCartera().getDescuentoBaja());
//		dbxDescuentoAlta.setValue(solicitudClienteCredito.getSolicitudCartera().getDescuentoAlta());
		chbesCanje.setChecked(solicitudClienteCredito.getEsCanje().equals(Constantes.SI)? true: false);
		chbEsAmpliacion.setChecked(solicitudClienteCredito.getEsAmpliacion().equals(Constantes.SI)? true: false);
		chbEsComisionable.setChecked(solicitudClienteCredito.getEsComisionable().equals(Constantes.SI)? true: false);
		
		if(selectPendientes==true){
			lblCreditoSolicitado.setValue(Util.toNumberFormat(solicitudClienteCredito.getLineaCreditoAprobada(),2));
			lblSobregiro.setValue(Util.toNumberFormat(solicitudClienteCredito.getSobregiro(),2));
		}else{
			if(solicitudClienteCredito.getEstadoSolicitud().equals(Constantes.ESTADOSOL_ACTIVA)){
				lblCreditoSolicitado.setValue(Util.toNumberFormat(solicitudClienteCredito.getLineaCreditoSolicitada(),2));
			}else{
				lblCreditoSolicitado.setValue(Util.toNumberFormat(solicitudClienteCredito.getLineaCreditoSolicitada(),2));
				dbxCreditoREconsiderar.setValue(solicitudClienteCredito.getLineaCreditoAprobada());
			}
			lblSobregiro.setValue(Util.toNumberFormat(solicitudClienteCredito.getSobregiro(),2));
			txtObservaciones.setText(solicitudClienteCredito.getObservaciones());
		}
		onChangeSobregiro();
		
		
		/*valida si es una extencion de canje publicitario*/
		if(chbesCanje.isChecked() && chbEsAmpliacion.isChecked())
			lblAmpliacion.setValue("EXTENSIÓN DE CANJE PUBLICITARIO");
		else
			lblAmpliacion.setValue("AMPLIACIÓN");
	}
	
	/**
	 * Lista pendienpes por aprobar LC.
	 */
	public void listPendientes(){
		String fechaInicio=null; String fechaFin=null; String estadoSolicitud=null; Long idCliente=null;
//		final UsuarioAprobador usuarioAprobador=null; 
		Boolean recu_Historia=false;
		if(cmbCliente.getSelectedItem().getValue() instanceof Cliente)
			idCliente=((Cliente)cmbCliente.getSelectedItem().getValue()).getId();
		
		List<LineaCreditoCliente>list=ServiceLocator.getLineaCreditoClienteManager().buscarSolicitudLineaCreditoN3(fechaInicio, fechaFin, estadoSolicitud, idCliente, null, recu_Historia);
					
		Listitem item=null;
		Listcell cell=null;
		int x=0;
		Util.limpiarListbox(listPendientes);
		
		for(LineaCreditoCliente lineaCreditoCliente: list){
			SolicitudCartera solicitudCartera=lineaCreditoCliente.getSolicitudClienteCredito().getSolicitudCartera();
			Usuario funcionario = solicitudCartera.getUsuario();
			Cliente cliente=solicitudCartera.getCliente();
			x++;
			item=new Listitem();
			cell=new Listcell(String.valueOf(x));
			item.appendChild(cell);
			cell=new Listcell(funcionario.getApellidoPaterno()+" "+funcionario.getApellidoMaterno()+", "+funcionario.getNombre());
			item.appendChild(cell);
			cell=new Listcell(cliente.getNumeroDocumento());
			cell.setStyle("font-size:11px !important");
			item.appendChild(cell);
			cell=new Listcell(cliente.getRazonSocial());
			item.appendChild(cell);
			cell=new Listcell(Constantes.FORMAT_DATE.format(lineaCreditoCliente.getSolicitudClienteCredito().getFechaSolicitud()));
			cell.setStyle("font-size:11px !important");
			item.appendChild(cell);
			cell=new Listcell(Constantes.FORMAT_LONG.format(lineaCreditoCliente.getSolicitudClienteCredito().getFechaAprobacion()));
			cell.setStyle("font-size:11px !important");
			item.appendChild(cell);
			cell=new Listcell(lineaCreditoCliente.getUsuarioInsercion());
			item.appendChild(cell);		
			final Toolbarbutton button= new Toolbarbutton("VER SOLICITUD ");
			button.setId(String.valueOf(x-1));
			button.setStyle("color:blue; font-size:9px !important");
			cell=new Listcell();
			cell.appendChild(button);
			item.appendChild(cell);
			
			button.addEventListener(Events.ON_CLICK,new EventListener<Event>() {
				@Override
				public void onEvent(Event event) throws Exception {
					selectPendientes=true;
					Listitem listitem=listPendientes.getItemAtIndex(Integer.valueOf(button.getId()));
					solicitudClienteCredito= new SolicitudClienteCredito();
					solicitudClienteCredito=ServiceLocator.getSolicitudClienteCreditoManager().buscarPorId(((LineaCreditoCliente)listitem.getValue()).getSolicitudClienteCredito().getId());
					
					try{
						if(!(solicitudClienteCredito.getEstadoSolicitud().equals(Constantes.ESTADOSOL_ACTIVA) && solicitudClienteCredito.getEstadoRegistro().equals(Constantes.VALUE_ACTIVO)))
							throw new SolicitudClienteCreditoAnuladaException();
						showDetalleSolicitud(solicitudClienteCredito);

						visibleSolicitud(true);
						visibleLisPendientes(false);
						
						tabHistorial.setDisabled(true);
						btnAceptar.setVisible(true);
						disabledControlsSolciitud(false);
						rbAprueba.setChecked(true);
//						Util.disabledBtnAceptar(false, btnAceptar, accesoGrabar());
						Util.disabledBtnAceptar(usuarioAprobador.getNivelAprobacion().intValue()!=Constantes.NIVEL_TRES, btnAceptar, accesoGrabar());
						action=Constantes.ACTION_NEW;
						
					}catch (SolicitudClienteCreditoAnuladaException sccanex){
						DlgMessage.information(Messages.getString("wndAprobarLineaCreditoN3.information.SolicitudClienteCreditoAnulada"));
					}
//					
				}
			});
			item.setValue(lineaCreditoCliente);
			listPendientes.appendChild(item);
		}
	}
	
	/**
	 * cargar los clientes que estan con una solicitud pendiente
	 */
	private void cargarClientePendientes(){
		String fechaInicio=null; String fechaFin=null; String estadoSolicitud=null; Long idCliente=null;
		UsuarioAprobador usuarioAprobador=null; Boolean recu_Historia=false;
	
		List<LineaCreditoCliente>list=ServiceLocator.getLineaCreditoClienteManager().buscarSolicitudLineaCreditoN3(fechaInicio, fechaFin, estadoSolicitud, idCliente, usuarioAprobador, recu_Historia);
		
		UtilData.cargarGenericData(cmbCliente, true);
		for (LineaCreditoCliente lineaCreditoCliente: list) {
			Cliente cliente=lineaCreditoCliente.getSolicitudClienteCredito().getSolicitudCartera().getCliente();
			Comboitem oComboitem = new Comboitem();
			oComboitem.setLabel(cliente.getRazonSocial());
			oComboitem.setValue(cliente);
			cmbCliente.appendChild(oComboitem);
		}
		cmbCliente.setSelectedIndex(0);
	}
	
	/**
	 * muestar o oculta datos de la silicitud
	 * @param visible
	 */
	private void visibleSolicitud(Boolean visible){
		grbSolicitud.setVisible(visible);
	}
	
	/**
	 * Calcula el monto del sobregiro, solo es referencial no se guarda en la DB
	 * solo se guarda el porcentaje.
	 */
	public void onChangeSobregiro(){
		Double montoSobregiro=.00;
		Double sobregiro=(lblSobregiro.getValue()!=null?Util.parseNumberFormat(lblSobregiro.getValue(),2):.00);
		if(sobregiro>0 && dbxCreditoREconsiderar.getValue()!=null)
			montoSobregiro=dbxCreditoREconsiderar.getValue()*(sobregiro/100);
		else if(sobregiro>0){
			Double creditosolicitado=Util.parseNumberFormat(lblCreditoSolicitado.getValue(),2);
			montoSobregiro=creditosolicitado*(sobregiro/100);
		}
			
		
		lblMontoSobregiro.setValue("% |"+Util.toNumberFormat(montoSobregiro,2));
//		if(dbxCreditoREconsiderar.getValue()!=null && lblSobregiro.getValue()!=null && lblSobregiro.getValue()>0){
//			dbxMontoSobregiro.setValue(dbxCreditoREconsiderar.getValue()*(lblSobregiro.getValue()/100));
//		}else{
//			dbxMontoSobregiro.setValue(0);
//		}
	}
	
	/**
	 * Muesttra o oculata listado de pendientes
	 * @param visible
	 */
	private void visibleLisPendientes(Boolean visible){
		listPendientes.setVisible(visible);
		btnBuscar.setVisible(visible);
		grdAproBusqCliente.setVisible(visible);
	}
	
	/**
	 * Activa o desactiva controles de la solicitud
	 * @param disabled
	 */
	private void disabledControlsSolciitud(Boolean disabled){
		rbAprueba.setDisabled(disabled);
		rbDesaprueba.setDisabled(disabled);
		rbDevuelveFinanzas.setDisabled(disabled);
		txtObservaciones.setReadonly(disabled);
		dbxCreditoREconsiderar.setReadonly(true);
	}
	
	/**
	 * cancela operación
	 */
	public void onClickCancelar(){
		clearControlSolcitud();
		visibleSolicitud(false);
		visibleLisPendientes(true);
		if (!(selectPendientes))
			tabHistorial.setSelected(true);
		tabHistorial.setDisabled(false);
	}
	
	/**
	 * Cuando pulsa el button Aceptar.
	 * @throws Exception
	 */
	public void onClickApectar() throws Exception{
		try{
			if(rbDevuelveFinanzas.isChecked() && dbxCreditoREconsiderar.getText().trim().isEmpty())
				throw new LineaCreditoAprobadoNullException();
			else if(rbDevuelveFinanzas.isChecked() && txtObservaciones.getText().trim().isEmpty())
				throw new ObservacionesNullException();
			else if (rbDesaprueba.isChecked() && txtMotivo.getText().trim().isEmpty())
				throw new MotivoDesapruebaNullException();
			if(!(solicitudClienteCredito.getEstadoSolicitud().equals(Constantes.ESTADOSOL_ACTIVA) && solicitudClienteCredito.getEstadoRegistro().equals(Constantes.VALUE_ACTIVO)))
				throw new SolicitudClienteCreditoAnuladaException();
			
			/*Buscar cartera cliente, por el idSolicitudCartera.*/
			TreeMap<String, Object> criteriosBusqueda = new TreeMap<String, Object>();
			criteriosBusqueda.put("cliente", solicitudClienteCredito.getSolicitudCartera().getCliente());
			criteriosBusqueda.put("estadoCartera", Constantes.ESTADOSOL_ACTIVA);
			List<CarteraCliente>list=ServiceLocator.getCarteraClienteManager().buscarPorX(criteriosBusqueda, null);
			if(list.size()>0){
				carteraCliente=new CarteraCliente();
				carteraCliente=list.get(0);
			}else
				throw new FuncionarioNullException();
						
			//Valiad si el usuario tiene el nivel requerido para la aprobacion
			if(usuarioAprobador.getNivelAprobacion().intValue()!=Constantes.NIVEL_TRES){
				DlgMessage.information(Messages.getString("wndAprobarCredito.information.noAccesoAprobar"));
				return;
			}	
				
			org.zkoss.zul.Messagebox.show(Messages.getString(rbAprueba.isChecked()?"wndAprobarLineaCredito.question.Aprobar": rbDesaprueba.isChecked()? "wndAprobarLineaCredito.question.Desaprobar": "wndAprobarCredito.question.devuelveSolicitudAFinanzas") , DlgMessage.NOMBREAPLICACION, DlgMessage.BTN_YESNO, org.zkoss.zul.Messagebox.QUESTION, new EventListener<Event>() {
				@Override
				public void onEvent(Event e) throws Exception {
					if(e.getName().equals("onYes")){
						Date fecha=Constantes.FORMAT_DATE_TIME_24H.parse(new MyTime().dateServer());					
						/*Inactiva el registro recuperado de la Solicitud de Credito*/
//						solicitudClienteCredito.setEstadoRegistro(Constantes.VALUE_INACTIVO);
//						UtilData.auditarRegistro(solicitudClienteCredito, true, usuarioAprobador.getUsuario(), Executions.getCurrent());
//						ServiceLocator.getSolicitudClienteCreditoManger().actualizar(solicitudClienteCredito);
						
						/*Recalcula el saldo para el credito del cliente*/
						Double sobregiro=.00; Double montoAprobado=.00;
						if (!(lblSobregiro.getValue()==null))
							sobregiro=Util.parseNumberFormat(lblSobregiro.getValue(),2);
						if(sobregiro>0){
							montoAprobado=Util.parseNumberFormat(lblCreditoSolicitado.getValue(),2)+(Util.parseNumberFormat(lblCreditoSolicitado.getValue(),2)*(sobregiro/100));
						}else 
							montoAprobado=Util.parseNumberFormat(lblCreditoSolicitado.getValue(),2);
						
						Double saldo=montoAprobado;

						if(chbEsAmpliacion.isChecked()){
							/*Obtiene el nuevo saldo, cuando es una ampliacion*/
							if(solicitudClienteCredito.getEsAmpliacion().equals(Constantes.SI))
								saldo = ServiceLocator.getLineaCreditoClienteManager().saldo(montoAprobado, carteraCliente.getCliente().getId());
							
							/*Inactiva Linea de credito actual*/
							TreeMap<String, Object> criteriosBusqueda = new TreeMap<String, Object>();
							criteriosBusqueda.put("carteraCliente", carteraCliente);
							criteriosBusqueda.put("estadoLineaCredito", Constantes.ESTADOSOL_ACTIVA);
							criteriosBusqueda.put("estadoRegistro", Constantes.VALUE_ACTIVO);
							List<LineaCreditoCliente>list=ServiceLocator.getLineaCreditoClienteManager().buscarPorX(criteriosBusqueda, null);
							for(LineaCreditoCliente  lineaCreditoCliente: list){
								lineaCreditoCliente.setEstadoLineaCredito(Constantes.ESTADOSOL_INACTIVA);
								lineaCreditoCliente.setEstadoRegistro(Constantes.VALUE_INACTIVO);
								lineaCreditoCliente.setFechaInactivacion(fecha);
								UtilData.auditarRegistro(lineaCreditoCliente, true, usuarioAprobador.getUsuario(), Executions.getCurrent());
								ServiceLocator.getLineaCreditoClienteManager().actualizar(lineaCreditoCliente);
							}
						}
						
						/*Inserta una nuevo registro con la solicitud de credito*/
						insertarSolcitudClienteCredito(solicitudClienteCredito);
						
						if(rbAprueba.isChecked())
							insertarLineaCreditoAprobada(solicitudClienteCredito, carteraCliente,saldo);
					
						onClickCancelar();						
						listPendientes();
					}
				}
			});
		}catch (SolicitudClienteCreditoAnuladaException sccanex){
			DlgMessage.information(Messages.getString("wndAprobarLineaCreditoN3.information.SolicitudClienteCreditoAnulada"));
		}catch (FuncionarioNullException fnex){
			DlgMessage.information(Messages.getString("wndAprobarLineaCreditoN3.information.ClienteSinFuncionario"));
		}catch (MotivoDesapruebaNullException mdenex){
			DlgMessage.information(Messages.getString("wndAprobarCredito.information.MotivoDesapruebaNull"));
		}catch (ObservacionesNullException obsnex){
			DlgMessage.information(Messages.getString("wndAprobarCredito.information.ObservacionesNull"));
			txtObservaciones.setFocus(true);
		}catch (LineaCreditoAprobadoNullException canex){
			DlgMessage.information(Messages.getString("wndAprobarCredito.information.CreditoReconsiderarNull"));
			dbxCreditoREconsiderar.setFocus(true);
		}catch (Exception e) {
			DlgMessage.error(this.getClass().getName()+" "+e.getMessage());
			e.printStackTrace();
		}
	}
	
	private void insertarLineaCreditoAprobada(SolicitudClienteCredito solicitudClienteCredito, CarteraCliente carteraCliente, Double saldo) throws Exception{
		/*Inserta el nuevo registro de la linea de credito, con la aprobacion o desaprobacion*/
		long lDefault=Constantes.FORMAT_DATE_TIME_24H.parse(Constantes.FECHA_DEFAULT).getTime();
		Date fDefault= new Date(lDefault);
		Date fecha=Constantes.FORMAT_DATE_TIME_24H.parse(new MyTime().dateServer());
		
//		/*Recalcula el saldo para el credito del cliente*/
//		Double sobregiro=.00; Double montoAprobado=.00;
//		if (!(lblSobregiro.getValue()==null))
//			sobregiro=Util.parseNumberFormat(lblSobregiro.getValue(),2);
//		if(sobregiro>0){
//			montoAprobado=Util.parseNumberFormat(lblCreditoSolicitado.getValue(),2)+(Util.parseNumberFormat(lblCreditoSolicitado.getValue(),2)/ sobregiro);
//		}else 
//			montoAprobado=Util.parseNumberFormat(lblCreditoSolicitado.getValue(),2);
//		
//		Double saldo=montoAprobado;
//		if(solicitudClienteCredito.getEsAmpliacion().equals(Constantes.SI))
//			saldo = ServiceLocator.getLineaCreditoClienteManager().saldo(montoAprobado, carteraCliente.getCliente().getId());			
		
		lineaCreditoCliente=new LineaCreditoCliente();
		lineaCreditoCliente.setCarteraCliente(carteraCliente);
		lineaCreditoCliente.setTipoCobranza(solicitudClienteCredito.getTipoCobranza());
		lineaCreditoCliente.setLineaCreditoSolicitada(Util.parseNumberFormat(lblCreditoSolicitado.getValue(),2));
		lineaCreditoCliente.setLineaCreditoAprobada(Util.parseNumberFormat(lblCreditoSolicitado.getValue(),2));
		lineaCreditoCliente.setFechaActivacion(fecha);
		lineaCreditoCliente.setEstadoLineaCredito(Constantes.ESTADOSOL_ACTIVA);
		lineaCreditoCliente.setSaldo(saldo);
		lineaCreditoCliente.setEstadoRegistro(Constantes.VALUE_ACTIVO);
		lineaCreditoCliente.setSolicitudClienteCredito(solicitudClienteCredito);
		lineaCreditoCliente.setSobregiro(Util.parseNumberFormat(lblSobregiro.getValue(),2));
		lineaCreditoCliente.setFechaCaducidad(fDefault);
		lineaCreditoCliente.setEsCanje(solicitudClienteCredito.getEsCanje());
		lineaCreditoCliente.setEsComisionable(solicitudClienteCredito.getEsComisionable());
		UtilData.auditarRegistro(lineaCreditoCliente,usuarioAprobador.getUsuario(), Executions.getCurrent());
		ServiceLocator.getLineaCreditoClienteManager().guardar(lineaCreditoCliente);
	}
	
	/**
	 * Inserta Solicitud Cliente Credito
	 * @param solicitudClientCredito : class
	 * @throws Exception
	 */
	private void insertarSolcitudClienteCredito(SolicitudClienteCredito solicitudClientCredito)throws Exception{
		Date fecha=Constantes.FORMAT_DATE_TIME_24H.parse(new MyTime().dateServer());
		
		usuarioAprobador=ServiceLocator.getUsuarioAprobadorManager().buscarPorId(usuarioAprobador.getId().longValue());
		
		solicitudClienteCredito = new SolicitudClienteCredito();
		solicitudClienteCredito .setUsuarioAprobador(usuarioAprobador);
		solicitudClienteCredito .setSolicitudCartera(solicitudClientCredito.getSolicitudCartera());
		solicitudClienteCredito .setTipoCobranza(solicitudClientCredito.getTipoCobranza());
		solicitudClienteCredito .setNumeroControl(solicitudClientCredito.getNumeroControl());
		
		if(rbDevuelveFinanzas.isChecked()){//El UGA Devuelve la solicitud
//			solicitudClienteCredito .setLineaCreditoSolicitada(dbxCreditoREconsiderar.getValue());
			solicitudClienteCredito .setLineaCreditoSolicitada(lblCreditoSolicitado.getValue()!=null? Util.parseNumberFormat(lblCreditoSolicitado.getValue(),2):.00);
			solicitudClienteCredito .setLineaCreditoAprobada(dbxCreditoREconsiderar.getValue());
			solicitudClienteCredito .setEstadoSolicitud(Constantes.ESTADOSOL_ACTIVA);
			solicitudClienteCredito .setNivelAprobacion(Constantes.NIVEL_TRES);
			solicitudClienteCredito .setFechaAprobacion(fecha);
			solicitudClienteCredito .setObservaciones(txtObservaciones.getText().trim().toUpperCase());
		}else if(rbDesaprueba.isChecked()) {
			solicitudClienteCredito .setLineaCreditoSolicitada(solicitudClientCredito.getLineaCreditoAprobada());
			solicitudClienteCredito .setLineaCreditoAprobada(solicitudClientCredito.getLineaCreditoAprobada());
			solicitudClienteCredito .setEstadoSolicitud(Constantes.ESTADOSOL_ANULADA);
			solicitudClienteCredito .setNivelAprobacion(Constantes.NIVEL_TRES);
			solicitudClienteCredito .setFechaAnulacion(fecha);
			solicitudClienteCredito .setObservaciones(txtObservaciones.getText().trim().toUpperCase());
		}else{
			solicitudClienteCredito .setLineaCreditoSolicitada(solicitudClientCredito.getLineaCreditoAprobada());
			solicitudClienteCredito .setLineaCreditoAprobada(lblCreditoSolicitado.getValue()!=null? Util.parseNumberFormat(lblCreditoSolicitado.getValue(),2):.00);
			solicitudClienteCredito .setEstadoSolicitud(Constantes.ESTADOSOL_ACTIVA);
			solicitudClienteCredito .setNivelAprobacion(Constantes.NIVEL_TRES);
			solicitudClienteCredito .setFechaAprobacion(fecha);
			solicitudClienteCredito .setObservaciones(txtObservaciones.getText().trim().toUpperCase());
		}
		
		solicitudClienteCredito .setSobregiro(lblSobregiro.getValue()!=null? Util.parseNumberFormat(lblSobregiro.getValue(),2):.00);
		solicitudClienteCredito .setFechaSolicitud(solicitudClientCredito.getFechaSolicitud());
		solicitudClienteCredito .setEsCanje(solicitudClientCredito.getEsCanje());
		solicitudClienteCredito .setEsComisionable(solicitudClientCredito.getEsComisionable());
		solicitudClienteCredito .setEsAmpliacion(solicitudClientCredito.getEsAmpliacion());
		solicitudClienteCredito .setEstadoRegistro(Constantes.VALUE_ACTIVO);
		UtilData.auditarRegistro(solicitudClienteCredito ,usuarioAprobador.getUsuario(), Executions.getCurrent());
		ServiceLocator.getSolicitudClienteCreditoManager().guardar(solicitudClienteCredito );
	}
	
	
	/**
	 * Limpia controles de la solcitud
	 */
	private void clearControlSolcitud(){
		lblRuc.setValue("");
		lblRazonSocial.setValue("");
		lblBaseHistorica.setValue("");
		lblOrigen.setValue("");
		lblTipoCombranza.setValue("");
		lblFechaSolicitud.setValue("");
		lblFuncionario.setValue("");
		lblCreditoSolicitado.setValue("");
		lblSobregiro.setValue("");
		lblMontoSobregiro.setValue("");
//		dbxMontoSobregiro.setText("");
//		dbxDescuentoBaja.setText("");
//		dbxDescuentoAlta.setText("");
		chbesCanje.setChecked(false);
		chbEsAmpliacion.setChecked(false);
		chbEsComisionable.setChecked(false);
		rbAprueba.setChecked(false);
		rbDesaprueba.setChecked(false);
		rbDevuelveFinanzas.setChecked(false);
		dbxCreditoREconsiderar.setText("");
		txtMotivo.setText("");
		txtObservaciones.setText("");
	}
	
	/**
	 * Historial de solicitudes
	 */
	public void listHistorial(){
		Long idCliente=null; String estadoSolicitud=""; Boolean recu_Historia=true;
		String fechaInicio=Constantes.FORMAT_DATE.format(dtxFechaInicio.getValue());
		String fechaFin=Constantes.FORMAT_DATE.format(dtxFechaFinal.getValue());
		if(cmbEstadoSolicitud.getSelectedIndex()>0)
			estadoSolicitud=cmbEstadoSolicitud.getSelectedItem().getValue();
		if(cmbClienteHist.getSelectedItem().getValue() instanceof Cliente)
			idCliente=((Cliente)cmbClienteHist.getSelectedItem().getValue()).getId();
		
		List<LineaCreditoCliente>list=ServiceLocator.getLineaCreditoClienteManager().buscarSolicitudLineaCreditoN3(fechaInicio, fechaFin, estadoSolicitud, idCliente, usuarioAprobador, recu_Historia);
				
		Listitem item=null;
		Listcell cell=null;
		int x=0;
		Util.limpiarListbox(listHistSolicitudes);
		
		for(LineaCreditoCliente lineaCreditoCliente: list){
			SolicitudCartera solicitudCartera=lineaCreditoCliente.getSolicitudClienteCredito().getSolicitudCartera();
			Usuario funcionario = solicitudCartera.getUsuario();
			Cliente cliente=solicitudCartera.getCliente();
			x++;
			item=new Listitem();
			cell=new Listcell(String.valueOf(x));
			item.appendChild(cell);
			cell=new Listcell(funcionario.getApellidoPaterno()+" "+funcionario.getApellidoMaterno()+", "+funcionario.getNombre());
			item.appendChild(cell);
			cell=new Listcell(cliente.getRazonSocial());
			item.appendChild(cell);
			cell=new Listcell(Constantes.FORMAT_DATE.format(lineaCreditoCliente.getSolicitudClienteCredito().getFechaSolicitud()));
			cell.setStyle("font-size:11px !important");
			item.appendChild(cell);
			
			if(lineaCreditoCliente.getEstadoLineaCredito().equals(Constantes.ESTADOSOL_ACTIVA))
				cell=new Listcell(Constantes.FORMAT_LONG.format(lineaCreditoCliente.getSolicitudClienteCredito().getFechaAprobacion()));
			else if(lineaCreditoCliente.getEstadoLineaCredito().equals(Constantes.ESTADOSOL_ANULADA))
				cell=new Listcell(Constantes.FORMAT_LONG.format(lineaCreditoCliente.getSolicitudClienteCredito().getFechaAnulacion()));
			else
				cell=new Listcell();
			cell.setStyle("font-size:11px !important");
			item.appendChild(cell);
			
			/*Validacion del estado aprobacion gerencia comercial*/
			if(!(lineaCreditoCliente.getSolicitudClienteCredito().getEstadoSolicitudAprobadoXFinanzas()==null)){
				if(lineaCreditoCliente.getEstadoLineaCredito().equals(Constantes.ESTADOSOL_ACTIVA))
					cell=new Listcell(Constantes.APROBADO_DESC);
				else if(lineaCreditoCliente.getEstadoLineaCredito().equals(Constantes.ESTADOSOL_INACTIVA))
					cell=new Listcell(Constantes.DESAPROBADO_DESC);
				else if(lineaCreditoCliente.getEstadoLineaCredito().equals(Constantes.ESTADOSOL_ANULADA))
					cell=new Listcell(Constantes.LABEL_ESTADOSOL_ANULADA_DESC);
				else cell=new Listcell("No definido");
			}else
				cell=new Listcell("DEVUELTA");
			
			item.appendChild(cell);
			
			final Toolbarbutton button= new Toolbarbutton();
//			cell=new Listcell();
			button.setContext(cell.getLabel());
//			button.setId(lineaCreditoCliente.getSolicitudClienteCredito().getId().toString());
			button.setZindex(lineaCreditoCliente.getSolicitudClienteCredito().getId().intValue());
			button.setStyle("color:blue; font-style:inherit; font-size: 8.5px !important");
			if(ServiceLocator.getLineaCreditoClienteManager().validadSolicitudAprobadaN3(lineaCreditoCliente.getSolicitudClienteCredito().getId())==false)
				button.setLabel("Modificar");
			else button.setLabel("Consultar");
						
			button.addEventListener(Events.ON_CLICK,new EventListener<Event>() {
				@Override
				public void onEvent(Event event) throws Exception {
					selectPendientes=false;
					solicitudClienteCredito= new SolicitudClienteCredito();
					solicitudClienteCredito=ServiceLocator.getSolicitudClienteCreditoManager().buscarPorId(Long.valueOf(button.getZindex()));
					showDetalleSolicitud(solicitudClienteCredito);
					
					btnAceptar.setVisible(true);
					visibleSolicitud(true);
					visibleLisPendientes(false);
					tabHistorial.setDisabled(true);
					tabPendientes.setSelected(true);
					
					if (!(button.getContext().equals("DEVUELTA"))){
						if(solicitudClienteCredito.getEstadoSolicitud().equals(Constantes.ESTADOSOL_ACTIVA))
							rbAprueba.setChecked(true);
						else if(solicitudClienteCredito.getEstadoSolicitud().equals(Constantes.ESTADOSOL_INACTIVA))
							rbDesaprueba.setChecked(true);
					}else{
						dbxCreditoREconsiderar.setValue(solicitudClienteCredito.getLineaCreditoAprobada());
						rbDevuelveFinanzas.setChecked(true);
					}
										
					if(ServiceLocator.getLineaCreditoClienteManager().validadSolicitudAprobadaN3(solicitudClienteCredito.getId())==false){
						rbDevuelveFinanzas.setChecked(true);
						dbxCreditoREconsiderar.setReadonly(false);
//						btnAceptar.setVisible(true);
						Util.disabledBtnAceptar(usuarioAprobador.getNivelAprobacion().intValue()!=Constantes.NIVEL_TRES, btnAceptar, accesoGrabar());
						disabledControlsSolciitud(btnAceptar.isDisabled());
						rbAprueba.setChecked(false);
						rbDesaprueba.setChecked(false);
					}else{
						disabledControlsSolciitud(true);
//						btnAceptar.setVisible(false);
						Util.disabledBtnAceptar(true, btnAceptar, false);
					}
				}
			});
			cell=new Listcell();
			cell.appendChild(button);
			item.appendChild(cell);
			
			item.addEventListener(Events.ON_DOUBLE_CLICK,new EventListener<Event>() {
				@Override
				public void onEvent(Event event) throws Exception {
					selectPendientes=false;
					Listitem listitem=listHistSolicitudes.getItemAtIndex(listHistSolicitudes.getSelectedIndex());
					solicitudClienteCredito= new SolicitudClienteCredito();
					solicitudClienteCredito=ServiceLocator.getSolicitudClienteCreditoManager().buscarPorId(((LineaCreditoCliente)listitem.getValue()).getSolicitudClienteCredito().getId());
					showDetalleSolicitud(solicitudClienteCredito);

					visibleSolicitud(true);
					visibleLisPendientes(false);
					tabHistorial.setDisabled(true);
					btnAceptar.setVisible(true);
					Util.disabledBtnAceptar(true, btnAceptar, false);
					disabledControlsSolciitud(true);
					tabPendientes.setSelected(true);
					if(solicitudClienteCredito.getEstadoSolicitud().equals(Constantes.ESTADOSOL_ACTIVA))
						rbAprueba.setChecked(true);
					else if(solicitudClienteCredito.getEstadoSolicitud().equals(Constantes.ESTADOSOL_INACTIVA))
					rbDesaprueba.setChecked(true);
				}
			});
			item.setValue(lineaCreditoCliente);
			listHistSolicitudes.appendChild(item);
		}
	}
	
//	private void desaprovarCredito(Long idLineaCreditoCliente){
//		Messagebox.show(Messages.getString("wndAprobarCredito.question.confirmaDesaprobacion") , DlgMessage.NOMBREAPLICACION, DlgMessage.BTN_YESNO, Messagebox.QUESTION, new EventListener<Event>() {
//			@Override
//			public void onEvent(Event e) throws Exception {
//				if(e.getName().equals("onYes")){
//					
//				}
//			}
//		});
//	}

}
