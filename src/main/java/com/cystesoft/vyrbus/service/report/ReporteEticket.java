package com.cystesoft.vyrbus.service.report;

import java.util.List;

import com.cystesoft.vyrbus.model.bean.VentaPasaje;
import com.cystesoft.vyrbus.service.util.Util;

import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRField;

public class ReporteEticket implements JRDataSource {
	private List<VentaPasaje> lstSales;
	private int index=-1;

	public ReporteEticket(List<VentaPasaje> lstSales){
		this.lstSales=lstSales;
	}

	@Override
	public Object getFieldValue(JRField field) throws JRException {
		Object value = null;
		String fieldName = field.getName();
		if("ncontrol".equals(fieldName))
			value = lstSales.get(index).getNumeroControl();
		else if("voucher".equals(fieldName))
			value = lstSales.get(index).getNumeroBoleto();
		else if("pasajero".equals(fieldName))
			value = lstSales.get(index).getPasajero().toString();
		else if("fecViaje".equals(fieldName))
			value = lstSales.get(index).getFechaPartida();
		else if("tipoDoc".equals(fieldName))
			value = lstSales.get(index).getPasajero().getTipoDocumento().getDenominacion();
		else if("documento".equals(fieldName))
			value = lstSales.get(index).getPasajero().getNumeroDocumento();
		else if("origen".equals(fieldName))
			value = lstSales.get(index).getRuta().getOrigen();
		else if("destino".equals(fieldName))
			value = lstSales.get(index).getRuta().getDestino();
		else if("servicio".equals(fieldName))
			value = lstSales.get(index).getServicio().getDenominacion();
		else if("asiento".equals(fieldName))
			value = lstSales.get(index).getNumeroAsiento().longValue();
		else if("embarque".equals(fieldName))
			value = lstSales.get(index).getAgenciaPartida().getDenominacion();
		else if("hora".equals(fieldName))
			value = lstSales.get(index).getHoraEmbarque();
		else if("centrocosto".equals(fieldName))
			value=lstSales.get(index).getCentroCosto()!=null?lstSales.get(index).getCentroCosto().getCodigo()+"-"+lstSales.get(index).getCentroCosto().getDenominacion():"";
		else if("labelcentrocosto".equals(fieldName))
			value=lstSales.get(index).getCentroCosto()!=null?"CENTRO COSTO :":"";
		else if("importePagado".equals(fieldName))
			value = Util.toNumberFormat(lstSales.get(index).getImportePagado(),2);
		else if ("labelImporteOtraMoneda".equals(fieldName))
			value = (lstSales.get(index).getTipoMoneda()!=null?"IMPORTE ("+lstSales.get(index).getTipoMoneda().getUnidadMonetaria()+")":"");
		else if ("importeOtraMoneda".equals(fieldName))
			value = (lstSales.get(index).getTipoMoneda()!=null && lstSales.get(index).getImportePagadoEquibalente()!=null?lstSales.get(index).getTipoMoneda().getSimboloMonetario()+" "+Util.toNumberFormat(lstSales.get(index).getImportePagadoEquibalente(),2):"");
		return value;
	}

	@Override
	public boolean next() throws JRException {
		index++;
		return (index<lstSales.size());
	}

}
