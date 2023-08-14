package pe.itsb.vyrbus.service.business;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

import pe.itsb.vyrbus.model.bean.TarjetaCredito;

public interface TarjetaCreditoManager {
	public ArrayList<TarjetaCredito> buscarPorEstadoRegistro(String estado, String criterioOrden)throws Exception;
	public ArrayList<TarjetaCredito> buscarPorX(TreeMap<String, Object> criteriosBusqueda, List<String> criteriosOrdenar)throws Exception;
	public TarjetaCredito buscarPorId(Long id)throws Exception;
	public void guardar(TarjetaCredito tarjetaCredito)throws Exception;
	public void actualizar(TarjetaCredito tarjetaCredito)throws Exception;
	public void inactivar(Long id)throws Exception;
	/**
	 * Realiza la busqueda de las Tarjetas de Credito de acuerdo a lso criterios enviados
	 * @param campo				: Campo que debera cumplir con los criterios enviados.
	 * @param criterios			: Identificadores de las Tarjetas de Credito separados por comas.
	 * @param criteriosOrdenar	: Criterios a utilizar para ordenar la informacion.
	 * @param estadoRegistro	: Estado de los registros.
	 * @return Lista de Tarjetas de Credito.
	 */
	public List<TarjetaCredito> buscarPorX(String campo, Object[] criterios, List<String> criteriosOrdenar, String estadoRegistro) throws Exception;
}
