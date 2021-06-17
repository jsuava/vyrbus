/**
 * Proyecto		: SISVYR
 * Sistema		: Sistema de Ventas y Reservas
 * Descripción	: 
 * Autor		: José Sullo Avalos
 * Fecha		: 01/08/2012
 */
package com.cystesoft.vyrbus.view.ctrl;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.TreeMap;

import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Doublebox;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Textbox;

import com.cystesoft.vyrbus.model.bean.Bus;
import com.cystesoft.vyrbus.model.bean.GrupoMantenimiento;
import com.cystesoft.vyrbus.model.bean.NumeroFlota;
import com.cystesoft.vyrbus.model.bean.Servicio;
import com.cystesoft.vyrbus.model.bean.TipoFlota;
import com.cystesoft.vyrbus.service.exceptions.CancelaGrabacionException;
import com.cystesoft.vyrbus.service.exceptions.GrupoMantenimientoNullException;
import com.cystesoft.vyrbus.service.exceptions.KilAcumuladoOdometroNullException;
import com.cystesoft.vyrbus.service.exceptions.KilRecorridoXDiaNullException;
import com.cystesoft.vyrbus.service.exceptions.NumeroBusDuplicadoException;
import com.cystesoft.vyrbus.service.exceptions.NumeroBusNullException;
import com.cystesoft.vyrbus.service.exceptions.NumeroChasisDuplicadoException;
import com.cystesoft.vyrbus.service.exceptions.NumeroChasisNullException;
import com.cystesoft.vyrbus.service.exceptions.NumeroFlotaNullException;
import com.cystesoft.vyrbus.service.exceptions.NumeroMotorDuplicadoException;
import com.cystesoft.vyrbus.service.exceptions.NumeroPlacaDuplicadoException;
import com.cystesoft.vyrbus.service.exceptions.NumeroPlacaNullException;
import com.cystesoft.vyrbus.service.exceptions.ServicioNullException;
import com.cystesoft.vyrbus.service.exceptions.TipoFlotaNullExceptipo;
import com.cystesoft.vyrbus.service.locator.ServiceLocator;
import com.cystesoft.vyrbus.service.util.Constantes;
import com.cystesoft.vyrbus.service.util.Messages;
import com.cystesoft.vyrbus.service.util.Util;
import com.cystesoft.vyrbus.service.util.UtilData;
import com.cystesoft.vyrbus.view.ui.DlgMessage;
import com.cystesoft.vyrbus.view.ui.WndFiltrarParametros;
import com.cystesoft.vyrbus.view.ui.WndOpcionesMantenimiento;
/**
 *
 * @author José Abanto
 * @since JDK1.6
 */
public class WndBus  extends WndOpcionesMantenimiento {
	
	private static final long serialVersionUID = 7015174319519567765L;
	private Combobox 	cboTipoFlota;
	private Combobox 	cboServicio;
	//private Combobox 	cboEmpresa;
	private Combobox 	cboGrupoMantenimiento;
	private Textbox 	txtnumeroBus;
	private Doublebox  dbkmRecoridoXDia;
	private Doublebox 	dbkmAcumuladoOdoMetro;
	private Doublebox 	dbkilAcumuladoEstandar;
	private Textbox  	txtnumeroPlaca;
	private Textbox  	txtnumeroEjes;
	private Textbox  	txtnumeroChasis;
	private Textbox  	txtnumeroMotor;
	private Textbox  	txttarjetaPropiedad;
	private Textbox  	txtcapacidad;
	private Textbox  	txtanioFabricacion;
	private Combobox 	cbonumeroFlota;
	
	private Bus oBus = null;
	
	
	private TreeMap<String, Object> criteriosBusqueda = new  TreeMap<String, Object>();
	private List<String> criteriosOrdenar = null;

	@Override
	public void onCreate() throws Exception {

		UtilData.cargarDataCombo(cboTipoFlota, TipoFlota.class, false);
		UtilData.cargarDataCombo(cboServicio, Servicio.class, false);
		UtilData.cargarDataCombo(cboGrupoMantenimiento, GrupoMantenimiento.class, false);
		UtilData.cargarDataCombo(cbonumeroFlota, NumeroFlota.class, false);
		criteriosOrdenar = new ArrayList<String>();
		criteriosOrdenar.add("codigo");
		
		dbkmRecoridoXDia.setLocale(Locale.US);
		dbkmAcumuladoOdoMetro.setLocale(Locale.US);
		dbkilAcumuladoEstandar.setLocale(Locale.US);
	}

	@Override
	public void initComponents() {
		cboTipoFlota = (Combobox) getFellow("cboTipoFlota");
		cboServicio  = (Combobox) getFellow("cboServicio");
		//cboEmpresa	 = (Combobox) getFellow("cboEmpresa");
		cboGrupoMantenimiento = (Combobox) getFellow("cboGrupoMantenimiento");
		txtnumeroBus = (Textbox) getFellow("txtnumeroBus");
		dbkmRecoridoXDia = (Doublebox) getFellow("dbkmRecoridoXDia");
		dbkmAcumuladoOdoMetro = (Doublebox) getFellow("dbkmAcumuladoOdoMetro");
		dbkilAcumuladoEstandar = (Doublebox) getFellow("dbkilAcumuladoEstandar");
		txtnumeroPlaca = (Textbox) getFellow("txtnumeroPlaca");
		txtnumeroEjes = (Textbox) getFellow("txtnumeroEjes");
		txtnumeroChasis = (Textbox) getFellow("txtnumeroChasis");
		txtnumeroMotor = (Textbox) getFellow("txtnumeroMotor");
		txttarjetaPropiedad = (Textbox) getFellow("txttarjetaPropiedad");
		txtcapacidad = (Textbox) getFellow("txtcapacidad");
		txtanioFabricacion = (Textbox) getFellow("txtanioFabricacion");
		cbonumeroFlota = (Combobox) getFellow("cbonumeroFlota");	
		cbonumeroFlota	 = (Combobox) getFellow("cbonumeroFlota");
	}
	
	@Override
	public void onNew() {
		cboTipoFlota.setSelectedIndex(0);
		cboServicio.setSelectedIndex(0);
		cbonumeroFlota.setSelectedIndex(0);
		cboGrupoMantenimiento.setSelectedIndex(0);
	}

	@Override
	public void onSearch() {
		final WndFiltrarParametros oWndFiltrar = new WndFiltrarParametros();
		
		oWndFiltrar.addParameter("1. Tipo de Flota", TipoFlota.class);
		oWndFiltrar.addParameter("2. Servicio", Servicio.class);
		oWndFiltrar.addParameter("3. Número de Flota", NumeroFlota.class);
		oWndFiltrar.addParameter("4. Número de Bus", String.class);
		
		
		this.appendChild(oWndFiltrar);
		oWndFiltrar.setMode("modal");
		oWndFiltrar.addEventListener(com.cystesoft.vyrbus.view.ui.Events.ON_FILTER, new EventListener<Event>() {

			@Override
			public void onEvent(Event event) throws Exception {
				Integer tipoFlota = (Integer) oWndFiltrar.getParameterValue("1. Tipo de Flota");
				Servicio oservicio = (Servicio) oWndFiltrar.getParameterValue("2. Servicio");
				Integer numeroFlota = (Integer) oWndFiltrar.getParameterValue("3. Número de Flota");
				String codigo = oWndFiltrar.getParameterValue("4. Número de Bus").toString();
				String estadoRegistro = Constantes.VALUE_ACTIVO;
				
				if (tipoFlota == null || tipoFlota == 0){
					criteriosBusqueda.remove("tipoFlota");
				}else{
					TipoFlota otipoFlota = new TipoFlota();
					otipoFlota.setId(tipoFlota);
					criteriosBusqueda.put("tipoFlota", otipoFlota);
				}
				
				if (oservicio == null ){
					criteriosBusqueda.remove("servicio");
				}else{
					
					criteriosBusqueda.put("servicio", oservicio);
				}
				
				if (numeroFlota == null || numeroFlota == 0){
					criteriosBusqueda.remove("numeroFlota");
				}else{
					NumeroFlota onumeroFlota = new NumeroFlota();
					onumeroFlota.setId(numeroFlota);
					criteriosBusqueda.put("numeroFlota", onumeroFlota);
				}
				
				if (codigo.trim().equals("")) {
					criteriosBusqueda.remove("codigo");
				}else{criteriosBusqueda.put("codigo", "%" + codigo + "%");}
				
				criteriosBusqueda.put("estadoRegistro", estadoRegistro);
				
				listarRegistros(ServiceLocator.getBusManager().buscarPorX(criteriosBusqueda, criteriosOrdenar));
			}
		});
		
	}

	@Override
	public void onRefresh(int tab) throws Exception {
		if (!criteriosBusqueda.isEmpty()){
			this.listarRegistros(ServiceLocator.getBusManager().buscarPorX(criteriosBusqueda, criteriosOrdenar));
		}
	}

	@Override
	public void onModify(int tab) throws Exception {
		Long id = new Long(0);
		id = ((Bus) listboxLista.getSelectedItem().getValue()).getId().longValue();
		this.mantenimientoRegistro(id);
	}

	@Override
	public void onCancel(int action) throws Exception {
		switch (action) {
			case ACTION_NEW:
				break;
			case ACTION_MODIFY:
				this.mantenimientoRegistro(new Long(textboxId.getText()));
				break;
		}
	}

	@Override
	public void onSave(int action) throws Exception {
		try{
			if (!(cboTipoFlota.getSelectedItem().getValue() instanceof TipoFlota))
				throw new TipoFlotaNullExceptipo();
			else if (!(cboServicio.getSelectedItem().getValue() instanceof Servicio))
				throw new ServicioNullException();
			else if (!(cbonumeroFlota.getSelectedItem().getValue() instanceof NumeroFlota))
				throw new NumeroFlotaNullException();
			else if (!(cboGrupoMantenimiento.getSelectedItem().getValue() instanceof GrupoMantenimiento))
				throw new GrupoMantenimientoNullException();
			else if (txtnumeroBus.getText().equals(""))
				throw new NumeroBusNullException();
			else if (dbkmRecoridoXDia.getText().equals("") || dbkmRecoridoXDia.getValue().equals(0))
				throw new KilRecorridoXDiaNullException();
			else if (dbkmAcumuladoOdoMetro.getText().equals("") || dbkmAcumuladoOdoMetro.getValue().equals(0))
				throw new KilAcumuladoOdometroNullException();
			else if (txtnumeroPlaca.getText().trim().equals(""))
				throw new NumeroPlacaNullException();
			else if (txtnumeroChasis.getText().trim().equals(""))
				throw new NumeroChasisNullException();
						
			if (action==ACTION_NEW)
				oBus = new Bus();
			
			Integer id = (textboxId.getText().equals("") ? 0 : new Integer(textboxId.getText()));
			oBus.setId(id);
			if (cboTipoFlota.getSelectedItem().getValue() instanceof TipoFlota) {
				TipoFlota oTipoFlota = new TipoFlota();			
				oTipoFlota.setId(((TipoFlota) cboTipoFlota.getSelectedItem().getValue()).getId());
				oTipoFlota.setDenominacion( ((TipoFlota) cboTipoFlota.getSelectedItem().getValue()).getDenominacion());
				oBus.setTipoFlota(oTipoFlota);
			}
			if  (cbonumeroFlota.getSelectedItem().getValue() instanceof NumeroFlota) {
				NumeroFlota oNumeroFlota = new NumeroFlota();
				oNumeroFlota.setId(((NumeroFlota) cbonumeroFlota.getSelectedItem().getValue()).getId());
				oNumeroFlota.setDenominacion( ((NumeroFlota) cbonumeroFlota.getSelectedItem().getValue()).getDenominacion());
				oBus.setNumeroFlota(oNumeroFlota);
			}
			if  (cboServicio.getSelectedItem().getValue() instanceof Servicio) {
				Servicio oServicio = new Servicio();
				oServicio.setId(((Servicio) cboServicio.getSelectedItem().getValue()).getId());
				oServicio.setDenominacion(((Servicio) cboServicio.getSelectedItem().getValue()).getDenominacion());
				oBus.setServicio(oServicio);
			}

			if  (cboGrupoMantenimiento.getSelectedItem().getValue()  instanceof GrupoMantenimiento) {
				GrupoMantenimiento oGrupoMantenimiento = new GrupoMantenimiento();
				oGrupoMantenimiento.setId(((GrupoMantenimiento) cboGrupoMantenimiento.getSelectedItem().getValue()).getId());
				oGrupoMantenimiento.setDenominacion(((GrupoMantenimiento) cboGrupoMantenimiento.getSelectedItem().getValue()).getDenominacion());
				oBus.setGrupoMantenimiento(oGrupoMantenimiento);
			}
						
			oBus.setCodigo(txtnumeroBus.getText());
			oBus.setKilRecorridoDia(dbkmRecoridoXDia.getValue());
			oBus.setKilAcumuladoOdometro(dbkmAcumuladoOdoMetro.getValue());
			
			if (!(dbkilAcumuladoEstandar.getText().equals("")))
				oBus.setKilAcumuladoEstandar(dbkilAcumuladoEstandar.getValue());
			oBus.setNumeroPlaca(txtnumeroPlaca.getText().trim().toUpperCase());
			oBus.setNumeroEjes(txtnumeroEjes.getText().equals("") ? 0 : new Integer(txtnumeroEjes.getText()));
			oBus.setNumeroChasis(txtnumeroChasis.getText().trim().toUpperCase());
			oBus.setNumeroMotor(txtnumeroMotor.getText().trim().toUpperCase());
			oBus.setTarjetaPropiedad(txttarjetaPropiedad.getText().trim().toUpperCase());
			oBus.setCapacidad(txtcapacidad.getText().equals("") ? 0 : new Integer(txtcapacidad.getText()));
			oBus.setAnioFabricacion(txtanioFabricacion.getText().equals("") ? 0 : new Integer(txtanioFabricacion.getText()));	
			oBus.setEstadoRegistro(Constantes.VALUE_ACTIVO);
			switch (action) {
				case ACTION_NEW:
					UtilData.auditarRegistro(oBus, getUsuario(), Executions.getCurrent());
					ServiceLocator.getBusManager().guardar(oBus);
					textboxId.setText((new Long(oBus.getId()).toString()));
					break;
				case ACTION_MODIFY:
					UtilData.auditarRegistro(oBus, true, getUsuario(), Executions.getCurrent());
					ServiceLocator.getBusManager().actualizar(oBus);
					break;
			}
			/*RECUPERA EL REGISTRO ACTUALIZADO O EL NUEVO*/        
			criteriosBusqueda.remove("tipoFlota");
			criteriosBusqueda.remove("servicio");
			criteriosBusqueda.remove("numeroFlota");
			criteriosBusqueda.remove("codigo");
			criteriosBusqueda.put("codigo", oBus.getCodigo());
			criteriosBusqueda.put("estadoRegistro", Constantes.VALUE_ACTIVO);
			listarRegistros(ServiceLocator.getBusManager().buscarPorX(criteriosBusqueda, criteriosOrdenar));
			
		}catch (TipoFlotaNullExceptipo tfnex){
			DlgMessage.information(Messages.getString("WndBus.information.TipoFlota"),cboTipoFlota);
			throw new CancelaGrabacionException();
		}catch (ServicioNullException snex){
			DlgMessage.information(Messages.getString("WndBus.information.Servicio"),cboServicio);
			throw new CancelaGrabacionException();
		}catch (NumeroFlotaNullException nfnex){
			DlgMessage.information(Messages.getString("WndBus.information.Numeroflota"),cbonumeroFlota);
			throw new CancelaGrabacionException();
		}catch (GrupoMantenimientoNullException gmnex){
			DlgMessage.information(Messages.getString("WndBus.information.GrupoMantenimiento"),cboGrupoMantenimiento);
			throw new CancelaGrabacionException();
		}catch (NumeroBusNullException nbnex){
			DlgMessage.information(Messages.getString("WndBus.information.NumeroBus"),txtnumeroBus);
			throw new CancelaGrabacionException();
		}catch (KilRecorridoXDiaNullException kxdnex){
			DlgMessage.information(Messages.getString("WndBus.information.KilRecorridoXDia"),dbkmRecoridoXDia);
			throw new CancelaGrabacionException();
		}catch (KilAcumuladoOdometroNullException kamnex){
			DlgMessage.information(Messages.getString("WndBus.information.KilAcumuladoOdometro"),dbkmAcumuladoOdoMetro);
			throw new CancelaGrabacionException();
		}catch (NumeroPlacaNullException npnex){
			DlgMessage.information(Messages.getString("WndBus.information.NumeroPlaca"),txtnumeroPlaca);
			throw new CancelaGrabacionException();
		}catch (NumeroChasisNullException ncnex){
			DlgMessage.information(Messages.getString("WndBus.information.NumeroChasis"),txtnumeroChasis);
			throw new CancelaGrabacionException();
		}catch (NumeroBusDuplicadoException nbdex){
			DlgMessage.information(Messages.getString("WndBus.information.NumeroBusDuplicado"),txtnumeroBus);
			throw new CancelaGrabacionException();
		}catch (NumeroPlacaDuplicadoException npdex){
			DlgMessage.information(Messages.getString("WndBus.information.NumeroPlacaDuplicado"),txtnumeroPlaca);
			throw new CancelaGrabacionException();
		}catch (NumeroChasisDuplicadoException ncdex){
			DlgMessage.information(Messages.getString("WndBus.information.NumeroChasisDuplicado"),txtnumeroChasis);
			throw new CancelaGrabacionException();
		}catch (NumeroMotorDuplicadoException nmdex){
			DlgMessage.information(Messages.getString("WndBus.information.NumeroMotorDuplicado"),txtnumeroMotor);
			throw new CancelaGrabacionException();
		}catch (Exception ex){
			DlgMessage.error(this.getClass().getName()+" "+ex.getMessage());
			ex.printStackTrace(); throw new CancelaGrabacionException();
		}
		
	}

	@Override
	public void onDelete(int tab) throws Exception {
		Long id = (long) 0;
		id = ((Bus) listboxLista.getSelectedItem().getValue()).getId().longValue();
//		switch (tab) {
//			case TAB_LIST:
//				id = new Long((String) listboxLista.getSelectedItem().getValue());
//				break;
//
//			case TAB_MAINTENANCE:
//				id = new Long(textboxId.getText());
//				break;
//		}
		Bus bus=ServiceLocator.getBusManager().buscarPorId(id);
		bus.setEstadoRegistro(Constantes.VALUE_INACTIVO);
		UtilData.auditarRegistro(bus, true, getUsuario(), Executions.getCurrent());
		ServiceLocator.getBusManager().actualizar(bus);
//		ServiceLocator.getBusManager().inactivar(id);
		
	}

	@Override
	public void onPrint(int tab) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onExport(int tab) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onHelp() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onClose() {
		closeTabWindow();
	}

	@Override
	public void onChangeTab(int tab) throws Exception {
		switch (tab) {
			case TAB_LIST:
				break;
			case TAB_MAINTENANCE:
				if (listboxLista.getSelectedIndex()> -1){
					this.mantenimientoRegistro(((Bus) listboxLista.getSelectedItem().getValue()).getId().longValue());
				}
				break;
		}
	}

	public void listarRegistros(ArrayList<Bus> arrayList) {
		
		Listitem item=null;
		Listcell cell=null;
		int x=1;
		
		Util.limpiarListbox(listboxLista);
		
		for(Bus bus: arrayList){
			item=new Listitem();
			cell=new Listcell(String.valueOf(x++));
			item.appendChild(cell);
			cell=new Listcell(bus.getTipoFlota().getDenominacion());
			item.appendChild(cell);
			cell=new Listcell(bus.getServicio().getDenominacion());
			item.appendChild(cell);
			cell=new Listcell(bus.getNumeroFlota().getDenominacion());
			item.appendChild(cell);
			cell=new Listcell(bus.getCodigo());
			cell.setStyle("font-size:11px !important");
			item.appendChild(cell);
			cell=new Listcell(bus.getNumeroPlaca());
			cell.setStyle("font-size:11px !important");
			item.appendChild(cell);
			cell=new Listcell(bus.getNumeroEjes().toString());
			cell.setStyle("font-size:11px !important");
			item.appendChild(cell);
			cell=new Listcell(bus.getTarjetaPropiedad());
			cell.setStyle("font-size:11px !important");
			item.appendChild(cell);
			cell=new Listcell(bus.getCapacidad().toString());
			cell.setStyle("font-size:11px !important");
			item.appendChild(cell);
			cell=new Listcell(bus.getAnioFabricacion().toString());
			cell.setStyle("font-size:11px !important");
			item.appendChild(cell);
			
			item.setValue(bus);
			listboxLista.appendChild(item);
		}		
	}
	
	public void mantenimientoRegistro (Long id) throws Exception {
		oBus = ServiceLocator.getBusManager().buscarPorId(id);
		TipoFlota otipoFlota = oBus.getTipoFlota();
		Servicio oservicio = oBus.getServicio();
		NumeroFlota onumeroFlota = oBus.getNumeroFlota();
		GrupoMantenimiento ogrupoMantenimiento = oBus.getGrupoMantenimiento();
		
		textboxId.setText(String.valueOf(oBus.getId()));
		
		if(otipoFlota != null){
			Util.seleccionarValorItemCombo(TipoFlota.class, cboTipoFlota, otipoFlota.getId());
		}
		if (oservicio != null){
			Util.seleccionarValorItemCombo(Servicio.class, cboServicio, oservicio.getId());
		}
		if (onumeroFlota != null){
			Util.seleccionarValorItemCombo(NumeroFlota.class, cbonumeroFlota, onumeroFlota.getId());
		}
		if (ogrupoMantenimiento != null){
			Util.seleccionarValorItemCombo(GrupoMantenimiento.class, cboGrupoMantenimiento, ogrupoMantenimiento.getId());
		}
		txtnumeroBus.setText(oBus.getCodigo());
		dbkmRecoridoXDia.setValue(oBus.getKilRecorridoDia());
		dbkmAcumuladoOdoMetro.setValue(oBus.getKilAcumuladoOdometro());
		if (oBus.getKilAcumuladoEstandar() != null)
			dbkilAcumuladoEstandar.setValue(oBus.getKilAcumuladoEstandar());
		txtnumeroPlaca.setText(oBus.getNumeroPlaca());
		txtnumeroEjes.setText(String.valueOf(oBus.getNumeroEjes()));
		txtnumeroChasis.setText(oBus.getNumeroChasis());
		txtnumeroMotor.setText(oBus.getNumeroMotor());
		txttarjetaPropiedad.setText(oBus.getTarjetaPropiedad());
		txtcapacidad.setText(String.valueOf(oBus.getCapacidad()));
		txtanioFabricacion.setText(String.valueOf(oBus.getAnioFabricacion()));	
	}
}
