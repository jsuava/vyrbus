package pe.itsb.vyrbus.model.bean;
// default package
// Generated 24/05/2012 11:51:01 AM by Hibernate Tools 3.4.0.CR1

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 *
 * @author JABANTO
 *
 */
public class Liquidacion extends GenericBean implements java.io.Serializable {
	private static final long serialVersionUID = 1L;
	private Integer id;
	private Integer anio;
	private Agencia agencia;
	private Usuario usuario;
	private String nombreUsuario;
	private Date fechaLiquidacion;
	private Double montoIngresado;
	private Integer estadoLiquidacion;
	private Double montoIngresadoDolares;

	private Set<DetalleLiquidacion> detalleLiquidaciones = new HashSet<>(0);
	private Set<LiquidacionOficina> liquidacionOficinas = new HashSet<>(0);

	//NO MAPEADO
	//Resumen Especie Valorada
	private String comprobante;
	private String serie;
	private String boletoInicial;
	private String boletoFinal;
	private Integer cantidadBoletos;
	private Double importe;
	private Integer cortes;
	private TipoComprobante tipoComprobante;


	//Resumen de ventas
	private double totalVoublers;
	private Integer cantidadContado;
	private double montoContado;
	private Integer cantidadTarjetaVisa;
	private double montoTarjetaVisa;
	private Integer cantidadTarjetaMasterCard;
	private double montoTarjetaMasterCard;
	private Integer cantidadCortesia;
	private double montoCortesia;
	private Integer cantidadPrepagado;
	private double montoPrepagado;
	private Integer cantidadCreditos;
	private double montoCreditos;
	private Integer cantidadPCE;
	private double montoPCE;
	private Integer cantidadCreditosDolares;
	private double montoCreditosDolares;
	private Integer cantidadContadoDolares;
	private double montoContadoDolares;
	private Integer cantidadTransferencias;
	private double montoTransferencias;
	private Integer cantidadYape;
	private double montoYape;


	//recibos de caja con tarjeta
	private Integer cantidadTarjetaVisaRC;
	private double montoTarjetaVisaRC;
	private Integer cantidadTarjetaMasterCardRC;
	private double montoTarjetaMasterCardRC;


	//Otros ingresos
	private Integer cantidadRC;
	private double montoRC;
	private Integer cantidadGastosAdminEfectivo;
	private double montoGastosAdminEfectivo;
	private Integer cantidadGastosAdminTarjetaVisa;
	private double montoGastosAdminTarjetaVisa;
	private Integer cantidadGastosAdminTarjetaMastercard;
	private double montoGastosAdminTarjetaMastercard;

	//Egresos
	private Integer cantidadGastoVarios;
	private double montoGastoVarios;
	private Integer cantidadPeajes;
	private double montoPeajes;
	private Integer cantidadPagoGiros;
	private double montoPagoGiros;
	private Integer cantidadDevolucion;
	private double montoDevolucion;
	private Integer cantidadGastoConDocumento;
	private double montoGastoConDocumento;
	private Integer cantidadNotasCredito;
	private double montoNotasCredito;
	private Integer cantidadEfecPool;
	private double montoEfecPool;
	private Integer cantidadTCVisaPool;
	private double montoTCVisaPool;
	private Integer cantidadTCMastercardPool;
	private double montoTCMastercardPool;

	/*Operaciones complementarias*/
	private Integer cantidadDevolucionTarjeta;
	private double montoDevolucionTarjeta;
//	private Integer cantidadDevolucion80;
//	private double montoDevolucion80;
//	private Integer cantidadDevolucion100;
//	private double montoDevolucion100;

	private Liquidacion liquidacionCarga;

	public Liquidacion() {
	}

	public Liquidacion(Integer id) {
		super();
		this.id = id;
	}

	/**
	 * @return Objeto id.
	 */
	public Integer getId() {
		return id;
	}
	/**
	 * @param id	: Setea el objeto id.
	 */
	public void setId(Integer id) {
		this.id = id;
	}

	/**
	 * @return Objeto anio.
	 */
	public Integer getAnio() {
		return anio;
	}
	/**
	 * @param anio	: Setea el objeto anio.
	 */
	public void setAnio(Integer anio) {
		this.anio = anio;
	}

	/**
	 * @return Objeto agencia.
	 */
	public Agencia getAgencia() {
		return agencia;
	}
	/**
	 * @param agencia	: Setea el objeto agencia.
	 */
	public void setAgencia(Agencia agencia) {
		this.agencia = agencia;
	}

	/**
	 * @return Objeto usuario.
	 */
	public Usuario getUsuario() {
		return usuario;
	}
	/**
	 * @param usuario	: Setea el objeto usuario.
	 */
	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	/**
	 * @return Objeto nombreUsuario.
	 */
	public String getNombreUsuario() {
		return nombreUsuario;
	}
	/**
	 * @param nombreUsuario	: Setea el objeto nombreUsuario.
	 */
	public void setNombreUsuario(String nombreUsuario) {
		this.nombreUsuario = nombreUsuario;
	}

	/**
	 * @return Objeto fechaLiquidacion.
	 */
	public Date getFechaLiquidacion() {
		return fechaLiquidacion;
	}
	/**
	 * @param fechaLiquidacion	: Setea el objeto fechaLiquidacion.
	 */
	public void setFechaLiquidacion(Date fechaLiquidacion) {
		this.fechaLiquidacion = fechaLiquidacion;
	}

	/**
	 * @return Objeto montoIngresado.
	 */
	public Double getMontoIngresado() {
		return montoIngresado;
	}
	/**
	 * @param montoIngresado	: Setea el objeto montoIngresado.
	 */
	public void setMontoIngresado(Double montoIngresado) {
		this.montoIngresado = montoIngresado;
	}
	/**
	 * @return Objeto estadoLiquidacion.
	 */
	public Integer getestadoLiquidacion(){
		return estadoLiquidacion;
	}
	/**
	 * @param estadoLiquidacion	: Setea el objeto estadoLiquidacion.
	 */
	public void setEstadoLiquidacion(Integer estadoLiquidacion){
		this.estadoLiquidacion=estadoLiquidacion;
	}


	//NO MAPEADOS-UTILIZADOS PARA EL REPORTE DE LA LIQUIDACION DE TURNO//
	//Especie valoradas
	public Integer getCantidadBoletos(){
		return cantidadBoletos;
	}
	public void setCantidadBoletos(Integer cantidadBoletos){
		this.cantidadBoletos=cantidadBoletos;
	}

	public String getboletoFinal(){
		return boletoFinal;
	}
	public void setBoletoFinal(String boletoFinal){
		this.boletoFinal=boletoFinal;
	}

	public String getBoletoInicial(){
		return boletoInicial;
	}
	public void setBoletoInicial(String boletoInicial){
		this.boletoInicial=boletoInicial;
	}

	public String getSerie(){
		return serie;
	}
	public void setSerie(String serie){
		this.serie=serie;
	}

	public TipoComprobante getTipoComprobante(){
		return tipoComprobante;
	}
	public void setTipoComprobante(TipoComprobante tipoComprobante){
		this.tipoComprobante=tipoComprobante;
	}

	public Integer getCorte(){
		return cortes;
	}
	public void setCorte(Integer corte){
		this.cortes=corte;
	}


	//Resumen de ventas
	public double getTotalVouchers(){
		return totalVoublers;
	}
	public void setTotalVouchers(double totalVouchers){
		this.totalVoublers=totalVouchers;
	}
	public Integer getCantidadContado(){
		return cantidadContado;
	}
	public void setCantidadContado(Integer cantidadContado){
		this.cantidadContado=cantidadContado;
	}
	public double getMontoContado(){
		return montoContado;
	}
	public void setMontoContado(double montocontado){
		this.montoContado=montocontado;
	}
	public Integer getCantidadTarjetaVisa(){
		return cantidadTarjetaVisa;
	}
	public void setCantidadTarjetaVisa(Integer cantidadTarjetaVisa){
		this.cantidadTarjetaVisa=cantidadTarjetaVisa;
	}
	public double getMontoTarjetaVisa(){
		return montoTarjetaVisa;
	}
	public void setMontoTarjetaVisa(double montoTarjetavisa){
		this.montoTarjetaVisa=montoTarjetavisa;
	}
	public Integer getCantidadTarjetaMasterCard(){
		return cantidadTarjetaMasterCard;
	}
	public void setCantidadTarjetaMasterCard(Integer cantidadTarjetaMasterCard){
		this.cantidadTarjetaMasterCard=cantidadTarjetaMasterCard;
	}
	public double getMontoTarjetaMasterCard(){
		return montoTarjetaMasterCard;
	}
	public void setMontoTarjetaMasterCard(double montoTarjetaMasterCard){
		this.montoTarjetaMasterCard=montoTarjetaMasterCard;
	}
	public Integer getCantidadcortesia(){
		return cantidadCortesia;
	}
	public void setCantidadCortesia(Integer cantidadCortesia){
		this.cantidadCortesia=cantidadCortesia;
	}
	public double getMontoCortesia(){
		return montoCortesia;
	}
	public void setMontoCortesia(double montoCortesia){
		this.montoCortesia=montoCortesia;
	}
	public Integer getCantidadPrepagado(){
		return cantidadPrepagado;
	}
	public void setCantidadPrepagado(Integer cantidadPrepagado){
		this.cantidadPrepagado=cantidadPrepagado;
	}
	public double getMontoPrepagado(){
		return montoPrepagado;
	}
	public void setMontoPrepagado(double montoPrepagado){
		this.montoPrepagado=montoPrepagado;
	}
	public Integer getCantidadCreditos(){
		return cantidadCreditos;
	}
	public void setCantidadCreditos(Integer cantidadCreditos){
		this.cantidadCreditos=cantidadCreditos;
	}
	public double getMontoCreditos(){
		return montoCreditos;
	}
	public void setMontoCreditos(double montoCreditos){
		this.montoCreditos=montoCreditos;
	}
	//Otros ingresos
	public Integer getCantidadRCCaja(){
		return cantidadRC;
	}
	public void setCantidadRC(Integer cantidadRC){
		this.cantidadRC=cantidadRC;
	}
	public double getMontoRC(){
		return montoRC;
	}
	public void setMontoRC(double montoRC){
		this.montoRC=montoRC;
	}
	//Egresos.
	public Integer getCantidadGastoVarios(){
		return cantidadGastoVarios;
	}
	public void setCantidadGastoVarios(Integer cantidadGastoVarios){
		this.cantidadGastoVarios=cantidadGastoVarios;
	}
	public double getMontoGastoVarios(){
		return montoGastoVarios;
	}
	public void setMontoGastoVarios(double montoGastoVarios){
		this.montoGastoVarios=montoGastoVarios;
	}
	public Integer getCantidadPeajes(){
		return cantidadPeajes;
	}
	public void setCantidadPeajes(Integer cantidadPeajes){
		this.cantidadPeajes=cantidadPeajes;
	}
	public double getMontoPeajes(){
		return montoPeajes;
	}
	public void setMontoPeajes(double montoPeajes){
		this.montoPeajes=montoPeajes;
	}
	public Integer getCantidadPagoGiros(){
		return cantidadPagoGiros;
	}
	public void setCantidadPagoGiros(Integer cantidadPagoGiros){
		this.cantidadPagoGiros=cantidadPagoGiros;
	}
	public double getMontoPagoGiros(){
		return montoPagoGiros;
	}
	public void setMontoPagoGiros(double montoPagoGiros){
		this.montoPagoGiros=montoPagoGiros;
	}
	public Integer getCantidadDevolucion (){
		return cantidadDevolucion;
	}
	public void setCantidadDevolucion(Integer cantidadDevolucion){
		this.cantidadDevolucion=cantidadDevolucion;
	}
	public double getMontoDevolucion(){
		return montoDevolucion;
	}
	public void setMontoDevolucion(double montodDevolucion){
		this.montoDevolucion=montodDevolucion;
	}
//	public Integer getCantidadDevolucion80 (){
//		return cantidadDevolucion80;
//	}
//	public void setCantidadDevolucion80(Integer cantidadDevolucion80){
//		this.cantidadDevolucion80=cantidadDevolucion80;
//	}
//	public double getMontoDevolucion80(){
//		return montoDevolucion80;
//	}
//	public void setMontoDevolucion80(double montodDevolucion80){
//		this.montoDevolucion80=montodDevolucion80;
//	}
//	public Integer getCantidadDevolucion100(){
//		return cantidadDevolucion100;
//	}
//	public void setCantidadDevolucion100(Integer cantidaddDevolucion100){
//		this.cantidadDevolucion100=cantidaddDevolucion100;
//	}
//	public double getMontoDevolucion100(){
//		return montoDevolucion100;
//	}
//	public void setMontoDevolucion100(double montoDevolucion100){
//		this.montoDevolucion100=montoDevolucion100;
//	}

	public Set<DetalleLiquidacion> getDetalleLiquidacions() {
		return this.detalleLiquidaciones;
	}
	public void setDetalleLiquidacions(
			Set<DetalleLiquidacion> detalleLiquidacions) {
		this.detalleLiquidaciones = detalleLiquidacions;
	}

	public Set<LiquidacionOficina> getLiquidacionOficinas() {
		return this.liquidacionOficinas;
	}
	public void setLiquidacionOficinas(
			Set<LiquidacionOficina> liquidacionOficinas) {
		this.liquidacionOficinas = liquidacionOficinas;
	}

	/**
	 * @return the cantidadTarjetaVisaRC
	 */
	public Integer getCantidadTarjetaVisaRC() {
		return cantidadTarjetaVisaRC;
	}

	/**
	 * @param cantidadTarjetaVisaRC the cantidadTarjetaVisaRC to set
	 */
	public void setCantidadTarjetaVisaRC(Integer cantidadTarjetaVisaRC) {
		this.cantidadTarjetaVisaRC = cantidadTarjetaVisaRC;
	}

	/**
	 * @return the montoTarjetaVisaRC
	 */
	public double getMontoTarjetaVisaRC() {
		return montoTarjetaVisaRC;
	}

	/**
	 * @param montoTarjetaVisaRC the montoTarjetaVisaRC to set
	 */
	public void setMontoTarjetaVisaRC(double montoTarjetaVisaRC) {
		this.montoTarjetaVisaRC = montoTarjetaVisaRC;
	}

	/**
	 * @return the cantidadTarjetaMasterCardRC
	 */
	public Integer getCantidadTarjetaMasterCardRC() {
		return cantidadTarjetaMasterCardRC;
	}

	/**
	 * @param cantidadTarjetaMasterCardRC the cantidadTarjetaMasterCardRC to set
	 */
	public void setCantidadTarjetaMasterCardRC(
			Integer cantidadTarjetaMasterCardRC) {
		this.cantidadTarjetaMasterCardRC = cantidadTarjetaMasterCardRC;
	}

	/**
	 * @return the montoTarjetaMasterCardRC
	 */
	public double getMontoTarjetaMasterCardRC() {
		return montoTarjetaMasterCardRC;
	}

	/**
	 * @param montoTarjetaMasterCardRC the montoTarjetaMasterCardRC to set
	 */
	public void setMontoTarjetaMasterCardRC(double montoTarjetaMasterCardRC) {
		this.montoTarjetaMasterCardRC = montoTarjetaMasterCardRC;
	}

	/**
	 * @return the cantidadGastoConDocumento
	 */
	public Integer getCantidadGastoConDocumento() {
		return cantidadGastoConDocumento;
	}

	/**
	 * @param cantidadGastoConDocumento the cantidadGastoConDocumento to set
	 */
	public void setCantidadGastoConDocumento(Integer cantidadGastoConDocumento) {
		this.cantidadGastoConDocumento = cantidadGastoConDocumento;
	}

	/**
	 * @return the montoGastoConDocumento
	 */
	public double getMontoGastoConDocumento() {
		return montoGastoConDocumento;
	}

	/**
	 * @param montoGastoConDocumento the montoGastoConDocumento to set
	 */
	public void setMontoGastoConDocumento(double montoGastoConDocumento) {
		this.montoGastoConDocumento = montoGastoConDocumento;
	}

	/**
	 * @return the cantidadNotasCredito
	 */
	public Integer getCantidadNotasCredito() {
		return cantidadNotasCredito;
	}

	/**
	 * @param cantidadNotasCredito the cantidadNotasCredito to set
	 */
	public void setCantidadNotasCredito(Integer cantidadNotasCredito) {
		this.cantidadNotasCredito = cantidadNotasCredito;
	}

	/**
	 * @return the montoNotasCredito
	 */
	public double getMontoNotasCredito() {
		return montoNotasCredito;
	}

	/**
	 * @param montoNotasCredito the montoNotasCredito to set
	 */
	public void setMontoNotasCredito(double montoNotasCredito) {
		this.montoNotasCredito = montoNotasCredito;
	}

	/**
	 * @return the cantidadEfecPool
	 */
	public Integer getCantidadEfecPool() {
		return cantidadEfecPool;
	}

	/**
	 * @param cantidadEfecPool the cantidadEfecPool to set
	 */
	public void setCantidadEfecPool(Integer cantidadEfecPool) {
		this.cantidadEfecPool = cantidadEfecPool;
	}

	/**
	 * @return the montoEfecPool
	 */
	public double getMontoEfecPool() {
		return montoEfecPool;
	}

	/**
	 * @param montoEfecPool the montoEfecPool to set
	 */
	public void setMontoEfecPool(double montoEfecPool) {
		this.montoEfecPool = montoEfecPool;
	}

	/**
	 * @return the cantidadTCVisaPool
	 */
	public Integer getCantidadTCVisaPool() {
		return cantidadTCVisaPool;
	}

	/**
	 * @param cantidadTCVisaPool the cantidadTCVisaPool to set
	 */
	public void setCantidadTCVisaPool(Integer cantidadTCVisaPool) {
		this.cantidadTCVisaPool = cantidadTCVisaPool;
	}

	/**
	 * @return the montoTCVisaPool
	 */
	public double getMontoTCVisaPool() {
		return montoTCVisaPool;
	}

	/**
	 * @param montoTCVisaPool the montoTCVisaPool to set
	 */
	public void setMontoTCVisaPool(double montoTCVisaPool) {
		this.montoTCVisaPool = montoTCVisaPool;
	}

	/**
	 * @return the cantidadTCMastercardPool
	 */
	public Integer getCantidadTCMastercardPool() {
		return cantidadTCMastercardPool;
	}

	/**
	 * @param cantidadTCMastercardPool the cantidadTCMastercardPool to set
	 */
	public void setCantidadTCMastercardPool(Integer cantidadTCMastercardPool) {
		this.cantidadTCMastercardPool = cantidadTCMastercardPool;
	}

	/**
	 * @return the montoTCMastercardPool
	 */
	public double getMontoTCMastercardPool() {
		return montoTCMastercardPool;
	}

	/**
	 * @param montoTCMastercardPool the montoTCMastercardPool to set
	 */
	public void setMontoTCMastercardPool(double montoTCMastercardPool) {
		this.montoTCMastercardPool = montoTCMastercardPool;
	}

	/**
	 * @return the cantidadDevolucionTarjeta
	 */
	public Integer getCantidadDevolucionTarjeta() {
		return cantidadDevolucionTarjeta;
	}

	/**
	 * @param cantidadDevolucionTarjeta the cantidadDevolucionTarjeta to set
	 */
	public void setCantidadDevolucionTarjeta(Integer cantidadDevolucionTarjeta) {
		this.cantidadDevolucionTarjeta = cantidadDevolucionTarjeta;
	}

	/**
	 * @return the montoDevolucionTarjeta
	 */
	public double getMontoDevolucionTarjeta() {
		return montoDevolucionTarjeta;
	}

	/**
	 * @param montoDevolucionTarjeta the montoDevolucionTarjeta to set
	 */
	public void setMontoDevolucionTarjeta(double montoDevolucionTarjeta) {
		this.montoDevolucionTarjeta = montoDevolucionTarjeta;
	}

	/**
	 * @return the cantidadGastosAdminEfectivo
	 */
	public Integer getCantidadGastosAdminEfectivo() {
		return cantidadGastosAdminEfectivo;
	}

	/**
	 * @param cantidadGastosAdminEfectivo the cantidadGastosAdminEfectivo to set
	 */
	public void setCantidadGastosAdminEfectivo(
			Integer cantidadGastosAdminEfectivo) {
		this.cantidadGastosAdminEfectivo = cantidadGastosAdminEfectivo;
	}

	/**
	 * @return the montoGastosAdminEfectivo
	 */
	public double getMontoGastosAdminEfectivo() {
		return montoGastosAdminEfectivo;
	}

	/**
	 * @param montoGastosAdminEfectivo the montoGastosAdminEfectivo to set
	 */
	public void setMontoGastosAdminEfectivo(double montoGastosAdminEfectivo) {
		this.montoGastosAdminEfectivo = montoGastosAdminEfectivo;
	}

	/**
	 * @return the cantidadGastosAdminTarjetaVisa
	 */
	public Integer getCantidadGastosAdminTarjetaVisa() {
		return cantidadGastosAdminTarjetaVisa;
	}

	/**
	 * @param cantidadGastosAdminTarjetaVisa the cantidadGastosAdminTarjetaVisa to set
	 */
	public void setCantidadGastosAdminTarjetaVisa(
			Integer cantidadGastosAdminTarjetaVisa) {
		this.cantidadGastosAdminTarjetaVisa = cantidadGastosAdminTarjetaVisa;
	}

	/**
	 * @return the montoGastosAdminTarjetaVisa
	 */
	public double getMontoGastosAdminTarjetaVisa() {
		return montoGastosAdminTarjetaVisa;
	}

	/**
	 * @param montoGastosAdminTarjetaVisa the montoGastosAdminTarjetaVisa to set
	 */
	public void setMontoGastosAdminTarjetaVisa(double montoGastosAdminTarjetaVisa) {
		this.montoGastosAdminTarjetaVisa = montoGastosAdminTarjetaVisa;
	}

	/**
	 * @return the cantidadGastosAdminTarjetaMastercard
	 */
	public Integer getCantidadGastosAdminTarjetaMastercard() {
		return cantidadGastosAdminTarjetaMastercard;
	}

	/**
	 * @param cantidadGastosAdminTarjetaMastercard the cantidadGastosAdminTarjetaMastercard to set
	 */
	public void setCantidadGastosAdminTarjetaMastercard(
			Integer cantidadGastosAdminTarjetaMastercard) {
		this.cantidadGastosAdminTarjetaMastercard = cantidadGastosAdminTarjetaMastercard;
	}

	/**
	 * @return the montoGastosAdminTarjetaMastercard
	 */
	public double getMontoGastosAdminTarjetaMastercard() {
		return montoGastosAdminTarjetaMastercard;
	}

	/**
	 * @param montoGastosAdminTarjetaMastercard the montoGastosAdminTarjetaMastercard to set
	 */
	public void setMontoGastosAdminTarjetaMastercard(
			double montoGastosAdminTarjetaMastercard) {
		this.montoGastosAdminTarjetaMastercard = montoGastosAdminTarjetaMastercard;
	}

	/**
	 * @return the cantidadPCE
	 */
	public Integer getCantidadPCE() {
		return cantidadPCE;
	}

	/**
	 * @param cantidadPCE the cantidadPCE to set
	 */
	public void setCantidadPCE(Integer cantidadPCE) {
		this.cantidadPCE = cantidadPCE;
	}

	/**
	 * @return the montoPCE
	 */
	public double getMontoPCE() {
		return montoPCE;
	}

	/**
	 * @param montoPCE the montoPCE to set
	 */
	public void setMontoPCE(double montoPCE) {
		this.montoPCE = montoPCE;
	}

	/**
	 * @return the cantidadCreditosDolares
	 */
	public Integer getCantidadCreditosDolares() {
		return cantidadCreditosDolares;
	}

	/**
	 * @param cantidadCreditosDolares the cantidadCreditosDolares to set
	 */
	public void setCantidadCreditosDolares(Integer cantidadCreditosDolares) {
		this.cantidadCreditosDolares = cantidadCreditosDolares;
	}

	/**
	 * @return the montoCreditosDolares
	 */
	public double getMontoCreditosDolares() {
		return montoCreditosDolares;
	}

	/**
	 * @param montoCreditosDolares the montoCreditosDolares to set
	 */
	public void setMontoCreditosDolares(double montoCreditosDolares) {
		this.montoCreditosDolares = montoCreditosDolares;
	}

	/**
	 * @return the cantidadContadoDolares
	 */
	public Integer getCantidadContadoDolares() {
		return cantidadContadoDolares;
	}

	/**
	 * @param cantidadContadoDolares the cantidadContadoDolares to set
	 */
	public void setCantidadContadoDolares(Integer cantidadContadoDolares) {
		this.cantidadContadoDolares = cantidadContadoDolares;
	}

	/**
	 * @return the montoContadoDolares
	 */
	public double getMontoContadoDolares() {
		return montoContadoDolares;
	}

	/**
	 * @param montoContadoDolares the montoContadoDolares to set
	 */
	public void setMontoContadoDolares(double montoContadoDolares) {
		this.montoContadoDolares = montoContadoDolares;
	}

	/**
	 * @return the montoIngresadoDolares
	 */
	public Double getMontoIngresadoDolares() {
		return montoIngresadoDolares;
	}

	/**
	 * @param montoIngresadoDolares the montoIngresadoDolares to set
	 */
	public void setMontoIngresadoDolares(Double montoIngresadoDolares) {
		this.montoIngresadoDolares = montoIngresadoDolares;
	}

	/**
	 * @return the importe
	 */
	public Double getImporte() {
		return importe;
	}

	/**
	 * @param importe the importe to set
	 */
	public void setImporte(Double importe) {
		this.importe = importe;
	}

	/**
	 * @return the comprobante
	 */
	public String getComprobante() {
		return comprobante;
	}

	/**
	 * @param comprobante the comprobante to set
	 */
	public void setComprobante(String comprobante) {
		this.comprobante = comprobante;
	}

	/**
	 * @return the liquidacionCarga
	 */
	public Liquidacion getLiquidacionCarga() {
		return liquidacionCarga;
	}

	/**
	 * @param liquidacionCarga the liquidacionCarga to set
	 */
	public void setLiquidacionCarga(Liquidacion liquidacionCarga) {
		this.liquidacionCarga = liquidacionCarga;
	}

	/**
	 * @return the cantidadTransferencias
	 */
	public Integer getCantidadTransferencias() {
		return cantidadTransferencias;
	}

	/**
	 * @param cantidadTransferencias the cantidadTransferencias to set
	 */
	public void setCantidadTransferencias(Integer cantidadTransferencias) {
		this.cantidadTransferencias = cantidadTransferencias;
	}

	/**
	 * @return the cantidadYape
	 */
	public Integer getCantidadYape() {
		return cantidadYape;
	}

	/**
	 * @param cantidadYape the cantidadYape to set
	 */
	public void setCantidadYape(Integer cantidadYape) {
		this.cantidadYape = cantidadYape;
	}

	/**
	 * @return the montoYape
	 */
	public double getMontoYape() {
		return montoYape;
	}

	/**
	 * @param montoYape the montoYape to set
	 */
	public void setMontoYape(double montoYape) {
		this.montoYape = montoYape;
	}

	/**
	 * @return the montoTransferencias
	 */
	public double getMontoTransferencias() {
		return montoTransferencias;
	}

	/**
	 * @param montoTransferencias the montoTransferencias to set
	 */
	public void setMontoTransferencias(double montoTransferencias) {
		this.montoTransferencias = montoTransferencias;
	}

}
