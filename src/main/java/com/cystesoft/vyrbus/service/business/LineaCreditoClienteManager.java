package com.cystesoft.vyrbus.service.business;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

import com.cystesoft.vyrbus.model.bean.LineaCreditoCliente;
import com.cystesoft.vyrbus.model.bean.Usuario;
import com.cystesoft.vyrbus.model.bean.UsuarioAprobador;

/**
 * 
 * @author JABANTO
 *
 */
public interface LineaCreditoClienteManager {
	
	/**
	 * Buscar LineaCreditoCliente por estado Registro
	 * @param estado		:Estado registro
	 * @param criterioOrden	: cadena de criterios para el orden de la Data recuperada.
	 * @return
	 */
	public ArrayList<LineaCreditoCliente> buscarPorEstadoRegistro(String estado, String criterioOrden);
	
	/**
	 * Buscar LineaCreditoCliente según un array de criterios
	 * @param criteriosBusqueda : Array de criterios para la busqueda
	 * @param criteriosOrdenar  : Lista de criterios para el orden de la Data recuperada.
	 * @return
	 */
	public ArrayList<LineaCreditoCliente> buscarPorX(TreeMap<String, Object> criteriosBusqueda, List<String> criteriosOrdenar);
	
	/**
	 * Buscar LineaCreditoCliente por ID
	 * @param id :Identificador del linea de credito del cliente
	 * @return
	 */
	public LineaCreditoCliente buscarPorId(Long id);
	
	/**
	 * Guarda LineaCreditoCliente
	 * @param lineaContadoCliente: class LineaCreditoCliente
	 */
	public void guardar(LineaCreditoCliente lineaCreditoCliente);
	
	/**
	 * Actualiza LineaCreditoCliente
	 * @param LineaContadoCliente: Class LineaCreditoCliente
	 */
	public void actualizar(LineaCreditoCliente lineaCreditoCliente);
	
	/**
	 * Inactivar LineaCreditoCliente
	 * @param id : Identificador de LineaCreditoCliente
	 */
	public void inactivar(Long id);
	
	/**
	 * Busca el historial o las solcitudes por Linea de credito pendientes por aprobar.
	 * @param fechaInicio		: Fecha inicio para la busqueda.
	 * @param fechaFin			: Fecha fin para la busqueda.
	 * @param estadoSolicitud	: estdo de sulicitud a buscar.
	 * @param idCliente			: Opsional, Identificador del cliente.
	 * @param UsuarioAprobador	: Opsional, Class usuario Aprobador
	 * @param recu_Historia		: (true) indica que recuperará el historial de solicitudes aprobadas o desaprobadas,
	 * 							  (false) indica que recuperara la solicitudes pendientes de LC por aprobar		
	 * @return
	 */
	public List<LineaCreditoCliente> buscarSolicitudLineaCreditoN2(String fechaInicio, String fechaFin, String estadoSolicitud, Long idCliente,UsuarioAprobador usuarioAprobador, Boolean recu_Historia);
	
	/**
	 * Valida si la solicitud devuelta por el UGA a sido aprobada o no por el UFA
	 * @param idSolicitudCartera : Identificador del la Solicitud cartera
	 * @return (true)la solicitud ya fue aprobada por el UFA; (false) solicitud pendiente por aprobar por el UFA
	 */
	public Boolean validadSolicitudAprobadaN3(Long idSolicitudCartera);
	
	/**
	 * Busca el historial o las solcitudes de credito pendientes por aprobar del Nivel 3.(Corresponde al area Gerencia Comercial)
	 * @param fechaInicio		: Fecha inicio para la busqueda.
	 * @param fechaFin			: Fecha fin para la busqueda.
	 * @param estadoSolicitud	: estdo de sulicitud a buscar.
	 * @param idCliente			: Opsional, Identificador del cliente.
	 * @param UsuarioAprobador  : Opsional, Class usuario Aprobador
	 * @param recu_Historia		: (true) indica que recuperará el historial de solicitudes aprobadas o desaprobadas,
	  							  (false) indica que recuperara la solicitudes pendientes por aprobar el credito.	
	 * @return
	 */
	public List<LineaCreditoCliente> buscarSolicitudLineaCreditoN3(String fechaInicio, String fechaFin, String estadoSolicitud, Long idCliente,UsuarioAprobador usuarioAprobador, Boolean recu_Historia);
	
	/**
	 * Obtiene el saldo para el cliente credito
	 * @param montoAprobado : Monto aprobado para la linea de credito
	 * @param idCliente		: Identificador del Cliente
	 * @return
	 */
	public Double saldo(Double montoAprobado, Long idCliente);
	
	/**
	 * Obtiene informacion de la Linea de Credito Actual del Cliente a consultar.
	 * @param idCliente	: Identidicador del Cliente
	 * @return
	 */
	public LineaCreditoCliente lineaCreditoCliente(Long idCliente);
	
	/**
	 * Obtiene los Clientes Crédito 
	 * @param idCliente		: Opcional, Identificador del Cliente.
	 * @param idFuncionario : Opcional, Identificador del funcionario.
	 * * @param tipoCliente	: nombre del tipo de cliente (Agencia de viajes, corporativo, canje)
	 * @return
	 */
	public List<LineaCreditoCliente> clientesCredito(Long idCliente, Integer idFuncionario, String tipoCliente);

	/**
	 * validando si el cliente tiene o no credito activo.
	 * @param idCliente	: Identificador del Cliente
	 * @return
	 */
	public LineaCreditoCliente validacionCreditoCliente(Long idCliente) throws Exception;
	
	/**
	 * Resta el saldo para el cliente despues de la compra.
	 * @param SaldoActual	: Saldo actual del cliente	
	 * @param monto			: monto a restar del saldo
	 * @param idLineaCreditoCliente : Identificador del Linea de credito del Cliente.
	 */
	public void restarSaldo(Double SaldoActual, Double monto, Long idLineaCreditoCliente)throws Exception;
	/**
	 * Calcula el saldo solo para cuando es una reduccion del la linea de credito
	 * @param nuevaLineaCredito : Monto de la nueva linea de credito
	 * @param rucclienteCredito	: Numero de ruc del cliente Credito
	 * @return Saldo
	 */
	public Double saldobyReduccion(Double nuevaLineaCredito, String rucclienteCredito);
	/**
	 * Actualiza el saldo de la linea de credito ante alguna anulacion. 
	 * @param motoActualizar 	: monto que se va a actualizar
	 * @param rucClienteCredito : Numero de ruc del Cliente al cual se le va a actualizar la linea de credito.
	 * @param aFavor			: Indica si la actualizacion del saldo sera a favor (true) o en controa(false)
	 * @throws Exception
	 */
	public void actualizarSaldo(Double motoActualizar, String rucClienteCredito,Usuario usuario, String ip, Boolean aFavor)throws Exception;
}
