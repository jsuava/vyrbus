package com.cystesoft.vyrbus.view.ui;

import java.io.Serializable;

import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zul.Button;
import org.zkoss.zul.Column;
import org.zkoss.zul.Columns;
import org.zkoss.zul.Grid;
import org.zkoss.zul.Label;
import org.zkoss.zul.Row;
import org.zkoss.zul.Rows;
import org.zkoss.zul.Separator;
import org.zkoss.zul.Window;

import com.cystesoft.vyrbus.model.bean.VentaPasaje;
import com.cystesoft.vyrbus.service.mappers.Asiento;
import com.cystesoft.vyrbus.service.util.Constantes;
import com.cystesoft.vyrbus.service.util.Util;

/**
 * 
 * @author JABANTO
 *
 */
public class WndVerPasajero extends WndBase implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Window oThisWindow = this;
	
	private Label lbAsiento=new Label();
	private Label lbEstado=new Label();
	private Label lbPasajero=new Label();
	private Label lbNumDocumento=new Label();
	private Label lbEdad=new Label();
	private Label lbSexo=new Label();
	private Label lbOrigen=new Label();
	private Label lbDestino=new Label();
	private Label lbNumControl=new Label();
	private Label lbNumBoleto=new Label();
	
	private VentaPasaje ventaPasaje=null;
	private Asiento asiento=null;
	
	/**
	 * Constructor
	 * @throws Exception
	 */
	public WndVerPasajero() throws Exception{
		super();
		initComponents();
		//onCreate();
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.tepsa.sisvyr.view.ui.WndBase#initComponents()
	 */
	@Override
	public void initComponents() {
		this.setMaximizable(false);
		this.setMinimizable(false);
		this.setSizable(false);
		this.setClosable(false);
		this.setStyle("padding: 5px");
		this.setWidth("430px");
		this.setVisible(true);
			
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.tepsa.sisvyr.view.ui.WndBase#onCreate()
	 */
	@SuppressWarnings("deprecation")
	@Override
	public void onCreate() throws Exception {
		Grid grid = new Grid();
		Rows rows = new Rows();
		Row row = new Row();
		
		Columns columns= new Columns();
		Column column= new Column();
		column.setWidth("100px");
		columns.appendChild(column);
		
		
		Label label= new Label();
		label.setValue("Nro. Asiento");
		label.setSclass("label-size11");
		lbAsiento.setValue(": "+getAsiento().getNumeroAsiento().toString()); //getVentaPasaje()==null?": ": ": "+getVentaPasaje().getNumeroAsiento().toString());
		lbAsiento.setSclass("label-size11");
		lbAsiento.setStyle("color:blue");
		row.appendChild(label);
		row.appendChild(lbAsiento);
		rows.appendChild(row);
		
		row = new Row();
		label=new Label();
		label.setValue("Estado del Asiento");
		label.setSclass("label-size11");
		String estado="";
		switch (getAsiento().getEstadoAsiento()) {
			case 0:
				estado=Constantes.DISPONIBLE;	
				break;
			case 1:
				estado=Constantes.VENDIDO;
				break;
			case 2:
				estado=Constantes.RESERVADO;
				break;
			case 3:
				estado=Constantes.BLOQUEADO;
				break;
		}
		lbEstado.setSclass("label-size11");
		lbEstado.setStyle("color:blue");
		lbEstado.setValue(": "+estado);
		row.appendChild(label);
		row.appendChild(lbEstado);
		rows.appendChild(row);
		
		row = new Row();
		label=new Label();
		label.setValue("Pasajero");
		label.setSclass("label-size11");
		lbPasajero.setValue(getVentaPasaje()==null?": ": ": "+getVentaPasaje().getPasajero().toString());
		lbPasajero.setSclass("label-size11");
		lbPasajero.setStyle("color:blue");
		row.appendChild(label);
		row.appendChild(lbPasajero);
		rows.appendChild(row);
		
		row = new Row();
		label=new Label();
		label.setValue("Nro. Documento");
		label.setSclass("label-size11");
		lbNumDocumento.setValue(getVentaPasaje()==null?": ": ": "+getVentaPasaje().getPasajero().getNumeroDocumento()==null?": ":": "+getVentaPasaje().getPasajero().getNumeroDocumento());
		lbNumDocumento.setSclass("label-size11");
		lbNumDocumento.setStyle("color:blue");
		row.appendChild(label);
		row.appendChild(lbNumDocumento);
		rows.appendChild(row);
		
		row = new Row();
		label=new Label();
		label.setValue("Edad");
		label.setSclass("label-size11");
		String edad= getVentaPasaje()==null?"": ""+Util.calculaEdad(getVentaPasaje().getPasajero().getFechaNacimiento());
		lbEdad.setValue(edad.equals("0")?":": ": "+ edad);
		lbEdad.setSclass("label-size11");	
		lbEdad.setStyle("color:blue");
		row.appendChild(label);
		row.appendChild(lbEdad);
		rows.appendChild(row);
		
		row = new Row();
		label=new Label();
		label.setValue("Sexo");
		label.setSclass("label-size11");
		lbSexo.setValue(getVentaPasaje()==null?": ": ": "+getVentaPasaje().getPasajero().getSexo().getDenominacion());
		lbSexo.setSclass("label-size11");
		lbSexo.setStyle("color:blue");
		row.appendChild(label);
		row.appendChild(lbSexo);
		rows.appendChild(row);
		
		row = new Row();
		label=new Label();
		label.setValue("Origen");
		label.setSclass("label-size11");
		lbOrigen.setValue(getVentaPasaje()==null?": ": ": "+getVentaPasaje().getRuta().getOrigen());
		lbOrigen.setSclass("label-size11");
		lbOrigen.setStyle("color:blue");
		row.appendChild(label);
		row.appendChild(lbOrigen);
		rows.appendChild(row);
		
		row = new Row();
		label=new Label();
		label.setValue("Destino");
		label.setSclass("label-size11");
		lbDestino.setValue(getVentaPasaje()==null?": ": ": "+getVentaPasaje().getRuta().getDestino());
		lbDestino.setSclass("label-size11");
		lbDestino.setStyle("color:blue");
		row.appendChild(label);
		row.appendChild(lbDestino);
		rows.appendChild(row);
		
		row = new Row();
		label=new Label();
		label.setValue("Nro. Control");
		label.setSclass("label-size11");
		lbNumControl.setValue(getVentaPasaje()==null?": ": getVentaPasaje().getNumeroControl()==null?": ":": "+getVentaPasaje().getNumeroControl());
		lbNumControl.setSclass("label-size11");
		lbNumControl.setStyle("color:blue");
		row.appendChild(label);
		row.appendChild(lbNumControl);
		rows.appendChild(row);
		
		row = new Row();
		label=new Label();
		label.setValue("Nro. Boleto");
		label.setSclass("label-size11");
		lbNumBoleto.setValue(getVentaPasaje()==null?": ":getVentaPasaje().getNumeroBoleto()==null?": ":": "+getVentaPasaje().getNumeroBoleto());
		lbNumBoleto.setSclass("label-size11");
		lbNumBoleto.setStyle("color:blue");
		row.appendChild(label);
		row.appendChild(lbNumBoleto);
		rows.appendChild(row);
		
		Button btnCerrar = new Button();
		btnCerrar.setLabel("Cerrar");
//		btnCerrar.setHeight("30px");
		btnCerrar.setClass("btnCommandM");
		btnCerrar.setSrc("resources/mp_cerrar.png");
		
		btnCerrar.addEventListener(Events.ON_CLICK, new EventListener<Event>() {
			@Override
			public void onEvent(Event event) throws Exception {
				oThisWindow.onClose();
			}
		});
		
		//row.appendChild();
		//rows.appendChild(row);
		
		row = new Row();
		row.appendChild(new Separator());
		row.appendChild(btnCerrar);
		row.setAlign("right");
		rows.appendChild(row);
		
		grid.appendChild(columns);
		grid.appendChild(rows);
		appendChild(grid);
	}
	
	public VentaPasaje getVentaPasaje(){
		return ventaPasaje;
	}
	
	public void setVentaPasaje(VentaPasaje ventaPasaje){
		this.ventaPasaje=ventaPasaje;
	}
	
	public Asiento getAsiento(){
		return asiento;
	}
	
	public void setAsiento(Asiento asiento){
		this.asiento=asiento;
	}
}
