package pe.itsb.vyrbus.service.business;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

import pe.itsb.vyrbus.model.bean.TipoDocumento;

public interface TipoDocumentoManager {
	public ArrayList<TipoDocumento> buscarPorEstadoRegistro(String estado, String criterioOrden)throws Exception;
	public ArrayList<TipoDocumento> buscarPorX(TreeMap<String, Object> criteriosBusqueda, List<String> criteriosOrdenar)throws Exception;
	public TipoDocumento buscarPorId(Long id)throws Exception;
	public void guardar(TipoDocumento tipoDocumento)throws Exception;
	public void actualizar(TipoDocumento tipoDocumento)throws Exception;
	public void inactivar(Long id)throws Exception;
}
