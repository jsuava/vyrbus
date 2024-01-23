/**
 * Proyecto		: SISVYR
 * Sistema		: Sistema de Ventas y Reservas
 * Descripci�n	:
 * Autor		: Jos�
 * Fecha		: 18/10/2013
 */
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
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Comboitem;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Timebox;

import com.cystesoft.vyrbus.model.bean.Agencia;
import com.cystesoft.vyrbus.model.bean.TipoComprobante;
import com.cystesoft.vyrbus.model.bean.TipoMovimiento;
import com.cystesoft.vyrbus.model.bean.VentaPasaje;
import com.cystesoft.vyrbus.service.exceptions.TipoComprobanteNullException;
import com.cystesoft.vyrbus.service.locator.ServiceLocator;
import com.cystesoft.vyrbus.service.util.Constantes;
import com.cystesoft.vyrbus.service.util.Messages;
import com.cystesoft.vyrbus.service.util.Util;
import com.cystesoft.vyrbus.service.util.UtilData;
import com.cystesoft.vyrbus.view.ui.DlgMessage;
import com.cystesoft.vyrbus.view.ui.WndBase;

/**
 * @author JABANTO
 *
 */
public class WndAnulacionVouchers extends WndBase{
	private static final long serialVersionUID = 1L;
	private Combobox cmbTipoComprobante;
	private Textbox txtNumeroVoucher;
	private Textbox txtNumeroControl;
	private Listbox lsbxVouchers;
	private Combobox cmbCliente;
	private Datebox dtbxFechaPartida;
	private Timebox tbxHoraPartida;

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.view.ui.WndBase#initComponents()
	 */
	@Override
	public void initComponents() {
		// TODO Auto-generated method stub
		txtNumeroVoucher=(Textbox)this.getFellow("txtNumeroVoucher");
		txtNumeroControl=(Textbox)this.getFellow("txtNumeroControl");
		lsbxVouchers=(Listbox)this.getFellow("lsbxVouchers");
		cmbTipoComprobante=(Combobox)this.getFellow("cmbTipoComprobante");
		cmbCliente=(Combobox)this.getFellow("cmbCliente");
		dtbxFechaPartida=(Datebox)this.getFellow("dtbxFechaPartida");
		tbxHoraPartida=(Timebox)this.getFellow("tbxHoraPartida");
	}

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.view.ui.WndBase#onCreate()
	 */
	@Override
	public void onCreate() throws Exception {
		// TODO Auto-generated method stub
		cargarTipoComprobanteVouchers(cmbTipoComprobante);
		Util.seleccionarValorItemCombo(TipoComprobante.class, cmbTipoComprobante, Constantes.ID_TIPCOM_VOUCHER_CORPORATIVO);
		cargarClientes(cmbCliente);

		dtbxFechaPartida.setValue(new Date());
		String fecha = Util.DatetoString(new Date(), "yyyyMMdd");
		dtbxFechaPartida.setConstraint("after "+fecha);
	}

	public void buscar() throws Exception{
		try{
			Util.limpiarListbox(lsbxVouchers);

			if(!(cmbTipoComprobante.getSelectedItem().getValue() instanceof TipoComprobante))
				throw new TipoComprobanteNullException();
			if(txtNumeroVoucher.getText().trim().isEmpty() && txtNumeroControl.getText().isEmpty() &&
					cmbCliente.getSelectedIndex()<=0 && dtbxFechaPartida.getValue()==null && tbxHoraPartida.getValue()==null){
				DlgMessage.information(Messages.getString("WndVouchers.information.noParameters"));
				return;
			}
			String numeroVoucher=null;
			String numeroControl=null;
			String rucCliente=null;
			String fechaPartida=null;
			String horaPartida=null;

			Integer idTipoComprobante=((TipoComprobante)cmbTipoComprobante.getSelectedItem().getValue()).getId();
			if(dtbxFechaPartida.getValue()!=null)
				fechaPartida=Constantes.FORMAT_DATE.format(dtbxFechaPartida.getValue());

			if(!(txtNumeroVoucher.getText().trim().isEmpty())){
				numeroVoucher=Util.autocompleNumberBoleto(txtNumeroVoucher.getText().trim());
				txtNumeroVoucher.setText(numeroVoucher);
				fechaPartida=null;
			}

			if(!(txtNumeroControl.getText().trim().isEmpty())){
				numeroControl = Util.generateControlNumber(txtNumeroControl.getText().trim().toUpperCase());
				txtNumeroControl.setText(numeroControl);
				numeroControl=txtNumeroControl.getText().trim();
				fechaPartida=null;
			}

			if(cmbCliente.getSelectedIndex()>0){
				Agencia agencia=ServiceLocator.getAgenciaManager().buscarPorId(((Agencia)cmbCliente.getSelectedItem().getValue()).getId().longValue());
				rucCliente=agencia.getConcesionario()!=null?agencia.getConcesionario().getRuc():null;
			}

			if(tbxHoraPartida.getValue()!=null)
				horaPartida=tbxHoraPartida.getText().trim();

			List<VentaPasaje>listVouchers=ServiceLocator.getVentaPasajesManager().buscarVoucherForAnulacion(idTipoComprobante, numeroVoucher, numeroControl,rucCliente, fechaPartida,horaPartida,null);

			Listitem item=null;
			Listcell cell=null;

			if(listVouchers.size()==0){
				DlgMessage.information(Messages.getString("WndVouchers.information.noRegistros"));
				return;
			}

			for(VentaPasaje ventaPasaje: listVouchers){
				boolean isAnulado=false;

				item=new Listitem();
				if(ventaPasaje.getTipoMovimiento().getId().intValue()==Constantes.ID_TIPMOV_ANULACION ||
						ventaPasaje.getTipoMovimiento().getId().intValue()==Constantes.ID_TIPMOV_DEVOLUCION ||
						ventaPasaje.getTipoMovimiento().getId().intValue()==Constantes.ID_TIPMOV_ANULACION){
					isAnulado=true;
				}

				cell=new Listcell(ventaPasaje.getCliente().getRazonSocial());
				if(isAnulado)cell.setStyle("color:red");
				item.appendChild(cell);

				cell=new Listcell(ventaPasaje.getPasajero().getNombresApellidos());
				if(isAnulado)cell.setStyle("color:red");
				item.appendChild(cell);

				cell= new Listcell(ventaPasaje.getNumeroVoucher());
				if(isAnulado)cell.setStyle("font-size:11px !important;color:red");
				else cell.setStyle("font-size:11px !important");
				item.appendChild(cell);

				cell=new Listcell(ventaPasaje.getNumeroBoleto());
				if(isAnulado)cell.setStyle("font-size:11px !important;color:red");
				else cell.setStyle("font-size:11px !important");
				item.appendChild(cell);

				cell=new Listcell(ventaPasaje.getNumeroControl());
				if(isAnulado)cell.setStyle("font-size:11px !important;color:red");
				else cell.setStyle("font-size:11px !important");
				item.appendChild(cell);

				cell= new Listcell(ventaPasaje.getRuta().toString());
				if(isAnulado)cell.setStyle("color:red");
				item.appendChild(cell);

				cell= new Listcell(Constantes.FORMAT_DATE.format(ventaPasaje.getFechaPartida()));
				if(isAnulado)cell.setStyle("font-size:11px !important;color:red");
				else cell.setStyle("font-size:11px !important");
				item.appendChild(cell);

				cell= new Listcell(ventaPasaje.getHoraPartida());
				if(isAnulado)cell.setStyle("font-size:11px !important;color:red");
				else cell.setStyle("font-size:11px !important");
				item.appendChild(cell);

				cell=new Listcell(ventaPasaje.getNumeroAsiento().toString());
				if(isAnulado)cell.setStyle("font-size:11px !important;color:red");
				else cell.setStyle("font-size:11px !important");
				item.appendChild(cell);

				cell=new Listcell(Constantes.FORMAT_LONG.format(ventaPasaje.getFechaInsercion()));
				if(isAnulado)cell.setStyle("font-size:11px !important;color:red");
				else cell.setStyle("font-size:11px !important");
				item.appendChild(cell);

				cell=new Listcell(ventaPasaje.getUsuario().getApellidoPaterno()+" "+ventaPasaje.getUsuario().getApellidoMaterno()+", "+ventaPasaje.getUsuario().getNombre());
				if(isAnulado)cell.setStyle("color:red");
				item.appendChild(cell);

				Button btnAnular= new Button("","/resources/mp_anular.png");
				cell= new Listcell();
				cell.appendChild(btnAnular);
				btnAnular.setId(ventaPasaje.getId().toString());
				btnAnular.setStyle("cursor:pointer");
				btnAnular.setTooltiptext("Haga click aqui si desea anular el Voucher");


				btnAnular.addEventListener(Events.ON_CLICK,new EventListener<Event>() {
					@Override
					public void onEvent(Event e) throws Exception {
						// TODO Auto-generated method stub
						validaAnulacionVoucher(e.getTarget().getId());

					}
				});

				if(isAnulado)//indica si el registro esta anulado
					btnAnular.setVisible(false);

				item.appendChild(cell);
				item.setValue(ventaPasaje);
				lsbxVouchers.appendChild(item);
			}



		}catch (TipoComprobanteNullException tcexn){
			DlgMessage.information(Messages.getString("WndVouchers.information.noTipoComprobante"),cmbTipoComprobante);
		}
	}

	public void validaAnulacionVoucher(final String idVenta){
		try{
			//Valida que la fecha de emision del vouchers este dentro del mes acual
			VentaPasaje ventaPasaje= ServiceLocator.getVentaPasajesManager().buscarUltimoRegistro(Long.valueOf(idVenta));
			if(ventaPasaje.getTipoComprobante().getId().intValue()==Constantes.ID_TIPCOM_BOLETO_VIAJE ||
					ventaPasaje.getTipoComprobante().getId().intValue()==Constantes.ID_TIPCOM_BOLETA_VENTA ||
					ventaPasaje.getTipoComprobante().getId().intValue()==Constantes.ID_TIPCOM_FACTURA){
				DlgMessage.information(Messages.getString("WndVouchers.information.voucherConBoleto"));
			}else{
				Messagebox.show(Messages.getString("WndVouchers.information.confirmarAnulacion"), DlgMessage.NOMBREAPLICACION, DlgMessage.BTN_YESNO, Messagebox.QUESTION,DlgMessage.BTN_DEFAULT_NO, new EventListener<Event>() {
					@Override
					public void onEvent(Event e){
						try{
							if(e.getName().equals("onYes")){
								VentaPasaje venta= new VentaPasaje();
								venta=ServiceLocator.getVentaPasajesManager().buscarPorId(Long.valueOf(idVenta));
								anularVoucher(venta);
							}
						}catch(Exception ex){
							ex.printStackTrace();
							DlgMessage.information(this.getClass().getSimpleName()+" "+ex.getMessage());
						}
					}
				});
			}

		}catch(Exception ex){
			DlgMessage.error(this.getClass().getSimpleName()+" "+ex.getMessage());
		}
	}

	private void anularVoucher(VentaPasaje ventaPasaje) throws Exception{
		Double importePagado=ventaPasaje.getImportePagado();

		ventaPasaje.setTarifa(0.0);
		ventaPasaje.setRecargo(0.0);
		ventaPasaje.setDescuento(0.0);
		ventaPasaje.setImportePagado(0.0);
		ventaPasaje.setAcuenta(0.0);
		ventaPasaje.setImportePagado(0.0);
		ventaPasaje.setTarifaEquibalente(ventaPasaje.getTarifaEquibalente()!=null?.00:null);
		ventaPasaje.setDescuentoEquibalente(ventaPasaje.getDescuentoEquibalente()!=null?.00:null);
		ventaPasaje.setImportePagadoEquibalente(ventaPasaje.getImportePagadoEquibalente()!=null?.00:null);
		ventaPasaje.setTipoMovimiento(new TipoMovimiento(Constantes.ID_TIPMOV_ANULACION));
		UtilData.auditarRegistro(ventaPasaje, true, getUsuario(), Executions.getCurrent());
//		int result = ServiceLocator.getVentaPasajesManager().anularMovimiento(ventaPasaje);
		ServiceLocator.getVentaPasajesManager().anularMovimiento(ventaPasaje,false, true);

		/*Actualiza el Saldo de la linea de credito - ##19/12/2014 - jabanto*/
		ServiceLocator.getLineaCreditoClienteManager().actualizarSaldo(importePagado, ventaPasaje.getRucClienteCredito(), getUsuario(), UtilData.ipLocal(Executions.getCurrent()), true);

//		if(result==Constantes.CORRECT){
		DlgMessage.information(Messages.getString("WndVouchers.information.exitoAnulacion"));
		buscar();
//		}
	}


	/**
	 * Carga los comprobante vouchers de agencia y cliente coprativo
	 * @param combobox
	 */
	public void cargarClientes(Combobox combobox) {
		try{
			combobox.getItems().clear();

			/*carga todas la agencias de viaje y corporativas*/
			String sid="";
			if(cmbTipoComprobante.getSelectedIndex()<=0)
				sid=Constantes.ID_TIPAGE_VIAJES+","+Constantes.ID_TIPAGE_CORPORATIVO;
			else if(cmbTipoComprobante.getSelectedIndex()>0){
				TipoComprobante tipoComprobante=cmbTipoComprobante.getSelectedItem().getValue();
				if(tipoComprobante.getId().intValue()==Constantes.ID_TIPCOM_VOUCHER_AGENCIA_VIAJES)
					sid=String.valueOf(Constantes.ID_TIPAGE_VIAJES);
				else if(tipoComprobante.getId().intValue()==Constantes.ID_TIPCOM_VOUCHER_CORPORATIVO)
					sid=String.valueOf(Constantes.ID_TIPAGE_CORPORATIVO);
			}

			String[] ids =sid.split(",");
			Integer[] oCriteriosIN = new Integer[ids.length];
			for(int i=0; i<ids.length; i++){
				oCriteriosIN[i]=Integer.valueOf(ids[i]);
			}

			List<String> criteriosOrdenar= new ArrayList<>();
			criteriosOrdenar.add("denominacion");
			UtilData.cargarAgencia("tipoAgencia.id", oCriteriosIN, criteriosOrdenar,combobox , false);

			cmbCliente.setFocus(true);
			cmbCliente.select();

		}catch(Exception ex){
			ex.printStackTrace();
		}
	}

	/**
	 * Carga los comprobante vouchers de agencia y cliente coprativo
	 * @param combobox
	 */
	public void cargarTipoComprobanteVouchers(Combobox combobox) {
		try{
			TreeMap<String, Object>parametros = new TreeMap<>();
			parametros.put("estadoRegistro", Constantes.VALUE_ACTIVO);
			ArrayList<TipoComprobante> lstEspecieValorada = ServiceLocator.getTipoComprobanteManager().buscarPorX(parametros, null);
			UtilData.cargarGenericData(combobox, false);

			for(TipoComprobante tipoComprobante : lstEspecieValorada){
				Comboitem oComboitem = new Comboitem();
				//Carga solamente los vouchers de laa agencias y Clientes corporativos.
				if(tipoComprobante.getId().intValue()==Constantes.ID_TIPCOM_VOUCHER_AGENCIA_VIAJES ||
						tipoComprobante.getId().intValue()==Constantes.ID_TIPCOM_VOUCHER_CORPORATIVO){
					/*carga el combo tipo Comprobante*/
					oComboitem.setLabel(tipoComprobante.getDenominacion());
					oComboitem.setValue(tipoComprobante);
					combobox.appendChild(oComboitem);
				}
			}

		}catch(Exception ex){
			ex.printStackTrace();
		}
	}

}
