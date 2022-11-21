/**
 * Proyecto		: SISVYR
 * Sistema		: Sistema de Ventas y Reservas
 * Descripción	: 
 * Autor		: José Abanto
 * Fecha		: 20 nov. 2022
 * Hora			: 02:47:30
 */
package com.cystesoft.vyrbus.service.xml;

import java.io.Serializable;
import java.util.List;

/**
 * @author abant
 *
 */
public class XmlDetalleCarpetaDespacho implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private List<XmlItemDetalleDespacho> xmlItemDetalleDespacho;
	/**
	 * @return the xmlItemDetalleDespacho
	 */
	public List<XmlItemDetalleDespacho> getXmlItemDetalleDespacho() {
		return xmlItemDetalleDespacho;
	}
	/**
	 * @param xmlItemDetalleDespacho the xmlItemDetalleDespacho to set
	 */
	public void setXmlItemDetalleDespacho(List<XmlItemDetalleDespacho> xmlItemDetalleDespacho) {
		this.xmlItemDetalleDespacho = xmlItemDetalleDespacho;
	}
}
