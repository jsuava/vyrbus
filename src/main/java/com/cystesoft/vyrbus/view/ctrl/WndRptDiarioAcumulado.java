package com.cystesoft.vyrbus.view.ctrl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;

import org.zkoss.zul.Checkbox;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.Frozen;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listhead;
import org.zkoss.zul.Listheader;
import org.zkoss.zul.Listitem;

import com.cystesoft.vyrbus.model.bean.Servicio;
import com.cystesoft.vyrbus.service.locator.ServiceLocator;
import com.cystesoft.vyrbus.service.util.Constantes;
import com.cystesoft.vyrbus.service.util.MyTime;
import com.cystesoft.vyrbus.service.util.Util;
import com.cystesoft.vyrbus.service.util.UtilData;
import com.cystesoft.vyrbus.view.ui.DlgMessage;
import com.cystesoft.vyrbus.view.ui.WndBase;

/**
 * 
 * @author JABANTO
 *
 */
public class WndRptDiarioAcumulado extends WndBase implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
   
	private Datebox dbxFechaInicial;
	private Datebox dbxFechaFinal;
	private Combobox cmbServicio;
	private Combobox cmbrutaItinerario;
	private Listbox lstbxDiarioAcumulado = new Listbox();
	private Checkbox cbxCuadroIngresos;
	private Checkbox cbxDetallado;
	
	/*
	 * (non-Javadoc)
	 * @see com.tepsa.sisvyr.view.ui.WndBase#initComponents()
	 */
	@Override
	public void initComponents() {
		dbxFechaInicial=(Datebox)this.getFellow("dbxFechaInicial");
		dbxFechaFinal=(Datebox)this.getFellow("dbxFechaFinal");
		cmbServicio=(Combobox)this.getFellow("cmbServicio");
		cmbrutaItinerario=(Combobox)this.getFellow("cmbrutaItinerario");
		cbxCuadroIngresos=(Checkbox)this.getFellow("cbxCuadroIngresos");
		cbxDetallado=(Checkbox)this.getFellow("cbxDetallado");
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.tepsa.sisvyr.view.ui.WndBase#onCreate()
	 */
	@Override
	public void onCreate() throws Exception {
		MyTime time = new MyTime();
		
		UtilData.cargarDataCombo(cmbServicio, Servicio.class, true);
		UtilData.cargarRutaItinerario(cmbrutaItinerario, true);
		
		dbxFechaInicial.setValue(Util.primerDiaDelMes());
		dbxFechaFinal.setValue(Constantes.FORMAT_DATE.parse(time.dateServer()));
		
		cmbServicio.setSelectedIndex(0);
		cmbrutaItinerario.setSelectedIndex(0);
	}
	
	/**
	 * 
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public void cargarDiarioAcumulado() throws Exception{
		try{
			lstbxDiarioAcumulado.getItems().clear();
//			Util.limpiarListbox(lstbxDiarioAcumulado);
			
			lstbxDiarioAcumulado.detach();
			lstbxDiarioAcumulado = new Listbox();
			lstbxDiarioAcumulado.setHeight("470px");
			
			Frozen frozen=new Frozen();
			frozen.setColumns(4);
			frozen.setStyle("background: #4285F4");
			lstbxDiarioAcumulado.appendChild(frozen);
			
//			<frozen columns="1" style="background: #99D9EA" >
//			<div style="padding: 0 10px;" /></frozen>
//			

			ArrayList<Object> lstReporte = new ArrayList<Object>();
			Date fechaInicial = dbxFechaInicial.getValue();
			Date fechaFinal = dbxFechaFinal.getValue();
			long idServicio=0;
			if(cmbServicio.getSelectedItem().getValue() instanceof Servicio)
				idServicio = (Long) cmbServicio.getSelectedItem().getValue();
			int ruta = Integer.valueOf(cmbrutaItinerario.getSelectedItem().getValue().toString());
			boolean mostrarCuadroIngresos =  cbxCuadroIngresos.isChecked();
			boolean detallado = cbxDetallado.isChecked();
			String[] atributoColumnas = null;

			if(detallado){
//				lstReporte = this.getServicios().consultarDetallado_DiarioAcumulado(fechaInicial,fechaFinal, idServicio, ruta, mostrarCuadroIngresos);
				lstReporte=ServiceLocator.getReportesManager().diarioAcumuladoDetallado(fechaInicial, fechaFinal, idServicio, ruta, mostrarCuadroIngresos);
			}else{
				lstReporte=ServiceLocator.getReportesManager().diarioAcumulado(fechaInicial, fechaFinal, idServicio, ruta, mostrarCuadroIngresos);				
			}
			
			/*genera columnas de a cuerdo a las agencias de partida y llegada recuperador en la consulta */
			if(lstReporte.size() > 0){
				ArrayList<Object> lstPrimeraFila = (ArrayList<Object>) lstReporte.get(0);
				atributoColumnas = new String[lstPrimeraFila.size()];

				for(int a = 0; a < lstPrimeraFila.size(); a ++){
						switch(a){
							case 0:
								atributoColumnas[a] = "width:135px;";
								break;
							case 1:
								atributoColumnas[a] = "width:40px; align:center;";
								break;
							case 2:
								if(detallado){
									atributoColumnas[a] = "width:160px;";																		
								}else{
									atributoColumnas[a] = "width:75px;";
								}
								break;
							case 3:
								if(cmbrutaItinerario.getSelectedItem().getValue().toString().equals(2)){
									atributoColumnas[a] = "width:200px;";
								}else{
									atributoColumnas[a] = "width:135px;";
								}
								break;
									
							default:
								if(!detallado && a == 4){
									atributoColumnas[a] = "width:40px; align:center;";
								}else{
									atributoColumnas[a] = "width:110px; align:right;";
								}
								break;
							}
						}
				}
			llenarListbox(lstbxDiarioAcumulado, lstReporte, atributoColumnas);
			this.appendChild(lstbxDiarioAcumulado);
			
		}catch(Exception ex){
			DlgMessage.error(this.getClass().getSimpleName()+" "+ex.getMessage());
		}	
	}
	
	
	public void exportarExcel(){
		if(lstbxDiarioAcumulado.getItems().size()>0)
			Util.exportarExcel(lstbxDiarioAcumulado, "Diario Acumulado");
	}
	
	@SuppressWarnings("unchecked")
	public static void llenarListbox(Listbox oListbox, ArrayList<Object> lstDatos, String[] atributoColumnas){
//		Util.limpiarListbox(oListbox);
		
		for(int f =0; f < lstDatos.size(); f++){
			
			ArrayList<Object> lstFila = (ArrayList<Object>) lstDatos.get(f);
			Listhead oListhead = new Listhead();
			Listitem oListitem = new Listitem();

			oListhead.setSizable(true);

			for(int c = 0; c < lstFila.size(); c++){
				String valorCelda = (lstFila.get(c) == null  || lstFila.get(c).equals("") ? "" : lstFila.get(c).toString());

				Listheader oListheader = new Listheader(valorCelda);
				Listcell oListcell = new Listcell(valorCelda);
								
				if(f == 0){
					oListheader.setAlign("left");
					oListheader.setStyle("color: #ffffff;");
				
					if(atributoColumnas != null && c < atributoColumnas.length && atributoColumnas[c] != null){
						String[] atributos = atributoColumnas[c].split(";");

						for(int a = 0; a < atributos.length; a++){
							String[] atributo = atributos[a].split(":");

							if(atributo.length > 1){
								String nombre = atributo[0].trim();
								String valor = atributo[1].trim();
	
								if(nombre.equals("width")){
									oListheader.setWidth(valor);								
								}else if(nombre.equals("height")){
									oListheader.setHeight(valor);								
								}if(nombre.equals("align")){
									oListheader.setAlign(valor);
								}else if(nombre.equals("sort")){
									oListheader.setSort(valor);
								}else if(nombre.equals("visible") && valor.equals("false")){
									oListheader.setVisible(false);	
								}else{
									oListheader.setAttribute(nombre, valor);
								}
							}
						}
					}

					oListhead.appendChild(oListheader);
				}else{
					if(Util.isNumeric(valorCelda)){
						oListcell.setStyle("font-size:11px !important");
					}else if (Util.isDecimal(valorCelda)){
						oListcell.setStyle("font-size:11px !important");
					}else if(c==2){//Para la columna que contiene la fecha y/o hora de partida del servicio.
						oListcell.setStyle("font-size:11px !important");	
					}
					oListitem.appendChild(oListcell);
				}
			}

			if(f == 0){
				oListbox.appendChild(oListhead);
			}else{
				oListbox.appendChild(oListitem);
			}
		}
	}
}


