/**
 * Proyecto		: SISVYR
 * Sistema		: Sistema de Ventas y Reservas
 * Descripción	: 
 * Autor		: José Abanto
 * Fecha		: 24 ene. 2023
 * Hora			: 15:00:26
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
import org.zkoss.zk.ui.Component;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listhead;
import org.zkoss.zul.Listheader;
import org.zkoss.zul.Listitem;

import com.cystesoft.vyrbus.service.mappers.VentasPiloto;

/**
 * @author Marco
 *
 */
@SuppressWarnings({"rawtypes"})
public class XlsReporteGeneralVentas extends HttpServlet  {

	private static final long serialVersionUID = 1L;
	
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException{
		doProcess(request, response);
	}

	protected void doProcess(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException{
		@SuppressWarnings("unchecked")
		Listbox lbxVentas = (Listbox)request.getSession().getAttribute("lbxVentas");
		String oficina = (String)request.getSession().getAttribute("oficina");
		String desde = (String)request.getSession().getAttribute("desde");
		String hasta = (String)request.getSession().getAttribute("hasta");
//		String rubro = (String)request.getSession().getAttribute("rubro");
		String fechaEmision = (String)request.getSession().getAttribute("fechaEmision");
		//16/09/2022 22:42:09
		String cadenaFecha =fechaEmision.replace('/', '-');
		cadenaFecha =cadenaFecha.replace(' ', '_');
		cadenaFecha =cadenaFecha.replace(':', '_');
		String fileName = "ReporteGeneralVentas_"+cadenaFecha+".xls";
		
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
            //(3, 2) oficina
            rowh = sheet.createRow((short)3);
			cellh = rowh.createCell((short)2);
			cellh.setCellValue(new HSSFRichTextString(oficina));
            //(4, 2) desde
			rowh = sheet.createRow((short)4);
			cellh = rowh.createCell((short)2);
			cellh.setCellValue(new HSSFRichTextString(desde));
            //(4, 7) hasta
			cellh = rowh.createCell((short)7);
			cellh.setCellValue(new HSSFRichTextString(hasta));

            int j = 7;
            String temp="";
            String valor="";
            int pos=0;
            int pos1=0;
            
            for (Component element : lbxVentas.getChildren()) {
    			
            	if(element.getClass().isInstance(new Listitem())){
    				/*Genera el Detalle*/
    				Listitem oListitem = (Listitem) element;
	                j++;
	                rowh = sheet.createRow((short)j);
	                
    				for(int i = 0; i < oListitem.getChildren().size(); i ++){
    					
    					if(oListitem.getChildren().get(i).getClass().isInstance(new Listcell())){
    						Listcell oListcell = (Listcell) oListitem.getChildren().get(i);
   						
    		                //Si son las primeras columnas: Agencia y fecha
    		                if(i<2) {
    		                	temp =oListcell.getLabel();
        		                //Agencia
        		                cellh = rowh.createCell((short)i);
        						cellh.setCellValue(new HSSFRichTextString(temp));    		                	
    		                }
    		                //Si son las demas que son numericas
    		                else {
        						//Montos de boletas y encomiendas
    		                	temp=oListcell.getLabel();
    		                	//Si hay coma la extraemos
    		                	if(temp.contains(",") && temp.chars().filter(ch -> ch == ',').count()==1) {
    		                		pos=temp.indexOf(",");
    		                		valor = temp.substring(0, pos)+temp.substring(pos+1);
    		                	}else if(temp.contains(",") && temp.chars().filter(ch -> ch == ',').count()==2) {
    		                		pos=temp.indexOf(",");
    		                		pos1=temp.indexOf(",", pos+1);
    		                		valor = temp.substring(0, pos)+temp.substring(pos+1, pos1)+temp.substring(pos1+1);
    		                	}
    		                	else
    		                		valor=temp;
    		                			
        						cellh = rowh.createCell((short)i);
        						cellh.setCellStyle(style);
        						cellh.setCellValue(Double.valueOf(valor));    		                	
    		                }
    		                
    					}
    					
    				}//Fin for objeto listcell

    			}
    		}//Fin for componente
            
            ByteArrayOutputStream outByteStream = new ByteArrayOutputStream();
			wb.write(outByteStream);
			byte [] outArray = outByteStream.toByteArray();
			response.setContentLength(outArray.length);
			OutputStream outStream = response.getOutputStream();
		    outStream.write(outArray);
		    outStream.flush();

        } catch (Exception e) {
        	log("EXPORT XLS REPORTE GENERAL DE VENTAS: "+e.toString());
            System.out.println(e.toString());
            e.printStackTrace();
        }
	}

}
