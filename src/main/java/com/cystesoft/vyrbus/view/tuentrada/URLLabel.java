/**
 * Proyecto		: SISVYR
 * Sistema		: Sistema de Ventas y Reservas
 * Descripción	: Clase utilizada para el applet de impresion.
 * Autor		: José Avalos Sullo
 * Fecha		: 09/06/2014
 */
package com.cystesoft.vyrbus.view.tuentrada;


import java.applet.Applet;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Label;
import java.awt.Rectangle;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.Serializable;
import java.net.URL;

public class URLLabel extends Label implements Serializable {
	private static final long serialVersionUID = 1L;
	private java.applet.Applet applet;
	private URL url;
	private String target = "";
	private Color unvisitedURL = Color.blue;
	private Color visitedURL = Color.green;

	public URLLabel(Applet applet , String url, String text){
		this(applet, url, text, "_blank");
	}

	public URLLabel(Applet applet , String url, String text, String target){
		super(text);
		setForeground(unvisitedURL);
		try {
			this.applet = applet;
			this.url = new URL(url);
			this.target = target;
			addMouseListener( new Clicked() );
		}catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void paint(Graphics g) {
		Rectangle r;
		super.paint(g);
		r = g.getClipBounds();
		g.drawLine(0,
				r.height - this.getFontMetrics(this.getFont()).getDescent(),
				this.getFontMetrics(this.getFont()).stringWidth(this.getText()),
				r.height - this.getFontMetrics(this.getFont()).getDescent());
	}

	public void setUnvisitedURLColor(Color c) {
		unvisitedURL = c;
	}

	public void setVisitedURLColor(Color c) {
		visitedURL = c;
	}

	class Clicked extends MouseAdapter{
		@Override
		public void mouseClicked(MouseEvent me){
			 setForeground(visitedURL);
			 applet.getAppletContext().showDocument(url, target);
		}
	}
}
