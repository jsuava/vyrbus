/**
 * Proyecto		: SISVYR
 * Sistema		: Sistema de Ventas y Reservas
 * Descripción	: Implementacion de metodos que permiten el acceso al modelo.
 * Autor		: José Avalos
 * Fecha		: 27/09/2012
 */
package pe.itsb.vyrbus.service.business.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

import org.springframework.transaction.annotation.Transactional;

import pe.itsb.vyrbus.model.bean.Bus;
import pe.itsb.vyrbus.model.dao.BusDAO;
import pe.itsb.vyrbus.service.business.BusManager;
import pe.itsb.vyrbus.service.exceptions.NumeroBusDuplicadoException;
import pe.itsb.vyrbus.service.exceptions.NumeroChasisDuplicadoException;
import pe.itsb.vyrbus.service.exceptions.NumeroMotorDuplicadoException;
import pe.itsb.vyrbus.service.exceptions.NumeroPlacaDuplicadoException;
import pe.itsb.vyrbus.service.util.Constantes;

/**
 * @author Jose
 *
 */
public class BusManagerImpl implements BusManager {
	private BusDAO busDAO;

	/**
	 * @return the busDAO
	 */
	public BusDAO getBusDAO() {
		return busDAO;
	}
	/**
	 * @param busDAO the busDAO to set
	 */
	public void setBusDAO(BusDAO busDAO) {
		this.busDAO = busDAO;
	}

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.service.business.BusManager#buscarPorEstadoRegistro(java.lang.String, java.lang.String)
	 */
	@Override
	@Transactional(readOnly=true)
	public ArrayList<Bus> buscarPorEstadoRegistro(String estado, String criterioOrden) throws Exception {
		return getBusDAO().buscarPorEstadoRegistro(estado, criterioOrden);
	}

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.service.business.BusManager#buscarPorX(java.util.TreeMap, java.util.List)
	 */
	@Override
	@Transactional (readOnly=true)
	public ArrayList<Bus> buscarPorX(TreeMap<String, Object> criteriosBusqueda, List<String> criteriosOrdenar) throws Exception {
		return getBusDAO().buscarPorX(criteriosBusqueda, criteriosOrdenar);
	}

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.service.business.BusManager#buscarPorId(java.lang.Long)
	 */
	@Override
	@Transactional (readOnly=true)
	public Bus buscarPorId(Long id) throws Exception {
		return getBusDAO().buscarPorId(id);
	}

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.service.business.BusManager#guardar(com.tepsa.sisvyr.model.bean.Bus)
	 */
	@Override
	@Transactional
	public void guardar(Bus bus) throws Exception {
		try{
			TreeMap<String, Object> criteriosBusqueda = new TreeMap<>();
			criteriosBusqueda.put("codigo", bus.getCodigo());
			criteriosBusqueda.put("estadoRegistro", Constantes.VALUE_ACTIVO);
			List<?> resultCodigo = getBusDAO().buscarPorX(criteriosBusqueda, null);
			/*Valida duplicidad codigo del bus "Número del Bus"*/
			if(resultCodigo.size()>0)
				throw new NumeroBusDuplicadoException();

			criteriosBusqueda.remove("codigo");
			criteriosBusqueda.put("numeroPlaca", bus.getNumeroPlaca());
			List<?> resultPlaca = getBusDAO().buscarPorX(criteriosBusqueda, null);
			/*Valida duplicidad del Número de la Placa*/
			if (resultPlaca.size()>0)
				throw new NumeroPlacaDuplicadoException();

			criteriosBusqueda.remove("codigo");criteriosBusqueda.remove("numeroPlaca");
			criteriosBusqueda.put("numeroChasis", bus.getNumeroChasis());
			List<?> resultChasis = getBusDAO().buscarPorX(criteriosBusqueda, null);
			/*Valida duplicidad del Número de Chasis*/
			if (resultChasis.size()>0)
				throw new NumeroChasisDuplicadoException();

			criteriosBusqueda.remove("codigo");criteriosBusqueda.remove("numeroPlaca"); criteriosBusqueda.remove("numeroChasis");
			criteriosBusqueda.put("numeroMotor", bus.getNumeroMotor());
			List<?> resultMotor = getBusDAO().buscarPorX(criteriosBusqueda, null);
			/*Valida duplicidad del Número del Motor*/
			if (resultMotor.size()>0)
				throw new NumeroMotorDuplicadoException();

			getBusDAO().guardar(bus);

		}catch (NumeroBusDuplicadoException nbdex){
			throw new NumeroBusDuplicadoException();
		}catch (NumeroPlacaDuplicadoException npdex){
			throw new NumeroPlacaDuplicadoException();
		}catch (NumeroChasisDuplicadoException ncdex){
			throw new NumeroChasisDuplicadoException();
		}catch (NumeroMotorDuplicadoException nmdex){
			throw new NumeroMotorDuplicadoException();
		}catch (Exception ex){
			throw new Exception(ex);
		}
	}

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.service.business.BusManager#actualizar(com.tepsa.sisvyr.model.bean.Bus)
	 */
	@Override
	@Transactional
	public void actualizar(Bus bus) throws Exception {
		try{
			TreeMap<String, Object> criteriosBusqueda = new TreeMap<>();
			criteriosBusqueda.put("codigo", bus.getCodigo());
			criteriosBusqueda.put("estadoRegistro", Constantes.VALUE_ACTIVO);
			List<?> resultCodigo = getBusDAO().buscarPorX(criteriosBusqueda, null);
			/*Valida duplicidad codigo del bus "Número del Bus"*/
			for (Object element : resultCodigo) {
				Bus obusCodigo = (Bus) element;
					if (!(obusCodigo.getId() == bus.getId()))
						throw new NumeroBusDuplicadoException();
			}

			criteriosBusqueda.remove("codigo");
			criteriosBusqueda.put("numeroPlaca", bus.getNumeroPlaca());
			List<?> resultPlaca = getBusDAO().buscarPorX(criteriosBusqueda, null);
			/*Valida duplicidad del Número de la Placa*/
			for (Object element : resultPlaca) {
				Bus obusPlaca= (Bus) element;
					if (!(obusPlaca.getId() == bus.getId()))
						throw new NumeroPlacaDuplicadoException();
			}

			criteriosBusqueda.remove("codigo");criteriosBusqueda.remove("numeroPlaca");
			criteriosBusqueda.put("numeroChasis", bus.getNumeroChasis());
			List<?> resultChasis = getBusDAO().buscarPorX(criteriosBusqueda, null);
			/*Valida duplicidad del Número de Chasis*/
			for (Object element : resultChasis) {
				Bus obusChasis= (Bus) element;
					if (!(obusChasis.getId() == bus.getId()))
						throw new NumeroChasisDuplicadoException();
			}

			criteriosBusqueda.remove("codigo");criteriosBusqueda.remove("numeroPlaca"); criteriosBusqueda.remove("numeroChasis");
			criteriosBusqueda.put("numeroMotor", bus.getNumeroMotor());
			List<?> resultMotor = getBusDAO().buscarPorX(criteriosBusqueda, null);
			/*Valida duplicidad del Número del Motor*/
			for (Object element : resultMotor) {
				Bus obusMotor= (Bus) element;
					if (!(obusMotor.getId() == bus.getId()))
						throw new NumeroMotorDuplicadoException();
			}

			getBusDAO().actualizar(bus);

		}catch (NumeroBusDuplicadoException nbdex){
			throw new NumeroBusDuplicadoException();
		}catch (NumeroPlacaDuplicadoException npdex){
			throw new NumeroPlacaDuplicadoException();
		}catch (NumeroChasisDuplicadoException ncdex){
			throw new NumeroChasisDuplicadoException();
		}catch (NumeroMotorDuplicadoException nmdex){
			throw new NumeroMotorDuplicadoException();
		}catch (Exception ex){
			throw new Exception(ex);
		}
	}

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.service.business.BusManager#innactivar(long)
	 */
	@Override
	@Transactional
	public void inactivar(long id) throws Exception {
		getBusDAO().inactivar(id);
	}

}
