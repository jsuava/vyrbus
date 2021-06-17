package cruzdelsur.ws.com.pe;

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
 * element interface generated in the cruzdelsur.ws.com.pe package.
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

	private final static QName _ResultAnularBoletoError_QNAME = new QName(
			"http://schemas.datacontract.org/2004/07/wscruzdelsur.Entities",
			"error");
	private final static QName _AnyURI_QNAME = new QName(
			"http://schemas.microsoft.com/2003/10/Serialization/", "anyURI");
	private final static QName _Char_QNAME = new QName(
			"http://schemas.microsoft.com/2003/10/Serialization/", "char");
	private final static QName _DateTime_QNAME = new QName(
			"http://schemas.microsoft.com/2003/10/Serialization/", "dateTime");
	private final static QName _ResultAnularBoleto_QNAME = new QName(
			"http://schemas.datacontract.org/2004/07/wscruzdelsur.Entities",
			"ResultAnularBoleto");
	private final static QName _QName_QNAME = new QName(
			"http://schemas.microsoft.com/2003/10/Serialization/", "QName");
	private final static QName _UnsignedShort_QNAME = new QName(
			"http://schemas.microsoft.com/2003/10/Serialization/",
			"unsignedShort");
	private final static QName _Generic_QNAME = new QName(
			"http://schemas.datacontract.org/2004/07/wscruzdelsur.Entities",
			"Generic");
	private final static QName _Float_QNAME = new QName(
			"http://schemas.microsoft.com/2003/10/Serialization/", "float");
	private final static QName _Long_QNAME = new QName(
			"http://schemas.microsoft.com/2003/10/Serialization/", "long");
	private final static QName _ArrayOfDistribucion_QNAME = new QName(
			"http://schemas.datacontract.org/2004/07/wscruzdelsur.Entities",
			"ArrayOfDistribucion");
	private final static QName _Short_QNAME = new QName(
			"http://schemas.microsoft.com/2003/10/Serialization/", "short");
	private final static QName _ArrayOfHorario_QNAME = new QName(
			"http://schemas.datacontract.org/2004/07/wscruzdelsur.Entities",
			"ArrayOfHorario");
	private final static QName _Base64Binary_QNAME = new QName(
			"http://schemas.microsoft.com/2003/10/Serialization/",
			"base64Binary");
	private final static QName _Byte_QNAME = new QName(
			"http://schemas.microsoft.com/2003/10/Serialization/", "byte");
	private final static QName _Boolean_QNAME = new QName(
			"http://schemas.microsoft.com/2003/10/Serialization/", "boolean");
	private final static QName _ArrayOfGeneric_QNAME = new QName(
			"http://schemas.datacontract.org/2004/07/wscruzdelsur.Entities",
			"ArrayOfGeneric");
	private final static QName _Error_QNAME = new QName(
			"http://schemas.datacontract.org/2004/07/wscruzdelsur.Entities",
			"Error");
	private final static QName _ResultDesbloquearAsiento_QNAME = new QName(
			"http://schemas.datacontract.org/2004/07/wscruzdelsur.Entities",
			"ResultDesbloquearAsiento");
	private final static QName _Pasajero_QNAME = new QName(
			"http://schemas.datacontract.org/2004/07/wscruzdelsur.Entities",
			"Pasajero");
	private final static QName _ResultBloquearAsiento_QNAME = new QName(
			"http://schemas.datacontract.org/2004/07/wscruzdelsur.Entities",
			"ResultBloquearAsiento");
	private final static QName _ResultVenta_QNAME = new QName(
			"http://schemas.datacontract.org/2004/07/wscruzdelsur.Entities",
			"ResultVenta");
	private final static QName _UnsignedByte_QNAME = new QName(
			"http://schemas.microsoft.com/2003/10/Serialization/",
			"unsignedByte");
	private final static QName _AnyType_QNAME = new QName(
			"http://schemas.microsoft.com/2003/10/Serialization/", "anyType");
	private final static QName _UnsignedInt_QNAME = new QName(
			"http://schemas.microsoft.com/2003/10/Serialization/",
			"unsignedInt");
	private final static QName _Int_QNAME = new QName(
			"http://schemas.microsoft.com/2003/10/Serialization/", "int");
	private final static QName _Horario_QNAME = new QName(
			"http://schemas.datacontract.org/2004/07/wscruzdelsur.Entities",
			"Horario");
	private final static QName _Decimal_QNAME = new QName(
			"http://schemas.microsoft.com/2003/10/Serialization/", "decimal");
	private final static QName _Double_QNAME = new QName(
			"http://schemas.microsoft.com/2003/10/Serialization/", "double");
	private final static QName _Guid_QNAME = new QName(
			"http://schemas.microsoft.com/2003/10/Serialization/", "guid");
	private final static QName _Distribucion_QNAME = new QName(
			"http://schemas.datacontract.org/2004/07/wscruzdelsur.Entities",
			"Distribucion");
	private final static QName _Duration_QNAME = new QName(
			"http://schemas.microsoft.com/2003/10/Serialization/", "duration");
	private final static QName _String_QNAME = new QName(
			"http://schemas.microsoft.com/2003/10/Serialization/", "string");
	private final static QName _UnsignedLong_QNAME = new QName(
			"http://schemas.microsoft.com/2003/10/Serialization/",
			"unsignedLong");
	private final static QName _AnularBoletoResponseAnularBoletoResult_QNAME = new QName(
			"http://tempuri.org/", "anularBoletoResult");
	private final static QName _HorarioAgenciaEmbarque2_QNAME = new QName(
			"http://schemas.datacontract.org/2004/07/wscruzdelsur.Entities",
			"agenciaEmbarque2");
	private final static QName _HorarioServicio_QNAME = new QName(
			"http://schemas.datacontract.org/2004/07/wscruzdelsur.Entities",
			"servicio");
	private final static QName _HorarioDireccionEmbarque2_QNAME = new QName(
			"http://schemas.datacontract.org/2004/07/wscruzdelsur.Entities",
			"direccionEmbarque2");
	private final static QName _HorarioTelefonoDesembarque1_QNAME = new QName(
			"http://schemas.datacontract.org/2004/07/wscruzdelsur.Entities",
			"telefonoDesembarque1");
	private final static QName _HorarioTelefonoDesembarque2_QNAME = new QName(
			"http://schemas.datacontract.org/2004/07/wscruzdelsur.Entities",
			"telefonoDesembarque2");
	private final static QName _HorarioDireccionEmbarque1_QNAME = new QName(
			"http://schemas.datacontract.org/2004/07/wscruzdelsur.Entities",
			"direccionEmbarque1");
	private final static QName _HorarioUnidadLlave_QNAME = new QName(
			"http://schemas.datacontract.org/2004/07/wscruzdelsur.Entities",
			"unidadLlave");
	private final static QName _HorarioRutaInternacional_QNAME = new QName(
			"http://schemas.datacontract.org/2004/07/wscruzdelsur.Entities",
			"rutaInternacional");
	private final static QName _HorarioAplicarPromocion_QNAME = new QName(
			"http://schemas.datacontract.org/2004/07/wscruzdelsur.Entities",
			"aplicarPromocion");
	private final static QName _HorarioAgenciaEmbarque1_QNAME = new QName(
			"http://schemas.datacontract.org/2004/07/wscruzdelsur.Entities",
			"agenciaEmbarque1");
	private final static QName _HorarioServicioLlave_QNAME = new QName(
			"http://schemas.datacontract.org/2004/07/wscruzdelsur.Entities",
			"servicioLlave");
	private final static QName _HorarioCodigoRuta_QNAME = new QName(
			"http://schemas.datacontract.org/2004/07/wscruzdelsur.Entities",
			"codigoRuta");
	private final static QName _HorarioTelefonoEmbarque2_QNAME = new QName(
			"http://schemas.datacontract.org/2004/07/wscruzdelsur.Entities",
			"telefonoEmbarque2");
	private final static QName _HorarioAgenciaDesembarque2_QNAME = new QName(
			"http://schemas.datacontract.org/2004/07/wscruzdelsur.Entities",
			"agenciaDesembarque2");
	private final static QName _HorarioTelefonoEmbarque1_QNAME = new QName(
			"http://schemas.datacontract.org/2004/07/wscruzdelsur.Entities",
			"telefonoEmbarque1");
	private final static QName _HorarioAgenciaDesembarque1_QNAME = new QName(
			"http://schemas.datacontract.org/2004/07/wscruzdelsur.Entities",
			"agenciaDesembarque1");
	private final static QName _HorarioAgenciaEmbarqueLlave2_QNAME = new QName(
			"http://schemas.datacontract.org/2004/07/wscruzdelsur.Entities",
			"agenciaEmbarqueLlave2");
	private final static QName _HorarioFechaHoraEmbarque1_QNAME = new QName(
			"http://schemas.datacontract.org/2004/07/wscruzdelsur.Entities",
			"fechaHoraEmbarque1");
	private final static QName _HorarioMenus_QNAME = new QName(
			"http://schemas.datacontract.org/2004/07/wscruzdelsur.Entities",
			"menus");
	private final static QName _HorarioFechaHoraEmbarque2_QNAME = new QName(
			"http://schemas.datacontract.org/2004/07/wscruzdelsur.Entities",
			"fechaHoraEmbarque2");
	private final static QName _HorarioDireccionDesembarque2_QNAME = new QName(
			"http://schemas.datacontract.org/2004/07/wscruzdelsur.Entities",
			"direccionDesembarque2");
	private final static QName _HorarioAgenciaEmbarqueLlave1_QNAME = new QName(
			"http://schemas.datacontract.org/2004/07/wscruzdelsur.Entities",
			"agenciaEmbarqueLlave1");
	private final static QName _HorarioFechaHoraDesembarque2_QNAME = new QName(
			"http://schemas.datacontract.org/2004/07/wscruzdelsur.Entities",
			"fechaHoraDesembarque2");
	private final static QName _HorarioProgramacionLlave_QNAME = new QName(
			"http://schemas.datacontract.org/2004/07/wscruzdelsur.Entities",
			"programacionLlave");
	private final static QName _HorarioTiempoViaje_QNAME = new QName(
			"http://schemas.datacontract.org/2004/07/wscruzdelsur.Entities",
			"tiempoViaje");
	private final static QName _HorarioDireccionDesembarque1_QNAME = new QName(
			"http://schemas.datacontract.org/2004/07/wscruzdelsur.Entities",
			"direccionDesembarque1");
	private final static QName _HorarioFechaHoraDesembarque1_QNAME = new QName(
			"http://schemas.datacontract.org/2004/07/wscruzdelsur.Entities",
			"fechaHoraDesembarque1");
	private final static QName _HorarioRuta_QNAME = new QName(
			"http://schemas.datacontract.org/2004/07/wscruzdelsur.Entities",
			"ruta");
	private final static QName _HorarioAgenciaDesembarqueLlave1_QNAME = new QName(
			"http://schemas.datacontract.org/2004/07/wscruzdelsur.Entities",
			"agenciaDesembarqueLlave1");
	private final static QName _HorarioNumeroServicio_QNAME = new QName(
			"http://schemas.datacontract.org/2004/07/wscruzdelsur.Entities",
			"numeroServicio");
	private final static QName _HorarioAgenciaDesembarqueLlave2_QNAME = new QName(
			"http://schemas.datacontract.org/2004/07/wscruzdelsur.Entities",
			"agenciaDesembarqueLlave2");
	private final static QName _PasajeroNombres_QNAME = new QName(
			"http://schemas.datacontract.org/2004/07/wscruzdelsur.Entities",
			"nombres");
	private final static QName _PasajeroNumeroBoleto_QNAME = new QName(
			"http://schemas.datacontract.org/2004/07/wscruzdelsur.Entities",
			"numeroBoleto");
	private final static QName _PasajeroEmail_QNAME = new QName(
			"http://schemas.datacontract.org/2004/07/wscruzdelsur.Entities",
			"email");
	private final static QName _PasajeroCodigoNacionalidad_QNAME = new QName(
			"http://schemas.datacontract.org/2004/07/wscruzdelsur.Entities",
			"codigoNacionalidad");
	private final static QName _PasajeroApellidoMaterno_QNAME = new QName(
			"http://schemas.datacontract.org/2004/07/wscruzdelsur.Entities",
			"apellidoMaterno");
	private final static QName _PasajeroRazonSocial_QNAME = new QName(
			"http://schemas.datacontract.org/2004/07/wscruzdelsur.Entities",
			"RazonSocial");
	private final static QName _PasajeroTipoTarifa_QNAME = new QName(
			"http://schemas.datacontract.org/2004/07/wscruzdelsur.Entities",
			"tipoTarifa");
	private final static QName _PasajeroNumeroDocumento_QNAME = new QName(
			"http://schemas.datacontract.org/2004/07/wscruzdelsur.Entities",
			"numeroDocumento");
	private final static QName _PasajeroRucEmpresa_QNAME = new QName(
			"http://schemas.datacontract.org/2004/07/wscruzdelsur.Entities",
			"RucEmpresa");
	private final static QName _PasajeroApellidoPaterno_QNAME = new QName(
			"http://schemas.datacontract.org/2004/07/wscruzdelsur.Entities",
			"apellidoPaterno");
	private final static QName _PasajeroTelefono_QNAME = new QName(
			"http://schemas.datacontract.org/2004/07/wscruzdelsur.Entities",
			"telefono");
	private final static QName _PasajeroFechaNacimiento_QNAME = new QName(
			"http://schemas.datacontract.org/2004/07/wscruzdelsur.Entities",
			"fechaNacimiento");
	private final static QName _PasajeroCodigoTipoDocumento_QNAME = new QName(
			"http://schemas.datacontract.org/2004/07/wscruzdelsur.Entities",
			"codigoTipoDocumento");
	private final static QName _ObtenerHorariosLocalidadDesembarque_QNAME = new QName(
			"http://tempuri.org/", "localidadDesembarque");
	private final static QName _ObtenerHorariosFechaProgramacion_QNAME = new QName(
			"http://tempuri.org/", "fechaProgramacion");
	private final static QName _ObtenerHorariosLocalidadEmbarque_QNAME = new QName(
			"http://tempuri.org/", "localidadEmbarque");
	private final static QName _ErrorMessage_QNAME = new QName(
			"http://schemas.datacontract.org/2004/07/wscruzdelsur.Entities",
			"message");
	private final static QName _ErrorCode_QNAME = new QName(
			"http://schemas.datacontract.org/2004/07/wscruzdelsur.Entities",
			"code");
	private final static QName _ResultBloquearAsientoResultadoTransaccion_QNAME = new QName(
			"http://schemas.datacontract.org/2004/07/wscruzdelsur.Entities",
			"resultadoTransaccion");
	private final static QName _ResultBloquearAsientoCodigoTransaccion_QNAME = new QName(
			"http://schemas.datacontract.org/2004/07/wscruzdelsur.Entities",
			"codigoTransaccion");
	private final static QName _GenericListGeneric_QNAME = new QName(
			"http://schemas.datacontract.org/2004/07/wscruzdelsur.Entities",
			"listGeneric");
	private final static QName _GenericValue_QNAME = new QName(
			"http://schemas.datacontract.org/2004/07/wscruzdelsur.Entities",
			"value");
	private final static QName _GenericKey_QNAME = new QName(
			"http://schemas.datacontract.org/2004/07/wscruzdelsur.Entities",
			"key");
	private final static QName _VenderPasajeResponseVenderPasajeResult_QNAME = new QName(
			"http://tempuri.org/", "venderPasajeResult");
	private final static QName _ObtenerAsientosDisponiblesResponseObtenerAsientosDisponiblesResult_QNAME = new QName(
			"http://tempuri.org/", "obtenerAsientosDisponiblesResult");
	private final static QName _VenderPasajeAgenciaEmbarqueLlave_QNAME = new QName(
			"http://tempuri.org/", "agenciaEmbarqueLlave");
	private final static QName _VenderPasajePasajero_QNAME = new QName(
			"http://tempuri.org/", "pasajero");
	private final static QName _VenderPasajeAgenciaDesembarqueLlave_QNAME = new QName(
			"http://tempuri.org/", "agenciaDesembarqueLlave");
	private final static QName _VenderPasajeCodigoTransaccion_QNAME = new QName(
			"http://tempuri.org/", "codigoTransaccion");
	private final static QName _ObtenerAsientosDisponiblesProgramacionLlave_QNAME = new QName(
			"http://tempuri.org/", "programacionLlave");
	private final static QName _ObtenerAsientosDisponiblesServicioLlave_QNAME = new QName(
			"http://tempuri.org/", "servicioLlave");
	private final static QName _ObtenerAsientosDisponiblesCodigoRuta_QNAME = new QName(
			"http://tempuri.org/", "codigoRuta");
	private final static QName _ObtenerAsientosDisponiblesUnidadLlave_QNAME = new QName(
			"http://tempuri.org/", "unidadLlave");
	private final static QName _ObtenerHorariosResponseObtenerHorariosResult_QNAME = new QName(
			"http://tempuri.org/", "obtenerHorariosResult");
	private final static QName _DistribucionTotalNivel_QNAME = new QName(
			"http://schemas.datacontract.org/2004/07/wscruzdelsur.Entities",
			"totalNivel");
	private final static QName _DistribucionElemento_QNAME = new QName(
			"http://schemas.datacontract.org/2004/07/wscruzdelsur.Entities",
			"elemento");
	private final static QName _DistribucionDescripcion_QNAME = new QName(
			"http://schemas.datacontract.org/2004/07/wscruzdelsur.Entities",
			"descripcion");
	private final static QName _DistribucionAsiento_QNAME = new QName(
			"http://schemas.datacontract.org/2004/07/wscruzdelsur.Entities",
			"asiento");
	private final static QName _DistribucionNivelPiso_QNAME = new QName(
			"http://schemas.datacontract.org/2004/07/wscruzdelsur.Entities",
			"nivelPiso");
	private final static QName _DistribucionTarifaDescripcion_QNAME = new QName(
			"http://schemas.datacontract.org/2004/07/wscruzdelsur.Entities",
			"tarifaDescripcion");
	private final static QName _ResultDesbloquearAsientoNivelUnidad_QNAME = new QName(
			"http://schemas.datacontract.org/2004/07/wscruzdelsur.Entities",
			"nivelUnidad");
	private final static QName _ResultDesbloquearAsientoCodigoAsiento_QNAME = new QName(
			"http://schemas.datacontract.org/2004/07/wscruzdelsur.Entities",
			"codigoAsiento");
	private final static QName _DesbloquearAsientoResponseDesbloquearAsientoResult_QNAME = new QName(
			"http://tempuri.org/", "desbloquearAsientoResult");
	private final static QName _BloquearAsientoResponseBloquearAsientoResult_QNAME = new QName(
			"http://tempuri.org/", "bloquearAsientoResult");
	private final static QName _BloquearAsientoNivelUnidad_QNAME = new QName(
			"http://tempuri.org/", "nivelUnidad");
	private final static QName _BloquearAsientoNumeroServicio_QNAME = new QName(
			"http://tempuri.org/", "numeroServicio");
	private final static QName _BloquearAsientoCodigoAsiento_QNAME = new QName(
			"http://tempuri.org/", "codigoAsiento");
	private final static QName _AnularBoletoSerie_QNAME = new QName(
			"http://tempuri.org/", "serie");
	private final static QName _AnularBoletoNumero_QNAME = new QName(
			"http://tempuri.org/", "numero");

	/**
	 * Create a new ObjectFactory that can be used to create new instances of
	 * schema derived classes for package: cruzdelsur.ws.com.pe
	 * 
	 */
	public ObjectFactory() {
	}

	/**
	 * Create an instance of {@link AnularBoletoResponse }
	 * 
	 */
	public AnularBoletoResponse createAnularBoletoResponse() {
		return new AnularBoletoResponse();
	}

	/**
	 * Create an instance of {@link ResultAnularBoleto }
	 * 
	 */
	public ResultAnularBoleto createResultAnularBoleto() {
		return new ResultAnularBoleto();
	}

	/**
	 * Create an instance of {@link ArrayOfGeneric }
	 * 
	 */
	public ArrayOfGeneric createArrayOfGeneric() {
		return new ArrayOfGeneric();
	}

	/**
	 * Create an instance of {@link Horario }
	 * 
	 */
	public Horario createHorario() {
		return new Horario();
	}

	/**
	 * Create an instance of {@link ArrayOfDistribucion }
	 * 
	 */
	public ArrayOfDistribucion createArrayOfDistribucion() {
		return new ArrayOfDistribucion();
	}

	/**
	 * Create an instance of {@link Pasajero }
	 * 
	 */
	public Pasajero createPasajero() {
		return new Pasajero();
	}

	/**
	 * Create an instance of {@link ObtenerHorarios }
	 * 
	 */
	public ObtenerHorarios createObtenerHorarios() {
		return new ObtenerHorarios();
	}

	/**
	 * Create an instance of {@link Error }
	 * 
	 */
	public Error createError() {
		return new Error();
	}

	/**
	 * Create an instance of {@link ResultBloquearAsiento }
	 * 
	 */
	public ResultBloquearAsiento createResultBloquearAsiento() {
		return new ResultBloquearAsiento();
	}

	/**
	 * Create an instance of {@link Generic }
	 * 
	 */
	public Generic createGeneric() {
		return new Generic();
	}

	/**
	 * Create an instance of {@link VenderPasajeResponse }
	 * 
	 */
	public VenderPasajeResponse createVenderPasajeResponse() {
		return new VenderPasajeResponse();
	}

	/**
	 * Create an instance of {@link ObtenerAsientosDisponiblesResponse }
	 * 
	 */
	public ObtenerAsientosDisponiblesResponse createObtenerAsientosDisponiblesResponse() {
		return new ObtenerAsientosDisponiblesResponse();
	}

	/**
	 * Create an instance of {@link ArrayOfHorario }
	 * 
	 */
	public ArrayOfHorario createArrayOfHorario() {
		return new ArrayOfHorario();
	}

	/**
	 * Create an instance of {@link VenderPasaje }
	 * 
	 */
	public VenderPasaje createVenderPasaje() {
		return new VenderPasaje();
	}

	/**
	 * Create an instance of {@link ObtenerAsientosDisponibles }
	 * 
	 */
	public ObtenerAsientosDisponibles createObtenerAsientosDisponibles() {
		return new ObtenerAsientosDisponibles();
	}

	/**
	 * Create an instance of {@link ObtenerHorariosResponse }
	 * 
	 */
	public ObtenerHorariosResponse createObtenerHorariosResponse() {
		return new ObtenerHorariosResponse();
	}

	/**
	 * Create an instance of {@link Distribucion }
	 * 
	 */
	public Distribucion createDistribucion() {
		return new Distribucion();
	}

	/**
	 * Create an instance of {@link ResultDesbloquearAsiento }
	 * 
	 */
	public ResultDesbloquearAsiento createResultDesbloquearAsiento() {
		return new ResultDesbloquearAsiento();
	}

	/**
	 * Create an instance of {@link DesbloquearAsientoResponse }
	 * 
	 */
	public DesbloquearAsientoResponse createDesbloquearAsientoResponse() {
		return new DesbloquearAsientoResponse();
	}

	/**
	 * Create an instance of {@link DesbloquearAsiento }
	 * 
	 */
	public DesbloquearAsiento createDesbloquearAsiento() {
		return new DesbloquearAsiento();
	}

	/**
	 * Create an instance of {@link ResultVenta }
	 * 
	 */
	public ResultVenta createResultVenta() {
		return new ResultVenta();
	}

	/**
	 * Create an instance of {@link BloquearAsientoResponse }
	 * 
	 */
	public BloquearAsientoResponse createBloquearAsientoResponse() {
		return new BloquearAsientoResponse();
	}

	/**
	 * Create an instance of {@link AnularBoleto }
	 * 
	 */
	public AnularBoleto createAnularBoleto() {
		return new AnularBoleto();
	}

	/**
	 * Create an instance of {@link BloquearAsiento }
	 * 
	 */
	public BloquearAsiento createBloquearAsiento() {
		return new BloquearAsiento();
	}

	/**
	 * Create an instance of {@link JAXBElement }{@code <}{@link Error }{@code >}
	 * 
	 */
	@XmlElementDecl(namespace = "http://schemas.datacontract.org/2004/07/wscruzdelsur.Entities", name = "error", scope = ResultAnularBoleto.class)
	public JAXBElement<Error> createResultAnularBoletoError(Error value) {
		return new JAXBElement<Error>(_ResultAnularBoletoError_QNAME,
				Error.class, ResultAnularBoleto.class, value);
	}

	/**
	 * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}
	 * 
	 */
	@XmlElementDecl(namespace = "http://schemas.microsoft.com/2003/10/Serialization/", name = "anyURI")
	public JAXBElement<String> createAnyURI(String value) {
		return new JAXBElement<String>(_AnyURI_QNAME, String.class, null, value);
	}

	/**
	 * Create an instance of {@link JAXBElement }{@code <}{@link Integer }{@code >}
	 * 
	 */
	@XmlElementDecl(namespace = "http://schemas.microsoft.com/2003/10/Serialization/", name = "char")
	public JAXBElement<Integer> createChar(Integer value) {
		return new JAXBElement<Integer>(_Char_QNAME, Integer.class, null, value);
	}

	/**
	 * Create an instance of {@link JAXBElement }{@code <}
	 * {@link XMLGregorianCalendar }{@code >}
	 * 
	 */
	@XmlElementDecl(namespace = "http://schemas.microsoft.com/2003/10/Serialization/", name = "dateTime")
	public JAXBElement<XMLGregorianCalendar> createDateTime(
			XMLGregorianCalendar value) {
		return new JAXBElement<XMLGregorianCalendar>(_DateTime_QNAME,
				XMLGregorianCalendar.class, null, value);
	}

	/**
	 * Create an instance of {@link JAXBElement }{@code <}
	 * {@link ResultAnularBoleto }{@code >}
	 * 
	 */
	@XmlElementDecl(namespace = "http://schemas.datacontract.org/2004/07/wscruzdelsur.Entities", name = "ResultAnularBoleto")
	public JAXBElement<ResultAnularBoleto> createResultAnularBoleto(
			ResultAnularBoleto value) {
		return new JAXBElement<ResultAnularBoleto>(_ResultAnularBoleto_QNAME,
				ResultAnularBoleto.class, null, value);
	}

	/**
	 * Create an instance of {@link JAXBElement }{@code <}{@link QName }{@code >}
	 * 
	 */
	@XmlElementDecl(namespace = "http://schemas.microsoft.com/2003/10/Serialization/", name = "QName")
	public JAXBElement<QName> createQName(QName value) {
		return new JAXBElement<QName>(_QName_QNAME, QName.class, null, value);
	}

	/**
	 * Create an instance of {@link JAXBElement }{@code <}{@link Integer }{@code >}
	 * 
	 */
	@XmlElementDecl(namespace = "http://schemas.microsoft.com/2003/10/Serialization/", name = "unsignedShort")
	public JAXBElement<Integer> createUnsignedShort(Integer value) {
		return new JAXBElement<Integer>(_UnsignedShort_QNAME, Integer.class,
				null, value);
	}

	/**
	 * Create an instance of {@link JAXBElement }{@code <}{@link Generic }{@code >}
	 * 
	 */
	@XmlElementDecl(namespace = "http://schemas.datacontract.org/2004/07/wscruzdelsur.Entities", name = "Generic")
	public JAXBElement<Generic> createGeneric(Generic value) {
		return new JAXBElement<Generic>(_Generic_QNAME, Generic.class, null,
				value);
	}

	/**
	 * Create an instance of {@link JAXBElement }{@code <}{@link Float }{@code >}
	 * 
	 */
	@XmlElementDecl(namespace = "http://schemas.microsoft.com/2003/10/Serialization/", name = "float")
	public JAXBElement<Float> createFloat(Float value) {
		return new JAXBElement<Float>(_Float_QNAME, Float.class, null, value);
	}

	/**
	 * Create an instance of {@link JAXBElement }{@code <}{@link Long }{@code >}
	 * 
	 */
	@XmlElementDecl(namespace = "http://schemas.microsoft.com/2003/10/Serialization/", name = "long")
	public JAXBElement<Long> createLong(Long value) {
		return new JAXBElement<Long>(_Long_QNAME, Long.class, null, value);
	}

	/**
	 * Create an instance of {@link JAXBElement }{@code <}
	 * {@link ArrayOfDistribucion }{@code >}
	 * 
	 */
	@XmlElementDecl(namespace = "http://schemas.datacontract.org/2004/07/wscruzdelsur.Entities", name = "ArrayOfDistribucion")
	public JAXBElement<ArrayOfDistribucion> createArrayOfDistribucion(
			ArrayOfDistribucion value) {
		return new JAXBElement<ArrayOfDistribucion>(_ArrayOfDistribucion_QNAME,
				ArrayOfDistribucion.class, null, value);
	}

	/**
	 * Create an instance of {@link JAXBElement }{@code <}{@link Short }{@code >}
	 * 
	 */
	@XmlElementDecl(namespace = "http://schemas.microsoft.com/2003/10/Serialization/", name = "short")
	public JAXBElement<Short> createShort(Short value) {
		return new JAXBElement<Short>(_Short_QNAME, Short.class, null, value);
	}

	/**
	 * Create an instance of {@link JAXBElement }{@code <}{@link ArrayOfHorario }
	 * {@code >}
	 * 
	 */
	@XmlElementDecl(namespace = "http://schemas.datacontract.org/2004/07/wscruzdelsur.Entities", name = "ArrayOfHorario")
	public JAXBElement<ArrayOfHorario> createArrayOfHorario(ArrayOfHorario value) {
		return new JAXBElement<ArrayOfHorario>(_ArrayOfHorario_QNAME,
				ArrayOfHorario.class, null, value);
	}

	/**
	 * Create an instance of {@link JAXBElement }{@code <}{@link byte[]}{@code >}
	 * 
	 */
	@XmlElementDecl(namespace = "http://schemas.microsoft.com/2003/10/Serialization/", name = "base64Binary")
	public JAXBElement<byte[]> createBase64Binary(byte[] value) {
		return new JAXBElement<byte[]>(_Base64Binary_QNAME, byte[].class, null,
				(value));
	}

	/**
	 * Create an instance of {@link JAXBElement }{@code <}{@link Byte }{@code >}
	 * 
	 */
	@XmlElementDecl(namespace = "http://schemas.microsoft.com/2003/10/Serialization/", name = "byte")
	public JAXBElement<Byte> createByte(Byte value) {
		return new JAXBElement<Byte>(_Byte_QNAME, Byte.class, null, value);
	}

	/**
	 * Create an instance of {@link JAXBElement }{@code <}{@link Boolean }{@code >}
	 * 
	 */
	@XmlElementDecl(namespace = "http://schemas.microsoft.com/2003/10/Serialization/", name = "boolean")
	public JAXBElement<Boolean> createBoolean(Boolean value) {
		return new JAXBElement<Boolean>(_Boolean_QNAME, Boolean.class, null,
				value);
	}

	/**
	 * Create an instance of {@link JAXBElement }{@code <}{@link ArrayOfGeneric }
	 * {@code >}
	 * 
	 */
	@XmlElementDecl(namespace = "http://schemas.datacontract.org/2004/07/wscruzdelsur.Entities", name = "ArrayOfGeneric")
	public JAXBElement<ArrayOfGeneric> createArrayOfGeneric(ArrayOfGeneric value) {
		return new JAXBElement<ArrayOfGeneric>(_ArrayOfGeneric_QNAME,
				ArrayOfGeneric.class, null, value);
	}

	/**
	 * Create an instance of {@link JAXBElement }{@code <}{@link Error }{@code >}
	 * 
	 */
	@XmlElementDecl(namespace = "http://schemas.datacontract.org/2004/07/wscruzdelsur.Entities", name = "Error")
	public JAXBElement<Error> createError(Error value) {
		return new JAXBElement<Error>(_Error_QNAME, Error.class, null, value);
	}

	/**
	 * Create an instance of {@link JAXBElement }{@code <}
	 * {@link ResultDesbloquearAsiento }{@code >}
	 * 
	 */
	@XmlElementDecl(namespace = "http://schemas.datacontract.org/2004/07/wscruzdelsur.Entities", name = "ResultDesbloquearAsiento")
	public JAXBElement<ResultDesbloquearAsiento> createResultDesbloquearAsiento(
			ResultDesbloquearAsiento value) {
		return new JAXBElement<ResultDesbloquearAsiento>(
				_ResultDesbloquearAsiento_QNAME,
				ResultDesbloquearAsiento.class, null, value);
	}

	/**
	 * Create an instance of {@link JAXBElement }{@code <}{@link Pasajero }
	 * {@code >}
	 * 
	 */
	@XmlElementDecl(namespace = "http://schemas.datacontract.org/2004/07/wscruzdelsur.Entities", name = "Pasajero")
	public JAXBElement<Pasajero> createPasajero(Pasajero value) {
		return new JAXBElement<Pasajero>(_Pasajero_QNAME, Pasajero.class, null,
				value);
	}

	/**
	 * Create an instance of {@link JAXBElement }{@code <}
	 * {@link ResultBloquearAsiento }{@code >}
	 * 
	 */
	@XmlElementDecl(namespace = "http://schemas.datacontract.org/2004/07/wscruzdelsur.Entities", name = "ResultBloquearAsiento")
	public JAXBElement<ResultBloquearAsiento> createResultBloquearAsiento(
			ResultBloquearAsiento value) {
		return new JAXBElement<ResultBloquearAsiento>(
				_ResultBloquearAsiento_QNAME, ResultBloquearAsiento.class,
				null, value);
	}

	/**
	 * Create an instance of {@link JAXBElement }{@code <}{@link ResultVenta }
	 * {@code >}
	 * 
	 */
	@XmlElementDecl(namespace = "http://schemas.datacontract.org/2004/07/wscruzdelsur.Entities", name = "ResultVenta")
	public JAXBElement<ResultVenta> createResultVenta(ResultVenta value) {
		return new JAXBElement<ResultVenta>(_ResultVenta_QNAME,
				ResultVenta.class, null, value);
	}

	/**
	 * Create an instance of {@link JAXBElement }{@code <}{@link Short }{@code >}
	 * 
	 */
	@XmlElementDecl(namespace = "http://schemas.microsoft.com/2003/10/Serialization/", name = "unsignedByte")
	public JAXBElement<Short> createUnsignedByte(Short value) {
		return new JAXBElement<Short>(_UnsignedByte_QNAME, Short.class, null,
				value);
	}

	/**
	 * Create an instance of {@link JAXBElement }{@code <}{@link Object }{@code >}
	 * 
	 */
	@XmlElementDecl(namespace = "http://schemas.microsoft.com/2003/10/Serialization/", name = "anyType")
	public JAXBElement<Object> createAnyType(Object value) {
		return new JAXBElement<Object>(_AnyType_QNAME, Object.class, null,
				value);
	}

	/**
	 * Create an instance of {@link JAXBElement }{@code <}{@link Long }{@code >}
	 * 
	 */
	@XmlElementDecl(namespace = "http://schemas.microsoft.com/2003/10/Serialization/", name = "unsignedInt")
	public JAXBElement<Long> createUnsignedInt(Long value) {
		return new JAXBElement<Long>(_UnsignedInt_QNAME, Long.class, null,
				value);
	}

	/**
	 * Create an instance of {@link JAXBElement }{@code <}{@link Integer }{@code >}
	 * 
	 */
	@XmlElementDecl(namespace = "http://schemas.microsoft.com/2003/10/Serialization/", name = "int")
	public JAXBElement<Integer> createInt(Integer value) {
		return new JAXBElement<Integer>(_Int_QNAME, Integer.class, null, value);
	}

	/**
	 * Create an instance of {@link JAXBElement }{@code <}{@link Horario }{@code >}
	 * 
	 */
	@XmlElementDecl(namespace = "http://schemas.datacontract.org/2004/07/wscruzdelsur.Entities", name = "Horario")
	public JAXBElement<Horario> createHorario(Horario value) {
		return new JAXBElement<Horario>(_Horario_QNAME, Horario.class, null,
				value);
	}

	/**
	 * Create an instance of {@link JAXBElement }{@code <}{@link BigDecimal }
	 * {@code >}
	 * 
	 */
	@XmlElementDecl(namespace = "http://schemas.microsoft.com/2003/10/Serialization/", name = "decimal")
	public JAXBElement<BigDecimal> createDecimal(BigDecimal value) {
		return new JAXBElement<BigDecimal>(_Decimal_QNAME, BigDecimal.class,
				null, value);
	}

	/**
	 * Create an instance of {@link JAXBElement }{@code <}{@link Double }{@code >}
	 * 
	 */
	@XmlElementDecl(namespace = "http://schemas.microsoft.com/2003/10/Serialization/", name = "double")
	public JAXBElement<Double> createDouble(Double value) {
		return new JAXBElement<Double>(_Double_QNAME, Double.class, null, value);
	}

	/**
	 * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}
	 * 
	 */
	@XmlElementDecl(namespace = "http://schemas.microsoft.com/2003/10/Serialization/", name = "guid")
	public JAXBElement<String> createGuid(String value) {
		return new JAXBElement<String>(_Guid_QNAME, String.class, null, value);
	}

	/**
	 * Create an instance of {@link JAXBElement }{@code <}{@link Distribucion }
	 * {@code >}
	 * 
	 */
	@XmlElementDecl(namespace = "http://schemas.datacontract.org/2004/07/wscruzdelsur.Entities", name = "Distribucion")
	public JAXBElement<Distribucion> createDistribucion(Distribucion value) {
		return new JAXBElement<Distribucion>(_Distribucion_QNAME,
				Distribucion.class, null, value);
	}

	/**
	 * Create an instance of {@link JAXBElement }{@code <}{@link Duration }
	 * {@code >}
	 * 
	 */
	@XmlElementDecl(namespace = "http://schemas.microsoft.com/2003/10/Serialization/", name = "duration")
	public JAXBElement<Duration> createDuration(Duration value) {
		return new JAXBElement<Duration>(_Duration_QNAME, Duration.class, null,
				value);
	}

	/**
	 * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}
	 * 
	 */
	@XmlElementDecl(namespace = "http://schemas.microsoft.com/2003/10/Serialization/", name = "string")
	public JAXBElement<String> createString(String value) {
		return new JAXBElement<String>(_String_QNAME, String.class, null, value);
	}

	/**
	 * Create an instance of {@link JAXBElement }{@code <}{@link BigInteger }
	 * {@code >}
	 * 
	 */
	@XmlElementDecl(namespace = "http://schemas.microsoft.com/2003/10/Serialization/", name = "unsignedLong")
	public JAXBElement<BigInteger> createUnsignedLong(BigInteger value) {
		return new JAXBElement<BigInteger>(_UnsignedLong_QNAME,
				BigInteger.class, null, value);
	}

	/**
	 * Create an instance of {@link JAXBElement }{@code <}
	 * {@link ResultAnularBoleto }{@code >}
	 * 
	 */
	@XmlElementDecl(namespace = "http://tempuri.org/", name = "anularBoletoResult", scope = AnularBoletoResponse.class)
	public JAXBElement<ResultAnularBoleto> createAnularBoletoResponseAnularBoletoResult(
			ResultAnularBoleto value) {
		return new JAXBElement<ResultAnularBoleto>(
				_AnularBoletoResponseAnularBoletoResult_QNAME,
				ResultAnularBoleto.class, AnularBoletoResponse.class, value);
	}

	/**
	 * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}
	 * 
	 */
	@XmlElementDecl(namespace = "http://schemas.datacontract.org/2004/07/wscruzdelsur.Entities", name = "agenciaEmbarque2", scope = Horario.class)
	public JAXBElement<String> createHorarioAgenciaEmbarque2(String value) {
		return new JAXBElement<String>(_HorarioAgenciaEmbarque2_QNAME,
				String.class, Horario.class, value);
	}

	/**
	 * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}
	 * 
	 */
	@XmlElementDecl(namespace = "http://schemas.datacontract.org/2004/07/wscruzdelsur.Entities", name = "servicio", scope = Horario.class)
	public JAXBElement<String> createHorarioServicio(String value) {
		return new JAXBElement<String>(_HorarioServicio_QNAME, String.class,
				Horario.class, value);
	}

	/**
	 * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}
	 * 
	 */
	@XmlElementDecl(namespace = "http://schemas.datacontract.org/2004/07/wscruzdelsur.Entities", name = "direccionEmbarque2", scope = Horario.class)
	public JAXBElement<String> createHorarioDireccionEmbarque2(String value) {
		return new JAXBElement<String>(_HorarioDireccionEmbarque2_QNAME,
				String.class, Horario.class, value);
	}

	/**
	 * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}
	 * 
	 */
	@XmlElementDecl(namespace = "http://schemas.datacontract.org/2004/07/wscruzdelsur.Entities", name = "telefonoDesembarque1", scope = Horario.class)
	public JAXBElement<String> createHorarioTelefonoDesembarque1(String value) {
		return new JAXBElement<String>(_HorarioTelefonoDesembarque1_QNAME,
				String.class, Horario.class, value);
	}

	/**
	 * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}
	 * 
	 */
	@XmlElementDecl(namespace = "http://schemas.datacontract.org/2004/07/wscruzdelsur.Entities", name = "telefonoDesembarque2", scope = Horario.class)
	public JAXBElement<String> createHorarioTelefonoDesembarque2(String value) {
		return new JAXBElement<String>(_HorarioTelefonoDesembarque2_QNAME,
				String.class, Horario.class, value);
	}

	/**
	 * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}
	 * 
	 */
	@XmlElementDecl(namespace = "http://schemas.datacontract.org/2004/07/wscruzdelsur.Entities", name = "direccionEmbarque1", scope = Horario.class)
	public JAXBElement<String> createHorarioDireccionEmbarque1(String value) {
		return new JAXBElement<String>(_HorarioDireccionEmbarque1_QNAME,
				String.class, Horario.class, value);
	}

	/**
	 * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}
	 * 
	 */
	@XmlElementDecl(namespace = "http://schemas.datacontract.org/2004/07/wscruzdelsur.Entities", name = "unidadLlave", scope = Horario.class)
	public JAXBElement<String> createHorarioUnidadLlave(String value) {
		return new JAXBElement<String>(_HorarioUnidadLlave_QNAME, String.class,
				Horario.class, value);
	}

	/**
	 * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}
	 * 
	 */
	@XmlElementDecl(namespace = "http://schemas.datacontract.org/2004/07/wscruzdelsur.Entities", name = "rutaInternacional", scope = Horario.class)
	public JAXBElement<String> createHorarioRutaInternacional(String value) {
		return new JAXBElement<String>(_HorarioRutaInternacional_QNAME,
				String.class, Horario.class, value);
	}

	/**
	 * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}
	 * 
	 */
	@XmlElementDecl(namespace = "http://schemas.datacontract.org/2004/07/wscruzdelsur.Entities", name = "aplicarPromocion", scope = Horario.class)
	public JAXBElement<String> createHorarioAplicarPromocion(String value) {
		return new JAXBElement<String>(_HorarioAplicarPromocion_QNAME,
				String.class, Horario.class, value);
	}

	/**
	 * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}
	 * 
	 */
	@XmlElementDecl(namespace = "http://schemas.datacontract.org/2004/07/wscruzdelsur.Entities", name = "agenciaEmbarque1", scope = Horario.class)
	public JAXBElement<String> createHorarioAgenciaEmbarque1(String value) {
		return new JAXBElement<String>(_HorarioAgenciaEmbarque1_QNAME,
				String.class, Horario.class, value);
	}

	/**
	 * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}
	 * 
	 */
	@XmlElementDecl(namespace = "http://schemas.datacontract.org/2004/07/wscruzdelsur.Entities", name = "servicioLlave", scope = Horario.class)
	public JAXBElement<String> createHorarioServicioLlave(String value) {
		return new JAXBElement<String>(_HorarioServicioLlave_QNAME,
				String.class, Horario.class, value);
	}

	/**
	 * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}
	 * 
	 */
	@XmlElementDecl(namespace = "http://schemas.datacontract.org/2004/07/wscruzdelsur.Entities", name = "codigoRuta", scope = Horario.class)
	public JAXBElement<String> createHorarioCodigoRuta(String value) {
		return new JAXBElement<String>(_HorarioCodigoRuta_QNAME, String.class,
				Horario.class, value);
	}

	/**
	 * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}
	 * 
	 */
	@XmlElementDecl(namespace = "http://schemas.datacontract.org/2004/07/wscruzdelsur.Entities", name = "telefonoEmbarque2", scope = Horario.class)
	public JAXBElement<String> createHorarioTelefonoEmbarque2(String value) {
		return new JAXBElement<String>(_HorarioTelefonoEmbarque2_QNAME,
				String.class, Horario.class, value);
	}

	/**
	 * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}
	 * 
	 */
	@XmlElementDecl(namespace = "http://schemas.datacontract.org/2004/07/wscruzdelsur.Entities", name = "agenciaDesembarque2", scope = Horario.class)
	public JAXBElement<String> createHorarioAgenciaDesembarque2(String value) {
		return new JAXBElement<String>(_HorarioAgenciaDesembarque2_QNAME,
				String.class, Horario.class, value);
	}

	/**
	 * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}
	 * 
	 */
	@XmlElementDecl(namespace = "http://schemas.datacontract.org/2004/07/wscruzdelsur.Entities", name = "telefonoEmbarque1", scope = Horario.class)
	public JAXBElement<String> createHorarioTelefonoEmbarque1(String value) {
		return new JAXBElement<String>(_HorarioTelefonoEmbarque1_QNAME,
				String.class, Horario.class, value);
	}

	/**
	 * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}
	 * 
	 */
	@XmlElementDecl(namespace = "http://schemas.datacontract.org/2004/07/wscruzdelsur.Entities", name = "agenciaDesembarque1", scope = Horario.class)
	public JAXBElement<String> createHorarioAgenciaDesembarque1(String value) {
		return new JAXBElement<String>(_HorarioAgenciaDesembarque1_QNAME,
				String.class, Horario.class, value);
	}

	/**
	 * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}
	 * 
	 */
	@XmlElementDecl(namespace = "http://schemas.datacontract.org/2004/07/wscruzdelsur.Entities", name = "agenciaEmbarqueLlave2", scope = Horario.class)
	public JAXBElement<String> createHorarioAgenciaEmbarqueLlave2(String value) {
		return new JAXBElement<String>(_HorarioAgenciaEmbarqueLlave2_QNAME,
				String.class, Horario.class, value);
	}

	/**
	 * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}
	 * 
	 */
	@XmlElementDecl(namespace = "http://schemas.datacontract.org/2004/07/wscruzdelsur.Entities", name = "fechaHoraEmbarque1", scope = Horario.class)
	public JAXBElement<String> createHorarioFechaHoraEmbarque1(String value) {
		return new JAXBElement<String>(_HorarioFechaHoraEmbarque1_QNAME,
				String.class, Horario.class, value);
	}

	/**
	 * Create an instance of {@link JAXBElement }{@code <}{@link ArrayOfGeneric }
	 * {@code >}
	 * 
	 */
	@XmlElementDecl(namespace = "http://schemas.datacontract.org/2004/07/wscruzdelsur.Entities", name = "menus", scope = Horario.class)
	public JAXBElement<ArrayOfGeneric> createHorarioMenus(ArrayOfGeneric value) {
		return new JAXBElement<ArrayOfGeneric>(_HorarioMenus_QNAME,
				ArrayOfGeneric.class, Horario.class, value);
	}

	/**
	 * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}
	 * 
	 */
	@XmlElementDecl(namespace = "http://schemas.datacontract.org/2004/07/wscruzdelsur.Entities", name = "fechaHoraEmbarque2", scope = Horario.class)
	public JAXBElement<String> createHorarioFechaHoraEmbarque2(String value) {
		return new JAXBElement<String>(_HorarioFechaHoraEmbarque2_QNAME,
				String.class, Horario.class, value);
	}

	/**
	 * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}
	 * 
	 */
	@XmlElementDecl(namespace = "http://schemas.datacontract.org/2004/07/wscruzdelsur.Entities", name = "direccionDesembarque2", scope = Horario.class)
	public JAXBElement<String> createHorarioDireccionDesembarque2(String value) {
		return new JAXBElement<String>(_HorarioDireccionDesembarque2_QNAME,
				String.class, Horario.class, value);
	}

	/**
	 * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}
	 * 
	 */
	@XmlElementDecl(namespace = "http://schemas.datacontract.org/2004/07/wscruzdelsur.Entities", name = "agenciaEmbarqueLlave1", scope = Horario.class)
	public JAXBElement<String> createHorarioAgenciaEmbarqueLlave1(String value) {
		return new JAXBElement<String>(_HorarioAgenciaEmbarqueLlave1_QNAME,
				String.class, Horario.class, value);
	}

	/**
	 * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}
	 * 
	 */
	@XmlElementDecl(namespace = "http://schemas.datacontract.org/2004/07/wscruzdelsur.Entities", name = "fechaHoraDesembarque2", scope = Horario.class)
	public JAXBElement<String> createHorarioFechaHoraDesembarque2(String value) {
		return new JAXBElement<String>(_HorarioFechaHoraDesembarque2_QNAME,
				String.class, Horario.class, value);
	}

	/**
	 * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}
	 * 
	 */
	@XmlElementDecl(namespace = "http://schemas.datacontract.org/2004/07/wscruzdelsur.Entities", name = "programacionLlave", scope = Horario.class)
	public JAXBElement<String> createHorarioProgramacionLlave(String value) {
		return new JAXBElement<String>(_HorarioProgramacionLlave_QNAME,
				String.class, Horario.class, value);
	}

	/**
	 * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}
	 * 
	 */
	@XmlElementDecl(namespace = "http://schemas.datacontract.org/2004/07/wscruzdelsur.Entities", name = "tiempoViaje", scope = Horario.class)
	public JAXBElement<String> createHorarioTiempoViaje(String value) {
		return new JAXBElement<String>(_HorarioTiempoViaje_QNAME, String.class,
				Horario.class, value);
	}

	/**
	 * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}
	 * 
	 */
	@XmlElementDecl(namespace = "http://schemas.datacontract.org/2004/07/wscruzdelsur.Entities", name = "direccionDesembarque1", scope = Horario.class)
	public JAXBElement<String> createHorarioDireccionDesembarque1(String value) {
		return new JAXBElement<String>(_HorarioDireccionDesembarque1_QNAME,
				String.class, Horario.class, value);
	}

	/**
	 * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}
	 * 
	 */
	@XmlElementDecl(namespace = "http://schemas.datacontract.org/2004/07/wscruzdelsur.Entities", name = "fechaHoraDesembarque1", scope = Horario.class)
	public JAXBElement<String> createHorarioFechaHoraDesembarque1(String value) {
		return new JAXBElement<String>(_HorarioFechaHoraDesembarque1_QNAME,
				String.class, Horario.class, value);
	}

	/**
	 * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}
	 * 
	 */
	@XmlElementDecl(namespace = "http://schemas.datacontract.org/2004/07/wscruzdelsur.Entities", name = "ruta", scope = Horario.class)
	public JAXBElement<String> createHorarioRuta(String value) {
		return new JAXBElement<String>(_HorarioRuta_QNAME, String.class,
				Horario.class, value);
	}

	/**
	 * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}
	 * 
	 */
	@XmlElementDecl(namespace = "http://schemas.datacontract.org/2004/07/wscruzdelsur.Entities", name = "agenciaDesembarqueLlave1", scope = Horario.class)
	public JAXBElement<String> createHorarioAgenciaDesembarqueLlave1(
			String value) {
		return new JAXBElement<String>(_HorarioAgenciaDesembarqueLlave1_QNAME,
				String.class, Horario.class, value);
	}

	/**
	 * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}
	 * 
	 */
	@XmlElementDecl(namespace = "http://schemas.datacontract.org/2004/07/wscruzdelsur.Entities", name = "numeroServicio", scope = Horario.class)
	public JAXBElement<String> createHorarioNumeroServicio(String value) {
		return new JAXBElement<String>(_HorarioNumeroServicio_QNAME,
				String.class, Horario.class, value);
	}

	/**
	 * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}
	 * 
	 */
	@XmlElementDecl(namespace = "http://schemas.datacontract.org/2004/07/wscruzdelsur.Entities", name = "agenciaDesembarqueLlave2", scope = Horario.class)
	public JAXBElement<String> createHorarioAgenciaDesembarqueLlave2(
			String value) {
		return new JAXBElement<String>(_HorarioAgenciaDesembarqueLlave2_QNAME,
				String.class, Horario.class, value);
	}

	/**
	 * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}
	 * 
	 */
	@XmlElementDecl(namespace = "http://schemas.datacontract.org/2004/07/wscruzdelsur.Entities", name = "nombres", scope = Pasajero.class)
	public JAXBElement<String> createPasajeroNombres(String value) {
		return new JAXBElement<String>(_PasajeroNombres_QNAME, String.class,
				Pasajero.class, value);
	}

	/**
	 * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}
	 * 
	 */
	@XmlElementDecl(namespace = "http://schemas.datacontract.org/2004/07/wscruzdelsur.Entities", name = "numeroBoleto", scope = Pasajero.class)
	public JAXBElement<String> createPasajeroNumeroBoleto(String value) {
		return new JAXBElement<String>(_PasajeroNumeroBoleto_QNAME,
				String.class, Pasajero.class, value);
	}

	/**
	 * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}
	 * 
	 */
	@XmlElementDecl(namespace = "http://schemas.datacontract.org/2004/07/wscruzdelsur.Entities", name = "email", scope = Pasajero.class)
	public JAXBElement<String> createPasajeroEmail(String value) {
		return new JAXBElement<String>(_PasajeroEmail_QNAME, String.class,
				Pasajero.class, value);
	}

	/**
	 * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}
	 * 
	 */
	@XmlElementDecl(namespace = "http://schemas.datacontract.org/2004/07/wscruzdelsur.Entities", name = "codigoNacionalidad", scope = Pasajero.class)
	public JAXBElement<String> createPasajeroCodigoNacionalidad(String value) {
		return new JAXBElement<String>(_PasajeroCodigoNacionalidad_QNAME,
				String.class, Pasajero.class, value);
	}

	/**
	 * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}
	 * 
	 */
	@XmlElementDecl(namespace = "http://schemas.datacontract.org/2004/07/wscruzdelsur.Entities", name = "apellidoMaterno", scope = Pasajero.class)
	public JAXBElement<String> createPasajeroApellidoMaterno(String value) {
		return new JAXBElement<String>(_PasajeroApellidoMaterno_QNAME,
				String.class, Pasajero.class, value);
	}

	/**
	 * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}
	 * 
	 */
	@XmlElementDecl(namespace = "http://schemas.datacontract.org/2004/07/wscruzdelsur.Entities", name = "RazonSocial", scope = Pasajero.class)
	public JAXBElement<String> createPasajeroRazonSocial(String value) {
		return new JAXBElement<String>(_PasajeroRazonSocial_QNAME,
				String.class, Pasajero.class, value);
	}

	/**
	 * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}
	 * 
	 */
	@XmlElementDecl(namespace = "http://schemas.datacontract.org/2004/07/wscruzdelsur.Entities", name = "tipoTarifa", scope = Pasajero.class)
	public JAXBElement<String> createPasajeroTipoTarifa(String value) {
		return new JAXBElement<String>(_PasajeroTipoTarifa_QNAME, String.class,
				Pasajero.class, value);
	}

	/**
	 * Create an instance of {@link JAXBElement }{@code <}{@link ArrayOfGeneric }
	 * {@code >}
	 * 
	 */
	@XmlElementDecl(namespace = "http://schemas.datacontract.org/2004/07/wscruzdelsur.Entities", name = "menus", scope = Pasajero.class)
	public JAXBElement<ArrayOfGeneric> createPasajeroMenus(ArrayOfGeneric value) {
		return new JAXBElement<ArrayOfGeneric>(_HorarioMenus_QNAME,
				ArrayOfGeneric.class, Pasajero.class, value);
	}

	/**
	 * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}
	 * 
	 */
	@XmlElementDecl(namespace = "http://schemas.datacontract.org/2004/07/wscruzdelsur.Entities", name = "numeroDocumento", scope = Pasajero.class)
	public JAXBElement<String> createPasajeroNumeroDocumento(String value) {
		return new JAXBElement<String>(_PasajeroNumeroDocumento_QNAME,
				String.class, Pasajero.class, value);
	}

	/**
	 * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}
	 * 
	 */
	@XmlElementDecl(namespace = "http://schemas.datacontract.org/2004/07/wscruzdelsur.Entities", name = "RucEmpresa", scope = Pasajero.class)
	public JAXBElement<String> createPasajeroRucEmpresa(String value) {
		return new JAXBElement<String>(_PasajeroRucEmpresa_QNAME, String.class,
				Pasajero.class, value);
	}

	/**
	 * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}
	 * 
	 */
	@XmlElementDecl(namespace = "http://schemas.datacontract.org/2004/07/wscruzdelsur.Entities", name = "apellidoPaterno", scope = Pasajero.class)
	public JAXBElement<String> createPasajeroApellidoPaterno(String value) {
		return new JAXBElement<String>(_PasajeroApellidoPaterno_QNAME,
				String.class, Pasajero.class, value);
	}

	/**
	 * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}
	 * 
	 */
	@XmlElementDecl(namespace = "http://schemas.datacontract.org/2004/07/wscruzdelsur.Entities", name = "telefono", scope = Pasajero.class)
	public JAXBElement<String> createPasajeroTelefono(String value) {
		return new JAXBElement<String>(_PasajeroTelefono_QNAME, String.class,
				Pasajero.class, value);
	}

	/**
	 * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}
	 * 
	 */
	@XmlElementDecl(namespace = "http://schemas.datacontract.org/2004/07/wscruzdelsur.Entities", name = "fechaNacimiento", scope = Pasajero.class)
	public JAXBElement<String> createPasajeroFechaNacimiento(String value) {
		return new JAXBElement<String>(_PasajeroFechaNacimiento_QNAME,
				String.class, Pasajero.class, value);
	}

	/**
	 * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}
	 * 
	 */
	@XmlElementDecl(namespace = "http://schemas.datacontract.org/2004/07/wscruzdelsur.Entities", name = "codigoTipoDocumento", scope = Pasajero.class)
	public JAXBElement<String> createPasajeroCodigoTipoDocumento(String value) {
		return new JAXBElement<String>(_PasajeroCodigoTipoDocumento_QNAME,
				String.class, Pasajero.class, value);
	}

	/**
	 * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}
	 * 
	 */
	@XmlElementDecl(namespace = "http://tempuri.org/", name = "localidadDesembarque", scope = ObtenerHorarios.class)
	public JAXBElement<String> createObtenerHorariosLocalidadDesembarque(
			String value) {
		return new JAXBElement<String>(
				_ObtenerHorariosLocalidadDesembarque_QNAME, String.class,
				ObtenerHorarios.class, value);
	}

	/**
	 * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}
	 * 
	 */
	@XmlElementDecl(namespace = "http://tempuri.org/", name = "fechaProgramacion", scope = ObtenerHorarios.class)
	public JAXBElement<String> createObtenerHorariosFechaProgramacion(
			String value) {
		return new JAXBElement<String>(_ObtenerHorariosFechaProgramacion_QNAME,
				String.class, ObtenerHorarios.class, value);
	}

	/**
	 * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}
	 * 
	 */
	@XmlElementDecl(namespace = "http://tempuri.org/", name = "localidadEmbarque", scope = ObtenerHorarios.class)
	public JAXBElement<String> createObtenerHorariosLocalidadEmbarque(
			String value) {
		return new JAXBElement<String>(_ObtenerHorariosLocalidadEmbarque_QNAME,
				String.class, ObtenerHorarios.class, value);
	}

	/**
	 * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}
	 * 
	 */
	@XmlElementDecl(namespace = "http://schemas.datacontract.org/2004/07/wscruzdelsur.Entities", name = "message", scope = Error.class)
	public JAXBElement<String> createErrorMessage(String value) {
		return new JAXBElement<String>(_ErrorMessage_QNAME, String.class,
				Error.class, value);
	}

	/**
	 * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}
	 * 
	 */
	@XmlElementDecl(namespace = "http://schemas.datacontract.org/2004/07/wscruzdelsur.Entities", name = "code", scope = Error.class)
	public JAXBElement<String> createErrorCode(String value) {
		return new JAXBElement<String>(_ErrorCode_QNAME, String.class,
				Error.class, value);
	}

	/**
	 * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}
	 * 
	 */
	@XmlElementDecl(namespace = "http://schemas.datacontract.org/2004/07/wscruzdelsur.Entities", name = "resultadoTransaccion", scope = ResultBloquearAsiento.class)
	public JAXBElement<String> createResultBloquearAsientoResultadoTransaccion(
			String value) {
		return new JAXBElement<String>(
				_ResultBloquearAsientoResultadoTransaccion_QNAME, String.class,
				ResultBloquearAsiento.class, value);
	}

	/**
	 * Create an instance of {@link JAXBElement }{@code <}{@link Error }{@code >}
	 * 
	 */
	@XmlElementDecl(namespace = "http://schemas.datacontract.org/2004/07/wscruzdelsur.Entities", name = "error", scope = ResultBloquearAsiento.class)
	public JAXBElement<Error> createResultBloquearAsientoError(Error value) {
		return new JAXBElement<Error>(_ResultAnularBoletoError_QNAME,
				Error.class, ResultBloquearAsiento.class, value);
	}

	/**
	 * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}
	 * 
	 */
	@XmlElementDecl(namespace = "http://schemas.datacontract.org/2004/07/wscruzdelsur.Entities", name = "codigoTransaccion", scope = ResultBloquearAsiento.class)
	public JAXBElement<String> createResultBloquearAsientoCodigoTransaccion(
			String value) {
		return new JAXBElement<String>(
				_ResultBloquearAsientoCodigoTransaccion_QNAME, String.class,
				ResultBloquearAsiento.class, value);
	}

	/**
	 * Create an instance of {@link JAXBElement }{@code <}{@link ArrayOfGeneric }
	 * {@code >}
	 * 
	 */
	@XmlElementDecl(namespace = "http://schemas.datacontract.org/2004/07/wscruzdelsur.Entities", name = "listGeneric", scope = Generic.class)
	public JAXBElement<ArrayOfGeneric> createGenericListGeneric(
			ArrayOfGeneric value) {
		return new JAXBElement<ArrayOfGeneric>(_GenericListGeneric_QNAME,
				ArrayOfGeneric.class, Generic.class, value);
	}

	/**
	 * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}
	 * 
	 */
	@XmlElementDecl(namespace = "http://schemas.datacontract.org/2004/07/wscruzdelsur.Entities", name = "value", scope = Generic.class)
	public JAXBElement<String> createGenericValue(String value) {
		return new JAXBElement<String>(_GenericValue_QNAME, String.class,
				Generic.class, value);
	}

	/**
	 * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}
	 * 
	 */
	@XmlElementDecl(namespace = "http://schemas.datacontract.org/2004/07/wscruzdelsur.Entities", name = "key", scope = Generic.class)
	public JAXBElement<String> createGenericKey(String value) {
		return new JAXBElement<String>(_GenericKey_QNAME, String.class,
				Generic.class, value);
	}

	/**
	 * Create an instance of {@link JAXBElement }{@code <}{@link ResultVenta }
	 * {@code >}
	 * 
	 */
	@XmlElementDecl(namespace = "http://tempuri.org/", name = "venderPasajeResult", scope = VenderPasajeResponse.class)
	public JAXBElement<ResultVenta> createVenderPasajeResponseVenderPasajeResult(
			ResultVenta value) {
		return new JAXBElement<ResultVenta>(
				_VenderPasajeResponseVenderPasajeResult_QNAME,
				ResultVenta.class, VenderPasajeResponse.class, value);
	}

	/**
	 * Create an instance of {@link JAXBElement }{@code <}
	 * {@link ArrayOfDistribucion }{@code >}
	 * 
	 */
	@XmlElementDecl(namespace = "http://tempuri.org/", name = "obtenerAsientosDisponiblesResult", scope = ObtenerAsientosDisponiblesResponse.class)
	public JAXBElement<ArrayOfDistribucion> createObtenerAsientosDisponiblesResponseObtenerAsientosDisponiblesResult(
			ArrayOfDistribucion value) {
		return new JAXBElement<ArrayOfDistribucion>(
				_ObtenerAsientosDisponiblesResponseObtenerAsientosDisponiblesResult_QNAME,
				ArrayOfDistribucion.class,
				ObtenerAsientosDisponiblesResponse.class, value);
	}

	/**
	 * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}
	 * 
	 */
	@XmlElementDecl(namespace = "http://tempuri.org/", name = "agenciaEmbarqueLlave", scope = VenderPasaje.class)
	public JAXBElement<String> createVenderPasajeAgenciaEmbarqueLlave(
			String value) {
		return new JAXBElement<String>(_VenderPasajeAgenciaEmbarqueLlave_QNAME,
				String.class, VenderPasaje.class, value);
	}

	/**
	 * Create an instance of {@link JAXBElement }{@code <}{@link Pasajero }
	 * {@code >}
	 * 
	 */
	@XmlElementDecl(namespace = "http://tempuri.org/", name = "pasajero", scope = VenderPasaje.class)
	public JAXBElement<Pasajero> createVenderPasajePasajero(Pasajero value) {
		return new JAXBElement<Pasajero>(_VenderPasajePasajero_QNAME,
				Pasajero.class, VenderPasaje.class, value);
	}

	/**
	 * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}
	 * 
	 */
	@XmlElementDecl(namespace = "http://tempuri.org/", name = "agenciaDesembarqueLlave", scope = VenderPasaje.class)
	public JAXBElement<String> createVenderPasajeAgenciaDesembarqueLlave(
			String value) {
		return new JAXBElement<String>(
				_VenderPasajeAgenciaDesembarqueLlave_QNAME, String.class,
				VenderPasaje.class, value);
	}

	/**
	 * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}
	 * 
	 */
	@XmlElementDecl(namespace = "http://tempuri.org/", name = "codigoTransaccion", scope = VenderPasaje.class)
	public JAXBElement<String> createVenderPasajeCodigoTransaccion(String value) {
		return new JAXBElement<String>(_VenderPasajeCodigoTransaccion_QNAME,
				String.class, VenderPasaje.class, value);
	}

	/**
	 * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}
	 * 
	 */
	@XmlElementDecl(namespace = "http://tempuri.org/", name = "programacionLlave", scope = ObtenerAsientosDisponibles.class)
	public JAXBElement<String> createObtenerAsientosDisponiblesProgramacionLlave(
			String value) {
		return new JAXBElement<String>(
				_ObtenerAsientosDisponiblesProgramacionLlave_QNAME,
				String.class, ObtenerAsientosDisponibles.class, value);
	}

	/**
	 * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}
	 * 
	 */
	@XmlElementDecl(namespace = "http://tempuri.org/", name = "servicioLlave", scope = ObtenerAsientosDisponibles.class)
	public JAXBElement<String> createObtenerAsientosDisponiblesServicioLlave(
			String value) {
		return new JAXBElement<String>(
				_ObtenerAsientosDisponiblesServicioLlave_QNAME, String.class,
				ObtenerAsientosDisponibles.class, value);
	}

	/**
	 * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}
	 * 
	 */
	@XmlElementDecl(namespace = "http://tempuri.org/", name = "agenciaEmbarqueLlave", scope = ObtenerAsientosDisponibles.class)
	public JAXBElement<String> createObtenerAsientosDisponiblesAgenciaEmbarqueLlave(
			String value) {
		return new JAXBElement<String>(_VenderPasajeAgenciaEmbarqueLlave_QNAME,
				String.class, ObtenerAsientosDisponibles.class, value);
	}

	/**
	 * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}
	 * 
	 */
	@XmlElementDecl(namespace = "http://tempuri.org/", name = "codigoRuta", scope = ObtenerAsientosDisponibles.class)
	public JAXBElement<String> createObtenerAsientosDisponiblesCodigoRuta(
			String value) {
		return new JAXBElement<String>(
				_ObtenerAsientosDisponiblesCodigoRuta_QNAME, String.class,
				ObtenerAsientosDisponibles.class, value);
	}

	/**
	 * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}
	 * 
	 */
	@XmlElementDecl(namespace = "http://tempuri.org/", name = "agenciaDesembarqueLlave", scope = ObtenerAsientosDisponibles.class)
	public JAXBElement<String> createObtenerAsientosDisponiblesAgenciaDesembarqueLlave(
			String value) {
		return new JAXBElement<String>(
				_VenderPasajeAgenciaDesembarqueLlave_QNAME, String.class,
				ObtenerAsientosDisponibles.class, value);
	}

	/**
	 * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}
	 * 
	 */
	@XmlElementDecl(namespace = "http://tempuri.org/", name = "unidadLlave", scope = ObtenerAsientosDisponibles.class)
	public JAXBElement<String> createObtenerAsientosDisponiblesUnidadLlave(
			String value) {
		return new JAXBElement<String>(
				_ObtenerAsientosDisponiblesUnidadLlave_QNAME, String.class,
				ObtenerAsientosDisponibles.class, value);
	}

	/**
	 * Create an instance of {@link JAXBElement }{@code <}{@link ArrayOfHorario }
	 * {@code >}
	 * 
	 */
	@XmlElementDecl(namespace = "http://tempuri.org/", name = "obtenerHorariosResult", scope = ObtenerHorariosResponse.class)
	public JAXBElement<ArrayOfHorario> createObtenerHorariosResponseObtenerHorariosResult(
			ArrayOfHorario value) {
		return new JAXBElement<ArrayOfHorario>(
				_ObtenerHorariosResponseObtenerHorariosResult_QNAME,
				ArrayOfHorario.class, ObtenerHorariosResponse.class, value);
	}

	/**
	 * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}
	 * 
	 */
	@XmlElementDecl(namespace = "http://schemas.datacontract.org/2004/07/wscruzdelsur.Entities", name = "totalNivel", scope = Distribucion.class)
	public JAXBElement<String> createDistribucionTotalNivel(String value) {
		return new JAXBElement<String>(_DistribucionTotalNivel_QNAME,
				String.class, Distribucion.class, value);
	}

	/**
	 * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}
	 * 
	 */
	@XmlElementDecl(namespace = "http://schemas.datacontract.org/2004/07/wscruzdelsur.Entities", name = "elemento", scope = Distribucion.class)
	public JAXBElement<String> createDistribucionElemento(String value) {
		return new JAXBElement<String>(_DistribucionElemento_QNAME,
				String.class, Distribucion.class, value);
	}

	/**
	 * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}
	 * 
	 */
	@XmlElementDecl(namespace = "http://schemas.datacontract.org/2004/07/wscruzdelsur.Entities", name = "descripcion", scope = Distribucion.class)
	public JAXBElement<String> createDistribucionDescripcion(String value) {
		return new JAXBElement<String>(_DistribucionDescripcion_QNAME,
				String.class, Distribucion.class, value);
	}

	/**
	 * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}
	 * 
	 */
	@XmlElementDecl(namespace = "http://schemas.datacontract.org/2004/07/wscruzdelsur.Entities", name = "asiento", scope = Distribucion.class)
	public JAXBElement<String> createDistribucionAsiento(String value) {
		return new JAXBElement<String>(_DistribucionAsiento_QNAME,
				String.class, Distribucion.class, value);
	}

	/**
	 * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}
	 * 
	 */
	@XmlElementDecl(namespace = "http://schemas.datacontract.org/2004/07/wscruzdelsur.Entities", name = "nivelPiso", scope = Distribucion.class)
	public JAXBElement<String> createDistribucionNivelPiso(String value) {
		return new JAXBElement<String>(_DistribucionNivelPiso_QNAME,
				String.class, Distribucion.class, value);
	}

	/**
	 * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}
	 * 
	 */
	@XmlElementDecl(namespace = "http://schemas.datacontract.org/2004/07/wscruzdelsur.Entities", name = "tarifaDescripcion", scope = Distribucion.class)
	public JAXBElement<String> createDistribucionTarifaDescripcion(String value) {
		return new JAXBElement<String>(_DistribucionTarifaDescripcion_QNAME,
				String.class, Distribucion.class, value);
	}

	/**
	 * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}
	 * 
	 */
	@XmlElementDecl(namespace = "http://schemas.datacontract.org/2004/07/wscruzdelsur.Entities", name = "nivelUnidad", scope = ResultDesbloquearAsiento.class)
	public JAXBElement<String> createResultDesbloquearAsientoNivelUnidad(
			String value) {
		return new JAXBElement<String>(
				_ResultDesbloquearAsientoNivelUnidad_QNAME, String.class,
				ResultDesbloquearAsiento.class, value);
	}

	/**
	 * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}
	 * 
	 */
	@XmlElementDecl(namespace = "http://schemas.datacontract.org/2004/07/wscruzdelsur.Entities", name = "resultadoTransaccion", scope = ResultDesbloquearAsiento.class)
	public JAXBElement<String> createResultDesbloquearAsientoResultadoTransaccion(
			String value) {
		return new JAXBElement<String>(
				_ResultBloquearAsientoResultadoTransaccion_QNAME, String.class,
				ResultDesbloquearAsiento.class, value);
	}

	/**
	 * Create an instance of {@link JAXBElement }{@code <}{@link Error }{@code >}
	 * 
	 */
	@XmlElementDecl(namespace = "http://schemas.datacontract.org/2004/07/wscruzdelsur.Entities", name = "error", scope = ResultDesbloquearAsiento.class)
	public JAXBElement<Error> createResultDesbloquearAsientoError(Error value) {
		return new JAXBElement<Error>(_ResultAnularBoletoError_QNAME,
				Error.class, ResultDesbloquearAsiento.class, value);
	}

	/**
	 * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}
	 * 
	 */
	@XmlElementDecl(namespace = "http://schemas.datacontract.org/2004/07/wscruzdelsur.Entities", name = "codigoAsiento", scope = ResultDesbloquearAsiento.class)
	public JAXBElement<String> createResultDesbloquearAsientoCodigoAsiento(
			String value) {
		return new JAXBElement<String>(
				_ResultDesbloquearAsientoCodigoAsiento_QNAME, String.class,
				ResultDesbloquearAsiento.class, value);
	}

	/**
	 * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}
	 * 
	 */
	@XmlElementDecl(namespace = "http://schemas.datacontract.org/2004/07/wscruzdelsur.Entities", name = "codigoTransaccion", scope = ResultDesbloquearAsiento.class)
	public JAXBElement<String> createResultDesbloquearAsientoCodigoTransaccion(
			String value) {
		return new JAXBElement<String>(
				_ResultBloquearAsientoCodigoTransaccion_QNAME, String.class,
				ResultDesbloquearAsiento.class, value);
	}

	/**
	 * Create an instance of {@link JAXBElement }{@code <}
	 * {@link ResultDesbloquearAsiento }{@code >}
	 * 
	 */
	@XmlElementDecl(namespace = "http://tempuri.org/", name = "desbloquearAsientoResult", scope = DesbloquearAsientoResponse.class)
	public JAXBElement<ResultDesbloquearAsiento> createDesbloquearAsientoResponseDesbloquearAsientoResult(
			ResultDesbloquearAsiento value) {
		return new JAXBElement<ResultDesbloquearAsiento>(
				_DesbloquearAsientoResponseDesbloquearAsientoResult_QNAME,
				ResultDesbloquearAsiento.class,
				DesbloquearAsientoResponse.class, value);
	}

	/**
	 * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}
	 * 
	 */
	@XmlElementDecl(namespace = "http://tempuri.org/", name = "codigoTransaccion", scope = DesbloquearAsiento.class)
	public JAXBElement<String> createDesbloquearAsientoCodigoTransaccion(
			String value) {
		return new JAXBElement<String>(_VenderPasajeCodigoTransaccion_QNAME,
				String.class, DesbloquearAsiento.class, value);
	}

	/**
	 * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}
	 * 
	 */
	@XmlElementDecl(namespace = "http://schemas.datacontract.org/2004/07/wscruzdelsur.Entities", name = "numeroBoleto", scope = ResultVenta.class)
	public JAXBElement<String> createResultVentaNumeroBoleto(String value) {
		return new JAXBElement<String>(_PasajeroNumeroBoleto_QNAME,
				String.class, ResultVenta.class, value);
	}

	/**
	 * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}
	 * 
	 */
	@XmlElementDecl(namespace = "http://schemas.datacontract.org/2004/07/wscruzdelsur.Entities", name = "resultadoTransaccion", scope = ResultVenta.class)
	public JAXBElement<String> createResultVentaResultadoTransaccion(
			String value) {
		return new JAXBElement<String>(
				_ResultBloquearAsientoResultadoTransaccion_QNAME, String.class,
				ResultVenta.class, value);
	}

	/**
	 * Create an instance of {@link JAXBElement }{@code <}{@link Error }{@code >}
	 * 
	 */
	@XmlElementDecl(namespace = "http://schemas.datacontract.org/2004/07/wscruzdelsur.Entities", name = "error", scope = ResultVenta.class)
	public JAXBElement<Error> createResultVentaError(Error value) {
		return new JAXBElement<Error>(_ResultAnularBoletoError_QNAME,
				Error.class, ResultVenta.class, value);
	}

	/**
	 * Create an instance of {@link JAXBElement }{@code <}
	 * {@link ResultBloquearAsiento }{@code >}
	 * 
	 */
	@XmlElementDecl(namespace = "http://tempuri.org/", name = "bloquearAsientoResult", scope = BloquearAsientoResponse.class)
	public JAXBElement<ResultBloquearAsiento> createBloquearAsientoResponseBloquearAsientoResult(
			ResultBloquearAsiento value) {
		return new JAXBElement<ResultBloquearAsiento>(
				_BloquearAsientoResponseBloquearAsientoResult_QNAME,
				ResultBloquearAsiento.class, BloquearAsientoResponse.class,
				value);
	}

	/**
	 * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}
	 * 
	 */
	@XmlElementDecl(namespace = "http://tempuri.org/", name = "programacionLlave", scope = BloquearAsiento.class)
	public JAXBElement<String> createBloquearAsientoProgramacionLlave(
			String value) {
		return new JAXBElement<String>(
				_ObtenerAsientosDisponiblesProgramacionLlave_QNAME,
				String.class, BloquearAsiento.class, value);
	}

	/**
	 * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}
	 * 
	 */
	@XmlElementDecl(namespace = "http://tempuri.org/", name = "localidadDesembarque", scope = BloquearAsiento.class)
	public JAXBElement<String> createBloquearAsientoLocalidadDesembarque(
			String value) {
		return new JAXBElement<String>(
				_ObtenerHorariosLocalidadDesembarque_QNAME, String.class,
				BloquearAsiento.class, value);
	}

	/**
	 * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}
	 * 
	 */
	@XmlElementDecl(namespace = "http://tempuri.org/", name = "nivelUnidad", scope = BloquearAsiento.class)
	public JAXBElement<String> createBloquearAsientoNivelUnidad(String value) {
		return new JAXBElement<String>(_BloquearAsientoNivelUnidad_QNAME,
				String.class, BloquearAsiento.class, value);
	}

	/**
	 * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}
	 * 
	 */
	@XmlElementDecl(namespace = "http://tempuri.org/", name = "servicioLlave", scope = BloquearAsiento.class)
	public JAXBElement<String> createBloquearAsientoServicioLlave(String value) {
		return new JAXBElement<String>(
				_ObtenerAsientosDisponiblesServicioLlave_QNAME, String.class,
				BloquearAsiento.class, value);
	}

	/**
	 * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}
	 * 
	 */
	@XmlElementDecl(namespace = "http://tempuri.org/", name = "codigoRuta", scope = BloquearAsiento.class)
	public JAXBElement<String> createBloquearAsientoCodigoRuta(String value) {
		return new JAXBElement<String>(
				_ObtenerAsientosDisponiblesCodigoRuta_QNAME, String.class,
				BloquearAsiento.class, value);
	}

	/**
	 * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}
	 * 
	 */
	@XmlElementDecl(namespace = "http://tempuri.org/", name = "localidadEmbarque", scope = BloquearAsiento.class)
	public JAXBElement<String> createBloquearAsientoLocalidadEmbarque(
			String value) {
		return new JAXBElement<String>(_ObtenerHorariosLocalidadEmbarque_QNAME,
				String.class, BloquearAsiento.class, value);
	}

	/**
	 * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}
	 * 
	 */
	@XmlElementDecl(namespace = "http://tempuri.org/", name = "numeroServicio", scope = BloquearAsiento.class)
	public JAXBElement<String> createBloquearAsientoNumeroServicio(String value) {
		return new JAXBElement<String>(_BloquearAsientoNumeroServicio_QNAME,
				String.class, BloquearAsiento.class, value);
	}

	/**
	 * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}
	 * 
	 */
	@XmlElementDecl(namespace = "http://tempuri.org/", name = "codigoAsiento", scope = BloquearAsiento.class)
	public JAXBElement<String> createBloquearAsientoCodigoAsiento(String value) {
		return new JAXBElement<String>(_BloquearAsientoCodigoAsiento_QNAME,
				String.class, BloquearAsiento.class, value);
	}

	/**
	 * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}
	 * 
	 */
	@XmlElementDecl(namespace = "http://tempuri.org/", name = "serie", scope = AnularBoleto.class)
	public JAXBElement<String> createAnularBoletoSerie(String value) {
		return new JAXBElement<String>(_AnularBoletoSerie_QNAME, String.class,
				AnularBoleto.class, value);
	}

	/**
	 * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}
	 * 
	 */
	@XmlElementDecl(namespace = "http://tempuri.org/", name = "numero", scope = AnularBoleto.class)
	public JAXBElement<String> createAnularBoletoNumero(String value) {
		return new JAXBElement<String>(_AnularBoletoNumero_QNAME, String.class,
				AnularBoleto.class, value);
	}

}
