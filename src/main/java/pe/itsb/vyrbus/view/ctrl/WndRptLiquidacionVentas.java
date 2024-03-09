/**
 * Proyecto		: SISVYR
 * Sistema		: Sistema de Ventas y Reservas
 * Descripci�n	:
 * Autor		: Jos� Abanto
 * Fecha		: 11 abr. 2022
 * Hora			: 14:56:10
 */
package pe.itsb.vyrbus.view.ctrl;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map.Entry;
import java.util.TreeMap;

import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zul.Checkbox;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.Hbox;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listfoot;
import org.zkoss.zul.Listfooter;
import org.zkoss.zul.Listheader;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Toolbarbutton;

import pe.itsb.vyrbus.model.bean.Agencia;
import pe.itsb.vyrbus.model.bean.Gasto;
import pe.itsb.vyrbus.model.bean.Liquidacion;
import pe.itsb.vyrbus.model.bean.TranscarUsuarioPersonal;
import pe.itsb.vyrbus.model.bean.Usuario;
import pe.itsb.vyrbus.model.bean.VentaPasaje;
import pe.itsb.vyrbus.service.locator.ServiceLocator;
import pe.itsb.vyrbus.service.util.Constantes;
import pe.itsb.vyrbus.service.util.CreateDocument;
import pe.itsb.vyrbus.service.util.Messages;
import pe.itsb.vyrbus.service.util.Util;
import pe.itsb.vyrbus.service.util.UtilData;
import pe.itsb.vyrbus.service.util.UtilFlag;
import pe.itsb.vyrbus.view.ui.DlgMessage;
import pe.itsb.vyrbus.view.ui.Events;
import pe.itsb.vyrbus.view.ui.WndBase;
import pe.itsb.vyrbus.view.ui.WndIFrame;

/**
 * @author abant
 *
 */
public class WndRptLiquidacionVentas extends WndBase{

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	private Datebox dtbxFechaInicio;
	private Combobox cmbAgencia;
	private Listbox ltbxLiquidacionVentas;
	private Checkbox chbxPorRangoFechas;
	private Datebox dtbxFechaFin;
	private Checkbox chbxDetallePorUsuario;
	private Listheader lthdrNombresUsuario;
	private Listheader lthdrUsuario;
	private Listheader lthdrEstadoLiqPasajes;
	private Listheader lthdrEstadoLiqCarga;

	private String fechaInicial = null;
	private String fechaFinal = null;
	private Integer agenciaId = null;
	private boolean isConnectionTranscar  = false;

	/* (non-Javadoc)
	 * @see pe.itsb.vyrbus.view.ui.WndBase#initComponents()
	 */
	@Override
	public void initComponents() {
		// TODO Auto-generated method stub
		super.initComponents();

		dtbxFechaInicio = (Datebox) this.getFellow("dtxFechaInicio");
		dtbxFechaFin = (Datebox) this.getFellow("dtbxFechaFin");
		cmbAgencia = (Combobox) this.getFellow("cmbAgencia");
		ltbxLiquidacionVentas = (Listbox) this.getFellow("ltbxLiquidacionVentas");
		chbxPorRangoFechas = (Checkbox) this.getFellow("chbxPorRangoFechas");
		dtbxFechaFin = (Datebox) this.getFellow("dtbxFechaFin");
		chbxDetallePorUsuario = (Checkbox) this.getFellow("chbxDetallePorUsuario");
		lthdrNombresUsuario = (Listheader) this.getFellow("lthdrNombresUsuario");
		lthdrUsuario = (Listheader) this.getFellow("lthdrUsuario");
		lthdrEstadoLiqPasajes = (Listheader) this.getFellow("lthdrEstadoLiqPasajes");
		lthdrEstadoLiqCarga = (Listheader) this.getFellow("lthdrEstadoLiqCarga");

		chbxPorRangoFechas.addEventListener(org.zkoss.zk.ui.event.Events.ON_CHECK, new EventListener<Event>() {
			@Override
			public void onEvent(Event event) throws Exception {
				// TODO Auto-generated method stub
				Util.limpiarCombobox(cmbAgencia);
				if(chbxPorRangoFechas.isChecked())
					UtilData.cargarDataCombo(cmbAgencia, Agencia.class, false);
				else
					UtilData.cargarDataCombo(cmbAgencia, Agencia.class, true);
				dtbxFechaFin.setDisabled(!chbxPorRangoFechas.isChecked());
				cleanResult(true);
			}
		});
		chbxDetallePorUsuario.addEventListener(org.zkoss.zk.ui.event.Events.ON_CHECK, new EventListener<Event>() {
			@Override
			public void onEvent(Event event) throws Exception {
				// TODO Auto-generated method stub
				Util.limpiarListbox(ltbxLiquidacionVentas);
				lthdrNombresUsuario.setVisible(chbxDetallePorUsuario.isChecked());
				lthdrUsuario.setVisible(chbxDetallePorUsuario.isChecked());
				lthdrEstadoLiqPasajes.setVisible(chbxDetallePorUsuario.isChecked());
				lthdrEstadoLiqCarga.setVisible(chbxDetallePorUsuario.isChecked());
			}
		});

	}


	/* (non-Javadoc)
	 * @see pe.itsb.vyrbus.view.ui.WndBase#onCreate()
	 */
	@Override
	public void onCreate() throws Exception {
		// TODO Auto-generated method stub
		super.onCreate();

		//Valida la conexión con transcar
		isConnectionTranscar = UtilFlag.isConeccionTranscar();
		
		dtbxFechaInicio.setValue(new Date());
		dtbxFechaFin.setValue(new Date());
		UtilData.cargarDataCombo(cmbAgencia, Agencia.class, true);

		dtbxFechaFin.setDisabled(true);
		lthdrNombresUsuario.setVisible(false);
		lthdrUsuario.setVisible(false);
		lthdrEstadoLiqPasajes.setVisible(false);
		lthdrEstadoLiqCarga.setVisible(false);
	}

	/**
	 * Evento Click del bot�n buscar
	 */
	public void onClick_btnBuscar() {
		try {
			Util.limpiarListbox(ltbxLiquidacionVentas);

//			if(chbxPorRangoFechas.isChecked() && !(cmbAgencia.getSelectedItem().getValue() instanceof Agencia)) {
//				DlgMessage.information(Messages.getString("WndEspecieValorada.information.Agencia"), cmbAgencia);
//				return;
//			}



			fechaInicial = Constantes.FORMAT_DATE.format(dtbxFechaInicio.getValue());
			fechaFinal = fechaInicial;
			agenciaId = null;
			if(cmbAgencia.getSelectedItem().getValue() instanceof Agencia)
				agenciaId =  ((Agencia)cmbAgencia.getSelectedItem().getValue()).getId();
			if(!dtbxFechaFin.isDisabled())
				fechaFinal = Constantes.FORMAT_DATE.format(dtbxFechaFin.getValue());

			//Busca las liquidaciones de PASAJES.
			List<Liquidacion> resultPasajes = ServiceLocator.getLiquidacionManager().buscarLiquidacion(fechaInicial, fechaFinal, agenciaId, null, null);

			//Busca las liquidaciones de CARGA
			TreeMap<String, Liquidacion> resultCarga = null;
			if(isConnectionTranscar)
				resultCarga = ServiceLocator.getTranscarWebManager().buscarLiquidacionCounter(fechaInicial, fechaFinal, agenciaId, null);

			//Carga el listado de liquidaciones
			cargarListaLiquidaciones(resultPasajes, resultCarga);


		} catch (Exception e) {
			e.printStackTrace();
			DlgMessage.error(e.getMessage());
		}
	}

	/**
	 * Evento Click del bot�n Detalle ventas
	 */
	public void viewDetalleVentas(Liquidacion liquidacion) {
		try {

			String nameFile = CreateDocument.creaRptLiquidacionByDetalleVentas(liquidacion);
			/*Carga el iframe*/
			String src=Constantes.URL_FORMATOS_LIQUIDACION + Constantes.CLAVE_PAHT+ nameFile;
			File file = new File(Constantes.DIRECTORY_LIQUIDACION + Constantes.CLAVE_PAHT +nameFile);
			final WndIFrame iFrame = new WndIFrame();
			iFrame.setSrc(src);
			iFrame.setFile(file);
			iFrame.setWidth("810");
			iFrame.setheight("600");
			iFrame.loadiframe();

			appendChild(iFrame);
			iFrame.setMode("modal");

		} catch (Exception e) {
			e.printStackTrace();
			DlgMessage.error(e.getMessage());
		}
	}

	/**
	 * Carga la vista preliminar del reporte Control Especies Valoradas
	 * @param liquidacionPasajes
	 * @param liquidacionesCarga
	 */
	private void viewCrtolEspeciesValoradas(Liquidacion liquidacion) {
		try {

			String nameFile = CreateDocument.creaRptLiquidacionByEspecieValorada(liquidacion, false);
			/*Carga el iframe*/
			String src=Constantes.URL_FORMATOS_LIQUIDACION +Constantes.CLAVE_PAHT+ nameFile;
			File file = new File(Constantes.DIRECTORY_LIQUIDACION + Constantes.CLAVE_PAHT +nameFile);
			final WndIFrame iFrame = new WndIFrame();
			iFrame.setSrc(src);
			iFrame.setFile(file);
			iFrame.setWidth("810");
			iFrame.setheight("600");
			iFrame.loadiframe();

			appendChild(iFrame);
			iFrame.setMode("modal");

		} catch (Exception e) {
			e.printStackTrace();
			DlgMessage.error(e.getMessage());
		}
	}

	public void onClick_btnExportar() {
		try {
			Util.exportarExcel(ltbxLiquidacionVentas, "");

		} catch (Exception e) {
			e.printStackTrace();
			DlgMessage.error(e.getMessage());
		}
	}

	/**
	 * Evento Click del bot�n Resumen de Saldos
	 */
	public void onClick_btnResumenSaldos() {
		try {
			if (!(cmbAgencia.getSelectedItem().getValue() instanceof Agencia)) {
				DlgMessage.information(Messages.getString("Generales.information.noSeleccionoAgencia"),cmbAgencia);
				return;
			}else if(ltbxLiquidacionVentas.getItemCount()>0) {
				Agencia agencia = (Agencia) cmbAgencia.getSelectedItem().getValue();
				String fechaLiquidacion = Constantes.FORMAT_DATE.format(dtbxFechaInicio.getValue());

				String nameFile = CreateDocument.creaRptLiquidacionByResumenSaldos(ltbxLiquidacionVentas, agencia, fechaLiquidacion);
				/*Carga el iframe*/
				String src=Constantes.URL_FORMATOS_LIQUIDACION +Constantes.CLAVE_PAHT+ nameFile;
				final WndIFrame iFrame = new WndIFrame();
				iFrame.setSrc(src);
				iFrame.setWidth("810");
				iFrame.setheight("600");
				iFrame.loadiframe();

				appendChild(iFrame);
				iFrame.setMode("modal");
			}

		} catch (Exception e) {
			e.printStackTrace();
			DlgMessage.error(e.getMessage());
		}
	}

	private Double buscarEfectivoLiquidacion(Liquidacion liquidacion)throws Exception{
		List<VentaPasaje> listDetalleVentas = new ArrayList<>();
		List<Gasto> resultGasto = new ArrayList<>();
		List<Gasto> lstIngresos = new ArrayList<>();
		String fecha = Constantes.FORMAT_DATE.format(liquidacion.getFechaLiquidacion());

		if(liquidacion!=null && liquidacion.getId()!=null) {
			Integer usuarioId = (chbxDetallePorUsuario.isChecked()?liquidacion.getUsuario().getId():null);

			//Gastos - Pasajes
			resultGasto = ServiceLocator.getGastoManager().buscarGasto(fecha, fecha, null, liquidacion.getAgencia().getId(), usuarioId);
			//Ventas Pasajes
			List<VentaPasaje> resultDetalleVentasPasaje = ServiceLocator.getVentaPasajesManager().buscarDetalladoVentas(liquidacion.getAgencia().getId(), usuarioId, fecha, fecha, -1);
			//Otros ingresos
			lstIngresos =  ServiceLocator.getGastoManager().obtenerGastosByLiquidacion(fecha, liquidacion.getAgencia().getId(), usuarioId, Constantes.TRUE_VALUE, false);

			for(VentaPasaje ventaPasaje: resultDetalleVentasPasaje) {
				ventaPasaje.setTipoConsulta(0); //Pasajes
				if(ventaPasaje.getTipoComprobante().getId().intValue()==Constantes.ID_TIPCOM_NOTA_CREDITO)
					ventaPasaje.getTipoFormaPago().setDenominacion("NOTA DE CREDITO");
				else if(ventaPasaje.getTipoMovimiento().getId().intValue()==Constantes.ID_TIPMOV_DEVOLUCION)
					ventaPasaje.getTipoFormaPago().setDenominacion("DEVOLUCION");
				else if(ventaPasaje.getTipoMovimiento().getId().intValue()==Constantes.ID_TIPMOV_ANULACION)
					ventaPasaje.getTipoFormaPago().setDenominacion("ANULACION");
				else if(ventaPasaje.getFormaPago().getId().intValue()==Constantes.ID_FORPAG_CREDITO)
					ventaPasaje.getTipoFormaPago().setDenominacion("CREDITO");
				else if(ventaPasaje.getFormaPago().getId().intValue()==Constantes.ID_FORPAG_CORTESIA)
					ventaPasaje.getTipoFormaPago().setDenominacion("CORTESIA");
				listDetalleVentas.add(ventaPasaje);
			}
		}


		//Ventas Carga
		Liquidacion liquidacionCarga = (liquidacion.getLiquidacionCarga()!=null?liquidacion.getLiquidacionCarga():null);
		if(liquidacionCarga !=null) {
			TranscarUsuarioPersonal transcarUsuarioPersonal = null;
			if(chbxDetallePorUsuario.isChecked()) {
				transcarUsuarioPersonal = new TranscarUsuarioPersonal();
				transcarUsuarioPersonal.setId(liquidacionCarga.getUsuario().getId());
				transcarUsuarioPersonal.setLogin(liquidacionCarga.getUsuario().getLogin());
			}

//			List<VentaPasaje> resultDetalleVentasCarga = ServiceLocator.getTranscarWebManager().buscarDetalleVentas(transcarUsuarioPersonal, liquidacionCarga.getAgencia().getId(), fecha, fecha);
			List<VentaPasaje> resultDetalleVentasCarga = ServiceLocator.getTranscarWebManager().buscarDetalleVentas(transcarUsuarioPersonal, liquidacion.getAgencia().getId(), fecha, fecha);

			for(VentaPasaje ventaCarga: resultDetalleVentasCarga) {
				ventaCarga.setTipoConsulta(1); //Carga
				if(ventaCarga.getTipoComprobante().getId().intValue()==Constantes.ID_TIPCOM_NOTA_CREDITO)
					ventaCarga.getTipoFormaPago().setDenominacion("NOTA DE CREDITO");

				listDetalleVentas.add(ventaCarga);
			}
		}

//		//Otros ingresos
//		Double totalOtrosIngresos = .00;
//		for(Gasto otroIngreso: lstIngresos) {
//			totalOtrosIngresos += otroIngreso.getMonto();
//		}

		//Calcula el efectivo a liquidar
		Double saldoEfectivo  = .00;
		if(listDetalleVentas.size()>0) {
			saldoEfectivo = CreateDocument.creaRptLiquidacionByEgresos(null, listDetalleVentas, resultGasto, null, lstIngresos, false, false);
		}

		return saldoEfectivo;
	}

	/**
	 * Carga el listado de liquidaciones
	 * @param liquidacionesVyr : Listado de liquidaciones a cargar
	 * @throws Exception
	 */
	private void cargarListaLiquidaciones(List<Liquidacion> liquidacionesVyr, TreeMap<String, Liquidacion> liquidacionesTranscar)throws Exception{

		Double totalSaldoLiquidacion = .00;
		ArrayList<String> lstAgencias = new ArrayList<>();

		//Cuando no existe liquidacion en pasajes pero si en transcar
		if(liquidacionesTranscar!=null) {
			for(Entry<String,Liquidacion> liquidacionTranscar : liquidacionesTranscar.entrySet()) {
				boolean createLiqPasajes = true;
				for(Liquidacion liquidacion: liquidacionesVyr) {
					Integer agencia_idtranscar = UtilData.getAgencia_Idtranscarweb(liquidacion.getAgencia().getId());
					String key_vyr = Constantes.FORMAT_DATE.format(liquidacion.getFechaLiquidacion());
					key_vyr += (agencia_idtranscar!=null?agencia_idtranscar.toString():"");
					key_vyr += liquidacion.getUsuario().getLogin();

					if(key_vyr.equals(liquidacionTranscar.getKey())) {
						liquidacion.setLiquidacionCarga(liquidacionTranscar.getValue());
						createLiqPasajes = false;
						break;
					}
				}

				//Crea una liquidacion ficticia para pasajes, cuando un usuario tiene una liquidacion en carga y no en pasajes.
				if(createLiqPasajes) {
					Liquidacion _liquidacionTranscar = liquidacionTranscar.getValue();
					Long agencia_idvyr = UtilData.getAgencia_Idvyrbus(_liquidacionTranscar.getAgencia().getId()).longValue();
					Agencia agenciavyr = ServiceLocator.getAgenciaManager().buscarPorId(agencia_idvyr);
					Usuario usuariovyr = ServiceLocator.getUsuarioManager().buscarUsuarioPorLogin(_liquidacionTranscar.getUsuario().getLogin(), null);
					Liquidacion _liquidacionVyr = new Liquidacion();
					_liquidacionVyr.setAgencia(agenciavyr);
					_liquidacionVyr.setFechaLiquidacion(_liquidacionTranscar.getFechaLiquidacion());
					_liquidacionVyr.setLiquidacionCarga(liquidacionTranscar.getValue());
					_liquidacionVyr.setUsuario(usuariovyr);
					liquidacionesVyr.add(_liquidacionVyr);
				}

			}
		}

		String keyBusqueda;
		for(Liquidacion liquidacion: liquidacionesVyr) {
			//Valida liquidacion de carga
			Integer agencia_idtranscar = UtilData.getAgencia_Idtranscarweb(liquidacion.getAgencia().getId());
			keyBusqueda = Constantes.FORMAT_DATE.format(liquidacion.getFechaLiquidacion());
			keyBusqueda += (agencia_idtranscar!=null?agencia_idtranscar.toString():"");
			Liquidacion liquidacionCarga = (liquidacion.getLiquidacionCarga()!=null?liquidacion.getLiquidacionCarga():null);

			Listitem item = new Listitem();
			Listcell cell =new Listcell(Constantes.FORMAT_DATE.format(liquidacion.getFechaLiquidacion()));
			if(!lstAgencias.contains(keyBusqueda) && !chbxDetallePorUsuario.isChecked()) {
				lstAgencias.add(keyBusqueda);

				cell.setStyle("font-size:12px !important");
				item.appendChild(cell);
				cell =new Listcell(liquidacion.getAgencia().toString());
				item.appendChild(cell);
				cell =new Listcell(liquidacion.getUsuario().toString());
				item.appendChild(cell);
				cell =new Listcell(liquidacion.getUsuario().getLogin());
				item.appendChild(cell);

				Double saldoLiquidacion = buscarEfectivoLiquidacion(liquidacion);
				totalSaldoLiquidacion += saldoLiquidacion;
				cell = new Listcell(Util.toNumberFormat(saldoLiquidacion, 2));
				cell.setStyle("font-size:11px !important");
				item.appendChild(cell);
			}else if(chbxDetallePorUsuario.isChecked()){
				cell.setStyle("font-size:12px !important");
				item.appendChild(cell);
				cell =new Listcell(liquidacion.getAgencia().toString());
				item.appendChild(cell);
				cell =new Listcell(liquidacion.getUsuario().toString());
				item.appendChild(cell);
				cell =new Listcell(liquidacion.getUsuario().getLogin());
				item.appendChild(cell);

				Double saldoLiquidacion = buscarEfectivoLiquidacion(liquidacion);
				totalSaldoLiquidacion += saldoLiquidacion;
				cell = new Listcell(Util.toNumberFormat(saldoLiquidacion, 2));
				cell.setStyle("font-size:11px !important");
				item.appendChild(cell);
			}

			if(chbxDetallePorUsuario.isChecked()) {
				cell = new Listcell();
				if(liquidacion.getestadoLiquidacion()==null) {
					cell = new Listcell("-------------");
					cell.setTooltiptext("No se encontr� liquidaci�n en Pasajes");
				}else if (liquidacion.getestadoLiquidacion().equals(Constantes.LIQUI_ESTA_CERRADO)) {
					cell = new Listcell(Constantes.LIQUI_ESTA_CERRADO_LABEL);
					cell.setStyle("font-size:11px !important");
				}else
					cell = new Listcell(Constantes.LIQUI_ESTA_ABIERTO_LABEL);
				item.appendChild(cell);


				//Estado liquidacion - Carga
				cell = new Listcell();
				if(liquidacionCarga!=null) {
					if (liquidacionCarga.getestadoLiquidacion().equals(Constantes.LIQUI_ESTA_CERRADO)) {
						cell = new Listcell(Constantes.LIQUI_ESTA_CERRADO_LABEL);
						cell.setStyle("font-size:11px !important");
					}else
						cell = new Listcell(Constantes.LIQUI_ESTA_ABIERTO_LABEL);
				}else {
					cell = new Listcell("-------------");
					cell.setTooltiptext("No se encontr� liquidaci�n en Carga");
				}

				item.appendChild(cell);

				cell = new Listcell();
				Hbox hbox=new Hbox();
				hbox.setAlign("center");
				/*Detalle Ventas*/
				Toolbarbutton toolbarbutton =new Toolbarbutton();
				toolbarbutton.setAttribute(Liquidacion.class.getName(), liquidacion);
				toolbarbutton.setImage("/resources/mp_preliminar.png");
				toolbarbutton.setAutodisable("self");
				toolbarbutton.setTooltiptext("Detalle Ventas");
				toolbarbutton.addEventListener(Events.ON_CLICK, new EventListener<Event>() {
					@Override
					public void onEvent(Event event) throws Exception {
						// TODO Auto-generated method stub
						Liquidacion _liquidacion = (Liquidacion)event.getTarget().getAttribute(Liquidacion.class.getName());
						viewDetalleVentas(_liquidacion);
					}
				});
				hbox.appendChild(toolbarbutton);

				/*Control especies valoradas*/
				toolbarbutton =new Toolbarbutton();
				toolbarbutton.setAttribute(Liquidacion.class.getName(), liquidacion);
				toolbarbutton.setImage("/resources/mp_preliminar.png");
				toolbarbutton.setAutodisable("self");
				toolbarbutton.setTooltiptext("Control Especies Valoradas");
				toolbarbutton.addEventListener(Events.ON_CLICK, new EventListener<Event>() {
					@Override
					public void onEvent(Event event) throws Exception {
						// TODO Auto-generated method stub
						Liquidacion _liquidacion = (Liquidacion)event.getTarget().getAttribute(Liquidacion.class.getName());
						viewCrtolEspeciesValoradas(_liquidacion);
					}
				});
				hbox.appendChild(toolbarbutton);

				cell.appendChild(hbox);
				item.appendChild(cell);
			}

			liquidacion.setLiquidacionCarga(liquidacionCarga);
			item.setValue(liquidacion);
			ltbxLiquidacionVentas.appendChild(item);
		}

		Listfoot listfoot= new Listfoot();
		Listfooter listfooter= new Listfooter("TOTAL");
		if(chbxDetallePorUsuario.isChecked())
			listfooter.setSpan(4);
		else
			listfooter.setSpan(2);
		listfoot.appendChild(listfooter);
		listfooter= new Listfooter(Util.toNumberFormat(totalSaldoLiquidacion, 2));
		listfoot.appendChild(listfooter);
		listfooter= new Listfooter();
		listfooter.setSpan(3);
		listfoot.appendChild(listfooter);
		ltbxLiquidacionVentas.appendChild(listfoot);
	}

	private void cleanResult(boolean resetFechas)throws Exception{
		dtbxFechaInicio.setValue(new Date());
		dtbxFechaFin.setValue(new Date());
		cmbAgencia.setSelectedIndex(0);
		Util.limpiarListbox(ltbxLiquidacionVentas);
	}

}
