/**
 * Proyecto		: SISVYR
 * Sistema		: Sistema de Ventas y Reservas
 * Descripción	:
 * Autor		: José Sullo Avalos
 * Fecha		: 07/03/2013
 */
package com.cystesoft.vyrbus.view.ctrl;

import javax.servlet.ServletRequest;

import org.zkoss.zk.ui.Executions;
import org.zkoss.zul.Window;

import com.cystesoft.vyrbus.service.util.Constantes;

/**
 * @author Jose
 *
 */
public class WndValidateAccess extends Window {
	private static final long serialVersionUID = 1L;
	private String addressMAC = null;

	public void onCreate(){
//		Begin custom 09-DIC-2013 TEPSA javalos CR#SISVYR002
//		Boolean validate = (Boolean)getDesktop().getExecution().getSession().getAttribute("validatePath");
//		if(validate==null)
//			Executions.sendRedirect("invalidAccess.zul");

		ServletRequest response = (ServletRequest)this.getDesktop().getExecution().getNativeRequest();
		addressMAC = response.getParameter("addressMAC");
		String dirMAC = "";
		char[] charMAC = addressMAC.toCharArray();
		for(int i=0; i<addressMAC.length();i++){
			if(charMAC[i] == ' ')
				charMAC[i]='+';
			dirMAC = dirMAC + String.valueOf(charMAC[i]);
		}
		this.getDesktop().getSession().setAttribute(Constantes.ATRIBUTO_DIR_MAC, dirMAC);
		Executions.sendRedirect("login.zul");
//		//Begin custom 09-DIC-2013 TEPSA javalos CR#SISVYR002
//		onAccess();
	}

//	public void onAccess(){
//		try{
//			/*	Validando que el CPU tenga permitido acceder al sistema	*/
//			UsuarioHardware usuarioHardware = ServiceLocator.getUsuarioHardwareManager().buscarXCodigo(address);
//			if(usuarioHardware.getId()==null)
//				throw new UsuarioHardwareNullException();
//			else{
//				/****** RECUPERANDO DATOS DE ACUERDO AL USUARIO HARDWARE******/
//				/*	Recupera el Canal de Venta	*/
//				CanalVenta canalVenta = new CanalVenta();
//				canalVenta.setId(usuarioHardware.getCanalVenta().getId());
//				canalVenta.setDenominacion(usuarioHardware.getCanalVenta().getDenominacion());
//
//				/*Valida si es canal web, para denegarle el acceso*/
//				if(canalVenta.getId().intValue()==Constantes.ID_CANVEN_WEB){
//					DlgMessage.information(Messages.getString("WndLogin.information.canalNoSession"));
//					return;
//				}
//
//				/*	Recupera la Agencia	*/
//				Agencia agencia = usuarioHardware.getAgencia();
//				TipoAgencia tipoAgencia = agencia.getTipoAgencia();
//				agencia.setTipoAgencia(tipoAgencia);
//
//				/*	Recupera el Tipo de Comprobante	*/
//				Integer idTipoComprobante = null;
//				if(agencia.getTipoAgencia().getId().intValue()==Constantes.ID_TIPAGE_TEPSA)
//					idTipoComprobante = Constantes.ID_TIPCOM_BOLETO_VIAJE;
//				else if(agencia.getTipoAgencia().getId().intValue()==Constantes.ID_TIPAGE_VIAJES)
//					idTipoComprobante = Constantes.ID_TIPCOM_VOUCHER_AGENCIA_VIAJES;
//				else
//					idTipoComprobante = Constantes.ID_TIPCOM_VOUCHER_CORPORATIVO;
//				//TipoComprobante tipoComprobante = new TipoComprobante();
//				TipoComprobante tipoComprobante=ServiceLocator.getTipoComprobanteManager().buscarPorId(idTipoComprobante.longValue());
//
//				this.getDesktop().getSession().setAttribute(Constantes.ATRIBUTO_USUARIO_HARDWARE, usuarioHardware);
//				this.getDesktop().getSession().setAttribute(Constantes.ATRIBUTO_CANAL_VENTA, canalVenta);
//				this.getDesktop().getSession().setAttribute(Constantes.ATRIBUTO_AGENCIA, agencia);
//				this.getDesktop().getSession().setAttribute(Constantes.ATRIBUTO_TIPO_COMPROBANTE, tipoComprobante);
//
//				/*	Busca Liquidacion abierta del usuario	*/
//				Liquidacion liquidacion = new Liquidacion();
//				liquidacion=UtilData.estadoLiquidacionUsuario(getUsuario(), agencia);
//
//				if (liquidacion !=null && liquidacion.getFechaLiquidacion() !=null)
//					this.getDesktop().getSession().setAttribute(Constantes.ATRIBUTO_FECHA_LIQUIDACION,liquidacion.getFechaLiquidacion());
//
//				/*	Calcula el porcentaje de correlativos utilizados del manifiesto */
//				Integer porcentajeCorrelativoManifiesto = 0;
//				porcentajeCorrelativoManifiesto = (int) UtilData.porcentajeCorrelativoManifiesto(agencia);
//
//				final Liquidacion liquidacion_f = liquidacion;
//				if (porcentajeCorrelativoManifiesto >=Constantes.ALERTAR_ENVIO_MANIFIESTOS){
//					/*Cuando el  correlativo del manifiesto es Mayor al 80 %*/
//					Messagebox.show(Messages.getString("WndManifiesto.information.utilizacionCorrelativosManifiesto")+" "+porcentajeCorrelativoManifiesto+" %", DlgMessage.NOMBREAPLICACION, DlgMessage.BTN_OK, Messagebox.INFORMATION, new EventListener<Event>() {
//						@Override
//						public void onEvent(Event e) throws Exception {
//							if(e.getName().equals("onOK") || e.getName().equals("onClose")){
//								//Busca liquidacion
//								if (!(liquidacion_f==null)){//El Usuario tiene una liquidacion abierta
//									Messagebox.show("El sistema ha detectado una caja abierta, con fecha: " + Constantes.FORMAT_DATE.format(liquidacion_f.getFechaLiquidacion())+". "+
//											Messages.getString("Generales.information.liquidacionAbierta"), DlgMessage.NOMBREAPLICACION, DlgMessage.BTN_OK, Messagebox.INFORMATION, new EventListener<Event>() {
//										@Override
//										public void onEvent(Event e) throws Exception {
//											if(e.getName().equals("onOK") || e.getName().equals("onClose")){
//												Executions.sendRedirect("principal.zul");
//											}
//										}
//									});
//								}else //El Usuario NO Tiene una liquidacion abierta
//									Executions.sendRedirect("principal.zul");
//							}
//						}
//					});
//				}else{//Cuando el uso del correlativo del manifiesto es menor al 80 %
//					//Busca liquidacion
//					if (!(liquidacion_f==null)){//El Usuario tiene liquidacion una abierta
//						Messagebox.show("El sistema ha detectado una caja abierta, con fecha: " + Constantes.FORMAT_DATE.format(liquidacion_f.getFechaLiquidacion())+". "+
//								Messages.getString("Generales.information.liquidacionAbierta"), DlgMessage.NOMBREAPLICACION, DlgMessage.BTN_OK, Messagebox.INFORMATION, new EventListener<Event>() {
//							@Override
//							public void onEvent(Event e) throws Exception {
//								if(e.getName().equals("onOK") || e.getName().equals("onClose")){
//									Executions.sendRedirect("principal.zul");
//								}
//							}
//						});
//					}else //El Usuario NO Tiene liquidacion abierta
//						Executions.sendRedirect("principal.zul");
//				}
//			}
//		}catch (UsuarioHardwareNullException uhnex){
//			Executions.sendRedirect("invalidAccess.zul");
//		}catch(Exception ex){
//			ex.printStackTrace();
//			DlgMessage.error(this.getClass().getName()+" "+ex.getMessage());
//		}
//	}
}
