/**
 * Proyecto		: SISVYR
 * Sistema		: Sistema de Ventas y Reservas
 * Descripción	: 
 * Autor		: jM
 * Fecha		: 17/05/2012
 */
package com.cystesoft.vyrbus.view.ctrl;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zul.Checkbox;
import org.zkoss.zul.Intbox;
import org.zkoss.zul.Textbox;

import com.cystesoft.vyrbus.model.bean.TipoDocumento;
import com.cystesoft.vyrbus.service.exceptions.CancelaGrabacionException;
import com.cystesoft.vyrbus.service.exceptions.DenominacionDuplicadaException;
import com.cystesoft.vyrbus.service.exceptions.DenominacionNullException;
import com.cystesoft.vyrbus.service.exceptions.NombreCortoDuplicadoException;
import com.cystesoft.vyrbus.service.exceptions.NombreCortoNullException;
import com.cystesoft.vyrbus.service.locator.ServiceLocator;
import com.cystesoft.vyrbus.service.util.Constantes;
import com.cystesoft.vyrbus.service.util.Messages;
import com.cystesoft.vyrbus.service.util.Util;
import com.cystesoft.vyrbus.service.util.UtilData;
import com.cystesoft.vyrbus.view.ui.DlgMessage;
import com.cystesoft.vyrbus.view.ui.WndFiltrarParametros;
import com.cystesoft.vyrbus.view.ui.WndOpcionesMantenimiento;

/**
 *
 * @author jM
 * @since JDK1.6
 */
public class WndTipoDocumento extends WndOpcionesMantenimiento {

	private static final long serialVersionUID = -3022508533748721546L;

	private Textbox txtDenominacion;
	private Textbox txtNombreCortoSufijo;
	private Textbox txtMascaraFormatoIngresoDatos;
	private Checkbox chkMarcarDocumentacionBus;
	private Intbox ibAlerta;
	private Textbox txtColorAlerta;
	private Intbox ibSegundaAlerta;
	private Textbox txtColorSegundaAlerta;
	
	private TipoDocumento oTipoDocumento = null;
	private TreeMap<String, Object> criteriosBusqueda = new TreeMap<String, Object>();
	private List<String> criteriosOrdenar = null;

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.window.ui.IBase#onCreate()
	 */
	@Override
	public void onCreate() throws Exception {
		criteriosOrdenar = new ArrayList<String>();
		criteriosOrdenar.add("denominacion");
	}

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.window.ui.IBase#initComponents()
	 */
	@Override
	public void initComponents() {
		txtDenominacion = (Textbox) getFellow("txtDenominacion");
		txtNombreCortoSufijo = (Textbox) getFellow("txtNombreCortoSufijo");
		txtMascaraFormatoIngresoDatos = (Textbox) getFellow("txtMascaraFormatoIngresoDatos");
		chkMarcarDocumentacionBus = (Checkbox) getFellow("chkMarcarDocumentacionBus");
		ibAlerta = (Intbox) getFellow("ibAlerta");
		txtColorAlerta = (Textbox) getFellow("txtColorAlerta");
		ibSegundaAlerta = (Intbox) getFellow("ibSegundaAlerta");
		txtColorSegundaAlerta = (Textbox) getFellow("txtColorSegundaAlerta");
	}

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.window.ui.IOpcionesMantenimiento#onNew()
	 */
	@Override
	public void onNew() {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.window.ui.IOpcionesMantenimiento#onSearch()
	 */
	@Override
	public void onSearch() {
		final WndFiltrarParametros oWndFiltrar = new WndFiltrarParametros();

		oWndFiltrar.addParameter("Denominación", String.class);
		oWndFiltrar.addParameter("Nombre corto (o sufijo)", String.class);

		this.appendChild(oWndFiltrar);
		oWndFiltrar.setMode("modal");
		oWndFiltrar.addEventListener(com.cystesoft.vyrbus.view.ui.Events.ON_FILTER, new EventListener<Event>() {

			@Override
			public void onEvent(Event event) throws Exception {
				String denominacion = (String) oWndFiltrar.getParameterValue("Denominación");
				String nombreCorto = (String) oWndFiltrar.getParameterValue("Nombre corto (o sufijo)");
				String estadoRegistro = Constantes.VALUE_ACTIVO;

				if(denominacion.trim().equals("")){
					criteriosBusqueda.remove("denominacion");
				}else{criteriosBusqueda.put("denominacion", "%" + denominacion + "%");}

				if(nombreCorto.trim().equals("")){
					criteriosBusqueda.remove("nombreCorto");
				}else {criteriosBusqueda.put("nombreCorto", "%" + nombreCorto + "%");}

				criteriosBusqueda.put("estadoRegistro", estadoRegistro);

				listarRegistros(ServiceLocator.getTipoDocumentoManager().buscarPorX(criteriosBusqueda, criteriosOrdenar));
			}
		});
	}

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.window.ui.IOpcionesMantenimiento#onRefresh(int)
	 */
	@Override
	public void onRefresh(int tab) throws Exception {
		if (!criteriosBusqueda.isEmpty()) {
			this.listarRegistros(ServiceLocator.getTipoDocumentoManager().buscarPorX(criteriosBusqueda, criteriosOrdenar));					
		}
	}

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.window.ui.IOpcionesMantenimiento#onModify(int)
	 */
	@Override
	public void onModify(int tab) throws Exception {
		try{
			Long id = new Long(0);
			id = new Long((String) listboxLista.getSelectedItem().getValue());
			this.mantenimientoRegistro(id);
		}catch (Exception ex){
			DlgMessage.error(this.getClass().getName()+" "+ex.getMessage());
			ex.printStackTrace();
		}
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
			if (txtDenominacion.getText().trim().equals(""))
				throw new DenominacionNullException();
			else if (txtNombreCortoSufijo.getText().trim().equals(""))
				throw new NombreCortoNullException();
			
			
			if (action==ACTION_NEW)
				oTipoDocumento = new TipoDocumento();
			
			Integer id = (textboxId.getText().equals("") ? 0 : new Integer(textboxId.getText()));
			oTipoDocumento.setId(id);
			oTipoDocumento.setDenominacion(txtDenominacion.getText().trim().toUpperCase());
			oTipoDocumento.setNombreCorto(txtNombreCortoSufijo.getText().trim().toUpperCase());
			oTipoDocumento.setMaskerade(txtMascaraFormatoIngresoDatos.getText().trim().toUpperCase());
			oTipoDocumento.setTipo(chkMarcarDocumentacionBus.isChecked()?TipoDocumento.BUS:TipoDocumento.PERSONALES);
			oTipoDocumento.setAlerta1(ibAlerta.getText().equals("") ? 0 : new Integer(ibAlerta.getValue().intValue()));
			oTipoDocumento.setColorAlerta1(txtColorAlerta.getText().trim().toUpperCase());
			oTipoDocumento.setAlerta2(ibSegundaAlerta.getText().equals("") ? 0 : new Integer(ibSegundaAlerta.getValue().intValue()));
			oTipoDocumento.setColorAlerta2(txtColorSegundaAlerta.getText().trim().toUpperCase());
			oTipoDocumento.setEstadoRegistro(Constantes.VALUE_ACTIVO);

			switch (action) {
				case ACTION_NEW:
					UtilData.auditarRegistro(oTipoDocumento, getUsuario(), Executions.getCurrent());
					ServiceLocator.getTipoDocumentoManager().guardar(oTipoDocumento);
					textboxId.setText(oTipoDocumento.getId().toString());
					break;

				case ACTION_MODIFY:
					UtilData.auditarRegistro(oTipoDocumento, true, getUsuario(), Executions.getCurrent());
					ServiceLocator.getTipoDocumentoManager().actualizar(oTipoDocumento);
					break;
			}
			/*RECUEPRA EL REGUSTRO ACTUALIZADO O EL NUEVO*/
			criteriosBusqueda.remove("denominacion");
			criteriosBusqueda.remove("nombreCorto");
			criteriosBusqueda.put("denominacion", oTipoDocumento.getDenominacion());
			criteriosBusqueda.put("estadoRegistro", Constantes.VALUE_ACTIVO);
			listarRegistros(ServiceLocator.getTipoDocumentoManager().buscarPorX(criteriosBusqueda, criteriosOrdenar));
			

		}catch (DenominacionNullException dnex){
			DlgMessage.information(Messages.getString("Generales.information.noIngresoDenominacion"),txtDenominacion);
			throw new CancelaGrabacionException();
		}catch (NombreCortoNullException ncnex){
			DlgMessage.information(Messages.getString("Generales.information.noIngresoNombreCorto"),txtNombreCortoSufijo);
			throw new CancelaGrabacionException();
		}catch (DenominacionDuplicadaException dndex){
			DlgMessage.information(Messages.getString("Generales.information.denominacionDuplicada"),txtDenominacion);
			throw new CancelaGrabacionException();
		}catch (NombreCortoDuplicadoException ncdex){
			DlgMessage.information(Messages.getString("Generales.information.nombreCortoDuplicado"),txtNombreCortoSufijo);
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

		ServiceLocator.getTipoDocumentoManager().inactivar(id);
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
	 * @see com.tepsa.sisvyr.window.ui.IOpcionesMantenimiento#onClose()
	 */
	@Override
	public void onClose() {
		closeTabWindow();
	}

	private void listarRegistros(ArrayList<TipoDocumento> lstRegistros) {
		ArrayList<Object> lstTipoDocumentos = new ArrayList<Object>();

		for(int r = 0; r < lstRegistros.size(); r ++) {
			TipoDocumento oTipoDocumento = lstRegistros.get(r);
			ArrayList<Object> lstFila = new ArrayList<Object>();

			lstFila.add(oTipoDocumento.getId());
			lstFila.add(r + 1);
			lstFila.add(oTipoDocumento.getDenominacion());
			lstFila.add(oTipoDocumento.getNombreCorto());
			lstFila.add(oTipoDocumento.getMaskerade());
			lstFila.add((oTipoDocumento.getTipo().intValue()==TipoDocumento.BUS ? "sí" : "no"));

			lstTipoDocumentos.add(lstFila);
		}

		Util.llenarListbox(listboxLista, lstTipoDocumentos, true);
	}
	
	private void mantenimientoRegistro(Long id) throws Exception {
		oTipoDocumento = ServiceLocator.getTipoDocumentoManager().buscarPorId(id);

		textboxId.setText(oTipoDocumento.getId().toString());
		txtDenominacion.setText(oTipoDocumento.getDenominacion());
		txtNombreCortoSufijo.setText(oTipoDocumento.getNombreCorto());
		
		if (oTipoDocumento.getMaskerade() !=null)
			txtMascaraFormatoIngresoDatos.setText(oTipoDocumento.getMaskerade());
		
		if (oTipoDocumento.getAlerta1() !=null)
			ibAlerta.setValue(oTipoDocumento.getAlerta1().intValue());
		
		if (oTipoDocumento.getAlerta2() !=null)
			ibSegundaAlerta.setValue(oTipoDocumento.getAlerta2().intValue());

		chkMarcarDocumentacionBus.setChecked(oTipoDocumento.getTipo().intValue()==TipoDocumento.BUS?true:false);
		txtColorAlerta.setText(oTipoDocumento.getColorAlerta1());
		txtColorSegundaAlerta.setText(oTipoDocumento.getColorAlerta2());
		
	}
}