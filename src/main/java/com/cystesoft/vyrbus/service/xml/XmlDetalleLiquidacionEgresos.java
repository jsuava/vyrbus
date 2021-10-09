/**
 * Proyecto		: SISVYR
 * Sistema		: Sistema de Ventas y Reservas
 * Descripción	: 
 * Autor		: José Abanto
 * Fecha		: 14 set. 2021
 * Hora			: 15:03:02
 */
package com.cystesoft.vyrbus.service.xml;

import java.io.Serializable;
import java.util.List;

import javax.xml.bind.annotation.XmlType;

/**
 * @author abant
 *
 */
@XmlType
public class XmlDetalleLiquidacionEgresos implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private List<XmlItemEgresoLiquidacion> v1_ItemEgresoLiquidacion;

	
	public XmlDetalleLiquidacionEgresos() {
		super();
	}
	
	public XmlDetalleLiquidacionEgresos(List<XmlItemEgresoLiquidacion> v1_ItemEgresoLiquidacion) {
		this.v1_ItemEgresoLiquidacion = v1_ItemEgresoLiquidacion;
	}
	
	/**
	 * @return the v1_ItemEgresoLiquidacion
	 */
	public List<XmlItemEgresoLiquidacion> getV1_ItemEgresoLiquidacion() {
		return v1_ItemEgresoLiquidacion;
	}

	/**
	 * @param v1_ItemEgresoLiquidacion the v1_ItemEgresoLiquidacion to set
	 */
	public void setV1_ItemEgresoLiquidacion(List<XmlItemEgresoLiquidacion> v1_ItemEgresoLiquidacion) {
		this.v1_ItemEgresoLiquidacion = v1_ItemEgresoLiquidacion;
	}

}
