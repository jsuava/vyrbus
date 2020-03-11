/**
 * Proyecto		: SISVYR
 * Sistema		: Sistema de Ventas y Reservas
 * Descripción	: 
 * Autor		: José Abanto
 * Fecha		: 13/04/2015
 * Hora			: 16:30:17
 */
package com.cystesoft.vyrbus.view.ctrl;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zul.Button;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Comboitem;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Textbox;

import com.cystesoft.vyrbus.model.bean.Agencia;
import com.cystesoft.vyrbus.model.bean.ConfiguracionImpresora;
import com.cystesoft.vyrbus.model.bean.UsuarioHardware;
import com.cystesoft.vyrbus.service.locator.ServiceLocator;
import com.cystesoft.vyrbus.service.util.Constantes;
import com.cystesoft.vyrbus.service.util.Messages;
import com.cystesoft.vyrbus.service.util.Util;
import com.cystesoft.vyrbus.service.util.UtilData;
import com.cystesoft.vyrbus.view.ui.DlgMessage;
import com.cystesoft.vyrbus.view.ui.WndBase;

/**
 * @author jabanto
 *
 */
public class WndConfPrinter extends WndBase{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Combobox cmbAgencia;
	private Combobox cmbUsuarioHardware;
	private Textbox txtNombreImpresora;
	private Combobox cmbPuerto;
	private Combobox cmbBistPorSegundo;
	private Combobox cmbBistDatos;
	private Combobox cmbParidad;
	private Combobox cmbBistParada;
	private Button btnGuardar;
//	private Button btnNuevo;
	
	
	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.view.ui.WndBase#initComponents()
	 */
	@Override
	public void initComponents() {
		// TODO Auto-generated method stub
		cmbAgencia=(Combobox)this.getFellow("cmbAgencia");
		cmbUsuarioHardware=(Combobox)this.getFellow("cmbUsuarioHardware");
		txtNombreImpresora=(Textbox)this.getFellow("txtNombreImpresora");
		cmbPuerto=(Combobox)this.getFellow("cmbPuerto");
		cmbBistPorSegundo=(Combobox)this.getFellow("cmbBistPorSegundo");
		cmbBistDatos=(Combobox)this.getFellow("cmbBistDatos");
		cmbParidad=(Combobox)this.getFellow("cmbParidad");
		cmbBistParada=(Combobox)this.getFellow("cmbBistParada");
		btnGuardar=(Button)this.getFellow("btnGuardar");
//		btnNuevo=(Button)this.getFellow("btnNuevo");
	}
	
	
	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.view.ui.WndBase#onCreate()
	 */
	@Override
	public void onCreate() throws Exception {
		// TODO Auto-generated method stub
		UtilData.cargarDataCombo(cmbAgencia, Agencia.class, false);
		Util.seleccionarValorItemCombo(Agencia.class, cmbAgencia, getAgencia().getId());
		/*carga los usuarios hardware asociados a la agencia*/
		onchange_usuarioHardware();
		
		/*Carga los puestos*/
		UtilData.cargarGenericData(cmbPuerto, false);
		for(int x=1;x<=20;x++){
			String id="COM"+String.valueOf(x);
			Comboitem comboitem= new Comboitem(id);
			comboitem.setValue(id);
			cmbPuerto.appendChild(comboitem);
		}
		cmbPuerto.setSelectedIndex(0);
		
		/*carga Bist por segundo*/
		UtilData.cargarGenericData(cmbBistPorSegundo, false);
		String arrayBistSegundo[] = {"75", "110", "134", "150", "300", "600", "1200", "1800", "2400", "4800", "7200", "9600", "14400", "19200",
            "38400", "57600", "11520", "12800"};
		for(String bistSegundo: arrayBistSegundo){
			Comboitem comboitem= new Comboitem(bistSegundo);
			comboitem.setValue(Long.valueOf(bistSegundo));
			cmbBistPorSegundo.appendChild(comboitem);
		}
		cmbBistPorSegundo.setSelectedIndex(0);
		
		/*carga los bist de datos*/
		UtilData.cargarGenericData(cmbBistDatos, false);
		String arrayBistDatos[] = {"5", "6", "7", "8"};
		for(String bistDatos: arrayBistDatos){
			Comboitem comboitem= new Comboitem(bistDatos);
			comboitem.setValue(Long.valueOf(bistDatos));
			cmbBistDatos.appendChild(comboitem);
		}
		cmbBistDatos.setSelectedIndex(0);
		
		/*carga paridad*/
		UtilData.cargarGenericData(cmbParidad, false);
		Comboitem comboitem= new Comboitem("SIN PARIDAD");
		comboitem.setValue(0);
		cmbParidad.appendChild(comboitem);
		comboitem= new Comboitem("IMPAR");
		comboitem.setValue(1);
		cmbParidad.appendChild(comboitem);
		comboitem= new Comboitem("PAR");
		comboitem.setValue(2);
		cmbParidad.appendChild(comboitem);
		comboitem= new Comboitem("MARCA");
		comboitem.setValue(3);
		cmbParidad.appendChild(comboitem);
		comboitem= new Comboitem("ESPACIO");
		comboitem.setValue(4);
		cmbParidad.appendChild(comboitem);
		cmbParidad.setSelectedIndex(0);
		
		/*bist de parada*/
		UtilData.cargarGenericData(cmbBistParada, false);
		String arrayBistParada[] = {"1", "2"};
		for(String bistParada :arrayBistParada){
			comboitem= new Comboitem(bistParada);
			comboitem.setValue(Integer.valueOf(bistParada));
			cmbBistParada.appendChild(comboitem);
		}
		cmbBistParada.setSelectedIndex(0);
		
		/*Solamente se habilita para el rol Super Usuario*/
		cmbAgencia.setDisabled(getRol().getId().intValue()!=Constantes.ID_ROL_SUPER_USUARIO);
	}
	
	public void onchange_usuarioHardware(){
		try {
			Util.limpiarCombobox(cmbUsuarioHardware);
			UtilData.cargarGenericData(cmbUsuarioHardware, false);
			if(cmbAgencia.getSelectedItem().getValue() instanceof Agencia){				
				TreeMap<String, Object>criteriosBusqueda= new TreeMap<>();
				criteriosBusqueda.put("agencia", cmbAgencia.getSelectedItem().getValue());
				criteriosBusqueda.put("estadoRegistro", Constantes.VALUE_ACTIVO);
				List<String>criteriosOrdenar= new ArrayList<>();
				criteriosOrdenar.add("descripcion");
				List<UsuarioHardware>result=ServiceLocator.getUsuarioHardwareManager().buscarPorX(criteriosBusqueda, criteriosOrdenar);
				for(UsuarioHardware usuarioHardware:result){
					Comboitem comboitem= new Comboitem(usuarioHardware.getDescripcion());
					comboitem.setValue(usuarioHardware);
					cmbUsuarioHardware.appendChild(comboitem);
				}
			}
			cmbUsuarioHardware.setSelectedIndex(0);
		} catch (Exception e) {
			e.printStackTrace();
			DlgMessage.error(e.getMessage());
		}
	}
	
	private void limpiarControles(){
		cmbUsuarioHardware.setSelectedIndex(0);
		txtNombreImpresora.setText("");
		cmbPuerto.setSelectedIndex(0);
		cmbBistPorSegundo.setSelectedIndex(0);
		cmbBistDatos.setSelectedIndex(0);
		cmbParidad.setSelectedIndex(0);
		cmbBistParada.setSelectedIndex(0);
	}
	
	private void habilitarControles(boolean disabled){
		cmbAgencia.setDisabled(getRol().getId().intValue()==Constantes.ID_ROL_SUPER_USUARIO?false:true);
		cmbUsuarioHardware.setDisabled(disabled);
		txtNombreImpresora.setDisabled(disabled);
		cmbPuerto.setDisabled(disabled);
		cmbBistPorSegundo.setDisabled(disabled);
		cmbBistDatos.setDisabled(disabled);
		cmbParidad.setDisabled(disabled);
		cmbBistParada.setDisabled(disabled);
	}	
	
	public void nuevo(){
		try {
			limpiarControles();
			habilitarControles(false);
			cmbUsuarioHardware.setFocus(true);
			
			btnGuardar.setDisabled(false);
		} catch (Exception e) {
			e.printStackTrace();
			DlgMessage.error(e.getMessage());
		}
	}
	
	public void guardar()throws Exception{
		try {
			if(!(cmbAgencia.getSelectedItem().getValue() instanceof Agencia)){
				DlgMessage.information(Messages.getString("wndConfigPrint.information.noAgencia"));
				return;
			}else if (!(cmbUsuarioHardware.getSelectedItem().getValue() instanceof UsuarioHardware)){
				DlgMessage.information(Messages.getString("wndConfigPrint.information.noUsuarioHardware"));
				return;
			}else if (txtNombreImpresora.getText().trim().isEmpty()){
				DlgMessage.information(Messages.getString("wndConfigPrint.information.noNombreImpresora"));
				return;
			}else if(cmbPuerto.getSelectedIndex()<=0){
				DlgMessage.information(Messages.getString("wndConfigPrint.information.noPuerto"));
				return;
			}else if (cmbBistPorSegundo.getSelectedIndex()<=0){
				DlgMessage.information(Messages.getString("wndConfigPrint.information.noBistPorSegundo"));
				return;
			}else if (cmbBistDatos.getSelectedIndex()<=0){
				DlgMessage.information(Messages.getString("wndConfigPrint.information.noBistDatos"));
				return;
			}else if(cmbParidad.getSelectedIndex()<=0){
				DlgMessage.information(Messages.getString("wndConfigPrint.information.noParidad"));
				return;
			}else if (cmbBistParada.getSelectedIndex()<=0){
				DlgMessage.information(Messages.getString("wndConfigPrint.information.noBistParada"));
				return;
			}
			
			Messagebox.show(Messages.getString("wndConfigImpresora.question.save"), DlgMessage.NOMBREAPLICACION, DlgMessage.BTN_YESNO, Messagebox.QUESTION,DlgMessage.BTN_DEFAULT_NO, new EventListener<Event>() {
				@Override
				public void onEvent(Event e){
					try{
						if(e.getName().equals("onYes")){
							ConfiguracionImpresora configuracionImpresora= new ConfiguracionImpresora();
							configuracionImpresora.setUsuarioHardware((UsuarioHardware)cmbUsuarioHardware.getSelectedItem().getValue());
							configuracionImpresora.setImpresora(txtNombreImpresora.getText().trim().toUpperCase());
							configuracionImpresora.setPuerto((String)cmbPuerto.getSelectedItem().getValue());	
							configuracionImpresora.setBistPorSegundo((Long)cmbBistPorSegundo.getSelectedItem().getValue());
							configuracionImpresora.setBistDatos((Long)cmbBistDatos.getSelectedItem().getValue());
							configuracionImpresora.setParidad((Integer)cmbParidad.getSelectedItem().getValue());
							configuracionImpresora.setBistParada((Integer)cmbBistParada.getSelectedItem().getValue());
							configuracionImpresora.setEstadoRegistro(Constantes.VALUE_ACTIVO);
							UtilData.auditarRegistro(configuracionImpresora, getUsuario(), Executions.getCurrent());
							ServiceLocator.getConfiguracionImpresoraManager().guardar(configuracionImpresora);
							
							
							DlgMessage.information(Messages.getString("Generales.information.exitoGuardar"));
							habilitarControles(true);
							btnGuardar.setDisabled(true);
						}
					}catch(Exception ex){
						ex.printStackTrace();
						DlgMessage.error(ex.getMessage());
					}
				}
			});
		} catch (Exception e) {
			e.printStackTrace();
			DlgMessage.error(e.getMessage());
		}
	}
}
