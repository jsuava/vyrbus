package com.cystesoft.vyrbus.view.ui;

import java.io.Serializable;
import java.util.ArrayList;

import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zul.Button;
import org.zkoss.zul.Div;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listhead;
import org.zkoss.zul.Listheader;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Window;

import com.cystesoft.vyrbus.model.bean.VentaPasaje;

/**
 *
 * @author JABANTO
 *
 */
public class WndComprobantesPendientesXAsociarBoleto extends WndBase implements Serializable {
	private static final long serialVersionUID = 1L;

	private Window oThisWindow = this;
	private ArrayList<VentaPasaje> lstPendientesXAsociarBoleto= new ArrayList<>();



	public WndComprobantesPendientesXAsociarBoleto() throws Exception{
		super();
		initComponents();
	}

	/*
	 * (non-Javadoc)
	 * @see com.tepsa.sisvyr.view.ui.WndBase#initComponents()
	 */
	@Override
	public void initComponents() {
		this.setTitle("COMPROBANTES PENDIENTES POR ASOCIAR A UN BOLETO");
		this.setMaximizable(false);
		this.setMinimizable(false);
		this.setSizable(false);
		this.setClosable(false);
		this.setStyle("padding: 5px");
		this.setWidth("680px");
		this.setHeight("210px");
		this.setVisible(true);
	}

	/*
	 * (non-Javadoc)
	 * @see com.tepsa.sisvyr.view.ui.WndBase#onCreate()
	 */
	@SuppressWarnings("deprecation")
	@Override
	public void onCreate() throws Exception {
		Listbox listbox=new Listbox();listbox.setHeight("150px");
		Listhead listhead=new Listhead();
		Listheader listheader=new Listheader();

		listheader.setLabel("TIPO COMPROBANTE"); listheader.setWidth("180px");listheader.setStyle("color: #ffffff");
		listhead.appendChild(listheader);
		listheader=new Listheader();listheader.setLabel("NUMERO"); listheader.setWidth("80px");listheader.setStyle("color: #ffffff");
		listhead.appendChild(listheader);
		listheader=new Listheader();listheader.setLabel("ASIENTO"); listheader.setWidth("50px");listheader.setStyle("color: #ffffff");
		listhead.appendChild(listheader);
		listheader=new Listheader();listheader.setLabel("NOMBRES DEL PASAJERO"); listheader.setWidth("180px");listheader.setStyle("color: #ffffff");
		listhead.appendChild(listheader);
		listheader=new Listheader();listheader.setLabel("RUTA"); listheader.setWidth("180px");listheader.setStyle("color: #ffffff");
		listhead.appendChild(listheader);
		listbox.appendChild(listhead);

		Listitem item=null;
		Listcell cell=null;

		for(VentaPasaje pasaje: lstPendientesXAsociarBoleto){
			item=new Listitem();
			cell=new Listcell(pasaje.getTipoComprobante().getDenominacion());
			item.appendChild(cell);
			cell=new Listcell(pasaje.getNumeroBoleto());
			cell.setStyle("font-size:11px !important");
			item.appendChild(cell);
			cell=new Listcell(pasaje.getNumeroAsiento().toString());
			cell.setStyle("font-size:11px !important");
			item.appendChild(cell);
			cell=new Listcell(pasaje.getPasajero().getApellidoPaterno()+" "+pasaje.getPasajero().getApellidoMaterno()+", "+ pasaje.getPasajero().getNombre());
			item.appendChild(cell);
			cell=new Listcell(pasaje.getRuta().getOrigen()+" - "+pasaje.getRuta().getDestino());
			item.appendChild(cell);

			item.setValue(pasaje);
			listbox.appendChild(item);
		}
		this.appendChild(listbox);

		Button button=new Button();
		button.setLabel("Cerrar"); button.setImage("/resources/mp_cerrar.png");
		button.setClass("btnCommandM");
		button.addEventListener(Events.ON_CLICK, new EventListener<Event>() {
			@Override
			public void onEvent(Event event) throws Exception {
				oThisWindow.onClose();
			}
		});

		Div div=new Div();
		div.setAlign("right");
		div.appendChild(button);
		this.appendChild(div);
	}

	public void setLstPendientesXAsociarBoleto(ArrayList<VentaPasaje> lstPendientesXAsociarBoleto){
		this.lstPendientesXAsociarBoleto=lstPendientesXAsociarBoleto;
	}
}
