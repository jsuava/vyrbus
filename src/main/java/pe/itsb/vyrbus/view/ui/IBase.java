/**
 * Proyecto		: SISVYR
 * Sistema		: Sistema de Ventas y Reservas
 * Descripción	:
 * Autor		: jM
 * Fecha		: 20/04/2012
 */
package pe.itsb.vyrbus.view.ui;

import java.util.List;

import org.zkoss.zk.ui.Component;

import pe.itsb.vyrbus.model.bean.Rol;

/**
 * Interface con los metodos para la clase {@link WndBase}
 *
 * @author jM
 * @since JDK1.6
 * @see WndBase
 */
public interface IBase {

	/**
	 * evento ZK Framework
	 */
	public void onCreate()throws Exception;

	/**
	 * Metodo donde se debe inicializar todos los componentes,
	 * este metodo es cargado automáticamente antes del metodo onCreate().
	 */
	public void initComponents();

	/*Habilita o Deshabilita el acceso al button nuevo, según el sol del usuario*/
	public Boolean accesoNuevo();
	/*Habilita o Deshabilita el acceso al button Modificar, según el sol del usuario*/
	public Boolean accesoModificar();
	/*Habilita o Deshabilita el acceso al button Grabar, según el sol del usuario*/
	public Boolean accesoGrabar();
	/*Habilita o Deshabilita el acceso al button Eliminar, según el sol del usuario*/
	public Boolean accesoEliminar();
	/*Habilita o Deshabilita el acceso al button Consultar, según el sol del usuario*/
	public Boolean accesoConsultar();
	/*Habilita o Deshabilita el acceso al button Imprimir, según el sol del usuario*/
	public Boolean accesoImprimir();
	/*Habilita o Deshabilita el acceso al button Exportar, según el sol del usuario*/
	public Boolean accesoExportar();
	/**
	 * Habilita controles solamente para el rol super usuario
	 * @param lstComponents : Lista de controles a los se le aplicara esta restriccion.
	 */
	public void accesoControlsRolSuperUsuario(List<Component> lstComponents);
	/**
	 * Habilita controles solamente a los roles enviados en la lista
	 * @param lstComponents	: Lista de controles a los se le aplicara esta restriccion.
	 * @param listRolesAceeso : Lista de roles a los que se van a permitir el acceso
	 */
	public void accesoControlsByRol(List<Component> lstComponents, List<Rol> listRolesAceeso);
	/**
	 * Habilita controles solamente a los roles enviados en la lista
	 * @param lstComponents	: Lista de controles a los se le aplicara esta restriccion.
	 * @param listRolesAceeso : Lista de roles a los que se van a permitir el acceso
	 * @param ocultarControls : valor logico
	 */
	public void accesoControlsByRol(List<Component> lstComponents, List<Rol> listRolesAceeso, Boolean ocultarControls);

}
