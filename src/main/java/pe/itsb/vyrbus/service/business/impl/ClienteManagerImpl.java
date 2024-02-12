/**
 * Proyecto		: SISVYR
 * Sistema		: Sistema de Ventas y Reservas
 * Descripci�n	: Implementacion de metodos que permiten el acceso al modelo.
 * Autor		: Jos� Avalos
 * Fecha		: 27/09/2012
 */
package pe.itsb.vyrbus.service.business.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

import org.springframework.transaction.annotation.Transactional;

import pe.itsb.vyrbus.model.bean.Cliente;
import pe.itsb.vyrbus.model.dao.ClienteDAO;
import pe.itsb.vyrbus.service.business.ClienteManager;
import pe.itsb.vyrbus.service.exceptions.CancelaGrabacionException;
import pe.itsb.vyrbus.service.exceptions.RazonSocialDuplicadoException;
import pe.itsb.vyrbus.service.exceptions.RucDuplicadoException;
import pe.itsb.vyrbus.service.util.Constantes;

/**
 * @author Jose
 *
 */
public class ClienteManagerImpl implements ClienteManager {
	private ClienteDAO clienteDAO;

	/**
	 * @return the clienteDAO
	 */
	public ClienteDAO getClienteDAO() {
		return clienteDAO;
	}
	/**
	 * @param clienteDAO the clienteDAO to set
	 */
	public void setClienteDAO(ClienteDAO clienteDAO) {
		this.clienteDAO = clienteDAO;
	}

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.service.business.ClienteManager#buscarPorEstadoRegistro(java.lang.String, java.lang.String)
	 */
	@Override
	@Transactional(readOnly=true)
	public ArrayList<Cliente> buscarPorEstadoRegistro(String estado, String criterioOrden)throws Exception {
		return getClienteDAO().buscarPorEstadoRegistro(estado, criterioOrden);
	}

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.service.business.ClienteManager#buscarPorX(java.util.TreeMap, java.util.List)
	 */
	@Override
	@Transactional(readOnly=true)
	public ArrayList<Cliente> buscarPorX(TreeMap<String, Object> criteriosBusqueda, List<String> criteriosOrdenar)throws Exception {
		return getClienteDAO().buscarPorX(criteriosBusqueda, criteriosOrdenar);
	}

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.service.business.ClienteManager#buscarPorId(java.lang.Long)
	 */
	@Override
	@Transactional(readOnly=true)
	public Cliente buscarPorId(Long id)throws Exception {
		return getClienteDAO().buscarPorId(id);
	}

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.service.business.ClienteManager#guardar(com.tepsa.sisvyr.model.bean.Cliente)
	 */
	@Override
	@Transactional
	public void guardar(Cliente cliente)throws Exception {
		try{
			TreeMap<String, Object> criteriosBusqueda = new TreeMap<>();
			criteriosBusqueda.put("numeroDocumento", cliente.getNumeroDocumento());
			criteriosBusqueda.put("estadoRegistro", Constantes.VALUE_ACTIVO);
			List<?> result = getClienteDAO().buscarPorX(criteriosBusqueda, null);
			/*Valida duplicidad de RUC*/
			if(result.size()>0)
				throw new RucDuplicadoException();

			criteriosBusqueda.remove("numeroDocumento");
			criteriosBusqueda.put("razonSocial", cliente.getRazonSocial());
			List<?> resultRS = getClienteDAO().buscarPorX(criteriosBusqueda, null);
			/*Valida duplicidad de Razon Social*/
			if (resultRS.size()>0)
				throw new RazonSocialDuplicadoException();

			getClienteDAO().guardar(cliente);

		}catch (RazonSocialDuplicadoException rsdex){
			throw new RazonSocialDuplicadoException();
		}catch(RucDuplicadoException rdnex){
			throw new RucDuplicadoException();
		}catch(Exception ex){
			throw new CancelaGrabacionException();
		}
	}

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.service.business.ClienteManager#actualizar(com.tepsa.sisvyr.model.bean.Cliente)
	 */
	@Override
	@Transactional
	public void actualizar(Cliente cliente)throws Exception {
		try{
			TreeMap<String, Object> criteriosBusqueda = new TreeMap<>();
			criteriosBusqueda.put("numeroDocumento", cliente.getNumeroDocumento());
			criteriosBusqueda.put("estadoRegistro", Constantes.VALUE_ACTIVO);
			List<?> result = getClienteDAO().buscarPorX(criteriosBusqueda, null);
			/*Valida duplicidad del Ruc*/
			for (Object element : result) {
				Cliente ocliente = (Cliente) element;
				if (!(ocliente.getId().equals(cliente.getId())))
					throw new RucDuplicadoException();
			}

			criteriosBusqueda.remove("numeroDocumento");
			criteriosBusqueda.put("razonSocial", cliente.getRazonSocial());
			List<?> resultRS = getClienteDAO().buscarPorX(criteriosBusqueda, null);
			/*Valida duplicidad de la Raz�n Social*/
			for (Object element : resultRS) {
				Cliente ocliente = (Cliente) element;
				if (!(ocliente.getId().equals(cliente.getId())))
					throw new RazonSocialDuplicadoException();
			}

			getClienteDAO().actualizar(cliente);

		}catch (RazonSocialDuplicadoException rsdex){
			throw new RazonSocialDuplicadoException();
		}catch(RucDuplicadoException rdnex){
			throw new RucDuplicadoException();
		}catch(Exception ex){
			throw new Exception(ex);
		}
	}

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.service.business.ClienteManager#inactivar(java.lang.Long)
	 */
	@Override
	@Transactional
	public void inactivar(Long id)throws Exception {
		getClienteDAO().inactivar(id);
	}

	/*
	 * (non-Javadoc)
	 * @see com.tepsa.sisvyr.service.business.ClienteManager#cargaClientesSolicitud()
	 */
	@Override
	public List<Cliente> cargaClientesSolicitud() {
		return getClienteDAO().cargaClientesSolicitud();
	}
	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.service.business.ClienteManager#buscarporFullTextIndex(java.lang.String[])
	 */
	@Override
	public ArrayList<Cliente> buscarPorRazonSocial(String[] razonSocial) throws Exception {
		return getClienteDAO().buscarPorRazonSocial(razonSocial);
	}
	@Override
	public Cliente buscarCliente_ServicioEspecial(String Ruc) throws Exception {
		return getClienteDAO().buscarCliente_ServicioEspecial(Ruc);
	}
	/*
	 * (non-Javadoc)
	 * @see com.tepsa.sisvyr.service.business.ClienteManager#buscarClienteAgencia(java.lang.String)
	 */
	@Override
	public List<Cliente> buscarClienteAgencia(String ruc)throws Exception{
		return getClienteDAO().buscarClienteAgencia(ruc);
	}
	/* (non-Javadoc)
	 * @see pe.itsb.vyrbus.service.business.ClienteManager#buscarPorRuc(java.lang.String)
	 */
	@Override
	public Cliente buscarPorRuc(String ruc) throws Exception {
		// TODO Auto-generated method stub
		
		TreeMap<String, Object>criteriosBusqueda = new TreeMap<String, Object>();
		criteriosBusqueda.put("numeroDocumento", ruc);
		criteriosBusqueda.put("estadoRegistro", Constantes.VALUE_ACTIVO);
		List<Cliente> result = buscarPorX(criteriosBusqueda, null);
		if(result.size()>0)
			return result.get(0);
		else
			return null;
	}
}
