package pe.itsb.vyrbus.service.business;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

import pe.itsb.vyrbus.model.bean.CanalVenta;

public interface CanalVentaManager {
	/**
	 * Busca los canales de venta por el estado del registro
	 * @param estado		: Estado de los registro a buscar.
	 * @param criterioOrden	: Criterio para ordenar los registros.
	 * @return ArrayList de Canales de venta
	 */
	public ArrayList<CanalVenta> buscarPorEstadoRegistro(String estado, String criterioOrden)throws Exception;
	public ArrayList<CanalVenta> buscarPorX(TreeMap<String, Object> criteriosBusqueda, List<String> criteriosOrdenar)throws Exception;
	public CanalVenta buscarPorId(Long id)throws Exception;
	public void guardar(CanalVenta canalVenta)throws Exception;
	public void actualizar(CanalVenta canalVenta)throws Exception;
	public void inactivar(Long id)throws Exception;
	/**
	 * Realiza la busqueda de los Canales de Venta de acuerdo a lso criterios enviados
	 * @param campo				: Campo que debera cumplir con los criterios enviados.
	 * @param criterios			: Identificadores de los canales de venta separados por comas.
	 * @param criteriosOrdenar	: Criterios a utilizar para ordenar la informacion.
	 * @param estadoRegistro	: Estado de los registros.
	 * @return Lista de Canales de Venta.
	 */
	public List<CanalVenta> buscarPorX(String campo, Object[] criterios, List<String> criteriosOrdenar, String estadoRegistro) throws Exception;
}
