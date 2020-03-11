package com.cystesoft.vyrbus.service.business;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

import com.cystesoft.vyrbus.model.bean.Sexo;

public interface SexoManager {
	public ArrayList<Sexo> buscarPorEstadoRegistro(String estado, String criterioOrden)throws Exception;
	public ArrayList<Sexo> buscarPorX(TreeMap<String, Object> criteriosBusqueda, List<String> criteriosOrdenar)throws Exception;
	public Sexo buscarPorId(Long id)throws Exception;
	public void guardar(Sexo sexo)throws Exception;
	public void actualizar(Sexo sexo)throws Exception;
	public void inactivar(Long id)throws Exception;
}
