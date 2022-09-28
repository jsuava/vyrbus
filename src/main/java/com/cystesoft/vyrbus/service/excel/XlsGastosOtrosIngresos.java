package com.cystesoft.vyrbus.service.excel;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
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

import com.cystesoft.vyrbus.model.bean.Gasto;
import com.cystesoft.vyrbus.model.bean.TipoGasto;
import com.cystesoft.vyrbus.service.util.Constantes;

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
		@SuppressWarnings("unchecked")
		ArrayList<Gasto> lstGastos = (ArrayList)request.getSession().getAttribute("lstGastos");
		String desde = (String)request.getSession().getAttribute("desde");
		String hasta = (String)request.getSession().getAttribute("hasta");
		String usuarioReporte = (String)request.getSession().getAttribute("usuarioReporte");
		String fechaEmision = (String)request.getSession().getAttribute("fechaEmision");
		String usuarioConsulta = (String)request.getSession().getAttribute("usuarioConsulta");
		String agenciaConsulta = (String)request.getSession().getAttribute("agenciaConsulta");
		String gastoConsulta = (String)request.getSession().getAttribute("gastoConsulta");
		//16/09/2022 22:42:09
		String cadenaFecha =fechaEmision.replace('/', '-');
		cadenaFecha =cadenaFecha.replace(' ', '_');
		cadenaFecha =cadenaFecha.replace(':', '_');
		String fileName = "GastosOtrosIngresos_"+cadenaFecha+".xls";


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
            //(2, 7) fechaEmision
            rowh = sheet.createRow((short)2);
			cellh = rowh.createCell((short)7);
			cellh.setCellValue(new HSSFRichTextString(fechaEmision));            
            //(3, 2) desde
            rowh = sheet.createRow((short)3);
			cellh = rowh.createCell((short)2);
			cellh.setCellValue(new HSSFRichTextString(desde));
            //(3, 5) hasta
			cellh = rowh.createCell((short)5);
			cellh.setCellValue(new HSSFRichTextString(hasta));
            //(4, 2) tipo
			rowh = sheet.createRow((short)4);
			cellh = rowh.createCell((short)2);
			cellh.setCellValue(new HSSFRichTextString(gastoConsulta));
            //(4, 5) agencia
			cellh = rowh.createCell((short)5);
			cellh.setCellValue(new HSSFRichTextString(agenciaConsulta));
            //(5, 2) usuario Consulta
			rowh = sheet.createRow((short)5);
			cellh = rowh.createCell((short)2);
			cellh.setCellValue(new HSSFRichTextString(usuarioConsulta));
			//(5, 5)usuario reporte
			cellh = rowh.createCell((short)5);
			cellh.setCellValue(new HSSFRichTextString(usuarioReporte));

            int j = 8;
            Double totalGastos = 0D;
            Double totalIngresos = 0D;
            int control=0;

            for (Iterator it = lstGastos.iterator(); it.hasNext();) {
                Gasto gasto = (Gasto) it.next();
                j++;
                if(gasto.getTipoGasto().getTipoOperacion().intValue() == Constantes.FALSE_VALUE) {
	                rowh = sheet.createRow((short)j);
	                cellh = rowh.createCell((short)0);
	                cellh.setCellStyle(styleFecha);
					cellh.setCellValue(gasto.getLiquidacion().getFechaLiquidacion());
	
					cellh = rowh.createCell((short)1);
					cellh.setCellValue(new HSSFRichTextString(gasto.getAgencia().getDenominacion()));
	
					cellh = rowh.createCell((short)2);
					cellh.setCellValue(new HSSFRichTextString(gasto.getLiquidacion().getNombreUsuario()));
	
					cellh = rowh.createCell((short)3);
					cellh.setCellValue(new HSSFRichTextString(gasto.getTipoGasto().getDenominacion()));
	
					cellh = rowh.createCell((short)4);
					cellh.setCellValue(new HSSFRichTextString(gasto.getNumeroDocumento()));
	
					cellh = rowh.createCell((short)5);
					cellh.setCellStyle(style);
					cellh.setCellValue(gasto.getMonto());
	
					cellh = rowh.createCell((short)6);
					cellh.setCellValue(new HSSFRichTextString(gasto.getTipoGasto().getTipoOperacion().intValue()==Constantes.FALSE_VALUE?"GASTO":"INGRESO"));
	
					cellh = rowh.createCell((short)7);
					cellh.setCellValue(new HSSFRichTextString(gasto.getObservacion()));
					totalGastos += gasto.getMonto();
                }else if(gasto.getTipoGasto().getTipoOperacion().intValue() == Constantes.TRUE_VALUE && control == 0) {
                	rowh = sheet.createRow((short)j);
	                cellh = rowh.createCell((short)3);
					cellh.setCellValue(new HSSFRichTextString("TOTAL GASTOS"));
	
					cellh = rowh.createCell((short)5);
					cellh.setCellStyle(style);
					cellh.setCellValue(totalGastos);
					control++;
					j+=2;
					
	                rowh = sheet.createRow((short)j);
	                cellh = rowh.createCell((short)0);
	                cellh.setCellStyle(styleFecha);
					cellh.setCellValue(gasto.getLiquidacion().getFechaLiquidacion());
	
					cellh = rowh.createCell((short)1);
					cellh.setCellValue(new HSSFRichTextString(gasto.getAgencia().getDenominacion()));
	
					cellh = rowh.createCell((short)2);
					cellh.setCellValue(new HSSFRichTextString(gasto.getLiquidacion().getNombreUsuario()));
	
					cellh = rowh.createCell((short)3);
					cellh.setCellValue(new HSSFRichTextString(gasto.getTipoGasto().getDenominacion()));
	
					cellh = rowh.createCell((short)4);
					cellh.setCellValue(new HSSFRichTextString(gasto.getNumeroDocumento()));
	
					cellh = rowh.createCell((short)5);
					cellh.setCellStyle(style);
					cellh.setCellValue(gasto.getMonto());
	
					cellh = rowh.createCell((short)6);
					cellh.setCellValue(new HSSFRichTextString(gasto.getTipoGasto().getTipoOperacion().intValue()==Constantes.FALSE_VALUE?"GASTO":"INGRESO"));
	
					cellh = rowh.createCell((short)7);
					cellh.setCellValue(new HSSFRichTextString(gasto.getObservacion()));
					totalIngresos += gasto.getMonto();
					
                }else {
	                rowh = sheet.createRow((short)j);
	                cellh = rowh.createCell((short)0);
	                cellh.setCellStyle(styleFecha);
					cellh.setCellValue(gasto.getLiquidacion().getFechaLiquidacion());
	
					cellh = rowh.createCell((short)1);
					cellh.setCellValue(new HSSFRichTextString(gasto.getAgencia().getDenominacion()));
	
					cellh = rowh.createCell((short)2);
					cellh.setCellValue(new HSSFRichTextString(gasto.getLiquidacion().getNombreUsuario()));
	
					cellh = rowh.createCell((short)3);
					cellh.setCellValue(new HSSFRichTextString(gasto.getTipoGasto().getDenominacion()));
	
					cellh = rowh.createCell((short)4);
					cellh.setCellValue(new HSSFRichTextString(gasto.getNumeroDocumento()));
	
					cellh = rowh.createCell((short)5);
					cellh.setCellStyle(style);
					cellh.setCellValue(gasto.getMonto());
	
					cellh = rowh.createCell((short)6);
					cellh.setCellValue(new HSSFRichTextString(gasto.getTipoGasto().getTipoOperacion().intValue()==Constantes.FALSE_VALUE?"GASTO":"INGRESO"));
	
					cellh = rowh.createCell((short)7);
					cellh.setCellValue(new HSSFRichTextString(gasto.getObservacion()));
					totalIngresos += gasto.getMonto();
                }
            }

            if(control > 0) {
            	j++;
            	rowh = sheet.createRow((short)j);
                cellh = rowh.createCell((short)3);
				cellh.setCellValue(new HSSFRichTextString("TOTAL INGRESOS"));

				cellh = rowh.createCell((short)5);
				cellh.setCellStyle(style);
				cellh.setCellValue(totalIngresos);
            }

            ByteArrayOutputStream outByteStream = new ByteArrayOutputStream();
			wb.write(outByteStream);
			byte [] outArray = outByteStream.toByteArray();
			response.setContentLength(outArray.length);
			OutputStream outStream = response.getOutputStream();
		    outStream.write(outArray);
		    outStream.flush();

        } catch (Exception e) {
        	log("EXPORT XLS GASTOS OTROS INGRESOS: "+e.toString());
            System.out.println(e.toString());
            e.printStackTrace();
        }
	}
}
