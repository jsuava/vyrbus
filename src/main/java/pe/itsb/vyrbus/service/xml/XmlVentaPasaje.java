/**
 * Proyecto		: SISVYR
 * Sistema		: Sistema de Ventas y Reservas
 * Descripción	:
 * Autor		: José Abanto
 * Fecha		: 07/10/2016
 * Hora			: 15:32:10
 */
package pe.itsb.vyrbus.service.xml;

import java.io.Serializable;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author jabanto
 *
 */
@XmlRootElement
public class XmlVentaPasaje implements Serializable, Cloneable{
	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	private XmlConfigPrint configPrint;
	private List<XmlVenta> venta;
	private XmlLiquidacionTuentrada liqTuentrada;
	private XmlLiquidacion liquidacion;

	/**
	 * @return the venta
	 */
	public List<XmlVenta> getVenta() {
		return venta;
	}

	/**
	 * @param venta the venta to set
	 */
	public void setVenta(List<XmlVenta> venta) {
		this.venta = venta;
	}

	/**
	 * @return the configPrint
	 */
	public XmlConfigPrint getConfigPrint() {
		return configPrint;
	}

	/**
	 * @param configPrint the configPrint to set
	 */
	public void setConfigPrint(XmlConfigPrint configPrint) {
		this.configPrint = configPrint;
	}

	/**
	 * @return the liqTuentrada
	 */
	public XmlLiquidacionTuentrada getLiqTuentrada() {
		return liqTuentrada;
	}

	/**
	 * @param liqTuentrada the liqTuentrada to set
	 */
	public void setLiqTuentrada(XmlLiquidacionTuentrada liqTuentrada) {
		this.liqTuentrada = liqTuentrada;
	}

	/**
	 * @return the liquidacion
	 */
	public XmlLiquidacion getLiquidacion() {
		return liquidacion;
	}

	/**
	 * @param liquidacion the liquidacion to set
	 */
	public void setLiquidacion(XmlLiquidacion liquidacion) {
		this.liquidacion = liquidacion;
	}

}
