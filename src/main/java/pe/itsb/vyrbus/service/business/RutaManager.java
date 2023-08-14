package pe.itsb.vyrbus.service.business;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

import pe.itsb.vyrbus.model.bean.Ruta;

public interface RutaManager {
	public ArrayList<Ruta> buscarPorEstadoRegistro(String estado, String criterioOrden)throws Exception;
	public ArrayList<Ruta> buscarPorX(TreeMap<String, Object> criteriosBusqueda, List<String> criteriosOrdenar)throws Exception;
	public Ruta buscarPorId(Long id)throws Exception;
	public void guardar(Ruta ruta)throws Exception;
	public void actualizar(Ruta ruta)throws Exception;
	public void inactivar(Long id)throws Exception;
	/**
	 * Realiza la busqueda de las Rutas que participan de la promocion.
	 * @param campo				: Campo que debera cumplir con los criterios enviados.
	 * @param criterios			: Identificadores de las rutas separados por comas.
	 * @param criteriosOrdenar	: Criterios a utilizar para ordenar la informacion.
	 * @param estadoRegistro	: Estado de los registros.
	 * @return Lista de rutas.
	 */
	public List<Ruta> buscarPorX(String campo, Object[] criterios, List<String> criteriosOrdenar, String estadoRegistro) throws Exception;
}
