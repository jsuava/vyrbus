package com.cystesoft.vyrbus.view.ctrl;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Comboitem;
import org.zkoss.zul.Textbox;

import com.cystesoft.vyrbus.model.bean.Agencia;
import com.cystesoft.vyrbus.model.bean.CanalVenta;
import com.cystesoft.vyrbus.model.bean.TitanUsuarioHardware;
import com.cystesoft.vyrbus.model.bean.UsuarioHardware;
import com.cystesoft.vyrbus.service.exceptions.AgenciaNullException;
import com.cystesoft.vyrbus.service.exceptions.AgenciaTranscarNullException;
import com.cystesoft.vyrbus.service.exceptions.CancelaGrabacionException;
import com.cystesoft.vyrbus.service.exceptions.CodigoDuplicadoException;
import com.cystesoft.vyrbus.service.exceptions.CodigoNullException;
import com.cystesoft.vyrbus.service.exceptions.DireccionIPNullException;
import com.cystesoft.vyrbus.service.exceptions.TipoIPNullException;
import com.cystesoft.vyrbus.service.exceptions.UsuarioHardwareCanalVentaNullException;
import com.cystesoft.vyrbus.service.exceptions.UsuarioHardwareDescripcionNullException;
import com.cystesoft.vyrbus.service.exceptions.UsuarioHardwareDuplicidadDescripcionException;
import com.cystesoft.vyrbus.service.locator.ServiceLocator;
import com.cystesoft.vyrbus.service.util.Constantes;
import com.cystesoft.vyrbus.service.util.Messages;
import com.cystesoft.vyrbus.service.util.Util;
import com.cystesoft.vyrbus.service.util.UtilData;
import com.cystesoft.vyrbus.view.ui.DlgMessage;
import com.cystesoft.vyrbus.view.ui.WndFiltrarParametros;
import com.cystesoft.vyrbus.view.ui.WndOpcionesMantenimiento;

/**
 * 
 * @author José Abanto
 *
 */
public class WndUsuarioHardware extends WndOpcionesMantenimiento{
	private static final long serialVersionUID = -4363466393354816965L;
	
	private Combobox cmbCanalVenta;
	private Combobox cmbAgencia;
	private Textbox	txtCodigo;
	private Textbox txtDireccionMAC;
	private Textbox txtDescripcion;
	private Textbox txtDireccionIP;
	private Combobox cmbTipoIP;
	
	private UsuarioHardware usuarioHardware=null;
	private TreeMap<String, Object> criteriosBusqueda = new TreeMap<String, Object>();
	private List<String> criteriosOrdenar = null;
	
	public static String MD2 = "MD2";
	public static String MD5 = "MD5";
	public static String SHA1 = "SHA-1";
	public static String SHA256 = "SHA-256";
	public static String SHA384 = "SHA-384";
	public static String SHA512 = "SHA-512";

	/*
	 * (non-Javadoc)
	 * @see com.tepsa.sisvyr.view.ui.IBase#initComponents()
	 */
	@Override
	public void initComponents() {
		cmbCanalVenta = (Combobox) this.getFellow("cmbCanalVenta");
		cmbAgencia = (Combobox) this.getFellow("cmbAgencia");
		txtCodigo = (Textbox) this.getFellow("txtCodigo");
		txtDescripcion = (Textbox) this.getFellow("txtDescripcion");
		txtDireccionMAC = (Textbox)this.getFellow("txtDireccionMAC");
		txtDireccionIP = (Textbox)this.getFellow("txtDireccionIP");
		cmbTipoIP = (Combobox) this.getFellow("cmbTipoIP");
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.tepsa.sisvyr.view.ui.IBase#onCreate()
	 */
	@Override
	public void onCreate() throws Exception {
		UtilData.cargarDataCombo(cmbCanalVenta, CanalVenta.class, false);
		UtilData.cargarDataCombo(cmbAgencia, Agencia.class, false);
		onLoadTipoIP();
		
		criteriosOrdenar = new ArrayList<String>();
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.tepsa.sisvyr.view.ui.IOpcionesMantenimiento#onNew()
	 */
	@Override
	public void onNew() throws Exception {
		cmbCanalVenta.setSelectedIndex(0);
		cmbAgencia.setSelectedIndex(0);
	}

	/*
	 * (non-Javadoc)
	 * @see com.tepsa.sisvyr.view.ui.IOpcionesMantenimiento#onSearch()
	 */
	@Override
	public void onSearch() throws Exception {
		final WndFiltrarParametros oWndFiltrar = new WndFiltrarParametros();
		criteriosOrdenar = new ArrayList<String>();
		criteriosOrdenar.add("codigo");
		criteriosOrdenar.add("descripcion");
		
		oWndFiltrar.addParameter("Agencia", Agencia.class);
		oWndFiltrar.addParameter("Canal de Venta", CanalVenta.class);
		oWndFiltrar.addParameter("Código", String.class);
		oWndFiltrar.addParameter("Descripcion", String.class);
		
		this.appendChild(oWndFiltrar);
		oWndFiltrar.setMode("modal");
		oWndFiltrar.addEventListener(com.cystesoft.vyrbus.view.ui.Events.ON_FILTER, new EventListener<Event>() {
			
			@Override
			public void onEvent(Event event) throws Exception {
				criteriosBusqueda = new TreeMap<String, Object>();
				Agencia agencia = (Agencia) oWndFiltrar.getParameterValue("Agencia");
				CanalVenta canalVenta = (CanalVenta) oWndFiltrar.getParameterValue("Canal de Venta");
				String codigo = oWndFiltrar.getParameterValue("Código").toString();
				String descripcion = oWndFiltrar.getParameterValue("Descripcion").toString();
				String estadoRegistro = Constantes.VALUE_ACTIVO;
				
				if (agencia==null){
					criteriosBusqueda.remove("agencia");
				}else {
//					Agencia oagencia = new Agencia();
//					oagencia.setId(agencia);
					criteriosBusqueda.put("agencia", agencia);
				}
												
				if (canalVenta == null )
					criteriosBusqueda.remove("canalVenta");
				else
					criteriosBusqueda.put("canalVenta", canalVenta);
				
				if (codigo==null)
					criteriosBusqueda.remove("codigo");
				else
					criteriosBusqueda.put("codigo", codigo + "%" );
				
				if (descripcion==null)
					criteriosBusqueda.remove("descripcion");
				else
					criteriosBusqueda.put("descripcion", descripcion + "%");
								
				criteriosBusqueda.put("estadoRegistro", estadoRegistro);

				listarRegistros(ServiceLocator.getUsuarioHardwareManager().buscarPorX(criteriosBusqueda, criteriosOrdenar));
			}
		});
		
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.tepsa.sisvyr.view.ui.IOpcionesMantenimiento#onRefresh(int)
	 */
	@Override
	public void onRefresh(int tab) throws Exception {
		if (!criteriosBusqueda.isEmpty()) {
			this.listarRegistros(ServiceLocator.getUsuarioHardwareManager().buscarPorX(criteriosBusqueda, criteriosOrdenar));					
		}
		
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.tepsa.sisvyr.view.ui.IOpcionesMantenimiento#onModify(int)
	 */
	@Override
	public void onModify(int tab) throws Exception {
		mantenimiento();
	}

	/*
	 * (non-Javadoc)
	 * @see com.tepsa.sisvyr.view.ui.IOpcionesMantenimiento#onCancel(int)
	 */
	@Override
	public void onCancel(int action) throws Exception {
		
	}
	
	/*
	 * (non-Javadoc)
	 * @see org.zkoss.zul.Window#onClose()
	 */
	@Override
	public void onClose() {
		closeTabWindow();
	}
	
	@Override
	public void onSave(int action) throws Exception {
		try{
			if (!(cmbCanalVenta.getSelectedItem().getValue() instanceof CanalVenta))
				throw new  UsuarioHardwareCanalVentaNullException();
			else if (cmbAgencia.getSelectedIndex()<=0)
				throw new AgenciaNullException();
			else if (txtDireccionIP.getValue().trim() == "")
				throw new DireccionIPNullException();
			else if (cmbTipoIP.getSelectedIndex() <=0)
				throw new TipoIPNullException();
			else if (txtDireccionMAC.getValue().trim() == "")
				throw new CodigoNullException();
			else if (txtDescripcion.getValue().trim() =="")
				throw new UsuarioHardwareDescripcionNullException();
			
			if (action==ACTION_NEW)
				usuarioHardware=new UsuarioHardware();
			
			Agencia agencia = new Agencia();
			agencia.setId(((Agencia)cmbAgencia.getSelectedItem().getValue()).getId());
			CanalVenta canalVenta = new CanalVenta();
			canalVenta.setId(((CanalVenta) cmbCanalVenta.getSelectedItem().getValue()).getId());
			Integer id = (textboxId.getText().equals("") ? 0 : new Integer(textboxId.getText()));
			
			generarCodigo(txtDireccionMAC.getText().trim().toUpperCase());
			
			usuarioHardware.setId(id);
			usuarioHardware.setAgencia(agencia);
			usuarioHardware.setCanalVenta(canalVenta);
			usuarioHardware.setDireccionMAC(txtDireccionMAC.getText().trim().toUpperCase());
			usuarioHardware.setCodigo(txtCodigo.getValue().trim());
			usuarioHardware.setDescripcion(txtDescripcion.getValue().trim().toUpperCase());
			usuarioHardware.setEstadoRegistro(Constantes.VALUE_ACTIVO);
			
			TitanUsuarioHardware titanUsuarioHardware = new TitanUsuarioHardware();
			titanUsuarioHardware.setIp(txtDireccionIP.getText().trim());
			titanUsuarioHardware.setIdDepartamento(1);
			titanUsuarioHardware.setIdTipoMaquina(1);
			titanUsuarioHardware.setFrecuenciaReloj(0);
			titanUsuarioHardware.setNombreEquipo(txtDescripcion.getText());
			titanUsuarioHardware.setEsServidor(0);
			titanUsuarioHardware.setParticiones(0);
			titanUsuarioHardware.setMemoria(0);
			titanUsuarioHardware.setIdUsuario(1);
			titanUsuarioHardware.setIdRol(1);
			titanUsuarioHardware.setIdUsuarioModificacion(1);
			titanUsuarioHardware.setRolModificacion(1);
			titanUsuarioHardware.setIpRegistro("10.10.10.1");
			titanUsuarioHardware.setIpModificacion("10.10.10.1");
			titanUsuarioHardware.setIdAgencia(id);
			titanUsuarioHardware.setIdTipoComputador(1);
			titanUsuarioHardware.setIdTipoIP(cmbTipoIP.getSelectedItem().getValue());
			
			usuarioHardware.setTitanUsuarioHardware(titanUsuarioHardware);
			
			switch (action) {
				case ACTION_NEW:
					UtilData.auditarRegistro(usuarioHardware, getUsuario(), Executions.getCurrent());
					ServiceLocator.getUsuarioHardwareManager().guardar(usuarioHardware);
					break;
	
				case ACTION_MODIFY:
					UtilData.auditarRegistro(usuarioHardware, true, getUsuario(), Executions.getCurrent());
					ServiceLocator.getUsuarioHardwareManager().actualizar(usuarioHardware);
					break;
			}
						
			/*Recupera el Usuario Harware Actualizado o Nuevo*/
			criteriosBusqueda = new TreeMap<String, Object>();
			criteriosBusqueda.put("codigo", usuarioHardware.getCodigo());
			listarRegistros(ServiceLocator.getUsuarioHardwareManager().buscarPorX(criteriosBusqueda, criteriosOrdenar));
			
		}catch (UsuarioHardwareCanalVentaNullException uhcvnex){
			DlgMessage.information(Messages.getString("Generales.information.noSeleccionoCanalVenta"),cmbCanalVenta);
			throw new CancelaGrabacionException();
		}catch (AgenciaNullException anex){
			DlgMessage.information(Messages.getString("Generales.information.noSeleccionoAgencia"),cmbAgencia);
			throw new CancelaGrabacionException();
		}catch (CodigoNullException cnex){
			DlgMessage.information(Messages.getString("Generales.information.noIngresoCodigo"),txtCodigo);
			throw new CancelaGrabacionException();
		}catch (DireccionIPNullException dipnex){
			DlgMessage.information(Messages.getString("Generales.information.noIngresoIP"),txtCodigo);
			throw new CancelaGrabacionException();
		}catch (TipoIPNullException tipnex){
			DlgMessage.information(Messages.getString("Generales.information.noSeleccionoTipoIP"),txtCodigo);
			throw new CancelaGrabacionException();
		}catch (UsuarioHardwareDescripcionNullException uhdnex){
			DlgMessage.information(Messages.getString("Generales.information.noIngresoDescripcion"),txtDescripcion);
			throw new CancelaGrabacionException();
		}catch (CodigoDuplicadoException cdex){
			DlgMessage.information(Messages.getString("Generales.information.codigoDuplicado"),txtCodigo);
			throw new CancelaGrabacionException();
		}catch (UsuarioHardwareDuplicidadDescripcionException uhddex){
			DlgMessage.information(Messages.getString("Generales.information.descripcionDuplicada"),txtDescripcion);
			throw new CancelaGrabacionException();
		}catch(AgenciaTranscarNullException atnex) {
			DlgMessage.information("La agencia no esta registrada en el Sistema de Carga");
			throw new CancelaGrabacionException();
		}catch (Exception ex) {
			DlgMessage.error(this.getClass().getName()+" "+ex.getMessage());
			ex.printStackTrace();throw new CancelaGrabacionException();
		}
		
	}

	/*
	 * (non-Javadoc)
	 * @see com.tepsa.sisvyr.view.ui.IOpcionesMantenimiento#onDelete(int)
	 */
	@Override
	public void onDelete(int tab) throws Exception {
		Long id = (long) 0;

		switch (tab) {
			case TAB_LIST:
				id = new Long((String) listboxLista.getSelectedItem().getValue());
				break;

			case TAB_MAINTENANCE:
				id = new Long(textboxId.getText());
				break;
		}

		ServiceLocator.getUsuarioHardwareManager().inactivar(id);
		
	}

	/*
	 * (non-Javadoc)
	 * @see com.tepsa.sisvyr.view.ui.IOpcionesMantenimiento#onPrint(int)
	 */
	@Override
	public void onPrint(int tab) throws Exception {
		// TODO Auto-generated method stub
		
	}

	/*
	 * (non-Javadoc)
	 * @see com.tepsa.sisvyr.view.ui.IOpcionesMantenimiento#onExport(int)
	 */
	@Override
	public void onExport(int tab) throws Exception {
		// TODO Auto-generated method stub
		
	}

	/*
	 * (non-Javadoc)
	 * @see com.tepsa.sisvyr.view.ui.IOpcionesMantenimiento#onHelp()
	 */
	@Override
	public void onHelp() throws Exception {
		// TODO Auto-generated method stub
		
	}

	/*
	 * (non-Javadoc)
	 * @see com.tepsa.sisvyr.view.ui.IOpcionesMantenimiento#onChangeTab(int)
	 */
	@Override
	public void onChangeTab(int tab) throws Exception {
		mantenimiento();
		
	}
	
	/**
	 * Recupera lista de registros, según la busqueda.
	 * @param lstRegistros
	 */
	private void listarRegistros(ArrayList<UsuarioHardware> lstRegistros) {
		ArrayList<Object> lstEspeciesValoradas = new ArrayList<Object>();

		for(int r = 0; r < lstRegistros.size(); r ++) {
			UsuarioHardware usuarioHardware  = lstRegistros.get(r);
			ArrayList<Object> lstFila = new ArrayList<Object>();

			lstFila.add(usuarioHardware.getId());
			lstFila.add(r + 1);
			lstFila.add(usuarioHardware.getDireccionMAC());
			lstFila.add(usuarioHardware.getCodigo());
			lstFila.add(usuarioHardware.getDescripcion());
			lstFila.add(usuarioHardware.getAgencia().getDenominacion());
			lstFila.add(usuarioHardware.getCanalVenta().getDenominacion());

			lstEspeciesValoradas.add(lstFila);
		}

		Util.llenarListbox(listboxLista, lstEspeciesValoradas, true);
	}
	
	/**
	 * Recupera datos para la edición.
	 */
	private void mantenimiento(){
		if(listboxLista.getSelectedIndex()>=0){
			usuarioHardware = new UsuarioHardware();
			usuarioHardware = ServiceLocator.getUsuarioHardwareManager().buscarPorId(new Long((String) listboxLista.getSelectedItem().getValue()));
			
			textboxId.setText(usuarioHardware.getId().toString());
			Util.seleccionarValorItemCombo(Agencia.class, cmbAgencia, usuarioHardware.getAgencia().getId());
			Util.seleccionarValorItemCombo(CanalVenta.class, cmbCanalVenta, usuarioHardware.getCanalVenta().getId());
			txtCodigo.setValue(usuarioHardware.getCodigo());
			txtDireccionMAC.setText(usuarioHardware.getDireccionMAC());
			txtDescripcion.setValue(usuarioHardware.getDescripcion());
		}
		
	}
	
	/**
     * Encripta un mensaje de texto mediante algoritmo de resumen de mensaje.
     * @return Codigo encriptado.
     */
	public String generarCodigo(String message){
		byte[] digest = null;
        byte[] buffer = message.trim().getBytes();
        try {
            MessageDigest messageDigest = MessageDigest.getInstance(SHA256);
            messageDigest.reset();
            messageDigest.update(buffer);
            digest = messageDigest.digest();
        }catch (NoSuchAlgorithmException ex) {
            DlgMessage.information(Messages.getString("Generales.information.noSuchAlgorithm"));
        }
        String hash = toHexadecimal(digest);
        txtCodigo.setText(hash);
        return toHexadecimal(digest);
	}
	
	/**
     * Convierte un arreglo de bytes a String usando valores hexadecimales
     * @param digest arreglo de bytes a convertir
     * @return String creado a partir de <code>digest</code>
     */
    private String toHexadecimal(byte[] digest){
        String hash = "";
        for(byte aux : digest) {
            int b = aux & 0xff;
            if (Integer.toHexString(b).length() == 1) hash += "0";
            hash += Integer.toHexString(b);
        }
        return hash;
    }
    
    private void onLoadTipoIP() {
    	UtilData.cargarGenericData(cmbTipoIP, false);
		Comboitem item = new Comboitem();
		item.setLabel("FIJO");
		item.setValue(1);
		cmbTipoIP.appendChild(item);
		item = new Comboitem();
		item.setLabel("MOVIL");
		item.setValue(2);
		cmbTipoIP.appendChild(item);		
    }
}
