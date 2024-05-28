/**
 * Proyecto		: SISVYR
 * Sistema		: Sistema de Ventas y Reservas
 * Descripción	: 
 * Autor		: José Abanto
 * Fecha		: 9 may. 2024
 * Hora			: 20:21:30
 */
package pe.itsb.vyrbus.model.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author abant
 *
 */
public class CuponPromocional  extends GenericBean implements Serializable, Cloneable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Long id;
	private String denominacion;
	private String rutas;
	private String servicios;
	private String canalesVenta;
	private String asientos;
	private String agencias;
	private Double valorDescuento;
	private String tipoDescuento;
	private String codigo;
	private Double valorMaximoDescuento;
	private Date fechaInicial;
	private Date fechaFinal;
	private String tipoFormaPagos;
	private String idaVuelta;
	private String tipoIdaVuelta;
	private String tipoAsientos;
	private Integer cantidaMaximaServicio;
	private String tipoCupon;
	
	private Double valorDescuentoAplicado; //No mapeado
	private List<Integer> listAgenciasId; //No mapeado
	private List<Integer> listServiciosId; //No mapeado
	private List<Integer> listCanalesVentaId; //No mapeado
	private List<Integer> listTiposFormaPagoId; //No mapeado
	private List<Integer> listTiposAsientosId; //No mapeado
	
	
	
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
	 * @return the canalesVenta
	 */
	public String getCanalesVenta() {
		return canalesVenta;
	}
	/**
	 * @param canalesVenta the canalesVenta to set
	 */
	public void setCanalesVenta(String canalesVenta) {
		this.canalesVenta = canalesVenta;
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
	 * @return the agencias
	 */
	public String getAgencias() {
		return agencias;
	}
	/**
	 * @param agencias the agencias to set
	 */
	public void setAgencias(String agencias) {
		this.agencias = agencias;
	}
	/**
	 * @return the valorDescuento
	 */
	public Double getValorDescuento() {
		return valorDescuento;
	}
	/**
	 * @param valorDescuento the valorDescuento to set
	 */
	public void setValorDescuento(Double valorDescuento) {
		this.valorDescuento = valorDescuento;
	}
	/**
	 * @return the tipoDescuento
	 */
	public String getTipoDescuento() {
		return tipoDescuento;
	}
	/**
	 * @param tipoDescuento the tipoDescuento to set
	 */
	public void setTipoDescuento(String tipoDescuento) {
		this.tipoDescuento = tipoDescuento;
	}
	/**
	 * @return the codigo
	 */
	public String getCodigo() {
		return codigo;
	}
	/**
	 * @param codigo the codigo to set
	 */
	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}
	/**
	 * @return the valorMaximoDescuento
	 */
	public Double getValorMaximoDescuento() {
		return valorMaximoDescuento;
	}
	/**
	 * @param valorMaximoDescuento the valorMaximoDescuento to set
	 */
	public void setValorMaximoDescuento(Double valorMaximoDescuento) {
		this.valorMaximoDescuento = valorMaximoDescuento;
	}
	/**
	 * @return the fechaInicial
	 */
	public Date getFechaInicial() {
		return fechaInicial;
	}
	/**
	 * @param fechaInicial the fechaInicial to set
	 */
	public void setFechaInicial(Date fechaInicial) {
		this.fechaInicial = fechaInicial;
	}
	/**
	 * @return the fechaFinal
	 */
	public Date getFechaFinal() {
		return fechaFinal;
	}
	/**
	 * @param fechaFinal the fechaFinal to set
	 */
	public void setFechaFinal(Date fechaFinal) {
		this.fechaFinal = fechaFinal;
	}
	
	/**
	 * @return the listAgenciasId
	 */
	public List<Integer> getListAgenciasId() {
		listAgenciasId= new ArrayList<>();
		if(agencias!=null  && !(agencias.trim().equals("*"))){
			String[] yAgencias=agencias.split(",");
			for(String agencia_id: yAgencias){
				listAgenciasId.add(Integer.valueOf(agencia_id));				
			}						
		}		
		
		return listAgenciasId;
	}

	/**
	 * @return the listServiciosId
	 */
	public List<Integer> getListServiciosId() {
		listServiciosId= new ArrayList<>();
		if(servicios!=null && !(servicios.trim().equals("*"))){
			String[] yServicios=servicios.split(",");
			for(String servicio_id: yServicios){
				listServiciosId.add(Integer.valueOf(servicio_id));				
			}						
		}
		
		return listServiciosId;
	}

	/**
	 * @return the listCanalesVentaId
	 */
	public List<Integer> getListCanalesVentaId() {
		listCanalesVentaId= new ArrayList<>();
		
		if(canalesVenta!=null && !(canalesVenta.trim().equals("*"))){
			String[] yCanalesVenta=canalesVenta.split(",");
			for(String canalVenta_id: yCanalesVenta){
				listCanalesVentaId.add(Integer.valueOf(canalVenta_id));				
			}						
		}
		
		return listCanalesVentaId;
	}

	/**
	 * @return the tipoFormaPagos
	 */
	public String getTipoFormaPagos() {
		return tipoFormaPagos;
	}
	/**
	 * @param tipoFormaPagos the tipoFormaPagos to set
	 */
	public void setTipoFormaPagos(String tipoFormaPagos) {
		this.tipoFormaPagos = tipoFormaPagos;
	}
	/**
	 * @return the listTiposFormaPagoId
	 */
	public List<Integer> getListTiposFormaPagoId() {
		listTiposFormaPagoId= new ArrayList<>();
		
		if(tipoFormaPagos!=null && !(tipoFormaPagos.trim().equals("*"))){
			String[] yTiposFormaPago=tipoFormaPagos.split(",");
			for(String tipoFormaPago_id: yTiposFormaPago){
				listTiposFormaPagoId.add(Integer.valueOf(tipoFormaPago_id));				
			}						
		}		
		
		return listTiposFormaPagoId;
	}

	public Double getValorDescuentoAplicado() {
		return valorDescuentoAplicado;
	}
	public void setValorDescuentoAplicado(Double valorDescuentoAplicado) {
		this.valorDescuentoAplicado = valorDescuentoAplicado;
	}
	
	
	/*
	 * (non-Javadoc)
	 * @see java.lang.Object#clone()
	 */
	@Override
	public CuponPromocional clone() throws CloneNotSupportedException {
		// TODO Auto-generated method stub
		return (CuponPromocional) super.clone();
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
	 * @return the tipoIdaVuelta
	 */
	public String getTipoIdaVuelta() {
		return tipoIdaVuelta;
	}
	/**
	 * @param tipoIdaVuelta the tipoIdaVuelta to set
	 */
	public void setTipoIdaVuelta(String tipoIdaVuelta) {
		this.tipoIdaVuelta = tipoIdaVuelta;
	}
	/**
	 * @return the tipoAsientos
	 */
	public String getTipoAsientos() {
		return tipoAsientos;
	}
	/**
	 * @param tipoAsientos the tipoAsientos to set
	 */
	public void setTipoAsientos(String tipoAsientos) {
		this.tipoAsientos = tipoAsientos;
	}
	
	/**
	 * @return the listTiposAsientos
	 */
	public List<Integer> getListTiposAsientosId() {
		listTiposAsientosId= new ArrayList<>();
		
		if(tipoAsientos!=null && !(tipoAsientos.trim().equals("*"))){
			String[] yTiposAsiento=tipoAsientos.split(",");
			for(String tipoAsiento_id: yTiposAsiento){
				listTiposAsientosId.add(Integer.valueOf(tipoAsiento_id));				
			}						
		}		
		
		
		return listTiposAsientosId;
	}
	/**
	 * @return the cantidaMaximaServicio
	 */
	public Integer getCantidaMaximaServicio() {
		return cantidaMaximaServicio;
	}
	/**
	 * @param cantidaMaximaServicio the cantidaMaximaServicio to set
	 */
	public void setCantidaMaximaServicio(Integer cantidaMaximaServicio) {
		this.cantidaMaximaServicio = cantidaMaximaServicio;
	}
	/**
	 * @return the tipoCupon
	 */
	public String getTipoCupon() {
		return tipoCupon;
	}
	/**
	 * @param tipoCupon the tipoCupon to set
	 */
	public void setTipoCupon(String tipoCupon) {
		this.tipoCupon = tipoCupon;
	}
}
