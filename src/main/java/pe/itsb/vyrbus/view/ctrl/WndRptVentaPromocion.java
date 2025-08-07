
package pe.itsb.vyrbus.view.ctrl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.zkoss.zul.Combobox;
import org.zkoss.zul.Comboitem;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.Label;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listfoot;
import org.zkoss.zul.Listfooter;
import org.zkoss.zul.Listitem;

import pe.itsb.vyrbus.model.bean.Agencia;
import pe.itsb.vyrbus.model.bean.Promocion;
import pe.itsb.vyrbus.model.bean.Usuario;
import pe.itsb.vyrbus.model.bean.VentaPasaje;
import pe.itsb.vyrbus.service.locator.ServiceLocator;
import pe.itsb.vyrbus.service.util.Constantes;
import pe.itsb.vyrbus.service.util.Messages;
import pe.itsb.vyrbus.service.util.Util;
import pe.itsb.vyrbus.service.util.UtilData;
import pe.itsb.vyrbus.view.ui.DlgMessage;
import pe.itsb.vyrbus.view.ui.WndBase;

/**
 * @author JABANTO
 *
 */
public class WndRptVentaPromocion extends WndBase {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	private Datebox dtbxFechaInicio;
	private Datebox dtbxFechaFinal;
	private Combobox cmbPromocion;
	private Listbox lstVentasPromocion;
	private Combobox cmbTipoDescuento;
	private Combobox cmbAgencia;
	private Combobox cmbUsuario;
	private Listbox lstVentasPromocionDeta;


	List<Agencia> lstAgencias;
	List<Usuario> lstUsuarios;

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.view.ui.WndBase#initComponents()
	 */
	@Override
	public void initComponents() {
		// TODO Auto-generated method stub
		dtbxFechaInicio=(Datebox)this.getFellow("dtbxFechaInicio");
		dtbxFechaFinal=(Datebox)this.getFellow("dtbxFechaFinal");
		cmbPromocion=(Combobox)this.getFellow("cmbPromocion");
		lstVentasPromocion=(Listbox)this.getFellow("lstVentasPromocion");
		cmbTipoDescuento=(Combobox)this.getFellow("cmbTipoDescuento");
		cmbAgencia = (Combobox)this.getFellow("cmbAgencia");
		cmbUsuario = (Combobox)this.getFellow("cmbUsuario");
		lstVentasPromocionDeta=(Listbox)this.getFellow("lstVentasPromocionDeta");
	}

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.view.ui.WndBase#onCreate()
	 */
	@Override
	public void onCreate() throws Exception {
		// TODO Auto-generated method stub
		dtbxFechaInicio.setValue(new Date());
		dtbxFechaFinal.setValue(new Date());
		cargarPromociones();


		/* Carga tipo descuento*/
		UtilData.cargarGenericData(cmbTipoDescuento, true);
		Comboitem comboitem=new Comboitem();
		comboitem.setLabel("SOLES");
		comboitem.setValue("S");
		cmbTipoDescuento.appendChild(comboitem);
		comboitem=new Comboitem();
		comboitem.setLabel("PORCENTAJE");
		comboitem.setValue("P");
		cmbTipoDescuento.appendChild(comboitem);
	}

	/**
	 * Carga el combo con las promociones, segun el rango de fecha que se haya seleccionado.
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public void cargarPromociones() throws Exception{
		try{
			if(lstVentasPromocion.getItemCount()>0)
				Util.limpiarListbox(lstVentasPromocion);
			if(lstVentasPromocionDeta.getItemCount()>0)
				Util.limpiarListbox(lstVentasPromocionDeta);
			
			Util.limpiarCombobox(cmbPromocion);
			String fechaInicio=Constantes.FORMAT_DATE.format(dtbxFechaInicio.getValue());
			String fechaFin=Constantes.FORMAT_DATE.format(dtbxFechaFinal.getValue());

			List<Object>lstVarios=ServiceLocator.getReportesManager().ventasPromocionLstPromociones(fechaInicio, fechaFin);

			List<Promocion> lstPromociones = (List<Promocion>) lstVarios.get(0);

			lstAgencias = (List<Agencia>) lstVarios.get(1);
			lstUsuarios = (List<Usuario>) lstVarios.get(2);

			UtilData.cargarGenericData(cmbPromocion, true);
			for(Promocion promocion:lstPromociones){
				Comboitem comboitem=new Comboitem();
				comboitem.setLabel(promocion.getDenominacion());
				comboitem.setValue(promocion);
				cmbPromocion.appendChild(comboitem);
			}

			//
			cargarAgencias();

			//
			cargarUsuarios(null);


		}catch (Exception ex){
			ex.getStackTrace();
			DlgMessage.error(Messages.getString(ex.getMessage()));
		}
	}



	public void onChange_cmbAgencia(){
		try {

			Integer agencia_id = null;
			if(cmbAgencia.getSelectedItem().getValue() instanceof Agencia)
				agencia_id = ((Agencia)cmbAgencia.getSelectedItem().getValue()).getId();

			cargarUsuarios(agencia_id);

		} catch (Exception ex) {
			ex.getStackTrace();
			DlgMessage.error(Messages.getString(ex.getMessage()));
		}
	}

	/**
	 *
	 * @throws Exception
	 */
	private void cargarAgencias()throws Exception{
		Util.limpiarCombobox(cmbAgencia);
		UtilData.cargarGenericData(cmbAgencia, true);
		for(Agencia agencia:lstAgencias){
			Comboitem comboitem=new Comboitem();
			comboitem.setLabel(agencia.getDenominacion());
			comboitem.setValue(agencia);
			cmbAgencia.appendChild(comboitem);
		}
	}

	/**
	 *
	 * @param agencia_id
	 * @throws Exception
	 */
	private void cargarUsuarios(Integer agencia_id)throws Exception{
		Util.limpiarCombobox(cmbUsuario);
		UtilData.cargarGenericData(cmbUsuario, true);
		for(Usuario usuario :lstUsuarios){
			Comboitem comboitem=new Comboitem();
			comboitem.setLabel(usuario.toString());
			comboitem.setValue(usuario);

			if(agencia_id==null || (agencia_id!=null && usuario.getAgencia().getId().intValue()==agencia_id))
				cmbUsuario.appendChild(comboitem);
		}
	}


	/**
	 * Busca las ventas asociadas a alguna promcion.
	 */
	public void buscarVentasPromocion(){
		try {
			Util.limpiarListbox(lstVentasPromocion);
			Util.limpiarListbox(lstVentasPromocionDeta);
			String style="font-size:11px !important";
			String fechaInicio=Constantes.FORMAT_DATE.format(dtbxFechaInicio.getValue());
			String fechaFin=Constantes.FORMAT_DATE.format(dtbxFechaFinal.getValue());
//			Long idPromocion=null;
			String idPromocion=null;
			String tipoDescuento=null;
			Integer agencia_id=null, usuario_id=null;

			if(cmbPromocion.getSelectedIndex()>0)
				idPromocion=((Promocion)cmbPromocion.getSelectedItem().getValue()).getTipoDescuento();
//				idPromocion=((Promocion)cmbPromocion.getSelectedItem().getValue()).getId();
			if(cmbTipoDescuento.getSelectedIndex()>0)
				tipoDescuento=cmbTipoDescuento.getSelectedItem().getValue();
			if(cmbAgencia.getSelectedItem().getValue() instanceof Agencia)
				agencia_id = ((Agencia)cmbAgencia.getSelectedItem().getValue()).getId();
			if(cmbUsuario.getSelectedItem().getValue() instanceof Usuario)
				usuario_id = ((Usuario)cmbUsuario.getSelectedItem().getValue()).getId();
			ArrayList<Promocion>lstVtaPromo=ServiceLocator.getReportesManager().ventasPromocion(fechaInicio, fechaFin, idPromocion,tipoDescuento, agencia_id, usuario_id);

			Double totalDescuento=.00, totalVenta=.00;
			int cantidad=0;
			for(Promocion promocion:lstVtaPromo){
				Listitem item=new Listitem();
				Listcell cell=new Listcell();
				item.appendChild(cell);
				cell=new Listcell(promocion.getAgencia().getDenominacion());
				item.appendChild(cell);
				cell=new Listcell(promocion.getDenominacion());
				item.appendChild(cell);
				cell=new Listcell(promocion.getBeneficio());
				cell.setStyle(style);
				item.appendChild(cell);
				cell=new Listcell(promocion.getCantidadViajesPasajero().toString());
				cell.setStyle(style);
				item.appendChild(cell);
				cell=new Listcell(Util.toNumberFormat(promocion.getTotalDescuento(), 2));
				cell.setStyle(style);
				item.appendChild(cell);
				cell=new Listcell(Util.toNumberFormat(promocion.getTotalVenta(), 2));
				cell.setStyle(style);
				item.appendChild(cell);

				item.setValue(promocion);
				lstVentasPromocion.appendChild(item);

				totalDescuento+=promocion.getTotalDescuento();
				totalVenta+=promocion.getTotalVenta();
				cantidad+=Integer.valueOf(promocion.getCantidadViajesPasajero());
			}

			/* AGREGA TOTALES AL FINAL DE LA LISTA*/
			String styleTotales="font-size:12px !important; color:red;font-weight: bold;";
			Label label = null;
			Listfoot listfoot= new  Listfoot();
			label = new Label("TOTALES");
			label.setStyle(styleTotales);
			Listfooter listfooter = new Listfooter();
			listfooter.setSpan(3);
			listfooter.appendChild(label);
			listfoot.appendChild(listfooter);
			label = new Label(Integer.toString(cantidad));
			label.setStyle(styleTotales);
			listfooter = new Listfooter();
			listfooter.setStyle("text-align:right");
			listfooter.appendChild(label);
			listfoot.appendChild(listfooter);
			label = new Label(Util.toNumberFormat(totalDescuento, 2));
			label.setStyle(styleTotales);
			listfooter = new Listfooter();
			listfooter.setStyle("text-align:right;");
			listfooter.appendChild(label);
			listfoot.appendChild(listfooter);
			label = new Label(Util.toNumberFormat(totalVenta, 2));
			label.setStyle(styleTotales);
			listfooter = new Listfooter();
			listfooter.setStyle("text-align:right;");
			listfooter.appendChild(label);
			listfoot.appendChild(listfooter);
			lstVentasPromocion.appendChild(listfoot);

		} catch (Exception e) {
			e.getStackTrace();
			DlgMessage.error(Messages.getString(e.getMessage()));
		}
	}

	/**
	 * Busca el detalle de la promocion seleccionada.
	 * @throws Exception
	 */
	public void buscarVentasPromocionDetalle() throws Exception{
		try{
			Util.limpiarListbox(lstVentasPromocionDeta);
			String fechaInicio=Constantes.FORMAT_DATE.format(dtbxFechaInicio.getValue());
			String fechaFin=Constantes.FORMAT_DATE.format(dtbxFechaFinal.getValue());

			int cant=0;
			String style="font-size:11px !important;";

			for(Listitem itemSummary: lstVentasPromocion.getSelectedItems()){
				Promocion promocion=itemSummary.getValue();
				ArrayList<VentaPasaje>lstVentasPromo=ServiceLocator.getReportesManager().ventasPromocionDeta(
													fechaInicio, fechaFin, promocion.getTipoDescuento(),
													promocion.getAgencia().getId());

				for(VentaPasaje ventaPasaje:lstVentasPromo){
					cant++;
					Listitem item=new Listitem();
					Listcell cell=null;

					cell=new Listcell(Integer.toString(cant));
					cell.setStyle(style);
					item.appendChild(cell);
					cell=new Listcell(Constantes.FORMAT_DATE.format(ventaPasaje.getFechaLiquidacion()));
					cell.setStyle(style);
					item.appendChild(cell);
					cell=new Listcell(ventaPasaje.getNumeroBoleto());
					cell.setStyle(style);
					item.appendChild(cell);
					cell=new Listcell(ventaPasaje.getPasajero().getNombresApellidos());
					item.appendChild(cell);
					cell=new Listcell(ventaPasaje.getRuta().toString());
					item.appendChild(cell);
					cell=new Listcell(ventaPasaje.getServicio().getDenominacion());
					item.appendChild(cell);
					cell=new Listcell(ventaPasaje.getFechaPartida()!=null?Constantes.FORMAT_DATE.format(ventaPasaje.getFechaPartida()):"");
					cell.setStyle(style);
					item.appendChild(cell);
					cell=new Listcell(ventaPasaje.getHoraPartida()!=null?ventaPasaje.getHoraPartida():"");
					cell.setStyle(style);
					item.appendChild(cell);
					cell=new Listcell(Util.toNumberFormat(ventaPasaje.getTarifa(),2));
					cell.setStyle(style+"text-align: right;");
					item.appendChild(cell);
					cell=new Listcell(Util.toNumberFormat(ventaPasaje.getDescuento(),2));
					cell.setStyle(style+"text-align: right;");
					item.appendChild(cell);
					cell=new Listcell(Util.toNumberFormat(ventaPasaje.getImportePagado(),2));
					cell.setStyle(style+"text-align: right;");
					item.appendChild(cell);
					cell=new Listcell(ventaPasaje.getUsuario().toString());
					item.appendChild(cell);
					cell=new Listcell(ventaPasaje.getAgencia().getDenominacion());
					item.appendChild(cell);
					cell=new Listcell(ventaPasaje.getObservaciones());
					item.appendChild(cell);

					lstVentasPromocionDeta.appendChild(item);
				}
			}

			if(lstVentasPromocionDeta.getItems().size()>0)
				lstVentasPromocionDeta.setSelectedIndex(0);

		}catch(Exception ex){
			ex.getStackTrace();
			DlgMessage.error(Messages.getString(ex.getMessage()));
		}

	}


	public void exportar(){
		if(lstVentasPromocion.getItems().size()>0){
			Util.exportarExcel(lstVentasPromocion, "VENTAS_PROMOCION_");
		}
	}

	public void exportarDetalle(){
		if(lstVentasPromocionDeta.getItems().size()>0){
			Util.exportarExcel(lstVentasPromocionDeta, "VENTAS_PROMOCION_DETALLE_");
		}
	}

}
