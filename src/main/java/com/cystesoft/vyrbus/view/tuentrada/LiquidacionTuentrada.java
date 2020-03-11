/**
 * Proyecto		: SISVYR
 * Sistema		: Sistema de Ventas y Reservas
 * Descripción	: Clase para almacenar los datos de la liquidacion de Tuentrada,
 * Autor		: José Avalos Sullo
 * Fecha		: 09/06/2014
 */
package com.cystesoft.vyrbus.view.tuentrada;

/**
 * 
 * @author Jose
 *
 */
public class LiquidacionTuentrada {
	private Integer cantidad;
	private Double monto;
	private String tipo;
	
//	private String usuarioNombres;
//	private String usuarioLogin;
//	private String cantidadEfectivo="0";
//	private String montoEfectivo="0.00";
//	private String cantidadTarjeta="0";
//	private String montoTarjeta="0.00";
//	private String fechaOperacion;
	
		
	public LiquidacionTuentrada() {
		super();
	}

	/**
	 * @param cantidad
	 * @param monto
	 * @param tipo
	 */
	public LiquidacionTuentrada(Integer cantidad, Double monto, String tipo) {
		super();
		this.cantidad = cantidad;
		this.monto = monto;
		this.tipo = tipo;
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
	 * @return the monto
	 */
	public Double getMonto() {
		return monto;
	}
	/**
	 * @param monto the monto to set
	 */
	public void setMonto(Double monto) {
		this.monto = monto;
	}

	/**
	 * @return the tipo
	 */
	public String getTipo() {
		return tipo;
	}
	/**
	 * @param tipo the tipo to set
	 */
	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	
}
