/**
 * Proyecto		: SISVYR
 * Sistema		: Sistema de Ventas y Reservas
 * Descripci�n	:
 * Autor		: Jos� Abanto
 * Fecha		: 16 set. 2022
 * Hora			: 21:26:38
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

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFDataFormat;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;

import pe.itsb.vyrbus.model.bean.Manifiesto;


/**
 * @author Marco
 *
 */
@SuppressWarnings({"rawtypes"})
public class XlsDevolucionIsc extends HttpServlet{

	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException{
		doProcess(request, response);
	}

	protected void doProcess(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException{
		@SuppressWarnings("unchecked")
		List<Manifiesto> lstManifiestos = (List<Manifiesto>)request.getSession().getAttribute("lstManifiestos");
		String desde = (String)request.getSession().getAttribute("desde");
		String hasta = (String)request.getSession().getAttribute("hasta");
		String usuario = (String)request.getSession().getAttribute("usuario");
		String fechaEmision = (String)request.getSession().getAttribute("fechaEmision");
		//16/09/2022 22:42:09
		String cadenaFecha =fechaEmision.replace('/', '-');
		cadenaFecha =cadenaFecha.replace(' ', '_');
		cadenaFecha =cadenaFecha.replace(':', '_');
		String fileName = "DevolucionISC_"+cadenaFecha+".xls";

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

			String serie;
			String numero;

            int j = 6;
            int item = 0;
            for (Iterator it = lstManifiestos.iterator(); it.hasNext();) {
                Manifiesto manifiesto = (Manifiesto) it.next();
                j++;
                item++;
                rowh = sheet.createRow((short)j);
                cellh = rowh.createCell((short)0);
				cellh.setCellValue(Integer.valueOf(item));

                rowh = sheet.createRow((short)j);
                cellh = rowh.createCell((short)1);
				cellh.setCellValue(new HSSFRichTextString(manifiesto.getCodigoBus()));

				cellh = rowh.createCell((short)2);
				cellh.setCellValue(new HSSFRichTextString(manifiesto.getRuc()));

				cellh = rowh.createCell((short)3);
				cellh.setCellValue(new HSSFRichTextString(manifiesto.getPer4949()));

				cellh = rowh.createCell((short)4);
				cellh.setCellValue(new HSSFRichTextString(manifiesto.getPeriodo()));

				serie = manifiesto.getNumeroManifiesto().substring(0, 3);
				numero = manifiesto.getNumeroManifiesto().substring(4);

				cellh = rowh.createCell((short)5);
				cellh.setCellValue(new HSSFRichTextString(serie));

				cellh = rowh.createCell((short)6);
				cellh.setCellValue(new HSSFRichTextString(numero));

				cellh = rowh.createCell((short)7);
				cellh.setCellValue(new HSSFRichTextString(manifiesto.getBus().getGrupoMantenimiento().getDenominacion()));

				cellh = rowh.createCell((short)8);
				cellh.setCellValue(new HSSFRichTextString(manifiesto.getPlaca()));

				cellh = rowh.createCell((short)9);
				cellh.setCellValue(new HSSFRichTextString(manifiesto.getCertificadoHabilitacion()));

				cellh = rowh.createCell((short)10);
				cellh.setCellStyle(styleFecha);
				cellh.setCellValue(manifiesto.getItinerario().getFechaPartida());

				cellh = rowh.createCell((short)11);
				cellh.setCellValue(new HSSFRichTextString(manifiesto.getPuntoPartidaDepartamento()));

				cellh = rowh.createCell((short)12);
				cellh.setCellValue(new HSSFRichTextString(manifiesto.getPuntoPartidaDistrito()));

				cellh = rowh.createCell((short)13);
				cellh.setCellValue(new HSSFRichTextString(manifiesto.getPuntoLlegadaDepartamento()));

				cellh = rowh.createCell((short)14);
				cellh.setCellValue(new HSSFRichTextString(manifiesto.getPuntoLlegadaDistrito()));

				cellh = rowh.createCell((short)15);
				cellh.setCellStyle(style);
				cellh.setCellValue(manifiesto.getImporte());
            }

            ByteArrayOutputStream outByteStream = new ByteArrayOutputStream();
			wb.write(outByteStream);
			byte [] outArray = outByteStream.toByteArray();
			response.setContentLength(outArray.length);
			OutputStream outStream = response.getOutputStream();
		    outStream.write(outArray);
		    outStream.flush();

        } catch (Exception e) {
        	log("EXPORT XLS DEVOLUCION ISC: "+e.toString());
            System.out.println(e.toString());
            e.printStackTrace();
        }
	}


}
