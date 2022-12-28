/**
 * Proyecto		: SISVYR
 * Sistema		: Sistema de Ventas y Reservas
 * Descripción	:
 * Autor		: José Abanto
 * Fecha		: 2 may. 2022
 * Hora			: 22:44:11
 */
package com.cystesoft.vyrbus.model.dao.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.TreeMap;

import org.jfree.util.Log;
import org.springframework.jdbc.core.JdbcTemplate;

import com.cystesoft.vyrbus.model.bean.Agencia;
import com.cystesoft.vyrbus.model.bean.Bus;
import com.cystesoft.vyrbus.model.bean.CanalVenta;
import com.cystesoft.vyrbus.model.bean.FormaPago;
import com.cystesoft.vyrbus.model.bean.Itinerario;
import com.cystesoft.vyrbus.model.bean.Liquidacion;
import com.cystesoft.vyrbus.model.bean.Manifiesto;
import com.cystesoft.vyrbus.model.bean.OperadorTarjetaCredito;
import com.cystesoft.vyrbus.model.bean.Pasajero;
import com.cystesoft.vyrbus.model.bean.Ruta;
import com.cystesoft.vyrbus.model.bean.TipoComprobante;
import com.cystesoft.vyrbus.model.bean.TipoFormaPago;
import com.cystesoft.vyrbus.model.bean.TipoMoneda;
import com.cystesoft.vyrbus.model.bean.TipoMovimiento;
import com.cystesoft.vyrbus.model.bean.TranscarLiquidacionTurno;
import com.cystesoft.vyrbus.model.bean.TranscarRolUsuario;
import com.cystesoft.vyrbus.model.bean.TranscarUsuarioPersonal;
import com.cystesoft.vyrbus.model.bean.Usuario;
import com.cystesoft.vyrbus.model.bean.VentaPasaje;
import com.cystesoft.vyrbus.model.dao.TranscarWebDAO;
import com.cystesoft.vyrbus.service.mappers.ResumenVentas;
import com.cystesoft.vyrbus.service.mappers.VentasPiloto;
import com.cystesoft.vyrbus.service.util.Constantes;
import com.cystesoft.vyrbus.service.util.Messages;
import com.cystesoft.vyrbus.service.util.Util;
import com.cystesoft.vyrbus.service.util.UtilData;

/**
 * @author abant
 *
 */
@SuppressWarnings("unchecked")
public class TranscarWebDAOImpl implements TranscarWebDAO{

	private JdbcTemplate jdbcTemplate;
	private int ID_TIPPAG_EFECTIVO = 1;
	private int ID_TIPPAG_YAPE = 3;
//	private int ID_TIPPAG_TARJETA = 2;
	private int ID_FORPAG_CONTADO = 1;
	private int ID_FORPAG_CREDITO = 2;
	private int ID_FORPAG_PCE = 3;
//	private int ID_FORPAG_CORTESIA = 3;
	private int ID_TIPCOM_FACTURA = 2;
	private int ID_TIPCOM_BOLETA = 1;
	private int ID_TIPCOM_NOTACREDITO = 7;
	private int ID_TIPCOM_PCE = 3;
//	private int ID_OPE_TARJETA_VISA = 1;
//	private int ID_OPE_TARJETA_MASTERCARD = 2;

	/**
	 * @return the jdbcTemplate
	 */
	public JdbcTemplate getJdbcTemplate() {
		return jdbcTemplate;
	}

	/**
	 * @param jdbcTemplate the jdbcTemplate to set
	 */
	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}


	/* (non-Javadoc)
	 * @see com.cystesoft.vyrbus.model.dao.TranscarWebDAO#buscarRolesUsuario()
	 */
	@Override
	public List<TranscarRolUsuario> buscarRolesUsuario() throws Exception {

		String sql = "Select r.rol_id, r.c_denominacion From TCMROL r Where r.c_estreg='A'";
		List<?> result=getJdbcTemplate().queryForList(sql);

		Map<String, Object> map = new HashMap<>();
		List<TranscarRolUsuario> resultRolUsuario= new ArrayList<>();
		for(int i=0;i<result.size();i++){
			map = (Map<String, Object>)result.get(i);
			TranscarRolUsuario rol = new TranscarRolUsuario();
			rol.setId(((BigDecimal)map.get("ROL_ID")).intValue());
			rol.setNombre(map.get("C_DENOMINACION").toString());

			resultRolUsuario.add(rol);
		}

		return resultRolUsuario;
	}

	/* (non-Javadoc)
	 * @see com.cystesoft.vyrbus.model.dao.TranscarWebDAO#buscarRolesUsuario(java.lang.Integer)
	 */
	@Override
	public List<TranscarRolUsuario> buscarRolesUsuario(Integer usuarioId) throws Exception {

		String sql= "Select u.usuario_id, u.c_login, r.rol_id, r.c_denominacion rol " +
				"From tcmusuario u " +
				" Inner Join tctusuario_rol ur On (ur.usuario_id=u.usuario_id) " +
				" Inner Join tcmrol r On (r.rol_id=ur.rol_id) " +
				"Where u.usuario_id =" + usuarioId + " " +
				"  And ur.c_estreg = 'A'";

		List<?> result=getJdbcTemplate().queryForList(sql);

		List<TranscarRolUsuario> resultRolesUsuario = new ArrayList<>();
		Map<String, Object> map = new HashMap<>();
		for(int i=0;i<result.size();i++){
			map = (Map<String, Object>)result.get(i);

			TranscarRolUsuario rolUsuario = new TranscarRolUsuario();
			rolUsuario.setTranscarUsuarioPersonal(new TranscarUsuarioPersonal(((BigDecimal)map.get("USUARIO_ID")).intValue(), map.get("C_LOGIN").toString()));
			rolUsuario.setId(((BigDecimal)map.get("ROL_ID")).intValue());
			rolUsuario.setNombre(map.get("ROL").toString());

			resultRolesUsuario.add(rolUsuario);
		}

		return resultRolesUsuario;
	}

	/* (non-Javadoc)
	 * @see com.cystesoft.vyrbus.model.dao.TranscarWebDAO#buscarUsuario(java.lang.String)
	 */
	@Override
	public TranscarUsuarioPersonal buscarUsuario(String login) throws Exception {
		// TODO Auto-generated method stub

		String sql = "Select u.usuario_id, u.c_login From tcmusuario u Where u.c_login = '"+login+"' And u.c_estreg = 'A'";

		List<?> result=getJdbcTemplate().queryForList(sql);

		TranscarUsuarioPersonal usuarioPersonal=null;
		Map<String, Object> map = new HashMap<>();
		for (Object element : result) {
			map = (Map<String, Object>)element;
			usuarioPersonal = new TranscarUsuarioPersonal();
			usuarioPersonal.setId(((BigDecimal)map.get("USUARIO_ID")).intValue());
			usuarioPersonal.setLogin(map.get("C_LOGIN").toString());
		}

		return usuarioPersonal;
	}

	/* (non-Javadoc)
	 * @see com.cystesoft.vyrbus.model.dao.TranscarWebDAO#guardarUsuario(com.cystesoft.vyrbus.model.bean.TranscarUsuarioPersonal, java.lang.String, boolean)
	 */
	@Override
	public void guardarUsuario(TranscarUsuarioPersonal transcarUsuario, String idsRoles, boolean isNuevo) throws Exception {
		Integer agencia_idtranscar = UtilData.getAgencia_Idtranscarweb(transcarUsuario.getAgenciaId());

		//Valida que la agencia del vyrbus este asociada a la del transcar web
		if(agencia_idtranscar==null) {
			throw new Exception(Messages.getString("Generales.informacion.agenciaNoAsociadaTranscarweb"));
		}

		String sql = null;

		Integer usuario_id = null;
		//Cuando es un usuario nuevo
		if(transcarUsuario.getId()==null) {
			//Genera el identificador para el UsuarioHardware
			sql = "select nextval('seq_tcmusuhard_id') usuhard_id ";
			Integer usuarioHardware_id = getJdbcTemplate().queryForInt(sql);
			String direccionMac = getDireccionMacDinamica();
			String codigo = Util.generarCodigo(direccionMac);

			//Realiza el insert del usuario hardware
			sql = "INSERT INTO tcmusuhard "
					+ "VALUES ( "
					+ " " + usuarioHardware_id + " "
					+ "," + agencia_idtranscar + " "
					+ ",'"+ codigo +"' "
					+ ",'"+ direccionMac + "' "
					+ ",'PC-"+ transcarUsuario.getLogin().toUpperCase() + "' "
					+ ",'A' "
					+ ",current_timestamp "
					+ ",'"+ transcarUsuario.getUsuarioInsercion() + "' "
					+ ",'"+ transcarUsuario.getIpInsercion() + "' "
					+ ",current_timestamp "
					+ ",'"+ transcarUsuario.getUsuarioInsercion() + "' "
					+ ",'"+ transcarUsuario.getIpInsercion() + "' "
					+ ") ";
			getJdbcTemplate().update(sql);


			//Genera el identificador para el usuario
			sql = "select nextval('seq_tcmusuario_id') usuario_id ";
			usuario_id= getJdbcTemplate().queryForInt(sql);

			//Inserta el usuario
			sql = "INSERT INTO tcmusuario (usuario_id, agencia_id, c_apepat, c_apemat, c_nombre, c_email, c_login, c_password, audipinse, audipmodi, audusuins, audusumod, usuhard_id, n_tipseg ) " +
					"VALUES ( "
					+ usuario_id +", "
					+ agencia_idtranscar + ","
					+ "'"+transcarUsuario.getApellidoParterno()+ "', "
					+ (transcarUsuario.getApellidoMaterno()!=null? "'"+transcarUsuario.getApellidoMaterno()+ "'":"Null") + ", "
					+ "'"+transcarUsuario.getNombres()+ "', "
					+ (transcarUsuario.getEmail()!=null? "'"+transcarUsuario.getEmail()+ "'": "Null") + ", "
					+ "'"+transcarUsuario.getLogin()+ "', "
					+ "'XENCRIPTAR "+transcarUsuario.getPassword()+ "', "
					+ "'"+transcarUsuario.getIpInsercion()+ "', "
					+ "'"+transcarUsuario.getIpModificacion()+ "', "
					+ "'"+transcarUsuario.getUsuarioInsercion()+ "', "
					+ "'"+transcarUsuario.getUsuarioModificacion()+ "', "
					+ ""+usuarioHardware_id+ ", "
					+ ""+Constantes.FALSE_VALUE+ " "
					+ ")";
			getJdbcTemplate().update(sql);
		}else {

			//Cuando es una modificación
			sql = "UPDATE tcmusuario SET "
					+ " c_apepat='"+transcarUsuario.getApellidoParterno()+"' "
					+ ",c_apemat="+(transcarUsuario.getApellidoMaterno()!=null? "'"+transcarUsuario.getApellidoMaterno()+"' ":"Null ")
					+ ",c_nombre='"+transcarUsuario.getNombres()+"' "
					+ ",c_email="+(transcarUsuario.getEmail()!=null? "'"+transcarUsuario.getEmail()+"' ":"Null ")
					+ ",c_password='XENCRIPTAR "+transcarUsuario.getPassword()+"' "
					+ ",audipmodi='"+transcarUsuario.getIpModificacion()+"' "
					+ ",audusumod='"+transcarUsuario.getUsuarioModificacion()+"' "
					+ ",c_estreg='"+transcarUsuario.getEstadoRegistro()+"' "
				+ "WHERE usuario_id="+transcarUsuario.getId();
			getJdbcTemplate().update(sql);
						
			if(idsRoles!=null ) {
				//Elimina los roles asocuados al usario, para luego insertar los nuevos enviados
				sql = "DELETE FROM tctusuario_rol ur WHERE ur.usuario_id="+transcarUsuario.getId();
				getJdbcTemplate().update(sql);
			}			
		}

		//Inserta los roles

		if(idsRoles !=null) {
			String[] roles = idsRoles.split(",");
			for(String rol_id: roles) {			
				sql = "INSERT INTO tctusuario_rol (rol_id, usuario_id) VALUES ("+rol_id+", "+usuario_id+" )";
				getJdbcTemplate().update(sql);	
			}		
		}				

	}

//	/* (non-Javadoc)
//	 * @see com.cystesoft.vyrbus.model.dao.TranscarWebDAO#buscarIdAgenciaByCodigoAgenciaPasajes(java.lang.String)
//	 */
//	@Override
//	public Integer buscarIdAgenciaByCodigoAgenciaPasajes(String codigoAgenciaPasajes) throws Exception {
//		// TODO Auto-generated method stub
//		return null;
//	}

	/* (non-Javadoc)
	 * @see com.cystesoft.vyrbus.model.dao.TranscarWebDAO#aperturarLiquidacion(com.cystesoft.vyrbus.model.bean.TranscarLiquidacionTurno)
	 */
	@Override
	public String aperturarLiquidacion(TranscarLiquidacionTurno liquidacionTurno, boolean isReapertura) throws Exception {
		int isCorret = 0;
		Integer agencia_idtranscar = UtilData.getAgencia_Idtranscarweb(liquidacionTurno.getAgenciaId());
		//Valida que la agencia del vyrbus este asociada a la del transcar web
		if(agencia_idtranscar==null) {
			throw new Exception(Messages.getString("Generales.informacion.agenciaNoAsociadaTranscarweb"));
		}		
		
		//Valida si es una reapertura
		if(isReapertura) {
			String sql =  "UPDATE tctliquidacion "
					    + "   SET n_estliq="+ Constantes.TRUE_VALUE
					    + "      ,n_totefeing=.00 "
					    + "      ,n_totefeliq=.00 "
					    + "WHERE agencia_id = "+ agencia_idtranscar +" "
					    + "  AND usuario_id="+ liquidacionTurno.getTranscarUsuarioPersonal().getId() +" "
					    + "  AND d_fecliq ='"+ Constantes.FORMAT_DATE.format(liquidacionTurno.getFechaApertura()) +"' ";
			Log.info("Reapertura Liquidacion - Transcar: "+ sql);
			isCorret = getJdbcTemplate().update(sql);
		}else {
			String sql = "INSERT INTO tctliquidacion " +
					"		         (liquidacion_id, agencia_id, usuario_id, d_fecliq, n_totefeliq, n_totefeing, n_estliq, c_estreg) " +
					"          VALUES " +
					"				 (nextval('seq_tctliquidacion_id'), "
									+ agencia_idtranscar + ", "
									+ liquidacionTurno.getTranscarUsuarioPersonal().getId() + ", "
									+ "'"+ Constantes.FORMAT_DATE.format(liquidacionTurno.getFechaApertura()) + "', "
									+ "0.00, 0.00, 1, 'A' "
									+ ") ";
			Log.info("Apertura Liquidacion - Transcar: "+sql);
			isCorret = getJdbcTemplate().update(sql);
		}
		
		String messageError = null;
		if(isCorret == Constantes.FALSE_VALUE)
			messageError = "Ha ocurrido un error al aperturar la liquidación";

		return messageError;
	}

	/* (non-Javadoc)
	 * @see com.cystesoft.vyrbus.model.dao.TranscarWebDAO#buscarDetalleVentas(com.cystesoft.vyrbus.model.bean.TranscarUsuarioPersonal, java.lang.Integer, java.lang.String, java.lang.String)
	 */
	@Override
	public List<VentaPasaje> buscarDetalleVentas(TranscarUsuarioPersonal usuario, Integer agenciaId,String fechaInicial, String fechaFinal) throws Exception {

		Integer agencia_idtranscar = agenciaId;
		if(agencia_idtranscar!=null) {
			agencia_idtranscar = UtilData.getAgencia_Idtranscarweb(agenciaId);
			//Valida que la agencia del vyrbus este asociada a la del transcar web
			if(agencia_idtranscar==null) {
				throw new Exception(Messages.getString("Generales.informacion.agenciaNoAsociadaTranscarweb"));
			}
		}

		Integer usuario_id = (usuario!=null? usuario.getId():null);

		String sql="SELECT ec.envcon_id, ec.d_fecven, av.agencia_id, av.c_denominacion agencia, cl.c_numdoc cliente_numdoc, cl.c_razsoc cliente_razonSocial " +
				"         ,rt.c_origen, rt.c_destino " +
				"         ,ec.forpag_id, ec.tippag_id, dp.opetar_id, ec.tipcom_id, ec.c_numcom, ec.n_subtotal, ec.n_impuesto, ec.n_total " +
				"	      ,us.c_apepat usuario_apepat, us.c_apemat usuario_apemat, us.c_nombre usuario_nombre, us.c_login usuario_login " +
				"		  ,CASE WHEN ec.d_fecanu IS NOT NULL AND ec.usuario_idanula IS NOT NULL THEN 2 ELSE 1 END estadoComprobante " + //-->1= Activo, 2=Anulado\r\n" +
				"		  ,ec.audfecins " +
				"FROM tctenvcon ec " +
				"  INNER JOIN tcmusuario us ON (us.usuario_id=ec.usuario_id) " +
				"	INNER JOIN tcmagencia av ON (av.agencia_id=ec.agencia_idventa) " +
				"	INNER JOIN tcmcliente cl ON (cl.cliente_id=ec.cliente_id) " +
				"	INNER JOIN tcmruta rt ON (rt.ruta_id=ec.ruta_id) " +
				"   LEFT JOIN tctdetpag dp ON (ec.envcon_id = dp.envcon_id)" +
				"WHERE ec.d_fecven BETWEEN to_date('"+fechaInicial+"', 'dd/MM/yyyy') AND to_date('"+fechaFinal+"', 'dd/MM/yyyy') " + 
				"  AND ec.agencia_idventa = COALESCE("+agencia_idtranscar+", ec.agencia_idventa) " + 
				"  AND ec.usuario_id = COALESCE("+usuario_id+", ec.usuario_id) " + 
				"  AND ec.c_estreg = 'A'";


		Log.info("buscarDetalleVentas - Transcar: "+sql);
		List<?> result = getJdbcTemplate().queryForList(sql);

		List<VentaPasaje> listVentasCarga = new ArrayList<>();
		Map<String, Object> map = null;
		int tipoPagoId=0;
		for(int i=0;i<result.size();i++){
			map = (Map<String, Object>)result.get(i);

			int idEstadoRegistro = (int)map.get("ESTADOCOMPROBANTE");
			int formaPagoId = ((BigDecimal)map.get("FORPAG_ID")).intValue();
			//Consistencia el tipo de pago para PCE que vienen de TRANSCAR
			if(map.get("TIPPAG_ID") != null)
				tipoPagoId = ((BigDecimal)map.get("TIPPAG_ID")).intValue();
			int tipoComprobanteId = ((BigDecimal)map.get("TIPCOM_ID")).intValue();
			String simboloMoneda= "S/";

			Agencia agencia= new Agencia();
			agencia.setId(((BigDecimal)map.get("AGENCIA_ID")).intValue());
			agencia.setDenominacion(map.get("AGENCIA").toString());

			Pasajero pasajero= new Pasajero();
			pasajero.setNumeroDocumento(map.get("CLIENTE_NUMDOC")!=null?map.get("CLIENTE_NUMDOC").toString():"");
			pasajero.setNombresApellidos(map.get("CLIENTE_RAZONSOCIAL").toString());

			Ruta ruta= new Ruta();
			ruta.setOrigen(map.get("C_ORIGEN").toString());
			ruta.setDestino(map.get("C_DESTINO").toString());

			Usuario usuarioVenta = new Usuario();
			usuarioVenta.setApellidoPaterno(map.get("USUARIO_APEPAT").toString());
			usuarioVenta.setApellidoMaterno(map.get("USUARIO_APEMAT")!=null?map.get("USUARIO_APEMAT").toString():null);
			usuarioVenta.setNombre(map.get("USUARIO_NOMBRE").toString());
			usuarioVenta.setLogin(map.get("USUARIO_LOGIN").toString());

			VentaPasaje ventaCarga= new VentaPasaje();
			if(idEstadoRegistro==2)
				ventaCarga.setTipoMovimiento(new TipoMovimiento(Constantes.ID_TIPMOV_ANULACION));
			else
				ventaCarga.setTipoMovimiento(new TipoMovimiento(Constantes.ID_TIPMOV_EFECTIVO));
			if(formaPagoId==ID_FORPAG_CONTADO) {
				if(tipoPagoId==ID_TIPPAG_EFECTIVO) {
					ventaCarga.setTipoTransaccion("V.(EF)");
					ventaCarga.setFormaPago(new FormaPago(Constantes.ID_FORPAG_CONTADO, "CONTADO"));
					ventaCarga.setTipoFormaPago(new TipoFormaPago(Constantes.ID_TIPFORPAG_EFECTIVO, "EFECTIVO"));
				}else if(tipoPagoId == ID_TIPPAG_YAPE) {
					ventaCarga.setTipoTransaccion("V.(YAP)");
					ventaCarga.setFormaPago(new FormaPago(Constantes.ID_FORPAG_CONTADO, "CONTADO"));
					ventaCarga.setTipoFormaPago(new TipoFormaPago(Constantes.ID_TIPFORPAG_YAPE, "YAPE"));
				}else {// if(tipoPagoId==ID_TIPPAG_TARJETA) {
					ventaCarga.setTipoTransaccion("V.(TC)");
					ventaCarga.setFormaPago(new FormaPago(Constantes.ID_FORPAG_CONTADO, "CONTADO"));
					ventaCarga.setTipoFormaPago(new TipoFormaPago(Constantes.ID_TIPFORPAG_TARJETA, "TARJETA"));
				}
			}else if(formaPagoId==ID_FORPAG_CREDITO) {
				ventaCarga.setTipoTransaccion("CREDITO");
				ventaCarga.setFormaPago(new FormaPago(Constantes.ID_FORPAG_CREDITO, "CREDITO"));
				ventaCarga.setTipoFormaPago(new TipoFormaPago(Constantes.ID_TIPFORPAG_CREDITO, "CREDITO"));
			}else if(formaPagoId==ID_FORPAG_PCE) {
				ventaCarga.setTipoTransaccion("PCE");
				ventaCarga.setFormaPago(new FormaPago(Constantes.ID_FORPAG_CREDITO, "PCE"));
				ventaCarga.setTipoFormaPago(new TipoFormaPago(Constantes.ID_TIPFORPAG_PCE, "PCE"));
			}else {
				ventaCarga.setTipoTransaccion("CORT");
				ventaCarga.setFormaPago(new FormaPago(Constantes.ID_FORPAG_CORTESIA, "CORTESIA"));
				ventaCarga.setTipoFormaPago(new TipoFormaPago(Constantes.ID_TIPFORPAG_CORTESIA, "CORTESIA"));
			}

			if(tipoComprobanteId==ID_TIPCOM_FACTURA)
				ventaCarga.setTipoComprobante(new TipoComprobante(Constantes.ID_TIPCOM_FACTURA, "FACTURA"));
			else if(tipoComprobanteId==ID_TIPCOM_BOLETA)
				ventaCarga.setTipoComprobante(new TipoComprobante(Constantes.ID_TIPCOM_BOLETA_VENTA, "BOLETA DE VENTA"));
			else if(tipoComprobanteId==ID_TIPCOM_NOTACREDITO) {
				ventaCarga.setTipoComprobante(new TipoComprobante(Constantes.ID_TIPCOM_NOTA_CREDITO, "NOTA DE CREDITO"));
				ventaCarga.setTipoTransaccion("NOTA CREDITO");
			}else if(tipoComprobanteId==ID_TIPCOM_PCE)
				ventaCarga.setTipoComprobante(new TipoComprobante(Constantes.ID_TIPCOM_GUIA_TRANSPORTISTA, "GUIA TRANSPORTISTA"));

			if(!(simboloMoneda.equals("S/")))
				ventaCarga.setTipoMoneda(new TipoMoneda(2)); //Dolares

			ventaCarga.setOperadorTarjetaCredito(map.get("OPETAR_ID")!=null?new OperadorTarjetaCredito(((BigDecimal)map.get("OPETAR_ID")).intValue()):null);
			ventaCarga.setNumeroBoleto(map.get("C_NUMCOM").toString());
			ventaCarga.setId(((BigDecimal)map.get("ENVCON_ID")).longValue());
			ventaCarga.setFechaLiquidacion((Date)map.get("D_FECVEN"));
			ventaCarga.setImportePagado(((BigDecimal)map.get("N_TOTAL")).doubleValue());
			ventaCarga.setTarifa(((BigDecimal)map.get("N_TOTAL")).doubleValue());
			ventaCarga.setFechaInsercion(map.get("AUDFECINS")!=null?(Date)map.get("AUDFECINS"):null);
			ventaCarga.setRecargo(.00);
			ventaCarga.setDescuento(.00);
			ventaCarga.setAcuenta(.00);
			ventaCarga.setPenalidad(.00);
			ventaCarga.setNumeroControl("T00000RANSCAR");
			ventaCarga.setAgencia(agencia);
			ventaCarga.setPasajero(pasajero);
			ventaCarga.setRuta(ruta);
			ventaCarga.setUsuario(usuarioVenta);

			listVentasCarga.add(ventaCarga);
		}



		return listVentasCarga;
	}

//	/* (non-Javadoc)
//	 * @see com.cystesoft.vyrbus.model.dao.TranscarWebDAO#buscarAgencias()
//	 */
//	@Override
//	public List<Agencia> buscarAgencias() throws Exception {
//		// TODO Auto-generated method stub
//		return null;
//	}

	/* (non-Javadoc)
	 * @see com.cystesoft.vyrbus.model.dao.TranscarWebDAO#buscarUsuariosByVenta(java.lang.Integer, java.lang.String, java.lang.String)
	 */
	@Override
	public List<Usuario> buscarUsuariosByVenta(Integer agenciaId, String fechaInicio, String fechaFin) throws Exception {

		Integer agencia_idtranscar = agenciaId;
		if(agencia_idtranscar!=null) {
			agencia_idtranscar = UtilData.getAgencia_Idtranscarweb(agenciaId);
			//Valida que la agencia del vyrbus este asociada a la del transcar web
			if(agencia_idtranscar==null) {
				throw new Exception(Messages.getString("Generales.informacion.agenciaNoAsociadaTranscarweb"));
			}
		}
		
		String sql = "SELECT ec.usuario_id, u.c_apepat, u.c_apemat, u.c_nombre, u.c_login " + 
				"FROM tctenvcon ec " + 
				"  INNER JOIN tcmusuario u ON (u.usuario_id=ec.usuario_id) " + 
				"WHERE ec.d_fecven BETWEEN to_date('"+fechaInicio+"', 'dd/MM/yyyy') AND to_date('"+fechaFin+"', 'dd/MM/yyyy') " + 
				"  AND ec.agencia_idventa = COALESCE("+agencia_idtranscar+", ec.agencia_idventa) " + 
				"	AND ec.c_estreg = 'A' " + 
				"GROUP BY ec.usuario_id, u.c_apepat, u.c_apemat, u.c_nombre, u.c_login";
		Log.info("buscarUsuariosByVenta - Transcar: " + sql);

		List<?> result=getJdbcTemplate().queryForList(sql);

		List<Usuario> listUsuariosenta = new ArrayList<>();
		Map<String, Object> map = null;
		for(int i=0;i<result.size();i++){
			map = (Map<String, Object>)result.get(i);

			Usuario usuario= new Usuario();
			usuario.setId(((BigDecimal)map.get("USUARIO_ID")).intValue());
			usuario.setApellidoPaterno(map.get("C_APEPAT").toString());
			usuario.setApellidoMaterno(map.get("c_apemat")!=null?map.get("c_apemat").toString():"");
			usuario.setNombre(map.get("C_NOMBRE").toString());
			usuario.setLogin(map.get("C_LOGIN").toString());
			listUsuariosenta.add(usuario);
		}


		return listUsuariosenta;
	}

	/* (non-Javadoc)
	 * @see com.cystesoft.vyrbus.model.dao.TranscarWebDAO#buscarLiquidacionTurnoResumenEspVal(java.lang.Integer, java.lang.Integer, java.lang.String, java.lang.String)
	 */
	@Override
	public List<Liquidacion> buscarLiquidacionTurnoResumenEspVal(Integer usuarioId, Integer agenciaId, String fechaInicio, String fechaFin) throws Exception {

		Integer agencia_idtranscar = agenciaId;
		if(agencia_idtranscar!=null) {
			agencia_idtranscar = UtilData.getAgencia_Idtranscarweb(agenciaId);
			//Valida que la agencia del vyrbus este asociada a la del transcar web
			if(agencia_idtranscar==null) {
				throw new Exception(Messages.getString("Generales.informacion.agenciaNoAsociadaTranscarweb"));
			}
		}

		String sql = "SELECT TipoComprobante, Serie, boletoInicial, BoletoFinal, BoletoFinal::INTEGER - boletoInicial::INTEGER +1 AS Cantidad, importe  " +
					"		,(BoletoFinal::INTEGER - boletoInicial::INTEGER +1) - cantRegistros AS Cortes, tipoComprobanteId  " +
					"FROM(   " +
//					"	   Recupera todos menos las confirmaciones de fecha habierta de fecha habierta  " +
					"		SELECT tc.tipcom_id tipoComprobanteId, tc.c_denominacion as TipoComprobante  " +
//					"			  ,MIN(SUBSTR(vp.c_numcom ,0,4)) AS Serie    " +
					"			  ,MIN(lpad( substr(vp.c_numcom, 1, (position('-' in vp.c_numcom )-1)), 4, '0' )) AS Serie" +
//					"		      ,MIN(SUBSTR(vp.c_numcom,6, LENGTH(vp.c_numcom))) AS boletoInicial     " +
					"			  ,MIN(lpad( substr(vp.c_numcom, (position('-' in vp.c_numcom )+1), (length(vp.c_numcom) - position('-' in vp.c_numcom ))), 8, '0' )) AS BoletoInicial" +
//					"		      ,MAX(SUBSTR(vp.c_numcom,6, LENGTH(vp.c_numcom)))  AS BoletoFinal  " +
					"			  ,MAX(lpad( substr(vp.c_numcom, (position('-' in vp.c_numcom )+1), (length(vp.c_numcom) - position('-' in vp.c_numcom ))), 8, '0' )) AS BoletoFinal" +
					"		      ,COUNT(DISTINCT(nb.c_numcom)) AS cantRegistros  " + 
					"			  ,SUM(vp.n_total) importe  " + 
					"		FROM tctenvcon vp     " + 
					"		   INNER JOIN tcmtipcom tc ON (tc.tipcom_id=vp.tipcom_id)  " + 
					"		   INNER JOIN (SELECT c_numcom FROM tctenvcon GROUP BY c_numcom)nb ON (nb.c_numcom=vp.c_numcom)   " + 
					"		WHERE vp.d_fecven BETWEEN to_date('"+fechaInicio+"', 'dd/MM/yyyy') and to_date('"+fechaFin+"', 'dd/MM/yyyy')  " + 
					"		  AND vp.agencia_idventa = COALESCE("+agencia_idtranscar+", vp.agencia_idventa)   " + 
					"		  AND vp.usuario_id= COALESCE("+usuarioId+", vp.usuario_id)  " + 
					"		  AND vp.c_estreg = 'A'  " + 
					"		  AND vp.d_fecanu IS NULL AND vp.usuario_idanula IS NULL  " + 
					"		GROUP BY tc.tipcom_id, tc.c_denominacion, tc.tipcom_id " + 
					")esv";

		Log.info("BuscarLiquidacionTurnoResumenEspval:" + sql);
		List<?> result = getJdbcTemplate().queryForList(sql);

		List<Liquidacion> listLiquidacion = new ArrayList<>();
		Map<String, Object> map = null;
		for(int i=0;i<result.size();i++){
			map = (Map<String, Object>)result.get(i);

			Liquidacion liquidacion= new Liquidacion();
			liquidacion.setTipoComprobante(new TipoComprobante(((BigDecimal)map.get("TIPOCOMPROBANTEID")).intValue(), map.get("TIPOCOMPROBANTE").toString()));
			liquidacion.setComprobante(map.get("TIPOCOMPROBANTE").toString());
			liquidacion.setSerie(map.get("SERIE").toString());
			liquidacion.setBoletoInicial(map.get("BOLETOINICIAL").toString());
			liquidacion.setBoletoFinal(map.get("BOLETOFINAL").toString());
			liquidacion.setCantidadBoletos(Integer.valueOf(map.get("CANTIDAD").toString()));
			liquidacion.setImporte(((BigDecimal)map.get("IMPORTE")).doubleValue());

			listLiquidacion.add(liquidacion);
		}

		return listLiquidacion;
	}

	/* (non-Javadoc)
	 * @see com.cystesoft.vyrbus.model.dao.TranscarWebDAO#buscarLiquidacionTurno(java.lang.Integer, java.lang.Integer, java.lang.String, java.lang.String)
	 */
	@Override
	public Liquidacion buscarLiquidacionTurno(Integer usuarioId, Integer agenciaId, String fechaInicio, String fechaFin) throws Exception {

		List<VentaPasaje> result = buscarDetalleVentas(new TranscarUsuarioPersonal(usuarioId), agenciaId, fechaInicio, fechaFin);

		Integer cantidadEfectivo=0, cantidadTarjetaVisa=0, cantidadTarjetaMastercard=0, cantidadNotaCredito=0, cantidadPce=0, cantidadYape=0;
        Double efectivo=.00, tarjetaVisa=.00, tarjetaMastercard=.00, notaCredito=.00, pce=.00, yape=.00;

		for(VentaPasaje ventaPasaje: result) {

			int tipoPagoId = ventaPasaje.getTipoFormaPago().getId();
			int formaPagoId = ventaPasaje.getFormaPago().getId();
			int tipoComprobanteId = ventaPasaje.getTipoComprobante().getId();
			int operadorTarjetaId = (ventaPasaje.getOperadorTarjetaCredito()!=null? ventaPasaje.getOperadorTarjetaCredito().getId(): 0);
			Double totalCosto = ventaPasaje.getImportePagado();

			if(formaPagoId==Constantes.ID_FORPAG_CONTADO){
				if(tipoComprobanteId==Constantes.ID_TIPCOM_BOLETA_VENTA || tipoComprobanteId==Constantes.ID_TIPCOM_FACTURA){
					if(tipoPagoId==Constantes.ID_TIPFORPAG_EFECTIVO){
						cantidadEfectivo ++;
						efectivo += totalCosto;
					}else if(tipoPagoId==Constantes.ID_TIPFORPAG_TARJETA && operadorTarjetaId==Constantes.ID_OPETARCRE_VISA){
						cantidadTarjetaVisa ++;
						tarjetaVisa += totalCosto;
					}else if(tipoPagoId==Constantes.ID_TIPFORPAG_TARJETA && operadorTarjetaId==Constantes.ID_OPETARCRE_MSTERCARD){
						cantidadTarjetaMastercard ++;
						tarjetaMastercard += totalCosto;
					}else if(tipoPagoId == ID_TIPPAG_YAPE) {
						cantidadYape ++;
						yape += totalCosto;
					}
				}else if(tipoComprobanteId==Constantes.ID_TIPCOM_NOTA_CREDITO){
					cantidadNotaCredito ++;
					notaCredito += totalCosto;
				}
			}else if(formaPagoId==Constantes.ID_TIPFORPAG_PCE){
				cantidadPce ++;
				pce += totalCosto;
			}

		}

		Liquidacion liquidacion= new Liquidacion();
        liquidacion.setMontoContado(efectivo);
        liquidacion.setCantidadContado(cantidadEfectivo);
        liquidacion.setMontoTarjetaVisa(tarjetaVisa);
        liquidacion.setCantidadTarjetaVisa(cantidadTarjetaVisa);
        liquidacion.setMontoTarjetaMasterCard(tarjetaMastercard);
        liquidacion.setCantidadTarjetaMasterCard(cantidadTarjetaMastercard);
        liquidacion.setMontoNotasCredito(notaCredito);
        liquidacion.setCantidadNotasCredito(cantidadNotaCredito);
        liquidacion.setMontoPCE(pce);
        liquidacion.setCantidadPCE(cantidadPce);
        liquidacion.setMontoCortesia(.00);
        liquidacion.setCantidadCortesia(0);
        liquidacion.setMontoCreditos(.00);
        liquidacion.setCantidadCreditos(0);
        liquidacion.setCantidadYape(cantidadYape);
        liquidacion.setMontoYape(yape);

		return liquidacion;
	}

	/*
	 * (non-Javadoc)
	 * @see com.cystesoft.vyrbus.model.dao.TranscarWebDAO#cerrarLiquidacion(java.lang.Integer, java.lang.Integer, java.lang.String, java.lang.Double, java.lang.Double)
	 */
	@Override
	public void cerrarLiquidacion(Integer usuarioId, Integer agenciaId, String fechaLiquidacion, Double efectivoIngresado, Double efectivoLiquidado)throws Exception{

		Integer agencia_idtranscar = agenciaId;
		if(agencia_idtranscar!=null) {
			agencia_idtranscar = UtilData.getAgencia_Idtranscarweb(agenciaId);
			//Valida que la agencia del vyrbus este asociada a la del transcar web
			if(agencia_idtranscar==null) {
				throw new Exception(Messages.getString("Generales.informacion.agenciaNoAsociadaTranscarweb"));
			}
		}


		String sql = "Select lq.liquidacion_id "
				+ "From tctliquidacion lq "
				+ "where lq.usuario_id =" + usuarioId + 
				"    AND lq.agencia_id =" + agencia_idtranscar + 
				"	 AND lq.d_fecliq =to_date('"+ fechaLiquidacion + "', 'dd/MM/yyyy') " +  
				"	 AND lq.n_estliq =" + Constantes.TRUE_VALUE + 
				"	 AND lq.c_estreg = 'A'";
		Log.info("Obteniendo el identificador de la liquidacion - transcar:" + sql);
		long liquidacion_id = jdbcTemplate.queryForLong(sql);


		sql ="UPDATE tctliquidacion " +
					"   SET n_totefeing = "+efectivoIngresado+", n_totefeliq = "+efectivoLiquidado+", n_estliq = 0 " +
					"WHERE liquidacion_id =" + liquidacion_id;
		Log.info("Cerrando Liquidacion - Transcar: " + sql);
		int isCorrect = getJdbcTemplate().update(sql);


		//Actualiza la ventas con el identificador de la liquidacion
		if(isCorrect == Constantes.TRUE_VALUE) {
			sql ="UPDATE tctenvcon SET liquidacion_id="+liquidacion_id+" "+
				 "WHERE envcon_id in ( SELECT ec.envcon_id "+
		                             " FROM tctenvcon ec "+
		                             " WHERE ec.d_fecven = to_date('"+fechaLiquidacion+"', 'dd/MM/yyyy') "+
		                             "   AND ec.agencia_idventa="+agencia_idtranscar+" AND ec.usuario_id="+usuarioId+"  "+
		                             "   AND ec.c_estreg = 'A') ";
			getJdbcTemplate().update(sql);

		}

	}

	/* (non-Javadoc)
	 * @see com.cystesoft.vyrbus.model.dao.TranscarWebDAO#buscarLiquidacionBus(java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	public TreeMap<String, Manifiesto> buscarLiquidacionBus(String fechaInicio, String fechaFin, String codigoBus)throws Exception {
		
		String sql = "SELECT gt.d_fecsal fecha_salida, ut.c_numuni bus_codigo, ut.c_placa bus_placa, pt.c_apepat piloto_apePat " + 
				"      ,pt.c_apemat piloto_apeMat, pt.c_nombre piloto_nombres, SUM(ec.n_total) total_soles " + 
				"FROM tctguitra gt " + 
				"  INNER JOIN tctdetguitra dgt ON (dgt.guitra_id=gt.guitra_id) " + 
				"	INNER JOIN tcmunitra ut ON (ut.unitra_id=gt.unitra_id) " + 
				"	INNER JOIN tcmpiloto pt ON (pt.piloto_id=gt.piloto_id) " + 
				"	INNER JOIN tctenvcon ec ON (ec.envcon_id=dgt.envio_id AND dgt.tipcom_id IN (2,1,3)) " + 
				"WHERE gt.d_fecsal BETWEEN to_date('"+fechaInicio+"', 'dd/MM/yyyy') AND to_date('"+fechaFin+"', 'dd/MM/yyyy') " + 
				"  AND gt.c_estreg = 'A' " + 
				"	AND dgt.c_estreg = 'A' " + 
				"	AND gt.c_numunitra = COALESCE(null, gt.c_numunitra) " + 
				"GROUP BY gt.d_fecsal, ut.c_numuni, ut.c_placa, pt.c_apepat, pt.c_apemat, pt.c_nombre " + 
				"ORDER BY gt.d_fecsal, ut.c_numuni, ut.c_placa, pt.c_apepat, pt.c_apemat, pt.c_nombre";

		Log.info("BuscarLiquidacionBus - Transcar: " + sql);
		List<?> result = getJdbcTemplate().queryForList(sql);

		TreeMap<String, Manifiesto> liquidacionBus = new TreeMap<>();
		Map<String, Object> map = null;
		for (Object element : result) {
			map = (Map<String, Object>)element;

			Itinerario itinerario = new Itinerario();
			itinerario.setFechaPartida((Date)map.get("fecha_salida"));
			Bus bus = new Bus();
			bus.setCodigo(map.get("BUS_CODIGO").toString());
			bus.setNumeroPlaca(map.get("BUS_PLALA")!=null?map.get("BUS_PLALA").toString():"");
			String piloto = (map.get("PILOTO_APEPAT")!=null?map.get("PILOTO_APEPAT").toString():"");
			piloto += (map.get("PILOTO_APEMAT")!=null?map.get("PILOTO_APEMAT").toString():"");
			piloto += (map.get("PILOTO_NOMBRES")!=null?map.get("PILOTO_NOMBRES").toString():"");

			Manifiesto manifiesto = new Manifiesto();
			manifiesto.setItinerario(itinerario);
			manifiesto.setBus(bus);
			manifiesto.setPiloto(piloto);
			manifiesto.setImporte(((BigDecimal)map.get("TOTAL_SOLES")).doubleValue());

			String keymap = Constantes.FORMAT_DATE.format(itinerario.getFechaPartida());
			keymap += "-" + bus.getCodigo();

			liquidacionBus.put(keymap, manifiesto);
		}


		return liquidacionBus;
	}

	/* (non-Javadoc)
	 * @see com.cystesoft.vyrbus.model.dao.TranscarWebDAO#buscarLiquidacionCounter(java.lang.String, java.lang.String, java.lang.Integer, java.lang.Integer)
	 */
	@Override
	public TreeMap<String, Liquidacion> buscarLiquidacionCounter(String fechaInicio, String fechaFin, Integer agenciaId, Integer usuarioId) throws Exception {

		Integer agencia_idtranscar = agenciaId;
		if(agencia_idtranscar!=null) {
			agencia_idtranscar = UtilData.getAgencia_Idtranscarweb(agenciaId);
			//Valida que la agencia del vyrbus este asociada a la del transcar web
			if(agencia_idtranscar==null) {
				throw new Exception(Messages.getString("Generales.informacion.agenciaNoAsociadaTranscarweb"));
			}
		}
		
		String sql = "SELECT to_char(lq.d_fecliq,'dd/MM/yyyy') fecha, lq.usuario_id ,up.c_login, ag.agencia_id, COALESCE(lq.n_totefeing,0) entre_soles " + 
					"	    ,0.00 entre_dola, lq.n_estliq cerrado, lq.audfecmod fecha_cierre " + 
					"FROM tctliquidacion lq " + 
					"	INNER JOIN tcmusuario up ON (up.usuario_id=lq.usuario_id) " + 
					"	INNER JOIN tcmagencia ag ON (ag.agencia_id=lq.agencia_id) " + 
					"WHERE lq.d_fecliq  BETWEEN to_date('"+fechaInicio+"', 'dd/MM/yyyy') AND to_date('"+fechaFin+"', 'dd/MM/yyyy') " + 
					"	AND ag.agencia_id = COALESCE("+agencia_idtranscar+", lq.agencia_id) " + 
					"	AND lq.c_estreg = 'A'";
	 	List<?> result = jdbcTemplate.queryForList(sql);

	 	Map<String, Object> map = new HashMap<>();

		TreeMap<String, Liquidacion> resultLiquidacion = new TreeMap<>();
		for (Object element : result) {
			map = (Map<String, Object>)element;

			Usuario usuario= new Usuario();
			usuario.setId(((BigDecimal)map.get("USUARIO_ID")).intValue());
			usuario.setLogin(map.get("C_LOGIN").toString());

			Liquidacion liquidacion= new Liquidacion();
			liquidacion.setFechaLiquidacion(Constantes.FORMAT_DATE.parse(map.get("FECHA").toString()));
			liquidacion.setUsuario(usuario);
			liquidacion.setAgencia(new Agencia(Integer.valueOf(map.get("AGENCIA_ID").toString()))); //Identificador de la agencia tal cual esta registradoe en VYR
			liquidacion.setImporte(((BigDecimal)map.get("ENTRE_SOLES")).doubleValue());
			liquidacion.setEstadoLiquidacion(((BigDecimal)map.get("CERRADO")).intValue());
			if(liquidacion.getestadoLiquidacion().intValue() == Constantes.FALSE_VALUE)
				liquidacion.setFechaModificacion(map.get("FECHA_CIERRE")!=null?(Date)map.get("FECHA_CIERRE"):null);

			//Key
			String key = Constantes.FORMAT_DATE.format(liquidacion.getFechaLiquidacion());
			key += liquidacion.getAgencia().getId().toString();
			key += liquidacion.getUsuario().getLogin();

			resultLiquidacion.put(key, liquidacion);
		}

		return resultLiquidacion;
	}

	/* (non-Javadoc)
	 * @see com.cystesoft.vyrbus.model.dao.TranscarWebDAO#actualizarPasswordUsuarioByLogin(java.lang.String)
	 */
	@Override
	public void actualizarPasswordUsuarioByLogin(String login, String passwordNew) throws Exception {
		String sql = "Update tcmusuario Set c_password= '"+passwordNew+"' Where c_login='"+login+"' ";

		Log.info("Actualiza Password Usuario - Carga: "+sql);
		getJdbcTemplate().update(sql);
	}

	private String getDireccionMacDinamica() {
		String direccionMac = "";
		String[] patron_A = {"A0","00", "0A" ,"00", "A0", "00"};
		String patron_B = "ABCDEFGHIJKLMNIOPQRSTUVWXYZ";

		try {
			Random random= new Random();

			int x = 0;
			for(String comp: patron_A) {
				x++;
				String pmac = "";
				String p1 = comp.substring(0,1);
				String p2 = comp.substring(1);
				int randomChar =  random.nextInt(patron_B.length());

				if(Util.isNumeric(p1)) {
					int randomInt =  random.nextInt(13);
					pmac += String.valueOf(randomInt);
				}else {
					char ochar = patron_B.charAt(randomChar);
					pmac += ochar;
				}

				if(Util.isNumeric(p2)) {
					int randomInt =  random.nextInt(13);
					pmac += String.valueOf(randomInt);
				}else {
					char ochar = patron_B.charAt(randomChar);
					pmac += ochar;
				}

				if(pmac.length()>2)
					pmac = pmac.substring(0,2);

				direccionMac += pmac;
				if (patron_A.length > x)
					direccionMac += "-";
			}

		} catch (Exception e) {
			direccionMac = "AB-00-BA-11-AC-99";
			e.printStackTrace();
		}

		return direccionMac;
	}
	
	@Override
	public List<VentasPiloto> buscarRegistroVentas(String fInicio, String fFin) throws Exception {

		
		String sql = "SELECT \r\n" + 
				"				ec.d_fecven FECHA, \r\n" + 
				"				CASE ec.tipcom_id  \r\n" + 
				"					WHEN 1\r\n" + 
				"					  THEN '03'\r\n" + 
				"					WHEN 2\r\n" + 
				"					  THEN '01' \r\n" + 
				"					WHEN 3 \r\n" + 
				"					  THEN '31'\r\n" + 
				"				END TD,\r\n" + 
				"				CASE ec.tipcom_id \r\n" + 
				"				  WHEN 3 \r\n" + 
				"					  THEN\r\n" + 
				"						  lpad(substr(ec.c_numcom, 1, 3), 4, '0') \r\n" + 
				"					  ELSE \r\n" + 
				"						  substr(ec.c_numcom, 1, 4) \r\n" + 
				"				END SERIE, \r\n" + 
				"				CASE ec.tipcom_id \r\n" + 
				"				  WHEN 3 \r\n" + 
				"					  THEN\r\n" + 
				"						  lpad(substr(ec.c_numcom, 6, 8), 8, '0') \r\n" + 
				"					  ELSE \r\n" + 
				"						  substr(ec.c_numcom, 6, 8) \r\n" + 
				"				END  NUMERO,\r\n" + 
				"				c.c_numdoc DNI, c.c_razsoc RAZON_SOCIAL, 0.00 EXONERADO, \r\n" + 
				"				ec.n_subtotal V_VENTA, ec.n_impuesto IGV, ec.n_total TOTAL,\r\n" + 
				"				agd.c_denominacion DESTINO, '' ASTO, ec.tipcom_id, '1' TIPMOV_ID \r\n" + 
				"FROM \r\n" + 
				"				tctenvcon ec \r\n" + 
				"				INNER JOIN tcmcliente c ON (ec.cliente_id = c.cliente_id)\r\n" + 
				"				INNER JOIN tcmagencia agd ON (ec.agencia_iddestino = agd.agencia_id)\r\n" + 
				"WHERE\r\n" + 
				"				ec.d_fecven between to_date('"+fInicio+"', 'dd/MM/yyyy') \r\n" + 
				"				AND to_date('"+fFin+"', 'dd/MM/yyyy')\r\n" + 
				"				AND ec.tipcom_id in (1, 2, 3)\r\n" + 
				"ORDER BY \r\n" + 
				"				ec.d_fecven, td,substr(ec.c_numcom, 1, 4), substr(ec.c_numcom, 6, 8)";
		Log.info("buscarUsuariosByVenta - Transcar: " + sql);

		List<?> result=getJdbcTemplate().queryForList(sql);

		List<VentasPiloto> lstVentas = new ArrayList<>();
		Map<String, Object> map = null;
		
		for(int i=0; i<result.size(); i++) {
			map = (Map<String, Object>)result.get(i);
			
			VentasPiloto regVentas = new VentasPiloto();
			regVentas.setFechaCompra((Date)map.get("FECHA"));
			regVentas.setTipoDocumentoSunat(map.get("TD").toString());
			regVentas.setSerie(map.get("SERIE").toString());
			regVentas.setNumero(map.get("NUMERO").toString());
			regVentas.setNumeroBoleto(map.get("SERIE").toString()+"-"+map.get("NUMERO").toString());
			regVentas.setRuc("");
			regVentas.setDni(map.get("DNI").toString());
			regVentas.setNombres(map.get("RAZON_SOCIAL").toString());
			regVentas.setExonerado(((BigDecimal)map.get("EXONERADO")).doubleValue());
			regVentas.setVenta(((BigDecimal)map.get("V_VENTA")).doubleValue());
			regVentas.setIgv(((BigDecimal)map.get("IGV")).doubleValue());
			regVentas.setTotal(((BigDecimal)map.get("TOTAL")).doubleValue());
			regVentas.setDestino(map.get("DESTINO").toString());
			regVentas.setAsiento(map.get("ASTO").toString());
			
			TipoComprobante tipoComprobante = new TipoComprobante();
			tipoComprobante.setId(((BigDecimal)map.get("TIPCOM_ID")).intValue());
			regVentas.setTipoComprobante(tipoComprobante);
			
			TipoMovimiento tipoMovimiento = new TipoMovimiento();
			tipoMovimiento.setId(Integer.parseInt(map.get("TIPMOV_ID").toString()) );
			regVentas.setTipoMovimiento(tipoMovimiento);
			
			lstVentas.add(regVentas);
		}
		
		return lstVentas;
	}
	
	@Override
	public List<ResumenVentas> buscarResumenVentas(String fechaDesde, String fechaHasta) {
		String sql = "";

		sql = "select \r\n" + 
				"				2 RUBRO, 1 CANALID, 'COUNTER' CANAL, ec.agencia_idventa IDAGENCIA, \r\n" + 
				"				a.c_denominacion AGENCIA,\r\n" + 
				"				CASE ec.tipcom_id \r\n" + 
				"				   WHEN 1 THEN 7 \r\n" + 
				"					 WHEN 2 THEN 2 \r\n" + 
				"					 WHEN 3 THEN 10 \r\n" + 
				"			  END IDCOMP, \r\n" + 
				"				CASE ec.tipcom_id \r\n" + 
				"					 WHEN 3 THEN 'GUIA REMISION TRANSPORTISTA' \r\n" + 
				"					 ELSE tc.c_denominacion \r\n" + 
				"			  END  COMP,  \r\n" + 
				"        COUNT(ec.tipcom_id) CANT,\r\n" + 
				"        SUM(ec.n_total) TOTAL,\r\n" + 
				"        ec.d_fecven FECVEN,\r\n" + 
				"        to_char(ec.d_fecven, 'yyyy') anio,\r\n" + 
				"        to_char(ec.d_fecven, 'mm') mes,\r\n" + 
				"        to_char(ec.d_fecven, 'dd') dia\r\n" + 
				"				--ec.*\r\n" + 
				"from \r\n" + 
				"				tctenvcon ec\r\n" + 
				"				inner join tcmagencia a on (ec.agencia_idventa = a.agencia_id)\r\n" + 
				"				inner join tcmtipcom tc on (ec.tipcom_id = tc.tipcom_id)\r\n" + 
				"WHERE\r\n" + 
				"				ec.d_fecven between to_date('"+fechaDesde+"', 'dd/mm/yyyy') and to_date('"+fechaHasta+"', 'dd/mm/yyyy')\r\n" + 
				"GROUP BY\r\n" + 
				"        ec.d_fecven, ec.agencia_idventa, a.c_denominacion, ec.tipcom_id, tc.c_denominacion\r\n" + 
				"ORDER BY\r\n" + 
				"        ec.d_fecven, a.c_denominacion, tc.c_denominacion;				\r\n"; 

		Log.info("RESUMEN VENTAS DE ENCOMIENDAS: "+sql);

		List<?> result=getJdbcTemplate().queryForList(sql);
		
		List<ResumenVentas> lstVentas = new ArrayList<>();
		Map<String, Object> map = null;
		Long cantidad;
		
		for(int i=0; i<result.size(); i++) {
			map = (Map<String, Object>)result.get(i);
			ResumenVentas resumenVentas = new ResumenVentas();
			
			resumenVentas.setRubro(((Integer)map.get("RUBRO")).intValue());
			
			CanalVenta canalVenta = new CanalVenta();
			canalVenta.setId(((Integer)map.get("CANALID")).intValue());
			canalVenta.setDenominacion(map.get("CANAL").toString());
			resumenVentas.setCanalVenta(canalVenta);
			
			Agencia agencia = new Agencia();
			agencia.setId(((BigDecimal)map.get("IDAGENCIA")).intValue());
			agencia.setDenominacion(map.get("AGENCIA").toString());
			resumenVentas.setAgencia(agencia);
			
			TipoComprobante tipoComprobante = new TipoComprobante();
			tipoComprobante.setId(((Integer)map.get("IDCOMP")).intValue());
			tipoComprobante.setDenominacion(map.get("COMP").toString());
			resumenVentas.setTipoComprobante(tipoComprobante);
			cantidad = ((Long)map.get("CANT")).longValue();
			resumenVentas.setCantidad( cantidad.intValue() );
			resumenVentas.setTotal(((BigDecimal)map.get("TOTAL")).doubleValue());
			resumenVentas.setFechaEmision((Date)map.get("FECVEN"));
			resumenVentas.setAnio(map.get("ANIO").toString());
			resumenVentas.setMes(map.get("MES").toString());
			resumenVentas.setDia(map.get("DIA").toString());
			
			lstVentas.add(resumenVentas);
		}
		
		return lstVentas;
	}

	/* (non-Javadoc)
	 * @see com.cystesoft.vyrbus.model.dao.TranscarWebDAO#buscaTotalVentasEfectivo(java.lang.Integer, java.lang.Integer, java.lang.String)
	 */
	@Override
	public Double buscaTotalVentasEfectivo(String loginUsuario, Integer idAgencia, String fecha) throws Exception {
		
		Integer agencia_idtranscar = idAgencia;
		if(agencia_idtranscar!=null) {
			agencia_idtranscar = UtilData.getAgencia_Idtranscarweb(idAgencia);
			//Valida que la agencia del vyrbus este asociada a la del transcar web
			if(agencia_idtranscar==null) {
				throw new Exception(Messages.getString("Generales.informacion.agenciaNoAsociadaTranscarweb"));
			}
		}
		
		//Busca el identificador del usuario en transcar web
		Integer idUsuario = 0;
		TranscarUsuarioPersonal usuarioPersonal = buscarUsuario(loginUsuario);
		if(usuarioPersonal !=null)
			idUsuario = usuarioPersonal.getId();
		
		//
		String sql = "SELECT COALESCE(SUM(ev.n_total),0) AS totalVentaEfectivo, null " + 
				"FROM tctenvcon ev " + 
				"WHERE ev.forpag_id = "+ Constantes.ID_FORPAG_CONTADO +" AND ev.tippag_id = "+ Constantes.ID_TIPFORPAG_EFECTIVO + " " +
				"  AND ev.c_estreg = '"+ Constantes.VALUE_ACTIVO +"' " + 
				"  AND ev.d_fecanu IS NULL AND ev.usuario_idanula IS NULL " + 
				"  AND ev.usuario_id =  "+ idUsuario +"  AND ev.agencia_idventa = "+ agencia_idtranscar +" AND ev.d_fecven = to_date('"+ fecha +"', 'dd/MM/yyyy') ";
		
		Log.info("buscaTotalVentasEfectivo: " + sql);
				
		List<?> result = getJdbcTemplate().queryForList(sql);
		Map<String, Object> map = (Map<String, Object>)result.get(0);
		Double total = ((BigDecimal) map.get("totalVentaEfectivo")).doubleValue();
		
		return total;
	}
	
	
}
