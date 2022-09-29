/**
 * Proyecto		: SISVYR
 * Sistema		: Sistema de Ventas y Reservas
 * Descripción	:
 * Autor		: José Abanto
 * Fecha		: 23 may. 2021
 * Hora			: 20:38:25
 */
package com.cystesoft.vyrbus.view.ctrl;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.eclipse.jdt.internal.compiler.impl.Constant;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.Label;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Radio;
import org.zkoss.zul.Tab;

import com.cystesoft.vyrbus.model.bean.Agencia;
import com.cystesoft.vyrbus.model.bean.EntidadEncomiendaPasajes;
import com.cystesoft.vyrbus.model.bean.TipoComprobante;
import com.cystesoft.vyrbus.model.bean.Usuario;
import com.cystesoft.vyrbus.service.locator.ServiceLocator;
import com.cystesoft.vyrbus.service.mappers.ResumenVentas;
import com.cystesoft.vyrbus.service.util.Constantes;
import com.cystesoft.vyrbus.service.util.Util;
import com.cystesoft.vyrbus.service.util.UtilData;
import com.cystesoft.vyrbus.view.ui.DlgMessage;
import com.cystesoft.vyrbus.view.ui.WndBase;

/**
 * @author Marco
 *
 */
public class WndRptGeneralVentas extends WndBase implements Serializable {

	private static final long serialVersionUID = 1L;

	private Radio rdFecha;
	private Radio rdMes;
	private Radio rdAnual;
	private Datebox dtbxFecInicioBus;
	private Datebox dtbxFecFinBus;
	private Combobox cmbAgencias;

	private Listbox lsbxVentas;
//	private Listbox lsbxTarifaFA;
//

	private Label lblHoraConsulta;
	private Label lblUsuarioConsulta;
	private Label lblFechaConsulta;

	private Tab tabVentas;

	private String[] arMeses = {"NAV", "ENE", "FEB", "MAR", "ABR", "MAY", "JUN", "JUL", "AGO", "SET", "OCT", "NOV", "DIC"};
	private List<Double> listRecordTotales = new ArrayList<>();
	private List<String> listCabeceraRecordTotales = new ArrayList<>();

	private Integer TIPO_ENTIDAD_AGENCIA = 1;
	private Integer IDTIPO_BOLETA_ENCOMIENDA = 1;
	private Integer IDTIPO_FACTURA_ENCOMIENDA = 2;
	private Integer IDTIPO_GRT_ENCOMIENDA = 3;
	
	Integer flag = 0;
	Integer ambosPisos=0;

	@Override
	public void initComponents() {
		// TODO Auto-generated method stub
		tabVentas = (Tab)this.getFellow("tabVentas");
//		tabVentasAnual = (Tab)this.getFellow("tabVentasAnual");

		cmbAgencias=(Combobox)this.getFellow("cmbAgencias");
		dtbxFecInicioBus=(Datebox)this.getFellow("dtbxFecInicioBus");
		dtbxFecFinBus=(Datebox)this.getFellow("dtbxFecFinBus");

		lsbxVentas=(Listbox)this.getFellow("lsbxVentas");
		rdFecha = (Radio)this.getFellow("rdFecha");
		rdMes = (Radio)this.getFellow("rdMes");
		rdAnual = (Radio)this.getFellow("rdAnual");


		lblHoraConsulta = (Label)this.getFellow("lblHoraConsulta");
		lblFechaConsulta = (Label)this.getFellow("lblFechaConsulta");
		lblUsuarioConsulta = (Label)this.getFellow("lblUsuarioConsulta");

	}

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.view.ui.WndBase#onCreate()
	 */
	@Override
	public void onCreate() throws Exception {
		// TODO Auto-generated method stub
		String fecha = Util.DatetoString(new Date(), "dd/MM/yyyy");

		lblFechaConsulta.setValue(fecha);

		Usuario usuario= (Usuario) getDesktop().getSession().getAttribute(Constantes.ATRIBUTO_USUARIO);
		lblUsuarioConsulta.setValue(usuario.getLogin());

		//Para la busqueda
		UtilData.cargarDataCombo(cmbAgencias, Agencia.class,true);

		dtbxFecInicioBus.setValue(new Date());
		dtbxFecFinBus.setValue(new Date());

		inicializarControles(true);

		for(int i=0; i<13; i++){
			listRecordTotales.add(0.0);
		}
		listCabeceraRecordTotales.add("");
		listCabeceraRecordTotales.add("");

		/*EVENTO ON_CHANGE DEL LA FECHA INICIO*/
//		dtbxFecInicioBus.addEventListener(Events.ON_CHANGE, new EventListener<Event>() {
//			@Override
//			public void onEvent(Event event) throws Exception {
//				String fecha = Util.DatetoString(dtbxFecInicioBus.getValue(), "yyyyMMdd");
//				dtbxFecFinBus.setConstraint("after "+fecha);
//				/*Asigna a fecha fin el valor de la fecha inicio*/
//				dtbxFecFinBus.setValue(dtbxFecInicioBus.getValue());
//			}
//		});

	}

	public void buscar() throws Exception{
		String fechaDesde = Constantes.FORMAT_DATE.format(dtbxFecInicioBus.getValue());
		String fechaHasta = Constantes.FORMAT_DATE.format(dtbxFecFinBus.getValue());
		Integer idAgencia = 0;
		Integer tipoConsulta = 0;

		limpiarArrays();

		if(cmbAgencias.getSelectedItem().getValue() instanceof Agencia)
			idAgencia=((Agencia)cmbAgencias.getSelectedItem().getValue()).getId();

		if(idAgencia > 0){
			if(rdMes.isChecked()){
				tipoConsulta=3;
				//Reacomodando las fechas
				String anio, mes, dia;
				anio = fechaHasta.substring(6);
				mes = fechaHasta.substring(3, 5);
				dia = fechaHasta.substring(0, 2);
				fechaDesde = "01" + fechaDesde.substring(2);
				LocalDate fecInicial = LocalDate.of(Integer.parseInt(anio), Integer.parseInt(mes), Integer.parseInt(dia));
				LocalDate fechaFinal = fecInicial.withDayOfMonth(fecInicial.lengthOfMonth());
				// Getting system timezone
				ZoneId systemTimeZone = ZoneId.systemDefault();

				// converting LocalDateTime to ZonedDateTime with the system timezone
				ZonedDateTime zonedDateTime = fechaFinal.atStartOfDay(systemTimeZone);
				Date utilDate = Date.from(zonedDateTime.toInstant());
				fechaHasta = Constantes.FORMAT_DATE.format(utilDate);

			}
			else
				tipoConsulta=2;
		}else{
			if(rdMes.isChecked()){
				DlgMessage.information("Para realizar busquedas mensuales debe seleccionar una Agencia.");
				cmbAgencias.focus();
				return;
			}
			else
				tipoConsulta=1;
		}

		List<ResumenVentas> lstResumen = ServiceLocator.getVentaPasajesManager().buscarResumenVentas(fechaDesde, fechaHasta, idAgencia, tipoConsulta);

		if(lstResumen.size()>0){
			if(tipoConsulta ==3)
				ensamblarResumenVentasByMonth(lstResumen);
			else
				ensamblarResumenVentas(lstResumen);
		}
		else{
			DlgMessage.information("No se encontró infotrmacion para la información brindada.");
			cmbAgencias.focus();
			return;
		}
	}


	public void ensamblarResumenVentas(List<ResumenVentas> lstResumen) {

		List<Double> listRecord = new ArrayList<>();
		List<String> listCabeceraRecord = new ArrayList<>();

		int i=0;

		Util.limpiarListbox(lsbxVentas);

		int j, k;

		ResumenVentas resumen0 = lstResumen.get(i);

		for(j=0; j<13; j++){
			listRecord.add(0.0);
		}
		listCabeceraRecord.add("");
		listCabeceraRecord.add("");
		k=0;

		for(i = 0; i<lstResumen.size(); i++)
		{
			ResumenVentas resumen = lstResumen.get(i);

			if( resumen0.getFechaVenta().equals(resumen.getFechaVenta())
			    && resumen0.getAgencia().getId().equals(resumen.getAgencia().getId()) )
			{
				//Agencia
				if(k==0){
					listCabeceraRecord.set(0, resumen.getAgencia().getDenominacion());
					listCabeceraRecord.set(1, resumen.getFechaVenta());
					k++;
				}

				//Canal counter
				if(resumen.getCanalVenta().getId() == 1)
				{
					//Comprobante Boletas
					if(resumen.getTipoComprobante().getId() == 7){
						listRecord.set(0, resumen.getTotal());
						listRecord.set(1, resumen.getCantidad().doubleValue());
					}
					//Comprobantefacturas
					else if(resumen.getTipoComprobante().getId() == 2)
					{
						listRecord.set(2, resumen.getTotal());
						listRecord.set(3, resumen.getCantidad().doubleValue());
					}
					//Nota Credito
					else if(resumen.getTipoComprobante().getId() == 8)
					{
						listRecord.set(4, resumen.getTotal());
						listRecord.set(5, resumen.getCantidad().doubleValue());
					}
				}
				//Canal Web
				else if(resumen.getCanalVenta().getId() == 2)
				{
					listRecord.set(10, resumen.getTotal());
					listRecord.set(11, resumen.getCantidad().doubleValue());
				}

				if(lstResumen.size()-i == 1)
				{
					listarResumenVentas(listRecord, listCabeceraRecord);
					listarTotalesResumenVentas(listRecord);
				}
			}
			else
			{
				//reiniciamos la iteracion
				resumen0=null;
				resumen0 = lstResumen.get(i);
				i--;
				k=0;

				listarResumenVentas(listRecord, listCabeceraRecord);

				//Limpiar los Arrays
				for(j=0; j<13; j++){
					listRecord.set(j, 0.0);
				}
				listCabeceraRecord.set(0, "");
				listCabeceraRecord.set(1, "");

			}

		}

	}

	public void ensamblarResumenVentasByMonth(List<ResumenVentas> lstResumen){

		List<Double> listRecord = new ArrayList<>();
		List<String> listCabeceraRecord = new ArrayList<>();

		int i=0;


		Util.limpiarListbox(lsbxVentas);

		int j, k;
		//for(ResumenVentas resumen : lstResumen) {
		ResumenVentas resumen0 = lstResumen.get(i);

		for(j=0; j<13; j++){
			listRecord.add(0.0);
		}
		listCabeceraRecord.add("");
		listCabeceraRecord.add("");
		k=0;

		for(i = 0; i<lstResumen.size(); i++)
		{
			ResumenVentas resumen = lstResumen.get(i);

			if( resumen0.getMes().equals(resumen.getMes()) )
			{
				//Agencia
				if(k==0){
					listCabeceraRecord.set(0, resumen.getAgencia().getDenominacion());
					listCabeceraRecord.set(1, arMeses[Integer.parseInt(resumen.getMes())]);
					k++;
				}

				//Canal counter
				if(resumen.getCanalVenta().getId() == 1)
				{
					//Comprobante Boletas
					if(resumen.getTipoComprobante().getId() == 7){
						listRecord.set(0, resumen.getTotal());
						listRecord.set(1, resumen.getCantidad().doubleValue());
					}
					//Comprobantefacturas
					else if(resumen.getTipoComprobante().getId() == 2)
					{
						listRecord.set(2, resumen.getTotal());
						listRecord.set(3, resumen.getCantidad().doubleValue());
					}
					//Nota Credito
					else if(resumen.getTipoComprobante().getId() == 8)
					{
						listRecord.set(4, resumen.getTotal());
						listRecord.set(5, resumen.getCantidad().doubleValue());
					}
				}
				//Canal Web
				else if(resumen.getCanalVenta().getId() == 2)
				{
					listRecord.set(10, resumen.getTotal());
					listRecord.set(11, resumen.getCantidad().doubleValue());
				}

				if(lstResumen.size()-i == 1)
				{
					listarResumenVentas(listRecord, listCabeceraRecord);
					listarTotalesResumenVentas(listRecord);
				}
			}
			else
			{
				//reiniciamos la iteracion
				resumen0=null;
				resumen0 = lstResumen.get(i);
				i--;
				k=0;

				listarResumenVentas(listRecord, listCabeceraRecord);

				//Limpiar los Arrays
				for(j=0; j<13; j++){
					listRecord.set(j, 0.0);
				}
				listCabeceraRecord.set(0, "");
				listCabeceraRecord.set(1, "");

			}

		}
	}

	public void listarTotalesResumenVentas(List<Double> listRecord){


		String styleDeposito = "font-size:11px !important; text-align: right; font-weight: bold;color:black";
		String styleEmpresa = "font-size:11px !important; text-align: right; font-weight: bold;color:red";
		String styleGeneral = "font-size:11px !important; text-align: right; font-weight: bold;color:blue";

		Listitem item = null;
		Listcell cell = null;
		Integer temp=0;

		//Insertar las listas cabecera y totales al listbox
		item = new Listitem();

		cell = new Listcell("TOTAL");
		cell.setStyle(styleDeposito);
		item.appendChild(cell);

		cell = new Listcell("");
		item.appendChild(cell);

		//Total Boletas
		if(listRecordTotales.get(0)==0){
			cell = new Listcell("0.00");
			cell.setStyle(styleDeposito);
			item.appendChild(cell);
			//Cantidad boletas
			cell = new Listcell("0");
			cell.setStyle(styleDeposito);
			item.appendChild(cell);
		}else{
			cell = new Listcell(listRecordTotales.get(0).toString());
			cell.setStyle(styleDeposito);
			item.appendChild(cell);
			//Cantidad boletas
			temp=listRecordTotales.get(1).intValue();
			cell = new Listcell(temp.toString());
			cell.setStyle(styleDeposito);
			item.appendChild(cell);
		}
		//Total facturas
		if(listRecordTotales.get(2)==0){
			cell = new Listcell("0.00");
			cell.setStyle(styleDeposito);
			item.appendChild(cell);
			//Cantidad boletas
			cell = new Listcell("0");
			cell.setStyle(styleDeposito);
			item.appendChild(cell);
		}else{
			cell = new Listcell(listRecordTotales.get(2).toString());
			cell.setStyle(styleDeposito);
			item.appendChild(cell);
			//Cantidad facturas
			temp=listRecordTotales.get(2).intValue();
			cell = new Listcell(temp.toString());
			cell.setStyle(styleDeposito);
			item.appendChild(cell);
		}
		//Total Notas credito
		if(listRecordTotales.get(4)==0){
			cell = new Listcell("0.00");
			cell.setStyle(styleDeposito);
			item.appendChild(cell);
			//Cantidad boletas
			cell = new Listcell("0");
			cell.setStyle(styleDeposito);
			item.appendChild(cell);
		}else{
			cell = new Listcell(listRecordTotales.get(4).toString());
			cell.setStyle(styleDeposito);
			item.appendChild(cell);
			//Cantidad NOtas credito
			temp=listRecordTotales.get(5).intValue();
			cell = new Listcell(temp.toString());
			cell.setStyle(styleDeposito);
			item.appendChild(cell);
		}
		//Total deposito
		cell = new Listcell(listRecordTotales.get(6).toString());
		cell.setStyle(styleDeposito);
		item.appendChild(cell);
		//Total GRT
		if(listRecordTotales.get(7)==0){
			cell = new Listcell("0.00");
			cell.setStyle(styleDeposito);
			item.appendChild(cell);
			//Cantidad boletas
			cell = new Listcell("0");
			cell.setStyle(styleDeposito);
			item.appendChild(cell);
		}else{
			cell = new Listcell(listRecordTotales.get(7).toString());
			cell.setStyle(styleDeposito);
			item.appendChild(cell);
			//Cantidad GRT
			temp=listRecordTotales.get(8).intValue();
			cell = new Listcell(temp.toString());
			cell.setStyle(styleDeposito);
			item.appendChild(cell);
		}
		//Total Transmar
		cell = new Listcell(listRecordTotales.get(9).toString());
		cell.setStyle(styleEmpresa);
		item.appendChild(cell);
		//Total Web
		if(listRecordTotales.get(10)==0){
			cell = new Listcell("0.00");
			cell.setStyle(styleDeposito);
			item.appendChild(cell);
			//Cantidad boletas
			cell = new Listcell("0");
			cell.setStyle(styleDeposito);
			item.appendChild(cell);
		}else{
			cell = new Listcell(listRecordTotales.get(10).toString());
			cell.setStyle(styleDeposito);
			item.appendChild(cell);
			//Cantidad web
			temp=listRecordTotales.get(11).intValue();
			cell = new Listcell(temp.toString());
			cell.setStyle(styleDeposito);
			item.appendChild(cell);
		}
		//Total General
		cell = new Listcell(listRecordTotales.get(12).toString());
		cell.setStyle(styleGeneral);
		item.appendChild(cell);

		lsbxVentas.appendChild(item);


	}

	public void listarResumenVentas(List<Double> listRecord, List<String> listCabeceraRecord){

		Listitem item = null;
		Listcell cell = null;

		//Calculamos los totales
		listRecord.set(6, listRecord.get(0)+listRecord.get(2)-listRecord.get(4));
		listRecord.set(9, listRecord.get(6)+listRecord.get(7));
		listRecord.set(12, listRecord.get(9)+listRecord.get(10));

		listRecordTotales.set(0, listRecordTotales.get(0)+listRecord.get(0));
		listRecordTotales.set(1, listRecordTotales.get(1)+listRecord.get(1));
		listRecordTotales.set(2, listRecordTotales.get(2)+listRecord.get(2));
		listRecordTotales.set(3, listRecordTotales.get(3)+listRecord.get(3));
		listRecordTotales.set(4, listRecordTotales.get(4)+listRecord.get(4));
		listRecordTotales.set(5, listRecordTotales.get(5)+listRecord.get(5));
		listRecordTotales.set(6, listRecordTotales.get(6)+listRecord.get(6));
		listRecordTotales.set(7, listRecordTotales.get(7)+listRecord.get(7));
		listRecordTotales.set(8, listRecordTotales.get(8)+listRecord.get(8));
		listRecordTotales.set(9, listRecordTotales.get(9)+listRecord.get(9));
		listRecordTotales.set(10, listRecordTotales.get(10)+listRecord.get(10));
		listRecordTotales.set(11, listRecordTotales.get(11)+listRecord.get(11));
		listRecordTotales.set(12, listRecordTotales.get(12)+listRecord.get(12));


		String styleDeposito = "font-size:11px !important; text-align: right; font-weight: bold;color:black";
		String styleEmpresa = "font-size:11px !important; text-align: right; font-weight: bold;color:red";
		String styleGeneral = "font-size:11px !important; text-align: right; font-weight: bold;color:blue";

		Integer temp=0;

		//Insertar las listas cabecera y totales al listbox
		item = new Listitem();

		cell = new Listcell(listCabeceraRecord.get(0));
		item.appendChild(cell);

		cell = new Listcell(listCabeceraRecord.get(1));
		item.appendChild(cell);

		//Total Boletas
		if(listRecord.get(0)==0){
			cell = new Listcell("0.00");
			item.appendChild(cell);
			//Cantidad boletas
			cell = new Listcell("0");
			item.appendChild(cell);
		}else{
			cell = new Listcell(listRecord.get(0).toString());
			item.appendChild(cell);
			//Cantidad boletas
			temp = listRecord.get(1).intValue();
			cell = new Listcell(temp.toString());
			item.appendChild(cell);
		}
		//Total facturas
		if(listRecord.get(2)==0){
			cell = new Listcell("0.00");
			item.appendChild(cell);
			//Cantidad boletas
			cell = new Listcell("0");
			item.appendChild(cell);
		}else{
			cell = new Listcell(listRecord.get(2).toString());
			item.appendChild(cell);
			//Cantidad facturas
			temp = listRecord.get(3).intValue();
			cell = new Listcell(temp.toString());
			item.appendChild(cell);
		}
		//Total Notas credito
		if(listRecord.get(4)==0){
			cell = new Listcell("0.00");
			item.appendChild(cell);
			//Cantidad boletas
			cell = new Listcell("0");
			item.appendChild(cell);
		}else{
			cell = new Listcell(listRecord.get(4).toString());
			item.appendChild(cell);
			//Cantidad NOtas credito
			temp = listRecord.get(5).intValue();
			cell = new Listcell(temp.toString());
			item.appendChild(cell);
		}
		//Total deposito
		cell = new Listcell(listRecord.get(6).toString());
		cell.setStyle(styleDeposito);
		item.appendChild(cell);
		//Total GRT
		if(listRecord.get(7)==0){
			cell = new Listcell("0.00");
			item.appendChild(cell);
			//Cantidad boletas
			cell = new Listcell("0");
			item.appendChild(cell);
		}else{
			cell = new Listcell(listRecord.get(7).toString());
			item.appendChild(cell);
			//Cantidad GRT
			temp = listRecord.get(8).intValue();
			cell = new Listcell(temp.toString());
			item.appendChild(cell);
		}
		//Total Transmar
		cell = new Listcell(listRecord.get(9).toString());
		cell.setStyle(styleEmpresa);
		item.appendChild(cell);
		//Total Web
		if(listRecord.get(10)==0){
			cell = new Listcell("0.00");
			item.appendChild(cell);
			//Cantidad boletas
			cell = new Listcell("0");
			item.appendChild(cell);
		}else{
			cell = new Listcell(listRecord.get(10).toString());
			item.appendChild(cell);
			//Cantidad web
			temp = listRecord.get(11).intValue();
			cell = new Listcell(temp.toString());
			item.appendChild(cell);
		}
		//Total General
		cell = new Listcell(listRecord.get(12).toString());
		cell.setStyle(styleGeneral);
		item.appendChild(cell);

		lsbxVentas.appendChild(item);
	}

	/**
	 * Exporta los resultados en formato .xls
	 */
	public void onClick_btnExportar(){
		try {

			if(lsbxVentas.getItemCount()>0)
				Util.exportarExcel(lsbxVentas, "ReporteGralVentas_");

		} catch (Exception e) {
			e.printStackTrace();
			DlgMessage.error(e.getMessage());
		}
	}



	public void limpiarArrays(){

		for(int i=0; i<13; i++){
			listRecordTotales.set(i, 0.0);
		}
		listCabeceraRecordTotales.set(0, "");
		listCabeceraRecordTotales.set(1, "");
	}


	private void inicializarControles(boolean estado){
//		Util.limpiarListbox(lsbxRutas);
		cmbAgencias.setSelectedIndex(0);

/*
		String fecha = Util.DatetoString(new Date(), "yyyyMMdd");
		dtbxFecInicio.setConstraint("after "+fecha);
		dtbxFecFinal.setConstraint("after "+fecha);
		dtbxFecInicio.setValue(null);
	dtbxFecFinal.setValue(null);
*/

	}
	
	public void actualizarEncomiendas() throws Exception {

		String fechaDesde = Constantes.FORMAT_DATE.format(dtbxFecInicioBus.getValue());
		String fechaHasta = Constantes.FORMAT_DATE.format(dtbxFecFinBus.getValue());
		
//		traer las equivalencias de agencias entre encomiendas y pasajes
		Map<String, EntidadEncomiendaPasajes> mapEntidadEncomiendaPasaje = (TreeMap<String, EntidadEncomiendaPasajes>)ServiceLocator.getVentaPasajesManager().buscarEquivalenciaEntidades(1);
		if (mapEntidadEncomiendaPasaje.size() == 0) {
			
			return;
		}
			

		
//		Obtener la ultima fecha transferida de las ventas
		
//		recuperar las ventas de encomiendas

		List<ResumenVentas> lstEncomiendas = ServiceLocator.getTranscarWebManager().buscarResumenVentas(fechaDesde, fechaHasta);
		if(lstEncomiendas.size() <= 0){
			DlgMessage.information("No se encontró infotrmacion para la información brindada.");
			dtbxFecFinBus.focus();
			return;
		}		
		ArrayList<ResumenVentas> lstEncomiendasCambiadas = new ArrayList<ResumenVentas>();
		
//		cambiar los ids de agencia en la data de encomiendas por la del vyrbus
		String key="";
		EntidadEncomiendaPasajes entidadEncomiendaPasajes = null;
		for (ResumenVentas resumenVentas : lstEncomiendas) {
			key = resumenVentas.getAgencia().getId()+"-"+TIPO_ENTIDAD_AGENCIA;
			entidadEncomiendaPasajes = mapEntidadEncomiendaPasaje.get(key); 
			if(entidadEncomiendaPasajes != null) {
				Agencia agencia = resumenVentas.getAgencia();
				agencia.setId(entidadEncomiendaPasajes.getIdAgenciaPasajes());
				resumenVentas.setAgencia(agencia);
				
//				Cambiar el tipo de comprobante de GRT de encomiendas por el utilizado en pasajes
//				Ahora se trae en la consulta desde el PostgreSQl

				lstEncomiendasCambiadas.add(resumenVentas);
			}
			
		}
		
//		Insertar la nueva lista cambiada al resumen
//		modificar la tabla resumen para que tenga un ID y trigger para la insercion/modificacion
//		Hacer el mapa para el hibernate
		
		
		
	}

}
