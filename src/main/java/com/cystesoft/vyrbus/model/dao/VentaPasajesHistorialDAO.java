/**
 * Proyecto		: SISVYR
 * Sistema		: Sistema de Ventas y Reservas.
 * Descripción	: Declaración de los metodos relacionados con la Venta de Pasajes.
 * Autor		: José Sullo Avalos
 * Fecha		: 05/07/2012
 */
package com.cystesoft.vyrbus.model.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

import com.cystesoft.vyrbus.model.bean.Agencia;
import com.cystesoft.vyrbus.model.bean.Itinerario;
import com.cystesoft.vyrbus.model.bean.Manifiesto;
import com.cystesoft.vyrbus.model.bean.Ruta;
import com.cystesoft.vyrbus.model.bean.Usuario;
import com.cystesoft.vyrbus.model.bean.VentaPasaje;
import com.cystesoft.vyrbus.model.bean.VentaPasajeHistorial;
import com.cystesoft.vyrbus.service.mappers.ResumenAnulacionPostergacion;
import com.cystesoft.vyrbus.service.mappers.ResumenVentas;
import com.cystesoft.vyrbus.service.mappers.VentasPiloto;

/**
 *
 * @author 
 * @since JDK1.6
 */
public interface VentaPasajesHistorialDAO extends GenericDAO {
	/**
	 * 
	 * @param estado
	 * @param criterioOrden
	 * @return
	 */
	public ArrayList<VentaPasajeHistorial> buscarPorEstadoRegistro(String estado, String criterioOrden);
	
	/**
	 * 
	 * @param criteriosBusqueda
	 * @param criteriosOrdenar
	 * @return
	 */
	public ArrayList<VentaPasajeHistorial> buscarPorX(TreeMap<String, Object> criteriosBusqueda, List<String> criteriosOrdenar);
	
	/**
	 * 
	 * @param id
	 * @return
	 */
	public VentaPasajeHistorial buscarPorId(Long id);
	
	/**
	 * 
	 * @param ventaPasajeHistorial
	 */
	public void guardar(VentaPasajeHistorial ventaPasajeHistorial);
	
	/**
	 * 
	 * @param ventaPasajeHistorial
	 */
	public void actualizar(VentaPasajeHistorial ventaPasajeHistorial);
}
