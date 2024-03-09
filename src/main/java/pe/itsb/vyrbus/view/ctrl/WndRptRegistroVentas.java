/**
 * Proyecto		: SISVYR
 * Sistema		: Sistema de Ventas y Reservas
 * Descripci�n	:
 * Autor		: Jos� Abanto
 * Fecha		: 9 set. 2022
 * Hora			: 17:24:14
 */
package pe.itsb.vyrbus.view.ctrl;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Session;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Comboitem;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Radio;

import pe.itsb.vyrbus.service.locator.ServiceLocator;
import pe.itsb.vyrbus.service.mappers.VentasPiloto;
import pe.itsb.vyrbus.service.util.Constantes;
import pe.itsb.vyrbus.service.util.Util;
import pe.itsb.vyrbus.service.util.UtilFlag;
import pe.itsb.vyrbus.view.ui.DlgMessage;
import pe.itsb.vyrbus.view.ui.WndBase;

/**
 * @author Marco
 *
 */
public class WndRptRegistroVentas extends WndBase  {

	private static final long serialVersionUID = 1L;

	private Datebox dtbxFechaInicio;
	private Datebox dtbxFechaFin;
	private Combobox cmbTipoComprobante;
	private Listbox lbxVentas;
	private Radio rdEncomiendas;
	private Radio rdPasajeros;
	private Radio rdAmbos;
	private boolean isConnectionTranscar = false;

	private static String[] tipoComprobante = {Constantes.COMBO_LABEL_TODOS, "BOLETA ELECTRONICA",
			"FACTURA ELECTRONICA"};

	List<VentasPiloto> lstVentas = null;
	List<VentasPiloto> lstVentasEncomiendas = null;

	@Override
	public void onCreate() throws Exception {
		// TODO Auto-generated method stub
		//Valida la conexión con transcar
		isConnectionTranscar = UtilFlag.isConeccionTranscar();
		dtbxFechaInicio.setValue(new Date());
		dtbxFechaFin.setValue(new Date());
		onLoadTipoComprobante();
		rdPasajeros.setChecked(true);
		rdEncomiendas.setDisabled(!isConnectionTranscar);
		rdAmbos.setDisabled(!isConnectionTranscar);
		
	}

	@Override
	public void initComponents() {
		dtbxFechaInicio=(Datebox)this.getFellow("dtbxFechaInicio");
		dtbxFechaFin=(Datebox)this.getFellow("dtbxFechaFin");
		cmbTipoComprobante=(Combobox)this.getFellow("cmbTipoComprobante");
		lbxVentas=(Listbox)this.getFellow("lbxVentas");
		rdEncomiendas = (Radio)this.getFellow("rdEncomiendas");
		rdPasajeros = (Radio)this.getFellow("rdPasajeros");
		rdAmbos = (Radio)this.getFellow("rdAmbos");

	}

	private void onLoadTipoComprobante(){
		for(String tipoComprobante : getTipoComprobante()){
			Comboitem comboitem = new Comboitem(tipoComprobante);
			cmbTipoComprobante.appendChild(comboitem);
		}
		cmbTipoComprobante.setSelectedIndex(0);
	}

	public static String[] getTipoComprobante() {
		return tipoComprobante;
	}


	public void buscar() throws Exception{
		Util.limpiarListbox(lbxVentas);

		String fechaInicio=null, fechaFin=null;

		if(dtbxFechaInicio.getValue()!=null)
			fechaInicio=Constantes.FORMAT_DATE.format(dtbxFechaInicio.getValue());
		if(dtbxFechaFin.getValue()!=null)
			fechaFin=Constantes.FORMAT_DATE.format(dtbxFechaFin.getValue());

		if(rdEncomiendas.isChecked()) {
			lstVentas = null;
			lstVentas = ServiceLocator.getTranscarWebManager().buscarRegistroVentas(fechaInicio, fechaFin);
			listarVentas(lstVentas);
			if(lstVentas.size()>0)
				listarVentas(lstVentas);
			else {
				DlgMessage.information("No se encontraron ventas para los criterios ingresados.");
				return;
			}

		}else if(rdPasajeros.isChecked()){
			lstVentas = ServiceLocator.getVentaPasajesManager().buscarRegistroVentas(fechaInicio, fechaFin);
			if(lstVentas.size()>0)
				listarVentas(lstVentas);
			else {
				DlgMessage.information("No se encontraron ventas para los criterios ingresados.");
				return;
			}
		}else {
			lstVentasEncomiendas = null;
			lstVentasEncomiendas = ServiceLocator.getTranscarWebManager().buscarRegistroVentas(fechaInicio, fechaFin);
			lstVentas = null;
			lstVentas = ServiceLocator.getVentaPasajesManager().buscarRegistroVentas(fechaInicio, fechaFin);

			if(lstVentasEncomiendas.size()>0) {
				for (VentasPiloto ventaEncomienda : lstVentasEncomiendas) {
					lstVentas.add(ventaEncomienda);
				}
				listarVentas(lstVentas);
			}else {
				if(lstVentas.size()>0) {
					listarVentas(lstVentas);
				}else {
					DlgMessage.information("No se encontraron ventas para los criterios ingresados.");
					return;
				}

			}

		}


	}


	public void listarVentas(List<VentasPiloto>lstVentas) {
		String style="font-size:12px !important";

		int i=0;
		for(VentasPiloto regVentas:lstVentas){
			Listitem item=new Listitem();
			i++;
			Listcell cell=new Listcell();
			cell=new Listcell(String.valueOf(i));
			item.appendChild(cell);

			cell=new Listcell(Util.DatetoString(regVentas.getFechaCompra(), Constantes.DATE_FORMAT));
			cell.setStyle(style);
			item.appendChild(cell);

			cell=new Listcell(regVentas.getTipoDocumentoSunat());
			cell.setStyle(style);
			item.appendChild(cell);


			cell=new Listcell(regVentas.getSerie());
			cell.setStyle(style);
			item.appendChild(cell);

			cell=new Listcell(regVentas.getNumero());
			cell.setStyle(style);
			item.appendChild(cell);

			cell=new Listcell(regVentas.getDni());
			cell.setStyle(style);
			item.appendChild(cell);

			cell=new Listcell(regVentas.getNombres());
			cell.setStyle(style);
			item.appendChild(cell);

			cell=new Listcell(Util.toNumberFormat(regVentas.getExonerado(), 2));
			cell.setStyle(style);
			item.appendChild(cell);

			cell=new Listcell(Util.toNumberFormat(regVentas.getVenta(), 2));
			cell.setStyle(style);
			item.appendChild(cell);

			cell=new Listcell(Util.toNumberFormat(regVentas.getIgv(), 2));
			cell.setStyle(style);
			item.appendChild(cell);

			cell=new Listcell(Util.toNumberFormat(regVentas.getTotal(), 2));
			cell.setStyle(style);
			item.appendChild(cell);

			cell=new Listcell(regVentas.getDestino());
			cell.setStyle(style);
			item.appendChild(cell);

			cell=new Listcell(regVentas.getAsiento().equals("-") ? "" : regVentas.getAsiento());
			cell.setStyle(style);
			item.appendChild(cell);

			cell=new Listcell(regVentas.getTipoComprobante().getId().toString());
			cell.setStyle(style);
			item.appendChild(cell);

			cell=new Listcell(regVentas.getTipoMovimiento().getId().toString());
			cell.setStyle(style);
			item.appendChild(cell);

			item.setValue(regVentas);

			lbxVentas.appendChild(item);
		}
	}



	public void exportarExcel(){
		Session session = getDesktop().getSession();
		String fecha = Util.DatetoString(new Date(), Constantes.DATE_TIME_FORMAT);

		HttpSession httpSession = (HttpSession)session.getNativeSession();
		httpSession.setAttribute("parcialPath",Constantes.DIRECTORY_EXCEL+"RegistroVentas.xls");
		httpSession.setAttribute("lstVentas", lstVentas);
		httpSession.setAttribute("empresa", ""); //Constantes.empresa);
		httpSession.setAttribute("ruc", ""); //Constantes.ruc);
		httpSession.setAttribute("desde", Constantes.FORMAT_DATE.format(dtbxFechaInicio.getValue()));
		httpSession.setAttribute("hasta", Constantes.FORMAT_DATE.format(dtbxFechaFin.getValue()));
		httpSession.setAttribute("fechaEmision", fecha);
		httpSession.setAttribute("rubro", (rdEncomiendas.isChecked() ? "ENCOMIENDAS" : rdPasajeros.isChecked() ?  "PASAJEROS" : "PASAJEROS / ENCOMIENDAS" ));
		Executions.sendRedirect("/exportXlsRegistroVentas.htm");

	}


}
