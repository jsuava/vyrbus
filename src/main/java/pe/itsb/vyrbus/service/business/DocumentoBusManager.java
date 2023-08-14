package pe.itsb.vyrbus.service.business;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

import pe.itsb.vyrbus.model.bean.DocumentoBus;

public interface DocumentoBusManager {
	public ArrayList<DocumentoBus> buscarPorEstadoRegistro(String estado, String criterioOrden)throws Exception;
	public ArrayList<DocumentoBus> buscarPorX(TreeMap<String, Object> criteriosBusqueda, List<String> criteriosOrdenar)throws Exception;
	public DocumentoBus buscarPorId(Long id)throws Exception;
	public void guardar(DocumentoBus documentoBus)throws Exception;
	public void actualizar(DocumentoBus documentoBus)throws Exception;
	public void inactivar(Long id)throws Exception;
}
