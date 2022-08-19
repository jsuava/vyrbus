package com.cystesoft.vyrbus.model.bean;
// default package


/**
 * Moneda entity. @author MyEclipse Persistence Tools
 */

public class VSMoneda  extends GenericBean implements java.io.Serializable {
	private static final long serialVersionUID = 1L;

    // Fields
	private Integer id;
     private String denominacion;
     private String codigo;


    // Constructors
    /** default constructor */
    public VSMoneda() {
    }
    public VSMoneda(Integer id){
    	this.id=id;
    }

    // Property accessors
    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDenominacion() {
        return this.denominacion;
    }

    public void setDenominacion(String denominacion) {
        this.denominacion = denominacion;
    }

    public String getCodigo() {
        return this.codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

}