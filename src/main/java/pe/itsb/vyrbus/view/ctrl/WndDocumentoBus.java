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
import org.zkoss.zul.Datebox;
import org.zkoss.zul.Textbox;

import pe.itsb.vyrbus.model.bean.Bus;
import pe.itsb.vyrbus.model.bean.DocumentoBus;
import pe.itsb.vyrbus.model.bean.EstadoDocumentoBus;
import pe.itsb.vyrbus.model.bean.TipoDocumento;
import pe.itsb.vyrbus.service.exceptions.CancelaGrabacionException;
import pe.itsb.vyrbus.service.exceptions.EstadoDocumentoBusNullException;
import pe.itsb.vyrbus.service.exceptions.FechaExpedicionNullException;
import pe.itsb.vyrbus.service.exceptions.FechaVencimientoNullException;
import pe.itsb.vyrbus.service.exceptions.NumeroBusDuplicadoException;
import pe.itsb.vyrbus.service.exceptions.NumeroBusNullException;
import pe.itsb.vyrbus.service.exceptions.NumeroDocumentoDuplicadoException;
import pe.itsb.vyrbus.service.exceptions.NumeroDocumentoNullException;
import pe.itsb.vyrbus.service.exceptions.TipoDocumentoNullException;
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
 * @author JA
 * @since JDK1.6
 */

public class WndDocumentoBus  extends WndOpcionesMantenimiento{
   private static final long serialVersionUID = -5451880424114680419L;

   private Combobox cboTipoDocumento;
   private Combobox cboBus;
   private Combobox cboEstadoDocumento;
   private Textbox  txtnumeroDocumento;
   private Datebox  dbfechaExpedicion;
   private Datebox 	dbfechaVencimiento;

   private DocumentoBus odocumentoBus=null;

	private TreeMap<String, Object> criteriosBusqueda = new TreeMap<>();
	private List<String> criteriosOrdenar = null;

	@Override
	public void onNew() {
		cboTipoDocumento.setSelectedIndex(0);
		cboBus.setSelectedIndex(0);
		cboEstadoDocumento.setSelectedIndex(0);
	}

	@Override
	public void onCreate() throws Exception {
		TreeMap<String, Object> criteriosBusqueda = new TreeMap<>();
		criteriosBusqueda.put("tipo", TipoDocumento.BUS);
		criteriosBusqueda.put("estadoRegistro", Constantes.VALUE_ACTIVO);
		UtilData.cargarDataCombo(cboTipoDocumento, TipoDocumento.class, criteriosBusqueda, false);
		UtilData.cargarDataCombo(cboBus, Bus.class, false);
		UtilData.cargarDataCombo(cboEstadoDocumento, EstadoDocumentoBus.class, false);
		criteriosOrdenar = new ArrayList<>();
		criteriosOrdenar.add("tipoDocumento");
	}

	@Override
	public void initComponents() {
		cboTipoDocumento = (Combobox) getFellow("cboTipoDocumento");
		cboBus = (Combobox) getFellow("cboBus");
		cboEstadoDocumento = (Combobox) getFellow("cboEstadoDocumento");
		txtnumeroDocumento = (Textbox) getFellow("txtnumeroDocumento");
		dbfechaExpedicion = (Datebox) getFellow("dbfechaExpedicion");
		dbfechaVencimiento = (Datebox) getFellow("dbfechaVencimiento");
	}

	@Override
	public void onSearch() throws Exception {
	   final WndFiltrarParametros oWndFiltrar = new WndFiltrarParametros();

	   oWndFiltrar.addParameter("Estado Documento Bus", EstadoDocumentoBus.class);
	   oWndFiltrar.addParameter("Número de Bus", Bus.class);

	   this.appendChild(oWndFiltrar);
		oWndFiltrar.setMode("modal");
		oWndFiltrar.addEventListener(pe.itsb.vyrbus.view.ui.Events.ON_FILTER, new EventListener<Event>() {

			@Override
			public void onEvent(Event event) throws Exception {
				Integer estadoDocumentoBus = (Integer) oWndFiltrar.getParameterValue("Estado Documento Bus");
				Integer numeroBus = (Integer) oWndFiltrar.getParameterValue("Número de Bus");
				String estadoRegistro = Constantes.VALUE_ACTIVO;

				criteriosBusqueda.remove("numeroDocumento");
				if (estadoDocumentoBus == null || estadoDocumentoBus == 0){
					criteriosBusqueda.remove("estadoDocumentoBus");
				}else{
					EstadoDocumentoBus oestadoDocumentoBus = new EstadoDocumentoBus();
					oestadoDocumentoBus.setId(estadoDocumentoBus);
					criteriosBusqueda.put("estadoDocumentoBus", oestadoDocumentoBus);
				}

				if (numeroBus== null || numeroBus==0) {
					criteriosBusqueda.remove("bus");
				}else{
					Bus obus = new Bus();
					obus.setId(numeroBus);
					criteriosBusqueda.put("bus", obus);
				}

				criteriosBusqueda.put("estadoRegistro", estadoRegistro);
				listarRegistros(ServiceLocator.getDocumentoBusManager().buscarPorX(criteriosBusqueda, criteriosOrdenar));
			}
		});
	}


	@Override
	public void onRefresh(int tab) throws Exception {
		if (!criteriosBusqueda.isEmpty()) {
			this.listarRegistros(ServiceLocator.getDocumentoBusManager().buscarPorX(criteriosBusqueda, criteriosOrdenar));
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
			if (!(cboTipoDocumento.getSelectedItem().getValue() instanceof TipoDocumento))
				 throw new TipoDocumentoNullException();
			else if (!(cboBus.getSelectedItem().getValue() instanceof Bus))
				throw new NumeroBusNullException();
			else if (!(cboEstadoDocumento.getSelectedItem().getValue() instanceof EstadoDocumentoBus))
				throw new EstadoDocumentoBusNullException();
			else if (txtnumeroDocumento.getText().trim().equals(""))
				throw new NumeroDocumentoNullException();
			else if (dbfechaExpedicion.getText().equals(""))
				throw new FechaExpedicionNullException();
			else if (dbfechaVencimiento.getText().equals(""))
				throw new FechaVencimientoNullException();

			if (action==ACTION_NEW)
				odocumentoBus = new DocumentoBus();
			Integer id = (textboxId.getText().equals("") ? 0 : new Integer(textboxId.getText()));

			odocumentoBus.setId(id);

			if (cboTipoDocumento.getSelectedIndex() > -1) {
				TipoDocumento otipoDocumento = new TipoDocumento();
				otipoDocumento.setId(((TipoDocumento) cboTipoDocumento.getSelectedItem().getValue()).getId());
				otipoDocumento.setDenominacion(((TipoDocumento) cboTipoDocumento.getSelectedItem().getValue()).getDenominacion());
				odocumentoBus.setTipoDocumento(otipoDocumento);
			}

			if (cboBus.getSelectedIndex()> -1){
				Bus obus = new Bus();
				obus.setId(((Bus) cboBus.getSelectedItem().getValue()).getId());
				obus.setCodigo( ((Bus) cboBus.getSelectedItem().getValue()).getCodigo());
				odocumentoBus.setBus(obus);
			}

			if (cboEstadoDocumento.getSelectedIndex()>-1){
				EstadoDocumentoBus oestaDocumentoBus = new EstadoDocumentoBus();
				oestaDocumentoBus.setId(((EstadoDocumentoBus) cboEstadoDocumento.getSelectedItem().getValue()).getId());
				oestaDocumentoBus.setDenominacion(((EstadoDocumentoBus) cboEstadoDocumento.getSelectedItem().getValue()).getDenominacion());
				odocumentoBus.setEstadoDocumentoBus(oestaDocumentoBus);
			}

			odocumentoBus.setNumeroDocumento(txtnumeroDocumento.getText().trim().toString());
			odocumentoBus.setFechaExpedicion(dbfechaExpedicion.getText());
			odocumentoBus.setFechaVencimiento(dbfechaVencimiento.getText());
			odocumentoBus.setEstadoRegistro(Constantes.VALUE_ACTIVO);
			switch (action) {
			case ACTION_NEW:
				UtilData.auditarRegistro(odocumentoBus, getUsuario(), Executions.getCurrent());
				ServiceLocator.getDocumentoBusManager().guardar(odocumentoBus);
				textboxId.setText((new Long(odocumentoBus.getId()).toString()));
				break;

			case ACTION_MODIFY:
				UtilData.auditarRegistro(odocumentoBus, true, getUsuario(), Executions.getCurrent());
				ServiceLocator.getDocumentoBusManager().actualizar(odocumentoBus);
				break;
			}
			/*RECUEPRA EL REGISTRO ALCTUALIZADO O EL NUEVO*/
			criteriosBusqueda.remove("estadoDocumentoBus");
			criteriosBusqueda.remove("bus");
			criteriosBusqueda.put("numeroDocumento", odocumentoBus.getNumeroDocumento());
			criteriosBusqueda.put("estadoRegistro", Constantes.VALUE_ACTIVO);
			listarRegistros(ServiceLocator.getDocumentoBusManager().buscarPorX(criteriosBusqueda, criteriosOrdenar));

		}catch (TipoDocumentoNullException tdnex){
			DlgMessage.information(Messages.getString("WndPersonal.information.TipoDocumento"),cboTipoDocumento);
			throw new CancelaGrabacionException();
		}catch (NumeroBusNullException nbnex){
			DlgMessage.information(Messages.getString("WndBus.information.NumeroBus"),cboBus);
			throw new CancelaGrabacionException();
		}catch (EstadoDocumentoBusNullException ednex){
			DlgMessage.information(Messages.getString("WndDucmentoBus.information.EstadoDocumento"),cboEstadoDocumento);
			throw new CancelaGrabacionException();
		}catch (NumeroDocumentoNullException ndnex){
			DlgMessage.information(Messages.getString("WndPersonal.information.NumeroDocuento"),txtnumeroDocumento);
			throw new CancelaGrabacionException();
		}catch (FechaExpedicionNullException fenex){
			DlgMessage.information(Messages.getString("WndDucmentoBus.information.FechaExpedicion"),dbfechaExpedicion);
			throw new CancelaGrabacionException();
		}catch (FechaVencimientoNullException fvnex){
			DlgMessage.information(Messages.getString("WndDucmentoBus.information.EstadoVencimiento"),dbfechaVencimiento);
			throw new CancelaGrabacionException();
		}catch (NumeroDocumentoDuplicadoException nddex){
			DlgMessage.information(Messages.getString("WndPersonal.information.NumeroDocumentoDuplicado"),txtnumeroDocumento);
			throw new CancelaGrabacionException();
		}catch (NumeroBusDuplicadoException nbdex){
			DlgMessage.information(Messages.getString("WndDucmentoBus.information.TipoDocBusDuplicado"),cboTipoDocumento);
			throw new CancelaGrabacionException();
		}catch (Exception ex){
			DlgMessage.error(this.getClass().getName()+" "+ex.getMessage());
			ex.printStackTrace(); throw new CancelaGrabacionException();
		}

	}


	@Override
	public void onDelete(int tab) throws Exception {
		Long id = (long) 0;
		id = new Long((String) listboxLista.getSelectedItem().getValue());

		DocumentoBus documentoBus=ServiceLocator.getDocumentoBusManager().buscarPorId(id);
		documentoBus.setEstadoRegistro(Constantes.VALUE_INACTIVO);
		UtilData.auditarRegistro(documentoBus, true, getUsuario(), Executions.getCurrent());
		ServiceLocator.getDocumentoBusManager().actualizar(documentoBus);
//		ServiceLocator.getDocumentoBusManager().inactivar(id);
	}

	@Override
	public void onClose() {
		closeTabWindow();
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

	private void listarRegistros(ArrayList<DocumentoBus> lstRegistros) {
		ArrayList<Object> lstDocumentos = new ArrayList<>();

		for(int r = 0; r < lstRegistros.size(); r ++) {
			DocumentoBus odocumentoBus = lstRegistros.get(r);
			ArrayList<Object> lstFila = new ArrayList<>();

			lstFila.add(odocumentoBus.getId());
			lstFila.add(r + 1);
			lstFila.add(odocumentoBus.getTipoDocumento().getDenominacion());
			lstFila.add(odocumentoBus.getBus().getCodigo());
			lstFila.add(odocumentoBus.getEstadoDocumentoBus().getDenominacion());
			lstFila.add(odocumentoBus.getNumeroDocumento());
			lstFila.add(odocumentoBus.getFechaExpedicion());
			lstFila.add(odocumentoBus.getFechaVencimiento());

			lstDocumentos.add(lstFila);
		}

		Util.llenarListbox(listboxLista, lstDocumentos, true);
	}

	private void mantenimientoRegistro(Long id) throws Exception {
		odocumentoBus = ServiceLocator.getDocumentoBusManager().buscarPorId(id);

		TipoDocumento otipoDocumento = odocumentoBus.getTipoDocumento();
		Bus obus = odocumentoBus.getBus();
		EstadoDocumentoBus oestadoDocumentoBus = odocumentoBus.getEstadoDocumentoBus();

		textboxId.setText(String.valueOf(odocumentoBus.getId()));
		if (otipoDocumento != null){
			Util.seleccionarValorItemCombo(TipoDocumento.class, cboTipoDocumento, otipoDocumento.getId());
		}
		if (obus != null){
			Util.seleccionarValorItemCombo(Bus.class, cboBus, obus.getId());
		}
		if (oestadoDocumentoBus != null){
			Util.seleccionarValorItemCombo(EstadoDocumentoBus.class, cboEstadoDocumento, oestadoDocumentoBus.getId());
		}

		txtnumeroDocumento.setText(odocumentoBus.getNumeroDocumento());
		dbfechaExpedicion.setText(odocumentoBus.getFechaExpedicion());
		dbfechaVencimiento.setText(odocumentoBus.getFechaVencimiento());
	}
}
