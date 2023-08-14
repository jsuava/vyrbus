/**
 * Proyecto		: SISVYR
 * Sistema		: Sistema de Ventas y Reservas
 * Descripción	:
 * Autor		: José Avalos Sullo
 * Fecha		: 28/04/2014
 */
package pe.itsb.vyrbus.view.ctrl;

import java.util.List;
import java.util.TreeMap;

import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Toolbarbutton;

import pe.itsb.vyrbus.model.bean.DetalleManifiesto;
import pe.itsb.vyrbus.model.bean.VentaPasaje;
import pe.itsb.vyrbus.service.locator.ServiceLocator;
import pe.itsb.vyrbus.service.util.Constantes;
import pe.itsb.vyrbus.service.util.Messages;
import pe.itsb.vyrbus.service.util.Util;
import pe.itsb.vyrbus.service.util.UtilData;
import pe.itsb.vyrbus.view.ui.DlgMessage;
import pe.itsb.vyrbus.view.ui.WndBase;

/**
 * @author JABANTO
 *
 */
public class WndLiberaBoletoManifiesto extends WndBase {
	private static final long serialVersionUID = 1L;
	private Textbox txtNroBoleto;
	private Listbox lsbxPasajeros;


	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.view.ui.WndBase#onCreate()
	 */
	@Override
	public void onCreate() throws Exception {

	}


	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.view.ui.WndBase#initComponents()
	 */
	@Override
	public void initComponents() {
		txtNroBoleto=(Textbox)this.getFellow("txtNroBoleto");
		lsbxPasajeros=(Listbox)this.getFellow("lsbxPasajeros");
	}


	public void buscarBoleto() throws Exception, Exception{
		Util.limpiarListbox(lsbxPasajeros);

		if(!(txtNroBoleto.getText().trim().isEmpty())){
			String numeroBoleto=Util.autocompleNumberBoleto(txtNroBoleto.getText().trim());
			txtNroBoleto.setText(numeroBoleto);
			List<VentaPasaje>listVenta=ServiceLocator.getVentaPasajesManager().buscarBoletoLiberarManifiesto(numeroBoleto);

			Listitem item=null;
			Listcell cell=null;
			String styleFont="font-size:11PX !important";

			for(VentaPasaje ventaPasaje:listVenta){
				item=new Listitem();
				cell=new Listcell(ventaPasaje.getNumeroBoleto());
				cell.setStyle(styleFont);
				item.appendChild(cell);
				cell=new Listcell(ventaPasaje.getPasajero().toString());
				item.appendChild(cell);
				cell=new Listcell(ventaPasaje.getPasajero().getTipoDocumento().getDenominacion());
				item.appendChild(cell);
				cell=new Listcell(ventaPasaje.getPasajero().getNumeroDocumento()!=null?ventaPasaje.getPasajero().getNumeroDocumento():"");
				cell.setStyle(styleFont);
				item.appendChild(cell);
				cell=new Listcell(ventaPasaje.getRuta().toString());
				item.appendChild(cell);
				cell=new Listcell(ventaPasaje.getNumeroAsiento().toString());
				cell.setStyle(styleFont);
				item.appendChild(cell);
				cell=new Listcell(ventaPasaje.getAgenciaPartida().getNombreCorto());
				item.appendChild(cell);

				Toolbarbutton btnLiberar=new Toolbarbutton("Liberar boleto");
				btnLiberar.setTooltiptext("Click para liberar el Boleto del Manifiesto.");
				btnLiberar.setStyle("text-transform:lowercase; color:blue;font-size:12px !important");
				btnLiberar.setId(ventaPasaje.getId().toString());
				cell=new Listcell();
				cell.appendChild(btnLiberar);
				item.appendChild(cell);
				btnLiberar.addEventListener(Events.ON_CLICK, new EventListener<Event>() {
					@Override
					public void onEvent(Event event) throws Exception {
						// TODO Auto-generated method stub
						liberarBoletoManifiesto(Long.valueOf(event.getTarget().getId()));
					}
				});

				item.setValue(ventaPasaje);
				lsbxPasajeros.appendChild(item);
			}
		}
	}


	private void liberarBoletoManifiesto(final Long idVenta) throws Exception{
		Messagebox.show(Messages.getString("WndLiberarBoletoMAnifiesto.question.confirmLiberarcion"), DlgMessage.NOMBREAPLICACION+" CONSIMACIÓN", DlgMessage.BTN_YESNO, Messagebox.QUESTION, Messagebox.NO, new EventListener<Event>() {
			@Override
			public void onEvent(Event e) throws Exception{
				if(e.getName().equals(Messagebox.ON_YES)){
					//Realiza la busqueda para la liberacion del manifiesto.
					TreeMap<String, Object>criteriosBusqueda=new TreeMap<>();
					criteriosBusqueda.put("ventaPasaje", new VentaPasaje(idVenta));
					criteriosBusqueda.put("estadoRegistro", Constantes.VALUE_ACTIVO);
					List<DetalleManifiesto> listDetalleManifiesto =ServiceLocator.getDetalleManifiestoManager().buscarPorX(criteriosBusqueda, null);

					for(DetalleManifiesto detalleManifiesto: listDetalleManifiesto){
						//Valida que el manifiesto del que se va a liberar el boleto este activo
						if(detalleManifiesto.getManifiesto().getEstadoRegistro().equals(Constantes.VALUE_ACTIVO)){
							detalleManifiesto.setEstadoRegistro(Constantes.VALUE_INACTIVO);
							UtilData.auditarRegistro(detalleManifiesto,true, getUsuario(), Executions.getCurrent());
							ServiceLocator.getDetalleManifiestoManager().actualizar(detalleManifiesto);

							Util.limpiarListbox(lsbxPasajeros);
							DlgMessage.information(Messages.getString("WndLiberarBoletoMAnifiesto.onformation.confirmLiberarcion"),txtNroBoleto);
						}
					}

				}
			}
		});

	}
}
