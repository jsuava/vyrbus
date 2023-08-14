package pe.itsb.vyrbus.service.business;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

import pe.itsb.vyrbus.model.bean.VentaPasajeHistorial;

public interface VentaPasajesHistorialManager {
	/**
	 *
	 * @param estado
	 * @param criterioOrden
	 * @return
	 */
	public ArrayList<VentaPasajeHistorial> buscarPorEstadoRegistro(String estado, String criterioOrden);

	/**
	 *
	 * @param criteriosBusqueda
	 * @param criteriosOrdenar
	 * @return
	 */
	public ArrayList<VentaPasajeHistorial> buscarPorX(TreeMap<String, Object> criteriosBusqueda, List<String> criteriosOrdenar);

	/**
	 *
	 * @param id
	 * @return
	 */
	public VentaPasajeHistorial buscarPorId(Long id);

	/**
	 *
	 * @param ventaPasajeHistorial
	 */
	public void guardar(VentaPasajeHistorial ventaPasajeHistorial);

	/**
	 *
	 * @param ventaPasajeHistorial
	 */
	public void actualizar(VentaPasajeHistorial ventaPasajeHistorial);
}
