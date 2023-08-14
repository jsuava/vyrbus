/**
 * Proyecto		: SISVYR
 * Sistema		: Sistema de Ventas y Reservas
 * Descripción	:
 * Autor		: José Abanto
 * Fecha		: 24 abr. 2022
 * Hora			: 01:39:59
 */
package pe.itsb.vyrbus.service.xml;

import java.io.Serializable;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author abant
 *
 */
@XmlRootElement
public class XmlEquipajes implements Serializable{

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	private List<XmlEquipaje> equipaje;

	/**
	 * @return the equipaje
	 */
	public List<XmlEquipaje> getEquipaje() {
		return equipaje;
	}

	/**
	 * @param equipaje the equipaje to set
	 */
	public void setEquipaje(List<XmlEquipaje> equipaje) {
		this.equipaje = equipaje;
	}



}
