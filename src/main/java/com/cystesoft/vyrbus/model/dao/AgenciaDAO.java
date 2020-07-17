/**
 * Proyecto		: SISVYR
 * Sistema		: Sistema de Ventas y Reservas
 * Descripción	: 
 * Autor		: jM
 * Fecha		: 04/05/2012
 */
package com.cystesoft.vyrbus.model.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

import com.cystesoft.vyrbus.model.bean.Agencia;

/**
 *
 * @author jM
 * @since JDK1.6
 */
public interface AgenciaDAO extends GenericDAO {
	public ArrayList<Agencia> buscarPorEstadoRegistro(String estado, String criterioOrden);
	public ArrayList<Agencia> buscarPorX(TreeMap<String, Object> criteriosBusqueda, List<String> criteriosOrdenar);
	public Agencia buscarPorId(Long id);
	public void guardar(Agencia agencia);
	public void actualizar(Agencia agencia);
	public void inactivar(Long id);
	/**
	 * Buscar la agencia de acuerdo al RUC de la Agencia de viajes o Corporativo (Concesionarios).
	 * @param ruc	: Ruc del concesionario a buscar.
	 * @return Agencia.
	 * @throws Exception
	 */
	public Agencia buscarAgenciaByRucClienteCredito(String ruc)throws Exception;
	/**
	 * Realiza la busqueda de las Agencias de acuerdo a lso criterios enviados
	 * @param campo				: Campo que debera cumplir con los criterios enviados.
	 * @param criterios			: Identificadores de las agencias separados por comas.
	 * @param criteriosOrdenar	: Criterios a utilizar para ordenar la informacion.
	 * @param estadoRegistro	: Estado de los registros.
	 * @return Lista de Agencias.
	 */
	public List<Agencia> buscarPorX(String campo, Object[] criterios, List<String> criteriosOrdenar, String estadoRegistro) throws Exception;
	/**
	 * Realiza la busqueda de las Agencias que han emitido Vouchers o RC y que aun no tienen Boleto.
	 * @param idTipoComprobante	: Identificador del tipo de comprobante.
	 * @param fechaPartida		: Fecha de partida del servicio.
	 * @return Lista de Agencias.
	 */
	public List<Agencia> buscarAgenciaComprobantesSinBoleto(Integer idTipoComprobante, String fechaPartida);
	/**
	 * Busca todoas la agencias para que se van a cargar en el detalle corporativo
	 * @return Lista de Agencias
	 * @throws Exception
	 */
	public List<Agencia> buscarAgenciasByDetalleCorporativo()throws Exception;
	/**
	 * Realiza la busqueda de las Agencias segun la localidad
	 * @param localidades	: Localidades a filtrar
	 * @return
	 * @throws Exception
	 */
	public List<Agencia>buscarAgenciaByLocalidad(String localidades)throws Exception;
}