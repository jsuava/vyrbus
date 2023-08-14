package pe.itsb.vyrbus.service.business;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

import pe.itsb.vyrbus.model.bean.MotivoTemporadaAlta;

/**
 *
 * @author JABANTO
 *
 */
public interface MotivoTemporadaAltaManager {
	public ArrayList<MotivoTemporadaAlta> buscarPorEstadoRegistro(String estado, String criterioOrden);
	public ArrayList<MotivoTemporadaAlta> buscarPorX(TreeMap<String, Object> criteriosBusqueda, List<String> criteriosOrdenar);
	public MotivoTemporadaAlta buscarPorId(Long id);
	public void guardar(MotivoTemporadaAlta motivoTemporadaAlta) throws Exception;
	public void actualizar(MotivoTemporadaAlta motivoTemporadaAlta) throws Exception;
	public void inactivar(Long id);
}
