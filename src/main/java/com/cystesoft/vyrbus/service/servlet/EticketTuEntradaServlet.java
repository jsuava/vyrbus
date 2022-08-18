/**
 * Proyecto		: SISVYR
 * Sistema		: Sistema de Ventas y Reservas
 * Descripción	:
 * Autor		: José Abanto
 * Fecha		: 19/08/2015
 * Hora			: 12:42:02
 */
package com.cystesoft.vyrbus.service.servlet;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.cystesoft.vyrbus.model.bean.VentaPasaje;
import com.cystesoft.vyrbus.service.report.ReporteEticketTuEntrada;

import net.sf.jasperreports.engine.JRExporter;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.engine.util.JRLoader;

/**
 * @author jabanto
 *
 */
public class EticketTuEntradaServlet extends HttpServlet {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	public EticketTuEntradaServlet(){
		super();
	}

	/**
	 * Destruction of the servlet. <br>
	 */
	@Override
	public void destroy() {
		super.destroy(); // Just puts "destroy" string in log
		// Put your code here
	}


	/**
	 * The doGet method of the servlet. <br>
	 *
	 * This method is called when a form has its tag value method equals to get.
	 *
	 * @param request the request send by the client to the server
	 * @param response the response send by the server to the client
	 * @throws ServletException if an error occurred
	 * @throws IOException if an error occurred
	 */
	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		processRequest(request, response);
	}

	/**
	 * The doPost method of the servlet. <br>
	 *
	 * This method is called when a form has its tag value method equals to post.
	 *
	 * @param request the request send by the client to the server
	 * @param response the response send by the server to the client
	 * @throws ServletException if an error occurred
	 * @throws IOException if an error occurred
	 */
	@Override
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		processRequest(request, response);
	}

	/**
	 * Initialization of the servlet. <br>
	 *
	 * @throws ServletException if an error occurs
	 */
	@Override
	public void init() throws ServletException {
		// Put your code here
	}

	@SuppressWarnings({"deprecation", "unchecked"})
	protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	    response.setHeader("Cache-Control", "no-cache");
	    response.setHeader("Pragma", "no-cache");
	    response.setDateHeader("Expires", 0);
	    response.setContentType("application/pdf");
//	    response.setContentType("application/text");

		List<VentaPasaje> lstVentaPasajes = (List<VentaPasaje>)request.getSession().getAttribute("lstVentas");
		try{
//			JasperReport reporte = (JasperReport)JRLoader.loadObject(getServletContext().getRealPath("WEB-INF/jasper/EticketTuEntrada.jasper"));
//
//			Map<String, Object> parameters = new HashMap<String, Object>();
//			JasperPrint jasperPrint = JasperFillManager.fillReport(reporte, parameters, new ReporteEticketTuEntrada(lstVentaPasajes));
//
//			JasperPrintManager.printReport(jasperPrint, true);

//			JasperReport reporte = (JasperReport)JRLoader.loadObject(getServletContext().getRealPath("WEB-INF/jasper/Boleto.jasper"));
			JasperReport reporte;
	    	InputStream inputStream = getServletContext().getResourceAsStream("WEB-INF/jasper/Boleto.jasper");
	    	reporte = (JasperReport)JRLoader.loadObject(inputStream);

			Map<String, Object> parameters = new HashMap<>();
			JasperPrint jasperPrint = JasperFillManager.fillReport(reporte, parameters, new ReporteEticketTuEntrada(lstVentaPasajes));


			ServletOutputStream out = response.getOutputStream();
			JRExporter jrExporter = new JRPdfExporter();
			jrExporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
			jrExporter.setParameter(JRExporterParameter.OUTPUT_STREAM, out);
			jrExporter.exportReport();


//			ServletOutputStream out = response.getOutputStream();
//
//			JasperViewer visor=new JasperViewer(jasperPrint,false);
//			visor.setTitle("José Abanto Abanto");
//			visor.setVisible(true);
//
//			 PrintService[] printService = PrintServiceLookup.lookupPrintServices(null, null);
//			//se elige la impresora
//			PrintService impresora = (PrintService) JOptionPane.showInputDialog(null, "Eliga impresora:",
//			"Imprimir Reporte", JOptionPane.QUESTION_MESSAGE, null, printService, printService[0]);
//
//			JRPrintServiceExporter jrprintServiceExporter = new JRPrintServiceExporter();
//			jrprintServiceExporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint );
//			jrprintServiceExporter.setParameter(JRPrintServiceExporterParameter.PRINT_SERVICE, impresora );
//			jrprintServiceExporter.setParameter(JRPrintServiceExporterParameter.DISPLAY_PRINT_DIALOG, Boolean.TRUE);
//			jrprintServiceExporter.exportReport();


//			JRTextExporter exporter = new JRTextExporter ();
//			exporter.setParameter (JRExporterParameter.JASPER_PRINT, jasperPrint);
//			exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, out);
//			exporter.setParameter (JRTextExporterParameter.CHARACTER_WIDTH, new Float(5));
//			exporter.setParameter (JRTextExporterParameter.CHARACTER_HEIGHT, new Float (10));
//			exporter.exportReport();


		}catch(Exception ex){
			ex.printStackTrace();
		}
	}
}
