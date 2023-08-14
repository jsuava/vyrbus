/**
 * Proyecto		: SISVYR
 * Sistema		: Sistema de Ventas y Reservas
 * Descripci�n	:
 * Autor		: Jos� Abanto
 * Fecha		: 20/08/2012
 */
package pe.itsb.vyrbus.view.ctrl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TreeMap;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.WrongValueException;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zul.Button;
import org.zkoss.zul.Caption;
import org.zkoss.zul.Checkbox;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.Div;
import org.zkoss.zul.Image;
import org.zkoss.zul.Label;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listhead;
import org.zkoss.zul.Listheader;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Tab;
import org.zkoss.zul.Tabbox;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

import pe.gob.mtc.wshr.ResultIdentidad;
import pe.itsb.vyrbus.model.bean.Agencia;
import pe.itsb.vyrbus.model.bean.EstadoCivil;
import pe.itsb.vyrbus.model.bean.HistoricoMembresia;
import pe.itsb.vyrbus.model.bean.Pasajero;
import pe.itsb.vyrbus.model.bean.PasajeroFrecuente;
import pe.itsb.vyrbus.model.bean.Reniec;
import pe.itsb.vyrbus.model.bean.Rol;
import pe.itsb.vyrbus.model.bean.Sexo;
import pe.itsb.vyrbus.model.bean.TipoDocumento;
import pe.itsb.vyrbus.model.bean.Ubigeo;
import pe.itsb.vyrbus.model.bean.VentaPasaje;
import pe.itsb.vyrbus.service.exceptions.ApellidoMaternoNullException;
import pe.itsb.vyrbus.service.exceptions.ApellidoPaternoNullException;
import pe.itsb.vyrbus.service.exceptions.CancelaGrabacionException;
import pe.itsb.vyrbus.service.exceptions.CorreoElectronicoNullException;
import pe.itsb.vyrbus.service.exceptions.DireccionNullException;
import pe.itsb.vyrbus.service.exceptions.DocumentoPaxDuplicadoException;
import pe.itsb.vyrbus.service.exceptions.FechaNacimientoNullxception;
import pe.itsb.vyrbus.service.exceptions.MailIncorectoException;
import pe.itsb.vyrbus.service.exceptions.NombresNullException;
import pe.itsb.vyrbus.service.exceptions.NumeroDocumentoNullException;
import pe.itsb.vyrbus.service.exceptions.PaxFreNumeroTarjetaNullException;
import pe.itsb.vyrbus.service.exceptions.PaxMotivoNoGrataNullException;
import pe.itsb.vyrbus.service.exceptions.SexoNullException;
import pe.itsb.vyrbus.service.exceptions.TarjetaDuplicadaException;
import pe.itsb.vyrbus.service.exceptions.TelefonoNullException;
import pe.itsb.vyrbus.service.exceptions.TipoDocumentoNullException;
import pe.itsb.vyrbus.service.exceptions.UbigeoNullException;
import pe.itsb.vyrbus.service.locator.ServiceLocator;
import pe.itsb.vyrbus.service.util.Constantes;
import pe.itsb.vyrbus.service.util.Messages;
import pe.itsb.vyrbus.service.util.MyTime;
import pe.itsb.vyrbus.service.util.Util;
import pe.itsb.vyrbus.service.util.UtilData;
import pe.itsb.vyrbus.view.ui.DlgMessage;
import pe.itsb.vyrbus.view.ui.WndFiltrarParametros;
import pe.itsb.vyrbus.view.ui.WndOpcionesMantenimiento;

/**
 *
 * @author JABANTO
 * @since JDK1.6
 */
public class WndPasajero extends WndOpcionesMantenimiento {
	private static final long serialVersionUID = -857691687288484666L;

	private Tabbox tbPasajero;
	private Textbox txtApellidoPaterno;
	private Textbox txtApellidoMaterno;
	private Textbox txtNombre;
	private Datebox dbfechaNacimiento;
	private Combobox cboDocumentoIdentificacion;
	private Textbox txtNumeroIdentificacion;
	private Combobox cboSexo;
	private Combobox cboEstadoCivil;
	private Textbox txtDireccion;
	private Textbox txtIdUbigeo;
	private Textbox txtUbicacionGeografica;
	private Button btnUbicacionGeografica;
	private Textbox txtCorreoElectronico;
	private Textbox txtTelefono;
	private Checkbox chkPersonaNoGrata;
	private Textbox txtMotivoNoGrata;
	private Image imgValidacionDNI;

	private Tab tabPaxFree;
	private Label lblKilometrosAcumulados;
	private Checkbox chkPasajeroFrecuente;
	private Textbox txtIdPasajeroFrecuente;
	private Textbox txtNumeroTarjeta;
	private Label lblFechaIngreso;
	private Label lblPuntosAcumulados;
	private Label lblPuntosUtilizados;
	private Label lblFechaActivacion;
	private Label lblFechaCaducidad;
	private Label lblFechaSuspension;
//	private Combobox cmbAgencia;
	private Label lbViajesAcumulados;
	private Label lbEstado;
//	private Label lbPersoAutoira;
	private Label lblPuntosDisponibles;
	private Label lbtotalViajes;
	private Label lbtotalViajesValidos;
//	private Textbox txtPersonaAutoriza;
	private Window wndHistory;
	private Window wndPasajero;

	private Listbox listPasajesAcumulados;

	private Pasajero oPasajero = null;
	private PasajeroFrecuente opasajeroFrecuente =null;

	private TreeMap<String, Object> condicionBusqueda = new TreeMap<>();
	private List<String> criteriosOrdenar = null;
//	private Integer paxFree;

	public static final String ESTADO_PAXFREE_NOREGISTRADO="NO REGISTRADO";

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.window.ui.IBase#onCreate()
	 */
	@Override
	public void onCreate() throws Exception {
		UtilData.cargarDataCombo(cboEstadoCivil, EstadoCivil.class, false);
		UtilData.cargarDataCombo(cboSexo, Sexo.class, false);
		TreeMap<String, Object> criteriosBusqueda = new TreeMap<>();
		criteriosBusqueda.put("tipo", TipoDocumento.PERSONALES);
		criteriosBusqueda.put("estadoRegistro", Constantes.VALUE_ACTIVO);
		UtilData.cargarDataCombo(cboDocumentoIdentificacion, TipoDocumento.class, criteriosBusqueda, false);
		UtilData.enlazarUbigeo(txtIdUbigeo, txtUbicacionGeografica, btnUbicacionGeografica,null);
//		UtilData.cargarDataCombo(cmbAgencia, Agencia.class, false);

		/*Carga todos las agencias, sin importar su estado - 04/04/2015*/
//		criteriosBusqueda=new TreeMap<String, Object>();
//		List<String>criteriosOrden=new ArrayList<String>();
//		criteriosOrden.add("denominacion");
//		List<Agencia>result=ServiceLocator.getAgenciaManager().buscarPorX(criteriosBusqueda, criteriosOrdenar);
//		UtilData.cargarGenericData(cmbAgencia, false);
//		for(Agencia agencia:result){
//			Comboitem comboitem=new Comboitem(agencia.getDenominacion());
//			comboitem.setValue(agencia);
//			cmbAgencia.appendChild(comboitem);
//		}


		chkPasajeroFrecuente.addEventListener(Events.ON_CHECK, new EventListener<Event>() {
			@Override
			public void onEvent(Event event) throws Exception {
				habilitarPasajeroFrecuente(chkPasajeroFrecuente.isChecked());
			}
		});

		chkPersonaNoGrata.addEventListener(Events.ON_CHECK, new EventListener<Event>() {
			@Override
			public void onEvent(Event event) throws Exception {
				habilitarPersonaNoGrata(chkPersonaNoGrata.isChecked());
				txtMotivoNoGrata.setFocus(true);
			}
		});

		cboDocumentoIdentificacion.addEventListener(Events.ON_CHANGE, new EventListener<Event>() {
			@Override
			public void onEvent(Event event) throws Exception {
				if(cboDocumentoIdentificacion.getSelectedItem().getValue() instanceof TipoDocumento){
					TipoDocumento tipoDocumento=cboDocumentoIdentificacion.getSelectedItem().getValue();
					if(tipoDocumento.getId().intValue()==Constantes.ID_TIPDOC_SN){
						txtNumeroIdentificacion.setDisabled(true);
						txtNumeroIdentificacion.setText("");
					}else
						txtNumeroIdentificacion.setDisabled(false);
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
		wndPasajero = (Window)getFellow("wndPasajero");
		tbPasajero = (Tabbox) getFellow("tbPasajero");

		txtApellidoPaterno=(Textbox) getFellow("txtApellidoPaterno");
		txtApellidoMaterno=(Textbox) getFellow("txtApellidoMaterno");
		txtNombre=(Textbox) getFellow("txtNombre");
		dbfechaNacimiento=(Datebox) getFellow("dbfechaNacimiento");
		cboDocumentoIdentificacion=(Combobox) getFellow("cboDocumentoIdentificacion");
		txtNumeroIdentificacion=(Textbox) getFellow("txtNumeroIdentificacion");
		cboSexo=(Combobox) getFellow("cboSexo");
		cboEstadoCivil=(Combobox) getFellow("cboEstadoCivil");
		txtDireccion=(Textbox) getFellow("txtDireccion");
		txtIdUbigeo=(Textbox) getFellow("txtIdUbigeo");
		txtUbicacionGeografica=(Textbox) getFellow("txtUbicacionGeografica");
		btnUbicacionGeografica=(Button) getFellow("btnUbicacionGeografica");
		txtTelefono=(Textbox)getFellow("txtTelefono");
		txtCorreoElectronico=(Textbox) getFellow("txtCorreoElectronico");
		imgValidacionDNI=(Image)this.getFellow("imgValidacionDNI");

		tabPaxFree=(Tab)this.getFellow("tabPaxFree");
		lblKilometrosAcumulados= (Label) getFellow("lblKilometrosAcumulados");
		chkPersonaNoGrata= (Checkbox) getFellow("chkPersonaNoGrata");
		txtIdPasajeroFrecuente = (Textbox) getFellow("txtIdPasajeroFrecuente");
		txtMotivoNoGrata = (Textbox) getFellow("txtMotivoNoGrata");
		chkPasajeroFrecuente = (Checkbox) getFellow("chkPasajeroFrecuente");
		txtNumeroTarjeta = (Textbox) getFellow("txtNumeroTarjeta");
		lblFechaIngreso = (Label) getFellow("lblFechaIngreso");
		lblPuntosAcumulados = (Label) getFellow("lblPuntosAcumulados");
		lblPuntosUtilizados = (Label) getFellow("lblPuntosUtilizados");
		lblFechaActivacion = (Label) getFellow("lblFechaActivacion");
		lblFechaCaducidad = (Label) getFellow("lblFechaCaducidad");
		lblFechaSuspension = (Label) getFellow("lblFechaSuspension");
//		cmbAgencia =(Combobox) this.getFellow("cmbAgencia");
		lbViajesAcumulados=(Label)this.getFellow("lbViajesAcumulados");
		lbEstado=(Label)this.getFellow("lbEstado");
		lblPuntosDisponibles=(Label)this.getFellow("lblPuntosDisponibles");
//		lbPersoAutoira=(Label)this.getFellow("lbPersoAutoira");
		lbtotalViajes=(Label)this.getFellow("lbtotalViajes");
		lbtotalViajesValidos=(Label)this.getFellow("lbtotalViajesValidos");
//		txtPersonaAutoriza=(Textbox)this.getFellow("txtPersonaAutoriza");

		listPasajesAcumulados=(Listbox)this.getFellow("listPasajesAcumulados");
	}

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.window.ui.IOpcionesMantenimiento#onNew()
	 */
	@Override
	public void onNew() {
		tbPasajero.setSelectedIndex(0);
		lblKilometrosAcumulados.setValue("0");
		habilitarPasajeroFrecuente(false);
		habilitarPersonaNoGrata(false);

		cboDocumentoIdentificacion.setSelectedIndex(0);
		cboEstadoCivil.setSelectedIndex(0);
		cboSexo.setSelectedIndex(0);
//		Util.seleccionarValorItemCombo(Agencia.class, cmbAgencia, getAgencia().getId());

		chkPasajeroFrecuente.setDisabled(true);
		dbfechaNacimiento.setText("01/01/1900");
		listPasajesAcumulados.getItems().clear();
		lbViajesAcumulados.setValue("");
//		lbPersoAutoira.setVisible(false);
//		txtPersonaAutoriza.setVisible(false);
		tabPaxFree.setDisabled(true);
	}

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.window.ui.IOpcionesMantenimiento#onSearch()
	 */
	@Override
	public void onSearch() throws Exception {
		final WndFiltrarParametros oWndFiltrar = new WndFiltrarParametros();

		oWndFiltrar.addParameter("1. Apellido Paterno", String.class);
		oWndFiltrar.addParameter("2. Apellido Materno", String.class);
		oWndFiltrar.addParameter("3. Nombres", String.class);
		oWndFiltrar.addParameter("4. N� de Documento", String.class);
		this.appendChild(oWndFiltrar);
		oWndFiltrar.setMode("modal");
		oWndFiltrar.addEventListener(pe.itsb.vyrbus.view.ui.Events.ON_FILTER, new EventListener<Event>() {

			@Override
			public void onEvent(Event event) throws Exception {
				condicionBusqueda= new TreeMap<>();

				String numeroIdentificacion = (String) oWndFiltrar.getParameterValue("4. N� de Documento");
				String apellidoPaterno = (String) oWndFiltrar.getParameterValue("1. Apellido Paterno");
				String apellidoMaterno = (String) oWndFiltrar.getParameterValue("2. Apellido Materno");
				String nombre = (String) oWndFiltrar.getParameterValue("3. Nombres");

				String nombresApellidos=nombre+" "+apellidoPaterno+" "+apellidoMaterno;


				if(!(nombresApellidos.trim().isEmpty()) && numeroIdentificacion.trim().isEmpty()){
					condicionBusqueda.put("nombresApellidos", nombresApellidos.trim());
					String[] str1 = nombresApellidos.trim().split(" ");
					listarRegistros(ServiceLocator.getPasajeroManager().buscarPorFullTextIndex(str1));
				}else{
					if(!(numeroIdentificacion.trim().isEmpty()))
						condicionBusqueda.put("numeroDocumento", numeroIdentificacion);
					if(!(nombresApellidos.trim().isEmpty()))
						condicionBusqueda.put("nombresApellidos", nombresApellidos.trim());
					condicionBusqueda.put("estadoRegistro", Constantes.VALUE_ACTIVO);

					listarRegistros(ServiceLocator.getPasajeroManager().buscarPorX(condicionBusqueda, criteriosOrdenar));
				}





//				listarRegistros(ServiceLocator.getPasajeroManager().buscarPorX(condicionBusqueda, criteriosOrdenar), paxFree);
			}
		});
	}
	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.window.ui.IOpcionesMantenimiento#onRefresh(int)
	 */
	@Override
	public void onRefresh(int tab) throws Exception {
		if (!condicionBusqueda.isEmpty()) {
			this.listarRegistros(ServiceLocator.getPasajeroManager().buscarPorX(condicionBusqueda, criteriosOrdenar));
		}
	}

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.window.ui.IOpcionesMantenimiento#onModify(int)
	 */
	@Override
	public void onModify(int tab) throws Exception {
		//Action=ACTION_MODIFY;
		Listitem listitem=listboxLista.getItemAtIndex(listboxLista.getSelectedIndex());
		mantenimientoRegistro(((Pasajero)listitem.getValue()).getId());
		habilitarPasajeroFrecuente(chkPasajeroFrecuente.isChecked());
		habilitarPersonaNoGrata(chkPersonaNoGrata.isChecked());
		tabPaxFree.setDisabled(false);

		/*Aplica acceso a los siguientes controles */
		List<Component>lstComponents=new ArrayList<>();
		lstComponents.add(cboDocumentoIdentificacion);
		lstComponents.add(txtNumeroIdentificacion);
		lstComponents.add(txtApellidoMaterno);
		lstComponents.add(txtApellidoPaterno);
		lstComponents.add(txtNombre);

		List<Rol>listaRolesAcceso=new ArrayList<>();
		listaRolesAcceso.add(new Rol(Constantes.ID_ROL_SUPER_USUARIO));
		listaRolesAcceso.add(new Rol(Constantes.ID_ROL_ADMINISTRADOR));
//		listaRolesAcceso.add(new Rol(Constantes.ID_ROL_GERENCIA_COMERCIAL));
//		listaRolesAcceso.add(new Rol(Constantes.ID_ROL_ASISTENTE_ADMIN_COMERCIAL));
		accesoControlsByRol(lstComponents, listaRolesAcceso);

	}

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.window.ui.IOpcionesMantenimiento#onCancel(int)
	 */
	@Override
	public void onCancel(int action) throws Exception {
		tbPasajero.setSelectedIndex(0);

	}

	@Override
	public void onSave (int action) throws Exception {
		try{
			if(!(cboDocumentoIdentificacion.getSelectedItem().getValue() instanceof TipoDocumento))
				throw new TipoDocumentoNullException();
			else if(txtNumeroIdentificacion.getText().trim().equals(""))
				throw new NumeroDocumentoNullException();
			else if(txtApellidoPaterno.getText().trim().equals(""))
				throw new ApellidoPaternoNullException();
			else if(txtNombre.getText().trim().equals(""))
				throw new NombresNullException();
			else if(txtUbicacionGeografica.getText().trim().equals(""))
				throw new UbigeoNullException();
			else if(!(cboSexo.getSelectedItem().getValue() instanceof Sexo))
				throw new SexoNullException();
			else if ((chkPersonaNoGrata.isChecked()) && txtMotivoNoGrata.getText().trim().equals(""))
				throw new PaxMotivoNoGrataNullException();
			else if (chkPasajeroFrecuente.isChecked() && txtNumeroTarjeta.getText().trim().equals(""))
				throw new PaxFreNumeroTarjetaNullException();
			else if (chkPasajeroFrecuente.isChecked() && (cboDocumentoIdentificacion.getText().equals("S/D")))
				throw new TipoDocumentoNullException();
			else if (chkPasajeroFrecuente.isChecked() && txtApellidoMaterno.getText().trim().equals(""))
				throw new ApellidoMaternoNullException();
			else if (chkPasajeroFrecuente.isChecked() && txtDireccion.getText().trim().equals(""))
				throw new DireccionNullException();
			else if (chkPasajeroFrecuente.isChecked() && txtTelefono.getText().trim().equals(""))
				throw new TelefonoNullException();
			else if (chkPasajeroFrecuente.isChecked() && (!(dbfechaNacimiento.getValue() instanceof Date)))
				throw new FechaNacimientoNullxception();
//			else if (!(cmbAgencia.getSelectedItem().getValue() instanceof Agencia))
//				throw new AgenciaNullException();
			else if (!(txtCorreoElectronico.getText().trim().isEmpty()))
				if (!UtilData.validateEmail(txtCorreoElectronico.getText().trim()))
					throw new MailIncorectoException();

			/*Validando que los apellidos y nombres no incluyan comillas simples - jabanto */
			if(txtApellidoPaterno.getText().trim().indexOf("'")>=0){
				DlgMessage.information(Messages.getString("WndVentaReserva.information.noComillaSimple")+", revice el Apellido Parteno del Pasajero.",txtApellidoPaterno);
				return;
			}else if(txtApellidoMaterno.getText().trim().indexOf("'")>=0){
				DlgMessage.information(Messages.getString("WndVentaReserva.information.noComillaSimple")+", revice el Apellido Parteno del Materno.",txtApellidoMaterno);
				return;
			}else if(txtNombre.getText().trim().indexOf("'")>0){
				DlgMessage.information(Messages.getString("WndVentaReserva.information.noComillaSimple")+", revice los Nombres del Pasajero.",txtNombre);
				return;
			}

			if (action==ACTION_NEW)
				oPasajero = new Pasajero();

			TipoDocumento oTipoDocumento = new TipoDocumento();
			Sexo oSexo = new Sexo();
			Ubigeo oUbigeo = new Ubigeo();
			Agencia oAgencia = new Agencia();

			Long id = (textboxId.getText().equals("") ? null : new Long(textboxId.getText()));

			oTipoDocumento.setId(((TipoDocumento) cboDocumentoIdentificacion.getSelectedItem().getValue()).getId());
			oTipoDocumento.setDenominacion(((TipoDocumento) cboDocumentoIdentificacion.getSelectedItem().getValue()).getDenominacion());

			/*Valida el dicumento cuando es DNI*/
			if(oTipoDocumento.getId().intValue()==Constantes.ID_TIPDOC_DNI){
				if(txtNumeroIdentificacion.getText().trim().length()!=8){
					DlgMessage.information(Messages.getString("WndVentaReserva.information.noValidDocumentPax"),txtNumeroIdentificacion);
					throw new CancelaGrabacionException();
				}else if (!(Util.isNumeric(txtNumeroIdentificacion.getText().trim()))){
					DlgMessage.information(Messages.getString("WndVentaReserva.information.noValidDocumentPax"),txtNumeroIdentificacion);
					throw new CancelaGrabacionException();
				}
			}


			oSexo.setId(((Sexo) cboSexo.getSelectedItem().getValue()).getId());
			oUbigeo.setId(txtIdUbigeo.getText());
			oAgencia.setId(getAgencia().getId());
//			oAgencia.setId(((Agencia) cmbAgencia.getSelectedItem().getValue()).getId());

			oPasajero.setId(id);
			oPasajero.setTipoDocumento(oTipoDocumento);
			oPasajero.setNumeroDocumento(txtNumeroIdentificacion.getText().trim());
			oPasajero.setApellidoPaterno(txtApellidoPaterno.getText().trim().toUpperCase());
			oPasajero.setApellidoMaterno(txtApellidoMaterno.getText().trim().toUpperCase());
			oPasajero.setNombre(txtNombre.getText().trim().toUpperCase());
			oPasajero.setNombresApellidos(oPasajero.getNombre()+" "+oPasajero.getApellidoPaterno()+" "+oPasajero.getApellidoMaterno());
			oPasajero.setFechaNacimiento(Util.DatetoString(dbfechaNacimiento.getValue(), Constantes.DATE_FORMAT)
					.equals(Util.DatetoString(Constantes.FECHA_NULL, Constantes.DATE_FORMAT))?null:Util.DatetoString(dbfechaNacimiento.getValue(), Constantes.DATE_FORMAT));

			oPasajero.setSexo(oSexo);
			if (!(cboEstadoCivil.getSelectedItem().getValue() instanceof EstadoCivil)){
				oPasajero.setEstadoCivil(null);
			}else{
				EstadoCivil oEstadoCivil = new EstadoCivil();
				oEstadoCivil.setId(((EstadoCivil) cboEstadoCivil.getSelectedItem().getValue()).getId());
				oPasajero.setEstadoCivil(oEstadoCivil);
			}
			oPasajero.setDireccion(txtDireccion.getText().trim().toUpperCase());
			oPasajero.setUbigeo(oUbigeo);
			oPasajero.setTelefono(txtTelefono.getText());
			oPasajero.setEmail(txtCorreoElectronico.getText());
			oPasajero.setKilometros(.00);
			oPasajero.setIndeseable((chkPersonaNoGrata.isChecked() ? 1 : 0));
			oPasajero.setMotivo(txtMotivoNoGrata.getText());
			oPasajero.setAgencia(oAgencia);
			oPasajero.setEstadoRegistro(Constantes.VALUE_ACTIVO);

			/*Valida si es DNI y si fue validado correctamente por la RENIEC. - 06/04/2015*/
			oPasajero.setValidadoReniec(Constantes.FALSE_VALUE);
			if(oPasajero.getTipoDocumento().getId().intValue()==Constantes.ID_TIPDOC_DNI){
				if(imgValidacionDNI.getSrc()!=null && !(imgValidacionDNI.getSrc().trim().isEmpty()) && imgValidacionDNI.getSrc().equals(Constantes.IMAGE_VALIDACION_DNI_OK))
					oPasajero.setValidadoReniec(Constantes.TRUE_VALUE);
			}

			//VALIDACION PARA EL PAXFREE
			if(action==ACTION_MODIFY){
				if( opasajeroFrecuente.getId() !=null){//PAXFREE ya fue registrado anteriormente)
					if(opasajeroFrecuente.getEstado().equals(Constantes.TRUE_VALUE)){ //PAXFREE ACTIVO
						if(!(chkPasajeroFrecuente.isChecked())){//El PAXFRE esta siendo desactivado.
							opasajeroFrecuente.setEstado(Constantes.FALSE_VALUE);
							opasajeroFrecuente.setFechaSuspension(Constantes.FORMAT_DATE_TIME_24H.parse(lblFechaSuspension.getValue()));

							UtilData.auditarRegistro(opasajeroFrecuente, true, getUsuario(),Executions.getCurrent());
							ServiceLocator.getPasajeroFrecuenteManager().actualizar(opasajeroFrecuente);

							opasajeroFrecuente.setPasajero(oPasajero);
							enviarMail(false, opasajeroFrecuente);
						}
					}else{//PAXFREE INACTIVO
						if(chkPasajeroFrecuente.isChecked()){//El PAXFRE esta siendo reactivado
							/*Inserta al historico de movimientos sobre las actualizaciones de las membresias de los pasajeros frecuentes.*/
							HistoricoMembresia historicoMembresia= new HistoricoMembresia();
							historicoMembresia.setPasajeroFrecuente(opasajeroFrecuente);
							historicoMembresia.setFechaActivacionAnterior(opasajeroFrecuente.getFechaActivacion());
							historicoMembresia.setFechaCaducidadAnterior(opasajeroFrecuente.getFechaCaducidad());
							historicoMembresia.setFechaSuspensionAnterior(opasajeroFrecuente.getFechaSuspension());
							historicoMembresia.setNuevaFechaActivacion(Constantes.FORMAT_DATE_TIME_24H.parse(lblFechaActivacion.getValue()));
							historicoMembresia.setNuevaFechaCaducidad(Constantes.FORMAT_DATE_TIME_24H.parse(lblFechaCaducidad.getValue()));
							historicoMembresia.setEstadoRegistro(Constantes.VALUE_ACTIVO);
							UtilData.auditarRegistro(historicoMembresia, getUsuario(), Executions.getCurrent());
							ServiceLocator.getHistoricoMembresiaManager().guardar(historicoMembresia);
							//Actualiza el pasajero frecuente.
							opasajeroFrecuente.setFechaActivacion(Constantes.FORMAT_DATE_TIME_24H.parse(lblFechaActivacion.getValue()));
							opasajeroFrecuente.setFechaCaducidad(Constantes.FORMAT_DATE_TIME_24H.parse(lblFechaCaducidad.getValue()));
							opasajeroFrecuente.setFechaSuspension(null);
							opasajeroFrecuente.setEstado(Constantes.TRUE_VALUE);
							UtilData.auditarRegistro(opasajeroFrecuente, true, getUsuario(),Executions.getCurrent());
							ServiceLocator.getPasajeroFrecuenteManager().actualizar(opasajeroFrecuente);

							enviarMail(true, opasajeroFrecuente);
						}
					}

				}else{
					if(chkPasajeroFrecuente.isChecked()){//EL PAXFREE esta siendo activado por primara vez
						opasajeroFrecuente = new PasajeroFrecuente();
						UtilData.auditarRegistro(opasajeroFrecuente, false, getUsuario(),Executions.getCurrent());

						opasajeroFrecuente.setEstado(Constantes.TRUE_VALUE);
						opasajeroFrecuente.setPasajero(oPasajero);
						opasajeroFrecuente.setId(null);
						opasajeroFrecuente.setNumeroTarjeta(txtNumeroTarjeta.getText());
						opasajeroFrecuente.setFechaIngreso(Constantes.FORMAT_DATE_TIME_24H.parse(lblFechaIngreso.getValue()));
						opasajeroFrecuente.setPuntosAcumulados(Integer.valueOf(lblPuntosAcumulados.getValue()));
						opasajeroFrecuente.setPuntosUtilizados(Integer.valueOf(lblPuntosUtilizados.getValue()));
//						opasajeroFrecuente.setPuntosUtilizados(Integer.valueOf(lblPuntosUtilizados.getValue()));
						opasajeroFrecuente.setFechaActivacion(Constantes.FORMAT_DATE_TIME_24H.parse(lblFechaActivacion.getValue()));
						opasajeroFrecuente.setFechaCaducidad(Constantes.FORMAT_DATE_TIME_24H.parse(lblFechaCaducidad.getValue()));
						if(lblFechaSuspension.getValue()!=null && !(lblFechaSuspension.getValue().isEmpty()))
							opasajeroFrecuente.setFechaSuspension(Constantes.FORMAT_DATE_TIME_24H.parse(lblFechaSuspension.getValue()));
						opasajeroFrecuente.setEstadoRegistro(Constantes.VALUE_ACTIVO);
						opasajeroFrecuente.setAgencia(oAgencia);
						ServiceLocator.getPasajeroFrecuenteManager().guardar(opasajeroFrecuente);

						enviarMail(true, opasajeroFrecuente);
					}
				}
			}

			switch (action) {
				case ACTION_NEW:
					UtilData.auditarRegistro(oPasajero, getUsuario(), Executions.getCurrent());
					ServiceLocator.getPasajeroManager().guardar(oPasajero);
					textboxId.setText((new Long(oPasajero.getId()).toString()));
					break;

				case ACTION_MODIFY:
					UtilData.auditarRegistro(oPasajero, true, getUsuario(), Executions.getCurrent());
					ServiceLocator.getPasajeroManager().actualizar(oPasajero);
					break;
			}
			/*RECUEPRA EL REGISTRO ACTUALIZADO O EL NUEVO*/
			condicionBusqueda= new TreeMap<>();
			condicionBusqueda.put("id", oPasajero.getId());
			condicionBusqueda.put("estadoRegistro", Constantes.VALUE_ACTIVO);
			listarRegistros(ServiceLocator.getPasajeroManager().buscarPorX(condicionBusqueda, criteriosOrdenar));

		}catch (CancelaGrabacionException cex){
			throw new CancelaGrabacionException();
		}catch (FechaNacimientoNullxception fnexc){
			DlgMessage.information(Messages.getString("WndPasajero.information.NoFechaNacimiento"),txtCorreoElectronico);
			tbPasajero.setSelectedIndex(0);
			throw new CancelaGrabacionException();
		}catch (MailIncorectoException mie){
			DlgMessage.information(Messages.getString("Generales.information.mailIncorrecto"),txtCorreoElectronico);
			throw new CancelaGrabacionException();
		}catch(TipoDocumentoNullException tdnex){
			DlgMessage.information(Messages.getString("WndVentaReserva.information.noSelectionTipoDocumento"),cboDocumentoIdentificacion);
			throw new CancelaGrabacionException();
		}catch(NumeroDocumentoNullException ndnex){
			DlgMessage.information(Messages.getString("WndVentaReserva.information.noDocumentoPax"),txtNumeroIdentificacion);
			throw new CancelaGrabacionException();
		}catch(ApellidoPaternoNullException apnex){
			DlgMessage.information(Messages.getString("WndVentaReserva.information.noApellidoPaterno"),txtApellidoPaterno);
			throw new CancelaGrabacionException();
		}catch(NombresNullException nnex){
			DlgMessage.information(Messages.getString("WndVentaReserva.information.noNombres"),txtNombre);
			throw new CancelaGrabacionException();
		}catch(UbigeoNullException unex){
			DlgMessage.information(Messages.getString("WndVentaReserva.information.noUbigeo"),btnUbicacionGeografica);
			throw new CancelaGrabacionException();
		}catch(SexoNullException snex){
			DlgMessage.information(Messages.getString("WndVentaReserva.information.noSelectionSexo"),cboSexo);
			throw new CancelaGrabacionException();
		}catch(DocumentoPaxDuplicadoException dpdnex){
			DlgMessage.information(Messages.getString("WndPasajero.information.noDocumentoPaxDuplicado"),txtNumeroIdentificacion);
			throw new CancelaGrabacionException();
		}catch(PaxMotivoNoGrataNullException pmngex){
			DlgMessage.information(Messages.getString("WndPasajero.information.PaxMotivoNoGrata"),txtMotivoNoGrata);
			throw new CancelaGrabacionException();
		}catch(PaxFreNumeroTarjetaNullException pfntnex){
			DlgMessage.information(Messages.getString("WndPasajero.information.PaxFreNumeroTarjeta"),txtNumeroTarjeta);
			throw new CancelaGrabacionException();
		}catch(CorreoElectronicoNullException cenex){
			DlgMessage.information(Messages.getString("WndPasajero.information.CorreoElectronico"),txtCorreoElectronico);
			tbPasajero.setSelectedIndex(0);
			throw new CancelaGrabacionException();
		}catch(ApellidoMaternoNullException amnex){
			DlgMessage.information(Messages.getString("WndPasajero.information.ApellidoMaterno"),txtApellidoMaterno);
			tbPasajero.setSelectedIndex(0);
			throw new CancelaGrabacionException();
		}catch(DireccionNullException dnex){
			DlgMessage.information(Messages.getString("WndPasajero.information.Direccion"),txtDireccion);
			tbPasajero.setSelectedIndex(0);
			throw new CancelaGrabacionException();
		}catch(TelefonoNullException tnex){
			DlgMessage.information(Messages.getString("Debe ingresar un N�mero de Tel�fono del Pasajero Frecuente."),txtTelefono);
			tbPasajero.setSelectedIndex(0);
			throw new CancelaGrabacionException();
		}catch(TarjetaDuplicadaException tdex){
			DlgMessage.information(Messages.getString("WndPasajero.information.DuplicaTarjeta"),txtNumeroTarjeta);
			throw new CancelaGrabacionException();
//		}catch (AgenciaNullException anex){
//			DlgMessage.information(Messages.getString("WndPasajero.information.NoAgencia"),cmbAgencia);
//			throw new CancelaGrabacionException();
		}catch(Exception ex){
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
				Listitem listitem=listboxLista.getItemAtIndex(listboxLista.getSelectedIndex());
				id = ((Pasajero)listitem.getValue()).getId();
				break;
			case TAB_MAINTENANCE:
				id = new Long(textboxId.getText());
				break;
		}
		ServiceLocator.getPasajeroManager().inactivar(id);
	}

	@Override
	public void onPrint(int tab) {
		// TODO Auto-generated method stub
	}

	@Override
	public void onExport(int tab) {
		// TODO Auto-generated method stub
	}

	@Override
	public void onHelp() {
		// TODO Auto-generated method stub
	}

	@Override
	public void onChangeTab(int tab) throws Exception {
		switch (tab) {
			case TAB_LIST:
				break;
			case TAB_MAINTENANCE:
				if (listboxLista.getSelectedIndex() > -1) {
					Listitem listitem=listboxLista.getItemAtIndex(listboxLista.getSelectedIndex());
					mantenimientoRegistro(((Pasajero)listitem.getValue()).getId());
				}
				break;
		}
	}


	@Override
	public void onClose() {
		closeTabWindow();
	}

	private void habilitarPasajeroFrecuente(boolean habilitar) {
		txtNumeroTarjeta.setDisabled(!habilitar);
	}

	private void habilitarPersonaNoGrata(boolean habilitar) {
		txtMotivoNoGrata.setDisabled(!habilitar);
		if (!habilitar) {
			txtMotivoNoGrata.setText("");
		}
	}

	private void listarRegistros(ArrayList<Pasajero> lstRegistros) throws Exception {
		listboxLista.getItems().clear();

		Listitem item=null;
		Listcell cell=null;
		int i=0;
		for(Pasajero pasajero: lstRegistros){
			i++;

			item=new Listitem();
			cell=new Listcell(String.valueOf(i));
			item.appendChild(cell);
			cell=new Listcell(pasajero.getTipoDocumento().getDenominacion());
			item.appendChild(cell);
			cell=new Listcell(pasajero.getNumeroDocumento());
			cell.setStyle("font-size:11px !important");
			item.appendChild(cell);
			cell=new Listcell(pasajero.toString());
			item.appendChild(cell);
			cell=new Listcell(pasajero.getFechaNacimiento()!=null?pasajero.getFechaNacimiento(): "");
			cell.setStyle("font-size:11px !important");
			item.appendChild(cell);
			cell=new Listcell(pasajero.getSexo().getDenominacion());
			item.appendChild(cell);
			cell=new Listcell();
			final Button button = new Button("Viajes", "resources/mp_preliminar.png");
			button.setClass("btnCommandM");
			button.addEventListener(Events.ON_CLICK, new EventListener<Event>() {
				@Override
				public void onEvent(Event e){
					wndHistory = onCreateWindowsHistory(e.getTarget().getId());
					wndPasajero.appendChild(wndHistory);
					wndHistory.doModal();
				}
			});
			button.setId(pasajero.getId().toString());
//			button.setWidth("100px");
			button.setTooltiptext("Haga click aqui para visualizar el historial de viajes del pasajero.");
			cell.appendChild(button);
			item.appendChild(cell);
			item.setValue(pasajero);
			listboxLista.appendChild(item);
		}
	}

//	/**
//	 * Verifica si el Pasajero existe el la reniec si es que se registrando un nuevo Pas
//	 * @throws Exception
//	 * @throws WrongValueException
//	 */
//	public void validarPax_MuestraBDReniec() throws WrongValueException, Exception{
//		if(oPasajero==null  && !(txtNumeroIdentificacion.getText().trim().isEmpty()) ){
//			Reniec reniec= ServiceLocator.getReniecManager().buscarPax(txtNumeroIdentificacion.getText().trim());
//			if(reniec!=null){
//				Util.seleccionarValorItemCombo(TipoDocumento.class, cboDocumentoIdentificacion, Constantes.ID_TIPDOC_DNI);
//				txtApellidoPaterno.setText(reniec.getApellidoPaterno());
//				txtApellidoMaterno.setText(reniec.getApellidoMaterno());
//				txtNombre.setText(reniec.getNombres());
//				dbfechaNacimiento.setValue(Constantes.FORMAT_DATE.parse(reniec.getFechaNacimiento()));
//				Util.seleccionarValorItemCombo(Sexo.class, cboSexo, Integer.valueOf(reniec.getSexo()));
//
//			}else{
//				String numeroDocumento=txtNumeroIdentificacion.getText().trim();
//				Integer idTipoDocumento= null;
//				if(cboDocumentoIdentificacion.getSelectedItem().getValue() instanceof TipoDocumento)
//					idTipoDocumento=((TipoDocumento)cboDocumentoIdentificacion.getSelectedItem().getValue()).getId();
//
//				limpiarControles();
//				dbfechaNacimiento.setValue(Constantes.FECHA_NULL);
//				cboSexo.setSelectedIndex(0);
//				cboEstadoCivil.setSelectedIndex(0);
//				cmbAgencia.setSelectedIndex(0);
//				//recupera valores ingresado por el usuario
//				txtNumeroIdentificacion.setText(numeroDocumento);
//				if(idTipoDocumento!=null) Util.seleccionarValorItemCombo(TipoDocumento.class, cboDocumentoIdentificacion, idTipoDocumento);
//			}
//		}
//	}
//
//	/**
//	 * Reliza la validacion del pasajero con el metodop getIdentifidad del WS del mtc en funcion al parametro configurado.
//	 * @throws WrongValueException
//	 * @throws Exception
//	 */
//	public void verificarPaxReniec() throws WrongValueException, Exception{
//		imgValidacionDNI.setSrc("");
//		Parametros parametros=ServiceLocator.getParametrosManager().buscarPorEstadoRegistro(Constantes.VALUE_ACTIVO);
//		if(parametros.getValidarDNIgetIdentidad()!=null && parametros.getValidarDNIgetIdentidad().intValue()==Constantes.TRUE_VALUE)
//			validarPax_getIdentidadMTC();
//		else
//			validarPax_MuestraBDReniec();
//	}

	/**
	 * Verifica si el Pasajero existe el la reniec si es que se registrando un nuevo Pas
	 * @throws Exception
	 * @throws WrongValueException
	 */
	public void verificarPaxReniec() throws WrongValueException, Exception{
		txtApellidoPaterno.setDisabled(false);
		txtApellidoMaterno.setDisabled(false);
		txtNombre.setDisabled(false);

		if(oPasajero==null
				&& !(txtNumeroIdentificacion.getText().trim().isEmpty())
				&& cboDocumentoIdentificacion.getSelectedItem().getValue() instanceof TipoDocumento
				&& ((TipoDocumento)cboDocumentoIdentificacion.getSelectedItem().getValue()).getId().intValue()==Constantes.ID_TIPDOC_DNI){

			String numerodocumento=txtNumeroIdentificacion.getText().trim();
			ResultIdentidad resultIdentidad=Util.getResultIdentidad(numerodocumento,imgValidacionDNI);
			if(resultIdentidad!=null){
				Util.seleccionarValorItemCombo(TipoDocumento.class, cboDocumentoIdentificacion, Constantes.ID_TIPDOC_DNI);
				/*si el DNI es correcto*/
				if(resultIdentidad.isReturn()){
					/*Para no permitir la modificacion de los appellidos y nombres al usuario*/
					txtApellidoPaterno.setDisabled(true);
					txtApellidoMaterno.setDisabled(true);
					txtNombre.setDisabled(true);
					/*Carga los apellidos y nombres retornados por la reniec*/
					txtApellidoPaterno.setText(resultIdentidad.getPaterno()!=null && !(resultIdentidad.getPaterno().trim().isEmpty()) ?resultIdentidad.getPaterno().trim():"");
					txtApellidoMaterno.setText(resultIdentidad.getMaterno()!=null && !(resultIdentidad.getMaterno().trim().isEmpty()) ? resultIdentidad.getMaterno().trim():"");
					txtNombre.setText(resultIdentidad.getNombre().trim());
					/*Obtiene datos como la fecha de nacimiento y el sexo de NUESTRA base de datos de la Reniec*/
					if(resultIdentidad.getReniec()!=null){
						dbfechaNacimiento.setValue(Constantes.FORMAT_DATE.parse(resultIdentidad.getReniec().getFechaNacimiento()));
						Util.seleccionarValorItemCombo(Sexo.class,cboSexo , Integer.valueOf(resultIdentidad.getReniec().getSexo()));
					}
				}
			}else{
				/*Consulta con NUESTRA BD reniec*/
				Reniec reniec= ServiceLocator.getReniecManager().buscarPax(numerodocumento);
				if(reniec!=null){
					Util.seleccionarValorItemCombo(TipoDocumento.class, cboDocumentoIdentificacion, Constantes.ID_TIPDOC_DNI);
					txtApellidoPaterno.setText(reniec.getApellidoPaterno());
					txtApellidoMaterno.setText(reniec.getApellidoMaterno());
					txtNombre.setText(reniec.getNombres());
					dbfechaNacimiento.setValue(Constantes.FORMAT_DATE.parse(reniec.getFechaNacimiento()));
					Util.seleccionarValorItemCombo(Sexo.class, cboSexo, Integer.valueOf(reniec.getSexo()));
				}else{
					Integer idTipoDocumento= null;
					if(cboDocumentoIdentificacion.getSelectedItem().getValue() instanceof TipoDocumento)
						idTipoDocumento=((TipoDocumento)cboDocumentoIdentificacion.getSelectedItem().getValue()).getId();

					limpiarControles();
					dbfechaNacimiento.setValue(Constantes.FECHA_NULL);
					cboSexo.setSelectedIndex(0);
					cboEstadoCivil.setSelectedIndex(0);
//					cmbAgencia.setSelectedIndex(0);
					//recupera valores ingresado por el usuario
					txtNumeroIdentificacion.setText(numerodocumento);
					if(idTipoDocumento!=null) Util.seleccionarValorItemCombo(TipoDocumento.class, cboDocumentoIdentificacion, idTipoDocumento);
				}
			}
		}
	}

	/**
	 * Para la edici�n del pasajero
	 * @param id : Identificacor del Pasajero.
	 * @throws Exception
	 */
	private void mantenimientoRegistro(Long id) throws Exception  {
//		Listitem listitem=listboxLista.getItemAtIndex(listboxLista.getSelectedIndex());
		oPasajero =ServiceLocator.getPasajeroManager().buscarPorId(id); //listitem.getValue();
		/*Busca PAXFREE*/
		PasajeroFrecuente paxFree= ServiceLocator.getPasajeroFrecuenteManager().buscarPaxFreeAndPuntos(oPasajero.getId(),null);
		if(paxFree!=null)
			oPasajero.setPasajeroFrecuente(paxFree);

		Ubigeo oUbigeo = ServiceLocator.getUbigeoManager().buscarPorId(oPasajero.getUbigeo().getId());
		String ubicacionCompleta = new String();

		if (oPasajero.getUbigeo() != null)
			ubicacionCompleta = ServiceLocator.getUbigeoManager().ubicacionGeografica(oUbigeo);
		if (oPasajero.getEstadoCivil() != null)
			Util.seleccionarValorItemCombo(EstadoCivil.class, cboEstadoCivil, oPasajero.getEstadoCivil().getId());
		else cboEstadoCivil.setSelectedIndex(0);
		if (oPasajero.getTipoDocumento() !=null)
			Util.seleccionarValorItemCombo(TipoDocumento.class, cboDocumentoIdentificacion, oPasajero.getTipoDocumento().getId());
		else cboDocumentoIdentificacion.setSelectedIndex(0);
		if (oPasajero.getSexo() != null)
			Util.seleccionarValorItemCombo(Sexo.class,cboSexo, oPasajero.getSexo().getId());
		else cboSexo.setSelectedIndex(0);
//		if (oPasajero.getAgencia() !=null)
//			Util.seleccionarValorItemCombo(Agencia.class,cmbAgencia, oPasajero.getAgencia().getId());

		textboxId.setText(oPasajero.getId().toString());
		txtNumeroIdentificacion.setText(oPasajero.getNumeroDocumento());
		txtApellidoPaterno.setText(oPasajero.getApellidoPaterno());
		txtApellidoMaterno.setText(oPasajero.getApellidoMaterno()!=null?oPasajero.getApellidoMaterno():"");
		txtNombre.setText(oPasajero.getNombre());
		if (oPasajero.getFechaNacimiento() != null)
			dbfechaNacimiento.setText(oPasajero.getFechaNacimiento().toString());
		else
			dbfechaNacimiento.setText(null);
		txtDireccion.setText(oPasajero.getDireccion());
		txtIdUbigeo.setText(oUbigeo.getId());
		txtUbicacionGeografica.setText(ubicacionCompleta);
		txtTelefono.setText(oPasajero.getTelefono());
		txtCorreoElectronico.setText(oPasajero.getEmail());
		lblKilometrosAcumulados.setValue(oPasajero.getKilometros()!=null?oPasajero.getKilometros().toString():"0");
		chkPersonaNoGrata.setChecked(oPasajero.getIndeseable()!=null?(oPasajero.getIndeseable() == 1):false);
		txtMotivoNoGrata.setText(oPasajero.getMotivo());

		/* RECUPERA DATOS DEL PASAJERO FRECUENTE */
		opasajeroFrecuente = new PasajeroFrecuente();
		if(oPasajero.getPasajeroFrecuente() !=null){
			opasajeroFrecuente =oPasajero.getPasajeroFrecuente(); //((Pasajero)listitem.getValue()).getPasajeroFrecuente();
			txtIdPasajeroFrecuente.setText(opasajeroFrecuente.getId().toString());
			txtNumeroTarjeta.setText(opasajeroFrecuente.getNumeroTarjeta().toString());
			lblFechaIngreso.setValue(Constantes.FORMAT_DATE_TIME_24H.format(opasajeroFrecuente.getFechaIngreso()));
			lblPuntosAcumulados.setValue(opasajeroFrecuente.getPuntosAcumulados().toString());
			lblPuntosUtilizados.setValue(opasajeroFrecuente.getPuntosUtilizados().toString());
			lblPuntosDisponibles.setValue(String.valueOf(opasajeroFrecuente.getPuntosAcumulados().intValue()-opasajeroFrecuente.getPuntosUtilizados().intValue()));
			lblFechaActivacion.setValue(Constantes.FORMAT_DATE_TIME_24H.format(opasajeroFrecuente.getFechaActivacion()));
			lblFechaCaducidad.setValue(Constantes.FORMAT_DATE_TIME_24H.format(opasajeroFrecuente.getFechaCaducidad()));
			lblFechaSuspension.setValue(opasajeroFrecuente.getFechaSuspension()!=null?Constantes.FORMAT_DATE_TIME_24H.format(opasajeroFrecuente.getFechaSuspension()):"");
			if(opasajeroFrecuente.getEstado().equals(Constantes.TRUE_VALUE)){
				chkPasajeroFrecuente.setChecked(true);
				lbEstado.setValue(Constantes.LABEL_ACTIVO_DESCP);
			}else{
				chkPasajeroFrecuente.setChecked(false);
				lbEstado.setValue(Constantes.LABEL_INACTIVO_DESCP);
			}
			txtNumeroTarjeta.setReadonly(true);
			txtNumeroIdentificacion.setDisabled(true);
		}else{
			chkPasajeroFrecuente.setChecked(false);
			txtIdPasajeroFrecuente.setText("");
			txtNumeroTarjeta.setText("");
			lblFechaIngreso.setValue("");
			lblPuntosAcumulados.setValue("");
			lblPuntosUtilizados.setValue("");
			lblFechaActivacion.setValue("");
			lblPuntosDisponibles.setValue("");
			lblFechaCaducidad.setValue("");
			lblFechaSuspension.setValue("");
			lbEstado.setValue(ESTADO_PAXFREE_NOREGISTRADO);
//			lbEstado.setValue(Constantes.NOREGISTRADO_DESCP);
			txtNumeroIdentificacion.setDisabled(false);
		}
		cargarPasajesAcumulados(oPasajero);

		/*Establece la imagen segun validacion del DNI del pasajero con la reniec - 04/04/2015 - jabanto*/
		if(oPasajero.getTipoDocumento().getId().intValue()==Constantes.ID_TIPDOC_DNI)
			Util.imagenValidacionDNIReniec(oPasajero.getValidadoReniec(), imgValidacionDNI);

		tbPasajero.setSelectedIndex(0);
//		cmbAgencia.setDisabled(true);
//		List<VentaPasaje> lstVentas = ServiceLocator.getVentaPasajesManager().buscarVentasByPasajero(oPasajero.getId());
	}

	public void onchkPasajeroFrecuente() throws Exception{
		MyTime time=new MyTime();
		Date mtime=Constantes.FORMAT_DATE_TIME_24H.parse(time.dateServer());

		if(oPasajero.getPasajeroFrecuente() !=null){//
			if(opasajeroFrecuente.getEstado().equals(Constantes.TRUE_VALUE)){ //El PAXFREE esta Activo.
				if (!(chkPasajeroFrecuente.isChecked())){ //El usuario inactiva el PaxFree
					lblFechaSuspension.setValue(Constantes.FORMAT_DATE_TIME_24H.format(mtime));
					lbEstado.setValue(Constantes.LABEL_INACTIVO_DESCP);

				}else{//El usuario Activa el PaxFree
					lbEstado.setValue(Constantes.LABEL_ACTIVO_DESCP);
					lblFechaSuspension.setValue("");
				}
			}else{//El PAXFREE esta Inactivo.
				if (!(chkPasajeroFrecuente.isChecked())){ //El usuario inactiva el PaxFree
					lblFechaCaducidad.setValue(Constantes.FORMAT_DATE_TIME_24H.format(opasajeroFrecuente.getFechaCaducidad()));
					lbEstado.setValue(Constantes.LABEL_ACTIVO_DESCP);
					lblFechaSuspension.setValue(Constantes.FORMAT_DATE_TIME_24H.format(opasajeroFrecuente.getFechaSuspension()));
					lblFechaActivacion.setValue(Constantes.FORMAT_DATE_TIME_24H.format(opasajeroFrecuente.getFechaActivacion()));
				}else{//El usuario Activa el PaxFree
					String fechaCaducidad=fechaCaducidad();
					lblFechaCaducidad.setValue(fechaCaducidad);
					lbEstado.setValue(Constantes.LABEL_ACTIVO_DESCP);
					lblFechaActivacion.setValue(Constantes.FORMAT_DATE_TIME_24H.format(mtime));
					lblFechaSuspension.setValue("");
				}

			}
		}else{
			if (chkPasajeroFrecuente.isChecked()){
				String fechaCaducidad=fechaCaducidad();

				lblFechaIngreso.setValue(Constantes.FORMAT_DATE_TIME_24H.format(mtime));
				lblPuntosAcumulados.setValue("0");
				lblPuntosUtilizados.setValue("0");
				lblPuntosDisponibles.setValue("0");
				lblFechaActivacion.setValue(Constantes.FORMAT_DATE_TIME_24H.format(mtime));
				lblFechaCaducidad.setValue(fechaCaducidad);
				lbEstado.setValue(Constantes.LABEL_ACTIVO_DESCP);
				txtNumeroTarjeta.setValue(numTarjetaPaxFree()); //txtNumeroTarjeta.setValue(agencia.getId()+"-"+usuario.getId()+"-");

				//txtNumeroTarjeta.setReadonly(true);
			}else{
				//txtNumeroTarjeta.setReadonly(true);
				txtNumeroTarjeta.setValue("");
				lblFechaIngreso.setValue("");
				lblPuntosAcumulados.setValue("");
				lblPuntosUtilizados.setValue("");
				lblPuntosDisponibles.setValue("");
				lblFechaActivacion.setValue("");
				lbEstado.setValue(ESTADO_PAXFREE_NOREGISTRADO);
//				lbEstado.setValue(Constantes.NOREGISTRADO_DESCP);
				lblFechaCaducidad.setValue("");
			}
		}
	}

	public void cargarPasajesAcumulados(Pasajero pasajero) throws Exception{
		listPasajesAcumulados.getItems().clear();

		MyTime time= new MyTime();
		Date mfechaActual=Constantes.FORMAT_DATE_TIME_24H.parse(time.dateServer());

//		String fechaFinal=Constantes.FORMAT_DATE.format(time.fechaServer().getTime());
		String fechaFinal=Constantes.FORMAT_DATE_TIME_24H.format(mfechaActual);
		long milSec_Menos=0;
		Long milSec_Act=(long)0;
		String fechaInicial="";

		//Cuando un pasajero frecuente esta activo
		if(pasajero.getPasajeroFrecuente() !=null && pasajero.getPasajeroFrecuente().getEstado().equals(Constantes.TRUE_VALUE)){
			fechaInicial=Constantes.FORMAT_DATE_TIME_24H.format(pasajero.getPasajeroFrecuente().getFechaActivacion());
			fechaFinal=Constantes.FORMAT_DATE_TIME_24H.format(pasajero.getPasajeroFrecuente().getFechaCaducidad());
		}else if(pasajero.getPasajeroFrecuente()!=null && pasajero.getPasajeroFrecuente().getEstado().intValue()==Constantes.FALSE_VALUE){
			//Cuando un pasajero frecuente esta inactivo
			long diasTrasncurridos=mfechaActual.getTime()-pasajero.getPasajeroFrecuente().getFechaSuspension().getTime();

			//Si los dias transcurridos desde la fecha de suspension hasta la fecha actual es menor o igual se toma la fecha inicial desde la fecha suspencion
			if(diasTrasncurridos<=Constantes.MILISEGUNDOS_X_DIA*Constantes.TIEMPO_PASAR_PAXFREE){
				fechaInicial=Constantes.FORMAT_DATE_TIME_24H.format(pasajero.getPasajeroFrecuente().getFechaSuspension());
			}else{
				//Si los dias transcurridos desde la fecha de suspension hasta la fecha actual es Mayor se toma la fecha inicial desde la fecha actual menos 6 meses atras.
				fechaInicial=Constantes.FORMAT_DATE_TIME_24H.format(mfechaActual.getTime()-(Constantes.MILISEGUNDOS_X_DIA*Constantes.TIEMPO_PASAR_PAXFREE));
			}

		}else{
			//Cuando noes aun pasajero frecuente
			milSec_Menos=Constantes.MILISEGUNDOS_X_DIA*Constantes.TIEMPO_PASAR_PAXFREE;
			milSec_Act=mfechaActual.getTime(); //.fechaServer().getTimeInMillis();
			Long milResul=milSec_Act-milSec_Menos;
			Calendar calendar= Calendar.getInstance();
			calendar.setTimeInMillis(milResul);
			Date dFechaInicial=calendar.getTime();
			fechaInicial=Constantes.FORMAT_DATE.format(dFechaInicial);
		}

		ArrayList<VentaPasaje>list =ServiceLocator.getVentaPasajesManager().buscarVentasPax(fechaInicial, fechaFinal, pasajero.getId());

		Listitem item = null;
		Listcell cell = null;

		int viajesNOValidos=0;

		for(VentaPasaje ventas: list){
			item= new Listitem();

			cell= new Listcell(ventas.getNumeroBoleto());
			cell.setStyle("font-size: 11px !important");
			if (ventas.getFormaPago().getId().intValue()==Constantes.ID_FORPAG_CORTESIA)
				cell.setStyle("color: #FA5858; font-size: 11px !important");
			item.appendChild(cell);

			cell=new Listcell(ventas.getServicio().getDenominacion());
			if (ventas.getFormaPago().getId().intValue()==Constantes.ID_FORPAG_CORTESIA)
				cell.setStyle("color: #FA5858;");
			item.appendChild(cell);

			cell=new Listcell(ventas.getRuta().getOrigen()+" - "+ventas.getRuta().getDestino());
			if (ventas.getFormaPago().getId().intValue()==Constantes.ID_FORPAG_CORTESIA)
				cell.setStyle("color: #FA5858;");
			item.appendChild(cell);

			cell=new Listcell(ventas.getFechaPartida()!=null? Constantes.FORMAT_DATE.format(ventas.getFechaPartida()): "");
			cell.setStyle("font-size: 11px !important");
			if (ventas.getFormaPago().getId().intValue()==Constantes.ID_FORPAG_CORTESIA)
				cell.setStyle("color: #FA5858; font-size: 11px !important");
			item.appendChild(cell);

			cell=new Listcell(ventas.getFormaPago().getDenominacion());
			if (ventas.getFormaPago().getId().intValue()==Constantes.ID_FORPAG_CORTESIA)
				cell.setStyle("color: #FA5858;");
			item.appendChild(cell);

			cell=new Listcell(ventas.getNumeroAsiento()!=null?ventas.getNumeroAsiento().toString(): "");
			cell.setStyle("font-size: 11px !important");
			if (ventas.getFormaPago().getId().intValue()==Constantes.ID_FORPAG_CORTESIA)
				cell.setStyle("color: #FA5858; font-size: 11px !important");
			item.appendChild(cell);

			cell=new Listcell(ventas.getImportePagado().toString());
			cell.setStyle("font-size: 11px !important");
			if (ventas.getFormaPago().getId().intValue()==Constantes.ID_FORPAG_CORTESIA)
				cell.setStyle("color: #FA5858; font-size: 11px !important");
			item.appendChild(cell);

			cell=new Listcell(Constantes.FORMAT_DATE.format(ventas.getFechaLiquidacion()));
			cell.setStyle("font-size: 11px !important");
			if (ventas.getFormaPago().getId().intValue()==Constantes.ID_FORPAG_CORTESIA)
				cell.setStyle("color: #FA5858; font-size: 11px !important");
			item.appendChild(cell);

			cell=new Listcell(ventas.getTipoMovimiento().getDenominacion());
			if (ventas.getFormaPago().getId().intValue()==Constantes.ID_FORPAG_CORTESIA)
				cell.setStyle("color: #FA5858");
			item.appendChild(cell);

			cell=new Listcell(ventas.getUsuario().getApellidoPaterno()+" "+ventas.getUsuario().getApellidoMaterno()+", "+ventas.getUsuario().getNombre());
			if (ventas.getFormaPago().getId().intValue()==Constantes.ID_FORPAG_CORTESIA)
				cell.setStyle("color: #FA5858");
			item.appendChild(cell);

			cell=new Listcell(ventas.getCliente().getNumeroDocumento());
			cell.setStyle("font-size: 11px !important");
			if (ventas.getFormaPago().getId().intValue()==Constantes.ID_FORPAG_CORTESIA)
				cell.setStyle("color: #FA5858");
			item.appendChild(cell);

			cell=new Listcell(ventas.getCliente().getRazonSocial());
			if (ventas.getFormaPago().getId().intValue()==Constantes.ID_FORPAG_CORTESIA)
				cell.setStyle("color: #FA5858");
			item.appendChild(cell);

			if(ventas.getFormaPago().getId().intValue()==Constantes.ID_FORPAG_CORTESIA)
				viajesNOValidos++;

			item.setValue(ventas);
			listPasajesAcumulados.appendChild(item);
		}

		chkPasajeroFrecuente.setDisabled(true);

		int viajesValidos=list.size()-viajesNOValidos;

		Listitem listitem=listboxLista.getItemAtIndex(listboxLista.getSelectedIndex());
		PasajeroFrecuente frecuente =  ServiceLocator.getPasajeroFrecuenteManager().buscarPaxfreeXNumeroDocumento(((Pasajero)listitem.getValue()).getNumeroDocumento());//((Pasajero)listitem.getValue()).getPasajeroFrecuente();
		if (frecuente!=null || viajesValidos>=Constantes.NUMERO_VIAJES_PAXFREE)
			chkPasajeroFrecuente.setDisabled(false);

		if(lblFechaActivacion.getValue()!=null && (!lblFechaActivacion.getValue().isEmpty())){
			if(pasajero.getPasajeroFrecuente()!=null){
				if(pasajero.getPasajeroFrecuente().getEstado().intValue()==Constantes.TRUE_VALUE){
					lbViajesAcumulados.setValue("Viajes acumulados en los �ltimos "+Constantes.TIEMPO_PASAR_PAXFREE / 30+" meses (del "
							+ Util.DatetoString(Constantes.FORMAT_DATE_TIME_24H.parse(lblFechaActivacion.getValue()), Constantes.DATE_FORMAT) +" al "
							+ Util.DatetoString(Constantes.FORMAT_DATE_TIME_24H.parse(lblFechaCaducidad.getValue()), Constantes.DATE_FORMAT)+" )");
				}else{
					lbViajesAcumulados.setValue("Viajes acumulados en los �ltimos "+Constantes.TIEMPO_PASAR_PAXFREE / 30+" meses (del "
							+ Util.DatetoString(Constantes.FORMAT_DATE_TIME_24H.parse(lblFechaSuspension.getValue()), Constantes.DATE_FORMAT) +" al "
							+ (fechaFinal.length()>=10?fechaFinal.substring(0,10):fechaFinal)+" )");
				}
			}
		}else{
			lbViajesAcumulados.setValue("Viajes acumulados en los �ltimos "+Constantes.TIEMPO_PASAR_PAXFREE / 30+" meses");
		}


		lbtotalViajes.setValue("Total viajes ==========> "+String.valueOf(list.size()));
		lbtotalViajesValidos.setValue("Total viajes v�lidos ====> "+Integer.toString(viajesValidos));
	}

	/**
	 * Obtiene la fecha de caducidad apara el PaxFree
	 * @return
	 * @throws Exception
	 */
	private String fechaCaducidad() throws Exception{
		MyTime time= new MyTime();
		Date mtime= Constantes.FORMAT_DATE_TIME_24H.parse(time.dateServer());

		long milSec1=Constantes.MILISEGUNDOS_X_DIA*Constantes.TIEMPO_PASAR_PAXFREE;
		Long milSec2=mtime.getTime();
		Long milResul=milSec1+milSec2;

		Calendar calendar= Calendar.getInstance();
		calendar.setTimeInMillis(milResul);

		Date dfecha=calendar.getTime();
		String fecha=Constantes.FORMAT_DATE_TIME_24H.format(dfecha);

		return fecha;
	}

	private String  numTarjetaPaxFree() throws Exception {
		String numeroTarjeta="";

		PasajeroFrecuente frecuente=ServiceLocator.getPasajeroFrecuenteManager().buscarMaxNumTarjeta();
		if(frecuente !=null){
			String gr1="0000"+""+getAgencia().getId().toString();
			String gr2="00000"+""+getUsuario().getId().toString();
			String gr3="000000"+""+frecuente.getNumeroTarjeta();

			numeroTarjeta=gr1.substring(gr1.length()-4,gr1.length())+"-"+gr2.substring(gr2.length()-5,gr2.length())+"-"+gr3.substring(gr3.length()-6,gr3.length());


//			numeroTarjeta=getAgencia().getId()+"-"+getUsuario().getId()+"-"+frecuente.getNumeroTarjeta();
		}

		return numeroTarjeta;
	}

	@SuppressWarnings("deprecation")
	private Window onCreateWindowsHistory(String idPasajero){
		String[][]col = new String[][]{{"COMPROBANTE","100px"},{"COMP.ANT","100px"},{"RAZON SOCIAL","160px"},{"SERVICIO","80px"},
				{"ORIGEN","80px"},{"DESTINO","80px"},{"F.PARTIDA","80px"},{"F.PAGO","80px"},{"ASI.","50px"},{"IMPORTE","70px"},
				{"F.VENTA","80px"},{"MOVIMIENTO","80px"},{"USUARIO","160px"}};

		Window window = null;
		try{
			List<VentaPasaje> lstVentas = ServiceLocator.getVentaPasajesManager().buscarVentasByPasajero(Long.valueOf(idPasajero));

			Caption caption = null;
			Listbox listbox = null;
			Listhead listhead = null;
			Listheader listheader = null;
			Listitem listitem = null;
			Listcell listcell = null;
			window = new Window("", "normal", true);
			window.setWidth("900px");
			caption = new Caption("HISTORIAL DE VIAJES");
			window.appendChild(caption);
			listbox = new Listbox();
			listbox.setWidth("880px");
			listbox.setHeight("300px");
			listhead = new Listhead();
			for(int i=0; i<col.length; i++){
				listheader = new Listheader();
				listheader.setLabel(col[i][0]);
				listheader.setWidth(col[i][1]);
				listheader.setStyle("color: #ffffff;");
				if(i==6 || i==8 || i==10)
					listheader.setAlign("center");
				else if(i==9)
					listheader.setAlign("right");
				listhead.appendChild(listheader);
			}
			listbox.appendChild(listhead);

			for(VentaPasaje venta : lstVentas){
				listitem = new Listitem();
				listcell = new Listcell(venta.getNumeroBoleto());
				listitem.appendChild(listcell);
				listcell = new Listcell(venta.getNumeroBoletoAnterior());
				listitem.appendChild(listcell);
				listcell = new Listcell(venta.getCliente()==null?"":venta.getCliente().getRazonSocial());
				listitem.appendChild(listcell);
				listcell = new Listcell(venta.getServicio().getDenominacion());
				listitem.appendChild(listcell);
				listcell = new Listcell(venta.getRuta().getOrigen());
				listitem.appendChild(listcell);
				listcell = new Listcell(venta.getRuta().getDestino());
				listitem.appendChild(listcell);
				listcell = new Listcell(venta.getFechaPartida()==null?"":Util.DatetoString(venta.getFechaPartida(), Constantes.DATE_FORMAT));
				listitem.appendChild(listcell);
				listcell = new Listcell(venta.getTipoFormaPago().getDenominacion());
				listitem.appendChild(listcell);
				listcell = new Listcell(venta.getNumeroAsiento()==null?"":venta.getNumeroAsiento().toString());
				listitem.appendChild(listcell);
				listcell = new Listcell(Util.toNumberFormat(venta.getImportePagado(), 2));
				listitem.appendChild(listcell);
				listcell = new Listcell(Util.DatetoString(venta.getFechaInsercion(), Constantes.DATE_FORMAT));
				listitem.appendChild(listcell);
				listcell = new Listcell(venta.getTipoMovimiento().getDenominacion());
				listitem.appendChild(listcell);
				listcell = new Listcell(venta.getUsuario().toString());
				listitem.appendChild(listcell);
				listbox.appendChild(listitem);
			}
			window.appendChild(listbox);
			Div div = new Div();
			div.setAlign("center");
			Button button = new Button("Cerrar", "resources/mp_cerrarEnabled.png");
			button.setClass("btnCommandL");
			button.addEventListener(Events.ON_CLICK, new EventListener<Event>() {
				@Override
				public void onEvent(Event e){
					wndHistory.onClose();
				}
			});
//			button.setHeight("27px");
			div.appendChild(button);
			window.appendChild(div);
//			window.appendChild(button);
		}catch(Exception ex){
			ex.printStackTrace();
		}
		return window;
	}

	/**
	 * Envia correo con la activacion/inactivacion del PaxFree;
	 * @throws Exception
	 */
	public void enviarMail(boolean isActivo, PasajeroFrecuente pasajeroFrecuente) throws Exception{
//		String asunto="";
//		if(isActivo)
//			asunto="Activaci�n de pasajero frecuente";
//		else asunto="Inactivaci�n de Pasajero Frecuente";
//
//		String mensaje="[Nota: este mensaje ha sido generado automaticamente, no responda por favor]\n\n";
//		mensaje+=tabular(7)+pasajeroFrecuente.getPasajero().getTipoDocumento().getDenominacion()+" :"+pasajeroFrecuente.getPasajero().getNumeroDocumento()+"\n";
//		mensaje+=tabular(2)+"Pasajero :"+pasajeroFrecuente.getPasajero().getNombresApellidos()+"\n";
//		if(isActivo){
//			mensaje+=tabular(2)+"Fecha activaci�n :"+Constantes.FORMAT_LONGSS.format(pasajeroFrecuente.getFechaActivacion())+"\n";
//			mensaje+=tabular(2)+"Fecha caducidad :"+Constantes.FORMAT_LONGSS.format(pasajeroFrecuente.getFechaCaducidad())+"\n";
//			mensaje+=tabular(2)+"Fecha reactivaci�n :"+new MyTime().dateServer()+"\n";
//			mensaje+=tabular(2)+"Cantidad viajes  :"+listPasajesAcumulados.getItems().size()+"\n";
//		}else
//			mensaje+=tabular(2)+"Fecha inactivaci�n :"+new MyTime().dateServer()+"\n";
//		mensaje+=tabular(3)+"Usuario :"+getUsuario().getNombre()+" "+getUsuario().getApellidoPaterno();
//
//		DestinatariosEmails emails= new DestinatariosEmails();
//		emails.setEmails("TO:jabanto@tepsa.com.pe");
//
//		Sendmail.enviaEmail(mensaje, asunto, emails);
	}


	/**
	 * Devuelve una cadena de espacios en blanco.
	 * @param espacios	: Numero de espacios en blanco.
	 * @return String
	 */
//	private static final String tabular(int espacios){
//		String cadena = "";
//		for(int i=0; i<espacios; i++){
//			cadena = cadena + " ";
//		}
//		return cadena;
//	}

}