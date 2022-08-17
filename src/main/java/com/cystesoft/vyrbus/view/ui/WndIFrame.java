package com.cystesoft.vyrbus.view.ui;

import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zul.Button;
import org.zkoss.zul.Grid;
import org.zkoss.zul.Iframe;
import org.zkoss.zul.Row;
import org.zkoss.zul.Rows;
import org.zkoss.zul.Window;

/**
 *
 * @author JABANTO
 *
 */
public class WndIFrame extends WndBase{
	private static final long serialVersionUID = -7136088649832461332L;

	public Window oThisWindow = this;
	public Button btnCerrar = new Button();

	private String src=null;
	private String width=null;
	private String height=null;

	/**
	 * Constructor
	 * @throws Exception
	 */
	public WndIFrame() throws Exception {
		super();
		this.initComponents();
		onCreate();
	}


	@Override
	public void initComponents() {
		this.setMaximizable(false);
		this.setMinimizable(false);
		this.setSizable(false);
		this.setClosable(false);
		this.setVisible(true);


	}

	@SuppressWarnings("deprecation")
	public void loadiframe(){
		Grid grid = new Grid();
		Rows rows = new Rows();
		Row row = new Row();


		int iwith= Integer.parseInt(width);
		iwith += 5;
		grid.setWidth(Integer.toString(iwith)+"px");

		Iframe iframe = new Iframe();
		iframe.setWidth(width+"px");
		iframe.setHeight(height+"px");
		iframe.setSrc(src);
		iframe.setStyle("border: 1px solid gray");

		btnCerrar.setLabel("Cerrar");
		btnCerrar.setHeight("30px");
		btnCerrar.setSclass("btnCommandM");
		btnCerrar.setSrc("resources/mp_cerrar.png");

		btnCerrar.addEventListener(Events.ON_CLICK, new EventListener<Event>() {
			@Override
			public void onEvent(Event event) throws Exception {
				oThisWindow.onClose();

			}

		});


		row.appendChild(iframe);
		rows.appendChild(row);

		row = new Row();
		row.appendChild(btnCerrar);
		row.setAlign("right");
		rows.appendChild(row);

		grid.appendChild(rows);
		appendChild(grid);

	}

	public void setSrc(String src){
		this.src=src;
	}
	public String getSrc(){
		return src;
	}

	@Override
	public void setWidth(String width){
		this.width=width;
	}
	@Override
	public String getWidth(){
		return width;
	}

	public void setheight(String height){
		this.height=height;
	}
	public String getheight(){
		return height;
	}

}
