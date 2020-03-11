package com.cystesoft.vyrbus.service.business;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

import com.cystesoft.vyrbus.model.bean.Nacionalidad;

public interface NacionalidadManager {
	public ArrayList<Nacionalidad> buscarPorEstadoRegistro(String estado, String criterioOrden)throws Exception;
	public ArrayList<Nacionalidad> buscarPorX(TreeMap<String, Object> criteriosBusqueda, List<String> criteriosOrdenar)throws Exception;
	public Nacionalidad buscarPorId(Long id)throws Exception;
	public void guardar(Nacionalidad nacionalidad)throws Exception;
	public void actualizar(Nacionalidad nacionalidad)throws Exception;
	public void inactivar(Long id)throws Exception;
}
