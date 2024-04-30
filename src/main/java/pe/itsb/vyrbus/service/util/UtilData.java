/**
 * Proyecto		: SISVYR
 * Sistema		: Sistema de Ventas y Reservas
 * Descripci�n	:
 * Autor		: jM
 * Fecha		: 05/06/2012
 */
package pe.itsb.vyrbus.service.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.TreeMap;

import org.springframework.transaction.annotation.Transactional;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Execution;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zul.Button;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Comboitem;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

import pe.itsb.vyrbus.model.bean.Agencia;
import pe.itsb.vyrbus.model.bean.AutorizadorCortesia;
import pe.itsb.vyrbus.model.bean.Bus;
import pe.itsb.vyrbus.model.bean.CanalVenta;
import pe.itsb.vyrbus.model.bean.CarteraCliente;
import pe.itsb.vyrbus.model.bean.Cliente;
import pe.itsb.vyrbus.model.bean.Concesionario;
import pe.itsb.vyrbus.model.bean.ConfiguracionImpresora;
import pe.itsb.vyrbus.model.bean.ControlEspecieValorada;
import pe.itsb.vyrbus.model.bean.DetalleLiquidacion;
import pe.itsb.vyrbus.model.bean.Empresa;
import pe.itsb.vyrbus.model.bean.EspecieValorada;
import pe.itsb.vyrbus.model.bean.EstadoCivil;
import pe.itsb.vyrbus.model.bean.EstadoDocumentoBus;
import pe.itsb.vyrbus.model.bean.FormaPago;
import pe.itsb.vyrbus.model.bean.GenericBean;
import pe.itsb.vyrbus.model.bean.GrupoMantenimiento;
import pe.itsb.vyrbus.model.bean.Itinerario;
import pe.itsb.vyrbus.model.bean.LineaCreditoCliente;
import pe.itsb.vyrbus.model.bean.Liquidacion;
import pe.itsb.vyrbus.model.bean.Localidad;
import pe.itsb.vyrbus.model.bean.MotivoCortesia;
import pe.itsb.vyrbus.model.bean.MotivoTemporadaAlta;
import pe.itsb.vyrbus.model.bean.Nacionalidad;
import pe.itsb.vyrbus.model.bean.NumeroFlota;
import pe.itsb.vyrbus.model.bean.ObjetoBus;
import pe.itsb.vyrbus.model.bean.OpcionMenu;
import pe.itsb.vyrbus.model.bean.OperadorTarjetaCredito;
import pe.itsb.vyrbus.model.bean.Personal;
import pe.itsb.vyrbus.model.bean.PoolLocalidad;
import pe.itsb.vyrbus.model.bean.PreferenciaAlimentaria;
import pe.itsb.vyrbus.model.bean.Rol;
import pe.itsb.vyrbus.model.bean.Ruta;
import pe.itsb.vyrbus.model.bean.Servicio;
import pe.itsb.vyrbus.model.bean.Sexo;
import pe.itsb.vyrbus.model.bean.TipoAgencia;
import pe.itsb.vyrbus.model.bean.TipoAsiento;
import pe.itsb.vyrbus.model.bean.TipoCentroCosto;
import pe.itsb.vyrbus.model.bean.TipoCobranza;
import pe.itsb.vyrbus.model.bean.TipoComprobante;
import pe.itsb.vyrbus.model.bean.TipoDocumento;
import pe.itsb.vyrbus.model.bean.TipoFlota;
import pe.itsb.vyrbus.model.bean.TipoFormaPago;
import pe.itsb.vyrbus.model.bean.TipoGasto;
import pe.itsb.vyrbus.model.bean.TipoItinerario;
import pe.itsb.vyrbus.model.bean.TipoMoneda;
import pe.itsb.vyrbus.model.bean.TipoMovimiento;
import pe.itsb.vyrbus.model.bean.TipoPersonal;
import pe.itsb.vyrbus.model.bean.TipoPrecio;
import pe.itsb.vyrbus.model.bean.TipoVia;
import pe.itsb.vyrbus.model.bean.TipoZona;
import pe.itsb.vyrbus.model.bean.TranscarUsuarioPersonal;
import pe.itsb.vyrbus.model.bean.Usuario;
import pe.itsb.vyrbus.model.bean.UsuarioAprobador;
import pe.itsb.vyrbus.model.bean.UsuarioHardware;
import pe.itsb.vyrbus.model.bean.UsuarioRol;
import pe.itsb.vyrbus.model.bean.VentaPool;
import pe.itsb.vyrbus.model.bean.VentaTramo;
import pe.itsb.vyrbus.service.exceptions.EspecieValoradaNotAvailableException;
import pe.itsb.vyrbus.service.locator.ServiceLocator;
import pe.itsb.vyrbus.view.ctrl.WndItinerario;
import pe.itsb.vyrbus.view.ui.DlgMessage;
import pe.itsb.vyrbus.view.ui.WndSeleccionaItinerario;
import pe.itsb.vyrbus.view.ui.WndSeleccionarUbigeo;

/**
 * Clase que se encarga de enlazar o cargar datos en los controles u objetos
 * @author jM
 * @since JDK1.6
 */
public class UtilData extends Window {

	private static final long serialVersionUID = 1L;

//	private Date	fechaInsercion;
//    private String  usuarioInsercion;
//	private String	ipInsercion;
//
//	public Date getFechaInsercion() {
//		return fechaInsercion;
//	}
//	public void setFechaInsercion(Date fechaInsercion) {
//		this.fechaInsercion = fechaInsercion;
//	}
//
//	public String getUsuarioInsercion(){
//		return usuarioInsercion;
//	}
//	public void setUsuarioInsercion(String usuarioInsercion) {
//		this.usuarioInsercion = usuarioInsercion;
//	}
//
//	public String getIpInsercion(){
//		return ipInsercion;
//	}
//	public void setIpInsercion(String ipInsercion) {
//		this.ipInsercion = ipInsercion;
//
//	}

	/**
	 * Constante para la data tipoIngresoLiquidacion
	 * @see UtilData#cargarDataCombo(Combobox, int)
	 */
	public static final int DATA_TIPO_INGRESO_LIQUIDACION = 0;
	/**
	 * Constante para la data tipoObjeto
	 * @see UtilData#cargarDataCombo(Combobox, int)
	 */
	public static final int DATA_TIPO_OBJETO = 1;

	/**
	 * Permite registrar las propiedades de auditoria del Pojo
	 * este m�todo debe ser usado para los registros nuevos.
	 * @param genericBean
	 * @param usuario
	 * @throws Exception
	 * @see GenericBean
	 */
	public static void auditarRegistro(GenericBean genericBean, Usuario usuario, Execution execution) throws Exception {
		auditarRegistro(genericBean, false,usuario, execution);
	}


	/**
	 * Permite rgistrar las propiedades de auditoria del Pojo
	 * este m�todo puede ser usado tanto para los registros nuevos o actualizados.
	 * @param genericBean
	 * @param actualizacion <b>false</b> = registro nuevo o <b>true</b> = registro actualizado
	 * @throws Exception
	 */
	public static void auditarRegistro(GenericBean genericBean, boolean actualizacion, Usuario usuario, Execution execution) throws Exception {
		String loginUsuario = usuario.getLogin();
		if (!actualizacion) {
			genericBean.setFechaInsercion(new Date());
			genericBean.setUsuarioInsercion(loginUsuario);
			genericBean.setIpInsercion(ipLocal(execution));
		}
	    genericBean.setUsuarioModificacion(loginUsuario);
		genericBean.setIpModificacion(ipLocal(execution));
	}

	/**
	 * Obtiene el IP del CPU
	 * @return : IP local del CPU
	 * @throws Exception
	 */
	public static String ipLocal(Execution execution) throws Exception{
		String ip = execution.getHeader("X-Forwarded-For");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = execution.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = execution.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = execution.getHeader("HTTP_CLIENT_IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = execution.getHeader("HTTP_X_FORWARDED_FOR");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = execution.getRemoteAddr();
        }
		return ip;
	}


//
//	/**
//	 * Permite enlazar los controles a la ventana de selecci�n de ubigeo
//	 * @param textboxId en este Textbox se devolvera el Id del ubigeo seleccionado
//	 * @param textboxUbicacionGeografica en este Textbox se devolvera la ubicaci�n geografica (Departamento / Provincia / Distrito) seleccionada
//	 * @param button ha este Button se le adjuntara un listener con la llamada a la ventana de selecci�n de ubigeo
//	 * @see WndSeleccionarUbigeo
//	 */
//	public static void enlazarUbigeo(final Textbox textboxId, final Textbox textboxUbicacionGeografica, final Button button) {
//		enlazarUbigeo(textboxId, textboxUbicacionGeografica, button, null);
//		if(button!=null){
//			button.setTooltiptext("Seleccionar ubicaci�n geogr�fica");
//			button.addEventListener(Events.ON_CLICK, new EventListener<Event>() {
//				@Override
//				public void onEvent(Event event) throws Exception {
//					final WndSeleccionarUbigeo oWndSeleccionarUbigeo = new WndSeleccionarUbigeo();
//
//					boolean buscarVentanaParent = true;
//					Component oComponent = button.getParent();
//					while(buscarVentanaParent){
//						 if(oComponent instanceof Window) {
//							 oComponent.appendChild(oWndSeleccionarUbigeo);
//							 buscarVentanaParent = false;
//						 }else{
//						 	oComponent = oComponent.getParent();
//						 }
//					}
//
//					oWndSeleccionarUbigeo.setMode("modal");
//					oWndSeleccionarUbigeo.setVisible(true);
//					oWndSeleccionarUbigeo.addEventListener(Events.ON_SELECT, new EventListener<Event>() {
//						@Override
//						public void onEvent(Event event) throws Exception {
//							textboxId.setText(oWndSeleccionarUbigeo.getIdUbigeo());
//							textboxUbicacionGeografica.setText(oWndSeleccionarUbigeo.getUbicacionGeografica());
//						}
//					});
//				}
//			});
//		}else{
//			textboxUbicacionGeografica.setTooltiptext("Escriba la ubicaci�n geogr�fica y luego pulse enter para realizar la b�squeda.");
//			textboxUbicacionGeografica.addEventListener(Events.ON_OK, new EventListener<Event>() {
//				@Override
//				public void onEvent(Event event) throws Exception {
//					final WndSeleccionarUbigeo oWndSeleccionarUbigeo = new WndSeleccionarUbigeo();
//					boolean buscarVentanaParent = true;
//					Component oComponent = textboxUbicacionGeografica.getParent();
//					while(buscarVentanaParent){
//						 if(oComponent instanceof Window) {
//							 oComponent.appendChild(oWndSeleccionarUbigeo);
//							 buscarVentanaParent = false;
//						 }else{
//						 	oComponent = oComponent.getParent();
//						 }
//					}
//
//					oWndSeleccionarUbigeo.setMode("modal");
//					oWndSeleccionarUbigeo.setVisible(true);
//					oWndSeleccionarUbigeo.addEventListener(Events.ON_SELECT, new EventListener<Event>() {
//						@Override
//						public void onEvent(Event event) throws Exception {
//							textboxId.setText(oWndSeleccionarUbigeo.getIdUbigeo());
//							textboxUbicacionGeografica.setText(oWndSeleccionarUbigeo.getUbicacionGeografica());
//						}
//					});
//				}
//			});
//		}

//	}

	/**
	 * Permite enlazar los controles a la ventana de selecci�n de ubigeo
	 * @param textboxId en este Textbox se devolvera el Id del ubigeo seleccionado
	 * @param textboxUbicacionGeografica en este Textbox se devolvera la ubicaci�n geografica (Departamento / Provincia / Distrito) seleccionada
	 * @param button ha este Button se le adjuntara un listener con la llamada a la ventana de selecci�n de ubigeo
	 * @see WndSeleccionarUbigeo
	 */
	public static void enlazarUbigeo(final Textbox textboxId, final Textbox textboxUbicacionGeografica, final Button button, final Object receiveFocus) {
		if(button!=null){
			button.setTooltiptext("Seleccionar ubicaci�n geogr�fica");
			button.addEventListener(Events.ON_CLICK, new EventListener<Event>() {
				@Override
				public void onEvent(Event event) throws Exception {
					final WndSeleccionarUbigeo oWndSeleccionarUbigeo = new WndSeleccionarUbigeo();
					boolean buscarVentanaParent = true;
					Component oComponent = button.getParent();
					while(buscarVentanaParent){
						 if(oComponent instanceof Window) {
							 oComponent.appendChild(oWndSeleccionarUbigeo);
							 buscarVentanaParent = false;
						 }else{
						 	oComponent = oComponent.getParent();
						 }
					}

					oWndSeleccionarUbigeo.setMode("modal");
					oWndSeleccionarUbigeo.setVisible(true);
					oWndSeleccionarUbigeo.addEventListener(Events.ON_SELECT, new EventListener<Event>() {
						@Override
						public void onEvent(Event event) throws Exception {
							textboxId.setText(oWndSeleccionarUbigeo.getIdUbigeo());
							textboxUbicacionGeografica.setText(oWndSeleccionarUbigeo.getUbicacionGeografica());
						}
					});
				}
			});
		}else{
			textboxUbicacionGeografica.setTooltiptext("Escriba la ubicación geográfica y luego pulse enter para realizar la búsqueda.");
			textboxUbicacionGeografica.addEventListener(Events.ON_OK, new EventListener<Event>() {
				@Override
				public void onEvent(Event event) throws Exception {
					final WndSeleccionarUbigeo oWndSeleccionarUbigeo = new WndSeleccionarUbigeo();
					if(!(textboxUbicacionGeografica.getText().trim().isEmpty())){
						String[] _ubicacion =textboxUbicacionGeografica.getText().trim().split("/");
						String ubicacion=_ubicacion[_ubicacion.length-1].trim().toUpperCase();
						oWndSeleccionarUbigeo.txtNombreUbigeo.setText(ubicacion);
						oWndSeleccionarUbigeo.buscar();
					}
					boolean buscarVentanaParent = true;
					Component oComponent = textboxUbicacionGeografica.getParent();
					while(buscarVentanaParent){
						 if(oComponent instanceof Window) {
							 oComponent.appendChild(oWndSeleccionarUbigeo);
							 buscarVentanaParent = false;
						 }else{
						 	oComponent = oComponent.getParent();
						 }
					}

					oWndSeleccionarUbigeo.setMode("modal");
					oWndSeleccionarUbigeo.setVisible(true);
					if(oWndSeleccionarUbigeo.lbxUbicacionGeografica.getItemCount()>0){
						oWndSeleccionarUbigeo.lbxUbicacionGeografica.setSelectedIndex(0);
						oWndSeleccionarUbigeo.lbxUbicacionGeografica.setFocus(true);
					}
					oWndSeleccionarUbigeo.addEventListener(Events.ON_SELECT, new EventListener<Event>() {
						@Override
						public void onEvent(Event event) throws Exception {
							textboxId.setText(oWndSeleccionarUbigeo.getIdUbigeo());
							textboxUbicacionGeografica.setText(oWndSeleccionarUbigeo.getUbicacionGeografica());
							if(receiveFocus!=null){
								if(receiveFocus instanceof Textbox)
									((Textbox)receiveFocus).setFocus(true);
							}
						}
					});
				}
			});
		}

	}

	/**
	 * Permite enlazar los controles a la ventana de selecci�n de Itinerario
	 * @param button ha este Button se le adjuntara un listener con la llamada a la ventana de selecci�n de itinerario
	 * @see WndItinerario
	 */
	public static void enlazarItinerario(final Button button) {
		button.setTooltiptext("Seleccionar Itinerario");
		button.addEventListener(Events.ON_CLICK, new EventListener<Event>() {
			@Override
			public void onEvent(Event event) throws Exception {
				final WndSeleccionaItinerario oWndSeleccionaItinerario = new WndSeleccionaItinerario();
				boolean buscarVentanaParent = true;
				Component oComponent = button.getParent();
				while(buscarVentanaParent){
					 if(oComponent instanceof Window) {
						 oComponent.appendChild(oWndSeleccionaItinerario);
						 buscarVentanaParent = false;
					 }else{
					 	oComponent = oComponent.getParent();
					 }
				}

				oWndSeleccionaItinerario.setMode("modal");
				oWndSeleccionaItinerario.setVisible(true);
			}
		});
	}



	/**
	 * Permite cargar data al control Combobox
	 * la data a cargar no cuenta con un Pojo asociado
	 * @param combobox
	 * @param data
	 * @see UtilData#DATA_TIPO_INGRESO_LIQUIDACION
	 * @see UtilData#DATA_TIPO_OBJETO
	 */
	public static void cargarDataCombo(Combobox combobox, int data,Boolean todos) {
		switch (data) {
			case DATA_TIPO_INGRESO_LIQUIDACION:
				cargarTipoIngresoLiquidacion(combobox, todos);
				break;

			case DATA_TIPO_OBJETO:
				cargarTipoObjeto(combobox);
				break;

			default :
				Comboitem oComboitem = new Comboitem();

				oComboitem.setLabel("[Data no definida: " + data + "]");
				combobox.appendChild(oComboitem);
				break;
		}
	}

	/**
	 * Permite cargar data al control Combobox
	 * la data a cargar cuenta con un Pojo asociado
	 * @param combobox	: Objeto que contendra los datos.
	 * @param oClass	: Clase con la que se llenara los datos.
	 * @param bTodos	: Indica si el primer elemento del combo es la opcion TODOS (TRUE) o SELECCIONE (FALSE).
	 * @throws Exception
	 */
	public static void cargarDataCombo(Combobox combobox, Class<?> oClass, Boolean bTodos) throws Exception {
		if (oClass.equals(TipoFlota.class)){
			cargarTipoFlota(combobox, bTodos);
		}else if (oClass.equals(Servicio.class)){
			cargarServicio(combobox, bTodos);
		}else if (oClass.equals(TipoComprobante.class)) {
			cargarTipoComprobante(combobox, bTodos);
		}else if (oClass.equals(GrupoMantenimiento.class)){
			cargarGrupoMantenimiento(combobox, bTodos);
		}else if (oClass.equals(NumeroFlota.class)){
			cargarNumeroFlota(combobox, bTodos);
		}else if (oClass.equals(Agencia.class)) {
			cargarAgencia(combobox, bTodos );
		}else if (oClass.equals(FormaPago.class)) {
			cargarFormaPago(combobox, bTodos);
		}else if (oClass.equals(OperadorTarjetaCredito.class)) {
			cargarOperadorTarjetaCredito(combobox, bTodos);
		}else if (oClass.equals(Concesionario.class)) {
			cargarConcesionario(combobox, bTodos);
		}else if (oClass.equals(Localidad.class)) {
			cargarLocalidad(combobox, bTodos);
		}else if (oClass.equals(TipoAgencia.class)) {
			cargarTipoAgencia(combobox, bTodos);
		}else if (oClass.equals(Sexo.class)) {
			cargarSexo(combobox, bTodos);
		}else if (oClass.equals(Nacionalidad.class)) {
			cargarNacionalidad(combobox, bTodos);
		}else if (oClass.equals(EstadoCivil.class)) {
			cargarEstadoCivil(combobox, bTodos);
		}else if (oClass.equals(TipoDocumento.class)) {
			cargarTipoDocumento(combobox, bTodos);
		}else if (oClass.equals(TipoVia.class)) {
			cargarTipoVia(combobox, bTodos);
		}else if (oClass.equals(TipoZona.class)) {
			cargarTipoZona(combobox, bTodos);
		}else if (oClass.equals(TipoPersonal.class)) {
			cargarTipoPersonal(combobox, bTodos);
		}else if (oClass.equals(ObjetoBus.class)) {
			cargarObjetoBus(combobox);
		}	else if (oClass.equals(EstadoDocumentoBus.class)) {
			cargarEstadoDocumentoBus(combobox, bTodos);
		}else if (oClass.equals(Bus.class)) {
			cargarBus(combobox, bTodos);
		}else if (oClass.equals(TipoItinerario.class)){
			cargarTipoItinerario(combobox, bTodos);
		}else if(oClass.equals(PreferenciaAlimentaria.class)){
			cargarPreferenciaAlimentaria(combobox, bTodos);
		}else if (oClass.equals(UsuarioHardware.class)){
			cargarUsuarioHasrdware(combobox, bTodos,null);
		}else if (oClass.equals(CanalVenta.class)){
			cargarCanalVenta(combobox, bTodos);
		}else if (oClass.equals(Personal.class)){
			cargarPersonal(combobox, bTodos);
		}else if (oClass.equals(TipoFormaPago.class)){
			cargarTipoformaPago(combobox, bTodos);
		}else if (oClass.equals(MotivoCortesia.class)){
			cargarMotivoCortesia(combobox, bTodos);
		}else if (oClass.equals(Rol.class)){
			cargarRoles(combobox, bTodos);
		}else if (oClass.equals(Usuario.class)){
			cargarUsuarios(combobox, bTodos);
		}else if (oClass.equals(Ruta.class)){
			cargarRutas(combobox, bTodos);
		}else if (oClass.equals(OpcionMenu.class)){
			//cargarOpcionMenu(combobox, bTodos);
			cargarMenuPadre(combobox, bTodos);
		}else if (oClass.equals(TipoGasto.class)){
			cargarTipoGasto(combobox, bTodos);
		}else if(oClass.equals(TipoMovimiento.class)){
			cargarTipoMovimiento(combobox, bTodos);
		}else if(oClass.equals(AutorizadorCortesia.class)){
			cargarAutorizadorCortesia(combobox, bTodos);
		}else if(oClass.equals(TipoCobranza.class)){
			cargarTipoCobranza(combobox, bTodos);
		}else if(oClass.equals(UsuarioAprobador.class)){
			cargarUsuarioAprobadores(combobox, bTodos);
		}else if(oClass.equals(MotivoTemporadaAlta.class)){
			cargarMotivoTemporadaAlta(combobox, bTodos);
		}else if (oClass.equals(TipoMoneda.class)){
			cargarTipoMoneda(combobox, bTodos);
		}else if (oClass.equals(Empresa.class)){
			cargarEmpresa(combobox, bTodos);
		}else{
			Comboitem oComboitem = new Comboitem();

			oComboitem.setLabel("[Class no definido: " + oClass.getSimpleName() + "]");
			combobox.appendChild(oComboitem);
		}
	}

	/**
	 * Permite cargar data al control Combobox la data a cargar cuenta con un Pojo asociado
	 * @param combobox		: Objeto que contendra los datos.
	 * @param oClass		: Clase con la que se llenara los datos.
	 * @param bTodos		: Indica si el primer elemento del combo es la opcion TODOS (TRUE) o SELECCIONE (FALSE).
	 * @param parametros	: Indica los parametros con los cuales deseamos realizar la busqueda.
	 * @throws Exception
	 */
	public static void cargarDataCombo(Combobox combobox, Class<?> oClass, TreeMap<String, Object> parametros, Boolean bTodos) throws Exception {
		if(oClass.equals(TipoComprobante.class))
			cargarTipoComprobante(combobox, parametros, bTodos);
		else if(oClass.equals(TipoDocumento.class))
			cargarTipoDocumento(combobox, parametros, bTodos);
		else if(oClass.equals(FormaPago.class))
			cargarFormaPago(combobox, parametros, bTodos);
		else if(oClass.equals(TipoAgencia.class))
			cargarTipoAgencia(combobox, parametros, bTodos);
		else if(oClass.equals(Usuario.class))
			cargarUsuarios(combobox, parametros, bTodos);
		else if(oClass.equals(Agencia.class))
			cargarAgencia(combobox, bTodos, parametros, null);
		else if (oClass.equals(TipoCentroCosto.class))
			cargarTiposCentroCosto(combobox, parametros, bTodos);
		else if(oClass.equals(Servicio.class))
			cargarServicio(combobox, parametros, bTodos);
	}


	/**
	 * Carga los tipos de centro de costo
	 * @param combobox
	 * @param parametros
	 * @param todos
	 * @throws Exception
	 */
	private static void cargarTiposCentroCosto(Combobox combobox, TreeMap<String, Object> parametros, boolean todos)throws Exception{
		List<String> criteriosOrdenar=new ArrayList<>();
		criteriosOrdenar.add("denominacion");
		List<TipoCentroCosto>result=ServiceLocator.getTipoCentroCostoManager().buscarPorX(parametros, criteriosOrdenar);
		cargarGenericData(combobox, todos);
		for(TipoCentroCosto tipoCentroCosto:result){
			Comboitem comboitem=new Comboitem(tipoCentroCosto.getDenominacion());
			comboitem.setValue(tipoCentroCosto);

			combobox.appendChild(comboitem);
		}

		combobox.setSelectedIndex(0);
	}

	private static void cargarTipoItinerario(Combobox combobox, Boolean todos) {
		try{

			ArrayList<TipoItinerario> lsttipoItinerario = ServiceLocator.getTipoItinerarioManager().buscarPorEstadoRegistro(Constantes.VALUE_ACTIVO, "denominacion");

			cargarGenericData(combobox, todos);
			for (TipoItinerario otipoItinerario : lsttipoItinerario) {
				Comboitem oComboitem = new Comboitem();
				oComboitem.setValue(otipoItinerario);
				oComboitem.setLabel(otipoItinerario.getDenominacion());

				combobox.appendChild(oComboitem);
			}
		}catch(Exception ex){
			ex.printStackTrace();
		}
	}

	/**
	 *
	 * @param combobox
	 * @param todos
	 */
	private static void cargarTipoIngresoLiquidacion(Combobox combobox, Boolean todos) {
		cargarGenericData(combobox, todos);

		Comboitem oComboitem = new Comboitem("INGRESO");
		Comboitem oComboitem2 = new Comboitem("EGRESO");
		Comboitem oComboitem3 = new Comboitem("AMBOS");

		oComboitem.setValue(0);
		oComboitem2.setValue(1);
		oComboitem3.setValue(2);

		combobox.appendChild(oComboitem);
		combobox.appendChild(oComboitem2);
		combobox.appendChild(oComboitem3);
	}

	/**
	 * Tipos de estado del Bus.
	 * @param combobox
	 */
	public static void cargarTipoEstado(Combobox combobox) {
		cargarGenericData(combobox, false);

		Comboitem oComboitem = new Comboitem("INICIAL");
		Comboitem oComboitem2 = new Comboitem("TRANCITO");
		Comboitem oComboitem3 = new Comboitem("FINAL");

		oComboitem.setValue(0);
		oComboitem2.setValue(1);
		oComboitem3.setValue(2);

		combobox.appendChild(oComboitem);
		combobox.appendChild(oComboitem2);
		combobox.appendChild(oComboitem3);
	}


	/**
	 * Carga los rubros para el tipo de comprobante
	 * @param combobox
	 */
	public static void cargarRubroTipoComprobante(Combobox combobox, Boolean bTodos){
		cargarGenericData(combobox, bTodos);

		Comboitem oComboitem = new Comboitem("PASAJES");
		Comboitem oComboitem2 = new Comboitem("CARGA");
		Comboitem oComboitem3 = new Comboitem("AMBOS");

		oComboitem.setValue(1);
		oComboitem2.setValue(2);
		oComboitem3.setValue(3);

		combobox.appendChild(oComboitem);
		combobox.appendChild(oComboitem2);
		combobox.appendChild(oComboitem3);
	}

	/**
	 * Carga los rubros para el tipo de comprobante
	 * @param combobox
	 */
	public static void cargarRutaItinerario(Combobox combobox, Boolean bTodos){
		//cargarGenericData(combobox, bTodos);

		Comboitem oComboitem = new Comboitem("LIMA - PROVINCIAS");
		Comboitem oComboitem2 = new Comboitem("PROVINCIAS - LIMA");
		Comboitem oComboitem3 = new Comboitem("PROVINCIAS");

		oComboitem.setValue(0);
		oComboitem2.setValue(1);
		oComboitem3.setValue(2);

		combobox.appendChild(oComboitem);
		combobox.appendChild(oComboitem2);
		combobox.appendChild(oComboitem3);
	}


	public static void cargarTipoObjeto(Combobox combobox) {
		Comboitem oComboitem0 = new Comboitem("SELECCIONE");
		Comboitem oComboitem = new Comboitem("GENERICO");
		Comboitem oComboitem2 = new Comboitem("ASIENTO");

		oComboitem0.setValue(-1);
		oComboitem.setValue(0);
		oComboitem2.setValue(1);

		combobox.appendChild(oComboitem0);
		combobox.appendChild(oComboitem);
		combobox.appendChild(oComboitem2);
	}

	public static void cargarAlternativos(Combobox combobox) {

		Comboitem oComboitemTodos = new Comboitem("TODOS");
		Comboitem oComboitem = new Comboitem("SI");
		Comboitem oComboitem2 = new Comboitem("NO");

		oComboitemTodos.setValue(-1);
		oComboitem.setValue(1);
		oComboitem2.setValue(0);

		combobox.appendChild(oComboitemTodos);
		combobox.appendChild(oComboitem);
		combobox.appendChild(oComboitem2);

		combobox.setSelectedIndex(0);
	}


	private static void cargarTipoComprobante(Combobox combobox, boolean todos) {
		try{
			ArrayList<TipoComprobante> lstComprobantes = ServiceLocator.getTipoComprobanteManager().buscarPorEstadoRegistro(Constantes.VALUE_ACTIVO, "denominacion");
			cargarGenericData(combobox, todos);
			for (TipoComprobante oTipoComprobante : lstComprobantes) {
				Comboitem oComboitem = new Comboitem();

				oComboitem.setValue(oTipoComprobante);
				oComboitem.setLabel(oTipoComprobante.getDenominacion());

				combobox.appendChild(oComboitem);
			}
		}catch(Exception ex){
			ex.printStackTrace();
		}
	}

	public static void cargarTipoComprobante(Combobox combobox, TreeMap<String, Object> parametros, Boolean todos) {
		try{
			ArrayList<String> criteriosOrdenar = new ArrayList<>();
			criteriosOrdenar.add("denominacion");
			ArrayList<TipoComprobante> lstComprobantes = ServiceLocator.getTipoComprobanteManager().buscarPorX(parametros, criteriosOrdenar);
			if(todos!=null)
				cargarGenericData(combobox, todos);
			for (TipoComprobante oTipoComprobante : lstComprobantes) {
				Comboitem oComboitem = new Comboitem();

				oComboitem.setValue(oTipoComprobante);
				oComboitem.setLabel(oTipoComprobante.getDenominacion());

				combobox.appendChild(oComboitem);
			}
		}catch(Exception ex){
			ex.printStackTrace();
		}
	}

	public static void cargarTipoComprobanteAgencia(Combobox combobox, Agencia agencia, boolean todos) {
		try{
			TreeMap<String, Object>parametros = new TreeMap<>();
			parametros.put("agencia", agencia);
			parametros.put("estadoRegistro", Constantes.VALUE_ACTIVO);

			ArrayList<EspecieValorada> lstEspecieValorada = ServiceLocator.getEspecieValoradaManager().buscarPorX(parametros, null);
			cargarGenericData(combobox, todos);

			for(EspecieValorada especieValorada: lstEspecieValorada){
				Comboitem oComboitem = new Comboitem();

					boolean flag=false; //para evitar la duplicidad de las agencias.
					for(int y=0; y<combobox.getItems().size(); y++){
						combobox.setSelectedIndex(y);
						if(combobox.getSelectedItem().getValue() instanceof TipoComprobante)
							if (((TipoComprobante)combobox.getSelectedItem().getValue()).getId()==especieValorada.getTipoComprobante().getId()){
								flag=true;
								break;
							}
					}

					if(!(flag)){
						/*carga el combo tipo Comprobante*/
						oComboitem.setValue(especieValorada.getTipoComprobante());
						oComboitem.setLabel(especieValorada.getTipoComprobante().getDenominacion());
						combobox.appendChild(oComboitem);
					}
			}
		}catch(Exception ex){
			ex.printStackTrace();
		}
	}

	public static void cargarTipoComprobanteSunat(Combobox combobox, boolean todos) {
		try{
			TreeMap<String, Object>parametros = new TreeMap<>();
			parametros.put("estadoRegistro", Constantes.VALUE_ACTIVO);

			ArrayList<String> criteriosOrdenar = new ArrayList<>();
			criteriosOrdenar.add("denominacion");
			ArrayList<TipoComprobante> lstComprobantes = ServiceLocator.getTipoComprobanteManager().buscarPorX(parametros, criteriosOrdenar);
			cargarGenericData(combobox, todos);
			for (TipoComprobante oTipoComprobante : lstComprobantes) {
				if(oTipoComprobante.getId()==Constantes.ID_TIPCOM_BOLETA_VENTA || oTipoComprobante.getId()==Constantes.ID_TIPCOM_FACTURA ||
						oTipoComprobante.getId()==Constantes.ID_TIPCOM_NOTA_CREDITO || oTipoComprobante.getId()==Constantes.ID_TIPCOM_NOTA_DEBITO) {
					Comboitem oComboitem = new Comboitem();

					oComboitem.setValue(oTipoComprobante);
					oComboitem.setLabel(oTipoComprobante.getDenominacion());

					combobox.appendChild(oComboitem);
				}
			}
		}catch(Exception ex){
			ex.printStackTrace();
		}
	}

	private static void cargarTipoFlota(Combobox combobox, Boolean todos) {
		try{
			ArrayList<TipoFlota> lstFlota = ServiceLocator.getTipoFlotaManager().buscarPorEstadoRegistro(Constantes.VALUE_ACTIVO, "denominacion");

			cargarGenericData(combobox, todos);
			for (TipoFlota oTipoFlota : lstFlota) {
				Comboitem oComboitem = new Comboitem();
				String datosTipoFlota = oTipoFlota.getDenominacion();
				oComboitem.setValue(oTipoFlota);
				oComboitem.setLabel(datosTipoFlota);
				combobox.appendChild(oComboitem);
			}
		}catch(Exception ex){
			ex.printStackTrace();
		}
	}

	private static void cargarServicio(Combobox combobox, TreeMap<String, Object> parametros, Boolean todos) {
		try{
			combobox.getItems().clear();
			List<String> criteriosOrdenar=new ArrayList<>();
			criteriosOrdenar.add("denominacion");
			ArrayList<Servicio> lsServicio = ServiceLocator.getServicioManager().buscarPorX(parametros, criteriosOrdenar);

			cargarGenericData(combobox, todos);
			for (Servicio oServicio : lsServicio) {
				Comboitem oComboitem = new Comboitem();
				String datosServicio = oServicio.getDenominacion();
				oComboitem.setValue(oServicio);
				oComboitem.setLabel(datosServicio);
				combobox.appendChild(oComboitem);
			}
		}catch(Exception ex){
			ex.printStackTrace();
		}
	}
	
	private static void cargarServicio(Combobox combobox, Boolean todos) {
		try{
			ArrayList<Servicio> lsServicio = ServiceLocator.getServicioManager().buscarPorEstadoRegistro(Constantes.VALUE_ACTIVO, "denominacion");

			cargarGenericData(combobox, todos);
			for (Servicio oServicio : lsServicio) {
				Comboitem oComboitem = new Comboitem();
				String datosServicio = oServicio.getDenominacion();
				oComboitem.setValue(oServicio);
				oComboitem.setLabel(datosServicio);
				combobox.appendChild(oComboitem);
			}
		}catch(Exception ex){
			ex.printStackTrace();
		}
	}

	private static void cargarGrupoMantenimiento (Combobox combobox, Boolean todos){
		try{
			ArrayList<GrupoMantenimiento> lsGrupoMantenimiento = ServiceLocator.getGrupoMantenimientoManager().buscarPorEstadoRegistro(Constantes.VALUE_ACTIVO, "denominacion");

			cargarGenericData(combobox, todos);
			for (GrupoMantenimiento oGrupoMantenimiento : lsGrupoMantenimiento) {
				Comboitem oComboitem = new Comboitem();
				String datosGrupoMantenimiento = oGrupoMantenimiento.getDenominacion();
				oComboitem.setValue(oGrupoMantenimiento);
				oComboitem.setLabel(datosGrupoMantenimiento);
				combobox.appendChild(oComboitem);
			}
		}catch(Exception ex){
			ex.printStackTrace();
		}
	}

	private static void cargarNumeroFlota (Combobox combobox, Boolean todos){
		try{
			ArrayList<NumeroFlota> lsNumeroFlota = ServiceLocator.getNumeroFlotaManager().buscarPorEstadoRegistro(Constantes.VALUE_ACTIVO, "id");

			cargarGenericData(combobox, todos);
			for (NumeroFlota oNumeroFlota : lsNumeroFlota) {
				Comboitem oComboitem = new Comboitem();
				String datosNumeroFlota = oNumeroFlota.getDenominacion();

				oComboitem.setValue(oNumeroFlota);
				oComboitem.setLabel(datosNumeroFlota);

				combobox.appendChild(oComboitem);
			}
		}catch(Exception ex){
			ex.printStackTrace();
		}
	}


	private static void cargarAgencia(Combobox combobox, Boolean todos) {
		try{
			combobox.getItems().clear();
			ArrayList<Agencia> lstAgencias = ServiceLocator.getAgenciaManager().buscarPorEstadoRegistro(Constantes.VALUE_ACTIVO, "denominacion");

			cargarGenericData(combobox, todos);
			for (Agencia oAgencia : lstAgencias) {
				Comboitem oComboitem = new Comboitem();
				//String datosAgencia = oAgencia.getTipoAgencia().getDenominacion() + " - " + oAgencia.getLocalidad().getDenominacion() + " - " + oAgencia.getDenominacion();
				String datosAgencia =oAgencia.getDenominacion();

				oComboitem.setValue(oAgencia);
				oComboitem.setLabel(datosAgencia);

				combobox.appendChild(oComboitem);
			}

		}catch(Exception ex){
			ex.printStackTrace();
		}
	}

	/**
	 * Carga Agencias y selecciona la agencia por defecto. Si el usuario esta asociado a una agencia, esta sera seleccioada por defecto, si no
	 * sera la agencia que esta asociada al usuario hardware.
	 * @param combobox 		: Control Combobox
	 * @param idTipoAgenica : Identificador del tipo de agencia
	 * @throws Exception
	 */
	public static void cargarAgenciaXtipoAgencia(Combobox combobox,Integer idTipoAgenica,Boolean todos) throws Exception{
		Agencia agenciaXHardware=(Agencia) Executions.getCurrent().getDesktop().getSession().getAttribute(Constantes.ATRIBUTO_AGENCIA);
		Usuario usuarioS=(Usuario) Executions.getCurrent().getDesktop().getSession().getAttribute(Constantes.ATRIBUTO_USUARIO);

		Usuario usuario=ServiceLocator.getUsuarioManager().buscarXId(usuarioS.getId().longValue());

		TreeMap<String, Object> criteriosBusqueda= new  TreeMap<>();
		ArrayList<String> criteriosOrdenar = new ArrayList<>();
		criteriosOrdenar.add("denominacion");
		if(idTipoAgenica!=null){
			criteriosBusqueda.put("tipoAgencia",new TipoAgencia(idTipoAgenica));
		}
		criteriosBusqueda.put("estadoRegistro", Constantes.VALUE_ACTIVO);
		UtilData.cargarAgencia(combobox, todos, criteriosBusqueda, criteriosOrdenar);

		if(usuario.getAgencia()==null)
			Util.seleccionarValorItemCombo(Agencia.class, combobox, agenciaXHardware.getId());
		else
			Util.seleccionarValorItemCombo(Agencia.class, combobox, usuario.getAgencia().getId());
	}



	public static void cargarAgencia(Combobox combobox, Boolean todos, TreeMap<String, Object> criteriosBusqueda, List<String> criteriosOrdenar) {
		try{
			combobox.getItems().clear();
			ArrayList<Agencia> lstAgencias = ServiceLocator.getAgenciaManager().buscarPorX(criteriosBusqueda, criteriosOrdenar);

			cargarGenericData(combobox, todos);
			for (Agencia agencia : lstAgencias) {
				Comboitem oComboitem = new Comboitem();
				oComboitem.setLabel(agencia.getDenominacion());
				oComboitem.setValue(agencia);
				combobox.appendChild(oComboitem);
			}
			if(combobox.getItems().size()>0)
				combobox.setSelectedIndex(0);
		}catch(Exception ex){
			ex.printStackTrace();
		}
	}

	public static void cargarAgencia(String campo,  Object[] criterios, List<String> criteriosOrdenar,Combobox combobox, Boolean todos) {
		try{
			List<Agencia> lstAgencias = ServiceLocator.getAgenciaManager().buscarPorX(campo, criterios, criteriosOrdenar, Constantes.VALUE_ACTIVO);

			cargarGenericData(combobox, todos);
			for (Agencia agencia : lstAgencias) {
				Comboitem oComboitem = new Comboitem();
				oComboitem.setLabel(agencia.getDenominacion());
				oComboitem.setValue(agencia);
				combobox.appendChild(oComboitem);
			}
			if(combobox.getItems().size()>0)
				combobox.setSelectedIndex(0);
		}catch(Exception ex){
			ex.printStackTrace();
		}
	}

	private static void cargarFormaPago(Combobox combobox, Boolean todos) {
		try{
			ArrayList<FormaPago> lstConcesionarios = ServiceLocator.getFormaPagoManager().buscarPorEstadoRegistro(Constantes.VALUE_ACTIVO, "denominacion");

			cargarGenericData(combobox, todos);
			for (FormaPago oFormaPago : lstConcesionarios) {
				Comboitem oComboitem = new Comboitem();

				oComboitem.setValue(oFormaPago);
				oComboitem.setLabel(oFormaPago.getDenominacion());

				combobox.appendChild(oComboitem);
			}
		}catch(Exception ex){
			ex.printStackTrace();
		}
	}

	public static void cargarFormaPago(Combobox combobox, TreeMap<String, Object> parametros, boolean todos) {
		try{
			ArrayList<String> criteriosOrdenar = new ArrayList<>();
			criteriosOrdenar.add("denominacion");
			ArrayList<FormaPago> lstFormaPago = ServiceLocator.getFormaPagoManager().buscarPorX(parametros, criteriosOrdenar);
			cargarGenericData(combobox, todos);
			for (FormaPago oFormaPago : lstFormaPago) {
				Comboitem oComboitem = new Comboitem();
				oComboitem.setValue(oFormaPago);
				oComboitem.setLabel(oFormaPago.getDenominacion());
				combobox.appendChild(oComboitem);
			}
		}catch(Exception ex){
			ex.printStackTrace();
		}
	}

	private static void cargarOperadorTarjetaCredito(Combobox combobox, Boolean todos) {
		try{
			ArrayList<OperadorTarjetaCredito> lstOperadoresTarjetaCredito = ServiceLocator.getOperadorTarjetaCreditoManager().buscarPorEstadoRegistro(Constantes.VALUE_ACTIVO, "denominacion");
			if(todos!=null)
				cargarGenericData(combobox, todos);
			for (OperadorTarjetaCredito oOperadorTarjetaCredito : lstOperadoresTarjetaCredito) {
				Comboitem oComboitem = new Comboitem();

				oComboitem.setValue(oOperadorTarjetaCredito);
				oComboitem.setLabel(oOperadorTarjetaCredito.getDenominacion());

				combobox.appendChild(oComboitem);
			}
		}catch(Exception ex){
			ex.printStackTrace();
		}
	}

	private static void cargarConcesionario(Combobox combobox, Boolean todos) {
		try{
			combobox.getItems().clear();
			ArrayList<Concesionario> lstConcesionarios = ServiceLocator.getConcesionarioManager().buscarPorEstadoRegistro(Constantes.VALUE_ACTIVO, "razonSocial");

			cargarGenericData(combobox, todos);
			for (Concesionario oConcesionario : lstConcesionarios) {
				Comboitem oComboitem = new Comboitem();

				oComboitem.setValue(oConcesionario);
				oComboitem.setLabel(oConcesionario.getRazonSocial());

				combobox.appendChild(oComboitem);
			}
		}catch(Exception ex){
			ex.printStackTrace();
		}
	}

	/**
	 * Realiza el llenado de las localidades
	 * @param combobox	: Objeto que contendra los datos.
	 * @param todos		: Indica si el primer elemento del combo es la opcion SELECCIONE o TODOS.
	 */
	private static void cargarLocalidad(Combobox combobox, boolean todos) {
		try{
			ArrayList<Localidad> lstLocalidades = ServiceLocator.getLocalidadManager().buscarPorEstadoRegistro(Constantes.VALUE_ACTIVO, "denominacion");
			Comboitem oComboitem = null;
			cargarGenericData(combobox, todos);

			for (Localidad oLocalidad : lstLocalidades) {
				oComboitem = new Comboitem();
				oComboitem.setLabel(oLocalidad.getDenominacion());

				oComboitem.setValue(oLocalidad);
				combobox.appendChild(oComboitem);
			}
		}catch(Exception ex){
			ex.printStackTrace();
		}
	}

	private static void cargarTipoAgencia(Combobox combobox, TreeMap<String, Object> parametros, boolean todos) {
		try{
			ArrayList<String> criteriosOrdenar = new ArrayList<>();
			criteriosOrdenar.add("denominacion");
			ArrayList<TipoAgencia> lstTipoAgencias = ServiceLocator.getTipoAgenciaManager().buscarPorX(parametros, criteriosOrdenar);

			cargarGenericData(combobox, todos);
			for (TipoAgencia oTipoAgencia : lstTipoAgencias) {
				Comboitem oComboitem = new Comboitem();

				oComboitem.setValue(oTipoAgencia);
				oComboitem.setLabel(oTipoAgencia.getDenominacion());

				combobox.appendChild(oComboitem);
			}
		}catch(Exception ex){
			ex.printStackTrace();
		}
	}

	private static void cargarTipoAgencia(Combobox combobox, boolean todos) {
		try{
			combobox.getItems().clear();
			ArrayList<TipoAgencia> lstTipoAgencias = ServiceLocator.getTipoAgenciaManager().buscarPorEstadoRegistro(Constantes.VALUE_ACTIVO, "denominacion");

			cargarGenericData(combobox, todos);
			for (TipoAgencia oTipoAgencia : lstTipoAgencias) {
				Comboitem oComboitem = new Comboitem();

				oComboitem.setValue(oTipoAgencia);
				oComboitem.setLabel(oTipoAgencia.getDenominacion());

				combobox.appendChild(oComboitem);
			}
		}catch(Exception ex){
			ex.printStackTrace();
		}
	}

	private static void cargarSexo(Combobox combobox, boolean todos) {
		try{
			ArrayList<Sexo> lstSexos = ServiceLocator.getSexoManager().buscarPorEstadoRegistro(Constantes.VALUE_ACTIVO, "denominacion");
			Comboitem oComboitem = null;
			cargarGenericData(combobox, todos);
			for (Sexo oSexo : lstSexos) {
				oComboitem = new Comboitem();
				oComboitem.setValue(oSexo);

				oComboitem.setLabel(oSexo.getDenominacion());
				combobox.appendChild(oComboitem);
			}
		}catch(Exception ex){
			ex.printStackTrace();
		}
	}

	private static void cargarNacionalidad(Combobox combobox, Boolean todos) {
		try{
			ArrayList<Nacionalidad> lstNacionalidades = ServiceLocator.getNacionalidadManager().buscarPorEstadoRegistro(Constantes.VALUE_ACTIVO, "denominacion");

			cargarGenericData(combobox, todos);
			for (Nacionalidad oNacionalidad : lstNacionalidades) {
				Comboitem oComboitem = new Comboitem();

				oComboitem.setValue(oNacionalidad);
				oComboitem.setLabel(oNacionalidad.getDenominacion());

				combobox.appendChild(oComboitem);
			}
		}catch(Exception ex){
			ex.printStackTrace();
		}
	}

	private static void cargarEstadoCivil(Combobox combobox, boolean todos) {
		try{
			ArrayList<EstadoCivil> lstEstadosCiviles = ServiceLocator.getEstadoCivilManager().buscarPorEstadoRegistro(Constantes.VALUE_ACTIVO, "denominacion");
			Comboitem oComboitem = null;
			cargarGenericData(combobox, todos);
			for (EstadoCivil oEstadoCivil : lstEstadosCiviles) {
				oComboitem = new Comboitem();
				oComboitem.setValue(oEstadoCivil);

				oComboitem.setLabel(oEstadoCivil.getDenominacion());
				combobox.appendChild(oComboitem);
			}
		}catch(Exception ex){
			ex.printStackTrace();
		}
	}

	private static void cargarTipoDocumento(Combobox combobox, boolean todos) {
		try{
			ArrayList<TipoDocumento> lstTiposDocumento = ServiceLocator.getTipoDocumentoManager().buscarPorEstadoRegistro(Constantes.VALUE_ACTIVO, "denominacion");
			cargarGenericData(combobox, todos);
			for (TipoDocumento oTipoDocumento : lstTiposDocumento) {
				Comboitem oComboitem = new Comboitem();


				oComboitem.setValue(oTipoDocumento);

				oComboitem.setLabel(oTipoDocumento.getDenominacion());
				combobox.appendChild(oComboitem);
			}
		}catch(Exception ex){
			ex.printStackTrace();
		}
	}

	private static void cargarTipoDocumento(Combobox combobox, TreeMap<String, Object> parametros, Boolean todos) {
		try{
			ArrayList<String> criteriosOrdenar = new ArrayList<>();
			criteriosOrdenar.add("denominacion");
			ArrayList<TipoDocumento> lstTiposDocumento = ServiceLocator.getTipoDocumentoManager().buscarPorX(parametros, criteriosOrdenar);
			if(todos!=null)
				cargarGenericData(combobox, todos);
			for (TipoDocumento oTipoDocumento : lstTiposDocumento) {
				Comboitem oComboitem = new Comboitem();


				oComboitem.setValue(oTipoDocumento);

				oComboitem.setLabel(oTipoDocumento.getDenominacion());
				combobox.appendChild(oComboitem);
			}
		}catch(Exception ex){
			ex.printStackTrace();
		}
	}

	private static void cargarTipoVia(Combobox combobox, Boolean todos) {
		try{
			ArrayList<TipoVia> lstTiposVia = ServiceLocator.getTipoViaManager().buscarPorEstadoRegistro(Constantes.VALUE_ACTIVO, "denominacion");

			cargarGenericData(combobox, todos);
			for (TipoVia oTipoVia : lstTiposVia) {
				Comboitem oComboitem = new Comboitem();

				oComboitem.setValue(oTipoVia);
				oComboitem.setLabel(oTipoVia.getDenominacion());

				combobox.appendChild(oComboitem);
			}
		}catch(Exception ex){
			ex.printStackTrace();
		}
	}

	private static void cargarTipoZona(Combobox combobox, Boolean todos) {
		try{
			ArrayList<TipoZona> lstTiposZona = ServiceLocator.getTipoZonaManager().buscarPorEstadoRegistro(Constantes.VALUE_ACTIVO, "denominacion");

			cargarGenericData(combobox, todos);
			for (TipoZona oTipoZona : lstTiposZona) {
				Comboitem oComboitem = new Comboitem();

				oComboitem.setValue(oTipoZona);
				oComboitem.setLabel(oTipoZona.getDenominacion());

				combobox.appendChild(oComboitem);
			}
		}catch(Exception ex){
			ex.printStackTrace();
		}
	}

	private static void cargarTipoPersonal(Combobox combobox, Boolean todos) {
		try{
			ArrayList<TipoPersonal> lstTiposPersonal = ServiceLocator.getTipoPersonalManager().buscarPorEstadoRegistro(Constantes.VALUE_ACTIVO, "denominacion");

			cargarGenericData(combobox, todos);
			for (TipoPersonal oTipoPersonal : lstTiposPersonal) {
				Comboitem oComboitem = new Comboitem();

				oComboitem.setValue(oTipoPersonal);
				oComboitem.setLabel(oTipoPersonal.getDenominacion());

				combobox.appendChild(oComboitem);
			}
		}catch(Exception ex){
			ex.printStackTrace();
		}
	}

	public static void cargarTipoPrecio(Combobox combobox, Boolean todos) {
		try{
			ArrayList<TipoPrecio> lstTiposPrecio = ServiceLocator.getTipoPrecioManager().buscarPorEstadoRegistro(Constantes.VALUE_ACTIVO, "denominacion");

			cargarGenericData(combobox, todos);
			for (TipoPrecio oTipoPrecio : lstTiposPrecio) {
				Comboitem oComboitem = new Comboitem();

				oComboitem.setValue(oTipoPrecio);
				oComboitem.setLabel(oTipoPrecio.getDenominacion());

				combobox.appendChild(oComboitem);
			}
		}catch(Exception ex){
			ex.printStackTrace();
		}
	}

	private static void cargarObjetoBus(Combobox combobox) {
		try{
			ArrayList<ObjetoBus> lstObjetosBus = ServiceLocator.getObjetoBusManager().buscarPorEstadoRegistro(Constantes.VALUE_ACTIVO, "denominacion");

			for (ObjetoBus oObjetoBus : lstObjetosBus) {
				Comboitem oComboitem = new Comboitem();

				oComboitem.setValue(oObjetoBus);
				oComboitem.setLabel(oObjetoBus.getDenominacion());

				combobox.appendChild(oComboitem);
			}
		}catch(Exception ex){
			ex.printStackTrace();
		}
	}

	private static void cargarEstadoDocumentoBus(Combobox combobox, Boolean todos) {
		try{
			ArrayList<EstadoDocumentoBus> lstEstadoDocumentoBus = ServiceLocator.getEstadoDocumentoBusManager().buscarPorEstadoRegistro(Constantes.VALUE_ACTIVO, "denominacion");

			cargarGenericData(combobox, todos);
			for (EstadoDocumentoBus oestadoDocumentoBus : lstEstadoDocumentoBus) {
				Comboitem oComboitem = new Comboitem();

				oComboitem.setValue(oestadoDocumentoBus);
				oComboitem.setLabel(oestadoDocumentoBus.getDenominacion());

				combobox.appendChild(oComboitem);
			}
		}catch(Exception ex){
			ex.printStackTrace();
		}
	}

	private static void cargarBus(Combobox combobox, Boolean todos) {
		try{
			ArrayList<Bus> lstBus = ServiceLocator.getBusManager().buscarPorEstadoRegistro(Constantes.VALUE_ACTIVO, "codigo");

			cargarGenericData(combobox, todos);
			for (Bus oBus : lstBus) {
				Comboitem oComboitem = new Comboitem();

				oComboitem.setValue(oBus);
				oComboitem.setLabel(oBus.getCodigo());

				combobox.appendChild(oComboitem);
			}
		}catch(Exception ex){
			ex.printStackTrace();
		}
	}

//	public static void cargarTipoDocumento(Combobox combobox, Boolean documentacionBus, Boolean todos) {
//		try{
//			TreeMap<String, Object> criterioBusqueda = new TreeMap<String, Object>();
//			List<String> criterioOrdena = null;
//			criterioOrdena = new ArrayList<String>();
//			criterioOrdena.add("denominacion");
//
//			String estadoRegistro = Constantes.ACTIVO;
//			Boolean tipo = documentacionBus;
//			criterioBusqueda.put("tipo", tipo);
//			criterioBusqueda.put("estadoRegistro", estadoRegistro);
//			ArrayList<TipoDocumento> lstTipoDocumento = (ServiceLocator.getTipoDocumentoManager().buscarPorX(criterioBusqueda, criterioOrdena));
//
//			cargarGenericData(combobox, todos);
//			for (int l = 0; l < lstTipoDocumento.size(); l ++) {
//				TipoDocumento oTipoDocumento = lstTipoDocumento.get(l);
//				Comboitem oComboitem = new Comboitem();
//
//				oComboitem.setValue(oTipoDocumento);
//
//				oComboitem.setLabel(oTipoDocumento.getDenominacion());
//				combobox.appendChild(oComboitem);
//
//			}
//		}catch(Exception ex){
//			ex.printStackTrace();
//		}
//	}


	/**
	 * Realiza la asignaci�n del primer elemento al Combobox enviado como parametro.
	 * @param combo		: Objeto al cual se le asignara el Comboitem.
	 * @param criterio	: Indica si el primer item sera la opcion TODOS (TRUE) o SELECCIONE (FALSE).
	 */
	public static void cargarGenericData(Combobox combo, boolean criterio){
		Comboitem oComboitem = new Comboitem();
		if(criterio)
			oComboitem.setLabel(Constantes.COMBO_LABEL_TODOS);
		else
			oComboitem.setLabel(Constantes.COMBO_LABEL_SELECCIONE);

		combo.appendChild(oComboitem);
		combo.setSelectedIndex(0);
	}

//	/**
//	 * Recupera las rutas, seg�n la localidad seleccionada .
//	 * @param combo		: Objeto al cual se le asignara el Comboitem.
//	 * @param id		: Indica el ID de la Localidad.
//	 * @param puntos	: si es null, carga todas la rutas que correcsponden al parametro "id",
//	 * 					  caso contrario solo carga aquellas rutas cuyo puntaje sean menor o igual al parametro "puntos"
//	 */
//	public static void cargarRutas(Combobox combobox, boolean todos, Integer id, Integer puntos, Integer idEmpresa) {
//		cargarRutas(combobox, todos, id, puntos, idEmpresa);
//	}


	/**
	 * Realiza la busqueda de las rutas
	 * @param combobox : Combobox en conde se van a mostrar las rutas
	 * @param todos	: true=TODOS; false=SELECCIONE
	 * @param id : Identificador de la localidad
	 * @param puntos : si es null, carga todas la rutas que correcsponden al parametro "id",
	 * 				   caso contrario solo carga aquellas rutas cuyo puntaje sean menor o igual al parametro "puntos"
	 * @param ocultarRutasPool : True valira: False no validar.
	 */
	public static void cargarRutas(Combobox combobox, boolean todos, Integer idOrigen, Integer puntos, Integer idEmpresa) {
		try{
			TreeMap<String, Object> criteriosBusqueda = new TreeMap<>();
			List<String> criteriosOrdenar = null;
			criteriosOrdenar = new ArrayList<>();
			criteriosOrdenar.add("destino");

			Localidad olocalida = new Localidad();
			olocalida.setId(idOrigen);
			criteriosBusqueda.put("localidadOrigen", olocalida);
			Empresa oEmpresa = new Empresa();
			oEmpresa.setId(idEmpresa);
			criteriosBusqueda.put("empresa", oEmpresa);
			criteriosBusqueda.put("estadoRegistro", Constantes.VALUE_ACTIVO);
			ArrayList<Ruta> lstRuta = ServiceLocator.getRutaManager().buscarPorX(criteriosBusqueda, criteriosOrdenar);

			UtilData.cargarGenericData(combobox, todos);
			for (Ruta oRuta : lstRuta) {
				//boolean addRuta=true;
				//if(addRuta){
					if(puntos!=null){
						if(oRuta.getPuntaje()!=null && oRuta.getPuntaje()>0 &&  puntos >= oRuta.getPuntaje()){
							Comboitem oComboitem = new Comboitem();
							oComboitem.setValue(oRuta);
							oComboitem.setLabel(oRuta.toString());
							combobox.appendChild(oComboitem);
						}
					}else{
						Comboitem oComboitem = new Comboitem();
						oComboitem.setValue(oRuta);
						oComboitem.setLabel(oRuta.toString());
						combobox.appendChild(oComboitem);
					}
				//}
			}
		}catch(Exception ex){
			ex.printStackTrace();
		}
	}

	public static void cargarUsuarioHasrdware(Combobox combobox, Boolean todos, Agencia agencia) {
		try{
			combobox.getItems().clear();

			ArrayList<UsuarioHardware> lstUsuarioHardware= new ArrayList<>();
			if(agencia ==null){
				lstUsuarioHardware = ServiceLocator.getUsuarioHardwareManager().buscarPorEstadoRegistro(Constantes.VALUE_ACTIVO, "descripcion");
			}
			else{
				TreeMap<String, Object> criteriosBusqueda = new TreeMap<>();
				criteriosBusqueda.put("agencia", agencia);
				criteriosBusqueda.put("estadoRegistro", Constantes.VALUE_ACTIVO);
				lstUsuarioHardware=ServiceLocator.getUsuarioHardwareManager().buscarPorX(criteriosBusqueda, null);
			}
			cargarGenericData(combobox, todos);
			for (UsuarioHardware usuarioHardware : lstUsuarioHardware) {
				Comboitem oComboitem = new Comboitem();
				oComboitem.setLabel(usuarioHardware.getDescripcion());
				oComboitem.setValue(usuarioHardware);
				combobox.appendChild(oComboitem);
			}
		}catch(Exception ex){
			ex.printStackTrace();
		}
	}

	public static void cargarTipoMoneda(Combobox combobox, Boolean todos) {
		try{
			combobox.getItems().clear();
			ArrayList<TipoMoneda> lstTiposMoneda=ServiceLocator.getTipoMonedaManager().buscarPorEstadoRegistro(Constantes.VALUE_ACTIVO, "denominacion");

			cargarGenericData(combobox, todos);
			for (TipoMoneda tipoMoneda : lstTiposMoneda) {
				Comboitem oComboitem = new Comboitem();
				oComboitem.setLabel(tipoMoneda.getDenominacion());
				oComboitem.setValue(tipoMoneda);
				combobox.appendChild(oComboitem);
			}
		}catch(Exception ex){
			ex.printStackTrace();
		}
	}

	public static void cargarEmpresa(Combobox combobox, Boolean todos) {
		try{
			combobox.getItems().clear();
			ArrayList<Empresa> lstTiposMoneda=ServiceLocator.getEmpresaManager().buscarPorEstadoRegistro(Constantes.VALUE_ACTIVO, "razonSocial");

			cargarGenericData(combobox, todos);
			for (Empresa empresa : lstTiposMoneda) {
				Comboitem oComboitem = new Comboitem();
				oComboitem.setLabel(empresa.getNombreCorto());
				oComboitem.setValue(empresa);
				combobox.appendChild(oComboitem);
			}
		}catch(Exception ex){
			ex.printStackTrace();
		}
	}


	private static void cargarPreferenciaAlimentaria(Combobox combobox, Boolean todos) {
		try{
			ArrayList<PreferenciaAlimentaria> lstPreferenciaAlimentaria = ServiceLocator.getPreferenciaAlimentariaManager().buscarPorEstadoRegistro(Constantes.VALUE_ACTIVO, "denominacion");

			cargarGenericData(combobox, todos);
			for (PreferenciaAlimentaria oPreferenciaAlimentaria : lstPreferenciaAlimentaria) {
				Comboitem oComboitem = new Comboitem();
				oComboitem.setLabel(oPreferenciaAlimentaria.getDenominacion());
				oComboitem.setValue(oPreferenciaAlimentaria);
				combobox.appendChild(oComboitem);
			}
		}catch(Exception ex){
			ex.printStackTrace();
		}
	}

	public static void cargarPersonalXtipo (Combobox combobox, Boolean todos, Integer idTipoPersonal) throws Exception{
		TreeMap<String, Object> criteriosBusqueda = new TreeMap<>();
		List<String> criteriosOrdenar = new ArrayList<>();
		TipoPersonal tipoPersonal = new TipoPersonal();
		criteriosOrdenar.add("apellidoPaterno");

		if (idTipoPersonal ==null) {
			criteriosBusqueda.remove("tipoPersonal");
		}else {
			tipoPersonal.setId(idTipoPersonal);
			criteriosBusqueda.put("tipoPersonal", tipoPersonal );
		}
		criteriosBusqueda.put("estadoRegistro", Constantes.VALUE_ACTIVO);
		ArrayList<Personal> lstPersonal = ServiceLocator.getPersonalManager().buscarPorX(criteriosBusqueda, criteriosOrdenar);

		//cargarGenericData(combobox, todos);

		for (Personal personal : lstPersonal) {
			Comboitem oComboitem = new Comboitem();
			oComboitem.setLabel(personal.getApellidoPaterno() +" "+  personal.getApellidoMaterno() + ", " + personal.getNombre());
			oComboitem.setValue(personal);
			combobox.appendChild(oComboitem);
		}
	}


	private static void cargarCanalVenta(Combobox combobox, Boolean todos) {
		try{
			ArrayList<CanalVenta> lstCanalVenta = ServiceLocator.getCanalVentaManager().buscarPorEstadoRegistro(Constantes.VALUE_ACTIVO, "denominacion");

			cargarGenericData(combobox, todos);
			for (CanalVenta canalVenta : lstCanalVenta) {
				Comboitem oComboitem = new Comboitem();
				oComboitem.setLabel(canalVenta.getDenominacion());
				oComboitem.setValue(canalVenta);
				combobox.appendChild(oComboitem);
			}
		}catch(Exception ex){
			ex.printStackTrace();
		}
	}


	public static void cargarPersonal (Combobox combobox, Boolean todos) throws Exception{
		ArrayList<Personal> lstPersonal = ServiceLocator.getPersonalManager().buscarPorEstadoRegistro(Constantes.VALUE_ACTIVO, "apellidoPaterno");

		if(todos)
			cargarGenericData(combobox, todos);

		for (Personal personal : lstPersonal) {
			Comboitem oComboitem = new Comboitem();
			if(personal.getApellidoMaterno()!=null)
				oComboitem.setLabel(personal.getApellidoPaterno() +" "+  personal.getApellidoMaterno() + ", " + personal.getNombre());
			else
				oComboitem.setLabel(personal.getApellidoPaterno() +" "+  " " + ", " + personal.getNombre());
			oComboitem.setValue(personal);
			combobox.appendChild(oComboitem);
		}
	}

	public static void cargarTipoformaPago (Combobox combobox, Boolean todos) throws Exception{
		ArrayList<TipoFormaPago> lstTipoFormaPago = ServiceLocator.getTipoFormaPagoManager().buscarPorEstadoRegistro(Constantes.VALUE_ACTIVO, "denominacion");

		cargarGenericData(combobox, todos);
		for (TipoFormaPago tipoFormaPago : lstTipoFormaPago) {
			Comboitem oComboitem = new Comboitem();
			oComboitem.setLabel(tipoFormaPago.getDenominacion());
			oComboitem.setValue(tipoFormaPago);
			combobox.appendChild(oComboitem);
		}
	}

	public static void cargarMotivoCortesia (Combobox combobox, Boolean todos) throws Exception{
		ArrayList<MotivoCortesia> lstMotivocortesia = ServiceLocator.getMotivoCortesiaManager().buscarPorEstadoRegistro(Constantes.VALUE_ACTIVO, "denominacion");

		cargarGenericData(combobox, todos);
		for (MotivoCortesia motivoCortesia : lstMotivocortesia) {
			Comboitem oComboitem = new Comboitem();
			oComboitem.setLabel(motivoCortesia.getDenominacion());
			oComboitem.setValue(motivoCortesia);
			combobox.appendChild(oComboitem);
		}
	}

	public static void cargarRutas (Combobox combobox, Boolean todos) throws Exception{
		ArrayList<Ruta> lstRutas = ServiceLocator.getRutaManager().buscarPorEstadoRegistro(Constantes.VALUE_ACTIVO, "origen");

		cargarGenericData(combobox, todos);
		for (Ruta ruta : lstRutas) {
			Comboitem oComboitem = new Comboitem();
			oComboitem.setLabel(ruta.getOrigen() +" / "+ ruta.getDestino());
			oComboitem.setValue(ruta);
			combobox.appendChild(oComboitem);
		}
	}

	public static void cargarTipoAsiento (Combobox combobox, Boolean todos) throws Exception{
		ArrayList<TipoAsiento> lstTipoAsiento = ServiceLocator.getTipoAsientoManager().buscarPorEstadoRegistro(Constantes.VALUE_ACTIVO, "denominacion");

		cargarGenericData(combobox, todos);
		for (TipoAsiento tipoAsiento : lstTipoAsiento) {
			Comboitem oComboitem = new Comboitem();
			oComboitem.setLabel(tipoAsiento.getDenominacion());
			oComboitem.setValue(tipoAsiento);
			combobox.appendChild(oComboitem);
		}
	}

//	public static void cargarOpcionMenu (Combobox combobox, Boolean todos) throws Exception{
//		ArrayList<OpcionMenu> lstOpcionMenu = ServiceLocator.getOpcionMenuMenager().buscarPorEstadoRegistro(Constantes.ACTIVO, "denominacion");
//		combobox.getItems().clear();
//		cargarGenericData(combobox, todos);
//		for (int l = 0; l < lstOpcionMenu.size(); l ++) {
//			OpcionMenu opcionMenu = lstOpcionMenu.get(l);
//			Comboitem oComboitem = new Comboitem();
//			oComboitem.setLabel(opcionMenu.getDenominacion());
//			oComboitem.setValue(opcionMenu);
//			combobox.appendChild(oComboitem);
//		}
//	}
//
	/**
	 * Carga Menus Padres
	 * @param combobox
	 * @param todos
	 * @throws Exception
	 */
	public static void cargarMenuPadre(Combobox  combobox, Boolean todos) throws Exception{
		List<OpcionMenu> lstOpcionMenu =  ServiceLocator.getOpcionMenuManager().buscaMenusPadres();
		combobox.getItems().clear();
		cargarGenericData(combobox, todos);
		for (OpcionMenu opcionMenu : lstOpcionMenu) {
			Comboitem oComboitem = new Comboitem();
			oComboitem.setLabel(opcionMenu.getDenominacion());
			oComboitem.setValue(opcionMenu);
			combobox.appendChild(oComboitem);
		}


	}


	public static void cargarRoles (Combobox combobox, Boolean todos) throws Exception{
		ArrayList<Rol> lstRoles = ServiceLocator.getRolManager().buscarPorEstadoRegistro(Constantes.VALUE_ACTIVO,"denominacion");
		combobox.getItems().clear();
		cargarGenericData(combobox, todos);
		for (Rol rol : lstRoles) {
			Comboitem oComboitem = new Comboitem();
			oComboitem.setLabel(rol.getDenominacion());
			oComboitem.setValue(rol);
			combobox.appendChild(oComboitem);
		}
	}


	public static void cargarUsuarios (Combobox combobox, Boolean todos) throws Exception{
		ArrayList<Usuario> lstUsuarios = ServiceLocator.getUsuarioManager().buscarPorEstadoRegistro(Constantes.VALUE_ACTIVO,"apellidoPaterno");

		if (todos)
			cargarGenericData(combobox, todos);

		for (Usuario usuario : lstUsuarios) {
			Comboitem oComboitem = new Comboitem();
			String ApellidoMaterno=usuario.getApellidoMaterno()!=null?usuario.getApellidoMaterno():"";
			oComboitem.setLabel(usuario.getApellidoPaterno()+" "+ApellidoMaterno+", "+ usuario.getNombre());
			oComboitem.setValue(usuario);
			combobox.appendChild(oComboitem);
		}
	}

	public static void cargarUsuarios (Combobox combobox, TreeMap<String, Object> parametros, Boolean todos){
		try{
			combobox.getItems().clear();
			ArrayList<String> criteriosOrdenar = new ArrayList<>();
			criteriosOrdenar.add("apellidoPaterno");
			ArrayList<Usuario> lstUsuarios = ServiceLocator.getUsuarioManager().buscarPorX(parametros, criteriosOrdenar);

			cargarGenericData(combobox, todos);
			for (Usuario usuario : lstUsuarios) {
				Comboitem oComboitem = new Comboitem();
				String ApellidoMaterno=usuario.getApellidoMaterno()!=null?usuario.getApellidoMaterno():"";
				oComboitem.setLabel(usuario.getApellidoPaterno()+" "+ApellidoMaterno+", "+ usuario.getNombre());
				oComboitem.setValue(usuario);
				combobox.appendChild(oComboitem);
			}
		}catch(Exception ex){
			ex.printStackTrace();
		}
	}

	public static void cargarUsuarioAprobadores (Combobox combobox, Boolean todos) throws Exception{
		ArrayList<UsuarioAprobador> lstUsuarioAprobador = ServiceLocator.getUsuarioAprobadorManager().buscarPorEstadoRegistro(Constantes.VALUE_ACTIVO,null);

		cargarGenericData(combobox, todos);

		for (UsuarioAprobador usuarioAprobador: lstUsuarioAprobador) {
			Usuario  usuario= usuarioAprobador.getUsuario();
			Comboitem oComboitem = new Comboitem();
			oComboitem.setLabel(usuario.getApellidoPaterno()+" "+usuario.getApellidoMaterno()+", "+ usuario.getNombre());
			oComboitem.setValue(usuarioAprobador);
			combobox.appendChild(oComboitem);
		}
	}

	public static void cargarUsuariosLiquidacion(Combobox combobox, String fechaInicio, String fechaFinal, Boolean todos, Integer idAgencia) throws Exception{
		combobox.getItems().clear();
		List<Usuario>listUsuario=ServiceLocator.getUsuarioManager().buscarUsuarioLiquidacion(fechaInicio, fechaFinal, idAgencia,null);

		cargarGenericData(combobox, todos);

		for (Usuario usuario: listUsuario) {
			Comboitem oComboitem = new Comboitem();
			oComboitem.setLabel(usuario.getApellidoPaterno()+" "+usuario.getApellidoMaterno()+", "+ usuario.getNombre());
			oComboitem.setValue(usuario);
			combobox.appendChild(oComboitem);
		}
	}

	/**
	 * Carga usuarios, basados en las liquidaciones.
	 * @param combobox :Combobox
	 * @param idAgencia	:Identificador de la agencia
	 * @param estaLoquidacion	: estado de la liquidaci�n
	 * @throws Exception
	 */
	public static void cargarUsuariosLiquidacion(Combobox combobox,Integer idAgencia, Integer estaLoquidacion,Boolean todos) throws Exception{
		combobox.getItems().clear();
		List<Usuario>listUsuario=ServiceLocator.getUsuarioManager().buscarUsuarioLiquidacion(null, null, idAgencia,estaLoquidacion);
		cargarGenericData(combobox, todos);

		for (Usuario usuario: listUsuario) {
			Comboitem oComboitem = new Comboitem();
			oComboitem.setLabel(usuario.toString());
			oComboitem.setValue(usuario);
			combobox.appendChild(oComboitem);
		}
	}


	public static void cargarTipoGasto (Combobox combobox, Boolean todos, Integer isIngreso) throws Exception{
		ArrayList<TipoGasto> lstTipoGastos= ServiceLocator.getTipoGastoManager().buscarPorEstadoRegistro(Constantes.VALUE_ACTIVO, "denominacion");

		cargarGenericData(combobox, todos);

		for (TipoGasto tipoGasto : lstTipoGastos) {
			if(tipoGasto.getTipoOperacion().intValue()==isIngreso) {
				Comboitem oComboitem = new Comboitem();
				oComboitem.setLabel(tipoGasto.getDenominacion());
				oComboitem.setValue(tipoGasto);
				combobox.appendChild(oComboitem);
			}
		}
	}

	private static void cargarTipoGasto (Combobox combobox, Boolean todos) throws Exception{
		ArrayList<TipoGasto> lstTipoGastos= ServiceLocator.getTipoGastoManager().buscarPorEstadoRegistro(Constantes.VALUE_ACTIVO, "denominacion");

		cargarGenericData(combobox, todos);

		for (TipoGasto tipoGasto : lstTipoGastos) {
			Comboitem oComboitem = new Comboitem();
			oComboitem.setLabel(tipoGasto.getDenominacion());
			oComboitem.setValue(tipoGasto);
			combobox.appendChild(oComboitem);
		}
	}

	/**
	 *
	 * @param combobox
	 * @param todos
	 * @throws Exception
	 */
	public static void cargarTipoMovimiento (Combobox combobox, Boolean todos) throws Exception{
		ArrayList<TipoMovimiento> lstTipoMovimiento= ServiceLocator.getTipoMovimientoManager().buscarPorEstadoRegistro(Constantes.VALUE_ACTIVO, "denominacion");

		cargarGenericData(combobox, todos);
		for (TipoMovimiento tipoMovimiento : lstTipoMovimiento) {
			Comboitem oComboitem = new Comboitem();
			oComboitem.setLabel(tipoMovimiento.getDenominacion());
			oComboitem.setValue(tipoMovimiento);
			combobox.appendChild(oComboitem);
		}
	}
	public static void cargarTipoMovimiento(String campo,  Object[] criterios, List<String> criteriosOrdenar,Combobox combobox, Boolean todos) {
		try{
			List<TipoMovimiento> lstTipoMovimientos = ServiceLocator.getTipoMovimientoManager().buscarPorX(campo, criterios, criteriosOrdenar, Constantes.VALUE_ACTIVO);
			cargarGenericData(combobox, todos);
			for (TipoMovimiento tipoMovimiento: lstTipoMovimientos) {
				Comboitem oComboitem = new Comboitem();
				oComboitem.setLabel(tipoMovimiento.getDenominacion());
				oComboitem.setValue(tipoMovimiento);
				combobox.appendChild(oComboitem);
			}
			if(combobox.getItems().size()>0)
				combobox.setSelectedIndex(0);
		}catch(Exception ex){
			ex.printStackTrace();
		}
	}


	/**
	 * Recupera todos los funcionarios
	 * @param combobox : Obejto Combobox
	 * @param todos	   :
	 * @param funcinario_idDiferente : Opcional,Identificador del funcionario el cual no se debe cargar en el combo box
	 * @throws Exception
	 */
	public static void cargarFuncionarios (Combobox combobox, Boolean todos,Integer funcinario_idDiferente) throws Exception{
		String sid=String.valueOf(Constantes.ID_ROL_FUNCIONARIO);
		String[] ids =sid.split(",");
		Integer[] oCriteriosIN = new Integer[ids.length];
		for(int i=0; i<ids.length; i++){
			oCriteriosIN[i]=Integer.valueOf(ids[i]);
		}

		List<String> criteriosOrdenar= new ArrayList<>();
		criteriosOrdenar.add("usuario");
		List<UsuarioRol>listUsuarioRol=ServiceLocator.getUsuarioRolManager().buscarPorX("rol.id", oCriteriosIN, criteriosOrdenar, Constantes.VALUE_ACTIVO);

		/* *****Ordena los funcionarios por apellido paterno. */
		Object[]obj=new String[listUsuarioRol.size()];
		int x=-1;
		for(UsuarioRol usuarioRol:listUsuarioRol){

			Usuario usuario=usuarioRol.getUsuario();
			if(usuario.getEstadoRegistro().equals(Constantes.VALUE_ACTIVO)){
				x++;
				obj[x]=usuario.getApellidoPaterno()+"-"+(usuario.getApellidoMaterno()!=null?usuario.getApellidoMaterno():"")+"-"+usuario.getNombre()+"-"+usuario.getId();
			}

		}
		Arrays.sort(obj, 0, x);
		/* ***************************************** */

		//Carga Funcionarios
		cargarGenericData(combobox, todos);
		for (Object element : obj) {
			Usuario funcionario=new Usuario();

			if(element!=null){
				String[] cadena=element.toString().split("-");
				funcionario.setApellidoPaterno(cadena[0].toString());
				funcionario.setApellidoMaterno(cadena[1].toString());
				funcionario.setNombre(cadena[2].toString());
				funcionario.setId(Integer.valueOf(cadena[3].toLowerCase()));

				if(funcinario_idDiferente!=null){
					if(funcionario.getId().intValue()!=funcinario_idDiferente.intValue()){
						Comboitem oComboitem = new Comboitem();
						oComboitem.setLabel(funcionario.toString());
						oComboitem.setValue(funcionario);
						combobox.appendChild(oComboitem);
					}
				}else{
					Comboitem oComboitem = new Comboitem();
					oComboitem.setLabel(funcionario.toString());
					oComboitem.setValue(funcionario);
					combobox.appendChild(oComboitem);
				}
			}
		}
//		for (UsuarioRol usuarioRol : listUsuarioRol) {
//			Usuario usuario=usuarioRol.getUsuario();
//			Comboitem oComboitem = new Comboitem();
//			oComboitem.setLabel(usuario.toString());//+" "+usuario.getApellidoPaterno()+" "+ usuario.getApellidoMaterno());
//			oComboitem.setValue(usuario);
//			combobox.appendChild(oComboitem);
//		}

	}
	/**
	 * Cargar los clientes asignados a la cartera de un determinado funcionario
	 * @param combobox		: Objeto Combobox
	 * @param todos			:
	 * @param Usuario		: Opcional, Usuario que hace referencia al Funcionario
	 * @throws Exception
	 */
	public static void cargarClientesCartera (Combobox combobox, Boolean todos,Usuario funcionario) throws Exception{
		Integer idFuncionaerio=null;
		if(funcionario!=null)
			idFuncionaerio=funcionario.getId();
		List<CarteraCliente>listCarteraCliente=ServiceLocator.getCarteraClienteManager().buscarClientesCartera(idFuncionaerio, null);

		/* *****ordenar los funcionarios */
		Object[]obj=new String[listCarteraCliente.size()];
		int x=-1;
		for(CarteraCliente carteraCliente: listCarteraCliente){
			x++;
			Cliente cliente=carteraCliente.getCliente();
			obj[x]=cliente.getRazonSocial()+";"+cliente.getNumeroDocumento()+";"+cliente.getId();
		}
		Arrays.sort(obj, 0, listCarteraCliente.size());
		/* ***************************************** */

		//Carga Funcionarios
		cargarGenericData(combobox, todos);
		for (Object element : obj) {
			Cliente cliente=new Cliente();

			String[] cadena=element.toString().split(";");
			cliente.setRazonSocial(cadena[0].toString());
			cliente.setNumeroDocumento(cadena[1].toString());
			cliente.setId(Long.valueOf(cadena[2].toString()));

			Comboitem oComboitem = new Comboitem();
			oComboitem.setLabel(cliente.toString());
			oComboitem.setValue(cliente);
			combobox.appendChild(oComboitem);
		}


//		cargarGenericData(combobox, todos);
//		for (CarteraCliente carteraCliente: listCarteraCliente) {
//			Cliente cliente=carteraCliente.getCliente();
//			Comboitem oComboitem = new Comboitem();
//			oComboitem.setLabel(cliente.getRazonSocial());
//			oComboitem.setValue(cliente);
//			combobox.appendChild(oComboitem);
//		}
	}


	/**
	 * Carga los clientes que ayan realizado alguna solciitud contado o credito
	 * @param combobox		:Objeto Combobox
	 * @param todos
	 * @throws Exception
	 */
	public static void cargarClientesSolicitud (Combobox combobox, Boolean todos) throws Exception{

		List<Cliente>list=ServiceLocator.getClienteManager().cargaClientesSolicitud();
		cargarGenericData(combobox, todos);
		for (Cliente cliente: list) {
			Comboitem oComboitem = new Comboitem();
			oComboitem.setLabel(cliente.getRazonSocial());
			oComboitem.setValue(cliente);
			combobox.appendChild(oComboitem);
		}
		combobox.setSelectedIndex(0);
	}


	/**
	 * Carga todos los clientes credito
	 * @param combobox
	 * @param todos
	 * @throws Exception
	 */
	public static void cargarClientesCredito (Combobox combobox, Boolean todos,Integer idFuncionario) throws Exception{

		List<LineaCreditoCliente> list= new ArrayList<>();
		list=ServiceLocator.getLineaCreditoClienteManager().clientesCredito(null, idFuncionario,null);

		cargarGenericData(combobox, todos);
		for (LineaCreditoCliente lineaCreditoCliente: list) {
			Cliente cliente=lineaCreditoCliente.getCarteraCliente().getCliente();
			Comboitem oComboitem = new Comboitem();
			oComboitem.setLabel(cliente.getRazonSocial());
			oComboitem.setValue(cliente);
			combobox.appendChild(oComboitem);
		}
	}


	public static void cargarTipoCobranza (Combobox combobox, Boolean todos) throws Exception{
		ArrayList<TipoCobranza> lstTipoCobranza= ServiceLocator.getTipoCobranzaManager().buscarPorEstadoRegistro(Constantes.VALUE_ACTIVO, "denominacion");

		cargarGenericData(combobox, todos);
		for (TipoCobranza tipoCobranza : lstTipoCobranza) {
			Comboitem oComboitem = new Comboitem();
			oComboitem.setLabel(tipoCobranza.getDenominacion());
			oComboitem.setValue(tipoCobranza);
			combobox.appendChild(oComboitem);
		}
	}

	/**
	 * Carga autorizadores de las cortesias
	 * @param combobox	: objeto que contendr� la data
	 * @param todos		: (true)TODOS, (false)SELECCIONE
	 * @throws Exception
	 */
	public static void cargarAutorizadorCortesia (Combobox combobox, Boolean todos) throws Exception{
		ArrayList<AutorizadorCortesia> lstAutorizadorCortesias= ServiceLocator.getAutorizadorCortesiaManager().buscarPorEstadoRegistro(Constantes.VALUE_ACTIVO, null);
		ArrayList<AutorizadorCortesia>arrayList= new ArrayList<>();

//		cargarGenericData(combobox, todos);
		for (int l = 0; l < lstAutorizadorCortesias.size(); l ++) {
			AutorizadorCortesia  autorizadorCortesia= lstAutorizadorCortesias.get(l);
			boolean flag=false;

			for(AutorizadorCortesia autoCortesia: arrayList){
				if(autoCortesia.getPersonal().getId()==autorizadorCortesia.getPersonal().getId()){
					flag=true;
					break;
				}
			}
			if (!flag){
//				Comboitem oComboitem = new Comboitem();
//				oComboitem.setLabel(autorizadorCortesia.getPersonal().toString());
//				oComboitem.setValue(autorizadorCortesia);
//				combobox.appendChild(oComboitem);
				arrayList.add(autorizadorCortesia);
			}
		}


		/* *****ordenar */
		Object[]obj=new String[arrayList.size()];
		int x=-1;
		for(AutorizadorCortesia autorizadorCortesia: arrayList){
			x++;
			obj[x]=autorizadorCortesia.getPersonal().toString()+";"+autorizadorCortesia.getId().toString();
		}
		Arrays.sort(obj, 0, arrayList.size());
		/* ***************************************** */

		//Carga los autorizadores
		cargarGenericData(combobox, todos);
		for (Object element : obj) {
			String[] cadena=element.toString().split(";");
			Long id=Long.valueOf(cadena[1].toString());
			AutorizadorCortesia autorizadorCortesia=ServiceLocator.getAutorizadorCortesiaManager().buscarPorId(id);

			Comboitem oComboitem = new Comboitem();
			oComboitem.setLabel(autorizadorCortesia.getPersonal().toString());
			oComboitem.setValue(autorizadorCortesia);
			combobox.appendChild(oComboitem);
		}



	}

	private static void cargarMotivoTemporadaAlta (Combobox combobox, Boolean todos) throws Exception{
		ArrayList<MotivoTemporadaAlta> lstMotTempAlt= ServiceLocator.getMotivoTemporadaAltaManager().buscarPorEstadoRegistro(Constantes.VALUE_ACTIVO, "nombreMotivo");

		cargarGenericData(combobox, todos);
		for (MotivoTemporadaAlta motivoTemporadaAlta : lstMotTempAlt) {
			Comboitem oComboitem = new Comboitem();
			oComboitem.setLabel(motivoTemporadaAlta.getNombreMotivo());
			oComboitem.setValue(motivoTemporadaAlta);
			combobox.appendChild(oComboitem);
		}
	}

//	/**
//	 * Consulta agencia, en la cual esta registrado el Pc, de donde se esta ingresado al sistema.
//	 * @param codigo : Codigo del usuario Hardware.
//	 * @return : class UsuarioHardware.
//	 */
//	@SuppressWarnings("rawtypes")
//	public static  ArrayList agenciaUsuarioHardware(String codigo){
//		TreeMap<String, Object> criteriosBusqueda = new TreeMap<String, Object>();
//		criteriosBusqueda.put("codigo", codigo);
//		criteriosBusqueda.put("estadoRegistro", Constantes.ACTIVO);
//		ArrayList<UsuarioHardware> lstUsuarioHardware= ServiceLocator.getUsuarioHardwareManager().buscarPorX(criteriosBusqueda, null);
//
//		return lstUsuarioHardware;
//	}
//
//	/**
//	 *
//	 * @return : codigo del usuario hardware.
//	 * @throws Exception
//	 */
//	public static String getCodigoUsuarioHardware() throws Exception{
//		/*Leer archivo txt*/
////		File file= new  File("d:\\secret.txt");
////
////		FileReader fileReader = new FileReader(file);
////		BufferedReader buffer = new BufferedReader(fileReader);
////		String codigo = buffer.readLine();
//		String codigo = "192.168.50.41";
//
//		GenericBean genericBean = new GenericBean();
//		genericBean.setCodigoUsuarioHardware(codigo);
//
//
//		return genericBean.getCodigoUsuarioHardware();
//	}

	/**
	 * Calcula porcentace de los correlativos utilizados en los manifiestos
	 * @param CorrelativoActual : correlativo actual
	 * @param CorrelativoFinal  : correlativo final
	 * @return : porcentaje utilizado
	 * @throws Exception
	 */
	public static Integer porcentajeCorrelativoManifiesto(Agencia agencia, Empresa empresa) throws Exception{
		Integer porcentajeUtilizado=0;
		EspecieValorada especieValoradaSunat  =  ServiceLocator.getManifiestoManager().consultaAutorizacionSunat(agencia.getId(), empresa.getId());
		if (especieValoradaSunat.getCorrelativoActual() !=null && especieValoradaSunat.getCorrelativoFinal() !=null){
//			Long correlativoActual = especieValoradaSunat.getCorrelativoActual();
//			Long correlativoFinal = especieValoradaSunat.getCorrelativoFinal();
//			PorcentajeCorrelativoManifiesto = (int) ((correlativoActual * 100) / correlativoFinal);
			if(especieValoradaSunat.getPorcentajeUtilizado()!=null)
				porcentajeUtilizado= especieValoradaSunat.getPorcentajeUtilizado().intValue();
		}
		return porcentajeUtilizado;
	}

	/**
	 * Valida Mail
	 * @param email: texto a validar
	 * @return : falso(MAil incorrecto), true(Mail Correcto)
	 */
	public static Boolean validateEmail(String email) {
		boolean corercto=true;
        if(!email.matches(".+@.+\\.[a-z]+")) {
        	corercto= false;
        }
		return corercto;
    }

	/**
	 * consulta el Accesos en para el usuario
	 * @param usuario
	 * @return 	: rol y accesos para el usuario.
	 */
//	public static Rol getRol(Usuario usuario){
//		Rol rol = new Rol();
//		TreeMap<String, Object> criteriosBusqueda = new TreeMap<String, Object>();
//		criteriosBusqueda.put("estadoRegistro", Constantes.VALUE_ACTIVO);
//		criteriosBusqueda.put("usuario", usuario);
//		List<UsuarioRol> listUsuarioRol = ServiceLocator.getUsuarioRolManager().buscarPorX(criteriosBusqueda, null);
//		rol = ServiceLocator.getRolManager().buscarPorId(new Long( ((UsuarioRol) listUsuarioRol.get(0)).getUsuarioRolID().getIdRol()));
//
//		return rol;
//	}

	/**
	 * Recupera el tipo de operacion
	 * @param combo	: Objeto al cual se le asignara el Comboitem.
	 */
	public static void cargarTipoOperacion(Combobox combobox){
		Comboitem oComboitem = new Comboitem("SELECCIONE");
		Comboitem oComboitem1 = new Comboitem("RESTA");
		Comboitem oComboitem2 = new Comboitem("SUMA");

		oComboitem.setValue(-1);
		oComboitem1.setValue(0);
		oComboitem2.setValue(1);

		combobox.appendChild(oComboitem);
		combobox.appendChild(oComboitem1);
		combobox.appendChild(oComboitem2);
	}

	/**
	 * Verifica si el usuario tiene o no una liquidacion abierta
	 * @param usuario
	 * @param agencia
	 * @return : true (el usuario tiene una liquidacion abierta); fale(el usuario no tiene ninguna liquidacion abierta)
	 */
	public static Liquidacion estadoLiquidacionUsuario(Usuario usuario, Agencia agencia){
		TreeMap<String, Object> criteriosBusqueda = new TreeMap<>();
		criteriosBusqueda.put("usuario", usuario);
		criteriosBusqueda.put("agencia", agencia);
		criteriosBusqueda.put("estadoLiquidacion", Constantes.LIQUI_ESTA_ABIERTO);
		criteriosBusqueda.put("estadoRegistro", Constantes.VALUE_ACTIVO);
		List<Liquidacion> listLiquidacion= ServiceLocator.getLiquidacionManager().buscarPorX(criteriosBusqueda, null);

		Liquidacion liquidacion=new Liquidacion();
		if (listLiquidacion.size()>0)
			liquidacion=listLiquidacion.get(0);
		else
			liquidacion=null;

		return liquidacion;
	}

	/**
	 * Verifica si el usuario tiene o no una liquidacion abierta
	 * @param usuario
	 * @param agencia
	 * @return : true (el usuario tiene una liquidacion abierta); fale(el usuario no tiene ninguna liquidacion abierta)
	 */
	public static Liquidacion buscarLiquidacionByUsuario(Integer idAgencia, Integer idUsuario, Integer estadoLiquidacion, String fecha) throws Exception{
		Liquidacion liquidacion = null;
		try {
			liquidacion= ServiceLocator.getLiquidacionManager().buscarLiquidacionByUsuario(idAgencia, idUsuario, estadoLiquidacion, fecha);
		}catch (Exception ex) {
			throw new Exception(ex.getMessage());
		}
		return liquidacion;
	}


//	/**
//	 * Realiza la busqueda del correlativo para el boleto a emitir.
//	 * @param tipoComprobante	: Tipo de comprobante que se buscara.
//	 * @param usuarioHardwareID	: Identificador del equipo para obtener las especies valoradas asignadas.
//	 * @return Numero de boleto.
//	 */
//	public static String buscarEspecieValorada(Integer tipoComprobante, Integer usuarioHardwareID){
//		String result = "";
//		try{
//			UsuarioHardware usuarioHardware=ServiceLocator.getUsuarioHardwareManager().buscarPorId(usuarioHardwareID.longValue());
//
//			TreeMap<String, Object> criteriosBusqueda = new TreeMap<String, Object>();
//			criteriosBusqueda.put("tipoComprobante",new TipoComprobante(tipoComprobante));
//			criteriosBusqueda.put("agencia",usuarioHardware.getAgencia());
//			criteriosBusqueda.put("estadoRegistro", Constantes.VALUE_ACTIVO);
//
//			List<EspecieValorada> lstEspecieValorada = ServiceLocator.getEspecieValoradaManager().buscarPorX(criteriosBusqueda, null);
//			if(lstEspecieValorada.size()==1){
//				EspecieValorada especieValorada = lstEspecieValorada.get(0);
//				if(especieValorada.getSeriefe()==null)
//					throw new EspecieValoradaNotAvailableException();
//				result = especieValorada.toString();
//			}else if(lstEspecieValorada.size()>1){
//				DlgMessage.information(Messages.getString("UtilData.information.noUniqueEspecieValorada"));
//			}else
//				DlgMessage.information(Messages.getString("UtilData.information.noAsignacionEspecieValorada"));

			//End Begin 21/10/2016
//			TreeMap<String, Object> criteriosBusqueda = new TreeMap<String, Object>();
//			criteriosBusqueda.put("controlEspecieValoradaID.idTipoComprobante", tipoComprobante);
//			criteriosBusqueda.put("controlEspecieValoradaID.idUsuarioHardware", usuarioHardwareID);
//			criteriosBusqueda.put("estadoRegistro", Constantes.VALUE_ACTIVO);
//
//			List<ControlEspecieValorada> lstEspecieValoradas = ServiceLocator.getControlEspecieValoradaManager().buscarPorX(criteriosBusqueda, null);
//			if(lstEspecieValorada.size()==1){
//				ControlEspecieValorada controlEspecieValorada = lstEspecieValorada.get(0);
//				result = controlEspecieValorada.toString();
//			}else if(lstEspecieValorada.size()>1){
//				DlgMessage.information(Messages.getString("UtilData.information.noUniqueEspecieValorada"));
//			}else
//				DlgMessage.information(Messages.getString("UtilData.information.noAsignacionEspecieValorada"));
//			return null;
//		}catch(EspecieValoradaNotAvailableException evnaex){
//			DlgMessage.information(Messages.getString("UtilData.information.notAvailableEspecieValorada"));
//		}catch(Exception ex){
//			DlgMessage.information(ex.getMessage());
//		}
//		return result;
//	}

//	/**
//	 * Realiza la busqueda del correlativo de la especie valorada, de los tipos de comprobante DIFERENTES al Boleto de Viaje
//	 * @param tipoComprobante	: Tipo de comprobante que se buscara.
//	 * @param Agencia			: Identificador de la Agencia
//	 * @return Numero de boleto.
//	 */
//	public static String buscarEspecieValorada(Integer tipoComprobante, Agencia agencia, Boolean ejecutarSeqByCorrelativo){
//		String result = ""; ;
//		try{
//			TreeMap<String, Object> criteriosBusqueda = new TreeMap<String, Object>();
//			criteriosBusqueda.put("tipoComprobante",new TipoComprobante(tipoComprobante));
//			criteriosBusqueda.put("agencia",agencia );
//			criteriosBusqueda.put("estadoRegistro", Constantes.VALUE_ACTIVO);
//
//			List<EspecieValorada> lstEspecieValorada = ServiceLocator.getEspecieValoradaManager().buscarPorX(criteriosBusqueda, null);
//			if(lstEspecieValorada.size()==1){
//				EspecieValorada especieValorada = lstEspecieValorada.get(0);
//				if((tipoComprobante.equals(Constantes.ID_TIPCOM_FACTURA) || tipoComprobante.equals(Constantes.ID_TIPCOM_BOLETA_VENTA) || tipoComprobante.equals(Constantes.ID_TIPCOM_NOTA_CREDITO) || tipoComprobante.equals(Constantes.ID_TIPCOM_NOTA_DEBITO))){
//					if(especieValorada.getSeriefe()==null || especieValorada.getSeriefe().trim().isEmpty())
//						throw new EspecieValoradaNotAvailableException();
//
//					/*Valida si debe ejecutar el sequenciador y si lo tiene configurado*/
//					if(ejecutarSeqByCorrelativo && especieValorada.getNameSecuenciador()!=null && !(especieValorada.getNameSecuenciador().trim().isEmpty())){
//						especieValorada=ServiceLocator.getEspecieValoradaManager().ejecutarSeqCorrelativo(especieValorada);
//						result = especieValorada.toString();
////					}else if (ejecutarSeqByCorrelativo){
////						throw new Exception(Messages.getString("UtilData.information.noSequece"));
//					}else{
//						result = especieValorada.toString();
//					}
//				}else
//					result = especieValorada.toString();
//			}else if(lstEspecieValorada.size()>1){
//				throw new Exception(Messages.getString("UtilData.information.noUniqueEspecieValorada"));
//			}else
//				throw new Exception(Messages.getString("UtilData.information.noAsignacionEspecieValorada"));
//		}catch(EspecieValoradaNotAvailableException evnaex){
//			DlgMessage.information(Messages.getString("UtilData.information.notAvailableEspecieValorada"));
//		}catch(Exception ex){
//			DlgMessage.information(ex.getMessage());
//		}
//		return result;
//	}
	/**
	 * Realiza la busqueda del correlativo de la especie valorada, de los tipos de comprobante DIFERENTES al Boleto de Viaje
	 * @param tipoComprobante	: Tipo de comprobante que se buscara.
	 * @param Agencia			: Identificador de la Agencia
	 * @return Numero de boleto.
	 * @throws Exception
	 */
	public static EspecieValorada buscarEspecieValorada(Integer tipoComprobante, Agencia agencia, Boolean ejecutarSeqByCorrelativo) throws Exception{
		EspecieValorada oespecieValorada=null;
		try{
			TreeMap<String, Object> criteriosBusqueda = new TreeMap<>();
			criteriosBusqueda.put("tipoComprobante",new TipoComprobante(tipoComprobante));
			criteriosBusqueda.put("agencia",agencia );
			criteriosBusqueda.put("estadoRegistro", Constantes.VALUE_ACTIVO);

			List<EspecieValorada> lstEspecieValorada = ServiceLocator.getEspecieValoradaManager().buscarPorX(criteriosBusqueda, null);
			if(lstEspecieValorada.size()==1){
				EspecieValorada especieValorada = lstEspecieValorada.get(0);
				if((tipoComprobante.equals(Constantes.ID_TIPCOM_FACTURA) || tipoComprobante.equals(Constantes.ID_TIPCOM_BOLETA_VENTA) || tipoComprobante.equals(Constantes.ID_TIPCOM_NOTA_CREDITO) || tipoComprobante.equals(Constantes.ID_TIPCOM_NOTA_DEBITO))){
					if(especieValorada.getSeriefe()==null || especieValorada.getSeriefe().trim().isEmpty())
						throw new EspecieValoradaNotAvailableException();

					/*Valida si debe ejecutar el sequenciador y si lo tiene configurado*/
					if(ejecutarSeqByCorrelativo && especieValorada.getNameSecuenciador()!=null && !(especieValorada.getNameSecuenciador().trim().isEmpty())){
						especieValorada=ServiceLocator.getEspecieValoradaManager().ejecutarSeqCorrelativo(especieValorada);
						oespecieValorada = especieValorada;
					}else{
						oespecieValorada=especieValorada;
					}
				}else
					oespecieValorada=especieValorada;
			}else if(lstEspecieValorada.size()>1){
				throw new Exception(Messages.getString("UtilData.information.noUniqueEspecieValorada"));
			}else
				throw new Exception(Messages.getString("UtilData.information.noAsignacionEspecieValorada"));
		}catch(EspecieValoradaNotAvailableException evnaex){
			throw new Exception(Messages.getString("UtilData.information.notAvailableEspecieValorada"));
//			DlgMessage.information(Messages.getString("UtilData.information.notAvailableEspecieValorada"));
		}catch(Exception ex){
			throw new Exception(ex);
//			DlgMessage.information(ex.getMessage());
		}

		return oespecieValorada;
	}

	/**
	 * Carga el tipo de transaccion(Venta y Reserva)
	 * @param combobox
	 * @param todos
	 */
	public static void cargarTipoTransaction(Combobox combobox) {
		//cargarGenericData(combobox, todos);

		Comboitem oComboitem2 = new Comboitem("VENTAS");
		Comboitem oComboitem3 = new Comboitem("RESERVAS");

		oComboitem2.setValue(1);
		oComboitem3.setValue(2);

		combobox.appendChild(oComboitem2);
		combobox.appendChild(oComboitem3);
	}

	/**
	 * Carga los tipos de convenio con el cliente
	 * @param combobox 	: objeto donde se cargar�n los resultados
	 * @param todos		: (True)muesta en el index 0 TODOS, (false) muestra en index 0 SELECCIONE
	 */
	public static void cargarTipoConvenio(Combobox combobox, Boolean todos){
		cargarGenericData(combobox, todos);

//		Comboitem oComboitem2 = new Comboitem("CONTADO");
		Comboitem oComboitem3 = new Comboitem("CREDITO");

//		oComboitem2.setValue(Constantes.TIPCON_CONTADO);
		oComboitem3.setValue(Constantes.TIPCON_CREDITO);

//		combobox.appendChild(oComboitem2);
		combobox.appendChild(oComboitem3);
	}

	/**
	 * Carga estos de la solicitud
	 * @param combobox 	: objeto donde se cargar�n los resultados
	 * @param todos		: (True)muesta en el index 0 TODOS, (false) muestra en index 0 SELECCIONE
	 */
	public static void cargarEstadoSolicitud(Combobox combobox, Boolean todos){
		cargarGenericData(combobox, todos);

		Comboitem oComboitem2 = new Comboitem(Constantes.APROBADO_DESC+"S");
		Comboitem oComboitem3 = new Comboitem(Constantes.DESAPROBADO_DESC+"S");

		oComboitem2.setValue(Constantes.ESTADOSOL_INACTIVA);
		oComboitem3.setValue(Constantes.ESTADOSOL_ANULADA);

		combobox.appendChild(oComboitem2);
		combobox.appendChild(oComboitem3);
	}

	/**
	 * Carga estados de la Linea de Credito de un cliente
	 * @param combobox
	 * @param todos
	 */
	public static void cargarEstadoSolicitudLC(Combobox combobox, Boolean todos){
		cargarGenericData(combobox, todos);

		Comboitem oComboitem2 = new Comboitem("APROBADAS");
		Comboitem oComboitem3 = new Comboitem("DESAPROBADAS");

		oComboitem2.setValue(Constantes.ESTADOSOL_ACTIVA);
		oComboitem3.setValue(Constantes.ESTADOSOL_ANULADA);

		combobox.appendChild(oComboitem2);
		combobox.appendChild(oComboitem3);
	}




//	/**
//	 * Carga el tipo de Cliente
//	 * @param combobox 	: objeto donde se cargar�n los resultados
//	 * @param todos		: (True)muesta en el index 0 TODOS, (false) muestra en index 0 SELECCIONE
//	 */
//	public static void cargarTipoCliente(Combobox combobox, Boolean todos){
//		cargarGenericData(combobox, todos);
//
//		Comboitem oComboitem2 = new Comboitem("CORPORATIVO");
//		Comboitem oComboitem3 = new Comboitem("AGENCIA DE VIAJE");
//
//		oComboitem2.setValue(Constantes.TIPCLI_CORPORATIVO);
//		oComboitem3.setValue(Constantes.TIPCLI_AGENCIA);
//
//		combobox.appendChild(oComboitem2);
//		combobox.appendChild(oComboitem3);
//	}
	/**
	 * Carga el Origen del Cliente
	 * @param Combobox 	: objeto donde se cargar�n los resultados
	 * @param todos		: (True)muesta en el index 0 TODOS, (false) muestra en index 0 SELECCIONE
	 */
	public static void cargarOrigenCliente(Combobox Combobox, Boolean todos){
		cargarGenericData(Combobox, todos);

		Comboitem oComboitem2 = new Comboitem(Constantes.ORIGEN_LIMA_DESC);
		Comboitem oComboitem3 = new Comboitem(Constantes.ORIGEN_PROVINCIAS_DESC);

		oComboitem2.setValue(Constantes.ORIGEN_LIMA);
		oComboitem3.setValue(Constantes.ORIGEN_PROVINCIAS);

		Combobox.appendChild(oComboitem2);
		Combobox.appendChild(oComboitem3);
	}


	/**
	 * Realiza la liberaci�n de asientos bloqueados por el usuarioHardware.
	 * @param usuarioHardware	: Usuario hardware del cual se desea borrar los asientos seleccionados.
	 */
	public static void liberarAsientos(Integer idUsuarioHardware){
		try{
			ServiceLocator.getTmpOcupacionAsientosManager().desbloquearAsientoByUsuarioHardware(idUsuarioHardware);
		}catch(Exception ex){
			DlgMessage.error(ex.getMessage());
		}
	}

	/**
	 * Carga los tipos de comision para el concesionario
	 * @param combobox : Objeto Combobox
	 */
	public static void cargarTipoComsion(Combobox  combobox){
		Comboitem oComboitem0 = new Comboitem("SELECCIONE");
		Comboitem oComboitem1 = new Comboitem("PORCENTAJE");
		Comboitem oComboitem2 = new Comboitem("FIJO");

		oComboitem0.setValue(-1);
		oComboitem1.setValue(0);
		oComboitem2.setValue(1);

		combobox.appendChild(oComboitem0);
		combobox.appendChild(oComboitem1);
		combobox.appendChild(oComboitem2);
	}

	/**
	 * Valida si un determinado usuario tiene o no mail configurado
	 * @param usuario
	 * @return true: si el suario tiene E-Mail configurado, false: si el usuario NO tiene E-Mail configurado
	 * @throws Exception
	 */
	public static Boolean validateUserMail(Usuario usuario) throws Exception{
		Usuario ousuario=ServiceLocator.getUsuarioManager().buscarPorId(usuario.getId().longValue());
		if(ousuario.getPersonal()==null){
			if(usuario.getEmailFuncionario()==null)
				return false;
			else
				return true;
		}else if (ousuario.getPersonal().getEmail()==null)
			return false;

		return true;
	}

	/**
	 * Realiza la apertura de la liquidacion.
	 * @param fechaLiquidacion	: Fecha con la que se va aperturar la liquidacion
	 * @return liquidacion
	 * @throws Exception
	 */
	public static Liquidacion aperturarLiquidacion(Date fechaLiquidacion,Usuario usuario,Agencia agencia) throws Exception{
		Liquidacion liquidacion = new Liquidacion();

		liquidacion.setAnio(new Integer(Constantes.FORMAT_YEAR.format(fechaLiquidacion.getTime())));
		liquidacion.setAgencia(agencia);
		liquidacion.setUsuario(usuario);
		liquidacion.setNombreUsuario(usuario.getLogin());
		liquidacion.setFechaLiquidacion(fechaLiquidacion);
		liquidacion.setMontoIngresado((double) 0);

		liquidacion.setMontoIngresadoDolares((double) 0);

		liquidacion.setEstadoLiquidacion(Constantes.LIQUI_ESTA_ABIERTO);
		liquidacion.setEstadoRegistro(Constantes.VALUE_ACTIVO);
		liquidacion.setMontoIngresadoDolares((double)0);

		UtilData.auditarRegistro(liquidacion, usuario, Executions.getCurrent());
		ServiceLocator.getLiquidacionManager().aperturarLiquidacion(liquidacion);

		return liquidacion;
	}





	/**
	 * Procesa el cierre de caja
	 * @param liquidacion	: Objeto liquidacion
	 * @param monto			: Monto con que se va ha cerrar
	 * @param usuario		: Objeto usuario
	 * @throws Exception
	 */
	public static void procesaCierreCaja(Liquidacion liquidacion, Double monto,Usuario usuario, Double montoDolares) throws Exception{
		/*Actualiza liquidaci�n*/
		liquidacion.setMontoIngresado(monto!=null? monto: .00);
		liquidacion.setEstadoLiquidacion(Constantes.LIQUI_ESTA_CERRADO);
		liquidacion.setEstadoRegistro(Constantes.VALUE_ACTIVO);
		liquidacion.setMontoIngresadoDolares(montoDolares);
//		auditarRegistro(liquidacion, usuario, Executions.getCurrent());
		auditarRegistro(liquidacion, true, usuario, Executions.getCurrent());
		ServiceLocator.getLiquidacionManager().actualizar(liquidacion);

		/*Busca ventas a liquidar.*/
		String fechaLiquidacion=Constantes.FORMAT_DATE.format(liquidacion.getFechaLiquidacion());
		Integer idAgencia=liquidacion.getAgencia().getId();
		Integer idUsuario=liquidacion.getUsuario().getId();
//		List<DetalleLiquidacion> list = ServiceLocator.getDetalleLiquidacionManager().buscarVentasALiquidar(fechaLiquidacion, idAgencia, idUsuario);

//		/*Guarda Detalle Liquidacion*/
		procesarDetalleLiquidacion(liquidacion, usuario);


		/*Actualiza el identificador de la liquidacion en las ventas del pool*/
		List<VentaPool> ventasPool=ServiceLocator.getVentaPoolManager().buscarVentas(fechaLiquidacion, fechaLiquidacion, liquidacion.getAgencia().getId(), liquidacion.getUsuario().getId());
		for(VentaPool ventaPool : ventasPool){
//			if(ventaPool.getEstadoRegistro().equals(Constantes.VALUE_ACTIVO)){
				ventaPool=ServiceLocator.getVentaPoolManager().buscarPorId(ventaPool.getId());
				ventaPool.setLiquidacion(liquidacion);
				auditarRegistro(ventaPool, true, liquidacion.getUsuario(), Executions.getCurrent());
				ServiceLocator.getVentaPoolManager().actualizar(ventaPool);
//			}
		}


//		for (DetalleLiquidacion detalleLiquidacion: list){
//			DetalleLiquidacion odetalleLiquidacion = new DetalleLiquidacion();
//			odetalleLiquidacion.setLiquidacion(liquidacion);
//			odetalleLiquidacion.setAnio(liquidacion.getAnio());
//			odetalleLiquidacion.setTipoFormaPago(detalleLiquidacion.getTipoFormaPago());
//			odetalleLiquidacion.setTipoMovimiento(detalleLiquidacion.getTipoMovimiento());
//			odetalleLiquidacion.setTipoComprobante(detalleLiquidacion.getTipoComprobante());
//			odetalleLiquidacion.setCantidad(detalleLiquidacion.getCantidad());
//			odetalleLiquidacion.setTotal(detalleLiquidacion.getTotal());
//			odetalleLiquidacion.setEstadoRegistro(Constantes.VALUE_ACTIVO);
//			auditarRegistro(odetalleLiquidacion, usuario, Executions.getCurrent());
//			ServiceLocator.getDetalleLiquidacionManager().guarda(odetalleLiquidacion);
//		}

		/*Actualiza el campo idLiquidacion de las ventas liquidadas.*/
		ServiceLocator.getDetalleLiquidacionManager().actualizaIdLiquidacionVentasLiquidadas(liquidacion.getId().longValue(),fechaLiquidacion, idAgencia, idUsuario);
	}

	/**
	 * REGISTRA EL DETALLE DE LA LIQUIDACION
	 * @param liquidacion		: Objet Liquidacion.
	 * @param fechaLiquidacion	: Fecha de la liquidacion.
	 * @param usuarioAuditoria	: Objeto usuario, quien realiza el cierre.
	 * @throws Exception
	 */
	public static void procesarDetalleLiquidacion(Liquidacion liquidacion,Usuario usuarioAuditoria) throws Exception{
		/* Primero se asegura, y elimina el detalle de la liquidacion, por si esta exista*/
		ServiceLocator.getDetalleLiquidacionManager().deleteXidLiquidacion(liquidacion.getId().longValue());

		/*Busca ventas a liquidar.*/
		String fechaLiquidacion=Constantes.FORMAT_DATE.format(liquidacion.getFechaLiquidacion());
		List<DetalleLiquidacion> list = ServiceLocator.getDetalleLiquidacionManager().buscarVentasALiquidar(fechaLiquidacion, liquidacion.getAgencia().getId(), liquidacion.getUsuario().getId());
		/*Guarda Detalle Liquidacion*/
		for (DetalleLiquidacion detalleLiquidacion: list){
			DetalleLiquidacion odetalleLiquidacion = new DetalleLiquidacion();
			odetalleLiquidacion.setLiquidacion(liquidacion);
			odetalleLiquidacion.setAnio(liquidacion.getAnio());
			odetalleLiquidacion.setTipoFormaPago(detalleLiquidacion.getTipoFormaPago());
			odetalleLiquidacion.setTipoMovimiento(detalleLiquidacion.getTipoMovimiento());
			odetalleLiquidacion.setTipoComprobante(detalleLiquidacion.getTipoComprobante());
			odetalleLiquidacion.setCantidad(detalleLiquidacion.getCantidad());
			odetalleLiquidacion.setTotal(detalleLiquidacion.getTotal());
			odetalleLiquidacion.setEstadoRegistro(Constantes.VALUE_ACTIVO);
			auditarRegistro(odetalleLiquidacion, usuarioAuditoria, Executions.getCurrent());
			ServiceLocator.getDetalleLiquidacionManager().guarda(odetalleLiquidacion);
		}
	}

	/**
	 * Valida si la ruta esta configurada para permitir la venta antes o despuesta de la hora de salida ## impl 10/11/2014 - jabanto
	 * @param idRuta : Identificador de la ruta
	 * @param idItinerario	: Identificador del Itinerario
	 * @param fechaPartida  : fecha de partida del servicio, en el caso de la fecha abierta sera null
	 * @return false si la venta esta deshabilitada, true caso contrario
	 * @throws Exception
	 */
	public static Boolean permiteVentaByTramo(Integer idRuta, Long idItinerario, String fechaPartida) throws Exception{

		List<VentaTramo>listVentaTramo=ServiceLocator.getVentaTramoManager().buscarByRuta(idRuta, fechaPartida);
		if(listVentaTramo.size()>0){
			Date fechahoraActual=new Date();
			Itinerario itinerario=idItinerario!=null?ServiceLocator.getItinerarioManager().buscarPorId(idItinerario):null;
			Date fecHoraPart=itinerario!=null?getFechaHora(itinerario.getFechaPartida(), itinerario.getHoraPartida()):null;

			/*Cuando el itinerario es null, esta condicion solamente se debe cumplir en la fecha abierta*/
			if(itinerario==null && listVentaTramo.size()>0)
				return false;

			if(listVentaTramo.size()==1){
				//Valida si la validacion debe ser antes o despuesta de la hora de salida
				VentaTramo ventaTramo=listVentaTramo.get(0);
				long minutos=ventaTramo.getTiempo()*Constantes.MILISEGUNDOS_X_MINUTO;

				if(ventaTramo.getItinerario()==null){
					//Validacion solamente por el identificador de la ruta
					if(ventaTramo.getDespuesHoraSalida().intValue()==Constantes.TRUE_VALUE){//Despues de la salida
						if(fechahoraActual.getTime()<(fecHoraPart.getTime()+minutos))
							return false;
					}
//					else{//Antes de la salida
//						if(fechahoraActual.getTime()>(fecHoraPart.getTime()+minutos))
//							return false;
//					}
				}else if (ventaTramo.getItinerario().getId().longValue()==itinerario.getId().intValue()){
					//Validacion por el identificador del itinerario
					if(ventaTramo.getDespuesHoraSalida().intValue()==Constantes.TRUE_VALUE){//Despues de la salida
						if(fechahoraActual.getTime()<(fecHoraPart.getTime()+minutos))
							return false;
					}
//					else{//Antes de la salida
//						if(fechahoraActual.getTime()>(fecHoraPart.getTime()+minutos))
//							return false;
//					}
				}

			}else{
				//Validacion por itinerario
				boolean validAplicadaByItinerario=false;
				for(VentaTramo ventaTramo:listVentaTramo){
					if(ventaTramo.getItinerario()!=null && ventaTramo.getItinerario().getId().intValue()==itinerario.getId().longValue()){
						validAplicadaByItinerario=true;
						long minutos=ventaTramo.getTiempo()*Constantes.MILISEGUNDOS_X_MINUTO;
						if(ventaTramo.getDespuesHoraSalida().intValue()==Constantes.TRUE_VALUE){//Despues de la salida
							if(fechahoraActual.getTime()<(fecHoraPart.getTime()+minutos))
								return false;
						}
//						else{//Antes de la salida
//							if(fechahoraActual.getTime()>(fecHoraPart.getTime()+minutos))
//								return false;
//						}
						break;
					}
				}

				//Validacion simple, si es que no se aplico la validacion por itinerario.
				if(!(validAplicadaByItinerario)){
					VentaTramo ventaTramo=listVentaTramo.get(0);
					long minutos=ventaTramo.getTiempo()*Constantes.MILISEGUNDOS_X_MINUTO;
					if(ventaTramo.getDespuesHoraSalida().intValue()==Constantes.TRUE_VALUE){//Despues de la salida
						if(fechahoraActual.getTime()<(fecHoraPart.getTime()+minutos))
							return false;
					}
//					else{//Antes de la salida
//						if(fechahoraActual.getTime()>(fecHoraPart.getTime()+minutos))
//							return false;
//					}
				}
			}
		}

		return true;
	}

	@SuppressWarnings("deprecation")
	private static Date getFechaHora(Date fecha, String hora) throws Exception{
		int index=hora.toString().indexOf(":");
		String sHora=hora.substring(0,index);
		String sMinuto=hora.substring(index+1,hora.length());
		String fechaPartida=Constantes.FORMAT_DATE.format(fecha);
		Date fecHoraPart=Constantes.FORMAT_DATE.parse(fechaPartida);
		fecHoraPart.setHours(Integer.valueOf(sHora));
		fecHoraPart.setMinutes(Integer.valueOf(sMinuto));

		return fecHoraPart;
	}

	/**
	 * Carga los formatos de los boletos
	 * @param combobox	: comtrol en donde los va a cargar
	 * @param todos
	 * @throws Exception
	 */
	public static void cargarFormatoBoletos(Combobox combobox, boolean todos)throws Exception{

//		InvoiceType invoiceType=new InvoiceType();
//		UBLExtensionsType extensionsType=new UBLExtensionsType();
//	 	invoiceType.setUBLExtensions(extensionsType);

		/**
		 * UBO_INVOICELINE
		 */


		cargarGenericData(combobox, todos);
		Comboitem comboitem=new Comboitem("FORMATO CHICO");
		comboitem.setValue(Constantes.FALSE_VALUE);
		combobox.appendChild(comboitem);
		comboitem=new Comboitem("FORMATO GRANDE");
		comboitem.setValue(Constantes.TRUE_VALUE);
		combobox.appendChild(comboitem);
//		comboitem=new Comboitem("ELECTRONICO");
//		comboitem.setValue(Constantes.TRUE_VALUE+1);
		combobox.appendChild(comboitem);

		combobox.setSelectedIndex(0);
	}

	/**
	 * Valida el tipo de formato de impresion del boleto
	 * @param idAgencia	: Identificador de la agencia.
	 * @param idTipoComprobante	: Identificador del tipo de comprobante.
	 * @param idUsuarioHarware	: Identificador del usuario hardware.
	 * @return true, si debe utilizar el formato grande, false si el normal.
	 * @throws Exception
	 */
	public static boolean getFormatoImpresion(Integer idAgencia,Integer idTipoComprobante,Integer idUsuarioHarware, Integer idEmpresa) throws Exception{

		List<ControlEspecieValorada> lstCtrlEspecieValorada=ServiceLocator.getControlEspecieValoradaManager().buscarEspecieValoradas(idAgencia, idTipoComprobante, idUsuarioHarware, idEmpresa);
		boolean formato=lstCtrlEspecieValorada.get(0).getFormato().intValue()==Constantes.TRUE_VALUE;

		return formato;
	}

	/**
	 * Realiza la busqueda de la configuracion de la impresora
	 * @param usuarioHardwareID : UsuarioHardwareID
	 * @return
	 * @throws Exception
	 */
	public static ConfiguracionImpresora getConfiguracionImpresora(Integer usuarioHardwareID)throws Exception{
		TreeMap<String, Object>criteriosBusqueda= new TreeMap<>();
		criteriosBusqueda.put("usuarioHardware", new UsuarioHardware(usuarioHardwareID));
		criteriosBusqueda.put("estadoRegistro", Constantes.VALUE_ACTIVO);
		List<ConfiguracionImpresora> result=ServiceLocator.getConfiguracionImpresoraManager().buscarPorX(criteriosBusqueda, null);

		ConfiguracionImpresora configuracionImpresora=null;
		if(result.size()>0)
			configuracionImpresora=result.get(0);

		return configuracionImpresora;
	}

	/**
	 * Realiza la busqueda del correlativo de la especie valorada, de los tipos de comprobante DIFERENTES al Boleto de Viaje
	 * @param tipoComprobante			: Tipo de comprobante que se buscara.
	 * @param agencia					: Identificador de la Agencia
	 * @param ejecutarSeqByCorrelativo	: Indica si se ejecutara el secuenciador
	 * @param usuarioHardware			: Usuario hardware para obtener sus especies
	 * @param aplicarA					: Indica si la NC o ND se aplica a boletas (1) o Facturas (2)
	 * @return Numero de boleto.
	 * @throws Exception
	 */
	public static ControlEspecieValorada buscarEspecieValoradaByCaja (Integer tipoComprobante, Agencia agencia, Boolean ejecutarSeqByCorrelativo, UsuarioHardware usuarioHardware, Integer aplicarA, Integer idEmpresa) throws Exception{
		ControlEspecieValorada oControlEspecieValorada=null;
		try{
			TreeMap<String, Object> criteriosBusqueda = new TreeMap<>();
			criteriosBusqueda.put("empresa",new Empresa(idEmpresa));
			criteriosBusqueda.put("tipoComprobante",new TipoComprobante(tipoComprobante));
			criteriosBusqueda.put("agencia",agencia );
			criteriosBusqueda.put("usuarioHardware", usuarioHardware);
			criteriosBusqueda.put("estadoRegistro", Constantes.VALUE_ACTIVO);
			if(tipoComprobante.equals(Constantes.ID_TIPCOM_NOTA_CREDITO) || tipoComprobante.equals(Constantes.ID_TIPCOM_NOTA_DEBITO))
				criteriosBusqueda.put("aplica", aplicarA);

			List<ControlEspecieValorada> lstControlEspecieValorada = ServiceLocator.getControlEspecieValoradaManager().buscarPorX(criteriosBusqueda, null);
			if(lstControlEspecieValorada.size()==1){
				ControlEspecieValorada controlEspecieValorada = lstControlEspecieValorada.get(0);
				if((tipoComprobante.equals(Constantes.ID_TIPCOM_FACTURA) || tipoComprobante.equals(Constantes.ID_TIPCOM_BOLETA_VENTA)
						|| tipoComprobante.equals(Constantes.ID_TIPCOM_NOTA_CREDITO) || tipoComprobante.equals(Constantes.ID_TIPCOM_NOTA_DEBITO)
						|| tipoComprobante.equals(Constantes.ID_TIPCOM_GUIA_TRANSPORTISTA) || tipoComprobante.equals(Constantes.ID_TIPCOM_TICKET_EQUIPAJE))){
//					if(controlEspecieValorada.getSeriefe()==null || controlEspecieValorada.getSeriefe().trim().isEmpty())
//						throw new EspecieValoradaNotAvailableException();

					/*Valida si debe ejecutar el sequenciador y si lo tiene configurado*/
					if(ejecutarSeqByCorrelativo && controlEspecieValorada.getSecuenciador()!=null && !(controlEspecieValorada.getSecuenciador().trim().isEmpty())){
						controlEspecieValorada=ServiceLocator.getControlEspecieValoradaManager().ejecutarSecuenciador(controlEspecieValorada);
						oControlEspecieValorada = controlEspecieValorada;
					}else{
						oControlEspecieValorada=controlEspecieValorada;
					}
				}else
					oControlEspecieValorada=controlEspecieValorada;
			}else if(lstControlEspecieValorada.size()>1) {
				if(tipoComprobante.equals(Constantes.ID_TIPCOM_NOTA_CREDITO) || tipoComprobante.equals(Constantes.ID_TIPCOM_NOTA_DEBITO)) {

				}else
					throw new Exception(Messages.getString("UtilData.information.noUniqueEspecieValorada"));
			}else
				throw new EspecieValoradaNotAvailableException();
		}catch(EspecieValoradaNotAvailableException evnaex){
			throw new EspecieValoradaNotAvailableException();
//			DlgMessage.information(Messages.getString("UtilData.information.notAvailableEspecieValorada"));
		}catch(Exception ex){
			throw new Exception(ex);
//			DlgMessage.information(ex.getMessage());
		}

		return oControlEspecieValorada;
	}

	/**
	 * Obtiene el identificador de la entidad agencia de la bd transcarweb
	 * @param agencia_idvrybus : Identificador de la agencia del vrybus
	 * @return identificar de la agencia_id del transcar
	 * @throws Exception
	 */
	public static Integer getAgencia_Idtranscarweb(Integer agencia_idvrybus)throws Exception{
		return ServiceLocator.getAgenciaManager().buscarAgencia_Idtranscarweb(agencia_idvrybus);
	}

	/**
	 * Obtiene el identificador de la entidad agencia de la bd vyrbus
	 * @param agencia_idtranscarweb : Identificador de la agencia del transcarweb
	 * @return identificar de la agencia_id del vyrbus
	 * @throws Exception
	 */
	public static Integer getAgencia_Idvyrbus(Integer agencia_idtranscarweb)throws Exception{
		return ServiceLocator.getAgenciaManager().buscarAgencia_Idvyrbus(agencia_idtranscarweb);
	}

	/**
	 * Realiza la busqueda de la liquidacion de carga
	 * @param liquidacionPasajes : Instancia de la liquidacion de pasajes
	 * @return
	 * @throws Exception
	 */
	public static Liquidacion buscarLiquidacionCarga(Liquidacion liquidacionPasajes)throws Exception{
		Liquidacion liquidacionCarga = null;
		try {
			TranscarUsuarioPersonal transcarUsuario = ServiceLocator.getTranscarWebManager().buscarUsuario(liquidacionPasajes.getUsuario().getLogin());
			if(transcarUsuario!=null) {
				String fecha = Constantes.FORMAT_DATE.format(liquidacionPasajes.getFechaLiquidacion());
				TreeMap<String, Liquidacion> resultCarga = ServiceLocator.getTranscarWebManager().buscarLiquidacionCounter(fecha, fecha, liquidacionPasajes.getAgencia().getId(), transcarUsuario.getId());
				if(resultCarga !=null) {
					Integer agencia_idtranscar = UtilData.getAgencia_Idtranscarweb(liquidacionPasajes.getAgencia().getId());
					String key = Constantes.FORMAT_DATE.format(liquidacionPasajes.getFechaLiquidacion());
//					key += liquidacionPasajes.getAgencia().getId().toString();
					key += (agencia_idtranscar!=null?agencia_idtranscar.toString(): "");
					key += liquidacionPasajes.getUsuario().getLogin();
					liquidacionCarga = resultCarga.get(key);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return liquidacionCarga;
	}

	public static void cargarTipoSeguridadUsuario(Combobox combobox, Boolean todos)throws Exception{

		cargarGenericData(combobox, todos);
		Comboitem comboitem = new Comboitem("USAR AUTENTICADOR");
		comboitem.setValue(Constantes.TRUE_VALUE);
		combobox.appendChild(comboitem);
		comboitem = new Comboitem("SIN AUTENTICADOR");
		comboitem.setValue(Constantes.FALSE_VALUE);
		combobox.appendChild(comboitem);

		combobox.setSelectedIndex(0);
	}
}

