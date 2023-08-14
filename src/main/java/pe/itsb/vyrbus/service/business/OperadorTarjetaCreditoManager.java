package pe.itsb.vyrbus.service.business;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

import pe.itsb.vyrbus.model.bean.OperadorTarjetaCredito;

public interface OperadorTarjetaCreditoManager {
	public ArrayList<OperadorTarjetaCredito> buscarPorEstadoRegistro(String estado, String criterioOrden)throws Exception;
	public ArrayList<OperadorTarjetaCredito> buscarPorX(TreeMap<String, Object> criteriosBusqueda, List<String> criteriosOrdenar)throws Exception;
	public OperadorTarjetaCredito buscarPorId(Long id)throws Exception;
	public void guardar(OperadorTarjetaCredito operadorTarjetaCredito)throws Exception;
	public void actualizar(OperadorTarjetaCredito operadorTarjetaCredito)throws Exception;
	public void inactivar(Long id)throws Exception;
}
