/**
 * Proyecto		: SISVYR
 * Sistema		: Sistema de Ventas y Reservas
 * Descripción	:
 * Autor		: José Avalos
 * Fecha		: 19/04/2013
 */
package pe.itsb.vyrbus.view.ctrl;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Textbox;

import pe.itsb.vyrbus.model.bean.MotivoTemporadaAlta;
import pe.itsb.vyrbus.service.exceptions.CancelaGrabacionException;
import pe.itsb.vyrbus.service.exceptions.DenominacionDuplicadaException;
import pe.itsb.vyrbus.service.exceptions.DenominacionNullException;
import pe.itsb.vyrbus.service.locator.ServiceLocator;
import pe.itsb.vyrbus.service.util.Constantes;
import pe.itsb.vyrbus.service.util.Messages;
import pe.itsb.vyrbus.service.util.Util;
import pe.itsb.vyrbus.service.util.UtilData;
import pe.itsb.vyrbus.view.ui.DlgMessage;
import pe.itsb.vyrbus.view.ui.WndFiltrarParametros;
import pe.itsb.vyrbus.view.ui.WndOpcionesMantenimiento;

/**
 * @author JABANTO
 *
 */
public class WndMotivoTemporadaAlta extends WndOpcionesMantenimiento {
	private static final long serialVersionUID = 1L;

	private Textbox txtNombreMotivo;

	private TreeMap<String, Object> criteriosBusqueda = new TreeMap<>();
	private List<String> criteriosOrdenar = null;

	private MotivoTemporadaAlta motivoTemporadaAlta=null;

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.view.ui.WndBase#initComponents()
	 */
	@Override
	public void initComponents() {
		txtNombreMotivo=(Textbox)this.getFellow("txtNombreMotivo");
	}

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.view.ui.WndBase#onCreate()
	 */
	@Override
	public void onCreate() throws Exception {
		criteriosOrdenar = new ArrayList<>();
		criteriosOrdenar.add("nombreMotivo");
	}


	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.view.ui.IOpcionesMantenimiento#onNew()
	 */
	@Override
	public void onNew() throws Exception {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.view.ui.IOpcionesMantenimiento#onSearch()
	 */
	@Override
	public void onSearch() throws Exception {
		criteriosBusqueda = new TreeMap<>();
		final WndFiltrarParametros oWndFiltrar = new WndFiltrarParametros();

		oWndFiltrar.addParameter("Nombre Motivo", String.class);

		this.appendChild(oWndFiltrar);
		oWndFiltrar.setMode("modal");
		oWndFiltrar.addEventListener(pe.itsb.vyrbus.view.ui.Events.ON_FILTER, new EventListener<Event>() {

			@Override
			public void onEvent(Event event) throws Exception {
				String nombreMotivo = (String) oWndFiltrar.getParameterValue("Nombre Motivo");
				String estadoRegistro = Constantes.VALUE_ACTIVO;

				if(nombreMotivo.trim().equals("")){
					criteriosBusqueda.remove("nombreMotivo");
				}else{criteriosBusqueda.put("nombreMotivo", "%" + nombreMotivo + "%");}

				criteriosBusqueda.put("estadoRegistro", estadoRegistro);

				listarRegistros(ServiceLocator.getMotivoTemporadaAltaManager().buscarPorX(criteriosBusqueda, criteriosOrdenar));
			}
		});

	}

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.view.ui.IOpcionesMantenimiento#onRefresh(int)
	 */
	@Override
	public void onRefresh(int tab) throws Exception {
		if (!criteriosBusqueda.isEmpty()) {
			this.listarRegistros(ServiceLocator.getMotivoTemporadaAltaManager().buscarPorX(criteriosBusqueda, criteriosOrdenar));
		}

	}

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.view.ui.IOpcionesMantenimiento#onModify(int)
	 */
	@Override
	public void onModify(int tab) throws Exception {

		Integer id = ((MotivoTemporadaAlta) listboxLista.getSelectedItem().getValue()).getId();
		this.mantenimientoRegistro(id);

	}

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.view.ui.IOpcionesMantenimiento#onCancel(int)
	 */
	@Override
	public void onCancel(int action) throws Exception {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.view.ui.IOpcionesMantenimiento#onSave(int)
	 */
	@Override
	public void onSave(int action) throws Exception {
		try{
			if (txtNombreMotivo.getText().trim().isEmpty())
				throw new DenominacionNullException();
			if (action==ACTION_NEW)
				motivoTemporadaAlta = new MotivoTemporadaAlta();

			motivoTemporadaAlta.setNombreMotivo(txtNombreMotivo.getText().trim().toUpperCase());
			motivoTemporadaAlta.setEstadoRegistro(Constantes.VALUE_ACTIVO);

			 switch (action) {
				case ACTION_NEW:
					UtilData.auditarRegistro(motivoTemporadaAlta, getUsuario(), Executions.getCurrent());
					ServiceLocator.getMotivoTemporadaAltaManager().guardar(motivoTemporadaAlta);
					textboxId.setText(motivoTemporadaAlta.getId().toString());
					break;

				case ACTION_MODIFY:
					UtilData.auditarRegistro(motivoTemporadaAlta, true, getUsuario(), Executions.getCurrent());
					ServiceLocator.getMotivoTemporadaAltaManager().actualizar(motivoTemporadaAlta);
					break;
			}
			 /*Recupera el registro actualizado o el Nuevo*/
			criteriosBusqueda = new TreeMap<>();
			criteriosBusqueda.put("id", motivoTemporadaAlta.getId());
			listarRegistros(ServiceLocator.getMotivoTemporadaAltaManager().buscarPorX(criteriosBusqueda, criteriosOrdenar));

		}catch (DenominacionDuplicadaException ddex){
			DlgMessage.information(Messages.getString("wndMotivoTemporadaAlta.Information.NombreMotivoDuplicado"),txtNombreMotivo);
			throw new CancelaGrabacionException();
		}catch(DenominacionNullException dnex){
			DlgMessage.information(Messages.getString("wndMotivoTemporadaAlta.Information.NombreMotivoNull"),txtNombreMotivo);
			throw new CancelaGrabacionException();
		}catch (Exception ex) {
			DlgMessage.error(this.getClass().getName()+" "+ex.getMessage());
			ex.printStackTrace(); throw new CancelaGrabacionException();
		}

	}

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.view.ui.IOpcionesMantenimiento#onDelete(int)
	 */
	@Override
	public void onDelete(int tab) throws Exception {
		Integer id= ((MotivoTemporadaAlta)listboxLista.getSelectedItem().getValue()).getId();
		ServiceLocator.getMotivoTemporadaAltaManager().inactivar(id.longValue());

	}

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.view.ui.IOpcionesMantenimiento#onPrint(int)
	 */
	@Override
	public void onPrint(int tab) throws Exception {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.view.ui.IOpcionesMantenimiento#onExport(int)
	 */
	@Override
	public void onExport(int tab) throws Exception {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.view.ui.IOpcionesMantenimiento#onHelp()
	 */
	@Override
	public void onHelp() throws Exception {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.view.ui.IOpcionesMantenimiento#onChangeTab(int)
	 */
	@Override
	public void onChangeTab(int tab) throws Exception {
		switch (tab) {
			case TAB_LIST:
				break;

			case TAB_MAINTENANCE:
				if (listboxLista.getSelectedIndex() > -1) {
					this.mantenimientoRegistro(((MotivoTemporadaAlta)listboxLista.getSelectedItem().getValue()).getId());
				}
				break;
		}

	}

	/* (non-Javadoc)
	 * @see org.zkoss.zul.Window#onClose()
	 */
	@Override
	public void onClose() {
			closeTabWindow();
	}

	private void listarRegistros(ArrayList<MotivoTemporadaAlta> lstRegistros) {
		Listitem item=null;
		Listcell cell=null;
		int x=0;
		Util.limpiarListbox(listboxLista);

		for(MotivoTemporadaAlta motivoTemporadaAlta: lstRegistros){
			x++;
			item=new Listitem();
			cell=new Listcell(String.valueOf(x));
			cell.setStyle("font-size:11px !important");
			item.appendChild(cell);
			cell=new Listcell(motivoTemporadaAlta.getNombreMotivo());
			item.appendChild(cell);

			item.setValue(motivoTemporadaAlta);
			listboxLista.appendChild(item);
		}
	}

	private void mantenimientoRegistro(Integer id) throws Exception {
		motivoTemporadaAlta = new MotivoTemporadaAlta();
		motivoTemporadaAlta = ServiceLocator.getMotivoTemporadaAltaManager().buscarPorId(id.longValue());

		textboxId.setText(motivoTemporadaAlta.getId().toString());
		txtNombreMotivo.setText(motivoTemporadaAlta.getNombreMotivo());

	}

}
