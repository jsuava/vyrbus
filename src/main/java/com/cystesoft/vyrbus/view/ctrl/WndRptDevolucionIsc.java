/**
 * Proyecto		: SISVYR
 * Sistema		: Sistema de Ventas y Reservas
 * Descripción	:
 * Autor		: José Abanto
 * Fecha		: 27 jun. 2021
 * Hora			: 19:38:57
 */
package com.cystesoft.vyrbus.view.ctrl;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Session;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listhead;
import org.zkoss.zul.Listheader;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Radio;
import org.zkoss.zul.Textbox;

import com.cystesoft.vyrbus.model.bean.Manifiesto;
import com.cystesoft.vyrbus.service.locator.ServiceLocator;
import com.cystesoft.vyrbus.service.mappers.VentasPiloto;
import com.cystesoft.vyrbus.service.util.Constantes;
import com.cystesoft.vyrbus.service.util.Util;
import com.cystesoft.vyrbus.view.ui.DlgMessage;
import com.cystesoft.vyrbus.view.ui.WndBase;

/**
 * @author Marco
 *
 */
public class WndRptDevolucionIsc extends WndBase {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	private Datebox dtbxFechaInicio;
	private Datebox dtbxFechaFin;
	private Textbox txtPer4949;
	private Textbox txtPeriodo;
	private Listbox lbxManifiestos;
	private Radio rdISC;
	private Radio rdVentas;

	List<VentasPiloto> lstVentas = null;

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.view.ui.WndBase#initComponents()
	 */
	@Override
	public void initComponents() {
		dtbxFechaInicio=(Datebox)this.getFellow("dtbxFechaInicio");
		dtbxFechaFin=(Datebox)this.getFellow("dtbxFechaFin");
		txtPer4949=(Textbox)this.getFellow("txtPer4949");
		txtPeriodo=(Textbox)this.getFellow("txtPeriodo");
		lbxManifiestos=(Listbox)this.getFellow("lbxManifiestos");
		rdISC = (Radio)this.getFellow("rdISC");
		rdVentas = (Radio)this.getFellow("rdVentas");

		rdISC.addEventListener(Events.ON_CHECK, new EventListener<Event>() {
			@Override
			public void onEvent(Event e) {
				createHeaders(true);
			}
		});

		rdVentas.addEventListener(Events.ON_CHECK, new EventListener<Event>() {
			@Override
			public void onEvent(Event e) {
				createHeaders(false);
			}
		});
	}


	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.view.ui.WndBase#onCreate()
	 */
	@Override
	public void onCreate() throws Exception {
		// TODO Auto-generated method stub
		dtbxFechaInicio.setValue(new Date());
		dtbxFechaFin.setValue(new Date());

	}



	public void buscar() throws Exception{
		Util.limpiarListbox(lbxManifiestos);

		String fechaInicio=null, fechaFin=null, periodo=null,per4949=null;

		if(rdISC.isChecked()) {
			if(txtPer4949.getText().trim().isEmpty()){
				DlgMessage.information("Debe de ingresar el codigo PER4949.");
				txtPer4949.focus();
				return;
			}else if(txtPeriodo.getText().trim().isEmpty()){
				DlgMessage.information("Debe de ingresar el codigo del periodo.");
				txtPeriodo.focus();
				return;
			}
		}

		if(dtbxFechaInicio.getValue()!=null)
			fechaInicio=Constantes.FORMAT_DATE.format(dtbxFechaInicio.getValue());
		if(dtbxFechaFin.getValue()!=null)
			fechaFin=Constantes.FORMAT_DATE.format(dtbxFechaFin.getValue());
		if(!(txtPer4949.getText().trim().isEmpty()))
			per4949=txtPer4949.getText().trim();
		if(!(txtPeriodo.getText().trim().isEmpty()))
			periodo=txtPeriodo.getText().trim();

		if(rdISC.isChecked()) {
			lstVentas = null;
			List<Manifiesto>lstManifiestos=ServiceLocator.getManifiestoManager().buscarDevolucionIsc(fechaInicio, fechaFin, per4949, periodo);
			listarISC(lstManifiestos);
		}else {
			lstVentas = ServiceLocator.getVentaPasajesManager().buscarVentasPagoPilotos(fechaInicio, fechaFin);
			if(lstVentas.size()>0)
				listarVentas(lstVentas);
			else {
				DlgMessage.information("No se encontraron ventas para los criterios ingresados.");
				return;
			}
		}
	}

	public void listarISC(List<Manifiesto> lstManifiestos) {
		String style="font-size:12px !important";
		String serie, numero;

		for(Manifiesto manifiesto:lstManifiestos){
			Listitem item=new Listitem();

			Listcell cell=new Listcell();
//			cell.setStyle(style);
//			item.appendChild(cell);
			cell=new Listcell(manifiesto.getItinerario().getId().toString());
			item.appendChild(cell);
			cell=new Listcell(manifiesto.getCodigoBus());
			cell.setStyle(style);
			item.appendChild(cell);
			cell=new Listcell(manifiesto.getRuc());
			cell.setStyle(style);
			item.appendChild(cell);
			cell=new Listcell(manifiesto.getPer4949());
			cell.setStyle(style);
			item.appendChild(cell);
			cell=new Listcell(manifiesto.getPeriodo());
			cell.setStyle(style);
			item.appendChild(cell);
			serie = manifiesto.getNumeroManifiesto().substring(0, 3);
			numero = manifiesto.getNumeroManifiesto().substring(4);
			cell=new Listcell(serie);
			cell.setStyle(style);
			item.appendChild(cell);
			cell=new Listcell(numero);
			cell.setStyle(style);
			item.appendChild(cell);
			cell=new Listcell(manifiesto.getBus().getGrupoMantenimiento().getDenominacion());
			cell.setStyle(style);
			item.appendChild(cell);
			cell=new Listcell(manifiesto.getPlaca());
			cell.setStyle(style);
			item.appendChild(cell);
			cell=new Listcell(manifiesto.getCertificadoHabilitacion());
			cell.setStyle(style);
			item.appendChild(cell);
			cell=new Listcell(Constantes.FORMAT_DATE.format(manifiesto.getItinerario().getFechaPartida()));
			cell.setStyle(style);
			item.appendChild(cell);
			cell=new Listcell(manifiesto.getPuntoPartidaDepartamento());
			cell.setStyle(style);
			item.appendChild(cell);
			cell=new Listcell(manifiesto.getPuntoPartidaDistrito());
			cell.setStyle(style);
			item.appendChild(cell);
			cell=new Listcell(manifiesto.getPuntoLlegadaDepartamento());
			cell.setStyle(style);
			item.appendChild(cell);
			cell=new Listcell(manifiesto.getPuntoLlegadaDistrito());
			cell.setStyle(style);
			item.appendChild(cell);
			cell=new Listcell(Util.toNumberFormat(manifiesto.getImporte(), 2));
			cell.setStyle(style);
			item.appendChild(cell);

			lbxManifiestos.appendChild(item);
		}
	}

	public void listarVentas(List<VentasPiloto>lstVentas) {
		String style="font-size:11px !important";

		int i=0;
		for(VentasPiloto ventasPiloto:lstVentas){
			Listitem item=new Listitem();
			i++;
			Listcell cell=new Listcell();
			cell=new Listcell(String.valueOf(i));
			item.appendChild(cell);

			cell=new Listcell(Util.DatetoString(ventasPiloto.getFechaCompra(), Constantes.DATE_FORMAT));
			item.appendChild(cell);

			cell=new Listcell(ventasPiloto.getNumeroBoleto());
			cell.setStyle(style);
			item.appendChild(cell);

			cell=new Listcell(ventasPiloto.getRuc()==null?"":ventasPiloto.getRuc());
			cell.setStyle(style);
			item.appendChild(cell);

			cell=new Listcell(ventasPiloto.getDni());
			cell.setStyle(style);
			item.appendChild(cell);

			cell=new Listcell(ventasPiloto.getNombres());
			cell.setStyle(style);
			item.appendChild(cell);

			cell=new Listcell(Util.toNumberFormat(ventasPiloto.getExonerado(), 2));
			cell.setStyle(style);
			item.appendChild(cell);

			cell=new Listcell(Util.toNumberFormat(ventasPiloto.getVenta(), 2));
			cell.setStyle(style);
			item.appendChild(cell);

			cell=new Listcell(Util.toNumberFormat(ventasPiloto.getIgv(), 2));
			cell.setStyle(style);
			item.appendChild(cell);

			cell=new Listcell(Util.toNumberFormat(ventasPiloto.getTotal(), 2));
			cell.setStyle(style);
			item.appendChild(cell);

			cell=new Listcell(Util.DatetoString(ventasPiloto.getFechaSalidaBus(), Constantes.DATE_FORMAT));
			cell.setStyle(style);
			item.appendChild(cell);

			cell=new Listcell(ventasPiloto.getHoraVenta());
			cell.setStyle(style);
			item.appendChild(cell);

			cell=new Listcell(ventasPiloto.getOrigen());
			cell.setStyle(style);
			item.appendChild(cell);

			cell=new Listcell(ventasPiloto.getDestino());
			cell.setStyle(style);
			item.appendChild(cell);

			cell=new Listcell(ventasPiloto.getAsiento());
			cell.setStyle(style);
			item.appendChild(cell);

			cell=new Listcell(ventasPiloto.getTipo());
			cell.setStyle(style);
			item.appendChild(cell);

			cell=new Listcell(ventasPiloto.getHoraSalidaBus());
			cell.setStyle(style);
			item.appendChild(cell);

			cell=new Listcell(ventasPiloto.getCodigo());
			cell.setStyle(style);
			item.appendChild(cell);

			cell=new Listcell(ventasPiloto.getPiloto());
			cell.setStyle(style);
			item.appendChild(cell);

			cell=new Listcell(ventasPiloto.getCopiloto()==null?"":ventasPiloto.getCopiloto());
			cell.setStyle(style);
			item.appendChild(cell);

			cell=new Listcell(ventasPiloto.getTripulante()==null?"":ventasPiloto.getTripulante());
			cell.setStyle(style);
			item.appendChild(cell);

			item.setValue(ventasPiloto);

			lbxManifiestos.appendChild(item);
		}
	}

	public void createHeaders(boolean isISC) {
		lbxManifiestos.detach();
		lbxManifiestos = new Listbox();
		Listhead listhead = new Listhead();
		String style = "color: #ffffff";

		if(isISC) {
			txtPer4949.setReadonly(false);
			txtPeriodo.setReadonly(false);
			Listheader listheader = new Listheader();
			listheader.setLabel("ID");
			listheader.setWidth("50px");
			listheader.setAlign("center");
			listheader.setStyle(style);
			listhead.appendChild(listheader);

			listheader = new Listheader();
			listheader.setLabel("NRO BUS");
			listheader.setWidth("70px");
			listheader.setAlign("center");
			listheader.setStyle(style);
			listhead.appendChild(listheader);

			listheader = new Listheader();
			listheader.setLabel("RUC");
			listheader.setWidth("100px");
			listheader.setAlign("center");
			listheader.setStyle(style);
			listhead.appendChild(listheader);

			listheader = new Listheader();
			listheader.setLabel("PER4949");
			listheader.setWidth("60px");
			listheader.setAlign("center");
			listheader.setStyle(style);
			listhead.appendChild(listheader);

			listheader = new Listheader();
			listheader.setLabel("PERIODO");
			listheader.setWidth("60px");
			listheader.setAlign("center");
			listheader.setStyle(style);
			listhead.appendChild(listheader);

			listheader = new Listheader();
			listheader.setLabel("CSSERIEF");
			listheader.setWidth("50px");
			listheader.setAlign("center");
			listheader.setStyle(style);
			listhead.appendChild(listheader);

			listheader = new Listheader();
			listheader.setLabel("NUMERO");
			listheader.setWidth("80px");
			listheader.setAlign("center");
			listheader.setStyle(style);
			listhead.appendChild(listheader);

			listheader = new Listheader();
			listheader.setLabel("MARCA");
			listheader.setWidth("120px");
			listheader.setAlign("");
			listheader.setStyle(style);
			listhead.appendChild(listheader);

			listheader = new Listheader();
			listheader.setLabel("NRO PLACA");
			listheader.setWidth("90px");
			listheader.setAlign("center");
			listheader.setStyle(style);
			listhead.appendChild(listheader);

			listheader = new Listheader();
			listheader.setLabel("NO_CHAB");
			listheader.setWidth("110px");
			listheader.setAlign("");
			listheader.setStyle(style);
			listhead.appendChild(listheader);

			listheader = new Listheader();
			listheader.setLabel("FEC_SAL");
			listheader.setWidth("center");
			listheader.setAlign("80px");
			listheader.setStyle(style);
			listhead.appendChild(listheader);

			listheader = new Listheader();
			listheader.setLabel("PP_DP");
			listheader.setWidth("150px");
			listheader.setAlign("");
			listheader.setStyle(style);
			listhead.appendChild(listheader);

			listheader = new Listheader();
			listheader.setLabel("PP_DS");
			listheader.setWidth("150px");
			listheader.setAlign("");
			listheader.setStyle(style);
			listhead.appendChild(listheader);

			listheader = new Listheader();
			listheader.setLabel("LL_DP");
			listheader.setWidth("150px");
			listheader.setAlign("");
			listheader.setStyle(style);
			listhead.appendChild(listheader);

			listheader = new Listheader();
			listheader.setLabel("LL_DS");
			listheader.setWidth("150px");
			listheader.setAlign("");
			listheader.setStyle(style);
			listhead.appendChild(listheader);

			listheader = new Listheader();
			listheader.setLabel("IMPORTE");
			listheader.setWidth("");
			listheader.setAlign("right");
			listheader.setStyle(style);
			listhead.appendChild(listheader);

			listheader = new Listheader();
			listheader.setLabel("");
			listheader.setWidth("");
			listheader.setAlign("");
			listheader.setStyle(style);
			listhead.appendChild(listheader);

			lbxManifiestos.appendChild(listhead);
			this.appendChild(lbxManifiestos);

		}else {
			txtPer4949.setReadonly(true);
			txtPeriodo.setReadonly(true);
			Listheader listheader = new Listheader("#","","30px");
			listheader.setAlign("center");
			listheader.setStyle(style);
			listhead.appendChild(listheader);

			listheader = new Listheader("F.COMPRA","","60px");
			listheader.setAlign("center");
			listheader.setStyle(style);
			listhead.appendChild(listheader);

//			listheader = new Listheader("TD","","25px");
//			listheader.setAlign("center");
//			listheader.setStyle(style);
//			listhead.appendChild(listheader);

			listheader = new Listheader("DOCUMENTO","","90px");
			listheader.setAlign("center");
			listheader.setStyle(style);
			listhead.appendChild(listheader);

			listheader = new Listheader("RUC","","80px");
			listheader.setAlign("center");
			listheader.setStyle(style);
			listhead.appendChild(listheader);

			listheader = new Listheader("DNI","","70px");
			listheader.setAlign("center");
			listheader.setStyle(style);
			listhead.appendChild(listheader);

			listheader = new Listheader("RAZON","","150px");
			listheader.setAlign("left");
			listheader.setStyle(style);
			listhead.appendChild(listheader);

			listheader = new Listheader("EXONERADA","","70px");
			listheader.setAlign("right");
			listheader.setStyle(style);
			listhead.appendChild(listheader);

			listheader = new Listheader("V. VENTA","","60px");
			listheader.setAlign("right");
			listheader.setStyle(style);
			listhead.appendChild(listheader);

			listheader = new Listheader("IGV","","60px");
			listheader.setAlign("right");
			listheader.setStyle(style);
			listhead.appendChild(listheader);

			listheader = new Listheader("TOTAL","","60px");
			listheader.setAlign("right");
			listheader.setStyle(style);
			listhead.appendChild(listheader);

			listheader = new Listheader("F.SALIDA.B","","70px");
			listheader.setAlign("center");
			listheader.setStyle(style);
			listhead.appendChild(listheader);

			listheader = new Listheader("H.VENTA","","50px");
			listheader.setAlign("center");
			listheader.setStyle(style);
			listhead.appendChild(listheader);

			listheader = new Listheader("OF. ORIGEN","","110px");
			listheader.setAlign("center");
			listheader.setStyle(style);
			listhead.appendChild(listheader);

			listheader = new Listheader("OF. DESTINO","","110px");
			listheader.setAlign("center");
			listheader.setStyle(style);
			listhead.appendChild(listheader);

			listheader = new Listheader("ASIENTO","","50px");
			listheader.setAlign("center");
			listheader.setStyle(style);
			listhead.appendChild(listheader);

			listheader = new Listheader("TIPO","","100px");
			listheader.setAlign("left");
			listheader.setStyle(style);
			listhead.appendChild(listheader);

			listheader = new Listheader("H.SALIDA.B","","80px");
			listheader.setAlign("center");
			listheader.setStyle(style);
			listhead.appendChild(listheader);

			listheader = new Listheader("PLACA","","50px");
			listheader.setAlign("center");
			listheader.setStyle(style);
			listhead.appendChild(listheader);

			listheader = new Listheader("CHOFER","","170px");
			listheader.setAlign("left");
			listheader.setStyle(style);
			listhead.appendChild(listheader);

			listheader = new Listheader("CHOFER2","","170px");
			listheader.setAlign("left");
			listheader.setStyle(style);
			listhead.appendChild(listheader);

			listheader = new Listheader("TRIPULANTE","","170px");
			listheader.setAlign("left");
			listheader.setStyle(style);
			listhead.appendChild(listheader);

			lbxManifiestos.appendChild(listhead);
			this.appendChild(lbxManifiestos);
		}
	}



	public void exportarExcel(){
		if(rdISC.isChecked()) {
			DlgMessage.information("Pendiente de implementacion");
		}else {
			Session session = getDesktop().getSession();
			HttpSession httpSession = (HttpSession)session.getNativeSession();
			httpSession.setAttribute("parcialPath",Constantes.DIRECTORY_EXCEL+"VentasPagoPilotos.xls");
			httpSession.setAttribute("lstVentas", lstVentas);
			Executions.sendRedirect("/exportXlsVentasPagoPilotos.htm");
		}

	}

}
