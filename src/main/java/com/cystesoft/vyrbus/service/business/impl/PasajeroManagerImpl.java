/**
 * Proyecto		: SISVYR
 * Sistema		: Sistema de Ventas y Reservas
 * Descripción	: Implementación de métodos que permiten el acceso al modelo.
 * Autor		: José Sullo Avalos
 * Fecha		: 28/09/2012
 */
package com.cystesoft.vyrbus.service.business.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

import org.springframework.transaction.annotation.Transactional;

import com.cystesoft.vyrbus.model.bean.DestinatariosEmails;
import com.cystesoft.vyrbus.model.bean.Pasajero;
import com.cystesoft.vyrbus.model.bean.TipoDocumento;
import com.cystesoft.vyrbus.model.dao.PasajeroDAO;
import com.cystesoft.vyrbus.service.business.PasajeroManager;
import com.cystesoft.vyrbus.service.exceptions.DocumentoPaxDuplicadoException;
import com.cystesoft.vyrbus.service.locator.ServiceLocator;
import com.cystesoft.vyrbus.service.util.Constantes;
import com.cystesoft.vyrbus.service.util.Sendmail;
import com.cystesoft.vyrbus.service.util.WSMTC;

/**
 * @author Jose
 *
 */
public class PasajeroManagerImpl implements PasajeroManager {
	private PasajeroDAO pasajeroDAO;

	/**
	 * @return the pasajeroDAO
	 */
	public PasajeroDAO getPasajeroDAO() {
		return pasajeroDAO;
	}
	/**
	 * @param pasajeroDAO the pasajeroDAO to set
	 */
	public void setPasajeroDAO(PasajeroDAO pasajeroDAO) {
		this.pasajeroDAO = pasajeroDAO;
	}

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.service.business.PasajeroManager#buscarPorEstadoRegistro(java.lang.String, java.lang.String)
	 */
	@Override
	public ArrayList<Pasajero> buscarPorEstadoRegistro(String estado, String criterioOrden) throws Exception {
		return getPasajeroDAO().buscarPorEstadoRegistro(estado, criterioOrden);
	}

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.service.business.PasajeroManager#buscarPorX(java.util.TreeMap, java.util.List)
	 */
	@Override
	public ArrayList<Pasajero> buscarPorX(TreeMap<String, Object> criteriosBusqueda, List<String> criteriosOrdenar) throws Exception {
		return getPasajeroDAO().buscarPorX(criteriosBusqueda, criteriosOrdenar);
	}

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.service.business.PasajeroManager#buscarPorId(java.lang.Long)
	 */
	@Override
	public Pasajero buscarPorId(Long id) throws Exception {
		Pasajero pasajero= getPasajeroDAO().buscarPorId(id);
		if(pasajero.getTipoDocumento().getId().intValue()==Constantes.ID_TIPDOC_DNI && pasajero.getValidadoReniec().intValue()==Constantes.FALSE_VALUE && pasajero.getNumeroDocumento()!=null){
			/*Actualiza los datos del pasajero con los de la reniec*/
			pasajero=WSMTC.updateDatosPaxByReniec(pasajero);
		}
		return pasajero;
	}

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.service.business.PasajeroManager#guardar(com.tepsa.sisvyr.model.bean.Pasajero)
	 */
	@Override
	@Transactional
	public void guardar(Pasajero pasajero) throws Exception {
		try{
			TreeMap<String, Object> criteriosBusqueda = new TreeMap<>();
			criteriosBusqueda.put("tipoDocumento", pasajero.getTipoDocumento());
			criteriosBusqueda.put("numeroDocumento", pasajero.getNumeroDocumento());
			criteriosBusqueda.put("estadoRegistro", Constantes.VALUE_ACTIVO);
			List<?> result = getPasajeroDAO().buscarPorX(criteriosBusqueda, null);
			/*Valida duplicidad del número de Documento del pasajero*/
			if(result.size()>0)
				throw new DocumentoPaxDuplicadoException();

			getPasajeroDAO().guardar(pasajero);


			/*Cuando el tipo de documento del pasajero es diferente s los personales, envia una alerta*/
			TipoDocumento tipoDocumento=ServiceLocator.getTipoDocumentoManager().buscarPorId(pasajero.getTipoDocumento().getId().longValue());
			if(tipoDocumento.getTipo().intValue()!=Constantes.FALSE_VALUE){
				enviarAlerta(pasajero,tipoDocumento, false);
			}

		}catch(DocumentoPaxDuplicadoException dpdnex){
			throw new DocumentoPaxDuplicadoException();
		}catch(Exception ex){
			throw new Exception(ex);
		}
	}

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.service.business.PasajeroManager#actualizar(com.tepsa.sisvyr.model.bean.Pasajero)
	 */
	@Override
	@Transactional
	public void actualizar(Pasajero pasajero) throws Exception {
		try{
			TreeMap<String, Object> criteriosBusqueda = new TreeMap<>();
			criteriosBusqueda.put("tipoDocumento", pasajero.getTipoDocumento());
			criteriosBusqueda.put("numeroDocumento", pasajero.getNumeroDocumento());
			criteriosBusqueda.put("estadoRegistro", Constantes.VALUE_ACTIVO);
			List<?> resul = getPasajeroDAO().buscarPorX(criteriosBusqueda, null);
			/*Valida duplicidad del número de Documento del pasajero*/
			for (Object element : resul) {
				Pasajero opasajero= (Pasajero) element;
				if (!(opasajero.getId().equals(pasajero.getId())))
					throw new DocumentoPaxDuplicadoException();
			}


			getPasajeroDAO().actualizar(pasajero);

			/*Cuando el tipo de documento del pasajero es diferente s los personales, envia una alerta*/
			TipoDocumento tipoDocumento=ServiceLocator.getTipoDocumentoManager().buscarPorId(pasajero.getTipoDocumento().getId().longValue());
			if(tipoDocumento.getTipo().intValue()!=Constantes.FALSE_VALUE){
				enviarAlerta(pasajero,tipoDocumento, true);
			}

		}catch (DocumentoPaxDuplicadoException dpdex){
			throw new DocumentoPaxDuplicadoException();
		}catch(Exception ex){
			throw new Exception(ex);
		}
	}

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.service.business.PasajeroManager#inactivar(java.lang.Long)
	 */
	@Override
	@Transactional
	public void inactivar(Long id) throws Exception {
		getPasajeroDAO().inactivar(id);
	}
	/*
	 * (non-Javadoc)
	 * @see com.tepsa.sisvyr.service.business.PasajeroManager#buscarPorFullTextIndex(java.lang.String[])
	 */
	@Override
	public ArrayList<Pasajero> buscarPorFullTextIndex(String[] nombres) throws Exception{
		return getPasajeroDAO().buscarPorFullTextIndex(nombres);
	}


	public static void enviarAlerta(Pasajero pasajero,TipoDocumento tipoDocumento, boolean update){
		try {
			String mensaje="Se ha "+(update?"actualizado":"registrado")+" el Siguiente Pasajero con un tipo de "+tipoDocumento.getDenominacion()+" ";
			mensaje+="\n Tipo :"+tipoDocumento.getDenominacion();
			mensaje+="\n Número :"+pasajero.getNumeroDocumento();
			mensaje+="\n Pasajero :"+pasajero.toString();
			mensaje+="\n Usuario :"+pasajero.getUsuarioModificacion();

			String toAddress="jabanto@tepsa.com.pe";

			//Envia E-Mail
			mensaje+="\n\n NOTA: [Este buzon es de envio automático, por favor no responda.]";
			DestinatariosEmails window = new DestinatariosEmails();
			window.setEmails("TO:"+toAddress);
			Sendmail.enviaEmail(mensaje,"Pasajero con tipo documento "+tipoDocumento.getDenominacion()+" ", window);
		} catch (Exception e) {
		}
	}
}
