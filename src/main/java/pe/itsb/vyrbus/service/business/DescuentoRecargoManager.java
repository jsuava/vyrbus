package pe.itsb.vyrbus.service.business;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

import pe.itsb.vyrbus.model.bean.DescuentoRecargo;

public interface DescuentoRecargoManager {
	public ArrayList<DescuentoRecargo> buscarPorEstadoRegistro(String estado, String criterioOrden)throws Exception;
	public ArrayList<DescuentoRecargo> buscarPorX(TreeMap<String, Object> criteriosBusqueda, List<String> criteriosOrdenar)throws Exception;
	public DescuentoRecargo buscarPorId(Long id)throws Exception;
	public void guardar(DescuentoRecargo descuentoRecargo)throws Exception;
	public void actualizar(DescuentoRecargo descuentoRecargo)throws Exception;
	public void inactivar(Long id)throws Exception;
}
