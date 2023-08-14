/**
 * Proyecto		: SISVYR
 * Sistema		: Sistema de Ventas y Reservas
 * Descripción	:
 * Autor		: José Abanto
 * Fecha		: 25/04/2013
 */
package pe.itsb.vyrbus.view.ctrl;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import org.zkoss.zul.Button;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.Doublebox;
import org.zkoss.zul.Groupbox;
import org.zkoss.zul.Image;
import org.zkoss.zul.Intbox;
import org.zkoss.zul.Label;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Radio;
import org.zkoss.zul.Textbox;

import pe.itsb.vyrbus.model.bean.Agencia;
import pe.itsb.vyrbus.model.bean.TipoComprobante;
import pe.itsb.vyrbus.model.bean.VentaPasaje;
import pe.itsb.vyrbus.service.locator.ServiceLocator;
import pe.itsb.vyrbus.service.util.Constantes;
import pe.itsb.vyrbus.service.util.MyTime;
import pe.itsb.vyrbus.service.util.Util;
import pe.itsb.vyrbus.service.util.UtilData;
import pe.itsb.vyrbus.view.ui.WndBase;

/**
 * @author JABANTO
 *
 */
@SuppressWarnings("unused")
public class WndEnvioEspecieValorada extends WndBase implements Serializable {
	private static final long serialVersionUID = 1L;

	private Datebox dtxFechaEnvio;
	private Combobox cmbAgencia;
	private Combobox cmbTipoComprobanteEnvio;
	private Intbox ibxNumeroSerieEnvio;
	private Intbox ibxUltCorrEnvio;
	private Intbox ibxCantidadEnvio;

	private Textbox txtUltimoCorrelativoEmitido;
	private Doublebox dbxPromedioBoletosPorDia;
	private Textbox txtUltimoCorrelativoEnviado;
	private Textbox txtFechaUltimoEnvio;
	private Listbox lbxListaSolicitudes;
	private Listbox lbxListaEnvios;
	private Listbox lbxListaEstadoCorrAgencia;
	private Datebox dtxMesAnio;
	private Combobox cmbAgenciaBq;
	private Radio rbListaSolicitudes;
	private Radio rbListaEnvios;
	private Radio rbListaEstadoCorrAgencia;
	private Datebox dtxFechaSolicitud;
	private Combobox cmbAgenciaSolicita;
	private Combobox cmbTipoComprobanteSolicita;
	private Intbox ibxSerieSolicita;
	private Intbox ibxCantidadSolicita;
	private Textbox txtUsuarioSolicita;
	private Button btnNuevoEnvio;
	private Button btnCancelar;
	private Button btnGuardar;
	private Groupbox gbxVerificaCorrelativo;
	private Listbox lbxVerificaCorrelativos;
	private Label lblMesAnio;
	private Textbox txtXUtilizado;
	private Combobox cmbTipoComprobanteBq;
	private Intbox ibxSerieBq;

	private String fechaUltimoEnvio="01/04/2013";

	/*
	 * (non-Javadoc)
	 *
	 * @see com.tepsa.sisvyr.view.ui.WndBase#initComponents()
	 */
	@Override
	public void initComponents() {
		dtxFechaEnvio=(Datebox)this.getFellow("dtxFechaEnvio");
		cmbAgencia=(Combobox)this.getFellow("cmbAgencia");
		cmbTipoComprobanteEnvio=(Combobox)this.getFellow("cmbTipoComprobanteEnvio");
		ibxNumeroSerieEnvio=(Intbox)this.getFellow("ibxNumeroSerieEnvio");
		ibxUltCorrEnvio=(Intbox)this.getFellow("ibxUltCorrEnvio");
		ibxCantidadEnvio=(Intbox)this.getFellow("ibxCantidadEnvio");
		txtUltimoCorrelativoEmitido=(Textbox)this.getFellow("txtUltimoCorrelativoEmitido");
		dbxPromedioBoletosPorDia=(Doublebox)this.getFellow("dbxPromedioBoletosPorDia");
		txtUltimoCorrelativoEnviado=(Textbox)this.getFellow("txtUltimoCorrelativoEnviado");
		txtFechaUltimoEnvio=(Textbox)this.getFellow("txtFechaUltimoEnvio");
		lbxListaSolicitudes=(Listbox)this.getFellow("lbxListaSolicitudes");
		lbxListaEnvios=(Listbox)this.getFellow("lbxListaEnvios");
		lbxListaEstadoCorrAgencia=(Listbox) this.getFellow("lbxListaEstadoCorrAgencia");
		dtxMesAnio=(Datebox)this.getFellow("dtxMesAnio");
		cmbAgenciaBq=(Combobox)this.getFellow("cmbAgenciaBq");
		rbListaSolicitudes=(Radio)this.getFellow("rbListaSolicitudes");
		rbListaEnvios=(Radio)this.getFellow("rbListaEnvios");
		rbListaEstadoCorrAgencia=(Radio) this.getFellow("rbListaEstadoCorrAgencia");
		dtxFechaSolicitud=(Datebox)this.getFellow("dtxFechaSolicitud");
		cmbAgenciaSolicita=(Combobox)this.getFellow("cmbAgenciaSolicita");
		cmbTipoComprobanteSolicita=(Combobox)this.getFellow("cmbTipoComprobanteSolicita");
		ibxSerieSolicita=(Intbox)this.getFellow("ibxSerieSolicita");
		ibxCantidadSolicita=(Intbox)this.getFellow("ibxCantidadSolicita");
		txtUsuarioSolicita=(Textbox)this.getFellow("txtUsuarioSolicita");
		btnNuevoEnvio=(Button)this.getFellow("btnNuevoEnvio");
		btnCancelar=(Button)this.getFellow("btnCancelar");
		btnGuardar=(Button)this.getFellow("btnGuardar");
		lbxVerificaCorrelativos=(Listbox)this.getFellow("lbxVerificaCorrelativos");
		gbxVerificaCorrelativo=(Groupbox)this.getFellow("gbxVerificaCorrelativo");
		lblMesAnio=(Label)this.getFellow("lblMesAnio");
		txtXUtilizado=(Textbox)this.getFellow("txtXUtilizado");
		cmbTipoComprobanteBq=(Combobox)this.getFellow("cmbTipoComprobanteBq");
		ibxSerieBq=(Intbox)this.getFellow("ibxSerieBq");


	}

	/*
	 * (non-Javadoc)
	 *
	 * @see com.tepsa.sisvyr.view.ui.WndBase#onCreate()
	 */
	@Override
	public void onCreate() throws Exception {
		dtxMesAnio.setValue(Constantes.FORMAT_DATE.parse(new MyTime().dateServer()));
		dtxFechaEnvio.setValue(Constantes.FORMAT_DATE.parse(new MyTime().dateServer()));
		UtilData.cargarDataCombo(cmbAgenciaBq, Agencia.class, true);
		UtilData.cargarDataCombo(cmbAgenciaSolicita, Agencia.class, true);
		UtilData.cargarDataCombo(cmbAgencia, Agencia.class, false);
		UtilData.cargarDataCombo(cmbTipoComprobanteSolicita, TipoComprobante.class,false);
		UtilData.cargarDataCombo(cmbTipoComprobanteEnvio, TipoComprobante.class,false);
		dbxPromedioBoletosPorDia.setLocale(Locale.US);

		UtilData.cargarDataCombo(cmbTipoComprobanteBq, TipoComprobante.class, true);

		//String serie=null;Integer idComprobante=null;Integer idAgencia=null;

		//List<VentaPasaje> lstResul = ServiceLocator.getVentaPasajesManager().correlativosFaltantesX(fechaUltimoEnvio, serie, idComprobante, idAgencia);

	}

	/**
	 * Cuando el usuario selecciona (Solicitudes)
	 */
	public void onCheckSolicitudes() {
		lbxListaEnvios.setVisible(false);
		lbxListaEstadoCorrAgencia.setVisible(false);
		Util.limpiarListbox(lbxListaEnvios);
		Util.limpiarListbox(lbxListaEstadoCorrAgencia);
		lbxListaSolicitudes.setVisible(true);
		dtxMesAnio.setVisible(true);
		lblMesAnio.setVisible(true);
	}

	/**
	 * cuando el usuario seleccionada (Envios)
	 */
	public void onCheckEnvios() {
		lbxListaSolicitudes.setVisible(false);
		lbxListaEstadoCorrAgencia.setVisible(false);
		Util.limpiarListbox(lbxListaSolicitudes);
		Util.limpiarListbox(lbxListaEstadoCorrAgencia);
		lbxListaEnvios.setVisible(true);
		dtxMesAnio.setVisible(true);
		lblMesAnio.setVisible(true);
	}

	/**
	 * Cuando el usuario selecciona la busqueda de (Estado de correlativos por agencia)
	 * @throws Exception
	 */
	public void onCheckEstadoCorrAgencia() throws Exception {
		lbxListaSolicitudes.setVisible(false);
		lbxListaEnvios.setVisible(false);
		Util.limpiarListbox(lbxListaSolicitudes);
		Util.limpiarListbox(lbxListaEnvios);
		lbxListaEstadoCorrAgencia.setVisible(true);
		dtxMesAnio.setVisible(false);
		lblMesAnio.setVisible(false);
	}

	/**
	 * Nuevo envio
	 * @throws Exception
	 */
	public void onNuevoEnvio() throws Exception {
		Util.disabledBtnGuardar(false, btnGuardar, accesoGrabar());
		Util.disabledBtnCancelar(false, btnCancelar);
		Util.disabledBtnNuevo(true, btnNuevoEnvio, accesoNuevo());
		limpiarControles();
		disabledControl(false);
		Util.limpiarListbox(lbxVerificaCorrelativos);
	}

	/**
	 * Cancela el envio de especies valordas.
	 * @throws Exception
	 */
	public void onCancelar() throws Exception {
		Util.disabledBtnCancelar(true, btnCancelar);
		Util.disabledBtnGuardar(true, btnGuardar, accesoGrabar());
		Util.disabledBtnNuevo(false, btnNuevoEnvio, accesoNuevo());
		limpiarControles();
		disabledControl(true);
		gbxVerificaCorrelativo.setVisible(false);
	}

	/**
	 * Gruarda el envio de las especies valiradas.
	 * @throws Exception
	 */
	public void onGuardar() throws Exception {
		Util.disabledBtnCancelar(true, btnCancelar);
		Util.disabledBtnGuardar(true, btnGuardar, accesoGrabar());
		Util.disabledBtnNuevo(false, btnNuevoEnvio, accesoNuevo());
		disabledControl(true);
	}

	/**
	 * Limpia los controles de datos del envio
	 */
	private void limpiarControles() {
		//Limpia comtroles de la solicitud
		dtxFechaSolicitud.setText("");
		cmbAgencia.setSelectedIndex(0);
		cmbTipoComprobanteSolicita.setSelectedIndex(0);
		ibxSerieSolicita.setText("");
		ibxCantidadSolicita.setText("");
		txtUsuarioSolicita.setText("");

		//Limpia controles del envio
		cmbAgencia.setSelectedIndex(0);
		cmbTipoComprobanteEnvio.setSelectedIndex(0);
		ibxNumeroSerieEnvio.setText("");
		ibxUltCorrEnvio.setText("");
		ibxCantidadEnvio.setText("");

		//Ultimos correlativos
		txtUltimoCorrelativoEmitido.setText("");
		dbxPromedioBoletosPorDia.setText("");
		txtXUtilizado.setText("");
		txtUltimoCorrelativoEnviado.setText("");
		txtFechaUltimoEnvio.setText("");

	}

	/**
	 * Limpia los controles de los ultimos correlativos utilizados
	 */
	private void limpiarControlesUltimoCorrelatiovo(){
		txtUltimoCorrelativoEmitido.setText("");
		dbxPromedioBoletosPorDia.setText("");
		txtXUtilizado.setText("");
		txtUltimoCorrelativoEnviado.setText("");
		txtFechaUltimoEnvio.setText("");
	}

	/**
	 * Habilida o deshabilita controels
	 * @param disabled : true desactiva controles; false activa controles
	 */
	private void disabledControl(Boolean disabled){
		cmbAgencia.setDisabled(disabled);
		cmbTipoComprobanteEnvio.setDisabled(disabled);
		ibxNumeroSerieEnvio.setDisabled(disabled);
		ibxUltCorrEnvio.setDisabled(disabled);
		ibxCantidadEnvio.setDisabled(disabled);
	}

	/**
	 * Muestra información de los ultimos correlativos utilicados para la agencia.
	 * @throws Exception
	 */
	public void ultimosCorrelativos() throws Exception{
		String serie = null;Integer idComprobante = null;Integer idAgencia = null;
		if(cmbAgencia.getSelectedItem().getValue() instanceof Agencia)
			idAgencia=((Agencia)cmbAgencia.getSelectedItem().getValue()).getId();
		if(!(ibxNumeroSerieEnvio.getText().trim().isEmpty()))
			serie=ibxNumeroSerieEnvio.getValue().toString();
		if(cmbTipoComprobanteEnvio.getSelectedItem().getValue() instanceof TipoComprobante)
			idComprobante=((TipoComprobante)cmbTipoComprobanteEnvio.getSelectedItem().getValue()).getId();
		limpiarControlesUltimoCorrelatiovo();
		Util.limpiarListbox(lbxVerificaCorrelativos);
		lbxVerificaCorrelativos.setRows(0);

		if(serie!=null && idAgencia!=null && idComprobante!=null){
			List<VentaPasaje> lstResul = ServiceLocator.getVentaPasajesManager().buscarUltimoCorrelativoEmitido(fechaUltimoEnvio, serie, idComprobante, idAgencia);
			//------
			listaVerificaCorrelativos();
			if(lbxVerificaCorrelativos.getItems().size()>0)
				gbxVerificaCorrelativo.setVisible(true);
			//------
			if(lstResul.size()>0){
				VentaPasaje venta=lstResul.get(0);
				txtUltimoCorrelativoEmitido.setText(venta.getUltimoEmitido());
				dbxPromedioBoletosPorDia.setValue(venta.getPromedioXdia());
				txtXUtilizado.setText(String.valueOf(Util.calculaPorcentaje(Double.valueOf(venta.getUltimoEmitido()), Double.valueOf(venta.getUltimoEnviadoXAbastecimientos()))));
				txtUltimoCorrelativoEnviado.setText(venta.getUltimoEnviadoXAbastecimientos());
				txtFechaUltimoEnvio.setText(venta.getFechaUltimoEnvioXAbastecimientos());
			}else
				gbxVerificaCorrelativo.setVisible(false);
		}else
			gbxVerificaCorrelativo.setVisible(false);
	}

	/**
	 * Lista los relativos de la agencia
	 * @throws Exception
	 */
	public void listEstadoCorrAgencia() throws Exception {
		Util.limpiarListbox(lbxListaEstadoCorrAgencia);
		if (rbListaEstadoCorrAgencia.isChecked()) {
			String serie = null; Integer idComprobante = null; Integer idAgencia = null;
			if (cmbAgenciaBq.getSelectedItem().getValue() instanceof Agencia)
				idAgencia = ((Agencia) cmbAgenciaBq.getSelectedItem().getValue()).getId();
			if(cmbTipoComprobanteBq.getSelectedItem().getValue() instanceof TipoComprobante)
				idComprobante=((TipoComprobante)cmbTipoComprobanteBq.getSelectedItem().getValue()).getId();
			if (!(ibxSerieBq.getText().trim().isEmpty()))
				serie=ibxSerieBq.getText().trim();
			List<VentaPasaje> lstResul = ServiceLocator.getVentaPasajesManager().buscarUltimoCorrelativoEmitido(fechaUltimoEnvio, serie, idComprobante, idAgencia);

			Listitem item = null;
			Listcell cell = null;
			int x = 0;

			for (VentaPasaje venta : lstResul) {
				x++;
				double xUtilizado=Util.calculaPorcentaje(Double.valueOf(venta.getUltimoEmitido()),Double.valueOf(venta.getUltimoEnviadoXAbastecimientos()));

				item = new Listitem();
				cell = new Listcell(String.valueOf(x));
				if(xUtilizado>Constantes.ALERTAR_ENVIO_ESPECIES_VALORADAS)
					cell.setStyle("font-size:11px !important; color:red");
				else
					cell.setStyle("font-size:11px !important");
				item.appendChild(cell);
				cell = new Listcell(venta.getAgencia().getNombreCorto());
				if(xUtilizado>Constantes.ALERTAR_ENVIO_ESPECIES_VALORADAS)
					cell.setStyle("color:red");
				item.appendChild(cell);
				cell = new Listcell(venta.getTipoComprobante().getDenominacion());
				if(xUtilizado>Constantes.ALERTAR_ENVIO_ESPECIES_VALORADAS)
					cell.setStyle("color:red");
				item.appendChild(cell);
				cell = new Listcell(venta.getNumeroSerie());
				if(xUtilizado>Constantes.ALERTAR_ENVIO_ESPECIES_VALORADAS)
					cell.setStyle("font-size:11px !important; color:red");
				else
					cell.setStyle("font-size:11px !important");
				item.appendChild(cell);
				cell = new Listcell(venta.getUltimoEnviadoXAbastecimientos());
				if(xUtilizado>Constantes.ALERTAR_ENVIO_ESPECIES_VALORADAS)
					cell.setStyle("font-size:11px !important; color:red");
				else
					cell.setStyle("font-size:11px !important");
				item.appendChild(cell);
				cell = new Listcell(venta.getUltimoEmitido());
				if(xUtilizado>Constantes.ALERTAR_ENVIO_ESPECIES_VALORADAS)
					cell.setStyle("font-size:11px !important; color:red");
				else
					cell.setStyle("font-size:11px !important");
				item.appendChild(cell);
				cell = new Listcell(venta.getPromedioXdia().toString());
				if(xUtilizado>Constantes.ALERTAR_ENVIO_ESPECIES_VALORADAS)
					cell.setStyle("font-size:11px !important; color:red");
				else
					cell.setStyle("font-size:11px !important");
				item.appendChild(cell);
				cell = new Listcell(String.valueOf(xUtilizado));
				if(xUtilizado>Constantes.ALERTAR_ENVIO_ESPECIES_VALORADAS)
					cell.setStyle("font-size:11px !important; color:red");
				else
					cell.setStyle("font-size:11px !important");
				item.appendChild(cell);

				cell = new Listcell(venta.getFechaUltimoEnvioXAbastecimientos());
				if(xUtilizado>Constantes.ALERTAR_ENVIO_ESPECIES_VALORADAS)
					cell.setStyle("font-size:11px !important; color:red");
				else
					cell.setStyle("font-size:11px !important");
				item.appendChild(cell);
				cell = new Listcell(venta.getDiasTranscurridos().toString());
				if(xUtilizado>Constantes.ALERTAR_ENVIO_ESPECIES_VALORADAS)
					cell.setStyle("font-size:11px !important; color:red");
				else
					cell.setStyle("font-size:11px !important");
				item.appendChild(cell);

				item.setValue(venta);
				lbxListaEstadoCorrAgencia.appendChild(item);
			}
		}
	}


	/**
	 * Lista de correlativos de la agencia para verificar los posibles cortes que esta pueda tener
	 * @throws Exception
	 */
	public void listaVerificaCorrelativos() throws Exception{
		String serie = null;Integer idComprobante = null;Integer idAgencia = null;

		if(cmbAgencia.getSelectedItem().getValue() instanceof Agencia)
			idAgencia=((Agencia)cmbAgencia.getSelectedItem().getValue()).getId();
		if(!(ibxNumeroSerieEnvio.getText().trim().isEmpty()))
			serie=ibxNumeroSerieEnvio.getValue().toString();
		if(cmbTipoComprobanteEnvio.getSelectedItem().getValue() instanceof TipoComprobante)
			idComprobante=((TipoComprobante)cmbTipoComprobanteEnvio.getSelectedItem().getValue()).getId();

		if(serie!=null && idAgencia!=null && idComprobante!=null){
			List<VentaPasaje> lstResul = ServiceLocator.getVentaPasajesManager().correlativosFaltantesX(fechaUltimoEnvio, serie, idComprobante, idAgencia, Constantes.FORMAT_DATE.format(new Date()),null);
			if(lstResul.size()>0){
				VentaPasaje venta=lstResul.get(0);
				txtUltimoCorrelativoEmitido.setText(venta.getUltimoEmitido());
				dbxPromedioBoletosPorDia.setValue(venta.getPromedioXdia());
				txtFechaUltimoEnvio.setText(fechaUltimoEnvio);

				Listitem item=null;
				Listcell cell=null;
				int x=0;
				Long n_bolBack=(long)0;
				Long n_bolNext=(long)0;
				int parametro=10;//Indica la cantidad de correlativos del inicio al final
				String c_inicio="";
				String c_fin="";
				String c_serieNext="";
				String c_faltantes="";

				for(int i=0; i<lstResul.size();i++){
					c_faltantes="";x=0;
					if(i>0)
						i+=+parametro-1;
					if(i<lstResul.size()){
						VentaPasaje vp=lstResul.get(i);
						String c_serie=vp.getNumeroSerie();
						c_inicio=vp.getNumeroBoleto();

						/*Obtiene el ultimo número segun el rango configurador en la variable "parametro" y valida los correlativos faltantes*/
						String c_serieBack="";
						for(int z=i; z<lstResul.size(); z++){
							x++;
							if(Integer.valueOf(x)>parametro)
								break;

							c_serieNext=lstResul.get(z).getNumeroSerie();
							n_bolNext=Long.valueOf(lstResul.get(z).getNumeroBoleto());

							//Valida los correlativos faltantes
							if(n_bolBack>0)
								if(!(n_bolBack+1==n_bolNext)){
									Long n_falIni=n_bolBack; Long n_falFin=(long)0;
									for(int t=1; t<(n_bolNext-n_bolBack); t++){
										if(t==1)n_falIni++;
										n_falFin=n_falIni+t;
										if(t==(n_bolNext-n_bolBack)-1)n_falFin--;
									}
									if(!(n_falIni.equals(n_falFin)))
										c_faltantes+=String.valueOf(n_falIni+"-"+n_falFin)+"; ";
									else
										c_faltantes+=String.valueOf(n_falIni)+"; ";
								}

							//Obtiene el ultimo numero segun el rango configurador en la variable "parametro"
							if(c_serieNext.equals(c_serie))
								c_fin=lstResul.get(z).getNumeroBoleto();

							if(c_serieBack.length()>0 && (!c_serieNext.equals(c_serieBack)))
								break;

							c_serieBack=lstResul.get(z).getNumeroSerie();
							n_bolBack=Long.valueOf(lstResul.get(z).getNumeroBoleto());
						}

						item=new Listitem();
						cell=new Listcell(vp.getAgencia().getNombreCorto());
						if(c_faltantes.length()>0) cell.setStyle("color:red");
						item.appendChild(cell);

						cell=new Listcell(vp.getTipoComprobante().getDenominacion());
						if(c_faltantes.length()>0) cell.setStyle("color:red");
						item.appendChild(cell);

						cell=new Listcell(c_serie);
						if(c_faltantes.length()>0)cell.setStyle("font-size:11px !important; color:red");
						else cell.setStyle("font-size:11px !important");
						item.appendChild(cell);

						cell=new Listcell(c_inicio);
						if(c_faltantes.length()>0)cell.setStyle("font-size:11px !important; color:red");
						else cell.setStyle("font-size:11px !important");
						item.appendChild(cell);

						cell=new Listcell(c_fin);
						if(c_faltantes.length()>0)cell.setStyle("font-size:11px !important; color:red");
						else cell.setStyle("font-size:11px !important");
						item.appendChild(cell);

						if(c_faltantes.length()>0){
							cell=new Listcell(c_faltantes.length()>0? "Faltantes: "+c_faltantes:"");
							cell.setStyle("color:red; text-transform:capitalize; font-size:11px !important");
						}else{
							cell=new Listcell();
							Image img= new Image();
							img.setSrc("/resources/mp_aceptarEnabled.png");
							img.setHeight("13px !important");
							cell.appendChild(img);
						}
						item.appendChild(cell);
						lbxVerificaCorrelativos.appendChild(item);
					}
					if(lbxVerificaCorrelativos.getItems().size()>5)
						lbxVerificaCorrelativos.setRows(5);
				}

			}
		}

	}

}

