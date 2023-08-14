package pe.itsb.vyrbus.view.ctrl;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Map.Entry;
import java.util.TreeMap;

import javax.servlet.http.HttpSession;

import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Session;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Comboitem;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.Tree;
import org.zkoss.zul.Treecell;
import org.zkoss.zul.Treechildren;
import org.zkoss.zul.Treecol;
import org.zkoss.zul.Treecols;
import org.zkoss.zul.Treeitem;
import org.zkoss.zul.Treerow;

import pe.itsb.vyrbus.model.bean.PreferenciaAlimentaria;
import pe.itsb.vyrbus.model.bean.VentaPasaje;
import pe.itsb.vyrbus.service.locator.ServiceLocator;
import pe.itsb.vyrbus.service.util.Constantes;
import pe.itsb.vyrbus.service.util.MyTime;
import pe.itsb.vyrbus.view.ui.WndBase;

/**
 *
 * @author JABANTO
 *
 */
public class WndRptCenasRuta extends WndBase implements Serializable {

	private static final long serialVersionUID = 1L;

	private Datebox dbxFechaInicial;
	private Combobox cmbTipoConsulta;
	private Datebox dbxFechaFinal;
	Tree  tree=new Tree();
	Treecols treecols=new Treecols();

//	int TIPCONSUL_TODOS=0;
	int TIPCONSUL_IDA=1;
	int TIPCONSUL_RETORNO=2;
	int TIPCONSUL_PROVINCIAS=3;

	TreeMap<String, Object>totalesServicios=null;
	Treechildren treechildrenRutas=null;

	/*
	 * (non-Javadoc)
	 * @see com.tepsa.sisvyr.view.ui.WndBase#initComponents()
	 */
	@Override
	public void initComponents() {
		dbxFechaInicial=(Datebox)this.getFellow("dbxFechaInicial");
		cmbTipoConsulta=(Combobox)this.getFellow("cmbTipoConsulta");
		dbxFechaFinal=(Datebox)this.getFellow("dbxFechaFinal");
	}

	/*
	 * (non-Javadoc)
	 * @see com.tepsa.sisvyr.view.ui.IBase#onCreate()
	 */
	@Override
	public void onCreate() throws Exception {
		MyTime time=new MyTime();
		dbxFechaInicial.setValue(Constantes.FORMAT_DATE.parse(time.dateServer()));
		dbxFechaFinal.setValue(Constantes.FORMAT_DATE.parse(time.dateServer()));

		cargarTipoConsulta(cmbTipoConsulta);

		cmbTipoConsulta.setSelectedIndex(0);
	}


	public void cargarAvanceXrutas() throws Exception{
		totalesServicios=new TreeMap<>();
//		totalAsientos=0;

		tree.setWidth("810px");
		tree.setHeight("530px");
		tree.setZclass("z-dottree");
		tree.getChildren().clear();
		/*Genera columnas*/
		treecols= new Treecols();
		treecols.appendChild(new Treecol("SERVICIO","","400px"));
		treecols.appendChild(new Treecol("HORA PARTIDA","","130px"));
		treecols.appendChild(new Treecol("CLASE","","170px"));
		treecols.appendChild(new Treecol("CENAS","","90px"));
		((Treecol)treecols.getChildren().get(1)).setAlign("Center");
		((Treecol)treecols.getChildren().get(1)).setTooltiptext("Hora de Partida");
		((Treecol)treecols.getChildren().get(3)).setAlign("Center");
		tree.appendChild(treecols);

		/*Realiza la buscaque de los itinerarios y ventas*/
		String fechaInicial=Constantes.FORMAT_DATE.format(dbxFechaInicial.getValue());
		String fechaFinal=Constantes.FORMAT_DATE.format(dbxFechaFinal.getValue());
		Integer tipoConsulta=cmbTipoConsulta.getSelectedItem().getValue();
//		String transaccion=String.valueOf(cmbTransaccion.getSelectedItem().getValue());

		Treechildren treechildren = new Treechildren();
		Treeitem itemDate = new Treeitem();
		Treerow treerow = new Treerow();
		Treecell treecell = new Treecell();

		List<PreferenciaAlimentaria> list=ServiceLocator.getReportesManager().cenasXRutas(fechaInicial, fechaFinal, String.valueOf(tipoConsulta));

		int ndias=(int) ((dbxFechaFinal.getValue().getTime()- dbxFechaInicial.getValue().getTime())/Constantes.MILISEGUNDOS_X_DIA)+1;
		Long lfcehaPArtida=dbxFechaInicial.getValue().getTime()-Constantes.MILISEGUNDOS_X_DIA;

		for(int x=0;x<ndias;x++){
//			totalAsientos=0;
			lfcehaPArtida+=Constantes.MILISEGUNDOS_X_DIA;
			Date fechaPartida= new Date(lfcehaPArtida);
				/*Agrega items agrupados por fecha de itinerario*/
				itemDate = new Treeitem();
				treerow = new Treerow();
				treecell = new Treecell(Constantes.FORMAT_DATE.format(fechaPartida),"resources/mp_calendarEnabled.png");
				treerow.appendChild(treecell);
				treerow.appendChild(new Treecell());

				/*Agrega rutas al item itemGoupFechas*/
				int cantPaxFecha=insertCenas(list,fechaPartida, itemDate);

				Treecell cell=new Treecell("");
				treerow.appendChild(cell);

				cell=new Treecell(String.valueOf(cantPaxFecha));
				cell.setStyle("font-weight:bold;color:red");
				treerow.appendChild(cell);

				insertTotales(itemDate, cantPaxFecha);

				itemDate.appendChild(treerow);
				treechildren.appendChild(itemDate);
		}



		tree.appendChild(treechildren);
		this.appendChild(tree);
	}

	private void insertTotales(Treeitem treeitem,Integer totalPax){

		//Inserta totales de los servicios
		int totalServicios=0;
		for(Entry<?, ?> e:totalesServicios.entrySet()){
			int lenSerator=e.getValue().toString().indexOf(";");
			int len=e.getValue().toString().length();
			String descripcion=String.valueOf(e.getValue()).substring(0, lenSerator);
			int cantServicio=Integer.parseInt(String.valueOf(e.getValue()).substring(lenSerator+1,len));
			newItemTotales(treeitem, descripcion, Integer.toString(cantServicio));

			totalServicios+=cantServicio;
		}
//		newItemTotales(treeitem, "TOTAL SERVICIOS ", totalServicios.toString());
		newItemTotales(treeitem, "TOTAL CENAS ", totalPax.toString());

	}

	private void newItemTotales(Treeitem treeitem,String descripcion,String valor){
		String styleForeColor = "font-size:12px;font-weight:bold;color:white";

		Treeitem item =new Treeitem();
		Treerow treerow = new Treerow();
		Treecell treecell = new Treecell();

		treerow.appendChild(new Treecell());
		treerow.appendChild(new Treecell());

		treecell=new Treecell(descripcion);
		treecell.setStyle(styleForeColor);
		treerow.appendChild(treecell);

		treecell=new Treecell(valor.toString());
		treecell.setStyle(styleForeColor);
		treerow.appendChild(treecell);

		treerow.setStyle("background:black");
		item.appendChild(treerow);
		treechildrenRutas.appendChild(item);
		treeitem.appendChild(treechildrenRutas);
	}

	private int insertCenas(List<PreferenciaAlimentaria> listAlimentacion,Date fechaPartida, Treeitem itemDate){

		int totalPax=0;
		treechildrenRutas = new Treechildren();

		for(PreferenciaAlimentaria alimentacion:listAlimentacion){
			//Valida si se trata de la mima fecha
			if(alimentacion.getFecha().getTime()==fechaPartida.getTime()){
				int CountPax=0;
				Treeitem itemAlimentacion = new Treeitem();
				Treerow rowAlimentacion = new Treerow();
				Treechildren treechildrenAlimentacion = new Treechildren();

				//Inserta Cenas por Itinerario
				for(VentaPasaje ventaXItinerario: alimentacion.getLstVentaXItinerario()){
					Treeitem itemXItinerario = new Treeitem();
					Treerow rowXItinerario = new Treerow();

					//Inserta x Rutas
					int cantCenas=0;
					Treechildren treechildrenXRuta = new Treechildren();
					for(VentaPasaje ventaXRuta: ventaXItinerario.getLstVentaXRuta()){
						if(ventaXRuta.getPreferenciaAlimentaria()!=null && ventaXRuta.getPreferenciaAlimentaria().getId().intValue()==alimentacion.getId().intValue() ){
							Treeitem itemXRuta = new Treeitem();
							Treerow rowXRuta = new Treerow();

							//Inserta Pasajeros
							Treechildren treechildrenXPasajero = new Treechildren();
							for(VentaPasaje ventaXPasajero: ventaXRuta.getLstPasajeros()){
								Treeitem itemXPasajero = new Treeitem();
								Treerow rowXPasajero = new Treerow();

								Treecell cellXPasajero = new Treecell(ventaXPasajero.getPasajero().getNombresApellidos());
								cellXPasajero.setTooltiptext("Nombre del Pasajero");
								rowXPasajero.appendChild(cellXPasajero);
								cellXPasajero=new Treecell(ventaXPasajero.getNumeroAsiento().toString());
								cellXPasajero.setTooltiptext("Número de Asiento");
								rowXPasajero.appendChild(cellXPasajero);
								cellXPasajero=new Treecell("");
								rowXPasajero.appendChild(cellXPasajero);
								cellXPasajero=new Treecell("");
								rowXPasajero.appendChild(cellXPasajero);

								itemXPasajero.appendChild(rowXPasajero);
								treechildrenXPasajero.appendChild(itemXPasajero);
							}

							Treecell cellXRuta = new Treecell(ventaXRuta.getItinerario().getRuta().toString());
							rowXRuta.appendChild(cellXRuta);
							if(ventaXRuta.getLstPasajeros().size()>0){
								cellXRuta= new Treecell("N° Asiento");
								cellXRuta.setStyle("font-weight:bold");
							}else
								cellXRuta= new Treecell("");
							rowXRuta.appendChild(cellXRuta);
							cellXRuta= new Treecell("");
							rowXRuta.appendChild(cellXRuta);
							cellXRuta= new Treecell(ventaXRuta.getCantidadPax().toString());
							rowXRuta.appendChild(cellXRuta);

							if(ventaXRuta.getLstPasajeros().size()>0){
								itemXRuta.appendChild(treechildrenXPasajero);
								itemXRuta.setOpen(false);
								rowXRuta.setStyle("background:#F9F7C8"); //F9F7C8 FEFEEE
							}

							itemXRuta.appendChild(rowXRuta);
							treechildrenXRuta.appendChild(itemXRuta);

							cantCenas+=ventaXRuta.getCantidadPax();
						}
					}

					itemXItinerario.appendChild(treechildrenXRuta);
					itemXItinerario.setOpen(false);
					Treecell treecellXItinerario = new Treecell(ventaXItinerario.getItinerario().getRuta().toString());
					rowXItinerario.appendChild(treecellXItinerario);
					treecellXItinerario = new Treecell(ventaXItinerario.getItinerario().getHoraPartida()!=null? ventaXItinerario.getItinerario().getHoraPartida():"");
					rowXItinerario.appendChild(treecellXItinerario);
					treecellXItinerario = new Treecell(ventaXItinerario.getItinerario().getServicio().getDenominacion()!=null? ventaXItinerario.getItinerario().getServicio().getDenominacion():"");
					rowXItinerario.appendChild(treecellXItinerario);
					treecellXItinerario = new Treecell(String.valueOf(ventaXItinerario.getCantidadPax()));
					treecellXItinerario.setStyle("font-weight:bold;color:#0934D4");
					rowXItinerario.appendChild(treecellXItinerario);

					rowXItinerario.setStyle("background:#FFFFD2");
					itemXItinerario.appendChild(rowXItinerario);
					treechildrenRutas.appendChild(itemXItinerario);
					/*Agrega el item rutas al itemGoupFechas*/
					itemDate.appendChild(treechildrenRutas);
					itemDate.setOpen(false);

					CountPax+=+cantCenas;
					totalPax+=+cantCenas;

					treechildrenAlimentacion.appendChild(itemXItinerario);
				}


				itemAlimentacion.appendChild(treechildrenAlimentacion);
				itemAlimentacion.setOpen(false);
				Treecell treecellAlimentacion = new Treecell(alimentacion.getDenominacion());
				rowAlimentacion.appendChild(treecellAlimentacion);
				treecellAlimentacion = new Treecell("");
				rowAlimentacion.appendChild(treecellAlimentacion);
				treecellAlimentacion = new Treecell("");
				rowAlimentacion.appendChild(treecellAlimentacion);
				treecellAlimentacion = new Treecell(String.valueOf(alimentacion.getCantidad()));
				treecellAlimentacion.setStyle("font-weight:bold;color:#0934D4");
				rowAlimentacion.appendChild(treecellAlimentacion);

				rowAlimentacion.setStyle("background:#E1F5A9");
				itemAlimentacion.appendChild(rowAlimentacion);
				treechildrenRutas.appendChild(itemAlimentacion);

				/*Agrega el item rutas al itemGoupFechas*/
				itemDate.appendChild(treechildrenRutas);
				itemDate.setOpen(false);

//				totalPax=CountPax;
			}
		}
		return totalPax;
	}

//	private void insertResum(String title,String valorTitle, Treechildren children, Treeitem itemDate, String styleBackground){
//		String styleForeColor = "font-size:12px;font-weight:bold;color:white";
//
//		Treeitem itemSumPax = new Treeitem();
//		Treerow rowResumen= new Treerow();
//		rowResumen.setStyle(styleBackground);
//
//		rowResumen.appendChild(new Treecell());
//
//		rowResumen.appendChild(new Treecell());
//
//		rowResumen.appendChild(new Treecell());
//		//--
//		Treecell cellTitle= new Treecell(title);
//		cellTitle.setStyle(styleForeColor);
//		rowResumen.appendChild(cellTitle);
//		//---
//		Treecell cellValue=new Treecell(valorTitle);
//		cellValue.setStyle(styleForeColor);
//		rowResumen.appendChild(cellValue);
//
//		itemSumPax.appendChild(rowResumen);
//		children.appendChild(itemSumPax);
//		itemDate.appendChild(children);
//	}
//
//
//	private void newItem(Treechildren children, Treeitem itemDate){
//		Treeitem itemSumPax = new Treeitem();
//		Treerow rowResumen= new Treerow();
//		rowResumen.appendChild(new Treecell());
//		rowResumen.appendChild(new Treecell());
//
//		itemSumPax.appendChild(rowResumen);
//		children.appendChild(itemSumPax);
//		itemDate.appendChild(children);
//	}

	/**
	 * Carga el tipo de consulta
	 * @param combobox:
	 */
	public void cargarTipoConsulta(Combobox combobox) {
//		Comboitem oComboitem0 = new Comboitem("TODOS");
		Comboitem oComboitem1 = new Comboitem("IDA");
		Comboitem oComboitem2 = new Comboitem("RETORNOS");
		Comboitem oComboitem3 = new Comboitem("PROVINCIAS");

//		oComboitem0.setValue(TIPCONSUL_TODOS);
		oComboitem1.setValue(TIPCONSUL_IDA);
		oComboitem2.setValue(TIPCONSUL_RETORNO);
		oComboitem3.setValue(TIPCONSUL_PROVINCIAS);

//		combobox.appendChild(oComboitem0);
		combobox.appendChild(oComboitem1);
		combobox.appendChild(oComboitem2);
		combobox.appendChild(oComboitem3);
	}

	public void exportar(){
		Session session = getDesktop().getSession();
		HttpSession httpSession = (HttpSession)session.getNativeSession();
		httpSession.setAttribute("path", Constantes.DIRECTORY_EXCEL+"AvanceSemanal.xls");
		httpSession.setAttribute("tree", tree);
		Executions.sendRedirect("/exportXlsAvanceSemanalXRutas.htm");
	}
}
