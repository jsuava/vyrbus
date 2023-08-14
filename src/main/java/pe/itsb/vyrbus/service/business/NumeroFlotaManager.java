package pe.itsb.vyrbus.service.business;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

import pe.itsb.vyrbus.model.bean.NumeroFlota;

public interface NumeroFlotaManager {
	public ArrayList<NumeroFlota> buscarPorEstadoRegistro(String estado, String criterioOrden)throws Exception;
	public ArrayList<NumeroFlota> buscarPorX(TreeMap<String, Object> criteriosBusqueda, List<String> criteriosOrdenar)throws Exception;
	public NumeroFlota buscarPorId(Long id)throws Exception;
	public void guardar(NumeroFlota numeroFlota)throws Exception;
	public void actualizar(NumeroFlota numeroFlota)throws Exception;
	public void inactivar(Long id)throws Exception;
}
