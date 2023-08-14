/**
 * Proyecto		: SISVYR
 * Sistema		: Sistema de Ventas y Reservas
 * Descripci�n	:
 * Autor		: Jos� Abanto
 * Fecha		: 21 jul. 2021
 * Hora			: 10:52:07
 */
package pe.itsb.vyrbus.view.ctrl;

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zul.Button;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Comboitem;
import org.zkoss.zul.Label;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

import pe.itsb.vyrbus.model.bean.Agencia;
import pe.itsb.vyrbus.model.bean.DetalleEquipaje;
import pe.itsb.vyrbus.model.bean.Itinerario;
import pe.itsb.vyrbus.service.locator.ServiceLocator;
import pe.itsb.vyrbus.service.util.Constantes;
import pe.itsb.vyrbus.service.util.CreateDocument;
import pe.itsb.vyrbus.service.util.Util;
import pe.itsb.vyrbus.view.ui.WndBase;
import pe.itsb.vyrbus.view.ui.WndIFrame;
import pe.itsb.vyrbus.view.ui.WndSeleccionaItinerario;

/**
 * @author abant
 *
 */
public class WndManifiestoEquipaje extends WndBase implements Serializable{

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

//	private Window wndManifiestoEquipaje;
	private Textbox txtItinerario;
	private Button btnBuscarItinerario;
	private Combobox cmbPuntocontrol;
	private Button btnRefrescar;
	private Combobox cmbImprimir;
	private Button btnPrevio;
	private Button btnImprimir;
	private Label lbRuta;
	private Label lbBus;
	private Label lbServicio;
	private Label lbFechaSalida;
	private Label lbFechaLlegada;
	private Label lbTripulante;
	private Label lbPiloto;
	private Label lbcopiloto;
	private Label lbCopilotoAux;
	private Listbox ltbxManifiestoEquipajes;


	private Itinerario itinerario;
	private List<DetalleEquipaje>resultDetalleEquipaje;


	/* (non-Javadoc)
	 * @see pe.itsb.vyrbus.view.ui.WndBase#onCreate()
	 */
	@Override
	public void onCreate() throws Exception {
		// TODO Auto-generated method stub
		super.onCreate();

		enlazarItinerario(btnBuscarItinerario);
	}

	/* (non-Javadoc)
	 * @see pe.itsb.vyrbus.view.ui.WndBase#initComponents()
	 */
	@Override
	public void initComponents() {
		// TODO Auto-generated method stub
		super.initComponents();

//		wndManifiestoEquipaje = (Window)this.getFellow("wndManifiestoEquipaje");
		txtItinerario = (Textbox)this.getFellow("txtItinerario");
		btnBuscarItinerario = (Button)this.getFellow("btnBuscarItinerario");
		cmbPuntocontrol = (Combobox)this.getFellow("cmbPuntocontrol");
		btnRefrescar = (Button)this.getFellow("btnRefrescar");
		cmbImprimir = (Combobox)this.getFellow("cmbImprimir");
		btnPrevio = (Button)this.getFellow("btnPrevio");
		btnImprimir = (Button)this.getFellow("btnImprimir");
		lbRuta = (Label)this.getFellow("lbRuta");
		lbBus = (Label)this.getFellow("lbBus");
		lbServicio = (Label)this.getFellow("lbServicio");
		lbFechaSalida = (Label)this.getFellow("lbFechaSalida");
		lbFechaLlegada = (Label)this.getFellow("lbFechaLlegada");
		lbTripulante = (Label)this.getFellow("lbTripulante");
		lbPiloto = (Label)this.getFellow("lbPiloto");
		lbcopiloto = (Label)this.getFellow("lbcopiloto");
		lbCopilotoAux = (Label)this.getFellow("lbCopilotoAux");
		ltbxManifiestoEquipajes = (Listbox)this.getFellow("ltbxManifiestoEquipajes");

		cmbPuntocontrol.addEventListener(Events.ON_CHANGE, new EventListener<Event>() {
			@Override
			public void onEvent(Event event) throws Exception {
				// TODO Auto-generated method stub
				change_cmbPuntoControl();
			}
		});
		btnRefrescar.addEventListener(Events.ON_CLICK, new EventListener<Event>() {
			@Override
			public void onEvent(Event event) throws Exception {
				// TODO Auto-generated method stub
				change_cmbPuntoControl();
			}
		});
		btnPrevio.addEventListener(Events.ON_CLICK, new EventListener<Event>() {
			@Override
			public void onEvent(Event event) throws Exception {
				// TODO Auto-generated method stub
				printManifiestoEquipaje(true);
			}
		});
		btnImprimir.addEventListener(Events.ON_CLICK, new EventListener<Event>() {
			@Override
			public void onEvent(Event event) throws Exception {
				// TODO Auto-generated method stub
				printManifiestoEquipaje(false);
			}
		});
	}

	/**
	 *
	 * @throws Exception
	 */
	private void change_cmbPuntoControl()throws Exception{
		if(!(txtItinerario.getText().trim().isEmpty())) {
			Long idItinerario = Long.valueOf(txtItinerario.getText());
			Integer agencia_idpuntoControl = ((Agencia)cmbPuntocontrol.getSelectedItem().getValue()).getId();

			resultDetalleEquipaje=ServiceLocator.getDetalleEquipajeManager().buscarManifiestoEquipaje(idItinerario, agencia_idpuntoControl);
			cargarDetalleManifiesto(resultDetalleEquipaje);
		}
	}

	/**
	 * Permite enlazar los controles a la ventana de selecci�n de Itinerario
	 * @param textboxItinerario :en este Textbox se devolvera el Id del itinerario seleccionado.
	 * @param button :ha este Button se le adjuntara un listener con la llamada a la ventana de selecci�n de itinerario
	 * @see WndItinerario:
	 */
	public  void enlazarItinerario(final Button button) {
		button.setTooltiptext("Seleccionar Itinerario");
		button.addEventListener(Events.ON_CLICK, new EventListener<Event>() {
			@Override
			public void onEvent(Event event) throws Exception {
				final WndSeleccionaItinerario oWndSeleccionaItinerario = new WndSeleccionaItinerario();
				boolean buscarVentanaParent = true;
				Component oComponent = button.getParent();
				while(buscarVentanaParent){
					 if(oComponent instanceof Window) {
						 oComponent.appendChild(oWndSeleccionaItinerario);
						 buscarVentanaParent = false;
					 }else{
					 	oComponent = oComponent.getParent();
					 }
				}
				oWndSeleccionaItinerario.onCreate();
				oWndSeleccionaItinerario.setMode("modal");
				oWndSeleccionaItinerario.setVisible(true);


				oWndSeleccionaItinerario.addEventListener(Events.ON_SELECT, new EventListener<Event>() {
					@Override
					public void onEvent(Event event) throws Exception {
//						Itinerario itinerario = ServiceLocator.getItinerarioManager().buscarPorId(oWndSeleccionaItinerario.getIdItinerario());
						/*lista de puntos de control*/
						cargaPuntoControl(oWndSeleccionaItinerario.getIdItinerario());

						cmbImprimir.getItems().clear();
						cmbImprimir.appendChild(new Comboitem("MANIFIESTO EQUIPAJES"));
						cmbImprimir.setSelectedIndex(0);

						/*Selecciona el origen en el punto de control*/
						Util.seleccionarValorItemCombo(Agencia.class, cmbPuntocontrol, getAgencia().getId());
						if(cmbPuntocontrol.getSelectedIndex()<0){
							if(cmbPuntocontrol.getItems().size()>0)
								cmbPuntocontrol.setSelectedIndex(0);
						}
						cargarItinerario(oWndSeleccionaItinerario.getIdItinerario(), oWndSeleccionaItinerario.getOrigen(), oWndSeleccionaItinerario.getItinerario().getRuta().getDestino());
					}
				});
			}
		});
	}

	/**
	 * Carga datos del itinerario.
	 * @param idItinerario	: identificador del itinerario.
	 * @param tramoOrigen 	: tramo origen
	 * @param tramoDestino	: tramo destino
	 * @throws Exception
	 */
	public  void cargarItinerario(Long idItinerario, String tramoOrigen, String tramoDestino) throws Exception{
		lbCopilotoAux.setValue("");
		lbRuta.setValue("");
		lbBus.setValue("");
		lbServicio.setValue("");
		lbFechaSalida.setValue("");
		lbFechaLlegada.setValue("");
		lbTripulante.setValue("");
		lbPiloto.setValue("");
		lbcopiloto.setValue("");
		lbCopilotoAux.setValue("");

		itinerario =  new Itinerario();
		itinerario = ServiceLocator.getManifiestoManager().consultaItinerario(idItinerario, tramoOrigen, tramoDestino);

		txtItinerario.setText(idItinerario.toString());
		lbRuta.setValue(itinerario.getRuta().toString());
		/*Valida si el itinerario esta o no asiciado a un bus*/
		if (itinerario.getBus() !=null){
			lbBus.setValue(itinerario.getBus().getCodigo());
			lbPiloto.setValue(itinerario.getBus().getProgramacionServicio().getPiloto().toString());
			lbcopiloto.setValue(itinerario.getBus().getProgramacionServicio().getCopiloto().toString());
			if(itinerario.getBus().getProgramacionServicio().getCopilotoAuxiliar()!=null)
				lbCopilotoAux.setValue(itinerario.getBus().getProgramacionServicio().getCopilotoAuxiliar().toString());
			if(itinerario.getBus().getProgramacionServicio().getTripulante() != null)
				lbTripulante.setValue(itinerario.getBus().getProgramacionServicio().getTripulante().toString());
		}
		lbFechaSalida.setValue(Constantes.FORMAT_DATE.format(itinerario.getFechaPartida())+" "+itinerario.getHoraPartida());
		lbFechaLlegada.setValue(Constantes.FORMAT_DATE.format( itinerario.getFechaLlegada())+" "+itinerario.getHoraLlegada());
		lbServicio.setValue(itinerario.getServicio().getDenominacion());

		if(cmbPuntocontrol.getSelectedIndex()>=0) {
			Integer agencia_idpuntoControl = ((Agencia)cmbPuntocontrol.getSelectedItem().getValue()).getId();
			resultDetalleEquipaje=ServiceLocator.getDetalleEquipajeManager().buscarManifiestoEquipaje(idItinerario, agencia_idpuntoControl);
			cargarDetalleManifiesto(resultDetalleEquipaje);
		}
	}

	/**
	 *Carga el detalle del manifiesto de equipajes
	 * @param listDetalleEquipaje
	 * @throws Exception
	 */
	private void cargarDetalleManifiesto(List<DetalleEquipaje> listDetalleEquipaje)throws Exception{
		Util.limpiarListbox(ltbxManifiestoEquipajes);
		for(DetalleEquipaje detalleEquipaje: listDetalleEquipaje) {
			String asiento = (detalleEquipaje.getVentaPasaje()!=null?detalleEquipaje.getVentaPasaje().getNumeroAsiento().toString():"");
			Listitem item = new Listitem();
			Listcell cell = new Listcell(asiento.length()==1?"0"+asiento:asiento);
			cell.setStyle("font-size:11px");
			item.appendChild(cell);
			cell = new Listcell(detalleEquipaje.getVentaPasaje()!=null?detalleEquipaje.getVentaPasaje().getNumeroBoleto():"");
			cell.setStyle("font-size:11px");
			item.appendChild(cell);
			cell = new Listcell(detalleEquipaje.getVentaPasaje()!=null?detalleEquipaje.getVentaPasaje().getPasajero().toString():"");
			item.appendChild(cell);
			cell = new Listcell(detalleEquipaje.getEquipaje().getObservaciones());
			cell.setStyle("font-size:11px");
			item.appendChild(cell);
			cell = new Listcell(Util.toNumberFormat(detalleEquipaje.getVentaPasaje().getImportePagado(), 2) );
			cell.setStyle("font-size:11px");
			item.appendChild(cell);


			item.setValue(detalleEquipaje);
			ltbxManifiestoEquipajes.appendChild(item);
		}
	}


	/**
	 * carga puntos de Control, seg�n el itinerario
	 * @param idItinerario : identificador del itinerario.
	 * @throws Exception
	 */
	private void cargaPuntoControl(Long idItinerario) throws Exception{
		ArrayList<Agencia> lsta= (ArrayList<Agencia>) ServiceLocator.getManifiestoManager().consultaPtoControl(idItinerario);
		cmbPuntocontrol.getItems().clear();
		for (Agencia agencia : lsta) {
			Comboitem oComboitem = new Comboitem();
			oComboitem.setLabel(agencia.getNombreCorto());
			oComboitem.setValue(agencia);
			cmbPuntocontrol.appendChild(oComboitem);
		}
	}

	/**
	 *
	 * @param isPrevio
	 * @throws Exception
	 */
	private void printManifiestoEquipaje(boolean isPrevio)throws Exception {
		if(resultDetalleEquipaje!=null && resultDetalleEquipaje.size()>0) {
			String numeroManifiesto = (Util.autocompleNumberBoleto("000-"+resultDetalleEquipaje.get(0).getEquipaje().getId().toString())).split("-")[1];
			File file = CreateDocument.creaManifiesto_Equipajes(resultDetalleEquipaje, itinerario);
			String src = Constantes.URL_FORMATOS_MANIFIESTOS+Constantes.CLAVE_PAHT+"MANIFIESTO_EQUIPAJE_"+ numeroManifiesto	+".txt";

			if(isPrevio) {
				final WndIFrame iFrame = new WndIFrame();
				iFrame.oThisWindow.setTitle("MANIFIESTO DE EQUIPAJES");
				iFrame.setSrc(src);
				iFrame.setWidth("1000");
				iFrame.setheight("600");
				iFrame.loadiframe();
				iFrame.btnCerrar.setVisible(false);
				iFrame.oThisWindow.setClosable(true);
				this.appendChild(iFrame);
				iFrame.setMode("modal");
			}else
				Util.descargarArchivo(file);
		}
	}



}
