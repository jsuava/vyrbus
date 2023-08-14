package pe.itsb.vyrbus.service.excel;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;

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
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;

import pe.itsb.vyrbus.model.bean.VentaPasaje;
import pe.itsb.vyrbus.service.util.Constantes;
import pe.itsb.vyrbus.service.util.Util;


public class XlsEstadosCuenta extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doProcess(request, response);
	}


//	private void doProcess(HttpServletRequest req, HttpServletResponse resp)throws ServletException, IOException{
//		Boolean rptEnacbezadosReporte=(Boolean)req.getSession().getAttribute("rptEnacbezadosReporte");
//
//		if(rptEnacbezadosReporte)
//			exportClientePerso(req, resp);

	protected void doProcess(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException{
		Boolean rptPersonalizado=(Boolean)request.getSession().getAttribute("rptPersonalizado");
		if(rptPersonalizado)
			exportClientePerso(request, response);
		else
			exportClienteEstandar(request, response);
	}

	/**
	 * Para algunos clientes como por ejemplo GYM
	 */
	private void exportClientePerso(HttpServletRequest request, HttpServletResponse response) throws ServletException,IOException{
		Listbox list = (Listbox)request.getSession().getAttribute("listbox");
		String path =  (String)request.getSession().getAttribute("path");
//		String usuario = (String)request.getSession().getAttribute("usuario");
		response.setContentType("application/vnd.ms-excel");
		response.setHeader("Expires:", "0");
		response.setHeader("Content-Disposition", "attachment; filename=DetalladoPersonalizado.xls");

		File template = new File(path);
		try{
			POIFSFileSystem fs = new POIFSFileSystem(new FileInputStream(template));
			HSSFWorkbook wb = new HSSFWorkbook(fs);
			HSSFSheet sheet = wb.getSheetAt(0);
            HSSFRow row = null;
            HSSFCell cell = null;
            HSSFDataFormat format = wb.createDataFormat();
            HSSFCellStyle style = wb.createCellStyle();
            style.setDataFormat(format.getFormat("#,##0.00"));
            HSSFCellStyle styleDate = wb.createCellStyle();
            styleDate.setDataFormat(format.getFormat("dd/MM/yyyy"));
            HSSFCellStyle styleNumber = wb.createCellStyle();
            styleNumber.setDataFormat(format.getFormat("#####0"));

			Double total = 0.0;
			int fila=3,y=0;

			for (Listitem item : list.getItems()) {
				y++;
				VentaPasaje ventaPasaje=(VentaPasaje)item.getValue();

				row = sheet.createRow((short)fila);

				cell = row.createCell((short)0);
				cell.setCellStyle(styleNumber);
				cell.setCellValue(y);
				cell = row.createCell((short)1);
				cell.setCellStyle(styleDate);
				cell.setCellValue(ventaPasaje.getFechaLiquidacion());
				cell = row.createCell((short)2);
				cell.setCellStyle(styleDate);
				cell.setCellValue(ventaPasaje.getFechaPartida());
				int diasTrans=(int) ((ventaPasaje.getFechaPartida().getTime() - ventaPasaje.getFechaLiquidacion().getTime())/Constantes.MILISEGUNDOS_X_DIA);
				cell = row.createCell((short)3);
				cell.setCellStyle(styleNumber);
				cell.setCellValue(diasTrans);
				row.createCell((short)4).setCellValue(new HSSFRichTextString(ventaPasaje.getPasajero().getNumeroDocumento().toString()));
				row.createCell((short)5).setCellValue(new HSSFRichTextString(ventaPasaje.getPasajero().toString()));
				row.createCell((short)6).setCellValue(new HSSFRichTextString(ventaPasaje.getVentaPasaje()!=null?ventaPasaje.getVentaPasaje().getAgencia().getNombreCorto():""));
				row.createCell((short)7).setCellValue(new HSSFRichTextString(ventaPasaje.getNumeroBoleto()));
				row.createCell((short)8).setCellValue(new HSSFRichTextString(ventaPasaje.getNumeroBoletoAnterior()!=null?ventaPasaje.getNumeroBoletoAnterior():""));
				row.createCell((short)9).setCellValue(new HSSFRichTextString(ventaPasaje.getRuta().toString()));
				row.createCell((short)10).setCellValue(new HSSFRichTextString(ventaPasaje.getRuta().getDestino()));
				row.createCell((short)11).setCellValue(new HSSFRichTextString("PERU"));
				row.createCell((short)12).setCellValue(new HSSFRichTextString(ventaPasaje.getRuta().getDestino()));
				cell = row.createCell((short)13);
				cell.setCellStyle(styleDate);
				cell.setCellValue(ventaPasaje.getFechaPartida());
				row.createCell((short)14).setCellValue(new HSSFRichTextString(ventaPasaje.getHoraPartida()));
				row.createCell((short)15).setCellValue(new HSSFRichTextString(ventaPasaje.getHoraLllegada()));
				cell = row.createCell((short)16);
				cell.setCellStyle(style);
//				cell.setCellValue(ventaPasaje.getImportePagado());
				/*valida tipo de moneda - jabanto - 15/08/2015*/
				 if(ventaPasaje.getTipoMoneda()!=null && ventaPasaje.getTipoMoneda().getId().intValue()!=Constantes.ID_TIPMON_SOLES)
					 cell.setCellValue(ventaPasaje.getImportePagadoEquibalente()!=null?ventaPasaje.getImportePagadoEquibalente():.00);
	                else
	                	cell.setCellValue(ventaPasaje.getImportePagado());
				cell = row.createCell((short)17);
				cell.setCellStyle(style);
				cell.setCellValue(0.00);
				cell = row.createCell((short)18);
				cell.setCellStyle(style);
//				cell.setCellValue(ventaPasaje.getImportePagado());
				/*valida tipo de moneda - jabanto - 15/08/2015*/
				 if(ventaPasaje.getTipoMoneda()!=null && ventaPasaje.getTipoMoneda().getId().intValue()!=Constantes.ID_TIPMON_SOLES){
					 cell.setCellValue(ventaPasaje.getImportePagadoEquibalente()!=null?ventaPasaje.getImportePagadoEquibalente():.00);
					 total+=+(ventaPasaje.getImportePagadoEquibalente()!=null?ventaPasaje.getImportePagadoEquibalente():.00);
				 }else{
	              	cell.setCellValue(ventaPasaje.getImportePagado());
	              	total+=+ventaPasaje.getImportePagado();
				 }



				row.createCell((short)19).setCellValue(new HSSFRichTextString(ventaPasaje.getCliente()!=null?ventaPasaje.getCliente().toString():""));
				row.createCell((short)20).setCellValue(new HSSFRichTextString(ventaPasaje.getCliente()!=null?ventaPasaje.getCliente().getNumeroDocumento():""));
				row.createCell((short)21).setCellValue(new HSSFRichTextString(ventaPasaje.getUsuario().toString()));
				if(ventaPasaje.getCentroCosto().getId()!=null)
					row.createCell((short)22).setCellValue(new HSSFRichTextString(ventaPasaje.getCentroCosto().getCodigo()+"-"+ventaPasaje.getCentroCosto().getDenominacion()));
				cell = row.createCell((short)23);
				cell.setCellStyle(style);
				cell.setCellValue(ventaPasaje.getRuta().getKilometros());
				row.createCell((short)24).setCellValue(new HSSFRichTextString(ventaPasaje.getEstadoDocumento()!=null?ventaPasaje.getEstadoDocumento():""));
				fila++;
			}
			row = sheet.createRow((short)fila);
			row.createCell((short)17).setCellValue(new HSSFRichTextString("TOTAL:"));
			cell = row.createCell((short)18);
			cell.setCellStyle(style);
			cell.setCellValue(total);

			ByteArrayOutputStream outByteStream = new ByteArrayOutputStream();
			wb.write(outByteStream);
			byte [] outArray = outByteStream.toByteArray();
			response.setContentLength(outArray.length);
			OutputStream outStream = response.getOutputStream();
		    outStream.write(outArray);
		    outStream.flush();


		}catch(Exception ex){
			ex.printStackTrace();
//			throw new ServletException(ex);
		}
	}

	/**
	 * Para pa mayoria de los cliente
	 * @throws ServletException,IOException
	 */
	private void exportClienteEstandar(HttpServletRequest request, HttpServletResponse response) throws ServletException,IOException{
		Listbox list = (Listbox)request.getSession().getAttribute("listbox");
		String path =  (String)request.getSession().getAttribute("path");
		String agencia = (String)request.getSession().getAttribute("agencia");
		String usuario = (String)request.getSession().getAttribute("usuario");
		String desde = (String)request.getSession().getAttribute("desde");
		String hasta = (String)request.getSession().getAttribute("hasta");
		response.setContentType("application/vnd.ms-excel");
		response.setHeader("Expires:", "0");
		response.setHeader("Content-Disposition", "attachment; filename=Detallado_"+agencia+".xls");


		File template = new File(path);
		try{
			POIFSFileSystem fs = new POIFSFileSystem(new FileInputStream(template));
			HSSFWorkbook wb = new HSSFWorkbook(fs);
			HSSFSheet sheet = wb.getSheetAt(0);
            HSSFRow row = null;
            HSSFCell celda = null;
            HSSFDataFormat format = wb.createDataFormat();
            HSSFCellStyle styleDouble = wb.createCellStyle();
            styleDouble.setDataFormat(format.getFormat("#,##0.00"));
            HSSFCellStyle styleInteger = wb.createCellStyle();
            styleInteger.setDataFormat(format.getFormat("######"));
            HSSFCellStyle styleDate = wb.createCellStyle();
            styleDate.setDataFormat(format.getFormat("dd/MM/yyyy"));

            row = sheet.createRow((short)2);
            row.createCell((short)1).setCellValue(new HSSFRichTextString(agencia));
            row = sheet.createRow((short)3);
            row.createCell((short)1).setCellValue(new HSSFRichTextString(usuario));
            row = sheet.createRow((short)4);
            row.createCell((short)1).setCellValue(new HSSFRichTextString(desde));
            row = sheet.createRow((short)5);
            row.createCell((short)1).setCellValue(new HSSFRichTextString(hasta));

            int fila=7;

			fila++;
			Double total = 0.0;
			int i=0;
			for (Listitem item : list.getItems()) {
				row = sheet.createRow((short)fila);
				for(int k=0; k<list.getListhead().getChildren().size(); k++){
					Listcell cell = (Listcell)item.getChildren().get(k);
					if(k!=11){
						if(k==1 || k==15){
							celda = row.createCell((short)i);
							celda.setCellStyle(styleDate);
							celda.setCellValue(Util.StringtoDate(cell.getLabel(), Constantes.DATE_FORMAT));
						}else if(k==8 || k==9 || k==10){
							celda = row.createCell((short)i);
							celda.setCellStyle(styleDouble);
//							celda.setCellValue(Double.parseDouble(cell.getLabel()));
							celda.setCellValue(Util.parseNumberFormat(cell.getLabel(),2));
							if(k==10)
								total = total + Util.parseNumberFormat(cell.getLabel(),2);
//								total = total + Double.parseDouble(cell.getLabel());
						}else if(Util.isNumeric(cell.getLabel())){
							celda = row.createCell((short)i);
							celda.setCellStyle(styleInteger);
							celda.setCellValue(Util.parseNumberFormat(cell.getLabel(),0));
//							celda.setCellValue(Integer.parseInt(cell.getLabel()));
						}else
							row.createCell((short)i).setCellValue(new HSSFRichTextString(cell.getLabel()));
						i++;
					}
				}
				i=0;
				fila++;
			}

			row = sheet.createRow((short)fila);
			celda = row.createCell((short)10);
			celda.setCellStyle(styleDouble);
			celda.setCellValue(total);

			ByteArrayOutputStream outByteStream = new ByteArrayOutputStream();
			wb.write(outByteStream);
			byte [] outArray = outByteStream.toByteArray();

			response.setContentLength(outArray.length);
			OutputStream outStream = response.getOutputStream();
		    outStream.write(outArray);
		    outStream.flush();
		}catch(Exception ex){
			ex.printStackTrace();
			throw new ServletException(ex);
		}
	}
}
