/**
 * Proyecto		: SISVYR
 * Sistema		: Sistema de Ventas y Reservas
 * Descripción	: Permite la comunicación entre la capa de la vista el la capa del negocio
 * 				  Responsable de cargar e inspeccionar los benas definidos en el ApplicationContext.xml.
 * Autor		: José Sullo Avalos
 * Fecha		: 28/09/2012
 */
package com.cystesoft.vyrbus.service.locator;


import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.zkoss.zkplus.spring.SpringUtil;

import com.cystesoft.vyrbus.service.business.*;

public class ServiceLocator {

	/**
	 * Constructor de la clase.
	 */
	private ServiceLocator() {
		super();
	}
	
	public static Session getHibernateSession(){
		return ((SessionFactory) SpringUtil.getBean("sessionFactory", SessionFactory.class)).getCurrentSession();
	}
	
	/**
	 * Obtiene el bean de Agencia, para realizar la comunicacion con la capa del modelo.
	 * @return <b>AgenciaManager</b>
	 */
	public static AgenciaManager getAgenciaManager(){
		return (AgenciaManager) SpringUtil.getBean("agenciaManager", AgenciaManager.class);
	}
	
	/**
	 * Obtiene el bean de Bus, para realizar la comunicacion con la capa del modelo.
	 * @return <b>BusManager</b>
	 */
	public static BusManager getBusManager(){
		return (BusManager) SpringUtil.getBean("busManager", BusManager.class);
	}
	
	/**
	 * Obtiene el bean de CanalVenta, para realizar la comunicacion con la capa del modelo.
	 * @return <b>CanalVentaManager</b>
	 */
	public static CanalVentaManager getCanalVentaManager(){
		return (CanalVentaManager) SpringUtil.getBean("canalVentaManager", CanalVentaManager.class);
	}
	
	/**
	 * Obtiene el bean de Cliente, para realizar la comunicacion con la capa del modelo.
	 * @return <b>ClienteManager</b>
	 */
	public static ClienteManager getClienteManager(){
		return (ClienteManager) SpringUtil.getBean("clienteManager", ClienteManager.class);
	}
	
	/**
	 * Obtiene el bean de Concesionario, para realizar la comunicacion con la capa del modelo.
	 * @return <b>ConcesionarioManager</b>
	 */
	public static ConcesionarioManager getConcesionarioManager(){
		return (ConcesionarioManager) SpringUtil.getBean("concesionarioManager", ConcesionarioManager.class);
	}
	
	/**
	 * Obtiene el bean de CondicionVenta, para realizar la comunicacion con la capa del modelo.
	 * @return <b>CondicionVentaManager</b>
	 */
	public static TipoMovimientoManager getTipoMovimientoManager(){
		return (TipoMovimientoManager) SpringUtil.getBean("tipoMovimientoManager", TipoMovimientoManager.class);
	}
	
	/**
	 * Obtiene el bean de ControlEspecieValorada, para realizar la comunicacion con la capa del modelo.
	 * @return <b>ControlEspecieValoradaManager</b>
	 */
	public static ControlEspecieValoradaManager getControlEspecieValoradaManager(){
		return (ControlEspecieValoradaManager) SpringUtil.getBean("controlEspecieValoradaManager", ControlEspecieValoradaManager.class);
	}
	
	/**
	 * Obtiene el bean de DescuentoRecargo, para realizar la comunicacion con la capa del modelo.
	 * @return <b>DescuentoRecargoManager</b>
	 */
	public static DescuentoRecargoManager getDescuentoRecargoManager(){
		return (DescuentoRecargoManager) SpringUtil.getBean("descuentoRecargoManager", DescuentoRecargoManager.class);
	}
	
	/**
	 * Obtiene el bean de DocumentoBus, para realizar la comunicacion con la capa del modelo.
	 * @return <b>DocumentoBusManager</b>
	 */
	public static DocumentoBusManager getDocumentoBusManager(){
		return (DocumentoBusManager) SpringUtil.getBean("documentoBusManager", DocumentoBusManager.class);
	}
	
	/**
	 * Obtiene el bean de EspecieValorada, para realizar la comunicacion con la capa del modelo.
	 * @return <b>EspecieValoradaManager</b>
	 */
	public static EspecieValoradaManager getEspecieValoradaManager(){
		return (EspecieValoradaManager) SpringUtil.getBean("especieValoradaManager", EspecieValoradaManager.class);
	}
	
	/**
	 * Obtiene el bean de EstadoBus, para realizar la comunicacion con la capa del modelo.
	 * @return <b>EstadoBusManager</b>
	 */
	public static EstadoBusManager getEstadoBusManager(){
		return (EstadoBusManager) SpringUtil.getBean("estadoBusManager", EstadoBusManager.class);
	}
	
	/**
	 * Obtiene el bean de EstadoCivil, para realizar la comunicacion con la capa del modelo.
	 * @return <b>EstadoCivilManager</b>
	 */
	public static EstadoCivilManager getEstadoCivilManager(){
		return (EstadoCivilManager) SpringUtil.getBean("estadoCivilManager", EstadoCivilManager.class);
	}
	
	/**
	 * Obtiene el bean de EstadoDocumentoBus, para realizar la comunicacion con la capa del modelo.
	 * @return <b>EstadoDocumentoBusManager</b>
	 */
	public static EstadoDocumentoBusManager getEstadoDocumentoBusManager(){
		return (EstadoDocumentoBusManager) SpringUtil.getBean("estadoDocumentoBusManager", EstadoDocumentoBusManager.class);
	}
	
	/**
	 * Obtiene el bean de FormaPago, para realizar la comunicacion con la capa del modelo.
	 * @return <b>FormaPagoManager</b>
	 */
	public static FormaPagoManager getFormaPagoManager(){
		return (FormaPagoManager) SpringUtil.getBean("formaPagoManager", FormaPagoManager.class);
	}
	
	/**
	 * Obtiene el bean de GrupoMantenimiento, para realizar la comunicacion con la capa del modelo.
	 * @return <b>GrupoMantenimientoManager</b>
	 */
	public static GrupoMantenimientoManager getGrupoMantenimientoManager(){
		return (GrupoMantenimientoManager) SpringUtil.getBean("grupoMantenimientoManager", GrupoMantenimientoManager.class);
	}
	/**
	 * Obtiene el bean de ItinerarioAgenciaPartida, para realizar la comunicacion con la capa del modelo.
	 * @return <b>ItinerarioAgenciaPartidaManager</b>
	 */
	public static ItinerarioAgenciaPartidaManager getItinerarioAgenciaPartidaManager(){
		return (ItinerarioAgenciaPartidaManager) SpringUtil.getBean("itinerarioAgenciaPartidaManager", ItinerarioAgenciaPartidaManager.class);
	}
	
	/**
	 * Obtiene el bean de ItinerarioAgenciaLlegada, para realizar la comunicacion con la capa del modelo.
	 * @return <b>ItinerarioAgenciaLlegadaManager</b>
	 */
	public static ItinerarioAgenciaLlegadaManager getItinerarioAgenciaLlegadaManager(){
		return (ItinerarioAgenciaLlegadaManager) SpringUtil.getBean("itinerarioAgenciaLlegadaManager", ItinerarioAgenciaLlegadaManager.class);
	}
	
	
	/**
	 * Obtiene el bean de Itinerario, para realizar la comunicacion con la capa del modelo.
	 * @return <b>ItinerarioManager</b>
	 */
	public static ItinerarioManager getItinerarioManager(){
		return (ItinerarioManager) SpringUtil.getBean("itinerarioManager", ItinerarioManager.class);
	}
	
	/**
	 * Obtiene el bean de DetalleItinerario, para realizar la comunicacion con la capa del modelo.
	 * @return <b>DetalleItinerarioManager</b>
	 */
	public static DetalleItinerarioManager getDetalleItinerarioManager(){
		return (DetalleItinerarioManager) SpringUtil.getBean("detalleItinerarioManager", DetalleItinerarioManager.class);
	}
	
	/**
	 * Obtiene el bean de Localidad, para realizar la comunicacion con la capa del modelo.
	 * @return <b>LocalidadManager</b>
	 */
	public static LocalidadManager getLocalidadManager(){
		return (LocalidadManager) SpringUtil.getBean("localidadManager", LocalidadManager.class);
	}
	
	/**
	 * Obtiene el bean de MapaBus, para realizar la comunicacion con la capa del modelo.
	 * @return <b>MapaBusManager</b>
	 */
	public static MapaBusManager getMapaBusManager(){
		return (MapaBusManager) SpringUtil.getBean("mapaBusManager", MapaBusManager.class);
	}
	
	/**
	 * Obtiene el bean de Nacionalidad, para realizar la comunicacion con la capa del modelo.
	 * @return <b>NacionalidadManager</b>
	 */
	public static NacionalidadManager getNacionalidadManager(){
		return (NacionalidadManager) SpringUtil.getBean("nacionalidadManager", NacionalidadManager.class);
	}
	
	/**
	 * Obtiene el bean de NumeroFlota, para realizar la comunicacion con la capa del modelo.
	 * @return <b>NumeroFlotaManager</b>
	 */
	public static NumeroFlotaManager getNumeroFlotaManager(){
		return (NumeroFlotaManager) SpringUtil.getBean("numeroFlotaManager", NumeroFlotaManager.class);
	}
	
	/**
	 * Obtiene el bean de ObjetoBus, para realizar la comunicacion con la capa del modelo.
	 * @return <b>ObjetoBusManager</b>
	 */
	public static ObjetoBusManager getObjetoBusManager(){
		return (ObjetoBusManager) SpringUtil.getBean("objetoBusManager", ObjetoBusManager.class);
	}
	
	/**
	 * Obtiene el bean de OperadorTarjetaCredito, para realizar la comunicacion con la capa del modelo.
	 * @return <b>OperadorTarjetaCreditoManager</b>
	 */
	public static OperadorTarjetaCreditoManager getOperadorTarjetaCreditoManager(){
		return (OperadorTarjetaCreditoManager) SpringUtil.getBean("operadorTarjetaCreditoManager", OperadorTarjetaCreditoManager.class);
	}
	
	/**
	 * Obtiene el bean de PasajeroFrecuente, para realizar la comunicacion con la capa del modelo.
	 * @return <b>PasajeroFrecuenteManager</b>
	 */
	public static PasajeroFrecuenteManager getPasajeroFrecuenteManager(){
		return (PasajeroFrecuenteManager) SpringUtil.getBean("pasajeroFrecuenteManager", PasajeroFrecuenteManager.class);
	}
	
	/**
	 * Obtiene el bean de Pasajero, para realizar la comunicacion con la capa del modelo.
	 * @return <b>PasajeroManager</b>
	 */
	public static PasajeroManager getPasajeroManager(){
		return (PasajeroManager) SpringUtil.getBean("pasajeroManager", PasajeroManager.class);
	}
	
	/**
	 * Obtiene el bean de Personal, para realizar la comunicacion con la capa del modelo.
	 * @return <b>PersonalManager</b>
	 */
	public static PersonalManager getPersonalManager(){
		return (PersonalManager) SpringUtil.getBean("personalManager", PersonalManager.class);
	}
	
	/**
	 * Obtiene el bean de PreferenciaAlimentaria, para realizar la comunicacion con la capa del modelo.
	 * @return <b>PreferenciaAlimentariaManager</b>
	 */
	public static PreferenciaAlimentariaManager getPreferenciaAlimentariaManager(){
		return (PreferenciaAlimentariaManager) SpringUtil.getBean("preferenciaAlimentariaManager", PreferenciaAlimentariaManager.class);
	}
	
	/**
	 * Obtiene el bean de Ruta, para realizar la comunicacion con la capa del modelo.
	 * @return <b>RutaManager</b>
	 */
	public static RutaManager getRutaManager(){
		return (RutaManager) SpringUtil.getBean("rutaManager", RutaManager.class);
	}
	
	/**
	 * Obtiene el bean de Servicio, para realizar la comunicacion con la capa del modelo.
	 * @return <b>Manager</b>
	 */
	public static ServicioManager getServicioManager(){
		return (ServicioManager) SpringUtil.getBean("servicioManager", ServicioManager.class);
	}
	
	/**
	 * Obtiene el bean de Sexo, para realizar la comunicacion con la capa del modelo.
	 * @return <b>SexoManager</b>
	 */
	public static SexoManager getSexoManager(){
		return (SexoManager) SpringUtil.getBean("sexoManager", SexoManager.class);
	}
	
	/**
	 * Obtiene el bean de TarjetaCredito, para realizar la comunicacion con la capa del modelo.
	 * @return <b>TarjetaCreditoManager</b>
	 */
	public static TarjetaCreditoManager getTarjetaCreditoManager(){
		return (TarjetaCreditoManager) SpringUtil.getBean("tarjetaCreditoManager", TarjetaCreditoManager.class);
	}
	
	/**
	 * Obtiene el bean de TipoAgencia, para realizar la comunicacion con la capa del modelo.
	 * @return <b>TipoAgenciaManager</b>
	 */
	public static TipoAgenciaManager getTipoAgenciaManager(){
		return (TipoAgenciaManager) SpringUtil.getBean("tipoAgenciaManager", TipoAgenciaManager.class);
	}
	
	/**
	 * Obtiene el bean de TipoComprobante, para realizar la comunicacion con la capa del modelo.
	 * @return <b>TipoComprobanteManager</b>
	 */
	public static TipoComprobanteManager getTipoComprobanteManager(){
		return (TipoComprobanteManager) SpringUtil.getBean("tipoComprobanteManager", TipoComprobanteManager.class);
	}
	
	/**
	 * Obtiene el bean de TipoDocumento, para realizar la comunicacion con la capa del modelo.
	 * @return <b>TipoDocumentoManager</b>
	 */
	public static TipoDocumentoManager getTipoDocumentoManager(){
		return (TipoDocumentoManager) SpringUtil.getBean("tipoDocumentoManager", TipoDocumentoManager.class);
	}
	
	/**
	 * Obtiene el bean de TipoFlota, para realizar la comunicacion con la capa del modelo.
	 * @return <b>TipoFlotaManager</b>
	 */
	public static TipoFlotaManager getTipoFlotaManager(){
		return (TipoFlotaManager) SpringUtil.getBean("tipoFlotaManager", TipoFlotaManager.class);
	}
	
	/**
	 * Obtiene el bean de TipoFormaPago, para realizar la comunicacion con la capa del modelo.
	 * @return <b>TipoFormaPagoManager</b>
	 */
	public static TipoFormaPagoManager getTipoFormaPagoManager(){
		return (TipoFormaPagoManager) SpringUtil.getBean("tipoFormaPagoManager", TipoFormaPagoManager.class);
	}
	
	/**
	 * Obtiene el bean de TipoItinerario, para realizar la comunicacion con la capa del modelo.
	 * @return <b>TipoItinerarioManager</b>
	 */
	public static TipoItinerarioManager getTipoItinerarioManager(){
		return (TipoItinerarioManager) SpringUtil.getBean("tipoItinerarioManager", TipoItinerarioManager.class);
	}
	
	/**
	 * Obtiene el bean de TipoPersonal, para realizar la comunicacion con la capa del modelo.
	 * @return <b>TipoPersonalManager</b>
	 */
	public static TipoPersonalManager getTipoPersonalManager(){
		return (TipoPersonalManager) SpringUtil.getBean("tipoPersonalManager", TipoPersonalManager.class);
	}
	
	/**
	 * Obtiene el bean de TipoVia, para realizar la comunicacion con la capa del modelo.
	 * @return <b>TipoViaManager</b>
	 */
	public static TipoViaManager getTipoViaManager(){
		return (TipoViaManager) SpringUtil.getBean("tipoViaManager", TipoViaManager.class);
	}
	
	/**
	 * Obtiene el bean de TipoZona, para realizar la comunicacion con la capa del modelo.
	 * @return <b>TipoZonaManager</b>
	 */
	public static TipoZonaManager getTipoZonaManager(){
		return (TipoZonaManager) SpringUtil.getBean("tipoZonaManager", TipoZonaManager.class);
	}
	
	/**
	 * Obtiene el bean de Ubigeo, para realizar la comunicacion con la capa del modelo.
	 * @return <b>UbigeoManager</b>
	 */
	public static UbigeoManager getUbigeoManager(){
		return (UbigeoManager) SpringUtil.getBean("ubigeoManager", UbigeoManager.class);
	}
	
	/**
	 * Obtiene el bean de Usuario, para realizar la comunicacion con la capa del modelo.
	 * @return <b>UsuarioManager</b>
	 */
	public static UsuarioManager getUsuarioManager(){
		return (UsuarioManager) SpringUtil.getBean("usuarioManager", UsuarioManager.class);
	}
	
	/**
	 * Obtiene el bean de VentaPasajes, para realizar la comunicacion con la capa del modelo.
	 * @return <b>VentaPasajesManager</b>
	 */
	public static VentaPasajesManager getVentaPasajesManager(){
		return (VentaPasajesManager) SpringUtil.getBean("ventaPasajesManager", VentaPasajesManager.class);
	}
	
	/**
	 * Obtiene el bean de VentaPasajes, para realizar la comunicacion con la capa del modelo.
	 * @return <b>VentaPasajesManager</b>
	 */
	public static TmpOcupacionAsientosManager getTmpOcupacionAsientosManager(){
		return (TmpOcupacionAsientosManager) SpringUtil.getBean("tmpOcupacionAsientosManager", TmpOcupacionAsientosManager.class);
	}
	
	/**
	 * Obtiene el bean de ProgramacionServicio, para realizar la comunicacion con la capa del modelo.
	 * @return<b>ProgramacionServicioManager</b>
	 */
	public static ProgramacionServicioManager getProgramacionServiciosManager(){
		return (ProgramacionServicioManager) SpringUtil.getBean("programacionServicioManager", ProgramacionServicioManager.class);
	}
	
	
	/**
	 * Obtiene el bean de UsuarioHardware, para realizar la comunicación con la capa modelo.
	 * @return<b>UsuarioHarwareManager</b>
	 */
	public static UsuarioHardwareManager getUsuarioHardwareManager(){
		return (UsuarioHardwareManager) SpringUtil.getBean("usuarioHardwareManager", UsuarioHardwareManager.class);
	}
	
	/**
	 *  Obtiene el bean de UsuarioHardware, para realizar la comunicación con la capa modelo.
	 * @return <b>TipoGastoManager</b>
	 */
	public static TipoGastoManager getTipoGastoManager(){
		return (TipoGastoManager) SpringUtil.getBean("tipoGastoManager", TipoGastoManager.class);
	}
	
	/**
	 *  Obtiene el bean de UsuarioHardware, para realizar la comunicación con la capa modelo.
	 * @return <b>CortesiaManager</b>
	 */
	public static CortesiaManager getCortesiaManager(){
		return (CortesiaManager) SpringUtil.getBean("cortesiaManager", CortesiaManager.class);
	}
	
	/**
	 *  Obtiene el bean de Motivocortecia, para realizar la comunicación con la capa modelo.
	 * @return <b>MotivoCortesiaManager</b>
	 */
	public static MotivoCortesiaManager getMotivoCortesiaManager(){
		return (MotivoCortesiaManager) SpringUtil.getBean("motivoCortesiaManager", MotivoCortesiaManager.class);
	}
	
	/**
	 *  Obtiene el bean de Manifiesto, para realizar la comunicación con la capa modelo.
	 * @return<b>ManifiestoManager</b>
	 */
	public static ManifiestoManager getManifiestoManager(){
		return (ManifiestoManager) SpringUtil.getBean("manifiestoManager", ManifiestoManager.class); 
	}
	
	/**
	 * Obtiene el bean de Rol, para realizar la comunicación con la capa modelo.
	 * @return<b>RolManager</b>
	 */
	public static RolManager getRolManager(){
		return (RolManager) SpringUtil.getBean("rolManager",RolManager.class);
	}
	
	/**
	 * Obtiene el bean de UsuarioRol, para realizar la comunicación con la capa modelo.
	 * @return <b>UsuarioRolManager</b>
	 */
	public static UsuarioRolManager getUsuarioRolManager(){
		return (UsuarioRolManager) SpringUtil.getBean("usuarioRolManager",UsuarioRolManager.class);
	}
	/**
	 * Obtiene el bean de OpcionMenu, para realizar la comunicación con la capa modelo.
	 * @return <b>OpcionMenu</b>
	 */
	public static OpcionMenuManager getOpcionMenuManager(){
		return (OpcionMenuManager)  SpringUtil.getBean("opcionMenuManager", OpcionMenuManager.class);
	}
	
	/**
	 * Obtiene el bean de RolOpcionMenuManager, para realizar la comunicación con la capa modelo.
	 * @return <b>RolOpcionMenuManager</b>
	 */
	public static RolOpcionMenuManager getRolOpcionMenuManager(){
		return (RolOpcionMenuManager) SpringUtil.getBean("rolOpcionMenuManager", RolOpcionMenuManager.class);
	}
	
	/**
	 * Obtiene el bean de GastoManager, para realizar la comunicación con la capa modelo.
	 * @return <b>GastoManager</b>
	 */
	public static GastoManager getGastoManager(){
		return (GastoManager) SpringUtil.getBean("gastoManager", GastoManager.class);
	}
	
	
	/**
	 * Obtiene el bean de LiquidacionBusManager, para realizar la comunicación con la capa modelo.
	 * @return <b>LiquidacionBusManager</b>
	 */
	public static LiquidacionBusManager getLiquidacionBusManager(){
		return (LiquidacionBusManager) SpringUtil.getBean("liquidacionBusManager", LiquidacionBusManager.class);
	}
	
	/**
	 * Obtiene el bean de GastoBusManager, para realizar la comunicación con la capa modelo.
	 * @return <b>GastoBusManager</b>
	 */
	public static GastoBusManager getGastoBusManager(){
		return (GastoBusManager) SpringUtil.getBean("gastoBusManager",GastoBusManager.class);
	}
	
	/**
	 * Obtiene el bean de LiquidacionManager, para realizar la comunicación con la capa modelo.
	 * @return <b>LiquidacionManager</b>
	 */
	public static LiquidacionManager getLiquidacionManager(){
		return (LiquidacionManager) SpringUtil.getBean("liquidacionManager",LiquidacionManager.class);
	}
	
	/**
	 * Obtiene el bean de DetalleLiquidacionManager, para realizar la comunicación con la capa modelo.
	 * @return <b>DetalleLiquidacionManager</b>
	 */
	public static DetalleLiquidacionManager getDetalleLiquidacionManager(){
		return (DetalleLiquidacionManager) SpringUtil.getBean("detalleLiquidacionManager",DetalleLiquidacionManager.class);
	}
	
	/**
	 * Obtiene el bean de LiquidacionOficinaManager, para realizar la comunicación con la capa modelo.
	 * @return <b>LiquidacionOficinaManager</b>
	 */
	public static LiquidacionOficinaManager getLiquidacionOficinaManager(){
		return (LiquidacionOficinaManager) SpringUtil.getBean("liquidacionOficinaManager", LiquidacionOficinaManager.class);
	}
	
	/**
	 * Obtiene el bean de TransbordoManager, para realizar la comunicación con la capa modelo.
	 * @return <b>TransbordoManager</b>
	 */
	public static TransbordoManager getTransbordoManajer(){ 
		return (TransbordoManager) SpringUtil.getBean("transbordoManager", TransbordoManager.class);
	}
	
	/**
	 * Obtiene el bean de ReportesManager, para realizar la comunicación con la capa modelo.
	 * @return <b>ReportesManager</b>
	 */
	public static ReportesManager getReportesManager(){
		return(ReportesManager) SpringUtil.getBean("reportesManager",ReportesManager.class);
	}
	
	/**
	 * Obtiene el bean de autorizadorCortesia, para realizar la comunicación con la capa modelo.
	 * @return <b>autorizadorCortesia</b>
	 */
	public static AutorizadorCortesiaManager getAutorizadorCortesiaManager(){
		return(AutorizadorCortesiaManager) SpringUtil.getBean("autorizadorCortesiaManager",(AutorizadorCortesiaManager.class));
	}
	
	/**
	 * Obtiene el bean de SolicitudCarteraManajer, para realizar la comunicación con la capa modelo.
	 * @return <b>SolicitudCarteraManajer</b>
	 */
	public static SolicitudCarteraManager getSolicitudCarteraManager(){
		return(SolicitudCarteraManager) SpringUtil.getBean("solicitudCarteraManager",(SolicitudCarteraManager.class));
	}
	
	/**
	 * Obtiene el bean de SolicitudClienteCreditoManger, para realizar la comunicación con la capa modelo.
	 * @return <b>SolicitudClienteCreditoManger</b>
	 */
	public static SolicitudClienteCreditoManager getSolicitudClienteCreditoManager(){
		return(SolicitudClienteCreditoManager) SpringUtil.getBean("solicitudClienteCreditoManager",(SolicitudClienteCreditoManager.class));
	}
	
	/**
	 * Obtiene el bean de TipoCobranzaManager, para realizar la comunicación con la capa modelo.
	 * @return <b>TipoCobranzaManager</b>
	 */
	public static TipoCobranzaManager getTipoCobranzaManager(){
		return(TipoCobranzaManager) SpringUtil.getBean("tipoCobranzaManager",(TipoCobranzaManager.class));
	}
	
	/**
	 * Obtiene el bean de CarteraClienteManager, para realizar la comunicación con la capa modelo.
	 * @return <b>CarteraClienteManager</b>
	 */
	public static CarteraClienteManager getCarteraClienteManager(){
		return(CarteraClienteManager) SpringUtil.getBean("carteraClienteManager",(CarteraClienteManager.class));
	}
	
	/**
	 * Obtiene el bean de UsuarioAprobadorManager, para realizar la comunicación con la capa modelo.
	 * @return <b>UsuarioAprobadorManager</b>
	 */
	public static UsuarioAprobadorManager getUsuarioAprobadorManager(){
		return(UsuarioAprobadorManager) SpringUtil.getBean("usuarioAprobadorManager",(UsuarioAprobadorManager.class));
	}
	
	/**
	 * Obtiene el bean de LineaContadoClienteManager, para realizar la comunicación con la capa modelo.
	 * @return <b>LineaContadoClienteManager</b>
	 */
	public static LineaContadoClienteManager getLineaContadoClienteManager(){
		return(LineaContadoClienteManager) SpringUtil.getBean("lineaContadoClienteManager",(LineaContadoClienteManager.class));
	}
	/**
	 * Obtiene el bean de LineaCreditoClienteManager, para realizar la comunicación con la capa modelo.
	 * @return <b>LineaCreditoClienteManager</b>
	 */
	public static LineaCreditoClienteManager getLineaCreditoClienteManager(){
		return(LineaCreditoClienteManager) SpringUtil.getBean("lineaCreditoClienteManager",(LineaCreditoClienteManager.class));
	}
	
	/**
	 * Obtiene el bean de MotivoTemporadaAltaManager, para realizar la comunicación con la capa modelo.
	 * @return <b>MotivoTemporadaAltaManager</b>
	 */
	public static MotivoTemporadaAltaManager getMotivoTemporadaAltaManager(){
		return(MotivoTemporadaAltaManager) SpringUtil.getBean("motivoTemporadaAltaManager",(MotivoTemporadaAltaManager.class));
	}
		
	/**
	 * Obtiene el bean de TemporadaAltaManager, para realizar la comunicación con la capa modelo.
	 * @return <b>TemporadaAltaManager</b>
	 */
	public static TemporadaAltaManager getTemporadaAltaManager(){
		return(TemporadaAltaManager) SpringUtil.getBean("temporadaAltaManager",(TemporadaAltaManager.class));
	}
	
	public static PromocionManager getPromocionManager(){
		return (PromocionManager) SpringUtil.getBean("promocionManager", PromocionManager.class);
	}
	
	/**
	 * Obtiene el bean de PuntosPasajeroFrecuenteManager, para realizar la comunicación con la capa modelo
	 * @return <b>PuntosPasajeroFrecuenteManager</b>
	 */
	public static PuntosPasajeroFrecuenteManager getPuntosPasajeroFrecuenteManager(){
		return (PuntosPasajeroFrecuenteManager) SpringUtil.getBean("puntosPasajeroFrecuenteManager", PuntosPasajeroFrecuenteManager.class);
	}
	
	/**
	 * Obtiene el bean de ParametrosManager, para realizar la comunicación con la capa modelo
	 * @return <b>ParametrosManager</b>
	 */
	public static ParametrosManager getParametrosManager(){
		return (ParametrosManager) SpringUtil.getBean("parametrosManager",ParametrosManager.class);
	}
	
	/**
	 * Obtiene el bean de HistoricoMembresiaManager, para realizar la comunicación con la capa modelo
	 * @return <b>HistoricoMembresiaManager</b>
	 */
	public static HistoricoMembresiaManager getHistoricoMembresiaManager(){
		return (HistoricoMembresiaManager) SpringUtil.getBean("historicoMembresiaManager",HistoricoMembresiaManager.class);
	}
	/**
	 * Obtiene el bean de OcupacionAsientosDesbloqueadosManager, para realizar la comunicación con la capa modelo
	 * @return <b>OcupacionAsientosDesbloqueadosManager</b>
	 */
	public static OcupacionAsientosDesbloqueadosManager getOcupacionAsientosDesbloqueadosManager(){
		return (OcupacionAsientosDesbloqueadosManager) SpringUtil.getBean("ocupacionAsientosDesbloqueadosManager",OcupacionAsientosDesbloqueadosManager.class);
	}
	/**
	 * Obtiene el bean de ReniecManager, para realizar la comunicación con la capa modelo
	 * @return <b>ReniecManager</b>
	 */
	public static ReniecManager getReniecManager(){
		return (ReniecManager) SpringUtil.getBean("reniecManager",ReniecManager.class);
	}
	/**
	 * Obtiene el bean de HistoricoControlEspecieValoradaManager, para realizar la comunicación con la capa modelo
	 * @return <b>HistoricoControlEspecieValoradaManager</b>
	 */
	public static HistoricoControlEspecieValoradaManager getHistoricoControlEspecieValoradaManager(){
		return (HistoricoControlEspecieValoradaManager) SpringUtil.getBean("historicoControlEspecieValoradaManager",HistoricoControlEspecieValoradaManager.class);
	}
	
	/**
	 * Obtiene el bean de CentroCostoManager, para realizar la comunicación con la capa modelo
	 * @return <b>CentroCostoManager</b>
	 */
	public static CentroCostoManager getCentroCostoManager(){
		return (CentroCostoManager) SpringUtil.getBean("centroCostoManager", CentroCostoManager.class);
	}
	
	/**
	 * Obtiene el bean de TerminosVentaManager, para realizar la comunicación con la capa modelo
	 * @return <b>TerminosVentaManager</b>
	 */
	public static TerminosVentaManager getTerminosVentaManager(){
		return (TerminosVentaManager) SpringUtil.getBean("terminosVentaManager", TerminosVentaManager.class);
	}
	
	/**
	 * Obtiene el bean de DetalleManifiestoManager, para realizar la comunicación con la capa modelo
	 * @return <b>DetalleManifiestoManager</b>
	 */
	public static DetalleManifiestoManager getDetalleManifiestoManager(){
		return (DetalleManifiestoManager) SpringUtil.getBean("detalleManifiestoManager",DetalleManifiestoManager.class);
	}
	
	/**
	 * Obtiene el bean de SerieEspecieValoradaManager, para realizar la comunicación con la capa modelo
	 * @return <b>SerieEspecieValoradaManager</b>
	 */
	public static SerieEspecieValoradaManager getSerieEspecieValoradaManager(){
		return (SerieEspecieValoradaManager) SpringUtil.getBean("serieEspecieValoradaManager",SerieEspecieValoradaManager.class);
	}
	
	public static AutorizadorMotivoCortesiaManager getAutorizadorMotivoCortesiaManager(){
		return (AutorizadorMotivoCortesiaManager) SpringUtil.getBean("autorizadorMotivoCortesiaManager",AutorizadorMotivoCortesiaManager.class);
	}
	
	/**
	 * Obtiene el bean de TarifaFechaAbiertaManager, para realizar la comunicación con la capa modelo
	 * @return <b>TarifaFechaAbiertaManager</b>
	 */
	public static TarifaFechaAbiertaManager getTarifaFechaAbierta(){
		return (TarifaFechaAbiertaManager) SpringUtil.getBean("tarifaFechaAbiertaManager",TarifaFechaAbiertaManager.class);
	}
		
	/**
	 * Obtiene el bean de VentaSeguroManager, para realizar la comunicación con la capa modelo
	 * @return <b>VentaSeguroManager</b>
	 */
	public static VentaSeguroManager getVentaSeguroManager(){
		return (VentaSeguroManager) SpringUtil.getBean("ventaSeguroManager",VentaSeguroManager.class);
	}
	
	/**
	 * Obtiene el bean de MTCRutaManager, para realizar la comunicación con la capa modelo
	 * @return <b>MTCRutaManager</b>
	 */
	public static MTCRutaManager getMTCRutaManager(){
		return (MTCRutaManager) SpringUtil.getBean("mtcRutaManager",MTCRutaManager.class);
	}
	
	/**
	 * Obtiene el bean de MTCDireccionTerminalManager, para realizar la comunicación con la capa modelo
	 * @return <b>MTCDireccionTerminalManager</b>
	 */
	public static MTCDireccionTerminalManager getMTCDireccionTerminalManager(){
		return (MTCDireccionTerminalManager) SpringUtil.getBean("mtcDireccionTerminalManager",MTCDireccionTerminalManager.class);
	}
	
	/**
	 * Obtiene el bean de MTCDetalleRutaManager, para realizar la comunicación con la capa modelo
	 * @return <b>MTCDetalleRutaManager</b>
	 */
	public static MTCDetalleRutaManager getMTCDetalleRutaManager(){
		return (MTCDetalleRutaManager) SpringUtil.getBean("mtcDetalleRutaManager",MTCDetalleRutaManager.class);
	}
	
	/**
	 * Obtiene el bean de HREManager, para realizar la comunicación con la capa modelo
	 * @return <b>HREManager</b>
	 */
	public static HREManager getHREManager(){
		return (HREManager) SpringUtil.getBean("hreManager",HREManager.class);
	}
	
	/**
	 * Obtiene el bean de DetalleFlotaHREManager, para realizar la comunicación con la capa modelo
	 * @return <b>DetalleFlotaHREManager</b>
	 */
	public static DetalleFlotaHREManager getDetalleFlotaHREManager(){
		return (DetalleFlotaHREManager) SpringUtil.getBean("detalleFlotaHREManager",DetalleFlotaHREManager.class);
	}
	/**
	 * Obtiene el bean de TitanManager, para realizar la comunicación con la capa modelo
	 * @return <b>TitanManager</b>
	 */
	public static TitanManager getTitanManager(){
		return (TitanManager) SpringUtil.getBean("titanManager",TitanManager.class);
	}
	/**
	 * Obtiene el bean de VentaTramoManager, para realizar la comunicación con la capa modelo
	 * @return <b>VentaTramoManager</b>
	 */
	public static VentaTramoManager getVentaTramoManager(){
		return (VentaTramoManager) SpringUtil.getBean("ventaTramoManager",VentaTramoManager.class);
	}
	/**
	 * Obtiene el bean de RangoSobregiroManager, para realizar la comunicación con la capa modelo
	 * @return <b>RangoSobregiroManager</b>
	 */
	public static RangoSobregiroManager getRangoSobregiroManager(){
		return (RangoSobregiroManager) SpringUtil.getBean("rangoSobregiroManager",RangoSobregiroManager.class);
	}
	/**
	 * Obtiene el bean de ConfiguracionImpresoraManager, para realizar la comunicación con la capa modelo
	 * @return <b>ConfiguracionImpresoraManager</b>
	 */
	public static ConfiguracionImpresoraManager getConfiguracionImpresoraManager(){
		return (ConfiguracionImpresoraManager) SpringUtil.getBean("configuracionImpresoraManager",ConfiguracionImpresoraManager.class);
	}
	/**
	 * Obtiene el bean de DestinatariosEmailsManager, para realizar la comunicación con la capa modelo
	 * @return <b>DestinatariosEmailsManager</b>
	 */
	public static DestinatariosEmailsManager getDestinatariosEmailsManager(){
		return (DestinatariosEmailsManager) SpringUtil.getBean("destinatariosEmailsDAO",DestinatariosEmailsManager.class);
	}
	/**
	 * Obtiene el bean de TipoCentroCostoManager, para realizar la comunicación con la capa modelo
	 * @return <b>TipoCentroCostoManager</b>
	 */
	public static TipoCentroCostoManager getTipoCentroCostoManager(){
		return (TipoCentroCostoManager) SpringUtil.getBean("tipoCentroCostoManager",TipoCentroCostoManager.class);
	}
	/**
	 * Obtiene el bean de TipoCambioManager, para realizar la comunicación con la capa modelo
	 * @return <b>TipoCambioManager</b>
	 */
	public static TipoCambioManager getTipoCambioManager(){
		return (TipoCambioManager) SpringUtil.getBean("tipoCambioManager",TipoCambioManager.class);
	}
	/**
	 * Obtiene el bean de TipoMonedaManager, para realizar la comunicación con la capa modelo
	 * @return <b>TipoMonedaManager</b>
	 */
	public static TipoMonedaManager getTipoMonedaManager(){
		return (TipoMonedaManager) SpringUtil.getBean("tipoMonedaManager",TipoMonedaManager.class);
	}
	/**
	 * Obtiene el bean de SessionManager, para realizar la comunicación con la capa modelo
	 * @return <b>SessionManager</b>
	 */
	public static SessionManager getSessionManager(){
		return (SessionManager) SpringUtil.getBean("sessionManager",SessionManager.class);
	}
	/**
	 * Obtiene el bean de ReimpresionAnticipadaManager, para realizar la comunicación con la capa modelo
	 * @return <b>ReimpresionAnticipadaManager</b>
	 */
	public static ReimpresionAnticipadaManager getReimpresionAnticipadaManager(){
		return (ReimpresionAnticipadaManager) SpringUtil.getBean("reimpresionAnticipadaManager",ReimpresionAnticipadaManager.class);
	}
	/**
	 * Obtiene el bean de TarifaClienteManager, para realizar la comunicación con la capa modelo
	 * @return <b>TarifaClienteManager</b>
	 */
	public static TarifaClienteManager getTarifaClienteManager(){
		return (TarifaClienteManager) SpringUtil.getBean("tarifaClienteManager",TarifaClienteManager.class);
	}
	/**
	 * Obtiene el bean de TarifaClienteDetalleManager, para realizar la comunicación con la capa modelo
	 * @return <b>TarifaClienteDetalleManager</b>
	 */
	public static TarifaClienteDetalleManager getTarifaClienteDetalleManager(){
		return (TarifaClienteDetalleManager) SpringUtil.getBean("tarifaClienteDetalleManager",TarifaClienteDetalleManager.class);
	}
	/**
	 * Obtiene el bean de TipoAsientoManager, para realizar la comunicación con la capa modelo
	 * @return <b>TipoAsientoManager</b>
	 */
	public static TipoAsientoManager getTipoAsientoManager(){
		return (TipoAsientoManager) SpringUtil.getBean("tipoAsientoManager",TipoAsientoManager.class);
	}
	/**
	 * Obtiene el bean de LocalidadIntegracionManager, para realizar la comunicación con la capa modelo
	 * @return <b>LocalidadIntegracionManager</b>
	 */
	public static PoolLocalidadManager getPoolLocalidadManager(){
		return (PoolLocalidadManager) SpringUtil.getBean("poolLocalidadManager",PoolLocalidadManager.class);
	}
	/**
	 * Obtiene el bean de VentaPoolManager, para realizar la comunicación con la capa modelo
	 * @return <b>VentaPoolManager</b>
	 */
	public static VentaPoolManager getVentaPoolManager(){
		return (VentaPoolManager) SpringUtil.getBean("ventaPoolManager",VentaPoolManager.class);
	}
	/**
	 * Obtiene el bean de OcupacionAsientosBloqueadosPoolManager, para realizar la comunicación con la capa modelo
	 * @return <b>OcupacionAsientosBloqueadosPoolManager</b>
	 */
	public static OcupacionAsientosBloqueadosPoolManager getOcupacionAsientosBloqueadosPoolManager(){
		return (OcupacionAsientosBloqueadosPoolManager) SpringUtil.getBean("ocupacionAsientosBloqueadosPoolManager",OcupacionAsientosBloqueadosPoolManager.class);
	}
	/**
	 * Obtiene el bean de TipoNotaManager, para realizar la comunicación con la capa modelo
	 * @return <b>TipoNotaManager</b>
	 */
	public static TipoNotaManager getTipoNotaManager(){
		return (TipoNotaManager) SpringUtil.getBean("tipoNotaManager",TipoNotaManager.class);
	}
	/**
	 * Obtiene el bean de EmbarquePasajeroManager, para realizar la comunicación con la capa modelo
	 * @return <b>EmbarquePasajeroManager</b>
	 */
	public static EmbarquePasajeroManager getEmbarquePasajeroManager(){
		return (EmbarquePasajeroManager) SpringUtil.getBean("embarquePasajeroManager",EmbarquePasajeroManager.class);
	}
	/**
	 * Obtiene el bean de DetalleEmbarquePasajeroManager, para realizar la comunicación con la capa modelo
	 * @return <b>DetalleEmbarquePasajeroManager</b>
	 */
	public static DetalleEmbarquePasajeroManager getDetalleEmbarquePasajeroManager(){
		return (DetalleEmbarquePasajeroManager) SpringUtil.getBean("detalleEmbarquePasajeroManager",DetalleEmbarquePasajeroManager.class);
	}
	/**
	 * Obtiene el bean de TipoPrecioManager, para realizar la comunicación con la capa modelo
	 * @return <b>TipoPrecioManager</b>
	 */
	public static TipoPrecioManager getTipoPrecioManager(){
		return (TipoPrecioManager) SpringUtil.getBean("tipoPrecioManager",TipoPrecioManager.class);
	}
	/**
	 * Obtiene el bean de DetalleButacaManager, para realizar la comunicación con la capa modelo
	 * @return <b>DetalleButacaManager</b>
	 */
	public static DetalleButacaManager getDetalleButacaManager(){
		return (DetalleButacaManager) SpringUtil.getBean("detalleButacaManager",DetalleButacaManager.class);
	}
	/**
	 * Obtiene el bean de ComprobantesBloqueadosManager, para realizar la comunicación con la capa modelo
	 * @return <b>ComprobantesBloqueadosManager</b>
	 */
	public static ComprobantesBloqueadosManager getComprobantesBloqueadosManager(){
		return (ComprobantesBloqueadosManager) SpringUtil.getBean("comprobantesBloqueadosManager",ComprobantesBloqueadosManager.class);
	}
	
	public static TarifaManager getTarifaManager(){
		return (TarifaManager) SpringUtil.getBean("tarifaManager", TarifaManager.class);
	}
	
	
	public static TarifaRegularManager getTarifaRegularManager(){
		return (TarifaRegularManager) SpringUtil.getBean("tarifaRegularManager", TarifaRegularManager.class);
	}
	
	public static TarifaRegularAudManager getTarifaRegularAudManager(){
		return (TarifaRegularAudManager) SpringUtil.getBean("tarifaRegularAudManager", TarifaRegularAudManager.class);
	}
	
	public static VentaServicioEspecialManager getVentaServicioEspecialManager() {
		return (VentaServicioEspecialManager) SpringUtil.getBean("ventaServicioEspecialManager", VentaServicioEspecialManager.class);
	}
	public static EquipajeManager getEquipajeManager(){
		return (EquipajeManager) SpringUtil.getBean("equipajeManager", EquipajeManager.class);
	}
	
	public static DetalleEquipajeManager getDetalleEquipajeManager(){
		return (DetalleEquipajeManager) SpringUtil.getBean("detalleEquipajeManager", DetalleEquipajeManager.class);
	}
	
	/**
	 * Obtiene el bean de TranscarManager, para realizar la comunicación con la capa modelo
	 * @return <b>TranscarManager</b>
	 */
	public static TranscarManager getTranscarManager(){
		return (TranscarManager) SpringUtil.getBean("transcarManager",TranscarManager.class);
	}
}
