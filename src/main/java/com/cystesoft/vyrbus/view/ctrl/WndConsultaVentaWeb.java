/**
 * Proyecto		: SISVYR
 * Sistema		: Sistema de Ventas y Reservas
 * Descripción	: 
 * Autor		: José Abanto
 * Fecha		: 12 ene. 2024
 * Hora			: 00:46:44
 */
package com.cystesoft.vyrbus.view.ctrl;

import java.util.Date;
import java.util.List;

import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zul.Button;
import org.zkoss.zul.Caption;
import org.zkoss.zul.Column;
import org.zkoss.zul.Columns;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.Grid;
import org.zkoss.zul.Groupbox;
import org.zkoss.zul.Label;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listhead;
import org.zkoss.zul.Listheader;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Row;
import org.zkoss.zul.Rows;
import org.zkoss.zul.Separator;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

import com.cystesoft.vyrbus.model.bean.TransactionOpenpay;
import com.cystesoft.vyrbus.model.bean.VentaPasaje;
import com.cystesoft.vyrbus.service.fe.Venta;
import com.cystesoft.vyrbus.service.locator.ServiceLocator;
import com.cystesoft.vyrbus.service.util.Constantes;
import com.cystesoft.vyrbus.service.util.Util;
import com.cystesoft.vyrbus.service.util.UtilData;
import com.cystesoft.vyrbus.view.ui.DlgMessage;
import com.cystesoft.vyrbus.view.ui.WndBase;

/**
 * @author Jose
 *
 */
public class WndConsultaVentaWeb extends WndBase {
	private static final long serialVersionUID = 1L;
	
	private Textbox txtCliente;
	private Datebox dtbxDesde;
	private Datebox dtbxHasta;
	private Combobox cmbEstado;
	private Listbox listboxLista;
	private Window winPasajeros;
	
	/* (non-Javadoc)
	 * @see com.cystesoft.vyrbus.view.ui.WndBase#onCreate()
	 */
	@Override
	public void onCreate() throws Exception {
		dtbxDesde.setValue(new Date());
		dtbxHasta.setValue(new Date());
		cmbEstado.setSelectedIndex(0);
	}
	
	/* (non-Javadoc)
	 * @see com.cystesoft.vyrbus.view.ui.WndBase#initComponents()
	 */
	@Override
	public void initComponents() {
		txtCliente = (Textbox)getFellow("txtCliente");
		cmbEstado = (Combobox)getFellow("cmbEstado");
		dtbxDesde = (Datebox)getFellow("dtbxDesde");
		dtbxHasta = (Datebox)getFellow("dtbxHasta");
		listboxLista = (Listbox)getFellow("listboxLista");
	}
	
	public void onSearch() throws Exception {
		try {
			Listitem item=null;
			Listcell cell=null;
			int x=1;
			
			String estado = cmbEstado.getSelectedIndex()==0?"":cmbEstado.getSelectedItem().getValue();
			String contacto = txtCliente.getText().trim();
			List<TransactionOpenpay> lstTransacciones =  ServiceLocator.getVentaPasajesManager().buscarVentaWeb(dtbxDesde.getText(), dtbxHasta.getText(), estado, contacto);
			if(lstTransacciones.size()>0) {
				for(TransactionOpenpay transactionOpenpay : lstTransacciones) {
					item = new Listitem();
					cell = new Listcell(String.valueOf(x++));
					item.appendChild(cell);
					cell = new Listcell(transactionOpenpay.getCodeTransaction());
					item.appendChild(cell);
					cell = new Listcell(transactionOpenpay.getStatus());
					item.appendChild(cell);
					cell = new Listcell(transactionOpenpay.getDescription());
					item.appendChild(cell);
					cell = new Listcell(transactionOpenpay.getError_message().equals("null")?"":transactionOpenpay.getError_message());
					item.appendChild(cell);
					cell = new Listcell(transactionOpenpay.getContacto());
					item.appendChild(cell);
					cell = new Listcell(transactionOpenpay.getCustomer_phone_number());
					item.appendChild(cell);
					cell = new Listcell(Util.toNumberFormat(transactionOpenpay.getAmount(), 2));
					item.appendChild(cell);
					cell = new Listcell();
					Button btnPasajero = new Button("Pasajeros");
					btnPasajero.setId(transactionOpenpay.getOrder_id());
					btnPasajero.addEventListener(Events.ON_CLICK, new EventListener<Event>() {
						public void onEvent(Event e) {
							mostrarPasajeros(e.getTarget().getId());							
						}
					});
					btnPasajero.setClass("btn btn-success");
					cell.appendChild(btnPasajero);
					item.appendChild(cell);
					listboxLista.appendChild(item);
				}
			}else {
				DlgMessage.information("No se encontro registros!!!");
			}
		}catch (Exception e) {
			e.getMessage();
		}		
	}
	
	public void mostrarPasajeros(String idOrder) {
		System.out.println(idOrder);
		
		List<VentaPasaje> lstVentas = ServiceLocator.getVentaPasajesManager().buscarVentasByNumeroOrden(idOrder);
		
		Caption caption = null;
		Groupbox groupbox = null;
		
		final Window win = new Window("", "normal", true);
		win.setWidth("700px");

		caption = new Caption("PASAJEROS", "resources/menu/menu_devolucion.png");
		win.appendChild(caption);
		
		Listbox listbox = new Listbox();
		Listitem listitem = null;
		Listcell listcell = null;
		Listhead listhead = null;
		Listheader listheader = null;
		
		listhead = new Listhead();
		listheader = new Listheader("Numero Documento");
		listheader.setStyle("color: #ffffff");
		listhead.appendChild(listheader);
		
		listheader = new Listheader("Pasajero");
		listheader.setStyle("color: #ffffff");
		listhead.appendChild(listheader);
		
		listheader = new Listheader("Ruta");
		listheader.setStyle("color: #ffffff");
		listhead.appendChild(listheader);
		
		listheader = new Listheader("Servicio");
		listheader.setStyle("color: #ffffff");
		listhead.appendChild(listheader);
		
		listheader = new Listheader("Fecha");
		listheader.setStyle("color: #ffffff");
		listhead.appendChild(listheader);
		
		listheader = new Listheader("Hora");
		listheader.setStyle("color: #ffffff");
		listhead.appendChild(listheader);
		
		listheader = new Listheader("Boleto");
		listheader.setStyle("color: #ffffff");
		listhead.appendChild(listheader);
		
		listheader = new Listheader("Asiento");
		listheader.setStyle("color: #ffffff");
		listhead.appendChild(listheader);
		
		listheader = new Listheader("Importe");
		listheader.setStyle("color: #ffffff");
		listhead.appendChild(listheader);
		
		listbox.appendChild(listhead);
		
		for(VentaPasaje venta : lstVentas) {
			listitem = new Listitem();
			listcell = new Listcell(venta.getPasajero().getNumeroDocumento());
			listitem.appendChild(listcell);
			
			listcell = new Listcell(venta.getPasajero().getNombresApellidos());
			listitem.appendChild(listcell);
			
			listcell = new Listcell(venta.getRuta().getOrigen() + " - " + venta.getRuta().getDestino());
			listitem.appendChild(listcell);
			
			listcell = new Listcell(venta.getServicio().getDenominacion());
			listitem.appendChild(listcell);
			
			listcell = new Listcell(Util.DatetoString(venta.getFechaPartida(), "dd/MM/yyyy"));
			listitem.appendChild(listcell);
			
			listcell = new Listcell(venta.getHoraPartida());
			listitem.appendChild(listcell);
			
			listcell = new Listcell(venta.getNumeroBoleto());
			listitem.appendChild(listcell);
			
			listcell = new Listcell(venta.getNumeroAsiento().toString());
			listitem.appendChild(listcell);
			
			listcell = new Listcell(Util.toNumberFormat(venta.getImportePagado(), 2));
			listitem.appendChild(listcell);
			
			listbox.appendChild(listitem);
		}
		
		win.appendChild(listbox);
		
		Button btnAceptar = new Button("Aceptar");
		btnAceptar.addEventListener(Events.ON_CLICK, new EventListener<Event>() {
			@Override
			public void onEvent(Event e){
				win.onClose();
			}
		});
		btnAceptar.setHeight("28px");
		btnAceptar.setWidth("100px");
		btnAceptar.setFocus(true);
		btnAceptar.setClass("btnCommandM");
		
		win.appendChild(btnAceptar);
		
		win.appendChild(new Separator("horizontal"));

		System.out.println(idOrder);
		this.appendChild(win);
		win.setMode("modal");
	}
	
	/* (non-Javadoc)
	 * @see org.zkoss.zul.Window#onClose()
	 */
	@Override
	public void onClose() {
		closeTabWindow();
	}
	
}
