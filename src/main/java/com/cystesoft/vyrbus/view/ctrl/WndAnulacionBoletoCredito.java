package com.cystesoft.vyrbus.view.ctrl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.TreeMap;

import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zul.Button;
import org.zkoss.zul.Div;
import org.zkoss.zul.Grid;
import org.zkoss.zul.Label;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Row;
import org.zkoss.zul.Rows;
import org.zkoss.zul.Space;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

import com.cystesoft.vyrbus.model.bean.Cliente;
import com.cystesoft.vyrbus.model.bean.Liquidacion;
import com.cystesoft.vyrbus.model.bean.VentaPasaje;
import com.cystesoft.vyrbus.service.locator.ServiceLocator;
import com.cystesoft.vyrbus.service.util.Constantes;
import com.cystesoft.vyrbus.service.util.Messages;
import com.cystesoft.vyrbus.service.util.Util;
import com.cystesoft.vyrbus.service.util.UtilData;
import com.cystesoft.vyrbus.service.util.WSFE;
import com.cystesoft.vyrbus.view.ui.DlgMessage;
import com.cystesoft.vyrbus.view.ui.WndBase;

/**
 * @author JABANTO
 *
 */
public class WndAnulacionBoletoCredito extends WndBase {
	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	private Textbox txtNumeroVoucher;
	private Textbox txtNumeroBoleto;
	private Listbox lsbxBoleto;

	private Boolean perdidaServicio=false;
	private Window wndAutorizacion = null;

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.view.ui.WndBase#initComponents()
	 */
	@Override
	public void initComponents() {
		// TODO Auto-generated method stub
		txtNumeroVoucher=(Textbox)this.getFellow("txtNumeroVoucher");
		txtNumeroBoleto=(Textbox)this.getFellow("txtNumeroBoleto");
		lsbxBoleto=(Listbox)this.getFellow("lsbxBoleto");
	}

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.view.ui.WndBase#onCreate()
	 */
	@Override
	public void onCreate() throws Exception {
		// TODO Auto-generated method stub
	}


	/**
	 *Busca el boleto
	 * @throws Exception
	 */
	@SuppressWarnings("deprecation")
	public void buscar() throws Exception {
		// TODO Auto-generated method stub
		Util.limpiarListbox(lsbxBoleto);

		String numeroVoucher=null;
		String numeroBoleto=null;

		if(!(txtNumeroVoucher.getText().trim().isEmpty())){
			txtNumeroVoucher.setText(Util.autocompleNumberBoleto(txtNumeroVoucher.getText().trim()));
			numeroVoucher=txtNumeroVoucher.getText().trim();
		}
		if(!(txtNumeroBoleto.getText().trim().isEmpty())){
			txtNumeroBoleto.setText(Util.autocompleNumberBoleto(txtNumeroBoleto.getText().trim()));
			numeroBoleto=txtNumeroBoleto.getText().trim();
		}

		//Si no ha ingresado ningún parámetro.
		if(numeroBoleto==null && numeroVoucher==null){
			DlgMessage.information(Messages.getString("WndVouchers.information.noParameters"));
			return;
		}
		//Realiza la busqueda del boleto
		List<VentaPasaje>listVouchers=ServiceLocator.getVentaPasajesManager().buscarVoucherForAnulacion(null, numeroVoucher, null,null, null,null,numeroBoleto);
		if(listVouchers.size()==0){
			DlgMessage.information(Messages.getString("WndVouchers.information.noRegistros"));
			return;
		}


		TreeMap<String, Object>criteriosBusqueda=new TreeMap<>();
		for(VentaPasaje ventaPasaje: listVouchers){
			Listitem item=new Listitem();
			Listcell cell=null;
			VentaPasaje ventaBoleto=null;


			if(ventaPasaje.getTipoMovimiento().getId().intValue()!=Constantes.ID_TIPMOV_ANULACION){
				ventaBoleto=ServiceLocator.getVentaPasajesManager().buscarPorId(ventaPasaje.getVentaPasaje().getId());

				/* Valida que la fecha de emision del boleto, este dentro del mes.*/

				/* Valida si el boleto aun esta permitido anular*/
				int hora=0,minuto=0;
				String[] horaPartida=ventaBoleto.getHoraPartida().split(":");
				for(int i=0;i<horaPartida.length;i++){
					if(i==0){
						hora=Integer.valueOf(horaPartida[i]);
					}else{
						minuto=Integer.valueOf(horaPartida[i]);
					}
				}

				Date fechaHoraViaje=new Date(ventaBoleto.getFechaPartida().getYear()
										    ,ventaBoleto.getFechaPartida().getMonth()
										    ,ventaBoleto.getFechaPartida().getDate()
										    ,hora,minuto);
				Date fechaHoraAnulacion=new Date();
				double horasDif=(double) ((fechaHoraViaje.getTime()-fechaHoraAnulacion.getTime())/Constantes.MILISEGUNDOS_X_HORA)+1;

				if(horasDif<=3)
					perdidaServicio=true;
				else
					perdidaServicio=false;

			}else{
				//Cuando el boleto esta anulado
				criteriosBusqueda=new TreeMap<>();
				List<String>criteriosOrden=new ArrayList<>();
				criteriosOrden.add("id");
				criteriosBusqueda.put("numeroBoleto",numeroBoleto!=null?numeroBoleto:numeroVoucher);
				criteriosBusqueda.put("tipoMovimiento", ventaPasaje.getTipoMovimiento());
				List<VentaPasaje>lstVentaPasaje=ServiceLocator.getVentaPasajesManager().buscarPorX(criteriosBusqueda, criteriosOrden);
				if(lstVentaPasaje.size()>0){
					ventaBoleto=lstVentaPasaje.get(0);
				}
			}

			/* Busca cliente/Agencia*/
			criteriosBusqueda=new TreeMap<>();
			criteriosBusqueda.put("numeroDocumento",ventaBoleto.getRucClienteCredito());
			List<Cliente>lstCliente=ServiceLocator.getClienteManager().buscarPorX(criteriosBusqueda, null);
			if(lstCliente.size()>0){
				Cliente cliente=lstCliente.get(0);
				ventaBoleto.setCliente(cliente);
			}

			boolean isAnulado=false;
			item=new Listitem();

			if(ventaBoleto.getTipoMovimiento().getId().intValue()==Constantes.ID_TIPMOV_ANULACION ||
					ventaBoleto.getTipoMovimiento().getId().intValue()==Constantes.ID_TIPMOV_DEVOLUCION ||
					ventaBoleto.getTipoMovimiento().getId().intValue()==Constantes.ID_TIPMOV_ANULACION){
				isAnulado=true;
			}

			cell=new Listcell(ventaBoleto.getCliente().getRazonSocial());
			if(isAnulado)cell.setStyle("color:red");
			item.appendChild(cell);

			cell= new Listcell(ventaBoleto.getNumeroBoletoAnterior());
			if(isAnulado)cell.setStyle("font-size:11px !important;color:red");
			else cell.setStyle("font-size:11px !important");
			item.appendChild(cell);

			cell=new Listcell(ventaBoleto.getNumeroBoleto());
			if(isAnulado)cell.setStyle("font-size:11px !important;color:red");
			else cell.setStyle("font-size:11px !important");
			item.appendChild(cell);

			cell= new Listcell(ventaBoleto.getRuta().toString());
			if(isAnulado)cell.setStyle("color:red");
			item.appendChild(cell);

			cell= new Listcell(Constantes.FORMAT_DATE.format(ventaBoleto.getFechaPartida()));
			if(isAnulado)cell.setStyle("font-size:11px !important;color:red");
			else cell.setStyle("font-size:11px !important");
			item.appendChild(cell);

			cell= new Listcell(ventaBoleto.getHoraPartida());
			if(isAnulado)cell.setStyle("font-size:11px !important;color:red");
			else cell.setStyle("font-size:11px !important");
			item.appendChild(cell);

			cell=new Listcell(ventaBoleto.getFechaLiquidacion()!=null?Constantes.FORMAT_DATE.format(ventaBoleto.getFechaLiquidacion()):"");
			if(isAnulado)cell.setStyle("font-size:11px !important;color:red");
			else cell.setStyle("font-size:11px !important");
			item.appendChild(cell);

			cell=new Listcell(ventaBoleto.getUsuario()!=null?ventaBoleto.getUsuario().toString():"");
			if(isAnulado)cell.setStyle("color:red");
			item.appendChild(cell);

			cell=new Listcell(ventaBoleto.getAgencia()!=null?ventaBoleto.getAgencia().getNombreCorto():"");
			if(isAnulado)cell.setStyle("font-size:11px !important;color:red");
			item.appendChild(cell);

			Button btnAnular= new Button("","/resources/mp_anular.png");
			cell= new Listcell();
			cell.appendChild(btnAnular);
			btnAnular.setId(ventaBoleto.getId().toString());
			btnAnular.setStyle("cursor:pointer");
			btnAnular.setTooltiptext("Haga click aqui si desea anular el Boleto");

			btnAnular.addEventListener(Events.ON_CLICK,new EventListener<Event>() {
				@Override
				public void onEvent(final Event ev) throws Exception {
					/*Valida si el boleto ya fue pagado*/
					VentaPasaje ventaPasaje= ServiceLocator.getVentaPasajesManager().buscarPorId(Long.valueOf(ev.getTarget().getId()));
					if(ventaPasaje.getEstadoDocumento().equals(Constantes.ESTADO_DOCUMENTO_PAGADO)){
						DlgMessage.information(Messages.getString("WndAnulacionBoletoCredito.information.boletoPagado"));
						return;
					}
					openVentanaAutorizacion(Long.valueOf(ev.getTarget().getId()));
				}
			});

			if(isAnulado)//indica si el registro esta anulado
				btnAnular.setVisible(false);

			item.appendChild(cell);
			item.setValue(ventaBoleto);
			lsbxBoleto.appendChild(item);
		}

	}

	/**
	 * Realiza la anulacion del boleto
	 * @throws Exception
	 */
	private void anularBoleto(Long idBoleto, String observaciones) throws Exception {
		String observacionBoleto="";
		VentaPasaje boleto=ServiceLocator.getVentaPasajesManager().buscarPorId(idBoleto);

//		/*Valida si el boleto ya esta liquidado*/
//		if(boleto.getLiquidacion()!=null){
//			/*Valida que el usuario que esta haciendo la anulacion tenga una liquidacion abierta*/
//			Date fechaLiquidacion=(Date)Executions.getCurrent().getDesktop().getSession().getAttribute(Constantes.ATRIBUTO_FECHA_LIQUIDACION);
//			if(fechaLiquidacion==null){
//				throw new Exception("El Comprobante ya fue liquidado, si desea realizar la anulación debe tener una liquidación abierta.");
//			}
//		}

		/**Temporal*/
		if(boleto.getTipoComprobante().getId().intValue()!=Constantes.ID_TIPCOM_BOLETO_VIAJE){
			DlgMessage.information("Comprobante no habilitado para la anulación.");
			return;
		}


		//Realizamos la busqueda agrupada por numero de control
		List<VentaPasaje> lstResult= ServiceLocator.getVentaPasajesManager().buscarBoletosDevolucion(null, boleto.getNumeroControl(), null);
		if(lstResult.get(0).getTipoMovimiento().getId().intValue()!=Constantes.ID_TIPMOV_CREDITO){
			VentaPasaje ultimoMovimiento= ServiceLocator.getVentaPasajesManager().buscarUltimoRegistro(lstResult.get(0).getVentaOriginal());
			DlgMessage.information(Messages.getString("WndAnulacionBoletoCredito.information.noAnulacionOtrosMovimientos")+ultimoMovimiento.getNumeroBoleto()+"("+ultimoMovimiento.getTipoMovimiento().getDenominacion()+")");
			return;
		}

		Double importeBoleto=boleto.getImportePagado();
		if(boleto.getObservaciones()!=null)
			observacionBoleto=boleto.getObservaciones()+" |  ";
		observacionBoleto+="OBS.ANULACIÓN: "+observaciones;
		if(observacionBoleto.length()>255)
			observacionBoleto=observacionBoleto.substring(0,255);
		boleto.setObservaciones(observacionBoleto);
		VentaPasaje notacredito= ServiceLocator.getVentaPasajesManager().anularBoletoCredito(boleto,getUsuario());
		if(notacredito!=null){
			WSFE.sendNota(notacredito);
		}else{
			/* Recalcula el detalle de la liquidacion*/
			if(boleto.getLiquidacion()!=null){
				Liquidacion liquidacion=ServiceLocator.getLiquidacionManager().buscarPorId(boleto.getLiquidacion().getId().longValue());
				/* Elimina el detalle para luego volver a insertar*/
				ServiceLocator.getDetalleLiquidacionManager().deleteXidLiquidacion(liquidacion.getId().longValue());
				/* Recalcula el detalle de la liquidacion, en el que fue incluido este boleto*/
				UtilData.procesarDetalleLiquidacion(liquidacion, getUsuario());
			}
		}

		/*Actualiza el Saldo de la linea de credito - ##19/12/2014 - jabanto*/
		ServiceLocator.getLineaCreditoClienteManager().actualizarSaldo(importeBoleto, boleto.getRucClienteCredito(), getUsuario(), UtilData.ipLocal(Executions.getCurrent()), true);

		//Refresca la busqueda.
		buscar();
	}

	private void openVentanaAutorizacion(Long idBoleto) throws Exception{
		wndAutorizacion = createVentanaAutorizacion(idBoleto);
		this.appendChild(wndAutorizacion);
		wndAutorizacion.setMode("modal");
	}

	@SuppressWarnings("deprecation")
	private  Window createVentanaAutorizacion(Long idBoleto) throws Exception {
		final Window window = new Window();
		window.setWidth("250px");

		Button btnAceptar = new Button();

		window.setMaximizable(false);
		window.setMinimizable(false);
		window.setSizable(false);
		window.setClosable(true);
		window.setTitle("Datos de autorización para la anulación");
		window.setStyle("padding: 5px");
		window.setWidth("350px");
		window.setVisible(true);

		Button btnCancelar = new Button();

		final Textbox txtObservaciones =  new Textbox();
		txtObservaciones.setMultiline(true);
		txtObservaciones.setMaxlength(255);
		txtObservaciones.setWidth("320px");
		txtObservaciones.setRows(5);

		Grid grid = new Grid();
		Rows rows = new Rows();

		Row row = new Row();
		Label label = new Label();
		label.setValue("Ingrese información de quién realiza la autorización (*)");
		label.setStyle("font-size:11px !important;font-weight: bold;color:black");
		row.appendChild(label);
		rows.appendChild(row);

		row=new Row();
		row.appendChild(txtObservaciones);
		rows.appendChild(row);

		row=new Row();
		rows.appendChild(row);

		row = new Row();
		btnCancelar.setLabel("Cancelar");
		btnCancelar.setImage("/resources/mp_cerrar.png");
		btnCancelar.setWidth("100px");
		Div div=new Div();
		div.setHeight("30px");
		div.setAlign("center");
		div.appendChild(new Space());
		div.appendChild(btnCancelar);

		div.appendChild(new Space());

		btnAceptar.setLabel("Aceptar");
		btnAceptar.setImage("/resources/mp_aceptarEnabled.png");
		btnAceptar.setWidth("100px");
		btnAceptar.setId(idBoleto.toString());
		div.appendChild(btnAceptar);

		grid.appendChild(rows);

		window.appendChild(grid);
		window.appendChild(div);

		/*CANCELAR*/
		btnCancelar.addEventListener(Events.ON_CLICK, new EventListener<Event>() {
			@Override
			public void onEvent(Event event) throws Exception {
				window.onClose();
			}
		});

		btnAceptar.addEventListener(Events.ON_CLICK, new EventListener<Event>() {
			@Override
			public void onEvent(final Event event) throws Exception {
				if (txtObservaciones.getText().trim().isEmpty()){
					DlgMessage.information(Messages.getString("WndAnulacionBoletoCredito.information.noObservaciones"),txtObservaciones);
					return;
				}
				Messagebox.show(Messages.getString("WndAnulacionBoletoCredito.question.confirmAnulacion"), DlgMessage.NOMBREAPLICACION, DlgMessage.BTN_YESNO, Messagebox.QUESTION,DlgMessage.BTN_DEFAULT_NO, new EventListener<Event>() {
					@Override
					public void onEvent(Event e) throws Exception {
						if(e.getName().equals("onYes")){
							try {
								if(perdidaServicio){
									Messagebox.show(Messages.getString("WndAnulacionBoletoCredito.question.confirmAnulacionPerdidaServicio"), DlgMessage.NOMBREAPLICACION, DlgMessage.BTN_YESNO, Messagebox.QUESTION,DlgMessage.BTN_DEFAULT_NO,new EventListener<Event>() {
										@Override
										public void onEvent(Event e) throws Exception {
											if(e.getName().equals("onYes")){
												anularBoleto(Long.valueOf(event.getTarget().getId()),txtObservaciones.getText().trim().toUpperCase());
												window.onClose();
											}
										}
									});
								}else{
									anularBoleto(Long.valueOf(event.getTarget().getId()),txtObservaciones.getText().trim().toUpperCase());
									window.onClose();
								}
							} catch (Exception e2) {
								e2.printStackTrace();
								DlgMessage.information(e2.getMessage());
							}
						}
					}
				});
			}
		});
		return window;
	}

}
