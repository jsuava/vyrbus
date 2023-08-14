/**
 * Proyecto		: SISVYR
 * Sistema		: Sistema de Ventas y Reservas
 * Descripción	:
 * Autor		: José Avalos
 * Fecha		: 25/10/2012
 */
package pe.itsb.vyrbus.service.mappers;

/**
 * @author Jose
 *
 */
public class Coordenada {
	private Integer fila;
	private Integer columna;
	private Integer piso;
	/**
	 *
	 */
	public Coordenada() {
		super();
	}
	/**
	 * @param fila
	 * @param columna
	 * @param piso
	 */
	public Coordenada(Integer fila, Integer columna, Integer piso) {
		super();
		this.fila = fila;
		this.columna = columna;
		this.piso = piso;
	}

	/**
	 * @return the fila
	 */
	public Integer getFila() {
		return fila;
	}
	/**
	 * @param fila the fila to set
	 */
	public void setFila(Integer fila) {
		this.fila = fila;
	}

	/**
	 * @return the columna
	 */
	public Integer getColumna() {
		return columna;
	}
	/**
	 * @param columna the columna to set
	 */
	public void setColumna(Integer columna) {
		this.columna = columna;
	}

	/**
	 * @return the piso
	 */
	public Integer getPiso() {
		return piso;
	}
	/**
	 * @param piso the piso to set
	 */
	public void setPiso(Integer piso) {
		this.piso = piso;
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return String.valueOf(fila + "-" + columna + "-" + piso);
	}
}
