/**
 * Proyecto		: SISVYR
 * Sistema		: Sistema de Ventas y Reservas
 * Descripción	:
 * Autor		: José Abanto
 * Fecha		: 11/05/2013
 */
package com.cystesoft.vyrbus.view.ctrl;


import java.io.FileOutputStream;
import java.util.Date;
import java.util.List;

import org.zkoss.zul.Datebox;
import org.zkoss.zul.Iframe;
import org.zkoss.zul.Textbox;

import com.cystesoft.vyrbus.model.bean.PasajeroFrecuente;
import com.cystesoft.vyrbus.model.bean.PuntosPasajeroFrecuente;
import com.cystesoft.vyrbus.model.bean.Ruta;
import com.cystesoft.vyrbus.service.exceptions.NumeroDocumentoNullException;
import com.cystesoft.vyrbus.service.locator.ServiceLocator;
import com.cystesoft.vyrbus.service.util.Constantes;
import com.cystesoft.vyrbus.service.util.Messages;
import com.cystesoft.vyrbus.service.util.MyTime;
import com.cystesoft.vyrbus.service.util.Util;
import com.cystesoft.vyrbus.view.ui.DlgMessage;
import com.cystesoft.vyrbus.view.ui.WndBase;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

/**
 * @author JABANTO
 *
 */
public class WndEstadoCuentaPuntosPaxFree extends WndBase {
	private static final long serialVersionUID = 1L;

	private Iframe iframe= new Iframe();
	private Datebox dtbFechaInicio;
	private Datebox dtbFechaFin;
	private Textbox txtDocumentoPaxfree;

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.view.ui.WndBase#initComponents()
	 */
	@Override
	public void initComponents(){
		dtbFechaInicio=(Datebox)this.getFellow("dtbFechaInicio");
		dtbFechaFin=(Datebox)this.getFellow("dtbFechaFin");
		txtDocumentoPaxfree=(Textbox)this.getFellow("txtDocumentoPaxfree");
	}

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.view.ui.WndBase#onCreate()
	 */
	@Override
	public void onCreate() throws Exception {
		MyTime myTime= new MyTime();
		Long lFechaInicio=Constantes.FORMAT_DATE.parse(myTime.dateServer()).getTime();
		lFechaInicio+= -(Constantes.MILISEGUNDOS_X_DIA*90);
		Date dfechaInicio= new Date(lFechaInicio);
		dtbFechaInicio.setValue(dfechaInicio);
		dtbFechaFin.setValue(Constantes.FORMAT_DATE.parse(myTime.dateServer()));

		txtDocumentoPaxfree.setFocus(true);
	}


	public void consultaEstadoCuenta() throws Exception{
		try{
			iframe.detach();

			/*Busca Pasajero Frecuente*/
			if(txtDocumentoPaxfree.getText().trim().isEmpty())
				throw new NumeroDocumentoNullException();
			PasajeroFrecuente pasajeroFrecuente=ServiceLocator.getPasajeroFrecuenteManager().buscarPaxfreeXNumeroDocumento(txtDocumentoPaxfree.getText().trim().toUpperCase());
			if(pasajeroFrecuente!=null){
				Long idPaxfree=pasajeroFrecuente.getId();
				String fechaInico=Constantes.FORMAT_DATE.format(dtbFechaInicio.getValue());
				String fechaFin=Constantes.FORMAT_DATE.format(dtbFechaFin.getValue());
				List<PuntosPasajeroFrecuente> lstPuntosPaxfree=ServiceLocator.getPuntosPasajeroFrecuenteManager().buscaEstadoCuentaPaxFree(idPaxfree, fechaInico, fechaFin);

				if(lstPuntosPaxfree.size()>0){
					String FILES =Constantes.DIRECTORY_PDF+"EstadoCuentaPuntos.pdf";
					Document document = new Document();
					PdfWriter.getInstance(document, new FileOutputStream(FILES));
					document.open();
					addContent(document, lstPuntosPaxfree);
					document.close();

					String src=Constantes.URL_FORMATOS_PDF+"EstadoCuentaPuntos.pdf";
					iframe.setSrc(src);
					iframe.setHeight("500px");
					iframe.setWidth("900px");
					this.appendChild(iframe);
				}
			}else{
				DlgMessage.information(Messages.getString("WndEstadoCuentaPuntos.Information.noPasajero"));
				txtDocumentoPaxfree.setFocus(true);
			}
		}catch (NumeroDocumentoNullException ndn){
			DlgMessage.information(Messages.getString("WndPersonal.information.NumeroDocuento"));
			txtDocumentoPaxfree.setFocus(true);
		}
	}


	private void addContent(Document document, List<PuntosPasajeroFrecuente> lstPuntosPaxfree) throws DocumentException {
		PuntosPasajeroFrecuente puntospaxfree=lstPuntosPaxfree.get(0);
//		Double asignados=.00;
//		Double canjeados=.00;
//		for(PuntosPasajeroFrecuente pPaxfree: lstPuntosPaxfree){
//			if(pPaxfree.getFechaCanje()!=null)
//				canjeados+=+pPaxfree.getPuntosAcumulados();
//
//			asignados+=pPaxfree.getPuntosAcumulados();
//		}


		Font fontCat = new Font(Font.FontFamily.TIMES_ROMAN, 8, Font.BOLD);
		Font fontDet = new Font(Font.FontFamily.TIMES_ROMAN, 8, Font.NORMAL);
		Font fontNumDate = new Font(Font.FontFamily.TIMES_ROMAN, 9, Font.NORMAL);
		String line="______________________________________________________________________________";

		Paragraph title= new Paragraph();
		title.add(new Paragraph("ESTADO DE CUENTA",new Font(Font.FontFamily.TIMES_ROMAN, 9,Font.getStyleValue("font-weight: bold !important;font-weight: underline;"))));
		title.setAlignment(Element.ALIGN_CENTER);
		addEmptyLine(title,1);
		document.add(title);

		Paragraph cab= new Paragraph();
		cab.add(new Paragraph("NRO. TARJETA : ",fontCat));
		cab.setAlignment(Element.ALIGN_LEFT);
		document.add(cab);
		cab= new Paragraph();cab.setMultipliedLeading(0);cab.setIndentationLeft(70);
		cab.add(new Paragraph(puntospaxfree.getPasajeroFrecuente().getNumeroTarjeta(),fontNumDate));
		document.add(cab);

		cab= new Paragraph();cab.setMultipliedLeading(1);
		cab.add(new Paragraph("TITULAR :",fontCat));
		cab.setIndentationLeft(22);
		document.add(cab);
		cab= new Paragraph();cab.setMultipliedLeading(0);cab.setIndentationLeft(70);
		cab.add(new Paragraph(puntospaxfree.getPasajeroFrecuente().getPasajero().getNombresApellidos(),fontDet));
		document.add(cab);

		/*Linea*/
		cab= new Paragraph();
		cab.add(new Paragraph(line));document.add(cab);

		cab= new Paragraph();cab.setMultipliedLeading(1);
		cab.add(new Paragraph("SALDO INICIAL DE PUNTOS :",fontCat));
		cab.setIndentationLeft(300);document.add(cab);
		cab= new Paragraph();cab.setMultipliedLeading(0);cab.setAlignment(Element.ALIGN_RIGHT);
		cab.add(new Paragraph(Util.toNumberFormat(puntospaxfree.getSaldoInicial(),2),fontNumDate));
		cab.setIndentationLeft(430);document.add(cab);

		cab= new Paragraph();cab.setMultipliedLeading(1);
		cab.add(new Paragraph("ASIGNADOS :",fontCat));
		cab.setIndentationLeft(361);document.add(cab);
		cab= new Paragraph();cab.setMultipliedLeading(0);cab.setAlignment(Element.ALIGN_RIGHT);
		cab.add(new Paragraph(Util.toNumberFormat(puntospaxfree.getTotalAsignado(),2),fontNumDate));
		cab.setIndentationLeft(430);document.add(cab);

		cab= new Paragraph();cab.setMultipliedLeading(1);
		cab.add(new Paragraph("CANJEADOS :",fontCat));
		cab.setIndentationLeft(360);document.add(cab);
		cab= new Paragraph();cab.setMultipliedLeading(0);cab.setAlignment(Element.ALIGN_RIGHT);
		cab.add(new Paragraph(Util.toNumberFormat(puntospaxfree.getTotalCajeados(),2),fontNumDate));
		cab.setIndentationLeft(430);document.add(cab);

		cab= new Paragraph();cab.setMultipliedLeading(1);
		cab.add(new Paragraph("SALDO  AL "+dtbFechaFin.getText()+" :",fontCat));
		cab.setIndentationLeft(328);document.add(cab);
		cab= new Paragraph();cab.setMultipliedLeading(0);cab.setAlignment(Element.ALIGN_RIGHT);
		cab.add(new Paragraph(Util.toNumberFormat(puntospaxfree.getSaldoActual(),2),fontNumDate));
		cab.setIndentationLeft(430);document.add(cab);

		cab= new Paragraph();cab.setMultipliedLeading(1);
		cab.add(new Paragraph("DISPONIBLE PARA CANJE :",fontCat));
		cab.setIndentationLeft(305);document.add(cab);
		cab= new Paragraph();cab.setMultipliedLeading(0);cab.setAlignment(Element.ALIGN_RIGHT);
		cab.add(new Paragraph(Util.toNumberFormat(puntospaxfree.getSaldoActual(),2),fontNumDate));
		cab.setIndentationLeft(430);document.add(cab);

		/*Linea*/
		cab= new Paragraph();cab.setMultipliedLeading(0);
		cab.add(new Paragraph(line));
		document.add(cab);

		/*----*/
		cab= new Paragraph();cab.setMultipliedLeading(-2);
		cab.add(new Paragraph("FECHA DEL ESTADO DE CUENTA",fontCat));
		document.add(cab);

		cab= new Paragraph();cab.setMultipliedLeading(1);
		cab.add(new Paragraph(dtbFechaInicio.getText()+" - "+dtbFechaFin.getText(),fontDet)); document.add(cab);
		/*----*/

		cab= new Paragraph();cab.setMultipliedLeading(3);
		cab.add(new Paragraph(" ",fontCat));
		document.add(cab);

		PdfPTable table = new PdfPTable(5);
		float[] columnWidths = new float[] {13f, 9f, 18f, 22f,10f};
		table.setWidths(columnWidths);
		table.setWidthPercentage(100);

		/* Cabecera */
		//transaccion
		PdfPCell c1 = new PdfPCell(new Phrase("TRANSACCIÓN",fontCat));
		c1.setBorderWidthTop(0);c1.setBorderWidthLeft(0);c1.setBorderWidthRight(0);
		table.addCell(c1);
		//Nro Boleto
		c1 = new PdfPCell(new Phrase("NRO.BOLETO",fontCat));
		c1.setHorizontalAlignment(Element.ALIGN_LEFT);
		c1.setBorderWidthTop(0);c1.setBorderWidthLeft(0);c1.setBorderWidthRight(0);
		table.addCell(c1);
		//Ruta
		c1 = new PdfPCell(new Phrase("RUTA",fontCat));
		c1.setBorderWidthTop(0);c1.setBorderWidthLeft(0);c1.setBorderWidthRight(0);
		table.addCell(c1);
		//Agencia
		c1 = new PdfPCell(new Phrase("AGENCA",fontCat));
		c1.setBorderWidthTop(0);c1.setBorderWidthLeft(0);c1.setBorderWidthRight(0);
		table.addCell(c1);
		//Puntos Asignados
		c1 = new PdfPCell(new Phrase("PUNTOS ASIGNADOS",fontCat));
		c1.setHorizontalAlignment(Element.ALIGN_RIGHT);
		c1.setBorderWidthTop(0);c1.setBorderWidthLeft(0);c1.setBorderWidthRight(0);
		table.addCell(c1);

		/* Detalle */
		Double total=(double) 0;
		for(PuntosPasajeroFrecuente dpaxfree: lstPuntosPaxfree){
			//Transacción
			PdfPCell cd=null;
			cd = new PdfPCell(new Phrase(Constantes.FORMAT_DATE_TIME_24H.format(dpaxfree.getFechaInsercion()).toString(),fontNumDate));
			cd.setBorder(0);cd.setHorizontalAlignment(Element.ALIGN_LEFT);
			table.addCell(cd);
			//Nro.Boleto
			cd = new PdfPCell(new Phrase(dpaxfree.getVentaPasaje().getNumeroBoleto(),fontNumDate));
			cd.setBorder(0);cd.setHorizontalAlignment(Element.ALIGN_LEFT);
			table.addCell(cd);
			//RUTA
			Ruta ruta= dpaxfree.getVentaPasaje().getRuta();
			cd = new PdfPCell(new Phrase(ruta.getOrigen()+" - "+ruta.getDestino(),fontDet));
			cd.setBorder(0);cd.setHorizontalAlignment(Element.ALIGN_LEFT);
			table.addCell(cd);
			//Agencia
			cd = new PdfPCell(new Phrase(dpaxfree.getVentaPasaje().getAgencia().getDenominacion(),fontDet));
			cd.setBorder(0);cd.setHorizontalAlignment(Element.ALIGN_LEFT);
			table.addCell(cd);
			//Puntos ganados
			cd = new PdfPCell(new Phrase(Util.toNumberFormat(dpaxfree.getPuntosAcumulados(),2),fontNumDate));
			cd.setBorder(0);cd.setHorizontalAlignment(Element.ALIGN_RIGHT);
			table.addCell(cd);
			//realiza la sumatoria para el total de puntos.
			total+=+dpaxfree.getPuntosAcumulados();
		}

		table.completeRow();
		document.add(table);

		cab= new Paragraph();cab.setMultipliedLeading(0);
		cab.add(new Paragraph(line));
		document.add(cab);

		cab= new Paragraph();cab.setMultipliedLeading(1);
		cab.add(new Paragraph("TOTAL",fontCat)); cab.setIndentationLeft(420);document.add(cab);
		cab= new Paragraph();cab.setMultipliedLeading(0);
		String stotal=Util.toNumberFormat(total,2);
		cab.add(new Paragraph(stotal,fontNumDate));
//		cab.add(new Paragraph(Util.toNumberFormat(puntospaxfree.getTotalAsignado(),2),fontNumDate));
		cab.setIndentationLeft(500-stotal.length());
		document.add(cab);

		cab= new Paragraph();cab.setMultipliedLeading(0);
		cab.add(new Paragraph(line));
		document.add(cab);

	}

	private static void addEmptyLine(Paragraph paragraph, int number) {
		for (int i = 0; i < number; i++) {
		      paragraph.add(new Paragraph(" "));
		}
	}




}
