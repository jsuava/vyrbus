package pe.itsb.vyrbus.service.business;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

import pe.itsb.vyrbus.model.bean.TipoFormaPago;

public interface TipoFormaPagoManager {
	public ArrayList<TipoFormaPago> buscarPorEstadoRegistro(String estado, String criterioOrden)throws Exception;
	public ArrayList<TipoFormaPago> buscarPorX(TreeMap<String, Object> criteriosBusqueda, List<String> criteriosOrdenar)throws Exception;
	public TipoFormaPago buscarPorId(Long id)throws Exception;
	public void guardar(TipoFormaPago tipoFormaPago)throws Exception;
	public void actualizar(TipoFormaPago tipoFormaPago)throws Exception;
	public void inactivar(Long id)throws Exception;
}
