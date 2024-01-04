/**
 * Proyecto		: SISVYR
 * Sistema		: Sistema de Ventas y Reservas
 * Descripción	: 
 * Autor		: José Abanto
 * Fecha		: 16 set. 2022
 * Hora			: 15:22:46
 */
package com.cystesoft.vyrbus.service.excel;

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

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFDataFormat;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;

import com.cystesoft.vyrbus.service.mappers.VentasPiloto;

/**
 * @author Marco
 *
 */
@SuppressWarnings({"rawtypes"})
public class XlsRegistroVentas extends HttpServlet {

	private static final long serialVersionUID = 1L;
	
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException{
		doProcess(request, response);
	}

	protected void doProcess(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException{
		@SuppressWarnings("unchecked")
		List<VentasPiloto> lstVentas = (List<VentasPiloto>)request.getSession().getAttribute("lstVentas");
		String empresa = (String)request.getSession().getAttribute("empresa");
		String ruc = (String)request.getSession().getAttribute("ruc");
		String desde = (String)request.getSession().getAttribute("desde");
		String hasta = (String)request.getSession().getAttribute("hasta");
		String rubro = (String)request.getSession().getAttribute("rubro");
		String fechaEmision = (String)request.getSession().getAttribute("fechaEmision");
		//16/09/2022 22:42:09
		String cadenaFecha =fechaEmision.replace('/', '-');
		cadenaFecha =cadenaFecha.replace(' ', '_');
		cadenaFecha =cadenaFecha.replace(':', '_');
		String fileName = "RegistroVentas_"+cadenaFecha+".xls";
		
        String parcialPath = (String)request.getSession().getAttribute("parcialPath");
        response.setContentType("application/vnd.ms-excel");
        response.setHeader("Content-Disposition", "attachment; filename="+fileName);

        File template = new File(parcialPath);
        try {
        	POIFSFileSystem fs = new POIFSFileSystem(new FileInputStream(template));
			HSSFWorkbook wb = new HSSFWorkbook(fs);
			HSSFSheet sheet = wb.getSheetAt(0);
            HSSFRow rowh = null;
            HSSFCell cellh = null;
            HSSFDataFormat format = wb.createDataFormat();
            HSSFCellStyle style = wb.createCellStyle();
            style.setDataFormat(format.getFormat("#,##0.00"));
            HSSFCellStyle styleFecha = wb.createCellStyle();
            styleFecha.setDataFormat(format.getFormat("dd/MM/yyyy"));
            
            //Escribir cabecera
            //(3, 2) ruc
            rowh = sheet.createRow((short)3);
			cellh = rowh.createCell((short)2);
			cellh.setCellValue(new HSSFRichTextString(ruc));
            //(3, 7) empresa
			cellh = rowh.createCell((short)7);
			cellh.setCellValue(new HSSFRichTextString(empresa));
            //(4, 2) desde
			rowh = sheet.createRow((short)4);
			cellh = rowh.createCell((short)2);
			cellh.setCellValue(new HSSFRichTextString(desde));
            //(4, 7) hasta
			cellh = rowh.createCell((short)7);
			cellh.setCellValue(new HSSFRichTextString(hasta));
            //(4, 10) rubro
			cellh = rowh.createCell((short)10);
			cellh.setCellValue(new HSSFRichTextString(rubro));

            int j = 6;
            for (Iterator it = lstVentas.iterator(); it.hasNext();) {
                VentasPiloto regVenta = (VentasPiloto) it.next();
                j++;
                rowh = sheet.createRow(j);
                cellh = rowh.createCell((short)0);
                cellh.setCellStyle(styleFecha);
				cellh.setCellValue(regVenta.getFechaCompra());

				cellh = rowh.createCell((short)1);
				cellh.setCellValue(new HSSFRichTextString(regVenta.getTipoDocumentoSunat()));

				cellh = rowh.createCell((short)2);
				cellh.setCellValue(new HSSFRichTextString(regVenta.getSerie()));

				cellh = rowh.createCell((short)3);
				cellh.setCellValue(new HSSFRichTextString(regVenta.getNumero()));

				cellh = rowh.createCell((short)4);
				cellh.setCellValue(new HSSFRichTextString(regVenta.getDni()));

				cellh = rowh.createCell((short)5);
				cellh.setCellValue(new HSSFRichTextString(regVenta.getNombres()));

				cellh = rowh.createCell((short)6);
				cellh.setCellStyle(style);
				cellh.setCellValue(regVenta.getExonerado());

				cellh = rowh.createCell((short)7);
				cellh.setCellStyle(style);
				cellh.setCellValue(regVenta.getVenta());

				cellh = rowh.createCell((short)8);
				cellh.setCellStyle(style);
				cellh.setCellValue(regVenta.getIgv());

				cellh = rowh.createCell((short)9);
				cellh.setCellStyle(style);
				cellh.setCellValue(regVenta.getTotal());

				cellh = rowh.createCell((short)10);
				cellh.setCellValue(new HSSFRichTextString(regVenta.getDestino()));

				cellh = rowh.createCell((short)11);
				cellh.setCellValue(new HSSFRichTextString(regVenta.getAsiento()));
            }

            ByteArrayOutputStream outByteStream = new ByteArrayOutputStream();
			wb.write(outByteStream);
			byte [] outArray = outByteStream.toByteArray();
			response.setContentLength(outArray.length);
			OutputStream outStream = response.getOutputStream();
		    outStream.write(outArray);
		    outStream.flush();

        } catch (Exception e) {
        	log("EXPORT XLS REGISTRO DE VENTAS: "+e.toString());
            System.out.println(e.toString());
            e.printStackTrace();
        }
	}

}
