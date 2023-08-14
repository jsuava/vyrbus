/**
 * Proyecto		: SISVYR
 * Sistema		: Sistema de Ventas y Reservas
 * Descripción	:
 * Autor		: jM
 * Fecha		: 06/06/2012
 */
package pe.itsb.vyrbus.view.ctrl;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zul.Button;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

import pe.itsb.vyrbus.model.bean.EstadoCivil;
import pe.itsb.vyrbus.model.bean.Nacionalidad;
import pe.itsb.vyrbus.model.bean.Personal;
import pe.itsb.vyrbus.model.bean.Sexo;
import pe.itsb.vyrbus.model.bean.TipoDocumento;
import pe.itsb.vyrbus.model.bean.TipoPersonal;
import pe.itsb.vyrbus.model.bean.TipoVia;
import pe.itsb.vyrbus.model.bean.TipoZona;
import pe.itsb.vyrbus.model.bean.Ubigeo;
import pe.itsb.vyrbus.service.exceptions.CancelaGrabacionException;
import pe.itsb.vyrbus.service.exceptions.CodigoDuplicadoException;
import pe.itsb.vyrbus.service.exceptions.CodigoNullException;
import pe.itsb.vyrbus.service.exceptions.EstadoCivilNullException;
import pe.itsb.vyrbus.service.exceptions.LicenciaDuplicadaException;
import pe.itsb.vyrbus.service.exceptions.NacionalidadException;
import pe.itsb.vyrbus.service.exceptions.NombreViaNullException;
import pe.itsb.vyrbus.service.exceptions.NombreZonaNullException;
import pe.itsb.vyrbus.service.exceptions.NombresNullException;
import pe.itsb.vyrbus.service.exceptions.NumeroDocumentoDuplicadoException;
import pe.itsb.vyrbus.service.exceptions.NumeroDocumentoNullException;
import pe.itsb.vyrbus.service.exceptions.SexoNullException;
import pe.itsb.vyrbus.service.exceptions.TipoDocumentoNullException;
import pe.itsb.vyrbus.service.exceptions.TipoPersonalNullException;
import pe.itsb.vyrbus.service.exceptions.TipoViaNullException;
import pe.itsb.vyrbus.service.exceptions.TipoZonaNullException;
import pe.itsb.vyrbus.service.exceptions.UbigeoNullException;
import pe.itsb.vyrbus.service.locator.ServiceLocator;
import pe.itsb.vyrbus.service.util.Constantes;
import pe.itsb.vyrbus.service.util.Messages;
import pe.itsb.vyrbus.service.util.Util;
import pe.itsb.vyrbus.service.util.UtilData;
import pe.itsb.vyrbus.view.ui.DlgMessage;
import pe.itsb.vyrbus.view.ui.WndFiltrarParametros;
import pe.itsb.vyrbus.view.ui.WndOpcionesMantenimiento;
import pe.itsb.vyrbus.view.ui.WndSeleccionarUbigeo;

/**
 *
 * @author jM
 * @since JDK1.6
 */
public class WndPersonal extends WndOpcionesMantenimiento {

	private static final long serialVersionUID = -291941257212836378L;

	private Window thisWindow = this;
	private Textbox txtApellidoPaterno;
	private Textbox txtApellidoMaterno;
	private Textbox txtNombre;
	private Combobox cboSexo;
	private Combobox cboNacionalidad;
	private Combobox cboEstadoCivil;
	private Combobox cboTipoDocumento;
	private Textbox txtNumeroDocumento;
	private Datebox dbFechaNacimiento;
	private Textbox txtLugarNacimiento;
	private Textbox txtDireccion;
	private Textbox txtIdUbigeo;
	private Textbox txtUbicacionGeografica;
	private Button btnUbicacionGeografica;
	private Textbox txtTelefono;
	private Textbox txtCorreoElectronico;
	private Combobox cboTipoVia;
	private Textbox txtNombreVia;
	private Combobox cboTipoZona;
	private Textbox txtNombreZona;
	private Combobox cboTipoPersonal;
	private Textbox txtCodigo;
	private Textbox txtNumeroLicencia;
	private Textbox txtCategoria;

	private Personal oPersonal = null;
	private TreeMap<String, Object> criteriosBusqueda = new TreeMap<>();
	private List<String> criteriosOrdenar = null;

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.window.ui.IBase#onCreate()
	 */
	@Override
	public void onCreate() throws Exception {
		UtilData.cargarDataCombo(cboSexo, Sexo.class, false);
		UtilData.cargarDataCombo(cboNacionalidad, Nacionalidad.class, false);
		UtilData.cargarDataCombo(cboEstadoCivil, EstadoCivil.class, false);
		TreeMap<String, Object> criteriosBusqueda = new TreeMap<>();
		criteriosBusqueda.put("tipo", TipoDocumento.PERSONALES);
		criteriosBusqueda.put("estadoRegistro", Constantes.VALUE_ACTIVO);
		UtilData.cargarDataCombo(cboTipoDocumento, TipoDocumento.class, criteriosBusqueda, false);
		UtilData.cargarDataCombo(cboTipoVia, TipoVia.class, false);
		UtilData.cargarDataCombo(cboTipoZona, TipoZona.class, false);
		UtilData.cargarDataCombo(cboTipoPersonal, TipoPersonal.class, false);

		btnUbicacionGeografica.addEventListener(Events.ON_CLICK, new EventListener<Event>() {
			@Override
			public void onEvent(Event event) throws Exception {
				final WndSeleccionarUbigeo oWndSeleccionarUbigeo = new WndSeleccionarUbigeo();
				oWndSeleccionarUbigeo.addEventListener(pe.itsb.vyrbus.view.ui.Events.ON_SELECT, new EventListener<Event>() {
					@Override
					public void onEvent(Event event) throws Exception {
						txtUbicacionGeografica.setText(oWndSeleccionarUbigeo.getUbicacionGeografica());
						txtIdUbigeo.setText(oWndSeleccionarUbigeo.getIdUbigeo());
					}
				});

				thisWindow.appendChild(oWndSeleccionarUbigeo);
				oWndSeleccionarUbigeo.setMode("modal");
				oWndSeleccionarUbigeo.setVisible(true);
			}
		});

		cboTipoDocumento.addEventListener(Events.ON_CHANGE, new EventListener<Event>() {
			@Override
			public void onEvent(Event event) throws Exception {
				txtNumeroDocumento.setText("");
				txtNumeroDocumento.setReadonly(false);
				if(cboTipoDocumento.getSelectedItem().getValue() instanceof TipoDocumento){
					if(((TipoDocumento)cboTipoDocumento.getSelectedItem().getValue()).getId().intValue()==Constantes.ID_TIPDOC_SN){
						txtNumeroDocumento.setReadonly(true);
					}
				}
			}
		});


		criteriosOrdenar = new ArrayList<>();
		criteriosOrdenar.add("apellidoPaterno");
		criteriosOrdenar.add("apellidoMaterno");
		criteriosOrdenar.add("nombre");
	}

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.window.ui.IBase#initComponents()
	 */
	@Override
	public void initComponents() {
		txtApellidoPaterno = (Textbox) getFellow("txtApellidoPaterno");
		txtApellidoMaterno = (Textbox) getFellow("txtApellidoMaterno");
		txtNombre = (Textbox) getFellow("txtNombre");
		cboSexo = (Combobox) getFellow("cboSexo");
		cboNacionalidad = (Combobox) getFellow("cboNacionalidad");
		cboEstadoCivil = (Combobox) getFellow("cboEstadoCivil");
		cboTipoDocumento = (Combobox) getFellow("cboTipoDocumento");
		txtNumeroDocumento = (Textbox) getFellow("txtNumeroDocumento");
		dbFechaNacimiento = (Datebox) getFellow("dbFechaNacimiento");
		txtLugarNacimiento = (Textbox) getFellow("txtLugarNacimiento");
		txtDireccion = (Textbox) getFellow("txtDireccion");
		txtIdUbigeo = (Textbox) getFellow("txtIdUbigeo");
		txtUbicacionGeografica = (Textbox) getFellow("txtUbicacionGeografica");
		btnUbicacionGeografica = (Button) getFellow("btnUbicacionGeografica");
		txtTelefono = (Textbox) getFellow("txtTelefono");
		txtCorreoElectronico = (Textbox) getFellow("txtCorreoElectronico");
		cboTipoVia = (Combobox) getFellow("cboTipoVia");
		txtNombreVia = (Textbox) getFellow("txtNombreVia");
		cboTipoZona = (Combobox) getFellow("cboTipoZona");
		txtNombreZona = (Textbox) getFellow("txtNombreZona");
		cboTipoPersonal = (Combobox) getFellow("cboTipoPersonal");
		txtCodigo = (Textbox) getFellow("txtCodigo");
		txtCategoria = (Textbox) getFellow("txtCategoria");
		txtNumeroLicencia = (Textbox) getFellow("txtNumeroLicencia");
	}

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.window.ui.IOpcionesMantenimiento#onNew()
	 */
	@Override
	public void onNew() {
		cboTipoPersonal.setSelectedIndex(0);
		cboTipoDocumento.setSelectedIndex(0);
		cboSexo.setSelectedIndex(0);
		cboNacionalidad.setSelectedIndex(0);
		cboNacionalidad.setSelectedIndex(0);
		cboEstadoCivil.setSelectedIndex(0);
		cboTipoVia.setSelectedIndex(0);
		cboTipoZona.setSelectedIndex(0);
		dbFechaNacimiento.setText("01/01/1900");
	}

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.window.ui.IOpcionesMantenimiento#onSearch()
	 */
	@Override
	public void onSearch() throws Exception {
		final WndFiltrarParametros oWndFiltrar = new WndFiltrarParametros();

		oWndFiltrar.addParameter("1. Tipo de Documento", TipoDocumento.class);
		oWndFiltrar.addParameter("2. Número de Documento", String.class);
		oWndFiltrar.addParameter("3. Apellido paterno", String.class);
		oWndFiltrar.addParameter("4. Apellido materno", String.class);
		oWndFiltrar.addParameter("5. Nombre", String.class);

		this.appendChild(oWndFiltrar);
		oWndFiltrar.setMode("modal");
		oWndFiltrar.addEventListener(pe.itsb.vyrbus.view.ui.Events.ON_FILTER, new EventListener<Event>() {
			@Override
			public void onEvent(Event event) throws Exception {
				Integer tipoDocumento = (Integer) oWndFiltrar.getParameterValue("1. Tipo de Documento");
				String nroDocumento = (String) oWndFiltrar.getParameterValue("2. Número de Documento");
				String apellidoPaterno = (String) oWndFiltrar.getParameterValue("3. Apellido paterno");
				String apellidoMaterno = (String) oWndFiltrar.getParameterValue("4. Apellido materno");
				String nombre = (String) oWndFiltrar.getParameterValue("5. Nombre");
				String estadoRegistro = Constantes.VALUE_ACTIVO;

				if (tipoDocumento == null || tipoDocumento == 0){
					criteriosBusqueda.remove("tipoDocumento");
				}else{
					TipoDocumento otipoDocumento = new TipoDocumento();
					otipoDocumento.setId(tipoDocumento);
					criteriosBusqueda.put("tipoDocumento", otipoDocumento);
				}

				if (nroDocumento.trim().equals("")){
					criteriosBusqueda.remove("nroDocumento");
				}else{criteriosBusqueda.put("nroDocumento", nroDocumento);}

				if (apellidoPaterno.trim().equals("")) {
					criteriosBusqueda.remove("apellidoPaterno");
				}else{criteriosBusqueda.put("apellidoPaterno", "%" + apellidoPaterno + "%");}

				if (apellidoMaterno.trim().equals("")) {
					criteriosBusqueda.remove("apellidoMaterno");
				}else {criteriosBusqueda.put("apellidoMaterno", "%" + apellidoMaterno + "%");}

				if (nombre.trim().equals("")) {
					criteriosBusqueda.remove("nombre");
				}else {criteriosBusqueda.put("nombre", "%" + nombre + "%");}

				criteriosBusqueda.put("estadoRegistro", estadoRegistro);

				listarRegistros(ServiceLocator.getPersonalManager().buscarPorX(criteriosBusqueda, criteriosOrdenar));
			}
		});

	}


	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.window.ui.IOpcionesMantenimiento#onRefresh(int)
	 */
	@Override
	public void onRefresh(int tab) throws Exception {
		if (!criteriosBusqueda.isEmpty()) {
			this.listarRegistros(ServiceLocator.getPersonalManager().buscarPorX(criteriosBusqueda, criteriosOrdenar));
		}

	}

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.window.ui.IOpcionesMantenimiento#onModify(int)
	 */
	@Override
	public void onModify(int tab) throws Exception {
		Long id = new Long(0);
		id = new Long((String) listboxLista.getSelectedItem().getValue());
		this.mantenimientoRegistro(id);
	}

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.window.ui.IOpcionesMantenimiento#onCancel(int)
	 */
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

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.window.ui.IOpcionesMantenimiento#onSave(int)
	 */
	@Override
	public void onSave(int action) throws Exception {
		try{
			if (!(cboTipoPersonal.getSelectedItem().getValue() instanceof TipoPersonal))
				throw new TipoPersonalNullException();
			else if (txtCodigo.getText().trim().equals(""))
				throw new CodigoNullException();
			else if (!(cboTipoDocumento.getSelectedItem().getValue() instanceof TipoDocumento))
				throw new TipoDocumentoNullException();
			else if (txtNumeroDocumento.getText().trim().equals(""))
				throw new NumeroDocumentoNullException();
			else if (txtNombre.getText().trim().equals(""))
				throw new NombresNullException();
			else if (!(cboSexo.getSelectedItem().getValue() instanceof Sexo))
				throw new SexoNullException();
			else if (!(cboNacionalidad.getSelectedItem().getValue() instanceof Nacionalidad))
				throw new NacionalidadException();
			else if (!(cboEstadoCivil.getSelectedItem().getValue() instanceof EstadoCivil))
				throw new EstadoCivilNullException();
			else if (txtUbicacionGeografica.getText().trim().equals(""))
				throw new UbigeoNullException();
			else if (!(cboTipoVia.getSelectedItem().getValue() instanceof TipoVia))
				throw new TipoViaNullException();
			else if (txtNombreVia.getText().trim().equals(""))
				throw new NombreViaNullException();
			else if (!(cboTipoZona.getSelectedItem().getValue() instanceof TipoZona))
				throw new TipoZonaNullException();
			else if (txtNombreZona.getText().trim().equals(""))
				throw new NombreZonaNullException();

			TipoDocumento oTipoDocumento = (TipoDocumento)cboTipoDocumento.getSelectedItem().getValue();
			TipoPersonal oTipoPersonal = (TipoPersonal)cboTipoPersonal.getSelectedItem().getValue();

			/*Valida si el tipo de personal es piloto/copiloto o terramoza, y el tipo de documento es SN, obliga que seleccione uno válido - JABANTO 22/10/2014*/
			if(oTipoDocumento.getId().intValue()==Constantes.ID_TIPDOC_SN &&
					(oTipoPersonal.getId().intValue()==Constantes.ID_TIPPER_PILOTO_COPILOTO || oTipoPersonal.getId().intValue()==Constantes.ID_TIPPER_TRIPULANTE)){
				throw new TipoDocumentoNullException();
			}

			/*Valida si el tipo de documento es DNI, por lo menos tenga 8 digitos - JABANTO 22/10/2014*/
			if(oTipoDocumento.getId().intValue()==Constantes.ID_TIPDOC_DNI && txtNumeroDocumento.getText().trim().length()!=8){
				DlgMessage.information(Messages.getString("WndPersonal.information.lenDniInvalid"),txtNumeroDocumento);
				throw new CancelaGrabacionException();
			}

			/*Valida si el tipo de personal es Pilo/copiloto, obliga a ingresar el numero de Licencia de conducir. - JABANTO 22/10/2014*/
			if(oTipoPersonal.getId().intValue()==Constantes.ID_TIPPER_PILOTO_COPILOTO && txtNumeroLicencia.getText().trim().isEmpty()){
				DlgMessage.information(Messages.getString("WndPersonal.information.noLicencia"),txtNumeroLicencia);
				throw new CancelaGrabacionException();
			}

			/*Valida la longitud de la licencia - JABANTO 22/10/2014*/
			if(!(txtNumeroLicencia.getText().trim().isEmpty()) && txtNumeroLicencia.getText().trim().length()<7){
				DlgMessage.information(Messages.getString("WndPersonal.information.licenicaInvalid"),txtNumeroLicencia);
				throw new CancelaGrabacionException();
			}

			if (action==ACTION_NEW)
				oPersonal = new Personal();

			Long id = (textboxId.getText().equals("") ? 0 : new Long(textboxId.getText()));
			Ubigeo oUbigeo = new Ubigeo();
			Sexo oSexo = new Sexo();
			Nacionalidad oNacionalidad = new Nacionalidad();
			EstadoCivil oEstadoCivil = new EstadoCivil();
			TipoVia oTipoVia = new TipoVia();
			TipoZona oTipoZona = new TipoZona();

			oUbigeo.setId(txtIdUbigeo.getText());
			oTipoPersonal.setId(((TipoPersonal) cboTipoPersonal.getSelectedItem().getValue()).getId());
			oTipoPersonal.setDenominacion(((TipoPersonal) cboTipoPersonal.getSelectedItem().getValue()).getDenominacion());
			oSexo.setId(((Sexo) cboSexo.getSelectedItem().getValue()).getId());
			oSexo.setDenominacion(((Sexo) cboSexo.getSelectedItem().getValue()).getDenominacion());
			oNacionalidad.setId(((Nacionalidad) cboNacionalidad.getSelectedItem().getValue()).getId());
			oEstadoCivil.setId(((EstadoCivil) cboEstadoCivil.getSelectedItem().getValue()).getId());
			oTipoDocumento.setId(((TipoDocumento) cboTipoDocumento.getSelectedItem().getValue()).getId());
			oTipoDocumento.setDenominacion( ((TipoDocumento) cboTipoDocumento.getSelectedItem().getValue()).getDenominacion());
			oTipoVia.setId(((TipoVia) cboTipoVia.getSelectedItem().getValue()).getId());
			oTipoZona.setId(((TipoZona) cboTipoZona.getSelectedItem().getValue()).getId());

			oPersonal.setId(id);
			oPersonal.setTipoPersonal(oTipoPersonal);
			oPersonal.setCodigo(txtCodigo.getText());
			oPersonal.setLicencia(txtNumeroLicencia.getText().trim().toUpperCase());
			oPersonal.setCategoria(txtCategoria.getText().trim().toUpperCase());
			oPersonal.setApellidoPaterno(txtApellidoPaterno.getText().trim().toUpperCase());
			oPersonal.setApellidoMaterno(txtApellidoMaterno.getText().trim()!=null? txtApellidoMaterno.getText().trim().toUpperCase(): "");
			oPersonal.setNombre(txtNombre.getText().trim().toUpperCase());
			oPersonal.setSexo(oSexo);
			oPersonal.setNacionalidad(oNacionalidad);
			oPersonal.setEstadoCivil(oEstadoCivil);
			oPersonal.setTipoDocumento(oTipoDocumento);
			oPersonal.setNroDocumento(txtNumeroDocumento.getText().trim());
			oPersonal.setFechaNacimiento(dbFechaNacimiento.getValue());
			oPersonal.setLugarNacimiento(txtLugarNacimiento.getText().trim().toUpperCase());
			oPersonal.setDireccion(txtDireccion.getText().trim().toUpperCase());
			oPersonal.setUbigeo(oUbigeo);
			oPersonal.setTelefono(txtTelefono.getText().trim());
			oPersonal.setEmail(txtCorreoElectronico.getText().trim().toLowerCase());
			oPersonal.setTipoVia(oTipoVia);
			oPersonal.setNombreVia(txtNombreVia.getText().trim().toUpperCase());
			oPersonal.setTipoZona(oTipoZona);
			oPersonal.setNombreZona(txtNombreZona.getText().trim().toUpperCase());
			oPersonal.setEstadoRegistro(Constantes.VALUE_ACTIVO);

			switch (action) {
				case ACTION_NEW:
					UtilData.auditarRegistro(oPersonal, getUsuario(), Executions.getCurrent());
					ServiceLocator.getPersonalManager().guardar(oPersonal);
					textboxId.setText(oPersonal.getId().toString());
					break;

				case ACTION_MODIFY:
					UtilData.auditarRegistro(oPersonal, true, getUsuario(), Executions.getCurrent());
					ServiceLocator.getPersonalManager().actualizar(oPersonal);
					break;
			}
			/*RECUPERA EL REGISTRO */
			criteriosBusqueda.remove("tipoDocumento"); criteriosBusqueda.remove("nroDocumento");
			criteriosBusqueda.remove("apellidoPaterno");criteriosBusqueda.remove("apellidoMaterno");
			criteriosBusqueda.remove("nombre");
			criteriosBusqueda.put("tipoDocumento", oTipoDocumento);
			criteriosBusqueda.put("nroDocumento", oPersonal.getNroDocumento());
			criteriosBusqueda.put("estadoRegistro", Constantes.VALUE_ACTIVO);

			listarRegistros(ServiceLocator.getPersonalManager().buscarPorX(criteriosBusqueda, criteriosOrdenar));

		}catch (TipoPersonalNullException tpnex){
			DlgMessage.information(Messages.getString("WndPersonal.information.TipoPerosnal"),cboTipoPersonal);
			throw new CancelaGrabacionException();
		}catch (CodigoNullException ccnex){
			DlgMessage.information(Messages.getString("WndPersonal.information.CodigoPerosnal"),txtCodigo);
			throw new CancelaGrabacionException();
		}catch (TipoDocumentoNullException tonex){
			DlgMessage.information(Messages.getString("WndPersonal.information.TipoDocumento"),cboTipoDocumento);
			throw new CancelaGrabacionException();
		}catch (NumeroDocumentoNullException ndnex){
			DlgMessage.information(Messages.getString("WndPersonal.information.NumeroDocuento"),txtNumeroDocumento);
			throw new CancelaGrabacionException();
		}catch (NombresNullException nnex){
			DlgMessage.information(Messages.getString("WndPersonal.information.Nombre"),txtNombre);
			throw new CancelaGrabacionException();
		}catch (SexoNullException snex){
			DlgMessage.information(Messages.getString("WndPersonal.information.Sexo"),cboSexo);
			throw new CancelaGrabacionException();
		}catch (NacionalidadException nnex){
			DlgMessage.information(Messages.getString("WndPersonal.information.Nacionalidad"),cboNacionalidad);
			throw new CancelaGrabacionException();
		}catch (EstadoCivilNullException ecnex){
			DlgMessage.information(Messages.getString("WndPersonal.information.EstadoCivil"),cboEstadoCivil);
			throw new CancelaGrabacionException();
		}catch (UbigeoNullException enex){
			DlgMessage.information(Messages.getString("WndPersonal.information.Ubigeo"),btnUbicacionGeografica);
			throw new CancelaGrabacionException();
		}catch (TipoViaNullException tvnex){
			DlgMessage.information(Messages.getString("WndPersonal.information.TipoVia"),cboTipoVia);
			throw new CancelaGrabacionException();
		}catch (NombreViaNullException nvnex){
			DlgMessage.information(Messages.getString("WndPersonal.information.NombreVia"),txtNombreVia);
			throw new CancelaGrabacionException();
		}catch (TipoZonaNullException tznex){
			DlgMessage.information(Messages.getString("WndPersonal.information.TipoZona"),cboTipoZona);
			throw new CancelaGrabacionException();
		}catch (NombreZonaNullException nznex){
			DlgMessage.information(Messages.getString("WndPersonal.information.NombreZona"),txtNombreZona);
			throw new CancelaGrabacionException();
		}catch (CodigoDuplicadoException cdex){
			DlgMessage.information(Messages.getString("WndPersonal.information.CodigoPersonalDuplicado"),txtCodigo);
			throw new CancelaGrabacionException();
		}catch (LicenciaDuplicadaException ldex){
			DlgMessage.information(Messages.getString("WndPersonal.information.LicenciaPersonalDuplicada"),txtNumeroLicencia);
			throw new CancelaGrabacionException();
		}catch (NumeroDocumentoDuplicadoException nddex){
			DlgMessage.information(Messages.getString("WndPersonal.information.NumeroDocumentoDuplicado"),txtNumeroDocumento);
			throw new CancelaGrabacionException();
		}catch (CancelaGrabacionException ed){
			throw new CancelaGrabacionException();
		}catch (Exception ex){
			DlgMessage.error(this.getClass().getName()+" "+ex.getMessage());
			ex.printStackTrace(); throw new CancelaGrabacionException();
		}
	}

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.window.ui.IOpcionesMantenimiento#onDelete(int)
	 */
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

		ServiceLocator.getPersonalManager().inactivar(id);
	}

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.window.ui.IOpcionesMantenimiento#onPrint(int)
	 */
	@Override
	public void onPrint(int tab) {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.window.ui.IOpcionesMantenimiento#onExport(int)
	 */
	@Override
	public void onExport(int tab) {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.window.ui.IOpcionesMantenimiento#onHelp()
	 */
	@Override
	public void onHelp() {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.window.ui.IOpcionesMantenimiento#onChangeTab(int)
	 */
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

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.window.IOpcionesMantenimiento#onClose()
	 */
	@Override
	public void onClose() {
		closeTabWindow();
	}

	private void listarRegistros(ArrayList<Personal> lstRegistros) throws Exception {
		ArrayList<Object> lstPersonales = new ArrayList<>();

		for (int r = 0; r < lstRegistros.size(); r ++) {
			Personal oPersonal = lstRegistros.get(r);
			ArrayList<Object> lstFila = new ArrayList<>();

			lstFila.add(oPersonal.getId());
			lstFila.add(r + 1);
			lstFila.add(oPersonal.getTipoPersonal().getDenominacion());
			lstFila.add(oPersonal.getCodigo());
			lstFila.add(oPersonal.getTipoDocumento().getDenominacion());
			lstFila.add(oPersonal.getNroDocumento());
			lstFila.add(oPersonal.getApellidoPaterno());
			lstFila.add(oPersonal.getApellidoMaterno());
			lstFila.add(oPersonal.getNombre());
			lstFila.add(oPersonal.getFechaNacimiento().toString().substring(0,10));
			lstFila.add(oPersonal.getSexo().getDenominacion());

			lstPersonales.add(lstFila);
		}

		Util.llenarListbox(listboxLista, lstPersonales, true);
	}

	private void mantenimientoRegistro(Long id) throws Exception {
		oPersonal = ServiceLocator.getPersonalManager().buscarPorId(id);

		textboxId.setText((new Long(oPersonal.getId())).toString());
		Util.seleccionarValorItemCombo(TipoPersonal.class, cboTipoPersonal, oPersonal.getTipoPersonal().getId());
		txtCodigo.setText(oPersonal.getCodigo());
		txtNumeroLicencia.setText(oPersonal.getLicencia());
		txtCategoria.setText(oPersonal.getCategoria());

		txtApellidoPaterno.setText(oPersonal.getApellidoPaterno());
		txtApellidoMaterno.setText(oPersonal.getApellidoMaterno());
		txtNombre.setText(oPersonal.getNombre());
		Util.seleccionarValorItemCombo(Sexo.class, cboSexo, oPersonal.getSexo().getId());
		Util.seleccionarValorItemCombo(Nacionalidad.class, cboNacionalidad, oPersonal.getNacionalidad().getId());
		Util.seleccionarValorItemCombo(EstadoCivil.class, cboEstadoCivil, oPersonal.getEstadoCivil().getId());
		Util.seleccionarValorItemCombo(TipoDocumento.class, cboTipoDocumento, oPersonal.getTipoDocumento().getId());
		txtNumeroDocumento.setText(oPersonal.getNroDocumento());
		dbFechaNacimiento.setValue(oPersonal.getFechaNacimiento());
		txtLugarNacimiento.setText(oPersonal.getLugarNacimiento());
		txtDireccion.setText(oPersonal.getDireccion());

		Ubigeo oUbigeo  = oPersonal.getUbigeo();
		String idUbigeo = new String();
		String ubicacionCompleta = new String();

		if (oPersonal.getUbigeo() != null) {
			idUbigeo = oUbigeo.getId();
			ubicacionCompleta = ServiceLocator.getUbigeoManager().ubicacionGeografica(oUbigeo);
		}
		txtIdUbigeo.setText(idUbigeo);
		txtUbicacionGeografica.setText(ubicacionCompleta);
		txtTelefono.setText(oPersonal.getTelefono());
		txtCorreoElectronico.setText(oPersonal.getEmail());

		Util.seleccionarValorItemCombo(TipoVia.class, cboTipoVia, oPersonal.getTipoVia().getId());
		txtNombreVia.setText(oPersonal.getNombreVia());
		Util.seleccionarValorItemCombo(TipoZona.class, cboTipoZona, oPersonal.getTipoZona().getId());
		txtNombreZona.setText(oPersonal.getNombreZona());
	}
}