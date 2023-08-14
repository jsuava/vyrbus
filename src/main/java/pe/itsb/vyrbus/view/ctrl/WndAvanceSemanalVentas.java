package pe.itsb.vyrbus.view.ctrl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.zkoss.bind.annotation.AfterCompose;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Session;
import org.zkoss.zk.ui.util.Clients;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listhead;
import org.zkoss.zul.Listheader;
import org.zkoss.zul.Listitem;

import pe.itsb.vyrbus.model.bean.Localidad;
import pe.itsb.vyrbus.model.bean.Servicio;
import pe.itsb.vyrbus.model.bean.VentaPasaje;
import pe.itsb.vyrbus.service.locator.ServiceLocator;
import pe.itsb.vyrbus.service.util.Constantes;
import pe.itsb.vyrbus.service.util.MyTime;
import pe.itsb.vyrbus.service.util.Util;
import pe.itsb.vyrbus.service.util.UtilData;
import pe.itsb.vyrbus.view.ui.DlgMessage;
import pe.itsb.vyrbus.view.ui.WndBase;

/**
 *
 * @author JABANTO
 *
 */
public class WndAvanceSemanalVentas extends WndBase implements Serializable{
	private static final long serialVersionUID = 1L;

	private Datebox dbDesde;
	private Combobox cmbOrigen;
	private Combobox cmbTipo;
	private Datebox dbHasta;
	private Combobox cmbDestino;
	private Combobox cmbServicio;
	private Listbox lbxAvance = new Listbox();

	Integer rowReco=0;

	/*
	 * (non-Javadoc)
	 * @see com.tepsa.sisvyr.view.ui.WndBase#initComponents()
	 */
	@Override
	public void initComponents() {
		dbDesde = (Datebox) this.getFellow("dbDesde");
		cmbOrigen = (Combobox) this.getFellow("cmbOrigen");
		cmbTipo = (Combobox) this.getFellow("cmbTipo");
		dbHasta = (Datebox) this.getFellow("dbHasta");
		cmbDestino = (Combobox) this.getFellow("cmbDestino");
		cmbServicio = (Combobox) this.getFellow("cmbServicio");
		//lbxAvance = (Listbox) this.getFellow("lbxAvance");
	}

	/*
	 * (non-Javadoc)
	 * @see com.tepsa.sisvyr.view.ui.WndBase#onCreate()
	 */
	@Override
	public void onCreate() throws Exception {
		MyTime time = new MyTime();
		dbDesde.setValue(Constantes.FORMAT_DATE.parse(time.dateServer()));
		dbHasta.setValue(Constantes.FORMAT_DATE.parse(time.dateServer()));
		UtilData.cargarDataCombo(cmbOrigen, Localidad.class, true);
		UtilData.cargarDataCombo(cmbDestino, Localidad.class, true);
		UtilData.cargarDataCombo(cmbServicio, Servicio.class, true);
		UtilData.cargarTipoTransaction(cmbTipo);

		cmbTipo.setSelectedIndex(0);

	}


	public void onBuscarAvance(){
		try{

			lbxAvance.getItems().clear();
			String idOrigen="";
			String idDestino="";
			String idServicio="";
			String fechaDesde = Constantes.FORMAT_DATE.format(dbDesde.getValue());
			String fechaHasta = Constantes.FORMAT_DATE.format(dbHasta.getValue());
			String tipoTransaction=cmbTipo.getSelectedItem().getValue().toString();

			if(cmbOrigen.getSelectedItem().getValue() instanceof Localidad)
				idOrigen=((Localidad)cmbOrigen.getSelectedItem().getValue()).getId().toString();
			if(cmbDestino.getSelectedItem().getValue() instanceof Localidad)
				idDestino=((Localidad)cmbDestino.getSelectedItem().getValue()).getId().toString();
			if(cmbServicio.getSelectedItem().getValue() instanceof Servicio)
				idServicio=((Servicio)cmbServicio.getSelectedItem().getValue()).getId().toString();


			lbxAvance.detach();
			lbxAvance = new Listbox();
			lbxAvance.setHeight("480px");

			Listhead listhead = new Listhead();
		    Listheader listheader = new Listheader();

			listheader.setLabel("#");
			listheader.setWidth("30px");
			listheader.setStyle("color: #ffffff;");
			listhead.appendChild(listheader);

			listheader =  new Listheader();
			listheader.setLabel("HORA");
			listheader.setWidth("70px");
			listheader.setSort("auto");
			listheader.setStyle("color: #ffffff;");
			listhead.appendChild(listheader);

			listheader =  new Listheader();
			listheader.setLabel("SERVICIO");
			listheader.setWidth("160px");
			listheader.setStyle("color: #ffffff;");
			listhead.appendChild(listheader);

			listheader =  new Listheader();
			listheader.setLabel("RUTA");
			listheader.setWidth("200px");
			listheader.setStyle("color: #ffffff;");
			listhead.appendChild(listheader);

			listheader =  new Listheader();
			listheader.setLabel("CAP.ASIENTOS");
			listheader.setStyle("color: #ffffff;");
			listheader.setVisible(false);
			listhead.appendChild(listheader);

			lbxAvance.appendChild(listhead);
			String style = "font-size:12px !important;font-weight:bold;color:white";

			ArrayList<Object> columnFechas = new ArrayList<>();
			List<VentaPasaje> lstAvance=null;
			List<VentaPasaje>lstAvanceColumns=null;

			if(idOrigen!="" || idDestino!=""){
				lstAvance = ServiceLocator.getVentaPasajesManager().buscarAvanceSemanalVentas(idOrigen, idDestino, tipoTransaction, idServicio, fechaDesde, fechaHasta);
				lstAvanceColumns=ServiceLocator.getVentaPasajesManager().buscarAvanceSemanalVentasColumns(idOrigen, idDestino, tipoTransaction, idServicio, fechaDesde, fechaHasta);
			}else{
				lstAvance = ServiceLocator.getVentaPasajesManager().buscarAvanceSemanalVentas(String.valueOf(Constantes.ID_LOC_LIMA), idDestino, tipoTransaction, idServicio, fechaDesde, fechaHasta);
				lstAvanceColumns=ServiceLocator.getVentaPasajesManager().buscarAvanceSemanalVentasColumns(String.valueOf(Constantes.ID_LOC_LIMA), idDestino, tipoTransaction, idServicio, fechaDesde, fechaHasta);
			}

			/*Genera columnas de a cuerdo al rango de fechas de busqueda*/
			for(VentaPasaje column: lstAvanceColumns){
			    listheader = new Listheader();
				listheader.setLabel(Constantes.FORMAT_DATE.format(column.getFechaPartida()));
				listheader.setWidth("70px");
				listheader.setAlign("right");
				listheader.setStyle("color: #ffffff;");
				listhead.appendChild(listheader);
				lbxAvance.appendChild(listhead);
				columnFechas.add(Constantes.FORMAT_DATE.format(column.getFechaPartida()));
			}

			addAvance(columnFechas, lstAvance);
			String pasBackground="#448aff";
			String ocuBackground="#2962ff";
			String totalesBackground = "#2962ff";

			//*Agrega avace de retorno, solamente si origen y destono están seleccionados en todos.
			if(idOrigen.isEmpty() && idDestino.isEmpty()){
				//Agrega resumen Lima provincias
				addResumen(columnFechas,"Pas Lima==>Prov",pasBackground,lstAvance);
				//Agrega ocupabilidad lima provincias
				addOcupabilidad(columnFechas, "%Ocup Lima==>Prov", ocuBackground, lstAvance);

				//consulta avance por el destino
				List<VentaPasaje>lstServicioRetorno=new ArrayList<>();
				lstServicioRetorno = ServiceLocator.getVentaPasajesManager().buscarAvanceSemanalVentas(idOrigen, String.valueOf(Constantes.ID_LOC_LIMA), tipoTransaction, idServicio, fechaDesde, fechaHasta);
				addAvance(columnFechas, lstServicioRetorno);
				//Agrega resumen provincias Lima
				addResumen(columnFechas,"Pas Prov==>Lima", pasBackground,lstServicioRetorno);
				//Agrega ocupabilidad provincias lima
				addOcupabilidad(columnFechas, "%Ocup Prov==>Lima", ocuBackground, lstServicioRetorno);

				//Consulta avance provincias
				List<VentaPasaje>lstServicioProvincias=new ArrayList<>();
				lstServicioProvincias = ServiceLocator.getVentaPasajesManager().buscarAvanceSemanalVentas(String.valueOf(Constantes.ID_LOC_LIMA), String.valueOf(Constantes.ID_LOC_LIMA), tipoTransaction, idServicio, fechaDesde, fechaHasta);
				addAvance(columnFechas, lstServicioProvincias);
				//Agrega resumen provincias
				addResumen(columnFechas,"Pas Provincias", pasBackground,lstServicioProvincias);
				//Agrega ocupabilidad provincias
				addOcupabilidad(columnFechas, "%Ocup Provincias", ocuBackground, lstServicioProvincias);

				addNewItem();
				//Agrega Total servicios - ida
				addTotalServiciosXIda(lstAvance, "SERV.LIMA==>PROV.", style);
				//Agrega Total servicios - retorno
				addTotalServiciosXIda(lstServicioRetorno, "SERV.PROV.==>LIMA", style);
				//Agrega el total servicios
				addTotalServicios("TOTAL SERVICIOS", lstAvance, lstServicioRetorno,lstServicioProvincias, style);
			}else{
				//Agera resumen según el origen y destino seleccionados.
				if(idOrigen.equals(String.valueOf(Constantes.ID_LOC_LIMA)) && idDestino.isEmpty()){
					addResumen(columnFechas,"Pas Lima==>Prov", pasBackground,lstAvance);
					addOcupabilidad(columnFechas,"%Ocup Lima==>Prov", ocuBackground,lstAvance);
					//Agrega Total servicios - ida
					addNewItem();
					addTotalServiciosXIda(lstAvance, "SERV.LIMA==>PROV.", style);
					//Agrega el total servicios
					addTotalServicios("TOTAL SERVICIOS", lstAvance, new ArrayList<VentaPasaje>(),new ArrayList<VentaPasaje>(), style);
				}else if(idOrigen.isEmpty() && idDestino.equals(String.valueOf(Constantes.ID_LOC_LIMA))){
					addResumen(columnFechas,"Pas Prov==> Lima", pasBackground,lstAvance);
					addOcupabilidad(columnFechas,"%Ocup Prov==> Lima", ocuBackground,lstAvance);
					//Agrega Total servicios - ida
					addNewItem();
					addTotalServiciosXIda(lstAvance, "SERV.PROV.==>LIMA", style);
					//Agrega el total servicios
					addTotalServicios("TOTAL SERVICIOS", lstAvance, new ArrayList<VentaPasaje>(),new ArrayList<VentaPasaje>(), style);
				}else{
					addResumen(columnFechas,"Pas "+cmbOrigen.getText()+"==>"+cmbDestino.getText(), pasBackground,lstAvance);
					addOcupabilidad(columnFechas,"%Ocup "+cmbOrigen.getText()+"==>"+cmbDestino.getText(), ocuBackground,lstAvance);
					//Agrega Total servicios - ida
					addNewItem();
					addTotalServiciosXIda(lstAvance, "SERV."+cmbOrigen.getText()+"==>"+cmbDestino.getText(), style);
					//Agrega el total servicios
					addTotalServicios("TOTAL SERVICIOS", lstAvance, new ArrayList<VentaPasaje>(),new ArrayList<VentaPasaje>(), style);
				}
			}

			//Agraga total pasajeros
			addResumen(columnFechas,"Total Pasajeros","#2962ff",null);
			//Calcula el total Ocupalidad
			addOcupabilidad(columnFechas,"%OCUPABILIDAD TOTAL","#2962ff",null);
			this.appendChild(lbxAvance);


		}catch(Exception ex){
			ex.printStackTrace();
			DlgMessage.error(this.getClass().getSimpleName()+" "+ex.getMessage());
		}
	}

	/**
	 * Agrega un nuevo item
	 */
	private void addNewItem(){
		Listitem item=new Listitem();
		item.appendChild(new Listcell());
		lbxAvance.appendChild(item);
	}

	/**
	 * Agrega el total servicios
	 * @param label			: El nombre que representa la fila. "TOTAL SERVICIOS"
	 * @param lisVentas1	: Para el caso de ida y vuelta es de IDA
	 * @param lstVentas2	: Para el caso de ida y vuelta es de RETORNO, si no es null.
	 * @param style			: Stylo de la celda
	 */
	private void addTotalServicios(String label,List<VentaPasaje> lisVentas1,List<VentaPasaje>lstVentas2,List<VentaPasaje>lstVentas3,String style){
		Listitem item = new Listitem();
		Listcell cell = new Listcell();

		item.appendChild(new Listcell());
		item.appendChild(new Listcell());
		item.appendChild(new Listcell());

		cell.setLabel(label);
		cell.setStyle(style);
		item.appendChild(cell);
		item.appendChild(new Listcell());

		//Obtiene el heardes del listbox (estos es para obtener las fechas para validar con la fecha de partida del servicio)
		Object component[]= lbxAvance.getHeads().toArray();
		Listhead listhead =(Listhead)component[0];

		for(int y=0; y <listhead.getChildren().size()-5; y++){
			Listheader listheader=(Listheader) listhead.getChildren().get(y+5);
			String fechaHeader=listheader.getLabel();
			int cont=0;
			for(VentaPasaje ventas : lisVentas1){
				String fechaPartida=Constantes.FORMAT_DATE.format(ventas.getFechaPartida());
				//Compara con la fecha del header
				if(fechaPartida.equals(fechaHeader))
						cont++;
			}

			//Cuando es de ida y retorno
			for(VentaPasaje ventas : lstVentas2){
				String fechaPartida=Constantes.FORMAT_DATE.format(ventas.getFechaPartida());
				//Compara con la fecha del header
				if(fechaPartida.equals(fechaHeader))
						cont++;
			}

			//Cuando es de ida y retorno
			for(VentaPasaje ventas : lstVentas3){
				String fechaPartida=Constantes.FORMAT_DATE.format(ventas.getFechaPartida());
				//Compara con la fecha del header
				if(fechaPartida.equals(fechaHeader))
						cont++;
			}

			cell= new Listcell(Integer.toString(cont));
			cell.setStyle(style);
			item.appendChild(cell);
		}
		cell.setStyle(style);
		item.appendChild(cell);
		item.setStyle("background:#2962ff");
		lbxAvance.appendChild(item);
	}



	/**
	 * Agrega resumen por ida y/o vuela.
	 * @param columnFechas	: Array con las columnas generadas dinamicamente.
	 * @param descripcion	: Nombre del resumen.
	 * @param background	: Color de fondo del item
	 */
	private void addResumen(ArrayList<Object> columnFechas, String descripcion, String background,List<VentaPasaje> lstVenta){
		/*Calcula totales*/
		String style = "font-size:12px !important;font-weight:bold;color:white";
		Listitem newItem= new Listitem();

		newItem.appendChild(new Listcell());
		newItem.appendChild(new Listcell());
		newItem.appendChild(new Listcell());
		Listcell cell= new Listcell(descripcion);
		cell.setStyle(style);
		newItem.appendChild(cell);
		newItem.appendChild(new Listcell());

		int index=4;
		for (Object element : columnFechas) {
			int total=0;
			index++;
			if(lstVenta==null){
				//Calcula el total pasajeros
				for(Listitem item: lbxAvance.getItems()){
					int size=item.getChildren().size();
					if(size>index){
						Listcell lcell=(Listcell) item.getChildren().get(index);
						Listcell litem=(Listcell) item.getChildren().get(0);
						String cantidad=lcell.getLabel();
						if(!(cantidad.isEmpty()) && !(litem.getLabel().isEmpty()))
							total+=Integer.valueOf(cantidad);
					}
				}

			}else{
				//Calcula el total pasajeros por ida o vuelta
				for (VentaPasaje ventaPasaje: lstVenta){
					String columnFecha=element.toString();
					String fechaPartida=Constantes.FORMAT_DATE.format(ventaPasaje.getFechaPartida());
					if(columnFecha.trim().equals(fechaPartida.trim()))
						total+=ventaPasaje.getCantidadPax();
				}
			}

			cell= new Listcell(Integer.toString(total));
			cell.setStyle(style);
			newItem.appendChild(cell);

			newItem.setStyle("background:"+background);
			lbxAvance.appendChild(newItem);
		}
	}

	private void addOcupabilidad(ArrayList<Object> columnFechas,String descripcion,String background,List<VentaPasaje> lstVenta  ){
		String style = "font-size:12px !important;font-weight:bold;color:white";
		Listitem newItem= new Listitem();
		newItem.appendChild(new Listcell());
		newItem.appendChild(new Listcell());
		newItem.appendChild(new Listcell());
		Listcell cell= new Listcell(descripcion);
		cell.setStyle(style);
		newItem.appendChild(cell);
		newItem.appendChild(new Listcell());

		int index=4;
		for (Object element : columnFechas) {
			int CapMAxAsientos=0;
			int asientosOcupados=0;
			index++;
			if(lstVenta==null){
				//Calcula el total pasajeros
				for(Listitem item: lbxAvance.getItems()){
					int size=item.getChildren().size();
					if(size>index){
						Listcell lcell=(Listcell) item.getChildren().get(index);
						Listcell litem=(Listcell) item.getChildren().get(0);
						String cantidad=lcell.getLabel();
						if(!(cantidad.isEmpty()) && !(litem.getLabel().toString().trim().equals("")) ){
							asientosOcupados+=Integer.valueOf(cantidad);
							CapMAxAsientos+=(((VentaPasaje) item.getValue()).getServicio().getTotalAsientos());
						}
					}
				}
			}else{
				//Calcula el total pasajeros por ida o vuelta
				for (VentaPasaje ventaPasaje: lstVenta){
					if(element.toString().equals(Constantes.FORMAT_DATE.format(ventaPasaje.getFechaPartida()))){
						asientosOcupados+=ventaPasaje.getCantidadPax();
						CapMAxAsientos+=ventaPasaje.getServicio().getTotalAsientos();
					}
				}
			}


			Double ocup=(double) (asientosOcupados*100);
			ocup=ocup/CapMAxAsientos;

			cell= new Listcell(Util.toNumberFormat(ocup,2));
			cell.setStyle(style);
			newItem.appendChild(cell);

			newItem.setStyle("background:"+background);
			lbxAvance.appendChild(newItem);
		}
	}


	@SuppressWarnings({"unused" })
	private ArrayList<Object> addAvance (ArrayList<Object> columnFechas,List<VentaPasaje> lstAvance) throws Exception{
		Listitem item = null;
		Listcell cell = null;
		int i=0;// int vl=0;
		String fechaviaje_A=null;

		ArrayList<Object> totales=new ArrayList<>();
		ArrayList<Object> hora_A = new ArrayList<>();
		ArrayList<Object> servicio_A = new ArrayList<>();
		ArrayList<Object> ruta_A = new ArrayList<>();

		int p =0;

		int iTotalServicios=0;
		int iTotalPasajeros=0;
		ArrayList<Object>cantServicios=new ArrayList<>();

		//Obtiene el heardes del listbox (estos es para obtener las fechas para validar con la fecha de partida del servicio)
		Object component[]= lbxAvance.getHeads().toArray();
		Listhead listhead =(Listhead)component[0];

		/*agrega los items al listbox*/
		for(VentaPasaje venta : lstAvance){
			fechaviaje_A=Constantes.FORMAT_DATE.format(venta.getFechaPartida());
			boolean existe=false;
			String hora="";
			String ruta="";
			String servicio="";
			//Validación para evitar las rutas duplicadas
			for(int y=0; y<ruta_A.size(); y++){
				hora=venta.getHoraPartida();
				ruta=venta.getRuta().toString();//.getOrigen()+" - "+venta.getRuta().getDestino();
				servicio=venta.getServicio().getDenominacion();
				if (ruta_A.get(y).equals(ruta) && servicio_A.get(y).equals(servicio) && hora_A.get(y).equals(hora) ){
					existe=true;
					break;}
			}

			if (!(existe)){
				i++;
				item = new Listitem();
				cell = new Listcell(String.valueOf(i));
				item.appendChild(cell);
				cell = new Listcell(venta.getHoraPartida());
				cell.setStyle("font-size:11px !important");
				item.appendChild(cell);
				cell = new Listcell(venta.getServicio().getDenominacion());
				item.appendChild(cell);
				cell = new Listcell(venta.getRuta().toString());
				item.appendChild(cell);

				//Capasidad del bus (esta columna esta oculta)
				cell = new Listcell(venta.getServicio().getTotalAsientos().toString());
				item.appendChild(cell);

				hora=venta.getHoraPartida();
				ruta=venta.getRuta().toString(); //.getOrigen()+" - "+venta.getRuta().getDestino();
				servicio=venta.getServicio().getDenominacion();

				for(int y=0; y <listhead.getChildren().size()-5; y++){
					Listheader listheader=(Listheader) listhead.getChildren().get(y+5);
					String fechaHeader=listheader.getLabel();

					int cont=0;
					for(VentaPasaje ventas : lstAvance){
						String fechaPartida=Constantes.FORMAT_DATE.format(ventas.getFechaPartida());

						//Compara con la fecha del header
						if(fechaPartida.equals(fechaHeader)){
							String horaPartida=ventas.getHoraPartida();
							String servicios=ventas.getServicio().getDenominacion();
							String rutas=ventas.getRuta().toString();

							//Realiza la compracion para colocar la cantidad de pasajeros
							if(rutas.equals(ruta) && servicios.equals(servicio) && horaPartida.equals(hora)){
								cell= new Listcell(ventas.getCantidadPax().toString());
								cell.setStyle("font-size:11px !important");
								item.appendChild(cell);
								cont++;
							}
						}
					}
					if(cont <=0)
						item.appendChild(new Listcell(""));
				}
			}

			item.setValue(venta);
			lbxAvance.appendChild(item);

			hora_A.add(venta.getHoraPartida());
			ruta_A.add(venta.getRuta().toString()); //.getOrigen()+" - "+venta.getRuta().getDestino());
			servicio_A.add(venta.getServicio().getDenominacion());
		}
		return cantServicios;
	}

	/**
	 * Agrega el total de servicios
	 * @param lstAvance
	 * @param label
	 * @param style
	 */
	public void addTotalServiciosXIda(List<VentaPasaje> lstAvance, String label,String style){
		Listitem item = new Listitem();
		Listcell cell = new Listcell();

		item.appendChild(new Listcell());
		item.appendChild(new Listcell());
		item.appendChild(new Listcell());

		cell.setLabel(label);
		cell.setStyle(style);
		item.appendChild(cell);
		item.appendChild(new Listcell());

		//Obtiene el heardes del listbox (estos es para obtener las fechas para validar con la fecha de partida del servicio)
		Object component[]= lbxAvance.getHeads().toArray();
		Listhead listhead =(Listhead)component[0];


		for(int y=0; y <listhead.getChildren().size()-5; y++){
			Listheader listheader=(Listheader) listhead.getChildren().get(y+5);
			String fechaHeader=listheader.getLabel();
			int cont=0;
			for(VentaPasaje ventas : lstAvance){
				String fechaPartida=Constantes.FORMAT_DATE.format(ventas.getFechaPartida());
				//Compara con la fecha del header
				if(fechaPartida.equals(fechaHeader))
					cont++;
			}
			cell=new Listcell(Integer.toString(cont));
			cell.setStyle(style);
			item.appendChild(cell);
		}

		cell.setStyle(style);
		item.appendChild(cell);
		item.setStyle("background:#2962ff");
		lbxAvance.appendChild(item);
	}

	public void btnExportar_OnClick(){
		Session session = getDesktop().getSession();
		HttpSession httpSession = (HttpSession)session.getNativeSession();
		httpSession.setAttribute("parcialPath",Constantes.DIRECTORY_EXCEL+"AvanceSemanal.xls");
		httpSession.setAttribute("lbxAvance", lbxAvance);
		Executions.sendRedirect("/exportXlsAvanceSemanal.htm");
	}


	@AfterCompose //@ContextParam(ContextType.VIEW) Component view
	public void initSetup() {
	    Clients.print(); // fire the printdialog
//	    Clients.evalJavaScript("window.close()"); // close the printwindow
	}
}
