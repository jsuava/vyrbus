/**
 * Proyecto		: SISVYR
 * Sistema		: Sistema de Ventas y Reservas
 * Descripciï¿½n	: Implementaciï¿½n de mï¿½todos que permiten el acceso al modelo.
 * Autor		: Josï¿½ Sullo Avalos
 * Fecha		: 28/09/2012
 */
package com.cystesoft.vyrbus.service.business.impl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.springframework.transaction.annotation.Transactional;
import org.zkoss.zk.ui.Executions;

import com.cystesoft.vyrbus.model.bean.Agencia;
import com.cystesoft.vyrbus.model.bean.CanalVenta;
import com.cystesoft.vyrbus.model.bean.Cliente;
import com.cystesoft.vyrbus.model.bean.ControlEspecieValorada;
import com.cystesoft.vyrbus.model.bean.DestinatariosEmails;
import com.cystesoft.vyrbus.model.bean.EntidadEncomiendaPasajes;
import com.cystesoft.vyrbus.model.bean.FormaPago;
import com.cystesoft.vyrbus.model.bean.Itinerario;
import com.cystesoft.vyrbus.model.bean.LineaCreditoCliente;
import com.cystesoft.vyrbus.model.bean.Liquidacion;
import com.cystesoft.vyrbus.model.bean.Manifiesto;
import com.cystesoft.vyrbus.model.bean.PasajeroFrecuente;
import com.cystesoft.vyrbus.model.bean.PuntosPasajeroFrecuente;
import com.cystesoft.vyrbus.model.bean.Ruta;
import com.cystesoft.vyrbus.model.bean.TipoComprobante;
import com.cystesoft.vyrbus.model.bean.TipoFormaPago;
import com.cystesoft.vyrbus.model.bean.TipoMovimiento;
import com.cystesoft.vyrbus.model.bean.TipoNota;
import com.cystesoft.vyrbus.model.bean.TitanLiquidacionTurnoPasaje;
import com.cystesoft.vyrbus.model.bean.TitanVentaPasaje;
import com.cystesoft.vyrbus.model.bean.TmpOcupacionAsientos;
import com.cystesoft.vyrbus.model.bean.Usuario;
import com.cystesoft.vyrbus.model.bean.UsuarioHardware;
import com.cystesoft.vyrbus.model.bean.VentaPasaje;
import com.cystesoft.vyrbus.model.bean.VentaPasajeHistorial;
import com.cystesoft.vyrbus.model.dao.ControlEspecieValoradaDAO;
import com.cystesoft.vyrbus.model.dao.EspecieValoradaDAO;
import com.cystesoft.vyrbus.model.dao.ItinerarioDAO;
import com.cystesoft.vyrbus.model.dao.LineaCreditoClienteDAO;
import com.cystesoft.vyrbus.model.dao.PasajeroFrecuenteDAO;
import com.cystesoft.vyrbus.model.dao.PuntosPasajeroFrecuenteDAO;
import com.cystesoft.vyrbus.model.dao.RutaDAO;
import com.cystesoft.vyrbus.model.dao.TitanDAO;
import com.cystesoft.vyrbus.model.dao.TmpOcupacionAsientosDAO;
import com.cystesoft.vyrbus.model.dao.VentaPasajesDAO;
import com.cystesoft.vyrbus.model.dao.VentaPasajesHistorialDAO;
import com.cystesoft.vyrbus.service.business.VentaPasajesManager;
import com.cystesoft.vyrbus.service.exceptions.CapacityExceedsException;
import com.cystesoft.vyrbus.service.exceptions.DuplicateSeatException;
import com.cystesoft.vyrbus.service.exceptions.NumeroBoletoDuplicadoException;
import com.cystesoft.vyrbus.service.exceptions.TiempoExpiracionBloqueoException;
import com.cystesoft.vyrbus.service.fe.Result;
import com.cystesoft.vyrbus.service.locator.ServiceLocator;
import com.cystesoft.vyrbus.service.mappers.ResumenAnulacionPostergacion;
import com.cystesoft.vyrbus.service.mappers.ResumenVentas;
import com.cystesoft.vyrbus.service.mappers.VentasPiloto;
import com.cystesoft.vyrbus.service.util.Constantes;
import com.cystesoft.vyrbus.service.util.Messages;
import com.cystesoft.vyrbus.service.util.MyTime;
import com.cystesoft.vyrbus.service.util.Sendmail;
import com.cystesoft.vyrbus.service.util.Util;
import com.cystesoft.vyrbus.service.util.UtilData;
import com.cystesoft.vyrbus.service.util.VentasNotas;
import com.cystesoft.vyrbus.service.util.WSFE;

/**
 * @author Jose
 *
 */
public class VentaPasajesManagerImpl implements VentaPasajesManager {
	private VentaPasajesDAO ventaPasajesDAO;
	private ControlEspecieValoradaDAO controlEspecieValoradaDAO;
	private ItinerarioDAO itinerarioDAO;
	private TmpOcupacionAsientosDAO tmpOcupacionAsientosDAO;
	private RutaDAO rutaDAO;
	private PasajeroFrecuenteDAO pasajeroFrecuenteDAO;
	private PuntosPasajeroFrecuenteDAO puntosPasajeroFrecuenteDAO;
	private TitanDAO titanDAO;
	private EspecieValoradaDAO especieValoradaDAO;
	private LineaCreditoClienteDAO lineaCreditoClienteDAO;
	private VentaPasajesHistorialDAO ventaPasajesHistorialDAO;

	/**
	 * @return the titanDAO
	 */
	public TitanDAO getTitanDAO() {
		return titanDAO;
	}
	/**
	 * @param titanDAO the titanDAO to set
	 */
	public void setTitanDAO(TitanDAO titanDAO) {
		this.titanDAO = titanDAO;
	}
	/**
	 * @return the ventaPasajesDAO
	 */
	public VentaPasajesDAO getVentaPasajesDAO() {
		return ventaPasajesDAO;
	}
	/**
	 * @param ventaPasajesDAO the ventaPasajesDAO to set
	 */
	public void setVentaPasajesDAO(VentaPasajesDAO ventaPasajesDAO) {
		this.ventaPasajesDAO = ventaPasajesDAO;
	}

	/**
	 * @return the controlEspecieValoradaDAO
	 */
	public ControlEspecieValoradaDAO getControlEspecieValoradaDAO() {
		return controlEspecieValoradaDAO;
	}
	/**
	 * @param controlEspecieValoradaDAO the controlEspecieValoradaDAO to set
	 */
	public void setControlEspecieValoradaDAO(ControlEspecieValoradaDAO controlEspecieValoradaDAO) {
		this.controlEspecieValoradaDAO = controlEspecieValoradaDAO;
	}

	/**
	 * @return the itinerarioDAO
	 */
	public ItinerarioDAO getItinerarioDAO() {
		return itinerarioDAO;
	}
	/**
	 * @param itinerarioDAO the itinerarioDAO to set
	 */
	public void setItinerarioDAO(ItinerarioDAO itinerarioDAO) {
		this.itinerarioDAO = itinerarioDAO;
	}

	/**
	 * @return the tmpOcupacionAsientosDAO
	 */
	public TmpOcupacionAsientosDAO getTmpOcupacionAsientosDAO() {
		return tmpOcupacionAsientosDAO;
	}
	/**
	 * @param tmpOcupacionAsientosDAO the tmpOcupacionAsientosDAO to set
	 */
	public void setTmpOcupacionAsientosDAO(
			TmpOcupacionAsientosDAO tmpOcupacionAsientosDAO) {
		this.tmpOcupacionAsientosDAO = tmpOcupacionAsientosDAO;
	}

	/**
	 * @return the rutaDAO
	 */
	public RutaDAO getRutaDAO() {
		return rutaDAO;
	}
	/**
	 * @param rutaDAO the rutaDAO to set
	 */
	public void setRutaDAO(RutaDAO rutaDAO) {
		this.rutaDAO = rutaDAO;
	}

	/**
	 * @return the pasajeroFrecuenteDAO
	 */
	public PasajeroFrecuenteDAO getPasajeroFrecuenteDAO() {
		return pasajeroFrecuenteDAO;
	}
	/**
	 * @param pasajeroFrecuenteDAO the pasajeroFrecuenteDAO to set
	 */
	public void setPasajeroFrecuenteDAO(PasajeroFrecuenteDAO pasajeroFrecuenteDAO) {
		this.pasajeroFrecuenteDAO = pasajeroFrecuenteDAO;
	}
	/**
	 * @return the puntosPasajeroFrecuenteDAO
	 */
	public PuntosPasajeroFrecuenteDAO getPuntosPasajeroFrecuenteDAO() {
		return puntosPasajeroFrecuenteDAO;
	}
	/**
	 * @param puntosPasajeroFrecuenteDAO the puntosPasajeroFrecuenteDAO to set
	 */
	public void setPuntosPasajeroFrecuenteDAO(PuntosPasajeroFrecuenteDAO puntosPasajeroFrecuenteDAO) {
		this.puntosPasajeroFrecuenteDAO = puntosPasajeroFrecuenteDAO;
	}
	/**
	 * @return the especieValoradaDAO
	 */
	public EspecieValoradaDAO getEspecieValoradaDAO() {
		return especieValoradaDAO;
	}
	/**
	 * @param especieValoradaDAO the especieValoradaDAO to set
	 */
	public void setEspecieValoradaDAO(EspecieValoradaDAO especieValoradaDAO) {
		this.especieValoradaDAO = especieValoradaDAO;
	}
	/**
	 * @return the lineaCreditoClienteDAO
	 */
	public LineaCreditoClienteDAO getLineaCreditoClienteDAO() {
		return lineaCreditoClienteDAO;
	}
	/**
	 * @param lineaCreditoClienteDAO the lineaCreditoClienteDAO to set
	 */
	public void setLineaCreditoClienteDAO(LineaCreditoClienteDAO lineaCreditoClienteDAO) {
		this.lineaCreditoClienteDAO = lineaCreditoClienteDAO;
	}


	/*
	 * (non-Javadoc)
	 * @see com.tepsa.sisvyr.service.business.VentaPasajesManager#guardarVenta(com.tepsa.sisvyr.model.bean.VentaPasaje, boolean, boolean, boolean)
	 */
	@Override
	@Transactional
	public int guardarVenta(VentaPasaje ventaPasaje, boolean isFechaAbierta, boolean generaControl, boolean validaBloqueo,boolean validarDuplicidadAsiento, boolean ejecutarSeqByCorrelativo) throws Exception {
		int result = Constantes.FAILURE;
		try{
			if(!isFechaAbierta && !(ventaPasaje.getTipoTransaccion().equals(Constantes.TIPO_OPERACION_VENTA_POOL)) && ventaPasaje.getNumeroAsiento()!=null && ventaPasaje.getNumeroPiso()!=null){
				/*	Validando que no se haya cambiado el tipo de Bus durante la venta	*/
				boolean excedeCapacidad = false;
				List<Object> lstCapacidad = getItinerarioDAO().validateCapacity(ventaPasaje.getItinerario().getId(), ventaPasaje.getNumeroAsiento(), ventaPasaje.getNumeroPiso());
				if(lstCapacidad.size()>0){
					excedeCapacidad = (Boolean)lstCapacidad.get(0);
					if(excedeCapacidad)
						throw new CapacityExceedsException((String)lstCapacidad.get(1));
				}

				/*	Validando que el asiento no haya sido utilizado antes de la venta	*/
				if(validarDuplicidadAsiento){
					Long ocupabilidad = getVentaPasajesDAO().validateSeat(ventaPasaje.getItinerario(), ventaPasaje.getRuta(), ventaPasaje.getNumeroAsiento(), ventaPasaje.getNumeroPiso());
					if(ocupabilidad.longValue()>0){
						if(ventaPasaje.getVentaPasaje()!=null && ventaPasaje.getVentaPasaje().getId().longValue()!=ocupabilidad.longValue())
							throw new DuplicateSeatException();
						else if(ventaPasaje.getVentaPasaje()==null)
							throw new DuplicateSeatException();
					}
				}
			}

			/*	Validando que no haya expirado el tiempo del bloqueo	*/
			if(validaBloqueo && ventaPasaje.getNumeroAsiento()!=null){
				TreeMap<String, Object> criteriosBusqueda = new TreeMap<>();
				criteriosBusqueda.put("itinerario.id", ventaPasaje.getItinerario().getId());
				criteriosBusqueda.put("ruta.id", ventaPasaje.getRuta().getId());
				if(ventaPasaje.getEsRemoto()!=null && ventaPasaje.getEsRemoto()){
					criteriosBusqueda.put("usuarioHardware.id", ventaPasaje.getUsuarioHardwareRemoto().getId());
					criteriosBusqueda.put("usuario.id", ventaPasaje.getUsuarioRemoto().getId());
				}else{
					criteriosBusqueda.put("usuarioHardware.id", ventaPasaje.getUsuarioHardware().getId());
					criteriosBusqueda.put("usuario.id", ventaPasaje.getUsuario().getId());
				}
				criteriosBusqueda.put("numeroAsiento", ventaPasaje.getNumeroAsiento());
				criteriosBusqueda.put("numeroPiso", ventaPasaje.getNumeroPiso());
				criteriosBusqueda.put("fechaPartida", Util.DatetoString(ventaPasaje.getFechaPartida(), Constantes.DATE_FORMAT));
				criteriosBusqueda.put("horaPartida", ventaPasaje.getHoraPartida());
				List<TmpOcupacionAsientos> lstTMP = getTmpOcupacionAsientosDAO().buscarPorX(criteriosBusqueda, null);
				if(lstTMP.size()==0)
					throw new TiempoExpiracionBloqueoException();
			}

			/*	Si la operación a realizar es una venta generar boleto.	*/
			/* Valida si no es un servicio especial*/
			if(!(ventaPasaje.getServicioEspecialFactura())){
				if(ventaPasaje.getTipoTransaccion().equals(Constantes.TIPO_OPERACION_VENTA) ||
						ventaPasaje.getTipoTransaccion().equals(Constantes.TIPO_OPERACION_VENTA_POOL) ||
						ventaPasaje.getTipoTransaccion().equals(Constantes.TIPO_OPERACION_EXCESO)){
					/**Begin 21/10/2016 - jabanto**/
					/*Vuelve a realizar la busqueda del correlativo y lo actualiza, a exception de los boletos, ya que no son necesarios pues son manuales*/
//					EspecieValorada especieValorada=null;
					ControlEspecieValorada controlEspecieValorada = null;
					if(ventaPasaje.getTipoComprobante().getId().intValue()!=Constantes.ID_TIPCOM_BOLETO_VIAJE){
//						especieValorada=UtilData.buscarEspecieValorada(ventaPasaje.getTipoComprobante().getId(), ventaPasaje.getAgencia(), true);
//						ventaPasaje.setNumeroBoleto(especieValorada.toString());
						controlEspecieValorada = UtilData.buscarEspecieValoradaByCaja(ventaPasaje.getTipoComprobante().getId(), ventaPasaje.getAgencia(), ejecutarSeqByCorrelativo, ventaPasaje.getUsuarioHardware(), null);
						ventaPasaje.setNumeroBoleto(controlEspecieValorada.toString());
					}
					/*	Validando que el numero del comprobante no exista en la DB 	*/
					if(!(ventaPasaje.getServicioEspecialFactura())){ //Solo se omite esta validación en el caso de los servicios especiales con Factura.
						if(isBoletoDuplicado(ventaPasaje.getNumeroBoleto(), ventaPasaje.getTipoComprobante().getId()))
							throw new NumeroBoletoDuplicadoException();
					}
					/*Actualiza el correlativo*/
					if(ventaPasaje.getTipoComprobante().getId().intValue()!=Constantes.ID_TIPCOM_BOLETO_VIAJE){
						int position = ventaPasaje.getNumeroBoleto().indexOf("-");
						Long correlativo = Long.valueOf(ventaPasaje.getNumeroBoleto().substring(position+1))+1;
//						especieValorada.setCorrelativoActual(correlativo);
//						getEspecieValoradaDAO().update(especieValorada);
						controlEspecieValorada.setCorrelativoActual(correlativo);
						getControlEspecieValoradaDAO().update(controlEspecieValorada);
					}
				}else
					ventaPasaje.setNumeroBoleto(null);
			}
			
			//Valida si es una confirmación de una reserva - jabanto - 23/02/2023
			if(ventaPasaje.getVentaPasaje()!=null && ventaPasaje.getVentaPasaje().getTipoTransaccion().equals(Constantes.TIPO_OPERACION_RESERVA)) {
				//Anula la reserva
				VentaPasaje ventaReserva = getVentaPasajesDAO().buscarPorId(ventaPasaje.getVentaPasaje().getId());
				ventaReserva.setTipoMovimiento(new TipoMovimiento(Constantes.ID_TIPMOV_ANULACION_SISTEMA));
				ventaReserva.setFechaAnulacion(new Date());
				ventaReserva.setUsuarioAnulacion(ventaPasaje.getUsuario());
				ventaReserva.setUsuarioModificacion(ventaPasaje.getUsuarioModificacion());
				ventaPasaje.setIpModificacion(ventaPasaje.getIpModificacion());
				getVentaPasajesDAO().update(ventaReserva);
			}
			

			/*	Guardando la instancia de la venta de pasajes	*/
			getVentaPasajesDAO().save(ventaPasaje);

			/*	Solo si se necesita generar numero de control	*/
			if(generaControl){
				String nControl = Util.generateControlNumber(Util.decimalToHexadecimal(ventaPasaje.getId()));
				ventaPasaje.setFechaInsercion(Util.StringtoDate(ventaPasajesDAO.getDateSystem(), Constantes.DATE_TIME_FORMAT));
				ventaPasaje.setNumeroControl(nControl);
				if(ventaPasaje.getTipoTransaccion().equals(Constantes.TIPO_OPERACION_VENTA))
					ventaPasaje.setVentaOriginal(ventaPasaje.getId());
				/*	Actualizando el numero de control a la venta realizada	*/
				getVentaPasajesDAO().update(ventaPasaje);
			}

			/* Valida el tipo de transaction y el IdVentaOriginal - 08/05/2013*/
			if((ventaPasaje.getTipoTransaccion().equals(Constantes.TIPO_OPERACION_VENTA) || ventaPasaje.getTipoTransaccion().equals(Constantes.TIPO_OPERACION_VENTA_POOL))
					&& ventaPasaje.getVentaOriginal()==null){
				ventaPasaje.setFechaInsercion(Constantes.FORMAT_DATE_TIME_24H.parse(new MyTime().dateServer()));
				ventaPasaje.setVentaOriginal(ventaPasaje.getId());
				getVentaPasajesDAO().update(ventaPasaje);
//			}else if(ventaPasaje.getTipoTransaccion().equals(Constantes.TIPO_OPERACION_RESERVA) && ventaPasaje.getFormaPago().getId().intValue()==Constantes.ID_FORPAG_CORTESIA){
//				ventaPasaje.setVentaOriginal(ventaPasaje.getId());
//				getVentaPasajesDAO().update(ventaPasaje);
			}

			/*	Si no es fecha Abierta	*/
			if(!isFechaAbierta && ventaPasaje.getNumeroAsiento()!=null && ventaPasaje.getNumeroPiso()!=null){
				/*	Eliminando el asiento de la tabla temporal	*/
				TmpOcupacionAsientos tmp = new TmpOcupacionAsientos();
				tmp.setRuta(ventaPasaje.getRuta());
				tmp.setItinerario(ventaPasaje.getItinerario());
				tmp.setNumeroAsiento(ventaPasaje.getNumeroAsiento());
				tmp.setNumeroPiso(ventaPasaje.getNumeroPiso());
				getTmpOcupacionAsientosDAO().desbloquearAsiento(tmp);
			}

			/*	Si se trata de un PAXFREE y es una Venta -- no entra cuando es una emisión de cortesia por puntos o cumpleaños desde el modulo "beneficios paxfree"	*/
			if(ventaPasaje.getPasajero().isPaxFree() && ventaPasaje.getTipoTransaccion().equals(Constantes.TIPO_OPERACION_VENTA)){
				PasajeroFrecuente paxfree = getPasajeroFrecuenteDAO().buscarPaxFree(ventaPasaje.getPasajero().getId(), Constantes.TRUE_VALUE);
				Ruta ruta = getRutaDAO().buscarPorId(new Long(ventaPasaje.getRuta().getId()));

				/*	Si la forma de pago es CORTESIA y el tipo CANJE X PUNTOS	*/
				if(paxfree!=null && ventaPasaje.getFormaPago().getId().intValue()==Constantes.ID_FORPAG_CORTESIA && ventaPasaje.getTipoFormaPago().getId().intValue()==Constantes.ID_TIPFORPAG_PUNTOS){
					paxfree.setPuntosAcumulados(paxfree.getPuntosAcumulados().intValue()-ruta.getPuntaje().intValue());
					paxfree.setPuntosUtilizados(paxfree.getPuntosUtilizados()+ruta.getPuntaje());
					paxfree.setUsuarioModificacion(ventaPasaje.getUsuarioModificacion());
					paxfree.setIpModificacion(ventaPasaje.getUsuarioModificacion());
					getPasajeroFrecuenteDAO().update(paxfree);

//				}else if(ventaPasaje.getFormaPago().getId().intValue()==Constantes.ID_FORPAG_CONTADO){	// Si es venta al Contado
//					paxfree.setPuntosAcumulados(paxfree.getPuntosAcumulados().intValue()+Constantes.PUNTOS_GANADOS_X_PAXFREE);
//					getPasajeroFrecuenteDAO().update(paxfree);
//
//					guardarPuntosPaxFree(paxfree, ventaPasaje);
//				}else if(ventaPasaje.getFormaPago().getId().intValue()==Constantes.ID_FORPAG_CREDITO
//						&& ventaPasaje.getTipoComprobante().getId().intValue()==Constantes.ID_TIPCOM_VOUCHER_AGENCIA_VIAJES){ //Si es agencia de viajes
//					paxfree.setPuntosAcumulados(paxfree.getPuntosAcumulados().intValue()+Constantes.PUNTOS_GANADOS_X_PAXFREE);
//					getPasajeroFrecuenteDAO().update(paxfree);
//
//					guardarPuntosPaxFree(paxfree, ventaPasaje);
//
				}else if(paxfree!=null && paxfree.getEstado().intValue()==Constantes.TRUE_VALUE &&
						(
						ventaPasaje.getTipoComprobante().getId().intValue()==Constantes.ID_TIPCOM_BOLETO_VIAJE ||
						ventaPasaje.getTipoComprobante().getId().intValue()==Constantes.ID_TIPCOM_BOLETA_VENTA ||
						ventaPasaje.getTipoComprobante().getId().intValue()==Constantes.ID_TIPCOM_FACTURA
						) && ventaPasaje.getFormaPago().getId().intValue()!=Constantes.ID_FORPAG_CORTESIA){
					/*Acumula puntos cuando el tipo de comprobante es boleto de viaje - 17/10/2013 - jabanto*/
					paxfree.setPuntosAcumulados(paxfree.getPuntosAcumulados().intValue()+Constantes.PUNTOS_GANADOS_X_PAXFREE);

					Usuario usuario= new Usuario();
					usuario.setLogin(ventaPasaje.getUsuarioInsercion());
					UtilData.auditarRegistro(paxfree, true,usuario, Executions.getCurrent());
					getPasajeroFrecuenteDAO().update(paxfree);

					//Genera puntos ganados
					guardarPuntosPaxFree(paxfree, ventaPasaje);
				}
			}

			/* Valida si el DNI del pasajero es o no valido según validacion previa con el reniec  02/06/2015*/
			Util.validarValidacionDNIReniec(ventaPasaje);

			result = Constantes.CORRECT;
		}catch(CapacityExceedsException ceex){
			throw new CapacityExceedsException();
		}catch(DuplicateSeatException dsex){
			throw new DuplicateSeatException();
		}catch(NumeroBoletoDuplicadoException nbdex){
			throw new NumeroBoletoDuplicadoException();
		}catch(TiempoExpiracionBloqueoException tebex){
			throw new TiempoExpiracionBloqueoException();
		}catch(Exception ex){
			ex.printStackTrace();
			throw new Exception(ex);
		}
		return result;
	}

	/*
	 * (non-Javadoc)
	 * @see com.tepsa.sisvyr.service.business.VentaPasajesManager#guardarVenta(com.tepsa.sisvyr.model.bean.VentaPasaje, boolean, boolean)
	 */
	@Override
	@Transactional
	public int guardarVentaIdaVuelta(List<VentaPasaje> lstVentas, boolean generaControl, boolean ejecutarSeqByCorrelativo) throws Exception {
		int result = Constantes.FAILURE;
		Long idIDA = new Long(0);
		try{
			for(int i=0; i<lstVentas.size(); i++){
				VentaPasaje ventaPasaje = lstVentas.get(i);

				/*	Validando que no se haya cambiado el tipo de Bus durante la venta	*/
				boolean excedeCapacidad = false;
				List<Object> lstCapacidad = getItinerarioDAO().validateCapacity(ventaPasaje.getItinerario().getId(), ventaPasaje.getNumeroAsiento(), ventaPasaje.getNumeroPiso());
				if(lstCapacidad.size()>0){
					excedeCapacidad = (Boolean)lstCapacidad.get(0);
					if(excedeCapacidad)
						throw new CapacityExceedsException((String)lstCapacidad.get(1));
				}

				/*	Validando que el asiento no haya sido utilizado antes de la venta	*/
				Long ocupabilidad = getVentaPasajesDAO().validateSeat(ventaPasaje.getItinerario(), ventaPasaje.getRuta(), ventaPasaje.getNumeroAsiento(), ventaPasaje.getNumeroPiso());
				if(ocupabilidad.longValue()>0){
					if(ventaPasaje.getVentaPasaje()!=null && ventaPasaje.getVentaPasaje().getId().longValue()!=ocupabilidad.longValue())
						throw new DuplicateSeatException();
					else if(ventaPasaje.getVentaPasaje()==null)
						throw new DuplicateSeatException();
				}

				/*	Validando que no haya expirado el tiempo del bloqueo	*/
				TreeMap<String, Object> criteriosBusqueda = new TreeMap<>();
				criteriosBusqueda.put("itinerario.id", ventaPasaje.getItinerario().getId());
				criteriosBusqueda.put("ruta.id", ventaPasaje.getRuta().getId());
				if(ventaPasaje.getEsRemoto()!=null && ventaPasaje.getEsRemoto()){
					criteriosBusqueda.put("usuarioHardware.id", ventaPasaje.getUsuarioHardwareRemoto().getId());
					criteriosBusqueda.put("usuario.id", ventaPasaje.getUsuarioRemoto().getId());
				}else{
					criteriosBusqueda.put("usuarioHardware.id", ventaPasaje.getUsuarioHardware().getId());
					criteriosBusqueda.put("usuario.id", ventaPasaje.getUsuario().getId());
				}
				criteriosBusqueda.put("numeroAsiento", ventaPasaje.getNumeroAsiento());
				criteriosBusqueda.put("numeroPiso", ventaPasaje.getNumeroPiso());
				criteriosBusqueda.put("fechaPartida", Util.DatetoString(ventaPasaje.getFechaPartida(), Constantes.DATE_FORMAT));
				criteriosBusqueda.put("horaPartida", ventaPasaje.getHoraPartida());
				List<TmpOcupacionAsientos> lstTMP = getTmpOcupacionAsientosDAO().buscarPorX(criteriosBusqueda, null);
				if(lstTMP.size()==0)
					throw new TiempoExpiracionBloqueoException();

				/*End Begin 24/10/2016 - jabanto*/
//				if(i==1){
//					int position = lstVentas.get(0).getNumeroBoleto().indexOf("-");
//					String serie = lstVentas.get(0).getNumeroBoleto().substring(0, position);
//					Long correlativo = Long.valueOf(lstVentas.get(0).getNumeroBoleto().substring(position+1))+1;
//					String correla = "00000000"+correlativo;
//					ventaPasaje.setNumeroBoleto(serie+"-"+correla.substring(correla.length()-8));
//				}

				/*	Si la operaciï¿½n a realizar es una venta generar boleto.*/
				if(ventaPasaje.getTipoTransaccion().equals(Constantes.TIPO_OPERACION_VENTA)){
					/**Begin 21/10/2016 - jabanto**/
					/*Vuelve a realizar la busqueda del correlativo y lo actualiza, a exception de los boletos, ya que no son necesarios pues son manuales*/
					/*BEGIN 14/06/2021 - javalos - Correlativo by caja*/
//					EspecieValorada especieValorada=null;
					ControlEspecieValorada controlEspecieValorada = null;
  					/*END 14/06/2021 - javalos - Correlativo by caja*/
					if(ventaPasaje.getTipoComprobante().getId().intValue()!=Constantes.ID_TIPCOM_BOLETO_VIAJE){
						/*BEGIN 14/06/2021 - javalos - Correlativo by caja*/
//						especieValorada=UtilData.buscarEspecieValorada(ventaPasaje.getTipoComprobante().getId(), ventaPasaje.getAgencia(), true);
//						ventaPasaje.setNumeroBoleto(especieValorada.toString());
						controlEspecieValorada = UtilData.buscarEspecieValoradaByCaja(ventaPasaje.getTipoComprobante().getId(), ventaPasaje.getAgencia(), ejecutarSeqByCorrelativo, ventaPasaje.getUsuarioHardware(), null);
						ventaPasaje.setNumeroBoleto(controlEspecieValorada.toString());
						/*END 14/06/2021 - javalos - Correlativo by caja*/
					}
					/*	Validando que el boleto no exista en la DB 	*/
					if(!(ventaPasaje.getServicioEspecialFactura())){ //Solo se omite esta validaciï¿½n en el caso de los servicios especiales con Factura.
						if(isBoletoDuplicado(ventaPasaje.getNumeroBoleto(), ventaPasaje.getTipoComprobante().getId()))
							throw new NumeroBoletoDuplicadoException();
					}
					/*Actualiza le correlativo*/
					if(ventaPasaje.getTipoComprobante().getId().intValue()!=Constantes.ID_TIPCOM_BOLETO_VIAJE){
						int position = ventaPasaje.getNumeroBoleto().indexOf("-");
						Long correlativo = Long.valueOf(ventaPasaje.getNumeroBoleto().substring(position+1))+1;
						/*BEGIN 14/06/2021 - javalos - Correlativo by caja*/
//						especieValorada.setCorrelativoActual(correlativo);
//						getEspecieValoradaDAO().update(especieValorada);
						controlEspecieValorada.setCorrelativoActual(correlativo);
						getControlEspecieValoradaDAO().update(controlEspecieValorada);
						/*END 14/06/2021 - javalos - Correlativo by caja*/
					}

					/*Eng Begin 24/10/2016 - jabanto*/
//					int position = ventaPasaje.getNumeroBoleto().indexOf("-");
//					String serie = ventaPasaje.getNumeroBoleto().substring(0, position);
//					Long correlativo = Long.valueOf(ventaPasaje.getNumeroBoleto().substring(position+1))+1;

					/* Valida el tipo de comprobante para en funciï¿½n a ello actualizar el correlativo - 14/10/2013-jabanto */
//					if(ventaPasaje.getTipoComprobante().getId().intValue()==Constantes.ID_TIPCOM_BOLETO_VIAJE)
//						/* Actualizando el correlativo en Control Especie Valorada*/
//						getControlEspecieValoradaDAO().actualizarCorrelativoEspecieValorada(ventaPasaje.getTipoComprobante().getId(), ventaPasaje.getUsuarioHardware().getId(), serie, correlativo);
//					else
//						/* Actualizando el correlativo en Especie Valorada*/
//						ServiceLocator.getEspecieValoradaManager().actualizarCorrelativoEspecieValorada(ventaPasaje.getTipoComprobante().getId(), ventaPasaje.getAgencia().getId(), serie, correlativo);
				}else
					ventaPasaje.setNumeroBoleto(null);



				/*	Guardando la instancia de la venta de pasajes	*/
				getVentaPasajesDAO().save(ventaPasaje);
				if(i==0){
					ventaPasaje.setIdentificadorIdaRetorno(ventaPasaje.getId());
					idIDA = ventaPasaje.getId();
				}else
					ventaPasaje.setIdentificadorIdaRetorno(idIDA);

				/*	Solo si se necesita generar numero de control	*/
				if(generaControl){
					String nControl = Util.generateControlNumber(Util.decimalToHexadecimal(ventaPasaje.getId()));
					ventaPasaje.setFechaInsercion(Util.StringtoDate(ventaPasajesDAO.getDateSystem(), Constantes.DATE_TIME_FORMAT));
					ventaPasaje.setNumeroControl(nControl);
					if(ventaPasaje.getTipoTransaccion().equals(Constantes.TIPO_OPERACION_VENTA))
						ventaPasaje.setVentaOriginal(ventaPasaje.getId());
					/*	Actualizando el numero de control a la venta realizada	*/
					getVentaPasajesDAO().update(ventaPasaje);
				}

				/* Valida el tipo de transaction y el IdVentaOriginal - 08/05/2013*/
				if(ventaPasaje.getTipoTransaccion().equals(Constantes.TIPO_OPERACION_VENTA) && ventaPasaje.getVentaOriginal()==null){
					ventaPasaje.setFechaInsercion(Constantes.FORMAT_DATE_TIME_24H.parse(new MyTime().dateServer()));
					ventaPasaje.setVentaOriginal(ventaPasaje.getId());
					getVentaPasajesDAO().update(ventaPasaje);
				}

				/*	Eliminando el asiento de la tabla temporal	*/
				TmpOcupacionAsientos tmp = new TmpOcupacionAsientos();
				tmp.setRuta(ventaPasaje.getRuta());
				tmp.setItinerario(ventaPasaje.getItinerario());
				tmp.setNumeroAsiento(ventaPasaje.getNumeroAsiento());
				tmp.setNumeroPiso(ventaPasaje.getNumeroPiso());
				getTmpOcupacionAsientosDAO().desbloquearAsiento(tmp);

				/*	Si se trata de un PAXFREE y es una Venta	*/
				if(ventaPasaje.getPasajero().isPaxFree() && ventaPasaje.getTipoTransaccion().equals(Constantes.TIPO_OPERACION_VENTA)){
					PasajeroFrecuente paxfree = getPasajeroFrecuenteDAO().buscarPaxFree(ventaPasaje.getPasajero().getId(), Constantes.TRUE_VALUE);
					Ruta ruta = getRutaDAO().buscarPorId(new Long(ventaPasaje.getRuta().getId()));

					/*	Si la forma de pago es CORTESIA y el tipo CANJE X PUNTOS	*/
					if(ventaPasaje.getFormaPago().getId().intValue()==Constantes.ID_FORPAG_CORTESIA && ventaPasaje.getTipoFormaPago().getId().intValue()==Constantes.ID_TIPFORPAG_PUNTOS){
						paxfree.setPuntosAcumulados(paxfree.getPuntosAcumulados().intValue()-ruta.getPuntaje().intValue());
						paxfree.setPuntosUtilizados(paxfree.getPuntosUtilizados()+ruta.getPuntaje());
						paxfree.setUsuarioModificacion(ventaPasaje.getUsuarioModificacion());
						paxfree.setIpModificacion(ventaPasaje.getUsuarioModificacion());
						getPasajeroFrecuenteDAO().update(paxfree);
//					}else if(ventaPasaje.getFormaPago().getId().intValue()==Constantes.ID_FORPAG_CONTADO){	// Si es venta al Contado
//						paxfree.setPuntosAcumulados(paxfree.getPuntosAcumulados().intValue()+Constantes.PUNTOS_GANADOS_X_PAXFREE);
//						getPasajeroFrecuenteDAO().update(paxfree);
//
//						guardarPuntosPaxFree(paxfree, ventaPasaje);
//					}else if(ventaPasaje.getFormaPago().getId().intValue()==Constantes.ID_FORPAG_CREDITO
//							&& ventaPasaje.getTipoComprobante().getId().intValue()==Constantes.ID_TIPCOM_VOUCHER_AGENCIA_VIAJES){ //Si es agencia de viajes
//						paxfree.setPuntosAcumulados(paxfree.getPuntosAcumulados().intValue()+Constantes.PUNTOS_GANADOS_X_PAXFREE);
//						getPasajeroFrecuenteDAO().update(paxfree);
//
//						guardarPuntosPaxFree(paxfree, ventaPasaje);
//					}
					}else if(paxfree!=null && paxfree.getEstado().intValue()==Constantes.TRUE_VALUE &&
							ventaPasaje.getTipoComprobante().getId().intValue()==Constantes.ID_TIPCOM_BOLETO_VIAJE &&
							ventaPasaje.getFormaPago().getId().intValue()!=Constantes.ID_FORPAG_CORTESIA){
						/*Acumula puntos cuando el tipo de comprobante es boleto y la forma de pago es diferente a cortesia de viaje - 17/10/2013 - jabanto*/
						paxfree.setPuntosAcumulados(paxfree.getPuntosAcumulados().intValue()+Constantes.PUNTOS_GANADOS_X_PAXFREE);
						getPasajeroFrecuenteDAO().update(paxfree);

						guardarPuntosPaxFree(paxfree, ventaPasaje);
					}
				}

				/* Valida si el DNI del pasajero es o no valido segï¿½n validacion previa con el reniec  02/06/2015*/
				Util.validarValidacionDNIReniec(ventaPasaje);
			}

			result = Constantes.CORRECT;
		}catch(CapacityExceedsException ceex){
			throw new CapacityExceedsException();
		}catch(DuplicateSeatException dsex){
			throw new DuplicateSeatException();
		}catch(NumeroBoletoDuplicadoException nbdex){
			throw new NumeroBoletoDuplicadoException();
		}catch(TiempoExpiracionBloqueoException tebex){
			throw new TiempoExpiracionBloqueoException();
		}catch(Exception ex){
			ex.printStackTrace();
			throw new Exception(ex);
		}
		return result;
	}

//	@SuppressWarnings({ "deprecation", "static-access" })
	private void guardarPuntosPaxFree(PasajeroFrecuente paxfree, VentaPasaje ventaPasaje) throws Exception{

		/* implementado 06/05/2013	*/
		/* Guarda los puntos del Pasajero Precuente	*/

		//Calcula la fecha de caducidad del punto ganado #Modificado 05/03/2015 - jabanto
//		Date fechaCaducidad= new Date(ventaPasaje.getFechaLiquidacion().getTime()+(Constantes.MILISEGUNDOS_X_DIA*Constantes.DIAS_CADUCAN_PUNTOS));
//		Calendar calFechaCaducidad=Calendar.getInstance();
//		calFechaCaducidad.set(fechaCaducidad.getYear(), fechaCaducidad.getMonth(), calFechaCaducidad.getActualMaximum(calFechaCaducidad.DAY_OF_MONTH), 0, 0, 0);
//		fechaCaducidad.setDate(calFechaCaducidad.get(Calendar.DAY_OF_MONTH));


		//Edit begin 11/04/2016 - jabanto - (La fecha de caducidad de los punto debe ser el ultimo dia del mes)
		Date dateCaducidad= new Date(ventaPasaje.getFechaLiquidacion().getTime()+(Constantes.MILISEGUNDOS_X_DIA*Constantes.DIAS_CADUCAN_PUNTOS));
		Calendar calendar= Calendar.getInstance();
		calendar.setTimeInMillis(dateCaducidad.getTime());
		calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
		Date fechaCaducidad=Constantes.FORMAT_DATE.parse(Constantes.FORMAT_DATE.format(calendar.getTime()));

		PuntosPasajeroFrecuente puntosPasajeroFrecuente= new PuntosPasajeroFrecuente();
		puntosPasajeroFrecuente.setVentaPasaje(ventaPasaje);
		puntosPasajeroFrecuente.setPasajeroFrecuente(paxfree);
		puntosPasajeroFrecuente.setFechaEmision(ventaPasaje.getFechaLiquidacion());
		puntosPasajeroFrecuente.setPuntosAcumulados(Constantes.PUNTOS_GANADOS_X_PAXFREE);
		puntosPasajeroFrecuente.setFechaCaducidad(fechaCaducidad);
		puntosPasajeroFrecuente.setEstadoRegistro(Constantes.VALUE_ACTIVO);

		Usuario usuario= new Usuario();
		usuario.setLogin(ventaPasaje.getUsuarioInsercion());
		UtilData.auditarRegistro(puntosPasajeroFrecuente,usuario, Executions.getCurrent());
		ServiceLocator.getPuntosPasajeroFrecuenteManager().guardar(puntosPasajeroFrecuente);
	}

	private void borrarAsientoTmpOcupacion(VentaPasaje venta)throws Exception{
		/*	Eliminando el asiento de la tabla temporal	*/
		TmpOcupacionAsientos tmp = new TmpOcupacionAsientos();
		tmp.setRuta(venta.getRuta());
		tmp.setItinerario(venta.getItinerario());
		tmp.setNumeroAsiento(venta.getNumeroAsiento());
		tmp.setNumeroPiso(venta.getNumeroPiso());
		getTmpOcupacionAsientosDAO().desbloquearAsiento(tmp);
	}

	private boolean isBoletoDuplicado(String boleto, Integer tipoComprobante)throws Exception{
		//Comentado: Ya no es necesario dado que los corretaltivos proviene de un sequenciador - 22/01/2024 - jabanto
//		int existeBoleto = getVentaPasajesDAO().validarNumeroBoleto(boleto, tipoComprobante);
//		if(existeBoleto>0)
//			return true;
		return false;
	}

	/*
	 * (non-Javadoc)
	 * @see com.tepsa.sisvyr.service.business.VentaPasajesManager#buscarVentasForMapaBus(java.lang.Long)
	 */
	@Override
	public List<VentaPasaje> buscarVentasForMapaBus(Long idItinerario)throws Exception{
		return getVentaPasajesDAO().buscarVentasForMapaBus(idItinerario);
	}

	/*
	 * (non-Javadoc)
	 * @see com.tepsa.sisvyr.service.business.VentaPasajesManager#buscarVentaById(java.lang.Long)
	 */
	@Override
	public VentaPasaje buscarVentaById(Long idVenta)throws Exception{
		return getVentaPasajesDAO().buscarVentaById(idVenta);
	}

	/*
	 * (non-Javadoc)
	 * @see com.tepsa.sisvyr.service.business.VentaPasajesManager#getDateSystem()
	 */
    @Override
	public String getDateSystem()throws Exception{
    	return getVentaPasajesDAO().getDateSystem();
    }

	/*
	 * (non-Javadoc)
	 * @see com.tepsa.sisvyr.service.business.VentaPasajesManager#buscarVentasByIdVenta(java.lang.Long)
	 */
	@Override
	public VentaPasaje buscarVentasByIdVenta(Long idVenta)throws Exception{
		return getVentaPasajesDAO().buscarVentasByIdVenta(idVenta);
	}

	/*
	 * (non-Javadoc)
	 * @see com.tepsa.sisvyr.service.business.VentaPasajesManager#buscarReservasPorConfirmar(java.lang.Integer, java.lang.Integer, java.lang.String[], java.lang.String, java.lang.String, java.lang.String, java.lang.Integer)
	 */
	@Override
	public List<VentaPasaje> buscarReservasPorConfirmar(Integer idOrigen, Integer idDestino, String[] pasajero, String numeroDocumento, String numeroBoleto, String fechaPartida, Integer idAgencia)throws Exception{
		return getVentaPasajesDAO().buscarReservasPorConfirmar(idOrigen, idDestino, pasajero, numeroDocumento, numeroBoleto, fechaPartida, idAgencia);
	}

	/*
	 * (non-Javadoc)
	 * @see com.tepsa.sisvyr.service.business.VentaPasajesManager#buscarFechaAbiertaPorConfirmar(java.lang.Integer, java.lang.Integer, java.lang.String[], java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	public List<VentaPasaje> buscarFechaAbiertaPorConfirmar(Integer idOrigen, Integer idDestino, String[] pasajero, String numeroControl, String numeroBoleto, String numeroDocumento)throws Exception{
		return getVentaPasajesDAO().buscarFechaAbiertaPorConfirmar(idOrigen, idDestino, pasajero, numeroControl, numeroBoleto, numeroDocumento);
	}

	/*
	 * (non-Javadoc)
	 * @see com.tepsa.sisvyr.service.business.VentaPasajesManager#buscarUsuarioPorAgencia(java.lang.Integer, java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	public List<Usuario> buscarUsuarioPorAgencia(Integer idAgencia, String estado, String fechaInicio, String fechaFin, String rucCredito)throws Exception{
		return getVentaPasajesDAO().buscarUsuarioPorAgencia(idAgencia, estado, fechaInicio, fechaFin, rucCredito);
	}

	/*
	 * (non-Javadoc)
	 * @see com.tepsa.sisvyr.service.business.VentaPasajesManager#buscarDetalladoVentas(java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	public List<VentaPasaje> buscarDetalladoVentas(String idAgencia, String idUsuario, String idTipoMovimiento, String fechaInicio, String fechaFin, String estado)throws Exception{
		return getVentaPasajesDAO().buscarDetalladoVentas(idAgencia, idUsuario, idTipoMovimiento, fechaInicio, fechaFin, estado);
	}

	/*
	 * (non-Javadoc)
	 * @see com.tepsa.sisvyr.service.business.VentaPasajesManager#duplicarBoleto(com.tepsa.sisvyr.model.bean.VentaPasaje)
	 */
	@Override
	@Transactional
	public int duplicarBoleto(VentaPasaje ventaOriginal, VentaPasaje ventaDuplicado)throws Exception{
		int result = Constantes.FAILURE;
		Integer idTipoComprobante = ventaOriginal.getTipoComprobante().getId();
		Integer idUsuarioHardware = ventaDuplicado.getUsuarioHardware().getId();

		if(isBoletoDuplicado(ventaDuplicado.getNumeroBoleto(), ventaDuplicado.getTipoComprobante().getId()))
			throw new NumeroBoletoDuplicadoException();

		/*	Realizamos la anulacion del boleto original	*/
		getVentaPasajesDAO().update(ventaOriginal);

		/*	Guardando la instancia de la venta de pasajes	*/
		ventaDuplicado.setFechaInsercion(Constantes.FORMAT_DATE_TIME_24H.parse(getDateSystem()));
		getVentaPasajesDAO().save(ventaDuplicado);


		/*	Recuperando el Voucher o Recibo de Caja que genero el boleto	*/
		TreeMap<String, Object> criteriosBusqueda = new TreeMap<>();
		criteriosBusqueda.put("ventaPasaje.id", ventaOriginal.getId());
		List<VentaPasaje> lstVenta = getVentaPasajesDAO().buscarPorX(criteriosBusqueda, null);
		if(lstVenta.size()>0){
			lstVenta.get(0).setVentaPasaje(ventaDuplicado);
			lstVenta.get(0).setNumeroBoletoAnterior(ventaDuplicado.getNumeroBoleto());
			getVentaPasajesDAO().update(lstVenta.get(0));
		}

		/*Valida si es un credito - jabanto 15/08/2015 ###############*/
		if(ventaDuplicado.getFormaPago().getId().intValue()==Constantes.ID_FORPAG_CREDITO){
			/*Buscando el voucher, para actualizar el identificador referencia asi como el el cambio numero boleto anteriori*/
			VentaPasaje voucher=getVentaPasajesDAO().buscarPorId(ventaDuplicado.getVentaOriginal());
			voucher.setVentaPasaje(ventaDuplicado);
			voucher.setNumeroBoletoAnterior(ventaDuplicado.getNumeroBoleto());
			getVentaPasajesDAO().update(voucher);
		}

		/*	Actualizando el correlativo de las especies valoradas	*/
		int position = ventaDuplicado.getNumeroBoleto().indexOf("-");
		String serie = ventaDuplicado.getNumeroBoleto().substring(0, position);
		Long correlativo = Long.valueOf(ventaDuplicado.getNumeroBoleto().substring(position+1))+1;
		getControlEspecieValoradaDAO().actualizarCorrelativoEspecieValorada(idTipoComprobante, idUsuarioHardware, serie, correlativo);

		/*	Generando el numero de control	*/
		String nControl = Util.generateControlNumber(Util.decimalToHexadecimal(ventaDuplicado.getId()));
		ventaDuplicado.setNumeroControl(nControl);
		/*	Actualizando el numero de control a la venta realizada	*/
		getVentaPasajesDAO().update(ventaDuplicado);
		result = Constantes.CORRECT;
		return result;
	}

	/*
	 * (non-Javadoc)
	 * @see com.tepsa.sisvyr.service.business.VentaPasajesManager#anularMovimiento(com.tepsa.sisvyr.model.bean.VentaPasaje, boolean)
	 */
	@Override
	@Transactional
	public VentaPasaje anularMovimiento(VentaPasaje movimiento,boolean forzarNotaCredito, boolean ejecutarSeqByCorrelativo)throws Exception{
//		int result = Constantes.FAILURE;
		VentaPasaje notaCredito=null;
		if(movimiento.getTipoComprobante().getId().intValue()==Constantes.ID_TIPCOM_BOLETO_VIAJE ||
				movimiento.getTipoComprobante().getId().intValue()==Constantes.ID_TIPCOM_RECIBO_CAJA ||
				movimiento.getTipoComprobante().getId().intValue()==Constantes.ID_TIPCOM_VOUCHER_AGENCIA_VIAJES ||
				movimiento.getTipoComprobante().getId().intValue()==Constantes.ID_TIPCOM_VOUCHER_CORPORATIVO){

			/*Boletos y recibos de caja y/o vouchers*/
			getVentaPasajesDAO().update(movimiento);
		}else{
			/*Valida que la anulacion este dentro de las 72 horas, desde el dia siguiente a la emision*/
			Integer horas_maximo=Constantes.HORAS_MAXIMO_ANULACION;
			Date dateStartLimit= new Date(movimiento.getFechaLiquidacion().getTime()+Constantes.MILISEGUNDOS_X_DIA);
			long horasTrans= (new Date().getTime()-dateStartLimit.getTime())/Constantes.MILISEGUNDOS_X_HORA;

			if(!forzarNotaCredito && (horasTrans<=horas_maximo || 
									  movimiento.getTipoComprobante().getId().intValue()==Constantes.ID_TIPCOM_NOTA_CREDITO ||
									  movimiento.getTipoComprobante().getId().intValue()==Constantes.ID_TIPCOM_NOTA_DEBITO)){
				/*Realiza solamente la anulacion de la nota de credito o debito*/

				//Primero anula en el WSFE
				//Comentado temporalmente por MAOE para pruebas sin Fact Electronica
				//MAOE 05/02/2024
				Result result=WSFE.anularComprobante(movimiento);
				if(result.isIsCorrect()) {
					getVentaPasajesDAO().update(movimiento);
				
					//Insertar el tracking de anulacion

					/*Activa el comprobante asociado a la nota de credito o debito*/
//					if(movimiento.getTipoComprobante().getId().intValue()==Constantes.ID_TIPCOM_NOTA_CREDITO ||
//							movimiento.getTipoComprobante().getId().intValue()==Constantes.ID_TIPCOM_NOTA_DEBITO){
//					}
				}
			
			//MAOE 05/02/2024
				else
					throw new Exception("No se pudo realizar la anulacion, por favor vuelva a intentarlo. (F.E.)");
			}else{
				/*Este debe ser anulado, pero con una nota de credito*/
				TipoNota tipoNota=ServiceLocator.getTipoNotaManager().buscarPorId((long)Constantes.ID_TIPNOTA_ANULACION);
				tipoNota.setMovimiento(movimiento.getObservaciones());
				notaCredito = generarNotaCredito(movimiento, tipoNota, true, false, ejecutarSeqByCorrelativo);
			}
		}
		if (movimiento.getFormaPago().getId().intValue()==Constantes.ID_FORPAG_CORTESIA &&
				movimiento.getTipoFormaPago().getId().intValue()==Constantes.ID_TIPFORPAG_PUNTOS){

			//Restaura los puntos utilizados para la cortesia por puntos
			ServiceLocator.getPuntosPasajeroFrecuenteManager().restaurarPuntos(movimiento.getVentaOriginal());

		}else if(movimiento.getFormaPago().getId().intValue()!=Constantes.ID_FORPAG_CORTESIA){
			//Realiza la anulacion de los puntos del pax fre
			anularPuntosPaxFree(movimiento);
		}

		//Cuando la Anulacion es de un boleto prepagado
		if(movimiento.getTipoComprobante().getId().intValue()==Constantes.ID_TIPCOM_BOLETO_VIAJE) {
			if(movimiento.getVentaOriginal()!=null){
				VentaPasaje reciboCaja=getVentaPasajesDAO().buscarPorId(movimiento.getVentaOriginal());
				if(reciboCaja.getTipoComprobante().getId().intValue()==Constantes.ID_TIPCOM_RECIBO_CAJA){
					getVentaPasajesDAO().activarReciboCaja(reciboCaja.getId());
				}
			}
		}
		

//		result = Constantes.CORRECT;
		return notaCredito;
	}



	/*###End Begin 09/11/2016 - jabanto*/
//	/*
//	 * (non-Javadoc)
//	 * @see com.tepsa.sisvyr.service.business.VentaPasajesManager#anularMovimiento(com.tepsa.sisvyr.model.bean.VentaPasaje)
//	 */
//	@Transactional
//	public int anularMovimiento(VentaPasaje movimiento)throws Exception{
//		int result = Constantes.FAILURE;
//
		/*###End Begin 09/11/2016 - jabanto*/
//		List<VentaPasaje> lstVentaPasajes = new ArrayList<VentaPasaje>();
//		List<VentaPasaje> lstVentasAnular = new ArrayList<VentaPasaje>();
		/*	Verificando si se trata de una venta ida y vuelta	*/
//		if(movimiento.getIdentificadorIdaRetorno()!=null){
//			TreeMap<String, Object> criteriosBusqueda = new TreeMap<String, Object>();
//			criteriosBusqueda.put("identificadorIdaRetorno", movimiento.getIdentificadorIdaRetorno());
//			List<String> criteriosOrdenar = new ArrayList<String>();
//			criteriosOrdenar.add("id");
//			lstVentaPasajes = getVentaPasajesDAO().buscarPorX(criteriosBusqueda, criteriosOrdenar);
//			VentaPasaje ventaIDA = null;
//			VentaPasaje ventaRETORNO = null;
//			if(lstVentaPasajes.size()==2){
//				ventaIDA = lstVentaPasajes.get(0);
//				ventaRETORNO = lstVentaPasajes.get(1);
//				if(ventaIDA.getIdentificadorIdaRetorno().longValue()==movimiento.getId().longValue()){
//					lstVentasAnular.add(movimiento);
//					ventaRETORNO.setTarifa(movimiento.getTarifa());
//					ventaRETORNO.setRecargo(movimiento.getRecargo());
//					ventaRETORNO.setDescuento(movimiento.getDescuento());
//					ventaRETORNO.setImportePagado(movimiento.getImportePagado());
//					ventaRETORNO.setAcuenta(movimiento.getAcuenta());
//					ventaRETORNO.setImportePagado(movimiento.getImportePagado());
//					ventaRETORNO.setTipoMovimiento(movimiento.getTipoMovimiento());
//					ventaRETORNO.setUsuarioModificacion(movimiento.getUsuarioModificacion());
//					ventaRETORNO.setIpModificacion(movimiento.getIpModificacion());
//					lstVentasAnular.add(ventaRETORNO);
//					getVentaPasajesDAO().update(movimiento);
//					getVentaPasajesDAO().update(ventaRETORNO);
//				}else{
//					ventaIDA.setTarifa(movimiento.getTarifa());
//					ventaIDA.setRecargo(movimiento.getRecargo());
//					ventaIDA.setDescuento(movimiento.getDescuento());
//					ventaIDA.setImportePagado(movimiento.getImportePagado());
//					ventaIDA.setAcuenta(movimiento.getAcuenta());
//					ventaIDA.setImportePagado(movimiento.getImportePagado());
//					ventaIDA.setTipoMovimiento(movimiento.getTipoMovimiento());
//					ventaIDA.setUsuarioModificacion(movimiento.getUsuarioModificacion());
//					ventaIDA.setIpModificacion(movimiento.getIpModificacion());
//					lstVentasAnular.add(ventaIDA);
//					lstVentasAnular.add(movimiento);
//					getVentaPasajesDAO().update(ventaIDA);
//					getVentaPasajesDAO().update(movimiento);
//				}
//			}
//		}else{
//			lstVentasAnular.add(movimiento);
//			getVentaPasajesDAO().update(movimiento);
//		}

		//Cuando es una anulaciï¿½n de una ida y vuelta.
//		if(lstVentaPasajes.size()>0){
//			for(VentaPasaje ventaAnular : lstVentaPasajes){
//				movimiento = ventaAnular;
//
//				if(movimiento.getTipoTransaccion().equals(Constantes.TIPO_OPERACION_VENTA) &&
//						movimiento.getFormaPago().getId().intValue()!=Constantes.ID_FORPAG_CORTESIA){
//
//					anularPuntosPaxFree(movimiento);//Anula los puntos asiciados a la venta, si es que los tuviese
//
//					if(movimiento.getFechaPartida()!=null){
//						Date fechaServidor = new Date();
//						Date fechaPartida = Util.StringtoDate(Util.DatetoString(movimiento.getFechaPartida(), Constantes.DATE_FORMAT)+" "+movimiento.getHoraPartida()+":00", Constantes.DATE_TIME_FORMAT);
//						Date fechaLimite = new Date(fechaPartida.getTime()-(Constantes.MILISEGUNDOS_X_HORA*6));
//						if(Util.comparaFechas(fechaServidor, fechaLimite, Util.OPER_MAYOR))
//							enviarEmail();
//
//					}
//				}
//			}
//		}else if (movimiento.getFormaPago().getId().intValue()==Constantes.ID_FORPAG_CORTESIA &&
//				movimiento.getTipoFormaPago().getId().intValue()==Constantes.ID_TIPFORPAG_PUNTOS){
//
//			//Restaura los puntos utilizados para la cortesia por puntos
//			ServiceLocator.getPuntosPasajeroFrecuenteManager().restaurarPuntos(movimiento.getVentaOriginal());
//
//		}else if(movimiento.getFormaPago().getId().intValue()!=Constantes.ID_FORPAG_CORTESIA){
//			//Cunado es silo IDA y diferente a la forma de pago cortesia.
//			anularPuntosPaxFree(movimiento);
//
//		}
//
//
//		//Cuando la Anulacion es de un boleto prepagado
//		if(movimiento.getTipoComprobante().getId().intValue()==Constantes.ID_TIPCOM_BOLETO_VIAJE) {
//			if(movimiento.getVentaOriginal()!=null){
//				VentaPasaje reciboCaja=getVentaPasajesDAO().buscarPorId(movimiento.getVentaOriginal());
//				if(reciboCaja.getTipoComprobante().getId().intValue()==Constantes.ID_TIPCOM_RECIBO_CAJA){
//					getVentaPasajesDAO().activarReciboCaja(reciboCaja.getId());
//				}
//			}
//		}


		/*Valida si el boleto ya fue transferido al titan*/
//
//
//
//
//		result = Constantes.CORRECT;
//		return result;
//	}

	/**
	 * Realiza la anulaciï¿½n de los puntos asociados a una venta.
	 * @param movimiento	: Objeto VentaPasaje
	 * @throws Exception
	 */
	private void anularPuntosPaxFree(VentaPasaje movimiento) throws Exception{
		/*Obtiene el ID de la venta ha validar los puntos asociados*/
		Long idVenta=null;
		if(movimiento.getVentaOriginal()!=null){
			VentaPasaje ventaOriginal=getVentaPasajesDAO().buscarPorId(movimiento.getVentaOriginal());

			if(ventaOriginal.getTipoComprobante().getId().equals(Constantes.ID_TIPCOM_BOLETO_VIAJE) ||
					ventaOriginal.getTipoComprobante().getId().equals(Constantes.ID_TIPCOM_BOLETA_VENTA) ||
					ventaOriginal.getTipoComprobante().getId().equals(Constantes.ID_TIPCOM_FACTURA) ){
				idVenta=ventaOriginal.getId();
			}else{
				//Si es un voucher
				if(ventaOriginal.getVentaPasaje()!=null)//Si  tiener el id que hace referencia al boleto asociado al voucher
					idVenta=ventaOriginal.getVentaPasaje().getId();
				else
					idVenta=ventaOriginal.getId();
			}

			/*busca si el movimiento tiene puntos asociados  */
			if(idVenta!=null){
				PuntosPasajeroFrecuente puntosPasajeroFrecuente= ServiceLocator.getPuntosPasajeroFrecuenteManager().buscarXIDVenta(idVenta);
				if(puntosPasajeroFrecuente!=null){
					/* Anula los puntos del paxfree obtenidos por la venta que se esta anulando. */
					puntosPasajeroFrecuente.setFechaAnulacion(Constantes.FORMAT_DATE_TIME_24H.parse(new MyTime().dateServer()));
					Usuario usuario= new Usuario();
					usuario.setLogin(movimiento.getUsuarioModificacion());
					UtilData.auditarRegistro(puntosPasajeroFrecuente, true, usuario, Executions.getCurrent());
					ServiceLocator.getPuntosPasajeroFrecuenteManager().actualizar(puntosPasajeroFrecuente);
				}
			}
		}
	}


//	private void enviarEmail(){
//
//	}

	/*
	 * (non-Javadoc)
	 * @see com.tepsa.sisvyr.service.business.VentaPasajesManager#confirmarFechaAbierta(com.tepsa.sisvyr.model.bean.VentaPasaje)
	 */
	@Override
	@Transactional
	public VentaPasaje confirmarFechaAbierta(VentaPasaje ventaPasaje, TipoNota tipoNotaCredito, boolean ejecutarSeqByCorrelativo)throws Exception{
//		int result = Constantes.FAILURE;
		VentaPasaje notaCredito=null;
		boolean isNewComprobante = (ventaPasaje.getId()==null);
		try{
			/*	Validando que no se haya cambiado el tipo de Bus durante la venta	*/
			boolean excedeCapacidad = false;
			List<Object> lstCapacidad = getItinerarioDAO().validateCapacity(ventaPasaje.getItinerario().getId(), ventaPasaje.getNumeroAsiento(), ventaPasaje.getNumeroPiso());
			if(lstCapacidad.size()>0){
				excedeCapacidad = (Boolean)lstCapacidad.get(0);
				if(excedeCapacidad)
					throw new CapacityExceedsException((String)lstCapacidad.get(1));
			}

			/*	Validando que el asiento no haya sido utilizado antes de la venta	*/
			Long ocupabilidad = getVentaPasajesDAO().validateSeat(ventaPasaje.getItinerario(), ventaPasaje.getRuta(), ventaPasaje.getNumeroAsiento(), ventaPasaje.getNumeroPiso());
			if(ocupabilidad.longValue()>0){
				if(ventaPasaje.getVentaPasaje()!=null && ventaPasaje.getVentaPasaje().getId().longValue()!=ocupabilidad.longValue())
					throw new DuplicateSeatException();
				else if(ventaPasaje.getVentaPasaje()==null)
					throw new DuplicateSeatException();
			}

			/*	Validando que no haya expirado el tiempo del bloqueo	*/
			TreeMap<String, Object> criteriosBusqueda = new TreeMap<>();
			criteriosBusqueda.put("itinerario.id", ventaPasaje.getItinerario().getId());
			criteriosBusqueda.put("ruta.id", ventaPasaje.getRuta().getId());
			criteriosBusqueda.put("usuarioHardware.id", ventaPasaje.getUsuarioHardware().getId());
			criteriosBusqueda.put("usuario.id", ventaPasaje.getUsuario().getId());
			criteriosBusqueda.put("numeroAsiento", ventaPasaje.getNumeroAsiento());
			criteriosBusqueda.put("numeroPiso", ventaPasaje.getNumeroPiso());
			criteriosBusqueda.put("fechaPartida", Util.DatetoString(ventaPasaje.getFechaPartida(), Constantes.DATE_FORMAT));
			criteriosBusqueda.put("horaPartida", ventaPasaje.getHoraPartida());
			List<TmpOcupacionAsientos> lstTMP = getTmpOcupacionAsientosDAO().buscarPorX(criteriosBusqueda, null);
			if(lstTMP.size()==0)
				throw new TiempoExpiracionBloqueoException();


			/*	Si el monto a pagar es mayor a cero validar el nuevo boleto.*/
//			if(ventaPasaje.getImportePagado() > 0.0 && tipoNotaCredito!=null){
			
			//Valida si debo o no emitir un nuevo comprobante
			if(isNewComprobante) {
				/*Anular el boleto F.A. con nota de credito y emitir uno nuevo con la nueva tarifa*/
//			 	notaCredito = generarNotaCredito(ventaPasaje.getVentaPasaje(), tipoNotaCredito,true, false);
				
				
				
				/*Genera una devoluciï¿½n del boleto original - jabanto - 26/09/2022 */				
				VentaPasaje ventaOriginal = getVentaPasajesDAO().buscarPorId(ventaPasaje.getVentaPasaje().getId());
				VentaPasaje ventaDevolucion = (VentaPasaje)ventaOriginal.clone();
				ventaDevolucion.setId(null);
				ventaDevolucion.setFechaLiquidacion(ventaPasaje.getFechaLiquidacion());
				ventaDevolucion.setAgencia(ventaPasaje.getAgencia());
				ventaDevolucion.setUsuario(ventaPasaje.getUsuario());
				ventaDevolucion.setCanalVenta(ventaOriginal.getCanalVenta());
				ventaDevolucion.setTipoMovimiento(new TipoMovimiento(Constantes.ID_TIPMOV_ANULACION_SISTEMA));
				ventaDevolucion.setObservaciones(ventaPasaje.getVentaPasaje().getObservaciones()!=null?ventaPasaje.getVentaPasaje().getObservaciones():null);
				UtilData.auditarRegistro(ventaDevolucion, ventaPasaje.getUsuario(), Executions.getCurrent());
				getVentaPasajesDAO().save(ventaDevolucion);

				/**Begin 25/10/2016 - jabanto**/
				/*Vuelve a realizar la busqueda del correlativo y lo actualiza, a exception de los boletos, ya que no son necesarios pues son manuales*/

			 	/*BEGIN 16/06/2021 - javalos - Correlativo by caja*/
//			 	EspecieValorada especieValorada=null;
			 	ControlEspecieValorada controlEspecieValorada = null;
			 	if(ventaPasaje.getTipoComprobante().getId().intValue()!=Constantes.ID_TIPCOM_BOLETO_VIAJE){
//			 		especieValorada=UtilData.buscarEspecieValorada(ventaPasaje.getTipoComprobante().getId(), ventaPasaje.getAgencia(), true);
//					ventaPasaje.setNumeroBoleto(especieValorada.toString());
			 		controlEspecieValorada = UtilData.buscarEspecieValoradaByCaja(ventaPasaje.getTipoComprobante().getId(), ventaPasaje.getAgencia(), ejecutarSeqByCorrelativo, ventaPasaje.getUsuarioHardware(), null);
					ventaPasaje.setNumeroBoleto(controlEspecieValorada.toString());
				}
			 	/*END 16/06/2021 - javalos - Correlativo by caja*/

				/*	Validando que el boleto no exista en la DB	*/
				if(isBoletoDuplicado(ventaPasaje.getNumeroBoleto(), ventaPasaje.getTipoComprobante().getId()))
					throw new NumeroBoletoDuplicadoException();

				/*Actualiza el correlativo 25/10/2016 - jabanto*/
				if(ventaPasaje.getTipoComprobante().getId().intValue()!=Constantes.ID_TIPCOM_BOLETO_VIAJE){
					int position = ventaPasaje.getNumeroBoleto().indexOf("-");
					Long correlativo = Long.valueOf(ventaPasaje.getNumeroBoleto().substring(position+1))+1;
					/*BEGIN 16/06/2021 - javalos - Correlativo by caja*/
//					especieValorada.setCorrelativoActual(correlativo);
//					getEspecieValoradaDAO().update(especieValorada);
					controlEspecieValorada.setCorrelativoActual(correlativo);
					getControlEspecieValoradaDAO().update(controlEspecieValorada);
					/*END 16/06/2021 - javalos - Correlativo by caja*/
				}
			}else {
				copyHistoryVentaPasaje(ventaPasaje.getId());
			}

			/*	Guardando la instancia de la venta de pasajes	*/
			ventaPasaje.setAcuenta(0.0);
			if(isNewComprobante)
				getVentaPasajesDAO().save(ventaPasaje);
			else {
				VentaPasaje ventaPasajeOrg = buscarPorId(ventaPasaje.getId());
				ventaPasaje.setId(ventaPasajeOrg.getId());
				ventaPasaje.setFechaInsercion(ventaPasajeOrg.getFechaInsercion()!=null?ventaPasajeOrg.getFechaInsercion():new Date());
				ventaPasaje.setUsuarioInsercion(ventaPasajeOrg.getUsuarioInsercion());
				ventaPasaje.setIpInsercion(ventaPasajeOrg.getIpInsercion());
				ventaPasaje.setNumeroBoleto(ventaPasajeOrg.getNumeroBoleto());
				ventaPasaje.setNumeroBoletoAnterior(ventaPasajeOrg.getNumeroBoletoAnterior()!=null?ventaPasajeOrg.getNumeroBoletoAnterior():null);
				ventaPasaje.setNumeroControl(ventaPasajeOrg.getNumeroControl());
				ventaPasaje.setFechaTransferencia(ventaPasajeOrg.getFechaTransferencia()!=null?ventaPasajeOrg.getFechaTransferencia():null);
				ventaPasaje.setFechaEnvioSFE(ventaPasajeOrg.getFechaEnvioSFE()!=null?ventaPasajeOrg.getFechaEnvioSFE():null);
				ventaPasaje.setEnviadoSFE(ventaPasajeOrg.getEnviadoSFE()!=null?ventaPasajeOrg.getEnviadoSFE():null);
				ventaPasaje.setUsuario(ventaPasajeOrg.getUsuario());
				ventaPasaje.setFechaLiquidacion(ventaPasajeOrg.getFechaLiquidacion());
				ventaPasaje.setAgencia(ventaPasajeOrg.getAgencia());
				ventaPasaje.setLiquidacion(ventaPasajeOrg.getLiquidacion()!=null?ventaPasajeOrg.getLiquidacion():null);
				ventaPasaje.setFormaPago(ventaPasajeOrg.getFormaPago());
				ventaPasaje.setTipoFormaPago(ventaPasajeOrg.getTipoFormaPago());
				ventaPasaje.setTarjetaCredito(ventaPasajeOrg.getTarjetaCredito()!=null?ventaPasajeOrg.getTarjetaCredito():null);
				
				getVentaPasajesDAO().update(ventaPasaje);
			}
			/*Valida si debe generar un nuevo numero de control*/
//			if(ventaPasaje.getImportePagado()>0.00){
			if(isNewComprobante){
				/*	Generando el numero de control de la nota	*/
				String nControl = Util.generateControlNumber(Util.decimalToHexadecimal(ventaPasaje.getId()));
				ventaPasaje.setNumeroControl(nControl);
				/*	Actualizando el numero de control a la nota	*/
				ventaPasaje.setFechaInsercion(Util.StringtoDate(getVentaPasajesDAO().getDateSystem(), Constantes.DATE_TIME_FORMAT));
				getVentaPasajesDAO().update(ventaPasaje);
			}

			/*	Eliminando el asiento de la tabla temporal	*/
			TmpOcupacionAsientos tmp = new TmpOcupacionAsientos();
			tmp.setRuta(ventaPasaje.getRuta());
			tmp.setItinerario(ventaPasaje.getItinerario());
			tmp.setNumeroAsiento(ventaPasaje.getNumeroAsiento());
			tmp.setNumeroPiso(ventaPasaje.getNumeroPiso());
			getTmpOcupacionAsientosDAO().desbloquearAsiento(tmp);

//			result = Constantes.CORRECT;
		}catch(CapacityExceedsException ceex){
			throw new CapacityExceedsException();
		}catch(DuplicateSeatException dsex){
			throw new DuplicateSeatException();
		}catch(NumeroBoletoDuplicadoException nbdex){
			throw new NumeroBoletoDuplicadoException();
		}catch(TiempoExpiracionBloqueoException tebex){
			throw new TiempoExpiracionBloqueoException();
		}catch(Exception ex){
			ex.printStackTrace();
			throw new Exception(ex);
		}
		return notaCredito;
	}

	/*
	 * (non-Javadoc)
	 * @see com.tepsa.sisvyr.service.business.VentaPasajesManager#buscarVentasPostergar(java.lang.Integer, java.lang.Integer, java.lang.String[], java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	public List<VentaPasaje> buscarVentasPostergar(Integer idOrigen, Integer idDestino, String[] pasajero, String numeroControl, String numeroBoleto, String fechaPartida)throws Exception{
		return getVentaPasajesDAO().buscarVentasPostergar(idOrigen, idDestino, pasajero, numeroControl, numeroBoleto, fechaPartida);
	}

	/*
	 * (non-Javadoc)
	 * @see com.tepsa.sisvyr.service.business.VentaPasajesManager#postergarBoleto(com.tepsa.sisvyr.model.bean.VentaPasaje)
	 */
	@Override
	@Transactional
	public VentaPasaje postergarBoleto(VentaPasaje boletoPostergar,Boolean validaBloqueo, VentaPasaje gastoAdministrativo, boolean ejecutarSeqByCorrelativo)throws Exception{
		try{
//			int result = Constantes.FAILURE;
			/*End Begin 04/11/2016 - jabanto*/
//			Integer idTipoComprobante = boletoPostergar.getTipoComprobante().getId();
//			Integer idUsuarioHardware = boletoPostergar.getUsuarioHardware().getId();

			
			if(boletoPostergar.getFechaPartida()!=null){
				/*	Validando que no se haya cambiado el tipo de Bus durante la venta	*/
				boolean excedeCapacidad = false;
				List<Object> lstCapacidad = getItinerarioDAO().validateCapacity(boletoPostergar.getItinerario().getId(), boletoPostergar.getNumeroAsiento(), boletoPostergar.getNumeroPiso());
				if(lstCapacidad.size()>0){
					excedeCapacidad = (Boolean)lstCapacidad.get(0);
					if(excedeCapacidad)
						throw new CapacityExceedsException((String)lstCapacidad.get(1));
				}

				/*	Validando que el asiento no haya sido utilizado antes de la venta	*/
				Long ocupabilidad = getVentaPasajesDAO().validateSeat(boletoPostergar.getItinerario(), boletoPostergar.getRuta(), boletoPostergar.getNumeroAsiento(), boletoPostergar.getNumeroPiso());
				if(ocupabilidad.longValue()>0){
					if(boletoPostergar.getVentaPasaje()!=null && boletoPostergar.getVentaPasaje().getId().longValue()!=ocupabilidad.longValue())
						throw new DuplicateSeatException();
					else if(boletoPostergar.getVentaPasaje()==null)
						throw new DuplicateSeatException();
				}
			}

			/*End Begin 04/11/2016 - jabanto*/
//			/*	validando que el boleto no este duplicado*/
//			if(isBoletoDuplicado(boletoPostergar.getNumeroBoleto(), boletoPostergar.getTipoComprobante().getId()))
//				throw new NumeroBoletoDuplicadoException();



			if(validaBloqueo &&  boletoPostergar.getNumeroAsiento()!=null){
				/*	Validando que no haya expirado el tiempo del bloqueo	*/
				TreeMap<String, Object> criteriosBusqueda = new TreeMap<>();
				criteriosBusqueda.put("itinerario.id", boletoPostergar.getItinerario().getId());
				criteriosBusqueda.put("ruta.id", boletoPostergar.getRuta().getId());
				criteriosBusqueda.put("usuarioHardware.id", boletoPostergar.getUsuarioHardware().getId());
				criteriosBusqueda.put("usuario.id", boletoPostergar.getUsuario().getId());
				criteriosBusqueda.put("numeroAsiento", boletoPostergar.getNumeroAsiento());
				criteriosBusqueda.put("numeroPiso", boletoPostergar.getNumeroPiso());
				criteriosBusqueda.put("fechaPartida", Util.DatetoString(boletoPostergar.getFechaPartida(), Constantes.DATE_FORMAT));
				criteriosBusqueda.put("horaPartida", boletoPostergar.getHoraPartida());
				List<TmpOcupacionAsientos> lstTMP = getTmpOcupacionAsientosDAO().buscarPorX(criteriosBusqueda, null);
				if(lstTMP.size()==0)
					throw new TiempoExpiracionBloqueoException();
			}

			/**Clonamos el boleto original - 10/11/2016 - jabanto*/
			VentaPasaje boletoOriginal = (VentaPasaje)boletoPostergar.getVentaPasaje().clone();
			if(boletoOriginal.getCliente()!=null)
				boletoOriginal.setCliente(ServiceLocator.getClienteManager().buscarPorId(boletoOriginal.getCliente().getId()));
			
			//Valida si debe o no emitir un nuevo comprobante - jabanto - 26/09/2022
			boolean isNewComprobante = false;
			boolean isCambioComprobante = false;
//			if(boletoOriginal.getImportePagado().doubleValue() < boletoPostergar.getImportePagado().doubleValue()){
			if(boletoPostergar.getImportePagado()>0){
				isNewComprobante = true;
				Double direfenciaImporte = boletoPostergar.getImportePagado(); //(boletoPostergar.getImportePagado() - boletoOriginal.getImportePagado());
				boletoPostergar.setImportePagado(direfenciaImporte);
			}else if (boletoOriginal.getTipoComprobante().getId().intValue() != boletoPostergar.getTipoComprobante().getId().intValue() || // cuando es un cambio de comprobante
					 (boletoOriginal.getCliente()!=null && boletoPostergar.getCliente()!=null && boletoOriginal.getCliente().getId().longValue()!=boletoPostergar.getCliente().getId()) || // Cambio de ruc
					 (boletoOriginal.getCliente()!=null && boletoPostergar.getCliente()!=null && !(boletoOriginal.getCliente().getRazonSocial().equals(boletoPostergar.getCliente().getRazonSocial()))) || // Cambio de razon social
					 (boletoOriginal.getCliente()!=null && boletoPostergar.getCliente()!=null && !(boletoOriginal.getCliente().getDireccion().equals(boletoPostergar.getCliente().getDireccion()))) ) // Cambio de direccion fiscal					
			{
				//Cuando es cambio de tipo de comprobante y/o cambio de datos del cliente(si es factura)
				isNewComprobante = true;
				isCambioComprobante = true;
			}
			boolean emitirNotaCredito=false;
			
			if(isNewComprobante) {
				boletoOriginal.setId(null);
				int canalVanetaId=boletoOriginal.getCanalVenta().getId();
				
				/*Realiza una devolucion si es una Boleto de Viaje o si este fue emitido por un canal de Agencia de Viajes o Web - 15/12/2016 - jabanto*/
				if(boletoOriginal.getTipoComprobante().getId().intValue()==Constantes.ID_TIPCOM_BOLETO_VIAJE
						|| canalVanetaId==Constantes.ID_CANVEN_AGENCIA_VIAJES
						|| canalVanetaId==Constantes.ID_CANVEN_WEB
						|| isCambioComprobante ){
					boletoOriginal.setPenalidad(0.00);
					boletoOriginal.setImportePagadoEfectivo(0.0);
					boletoOriginal.setImportePagadoTarjeta(0.0);
					boletoOriginal.setUsuario(boletoPostergar.getUsuario());
					boletoOriginal.setAgencia(boletoPostergar.getAgencia());
					boletoOriginal.setTipoMovimiento(new TipoMovimiento(Constantes.ID_TIPMOV_ANULACION_SISTEMA));
					boletoOriginal.setEstadoRegistro(Constantes.VALUE_ACTIVO);
					boletoOriginal.setObservaciones("==>DEV. X SISTEMA - X EDICION VENTA "+(isCambioComprobante?"- - CAMBIO TIPO COMPROBANTE ":"")+"<===");
					boletoOriginal.setFechaLiquidacion(boletoPostergar.getFechaLiquidacion());
					boletoOriginal.setUsuarioInsercion(boletoPostergar.getUsuarioInsercion());
					boletoOriginal.setIpInsercion(boletoPostergar.getIpInsercion());
					boletoOriginal.setUsuarioModificacion(boletoPostergar.getUsuarioModificacion());
					boletoOriginal.setIpModificacion(boletoPostergar.getIpModificacion());
					boletoOriginal.setLiquidacion(null);
					boletoOriginal.setFechaTransferencia(null);					
				}else{
					boletoOriginal.setTipoMovimiento(new TipoMovimiento(Constantes.ID_TIPMOV_ANULACION_SISTEMA));
					boletoOriginal.setUsuario(boletoPostergar.getUsuario());
					boletoOriginal.setUsuarioInsercion(boletoPostergar.getUsuarioInsercion());
					boletoOriginal.setIpInsercion(boletoPostergar.getIpInsercion());
					boletoOriginal.setUsuarioModificacion(boletoPostergar.getUsuarioModificacion());
					boletoOriginal.setIpModificacion(boletoPostergar.getIpModificacion());
					
//					emitirNotaCredito=true;
				}
				/* Anulando/devuleve el movimiento anterior	*/
				getVentaPasajesDAO().save(boletoOriginal);
			}

			/*Generando la Nota de credito si el boleto original no es boleto de viaje - 04/11/2016 - jabanto*/
			VentaPasaje notaCredito=null;
			if(emitirNotaCredito)
				notaCredito = generarNotaCredito(boletoPostergar.getVentaPasaje(), boletoPostergar.getTipoNota(), false, false, ejecutarSeqByCorrelativo);

			/**Begin 04/11/2016 - jabanto**/
			/*Vuelve a realizar la busqueda del correlativo y lo actualiza, a exception de los boletos, ya que no son necesarios pues son manuales*/
			/*BEGIN 15/06/2021 - javalos - Correlativo by caja*/
//			EspecieValorada especieValorada=null;
			ControlEspecieValorada controlEspecieValorada = null;
			/*END 15/06/2021 - javalos - Correlativo by caja*/
			if(boletoPostergar.getTipoComprobante().getId().intValue()!=Constantes.ID_TIPCOM_BOLETO_VIAJE && isNewComprobante){
				/*BEGIN 15/06/2021 - javalos - Correlativo by caja*/
//				especieValorada=UtilData.buscarEspecieValorada(boletoPostergar.getTipoComprobante().getId(), boletoPostergar.getAgencia(), true);
//				boletoPostergar.setNumeroBoleto(especieValorada.toString());
				controlEspecieValorada = UtilData.buscarEspecieValoradaByCaja(boletoPostergar.getTipoComprobante().getId(), boletoPostergar.getAgencia(), ejecutarSeqByCorrelativo, boletoPostergar.getUsuarioHardware(), null);
				boletoPostergar.setNumeroBoleto(controlEspecieValorada.toString());
				/*END 15/06/2021 - javalos - Correlativo by caja*/
			
				/*	Validando que el boleto no exista en la DB	*/
				if(isBoletoDuplicado(boletoPostergar.getNumeroBoleto(), boletoPostergar.getTipoComprobante().getId()))
					throw new NumeroBoletoDuplicadoException();
				/*Actualiza el correlativo 04/11/2016 - jabanto*/
				if(boletoPostergar.getTipoComprobante().getId().intValue()!=Constantes.ID_TIPCOM_BOLETO_VIAJE){
					int position = boletoPostergar.getNumeroBoleto().indexOf("-");
					Long correlativo = Long.valueOf(boletoPostergar.getNumeroBoleto().substring(position+1))+1;
					/*BEGIN 15/06/2021 - javalos - Correlativo by caja*/
//					especieValorada.setCorrelativoActual(correlativo);
//					getEspecieValoradaDAO().update(especieValorada);
					controlEspecieValorada.setCorrelativoActual(correlativo);
					getControlEspecieValoradaDAO().update(controlEspecieValorada);
					/*END 15/06/2021 - javalos - Correlativo by caja*/
				}
			}			
			
			
			/*	Generando el nuevo boleto	*/
			boletoPostergar.setTipoNota(null);
			if(isNewComprobante) {
				getVentaPasajesDAO().save(boletoPostergar);
			}else {
				//Realiza una copia del registro al historial
				copyHistoryVentaPasaje(boletoOriginal.getId());
				
				//Solamente actualiza los cambios realizados
				VentaPasaje oVentaPasaje = buscarVentaById(boletoOriginal.getId());
				boletoPostergar.setId(oVentaPasaje.getId());
				boletoPostergar.setFechaInsercion(oVentaPasaje.getFechaInsercion()!=null?oVentaPasaje.getFechaInsercion():new Date());
				boletoPostergar.setUsuarioInsercion(oVentaPasaje.getUsuarioInsercion());
				boletoPostergar.setIpInsercion(oVentaPasaje.getIpInsercion());
				boletoPostergar.setNumeroBoleto(oVentaPasaje.getNumeroBoleto());
				boletoPostergar.setNumeroBoletoAnterior(oVentaPasaje.getNumeroBoletoAnterior()!=null?oVentaPasaje.getNumeroBoletoAnterior():null);
				boletoPostergar.setNumeroControl(oVentaPasaje.getNumeroControl());
				boletoPostergar.setFechaTransferencia(oVentaPasaje.getFechaTransferencia()!=null?oVentaPasaje.getFechaTransferencia():null);
				boletoPostergar.setFechaEnvioSFE(oVentaPasaje.getFechaEnvioSFE()!=null?oVentaPasaje.getFechaEnvioSFE():null);
				boletoPostergar.setEnviadoSFE(oVentaPasaje.getEnviadoSFE()!=null?oVentaPasaje.getEnviadoSFE():null);
				boletoPostergar.setUsuario(oVentaPasaje.getUsuario());
				boletoPostergar.setFechaLiquidacion(oVentaPasaje.getFechaLiquidacion());
				boletoPostergar.setAgencia(oVentaPasaje.getAgencia());
				boletoPostergar.setLiquidacion(oVentaPasaje.getLiquidacion()!=null?oVentaPasaje.getLiquidacion():null);
				boletoPostergar.setFormaPago(oVentaPasaje.getFormaPago());
				boletoPostergar.setTipoFormaPago(oVentaPasaje.getTipoFormaPago());
				boletoPostergar.setTarjetaCredito(oVentaPasaje.getTarjetaCredito()!=null?oVentaPasaje.getTarjetaCredito():null);
				boletoPostergar.setCanalVenta(oVentaPasaje.getCanalVenta());
				boletoPostergar.setTarifa(oVentaPasaje.getTarifa());
				boletoPostergar.setImportePagado(oVentaPasaje.getImportePagado());
				boletoPostergar.setDescuento(oVentaPasaje.getDescuento());
				
				getVentaPasajesDAO().update(boletoPostergar);
			}
			
			//BEGIN javalos Comentado para Transmar no usan los gastos administrativos.
			/*	Generando el gasto administrativo*/
//			if(gastoAdministrativo!=null){
//				int result=generarGastoAdministrativo(gastoAdministrativo,true);
//				if(result==Constantes.FAILURE)
//					throw new Exception(Messages.getString("Generales.information.noGenerateGastoAdministrativo"));
//			}
			//END javalos

			/*Actualiza los datos del cliente por si estos hayan sido cambiados 08/11/2016 - jabanto*/
			if(boletoPostergar.getCliente()!=null){
				Cliente cliente=boletoPostergar.getCliente();
				Cliente clienteOriginal=ServiceLocator.getClienteManager().buscarPorId(boletoPostergar.getCliente().getId());
				boolean updateCliente=false;
				if(!(cliente.getRazonSocial().equals(clienteOriginal.getRazonSocial()))){
					updateCliente=true;
				}else if (cliente.getDireccion()==null && clienteOriginal.getDireccion()!=null){
					updateCliente=true;
				}else if (cliente.getDireccion()!=null && !(cliente.getDireccion().equals(clienteOriginal.getDireccion()))){
					updateCliente=true;
				}
				if(updateCliente){
					UtilData.auditarRegistro(cliente, true, boletoPostergar.getUsuario(), Executions.getCurrent());
					ServiceLocator.getClienteManager().actualizar(cliente);
				}
			}

			/* actualiza el campo VENPAS_IDREF del registro original con el Id del nuevo boleto generado 12/12/2013-jabanto*/
			if(isNewComprobante) {
				boletoPostergar.getVentaPasaje().setVentaPasaje(boletoPostergar);
				getVentaPasajesDAO().update(boletoPostergar.getVentaPasaje());	
			
				/*	Generando el numero de control	*/
				String nControl = Util.generateControlNumber(Util.decimalToHexadecimal(boletoPostergar.getId()));
				boletoPostergar.setNumeroControl(nControl);
				/*	Actualizando el numero de control a la venta realizada	*/
				boletoPostergar.setFechaInsercion(Util.StringtoDate(getVentaPasajesDAO().getDateSystem(), Constantes.DATE_TIME_FORMAT));
				getVentaPasajesDAO().update(boletoPostergar);
			}
			
			/*End Begin 04/11/2016 - jabanto*/
//			/*	Actualizando el correlativo de las especies valoradas	*/
//			int position = boletoPostergar.getNumeroBoleto().indexOf("-");
//			String serie = boletoPostergar.getNumeroBoleto().substring(0, position);
//			Long correlativo = Long.valueOf(boletoPostergar.getNumeroBoleto().substring(position+1))+1;
//			getControlEspecieValoradaDAO().actualizarCorrelativoEspecieValorada(idTipoComprobante, idUsuarioHardware, serie, correlativo);

			if(boletoPostergar.getFechaPartida()!=null)
				borrarAsientoTmpOcupacion(boletoPostergar);

			/* Valida si el DNI del pasajero es o no valido segï¿½n validacion previa con el reniec  02/06/2015*/
			Util.validarValidacionDNIReniec(boletoPostergar);

//			result = Constantes.CORRECT;
//			return result;
			return notaCredito;
		}catch(CapacityExceedsException ceex){
			throw new CapacityExceedsException();
		}catch(DuplicateSeatException dsex){
			throw new Exception(dsex);
		}catch(NumeroBoletoDuplicadoException nbdex){
			throw new NumeroBoletoDuplicadoException();
		}catch(TiempoExpiracionBloqueoException tebex){
			throw new TiempoExpiracionBloqueoException();
		}catch(Exception ex){
			ex.printStackTrace();
			throw new Exception(ex);
		}
	}

	/*
	 * (non-Javadoc)
	 * @see com.tepsa.sisvyr.service.business.VentaPasajesManager#reimprimirBoleto(com.tepsa.sisvyr.model.bean.VentaPasaje, com.tepsa.sisvyr.model.bean.VentaPasaje)
	 */
	@Override
	@Transactional
	public int reimprimirBoleto(VentaPasaje ventaOriginal, VentaPasaje ventaReimprimir, boolean ejecutarSeqByCorrelativo)throws Exception{
		int result = Constantes.FAILURE;
//		Integer idTipoComprobante = ventaReimprimir.getTipoComprobante().getId();
//		Integer idUsuarioHardware = ventaReimprimir.getUsuarioHardware().getId();

		/*###End begin 28/10/2016 - jabanto*/
//		if(isBoletoDuplicado(ventaReimprimir.getNumeroBoleto(), ventaReimprimir.getTipoComprobante().getId()))
//			throw new NumeroBoletoDuplicadoException();

		/**Begin 25/10/2016 - jabanto**/
		/*Vuelve a realizar la busqueda del correlativo y lo actualiza, a exception de los boletos, ya que no son necesarios pues son manuales*/

		/*BEGIN 16/06/2021 - javalos - Correlativo by caja*/
//		EspecieValorada especieValorada=null;
//		if(ventaReimprimir.getTipoComprobante().getId().intValue()!=Constantes.ID_TIPCOM_BOLETO_VIAJE){
//			especieValorada=UtilData.buscarEspecieValorada(ventaReimprimir.getTipoComprobante().getId(), ventaReimprimir.getAgencia(), true);
//			ventaReimprimir.setNumeroBoleto(especieValorada.toString());
//		}
		ControlEspecieValorada controlEspecieValorada = null;
		if(ventaReimprimir.getTipoComprobante().getId().intValue()!=Constantes.ID_TIPCOM_BOLETO_VIAJE){
			controlEspecieValorada=UtilData.buscarEspecieValoradaByCaja(ventaReimprimir.getTipoComprobante().getId(), ventaReimprimir.getAgencia(), ejecutarSeqByCorrelativo, ventaReimprimir.getUsuarioHardware(), null);
			ventaReimprimir.setNumeroBoleto(controlEspecieValorada.toString());
		}
		/*END 16/06/2021 - javalos - Correlativo by caja*/

		/*	Validando que el boleto no exista en la DB	*/
		if(isBoletoDuplicado(ventaReimprimir.getNumeroBoleto(), ventaReimprimir.getTipoComprobante().getId()))
			throw new NumeroBoletoDuplicadoException();

		/*Actualiza el correlativo 25/10/2016 - jabanto*/
		if(ventaReimprimir.getTipoComprobante().getId().intValue()!=Constantes.ID_TIPCOM_BOLETO_VIAJE){
			int position = ventaReimprimir.getNumeroBoleto().indexOf("-");
			Long correlativo = Long.valueOf(ventaReimprimir.getNumeroBoleto().substring(position+1))+1;
			/*BEGIN 16/06/2021 - javalos - Correlativo by caja*/
//			especieValorada.setCorrelativoActual(correlativo);
//			getEspecieValoradaDAO().update(especieValorada);
			controlEspecieValorada.setCorrelativoActual(correlativo);
			getControlEspecieValoradaDAO().update(controlEspecieValorada);
			/*END 16/06/2021 - javalos - Correlativo by caja*/
		}


		/*	Realizando la anulaciï¿½n del boleto despues de la reimpresion	*/
		getVentaPasajesDAO().save(ventaOriginal);

		/*	Guardando la instancia de la venta de pasajes	*/
		getVentaPasajesDAO().save(ventaReimprimir);

		/* actualiza el campo VENPAS_IDREF del registro original con el Id del nuevo boleto generado 12/12/2013-jabanto*/
		ventaReimprimir.getVentaPasaje().setVentaPasaje(ventaReimprimir);
		getVentaPasajesDAO().update(ventaReimprimir.getVentaPasaje());

		/*	Actualizamos la referencia del boleto original para el caso de Vouchers y Recibos de Caja	*/
		if((ventaOriginal.getTipoComprobante().getId().intValue()==Constantes.ID_TIPCOM_RECIBO_CAJA
				|| ventaOriginal.getTipoComprobante().getId().intValue()==Constantes.ID_TIPCOM_VOUCHER_AGENCIA_VIAJES
				|| ventaOriginal.getTipoComprobante().getId().intValue()==Constantes.ID_TIPCOM_VOUCHER_CORPORATIVO) && ventaReimprimir.getVentaOriginal()!=null){

			VentaPasaje venta = getVentaPasajesDAO().buscarPorId(ventaReimprimir.getVentaOriginal());
			/*	Validamos que se trate de un Voucher o Recibo de Caja	*/
			venta.setVentaPasaje(ventaReimprimir);
			getVentaPasajesDAO().update(venta);

			/*Valida si es Pasajero frecuente - 17/10/2013 - jabanto*/
			PasajeroFrecuente paxfree = getPasajeroFrecuenteDAO().buscarPaxFree(ventaReimprimir.getPasajero().getId(), Constantes.TRUE_VALUE);
			if(paxfree!=null && ventaReimprimir.getTipoTransaccion().equals(Constantes.TIPO_OPERACION_VENTA)){
				if(ventaReimprimir.getTipoComprobante().getId().intValue()==Constantes.ID_TIPCOM_BOLETO_VIAJE &&
						ventaReimprimir.getFormaPago().getId().intValue()!=Constantes.ID_FORPAG_CORTESIA){
					/*Acumula puntos cuando el tipo de comprobante es boleto de viaje */
					paxfree.setPuntosAcumulados(paxfree.getPuntosAcumulados().intValue()+Constantes.PUNTOS_GANADOS_X_PAXFREE);
					getPasajeroFrecuenteDAO().update(paxfree);

					guardarPuntosPaxFree(paxfree, ventaReimprimir);
				}
			}


		}

		/*	Generando el numero de control del nuevo boleto	*/
		String nControl = Util.generateControlNumber(Util.decimalToHexadecimal(ventaReimprimir.getId()));
		ventaReimprimir.setNumeroControl(nControl);
		/*	Actualizando el numero de control a la venta realizada	*/
		ventaReimprimir.setFechaInsercion(Util.StringtoDate(getVentaPasajesDAO().getDateSystem(), Constantes.DATE_TIME_FORMAT));
		getVentaPasajesDAO().update(ventaReimprimir);


		/*##End Begin 28/10/2016 - jabanto*/
		/*	Actualizando el correlativo de las especies valoradas	*/
//		int position = ventaReimprimir.getNumeroBoleto().indexOf("-");
//		String serie = ventaReimprimir.getNumeroBoleto().substring(0, position);
//		Long correlativo = Long.valueOf(ventaReimprimir.getNumeroBoleto().substring(position+1))+1;
//		getControlEspecieValoradaDAO().actualizarCorrelativoEspecieValorada(idTipoComprobante, idUsuarioHardware, serie, correlativo);


		result = Constantes.CORRECT;
		return result;
	}
	/*
	 * (non-Javadoc)
	 * @see com.tepsa.sisvyr.service.business.VentaPasajesManager#buscarBoletosDevolucion(java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	public List<VentaPasaje> buscarBoletosDevolucion(String numeroDocumento, String numeroControl, String numeroBoleto)throws Exception{
		return getVentaPasajesDAO().buscarBoletosDevolucion(numeroDocumento, numeroControl, numeroBoleto);
	}
	/*
	 * (non-Javadoc)
	 * @see com.tepsa.sisvyr.service.business.VentaPasajesManager#devolucionBoleto(com.tepsa.sisvyr.model.bean.VentaPasaje)
	 */
	@Override
	@Transactional
	public VentaPasaje devolucionBoleto(VentaPasaje venta, VentaPasaje gastoAdministrativo, boolean ejecutarSeqByCorrelativo)throws Exception{
		/*	Guardando la instancia de la devolucion	*/
		getVentaPasajesDAO().save(venta);

		/*Anula los puntos asociados a la ventas que no sea por Cortesia*/
		if((venta.getTipoComprobante().getId().intValue()==Constantes.ID_TIPCOM_BOLETO_VIAJE ||
				venta.getTipoComprobante().getId().intValue()==Constantes.ID_TIPCOM_BOLETA_VENTA ||
				venta.getTipoComprobante().getId().intValue()==Constantes.ID_TIPCOM_FACTURA) && venta.getFormaPago().getId().intValue()!=Constantes.ID_FORPAG_CORTESIA){

			anularPuntosPaxFree(venta);

		}else{
			/*Cuando la devolucion es de una cortesia por puntos*/
			if(venta.getFormaPago().getId().intValue()==Constantes.ID_FORPAG_CORTESIA &&
					venta.getTipoFormaPago().getId().intValue()==Constantes.ID_TIPFORPAG_PUNTOS){
				//Restaura los puntos utilizados para la cortesia por puntos
				ServiceLocator.getPuntosPasajeroFrecuenteManager().restaurarPuntos(venta.getVentaOriginal());
			}
		}

		VentaPasaje notaCredito=null;
		/*Valida si debe o no aplicar nota de credito al comprobante que se esta devolviendo*/
		/*=================================================================================*/
		if(venta.getVentaPasaje().getTipoComprobante().getId().intValue()!=Constantes.ID_TIPCOM_BOLETO_VIAJE &&
				venta.getVentaPasaje().getTipoComprobante().getId().intValue()!=Constantes.ID_TIPCOM_RECIBO_CAJA){

			boolean emitirNota=true;
			/*Valida si el Comprobante ha sido emitido por los operadores POOL - 14/11/2016 - jabanto*/
			if(venta.getRucClienteCredito()!=null && (venta.getRucClienteCredito().equals(Constantes.RUC_CRUZ_DEL_SUR) ||
													  venta.getRucClienteCredito().equals(Constantes.RUC_CIVA))){
				emitirNota=false;
			}else if (venta.getCanalVenta().getId().intValue()==Constantes.ID_CANVEN_AGENCIA_VIAJES || venta.getCanalVenta().getId().intValue()==Constantes.ID_CANVEN_WEB){
				/*Cuando el canal por el cual se emitio el comprobante es agencia de viaje o web, no emite nota de credito
				 * Basicamente por el tema de la cobranza, pues estos comprobantes siempre se deben cobrar. - 15/12/2016 - jabanto
				 * */
				emitirNota=false;
			}
			
			/*===========================================================*/
			/*	javalos - 18-01-2023*/
			/***********************************************/
			/*		PARA TRANSMAR NO DEBE EMITIR NC	*/
			/***********************************************/
			emitirNota = false;
			/***********************************************/

			if(emitirNota){
				notaCredito=generarNotaCredito(venta.getVentaPasaje(), venta.getTipoNota(),false, false, ejecutarSeqByCorrelativo);
				if(notaCredito==null)
					throw new Exception("Ha ocurrido un error al emitir la Nota de Crï¿½dito...");
			}
		}

		/*Valida si debe emitir el gasto administrativo*/
		if(gastoAdministrativo!=null) {
			gastoAdministrativo.setUsuarioHardware(venta.getUsuarioHardware());
			generarGastoAdministrativo(gastoAdministrativo,true, ejecutarSeqByCorrelativo);
		}
		return notaCredito;
	}
	/*
	 * (non-Javadoc)
	 * @see com.tepsa.sisvyr.service.business.VentaPasajesManager#buscarAvanceSemanalVentas(java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	public List<VentaPasaje> buscarAvanceSemanalVentas(String idOrigen,String idDestino, String tipoTransaction, String idServicio,String fechaDesde, String fechaHasta) throws Exception {
		return getVentaPasajesDAO().buscarAvanceSemanalVentas(idOrigen, idDestino, tipoTransaction, idServicio, fechaDesde, fechaHasta);
	}
	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.service.business.VentaPasajesManager#buscarAvanceSemanalVentasColumns(java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	public List<VentaPasaje> buscarAvanceSemanalVentasColumns(String idOrigen,String idDestino, String tipoTransaction, String idServicio,String fechaDesde, String fechaHasta) throws Exception {
		// TODO Auto-generated method stub
		return getVentaPasajesDAO().buscarAvanceSemanalVentasColumns(idOrigen, idDestino, tipoTransaction, idServicio, fechaDesde, fechaHasta);
	}
	/*
	 * (non-Javadoc)
	 * @see com.tepsa.sisvyr.service.business.VentaPasajesManager#contarViajesValidos(java.lang.Long, java.lang.String, java.lang.String)
	 */
	@Override
	public int contarViajesValidos(Long idPasajero, String fechaInicial, String fechaFinal)throws Exception{
		return getVentaPasajesDAO().contarViajesValidos(idPasajero, fechaInicial, fechaFinal);
	}
	/*
	 * (non-Javadoc)
	 * @see com.tepsa.sisvyr.service.business.VentaPasajesManager#buscarBoletosReimprimir(java.lang.String, java.lang.String[], java.lang.String)
	 */
	@Override
	public List<VentaPasaje> buscarBoletosReimprimir(String numeroDocumento, String[] pasajero, String fechaPartida)throws Exception{
		return getVentaPasajesDAO().buscarBoletosReimprimir(numeroDocumento, pasajero, fechaPartida);
	}
	/*
	 * (non-Javadoc)
	 * @see com.tepsa.sisvyr.service.business.VentaPasajesManager#buscarComprobantesSinBoleto(java.lang.String, java.lang.Integer, java.lang.Integer, java.lang.Integer, java.lang.Integer)
	 */
	@Override
	public List<VentaPasaje> buscarComprobantesSinBoleto(String fechaPartida, Integer idAgencia,Integer idTipoComprobante, Integer idRol, Integer idAgenciaEmision)throws Exception{
		return getVentaPasajesDAO().buscarComprobantesSinBoleto(fechaPartida, idAgencia,idTipoComprobante, idRol, idAgenciaEmision);
	}
	/*
	 * (non-Javadoc)
	 * @see com.tepsa.sisvyr.service.business.VentaPasajesManager#buscarPorId(java.lang.Long)
	 */
	@Override
	public VentaPasaje buscarPorId(Long id) {
		return getVentaPasajesDAO().buscarPorId(id);
	}
	@Override
	public void transbordarPax(Integer numeroAsiento, Long idVentaPasaje,Itinerario itinerario) throws Exception {
		getVentaPasajesDAO().transbordarPax(numeroAsiento, idVentaPasaje, itinerario);
	}
	/*
	 * (non-Javadoc)
	 * @see com.tepsa.sisvyr.service.business.VentaPasajesManager#buscarVentasPax(java.lang.String, java.lang.String, java.lang.Long)
	 */
	@Override
	public ArrayList<VentaPasaje> buscarVentasPax(String fechaInicial,String fechaFinal, Long pasajeroID) throws Exception {
		return getVentaPasajesDAO().buscarVentasPax(fechaInicial, fechaFinal, pasajeroID);
	}
	/*
	 * (non-Javadoc)
	 * @see com.tepsa.sisvyr.service.business.VentaPasajesManager#validaOcupabilidad(java.lang.Long, java.lang.Integer, java.lang.String, java.lang.Integer)
	 */
	@Override
	public List<VentaPasaje> validaOcupabilidad(Long idItinerario,Integer idRuta, String numeroAsientos, Integer numeroPiso)throws Exception {
		return getVentaPasajesDAO().validaOcupabilidad(idItinerario, idRuta, numeroAsientos, numeroPiso);
	}
	/*
	 * (non-Javadoc)
	 * @see com.tepsa.sisvyr.service.business.VentaPasajesManager#validaBoletos_ServicioEspecial(java.lang.String, java.lang.String)
	 */
	@Override
	public Integer validaBoletos_ServicioEspecial(String boletoInicial,String boletoFinal) throws Exception {
		return getVentaPasajesDAO().validaBoletos_ServicioEspecial(boletoInicial, boletoFinal);
	}
	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.service.business.VentaPasajesManager#ultimoCorrelativosEmitidos(java.lang.String, java.lang.String, java.lang.Integer)
	 */
	@Override
	public List<VentaPasaje> buscarUltimoCorrelativoEmitido(String fechaUltimoEnvio, String serie, Integer idComprobante, Integer idAgencia)throws Exception {
		return getVentaPasajesDAO().buscarUltimoCorrelativoEmitido(fechaUltimoEnvio, serie, idComprobante, idAgencia);
	}
	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.service.business.VentaPasajesManager#correlativosFaltantesX(java.lang.String, java.lang.String, java.lang.Integer, java.lang.Integer)
	 */
	@Override
	public List<VentaPasaje> correlativosFaltantesX(String fechaInicio, String serie,Integer idComprobante, Integer idAgencia,String fechaFin, Integer idUsuario) throws Exception {
		return getVentaPasajesDAO().correlativosFaltantesX(fechaInicio, serie, idComprobante, idAgencia, fechaFin,idUsuario);
	}
	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.service.business.VentaPasajesManager#buscaTotalVentasEfectivo(java.lang.Integer, java.lang.Integer, java.lang.String)
	 */
	@Override
	public Double buscaTotalVentasEfectivo(Integer idUsuario,Integer idAgencia, String fecha) throws Exception {
		// TODO Auto-generated method stub
		return getVentaPasajesDAO().buscaTotalVentasEfectivo(idUsuario, idAgencia, fecha);
	}
	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.service.business.VentaPasajesManager#buscarPorX(java.util.TreeMap, java.util.List)
	 */
	@Override
	public ArrayList<VentaPasaje> buscarPorX(TreeMap<String, Object> criteriosBusqueda,List<String> criteriosOrdenar) {
		// TODO Auto-generated method stub
		return getVentaPasajesDAO().buscarPorX(criteriosBusqueda, criteriosOrdenar);
	}
	/*
	 * (non-Javadoc)
	 * @see com.tepsa.sisvyr.service.business.VentaPasajesManager#buscarDetalladoVentas(java.lang.Integer, java.lang.Integer, java.lang.String, java.lang.String, java.lang.Integer)
	 */
	@Override
	public List<VentaPasaje> buscarDetalladoVentas(Integer idAgencia, Integer idUsuario, String fechaInicial, String fechaFinal, Integer criterio)throws Exception{
		return getVentaPasajesDAO().buscarDetalladoVentas(idAgencia, idUsuario, fechaInicial, fechaFinal, criterio);
	}
	/*
	 * (non-Javadoc)
	 * @see com.tepsa.sisvyr.service.business.VentaPasajesManager#buscarDetalleVentasAgencia(java.lang.String, java.lang.String, java.lang.String, java.lang.Long, java.lang.String, java.lang.Boolean, boolean)
	 */
	@Override
	public List<VentaPasaje> buscarDetalleVentasAgencia(String fechaInicio,String fechaFin, String rucClientecredito, Long idUsuario,String orden,Boolean incluirAnulados, boolean isSoles, String estadoBoleto, Integer centroCostoID,boolean byFechaReimpresion)throws Exception {
		// TODO Auto-generated method stub
		return getVentaPasajesDAO().buscarDetalleVentasAgencia(fechaInicio, fechaFin, rucClientecredito, idUsuario,orden,incluirAnulados,isSoles, estadoBoleto, centroCostoID,byFechaReimpresion);
	}
	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.service.business.VentaPasajesManager#buscarUltimoRegistro(java.lang.Long)
	 */
	@Override
	public VentaPasaje buscarUltimoRegistro(Long idVentaOriginal) {
		// TODO Auto-generated method stub
		return getVentaPasajesDAO().buscarUltimoRegistro(idVentaOriginal);
	}
	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.service.business.VentaPasajesManager#buscarVoucherForAnulacion(java.lang.Integer, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	public List<VentaPasaje> buscarVoucherForAnulacion(Integer idTipoComprobante, String numVoucher, String numcontrol,String rucCliente, String fechaPartida, String horaPartida,String boleto) {
		// TODO Auto-generated method stub
		return getVentaPasajesDAO().buscarVoucherForAnulacion(idTipoComprobante, numVoucher, numcontrol, rucCliente, fechaPartida, horaPartida,boleto);
	}
	/*
	 * (non-Javadoc)
	 * @see com.tepsa.sisvyr.service.business.VentaPasajesManager#anularReserva(com.tepsa.sisvyr.model.bean.VentaPasaje)
	 */
	@Transactional
	@Override
	public int anularReserva(VentaPasaje reserva)throws Exception{
		int result = Constantes.FAILURE;
		getVentaPasajesDAO().update(reserva);
		result = Constantes.CORRECT;
		return result;
	}
	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.service.business.VentaPasajesManager#buscarEstadoVentasReservas(java.lang.String, java.lang.String, java.lang.Integer, java.lang.String, java.lang.Integer, java.lang.String, java.lang.String, java.lang.Integer, java.lang.String, java.lang.String, java.lang.Integer, java.lang.Integer, java.lang.Integer)
	 */
	@Override
	public List<VentaPasaje> buscarEstadoVentasReservas(String fechaInicialVenta, String fechaFinVenta,Integer tipoBusqCliente, String busqCliente, Integer tipoBusqPax,String busqPax, String fechaPartida, Integer idUsuario,String numeroControl,
			String numeroBoleto, Integer idOrigen,Integer idDestino, Integer tipoMovimiento) throws Exception {
		// TODO Auto-generated method stub
		return getVentaPasajesDAO().buscarEstadoVentasReservas(fechaInicialVenta, fechaFinVenta, tipoBusqCliente, busqCliente, tipoBusqPax, busqPax, fechaPartida, idUsuario, numeroControl, numeroBoleto, idOrigen, idDestino, tipoMovimiento);
	}
	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.service.business.VentaPasajesManager#buscarOperacionesRemotras(java.lang.String, java.lang.Integer, java.lang.Integer, java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	public List<VentaPasaje> buscarOperacionesRemotras(String fechaPartida,Integer idOrigen, Integer idDestino, String numeroBoleto,String numeroControl, String documentoPax) throws Exception {
		// TODO Auto-generated method stub
		return getVentaPasajesDAO().buscarOperacionesRemotras(fechaPartida, idOrigen, idDestino, numeroBoleto, numeroControl, documentoPax);
	}
	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.service.business.VentaPasajesManager#cambiarAsientoPax(java.lang.Integer, java.lang.Long)
	 */
	@Transactional
	@Override
	public void cambiarAsientoPax(Integer nuevoAsiento, Integer numeroPiso,Long idVentaPasajes)throws Exception {
		try{
			// TODO Auto-generated method stub
			VentaPasaje ventaPasaje=getVentaPasajesDAO().buscarPorId(idVentaPasajes);

			/*	Validando que no se haya cambiado el tipo de Bus durante la venta	*/
			boolean excedeCapacidad = false;
			List<Object> lstCapacidad = getItinerarioDAO().validateCapacity(ventaPasaje.getItinerario().getId(), nuevoAsiento, numeroPiso);
			if(lstCapacidad.size()>0){
				excedeCapacidad = (Boolean)lstCapacidad.get(0);
				if(excedeCapacidad)
					throw new CapacityExceedsException((String)lstCapacidad.get(1));
			}

			/*	Validando que el asiento no haya sido utilizado antes de la venta	*/
			Long ocupabilidad = getVentaPasajesDAO().validateSeat(ventaPasaje.getItinerario(), ventaPasaje.getRuta(), nuevoAsiento, numeroPiso);
			if(ocupabilidad.longValue()>0){
				if(ventaPasaje.getVentaPasaje()!=null && ventaPasaje.getVentaPasaje().getId().longValue()!=ocupabilidad.longValue())
					throw new DuplicateSeatException();
				else if(ventaPasaje.getVentaPasaje()==null)
					throw new DuplicateSeatException();
			}

			ventaPasaje.setNumeroAsiento(nuevoAsiento);
			ventaPasaje.setNumeroPiso(numeroPiso);

			Usuario usuario=(Usuario) Executions.getCurrent().getSession().getAttribute(Constantes.ATRIBUTO_USUARIO);
			UtilData.auditarRegistro(ventaPasaje, true, usuario, Executions.getCurrent());
			getVentaPasajesDAO().update(ventaPasaje);

		}catch(CapacityExceedsException ceex){
			throw new CapacityExceedsException();
		}catch(DuplicateSeatException dsex){
			throw new DuplicateSeatException();
		}catch(Exception ex){
			ex.printStackTrace();
			throw new Exception(ex);
		}

	}
	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.service.business.VentaPasajesManager#buscarTotalVentas(java.lang.String, java.lang.Integer, java.lang.Integer)
	 */
	@Override
	public Double buscarTotalVentas(String fechaLiquidacion, Integer idAgencia,Integer idUsuario) {
		return getVentaPasajesDAO().buscarTotalVentas(fechaLiquidacion, idAgencia, idUsuario);
	}

	/*
	 * (non-Javadoc)
	 * @see com.tepsa.sisvyr.service.business.VentaPasajesManager#buscarVentasByPasajero(java.lang.Long)
	 */
	@Override
	public List<VentaPasaje> buscarVentasByPasajero(Long idPasajero)throws Exception{
		return getVentaPasajesDAO().buscarVentasByPasajero(idPasajero);
	}
	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.service.business.VentaPasajesManager#buscarBoletoLiberarManifiesto(java.lang.String)
	 */
	@Override
	public List<VentaPasaje> buscarBoletoLiberarManifiesto(String numeroboleto)throws Exception {
		return getVentaPasajesDAO().buscarBoletoLiberarManifiesto(numeroboleto);
	}
	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.service.business.VentaPasajesManager#anularBoletoCredito(com.tepsa.sisvyr.model.bean.VentaPasaje)
	 */
	@Transactional
	@Override
	public VentaPasaje anularBoletoCredito(VentaPasaje boleto, Usuario usuarioAuditoria) throws Exception {
		// TODO Auto-generated method stub

		TreeMap<String, Object>criteriosBusqueda=new TreeMap<>();
		List<String>criteriosOrden=new ArrayList<>();
		Double importeBoleto=boleto.getImportePagado();

		/********* ANULACION VOUCHER ************************/
		//BUSCA VOUCHER
		criteriosBusqueda.put("numeroBoleto", boleto.getNumeroBoletoAnterior());
		criteriosBusqueda.put("rucClienteCredito", boleto.getRucClienteCredito());
		criteriosOrden.add("id");
		List<VentaPasaje>lstVoucher=buscarPorX(criteriosBusqueda, criteriosOrden);
		if(lstVoucher.size()==2){
			VentaPasaje voucherOriginal=lstVoucher.get(0);
			VentaPasaje voucherAnulacionSistema=lstVoucher.get(1);

			//Realiza la anulacion del voucher
			voucherOriginal.setTipoMovimiento(new TipoMovimiento(Constantes.ID_TIPMOV_ANULACION));
			voucherOriginal.setTarifa(0.00);
			voucherOriginal.setDescuento(0.00);
			voucherOriginal.setPenalidad(0.00);
			voucherOriginal.setRecargo(0.00);
			voucherOriginal.setImportePagado(0.00);
			voucherOriginal.setVentaPasaje(null);
			getVentaPasajesDAO().update(voucherOriginal);

			//Elimina el segundo registro
			getVentaPasajesDAO().delete(voucherAnulacionSistema);

		}else{
			//ERROR AL ANULAR EL VOUCHER (crear excepcion)
			throw new Exception("Error al anular el Voucher.");
		}

		/********* ANULACION BOLETO ************************/
		boleto.setTipoMovimiento(new TipoMovimiento(Constantes.ID_TIPMOV_ANULACION));
		boleto.setVentaPasaje(null);
		boleto.setVentaOriginal(boleto.getId());
		boleto.setTarifa(0.00);
		boleto.setImportePagado(0.00);
		boleto.setTarifaEquibalente(boleto.getTarifaEquibalente()!=null?.00:null);
		boleto.setDescuentoEquibalente(boleto.getDescuentoEquibalente()!=null?.00:null);
		boleto.setImportePagadoEquibalente(boleto.getImportePagadoEquibalente()!=null?.00:null);
		getVentaPasajesDAO().update(boleto);

		/* Valida si el boleto ya esta liquidado*/
		if(boleto.getLiquidacion()!=null){

			/******** VALIDA SI EL BOLETO FUE TRANSFERIDO O NO AL TITAN*/
			if(boleto.getFechaTransferencia()!=null){
//						TitanDAOImpl getTitanDAO=new TitanDAOImpl();


				/****** QUITA EL BOLETO DE LA LIQUIDACION - TITAN */
				/* Busca liquidacion turno pasaje - TITAN */
				TitanLiquidacionTurnoPasaje liquidacionTurnoPasaje=getTitanDAO().buscarLiquidacionTurnoPasajeByIdLiquidacion(boleto.getLiquidacion().getId().longValue());
				/* Actualiza el la cantidad y total credito en la liquidacion turno pasaje (t_liqui_turno_pasaje) - TITAN*/
				if(liquidacionTurnoPasaje!=null){
					Integer cantidadCredito=liquidacionTurnoPasaje.getCantidadCredito()-1;
					Double totalCredito=liquidacionTurnoPasaje.getTotalCredito()-importeBoleto;
					Double montoEfectivo=liquidacionTurnoPasaje.getMontoEfectivo()-importeBoleto;

					liquidacionTurnoPasaje.setCantidadCredito(cantidadCredito);
					liquidacionTurnoPasaje.setTotalCredito(totalCredito);
					liquidacionTurnoPasaje.setMontoEfectivo(montoEfectivo);
					getTitanDAO().actualizaLiquidacionTurnoPasajeByIdLiquidacion(liquidacionTurnoPasaje);
				}else{
					//No se ha encontrado la transferencia de la liquidacion
					throw new Exception("No se ha encontrado la transferencia de la Liquidaciï¿½n, \n" +
										"por lo que no se pudo hacer el recalculo en los totales de Boletos Crï¿½dito en la liquidaciï¿½n de turno en TITAN. ");
				}


				/******* ANULA BOLETO EN TITAN */
				//Busca el boleto
				int CONDICION_BOLETO_CUENTA_CORRIENTE=25;
				int CONDICION_BOLETO_CANJE_PUBLICITARIO=18;
				//---
				String nroBoleto=boleto.getNumeroBoleto();
				int lenSeparator=nroBoleto.indexOf("-");
				String serieBoleto=nroBoleto.substring(0,lenSeparator);
				String numeroBoleto=nroBoleto.substring(lenSeparator+1,nroBoleto.length());
				Integer idCondicionBoleto=0;
				if(boleto.getTipoFormaPago().getId().intValue()==Constantes.ID_TIPFORPAG_CANJE_PUBLICITARIO)
					idCondicionBoleto=CONDICION_BOLETO_CANJE_PUBLICITARIO;
				else
					idCondicionBoleto=CONDICION_BOLETO_CUENTA_CORRIENTE;
				TitanVentaPasaje titanVentaPasaje=getTitanDAO().buscarBoletoVentaPasaje(serieBoleto, numeroBoleto, idCondicionBoleto);

				if(titanVentaPasaje!=null){
					getTitanDAO().anularDevolverBoletoVentaPasaje(titanVentaPasaje.getId(),true);//Anula el boleto - TITAN
				}else{
					//El boleto esta como transferido en el Sisvyr, sin embargo este no a sido encontrado en TITAN.
					throw new Exception("El Boleto esta como transferido en el Sisvyr, sin embargo ï¿½ste no ha sido encontrado en TITAN");
				}

				/******* VALIDA SI EL BOLETO ESTA O NO TRANSFERIDO AL OFISIS */
				if(titanVentaPasaje.getFechaTransferencia()!=null){
					//Envia correo
					try{
						String title="Anulaciï¿½n Boleto Crï¿½dito";
						String toAddress="sistemas@tepsa.com.pe";
//								String mensaje="Se ha realizado la anulaciï¿½n del Siguiente Boleto, el cual ya ha sido transferidos al OFISIS.\n\n";
						String mensaje="Se ha realizado la anulaciï¿½n del Siguiente Boleto, el cual ya fue transferido al OFISIS.\n\n";

						mensaje+="Nro. Boleto   : "+boleto.getNumeroBoleto()+"\n";
						mensaje+="Fecha Emisiï¿½n : "+Constantes.FORMAT_DATE.format(boleto.getFechaLiquidacion())+"\n";
						mensaje+="Fecha Transferencia : "+Constantes.FORMAT_DATE.format(titanVentaPasaje.getFechaTransferencia())+"\n";

						mensaje+="\n\n";
						mensaje+="NOTA: [Este buzon es de envio automï¿½tico, por favor no responda.]";
						DestinatariosEmails window = new DestinatariosEmails();
						window.setEmails("TO:"+toAddress);
						Sendmail.enviaEmail(mensaje,title, window);

					}catch(Exception ex){
						ex.printStackTrace();
					}
				}
			}
		}
		return null;
	}
	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.service.business.VentaPasajesManager#buscarBoletoByAsegurado(java.lang.String)
	 */
	@Override
	public VentaPasaje buscarBoletoByAsegurado(String numeroBoleto)throws Exception {
		// TODO Auto-generated method stub
		return getVentaPasajesDAO().buscarBoletoByAsegurado(numeroBoleto);
	}
	/*
	 * (non-Javadoc)
	 * @see com.tepsa.sisvyr.service.business.VentaPasajesManager#buscarPasajerosByME(java.lang.Long)
	 */
	@Override
	public List<VentaPasaje>buscarPasajerosByME(Long itinerarioId) throws Exception {
		return getVentaPasajesDAO().buscarPasajerosByME(itinerarioId);
	}
	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.service.business.VentaPasajesManager#actualizar(com.tepsa.sisvyr.model.bean.VentaPasaje)
	 */
	@Transactional
	@Override
	public void actualizar(VentaPasaje ventaPasaje) throws Exception {
		// TODO Auto-generated method stub
		getVentaPasajesDAO().update(ventaPasaje);
	}
	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.service.business.VentaPasajesManager#buscarVentasPorPuntoVenta(java.lang.String, java.lang.String, java.lang.Integer, java.lang.Integer, java.lang.Integer, java.lang.Integer)
	 */
	@Override
	public List<Agencia> buscarVentasPorPuntoVenta(String fechaInicial, String fechaFinal, Integer idAgencia,Integer idUsuario, String idsTiposMovimientos, Integer idFormaPago)throws Exception {
		// TODO Auto-generated method stub
		return getVentaPasajesDAO().buscarVentasPorPuntoVenta(fechaInicial, fechaFinal, idAgencia, idUsuario, idsTiposMovimientos, idFormaPago);
	}

	@Transactional
	@Override
	public VentaPasaje generarNotaCredito(VentaPasaje ventaAplica, TipoNota tipoNota, boolean anularMovimiento, boolean copyCanalOriginal, Liquidacion liquidacion, boolean ejecutarSeqByCorrelativo) throws Exception {
		Agencia agencia=null;
		Usuario usuario=null;
		Date fechaLiquidacion=null;

		if(liquidacion==null){
			agencia = (Agencia) Executions.getCurrent().getDesktop().getSession().getAttribute(Constantes.ATRIBUTO_AGENCIA);
			usuario = (Usuario) Executions.getCurrent().getDesktop().getSession().getAttribute(Constantes.ATRIBUTO_USUARIO);
			fechaLiquidacion=(Date)Executions.getCurrent().getDesktop().getSession().getAttribute(Constantes.ATRIBUTO_FECHA_LIQUIDACION);
		}else{
			agencia=liquidacion.getAgencia();
			usuario=ServiceLocator.getUsuarioManager().buscarPorId(liquidacion.getUsuario().getId().longValue());
			fechaLiquidacion=liquidacion.getFechaLiquidacion();
		}


		CanalVenta canalVenta = (CanalVenta)Executions.getCurrent().getDesktop().getSession().getAttribute(Constantes.ATRIBUTO_CANAL_VENTA);
		UsuarioHardware usuarioHardware= (UsuarioHardware)Executions.getCurrent().getDesktop().getSession().getAttribute(Constantes.ATRIBUTO_USUARIO_HARDWARE);

		if(fechaLiquidacion==null)
			fechaLiquidacion =Constantes.FORMAT_DATE.parse(MyTime.dateTimeServer());

		VentaPasaje ventaOriginal= ServiceLocator.getVentaPasajesManager().buscarPorId(ventaAplica.getId());//.getVentaOriginal());

		if(anularMovimiento){
			/*Primero realizando la anulaciÃ³n de la Boleta o Factura*/
			VentaPasaje ventaAnulacion = (VentaPasaje)ventaOriginal.clone();
			ventaAnulacion.setId(null);
			ventaAnulacion.setFechaLiquidacion(fechaLiquidacion);
			ventaAnulacion.setAgencia(agencia);
			ventaAnulacion.setUsuario(usuario);
			ventaAnulacion.setCanalVenta(copyCanalOriginal?ventaOriginal.getCanalVenta():canalVenta);
			ventaAnulacion.setTipoMovimiento(new TipoMovimiento(Constantes.ID_TIPMOV_ANULACION_SISTEMA));
			ventaAnulacion.setObservaciones(ventaAplica.getObservaciones()!=null?ventaAplica.getObservaciones():null);
			UtilData.auditarRegistro(ventaAnulacion, usuario, Executions.getCurrent());
			getVentaPasajesDAO().save(ventaAnulacion);
		}

		/*Armando la nota de credito*/
		VentaPasaje notaCredito= new VentaPasaje();
		notaCredito.setVentaPasaje(ventaOriginal);
		notaCredito.setVentaOriginal(ventaOriginal.getVentaOriginal()!=null?ventaOriginal.getVentaOriginal():ventaOriginal.getId());
		notaCredito.setItinerario(new Itinerario((long)1));
		notaCredito.setRuta(ventaOriginal.getRuta());
		notaCredito.setCliente(ventaOriginal.getCliente());
		notaCredito.setPasajero(ventaOriginal.getPasajero());
		notaCredito.setServicio(ventaOriginal.getServicio());
		notaCredito.setTipoComprobante(new TipoComprobante(Constantes.ID_TIPCOM_NOTA_CREDITO));
//		notaCredito.setTipoMovimiento(ventaAplica.getTipoMovimiento());
		notaCredito.setTipoMovimiento(new TipoMovimiento(Constantes.ID_TIPMOV_EFECTIVO));
//		notaCredito.setFormaPago(new FormaPago(Constantes.ID_FORPAG_CONTADO));
		notaCredito.setFormaPago((ventaOriginal.getFormaPago()!=null?ventaOriginal.getFormaPago():new FormaPago(Constantes.ID_FORPAG_CONTADO)));
		notaCredito.setTipoFormaPago(new TipoFormaPago(Constantes.ID_TIPFORPAG_EFECTIVO));
		notaCredito.setNumeroBoletoAnterior(ventaOriginal.getNumeroBoleto());
		notaCredito.setNumeroControl("-");
		notaCredito.setSecuencial(0);
//		notaCredito.setTarifa(ventaOriginal.getTarifa());
		notaCredito.setTarifa(ventaOriginal.getImportePagado());
		notaCredito.setRecargo(0.00);
		notaCredito.setDescuento(0.00);
		notaCredito.setPenalidad(0.00);
		notaCredito.setAcuenta(0.00);
		notaCredito.setImportePagadoByDiferencia(0.00);
		notaCredito.setImportePagadoEfectivo(0.00);
		notaCredito.setImportePagadoTarjeta(0.00);
		notaCredito.setIgv(ventaOriginal.getIgv());
		notaCredito.setTipoTransaccion(Constantes.TIPO_OPERACION_VARIOS);
		notaCredito.setImportePagado(ventaOriginal.getImportePagado());
		notaCredito.setFechaCaducidad(new Date());
		notaCredito.setFechaLiquidacion(fechaLiquidacion);
		notaCredito.setAgencia(agencia);
		notaCredito.setUsuario(usuario);
		notaCredito.setCanalVenta(copyCanalOriginal?ventaOriginal.getCanalVenta():usuarioHardware.getCanalVenta());
		notaCredito.setIdaRetorno(Constantes.FALSE_VALUE);
		notaCredito.setEsFechaAbierta(Constantes.FALSE_VALUE);
		notaCredito.setObservaciones(tipoNota.getMovimiento());
		notaCredito.setTipoNota(tipoNota);
		notaCredito.setEstadoRegistro(Constantes.VALUE_ACTIVO);
		notaCredito.setRucClienteCredito(ventaOriginal.getRucClienteCredito()!=null?ventaOriginal.getRucClienteCredito():null);
		UtilData.auditarRegistro(notaCredito, usuario, Executions.getCurrent());

		//Recuperando el correlativo
		/*BEGIN 15/06/2021 - javalos - Correlativo by caja*/
		Integer aplicarA = 0;
		if(ventaAplica.getTipoComprobante().getId().intValue()==Constantes.ID_TIPCOM_BOLETA_VENTA)
			aplicarA = Constantes.APLICAR_NC_A_BOLETA;
		else if(ventaAplica.getTipoComprobante().getId().intValue()==Constantes.ID_TIPCOM_FACTURA)
			aplicarA = Constantes.APLICAR_NC_A_FACTURA;

//		EspecieValorada especieValorada=UtilData.buscarEspecieValorada(notaCredito.getTipoComprobante().getId(), notaCredito.getAgencia(), true);
//		String numeroNota=especieValorada.toString();
//		if(ventaAplica.getTipoComprobante().getId().intValue()==Constantes.ID_TIPCOM_BOLETA_VENTA)
//			numeroNota="B"+numeroNota;
//		else
//			numeroNota="F"+numeroNota;
//		notaCredito.setNumeroBoleto(numeroNota);

		ControlEspecieValorada controlEspecieValorada = UtilData.buscarEspecieValoradaByCaja(notaCredito.getTipoComprobante().getId(), notaCredito.getAgencia(), ejecutarSeqByCorrelativo, usuarioHardware, aplicarA);
		notaCredito.setNumeroBoleto(controlEspecieValorada.toString());
		/*END 15/06/2021 - javalos - Correlativo by caja*/

		/*	Validando que el boleto no exista en la DB	*/
		if(isBoletoDuplicado(notaCredito.getNumeroBoleto(), notaCredito.getTipoComprobante().getId()))
			throw new NumeroBoletoDuplicadoException();

		/*Actualiza el correlativo 25/10/2016 - jabanto*/
		int position = notaCredito.getNumeroBoleto().indexOf("-");
		Long correlativo = Long.valueOf(notaCredito.getNumeroBoleto().substring(position+1))+1;
		/*BEGIN 15/06/2021 - javalos - Correlativo by caja*/
//		especieValorada.setCorrelativoActual(correlativo);
//		getEspecieValoradaDAO().update(especieValorada);
		controlEspecieValorada.setCorrelativoActual(correlativo);
		getControlEspecieValoradaDAO().update(controlEspecieValorada);
		/*END 15/06/2021 - javalos - Correlativo by caja*/

		/*Guardando la instancia de la nota de credito*/
		getVentaPasajesDAO().save(notaCredito);
		/*Actualizando el numero de control*/
		String nControl = Util.generateControlNumber(Util.decimalToHexadecimal(notaCredito.getId()));
		notaCredito.setNumeroControl(nControl);
		notaCredito.setFechaInsercion(Util.StringtoDate(getVentaPasajesDAO().getDateSystem(), Constantes.DATE_TIME_FORMAT));
		getVentaPasajesDAO().update(notaCredito);

		/*Actualizamos la referencia de la nota*/
		ventaOriginal.setVentaPasaje(notaCredito);
		getVentaPasajesDAO().update(ventaOriginal);

		return notaCredito;
	}
	/*
	 * (non-Javadoc)
	 * @see com.tepsa.sisvyr.service.business.VentaPasajesManager#generarNotaCredito(com.tepsa.sisvyr.model.bean.VentaPasaje, com.tepsa.sisvyr.model.bean.TipoNota)
	 */
	@Transactional
	@Override
	public VentaPasaje generarNotaCredito(VentaPasaje ventaAplica, TipoNota tipoNota, boolean anularMovimiento, boolean copyCanalOriginal, boolean ejecutarSeqByCorrelativo) throws Exception {
		return generarNotaCredito(ventaAplica, tipoNota, anularMovimiento, copyCanalOriginal, null, ejecutarSeqByCorrelativo);
	}
	/*
	 * (non-Javadoc)
	 * @see com.tepsa.sisvyr.service.business.VentaPasajesManager#generarGastoAdministrativo(com.tepsa.sisvyr.model.bean.VentaPasaje, java.lang.Boolean)
	 */
	@Transactional
	@Override
	public int generarGastoAdministrativo(VentaPasaje gastoAdministrativo, Boolean generarCorrelativo, boolean ejecutarSeqByCorrelativo) throws Exception {
		int result=Constantes.FAILURE;

		/*Guarda el Gasto administrativo*/
		/*=================================================================================*/
		/**Begin 25/10/2016 - jabanto**/
		/*Vuelve a realizar la busqueda del correlativo y lo actualiza, a exception de los boletos, ya que no son necesarios pues son manuales*/
		/*BEGIN 15/06/2021 - javalos - Correlativo by caja*/
//		EspecieValorada especieValorada=null;
		ControlEspecieValorada controlEspecieValorada = null;
		if(generarCorrelativo){//Valida si debe volver a generar el correlativo del comprobante
			if(gastoAdministrativo.getTipoComprobante().getId().intValue()!=Constantes.ID_TIPCOM_BOLETO_VIAJE){
//				especieValorada=UtilData.buscarEspecieValorada(gastoAdministrativo.getTipoComprobante().getId(), gastoAdministrativo.getAgencia(), true);
//				gastoAdministrativo.setNumeroBoleto(especieValorada.toString());
				controlEspecieValorada = UtilData.buscarEspecieValoradaByCaja(gastoAdministrativo.getTipoComprobante().getId(), gastoAdministrativo.getAgencia(), ejecutarSeqByCorrelativo, gastoAdministrativo.getUsuarioHardware(), null);
				gastoAdministrativo.setNumeroBoleto(controlEspecieValorada.toString());
			}
		}
		/*END 15/06/2021 - javalos - Correlativo by caja*/

		/*	Validando que el boleto no exista en la DB	*/
		if(isBoletoDuplicado(gastoAdministrativo.getNumeroBoleto(), gastoAdministrativo.getTipoComprobante().getId()))
			throw new NumeroBoletoDuplicadoException();

		/*Actualiza el correlativo 25/10/2016 - jabanto*/
		/*BEGIN 15/06/2021 - javalos - Correlativo by caja*/
//		if(especieValorada!=null && gastoAdministrativo.getTipoComprobante().getId().intValue()!=Constantes.ID_TIPCOM_BOLETO_VIAJE){
//			int position = gastoAdministrativo.getNumeroBoleto().indexOf("-");
//			Long correlativo = Long.valueOf(gastoAdministrativo.getNumeroBoleto().substring(position+1))+1;
//			especieValorada.setCorrelativoActual(correlativo);
//			getEspecieValoradaDAO().update(especieValorada);
//		}
		if(controlEspecieValorada!=null && gastoAdministrativo.getTipoComprobante().getId().intValue()!=Constantes.ID_TIPCOM_BOLETO_VIAJE){
			int position = gastoAdministrativo.getNumeroBoleto().indexOf("-");
			Long correlativo = Long.valueOf(gastoAdministrativo.getNumeroBoleto().substring(position+1))+1;
			controlEspecieValorada.setCorrelativoActual(correlativo);
			getControlEspecieValoradaDAO().update(controlEspecieValorada);
		}
		/*END 15/06/2021 - javalos - Correlativo by caja*/

		//Valida si es un canje de PCE - 22/07/2021
		if(gastoAdministrativo.getVentaPasaje().getTipoComprobante().getId().intValue()==Constantes.ID_TIPCOM_GUIA_TRANSPORTISTA) {
			VentaPasaje guiaRemisionTrans= gastoAdministrativo.getVentaPasaje();
			guiaRemisionTrans.setTipoMovimiento(new TipoMovimiento(Constantes.ID_TIPMOV_ANULACION_SISTEMA));
			guiaRemisionTrans.setUsuario(gastoAdministrativo.getUsuario());
			guiaRemisionTrans.setUsuarioInsercion(gastoAdministrativo.getUsuarioInsercion());
			guiaRemisionTrans.setIpInsercion(gastoAdministrativo.getIpInsercion());
			guiaRemisionTrans.setUsuarioModificacion(gastoAdministrativo.getUsuarioModificacion());
			guiaRemisionTrans.setIpModificacion(gastoAdministrativo.getIpModificacion());

			/* Anula el movimiento anterior*/
			getVentaPasajesDAO().save(guiaRemisionTrans);
		}


		/*Guardando el gasto administrativo*/
		getVentaPasajesDAO().save(gastoAdministrativo);

		/*Actualizando con el numero de control*/
		String nControl = Util.generateControlNumber(Util.decimalToHexadecimal(gastoAdministrativo.getId()));
		gastoAdministrativo.setNumeroControl(nControl);
		gastoAdministrativo.setFechaInsercion(Util.StringtoDate(getVentaPasajesDAO().getDateSystem(), Constantes.DATE_TIME_FORMAT));
		getVentaPasajesDAO().update(gastoAdministrativo);

		result=Constantes.CORRECT;

		return result;
	}
//	/* (non-Javadoc)
//	 * @see com.tepsa.sisvyr.service.business.VentaPasajesManager#generarNotaCredito(java.util.List, com.tepsa.sisvyr.model.bean.TipoNota, boolean)
//	 */
//	@Transactional
//	@Override
//	public VentasNotas generarNotaCreditoRegularizacion(List<VentaPasaje> listVentas,TipoNota tipoNota, boolean newCompVenta) throws Exception {
//		Agencia agencia = (Agencia) Executions.getCurrent().getDesktop().getSession().getAttribute(Constantes.ATRIBUTO_AGENCIA);
//		Usuario usuario = (Usuario) Executions.getCurrent().getDesktop().getSession().getAttribute(Constantes.ATRIBUTO_USUARIO);
//		Date fechaLiquidacion=(Date)Executions.getCurrent().getDesktop().getSession().getAttribute(Constantes.ATRIBUTO_FECHA_LIQUIDACION);
//
//		List<VentaPasaje>listNotasCredito= new ArrayList<>();
//		List<VentaPasaje>listNuevoComprobantes= new ArrayList<>();
//
//		for(VentaPasaje ventaPasaje :listVentas){
//			boolean anularMovimiento=true;
//			/*Valida si debe anular o no el movimiento*/
//			if(ventaPasaje.getFechaPartida()!=null){
//				String fechaHoraPartida=Constantes.FORMAT_DATE.format(ventaPasaje.getFechaPartida())+" "+ventaPasaje.getHoraPartida();
//				Date dfechaHoraPartida=Constantes.FORMAT_LONG.parse(fechaHoraPartida);
//				Date date=Constantes.FORMAT_LONG.parse(MyTime.dateTimeServer());
//				if(date.getTime()>dfechaHoraPartida.getTime())
//					anularMovimiento=false;
//			}
//			VentaPasaje notacredito= generarNotaCredito(ventaPasaje, tipoNota, anularMovimiento, true);
//			listNotasCredito.add(notacredito);
//			/*actualiza el comprobante como pagado*/
//			String estadoComp=ventaPasaje.getEstadoDocumento();
//			ventaPasaje= getVentaPasajesDAO().buscarPorId(ventaPasaje.getId());
//			ventaPasaje.setEstadoDocumento(Constantes.ESTADO_DOCUMENTO_PAGADO);
//			getVentaPasajesDAO().update(ventaPasaje);
//
//			/*Valida si es necesario emitir un nuevo commprovante de venta*/
//			if(newCompVenta){
//				VentaPasaje newComprobante=(VentaPasaje) ventaPasaje.clone();
//				newComprobante.setId(null);
//				newComprobante.setTipoMovimiento(new TipoMovimiento(Constantes.ID_TIPMOV_REGULACION));
//				newComprobante.setAgencia(agencia);
//				newComprobante.setUsuario(usuario);
//				newComprobante.setFechaLiquidacion(fechaLiquidacion);
//				newComprobante.setLiquidacion(null);
//				newComprobante.setFechaTransferencia(null);
//				newComprobante.setEnviadoSFE(null);
//				newComprobante.setFechaEnvioSFE(null);
//				newComprobante.setEstadoDocumento(estadoComp);
//				UtilData.auditarRegistro(newComprobante, usuario, Executions.getCurrent());
//				int result=guardarVenta(newComprobante, false, true, false,false);
//				if(result==Constantes.CORRECT){
//					newComprobante.setVentaOriginal(ventaPasaje.getVentaOriginal());
//					newComprobante.setVentaPasaje(null);
//					getVentaPasajesDAO().update(newComprobante);
//					listNuevoComprobantes.add(newComprobante);
//				}
//			}
//		}
//
//		VentasNotas ventasNotas= new VentasNotas();
//		ventasNotas.setListNotasCredito(listNotasCredito);
//		ventasNotas.setListVentas(listNuevoComprobantes);
//
//		return ventasNotas;
//	}
	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.service.business.VentaPasajesManager#procesarAnulacionBy(java.util.List, int, boolean)
	 */
	@Override
	@Transactional
	public VentasNotas procesarAnulacionBy(List<VentaPasaje> lstVentas,int tipoAnulacion, boolean anularMovimiento, Liquidacion liquidacion, boolean ejecutarSeqByCorrelativo) throws Exception {
		VentasNotas ventasNotas= new VentasNotas();
		List<VentaPasaje>notasCredito= new ArrayList<>();
		List<VentaPasaje>nuevosComprobantes= new ArrayList<>();

		Integer horas_maximo=Constantes.HORAS_MAXIMO_ANULACION;
		for(VentaPasaje ventaPasaje : lstVentas){
			boolean aplicarNotaCredito=false;
			boolean emitirNuevoComprobante=false;
			Double importePagado=ventaPasaje.getImportePagado();
			switch (tipoAnulacion) {
				case Constantes.TIPO_ANULACION_REGULAR:
					/*Valida que la anulacion este dentro de las 72 horas, desde el dia siguiente a la emision*/
					Date dateStartLimit= new Date(ventaPasaje.getFechaLiquidacion().getTime()+Constantes.MILISEGUNDOS_X_DIA);
					long horasTrans= (new Date().getTime()-dateStartLimit.getTime())/Constantes.MILISEGUNDOS_X_HORA;
					if(horasTrans<=horas_maximo){
						Result result=WSFE.anularComprobante(ventaPasaje); //Primero anula en el WSFE
						if(result.isIsCorrect()){
							VentaPasaje anulacion=buscarPorId(ventaPasaje.getId());
							anulacion.setObservaciones(ventaPasaje.getObservaciones());
							anulacion.setFechaAnulacion(ventaPasaje.getFechaAnulacion()!=null?ventaPasaje.getFechaAnulacion(): new Date());
							anulacion.setUsuarioAnulacion(ventaPasaje.getUsuarioAnulacion()!=null?ventaPasaje.getUsuarioAnulacion():null);
							if(anulacion.getFormaPago().getId().intValue()==Constantes.ID_FORPAG_CREDITO && ventaPasaje.getVentaPasaje()!=null){
								/*Busca el Voucher y el registro de anulacion del Voucher*/
								TreeMap<String, Object>criteriosBusqueda= new TreeMap<>();
								criteriosBusqueda.put("numeroBoleto", anulacion.getNumeroBoletoAnterior());
								criteriosBusqueda.put("rucClienteCredito", anulacion.getRucClienteCredito());
								List<String>criteriosOrden= new ArrayList<>();
								criteriosOrden.add("id");
								List<VentaPasaje>lstVoucher=buscarPorX(criteriosBusqueda, criteriosOrden);
								if(lstVoucher.size()==2){
									VentaPasaje voucherOriginal=lstVoucher.get(0);
									VentaPasaje voucherAnulacionSistema=lstVoucher.get(1);
									/*Valida que realmente sea un voucher*/
									if(ventaPasaje.getVentaPasaje().getId().longValue()==voucherOriginal.getId().longValue() &&
											(voucherOriginal.getTipoComprobante().getId().intValue()==Constantes.ID_TIPCOM_VOUCHER_AGENCIA_VIAJES ||
											voucherOriginal.getTipoComprobante().getId().intValue()==Constantes.ID_TIPCOM_VOUCHER_CORPORATIVO)){
										//Realiza la anulacion del voucher
										voucherOriginal.setTipoMovimiento(new TipoMovimiento(Constantes.ID_TIPMOV_ANULACION));
										voucherOriginal.setTarifa(0.00);
										voucherOriginal.setDescuento(0.00);
										voucherOriginal.setPenalidad(0.00);
										voucherOriginal.setRecargo(0.00);
										voucherOriginal.setImportePagado(0.00);
										voucherOriginal.setVentaPasaje(null);
										getVentaPasajesDAO().update(voucherOriginal);

										//Elimina el segundo registro
										getVentaPasajesDAO().delete(voucherAnulacionSistema);
									}
								}else{
									//ERROR AL ANULAR EL VOUCHER (crear excepcion)
									throw new Exception("Error al anular el Voucher.");
								}
							}
							/*Anula el comprobante electronico*/
							anulacion.setTarifa(0.0);
							anulacion.setRecargo(0.0);
							anulacion.setDescuento(0.0);
							anulacion.setImportePagado(0.0);
							anulacion.setAcuenta(0.0);
							anulacion.setImportePagado(0.0);
							anulacion.setTipoMovimiento(new TipoMovimiento(Constantes.ID_TIPMOV_ANULACION));
							UtilData.auditarRegistro(anulacion, true,(Usuario)Executions.getCurrent().getDesktop().getSession().getAttribute(Constantes.ATRIBUTO_USUARIO), Executions.getCurrent());
							getVentaPasajesDAO().update(anulacion);
						}else
							throw new Exception("No se puedo continuar con la anulaciï¿½n, el comprobante Nï¿½ "+ventaPasaje.getNumeroBoleto()+" No existe en el S.F.E.");
					}else
						aplicarNotaCredito=true;
					break;
				case Constantes.TIPO_ANULACION_NC:
					aplicarNotaCredito=true;
					break;
				case Constantes.TIPO_ANULACION_NC_NEW_COMPROBANTE:
					aplicarNotaCredito=true;
					emitirNuevoComprobante=true;
					break;
				default:
					break;
			}

			/*Valida si debe o no aplicar una nota de credito al comprobante*/
			if(aplicarNotaCredito){
				TipoNota tipoNota=ServiceLocator.getTipoNotaManager().buscarPorId((long)Constantes.ID_TIPNOTA_ANULACION);
				VentaPasaje notaCredito = generarNotaCredito(ventaPasaje, tipoNota, anularMovimiento, true,liquidacion, ejecutarSeqByCorrelativo);
				notasCredito.add(notaCredito);

				/*Coloca al comprobante en estado pagado*/
				if(ventaPasaje.getFormaPago().getId().intValue()==Constantes.ID_FORPAG_CREDITO){
					VentaPasaje ventaCredito=buscarPorId(ventaPasaje.getId());
					ventaCredito.setEstadoDocumento(Constantes.ESTADO_DOCUMENTO_PAGADO);
					getVentaPasajesDAO().update(ventaCredito);
				}
			}

			/*Restaura su linea de credito del Cliente*/
			if(ventaPasaje.getFormaPago().getId().intValue()==Constantes.ID_FORPAG_CREDITO && ventaPasaje.getRucClienteCredito()!=null){
				List<Cliente> lstCliente= ServiceLocator.getClienteManager().buscarClienteAgencia(ventaPasaje.getRucClienteCredito());
				if(lstCliente.size()>0){
					LineaCreditoCliente lineaCreditoCliente= getLineaCreditoClienteDAO().buscarPorId(lstCliente.get(0).getLineaCreditoCliente().getId());
					if(lineaCreditoCliente!=null) {
						Double saldo=lineaCreditoCliente.getSaldo();
						saldo+=+importePagado;
						lineaCreditoCliente.setSaldo(saldo);
						getLineaCreditoClienteDAO().update(lineaCreditoCliente);	
					}
				}
			}

			/*Valida si debe o no emitir un nuevo comprobante*/
			if(emitirNuevoComprobante){
				VentaPasaje newComprobante=(VentaPasaje) ventaPasaje.clone();
				newComprobante.setId(null);
//				newComprobante.setTipoMovimiento(new TipoMovimiento(Constantes.ID_TIPMOV_REGULACION));
				newComprobante.setAgencia(liquidacion.getAgencia());
				newComprobante.setUsuario(liquidacion.getUsuario());
				newComprobante.setFechaLiquidacion(liquidacion.getFechaLiquidacion());
				newComprobante.setLiquidacion(null);
				newComprobante.setFechaTransferencia(null);
				newComprobante.setEnviadoSFE(null);
				newComprobante.setFechaEnvioSFE(null);
				newComprobante.setEstadoDocumento(ventaPasaje.getFormaPago().getId().intValue()==Constantes.ID_FORPAG_CREDITO?Constantes.ESTADO_DOCUMENTO_ACTIVO:Constantes.ESTADO_DOCUMENTO_PAGADO);
				UtilData.auditarRegistro(newComprobante, liquidacion.getUsuario(), Executions.getCurrent());
				int result=guardarVenta(newComprobante, false, true, false,false, ejecutarSeqByCorrelativo);
				if(result==Constantes.CORRECT){
					newComprobante.setVentaOriginal(ventaPasaje.getVentaOriginal());
					newComprobante.setVentaPasaje(null);
					getVentaPasajesDAO().update(newComprobante);
					nuevosComprobantes.add(newComprobante);
				}
			}

			/*Valida si es una cortesia para retornale sus puntos*/
			if (ventaPasaje.getFormaPago().getId().intValue()==Constantes.ID_FORPAG_CORTESIA && ventaPasaje.getTipoFormaPago().getId().intValue()==Constantes.ID_TIPFORPAG_PUNTOS){
				ServiceLocator.getPuntosPasajeroFrecuenteManager().restaurarPuntos(ventaPasaje.getVentaOriginal()); //Restaura los puntos utilizados para la cortesia por puntos
			}else if(ventaPasaje.getFormaPago().getId().intValue()!=Constantes.ID_FORPAG_CORTESIA){
				anularPuntosPaxFree(ventaPasaje);//Realiza la anulacion de los puntos del pax fre
			}
		}

		ventasNotas.setListNotasCredito(notasCredito);
		ventasNotas.setListVentas(nuevosComprobantes);
		return ventasNotas;
	}

	/*
	 * (non-Javadoc)
	 * @see com.cystesoft.vyrbus.service.business.VentaPasajesManager#buscarAvanceVentas(java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	public List<VentaPasaje> buscarAvanceVentas(String idOrigen, String idDestino, String idServicio, String fechaDesde, String fechaHasta) throws Exception {
		// TODO Auto-generated method stub
		return getVentaPasajesDAO().buscarAvanceVentas(idOrigen, idDestino, idServicio, fechaDesde, fechaHasta);
	}
	/*
	 * (non-Javadoc)
	 * @see com.cystesoft.vyrbus.service.business.VentaPasajesManager#buscarBoletosAnuladosByX(java.lang.String, java.lang.String, java.lang.Integer)
	 */
	@Override
	public List<ResumenAnulacionPostergacion> buscarBoletosAnuladosByX(String fechaDesde, String fechaHasta, Integer criterio){
		return getVentaPasajesDAO().buscarBoletosAnuladosByX(fechaDesde, fechaHasta, criterio);
	}
	/*
	 * (non-Javadoc)
	 * @see com.cystesoft.vyrbus.service.business.VentaPasajesManager#buscarBoletosAnuladosDetalladoByX(java.lang.String, java.lang.String, java.lang.Integer, java.lang.Integer)
	 */
	@Override
	public List<VentaPasaje> buscarBoletosAnuladosDetalladoByX(String fechaDesde, String fechaHasta, Integer id, Integer criterio){
		return getVentaPasajesDAO().buscarBoletosAnuladosDetalladoByX(fechaDesde, fechaHasta, id, criterio);
	}
	/*
	 * (non-Javadoc)
	 * @see com.cystesoft.vyrbus.service.business.VentaPasajesManager#buscarBoletosPostergadosByX(java.lang.String, java.lang.String, java.lang.Integer)
	 */
	@Override
	public List<ResumenAnulacionPostergacion> buscarBoletosPostergadosByX(String fechaDesde, String fechaHasta, Integer criterio, Integer nroPostergaciones){
		return getVentaPasajesDAO().buscarBoletosPostergadosByX(fechaDesde, fechaHasta, criterio, nroPostergaciones);
	}

	@Override
	public List<VentaPasaje> buscarBoletosPostergadosDetalladoByX(String fechaDesde, String fechaHasta, Integer id, Integer criterio, Integer nroPostergaciones){
		return getVentaPasajesDAO().buscarBoletosPostergadosDetalladoByX(fechaDesde, fechaHasta, id, criterio, nroPostergaciones);
	}

	@Override
	public List<ResumenVentas> buscarResumenVentas(String fechaDesde, String fechaHasta, Integer idAgencia, Integer nroConsulta){
		return getVentaPasajesDAO().buscarResumenVentas(fechaDesde, fechaHasta, idAgencia, nroConsulta);
	}

	@Override
	public List<VentaPasaje> buscarHistorialComprobante(String numeroComprobante){
		return getVentaPasajesDAO().buscarHistorialComprobante(numeroComprobante);
	}

	@Override
	@Transactional
	public int guardarServicioEspecial(VentaPasaje ventaPasaje, boolean ejecutarSeqByCorrelativo) throws Exception {
		ControlEspecieValorada controlEspecieValorada = UtilData.buscarEspecieValoradaByCaja(ventaPasaje.getTipoComprobante().getId(), ventaPasaje.getAgencia(), ejecutarSeqByCorrelativo, ventaPasaje.getUsuarioHardware(), null);
		ventaPasaje.setNumeroBoleto(controlEspecieValorada.toString());

		int result = Constantes.FAILURE;

		try {
		/*Actualiza el correlativo*/
//		if(ventaPasaje.getTipoComprobante().getId().intValue()!=Constantes.ID_TIPCOM_BOLETO_VIAJE){
			int position = ventaPasaje.getNumeroBoleto().indexOf("-");
			Long correlativo = Long.valueOf(ventaPasaje.getNumeroBoleto().substring(position+1))+1;
			controlEspecieValorada.setCorrelativoActual(correlativo);
			getControlEspecieValoradaDAO().update(controlEspecieValorada);
//		}
			getVentaPasajesDAO().guardarServicioEspecial(ventaPasaje);

			String nControl = Util.generateControlNumber(Util.decimalToHexadecimal(ventaPasaje.getId()));
			ventaPasaje.setNumeroControl(nControl);
			ventaPasaje.setFechaInsercion(Util.StringtoDate(getVentaPasajesDAO().getDateSystem(), Constantes.DATE_TIME_FORMAT));
			ventaPasaje.setVentaOriginal(ventaPasaje.getId());
			getVentaPasajesDAO().update(ventaPasaje);

			result  = Constantes.CORRECT;
		}catch (Exception e) {
			throw new Exception(e);
		}
		return result;
	}

	/* (non-Javadoc)
	 * @see com.cystesoft.vyrbus.service.business.VentaPasajesManager#procesarAnulacionServicioEspecial(java.util.List, int, boolean, com.cystesoft.vyrbus.model.bean.Liquidacion)
	 */
	@Override
	@Transactional
	public VentasNotas procesarAnulacionServicioEspecial(List<VentaPasaje> lstVentas, int tipoAnulacion, boolean anularMovimiento, Liquidacion liquidacion, boolean ejecutarSeqByCorrelativo) throws Exception {
		VentasNotas ventasNotas= new VentasNotas();
		List<VentaPasaje>notasCredito= new ArrayList<>();
		List<VentaPasaje>nuevosComprobantes= new ArrayList<>();

		Integer horas_maximo=Constantes.HORAS_MAXIMO_ANULACION;
		for(VentaPasaje ventaPasaje : lstVentas){
			boolean aplicarNotaCredito=false;
//			boolean emitirNuevoComprobante=false;
//			Double importePagado=ventaPasaje.getImportePagado();
			switch (tipoAnulacion) {
				case Constantes.TIPO_ANULACION_REGULAR:
					/*Valida que la anulacion este dentro de las 72 horas, desde el dia siguiente a la emision*/
					Date dateStartLimit= new Date(ventaPasaje.getFechaLiquidacion().getTime()+Constantes.MILISEGUNDOS_X_DIA);
					long horasTrans= (new Date().getTime()-dateStartLimit.getTime())/Constantes.MILISEGUNDOS_X_HORA;
					if(horasTrans<=horas_maximo){
						Result result=WSFE.anularComprobante(ventaPasaje); //Primero anula en el WSFE
						if(result.isIsCorrect()){
							VentaPasaje anulacion=buscarPorId(ventaPasaje.getId());
							anulacion.setObservaciones(ventaPasaje.getObservaciones());
							/*Anula el comprobante electronico*/
							anulacion.setTarifa(0.0);
							anulacion.setRecargo(0.0);
							anulacion.setDescuento(0.0);
							anulacion.setImportePagado(0.0);
							anulacion.setAcuenta(0.0);
							anulacion.setImportePagado(0.0);
							anulacion.setTipoMovimiento(new TipoMovimiento(Constantes.ID_TIPMOV_ANULACION));
							UtilData.auditarRegistro(anulacion, true,(Usuario)Executions.getCurrent().getDesktop().getSession().getAttribute(Constantes.ATRIBUTO_USUARIO), Executions.getCurrent());
							getVentaPasajesDAO().update(anulacion);
						}else
							throw new Exception("No se puedo continuar con la anulaciï¿½n, el comprobante Nï¿½ "+ventaPasaje.getNumeroBoleto()+" No existe en el S.F.E.");
					}else
						aplicarNotaCredito=true;
					break;
				case Constantes.TIPO_ANULACION_NC:
					aplicarNotaCredito=true;
					break;
				default:
					break;
			}

			/*Valida si debe o no aplicar una nota de credito al comprobante*/
			if(aplicarNotaCredito){
				TipoNota tipoNota=ServiceLocator.getTipoNotaManager().buscarPorId((long)Constantes.ID_TIPNOTA_ANULACION);
				VentaPasaje notaCredito = generarNotaCredito(ventaPasaje, tipoNota, anularMovimiento, true,liquidacion, ejecutarSeqByCorrelativo);
				notasCredito.add(notaCredito);

				/*Coloca al comprobante en estado pagado*/
				if(ventaPasaje.getFormaPago().getId().intValue()==Constantes.ID_FORPAG_CREDITO){
					VentaPasaje ventaCredito=buscarPorId(ventaPasaje.getId());
					ventaCredito.setEstadoDocumento(Constantes.ESTADO_DOCUMENTO_PAGADO);
					getVentaPasajesDAO().update(ventaCredito);
				}
			}

//			/*Restaura su linea de credito del Cliente*/
//			if(ventaPasaje.getFormaPago().getId().intValue()==Constantes.ID_FORPAG_CREDITO && ventaPasaje.getRucClienteCredito()!=null){
//				List<Cliente> lstCliente= ServiceLocator.getClienteManager().buscarClienteAgencia(ventaPasaje.getRucClienteCredito());
//				if(lstCliente.size()>0){
//					LineaCreditoCliente lineaCreditoCliente= getLineaCreditoClienteDAO().buscarPorId(lstCliente.get(0).getLineaCreditoCliente().getId());
//					Double saldo=lineaCreditoCliente.getSaldo();
//					saldo+=+importePagado;
//					lineaCreditoCliente.setSaldo(saldo);
//					getLineaCreditoClienteDAO().update(lineaCreditoCliente);
//				}
//			}
		}

		ventasNotas.setListNotasCredito(notasCredito);
		ventasNotas.setListVentas(nuevosComprobantes);
		return ventasNotas;
	}
	/* (non-Javadoc)
	 * @see com.cystesoft.vyrbus.service.business.VentaPasajesManager#buscarFacturasServicioEspecial(java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	public List<VentaPasaje> buscarFacturasServicioEspecial(String numComprobante, String fDesde, String fHasta) throws Exception {
		return getVentaPasajesDAO().buscarFacturasServicioEspecial(numComprobante, fDesde, fHasta);
	}

	/*
	 * (non-Javadoc)
	 * @see com.cystesoft.vyrbus.service.business.VentaPasajesManager#buscarVentasPagoPilotos(java.lang.String, java.lang.String)
	 */
	@Override
	public List<VentasPiloto> buscarVentasPagoPilotos(String fInicio, String fFin) throws Exception{
		return getVentaPasajesDAO().buscarVentasPagoPilotos(fInicio, fFin);
	}
	
	@Override
	public List<VentasPiloto> buscarRegistroVentas(String fInicio, String fFin) throws Exception{
		return getVentaPasajesDAO().buscarRegistroVentas(fInicio, fFin);
	}
	
	/* (non-Javadoc)
	 * @see com.cystesoft.vyrbus.service.business.VentaPasajesManager#buscarBoletosPerdidaServicio(java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	public List<VentaPasaje> buscarBoletosPerdidaServicio(String numeroDocumento, String numeroControl, String numeroBoleto) throws Exception {
		return getVentaPasajesDAO().buscarBoletosPerdidaServicio(numeroDocumento, numeroControl, numeroBoleto);
	}
	/* (non-Javadoc)
	 * @see com.cystesoft.vyrbus.service.business.VentaPasajesManager#guardarPerdidaServicio(java.lang.Long, java.lang.String)
	 */
	@Transactional
	@Override
	public void guardarPerdidaServicio(VentaPasaje perdidaServicio) throws Exception{
		getVentaPasajesDAO().guardarPerdidaServicio(perdidaServicio);
	}
	/* (non-Javadoc)
	 * @see com.cystesoft.vyrbus.service.business.VentaPasajesManager#buscarLiquidacionBus(java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	public List<Manifiesto> buscarLiquidacionBus(String fechaInicio, String fechaFin, String codigoBus) throws Exception {
		// TODO Auto-generated method stub
		return getVentaPasajesDAO().buscarLiquidacionBus(fechaInicio, fechaFin, codigoBus);
	}

	/* (non-Javadoc)
	 * @see com.cystesoft.vyrbus.service.business.VentaPasajesManager#buscarEquivalenciaEntidades(java.lang.Integer)
	 */
	@Override
	public Map<String, EntidadEncomiendaPasajes> buscarEquivalenciaEntidades(Integer tipoEntidad) throws Exception {
		// TODO Auto-generated method stub
		return getVentaPasajesDAO().buscarEquivalenciaEntidades(tipoEntidad);
	}
	
	/**
	 * @return the ventaPasajesHistorialDAO
	 */
	public VentaPasajesHistorialDAO getVentaPasajesHistorialDAO() {
		return ventaPasajesHistorialDAO;
	}
	/**
	 * @param ventaPasajesHistorialDAO the ventaPasajesHistorialDAO to set
	 */
	public void setVentaPasajesHistorialDAO(VentaPasajesHistorialDAO ventaPasajesHistorialDAO) {
		this.ventaPasajesHistorialDAO = ventaPasajesHistorialDAO;
	}


	private void copyHistoryVentaPasaje(Long ventaPasajeIdOriginal)throws Exception {
		VentaPasaje ventaPasaje = getVentaPasajesDAO().buscarPorId(ventaPasajeIdOriginal);
		
		VentaPasajeHistorial historial = new VentaPasajeHistorial();
		historial.setVentaPasajeId(ventaPasaje.getId());
		historial.setVentaPasajeIdRef(ventaPasaje.getVentaPasaje()!=null?ventaPasaje.getVentaPasaje().getId():null);
		historial.setVentaOriginal(ventaPasaje.getVentaOriginal()!=null?ventaPasaje.getVentaOriginal():null);
		historial.setItinerario(ventaPasaje.getItinerario()!=null?ventaPasaje.getItinerario():null);
		historial.setRuta(ventaPasaje.getRuta()!=null?ventaPasaje.getRuta():null);
		historial.setCliente(ventaPasaje.getCliente()!=null?ventaPasaje.getCliente():null);
		historial.setPasajero(ventaPasaje.getPasajero()!=null?ventaPasaje.getPasajero():null);
		historial.setFormaPago(ventaPasaje.getFormaPago()!=null?ventaPasaje.getFormaPago():null);
		historial.setServicio(ventaPasaje.getServicio()!=null?ventaPasaje.getServicio():null);
		historial.setTipoComprobante(ventaPasaje.getTipoComprobante()!=null?ventaPasaje.getTipoComprobante():null);
		historial.setTipoMovimiento(ventaPasaje.getTipoMovimiento()!=null?ventaPasaje.getTipoMovimiento():null);
		historial.setTipoFormaPago(ventaPasaje.getTipoFormaPago()!=null?ventaPasaje.getTipoFormaPago():null);
		historial.setTarjetaCredito(ventaPasaje.getTarjetaCredito()!=null?ventaPasaje.getTarjetaCredito():null);
		historial.setNumeroBoleto(ventaPasaje.getNumeroBoleto()!=null?ventaPasaje.getNumeroBoleto():null);
		historial.setNumeroBoletoAnterior(ventaPasaje.getNumeroBoletoAnterior()!=null?ventaPasaje.getNumeroBoletoAnterior():null);
		historial.setNumeroAsiento(ventaPasaje.getNumeroAsiento()!=null?ventaPasaje.getNumeroAsiento():null);
		historial.setNumeroPiso(ventaPasaje.getNumeroPiso()!=null?ventaPasaje.getNumeroPiso():null);
		historial.setNumeroControl(ventaPasaje.getNumeroControl()!=null?ventaPasaje.getNumeroControl():null);
		historial.setAgenciaPartida(ventaPasaje.getAgenciaPartida()!=null?ventaPasaje.getAgenciaPartida():null);
		historial.setFechaPartida(ventaPasaje.getFechaPartida()!=null?ventaPasaje.getFechaPartida():null);
		historial.setHoraPartida(ventaPasaje.getHoraPartida()!=null?ventaPasaje.getHoraPartida():null);
		historial.setAgenciaLlegada(ventaPasaje.getAgenciaLlegada()!=null?ventaPasaje.getAgenciaLlegada():null);
		historial.setFechaLlegada(ventaPasaje.getFechaLlegada()!=null?ventaPasaje.getFechaLlegada():null);
		historial.setHoraLllegada(ventaPasaje.getHoraLllegada()!=null?ventaPasaje.getHoraLllegada():null);
		historial.setSecuencial(ventaPasaje.getSecuencial()!=null?ventaPasaje.getSecuencial():null);
		historial.setTarifa(ventaPasaje.getTarifa()!=null?ventaPasaje.getTarifa():null);
		historial.setRecargo(ventaPasaje.getRecargo()!=null?ventaPasaje.getRecargo():null);
		historial.setDescuento(ventaPasaje.getDescuento()!=null?ventaPasaje.getDescuento():null);
		historial.setPenalidad(ventaPasaje.getPenalidad()!=null?ventaPasaje.getPenalidad():null);
		historial.setAcuenta(ventaPasaje.getAcuenta()!=null?ventaPasaje.getAcuenta():null);
		historial.setImportePagado(ventaPasaje.getImportePagado()!=null?ventaPasaje.getImportePagado():null);
		historial.setImportePagadoEfectivo(ventaPasaje.getImportePagadoEfectivo()!=null?ventaPasaje.getImportePagadoEfectivo():null);
		historial.setImportePagadoTarjeta(ventaPasaje.getImportePagadoTarjeta()!=null?ventaPasaje.getImportePagadoTarjeta():null);
		historial.setTipoTransaccion(ventaPasaje.getTipoTransaccion()!=null?ventaPasaje.getTipoTransaccion():null);
		historial.setFechaCaducidad(ventaPasaje.getFechaCaducidad()!=null?ventaPasaje.getFechaCaducidad():null);
		historial.setLiquidacion(ventaPasaje.getLiquidacion()!=null?ventaPasaje.getLiquidacion():null);
		historial.setAgencia(ventaPasaje.getAgencia()!=null?ventaPasaje.getAgencia():null);
		historial.setFechaLiquidacion(ventaPasaje.getFechaLiquidacion()!=null?ventaPasaje.getFechaLiquidacion():null);
		historial.setUsuario(ventaPasaje.getUsuario()!=null?ventaPasaje.getUsuario():null);
		historial.setCanalVenta(ventaPasaje.getCanalVenta()!=null?ventaPasaje.getCanalVenta():null);
		historial.setNumeroOperacionBancaria(ventaPasaje.getNumeroOperacionBancaria()!=null?ventaPasaje.getNumeroOperacionBancaria():null);
		historial.setFechaExpiracionReserva(ventaPasaje.getFechaExpiracionReserva()!=null?ventaPasaje.getFechaExpiracionReserva():null);
		historial.setHoraExpiracionReserva(ventaPasaje.getHoraExpiracionReserva()!=null?ventaPasaje.getHoraExpiracionReserva():null);
		historial.setPreferenciaAlimentaria(ventaPasaje.getPreferenciaAlimentaria()!=null?ventaPasaje.getPreferenciaAlimentaria():null);
		historial.setIdaRetorno(ventaPasaje.getIdaRetorno()!=null?ventaPasaje.getIdaRetorno():null);
		historial.setRucClienteCredito(ventaPasaje.getRucClienteCredito()!=null?ventaPasaje.getRucClienteCredito():null);
		historial.setEsFechaAbierta(ventaPasaje.getEsFechaAbierta()!=null?ventaPasaje.getEsFechaAbierta():null);
		historial.setObservaciones(ventaPasaje.getObservaciones()!=null?ventaPasaje.getObservaciones():null);
		historial.setPromocion(ventaPasaje.getPromocion()!=null?ventaPasaje.getPromocion():null);
		historial.setEstadoRegistro(ventaPasaje.getEstadoRegistro()!=null?ventaPasaje.getEstadoRegistro():null);
		historial.setFechaInsercion(ventaPasaje.getFechaInsercion()!=null?ventaPasaje.getFechaInsercion():null);
		historial.setUsuarioInsercion(ventaPasaje.getUsuarioInsercion()!=null?ventaPasaje.getUsuarioInsercion():null);
		historial.setIpInsercion(ventaPasaje.getIpInsercion()!=null?ventaPasaje.getIpInsercion():null);
		historial.setFechaModificacion(ventaPasaje.getFechaModificacion()!=null?ventaPasaje.getFechaModificacion():null);
		historial.setUsuarioModificacion(ventaPasaje.getUsuarioModificacion()!=null?ventaPasaje.getUsuarioModificacion():null);
		historial.setIpModificacion(ventaPasaje.getIpModificacion()!=null?ventaPasaje.getIpModificacion():null);
		historial.setCentroCosto(ventaPasaje.getCentroCosto()!=null?ventaPasaje.getCentroCosto():null);
		historial.setFechaTransferencia(ventaPasaje.getFechaTransferencia()!=null?ventaPasaje.getFechaTransferencia():null);
		historial.setEstadoDocumento(ventaPasaje.getEstadoDocumento()!=null?ventaPasaje.getEstadoDocumento():null);
		historial.setEnviadoSFE(ventaPasaje.getEnviadoSFE()!=null?ventaPasaje.getEnviadoSFE():null);
		historial.setFechaEnvioSFE(ventaPasaje.getFechaEnvioSFE()!=null?ventaPasaje.getFechaEnvioSFE():null);
		historial.setImportePagadoByDiferencia(ventaPasaje.getImportePagadoByDiferencia()!=null?ventaPasaje.getImportePagadoByDiferencia():null);
		
		getVentaPasajesHistorialDAO().guardar(historial);
		
	}
	/* (non-Javadoc)
	 * @see com.cystesoft.vyrbus.service.business.VentaPasajesManager#postergarFAMasivo(java.util.List)
	 */
	@Override
	@Transactional
	public Integer postergarFAMasivo(List<VentaPasaje> lstVentas, String motivo, String usuario) throws Exception {
		try {
			int result = 0;
			for(VentaPasaje venta : lstVentas) {
				VentaPasaje ventaPostergar = getVentaPasajesDAO().buscarPorId(venta.getId());
				copyHistoryVentaPasaje(ventaPostergar.getId());
				ventaPostergar.setVentaPasaje(ventaPostergar);
				ventaPostergar.setItinerario(new Itinerario(new Long(1)));
				ventaPostergar.setTipoMovimiento(new TipoMovimiento(9));
				ventaPostergar.setNumeroAsiento(null);
				ventaPostergar.setNumeroPiso(null);
				ventaPostergar.setAgenciaPartida(null);
				ventaPostergar.setFechaPartida(null);
				ventaPostergar.setHoraPartida(null);
				ventaPostergar.setAgenciaLlegada(null);
				ventaPostergar.setFechaLlegada(null);
				ventaPostergar.setHoraLllegada(null);
				ventaPostergar.setEsFechaAbierta(1);
				ventaPostergar.setObservaciones(motivo);
				ventaPostergar.setUsuarioModificacion(usuario);
				getVentaPasajesDAO().update(ventaPostergar);
			}
			result = 1;
			return result;
		}catch (Exception ex) {
			throw new Exception(ex.getMessage());
		}
	}
	/* (non-Javadoc)
	 * @see com.cystesoft.vyrbus.service.business.VentaPasajesManager#actualizarCorrelativoComprobante(java.lang.Object, java.lang.Boolean)
	 */
	@Transactional
	@Override
	public Object actualizarCorrelativoComprobante(Object object, Boolean ejecutarSeqByCorrelativo) throws Exception {
		// TODO Auto-generated method stub
		
		if(object instanceof VentaPasaje) {
			VentaPasaje ventaPasaje = (VentaPasaje)object;
			/* Valida si no es un servicio especial*/
			if(!(ventaPasaje.getServicioEspecialFactura())){
				if(ventaPasaje.getTipoTransaccion().equals(Constantes.TIPO_OPERACION_VENTA) ||
						ventaPasaje.getTipoTransaccion().equals(Constantes.TIPO_OPERACION_VENTA_POOL) ||
						ventaPasaje.getTipoTransaccion().equals(Constantes.TIPO_OPERACION_EXCESO)){
					ControlEspecieValorada controlEspecieValorada = null;
					if(ventaPasaje.getTipoComprobante().getId().intValue()!=Constantes.ID_TIPCOM_BOLETO_VIAJE){
						
						Integer aplicarA = null;
						if(ventaPasaje.getTipoComprobante().getId()==Constantes.ID_TIPCOM_NOTA_CREDITO || ventaPasaje.getTipoComprobante().getId()==Constantes.ID_TIPCOM_NOTA_DEBITO) {
							if(ventaPasaje.getVentaPasaje().getTipoComprobante().getId().intValue()==Constantes.ID_TIPCOM_BOLETA_VENTA)
								aplicarA = Constantes.APLICAR_NC_A_BOLETA;
							else if(ventaPasaje.getVentaPasaje().getTipoComprobante().getId().intValue()==Constantes.ID_TIPCOM_FACTURA)
								aplicarA = Constantes.APLICAR_NC_A_FACTURA;	
						}						
						
						controlEspecieValorada = UtilData.buscarEspecieValoradaByCaja(ventaPasaje.getTipoComprobante().getId(), ventaPasaje.getAgencia(), ejecutarSeqByCorrelativo, ventaPasaje.getUsuarioHardware(), aplicarA);
						ventaPasaje.setNumeroBoleto(controlEspecieValorada.toString());
					}
					/*	Validando que el numero del comprobante no exista en la DB 	*/
					if(isBoletoDuplicado(ventaPasaje.getNumeroBoleto(), ventaPasaje.getTipoComprobante().getId()))
						throw new NumeroBoletoDuplicadoException();
					
					/*Actualiza el correlativo*/
					if(ventaPasaje.getTipoComprobante().getId().intValue()!=Constantes.ID_TIPCOM_BOLETO_VIAJE){
						int position = ventaPasaje.getNumeroBoleto().indexOf("-");
						Long correlativo = Long.valueOf(ventaPasaje.getNumeroBoleto().substring(position+1))+1;
						controlEspecieValorada.setCorrelativoActual(correlativo);
						getControlEspecieValoradaDAO().update(controlEspecieValorada);
					}
				}else
					ventaPasaje.setNumeroBoleto(null);
			}
		}
		
		
		
		return null;
	}

//	private String generarBoleto(String numBoleto, Integer idTipoComprobante, Integer idUsuarioHW) throws Exception{
//		int position = numBoleto.indexOf("-");
//		String serie = numBoleto.substring(0, position);
//		/*	Buscamos el ultimo correlativo de las especies valoradas	*/
//		ControlEspecieValorada especieValorada = getControlEspecieValoradaDAO().buscarUltimoCorrelativoEspecieValorada(idTipoComprobante, idUsuarioHW, serie);
//		Long correlativo = especieValorada.getCorrelativoActual()+1;
//		String sCorrelativo = "0000000"+correlativo;
//		String boleto = serie+"-"+sCorrelativo.substring(sCorrelativo.length()-7);
//		return boleto;
//	}
}
