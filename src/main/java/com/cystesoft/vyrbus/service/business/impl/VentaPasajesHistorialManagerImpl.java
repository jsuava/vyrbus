/**
 * Proyecto		: SISVYR
 * Sistema		: Sistema de Ventas y Reservas
 * Descripción	: Implementación de métodos que permiten el acceso al modelo.
 * Autor		: José Sullo Avalos
 * Fecha		: 28/09/2012
 */
package com.cystesoft.vyrbus.service.business.impl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TreeMap;

import org.springframework.transaction.annotation.Transactional;
import org.zkoss.zk.ui.Executions;

import com.cystesoft.vyrbus.model.bean.Agencia;
import com.cystesoft.vyrbus.model.bean.CanalVenta;
import com.cystesoft.vyrbus.model.bean.Cliente;
import com.cystesoft.vyrbus.model.bean.ControlEspecieValorada;
import com.cystesoft.vyrbus.model.bean.DestinatariosEmails;
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
import com.cystesoft.vyrbus.service.business.VentaPasajesHistorialManager;
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
public class VentaPasajesHistorialManagerImpl implements VentaPasajesHistorialManager {
	private VentaPasajesHistorialDAO ventaPasajesHistorialDAO;

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

	/* (non-Javadoc)
	 * @see com.cystesoft.vyrbus.service.business.VentaPasajesHistorialManager#buscarPorEstadoRegistro(java.lang.String, java.lang.String)
	 */
	@Override
	public ArrayList<VentaPasajeHistorial> buscarPorEstadoRegistro(String estado, String criterioOrden) {
		// TODO Auto-generated method stub
		return getVentaPasajesHistorialDAO().buscarPorEstadoRegistro(estado, criterioOrden);
	}

	/* (non-Javadoc)
	 * @see com.cystesoft.vyrbus.service.business.VentaPasajesHistorialManager#buscarPorX(java.util.TreeMap, java.util.List)
	 */
	@Override
	public ArrayList<VentaPasajeHistorial> buscarPorX(TreeMap<String, Object> criteriosBusqueda,
			List<String> criteriosOrdenar) {
		// TODO Auto-generated method stub
		return getVentaPasajesHistorialDAO().buscarPorX(criteriosBusqueda, criteriosOrdenar);
	}

	/* (non-Javadoc)
	 * @see com.cystesoft.vyrbus.service.business.VentaPasajesHistorialManager#buscarPorId(java.lang.Long)
	 */
	@Override
	public VentaPasajeHistorial buscarPorId(Long id) {
		// TODO Auto-generated method stub
		return getVentaPasajesHistorialDAO().buscarPorId(id);
	}

	/* (non-Javadoc)
	 * @see com.cystesoft.vyrbus.service.business.VentaPasajesHistorialManager#guardar(com.cystesoft.vyrbus.model.bean.VentaPasajeHistorial)
	 */
	@Override
	public void guardar(VentaPasajeHistorial ventaPasajeHistorial) {
		// TODO Auto-generated method stub
		getVentaPasajesHistorialDAO().guardar(ventaPasajeHistorial);
	}

	/* (non-Javadoc)
	 * @see com.cystesoft.vyrbus.service.business.VentaPasajesHistorialManager#actualizar(com.cystesoft.vyrbus.model.bean.VentaPasajeHistorial)
	 */
	@Override
	public void actualizar(VentaPasajeHistorial ventaPasajeHistorial) {
		// TODO Auto-generated method stub
		getVentaPasajesHistorialDAO().actualizar(ventaPasajeHistorial);
	}
	
}
