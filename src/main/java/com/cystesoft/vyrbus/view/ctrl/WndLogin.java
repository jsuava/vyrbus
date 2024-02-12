/**
 * Proyecto		: SISVYR
 * Sistema		: Sistema de Ventas y Reservas
 * Descripci�n	: Clase que manejara los metodos relacionados con el acceso al sistema.
 * Autor		: Jos� Sullo Avalos
 * Fecha		: 09/04/2012
 */
package com.cystesoft.vyrbus.view.ctrl;

import java.util.Date;
import java.util.List;
import java.util.TreeMap;

import javax.security.auth.login.LoginException;
//import javax.servlet.ServletRequest;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.zkforge.bwcaptcha.Captcha;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zul.Checkbox;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Comboitem;
import org.zkoss.zul.Div;
import org.zkoss.zul.Hlayout;
import org.zkoss.zul.Label;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Separator;
import org.zkoss.zul.Textbox;

import com.cystesoft.vyrbus.model.bean.Agencia;
import com.cystesoft.vyrbus.model.bean.CanalVenta;
import com.cystesoft.vyrbus.model.bean.ControlAcceso;
import com.cystesoft.vyrbus.model.bean.Liquidacion;
import com.cystesoft.vyrbus.model.bean.Parametros;
import com.cystesoft.vyrbus.model.bean.Rol;
import com.cystesoft.vyrbus.model.bean.TipoAgencia;
import com.cystesoft.vyrbus.model.bean.TipoComprobante;
import com.cystesoft.vyrbus.model.bean.Usuario;
import com.cystesoft.vyrbus.model.bean.UsuarioAprobador;
import com.cystesoft.vyrbus.model.bean.UsuarioHardware;
import com.cystesoft.vyrbus.model.bean.UsuarioRol;
import com.cystesoft.vyrbus.service.exceptions.CaptchaNullException;
import com.cystesoft.vyrbus.service.exceptions.ControlAccesoException;
import com.cystesoft.vyrbus.service.exceptions.LoginNullException;
import com.cystesoft.vyrbus.service.exceptions.LoginRedirectException;
import com.cystesoft.vyrbus.service.exceptions.PasswordException;
import com.cystesoft.vyrbus.service.exceptions.UsuarioHardwareNullException;
import com.cystesoft.vyrbus.service.exceptions.UsuarioNullException;
import com.cystesoft.vyrbus.service.exceptions.UsuarioRolNullException;
import com.cystesoft.vyrbus.service.locator.ServiceLocator;
import com.cystesoft.vyrbus.service.util.Constantes;
import com.cystesoft.vyrbus.service.util.Encriptar;
import com.cystesoft.vyrbus.service.util.Messages;
import com.cystesoft.vyrbus.service.util.MyTime;
import com.cystesoft.vyrbus.service.util.Util;
import com.cystesoft.vyrbus.service.util.UtilData;
import com.cystesoft.vyrbus.view.ui.DlgMessage;
import com.cystesoft.vyrbus.view.ui.WndBase;


/**
 * Clase que implementa los metodos relacionados con el acceso al sistema.
 * @author JABANTO
 * @since JDK1.6
 */
public class WndLogin extends WndBase {
	private static final long serialVersionUID = 2552336033225034075L;

	private Captcha cpaImagen;
	private Textbox txtLogin;
	private Textbox txtPassword;
	private Textbox txtImagen;
	private Combobox cmbRol;
	private Label lblVersion;
	private Div divRol;
	private Separator spBtnIngresar;
	private Hlayout hlyoutCodigoAcceso;
	private Hlayout hlAccessCode;
	private Checkbox chkOmitirCodigoAcceso;
	private Textbox txtCodigoAcceso;
	private Textbox txtAccessCode;
	private Label lblCopyRigth;

//	private String address = null;

	@Override
	public void onCreate() {
//		txtLogin.setText("abanto");
//		txtPassword.setText("sisvyrdev");
//		txtImagen.setText(cpaImagen.getValue());


		//Begin custom 09-DIC-2013 TEPSA javalos CR#SISVYR002
//		Boolean validate = (Boolean)getDesktop().getExecution().getSession().getAttribute("validatePath");
//		if(validate==null)
//			Executions.sendRedirect("invalidAccess.zul");
//
//		ServletRequest response = (ServletRequest)this.getDesktop().getExecution().getNativeRequest();
//		address = response.getParameter("address");
		//End custom 09-DIC-2013 TEPSA javalos CR#SISVYR002

		//Obtenemos el codigo generado
		String cryptoMAC = (String)getDesktop().getSession().getAttribute(Constantes.ATRIBUTO_DIR_MAC);
		if(cryptoMAC != null) {
			txtAccessCode.setText((String)(getDesktop().getSession().getAttribute(Constantes.ATRIBUTO_DIR_MAC)));
			String deco = Encriptar.decodifica(cryptoMAC, Constantes.KEY_CRYPTO );
			String[] decoArray = deco.split(" ");
			txtLogin.setText(decoArray[1]);
			txtPassword.setText(decoArray[2]);
		}else
			txtAccessCode.setText("");

		/*Busca Parametros*/
		try{
			Parametros parametros= ServiceLocator.getParametrosManager().buscarPorEstadoRegistro("A");
			if(parametros!=null){
				getDesktop().getSession().setAttribute(Constantes.ATRIBUTO_PARAMETROS, parametros);
			}else{
				DlgMessage.information(Messages.getString("Generales.information.errorCargarParametros"));
				Executions.sendRedirect("invalidAccess.zul");
			}
			lblVersion.setValue(Constantes.SYSTEM_VERSION);
			lblCopyRigth.setValue("Copyright, ITSB CONSULTING SAC (C) - " + Constantes.FORMAT_YEAR.format(new Date()));
		}catch (Exception e) {
			e.printStackTrace();
			DlgMessage.information(Messages.getString("Generales.information.errorCargarParametros"));
			//Executions.sendRedirect("invalidAccess.zul");
		}

		/*Descomentar solo para desarrollo - 09/01/2017 - jabanto*/
		try {
//			txtLogin.setText("javalos");
//			txtPassword.setText("");
//			txtImagen.setText(cpaImagen.getValue());
//			txtLogin.setText("moscco");
//			txtPassword.setText("Ant@res1091");
//			txtImagen.setText(cpaImagen.getValue());
//
//			onAccess();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}


	@Override
	public void initComponents() {
		txtLogin = (Textbox)this.getFellow("txtLogin");
		txtPassword = (Textbox)this.getFellow("txtPassword");
		cpaImagen = (Captcha)this.getFellow("cpaImagen");
		txtImagen = (Textbox)this.getFellow("txtImagen");
		cmbRol=(Combobox)this.getFellow("cmbRol");
		divRol=(Div)this.getFellow("divRol");
		spBtnIngresar=(Separator)this.getFellow("spBtnIngresar");
		lblVersion = (Label)this.getFellow("lblVersion");
		hlyoutCodigoAcceso=(Hlayout)this.getFellow("hlyoutCodigoAcceso");
		chkOmitirCodigoAcceso=(Checkbox)this.getFellow("chkOmitirCodigoAcceso");
		txtCodigoAcceso=(Textbox)this.getFellow("txtCodigoAcceso");
		lblCopyRigth=(Label)this.getFellow("lblCopyRigth");
		hlAccessCode=(Hlayout)this.getFellow("hlAccessCode");
		txtAccessCode=(Textbox)this.getFellow("txtAccessCode");
	}


	public void onValidarAccess()throws Exception{

	}

	/**
	 *
	 * @throws Exception
	 */
	public void onAccess()throws Exception{
		try{
			if(txtLogin.getText().trim().equals(new String("")))
				throw new LoginNullException();
			else if(txtPassword.getText().equals(new String("")))
				throw new PasswordException(PasswordException.PASSWORD_NULL); //PasswordNullException();
			else if(txtImagen.getText().trim().equals(new String("")))
				throw new CaptchaNullException(CaptchaNullException.CODIGO_VACIO);
			else if(!cpaImagen.getValue().toUpperCase().equals(txtImagen.getText().trim().toUpperCase()))
				throw new CaptchaNullException(1);

//			/*	Validando que el CPU tenga permitido acceder al sistema	*/
//			UsuarioHardware usuarioHardware = ServiceLocator.getUsuarioHardwareManager().buscarXCodigo(address);
//			if(usuarioHardware.getId()==null)
//				throw new UsuarioHardwareNullException();

			/*	Valida usuario y password sean correctos	*/
			//Usuario usuario = new Usuario();
			final Usuario usuario = ServiceLocator.getUsuarioManager().buscarUsuarioPorLoginPassword(txtLogin.getText().trim(), txtPassword.getText().trim(), Constantes.VALUE_ACTIVO);
			if (usuario == null)
				throw new UsuarioNullException();
			
			Usuario userLogueado = (Usuario)this.getDesktop().getSession().getAttribute(Constantes.ATRIBUTO_USUARIO);
			if(userLogueado != null && usuario.getId()!= userLogueado.getId()) {
				throw new LoginRedirectException(userLogueado.getLogin());				
			}

			ControlAcceso controlAcceso = null;
			if(usuario.getTipoSeguridad().intValue()==Constantes.VALIDAR_APPLET) {
				if(txtAccessCode.getText().trim().equals(""))
					throw new ControlAccesoException(ControlAccesoException.EMPTY_CODIGO);

				controlAcceso = ServiceLocator.getUsuarioManager().buscarCodigoAcceso(usuario.getId(), txtAccessCode.getText().trim(), Constantes.VALUE_ACTIVO);
				if (controlAcceso == null)
					throw new ControlAccesoException(ControlAccesoException.EXPIRED_CODIGO);
			}

//			txtLogin.setText("");
//			txtPassword.setText("");
//			txtAccessCode.setText("");

//			Session session =null;
//			Boolean ejecutarApplet=false;
//			/*Para el solicitar el codigo de acceso*/
//			if(hlyoutCodigoAcceso.isVisible()==false){
//				if(usuario.getTipoSeguridad().intValue()==Constantes.VALIDAR_APPLET){
//					hlyoutCodigoAcceso.setVisible(true);
//					txtCodigoAcceso.setFocus(true);
//					return;
//				}
//			}else if (!(chkOmitirCodigoAcceso.isChecked()) && txtCodigoAcceso.getText().trim().isEmpty()){
//				DlgMessage.information(Messages.getString("WndLogin.information.noCodigoAcceso"),txtCodigoAcceso);
//				return;
//			}else if (!(chkOmitirCodigoAcceso.isChecked()) && !(txtCodigoAcceso.getText().trim().isEmpty())){
//				TreeMap<String, Object>criteriosBusqueda = new TreeMap<>();
//				criteriosBusqueda.put("codigoAutorizacion", txtCodigoAcceso.getText().trim().toUpperCase());
//				criteriosBusqueda.put("estado", Constantes.TRUE_VALUE);
//				criteriosBusqueda.put("sessionIniciada", Constantes.FALSE_VALUE);
//				criteriosBusqueda.put("estadoRegistro", Constantes.VALUE_ACTIVO);
//				List<Session> resultSession=ServiceLocator.getSessionManager().buscarPorX(criteriosBusqueda, null);
//				if(resultSession.size()>0){
//					session=resultSession.get(0);
//					Date fechaActual=new Date();
//					if(session.getFechaInicial().getTime()<=fechaActual.getTime() && session.getFechaFin().getTime()<=fechaActual.getTime()){
//						DlgMessage.information(Messages.getString("WndLogin.information.codigoAccesoExpiro"),txtCodigoAcceso);
//						return;
//					}
//				}else{
//					DlgMessage.information(Messages.getString("WndLogin.information.noExisteCodigoAcceso"),txtCodigoAcceso);
//					return;
//				}
//			}else if (chkOmitirCodigoAcceso.isChecked())
//				ejecutarApplet=true;


			/*	Valida si el usuario tiene o no un rol asignado.	*/
			Rol rol = new Rol();
			List<UsuarioRol> listUsuarioRol=null;
			if(!divRol.isVisible()){
				listUsuarioRol = ServiceLocator.getUsuarioRolManager().buscarXIdUsuario(usuario.getId());
				if (listUsuarioRol.size()==1){
					rol = ServiceLocator.getRolManager().buscarPorId(new Long(listUsuarioRol.get(0).getUsuarioRolID().getIdRol()));
				}else if (listUsuarioRol.size()>1){
					UtilData.cargarGenericData(cmbRol, false);
					for(UsuarioRol usuarioRol:listUsuarioRol){
						Comboitem comboitem=new Comboitem();
							comboitem.setLabel(usuarioRol.getRol().getDenominacion());
							comboitem.setValue(usuarioRol.getRol());
							cmbRol.appendChild(comboitem);
					}
					spBtnIngresar.setHeight("5px");
					divRol.setVisible(true);
					cmbRol.setFocus(true);
					cmbRol.select();
					return;
				}else
					throw new UsuarioRolNullException();
			}else{
				if(cmbRol.getSelectedItem().getValue() instanceof Rol){
					rol=(Rol) cmbRol.getSelectedItem().getValue();
					rol=ServiceLocator.getRolManager().buscarPorId(rol.getId().longValue());
				}else{
					DlgMessage.information(Messages.getString("WndLogin.information.rolNoSelect"),cmbRol);
					return;
				}
			}

			/*	Recupera al usuario aprobador	*/
			UsuarioAprobador usuarioAprobador= null;
			TreeMap<String, Object> criterioBusqueda = new TreeMap<>();
			criterioBusqueda.put("estadoRegistro", Constantes.VALUE_ACTIVO);
			criterioBusqueda.put("usuario", usuario);
			List<UsuarioAprobador> listUsuarioAprobador = ServiceLocator.getUsuarioAprobadorManager().buscarPorX(criterioBusqueda, null);
			if(listUsuarioAprobador.size()>0){
				usuarioAprobador=new UsuarioAprobador();
				usuarioAprobador=listUsuarioAprobador.get(0);
			}

			this.getDesktop().getSession().setAttribute(Constantes.ATRIBUTO_USUARIO, usuario);
			this.getDesktop().getSession().setAttribute(Constantes.ATRIBUTO_USUARIO_APROBADOR, usuarioAprobador);
			this.getDesktop().getSession().setAttribute(Constantes.ATRIBUTO_ROL, rol);

//			###EDIT GEBIN 04/05/2016 - jabanto
//			if(ejecutarApplet){
//					Executions.sendRedirect("generateAddress.zul");
//			}else{
				UsuarioHardware usuarioHardware=null;
//				//Actualiza la session del usuario como iniciada.
				if(controlAcceso!=null){
//					session.setSessionIniciada(Constantes.TRUE_VALUE);
//					session.setFechaAccesoSistema(new Date());
//					ServiceLocator.getSessionManager().actualizar(session);
//					//
//					usuarioHardware = session.getUsuarioHardware();
					usuarioHardware = ServiceLocator.getUsuarioHardwareManager().buscarPorId(controlAcceso.getUsuarioHardware().getId().longValue());
				}else{
					if(usuario.getUsuarioHardware()==null)
						throw new UsuarioHardwareNullException();
					usuarioHardware = ServiceLocator.getUsuarioHardwareManager().buscarPorId(usuario.getUsuarioHardware().getId().longValue());
				}


				/****** RECUPERANDO DATOS DE ACUERDO AL USUARIO HARDWARE******/
				/*	Recupera el Canal de Venta	*/
				CanalVenta canalVenta = new CanalVenta();
				canalVenta.setId(usuarioHardware.getCanalVenta().getId());
				canalVenta.setDenominacion(usuarioHardware.getCanalVenta().getDenominacion());

				/*Valida si es canal web, para denegarle el acceso*/
				if(canalVenta.getId().intValue()==Constantes.ID_CANVEN_WEB){
					DlgMessage.information(Messages.getString("WndLogin.information.canalNoSession"));
					return;
				}

				/*	Recupera la Agencia	*/
				Agencia agencia = usuarioHardware.getAgencia();
				final TipoAgencia tipoAgencia = agencia.getTipoAgencia();
				agencia.setTipoAgencia(tipoAgencia);

				/*	Recupera el Tipo de Comprobante	*/
				Integer idTipoComprobante = null;
				if(agencia.getTipoAgencia().getId().intValue()==Constantes.ID_TIPAGE_TEPSA)
					idTipoComprobante = Constantes.ID_TIPCOM_BOLETO_VIAJE;
				else if(agencia.getTipoAgencia().getId().intValue()==Constantes.ID_TIPAGE_VIAJES)
					idTipoComprobante = Constantes.ID_TIPCOM_VOUCHER_AGENCIA_VIAJES;
				else
					idTipoComprobante = Constantes.ID_TIPCOM_VOUCHER_CORPORATIVO;
				TipoComprobante tipoComprobante=ServiceLocator.getTipoComprobanteManager().buscarPorId(idTipoComprobante.longValue());

				this.getDesktop().getSession().setAttribute(Constantes.ATRIBUTO_USUARIO_HARDWARE, usuarioHardware);
				this.getDesktop().getSession().setAttribute(Constantes.ATRIBUTO_CANAL_VENTA, canalVenta);
				this.getDesktop().getSession().setAttribute(Constantes.ATRIBUTO_AGENCIA, agencia);
				this.getDesktop().getSession().setAttribute(Constantes.ATRIBUTO_TIPO_COMPROBANTE, tipoComprobante);
				//************************************************************************

				/*	Busca Liquidacion abierta del usuario	*/
				Liquidacion liquidacion = new Liquidacion();
				liquidacion=UtilData.estadoLiquidacionUsuario(usuario, agencia);

				/* Realiza el cierre y/o apertura de manera automatica para el caso de las agencias de viaje o corporativos - impl 31/03/2014 */
				if(tipoAgencia.getId().intValue()!=Constantes.ID_TIPAGE_TEPSA){
					Date fechaServer=Constantes.FORMAT_DATE.parse(new MyTime().dateServer());
					if(liquidacion!=null && liquidacion.getFechaLiquidacion().getTime()<fechaServer.getTime()){
						//Realiza el cierre
						Double totalVentas=ServiceLocator.getVentaPasajesManager().buscarTotalVentas(Constantes.FORMAT_DATE.format(liquidacion.getFechaLiquidacion()), agencia.getId(), usuario.getId());
						UtilData.procesaCierreCaja(liquidacion, totalVentas,usuario, 0.0);

						//Apertura una nueva liquidacion
						liquidacion=UtilData.aperturarLiquidacion(fechaServer,usuario,agencia);
					}else if (liquidacion==null){
						//Apertura una nueva liquidacion
						liquidacion=UtilData.aperturarLiquidacion(fechaServer,usuario,agencia);
					}
				}

				if (liquidacion !=null && liquidacion.getFechaLiquidacion() !=null)
					this.getDesktop().getSession().setAttribute(Constantes.ATRIBUTO_FECHA_LIQUIDACION,liquidacion.getFechaLiquidacion());

				/*	Calcula el porcentaje de correlativos utilizados del manifiesto */
				int porcentajeCorrelativoManifiesto = 0;
				porcentajeCorrelativoManifiesto = (int) UtilData.porcentajeCorrelativoManifiesto(agencia);

				final Liquidacion liquidacion_f = liquidacion;
				if (porcentajeCorrelativoManifiesto >=Constantes.ALERTAR_ENVIO_MANIFIESTOS){
					/*Cuando el  correlativo del manifiesto es Mayor al 80 %*/
					Messagebox.show(Messages.getString("WndManifiesto.information.utilizacionCorrelativosManifiesto")+" "+porcentajeCorrelativoManifiesto+" %", DlgMessage.NOMBREAPLICACION, DlgMessage.BTN_OK, Messagebox.INFORMATION, new EventListener<Event>() {
						@Override
						public void onEvent(Event e) throws Exception {
							if(e.getName().equals("onOK") || e.getName().equals("onClose")){
								//El Usuario tiene una liquidacion abierta y es una agencia tepsa
								if (liquidacion_f!=null && tipoAgencia.getId().intValue()==Constantes.ID_TIPAGE_TEPSA){
									Messagebox.show("El sistema ha detectado una caja abierta, con fecha: " + Constantes.FORMAT_DATE.format(liquidacion_f.getFechaLiquidacion())+". "+
											Messages.getString("Generales.information.liquidacionAbierta"), DlgMessage.NOMBREAPLICACION, DlgMessage.BTN_OK, Messagebox.INFORMATION, new EventListener<Event>() {
										@Override
										public void onEvent(Event e) throws Exception {
											if(e.getName().equals("onOK") || e.getName().equals("onClose")){
												Executions.sendRedirect("principal.zul");
											}
										}
									});
								}else
									//El Usuario NO Tiene liquidacion abierta o es una agencia de viajes o corporativo
									Executions.sendRedirect("principal.zul");
							}
						}
					});
				}else{//Cuando el uso del correlativo del manifiesto es menor al 80 %

					//El Usuario tiene liquidacion una abierta y es una agencia tepsa
					if (liquidacion_f!=null && tipoAgencia.getId().intValue()==Constantes.ID_TIPAGE_TEPSA){
						Messagebox.show("El sistema ha detectado una caja abierta, con fecha: " + Constantes.FORMAT_DATE.format(liquidacion_f.getFechaLiquidacion())+". "+
								Messages.getString("Generales.information.liquidacionAbierta"), DlgMessage.NOMBREAPLICACION, DlgMessage.BTN_OK, Messagebox.INFORMATION, new EventListener<Event>() {
							@Override
							public void onEvent(Event e) throws Exception {
								if(e.getName().equals("onOK") || e.getName().equals("onClose")){
									Executions.sendRedirect("principal.zul");
								}
							}
						});
					}else
						//El Usuario NO Tiene liquidacion abierta o es una agencia de viajes o corporativo
						Executions.sendRedirect("principal.zul");
				}
//			}

//			##END BEGIN 04/05/2016 - jabanto
//			if(usuario.getTipoSeguridad().intValue()==Constantes.VALIDAR_APPLET){
//				Executions.sendRedirect("generateAddress.zul");
//			}else{
//				if(usuario.getUsuarioHardware()==null)
//					throw new UsuarioHardwareNullException();
//
//				UsuarioHardware usuarioHardware = ServiceLocator.getUsuarioHardwareManager().buscarPorId(usuario.getUsuarioHardware().getId().longValue());
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
//				final TipoAgencia tipoAgencia = agencia.getTipoAgencia();
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
//				TipoComprobante tipoComprobante=ServiceLocator.getTipoComprobanteManager().buscarPorId(idTipoComprobante.longValue());
//
//				this.getDesktop().getSession().setAttribute(Constantes.ATRIBUTO_USUARIO_HARDWARE, usuario.getUsuarioHardware());
//				this.getDesktop().getSession().setAttribute(Constantes.ATRIBUTO_CANAL_VENTA, canalVenta);
//				this.getDesktop().getSession().setAttribute(Constantes.ATRIBUTO_AGENCIA, agencia);
//				this.getDesktop().getSession().setAttribute(Constantes.ATRIBUTO_TIPO_COMPROBANTE, tipoComprobante);
//				//************************************************************************
//
//				/*	Busca Liquidacion abierta del usuario	*/
//				Liquidacion liquidacion = new Liquidacion();
//				liquidacion=UtilData.estadoLiquidacionUsuario(usuario, agencia);
//
//				/* Realiza el cierre y/o apertura de manera automatica para el caso de las agencias de viaje o corporativos - impl 31/03/2014 */
//				if(tipoAgencia.getId().intValue()!=Constantes.ID_TIPAGE_TEPSA){
//					Date fechaServer=Constantes.FORMAT_DATE.parse(new MyTime().dateServer());
//					if(liquidacion!=null && liquidacion.getFechaLiquidacion().getTime()<fechaServer.getTime()){
//						//Realiza el cierre
//						Double totalVentas=ServiceLocator.getVentaPasajesManager().buscarTotalVentas(Constantes.FORMAT_DATE.format(liquidacion.getFechaLiquidacion()), agencia.getId(), usuario.getId());
//						UtilData.procesaCierreCaja(liquidacion, totalVentas,usuario);
//
//						//Apertura una nueva liquidacion
//						liquidacion=UtilData.aperturarLiquidacion(fechaServer,usuario,agencia);
//					}else if (liquidacion==null){
//						//Apertura una nueva liquidacion
//						liquidacion=UtilData.aperturarLiquidacion(fechaServer,usuario,agencia);
//					}
//				}
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
//								//El Usuario tiene una liquidacion abierta y es una agencia tepsa
//								if (liquidacion_f!=null && tipoAgencia.getId().intValue()==Constantes.ID_TIPAGE_TEPSA){
//									Messagebox.show("El sistema ha detectado una caja abierta, con fecha: " + Constantes.FORMAT_DATE.format(liquidacion_f.getFechaLiquidacion())+". "+
//											Messages.getString("Generales.information.liquidacionAbierta"), DlgMessage.NOMBREAPLICACION, DlgMessage.BTN_OK, Messagebox.INFORMATION, new EventListener<Event>() {
//										@Override
//										public void onEvent(Event e) throws Exception {
//											if(e.getName().equals("onOK") || e.getName().equals("onClose")){
//												Executions.sendRedirect("principal.zul");
//											}
//										}
//									});
//								}else
//									//El Usuario NO Tiene liquidacion abierta o es una agencia de viajes o corporativo
//									Executions.sendRedirect("principal.zul");
//							}
//						}
//					});
//				}else{//Cuando el uso del correlativo del manifiesto es menor al 80 %
//
//					//El Usuario tiene liquidacion una abierta y es una agencia tepsa
//					if (liquidacion_f!=null && tipoAgencia.getId().intValue()==Constantes.ID_TIPAGE_TEPSA){
//						Messagebox.show("El sistema ha detectado una caja abierta, con fecha: " + Constantes.FORMAT_DATE.format(liquidacion_f.getFechaLiquidacion())+". "+
//								Messages.getString("Generales.information.liquidacionAbierta"), DlgMessage.NOMBREAPLICACION, DlgMessage.BTN_OK, Messagebox.INFORMATION, new EventListener<Event>() {
//							@Override
//							public void onEvent(Event e) throws Exception {
//								if(e.getName().equals("onOK") || e.getName().equals("onClose")){
//									Executions.sendRedirect("principal.zul");
//								}
//							}
//						});
//					}else
//						//El Usuario NO Tiene liquidacion abierta o es una agencia de viajes o corporativo
//						Executions.sendRedirect("principal.zul");
//				}
//			}

		} catch (UsuarioHardwareNullException uhnex){
			Executions.sendRedirect("invalidAccess.zul");
		} catch (UsuarioNullException unex){
			DlgMessage.information("Nombre de usuario o contrase�a incorrectos");
		}catch (LoginException lex){
			DlgMessage.information("Nombre de usuario o contrase�a incorrectos");
		}catch (UsuarioRolNullException urne){
			DlgMessage.information(Messages.getString("WndLogin.information.usuarioSinRol"));
		}catch(LoginNullException lnex){
			DlgMessage.information(Messages.getString("WndLogin.information.usuario"), txtLogin);
		}catch (PasswordException cp) {
			if(cp.getTipo().intValue()==PasswordException.PASSWORD_NULL){
				DlgMessage.information(Messages.getString("WndLogin.information.password"));
				txtPassword.setFocus(true);
			}else if(cp.getTipo()==PasswordException.PASSWORD_INCOREC){
				DlgMessage.information("Nombre de usuario o contrase�a incorrectos");
			}
		}catch(CaptchaNullException cnex){
			if(cnex.getLevel().intValue()==0)
				DlgMessage.information(Messages.getString("WndLogin.information.captcha"),txtImagen);
			else if(cnex.getLevel().intValue()==1)
				DlgMessage.information(Messages.getString("WndLogin.information.captchaNoEquals"),txtImagen);
			txtImagen.setText("");
			cpaImagen.randomValue();
		}catch (ControlAccesoException lex){
			if(lex.getTipo()==ControlAccesoException.EMPTY_CODIGO){
				Executions.sendRedirect("invalidAccess.zul");
			}else{
				DlgMessage.information("El codigo de acceso no existe o no es valido.", txtAccessCode);
			}
		}catch(LoginRedirectException lrex) {
			Messagebox.show("Ya hay un usuario logueado en el Sistema, si desea ingresar al Sistema abra otro NAVEGADOR o cierre la sesion del usuario "+lrex.getMensaje().toUpperCase(), DlgMessage.NOMBREAPLICACION, DlgMessage.BTN_OK, Messagebox.QUESTION, new EventListener<Event>() {
				@Override
				public void onEvent(Event e){
					if(e.getName().equals("onOK")){
						Executions.sendRedirect("login.zul");
					}
				}
			});			
		}catch(Exception ex){
			ex.printStackTrace();
			DlgMessage.error(this.getClass().getName()+" "+ex.getMessage());
		}
	}


	public void onExit()throws Exception {
		cpaImagen.randomValue();
	}


	public void txtLogin_onchange(){
		txtPassword.setText("");
		Util.limpiarCombobox(cmbRol);
		divRol.setVisible(false);
		spBtnIngresar.setHeight("14px");
	}

	public void createCookie(){
		HttpServletResponse response = (HttpServletResponse)Executions.getCurrent().getNativeResponse();
        Cookie userCookie = new Cookie("user", "xxx123");
        response.addCookie(userCookie);
        Cookie [] cookies = ((HttpServletRequest)Executions.getCurrent().getNativeRequest()).getCookies();
        System.out.println(cookies[0].getName());
        System.out.println(cookies[0].getValue());
	}


}
