package pe.itsb.vyrbus.model.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

import pe.itsb.vyrbus.model.bean.GastoBus;

/**
 *
 * @author JABANTO
 *
 */
public interface GastoBusDAO extends GenericDAO {

	/**
	 * Busqueda por un ArraLis de Parametros.
	 * @param criteriosBusqueda :
	 * @param criteriosOrdenar	: lista de criterios para el orden de los datos
	 * @return
	 */
	public ArrayList<GastoBus> buscarPorX(TreeMap<String, Object> criteriosBusqueda, List<String> criteriosOrdenar);

	/**
	 * Guarda los gastos del bus
	 * @param gastoBus
	 */
	public void guardar(GastoBus gastoBus);

	/**
	 *
	 * @param id : identificador de la liquidacion del bus.
	 */
	public void delete(Long idLiquidacionBus);




}
