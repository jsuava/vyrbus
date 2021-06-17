/**
 * Proyecto		: SISVYR
 * Sistema		: Sistema de Ventas y Reservas
 * Descripción	: 
 * Autor		: José Abanto
 * Fecha		: 30/09/2014
 * Hora			: 15:45:08
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

import com.cystesoft.vyrbus.model.bean.VentaPasaje;
import com.cystesoft.vyrbus.service.report.ReporteManifiesto;

/**
 * @author JABANTO
 *
 */
public class ReporteManifiestoServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * Constructor of the object.
	 */
	public ReporteManifiestoServlet() {
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
		List<VentaPasaje>lstPasajeros=(List<VentaPasaje>)request.getSession().getAttribute("listPasajeros");
	    String numeroManifiesto=(String)request.getSession().getAttribute("numeroManifiesto");
	    String usuario=(String)request.getSession().getAttribute("usuario");
	    String agencia=(String)request.getSession().getAttribute("agencia");
	    String itinerario=(String)request.getSession().getAttribute("itinerario");
	    String origen=(String)request.getSession().getAttribute("origen");
	    String destino=(String)request.getSession().getAttribute("destino");
	    String piloto=(String)request.getSession().getAttribute("piloto");
	    String copiloto=(String)request.getSession().getAttribute("copiloto");
	    String copilotoAux=(String)request.getSession().getAttribute("copilotoAux");
	    String tripulante=(String)request.getSession().getAttribute("tripulante");
	    String licencia1=(String)request.getSession().getAttribute("licencia1");
	    String licencia2=(String)request.getSession().getAttribute("licencia2");
	    String licencia3=(String)request.getSession().getAttribute("licencia3");
	    String bus=(String)request.getSession().getAttribute("bus");
	    String placaBus=(String)request.getSession().getAttribute("placaBus");
	    String tarjetaHabilitacion=(String)request.getSession().getAttribute("tarjetaHabilitacion");
	    String salida=(String)request.getSession().getAttribute("salida");
	    String servicio=(String)request.getSession().getAttribute("servicio");
	    String totalAsientos=(String)request.getSession().getAttribute("totalAsientos");
	    String numeroAutoSunat=(String)request.getSession().getAttribute("numeroAutoSunat");
	    String totalPasajeros=(String)request.getSession().getAttribute("totalPasajeros");
	    String dniTripulante=(String)request.getSession().getAttribute("dniTripulante");
	    
	    try {
	    	JasperReport reporte;
	    	reporte = (JasperReport)JRLoader.loadObject(getServletContext().getRealPath("WEB-INF/jasper/ReporteManifiesto.jasper"));
	    		    	
	    	Map<String, Object> parameters = new HashMap<String, Object>();
			parameters.put("usuario",usuario);
			parameters.put("agencia",agencia);
			parameters.put("origen",origen);
			parameters.put("chofer1",piloto);
			parameters.put("licencia1",licencia1);
			parameters.put("chofer2",copiloto);
			parameters.put("licencia2",licencia2);
			parameters.put("chofer3",copilotoAux);
			parameters.put("licencia3",licencia3);
			parameters.put("tripulante",tripulante);
			parameters.put("itinerario",itinerario);
			parameters.put("destino",destino);
			parameters.put("salida",salida);
			parameters.put("nroManifiesto",numeroManifiesto);
			parameters.put("bus",bus);
			parameters.put("placa",placaBus);
			parameters.put("tarjetaHabilitacion",tarjetaHabilitacion);
			parameters.put("servicio",servicio);
			parameters.put("totalAsientos",totalAsientos);
			parameters.put("numeroAutoSunat",numeroAutoSunat);
			parameters.put("totalPasajeros",totalPasajeros);
			parameters.put("dniTripulante",dniTripulante);
						
						
			JasperPrint jasperPrint = JasperFillManager.fillReport(reporte, parameters, new ReporteManifiesto(lstPasajeros));
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

