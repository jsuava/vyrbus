/**
 * Proyecto		: SISVYR
 * Sistema		: Sistema de Ventas y Reservas
 * Descripci�n	:
 * Autor		: Jos� Abanto
 * Fecha		: 23 may. 2021
 * Hora			: 20:38:25
 */
package pe.itsb.vyrbus.view.ctrl;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Session;
import org.zkoss.zul.Button;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.Label;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Radio;
import org.zkoss.zul.Tab;

import pe.itsb.vyrbus.model.bean.Agencia;
import pe.itsb.vyrbus.model.bean.EntidadEncomiendaPasajes;
import pe.itsb.vyrbus.model.bean.HistoricoResumenVentas;
import pe.itsb.vyrbus.model.bean.Usuario;
import pe.itsb.vyrbus.service.locator.ServiceLocator;
import pe.itsb.vyrbus.service.mappers.ResumenVentas;
import pe.itsb.vyrbus.service.util.Constantes;
import pe.itsb.vyrbus.service.util.Util;
import pe.itsb.vyrbus.service.util.UtilData;
import pe.itsb.vyrbus.service.util.UtilFlag;
import pe.itsb.vyrbus.view.ui.DlgMessage;
import pe.itsb.vyrbus.view.ui.WndBase;

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
	private Button btnActualizar;

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
	Integer mesIni, mesFin;

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
		btnActualizar = (Button)this.getFellow("btnActualizar");

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

		if(getRol().getId().intValue()!=Constantes.ID_ROL_SUPER_USUARIO) {
			btnActualizar.setVisible(false);
		}

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

		if(idAgencia >= 0){
			if(rdMes.isChecked()){
				tipoConsulta=3;
				//Reacomodando las fechas
				String anio, mes, dia;
				anio = fechaHasta.substring(6);
				mes = fechaHasta.substring(3, 5);
				dia = fechaHasta.substring(0, 2);
				fechaDesde = "01" + fechaDesde.substring(2);
				LocalDate fecInicial = LocalDate.of(Integer.parseInt(anio), Integer.parseInt(mes), Integer.parseInt(dia));
				mesIni = Integer.parseInt(mes);
				LocalDate fechaFinal = fecInicial.withDayOfMonth(fecInicial.lengthOfMonth());
				// Getting system timezone
				ZoneId systemTimeZone = ZoneId.systemDefault();

				// converting LocalDateTime to ZonedDateTime with the system timezone
				ZonedDateTime zonedDateTime = fechaFinal.atStartOfDay(systemTimeZone);
				Date utilDate = Date.from(zonedDateTime.toInstant());
				fechaHasta = Constantes.FORMAT_DATE.format(utilDate);
				mesFin = Integer.parseInt(fechaHasta.substring(3, 5));
			}
			else if(rdAnual.isChecked()) 
				tipoConsulta=1;
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
			DlgMessage.information("No se encontraron resultados para la informaci\\u00F3n brindada.");
			cmbAgencias.focus();
			return;
		}
	}

	//Resumen de ventas para un rango de fechas
	public void ensamblarResumenVentas(List<ResumenVentas> lstResumen) {

		List<Double> listRecord = new ArrayList<>();
		List<String> listCabeceraRecord = new ArrayList<>();

		int i=0;

		Util.limpiarListbox(lsbxVentas);

		int j, k;

		ResumenVentas resumen0 = lstResumen.get(i);

		for(j=0; j<9; j++){
			listRecord.add(0.0);
		}
		listCabeceraRecord.add("");
		listCabeceraRecord.add("");
		k=0;

		for(i = 0; i<lstResumen.size(); i++) {
			ResumenVentas resumen = lstResumen.get(i);

			if( resumen0.getFechaVenta().equals(resumen.getFechaVenta())
				&& resumen0.getAgencia().getId().equals(resumen.getAgencia().getId()) )	{
				//Agencia
				if(k==0){
					listCabeceraRecord.set(0, resumen.getAgencia().getDenominacion());
					listCabeceraRecord.set(1, resumen.getFechaVenta());
					k++;
				}

				//Canal counter
				if(resumen.getCanalVenta().getId() == 1){
					//COMPROBANTES DE PASAJEROS
					if(resumen.getRubro() == 1) {
						//Comprobante Boletas
						if(resumen.getTipoComprobante().getId() == 7){
							listRecord.set(0, resumen.getTotal());
						}
						//Comprobantefacturas
						else if(resumen.getTipoComprobante().getId() == 2){
							listRecord.set(1, resumen.getTotal());
						}
						//Nota Credito
						else if(resumen.getTipoComprobante().getId() == 8){
							listRecord.set(2, resumen.getTotal());
						}
					//COMPROBANTES DE ENCOMIENDAS
					}else {
						//Comprobante Boletas
						if(resumen.getTipoComprobante().getId() == 7){
							listRecord.set(3, resumen.getTotal());
						}
						//Comprobantefacturas
						else if(resumen.getTipoComprobante().getId() == 2){
							listRecord.set(4, resumen.getTotal());
						}
						//Nota Credito
						else if(resumen.getTipoComprobante().getId() == 8){
							listRecord.set(5, resumen.getTotal());
						}
						//GRT
						else if(resumen.getTipoComprobante().getId() == 10){
							listRecord.set(6, resumen.getTotal());
						}
					}
				}
				//Canal Web
				else if(resumen.getCanalVenta().getId() == 2)
				{
					if(resumen.getRubro() == 1) {
						//Comprobante Boletas
						if(resumen.getTipoComprobante().getId() == 7){
							listRecord.set(0, resumen.getTotal());
						}
						//Comprobantefacturas
						else if(resumen.getTipoComprobante().getId() == 2){
							listRecord.set(1, resumen.getTotal());
						}
						//Nota Credito
						else if(resumen.getTipoComprobante().getId() == 8){
							listRecord.set(2, resumen.getTotal());
						}
					//COMPROBANTES DE ENCOMIENDAS
					}
				}

				if(lstResumen.size()-i == 1){
					listarResumenVentas(listRecord, listCabeceraRecord);
					listarTotalesResumenVentas(listRecord);
				}
			}
			else{
				//reiniciamos la iteracion
				resumen0=null;
				resumen0 = lstResumen.get(i);
				i--;
				k=0;

				listarResumenVentas(listRecord, listCabeceraRecord);

				//Limpiar los Arrays
				for(j=0; j<9; j++){
					listRecord.set(j, 0.0);
				}
				listCabeceraRecord.set(0, "");
				listCabeceraRecord.set(1, "");

			}//Fin de la misma oficina y fecha
		}//Fin del for

	}

	//Resumen de ventas por mes
	public void ensamblarResumenVentasByMonth(List<ResumenVentas> lstResumen){

		List<Double> listRecord = new ArrayList<>();
		List<String> listCabeceraRecord = new ArrayList<>();

		int i=0;


		Util.limpiarListbox(lsbxVentas);

		int j, k;
		//for(ResumenVentas resumen : lstResumen) {
		ResumenVentas resumen0 = lstResumen.get(i);

		for(j=0; j<9; j++){
			listRecord.add(0.0);
		}
		listCabeceraRecord.add("");
		listCabeceraRecord.add("");
		k=0;

		for(i = 0; i<lstResumen.size(); i++){
			ResumenVentas resumen = lstResumen.get(i);

			if( resumen0.getMes().equals(resumen.getMes())
				&& resumen0.getAgencia().getId().equals(resumen.getAgencia().getId())){
				//Agencia
				if(k==0){
					listCabeceraRecord.set(0, resumen.getAgencia().getDenominacion());
					listCabeceraRecord.set(1, resumen.getAnio()+"-"+arMeses[Integer.parseInt(resumen.getMes())]);
					k++;
				}

				//Canal counter
				if(resumen.getCanalVenta().getId() == 1){

					if(resumen.getRubro()==1) {
						//Comprobante Boletas PASAJES
						if(resumen.getTipoComprobante().getId() == 7){
							listRecord.set(0, resumen.getTotal());
						}
						//Comprobante facturas PASAJES
						else if(resumen.getTipoComprobante().getId() == 2){
							listRecord.set(1, resumen.getTotal());
						}
						//Nota Credito PASAJES
						else if(resumen.getTipoComprobante().getId() == 8){
							listRecord.set(2, resumen.getTotal());
						}
					}
					else {
						//Comprobante Boletas ENCOMIENDAS
						if(resumen.getTipoComprobante().getId() == 7){
							listRecord.set(3, resumen.getTotal());
						}
						//Comprobante facturas ENCOMIENDAS
						else if(resumen.getTipoComprobante().getId() == 2){
							listRecord.set(4, resumen.getTotal());
						}
						//Nota Credito ENCOMIENDAS
						else if(resumen.getTipoComprobante().getId() == 8){
							listRecord.set(5, resumen.getTotal());
						}
						//GRT
						else if(resumen.getTipoComprobante().getId() == 10){
							listRecord.set(6, resumen.getTotal());
						}
					}

				}
				//Canal Web
				else if(resumen.getCanalVenta().getId() >= 2){
					if(resumen.getRubro()==1) {
						//Comprobante Boletas PASAJES
						if(resumen.getTipoComprobante().getId() == 7){
							listRecord.set(0, resumen.getTotal());
						}
						//Comprobante facturas PASAJES
						else if(resumen.getTipoComprobante().getId() == 2){
							listRecord.set(1, resumen.getTotal());
						}
						//Nota Credito PASAJES
						else if(resumen.getTipoComprobante().getId() == 8){
							listRecord.set(2, resumen.getTotal());
						}
					}
				}

				if(lstResumen.size()-i == 1){
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
				for(j=0; j<9; j++){
					listRecord.set(j, 0.0);
				}
				listCabeceraRecord.set(0, "");
				listCabeceraRecord.set(1, "");

			}

		}//Fin del for
	}

	public void listarTotalesResumenVentas(List<Double> listRecord){


		String styleDeposito = "font-size:11px !important; text-align: right; font-weight: bold;color:black";
		String styleEmpresa = "font-size:11px !important; text-align: right; font-weight: bold;color:red";
		String styleGeneral = "font-size:11px !important; text-align: right; font-weight: bold;color:blue";

		Listitem item = null;
		Listcell cell = null;
		int temp=0;

		//Insertar las listas cabecera y totales al listbox
		item = new Listitem();

		cell = new Listcell("TOTAL");
		cell.setStyle(styleDeposito);
		item.appendChild(cell);

		cell = new Listcell("");
		item.appendChild(cell);

		//Total Boletas PASAJES
		cell = (listRecordTotales.get(0)==0 ? new Listcell("0.00") : new Listcell(Util.toNumberFormat(listRecordTotales.get(0), 2)));
		cell.setStyle(styleDeposito);
		item.appendChild(cell);
		//Total facturas PASAJES
		cell = (listRecordTotales.get(1)==1 ? new Listcell("0.00") : new Listcell(Util.toNumberFormat(listRecordTotales.get(1), 2)));
		cell.setStyle(styleDeposito);
		item.appendChild(cell);
		//TOTAL NC PASAJES
		cell = (listRecordTotales.get(2)==0 ? new Listcell("0.00") : new Listcell(Util.toNumberFormat(listRecordTotales.get(2), 2)));
		cell.setStyle(styleDeposito);
		item.appendChild(cell);
		//TOTAL BOLETAS ENCOMIENDAS
		cell = (listRecordTotales.get(3)==0 ? new Listcell("0.00") : new Listcell(Util.toNumberFormat(listRecordTotales.get(3), 2)));
		cell.setStyle(styleDeposito);
		item.appendChild(cell);
		//TOTAL FACTURAS ENCOMIENDAS
		cell = (listRecordTotales.get(4)==0 ? new Listcell("0.00") : new Listcell(Util.toNumberFormat(listRecordTotales.get(4), 2)));
		cell.setStyle(styleDeposito);
		item.appendChild(cell);
		//TOTAL NC ENCOMIENDAS
		cell = (listRecordTotales.get(5)==0 ? new Listcell("0.00") : new Listcell(Util.toNumberFormat(listRecordTotales.get(5), 2)));
		cell.setStyle(styleDeposito);
		item.appendChild(cell);
		//TOTAL GRT
		cell = (listRecordTotales.get(6)==0 ? new Listcell("0.00") : new Listcell(Util.toNumberFormat(listRecordTotales.get(6), 2)));
		cell.setStyle(styleDeposito);
		item.appendChild(cell);
		//VENTA TOTAL
		cell = (listRecordTotales.get(7)==0 ? new Listcell("0.00") : new Listcell(Util.toNumberFormat(listRecordTotales.get(7), 2)));
		cell.setStyle(styleDeposito);
		item.appendChild(cell);

		item.appendChild(cell);
		//VENTA CONTADO
		cell = (listRecordTotales.get(8)==0 ? new Listcell("0.00") : new Listcell(Util.toNumberFormat(listRecordTotales.get(8), 2)));
		cell.setStyle(styleDeposito);
		item.appendChild(cell);


		lsbxVentas.appendChild(item);
	}

	public void listarResumenVentas(List<Double> listRecord, List<String> listCabeceraRecord){

		Listitem item = null;
		Listcell cell = null;

		//Calculamos los totales
		//Venta Total
		listRecord.set(7, listRecord.get(0)+listRecord.get(1)-listRecord.get(2)+listRecord.get(3)+listRecord.get(4)-listRecord.get(5)+listRecord.get(6));
		//Venta Contado
		listRecord.set(8, listRecord.get(0)+listRecord.get(1)-listRecord.get(2)+listRecord.get(3)+listRecord.get(4)-listRecord.get(5));
//		listRecord.set(12, listRecord.get(9)+listRecord.get(10));

		listRecordTotales.set(0, listRecordTotales.get(0)+listRecord.get(0));
		listRecordTotales.set(1, listRecordTotales.get(1)+listRecord.get(1));
		listRecordTotales.set(2, listRecordTotales.get(2)+listRecord.get(2));
		listRecordTotales.set(3, listRecordTotales.get(3)+listRecord.get(3));
		listRecordTotales.set(4, listRecordTotales.get(4)+listRecord.get(4));
		listRecordTotales.set(5, listRecordTotales.get(5)+listRecord.get(5));
		listRecordTotales.set(6, listRecordTotales.get(6)+listRecord.get(6));
		listRecordTotales.set(7, listRecordTotales.get(7)+listRecord.get(7));
		listRecordTotales.set(8, listRecordTotales.get(8)+listRecord.get(8));

		String styleDeposito = "font-size:11px !important; text-align: right; font-weight: bold;color:black";
		String styleEmpresa = "font-size:11px !important; text-align: right; font-weight: bold;color:red";
		String styleGeneral = "font-size:11px !important; text-align: right; font-weight: bold;color:blue";

		int temp=0;

		//Insertar las listas cabecera y totales al listbox
		item = new Listitem();

		cell = new Listcell(listCabeceraRecord.get(0));
		item.appendChild(cell);

		cell = new Listcell(listCabeceraRecord.get(1));
		item.appendChild(cell);

		//Total Boletas PASAJES
		cell = (listRecord.get(0)==0 ? new Listcell("0.00") : new Listcell(Util.toNumberFormat(listRecord.get(0), 2)));
		item.appendChild(cell);
		//Total facturas PASAJES
		cell = (listRecord.get(1)==1 ? new Listcell("0.00") : new Listcell(Util.toNumberFormat(listRecord.get(1), 2)));
		item.appendChild(cell);
		//TOTAL NC PASAJES
		cell = (listRecord.get(2)==0 ? new Listcell("0.00") : new Listcell(Util.toNumberFormat(listRecord.get(2), 2)));
		item.appendChild(cell);
		//TOTAL BOLETAS ENCOMIENDAS
		cell = (listRecord.get(3)==0 ? new Listcell("0.00") : new Listcell(Util.toNumberFormat(listRecord.get(3), 2)));
		item.appendChild(cell);
		//TOTAL FACTURAS ENCOMIENDAS
		cell = (listRecord.get(4)==0 ? new Listcell("0.00") : new Listcell(Util.toNumberFormat(listRecord.get(4), 2)));
		item.appendChild(cell);
		//TOTAL NC ENCOMIENDAS
		cell = (listRecord.get(5)==0 ? new Listcell("0.00") : new Listcell(Util.toNumberFormat(listRecord.get(5), 2)));
		item.appendChild(cell);
		//TOTAL GRT
		cell = (listRecord.get(6)==0 ? new Listcell("0.00") : new Listcell(Util.toNumberFormat(listRecord.get(6), 2)));
		item.appendChild(cell);
		//VENTA TOTAL
		cell = (listRecord.get(7)==0 ? new Listcell("0.00") : new Listcell(Util.toNumberFormat(listRecord.get(7), 2)));
		cell.setStyle(styleGeneral);
		item.appendChild(cell);

		item.appendChild(cell);
		//VENTA CONTADO
		cell = (listRecord.get(8)==0 ? new Listcell("0.00") : new Listcell(Util.toNumberFormat(listRecord.get(8), 2)));
		item.appendChild(cell);

		lsbxVentas.appendChild(item);
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
		Map<String, EntidadEncomiendaPasajes> mapEntidadEncomiendaPasaje = ServiceLocator.getVentaPasajesManager().buscarEquivalenciaEntidades(1);
		if (mapEntidadEncomiendaPasaje.size() == 0) {
			DlgMessage.information("No se encontr� informacion Sobre equivalencias entre entidades.");
			return;
		}


//		Obtener la ultima fecha transferida de las ventas
		//select to_char(max(rv.d_fecven+1), 'dd/MM/yyyy') fechaventa from vrhresven rv where rv.n_rubro=2;

//		recuperar las ventas de encomiendas
		List<ResumenVentas> lstEncomiendas = new ArrayList<ResumenVentas>();
		//Valida la conexión con transcar
		boolean isConnectionTranscar = UtilFlag.isConeccionTranscar();
		if(isConnectionTranscar) {
			lstEncomiendas = ServiceLocator.getTranscarWebManager().buscarResumenVentas(fechaDesde, fechaHasta);
			if(lstEncomiendas.size() <= 0){
				DlgMessage.information("No se encontró información para la información brindada.");
				dtbxFecFinBus.focus();
				return;
			}	
		}
		
		ArrayList<ResumenVentas> lstEncomiendasCambiadas = new ArrayList<>();

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
		HistoricoResumenVentas hrv = null;
		for (ResumenVentas resumenVentas : lstEncomiendasCambiadas) {
			hrv = new HistoricoResumenVentas();
			hrv.setRubro(resumenVentas.getRubro());
			hrv.setIdCanalVenta(resumenVentas.getCanalVenta().getId());
			hrv.setNombreCanal(resumenVentas.getCanalVenta().getDenominacion());
			hrv.setIdAgencia(resumenVentas.getAgencia().getId());
			hrv.setNombreAgencia(resumenVentas.getAgencia().getDenominacion());
			hrv.setIdTipoComprobante(resumenVentas.getTipoComprobante().getId());
			hrv.setComprobante(resumenVentas.getTipoComprobante().getDenominacion());
			hrv.setCantidad(resumenVentas.getCantidad());
			hrv.setTotal(resumenVentas.getTotal());
			hrv.setFechaVenta(resumenVentas.getFechaEmision());
			hrv.setAnio(resumenVentas.getAnio());
			hrv.setMes(resumenVentas.getMes());
			hrv.setDia(resumenVentas.getDia());
			hrv.setEstadoRegistro(Constantes.VALUE_ACTIVO);
			UtilData.auditarRegistro(hrv, getUsuario(), Executions.getCurrent());
			ServiceLocator.getHistoricoResumenVentasManager().guardar(hrv);

		}
//		modificar la tabla resumen para que tenga un ID y trigger para la insercion/modificacion
//		Hacer el mapa para el hibernate



	}

	/**
	 * Exporta los resultados en formato .xls
	 */
	public void onClick_btnExportar(){
		Session session = getDesktop().getSession();
		String fecha = Util.DatetoString(new Date(), Constantes.DATE_TIME_FORMAT);
		Integer idAgencia=0;
		Agencia agencia=new Agencia();

		if(cmbAgencias.getSelectedItem().getValue() instanceof Agencia) {
			agencia=((Agencia)cmbAgencias.getSelectedItem().getValue());
			idAgencia = agencia.getId();
		}

		HttpSession httpSession = (HttpSession)session.getNativeSession();
		httpSession.setAttribute("parcialPath",Constantes.DIRECTORY_EXCEL+"ReporteGeneralVentas.xls");
		httpSession.setAttribute("lbxVentas", lsbxVentas);
		httpSession.setAttribute("oficina", (idAgencia==0 ? "TODOS" : agencia.getDenominacion()));
		if(rdFecha.isChecked()) {
			httpSession.setAttribute("desde", Constantes.FORMAT_DATE.format(dtbxFecInicioBus.getValue()));
			httpSession.setAttribute("hasta", Constantes.FORMAT_DATE.format(dtbxFecFinBus.getValue()));
		}
		else if(rdMes.isChecked()) {
			httpSession.setAttribute("desde", arMeses[mesIni]);
			httpSession.setAttribute("hasta", arMeses[mesFin]);
		}

		httpSession.setAttribute("fechaEmision", fecha);
//		httpSession.setAttribute("rubro", (rdEncomiendas.isChecked() ? "ENCOMIENDAS" : rdPasajeros.isChecked() ?  "PASAJEROS" : "PASAJEROS / ENCOMIENDAS" ));
		Executions.sendRedirect("/exportXlsReporteGeneralVentas.htm");


//		try {
//
//			if(lsbxVentas.getItemCount()>0)
//				Util.exportarExcel(lsbxVentas, "ReporteGralVentas_");
//
//		} catch (Exception e) {
//			e.printStackTrace();
//			DlgMessage.error(e.getMessage());
//		}
	}


}
