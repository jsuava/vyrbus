package com.cystesoft.vyrbus.service.business.impl;

import java.util.ArrayList;
import java.util.Date;

import com.cystesoft.vyrbus.model.bean.DetalleItinerario;
import com.cystesoft.vyrbus.model.bean.PreferenciaAlimentaria;
import com.cystesoft.vyrbus.model.bean.Promocion;
import com.cystesoft.vyrbus.model.bean.Transbordo;
import com.cystesoft.vyrbus.model.bean.VentaPasaje;
import com.cystesoft.vyrbus.model.bean.report.RptAvanceBuses;
import com.cystesoft.vyrbus.model.dao.ReportesDAO;
import com.cystesoft.vyrbus.service.business.ReportesManager;

/**
 * 
 * @author José Abanto
 *
 */
public class ReportesManagerImpl implements ReportesManager {
	private ReportesDAO reportesDAO;
	
	public ReportesDAO getReportesDAO(){
		return reportesDAO;
	}
	
	public void setReportesDAO (ReportesDAO reportesDAO){
		this.reportesDAO=reportesDAO;
	}

	/*
	 * (non-Javadoc)
	 * @see com.tepsa.sisvyr.service.business.ReportesManager#tarifario(java.lang.String, java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	public ArrayList<DetalleItinerario> tarifario(String fechaInical,String fechaFinal, Integer idServicio, Integer idLocalidad,boolean incluirTarifaCero, Integer idRuta) throws Exception {
		return getReportesDAO().tarifario(fechaInical, fechaFinal, idServicio, idLocalidad,incluirTarifaCero,idRuta);
	}

//	/*
//	 * (non-Javadoc)
//	 * @see com.tepsa.sisvyr.service.business.ReportesManager#diarioAcumulado(java.lang.String, java.lang.String, java.lang.String, java.lang.String)
//	 */
//	@Override
//	public ArrayList<VentaPasaje> diarioAcumulado(String fechaInical,String fechaFinal, String servicio, String rutaItinerario)throws Exception {
//		return getReportesDAO().diarioAcumulado(fechaInical, fechaFinal, servicio, rutaItinerario);
//	}

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.service.business.ReportesManager#diarioAcumulado(java.util.Date, java.util.Date, long, int, boolean)
	 */
	@Override
	public ArrayList<Object> diarioAcumulado(Date fechaInicial,Date fechaFinal, long idServicio, int limaProvincias,boolean mostrarCuadroIngresos) throws Exception {
		return getReportesDAO().diarioAcumulado(fechaInicial, fechaFinal, idServicio, limaProvincias, mostrarCuadroIngresos);
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.tepsa.sisvyr.service.business.ReportesManager#avanceSemanalXRutas(java.lang.String, java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	public ArrayList<VentaPasaje> avanceSemanalXRutas(String fechaInicial,String fechaFinal, String tipoConsulta, String transaccion)throws Exception {
		return getReportesDAO().avanceSemanalXRutas(fechaInicial, fechaFinal, tipoConsulta, transaccion);
	}

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.service.business.ReportesManager#diarioAcumuladoDetallado(java.util.Date, java.util.Date, long, int, boolean)
	 */
	@Override
	public ArrayList<Object> diarioAcumuladoDetallado(Date fechaInicial,Date fechaFinal, long idServicio, int limaProvincias,boolean mostrarCuadroIngresos) throws Exception {
		// TODO Auto-generated method stub
		return getReportesDAO().diarioAcumuladoDetallado(fechaInicial, fechaFinal, idServicio, limaProvincias, mostrarCuadroIngresos);
	}

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.service.business.ReportesManager#pasajerosTransbordados(java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.Long)
	 */
	@Override
	public ArrayList<Transbordo> pasajerosTransbordados(String fechaTransInicio, String fechaTransFin, String origen,String destino, String boleto, Long idPasajero) throws Exception {
		// TODO Auto-generated method stub
		return getReportesDAO().pasajerosTransbordados(fechaTransInicio, fechaTransFin, origen, destino, boleto, idPasajero);
	}

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.service.business.ReportesManager#cenasXRutas(java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	public ArrayList<PreferenciaAlimentaria> cenasXRutas(String fechaInicial,String fechaFinal, String tipoConsulta) throws Exception {
		// TODO Auto-generated method stub
		return getReportesDAO().cenasXRutas(fechaInicial, fechaFinal, tipoConsulta);
	}

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.service.business.ReportesManager#ventasPromocion(java.lang.String, java.lang.String, java.lang.Long)
	 */
	@Override
	public ArrayList<Promocion> ventasPromocion(String fechaInicio,String fechaFin, Long idPromocion,String tipoDescuento) throws Exception {
		// TODO Auto-generated method stub
		return getReportesDAO().ventasPromocion(fechaInicio, fechaFin, idPromocion,tipoDescuento);
	}

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.service.business.ReportesManager#ventasPromocionLstPromociones(java.lang.String, java.lang.String)
	 */
	@Override
	public ArrayList<Promocion> ventasPromocionLstPromociones(String fechaInicio, String fechaFin) throws Exception {
		// TODO Auto-generated method stub
		return getReportesDAO().ventasPromocionLstPromociones(fechaInicio, fechaFin); 
	}

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.service.business.ReportesManager#ventasPromocionDeta(java.lang.String, java.lang.String, java.lang.Long)
	 */
	@Override
	public ArrayList<VentaPasaje> ventasPromocionDeta(String fechaInicio,String fechafin, Long idPromocion) throws Exception {
		// TODO Auto-generated method stub
		return getReportesDAO().ventasPromocionDeta(fechaInicio, fechafin, idPromocion);
	}

	/* 
	 * (non-Javadoc)
	 * @see com.tepsa.sisvyr.service.business.ReportesManager#avancesBuses(java.lang.String, java.lang.String, java.lang.Integer, java.lang.Integer, java.lang.Integer, java.lang.Integer)
	 */
	@Override
	public RptAvanceBuses avancesBuses(String fechaInicio, String fechaFin,Integer idLocalidadOrigen, Integer idLocalidadDestino,Integer idServicio, Integer codigoBus) throws Exception {
		// TODO Auto-generated method stub
		return getReportesDAO().avancesBuses(fechaInicio, fechaFin, idLocalidadOrigen, idLocalidadDestino, idServicio, codigoBus);
	}


}
