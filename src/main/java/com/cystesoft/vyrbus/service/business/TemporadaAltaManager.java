package com.cystesoft.vyrbus.service.business;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

import com.cystesoft.vyrbus.model.bean.TemporadaAlta;

/**
 *
 * @author JABANTO
 *
 */
public interface TemporadaAltaManager {
	public ArrayList<TemporadaAlta> buscarPorX(TreeMap<String, Object> criteriosBusqueda, List<String> criteriosOrdenar);

	/**
	 * Busca por el estado registro
	 * @param estado		: Estdo registro a buscar
	 * @param criterioOrden	: Criterios para el orden de la Data.
	 * @return
	 */
	public ArrayList<TemporadaAlta> buscarPorEstadoRegistro(String estado, String criterioOrden);

	/**
	 * Guarda un registro nuevo
	 * @param temporadaAlta
	 */
	public void guardar(TemporadaAlta temporadaAlta);

	/**
	 * Actualiza el Registro
	 * @param temporadaAlta
	 */
	public void actualizar(TemporadaAlta temporadaAlta);


	/**
	 * Busca temporada alta
	 * @param anio 	: Anio a consultar
	 * @param mes  	: Opcional, mes del anio a consultar
	 * @param dia	: Opcional, dia del mes a consultar
	 * @return
	 */
	public ArrayList<TemporadaAlta> buscarTemporadaAlta(String anio, String mes,  String dia) throws Exception;

	/**
	 * Anula temporada alta
	 * @param anio 		: Anio afectado a la eliminación.
	 * @param ms		: opcional, mes.
	 * @param dia 	: Usuario que realiza la anulacion.
	 * @throws Exception
	 */
	public void anularTemporadaAlta(String anio, String mes, String dia) throws Exception;
}
