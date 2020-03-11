/**
 * Proyecto		: SISVYR
 * Sistema		: Sistema de Ventas y Reservas
 * Descripción	: 
 * Autor		: José Abanto
 * Fecha		: 19/08/2015
 * Hora			: 12:32:56
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
 * @author jabanto
 *
 */
public class ReporteEticketTuEntrada implements JRDataSource {

	private List<VentaPasaje> lstSales;
	private int index=-1;
	
	public ReporteEticketTuEntrada(List<VentaPasaje> lstSales){
		this.lstSales=lstSales;
	}
	

	public Object getFieldValue(JRField field) throws JRException {
		Object value = null;
		String fieldName = field.getName();
		if("voucher".equals(fieldName))
			value = lstSales.get(index).getNumeroBoleto();
		else if("origen".equals(fieldName))
			value=lstSales.get(index).getRuta().getOrigen();
		else if("destino".equals(fieldName))
			value=lstSales.get(index).getRuta().getDestino();
		else if("pasajero".equals(fieldName))
			value=lstSales.get(index).getPasajero().toString();
		else if("importe".equals(fieldName))
			value="S/. "+Util.toNumberFormat(lstSales.get(index).getImportePagado(),2);
		else if("fechaPartida".equals(fieldName))
			value=Constantes.FORMAT_DATE.format(lstSales.get(index).getFechaPartida());
		else if("horaPartida".equals(fieldName))
			value=lstSales.get(index).getHoraPartida();
		else if("documentoIdentidad".equals(fieldName))
			value=lstSales.get(index).getPasajero().getNumeroDocumento();
		else if("asiento".equals(fieldName))
			value=lstSales.get(index).getNumeroAsiento().toString();
		else if("puntoEmbarque".equals(fieldName))
			value=lstSales.get(index).getAgenciaPartida().getNombreCorto();
		else if("numeroControl".equals(fieldName))
			value=lstSales.get(index).getNumeroControl();
		else if("usuario".equals(fieldName))
			value=lstSales.get(index).getUsuarioInsercion();
		else if("fechaOperacion".equals(fieldName))
			value=Constantes.FORMAT_DATE.format(lstSales.get(index).getFechaLiquidacion());
		else if("empresa".equals(fieldName))
			value=(lstSales.get(index).getCliente()!=null?lstSales.get(index).getCliente().toString():"");
		else if("rucEmpresa".equals(fieldName))
			value=(lstSales.get(index).getCliente()!=null?lstSales.get(index).getCliente().getNumeroDocumento():"");
		
		return value;
	}


	public boolean next() throws JRException {
		index++;
		return (index<lstSales.size());
	}
	

}
