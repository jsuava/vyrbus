/**
 * Proyecto		: SISVYR
 * Sistema		: Sistema de Ventas y Reservas
 * Descripción	: Implementacion de metodos que permiten el acceso al modelo.
 * Autor		: José Avalos
 * Fecha		: 28/09/2012
 */
package pe.itsb.vyrbus.service.business.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

import org.springframework.transaction.annotation.Transactional;

import pe.itsb.vyrbus.model.bean.TipoComprobante;
import pe.itsb.vyrbus.model.dao.TipoComprobanteDAO;
import pe.itsb.vyrbus.service.business.TipoComprobanteManager;
import pe.itsb.vyrbus.service.exceptions.DenominacionDuplicadaException;
import pe.itsb.vyrbus.service.exceptions.NombreCortoDuplicadoException;
import pe.itsb.vyrbus.service.util.Constantes;

/**
 * @author Jose
 *
 */
public class TipoComprobanteManagerImpl implements TipoComprobanteManager {
	private TipoComprobanteDAO tipoComprobanteDAO;

	/**
	 * @return the tipoComprobanteDAO
	 */
	public TipoComprobanteDAO getTipoComprobanteDAO() {
		return tipoComprobanteDAO;
	}
	/**
	 * @param tipoComprobanteDAO the tipoComprobanteDAO to set
	 */
	public void setTipoComprobanteDAO(TipoComprobanteDAO tipoComprobanteDAO) {
		this.tipoComprobanteDAO = tipoComprobanteDAO;
	}

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.service.business.TipoComprobanteManager#buscarPorEstadoRegistro(java.lang.String, java.lang.String)
	 */
	@Override
	@Transactional(readOnly=true)
	public ArrayList<TipoComprobante> buscarPorEstadoRegistro(String estado, String criterioOrden) throws Exception {
		return getTipoComprobanteDAO().buscarPorEstadoRegistro(estado, criterioOrden);
	}

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.service.business.TipoComprobanteManager#buscarPorX(java.util.TreeMap, java.util.List)
	 */
	@Override
	@Transactional(readOnly=true)
	public ArrayList<TipoComprobante> buscarPorX(TreeMap<String, Object> criteriosBusqueda, List<String> criteriosOrdenar) throws Exception {
		return getTipoComprobanteDAO().buscarPorX(criteriosBusqueda, criteriosOrdenar);
	}

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.service.business.TipoComprobanteManager#buscarPorId(java.lang.Long)
	 */
	@Override
	@Transactional(readOnly=true)
	public TipoComprobante buscarPorId(Long id) throws Exception {
		return getTipoComprobanteDAO().buscarPorId(id);
	}

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.service.business.TipoComprobanteManager#guardar(com.tepsa.sisvyr.model.bean.TipoComprobante)
	 */
	@Override
	@Transactional
	public void guardar(TipoComprobante tipoComprobante) throws Exception {
		try{
			TreeMap<String, Object> criteriosBusqueda = new TreeMap<>();
			criteriosBusqueda.put("denominacion", tipoComprobante.getDenominacion());
			criteriosBusqueda.put("estadoRegistro", Constantes.VALUE_ACTIVO);
			List<?> resultDenominacion = getTipoComprobanteDAO().buscarPorX(criteriosBusqueda, null);
			/*Valida duplicidad de la denominación*/
			if(resultDenominacion.size()>0)
				throw new DenominacionDuplicadaException();

			criteriosBusqueda.remove("denominacion");
			criteriosBusqueda.put("abreviatura", tipoComprobante.getAbreviatura());
			List<?> resultNombreCorto = getTipoComprobanteDAO().buscarPorX(criteriosBusqueda, null);
			/*Valida duplicidad del Nombre corto*/
			if (resultNombreCorto.size()>0)
				throw new NombreCortoDuplicadoException();

			getTipoComprobanteDAO().guardar(tipoComprobante);

		}catch (DenominacionDuplicadaException rsdex){
			throw new DenominacionDuplicadaException();
		}catch(NombreCortoDuplicadoException rdnex){
			throw new NombreCortoDuplicadoException();
		}catch(Exception ex){
			throw new Exception(ex);
		}
	}

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.service.business.TipoComprobanteManager#actualizar(com.tepsa.sisvyr.model.bean.TipoComprobante)
	 */
	@Override
	@Transactional
	public void actualizar(TipoComprobante tipoComprobante) throws Exception {
		try{
			TreeMap<String, Object> criteriosBusqueda = new TreeMap<>();
			criteriosBusqueda.put("denominacion", tipoComprobante.getDenominacion());
			criteriosBusqueda.put("estadoRegistro", Constantes.VALUE_ACTIVO);
			List<?> resultDenominacion = getTipoComprobanteDAO().buscarPorX(criteriosBusqueda, null);

			criteriosBusqueda.remove("denominacion");
			criteriosBusqueda.put("abreviatura", tipoComprobante.getAbreviatura());
			List<?> resultnombreCorto = getTipoComprobanteDAO().buscarPorX(criteriosBusqueda, null);

			/*Valida duplicidad de la denominación*/
			for (Object element : resultDenominacion) {
				TipoComprobante otipoComprobante = (TipoComprobante) element;
					if (!(otipoComprobante.getId() == tipoComprobante.getId()))
						throw new DenominacionDuplicadaException();
				}

			/*Valida duplicidad del Nombre corto*/
			for (Object element : resultnombreCorto) {
				TipoComprobante otipoComprobante= (TipoComprobante) element;
					if (!(otipoComprobante.getId() == tipoComprobante.getId()))
						throw new NombreCortoDuplicadoException();
				}

			getTipoComprobanteDAO().actualizar(tipoComprobante);

		}catch (DenominacionDuplicadaException rsdex){
			throw new DenominacionDuplicadaException();
		}catch(NombreCortoDuplicadoException rdnex){
			throw new NombreCortoDuplicadoException();
		}catch(Exception ex){
			throw new Exception(ex);
		}
	}

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.service.business.TipoComprobanteManager#inactivar(java.lang.Long)
	 */
	@Override
	@Transactional
	public void inactivar(Long id) throws Exception {
		getTipoComprobanteDAO().inactivar(id);
	}

}
