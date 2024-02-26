/**
 * Proyecto		: SISVYR
 * Sistema		: Sistema de Ventas y Reservas
 * Descripci�n	:
 * Autor		: Jos� Abanto
 * Fecha		: 26 oct. 2021
 * Hora			: 10:25:21
 */
package pe.itsb.vyrbus.model.dao.impl;

import java.io.File;
import java.io.FileInputStream;
import java.math.BigDecimal;
import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.TreeMap;

import org.jfree.util.Log;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import pe.itsb.vyrbus.model.bean.Agencia;
import pe.itsb.vyrbus.model.bean.Bus;
import pe.itsb.vyrbus.model.bean.CanalVenta;
import pe.itsb.vyrbus.model.bean.FormaPago;
import pe.itsb.vyrbus.model.bean.Itinerario;
import pe.itsb.vyrbus.model.bean.Liquidacion;
import pe.itsb.vyrbus.model.bean.Manifiesto;
import pe.itsb.vyrbus.model.bean.Pasajero;
import pe.itsb.vyrbus.model.bean.Ruta;
import pe.itsb.vyrbus.model.bean.TipoComprobante;
import pe.itsb.vyrbus.model.bean.TipoFormaPago;
import pe.itsb.vyrbus.model.bean.TipoMoneda;
import pe.itsb.vyrbus.model.bean.TipoMovimiento;
import pe.itsb.vyrbus.model.bean.TranscarLiquidacionTurno;
import pe.itsb.vyrbus.model.bean.TranscarRolUsuario;
import pe.itsb.vyrbus.model.bean.TranscarUsuarioPersonal;
import pe.itsb.vyrbus.model.bean.Usuario;
import pe.itsb.vyrbus.model.bean.VentaPasaje;
import pe.itsb.vyrbus.model.dao.TranscarDAO;
import pe.itsb.vyrbus.service.util.Constantes;
import pe.itsb.vyrbus.service.util.Util;

/**
 * @author abant
 *
 */
@SuppressWarnings("unchecked")
public class TranscarDAOImpl implements TranscarDAO{
	private JdbcTemplate jdbcTemplate;
	private int ID_TIPPAG_EFECTIVO = 1;
	private int ID_TIPPAG_TARJETA = 2;
	private int ID_FORPAG_CONTADO = 1;
	private int ID_FORPAG_CREDITO = 2;
	private int ID_FORPAG_PCE = 3;
	private int ID_TIPCOM_FACTURA = 1;
	private int ID_TIPCOM_BOLETA = 2;
	private int ID_TIPCOM_NOTACREDITO = 30;
	private int ID_TIPCOM_PCE = 85;
	private int ID_TARJETA_VISA = 19;
	private int ID_TARJETA_MASTERCARD = 21;

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
	 * @see pe.itsb.vyrbus.model.dao.TranscarDAO#buscarRolesUsauurio()
	 */
	@Override
	public List<TranscarRolUsuario> buscarRolesUsuario() throws Exception {
		// TODO Auto-generated method stub

		String storeProcedure="{call PKG_IVOCONTROLUSUARIO.SP_LISTA_ROL_USUARIO(?,?)}";
		//Llamanado el SP
//		CallableStatement callableStatement = getJdbcTranscar().getDataSource().getConnection().prepareCall(storeProcedure);
		CallableStatement callableStatement = getJdbcTranscar().getDataSource().getConnection().prepareCall(storeProcedure);
		//Parametros de entrada (index, parametros)
		callableStatement.setInt(1, 1);
		callableStatement.registerOutParameter(2, oracle.jdbc.OracleTypes.CURSOR);
		callableStatement.execute();

		//Se obtiene el cursor en forma de ResultSet
		ResultSet resultSet = (ResultSet) callableStatement.getObject(2);
		List<TranscarRolUsuario> resultRolUsuario= new ArrayList<>();
		while (resultSet.next()) {
			TranscarRolUsuario rolUsuario= new TranscarRolUsuario();
			rolUsuario.setId(resultSet.getInt("IDROL_USUARIO"));
			rolUsuario.setNombre(resultSet.getString("ROL_USUARIO"));
			resultRolUsuario.add(rolUsuario);
		}
		callableStatement.close();

		return resultRolUsuario;
	}

	/* (non-Javadoc)
	 * @see pe.itsb.vyrbus.model.dao.TranscarDAO#buscarRolesUsuario(java.lang.Integer)
	 */
	@Override
	public List<TranscarRolUsuario> buscarRolesUsuario(Integer usuarioId) throws Exception {
		// TODO Auto-generated method stub

		String storeProcedure="{call PKG_IVOCONTROLUSUARIO.SP_DATOS_USUARIO_ROL(?,?,?)}";
		//Llamanado el SP
//		CallableStatement callableStatement = getJdbcTranscar().getDataSource().getConnection().prepareCall(storeProcedure);
		CallableStatement callableStatement = getJdbcTranscar().getDataSource().getConnection().prepareCall(storeProcedure);
		//Parametros de entrada
		callableStatement.setInt("idUser", usuarioId);
		callableStatement.registerOutParameter("cur_Usuario", oracle.jdbc.OracleTypes.CURSOR);
		callableStatement.registerOutParameter("cur_Roles", oracle.jdbc.OracleTypes.CURSOR);
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
		List<TranscarRolUsuario> resultRolUsuario= new ArrayList<>();
		while (resultSet.next()) {
			TranscarRolUsuario rolUsuario= new TranscarRolUsuario();
			rolUsuario.setId(resultSet.getInt("IDROL"));
			rolUsuario.setNombre(resultSet.getString("ROL"));
			rolUsuario.setTranscarUsuarioPersonal(usuarioPersonal);
			resultRolUsuario.add(rolUsuario);
		}
		callableStatement.close();

		return resultRolUsuario;
	}

	/* (non-Javadoc)
	 * @see pe.itsb.vyrbus.model.dao.TranscarDAO#buscarUsuarioPersonal(java.lang.String)
	 */

	@Override
	public TranscarUsuarioPersonal buscarUsuarioPersonal(String login) throws Exception {
		// TODO Auto-generated method stub
		String sql="select up.idusuario_personal, up.login from t_usuario_personal up where up.login='"+login+"' and up.idestado_registro=1 ";

		List<?> result=getJdbcTranscar().queryForList(sql);

		TranscarUsuarioPersonal usuarioPersonal=null;
		Map<String, Object> map = new HashMap<>();
		for (Object element : result) {
			map = (Map<String, Object>)element;
			usuarioPersonal = new TranscarUsuarioPersonal();
			usuarioPersonal.setId(((BigDecimal)map.get("IDUSUARIO_PERSONAL")).intValue());
			usuarioPersonal.setLogin(map.get("LOGIN").toString());
		}

		return usuarioPersonal;
	}

	/* (non-Javadoc)
	 * @see pe.itsb.vyrbus.model.dao.TranscarDAO#guardarUsuarioPersonal(pe.itsb.vyrbus.model.bean.TranscarUsusrioPeronal, boolean)
	 */
	@Override
	public void guardarUsuarioPersonal(TranscarUsuarioPersonal transcarUsusrioPeronal, String idsRoles, boolean isNuevo)
			throws Exception {
		// TODO Auto-generated method stub
		int icontrol = (isNuevo?1:2);
		String storeProcedure="{call PKG_IVOCONTROLUSUARIO.INSUPD_USUARIO_PERSONAL_2(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}";
		//Llamanado el SP
//		CallableStatement callableStatement = jdbcTemplate.getDataSource().getConnection().prepareCall(storeProcedure);
		CallableStatement callableStatement = getJdbcTranscar().getDataSource().getConnection().prepareCall(storeProcedure);
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
        callableStatement.registerOutParameter("cur_usuario_Personal", oracle.jdbc.OracleTypes.CURSOR);
        callableStatement.registerOutParameter("cur_usuario_Modificado", oracle.jdbc.OracleTypes.CURSOR);
        callableStatement.registerOutParameter("cur_err", oracle.jdbc.OracleTypes.CURSOR); //41
		callableStatement.execute();

		ResultSet resultSet = (ResultSet) callableStatement.getObject("cur_usuario_Personal");
		while (resultSet.next()) {
			System.out.println(resultSet.getString("msgbox").toString());
		}

		callableStatement.close();
	}

//	/* (non-Javadoc)
//	 * @see pe.itsb.vyrbus.model.dao.TranscarDAO#buscarIdAgenciaByCodigoAgenciaPasajes(java.lang.String)
//	 */
//	@Override
//	public Integer buscarIdAgenciaByCodigoAgenciaPasajes(String codigoAgenciaPasajes) throws Exception {
//		// TODO Auto-generated method stub
//		Integer agenciaId = null;
//		try {
//			String sql="select ag.idagencias from t_agencias ag where ag.cod_age_sisvyr = '"+codigoAgenciaPasajes+"'";
//			agenciaId=getJdbcTranscar().queryForInt(sql);
//		} catch (Exception e) {}
//
//		return agenciaId;
//	}

	/* (non-Javadoc)
	 * @see pe.itsb.vyrbus.model.dao.TranscarDAO#aperturarLiquidacion(pe.itsb.vyrbus.model.bean.TranscarLiquidacionTurno)
	 */
	@Override
	public String aperturarLiquidacion(TranscarLiquidacionTurno liquidacionTurno) throws Exception {
		// TODO Auto-generated method stub

		String storeProcedure="{call PKG_IVOCIERRE_LIQUIDACIONES.SP_INSUPD_LIQUI_TURNOS_(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}";
		//Llamanado el SP
//		CallableStatement callableStatement = jdbcTemplate.getDataSource().getConnection().prepareCall(storeProcedure);
		CallableStatement callableStatement = getJdbcTranscar().getDataSource().getConnection().prepareCall(storeProcedure);
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
        callableStatement.registerOutParameter("CUR_INSUPD_LIQUI_TURNOS",oracle.jdbc.OracleTypes.CURSOR);
        callableStatement.execute();

        ResultSet resultSet = (ResultSet) callableStatement.getObject("CUR_INSUPD_LIQUI_TURNOS");
        ResultSetMetaData rsmd=resultSet.getMetaData();
        int nroColumns = rsmd.getColumnCount();

        //System.out.println("Total columns: " + rsmd.getColumnCount());
        //System.out.println("Column Name of 1st column: " + rsmd.getColumnName(1));
        //System.out.println("Column Type Name of 1st column: " + rsmd.getColumnTypeName(1));

		String messageError=null;
        while (resultSet.next()) {
			try {
				if(nroColumns > 1)
					messageError = resultSet.getString("errmsg").toString();
				else
					messageError = null;

			} catch (Exception e) {
//				callableStatement.close();
			}
		}

        callableStatement.close();

		return messageError;
	}

	/* (non-Javadoc)
	 * @see pe.itsb.vyrbus.model.dao.TranscarDAO#buscarDetalleVentas(java.lang.Integer, java.lang.Integer, java.lang.String, java.lang.String)
	 */
	@Override
	public List<VentaPasaje> buscarDetalleVentas(TranscarUsuarioPersonal usuarioPersonal, Integer agenciaId, String fechaInicial,String fechaFinal) throws Exception {
		// TODO Auto-generated method stub
//		int TIPO_PAGO_EFECTIVO = 1;
//		int TIPO_PAGO_TARJETA = 2;
////		int TIPO_PAGO_CORTESIA = 3;
//		int FORMA_PAGO_CONTADO = 1;
//		int FORMA_PAGO_CREDITO = 2;
////		int FORMA_PAGO_PCE = 3;

		if(usuarioPersonal==null){
			usuarioPersonal = new TranscarUsuarioPersonal();
			usuarioPersonal.setId(0);
		}

		if(agenciaId ==null)
			agenciaId = 0;

		String storeProcedure="{call PKG_IVOCIERRE_LIQUIDACIONES.SP_LIST_VENTAS_PRELI_TURNO_2(?,?,?,?,?,?,?)}";
		//Llamanado el SP
//		CallableStatement callableStatement = jdbcTemplate.getDataSource().getConnection().prepareCall(storeProcedure);
		CallableStatement callableStatement = getJdbcTranscar().getDataSource().getConnection().prepareCall(storeProcedure);
		//Parametros de entrada
		callableStatement.setInt("P_IDUSUARIO_PERSONAL", usuarioPersonal.getId());
        callableStatement.setInt("p_IDAgencias", agenciaId);
        callableStatement.setString("P_FECHA_INICIAL", fechaInicial);
        callableStatement.setString("P_FECHA_FINAL", fechaFinal);
        callableStatement.registerOutParameter("cur_resumen",oracle.jdbc.OracleTypes.CURSOR);
        callableStatement.registerOutParameter("CUR_LIST_VENTAS_PRELI_TURNO",oracle.jdbc.OracleTypes.CURSOR);
        callableStatement.registerOutParameter("CUR_ERROR",oracle.jdbc.OracleTypes.CURSOR);
        callableStatement.execute();

        ResultSet resultSet = (ResultSet) callableStatement.getObject("CUR_LIST_VENTAS_PRELI_TURNO");
        List<VentaPasaje> listResult= new ArrayList<>();
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


			if(formaPagoId==ID_FORPAG_CONTADO) {
				if(tipoPagoId==ID_TIPPAG_EFECTIVO) {
					ventaCarga.setTipoTransaccion("V.(EF)");
					ventaCarga.setFormaPago(new FormaPago(Constantes.ID_FORPAG_CONTADO, "CONTADO"));
					ventaCarga.setTipoFormaPago(new TipoFormaPago(Constantes.ID_TIPFORPAG_EFECTIVO, "EFECTIVO"));
				}else if(tipoPagoId==ID_TIPPAG_TARJETA) {
					ventaCarga.setTipoTransaccion("V.(TC)");
					ventaCarga.setFormaPago(new FormaPago(Constantes.ID_FORPAG_CONTADO, "CONTADO"));
					ventaCarga.setTipoFormaPago(new TipoFormaPago(Constantes.ID_TIPFORPAG_TARJETA, "TARJETA"));
				}else {
					ventaCarga.setTipoTransaccion("CORT");
					ventaCarga.setFormaPago(new FormaPago(Constantes.ID_FORPAG_CORTESIA, "CORTESIA"));
					ventaCarga.setTipoFormaPago(new TipoFormaPago(Constantes.ID_TIPFORPAG_CORTESIA, "CORTESIA"));
				}
			}else if(tipoPagoId==ID_FORPAG_CREDITO) {
				ventaCarga.setTipoTransaccion("CREDITO");
				ventaCarga.setFormaPago(new FormaPago(Constantes.ID_FORPAG_CREDITO, "CREDITO"));
				ventaCarga.setTipoFormaPago(new TipoFormaPago(Constantes.ID_TIPFORPAG_CREDITO, "CREDITO"));
			}else {
				ventaCarga.setTipoTransaccion("PCE");
				ventaCarga.setFormaPago(new FormaPago(Constantes.ID_FORPAG_CREDITO, "PCE"));
				ventaCarga.setTipoFormaPago(new TipoFormaPago(Constantes.ID_TIPFORPAG_PCE, "PCE"));
			}

			if(tipoComprobanteId==1)
				ventaCarga.setTipoComprobante(new TipoComprobante(Constantes.ID_TIPCOM_FACTURA, "FACTURA"));
			else if(tipoComprobanteId==2)
				ventaCarga.setTipoComprobante(new TipoComprobante(Constantes.ID_TIPCOM_BOLETA_VENTA, "BOLETA DE VENTA"));
			else if(tipoComprobanteId==30) {
				ventaCarga.setTipoComprobante(new TipoComprobante(Constantes.ID_TIPCOM_NOTA_CREDITO, "NOTA DE CREDITO"));
				ventaCarga.setTipoTransaccion("NOTA CREDITO");
			}else if(tipoComprobanteId==85)
				ventaCarga.setTipoComprobante(new TipoComprobante(Constantes.ID_TIPCOM_GUIA_TRANSPORTISTA, "GUIA TRANSPORTISTA"));

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
			usuario.setLogin(usuarioPersonal.getLogin());
			ventaCarga.setUsuario(usuario);
			ventaCarga.setFechaInsercion(resultSet.getDate("FECHA_ACTUALIZACION"));
			ventaCarga.setFechaLiquidacion(resultSet.getDate("FECHA_FACTURA"));
			Agencia agenciaVenta=new Agencia();
			agenciaVenta.setDenominacion(resultSet.getString("NOMBRE_AGENCIA"));
			ventaCarga.setAgencia(agenciaVenta);
			Pasajero pasajero = new Pasajero();
			pasajero.setNumeroDocumento(resultSet.getString("CODIGO_CLIENTE")!=null? resultSet.getString("CODIGO_CLIENTE"): "");
			pasajero.setNombresApellidos(resultSet.getString("RAZON_SOCIAL"));
			ventaCarga.setPasajero(pasajero);
			Ruta ruta = new Ruta();
			ruta.setOrigen(resultSet.getString("NOMBRE_UNIDAD_ORI"));
			ruta.setDestino(resultSet.getString("NOMBRE_UNIDAD_DES"));
			ventaCarga.setRuta(ruta);


			listResult.add(ventaCarga);
		}

        callableStatement.close();

		return listResult;
	}

	/* (non-Javadoc)
	 * @see pe.itsb.vyrbus.model.dao.TranscarDAO#buscarAgencias()
	 */
	@Override
	public List<Agencia> buscarAgencias() throws Exception {
		// TODO Auto-generated method stub
		String sql="select  T_AGENCIAS.IDAGENCIAS IDAGENCIAS, "
				+ "         T_AGENCIAS.NOMBRE_AGENCIA, T_AGENCIAS.COD_AGE_SISVYR "
				+ "from T_AGENCIAS "
				+ "where T_AGENCIAS.Idestado_Registro=1 ";

		List<?> result=getJdbcTranscar().queryForList(sql);
//		List<?> result getJdbcTranscar().queryForList(sql);

		Map<String, Object> map = new HashMap<>();
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
	 * @see pe.itsb.vyrbus.model.dao.TranscarDAO#buscarUsuariosByVenta(java.lang.Integer, java.lang.String, java.lang.String)
	 */
	@Override
	public List<Usuario> buscarUsuariosByVenta(Integer agenciaId, String fechaInicio, String fechaFin)throws Exception {
		if(agenciaId==null)
			agenciaId = 0;
		// TODO Auto-generated method stub
		String storeProcedure="{call PKG_IVOCONTROLUSUARIO.sp_Lista_UsuariosByVenta(?,?,?,?)}";
		//Llamanado el SP
//		CallableStatement callableStatement = jdbcTemplate.getDataSource().getConnection().prepareCall(storeProcedure);
//		CallableStatement callableStatement = getConnectionTranscar().prepareCall(storeProcedure);
		CallableStatement callableStatement = getJdbcTranscar().getDataSource().getConnection().prepareCall(storeProcedure);
		//Parametros de entrada
		callableStatement.setInt("VI_AGENCIAID", agenciaId);
        callableStatement.setString("VI_FECHAINICIO", fechaInicio);
        callableStatement.setString("VI_FECHAFIN", fechaFin);
        callableStatement.registerOutParameter("CUR_USUARIOS",oracle.jdbc.OracleTypes.CURSOR);
        callableStatement.execute();

        ResultSet resultSet = (ResultSet) callableStatement.getObject("CUR_USUARIOS");
        List<Usuario> listUsuarios= new ArrayList<>();
        while (resultSet.next()) {
			Usuario usuario= new Usuario();
			usuario.setId(resultSet.getInt("IDUSUARIO_PERSONAL"));
			usuario.setApellidoPaterno(resultSet.getString("APELLIDO_PATERNO"));
			usuario.setApellidoMaterno(resultSet.getString("APELLIDO_MATERNO"));
			usuario.setNombre(resultSet.getString("NOMBRE"));
			usuario.setLogin(resultSet.getString("LOGIN"));

			listUsuarios.add(usuario);
		}

        callableStatement.close();

		return listUsuarios;
	}

	/* (non-Javadoc)
	 * @see pe.itsb.vyrbus.model.dao.TranscarDAO#buscarLiquidacionTurno(java.lang.Integer, java.lang.Integer, java.lang.String, java.lang.String)
	 */
	@Override
	public List<Liquidacion> buscarLiquidacionTurnoResumenEspVal(Integer usuarioId, Integer agenciaId, String fechaInicio, String fechaFin) throws Exception {
		// TODO Auto-generated method stub

		if(usuarioId==null)
			usuarioId=0;
		if(agenciaId ==null)
			agenciaId = 0;

		String storeProcedure="{call PKG_IVOCIERRE_LIQUIDACIONES.SP_LIST_VENTAS_PRELI_TURNO_2(?,?,?,?,?,?,?)}";
		//Llamanado el SP
		CallableStatement callableStatement = getJdbcTranscar().getDataSource().getConnection().prepareCall(storeProcedure);
		//Parametros de entrada
		callableStatement.setInt("P_IDUSUARIO_PERSONAL", usuarioId);
        callableStatement.setInt("p_IDAgencias", agenciaId);
        callableStatement.setString("P_FECHA_INICIAL", fechaInicio);
        callableStatement.setString("P_FECHA_FINAL", fechaFin);
        callableStatement.registerOutParameter("cur_resumen",oracle.jdbc.OracleTypes.CURSOR);
        callableStatement.registerOutParameter("CUR_LIST_VENTAS_PRELI_TURNO",oracle.jdbc.OracleTypes.CURSOR);
        callableStatement.registerOutParameter("CUR_ERROR",oracle.jdbc.OracleTypes.CURSOR);
        callableStatement.execute();

        ResultSet resultSetResumen = (ResultSet) callableStatement.getObject("cur_resumen");
//        ResultSetMetaData rsmdResumen=resultSetResumen.getMetaData();
//        int nroColumnsResumen = rsmdResumen.getColumnCount();

        ResultSet resultSet = (ResultSet) callableStatement.getObject("CUR_LIST_VENTAS_PRELI_TURNO");
//        ResultSetMetaData rsmd=resultSet.getMetaData();
//        int nroColumns = rsmd.getColumnCount();

        List<Liquidacion> listResult= new ArrayList<>();

        while (resultSet.next()) {
        	Double montoFacturas=.00;
        	Double montoBoletas=.00;
			while (resultSetResumen.next()){
				montoFacturas += resultSetResumen.getDouble("FACTURA");
				montoBoletas += resultSetResumen.getDouble("BOLETA");
			}

			if(resultSet.getString("FACTU_SERI")!=null && !(resultSet.getString("FACTU_SERI").equals("0"))){
				Liquidacion liquidacion= new Liquidacion();
				liquidacion.setTipoComprobante(new TipoComprobante(Constantes.ID_TIPCOM_FACTURA));
				liquidacion.setComprobante("FACTURA");
				String serie = resultSet.getString("FACTU_SERI");
				String numeroInicial = String.valueOf(resultSet.getInt("FACTU_INI"));
				String numeroFinal = String.valueOf(resultSet.getInt("FACTU_FINAL"));
				liquidacion.setSerie(serie);
				liquidacion.setBoletoInicial(Util.autocompleNumberBoleto(serie+"-"+numeroInicial).split("-")[1]);
				liquidacion.setBoletoFinal(Util.autocompleNumberBoleto(serie+"-"+numeroFinal).split("-")[1]);
				liquidacion.setCantidadBoletos(resultSet.getInt("FACTU_CONTA"));
				liquidacion.setImporte(montoFacturas);
				listResult.add(liquidacion);
			}
			if(resultSet.getString("BOLE_SERI")!=null && !(resultSet.getString("BOLE_SERI").equals("0"))){
				Liquidacion liquidacion= new Liquidacion();
				liquidacion.setTipoComprobante(new TipoComprobante(Constantes.ID_TIPCOM_BOLETA_VENTA));
				liquidacion.setComprobante("BOLETA DE VENTA");
				String serie = resultSet.getString("BOLE_SERI");
				String numeroInicial = String.valueOf(resultSet.getInt("BOLE_INI"));
				String numeroFinal = String.valueOf(resultSet.getInt("BOLE_FINAL"));
				liquidacion.setSerie(serie);
				liquidacion.setBoletoInicial(Util.autocompleNumberBoleto(serie+"-"+numeroInicial).split("-")[1]);
				liquidacion.setBoletoFinal(Util.autocompleNumberBoleto(serie+"-"+numeroFinal).split("-")[1]);
				liquidacion.setCantidadBoletos(resultSet.getInt("BOLE_CONTA"));
				liquidacion.setImporte(montoBoletas);
				listResult.add(liquidacion);
			}
			break;
		}

        Liquidacion liquidacion = null;
        Double montoPce = .00;
        Integer cantidadPce = 0;
		while (resultSet.next()) {
			int tipoComprobanteId = resultSet.getInt("IDTIPO_COMPROBANTE");
			if(tipoComprobanteId==ID_TIPCOM_PCE){ //Solo pce
				montoPce += resultSet.getDouble("TOTAL_COSTO");
				cantidadPce ++;
				if(liquidacion==null){
					liquidacion= new Liquidacion();
					liquidacion.setTipoComprobante(new TipoComprobante(Constantes.ID_TIPCOM_GUIA_TRANSPORTISTA));
					liquidacion.setComprobante("PCE");
					liquidacion.setSerie("    ");
					liquidacion.setBoletoInicial(resultSet.getString("NRO_FACTURA1"));
				}else{
					liquidacion.setBoletoFinal(resultSet.getString("NRO_FACTURA1"));
					liquidacion.setCantidadBoletos(cantidadPce);
					liquidacion.setImporte(montoPce);
				}
			}
		}
		if(liquidacion!=null){
			if(liquidacion.getboletoFinal()==null){
				liquidacion.setBoletoFinal(liquidacion.getBoletoInicial());
				liquidacion.setCantidadBoletos(cantidadPce);
				liquidacion.setImporte(montoPce);
			}
			if(liquidacion.getBoletoInicial().length()>8)
				liquidacion.setBoletoInicial(liquidacion.getBoletoInicial().substring(3,liquidacion.getBoletoInicial().length()));
			if(liquidacion.getboletoFinal().length()>8)
				liquidacion.setBoletoFinal(liquidacion.getboletoFinal().substring(3,liquidacion.getboletoFinal().length()));
			listResult.add(liquidacion);
		}

		callableStatement.close();

		return listResult;
	}

	/* (non-Javadoc)
	 * @see pe.itsb.vyrbus.model.dao.TranscarDAO#buscarLiquidacionTurno(java.lang.Integer, java.lang.Integer, java.lang.String, java.lang.String)
	 */
	@Override
	public Liquidacion buscarLiquidacionTurno(Integer usuarioId,Integer agenciaId, String fechaInicio, String fechaFin)throws Exception {
		// TODO Auto-generated method stub

		if(usuarioId==null)
			usuarioId=0;
		if(agenciaId ==null)
			agenciaId = 0;

		String storeProcedure="{call PKG_IVOCIERRE_LIQUIDACIONES.SP_LIST_VENTAS_PRELI_TURNO_2(?,?,?,?,?,?,?)}";
		//Llamanado el SP
		CallableStatement callableStatement = getJdbcTranscar().getDataSource().getConnection().prepareCall(storeProcedure);
		//Parametros de entrada
		callableStatement.setInt("P_IDUSUARIO_PERSONAL", usuarioId);
        callableStatement.setInt("p_IDAgencias", agenciaId);
        callableStatement.setString("P_FECHA_INICIAL", fechaInicio);
        callableStatement.setString("P_FECHA_FINAL", fechaFin);
        callableStatement.registerOutParameter("cur_resumen",oracle.jdbc.OracleTypes.CURSOR);
        callableStatement.registerOutParameter("CUR_LIST_VENTAS_PRELI_TURNO",oracle.jdbc.OracleTypes.CURSOR);
        callableStatement.registerOutParameter("CUR_ERROR",oracle.jdbc.OracleTypes.CURSOR);
        callableStatement.execute();

//        ResultSet resultSetResumen = (ResultSet) callableStatement.getObject("cur_resumen");
//        Double efectivo=.00, tarjetaVisa=.00, tarjetaMastercard=.00, notaCredito=.00, pce=.00;
//        while (resultSetResumen.next()) {
//			int tipoPagoId = resultSetResumen.getInt("IDTIPO_PAGO");
//			int tarjetasId = resultSetResumen.getInt("IDTARJETAS");
//			if(tipoPagoId==1){ //Efectivo
//				efectivo += resultSetResumen.getDouble("BOLETA");
//				efectivo += resultSetResumen.getDouble("FACTURA");
//				notaCredito += resultSetResumen.getDouble("NOTA_CREDITO");
//			}
//			if(tipoPagoId==2 && tarjetasId==19){ //Tarjeta Visa
//				tarjetaVisa += resultSetResumen.getDouble("BOLETA");
//				tarjetaVisa += resultSetResumen.getDouble("FACTURA");
//				notaCredito += resultSetResumen.getDouble("NOTA_CREDITO");
//			}
//			if(tipoPagoId==2 && tarjetasId==21){ //Tarjeta Mastercard
//				tarjetaMastercard += resultSetResumen.getDouble("BOLETA");
//				tarjetaMastercard += resultSetResumen.getDouble("FACTURA");
//				notaCredito += resultSetResumen.getDouble("NOTA_CREDITO");
//			}
//			if(tipoPagoId==85){ //PCE
//				pce += resultSetResumen.getDouble("PCE");
//			}
//		}

        ResultSet resultSet = (ResultSet) callableStatement.getObject("CUR_LIST_VENTAS_PRELI_TURNO");
        Integer cantidadEfectivo=0, cantidadTarjetaVisa=0, cantidadTarjetaMastercard=0, cantidadNotaCredito=0, cantidadPce=0;
        Double efectivo=.00, tarjetaVisa=.00, tarjetaMastercard=.00, notaCredito=.00, pce=.00;
        while (resultSet.next()) {
//        	int idEstadoRegistro = resultSet.getInt("IDESTADO_FACTURA");
			int tipoPagoId = resultSet.getInt("IDTIPO_PAGO");
			int formaPagoId = resultSet.getInt("IDFORMA_PAGO");
			int tipoComprobanteId = resultSet.getInt("IDTIPO_COMPROBANTE");
			int tarjetasId = resultSet.getInt("IDTARJETAS");
			Double totalCosto = resultSet.getDouble("TOTAL_COSTO");

			if(formaPagoId==ID_FORPAG_CONTADO){
				if(tipoComprobanteId==ID_TIPCOM_BOLETA || tipoComprobanteId==ID_TIPCOM_FACTURA){
					if(tipoPagoId==ID_TIPPAG_EFECTIVO){
						cantidadEfectivo ++;
						efectivo += totalCosto;
					}else if(tipoPagoId==ID_TIPPAG_TARJETA && tarjetasId==ID_TARJETA_VISA){
						cantidadTarjetaVisa ++;
						tarjetaVisa += totalCosto;
					}else if(tipoPagoId==ID_TIPPAG_TARJETA && tarjetasId==ID_TARJETA_MASTERCARD){
						cantidadTarjetaMastercard ++;
						tarjetaMastercard += totalCosto;
					}
				}else if(tipoComprobanteId==ID_TIPCOM_NOTACREDITO){
					cantidadNotaCredito ++;
					notaCredito += totalCosto;
				}
			}else if(formaPagoId==ID_FORPAG_PCE){
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

        callableStatement.close();

		return liquidacion;
	}


	/* (non-Javadoc)
	 * @see pe.itsb.vyrbus.model.dao.TranscarDAO#cerrarLiquidacion(java.lang.Integer, java.lang.Integer, java.lang.String, java.lang.String)
	 */
	@Override
	public void cerrarLiquidacion(Integer usuarioId, Integer agenciaId,String fechaInicio, String fechaFin) throws Exception {
		// TODO Auto-generated method stub

		Liquidacion liquidacionTurno = buscarLiquidacionTurno(usuarioId, agenciaId, fechaInicio, fechaFin);

		Double entreSoles = .00, entreDolares = .00, totalFactura = .00;
		Double totalBoleta = .00, totalVisa = .00, totalMastercard = .00, totalDevolucion = .00,totalNotaCredito = .00;
		//****************Consulta los montos totales
		String storeProcedure="{call PKG_LIQUIDACION_OFICINAS.SP_Monto(?,?,?,?,?,?)}";
		CallableStatement callableStatement = getJdbcTranscar().getDataSource().getConnection().prepareCall(storeProcedure);
		callableStatement.setInt("ni_Opcion", 1);
		callableStatement.setInt("ni_Usuario	", usuarioId);
		callableStatement.setString("vi_Fecha", fechaInicio);
		callableStatement.setInt("ni_Agencia", agenciaId);
		callableStatement.registerOutParameter("co_datos",oracle.jdbc.OracleTypes.CURSOR);
		callableStatement.registerOutParameter("co_error",oracle.jdbc.OracleTypes.CURSOR);
		callableStatement.execute();

		ResultSet resultSet = (ResultSet) callableStatement.getObject("co_datos");
		while (resultSet.next()) {
			Double montoFactura = resultSet.getDouble("FACTURA");
			Double montoBoleta = resultSet.getDouble("BOLETA");
			totalFactura += montoFactura;
			totalBoleta += montoBoleta;
			totalNotaCredito += resultSet.getDouble("NOTA_CREDITO");

			String tarjeta = resultSet.getString("TARJETA");
			if(tarjeta.equals("VISA")){
				totalVisa += montoFactura + montoBoleta;
			}else if(tarjeta.equals("MASTER CARD")){
				totalMastercard += montoFactura + montoBoleta;
			}
		}
		entreSoles = liquidacionTurno.getMontoContado() - totalNotaCredito;

		//Obtiene el identificado de la liquidacion
		String sql = "select lq.idliqui_turnos "
				   + "from t_liqui_turnos lq  "
				   + "where lq.idusuario_personal= "+usuarioId+" and lq.idagencias="+agenciaId+" and to_char(lq.fecha_aper,'dd/MM/yyyy') = '"+fechaInicio+"'";
		List<?> result=getJdbcTranscar().queryForList(sql);
		Long liquidacionId = (long)0;
		Map<String, Object> map = new HashMap<>();
		for (Object element : result) {
			map = (Map<String, Object>)element;
			liquidacionId = ((BigDecimal)map.get("idliqui_turnos")).longValue();
		}

		/**Realiza el cierre de la liquidacion ***/
		storeProcedure="{call PKG_IVOCIERRE_LIQUIDACIONES.SP_LIQUI_TURNO_I(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}"; //21
		//Llamanado el SP
		callableStatement = getJdbcTranscar().getDataSource().getConnection().prepareCall(storeProcedure);
		//Parametros de entrada
		callableStatement.setLong("P_IDLIQUI_TURNOS", liquidacionId);
		callableStatement.setInt("P_IDUSUARIO_PERSONAL", usuarioId);
        callableStatement.setInt("P_IDAGENCIAS", agenciaId);
        callableStatement.setString("P_FECHA_INICIAL", fechaInicio);
        callableStatement.setString("P_FECHA_FINAL", fechaFin);
        callableStatement.setString("P_IPMOD", "");
        callableStatement.setInt("P_IDUSUARIO_PERSONALMOD", usuarioId);
        callableStatement.setString("P_OBsER", "null");
        callableStatement.setInt("P_CERRADO", 1);
        callableStatement.setInt("P_IDROL_USUARIOMOD", 1);
        callableStatement.setDouble("p_ENTRE_SOLES", entreSoles);
        callableStatement.setDouble("p_ENTRE_DOLA", entreDolares);
        callableStatement.setDouble("p_ENTRE_TIPO_CAMBI", 00);

        callableStatement.setDouble("p_total_Monto_Factura", totalFactura);
        callableStatement.setDouble("p_total_Monto_Boleta", totalBoleta);
        callableStatement.setDouble("p_Total_Monto_PCE", liquidacionTurno.getMontoPCE());
        callableStatement.setDouble("p_total_Visa_Carga", totalVisa);
        callableStatement.setDouble("p_total_masterCard_Carga", totalMastercard);
        callableStatement.setDouble("p_total_Devolucion_Carga", totalDevolucion);
        callableStatement.setDouble("p_total_Monto_NotaCredito", totalNotaCredito);

        callableStatement.registerOutParameter("CUR_LIQUI_TURNO",oracle.jdbc.OracleTypes.CURSOR);
        callableStatement.execute();


	}


	/**
	 *
	 * @return
	 * @throws Exception
	 */
	private JdbcTemplate getJdbcTranscar() throws Exception {
		try {
		 	String pathJdbcproperties = Util.getPath()+Util.separator+"WEB-INF"+Util.separator+"jdbc.properties";
		 	File file = new File(pathJdbcproperties);
		 	FileInputStream input = new FileInputStream(file);
			Properties properties = new Properties();
			properties.load(input);

			DriverManagerDataSource driverManagerDataSource=new DriverManagerDataSource();
			driverManagerDataSource.setDriverClassName(properties.getProperty("jdbc.driverClassName"));
			driverManagerDataSource.setUrl(properties.getProperty("jdbc.transcar.url"));
			driverManagerDataSource.setUsername(properties.getProperty("jdbc.transcar.userName"));
			driverManagerDataSource.setPassword(properties.getProperty("jdbc.transcar.password"));

			JdbcTemplate jdbcTemplate=new JdbcTemplate(driverManagerDataSource);

			return jdbcTemplate;
		}finally{
			try {
			} catch (Exception ex) {
				throw new Exception(ex.getMessage());
			}
		}
	}

	/* (non-Javadoc)
	 * @see pe.itsb.vyrbus.model.dao.TranscarDAO#buscarLiquidacionBus(java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	public TreeMap<String, Manifiesto> buscarLiquidacionBus(String fechaInicio, String fechaFin, String codigoBus) throws Exception {
		// TODO Auto-generated method stub
		String sql = "SELECT gt.fecha_salida fecha_salida, ut.nro_unidad_transporte bus_codigo, ut.placa bus_placa "
				         + ",pt.apepat piloto_apePat, pt.apemat piloto_apeMat, pt.nomper piloto_nombres "
				         + ",SUM(dgt.total_costo) total_soles "
				   + "FROM t_guia_transportista gt "
				     + "INNER JOIN t_guia_transportista_detall dgt ON (dgt.idguia_transportista=gt.idguia_transportista) "
				     + "INNER JOIN t_unidad_transporte ut ON (ut.idunidad_transporte=gt.idunidad_transporte) "
				     + "INNER JOIN t_usuario_personal pt ON (pt.idusuario_personal=gt.idusuario_personal_piloto) "
				   + "WHERE gt.fecha_salida BETWEEN '"+fechaInicio+"' AND '"+fechaFin+"' "
				     + "AND gt.idestado_registro NOT IN (2,3) "
				     + "AND dgt.idestado_registro NOT IN (2,3) "
				     + "AND ut.nro_unidad_transporte = NVL ("+codigoBus+", ut.nro_unidad_transporte) "
				   + "GROUP BY gt.fecha_salida, ut.nro_unidad_transporte, ut.placa, pt.apepat, pt.apemat, pt.nomper "
				   + "ORDER BY gt.fecha_salida, ut.nro_unidad_transporte, pt.apepat, pt.apemat, pt.nomper";

		List<?> result=getJdbcTranscar().queryForList(sql);
		Map<String, Object> map = new HashMap<>();

		TreeMap<String, Manifiesto> liquidacionBus = new TreeMap<>();
		for (Object element : result) {
			map = (Map<String, Object>)element;

			Itinerario itinerario = new Itinerario();
			itinerario.setFechaPartida((Date)map.get("fecha_salida"));
			Bus bus = new Bus();
			bus.setCodigo(map.get("bus_codigo").toString());
			bus.setNumeroPlaca(map.get("bus_plala").toString());
			String piloto = (map.get("piloto_apePat")!=null?map.get("piloto_apePat").toString():"");
			piloto += (map.get("piloto_apeMat")!=null?map.get("piloto_apeMat").toString():"");
			piloto += (map.get("piloto_nombres")!=null?map.get("piloto_nombres").toString():"");

			Manifiesto manifiesto = new Manifiesto();
			manifiesto.setItinerario(itinerario);
			manifiesto.setBus(bus);
			manifiesto.setPiloto(piloto);
			manifiesto.setImporte(((BigDecimal)map.get("total_soles")).doubleValue());

			String keymap = Constantes.FORMAT_DATE.format(itinerario.getFechaPartida());
			keymap += "-" + bus.getCodigo();

			liquidacionBus.put(keymap, manifiesto);
		}

		return liquidacionBus;
	}

	/* (non-Javadoc)
	 * @see pe.itsb.vyrbus.model.dao.TranscarDAO#buscarLiquidacionCounter(java.lang.String, java.lang.String, java.lang.Integer, java.lang.Integer)
	 */
	@Override
	public TreeMap<String, Liquidacion> buscarLiquidacionCounter(String fechaInicio, String fechaFin,  Integer agenciaId, Integer usuarioId) throws Exception {
		// TODO Auto-generated method stub
		String sql = "SELECT to_char(lq.fecha_aper,'dd/MM/yyyy') fecha, lq.idusuario_personal,up.login, ag.cod_age_sisvyr, NVL(lq.entre_soles,0) entre_soles "
				+ "  ,lq.entre_dola, lq.cerrado, lq.fecha_cierre "
				+ "FROM t_liqui_turnos lq "
				+ "  INNER JOIN t_usuario_personal up ON (up.idusuario_personal=lq.idusuario_personal) "
				+ "  INNER JOIN t_agencias ag ON (ag.idagencias=lq.idagencias) "
				+ "WHERE to_char(lq.fecha_aper,'dd/MM/yyyy') BETWEEN '" + fechaInicio + "' AND '" + fechaFin + "' "
				+ "  AND ag.cod_age_sisvyr = NVL(" + agenciaId + ", lq.idagencias) "
//				+ "  AND lq.idusuario_personal = NVL("++", lq.idusuario_personal)  "
				+ "  AND lq.idestado_registro = 1";
		Log.info(sql);

		List<?> result=getJdbcTranscar().queryForList(sql);
		Map<String, Object> map = new HashMap<>();

		TreeMap<String, Liquidacion> resultLiquidacion = new TreeMap<>();
		for (Object element : result) {
			map = (Map<String, Object>)element;

			Usuario usuario= new Usuario();
			usuario.setId(((BigDecimal)map.get("idusuario_personal")).intValue());
			usuario.setLogin(map.get("login").toString());

			Liquidacion liquidacion= new Liquidacion();
			liquidacion.setFechaLiquidacion(Constantes.FORMAT_DATE.parse(map.get("fecha").toString()));
			liquidacion.setUsuario(usuario);
			liquidacion.setAgencia(new Agencia(Integer.valueOf(map.get("cod_age_sisvyr").toString()))); //Identificador de la agencia tal cual esta registradoe en VYR
			liquidacion.setImporte(((BigDecimal)map.get("entre_soles")).doubleValue());
			liquidacion.setEstadoLiquidacion(((BigDecimal)map.get("cerrado")).intValue());
			liquidacion.setFechaModificacion(map.get("fecha_cierre")!=null?(Date)map.get("fecha_cierre"):null);

			//Key
			String key = Constantes.FORMAT_DATE.format(liquidacion.getFechaLiquidacion());
			key += liquidacion.getAgencia().getId().toString();
			key += liquidacion.getUsuario().getLogin();

			resultLiquidacion.put(key, liquidacion);
		}


		return resultLiquidacion;
	}
}
