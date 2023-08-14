
package pe.itsb.vyrbus.view.ctrl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.TreeMap;

import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zul.Button;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Image;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Messagebox;

import pe.itsb.vyrbus.model.bean.CarteraCliente;
import pe.itsb.vyrbus.model.bean.LineaContadoCliente;
import pe.itsb.vyrbus.model.bean.LineaCreditoCliente;
import pe.itsb.vyrbus.model.bean.TitanFuncionarioPersonaPasaje;
import pe.itsb.vyrbus.model.bean.TitanPersona;
import pe.itsb.vyrbus.model.bean.TitanUsuarioPersonal;
import pe.itsb.vyrbus.model.bean.Usuario;
import pe.itsb.vyrbus.service.exceptions.ClienteException;
import pe.itsb.vyrbus.service.exceptions.FuncionarioNullException;
import pe.itsb.vyrbus.service.locator.ServiceLocator;
import pe.itsb.vyrbus.service.util.Constantes;
import pe.itsb.vyrbus.service.util.Messages;
import pe.itsb.vyrbus.service.util.MyTime;
import pe.itsb.vyrbus.service.util.Util;
import pe.itsb.vyrbus.service.util.UtilData;
import pe.itsb.vyrbus.view.ui.DlgMessage;
import pe.itsb.vyrbus.view.ui.WndBase;

/**
 *
 * @author José Abanto
 *
 */
public class WndReasignarCartera extends WndBase {

	private static final long serialVersionUID = 1L;

	private Combobox cmbFuncionarioAnterior;
	private Combobox cmbNuevoFuncionario;
	private Listbox listClientesAReasignar;
	private Listbox listClientesReasignados;
	private Image imIzqierda;
	private Image imIzquierdaIzq;
	private Image imDerecha;
	private Image imDerechaDer;
	private Button btnGuardar;

	String derechaDisabled="/resources/mp_rightArrowDisabled.png";
	String derechaDerDisabled="/resources/mp_allRightArrowDisabled.png";
	String derechaEnabled="/resources/mp_rightArrowEnabled.png";
	String derechaDerEnabled="/resources/mp_allRightArrowEnabled.png";

	String izquierdaDisabled="/resources/mp_leftArrowDisabled.png";
	String izquierdaIzqDisabled="/resources/mp_allLeftArrowDisabled.png";
	String izquierdaEnabled="/resources/mp_leftArrowEnabled.png";
	String izquierdaIzqEnabled="/resources/mp_allLeftArrowEnabled.png";


	//private Usuario usuario=new Usuario();

	/*
	 * (non-Javadoc)
	 * @see com.tepsa.sisvyr.view.ui.WndBase#initComponents()
	 */
	@Override
	public void initComponents() {
		listClientesAReasignar=(Listbox)this.getFellow("listClientesAReasignar");
		listClientesReasignados=(Listbox)this.getFellow("listClientesReasignados");
		cmbFuncionarioAnterior=(Combobox)this.getFellow("cmbFuncionarioAnterior");
		cmbNuevoFuncionario=(Combobox)this.getFellow("cmbNuevoFuncionario");
		imIzqierda=(Image)this.getFellow("imIzqierda");
		imIzquierdaIzq=(Image)this.getFellow("imIzquierdaIzq");
		imDerecha=(Image)this.getFellow("imDerecha");
		imDerechaDer=(Image)this.getFellow("imDerechaDer");
		btnGuardar=(Button)this.getFellow("btnGuardar");

		/*Evento SELECT Lista Clientes a Reasignar*/
		listClientesAReasignar.addEventListener(Events.ON_SELECT, new EventListener<Event>() {
			@Override
			public void onEvent(Event event) throws Exception {
				if(listClientesAReasignar.getSelectedItems().size()>=1)
					disabledFlecha(imDerecha,derechaEnabled);
				else
					disabledFlecha(imDerecha,derechaDisabled);
			}
		});
		/* Mover cliente(s) seleccionado(s) de la Derecha a izquierada*/
		imDerecha.addEventListener(Events.ON_CLICK,new EventListener<Event>() {
			@Override
			public void onEvent(Event event) throws Exception {
				if(imDerecha.getSrc().equals(derechaEnabled))
					addLists(listClientesAReasignar, true, listClientesReasignados, imDerecha, imDerechaDer, derechaDisabled, derechaDerDisabled);
				if(listClientesReasignados.getItems().size()>0)
					disabledFlecha(imIzquierdaIzq, izquierdaIzqEnabled);
			}
		});
		/* Mover todos los clientes de Derecha a izquierda*/
		imDerechaDer.addEventListener(Events.ON_CLICK,new EventListener<Event>() {
			@Override
			public void onEvent(Event event) throws Exception {
				if(imDerechaDer.getSrc().equals(derechaDerEnabled))
					addLists(listClientesAReasignar, false, listClientesReasignados, imDerecha, imDerechaDer, derechaDisabled, derechaDerDisabled);
				if(listClientesReasignados.getItems().size()>0)
					disabledFlecha(imIzquierdaIzq, izquierdaIzqEnabled);
			}
		});


		/*Evento Deshacer reasignación de cliente(s) seleccionado(s)*/
		imIzqierda.addEventListener(Events.ON_CLICK,new EventListener<Event>() {
			@Override
			public void onEvent(Event event) throws Exception {
				if(imIzqierda.getSrc().equals(izquierdaEnabled))
					addLists(listClientesReasignados, true, listClientesAReasignar, imIzqierda, imIzquierdaIzq, izquierdaDisabled, izquierdaIzqDisabled);
				if(listClientesAReasignar.getItems().size()>0)
					disabledFlecha(imDerechaDer, derechaDerEnabled);

			}
		});
		/*Deshacer reasignación de todos los clientes en la lista*/
		imIzquierdaIzq.addEventListener(Events.ON_CLICK,new EventListener<Event>() {
			@Override
			public void onEvent(Event event) throws Exception {
				if(imIzquierdaIzq.getSrc().equals(izquierdaIzqEnabled))
					addLists(listClientesReasignados, false, listClientesAReasignar, imIzqierda, imIzquierdaIzq, izquierdaDisabled, izquierdaIzqDisabled);
				if(listClientesAReasignar.getItems().size()>0)
					disabledFlecha(imDerechaDer, derechaDerEnabled);
			}
		});
		/*Evento SELECT Lista Clientes a Reasignados*/
		listClientesReasignados.addEventListener(Events.ON_SELECT,new EventListener<Event>() {
			@Override
			public void onEvent(Event event) throws Exception {
				if(listClientesReasignados.getSelectedItems().size()>=1)
					disabledFlecha(imIzqierda,izquierdaEnabled);
				else
					disabledFlecha(imIzqierda,izquierdaDisabled);
			}
		});
	}

	/*
	 * (non-Javadoc)
	 * @see com.tepsa.sisvyr.view.ui.WndBase#onCreate()
	 */
	@Override
	public void onCreate()throws Exception{
		UtilData.cargarFuncionarios(cmbFuncionarioAnterior, false,null);
		UtilData.cargarGenericData(cmbNuevoFuncionario, false);
//		UtilData.cargarFuncionarios(cmbNuevoFuncionario, true,null);
		cmbFuncionarioAnterior.setSelectedIndex(0);
		cmbNuevoFuncionario.setSelectedIndex(0);

		btnGuardar.setDisabled(accesoGrabar()?false:true);

	}

	/**
	 * Cargar los clientes en cartera.
	 * @throws Exception
	 */
	public void cargarClientes() throws Exception{
		try{
			Util.limpiarListbox(listClientesAReasignar);
			Util.limpiarListbox(listClientesReasignados);
			disabledFlecha(imDerechaDer,derechaDerDisabled);
			disabledFlecha(imIzquierdaIzq,izquierdaIzqDisabled);

			/*Carga los funcionarios a quien se le van a asignar los clientes, a exception del funcionario con la cartera actual */
			if(!(cmbFuncionarioAnterior.getSelectedItem().getValue() instanceof Usuario))
				throw new FuncionarioNullException();

//			TreeMap<String, Object> criteriosBusqueda = new TreeMap<String, Object>();
//			List<String> criteriosOrdenar = new ArrayList<String>();
//			criteriosOrdenar.add("nombre");
//			Rol rol=new Rol();
//			rol.setId(Constantes.ID_ROL_FUNCIONARIO);
//			criteriosBusqueda.put("rol", rol);
//			criteriosBusqueda.put("estadoRegistro", Constantes.VALUE_ACTIVO);
//			List<UsuarioRol>listUsuarioRol=ServiceLocator.getUsuarioRolManager().buscarPorX(criteriosBusqueda, null);
//			Util.limpiarCombobox(cmbNuevoFuncionario);
//			UtilData.cargarGenericData(cmbNuevoFuncionario, false);
//			for (UsuarioRol usuarioRol : listUsuarioRol) {
//				if(!(((Usuario)cmbFuncionarioAnterior.getSelectedItem().getValue()).getId().equals(usuarioRol.getUsuario().getId()))){
//					Usuario usuario=usuarioRol.getUsuario();
//					Comboitem oComboitem = new Comboitem();
//					oComboitem.setLabel(usuario.getNombre()+" "+usuario.getApellidoPaterno()+" "+ usuario.getApellidoMaterno());
//					oComboitem.setValue(usuario);
//					cmbNuevoFuncionario.appendChild(oComboitem);
//				}
//			}

			/*Carga Clientes*/
			TreeMap<String, Object>criteriosBusqueda = new TreeMap<>();
			criteriosBusqueda.put("usuario", (cmbFuncionarioAnterior.getSelectedItem().getValue()));
			criteriosBusqueda.put("estadoCartera", Constantes.ESTADOSOL_ACTIVA);
			List<CarteraCliente>list=ServiceLocator.getCarteraClienteManager().buscarPorX(criteriosBusqueda, null);

			Listitem item=new Listitem();
			Listcell cell=new Listcell();

			for(CarteraCliente carteraCliente: list){
				item=new Listitem();
				cell = new Listcell(carteraCliente.getCliente().getRazonSocial());
				item.appendChild(cell);

				item.setValue(carteraCliente);
				listClientesAReasignar.appendChild(item);
			}

			Util.limpiarCombobox(cmbNuevoFuncionario);
			if(list.size()>0)
				/*Carga todos los funcionarios a excepcion el funcionario del cual se va a realizar la reasignacion*/
				UtilData.cargarFuncionarios(cmbNuevoFuncionario, false, ((Usuario)cmbFuncionarioAnterior.getSelectedItem().getValue()).getId());
			else
				UtilData.cargarGenericData(cmbNuevoFuncionario, false);

			if(listClientesAReasignar.getItems().size()>0)
				disabledFlecha(imDerechaDer,derechaDerEnabled);

		}catch (FuncionarioNullException fnex){
			DlgMessage.information(Messages.getString("wndReasignacionCartera.information.noSelecFuncionarioOr"));
		}
	}

	/**
	 * Habilita o deshabilita fechas
	 * @param image	: Imagen
	 * @param src	: ruta de la imagen.
	 */
	private void disabledFlecha(Image image, String src){
		image.setSrc(src);
		if (src.equals(derechaDisabled) || src.equals(derechaDerDisabled) || src.equals(izquierdaDisabled) || src.equals(izquierdaIzqDisabled)){
			image.setStyle("cursor:default");
			if(image.getId().equals("imDerecha"))
				image.setTooltiptext("");
			else if(image.getId().equals("imDerechaDer"))
				image.setTooltiptext("");
			else if(image.getId().equals("imIzquierda"))
				image.setTooltiptext("");
			else if(image.getId().equals("imIzquierdaIzq"))
				image.setTooltiptext("");
		}else{
			image.setStyle("cursor:pointer");
			if(image.getId().equals("imDerecha"))
				image.setTooltiptext("Reasignar cliente(s) seleccionado(s)");
			else if(image.getId().equals("imDerechaDer"))
				image.setTooltiptext("Reasignar todos los clientes de la lista");
			else if(image.getId().equals("imIzquierda"))
				image.setTooltiptext("Deshacer reasignación de cliente(s) seleccionado(s)");
			else if(image.getId().equals("imIzquierdaIzq"))
				image.setTooltiptext("Deshacer reasignación de todos los clientes en la lista");
		}

	}

	/**
	 * Agregar clientes a la lista.
	 * @param listboxOrigen : Listbox Origen.
	 * @param seleccionados : (true) indica que se agregaran solo los cliente seleccionados; (false) todos los clientes de la lista.
	 * @param listboxDestino: Listbox Destino.
	 * @param image			: Objeto Image.
	 * @param src			: Ruta de la imagen.
	 */
	private void addLists(Listbox listboxOrigen, Boolean seleccionados, Listbox listboxDestino, Image image,Image imageAll, String src, String srcAll){
			List<Listitem> listRemove= new ArrayList<>();

			for(Listitem iteml: seleccionados?listboxOrigen.getSelectedItems() :listboxOrigen.getItems()){
				CarteraCliente carteraCliente=iteml.getValue();
				Listitem item=new Listitem();
				Listcell cell=new Listcell(carteraCliente.getCliente().getRazonSocial());
				item.appendChild(cell);

				item.setValue(carteraCliente);
				listboxDestino.appendChild(item);
				listRemove.add(iteml);
			}
			//Elimina los ítems seleccionados
			for(Listitem item: listRemove){
				listboxOrigen.removeItemAt(item.getIndex());
			}
			if(seleccionados){
				if(listboxOrigen.getItems().size()==0)
					disabledFlecha(imageAll, srcAll);
			}else{
				disabledFlecha(imageAll, srcAll);
			}

			disabledFlecha(image, src );
	}

	/**
	 * Guarda la reasignación.
	 * @throws Exception
	 */
	public void onSave()throws Exception{
		try{
			if (!(cmbNuevoFuncionario.getSelectedItem().getValue() instanceof Usuario))
				throw new FuncionarioNullException();
			else if(listClientesReasignados.getItems().size()==0)
				throw new ClienteException(ClienteException.CLIENTE_NULL);
			else if(cmbFuncionarioAnterior.getSelectedItem().getValue().equals(cmbNuevoFuncionario.getSelectedItem().getValue()))
				throw new ClienteException(ClienteException.ASIGNACION_CARTERA);

			Messagebox.show(Messages.getString("wndReasignacionCartera.question.confirmarReasignacion"), DlgMessage.NOMBREAPLICACION, DlgMessage.BTN_YESNO, Messagebox.QUESTION, new EventListener<Event>() {
				@Override
				public void onEvent(Event event) throws Exception {
					if(event.getName().equals("onYes")){
						for (Listitem  item: listClientesReasignados.getItems()){
							CarteraCliente carteraCliente=item.getValue();
							reasignaCartera(carteraCliente, ((Usuario)cmbNuevoFuncionario.getSelectedItem().getValue()));
						}
						cmbNuevoFuncionario.setSelectedIndex(0);
						cmbFuncionarioAnterior.setSelectedIndex(0);
						Util.limpiarListbox(listClientesAReasignar);
						Util.limpiarListbox(listClientesReasignados);
						disabledFlecha(imIzquierdaIzq, izquierdaIzqDisabled);
					}
				}
			});

		}catch (ClienteException cl){
			if(cl.getTipo().intValue()==ClienteException.ASIGNACION_CARTERA){
				DlgMessage.information(Messages.getString("wndReasignacionCartera.information.funcionariosIguales"),cmbNuevoFuncionario);
			}else if (cl.getTipo().intValue()==ClienteException.CLIENTE_NULL){
				DlgMessage.information(Messages.getString("wndReasignacionCartera.information.noClientesReasg"));
			}
		}catch (FuncionarioNullException fnex){
			DlgMessage.information(Messages.getString("wndReasignacionCartera.information.noSelecFuncionarioDs"),cmbNuevoFuncionario);
		}catch (Exception e) {
			DlgMessage.error(this.getClass().getName()+" "+e.getMessage());
			e.printStackTrace();
		}
	}

	/**
	 * Realiza la reasignacion de cartera de los clientes.
	 * @param carteraCliente : Class CarteraCliente
	 * @param funcionario	 : Funcionario a quien se le asignarán los clientes.
	 * @throws Exception
	 */
	private void reasignaCartera(CarteraCliente carteraCliente, Usuario funcionario) throws Exception{
		Date fecha=Constantes.FORMAT_DATE_TIME_24H.parse(new MyTime().dateServer());
		long lDefault=Constantes.FORMAT_DATE_TIME_24H.parse(Constantes.FECHA_DEFAULT).getTime();
		Date fDefault= new Date(lDefault);

		/*Inactiva el registro actual de la cartea.*/
		carteraCliente.setEstadoCartera(Constantes.ESTADOSOL_INACTIVA);
		carteraCliente.setFechaSuspension(fecha);
		UtilData.auditarRegistro(carteraCliente, true, getUsuario(), Executions.getCurrent());
		ServiceLocator.getCarteraClienteManager().actualizar(carteraCliente);
		/*Inserta un nuevo registro con los cambios de la reasignacion*/
		CarteraCliente ocarteraCliente= new CarteraCliente();
		ocarteraCliente.setUsuario(funcionario);
		ocarteraCliente.setCliente(carteraCliente.getCliente());
		ocarteraCliente.setSolicitudCartera(carteraCliente.getSolicitudCartera());
		ocarteraCliente.setBaseHistorica(carteraCliente.getBaseHistorica());///*POR CONFIRMAR SI SE TIENE QUE CALCULAR NUEVAMENTE O SERA LA MISMA*///
		ocarteraCliente.setFechaAsignacion(fecha);
		ocarteraCliente.setFechaSuspension(fDefault);
		ocarteraCliente.setEstadoRegistro(Constantes.VALUE_ACTIVO);
		ocarteraCliente.setEstadoCartera(Constantes.ESTADOSOL_ACTIVA);
		UtilData.auditarRegistro(ocarteraCliente, getUsuario(), Executions.getCurrent());
		ServiceLocator.getCarteraClienteManager().guardar(ocarteraCliente);


		/*Valida si se trata de un cliente Contado*/
		TreeMap<String, Object> criteriosBusqueda = new TreeMap<>();
		criteriosBusqueda.put("carteraCliente", carteraCliente);
		criteriosBusqueda.put("estadoRegistro", Constantes.VALUE_ACTIVO);
		List<LineaContadoCliente> listcc=ServiceLocator.getLineaContadoClienteManager().buscarPorX(criteriosBusqueda, null);
		if(listcc.size()>0) { //Cliente contado
			LineaContadoCliente lineaContado=listcc.get(0);
			/*Inactiva la linea contado cliente actual*/
			lineaContado.setFechaCaducidad(fecha);
			lineaContado.setFechaSuspension(fecha);
			lineaContado.setEstadoLineaContado(Constantes.ESTADOSOL_INACTIVA);
			lineaContado.setEstadoRegistro(Constantes.VALUE_INACTIVO);
			UtilData.auditarRegistro(lineaContado, true, getUsuario(), Executions.getCurrent());
			ServiceLocator.getLineaContadoClienteManager().actualizar(lineaContado);
			/*Inserta un nuevo registro, asociado a la reacignacion de cartera*/
			LineaContadoCliente oLineaContado=new LineaContadoCliente();
			oLineaContado.setCarteraCliente(ocarteraCliente);
			oLineaContado.setSolicitudCartera(lineaContado.getSolicitudCartera());
			oLineaContado.setDescuentoAlta(lineaContado.getDescuentoAlta());
			oLineaContado.setDescuentoBaja(lineaContado.getDescuentoBaja());
			oLineaContado.setEsComisionable(lineaContado.getEsComisionable());
			oLineaContado.setFechaActivacion(fecha);
			oLineaContado.setFechaCaducidad(fDefault);
			oLineaContado.setEstadoLineaContado(Constantes.ESTADOSOL_ACTIVA);
			oLineaContado.setEstadoRegistro(Constantes.VALUE_ACTIVO);
			UtilData.auditarRegistro(oLineaContado, getUsuario(), Executions.getCurrent());
			ServiceLocator.getLineaContadoClienteManager().guardar(oLineaContado);

		}else{ //Evalua si es un Cliente Crédito
			 criteriosBusqueda = new TreeMap<>();
			criteriosBusqueda.put("carteraCliente", carteraCliente);
			criteriosBusqueda.put("estadoRegistro", Constantes.VALUE_ACTIVO);
			List<LineaCreditoCliente> listccr=ServiceLocator.getLineaCreditoClienteManager().buscarPorX(criteriosBusqueda, null);
			if(listccr.size()>0){
				LineaCreditoCliente lineaCredito=listccr.get(0);
				/*Inactiva el registro actual de la Linea de Credito*/
				lineaCredito.setFechaInactivacion(fecha);
				lineaCredito.setEstadoLineaCredito(Constantes.ESTADOSOL_INACTIVA);
				lineaCredito.setEstadoRegistro(Constantes.VALUE_INACTIVO);
				UtilData.auditarRegistro(lineaCredito, true, getUsuario(), Executions.getCurrent());
				ServiceLocator.getLineaCreditoClienteManager().actualizar(lineaCredito);
				/*Inserta un nuevo registro, asociado a al reasignacion de cartera*/
				LineaCreditoCliente olineaCredito=new LineaCreditoCliente();
				olineaCredito.setCarteraCliente(ocarteraCliente);
				olineaCredito.setTipoCobranza(lineaCredito.getTipoCobranza());
				olineaCredito.setSolicitudClienteCredito(lineaCredito.getSolicitudClienteCredito());
				olineaCredito.setLineaCreditoSolicitada(lineaCredito.getLineaCreditoSolicitada());
				olineaCredito.setLineaCreditoAprobada(lineaCredito.getLineaCreditoAprobada());
				olineaCredito.setSobregiro(lineaCredito.getSobregiro());
				olineaCredito.setSaldo(lineaCredito.getSaldo());
				olineaCredito.setFechaActivacion(fecha);
				olineaCredito.setFechaCaducidad(fDefault);
				olineaCredito.setEstadoLineaCredito(Constantes.ESTADOSOL_ACTIVA);
				olineaCredito.setEsCanje(lineaCredito.getEsCanje());
				olineaCredito.setEsComisionable(lineaCredito.getEsComisionable());
				olineaCredito.setEstadoRegistro(Constantes.VALUE_ACTIVO);
				UtilData.auditarRegistro(olineaCredito, getUsuario(), Executions.getCurrent());
				ServiceLocator.getLineaCreditoClienteManager().guardar(olineaCredito);


				/**==============================================IMPL- JABANTO - 20/11/2014 ===============================================*/
				/* Replica la reasignacion en TITAN  */
				TitanUsuarioPersonal titanUsuarioPersonalRegistro=ServiceLocator.getTitanManager().buscarUsuarioPersonalPorLogin(getUsuario().getLogin());
				//Busca el funcionario por su login.
				TitanUsuarioPersonal titanFuncionarioPasajes = ServiceLocator.getTitanManager().buscarUsuarioPersonalPorLogin(carteraCliente.getUsuario().getLogin());
				if(titanFuncionarioPasajes==null){
					//Registra el funcionario en Titan
					titanFuncionarioPasajes=new TitanUsuarioPersonal();
					titanFuncionarioPasajes.setNombres(carteraCliente.getUsuario().getNombre());
					titanFuncionarioPasajes.setApellidoPaterno(carteraCliente.getUsuario().getApellidoPaterno());
					titanFuncionarioPasajes.setApellidoMaterno(carteraCliente.getUsuario().getApellidoMaterno());
					titanFuncionarioPasajes.setLogin(carteraCliente.getUsuario().getLogin());
					ServiceLocator.getTitanManager().guardarUsuarioPersonal(titanFuncionarioPasajes);
				}

				//Busca el cliente por el numero de RUC.
				TitanPersona titanPersona= ServiceLocator.getTitanManager().buscarPersonaPorRuc(carteraCliente.getCliente().getNumeroDocumento());
				if(titanPersona!=null){
					//Valida si el cliente esta actualmente en alguna cartera
					TitanFuncionarioPersonaPasaje funcionarioPersonaPasaje=ServiceLocator.getTitanManager().buscarFuncionarioPersonaPasajePorIdPersona(titanPersona.getId());
					if(funcionarioPersonaPasaje!=null){
						funcionarioPersonaPasaje.setFuncionario(titanFuncionarioPasajes);
						funcionarioPersonaPasaje.setFuncionarioActual(titanFuncionarioPasajes);
						funcionarioPersonaPasaje.setUsuarioPersonal(titanUsuarioPersonalRegistro);
						funcionarioPersonaPasaje.setIp(UtilData.ipLocal(Executions.getCurrent()));
						ServiceLocator.getTitanManager().actualizaFuncionarioPerosnaPasajes(funcionarioPersonaPasaje);
					}
					/**==============================================END IMPL- JABANTO - 20/11/2014 ===============================================*/
				}


			}
		}
	}
}
