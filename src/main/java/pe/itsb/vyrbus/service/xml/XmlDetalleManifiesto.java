/**
 * Proyecto		: SISVYR
 * Sistema		: Sistema de Ventas y Reservas
 * Descripci�n	:
 * Autor		: Jos� Abanto
 * Fecha		: 20 nov. 2022
 * Hora			: 02:52:16
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
public class XmlDetalleManifiesto implements Serializable{

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	private List<XmlItemDetalleManifiesto> XmlItemDetalleManifiesto;
	/**
	 * @return the xmlItemDetalleManifiesto
	 */
	public List<XmlItemDetalleManifiesto> getXmlItemDetalleManifiesto() {
		return XmlItemDetalleManifiesto;
	}
	/**
	 * @param xmlItemDetalleManifiesto the xmlItemDetalleManifiesto to set
	 */
	public void setXmlItemDetalleManifiesto(List<XmlItemDetalleManifiesto> xmlItemDetalleManifiesto) {
		XmlItemDetalleManifiesto = xmlItemDetalleManifiesto;
	}
}
