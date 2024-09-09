package pe.itsb.vyrbus.model.bean;

import java.io.Serializable;
import java.util.Date;
import java.util.List;


/**
 * Promocion entity. @author MyEclipse Persistence Tools
 */
public class Promocion extends GenericBean implements Serializable, Cloneable {
	private static final long serialVersionUID = 1L;
	private Long id;
	private String denominacion;
    private String rutas;
    private String servicios;
    private String puntoVenta;
    private String canalVenta;
    private String pasajeroNuevo;
    private String cantidadViajesPasajero;
    private String asientos;
    private String edadPasajero;
    private String cliente;
    private String idaVuelta;
    private Double valorDescuento;
    private String tipoDescuento;
    private Double porImporte;
    private String formaPago;
    private String tipoFormaPago;
    private String tarjetaCredito;
    private String enTemporada;
    private String pasajeroFrecuente;
    private Date fechaInicio;
    private Date fechaFin;
    private String expresion;
    private String beneficio;
    private Integer esAcumulable;
    private Integer esTarifa;
    private String horasPartida;

    private List<Ruta> listRutas;							//No mapeado
    private List<Servicio> listServicio;					//No mapeado
    private List<Agencia> listPuntoVenta;					//No mapeado
    private List<CanalVenta> listCanalVenta;				//No mapeado
    private List<FormaPago> listFormaPago;					//No mapeado
    private List<TipoFormaPago> listTipoFormaPago;			//No mapeado
    private List<TarjetaCredito> listTarjetaCredito;		//No mapeado
    private Agencia agencia;								//No mapeado
    private Integer cantidad;								//No mapeado
    private Double totalDescuento;							//No mapeado
    private Double totalVenta;								//No mapeado


    /*	Constantes para el tipo de descuento en las promociones */
	public static final String PROMOCION_TIPO_DESCUENTO_LABEL_FIJO = "FIJO";
	public static final String PROMOCION_TIPO_DESCUENTO_LABEL_PORCENTAJE = "PORCENTAJE";
	public static final String PROMOCION_TIPO_DESCUENTO_LABEL_TARIFA = "TARIFA";
	public static final String PROMOCION_TEMPORADA_LABEL_NOALTA = "NO VALIDO EN ALTA";
	public static final String PROMOCION_TIPO_DESCUENTO_VALUE_FIJO = "S";
	public static final String PROMOCION_TIPO_DESCUENTO_VALUE_PORCENTAJE = "P";
	public static final String PROMOCION_TIPO_DESCUENTO_VALUE_TARIFA = "T";
	public static final String PROMOCION_TEMPORADA_VALUE_NOALTA = "B";

	public static final String TOKEN_RUTA = "RUTA";
	public static final String TOKEN_SERVICIO = "SERV";
	public static final String TOKEN_CANAL_VENTA = "CVEN";
	public static final String TOKEN_PUNTO_VENTA = "PVEN";
	public static final String TOKEN_PASAJERO_NUEVO = "PAXN";
	public static final String TOKEN_CANTIDAD_VIAJES = "NVIA";
	public static final String TOKEN_ASIENTO = "NASI";
	public static final String TOKEN_CLIENTE = "CLIE";
	public static final String TOKEN_IDA_VUELTA = "IDVU";
	public static final String TOKEN_FORMA_PAGO = "FORP";
	public static final String TOKEN_TIPO_FORMA_PAGO = "TIPP";
	public static final String TOKEN_TARJETA_CREDITO = "TCRE";
	public static final String TOKEN_TEMPORADA = "TEMP";
	public static final String TOKEN_PAXFRE = "PAXF";
	public static final String TOKEN_VALOR_DESCUENTO = "VALD";
	public static final String TOKEN_TIPO_DESCUENTO = "TIPD";
	public static final String TOKEN_IMPORTE = "IMPO";
	public static final String TOKEN_HORA_PARTIDA="HORP";
    // Constructors

    /** default constructor */
    public Promocion() {
    }

	/**
	 * @param id
	 */
	public Promocion(Long id) {
		super();
		this.id = id;
	}

	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}

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
	 * @return the denominacion
	 */
	public String getDenominacion() {
		return denominacion;
	}
	/**
	 * @param denominacion the denominacion to set
	 */
	public void setDenominacion(String denominacion) {
		this.denominacion = denominacion;
	}

	/**
	 * @return the rutas
	 */
	public String getRutas() {
		return rutas;
	}
	/**
	 * @param rutas the rutas to set
	 */
	public void setRutas(String rutas) {
		this.rutas = rutas;
	}

	/**
	 * @return the servicios
	 */
	public String getServicios() {
		return servicios;
	}
	/**
	 * @param servicios the servicios to set
	 */
	public void setServicios(String servicios) {
		this.servicios = servicios;
	}

	/**
	 * @return the puntoVenta
	 */
	public String getPuntoVenta() {
		return puntoVenta;
	}
	/**
	 * @param puntoVenta the puntoVenta to set
	 */
	public void setPuntoVenta(String puntoVenta) {
		this.puntoVenta = puntoVenta;
	}

	/**
	 * @return the canalVenta
	 */
	public String getCanalVenta() {
		return canalVenta;
	}
	/**
	 * @param canalVenta the canalVenta to set
	 */
	public void setCanalVenta(String canalVenta) {
		this.canalVenta = canalVenta;
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
	 * @return the cantidadViajesPasajero
	 */
	public String getCantidadViajesPasajero() {
		return cantidadViajesPasajero;
	}
	/**
	 * @param cantidadViajesPasajero the cantidadViajesPasajero to set
	 */
	public void setCantidadViajesPasajero(String cantidadViajesPasajero) {
		this.cantidadViajesPasajero = cantidadViajesPasajero;
	}

	/**
	 * @return the asientos
	 */
	public String getAsientos() {
		return asientos;
	}
	/**
	 * @param asientos the asientos to set
	 */
	public void setAsientos(String asientos) {
		this.asientos = asientos;
	}

	/**
	 * @return the edadPasajero
	 */
	public String getEdadPasajero() {
		return edadPasajero;
	}
	/**
	 * @param edadPasajero the edadPasajero to set
	 */
	public void setEdadPasajero(String edadPasajero) {
		this.edadPasajero = edadPasajero;
	}

	/**
	 * @return the cliente
	 */
	public String getCliente() {
		return cliente;
	}
	/**
	 * @param cliente the cliente to set
	 */
	public void setCliente(String cliente) {
		this.cliente = cliente;
	}

	/**
	 * @return the idaVuelta
	 */
	public String getIdaVuelta() {
		return idaVuelta;
	}
	/**
	 * @param idaVuelta the idaVuelta to set
	 */
	public void setIdaVuelta(String idaVuelta) {
		this.idaVuelta = idaVuelta;
	}

	/**
	 * @return the valosDescuento
	 */
	public Double getValorDescuento() {
		return valorDescuento;
	}
	/**
	 * @param valorDescuento the valosDescuento to set
	 */
	public void setValorDescuento(Double valorDescuento) {
		this.valorDescuento = valorDescuento;
	}

	/**
	 * @return the tipoDescuento Fijo o Porcentaje
	 */
	public String getTipoDescuento() {
		return tipoDescuento;
	}
	/**
	 * @param tipoDescuento the tipoDescuento to set Fijo o Porcentaje
	 */
	public void setTipoDescuento(String tipoDescuento) {
		this.tipoDescuento = tipoDescuento;
	}

	/**
	 * @return the porImporte
	 */
	public Double getPorImporte() {
		return porImporte;
	}
	/**
	 * @param porImporte the porImporte to set
	 */
	public void setPorImporte(Double porImporte) {
		this.porImporte = porImporte;
	}

	/**
	 * @return the formaPago
	 */
	public String getFormaPago() {
		return formaPago;
	}
	/**
	 * @param formaPago the formaPago to set
	 */
	public void setFormaPago(String formaPago) {
		this.formaPago = formaPago;
	}

	/**
	 * @return the tipoPago
	 */
	public String getTipoFormaPago() {
		return tipoFormaPago;
	}
	/**
	 * @param tipoPago the tipoPago to set
	 */
	public void setTipoFormaPago(String tipoFormaPago) {
		this.tipoFormaPago = tipoFormaPago;
	}

	/**
	 * @return the tarjetaCredito
	 */
	public String getTarjetaCredito() {
		return tarjetaCredito;
	}
	/**
	 * @param tarjetaCredito the tarjetaCredito to set
	 */
	public void setTarjetaCredito(String tarjetaCredito) {
		this.tarjetaCredito = tarjetaCredito;
	}

	/**
	 * @return the enTemporada
	 */
	public String getEnTemporada() {
		return enTemporada;
	}
	/**
	 * @param enTemporada the enTemporada to set
	 */
	public void setEnTemporada(String enTemporada) {
		this.enTemporada = enTemporada;
	}

	/**
	 * @return the pasajeroFrecuente
	 */
	public String getPasajeroFrecuente() {
		return pasajeroFrecuente;
	}
	/**
	 * @param pasajeroFrecuente the pasajerofrecuente to set
	 */
	public void setPasajeroFrecuente(String pasajeroFrecuente) {
		this.pasajeroFrecuente = pasajeroFrecuente;
	}

	/**
	 * @return the fechaInicio
	 */
	public Date getFechaInicio() {
		return fechaInicio;
	}
	/**
	 * @param fechaInicio the fechaInicio to set
	 */
	public void setFechaInicio(Date fechaInicio) {
		this.fechaInicio = fechaInicio;
	}

	/**
	 * @return the fechaFin
	 */
	public Date getFechaFin() {
		return fechaFin;
	}
	/**
	 * @param fechaFin the fechaFin to set
	 */
	public void setFechaFin(Date fechaFin) {
		this.fechaFin = fechaFin;
	}

	/**
	 * @return the expresion
	 */
	public String getExpresion() {
		return expresion;
	}
	/**
	 * @param expresion the expresion to set
	 */
	public void setExpresion(String expresion) {
		this.expresion = expresion;
	}

	/**
	 * @return the beneficio
	 */
	public String getBeneficio() {
		return beneficio;
	}
	/**
	 * @param beneficio the beneficio to set
	 */
	public void setBeneficio(String beneficio) {
		this.beneficio = beneficio;
	}

	/**
	 * @return the esAcumulable
	 */
	public Integer getEsAcumulable() {
		return esAcumulable;
	}
	/**
	 * @param esAcumulable the esAcumulable to set
	 */
	public void setEsAcumulable(Integer esAcumulable) {
		this.esAcumulable = esAcumulable;
	}

	/**
	 * @return the estarifa
	 */
	public Integer getEsTarifa() {
		return esTarifa;
	}
	/**
	 * @param estarifa the estarifa to set
	 */
	public void setEsTarifa(Integer esTarifa) {
		this.esTarifa = esTarifa;
	}

	/**
	 * @return the listRutas
	 */
	public List<Ruta> getListRutas() {
		return listRutas;
	}
	/**
	 * @param listRutas the listRutas to set
	 */
	public void setListRutas(List<Ruta> listRutas) {
		this.listRutas = listRutas;
	}

	/**
	 * @return the listServicio
	 */
	public List<Servicio> getListServicio() {
		return listServicio;
	}
	/**
	 * @param listServicio the listServicio to set
	 */
	public void setListServicio(List<Servicio> listServicio) {
		this.listServicio = listServicio;
	}

	/**
	 * @return the listPuntoVenta
	 */
	public List<Agencia> getListPuntoVenta() {
		return listPuntoVenta;
	}
	/**
	 * @param listPuntoVenta the listPuntoVenta to set
	 */
	public void setListPuntoVenta(List<Agencia> listPuntoVenta) {
		this.listPuntoVenta = listPuntoVenta;
	}

	/**
	 * @return the listCanalVenta
	 */
	public List<CanalVenta> getListCanalVenta() {
		return listCanalVenta;
	}
	/**
	 * @param listCanalVenta the listCanalVenta to set
	 */
	public void setListCanalVenta(List<CanalVenta> listCanalVenta) {
		this.listCanalVenta = listCanalVenta;
	}

	/**
	 * @return the listFormaPago
	 */
	public List<FormaPago> getListFormaPago() {
		return listFormaPago;
	}
	/**
	 * @param listFormaPago the listFormaPago to set
	 */
	public void setListFormaPago(List<FormaPago> listFormaPago) {
		this.listFormaPago = listFormaPago;
	}

	/**
	 * @return the listTipoFormaPago
	 */
	public List<TipoFormaPago> getListTipoFormaPago() {
		return listTipoFormaPago;
	}
	/**
	 * @param listTipoFormaPago the listTipoFormaPago to set
	 */
	public void setListTipoFormaPago(List<TipoFormaPago> listTipoFormaPago) {
		this.listTipoFormaPago = listTipoFormaPago;
	}

	/**
	 * @return the listTarjetaCredito
	 */
	public List<TarjetaCredito> getListTarjetaCredito() {
		return listTarjetaCredito;
	}
	/**
	 * @param listTarjetaCredito the listTarjetaCredito to set
	 */
	public void setListTarjetaCredito(List<TarjetaCredito> listTarjetaCredito) {
		this.listTarjetaCredito = listTarjetaCredito;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#clone()
	 */
	@Override
	public Object clone() throws CloneNotSupportedException {
		// TODO Auto-generated method stub
		return super.clone();
	}

	/**
	 * @return the cantidad
	 */
	public Integer getCantidad() {
		return cantidad;
	}

	/**
	 * @param cantidad the cantidad to set
	 */
	public void setCantidad(Integer cantidad) {
		this.cantidad = cantidad;
	}

	/**
	 * @return the totalDescuento
	 */
	public Double getTotalDescuento() {
		return totalDescuento;
	}

	/**
	 * @param totalDescuento the totalDescuento to set
	 */
	public void setTotalDescuento(Double totalDescuento) {
		this.totalDescuento = totalDescuento;
	}

	/**
	 * @return the totalVenta
	 */
	public Double getTotalVenta() {
		return totalVenta;
	}

	/**
	 * @param totalVenta the totalVenta to set
	 */
	public void setTotalVenta(Double totalVenta) {
		this.totalVenta = totalVenta;
	}

	/**
	 * @return the horasPartida
	 */
	public String getHorasPartida() {
		return horasPartida;
	}

	/**
	 * @param horasPartida the horasPartida to set
	 */
	public void setHorasPartida(String horasPartida) {
		this.horasPartida = horasPartida;
	}
}
