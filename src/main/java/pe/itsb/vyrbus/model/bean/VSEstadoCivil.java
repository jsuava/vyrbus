/**
 * Proyecto		: SISVYR
 * Sistema		: Sistema de Ventas y Reservas
 * Descripción	:
 * Fecha		: 24/06/2014
 */
package pe.itsb.vyrbus.model.bean;

import java.io.Serializable;


/**
 * @author JABANTO
 *
 */
public class VSEstadoCivil extends GenericBean  implements Serializable {
	private static final long serialVersionUID = 1L;

	// Fields
     private Integer id;
     private String denominacion;
     private Integer codigo;

    // Constructors
    /** default constructor */
    public VSEstadoCivil() {
    }
    public VSEstadoCivil(Integer id){
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

    public Integer getCodigo() {
        return this.codigo;
    }

    public void setCodigo(Integer codigo) {
        this.codigo = codigo;
    }

}
