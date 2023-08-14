package pe.itsb.vyrbus.service.business;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

import pe.itsb.vyrbus.model.bean.TipoAgencia;

public interface TipoAgenciaManager {
	public ArrayList<TipoAgencia> buscarPorEstadoRegistro(String estado, String criterioOrden)throws Exception;
	public ArrayList<TipoAgencia> buscarPorX(TreeMap<String, Object> criteriosBusqueda, List<String> criteriosOrdenar)throws Exception;
	public TipoAgencia buscarPorId(Long id)throws Exception;
	public void guardar(TipoAgencia tipoAgencia)throws Exception;
	public void actualizar(TipoAgencia tipoAgencia)throws Exception;
	public void inactivar(Long id)throws Exception;
}
