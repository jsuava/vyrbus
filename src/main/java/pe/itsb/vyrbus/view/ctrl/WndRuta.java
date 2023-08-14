/**
 * Proyecto		: SISVYR
 * Sistema		: Sistema de Ventas y Reservas
 * Descripci�n	:
 * Autor		: jM
 * Fecha		: 02/05/2012
 */
package pe.itsb.vyrbus.view.ctrl;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.TreeMap;

import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Doublebox;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;

import pe.itsb.vyrbus.model.bean.Empresa;
import pe.itsb.vyrbus.model.bean.Localidad;
import pe.itsb.vyrbus.model.bean.Ruta;
import pe.itsb.vyrbus.service.exceptions.CancelaGrabacionException;
import pe.itsb.vyrbus.service.exceptions.EmpresaException;
import pe.itsb.vyrbus.service.exceptions.HorasViajeNullException;
import pe.itsb.vyrbus.service.exceptions.KilometrosNullException;
import pe.itsb.vyrbus.service.exceptions.LocalidadNullException;
import pe.itsb.vyrbus.service.exceptions.RutaDuplicadaException;
import pe.itsb.vyrbus.service.exceptions.RutaPuntajeNullException;
import pe.itsb.vyrbus.service.exceptions.RutasSeleccionadasIgualesException;
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
public class WndRuta extends WndOpcionesMantenimiento {

	private static final long serialVersionUID = 5793820793678398114L;

	private Combobox cboLocalidadOrigen;
	private Combobox cboLocalidadDestino;
	private Combobox cmbEmpresa;
	private Doublebox dbKilometros;
	private Doublebox spHorasViaje;
	private Doublebox itPuntaje;

	private Ruta oRuta=null;
	private TreeMap<String, Object> criteriosBusqueda = new TreeMap<>();
	private List<String> criteriosOrdenar = null;


	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.window.ui.IBase#onCreate()
	 */
	@Override
	public void onCreate() throws Exception {
		UtilData.cargarDataCombo(cboLocalidadOrigen, Localidad.class, false);
		UtilData.cargarDataCombo(cboLocalidadDestino, Localidad.class, false);
		UtilData.cargarDataCombo(cmbEmpresa, Empresa.class, false);
		criteriosOrdenar = new ArrayList<>();
		criteriosOrdenar.add("localidadOrigen");

		spHorasViaje.setLocale(Locale.US);
		dbKilometros.setLocale(Locale.US);
	}

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.window.ui.IBase#initComponents()
	 */
	@Override
	public void initComponents() {
		cboLocalidadOrigen = (Combobox) getFellow("cboLocalidadOrigen");
		cboLocalidadDestino = (Combobox) getFellow("cboLocalidadDestino");
		cmbEmpresa = (Combobox) getFellow("cmbEmpresa");
		dbKilometros = (Doublebox) getFellow("dbKilometros");
		spHorasViaje = (Doublebox) getFellow("spHorasViaje");
		itPuntaje = (Doublebox) getFellow("itPuntaje");
	}

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.window.ui.IOpcionesMantenimiento#onNew()
	 */
	@Override
	public void onNew() {
		cmbEmpresa.setSelectedIndex(0);
		cboLocalidadOrigen.setSelectedIndex(0);
		cboLocalidadDestino.setSelectedIndex(0);
	}


	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.window.ui.IOpcionesMantenimiento#onSearch()
	 */
	@Override
	public void onSearch() throws Exception {
		final WndFiltrarParametros oWndFiltrar = new WndFiltrarParametros();

		oWndFiltrar.addParameter("1. Origen", Localidad.class);
		oWndFiltrar.addParameter("2. Destino", Localidad.class);
		oWndFiltrar.addParameter("3. Empresa", Empresa.class);

		this.appendChild(oWndFiltrar);
		oWndFiltrar.setMode("modal");

		oWndFiltrar.addEventListener(pe.itsb.vyrbus.view.ui.Events.ON_FILTER, new EventListener<Event>() {
			@Override
			public void onEvent(Event event) throws Exception {
				Localidad localidadOrigen = (Localidad) oWndFiltrar.getParameterValue("1. Origen");
				if(localidadOrigen==null)
					criteriosBusqueda.remove("origen");
				else
					criteriosBusqueda.put("origen", localidadOrigen.getDenominacion());

				Localidad localidadDestino = (Localidad) oWndFiltrar.getParameterValue("2. Destino");
				if(localidadDestino==null)
					criteriosBusqueda.remove("destino");
				else
					criteriosBusqueda.put("destino", localidadDestino.getDenominacion());
				
				Empresa empresa = (Empresa)oWndFiltrar.getParameterValue("3. Empresa");
				if(empresa == null)
					criteriosBusqueda.remove("empresa");
				else {
					criteriosBusqueda.put("empresa", empresa);
				}

	        	String estadoRegistro = Constantes.VALUE_ACTIVO;
	        	criteriosBusqueda.put("estadoRegistro", estadoRegistro);
	        	listarRegistros(ServiceLocator.getRutaManager().buscarPorX(criteriosBusqueda, criteriosOrdenar));
			}

		});

	}


	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.window.ui.IOpcionesMantenimiento#onRefresh(int)
	 */
	@Override
	public void onRefresh(int tab) throws Exception {
		if (!criteriosBusqueda.isEmpty()) {
			this.listarRegistros(ServiceLocator.getRutaManager().buscarPorX(criteriosBusqueda, criteriosOrdenar));
		}
	}


	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.window.ui.IOpcionesMantenimiento#onModify(int)
	 */
	@Override
	public void onModify(int tab) throws Exception {
		Long id = new Long(0);
		id = ((Ruta) listboxLista.getSelectedItem().getValue()).getId().longValue();
		if(id!=null)
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
			if (!(cboLocalidadOrigen.getSelectedItem().getValue() instanceof Localidad))
				throw new LocalidadNullException(LocalidadNullException.ORIGEN);
			else if (!(cboLocalidadDestino.getSelectedItem().getValue() instanceof Localidad))
				throw new LocalidadNullException(LocalidadNullException.DESTINO);
			else if (!(cmbEmpresa.getSelectedItem().getValue() instanceof Empresa))
				throw new EmpresaException();
			else if (dbKilometros.getText().equals(0) || dbKilometros.getText().equals(""))
				throw new KilometrosNullException();
			else if (spHorasViaje.getText().equals(""))
				throw new HorasViajeNullException();
			else if (cboLocalidadOrigen.getSelectedItem().getValue() instanceof Localidad && cboLocalidadDestino.getSelectedItem().getValue() instanceof Localidad){
				if (((Localidad) cboLocalidadOrigen.getSelectedItem().getValue()).getId() == ((Localidad) cboLocalidadDestino.getSelectedItem().getValue()).getId())
					throw new RutasSeleccionadasIgualesException();
			}
			if (itPuntaje.getText()=="")
				throw new RutaPuntajeNullException();

			if (action==ACTION_NEW)
				oRuta = new Ruta();

			Localidad oLocalidadOrigen = new Localidad();
			Localidad oLocalidadDestino = new Localidad();
			Empresa oEmpresa = new Empresa();
			Integer id = (textboxId.getText().equals("") ? 0 : new Integer(textboxId.getText()));

			oLocalidadOrigen.setId(((Localidad) cboLocalidadOrigen.getSelectedItem().getValue()).getId());
			oLocalidadOrigen.setDenominacion(cboLocalidadOrigen.getText());
			oLocalidadDestino.setId(((Localidad) cboLocalidadDestino.getSelectedItem().getValue()).getId());
			oLocalidadDestino.setDenominacion(cboLocalidadDestino.getText());
			oEmpresa.setId(((Empresa)cmbEmpresa.getSelectedItem().getValue()).getId());
			oEmpresa.setRazonSocial(cmbEmpresa.getText());
			oEmpresa.setNombreCorto(((Empresa)cmbEmpresa.getSelectedItem().getValue()).getNombreCorto());

			oRuta.setId(id);
			oRuta.setKilometros(dbKilometros.getValue());
			oRuta.setHorasViaje(spHorasViaje.getValue());
			oRuta.setLocalidadOrigen(oLocalidadOrigen);
			oRuta.setOrigen(oLocalidadOrigen.getDenominacion());
			oRuta.setLocalidadDestino(oLocalidadDestino);
			oRuta.setDestino(oLocalidadDestino.getDenominacion());
			oRuta.setEmpresa(oEmpresa);
			oRuta.setPuntaje(itPuntaje.getValue().intValue());
			oRuta.setEstadoRegistro(Constantes.VALUE_ACTIVO);

			switch (action) {
				case ACTION_NEW:
					UtilData.auditarRegistro(oRuta, getUsuario(), Executions.getCurrent());
					ServiceLocator.getRutaManager().guardar(oRuta);
					textboxId.setText(oRuta.getId().toString());
					break;

				case ACTION_MODIFY:
					UtilData.auditarRegistro(oRuta, true, getUsuario(), Executions.getCurrent());
					ServiceLocator.getRutaManager().actualizar(oRuta);
					break;
			}
			/*RECUPERA EL REGISTRO ACTUALIZADO O EL NUEVO*/
	        criteriosBusqueda.remove("destino");
	        criteriosBusqueda.remove("origen");
	        criteriosBusqueda.put("origen", oRuta.getOrigen());
	        criteriosBusqueda.put("destino", oRuta.getDestino());
	        criteriosBusqueda.put("estadoRegistro", Constantes.VALUE_ACTIVO);
			this.listarRegistros(ServiceLocator.getRutaManager().buscarPorX(criteriosBusqueda, criteriosOrdenar));

		}catch(LocalidadNullException lnex){
			if(lnex.getOrigenDestino().intValue()==LocalidadNullException.ORIGEN){
				DlgMessage.information(Messages.getString("WndRuta.information.LocalidadOrigen"),cboLocalidadDestino);
				throw new CancelaGrabacionException();
			}else{
				DlgMessage.information(Messages.getString("WndRuta.information.LocalidadDestino"),cboLocalidadDestino);
				throw new CancelaGrabacionException(); }
		}catch(EmpresaException eex) {
			DlgMessage.information(Messages.getString("WndRuta.information.Empresa"),dbKilometros);
			throw new CancelaGrabacionException();
		}catch (KilometrosNullException knex){
			DlgMessage.information(Messages.getString("WndRuta.information.Kilometros"),dbKilometros);
			throw new CancelaGrabacionException();
		}catch (HorasViajeNullException hvnex){
			DlgMessage.information(Messages.getString("WndRuta.information.HorasViaje"),spHorasViaje);
			throw new CancelaGrabacionException();
		}catch (RutaDuplicadaException rdex){
			DlgMessage.information(Messages.getString("WndRuta.information.RutaDuplicada"),cboLocalidadOrigen);
			throw new CancelaGrabacionException();
		}catch (RutasSeleccionadasIgualesException rsiex){
			DlgMessage.information(Messages.getString("WndRuta.information.LocalidadOrigenDestinoIguales"),cboLocalidadOrigen);
			throw new CancelaGrabacionException();
		}catch (RutaPuntajeNullException rpnex){
			DlgMessage.information(Messages.getString("WndRuta.information.NoPuntaje"),itPuntaje);
			throw new CancelaGrabacionException();
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
				id = ((Ruta) listboxLista.getSelectedItem().getValue()).getId().longValue();
				break;

			case TAB_MAINTENANCE:
				id = new Long(textboxId.getText());
				break;
		}

		ServiceLocator.getRutaManager().inactivar(id);
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
					this.mantenimientoRegistro( ((Ruta) listboxLista.getSelectedItem().getValue()).getId().longValue());
				}
				break;
		}
	}

	private void listarRegistros(ArrayList<Ruta> lstRegistros) {
		Listitem item=null;
		Listcell cell=null;
		int x=0;
		Util.limpiarListbox(listboxLista);

		for(Ruta ruta: lstRegistros){
			x++;
			item=new Listitem();
			cell=new Listcell(String.valueOf(x));
			cell.setStyle("font-size:11px !important");
			item.appendChild(cell);
			cell=new Listcell(ruta.getEmpresa().getNombreCorto());
			item.appendChild(cell);
			cell=new Listcell(ruta.getOrigen());
			item.appendChild(cell);
			cell=new Listcell(ruta.getDestino());
			item.appendChild(cell);
			cell=new Listcell(Util.toNumberFormat(ruta.getKilometros(), 2));
			cell.setStyle("font-size:11px !important; text-align:right");
			item.appendChild(cell);
			cell=new Listcell(ruta.getHorasViaje().toString());
			cell.setStyle("font-size:11px !important; text-align:right");
			item.appendChild(cell);

			item.setValue(ruta);
			listboxLista.appendChild(item);
		}
	}

	private void mantenimientoRegistro(Long id) throws Exception {
		oRuta = ServiceLocator.getRutaManager().buscarPorId(id);

		textboxId.setText(oRuta.getId().toString());
		dbKilometros.setValue(oRuta.getKilometros());
		spHorasViaje.setValue((oRuta.getHorasViaje()));
		itPuntaje.setValue(oRuta.getPuntaje() != null ? oRuta.getPuntaje(): 0 );
		Util.seleccionarValorItemCombo(Localidad.class, cboLocalidadOrigen, oRuta.getLocalidadOrigen().getId());
		Util.seleccionarValorItemCombo(Localidad.class, cboLocalidadDestino, oRuta.getLocalidadDestino().getId());
		Util.seleccionarValorItemCombo(Empresa.class, cmbEmpresa, oRuta.getEmpresa().getId());
	}


	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.window.IOpcionesMantenimiento#onClose()
	 */
	@Override
	public void onClose() {
		closeTabWindow();
	}
}