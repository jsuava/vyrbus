package com.cystesoft.vyrbus.service.business;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

import com.cystesoft.vyrbus.model.bean.Personal;

public interface PersonalManager {
	public ArrayList<Personal> buscarPorEstadoRegistro(String estado, String criterioOrden)throws Exception;
	public ArrayList<Personal> buscarPorX(TreeMap<String, Object> criteriosBusqueda, List<String> criteriosOrdenar)throws Exception;
	public Personal buscarPorId(Long id)throws Exception;
	public void guardar(Personal personal)throws Exception;
	public void actualizar(Personal personal)throws Exception;
	public void inactivar(Long id)throws Exception;
	
	/**
	 * Buscar E-Mails para el envio del alertas.
	 * @param iDsRol : Identificador del Rol
	 * @return
	 * @throws Exception
	 */
	public String buscarMailsXRols(String iDsRol)throws Exception;
}
