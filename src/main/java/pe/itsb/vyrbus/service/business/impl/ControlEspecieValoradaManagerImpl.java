/**
 * Proyecto		: SISVYR
 * Sistema		: Sistema de Ventas y Reservas
 * Descripci�n	: Implementaci�n de m�todos que permiten el acceso al modelo.
 * Autor		: Jos� Avalos
 * Fecha		: 28/09/2012
 */
package pe.itsb.vyrbus.service.business.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

import org.springframework.transaction.annotation.Transactional;

import pe.itsb.vyrbus.model.bean.ControlEspecieValorada;
import pe.itsb.vyrbus.model.bean.ControlEspecieValoradaID;
import pe.itsb.vyrbus.model.dao.ControlEspecieValoradaDAO;
import pe.itsb.vyrbus.service.business.ControlEspecieValoradaManager;
import pe.itsb.vyrbus.service.exceptions.CorrelativoException;
import pe.itsb.vyrbus.service.util.Constantes;

/**
 * @author Jose
 *
 */
public class ControlEspecieValoradaManagerImpl implements ControlEspecieValoradaManager {
	private ControlEspecieValoradaDAO controlEspecieValoradaDAO;

	/**
	 * @return the controlEspecieValoradaDAO
	 */
	public ControlEspecieValoradaDAO getControlEspecieValoradaDAO() {
		return controlEspecieValoradaDAO;
	}
	/**
	 * @param controlEspecieValoradaDAO the controlEspecieValoradaDAO to set
	 */
	public void setControlEspecieValoradaDAO(
			ControlEspecieValoradaDAO controlEspecieValoradaDAO) {
		this.controlEspecieValoradaDAO = controlEspecieValoradaDAO;
	}


	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.service.business.ControlEspecieValoradaManager#buscarPorX(java.util.TreeMap, java.util.List)
	 */
	@Override
	public ArrayList<ControlEspecieValorada> buscarPorX(TreeMap<String, Object> criteriosBusqueda, List<String> criteriosOrdenar) throws Exception {
		return getControlEspecieValoradaDAO().buscarPorX(criteriosBusqueda, criteriosOrdenar);
	}

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.service.business.ControlEspecieValoradaManager#guardar(com.tepsa.sisvyr.model.bean.ControlEspecieValorada)
	 */
	@Override
	@Transactional
	public int guardar(ControlEspecieValorada controlEspecieValorada) throws Exception {
		try{

			if(controlEspecieValorada.getTipoComprobante().getId().intValue()==Constantes.ID_TIPCOM_NOTA_CREDITO ||
					controlEspecieValorada.getTipoComprobante().getId().intValue()==Constantes.ID_TIPCOM_NOTA_DEBITO) {

				TreeMap<String, Object> criteriosBusqueda = new TreeMap<>();
				criteriosBusqueda.put("empresa", controlEspecieValorada.getEmpresa());
				criteriosBusqueda.put("tipoComprobante", controlEspecieValorada.getTipoComprobante());
				criteriosBusqueda.put("usuarioHardware", controlEspecieValorada.getUsuarioHardware());
				List<?> result = getControlEspecieValoradaDAO().buscarPorX(criteriosBusqueda, null);

				/*Valida duplicidad del tipo comprobante y UsuarioHardware*/
				if(result.size()>0)
					throw new CorrelativoException(CorrelativoException.DUPLICADO);

				ControlEspecieValorada controlEspecieValorada2 = (ControlEspecieValorada)controlEspecieValorada.clone();
//				ControlEspecieValoradaID controlEspecieValoradaID = (ControlEspecieValoradaID)controlEspecieValorada.getControlEspecieValoradaID().clone();

				controlEspecieValorada.setSerie("B"+controlEspecieValorada.getSerie());
//				controlEspecieValorada.getControlEspecieValoradaID().setStrSerie(controlEspecieValorada.getSerie());
				controlEspecieValorada.setSecuenciador(controlEspecieValorada.getSecuenciador()+controlEspecieValorada.getSerie());
				controlEspecieValorada.setAplica(1);

				/*Inserta el control especie valorada*/
				getControlEspecieValoradaDAO().guardar(controlEspecieValorada);
				/*Generar secuenciador de la caja*/
				getControlEspecieValoradaDAO().generarSecuenciador(controlEspecieValorada.getSecuenciador(), controlEspecieValorada.getCorrelativoActual());

				controlEspecieValorada2.setSerie("F"+controlEspecieValorada2.getSerie());
//				controlEspecieValoradaID.setStrSerie(controlEspecieValorada2.getSerie());
//				controlEspecieValorada2.setControlEspecieValoradaID(controlEspecieValoradaID);
				controlEspecieValorada2.setSecuenciador(controlEspecieValorada2.getSecuenciador()+controlEspecieValorada2.getSerie());
				controlEspecieValorada2.setAplica(2);

				/*Inserta el control especie valorada*/
				getControlEspecieValoradaDAO().guardar(controlEspecieValorada2);
				/*Generar secuenciador de la caja*/
				getControlEspecieValoradaDAO().generarSecuenciador(controlEspecieValorada2.getSecuenciador(), controlEspecieValorada2.getCorrelativoActual());

				return Constantes.CORRECT;
			}else {
				TreeMap<String, Object> criteriosBusqueda = new TreeMap<>();
				criteriosBusqueda.put("empresa", controlEspecieValorada.getEmpresa());
				criteriosBusqueda.put("tipoComprobante", controlEspecieValorada.getTipoComprobante());
				criteriosBusqueda.put("usuarioHardware", controlEspecieValorada.getUsuarioHardware());
				//criteriosBusqueda.put("estadoRegistro", Constantes.ACTIVO);
				List<?> result = getControlEspecieValoradaDAO().buscarPorX(criteriosBusqueda, null);

				/*Valida duplicidad del tipo comprobante y UsuarioHardware*/
				if(result.size()>0)
					throw new CorrelativoException(CorrelativoException.DUPLICADO); //ControlEspecieValoradaDuplicidadException();

				criteriosBusqueda = new TreeMap<>();
				criteriosBusqueda.put("empresa", controlEspecieValorada.getEmpresa());
				criteriosBusqueda.put("serie", controlEspecieValorada.getSerie());
				criteriosBusqueda.put("tipoComprobante", controlEspecieValorada.getTipoComprobante());
				result = getControlEspecieValoradaDAO().buscarPorX(criteriosBusqueda, null);

				/*Valida duplicidad de la serie*/
				if(result.size()>0)
					throw new CorrelativoException(CorrelativoException.SERIE_DUPLICADA); //ControlEspecieValoradaDuplicidadException();

				/*Inserta el control especie valorada*/
				getControlEspecieValoradaDAO().guardar(controlEspecieValorada);

				/*Generar secuenciador de la caja*/
				getControlEspecieValoradaDAO().generarSecuenciador(controlEspecieValorada.getSecuenciador(), controlEspecieValorada.getCorrelativoActual());

				/*Inserta los correlativos para para la validacion*/
	//			guardarValidacionEspecieValorada(controlEspecieValorada);

				return Constantes.CORRECT;
			}
		}catch (CorrelativoException c){
			if(c.getTipo().intValue()==CorrelativoException.DUPLICADO)
				throw new CorrelativoException(CorrelativoException.DUPLICADO);
			else if (c.getTipo().intValue()==CorrelativoException.SERIE_DUPLICADA){
				throw new CorrelativoException(CorrelativoException.SERIE_DUPLICADA);
			}
			else
				throw new Exception(c);
		}catch(Exception ex){
			throw new Exception(ex);
		}
	}

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.service.business.ControlEspecieValoradaManager#actualizar(com.tepsa.sisvyr.model.bean.ControlEspecieValorada)
	 */
	@Override
	@Transactional
	public int actualizar(ControlEspecieValorada controlEspecieValorada) throws Exception {
		TreeMap<String, Object> criteriosBusqueda = new TreeMap<>();
		criteriosBusqueda.put("tipoComprobante", controlEspecieValorada.getTipoComprobante());
		criteriosBusqueda.put("usuarioHardware", controlEspecieValorada.getUsuarioHardware());
		criteriosBusqueda.put("estadoRegistro", Constantes.VALUE_ACTIVO);
		List<?> result = getControlEspecieValoradaDAO().buscarPorX(criteriosBusqueda, null);

		/*actualiza el control especie valorada*/
		getControlEspecieValoradaDAO().actualizar(controlEspecieValorada);

		ControlEspecieValorada controlEspecieValoradaFirst = null;
		if(result.size() > 0) {
			controlEspecieValoradaFirst = (ControlEspecieValorada)result.get(0);
			/*eliminamos el secuenciador*/
			getControlEspecieValoradaDAO().eliminarSecuenciador(controlEspecieValoradaFirst.getSecuenciador());
		}

		/*creamos el secuenciador*/
		getControlEspecieValoradaDAO().generarSecuenciador(controlEspecieValorada.getSecuenciador(), controlEspecieValorada.getCorrelativoActual());

		/*Inserta los correlativos para para la validacion*/
//		guardarValidacionEspecieValorada(controlEspecieValorada);

		return Constantes.CORRECT;
	}

	@Override
	@Transactional
	public void inactivar(Integer id)throws Exception {
		 getControlEspecieValoradaDAO().inactivar(id);
	}
//	public void inactivar(ControlEspecieValoradaID controlEspecieValoradaID)throws Exception {
//		 getControlEspecieValoradaDAO().inactivar(controlEspecieValoradaID);
//	}

	/*
	 * (non-Javadoc)
	 * @see pe.itsb.vyrbus.service.business.ControlEspecieValoradaManager#buscarEspecieValoradas(java.lang.Integer, java.lang.Integer, java.lang.Integer, java.lang.Integer)
	 */
	@Override
	public List<ControlEspecieValorada> buscarEspecieValoradas(Integer idAgencia, Integer idTipoComprobante, Integer idUsuarioHardware, Integer idEmpresa) throws Exception {
		return getControlEspecieValoradaDAO().buscarEspecieValoradas(idAgencia, idTipoComprobante,idUsuarioHardware, idEmpresa);
	}
	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.service.business.ControlEspecieValoradaManager#validaEVOtrasCajas(java.lang.Integer, java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	public List<ControlEspecieValorada> validaEVOtrasCajas(Integer idUsuarioHardware,String serie, String inicial, String Final) throws Exception {
		return getControlEspecieValoradaDAO().validaEVOtrasCajas(idUsuarioHardware, serie, inicial, Final);
	}
	/**
	 * Busca las especies valoradas asignadas por maquina de acuerdo a la agencia.
	 * @param idAgencia	: Identificador de la agencia.
	 * @return Lista de Maquinas Pc con sus respectivas especies valoradas asignadas.
	 * @throws Exception
	 */
	@Override
	public List<ControlEspecieValorada> buscarEspecieValoradaPorAgencia(Integer idAgencia)throws Exception{
		return getControlEspecieValoradaDAO().buscarEspecieValoradaPorAgencia(idAgencia);
	}
	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.model.dao.ControlEspecieValoradaDAO#actualizarCorrelativoEspecieValorada(java.lang.Integer, java.lang.Integer, java.lang.String, int)
	 */
	@Override
	public int actualizarCorrelativoEspecieValorada(Integer idTipCom, Integer idUsuHar, String serie, long correlativo) throws Exception {
		return getControlEspecieValoradaDAO().actualizarCorrelativoEspecieValorada(idTipCom, idUsuHar, serie, correlativo);
	}
	/* (non-Javadoc)
	 * @see pe.itsb.vyrbus.service.business.ControlEspecieValoradaManager#ejecutarSeqCorrelativo(pe.itsb.vyrbus.model.bean.ControlEspecieValorada)
	 */
	@Override
	public ControlEspecieValorada ejecutarSecuenciador(ControlEspecieValorada controlEspecieValorada) throws Exception {
		return getControlEspecieValoradaDAO().ejecutarSecuenciador(controlEspecieValorada);
	}

//	@Transactional
//	/**
//	 * Guarda los correlativos para la validacion al momento del cierre de caja.
//	 * @param controlEspecieValorada
//	 * @throws Exception
//	 */
//	private void guardarValidacionEspecieValorada(ControlEspecieValorada controlEspecieValorada)throws Exception{
//		ValidacionEspecieValorada validacionEspecieValorada=new ValidacionEspecieValorada();
//		validacionEspecieValorada.setFechaLiquidacion(new Date());
//		validacionEspecieValorada.setTipoComprobante(controlEspecieValorada.getTipoComprobante());
//		validacionEspecieValorada.setUsuarioHardware(controlEspecieValorada.getUsuarioHardware());
//		validacionEspecieValorada.setSerie(controlEspecieValorada.getSerie());
//		validacionEspecieValorada.setCorrelativoInicial(controlEspecieValorada.getCorrelativoInicio());
//		validacionEspecieValorada.setCorrelativoFinal(controlEspecieValorada.getCorrelativoFin());
//		validacionEspecieValorada.setEstadoRegistro(Constantes.VALUE_ACTIVO);
//		UtilData.auditarRegistro(validacionEspecieValorada, new Usuario(controlEspecieValorada.getUsuarioModificacion()), Executions.getCurrent());
//		getValidacionEspecieValoradaDAO().guardar(validacionEspecieValorada);
//	}
}
