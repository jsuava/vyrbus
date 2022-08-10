/**
 * Proyecto		: SISVYR
 * Sistema		: Sistema de Ventas y Reservas
 * Descripción	: 
 * Autor		: José Sullo Avalos
 * Fecha		: 28/03/2014
 */
package com.cystesoft.vyrbus.view.ctrl;


import org.zkoss.zul.Div;
import org.zkoss.zul.Iframe;
import org.zkoss.zul.Label;
import org.zkoss.zul.Toolbarbutton;

import com.cystesoft.vyrbus.service.util.Constantes;
import com.cystesoft.vyrbus.view.ui.WndBase;

/**
 * @author JABANTO
 *
 */
@SuppressWarnings("deprecation")
public class WndVideoTutorial extends WndBase {
	private Toolbarbutton tbnBoleta;
	private Toolbarbutton tbnFactura;
	private Toolbarbutton tbnReserva;
	private Toolbarbutton tbnConfReserva;
	
	private Iframe frame=new Iframe();
	private Div div=new Div();
	Label label=new Label();

	/**
	 * 
	 */
	
	private static final long serialVersionUID = 1L;
	
	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.view.ui.WndBase#initComponents()
	 */
	@Override
	public void initComponents() {
		tbnBoleta=(Toolbarbutton)this.getFellow("tbnBoleta");
		tbnFactura=(Toolbarbutton)this.getFellow("tbnFactura");
		tbnReserva=(Toolbarbutton)this.getFellow("tbnReserva");
		tbnConfReserva=(Toolbarbutton)this.getFellow("tbnConfReserva");
	}
	
	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.view.ui.WndBase#onCreate()
	 */
	@Override
	public void onCreate() throws Exception {
		// TODO Auto-generated method stub
		div.setWidth("970px");
		div.setAlign("center");
		
		label.setStyle(getStyleLabel());
		
		frame.setWidth("1024px");
		frame.setHeight("545px");
		
		tbnBoleta.setDisabled(false);
		tbnFactura.setDisabled(false);
		tbnReserva.setDisabled(false);
		tbnConfReserva.setDisabled(false);
		
//		if(getAgencia().getTipoAgencia().getId().intValue()==Constantes.ID_TIPAGE_VIAJES)
//			tbnBoleta.setDisabled(false);
//		else if(getAgencia().getTipoAgencia().getId().intValue()==Constantes.ID_TIPAGE_CORPORATIVO){
//			tbnFactura.setDisabled(false);
//		}else if(getAgencia().getTipoAgencia().getId().intValue()==Constantes.ID_TIPAGE_TEPSA){
//			tbnBoleta.setDisabled(false);
//			tbnFactura.setDisabled(false);
//		}
	}
	
	public void boletas() throws Exception{
		div.detach();
		frame.detach();
		
		label.setValue("::: VIDEO TUTORIAL PARA LA EMISION DE BOLETAS :::");
		div.appendChild(label);
		this.appendChild(div);
		
		frame.setSrc(Constantes.DIRECTORY_HTML+"videoAgenciaViajes.html");
		this.appendChild(frame);
	}
	
	public void facturas(){
		div.detach();
		frame.detach();
		
		label.setValue("::: VIDEO TUTORIAL PARA LA EMISION DE FACTURAS :::");
		div.appendChild(label);
		this.appendChild(div);
		
		frame.setSrc(Constantes.DIRECTORY_HTML+"videoCorporativos.html");
		this.appendChild(frame);
	}
	
	public void reservas(){
		div.detach();
		frame.detach();
		
		label.setValue("::: VIDEO TUTORIAL PARA LA REALIZACION DE RESERVAS :::");
		div.appendChild(label);
		this.appendChild(div);
		
		frame.setSrc(Constantes.DIRECTORY_HTML+"videoCorporativos.html");
		this.appendChild(frame);
	}
	
	public void confirmacionReserva(){
		div.detach();
		frame.detach();
		
		label.setValue("::: VIDEO TUTORIAL PARA CONFIRMACION DE RESERVAS :::");
		div.appendChild(label);
		this.appendChild(div);
		
		frame.setSrc(Constantes.DIRECTORY_HTML+"videoCorporativos.html");
		this.appendChild(frame);
	}
	
	private String getStyleLabel(){
		String style="font-weight: bold;color:red;font-size:14px !important;";
		
		return style;
	}
	
}
