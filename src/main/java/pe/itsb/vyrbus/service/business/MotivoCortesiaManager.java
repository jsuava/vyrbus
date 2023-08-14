package pe.itsb.vyrbus.service.business;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

import pe.itsb.vyrbus.model.bean.MotivoCortesia;

public interface MotivoCortesiaManager {

	/**
	 *
	 * @param estado
	 * @param criterioOrden
	 * @return
	 */
	public ArrayList<MotivoCortesia> buscarPorEstadoRegistro(String estado, String criterioOrden);

	/**
	 *
	 * @param criteriosBusqueda
	 * @param criteriosOrdenar
	 * @return
	 */
	public ArrayList<MotivoCortesia> buscarPorX(TreeMap<String, Object> criteriosBusqueda, List<String> criteriosOrdenar);

	/**
	 *
	 * @param id
	 * @return
	 */
	public MotivoCortesia buscarPorId(Long id);

	/**
	 *
	 * @param motivoCortesia
	 */
	public void guardar(MotivoCortesia motivoCortesia) throws Exception;


	/**
	 *
	 * @param motivoCortesia
	 */
	public void actualizar(MotivoCortesia motivoCortesia) throws Exception;

	/**
	 *
	 * @param id
	 */
	public void inactivar(Long id);
}
