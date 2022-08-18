/**
 * Proyecto		: SISVYR
 * Sistema		: Sistema de Ventas y Reservas
 * Descripción	:
 * Autor		: José Abanto
 * Fecha		: 14/10/2015
 * Hora			: 15:21:18
 */
package com.cystesoft.vyrbus.model.bean.report;

import java.io.Serializable;
import java.util.List;
import java.util.TreeMap;

/**
 * @author jabanto
 *
 */
public class RptAvanceBuses implements Serializable{

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	private TreeMap<String, RptAvanceBus> mapAvanceBus;
	private List<String> encabezado;


	public RptAvanceBuses(){
		super();
	}

	public RptAvanceBuses (TreeMap<String, RptAvanceBus> mapAvanceBus, List<String> encabezado){
		this.setMapAvanceBus(mapAvanceBus);
		this.setEncabezado(encabezado);
	}


	/**
	 * @return the encabezado
	 */
	public List<String> getEncabezado() {
		return encabezado;
	}
	/**
	 * @param encabezado the encabezado to set
	 */
	public void setEncabezado(List<String> encabezado) {
		this.encabezado = encabezado;
	}

	/**
	 * @return the mapAvanceBus
	 */
	public TreeMap<String, RptAvanceBus> getMapAvanceBus() {
		return mapAvanceBus;
	}

	/**
	 * @param mapAvanceBus the mapAvanceBus to set
	 */
	public void setMapAvanceBus(TreeMap<String, RptAvanceBus> mapAvanceBus) {
		this.mapAvanceBus = mapAvanceBus;
	}


}
