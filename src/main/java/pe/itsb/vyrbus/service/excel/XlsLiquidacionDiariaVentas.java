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

import pe.itsb.vyrbus.model.bean.VentaPasaje;

@SuppressWarnings({"rawtypes"})
public class XlsLiquidacionDiariaVentas extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException{
		doProcess(request, response);
	}

	protected void doProcess(HttpServletRequest request, HttpServletResponse response){
		Listbox listbox = (Listbox)request.getSession().getAttribute("lbxVentas");
        String parcialPath = (String)request.getSession().getAttribute("parcialPath");
        response.setContentType("application/vnd.ms-excel");
        response.setHeader("Content-Disposition", "attachment; filename=LiquidacionDiariaVentas.xls");

        File template = new File(parcialPath);
        try {
        	POIFSFileSystem fs = new POIFSFileSystem(new FileInputStream(template));
			HSSFWorkbook wb = new HSSFWorkbook(fs);
			HSSFSheet sheet = wb.getSheetAt(0);
            HSSFRow rowh = null;

//            HSSFDataFormat format = wb.createDataFormat();
//            HSSFCellStyle style = wb.createCellStyle();
//            style.setDataFormat(format.getFormat("#,##0.00"));


            List listItems = listbox.getItems();
            int j = 2;
            for (Iterator it = listItems.iterator(); it.hasNext();) {
                Listitem item = (Listitem) it.next();
                j++;
                VentaPasaje venta=item.getValue();
                rowh = sheet.createRow((short)j);
                rowh.createCell((short) 0).setCellValue(new HSSFRichTextString(String.valueOf(j-2)));
                rowh.createCell((short) 1).setCellValue(new HSSFRichTextString(venta.getTipoTransaccion()));
                rowh.createCell((short) 2).setCellValue(new HSSFRichTextString(venta.getNumeroControl()));
                rowh.createCell((short) 3).setCellValue(new HSSFRichTextString(venta.getNumeroBoleto()));
                rowh.createCell((short) 4).setCellValue(new HSSFRichTextString(venta.getNumeroBoletoAnterior()));
                if(venta.getNumeroBoleto().length()==13)
                	rowh.createCell((short) 5).setCellValue(new HSSFRichTextString(venta.getPasajero()!=null ? venta.getPasajero().toString() : ""));
                else
                	rowh.createCell((short) 5).setCellValue(new HSSFRichTextString(venta.getPasajero()!=null ? venta.getPasajero().getNombresApellidos() : ""));
                rowh.createCell((short) 6).setCellValue(venta.getTarifa());
                rowh.createCell((short) 7).setCellValue(venta.getDescuento());
                //rowh.createCell((short) 8).setCellValue(new HSSFRichTextString(venta.getTipoMoneda()!=null ? "DOLARES" : "SOLES"));
                rowh.createCell((short) 8).setCellValue(venta.getAcuenta());
                rowh.createCell((short) 9).setCellValue(venta.getPenalidad());
               	rowh.createCell((short) 10).setCellValue(venta.getImportePagado());
                rowh.createCell((short) 11).setCellValue(new HSSFRichTextString(venta.getUsuario().toString()));
                rowh.createCell((short) 12).setCellValue(venta.getFechaInsercion());
                rowh.createCell((short) 13).setCellValue(new HSSFRichTextString(venta.getAgencia()!=null ? venta.getAgencia().getDenominacion() : ""));
            }

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
