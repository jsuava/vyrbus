package pe.itsb.vyrbus.view.ctrl;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zul.Textbox;

import pe.itsb.vyrbus.model.bean.TipoItinerario;
import pe.itsb.vyrbus.service.exceptions.CancelaGrabacionException;
import pe.itsb.vyrbus.service.exceptions.DenominacionDuplicadaException;
import pe.itsb.vyrbus.service.exceptions.DenominacionNullException;
import pe.itsb.vyrbus.service.exceptions.NombreCortoDuplicadoException;
import pe.itsb.vyrbus.service.exceptions.NombreCortoNullException;
import pe.itsb.vyrbus.service.locator.ServiceLocator;
import pe.itsb.vyrbus.service.util.Constantes;
import pe.itsb.vyrbus.service.util.Messages;
import pe.itsb.vyrbus.service.util.Util;
import pe.itsb.vyrbus.service.util.UtilData;
import pe.itsb.vyrbus.view.ui.DlgMessage;
import pe.itsb.vyrbus.view.ui.WndFiltrarParametros;
import pe.itsb.vyrbus.view.ui.WndOpcionesMantenimiento;

public class WndTipoItinerario extends WndOpcionesMantenimiento {

	private static final long serialVersionUID = -4567920156712129053L;

	private Textbox txtDenominacion;
	private Textbox	txtNombreCorto;

	private TipoItinerario tipoItinerario = null;
	private TreeMap<String, Object> criteriosBusqueda = new TreeMap<>();
	private List<String> criteriosOrdenar = null;

	@Override
	public void onCreate() throws Exception {
		criteriosOrdenar = new ArrayList<>();
		criteriosOrdenar.add("denominacion");
	}

	@Override
	public void initComponents() {
		txtDenominacion = (Textbox) this.getFellow("txtDenominacion");
		txtNombreCorto = (Textbox) this.getFellow("txtNombreCorto");

	}

	@Override
	public void onNew() throws Exception {
		// TODO Auto-generated method stub

	}

	@Override
	public void onSearch() throws Exception {
		final WndFiltrarParametros oWndFiltrar = new WndFiltrarParametros();

		oWndFiltrar.addParameter("Denominación", String.class);
		oWndFiltrar.addParameter("Nombre Corto", String.class);

		this.appendChild(oWndFiltrar);
		oWndFiltrar.setMode("modal");
		oWndFiltrar.addEventListener(pe.itsb.vyrbus.view.ui.Events.ON_FILTER, new EventListener<Event>() {

			@Override
			public void onEvent(Event event) throws Exception {
				String denominacion = (String) oWndFiltrar.getParameterValue("Denominación");
				String nombreCorto = (String) oWndFiltrar.getParameterValue("Nombre Corto");
				String estadoRegistro = Constantes.VALUE_ACTIVO;

				criteriosBusqueda = new TreeMap<>();
				if (denominacion.trim().equals("")) {
					criteriosBusqueda.remove("denominacion");
				}else {criteriosBusqueda.put("denominacion", denominacion + "%");}

				if (nombreCorto.trim().equals("")) {
					criteriosBusqueda.remove("nombreCorto");
				}else {criteriosBusqueda.put("nombreCorto", nombreCorto + "%");}

				criteriosBusqueda.put("estadoRegistro", estadoRegistro);

				listarRegistros(ServiceLocator.getTipoItinerarioManager().buscarPorX(criteriosBusqueda, criteriosOrdenar));
			}
		});

	}

	@Override
	public void onRefresh(int tab) throws Exception {
		if (!criteriosBusqueda.isEmpty()) {
			this.listarRegistros(ServiceLocator.getTipoItinerarioManager().buscarPorX(criteriosBusqueda, criteriosOrdenar));
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
			if (txtDenominacion.getValue().trim()=="")
				throw new DenominacionNullException();
			else if (txtNombreCorto.getValue().trim() =="")
				throw new NombreCortoNullException();

			if (action==ACTION_NEW){
				tipoItinerario=new TipoItinerario();
				tipoItinerario.setId(null);
			}else{
				tipoItinerario.setId(new Integer(textboxId.getValue()));
			}

			tipoItinerario.setDenominacion(txtDenominacion.getValue().trim().toUpperCase());
			tipoItinerario.setNombreCorto(txtNombreCorto.getValue().trim().toUpperCase());
			tipoItinerario.setEstadoRegistro(Constantes.VALUE_ACTIVO);

			switch (action) {
				case ACTION_NEW:
					UtilData.auditarRegistro(tipoItinerario, getUsuario(), Executions.getCurrent());
					ServiceLocator.getTipoItinerarioManager().guardar(tipoItinerario);
				//	textboxId.setText(oConcesionario.getId().toString());
					break;
				case ACTION_MODIFY:
					UtilData.auditarRegistro(tipoItinerario, true, getUsuario(), Executions.getCurrent());
					ServiceLocator.getTipoItinerarioManager().actualizar(tipoItinerario);
					break;
			}

			/*Recupera el registro actualizado o el nuevo*/
			criteriosBusqueda = new TreeMap<>();
			criteriosBusqueda.put("denominacion",tipoItinerario.getDenominacion() + "%");
			criteriosBusqueda.put("estadoRegistro", Constantes.VALUE_ACTIVO);
			listarRegistros(ServiceLocator.getTipoItinerarioManager().buscarPorX(criteriosBusqueda, criteriosOrdenar));


		}catch (DenominacionNullException dnex){
			DlgMessage.information(Messages.getString("Generales.information.noIngresoDenominacion"),txtDenominacion);
			throw new CancelaGrabacionException();
		}catch (NombreCortoNullException ncnex){
			DlgMessage.information(Messages.getString("Generales.information.noIngresoNombreCorto"),txtNombreCorto);
			throw new CancelaGrabacionException();
		}catch (DenominacionDuplicadaException dpex){
			DlgMessage.information(Messages.getString("Generales.information.denominacionDuplicada"),txtDenominacion);
			throw new CancelaGrabacionException();
		}catch (NombreCortoDuplicadoException ncdex){
			DlgMessage.information(Messages.getString("Generales.information.nombreCortoDuplicado"),txtNombreCorto);
			throw new CancelaGrabacionException();
		}

		catch (Exception ex) {
			DlgMessage.error(this.getClass().getName() + " " + ex.getMessage());
			ex.printStackTrace(); throw new CancelaGrabacionException();
		}
	}

	@Override
	public void onClose() {
		closeTabWindow();
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

		ServiceLocator.getTipoItinerarioManager().inactivar(id);

	}

	@Override
	public void onPrint(int tab) throws Exception {
		// TODO Auto-generated method stub

	}

	@Override
	public void onExport(int tab) throws Exception {
		// TODO Auto-generated method stub

	}

	@Override
	public void onHelp() throws Exception {
		// TODO Auto-generated method stub

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

	private void listarRegistros(ArrayList<TipoItinerario> lstRegistros) {
		ArrayList<Object> lstFormasPago = new ArrayList<>();

		for(int r = 0; r < lstRegistros.size(); r ++) {
			TipoItinerario tipoItinerario = lstRegistros.get(r);
			ArrayList<Object> lstFila = new ArrayList<>();

			lstFila.add(tipoItinerario.getId());
			lstFila.add(r + 1);
			lstFila.add(tipoItinerario.getDenominacion());
			lstFila.add(tipoItinerario.getNombreCorto());

			lstFormasPago.add(lstFila);
		}

		Util.llenarListbox(listboxLista, lstFormasPago, true);
	}

	private void mantenimientoRegistro(Long id) throws Exception {
		tipoItinerario = new TipoItinerario();
		tipoItinerario = ServiceLocator.getTipoItinerarioManager().buscarPorId(id);

		textboxId.setText(tipoItinerario.getId().toString());
		txtDenominacion.setValue(tipoItinerario.getDenominacion());
		txtNombreCorto.setValue(tipoItinerario.getNombreCorto());
	}

}
