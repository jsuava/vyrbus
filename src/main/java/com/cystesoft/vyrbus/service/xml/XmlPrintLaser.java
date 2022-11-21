/**
 * Proyecto		: SISVYR
 * Sistema		: Sistema de Ventas y Reservas
 * Descripción	: 
 * Autor		: José Abanto
 * Fecha		: 20 nov. 2022
 * Hora			: 03:06:57
 */
package com.cystesoft.vyrbus.service.xml;

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author abant
 *
 */
@XmlRootElement
public class XmlPrintLaser {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private XmlConfigPrint a_configPrint;
	private List<XmlManifiesto> xmlManifiesto;	
	private XmlCarpetaDespacho xmlCarpetaDespacho;
	private XmlHRE xmlHRE;
	
	private String z_rpt;

	/**
	 * @return the a_configPrint
	 */
	public XmlConfigPrint getA_configPrint() {
		return a_configPrint;
	}

	/**
	 * @param a_configPrint the a_configPrint to set
	 */
	public void setA_configPrint(XmlConfigPrint a_configPrint) {
		this.a_configPrint = a_configPrint;
	}

	/**
	 * @return the xmlManifiesto
	 */
	public List<XmlManifiesto> getXmlManifiesto() {
		return xmlManifiesto;
	}

	/**
	 * @param xmlManifiesto the xmlManifiesto to set
	 */
	public void setXmlManifiesto(List<XmlManifiesto> xmlManifiesto) {
		this.xmlManifiesto = xmlManifiesto;
	}

	/**
	 * @return the z_rpt
	 */
	public String getZ_rpt() {
		return z_rpt;
	}

	/**
	 * @param z_rpt the z_rpt to set
	 */
	public void setZ_rpt(String z_rpt) {
		this.z_rpt = z_rpt;
	}

	/**
	 * @return the xmlCarpetaDespacho
	 */
	public XmlCarpetaDespacho getXmlCarpetaDespacho() {
		return xmlCarpetaDespacho;
	}

	/**
	 * @param xmlCarpetaDespacho the xmlCarpetaDespacho to set
	 */
	public void setXmlCarpetaDespacho(XmlCarpetaDespacho xmlCarpetaDespacho) {
		this.xmlCarpetaDespacho = xmlCarpetaDespacho;
	}

	/**
	 * @return the xmlHRE
	 */
	public XmlHRE getXmlHRE() {
		return xmlHRE;
	}

	/**
	 * @param xmlHRE the xmlHRE to set
	 */
	public void setXmlHRE(XmlHRE xmlHRE) {
		this.xmlHRE = xmlHRE;
	}
}
