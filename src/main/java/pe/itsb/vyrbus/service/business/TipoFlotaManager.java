package pe.itsb.vyrbus.service.business;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

import pe.itsb.vyrbus.model.bean.TipoFlota;

public interface TipoFlotaManager {
	public ArrayList<TipoFlota> buscarPorEstadoRegistro(String estado, String criterioOrden)throws Exception;
	public ArrayList<TipoFlota> buscarPorX(TreeMap<String, Object> criteriosBusqueda, List<String> criteriosOrdenar)throws Exception;
	public TipoFlota buscarPorId(Long id)throws Exception;
	public void guardar(TipoFlota tipoFlota)throws Exception;
	public void actualizar(TipoFlota tipoFlota)throws Exception;
	public void inactivar(Long id)throws Exception;
}
