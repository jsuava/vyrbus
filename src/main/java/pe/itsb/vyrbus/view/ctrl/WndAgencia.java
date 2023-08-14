/**
 * Proyecto		: SISVYR
 * Sistema		: Sistema de Ventas y Reservas
 * Descripción	:
 * Autor		: jM
 * Fecha		: 02/05/2012
 */
package pe.itsb.vyrbus.view.ctrl;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zul.Button;
import org.zkoss.zul.Checkbox;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Textbox;

import pe.itsb.vyrbus.model.bean.Agencia;
import pe.itsb.vyrbus.model.bean.Concesionario;
import pe.itsb.vyrbus.model.bean.Localidad;
import pe.itsb.vyrbus.model.bean.TipoAgencia;
import pe.itsb.vyrbus.model.bean.Ubigeo;
import pe.itsb.vyrbus.service.exceptions.CancelaGrabacionException;
import pe.itsb.vyrbus.service.exceptions.ConcesionarioNullException;
import pe.itsb.vyrbus.service.exceptions.DenominacionDuplicadaException;
import pe.itsb.vyrbus.service.exceptions.DenominacionNullException;
import pe.itsb.vyrbus.service.exceptions.LocalidadNullException;
import pe.itsb.vyrbus.service.exceptions.NombreCortoDuplicadoException;
import pe.itsb.vyrbus.service.exceptions.NombreCortoNullException;
import pe.itsb.vyrbus.service.exceptions.TipoAgenciaNullException;
import pe.itsb.vyrbus.service.exceptions.UbigeoNullException;
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
 * @author jM
 * @since JDK1.6
 */
public class WndAgencia extends WndOpcionesMantenimiento {

	private static final long serialVersionUID = 7015174319519567765L;

	private Combobox cboTipoAgencia;
	private Combobox cboLocalidad;
	private Combobox cboConcesionario;
	private Textbox txtIdUbigeo;
	private Textbox txtUbicacionGeografica;
	private Textbox txtDenominacion;
	private Textbox txtNombreCorto;
	private Checkbox chkMarcarTerminal;
	private Button btnUbicacionGeografica;
	private Textbox txtDireccion;
	private Textbox txtCodigo;

	private Agencia oAgencia=null;
	//private Usuario usuario= new Usuario();

	private TreeMap<String, Object> criteriosBusqueda = new TreeMap<>();
	private List<String> criteriosOrdenar = null;

	@Override
	public void onCreate() throws Exception {
		//	usuario= (Usuario) getDesktop().getSession().getAttribute(Constantes.ATRIBUTO_USUARIO);
		/*********************************************************************/



//		ServiceLocator.getAgenciaManager().guardar(getAgencia());

		UtilData.cargarDataCombo(cboTipoAgencia, TipoAgencia.class, false);
		UtilData.cargarDataCombo(cboLocalidad, Localidad.class, false);
		UtilData.cargarDataCombo(cboConcesionario, Concesionario.class, false);
		UtilData.enlazarUbigeo(txtIdUbigeo, txtUbicacionGeografica, btnUbicacionGeografica,null);
		criteriosOrdenar = new ArrayList<>();
		criteriosOrdenar.add("denominacion");
	}


	@Override
	public void initComponents() {
		cboTipoAgencia = (Combobox) getFellow("cboTipoAgencia");
		cboLocalidad = (Combobox) getFellow("cboLocalidad");
		cboConcesionario = (Combobox) getFellow("cboConcesionario");
		txtIdUbigeo = (Textbox) getFellow("txtIdUbigeo");
		txtUbicacionGeografica = (Textbox) getFellow("txtUbicacionGeografica");
		txtDenominacion = (Textbox) getFellow("txtDenominacion");
		txtNombreCorto = (Textbox) getFellow("txtNombreCorto");
		chkMarcarTerminal = (Checkbox) getFellow("chkMarcarTerminal");
		btnUbicacionGeografica = (Button) getFellow("btnUbicacionGeografica");
		txtDireccion = (Textbox) this.getFellow("txtDireccion");
		txtCodigo = (Textbox) this.getFellow("txtCodigo");
	}

	@Override
	public void onNew() {
		cboTipoAgencia.setSelectedIndex(0);
		cboLocalidad.setSelectedIndex(0);
		cboConcesionario.setSelectedIndex(0);
	}

	@Override
	public void onSearch() {
		final WndFiltrarParametros oWndFiltrar = new WndFiltrarParametros();

		oWndFiltrar.addParameter("1. Tipo de Agencia", TipoAgencia.class);
		oWndFiltrar.addParameter("2. Localidad", Localidad.class);
		oWndFiltrar.addParameter("3. Concesionario", Concesionario.class);
		oWndFiltrar.addParameter("4. Denominación", String.class);
		oWndFiltrar.addParameter("5. Nombre Corto (o sufijo)", String.class);

		this.appendChild(oWndFiltrar);
		oWndFiltrar.setMode("modal");
		oWndFiltrar.addEventListener(pe.itsb.vyrbus.view.ui.Events.ON_FILTER, new EventListener<Event>() {
			@Override
			public void onEvent(Event event) throws Exception {
				criteriosBusqueda = new TreeMap<>();

				Integer tipoAgencia = (Integer) oWndFiltrar.getParameterValue("1. Tipo de Agencia");
				Integer localidad = (Integer) oWndFiltrar.getParameterValue("2. Localidad");
				Integer concesionario = (Integer) oWndFiltrar.getParameterValue("3. Concesionario");
				String denominacion = oWndFiltrar.getParameterValue("4. Denominación").toString().trim();
				String nombreCorto = oWndFiltrar.getParameterValue("5. Nombre Corto (o sufijo)").toString().trim();
				String estadoRegistro = Constantes.VALUE_ACTIVO;

				if (denominacion.trim().equals("")) {
					criteriosBusqueda.remove("denominacion");
				}else{criteriosBusqueda.put("denominacion", "%" + denominacion + "%");}

				if(nombreCorto.trim().equals("")) {
					criteriosBusqueda.remove("nombreCorto");
				}else{criteriosBusqueda.put("nombreCorto", "%" + nombreCorto + "%");}

				if (tipoAgencia != null){
					TipoAgencia otipoAgencia = new TipoAgencia();
					otipoAgencia.setId(tipoAgencia);
					criteriosBusqueda.put("tipoAgencia", otipoAgencia);
				}else{criteriosBusqueda.remove("tipoAgencia");}

				if (localidad != null){
					Localidad olocaLocalidad = new Localidad();
					olocaLocalidad.setId(localidad);
					criteriosBusqueda.put("localidad", olocaLocalidad);
				}else{criteriosBusqueda.remove("localidad");}

				if (concesionario != null){
					Concesionario oconcesionario = new Concesionario();
					oconcesionario.setId(concesionario);
					criteriosBusqueda.put("concesionario", oconcesionario);
				}else{criteriosBusqueda.remove("concesionario");}

				criteriosBusqueda.put("estadoRegistro", estadoRegistro);
				listarRegistros(ServiceLocator.getAgenciaManager().buscarPorX(criteriosBusqueda, criteriosOrdenar));
			}
		});
	}

	@Override
	public void onRefresh(int tab) {
		try{
			if (!criteriosBusqueda.isEmpty()) {
				this.listarRegistros(ServiceLocator.getAgenciaManager().buscarPorX(criteriosBusqueda, criteriosOrdenar));
			}
		}catch(Exception ex){
			ex.printStackTrace();
		}
	}

	@Override
	public void onModify(int tab) throws Exception{
		Long id = new Long(0);
		id = new Long((String) listboxLista.getSelectedItem().getValue());
		this.mantenimientoRegistro(id);
	}

	@Override
	public void onCancel(int action) throws Exception{
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
		try {
			if (!(cboTipoAgencia.getSelectedItem().getValue() instanceof TipoAgencia))
				throw new TipoAgenciaNullException();
			else if (!(cboLocalidad.getSelectedItem().getValue() instanceof Localidad))
				throw new LocalidadNullException();
			else if (!(cboConcesionario.getSelectedItem().getValue() instanceof Concesionario))
				throw new ConcesionarioNullException();
			else if (txtUbicacionGeografica.getText().trim().equals(""))
				throw new UbigeoNullException();
			else if (txtDenominacion.getText().trim().equals(""))
				throw new DenominacionNullException();
			else if (txtNombreCorto.getText().trim().equals(""))
				throw new NombreCortoNullException();
			else if (txtCodigo.getText().trim().isEmpty()){
				DlgMessage.information(Messages.getString("WndAgencia.information.noCodigo"),txtCodigo);
				return;
			}

			if(action==ACTION_NEW)
				 oAgencia = new Agencia();

			Integer id = (textboxId.getText().equals("") ? 0 : new Integer(textboxId.getText()));
			oAgencia.setId(id);
			if (cboTipoAgencia.getSelectedIndex() > -1) {
				TipoAgencia oTipoAgencia = new TipoAgencia();
				oTipoAgencia.setId(((TipoAgencia) cboTipoAgencia.getSelectedItem().getValue()).getId());
				oAgencia.setTipoAgencia(oTipoAgencia);
			}
			if (cboLocalidad.getSelectedIndex() > -1) {
				Localidad oLocalidad = new Localidad();
				oLocalidad.setId(((Localidad) cboLocalidad.getSelectedItem().getValue()).getId());
				oAgencia.setLocalidad(oLocalidad);
			}
			if (cboConcesionario.getSelectedIndex() > -1) {
				Concesionario oConcesionario = new Concesionario();
				oConcesionario.setId(((Concesionario) cboConcesionario.getSelectedItem().getValue()).getId());
				oAgencia.setConcesionario(oConcesionario);
			}
			if (txtIdUbigeo.getText().trim().length() > 0) {
				Ubigeo oUbigeo = new Ubigeo();
				oUbigeo.setId(txtIdUbigeo.getText());
				oAgencia.setUbigeo(oUbigeo);
			}
			oAgencia.setDenominacion(txtDenominacion.getText().toUpperCase());
			oAgencia.setNombreCorto(txtNombreCorto.getText().toUpperCase());
			oAgencia.setDireccion(txtDireccion.getText().toUpperCase());
			oAgencia.setCodigo(txtCodigo.getText().toUpperCase());
			oAgencia.setEsTerminal(chkMarcarTerminal.isChecked());
			oAgencia.setEstadoRegistro(Constantes.VALUE_ACTIVO);
			switch (action) {
				case ACTION_NEW:
					UtilData.auditarRegistro(oAgencia, getUsuario(), Executions.getCurrent());
					ServiceLocator.getAgenciaManager().guardar(oAgencia);
					textboxId.setText((new Long(oAgencia.getId()).toString()));
					break;

				case ACTION_MODIFY:
					UtilData.auditarRegistro(oAgencia, true, getUsuario(), Executions.getCurrent());
					ServiceLocator.getAgenciaManager().actualizar(oAgencia);
					break;
			}
			/*RECUPERA EL REGISTRO ACTUALIZADO O EL NUEVO*/
			criteriosBusqueda = new TreeMap<>();
			criteriosBusqueda.put("id", oAgencia.getId());
			listarRegistros(ServiceLocator.getAgenciaManager().buscarPorX(criteriosBusqueda, criteriosOrdenar));

		}catch (TipoAgenciaNullException tanex){
			DlgMessage.information(Messages.getString("WndAgencia.information.noSeleccionoTipoAgencia"),cboTipoAgencia);
			throw new CancelaGrabacionException();
		}catch (LocalidadNullException lnex){
			DlgMessage.information(Messages.getString("WndAgencia.information.noSeleccionoLocalidad"),cboLocalidad);
			throw new CancelaGrabacionException();
		}catch (ConcesionarioNullException cnex){
			DlgMessage.information(Messages.getString("WndAgencia.information.noSeleccionoConcesionario"),cboConcesionario);
			throw new CancelaGrabacionException();
		}catch (UbigeoNullException unex){
			DlgMessage.information(Messages.getString("WndAgencia.information.noIngresoUbigeo"),btnUbicacionGeografica);
			throw new CancelaGrabacionException();
		}catch (DenominacionNullException dnex){
			DlgMessage.information(Messages.getString("Generales.information.noIngresoDenominacion"),txtDenominacion);
			throw new CancelaGrabacionException();
		}catch (NombreCortoNullException ncnex){
			DlgMessage.information(Messages.getString("Generales.information.noIngresoNombreCorto"),txtNombreCorto);
			throw new CancelaGrabacionException();
		}catch (DenominacionDuplicadaException ddex){
			DlgMessage.information(Messages.getString("Generales.information.denominacionDuplicada"),txtDenominacion);
			throw new CancelaGrabacionException();
		}catch (NombreCortoDuplicadoException ncdex){
			DlgMessage.information(Messages.getString("Generales.information.nombreCortoDuplicado"),txtNombreCorto);
			throw new CancelaGrabacionException();
		}catch (Exception ex){
			DlgMessage.error(this.getClass().getName()+" "+ex.getMessage());
			ex.printStackTrace();throw new CancelaGrabacionException();
		}

	}

	@Override
	public void onDelete(int tab) throws Exception{
		Long id = (long) 0;

		switch (tab) {
			case TAB_LIST:
				id = new Long((String) listboxLista.getSelectedItem().getValue());
				break;

			case TAB_MAINTENANCE:
				id = new Long(textboxId.getText());
				break;
		}

		ServiceLocator.getAgenciaManager().inactivar(id);
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
	public void onChangeTab(int tab) throws Exception{
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

	public void listarRegistros(ArrayList<Agencia> lstRegistro) throws Exception{
		ArrayList<Object> lstAgencias = new ArrayList<>();

		for(int r = 0; r < lstRegistro.size(); r ++) {
			Agencia oAgencia = lstRegistro.get(r);
			ArrayList<Object> lstFila = new ArrayList<>();
			if (oAgencia.getTipoAgencia().getDenominacion()==null){
				TipoAgencia tipoAgencia = new TipoAgencia();
				tipoAgencia= ServiceLocator.getTipoAgenciaManager().buscarPorId(oAgencia.getTipoAgencia().getId().longValue());
				oAgencia.setTipoAgencia(tipoAgencia);
			}
			if (oAgencia.getLocalidad().getDenominacion()==null){
				Localidad localidad= new Localidad();
				localidad = ServiceLocator.getLocalidadManager().buscarPorId(oAgencia.getLocalidad().getId().longValue());
				oAgencia.setLocalidad(localidad);
			}

			if(oAgencia.getConcesionario()!=null){
				if (oAgencia.getConcesionario().getRazonSocial()==null){
					Concesionario concesionario = new Concesionario();
					concesionario= ServiceLocator.getConcesionarioManager().buscarPorId(oAgencia.getConcesionario().getId().longValue());
					oAgencia.setConcesionario(concesionario);
				}
			}


			Ubigeo ubigeo = new Ubigeo();
			ubigeo = ServiceLocator.getUbigeoManager().buscarPorId(oAgencia.getUbigeo().getId());
			oAgencia.setUbigeo(ubigeo);


			lstFila.add(oAgencia.getId());//idAgencia
			lstFila.add(r + 1);//Item
			lstFila.add(oAgencia.getTipoAgencia().getDenominacion());
			lstFila.add(oAgencia.getLocalidad().getDenominacion());
			lstFila.add(oAgencia.getConcesionario()!=null?oAgencia.getConcesionario().getRazonSocial():"");
			lstFila.add(ServiceLocator.getUbigeoManager().ubicacionGeografica(oAgencia.getUbigeo()));
			lstFila.add(oAgencia.getDenominacion());
			lstFila.add(oAgencia.getNombreCorto());
			lstFila.add((oAgencia.getEsTerminal() ? "Sí" : "No"));

			lstAgencias.add(lstFila);
		}

		Util.llenarListbox(listboxLista, lstAgencias, true);
	}

	private void mantenimientoRegistro(Long id) throws Exception {
		oAgencia = ServiceLocator.getAgenciaManager().buscarPorId(id);
		Ubigeo oUbigeo = oAgencia.getUbigeo();
		String idUbigeo = "", ubicacionCompleta = "";
		TipoAgencia oTipoAgencia = oAgencia.getTipoAgencia();
		Localidad oLocalidad = oAgencia.getLocalidad();
		Concesionario oConcesionario = oAgencia.getConcesionario();

		textboxId.setText(oAgencia.getId().toString());



		if (oTipoAgencia != null) {
			Util.seleccionarValorItemCombo(TipoAgencia.class, cboTipoAgencia, oTipoAgencia.getId());
		}else
			cboTipoAgencia.setSelectedIndex(0);

		if (oLocalidad != null) {
			Util.seleccionarValorItemCombo(Localidad.class, cboLocalidad, oLocalidad.getId());
		}else
			cboLocalidad.setSelectedIndex(0);

		if (oConcesionario != null) {
			Util.seleccionarValorItemCombo(Concesionario.class, cboConcesionario, oConcesionario.getId());
		}else
			cboConcesionario.setSelectedIndex(0);

		if (oUbigeo != null) {
			idUbigeo = oUbigeo.getId();
			ubicacionCompleta = ServiceLocator.getUbigeoManager().ubicacionGeografica(oUbigeo);
		}

		txtIdUbigeo.setText(idUbigeo);
		txtUbicacionGeografica.setText(ubicacionCompleta);

		txtDenominacion.setText(oAgencia.getDenominacion());
		txtNombreCorto.setText(oAgencia.getNombreCorto());
		txtDireccion.setText(oAgencia.getDireccion()!=null?oAgencia.getDireccion():"");
		txtCodigo.setText(oAgencia.getCodigo()!=null?oAgencia.getCodigo():"");
		chkMarcarTerminal.setChecked(oAgencia.getEsTerminal());
	}
}