/**
 * Proyecto		: SISVYR
 * Sistema		: Sistema de Ventas y Reservas
 * Descripción	:
 * Autor		: José Abanto
 * Fecha		: 25 jul. 2023
 * Hora			: 00:21:02
 */
package pe.itsb.vyrbus.service.business.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

import pe.itsb.vyrbus.model.bean.Empresa;
import pe.itsb.vyrbus.model.dao.EmpresaDAO;
import pe.itsb.vyrbus.service.business.EmpresaManager;
import pe.itsb.vyrbus.service.exceptions.NumeroDocumentoDuplicadoException;
import pe.itsb.vyrbus.service.util.Constantes;

/**
 * @author Jose
 *
 */
public class EmpresaManagerImpl implements EmpresaManager {
	private EmpresaDAO empresaDAO;


	/**
	 * @return the empresaDAO
	 */
	public EmpresaDAO getEmpresaDAO() {
		return empresaDAO;
	}

	/**
	 * @param empresaDAO the empresaDAO to set
	 */
	public void setEmpresaDAO(EmpresaDAO empresaDAO) {
		this.empresaDAO = empresaDAO;
	}

	/* (non-Javadoc)
	 * @see pe.itsb.vyrbus.service.business.EmpresaManager#buscarPorEstadoRegistro(java.lang.String, java.lang.String)
	 */
	@Override
	public ArrayList<Empresa> buscarPorEstadoRegistro(String estado, String criterioOrden) throws Exception {
		return getEmpresaDAO().buscarPorEstadoRegistro(estado, criterioOrden);
	}

	/* (non-Javadoc)
	 * @see pe.itsb.vyrbus.service.business.EmpresaManager#buscarPorX(java.util.TreeMap, java.util.List)
	 */
	@Override
	public ArrayList<Empresa> buscarPorX(TreeMap<String, Object> criteriosBusqueda, List<String> criteriosOrdenar) throws Exception {
		return getEmpresaDAO().buscarPorX(criteriosBusqueda, criteriosOrdenar);
	}

	/* (non-Javadoc)
	 * @see pe.itsb.vyrbus.service.business.EmpresaManager#buscarPorId(java.lang.Long)
	 */
	@Override
	public Empresa buscarPorId(Long id) throws Exception {
		return getEmpresaDAO().buscarPorId(id);
	}

	/* (non-Javadoc)
	 * @see pe.itsb.vyrbus.service.business.EmpresaManager#guardar(pe.itsb.vyrbus.model.bean.Empresa)
	 */
	@Override
	public void guardar(Empresa empresa) throws Exception {
		try{
			TreeMap<String, Object> criteriosBusqueda = new TreeMap<>();
			criteriosBusqueda.put("numeroDocumento", empresa.getNumeroDocumento());
			criteriosBusqueda.put("estadoRegistro", Constantes.VALUE_ACTIVO);
			List<?> resultRUC = getEmpresaDAO().buscarPorX(criteriosBusqueda, null);
			/*Valida duplicidad de la denominaci�n*/
			if(resultRUC.size()>0)
				throw new NumeroDocumentoDuplicadoException();

			getEmpresaDAO().guardar(empresa);

		}catch (NumeroDocumentoDuplicadoException rsdex){
			throw new NumeroDocumentoDuplicadoException();
		}catch(Exception ex){
			throw new Exception(ex);
		}
	}

	/* (non-Javadoc)
	 * @see pe.itsb.vyrbus.service.business.EmpresaManager#actualizar(pe.itsb.vyrbus.model.bean.Empresa)
	 */
	@Override
	public void actualizar(Empresa empresa) throws Exception {
		try{
			TreeMap<String, Object> criteriosBusqueda = new TreeMap<>();
			criteriosBusqueda.put("numeroDocumento", empresa.getNumeroDocumento());
			criteriosBusqueda.put("estadoRegistro", Constantes.VALUE_ACTIVO);
			List<?> resultDenominacion = getEmpresaDAO().buscarPorX(criteriosBusqueda, null);
			/*Valida duplicidad del RUC*/
			for (Object element : resultDenominacion) {
				Empresa oEmpresa = (Empresa) element;
					if (!(oEmpresa.getId() == empresa.getId()))
						throw new NumeroDocumentoDuplicadoException();
				}

			getEmpresaDAO().actualizar(empresa);

		}catch (NumeroDocumentoDuplicadoException rsdex){
			throw new NumeroDocumentoDuplicadoException();
		}catch(Exception ex){
			throw new Exception(ex);
		}
	}

	/* (non-Javadoc)
	 * @see pe.itsb.vyrbus.service.business.EmpresaManager#inactivar(java.lang.Long)
	 */
	@Override
	public void inactivar(Long id) throws Exception {
		getEmpresaDAO().inactivar(id);
	}

}
