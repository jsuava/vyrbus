
package com.cystesoft.vyrbus.service.fe;

import java.math.BigDecimal;
import java.math.BigInteger;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.datatype.Duration;
import javax.xml.datatype.XMLGregorianCalendar;
import javax.xml.namespace.QName;

/**
 * This object contains factory methods for each Java content interface and Java
 * element interface generated in the com.cystesoft.vyrbus.service.fe package.
 * <p>
 * An ObjectFactory allows you to programatically construct new instances of the
 * Java representation for XML content. The Java representation of XML content
 * can consist of schema derived interfaces and classes representing the binding
 * of schema type definitions, element declarations and model groups. Factory
 * methods for each of these are provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {

	private final static QName _UnsignedLong_QNAME = new QName("http://schemas.microsoft.com/2003/10/Serialization/",
			"unsignedLong");
	private final static QName _UnsignedByte_QNAME = new QName("http://schemas.microsoft.com/2003/10/Serialization/",
			"unsignedByte");
	private final static QName _UnsignedShort_QNAME = new QName("http://schemas.microsoft.com/2003/10/Serialization/",
			"unsignedShort");
	private final static QName _DocumentoReferencia_QNAME = new QName(
			"http://schemas.datacontract.org/2004/07/FEService.Input", "DocumentoReferencia");
	private final static QName _Duration_QNAME = new QName("http://schemas.microsoft.com/2003/10/Serialization/",
			"duration");
	private final static QName _DetalleVenta_QNAME = new QName(
			"http://schemas.datacontract.org/2004/07/FEService.Input", "DetalleVenta");
	private final static QName _RetencionDetalleInp_QNAME = new QName(
			"http://schemas.datacontract.org/2004/07/FEService.Input", "RetencionDetalleInp");
	private final static QName _Long_QNAME = new QName("http://schemas.microsoft.com/2003/10/Serialization/", "long");
	private final static QName _Nota_QNAME = new QName("http://schemas.datacontract.org/2004/07/FEService.Input",
			"Nota");
	private final static QName _Float_QNAME = new QName("http://schemas.microsoft.com/2003/10/Serialization/", "float");
	private final static QName _DocumentCDR_QNAME = new QName("http://schemas.datacontract.org/2004/07/FEService.Input",
			"DocumentCDR");
	private final static QName _InformacionAdicional_QNAME = new QName(
			"http://schemas.datacontract.org/2004/07/FEService.Input", "InformacionAdicional");
	private final static QName _DateTime_QNAME = new QName("http://schemas.microsoft.com/2003/10/Serialization/",
			"dateTime");
	private final static QName _ArrayOfDetalleVenta_QNAME = new QName(
			"http://schemas.datacontract.org/2004/07/FEService.Input", "ArrayOfDetalleVenta");
	private final static QName _InformacionAdicionalTotalMonedaAdicional_QNAME = new QName(
			"http://schemas.datacontract.org/2004/07/FEService.Input", "InformacionAdicional.TotalMonedaAdicional");
	private final static QName _AnyType_QNAME = new QName("http://schemas.microsoft.com/2003/10/Serialization/",
			"anyType");
	private final static QName _String_QNAME = new QName("http://schemas.microsoft.com/2003/10/Serialization/",
			"string");
	private final static QName _InformacionAdicionalPropiedadAdicional_QNAME = new QName(
			"http://schemas.datacontract.org/2004/07/FEService.Input", "InformacionAdicional.PropiedadAdicional");
	private final static QName _UnsignedInt_QNAME = new QName("http://schemas.microsoft.com/2003/10/Serialization/",
			"unsignedInt");
	private final static QName _Char_QNAME = new QName("http://schemas.microsoft.com/2003/10/Serialization/", "char");
	private final static QName _Cliente_QNAME = new QName("http://schemas.datacontract.org/2004/07/FEService.Input",
			"Cliente");
	private final static QName _Short_QNAME = new QName("http://schemas.microsoft.com/2003/10/Serialization/", "short");
	private final static QName _Guid_QNAME = new QName("http://schemas.microsoft.com/2003/10/Serialization/", "guid");
	private final static QName _Decimal_QNAME = new QName("http://schemas.microsoft.com/2003/10/Serialization/",
			"decimal");
	private final static QName _Boolean_QNAME = new QName("http://schemas.microsoft.com/2003/10/Serialization/",
			"boolean");
	private final static QName _ArrayOfRetencionDetalleInp_QNAME = new QName(
			"http://schemas.datacontract.org/2004/07/FEService.Input", "ArrayOfRetencionDetalleInp");
	private final static QName _ArrayOfInformacionAdicionalTotalMonedaAdicional_QNAME = new QName(
			"http://schemas.datacontract.org/2004/07/FEService.Input",
			"ArrayOfInformacionAdicional.TotalMonedaAdicional");
	private final static QName _RetencionInp_QNAME = new QName(
			"http://schemas.datacontract.org/2004/07/FEService.Input", "RetencionInp");
	private final static QName _Base64Binary_QNAME = new QName("http://schemas.microsoft.com/2003/10/Serialization/",
			"base64Binary");
	private final static QName _Int_QNAME = new QName("http://schemas.microsoft.com/2003/10/Serialization/", "int");
	private final static QName _AnyURI_QNAME = new QName("http://schemas.microsoft.com/2003/10/Serialization/",
			"anyURI");
	private final static QName _Result_QNAME = new QName("http://schemas.datacontract.org/2004/07/FEService.Util",
			"Result");
	private final static QName _Venta_QNAME = new QName("http://schemas.datacontract.org/2004/07/FEService.Input",
			"Venta");
	private final static QName _ArrayOfInformacionAdicionalPropiedadAdicional_QNAME = new QName(
			"http://schemas.datacontract.org/2004/07/FEService.Input",
			"ArrayOfInformacionAdicional.PropiedadAdicional");
	private final static QName _ArrayOfRetencionInp_QNAME = new QName(
			"http://schemas.datacontract.org/2004/07/FEService.Input", "ArrayOfRetencionInp");
	private final static QName _DocumentoBaja_QNAME = new QName(
			"http://schemas.datacontract.org/2004/07/FEService.Input", "DocumentoBaja");
	private final static QName _Byte_QNAME = new QName("http://schemas.microsoft.com/2003/10/Serialization/", "byte");
	private final static QName _Double_QNAME = new QName("http://schemas.microsoft.com/2003/10/Serialization/",
			"double");
	private final static QName _QName_QNAME = new QName("http://schemas.microsoft.com/2003/10/Serialization/", "QName");
	private final static QName _SetConsultarCDRResponseSetConsultarCDRResult_QNAME = new QName("http://tempuri.org/",
			"setConsultarCDRResult");
	private final static QName _SetBajasResponseSetBajasResult_QNAME = new QName("http://tempuri.org/",
			"setBajasResult");
	private final static QName _InformacionAdicionalPropiedadAdicionalNombre_QNAME = new QName(
			"http://schemas.datacontract.org/2004/07/FEService.Input", "nombre");
	private final static QName _InformacionAdicionalPropiedadAdicionalValue_QNAME = new QName(
			"http://schemas.datacontract.org/2004/07/FEService.Input", "value");
	private final static QName _InformacionAdicionalPropiedadAdicionalCodigo_QNAME = new QName(
			"http://schemas.datacontract.org/2004/07/FEService.Input", "codigo");
	private final static QName _RetencionInpSerie_QNAME = new QName(
			"http://schemas.datacontract.org/2004/07/FEService.Input", "Serie");
	private final static QName _RetencionInpTasaRetencion_QNAME = new QName(
			"http://schemas.datacontract.org/2004/07/FEService.Input", "tasaRetencion");
	private final static QName _RetencionInpRazonSocial_QNAME = new QName(
			"http://schemas.datacontract.org/2004/07/FEService.Input", "RazonSocial");
	private final static QName _RetencionInpNumeroCorrelativo_QNAME = new QName(
			"http://schemas.datacontract.org/2004/07/FEService.Input", "NumeroCorrelativo");
	private final static QName _RetencionInpId_QNAME = new QName(
			"http://schemas.datacontract.org/2004/07/FEService.Input", "id");
	private final static QName _RetencionInpDetalleRetencion_QNAME = new QName(
			"http://schemas.datacontract.org/2004/07/FEService.Input", "detalleRetencion");
	private final static QName _RetencionInpRegistroRetencion_QNAME = new QName(
			"http://schemas.datacontract.org/2004/07/FEService.Input", "registroRetencion");
	private final static QName _RetencionInpCorreo_QNAME = new QName(
			"http://schemas.datacontract.org/2004/07/FEService.Input", "correo");
	private final static QName _RetencionInpFechaEmision_QNAME = new QName(
			"http://schemas.datacontract.org/2004/07/FEService.Input", "FechaEmision");
	private final static QName _RetencionInpRuc_QNAME = new QName(
			"http://schemas.datacontract.org/2004/07/FEService.Input", "Ruc");
	private final static QName _RetencionInpImporteTotalRetenidoEnLetras_QNAME = new QName(
			"http://schemas.datacontract.org/2004/07/FEService.Input", "ImporteTotalRetenidoEnLetras");
	private final static QName _DocumentoBajaDescripcionMotivo_QNAME = new QName(
			"http://schemas.datacontract.org/2004/07/FEService.Input", "descripcionMotivo");
	private final static QName _DocumentoBajaUsuarioInsercion_QNAME = new QName(
			"http://schemas.datacontract.org/2004/07/FEService.Input", "usuarioInsercion");
	private final static QName _DocumentoBajaFechaEmision_QNAME = new QName(
			"http://schemas.datacontract.org/2004/07/FEService.Input", "fechaEmision");
	private final static QName _DocumentoBajaUsuarioModificacion_QNAME = new QName(
			"http://schemas.datacontract.org/2004/07/FEService.Input", "usuarioModificacion");
	private final static QName _DocumentoBajaNumeroCorrelativo_QNAME = new QName(
			"http://schemas.datacontract.org/2004/07/FEService.Input", "numeroCorrelativo");
	private final static QName _DocumentoBajaRucEmpresa_QNAME = new QName(
			"http://schemas.datacontract.org/2004/07/FEService.Input", "rucEmpresa");
	private final static QName _DocumentoBajaNumeroSerie_QNAME = new QName(
			"http://schemas.datacontract.org/2004/07/FEService.Input", "numeroSerie");
	private final static QName _DocumentoBajaTipoDocumentoID_QNAME = new QName(
			"http://schemas.datacontract.org/2004/07/FEService.Input", "tipoDocumentoID");
	private final static QName _RetencionDetalleInpFechaPago_QNAME = new QName(
			"http://schemas.datacontract.org/2004/07/FEService.Input", "fechaPago");
	private final static QName _RetencionDetalleInpMonedaOriginal_QNAME = new QName(
			"http://schemas.datacontract.org/2004/07/FEService.Input", "monedaOriginal");
	private final static QName _RetencionDetalleInpFechaEmisonDoctRelacionado_QNAME = new QName(
			"http://schemas.datacontract.org/2004/07/FEService.Input", "fechaEmisonDoctRelacionado");
	private final static QName _RetencionDetalleInpMonedaPagado_QNAME = new QName(
			"http://schemas.datacontract.org/2004/07/FEService.Input", "monedaPagado");
	private final static QName _RetencionDetalleInpTipoDoctRelacionado_QNAME = new QName(
			"http://schemas.datacontract.org/2004/07/FEService.Input", "tipoDoctRelacionado");
	private final static QName _RetencionDetalleInpNumDoctRelacionado_QNAME = new QName(
			"http://schemas.datacontract.org/2004/07/FEService.Input", "numDoctRelacionado");
	private final static QName _RetencionDetalleInpNumeroPago_QNAME = new QName(
			"http://schemas.datacontract.org/2004/07/FEService.Input", "numeroPago");
	private final static QName _RetencionDetalleInpMonedaDoctRelacionado_QNAME = new QName(
			"http://schemas.datacontract.org/2004/07/FEService.Input", "monedaDoctRelacionado");
	private final static QName _SetEnviarComprobantesSunatResponseSetEnviarComprobantesSunatResult_QNAME = new QName(
			"http://tempuri.org/", "setEnviarComprobantesSunatResult");
	private final static QName _InformacionAdicionalTotalMonedaAdicionalValor_QNAME = new QName(
			"http://schemas.datacontract.org/2004/07/FEService.Input", "valor");
	private final static QName _VentaListDetalleVenta_QNAME = new QName(
			"http://schemas.datacontract.org/2004/07/FEService.Input", "listDetalleVenta");
	private final static QName _VentaNumeroPrefactura_QNAME = new QName(
			"http://schemas.datacontract.org/2004/07/FEService.Input", "numeroPrefactura");
	private final static QName _VentaObservaciones_QNAME = new QName(
			"http://schemas.datacontract.org/2004/07/FEService.Input", "observaciones");
	private final static QName _VentaHoraEmision_QNAME = new QName(
			"http://schemas.datacontract.org/2004/07/FEService.Input", "horaEmision");
	private final static QName _VentaCliente_QNAME = new QName(
			"http://schemas.datacontract.org/2004/07/FEService.Input", "cliente");
	private final static QName _VentaGlosaRetencion_QNAME = new QName(
			"http://schemas.datacontract.org/2004/07/FEService.Input", "glosaRetencion");
	private final static QName _VentaCentroCosto_QNAME = new QName(
			"http://schemas.datacontract.org/2004/07/FEService.Input", "centroCosto");
	private final static QName _VentaDocumentoReferencia_QNAME = new QName(
			"http://schemas.datacontract.org/2004/07/FEService.Input", "documentoReferencia");
	private final static QName _VentaDireccionEmbarque_QNAME = new QName(
			"http://schemas.datacontract.org/2004/07/FEService.Input", "direccionEmbarque");
	private final static QName _VentaProducto_QNAME = new QName(
			"http://schemas.datacontract.org/2004/07/FEService.Input", "producto");
	private final static QName _VentaTipoComprobanteID_QNAME = new QName(
			"http://schemas.datacontract.org/2004/07/FEService.Input", "tipoComprobanteID");
	private final static QName _VentaInformacionAdicional_QNAME = new QName(
			"http://schemas.datacontract.org/2004/07/FEService.Input", "informacionAdicional");
	private final static QName _DetalleVentaCodigoProducto_QNAME = new QName(
			"http://schemas.datacontract.org/2004/07/FEService.Input", "codigoProducto");
	private final static QName _DetalleVentaCodigoTipoPrecio_QNAME = new QName(
			"http://schemas.datacontract.org/2004/07/FEService.Input", "codigoTipoPrecio");
	private final static QName _DetalleVentaUnidadMedida_QNAME = new QName(
			"http://schemas.datacontract.org/2004/07/FEService.Input", "unidadMedida");
	private final static QName _DetalleVentaCodigoAfectacionIgv_QNAME = new QName(
			"http://schemas.datacontract.org/2004/07/FEService.Input", "codigoAfectacionIgv");
	private final static QName _DetalleVentaDescripcion_QNAME = new QName(
			"http://schemas.datacontract.org/2004/07/FEService.Input", "descripcion");
	private final static QName _BuscarDetalleComprobanteResponseBuscarDetalleComprobanteResult_QNAME = new QName(
			"http://tempuri.org/", "buscarDetalleComprobanteResult");
	private final static QName _SetConsultarCDRDocumentCDR_QNAME = new QName("http://tempuri.org/", "documentCDR");
	private final static QName _SetConsultarCDRToken_QNAME = new QName("http://tempuri.org/", "token");
	private final static QName _SetBajasFechaEmisionComprobantes_QNAME = new QName("http://tempuri.org/",
			"fechaEmisionComprobantes");
	private final static QName _SetBajasRucEmpresa_QNAME = new QName("http://tempuri.org/", "rucEmpresa");
	private final static QName _SetNotaVentaVenta_QNAME = new QName("http://tempuri.org/", "venta");
	private final static QName _SetNotaVentaNota_QNAME = new QName("http://tempuri.org/", "nota");
	private final static QName _GetRepresentacionImpresaStrRucEmpresa_QNAME = new QName("http://tempuri.org/",
			"strRucEmpresa");
	private final static QName _GetRepresentacionImpresaTipoComprobante_QNAME = new QName("http://tempuri.org/",
			"tipoComprobante");
	private final static QName _GetRepresentacionImpresaCorrelativo_QNAME = new QName("http://tempuri.org/",
			"correlativo");
	private final static QName _GetRepresentacionImpresaSerie_QNAME = new QName("http://tempuri.org/", "serie");
	private final static QName _SetNotaNotaCreditoDebito_QNAME = new QName("http://tempuri.org/", "notaCreditoDebito");
	private final static QName _SetBajaDocumentoDocumentoBaja_QNAME = new QName("http://tempuri.org/", "documentoBaja");
	private final static QName _InformacionAdicionalPropiedadesAdicionales_QNAME = new QName(
			"http://schemas.datacontract.org/2004/07/FEService.Input", "PropiedadesAdicionales");
	private final static QName _InformacionAdicionalTotalesMonedaAdicional_QNAME = new QName(
			"http://schemas.datacontract.org/2004/07/FEService.Input", "TotalesMonedaAdicional");
	private final static QName _ResultMessage_QNAME = new QName(
			"http://schemas.datacontract.org/2004/07/FEService.Util", "Message");
	private final static QName _ResultBarcodeQR_QNAME = new QName(
			"http://schemas.datacontract.org/2004/07/FEService.Util", "barcode_QR");
	private final static QName _ResultPdf_QNAME = new QName("http://schemas.datacontract.org/2004/07/FEService.Util",
			"pdf");
	private final static QName _ResultRetencionesInp_QNAME = new QName(
			"http://schemas.datacontract.org/2004/07/FEService.Util", "retencionesInp");
	private final static QName _ResultListDetalleVenta_QNAME = new QName(
			"http://schemas.datacontract.org/2004/07/FEService.Util", "listDetalleVenta");
	private final static QName _ResultBarcode_QNAME = new QName(
			"http://schemas.datacontract.org/2004/07/FEService.Util", "barcode");
	private final static QName _SetNotaResponseSetNotaResult_QNAME = new QName("http://tempuri.org/", "setNotaResult");
	private final static QName _SetBajaDocumentoResponseSetBajaDocumentoResult_QNAME = new QName("http://tempuri.org/",
			"setBajaDocumentoResult");
	private final static QName _DocumentCDRTicket_QNAME = new QName(
			"http://schemas.datacontract.org/2004/07/FEService.Input", "ticket");
	private final static QName _SetNotaVentaResponseSetNotaVentaResult_QNAME = new QName("http://tempuri.org/",
			"setNotaVentaResult");
	private final static QName _SetVentaResponseSetVentaResult_QNAME = new QName("http://tempuri.org/",
			"setVentaResult");
	private final static QName _GetRepresentacionImpresaResponseGetRepresentacionImpresaResult_QNAME = new QName(
			"http://tempuri.org/", "getRepresentacionImpresaResult");
	private final static QName _DocumentoReferenciaTipoComprobante_QNAME = new QName(
			"http://schemas.datacontract.org/2004/07/FEService.Input", "TipoComprobante");
	private final static QName _DocumentoReferenciaNumeroDocumento_QNAME = new QName(
			"http://schemas.datacontract.org/2004/07/FEService.Input", "NumeroDocumento");
	private final static QName _DocumentoReferenciaFechaDocumento_QNAME = new QName(
			"http://schemas.datacontract.org/2004/07/FEService.Input", "FechaDocumento");
	private final static QName _ClienteNombres_QNAME = new QName(
			"http://schemas.datacontract.org/2004/07/FEService.Input", "nombres");
	private final static QName _ClienteDireccion_QNAME = new QName(
			"http://schemas.datacontract.org/2004/07/FEService.Input", "direccion");
	private final static QName _ClienteNumeroDocumento_QNAME = new QName(
			"http://schemas.datacontract.org/2004/07/FEService.Input", "numeroDocumento");
	private final static QName _SetResumenesResponseSetResumenesResult_QNAME = new QName("http://tempuri.org/",
			"setResumenesResult");
	private final static QName _NotaDescripcionSustento_QNAME = new QName(
			"http://schemas.datacontract.org/2004/07/FEService.Input", "descripcionSustento");
	private final static QName _NotaCodigoTipoNota_QNAME = new QName(
			"http://schemas.datacontract.org/2004/07/FEService.Input", "codigoTipoNota");
	private final static QName _NotaDescripcionTipoNota_QNAME = new QName(
			"http://schemas.datacontract.org/2004/07/FEService.Input", "descripcionTipoNota");
	private final static QName _SetUpdateStatusCDRResponseSetUpdateStatusCDRResult_QNAME = new QName(
			"http://tempuri.org/", "setUpdateStatusCDRResult");

	/**
	 * Create a new ObjectFactory that can be used to create new instances of
	 * schema derived classes for package: com.cystesoft.vyrbus.service.fe
	 * 
	 */
	public ObjectFactory() {
	}

	/**
	 * Create an instance of {@link SetConsultarCDRResponse }
	 * 
	 */
	public SetConsultarCDRResponse createSetConsultarCDRResponse() {
		return new SetConsultarCDRResponse();
	}

	/**
	 * Create an instance of {@link SetBajasResponse }
	 * 
	 */
	public SetBajasResponse createSetBajasResponse() {
		return new SetBajasResponse();
	}

	/**
	 * Create an instance of {@link InformacionAdicionalPropiedadAdicional }
	 * 
	 */
	public InformacionAdicionalPropiedadAdicional createInformacionAdicionalPropiedadAdicional() {
		return new InformacionAdicionalPropiedadAdicional();
	}

	/**
	 * Create an instance of
	 * {@link ArrayOfInformacionAdicionalTotalMonedaAdicional }
	 * 
	 */
	public ArrayOfInformacionAdicionalTotalMonedaAdicional createArrayOfInformacionAdicionalTotalMonedaAdicional() {
		return new ArrayOfInformacionAdicionalTotalMonedaAdicional();
	}

	/**
	 * Create an instance of {@link RetencionInp }
	 * 
	 */
	public RetencionInp createRetencionInp() {
		return new RetencionInp();
	}

	/**
	 * Create an instance of {@link DocumentoBaja }
	 * 
	 */
	public DocumentoBaja createDocumentoBaja() {
		return new DocumentoBaja();
	}

	/**
	 * Create an instance of {@link RetencionDetalleInp }
	 * 
	 */
	public RetencionDetalleInp createRetencionDetalleInp() {
		return new RetencionDetalleInp();
	}

	/**
	 * Create an instance of {@link SetEnviarComprobantesSunatResponse }
	 * 
	 */
	public SetEnviarComprobantesSunatResponse createSetEnviarComprobantesSunatResponse() {
		return new SetEnviarComprobantesSunatResponse();
	}

	/**
	 * Create an instance of {@link Venta }
	 * 
	 */
	public Venta createVenta() {
		return new Venta();
	}

	/**
	 * Create an instance of {@link InformacionAdicionalTotalMonedaAdicional }
	 * 
	 */
	public InformacionAdicionalTotalMonedaAdicional createInformacionAdicionalTotalMonedaAdicional() {
		return new InformacionAdicionalTotalMonedaAdicional();
	}

	/**
	 * Create an instance of {@link DetalleVenta }
	 * 
	 */
	public DetalleVenta createDetalleVenta() {
		return new DetalleVenta();
	}

	/**
	 * Create an instance of {@link BuscarDetalleComprobanteResponse }
	 * 
	 */
	public BuscarDetalleComprobanteResponse createBuscarDetalleComprobanteResponse() {
		return new BuscarDetalleComprobanteResponse();
	}

	/**
	 * Create an instance of {@link SetConsultarCDR }
	 * 
	 */
	public SetConsultarCDR createSetConsultarCDR() {
		return new SetConsultarCDR();
	}

	/**
	 * Create an instance of {@link SetUpdatePdfResponse }
	 * 
	 */
	public SetUpdatePdfResponse createSetUpdatePdfResponse() {
		return new SetUpdatePdfResponse();
	}

	/**
	 * Create an instance of {@link SetBajas }
	 * 
	 */
	public SetBajas createSetBajas() {
		return new SetBajas();
	}

	/**
	 * Create an instance of {@link SetNotaVenta }
	 * 
	 */
	public SetNotaVenta createSetNotaVenta() {
		return new SetNotaVenta();
	}

	/**
	 * Create an instance of {@link GetRepresentacionImpresa }
	 * 
	 */
	public GetRepresentacionImpresa createGetRepresentacionImpresa() {
		return new GetRepresentacionImpresa();
	}

	/**
	 * Create an instance of {@link SetNota }
	 * 
	 */
	public SetNota createSetNota() {
		return new SetNota();
	}

	/**
	 * Create an instance of {@link ArrayOfDetalleVenta }
	 * 
	 */
	public ArrayOfDetalleVenta createArrayOfDetalleVenta() {
		return new ArrayOfDetalleVenta();
	}

	/**
	 * Create an instance of {@link SetBajaDocumento }
	 * 
	 */
	public SetBajaDocumento createSetBajaDocumento() {
		return new SetBajaDocumento();
	}

	/**
	 * Create an instance of {@link InformacionAdicional }
	 * 
	 */
	public InformacionAdicional createInformacionAdicional() {
		return new InformacionAdicional();
	}

	/**
	 * Create an instance of {@link Result }
	 * 
	 */
	public Result createResult() {
		return new Result();
	}

	/**
	 * Create an instance of {@link SetNotaResponse }
	 * 
	 */
	public SetNotaResponse createSetNotaResponse() {
		return new SetNotaResponse();
	}

	/**
	 * Create an instance of {@link SetBajaDocumentoResponse }
	 * 
	 */
	public SetBajaDocumentoResponse createSetBajaDocumentoResponse() {
		return new SetBajaDocumentoResponse();
	}

	/**
	 * Create an instance of {@link SetResumenes }
	 * 
	 */
	public SetResumenes createSetResumenes() {
		return new SetResumenes();
	}

	/**
	 * Create an instance of {@link DocumentCDR }
	 * 
	 */
	public DocumentCDR createDocumentCDR() {
		return new DocumentCDR();
	}

	/**
	 * Create an instance of {@link SetUpdateStatusCDR }
	 * 
	 */
	public SetUpdateStatusCDR createSetUpdateStatusCDR() {
		return new SetUpdateStatusCDR();
	}

	/**
	 * Create an instance of {@link SetEnviarComprobantesSunat }
	 * 
	 */
	public SetEnviarComprobantesSunat createSetEnviarComprobantesSunat() {
		return new SetEnviarComprobantesSunat();
	}

	/**
	 * Create an instance of {@link SetNotaVentaResponse }
	 * 
	 */
	public SetNotaVentaResponse createSetNotaVentaResponse() {
		return new SetNotaVentaResponse();
	}

	/**
	 * Create an instance of {@link SetVenta }
	 * 
	 */
	public SetVenta createSetVenta() {
		return new SetVenta();
	}

	/**
	 * Create an instance of {@link BuscarDetalleComprobante }
	 * 
	 */
	public BuscarDetalleComprobante createBuscarDetalleComprobante() {
		return new BuscarDetalleComprobante();
	}

	/**
	 * Create an instance of {@link SetVentaResponse }
	 * 
	 */
	public SetVentaResponse createSetVentaResponse() {
		return new SetVentaResponse();
	}

	/**
	 * Create an instance of {@link GetRepresentacionImpresaResponse }
	 * 
	 */
	public GetRepresentacionImpresaResponse createGetRepresentacionImpresaResponse() {
		return new GetRepresentacionImpresaResponse();
	}

	/**
	 * Create an instance of {@link DocumentoReferencia }
	 * 
	 */
	public DocumentoReferencia createDocumentoReferencia() {
		return new DocumentoReferencia();
	}

	/**
	 * Create an instance of {@link ArrayOfRetencionDetalleInp }
	 * 
	 */
	public ArrayOfRetencionDetalleInp createArrayOfRetencionDetalleInp() {
		return new ArrayOfRetencionDetalleInp();
	}

	/**
	 * Create an instance of {@link Cliente }
	 * 
	 */
	public Cliente createCliente() {
		return new Cliente();
	}

	/**
	 * Create an instance of {@link Nota }
	 * 
	 */
	public Nota createNota() {
		return new Nota();
	}

	/**
	 * Create an instance of {@link SetUpdatePdf }
	 * 
	 */
	public SetUpdatePdf createSetUpdatePdf() {
		return new SetUpdatePdf();
	}

	/**
	 * Create an instance of {@link SetResumenesResponse }
	 * 
	 */
	public SetResumenesResponse createSetResumenesResponse() {
		return new SetResumenesResponse();
	}

	/**
	 * Create an instance of {@link SetUpdateStatusCDRResponse }
	 * 
	 */
	public SetUpdateStatusCDRResponse createSetUpdateStatusCDRResponse() {
		return new SetUpdateStatusCDRResponse();
	}

	/**
	 * Create an instance of
	 * {@link ArrayOfInformacionAdicionalPropiedadAdicional }
	 * 
	 */
	public ArrayOfInformacionAdicionalPropiedadAdicional createArrayOfInformacionAdicionalPropiedadAdicional() {
		return new ArrayOfInformacionAdicionalPropiedadAdicional();
	}

	/**
	 * Create an instance of {@link ArrayOfRetencionInp }
	 * 
	 */
	public ArrayOfRetencionInp createArrayOfRetencionInp() {
		return new ArrayOfRetencionInp();
	}

	/**
	 * Create an instance of {@link JAXBElement }{@code <}{@link BigInteger
	 * }{@code >}}
	 * 
	 */
	@XmlElementDecl(namespace = "http://schemas.microsoft.com/2003/10/Serialization/", name = "unsignedLong")
	public JAXBElement<BigInteger> createUnsignedLong(BigInteger value) {
		return new JAXBElement<BigInteger>(_UnsignedLong_QNAME, BigInteger.class, null, value);
	}

	/**
	 * Create an instance of {@link JAXBElement }{@code <}{@link Short
	 * }{@code >}}
	 * 
	 */
	@XmlElementDecl(namespace = "http://schemas.microsoft.com/2003/10/Serialization/", name = "unsignedByte")
	public JAXBElement<Short> createUnsignedByte(Short value) {
		return new JAXBElement<Short>(_UnsignedByte_QNAME, Short.class, null, value);
	}

	/**
	 * Create an instance of {@link JAXBElement }{@code <}{@link Integer
	 * }{@code >}}
	 * 
	 */
	@XmlElementDecl(namespace = "http://schemas.microsoft.com/2003/10/Serialization/", name = "unsignedShort")
	public JAXBElement<Integer> createUnsignedShort(Integer value) {
		return new JAXBElement<Integer>(_UnsignedShort_QNAME, Integer.class, null, value);
	}

	/**
	 * Create an instance of {@link JAXBElement
	 * }{@code <}{@link DocumentoReferencia }{@code >}}
	 * 
	 */
	@XmlElementDecl(namespace = "http://schemas.datacontract.org/2004/07/FEService.Input", name = "DocumentoReferencia")
	public JAXBElement<DocumentoReferencia> createDocumentoReferencia(DocumentoReferencia value) {
		return new JAXBElement<DocumentoReferencia>(_DocumentoReferencia_QNAME, DocumentoReferencia.class, null, value);
	}

	/**
	 * Create an instance of {@link JAXBElement }{@code <}{@link Duration
	 * }{@code >}}
	 * 
	 */
	@XmlElementDecl(namespace = "http://schemas.microsoft.com/2003/10/Serialization/", name = "duration")
	public JAXBElement<Duration> createDuration(Duration value) {
		return new JAXBElement<Duration>(_Duration_QNAME, Duration.class, null, value);
	}

	/**
	 * Create an instance of {@link JAXBElement }{@code <}{@link DetalleVenta
	 * }{@code >}}
	 * 
	 */
	@XmlElementDecl(namespace = "http://schemas.datacontract.org/2004/07/FEService.Input", name = "DetalleVenta")
	public JAXBElement<DetalleVenta> createDetalleVenta(DetalleVenta value) {
		return new JAXBElement<DetalleVenta>(_DetalleVenta_QNAME, DetalleVenta.class, null, value);
	}

	/**
	 * Create an instance of {@link JAXBElement
	 * }{@code <}{@link RetencionDetalleInp }{@code >}}
	 * 
	 */
	@XmlElementDecl(namespace = "http://schemas.datacontract.org/2004/07/FEService.Input", name = "RetencionDetalleInp")
	public JAXBElement<RetencionDetalleInp> createRetencionDetalleInp(RetencionDetalleInp value) {
		return new JAXBElement<RetencionDetalleInp>(_RetencionDetalleInp_QNAME, RetencionDetalleInp.class, null, value);
	}

	/**
	 * Create an instance of {@link JAXBElement }{@code <}{@link Long
	 * }{@code >}}
	 * 
	 */
	@XmlElementDecl(namespace = "http://schemas.microsoft.com/2003/10/Serialization/", name = "long")
	public JAXBElement<Long> createLong(Long value) {
		return new JAXBElement<Long>(_Long_QNAME, Long.class, null, value);
	}

	/**
	 * Create an instance of {@link JAXBElement }{@code <}{@link Nota
	 * }{@code >}}
	 * 
	 */
	@XmlElementDecl(namespace = "http://schemas.datacontract.org/2004/07/FEService.Input", name = "Nota")
	public JAXBElement<Nota> createNota(Nota value) {
		return new JAXBElement<Nota>(_Nota_QNAME, Nota.class, null, value);
	}

	/**
	 * Create an instance of {@link JAXBElement }{@code <}{@link Float
	 * }{@code >}}
	 * 
	 */
	@XmlElementDecl(namespace = "http://schemas.microsoft.com/2003/10/Serialization/", name = "float")
	public JAXBElement<Float> createFloat(Float value) {
		return new JAXBElement<Float>(_Float_QNAME, Float.class, null, value);
	}

	/**
	 * Create an instance of {@link JAXBElement }{@code <}{@link DocumentCDR
	 * }{@code >}}
	 * 
	 */
	@XmlElementDecl(namespace = "http://schemas.datacontract.org/2004/07/FEService.Input", name = "DocumentCDR")
	public JAXBElement<DocumentCDR> createDocumentCDR(DocumentCDR value) {
		return new JAXBElement<DocumentCDR>(_DocumentCDR_QNAME, DocumentCDR.class, null, value);
	}

	/**
	 * Create an instance of {@link JAXBElement
	 * }{@code <}{@link InformacionAdicional }{@code >}}
	 * 
	 */
	@XmlElementDecl(namespace = "http://schemas.datacontract.org/2004/07/FEService.Input", name = "InformacionAdicional")
	public JAXBElement<InformacionAdicional> createInformacionAdicional(InformacionAdicional value) {
		return new JAXBElement<InformacionAdicional>(_InformacionAdicional_QNAME, InformacionAdicional.class, null,
				value);
	}

	/**
	 * Create an instance of {@link JAXBElement
	 * }{@code <}{@link XMLGregorianCalendar }{@code >}}
	 * 
	 */
	@XmlElementDecl(namespace = "http://schemas.microsoft.com/2003/10/Serialization/", name = "dateTime")
	public JAXBElement<XMLGregorianCalendar> createDateTime(XMLGregorianCalendar value) {
		return new JAXBElement<XMLGregorianCalendar>(_DateTime_QNAME, XMLGregorianCalendar.class, null, value);
	}

	/**
	 * Create an instance of {@link JAXBElement
	 * }{@code <}{@link ArrayOfDetalleVenta }{@code >}}
	 * 
	 */
	@XmlElementDecl(namespace = "http://schemas.datacontract.org/2004/07/FEService.Input", name = "ArrayOfDetalleVenta")
	public JAXBElement<ArrayOfDetalleVenta> createArrayOfDetalleVenta(ArrayOfDetalleVenta value) {
		return new JAXBElement<ArrayOfDetalleVenta>(_ArrayOfDetalleVenta_QNAME, ArrayOfDetalleVenta.class, null, value);
	}

	/**
	 * Create an instance of {@link JAXBElement
	 * }{@code <}{@link InformacionAdicionalTotalMonedaAdicional }{@code >}}
	 * 
	 */
	@XmlElementDecl(namespace = "http://schemas.datacontract.org/2004/07/FEService.Input", name = "InformacionAdicional.TotalMonedaAdicional")
	public JAXBElement<InformacionAdicionalTotalMonedaAdicional> createInformacionAdicionalTotalMonedaAdicional(
			InformacionAdicionalTotalMonedaAdicional value) {
		return new JAXBElement<InformacionAdicionalTotalMonedaAdicional>(
				_InformacionAdicionalTotalMonedaAdicional_QNAME, InformacionAdicionalTotalMonedaAdicional.class, null,
				value);
	}

	/**
	 * Create an instance of {@link JAXBElement }{@code <}{@link Object
	 * }{@code >}}
	 * 
	 */
	@XmlElementDecl(namespace = "http://schemas.microsoft.com/2003/10/Serialization/", name = "anyType")
	public JAXBElement<Object> createAnyType(Object value) {
		return new JAXBElement<Object>(_AnyType_QNAME, Object.class, null, value);
	}

	/**
	 * Create an instance of {@link JAXBElement }{@code <}{@link String
	 * }{@code >}}
	 * 
	 */
	@XmlElementDecl(namespace = "http://schemas.microsoft.com/2003/10/Serialization/", name = "string")
	public JAXBElement<String> createString(String value) {
		return new JAXBElement<String>(_String_QNAME, String.class, null, value);
	}

	/**
	 * Create an instance of {@link JAXBElement
	 * }{@code <}{@link InformacionAdicionalPropiedadAdicional }{@code >}}
	 * 
	 */
	@XmlElementDecl(namespace = "http://schemas.datacontract.org/2004/07/FEService.Input", name = "InformacionAdicional.PropiedadAdicional")
	public JAXBElement<InformacionAdicionalPropiedadAdicional> createInformacionAdicionalPropiedadAdicional(
			InformacionAdicionalPropiedadAdicional value) {
		return new JAXBElement<InformacionAdicionalPropiedadAdicional>(_InformacionAdicionalPropiedadAdicional_QNAME,
				InformacionAdicionalPropiedadAdicional.class, null, value);
	}

	/**
	 * Create an instance of {@link JAXBElement }{@code <}{@link Long
	 * }{@code >}}
	 * 
	 */
	@XmlElementDecl(namespace = "http://schemas.microsoft.com/2003/10/Serialization/", name = "unsignedInt")
	public JAXBElement<Long> createUnsignedInt(Long value) {
		return new JAXBElement<Long>(_UnsignedInt_QNAME, Long.class, null, value);
	}

	/**
	 * Create an instance of {@link JAXBElement }{@code <}{@link Integer
	 * }{@code >}}
	 * 
	 */
	@XmlElementDecl(namespace = "http://schemas.microsoft.com/2003/10/Serialization/", name = "char")
	public JAXBElement<Integer> createChar(Integer value) {
		return new JAXBElement<Integer>(_Char_QNAME, Integer.class, null, value);
	}

	/**
	 * Create an instance of {@link JAXBElement }{@code <}{@link Cliente
	 * }{@code >}}
	 * 
	 */
	@XmlElementDecl(namespace = "http://schemas.datacontract.org/2004/07/FEService.Input", name = "Cliente")
	public JAXBElement<Cliente> createCliente(Cliente value) {
		return new JAXBElement<Cliente>(_Cliente_QNAME, Cliente.class, null, value);
	}

	/**
	 * Create an instance of {@link JAXBElement }{@code <}{@link Short
	 * }{@code >}}
	 * 
	 */
	@XmlElementDecl(namespace = "http://schemas.microsoft.com/2003/10/Serialization/", name = "short")
	public JAXBElement<Short> createShort(Short value) {
		return new JAXBElement<Short>(_Short_QNAME, Short.class, null, value);
	}

	/**
	 * Create an instance of {@link JAXBElement }{@code <}{@link String
	 * }{@code >}}
	 * 
	 */
	@XmlElementDecl(namespace = "http://schemas.microsoft.com/2003/10/Serialization/", name = "guid")
	public JAXBElement<String> createGuid(String value) {
		return new JAXBElement<String>(_Guid_QNAME, String.class, null, value);
	}

	/**
	 * Create an instance of {@link JAXBElement }{@code <}{@link BigDecimal
	 * }{@code >}}
	 * 
	 */
	@XmlElementDecl(namespace = "http://schemas.microsoft.com/2003/10/Serialization/", name = "decimal")
	public JAXBElement<BigDecimal> createDecimal(BigDecimal value) {
		return new JAXBElement<BigDecimal>(_Decimal_QNAME, BigDecimal.class, null, value);
	}

	/**
	 * Create an instance of {@link JAXBElement }{@code <}{@link Boolean
	 * }{@code >}}
	 * 
	 */
	@XmlElementDecl(namespace = "http://schemas.microsoft.com/2003/10/Serialization/", name = "boolean")
	public JAXBElement<Boolean> createBoolean(Boolean value) {
		return new JAXBElement<Boolean>(_Boolean_QNAME, Boolean.class, null, value);
	}

	/**
	 * Create an instance of {@link JAXBElement
	 * }{@code <}{@link ArrayOfRetencionDetalleInp }{@code >}}
	 * 
	 */
	@XmlElementDecl(namespace = "http://schemas.datacontract.org/2004/07/FEService.Input", name = "ArrayOfRetencionDetalleInp")
	public JAXBElement<ArrayOfRetencionDetalleInp> createArrayOfRetencionDetalleInp(ArrayOfRetencionDetalleInp value) {
		return new JAXBElement<ArrayOfRetencionDetalleInp>(_ArrayOfRetencionDetalleInp_QNAME,
				ArrayOfRetencionDetalleInp.class, null, value);
	}

	/**
	 * Create an instance of {@link JAXBElement
	 * }{@code <}{@link ArrayOfInformacionAdicionalTotalMonedaAdicional
	 * }{@code >}}
	 * 
	 */
	@XmlElementDecl(namespace = "http://schemas.datacontract.org/2004/07/FEService.Input", name = "ArrayOfInformacionAdicional.TotalMonedaAdicional")
	public JAXBElement<ArrayOfInformacionAdicionalTotalMonedaAdicional> createArrayOfInformacionAdicionalTotalMonedaAdicional(
			ArrayOfInformacionAdicionalTotalMonedaAdicional value) {
		return new JAXBElement<ArrayOfInformacionAdicionalTotalMonedaAdicional>(
				_ArrayOfInformacionAdicionalTotalMonedaAdicional_QNAME,
				ArrayOfInformacionAdicionalTotalMonedaAdicional.class, null, value);
	}

	/**
	 * Create an instance of {@link JAXBElement }{@code <}{@link RetencionInp
	 * }{@code >}}
	 * 
	 */
	@XmlElementDecl(namespace = "http://schemas.datacontract.org/2004/07/FEService.Input", name = "RetencionInp")
	public JAXBElement<RetencionInp> createRetencionInp(RetencionInp value) {
		return new JAXBElement<RetencionInp>(_RetencionInp_QNAME, RetencionInp.class, null, value);
	}

	/**
	 * Create an instance of {@link JAXBElement
	 * }{@code <}{@link byte[]}{@code >}}
	 * 
	 */
	@XmlElementDecl(namespace = "http://schemas.microsoft.com/2003/10/Serialization/", name = "base64Binary")
	public JAXBElement<byte[]> createBase64Binary(byte[] value) {
		return new JAXBElement<byte[]>(_Base64Binary_QNAME, byte[].class, null, ((byte[]) value));
	}

	/**
	 * Create an instance of {@link JAXBElement }{@code <}{@link Integer
	 * }{@code >}}
	 * 
	 */
	@XmlElementDecl(namespace = "http://schemas.microsoft.com/2003/10/Serialization/", name = "int")
	public JAXBElement<Integer> createInt(Integer value) {
		return new JAXBElement<Integer>(_Int_QNAME, Integer.class, null, value);
	}

	/**
	 * Create an instance of {@link JAXBElement }{@code <}{@link String
	 * }{@code >}}
	 * 
	 */
	@XmlElementDecl(namespace = "http://schemas.microsoft.com/2003/10/Serialization/", name = "anyURI")
	public JAXBElement<String> createAnyURI(String value) {
		return new JAXBElement<String>(_AnyURI_QNAME, String.class, null, value);
	}

	/**
	 * Create an instance of {@link JAXBElement }{@code <}{@link Result
	 * }{@code >}}
	 * 
	 */
	@XmlElementDecl(namespace = "http://schemas.datacontract.org/2004/07/FEService.Util", name = "Result")
	public JAXBElement<Result> createResult(Result value) {
		return new JAXBElement<Result>(_Result_QNAME, Result.class, null, value);
	}

	/**
	 * Create an instance of {@link JAXBElement }{@code <}{@link Venta
	 * }{@code >}}
	 * 
	 */
	@XmlElementDecl(namespace = "http://schemas.datacontract.org/2004/07/FEService.Input", name = "Venta")
	public JAXBElement<Venta> createVenta(Venta value) {
		return new JAXBElement<Venta>(_Venta_QNAME, Venta.class, null, value);
	}

	/**
	 * Create an instance of {@link JAXBElement
	 * }{@code <}{@link ArrayOfInformacionAdicionalPropiedadAdicional
	 * }{@code >}}
	 * 
	 */
	@XmlElementDecl(namespace = "http://schemas.datacontract.org/2004/07/FEService.Input", name = "ArrayOfInformacionAdicional.PropiedadAdicional")
	public JAXBElement<ArrayOfInformacionAdicionalPropiedadAdicional> createArrayOfInformacionAdicionalPropiedadAdicional(
			ArrayOfInformacionAdicionalPropiedadAdicional value) {
		return new JAXBElement<ArrayOfInformacionAdicionalPropiedadAdicional>(
				_ArrayOfInformacionAdicionalPropiedadAdicional_QNAME,
				ArrayOfInformacionAdicionalPropiedadAdicional.class, null, value);
	}

	/**
	 * Create an instance of {@link JAXBElement
	 * }{@code <}{@link ArrayOfRetencionInp }{@code >}}
	 * 
	 */
	@XmlElementDecl(namespace = "http://schemas.datacontract.org/2004/07/FEService.Input", name = "ArrayOfRetencionInp")
	public JAXBElement<ArrayOfRetencionInp> createArrayOfRetencionInp(ArrayOfRetencionInp value) {
		return new JAXBElement<ArrayOfRetencionInp>(_ArrayOfRetencionInp_QNAME, ArrayOfRetencionInp.class, null, value);
	}

	/**
	 * Create an instance of {@link JAXBElement }{@code <}{@link DocumentoBaja
	 * }{@code >}}
	 * 
	 */
	@XmlElementDecl(namespace = "http://schemas.datacontract.org/2004/07/FEService.Input", name = "DocumentoBaja")
	public JAXBElement<DocumentoBaja> createDocumentoBaja(DocumentoBaja value) {
		return new JAXBElement<DocumentoBaja>(_DocumentoBaja_QNAME, DocumentoBaja.class, null, value);
	}

	/**
	 * Create an instance of {@link JAXBElement }{@code <}{@link Byte
	 * }{@code >}}
	 * 
	 */
	@XmlElementDecl(namespace = "http://schemas.microsoft.com/2003/10/Serialization/", name = "byte")
	public JAXBElement<Byte> createByte(Byte value) {
		return new JAXBElement<Byte>(_Byte_QNAME, Byte.class, null, value);
	}

	/**
	 * Create an instance of {@link JAXBElement }{@code <}{@link Double
	 * }{@code >}}
	 * 
	 */
	@XmlElementDecl(namespace = "http://schemas.microsoft.com/2003/10/Serialization/", name = "double")
	public JAXBElement<Double> createDouble(Double value) {
		return new JAXBElement<Double>(_Double_QNAME, Double.class, null, value);
	}

	/**
	 * Create an instance of {@link JAXBElement }{@code <}{@link QName
	 * }{@code >}}
	 * 
	 */
	@XmlElementDecl(namespace = "http://schemas.microsoft.com/2003/10/Serialization/", name = "QName")
	public JAXBElement<QName> createQName(QName value) {
		return new JAXBElement<QName>(_QName_QNAME, QName.class, null, value);
	}

	/**
	 * Create an instance of {@link JAXBElement }{@code <}{@link Result
	 * }{@code >}}
	 * 
	 */
	@XmlElementDecl(namespace = "http://tempuri.org/", name = "setConsultarCDRResult", scope = SetConsultarCDRResponse.class)
	public JAXBElement<Result> createSetConsultarCDRResponseSetConsultarCDRResult(Result value) {
		return new JAXBElement<Result>(_SetConsultarCDRResponseSetConsultarCDRResult_QNAME, Result.class,
				SetConsultarCDRResponse.class, value);
	}

	/**
	 * Create an instance of {@link JAXBElement }{@code <}{@link Result
	 * }{@code >}}
	 * 
	 */
	@XmlElementDecl(namespace = "http://tempuri.org/", name = "setBajasResult", scope = SetBajasResponse.class)
	public JAXBElement<Result> createSetBajasResponseSetBajasResult(Result value) {
		return new JAXBElement<Result>(_SetBajasResponseSetBajasResult_QNAME, Result.class, SetBajasResponse.class,
				value);
	}

	/**
	 * Create an instance of {@link JAXBElement }{@code <}{@link String
	 * }{@code >}}
	 * 
	 */
	@XmlElementDecl(namespace = "http://schemas.datacontract.org/2004/07/FEService.Input", name = "nombre", scope = InformacionAdicionalPropiedadAdicional.class)
	public JAXBElement<String> createInformacionAdicionalPropiedadAdicionalNombre(String value) {
		return new JAXBElement<String>(_InformacionAdicionalPropiedadAdicionalNombre_QNAME, String.class,
				InformacionAdicionalPropiedadAdicional.class, value);
	}

	/**
	 * Create an instance of {@link JAXBElement }{@code <}{@link String
	 * }{@code >}}
	 * 
	 */
	@XmlElementDecl(namespace = "http://schemas.datacontract.org/2004/07/FEService.Input", name = "value", scope = InformacionAdicionalPropiedadAdicional.class)
	public JAXBElement<String> createInformacionAdicionalPropiedadAdicionalValue(String value) {
		return new JAXBElement<String>(_InformacionAdicionalPropiedadAdicionalValue_QNAME, String.class,
				InformacionAdicionalPropiedadAdicional.class, value);
	}

	/**
	 * Create an instance of {@link JAXBElement }{@code <}{@link String
	 * }{@code >}}
	 * 
	 */
	@XmlElementDecl(namespace = "http://schemas.datacontract.org/2004/07/FEService.Input", name = "codigo", scope = InformacionAdicionalPropiedadAdicional.class)
	public JAXBElement<String> createInformacionAdicionalPropiedadAdicionalCodigo(String value) {
		return new JAXBElement<String>(_InformacionAdicionalPropiedadAdicionalCodigo_QNAME, String.class,
				InformacionAdicionalPropiedadAdicional.class, value);
	}

	/**
	 * Create an instance of {@link JAXBElement }{@code <}{@link String
	 * }{@code >}}
	 * 
	 */
	@XmlElementDecl(namespace = "http://schemas.datacontract.org/2004/07/FEService.Input", name = "Serie", scope = RetencionInp.class)
	public JAXBElement<String> createRetencionInpSerie(String value) {
		return new JAXBElement<String>(_RetencionInpSerie_QNAME, String.class, RetencionInp.class, value);
	}

	/**
	 * Create an instance of {@link JAXBElement }{@code <}{@link String
	 * }{@code >}}
	 * 
	 */
	@XmlElementDecl(namespace = "http://schemas.datacontract.org/2004/07/FEService.Input", name = "tasaRetencion", scope = RetencionInp.class)
	public JAXBElement<String> createRetencionInpTasaRetencion(String value) {
		return new JAXBElement<String>(_RetencionInpTasaRetencion_QNAME, String.class, RetencionInp.class, value);
	}

	/**
	 * Create an instance of {@link JAXBElement }{@code <}{@link String
	 * }{@code >}}
	 * 
	 */
	@XmlElementDecl(namespace = "http://schemas.datacontract.org/2004/07/FEService.Input", name = "RazonSocial", scope = RetencionInp.class)
	public JAXBElement<String> createRetencionInpRazonSocial(String value) {
		return new JAXBElement<String>(_RetencionInpRazonSocial_QNAME, String.class, RetencionInp.class, value);
	}

	/**
	 * Create an instance of {@link JAXBElement }{@code <}{@link String
	 * }{@code >}}
	 * 
	 */
	@XmlElementDecl(namespace = "http://schemas.datacontract.org/2004/07/FEService.Input", name = "NumeroCorrelativo", scope = RetencionInp.class)
	public JAXBElement<String> createRetencionInpNumeroCorrelativo(String value) {
		return new JAXBElement<String>(_RetencionInpNumeroCorrelativo_QNAME, String.class, RetencionInp.class, value);
	}

	/**
	 * Create an instance of {@link JAXBElement }{@code <}{@link String
	 * }{@code >}}
	 * 
	 */
	@XmlElementDecl(namespace = "http://schemas.datacontract.org/2004/07/FEService.Input", name = "id", scope = RetencionInp.class)
	public JAXBElement<String> createRetencionInpId(String value) {
		return new JAXBElement<String>(_RetencionInpId_QNAME, String.class, RetencionInp.class, value);
	}

	/**
	 * Create an instance of {@link JAXBElement
	 * }{@code <}{@link ArrayOfRetencionDetalleInp }{@code >}}
	 * 
	 */
	@XmlElementDecl(namespace = "http://schemas.datacontract.org/2004/07/FEService.Input", name = "detalleRetencion", scope = RetencionInp.class)
	public JAXBElement<ArrayOfRetencionDetalleInp> createRetencionInpDetalleRetencion(
			ArrayOfRetencionDetalleInp value) {
		return new JAXBElement<ArrayOfRetencionDetalleInp>(_RetencionInpDetalleRetencion_QNAME,
				ArrayOfRetencionDetalleInp.class, RetencionInp.class, value);
	}

	/**
	 * Create an instance of {@link JAXBElement }{@code <}{@link String
	 * }{@code >}}
	 * 
	 */
	@XmlElementDecl(namespace = "http://schemas.datacontract.org/2004/07/FEService.Input", name = "registroRetencion", scope = RetencionInp.class)
	public JAXBElement<String> createRetencionInpRegistroRetencion(String value) {
		return new JAXBElement<String>(_RetencionInpRegistroRetencion_QNAME, String.class, RetencionInp.class, value);
	}

	/**
	 * Create an instance of {@link JAXBElement }{@code <}{@link String
	 * }{@code >}}
	 * 
	 */
	@XmlElementDecl(namespace = "http://schemas.datacontract.org/2004/07/FEService.Input", name = "correo", scope = RetencionInp.class)
	public JAXBElement<String> createRetencionInpCorreo(String value) {
		return new JAXBElement<String>(_RetencionInpCorreo_QNAME, String.class, RetencionInp.class, value);
	}

	/**
	 * Create an instance of {@link JAXBElement }{@code <}{@link String
	 * }{@code >}}
	 * 
	 */
	@XmlElementDecl(namespace = "http://schemas.datacontract.org/2004/07/FEService.Input", name = "FechaEmision", scope = RetencionInp.class)
	public JAXBElement<String> createRetencionInpFechaEmision(String value) {
		return new JAXBElement<String>(_RetencionInpFechaEmision_QNAME, String.class, RetencionInp.class, value);
	}

	/**
	 * Create an instance of {@link JAXBElement }{@code <}{@link String
	 * }{@code >}}
	 * 
	 */
	@XmlElementDecl(namespace = "http://schemas.datacontract.org/2004/07/FEService.Input", name = "Ruc", scope = RetencionInp.class)
	public JAXBElement<String> createRetencionInpRuc(String value) {
		return new JAXBElement<String>(_RetencionInpRuc_QNAME, String.class, RetencionInp.class, value);
	}

	/**
	 * Create an instance of {@link JAXBElement }{@code <}{@link String
	 * }{@code >}}
	 * 
	 */
	@XmlElementDecl(namespace = "http://schemas.datacontract.org/2004/07/FEService.Input", name = "ImporteTotalRetenidoEnLetras", scope = RetencionInp.class)
	public JAXBElement<String> createRetencionInpImporteTotalRetenidoEnLetras(String value) {
		return new JAXBElement<String>(_RetencionInpImporteTotalRetenidoEnLetras_QNAME, String.class,
				RetencionInp.class, value);
	}

	/**
	 * Create an instance of {@link JAXBElement }{@code <}{@link String
	 * }{@code >}}
	 * 
	 */
	@XmlElementDecl(namespace = "http://schemas.datacontract.org/2004/07/FEService.Input", name = "descripcionMotivo", scope = DocumentoBaja.class)
	public JAXBElement<String> createDocumentoBajaDescripcionMotivo(String value) {
		return new JAXBElement<String>(_DocumentoBajaDescripcionMotivo_QNAME, String.class, DocumentoBaja.class, value);
	}

	/**
	 * Create an instance of {@link JAXBElement }{@code <}{@link String
	 * }{@code >}}
	 * 
	 */
	@XmlElementDecl(namespace = "http://schemas.datacontract.org/2004/07/FEService.Input", name = "usuarioInsercion", scope = DocumentoBaja.class)
	public JAXBElement<String> createDocumentoBajaUsuarioInsercion(String value) {
		return new JAXBElement<String>(_DocumentoBajaUsuarioInsercion_QNAME, String.class, DocumentoBaja.class, value);
	}

	/**
	 * Create an instance of {@link JAXBElement }{@code <}{@link String
	 * }{@code >}}
	 * 
	 */
	@XmlElementDecl(namespace = "http://schemas.datacontract.org/2004/07/FEService.Input", name = "fechaEmision", scope = DocumentoBaja.class)
	public JAXBElement<String> createDocumentoBajaFechaEmision(String value) {
		return new JAXBElement<String>(_DocumentoBajaFechaEmision_QNAME, String.class, DocumentoBaja.class, value);
	}

	/**
	 * Create an instance of {@link JAXBElement }{@code <}{@link String
	 * }{@code >}}
	 * 
	 */
	@XmlElementDecl(namespace = "http://schemas.datacontract.org/2004/07/FEService.Input", name = "usuarioModificacion", scope = DocumentoBaja.class)
	public JAXBElement<String> createDocumentoBajaUsuarioModificacion(String value) {
		return new JAXBElement<String>(_DocumentoBajaUsuarioModificacion_QNAME, String.class, DocumentoBaja.class,
				value);
	}

	/**
	 * Create an instance of {@link JAXBElement }{@code <}{@link String
	 * }{@code >}}
	 * 
	 */
	@XmlElementDecl(namespace = "http://schemas.datacontract.org/2004/07/FEService.Input", name = "numeroCorrelativo", scope = DocumentoBaja.class)
	public JAXBElement<String> createDocumentoBajaNumeroCorrelativo(String value) {
		return new JAXBElement<String>(_DocumentoBajaNumeroCorrelativo_QNAME, String.class, DocumentoBaja.class, value);
	}

	/**
	 * Create an instance of {@link JAXBElement }{@code <}{@link String
	 * }{@code >}}
	 * 
	 */
	@XmlElementDecl(namespace = "http://schemas.datacontract.org/2004/07/FEService.Input", name = "rucEmpresa", scope = DocumentoBaja.class)
	public JAXBElement<String> createDocumentoBajaRucEmpresa(String value) {
		return new JAXBElement<String>(_DocumentoBajaRucEmpresa_QNAME, String.class, DocumentoBaja.class, value);
	}

	/**
	 * Create an instance of {@link JAXBElement }{@code <}{@link String
	 * }{@code >}}
	 * 
	 */
	@XmlElementDecl(namespace = "http://schemas.datacontract.org/2004/07/FEService.Input", name = "numeroSerie", scope = DocumentoBaja.class)
	public JAXBElement<String> createDocumentoBajaNumeroSerie(String value) {
		return new JAXBElement<String>(_DocumentoBajaNumeroSerie_QNAME, String.class, DocumentoBaja.class, value);
	}

	/**
	 * Create an instance of {@link JAXBElement }{@code <}{@link String
	 * }{@code >}}
	 * 
	 */
	@XmlElementDecl(namespace = "http://schemas.datacontract.org/2004/07/FEService.Input", name = "tipoDocumentoID", scope = DocumentoBaja.class)
	public JAXBElement<String> createDocumentoBajaTipoDocumentoID(String value) {
		return new JAXBElement<String>(_DocumentoBajaTipoDocumentoID_QNAME, String.class, DocumentoBaja.class, value);
	}

	/**
	 * Create an instance of {@link JAXBElement }{@code <}{@link String
	 * }{@code >}}
	 * 
	 */
	@XmlElementDecl(namespace = "http://schemas.datacontract.org/2004/07/FEService.Input", name = "fechaPago", scope = RetencionDetalleInp.class)
	public JAXBElement<String> createRetencionDetalleInpFechaPago(String value) {
		return new JAXBElement<String>(_RetencionDetalleInpFechaPago_QNAME, String.class, RetencionDetalleInp.class,
				value);
	}

	/**
	 * Create an instance of {@link JAXBElement }{@code <}{@link String
	 * }{@code >}}
	 * 
	 */
	@XmlElementDecl(namespace = "http://schemas.datacontract.org/2004/07/FEService.Input", name = "monedaOriginal", scope = RetencionDetalleInp.class)
	public JAXBElement<String> createRetencionDetalleInpMonedaOriginal(String value) {
		return new JAXBElement<String>(_RetencionDetalleInpMonedaOriginal_QNAME, String.class,
				RetencionDetalleInp.class, value);
	}

	/**
	 * Create an instance of {@link JAXBElement }{@code <}{@link String
	 * }{@code >}}
	 * 
	 */
	@XmlElementDecl(namespace = "http://schemas.datacontract.org/2004/07/FEService.Input", name = "fechaEmisonDoctRelacionado", scope = RetencionDetalleInp.class)
	public JAXBElement<String> createRetencionDetalleInpFechaEmisonDoctRelacionado(String value) {
		return new JAXBElement<String>(_RetencionDetalleInpFechaEmisonDoctRelacionado_QNAME, String.class,
				RetencionDetalleInp.class, value);
	}

	/**
	 * Create an instance of {@link JAXBElement }{@code <}{@link String
	 * }{@code >}}
	 * 
	 */
	@XmlElementDecl(namespace = "http://schemas.datacontract.org/2004/07/FEService.Input", name = "monedaPagado", scope = RetencionDetalleInp.class)
	public JAXBElement<String> createRetencionDetalleInpMonedaPagado(String value) {
		return new JAXBElement<String>(_RetencionDetalleInpMonedaPagado_QNAME, String.class, RetencionDetalleInp.class,
				value);
	}

	/**
	 * Create an instance of {@link JAXBElement }{@code <}{@link String
	 * }{@code >}}
	 * 
	 */
	@XmlElementDecl(namespace = "http://schemas.datacontract.org/2004/07/FEService.Input", name = "tipoDoctRelacionado", scope = RetencionDetalleInp.class)
	public JAXBElement<String> createRetencionDetalleInpTipoDoctRelacionado(String value) {
		return new JAXBElement<String>(_RetencionDetalleInpTipoDoctRelacionado_QNAME, String.class,
				RetencionDetalleInp.class, value);
	}

	/**
	 * Create an instance of {@link JAXBElement }{@code <}{@link String
	 * }{@code >}}
	 * 
	 */
	@XmlElementDecl(namespace = "http://schemas.datacontract.org/2004/07/FEService.Input", name = "numDoctRelacionado", scope = RetencionDetalleInp.class)
	public JAXBElement<String> createRetencionDetalleInpNumDoctRelacionado(String value) {
		return new JAXBElement<String>(_RetencionDetalleInpNumDoctRelacionado_QNAME, String.class,
				RetencionDetalleInp.class, value);
	}

	/**
	 * Create an instance of {@link JAXBElement }{@code <}{@link String
	 * }{@code >}}
	 * 
	 */
	@XmlElementDecl(namespace = "http://schemas.datacontract.org/2004/07/FEService.Input", name = "numeroPago", scope = RetencionDetalleInp.class)
	public JAXBElement<String> createRetencionDetalleInpNumeroPago(String value) {
		return new JAXBElement<String>(_RetencionDetalleInpNumeroPago_QNAME, String.class, RetencionDetalleInp.class,
				value);
	}

	/**
	 * Create an instance of {@link JAXBElement }{@code <}{@link String
	 * }{@code >}}
	 * 
	 */
	@XmlElementDecl(namespace = "http://schemas.datacontract.org/2004/07/FEService.Input", name = "monedaDoctRelacionado", scope = RetencionDetalleInp.class)
	public JAXBElement<String> createRetencionDetalleInpMonedaDoctRelacionado(String value) {
		return new JAXBElement<String>(_RetencionDetalleInpMonedaDoctRelacionado_QNAME, String.class,
				RetencionDetalleInp.class, value);
	}

	/**
	 * Create an instance of {@link JAXBElement }{@code <}{@link Result
	 * }{@code >}}
	 * 
	 */
	@XmlElementDecl(namespace = "http://tempuri.org/", name = "setEnviarComprobantesSunatResult", scope = SetEnviarComprobantesSunatResponse.class)
	public JAXBElement<Result> createSetEnviarComprobantesSunatResponseSetEnviarComprobantesSunatResult(Result value) {
		return new JAXBElement<Result>(_SetEnviarComprobantesSunatResponseSetEnviarComprobantesSunatResult_QNAME,
				Result.class, SetEnviarComprobantesSunatResponse.class, value);
	}

	/**
	 * Create an instance of {@link JAXBElement }{@code <}{@link String
	 * }{@code >}}
	 * 
	 */
	@XmlElementDecl(namespace = "http://schemas.datacontract.org/2004/07/FEService.Input", name = "nombre", scope = InformacionAdicionalTotalMonedaAdicional.class)
	public JAXBElement<String> createInformacionAdicionalTotalMonedaAdicionalNombre(String value) {
		return new JAXBElement<String>(_InformacionAdicionalPropiedadAdicionalNombre_QNAME, String.class,
				InformacionAdicionalTotalMonedaAdicional.class, value);
	}

	/**
	 * Create an instance of {@link JAXBElement }{@code <}{@link String
	 * }{@code >}}
	 * 
	 */
	@XmlElementDecl(namespace = "http://schemas.datacontract.org/2004/07/FEService.Input", name = "valor", scope = InformacionAdicionalTotalMonedaAdicional.class)
	public JAXBElement<String> createInformacionAdicionalTotalMonedaAdicionalValor(String value) {
		return new JAXBElement<String>(_InformacionAdicionalTotalMonedaAdicionalValor_QNAME, String.class,
				InformacionAdicionalTotalMonedaAdicional.class, value);
	}

	/**
	 * Create an instance of {@link JAXBElement }{@code <}{@link String
	 * }{@code >}}
	 * 
	 */
	@XmlElementDecl(namespace = "http://schemas.datacontract.org/2004/07/FEService.Input", name = "codigo", scope = InformacionAdicionalTotalMonedaAdicional.class)
	public JAXBElement<String> createInformacionAdicionalTotalMonedaAdicionalCodigo(String value) {
		return new JAXBElement<String>(_InformacionAdicionalPropiedadAdicionalCodigo_QNAME, String.class,
				InformacionAdicionalTotalMonedaAdicional.class, value);
	}

	/**
	 * Create an instance of {@link JAXBElement
	 * }{@code <}{@link ArrayOfDetalleVenta }{@code >}}
	 * 
	 */
	@XmlElementDecl(namespace = "http://schemas.datacontract.org/2004/07/FEService.Input", name = "listDetalleVenta", scope = Venta.class)
	public JAXBElement<ArrayOfDetalleVenta> createVentaListDetalleVenta(ArrayOfDetalleVenta value) {
		return new JAXBElement<ArrayOfDetalleVenta>(_VentaListDetalleVenta_QNAME, ArrayOfDetalleVenta.class,
				Venta.class, value);
	}

	/**
	 * Create an instance of {@link JAXBElement }{@code <}{@link String
	 * }{@code >}}
	 * 
	 */
	@XmlElementDecl(namespace = "http://schemas.datacontract.org/2004/07/FEService.Input", name = "numeroPrefactura", scope = Venta.class)
	public JAXBElement<String> createVentaNumeroPrefactura(String value) {
		return new JAXBElement<String>(_VentaNumeroPrefactura_QNAME, String.class, Venta.class, value);
	}

	/**
	 * Create an instance of {@link JAXBElement }{@code <}{@link String
	 * }{@code >}}
	 * 
	 */
	@XmlElementDecl(namespace = "http://schemas.datacontract.org/2004/07/FEService.Input", name = "observaciones", scope = Venta.class)
	public JAXBElement<String> createVentaObservaciones(String value) {
		return new JAXBElement<String>(_VentaObservaciones_QNAME, String.class, Venta.class, value);
	}

	/**
	 * Create an instance of {@link JAXBElement }{@code <}{@link String
	 * }{@code >}}
	 * 
	 */
	@XmlElementDecl(namespace = "http://schemas.datacontract.org/2004/07/FEService.Input", name = "usuarioModificacion", scope = Venta.class)
	public JAXBElement<String> createVentaUsuarioModificacion(String value) {
		return new JAXBElement<String>(_DocumentoBajaUsuarioModificacion_QNAME, String.class, Venta.class, value);
	}

	/**
	 * Create an instance of {@link JAXBElement }{@code <}{@link String
	 * }{@code >}}
	 * 
	 */
	@XmlElementDecl(namespace = "http://schemas.datacontract.org/2004/07/FEService.Input", name = "numeroCorrelativo", scope = Venta.class)
	public JAXBElement<String> createVentaNumeroCorrelativo(String value) {
		return new JAXBElement<String>(_DocumentoBajaNumeroCorrelativo_QNAME, String.class, Venta.class, value);
	}

	/**
	 * Create an instance of {@link JAXBElement }{@code <}{@link String
	 * }{@code >}}
	 * 
	 */
	@XmlElementDecl(namespace = "http://schemas.datacontract.org/2004/07/FEService.Input", name = "rucEmpresa", scope = Venta.class)
	public JAXBElement<String> createVentaRucEmpresa(String value) {
		return new JAXBElement<String>(_DocumentoBajaRucEmpresa_QNAME, String.class, Venta.class, value);
	}

	/**
	 * Create an instance of {@link JAXBElement }{@code <}{@link String
	 * }{@code >}}
	 * 
	 */
	@XmlElementDecl(namespace = "http://schemas.datacontract.org/2004/07/FEService.Input", name = "horaEmision", scope = Venta.class)
	public JAXBElement<String> createVentaHoraEmision(String value) {
		return new JAXBElement<String>(_VentaHoraEmision_QNAME, String.class, Venta.class, value);
	}

	/**
	 * Create an instance of {@link JAXBElement }{@code <}{@link Cliente
	 * }{@code >}}
	 * 
	 */
	@XmlElementDecl(namespace = "http://schemas.datacontract.org/2004/07/FEService.Input", name = "cliente", scope = Venta.class)
	public JAXBElement<Cliente> createVentaCliente(Cliente value) {
		return new JAXBElement<Cliente>(_VentaCliente_QNAME, Cliente.class, Venta.class, value);
	}

	/**
	 * Create an instance of {@link JAXBElement }{@code <}{@link String
	 * }{@code >}}
	 * 
	 */
	@XmlElementDecl(namespace = "http://schemas.datacontract.org/2004/07/FEService.Input", name = "glosaRetencion", scope = Venta.class)
	public JAXBElement<String> createVentaGlosaRetencion(String value) {
		return new JAXBElement<String>(_VentaGlosaRetencion_QNAME, String.class, Venta.class, value);
	}

	/**
	 * Create an instance of {@link JAXBElement }{@code <}{@link String
	 * }{@code >}}
	 * 
	 */
	@XmlElementDecl(namespace = "http://schemas.datacontract.org/2004/07/FEService.Input", name = "centroCosto", scope = Venta.class)
	public JAXBElement<String> createVentaCentroCosto(String value) {
		return new JAXBElement<String>(_VentaCentroCosto_QNAME, String.class, Venta.class, value);
	}

	/**
	 * Create an instance of {@link JAXBElement
	 * }{@code <}{@link DocumentoReferencia }{@code >}}
	 * 
	 */
	@XmlElementDecl(namespace = "http://schemas.datacontract.org/2004/07/FEService.Input", name = "documentoReferencia", scope = Venta.class)
	public JAXBElement<DocumentoReferencia> createVentaDocumentoReferencia(DocumentoReferencia value) {
		return new JAXBElement<DocumentoReferencia>(_VentaDocumentoReferencia_QNAME, DocumentoReferencia.class,
				Venta.class, value);
	}

	/**
	 * Create an instance of {@link JAXBElement }{@code <}{@link String
	 * }{@code >}}
	 * 
	 */
	@XmlElementDecl(namespace = "http://schemas.datacontract.org/2004/07/FEService.Input", name = "usuarioInsercion", scope = Venta.class)
	public JAXBElement<String> createVentaUsuarioInsercion(String value) {
		return new JAXBElement<String>(_DocumentoBajaUsuarioInsercion_QNAME, String.class, Venta.class, value);
	}

	/**
	 * Create an instance of {@link JAXBElement }{@code <}{@link String
	 * }{@code >}}
	 * 
	 */
	@XmlElementDecl(namespace = "http://schemas.datacontract.org/2004/07/FEService.Input", name = "direccionEmbarque", scope = Venta.class)
	public JAXBElement<String> createVentaDireccionEmbarque(String value) {
		return new JAXBElement<String>(_VentaDireccionEmbarque_QNAME, String.class, Venta.class, value);
	}

	/**
	 * Create an instance of {@link JAXBElement }{@code <}{@link String
	 * }{@code >}}
	 * 
	 */
	@XmlElementDecl(namespace = "http://schemas.datacontract.org/2004/07/FEService.Input", name = "producto", scope = Venta.class)
	public JAXBElement<String> createVentaProducto(String value) {
		return new JAXBElement<String>(_VentaProducto_QNAME, String.class, Venta.class, value);
	}

	/**
	 * Create an instance of {@link JAXBElement }{@code <}{@link String
	 * }{@code >}}
	 * 
	 */
	@XmlElementDecl(namespace = "http://schemas.datacontract.org/2004/07/FEService.Input", name = "fechaEmision", scope = Venta.class)
	public JAXBElement<String> createVentaFechaEmision(String value) {
		return new JAXBElement<String>(_DocumentoBajaFechaEmision_QNAME, String.class, Venta.class, value);
	}

	/**
	 * Create an instance of {@link JAXBElement }{@code <}{@link String
	 * }{@code >}}
	 * 
	 */
	@XmlElementDecl(namespace = "http://schemas.datacontract.org/2004/07/FEService.Input", name = "tipoComprobanteID", scope = Venta.class)
	public JAXBElement<String> createVentaTipoComprobanteID(String value) {
		return new JAXBElement<String>(_VentaTipoComprobanteID_QNAME, String.class, Venta.class, value);
	}

	/**
	 * Create an instance of {@link JAXBElement
	 * }{@code <}{@link InformacionAdicional }{@code >}}
	 * 
	 */
	@XmlElementDecl(namespace = "http://schemas.datacontract.org/2004/07/FEService.Input", name = "informacionAdicional", scope = Venta.class)
	public JAXBElement<InformacionAdicional> createVentaInformacionAdicional(InformacionAdicional value) {
		return new JAXBElement<InformacionAdicional>(_VentaInformacionAdicional_QNAME, InformacionAdicional.class,
				Venta.class, value);
	}

	/**
	 * Create an instance of {@link JAXBElement }{@code <}{@link String
	 * }{@code >}}
	 * 
	 */
	@XmlElementDecl(namespace = "http://schemas.datacontract.org/2004/07/FEService.Input", name = "numeroSerie", scope = Venta.class)
	public JAXBElement<String> createVentaNumeroSerie(String value) {
		return new JAXBElement<String>(_DocumentoBajaNumeroSerie_QNAME, String.class, Venta.class, value);
	}

	/**
	 * Create an instance of {@link JAXBElement }{@code <}{@link String
	 * }{@code >}}
	 * 
	 */
	@XmlElementDecl(namespace = "http://schemas.datacontract.org/2004/07/FEService.Input", name = "codigoProducto", scope = DetalleVenta.class)
	public JAXBElement<String> createDetalleVentaCodigoProducto(String value) {
		return new JAXBElement<String>(_DetalleVentaCodigoProducto_QNAME, String.class, DetalleVenta.class, value);
	}

	/**
	 * Create an instance of {@link JAXBElement }{@code <}{@link String
	 * }{@code >}}
	 * 
	 */
	@XmlElementDecl(namespace = "http://schemas.datacontract.org/2004/07/FEService.Input", name = "codigoTipoPrecio", scope = DetalleVenta.class)
	public JAXBElement<String> createDetalleVentaCodigoTipoPrecio(String value) {
		return new JAXBElement<String>(_DetalleVentaCodigoTipoPrecio_QNAME, String.class, DetalleVenta.class, value);
	}

	/**
	 * Create an instance of {@link JAXBElement }{@code <}{@link String
	 * }{@code >}}
	 * 
	 */
	@XmlElementDecl(namespace = "http://schemas.datacontract.org/2004/07/FEService.Input", name = "unidadMedida", scope = DetalleVenta.class)
	public JAXBElement<String> createDetalleVentaUnidadMedida(String value) {
		return new JAXBElement<String>(_DetalleVentaUnidadMedida_QNAME, String.class, DetalleVenta.class, value);
	}

	/**
	 * Create an instance of {@link JAXBElement }{@code <}{@link String
	 * }{@code >}}
	 * 
	 */
	@XmlElementDecl(namespace = "http://schemas.datacontract.org/2004/07/FEService.Input", name = "codigoAfectacionIgv", scope = DetalleVenta.class)
	public JAXBElement<String> createDetalleVentaCodigoAfectacionIgv(String value) {
		return new JAXBElement<String>(_DetalleVentaCodigoAfectacionIgv_QNAME, String.class, DetalleVenta.class, value);
	}

	/**
	 * Create an instance of {@link JAXBElement }{@code <}{@link String
	 * }{@code >}}
	 * 
	 */
	@XmlElementDecl(namespace = "http://schemas.datacontract.org/2004/07/FEService.Input", name = "descripcion", scope = DetalleVenta.class)
	public JAXBElement<String> createDetalleVentaDescripcion(String value) {
		return new JAXBElement<String>(_DetalleVentaDescripcion_QNAME, String.class, DetalleVenta.class, value);
	}

	/**
	 * Create an instance of {@link JAXBElement }{@code <}{@link Result
	 * }{@code >}}
	 * 
	 */
	@XmlElementDecl(namespace = "http://tempuri.org/", name = "buscarDetalleComprobanteResult", scope = BuscarDetalleComprobanteResponse.class)
	public JAXBElement<Result> createBuscarDetalleComprobanteResponseBuscarDetalleComprobanteResult(Result value) {
		return new JAXBElement<Result>(_BuscarDetalleComprobanteResponseBuscarDetalleComprobanteResult_QNAME,
				Result.class, BuscarDetalleComprobanteResponse.class, value);
	}

	/**
	 * Create an instance of {@link JAXBElement }{@code <}{@link DocumentCDR
	 * }{@code >}}
	 * 
	 */
	@XmlElementDecl(namespace = "http://tempuri.org/", name = "documentCDR", scope = SetConsultarCDR.class)
	public JAXBElement<DocumentCDR> createSetConsultarCDRDocumentCDR(DocumentCDR value) {
		return new JAXBElement<DocumentCDR>(_SetConsultarCDRDocumentCDR_QNAME, DocumentCDR.class, SetConsultarCDR.class,
				value);
	}

	/**
	 * Create an instance of {@link JAXBElement }{@code <}{@link String
	 * }{@code >}}
	 * 
	 */
	@XmlElementDecl(namespace = "http://tempuri.org/", name = "token", scope = SetConsultarCDR.class)
	public JAXBElement<String> createSetConsultarCDRToken(String value) {
		return new JAXBElement<String>(_SetConsultarCDRToken_QNAME, String.class, SetConsultarCDR.class, value);
	}

	/**
	 * Create an instance of {@link JAXBElement }{@code <}{@link String
	 * }{@code >}}
	 * 
	 */
	@XmlElementDecl(namespace = "http://tempuri.org/", name = "fechaEmisionComprobantes", scope = SetBajas.class)
	public JAXBElement<String> createSetBajasFechaEmisionComprobantes(String value) {
		return new JAXBElement<String>(_SetBajasFechaEmisionComprobantes_QNAME, String.class, SetBajas.class, value);
	}

	/**
	 * Create an instance of {@link JAXBElement }{@code <}{@link String
	 * }{@code >}}
	 * 
	 */
	@XmlElementDecl(namespace = "http://tempuri.org/", name = "rucEmpresa", scope = SetBajas.class)
	public JAXBElement<String> createSetBajasRucEmpresa(String value) {
		return new JAXBElement<String>(_SetBajasRucEmpresa_QNAME, String.class, SetBajas.class, value);
	}

	/**
	 * Create an instance of {@link JAXBElement }{@code <}{@link String
	 * }{@code >}}
	 * 
	 */
	@XmlElementDecl(namespace = "http://tempuri.org/", name = "token", scope = SetBajas.class)
	public JAXBElement<String> createSetBajasToken(String value) {
		return new JAXBElement<String>(_SetConsultarCDRToken_QNAME, String.class, SetBajas.class, value);
	}

	/**
	 * Create an instance of {@link JAXBElement }{@code <}{@link Venta
	 * }{@code >}}
	 * 
	 */
	@XmlElementDecl(namespace = "http://tempuri.org/", name = "venta", scope = SetNotaVenta.class)
	public JAXBElement<Venta> createSetNotaVentaVenta(Venta value) {
		return new JAXBElement<Venta>(_SetNotaVentaVenta_QNAME, Venta.class, SetNotaVenta.class, value);
	}

	/**
	 * Create an instance of {@link JAXBElement }{@code <}{@link String
	 * }{@code >}}
	 * 
	 */
	@XmlElementDecl(namespace = "http://tempuri.org/", name = "token", scope = SetNotaVenta.class)
	public JAXBElement<String> createSetNotaVentaToken(String value) {
		return new JAXBElement<String>(_SetConsultarCDRToken_QNAME, String.class, SetNotaVenta.class, value);
	}

	/**
	 * Create an instance of {@link JAXBElement }{@code <}{@link Nota
	 * }{@code >}}
	 * 
	 */
	@XmlElementDecl(namespace = "http://tempuri.org/", name = "nota", scope = SetNotaVenta.class)
	public JAXBElement<Nota> createSetNotaVentaNota(Nota value) {
		return new JAXBElement<Nota>(_SetNotaVentaNota_QNAME, Nota.class, SetNotaVenta.class, value);
	}

	/**
	 * Create an instance of {@link JAXBElement }{@code <}{@link String
	 * }{@code >}}
	 * 
	 */
	@XmlElementDecl(namespace = "http://tempuri.org/", name = "strRucEmpresa", scope = GetRepresentacionImpresa.class)
	public JAXBElement<String> createGetRepresentacionImpresaStrRucEmpresa(String value) {
		return new JAXBElement<String>(_GetRepresentacionImpresaStrRucEmpresa_QNAME, String.class,
				GetRepresentacionImpresa.class, value);
	}

	/**
	 * Create an instance of {@link JAXBElement }{@code <}{@link String
	 * }{@code >}}
	 * 
	 */
	@XmlElementDecl(namespace = "http://tempuri.org/", name = "tipoComprobante", scope = GetRepresentacionImpresa.class)
	public JAXBElement<String> createGetRepresentacionImpresaTipoComprobante(String value) {
		return new JAXBElement<String>(_GetRepresentacionImpresaTipoComprobante_QNAME, String.class,
				GetRepresentacionImpresa.class, value);
	}

	/**
	 * Create an instance of {@link JAXBElement }{@code <}{@link String
	 * }{@code >}}
	 * 
	 */
	@XmlElementDecl(namespace = "http://tempuri.org/", name = "correlativo", scope = GetRepresentacionImpresa.class)
	public JAXBElement<String> createGetRepresentacionImpresaCorrelativo(String value) {
		return new JAXBElement<String>(_GetRepresentacionImpresaCorrelativo_QNAME, String.class,
				GetRepresentacionImpresa.class, value);
	}

	/**
	 * Create an instance of {@link JAXBElement }{@code <}{@link String
	 * }{@code >}}
	 * 
	 */
	@XmlElementDecl(namespace = "http://tempuri.org/", name = "serie", scope = GetRepresentacionImpresa.class)
	public JAXBElement<String> createGetRepresentacionImpresaSerie(String value) {
		return new JAXBElement<String>(_GetRepresentacionImpresaSerie_QNAME, String.class,
				GetRepresentacionImpresa.class, value);
	}

	/**
	 * Create an instance of {@link JAXBElement }{@code <}{@link String
	 * }{@code >}}
	 * 
	 */
	@XmlElementDecl(namespace = "http://tempuri.org/", name = "token", scope = GetRepresentacionImpresa.class)
	public JAXBElement<String> createGetRepresentacionImpresaToken(String value) {
		return new JAXBElement<String>(_SetConsultarCDRToken_QNAME, String.class, GetRepresentacionImpresa.class,
				value);
	}

	/**
	 * Create an instance of {@link JAXBElement }{@code <}{@link String
	 * }{@code >}}
	 * 
	 */
	@XmlElementDecl(namespace = "http://tempuri.org/", name = "token", scope = SetNota.class)
	public JAXBElement<String> createSetNotaToken(String value) {
		return new JAXBElement<String>(_SetConsultarCDRToken_QNAME, String.class, SetNota.class, value);
	}

	/**
	 * Create an instance of {@link JAXBElement }{@code <}{@link Nota
	 * }{@code >}}
	 * 
	 */
	@XmlElementDecl(namespace = "http://tempuri.org/", name = "notaCreditoDebito", scope = SetNota.class)
	public JAXBElement<Nota> createSetNotaNotaCreditoDebito(Nota value) {
		return new JAXBElement<Nota>(_SetNotaNotaCreditoDebito_QNAME, Nota.class, SetNota.class, value);
	}

	/**
	 * Create an instance of {@link JAXBElement }{@code <}{@link DocumentoBaja
	 * }{@code >}}
	 * 
	 */
	@XmlElementDecl(namespace = "http://tempuri.org/", name = "documentoBaja", scope = SetBajaDocumento.class)
	public JAXBElement<DocumentoBaja> createSetBajaDocumentoDocumentoBaja(DocumentoBaja value) {
		return new JAXBElement<DocumentoBaja>(_SetBajaDocumentoDocumentoBaja_QNAME, DocumentoBaja.class,
				SetBajaDocumento.class, value);
	}

	/**
	 * Create an instance of {@link JAXBElement }{@code <}{@link String
	 * }{@code >}}
	 * 
	 */
	@XmlElementDecl(namespace = "http://tempuri.org/", name = "token", scope = SetBajaDocumento.class)
	public JAXBElement<String> createSetBajaDocumentoToken(String value) {
		return new JAXBElement<String>(_SetConsultarCDRToken_QNAME, String.class, SetBajaDocumento.class, value);
	}

	/**
	 * Create an instance of {@link JAXBElement
	 * }{@code <}{@link ArrayOfInformacionAdicionalPropiedadAdicional
	 * }{@code >}}
	 * 
	 */
	@XmlElementDecl(namespace = "http://schemas.datacontract.org/2004/07/FEService.Input", name = "PropiedadesAdicionales", scope = InformacionAdicional.class)
	public JAXBElement<ArrayOfInformacionAdicionalPropiedadAdicional> createInformacionAdicionalPropiedadesAdicionales(
			ArrayOfInformacionAdicionalPropiedadAdicional value) {
		return new JAXBElement<ArrayOfInformacionAdicionalPropiedadAdicional>(
				_InformacionAdicionalPropiedadesAdicionales_QNAME, ArrayOfInformacionAdicionalPropiedadAdicional.class,
				InformacionAdicional.class, value);
	}

	/**
	 * Create an instance of {@link JAXBElement
	 * }{@code <}{@link ArrayOfInformacionAdicionalTotalMonedaAdicional
	 * }{@code >}}
	 * 
	 */
	@XmlElementDecl(namespace = "http://schemas.datacontract.org/2004/07/FEService.Input", name = "TotalesMonedaAdicional", scope = InformacionAdicional.class)
	public JAXBElement<ArrayOfInformacionAdicionalTotalMonedaAdicional> createInformacionAdicionalTotalesMonedaAdicional(
			ArrayOfInformacionAdicionalTotalMonedaAdicional value) {
		return new JAXBElement<ArrayOfInformacionAdicionalTotalMonedaAdicional>(
				_InformacionAdicionalTotalesMonedaAdicional_QNAME,
				ArrayOfInformacionAdicionalTotalMonedaAdicional.class, InformacionAdicional.class, value);
	}

	/**
	 * Create an instance of {@link JAXBElement }{@code <}{@link String
	 * }{@code >}}
	 * 
	 */
	@XmlElementDecl(namespace = "http://schemas.datacontract.org/2004/07/FEService.Util", name = "Message", scope = Result.class)
	public JAXBElement<String> createResultMessage(String value) {
		return new JAXBElement<String>(_ResultMessage_QNAME, String.class, Result.class, value);
	}

	/**
	 * Create an instance of {@link JAXBElement
	 * }{@code <}{@link byte[]}{@code >}}
	 * 
	 */
	@XmlElementDecl(namespace = "http://schemas.datacontract.org/2004/07/FEService.Util", name = "barcode_QR", scope = Result.class)
	public JAXBElement<byte[]> createResultBarcodeQR(byte[] value) {
		return new JAXBElement<byte[]>(_ResultBarcodeQR_QNAME, byte[].class, Result.class, ((byte[]) value));
	}

	/**
	 * Create an instance of {@link JAXBElement
	 * }{@code <}{@link byte[]}{@code >}}
	 * 
	 */
	@XmlElementDecl(namespace = "http://schemas.datacontract.org/2004/07/FEService.Util", name = "pdf", scope = Result.class)
	public JAXBElement<byte[]> createResultPdf(byte[] value) {
		return new JAXBElement<byte[]>(_ResultPdf_QNAME, byte[].class, Result.class, ((byte[]) value));
	}

	/**
	 * Create an instance of {@link JAXBElement
	 * }{@code <}{@link ArrayOfRetencionInp }{@code >}}
	 * 
	 */
	@XmlElementDecl(namespace = "http://schemas.datacontract.org/2004/07/FEService.Util", name = "retencionesInp", scope = Result.class)
	public JAXBElement<ArrayOfRetencionInp> createResultRetencionesInp(ArrayOfRetencionInp value) {
		return new JAXBElement<ArrayOfRetencionInp>(_ResultRetencionesInp_QNAME, ArrayOfRetencionInp.class,
				Result.class, value);
	}

	/**
	 * Create an instance of {@link JAXBElement
	 * }{@code <}{@link ArrayOfDetalleVenta }{@code >}}
	 * 
	 */
	@XmlElementDecl(namespace = "http://schemas.datacontract.org/2004/07/FEService.Util", name = "listDetalleVenta", scope = Result.class)
	public JAXBElement<ArrayOfDetalleVenta> createResultListDetalleVenta(ArrayOfDetalleVenta value) {
		return new JAXBElement<ArrayOfDetalleVenta>(_ResultListDetalleVenta_QNAME, ArrayOfDetalleVenta.class,
				Result.class, value);
	}

	/**
	 * Create an instance of {@link JAXBElement
	 * }{@code <}{@link byte[]}{@code >}}
	 * 
	 */
	@XmlElementDecl(namespace = "http://schemas.datacontract.org/2004/07/FEService.Util", name = "barcode", scope = Result.class)
	public JAXBElement<byte[]> createResultBarcode(byte[] value) {
		return new JAXBElement<byte[]>(_ResultBarcode_QNAME, byte[].class, Result.class, ((byte[]) value));
	}

	/**
	 * Create an instance of {@link JAXBElement }{@code <}{@link Result
	 * }{@code >}}
	 * 
	 */
	@XmlElementDecl(namespace = "http://tempuri.org/", name = "setNotaResult", scope = SetNotaResponse.class)
	public JAXBElement<Result> createSetNotaResponseSetNotaResult(Result value) {
		return new JAXBElement<Result>(_SetNotaResponseSetNotaResult_QNAME, Result.class, SetNotaResponse.class, value);
	}

	/**
	 * Create an instance of {@link JAXBElement }{@code <}{@link Result
	 * }{@code >}}
	 * 
	 */
	@XmlElementDecl(namespace = "http://tempuri.org/", name = "setBajaDocumentoResult", scope = SetBajaDocumentoResponse.class)
	public JAXBElement<Result> createSetBajaDocumentoResponseSetBajaDocumentoResult(Result value) {
		return new JAXBElement<Result>(_SetBajaDocumentoResponseSetBajaDocumentoResult_QNAME, Result.class,
				SetBajaDocumentoResponse.class, value);
	}

	/**
	 * Create an instance of {@link JAXBElement }{@code <}{@link String
	 * }{@code >}}
	 * 
	 */
	@XmlElementDecl(namespace = "http://tempuri.org/", name = "fechaEmisionComprobantes", scope = SetResumenes.class)
	public JAXBElement<String> createSetResumenesFechaEmisionComprobantes(String value) {
		return new JAXBElement<String>(_SetBajasFechaEmisionComprobantes_QNAME, String.class, SetResumenes.class,
				value);
	}

	/**
	 * Create an instance of {@link JAXBElement }{@code <}{@link String
	 * }{@code >}}
	 * 
	 */
	@XmlElementDecl(namespace = "http://tempuri.org/", name = "rucEmpresa", scope = SetResumenes.class)
	public JAXBElement<String> createSetResumenesRucEmpresa(String value) {
		return new JAXBElement<String>(_SetBajasRucEmpresa_QNAME, String.class, SetResumenes.class, value);
	}

	/**
	 * Create an instance of {@link JAXBElement }{@code <}{@link String
	 * }{@code >}}
	 * 
	 */
	@XmlElementDecl(namespace = "http://tempuri.org/", name = "token", scope = SetResumenes.class)
	public JAXBElement<String> createSetResumenesToken(String value) {
		return new JAXBElement<String>(_SetConsultarCDRToken_QNAME, String.class, SetResumenes.class, value);
	}

	/**
	 * Create an instance of {@link JAXBElement }{@code <}{@link String
	 * }{@code >}}
	 * 
	 */
	@XmlElementDecl(namespace = "http://schemas.datacontract.org/2004/07/FEService.Input", name = "id", scope = DocumentCDR.class)
	public JAXBElement<String> createDocumentCDRId(String value) {
		return new JAXBElement<String>(_RetencionInpId_QNAME, String.class, DocumentCDR.class, value);
	}

	/**
	 * Create an instance of {@link JAXBElement }{@code <}{@link String
	 * }{@code >}}
	 * 
	 */
	@XmlElementDecl(namespace = "http://schemas.datacontract.org/2004/07/FEService.Input", name = "rucEmpresa", scope = DocumentCDR.class)
	public JAXBElement<String> createDocumentCDRRucEmpresa(String value) {
		return new JAXBElement<String>(_DocumentoBajaRucEmpresa_QNAME, String.class, DocumentCDR.class, value);
	}

	/**
	 * Create an instance of {@link JAXBElement }{@code <}{@link String
	 * }{@code >}}
	 * 
	 */
	@XmlElementDecl(namespace = "http://schemas.datacontract.org/2004/07/FEService.Input", name = "ticket", scope = DocumentCDR.class)
	public JAXBElement<String> createDocumentCDRTicket(String value) {
		return new JAXBElement<String>(_DocumentCDRTicket_QNAME, String.class, DocumentCDR.class, value);
	}

	/**
	 * Create an instance of {@link JAXBElement }{@code <}{@link String
	 * }{@code >}}
	 * 
	 */
	@XmlElementDecl(namespace = "http://tempuri.org/", name = "rucEmpresa", scope = SetUpdateStatusCDR.class)
	public JAXBElement<String> createSetUpdateStatusCDRRucEmpresa(String value) {
		return new JAXBElement<String>(_SetBajasRucEmpresa_QNAME, String.class, SetUpdateStatusCDR.class, value);
	}

	/**
	 * Create an instance of {@link JAXBElement }{@code <}{@link String
	 * }{@code >}}
	 * 
	 */
	@XmlElementDecl(namespace = "http://tempuri.org/", name = "token", scope = SetUpdateStatusCDR.class)
	public JAXBElement<String> createSetUpdateStatusCDRToken(String value) {
		return new JAXBElement<String>(_SetConsultarCDRToken_QNAME, String.class, SetUpdateStatusCDR.class, value);
	}

	/**
	 * Create an instance of {@link JAXBElement }{@code <}{@link String
	 * }{@code >}}
	 * 
	 */
	@XmlElementDecl(namespace = "http://tempuri.org/", name = "rucEmpresa", scope = SetEnviarComprobantesSunat.class)
	public JAXBElement<String> createSetEnviarComprobantesSunatRucEmpresa(String value) {
		return new JAXBElement<String>(_SetBajasRucEmpresa_QNAME, String.class, SetEnviarComprobantesSunat.class,
				value);
	}

	/**
	 * Create an instance of {@link JAXBElement }{@code <}{@link String
	 * }{@code >}}
	 * 
	 */
	@XmlElementDecl(namespace = "http://tempuri.org/", name = "token", scope = SetEnviarComprobantesSunat.class)
	public JAXBElement<String> createSetEnviarComprobantesSunatToken(String value) {
		return new JAXBElement<String>(_SetConsultarCDRToken_QNAME, String.class, SetEnviarComprobantesSunat.class,
				value);
	}

	/**
	 * Create an instance of {@link JAXBElement }{@code <}{@link Result
	 * }{@code >}}
	 * 
	 */
	@XmlElementDecl(namespace = "http://tempuri.org/", name = "setNotaVentaResult", scope = SetNotaVentaResponse.class)
	public JAXBElement<Result> createSetNotaVentaResponseSetNotaVentaResult(Result value) {
		return new JAXBElement<Result>(_SetNotaVentaResponseSetNotaVentaResult_QNAME, Result.class,
				SetNotaVentaResponse.class, value);
	}

	/**
	 * Create an instance of {@link JAXBElement }{@code <}{@link Venta
	 * }{@code >}}
	 * 
	 */
	@XmlElementDecl(namespace = "http://tempuri.org/", name = "venta", scope = SetVenta.class)
	public JAXBElement<Venta> createSetVentaVenta(Venta value) {
		return new JAXBElement<Venta>(_SetNotaVentaVenta_QNAME, Venta.class, SetVenta.class, value);
	}

	/**
	 * Create an instance of {@link JAXBElement }{@code <}{@link String
	 * }{@code >}}
	 * 
	 */
	@XmlElementDecl(namespace = "http://tempuri.org/", name = "token", scope = SetVenta.class)
	public JAXBElement<String> createSetVentaToken(String value) {
		return new JAXBElement<String>(_SetConsultarCDRToken_QNAME, String.class, SetVenta.class, value);
	}

	/**
	 * Create an instance of {@link JAXBElement }{@code <}{@link String
	 * }{@code >}}
	 * 
	 */
	@XmlElementDecl(namespace = "http://tempuri.org/", name = "rucEmpresa", scope = BuscarDetalleComprobante.class)
	public JAXBElement<String> createBuscarDetalleComprobanteRucEmpresa(String value) {
		return new JAXBElement<String>(_SetBajasRucEmpresa_QNAME, String.class, BuscarDetalleComprobante.class, value);
	}

	/**
	 * Create an instance of {@link JAXBElement }{@code <}{@link String
	 * }{@code >}}
	 * 
	 */
	@XmlElementDecl(namespace = "http://tempuri.org/", name = "tipoComprobante", scope = BuscarDetalleComprobante.class)
	public JAXBElement<String> createBuscarDetalleComprobanteTipoComprobante(String value) {
		return new JAXBElement<String>(_GetRepresentacionImpresaTipoComprobante_QNAME, String.class,
				BuscarDetalleComprobante.class, value);
	}

	/**
	 * Create an instance of {@link JAXBElement }{@code <}{@link String
	 * }{@code >}}
	 * 
	 */
	@XmlElementDecl(namespace = "http://tempuri.org/", name = "correlativo", scope = BuscarDetalleComprobante.class)
	public JAXBElement<String> createBuscarDetalleComprobanteCorrelativo(String value) {
		return new JAXBElement<String>(_GetRepresentacionImpresaCorrelativo_QNAME, String.class,
				BuscarDetalleComprobante.class, value);
	}

	/**
	 * Create an instance of {@link JAXBElement }{@code <}{@link String
	 * }{@code >}}
	 * 
	 */
	@XmlElementDecl(namespace = "http://tempuri.org/", name = "serie", scope = BuscarDetalleComprobante.class)
	public JAXBElement<String> createBuscarDetalleComprobanteSerie(String value) {
		return new JAXBElement<String>(_GetRepresentacionImpresaSerie_QNAME, String.class,
				BuscarDetalleComprobante.class, value);
	}

	/**
	 * Create an instance of {@link JAXBElement }{@code <}{@link String
	 * }{@code >}}
	 * 
	 */
	@XmlElementDecl(namespace = "http://tempuri.org/", name = "token", scope = BuscarDetalleComprobante.class)
	public JAXBElement<String> createBuscarDetalleComprobanteToken(String value) {
		return new JAXBElement<String>(_SetConsultarCDRToken_QNAME, String.class, BuscarDetalleComprobante.class,
				value);
	}

	/**
	 * Create an instance of {@link JAXBElement }{@code <}{@link Result
	 * }{@code >}}
	 * 
	 */
	@XmlElementDecl(namespace = "http://tempuri.org/", name = "setVentaResult", scope = SetVentaResponse.class)
	public JAXBElement<Result> createSetVentaResponseSetVentaResult(Result value) {
		return new JAXBElement<Result>(_SetVentaResponseSetVentaResult_QNAME, Result.class, SetVentaResponse.class,
				value);
	}

	/**
	 * Create an instance of {@link JAXBElement }{@code <}{@link Result
	 * }{@code >}}
	 * 
	 */
	@XmlElementDecl(namespace = "http://tempuri.org/", name = "getRepresentacionImpresaResult", scope = GetRepresentacionImpresaResponse.class)
	public JAXBElement<Result> createGetRepresentacionImpresaResponseGetRepresentacionImpresaResult(Result value) {
		return new JAXBElement<Result>(_GetRepresentacionImpresaResponseGetRepresentacionImpresaResult_QNAME,
				Result.class, GetRepresentacionImpresaResponse.class, value);
	}

	/**
	 * Create an instance of {@link JAXBElement }{@code <}{@link String
	 * }{@code >}}
	 * 
	 */
	@XmlElementDecl(namespace = "http://schemas.datacontract.org/2004/07/FEService.Input", name = "TipoComprobante", scope = DocumentoReferencia.class)
	public JAXBElement<String> createDocumentoReferenciaTipoComprobante(String value) {
		return new JAXBElement<String>(_DocumentoReferenciaTipoComprobante_QNAME, String.class,
				DocumentoReferencia.class, value);
	}

	/**
	 * Create an instance of {@link JAXBElement }{@code <}{@link String
	 * }{@code >}}
	 * 
	 */
	@XmlElementDecl(namespace = "http://schemas.datacontract.org/2004/07/FEService.Input", name = "NumeroDocumento", scope = DocumentoReferencia.class)
	public JAXBElement<String> createDocumentoReferenciaNumeroDocumento(String value) {
		return new JAXBElement<String>(_DocumentoReferenciaNumeroDocumento_QNAME, String.class,
				DocumentoReferencia.class, value);
	}

	/**
	 * Create an instance of {@link JAXBElement }{@code <}{@link String
	 * }{@code >}}
	 * 
	 */
	@XmlElementDecl(namespace = "http://schemas.datacontract.org/2004/07/FEService.Input", name = "FechaDocumento", scope = DocumentoReferencia.class)
	public JAXBElement<String> createDocumentoReferenciaFechaDocumento(String value) {
		return new JAXBElement<String>(_DocumentoReferenciaFechaDocumento_QNAME, String.class,
				DocumentoReferencia.class, value);
	}

	/**
	 * Create an instance of {@link JAXBElement }{@code <}{@link String
	 * }{@code >}}
	 * 
	 */
	@XmlElementDecl(namespace = "http://schemas.datacontract.org/2004/07/FEService.Input", name = "nombres", scope = Cliente.class)
	public JAXBElement<String> createClienteNombres(String value) {
		return new JAXBElement<String>(_ClienteNombres_QNAME, String.class, Cliente.class, value);
	}

	/**
	 * Create an instance of {@link JAXBElement }{@code <}{@link String
	 * }{@code >}}
	 * 
	 */
	@XmlElementDecl(namespace = "http://schemas.datacontract.org/2004/07/FEService.Input", name = "direccion", scope = Cliente.class)
	public JAXBElement<String> createClienteDireccion(String value) {
		return new JAXBElement<String>(_ClienteDireccion_QNAME, String.class, Cliente.class, value);
	}

	/**
	 * Create an instance of {@link JAXBElement }{@code <}{@link String
	 * }{@code >}}
	 * 
	 */
	@XmlElementDecl(namespace = "http://schemas.datacontract.org/2004/07/FEService.Input", name = "numeroDocumento", scope = Cliente.class)
	public JAXBElement<String> createClienteNumeroDocumento(String value) {
		return new JAXBElement<String>(_ClienteNumeroDocumento_QNAME, String.class, Cliente.class, value);
	}

	/**
	 * Create an instance of {@link JAXBElement }{@code <}{@link String
	 * }{@code >}}
	 * 
	 */
	@XmlElementDecl(namespace = "http://schemas.datacontract.org/2004/07/FEService.Input", name = "tipoDocumentoID", scope = Cliente.class)
	public JAXBElement<String> createClienteTipoDocumentoID(String value) {
		return new JAXBElement<String>(_DocumentoBajaTipoDocumentoID_QNAME, String.class, Cliente.class, value);
	}

	/**
	 * Create an instance of {@link JAXBElement }{@code <}{@link Result
	 * }{@code >}}
	 * 
	 */
	@XmlElementDecl(namespace = "http://tempuri.org/", name = "setResumenesResult", scope = SetResumenesResponse.class)
	public JAXBElement<Result> createSetResumenesResponseSetResumenesResult(Result value) {
		return new JAXBElement<Result>(_SetResumenesResponseSetResumenesResult_QNAME, Result.class,
				SetResumenesResponse.class, value);
	}

	/**
	 * Create an instance of {@link JAXBElement }{@code <}{@link String
	 * }{@code >}}
	 * 
	 */
	@XmlElementDecl(namespace = "http://schemas.datacontract.org/2004/07/FEService.Input", name = "usuarioModificacion", scope = Nota.class)
	public JAXBElement<String> createNotaUsuarioModificacion(String value) {
		return new JAXBElement<String>(_DocumentoBajaUsuarioModificacion_QNAME, String.class, Nota.class, value);
	}

	/**
	 * Create an instance of {@link JAXBElement }{@code <}{@link String
	 * }{@code >}}
	 * 
	 */
	@XmlElementDecl(namespace = "http://schemas.datacontract.org/2004/07/FEService.Input", name = "numeroCorrelativo", scope = Nota.class)
	public JAXBElement<String> createNotaNumeroCorrelativo(String value) {
		return new JAXBElement<String>(_DocumentoBajaNumeroCorrelativo_QNAME, String.class, Nota.class, value);
	}

	/**
	 * Create an instance of {@link JAXBElement }{@code <}{@link String
	 * }{@code >}}
	 * 
	 */
	@XmlElementDecl(namespace = "http://schemas.datacontract.org/2004/07/FEService.Input", name = "rucEmpresa", scope = Nota.class)
	public JAXBElement<String> createNotaRucEmpresa(String value) {
		return new JAXBElement<String>(_DocumentoBajaRucEmpresa_QNAME, String.class, Nota.class, value);
	}

	/**
	 * Create an instance of {@link JAXBElement }{@code <}{@link String
	 * }{@code >}}
	 * 
	 */
	@XmlElementDecl(namespace = "http://schemas.datacontract.org/2004/07/FEService.Input", name = "horaEmision", scope = Nota.class)
	public JAXBElement<String> createNotaHoraEmision(String value) {
		return new JAXBElement<String>(_VentaHoraEmision_QNAME, String.class, Nota.class, value);
	}

	/**
	 * Create an instance of {@link JAXBElement }{@code <}{@link Cliente
	 * }{@code >}}
	 * 
	 */
	@XmlElementDecl(namespace = "http://schemas.datacontract.org/2004/07/FEService.Input", name = "cliente", scope = Nota.class)
	public JAXBElement<Cliente> createNotaCliente(Cliente value) {
		return new JAXBElement<Cliente>(_VentaCliente_QNAME, Cliente.class, Nota.class, value);
	}

	/**
	 * Create an instance of {@link JAXBElement
	 * }{@code <}{@link DocumentoReferencia }{@code >}}
	 * 
	 */
	@XmlElementDecl(namespace = "http://schemas.datacontract.org/2004/07/FEService.Input", name = "documentoReferencia", scope = Nota.class)
	public JAXBElement<DocumentoReferencia> createNotaDocumentoReferencia(DocumentoReferencia value) {
		return new JAXBElement<DocumentoReferencia>(_VentaDocumentoReferencia_QNAME, DocumentoReferencia.class,
				Nota.class, value);
	}

	/**
	 * Create an instance of {@link JAXBElement }{@code <}{@link String
	 * }{@code >}}
	 * 
	 */
	@XmlElementDecl(namespace = "http://schemas.datacontract.org/2004/07/FEService.Input", name = "usuarioInsercion", scope = Nota.class)
	public JAXBElement<String> createNotaUsuarioInsercion(String value) {
		return new JAXBElement<String>(_DocumentoBajaUsuarioInsercion_QNAME, String.class, Nota.class, value);
	}

	/**
	 * Create an instance of {@link JAXBElement }{@code <}{@link String
	 * }{@code >}}
	 * 
	 */
	@XmlElementDecl(namespace = "http://schemas.datacontract.org/2004/07/FEService.Input", name = "descripcionSustento", scope = Nota.class)
	public JAXBElement<String> createNotaDescripcionSustento(String value) {
		return new JAXBElement<String>(_NotaDescripcionSustento_QNAME, String.class, Nota.class, value);
	}

	/**
	 * Create an instance of {@link JAXBElement }{@code <}{@link String
	 * }{@code >}}
	 * 
	 */
	@XmlElementDecl(namespace = "http://schemas.datacontract.org/2004/07/FEService.Input", name = "codigoTipoNota", scope = Nota.class)
	public JAXBElement<String> createNotaCodigoTipoNota(String value) {
		return new JAXBElement<String>(_NotaCodigoTipoNota_QNAME, String.class, Nota.class, value);
	}

	/**
	 * Create an instance of {@link JAXBElement }{@code <}{@link String
	 * }{@code >}}
	 * 
	 */
	@XmlElementDecl(namespace = "http://schemas.datacontract.org/2004/07/FEService.Input", name = "descripcionTipoNota", scope = Nota.class)
	public JAXBElement<String> createNotaDescripcionTipoNota(String value) {
		return new JAXBElement<String>(_NotaDescripcionTipoNota_QNAME, String.class, Nota.class, value);
	}

	/**
	 * Create an instance of {@link JAXBElement }{@code <}{@link String
	 * }{@code >}}
	 * 
	 */
	@XmlElementDecl(namespace = "http://schemas.datacontract.org/2004/07/FEService.Input", name = "fechaEmision", scope = Nota.class)
	public JAXBElement<String> createNotaFechaEmision(String value) {
		return new JAXBElement<String>(_DocumentoBajaFechaEmision_QNAME, String.class, Nota.class, value);
	}

	/**
	 * Create an instance of {@link JAXBElement }{@code <}{@link String
	 * }{@code >}}
	 * 
	 */
	@XmlElementDecl(namespace = "http://schemas.datacontract.org/2004/07/FEService.Input", name = "tipoComprobanteID", scope = Nota.class)
	public JAXBElement<String> createNotaTipoComprobanteID(String value) {
		return new JAXBElement<String>(_VentaTipoComprobanteID_QNAME, String.class, Nota.class, value);
	}

	/**
	 * Create an instance of {@link JAXBElement
	 * }{@code <}{@link InformacionAdicional }{@code >}}
	 * 
	 */
	@XmlElementDecl(namespace = "http://schemas.datacontract.org/2004/07/FEService.Input", name = "informacionAdicional", scope = Nota.class)
	public JAXBElement<InformacionAdicional> createNotaInformacionAdicional(InformacionAdicional value) {
		return new JAXBElement<InformacionAdicional>(_VentaInformacionAdicional_QNAME, InformacionAdicional.class,
				Nota.class, value);
	}

	/**
	 * Create an instance of {@link JAXBElement }{@code <}{@link String
	 * }{@code >}}
	 * 
	 */
	@XmlElementDecl(namespace = "http://schemas.datacontract.org/2004/07/FEService.Input", name = "numeroSerie", scope = Nota.class)
	public JAXBElement<String> createNotaNumeroSerie(String value) {
		return new JAXBElement<String>(_DocumentoBajaNumeroSerie_QNAME, String.class, Nota.class, value);
	}

	/**
	 * Create an instance of {@link JAXBElement }{@code <}{@link Result
	 * }{@code >}}
	 * 
	 */
	@XmlElementDecl(namespace = "http://tempuri.org/", name = "setUpdateStatusCDRResult", scope = SetUpdateStatusCDRResponse.class)
	public JAXBElement<Result> createSetUpdateStatusCDRResponseSetUpdateStatusCDRResult(Result value) {
		return new JAXBElement<Result>(_SetUpdateStatusCDRResponseSetUpdateStatusCDRResult_QNAME, Result.class,
				SetUpdateStatusCDRResponse.class, value);
	}

}
