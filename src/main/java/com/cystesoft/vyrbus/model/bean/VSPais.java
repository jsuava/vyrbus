package com.cystesoft.vyrbus.model.bean;
// default package

/**
 * 
 * @author JABANTO
 *
 */
public class VSPais  extends GenericBean implements java.io.Serializable {
	private static final long serialVersionUID = 1L;

    // Fields    
	private Integer id;
     private String denominacion;
     private String docigo;
    

    // Constructors
    /** default constructor */
    public VSPais() {
    }

    /**
	 * @param genAfiPaisPeru
	 */
	public VSPais(Integer id) {
		// TODO Auto-generated constructor stub
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

    public String getDocigo() {
        return this.docigo;
    }
    
    public void setDocigo(String docigo) {
        this.docigo = docigo;
    }




}