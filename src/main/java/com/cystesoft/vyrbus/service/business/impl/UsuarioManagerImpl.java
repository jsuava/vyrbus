/**
 * Proyecto		: SISVYR
 * Sistema		: Sistema de Ventas y Reservas
 * Descripción	: Implementacion de metodos que permiten el acceso al modelo.
 * Autor		: José Sullo Avalos
 * Fecha		: 27/09/2012
 */
package com.cystesoft.vyrbus.service.business.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

import javax.security.auth.login.LoginException;

import org.springframework.transaction.annotation.Transactional;

import com.cystesoft.vyrbus.model.bean.DestinatariosEmails;
import com.cystesoft.vyrbus.model.bean.Usuario;
import com.cystesoft.vyrbus.model.dao.UsuarioDAO;
import com.cystesoft.vyrbus.service.business.UsuarioManager;
import com.cystesoft.vyrbus.service.exceptions.PasswordException;
import com.cystesoft.vyrbus.service.exceptions.UsuarioLoginDuplicadoException;
import com.cystesoft.vyrbus.service.util.Constantes;
import com.cystesoft.vyrbus.service.util.Encriptar;
import com.cystesoft.vyrbus.service.util.Sendmail;

/**
 * @author Jose
 *
 */
public class UsuarioManagerImpl implements UsuarioManager {
	private UsuarioDAO usuarioDAO;

	/**
	 * @return the usuarioDAO
	 */
	public UsuarioDAO getUsuarioDAO() {
		return usuarioDAO;
	}
	/**
	 * @param usuarioDAO the usuarioDAO to set
	 */
	public void setUsuarioDAO(UsuarioDAO usuarioDAO) {
		this.usuarioDAO = usuarioDAO;
	}

	/*
	 * (non-Javadoc)
	 * @see com.tepsa.sisvyr.service.business.UsuarioManager#buscarUsuarioPorLogin(java.lang.String, java.lang.String)
	 */
	@Override
	public Usuario buscarUsuarioPorLogin(String login, String estado)throws Exception {
		return getUsuarioDAO().buscarUsuarioPorLogin(login, estado);
	}

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.service.business.UsuarioManager#buscarUsuarioPorLoginPassword(java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	public Usuario buscarUsuarioPorLoginPassword(String login, String password, String estado) throws Exception {
		Usuario usuario = getUsuarioDAO().buscarUsuarioPorLogin(login, Constantes.VALUE_ACTIVO);
		if(usuario !=null){
			String desencrypt = Encriptar.decodifica(usuario.getPassword(), login);
			if(desencrypt.equals(password)){
				Usuario usuario1 = getUsuarioDAO().buscarUsuarioPorLoginPassword(login, usuario.getPassword(), estado);
//				usuario1.setPassword(Encriptar.decodifica(usuario.getPassword(), usuario.getLogin()));
				usuario1.setPwdNormal(Encriptar.decodifica(usuario.getPassword(), usuario.getLogin()));
				return usuario1;
			}else
				throw new PasswordException(PasswordException.PASSWORD_INCOREC);
		}else
			throw new LoginException();
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.tepsa.sisvyr.service.business.UsuarioManager#buscarPorEstadoRegistro(java.lang.String, java.lang.String)
	 */
	@Override
	public ArrayList<Usuario> buscarPorEstadoRegistro(String estado,String criterioOrden) throws Exception{
		ArrayList<Usuario> lstUsuarios = getUsuarioDAO().buscarPorEstadoRegistro(estado, criterioOrden);
		ArrayList<Usuario> lstResult = new ArrayList<Usuario>(); 
		for(Usuario  usuario : lstUsuarios){
			String pwd = Encriptar.decodifica(usuario.getPassword(), usuario.getLogin());
			if(pwd!=null){
//				usuario.setPassword(pwd);
				usuario.setPwdNormal(pwd);
				lstResult.add(usuario);
			}
		}
		return lstResult;
		
//		return lstUsuarios;
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.tepsa.sisvyr.service.business.UsuarioManager#buscarPorX(java.util.TreeMap, java.util.List)
	 */
	@Override
	public ArrayList<Usuario> buscarPorX(TreeMap<String, Object> criteriosBusqueda,List<String> criteriosOrdenar)throws Exception {
		ArrayList<Usuario> lstUsuarios = getUsuarioDAO().buscarPorX(criteriosBusqueda, criteriosOrdenar);
		ArrayList<Usuario> lstResult = new ArrayList<Usuario>();
		for(Usuario  usuario : lstUsuarios){
//			usuario.setPassword(Encriptar.decodifica(usuario.getPassword(), usuario.getLogin()));
			usuario.setPwdNormal(Encriptar.decodifica(usuario.getPassword(), usuario.getLogin()));
			lstResult.add(usuario);
		}
		return lstResult;
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.tepsa.sisvyr.service.business.UsuarioManager#buscarPorId(java.lang.Long)
	 */
	@Override
	public Usuario buscarPorId(Long id) throws Exception {
		Usuario usuario = getUsuarioDAO().buscarPorId(id);
//		usuario.setPassword(Encriptar.decodifica(usuario.getPassword(), usuario.getLogin()));
		usuario.setPwdNormal(Encriptar.decodifica(usuario.getPassword(), usuario.getLogin()));
		return usuario;
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.tepsa.sisvyr.service.business.UsuarioManager#guardar(com.tepsa.sisvyr.model.bean.Usuario)
	 */
	@Override
	@Transactional
	public int guardar(Usuario usuario) throws Exception{
		try{
			int result = Constantes.FAILURE;
			/*Valida duplicidad de Login de usuario*/
			TreeMap<String, Object> criteriosBusqueda = new TreeMap<String, Object>();
			criteriosBusqueda.put("login", usuario.getLogin());
//			criteriosBusqueda.put("estadoRegistro", Constantes.VALUE_ACTIVO);
			List<?> resultLogin = getUsuarioDAO().buscarPorX(criteriosBusqueda, null);
			if (resultLogin.size() > 0){
				throw new UsuarioLoginDuplicadoException();
			}
			String passwd = usuario.getPwdNormal();
			usuario.setPassword(Encriptar.codifica(passwd, usuario.getLogin()));
			getUsuarioDAO().guardar(usuario);
			
			//Realiza el envio del E-Mail con las credenciales de acceso.
			if(usuario.getEmailInfo()!=null){
				String mensaje = "Datos para el inicio de sesion en el Sistema de Ventas TEPSA.\n\n";
				mensaje = mensaje + "Nombre :\t\t"+usuario.toString()+"\n";
				mensaje = mensaje + "Usuario :\t\t"+usuario.getLogin()+"\n";
				mensaje = mensaje + "Password :\t\t"+passwd+"\n\n";
				mensaje = mensaje + "Una vez que ingrese al sistema con estas credenciales debera de cambiarlas para poder continuar con sus operaciones.\n\n";
				mensaje = mensaje + "NOTA: [Este buzon es de envio automático, por favor no responda.]";
				
				DestinatariosEmails window = new DestinatariosEmails();
				//window.setEmails("TO:"+usuario.getEmailInfo()+";BCC:sistemas@tepsa.com.pe");
				window.setEmails("TO:"+usuario.getEmailInfo());//+";BCC:jabanto@tepsa.com.pe");
				
				Sendmail.enviaEmail(mensaje, "Credenciales de Acceso - Sisvyr", window);
			}
			
			
			result = Constantes.CORRECT;
			return result;			
		}catch(UsuarioLoginDuplicadoException uldex){
			throw new UsuarioLoginDuplicadoException();
		}
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.tepsa.sisvyr.service.business.UsuarioManager#actualizar(com.tepsa.sisvyr.model.bean.Usuario)
	 */
	@Override
	@Transactional
	public void actualizar(Usuario usuario)throws Exception {
//		String passwd = usuario.getPassword();
		String passwd = usuario.getPwdNormal();
		usuario.setPassword(Encriptar.codifica(passwd, usuario.getLogin()));
		getUsuarioDAO().actualizar(usuario);
		
		//Realiza el envio del E-Mail con las credenciales de acceso.
		if(usuario.getEmailInfo()!=null){
			String mensaje = "Datos para el inicio de sesion en el Sistema de Ventas TEPSA.\n\n";
			mensaje = mensaje + "Nombre :\t\t"+usuario.toString()+"\n";
			mensaje = mensaje + "Usuario :\t\t"+usuario.getLogin()+"\n";
			mensaje = mensaje + "Password :\t\t"+passwd+"\n\n";
			mensaje = mensaje + "Una vez que ingrese al sistema con estas credenciales debera de cambiarlas para poder continuar con sus operaciones.\n\n";
			mensaje = mensaje + "NOTA: [Este buzon es de envio automático, por favor no responda.]";
			
			DestinatariosEmails window = new DestinatariosEmails();
			//window.setEmails("TO:"+usuario.getEmailInfo()+";BCC:sistemas@tepsa.com.pe");
			window.setEmails("TO:"+usuario.getEmailInfo());//+";BCC:jabanto@tepsa.com.pe");
//			if(usuario.getEmailInfo()!=null)
			Sendmail.enviaEmail(mensaje, "Credenciales de Acceso - Sisvyr", window);
		}
				
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.tepsa.sisvyr.service.business.UsuarioManager#inactivar(java.lang.Long)
	 */
	@Override
	@Transactional
	public void inactivar(Long id) throws Exception {
		getUsuarioDAO().inactivar(id);
		
	}
	@Override
	public void activar(Long id) throws Exception {
		getUsuarioDAO().activar(id);
		
	}
	/* 
	 * (non-Javadoc)
	 * @see com.tepsa.sisvyr.service.business.UsuarioManager#buscarUsuarioLiquidacion(java.lang.String, java.lang.String, java.lang.Integer)
	 */
	@Override
	public List<Usuario> buscarUsuarioLiquidacion(String fechaInicio,String fechaFinal, Integer idAgencia, Integer estadoLiquidacion) throws Exception {
		// TODO Auto-generated method stub
		return getUsuarioDAO().buscarUsuarioLiquidacion(fechaInicio, fechaFinal, idAgencia,estadoLiquidacion);
	}
	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.service.business.UsuarioManager#buscarXId(java.lang.Long)
	 */
	@Override
	public Usuario buscarXId(Long id) throws Exception {
		// TODO Auto-generated method stub
		return getUsuarioDAO().buscarPorId(id);
	}
	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.service.business.UsuarioManager#buscarPorX(java.lang.String, java.lang.Object[], java.util.List, java.lang.String)
	 */
	@Override
	public List<Usuario> buscarPorX(String campo, Object[] criterios,List<String> criteriosOrdenar, String estadoRegistro)throws Exception {
		// TODO Auto-generated method stub
		return getUsuarioDAO().buscarPorX(campo, criterios, criteriosOrdenar, estadoRegistro);
	}
}
