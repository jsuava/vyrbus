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
	private Toolbarbutton tbnAgencias;
	private Toolbarbutton tbnCorporativos;
	
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
		tbnAgencias=(Toolbarbutton)this.getFellow("tbnAgencias");
		tbnCorporativos=(Toolbarbutton)this.getFellow("tbnCorporativos");
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
		
		tbnAgencias.setDisabled(true);
		tbnCorporativos.setDisabled(true);
		
		
		if(getAgencia().getTipoAgencia().getId().intValue()==Constantes.ID_TIPAGE_VIAJES)
			tbnAgencias.setDisabled(false);
		else if(getAgencia().getTipoAgencia().getId().intValue()==Constantes.ID_TIPAGE_CORPORATIVO){
			tbnCorporativos.setDisabled(false);
		}else if(getAgencia().getTipoAgencia().getId().intValue()==Constantes.ID_TIPAGE_TEPSA){
			tbnAgencias.setDisabled(false);
			tbnCorporativos.setDisabled(false);
		}
	}
	
	public void agencias() throws Exception{
		div.detach();
		frame.detach();
		
		label.setValue("::: VIDEO TUTORIAL PARA LA EMISIÓN DE VOUCHERS EN UNA AGENCIA DE VIAJES :::");
		div.appendChild(label);
		this.appendChild(div);
		
		frame.setSrc(Constantes.DIRECTORY_HTML+"videoAgenciaViajes.html");
		this.appendChild(frame);
	}
	
	public void corporativos(){
		div.detach();
		frame.detach();
		
		label.setValue("::: VIDEO TUTORIAL PARA LA EMISIÓN DE VOUCHERS POR CLIENTES CORPORATIVOS :::");
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
