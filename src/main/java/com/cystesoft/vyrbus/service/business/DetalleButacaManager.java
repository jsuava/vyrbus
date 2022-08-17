/**
 * Proyecto		: SISVYR
 * Sistema		: Sistema de Ventas y Reservas
 * Descripción	:
 * Autor		: José Abanto
 * Fecha		: 05/01/2017
 * Hora			: 15:31:45
 */
package com.cystesoft.vyrbus.service.business;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

import com.cystesoft.vyrbus.model.bean.DetalleButaca;
import com.cystesoft.vyrbus.model.bean.Usuario;

/**
 * @author Jose Abanto
 *
 */
public interface DetalleButacaManager {
	/**
	 *
	 * @param estado
	 * @param criterioOrden
	 * @return
	 */
	public ArrayList<DetalleButaca> buscarPorEstadoRegistro(String estado, String criterioOrden);

	/**
	 *
	 * @param criteriosBusqueda
	 * @param criteriosOrdenar
	 * @return
	 */
	public ArrayList<DetalleButaca> buscarPorX(TreeMap<String, Object> criteriosBusqueda, List<String> criteriosOrdenar);

	/**
	 *
	 * @param id
	 * @return
	 */
	public DetalleButaca buscarPorId(Long id);

	/**
	 *
	 * @param detalleButaca
	 */
	public void guardar(DetalleButaca detalleButaca);

	/**
	 *
	 * @param detalleButaca
	 */
	public void actualizar(DetalleButaca detalleButaca);

	/**
	 *
	 * @param id
	 */
	public void inactivar(Long id);
	/**
	 *
	 * @param detalleButacas
	 * @return
	 * @throws Exception
	 */
	public int guardar(List<DetalleButaca> detalleButacas, String fechaInicial, String fechaFinal, Integer servicioId, Usuario usuario)throws Exception;
}
