/**
 * Proyecto		: SISVYR
 * Sistema		: Sistema de Ventas y Reservas
 * Descripci�n	:
 * Autor		: jM
 * Fecha		: 30/04/2012
 */
package pe.itsb.vyrbus.view.ctrl;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zul.Checkbox;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Groupbox;
import org.zkoss.zul.Spinner;
import org.zkoss.zul.Textbox;

import pe.itsb.vyrbus.model.bean.Empresa;
import pe.itsb.vyrbus.model.bean.Servicio;
import pe.itsb.vyrbus.service.exceptions.CancelaGrabacionException;
import pe.itsb.vyrbus.service.exceptions.DenominacionDuplicadaException;
import pe.itsb.vyrbus.service.exceptions.DenominacionNullException;
import pe.itsb.vyrbus.service.exceptions.EmpresaException;
import pe.itsb.vyrbus.service.exceptions.NombreCortoDuplicadoException;
import pe.itsb.vyrbus.service.exceptions.NombreCortoNullException;
import pe.itsb.vyrbus.service.exceptions.NumeroAsientoNullException;
import pe.itsb.vyrbus.service.exceptions.NumeroColumnasNullException;
import pe.itsb.vyrbus.service.exceptions.NumeroFilasNullException;
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
public class WndServicio extends WndOpcionesMantenimiento {

	private static final long serialVersionUID = 1998913859947825458L;

/*	private Textbox txtDenominacion;
	private Textbox txtNombreCorto;
	private Intbox itPisos;
	private Intbox itAsientos;
	private Intbox itFilas;
	private Intbox itColumnas;
	private Intbox itFilas2;
	private Intbox itColumnas2;
	*/

	private Textbox txtDenominacion;
	private Textbox txtNombreCorto;
	private Checkbox chkBusDosPisos;
	private Spinner spAsientos;
	private Spinner spFilas;
	private Spinner spColumnas;
	private Spinner spAsientos2;
	private Spinner spFilas2;
	private Spinner spColumnas2;
	private Groupbox grpSegundoPiso;
	private Combobox cmbEmpresa;


	private Servicio oServicio = null;

	private TreeMap<String, Object> criteriosBusqueda = new TreeMap<>();
	private List<String> criteriosOrdenar = null;

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.window.ui.IBase#onCreate()
	 */
	@Override
	public void onCreate() throws Exception {
		criteriosOrdenar = new ArrayList<>();
		criteriosOrdenar.add("denominacion");
		UtilData.cargarDataCombo(cmbEmpresa, Empresa.class, false);
	}

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.window.ui.IBase#initComponents()
	 */
	@Override
	public void initComponents() {
/*		txtDenominacion = (Textbox) getFellow("txtDenominacion");
		txtNombreCorto = (Textbox) getFellow("txtNombreCorto");
		itPisos = (Intbox) getFellow("itPisos");
		itAsientos = (Intbox) getFellow("itAsientos");
		itFilas = (Intbox) getFellow("itFilas");
		itColumnas = (Intbox) getFellow("itColumnas");
		itFilas2 = (Intbox) getFellow("itFilas2");
		itColumnas2 = (Intbox) getFellow("itColumnas2");
		*/

		txtDenominacion = (Textbox) getFellow("txtDenominacion");
		txtNombreCorto = (Textbox) getFellow("txtNombreCorto");
		chkBusDosPisos = (Checkbox) getFellow("chkBusDosPisos");
		spAsientos = (Spinner) getFellow("spAsientos");
		spFilas = (Spinner) getFellow("spFilas");
		spColumnas = (Spinner) getFellow("spColumnas");
		spAsientos2 = (Spinner) getFellow("spAsientos2");
		spFilas2 = (Spinner) getFellow("spFilas2");
		spColumnas2 = (Spinner) getFellow("spColumnas2");
		grpSegundoPiso = (Groupbox)this.getFellow("grpSegundoPiso");
		cmbEmpresa = (Combobox)this.getFellow("cmbEmpresa");

	}

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.window.ui.IOpcionesMantenimiento#onNew()
	 */
	@Override
	public void onNew() {
		cmbEmpresa.setSelectedIndex(0);
		habilitarSegundoPiso(false);
	}

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.window.ui.IOpcionesMantenimiento#onSearch()
	 */
	@Override
	public void onSearch() throws Exception {
		final WndFiltrarParametros oWndFiltrar = new WndFiltrarParametros();

		oWndFiltrar.addParameter("1. Empresa", Empresa.class);
		oWndFiltrar.addParameter("2. Denominación", String.class);
		oWndFiltrar.addParameter("3. Código", String.class);
		oWndFiltrar.addParameter("4. Nombre corto", String.class);

		this.appendChild(oWndFiltrar);
		oWndFiltrar.setMode("modal");
		oWndFiltrar.addEventListener(pe.itsb.vyrbus.view.ui.Events.ON_FILTER, new EventListener<Event>() {

			@Override
			public void onEvent(Event event) throws Exception {
				Empresa empresa = (Empresa)oWndFiltrar.getParameterValue("1. Empresa");
				String denominacion = (String) oWndFiltrar.getParameterValue("2. Denominación");
				String codigo = (String) oWndFiltrar.getParameterValue("3. Código");
				String nombreCorto = (String) oWndFiltrar.getParameterValue("4. Nombre corto");
				String estadoRegistro = Constantes.VALUE_ACTIVO;

				if(empresa == null)
					criteriosBusqueda.remove("empresa");
				else {
					criteriosBusqueda.put("empresa", empresa);
				}
				
				if (denominacion.trim().equals("")) {
					criteriosBusqueda.remove("denominacion");
				}else {
					criteriosBusqueda.put("denominacion", "%" + denominacion + "%");
				}

				if (codigo.trim().equals("")) {
					criteriosBusqueda.remove("codigo");
				}else {criteriosBusqueda.put("codigo", "%" + codigo + "%");}

				if (nombreCorto.trim().equals("")) {
					criteriosBusqueda.remove("nombreCorto");
				}else {criteriosBusqueda.put("nombreCorto", "%" + nombreCorto + "%");}

				criteriosBusqueda.put("estadoRegistro", estadoRegistro);
				listarRegistros(ServiceLocator.getServicioManager().buscarPorX(criteriosBusqueda, criteriosOrdenar));
			}
		});
	}

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.window.ui.IOpcionesMantenimiento#onRefresh(int)
	 */
	@Override
	public void onRefresh(int tab) throws Exception {
		if (!criteriosBusqueda.isEmpty()) {
			this.listarRegistros(ServiceLocator.getServicioManager().buscarPorX(criteriosBusqueda, criteriosOrdenar));
		}
	}

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.window.ui.IOpcionesMantenimiento#onModify()
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
	public void onSave(int action) throws Exception{
		try{
			if (!(cmbEmpresa.getSelectedItem().getValue() instanceof Empresa))
				throw new EmpresaException();
			else if (txtDenominacion.getText().trim().equals(""))
				throw new DenominacionNullException();
			else if (txtNombreCorto.getText().trim().equals(""))
				throw new NombreCortoNullException();
			else if (spAsientos.getValue().intValue()<=0)
				throw new NumeroAsientoNullException(1);
			else if (spFilas.getValue().intValue()==0)
				throw new NumeroFilasNullException(1);
			else if (spColumnas.getValue().intValue()==0)
				throw new NumeroColumnasNullException(1);
			else if(chkBusDosPisos.isChecked()){
				if (spAsientos2.getValue().intValue()<=0)
					throw new NumeroAsientoNullException(2);
				else if (spFilas2.getValue().intValue()==0)
					throw new NumeroFilasNullException(2);
				else if (spColumnas2.getValue().intValue()==0)
					throw new NumeroColumnasNullException(2);
			}

			if(action==ACTION_NEW)
				oServicio = new Servicio();
			
			Empresa oEmpresa = new Empresa();

			Integer id = (textboxId.getText().equals("") ? 0 : new Integer(textboxId.getText()));
			/*
			Integer Pisos = itPisos.getValue();
			Integer Asientos = itAsientos.getValue();
			Integer Filas = itFilas.getValue();
			Integer Columnas = itColumnas.getValue();
			Integer Filas2 = 0;
			Integer Columnas2 = 0;


			if(Pisos > 1){
				Filas2 = itFilas2.getValue();
				Columnas2 = itColumnas2.getValue();
			}
			*/
			oServicio.setId(id);
			oEmpresa.setId(((Empresa)cmbEmpresa.getSelectedItem().getValue()).getId());
			oEmpresa.setRazonSocial(cmbEmpresa.getText());
			oEmpresa.setNombreCorto(((Empresa)cmbEmpresa.getSelectedItem().getValue()).getNombreCorto());
			oServicio.setEmpresa(oEmpresa);
			oServicio.setDenominacion(txtDenominacion.getText().toUpperCase());
			oServicio.setNombreCorto(txtNombreCorto.getText().toUpperCase());
			oServicio.setNumeroPisos(chkBusDosPisos.isChecked()?2:1);
			oServicio.setNumeroAsientosPiso1(spAsientos.getValue());
			oServicio.setNumeroFilasPiso1(spFilas.getValue());
			oServicio.setNumeroColumnasPiso1(spColumnas.getValue());
			oServicio.setNumeroAsientosPiso2(spAsientos2.getValue()==0?null:spAsientos2.getValue());
			oServicio.setNumeroFilasPiso2(spFilas2.getValue()==0?null:spFilas2.getValue());
			oServicio.setNumeroColumnasPiso2(spColumnas2.getValue()==0?null:spColumnas2.getValue());

			/*
			if(Pisos > 1) {
				oServicio.setNumeroFilasPiso2(Filas2);
				oServicio.setNumeroColumnasPiso2(Columnas2);
			}
			*/
			oServicio.setEstadoRegistro(Constantes.VALUE_ACTIVO);

			switch (action) {
				case ACTION_NEW:
					UtilData.auditarRegistro(oServicio, getUsuario(), Executions.getCurrent());
					oServicio.setUsuarioInsercion(getUsuario().getLogin());
					ServiceLocator.getServicioManager().guardar(oServicio);
					textboxId.setText(oServicio.getId().toString());
					break;

				case ACTION_MODIFY:
					UtilData.auditarRegistro(oServicio, true, getUsuario(), Executions.getCurrent());
					oServicio.setUsuarioModificacion(getUsuario().getLogin());
					ServiceLocator.getServicioManager().actualizar(oServicio);
					break;
			}
			/*RECUPERA EL REGISTRO ACTUALIZADO O EL  NUEVO*/
			criteriosBusqueda.remove("denominacion");criteriosBusqueda.remove("codigo");criteriosBusqueda.remove("nombreCorto");
			criteriosBusqueda.put("denominacion", oServicio.getDenominacion());
			criteriosBusqueda.put("estadoRegistro", Constantes.VALUE_ACTIVO);
			this.listarRegistros(ServiceLocator.getServicioManager().buscarPorX(criteriosBusqueda, criteriosOrdenar));

		}catch(EmpresaException eex) {
			DlgMessage.information(Messages.getString("WndRuta.information.Empresa"),cmbEmpresa);
			throw new CancelaGrabacionException();
		}catch (DenominacionNullException dnex){
			DlgMessage.information(Messages.getString("Generales.information.noIngresoDenominacion"),txtDenominacion);
			throw new CancelaGrabacionException();
		}catch (NombreCortoNullException ncnex){
			DlgMessage.information(Messages.getString("Generales.information.noIngresoNombreCorto"),txtNombreCorto);
			throw new CancelaGrabacionException();
		}catch (NumeroAsientoNullException nanex){
			if(nanex.getNumeroPiso().intValue()==1)
				DlgMessage.information(Messages.getString("WndServicio.information.noIngresoNumeroAsientos")+" del Primer Piso");
			else
				DlgMessage.information(Messages.getString("WndServicio.information.noIngresoNumeroAsientos")+" del Segundo Piso",spAsientos);
			throw new CancelaGrabacionException();
		}catch (NumeroFilasNullException nfnex){
			if(nfnex.getNumeroPiso().intValue()==1)
				DlgMessage.information(Messages.getString("WndServicio.information.noIngresoNumeroFilas")+" del Primer Piso");
			else
				DlgMessage.information(Messages.getString("WndServicio.information.noIngresoNumeroFilas")+" del Segundo Piso",spFilas);
			throw new CancelaGrabacionException();
		}catch (NumeroColumnasNullException ncnex){
			if(ncnex.getNumeroPiso().intValue()==1)
				DlgMessage.information(Messages.getString("WndServicio.information.noIngresoCantidadColumnas")+" del Primer Piso");
			else
				DlgMessage.information(Messages.getString("WndServicio.information.noIngresoCantidadColumnas")+" del Segundo Piso", spColumnas);
			throw new CancelaGrabacionException();
		}catch (DenominacionDuplicadaException rsdex){
			DlgMessage.information(Messages.getString("Generales.information.denominacionDuplicada"),txtDenominacion);
			throw new CancelaGrabacionException();
		}catch(NombreCortoDuplicadoException rdnex){
			DlgMessage.information(Messages.getString("Generales.information.nombreCortoDuplicado"),txtNombreCorto);
			throw new CancelaGrabacionException();
		}catch(Exception ex){
			DlgMessage.error(this.getClass().getName()+" "+ex.getMessage());
			ex.printStackTrace();
			throw new CancelaGrabacionException();
		}

	}

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.window.ui.IOpcionesMantenimiento#onDelete()
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

		ServiceLocator.getServicioManager().inactivar(id);
	}

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.window.ui.IOpcionesMantenimiento#onPrint()
	 */
	@Override
	public void onPrint(int tab) {
		// TODO Auto-generated method stub
	}

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.window.ui.IOpcionesMantenimiento#onExport()
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
	 * @see com.tepsa.sisvyr.window.ui.IOpcionesMantenimiento#onClose()
	 */
	@Override
	public void onClose() {
		closeTabWindow();
	}

	private void listarRegistros(ArrayList<Servicio> lstRegistros) {
		ArrayList<Object> lstServicios = new ArrayList<>();

		for(int r = 0; r < lstRegistros.size(); r ++) {
			Servicio oServicio = lstRegistros.get(r);
			ArrayList<Object> lstFila = new ArrayList<>();

			lstFila.add(oServicio.getId());
			lstFila.add(r + 1);
			lstFila.add(oServicio.getEmpresa().getRazonSocial());
			lstFila.add(oServicio.getDenominacion());
			lstFila.add(oServicio.getNombreCorto());
			lstFila.add(oServicio.getNumeroPisos());
			lstFila.add(oServicio.getNumeroAsientosPiso1());
			lstFila.add(oServicio.getNumeroFilasPiso1());
			lstFila.add(oServicio.getNumeroColumnasPiso1());
			lstServicios.add(lstFila);
		}

		Util.llenarListbox(listboxLista, lstServicios, true);
	}


	private void mantenimientoRegistro(Long id) throws Exception {
		oServicio = ServiceLocator.getServicioManager().buscarPorId(id);
		//Servicio outilData = ServiceDelegate.instance().servicio_buscarPorId(id);

		Util.seleccionarValorItemCombo(Empresa.class, cmbEmpresa, oServicio.getEmpresa().getId());
		textboxId.setText(oServicio.getId().toString());
		txtDenominacion.setText(oServicio.getDenominacion());
		txtNombreCorto.setText(oServicio.getNombreCorto());
		if(oServicio.getNumeroPisos().intValue()==2){
			chkBusDosPisos.setChecked(true);
			spAsientos2.setValue(oServicio.getNumeroAsientosPiso2());
			spFilas2.setValue(oServicio.getNumeroFilasPiso2());
			spColumnas2.setValue(oServicio.getNumeroColumnasPiso2());
			grpSegundoPiso.setVisible(true);
		}else{
			chkBusDosPisos.setChecked(false);
			spAsientos2.setValue(0);
			spFilas2.setValue(0);
			spColumnas2.setValue(0);
			grpSegundoPiso.setVisible(false);
		}
		spAsientos.setValue(oServicio.getNumeroAsientosPiso1());
		spFilas.setValue(oServicio.getNumeroFilasPiso1());
		spColumnas.setValue(oServicio.getNumeroColumnasPiso1());
	}

	public void habilitarSegundoPiso(boolean habilitar) {
		grpSegundoPiso.setVisible(habilitar);
		spAsientos2.setDisabled(!habilitar);
		spFilas2.setDisabled(!habilitar);
		spColumnas2.setDisabled(!habilitar);
		spAsientos2.setValue(0);
		spFilas2.setValue(0);
		spColumnas2.setValue(0);
	}



}