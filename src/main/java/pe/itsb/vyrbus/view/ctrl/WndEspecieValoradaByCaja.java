package pe.itsb.vyrbus.view.ctrl;

import java.util.List;

import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zul.Button;
import org.zkoss.zul.Column;
import org.zkoss.zul.Columns;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Comboitem;
import org.zkoss.zul.Div;
import org.zkoss.zul.Grid;
import org.zkoss.zul.Label;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Longbox;
import org.zkoss.zul.Row;
import org.zkoss.zul.Rows;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

import pe.itsb.vyrbus.model.bean.Agencia;
import pe.itsb.vyrbus.model.bean.ControlEspecieValorada;
import pe.itsb.vyrbus.model.bean.ControlEspecieValoradaID;
import pe.itsb.vyrbus.model.bean.Empresa;
import pe.itsb.vyrbus.model.bean.TipoComprobante;
import pe.itsb.vyrbus.model.bean.Usuario;
import pe.itsb.vyrbus.model.bean.UsuarioHardware;
import pe.itsb.vyrbus.service.exceptions.AgenciaNullException;
import pe.itsb.vyrbus.service.exceptions.CancelaGrabacionException;
import pe.itsb.vyrbus.service.exceptions.CorrelativoException;
import pe.itsb.vyrbus.service.exceptions.EmpresaException;
import pe.itsb.vyrbus.service.exceptions.NumeroSerieNoRegistradaException;
import pe.itsb.vyrbus.service.exceptions.NumeroSerieNullException;
import pe.itsb.vyrbus.service.exceptions.RangoEspecieValoradaRegistradaException;
import pe.itsb.vyrbus.service.exceptions.TipoComprobanteNullException;
import pe.itsb.vyrbus.service.exceptions.UsuarioHardwareNullException;
import pe.itsb.vyrbus.service.locator.ServiceLocator;
import pe.itsb.vyrbus.service.util.Constantes;
import pe.itsb.vyrbus.service.util.Messages;
import pe.itsb.vyrbus.service.util.Util;
import pe.itsb.vyrbus.service.util.UtilData;
import pe.itsb.vyrbus.view.ui.DlgMessage;
import pe.itsb.vyrbus.view.ui.WndOpcionesMantenimiento;

/**
 *
 * @author Jose Avalos
 *
 */
public class WndEspecieValoradaByCaja extends WndOpcionesMantenimiento {
	private static final long serialVersionUID = 2208129491803334928L;

	private Combobox cmbTipoComprobante;
	private Combobox cmbUsuarioHardware;
	private Textbox	txtSerie;
	private Combobox cmbAgencia;
	private Longbox lgbxCorrelativoInicio;
	private Combobox cmbEmpresa;

	private ControlEspecieValorada controlEspecieValorada = null;
	private Window wndSearch = null;


	/*Variables para la busqueda*/
	private Integer idEmpresa=null;
	private Integer idAgencia=null;
	private Integer idTipoComprobante=null;
	private Integer idUsuarioHardware=null;

	@Override
	public void onCreate() throws Exception {

		/*carga solamente las tepsa*/
		UtilData.cargarAgenciaXtipoAgencia(cmbAgencia, Constantes.ID_TIPAGE_TEPSA, false);
		UtilData.cargarDataCombo(cmbEmpresa, Empresa.class, false);

		/**Solo se habilita este control para el rol Superusuario*/
		cmbAgencia.setDisabled(Constantes.ID_ROL_SUPER_USUARIO!=getRol().getId().intValue());
		/********************************/

		//Carga por default las especies valoradas registradas en la agencia, asociadas al usuario hardware.
		idAgencia=getAgencia().getId();
		idUsuarioHardware=getUsuarioHardware().getId();
		listarRegistros(ServiceLocator.getControlEspecieValoradaManager().buscarEspecieValoradas(idAgencia, idTipoComprobante, idUsuarioHardware, null));
	}

	@Override
	public void initComponents() {
		cmbTipoComprobante = (Combobox) this.getFellow("cmbTipoComprobante");
		cmbUsuarioHardware = (Combobox) this.getFellow("cmbUsuarioHardware");
		txtSerie = (Textbox) this.getFellow("txtSerie");
		cmbAgencia=(Combobox)getFellow("cmbAgencia");
		cmbEmpresa=(Combobox)getFellow("cmbEmpresa");
		lgbxCorrelativoInicio = (Longbox)getFellow("lgbxCorrelativoInicio");
	}

	@Override
	public void onNew() throws Exception {
		Usuario usuario=ServiceLocator.getUsuarioManager().buscarXId(getUsuario().getId().longValue());
		if(usuario.getAgencia()==null)
			Util.seleccionarValorItemCombo(Agencia.class, cmbAgencia, getAgencia().getId());
		else
			Util.seleccionarValorItemCombo(Agencia.class, cmbAgencia, usuario.getAgencia().getId());

		cmbEmpresa.setSelectedIndex(0);
		cmbTipoComprobante.getItems().clear();
		cmbUsuarioHardware.getItems().clear();
		UtilData.cargarGenericData(cmbUsuarioHardware, false);
		UtilData.cargarGenericData(cmbTipoComprobante, false);

		if(cmbAgencia.getSelectedItem()!=null){
			/*carga los usuarios hardware segun la agencia seleccionada*/
			UtilData.cargarUsuarioHasrdware(cmbUsuarioHardware, false, ((Agencia)cmbAgencia.getSelectedItem().getValue()));
		}else
			cmbAgencia.setSelectedIndex(0);

		onLoadTipoComprobante(cmbTipoComprobante);

		cmbTipoComprobante.setSelectedIndex(0);
		lgbxCorrelativoInicio.setValue(0L);

		//Por defecto selecciona el usuario harware donde se ingresa al sistema.
		Util.seleccionarValorItemCombo(UsuarioHardware.class, cmbUsuarioHardware, getUsuarioHardware().getId());
//		if(!(cmbUsuarioHardware.getSelectedItem().getValue() instanceof UsuarioHardware))
//			cmbUsuarioHardware.setSelectedIndex(0);

		/**Habilita controles para el rol super usuario*/
		cmbAgencia.setDisabled( !(getRol().getId().equals(Constantes.ID_ROL_SUPER_USUARIO)));
		cmbUsuarioHardware.setDisabled(!(getRol().getId().equals(Constantes.ID_ROL_SUPER_USUARIO)));
	}

	@Override
	public void onSearch() throws Exception {
		wndSearch = createWindowSearch();
		this.appendChild(wndSearch);
		wndSearch.setMode("modal");
	}

	/*
	 * (non-Javadoc)
	 * @see com.tepsa.sisvyr.view.ui.IOpcionesMantenimiento#onRefresh(int)
	 */
	@Override
	public void onRefresh(int tab) throws Exception {
		Util.limpiarListbox(listboxLista);
		listarRegistros(ServiceLocator.getControlEspecieValoradaManager().buscarEspecieValoradas(idAgencia, idTipoComprobante,idUsuarioHardware, idEmpresa));
	}

	@Override
	public void onModify(int tab) throws Exception {
		mantenimiento();
	}

	@Override
	public void onCancel(int action) throws Exception {
		// TODO Auto-generated method stub

	}

	@Override
	public void onSave(int action) throws Exception {
		String msn="";
		try{
			if (!(cmbEmpresa.getSelectedItem().getValue() instanceof Empresa))
				throw new EmpresaException();
			else if (!(cmbAgencia.getSelectedItem().getValue() instanceof Agencia))
				throw new AgenciaNullException();
			else if (!(cmbUsuarioHardware.getSelectedItem().getValue() instanceof UsuarioHardware))
				throw new UsuarioHardwareNullException();
			else if (!(cmbTipoComprobante.getSelectedItem().getValue() instanceof TipoComprobante))
				throw new TipoComprobanteNullException();
			else if (txtSerie.getText().trim()==null || txtSerie.getText().trim().equals(0))
				throw new NumeroSerieNullException();
			else if (lgbxCorrelativoInicio.getValue()<=0)
                throw new CorrelativoException(CorrelativoException.ACTUAL_NULL);

			String correlativo = "";
			if(((TipoComprobante)cmbTipoComprobante.getSelectedItem().getValue()).getId()==Constantes.ID_TIPCOM_BOLETA_VENTA)
				correlativo = "SEQ_AGE"+((Agencia)cmbAgencia.getSelectedItem().getValue()).getId()+((Empresa)cmbEmpresa.getSelectedItem().getValue()).getSigla()+"BOL"+txtSerie.getText().trim().toUpperCase();
			else if(((TipoComprobante)cmbTipoComprobante.getSelectedItem().getValue()).getId()==Constantes.ID_TIPCOM_FACTURA)
				correlativo = "SEQ_AGE"+((Agencia)cmbAgencia.getSelectedItem().getValue()).getId()+((Empresa)cmbEmpresa.getSelectedItem().getValue()).getSigla()+"FAC"+txtSerie.getText().trim().toUpperCase();
			else if(((TipoComprobante)cmbTipoComprobante.getSelectedItem().getValue()).getId()==Constantes.ID_TIPCOM_NOTA_CREDITO)
				correlativo = "SEQ_AGE"+((Agencia)cmbAgencia.getSelectedItem().getValue()).getId()+((Empresa)cmbEmpresa.getSelectedItem().getValue()).getSigla()+"NC";
			else if(((TipoComprobante)cmbTipoComprobante.getSelectedItem().getValue()).getId()==Constantes.ID_TIPCOM_NOTA_DEBITO)
				correlativo = "SEQ_AGE"+((Agencia)cmbAgencia.getSelectedItem().getValue()).getId()+((Empresa)cmbEmpresa.getSelectedItem().getValue()).getSigla()+"ND";
			else if(((TipoComprobante)cmbTipoComprobante.getSelectedItem().getValue()).getId()==Constantes.ID_TIPCOM_GUIA_TRANSPORTISTA)
				correlativo = "SEQ_AGE"+((Agencia)cmbAgencia.getSelectedItem().getValue()).getId()+((Empresa)cmbEmpresa.getSelectedItem().getValue()).getSigla()+"GRT"+txtSerie.getText().trim().toUpperCase();
			else if(((TipoComprobante)cmbTipoComprobante.getSelectedItem().getValue()).getId()==Constantes.ID_TIPCOM_TICKET_EQUIPAJE)
				correlativo = "SEQ_AGE"+((Agencia)cmbAgencia.getSelectedItem().getValue()).getId()+((Empresa)cmbEmpresa.getSelectedItem().getValue()).getSigla()+"TKQ"+txtSerie.getText().trim().toUpperCase();

			if (action==ACTION_NEW)
				controlEspecieValorada= new ControlEspecieValorada();
			
			Empresa empresa = new Empresa();
			empresa.setId(((Empresa)cmbEmpresa.getSelectedItem().getValue()).getId());
			empresa.setRazonSocial(((Empresa)cmbEmpresa.getSelectedItem().getValue()).getRazonSocial());			

			TipoComprobante tipoComprobante = new TipoComprobante();
			tipoComprobante.setId(((TipoComprobante) cmbTipoComprobante.getSelectedItem().getValue()).getId());
			tipoComprobante.setDenominacion(((TipoComprobante) cmbTipoComprobante.getSelectedItem().getValue()).getDenominacion());
			UsuarioHardware usuarioHardware = new UsuarioHardware();
			usuarioHardware.setId(((UsuarioHardware)cmbUsuarioHardware.getSelectedItem().getValue()).getId());
			usuarioHardware.setDescripcion(((UsuarioHardware)cmbUsuarioHardware.getSelectedItem().getValue()).getDescripcion());
			usuarioHardware.setAgencia(((Agencia)cmbAgencia.getSelectedItem().getValue()));

			ControlEspecieValoradaID controlEspecieValoradaID = new ControlEspecieValoradaID();
			controlEspecieValoradaID.setIdTipoComprobante(tipoComprobante.getId());
			controlEspecieValoradaID.setIdUsuarioHardware(usuarioHardware.getId());
			controlEspecieValoradaID.setStrSerie(txtSerie.getText().toUpperCase().trim().toString());
			controlEspecieValoradaID.setIdEmpresa(empresa.getId());

			controlEspecieValorada.setAgencia(usuarioHardware.getAgencia());
			controlEspecieValorada.setEmpresa(empresa);
			controlEspecieValorada.setControlEspecieValoradaID(controlEspecieValoradaID);
			controlEspecieValorada.setTipoComprobante(tipoComprobante);
			controlEspecieValorada.setUsuarioHardware(usuarioHardware);
			controlEspecieValorada.setSerie(txtSerie.getText().toUpperCase().trim().toString());
			controlEspecieValorada.setSecuenciador(correlativo);
			controlEspecieValorada.setCorrelativoInicio(new Long(1));
			controlEspecieValorada.setCorrelativoFin(new Long(99999999));
			controlEspecieValorada.setCorrelativoActual(lgbxCorrelativoInicio.getValue());
			controlEspecieValorada.setFormato(0);
			controlEspecieValorada.setEstadoRegistro(Constantes.VALUE_ACTIVO);

			switch (action) {
				case ACTION_NEW:
					UtilData.auditarRegistro(controlEspecieValorada, getUsuario(), Executions.getCurrent());
					ServiceLocator.getControlEspecieValoradaManager().guardar(controlEspecieValorada);
					break;
				case ACTION_MODIFY:
					UtilData.auditarRegistro(controlEspecieValorada, true, getUsuario(), Executions.getCurrent());
					ServiceLocator.getControlEspecieValoradaManager().actualizar(controlEspecieValorada);
					break;
			}

			idAgencia=((Agencia)cmbAgencia.getSelectedItem().getValue()).getId();
			idTipoComprobante=controlEspecieValoradaID.getIdTipoComprobante();
			idUsuarioHardware=controlEspecieValoradaID.getIdUsuarioHardware();
			idEmpresa = controlEspecieValoradaID.getIdEmpresa();
			Util.limpiarListbox(listboxLista);
			listarRegistros(ServiceLocator.getControlEspecieValoradaManager().buscarEspecieValoradas(idAgencia, idTipoComprobante,idUsuarioHardware, idEmpresa));


		}catch(EmpresaException eex) {
			DlgMessage.information(Messages.getString("WndControlEspecieValorada.Information.empresa")+msn);
			throw new CancelaGrabacionException();
		}catch (RangoEspecieValoradaRegistradaException revyrex){
			DlgMessage.information(Messages.getString("WndControlEspecieValorada.Information.evUYaRegistradas")+msn);
			throw new CancelaGrabacionException();
		}catch (AgenciaNullException aex){
			DlgMessage.information(Messages.getString("WndEspecieValorada.information.Agencia"),cmbAgencia);
			throw new CancelaGrabacionException();
		}catch (TipoComprobanteNullException tcnex){
			DlgMessage.information(Messages.getString("WndVentaReserva.information.noTipoComprobante"),cmbTipoComprobante);
			throw new CancelaGrabacionException();
		}catch (UsuarioHardwareNullException uhnex){
			DlgMessage.information(Messages.getString("WndControlEspecieValorada.Information.UsuarioHarware"),cmbUsuarioHardware);
			throw new CancelaGrabacionException();
		}catch (NumeroSerieNullException nsnex){
			DlgMessage.information(Messages.getString("WndEspecieValorada.information.NumeroSerie"),txtSerie);
			throw new CancelaGrabacionException();
		}catch(CorrelativoException c){
			if(c.getTipo().intValue()==CorrelativoException.SERIE_DUPLICADA){
				DlgMessage.information(Messages.getString("WndControlEspecieValorada.information.NumeroSerieDuplicada"), txtSerie);
				throw new CancelaGrabacionException();
			}else if(c.getTipo().intValue()==CorrelativoException.DUPLICADO){
				DlgMessage.information(Messages.getString("WndControlEspecieValorada.Information.DuplicidadTipoComprobanteUsuarioHarware"),cmbTipoComprobante);
				throw new CancelaGrabacionException();
			}else if(c.getTipo().intValue()==CorrelativoException.ACTUAL_NULL){
				DlgMessage.information(Messages.getString("WndControlEspecieValorada.Information.CorrelativoActualNull"),cmbTipoComprobante);
				throw new CancelaGrabacionException();
			}
		}catch (NumeroSerieNoRegistradaException evnaex){
			DlgMessage.information(Messages.getString("WndControlEspecieValorada.Information.SerieNoValida"));
			throw new CancelaGrabacionException();
		}catch (Exception ex) {
			DlgMessage.error(this.getClass().getName()+" "+ex.getMessage());
			ex.printStackTrace();throw new CancelaGrabacionException();
		}

	}

	@Override
	public void onDelete(int tab) throws Exception {
		switch (tab) {
			case TAB_LIST:
				Listitem itemListBox = listboxLista.getItemAtIndex(listboxLista.getSelectedIndex());
				ServiceLocator.getControlEspecieValoradaManager().inactivar(((ControlEspecieValorada)itemListBox.getValue()).getControlEspecieValoradaID());
				break;
			case TAB_MAINTENANCE:
				ServiceLocator.getControlEspecieValoradaManager().inactivar(controlEspecieValorada.getControlEspecieValoradaID());
				break;
		}
	}

	@Override
	public void onPrint(int tab) throws Exception {
	}

	@Override
	public void onExport(int tab) throws Exception {
	}

	@Override
	public void onHelp() throws Exception {
	}

	@Override
	public void onChangeTab(int tab) throws Exception {
		switch (tab) {
		case TAB_LIST:
			break;
		case TAB_MAINTENANCE:
			if (listboxLista.getSelectedIndex() > -1) {
				mantenimiento();
			}
			break;
		}
	}

	@Override
	public void onClose() {
		closeTabWindow();
	}


	private void listarRegistros(List<ControlEspecieValorada> lstRegistros) {
		if(lstRegistros.size()>0){
			Listitem item = null;
			Listcell cell = null;
			int x =0;
			for(ControlEspecieValorada controlEspecieValorada : lstRegistros){
				item = new Listitem();
				x += +1;
				cell = new Listcell((Integer.toString(x)));
				item.appendChild(cell); //Correlativo
				cell = new Listcell(controlEspecieValorada.getEmpresa().getRazonSocial());
				item.appendChild(cell);
				cell = new Listcell(controlEspecieValorada.getTipoComprobante().getDenominacion());
				item.appendChild(cell);
				cell = new Listcell(controlEspecieValorada.getUsuarioHardware().getDescripcion());
				item.appendChild(cell);
				cell = new Listcell(controlEspecieValorada.getSerie());
				cell.setStyle("font-size:12px !important");
				item.appendChild(cell);
				cell = new Listcell(controlEspecieValorada.getUsuarioHardware().getAgencia().getNombreCorto());
				item.appendChild(cell);
				cell = new Listcell(controlEspecieValorada.getSecuenciador());
				item.appendChild(cell);

				item.setValue(controlEspecieValorada);
				listboxLista.appendChild(item);
			}
		}
	}

	/**
	 * @throws Exception
	 *
	 */
	private void mantenimiento() throws Exception{
		if(listboxLista.getSelectedIndex()>=0){
			Listitem lItemControlEspecieValorada = listboxLista.getItemAtIndex(listboxLista.getSelectedIndex());
			controlEspecieValorada = new ControlEspecieValorada();
			controlEspecieValorada = lItemControlEspecieValorada.getValue();
			textboxId.setText(controlEspecieValorada.getControlEspecieValoradaID().getIdTipoComprobante().toString());
			Util.seleccionarValorItemCombo(Agencia.class, cmbAgencia, (controlEspecieValorada.getUsuarioHardware().getAgencia().getId()));
			/*carga os usuarios hardware segun la agencia seleccionada*/
			UtilData.cargarUsuarioHasrdware(cmbUsuarioHardware, false, ((Agencia)cmbAgencia.getSelectedItem().getValue()));

			UtilData.cargarGenericData(cmbTipoComprobante, true);
			onLoadTipoComprobante(cmbTipoComprobante);
			Util.seleccionarValorItemCombo(TipoComprobante.class, cmbTipoComprobante, (controlEspecieValorada.getTipoComprobante().getId()));
			Util.seleccionarValorItemCombo(UsuarioHardware.class, cmbUsuarioHardware, (controlEspecieValorada.getUsuarioHardware().getId()));
			txtSerie.setText(controlEspecieValorada.getSerie());
			lgbxCorrelativoInicio.setValue(controlEspecieValorada.getCorrelativoActual());

			cmbTipoComprobante.setDisabled(true);
			cmbUsuarioHardware.setDisabled(true);
			cmbAgencia.setDisabled(true);
			txtSerie.setDisabled(true);
		}
	}

	@SuppressWarnings("deprecation")
	private Window createWindowSearch() throws Exception{

		final Window window = new Window(Messages.getString("System.title"), "normal", true);
		window.setWidth("450px");

		Grid grid = new Grid();
		Rows rows = new Rows();
		Row row = new Row();
		Columns columns = new Columns();
		Column column = new Column();
		column.setWidth("150px");
		columns.appendChild(column);
		grid.appendChild(columns);

		Label label = new Label("Empresa");
		label.setSclass("label-size11");
		final Combobox cmbEmpresaSearch = new Combobox();
		cmbEmpresaSearch.setWidth("200px");
		cmbEmpresaSearch.setReadonly(true);
		row.appendChild(label);
		row.appendChild(cmbEmpresaSearch);
		rows.appendChild(row);
		
		label = new Label("Agencia");
		label.setSclass("label-size11");
		final Combobox cmbAgencia= new Combobox();
		cmbAgencia.setWidth("200px");
		cmbAgencia.setReadonly(true);
		row = new Row(); 
		row.appendChild(label);
		row.appendChild(cmbAgencia);
		rows.appendChild(row);

		label = new Label("Usuario Hardware");
		label.setSclass("label-size11");
		final Combobox cmbUsuarioHardarware= new Combobox();
		cmbUsuarioHardarware.setWidth("200px");
		cmbUsuarioHardarware.setReadonly(true);
		row = new Row();
		row.appendChild(label);
		row.appendChild(cmbUsuarioHardarware);
		rows.appendChild(row);

		label = new Label();
		label.setValue("Tipo Comprobante");
		label.setSclass("label-size11");
		final Combobox cmbTipoComprobante= new Combobox();
		cmbTipoComprobante.setWidth("200px");
		cmbTipoComprobante.setReadonly(true);

		row = new Row();
		row.appendChild(label);
		row.appendChild(cmbTipoComprobante);
		rows.appendChild(row);

		Div div = new Div();
		Button button = new Button();
		button.setLabel("Filtrar");
		button.setImage("/resources/mp_filtrar.png");
		div.setAlign("center");
		div.appendChild(button);

		grid.appendChild(rows);
		window.appendChild(grid);
		window.appendChild(div);

		/*Carga solamente la agencias tepsa*/
		UtilData.cargarAgenciaXtipoAgencia(cmbAgencia, Constantes.ID_TIPAGE_TEPSA,true);
		UtilData.cargarDataCombo(cmbEmpresaSearch, Empresa.class, false);

		if(cmbAgencia.getSelectedItem()==null)
			cmbAgencia.setSelectedIndex(0);

		//Carga Usuario Hardware
		if(cmbAgencia.getSelectedItem().getValue() instanceof Agencia){
			UtilData.cargarUsuarioHasrdware(cmbUsuarioHardarware, false, ((Agencia)cmbAgencia.getSelectedItem().getValue()));
			Util.seleccionarValorItemCombo(UsuarioHardware.class, cmbUsuarioHardarware, getUsuarioHardware().getId());
			if(cmbUsuarioHardarware.getSelectedIndex()<0)
				cmbUsuarioHardarware.setSelectedIndex(0);
		}else
			UtilData.cargarGenericData(cmbUsuarioHardarware, false);

		UtilData.cargarGenericData(cmbUsuarioHardarware, true);
		UtilData.cargarGenericData(cmbTipoComprobante, true	);
		onLoadTipoComprobante(cmbTipoComprobante);
//		TreeMap<String, Object> criteriosBusqueda = new TreeMap<String, Object>();
//		criteriosBusqueda.put("estadoRegistro", Constantes.VALUE_ACTIVO);
//		UtilData.cargarTipoComprobante(cmbTipoComprobante, criteriosBusqueda, isChildable());

		cmbAgencia.addEventListener(Events.ON_CHANGE, new EventListener<Event>() {
			@Override
			public void onEvent(Event event) throws Exception {
//				Util.limpiarCombobox(cmbTipoComprobante);
				UtilData.cargarUsuarioHasrdware(cmbUsuarioHardarware, false, ((Agencia)cmbAgencia.getSelectedItem().getValue()));
//				onLoadTipoComprobante(cmbTipoComprobante);
			}
		});

		button.addEventListener(Events.ON_CLICK, new EventListener<Event>() {
			@Override
			public void onEvent(Event event) throws Exception {
				idTipoComprobante=null;idAgencia=null; idUsuarioHardware=null; idEmpresa=null;

				if(cmbEmpresaSearch.getSelectedItem().getValue() instanceof Empresa)
					idEmpresa = ((Empresa)cmbEmpresaSearch.getSelectedItem().getValue()).getId();
				if(cmbAgencia.getSelectedItem().getValue() instanceof Agencia)
					idAgencia = ((Agencia)cmbAgencia.getSelectedItem().getValue()).getId();
				if(cmbUsuarioHardarware.getSelectedItem().getValue() instanceof UsuarioHardware)
					idUsuarioHardware=((UsuarioHardware)cmbUsuarioHardarware.getSelectedItem().getValue()).getId();
				if(cmbTipoComprobante.getSelectedItem().getValue() instanceof TipoComprobante)
					idTipoComprobante=((TipoComprobante)cmbTipoComprobante.getSelectedItem().getValue()).getId();

				Util.limpiarListbox(listboxLista);
				listarRegistros(ServiceLocator.getControlEspecieValoradaManager().buscarEspecieValoradas(idAgencia, idTipoComprobante, idUsuarioHardware, idEmpresa));
				window.onClose();
			}

		});

		/**Solo se habilita este control para el rol Superusuario*/
		cmbAgencia.setDisabled(Constantes.ID_ROL_SUPER_USUARIO!=getRol().getId().intValue());
		/********************************/

		return window;
	}

	public void onLoadTipoComprobante(Combobox cmbTipCom) {
		try {
			List<TipoComprobante>result = ServiceLocator.getTipoComprobanteManager().buscarPorEstadoRegistro(Constantes.VALUE_ACTIVO, "denominacion");
			Comboitem comboitem= null;
			for(TipoComprobante tipoComprobante:result){
				if(tipoComprobante.getId().intValue()!=Constantes.ID_TIPCOM_BOLETO_VIAJE &&
						tipoComprobante.getId().intValue()!=Constantes.ID_TIPCOM_MANIFIESTO_PAX &&
						tipoComprobante.getId().intValue()!=Constantes.ID_TIPCOM_RECIBO_CAJA &&
						tipoComprobante.getId().intValue()!=Constantes.ID_TIPCOM_VOUCHER_AGENCIA_VIAJES &&
						tipoComprobante.getId().intValue()!=Constantes.ID_TIPCOM_VOUCHER_CORPORATIVO){
					comboitem= new Comboitem(tipoComprobante.getDenominacion());
					comboitem.setValue(tipoComprobante);
					cmbTipCom.appendChild(comboitem);
				}
			}
		}catch (Exception ex) {
			DlgMessage.error(this.getClass().getName()+" "+ex.getMessage());
		}
	}
}