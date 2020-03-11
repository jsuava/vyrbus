package com.cystesoft.vyrbus.service.business;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

import com.cystesoft.vyrbus.model.bean.TipoComprobante;

public interface TipoComprobanteManager {
	public ArrayList<TipoComprobante> buscarPorEstadoRegistro(String estado, String criterioOrden)throws Exception;
	public ArrayList<TipoComprobante> buscarPorX(TreeMap<String, Object> criteriosBusqueda, List<String> criteriosOrdenar)throws Exception;
	public TipoComprobante buscarPorId(Long id)throws Exception;
	public void guardar(TipoComprobante tipoComprobante)throws Exception;
	public void actualizar(TipoComprobante tipoComprobante)throws Exception;
	public void inactivar(Long id)throws Exception;
}
