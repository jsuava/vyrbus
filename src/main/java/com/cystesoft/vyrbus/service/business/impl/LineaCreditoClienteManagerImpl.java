package com.cystesoft.vyrbus.service.business.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

import org.springframework.transaction.annotation.Transactional;

import com.cystesoft.vyrbus.model.bean.Cliente;
import com.cystesoft.vyrbus.model.bean.DestinatariosEmails;
import com.cystesoft.vyrbus.model.bean.LineaCreditoCliente;
import com.cystesoft.vyrbus.model.bean.Usuario;
import com.cystesoft.vyrbus.model.bean.UsuarioAprobador;
import com.cystesoft.vyrbus.model.dao.LineaCreditoClienteDAO;
import com.cystesoft.vyrbus.service.business.LineaCreditoClienteManager;
import com.cystesoft.vyrbus.service.locator.ServiceLocator;
import com.cystesoft.vyrbus.service.util.Constantes;
import com.cystesoft.vyrbus.service.util.Sendmail;
import com.cystesoft.vyrbus.service.util.Util;

/**
 * 
 * @author JABANTO
 *
 */
public class LineaCreditoClienteManagerImpl implements LineaCreditoClienteManager {
	private LineaCreditoClienteDAO lineaCreditoClienteDAO;

	public LineaCreditoClienteDAO getLineaCreditoClienteDAO() {
		return lineaCreditoClienteDAO;
	}

	public void setLineaCreditoClienteDAO(LineaCreditoClienteDAO lineaCreditoClienteDAO) {
		this.lineaCreditoClienteDAO = lineaCreditoClienteDAO;
	}

	/*
	 * (non-Javadoc)
	 * @see com.tepsa.sisvyr.service.business.LineaCreditoClienteManager#buscarPorEstadoRegistro(java.lang.String, java.lang.String)
	 */
	@Override
	public ArrayList<LineaCreditoCliente> buscarPorEstadoRegistro(String estado, String criterioOrden) {
		return getLineaCreditoClienteDAO().buscarPorEstadoRegistro(estado, criterioOrden);
	}

	/*
	 * (non-Javadoc)
	 * @see com.tepsa.sisvyr.service.business.LineaCreditoClienteManager#buscarPorX(java.util.TreeMap, java.util.List)
	 */
	@Override
	public ArrayList<LineaCreditoCliente> buscarPorX(TreeMap<String, Object> criteriosBusqueda,List<String> criteriosOrdenar) {
		return getLineaCreditoClienteDAO().buscarPorX(criteriosBusqueda, criteriosOrdenar);
	}

	/*
	 * (non-Javadoc)
	 * @see com.tepsa.sisvyr.service.business.LineaCreditoClienteManager#buscarPorId(java.lang.Long)
	 */
	@Override
	public LineaCreditoCliente buscarPorId(Long id) {
		return getLineaCreditoClienteDAO().buscarPorId(id);
	}

	/*
	 * (non-Javadoc)
	 * @see com.tepsa.sisvyr.service.business.LineaCreditoClienteManager#guardar(com.tepsa.sisvyr.model.bean.LineaCreditoCliente)
	 */
	@Override
	@Transactional
	public void guardar(LineaCreditoCliente lineaCreditoCliente) {
		getLineaCreditoClienteDAO().guardar(lineaCreditoCliente);
	}

	/*
	 * (non-Javadoc)
	 * @see com.tepsa.sisvyr.service.business.LineaCreditoClienteManager#actualizar(com.tepsa.sisvyr.model.bean.LineaCreditoCliente)
	 */
	@Override
	@Transactional
	public void actualizar(LineaCreditoCliente lineaCreditoCliente) {
		getLineaCreditoClienteDAO().actualizar(lineaCreditoCliente);
	}

	/*
	 * (non-Javadoc)
	 * @see com.tepsa.sisvyr.service.business.LineaCreditoClienteManager#inactivar(java.lang.Long)
	 */
	@Override
	@Transactional
	public void inactivar(Long id) {
		getLineaCreditoClienteDAO().inactivar(id);
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.tepsa.sisvyr.service.business.SolicitudClienteCreditoManger#buscarSolicitudLineaCredito(java.lang.String, java.lang.String, java.lang.String, java.lang.Long, java.lang.Integer, java.lang.Boolean)
	 */
	@Override
	public List<LineaCreditoCliente> buscarSolicitudLineaCreditoN2(String fechaInicio, String fechaFin, String estadoSolicitud,Long idCliente, UsuarioAprobador usuarioAprobador, Boolean recu_Historia) {
		return getLineaCreditoClienteDAO().buscarSolicitudLineaCreditoN2(fechaInicio, fechaFin, estadoSolicitud, idCliente, usuarioAprobador, recu_Historia);
	}

	/*
	 * (non-Javadoc)
	 * @see com.tepsa.sisvyr.service.business.LineaCreditoClienteManager#buscarSolicitudLineaCreditoN3(java.lang.String, java.lang.String, java.lang.String, java.lang.Long, java.lang.Integer, java.lang.Boolean)
	 */
	@Override
	public List<LineaCreditoCliente> buscarSolicitudLineaCreditoN3(String fechaInicio, String fechaFin, String estadoSolicitud,Long idCliente, UsuarioAprobador usuarioAprobador, Boolean recu_Historia) {
		return getLineaCreditoClienteDAO().buscarSolicitudLineaCreditoN3(fechaInicio, fechaFin, estadoSolicitud, idCliente, usuarioAprobador, recu_Historia);
	}

	/*
	 * (non-Javadoc)
	 * @see com.tepsa.sisvyr.service.business.LineaCreditoClienteManager#saldo(java.lang.Double, java.lang.Long)
	 */
	@Override
	public Double saldo(Double montoAprobado, Long idCliente) {
		return getLineaCreditoClienteDAO().saldo(montoAprobado, idCliente);
	}

	/*
	 * (non-Javadoc)
	 * @see com.tepsa.sisvyr.service.business.LineaCreditoClienteManager#lineaCreditoCliente(java.lang.Long)
	 */
	@Override
	public LineaCreditoCliente lineaCreditoCliente(Long idCliente) {
		return getLineaCreditoClienteDAO().lineaCreditoCliente(idCliente);
	}

	/*
	 * (non-Javadoc)
	 * @see com.tepsa.sisvyr.service.business.LineaCreditoClienteManager#clientesCredito(java.lang.Long, java.lang.Integer, java.lang.String)
	 */
	@Override
	public List<LineaCreditoCliente> clientesCredito(Long idCliente,Integer idFuncionario, String tipoCliente) {
		return getLineaCreditoClienteDAO().clientesCredito(idCliente, idFuncionario,tipoCliente);
	}

	/*
	 * (non-Javadoc)
	 * @see com.tepsa.sisvyr.service.business.LineaCreditoClienteManager#validadSolicitudAprobadaN3(java.lang.Long)
	 */
	@Override
	public Boolean validadSolicitudAprobadaN3(Long idSolicitudCartera) {
		return getLineaCreditoClienteDAO().validadSolicitudAprobadaN3(idSolicitudCartera);
	}

	/*
	 * (non-Javadoc)
	 * @see com.tepsa.sisvyr.service.business.LineaCreditoClienteManager#validacionCreditoCliente(java.lang.Long)
	 */
	@Override
	public LineaCreditoCliente validacionCreditoCliente(Long idCliente) throws Exception {
		return getLineaCreditoClienteDAO().validacionCreditoCliente(idCliente);
	}

	/*
	 * (non-Javadoc)
	 * @see com.tepsa.sisvyr.service.business.LineaCreditoClienteManager#restarSaldo(java.lang.Double, java.lang.Double, java.lang.Long)
	 */
	@Override
	public void restarSaldo(Double SaldoActual, Double monto,Long idLineaCreditoCliente)throws Exception {
		getLineaCreditoClienteDAO().restarSaldo(SaldoActual, monto, idLineaCreditoCliente);
		
		/*Valida y envia alerta si el cliente esta sobregirado.*/
		validarSaldo(idLineaCreditoCliente, null);
	}

	/* 
	 * (non-Javadoc)
	 * @see com.tepsa.sisvyr.service.business.LineaCreditoClienteManager#saldobyReduccion(java.lang.Double, java.lang.String)
	 */
	@Override
	public Double saldobyReduccion(Double nuevaLineaCredito, String rucclienteCredito) {
		// TODO Auto-generated method stub
		return getLineaCreditoClienteDAO().saldobyReduccion(nuevaLineaCredito, rucclienteCredito);
	}

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.service.business.LineaCreditoClienteManager#actualizarSaldo(java.lang.Double, java.lang.String, com.tepsa.sisvyr.model.bean.Usuario, java.lang.String, java.lang.Boolean)
	 */
	@Override
	public void actualizarSaldo(Double motoActualizar,String rucClienteCredito, Usuario usuario, String ip, Boolean aFavor)throws Exception {
		// TODO Auto-generated method stub
		getLineaCreditoClienteDAO().actualizarSaldo(motoActualizar, rucClienteCredito, usuario, ip, aFavor);
		if(!(aFavor)){
			/*Valida y envia alerta si el cliente esta sobregirado.*/
			validarSaldo(null, rucClienteCredito);
		}
	}
	
	/**
	 * Valida y envia alerta si el cliente esta sobregirado.
	 * @param idLineaCreditoCliente	: Identificador de la Linea de Credito
	 * @param ruc	: Numero de ruc del cliente.
	 * @throws Exception 
	 */
	private void validarSaldo(Long idLineaCreditoCliente, String ruc) throws Exception {
		LineaCreditoCliente lineaCreditoCliente =null;
		Cliente cliente=null;
		
		//Obtiene el registro de la linea de credito.
		if(idLineaCreditoCliente!=null){
			//x el identificador de la linea de credito
			lineaCreditoCliente=ServiceLocator.getLineaCreditoClienteManager().buscarPorId(idLineaCreditoCliente);
			cliente=lineaCreditoCliente.getCarteraCliente().getCliente();
		}else if (ruc!=null){
			//x numero de ruc del cliente
			TreeMap<String, Object>criteriosBusqueda=new TreeMap<String, Object>();
			criteriosBusqueda.put("numeroDocumento", ruc);
			List<Cliente> result= ServiceLocator.getClienteManager().buscarPorX(criteriosBusqueda, null);
			if(result.size()>0){
				cliente=result.get(0);
				lineaCreditoCliente=lineaCreditoCliente(cliente.getId());
			}
		}
		
		/*Realiza la validación para determinar el uso del sobregiro y enviar la alerta*/
		if(lineaCreditoCliente!=null && lineaCreditoCliente.getEsCanje()!=null && lineaCreditoCliente.getEsCanje().equals(Constantes.NO)){
			//Calcula el monto del sobregiro
			Double valorSobregiro=lineaCreditoCliente.getLineaCreditoAprobada()*(lineaCreditoCliente.getSobregiro()/100);
			Double montoSobregirado=valorSobregiro-lineaCreditoCliente.getSaldo(); 
			//Valida si el cliente esta utilizado el sobregiro
			if(montoSobregirado >=0){
				/*Busca funcionario*/
				String toFuncionario=null;
				String ccJefeVentas="jmuschi@tepsa.com.pe";
				String bccSistema="moscco@tepsa.com.pe,jabanto@tepsa.com.pe";
				Usuario funcionario=ServiceLocator.getUsuarioManager().buscarPorId(lineaCreditoCliente.getCarteraCliente().getUsuario().getId().longValue());
				if(funcionario.getPersonal()!=null && funcionario.getPersonal().getEmail()!=null)
					toFuncionario=funcionario.getPersonal().getEmail();
				
				/*SOLO DESCOMENTAR PARA REALIZAR PRUEBAS */
//				toFuncionario="jabanto@tepsa.com.pe";
//				ccJefeVentas="jabanto@tepsa.com.pe";
//				bccSistema="jabanto@tepsa.com.pe";
								
				String lineaCredito=Util.toNumberFormat(lineaCreditoCliente.getLineaCreditoAprobada(),2);
				String sobregiro=Util.toNumberFormat(valorSobregiro,2);//+" ("+Util.toNumberFormat(lineaCreditoCliente.getSobregiro(),2)+" %)";
				String saldo=Util.toNumberFormat(lineaCreditoCliente.getSaldo(), 2);
								
				int base=12;
				//Envia E-Mail
				String mensaje="CLIENTE       : "+cliente.toString()+"\n";
				mensaje+="LINEA CREDITO :"+tabular(base-lineaCredito.length())+lineaCredito+"\n";
				mensaje+="SOBREGIRO     :"+tabular(base-sobregiro.length())+sobregiro+" ("+Util.toNumberFormat(lineaCreditoCliente.getSobregiro(),2)+" %) \n";
				mensaje+="SALDO         :"+tabular(base-saldo.length())+saldo+"\n\n";
				mensaje+="MONTO SOBREGIRADO   : "+Util.toNumberFormat(montoSobregirado, 2)+"\n";

				if(toFuncionario==null){
					toFuncionario="moscco@tepsa.com.pe";
					mensaje+="No se ha encontrado el E-Mail del Funcionario al cual esta asigndo este Cliente, por lo que no se ha podido notificar.";
				}
				
				mensaje+="\n\n\n";
				mensaje+="NOTA: [Este buzon es de envio automático, por favor no responda.]";
				DestinatariosEmails window = new DestinatariosEmails();
//				DestinatariosEmails window=ServiceLocator.getDestinatariosEmailsManager().buscarByObjeto("");
				window.setEmails("TO:"+toFuncionario+";CC:"+ccJefeVentas+";BCC:"+bccSistema);
				Sendmail.enviaEmail(mensaje,"Sobregiro de Clientes", window);
			}
		}
		
	}
	
	/**
	 * Devuelve una cadena de espacios en blanco.
	 * @param espacios	: Numero de espacios en blanco.
	 * @return String
	 */
	private final String tabular(int espacios){
		String cadena = "";
		for(int i=0; i<espacios; i++){
			cadena = cadena + " ";
		}
		return cadena;
	}
}
