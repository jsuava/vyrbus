/**
 * Proyecto		: SISVYR
 * Sistema		: Sistema de Ventas y Reservas
 * Descripción	:
 * Autor		: José Avalos
 * Fecha		: 25/11/2013
 */
package pe.itsb.vyrbus.view.ctrl;

import java.util.List;
import java.util.TreeMap;

import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zul.Button;
import org.zkoss.zul.Column;
import org.zkoss.zul.Columns;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Div;
import org.zkoss.zul.Grid;
import org.zkoss.zul.Intbox;
import org.zkoss.zul.Label;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Row;
import org.zkoss.zul.Rows;
import org.zkoss.zul.Window;

import pe.itsb.vyrbus.model.bean.Agencia;
import pe.itsb.vyrbus.model.bean.SerieEspecieValorada;
import pe.itsb.vyrbus.model.bean.SerieEspecieValoradaID;
import pe.itsb.vyrbus.model.bean.TipoComprobante;
import pe.itsb.vyrbus.service.exceptions.AgenciaNullException;
import pe.itsb.vyrbus.service.exceptions.CancelaGrabacionException;
import pe.itsb.vyrbus.service.exceptions.NumeroSerieDuplicadoException;
import pe.itsb.vyrbus.service.exceptions.NumeroSerieNullException;
import pe.itsb.vyrbus.service.exceptions.TipoComprobanteNullException;
import pe.itsb.vyrbus.service.locator.ServiceLocator;
import pe.itsb.vyrbus.service.util.Constantes;
import pe.itsb.vyrbus.service.util.Messages;
import pe.itsb.vyrbus.service.util.Util;
import pe.itsb.vyrbus.service.util.UtilData;
import pe.itsb.vyrbus.view.ui.DlgMessage;
import pe.itsb.vyrbus.view.ui.WndOpcionesMantenimiento;

/**
 * @author JABANTO
 *
 */
public class WndSerieEspecieValorada extends WndOpcionesMantenimiento {
	private static final long serialVersionUID = 1L;

	private Combobox cmbAgencia;
	private Combobox cmbTipoComprobante;
	private Intbox itbxNumeroSerie;

	private Window wndSearch = null;
	private TreeMap<String, Object>criteriosBusqueda=null;
	private List<String>criteriosOrden=null;

	private SerieEspecieValorada serieEspecieValorada=null;

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.view.ui.WndBase#initComponents()
	 */
	@Override
	public void initComponents() {
		// TODO Auto-generated method stub
		cmbAgencia=(Combobox)this.getFellow("cmbAgencia");
		cmbTipoComprobante=(Combobox)this.getFellow("cmbTipoComprobante");
		itbxNumeroSerie=(Intbox)this.getFellow("itbxNumeroSerie");
	}

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.view.ui.WndBase#onCreate()
	 */
	@Override
	public void onCreate() throws Exception {
		// TODO Auto-generated method stub
		UtilData.cargarAgenciaXtipoAgencia(cmbAgencia, Constantes.ID_TIPAGE_TEPSA,true);
		UtilData.cargarDataCombo(cmbTipoComprobante, TipoComprobante.class, false);
	}

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.view.ui.IOpcionesMantenimiento#onNew()
	 */
	@Override
	public void onNew() throws Exception {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.view.ui.IOpcionesMantenimiento#onSearch()
	 */
	@Override
	public void onSearch() throws Exception {
		// TODO Auto-generated method stub
		ventarBusqueda();
	}

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.view.ui.IOpcionesMantenimiento#onRefresh(int)
	 */
	@Override
	public void onRefresh(int tab) throws Exception {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.view.ui.IOpcionesMantenimiento#onModify(int)
	 */
	@Override
	public void onModify(int tab) throws Exception {
		// TODO Auto-generated method stub
		mantenimiento();
	}

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.view.ui.IOpcionesMantenimiento#onCancel(int)
	 */
	@Override
	public void onCancel(int action) throws Exception {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.view.ui.IOpcionesMantenimiento#onSave(int)
	 */
	@Override
	public void onSave(int action) throws Exception {
		// TODO Auto-generated method stub
		try{
			if(!(cmbAgencia.getSelectedItem().getValue() instanceof Agencia))
				throw new AgenciaNullException();
			else if (!(cmbTipoComprobante.getSelectedItem().getValue() instanceof TipoComprobante))
				throw new TipoComprobanteNullException();
			else if (itbxNumeroSerie.getText().trim().isEmpty())
				throw new NumeroSerieNullException();

			if(action==ACTION_NEW)
				serieEspecieValorada=new SerieEspecieValorada();

			TipoComprobante tipoComprobante=cmbTipoComprobante.getSelectedItem().getValue();
			Agencia agencia=cmbAgencia.getSelectedItem().getValue();

			SerieEspecieValoradaID serieEspecieValoradaID=new SerieEspecieValoradaID();
			serieEspecieValoradaID.setIdTipoComprobante(tipoComprobante.getId());
			serieEspecieValoradaID.setNumeroSerie(itbxNumeroSerie.getText().trim());

			serieEspecieValorada.setSerieEspecieValorada_ID(serieEspecieValoradaID);
			serieEspecieValorada.setTipoComprobante(tipoComprobante);
			serieEspecieValorada.setAgencia(agencia);
			serieEspecieValorada.setEstadoRegistro(Constantes.VALUE_ACTIVO);

			switch (action) {
			case ACTION_NEW:
				UtilData.auditarRegistro(serieEspecieValorada, getUsuario(), Executions.getCurrent());
//				ServiceLocator.getSerieEspecieValoradaManager()
				break;

			case ACTION_MODIFY:
				break;
			}



		}catch(AgenciaNullException anex){
			DlgMessage.information(Messages.getString(""),cmbAgencia);
			throw new CancelaGrabacionException();
		}catch (TipoComprobanteNullException tcnex){
			DlgMessage.information(Messages.getString(""),cmbTipoComprobante);
			throw new CancelaGrabacionException();
		}catch (NumeroSerieNullException nsex){
			DlgMessage.information(Messages.getString(""),itbxNumeroSerie);
			throw new CancelaGrabacionException();
		}catch (NumeroSerieDuplicadoException nsdex){
			DlgMessage.information(Messages.getString(""),itbxNumeroSerie);
			throw new CancelaGrabacionException();
		}catch (Exception ex) {
			DlgMessage.error(this.getClass().getName()+" "+ex.getMessage());
			ex.printStackTrace();throw new CancelaGrabacionException();
		}





	}

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.view.ui.IOpcionesMantenimiento#onDelete(int)
	 */
	@Override
	public void onDelete(int tab) throws Exception {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.view.ui.IOpcionesMantenimiento#onPrint(int)
	 */
	@Override
	public void onPrint(int tab) throws Exception {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.view.ui.IOpcionesMantenimiento#onExport(int)
	 */
	@Override
	public void onExport(int tab) throws Exception {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.view.ui.IOpcionesMantenimiento#onHelp()
	 */
	@Override
	public void onHelp() throws Exception {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.view.ui.IOpcionesMantenimiento#onChangeTab(int)
	 */
	@Override
	public void onChangeTab(int tab) throws Exception {
		// TODO Auto-generated method stub

	}


	private void mantenimiento(){
		if(listboxLista.getSelectedIndex()>0){
			SerieEspecieValorada serieEspecieValorada=listboxLista.getSelectedItem().getValue();
			Util.seleccionarValorItemCombo(Agencia.class, cmbAgencia, serieEspecieValorada.getAgencia().getId());
			Util.seleccionarValorItemCombo(TipoComprobante.class, cmbTipoComprobante, serieEspecieValorada.getTipoComprobante().getId());
			itbxNumeroSerie.setText(serieEspecieValorada.getNumeroSerie());
		}
	}


	private void ventarBusqueda() throws Exception{
		wndSearch = createWindowSearch();
		this.appendChild(wndSearch);
		wndSearch.setMode("modal");
	}

	@SuppressWarnings("deprecation")
	private Window createWindowSearch() throws Exception{

		final Window window = new Window(Messages.getString("System.title"), "normal", true);
		window.setWidth("450px");

		Grid grid= new Grid();
		Rows rows= new Rows();
		Row row= new Row();
		Columns columns= new Columns();
		Column column= new Column();
		column.setWidth("150px");
		columns.appendChild(column);
		grid.appendChild(columns);

		Label label= new Label("Agencia");
		label.setSclass("label-size11");
		final Combobox cmbAgencia= new Combobox();
		cmbAgencia.setWidth("200px");
		cmbAgencia.setReadonly(true);
		row.appendChild(label);
		row.appendChild(cmbAgencia);
		rows.appendChild(row);

		label= new Label();
		label.setValue("Tipo Comprobante");
		label.setSclass("label-size11");
		final Combobox cmbTipoComprobante= new Combobox();
		cmbTipoComprobante.setWidth("200px");
		cmbTipoComprobante.setReadonly(true);

		row= new Row();
		row.appendChild(label);
		row.appendChild(cmbTipoComprobante);
		rows.appendChild(row);

		Div div= new Div();
		Button button= new Button();
		button.setLabel("Filtrar");
		button.setImage("/resources/mp_filtrar.png");
		div.setAlign("center");
		div.appendChild(button);

		grid.appendChild(rows);
		window.appendChild(grid);
		window.appendChild(div);

		/*Carga solamente la agencias tepsa*/
		UtilData.cargarAgenciaXtipoAgencia(cmbAgencia, Constantes.ID_TIPAGE_TEPSA,true);

		if(cmbAgencia.getSelectedItem()==null)
			cmbAgencia.setSelectedIndex(0);

		//Carga Tipo de comprobante
		if(cmbAgencia.getSelectedItem().getValue() instanceof Agencia)
			UtilData.cargarTipoComprobanteAgencia(cmbTipoComprobante, ((Agencia)cmbAgencia.getSelectedItem().getValue()), true);
		else
			UtilData.cargarGenericData(cmbTipoComprobante, false);

		cmbAgencia.addEventListener(Events.ON_CHANGE, new EventListener<Event>() {
			@Override
			public void onEvent(Event event) throws Exception {
				Util.limpiarCombobox(cmbTipoComprobante);
				UtilData.cargarTipoComprobanteAgencia(cmbTipoComprobante, ((Agencia)cmbAgencia.getSelectedItem().getValue()), true);
			}
		});


		button.addEventListener(Events.ON_CLICK, new EventListener<Event>() {
			@Override
			public void onEvent(Event event) throws Exception {
				Util.limpiarListbox(listboxLista);

				listarSeries((TipoComprobante)cmbTipoComprobante.getSelectedItem().getValue(),(Agencia)cmbAgencia.getSelectedItem().getValue());
				window.onClose();
			}
		});


		/**Solo se habilita este control para el rol Superusuario*/
		cmbAgencia.setDisabled(Constantes.ID_ROL_SUPER_USUARIO!=getRol().getId().intValue());
		/********************************/

		return window;
	}

	public void listarSeries(TipoComprobante tipoComprobante, Agencia agencia){
		criteriosBusqueda=new TreeMap<>();
		criteriosBusqueda.put("tipoComprobante", tipoComprobante);
		criteriosBusqueda.put("agencia", agencia);
		criteriosBusqueda.put("estadoRegistro", Constantes.VALUE_ACTIVO);
		List<SerieEspecieValorada>listSeries=ServiceLocator.getSerieEspecieValoradaManager().buscarPorX(criteriosBusqueda, criteriosOrden);

		for(SerieEspecieValorada serieEspecieValorada: listSeries){
			Listitem item=new Listitem();

			Listcell cell= new Listcell(serieEspecieValorada.getAgencia().getDenominacion());
			item.appendChild(cell);
			cell=new Listcell(serieEspecieValorada.getTipoComprobante().getDenominacion());
			item.appendChild(cell);
			cell=new Listcell(serieEspecieValorada.getNumeroSerie());
			cell.setStyle("font-size:11px");
			item.appendChild(cell);

			item.setValue(serieEspecieValorada);
			listboxLista.appendChild(item);
		}


	}

}
