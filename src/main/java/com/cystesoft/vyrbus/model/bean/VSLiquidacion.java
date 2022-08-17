/**
 * Proyecto		: SISVYR
 * Sistema		: Sistema de Ventas y Reservas
 * Descripción	:
 * Autor		: José Avalos Sullo
 * Fecha		: 26/06/2014
 */
package com.cystesoft.vyrbus.model.bean;

import java.io.Serializable;
import java.util.Date;

/**
 * @author JABANTO
 *
 */
public class VSLiquidacion extends GenericBean implements Serializable{
	private static final long serialVersionUID = 1L;
	// Fields
	 private Integer id;
     private Integer anio;
     private Integer usuarioID;
     private Integer agenciaID;
     private String nombreUsuario;
     private Date fechaLiquidacion;
     private Double montoIngresado;
     private Integer estadoLiquidacion;

     private Integer cantidadVentasPaxFree=0;		//No Mapeado
     private Integer cantidadVentasPaxNromal=0;		//No Mapeado
     private double montoVentasPaxFree=.00;			//No Mapeado
     private double montoVentasPaxNromal=.00;		//No Mapeado
     private Integer cantidadVentasTotal=0;			//No Mapeado
     private double montoVentasTotal=.00;			//No Mapeado

    // Constructors
    /** default constructor */
    public VSLiquidacion() {
    }
    // Property accessors

    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getAnio() {
        return this.anio;
    }

    public void setAnio(Integer anio) {
        this.anio = anio;
    }

    public String getNombreUsuario() {
        return this.nombreUsuario;
    }

    public void setNombreUsuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }

    public Date getFechaLiquidacion() {
        return this.fechaLiquidacion;
    }

    public void setFechaLiquidacion(Date fechaLiquidacion) {
        this.fechaLiquidacion = fechaLiquidacion;
    }

    public Double getMontoIngresado() {
        return this.montoIngresado;
    }

    public void setMontoIngresado(Double montoIngresado) {
        this.montoIngresado = montoIngresado;
    }

    public Integer getEstadoLiquidacion() {
        return this.estadoLiquidacion;
    }

    public void setEstadoLiquidacion(Integer estadoLiquidacion) {
        this.estadoLiquidacion = estadoLiquidacion;
    }

	/**
	 * @return the usuarioID
	 */
	public Integer getUsuarioID() {
		return usuarioID;
	}

	/**
	 * @param usuarioID the usuarioID to set
	 */
	public void setUsuarioID(Integer usuarioID) {
		this.usuarioID = usuarioID;
	}

	/**
	 * @return the agenciaID
	 */
	public Integer getAgenciaID() {
		return agenciaID;
	}

	/**
	 * @param agenciaID the agenciaID to set
	 */
	public void setAgenciaID(Integer agenciaID) {
		this.agenciaID = agenciaID;
	}

	/**
	 * @return the cantidadVentasPaxFree
	 */
	public Integer getCantidadVentasPaxFree() {
		return cantidadVentasPaxFree;
	}

	/**
	 * @param cantidadVentasPaxFree the cantidadVentasPaxFree to set
	 */
	public void setCantidadVentasPaxFree(Integer cantidadVentasPaxFree) {
		this.cantidadVentasPaxFree = cantidadVentasPaxFree;
	}

	/**
	 * @return the cantidadVentasPaxNromal
	 */
	public Integer getCantidadVentasPaxNromal() {
		return cantidadVentasPaxNromal;
	}

	/**
	 * @param cantidadVentasPaxNromal the cantidadVentasPaxNromal to set
	 */
	public void setCantidadVentasPaxNromal(Integer cantidadVentasPaxNromal) {
		this.cantidadVentasPaxNromal = cantidadVentasPaxNromal;
	}

	/**
	 * @return the montoVentasPaxFree
	 */
	public double getMontoVentasPaxFree() {
		return montoVentasPaxFree;
	}

	/**
	 * @param montoVentasPaxFree the montoVentasPaxFree to set
	 */
	public void setMontoVentasPaxFree(double montoVentasPaxFree) {
		this.montoVentasPaxFree = montoVentasPaxFree;
	}

	/**
	 * @return the montoVentasPaxNromal
	 */
	public double getMontoVentasPaxNromal() {
		return montoVentasPaxNromal;
	}

	/**
	 * @param montoVentasPaxNromal the montoVentasPaxNromal to set
	 */
	public void setMontoVentasPaxNromal(double montoVentasPaxNromal) {
		this.montoVentasPaxNromal = montoVentasPaxNromal;
	}

	/**
	 * @return the cantidadTotal
	 */
	public Integer getCantidadVentasTotal() {
		return cantidadVentasTotal;
	}

	/**
	 * @param cantidadVentasTotal the cantidadTotal to set
	 */
	public void setCantidadVentasTotal(Integer cantidadVentasTotal) {
		this.cantidadVentasTotal = cantidadVentasTotal;
	}

	/**
	 * @return the montoVentastotal
	 */
	public double getMontoVentasTotal() {
		return montoVentasTotal;
	}

	/**
	 * @param montoVentastotal the montoVentastotal to set
	 */
	public void setMontoVentasTotal(double montoVentastotal) {
		this.montoVentasTotal = montoVentastotal;
	}
}
