package com.cystesoft.vyrbus.service.business;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

import com.cystesoft.vyrbus.model.bean.EstadoCivil;

public interface EstadoCivilManager {
	public ArrayList<EstadoCivil> buscarPorEstadoRegistro(String estado, String criterioOrden)throws Exception;
	public ArrayList<EstadoCivil> buscarPorX(TreeMap<String, Object> criteriosBusqueda, List<String> criteriosOrdenar)throws Exception;	
	public EstadoCivil buscarPorId(Long id)throws Exception;
	public void guardar(EstadoCivil estadoCivil)throws Exception;
	public void actualizar(EstadoCivil estadoCivil)throws Exception;
	public void inactivar(Long id)throws Exception;
}
