/**
 * Proyecto		: SISVYR
 * Sistema		: Sistema de Ventas y Reservas
 * Descripción	: 
 * Autor		: José Abanto
 * Fecha		: 9 may. 2024
 * Hora			: 20:25:49
 */
package pe.itsb.vyrbus.model.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import pe.itsb.vyrbus.service.util.Util;


/**
 * @author abant
 *
 */
public class TarifaByAsientoDetalle extends GenericBean implements Serializable, Cloneable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Long id;
	private TarifaByAsiento tarifaByAsiento;
	private CanalVenta canalVenta;
	private TipoAsiento tipoAsiento;
	private String asientos;
	private Double precio;
	private TipoPrecio tipoPrecio;
	private Itinerario itinerario;
	private Agencia agencia;
	private String observaciones;
	private TarifaByAsientoPlantillaPromocion tarifaByAsientoPlantillaPromocion;
	//Para los descuentos
	private Double valorDescuento;
	private String tipoDescuento;
	private Integer ratioDiasFechaEmison;
	private Double valorMaximoDescuentoSoles;
	
	/**
	 * Valor del incremento o rebaja
	 */
	private Double value;		//No mapeado
	private boolean incremento;	//no mapeado
	private Double descuentoSoles; //no mapeado
	
//	private int numeroAsiento;	//No mapeado
	
//	private Double precioByAsiento;
	
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
	 * @return the canalVenta
	 */
	public CanalVenta getCanalVenta() {
		return canalVenta;
	}
	/**
	 * @param canalVenta the canalVenta to set
	 */
	public void setCanalVenta(CanalVenta canalVenta) {
		this.canalVenta = canalVenta;
	}
	/**
	 * @return the tipoAsiento
	 */
	public TipoAsiento getTipoAsiento() {
		return tipoAsiento;
	}
	/**
	 * @param tipoAsiento the tipoAsiento to set
	 */
	public void setTipoAsiento(TipoAsiento tipoAsiento) {
		this.tipoAsiento = tipoAsiento;
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
	 * @return the itinerario
	 */
	public Itinerario getItinerario() {
		return itinerario;
	}
	/**
	 * @param itinerario the itinerario to set
	 */
	public void setItinerario(Itinerario itinerario) {
		this.itinerario = itinerario;
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
	 * @return the tipoPrecio
	 */
	public TipoPrecio getTipoPrecio() {
		return tipoPrecio;
	}
	/**
	 * @param tipoPrecio the tipoPrecio to set
	 */
	public void setTipoPrecio(TipoPrecio tipoPrecio) {
		this.tipoPrecio = tipoPrecio;
	}
	/**
	 * @return the observaciones
	 */
	public String getObservaciones() {
		return observaciones;
	}
	/**
	 * @param observaciones the observaciones to set
	 */
	public void setObservaciones(String observaciones) {
		this.observaciones = observaciones;
	}
	/**
	 * @return the precio
	 */
	public Double getPrecio() {
		return precio;
	}
	/**
	 * @param precio the precio to set
	 */
	public void setPrecio(Double precio) {
		this.precio = precio;
	}
	/**
	 * @return the tarifaPlantillaPromocion
	 */
//	public TarifaPlantillaPromocion getTarifaPlantillaPromocion() {
//		return tarifaPlantillaPromocion;
//	}
//	/**
//	 * @param tarifaPlantillaPromocion the tarifaPlantillaPromocion to set
//	 */
//	public void setTarifaPlantillaPromocion(TarifaPlantillaPromocion tarifaPlantillaPromocion) {
//		this.tarifaPlantillaPromocion = tarifaPlantillaPromocion;
//	}
	/* (non-Javadoc)
	 * @see java.lang.Object#clone()
	 */
	@Override
	public Object clone() throws CloneNotSupportedException {
		// TODO Auto-generated method stub
		return super.clone();
	}
	
	/**
	 * Obtiene la lista de asientos
	 * @return
	 */
	public List<Integer> getListAsientos(){
		List<Integer> listAsientos= new ArrayList<>();
		
		if(this.asientos!=null){
			String[] _y_asientos=asientos.split(";");
			for(String _f_asientos: _y_asientos){
				if(_f_asientos.indexOf("-")>0){
					/**Asientos por rango Ejemplo (11-36), (1-44), (1-36)*/					
					int _inicio=Integer.valueOf(_f_asientos.split("-")[0]);
					int _final=Integer.valueOf(_f_asientos.split("-")[1]);
					for(int _asiento=_inicio;_asiento<=_final;_asiento++){
						listAsientos.add(_asiento);
					}					
				}else if(_f_asientos.indexOf(",")>0){
					/**Asientos individuales Ejemplo (2,5,6,8)*/					
					for(int x=0;x<_f_asientos.split(",").length;x++){
						int _asiento=Integer.valueOf(_f_asientos.split(",")[x]);
						listAsientos.add(_asiento);
					}					
				}else{
					int _asiento=Integer.valueOf(_f_asientos);
					listAsientos.add(_asiento);					
				}
			}			
		}
		
		return listAsientos;
	}
	
	/**
	 * 
	 * @param numeroAsiento
	 * @param canven_id
	 * @param agencia_id
	 * @param itinerario_id
	 * @return
	 */
	public Double getprecioByAsiento(int numeroAsiento, int canven_id, int agencia_id, long itinerario_id) {	
		Double precioByAsiento=0.00;
		
		if(asientos!=null){
			String[] _y_asientos=asientos.split(";");
			for(String _f_asientos: _y_asientos){
				if(_f_asientos.indexOf("-")>0){
					/**Asientos por rango Ejemplo (11-36)*/					
					int _inicio=Integer.valueOf(_f_asientos.split("-")[0]);
					int _final=Integer.valueOf(_f_asientos.split("-")[1]);
					for(int _asiento=_inicio;_asiento<=_final;_asiento++){
						if(_asiento==numeroAsiento){
							//Valida el canal de venta
							if(this.canalVenta==null || this.canalVenta.getId().intValue()==canven_id){
								//Valida la agencia (subcanal de venta)
								if(this.getAgencia()==null || this.agencia.getId().intValue()==agencia_id){
									//Valida el itinerario
									if(this.itinerario==null || this.itinerario.getId().longValue()==itinerario_id){
										precioByAsiento=this.precio;
										break;
									}									
								}								
							}							
						}
					}					
				}else if(_f_asientos.indexOf(",")>0){
					/**Asientos individuales Ejemplo (2,5,6,8)*/					
					for(int x=0;x<_f_asientos.split(",").length;x++){
						int _asiento=Integer.valueOf(_f_asientos.split(",")[x]);
						if(_asiento==numeroAsiento){
							//Valida el canal de venta
							if(this.canalVenta==null || this.canalVenta.getId().intValue()==canven_id){
								//Valida la agencia (subcanal de venta)
								if(this.getAgencia()==null || this.agencia.getId().intValue()==agencia_id){
									//Valida el itinerario
									if(this.itinerario==null || this.itinerario.getId().longValue()==itinerario_id){
										precioByAsiento=this.precio;
										break;
									}									
								}								
							}								
						}
					}					
				}else{
					if (Util.isNumeric(_f_asientos.trim())){
						if(Integer.valueOf(_f_asientos).intValue()==numeroAsiento){
							//Valida el canal de venta
							if(this.canalVenta==null || this.canalVenta.getId().intValue()==canven_id){
								//Valida la agencia (subcanal de venta)
								if(this.getAgencia()==null || this.agencia.getId().intValue()==agencia_id){
									//Valida el itinerario
									if(this.itinerario==null || this.itinerario.getId().longValue()==itinerario_id){
										precioByAsiento=this.precio;
										break;
									}									
								}								
							}							
						}
					}					
				}
			}
		}
		return precioByAsiento;
	}
	
	/**
	 * 
	 * @param numeroAsiento
	 * @param canven_id
	 * @param agencia_id
	 * @param itinerario_id
	 * @return
	 */
	public Double getDescuetoByAsiento(int numeroAsiento, int canven_id, int agencia_id, long itinerario_id) {	
		Double descuento=0.00;
		
		if(asientos!=null && this.valorDescuento!=null && this.valorDescuento>0){
			String[] _y_asientos=asientos.split(";");
			for(String _f_asientos: _y_asientos){
				if(_f_asientos.indexOf("-")>0){
					/**Asientos por rango Ejemplo (11-36)*/					
					int _inicio=Integer.valueOf(_f_asientos.split("-")[0]);
					int _final=Integer.valueOf(_f_asientos.split("-")[1]);
					for(int _asiento=_inicio;_asiento<=_final;_asiento++){
						if(_asiento==numeroAsiento){
							//Valida el canal de venta
							if(this.canalVenta==null || this.canalVenta.getId().intValue()==canven_id){
								//Valida la agencia (subcanal de venta)
								if(this.getAgencia()==null || this.agencia.getId().intValue()==agencia_id){
									//Valida el itinerario
									if(this.itinerario==null || this.itinerario.getId().longValue()==itinerario_id){
										descuento=this.valorDescuento;
										break;
									}									
								}								
							}							
						}
					}					
				}else if(_f_asientos.indexOf(",")>0){
					/**Asientos individuales Ejemplo (2,5,6,8)*/					
					for(int x=0;x<_f_asientos.split(",").length;x++){
						int _asiento=Integer.valueOf(_f_asientos.split(",")[x]);
						if(_asiento==numeroAsiento){
							//Valida el canal de venta
							if(this.canalVenta==null || this.canalVenta.getId().intValue()==canven_id){
								//Valida la agencia (subcanal de venta)
								if(this.getAgencia()==null || this.agencia.getId().intValue()==agencia_id){
									//Valida el itinerario
									if(this.itinerario==null || this.itinerario.getId().longValue()==itinerario_id){
										descuento=this.valorDescuento;
										break;
									}									
								}								
							}								
						}
					}					
				}else{
					if (Util.isNumeric(_f_asientos.trim())){
						if(Integer.valueOf(_f_asientos).intValue()==numeroAsiento){
							//Valida el canal de venta
							if(this.canalVenta==null || this.canalVenta.getId().intValue()==canven_id){
								//Valida la agencia (subcanal de venta)
								if(this.getAgencia()==null || this.agencia.getId().intValue()==agencia_id){
									//Valida el itinerario
									if(this.itinerario==null || this.itinerario.getId().longValue()==itinerario_id){
										descuento=this.valorDescuento;
										break;
									}									
								}								
							}							
						}
					}					
				}
			}
		}
		return descuento;
	}
	/**
	 * @return the value
	 */
	public Double getValue() {
		return value;
	}
	/**
	 * @param value the value to set
	 */
	public void setValue(Double value) {
		this.value = value;
	}
	/**
	 * @return the incremento
	 */
	public boolean isIncremento() {
		return incremento;
	}
	/**
	 * @param incremento the incremento to set
	 */
	public void setIncremento(boolean incremento) {
		this.incremento = incremento;
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
	 * @return the descuentoSoles
	 */
	public Double getDescuentoSoles() {
		return descuentoSoles;
	}
	/**
	 * @param descuentoSoles the descuentoSoles to set
	 */
	public void setDescuentoSoles(Double descuentoSoles) {
		this.descuentoSoles = descuentoSoles;
	}
	/**
	 * @return the valorMaximoDescuentoSoles
	 */
	public Double getValorMaximoDescuentoSoles() {
		return valorMaximoDescuentoSoles;
	}
	/**
	 * @param valorMaximoDescuentoSoles the valorMaximoDescuentoSoles to set
	 */
	public void setValorMaximoDescuentoSoles(Double valorMaximoDescuentoSoles) {
		this.valorMaximoDescuentoSoles = valorMaximoDescuentoSoles;
	}
	/**
	 * @return the ratioDiasFechaEmison
	 */
	public Integer getRatioDiasFechaEmison() {
		return ratioDiasFechaEmison;
	}
	/**
	 * @param ratioDiasFechaEmison the ratioDiasFechaEmison to set
	 */
	public void setRatioDiasFechaEmison(Integer ratioDiasFechaEmison) {
		this.ratioDiasFechaEmison = ratioDiasFechaEmison;
	}
	/**
	 * @return the tarifaByAsientoPlantillaPromocion
	 */
	public TarifaByAsientoPlantillaPromocion getTarifaByAsientoPlantillaPromocion() {
		return tarifaByAsientoPlantillaPromocion;
	}
	/**
	 * @param tarifaByAsientoPlantillaPromocion the tarifaByAsientoPlantillaPromocion to set
	 */
	public void setTarifaByAsientoPlantillaPromocion(TarifaByAsientoPlantillaPromocion tarifaByAsientoPlantillaPromocion) {
		this.tarifaByAsientoPlantillaPromocion = tarifaByAsientoPlantillaPromocion;
	}
	/**
	 * @return the tarifaByAsiento
	 */
	public TarifaByAsiento getTarifaByAsiento() {
		return tarifaByAsiento;
	}
	/**
	 * @param tarifaByAsiento the tarifaByAsiento to set
	 */
	public void setTarifaByAsiento(TarifaByAsiento tarifaByAsiento) {
		this.tarifaByAsiento = tarifaByAsiento;
	}

}
