/**
 * Proyecto		: SISVYR
 * Sistema		: Sistema de Ventas y Reservas
 * Descripci�n	:
 * Autor		: Marco Antonio Oscco
 * Fecha		: 28/05/2013
 */
package com.cystesoft.vyrbus.view.ctrl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;
import java.util.TreeMap;

import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zul.Checkbox;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Comboitem;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.Doublebox;
import org.zkoss.zul.Label;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Radio;
import org.zkoss.zul.Tab;
import org.zkoss.zul.Timebox;
import org.zkoss.zul.Toolbarbutton;

import com.cystesoft.vyrbus.model.bean.CanalVenta;
import com.cystesoft.vyrbus.model.bean.Localidad;
import com.cystesoft.vyrbus.model.bean.Ruta;
import com.cystesoft.vyrbus.model.bean.Servicio;
import com.cystesoft.vyrbus.model.bean.Tarifa;
import com.cystesoft.vyrbus.model.bean.TarifaFechaAbierta;
import com.cystesoft.vyrbus.model.bean.TarifaRegular;
import com.cystesoft.vyrbus.model.bean.TarifaRegularAud;
import com.cystesoft.vyrbus.model.bean.TipoItinerario;
import com.cystesoft.vyrbus.service.locator.ServiceLocator;
import com.cystesoft.vyrbus.service.util.Constantes;
import com.cystesoft.vyrbus.service.util.Messages;
import com.cystesoft.vyrbus.service.util.Util;
import com.cystesoft.vyrbus.service.util.UtilData;
import com.cystesoft.vyrbus.view.ui.DlgMessage;
import com.cystesoft.vyrbus.view.ui.WndBase;

/**
 * @author Marco Oscco
 *
 */
public class WndTarifario extends WndBase implements Serializable {

	private static final long serialVersionUID = 1L;
	private Radio rdAmbos;
	private Radio rdSinTarifa;
	private Radio rdConTarifa;
	private Combobox cmbCanalBus;
	private Combobox cmbServicioBus;
	private Combobox cmbOrigenBus;
	private Combobox cmbDestinoBus;
	private Datebox dtbxFecInicioBus;
	private Datebox dtbxFecFinBus;
	private Timebox tmbxHoraPartidaBus;
	private Combobox cmbTipoItinerario;
	private Listbox lsbxRutas;
	private Listbox lsbxTarifaFA;
//
	private Combobox cmbCanal;
	private Combobox cmbServicio;
	private Combobox cmbOrigen;
	private Combobox cmbDestino;
	private Datebox dtbxFecInicio;
	private Datebox dtbxFecFinal;
	private Combobox cmbPiso;
	private Combobox cmbZona;
	private Timebox tmbxHoraPartida;
	private Label lblTarifafa;
	private Checkbox chkPorServicio;
	private Checkbox chkTarifaAbierta;
	private Checkbox chkTarifaFA;

	private Tab tabTarifa;
	private Tab tabTarifaFA;
//	private Button btnAplicar;

	private Doublebox dlbxTarifaP1;
	private Doublebox dlbxTarifaP2;
//	private Listheader lstheaFecha;
//	private Listheader lstheaItinerario;
//	private Checkbox ckbxDetalle;
//	private Intbox ibItinerario;

	private Doublebox dlbxTarifaFa;
//	private Doublebox dlbxTarifaLista;


//	private Listbox ltbxMantoTarifario;
	private Toolbarbutton btnNuevaTarifa;
	private Toolbarbutton btnCopiarTarifa;
	private Toolbarbutton btnCancelar;
	private Toolbarbutton btnGuardarTarifa;
//
//	private String ATTRIBUTE_CANAL_VENTA="1";
//	private String ATTRIBUTE_SERVICIO="2";
//	private String ATTRIBUTE_ORIGEN="3";
//	private String ATTRIBUTE_DESTINO="4";
//	private String ATTRIBUTE_HORA_PARTIDA="5";
//	private String ATTRIBUTE_PISO="6";
//	private String ATTRIBUTE_ZONABUS="7";
//	private String ATTRIBUTE_PRECIO="8";

//	private String ATTRIBUTE_ITINERARIO="8";
	Integer flag = 0;
	Integer ambosPisos=0;


	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.view.ui.WndBase#initComponents()
	 */
	@Override
	public void initComponents() {
		// TODO Auto-generated method stub
		tabTarifa = (Tab)this.getFellow("tabTarifa");
		tabTarifaFA = (Tab)this.getFellow("tabTarifaFA");

		cmbCanalBus=(Combobox)this.getFellow("cmbCanalBus");
		cmbServicioBus=(Combobox)this.getFellow("cmbServicioBus");
		cmbOrigenBus=(Combobox)this.getFellow("cmbOrigenBus");
		cmbDestinoBus=(Combobox)this.getFellow("cmbDestinoBus");
		dtbxFecInicioBus=(Datebox)this.getFellow("dtbxFecInicioBus");
		dtbxFecFinBus=(Datebox)this.getFellow("dtbxFecFinBus");
		tmbxHoraPartidaBus=(Timebox)this.getFellow("tmbxHoraPartidaBus");
		cmbCanal=(Combobox)this.getFellow("cmbCanal");
		cmbPiso=(Combobox)this.getFellow("cmbPiso");
		cmbZona=(Combobox)this.getFellow("cmbZona");

		dtbxFecInicio=(Datebox)this.getFellow("dtbxFecInicio");
		dtbxFecFinal=(Datebox)this.getFellow("dtbxFecFinal");
		lsbxRutas=(Listbox)this.getFellow("lsbxRutas");
		lsbxTarifaFA = (Listbox)this.getFellow("lsbxTarifaFA");
		rdAmbos = (Radio)this.getFellow("rdAmbos");
		rdSinTarifa = (Radio)this.getFellow("rdSinTarifa");
		rdConTarifa = (Radio)this.getFellow("rdConTarifa");

		//Objetos para la edicion o adicion de tarifas
		cmbOrigen=(Combobox)this.getFellow("cmbOrigen");
		cmbDestino=(Combobox)this.getFellow("cmbDestino");
		tmbxHoraPartida=(Timebox)this.getFellow("tmbxHoraPartida");
		cmbServicio=(Combobox)this.getFellow("cmbServicio");
		dlbxTarifaP1=(Doublebox)this.getFellow("dlbxTarifaP1");
		dlbxTarifaP2=(Doublebox)this.getFellow("dlbxTarifaP2");

		lblTarifafa = (Label)this.getFellow("lblTarifafa");
		chkPorServicio = (Checkbox)this.getFellow("chkPorServicio");
		chkTarifaAbierta = (Checkbox)this.getFellow("chkTarifaAbierta");
		chkTarifaFA = (Checkbox)this.getFellow("chkTarifaFA");


//		btnAplicar = (Button)this.getFellow("btnAplicar");

//		rbActualizaTarifa=(Radio)this.getFellow("rbActualizaTarifa");
//		rbIncrementoTarifa=(Radio)this.getFellow("rbIncrementoTarifa");

//		lstheaFecha=(Listheader)this.getFellow("lstheaFecha");
//		lstheaItinerario=(Listheader)this.getFellow("lstheaItinerario");
//		ckbxDetalle=(Checkbox)this.getFellow("ckbxDetalle");
//		ibItinerario=(Intbox)this.getFellow("ibItinerario");

		cmbTipoItinerario=(Combobox)this.getFellow("cmbTipoItinerario");
		dlbxTarifaFa=(Doublebox)this.getFellow("dlbxTarifaFa");
//		dlbxTarifaLista=(Doublebox)this.getFellow("dlbxTarifaLista");

//		ltbxMantoTarifario=(Listbox)this.getFellow("ltbxMantoTarifario");
		btnNuevaTarifa=(Toolbarbutton)this.getFellow("btnNuevaTarifa");
		btnCopiarTarifa=(Toolbarbutton)this.getFellow("btnCopiarTarifa");
		btnCancelar=(Toolbarbutton)this.getFellow("btnCancelar");
		btnGuardarTarifa=(Toolbarbutton)this.getFellow("btnGuardarTarifa");

	}

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.view.ui.WndBase#onCreate()
	 */
	@Override
	public void onCreate() throws Exception {
		// TODO Auto-generated method stub
		String fecha = Util.DatetoString(new Date(), "yyyyMMdd");

		//Para la busqueda
		UtilData.cargarDataCombo(cmbCanalBus, CanalVenta.class,true);
		UtilData.cargarDataCombo(cmbServicioBus, Servicio.class,true);
		UtilData.cargarDataCombo(cmbOrigenBus, Localidad.class,true);
		UtilData.cargarDataCombo(cmbDestinoBus, Localidad.class,true);
		UtilData.cargarDataCombo(cmbTipoItinerario, TipoItinerario.class,true);

		dtbxFecInicioBus.setConstraint("after "+fecha);
		dtbxFecFinBus.setConstraint("after "+fecha);
		dtbxFecInicioBus.setValue(new Date());
		dtbxFecFinBus.setValue(new Date());


		dlbxTarifaP1.setLocale(Locale.US);
		dlbxTarifaP2.setLocale(Locale.US);
		dlbxTarifaFa.setLocale(Locale.US);

		//Para la Edicion o adicion de tarifas
		UtilData.cargarDataCombo(cmbCanal, CanalVenta.class,false);
		UtilData.cargarDataCombo(cmbServicio, Servicio.class,true);
		UtilData.cargarDataCombo(cmbOrigen, Localidad.class,true);
		UtilData.cargarDataCombo(cmbDestino, Localidad.class,true);

		dtbxFecInicio.setConstraint("after "+fecha);
		dtbxFecFinal.setConstraint("after "+fecha);
		dtbxFecInicio.setValue(null);
		dtbxFecFinal.setValue(null);

		onLoadPiso(2);
		onLoadZona();
		inicializarControles(true);

		if(getRol().getId().intValue()==Constantes.ID_ROL_ADMINISTRADOR) {
			for(Comboitem comboitem: cmbCanal.getItems()) {
				if(comboitem.getValue()!=null && comboitem.getValue() instanceof CanalVenta && ((CanalVenta)comboitem.getValue()).getId().intValue()!=Constantes.ID_CANVEN_COUNTER) {
					cmbCanal.getItemAtIndex(comboitem.getIndex()).setVisible(false);
				}
			}
		}else if(!(getRol().getId().intValue()==Constantes.ID_ROL_ADMIN || getRol().getId().intValue()==Constantes.ID_ROL_SUPER_USUARIO)) {
			for(Comboitem comboitem: cmbCanal.getItems()) {
				if(comboitem.getValue()!=null && comboitem.getValue() instanceof CanalVenta) {
					cmbCanal.getItemAtIndex(comboitem.getIndex()).setVisible(false);
				}
			}
		}
		
		

//		dlbxTarifaLista.setLocale(Locale.US);
//		rbIncrementoTarifa.setDisabled(getRol().getId().intValue()==Constantes.ID_ROL_MARKETING);
//		ckbxDetalle.setDisabled(true);

		Util.seleccionarValorItemCombo(TipoItinerario.class, cmbTipoItinerario, Constantes.ID_TIPITI_REGULAR);

		/*Validacion de roles para la modificacion de la tarifa real del servicio y fecha abierta*/
//		dlbxTarifa.setDisabled(getRol().getId().intValue()==Constantes.ID_ROL_MARKETING);
//		dlbxTarifaFa.setDisabled(getRol().getId().intValue()==Constantes.ID_ROL_MARKETING);

		lsbxRutas.addEventListener(Events.ON_DOUBLE_CLICK,new EventListener<Event>() {
			@Override
			public void onEvent(Event event) throws Exception {
				if(!(btnCopiarTarifa.isDisabled()))
					onClick_btnCopiarTarifa();
			}
		});

		lsbxTarifaFA.addEventListener(Events.ON_DOUBLE_CLICK,new EventListener<Event>() {
			@Override
			public void onEvent(Event event) throws Exception {
				if(!(btnCopiarTarifa.isDisabled()))
					onClick_btnCopiarTarifa();
			}
		});



		/*EVENTO ON_CHANGE DEL LA FECHA INICIO*/
		dtbxFecInicioBus.addEventListener(Events.ON_CHANGE, new EventListener<Event>() {
			@Override
			public void onEvent(Event event) throws Exception {
				String fecha = Util.DatetoString(dtbxFecInicioBus.getValue(), "yyyyMMdd");
				dtbxFecFinBus.setConstraint("after "+fecha);
				/*Asigna a fecha fin el valor de la fecha inicio*/
				dtbxFecFinBus.setValue(dtbxFecInicioBus.getValue());
			}
		});



	}

	private void onLoadPiso(Integer pisos){
		Comboitem cmbItem = new Comboitem("TODOS");
		cmbItem.setValue("0");
		cmbPiso.appendChild(cmbItem);
		cmbItem = new Comboitem("PISO 1");
		cmbItem.setValue("1");
		cmbPiso.appendChild(cmbItem);
		cmbPiso.setSelectedIndex(1);
		if(pisos==2){
			cmbItem = new Comboitem("PISO 2");
			cmbItem.setValue("2");
			cmbPiso.appendChild(cmbItem);
			cmbPiso.setSelectedIndex(0);
		}
	}

	private void onSelectPiso(Servicio oServicio){
//		Util.seleccionarValorItemCombo(Servicio.class, cmbServicio, oServicio.getId());
		cmbZona.setSelectedIndex(1);
		if(oServicio.getNumeroPisos()==1){
			cmbPiso.setSelectedIndex(1);
			habilitarPisoUno(true);
		}
		else{
			cmbPiso.setSelectedIndex(0);
			habilitarPisoUno(false);
		}

	}

	private void onLoadZona(){
		Comboitem cmbItem = new Comboitem("TODOS");
		cmbItem.setValue("0");
		cmbZona.appendChild(cmbItem);
		cmbItem = new Comboitem("ZONA 1");
		cmbItem.setValue("1");
		cmbZona.appendChild(cmbItem);
		cmbZona.setSelectedIndex(0);
	}

	public void editarTarifa() throws Exception{
		if(!chkTarifaFA.isChecked()){
			Integer index = lsbxRutas.getSelectedIndex();
			if (index >= 0){
				Listitem itemTarifa = lsbxRutas.getItemAtIndex(index);

				//Canal de venta
				String strCanalVenta = ((TarifaRegular) itemTarifa.getValue()).getTarifa().getCanalVenta().getDenominacion();
				if(strCanalVenta != Constantes.SIN_TARIFA) {
					Util.seleccionarValorItemCombo(CanalVenta.class, cmbCanal, ((TarifaRegular) itemTarifa.getValue()).getTarifa().getCanalVenta().getId());					
				}else if(cmbCanal.getItems().size()>0 && cmbCanal.getSelectedIndex() < 0)
						cmbCanal.setSelectedIndex(0);

				//Servicio
				Util.seleccionarValorItemCombo(Servicio.class, cmbServicio,
										((TarifaRegular) itemTarifa.getValue()).getTarifa().getServicio().getId());

				//Selecciona el Origen
				Util.seleccionarValorItemCombo(Localidad.class, cmbOrigen,
										((TarifaRegular) itemTarifa.getValue()).getTarifa().getRuta().getLocalidadOrigen().getId());
				//Destino
				Util.seleccionarValorItemCombo(Localidad.class, cmbDestino,
										((TarifaRegular) itemTarifa.getValue()).getTarifa().getRuta().getLocalidadDestino().getId());

				tmbxHoraPartida.setText(((TarifaRegular) itemTarifa.getValue()).getHoraPartida());

				Integer indexPiso = ((TarifaRegular) itemTarifa.getValue()).getTarifa().getPisoBus();
				if(indexPiso!=null)
					cmbPiso.setSelectedIndex(indexPiso+1 );
				else
					cmbPiso.setSelectedIndex(0);

				Integer indexZona = ((TarifaRegular) itemTarifa.getValue()).getTarifa().getZonaBus();
				if(indexZona!=null)
					cmbZona.setSelectedIndex(indexZona);
				else
					cmbZona.setSelectedIndex(0);

				Double tarifa = ((TarifaRegular) itemTarifa.getValue()).getMonto();
				if(tarifa!=null){
					if(indexPiso!=null){
						if(indexPiso==0){
							dlbxTarifaP1.setValue(tarifa);
							habilitarPisoUno(true);

						}
						else{
							dlbxTarifaP1.setValue(0);
							dlbxTarifaP2.setValue(tarifa);
						}
					}
				}
				else{
					dlbxTarifaP1.setValue(0);
					dlbxTarifaP2.setValue(0);
				}

				//Fechas
				dtbxFecInicio.setValue( ((TarifaRegular) itemTarifa.getValue()).getFechaTarifa() );
				dtbxFecFinal.setValue( ((TarifaRegular) itemTarifa.getValue()).getFechaTarifa() );

				dtbxFecInicio.setDisabled(true);
				onChangeServicio();

			}else{
				DlgMessage.information(Messages.getString("WndItinerario.information.SeleccionarTramo"));
			}
		}
		//Editar tarifas en Fecha Abierta
		else{
			Integer index = lsbxTarifaFA.getSelectedIndex();
			if (index >= 0){
				Listitem itemTarifa = lsbxTarifaFA.getItemAtIndex(index);

				//Servicio
				Util.seleccionarValorItemCombo(Servicio.class, cmbServicio,
										((TarifaFechaAbierta) itemTarifa.getValue()).getServicio().getId());

				//Selecciona el Origen
				Util.seleccionarValorItemCombo(Localidad.class, cmbOrigen,
										((TarifaFechaAbierta) itemTarifa.getValue()).getRuta().getLocalidadOrigen().getId());
				//Destino
				Util.seleccionarValorItemCombo(Localidad.class, cmbDestino,
										((TarifaFechaAbierta) itemTarifa.getValue()).getRuta().getLocalidadDestino().getId());

				Double tarifa = ((TarifaFechaAbierta) itemTarifa.getValue()).getMonto();
				if(tarifa!=null)
					dlbxTarifaFa.setValue(tarifa);
				else
					dlbxTarifaFa.setValue(0);

				dlbxTarifaFa.setFocus(true);

			}else{
				DlgMessage.information(Messages.getString("WndItinerario.information.SeleccionarTramo"));
			}
		}
	}

	public void onChangeServicio()throws Exception{
		if(!chkTarifaAbierta.isChecked()){
			//seleccionar el servicio
			Long seleccionado = Long.valueOf( ((Servicio)cmbServicio.getSelectedItem().getValue()).getId() );

			//recuperar el numero de pisos del servicio
			Servicio oServicio = ServiceLocator.getServicioManager().buscarPorId(seleccionado);
			onSelectPiso(oServicio);
		}
		else{

		}

	}

	public void onChangePiso()throws Exception{
		Long seleccionado = Long.valueOf( ((Servicio)cmbServicio.getSelectedItem().getValue()).getId() );

		//recuperar el numero de pisos del servicio
		Servicio oServicio = ServiceLocator.getServicioManager().buscarPorId(seleccionado);

		if(oServicio.getNumeroPisos() == 1 && cmbPiso.getSelectedIndex() == 2){
			DlgMessage.information("El servicio tiene un solo piso, no se puede seleccionar el piso 2");
			cmbPiso.setSelectedIndex(1);

		}
		else if(oServicio.getNumeroPisos() == 2 && cmbPiso.getSelectedIndex() == 0 ){
			dlbxTarifaP1.setDisabled(false);
			dlbxTarifaP2.setDisabled(false);

		}
		else if(oServicio.getNumeroPisos() == 2 && cmbPiso.getSelectedIndex() == 1 ){
			dlbxTarifaP1.setDisabled(false);
			dlbxTarifaP2.setDisabled(true);

		}
		else if(oServicio.getNumeroPisos() == 2 && cmbPiso.getSelectedIndex() == 2 ){
			dlbxTarifaP1.setDisabled(true);
			dlbxTarifaP2.setDisabled(false);

		}


	}

	public void onSelectTarifaFechaAbierta(){

		if(chkTarifaAbierta.isChecked()){
			inicializarControles(true);
			btnNuevaTarifa.setDisabled(true);
			btnCopiarTarifa.setDisabled(true);
			btnCancelar.setDisabled(false);
			btnGuardarTarifa.setDisabled(false);
			cmbServicio.setDisabled(false);
			cmbOrigen.setDisabled(false);
			cmbDestino.setDisabled(false);
			lblTarifafa.setVisible(true);
			dlbxTarifaFa.setVisible(true);
			dlbxTarifaFa.setDisabled(false);
		}
		else{
			inicializarControles(false);

		}

	}

	public void onClick_btnCopiarTarifaFA(){

	}

	public void onSelectBuscarTarifaFechaAbierta(){
		onClick_btnCancelar();
		if(chkTarifaFA.isChecked()){
			tabTarifa.setVisible(false);
			tabTarifaFA.setVisible(true);
			tabTarifa.setSelected(false);
			tabTarifaFA.setSelected(true);
		}
		else{
			tabTarifa.setVisible(true);
			tabTarifaFA.setVisible(false);
			tabTarifa.setSelected(true);
			tabTarifaFA.setSelected(false);


		}
		Util.limpiarListbox(lsbxRutas);
		Util.limpiarListbox(lsbxTarifaFA);
	}

	public void habilitarPisoUno(boolean estado){
//		cmbPiso.setDisabled(estado);
		cmbZona.setDisabled(estado);
		dlbxTarifaP2.setDisabled(estado);
		dlbxTarifaP2.setValue(0);
	}




	/*
	 * MAOE - 16/07/2020
	 * Metodos para el Toollbar
	 *
	 */
	/**
	 * Limpia los controles
	 */
	private void inicializarControles(boolean estado){
//		Util.limpiarListbox(lsbxRutas);
		cmbCanal.setSelectedIndex(0);
		cmbServicio.setSelectedIndex(0);
		cmbOrigen.setSelectedIndex(0);
		cmbDestino.setSelectedIndex(0);
		tmbxHoraPartida.setText("");
		cmbPiso.setSelectedIndex(0);
		cmbZona.setSelectedIndex(0);
		dlbxTarifaP1.setValue(0);
		dlbxTarifaP2.setValue(0);
		dlbxTarifaFa.setValue(0);
		String fecha = Util.DatetoString(new Date(), "yyyyMMdd");
		dtbxFecInicio.setConstraint("after "+fecha);
		dtbxFecFinal.setConstraint("after "+fecha);
		dtbxFecInicio.setValue(null);
		dtbxFecFinal.setValue(null);
		chkPorServicio.setChecked(false);
		chkTarifaAbierta.setChecked(chkTarifaAbierta.isChecked());



		cmbCanal.setDisabled(estado);
		cmbServicio.setDisabled(estado);
		cmbOrigen.setDisabled(estado);
		cmbDestino.setDisabled(estado);
		tmbxHoraPartida.setDisabled(estado);
		cmbPiso.setDisabled(estado);
		cmbZona.setDisabled(estado);
		dlbxTarifaP1.setDisabled(estado);
		dlbxTarifaP2.setDisabled(estado);
		dlbxTarifaFa.setDisabled(estado);
		dtbxFecInicio.setDisabled(estado);
		dtbxFecFinal.setDisabled(estado);
		chkPorServicio.setDisabled(estado);

		lblTarifafa.setVisible(false);
		dlbxTarifaFa.setVisible(false);

		tabTarifaFA.setVisible(false);

//		cmbTipoServicio.setSelectedIndex(0);
	}

	private void limpiarControlesProgramacion(){
		dtbxFecInicio.setValue(null);
		dtbxFecInicio.setDisabled(false);
		dtbxFecFinal.setValue(null);
		cmbCanal.setSelectedIndex(0);
		cmbServicio.setSelectedIndex(0);
		cmbOrigen.setSelectedIndex(0);
		cmbDestino.setSelectedIndex(0);
		tmbxHoraPartida.setText("");
		cmbPiso.setSelectedIndex(0);
		cmbZona.setSelectedIndex(0);
		dlbxTarifaP1.setValue(0);
		dlbxTarifaP2.setValue(0);
		dlbxTarifaFa.setValue(0);
		lblTarifafa.setVisible(false);
		dlbxTarifaFa.setVisible(false);
		chkPorServicio.setChecked(false);
		chkTarifaAbierta.setChecked(false);

	}


	/**
	 *
	 */
	public void onClick_btnNuevaTarifa(){
		try {
			limpiarControlesProgramacion();

			btnNuevaTarifa.setDisabled(true);
			btnCopiarTarifa.setDisabled(true);
			btnCancelar.setDisabled(false);
			btnGuardarTarifa.setDisabled(false);

			if(!chkTarifaFA.isChecked())
				inicializarControles(false);
			else{
				chkTarifaAbierta.setChecked(true);
				onSelectTarifaFechaAbierta();
			}

		} catch (Exception e) {
			DlgMessage.information(e.getMessage());
			e.printStackTrace();
		}
	}

	/**
	 *
	 */
	public void onClick_btnCopiarTarifa(){
		try{
			if(!chkTarifaFA.isChecked()){
				if(lsbxRutas.getItemCount()>0){
					CanalVenta canalVenta = null;
					try {
						canalVenta = ((TarifaRegular)lsbxRutas.getSelectedItem().getValue()).getTarifa().getCanalVenta();
						
					} catch (Exception e) {}
					
					//Valida el perfil del usuario para la edicion de una tarifa.
					if(canalVenta !=null){
						for(Comboitem comboitemCanal: cmbCanal.getItems()) {
							if(comboitemCanal.getValue()!=null && comboitemCanal.getValue() instanceof CanalVenta) {
								if(((CanalVenta)comboitemCanal.getValue()).getId().intValue()==canalVenta.getId().intValue() && comboitemCanal.isVisible()==false){
									DlgMessage.information(Messages.getString("wndTarifa.information.noPerfilEdicicon"));			
									return;
								}
							}
						}
					}else {
						DlgMessage.information(Messages.getString("wndTarifa.information.noPerfilEdicicon"));			
						return;
					}
					
					limpiarControlesProgramacion();
					btnNuevaTarifa.setDisabled(true);
					btnCopiarTarifa.setDisabled(true);
					btnCancelar.setDisabled(false);
					btnGuardarTarifa.setDisabled(false);
					inicializarControles(false);
					editarTarifa();
				}
				else
					DlgMessage.information("No se encontraron tarifas a copiar en la lista de busqueda.");
			}
			else{
				if(lsbxTarifaFA.getItemCount() > 0){
					limpiarControlesProgramacion();
					btnNuevaTarifa.setDisabled(true);
					btnCopiarTarifa.setDisabled(true);
					btnCancelar.setDisabled(false);
					btnGuardarTarifa.setDisabled(false);
//					inicializarControles(false);
					chkTarifaAbierta.setChecked(true);
					onSelectTarifaFechaAbierta();
					editarTarifa();
				}else
					DlgMessage.information("No se encontraron tarifas a copiar en la lista de busqueda.");
			}
		} catch(Exception e) {
			DlgMessage.information(e.getMessage());
			e.printStackTrace();
		}

	}

	/**
	 *
	 */
	public void onClick_btnCancelar(){
		try {
			limpiarControlesProgramacion();
			inicializarControles(true);
//			divNuevaTarifa.setVisible(false);
			btnNuevaTarifa.setDisabled(false);
			btnCopiarTarifa.setDisabled(false);
			btnCancelar.setDisabled(true);
			btnGuardarTarifa.setDisabled(true);
		} catch (Exception e) {
			e.printStackTrace();
			DlgMessage.error(e.getMessage());
		}
	}


	public boolean validar(){
		boolean bRet=true;

		return bRet;
	}


	/**
	 *  Realiza la busqueda de las rutas para la actualizaci�n las tarifas
	 * @throws Exception
	 */
	public void buscar() throws Exception{
		if(!chkTarifaFA.isChecked()){
			Util.limpiarListbox(lsbxRutas);
			Integer idCanalVenta=null;
			Integer idOrigen=null;
			Integer idDestino=null;
			Integer idServicio=null;
			Integer idTipoItinerario=null;
			String strFechaInicioBus, strFechaFinBus;
			String horaPartida=null;
			Integer con_o_sin_tarifa=null;


			strFechaInicioBus = Constantes.FORMAT_DATE.format(dtbxFecInicioBus.getValue());
			strFechaFinBus = Constantes.FORMAT_DATE.format(dtbxFecFinBus.getValue());


			if(cmbCanalBus.getSelectedItem().getValue() instanceof CanalVenta)
				idCanalVenta=((CanalVenta)cmbCanalBus.getSelectedItem().getValue()).getId();
			if(cmbServicioBus.getSelectedItem().getValue() instanceof Servicio)
				idServicio=((Servicio)cmbServicioBus.getSelectedItem().getValue()).getId();
			if(cmbOrigenBus.getSelectedItem().getValue() instanceof Localidad)
				idOrigen=((Localidad)cmbOrigenBus.getSelectedItem().getValue()).getId();
			if(cmbDestinoBus.getSelectedItem().getValue() instanceof Localidad)
				idDestino=((Localidad)cmbDestinoBus.getSelectedItem().getValue()).getId();
			if(tmbxHoraPartidaBus.getText()!="")
				horaPartida=tmbxHoraPartidaBus.getText();
			if(cmbTipoItinerario.getSelectedItem().getValue() instanceof TipoItinerario)
				idTipoItinerario=((TipoItinerario)cmbTipoItinerario.getSelectedItem().getValue()).getId();

			if(rdAmbos.isChecked())
				con_o_sin_tarifa=2;
			else if(rdSinTarifa.isChecked())
				con_o_sin_tarifa=0;
			else if(rdConTarifa.isChecked())
				con_o_sin_tarifa=1;

			List<TarifaRegular>lstTarifas = ServiceLocator.getTarifaRegularManager().listarTarifasPorServicios(
															idCanalVenta, idServicio, idOrigen, idDestino,
															idTipoItinerario, strFechaInicioBus, strFechaFinBus, horaPartida, con_o_sin_tarifa);

			/*
			 * 12/07/2020
			 * MAOE
			 * En esta parte recuperar las tarifas regulares
			 *
			 */

			/* Recupera las tarifas a fecha abierta */
	//		List<TarifaFechaAbierta>lstTarifaFA=ServiceLocator.getTarifaFechaAbierta().buscarTarifas(idOrigen, idDestino, idServicio);

			Listitem item=null;
			Listcell cell=null;

			for(TarifaRegular tarifaRegular: lstTarifas){
				Ruta ruta = tarifaRegular.getTarifa().getRuta();


				item=new Listitem();
				cell=new Listcell(tarifaRegular.getItinerario().getId().toString());


				cell.setStyle("font-size:11px !important");
				item.appendChild(cell);
				//CANAL DE VENTA SI ES NULL NO EXISTE TARIFA ASOCIADA
				String strCanal = tarifaRegular.getTarifa().getCanalVenta().getDenominacion();
				if(strCanal!=null && strCanal.length()>0){
					cell=new Listcell(tarifaRegular.getTarifa().getCanalVenta().getDenominacion());
				}
				else{
					cell=new Listcell("SIN TARIFA");
					cell.setStyle("font-size:11px !important; text-align: left; font-weight: bold;color:red");
				}
				item.appendChild(cell);
				//SERVICIO
				cell=new Listcell(tarifaRegular.getTarifa().getServicio().getDenominacion());
				item.appendChild(cell);
				//RUTA
				cell=new Listcell(ruta.getLocalidadOrigen().getDenominacion()+" - "+ruta.getLocalidadDestino().getDenominacion());
				item.appendChild(cell);

				GregorianCalendar gregCalendad= new GregorianCalendar();
				gregCalendad.setTime(tarifaRegular.getFechaTarifa());
				cell=new Listcell(gregCalendad.getDisplayName(Calendar.DAY_OF_WEEK, 2, Locale.getDefault())+" "+gregCalendad.get(Calendar.DAY_OF_MONTH)+
								 " de "+gregCalendad.getDisplayName(Calendar.MONTH, 2, Locale.getDefault())+" del "+gregCalendad.get(Calendar.YEAR));
				item.appendChild(cell);
				cell=new Listcell(tarifaRegular.getHoraPartida());
				item.appendChild(cell);
				Integer pisoBus = (tarifaRegular.getTarifa().getPisoBus()!=null ? tarifaRegular.getTarifa().getPisoBus() : null);
				String piso;
				if(pisoBus!=null){
					pisoBus = tarifaRegular.getTarifa().getPisoBus()+1;
					piso = Integer.toString(pisoBus)+"P";
				}
				else
					piso="";
				cell=new Listcell(piso);
				cell.setStyle("font-size:11px !important; text-align: right; font-weight: bold;color:red");
				cell.setTooltiptext("Piso del Bus.");
				item.appendChild(cell);
				//ZONA DEL BUS EN CASO TENGA MAS DE UNA
				cell=new Listcell(tarifaRegular.getTarifa().getZonaBus()!=null ? tarifaRegular.getTarifa().getZonaBus().toString() : "");
				item.appendChild(cell);
				//TARIFA DEL BUS
				cell=new Listcell(Util.toNumberFormat(tarifaRegular.getMonto(), 2));
				cell.setStyle("font-size:11px !important; text-align: right; font-weight: bold;color:blue");
				cell.setTooltiptext("Tarifa para la Venta.");
				item.appendChild(cell);

				item.setValue(tarifaRegular);
				lsbxRutas.appendChild(item);
			}
		}
		//Busca las tarifas en fecha abierta
		else
		{
			Util.limpiarListbox(lsbxTarifaFA);

			Integer idOrigen=null;
			Integer idDestino=null;
			Integer idServicio=null;

			if(cmbServicioBus.getSelectedItem().getValue() instanceof Servicio)
				idServicio=((Servicio)cmbServicioBus.getSelectedItem().getValue()).getId();
			if(cmbOrigenBus.getSelectedItem().getValue() instanceof Localidad)
				idOrigen=((Localidad)cmbOrigenBus.getSelectedItem().getValue()).getId();
			if(cmbDestinoBus.getSelectedItem().getValue() instanceof Localidad)
				idDestino=((Localidad)cmbDestinoBus.getSelectedItem().getValue()).getId();

			/* Recupera las tarifas a fecha abierta */
			List<TarifaFechaAbierta>lstTarifasFA = ServiceLocator.getTarifaFechaAbierta().listarTarifasFA(idOrigen, idDestino, idServicio);

			/*
			 * 12/07/2020
			 * MAOE
			 * En esta parte recuperar las tarifas regulares
			 *
			 */

			Listitem item=null;
			Listcell cell=null;

			for(TarifaFechaAbierta tarifaFA: lstTarifasFA){
				Ruta ruta = tarifaFA.getRuta();


				item=new Listitem();
				long idTarifa = tarifaFA.getId();
				cell=new Listcell(Long.toString(idTarifa));


				cell.setStyle("font-size:11px !important");
				item.appendChild(cell);
				//CANAL DE VENTA SI ES NULL NO EXISTE TARIFA ASOCIADA
				String strCanal = "COUNTER";
				cell=new Listcell(strCanal);

				item.appendChild(cell);
				//SERVICIO
				cell=new Listcell(tarifaFA.getServicio().getDenominacion());
				item.appendChild(cell);
				//RUTA
				cell=new Listcell(ruta.getOrigen()+" - "+ruta.getDestino());
				item.appendChild(cell);

				//TARIFA DEL BUS
				cell=new Listcell(Util.toNumberFormat(tarifaFA.getMonto(), 2));
				cell.setStyle("font-size:11px !important; text-align: right; font-weight: bold;color:blue");
				cell.setTooltiptext("Tarifa para la Venta.");
				item.appendChild(cell);

				GregorianCalendar gregCalendarA = new GregorianCalendar();
				gregCalendarA.setTime(tarifaFA.getFechaActivacion());
				cell=new Listcell(gregCalendarA.getDisplayName(Calendar.DAY_OF_WEEK, 2, Locale.getDefault())+" "+gregCalendarA.get(Calendar.DAY_OF_MONTH)+
								 " de "+gregCalendarA.getDisplayName(Calendar.MONTH, 2, Locale.getDefault())+" del "+gregCalendarA.get(Calendar.YEAR));
				item.appendChild(cell);

				GregorianCalendar gregCalendarC = new GregorianCalendar();
				gregCalendarC.setTime(tarifaFA.getFechaCaducidad());
				cell=new Listcell(gregCalendarC.getDisplayName(Calendar.DAY_OF_WEEK, 2, Locale.getDefault())+" "+gregCalendarC.get(Calendar.DAY_OF_MONTH)+
								 " de "+gregCalendarC.getDisplayName(Calendar.MONTH, 2, Locale.getDefault())+" del "+gregCalendarC.get(Calendar.YEAR));
				item.appendChild(cell);

				item.setValue(tarifaFA);
				lsbxTarifaFA.appendChild(item);
			}
		}
	}


	/**
	 *
	 */
	public void onClick_btnGuardarTarifa(){
		try {
			if(!chkTarifaAbierta.isChecked()){

				final Integer idCanal, idServicio, idOrigen, idDestino, zona;
				Long seleccionado;
				Servicio oServicio;

				if(cmbServicio.getSelectedIndex()==0){
					DlgMessage.information("Debe de ingresar el Servicio para la Tarifa. ");
					cmbServicio.focus();
					return;
				}else{
					seleccionado = Long.valueOf(((Servicio)cmbServicio.getSelectedItem().getValue()).getId());
					oServicio = ServiceLocator.getServicioManager().buscarPorId(seleccionado);
				}

				flag=0;
				ambosPisos=0;

				//Si las fechas inicial y final son vacias
				if(dtbxFecInicio.getValue()==null || dtbxFecFinal.getValue()==null ||
					(dtbxFecInicio.getValue()==null && dtbxFecFinal.getValue()==null)){
					DlgMessage.information("Debe ingresar las fechas de inicio y fin para la creacion de las tarifas.");
					return;
				}
				//Si la fecha Final es menor que la fecha mayor
				if (dtbxFecInicio.getValue().getTime()>dtbxFecFinal.getValue().getTime()){
					DlgMessage.information(Messages.getString("wndProgramacionTarifa.information.fechaDesdeMayor"), dtbxFecFinal);
					return;
				}
	//			Validando el tarifario ingresado
				else if(cmbCanal.getSelectedIndex()==0){
					DlgMessage.information("Debe seleccionar un Canal de Venta para la tarifa.");
					cmbCanal.focus();
					return;
				}else if(cmbOrigen.getSelectedIndex()==0){
					DlgMessage.information("Debe de ingresar el Origen para la Tarifa. ");
					cmbOrigen.focus();
					return;
				}else if(cmbDestino.getSelectedIndex()==0 ){
					DlgMessage.information("Debe de ingresar el Destino. para la Tarifa.");
					cmbDestino.focus();
					return;
				}else if(tmbxHoraPartida.getText().isEmpty() && !chkPorServicio.isChecked()){
					DlgMessage.information("Debe de ingresar la hora de partida del servicio");
					tmbxHoraPartida.focus();
					return;
				}else if(cmbPiso.getSelectedIndex()==0 && (dlbxTarifaP1.getValue()==0 && dlbxTarifaP2.getValue()>0)){
					DlgMessage.information("Debe de ingresar el Piso del bus al cual se le creara la Tarifa.");
					cmbPiso.focus();
					return;
				}else if(cmbPiso.getSelectedIndex()==0 && (dlbxTarifaP1.getValue()>0 && dlbxTarifaP2.getValue()==0)){
					DlgMessage.information("Debe de ingresar el Piso del bus al cual se le creara la Tarifa.");
					cmbPiso.focus();
					return;
				}else if(cmbPiso.getSelectedIndex()==0 && (dlbxTarifaP1.getValue()==0 && dlbxTarifaP2.getValue()==0)){
					DlgMessage.information("Debe de ingresar el Piso del bus al cual se le creara la Tarifa.");
					cmbPiso.focus();
					return;
				}else if(cmbPiso.getSelectedIndex()==1 && dlbxTarifaP1.getValue()==0){
					DlgMessage.information("Debe de ingresar un monto para crear la Tarifa.");
					dlbxTarifaP1.focus();
					return;
				}else if(cmbPiso.getSelectedIndex()==1 && dlbxTarifaP1.getValue()>0 && dlbxTarifaP1.getValue()<Constantes.TARIFA_MINIMA){
					DlgMessage.information("El monto m�nimo para crear una tarifa es de "+ Constantes.TARIFA_MINIMA);
					dlbxTarifaP1.focus();
					return;
				}else if(cmbPiso.getSelectedIndex()==2 && dlbxTarifaP2.getValue()==0 && oServicio.getNumeroPisos()==2){
					DlgMessage.information("Debe de ingresar el monto de la Tarifa del piso 2.");
					dlbxTarifaP2.focus();
					return;
				}else if(cmbPiso.getSelectedIndex()==2 && dlbxTarifaP2.getValue()>0 && dlbxTarifaP2.getValue()<Constantes.TARIFA_MINIMA && oServicio.getNumeroPisos()==2){
					DlgMessage.information("El monto m�nimo para crear una tarifa es de "+ Constantes.TARIFA_MINIMA);
					cmbPiso.focus();
					return;
				}else if(cmbZona.getSelectedIndex()==0){
					DlgMessage.information("Debe de ingresar la zona del bus para la Tarifa.");
					cmbZona.focus();
					return;
				}else if(dtbxFecInicio.getValue()==null){
					DlgMessage.information("Debe ingresar la Fecha de Inicio para la creacion de las Tarifas.");
					dtbxFecInicio.focus();
					return;
				}else if(dtbxFecFinal.getValue()==null){
					DlgMessage.information("Debe de ingresar la fecha final hasta cuando se creara las tarifas.");
					dtbxFecFinal.focus();
					return;
	//			}else if(dlbxTarifaP1.getValue()==0){
	//				DlgMessage.information("Debe de ingresar el monto de la Tarifa del piso 1.");
	//				dlbxTarifaP1.focus();
	//				return;
				}else if(dlbxTarifaP2.getValue()==0 && oServicio.getNumeroPisos()>2){
					DlgMessage.information("Debe de ingresar el monto de la Tarifa del piso 2.");
					dlbxTarifaP2.focus();
					return;
				}else if(dlbxTarifaP2.getValue()>0 && dlbxTarifaP2.getValue()<Constantes.TARIFA_MINIMA && oServicio.getNumeroPisos()>2){
					DlgMessage.information("El monto m�nimo para crear una tarifa es de "+ Constantes.TARIFA_MINIMA);
					dlbxTarifaP2.focus();
					return;
				}else{
					
					//Si selecicono ambos pisos para actualizar marcamos el mensaje
					//MAOE 30/12/2022 Para validar el monto de tarifa minimo
					if(cmbPiso.getSelectedIndex()==0 && (dlbxTarifaP1.getValue()>0 && dlbxTarifaP2.getValue()>0)){
						if(dlbxTarifaP1.getValue()>0 && dlbxTarifaP1.getValue()<Constantes.TARIFA_MINIMA) {
							DlgMessage.information("El monto m�nimo para crear una tarifa es de "+ Constantes.TARIFA_MINIMA);
							dlbxTarifaP1.focus();
							return;
						}else if(dlbxTarifaP2.getValue()>0 && dlbxTarifaP2.getValue()<Constantes.TARIFA_MINIMA){
							DlgMessage.information("El monto m�nimo para crear una tarifa es de "+ Constantes.TARIFA_MINIMA);
							dlbxTarifaP2.focus();
							return;
						}else
							ambosPisos=1;
					}


					idCanal = ((CanalVenta)cmbCanal.getSelectedItem().getValue()).getId();
					idServicio = ((Servicio)cmbServicio.getSelectedItem().getValue()).getId();
					idOrigen = ((Localidad)cmbOrigen.getSelectedItem().getValue()).getId();
					idDestino = ((Localidad)cmbDestino.getSelectedItem().getValue()).getId();
					zona = cmbZona.getSelectedIndex();
					String strHoraServicio = tmbxHoraPartida.getText();
					String fechaInicio=Constantes.FORMAT_DATE.format(dtbxFecInicio.getValue());
					String fechaFinal=Constantes.FORMAT_DATE.format(dtbxFecFinal.getValue());
					Integer porServicio = (chkPorServicio.isChecked() ? 1 : 0);
					Integer piso=0;
					if(cmbPiso.getSelectedIndex()==1)
						piso = 0;
					else if(cmbPiso.getSelectedIndex()==2)
						piso=1;


	//				BUSCAR LAS TARIFAS DE ACUERDO A LO QUE SE VA A INGRESAR
					String tarifasYservicios = ServiceLocator.getTarifaRegularManager().buscarCantidadTarifasAReemplazar(
							idCanal,
							idServicio,
							idOrigen,
							idDestino,
							piso,
							zona,
							fechaInicio,
							fechaFinal,
							strHoraServicio,
							porServicio);

					String[] cantidades = tarifasYservicios.split(",");
					String strMensaje = "No se encontraron Tarifas ni servicios asociados a la configuracion ingresada!.\nRealmente desea continuar con la creacion de tarifas?";
					Integer cantidadTarifas = Integer.valueOf(cantidades[0]);
					Integer cantidadServicios = Integer.valueOf(cantidades[1]);

//					//Si selecicono ambos pisos para actualizar marcamos el mensaje
//					if(cmbPiso.getSelectedIndex()==0 && (dlbxTarifaP1.getValue()>0 && dlbxTarifaP2.getValue()>0)){
//						ambosPisos=1;
//					}



					/*
					 * MAOE: 18/07/2020
					 *Valor 0: Hay servicios pero no tarifas
					 *Valor 1: Hay tarifas pero no hay servicios programados
					 *Valor 2: Hay tarifas y servicios y se reemplazaran las tarifas
					 */
					if(ambosPisos==1)
						strMensaje = "Ha seleccionado actualizar la tarifa de los dos pisos al mismo tiempo.\n";
					else
						strMensaje="";

					if(cantidadTarifas == 0 && cantidadServicios > 0){
						strMensaje += "Se va a iniciar el proceso de creacion de tarifas.\nEste proceso puede tardar varios minutos! \n �Realmente desea continuar?";
					}else if(cantidadTarifas > 0 && cantidadServicios == 0){
						flag=1;
						strMensaje += "No se encontraron Servicios para las fechas y destinos indicadas. \nRealmente desea crear las tarifas seg�n la configuraci�n ingresada?";
					}else if(cantidadTarifas > 0 && cantidadServicios > 0){
						flag=2;
						strMensaje += "Se encontraron " + cantidades[0] + " de Tarifas, este proceso reemplazara estas tarifas! \n �Realmente desea continuar?";
					}


					Messagebox.show(strMensaje, DlgMessage.NOMBREAPLICACION, DlgMessage.BTN_YESNO, Messagebox.QUESTION,DlgMessage.BTN_DEFAULT_NO, new EventListener<Event>() {
						@Override
						public void onEvent(Event e) throws Exception{
							try{
								if(e.getName().equals("onYes")){
									//Guarda la tarifa

									long cantidadDias=((dtbxFecFinal.getValue().getTime()+Constantes.MILISEGUNDOS_X_DIA)-dtbxFecInicio.getValue().getTime());
									Date fechaPartida=new Date();
									fechaPartida.setTime(dtbxFecInicio.getValue().getTime());
									int dias = (int) (cantidadDias /  Constantes.MILISEGUNDOS_X_DIA);
									//Si es mas de un dia
									if(dias>1){
										Integer pisoElegido;
										if(ambosPisos == 1)
											pisoElegido=null;
										else{
											pisoElegido = cmbPiso.getSelectedIndex();
											pisoElegido = pisoElegido-1;
										}

										//Preparamos la Ruta
										Ruta ruta = new Ruta();
										List<Ruta>lstRuta=new ArrayList<>();
										TreeMap<String, Object>criterioBusqueda=new TreeMap<>();
										criterioBusqueda.put("localidadOrigen", new Localidad(idOrigen));
										criterioBusqueda.put("localidadDestino", new Localidad(idDestino));
										criterioBusqueda.put("estadoRegistro", Constantes.VALUE_ACTIVO);
										lstRuta=ServiceLocator.getRutaManager().buscarPorX(criterioBusqueda, null);
										ruta = lstRuta.get(0);

										String strFechatarifa=Constantes.FORMAT_DATE.format(fechaPartida);

										//Consultamos para extraer la cabecera
										List <Tarifa> lstTarifa = ServiceLocator.getTarifaManager().buscarTarifa(
												idCanal, idServicio, idOrigen, idDestino, pisoElegido, zona);
										//Si existe cabecera
										if(lstTarifa.size()>0){
											//Por cada dia hacer
											for (int x=0 ; x < dias; x ++){
												for(Tarifa tarifa: lstTarifa){
													//Recuperamos el detalle de las cabeceras de tarifa
													List<TarifaRegular> lstTarifaRegular=null;
														lstTarifaRegular = ServiceLocator.getTarifaRegularManager().buscarTarifasAReemplazar(
																	tarifa.getCanalVenta().getId(),
																	tarifa.getServicio().getId(),
																	idOrigen, idDestino, tarifa.getPisoBus(), zona,
																	strFechatarifa, strFechatarifa,
																	tmbxHoraPartida.getText(), (chkPorServicio.isChecked() ? 1 : 0));
	//												Si hay Tarifas a reemplazar moverlas y volverlas a insertar segun lo ingresado
													if(lstTarifaRegular.size()>0){
														for(TarifaRegular otarifaRegular: lstTarifaRegular){
															//Mover las tarifas actuales a la nueva tabla para auditoria
															TarifaRegularAud tarifaRegularAud = new TarifaRegularAud();
															tarifaRegularAud.setidTarifa(otarifaRegular.getTarifa().getId());
															tarifaRegularAud.setIdTarreg(otarifaRegular.getId());
															tarifaRegularAud.setIdCanven(otarifaRegular.getTarifa().getCanalVenta().getId());
															tarifaRegularAud.setIdRuta(otarifaRegular.getTarifa().getRuta().getId());
															tarifaRegularAud.setIdServicio(otarifaRegular.getTarifa().getServicio().getId());
															tarifaRegularAud.setFechaTarifa(otarifaRegular.getFechaTarifa());
															tarifaRegularAud.setHoraPartida(otarifaRegular.getHoraPartida());
															tarifaRegularAud.setPisoBus(otarifaRegular.getTarifa().getPisoBus());
															tarifaRegularAud.setZonaBus(otarifaRegular.getTarifa().getZonaBus());
															tarifaRegularAud.setMonto(otarifaRegular.getMonto());
															tarifaRegularAud.setEstadoRegistro(Constantes.VALUE_ACTIVO);
															UtilData.auditarRegistro(tarifaRegularAud, getUsuario(), Executions.getCurrent());
															ServiceLocator.getTarifaRegularAudManager().guardar(tarifaRegularAud);

															//Eliminar los registros movidos
															ServiceLocator.getTarifaRegularManager().delete(otarifaRegular.getId());
														}

															//Insertar lo snuevos registros de tarifa
															TarifaRegular tarifaRegular = new TarifaRegular();
															Date sFechaTarifa = new Date();
															sFechaTarifa.setTime(fechaPartida.getTime());

															tarifaRegular.setTarifa(tarifa);
															tarifaRegular.setFechaTarifa(sFechaTarifa);
															tarifaRegular.setHoraPartida(tmbxHoraPartida.getText());
															if(tarifa.getPisoBus()==0)
																tarifaRegular.setMonto(dlbxTarifaP1.getValue());
															else
																tarifaRegular.setMonto(dlbxTarifaP2.getValue());
															tarifaRegular.setEstadoRegistro(Constantes.VALUE_ACTIVO);
															UtilData.auditarRegistro(tarifaRegular, getUsuario(), Executions.getCurrent());
															ServiceLocator.getTarifaRegularManager().guardar(tarifaRegular);

													}
	//												Si no hay tarifas a reemplazar hay que insertarlas
													else{

														//Insertar lo snuevos registros de tarifa
														TarifaRegular tarifaRegular = new TarifaRegular();
														Date sFechaTarifa = new Date();
														sFechaTarifa.setTime(fechaPartida.getTime());
														tarifaRegular.setTarifa(tarifa);
														tarifaRegular.setFechaTarifa(sFechaTarifa);
														tarifaRegular.setHoraPartida(tmbxHoraPartida.getText());
														if(tarifa.getPisoBus()==0)
															tarifaRegular.setMonto(dlbxTarifaP1.getValue());
														else
															tarifaRegular.setMonto(dlbxTarifaP2.getValue());
														tarifaRegular.setEstadoRegistro(Constantes.VALUE_ACTIVO);
														UtilData.auditarRegistro(tarifaRegular, getUsuario(), Executions.getCurrent());
														ServiceLocator.getTarifaRegularManager().guardar(tarifaRegular);

													}
												}

												fechaPartida.setTime(fechaPartida.getTime() + Constantes.MILISEGUNDOS_X_DIA );
												strFechatarifa=Constantes.FORMAT_DATE.format(fechaPartida);
											}
										}
										//Si no hay cabecera
										else{
											//Guardar la cabecera
											Tarifa cabeceraPiso1=null, cabeceraPiso2=null;
											//Si son dos pisos
											if(pisoElegido==null){
												for(int i=0; i<2; i++){
													Tarifa tarifa = new Tarifa();
													CanalVenta canalVenta = new CanalVenta();
													canalVenta.setId(idCanal);
													Servicio servicio = new Servicio();
													servicio.setId(idServicio);
													tarifa.setCanalVenta(canalVenta);
													tarifa.setServicio(servicio);
													tarifa.setRuta(ruta);
													tarifa.setPisoBus(i);
													tarifa.setZonaBus(zona);
													tarifa.setEstadoRegistro(Constantes.VALUE_ACTIVO);
													UtilData.auditarRegistro(tarifa,  getUsuario(), Executions.getCurrent());
													ServiceLocator.getTarifaManager().guardar(tarifa);
													if(i==0)
														cabeceraPiso1 = (Tarifa)tarifa.clone();
													else
														cabeceraPiso2 = (Tarifa)tarifa.clone();
												}
												//Obtener las cabeceras de las tarifas
												Tarifa tarifa = null;
												//Por cada dia hacer
												for (int x=0 ; x < dias; x ++){

													for(int i=0; i<2; i++){
														//Insertar el detalle
														TarifaRegular tarifaRegular = new TarifaRegular();
														Date sFechaTarifa = new Date();
														sFechaTarifa.setTime(fechaPartida.getTime());

														if(i==0)
															tarifa = cabeceraPiso1;
														else
															tarifa = cabeceraPiso2;
														tarifaRegular.setTarifa(tarifa);
														tarifaRegular.setFechaTarifa(sFechaTarifa);
														tarifaRegular.setHoraPartida(tmbxHoraPartida.getText());
														if(tarifa.getPisoBus()==0)
															tarifaRegular.setMonto(dlbxTarifaP1.getValue());
														else
															tarifaRegular.setMonto(dlbxTarifaP2.getValue());
														tarifaRegular.setEstadoRegistro(Constantes.VALUE_ACTIVO);
														UtilData.auditarRegistro(tarifaRegular, getUsuario(), Executions.getCurrent());
														ServiceLocator.getTarifaRegularManager().guardar(tarifaRegular);
													}
													fechaPartida.setTime(fechaPartida.getTime() + Constantes.MILISEGUNDOS_X_DIA );
												}
											}
											//Si es un solo piso
											else{
												Tarifa tarifa = new Tarifa();
												CanalVenta canalVenta = new CanalVenta();
												canalVenta.setId(idCanal);
												Servicio servicio = new Servicio();
												servicio.setId(idServicio);
												tarifa.setCanalVenta(canalVenta);
												tarifa.setServicio(servicio);
												tarifa.setRuta(ruta);
												tarifa.setPisoBus(pisoElegido);
												tarifa.setZonaBus(zona);
												tarifa.setEstadoRegistro(Constantes.VALUE_ACTIVO);
												UtilData.auditarRegistro(tarifa,  getUsuario(), Executions.getCurrent());
												ServiceLocator.getTarifaManager().guardar(tarifa);

												//Insertar el detalle
												for (int x=0 ; x < dias; x ++){
													TarifaRegular tarifaRegular = new TarifaRegular();
													Date sFechaTarifa = new Date();
													sFechaTarifa.setTime(fechaPartida.getTime());

													tarifaRegular.setTarifa(tarifa);
													tarifaRegular.setFechaTarifa(sFechaTarifa);
													tarifaRegular.setHoraPartida(tmbxHoraPartida.getText());
													if(tarifa.getPisoBus()==0)
														tarifaRegular.setMonto(dlbxTarifaP1.getValue());
													else
														tarifaRegular.setMonto(dlbxTarifaP2.getValue());
													tarifaRegular.setEstadoRegistro(Constantes.VALUE_ACTIVO);
													UtilData.auditarRegistro(tarifaRegular, getUsuario(), Executions.getCurrent());
													ServiceLocator.getTarifaRegularManager().guardar(tarifaRegular);

													fechaPartida.setTime(fechaPartida.getTime() + Constantes.MILISEGUNDOS_X_DIA );
												}
											}

										}
									//Cuando es solamente una D�a
									}else{
										Integer pisoElegido;
										if(ambosPisos == 1)
											pisoElegido = null;
										else{
											pisoElegido = cmbPiso.getSelectedIndex();

											//Los pisos se numeran entre 0 y 1
											pisoElegido = pisoElegido-1;
										}

										//Preparamos la Ruta
										Ruta ruta = new Ruta();
										List<Ruta>lstRuta=new ArrayList<>();
										TreeMap<String, Object>criterioBusqueda=new TreeMap<>();
										criterioBusqueda.put("localidadOrigen", new Localidad(idOrigen));
										criterioBusqueda.put("localidadDestino", new Localidad(idDestino));
										criterioBusqueda.put("estadoRegistro", Constantes.VALUE_ACTIVO);
										lstRuta=ServiceLocator.getRutaManager().buscarPorX(criterioBusqueda, null);
										ruta = lstRuta.get(0);

										String strFechatarifa=Constantes.FORMAT_DATE.format(fechaPartida);

										//Consultamos para extraer la cabecera
										List <Tarifa> lstTarifa = ServiceLocator.getTarifaManager().buscarTarifa(
												idCanal, idServicio, idOrigen, idDestino, pisoElegido, zona);
										//Si hay cabecera la iteramos para obtener las tarifas actuales
										if(lstTarifa.size()>0){
											for(Tarifa tarifa: lstTarifa){
													//Recuperamos el detalle de las cabeceras de tarifa
													List<TarifaRegular> lstTarifaRegular = ServiceLocator.getTarifaRegularManager().buscarTarifasAReemplazar(
																	tarifa.getCanalVenta().getId(),
																	tarifa.getServicio().getId(),
																	idOrigen, idDestino, tarifa.getPisoBus(), zona,
																	strFechatarifa, strFechatarifa,
																	tmbxHoraPartida.getText(), (chkPorServicio.isChecked() ? 1 : 0));
													//Si hay Tarifas a reemplazar moverlas y volverlas a insertar segun lo ingresado
													if(lstTarifaRegular.size()>0){
														for(TarifaRegular otarifaRegular: lstTarifaRegular){
															//Mover las tarifas actuales a la nueva tabla para auditoria
															TarifaRegularAud tarifaRegularAud = new TarifaRegularAud();
															tarifaRegularAud.setidTarifa(otarifaRegular.getTarifa().getId());
															tarifaRegularAud.setIdTarreg(otarifaRegular.getId());
															tarifaRegularAud.setIdCanven(otarifaRegular.getTarifa().getCanalVenta().getId());
															tarifaRegularAud.setIdRuta(otarifaRegular.getTarifa().getRuta().getId());
															tarifaRegularAud.setIdServicio(otarifaRegular.getTarifa().getServicio().getId());
															tarifaRegularAud.setFechaTarifa(otarifaRegular.getFechaTarifa());
															tarifaRegularAud.setHoraPartida(otarifaRegular.getHoraPartida());
															tarifaRegularAud.setPisoBus(otarifaRegular.getTarifa().getPisoBus());
															tarifaRegularAud.setZonaBus(otarifaRegular.getTarifa().getZonaBus());
															tarifaRegularAud.setMonto(otarifaRegular.getMonto());
															tarifaRegularAud.setEstadoRegistro(Constantes.VALUE_ACTIVO);
															UtilData.auditarRegistro(tarifaRegularAud, getUsuario(), Executions.getCurrent());
															ServiceLocator.getTarifaRegularAudManager().guardar(tarifaRegularAud);

															//Eliminar los registros movidos
															ServiceLocator.getTarifaRegularManager().delete(otarifaRegular.getId());

															//Insertar lo snuevos registros de tarifa
															TarifaRegular tarifaRegular = new TarifaRegular();
															tarifaRegular.setTarifa(tarifa);
															tarifaRegular.setFechaTarifa(fechaPartida);
															tarifaRegular.setHoraPartida(tmbxHoraPartida.getText());
															if(tarifa.getPisoBus()==0)
																tarifaRegular.setMonto(dlbxTarifaP1.getValue());
															else
																tarifaRegular.setMonto(dlbxTarifaP2.getValue());
															tarifaRegular.setEstadoRegistro(Constantes.VALUE_ACTIVO);
															UtilData.auditarRegistro(tarifaRegular, getUsuario(), Executions.getCurrent());
															ServiceLocator.getTarifaRegularManager().guardar(tarifaRegular);
														}
													}
													//Si no hay tarifas a reemplazar hay que insertarlas
													else{

														//Insertar lo snuevos registros de tarifa
														TarifaRegular tarifaRegular = new TarifaRegular();
														tarifaRegular.setTarifa(tarifa);
														tarifaRegular.setFechaTarifa(fechaPartida);
														tarifaRegular.setHoraPartida(tmbxHoraPartida.getText());
														if(tarifa.getPisoBus()==0)
															tarifaRegular.setMonto(dlbxTarifaP1.getValue());
														else
															tarifaRegular.setMonto(dlbxTarifaP2.getValue());
														tarifaRegular.setEstadoRegistro(Constantes.VALUE_ACTIVO);
														UtilData.auditarRegistro(tarifaRegular, getUsuario(), Executions.getCurrent());
														ServiceLocator.getTarifaRegularManager().guardar(tarifaRegular);

													}
											}
										}
										//Si no hay cabecera quiere decir que las tarifas son nuevas
										else{
											//Guardar la cabecera
											//Si son dos pisos
											if(pisoElegido==null){
												for(int i=0; i<2; i++){
													Tarifa tarifa = new Tarifa();
													CanalVenta canalVenta = new CanalVenta();
													canalVenta.setId(idCanal);
													Servicio servicio = new Servicio();
													servicio.setId(idServicio);
													tarifa.setCanalVenta(canalVenta);
													tarifa.setServicio(servicio);
													tarifa.setRuta(ruta);
													tarifa.setPisoBus(i);
													tarifa.setZonaBus(zona);
													tarifa.setEstadoRegistro(Constantes.VALUE_ACTIVO);
													UtilData.auditarRegistro(tarifa,  getUsuario(), Executions.getCurrent());
													ServiceLocator.getTarifaManager().guardar(tarifa);

													//Insertar el detalle
													TarifaRegular tarifaRegular = new TarifaRegular();
													tarifaRegular.setTarifa(tarifa);
													tarifaRegular.setFechaTarifa(fechaPartida);
													tarifaRegular.setHoraPartida(tmbxHoraPartida.getText());
													if(tarifa.getPisoBus()==0)
														tarifaRegular.setMonto(dlbxTarifaP1.getValue());
													else
														tarifaRegular.setMonto(dlbxTarifaP2.getValue());
													tarifaRegular.setEstadoRegistro(Constantes.VALUE_ACTIVO);
													UtilData.auditarRegistro(tarifaRegular, getUsuario(), Executions.getCurrent());
													ServiceLocator.getTarifaRegularManager().guardar(tarifaRegular);
												}
											}
											//Si es un solo piso
											else{
												Tarifa tarifa = new Tarifa();
												CanalVenta canalVenta = new CanalVenta();
												canalVenta.setId(idCanal);
												Servicio servicio = new Servicio();
												servicio.setId(idServicio);
												tarifa.setCanalVenta(canalVenta);
												tarifa.setServicio(servicio);
												tarifa.setRuta(ruta);
												tarifa.setPisoBus(pisoElegido);
												tarifa.setZonaBus(zona);
												tarifa.setEstadoRegistro(Constantes.VALUE_ACTIVO);
												UtilData.auditarRegistro(tarifa,  getUsuario(), Executions.getCurrent());
												ServiceLocator.getTarifaManager().guardar(tarifa);

												//Insertar el detalle
												TarifaRegular tarifaRegular = new TarifaRegular();
												tarifaRegular.setTarifa(tarifa);
												tarifaRegular.setFechaTarifa(fechaPartida);
												tarifaRegular.setHoraPartida(tmbxHoraPartida.getText());
												if(tarifa.getPisoBus()==0)
													tarifaRegular.setMonto(dlbxTarifaP1.getValue());
												else
													tarifaRegular.setMonto(dlbxTarifaP2.getValue());
												tarifaRegular.setEstadoRegistro(Constantes.VALUE_ACTIVO);
												UtilData.auditarRegistro(tarifaRegular, getUsuario(), Executions.getCurrent());
												ServiceLocator.getTarifaRegularManager().guardar(tarifaRegular);
											}

										}

									}

									onClick_btnCancelar();
									buscar();

								}
							} catch (Exception e2) {
								e2.printStackTrace();
								DlgMessage.error(e2.getMessage());
							}

						}
					});
				}
			}
			//Si se trata de tarifa a fecha abierta
			else{

				final Integer idServicio, idOrigen, idDestino;


				if(cmbServicio.getSelectedIndex()==0){
					DlgMessage.information("Debe de ingresar el Servicio para la Tarifa de Fecha Abierta. ");
					cmbServicio.focus();
					return;
				}else if(cmbOrigen.getSelectedIndex()==0){
					DlgMessage.information("Debe de ingresar el Origen para la Tarifa de Fecha Abierta. ");
					cmbOrigen.focus();
					return;
				}else if(cmbDestino.getSelectedIndex()==0 ){
					DlgMessage.information("Debe de ingresar el Destino. para la Tarifa de Fecha Abierta.");
					cmbDestino.focus();
					return;
				}else if(dlbxTarifaFa.getValue() == 0){
					DlgMessage.information("Debe de ingresar el monto de la Tarifa de Fecha Abierta");
					dlbxTarifaFa.focus();
					return;
				}else if(dlbxTarifaFa.getValue() > 0 && dlbxTarifaFa.getValue() < Constantes.TARIFA_MINIMA){
					DlgMessage.information("El monto minimo para crear una tarifa es de " + Constantes.TARIFA_MINIMA);
					dlbxTarifaFa.focus();
					return;
				}
				//Procedemos con el guardado de la tarifa
				else{

					idServicio = ((Servicio)cmbServicio.getSelectedItem().getValue()).getId();
					idOrigen = ((Localidad)cmbOrigen.getSelectedItem().getValue()).getId();
					idDestino = ((Localidad)cmbDestino.getSelectedItem().getValue()).getId();

					String strOrigen, strDestino, strServicio;
					strServicio = ((Servicio)cmbServicio.getSelectedItem().getValue()).getDenominacion();
					strOrigen = ((Localidad)cmbOrigen.getSelectedItem().getValue()).getDenominacion();
					strDestino = ((Localidad)cmbDestino.getSelectedItem().getValue()).getDenominacion();

					/*
					 * MAOE: 18/07/2020
					 *Valor 0: Hay servicios pero no tarifas
					 *Valor 1: Hay tarifas pero no hay servicios programados
					 *Valor 2: Hay tarifas y servicios y se reemplazaran las tarifas
					 */
					String strMensaje = "Se va a registrar la tarifa de Fecha Abierta para el Servicio "+ strServicio +" \nen la ruta "+ strOrigen + " - " + strDestino + ".\n �Realmente desea continuar?";
//
//
					Messagebox.show(strMensaje, DlgMessage.NOMBREAPLICACION, DlgMessage.BTN_YESNO, Messagebox.QUESTION,DlgMessage.BTN_DEFAULT_NO, new EventListener<Event>() {
						@Override
						public void onEvent(Event e) throws Exception{
							try{
								if(e.getName().equals("onYes")){
									//Guarda la tarifa
									Double nuevaTarifa=dlbxTarifaFa.getValue();

									TarifaFechaAbierta tarifaFechaAbierta=null;
//									TarifaFechaAbierta oldTarifaFechaAbierta;
									Date fechaActual=new Date();

									//Preparamos la Ruta
									Ruta ruta = new Ruta();
									List<Ruta>lstRuta=new ArrayList<>();
									TreeMap<String, Object>criterioBusqueda=new TreeMap<>();
									criterioBusqueda.put("localidadOrigen", new Localidad(idOrigen));
									criterioBusqueda.put("localidadDestino", new Localidad(idDestino));
									criterioBusqueda.put("estadoRegistro", Constantes.VALUE_ACTIVO);
									lstRuta=ServiceLocator.getRutaManager().buscarPorX(criterioBusqueda, null);
									if( lstRuta.size() > 0 ){
										ruta = lstRuta.get(0);

										Servicio servicio = new Servicio();
										servicio.setId(idServicio);

										//Buscmos la tarifa
										// Inactiva el regitro, para ello recuperamos la tarifa con los datos ingresados
										List<TarifaFechaAbierta>lstTarifasFA=new ArrayList<>();
										lstTarifasFA = ServiceLocator.getTarifaFechaAbierta().buscarTarifas(idOrigen, idDestino, idServicio);

										//Si existe tarifa inactivamos el registro
										if( lstTarifasFA.size() > 0 ){
											for(TarifaFechaAbierta oTarifaFA:lstTarifasFA){
												oTarifaFA.setEstadoRegistro(Constantes.VALUE_INACTIVO);
												oTarifaFA.setFechaSuspencion(fechaActual);
												UtilData.auditarRegistro(oTarifaFA, true, getUsuario(), Executions.getCurrent());
												ServiceLocator.getTarifaFechaAbierta().actualizar(oTarifaFA);

												//Guardamos la nueva Tarifa
												tarifaFechaAbierta=new TarifaFechaAbierta();
												tarifaFechaAbierta.setRuta(ruta);
												tarifaFechaAbierta.setServicio(servicio);
												tarifaFechaAbierta.setMonto(nuevaTarifa);
												tarifaFechaAbierta.setFechaActivacion(fechaActual);
												tarifaFechaAbierta.setFechaCaducidad(new Date(fechaActual.getTime()+Constantes.MILISEGUNDOS_X_DIA*365));
												tarifaFechaAbierta.setEstadoRegistro(Constantes.VALUE_ACTIVO);
												UtilData.auditarRegistro(tarifaFechaAbierta, getUsuario(), Executions.getCurrent());
												ServiceLocator.getTarifaFechaAbierta().guardar(tarifaFechaAbierta);
											}
										}
										//Si no existe solo insertamos la nueva tarifa
										else{
											tarifaFechaAbierta=new TarifaFechaAbierta();
											tarifaFechaAbierta.setRuta(ruta);
											tarifaFechaAbierta.setServicio(servicio);
											tarifaFechaAbierta.setMonto(nuevaTarifa);
											tarifaFechaAbierta.setFechaActivacion(fechaActual);
											tarifaFechaAbierta.setFechaCaducidad(new Date(fechaActual.getTime()+Constantes.MILISEGUNDOS_X_DIA*365));
											tarifaFechaAbierta.setEstadoRegistro(Constantes.VALUE_ACTIVO);
											UtilData.auditarRegistro(tarifaFechaAbierta, getUsuario(), Executions.getCurrent());
											ServiceLocator.getTarifaFechaAbierta().guardar(tarifaFechaAbierta);
										}

										onClick_btnCancelar();
										buscar();

									}
									else{
										DlgMessage.information("La ruta ingresada para la Tarifa Fecha Abierta no existe, no se realizo ninguna accion.");
									}

								}
							} catch (Exception e2) {
								e2.printStackTrace();
								DlgMessage.error(e2.getMessage());
							}
						}
					});
				}//Fin con el gusrdaddo de la tarifa
			}//Fin Fecha Abierta
		} catch (Exception e) {
			e.printStackTrace();
			DlgMessage.error(e.getMessage());
		}
	}


	public void onCkeckRbActualizaTarifa(){
//		dlbxTarifa.setReadonly(false);
//		dlbxTarifa.setFocus(true);
	}

	public void onCheckRbIncrementaTarifa(){
//		 dlbxTarifa.setReadonly(false);
//		 dlbxTarifa.setFocus(true);

	}



}
