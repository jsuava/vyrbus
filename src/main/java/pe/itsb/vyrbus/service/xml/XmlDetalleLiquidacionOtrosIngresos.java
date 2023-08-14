/**
 * Proyecto		: SISVYR
 * Sistema		: Sistema de Ventas y Reservas
 * Descripción	:
 * Autor		: José Abanto
 * Fecha		: 14 set. 2021
 * Hora			: 15:02:27
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
public class XmlDetalleLiquidacionOtrosIngresos implements Serializable{

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	private List<XmlItemOtroIngresoLiquidacion> v1_ItemOtroIngresoLiquidacion;

	public XmlDetalleLiquidacionOtrosIngresos() {
		super();
	}

	public XmlDetalleLiquidacionOtrosIngresos(List<XmlItemOtroIngresoLiquidacion> v1_ItemOtroIngresoLiquidacion) {
		this.v1_ItemOtroIngresoLiquidacion = v1_ItemOtroIngresoLiquidacion;
	}

	/**
	 * @return the v1_itemItemOtroIngresoLiquidacion
	 */
	public List<XmlItemOtroIngresoLiquidacion> getV1_ItemOtroIngresoLiquidacion() {
		return v1_ItemOtroIngresoLiquidacion;
	}

	/**
	 * @param v1_itemItemOtroIngresoLiquidacion the v1_itemItemOtroIngresoLiquidacion to set
	 */
	public void setV1_ItemOtroIngresoLiquidacion(List<XmlItemOtroIngresoLiquidacion> v1_ItemOtroIngresoLiquidacion) {
		this.v1_ItemOtroIngresoLiquidacion = v1_ItemOtroIngresoLiquidacion;
	}
}
