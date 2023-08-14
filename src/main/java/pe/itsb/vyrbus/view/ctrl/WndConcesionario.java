/**
 * Proyecto		: SISVYR
 * Sistema		: Sistema de Ventas y Reservas
 * Descripción	:
 * Autor		: jM
 * Fecha		: 02/05/2012
 */
package pe.itsb.vyrbus.view.ctrl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.TreeMap;

import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zul.Checkbox;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.Intbox;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Textbox;

import pe.itsb.vyrbus.model.bean.Concesionario;
import pe.itsb.vyrbus.service.exceptions.CancelaGrabacionException;
import pe.itsb.vyrbus.service.exceptions.EliminacionRegistrosNullExcepcion;
import pe.itsb.vyrbus.service.exceptions.FechaActivacionNullException;
import pe.itsb.vyrbus.service.exceptions.NumeroDocumentoNullException;
import pe.itsb.vyrbus.service.exceptions.RazonSocialDuplicadoException;
import pe.itsb.vyrbus.service.exceptions.RazonSocialNullException;
import pe.itsb.vyrbus.service.exceptions.RucDuplicadoException;
import pe.itsb.vyrbus.service.exceptions.RucInvalidoException;
import pe.itsb.vyrbus.service.exceptions.TipoComisionException;
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
 * @author JABANTO
 * @since JDK1.6
 */
public class WndConcesionario extends WndOpcionesMantenimiento {

	private static final long serialVersionUID =1L;

	private Textbox txtRazonSocial;
	private Textbox txtRuc;
	private Textbox txtDireccion;
	private Datebox dbFechaActivacion;
	private Datebox dbFechaCaducidad;
	private Datebox dbFechaSuspension;
	private Intbox itbxComision;
	private Combobox cmbTipoComision;
	private Checkbox chbxIncluyeIgv;

	private Concesionario oConcesionario=null;

	private TreeMap<String, Object> criteriosBusqueda = new TreeMap<>();
	private List<String> criteriosOrdenar = null;

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.window.ui.IBase#onCreate()
	 */
	@Override
	public void onCreate() throws Exception {
		criteriosOrdenar = new ArrayList<>();
		criteriosOrdenar.add("razonSocial");
		UtilData.cargarTipoComsion(cmbTipoComision);
	}

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.window.ui.IBase#initComponents()
	 */
	@Override
	public void initComponents() {
		txtRazonSocial = (Textbox) getFellow("txtRazonSocial");
		txtRuc = (Textbox) getFellow("txtRuc");
		txtDireccion = (Textbox) getFellow("txtDireccion");
		dbFechaActivacion = (Datebox) getFellow("dbFechaActivacion");
		dbFechaCaducidad = (Datebox) getFellow("dbFechaCaducidad");
		dbFechaSuspension = (Datebox) getFellow("dbFechaSuspension");
		itbxComision=(Intbox)this.getFellow("itbxComision");
		cmbTipoComision=(Combobox)this.getFellow("cmbTipoComision");
		chbxIncluyeIgv=(Checkbox)this.getFellow("chbxIncluyeIgv");
	}

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.window.ui.IOpcionesMantenimiento#onNew()
	 */
	@Override
	public void onNew() {
		// TODO Auto-generated method stub
		dbFechaActivacion.setValue(new Date());
		chbxIncluyeIgv.setChecked(true);
		cmbTipoComision.setSelectedIndex(0);

	}
	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.window.ui.IOpcionesMantenimiento#onSearch()
	 */
	@Override
	public void onSearch() throws Exception {
		final WndFiltrarParametros oWndFiltrar = new WndFiltrarParametros();

		oWndFiltrar.addParameter("Razón social", String.class);
		oWndFiltrar.addParameter("Nº de Ruc", String.class);
		//oWndFiltrar.addParameter("Fecha de activación", Date.class);

		this.appendChild(oWndFiltrar);
		oWndFiltrar.setMode("modal");

		oWndFiltrar.addEventListener(pe.itsb.vyrbus.view.ui.Events.ON_FILTER, new EventListener<Event>() {

			@Override
			public void onEvent(Event event) throws Exception {
				String razonSocial = (String) oWndFiltrar.getParameterValue("Razón social");
				String ruc = (String) oWndFiltrar.getParameterValue("Nº de Ruc");
				String estadoRegistro = Constantes.VALUE_ACTIVO;

				if (razonSocial.trim().equals("")) {
					criteriosBusqueda.remove("razonSocial");
				}else {criteriosBusqueda.put("razonSocial", "%" + razonSocial + "%");}

				if (ruc.trim().equals("")) {
					criteriosBusqueda.remove("ruc");
				}else {criteriosBusqueda.put("ruc", "%" + ruc + "%");}

				criteriosBusqueda.put("estadoRegistro", estadoRegistro);

				listarRegistros(ServiceLocator.getConcesionarioManager().buscarPorX(criteriosBusqueda, criteriosOrdenar));

			}
		});

	}
	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.window.ui.IOpcionesMantenimiento#onRefresh(int)
	 */
	@Override
	public void onRefresh(int tab) throws Exception {
		if (!criteriosBusqueda.isEmpty()) {
			this.listarRegistros(ServiceLocator.getConcesionarioManager().buscarPorX(criteriosBusqueda, criteriosOrdenar));
		}
	}
	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.window.ui.IOpcionesMantenimiento#onModify(int)
	 */
	@Override
	public void onModify(int tab) throws Exception {
		Long id = new Long(0);
		id = ((Concesionario) listboxLista.getSelectedItem().getValue()).getId().longValue();
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
				//this.mantenimientoRegistro(new Long((String) listboxLista.getSelectedItem().getValue()));
				break;
		}
	}
	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.window.ui.IOpcionesMantenimiento#onSave(int)
	 */
	@Override
	public void onSave(int action) throws Exception {
		try{
			if (txtRazonSocial.getText().trim().equals("") )
				throw new RazonSocialNullException();
			else if (txtRuc.getText().trim().length() != 11)
				throw new NumeroDocumentoNullException();
			else if (dbFechaActivacion.getValue()==null)
				throw new FechaActivacionNullException();
			else if (!(Util.validarRUC(txtRuc.getText().trim())))
				throw new RucInvalidoException();
			else if (itbxComision.getValue()==null || itbxComision.getValue()<=0)
				throw new  NumberFormatException();
			else if(cmbTipoComision.getSelectedIndex()<=0)
				throw new TipoComisionException();


			if (action== ACTION_NEW)
				oConcesionario = new Concesionario();

			Integer id = (textboxId.getText().equals("") ? 0 : new Integer(textboxId.getText()));
			oConcesionario.setId(id);
			oConcesionario.setRazonSocial(txtRazonSocial.getText().trim().toUpperCase());
			oConcesionario.setRuc(txtRuc.getText());
			oConcesionario.setDireccion(txtDireccion.getText().trim().toUpperCase());
			oConcesionario.setFechaActivacion(dbFechaActivacion.getValue());
			oConcesionario.setFechaCaducidad(dbFechaCaducidad.getValue());
			oConcesionario.setFechaSuspension(dbFechaSuspension.getValue());
			oConcesionario.setComision(itbxComision.getValue());
			oConcesionario.setTipoComision((Integer)cmbTipoComision.getSelectedItem().getValue());
			oConcesionario.setIncluyeIgv(chbxIncluyeIgv.isChecked()?1:0);
			oConcesionario.setEstadoRegistro(Constantes.VALUE_ACTIVO);

			switch (action) {
				case ACTION_NEW:
					UtilData.auditarRegistro(oConcesionario,getUsuario(), Executions.getCurrent());
					ServiceLocator.getConcesionarioManager().guardar(oConcesionario);
					textboxId.setText(oConcesionario.getId().toString());
					break;
				case ACTION_MODIFY:
					UtilData.auditarRegistro(oConcesionario, true,getUsuario(), Executions.getCurrent());
					ServiceLocator.getConcesionarioManager().actualizar(oConcesionario);
					break;
			}
			/*RECUPERA EL REGISTRO ACTUALIZADO O EL NUEVO*/
			criteriosBusqueda.remove("razonSocial");criteriosBusqueda.remove("ruc");
			criteriosBusqueda.put("razonSocial", oConcesionario.getRazonSocial());
			criteriosBusqueda.put("estadoRegistro", Constantes.VALUE_ACTIVO);
			this.listarRegistros(ServiceLocator.getConcesionarioManager().buscarPorX(criteriosBusqueda, criteriosOrdenar));


		}catch (NumberFormatException nex){
			DlgMessage.information(Messages.getString("WndConcesionario.information.noComision"),itbxComision);
			throw new CancelaGrabacionException();
		}catch (TipoComisionException tcex){
			DlgMessage.information(Messages.getString("WndConcesionario.information.noSelectedTipoComision"),cmbTipoComision);
			throw new CancelaGrabacionException();
		}catch (RucInvalidoException rinex){
			DlgMessage.information(Messages.getString("WndConcesionario.information.numeroRucInvalido"),txtRuc);
			throw new CancelaGrabacionException();
		}catch(RucDuplicadoException rdnex){
			DlgMessage.information(Messages.getString("WndConcesionario.information.numeroRucDuplicado"),txtRuc);
			throw new CancelaGrabacionException();
		}catch(RazonSocialNullException rsnex){
			DlgMessage.information(Messages.getString("WndConcesionario.information.noIngresoRazonSocial"),txtRazonSocial);
			throw new CancelaGrabacionException();
		}catch(NumeroDocumentoNullException ndnex){
			DlgMessage.information(Messages.getString("WndConcesionario.information.noIngresoNumeroRuc"),txtRuc);
			throw new CancelaGrabacionException();
		}catch(RazonSocialDuplicadoException rsdex){
			DlgMessage.information(Messages.getString("WndConcesionario.information.razonSocialDuplicada"),txtRazonSocial);
			throw new CancelaGrabacionException();
		}catch(FechaActivacionNullException fanex){
			DlgMessage.information(Messages.getString("WndConcesionario.information.noSeleccionoFechaActivacion"),dbFechaActivacion);
			throw new CancelaGrabacionException();
		}catch(Exception ex){
			DlgMessage.error(this.getClass().getName()+" "+ex.getMessage());
			ex.printStackTrace();throw new CancelaGrabacionException();
		}
	}
	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.window.ui.IOpcionesMantenimiento#onDelete(int)
	 */
	@Override
	public void onDelete(int tab) throws Exception {
		try{
			//EliminacionRegistrosNullExcepcion
			Long id = (long) 0;

			id = new Long((String) listboxLista.getSelectedItem().getValue());
			if (id !=null){
				ServiceLocator.getConcesionarioManager().inactivar(id);
				//this.listarRegistros(ServiceDelegate.instance().concesionario_buscarPorX(criteriosBusqueda, criteriosOrdenar));
			}

		}catch(EliminacionRegistrosNullExcepcion ernex){
			DlgMessage.information(Messages.getString("WndConcesionario.information.noSeleccionoRegistro"));
		}catch(Exception ex){
			DlgMessage.error(this.getClass().getName()+" "+ex.getMessage());
			ex.printStackTrace();
		}
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
				if (listboxLista.getSelectedIndex() >=0) {
					this.mantenimientoRegistro(((Concesionario) listboxLista.getSelectedItem().getValue()).getId().longValue());
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

	private void listarRegistros(ArrayList<Concesionario> lstRegistros) {
		Listitem item=null;
		Listcell cell=null;
		int x=0;
		Util.limpiarListbox(listboxLista);

		for(Concesionario concesionario: lstRegistros){
			x++;

			item= new Listitem();
			//Item
			cell= new Listcell(String.valueOf(x));
			cell.setStyle("font-size:11px !Important");
			item.appendChild(cell);

			//Ruc
			cell=new Listcell(concesionario.getRuc());
			cell.setStyle("font-size:11px !Important");
			item.appendChild(cell);

			//Razón social
			cell=new Listcell(concesionario.getRazonSocial());
			item.appendChild(cell);

			//Direccion
			cell=new Listcell(concesionario.getDireccion());
			item.appendChild(cell);

			//Fecha de Activación
			cell=new Listcell(Constantes.FORMAT_DATE.format(concesionario.getFechaActivacion()));
			cell.setStyle("font-size:11px !Important");
			item.appendChild(cell);

			item.setValue(concesionario);
			listboxLista.appendChild(item);
		}

	}

	private void mantenimientoRegistro(Long id) throws Exception {
		 oConcesionario = ServiceLocator.getConcesionarioManager().buscarPorId(id);

		textboxId.setText(oConcesionario.getId().toString());
		txtRazonSocial.setText(oConcesionario.getRazonSocial());
		txtRuc.setText(oConcesionario.getRuc());
		txtDireccion.setText(oConcesionario.getDireccion());
		dbFechaActivacion.setValue(oConcesionario.getFechaActivacion());
		dbFechaCaducidad.setValue(oConcesionario.getFechaCaducidad());
		dbFechaSuspension.setValue(oConcesionario.getFechaSuspension());
		itbxComision.setValue(oConcesionario.getComision()!=null? oConcesionario.getComision():null);
		if(oConcesionario.getTipoComision()!=null)
			Util.seleccionarValorItemCombobox(cmbTipoComision, oConcesionario.getTipoComision());
		else cmbTipoComision.setSelectedIndex(0);
		if(oConcesionario.getIncluyeIgv()!=null){
			chbxIncluyeIgv.setChecked(oConcesionario.getIncluyeIgv().intValue()==1);
		}else
			chbxIncluyeIgv.setChecked(false);

	}
}