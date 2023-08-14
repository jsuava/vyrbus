/**
 * Proyecto		: SISVYR
 * Sistema		: Sistema de Ventas y Reservas
 * Descripción	:
 * Autor		: José Avalos
 * Fecha		: 25/08/2012
 */
package pe.itsb.vyrbus.view.ctrl;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Textbox;

import pe.itsb.vyrbus.model.bean.EstadoDocumentoBus;
import pe.itsb.vyrbus.service.exceptions.CancelaGrabacionException;
import pe.itsb.vyrbus.service.exceptions.DenominacionDuplicadaException;
import pe.itsb.vyrbus.service.exceptions.DenominacionNullException;
import pe.itsb.vyrbus.service.exceptions.TipoEstadoNullException;
import pe.itsb.vyrbus.service.locator.ServiceLocator;
import pe.itsb.vyrbus.service.util.Constantes;
import pe.itsb.vyrbus.service.util.Messages;
import pe.itsb.vyrbus.service.util.Util;
import pe.itsb.vyrbus.service.util.UtilData;
import pe.itsb.vyrbus.view.ui.DlgMessage;
import pe.itsb.vyrbus.view.ui.WndFiltrarParametros;
import pe.itsb.vyrbus.view.ui.WndOpcionesMantenimiento;

/**
 *
 * @author José Avalos
 * @since JDK1.6
 */
public class WndEstadoDocumentoBus extends WndOpcionesMantenimiento {

	private static final long serialVersionUID = 7015174319519567765L;

	private Textbox 	txtdenominacion;
	private Textbox 	txtcodigo;
	private Combobox 	cbotipoEstado;

	private EstadoDocumentoBus oestadoDocumentoBus = null;

	private TreeMap<String, Object> criteriosBusqueda = new TreeMap<>();
	private List<String> criteriosOrdenar=null;


	@Override
	public void initComponents() {
		txtdenominacion = (Textbox) getFellow("txtdenominacion");
		txtcodigo = (Textbox) getFellow("txtcodigo");
		cbotipoEstado = (Combobox) getFellow("cbotipoEstado");
	}


	@Override
	public void onCreate() throws Exception {
		UtilData.cargarTipoEstado(cbotipoEstado);
		criteriosOrdenar = new ArrayList<>();
		criteriosOrdenar.add("denominacion");
	}


	@Override
	public void onNew() {
		cbotipoEstado.setSelectedIndex(0);
	}

	@Override
	public void onSearch() {
		final WndFiltrarParametros oWndFiltrar = new WndFiltrarParametros();

		oWndFiltrar.addParameter("CODIGO", String.class);
		oWndFiltrar.addParameter("DENOMINACION", String.class);

		this.appendChild(oWndFiltrar);
		oWndFiltrar.setMode("modal");
		oWndFiltrar.addEventListener(pe.itsb.vyrbus.view.ui.Events.ON_FILTER, new EventListener<Event>() {
			@Override
			public void onEvent(Event event) throws Exception {
				String codigo = oWndFiltrar.getParameterValue("CODIGO").toString().trim();
				String denominacion = oWndFiltrar.getParameterValue("DENOMINACION").toString().trim();
				String estadoRegistro = Constantes.VALUE_ACTIVO;

				if (denominacion.trim().equals("")){
					criteriosBusqueda.remove("denominacion");
				}else{criteriosBusqueda.put("denominacion", "%" + denominacion + "%");}

				if (codigo.trim().equals("")){
					criteriosBusqueda.remove("codigo");
				}else{criteriosBusqueda.put("codigo", "%"  + codigo + "%");}

				criteriosBusqueda.put("estadoRegistro", estadoRegistro);
				listarRegistros(ServiceLocator.getEstadoDocumentoBusManager().buscarPorX(criteriosBusqueda, criteriosOrdenar));
			}

		});

	}

	@Override
	public void onRefresh(int tab) throws Exception {
		if (!criteriosBusqueda.isEmpty()){
			this.listarRegistros(ServiceLocator.getEstadoDocumentoBusManager().buscarPorX(criteriosBusqueda, criteriosOrdenar));
		}
	}

	@Override
	public void onModify(int tab) throws Exception {
		Long id = new Long(0);
		id = new Long((String) listboxLista.getSelectedItem().getValue());
		this.mantenimientoRegistro(id);
	}

	@Override
	public void onCancel(int action) throws Exception {
		// TODO Auto-generated method stub
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
			if (txtdenominacion.getText().trim().equals(""))
				throw new DenominacionNullException();
			else if (cbotipoEstado.getSelectedIndex()==0)
				throw new TipoEstadoNullException();

			if (action==ACTION_NEW)
				oestadoDocumentoBus = new EstadoDocumentoBus();

			Integer id = (textboxId.getText().equals("") ? 0 : new Integer(textboxId.getText()));

			oestadoDocumentoBus.setId(id);

			if (cbotipoEstado.getSelectedIndex() > -1) {
				oestadoDocumentoBus.setTipoEstado((Integer) cbotipoEstado.getSelectedItem().getValue());
			}
			oestadoDocumentoBus.setDenominacion(txtdenominacion.getText().trim().toUpperCase());
			oestadoDocumentoBus.setCodigo(txtcodigo.getText().trim().toUpperCase());
			oestadoDocumentoBus.setEstadoRegistro(Constantes.VALUE_ACTIVO);

			switch (action) {
			case ACTION_NEW:
				UtilData.auditarRegistro(oestadoDocumentoBus, getUsuario(), Executions.getCurrent());
				ServiceLocator.getEstadoDocumentoBusManager().guardar(oestadoDocumentoBus);
				textboxId.setText((new Long(oestadoDocumentoBus.getId()).toString()));
				break;
			case ACTION_MODIFY:
				UtilData.auditarRegistro(oestadoDocumentoBus, true, getUsuario(), Executions.getCurrent());
				ServiceLocator.getEstadoDocumentoBusManager().actualizar(oestadoDocumentoBus);
				break;
			}
			/*RECUPERA EL REGISTRO ACTUALIZADO O EL NUEVO*/
			criteriosBusqueda.remove("denominacion");
			criteriosBusqueda.remove("codigo");
			criteriosBusqueda.put("denominacion", oestadoDocumentoBus.getDenominacion());
			criteriosBusqueda.put("estadoRegistro", Constantes.VALUE_ACTIVO);
			listarRegistros(ServiceLocator.getEstadoDocumentoBusManager().buscarPorX(criteriosBusqueda, criteriosOrdenar));

		}catch (DenominacionNullException dnex){
			DlgMessage.information(Messages.getString("Denominacion"),txtdenominacion);
			throw new CancelaGrabacionException();
		}catch (TipoEstadoNullException tenex){
			DlgMessage.information(Messages.getString("WndEstadoDocumentosBus.information.TipoEstado"),cbotipoEstado);
			throw new CancelaGrabacionException();
		}catch (DenominacionDuplicadaException dnex){
			DlgMessage.information(Messages.getString("DenominacionDuplicada"),txtdenominacion);
			throw new CancelaGrabacionException();
		}catch (Exception ex){
			DlgMessage.error(this.getClass().getName()+" "+ex.getMessage());
			ex.printStackTrace(); throw new CancelaGrabacionException();
		}

	}

	@Override
	public void onDelete(int tab) throws Exception {
		Long id = (long) 0;

		switch (tab) {
			case TAB_LIST:
				id = new Long((String) listboxLista.getSelectedItem().getValue());
				break;

			case TAB_MAINTENANCE:
				id = new Long(textboxId.getText());
				break;
		}
		ServiceLocator.getEstadoDocumentoBusManager().buscarPorId(id);

	}

	@Override
	public void onPrint(int tab) {


	}

	@Override
	public void onExport(int tab) {


	}

	@Override
	public void onHelp() {


	}

	@Override
	public void onChangeTab(int tab) throws Exception {
		switch (tab) {
		case TAB_LIST:
			break;

		case TAB_MAINTENANCE:
			if (listboxLista.getSelectedIndex() > -1) {
				this.mantenimientoRegistro(new Long((String) listboxLista.getSelectedItem().getValue()));
			}
			break;
		}
	}

	@Override
	public void onClose() {
		closeTabWindow();
	}

   public void listarRegistros (ArrayList<EstadoDocumentoBus> listRegistro ) {
	   ArrayList<Object> listEstadoDocumentoBus = new ArrayList<>();

	   for (int r =0; r < listRegistro.size(); r++ ) {
		   EstadoDocumentoBus oestadoDocumentoBus = listRegistro.get(r);
		   ArrayList<Object> listFila = new  ArrayList<>();

		   listFila.add(oestadoDocumentoBus.getId());
		   listFila.add(r + 1);//Item
		   listFila.add(oestadoDocumentoBus.getDenominacion());
		   listFila.add(oestadoDocumentoBus.getCodigo());
		   listFila.add(oestadoDocumentoBus.getTipoEstado());

		   listEstadoDocumentoBus.add(listFila);
	   }
	   Util.llenarListbox(listboxLista, listEstadoDocumentoBus, true);
   }


   private void mantenimientoRegistro(Long id) throws Exception {
	   oestadoDocumentoBus = ServiceLocator.getEstadoDocumentoBusManager().buscarPorId(id);

	   textboxId.setText(oestadoDocumentoBus.getId().toString());
	   txtdenominacion.setText(oestadoDocumentoBus.getDenominacion());
	   txtcodigo.setText(oestadoDocumentoBus.getCodigo());
	   Util.seleccionarValorItemCombobox(cbotipoEstado, oestadoDocumentoBus.getTipoEstado());

   }

}
