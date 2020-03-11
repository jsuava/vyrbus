package com.cystesoft.vyrbus.service.business.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

import org.springframework.transaction.annotation.Transactional;

import com.cystesoft.vyrbus.model.bean.DestinatariosEmails;
import com.cystesoft.vyrbus.model.bean.SolicitudCartera;
import com.cystesoft.vyrbus.model.bean.SolicitudClienteCredito;
import com.cystesoft.vyrbus.model.bean.TipoCobranza;
import com.cystesoft.vyrbus.model.bean.Usuario;
import com.cystesoft.vyrbus.model.bean.UsuarioAprobador;
import com.cystesoft.vyrbus.model.dao.SolicitudClienteCreditoDAO;
import com.cystesoft.vyrbus.service.business.SolicitudClienteCreditoManager;
import com.cystesoft.vyrbus.service.locator.ServiceLocator;
import com.cystesoft.vyrbus.service.util.Constantes;
import com.cystesoft.vyrbus.service.util.Sendmail;

/**
 * 
 * @author JABANTO
 *
 */
public class SolicitudClienteCreditoMangerImpl implements SolicitudClienteCreditoManager {
	
	public SolicitudClienteCreditoDAO solicitudClienteCreditoDAO;
	
	public void setSolicitudClienteCreditoDAO(SolicitudClienteCreditoDAO solicitudClienteCreditoDAO){
		this.solicitudClienteCreditoDAO=solicitudClienteCreditoDAO;
	}
	
	public SolicitudClienteCreditoDAO getSolicitudClienteCreditoDAO(){
		return this.solicitudClienteCreditoDAO;
	}

	/*
	 * (non-Javadoc)
	 * @see com.tepsa.sisvyr.service.business.SolicitudClienteCreditoManger#buscarPorX(java.util.TreeMap, java.util.List)
	 */
	@Override
	public ArrayList<SolicitudClienteCredito> buscarPorX(TreeMap<String, Object> criteriosBusqueda,List<String> criteriosOrdenar) {
		return getSolicitudClienteCreditoDAO().buscarPorX(criteriosBusqueda, criteriosOrdenar);
	}

	/*
	 * (non-Javadoc)
	 * @see com.tepsa.sisvyr.service.business.SolicitudClienteCreditoManger#buscarPorId(java.lang.Long)
	 */
	@Override
	public SolicitudClienteCredito buscarPorId(Long id) {
		return getSolicitudClienteCreditoDAO().buscarPorId(id);
	}

	/*
	 * (non-Javadoc)
	 * @see com.tepsa.sisvyr.service.business.SolicitudClienteCreditoManger#guardar(com.tepsa.sisvyr.model.bean.SolicitudClienteCredito)
	 */
	@Override
	@Transactional
	public void guardar(SolicitudClienteCredito solicitudClienteCredito)throws Exception {
		//Guarda
		getSolicitudClienteCreditoDAO().guardar(solicitudClienteCredito);
		
		
		//Solamente envia las alertas si es que el nievel de aprobacion es diferente a tres.
		if(solicitudClienteCredito.getNivelAprobacion().intValue()!=Constantes.NIVEL_TRES){
			//True si la solicitud es de credito, false si es al contado
			Boolean esSolicitudCredito=solicitudClienteCredito.getLineaCreditoSolicitada()>0;
			
			SolicitudCartera solicitudCartera=ServiceLocator.getSolicitudCarteraManager().buscarPorId(solicitudClienteCredito.getSolicitudCartera().getId());
			Usuario funcionario=ServiceLocator.getUsuarioManager().buscarXId(solicitudCartera.getUsuario().getId().longValue());
			//Busca el usuario aprobador por su id.
			if(solicitudClienteCredito.getUsuarioAprobador()!=null){
				UsuarioAprobador  usuarioAprobador=ServiceLocator.getUsuarioAprobadorManager().buscarPorId(solicitudClienteCredito.getUsuarioAprobador().getId().longValue());
				solicitudClienteCredito.setUsuarioAprobador(usuarioAprobador);
				
			}
			
			String title=""; 
			String toAddress=""; 
			String ccAddress=""; 
			String bccAddress=""; 
			String mensaje = "";
			String emailFuncionario=funcionario.getPersonal()==null?funcionario.getEmailFuncionario():funcionario.getPersonal().getEmail();
			
			mensaje="Funcionario     : "+funcionario.toString()+"\n";
			mensaje+="Fecha Solicitud : "+Constantes.FORMAT_LONG.format(solicitudCartera.getFechaSolicitud())+"\n";
			mensaje+="RUC Cliente     : "+solicitudCartera.getCliente().getNumeroDocumento()+"\n";
			mensaje+="Razón Social    : "+solicitudCartera.getCliente().getRazonSocial()+"\n";
			/*Valida si la solicitud es de tipo crédito o contado*/
			if(solicitudClienteCredito.getLineaCreditoSolicitada()==0 && solicitudClienteCredito.getTipoCobranza()==null){//Es contado
				mensaje+="Tipo Cliente : Contado. \n";
				mensaje+="Desct. temporada baja: "+solicitudCartera.getDescuentoBaja()+" % \n";
				mensaje+="Desct. temporada alta: "+solicitudCartera.getDescuentoAlta()+" % \n";
			}else{//Es Crédito
				TipoCobranza tipoCobranza=ServiceLocator.getTipoCobranzaManager().buscarPorId(solicitudClienteCredito.getTipoCobranza().getId().longValue());
				mensaje+="Tipo de Cliente : Crédito. \n";
				mensaje+="Tipo de Cobranza: "+tipoCobranza.getDenominacion()+"\n";
				mensaje+="Linea Crédito Solicitada: "+solicitudClienteCredito.getLineaCreditoSolicitada()+"\n";
				mensaje+="Sobregiro\t\t: "+solicitudClienteCredito.getSobregiro()+" % \n";
				if(solicitudCartera.getDescuentoBaja()>0)
					mensaje+="Desct. temporada baja: "+solicitudCartera.getDescuentoBaja()+" % \n";
				if(solicitudCartera.getDescuentoAlta()>0)
					mensaje+="Desct. temporada alta: "+solicitudCartera.getDescuentoAlta()+" % \n";
			}
			
			//Cuando se realiza la solicitud por parte del funcionario.
			if(solicitudClienteCredito.getNivelAprobacion()==0 || solicitudClienteCredito.getNivelAprobacion()==null){
				if(solicitudClienteCredito.getEsAmpliacion().equals(Constantes.SI)){//Valida si es un Ampliación
					if(solicitudClienteCredito.getEsCanje()!=null && solicitudClienteCredito.getEsCanje().equals(Constantes.SI))
						title="Extensión Canje publicitario - ";
					else
						title="Solicitud Ampliación Crédito - ";
				}else if(solicitudClienteCredito.getEsCanje().equals(Constantes.SI))//Valida si es un Canje
					title="Solicitud Canje Publicitario - ";
				else
					title="Solicitud Cartera - ";
				
				//Obtiene los usuarios aprobadores de nivel uno para realizar el envio del email.
				List<UsuarioAprobador> listUsuApro= ServiceLocator.getUsuarioAprobadorManager().buscarXNivel(Constantes.NIVEL_UNO);	
				for(UsuarioAprobador aprobador: listUsuApro){
					if(aprobador.getUsuario().getPersonal()!=null && aprobador.getUsuario().getPersonal().getEmail()!=null){
						if(toAddress.length()==0)
							toAddress=aprobador.getUsuario().getPersonal().getEmail();
						else
							toAddress+=","+aprobador.getUsuario().getPersonal().getEmail();
					}
				}
				/*Envia copia al funcionario*/
				ccAddress=emailFuncionario;
			}else{
				/*En las aprovaciones de los diferentes niveles*/
				
				//configura el titulo del mensaje
				if(solicitudClienteCredito.getEsAmpliacion().equals(Constantes.SI)){
					if(solicitudClienteCredito.getEsCanje()!=null && solicitudClienteCredito.getEsCanje().equals(Constantes.SI))
						title="Extensión Canje publicitario ";
					else
						title="Ampliación Linea de Crédito ";
				}else if(solicitudClienteCredito.getEsCanje().equals(Constantes.SI)){
					title="Canje Publicitario ";
				}else{
					switch (solicitudClienteCredito.getNivelAprobacion()) {
					case Constantes.NIVEL_UNO:
						title="Solicitud Cartera ";
						break;
					case Constantes.NIVEL_DOS:
						title="Solicitud Crédito ";
						break;
					case Constantes.NIVEL_TRES:
						title="Solicitud Crédito ";
						break;
					default:
						break;
					}			
				}
					
				//valida el envio de email segun el estado de la solicitud
				if(!(solicitudClienteCredito.getEstadoSolicitud().equals(Constantes.ESTADOSOL_ACTIVA))){//Desaprobacion
					title+="Desaprobado - ";
					toAddress=emailFuncionario;//envia mail al funcionario.
				}else{
					title+="Aprobado - ";
//					if(solicitudClienteCredito.getNivelAprobacion()==Constantes.NIVEL_TRES)
					if(solicitudClienteCredito.getNivelAprobacion()==Constantes.NIVEL_DOS)
						toAddress=emailFuncionario;
					else{
						if(esSolicitudCredito)//Si es credito
							ccAddress=emailFuncionario;
						else//Si es contado
							toAddress=emailFuncionario;
					}
				}
							
//				if(solicitudClienteCredito.getNivelAprobacion()!=Constantes.NIVEL_TRES){
				if(solicitudClienteCredito.getNivelAprobacion()!=Constantes.NIVEL_DOS){
					//Valida si es el Cliente es de tipo crédito y la solicitud es aprobada.
					if(solicitudClienteCredito.getLineaCreditoSolicitada()>0 && solicitudClienteCredito.getTipoCobranza()!=null && (solicitudClienteCredito.getEstadoSolicitud().equals(Constantes.ESTADOSOL_ACTIVA)) ){
						//Recupera el usuario aprobador del siguiente nivel
						List<UsuarioAprobador> listUsuApro= ServiceLocator.getUsuarioAprobadorManager().buscarXNivel(solicitudClienteCredito.getNivelAprobacion()+1);		
						for(UsuarioAprobador aprobador: listUsuApro){
							if(aprobador.getUsuario().getPersonal()!=null && aprobador.getUsuario().getPersonal().getEmail()!=null){
								if(toAddress.length()==0) toAddress=aprobador.getUsuario().getPersonal().getEmail();
								else toAddress+=","+aprobador.getUsuario().getPersonal().getEmail();
							}
						}
					}
					
				}else{
					/*Busca usuario(s) probador(es) de nivel 1 para enviar correo de confirmacion de la aprobacion final por parte de la gerencia comercial*/
					List<UsuarioAprobador> listUsuApro= ServiceLocator.getUsuarioAprobadorManager().buscarXNivel(Constantes.NIVEL_UNO);		
					for(UsuarioAprobador aprobador: listUsuApro){
						if(aprobador.getUsuario().getPersonal()!=null && aprobador.getUsuario().getPersonal().getEmail()!=null){
							if(toAddress.length()==0) toAddress=aprobador.getUsuario().getPersonal().getEmail();
							else toAddress+=","+aprobador.getUsuario().getPersonal().getEmail();
						}
					}
//					// para el funcionario
//					if(toAddress.length()==0) toAddress+=emailFuncionario;
//					else toAddress+=","+emailFuncionario;
				}
							
				if(ccAddress.length()==0)//Copia el correo al usuario aprobador
					ccAddress=solicitudClienteCredito.getUsuarioAprobador().getUsuario().getPersonal().getEmail();
				else 
					ccAddress+=","+solicitudClienteCredito.getUsuarioAprobador().getUsuario().getPersonal().getEmail();
			}
			
			//Datos de la Aprobación/desaprobacion.
			if(solicitudClienteCredito.getNivelAprobacion()!=null && solicitudClienteCredito.getNivelAprobacion()>0){
				String mensajeApro="\n\n";
				String usuApro="";
				if(solicitudClienteCredito.getEstadoSolicitud().equals(Constantes.ESTADOSOL_ACTIVA)){
					if(solicitudClienteCredito.getFechaAprobacion()!=null)
						mensajeApro+="Fecha de Aprobación   : "+Constantes.FORMAT_LONG.format(solicitudClienteCredito.getFechaAprobacion())+"\n";
					if(solicitudClienteCredito.getLineaCreditoSolicitada()>0 && solicitudClienteCredito.getTipoCobranza()!=null)//Valida si la solicitud es de crédito
						mensajeApro+="Linea Crédito Aprobada: "+solicitudClienteCredito.getLineaCreditoAprobada()+"\n";
					if(solicitudClienteCredito.getNivelAprobacion()!=null && solicitudClienteCredito.getNivelAprobacion()>0)
						usuApro="Usuario Aprobación    : "+solicitudClienteCredito.getUsuarioAprobador().getUsuario().toString();
				}else{
					mensajeApro+="Solicitud Desaprobada:\n";
					mensajeApro+="Motivo  : "+solicitudClienteCredito.getObservaciones()+"\n";
					mensajeApro+="Fecha   : "+Constantes.FORMAT_LONG.format(solicitudClienteCredito.getFechaAnulacion())+"\n";
					usuApro="Usuario : "+solicitudClienteCredito.getUsuarioAprobador().getUsuario().toString();
				}
				/*Espesifica el area que realiza la aprobación*/
				switch (solicitudClienteCredito.getNivelAprobacion()) {
				case Constantes.NIVEL_UNO:
					usuApro+=" - Jefe de Ventas \n";
					break;
				case Constantes.NIVEL_DOS:
					usuApro+=" - Finanzas \n";
					break;
				case Constantes.NIVEL_TRES:
					usuApro+=" - Gerencia comercial \n";
					break;
					
				default:
					break;
				}				
				mensajeApro+=usuApro;
				if(solicitudClienteCredito.getObservaciones()!=null  &&  solicitudClienteCredito.getEstadoSolicitud().equals(Constantes.ESTADOSOL_ACTIVA) ){
					if(!(solicitudClienteCredito.getObservaciones().trim().isEmpty()))
						mensajeApro+="Observaciones         : "+solicitudClienteCredito.getObservaciones()+"\n";
				}
					
				mensaje+=mensajeApro;
			}
			title+=solicitudClienteCredito.getSolicitudCartera().getCliente().getRazonSocial();//Agrega al titulo la rozón social
					
			//SOLO COMENTAR PARA PRUEBAS 
			bccAddress="sistemas@tepsa.com.pe";
			
			/*SOLO DESCOMENTAR PARA REALIZAR PRUEBAS */
//			toAddress="jabanto@tepsa.com.pe";
//			ccAddress="jabanto@tepsa.com.pe";
//			bccAddress="jabanto@tepsa.com.pe";
			
			if(toAddress.isEmpty())
				toAddress=ccAddress;
			if(ccAddress.isEmpty())
				ccAddress=toAddress;
		
			//Envia E-Mail
			mensaje+="\n\n";
			mensaje+="NOTA: [Este buzon es de envio automático, por favor no responda.]";
			DestinatariosEmails window = new DestinatariosEmails();
			window.setEmails("TO:"+toAddress+";CC:"+ccAddress+";BCC:"+bccAddress);
			Sendmail.enviaEmail(mensaje,title, window);
		}
	}
	/*
	 * (non-Javadoc)
	 * @see com.tepsa.sisvyr.service.business.SolicitudClienteCreditoManger#actalizar(com.tepsa.sisvyr.model.bean.SolicitudClienteCredito)
	 */
	@Override
	@Transactional
	public void actualizar(SolicitudClienteCredito solicitudClienteCredito)throws Exception {
		getSolicitudClienteCreditoDAO().actualizar(solicitudClienteCredito);
	}

	/*
	 * (non-Javadoc)
	 * @see com.tepsa.sisvyr.service.business.SolicitudClienteCreditoManger#aprobarSolicitud(java.lang.Long, com.tepsa.sisvyr.model.bean.UsuarioAprobador)
	 */
	@Override
	public void aprobarSolicitud(Long idSolicitudClienteCredito,UsuarioAprobador usuarioAprobador) throws Exception {
		getSolicitudClienteCreditoDAO().aprobarSolicitud(idSolicitudClienteCredito, usuarioAprobador);
	}

	/*
	 * (non-Javadoc)
	 * @see com.tepsa.sisvyr.service.business.SolicitudClienteCreditoManger#anulaSolicitud(java.lang.Integer, com.tepsa.sisvyr.model.bean.UsuarioAprobador)
	 */
	@Override
	public void anulaSolicitud(Integer idSolicitudClienteCredito,UsuarioAprobador usuarioAprobador) throws Exception {
		getSolicitudClienteCreditoDAO().anulaSolicitud(idSolicitudClienteCredito, usuarioAprobador);		
	}

	/*
	 * (non-Javadoc)
	 * @see com.tepsa.sisvyr.service.business.SolicitudClienteCreditoManger#inactivar(java.lang.Long)
	 */
	@Override
	@Transactional
	public void inactivar(Long id) {
		getSolicitudClienteCreditoDAO().inactivar(id);
	}

	/*
	 * (non-Javadoc)
	 * @see com.tepsa.sisvyr.service.business.SolicitudClienteCreditoManger#validadSolicitudPendiente(java.lang.Long)
	 */
	@Override
	public SolicitudClienteCredito validadSolicitudPendiente(Long idCliente)throws Exception {
		return getSolicitudClienteCreditoDAO().validadSolicitudPendiente(idCliente);
	}

	/*
	 * (non-Javadoc)
	 * @see com.tepsa.sisvyr.service.business.SolicitudClienteCreditoManger#buscarHistorialSolicitudesCarteraCredito(java.lang.String, java.lang.String, java.lang.Integer, java.lang.Long)
	 */
	@Override
	public List<SolicitudClienteCredito> buscarHistorialSolicitudesCarteraCredito(String fechaInicial, String fechaFinal, Integer idFuncionario,Long idCliente) throws Exception {
		return getSolicitudClienteCreditoDAO().buscarHistorialSolicitudesCarteraCredito(fechaInicial, fechaFinal, idFuncionario, idCliente);
	}

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.service.business.SolicitudClienteCreditoManger#buscarPendientes()
	 */
	@Override
	public List<SolicitudClienteCredito> buscarPendientesN1() {
		// TODO Auto-generated method stub
		return getSolicitudClienteCreditoDAO().buscarPendientesN1();
	}

}
