/**
 * Proyecto		: SISVYR
 * Sistema		: Sistema de Ventas y Reservas
 * Descripci�n	:
 * Autor		: Jos� Avalos
 * Fecha		: 14/10/2013
 */
package pe.itsb.vyrbus.service.servlet;

import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
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
import pe.itsb.vyrbus.model.bean.TerminosVenta;
import pe.itsb.vyrbus.model.bean.VentaPasaje;
import pe.itsb.vyrbus.service.report.ReporteEticket;
import pe.itsb.vyrbus.service.util.Constantes;

/**
 * @author Jose
 *
 */
public class EticketServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * Constructor of the object.
	 */
	public EticketServlet() {
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

	    ServletOutputStream out = response.getOutputStream();

		List<TerminosVenta> lstTerminos = (List<TerminosVenta>)request.getSession().getAttribute("terminos");
		List<VentaPasaje> lstVentas = (List<VentaPasaje>)request.getSession().getAttribute("lstVentas");
		VentaPasaje ventaPasaje = lstVentas.get(0);
		String concesionario = (String)request.getSession().getAttribute("concesionario");
		String labelRazonSocial="RAZON SOCIAL";
		String labelRuc="RUC";
		String simbolo=":";
		String razonSocial="";
		String ruc="";
		if(ventaPasaje.getAgencia().getTipoAgencia().getId().intValue()==Constantes.ID_TIPAGE_VIAJES && ventaPasaje.getCliente()!=null){
			razonSocial=ventaPasaje.getCliente().getRazonSocial();
			ruc=ventaPasaje.getCliente().getNumeroDocumento();
		}

		try{
//			JasperReport reporte = (JasperReport)JRLoader.loadObject(getServletContext().getRealPath("WEB-INF/jasper/Eticket.jasper"));
			JasperReport reporte;
	    	InputStream inputStream = getServletContext().getResourceAsStream("WEB-INF/jasper/Eticket.jasper");
	    	reporte = (JasperReport)JRLoader.loadObject(inputStream);

			String terminos = "";
			for (TerminosVenta termsSale : lstTerminos) {
				if(termsSale.getOrden()==1){
					if(termsSale.getIdioma().equals(new String("ES")))
						terminos = terminos+"IMPORTANTE!";
					else
						terminos = terminos+"\n\n"+"IMPORTANT!";
				}
				terminos = terminos+"\n"+termsSale.getOrden()+" "+termsSale.getDenominacion();
			}

			Map<String, Object> parameters = new HashMap<>();
			parameters.put("agencia", concesionario);
			parameters.put("fechaCompra", new SimpleDateFormat(Constantes.DATE_FORMAT).format(ventaPasaje.getFechaInsercion()));
			parameters.put("importe", ventaPasaje.getImportePagado());
			parameters.put("usuario", ventaPasaje.getUsuario().getLogin());
			parameters.put("terminos", terminos);
			parameters.put("labelRazonSocial", (razonSocial.trim().isEmpty()?"":labelRazonSocial) );
			parameters.put("razonSocial", razonSocial );
			parameters.put("labelRuc", (ruc.trim().isEmpty()?"":labelRuc));
			parameters.put("ruc", ruc);
			parameters.put("simbolo", (ruc.trim().isEmpty()?"":simbolo));


			JasperPrint jasperPrint = JasperFillManager.fillReport(reporte, parameters, new ReporteEticket(lstVentas));
//			JasperPrintManager.printReport(jasperPrint, false);

			JRExporter jrExporter = new JRPdfExporter();
			jrExporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
			jrExporter.setParameter(JRExporterParameter.OUTPUT_STREAM, out);
			jrExporter.exportReport();


		}catch(Exception ex){
			ex.printStackTrace();
		}
	}

}
