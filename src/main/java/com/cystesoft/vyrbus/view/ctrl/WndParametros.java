/**
 * Proyecto		: SISVYR
 * Sistema		: Sistema de Ventas y Reservas
 * Descripción	: 
 * Autor		: José 
 * Fecha		: 15/05/2013
 */
package com.cystesoft.vyrbus.view.ctrl;


import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zul.Intbox;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Messagebox;

import com.cystesoft.vyrbus.model.bean.Parametros;
import com.cystesoft.vyrbus.model.bean.UsuarioAprobador;
import com.cystesoft.vyrbus.service.exceptions.ParametrosException;
import com.cystesoft.vyrbus.service.locator.ServiceLocator;
import com.cystesoft.vyrbus.service.util.Constantes;
import com.cystesoft.vyrbus.service.util.Messages;
import com.cystesoft.vyrbus.service.util.MyTime;
import com.cystesoft.vyrbus.service.util.UtilData;
import com.cystesoft.vyrbus.view.ui.DlgMessage;
import com.cystesoft.vyrbus.view.ui.WndBase;

/**
 * @author JABANTO
 *
 */
public class WndParametros extends WndBase {
	private static final long serialVersionUID = 1L;
	
	
	private Intbox itTiempoExpiraReserva;
	private Intbox itTiempoAcumulaViajesParaPaxFree;
	private Intbox itViajesRequeridosIngresarPaxFre;
	private Intbox itTiempoBoletoValido;
	private Intbox itPuntosAcumulaPorVentaPaxFre;
	private Intbox itMontocobrarPostergacion;
	private Intbox itMontoCobrarReimpresion;
	private Intbox itMaximoPostergacionesBoleto;
	private Intbox itTiempoPuedeRealizarPostergacion;
	private Intbox itTiempoPaxCanjeBoletoCumpleanios;
	private Intbox itTiempoCaducanPuntosOptenidosPaxfree;
	private Intbox itAlertaEnvioEspecieValorada;
	private Intbox itAlertaSolicitaManifiestoPax;
	private Intbox itMaximoAsientosSeleccionados;
	private Intbox itMaximoDuplicidadComprobante;
	private Intbox itMontoPenalidadCambioNombre;
	private Intbox itMaximoBloqueoasiento;
	private Intbox itMaximoReservaAntesServicio;
	private Intbox iCantidadViajesParaCortesia;
	
	private Listbox lbxUsuarioGerenciaComercialAprobador;
	private Listbox lbxUsuarioComercialAprobador;
	private Listbox lbxUsuarioFinazasAprobador;
	private Listbox lbxUsuarioGerenciaComercial;

	Parametros parametros=null;
	
	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.view.ui.WndBase#initComponents()
	 */
	@Override
	public void initComponents() {
		itTiempoExpiraReserva=(Intbox)this.getFellow("itTiempoExpiraReserva");
		itTiempoAcumulaViajesParaPaxFree=(Intbox)this.getFellow("itTiempoAcumulaViajesParaPaxFree");
		itViajesRequeridosIngresarPaxFre=(Intbox)this.getFellow("itViajesRequeridosIngresarPaxFre");
		itTiempoBoletoValido=(Intbox)this.getFellow("itTiempoBoletoValido");
		itPuntosAcumulaPorVentaPaxFre=(Intbox)this.getFellow("itPuntosAcumulaPorVentaPaxFre");
		itMontocobrarPostergacion=(Intbox)this.getFellow("itMontocobrarPostergacion");
		itMontoCobrarReimpresion=(Intbox)this.getFellow("itMontoCobrarReimpresion");
		itMaximoPostergacionesBoleto=(Intbox)this.getFellow("itMaximoPostergacionesBoleto");
		itTiempoPuedeRealizarPostergacion=(Intbox)this.getFellow("itTiempoPuedeRealizarPostergacion");
		itTiempoPaxCanjeBoletoCumpleanios=(Intbox)this.getFellow("itTiempoPaxCanjeBoletoCumpleanios");
		itTiempoCaducanPuntosOptenidosPaxfree=(Intbox)this.getFellow("itTiempoCaducanPuntosOptenidosPaxfree");
		itAlertaEnvioEspecieValorada=(Intbox)this.getFellow("itAlertaEnvioEspecieValorada");
		itAlertaSolicitaManifiestoPax=(Intbox)this.getFellow("itAlertaSolicitaManifiestoPax");
		itMaximoAsientosSeleccionados=(Intbox)this.getFellow("itMaximoAsientosSeleccionados");
		
		itMaximoDuplicidadComprobante=(Intbox)this.getFellow("itMaximoDuplicidadComprobante");
		itMontoPenalidadCambioNombre=(Intbox)this.getFellow("itMontoPenalidadCambioNombre");
		itMaximoBloqueoasiento=(Intbox)this.getFellow("itMaximoBloqueoasiento");
		itMaximoReservaAntesServicio=(Intbox)this.getFellow("itMaximoReservaAntesServicio");
		iCantidadViajesParaCortesia=(Intbox)this.getFellow("iCantidadViajesParaCortesia");
		
		lbxUsuarioGerenciaComercialAprobador=(Listbox)this.getFellow("lbxUsuarioGerenciaComercialAprobador");
		lbxUsuarioComercialAprobador=(Listbox)this.getFellow("lbxUsuarioComercialAprobador");
		lbxUsuarioFinazasAprobador=(Listbox)this.getFellow("lbxUsuarioFinazasAprobador");
		lbxUsuarioGerenciaComercial=(Listbox)this.getFellow("lbxUsuarioGerenciaComercial");
	}
	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.view.ui.WndBase#onCreate()
	 */
	@Override
	public void onCreate() throws Exception {
		parametros= ServiceLocator.getParametrosManager().buscarPorEstadoRegistro(Constantes.VALUE_ACTIVO);
//		parametros=(Parametros) getDesktop().getSession().getAttribute(Constantes.ATRIBUTO_PARAMETROS);
						
		itTiempoExpiraReserva.setValue(parametros.getTiempoExpiraReserva());
		itTiempoAcumulaViajesParaPaxFree.setValue(parametros.getTiempoProgramaPasajeroFrecuente());
		itViajesRequeridosIngresarPaxFre.setValue(parametros.getViajesRequeridosPasajeroFrecuente());
		itTiempoBoletoValido.setValue(parametros.getTiempoCaducidadBoleto());
		itPuntosAcumulaPorVentaPaxFre.setValue(parametros.getPuntosAcumuladosPasajeroFrecuente());
		itMontocobrarPostergacion.setValue(parametros.getPenalidadPostergacion());
		itMontoCobrarReimpresion.setValue(parametros.getPenalidadReimpresion());
		itMaximoPostergacionesBoleto.setValue(parametros.getMaximoPostergaciones());
		itTiempoPuedeRealizarPostergacion.setValue(parametros.getTiempoPostergacion());
		itTiempoPaxCanjeBoletoCumpleanios.setValue(parametros.getRangoCanjeCumpleanios());
		itTiempoCaducanPuntosOptenidosPaxfree.setValue(parametros.getTiempoCaducidadPuntos());
		itAlertaEnvioEspecieValorada.setValue(parametros.getAlertarEnvioEspecieValorda());
		itAlertaSolicitaManifiestoPax.setValue(parametros.getAlertarEnvioManifiestoPasajeros());
		itMaximoAsientosSeleccionados.setValue(parametros.getMaximoAsientosSeleccionados());
		
		itMaximoDuplicidadComprobante.setValue(parametros.getTiempoMaximoDuplicarBoleto());
		itMontoPenalidadCambioNombre.setValue(parametros.getPenalidadCambioNombre());
		itMaximoBloqueoasiento.setValue(parametros.getTiempoExpiraBloqueo());
		itMaximoReservaAntesServicio.setValue(parametros.getTiempoMaximoPermiteReserva());
		iCantidadViajesParaCortesia.setValue(parametros.getViajesAcumuladosPasajero());
		
//		/*carga usuarios aprobadores*/
//		cargarListaUsuarios(lbxUsuarioGerenciaComercialAprobador);//Generancia Comercial Aprobador (Genencia comercial)
//		cargarListaUsuarios(lbxUsuarioComercialAprobador);//Asigancion de cartera (Jefe de Ventas)
//		cargarListaUsuarios(lbxUsuarioFinazasAprobador);//Aprobacion de Credito
//		cargarListaUsuarios(lbxUsuarioGerenciaComercial);//Usuario Genencia Comercial
//		
//		/*Realiza el select de los usuarios aprobadores*/
//		selectItemsUsuarios(lbxUsuarioGerenciaComercialAprobador, parametros.getUsuarioGerenciaComercialAprobador());
//		selectItemsUsuarios(lbxUsuarioComercialAprobador, parametros.getUsuarioComercialAprobador());
//		selectItemsUsuarios(lbxUsuarioFinazasAprobador, parametros.getUsuarioFinanzasAprobador());
//		selectItemsUsuarios(lbxUsuarioGerenciaComercial, parametros.getUsuarioGerenciaComercial());
	}
	
	
	public void GuardarParametros()throws Exception{
		try{
			if(itTiempoExpiraReserva.getText().trim().isEmpty())
				throw new ParametrosException(ParametrosException.TIEMPO_EXPIRA_RESERVA_NULL);
			else if(itTiempoAcumulaViajesParaPaxFree.getText().trim().isEmpty())
				throw new ParametrosException(ParametrosException.TIEMPO_ACUMULA_VIAJES_PARA_PAXFRE_NULL);
			else if(itViajesRequeridosIngresarPaxFre.getText().trim().isEmpty())
				throw new ParametrosException(ParametrosException.VIAJES_REQUERIDOS_INGRESA_PAXFRE_NULL);
			else if(itTiempoBoletoValido.getText().trim().isEmpty())
				throw new ParametrosException(ParametrosException.TIEMPO_BOLETO_VALIDO_NULL);
			else if(itPuntosAcumulaPorVentaPaxFre.getText().trim().isEmpty())
				throw new ParametrosException(ParametrosException.PUNTOS_ACUMULA_POR_VENTAPAXFRE_NULL);
			else if(itMontocobrarPostergacion.getText().trim().isEmpty())
				throw new ParametrosException(ParametrosException.MONTO_COBRAR_POSTERGACION_NULL);
			else if(itMontoCobrarReimpresion.getText().trim().isEmpty())
				throw new ParametrosException(ParametrosException.MONTO_COBRAR_REIMPRESION_NULL);
			else if(itMaximoPostergacionesBoleto.getText().trim().isEmpty())
				throw new ParametrosException(ParametrosException.MAXIMO_POSTERGACIONES_BOLETO_NULL);
			else if(itTiempoPuedeRealizarPostergacion.getText().trim().isEmpty())
				throw new ParametrosException(ParametrosException.TIEMPO_PUEDE_REALIZAR_POSTERGACION_NULL);
			else if(itTiempoPaxCanjeBoletoCumpleanios.getText().trim().isEmpty())
				throw new ParametrosException(ParametrosException.TIEMPO_PAX_CANJE_BOLETO_CUMPLEANIOS_NULL);
			else if(itTiempoCaducanPuntosOptenidosPaxfree.getText().trim().isEmpty())
				throw new ParametrosException(ParametrosException.TIEMPO_CADUCAN_PUNTOS_OPTENIDOS_PAXFREE_NULL);
			else if(itAlertaEnvioEspecieValorada.getText().trim().isEmpty())
				throw new ParametrosException(ParametrosException.ALERTA_ENVIO_ESPECIE_VALORADA_NULL);
			else if(itAlertaSolicitaManifiestoPax.getText().trim().isEmpty())
				throw new ParametrosException(ParametrosException.ALERTA_SOLICITA_MANIFIESTOPAX_NULL);
			else if(itMaximoAsientosSeleccionados.getText().trim().isEmpty())
				throw new ParametrosException(ParametrosException.MAXIMO_ASIENTOS_SELECCIONADOS_NULL);
			
			/*Usuario Gerencia Comercial Aprobador*/
			final String iDsUsuarioGerenciaComercialAprobador=getIDsUsuario(lbxUsuarioGerenciaComercialAprobador);
			final String iDsUsuarioComercialAprobador=getIDsUsuario(lbxUsuarioComercialAprobador);
			final String iDsUsuarioFinanzasAprobador=getIDsUsuario(lbxUsuarioFinazasAprobador);
			final String iDsUsuarioGerenciaComercial=getIDsUsuario(lbxUsuarioGerenciaComercial);
			
//			if(iDsUsuarioGerenciaComercialAprobador.length()==0)
//				throw new ParametrosException(ParametrosException.USUARIO_GENECIA_APROBADOR_NULL);
//			else if (iDsUsuarioComercialAprobador.length()==0)
//				throw new ParametrosException(ParametrosException.USUARIO_COMERCIAL_APROBADOR_NULL);
//			else if (iDsUsuarioFinanzasAprobador.length()==0)
//				throw new ParametrosException(ParametrosException.USUARIO_FINANZAS_APROBADOR_NULL);
//			else if (iDsUsuarioGerenciaComercial.length()==0)
//				throw new ParametrosException(ParametrosException.USUARIO_GENRENCIA_COMERCIAL_NULL);
				
			Messagebox.show(Messages.getString("wndParametros.question.confirmaUpdate"), DlgMessage.NOMBREAPLICACION, DlgMessage.BTN_YESNO, Messagebox.QUESTION, new EventListener<Event>() {
				@Override
				public void onEvent(Event e) throws Exception {
					if(e.getName().equals("onYes")){
						/*Inactiva el registro actual*/
						parametros.setEstadoRegistro(Constantes.VALUE_INACTIVO);
						UtilData.auditarRegistro(parametros, true, getUsuario(), Executions.getCurrent());
						ServiceLocator.getParametrosManager().actualizar(parametros);
						
						/*Inserta un nuevo registro*/
						parametros= new Parametros();
						parametros.setTiempoExpiraReserva(itTiempoExpiraReserva.getValue());
						parametros.setTiempoProgramaPasajeroFrecuente(itTiempoAcumulaViajesParaPaxFree.getValue());
						parametros.setViajesRequeridosPasajeroFrecuente(itViajesRequeridosIngresarPaxFre.getValue());
						parametros.setTiempoCaducidadBoleto(itTiempoBoletoValido.getValue());
						parametros.setPuntosAcumuladosPasajeroFrecuente(itPuntosAcumulaPorVentaPaxFre.getValue());
						parametros.setPenalidadPostergacion(itMontocobrarPostergacion.getValue());
						parametros.setPenalidadReimpresion(itMontoCobrarReimpresion.getValue());
						parametros.setMaximoPostergaciones(itMaximoPostergacionesBoleto.getValue());
						parametros.setTiempoPostergacion(itTiempoPuedeRealizarPostergacion.getValue());
						parametros.setRangoCanjeCumpleanios(itTiempoPaxCanjeBoletoCumpleanios.getValue());
						parametros.setTiempoCaducidadPuntos(itTiempoCaducanPuntosOptenidosPaxfree.getValue());
						parametros.setAlertarEnvioEspecieValorda(itAlertaEnvioEspecieValorada.getValue());
						parametros.setAlertarEnvioManifiestoPasajeros(itAlertaSolicitaManifiestoPax.getValue());
//						parametros.setUsuarioGerenciaComercialAprobador(iDsUsuarioGerenciaComercialAprobador);
//						parametros.setUsuarioComercialAprobador(iDsUsuarioComercialAprobador);
//						parametros.setUsuarioFinanzasAprobador(iDsUsuarioFinanzasAprobador);
//						parametros.setUsuarioGerenciaComercial(iDsUsuarioGerenciaComercial);
						parametros.setMaximoAsientosSeleccionados(itMaximoAsientosSeleccionados.getValue());
						parametros.setEstadoRegistro(Constantes.VALUE_ACTIVO);
						UtilData.auditarRegistro(parametros, getUsuario(), Executions.getCurrent());
						ServiceLocator.getParametrosManager().guardar(parametros);
						parametros.setFechaInsercion(Constantes.FORMAT_DATE_TIME_24H.parse(new MyTime().dateServer()));
					
						/*actualiza el atributo PARAMETROS*/
//						getDesktop().getSession().removeAttribute(Constantes.ATRIBUTO_PARAMETROS);
//						getDesktop().getSession().setAttribute(Constantes.ATRIBUTO_PARAMETROS, parametros);
					}
				}
			});
			
		}catch (ParametrosException p){
			if(p.getTipo().intValue()==ParametrosException.TIEMPO_EXPIRA_RESERVA_NULL){
				DlgMessage.information(Messages.getString("wndParametros.information.noTiempoExpiraReserva"),itTiempoExpiraReserva);
			}else if (p.getTipo().intValue()==ParametrosException.TIEMPO_ACUMULA_VIAJES_PARA_PAXFRE_NULL){
				DlgMessage.information(Messages.getString("wndParametros.information.noTiempoAcumulaViajesParaPaxFree"),itTiempoAcumulaViajesParaPaxFree);
			}else if (p.getTipo().intValue()==ParametrosException.VIAJES_REQUERIDOS_INGRESA_PAXFRE_NULL){
				DlgMessage.information(Messages.getString("wndParametros.information.noViajesRequeridosIngresarPaxFre"),itViajesRequeridosIngresarPaxFre);
			}else if (p.getTipo().intValue()==ParametrosException.TIEMPO_BOLETO_VALIDO_NULL){
				DlgMessage.information(Messages.getString("wndParametros.information.noTiempoBoletoValido"),itTiempoBoletoValido);
			}else if (p.getTipo().intValue()==ParametrosException.PUNTOS_ACUMULA_POR_VENTAPAXFRE_NULL){
				DlgMessage.information(Messages.getString("wndParametros.information.noPuntosAcumulaPorVentaPaxFre"),itPuntosAcumulaPorVentaPaxFre);
			}else if (p.getTipo().intValue()==ParametrosException.MONTO_COBRAR_POSTERGACION_NULL){
				DlgMessage.information(Messages.getString("wndParametros.information.noMontocobrarPostergacion"),itMontocobrarPostergacion);
			}else if (p.getTipo().intValue()==ParametrosException.MONTO_COBRAR_REIMPRESION_NULL){
				DlgMessage.information(Messages.getString("wndParametros.information.noMontoCobrarReimpresion"),itMontoCobrarReimpresion);
			}else if (p.getTipo().intValue()==ParametrosException.MAXIMO_POSTERGACIONES_BOLETO_NULL){
				DlgMessage.information(Messages.getString("wndParametros.information.noMaximoPostergacionesBoleto"),itMaximoPostergacionesBoleto);
			}else if (p.getTipo().intValue()==ParametrosException.TIEMPO_PUEDE_REALIZAR_POSTERGACION_NULL){
				DlgMessage.information(Messages.getString("wndParametros.information.noTiempoPuedeRealizarPostergacion"),itTiempoPuedeRealizarPostergacion);
			}else if (p.getTipo().intValue()==ParametrosException.TIEMPO_PAX_CANJE_BOLETO_CUMPLEANIOS_NULL){
				DlgMessage.information(Messages.getString("wndParametros.information.noTiempoPaxCanjeBoletoCumpleanios"),itTiempoPaxCanjeBoletoCumpleanios);
			}else if (p.getTipo().intValue()==ParametrosException.TIEMPO_CADUCAN_PUNTOS_OPTENIDOS_PAXFREE_NULL){
				DlgMessage.information(Messages.getString("wndParametros.information.noTiempoCaducanPuntosOptenidosPaxfreevDebe"),itTiempoCaducanPuntosOptenidosPaxfree);
			}else if (p.getTipo().intValue()==ParametrosException.ALERTA_ENVIO_ESPECIE_VALORADA_NULL){
				DlgMessage.information(Messages.getString("wndParametros.information.noAlertaEnvioEspecieValorada"),itAlertaEnvioEspecieValorada);
			}else if (p.getTipo().intValue()==ParametrosException.ALERTA_SOLICITA_MANIFIESTOPAX_NULL){
				DlgMessage.information(Messages.getString("wndParametros.information.noAlertaSolicitaManifiestoPax"),itAlertaSolicitaManifiestoPax);
			}else if (p.getTipo().intValue()==ParametrosException.USUARIO_GENECIA_APROBADOR_NULL){
				DlgMessage.information(Messages.getString("wndParametros.information.noSelctUsuarioGeneciaAprobador"));
			}else if (p.getTipo().intValue()==ParametrosException.USUARIO_COMERCIAL_APROBADOR_NULL){
				DlgMessage.information(Messages.getString("wndParametros.information.noSelctUsuarioComercialAprobador"));
			}else if (p.getTipo().intValue()==ParametrosException.USUARIO_FINANZAS_APROBADOR_NULL){
				DlgMessage.information(Messages.getString("wndParametros.information.noSelctUsuarioFinanzasAprobador"));
			}else if (p.getTipo().intValue()==ParametrosException.USUARIO_GENRENCIA_COMERCIAL_NULL){
				DlgMessage.information(Messages.getString("wndParametros.information.noSelctUsuarioGenrenciaComercial"));
			}else if(p.getTipo().intValue()==ParametrosException.MAXIMO_ASIENTOS_SELECCIONADOS_NULL){
				DlgMessage.information(Messages.getString("wndParametros.information.noMaximoAsientosSeleccionados"));
			}
		}catch (Exception e) {
			DlgMessage.error(this.getClass().getName()+" "+e.getMessage());
			e.printStackTrace();
		}
	}
	
//	/**
//	 * selecciona los items de las listas de los usuarios aprobadores.
//	 * @param listbox		: listbox donde se ara el select.	
//	 * @param iDsUsuario	: ids de los usuarios aprobadores
//	 */
//	private void selectItemsUsuarios(Listbox listbox, String iDsUsuario){
//		/*Selecciona usuarios Gerencia Comercial Aprobador*/
//		String[] lstUGC = iDsUsuario.split(",");
//		String iDugc="";
//		
//		for(int i=0;i<lstUGC.length;i++){
//			iDugc=lstUGC[i].toString();
//			int x=-1;
//			for(Listitem item: listbox.getItems()){
//				x++;
//				UsuarioAprobador usuarioAprobador=item.getValue();
//				if(usuarioAprobador.getUsuario().getId().toString().equals(iDugc))
//					listbox.getItemAtIndex(x).setSelected(true);
//			}
//		}
//	}
	
	/**
	 * Optiene los IDs del usuarios, según el parametro indicado
	 * @param listbox	: Listbox del cual se optienen los IDs del usuario.
	 * @return
	 */
	private String getIDsUsuario(Listbox listbox){
		/*Usuario Gerencia Comercial Aprobador*/
		String iDsUsuario="";
		for (Listitem item: listbox.getSelectedItems()){
			UsuarioAprobador usuarioAprobador=item.getValue();
			if(iDsUsuario.length()==0)
				iDsUsuario=usuarioAprobador.getUsuario().getId().toString();
			else iDsUsuario+=","+usuarioAprobador.getUsuario().getId().toString();
		}
		return iDsUsuario;
	}
	
	
//	/**
//	 * Cargar listboxs con los usuarios
//	 * @param listbox
//	 */
//	private void cargarListaUsuarios(Listbox listbox){
//		ArrayList<UsuarioAprobador> lstUsuarioAprobador = ServiceLocator.getUsuarioAprobadorManager().buscarPorEstadoRegistro(Constantes.VALUE_ACTIVO,null);
//		
//		Listitem item= null;
//		Listcell cell= null;
//		
//		for (UsuarioAprobador usuarioAprobador: lstUsuarioAprobador) {
//			Usuario  usuario= usuarioAprobador.getUsuario();
//			if(usuario.getApellidoMaterno()==null)
//				usuario.setApellidoMaterno("");
//			item= new Listitem();
//			cell=new Listcell(usuario.getApellidoPaterno()+" "+usuario.getApellidoMaterno()+", "+ usuario.getNombre());
//			item.appendChild(cell);
//			
//			item.setValue(usuarioAprobador);
//			listbox.appendChild(item);
//		}
//	}
	
	
}
