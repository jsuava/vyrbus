package pe.itsb.vyrbus.service.business;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

import pe.itsb.vyrbus.model.bean.UsuarioHardware;

public interface UsuarioHardwareManager {
	/**
	 *
	 * @param estado
	 * @param criterioOrden
	 * @return
	 */
	public ArrayList<UsuarioHardware> buscarPorEstadoRegistro(String estado, String criterioOrden);

	/**
	 *
	 * @param criteriosBusqueda
	 * @param criteriosOrdenar
	 * @return
	 */
	public ArrayList<UsuarioHardware> buscarPorX(TreeMap<String, Object> criteriosBusqueda, List<String> criteriosOrdenar);

	/**
	 *
	 * @param id
	 * @return
	 */
	public UsuarioHardware buscarPorId(Long id);

	/**
	 *
	 * @param usuarioHardware
	 */
	public int guardar(UsuarioHardware usuarioHardware) throws Exception;

	/**
	 *
	 * @param usuarioHardware
	 */
	public void actualizar(UsuarioHardware usuarioHardware) throws Exception;

	/**
	 *
	 * @param id
	 */
	public void inactivar(Long id);

	/**
	 * Busqueda por código
	 * @param Codigo	: código del usuario hadrware
	 * @return
	 * @throws Exception
	 */
	public UsuarioHardware buscarXCodigo(String codigo) throws Exception;
}
