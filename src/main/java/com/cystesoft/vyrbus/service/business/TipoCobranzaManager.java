package com.cystesoft.vyrbus.service.business;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

import com.cystesoft.vyrbus.model.bean.TipoCobranza;

/**
 * 
 * @author JABANTO
 *
 */
public interface TipoCobranzaManager {
	public ArrayList<TipoCobranza> buscarPorEstadoRegistro(String estado, String criterioOrden);
	public ArrayList<TipoCobranza> buscarPorX(TreeMap<String, Object> criteriosBusqueda, List<String> criteriosOrdenar);
	public TipoCobranza buscarPorId(Long id);
	public void guardar(TipoCobranza tipoCobranza) throws Exception;
	public void actualizar(TipoCobranza tipoCobranza) throws Exception;
	public void inactivar(Long id);
}
