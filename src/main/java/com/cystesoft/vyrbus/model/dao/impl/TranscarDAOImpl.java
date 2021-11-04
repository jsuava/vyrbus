/**
 * Proyecto		: SISVYR
 * Sistema		: Sistema de Ventas y Reservas
 * Descripción	: 
 * Autor		: José Abanto
 * Fecha		: 26 oct. 2021
 * Hora			: 10:25:21
 */
package com.cystesoft.vyrbus.model.dao.impl;

import java.math.BigDecimal;
import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.JdbcTemplate;

import com.cystesoft.vyrbus.model.bean.Agencia;
import com.cystesoft.vyrbus.model.bean.CanalVenta;
import com.cystesoft.vyrbus.model.bean.FormaPago;
import com.cystesoft.vyrbus.model.bean.TipoComprobante;
import com.cystesoft.vyrbus.model.bean.TipoMoneda;
import com.cystesoft.vyrbus.model.bean.TipoMovimiento;
import com.cystesoft.vyrbus.model.bean.TranscarLiquidacionTurno;
import com.cystesoft.vyrbus.model.bean.TranscarRolUsuario;
import com.cystesoft.vyrbus.model.bean.TranscarUsuarioPersonal;
import com.cystesoft.vyrbus.model.bean.Usuario;
import com.cystesoft.vyrbus.model.bean.VentaPasaje;
import com.cystesoft.vyrbus.model.dao.TranscarDAO;
import com.cystesoft.vyrbus.service.util.Constantes;

import oracle.jdbc.internal.OracleTypes;

/**
 * @author abant
 *
 */
@SuppressWarnings("unchecked")
public class TranscarDAOImpl implements TranscarDAO{
	private JdbcTemplate jdbcTemplate;

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
	 * @see com.cystesoft.vyrbus.model.dao.TranscarDAO#buscarRolesUsauurio()
	 */
	@Override
	public List<TranscarRolUsuario> buscarRolesUsuario() throws Exception {
		// TODO Auto-generated method stub
		
		String storeProcedure="{call PKG_IVOCONTROLUSUARIO.SP_LISTA_ROL_USUARIO(?,?)}";				
		//Llamanado el SP
		CallableStatement callableStatement = jdbcTemplate.getDataSource().getConnection().prepareCall(storeProcedure);		
		//Parametros de entrada (index, parametros)
		callableStatement.setInt(1, 1);
		callableStatement.registerOutParameter(2, OracleTypes.CURSOR);
		callableStatement.execute();
		
		//Se obtiene el cursor en forma de ResultSet
		ResultSet resultSet = (ResultSet) callableStatement.getObject(2);
		List<TranscarRolUsuario> resultRolUsuario= new ArrayList<TranscarRolUsuario>();
		while (resultSet.next()) {
			TranscarRolUsuario rolUsuario= new TranscarRolUsuario();
			rolUsuario.setId(resultSet.getInt("IDROL_USUARIO"));
			rolUsuario.setNombre(resultSet.getString("ROL_USUARIO"));
			resultRolUsuario.add(rolUsuario);
		}
		
		return resultRolUsuario;
	}

	/* (non-Javadoc)
	 * @see com.cystesoft.vyrbus.model.dao.TranscarDAO#buscarRolesUsuario(java.lang.Integer)
	 */
	@Override
	public List<TranscarRolUsuario> buscarRolesUsuario(Integer usuarioId) throws Exception {
		// TODO Auto-generated method stub
		
		String storeProcedure="{call PKG_IVOCONTROLUSUARIO.SP_DATOS_USUARIO_ROL(?,?,?)}";				
		//Llamanado el SP
		CallableStatement callableStatement = jdbcTemplate.getDataSource().getConnection().prepareCall(storeProcedure);		
		//Parametros de entrada
		callableStatement.setInt("idUser", usuarioId);
		callableStatement.registerOutParameter("cur_Usuario", OracleTypes.CURSOR);
		callableStatement.registerOutParameter("cur_Roles", OracleTypes.CURSOR);
		callableStatement.execute();
		
		//Se obtiene el cursor en forma de ResultSet
		TranscarUsuarioPersonal usuarioPersonal= null;
		ResultSet resultSet = (ResultSet) callableStatement.getObject("cur_Usuario");
		while (resultSet.next()) {
			usuarioPersonal = new TranscarUsuarioPersonal();
			usuarioPersonal.setId(usuarioId);
			usuarioPersonal.setPermiteVentaOtrasAgencias(resultSet.getInt("IND_OTRAS_AGENCIAS"));
			usuarioPersonal.setAutorizaEntregaSinVerificarUsuario(resultSet.getInt("Ind_Autoriza_Entrega"));
		}
		
		resultSet = (ResultSet) callableStatement.getObject("cur_Roles");
		List<TranscarRolUsuario> resultRolUsuario= new ArrayList<TranscarRolUsuario>();
		while (resultSet.next()) {
			TranscarRolUsuario rolUsuario= new TranscarRolUsuario();
			rolUsuario.setId(resultSet.getInt("IDROL"));
			rolUsuario.setNombre(resultSet.getString("ROL"));
			rolUsuario.setTranscarUsuarioPersonal(usuarioPersonal);
			resultRolUsuario.add(rolUsuario);
		}
		
		return resultRolUsuario;
	}

	/* (non-Javadoc)
	 * @see com.cystesoft.vyrbus.model.dao.TranscarDAO#buscarUsuarioPersonal(java.lang.String)
	 */
	
	@Override
	public TranscarUsuarioPersonal buscarUsuarioPersonal(String login) throws Exception {
		// TODO Auto-generated method stub
		String sql="select up.idusuario_personal, up.login from t_usuario_personal up where up.login='"+login+"' and up.idestado_registro=1 ";
		
		List<?> result=jdbcTemplate.queryForList(sql);
		
		TranscarUsuarioPersonal usuarioPersonal=null;
		Map<String, Object> map = new HashMap<String, Object>();
		for(int i=0;i<result.size();i++){
			map = (Map<String, Object>)result.get(i);
			usuarioPersonal = new TranscarUsuarioPersonal();
			usuarioPersonal.setId(((BigDecimal)map.get("IDUSUARIO_PERSONAL")).intValue());
			usuarioPersonal.setLogin(map.get("LOGIN").toString());
		}
				
		return usuarioPersonal;
	}

	/* (non-Javadoc)
	 * @see com.cystesoft.vyrbus.model.dao.TranscarDAO#guardarUsuarioPersonal(com.cystesoft.vyrbus.model.bean.TranscarUsusrioPeronal, boolean)
	 */
	@Override
	public void guardarUsuarioPersonal(TranscarUsuarioPersonal transcarUsusrioPeronal, String idsRoles, boolean isNuevo)
			throws Exception {
		// TODO Auto-generated method stub
		int icontrol = (isNuevo?1:2);		
		String storeProcedure="{call PKG_IVOCONTROLUSUARIO.INSUPD_USUARIO_PERSONAL_2(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}";
		//Llamanado el SP
		CallableStatement callableStatement = jdbcTemplate.getDataSource().getConnection().prepareCall(storeProcedure);		
		//Parametros de entrada
		callableStatement.setInt("iCONTROL", icontrol);
		callableStatement.setInt("iIDTURNOS_AGENCIA", 0);
        callableStatement.setInt("iIDUSUARIO_PERSONAL", transcarUsusrioPeronal.getId()!=null?transcarUsusrioPeronal.getId():0);
        callableStatement.setString("iIP", transcarUsusrioPeronal.getIpInsercion());
        callableStatement.setInt("iIDROL_USUARIO", 0);
        callableStatement.setString("iNOMPER", transcarUsusrioPeronal.getNombres());
        callableStatement.setString("iAPEMAT", transcarUsusrioPeronal.getApellidoMaterno()!=null?transcarUsusrioPeronal.getApellidoMaterno():null);
        callableStatement.setString("iAPEPAT", transcarUsusrioPeronal.getApellidoParterno());
        callableStatement.setInt("iIDTIPO_DOC_IDENTIDAD", transcarUsusrioPeronal.getTipoDocumentoIdentidad());
        callableStatement.setString("iNRODOC", null);
        callableStatement.setString("iFECHA_NACIMIENTO", "01/01/1990");
        callableStatement.setInt("iIDTIPO_USUARIO_PERSONAL", 0);
        callableStatement.setString("iLOGIN", transcarUsusrioPeronal.getLogin());
        callableStatement.setString("iPASSWORD", transcarUsusrioPeronal.getPassword());
        callableStatement.setString("iE_MAIL", transcarUsusrioPeronal.getEmail()!=null?transcarUsusrioPeronal.getEmail():null);
        callableStatement.setString("iTELEFONO", null);
        callableStatement.setString("iCELULAR", null);
        callableStatement.setString("iDIRECCION", null);
        callableStatement.setInt("iIDUSUARIO_CREADOR", 1);
        callableStatement.setInt("iIDAREA_SISTEMA", 1);
        callableStatement.setInt("iIDROL_CREADOR", 1);
        callableStatement.setInt("iIDSEXO", transcarUsusrioPeronal.getSexoId());
        callableStatement.setInt("iIDESTADO_REGISTRO", Constantes.TRUE_VALUE);
        callableStatement.setInt("iIDDISTRITO", transcarUsusrioPeronal.getDistrito());
        callableStatement.setInt("iIDPROVINCIA", transcarUsusrioPeronal.getProvincia());
        callableStatement.setInt("iIDDEPARTAMENTO", transcarUsusrioPeronal.getDepartamentoId());
        callableStatement.setInt("iIDPAIS", 347);
        callableStatement.setInt("iIDUBIGEO", transcarUsusrioPeronal.getUbigeo());
//        callableStatement.setBlob("iFOTO", blobFoto);
        callableStatement.setInt("iIDESTADO_CIVIL", 1);
        callableStatement.setString("iFAX", null);
        callableStatement.setString("iRPM", null);
        callableStatement.setInt("iIDUSUARIO_MODIFICADOR", 1);
        callableStatement.setString("iIDSRoles", idsRoles);
        callableStatement.setString("iinro_licencia", null);
        callableStatement.setInt("iIDOTRAS_AGENCIAS", transcarUsusrioPeronal.getPermiteVentaOtrasAgencias());
        callableStatement.setInt("iind_autoriza_entrega", transcarUsusrioPeronal.getAutorizaEntregaSinVerificarUsuario());
        callableStatement.setInt("iRolDefecto", 0);
        callableStatement.registerOutParameter("cur_usuario_Personal", OracleTypes.CURSOR);
        callableStatement.registerOutParameter("cur_usuario_Modificado", OracleTypes.CURSOR);
        callableStatement.registerOutParameter("cur_err", OracleTypes.CURSOR); //41		
		callableStatement.execute();
		
		ResultSet resultSet = (ResultSet) callableStatement.getObject("cur_usuario_Personal");		
		while (resultSet.next()) {
			System.out.println(resultSet.getString("msgbox").toString());
		}
	}

	/* (non-Javadoc)
	 * @see com.cystesoft.vyrbus.model.dao.TranscarDAO#buscarIdAgenciaByCodigoAgenciaPasajes(java.lang.String)
	 */
	@Override
	public Integer buscarIdAgenciaByCodigoAgenciaPasajes(String codigoAgenciaPasajes) throws Exception {
		// TODO Auto-generated method stub
		Integer agenciaId = null;
		try {
			String sql="select ag.idagencias from t_agencias ag where ag.cod_age_sisvyr = '"+codigoAgenciaPasajes+"'";
			agenciaId=jdbcTemplate.queryForInt(sql);	
		} catch (Exception e) {}
				
		return agenciaId;
	}

	/* (non-Javadoc)
	 * @see com.cystesoft.vyrbus.model.dao.TranscarDAO#aperturarLiquidacion(com.cystesoft.vyrbus.model.bean.TranscarLiquidacionTurno)
	 */
	@Override
	public String aperturarLiquidacion(TranscarLiquidacionTurno liquidacionTurno) throws Exception {
		// TODO Auto-generated method stub
		
		String storeProcedure="{call PKG_IVOCIERRE_LIQUIDACIONES.SP_INSUPD_LIQUI_TURNOS_(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}";
		//Llamanado el SP
		CallableStatement callableStatement = jdbcTemplate.getDataSource().getConnection().prepareCall(storeProcedure);		
		//Parametros de entrada
		callableStatement.setInt("P_OPERACION", liquidacionTurno.getOperacion());
        callableStatement.setInt("P_NRO_FACTU", 0);
        callableStatement.setDouble("P_TOTAL_MONTO_FACTU", .00);
        callableStatement.setInt("P_NRO_BOLE", 0);
        callableStatement.setDouble("P_TOTAL_MONTO_BOLE", 0.00);
        callableStatement.setInt("P_NRO_ANULA", 0);
        callableStatement.setDouble("P_TOTAL_MONTO_ANULA", .00);
        callableStatement.setInt("P_NRO_PAGO_ENTRE", 0);
        callableStatement.setDouble("P_TOTAL_MONTO_PAGO_ENTRE", .00);
        callableStatement.setInt("P_NRO_DEVO", 0);
        callableStatement.setDouble("P_TOTAL_MONTO_DEVO", .00);
        callableStatement.setInt("P_NRO_TARJE_CREDI", 0);
        callableStatement.setDouble("P_TOTAL_MONTO_TARJE_CREDI", .00);
        callableStatement.setInt("P_NRO_SOBRES", 0);
        callableStatement.setDouble("P_TOTAL_MONTO_SOBRES", .00);
        callableStatement.setDouble("P_TOTAL_PESO", .00);
        callableStatement.setDouble("P_TOTAL_VOLU", .00);
        callableStatement.setDouble("P_TOTAL_SOBRES", .00);
        callableStatement.setDouble("P_TIPO_CAMBI", .00);
        callableStatement.setDouble("P_TOTAL_MONTO_SOLES", .00);
        callableStatement.setDouble("P_TOTAL_MONTO_DOLA", 0.00);
        callableStatement.setDouble("P_TOTAL_MONTO_BOUCHER_SOLES", .00);
        callableStatement.setDouble("P_TOTAL_MONTO_BOUCHER_DOLA", 0.00);
        callableStatement.setString("P_FECHA_APER", Constantes.FORMAT_DATE.format(liquidacionTurno.getFechaApertura()));
        callableStatement.setString("P_FECHA_CIERRE", null);
        callableStatement.setInt("P_IMPRESO", 0);
        callableStatement.setInt("P_CERRADO", 0);
        callableStatement.setString("P_IP", liquidacionTurno.getIpInsercion());
        callableStatement.setString("P_IPMOD", liquidacionTurno.getIpInsercion());
        callableStatement.setInt("P_IDUSUARIO_PERSONAL", liquidacionTurno.getTranscarUsuarioPersonal().getId());
        callableStatement.setInt("P_IDUSUARIO_PERSONALMOD", liquidacionTurno.getTranscarUsuarioPersonal().getId());
        callableStatement.setString("P_FECHA_REGISTRO", Constantes.FORMAT_DATE.format(liquidacionTurno.getFechaApertura()));
        callableStatement.setString("P_FECHA_ACTUALIZACION", Constantes.FORMAT_DATE.format(liquidacionTurno.getFechaApertura()));
        callableStatement.setInt("P_IDROL_USUARIO", 1);
        callableStatement.setInt("P_IDROL_USUARIOMOD", 1);
        callableStatement.setInt("P_IDAGENCIAS", liquidacionTurno.getAgenciaId());
        callableStatement.setInt("P_IDUNIDAD_AGENCIA",  0);
        callableStatement.setInt("P_IDESTADO_REGISTRO", 1);
        callableStatement.registerOutParameter("CUR_INSUPD_LIQUI_TURNOS",OracleTypes.CURSOR);
        callableStatement.execute();
		
        ResultSet resultSet = (ResultSet) callableStatement.getObject("CUR_INSUPD_LIQUI_TURNOS");		
		String messageError=null;
        while (resultSet.next()) {
			try {
				messageError = resultSet.getString("errmsg").toString();
			} catch (Exception e) {}
		}
		
		return messageError;
	}

	/* (non-Javadoc)
	 * @see com.cystesoft.vyrbus.model.dao.TranscarDAO#buscarDetalleVentas(java.lang.Integer, java.lang.Integer, java.lang.String, java.lang.String)
	 */
	@Override
	public List<VentaPasaje> buscarDetalleVentas(Integer usuarioId, Integer agenciaId, String fechaInicial,String fechaFinal) throws Exception {
		// TODO Auto-generated method stub
		int TIPO_PAGO_EFECTIVO = 1;
		int TIPO_PAGO_TARJETA = 2;
//		int TIPO_PAGO_CORTESIA = 3;
		int FORMA_PAGO_CONTADO = 1;
		int FORMA_PAGO_CREDITO = 2;
//		int FORMA_PAGO_PCE = 3;
		
		if(usuarioId==null)
			usuarioId=0;
		if(agenciaId ==null)
			agenciaId = 0;
		
		String storeProcedure="{call PKG_IVOCIERRE_LIQUIDACIONES.SP_LIST_VENTAS_PRELI_TURNO_2(?,?,?,?,?,?,?)}";
		//Llamanado el SP
		CallableStatement callableStatement = jdbcTemplate.getDataSource().getConnection().prepareCall(storeProcedure);		
		//Parametros de entrada
		callableStatement.setInt("P_IDUSUARIO_PERSONAL", usuarioId);
        callableStatement.setInt("p_IDAgencias", agenciaId);
        callableStatement.setString("P_FECHA_INICIAL", fechaInicial);
        callableStatement.setString("P_FECHA_FINAL", fechaFinal);
        callableStatement.registerOutParameter("cur_resumen",OracleTypes.CURSOR);
        callableStatement.registerOutParameter("CUR_LIST_VENTAS_PRELI_TURNO",OracleTypes.CURSOR);
        callableStatement.registerOutParameter("CUR_ERROR",OracleTypes.CURSOR);
        callableStatement.execute();
        
        ResultSet resultSet = (ResultSet) callableStatement.getObject("CUR_LIST_VENTAS_PRELI_TURNO");
        List<VentaPasaje> listResult= new ArrayList<VentaPasaje>();
        while (resultSet.next()) {
        	
        	int idEstadoRegistro = resultSet.getInt("IDESTADO_FACTURA");
			int tipoPagoId = resultSet.getInt("IDTIPO_PAGO");
			int formaPagoId = resultSet.getInt("IDFORMA_PAGO");
			int tipoComprobanteId = resultSet.getInt("IDTIPO_COMPROBANTE");
			String simboloMoneda= resultSet.getString("SIMBOLO_MONEDA");			
			
			VentaPasaje ventaCarga = new VentaPasaje();
			ventaCarga.setCanalVenta(new CanalVenta(Constantes.ID_CANVEN_COUNTER));
			if(idEstadoRegistro==2 || idEstadoRegistro==3) 
				ventaCarga.setTipoMovimiento(new TipoMovimiento(Constantes.ID_TIPMOV_ANULACION));
			else
				ventaCarga.setTipoMovimiento(new TipoMovimiento(Constantes.ID_TIPMOV_EFECTIVO));									
			
			
			if(formaPagoId==FORMA_PAGO_CONTADO) {
				if(tipoPagoId==TIPO_PAGO_EFECTIVO) {
					ventaCarga.setTipoTransaccion("V.(EF)");
					ventaCarga.setFormaPago(new FormaPago(Constantes.ID_FORPAG_CONTADO));
				}else if(tipoPagoId==TIPO_PAGO_TARJETA) {
					ventaCarga.setTipoTransaccion("V.(TC)");
					ventaCarga.setFormaPago(new FormaPago(Constantes.ID_FORPAG_CONTADO));
				}else {
					ventaCarga.setTipoTransaccion("CORT"); 
					ventaCarga.setFormaPago(new FormaPago(Constantes.ID_FORPAG_CORTESIA));
				}
			}else if(tipoPagoId==FORMA_PAGO_CREDITO) {
				ventaCarga.setTipoTransaccion("CREDITO");
				ventaCarga.setFormaPago(new FormaPago(Constantes.ID_FORPAG_CREDITO));
			}else {
				ventaCarga.setTipoTransaccion("PCE");
				ventaCarga.setFormaPago(new FormaPago(Constantes.ID_FORPAG_CREDITO));
			}
			
			if(tipoComprobanteId==1)
				ventaCarga.setTipoComprobante(new TipoComprobante(Constantes.ID_TIPCOM_FACTURA));
			else if(tipoComprobanteId==2)
				ventaCarga.setTipoComprobante(new TipoComprobante(Constantes.ID_TIPCOM_BOLETA_VENTA));
			else if(tipoComprobanteId==30) {
				ventaCarga.setTipoComprobante(new TipoComprobante(Constantes.ID_TIPCOM_NOTA_CREDITO));
				ventaCarga.setTipoTransaccion("NOTA CREDITO");
			}else if(tipoComprobanteId==85)
				ventaCarga.setTipoComprobante(new TipoComprobante(Constantes.ID_TIPCOM_GUIA_TRANSPORTISTA));
	
			if(resultSet.getString("SERIE_FACTURA")!=null)
				ventaCarga.setNumeroBoleto(resultSet.getString("SERIE_FACTURA")+"-"+resultSet.getString("NRO_FACTURA"));
			else
				ventaCarga.setNumeroBoleto(resultSet.getString("NRO_FACTURA"));
			if(!(simboloMoneda.equals("S/")))
				ventaCarga.setTipoMoneda(new TipoMoneda(2)); //Dolares
			ventaCarga.setId(resultSet.getLong("IDFACTURA"));
			ventaCarga.setTarifa(resultSet.getDouble("TOTAL_COSTO"));
			ventaCarga.setRecargo(0.00);
			ventaCarga.setDescuento(0.00);
			ventaCarga.setAcuenta(0.00);
			ventaCarga.setPenalidad(0.00);
			ventaCarga.setImportePagado(ventaCarga.getTarifa());
			Usuario usuario= new Usuario();
			usuario.setApellidoPaterno("");
			usuario.setApellidoMaterno("");
			usuario.setNombre(resultSet.getString("NOMBRES"));
			ventaCarga.setUsuario(usuario);
			ventaCarga.setFechaInsercion(resultSet.getDate("FECHA_ACTUALIZACION"));
			ventaCarga.setFechaLiquidacion(resultSet.getDate("FECHA_FACTURA"));
			
			
			listResult.add(ventaCarga);
		}
        
        
		return listResult;
	}

	/* (non-Javadoc)
	 * @see com.cystesoft.vyrbus.model.dao.TranscarDAO#buscarAgencias()
	 */
	@Override
	public List<Agencia> buscarAgencias() throws Exception {
		// TODO Auto-generated method stub
		String sql="select  T_AGENCIAS.IDAGENCIAS IDAGENCIAS, "
				+ "         T_AGENCIAS.NOMBRE_AGENCIA, T_AGENCIAS.COD_AGE_SISVYR "
				+ "from T_AGENCIAS "
				+ "where T_AGENCIAS.Idestado_Registro=1 ";
		
		List<?> result=jdbcTemplate.queryForList(sql);
		
		Map<String, Object> map = new HashMap<String, Object>();
		List<Agencia> listAgencias= new ArrayList<>();
		for(int i=0;i<result.size();i++){
			map = (Map<String, Object>)result.get(i);
			Agencia agencia= new Agencia();
			agencia.setId(((BigDecimal)map.get("IDAGENCIAS")).intValue());
			agencia.setDenominacion(map.get("NOMBRE_AGENCIA").toString());
			if(map.get("COD_AGE_SISVYR")!=null)
				agencia.setCodigo(map.get("COD_AGE_SISVYR").toString());
			listAgencias.add(agencia);
		}
				
		return listAgencias;
	}

	/* (non-Javadoc)
	 * @see com.cystesoft.vyrbus.model.dao.TranscarDAO#buscarUsuariosByVenta(java.lang.Integer, java.lang.String, java.lang.String)
	 */
	@Override
	public List<Usuario> buscarUsuariosByVenta(Integer agenciaId, String fechaInicio, String fechaFin)throws Exception {
		if(agenciaId==null)
			agenciaId = 0;
		// TODO Auto-generated method stub
		String storeProcedure="{call PKG_IVOCONTROLUSUARIO.sp_Lista_UsuariosByVenta(?,?,?,?)}";
		//Llamanado el SP
		CallableStatement callableStatement = jdbcTemplate.getDataSource().getConnection().prepareCall(storeProcedure);		
		//Parametros de entrada
		callableStatement.setInt("VI_AGENCIAID", agenciaId);
        callableStatement.setString("VI_FECHAINICIO", fechaInicio);
        callableStatement.setString("VI_FECHAFIN", fechaFin);
        callableStatement.registerOutParameter("CUR_USUARIOS",OracleTypes.CURSOR);
        callableStatement.execute();
        
        ResultSet resultSet = (ResultSet) callableStatement.getObject("CUR_USUARIOS");
        List<Usuario> listUsuarios= new ArrayList<Usuario>();
        while (resultSet.next()) {
			Usuario usuario= new Usuario();
			usuario.setId(resultSet.getInt("IDUSUARIO_PERSONAL"));
			usuario.setApellidoPaterno(resultSet.getString("APELLIDO_PATERNO"));
			usuario.setApellidoMaterno(resultSet.getString("APELLIDO_MATERNO"));
			usuario.setNombre(resultSet.getString("NOMBRE"));
			usuario.setLogin(resultSet.getString("LOGIN"));
			
			listUsuarios.add(usuario);
		}		
		
		return listUsuarios;
	}
	
	
	
}
