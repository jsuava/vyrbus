package pe.itsb.vyrbus.service.business;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

import pe.itsb.vyrbus.model.bean.EstadoDocumentoBus;

public interface EstadoDocumentoBusManager {
	public ArrayList<EstadoDocumentoBus> buscarPorEstadoRegistro(String estado, String criterioOrden)throws Exception;
	public ArrayList<EstadoDocumentoBus> buscarPorX(TreeMap<String, Object> criteriosBusqueda, List<String> criteriosOrdenar)throws Exception;
	public EstadoDocumentoBus buscarPorId(Long id)throws Exception;
	public void guardar(EstadoDocumentoBus estadoDocumentoBus)throws Exception;
	public void actualizar(EstadoDocumentoBus estadoDocumentoBus)throws Exception;
	public void inactivar(Long id)throws Exception;
}
