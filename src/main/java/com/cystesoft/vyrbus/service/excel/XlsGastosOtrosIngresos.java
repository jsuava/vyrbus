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
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listhead;
import org.zkoss.zul.Listheader;
import org.zkoss.zul.Listitem;

//ITSB001 06/02/2022
//MAOE: Se agrego la funcion que reemplaza la coma por na pues daba un error al convertir de cadena a float.

@SuppressWarnings({"rawtypes"})
public class XlsGastosOtrosIngresos extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException{
		doProcess(request, response);
	}
	
	protected void doProcess(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException{
		Listbox listbox = (Listbox)request.getSession().getAttribute("lbxGastosOtrosIngresos");
        String parcialPath = (String)request.getSession().getAttribute("parcialPath");
        response.setContentType("application/vnd.ms-excel");
        response.setHeader("Content-Disposition", "attachment; filename=GastosOtrosIngresos.xls");
        
        File template = new File(parcialPath);
        try {
//            Workbook workbook = Workbook.getWorkbook(template);
//            WritableWorkbook w = Workbook.createWorkbook(response.getOutputStream(), workbook);
//            WritableSheet s = w.getSheet(0);
        	
        	POIFSFileSystem fs = new POIFSFileSystem(new FileInputStream(template));
			HSSFWorkbook wb = new HSSFWorkbook(fs);
			HSSFSheet sheet = wb.getSheetAt(0);
            HSSFRow rowh = null;
            HSSFCell cellh = null; 
            HSSFDataFormat format = wb.createDataFormat();
            HSSFCellStyle style = wb.createCellStyle();
            style.setDataFormat(format.getFormat("#,##0.00"));
        	
            
            HSSFCellStyle heardersStyle = wb.createCellStyle();
            HSSFFont headersFont = wb.createFont();
            headersFont.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
            heardersStyle.setFont(headersFont);
            
            Listhead head = listbox.getListhead();
            List listItems = listbox.getItems();
            int i = 0;
            int j = 5;
            for (Iterator it = head.getChildren().iterator(); it.hasNext();) {
                Listheader header = (Listheader) it.next();
                rowh = sheet.createRow((short)j);
                rowh.createCell((short)i).setCellStyle(heardersStyle);
				rowh.createCell((short)i).setCellValue(new HSSFRichTextString(header.getLabel()));
                i++;
            }
//            j++;
            for (Iterator it = listItems.iterator(); it.hasNext();) {
                Listitem item = (Listitem) it.next();
                j++;
                i = 0;
                for (Iterator it2 = item.getChildren().iterator(); it2.hasNext();) {
                    Listcell currentCell = (Listcell) it2.next();
                    rowh = sheet.createRow((short)j);
                     
//                    if (Util.isNumeric(currentCell.getLabel())) {
                    if(i==8) {
                    	cellh = rowh.createCell((short)i);
						cellh.setCellStyle(style);
						cellh.setCellValue(Double.parseDouble(currentCell.getLabel().replace(",", ""))); //ITSB001                    	
                    } else {
                        rowh.createCell((short) i).setCellValue(new HSSFRichTextString(currentCell.getLabel()));
                    }
                    i++;
                }
            }
//            w.write();
//            w.close(); 
            
            ByteArrayOutputStream outByteStream = new ByteArrayOutputStream();
			wb.write(outByteStream);
			byte [] outArray = outByteStream.toByteArray();
			response.setContentLength(outArray.length);
			OutputStream outStream = response.getOutputStream();
		    outStream.write(outArray);
		    outStream.flush();
		    
        } catch (Exception e) {
        	log("EXPORT XLS AVANCE SEMANAL: "+e.toString());
            System.out.println(e.toString());
            e.printStackTrace();
        }
	}
}
