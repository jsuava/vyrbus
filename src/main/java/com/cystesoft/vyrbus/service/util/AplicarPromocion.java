/**
 * Proyecto		: SISVYR
 * Sistema		: Sistema de Ventas y Reservas
 * Descripción	: 
 * Autor		: José Sullo Avalos
 * Fecha		: 14/05/2013
 */
package com.cystesoft.vyrbus.service.util;

import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zul.Button;
import org.zkoss.zul.Caption;
import org.zkoss.zul.Div;
import org.zkoss.zul.Doublebox;
import org.zkoss.zul.Grid;
import org.zkoss.zul.Image;
import org.zkoss.zul.Label;
import org.zkoss.zul.Row;
import org.zkoss.zul.Rows;
import org.zkoss.zul.Space;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

import com.cystesoft.vyrbus.model.bean.Agencia;
import com.cystesoft.vyrbus.model.bean.Promocion;
import com.cystesoft.vyrbus.model.bean.TemporadaAlta;
import com.cystesoft.vyrbus.model.bean.TipoCambio;
import com.cystesoft.vyrbus.service.locator.ServiceLocator;
import com.cystesoft.vyrbus.view.ui.DlgMessage;

/**
 * @author Jose
 *
 */
public class AplicarPromocion{
	private Integer idRuta;
	private Integer idServicio;
	private Integer idPuntoVenta;
	private Integer idCanalVenta;
	private String pasajeroNuevo;
	private String cantidadViajes;
	private String asiento;
	private Long idCliente;
	private Boolean idaVuelta;
	private Integer idFormaPago;
	private Integer idTipoFormaPago;
	private Integer idTarjetaCredito;
	private Date fechaPartida;
	private Boolean pasajeroFrecuente;
	private Doublebox dblTarifa; 
	private Doublebox dblDescuento; 
	private Doublebox dblImporte;
	private Doublebox dblRecargo;
	private Label lblPromocion;
	private Image imgQuitarPromocion;
	private Textbox txtIdPromocion;
//	private PagoSoles pagoSoles;
	private Agencia agencia;
	private Doublebox dblDescuentoOtraMoneda;
	private Doublebox dblImporteOtraMoneda;
	private String horaPartida; //12/12/2015 - jabanto
	
	public AplicarPromocion(Integer idRuta, Integer idServicio,
			Integer idPuntoVenta, Integer idCanalVenta, String pasajeroNuevo,
			String cantidadViajes, String asiento, Long idCliente,
			Boolean idaVuelta, Integer idFormaPago, Integer idTipoFormaPago,
			Integer idTarjetaCredito, Date fechaPartida,
			Boolean pasajeroFrecuente, Doublebox dblTarifa, Doublebox dblDescuento, Doublebox dblImporte, 
			Doublebox dblRecargo, Label lblPromocion, Image imgQuitarPromocion, Textbox txtIdPromocion,
			String horaPartida) {
		super();
		this.idRuta = idRuta;
		this.idServicio = idServicio;
		this.idPuntoVenta = idPuntoVenta;
		this.idCanalVenta = idCanalVenta;
		this.pasajeroNuevo = pasajeroNuevo;
		this.cantidadViajes = cantidadViajes;
		this.asiento = asiento;
		this.idCliente = idCliente;
		this.idaVuelta = idaVuelta;
		this.idFormaPago = idFormaPago;
		this.idTipoFormaPago = idTipoFormaPago;
		this.idTarjetaCredito = idTarjetaCredito;
		this.fechaPartida = fechaPartida;
		this.pasajeroFrecuente = pasajeroFrecuente;
		this.dblTarifa = dblTarifa;
		this.dblDescuento = dblDescuento;
		this.dblImporte = dblImporte;
		this.dblRecargo = dblRecargo;
		this.lblPromocion = lblPromocion;
		this.imgQuitarPromocion = imgQuitarPromocion;
		this.txtIdPromocion = txtIdPromocion;
		this.horaPartida=horaPartida;
	}
	
	
	public AplicarPromocion(Integer idRuta, Integer idServicio,
			Integer idPuntoVenta, Integer idCanalVenta, String pasajeroNuevo,
			String cantidadViajes, String asiento, Long idCliente,
			Boolean idaVuelta, Integer idFormaPago, Integer idTipoFormaPago,
			Integer idTarjetaCredito, Date fechaPartida,
			Boolean pasajeroFrecuente, Doublebox dblTarifa, Doublebox dblDescuento, Doublebox dblImporte, 
			Doublebox dblRecargo, Label lblPromocion, Image imgQuitarPromocion, Textbox txtIdPromocion,
			Agencia agencia,Doublebox dblImporteOtraMoneda,Doublebox dblDescuentoOtraMoneda,
			String horaPartida) {
		super();
		this.idRuta = idRuta;
		this.idServicio = idServicio;
		this.idPuntoVenta = idPuntoVenta;
		this.idCanalVenta = idCanalVenta;
		this.pasajeroNuevo = pasajeroNuevo;
		this.cantidadViajes = cantidadViajes;
		this.asiento = asiento;
		this.idCliente = idCliente;
		this.idaVuelta = idaVuelta;
		this.idFormaPago = idFormaPago;
		this.idTipoFormaPago = idTipoFormaPago;
		this.idTarjetaCredito = idTarjetaCredito;
		this.fechaPartida = fechaPartida;
		this.pasajeroFrecuente = pasajeroFrecuente;
		this.dblTarifa = dblTarifa;
		this.dblDescuento = dblDescuento;
		this.dblImporte = dblImporte;
		this.dblRecargo = dblRecargo;
		this.lblPromocion = lblPromocion;
		this.imgQuitarPromocion = imgQuitarPromocion;
		this.txtIdPromocion = txtIdPromocion;
//		this.pagoSoles=pagoSoles;
		this.agencia=agencia;
		this.dblImporteOtraMoneda=dblImporteOtraMoneda;
		this.dblDescuentoOtraMoneda=dblDescuentoOtraMoneda;
		this.horaPartida=horaPartida;
	}
	
	public AplicarPromocion() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @return the idRuta
	 */
	public Integer getIdRuta() {
		return idRuta;
	}
	/**
	 * @param idRuta the idRuta to set
	 */
	public void setIdRuta(Integer idRuta) {
		this.idRuta = idRuta;
	}

	/**
	 * @return the idServicio
	 */
	public Integer getIdServicio() {
		return idServicio;
	}
	/**
	 * @param idServicio the idServicio to set
	 */
	public void setIdServicio(Integer idServicio) {
		this.idServicio = idServicio;
	}

	/**
	 * @return the idPuntoVenta
	 */
	public Integer getIdPuntoVenta() {
		return idPuntoVenta;
	}
	/**
	 * @param idPuntoVenta the idPuntoVenta to set
	 */
	public void setIdPuntoVenta(Integer idPuntoVenta) {
		this.idPuntoVenta = idPuntoVenta;
	}

	/**
	 * @return the idCanalVenta
	 */
	public Integer getIdCanalVenta() {
		return idCanalVenta;
	}
	/**
	 * @param idCanalVenta the idCanalVenta to set
	 */
	public void setIdCanalVenta(Integer idCanalVenta) {
		this.idCanalVenta = idCanalVenta;
	}

	/**
	 * @return the pasajeroNuevo
	 */
	public String getPasajeroNuevo() {
		return pasajeroNuevo;
	}
	/**
	 * @param pasajeroNuevo the pasajeroNuevo to set
	 */
	public void setPasajeroNuevo(String pasajeroNuevo) {
		this.pasajeroNuevo = pasajeroNuevo;
	}

	/**
	 * @return the cantidadViajes
	 */
	public String getCantidadViajes() {
		return cantidadViajes;
	}
	/**
	 * @param cantidadViajes the cantidadViajes to set
	 */
	public void setCantidadViajes(String cantidadViajes) {
		this.cantidadViajes = cantidadViajes;
	}

	/**
	 * @return the asiento
	 */
	public String getAsiento() {
		return asiento;
	}
	/**
	 * @param asiento the asiento to set
	 */
	public void setAsiento(String asiento) {
		this.asiento = asiento;
	}

	/**
	 * @return the idCliente
	 */
	public Long getIdCliente() {
		return idCliente;
	}
	/**
	 * @param idCliente the idCliente to set
	 */
	public void setIdCliente(Long idCliente) {
		this.idCliente = idCliente;
	}

	/**
	 * @return the idaVuelta
	 */
	public Boolean getIdaVuelta() {
		return idaVuelta;
	}
	/**
	 * @param idaVuelta the idaVuelta to set
	 */
	public void setIdaVuelta(Boolean idaVuelta) {
		this.idaVuelta = idaVuelta;
	}

	/**
	 * @return the idFormaPago
	 */
	public Integer getIdFormaPago() {
		return idFormaPago;
	}
	/**
	 * @param idFormaPago the idFormaPago to set
	 */
	public void setIdFormaPago(Integer idFormaPago) {
		this.idFormaPago = idFormaPago;
	}

	/**
	 * @return the idTipoFormaPago
	 */
	public Integer getIdTipoFormaPago() {
		return idTipoFormaPago;
	}
	/**
	 * @param idTipoFormaPago the idTipoFormaPago to set
	 */
	public void setIdTipoFormaPago(Integer idTipoFormaPago) {
		this.idTipoFormaPago = idTipoFormaPago;
	}

	/**
	 * @return the idTarjetaCredito
	 */
	public Integer getIdTarjetaCredito() {
		return idTarjetaCredito;
	}
	/**
	 * @param idTarjetaCredito the idTarjetaCredito to set
	 */
	public void setIdTarjetaCredito(Integer idTarjetaCredito) {
		this.idTarjetaCredito = idTarjetaCredito;
	}

	/**
	 * @return the fechaPartida
	 */
	public Date getFechaPartida() {
		return fechaPartida;
	}
	/**
	 * @param fechaPartida the fechaPartida to set
	 */
	public void setFechaPartida(Date fechaPartida) {
		this.fechaPartida = fechaPartida;
	}

	/**
	 * @return the pasajeroFrecuente
	 */
	public Boolean isPasajeroFrecuente() {
		return pasajeroFrecuente;
	}
	/**
	 * @param pasajeroFrecuente the pasajeroFrecuente to set
	 */
	public void setPasajeroFrecuente(Boolean pasajeroFrecuente) {
		this.pasajeroFrecuente = pasajeroFrecuente;
	}

	/**
	 * @return the dblTarifa
	 */
	public Doublebox getDblTarifa() {
		return dblTarifa;
	}
	/**
	 * @param dblTarifa the dblTarifa to set
	 */
	public void setDblTarifa(Doublebox dblTarifa) {
		this.dblTarifa = dblTarifa;
	}

	/**
	 * @return the dblDescuento
	 */
	public Doublebox getDblDescuento() {
		return dblDescuento;
	}
	/**
	 * @param dblDescuento the dblDescuento to set
	 */
	public void setDblDescuento(Doublebox dblDescuento) {
		this.dblDescuento = dblDescuento;
	}

	/**
	 * @return the dblImporte
	 */
	public Doublebox getDblImporte() {
		return dblImporte;
	}
	/**
	 * @param dblImporte the dblImporte to set
	 */
	public void setDblImporte(Doublebox dblImporte) {
		this.dblImporte = dblImporte;
	}

	/**
	 * @return the dblRecargo
	 */
	public Doublebox getDblRecargo() {
		return dblRecargo;
	}
	/**
	 * @param dblRecargo the dblRecargo to set
	 */
	public void setDblRecargo(Doublebox dblRecargo) {
		this.dblRecargo = dblRecargo;
	}

	/**
	 * @return the lblPromocion
	 */
	public Label getLblPromocion() {
		return lblPromocion;
	}
	/**
	 * @param lblPromocion the lblPromocion to set
	 */
	public void setLblPromocion(Label lblPromocion) {
		this.lblPromocion = lblPromocion;
	}

	/**
	 * @return the imgQuitarPromocion
	 */
	public Image getImgQuitarPromocion() {
		return imgQuitarPromocion;
	}
	/**
	 * @param imgQuitarPromocion the imgQuitarPromocion to set
	 */
	public void setImgQuitarPromocion(Image imgQuitarPromocion) {
		this.imgQuitarPromocion = imgQuitarPromocion;
	}

	/**
	 * @return the txtIdPromocion
	 */
	public Textbox getTxtIdPromocion() {
		return txtIdPromocion;
	}
	/**
	 * @param txtIdPromocion the txtIdPromocion to set
	 */
	public void setTxtIdPromocion(Textbox txtIdPromocion) {
		this.txtIdPromocion = txtIdPromocion;
	}

	/**
	 * Obtiene las promociones vigentes de la base de datos y las muestras en pantalla.
	 */
	@SuppressWarnings("deprecation")
	public Window loadPromociones(boolean paxfre, String idCliente, String fechaPartida){
		try{
			List<Promocion> lstPromociones = ServiceLocator.getPromocionManager().buscarPromocionesAplicables(paxfre, idCliente, Constantes.VALUE_ACTIVO, fechaPartida);
			
			/*	Verificando si la fecha de viaje esta considerado como Temporada Alta	*/
			Date date=Constantes.FORMAT_DATE.parse(fechaPartida);
			String dia=Constantes.FORMAT_DAY.format(date);
			String mes=Constantes.FORMAT_MONTH.format(date);
			String anio=Constantes.FORMAT_YEAR.format(date);				
			List<TemporadaAlta>lstTemAlta = ServiceLocator.getTemporadaAltaManager().buscarTemporadaAlta(anio, mes, dia);
			
			if(lstPromociones.size()>0){
				final Window win = new Window("", "normal", true);
				win.setHeight("300px");
				win.setContentStyle("overflow:auto !important");
				Caption caption = new Caption("PROMOCIONES", "resources/mp_promocion.png");
				win.appendChild(caption);
				Grid grid = new Grid();
				grid.setWidth("250px");
				Rows rows = new Rows();
				Row row = null;
				for(int i=0; i<lstPromociones.size(); i++){
					Promocion promocion = lstPromociones.get(i);
					/*	Verificamos que si es Temporada Alta la promocion no tenga restriccion de Temporada	*/
					if(lstTemAlta.size()==0 || (lstTemAlta.size()>0 && promocion.getEnTemporada().equals("*"))){
						if(i%2==0){
							if(i!=0 && row!=null){
								rows.appendChild(row);
							}
							row = new Row();
						}							
						final Button button = new Button(promocion.getDenominacion());
						button.addEventListener(Events.ON_CLICK, new EventListener<Event>() {
							public void onEvent(Event e){
								executePromocion(button.getId(), true);
							}
						});
						button.setId(promocion.getId().toString());
						button.setWidth("120px");
						button.setHeight("35px");
						button.setStyle("font-size:9px");
						if(row==null)
							row = new Row();
						row.appendChild(button);
					}
				}
				if(row!=null){
					rows.appendChild(row);
					grid.appendChild(rows);
					win.appendChild(grid);
					Space space = new Space();
					space.setHeight("7px");
					win.appendChild(space);
					Div div = new Div();
					div.setAlign("center");
					Button button = new Button("Cerrar", "resources/mp_cerrarEnabled.png");
					button.addEventListener(Events.ON_CLICK, new EventListener<Event>() {
						public void onEvent(Event e){
							win.onClose();
						}
					});
					button.setHeight("28px");
					div.appendChild(button);
					win.appendChild(div);
					return win;
				}else{
					DlgMessage.information(Messages.getString("WndVentaReserva.information.noPromociones"));
					return null;
				}
			}else{
				DlgMessage.information(Messages.getString("WndVentaReserva.information.noPromociones"));
				return null;
			}
		}catch(Exception ex){
			DlgMessage.information(this.getClass().getSimpleName()+" "+ex.getMessage());
			return null;
		}
	}
	
	/**
	 * Realiza la validacion de la promocion y la aplica si cumple con los requisitos.
	 * @param idPromocion	: Identificador de la promocion seleccionada.
	 * @param mostrarMsg	: indica si se mostrara los mensajes de validacion.
	 * @return Objeto promocion.
	 */
	public Promocion executePromocion(String idPromocion, boolean mostrarMsg){
		Promocion promocionAplicada = null;
		try{
			Promocion promocion = ServiceLocator.getPromocionManager().buscarPorId(Long.valueOf(idPromocion));
			String[] aExpresion = promocion.getExpresion().split(";");
			Map<String, List<String>> mapExpresionPromocion = new LinkedHashMap<String, List<String>>();
			for(int i=0; i<aExpresion.length; i++){
				String expresion = aExpresion[i];
				String key = expresion.substring(0, 4);
				String[] aValue = expresion.substring(5).split(",");
				List<String> value = new ArrayList<String>();
				if(aValue.length==1){
					if(!aValue[0].equals("*")){
						value.add(aValue[0]);
						mapExpresionPromocion.put(key, value);
					}
				}else{
					for(String sValue : aValue){
						value.add(sValue);
					}
					mapExpresionPromocion.put(key, value);
				}				
			}
			
//			DetalleItinerario detalle = getObjetoConfirmar().getDetalleItinerario();
			Map<String, List<String>> mapExpresionVenta = new LinkedHashMap<String, List<String>>();
			/*	Enviando la Ruta	*/
			List<String> value = new ArrayList<String>();
			value.add(getIdRuta().toString());
			mapExpresionVenta.put(Promocion.TOKEN_RUTA, value);
			/*	Enviando el Servicio	*/
			value = new ArrayList<String>();
			value.add(getIdServicio().toString());
			mapExpresionVenta.put(Promocion.TOKEN_SERVICIO, value);
			/*	Enviando el Punto de Venta	*/
			value = new ArrayList<String>();
			value.add(getIdPuntoVenta().toString());
			mapExpresionVenta.put(Promocion.TOKEN_PUNTO_VENTA, value);
			/*	Enviando el Canal de Venta	*/
			value = new ArrayList<String>();
			value.add(getIdCanalVenta().toString());
			mapExpresionVenta.put(Promocion.TOKEN_CANAL_VENTA, value);
			
			/*	Enviando si es Pasajero Nuevo	*/
			
			/*	Enviando la Cantidad de Viajes del Pasajero	*/
			
			/*	Enviando el Asiento	*/
			value = new ArrayList<String>();
			value.add(getAsiento());  
			mapExpresionVenta.put(Promocion.TOKEN_ASIENTO, value);
			/*	Enviando el Cliente	*/
			value = new ArrayList<String>();
			value.add(getIdCliente()!=null?getIdCliente().toString():"*");
			mapExpresionVenta.put(Promocion.TOKEN_CLIENTE, value);
			/*	Enviando si es Ida y Vuelta	*/
			value = new ArrayList<String>();
			value.add(getIdaVuelta()?"S":"*");
			mapExpresionVenta.put(Promocion.TOKEN_IDA_VUELTA, value);
			/*	Enviando la Forma de Pago	*/
			value = new ArrayList<String>();
			value.add(getIdFormaPago().toString());
			mapExpresionVenta.put(Promocion.TOKEN_FORMA_PAGO, value);
			/*	Enviado el Tipo de Forma de Pago	*/
			value = new ArrayList<String>();
			value.add(getIdTipoFormaPago().toString());
			mapExpresionVenta.put(Promocion.TOKEN_TIPO_FORMA_PAGO, value);
			/*	Enviando la Tarjeta de Credito	*/
			value = new ArrayList<String>();
			if(getIdTarjetaCredito()!=null)
				value.add(getIdTarjetaCredito().toString());
			else
				value.add("*");
			mapExpresionVenta.put(Promocion.TOKEN_TARJETA_CREDITO, value);
			/*	Enviando la Temporada	*/
			value = new ArrayList<String>();
			if(getFechaPartida()!=null){
				String anio = Constantes.FORMAT_YEAR.format(getFechaPartida());
				String mes = Constantes.FORMAT_MONTH.format(getFechaPartida());
				String dia = Constantes.FORMAT_DAY.format(getFechaPartida());
				List<TemporadaAlta> lstTemporada = ServiceLocator.getTemporadaAltaManager().buscarTemporadaAlta(anio, mes, dia);
				if(lstTemporada.size()>0)
					value.add("*");
				else
					value.add("B");
			}else
				value.add("*");
			mapExpresionVenta.put(Promocion.TOKEN_TEMPORADA, value);
			/*	Enviando si es PaxFre*/
			value = new ArrayList<String>();
			if(isPasajeroFrecuente())
				value.add("S");
			else
				value.add("*");
			mapExpresionVenta.put(Promocion.TOKEN_PAXFRE, value);
			
			/* Enviando de la hora de partida del servicio - 14/12/2015 - jabanto*/
			value = new ArrayList<String>();
			value.add(getHoraPartida().replaceAll(":", "."));
			mapExpresionVenta.put(Promocion.TOKEN_HORA_PARTIDA, value);
			
			
			
			boolean aplicarPromocion = false;
			
			for(String key : mapExpresionPromocion.keySet()){
				if(!mapExpresionVenta.containsKey(key)){
//					if(promocion.getEsTarifa().intValue()==Constantes.FALSE_VALUE)
					if(mostrarMsg)
						showMessage(key);
					aplicarPromocion = false;
					break;
				}else{
					List<String> valueVenta = mapExpresionVenta.get(key);
					List<String> valuesPromocion = mapExpresionPromocion.get(key);
					if(valuesPromocion.contains(valueVenta.get(0)))
						aplicarPromocion = true;
					else{
						aplicarPromocion = false;
//						if(promocion.getEsTarifa().intValue()==Constantes.FALSE_VALUE)
						if(mostrarMsg)
							showMessage(key);
						break;
					}
				}				
			}
			
			if(aplicarPromocion){
				/*	Si la promocion es la tarifa a considerar	*/
				promocionAplicada = promocion;
				if(promocion.getEsTarifa().intValue()==Constantes.TRUE_VALUE){
					dblTarifa.setValue(promocion.getPorImporte());
					dblImporte.setValue(dblTarifa.getValue()-dblDescuento.getValue()+(dblRecargo==null?0.0:(dblRecargo.getValue()==null?0.0:dblRecargo.getValue())));
				}else{
					Double dsct = 0.0;
					txtIdPromocion.setText(promocion.getId().toString());
//					if(promocion.getTipoDescuento().equals(Promocion.PROMOCION_TIPO_DESCUENTO_VALUE_FIJO)){
//						if(promocion.getValorDescuento()!=null)
//							dsct = promocion.getValorDescuento();
//						else
//							dsct = dblTarifa.getValue() - promocion.getPorImporte();
//					}else
//						dsct = (dblTarifa.getValue() * promocion.getValorDescuento())/100;
					
					
					//08/08/2015 - jabanto
//					if(pagoSoles==null){
//					if(dblImporteOtraMoneda==null && dblDescuentoOtraMoneda==null){
					if(promocion.getTipoDescuento().equals(Promocion.PROMOCION_TIPO_DESCUENTO_VALUE_FIJO)){
						if(promocion.getValorDescuento()!=null)
							dsct = promocion.getValorDescuento();
						else
							dsct = dblTarifa.getValue() - promocion.getPorImporte();
					}else
						dsct = (dblTarifa.getValue() * promocion.getValorDescuento())/100;
					dblImporte.setValue(Util.calculoTotalPagar(dblTarifa.getValue(), dsct, .00));
					dblDescuento.setValue(dblTarifa.getValue()- dblImporte.getValue());
					dblDescuento.setTooltiptext("Aplicando la Promocion : "+promocionAplicada.getDenominacion());
					lblPromocion.setValue("Promocion : "+promocionAplicada.getDenominacion()+"  <=======>  Beneficio : "+
							(promocionAplicada.getTipoDescuento().equals(Promocion.PROMOCION_TIPO_DESCUENTO_VALUE_PORCENTAJE)
									?(Util.toNumberFormat(promocionAplicada.getValorDescuento(),0)+"%"):("S/. "+Util.toNumberFormat(dblDescuento.getValue(), 2))));
					imgQuitarPromocion.setVisible(true);
						
					/*Valida si es otra moneda*/
					if(dblImporteOtraMoneda!=null && dblDescuentoOtraMoneda!=null){
						/*Valida si es el la ida o el retorno*/
//						if(dblDescuento.getId().equals("dblDescuentoRetorno") && dblTarifa.getId().equals("dblTarifaRetorno")){
//							//Es retorno
//							if(promocion.getTipoDescuento().equals(Promocion.PROMOCION_TIPO_DESCUENTO_VALUE_FIJO)){
//								if(promocion.getValorDescuento()!=null)
//									dsct = promocion.getValorDescuento();
//								else
//									dsct = pagoSoles.getTarifaRetorno() - promocion.getPorImporte();
//							}else
//								dsct = (pagoSoles.getTarifaRetorno() * promocion.getValorDescuento())/100;
//							
//							Double importe=Util.calculoTotalPagar(pagoSoles.getTarifaRetorno(), dsct, .00);
//							pagoSoles.setImportePagado(importe);
//							pagoSoles.setDescuentoRetorno(pagoSoles.getTarifaRetorno()-pagoSoles.getImportePagado());
//							
//						}else{
							//Es ida
//							if(promocion.getTipoDescuento().equals(Promocion.PROMOCION_TIPO_DESCUENTO_VALUE_FIJO)){
//								if(promocion.getValorDescuento()!=null)
//									dsct = promocion.getValorDescuento();
//								else
//									dsct = pagoSoles.getTarifa() - promocion.getPorImporte();
//							}else
//								dsct = (pagoSoles.getTarifa() * promocion.getValorDescuento())/100;
//							
//							Double importe=Util.calculoTotalPagar(pagoSoles.getTarifa(), dsct, .00);
//							pagoSoles.setImportePagado(importe);
//							pagoSoles.setDescuento(pagoSoles.getTarifa()-pagoSoles.getImportePagado());
//						}
						
						/*Dedonda el descuento*/
						TipoCambio tipoCambio=Util.getTipoCambioEquiMonedaLocal(agencia, dsct,true);
						Double dsctOtraMoneda=(tipoCambio!=null?tipoCambio.getEquivalenteMonedaLocal():dsct);
						
						tipoCambio=Util.getTipoCambioEquiMonedaLocal(agencia, dblTarifa.getValue(),false);
						Double tarifaOtraMoneda=(tipoCambio!=null?tipoCambio.getEquivalenteMonedaLocal():dblTarifa.getValue());
						
						dblImporteOtraMoneda.setValue(Util.calculoTotalPagar(tarifaOtraMoneda, dsctOtraMoneda, .00));
						dblDescuentoOtraMoneda.setValue(tarifaOtraMoneda - dblImporteOtraMoneda.getValue());
						dblDescuentoOtraMoneda.setTooltiptext("Aplicando la Promocion : "+promocionAplicada.getDenominacion());
						lblPromocion.setValue("Promocion : "+promocionAplicada.getDenominacion()+"  <=======>  Beneficio : "+
								(promocionAplicada.getTipoDescuento().equals(Promocion.PROMOCION_TIPO_DESCUENTO_VALUE_PORCENTAJE)
										?(Util.toNumberFormat(promocionAplicada.getValorDescuento(),0)+"%"):(tipoCambio.getTipoMoneda().getSimboloMonetario()+" "+Util.toNumberFormat(dblDescuentoOtraMoneda.getValue(), 2))));
						imgQuitarPromocion.setVisible(true);
						
					}
					
					
				}
			}			
			System.out.println(mapExpresionPromocion.toString());
			System.out.println(mapExpresionVenta.toString());
		}catch(Exception ex){
			ex.printStackTrace();
			DlgMessage.error(this.getClass().getSimpleName()+" "+ex.getMessage());
		}
		return promocionAplicada;
	}
	
	/**
	 * Muestra el mensaje personalizado si la venta no cumple con algun criterio de la promocion.
	 * @param key	: Token a comparar para mostrar el mensaje.
	 */
	private void showMessage(String key){
		if(key.equals(Promocion.TOKEN_RUTA))
			DlgMessage.information(Messages.getString("WndPromociones.information.noRuta"));
		else if(key.equals(Promocion.TOKEN_SERVICIO))
			DlgMessage.information(Messages.getString("WndPromociones.information.noServicio"));
		else if(key.equals(Promocion.TOKEN_CANAL_VENTA))
			DlgMessage.information(Messages.getString("WndPromociones.information.noCanalVenta"));
		else if(key.equals(Promocion.TOKEN_PUNTO_VENTA))
			DlgMessage.information(Messages.getString("WndPromociones.information.noPuntoVenta"));
		else if(key.equals(Promocion.TOKEN_PASAJERO_NUEVO))
			DlgMessage.information(Messages.getString("WndPromociones.information.noPasajeroNuevo"));
		else if(key.equals(Promocion.TOKEN_CANTIDAD_VIAJES))
			DlgMessage.information(Messages.getString("WndPromociones.information.noCantidadViajes"));
		else if(key.equals(Promocion.TOKEN_ASIENTO))
			DlgMessage.information(Messages.getString("WndPromociones.information.noAsiento"));
		else if(key.equals(Promocion.TOKEN_CLIENTE))
			DlgMessage.information(Messages.getString("WndPromociones.information.noCliente"));
		else if(key.equals(Promocion.TOKEN_IDA_VUELTA))
			DlgMessage.information(Messages.getString("WndPromociones.information.noIdaVuelta"));
		else if(key.equals(Promocion.TOKEN_FORMA_PAGO))
			DlgMessage.information(Messages.getString("WndPromociones.information.noFormaPago"));
		else if(key.equals(Promocion.TOKEN_TIPO_FORMA_PAGO))
			DlgMessage.information(Messages.getString("WndPromociones.information.noTipoFormaPago"));
		else if(key.equals(Promocion.TOKEN_TARJETA_CREDITO))
			DlgMessage.information(Messages.getString("WndPromociones.information.noTarjetaCredito"));
		else if(key.equals(Promocion.TOKEN_TEMPORADA))
			DlgMessage.information(Messages.getString("WndPromociones.information.noTemporada"));
		else if(key.equals(Promocion.TOKEN_PAXFRE))
			DlgMessage.information(Messages.getString("WndPromociones.information.noPaxFre"));
	}

//	/**
//	 * @return the pagoSoles
//	 */
//	public PagoSoles getPagoSoles() {
//		return pagoSoles;
//	}
//
//	/**
//	 * @param pagoSoles the pagoSoles to set
//	 */
//	public void setPagoSoles(PagoSoles pagoSoles) {
//		this.pagoSoles = pagoSoles;
//	}


	/**
	 * @return the agencia
	 */
	public Agencia getAgencia() {
		return agencia;
	}


	/**
	 * @param agencia the agencia to set
	 */
	public void setAgencia(Agencia agencia) {
		this.agencia = agencia;
	}


	/**
	 * @return the dblDescuentoOtraMoneda
	 */
	public Doublebox getDblDescuentoOtraMoneda() {
		return dblDescuentoOtraMoneda;
	}


	/**
	 * @param dblDescuentoOtraMoneda the dblDescuentoOtraMoneda to set
	 */
	public void setDblDescuentoOtraMoneda(Doublebox dblDescuentoOtraMoneda) {
		this.dblDescuentoOtraMoneda = dblDescuentoOtraMoneda;
	}


	/**
	 * @return the dblImporteOtraMoneda
	 */
	public Doublebox getDblImporteOtraMoneda() {
		return dblImporteOtraMoneda;
	}


	/**
	 * @param dblImporteOtraMoneda the dblImporteOtraMoneda to set
	 */
	public void setDblImporteOtraMoneda(Doublebox dblImporteOtraMoneda) {
		this.dblImporteOtraMoneda = dblImporteOtraMoneda;
	}


	/**
	 * @return the horaPartida
	 */
	public String getHoraPartida() {
		return horaPartida;
	}


	/**
	 * @param horaPartida the horaPartida to set
	 */
	public void setHoraPartida(String horaPartida) {
		this.horaPartida = horaPartida;
	}
	
	
//	/**
//	 * Realiza la validacion de la promocion y la aplica si cumple con los requisitos.
//	 * @param promocion : Objeto promocion
//	 * @param lstTemporada	: lista temporadas altas
//	 * @param aplicarDescuento
//	 * @return
//	 */
//	public Promocion ejecutarPromocion(Promocion promocion, boolean xDescuento){
//		Promocion promocionAplicada = null;
//		try{
//			//Promocion promocion = ServiceLocator.getPromocionManager().buscarPorId(Long.valueOf(idPromocion));
//			String[] aExpresion = promocion.getExpresion().split(";");
//			Map<String, List<String>> mapExpresionPromocion = new LinkedHashMap<String, List<String>>();
//			for(int i=0; i<aExpresion.length; i++){
//				String expresion = aExpresion[i];
//				String key = expresion.substring(0, 4);
//				String[] aValue = expresion.substring(5).split(",");
//				List<String> value = new ArrayList<String>();
//				if(aValue.length==1){
//					if(!aValue[0].equals("*")){
//						value.add(aValue[0]);
//						mapExpresionPromocion.put(key, value);
//					}
//				}else{
//					for(String sValue : aValue){
//						value.add(sValue);
//					}
//					mapExpresionPromocion.put(key, value);
//				}				
//			}
//			
////			DetalleItinerario detalle = getObjetoConfirmar().getDetalleItinerario();
//			Map<String, List<String>> mapExpresionVenta = new LinkedHashMap<String, List<String>>();
//			/*	Enviando la Ruta	*/
//			List<String> value = new ArrayList<String>();
//			value.add(getIdRuta().toString());
//			mapExpresionVenta.put(Promocion.TOKEN_RUTA, value);
//			/*	Enviando el Servicio	*/
//			value = new ArrayList<String>();
//			value.add(getIdServicio().toString());
//			mapExpresionVenta.put(Promocion.TOKEN_SERVICIO, value);
//			/*	Enviando el Punto de Venta	*/
//			value = new ArrayList<String>();
//			value.add(getIdPuntoVenta().toString());
//			mapExpresionVenta.put(Promocion.TOKEN_PUNTO_VENTA, value);
//			/*	Enviando el Canal de Venta	*/
//			value = new ArrayList<String>();
//			value.add(getIdCanalVenta().toString());
//			mapExpresionVenta.put(Promocion.TOKEN_CANAL_VENTA, value);
//			/*	Enviando si es Pasajero Nuevo	*/
//			/*	Enviando la Cantidad de Viajes del Pasajero	*/
//			/*	Enviando el Asiento	*/
//			value = new ArrayList<String>();
//			value.add(getAsiento());
//			mapExpresionVenta.put(Promocion.TOKEN_ASIENTO, value);
//			/*	Enviando el Cliente	*/
//			value = new ArrayList<String>();
//			value.add(getIdCliente()!=null?getIdCliente().toString():"*");
//			mapExpresionVenta.put(Promocion.TOKEN_CLIENTE, value);
//			/*	Enviando si es Ida y Vuelta	*/
//			value = new ArrayList<String>();
//			value.add(getIdaVuelta()?"S":"*");
//			mapExpresionVenta.put(Promocion.TOKEN_IDA_VUELTA, value);
//			/*	Enviando la Forma de Pago	*/
//			value = new ArrayList<String>();
//			value.add(getIdFormaPago().toString());
//			mapExpresionVenta.put(Promocion.TOKEN_FORMA_PAGO, value);
//			/*	Enviado el Tipo de Forma de Pago	*/
//			value = new ArrayList<String>();
//			value.add(getIdTipoFormaPago().toString());
//			mapExpresionVenta.put(Promocion.TOKEN_TIPO_FORMA_PAGO, value);
//			/*	Enviando la Tarjeta de Credito	*/
//			value = new ArrayList<String>();
//			if(getIdTarjetaCredito()!=null)
//				value.add(getIdTarjetaCredito().toString());
//			else
//				value.add("*");
//			mapExpresionVenta.put(Promocion.TOKEN_TARJETA_CREDITO, value);
//			/*	Enviando la Temporada	*/
//			value = new ArrayList<String>();
//			if(getFechaPartida()!=null){
//				String anio = Constantes.FORMAT_YEAR.format(getFechaPartida());
//				String mes = Constantes.FORMAT_MONTH.format(getFechaPartida());
//				String dia = Constantes.FORMAT_DAY.format(getFechaPartida());
//				List<TemporadaAlta>	lstTemporada = ServiceLocator.getTemporadaAltaManager().buscarTemporadaAlta(anio, mes, dia);
//				if(lstTemporada.size()>0)
//					value.add("*");
//				else
//					value.add("B");
//			}else
//				value.add("*");
//			mapExpresionVenta.put(Promocion.TOKEN_TEMPORADA, value);
//			/*	Enviando si es PaxFre*/
//			value = new ArrayList<String>();
//			if(isPasajeroFrecuente())
//				value.add("S");
//			else
//				value.add("*");
//			mapExpresionVenta.put(Promocion.TOKEN_PAXFRE, value);
//			
//			boolean aplicarPromocion = false;
//			for(String key : mapExpresionPromocion.keySet()){
//				if(!mapExpresionVenta.containsKey(key)){
//					aplicarPromocion = false;
//					break;
//				}else{
//					List<String> valueVenta = mapExpresionVenta.get(key);
//					List<String> valuesPromocion = mapExpresionPromocion.get(key);
//					if(valuesPromocion.contains(valueVenta.get(0)))
//						aplicarPromocion = true;
//					else{
//						aplicarPromocion = false;
//						break;
//					}
//				}				
//			}
//			
//			if(aplicarPromocion){
//				/*Aplca descuento*/
//				if(xDescuento){
//					
//				}else{
//					
//					
//				}
//				Double dsct = 0.0;
//				if(promocion.getTipoDescuento().equals(Promocion.PROMOCION_TIPO_DESCUENTO_VALUE_FIJO)){
//					if(promocion.getValorDescuento()!=null)
//						dsct = promocion.getValorDescuento();
//					else
//						dsct = dblTarifa.getValue() - promocion.getPorImporte();
//				}else
//					dsct = (dblTarifa.getValue() * promocion.getValorDescuento())/100;
//				
//				dblImporte.setValue(Util.calculoTotalPagar(dblTarifa.getValue(), dsct, .00));
//				dblDescuento.setValue(dblTarifa.getValue()- dblImporte.getValue());
//				
//				promocionAplicada = promocion;
//				dblDescuento.setTooltiptext("Aplicando la Promocion : "+promocionAplicada.getDenominacion());
//			}
//			
////			System.out.println(mapExpresionPromocion.toString());
////			System.out.println(mapExpresionVenta.toString());
//			
//			
//		}catch(Exception ex){
//			DlgMessage.information(this.getClass().getSimpleName()+" "+ex.getMessage());
//		}
//		return promocionAplicada;
//	}
//	
}
