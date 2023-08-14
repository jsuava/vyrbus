package pe.itsb.vyrbus.service.business;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

import pe.itsb.vyrbus.model.bean.Flag;


public interface FlagManager {
	public ArrayList<Flag> buscarPorEstadoRegistro(String estado, String criterioOrden)throws Exception;
	public ArrayList<Flag> buscarPorX (TreeMap<String, Object> criteriosBusqueda, List<String> criteriosOrdenar)throws Exception;
	public Flag buscarPorId (Long id)throws Exception;
	public void guardar(Flag flag)throws Exception;
	public void actualizar(Flag flag)throws Exception;

}
