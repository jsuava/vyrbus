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

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFDataFormat;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;

import pe.itsb.vyrbus.service.mappers.VentasPiloto;

@SuppressWarnings({"rawtypes"})
public class XlsVentasPagoPilotos extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException{
		doProcess(request, response);
	}

	protected void doProcess(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException{
		@SuppressWarnings("unchecked")
		List<VentasPiloto> lstVentas = (List<VentasPiloto>)request.getSession().getAttribute("lstVentas");
		String desde = (String)request.getSession().getAttribute("desde");
		String hasta = (String)request.getSession().getAttribute("hasta");
		String usuario = (String)request.getSession().getAttribute("usuario");
		String fechaEmision = (String)request.getSession().getAttribute("fechaEmision");
		//16/09/2022 22:42:09
		String cadenaFecha =fechaEmision.replace('/', '-');
		cadenaFecha =cadenaFecha.replace(' ', '_');
		cadenaFecha =cadenaFecha.replace(':', '_');
		String fileName = "VentasPagoPilotos_"+cadenaFecha+".xls";

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
            //(3, 4) desde
            rowh = sheet.createRow((short)3);
			cellh = rowh.createCell((short)4);
			cellh.setCellValue(new HSSFRichTextString(desde));
            //(3, 12) usuario
			cellh = rowh.createCell((short)12);
			cellh.setCellValue(new HSSFRichTextString(usuario));
            //(4, 4) hasta
			rowh = sheet.createRow((short)4);
			cellh = rowh.createCell((short)4);
			cellh.setCellValue(new HSSFRichTextString(hasta));
            //(4, 12) fechaEmision
			cellh = rowh.createCell((short)12);
			cellh.setCellValue(new HSSFRichTextString(fechaEmision));

            int j = 6;
            int item=0;
            for (Iterator it = lstVentas.iterator(); it.hasNext();) {
                VentasPiloto ventaPiloto = (VentasPiloto) it.next();
                j++;
                item++;
                rowh = sheet.createRow((short)j);
                cellh = rowh.createCell((short)0);
				cellh.setCellValue(Integer.valueOf(item));

				cellh = rowh.createCell((short)1);
				cellh.setCellStyle(styleFecha);
				cellh.setCellValue(ventaPiloto.getFechaCompra());

				cellh = rowh.createCell((short)2);
				cellh.setCellValue(new HSSFRichTextString(ventaPiloto.getNumeroBoleto()));

				cellh = rowh.createCell((short)3);
				cellh.setCellValue(new HSSFRichTextString(ventaPiloto.getRuc()));

				cellh = rowh.createCell((short)4);
				cellh.setCellValue(new HSSFRichTextString(ventaPiloto.getDni()));

				cellh = rowh.createCell((short)5);
				cellh.setCellValue(new HSSFRichTextString(ventaPiloto.getNombres()));

				cellh = rowh.createCell((short)6);
				cellh.setCellStyle(style);
				cellh.setCellValue(ventaPiloto.getExonerado());

				cellh = rowh.createCell((short)7);
				cellh.setCellStyle(style);
				cellh.setCellValue(ventaPiloto.getVenta());

				cellh = rowh.createCell((short)8);
				cellh.setCellStyle(style);
				cellh.setCellValue(ventaPiloto.getIgv());

				cellh = rowh.createCell((short)9);
				cellh.setCellStyle(style);
				cellh.setCellValue(ventaPiloto.getTotal());

				cellh = rowh.createCell((short)10);
				cellh.setCellStyle(styleFecha);
				cellh.setCellValue(ventaPiloto.getFechaSalidaBus());

				cellh = rowh.createCell((short)11);
				cellh.setCellValue(new HSSFRichTextString(ventaPiloto.getHoraVenta()));

				cellh = rowh.createCell((short)12);
				cellh.setCellValue(new HSSFRichTextString(ventaPiloto.getOrigen()));

				cellh = rowh.createCell((short)13);
				cellh.setCellValue(new HSSFRichTextString(ventaPiloto.getDestino()));

				cellh = rowh.createCell((short)14);
				cellh.setCellValue(new HSSFRichTextString(ventaPiloto.getAsiento()));

				cellh = rowh.createCell((short)15);
				cellh.setCellValue(new HSSFRichTextString(ventaPiloto.getTipo()));

				cellh = rowh.createCell((short)16);
				cellh.setCellValue(new HSSFRichTextString(ventaPiloto.getHoraSalidaBus()));

				cellh = rowh.createCell((short)17);
				cellh.setCellValue(new HSSFRichTextString(ventaPiloto.getCodigo()));

				cellh = rowh.createCell((short)18);
				cellh.setCellValue(new HSSFRichTextString(ventaPiloto.getPiloto()));

				cellh = rowh.createCell((short)19);
				cellh.setCellValue(new HSSFRichTextString(ventaPiloto.getCopiloto()));

				cellh = rowh.createCell((short)20);
				cellh.setCellValue(new HSSFRichTextString(ventaPiloto.getTripulante()));
            }

            ByteArrayOutputStream outByteStream = new ByteArrayOutputStream();
			wb.write(outByteStream);
			byte [] outArray = outByteStream.toByteArray();
			response.setContentLength(outArray.length);
			OutputStream outStream = response.getOutputStream();
		    outStream.write(outArray);
		    outStream.flush();

        } catch (Exception e) {
        	log("EXPORT XLS VENTA PAGO PILOTOS: "+e.toString());
            System.out.println(e.toString());
            e.printStackTrace();
        }
	}
}
