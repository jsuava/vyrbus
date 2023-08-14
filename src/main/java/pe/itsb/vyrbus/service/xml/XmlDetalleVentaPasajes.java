/**
 * Proyecto		: SISVYR
 * Sistema		: Sistema de Ventas y Reservas
 * Descripción	:
 * Autor		: José Abanto
 * Fecha		: 07/10/2016
 * Hora			: 17:42:30
 */
package pe.itsb.vyrbus.service.xml;

import java.io.Serializable;
import java.util.List;

import javax.xml.bind.annotation.XmlType;

/**
 * @author jabanto
 *
 */
@XmlType
public class XmlDetalleVentaPasajes implements Serializable{
	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	private List<XmlItem> item;

	/**
	 * @return the item
	 */
	public List<XmlItem> getItem() {
		return item;
	}

	/**
	 * @param item the item to set
	 */
	public void setItem(List<XmlItem> item) {
		this.item = item;
	}
}
