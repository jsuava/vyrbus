/**
 * Proyecto		: SISVYR
 * Sistema		: Sistema de Ventas y Reservas
 * Descripci�n	:
 * Fecha		: 25/06/2014
 */
package com.cystesoft.vyrbus.service.business.impl;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.cystesoft.vyrbus.model.bean.Agencia;
import com.cystesoft.vyrbus.model.bean.Usuario;
import com.cystesoft.vyrbus.model.bean.VSAfiliacion;
import com.cystesoft.vyrbus.model.bean.VSAsegurado;
import com.cystesoft.vyrbus.model.bean.VSAsignacionCertificados;
import com.cystesoft.vyrbus.model.bean.VSCiudad;
import com.cystesoft.vyrbus.model.bean.VSContratante;
import com.cystesoft.vyrbus.model.bean.VSEncabezadoAfiliacion;
import com.cystesoft.vyrbus.model.bean.VSEstadoCivil;
import com.cystesoft.vyrbus.model.bean.VSEstructuraAsegurado;
import com.cystesoft.vyrbus.model.bean.VSEstructuraContratante;
import com.cystesoft.vyrbus.model.bean.VSEstructuraDeclaracion;
import com.cystesoft.vyrbus.model.bean.VSLiquidacion;
import com.cystesoft.vyrbus.model.bean.VSSexo;
import com.cystesoft.vyrbus.model.bean.VSTipoDocumento;
import com.cystesoft.vyrbus.model.bean.VSTipoProceso;
import com.cystesoft.vyrbus.model.dao.VentaSeguroDAO;
import com.cystesoft.vyrbus.service.business.VentaSeguroManager;
import com.cystesoft.vyrbus.service.exceptions.VSAfialiacionException;

/**
 * @author JABANTO
 *
 */
public class VentaSeguroManagerImpl implements VentaSeguroManager {
	private VentaSeguroDAO ventaSeguroDAO;

	/**
	 * @return the ventaSeguroDAO
	 */
	public VentaSeguroDAO getVentaSeguroDAO() {
		return ventaSeguroDAO;
	}
	/**
	 * @param ventaSeguroDAO the ventaSeguroDAO to set
	 */
	public void setVentaSeguroDAO(VentaSeguroDAO ventaSeguroDAO) {
		this.ventaSeguroDAO = ventaSeguroDAO;
	}


	/** TRANSACCIONES REFERIDAS AL TIPO DE DOCUMENTO*/
	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.service.business.VentaSeguroManager#buscarTipoDocumentoPorEstado(java.lang.String)
	 */
	@Override
	public List<VSTipoDocumento> buscarTipoDocumentoPorEstado(String estado)throws Exception {
		return getVentaSeguroDAO().buscarTipoDocumentoPorEstado(estado);
	}


	/** TRANSACCIONES REFERIDAS SEXO DEL ASEGURADO*/
	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.service.business.VentaSeguroManager#buscarSexoPorEstado(java.lang.String)
	 */
	@Override
	public List<VSSexo> buscarSexoPorEstado(String estado) throws Exception {
		return getVentaSeguroDAO().buscarSexoPorEstado(estado);
	}


	/** TRANSACCIONES REFERIDAS ESTADO CIVIL */
	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.service.business.VentaSeguroManager#buscarEstadoCivilPorEstado(java.lang.String)
	 */
	@Override
	public List<VSEstadoCivil> buscarEstadoCivilPorEstado(String estado)throws Exception {
		return getVentaSeguroDAO().buscarEstadoCivilPorEstado(estado);
	}


	/** TRANSACCIONES REFERIDAS A LAS CIUDADES */
	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.service.business.VentaSeguroManager#buscarCiudadesPorEstado(java.lang.String)
	 */
	@Override
	public List<VSCiudad> buscarCiudadesPorEstado(String estado)throws Exception {
		return getVentaSeguroDAO().buscarCiudadesPorEstado(estado);
	}


	/** TRANSACCIONES REFERIDAS AL ASEGURADO */
	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.service.business.VentaSeguroManager#buscarAseguradoByDocumento(java.lang.Integer, java.lang.String)
	 */
	@Override
	public VSAsegurado buscarAseguradoByDocumento(Integer idTipoDocumento,String numeroDocumento) throws Exception {
		return getVentaSeguroDAO().buscarAseguradoByDocumento(idTipoDocumento, numeroDocumento);
	}
	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.service.business.VentaSeguroManager#aseguradoActualizar(com.tepsa.sisvyr.model.bean.VSAsegurado)
	 */
	@Override
	@Transactional
	public void actualizarAsegurado(VSAsegurado asegurado) throws Exception {
		getVentaSeguroDAO().actualizarAsegurado(asegurado);
	}
	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.service.business.VentaSeguroManager#aseguradoGuardar(com.tepsa.sisvyr.model.bean.VSAsegurado)
	 */
	@Override
	@Transactional
	public void guardarAsegurado(VSAsegurado asegurado) throws Exception {
		getVentaSeguroDAO().guardarAsegurado(asegurado);
	}


	/** TRANSACCIONES REFERIDAS A LA LIQUIDACION*/
	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.service.business.VentaSeguroManager#buscarLiquidacion(java.lang.Integer, java.lang.String, java.lang.Integer)
	 */
	@Override
	public VSLiquidacion buscarLiquidacion(Integer idUsuario,Integer idAgencia,String fechaLiquidacion, Integer estado) throws Exception {
		return getVentaSeguroDAO().buscarLiquidacion(idUsuario,idAgencia, fechaLiquidacion, estado);
	}
	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.service.business.VentaSeguroManager#aperturarLiquidacion(com.tepsa.sisvyr.model.bean.VSLiquidacion)
	 */
	@Override
	@Transactional
	public void aperturarLiquidacion(VSLiquidacion vsLiquidacion)throws Exception {
		getVentaSeguroDAO().aperturarLiquidacion(vsLiquidacion);
	}
	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.service.business.VentaSeguroManager#cerrarLiquidacion(com.tepsa.sisvyr.model.bean.VSLiquidacion)
	 */
	@Override
	@Transactional
	public void cerrarLiquidacion(VSLiquidacion vsLiquidacion) throws Exception {
		getVentaSeguroDAO().cerrarLiquidacion(vsLiquidacion);
	}
	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.service.business.VentaSeguroManager#buscarLiquidacionVentas(java.lang.Integer)
	 */
	@Override
	public VSLiquidacion buscarLiquidacionVentas(Integer idLiquidacion)throws Exception {
		return getVentaSeguroDAO().buscarLiquidacionVentas(idLiquidacion);
	}


	/** TRANSACCIONES REFERIDAS A LA AFILIACION*/
	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.service.business.VentaSeguroManager#guardarAfiliacion(com.tepsa.sisvyr.model.bean.VSAfiliacion)
	 */
	@Override
	@Transactional
	public void guardarAfiliacion(VSAfiliacion vsAfiliacion) throws Exception {
		try{
			//valida duplicidad del certificado.
			Boolean existeCertificado=getVentaSeguroDAO().validarCertificadoAfiliacion(vsAfiliacion.getNumeroCertificado());
			if(existeCertificado)
				throw new VSAfialiacionException(VSAfialiacionException.DUPLICITY_CERTIFICADO);

			getVentaSeguroDAO().guardarAfiliacion(vsAfiliacion);

		}catch(VSAfialiacionException aex){
			if(aex.getTipo().intValue()==VSAfialiacionException.DUPLICITY_CERTIFICADO)
				throw new VSAfialiacionException(VSAfialiacionException.DUPLICITY_CERTIFICADO);
		}

	}
	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.service.business.VentaSeguroManager#validarBoletoAfiliacion(java.lang.String)
	 */
	@Override
	public Boolean validarBoletoAfiliacion(String numeroBoleto)throws Exception {
		return getVentaSeguroDAO().validarBoletoAfiliacion(numeroBoleto);
	}
	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.service.business.VentaSeguroManager#buscarAfiliacionesByProceso(java.lang.String, java.lang.String)
	 */
	@Override
	public List<VSAfiliacion> buscarAfiliacionesByProceso(String fechaInicio,String fechaFin) throws Exception {
		return getVentaSeguroDAO().buscarAfiliacionesByProceso(fechaInicio, fechaFin);
	}
	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.service.business.VentaSeguroManager#actualizaFechaProcesoAfiliacion(java.lang.String)
	 */
	@Override
	public void actualizaFechaProcesoAfiliacion(String Ids) throws Exception {
		getVentaSeguroDAO().actualizaFechaProcesoAfiliacion(Ids);
	}
	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.service.business.VentaSeguroManager#buscarAfiliacionesByConsulta(java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.Integer, java.lang.Integer, java.lang.String)
	 */
	@Override
	public List<VSAfiliacion> buscarAfiliacionesByConsulta(String fechaInicio,String fechaFin, String tipoPasajero, String numDoctoPax,Integer idUsuario, Integer idAgencia, String boleto)throws Exception {
		return getVentaSeguroDAO().buscarAfiliacionesByConsulta(fechaInicio, fechaFin, tipoPasajero, numDoctoPax, idUsuario, idAgencia, boleto);
	}
	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.service.business.VentaSeguroManager#buscarAgenciasAfiliacionesByConsulta(java.lang.String, java.lang.String, java.lang.Integer)
	 */
	@Override
	public List<Usuario> buscarUsuariosAfiliacionesByConsulta(String fechaInicio, String fechaFin, Integer idAgencia)throws Exception {
		return getVentaSeguroDAO().buscarUsuariosAfiliacionesByConsulta(fechaInicio, fechaFin, idAgencia);
	}
	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.service.business.VentaSeguroManager#buscarAgenciasAfiliacionesByConsulta(java.lang.String, java.lang.String)
	 */
	@Override
	public List<Agencia> buscarAgenciasAfiliacionesByConsulta(String fechaInicio, String fechaFin) throws Exception {
		return getVentaSeguroDAO().buscarAgenciasAfiliacionesByConsulta(fechaInicio, fechaFin);
	}
	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.service.business.VentaSeguroManager#anularCertificado(java.lang.String)
	 */
	@Override
	public void anularCertificado(String numeroCertificado) throws Exception {
		// TODO Auto-generated method stub
		getVentaSeguroDAO().anularCertificado(numeroCertificado);
	}


	/** TRANSACCIONES REFERIDAS AL TIPO DE PROCESO*/
	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.service.business.VentaSeguroManager#buscarTipoProcesoPorEstado(java.lang.String)
	 */
	@Override
	public List<VSTipoProceso> buscarTipoProcesoPorEstado(String estado)throws Exception {
		return getVentaSeguroDAO().buscarTipoProcesoPorEstado(estado);
	}


	/** TRANSACCIONES REFERIDAS AL TIPO DE PROCESO*/
	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.service.business.VentaSeguroManager#buscarContratantePorID(java.lang.Integer)
	 */
	@Override
	public VSContratante buscarContratantePorID(Integer id) throws Exception {
		return getVentaSeguroDAO().buscarContratantePorID(id);
	}


	/** TRANSACCIONES REFERIDAS AL ENCABEZADO DE LA AFILIACION*/
	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.service.business.VentaSeguroManager#guardarEncabezadoAfiliacion(com.tepsa.sisvyr.model.bean.VSEncabezadoAfiliacion)
	 */
	@Override
	@Transactional
	public void guardarEncabezadoAfiliacion(VSEncabezadoAfiliacion vsEncabezadoAfiliacion) throws Exception {
		getVentaSeguroDAO().guardarEncabezadoAfiliacion(vsEncabezadoAfiliacion);
	}


	/** TRANSACCIONES REFERIDAS A LA ESTRUCTURA DE LA DECLARACION*/
	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.service.business.VentaSeguroManager#guardarEstructuraDeclaracion(com.tepsa.sisvyr.model.bean.VSEstructuraDeclaracion)
	 */
	@Override
	@Transactional
	public void guardarEstructuraDeclaracion(VSEstructuraDeclaracion vsEstructuraDeclaracion) throws Exception {
		getVentaSeguroDAO().guardarEstructuraDeclaracion(vsEstructuraDeclaracion);
	}


	/** TRANSACCIONES REFERIDAS A LA ESTRUCTURA DEL CONTRATANTE*/
	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.service.business.VentaSeguroManager#guaradrEstructuraContrantante(com.tepsa.sisvyr.model.bean.VSEstructuraContratante)
	 */
	@Override
	@Transactional
	public void guardarEstructuraContrantante(VSEstructuraContratante vsEstructuraContratante) throws Exception {
		getVentaSeguroDAO().guardarEstructuraContrantante(vsEstructuraContratante);
	}


	/** TRANSACCIONES REFERIDAS A LA ESTRUCTURA DEL ASEGURADO*/
	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.service.business.VentaSeguroManager#guardarEstructuraAsegurado(com.tepsa.sisvyr.model.bean.VSEstructuraAsegurado)
	 */
	@Override
	@Transactional
	public void guardarEstructuraAsegurado(VSEstructuraAsegurado vsEstructuraAsegurado) throws Exception {
		getVentaSeguroDAO().guardarEstructuraAsegurado(vsEstructuraAsegurado);
	}



	/** TRANSACCIONES REFERIDAS A LA ASIGNACION DE CERTIFICADOS*/
	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.service.business.VentaSeguroManager#buscarAsignacionCertificados(java.lang.Integer)
	 */
	@Override
	public VSAsignacionCertificados buscarAsignacionCertificadosPorIdAgencia(Integer idAgencia) throws Exception {
		return getVentaSeguroDAO().buscarAsignacionCertificadosPorIdAgencia(idAgencia);
	}
	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.service.business.VentaSeguroManager#buscarAsignacionCertificadosPorX(java.util.TreeMap, java.util.List)
	 */
	@Override
	public List<VSAsignacionCertificados> buscarAsignacionCertificados(Integer idAgencia) throws Exception {
		// TODO Auto-generated method stub
		return getVentaSeguroDAO().buscarAsignacionCertificados(idAgencia);
	}
	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.service.business.VentaSeguroManager#buscarAsignacionCertificados()
	 */
	@Override
	public List<VSAsignacionCertificados> buscarAsignacionCertificados()throws Exception {
		// TODO Auto-generated method stub
		return getVentaSeguroDAO().buscarAsignacionCertificados(null);
	}
	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.service.business.VentaSeguroManager#guardarAsignacionCertificados(com.tepsa.sisvyr.model.bean.VSAsignacionCertificados)
	 */
	@Transactional
	@Override
	public void guardarAsignacionCertificados(VSAsignacionCertificados vsAsignacionCertificados) throws Exception {
		// TODO Auto-generated method stub
		getVentaSeguroDAO().save(vsAsignacionCertificados);
	}
	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.service.business.VentaSeguroManager#actualizarAsignacionCertificados(com.tepsa.sisvyr.model.bean.VSAsignacionCertificados)
	 */
	@Transactional
	@Override
	public void actualizarAsignacionCertificados(VSAsignacionCertificados vsAsignacionCertificados) throws Exception {
		// TODO Auto-generated method stub
		getVentaSeguroDAO().update(vsAsignacionCertificados);
	}
	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.service.business.VentaSeguroManager#inactivarAsignacionCertificado(java.lang.Integer)
	 */
	@Override
	public void inactivarAsignacionCertificado(Integer id)throws Exception {
		// TODO Auto-generated method stub
		getVentaSeguroDAO().inactivarAsignacionCertificado(id);
	}
//	/* (non-Javadoc)
//	 * @see com.tepsa.sisvyr.service.business.VentaSeguroManager#buscarAsignacionCertificadoPorX(java.util.TreeMap)
//	 */
//	@Override
//	public List<VSAsignacionCertificados> buscarAsignacionCertificadoPorX(TreeMap<String, Object> criteriosBusqueda) throws Exception {
//		// TODO Auto-generated method stub
//		return getVentaSeguroDAO().buscarAsignacionCertificadoPorX(criteriosBusqueda);
//	}
	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.service.business.VentaSeguroManager#ValidarAsignacionCertificado(java.lang.Long)
	 */
	@Override
	public VSAsignacionCertificados ValidarAsignacionCertificado(Long correlativo) throws Exception {
		// TODO Auto-generated method stub
		return getVentaSeguroDAO().ValidarAsignacionCertificado(correlativo);
	}



}
