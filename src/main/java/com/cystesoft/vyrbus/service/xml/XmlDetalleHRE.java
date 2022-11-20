/**
 * Proyecto		: SISVYR
 * Sistema		: Sistema de Ventas y Reservas
 * Descripción	: 
 * Autor		: José Abanto
 * Fecha		: 20 nov. 2022
 * Hora			: 02:56:21
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
public class XmlDetalleHRE implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private List<XmlItemDetalleHRE> xmlItemDetalleHREs;
	/**
	 * @return the xmlItemDetalleHREs
	 */
	public List<XmlItemDetalleHRE> getXmlItemDetalleHREs() {
		return xmlItemDetalleHREs;
	}
	/**
	 * @param xmlItemDetalleHREs the xmlItemDetalleHREs to set
	 */
	public void setXmlItemDetalleHREs(List<XmlItemDetalleHRE> xmlItemDetalleHREs) {
		this.xmlItemDetalleHREs = xmlItemDetalleHREs;
	}
}
