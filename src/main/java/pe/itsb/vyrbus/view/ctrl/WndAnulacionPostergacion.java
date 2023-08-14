/**
 * Proyecto		: SISVYR
 * Sistema		: Sistema de Ventas y Reservas
 * Descripci�n	:
 * Autor		: Jos� Abanto
 * Fecha		: 22 may. 2021
 * Hora			: 21:42:04
 */
package pe.itsb.vyrbus.view.ctrl;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Session;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.Hlayout;
import org.zkoss.zul.Intbox;
import org.zkoss.zul.Label;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Radiogroup;

import pe.itsb.vyrbus.model.bean.VentaPasaje;
import pe.itsb.vyrbus.service.locator.ServiceLocator;
import pe.itsb.vyrbus.service.mappers.ResumenAnulacionPostergacion;
import pe.itsb.vyrbus.service.util.Constantes;
import pe.itsb.vyrbus.service.util.Messages;
import pe.itsb.vyrbus.service.util.MyTime;
import pe.itsb.vyrbus.service.util.Util;
import pe.itsb.vyrbus.view.ui.DlgMessage;
import pe.itsb.vyrbus.view.ui.WndBase;

/**
 * @author Jose
 *
 */
public class WndAnulacionPostergacion extends WndBase {

	private static final long serialVersionUID = 1L;
	private Datebox dbDesde;
	private Datebox dbHasta;
	private Listbox lbxAgencias;
	private Listbox lbxUsuarios;
	private Listbox lbxDetallado;
	private Radiogroup rdgCriterios;
	private Hlayout hlytResumen;
	private Intbox ibxCantidadPostergaciones;
	private Label lblAgencia;
	private Label lblUsuario;

	private static final int SEARCH_BY_AGENCIA = 1;
	private static final int SEARCH_BY_USER = 2;

	/* (non-Javadoc)
	 * @see pe.itsb.vyrbus.view.ui.WndBase#initComponents()
	 */
	@Override
	public void initComponents() {
		dbDesde = (Datebox)this.getFellow("dbDesde");
		dbHasta = (Datebox)this.getFellow("dbHasta");
		lbxAgencias = (Listbox)this.getFellow("lbxAgencias");
		lbxUsuarios = (Listbox)this.getFellow("lbxUsuarios");
		lbxDetallado = (Listbox)this.getFellow("lbxDetallado");
		lblAgencia = (Label)this.getFellow("lblAgencia");
		lblUsuario = (Label)this.getFellow("lblUsuario");
		dbHasta = (Datebox)this.getFellow("dbHasta");
		ibxCantidadPostergaciones = (Intbox)this.getFellow("ibxCantidadPostergaciones");
		rdgCriterios = (Radiogroup)this.getFellow("rdgCriterios");
		hlytResumen = (Hlayout)this.getFellow("hlytResumen");
	}

	/* (non-Javadoc)
	 * @see pe.itsb.vyrbus.view.ui.WndBase#onCreate()
	 */
	@Override
	public void onCreate() throws Exception {
		MyTime time = new MyTime();
		dbDesde.setValue(Constantes.FORMAT_DATE.parse(time.dateServer()));
		dbHasta.setValue(Constantes.FORMAT_DATE.parse(time.dateServer()));
		rdgCriterios.setSelectedIndex(0);
		inicializarControles(true);

		lbxAgencias.addEventListener(Events.ON_DOUBLE_CLICK,new EventListener<Event>() {
			@Override
			public void onEvent(Event event) throws Exception {
				//Si la busqueda es para Anulados
				if(rdgCriterios.getSelectedIndex()==0){
					if(lbxAgencias.getSelectedIndex() >= 0)
						buscarDetallado(SEARCH_BY_AGENCIA);
				}else{//Sino es para postergaciones
					if(lbxAgencias.getSelectedIndex() >= 0)
						buscarDetallado(SEARCH_BY_AGENCIA);
				}
			}
		});

		lbxUsuarios.addEventListener(Events.ON_DOUBLE_CLICK,new EventListener<Event>() {
			@Override
			public void onEvent(Event event) throws Exception {
				//Si la busqueda es para Anulados
				if(rdgCriterios.getSelectedIndex()==0){
					if(lbxUsuarios.getSelectedIndex() >= 0)
						buscarDetallado(SEARCH_BY_USER);
				}else{//Sino es para postergaciones
					if(lbxUsuarios.getSelectedIndex() >= 0)
						buscarDetallado(SEARCH_BY_USER);
				}
			}
		});

	}

	private void inicializarControles(boolean estado){
		ibxCantidadPostergaciones.setDisabled(estado);
	}

	public void limpiarListbox(){
		Util.limpiarListbox(lbxAgencias);
		Util.limpiarListbox(lbxUsuarios);
		Util.limpiarListbox(lbxDetallado);
		lbxDetallado.setVisible(false);
	}

	public void rdgOnClick(){

		if(rdgCriterios.getSelectedIndex() == 0){
			inicializarControles(true);
			lblAgencia.setValue("ANULACIONES POR AGENCIA");
			lblUsuario.setValue("ANULACIONES POR USUARIO");
			limpiarListbox();
			ibxCantidadPostergaciones.setText("");
		}else{
			inicializarControles(false);
			ibxCantidadPostergaciones.setFocus(true);
			lblAgencia.setValue("POSTERGACIONES POR AGENCIA");
			lblUsuario.setValue("POSTERGACIONES POR USUARIO");
			limpiarListbox();
		}

	}

	public void onSearch() {
		String fechaDesde = Constantes.FORMAT_DATE.format(dbDesde.getValue());
		String fechaHasta = Constantes.FORMAT_DATE.format(dbHasta.getValue());
		Integer nroPostergaciones;
		if(rdgCriterios.getSelectedItem() == null) {
			DlgMessage.information(Messages.getString("wndAnulacionPostergacion.information.noCriterios"));
			return;
		}

		try {
			if(rdgCriterios.getSelectedIndex()==0) {

				List<ResumenAnulacionPostergacion> lstAnuladosByAgencia = ServiceLocator.getVentaPasajesManager().buscarBoletosAnuladosByX(fechaDesde, fechaHasta, SEARCH_BY_AGENCIA);
				listarAnuladosPostergadosByAgencia(lstAnuladosByAgencia);
				List<ResumenAnulacionPostergacion> lstAnuladosByUsuario = ServiceLocator.getVentaPasajesManager().buscarBoletosAnuladosByX(fechaDesde, fechaHasta, SEARCH_BY_USER);
				listarAnuladosPostergadosByUsuario(lstAnuladosByUsuario);
			}else if(rdgCriterios.getSelectedIndex()==1) {

				if(ibxCantidadPostergaciones.intValue()>0 && ibxCantidadPostergaciones.getValue() != null){
					nroPostergaciones = ibxCantidadPostergaciones.intValue();
				}else{
					nroPostergaciones = 0;
				}
				List<ResumenAnulacionPostergacion> lstPostergadosByAgencia = ServiceLocator.getVentaPasajesManager().buscarBoletosPostergadosByX(fechaDesde, fechaHasta, SEARCH_BY_AGENCIA, nroPostergaciones);
				listarAnuladosPostergadosByAgencia(lstPostergadosByAgencia);
				List<ResumenAnulacionPostergacion> lstPostergadosByUsuario = ServiceLocator.getVentaPasajesManager().buscarBoletosPostergadosByX(fechaDesde, fechaHasta, SEARCH_BY_USER, nroPostergaciones);
				listarAnuladosPostergadosByUsuario(lstPostergadosByUsuario);
			}else {
				System.out.println("Posterga x numero");
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void listarAnuladosPostergadosByAgencia(List<ResumenAnulacionPostergacion> lstResumen) {
		Listitem item = null;
		Listcell cell = null;

		Util.limpiarListbox(lbxAgencias);
		lbxDetallado.setVisible(false);

		int i=0;
		for(ResumenAnulacionPostergacion resumen : lstResumen) {
			i++;
			item = new Listitem();

			cell = new Listcell(String.valueOf(i));
			item.appendChild(cell);

			cell = new Listcell(resumen.getId().toString());
			item.appendChild(cell);

			cell = new Listcell(resumen.getDenominacion());
			item.appendChild(cell);

			cell = new Listcell(resumen.getTotal().toString());
			item.appendChild(cell);

			item.setValue(resumen);

			lbxAgencias.appendChild(item);

		}
	}


	public void listarAnuladosPostergadosByUsuario(List<ResumenAnulacionPostergacion> lstResumen) {

		Listitem item = null;
		Listcell cell = null;

		Util.limpiarListbox(lbxUsuarios);
		lbxDetallado.setVisible(false);

		int i=0;
		for(ResumenAnulacionPostergacion resumen : lstResumen) {
			i++;
			item = new Listitem();

			cell = new Listcell(String.valueOf(i));
			item.appendChild(cell);

			cell = new Listcell(resumen.getId().toString());
			item.appendChild(cell);

			cell = new Listcell(resumen.getDenominacion());
			item.appendChild(cell);

			cell = new Listcell(resumen.getTotal().toString());
			item.appendChild(cell);

			item.setValue(resumen);

			lbxUsuarios.appendChild(item);

		}

	}

	public void buscarDetallado(int tipoBusqueda){

		String fechaDesde = Constantes.FORMAT_DATE.format(dbDesde.getValue());
		String fechaHasta = Constantes.FORMAT_DATE.format(dbHasta.getValue());
		Integer nroPostergaciones;

		Listitem item = null;
		Listcell cell = null;

		List<VentaPasaje> lstDetVentas = null;

		Util.limpiarListbox(lbxDetallado);
		if(rdgCriterios.getSelectedIndex() == 0){

			if(tipoBusqueda == SEARCH_BY_AGENCIA){
				Integer index = lbxAgencias.getSelectedIndex();
				Listitem itemAnulado = lbxAgencias.getItemAtIndex(index);
				lstDetVentas = ServiceLocator.getVentaPasajesManager()
															.buscarBoletosAnuladosDetalladoByX(
																	fechaDesde,
																	fechaHasta,
																	((ResumenAnulacionPostergacion)itemAnulado.getValue()).getId(),
																	SEARCH_BY_AGENCIA);
			}else{
				Integer index = lbxUsuarios.getSelectedIndex();
				Listitem itemAnulado = lbxUsuarios.getItemAtIndex(index);
				lstDetVentas = ServiceLocator.getVentaPasajesManager()
															.buscarBoletosAnuladosDetalladoByX(
																	fechaDesde,
																	fechaHasta,
																	((ResumenAnulacionPostergacion)itemAnulado.getValue()).getId(),
																	SEARCH_BY_USER);
			}
		}else if(rdgCriterios.getSelectedIndex() == 1){

			if(ibxCantidadPostergaciones.intValue()>0 && ibxCantidadPostergaciones.getValue() != null){
				nroPostergaciones = ibxCantidadPostergaciones.intValue();
			}else{
				nroPostergaciones = 0;
			}

			if(tipoBusqueda == SEARCH_BY_AGENCIA){
				Integer index = lbxAgencias.getSelectedIndex();
				Listitem itemPostergado = lbxAgencias.getItemAtIndex(index);
				lstDetVentas = ServiceLocator.getVentaPasajesManager()
															.buscarBoletosPostergadosDetalladoByX(
																	fechaDesde,
																	fechaHasta,
																	((ResumenAnulacionPostergacion)itemPostergado.getValue()).getId(),
																	SEARCH_BY_AGENCIA,
																	nroPostergaciones);
			}else{
				Integer index = lbxUsuarios.getSelectedIndex();
				Listitem itemPostergado = lbxUsuarios.getItemAtIndex(index);
				lstDetVentas = ServiceLocator.getVentaPasajesManager()
															.buscarBoletosPostergadosDetalladoByX(
																	fechaDesde,
																	fechaHasta,
																	((ResumenAnulacionPostergacion)itemPostergado.getValue()).getId(),
																	SEARCH_BY_USER,
																	nroPostergaciones);
			}
		}

		String strUsuarioAnulacion;

		for(VentaPasaje ventaPasaje : lstDetVentas) {
			item = new Listitem();

			cell = new Listcell(ventaPasaje.getId().toString());
			item.appendChild(cell);

			cell = new Listcell(ventaPasaje.getVentaOriginal().toString());
			item.appendChild(cell);

			cell = new Listcell(ventaPasaje.getNumeroBoleto());
			item.appendChild(cell);

			cell = new Listcell(ventaPasaje.getNumeroBoletoAnterior());
			item.appendChild(cell);

			cell = new Listcell(ventaPasaje.getNumeroControl());
			item.appendChild(cell);

			cell = new Listcell(ventaPasaje.getSecuencial().toString());
			item.appendChild(cell);

			cell = new Listcell(ventaPasaje.getPasajero().getNombresApellidos());
			item.appendChild(cell);

			cell = new Listcell(ventaPasaje.getTipoComprobante().getAbreviatura());
			item.appendChild(cell);

			cell = new Listcell(ventaPasaje.getTipoMovimiento().getAbreviatura());
			item.appendChild(cell);

			cell = new Listcell(ventaPasaje.getCanalVenta().getNombreCorto());
			item.appendChild(cell);

			cell = new Listcell(ventaPasaje.getNumeroPiso().toString());
			item.appendChild(cell);

			cell = new Listcell(ventaPasaje.getNumeroAsiento().toString());
			item.appendChild(cell);

			cell = new Listcell( ventaPasaje.getFechaPartida()==null ? "01/01/1900" : Constantes.FORMAT_DATE.format(ventaPasaje.getFechaPartida()) );
			item.appendChild(cell);

			cell = new Listcell(ventaPasaje.getHoraPartida());
			item.appendChild(cell);

			cell = new Listcell(ventaPasaje.getTarifa().toString());
			item.appendChild(cell);

			cell = new Listcell(ventaPasaje.getImportePagadoByDiferencia().toString());
			item.appendChild(cell);

			//Fecha Venta
			cell = new Listcell(Constantes.FORMAT_DATE.format(ventaPasaje.getFechaLiquidacion()));
			item.appendChild(cell);

			//USuario Venta
			cell = new Listcell(ventaPasaje.getUsuario().getApellidoPaterno()
					+" " + ventaPasaje.getUsuario().getApellidoMaterno()
					+" " + ventaPasaje.getUsuario().getNombre());
			item.appendChild(cell);

			//Fecha Anulacion, Lee la fecha de anulacion,  MAOE 31/03/2023
			cell = new Listcell(rdgCriterios.getSelectedIndex()==0 ?Constantes.FORMAT_DATE_TIME_24H.format(ventaPasaje.getFechaAnulacion()) : "");
			item.appendChild(cell);
			//Usuario Anulacion, lee el usuario anulacion. MAOE 31/03/2023
			if(rdgCriterios.getSelectedIndex()==0) {
				strUsuarioAnulacion = ventaPasaje.getUsuarioAnulacion().getApellidoPaterno()
						+" " + ventaPasaje.getUsuarioAnulacion().getApellidoMaterno()
						+" " + ventaPasaje.getUsuarioAnulacion().getNombre();
			}
			else
				strUsuarioAnulacion="";
			cell = new Listcell(strUsuarioAnulacion);
			item.appendChild(cell);

			cell = new Listcell(ventaPasaje.getAgencia().getNombreCorto());
			item.appendChild(cell);

			cell = new Listcell(ventaPasaje.getObservaciones());
			item.appendChild(cell);

			lbxDetallado.appendChild(item);

		}

		lbxDetallado.setVisible(true);

	}

	public void buscarDetalladoPostergaciones(int tipoBusqueda){
		Integer index = lbxUsuarios.getSelectedIndex();
	}

	public void btnExportar_OnClick(){
		if(lbxDetallado.getItemCount()>0) {
			Session session = getDesktop().getSession();
			HttpSession httpSession = (HttpSession)session.getNativeSession();
			httpSession.setAttribute("parcialPath",Constantes.DIRECTORY_EXCEL+"AnulacionesPostergaciones.xls");
			httpSession.setAttribute("lbxAnulacionesPostergaciones", lbxDetallado);
			Executions.sendRedirect("/exportXlsAnulacionesPostergaciones.htm");
		}
	}
}
