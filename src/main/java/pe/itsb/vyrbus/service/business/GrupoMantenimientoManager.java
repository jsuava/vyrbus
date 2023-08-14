package pe.itsb.vyrbus.service.business;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

import pe.itsb.vyrbus.model.bean.GrupoMantenimiento;

public interface GrupoMantenimientoManager {
	public ArrayList<GrupoMantenimiento> buscarPorEstadoRegistro(String estado, String criterioOrden)throws Exception;
	public ArrayList<GrupoMantenimiento> buscarPorX(TreeMap<String, Object> criteriosBusqueda, List<String> criteriosOrdenar)throws Exception;
	public GrupoMantenimiento buscarPorId(Long id)throws Exception;
	public void guardar(GrupoMantenimiento grupoMantenimiento)throws Exception;
	public void actualizar(GrupoMantenimiento grupoMantenimiento)throws Exception;
	public void inactivar(Long id)throws Exception;
}
