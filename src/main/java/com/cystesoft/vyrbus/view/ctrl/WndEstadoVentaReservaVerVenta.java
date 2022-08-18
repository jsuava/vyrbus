
package com.cystesoft.vyrbus.view.ctrl;

import java.util.List;
import java.util.TreeMap;

import org.zkoss.zul.Checkbox;
import org.zkoss.zul.Label;
import org.zkoss.zul.Tab;
//import org.zkoss.zul.Window;

import com.cystesoft.vyrbus.model.bean.Agencia;
import com.cystesoft.vyrbus.model.bean.Cliente;
import com.cystesoft.vyrbus.model.bean.Cortesia;
import com.cystesoft.vyrbus.model.bean.Pasajero;
import com.cystesoft.vyrbus.model.bean.Promocion;
import com.cystesoft.vyrbus.model.bean.TipoComprobante;
import com.cystesoft.vyrbus.model.bean.VentaPasaje;
import com.cystesoft.vyrbus.service.locator.ServiceLocator;
import com.cystesoft.vyrbus.service.util.Constantes;
import com.cystesoft.vyrbus.service.util.Util;
import com.cystesoft.vyrbus.view.ui.WndBase;


/**
 * @author JABANTO
 *
 */
public class WndEstadoVentaReservaVerVenta extends WndBase {
	private static final long serialVersionUID = 1L;

	/*INFORMACION DE LA VENTA*/
	private Label lblTipoOperacion;
	private Label lblCanalVenta;
	private Label lblServicio;
	private Label lblOrigen;
	private Label lblDestino;
	private Label lblPtoEmbarque;
	private Label lblPtoLlegada;
	private Label lblFechaPartida;
	private Label lblFechaLlegada;
	private Label lblHoraPartida;
	private Label lblHoraLlegada;
	private Label lblNroAsiento;
	private Label lblLabelTarifa;
	private Label lblTarifa;
	private Label lblNroBoleto;
	private Label lblNroBoletoRef;
	private Label lblAlimentacion;
	private Label lblObservaciones;
	private Label lblFechaEmision;
	private Label lblUsuarioEmision;
	/*PASAJERO*/
	private Label lblPaxTipoDocumento;
	private Label lblPaxNroDocumento;
	private Label lblPaxApellidoPaterno;
	private Label lblPaxApellidoMaterno;
	private Label lblPaxNombres;
	private Label lblPaxDireccion;
	private Label lblPaxTelefono;
	private Label lblPaxUbigeo;
	private Label lblPaxEmail;
	private Label lblPaxFechaNacimiento;
	private Label lblPaxSexo;
	private Label lblPaxEstadoCivil;
	/*CLIENTE*/
	private Label lblCliRuc;
	private Label lblCliRazonSocial;
	private Label lblCliTelefono1;
	private Label lblCliDireccion;
	private Label lblCliTelefono2;
	private Label lblCliUbigeo;
	private Label lblCliEmail;
	/*PAGOS*/
	private Label lblPagoTarifa;
	private Label lblPagoTipoComprobante;
	private Label lblPagoRecargo;
	private Label lblPagoFormaPago;
	private Label lblPagoDescuento;
	private Label lblPagoTipoFormaPago;
	private Label lblPagoImporte;
	private Label lblMoneda;
	private Checkbox chbxPagoMixto;
	private Label lblPagoOperadorTarjeta;
	private Label lblPagoLabelImporteEfectivo;
	private Label lblPagoImporteEfectivo;
	private Label lblPagoTarjetaCredito;
	private Label lblPagoLabelImporteTarjeta;
	private Label lblPagoImporteTarjeta;
	private Label lblPagoNumeroOperacion;
	private Label lblDescPromocion;
	/*CORTESIAS*/
	private Tab tbCortesia;
	private Label lblCortesiaTipo;
	private Label lblCortesiaMotivo;
	private Label lblCortesiaUsuarioAuto;
	private Label lblCortesiaUsuarioEmision;
	private Label lblCortesiaAgenciaEmision;
	/*DOCUMENTO REFERENCIA (N.C.)*/
	private Tab tbDoctReferencia;
	private Label lblDocRefTipoDocumento;
	private Label lblDocRefNumerodo;
	private Label lblDocRefFechaEmision;
	private Label lblDocRefMotivo;
	private Label lblDocRefAgenciaUsuario;

//	private Window wndEstadoVyRVerVenta;
//	private Button btnEnviarSunat;

	/*OTAS VARIABLES*/
	private VentaPasaje ventaPasaje;



	/**
	 * @return the ventaPasaje
	 */
	public VentaPasaje getVentaPasaje() {
		return ventaPasaje;
	}

	/**
	 * @param ventaPasaje the ventaPasaje to set
	 */
	public void setVentaPasaje(VentaPasaje ventaPasaje) {
		this.ventaPasaje = ventaPasaje;
	}

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.view.ui.WndBase#initComponents()
	 */
	@Override
	public void initComponents() {
		// TODO Auto-generated method stub
		/*INFORMACION DE LA VENTA*/
		lblTipoOperacion=(Label)this.getFellow("lblTipoOperacion");
		lblCanalVenta=(Label)this.getFellow("lblCanalVenta");
		lblServicio=(Label)this.getFellow("lblServicio");
		lblOrigen=(Label)this.getFellow("lblOrigen");
		lblDestino=(Label)this.getFellow("lblDestino");
		lblPtoEmbarque=(Label)this.getFellow("lblPtoEmbarque");
		lblPtoLlegada=(Label)this.getFellow("lblPtoLlegada");
		lblFechaPartida=(Label)this.getFellow("lblFechaPartida");
		lblFechaLlegada=(Label)this.getFellow("lblFechaLlegada");
		lblHoraPartida=(Label)this.getFellow("lblHoraPartida");
		lblHoraLlegada=(Label)this.getFellow("lblHoraLlegada");
		lblNroAsiento=(Label)this.getFellow("lblNroAsiento");
		lblLabelTarifa=(Label)this.getFellow("lblLabelTarifa");
		lblTarifa=(Label)this.getFellow("lblTarifa");
		lblNroBoleto=(Label)this.getFellow("lblNroBoleto");
		lblNroBoletoRef=(Label)this.getFellow("lblNroBoletoRef");
		lblAlimentacion=(Label)this.getFellow("lblAlimentacion");
		lblObservaciones=(Label)this.getFellow("lblObservaciones");
		lblFechaEmision=(Label)this.getFellow("lblFechaEmision");
		lblUsuarioEmision=(Label)this.getFellow("lblUsuarioEmision");
		/*PASAJERO*/
		lblPaxTipoDocumento=(Label)this.getFellow("lblPaxTipoDocumento");
		lblPaxNroDocumento=(Label)this.getFellow("lblPaxNroDocumento");
		lblPaxApellidoPaterno=(Label)this.getFellow("lblPaxApellidoPaterno");
		lblPaxApellidoMaterno=(Label)this.getFellow("lblPaxApellidoMaterno");
		lblPaxNombres=(Label)this.getFellow("lblPaxNombres");
		lblPaxDireccion=(Label)this.getFellow("lblPaxDireccion");
		lblPaxTelefono=(Label)this.getFellow("lblPaxTelefono");
		lblPaxUbigeo=(Label)this.getFellow("lblPaxUbigeo");
		lblPaxEmail=(Label)this.getFellow("lblPaxEmail");
		lblPaxFechaNacimiento=(Label)this.getFellow("lblPaxFechaNacimiento");
		lblPaxSexo=(Label)this.getFellow("lblPaxSexo");
		lblPaxEstadoCivil=(Label)this.getFellow("lblPaxEstadoCivil");
		/*CLIENTE*/
		lblCliRuc=(Label)this.getFellow("lblCliRuc");
		lblCliRazonSocial=(Label)this.getFellow("lblCliRazonSocial");
		lblCliTelefono1=(Label)this.getFellow("lblCliTelefono1");
		lblCliDireccion=(Label)this.getFellow("lblCliDireccion");
		lblCliTelefono2=(Label)this.getFellow("lblCliTelefono2");
		lblCliUbigeo=(Label)this.getFellow("lblCliUbigeo");
		lblCliEmail=(Label)this.getFellow("lblCliEmail");
		/*PAGOS*/
		lblPagoTarifa=(Label)this.getFellow("lblPagoTarifa");
		lblPagoTipoComprobante=(Label)this.getFellow("lblPagoTipoComprobante");
		lblPagoRecargo=(Label)this.getFellow("lblPagoRecargo");
		lblPagoFormaPago=(Label)this.getFellow("lblPagoFormaPago");
		lblPagoDescuento=(Label)this.getFellow("lblPagoDescuento");
		lblPagoTipoFormaPago=(Label)this.getFellow("lblPagoTipoFormaPago");
		lblPagoImporte=(Label)this.getFellow("lblPagoImporte");
		lblMoneda=(Label)this.getFellow("lblMoneda");
		chbxPagoMixto=(Checkbox)this.getFellow("chbxPagoMixto");
		lblPagoOperadorTarjeta=(Label)this.getFellow("lblPagoOperadorTarjeta");
		lblPagoLabelImporteEfectivo=(Label)this.getFellow("lblPagoLabelImporteEfectivo");
		lblPagoImporteEfectivo=(Label)this.getFellow("lblPagoImporteEfectivo");
		lblPagoTarjetaCredito=(Label)this.getFellow("lblPagoTarjetaCredito");
		lblPagoLabelImporteTarjeta=(Label)this.getFellow("lblPagoLabelImporteTarjeta");
		lblPagoImporteTarjeta=(Label)this.getFellow("lblPagoImporteTarjeta");
		lblPagoNumeroOperacion=(Label)this.getFellow("lblPagoNumeroOperacion");
		lblDescPromocion=(Label)this.getFellow("lblDescPromocion");
		/*CORTESIA*/
		tbCortesia=(Tab)this.getFellow("tbCortesia");
		lblCortesiaTipo=(Label)this.getFellow("lblCortesiaTipo");
		lblCortesiaMotivo=(Label)this.getFellow("lblCortesiaMotivo");
		lblCortesiaUsuarioAuto=(Label)this.getFellow("lblCortesiaUsuarioAuto");
		lblCortesiaUsuarioEmision=(Label)this.getFellow("lblCortesiaUsuarioEmision");
		lblCortesiaAgenciaEmision=(Label)this.getFellow("lblCortesiaAgenciaEmision");
		/*DOCUMENTO REFERENCIA (N.C)*/
		tbDoctReferencia=(Tab)this.getFellow("tbDoctReferencia");
		lblDocRefTipoDocumento=(Label)this.getFellow("lblDocRefTipoDocumento");
		lblDocRefNumerodo=(Label)this.getFellow("lblDocRefNumerodo");
		lblDocRefFechaEmision=(Label)this.getFellow("lblDocRefFechaEmision");
		lblDocRefMotivo=(Label)this.getFellow("lblDocRefMotivo");
		lblDocRefAgenciaUsuario=(Label)this.getFellow("lblDocRefAgenciaUsuario");
//		wndEstadoVyRVerVenta=(Window)this.getFellow("wndEstadoVyRVerVenta");
//		btnEnviarSunat=(Button)this.getFellow("btnEnviarSunat");
	}

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.view.ui.WndBase#onCreate()
	 */
	@Override
	public void onCreate() throws Exception {
		// TODO Auto-generated method stub
		String tipoOperacion=getVentaPasaje().getTipoTransaccion();
		ventaPasaje=ServiceLocator.getVentaPasajesManager().buscarPorId(getVentaPasaje().getId());
		/*INFORMACION DE LA VENTA*/
		lblTipoOperacion.setValue(tipoOperacion);
		if(ventaPasaje.getRucClienteCredito()!=null){
			Agencia agencia= ServiceLocator.getAgenciaManager().buscarAgenciaByRucClienteCredito(ventaPasaje.getRucClienteCredito());
			if(agencia!=null)
				lblCanalVenta.setValue(ventaPasaje.getCanalVenta().getDenominacion()+" <===> "+agencia.getDenominacion());
			else
				lblCanalVenta.setValue(ventaPasaje.getCanalVenta().getDenominacion()+" <===> "+"AGENCIA NO ENCONTRADA");
		}else
			lblCanalVenta.setValue(ventaPasaje.getCanalVenta().getDenominacion()+" <===> "+ventaPasaje.getAgencia().getDenominacion());

		lblServicio.setValue(ventaPasaje.getServicio()!=null?ventaPasaje.getServicio().getDenominacion():"");
		lblOrigen.setValue(ventaPasaje.getRuta()!=null?ventaPasaje.getRuta().getOrigen():"");
		lblDestino.setValue(ventaPasaje.getRuta()!=null?ventaPasaje.getRuta().getDestino():"");
		lblPtoEmbarque.setValue(ventaPasaje.getAgenciaPartida()!=null?ventaPasaje.getAgenciaPartida().getNombreCorto():"");
		lblPtoLlegada.setValue(ventaPasaje.getAgenciaLlegada()!=null?ventaPasaje.getAgenciaLlegada().getNombreCorto():"");
		lblFechaPartida.setValue(ventaPasaje.getItinerario().getId().intValue()!=1?Constantes.FORMAT_DATE.format(ventaPasaje.getItinerario().getFechaPartida()):"");
		lblFechaLlegada.setValue(ventaPasaje.getItinerario().getId().intValue()!=1?Constantes.FORMAT_DATE.format(ventaPasaje.getItinerario().getFechaLlegada()):"");
		lblHoraPartida.setValue(ventaPasaje.getHoraPartida()!=null?ventaPasaje.getHoraPartida():"");
		lblHoraLlegada.setValue(ventaPasaje.getHoraLllegada()!=null?ventaPasaje.getHoraLllegada():"");
		lblNroAsiento.setValue(ventaPasaje.getNumeroAsiento()!=null?ventaPasaje.getNumeroAsiento().toString():"");
		//Cuando es una reserva
		if(ventaPasaje.getTipoTransaccion().equals(Constantes.TIPO_OPERACION_RESERVA)){
			lblLabelTarifa.setValue("FECHA HORA EXPIRA :");
			String fechahoraExpress=ventaPasaje.getFechaExpiracionReserva()!=null?Constantes.FORMAT_DATE.format(ventaPasaje.getFechaExpiracionReserva()):"";
			fechahoraExpress+=ventaPasaje.getHoraExpiracionReserva()!=null?"  "+ventaPasaje.getHoraExpiracionReserva():"";
			lblTarifa.setValue(fechahoraExpress);
//			lblTarifa.setValue(ventaPasaje.getFechaExpiracionReserva()!=null?ventaPasaje.getFechaExpiracionReserva().toString():"");
		}else{
			lblLabelTarifa.setValue("TARIFA :");
			lblTarifa.setValue(ventaPasaje.getTarifa()!=null?Util.toNumberFormat(ventaPasaje.getTarifa(),2):"");
		}
		lblNroBoleto.setValue(ventaPasaje.getNumeroBoleto());
		lblNroBoletoRef.setValue(ventaPasaje.getNumeroBoletoAnterior()!=null?ventaPasaje.getNumeroBoletoAnterior():"");
		lblAlimentacion.setValue(ventaPasaje.getPreferenciaAlimentaria()!=null?ventaPasaje.getPreferenciaAlimentaria().getDenominacion():"");
		lblFechaEmision.setValue(ventaPasaje.getFechaInsercion()!=null?Constantes.FORMAT_LONG.format(ventaPasaje.getFechaInsercion()):"");
		lblUsuarioEmision.setValue(ventaPasaje.getUsuario()!=null?ventaPasaje.getUsuario().toString()+" - "+ventaPasaje.getAgencia().getNombreCorto():"");
		lblObservaciones.setValue(ventaPasaje.getObservaciones());
		/*PASAJERO*/
		Pasajero pasajero=ventaPasaje.getPasajero();
		lblPaxTipoDocumento.setValue(pasajero.getTipoDocumento().getDenominacion());
		lblPaxNroDocumento.setValue(pasajero.getNumeroDocumento()!=null?pasajero.getNumeroDocumento():"");
		lblPaxApellidoPaterno.setValue(pasajero.getApellidoPaterno());
		lblPaxApellidoMaterno.setValue(pasajero.getApellidoMaterno());
		lblPaxNombres.setValue(pasajero.getNombre());
		lblPaxDireccion.setValue(pasajero.getDireccion());
		lblPaxTelefono.setValue(pasajero.getTelefono());
		lblPaxUbigeo.setValue(pasajero.getUbigeo()!=null?ServiceLocator.getUbigeoManager().ubicacionGeografica(pasajero.getUbigeo()):"");
		lblPaxEmail.setValue(pasajero.getEmail());
		lblPaxFechaNacimiento.setValue(pasajero.getFechaNacimiento()!=null?pasajero.getFechaNacimiento():"");
		lblPaxSexo.setValue(pasajero.getSexo().getDenominacion());
		lblPaxEstadoCivil.setValue(pasajero.getEstadoCivil()!=null?pasajero.getEstadoCivil().getDenominacion():"");
		/*CLIENTE*/
		Cliente cliente=ventaPasaje.getCliente();
		lblCliRuc.setValue(cliente!=null? cliente.getNumeroDocumento():"");
		lblCliRazonSocial.setValue(cliente!=null? cliente.getRazonSocial():"");
		lblCliTelefono1.setValue(cliente!=null? cliente.getTelefonoFijo():"");
		lblCliDireccion.setValue(cliente!=null? cliente.getDireccion():"");
		lblCliTelefono2.setValue(cliente!=null? cliente.getTelefonoFijo2():"");
		lblCliUbigeo.setValue(cliente!=null? cliente.getUbigeo()!=null? ServiceLocator.getUbigeoManager().ubicacionGeografica(cliente.getUbigeo()):"":"");
		lblCliEmail.setValue(cliente!=null? cliente.getEmail():"");
		/*PAGOS*/
		lblPagoTarifa.setValue(Util.toNumberFormat(ventaPasaje.getTarifa(), 2));
		lblPagoTipoComprobante.setValue(ventaPasaje.getTipoComprobante().getDenominacion());
		lblPagoRecargo.setValue(ventaPasaje.getRecargo()!=null?Util.toNumberFormat(ventaPasaje.getRecargo(),2):"0.00");
		lblPagoFormaPago.setValue(ventaPasaje.getFormaPago()!=null?ventaPasaje.getFormaPago().getDenominacion():"");
		lblPagoDescuento.setValue(ventaPasaje.getDescuento()!=null? Util.toNumberFormat(ventaPasaje.getDescuento(), 2):"0.00");
		lblPagoTipoFormaPago.setValue(ventaPasaje.getTipoFormaPago()!=null?ventaPasaje.getTipoFormaPago().getDenominacion():"");
		lblPagoImporte.setValue(Util.toNumberFormat(ventaPasaje.getImportePagado(), 2));
		//Cuando es Pago mixo
		if(ventaPasaje.getImportePagadoEfectivo()!=null && ventaPasaje.getImportePagadoEfectivo()>0){
			chbxPagoMixto.setChecked(true);
			lblPagoLabelImporteEfectivo.setVisible(true);
			lblPagoLabelImporteTarjeta.setVisible(true);
			lblPagoImporteEfectivo.setVisible(true);
			lblPagoImporteTarjeta.setVisible(true);
			lblPagoImporteEfectivo.setValue(Util.toNumberFormat(ventaPasaje.getImportePagadoEfectivo(), 2));
			lblPagoImporteTarjeta.setValue(Util.toNumberFormat(ventaPasaje.getImportePagadoTarjeta(), 2));
		}
		lblPagoOperadorTarjeta.setValue(ventaPasaje.getTarjetaCredito()!=null? ventaPasaje.getTarjetaCredito().getOperadorTarjetaCredito().getDenominacion(): "");
		lblPagoTarjetaCredito.setValue(ventaPasaje.getTarjetaCredito()!=null? ventaPasaje.getTarjetaCredito().getDenominacion():"");
		lblPagoNumeroOperacion.setValue(ventaPasaje.getNumeroOperacionBancaria()!=null? ventaPasaje.getNumeroOperacionBancaria():"");
		lblMoneda.setValue(ventaPasaje.getTipoMoneda()!=null ? ventaPasaje.getTipoMoneda().getDenominacion() : "SOLES");

		/*Si es que fue aplicada alguna promocion */
		if(ventaPasaje.getPromocion()!=null){
			Promocion promocion=ventaPasaje.getPromocion();
			String tipoDescuento=promocion.getTipoDescuento().equals("P")?" %":" S/";
			String descPromocion="PROMOCIÓN: "+promocion.getDenominacion()+" <=======> BENEFICIO : "+Util.toNumberFormat(Double.valueOf(promocion.getValorDescuento()),2)+tipoDescuento;
			lblDescPromocion.setValue(descPromocion);

		}

		tbCortesia.setVisible(false);
		/*CORTESIA*/
		if(ventaPasaje.getFormaPago()!=null && ventaPasaje.getFormaPago().getId().intValue()==Constantes.ID_FORPAG_CORTESIA){
			if (ventaPasaje.getTipoFormaPago().getId().intValue()!=Constantes.ID_TIPFORPAG_PUNTOS &&
			    ventaPasaje.getTipoFormaPago().getId().intValue()!=Constantes.ID_TIPFORPAG_CUMPLEANIOS ){
				tbCortesia.setVisible(true);
				//Busca el registro por la idVentaOriginal
				VentaPasaje ventaOriginal=ServiceLocator.getVentaPasajesManager().buscarPorId(ventaPasaje.getVentaOriginal()!=null?ventaPasaje.getVentaOriginal():ventaPasaje.getId());
				//Busca la cortesia emitida como fecha abierta
				TreeMap<String, Object>criteriosBusqueda=new TreeMap<>();
				criteriosBusqueda.put("numeroControl", ventaOriginal.getNumeroControl());
				criteriosBusqueda.put("tipoTransaccion", Constantes.TIPO_OPERACION_RESERVA);
				List<VentaPasaje> lstVentaCortesia=ServiceLocator.getVentaPasajesManager().buscarPorX(criteriosBusqueda, null);
				if(lstVentaCortesia.size()>0){
					//Busca la cortesia
					Cortesia cortesia=ServiceLocator.getCortesiaManager().buscarXIDventa(lstVentaCortesia.get(0).getId());
					if(cortesia!=null){
						lblCortesiaTipo.setValue(cortesia.getTipoFormaPago().getDenominacion());
						if(cortesia.getMotivoCortesia().getId().intValue()!=Constantes.ID_MOTCOR_OTROS)
							lblCortesiaMotivo.setValue(cortesia.getMotivoCortesia().getDenominacion());
						else if(cortesia.getDetalleMotivoCortesia()!=null)
							lblCortesiaMotivo.setValue(cortesia.getDetalleMotivoCortesia());
						lblCortesiaUsuarioAuto.setValue(cortesia.getPersonal().toString());
						lblCortesiaUsuarioEmision.setValue(lstVentaCortesia.get(0).getUsuario().toString());
						lblCortesiaAgenciaEmision.setValue(lstVentaCortesia.get(0).getAgencia().getDenominacion());
					}
				}
			}
		}

		/*DOCUMENTO REFERENCIA*/
		tbDoctReferencia.setVisible(false);
		//Valida si es una Boleta o Factura
		if(ventaPasaje.getTipoComprobante().getId().intValue()==Constantes.ID_TIPCOM_BOLETA_VENTA || ventaPasaje.getTipoComprobante().getId().intValue()==Constantes.ID_TIPCOM_FACTURA){
			//Valida si el comprobante tiene una nota de credito aplicada
			TreeMap<String, Object>criteriosBusqueda= new TreeMap<>();
			criteriosBusqueda.put("tipoComprobante", new TipoComprobante(Constantes.ID_TIPCOM_NOTA_CREDITO));
			criteriosBusqueda.put("ventaPasaje", ventaPasaje);
			criteriosBusqueda.put("estadoRegistro", Constantes.VALUE_ACTIVO);
			List<VentaPasaje>result=ServiceLocator.getVentaPasajesManager().buscarPorX(criteriosBusqueda, null);
			if(result.size()>0){
				VentaPasaje notaCredito=result.get(0);
				lblDocRefTipoDocumento.setValue(notaCredito.getTipoComprobante().getDenominacion());
				lblDocRefNumerodo.setValue(notaCredito.getNumeroBoleto());
				lblDocRefFechaEmision.setValue(Constantes.FORMAT_DATE.format(notaCredito.getFechaLiquidacion()));
				lblDocRefMotivo.setValue(notaCredito.getObservaciones());
				lblDocRefAgenciaUsuario.setValue(notaCredito.getAgencia().toString() + " - " + notaCredito.getUsuario().toString() );
				tbDoctReferencia.setLabel("Nota de Crédito");
				tbDoctReferencia.setVisible(true);
			}
		}else if(ventaPasaje.getTipoComprobante().getId().intValue()==Constantes.ID_TIPCOM_NOTA_CREDITO){
			//Si es una nota de credito
			VentaPasaje ventaReferencial = ServiceLocator.getVentaPasajesManager().buscarPorId(ventaPasaje.getVentaPasaje().getId());
			String tipoDocuemnto=ventaReferencial.getTipoComprobante().getDenominacion();
			lblDocRefTipoDocumento.setValue(tipoDocuemnto);
			lblDocRefNumerodo.setValue(ventaReferencial.getNumeroBoleto());
			lblDocRefFechaEmision.setValue(Constantes.FORMAT_DATE.format(ventaReferencial.getFechaLiquidacion()));
			lblDocRefAgenciaUsuario.setValue(ventaReferencial.getAgencia().toString() + " - " +ventaReferencial.getUsuario().toString() );
			tbDoctReferencia.setLabel(tipoDocuemnto.substring(0,1)+""+tipoDocuemnto.substring(1).toLowerCase());
			tbDoctReferencia.setVisible(true);
		}
	}
}
