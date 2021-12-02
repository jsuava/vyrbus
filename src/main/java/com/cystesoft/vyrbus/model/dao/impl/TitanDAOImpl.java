/**
 * Proyecto		: SISVYR
 * Sistema		: Sistema de Ventas y Reservas
 * Descripción	: 
 * Fecha		: 17/06/2014
 */
package com.cystesoft.vyrbus.model.dao.impl;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.jdbc.core.JdbcTemplate;

import com.cystesoft.vyrbus.model.bean.TitanComisionPersonaBase;
import com.cystesoft.vyrbus.model.bean.TitanFuncionarioPersonaPasaje;
import com.cystesoft.vyrbus.model.bean.TitanLiquidacionTurnoPasaje;
import com.cystesoft.vyrbus.model.bean.TitanPersona;
import com.cystesoft.vyrbus.model.bean.TitanUsuarioHardware;
import com.cystesoft.vyrbus.model.bean.TitanUsuarioPersonal;
import com.cystesoft.vyrbus.model.bean.TitanVentaPasaje;
import com.cystesoft.vyrbus.model.dao.TitanDAO;
import com.cystesoft.vyrbus.service.util.Constantes;

/**
 * @author JABANTO
 *
 */
@SuppressWarnings("unchecked")
public class TitanDAOImpl implements TitanDAO {
	private JdbcTemplate jdbcTemplate;
	Logger log = Logger.getLogger(this.getClass());
	Integer USUARIO_PERSONAL_SISTEMAS=19388;
	
	/**
	 * @param jdbcTemplate the jdbcTemplate to set
	 */
	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}
	
	/** ******** TRANSACIONES REFERIDAS A LA LIQUIDACION TURNO PASAJE **********************/
	
	/* (non-Javadoc)
	 * @see com.tepsa.titan.dao.TitanDAO#buscarLiquidacionTurnoPasajeByIdLiquidacion(java.lang.Long)
	 */
	@Override
	public TitanLiquidacionTurnoPasaje buscarLiquidacionTurnoPasajeByIdLiquidacion(Long idLiquidacionSisvyr) throws Exception {
		String sql="SELECT ltp.id "+
					      ",ltp.fecha_apertura "+
					      ",ltp.cantcred AS cantidadCredito "+
					      ",ltp.totcred AS totalCredito "+
					      ",ltp.transferencia AS transferencia "+
					      ",ltp.idliq_sisvyr AS liquidacion_idSisvyr "+
					      ",ltp.fecha_registro" +
					      ",ltp.montoEfectivo "+
					      ",ltp.cantrecibo "+//
					      ",ltp.totrecibo "+
					      ",ltp.canttc "+
					      ",ltp.tottc "+
					      ",ltp.cantdevpsjes "+
					      ",ltp.totdevpsjes "+
					"FROM t_liqui_turno_pasaje ltp "+
					"WHERE ltp.idliq_sisvyr="+idLiquidacionSisvyr;
		log.info(sql);
		TitanLiquidacionTurnoPasaje liquidacionTurnoPasaje=null;
		
		List<?> result=jdbcTemplate.queryForList(sql);
		Map<String, Object> map = new HashMap<String, Object>();
		for(int i=0;i<result.size();i++){
			map = (Map<String, Object>)result.get(i);
			
			liquidacionTurnoPasaje=new TitanLiquidacionTurnoPasaje();
			liquidacionTurnoPasaje.setId(((BigDecimal)map.get("ID")).longValue());
			liquidacionTurnoPasaje.setFechaApertura((Date)map.get("FECHA_APERTURA"));
			liquidacionTurnoPasaje.setCantidadCredito(((BigDecimal)map.get("CANTIDADCREDITO")).intValue());
			liquidacionTurnoPasaje.setTotalCredito(((BigDecimal)map.get("TOTALCREDITO")).doubleValue());;
			liquidacionTurnoPasaje.setTransferencia(((BigDecimal)map.get("TRANSFERENCIA")).intValue());
			liquidacionTurnoPasaje.setLiquidacionSisVyrID(((BigDecimal)map.get("LIQUIDACION_IDSISVYR")).longValue());
			liquidacionTurnoPasaje.setFechaRegistro((Date)map.get("FECHA_REGISTRO"));
			liquidacionTurnoPasaje.setMontoEfectivo(((BigDecimal)map.get("MONTOEFECTIVO")).doubleValue());
			liquidacionTurnoPasaje.setCantidadReciboCaja(((BigDecimal)map.get("CANTRECIBO")).intValue());
			liquidacionTurnoPasaje.setTotalReciboCaja(((BigDecimal)map.get("TOTRECIBO")).doubleValue());
			liquidacionTurnoPasaje.setCantidadTarjetaCredito(((BigDecimal)map.get("CANTTC")).intValue());
			liquidacionTurnoPasaje.setTotalTarjetaCredito(((BigDecimal)map.get("TOTTC")).doubleValue());
			liquidacionTurnoPasaje.setCantidadDevolucion(((BigDecimal)map.get("CANTDEVPSJES")).intValue());
			liquidacionTurnoPasaje.setTotalDevolucion(((BigDecimal)map.get("TOTDEVPSJES")).doubleValue());
		}
		return liquidacionTurnoPasaje;
	}
	/* (non-Javadoc)
	 * @see com.tepsa.titan.dao.TitanDAO#actualizaLiquidacionTurnoPasajeByIdLiquidacion(com.tepsa.titan.bean.TitanLiquidacionTurnoPasaje)
	 */
	@Override
	public void actualizaLiquidacionTurnoPasajeByIdLiquidacion(TitanLiquidacionTurnoPasaje liquidacionTurnoPasaje)throws Exception {
		String sql="UPDATE t_liqui_turno_pasaje SET " +
						"cantcred="+liquidacionTurnoPasaje.getCantidadCredito()+" "+
						",totcred="+liquidacionTurnoPasaje.getTotalCredito()+" " +
						",montoEfectivo="+liquidacionTurnoPasaje.getMontoEfectivo()+" "+
						",cantrecibo="+liquidacionTurnoPasaje.getCantidadReciboCaja()+" "+
					    ",totrecibo="+liquidacionTurnoPasaje.getTotalReciboCaja()+" "+
					    ",canttc="+liquidacionTurnoPasaje.getCantidadTarjetaCredito()+" "+
					    ",tottc="+liquidacionTurnoPasaje.getTotalTarjetaCredito()+" "+
					    ",cantdevpsjes="+liquidacionTurnoPasaje.getCantidadDevolucion()+" "+
					    ",totdevpsjes="+liquidacionTurnoPasaje.getTotalDevolucion()+" "+
					"WHERE id="+liquidacionTurnoPasaje.getId();
		log.info("Actulizando liquidacion turno pasajes - TITAN "+sql);
		jdbcTemplate.execute(sql);		
	}

	
	
	/** ******** TRANSACIONES REFERIDAS A LA VENTA PASAJES **********************/
	/* (non-Javadoc)
	 * @see com.tepsa.titan.dao.TitanDAO#buscarBoletoVentaPasaje(java.lang.String, java.lang.String, java.lang.Integer, java.lang.Integer)
	 */
	@Override
	public TitanVentaPasaje buscarBoletoVentaPasaje(String serieBoleto,String numeroBoleto, Integer idCondicionBoleto) throws Exception {
		String sql="SELECT vp.idventa_pasajes "+
					      ",vp.serie_comprobante "+
					      ",vp.nro_comprobante "+
					      ",vp.idcondicion_boleto "+
					      ",vp.fecha_transferencia "+
					      ",vp.idtarjetas "+
					"FROM t_venta_pasajes vp " +
					"WHERE vp.serie_comprobante='"+serieBoleto+"' "+
					      "AND vp.nro_comprobante='"+numeroBoleto+"' "+
					      "AND vp.idcondicion_boleto="+idCondicionBoleto;
		
		TitanVentaPasaje ventaPasaje=null;
		List<?>result=jdbcTemplate.queryForList(sql);
		HashMap<String, Object>map=new HashMap<String,Object>();
		if(result.size()>0){
			map=(HashMap<String, Object>)result.get(0);
			
			ventaPasaje=new TitanVentaPasaje();
			ventaPasaje.setId(((BigDecimal)map.get("IDVENTA_PASAJES")).longValue());
			ventaPasaje.setSerie(map.get("SERIE_COMPROBANTE").toString());
			ventaPasaje.setNumeroBoleto(map.get("NRO_COMPROBANTE").toString());
			ventaPasaje.setIdCondicionBoleto(((BigDecimal)map.get("IDCONDICION_BOLETO")).intValue());
			ventaPasaje.setFechaTransferencia(map.get("FECHA_TRANSFERENCIA")!=null?Constantes.FORMAT_DATE.parse(map.get("FECHA_TRANSFERENCIA").toString()):null);
			ventaPasaje.setIdTarjetas(map.get("IDTARJETAS")!=null?((BigDecimal)map.get("IDTARJETAS")).intValue():null);
		}	
		return ventaPasaje;
	}
	/* 
	 * (non-Javadoc)
	 * @see com.tepsa.sisvyr.model.dao.TitanDAO#anularDevolverBoletoVentaPasaje(java.lang.Long, java.lang.Boolean)
	 */
	@Override
	public void anularDevolverBoletoVentaPasaje(Long idVentaPasje,Boolean isAnulacion) throws Exception {
		String sql="";
		/*Valida si es una anulacion*/
		if(isAnulacion){
			//Anula el Boleto
			sql="UPDATE t_venta_pasajes SET "+ 
				      "idcondicion_boleto=10 "+
				      ",idestado_registro=2 "+
				      ",monto_base=0 "+
				      ",monto_penalidad=0 "+
				      ",monto_descuento=0 "+
				      ",monto_recargo=0 "+
				      ",monto_total=0 "+
				      ",monto_efectivo=0 "+
				      ",monto_tarjeta=0 "+
				"WHERE idventa_pasajes="+idVentaPasje;
		}else{
			//Devuelve el boleto
			sql="UPDATE t_venta_pasajes SET "+ 
				      "idcondicion_boleto=11 "+
				"WHERE idventa_pasajes="+idVentaPasje;
		}
		
		jdbcTemplate.execute(sql);
	}

	/*
	 * (non-Javadoc)
	 * @see com.tepsa.sisvyr.model.dao.TitanDAO#actualizarFormaPago(com.tepsa.sisvyr.model.bean.TitanVentaPasaje)
	 */
	@Override
	public void actualizarFormaPago(TitanVentaPasaje titanVentaPasaje)throws Exception{
		
	}
	
	/********* TRANSACIONES REFERIDAS AL USUARIO PERSONAL **********************/
	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.model.dao.TitanDAO#buscarUsuarioPersonalPorLogin(java.lang.String)
	 */
	@Override
	public TitanUsuarioPersonal buscarUsuarioPersonalPorLogin(String login)throws Exception {
		String sql="SELECT up.idusuario_personal "+
					      ",up.login "+
					      ",up.apepat As apellidoPaterno "+
					      ",up.apemat AS apellidoMaterno "+
					      ",up.nomper AS nombres "+
					"FROM t_Usuario_Personal up "+
					"WHERE up.login='"+login+"' "+
					  "AND up.idestado_registro=1";
		log.info(sql);
		
		List<?> result= jdbcTemplate.queryForList(sql);
		HashMap<String, Object>map=new HashMap<String,Object>();
		TitanUsuarioPersonal usuarioPersonal=null;
		
		if(result.size()>0){
			map=(HashMap<String, Object>)result.get(0);
			
			usuarioPersonal=new TitanUsuarioPersonal();
			usuarioPersonal.setId(((BigDecimal)map.get("IDUSUARIO_PERSONAL")).longValue());
			usuarioPersonal.setLogin(map.get("LOGIN").toString());
			usuarioPersonal.setApellidoPaterno(map.get("APELLIDOPATERNO")!=null?map.get("APELLIDOPATERNO").toString():null);
			usuarioPersonal.setApellidoMaterno(map.get("APELLIDOMATERNO")!=null?map.get("APELLIDOMATERNO").toString():null);
			usuarioPersonal.setNombres(map.get("NOMBRES")!=null?map.get("NOMBRES").toString():null);
		}
		
		return usuarioPersonal;
	}
	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.model.dao.TitanDAO#guardarUsuarioPersonal(com.tepsa.sisvyr.model.bean.TitanUsuarioPersonal)
	 */
	@Override
	public void guardarUsuarioPersonal(TitanUsuarioPersonal titanUsuarioPersonal)throws Exception {
		// TODO Auto-generated method stub
		//Obtiene el id con que que se va a registrar el usuario personal
		Long idIsuarioPersonal = (long) 0;
		String sql="SELECT sec_idusuario_personal.nextval FROM DUAL";
		log.info(sql);
		idIsuarioPersonal= jdbcTemplate.queryForLong(sql);
		//Setea el nuevo identificador para el usuario personal
		titanUsuarioPersonal.setId(idIsuarioPersonal);
		
		//Realiza la creacion del usuario personal
		sql="INSERT INTO t_usuario_personal "+
						 "(idusuario_personal "+
						   ",nomper "+
						   ",apemat "+
						   ",apepat "+
						   ",idtipo_doc_identidad "+
						   ",idtipo_usuario_personal "+
						   ",login "+
						   ",password "+
						   ",idusuario_creador "+
						   ",idrol_creador "+
						   ",fecha_registro "+
						   ",fecha_actualizacion "+
						   ",idarea_sistema "+
						   ",idsexo "+
						   ",idestado_registro "+
						   ",iddistrito "+
						   ",idprovincia "+
						   ",iddepartamento "+
						   ",idpais "+
						   ",idubigeo "+
						   ",idestado_civil "+
						   ",idusuario_modificador "+
						   ",id_comi_funci "+
						   ",trabaja_en "+
						   ",ind_otras_agencias "+
						   ",ind_autoriza_entrega) "+
			 "VALUES "+
						  "("+titanUsuarioPersonal.getId()+" "+ //v_idusuario_personal
						   ",'"+titanUsuarioPersonal.getNombres() +"' "+
						   ",'"+titanUsuarioPersonal.getApellidoMaterno() +"' "+
						   ",'"+titanUsuarioPersonal.getApellidoPaterno() +"' "+
						   ",9 "+ //-->idtipo_doc_identidad (S/N)
						   ",361  "+ //-->dtipo_usuario_personal (G.Comercial)
						   ",'"+titanUsuarioPersonal.getLogin()+"' "+ //Login
						   ",'"+titanUsuarioPersonal.getLogin()+"' "+ // Password
						   ","+USUARIO_PERSONAL_SISTEMAS+" "+ //--idusuario_creador (sistemas)
						   ",2 "+ // -->idrol_creador
						   ",sysdate  "+ // -->fecha_registro
						   ",sysdate  "+ //-->fecha_actualizacion
						   ",1 "+ //-->idarea_sistema
						   ",1 "+ //-->idsexo
						   ",1 "+ //-->idestado_registro
						   ",2 "+ //-->iddistrito
						   ",17 "+ //-->idprovincia
						   ",15 "+ //-->iddepartamento
						   ",4 "+ //-->idpais
						   ",7895 "+ //-->idubigeo
						   ",1 "+ //-->idestado_civil
						   ","+USUARIO_PERSONAL_SISTEMAS+" "+ //-->idusuario_modificador (sistemas)
						   ",'FUPN' "+ //-->v_id_comi_funci
						   ",'PASA' "+ //-->trabaja_en
						   ",0 "+ //-->ind_otras_agencias
						   ",0 "+ //-->ind_autoriza_entrega
						   ") ";
		log.info(sql);
		jdbcTemplate.execute(sql);
	}
	
	
	/********* TRANSACIONES REFERIDAS AL CLIENTE (T_PERSONAL) **********************/
	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.model.dao.TitanDAO#buscarPersonaPorRuc(java.lang.String)
	 */
	@Override
	public TitanPersona buscarPersonaPorRuc(String numeroRuc) throws Exception {
		String sql="SELECT p.idpersona "+
				      ",p.idtipo_persona "+
				      ",p.codigo_cliente "+
				      ",p.cliente_corporativo "+
				      ",p.nu_docu_suna "+
				      ",p.razon_social "+
				      ",p.fecha_ingreso "+
				      ",p.idtipo_doc_identidad "+
				      ",p.idrubro "+
				      ",p.idclasificacion_persona "+
				      ",p.tipo_credito "+
				      ",p.origen "+
				      ",p.idestado_registro "+
//				      ",p.idusuario_personal "+
//				      ",p.idrol_usuario "+
//				      ",p.ip "+
//				      ",p.fecha_registro "+
				"FROM T_PERSONA p  "+
				"WHERE p.nu_docu_suna='"+numeroRuc+"' "+
				    "AND p.idestado_registro=1";
		log.info(sql);
		
		List<?>result=jdbcTemplate.queryForList(sql);
		HashMap<String, Object>map=new HashMap<String,Object>();
		TitanPersona persona=null;
		
		if(result.size()>0){
			map=(HashMap<String, Object>) result.get(0);
			
			persona=new TitanPersona();
			persona.setId(((BigDecimal)map.get("IDPERSONA")).longValue());
			persona.setTipoPersona(map.get("IDTIPO_PERSONA")!=null?((BigDecimal)map.get("IDTIPO_PERSONA")).intValue():null);
			persona.setCodigoCliente(map.get("codigo_cliente")!=null?map.get("codigo_cliente").toString():null);
			persona.setClienteCorporativo(map.get("CLIENTE_CORPORATIVO")!=null?((BigDecimal)map.get("CLIENTE_CORPORATIVO")).intValue():null);
			persona.setNumeroDocumentoSunat(map.get("NU_DOCU_SUNA")!=null?map.get("NU_DOCU_SUNA").toString():null);
			persona.setRazonSocial(map.get("RAZON_SOCIAL")!=null?map.get("RAZON_SOCIAL").toString():null);
			persona.setFechaIngreso(map.get("FECHA_INGRESO")!=null?(Date)map.get("FECHA_INGRESO"):null);
			persona.setTipoDocumentoIdentidadID(map.get("IDTIPO_DOC_IDENTIDAD")!=null?((BigDecimal)map.get("IDTIPO_DOC_IDENTIDAD")).intValue():null);
			persona.setRubroID(map.get("IDRUBRO")!=null?((BigDecimal)map.get("IDRUBRO")).intValue():null);
			persona.setClasificacionPersonaID(map.get("IDCLASIFICACION_PERSONA")!=null?((BigDecimal)map.get("IDCLASIFICACION_PERSONA")).intValue():null);
			persona.setTipoCredito(map.get("TIPO_CREDITO")!=null?map.get("TIPO_CREDITO").toString():null);
			persona.setOrigen(map.get("ORIGEN")!=null?((BigDecimal)map.get("ORIGEN")).intValue():null);
			persona.setEstadoRegistro(map.get("IDESTADO_REGISTRO")!=null?((BigDecimal)map.get("IDESTADO_REGISTRO")).intValue():null);
		}
	
		return persona;
	}
	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.model.dao.TitanDAO#actualizaPersona(com.tepsa.sisvyr.model.bean.TitanPersona)
	 */
	@Override
	public void actualizaPersona(TitanPersona persona) throws Exception {
		try{
			String sql= "UPDATE t_persona "+
							   "SET idtipo_persona ="+(persona.getTipoPersona()!=null?persona.getTipoPersona():null)+", "+
							       "codigo_cliente ="+(persona.getCodigoCliente()!=null?" '"+persona.getCodigoCliente()+"' ":null)+", "+
							       "cliente_corporativo ="+(persona.getClienteCorporativo()!=null?persona.getClienteCorporativo():null)+", "+
							       "razon_social ='"+persona.getRazonSocial()+"', "+
							       "idtipo_doc_identidad ="+persona.getTipoDocumentoIdentidadID()+", "+
							       "nu_rete_suna ='"+persona.getNumeroDocumentoSunat()+"', "+
							       "idrubro ="+(persona.getRubroID()!=null?persona.getRubroID():null)+", "+
							       "idclasificacion_persona ="+(persona.getClasificacionPersonaID()!=null?persona.getClasificacionPersonaID():null)+", "+
							       "tipo_credito ="+(persona.getTipoCredito()!=null?persona.getTipoCredito():null)+", "+
							       "nombres ='"+persona.getRazonSocial()+"', "+
							       "origen ="+(persona.getOrigen()!=null?persona.getOrigen():null)+","+
					 "WHERE idpersona ="+persona.getId()+" ";
		
		log.info(sql);
		jdbcTemplate.execute(sql);
		}catch(Exception ex){
			ex.printStackTrace();
			throw new Exception(ex.getMessage());
		}
		
	}
	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.model.dao.TitanDAO#guardarPersona(com.tepsa.sisvyr.model.bean.TitanPersona)
	 */
	@Override
	public void guardarPersona(TitanPersona persona) throws Exception {
		try {
			//Variables fijas.
			final Integer ID_PAIS_PERU=4;
			final Integer ID_DEPARTAMENTO_LIMA=15;
			final Integer ID_PROVINCIA_LIMA=17;
			final Integer ID_DISTRITO_LIMA=2;
			final Integer NRO_DIGITO_SERIE=3;
			
			//Obtiene el id para el cliente
			String sql="SELECT sec_idpersona.nextval FROM dual";
			log.info(sql);
			persona.setId(jdbcTemplate.queryForLong(sql));
			
			//Inserta el cliente
			sql="INSERT INTO t_persona "+
		             "(idpersona "+
		    	      ",idtipo_persona "+
		    	      ",codigo_cliente "+
		    	      ",cliente_corporativo "+
		    	      ",razon_social "+
		    	      ",fecha_ingreso "+
		    	      ",idtipo_doc_identidad "+
		    	      ",nu_docu_suna "+
		    	      ",nu_rete_suna "+
		    	      ",idrubro "+
		    	      ",idclasificacion_persona "+
		    	      ",idestado_registro "+
		    	      ",idusuario_personal "+
		    	      ",idrol_usuario "+
		    	      ",ip "+
		    	      ",fecha_registro "+
		    	      ",idusuario_personalmod "+
		    	      ",idrol_usuariomod "+
		    	      ",ipmod "+
		    	      ",fecha_actualizacion "+
		    	      ",idpais "+
		    	      ",iddepartamento "+
		    	      ",idprovincia "+
		    	      ",iddistrito "+
		    	      ",nro_digito_serie "+
		    	      ",tipo_credito "+
		    	      ",vigente "+
		    	      ",nombres "+
		    	      ",origen) "+
			   "VALUES "+
					   "( "+
					      persona.getId()+
					  ",1 "+ // Tipo de persona - juridica 
					  ",'"+persona.getCodigoCliente()+"' "+
					  ",0 "+ //clienteCorporativo - Cliente corporativo
					  ",'"+persona.getRazonSocial()+"' "+
					  ",to_date('"+Constantes.FORMAT_DATE.format(new Date())+"','dd/MM/yyyy') "+
					  ","+persona.getTipoDocumentoIdentidadID()+
					  ",'"+persona.getNumeroDocumentoSunat()+"' "+
					  ",'"+persona.getNumeroDocumentoSunat()+"' "+
					  ",350 "+ //Rubro - Generico
					  ",1 "+ //Clasificacion persona - Pasajes
					  ",1 "+ //Estado del registro 
					  ","+(persona.getUsuarioPersonal()!=null?persona.getUsuarioPersonal().getId():USUARIO_PERSONAL_SISTEMAS)+" "+
					  ",1036 "+ /*Rol Creador - Por defecto el rol de Sistemas */
					  ",'"+persona.getIp()+"' "+
					  ", SYSDATE "+ //Fecha registro
					  ","+(persona.getUsuarioPersonal()!=null?persona.getUsuarioPersonal().getId():USUARIO_PERSONAL_SISTEMAS)+" "+
					  ",1036 "+ /*Por defecto el rol de Sistemas*/
					  ",'"+persona.getIp()+"' "+
					  ", SYSDATE "+ //Fecha actualizacion
					  ","+ID_PAIS_PERU+
					  ","+ID_DEPARTAMENTO_LIMA+
					  ","+ID_PROVINCIA_LIMA+
					  ","+ID_DISTRITO_LIMA+
					  ","+NRO_DIGITO_SERIE+
					  ",'"+persona.getTipoCredito()+"' "+
					  ", 1"+ //VIGENTE
					  ",'"+persona.getRazonSocial()+"' "+
					  ","+persona.getOrigen()+
					    ")";
			log.info(sql);
			jdbcTemplate.execute(sql);
						
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception(e.getMessage());
		}
	}

	
	/********* TRANSACIONES REFERIDAS FUNCIONARIO PERSONA PASAJE **********************/
	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.model.dao.TitanDAO#buscarFuncionarioPersonaPasajePorIdPersona(java.lang.Long)
	 */
	@Override
	public TitanFuncionarioPersonaPasaje buscarFuncionarioPersonaPasajePorIdPersona(Long idPersona) throws Exception {
		String sql="SELECT pfp.idfuncionario "+
					      ",pfp.idfuncionario_actual "+
					      ",pfp.tipo_funionario "+
					      ",pfp.idpersona "+
					      ",TO_CHAR(pfp.fecha_activacion,'dd/MM/yyyy')fecha_activacion "+
					      ",TO_CHAR(pfp.fecha_suspencion,'dd/MM/yyyy') fecha_suspencion "+
					      ",pfp.comisionable  "+
					      ",pfp.idusuario_personal "+
					"FROM t_funcionario_persona_pasaje pfp "+
					"WHERE pfp.idpersona="+idPersona+" AND pfp.idestado_registro=1";
		log.info(sql);
		
		List<?>result=jdbcTemplate.queryForList(sql);
		HashMap<String, Object>map=new HashMap<String,Object>();
		TitanFuncionarioPersonaPasaje funcionarioPersonaPasaje=null;
		
		if(result.size()>0){
			map=(HashMap<String, Object>) result.get(0);
			
			funcionarioPersonaPasaje=new TitanFuncionarioPersonaPasaje();
			
			TitanUsuarioPersonal funcionario=null;
			if(map.get("IDFUNCIONARIO")!=null){
				funcionario=new TitanUsuarioPersonal();
				funcionario.setId(((BigDecimal)map.get("IDFUNCIONARIO")).longValue());
			}
			TitanUsuarioPersonal funcionarioActual=null;
			if(map.get("IDFUNCIONARIO_ACTUAL")!=null){
				funcionarioActual=new TitanUsuarioPersonal();
				funcionarioActual.setId(((BigDecimal)map.get("IDFUNCIONARIO_ACTUAL")).longValue());
			}
			TitanPersona persona=null;
			if(map.get("IDPERSONA")!=null){
				persona=new TitanPersona();
				persona.setId(((BigDecimal)map.get("IDPERSONA")).longValue());
			}
			if(map.get("FECHA_ACTIVACION")!=null)
				funcionarioPersonaPasaje.setFechaActivacion(map.get("FECHA_ACTIVACION").toString());
			if(map.get("FECHA_SUSPENCION")!=null)
				funcionarioPersonaPasaje.setFechaSuspencion(map.get("FECHA_SUSPENCION").toString());
			if(map.get("COMISIONABLE")!=null)
				funcionarioPersonaPasaje.setComisionable(((BigDecimal) map.get("COMISIONABLE")).intValue());
			TitanUsuarioPersonal usuarioPersonal=null;
			if(map.get("IDUSUARIO_PERSONAL")!=null){
				usuarioPersonal=new TitanUsuarioPersonal(((BigDecimal) map.get("IDUSUARIO_PERSONAL")).longValue());
			}
				
			funcionarioPersonaPasaje.setFuncionario(funcionario);
			funcionarioPersonaPasaje.setFuncionarioActual(funcionarioActual);
			funcionarioPersonaPasaje.setPersona(persona);
			funcionarioPersonaPasaje.setUsuarioPersonal(usuarioPersonal);
		}
		
		return funcionarioPersonaPasaje;
	}
	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.model.dao.TitanDAO#actualizaFuncionarioPerosnaPasajes(com.tepsa.sisvyr.model.bean.TitanFuncionarioPersonaPasaje)
	 */
	@Override
	public void actualizaFuncionarioPerosnaPasajes(TitanFuncionarioPersonaPasaje funcionarioPersonaPasaje)throws Exception {
		String sql="UPDATE t_funcionario_persona_pasaje "
					 +"SET idfuncionario ="+funcionarioPersonaPasaje.getFuncionario().getId()+", "
					     +"idfuncionario_actual ="+funcionarioPersonaPasaje.getFuncionarioActual().getId()+", "
					     +"idusuario_personalmod ="+(funcionarioPersonaPasaje.getUsuarioPersonal()!=null?funcionarioPersonaPasaje.getUsuarioPersonal().getId():USUARIO_PERSONAL_SISTEMAS)+", "
					     +"ipmod ='"+funcionarioPersonaPasaje.getIp()+"', "
					     +"fecha_actualizacion = SYSDATE , "
					     +"fecha_activacion =to_date('"+funcionarioPersonaPasaje.getFechaActivacion()+"','dd/MM/yyyy'), "
					     +"fecha_suspencion =to_date('"+funcionarioPersonaPasaje.getFechaSuspencion()+"','dd/MM/yyyy'), "
					     +"comisionable = "+funcionarioPersonaPasaje.getComisionable()+ " "
				  +"WHERE idPersona="+funcionarioPersonaPasaje.getPersona().getId();
		log.info(sql);
		jdbcTemplate.execute(sql);
	}
	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.model.dao.TitanDAO#guardaFuncionarioPerosnaPasajes(com.tepsa.sisvyr.model.bean.TitanFuncionarioPersonaPasaje)
	 */
	@Override
	public void guardaFuncionarioPerosnaPasajes(TitanFuncionarioPersonaPasaje funcionarioPersonaPasaje)throws Exception {
		String fechaSuspencion=null;
		//Calcula la fecha de suspencion la cual sera de 6 años despues de la fecha de activacion
		Date dfechaSuspencion=new Date(Constantes.FORMAT_DATE.parse(funcionarioPersonaPasaje.getFechaActivacion()).getTime()+((Constantes.MILISEGUNDOS_X_DIA*Constantes.DIAS_DEL_ANIO)*6));
		fechaSuspencion=Constantes.FORMAT_DATE.format(dfechaSuspencion);
		
		String sql="INSERT INTO t_funcionario_persona_pasaje "
							 +"(idfuncionario "
							 + ",idfuncionario_actual "
							 + ",tipo_funionario "
							 + ",idpersona "
							 + ",idusuario_personal "
							 + ",idrol_usuario "
							 + ",ip "
							 + ",fecha_registro "
							 + ",idusuario_personalmod " 
							 + ",idrol_usuariomod "
							 + ",ipmod "
							 + ",fecha_actualizacion "
							 + ",idestado_registro "
							 + ",fecha_activacion "
							 + ",fecha_suspencion "
							 + ",comisionable) "
					+"VALUES "
					         +"("+funcionarioPersonaPasaje.getFuncionario().getId()+" "
					         + ","+funcionarioPersonaPasaje.getFuncionarioActual().getId()+" "
					         + ",28 " /*Rol Funcionario pasajes*/
					         + ","+funcionarioPersonaPasaje.getPersona().getId()+" "
					         + ","+(funcionarioPersonaPasaje.getUsuarioPersonal()!=null?funcionarioPersonaPasaje.getUsuarioPersonal().getId():USUARIO_PERSONAL_SISTEMAS)+" "
					         + ",1036 " /*Por defecto el rol de Sistemas*/
					         + ",'"+funcionarioPersonaPasaje.getIp()+"' "
					         + ",SYSDATE " //fecha_registro
					         + ","+(funcionarioPersonaPasaje.getUsuarioPersonal()!=null?funcionarioPersonaPasaje.getUsuarioPersonal().getId():USUARIO_PERSONAL_SISTEMAS)+" "
					         + ",1036 " /*Por defecto el rol de Sistemas*/
					         + ",'"+funcionarioPersonaPasaje.getIp()+"' "
					         + ",SYSDATE " //fecha_actualizacion
					         + ",1 "//idestado_registro
					         + ",to_date('"+funcionarioPersonaPasaje.getFechaActivacion()+"','dd/MM/yyyy') "
					         + ",to_date('"+fechaSuspencion+"','dd/MM/yyyy') "
					         + ","+funcionarioPersonaPasaje.getComisionable()+") ";
		log.info(sql);
		jdbcTemplate.execute(sql);
	}

	
	/********* TRANSACIONES REFERIDAS COMISION PERSONA BASE HISTORICA **********************/
	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.model.dao.TitanDAO#buscarBaseHistoricaPorIdPersona(java.lang.Long)
	 */
	@Override
	public TitanComisionPersonaBase buscarBaseHistoricaPorIdPersona(Long idPersona) throws Exception {
		String sql="SELECT idpersona, idestado_registro, monto_base FROM t_comision_persona_base WHERE idPersona="+idPersona+" AND idEstado_registro=1";
		log.info(sql);
		
		List<?>result=jdbcTemplate.queryForList(sql);
		HashMap<String, Object>map=new HashMap<String,Object>();
		TitanComisionPersonaBase comisionPersonaBase=null;
		
		if(result.size()>0){
			map=(HashMap<String, Object>) result.get(0);
			comisionPersonaBase=new TitanComisionPersonaBase();
			
			comisionPersonaBase.setPersona(new TitanPersona(((BigDecimal)map.get("IDPERSONA")).longValue()));
			comisionPersonaBase.setMontoBase(((BigDecimal)map.get("MONTO_BASE")).doubleValue());
			comisionPersonaBase.setEstadoRegistroID(((BigDecimal)map.get("IDESTADO_REGISTRO")).intValue());
		}
			
		return comisionPersonaBase;
	}
	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.model.dao.TitanDAO#actualizaBaseHistorica(com.tepsa.sisvyr.model.bean.TitanComisionPersonaBase)
	 */
	@Override
	public void actualizaBaseHistorica(TitanComisionPersonaBase comisionPersonaBase) throws Exception {
		String sql="UPDATE t_comision_persona_base "
					   +"SET idpersona ="+comisionPersonaBase.getPersona().getId()+", "
					   	   +"idestado_registro = "+comisionPersonaBase.getEstadoRegistroID()+", "
					       +"monto_base = "+comisionPersonaBase.getMontoBase()+", "
				  +"WHERE idpersona="+comisionPersonaBase.getPersona().getId();
		log.info(sql);
		jdbcTemplate.execute(sql);
	}
	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.model.dao.TitanDAO#guardaBaseHistorica(com.tepsa.sisvyr.model.bean.TitanComisionPersonaBase)
	 */
	@Override
	public void guardaBaseHistorica(TitanComisionPersonaBase comisionPersonaBase)throws Exception {
		String sql="INSERT INTO t_comision_persona_base "
								+"(idpersona "
							    + ",idestado_registro "
							    + ",monto_base) "
					     +"VALUES "
						  	    +"("+comisionPersonaBase.getPersona().getId()+" "
						  	    + ","+comisionPersonaBase.getEstadoRegistroID()+" "
						  	    + ","+comisionPersonaBase.getMontoBase()+") ";
		log.info(sql);
		jdbcTemplate.execute(sql);
	}

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.model.dao.TitanDAO#buscarFechaFacturaEspecial(java.lang.String, java.lang.String)
	 */
	@Override
	public String buscarFechaFacturaEspecial(String serie, String numero, String numeroRuc)throws Exception {
		// TODO Auto-generated method stub
		if(numero.length()==8)
			numero=numero.substring(1);
		String sql="SELECT FO.IDFACTURA_OTRO, to_char(FO.FECHA_FACTURA,'dd/MM/yyyy') FECHA_FACTURA "
				 + "FROM T_FACTURA_OTRO FO "
				 	+ "INNER JOIN T_PERSONA P ON (P.IDPERSONA=FO.IDPERSONA) "
				 + "WHERE FO.SERIE_FACTURA='"+serie+"' AND FO.NRO_FACTURA='"+numero+"' AND P.NU_DOCU_SUNA='"+numeroRuc+"'";
		log.info(sql);
		
		String fechaFactura=null;
		List<?>result=jdbcTemplate.queryForList(sql);
		Map<String, Object> map = new HashMap<String, Object>();
		
		for(int i=0;i<result.size();i++){
			map = (Map<String, Object>)result.get(i);
			
			fechaFactura=map.get("FECHA_FACTURA").toString();
		}
		
		return fechaFactura;
	}

	/* (non-Javadoc)
	 * @see com.cystesoft.vyrbus.model.dao.TitanDAO#guardarUsuarioHardware(com.cystesoft.vyrbus.model.bean.TitanUsuarioHardware)
	 */
	@Override
	public void guardarUsuarioHardware(TitanUsuarioHardware titanUsuarioHardware) throws Exception {
		String sql = "INSERT INTO t_cpu(ip, iddepartamento_oficina, idtipo_maquina, frecuencia_reloj, nombre_equipo, nombre_red, es_servidor, nro_particiones, "
				+ "memoria_disco_duro_gb, idusuario_personal, idrol_usuario, idusuario_personalmod, idrol_usuariomod, ipregistro, ipmod, idagencias, "
				+ "idtipo_computador, idtipo_ip, vyrusuhard_id) VALUES ('"+titanUsuarioHardware.getIp()+"', "+titanUsuarioHardware.getIdDepartamento()+", "
				+ titanUsuarioHardware.getIdTipoMaquina()+", "+titanUsuarioHardware.getFrecuenciaReloj()+", '"+titanUsuarioHardware.getNombreEquipo()+"', '"
				+ titanUsuarioHardware.getNombreRed()+"', "+titanUsuarioHardware.getEsServidor()+", "+titanUsuarioHardware.getParticiones()+ ", "
				+ titanUsuarioHardware.getMemoria()+", "+titanUsuarioHardware.getIdUsuario()+", "+titanUsuarioHardware.getIdRol()+", "
				+ titanUsuarioHardware.getIdUsuarioModificacion()+", "+titanUsuarioHardware.getRolModificacion()+", '"+titanUsuarioHardware.getIpRegistro()+"', '" 
				+ titanUsuarioHardware.getIpModificacion()+"', "+titanUsuarioHardware.getIdAgencia()+", "+titanUsuarioHardware.getIdTipoComputador()+", "
				+ titanUsuarioHardware.getIdTipoIP()+", "+titanUsuarioHardware.getIdUsuarioHardwareVyR()+")";
		log.info(sql);
		jdbcTemplate.execute(sql);		
		
	}

	/* (non-Javadoc)
	 * @see com.cystesoft.vyrbus.model.dao.TitanDAO#buscarAgencia(java.lang.Integer)
	 */
	@Override
	public Integer buscarAgencia(Integer idAgenciaPasajes) throws Exception {
		String sql = "SELECT idagencias FROM T_AGENCIAS WHERE IDAGENCIAS_UNIX="+idAgenciaPasajes;
		log.info(sql);
		List<?> result = jdbcTemplate.queryForList(sql);
		HashMap<String, Object> map = new HashMap<String,Object>();
		Integer idAgencia=null;
		if(result.size()>0) {
			map = (HashMap<String, Object>) result.get(0);
			idAgencia = ((BigDecimal)map.get("idagencias")).intValue();
		}	
		return idAgencia;
	}

	/* (non-Javadoc)
	 * @see com.cystesoft.vyrbus.model.dao.TitanDAO#buscarUsuarioHardware(java.lang.Integer)
	 */
	@Override
	public TitanUsuarioHardware buscarUsuarioHardwareByIdVyR(Integer id) throws Exception {
		String sql = "SELECT ip, nombre_equipo, idusuario_personalmod, idrol_usuariomod, ipmod, idagencias, "
				+ "idtipo_ip, vyrusuhard_id FROM t_cpu WHERE vyrusuhard_id="+id;
		log.info(sql);
		
		List<?> result = jdbcTemplate.queryForList(sql);
		HashMap<String, Object> map = new HashMap<String,Object>();
		TitanUsuarioHardware titanUsuarioHardware = null;
		
		if(result.size()>0){
			map = (HashMap<String, Object>) result.get(0);
			titanUsuarioHardware = new TitanUsuarioHardware();
			
			titanUsuarioHardware.setIp(map.get("IP").toString());
			titanUsuarioHardware.setNombreEquipo(map.get("NOMBRE_EQUIPO").toString());
			titanUsuarioHardware.setIdUsuarioModificacion(((BigDecimal)map.get("IDUSUARIO_PERSONALMOD")).intValue());
			titanUsuarioHardware.setRolModificacion(((BigDecimal)map.get("IDROL_USUARIOMOD")).intValue());
			titanUsuarioHardware.setIpModificacion(map.get("IPMOD").toString());
			titanUsuarioHardware.setIdUsuarioModificacion(((BigDecimal)map.get("IDAGENCIAS")).intValue());
			titanUsuarioHardware.setIdTipoIP(((BigDecimal)map.get("IDTIPO_IP")).intValue());
			titanUsuarioHardware.setIdUsuarioHardwareVyR(((BigDecimal)map.get("VYRUSUHARD_ID")).intValue());
		}
		
		return titanUsuarioHardware;
	}

	/* (non-Javadoc)
	 * @see com.cystesoft.vyrbus.model.dao.TitanDAO#actualizarUsuarioHardware(com.cystesoft.vyrbus.model.bean.TitanUsuarioHardware)
	 */
	@Override
	public void actualizarUsuarioHardware(TitanUsuarioHardware titanUsuarioHardware) throws Exception {
		String sql = "UPDATE t_cpu SET idAgencias="+titanUsuarioHardware.getIdAgencia()+", nombre_equipo='"+titanUsuarioHardware.getNombreEquipo() +
				"', idtipo_ip="+titanUsuarioHardware.getIdTipoIP() + " WHERE ip = '"+titanUsuarioHardware.getIp()+"'";
		
		log.info(sql);
		jdbcTemplate.execute(sql);
	}

	/* (non-Javadoc)
	 * @see com.cystesoft.vyrbus.model.dao.TitanDAO#buscarIpUsuarioHardware(java.lang.String)
	 */
	@Override
	public String buscarIdUsuarioHardware(String ip) throws Exception {
		String sql = "SELECT ip FROM t_cpu WHERE ip='"+ip+"'";
		
		log.info(sql);
		List<?> result = jdbcTemplate.queryForList(sql);
		HashMap<String, Object> map = new HashMap<String,Object>();
		String idUsuarioHardware=null;
		if(result.size()>0) {
			map = (HashMap<String, Object>) result.get(0);
			idUsuarioHardware = map.get("ip").toString();
		}	
		return idUsuarioHardware;		
	}

	/* (non-Javadoc)
	 * @see com.cystesoft.vyrbus.model.dao.TitanDAO#inactivarUsuarioHardware(java.lang.Integer)
	 */
	@Override
	public void inactivarUsuarioHardware(Integer idUsuarioHardwareVyR) {
		String sql = "UPDATE t_cpu SET idestado_registro=2 WHERE vyrusuhard_id="+idUsuarioHardwareVyR;
		
		log.info(sql);
		jdbcTemplate.execute(sql);
	}
}
