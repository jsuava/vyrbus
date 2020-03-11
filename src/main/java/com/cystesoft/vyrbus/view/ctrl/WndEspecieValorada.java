/**
 * Proyecto		: SISVYR
 * Sistema		: Sistema de Ventas y Reservas
 * Descripción	: 
 * Autor		: José Abanto
 * Fecha		: 25/08/2012
 */
package com.cystesoft.vyrbus.view.ctrl;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zul.Button;
import org.zkoss.zul.Column;
import org.zkoss.zul.Columns;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Comboitem;
import org.zkoss.zul.Div;
import org.zkoss.zul.Grid;
import org.zkoss.zul.Label;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Row;
import org.zkoss.zul.Rows;
import org.zkoss.zul.Spinner;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

import com.cystesoft.vyrbus.model.bean.Agencia;
import com.cystesoft.vyrbus.model.bean.DestinatariosEmails;
import com.cystesoft.vyrbus.model.bean.EspecieValorada;
import com.cystesoft.vyrbus.model.bean.SerieEspecieValorada;
import com.cystesoft.vyrbus.model.bean.TipoAgencia;
import com.cystesoft.vyrbus.model.bean.TipoComprobante;
import com.cystesoft.vyrbus.model.bean.Usuario;
import com.cystesoft.vyrbus.service.exceptions.AgenciaNullException;
import com.cystesoft.vyrbus.service.exceptions.CancelaGrabacionException;
import com.cystesoft.vyrbus.service.exceptions.CorrelativoException;
import com.cystesoft.vyrbus.service.exceptions.NumeroAutorizacionSunatNullException;
import com.cystesoft.vyrbus.service.exceptions.NumeroSerieDuplicadoException;
import com.cystesoft.vyrbus.service.exceptions.NumeroSerieNullException;
import com.cystesoft.vyrbus.service.exceptions.TipoComprobanteNullException;
import com.cystesoft.vyrbus.service.locator.ServiceLocator;
import com.cystesoft.vyrbus.service.util.Constantes;
import com.cystesoft.vyrbus.service.util.Messages;
import com.cystesoft.vyrbus.service.util.MyTime;
import com.cystesoft.vyrbus.service.util.Sendmail;
import com.cystesoft.vyrbus.service.util.Util;
import com.cystesoft.vyrbus.service.util.UtilData;
import com.cystesoft.vyrbus.view.ui.DlgMessage;
import com.cystesoft.vyrbus.view.ui.WndOpcionesMantenimiento;

/**
 *
 * @author JABANTO
 * @since JDK1.6
 */
public class WndEspecieValorada extends WndOpcionesMantenimiento {

	private static final long serialVersionUID = -4742532250389573300L;

	private Combobox cboTipoComprobante;
	private Textbox txtNumeroSerie;
	private Combobox cboAgencia;
	private Spinner spCorrelativoInicial;
	private Spinner spCorrelativoFinal;
	private Spinner spCorrelativoActual;
	private Textbox txtAutorizacionSunat;
	private Combobox cmbFormato;
	
	private EspecieValorada oespecieValorada = null;
	private Window wndSearch = null;

	private TreeMap<String, Object> criteriosBusqueda = new TreeMap<String, Object>();
	private List<String> criteriosOrdenar = null;
	
	
	private String ABECEDARIO="ABCDEFGHIJKLMNOPQXYZ";
	private String AUTORIZACION_SUNAT="0180050002620";
	
	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.window.ui.IBase#onCreate()
	 */
	@Override
	public void onCreate() throws Exception {	
//		UtilData.cargarDataCombo(cboAgencia, Agencia.class, false);
		UtilData.cargarAgenciaXtipoAgencia(cboAgencia, Constantes.ID_TIPAGE_TEPSA, false);
//		UtilData.cargarDataCombo(cboTipoComprobante, TipoComprobante.class, false);
		UtilData.cargarFormatoBoletos(cmbFormato, false);
		
		/*Carga los tipos de comprobantes*/
		List<TipoComprobante>result=ServiceLocator.getTipoComprobanteManager().buscarPorEstadoRegistro(Constantes.VALUE_ACTIVO, "denominacion");
		Comboitem comboitem= new Comboitem(Constantes.COMBO_LABEL_SELECCIONE);
		cboTipoComprobante.appendChild(comboitem);
		for(TipoComprobante tipoComprobante:result){
			if(tipoComprobante.getId().intValue()!=Constantes.ID_TIPCOM_BOLETO_VIAJE){
				comboitem= new Comboitem(tipoComprobante.getDenominacion());
				comboitem.setValue(tipoComprobante);
				cboTipoComprobante.appendChild(comboitem);
			}
		}		
		
		criteriosOrdenar = new ArrayList<String>();
		//criteriosOrdenar.add("idAgencia");
		
		cboTipoComprobante.addEventListener(Events.ON_SELECT,new EventListener<Event>() {
			@Override
			public void onEvent(Event event) throws Exception {
				// TODO Auto-generated method stub
				try{
					
					onSelectTipoComprobante();
					
				}catch(CorrelativoException cex){
					if(cex.getTipo().intValue()==CorrelativoException.DUPLICADO){
						DlgMessage.information(Messages.getString("WndEspecieValorada.information.duplcateCorrelativoAgencia"));
						cboTipoComprobante.setSelectedIndex(0);
					}
				}catch (AgenciaNullException anex) {
					DlgMessage.information(Messages.getString("WndEspecieValorada.information.Agencia"),cboAgencia);
					cboTipoComprobante.setSelectedIndex(0);
					cboAgencia.setSelectedIndex(0);
				}
			}	
		});
		
	}

	private void onSelectTipoComprobante()throws Exception{
		txtNumeroSerie.setText("");
		spCorrelativoInicial.setText("");
		spCorrelativoActual.setText("");
		spCorrelativoFinal.setText("");
		habilitaControles(true);
		/**A manera temporal el combo agencias solo se habilitará para el rol Super Usuario*/
		cboAgencia.setDisabled( !(getRol().getId().equals(Constantes.ID_ROL_SUPER_USUARIO)));
							
		if (cboAgencia.getSelectedIndex()<=0)
			throw new AgenciaNullException();
		
		if(cboTipoComprobante.getSelectedItem().getValue() instanceof TipoComprobante){
			TipoComprobante tipoComprobante=cboTipoComprobante.getSelectedItem().getValue();
														
			if (!(tipoComprobante.getId().equals(Constantes.ID_TIPCOM_BOLETO_VIAJE) || 
					tipoComprobante.getId().intValue()==Constantes.ID_TIPCOM_MANIFIESTO_PAX) ){
				//Valida si la agencia seleccionada no tiene correlativo asignado
				TreeMap<String, Object> criterioBusqueda= new TreeMap<String, Object>();
				criterioBusqueda.put("agencia", (Agencia)cboAgencia.getSelectedItem().getValue());
				criterioBusqueda.put("tipoComprobante", (TipoComprobante)cboTipoComprobante.getSelectedItem().getValue());
				criterioBusqueda.put("estadoRegistro", Constantes.VALUE_ACTIVO);
				List<EspecieValorada>listEv=ServiceLocator.getEspecieValoradaManager().buscarPorX(criterioBusqueda, null);
				if(listEv.size()>0)
					throw new CorrelativoException(CorrelativoException.DUPLICADO);
				
				//Genera los correlativos de manera automática 10/11/2016 - jabanto
				String ultimaSerie=ServiceLocator.getEspecieValoradaManager().buscarUltimaSerieUtilAge(tipoComprobante.getId());
				if(tipoComprobante.getId().intValue()==Constantes.ID_TIPCOM_BOLETA_VENTA || tipoComprobante.getId().intValue()==Constantes.ID_TIPCOM_FACTURA ||
						tipoComprobante.getId().intValue()==Constantes.ID_TIPCOM_NOTA_CREDITO || tipoComprobante.getId().intValue()==Constantes.ID_TIPCOM_NOTA_DEBITO){
					
					/*Calcula el siguiente numero de serie*/
					String snumero="";
					for(int i=0;i<ultimaSerie.length();i++){
						char schar=ultimaSerie.charAt(i);
						if(Util.isNumeric(String.valueOf(schar)))
							snumero+= String.valueOf(schar);
					}
					Integer numero=Integer.valueOf(snumero);
					numero+= +1;
					
					/*Calcula el sub-prefijo*/
					String sub_pre="";
					for(int i=1;i<ultimaSerie.length();i++){
						char schar=ultimaSerie.charAt(i);
						if(!(Util.isNumeric(String.valueOf(schar))))
							sub_pre+=String.valueOf(schar);
					}
					
					/*determina el sub-prefijo*/
					switch (sub_pre.length()) {
						case 1:
							if(numero.intValue()>99){
								sub_pre=getNextLetra(sub_pre);
								numero=1;
							}
							break;
						case 2:
							String sub_subpre=sub_pre.substring(0, 1);
							if(numero.intValue()>9){
								sub_subpre=getNextLetra(sub_subpre);
								numero=1;
								sub_pre=sub_pre.substring(0, 1)+sub_subpre;
							}
							break;
						default:
							break;
					}
					
					String pretc=ultimaSerie.substring(0,1); //Saca el prefijo del tipo de comprobante (B=Boleta; F=Factura)
					String prefijos=pretc+sub_pre;
					String serie="";
					switch (prefijos.length()) {
						case 2:
							if(numero.intValue()<=9)
								serie=pretc+sub_pre+"0"+numero.toString();
							else
								serie=pretc+sub_pre+numero.toString();
							break;
						case 3:
							serie=pretc+sub_pre+numero.toString();
							break;
						default:
							break;
					}
					
					if((tipoComprobante.getId().intValue()==Constantes.ID_TIPCOM_NOTA_CREDITO || tipoComprobante.getId().intValue()==Constantes.ID_TIPCOM_NOTA_DEBITO) && serie.trim().length()>0)
						serie=serie.substring(1); //Suprime el prefijo, este lo asigna el sistema cuento ge genera la nota de credito o debito
					
					if(serie.isEmpty()){
						DlgMessage.information("No hay números de serie disponibles para generar.");
						return;
					}
					
					txtNumeroSerie.setText(serie);
					txtAutorizacionSunat.setText(AUTORIZACION_SUNAT);
				}else
					txtNumeroSerie.setText(String.valueOf(Integer.valueOf(ultimaSerie).intValue()+1));
				spCorrelativoInicial.setValue(1);
				spCorrelativoActual.setValue(1);
				spCorrelativoFinal.setValue(999999999);
				
				/*End Begin 10/11/2016 - jabanto*/
//				//Genera los correlativos de manera automática
//				Integer ultimaSeria=ServiceLocator.getEspecieValoradaManager().buscarUltimaSerieUtilAge(tipoComprobante.getId());
//				txtNumeroSerie.setText(String.valueOf(ultimaSeria+1));
//				spCorrelativoInicial.setValue(1);
//				spCorrelativoActual.setValue(1);
//				spCorrelativoFinal.setValue(999999999);
										
				habilitaControles(false);
				cboAgencia.setDisabled(false);
				cboTipoComprobante.setDisabled(false);
			}else if (tipoComprobante.getId().equals(Constantes.ID_TIPCOM_BOLETO_VIAJE)){
				cmbFormato.setSelectedIndex(0);
				cmbFormato.setDisabled(false);
			}else{
				cmbFormato.setSelectedIndex(0);
				cmbFormato.setDisabled(true);
			}
		}
	}
	
	private String getNextLetra(String letraActual)throws Exception{
		String nextLetra="";
		int index= ABECEDARIO.indexOf(letraActual);
		int maxindex=ABECEDARIO.length()-1;
		
		if(index==maxindex)
			nextLetra=letraActual+ABECEDARIO.charAt(0);
		else
			nextLetra=String.valueOf(ABECEDARIO.charAt(index+1));
		
		return nextLetra;
	}
	
	
	@Override
	public void initComponents() {
		cboTipoComprobante = (Combobox) getFellow("cboTipoComprobante");
		txtNumeroSerie = (Textbox) getFellow("txtNumeroSerie");
		cboAgencia = (Combobox) getFellow("cboAgencia");
		spCorrelativoInicial = (Spinner) getFellow("spCorrelativoInicial");
		spCorrelativoFinal = (Spinner) getFellow("spCorrelativoFinal");
		spCorrelativoActual = (Spinner) getFellow("spCorrelativoActual");
		txtAutorizacionSunat = (Textbox) getFellow("txtAutorizacionSunat");
		cmbFormato=(Combobox)this.getFellow("cmbFormato");
	}
	
	@Override
	public void onNew() {
		cboTipoComprobante.setSelectedIndex(0);
		try {			
			Usuario usuario=ServiceLocator.getUsuarioManager().buscarXId(getUsuario().getId().longValue());
			if(usuario.getAgencia()==null)
				Util.seleccionarValorItemCombo(Agencia.class, cboAgencia, getAgencia().getId());
			else
				Util.seleccionarValorItemCombo(Agencia.class, cboAgencia, usuario.getAgencia().getId());
			
			cmbFormato.setSelectedIndex(0);
			
			/**A manera temporal el combo agencias solo se habilitará para el rol Super Usuario*/
			cboAgencia.setDisabled( !(getRol().getId().equals(Constantes.ID_ROL_SUPER_USUARIO)));
		} catch (Exception e) {
		}
	}

	@Override
	public void onSearch() throws Exception {
		ventarBusqueda();
	}
	
	@Override
	public void onRefresh(int tab) throws Exception {
		// TODO Auto-generated method stub
		if (!criteriosBusqueda.isEmpty()) {
			listarRegistros(ServiceLocator.getEspecieValoradaManager().buscarPorX(criteriosBusqueda, criteriosOrdenar));					
		}	
	}
	
	@Override
	public void onModify(int tab) throws Exception {
		// TODO Auto-generated method stub
		Long id = new Long(0);
		id = ((EspecieValorada) listboxLista.getSelectedItem().getValue()).getId().longValue();
		this.mantenimientoRegistro(id);
		cboAgencia.setDisabled(true);
		cboTipoComprobante.setDisabled(true);
		txtNumeroSerie.setDisabled(true);
		spCorrelativoInicial.setDisabled(true);
		spCorrelativoActual.setDisabled(true);
		spCorrelativoFinal.setDisabled(true);
		txtAutorizacionSunat.setDisabled(true);
		btnGuardar.setDisabled(true);
	}
	
	@Override
	public void onCancel(int action) throws Exception {
		// TODO Auto-generated method stub
		switch (action) {
			case ACTION_NEW:
				break;

			case ACTION_MODIFY:
				this.mantenimientoRegistro(new Long(textboxId.getText()));
				break;
		}
	}
	
	@Override
	public void onSave(int action) throws Exception {
		Long ultimoCorrRegistrado=(long) 0;
		try{
			if (cboAgencia.getSelectedIndex()<=0 || (!(cboAgencia.getSelectedItem().getValue() instanceof Agencia)))
				throw new AgenciaNullException();
			else if (!(cboTipoComprobante.getSelectedItem().getValue() instanceof TipoComprobante))
				throw new TipoComprobanteNullException();
			else if (txtNumeroSerie.getText().equals("") || txtNumeroSerie.getValue().equals(0))
				throw new NumeroSerieNullException();
			else if (spCorrelativoInicial.getText().equals("") ||  spCorrelativoInicial.getValue().equals(0))
				throw new CorrelativoException(CorrelativoException.INICIAL_NULL);
			else if (spCorrelativoFinal.getText().equals("") || spCorrelativoFinal.getValue().equals(0))
				throw new CorrelativoException(CorrelativoException.FINAL_NULL);
			else if (spCorrelativoActual.getText().equals("") || spCorrelativoActual.getValue().equals(0))
				throw new CorrelativoException(CorrelativoException.ACTUAL_NULL);
			else if (txtAutorizacionSunat.isDisabled()==false &&  txtAutorizacionSunat.getText().trim().equals("") && 
					((TipoComprobante)cboTipoComprobante.getSelectedItem().getValue()).getId().intValue()==Constantes.ID_TIPCOM_MANIFIESTO_PAX)
				throw new NumeroAutorizacionSunatNullException();
			else if (spCorrelativoFinal.getValue() < spCorrelativoInicial.getValue())
				throw new CorrelativoException(CorrelativoException.FINAL_MENOR_INICIAL);
			else if (spCorrelativoActual.getValue() < spCorrelativoInicial.getValue())
				throw new CorrelativoException(CorrelativoException.ACTUAL_MENOR_INICIAL);
			else if (spCorrelativoActual.getValue() > spCorrelativoFinal.getValue())
				throw new CorrelativoException(CorrelativoException.ACTUAL_MAYOR_FINAL);
			else if (((TipoComprobante)cboTipoComprobante.getSelectedItem().getValue()).getId().intValue()==Constantes.ID_TIPCOM_BOLETO_VIAJE && cmbFormato.getSelectedIndex()<=0){
				DlgMessage.information(Messages.getString("WndEspecieValorada.information.noFormato"),cmbFormato);
				throw new CancelaGrabacionException();
			}
			
			TipoComprobante tipoComprobante=cboTipoComprobante.getSelectedItem().getValue();
			Agencia agencia=cboAgencia.getSelectedItem().getValue();
			final int acction=action;
			
			if(txtNumeroSerie.isDisabled()==false){
				//valida si la serie esta registra en el maestro de series y agencias.
				final SerieEspecieValorada serieEspecieValorada=ServiceLocator.getSerieEspecieValoradaManager().buscarPorID(txtNumeroSerie.getValue().toString(), tipoComprobante.getId());
				if(serieEspecieValorada==null)
					throw new CorrelativoException(CorrelativoException.SERIE_NO_REGISTRADA);
				
				//Valida el correlativo incicial y final no sea menor al ultimo registrado en la BD
				EspecieValorada especieValorada=ServiceLocator.getEspecieValoradaManager().buscarUltimaEspecieRegistrada(tipoComprobante.getId(), txtNumeroSerie.getValue().toString(),agencia.getId());
				if(especieValorada!=null){
					ultimoCorrRegistrado=especieValorada.getCorrelativoFinal();
					if(ultimoCorrRegistrado>=spCorrelativoInicial.getValue())
						throw new CorrelativoException(CorrelativoException.CORRELATIVO_INICIAL_MENOR_DB);
					else if (ultimoCorrRegistrado>=spCorrelativoFinal.getValue())
						throw new CorrelativoException(CorrelativoException.CORRELATIVO_FINAL_MENOR_DB);
				}
				
				Long diferencia=spCorrelativoInicial.getValue()-ultimoCorrRegistrado;
				if(diferencia>1){//Si existe diferencia entre el ultimo correlativo registrado y el inicial que se este registrando
					//Valida si el rol es superusuario
					if(getRol().getId().intValue()==Constantes.ID_ROL_SUPER_USUARIO){
						Messagebox.show(Messages.getString("El último correlativo registrado es: "+ultimoCorrRegistrado+" \n y el que se va a registrar es desde el "+spCorrelativoInicial.getValue()+",\n"+
						"lo que hace una diferencia de "+diferencia+" correlativos . \n\n "+
						" ¿Realmente desea continuar?"), DlgMessage.NOMBREAPLICACION, DlgMessage.BTN_YESNO, Messagebox.QUESTION,DlgMessage.BTN_DEFAULT_NO, new EventListener<Event>() {
							@Override
							public void onEvent(Event e) throws Exception {
								if(e.getName().equals("onYes")){
									EventSaveEspeciValorada(acction);
								}
							}
						});
					}else{
						DlgMessage.information("El último correlativo registrado es: "+ultimoCorrRegistrado+" \n y el que se va a regsitrar es desde el "+spCorrelativoInicial.getValue()+",\n"+
								"lo que hace una diferencia de "+diferencia+" correlativos . \n \n " +
								"La diferencia solamente debe de ser un correlativo, No puede continuar.");
					}
				}else
					EventSaveEspeciValorada(action);
			}else
				EventSaveEspeciValorada(action);
			
			throw new CancelaGrabacionException();
		}catch (TipoComprobanteNullException tcnex){
			DlgMessage.information(Messages.getString("WndEspecieValorada.information.TipoComprobante"),cboTipoComprobante);
			throw new CancelaGrabacionException();
		}catch (NumeroSerieNullException nsnex){
			DlgMessage.information(Messages.getString("WndEspecieValorada.information.NumeroSerie"),txtNumeroSerie);
			throw new CancelaGrabacionException();
		}catch (AgenciaNullException anex){
			DlgMessage.information(Messages.getString("WndEspecieValorada.information.Agencia"),cboAgencia);
			throw new CancelaGrabacionException();
		}catch (CorrelativoException c){
			if(c.getTipo().intValue()==CorrelativoException.INICIAL_NULL){
				DlgMessage.information(Messages.getString("WndEspecieValorada.information.CorrelativoInicial"),spCorrelativoInicial);
				throw new CancelaGrabacionException();
			}else if (c.getTipo().intValue()==CorrelativoException.FINAL_NULL){
				DlgMessage.information(Messages.getString("WndEspecieValorada.information.CorrelativoFinal"),spCorrelativoFinal);
				throw new CancelaGrabacionException();
			}else if (c.getTipo().intValue()==CorrelativoException.ACTUAL_NULL){
				DlgMessage.information(Messages.getString("WndEspecieValorada.information.CorrelativoActual"),spCorrelativoActual);
				throw new CancelaGrabacionException();
			}else if (c.getTipo().intValue()==CorrelativoException.FINAL_MENOR_INICIAL){
				DlgMessage.information(Messages.getString("WndEspecieValorada.information.CorrelativoFinalMenorInicial"),spCorrelativoFinal);
				throw new CancelaGrabacionException();
			}else if (c.getTipo().intValue()==CorrelativoException.ACTUAL_MENOR_INICIAL){
				DlgMessage.information(Messages.getString("WndEspecieValorada.information.CorrelativoActualMenorInicial"),spCorrelativoActual);
				throw new CancelaGrabacionException();
			}else if (c.getTipo().intValue()==CorrelativoException.ACTUAL_MAYOR_FINAL){
				DlgMessage.information(Messages.getString("WndEspecieValorada.information.CorrelativoActualMayorFinal"),spCorrelativoActual);
				throw new CancelaGrabacionException();
			}else if (c.getTipo().intValue()==CorrelativoException.CORRELATIVO_INICIAL_MENOR_DB){
				DlgMessage.information(Messages.getString("WndEspecieValorada.information.correlativoInicalMenorUltimoDB")+
						"\n El último es: "+ultimoCorrRegistrado.toString()
						,spCorrelativoInicial);
				throw new CancelaGrabacionException();
			}else if(c.getTipo().intValue()==CorrelativoException.CORRELATIVO_FINAL_MENOR_DB){
				DlgMessage.information(Messages.getString("WndEspecieValorada.information.correlativoFinallMenorUltimoDB")+
						"\n El último es: "+ultimoCorrRegistrado.toString()
						,spCorrelativoFinal);
				throw new CancelaGrabacionException();
			}else if (c.getTipo().intValue()==CorrelativoException.SERIE_NO_REGISTRADA){
				DlgMessage.information(Messages.getString("WndEspecieValorada.information.noSerieRegistrada"),txtNumeroSerie);
				throw new CancelaGrabacionException();
			}
		}catch (NumeroAutorizacionSunatNullException nasnex){
			DlgMessage.information(Messages.getString("WndEspecieValorada.information.NumeroAutorizacionSunat"),txtAutorizacionSunat);
			throw new CancelaGrabacionException();
		}catch (NumeroSerieDuplicadoException nsdex){
			DlgMessage.information(Messages.getString("WndEspecieValorada.information.NumeroSerieDuplicado"),txtNumeroSerie);
			throw new CancelaGrabacionException();
		}catch (CancelaGrabacionException cg) {
			throw new CancelaGrabacionException();
		}catch (Exception ex){
			DlgMessage.error(this.getClass().getName()+" "+ex.getMessage());
			ex.printStackTrace(); throw new CancelaGrabacionException();
		}
	}
	
	
	private void EventSaveEspeciValorada(final int acction) throws Exception{
		Agencia agencia=(Agencia)cboAgencia.getSelectedItem().getValue();
		final TipoComprobante tipoComprobante=cboTipoComprobante.getSelectedItem().getValue();
		final SerieEspecieValorada serieEspecieValorada=ServiceLocator.getSerieEspecieValoradaManager().buscarPorID(txtNumeroSerie.getValue().toString(), tipoComprobante.getId());
		
		if(txtNumeroSerie.isDisabled()==false){
			if(serieEspecieValorada.getAgencia().getId().intValue()!=agencia.getId().intValue() && tipoComprobante.getId().intValue()==Constantes.ID_TIPCOM_MANIFIESTO_PAX){
				/*Valida si la serie que se va a registrar pertenece a la agencia que esta haciendo el registro*/
				DlgMessage.information("Este número de Serie está registrada en la Agencia "+serieEspecieValorada.getAgencia().toString()+", no puede continuar.");
				return;
			}else if(serieEspecieValorada.getAgencia().getId().intValue()!=agencia.getId().intValue()){
				Messagebox.show(Messages.getString("Este número de Serie está registrada en la Agencia "+serieEspecieValorada.getAgencia().toString()+
						"\n\n ¿Realmente desea registrarla?"), DlgMessage.NOMBREAPLICACION, DlgMessage.BTN_YESNO, Messagebox.QUESTION,DlgMessage.BTN_DEFAULT_NO, new EventListener<Event>() {
					@Override
					public void onEvent(Event e) throws Exception {
						if(e.getName().equals("onYes")){
					 		guardaEspecieValoda(acction);
					 		enviarCorreo(serieEspecieValorada.getAgencia());
						}
					}
				});
			}else{
				guardaEspecieValoda(acction);
			}
		}else
			guardaEspecieValoda(acction);
		
	}
	/**
	 * Envia correo ante un ingreso de una serie duplicada por el usuario.
	 * @param especieValorada
	 * @throws Exception 
	 */
	private void enviarCorreo(Agencia agenciaCorresponde) throws Exception{
		String title="Número de Serie utilizada en más de una Agencia";
		String toAddress="rdelnegro@tepsa.com.pe, dquispe@tepsa.com.pe";
		String ccAddress="sistemas@tepsa.com.pe";
		String mensaje = "";
		
		//Busca mail genencia Comercial.
//		Parametros parametros=ServiceLocator.getParametrosManager().buscarPorEstadoRegistro(Constantes.VALUE_ACTIVO);
//		if(parametros.getUsuarioGerenciaComercial()!=null){
//			String sid=parametros.getUsuarioGerenciaComercial();
//			String[] ids =sid.split(","); 
//			Integer[] oCriteriosIN = new Integer[ids.length];
//			for(int i=0; i<ids.length; i++){
//				oCriteriosIN[i]=Integer.valueOf(ids[i]);
//			}
//			List<Usuario> lstUsuarios = ServiceLocator.getUsuarioManager().buscarPorX("id", oCriteriosIN, null, Constantes.VALUE_ACTIVO);
//			
//			for(Usuario usuario: lstUsuarios){
//				if(usuario.getPersonal()!=null && usuario.getPersonal().getEmail()!=null){
//					Personal personal=usuario.getPersonal();
//					toAddress+=personal.getEmail();
//				}
//			}
//		}
		//Cuerpo del mensaje		
		mensaje="Se ha registrado un número de Serie de un comprobante que corresponde a otra Agencia: \n";
		mensaje+="Tipo de Comprobante            :"+cboTipoComprobante.getText()+"\n";
		mensaje+="Agencia a quién le corresponde :"+agenciaCorresponde.toString()+"\n";
		mensaje+="Agencia en donde se registró   :"+cboAgencia.getText()+"\n";
		mensaje+="Usuario registro               :"+getUsuario().toString()+"\n";
		mensaje+="Fecha hora registro            :"+new MyTime().dateServer()+"\n";
		mensaje+="Número de Serie                :"+txtNumeroSerie.getValue().toString()+"\n";
		mensaje+="Correlativos registrados       :Del "+spCorrelativoInicial.getValue()+" Al "+spCorrelativoFinal.getValue()+"\n";
		
		//Si no encuentra el email de genencia comercial.
//		if(toAddress.isEmpty()){
//			toAddress=ccAddress;
//			mensaje+="\n\n";
//			mensaje+="Alerta : NO SE HA ENCONTRADO EL E-MAIL DEL USUARIO GERENCIA COMERCIAL. POR LO QUE NO HA PODIDO NOTIFICAR.\n";
//		}
		
		//Envia E-Mail
		mensaje+="\n\n";
		mensaje+="NOTA: [Este buzon es de envio automático, por favor no responda.]";
		DestinatariosEmails window = new DestinatariosEmails();
		window.setEmails("TO:"+toAddress+";CC:"+ccAddress);
		Sendmail.enviaEmail(mensaje,title, window);
		
	}
	
	private void guardaEspecieValoda(int action) throws Exception{
		try{
			if (action==ACTION_NEW)
				oespecieValorada = new EspecieValorada();
			
			Integer id = (textboxId.getText().equals("") ? 0 : new Integer(textboxId.getText()));
			oespecieValorada.setId(id);
		
			TipoComprobante otipoComprobante = new TipoComprobante();
			otipoComprobante.setId((Integer) ((TipoComprobante) cboTipoComprobante.getSelectedItem().getValue()).getId());
			oespecieValorada.setTipoComprobante(otipoComprobante);
			
			Agencia oagencia = new Agencia();
			oagencia.setId(((Agencia) cboAgencia.getSelectedItem().getValue()).getId());
			oagencia.setDenominacion(((Agencia) cboAgencia.getSelectedItem().getValue()).getDenominacion());
			oespecieValorada.setAgencia(oagencia);
			
			if(Util.isNumeric(txtNumeroSerie.getText()))
				oespecieValorada.setSerie(Integer.valueOf(txtNumeroSerie.getValue()));
			else
				oespecieValorada.setSeriefe(txtNumeroSerie.getText());
//			oespecieValorada.setSerie(txtNumeroSerie.getValue());
			
			oespecieValorada.setCorrelativoInicial(spCorrelativoInicial.getValue().equals("") ? 0 : new Long (spCorrelativoInicial.getValue()));
			oespecieValorada.setCorrelativoFinal(spCorrelativoFinal.getValue().equals("") ? 0 : new Long (spCorrelativoFinal.getValue()));
			oespecieValorada.setCorrelativoActual(spCorrelativoActual.getValue().equals("") ? 0 : new Long (spCorrelativoActual.getValue()));
			oespecieValorada.setAutorizacionSunat(txtAutorizacionSunat.getText());
			if(cmbFormato.getSelectedIndex()>0)			
				oespecieValorada.setFormato((int)cmbFormato.getSelectedItem().getValue());
			else
				oespecieValorada.setFormato(Constantes.FALSE_VALUE);
			oespecieValorada.setEstadoRegistro(Constantes.VALUE_ACTIVO);

			/*Busca alguna especie valorada de la agencia para inactivarla, solo si es diferente al manifiesto de pasajeros.*/
			if(oespecieValorada.getTipoComprobante().getId().intValue()!=Constantes.ID_TIPCOM_MANIFIESTO_PAX){
				criteriosBusqueda=new TreeMap<String, Object>();
				criteriosBusqueda.put("agencia", oespecieValorada.getAgencia());
				criteriosBusqueda.put("tipoComprobante", oespecieValorada.getTipoComprobante());
				criteriosBusqueda.put("serie", oespecieValorada.getSerie());
				criteriosBusqueda.put("estadoRegistro", Constantes.VALUE_ACTIVO);
				List<EspecieValorada>listEsv=ServiceLocator.getEspecieValoradaManager().buscarPorX(criteriosBusqueda, null);
				for(EspecieValorada especieValorada:listEsv){
					UtilData.auditarRegistro(especieValorada, true, getUsuario(), Executions.getCurrent());
					ServiceLocator.getEspecieValoradaManager().inactivar(especieValorada.getId().longValue());
					
					oespecieValorada.setCorrelativoInicial(especieValorada.getCorrelativoInicial());
					oespecieValorada.setCorrelativoActual(oespecieValorada.getCorrelativoActual());
				}
			}else{
				//Cuando es Manifiesto de pasajeros, Busca el Ultimo manifiesto registrado
				EspecieValorada ultimaEspecieValorada = ServiceLocator.getEspecieValoradaManager().buscarUltimaEspecieRegistrada(oespecieValorada.getTipoComprobante().getId(), oespecieValorada.getSerie().toString(), oespecieValorada.getAgencia().getId());
				if(ultimaEspecieValorada!=null){
					//Si los correlativos de ultima especie valorada estan agotados, los inhabilita.
					if(ultimaEspecieValorada.getCorrelativoActual().longValue()>=ultimaEspecieValorada.getCorrelativoFinal().longValue()){
						ultimaEspecieValorada.setEstadoRegistro(Constantes.VALUE_INACTIVO);
						UtilData.auditarRegistro(ultimaEspecieValorada,true,getUsuario(),Executions.getCurrent());
						ServiceLocator.getEspecieValoradaManager().actualizar(ultimaEspecieValorada);
					}else{
						//Si los correlativos de la ultima especie valorada aún estan disponibles el nuevo registro sera insertado con el estado inactivo.
						oespecieValorada.setEstadoRegistro(Constantes.VALUE_INACTIVO);
					}
				}
			}
			switch (action){
				case ACTION_NEW:
					UtilData.auditarRegistro(oespecieValorada, getUsuario(), Executions.getCurrent());
					ServiceLocator.getEspecieValoradaManager().guardar(oespecieValorada);
					textboxId.setText((new Long(oespecieValorada.getId()).toString()));
					break;
				case ACTION_MODIFY:
					UtilData.auditarRegistro(oespecieValorada,getUsuario(), Executions.getCurrent());
					ServiceLocator.getEspecieValoradaManager().guardar(oespecieValorada);
					break;
			}
			
			/*RECUPERA EL REGISTRO ACTUALIZADO O EL NUEVO*/
			criteriosBusqueda = new TreeMap<String, Object>();
			criteriosBusqueda.put("serie", oespecieValorada.getSerie());
			criteriosBusqueda.put("correlativoInicial", oespecieValorada.getCorrelativoInicial());
			criteriosBusqueda.put("correlativoActual", oespecieValorada.getCorrelativoActual());
			criteriosBusqueda.put("correlativoFinal", oespecieValorada.getCorrelativoFinal());
			criteriosBusqueda.put("estadoRegistro", Constantes.VALUE_ACTIVO);
			listarRegistros(ServiceLocator.getEspecieValoradaManager().buscarPorX(criteriosBusqueda, criteriosOrdenar));
			
//			String accionMensaje = "";
//			switch(action){
//				case ACTION_NEW:
//					accionMensaje = "grabado";
//					break;
//				case ACTION_MODIFY:
//					accionMensaje = "actualizado";
//					break;
//			}
			DlgMessage.information(Messages.getString("Generales.information.exitoGuardar"));	
			oTabLista.setDisabled(false);
			
			btnNuevo.setDisabled(accesoNuevo()?false:true);
			btnBuscar.setDisabled(accesoConsultar()?false:true);
			btnImprimir.setDisabled(accesoImprimir()?false:true);
			btnExportar.setDisabled(accesoExportar()?false:true);	
			btnRefrescar.setDisabled(false);
			btnModificar.setDisabled(true);
			btnCancelar.setDisabled(true);
			btnGuardar.setDisabled(true);
			btnEliminar.setDisabled(true);
			btnCerrar.setDisabled(false);
			oTabbox.setSelectedIndex(0);
		}catch (NumeroSerieDuplicadoException ne){
			DlgMessage.information(Messages.getString("WndEspecieValorada.information.NumeroSerieDuplicado"));
		}
		
	}
	
	@Override
	public void onDelete(int tab) throws Exception {
		// TODO Auto-generated method stubdfdfdf
//		Long id = (long) 0;
//
//		switch (tab) {
//			case TAB_LIST:
//				id = new Long((String) listboxLista.getSelectedItem().getValue());
//				break;
//
//			case TAB_MAINTENANCE:
//				id = new Long(textboxId.getText());
//				break;
//		}
//		ServiceLocator.getEspecieValoradaManager().inactivar(id);
	}
	
	@Override
	public void onPrint(int tab) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void onExport(int tab) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void onHelp() {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void onChangeTab(int tab) throws Exception {
		// TODO Auto-generated method stub
		switch (tab) {
		case TAB_LIST:
			break;

		case TAB_MAINTENANCE:
			if (listboxLista.getSelectedIndex() > -1) {
				this.mantenimientoRegistro(((EspecieValorada) listboxLista.getSelectedItem().getValue()).getId().longValue());
			}
			break;
		}
	}
	
	@Override
	public void onClose() {
		closeTabWindow();
	}

	private void listarRegistros(ArrayList<EspecieValorada> lstRegistros) throws Exception {
//		ArrayList<Object> lstEspeciesValoradas = new ArrayList<Object>();
		
		Util.limpiarListbox(listboxLista);
		
		Listitem item = null;
		Listcell cell = null;
		
		int x=0;
		for (EspecieValorada especieValorada: lstRegistros){
			if(especieValorada.getTipoComprobante().getId().intValue()!=Constantes.ID_TIPCOM_BOLETO_VIAJE ){
				x++;
				if (especieValorada.getTipoComprobante().getDenominacion()==null){
					TipoComprobante tipoComprobante = new TipoComprobante();
					tipoComprobante=ServiceLocator.getTipoComprobanteManager().buscarPorId(especieValorada.getTipoComprobante().getId().longValue());
					especieValorada.setTipoComprobante(tipoComprobante);
				}
				
				item= new Listitem();
				cell= new Listcell(String.valueOf(x));
				cell.setStyle("font-size:11px !important");
				item.appendChild(cell);
				cell= new Listcell(especieValorada.getAgencia().getDenominacion());
				item.appendChild(cell);
				cell= new Listcell(especieValorada.getTipoComprobante().getDenominacion());
				item.appendChild(cell);
				cell= new Listcell(especieValorada.getSerie()!=null?especieValorada.getSerie().toString():especieValorada.getSeriefe());
				cell.setStyle("font-size:11px !important");
				item.appendChild(cell);
				cell= new Listcell(especieValorada.getCorrelativoInicial().toString());
				cell.setStyle("font-size:11px !important");
				item.appendChild(cell);
				cell= new Listcell(especieValorada.getCorrelativoFinal().toString());
				cell.setStyle("font-size:11px !important");
				item.appendChild(cell);
				cell= new Listcell(especieValorada.getCorrelativoActual().toString());
				cell.setStyle("font-size:11px !important");
				item.appendChild(cell);
				cell= new Listcell(especieValorada.getAutorizacionSunat());
				cell.setStyle("font-size:11px !important");
				item.appendChild(cell);
				
				item.setValue(especieValorada);
				listboxLista.appendChild(item);
			}
		}
	}
	
	public void mantenimientoRegistro(Long id) throws Exception {
//		oespecieValorada = ServiceLocator.getEspecieValoradaManager().buscarPorId(id);
		if(listboxLista.getSelectedIndex()>=0){
			oespecieValorada = listboxLista.getItemAtIndex(listboxLista.getSelectedIndex()).getValue();
			TipoComprobante otipoComprobante = oespecieValorada.getTipoComprobante();
			Agencia oagencia = oespecieValorada.getAgencia();
			
			textboxId.setText(oespecieValorada.getId().toString());
			
			if (otipoComprobante != null){
				Util.seleccionarValorItemCombo(TipoComprobante.class, cboTipoComprobante, otipoComprobante.getId());
			}
			if (oagencia != null){
				Util.seleccionarValorItemCombo(Agencia.class, cboAgencia, oagencia.getId());
			}
			txtNumeroSerie.setText(oespecieValorada.getSerie().toString());
			spCorrelativoInicial.setText(oespecieValorada.getCorrelativoInicial().toString());
			spCorrelativoFinal.setText(oespecieValorada.getCorrelativoFinal().toString());
			spCorrelativoActual.setText(oespecieValorada.getCorrelativoActual().toString());
			if(oespecieValorada.getTipoComprobante().getId().intValue()==Constantes.ID_TIPCOM_BOLETO_VIAJE)
				Util.seleccionarValorItemCombobox(cmbFormato, oespecieValorada.getFormato());
			else
				cmbFormato.setSelectedIndex(0);
			txtAutorizacionSunat.setText(oespecieValorada.getAutorizacionSunat()!=null? oespecieValorada.getAutorizacionSunat(): "");
			
			cboAgencia.setDisabled(true);
			cboTipoComprobante.setDisabled(true);
			cmbFormato.setDisabled(true);
			
			
			if (!(oespecieValorada.getTipoComprobante().getId().intValue()==Constantes.ID_TIPCOM_BOLETO_VIAJE)){
				habilitaControles(false);
				btnGuardar.setDisabled(true);
			}
		}else
			DlgMessage.information(Messages.getString("Dede se seleccionar un registro"),DlgMessage.BTN_OK);

	}
	
	
	private void ventarBusqueda() throws Exception{
		wndSearch = createWindowSearch();
		this.appendChild(wndSearch);
		wndSearch.setMode("modal");
	}
	
	@SuppressWarnings("deprecation")
	private Window createWindowSearch() throws Exception{
		
		final Window window = new Window(Messages.getString("System.title"), "normal", true);
		window.setWidth("450px");
		
		Grid grid= new Grid();
		Rows rows= new Rows();
		Row row= new Row();
		Columns columns= new Columns();
		Column column= new Column();
		column.setWidth("100px");
		columns.appendChild(column);
		grid.appendChild(columns);
		
		//Tipo Agencia
		Label label= new Label("Tipo Agencia");
		label.setSclass("label-size11");
		final Combobox cmbTipoAgencia= new Combobox();
		cmbTipoAgencia.setWidth("200px");
		UtilData.cargarDataCombo(cmbTipoAgencia, TipoAgencia.class, true);
		Usuario usuario=ServiceLocator.getUsuarioManager().buscarPorId(getUsuario().getId().longValue());
		if(usuario.getAgencia()==null)
			Util.seleccionarValorItemCombo(TipoAgencia.class, cmbTipoAgencia, getAgencia().getTipoAgencia().getId());
		else
			Util.seleccionarValorItemCombo(TipoAgencia.class, cmbTipoAgencia, usuario.getAgencia().getTipoAgencia().getId());
		cmbTipoAgencia.setReadonly(true);
		row.appendChild(label);
		row.appendChild(cmbTipoAgencia);
		rows.appendChild(row);
		
		//Carga Agencia
		label= new Label("Agencia");
		label.setSclass("label-size11");
		final Combobox cmbAgencia= new Combobox();
		cmbAgencia.setWidth("200px");
		UtilData.cargarAgenciaXtipoAgencia(cmbAgencia,((TipoAgencia)cmbTipoAgencia.getSelectedItem().getValue()).getId(),true);
		cmbAgencia.setReadonly(false);
		row= new Row();
		row.appendChild(label);
		row.appendChild(cmbAgencia);
		rows.appendChild(row);
		
		label= new Label();
		label.setValue("Tipo Comprobante");
		label.setSclass("label-size11");
		final Combobox cmbTipoComprobante= new Combobox();
		cmbTipoComprobante.setReadonly(true);
		cmbTipoComprobante.setWidth("200px");
		
		//Carga Tipo de comprobante
		if(cmbAgencia.getSelectedItem().getValue() instanceof Agencia){
			UtilData.cargarTipoComprobanteAgencia(cmbTipoComprobante, ((Agencia)cmbAgencia.getSelectedItem().getValue()), true);
			cmbTipoComprobante.setSelectedIndex(0);
		}else
			UtilData.cargarGenericData(cmbTipoComprobante, false);

		row= new Row();
		row.appendChild(label);
		row.appendChild(cmbTipoComprobante);
		rows.appendChild(row);
		
		Div div= new Div();
		Button button= new Button();
		button.setLabel("Filtrar");
		button.setImage("/resources/mp_filtrar.png");
		div.setAlign("center");
		div.appendChild(button);
		
		grid.appendChild(rows);
		window.appendChild(grid);
		window.appendChild(div);
		
		cmbAgencia.addEventListener(Events.ON_FOCUS, new EventListener<Event>() {
			@Override
			public void onEvent(Event event) throws Exception {
				cmbAgencia.select();
			}
		});
		
		cmbTipoAgencia.addEventListener(Events.ON_CHANGE, new EventListener<Event>() {
			@Override
			public void onEvent(Event event) throws Exception {
				// TODO Auto-generated method stub
				cmbAgencia.getItems().clear();
				cmbTipoComprobante.getItems().clear();
				UtilData.cargarAgenciaXtipoAgencia(cmbAgencia,((TipoAgencia)cmbTipoAgencia.getSelectedItem().getValue()).getId(),true);
				cmbAgencia.setSelectedIndex(0);
				UtilData.cargarGenericData(cmbTipoComprobante, true);
			}
		});
		
		cmbAgencia.addEventListener(Events.ON_CHANGE, new EventListener<Event>() {
			@Override
			public void onEvent(Event event) throws Exception {
				Util.limpiarCombobox(cmbTipoComprobante);
				if(cmbAgencia.getSelectedIndex()>0){
					/*Carga comprobantes asociados a la agencia*/
					UtilData.cargarTipoComprobanteAgencia(cmbTipoComprobante,(Agencia)cmbAgencia.getSelectedItem().getValue(), true);
					cmbTipoComprobante.setSelectedIndex(0);
				}else{
					UtilData.cargarDataCombo(cmbTipoComprobante, TipoComprobante.class, true);
					cmbTipoComprobante.setSelectedIndex(0);
				}
			}
		});
		
		
		button.addEventListener(Events.ON_CLICK, new EventListener<Event>() {
			@Override
			public void onEvent(Event event) throws Exception {
				Util.limpiarListbox(listboxLista);
				criteriosBusqueda = new TreeMap<String, Object>();
				
				if(cmbAgencia.getSelectedIndex()>0)	
					criteriosBusqueda.put("agencia", cmbAgencia.getSelectedItem().getValue()); 
				if(cmbTipoComprobante.getSelectedIndex()>0)
					criteriosBusqueda.put("tipoComprobante", cmbTipoComprobante.getSelectedItem().getValue());								
				criteriosBusqueda.put("estadoRegistro", Constantes.VALUE_ACTIVO);

				criteriosOrdenar= new ArrayList<String>();
				criteriosOrdenar.add("agencia");
				
				listarRegistros(ServiceLocator.getEspecieValoradaManager().buscarPorX(criteriosBusqueda, criteriosOrdenar));
								
				window.onClose();
			}
			
		});
		
		
		/**Solo se habilita este control para el rol Superusuario*/
		cmbTipoAgencia.setDisabled(Constantes.ID_ROL_SUPER_USUARIO!=getRol().getId().intValue());
		cmbAgencia.setDisabled(Constantes.ID_ROL_SUPER_USUARIO!=getRol().getId().intValue());
		/********************************/
		
		
		return window;	
	}
	
	
	
}