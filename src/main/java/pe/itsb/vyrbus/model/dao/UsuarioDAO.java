/**
 * Proyecto		: SISVYR
 * Sistema		: Sistema de Ventas y Reservas
 * Descripción	: Interfaz que declara todos los metodos que permitiran manipular datos de los usuarios.
 * Autor		: José Sullo Avalos
 * Fecha		: 07/04/2012
 */
package pe.itsb.vyrbus.model.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

import pe.itsb.vyrbus.model.bean.ControlAcceso;
import pe.itsb.vyrbus.model.bean.Usuario;

/**
 * <br>Interfaz que declara todos los metodos que permitiran manipular datos de los usuarios.</br>
 * @author José Sullo Avalos
 * @since JDK1.6
 */
public interface UsuarioDAO extends GenericDAO {
	/**
	 * Busca en la tabla usuarios si el login existe.
	 * @param login	: Nombre o texto a buscar.
	 * @return Devuelve el usuario.
	 * @throws Exception
	 */
	public Usuario buscarUsuarioPorLogin(String login, String estado)throws Exception;
	/**
	 * Busca el usuario por su login y password.
	 * @param login		: Nombre del usuario.
	 * @param password	: Contraseña del usuario.
	 * @return Usuario que coincide con los datos ingresados.
	 * @throws Exception
	 */
	public Usuario buscarUsuarioPorLoginPassword(String login, String password, String estado) throws Exception;
	/**
	 * Busqueda por estado registro
	 * @param estado		:Estado del Registro.
	 * @param criterioOrden	: Criterios para el orden de los datos.
	 * @return
	 */
	public ArrayList<Usuario> buscarPorEstadoRegistro(String estado, String criterioOrden);
	/**
	 * Busqueda por un array de criterios.
	 * @param criteriosBusqueda: Array de criterios de busqueda
	 * @param criteriosOrdenar : Lista de criterios para el Orden de los Datos
	 * @return :
	 */
	public ArrayList<Usuario> buscarPorX(TreeMap<String, Object> criteriosBusqueda, List<String> criteriosOrdenar);
	/**
	 * Busqueda de usuario por id
	 * @param id : Identificador Usuario
	 * @return :
	 */
	public Usuario buscarPorId(Long id);
	/**
	 *
	 * @param
	 */
	public void guardar(Usuario usuario);
	/**
	 *
	 * @param
	 */
	public void actualizar(Usuario usuario);
	/**
	 * Inactivar Usuario
	 * @param id : Identificador del Usuario
	 */
	public void inactivar(Long id);
	/**
	 * Activar Usuario
	 * @param id: Identificador del Usuario
	 */
	public void activar (Long id);

	/**
	 * Busca los usuarios que tengan una liquidacion
	 * @param fechaInicio	: Fecha inicial de la búsqueda.
	 * @param fechaFinal   	: Fecha final de la búsqueda.
	 * @param idAgencia		: Identificador de la agencia.
	 * @param estadoLiquidacion : Estado de la liquidacion.
	 * @return lista de usuarios
	 * @throws Exception
	 */
	public List<Usuario> buscarUsuarioLiquidacion(String fechaInicio, String fechaFinal, Integer idAgencia, Integer estadoLiquidacion) throws Exception;

	/**
	 * Realiza la busqueda de los usuarios de acuerdo a lso criterios enviados
	 * @param campo				: Campo que debera cumplir con los criterios enviados.
	 * @param criterios			: Identificadores de los usuarios separados por comas.
	 * @param criteriosOrdenar	: Criterios a utilizar para ordenar la informacion.
	 * @param estadoRegistro	: Estado de los registros.
	 * @return Lista de Agencias.
	 */
	public List<Usuario> buscarPorX(String campo, Object[] criterios, List<String> criteriosOrdenar, String estadoRegistro) throws Exception;
	/**
	 * Realiza la busqueda del codigo de acceso para permitir ingresar al sistema
	 * @param idUsuario	: Identificador del usuario
	 * @param codigo	: Codigo de acceso a buscar
	 * @param estado	: Estado del registro.
	 * @return
	 * @throws Exception
	 */
	public ControlAcceso buscarCodigoAcceso(int idUsuario, String codigo, String estado) throws Exception;
}
