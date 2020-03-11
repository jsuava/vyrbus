/**
 * Proyecto		: SISVYR
 * Sistema		: Sistema de Ventas y Reservas
 * Descripción	: 
 * Autor		: José Sullo Avalos
 * Fecha		: 14/10/2013
 */
package com.cystesoft.vyrbus.service.servlet;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.jasperreports.engine.JRExporter;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.engine.util.JRLoader;

import com.cystesoft.vyrbus.model.bean.TipoAgencia;
import com.cystesoft.vyrbus.model.bean.VentaPasaje;
import com.cystesoft.vyrbus.service.report.ReporteVentasRealizadas;
import com.cystesoft.vyrbus.service.util.Constantes;

/**
 * @author JABANTO
 *
 */
public class DetalladoVentasServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * Constructor of the object.
	 */
	public DetalladoVentasServlet() {
		super();
	}

	/**
	 * Destruction of the servlet. <br>
	 */
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
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		processRequest(request, response);	
	}

	/**
	 * Initialization of the servlet. <br>
	 *
	 * @throws ServletException if an error occurs
	 */
	public void init() throws ServletException {
		// Put your code here
	}

	/**
	 * @param request
	 * @param response
	 */
	@SuppressWarnings("deprecation")
	private void processRequest(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException {
		// TODO Auto-generated method stub
//		response.setHeader("Content-Disposition", "attachment; filename=\"reporte.pdf\";");
		response.setHeader("Cache-Control", "no-cache");
	    response.setHeader("Pragma", "no-cache");
	    response.setDateHeader("Expires", 0);
	    response.setContentType("application/pdf");
	    
	    ServletOutputStream out = response.getOutputStream();
	    
	    /*Recupera variables de sesión*/
	    @SuppressWarnings("unchecked")
		List<VentaPasaje>lstVentas=(List<VentaPasaje>)request.getSession().getAttribute("lstVentas");
	    TipoAgencia tipoAgencia=(TipoAgencia)request.getSession().getAttribute("tipoAgencia");
	    String usuario=(String)request.getSession().getAttribute("usuario");
	    String agencia=(String)request.getSession().getAttribute("agencia");
	    String fechaInicio=(String)request.getSession().getAttribute("fechaInicio");
	    String fechaFinal=(String)request.getSession().getAttribute("fechaFinal");

	    try {
	    	JasperReport reporte;
	    	if(tipoAgencia.getId().intValue()==Constantes.ID_TIPAGE_CORPORATIVO)
	    		reporte = (JasperReport)JRLoader.loadObject(getServletContext().getRealPath("WEB-INF/jasper/VentasCorporativas.jasper"));
	    	else
	    		reporte = (JasperReport)JRLoader.loadObject(getServletContext().getRealPath("WEB-INF/jasper/VentasRealizadas.jasper"));
	    	
	    	Map<String, Object> parameters = new HashMap<String, Object>();
			parameters.put("usuario",usuario);
			parameters.put("local",agencia);
			parameters.put("desde",fechaInicio);
			parameters.put("hasta",fechaFinal);
			
			JasperPrint jasperPrint = JasperFillManager.fillReport(reporte, parameters, new ReporteVentasRealizadas(lstVentas));
			JRExporter jrExporter = new JRPdfExporter();
			jrExporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
			jrExporter.setParameter(JRExporterParameter.OUTPUT_STREAM, out);
			jrExporter.exportReport();
			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
}
