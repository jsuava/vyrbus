/**
 * Proyecto		: SISVYR
 * Sistema		: Sistema de Ventas y Reservas
 * Descripción	:
 * Autor		: José Abanto
 * Fecha		: 14 set. 2021
 * Hora			: 14:48:16
 */
package pe.itsb.vyrbus.service.xml;

import java.io.Serializable;
import java.util.List;

import javax.xml.bind.annotation.XmlType;

/**
 * @author abant
 *
 */
@XmlType
public class XmlDetalleLiquidacionIngresoVenta implements Serializable{

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	private List<XmlItemIngresoVentaLiquidacion> v1_itemIngresoVentaLiquidacion;
	/**
	 * @return the v1_itemIngresoVentaLiquidacion
	 */
	public List<XmlItemIngresoVentaLiquidacion> getV1_itemIngresoVentaLiquidacion() {
		return v1_itemIngresoVentaLiquidacion;
	}
	/**
	 * @param v1_itemIngresoVentaLiquidacion the v1_itemIngresoVentaLiquidacion to set
	 */
	public void setV1_itemIngresoVentaLiquidacion(List<XmlItemIngresoVentaLiquidacion> v1_itemIngresoVentaLiquidacion) {
		this.v1_itemIngresoVentaLiquidacion = v1_itemIngresoVentaLiquidacion;
	}

}
