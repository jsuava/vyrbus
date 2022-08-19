/**
 * Proyecto		: SISVYR
 * Sistema		: Sistema de Ventas y Reservas
 * Descripción	:
 * Autor		: José Abanto
 * Fecha		: 20/08/2015
 * Hora			: 15:54:59
 */
package com.cystesoft.vyrbus.service.report;

import java.util.List;

import com.cystesoft.vyrbus.model.bean.Liquidacion;
import com.cystesoft.vyrbus.service.util.Constantes;
import com.cystesoft.vyrbus.service.util.Util;

import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRField;

/**
 * @author jabanto
 *
 */
public class ReporteLiquidacionTuEntrada implements JRDataSource{
	List<Liquidacion> lstLiquidacion;
	private int index=-1;


	public ReporteLiquidacionTuEntrada(){
		super();
	}

	public ReporteLiquidacionTuEntrada(List<Liquidacion> lstLiquidacion){
		this.lstLiquidacion=lstLiquidacion;
	}

	/* (non-Javadoc)
	 * @see net.sf.jasperreports.engine.JRDataSource#getFieldValue(net.sf.jasperreports.engine.JRField)
	 */
	@Override
	public Object getFieldValue(JRField field) throws JRException {
		Object value = null;
		String fieldName = field.getName();
		if("puntoVenta".equals(fieldName))
			value = lstLiquidacion.get(index).getAgencia().getDenominacion();
		else if ("fechaLiquidacion".equals(fieldName))
			value=Constantes.FORMAT_DATE.format(lstLiquidacion.get(index).getFechaLiquidacion());
		else if("nombresUsuario".equals(fieldName))
			value=lstLiquidacion.get(index).getUsuario().getNombre();
		else if("usuario".equals(fieldName))
			value="Usuario : "+lstLiquidacion.get(index).getNombreUsuario();
		else if("cantidadVentaContado".equals(fieldName))
			value=lstLiquidacion.get(index).getCantidadContado().toString();
		else if("cantidadVentaTCredito".equals(fieldName))
			value=lstLiquidacion.get(index).getCantidadTarjetaVisa().toString();
		else if("cantidadTotal".equals(fieldName))
			value=String.valueOf(lstLiquidacion.get(index).getCantidadContado()+lstLiquidacion.get(index).getCantidadTarjetaVisa());
		else if("totalContado".equals(fieldName))
			value=Util.toNumberFormat(lstLiquidacion.get(index).getMontoContado(),2);
		else if("totalTCredito".equals(fieldName))
			value=Util.toNumberFormat(lstLiquidacion.get(index).getMontoTarjetaVisa(),2);
		else if("total".equals(fieldName))
			value=Util.toNumberFormat(lstLiquidacion.get(index).getMontoContado()+lstLiquidacion.get(index).getMontoTarjetaVisa(),2);
		else if("totalALiquidar".equals(fieldName))
			value=Util.toNumberFormat(lstLiquidacion.get(index).getMontoContado(), 2);

		return value;
	}



	/* (non-Javadoc)
	 * @see net.sf.jasperreports.engine.JRDataSource#next()
	 */
	@Override
	public boolean next() throws JRException {
		index++;
		return (index<lstLiquidacion.size());
	}

}
