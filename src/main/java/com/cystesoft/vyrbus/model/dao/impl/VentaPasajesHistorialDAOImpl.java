/**
 * Proyecto		: SISVYR
 * Sistema		: Sistema de Ventas y Reservas
 * Descripción	: Implementación de metodos relacionados con la Venta de Pasajes.
 * Autor		: José Sullo Avalos
 * Fecha		: 09/07/2012
 */
package com.cystesoft.vyrbus.model.dao.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.TreeMap;

import org.hibernate.SQLQuery;

import com.cystesoft.vyrbus.model.bean.Agencia;
import com.cystesoft.vyrbus.model.bean.Bus;
import com.cystesoft.vyrbus.model.bean.CanalVenta;
import com.cystesoft.vyrbus.model.bean.CentroCosto;
import com.cystesoft.vyrbus.model.bean.Cliente;
import com.cystesoft.vyrbus.model.bean.Concesionario;
import com.cystesoft.vyrbus.model.bean.EstadoCivil;
import com.cystesoft.vyrbus.model.bean.FormaPago;
import com.cystesoft.vyrbus.model.bean.Itinerario;
import com.cystesoft.vyrbus.model.bean.Liquidacion;
import com.cystesoft.vyrbus.model.bean.Localidad;
import com.cystesoft.vyrbus.model.bean.Manifiesto;
import com.cystesoft.vyrbus.model.bean.Nacionalidad;
import com.cystesoft.vyrbus.model.bean.OperadorTarjetaCredito;
import com.cystesoft.vyrbus.model.bean.Pasajero;
import com.cystesoft.vyrbus.model.bean.PreferenciaAlimentaria;
import com.cystesoft.vyrbus.model.bean.Promocion;
import com.cystesoft.vyrbus.model.bean.Ruta;
import com.cystesoft.vyrbus.model.bean.Servicio;
import com.cystesoft.vyrbus.model.bean.Sexo;
import com.cystesoft.vyrbus.model.bean.TarjetaCredito;
import com.cystesoft.vyrbus.model.bean.TipoAgencia;
import com.cystesoft.vyrbus.model.bean.TipoCobranza;
import com.cystesoft.vyrbus.model.bean.TipoComprobante;
import com.cystesoft.vyrbus.model.bean.TipoDocumento;
import com.cystesoft.vyrbus.model.bean.TipoFormaPago;
import com.cystesoft.vyrbus.model.bean.TipoMoneda;
import com.cystesoft.vyrbus.model.bean.TipoMovimiento;
import com.cystesoft.vyrbus.model.bean.Ubigeo;
import com.cystesoft.vyrbus.model.bean.Usuario;
import com.cystesoft.vyrbus.model.bean.VentaPasaje;
import com.cystesoft.vyrbus.model.bean.VentaPasajeHistorial;
import com.cystesoft.vyrbus.model.bean.report.RptVentaUsuario;
import com.cystesoft.vyrbus.model.dao.VentaPasajesDAO;
import com.cystesoft.vyrbus.model.dao.VentaPasajesHistorialDAO;
import com.cystesoft.vyrbus.service.locator.ServiceLocator;
import com.cystesoft.vyrbus.service.mappers.ResumenAnulacionPostergacion;
import com.cystesoft.vyrbus.service.mappers.ResumenVentas;
import com.cystesoft.vyrbus.service.mappers.SecuenciaTramo;
import com.cystesoft.vyrbus.service.mappers.VentasPiloto;
import com.cystesoft.vyrbus.service.util.Constantes;
import com.cystesoft.vyrbus.service.util.MyTime;
import com.cystesoft.vyrbus.service.util.Util;

/**
 *
 * @author José Sullo Avalos
 * @since JDK1.6
 */
@SuppressWarnings("unchecked")
public class VentaPasajesHistorialDAOImpl extends GenericDAOImpl implements VentaPasajesHistorialDAO {

	/* (non-Javadoc)
	 * @see com.cystesoft.vyrbus.model.dao.VentaPasajesHistorialDAO#buscarPorEstadoRegistro(java.lang.String, java.lang.String)
	 */
	@Override
	public ArrayList<VentaPasajeHistorial> buscarPorEstadoRegistro(String estado, String criterioOrden) {
		// TODO Auto-generated method stub
		return (ArrayList<VentaPasajeHistorial>) super.findByEstadoRegistro(VentaPasajeHistorial.class, estado, criterioOrden);
	}

	/* (non-Javadoc)
	 * @see com.cystesoft.vyrbus.model.dao.VentaPasajesHistorialDAO#buscarPorX(java.util.TreeMap, java.util.List)
	 */
	@Override
	public ArrayList<VentaPasajeHistorial> buscarPorX(TreeMap<String, Object> criteriosBusqueda,
			List<String> criteriosOrdenar) {
		// TODO Auto-generated method stub
		return (ArrayList<VentaPasajeHistorial>) super.findByX(VentaPasajeHistorial.class, criteriosBusqueda, criteriosOrdenar);
	}

	/* (non-Javadoc)
	 * @see com.cystesoft.vyrbus.model.dao.VentaPasajesHistorialDAO#buscarPorId(java.lang.Long)
	 */
	@Override
	public VentaPasajeHistorial buscarPorId(Long id) {
		// TODO Auto-generated method stub
		return (VentaPasajeHistorial) super.findById(VentaPasajeHistorial.class, id);
	}

	/* (non-Javadoc)
	 * @see com.cystesoft.vyrbus.model.dao.VentaPasajesHistorialDAO#guardar(com.cystesoft.vyrbus.model.bean.VentaPasajeHistorial)
	 */
	@Override
	public void guardar(VentaPasajeHistorial ventaPasajeHistorial) {
		// TODO Auto-generated method stub
		super.save(ventaPasajeHistorial);
	}

	/* (non-Javadoc)
	 * @see com.cystesoft.vyrbus.model.dao.VentaPasajesHistorialDAO#actualizar(com.cystesoft.vyrbus.model.bean.VentaPasajeHistorial)
	 */
	@Override
	public void actualizar(VentaPasajeHistorial ventaPasajeHistorial) {
		// TODO Auto-generated method stub
		super.update(ventaPasajeHistorial);
	}

	
}

