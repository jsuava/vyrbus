package com.cystesoft.vyrbus.model.bean;


/**
 * Parametros entity. @author MyEclipse Persistence Tools
 */

public class Parametros extends GenericBean implements java.io.Serializable {
	private static final long serialVersionUID = 1L;
	private Integer id;
    private Integer tiempoExpiraReserva;
    private Integer tiempoProgramaPasajeroFrecuente;
    private Integer viajesRequeridosPasajeroFrecuente;
    private Integer tiempoCaducidadBoleto;
    private Integer puntosAcumuladosPasajeroFrecuente;
    private Integer penalidadPostergacion;
    private Integer penalidadReimpresion;
    private Integer penalidadCambioNombre;
    private Integer maximoPostergaciones;
    private Integer tiempoPostergacion;
    private Integer rangoCanjeCumpleanios;
    private Integer tiempoCaducidadPuntos;
    
    private Integer alertarEnvioEspecieValorda;
    private Integer alertarEnvioManifiestoPasajeros;
    private String usuarioGerenciaComercialAprobador;
    private String usuarioComercialAprobador;
    private String usuarioFinanzasAprobador;
    private String usuarioGerenciaComercial;
    private Integer maximoAsientosSeleccionados;
    private Integer tiempoMaximoDuplicarBoleto;
    private String usuarioRemoto;
    private String usuarioAnulaReserva;
    private Integer tiempoExpiraBloqueo;
    private String tiempoExpiraBloqueoPersonalizado;
    private Integer tiempoMaximoPermiteReserva;
    private String proxy;
    private String rolesOmitirValidacionWsMTCError;
    private Integer alertarVentaDNIFalso; //Parametro que indica si debe o no enviar la alerta al registrar una venta con DNI falso
    private Integer validarDNIgetIdentidad; //Pata controlar la validacion del DNI al momento de la venta con el metodo getIdentidad del WS del MTC.
    private Integer tiempoEmisionHRE; //indica el tiempo desde que se puede emitir la hre, tomando en cuenta la hora de partida del servicio
    private Integer validarProgramacionMtc;
    private Integer tiempoExpiracionBloqueComprobante;
    
    // Constructors
    /** default constructor */
    public Parametros() {
    }

	/**
	 * @return the id
	 */
	public Integer getId() {
		return id;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(Integer id) {
		this.id = id;
	}

	/**
	 * @return the tiempoExpiraReserva
	 */
	public Integer getTiempoExpiraReserva() {
		return tiempoExpiraReserva;
	}
	/**
	 * @param tiempoExpiraReserva the tiempoExpiraReserva to set
	 */
	public void setTiempoExpiraReserva(Integer tiempoExpiraReserva) {
		this.tiempoExpiraReserva = tiempoExpiraReserva;
	}

	/**
	 * @return the tiempoProgramaPasajeroFrecuente
	 */
	public Integer getTiempoProgramaPasajeroFrecuente() {
		return tiempoProgramaPasajeroFrecuente;
	}
	/**
	 * @param tiempoProgramaPasajeroFrecuente the tiempoProgramaPasajeroFrecuente to set
	 */
	public void setTiempoProgramaPasajeroFrecuente(Integer tiempoProgramaPasajeroFrecuente) {
		this.tiempoProgramaPasajeroFrecuente = tiempoProgramaPasajeroFrecuente;
	}

	/**
	 * @return the viajesRequeridosPasajeroFrecuente
	 */
	public Integer getViajesRequeridosPasajeroFrecuente() {
		return viajesRequeridosPasajeroFrecuente;
	}
	/**
	 * @param viajesRequeridosPasajeroFrecuente the viajesRequeridosPasajeroFrecuente to set
	 */
	public void setViajesRequeridosPasajeroFrecuente(Integer viajesRequeridosPasajeroFrecuente) {
		this.viajesRequeridosPasajeroFrecuente = viajesRequeridosPasajeroFrecuente;
	}

	/**
	 * @return the tiempoCaducidadBoleto
	 */
	public Integer getTiempoCaducidadBoleto() {
		return tiempoCaducidadBoleto;
	}
	/**
	 * @param tiempoCaducidadBoleto the tiempoCaducidadBoleto to set
	 */
	public void setTiempoCaducidadBoleto(Integer tiempoCaducidadBoleto) {
		this.tiempoCaducidadBoleto = tiempoCaducidadBoleto;
	}

	/**
	 * @return the puntosAcumuladosPasajeroFrecuente
	 */
	public Integer getPuntosAcumuladosPasajeroFrecuente() {
		return puntosAcumuladosPasajeroFrecuente;
	}
	/**
	 * @param puntosAcumuladosPasajeroFrecuente the puntosAcumuladosPasajeroFrecuente to set
	 */
	public void setPuntosAcumuladosPasajeroFrecuente(Integer puntosAcumuladosPasajeroFrecuente) {
		this.puntosAcumuladosPasajeroFrecuente = puntosAcumuladosPasajeroFrecuente;
	}

	/**
	 * @return the penalidadPostergacion
	 */
	public Integer getPenalidadPostergacion() {
		return penalidadPostergacion;
	}
	/**
	 * @param penalidadPostergacion the penalidadPostergacion to set
	 */
	public void setPenalidadPostergacion(Integer penalidadPostergacion) {
		this.penalidadPostergacion = penalidadPostergacion;
	}

	/**
	 * @return the penalidadReimpresion
	 */
	public Integer getPenalidadReimpresion() {
		return penalidadReimpresion;
	}
	/**
	 * @param penalidadReimpresion the penalidadReimpresion to set
	 */
	public void setPenalidadReimpresion(Integer penalidadReimpresion) {
		this.penalidadReimpresion = penalidadReimpresion;
	}

	/**
	 * @return the penalidadCambioNombre
	 */
	public Integer getPenalidadCambioNombre() {
		return penalidadCambioNombre;
	}
	/**
	 * @param penalidadCambioNombre the penalidadCambioNombre to set
	 */
	public void setPenalidadCambioNombre(Integer penalidadCambioNombre) {
		this.penalidadCambioNombre = penalidadCambioNombre;
	}

	/**
	 * @return the maximoPostergaciones
	 */
	public Integer getMaximoPostergaciones() {
		return maximoPostergaciones;
	}
	/**
	 * @param maximoPostergaciones the maximoPostergaciones to set
	 */
	public void setMaximoPostergaciones(Integer maximoPostergaciones) {
		this.maximoPostergaciones = maximoPostergaciones;
	}

	/**
	 * @return the tiempoPostergacion
	 */
	public Integer getTiempoPostergacion() {
		return tiempoPostergacion;
	}
	/**
	 * @param tiempoPostergacion the tiempoPostergacion to set
	 */
	public void setTiempoPostergacion(Integer tiempoPostergacion) {
		this.tiempoPostergacion = tiempoPostergacion;
	}

	/**
	 * @return the rangoCanjeCumpleanios
	 */
	public Integer getRangoCanjeCumpleanios() {
		return rangoCanjeCumpleanios;
	}
	/**
	 * @param rangoCanjeCumpleanios the rangoCanjeCumpleanios to set
	 */
	public void setRangoCanjeCumpleanios(Integer rangoCanjeCumpleanios) {
		this.rangoCanjeCumpleanios = rangoCanjeCumpleanios;
	}

	/**
	 * @return the tiempoCaducidadPuntos
	 */
	public Integer getTiempoCaducidadPuntos() {
		return tiempoCaducidadPuntos;
	}
	/**
	 * @param tiempoCaducidadPuntos the tiempoCaducidadPuntos to set
	 */
	public void setTiempoCaducidadPuntos(Integer tiempoCaducidadPuntos) {
		this.tiempoCaducidadPuntos = tiempoCaducidadPuntos;
	}

	/**
	 * @return the alertarEnvioEspecieValorda
	 */
	public Integer getAlertarEnvioEspecieValorda() {
		return alertarEnvioEspecieValorda;
	}

	/**
	 * @param alertarEnvioEspecieValorda the alertarEnvioEspecieValorda to set
	 */
	public void setAlertarEnvioEspecieValorda(Integer alertarEnvioEspecieValorda) {
		this.alertarEnvioEspecieValorda = alertarEnvioEspecieValorda;
	}

	/**
	 * @return the alertarEnvioManifiestoPasajeros
	 */
	public Integer getAlertarEnvioManifiestoPasajeros() {
		return alertarEnvioManifiestoPasajeros;
	}

	/**
	 * @param alertarEnvioManifiestoPasajeros the alertarEnvioManifiestoPasajeros to set
	 */
	public void setAlertarEnvioManifiestoPasajeros(
			Integer alertarEnvioManifiestoPasajeros) {
		this.alertarEnvioManifiestoPasajeros = alertarEnvioManifiestoPasajeros;
	}

	/**
	 * @return the usuarioGerenciaCommercialAprobador
	 */
	public String getUsuarioGerenciaComercialAprobador() {
		return usuarioGerenciaComercialAprobador;
	}

	/**
	 * @param usuarioGerenciaCommercialAprobador the usuarioGerenciaCommercialAprobador to set
	 */
	public void setUsuarioGerenciaComercialAprobador(
			String usuarioGerenciaComercialAprobador) {
		this.usuarioGerenciaComercialAprobador = usuarioGerenciaComercialAprobador;
	}

	/**
	 * @return the usuarioComercialAprobador
	 */
	public String getUsuarioComercialAprobador() {
		return usuarioComercialAprobador;
	}

	/**
	 * @param usuarioComercialAprobador the usuarioComercialAprobador to set
	 */
	public void setUsuarioComercialAprobador(String usuarioComercialAprobador) {
		this.usuarioComercialAprobador = usuarioComercialAprobador;
	}

	/**
	 * @return the usuarioFinanzasAprobador
	 */
	public String getUsuarioFinanzasAprobador() {
		return usuarioFinanzasAprobador;
	}

	/**
	 * @param usuarioFinanzasAprobador the usuarioFinanzasAprobador to set
	 */
	public void setUsuarioFinanzasAprobador(String usuarioFinanzasAprobador) {
		this.usuarioFinanzasAprobador = usuarioFinanzasAprobador;
	}

	/**
	 * @return the usuarioGerenciaComercial
	 */
	public String getUsuarioGerenciaComercial() {
		return usuarioGerenciaComercial;
	}

	/**
	 * @param usuarioGerenciaComercial the usuarioGerenciaComercial to set
	 */
	public void setUsuarioGerenciaComercial(String usuarioGerenciaComercial) {
		this.usuarioGerenciaComercial = usuarioGerenciaComercial;
	}

	/**
	 * @return the maximoAsientosSeleccionados
	 */
	public Integer getMaximoAsientosSeleccionados() {
		return maximoAsientosSeleccionados;
	}

	/**
	 * @param maximoAsientosSeleccionados the maximoAsientosSeleccionados to set
	 */
	public void setMaximoAsientosSeleccionados(Integer maximoAsientosSeleccionados) {
		this.maximoAsientosSeleccionados = maximoAsientosSeleccionados;
	}

	/**
	 * @return the tiempoMaximoDuplicarBoleto
	 */
	public Integer getTiempoMaximoDuplicarBoleto() {
		return tiempoMaximoDuplicarBoleto;
	}

	/**
	 * @param tiempoMaximoDuplicarBoleto the tiempoMaximoDuplicarBoleto to set
	 */
	public void setTiempoMaximoDuplicarBoleto(Integer tiempoMaximoDuplicarBoleto) {
		this.tiempoMaximoDuplicarBoleto = tiempoMaximoDuplicarBoleto;
	}

	/**
	 * @return the usuarioRemoto
	 */
	public String getUsuarioRemoto() {
		return usuarioRemoto;
	}
	/**
	 * @param usuarioRemoto the usuarioRemoto to set
	 */
	public void setUsuarioRemoto(String usuarioRemoto) {
		this.usuarioRemoto = usuarioRemoto;
	}

	/**
	 * @return the usuarioAnulaReserva
	 */
	public String getUsuarioAnulaReserva() {
		return usuarioAnulaReserva;
	}
	/**
	 * @param usuarioAnulaReserva the usuarioAnulaReserva to set
	 */
	public void setUsuarioAnulaReserva(String usuarioAnulaReserva) {
		this.usuarioAnulaReserva = usuarioAnulaReserva;
	}

	/**
	 * @return the tiempoExpiraBloqueo
	 */
	public Integer getTiempoExpiraBloqueo() {
		return tiempoExpiraBloqueo;
	}
	/**
	 * @param tiempoExpiraBloqueo the tiempoExpiraBloqueo to set
	 */
	public void setTiempoExpiraBloqueo(Integer tiempoExpiraBloqueo) {
		this.tiempoExpiraBloqueo = tiempoExpiraBloqueo;
	}

	/**
	 * @return the tiempoExpiraBloqueoPersonalizado
	 */
	public String getTiempoExpiraBloqueoPersonalizado() {
		return tiempoExpiraBloqueoPersonalizado;
	}

	/**
	 * @param tiempoExpiraBloqueoPersonalizado the tiempoExpiraBloqueoPersonalizado to set
	 */
	public void setTiempoExpiraBloqueoPersonalizado(
			String tiempoExpiraBloqueoPersonalizado) {
		this.tiempoExpiraBloqueoPersonalizado = tiempoExpiraBloqueoPersonalizado;
	}

	/**
	 * @return the tiempoMaximoPermiteReserva
	 */
	public Integer getTiempoMaximoPermiteReserva() {
		return tiempoMaximoPermiteReserva;
	}

	/**
	 * @param tiempoMaximoPermiteReserva the tiempoMaximoPermiteReserva to set
	 */
	public void setTiempoMaximoPermiteReserva(Integer tiempoMaximoPermiteReserva) {
		this.tiempoMaximoPermiteReserva = tiempoMaximoPermiteReserva;
	}

	/**
	 * @return the proxy
	 */
	public String getProxy() {
		return proxy;
	}

	/**
	 * @param proxy the proxy to set
	 */
	public void setProxy(String proxy) {
		this.proxy = proxy;
	}

	/**
	 * @return the rolesOmitirValidacionWsMTCError
	 */
	public String getRolesOmitirValidacionWsMTCError() {
		return rolesOmitirValidacionWsMTCError;
	}

	/**
	 * @param rolesOmitirValidacionWsMTCError the rolesOmitirValidacionWsMTCError to set
	 */
	public void setRolesOmitirValidacionWsMTCError(
			String rolesOmitirValidacionWsMTCError) {
		this.rolesOmitirValidacionWsMTCError = rolesOmitirValidacionWsMTCError;
	}

	/**
	 * @return the alertarVentaDNIFalso
	 */
	public Integer getAlertarVentaDNIFalso() {
		return alertarVentaDNIFalso;
	}

	/**
	 * @param alertarVentaDNIFalso the alertarVentaDNIFalso to set
	 */
	public void setAlertarVentaDNIFalso(Integer alertarVentaDNIFalso) {
		this.alertarVentaDNIFalso = alertarVentaDNIFalso;
	}

	/**
	 * @return the validarDNIgetIdentidad
	 */
	public Integer getValidarDNIgetIdentidad() {
		return validarDNIgetIdentidad;
	}

	/**
	 * @param validarDNIgetIdentidad the validarDNIgetIdentidad to set
	 */
	public void setValidarDNIgetIdentidad(Integer validarDNIgetIdentidad) {
		this.validarDNIgetIdentidad = validarDNIgetIdentidad;
	}

	/**
	 * @return the tiempoEmisionHRE
	 */
	public Integer getTiempoEmisionHRE() {
		return tiempoEmisionHRE;
	}

	/**
	 * @param tiempoEmisionHRE the tiempoEmisionHRE to set
	 */
	public void setTiempoEmisionHRE(Integer tiempoEmisionHRE) {
		this.tiempoEmisionHRE = tiempoEmisionHRE;
	}

	/**
	 * @return the validarProgramacionMtc
	 */
	public Integer getValidarProgramacionMtc() {
		return validarProgramacionMtc;
	}

	/**
	 * @param validarProgramacionMtc the validarProgramacionMtc to set
	 */
	public void setValidarProgramacionMtc(Integer validarProgramacionMtc) {
		this.validarProgramacionMtc = validarProgramacionMtc;
	}

	/**
	 * @return the tiempoExpiracionBloqueComprobante
	 */
	public Integer getTiempoExpiracionBloqueComprobante() {
		return tiempoExpiracionBloqueComprobante;
	}

	/**
	 * @param tiempoExpiracionBloqueComprobante the tiempoExpiracionBloqueComprobante to set
	 */
	public void setTiempoExpiracionBloqueComprobante(
			Integer tiempoExpiracionBloqueComprobante) {
		this.tiempoExpiracionBloqueComprobante = tiempoExpiracionBloqueComprobante;
	}

	
}