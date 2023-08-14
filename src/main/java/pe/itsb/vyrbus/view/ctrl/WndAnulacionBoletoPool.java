/**
 * Proyecto		: SISVYR
 * Sistema		: Sistema de Ventas y Reservas
 * Descripción	:
 * Autor		: José Abanto
 * Fecha		: 04/10/2016
 * Hora			: 11:21:05
 */
package pe.itsb.vyrbus.view.ctrl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.TreeMap;

import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Comboitem;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listfoot;
import org.zkoss.zul.Listfooter;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Toolbarbutton;

import pe.itsb.vyrbus.model.bean.Agencia;
import pe.itsb.vyrbus.model.bean.Usuario;
import pe.itsb.vyrbus.model.bean.VentaPool;
import pe.itsb.vyrbus.service.locator.ServiceLocator;
import pe.itsb.vyrbus.service.util.Constantes;
import pe.itsb.vyrbus.service.util.Messages;
import pe.itsb.vyrbus.service.util.RESTCiva;
import pe.itsb.vyrbus.service.util.Util;
import pe.itsb.vyrbus.service.util.UtilData;
import pe.itsb.vyrbus.service.util.WSCruzdelsur;
import pe.itsb.vyrbus.view.ui.DlgMessage;
import pe.itsb.vyrbus.view.ui.Events;
import pe.itsb.vyrbus.view.ui.WndBase;

/**
 * @author jabanto
 *
 */
public class WndAnulacionBoletoPool extends WndBase{

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	private Textbox txtNumeroBoleto;
	private Listbox ltxResultados;
	private Datebox dtbxFechaInicio;
	private Datebox dtbxFechaFin;
	private Combobox cmbLocal;
	private Combobox cmbCounter;

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.view.ui.WndBase#onCreate()
	 */
	@Override
	public void onCreate() throws Exception {
		dtbxFechaInicio.setValue(new Date());
		dtbxFechaFin.setValue(new Date());

		UtilData.cargarAgenciaXtipoAgencia(cmbLocal, Constantes.ID_TIPAGE_TEPSA, true);
		/*Selecciona la agencia por defecto*/
		for(Comboitem comboitem : cmbLocal.getItems()){
			if(comboitem.getValue()!=null && ((Agencia)comboitem.getValue()).getId().intValue()==getAgencia().getId().intValue())
				cmbLocal.setSelectedItem(comboitem);
		}
		/*Cargando las counetes*/
		onLoadCounters();


		cmbLocal.addEventListener(Events.ON_SELECT,new EventListener<Event>() {
			@Override
			public void onEvent(Event event) throws Exception {
				onLoadCounters();
			}
		});
	}

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.view.ui.WndBase#initComponents()
	 */
	@Override
	public void initComponents() {
		txtNumeroBoleto=(Textbox)this.getFellow("txtNumeroBoleto");
		ltxResultados=(Listbox)this.getFellow("ltxResultados");
		dtbxFechaInicio=(Datebox)this.getFellow("dtbxFechaInicio");
		dtbxFechaFin=(Datebox)this.getFellow("dtbxFechaFin");
		cmbLocal=(Combobox)this.getFellow("cmbLocal");
		cmbCounter=(Combobox)this.getFellow("cmbCounter");
	}


	public void buscar()throws Exception{
		try {
			Util.limpiarListbox(ltxResultados);
			List<VentaPool> result= new ArrayList<>();
			if(!(txtNumeroBoleto.getText().trim().isEmpty())){
				TreeMap<String, Object>criteriosBusqueda= new TreeMap<>();
				criteriosBusqueda.put("numeroBoletoTepsa", txtNumeroBoleto.getText().toUpperCase().trim());
				result=ServiceLocator.getVentaPoolManager().buscarPorX(criteriosBusqueda, null);
			}else{
				Integer agenciaId=null;
				Integer usuarioId=null;
				String fechaInicio=Constantes.FORMAT_DATE.format(dtbxFechaInicio.getValue());
				String fechaFin=Constantes.FORMAT_DATE.format(dtbxFechaFin.getValue());
				if(cmbLocal.getSelectedIndex()>0 && cmbLocal.getSelectedItem().getValue() instanceof Agencia)
					agenciaId=((Agencia)cmbLocal.getSelectedItem().getValue()).getId();
				if(cmbCounter.getSelectedIndex()>0 && cmbCounter.getSelectedItem().getValue() instanceof Usuario)
					usuarioId=((Usuario)cmbCounter.getSelectedItem().getValue()).getId();
				result=ServiceLocator.getVentaPoolManager().buscarVentas(fechaInicio, fechaFin, agenciaId, usuarioId);
			}

			String styleFont11="font-size:11px !important;";
			Double total=.00;
			for(VentaPool ventaPool:result){
				total+=ventaPool.getImportePagado();
				String style=ventaPool.getEstadoRegistro().equals(Constantes.VALUE_INACTIVO)?"color:red;":"";
				Listitem item= new Listitem();
				if(ventaPool.getOperador().equals(Constantes.OPERADO_POR_CRUZ_DEL_SUR))
					item.setImage("/resources/mp_cruzdelsur.png");
				else if (ventaPool.getOperador().equals(Constantes.OPERADO_CIVA))
					item.setImage("/resources/mp_excluciva.png");
				Listcell cell= new Listcell(ventaPool.getNumeroBoletoTepsa());
				cell.setStyle(styleFont11+style);
				item.appendChild(cell);
				cell= new Listcell(ventaPool.getPasajero().toString());
				cell.setStyle(style);
				item.appendChild(cell);
				cell= new Listcell(ventaPool.getRuta());
				cell.setStyle(style);
				item.appendChild(cell);
				cell= new Listcell(Constantes.FORMAT_DATE.format(ventaPool.getFechaPartida()));
				cell.setStyle(styleFont11+style);
				item.appendChild(cell);
				cell= new Listcell(ventaPool.getHoraPartida());
				cell.setStyle(styleFont11+style);
				item.appendChild(cell);
				cell= new Listcell(ventaPool.getServicio());
				cell.setStyle(style);
				item.appendChild(cell);
				cell= new Listcell(Util.toNumberFormat(ventaPool.getImportePagado(),2));
				cell.setStyle(styleFont11+style);
				item.appendChild(cell);
				if(ventaPool.getEstadoRegistro().equals(Constantes.VALUE_ACTIVO)){
					cell= new Listcell();
					Toolbarbutton toolbarbutton = new Toolbarbutton();
					toolbarbutton.setLabel("Anular");
					toolbarbutton.setAttribute(VentaPool.class.getName(), ventaPool);
					toolbarbutton.addEventListener(Events.ON_CLICK,new EventListener<Event>() {
						@Override
						public void onEvent(final Event event) throws Exception {
							try {

								Messagebox.show(Messages.getString("WndLiquidacionDiariaVentas.information.confirmarAnulacion"), DlgMessage.NOMBREAPLICACION, DlgMessage.BTN_YESNO, Messagebox.QUESTION,DlgMessage.BTN_DEFAULT_NO, new EventListener<Event>() {
									@Override
									public void onEvent(Event e){
										try {
											if(e.getName().equals("onYes")){
												anularBoleto((VentaPool)event.getTarget().getAttribute(VentaPool.class.getName()));
												buscar();
											}
										} catch (Exception e2) {
											e2.printStackTrace();
											DlgMessage.error(e2.getMessage());
										}
									}
								});
							} catch (Exception e) {
								e.printStackTrace();
								DlgMessage.error(e.getMessage());
							}
						}
					});
					cell.appendChild(toolbarbutton);
					item.appendChild(cell);
				}
				item.setValue(ventaPool);
				ltxResultados.appendChild(item);
			}

			/*Agregando el total*/
			Listfoot listfoot= new Listfoot();
			Listfooter listfooter= new Listfooter("TOTAL");
			listfooter.setSpan(7);
			listfoot.appendChild(listfooter);
			listfooter= new Listfooter(Util.toNumberFormat(total, 2));
			listfooter.setAlign("right");
			listfoot.appendChild(listfooter);
			ltxResultados.appendChild(listfoot);
		} catch (Exception e) {
			e.printStackTrace();
			DlgMessage.error(e.getMessage());
		}
	}

	/**
	 * Realiza la anulacion del Boleto
	 * @param ventaPool : Instacia del Objeto VentaPool.
	 * @throws Exception
	 */
	private void anularBoleto(VentaPool ventaPool)throws Exception{
		if(ventaPool.getOperador().equals(Constantes.OPERADO_POR_CRUZ_DEL_SUR)){
			WSCruzdelsur.anularBoleto(ventaPool.getNumeroComprobanteOperador());
			VentaPool oVentaPool=ServiceLocator.getVentaPoolManager().buscarPorId(ventaPool.getId());
			/*realiza la anulacion en nuestra Base de datos*/
			oVentaPool.setEstadoRegistro(Constantes.VALUE_INACTIVO);
			oVentaPool.setImportePagado(.00);
			UtilData.auditarRegistro(oVentaPool, true, getUsuario(), Executions.getCurrent());
			ServiceLocator.getVentaPoolManager().actualizar(oVentaPool);
		}else if (ventaPool.getOperador().equals(Constantes.OPERADO_CIVA)){
			RESTCiva.anularBoleto(ventaPool.getNumeroBoletoTepsa());
			/*realiza la anulacion en nuestra Base de datos*/
			VentaPool oVentaPool=ServiceLocator.getVentaPoolManager().buscarPorId(ventaPool.getId());
			oVentaPool.setImportePagado(.00);
			oVentaPool.setEstadoRegistro(Constantes.VALUE_INACTIVO);
			UtilData.auditarRegistro(oVentaPool, true, getUsuario(), Executions.getCurrent());
			ServiceLocator.getVentaPoolManager().actualizar(oVentaPool);
		}
	}

	public void onValidateFecha(){
		if(Util.comparaFechas(dtbxFechaInicio.getValue(), dtbxFechaFin.getValue(), Util.OPER_MAYOR)){
			cmbCounter.getItems().clear();
			cmbCounter.setText("");
			DlgMessage.information(Messages.getString("WndLiquidacionDiariaVentas.information.fechaInicioMenor"));
		}else{
			if(cmbLocal.getSelectedItem().getValue() instanceof Agencia)
				onLoadCounters();
		}
	}

	public void onLoadCounters(){
		try{
			cmbCounter.getItems().clear();
			cmbCounter.setText("");
			Comboitem cmbitem = null;
			if(cmbLocal.getSelectedItem().getValue() instanceof Agencia){
				Agencia agencia = (Agencia)cmbLocal.getSelectedItem().getValue();
				List<Usuario> lstUsuarios = ServiceLocator.getVentaPoolManager().buscarUsuarioPorAgencia(agencia.getId(),
						Util.DatetoString(dtbxFechaInicio.getValue(), Constantes.DATE_FORMAT),
						Util.DatetoString(dtbxFechaFin.getValue(), Constantes.DATE_FORMAT));
				if(lstUsuarios.size()>0){
					cmbitem = new Comboitem(Constantes.COMBO_LABEL_TODOS);
					cmbCounter.appendChild(cmbitem);
					cmbCounter.setSelectedItem(cmbitem);
					for(Usuario usuario : lstUsuarios){
						cmbitem = new Comboitem();
						cmbitem.setLabel(usuario.toString());
						cmbitem.setValue(usuario);
						cmbCounter.appendChild(cmbitem);
					}
					cmbCounter.setDisabled(false);
				}else
					cmbCounter.setDisabled(true);
			}
		}catch(Exception ex){
			ex.printStackTrace();
		}
	}
}
