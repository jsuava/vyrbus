package com.cystesoft.vyrbus.service.business;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

import com.cystesoft.vyrbus.model.bean.TipoMovimiento;

public interface TipoMovimientoManager {
	public ArrayList<TipoMovimiento> buscarPorEstadoRegistro(String estado, String criterioOrden)throws Exception;
	public ArrayList<TipoMovimiento> buscarPorX(TreeMap<String, Object> criteriosBusqueda, List<String> criteriosOrdenar)throws Exception;
	public TipoMovimiento buscarPorId(Long id)throws Exception;
	public void guardar(TipoMovimiento condicionVenta)throws Exception;
	public void actualizar(TipoMovimiento condicionVenta)throws Exception;
	public void inactivar(Long id)throws Exception;
	
	/**
	 * Realiza la busqueda de lostipo de movimiento de acuerdo a lso criterios enviados
	 * @param campo				: Campo que debera cumplir con los criterios enviados.
	 * @param criterios			: Identificadores de las agencias separados por comas.
	 * @param criteriosOrdenar	: Criterios a utilizar para ordenar la informacion.
	 * @param estadoRegistro	: Estado de los registros.
	 * @return Lista de tipos de movimiento.
	 */
	public List<TipoMovimiento> buscarPorX(String campo, Object[] criterios, List<String> criteriosOrdenar, String estadoRegistro) throws Exception;
}
