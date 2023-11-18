/**
 * Proyecto		: SISVYR
 * Sistema		: Sistema de Ventas y Reservas
 * Descripci�n	:
 * Autor		: Jos� Abanto
 * Fecha		: 30/09/2014
 * Hora			: 15:50:46
 */
package com.cystesoft.vyrbus.service.report;

import java.util.List;

import com.cystesoft.vyrbus.model.bean.VentaPasaje;
import com.cystesoft.vyrbus.service.util.Constantes;
import com.cystesoft.vyrbus.service.util.Util;

import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRField;

/**
 * @author JABANTO
 *
 */
public class ReporteManifiesto implements JRDataSource{
	private int index = -1;
	private List<VentaPasaje> listPasajeros;

	public ReporteManifiesto(List<VentaPasaje> listPasajeros){
		this.listPasajeros = listPasajeros;
	}

	/* (non-Javadoc)
	 * @see net.sf.jasperreports.engine.JRDataSource#getFieldValue(net.sf.jasperreports.engine.JRField)
	 */
	@Override
	public Object getFieldValue(JRField field) throws JRException {
		Object value = null;
		String fieldName = field.getName();
		if("asiento".equals(fieldName))
			value = (listPasajeros.get(index).getNumeroAsiento());
		else if ("boleto".equals(fieldName))
			value = (listPasajeros.get(index).getNumeroBoleto()!=null?listPasajeros.get(index).getNumeroBoleto():"");
		else if ("nombrePasajero".equals(fieldName))
			value = (listPasajeros.get(index).getPasajero()!=null?listPasajeros.get(index).getPasajero().toString():"");
		else if ("edad".equals(fieldName))
			try {
				value = (listPasajeros.get(index).getPasajero()!=null?Util.calculaEdad(listPasajeros.get(index).getPasajero().getFechaNacimiento()).toString():"");
			} catch (Exception e) {
				e.printStackTrace();
			}
		else if ("tipoDocumento".equals(fieldName)){
			value = (listPasajeros.get(index).getPasajero()!=null?listPasajeros.get(index).getPasajero().getTipoDocumento().getDenominacion():" ");
		}else if ("numeroDocumento".equals(fieldName))
			value = (listPasajeros.get(index).getPasajero()!=null?(listPasajeros.get(index).getPasajero().getNumeroDocumento()!=null?listPasajeros.get(index).getPasajero().getNumeroDocumento():""):"");
		else if ("destino".equals(fieldName))
			value = (listPasajeros.get(index).getRuta()!=null?listPasajeros.get(index).getAgenciaLlegada().getDenominacion():"");
//		value = (listPasajeros.get(index).getRuta()!=null?listPasajeros.get(index).getRuta().getDestino():"");
		else if ("ptoEmbarque".equals(fieldName))
			value = (listPasajeros.get(index).getAgenciaPartida()!=null?listPasajeros.get(index).getAgenciaPartida().getNombreCorto():"");
		else if ("importe".equals(fieldName))
			value = (listPasajeros.get(index).getImportePagado()!=null?"S/ "+ Util.toNumberFormat(listPasajeros.get(index).getImportePagado(),2):"");
		else if ("formaPago".equals(fieldName)){
			if(listPasajeros.get(index).getFormaPago()!=null)
				value = (listPasajeros.get(index).getFormaPago().getId().intValue()!=Constantes.ID_FORPAG_CORTESIA?"CONTADO":listPasajeros.get(index).getFormaPago().getDenominacion());
			else
				value="";
		}
		return value;
	}

	/* (non-Javadoc)
	 * @see net.sf.jasperreports.engine.JRDataSource#next()
	 */
	@Override
	public boolean next() throws JRException {
		index++;
		return (index<listPasajeros.size());
	}
}
