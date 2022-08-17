/**
 * Proyecto		: SISVYR
 * Sistema		: Sistema de Ventas y Reservas
 * Descripción	:
 * Autor		: José Abanto
 * Fecha		: 01/09/2016
 * Hora			: 09:38:47
 */
package com.cystesoft.vyrbus.service.util;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.TreeMap;

import org.apache.http.HttpHost;
import org.json.JSONArray;
import org.json.JSONObject;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zul.Button;
import org.zkoss.zul.Caption;
import org.zkoss.zul.Column;
import org.zkoss.zul.Columns;
import org.zkoss.zul.Div;
import org.zkoss.zul.Grid;
import org.zkoss.zul.Groupbox;
import org.zkoss.zul.Image;
import org.zkoss.zul.Label;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Row;
import org.zkoss.zul.Rows;
import org.zkoss.zul.Separator;

import com.cystesoft.vyrbus.model.bean.Agencia;
import com.cystesoft.vyrbus.model.bean.Cliente;
import com.cystesoft.vyrbus.model.bean.DetalleItinerario;
import com.cystesoft.vyrbus.model.bean.Itinerario;
import com.cystesoft.vyrbus.model.bean.Menu;
import com.cystesoft.vyrbus.model.bean.ObjectCiva;
import com.cystesoft.vyrbus.model.bean.OcupacionAsientosBloqueadosPool;
import com.cystesoft.vyrbus.model.bean.Pasajero;
import com.cystesoft.vyrbus.model.bean.PoolLocalidad;
import com.cystesoft.vyrbus.model.bean.Ruta;
import com.cystesoft.vyrbus.model.bean.Servicio;
import com.cystesoft.vyrbus.model.bean.TipoItinerario;
import com.cystesoft.vyrbus.model.bean.Usuario;
import com.cystesoft.vyrbus.service.locator.ServiceLocator;
import com.cystesoft.vyrbus.view.ui.DlgMessage;
import com.cystesoft.vyrbus.view.ui.Events;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;

/**
 * @author jabanto
 *
 */
@SuppressWarnings("deprecation")
public class RESTCiva implements Serializable{
	//-->Desarrollo
//	private static String URI_API="http://www.excluciva.pe/apiciva/dev/";
//	private static String API_KEY="/apikey/a526cc6013c9db58092a77bcf2a96cc462936e16";

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	//-->Produccion
	private static String URI_API="http://www.excluciva.pe/apiciva/";
	private static String API_KEY="/apikey/a526cc6013c9db58092a77bcf2a96cc462936e16";
	private static String URI_DNI="https://dniruc.apisperu.com/api/v1/dni/";
	private static String URI_RUC="https://dniruc.apisperu.com/api/v1/ruc/";
	private static String TOKEN_DOCIDE="?token=eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJlbWFpbCI6Im9zY2NvZW1AZ21haWwuY29tIn0.W8wDh8URSCh8lbT-z_cQSQ_yQwMIA_QV00TejXvBIHg";

	private static Integer ID_SERVICIO_EXCLUCIVA=4;
	private static Integer ID_SERVICIO_SUPERCIVA=6;
	private static Integer ID_SERVICIO_ECONOCIVA=2;

	/**
	 * Realiza el llamado a la APPI, a traves del metodo POST
	 * @param metod: Nombre del metodo a ejecutar
	 * @param parametros : Parametros concatenados
	 * @return HttpResponse<JsonNode>
	 * @throws Exception
	 */
	private static HttpResponse<JsonNode> getPOST_REST(String metod, String parametros)throws Exception{
		Unirest.setProxy(new HttpHost("192.168.50.1", 8080));
		HttpResponse<JsonNode> response = Unirest.post(URI_API+metod+API_KEY)
//				  .header("cache-control", "no-cache")
//				  .header("postman-token", "dca64e95-c3bb-93a4-e90f-a1530e577510")
				  .header("content-type", "application/x-www-form-urlencoded")
				  .body(parametros)
				  .asJson();

		return response;
	}

	private static HttpResponse<JsonNode> getPOST_REST_DNI(String dni, String parametros)throws Exception{
//		Unirest.setProxy(new HttpHost("192.168.50.1", 8080));
		HttpResponse<JsonNode> response = Unirest.get(URI_DNI+parametros+TOKEN_DOCIDE)
//				  .header("cache-control", "no-cache")
//				  .header("postman-token", "dca64e95-c3bb-93a4-e90f-a1530e577510")
				  .header("content-type", "application/x-www-form-urlencoded")
//				  .body(parametros)
				  .asJson();

		return response;
	}

	private static HttpResponse<JsonNode> getPOST_REST_RUC(String ruc, String parametros)throws Exception{
//		Unirest.setProxy(new HttpHost("192.168.50.1", 8080));
		System.out.println(URI_RUC+parametros+TOKEN_DOCIDE);
		HttpResponse<JsonNode> response = Unirest.get(URI_RUC+parametros+TOKEN_DOCIDE)
//				  .header("cache-control", "no-cache")
//				  .header("postman-token", "dca64e95-c3bb-93a4-e90f-a1530e577510")
				  .header("content-type", "application/x-www-form-urlencoded")
//				  .body(parametros)
				  .asJson();

		return response;
	}

	public static List<String> getDatosDni(String dni)throws Exception{
		try{
		List<String>result = new ArrayList<>();
		HttpResponse<JsonNode> response=getPOST_REST_DNI(null,  dni);
		if(response.getStatus()==200 /*&& response.getBody().isArray()*/){//OK
			JSONArray jsonArray=response.getBody().getArray();
			for (int i = 0; i < jsonArray.length(); i++) {
			    JSONObject jsonobject = jsonArray.getJSONObject(i);


			    result.add(jsonobject.getString("dni"));
			    result.add(jsonobject.getString("nombres"));
			    result.add(jsonobject.getString("apellidoPaterno"));
			    result.add(jsonobject.getString("apellidoMaterno"));
//			    result.add(String.valueOf(jsonobject.getInt("codVerifica")));

			}
		}

		return result;
		}catch(Exception e){
			e.printStackTrace();
//			DlgMessage.error("Error al consultar dni \n"+e.getMessage());
			DlgMessage.error("Error al consultar DNI: o pertenece a un menor de edad o no existe.");
//			return new ArrayList<>();
			return null;
		}
	}

	public static List<String> getDatosRuc(String ruc)throws Exception{
		try{
		List<String>result = new ArrayList<>();
		HttpResponse<JsonNode> response=getPOST_REST_RUC(null,  ruc);
		if(response.getStatus()==200 /*&& response.getBody().isArray()*/){//OK
			JSONArray jsonArray=response.getBody().getArray();
			for (int i = 0; i < jsonArray.length(); i++) {
			    JSONObject jsonobject = jsonArray.getJSONObject(i);

			    result.add(jsonobject.getString("ruc"));
			    result.add(jsonobject.getString("razonSocial"));
			    result.add(jsonobject.getString("direccion"));
			}
		}

		return result;
		}catch(Exception e){
			e.printStackTrace();
//			DlgMessage.error("Error al consultar dni \n"+e.getMessage());
			DlgMessage.error("Error al consultar RUC: o no esta activo o se encuentra de baja.");
//			return new ArrayList<>();
			return null;
		}
	}



	/**
	 * Realiza el llamado a la APPI, a traves del metodo POST
	 * @param metod: Nombre del metodo a ejecutar
	 * @param parametros : Parametros concatenados
	 * @return HttpResponse<JsonNode>
	 * @throws Exception
	 */
	private static HttpResponse<String> getPOST_REST_STRING(String metod, String parametros)throws Exception{
		Unirest.setProxy(new HttpHost("192.168.50.1", 8080));
		HttpResponse<String> response = Unirest.post(URI_API+metod+API_KEY)
//				  .header("cache-control", "no-cache")
//				  .header("postman-token", "dca64e95-c3bb-93a4-e90f-a1530e577510")
				  .header("content-type", "application/x-www-form-urlencoded")
				  .body(parametros).asString();

		return response;
	}

	/**
	 * Realiza la busqueda de los itinerarios programados
	 * @param fechaProgramacion	: Fecha de programacion o de viaje
	 * @param localidad_IDOrigen	: Identificador de la localidad Origen - TEPSA
	 * @param localidad_IDDestino	: Identificador de la localidad Destino - TEPSA
	 * @return	lista de resultados
	 * @throws Exception
	 */
	public static List<DetalleItinerario> getItienrarios(String fechaProgramacion, Integer localidad_IDOrigen, Integer localidad_IDDestino)throws Exception{
		try {

			List<DetalleItinerario>result= new ArrayList<>();
			PoolLocalidad localidadDestinoPool=null;
			String origenId=null;

			/*Consulta si es una ruta compartida con Cruz del sur*/
			List<PoolLocalidad>poolLocalidad=ServiceLocator.getPoolLocalidadManager().buscarByLocalidadID(localidad_IDOrigen);
			if(poolLocalidad.size()>0){
				List<PoolLocalidad>integracionDestino=ServiceLocator.getPoolLocalidadManager().buscarByLocalidadID(localidad_IDDestino);
				if(integracionDestino.size()>0){
					/*Obtiene los identificadores de los origenes*/
					for(PoolLocalidad localidadIntegracion: poolLocalidad){
						if(localidadIntegracion.getRuc().equals(Constantes.RUC_CIVA)){
							origenId=localidadIntegracion.getLocalidadDestinoPoolID();
							break;
						}
					}
					/*Obtiene los identificadores de los destinos*/
					for(PoolLocalidad localidadIntegracion: integracionDestino){
						if(localidadIntegracion.getRuc().equals(Constantes.RUC_CIVA)){
							localidadDestinoPool=localidadIntegracion;
							break;
						}
					}
				}
			}
			if(origenId!=null && localidadDestinoPool!=null){
				DateFormat FORMAT_DATE=new SimpleDateFormat ("yyyyMMdd");
				Date date=Constantes.FORMAT_DATE.parse(fechaProgramacion);
				String parametros="Fecha="+(FORMAT_DATE.format(date))+ "&"
								+ "IdOrigen="+origenId+"&"
								+ "IdDestino="+localidadDestinoPool.getLocalidadDestinoPoolID();
				HttpResponse<JsonNode> response=getPOST_REST("itinerarios", parametros);

				if(response.getStatus()==200 && response.getBody().isArray()){//OK
					JSONArray jsonArray=response.getBody().getArray();
					for (int i = 0; i < jsonArray.length(); i++) {
					    JSONObject jsonobject = jsonArray.getJSONObject(i);

					    ObjectCiva objectCiva=new ObjectCiva();
					    objectCiva.setIdBus(jsonobject.getInt("IdBus"));
					    objectCiva.setTipoServicio(jsonobject.getString("TipoServicio"));
					    objectCiva.setRutaBus(jsonobject.getString("RutaBus").replaceAll("->", "-").trim());
					    objectCiva.setIdEscala(String.valueOf(jsonobject.getInt("IdEscala")));
					    objectCiva.setRutaRecorrido(jsonobject.getString("RutaRecorrido").replaceAll("->", "-").trim());
					    objectCiva.setFechaSalidaBus(fechaProgramacion);
					    /*Dando formato a la fecha de llegada*/
					    String[] yFechaLlegada=jsonobject.getString("FechaLlegadaBus").split(" ");
					    String sfechaLlegada=(yFechaLlegada[1].trim()+"/"+Util.getNumberMes(yFechaLlegada[0].trim().toUpperCase())+"/"+yFechaLlegada[2].trim()).trim();
					    objectCiva.setFechaLlegadaBus(sfechaLlegada);
					    objectCiva.setDireccionSalidaBus(jsonobject.getString("DireccionSalidaBus"));
					    objectCiva.setDireccionLlegadaBus(jsonobject.getString("DireccionLlegadaBus"));
					    /*Formateando la hora del embarque*/
					    String[] yhoraEmbarque= jsonobject.getString("HoraEmbarque").split(" ");
					    String shora=yhoraEmbarque[yhoraEmbarque.length-1];
					    String hora="";
					    String minuto="";
					    /*Valida si es tarde o mañana*/
					    if(shora.indexOf("PM")>0){
					    	hora=String.valueOf(Integer.valueOf(shora.split(":")[0]).intValue()+12);
					    	minuto=shora.split(":")[1].replaceAll("PM", "");
					    }else{
					    	hora=shora.split(":")[0];
					    	minuto=shora.split(":")[1].replaceAll("AM", "");
					    }
					    String horaEmbarque=hora+":"+minuto;
					    objectCiva.setHoraEmbarque(horaEmbarque);
					    /*Formateando la hora del desembarque*/
					    String[] yhoraDesembarque= jsonobject.getString("HoraDesembarque").split(" ");
					    shora=yhoraDesembarque[yhoraDesembarque.length-1];
					    hora="";
					    minuto="";
					    /*Valida si es tarde o mañana*/
					    if(shora.indexOf("PM")>0){
					    	hora=String.valueOf(Integer.valueOf(shora.split(":")[0]).intValue()+12);
					    	minuto=shora.split(":")[1].replaceAll("PM", "");
					    }else{
					    	hora=shora.split(":")[0];
					    	minuto=shora.split(":")[1].replaceAll("AM", "");
					    }
					    String horaDesembarque=hora+":"+minuto;
					    objectCiva.setHoraDesembarque(horaDesembarque);
					    objectCiva.setDireccionEmbarque(jsonobject.getString("DireccionSalidaBus"));
					    objectCiva.setDireccionDesembarque(jsonobject.getString("DireccionLlegadaBus"));
					    objectCiva.setTotalHoras(String.valueOf(jsonobject.getInt("TotalHoras")));
					    objectCiva.setPrecioPiso1(Double.valueOf(jsonobject.getDouble("PrecioPiso1")));
					    objectCiva.setPrecioPiso2(jsonobject.get("PrecioPiso2")!=null?Double.valueOf(jsonobject.getDouble("PrecioPiso2")):.00);
					    objectCiva.setIdOrigen(jsonobject.getInt("IdOrigen"));
					    objectCiva.setIdDestino(jsonobject.getInt("IdDestino"));
					    objectCiva.setIdServicio(jsonobject.getInt("IdServicio"));

					    DetalleItinerario detalleItinerario=new DetalleItinerario();
						Agencia agenciaPartida= new Agencia();
						agenciaPartida.setNombreCorto(objectCiva.getDireccionEmbarque());
						detalleItinerario.setAgenciaPartida(agenciaPartida);
						Servicio servicio= new Servicio();
						servicio.setDenominacion(objectCiva.getTipoServicio());
						Itinerario itinerario= new Itinerario();
						itinerario.setServicio(servicio);
						itinerario.setOperadoPor(Constantes.OPERADO_CIVA);
						TipoItinerario tipoItinerario= new TipoItinerario();
						tipoItinerario.setId(Constantes.ID_TIPITI_REGULAR);
						itinerario.setTipoItinerario(tipoItinerario);
						detalleItinerario.setItinerario(itinerario);
						Ruta ruta= new Ruta();
//						String[] sruta=objectCiva.getRutaBus().split("-");
						String[] sruta=objectCiva.getRutaRecorrido().split("-");
						ruta.setOrigen(sruta[0].trim());
						ruta.setDestino(sruta[1].trim());
						detalleItinerario.setRuta(ruta);
						detalleItinerario.setHoraPartida(objectCiva.getHoraEmbarque());
						detalleItinerario.setFechaPartida(Constantes.FORMAT_DATE.parse(fechaProgramacion));
						detalleItinerario.setTarifa(objectCiva.getPrecioPiso1());
						detalleItinerario.setTarifaSegundoPiso(objectCiva.getPrecioPiso2());

						detalleItinerario.setPoolLocalidad(localidadDestinoPool);
						detalleItinerario.setObjectCiva(objectCiva);
						result.add(detalleItinerario);
					}
				}
			}


			return result;
		} catch (Exception e) {
			e.printStackTrace();
			DlgMessage.error("Error al consultar Itienrarios de TURISMO CIVA \n"+e.getMessage());
			return new ArrayList<>();
		}
	}

	/**
	 * Realiza la creacion del mapa del Bus
	 * @param grpbxParent	: Object groupbox en cual contiene el mapa
	 * @param detalleItinerario	: Instancia del Object DetalleItinerario
	 * @param gridOcupabilidad	: Object Grid para mostrar la ocupabilidad
	 * @throws Exception
	 */

	public static void crearEstructura(Groupbox grpbxParent, DetalleItinerario detalleItinerario, Grid gridOcupabilidad, Agencia agencia, Usuario usuario)throws Exception{
		inicializarEstructura(grpbxParent);
		try {
			ObjectCiva objectCiva=detalleItinerario.getObjectCiva();

			Image imagen = generarImagen("/resources/mapa/bus_primerPiso.png", 247, 35);
			Div divPiso1=new Div();
			divPiso1.appendChild(imagen);

			String parametros="IdBus="+objectCiva.getIdBus()+ "&" + "IdEscala="+objectCiva.getIdEscala();
			HttpResponse<JsonNode> response=getPOST_REST("bus", parametros);

			Grid gridPiso1= new Grid();
			gridPiso1.setStyle("border:none;");
			gridPiso1.setWidth("245px");
			//columns
			Columns columns= new Columns();
			Column column= new Column("","","10px");
			columns.appendChild(column);
			column= new Column("","","50px");//Columna 1
			column.setAlign("center");
			columns.appendChild(column);
			column= new Column("","","50px");//Columna 2
			column.setAlign("center");
			columns.appendChild(column);
			column= new Column("","","70px");//Columna 3 (Pasadiso)
			column.setAlign("center");
			columns.appendChild(column);
			column= new Column("","","65px");//Columna 4
			column.setAlign("center");
			columns.appendChild(column);
			gridPiso1.appendChild(columns);
			//rows
			Rows rows= new Rows();
			Row row=null;

			if(response.getStatus()==200){ //OK
				JSONArray jsonArray=response.getBody().getArray();
				for (int i = 0; i < jsonArray.length(); i++) {
				    JSONObject jsonobject = jsonArray.getJSONObject(i);
				    if(objectCiva.getIdServicio().intValue()==ID_SERVICIO_EXCLUCIVA.intValue()){
				    	/*Carga mapa del piso 1*/
					    JSONObject asientosPiso = jsonobject.getJSONObject("AsientosPiso1");
					    loadMapa_Excluciva(asientosPiso, objectCiva, rows, false,agencia,usuario);
					    /*Carga mapa del piso 2*/
					    asientosPiso = jsonobject.getJSONObject("AsientosPiso2");
					    loadMapa_Excluciva(asientosPiso, objectCiva, rows, true,agencia,usuario);
				    }else if (objectCiva.getIdServicio().intValue()==ID_SERVICIO_SUPERCIVA){
				    	/*Carga mapa del piso 1*/
//					    JSONObject asientosPiso = jsonobject.getJSONObject("AsientosPiso1");


					    /*Carga mapa del piso 2*/
//					    asientosPiso = jsonobject.getJSONObject("AsientosPiso2");

				    }else if (objectCiva.getIdServicio().intValue()==ID_SERVICIO_ECONOCIVA){
				    	/*Carga mapa del piso 1*/
//					    JSONObject asientosPiso = jsonobject.getJSONObject("AsientosPiso1");


				    }
				}

				row= new Row();
				row.setSpans("5");
				row.setStyle("background:#99D9EA");
				Separator separator= new Separator();
				separator.setHeight("30px");
				row.appendChild(separator);
				rows.appendChild(row);

				gridPiso1.appendChild(rows);
				grpbxParent.appendChild(divPiso1);
				grpbxParent.appendChild(gridPiso1);
			}


		} catch (Exception e) {
			e.printStackTrace();
			DlgMessage.error("Error al consultar la ocupabilidad \n"+e.getMessage());
		}
	}

	/**
	 * Carga el mapa de asientos del Servicio Excluciva
	 * @param asientosPiso	: instancia del object JSONObject
	 * @param objectCiva	: instancia del Object objectCiva
	 * @param rows	: Rows del Grid
	 * @param isPiso2 : true=Piso2; false=Piso1
	 * @throws Exception
	 */
	private static void loadMapa_Excluciva(JSONObject asientosPiso,ObjectCiva objectCiva,Rows rows, boolean isPiso2,Agencia agencia, Usuario usuario)throws Exception{
		if(isPiso2){
			Image imagen = generarImagen("/resources/mapa/bus_segundoPiso.png", 247, 30);
			Div divPiso2=new Div();
			divPiso2.appendChild(imagen);
			Row row= new Row();
		    row.setSpans("5");
		    row.setStyle("background:#99D9EA");
		    row.appendChild(divPiso2);
		    rows.appendChild(row);
		}
		Row row=null;
		int columnasXpiso=3;
		int columna = 0, asientosPiso1=12;

		for(int x=0;x<asientosPiso.length();x++){
			int x_asiento=0;
			if(isPiso2)
				x_asiento=x+asientosPiso1+1;
			else
				x_asiento=x+1;
	    	JSONObject jsonAsiento=asientosPiso.getJSONObject(String.valueOf(x_asiento));
	    	int estadoAsiento =  jsonAsiento.getInt("EstadoAsiento");
	    	int numeroAsiento=jsonAsiento.getInt("NumeroAsiento");
	    	String asientoID=jsonAsiento.getString("IdAsiento");
	    	AsientoPool asiento=new AsientoPool();
	    	asiento.setAsientoID(asientoID);
	    	asiento.setObjectCiva(objectCiva);
	    	asiento.setDisponible(estadoAsiento);
	    	asiento.setAgencia(agencia);
	    	asiento.setUsuario(usuario);
	    	asiento.setNivelAsiento(isPiso2?2:1);
	    	asiento.setWidth("38px");
	    	asiento.setHeight("26px");
	    	columna++;
	    	if(row==null){
	    		row= new Row();
	    		row.setStyle("background:#99D9EA");
	    		row.appendChild(new Separator());
	    	}
	    	if(columna==3){
	    		row.appendChild(new Separator());
	    		if(x_asiento==18){
	    			row.appendChild(new Separator());
	    			columna=1;
		    		row= new Row();
		    		row.setStyle("background:#99D9EA");
		    		row.appendChild(new Separator());
	    		}
	    	}else if(columna>columnasXpiso){
	    		columna=1;
	    		row= new Row();
	    		row.setStyle("background:#99D9EA");
	    		row.appendChild(new Separator());
	    	}
	    	String styleAsiento="background-color: #D1CFCF;";
	    	/*Validando el estado del asiento*/
	    	if(estadoAsiento==Constantes.ASIENTO_DISPONIBLE){
	    		styleAsiento+="cursor:pointer;";
	    		Label label= new Label((String.valueOf(numeroAsiento).toString().length()==1?"0"+String.valueOf(numeroAsiento).toString():String.valueOf(numeroAsiento).toString()));
	    		String styleLabel="cursor:pointer;font-size:12px !important;color:black;";
				label.setStyle(styleLabel);
				asiento.setNumeroAsiento(label.getValue());
				asiento.appendChild(label);
				asiento.addEventListener(Events.ON_CLICK, new EventListener<Event>() {
					@Override
					public void onEvent(Event event) throws Exception {
						try {

							onClickAsiento(event);

						} catch (Exception e) {
							e.printStackTrace();
							DlgMessage.error(e.getMessage());
						}
					}
				});
	    	}else{
	    		Image imagen=generarImagen(AsientoPool.imagenOcupado, null, 18);
				asiento.appendChild(imagen);
	    	}
	    	asiento.setStyle(styleAsiento);
	    	row.appendChild(asiento);
	    	rows.appendChild(row);
	    }
	}


	/**
	 * cuando el Usuario Bloquea/Desbloquea un asiento
	 * @param e : Event
	 * @throws Exception
	 */
	private static void onClickAsiento(Event e)throws Exception{
		AsientoPool asiento= (AsientoPool) e.getTarget();
		Listbox listAsientos=asiento.getObjectCiva().getListAsientos();
		Button btnNextTabVenta=asiento.getObjectCiva().getBtnNextTabVenta();

		if(asiento.getDisponible().intValue()==Constantes.ASIENTO_DISPONIBLE){
			/*Desbloquea el asiento*/
			String parametros="IdAsiento="+asiento.getAsientoID().toString();
			HttpResponse<JsonNode> response=getPOST_REST("bus-reservar-asiento", parametros);
			if(response.getStatus()==200){ //Ok
				JSONArray jsonArray= response.getBody().getArray();
				JSONObject jsonObject=jsonArray.getJSONObject(0);
				//Agregando el menu
				List<TreeMap<String, Object>>menus=new  ArrayList<>();
				for (int i = 0; i < jsonObject.getJSONArray("Menu").length(); i++) {
				    JSONObject jsonMenu = (JSONObject) jsonObject.getJSONArray("Menu").get(i);
				    Menu omenu= new Menu();
				    omenu.setId(jsonMenu.getInt("IdMenu"));
				    omenu.setDenominacion(jsonMenu.get("NombreMenu").toString());
				    TreeMap<String, Object> menu= new TreeMap<>();
				    menu.put("Menu", omenu);
				    menus.add(menu);
				}
				asiento.setDisponible(Constantes.ASIENTO_BLOQUEADO);
				asiento.getObjectCiva().setReservaID(jsonObject.getInt("IdReserva"));
				asiento.setTarifa(jsonObject.getDouble("Precio"));///Valor temporales
				asiento.getObjectCiva().setMenus(menus);
				for(Component component : asiento.getChildren()){
					if(component instanceof Label && ((Label)component).getValue().equals(asiento.getNumeroAsiento())){
						Label label=(Label)component;
						label.setVisible(false);
						break;
					}
				}

				Image imagen=generarImagen(AsientoPool.imagenBloqueado, null, 18);
				asiento.appendChild(imagen);

				/*Agrega el asiento seleccionado a la lista de asientos seleccionados*/
				Listitem listitemAsientos = new Listitem();
				Listcell cell = new Listcell(asiento.getObjectCiva().getRutaBus().toUpperCase());
				listitemAsientos.appendChild(cell);
				cell = new Listcell(asiento.getNumeroAsiento());
				listitemAsientos.setValue(asiento);
				listitemAsientos.appendChild(cell);
				listAsientos.appendChild(listitemAsientos);

				/*Registra el bloqueo del asiento*/
				registrarBloqueoAsiento(asiento);
			}else{
				DlgMessage.information(response.getBody().toString());
			}
		}else if (asiento.getDisponible().intValue()==Constantes.ASIENTO_BLOQUEADO){
			/*Desbloquea el asiento*/
			HttpResponse<JsonNode> response=desbloquearAsiento(asiento);
			if(response.getStatus()==200){ //Ok
				//Elimina el asiento del listbox
				List<Listitem> items = listAsientos.getItems();
				for(int i=0; i<items.size(); i++){
					AsientoPool asiento2 = items.get(i).getValue();
					if(asiento2.getNumeroAsiento().equals(asiento.getNumeroAsiento())){
						listAsientos.removeItemAt(i);
						break;
					}
				}

			}else{
				DlgMessage.information(response.getBody().toString());
			}
		}

		/*	Habilita o deshablita el boton para pasar a la siguiente pestana	*/
		if(listAsientos.getItems().size()==0)
			btnNextTabVenta.setDisabled(true);
		else
			btnNextTabVenta.setDisabled(false);
	}

	/**
	 * Desbloquea un asiento
	 * @param asiento : Objeto asientoPool
	 * @return
	 * @throws Exception
	 */
	public static HttpResponse<JsonNode> desbloquearAsiento(AsientoPool asiento)throws Exception{
		String parametros="IdAsiento="+asiento.getAsientoID()+"&"+"IdReserva="+asiento.getObjectCiva().getReservaID().toString();
		HttpResponse<JsonNode> response=getPOST_REST("bus-eliminar-reserva", parametros);
		if(response.getStatus()==200){ //Ok
			/*Elimina el asiento registrado como bloqueado*/
			eliminarBloqueoAsiento(asiento.getObjectCiva().getReservaID().toString());

			asiento.setDisponible(Constantes.FALSE_VALUE); //Coloca el estado nuevamente como Disponible
			asiento.getObjectCiva().setReservaID(null);
			asiento.getObjectCiva().setReservaFechaExpirarion(null);
		    asiento.getObjectCiva().setMenus(null);
		    asiento.getObjectCiva().setPrecioAsiento(null);

		    Component componentImage=null;
			for(Component component : asiento.getChildren()){
				if(component instanceof Label && ((Label)component).getValue().equals(asiento.getNumeroAsiento())){
					Label label=(Label)component;
					label.setVisible(true);
				}else if(component instanceof Image && ((Image)component).getSrc().equals(AsientoPool.imagenBloqueado)){
					componentImage=component;
				}
			}
			if(componentImage!=null)
				asiento.removeChild(componentImage);
		}

		return response;
	}

	/***
	 * Desbloquea todos los asientos bloqueados
	 * @param listAsientos : Listbox con los asientos bloqueados
	 * @throws Exception
	 */
	public static void desbloquearAsientos(Listbox listAsientos)throws Exception{
		for(Listitem item :listAsientos.getItems()){
			AsientoPool asiento=item.getValue();
			desbloquearAsiento(asiento);
		}

		Util.limpiarListbox(listAsientos);
	}


	/**
	 * Inicializa(limpia los objetos existentes) el contenedor de los asientos.
	 */
	private static void inicializarEstructura(Groupbox groupbox){
		for(int i=groupbox.getChildren().size()-1; i>-1; i--){
			Component component = groupbox.getChildren().get(i);
			if(!(component instanceof Caption))
				groupbox.removeChild(component);
		}
	}
	/**
	 * Genera el objeto imagen para los pisos del bus
	 * @param src		: Path de la imagen a mostrar.
	 * @param width		: Ancho de la imagen.
	 * @param height	: Alto de la imagen.
	 * @return Image.
	 */
	private static Image generarImagen(String src, Integer width, Integer height){
		Image imagen = new Image();
		imagen.setSrc(src);
		if(width!=null)
			imagen.setWidth(String.valueOf(width)+"px");
		if(height!=null)
			imagen.setHeight(String.valueOf(height)+"px");
		return imagen;
	}

	public static void registrarBloqueoAsiento(AsientoPool asiento)throws Exception{
		OcupacionAsientosBloqueadosPool bloqueadosPool= new OcupacionAsientosBloqueadosPool();
		bloqueadosPool.setAgencia(asiento.getAgencia());
		bloqueadosPool.setCodigoTransaccion(asiento.getObjectCiva().getReservaID().toString());
		bloqueadosPool.setFechaPartida(Constantes.FORMAT_DATE.parse(asiento.getObjectCiva().getFechaSalidaBus()));
		bloqueadosPool.setHoraPartida(asiento.getObjectCiva().getHoraEmbarque());
		bloqueadosPool.setOperacion(Constantes.OPERADO_CIVA);
		bloqueadosPool.setNumeroAsiento(asiento.getNumeroAsiento());
		bloqueadosPool.setPiso(asiento.getNivelAsiento());
		bloqueadosPool.setRuta(asiento.getObjectCiva().getRutaBus().toUpperCase());
		bloqueadosPool.setUsuario(asiento.getUsuario());
		bloqueadosPool.setEstadoRegistro(Constantes.VALUE_ACTIVO);
		UtilData.auditarRegistro(bloqueadosPool, asiento.getUsuario(), Executions.getCurrent());
		ServiceLocator.getOcupacionAsientosBloqueadosPoolManager().guardar(bloqueadosPool);
	}


	public static void eliminarBloqueoAsiento(String codigoTransaccion)throws Exception{
		ServiceLocator.getOcupacionAsientosBloqueadosPoolManager().delete(codigoTransaccion);
	}


	public static void actualizarNumeroComprobante(String numeroPedido, String numeroBoletoEnviado, String nuevoNumeroBoleto)throws Exception{
		try {
			String parametros="NumeroPedido="+numeroPedido+"&NumeroBoleto="+numeroBoletoEnviado+"&NuevoBoleto"+nuevoNumeroBoleto;
			HttpResponse<JsonNode> response=getPOST_REST("actualizar-boleto", parametros);
			if(response.getStatus()==200){ //Ok

			}else{

			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}


	public static String enviarVenta(Pasajero pasajero, Cliente cliente, Long idReserva, String idMenu, String numeroBoleto)throws Exception{
		String numeroPedido=null;
		String tipoDocumento=null;
		switch (pasajero.getTipoDocumento().getId()) {
		case Constantes.ID_TIPDOC_DNI:
			tipoDocumento="1";
			break;
		case Constantes.ID_TIPDOC_PASAPORTE:
			tipoDocumento="5";
			break;
		default:
			tipoDocumento="5";
			break;
		}
		String numeroDocumento=pasajero.getNumeroDocumento();
		String datosPasajero=pasajero.toString();
		String ruc=null;
		String razonSocial=null;
		String direccion=null;
		if(cliente!=null){
			ruc=cliente.getNumeroDocumento().trim();
			razonSocial=cliente.getRazonSocial().trim();
			direccion=(cliente.getDireccion()!=null?cliente.getDireccion().trim():null);
		}

		String parametros="Pasajeros={\"TipoDocumento\":{\"1\":\""+tipoDocumento+"\"}"
				                  + ",\"NumeroDocumento\":{\"1\":\""+numeroDocumento+"\"}"
				                  + ",\"IdReserva\":{\"1\":\""+idReserva+"\"},"
				                  + "\"DatosPasajero\":{\"1\":\""+datosPasajero+"\"}"
//				                  + ",\"Email\":{\"1\":\"emails@gmail.com\"}"
				                  + ",\"Menu\":{\"1\":\""+idMenu+"\"}";
		if(ruc!=null)
			parametros+=",\"RUC\":{\"1\":\""+ruc+"\"}";
		if(razonSocial!=null)
			parametros+=",\"RazonSocial\":{\"1\":\""+razonSocial+"\"}";
		if(direccion!=null)
			parametros+=",\"Direccion\":{\"1\":\""+direccion+"\"}";
		parametros+=",\"NumeroBoleto\":{\"1\":\""+numeroBoleto+"\"}";
		parametros+="}";
		HttpResponse<JsonNode> response=getPOST_REST("confirmar-venta", parametros);
//		Log.info("responseStatus : " + response.getStatus());
		if(response.getStatus()==200){ //Ok
			String[] yresult=response.getBody().toString().split(",");
			for(String res : yresult){
//				Log.info("Consultando numero de pedido...");
				if(res.indexOf("NumeroPedido")>0){
					numeroPedido=res.split(":")[1].toString().replaceAll("\"", "").trim();
					numeroPedido=numeroPedido.replaceAll("}", "").trim();
//					Log.info("numero de pedido : " + numeroPedido);
					break;
				}
			}
		}else{
			throw new Exception(response.getBody().toString());
		}

		return numeroPedido;
	}

	/**
	 * Realiza la anulacion del Boleto
	 * @param numeroBoleto	: numero de Boleto - Tepsa a anular
	 * @throws Exception
	 */
	public static void anularBoleto(String numeroBoleto)throws Exception{
		String parametros="NumeroBoleto="+numeroBoleto+"";
		HttpResponse<String> response=getPOST_REST_STRING("anular-boleto", parametros);
		if(response.getStatus()==200){
			String[] ybody=response.getBody().split(":");
			if(!(ybody[1].toString().replace("}", "").trim().equals("true"))){
				throw new Exception("API:CIVA - No es posible realizar la anulación. \n"+response.getBody());
			}
		}else{
			throw new Exception(response.getBody().toString());
		}
	}
}
