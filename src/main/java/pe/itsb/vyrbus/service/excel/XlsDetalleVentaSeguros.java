/**
 * Proyecto		: SISVYR
 * Sistema		: Sistema de Ventas y Reservas
 * Descripción	:
 * Autor		: José Abanto
 * Fecha		: 17/11/2014
 * Hora			: 14:16:57
 */
package pe.itsb.vyrbus.service.excel;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Iterator;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listitem;

import pe.itsb.vyrbus.model.bean.VSAfiliacion;
import pe.itsb.vyrbus.service.util.Constantes;
import pe.itsb.vyrbus.service.util.Util;

/**
 * @author JABANTO
 *
 */
@SuppressWarnings({"rawtypes"})
public class XlsDetalleVentaSeguros extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException{
		doProcess(request, response);
	}

	protected void doProcess(HttpServletRequest request, HttpServletResponse response){
		Listbox listbox = (Listbox)request.getSession().getAttribute("lbxVentaSeguros");
        String parcialPath = (String)request.getSession().getAttribute("parcialPath");
        response.setContentType("application/vnd.ms-excel");
        response.setHeader("Content-Disposition", "attachment; filename=DetalladoVentaSeguros.xls");

        File template = new File(parcialPath);
        try {
        	POIFSFileSystem fs = new POIFSFileSystem(new FileInputStream(template));
			HSSFWorkbook wb = new HSSFWorkbook(fs);
			HSSFSheet sheet = wb.getSheetAt(0);
            HSSFRow rowh = null;

            List listItems = listbox.getItems();
            int j = 3;
            for (Iterator it = listItems.iterator(); it.hasNext();) {
                Listitem item = (Listitem) it.next();
                j++;
                VSAfiliacion afiliacion=item.getValue();
                rowh = sheet.createRow((short)j);
                rowh.createCell((short) 0).setCellValue(new HSSFRichTextString(String.valueOf(j-3)));
                rowh.createCell((short) 1).setCellValue(new HSSFRichTextString(Constantes.FORMAT_DATE.format(afiliacion.getFechaVenta())));
                rowh.createCell((short) 2).setCellValue(new HSSFRichTextString(afiliacion.getNumeroBoleto()!=null?afiliacion.getNumeroBoleto():""));
                rowh.createCell((short) 3).setCellValue(new HSSFRichTextString(afiliacion.getVsAsegurado().toString()));
                rowh.createCell((short) 4).setCellValue(new HSSFRichTextString(afiliacion.getVsAsegurado().getTipoPasajero()));
                rowh.createCell((short) 5).setCellValue(new HSSFRichTextString(afiliacion.getVsAsegurado().getNumeroDocumento()));
                rowh.createCell((short) 6).setCellValue(new HSSFRichTextString(afiliacion.getVsCiudad().getDenominacion()));
                rowh.createCell((short) 7).setCellValue(new HSSFRichTextString(afiliacion.getNumeroCertificado()));
                rowh.createCell((short) 8).setCellValue(new HSSFRichTextString(Constantes.FORMAT_DATE.format(afiliacion.getFechaVigenciaInicial())));
                rowh.createCell((short) 9).setCellValue(new HSSFRichTextString(Constantes.FORMAT_DATE.format(afiliacion.getFechaVigenciaFinal())));
                rowh.createCell((short) 10).setCellValue(Double.valueOf(Util.toNumberFormat(afiliacion.getImportePagado(), 2)));
                rowh.createCell((short) 11).setCellValue(new HSSFRichTextString(afiliacion.getAgencia()!=null?afiliacion.getAgencia().getNombreCorto():""));
                rowh.createCell((short) 12).setCellValue(new HSSFRichTextString(afiliacion.getUsuario().toString()));
                rowh.createCell((short) 13).setCellValue(new HSSFRichTextString(afiliacion.getFechaProcesoAfiliacion()!=null?Constantes.FORMAT_DATE.format(afiliacion.getFechaProcesoAfiliacion()):""));
            }

            ByteArrayOutputStream outByteStream = new ByteArrayOutputStream();
			wb.write(outByteStream);
			byte [] outArray = outByteStream.toByteArray();
			response.setContentLength(outArray.length);
			OutputStream outStream = response.getOutputStream();
		    outStream.write(outArray);
		    outStream.flush();

        } catch (Exception e) {
        	log("EXPORT XLS VENTA DE SEGUROS: "+e.toString());
            System.out.println(e.toString());
            e.printStackTrace();
        }
	}


}
